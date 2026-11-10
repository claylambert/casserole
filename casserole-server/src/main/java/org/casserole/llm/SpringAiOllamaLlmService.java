/*
 * Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
 * All rights reserved.
 *
 * Author: Clay Lambert
 *
 * Licensed under the MIT License.
 * If you use or modify this source, you must include this copyright notice
 * and the MIT License permission notice in all copies or substantial portions
 * of the software, including modifications.
 */

package org.casserole.llm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.ollama.api.ThinkOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import reactor.core.Exceptions;

// @aa-sdd PK-0604 PK-0727 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0620 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0632 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0621 PK-0676 PK-0679 PK-0680 PK-0682 PK-0683 PK-0684 docs/specs/java/org/casserole/llm/llm.spec.md
/**
 * The {@code SpringAiOllamaLlmService} class is the Ollama backend for one chat completion.
 * The class implements {@link LlmService} and {@link LlmProvider}. It holds a chat model, an API bean, a context-window integer, and a configured model id.
 * A caller asks for one completion. A set think option with a sink and a text-only prompt streams through the shared API. Think left unset, a null sink, or a prompt that is not text-only stays on the one-shot chat-model call.
 *
 * @author clayl (2026-08-24)
 */
@Service
public class SpringAiOllamaLlmService implements LlmService, LlmProvider {

    private final ChatModel chatModel;

    /** The field holds the injected API bean. The stream path uses this bean. The service does not build a second client. */
    private final OllamaApi ollamaApi;

    /** The field is the configured context window. A value above {@code 0} is sent as {@code num_ctx}. {@code 0} leaves {@code num_ctx} off the request. */
    private final int numCtx;

    /** The field is {@code spring.ai.ollama.chat.options.model}. The stream uses it when the caller model and the chat-model default are both empty. */
    private final String configuredModel;

    /**
     * The constructor stores the chat model, the API bean, the context window, and the configured model id.
     * A null configured model is stored as an empty string.
     *
     * @param chatModel The chat model is the auto-configured Ollama chat model. The one-shot path calls it.
     * @param ollamaApi The API bean is the auto-configured Ollama API. The stream path uses it.
     * @param numCtx The integer is {@code spring.ai.ollama.chat.options.num-ctx}. {@code 0} leaves {@code num_ctx} off the request.
     * @param configuredModel The text is {@code spring.ai.ollama.chat.options.model}. An empty value means the property is unset.
     */
    public SpringAiOllamaLlmService(
            ChatModel chatModel,
            OllamaApi ollamaApi,
            @Value("${spring.ai.ollama.chat.options.num-ctx:0}") int numCtx,
            @Value("${spring.ai.ollama.chat.options.model:}") String configuredModel) {
        this.chatModel = chatModel;
        this.ollamaApi = ollamaApi;
        this.numCtx = numCtx;
        this.configuredModel = configuredModel == null ? "" : configuredModel;
    }

    /**
     * The {@code getId} method returns the provider id.
     *
     * @return The return value is {@code ollama}.
     */
    @Override
    public String getId() {
        return "ollama";
    }

    /**
     * The {@code complete} method sends the prompt with an empty model, an empty think string, and no sink.
     * The empty think string leaves the think option unset. The null sink keeps the call on the one-shot path.
     *
     * @param prompt The prompt is the assembled request.
     * @return The return value is the completion from {@link #complete(Prompt, String, String, GrokLlmService.DeltaSink)}.
     * @throws Exception The method throws this exception when the one-shot call returns nothing.
     */
    @Override
    public ChatResponse complete(Prompt prompt) throws Exception {
        return complete(prompt, "", "", null);
    }

    /**
     * The {@code complete} method sends one prompt with a model id and a think string and no sink.
     * A null sink keeps the call on the one-shot chat-model path.
     *
     * @param prompt The prompt is the assembled request.
     * @param model The model is the id after {@code ollama/}. An empty model keeps the configured model on the one-shot path.
     * @param think The think text is empty to leave think unset, {@code true} for the enabled flag, or any other non-empty text except {@code false} for a level.
     * @return The return value is the completion.
     * @throws Exception The method throws this exception when the client returns nothing.
     */
    public ChatResponse complete(Prompt prompt, String model, String think) throws Exception {
        return complete(prompt, model, think, null);
    }

    /**
     * The {@code complete} method chooses a stream or a one-shot call.
     * The model text is trimmed. A null model becomes an empty string. {@link #thinkOptionOf} turns the think text into an option.
     * If the option is null, the sink is null, or {@link #textOnly} is false, the method calls {@link #callOnce}. Otherwise the method calls {@link #streamThink}.
     *
     * The text {@code true} becomes the enabled think flag. Any other non-empty text except {@code false} becomes a think level that carries that text. Empty text and {@code false} leave the option unset. This method never returns the disabled boolean flag. The string {@code DISABLED} is non-empty and is not {@code false}, so it becomes a level.
     * A set option, a sink, and a text-only prompt stream with {@code stream} set to true. Each thinking piece goes to the sink as kind {@code t}. Kind {@code c} is not sent. The joined message content is the reply.
     * The stream request carries the resolved model, the think option, and {@code num_ctx} when that integer is greater than {@code 0}.
     * The streamed reply uses empty generation metadata, so it has no {@code thinking} key. An image, a tool call, or a tool result stays on the one-shot call. The one-shot reply keeps thinking text when the metadata key {@code thinking} is present.
     *
     * @param prompt The prompt is the assembled request.
     * @param model The model is the id after {@code ollama/}. An empty model keeps the configured model when the stream must resolve one.
     * @param think The think text is empty or {@code false} to leave think unset, {@code true} for the enabled flag, or another non-empty text for a level.
     * @param sink The sink receives kind {@code t} pieces. A null sink keeps the one-shot call.
     * @return The return value is the completion.
     * @throws Exception The method throws this exception when the client returns nothing or the read is stopped.
     */
    public ChatResponse complete(Prompt prompt, String model, String think, GrokLlmService.DeltaSink sink)
            throws Exception {
        String modelId = model == null ? "" : model.trim();
        ThinkOption option = thinkOptionOf(think);
        if (option == null || sink == null || !textOnly(prompt)) {
            return callOnce(prompt, modelId, option);
        }
        return streamThink(prompt, modelId, option, sink);
    }

    /**
     * The {@code thinkingText} method reads the metadata key {@code thinking} from a one-shot reply.
     * A null response, a null result, null metadata, a null value, or the text {@code null} returns an empty string.
     *
     * @param response The response is one chat completion.
     * @return The return value is the thinking text, or an empty string when that text is absent.
     */
    public static String thinkingText(ChatResponse response) {
        if (response == null || response.getResult() == null) {
            return "";
        }
        Generation generation = response.getResult();
        if (generation.getMetadata() == null) {
            return "";
        }
        Object value = generation.getMetadata().get("thinking");
        if (value == null) {
            return "";
        }
        String text = String.valueOf(value);
        if ("null".equals(text)) {
            return "";
        }
        return text;
    }

    /**
     * The {@code thinkOptionOf} method turns a think string into the option the request will send.
     * A null string is treated as empty. The text {@code true} returns the enabled flag. Any other non-empty text except {@code false} returns a level that carries that text. Empty text and {@code false} return null, so think stays unset.
     *
     * @param think The think text is empty to leave think unset, {@code true} for the enabled flag, or another non-empty text for a level.
     * @return The return value is the option to send, or null when think stays unset.
     */
    static ThinkOption thinkOptionOf(String think) {
        String level = think == null ? "" : think.trim();
        if ("true".equals(level)) {
            return ThinkOption.ThinkBoolean.ENABLED;
        }
        if (level.length() > 0 && !"false".equals(level)) {
            return new ThinkOption.ThinkLevel(level);
        }
        return null;
    }

    /**
     * The {@code textOnly} method reports whether every message is plain system, user, or assistant text.
     * A null prompt returns false. A null instruction list returns true. A null message or a null message type returns false.
     * A user message with media returns false. An assistant message with tool calls returns false. Any other message type returns false.
     *
     * @param prompt The prompt is the assembled request.
     * @return The return value is true when every message is plain text.
     */
    static boolean textOnly(Prompt prompt) {
        if (prompt == null) {
            return false;
        }
        List<Message> messages = prompt.getInstructions();
        if (messages == null) {
            return true;
        }
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message == null || message.getMessageType() == null) {
                return false;
            }
            if (message instanceof UserMessage user) {
                List<?> media = user.getMedia();
                if (media != null && !media.isEmpty()) {
                    return false;
                }
            }
            if (message instanceof AssistantMessage assistant && assistant.hasToolCalls()) {
                return false;
            }
            MessageType type = message.getMessageType();
            if (type != MessageType.SYSTEM && type != MessageType.USER && type != MessageType.ASSISTANT) {
                return false;
            }
        }
        return true;
    }

    /**
     * The {@code acceptStreamChunk} method reads one stream frame.
     * A null chunk, a null message, or a null content builder returns without writing. Non-empty thinking goes to the sink as kind {@code t} when the sink is present. Non-empty content is appended to the builder and is not sent as its own delta. Kind {@code c} is not sent. An empty thinking string and an empty content string are skipped.
     *
     * @param chunk The chunk is one stream frame.
     * @param content The builder holds the joined reply text.
     * @param sink The sink receives kind {@code t} pieces.
     * @throws Exception The method throws this exception when the sink stops the read.
     */
    static void acceptStreamChunk(OllamaApi.ChatResponse chunk, StringBuilder content, GrokLlmService.DeltaSink sink)
            throws Exception {
        if (chunk == null || chunk.message() == null || content == null) {
            return;
        }
        String thinking = chunk.message().thinking();
        if (thinking != null && thinking.length() > 0 && sink != null) {
            sink.delta('t', thinking);
        }
        String piece = chunk.message().content();
        if (piece != null && piece.length() > 0) {
            content.append(piece);
        }
    }

    /**
     * The {@code raiseStreamFailure} method unwraps a stream failure and throws it again.
     * {@link GrokLlmService.CallStopped} is thrown as itself. A runtime exception is thrown as itself. An error is thrown as itself. A checked exception from the unwrapped cause is thrown as itself.
     * If the unwrapped cause is none of those and the original throwable is an exception, that original exception is thrown. Any other case throws a new exception. A null throwable uses the message {@code Ollama stream failed.} Otherwise the new exception uses the throwable message.
     *
     * @param thrown The throwable is the failure from the stream pull.
     * @throws Exception The method throws the stopped read or the provider failure.
     */
    static void raiseStreamFailure(Throwable thrown) throws Exception {
        Throwable cause = thrown == null ? null : Exceptions.unwrap(thrown);
        if (cause instanceof GrokLlmService.CallStopped stopped) {
            throw stopped;
        }
        if (cause instanceof RuntimeException runtime) {
            throw runtime;
        }
        if (cause instanceof Error error) {
            throw error;
        }
        if (cause instanceof Exception checked) {
            throw checked;
        }
        if (thrown instanceof Exception checked) {
            throw checked;
        }
        throw new Exception(thrown == null ? "Ollama stream failed." : String.valueOf(thrown.getMessage()));
    }

    /**
     * The {@code replyOf} method builds a completion from joined content and empty generation metadata.
     * A null text is stored as an empty string. The metadata is empty, so the reply has no {@code thinking} key. {@link #thinkingText} on this reply returns an empty string.
     *
     * @param text The text is the joined content.
     * @return The return value is the completion.
     */
    static ChatResponse replyOf(String text) {
        AssistantMessage message = new AssistantMessage(text == null ? "" : text);
        Generation generation = new Generation(message, ChatGenerationMetadata.NULL);
        ArrayList<Generation> generations = new ArrayList<Generation>();
        generations.add(generation);
        return new ChatResponse(generations);
    }

    /**
     * The {@code callOnce} method sends one prompt through the chat model.
     * If the model id is non-empty, the context window is above {@code 0}, or the think option is set, those values are copied onto a new options object and the prompt is rebuilt. Otherwise the original prompt is sent.
     * A null response throws an exception with the message {@code Ollama returned no response.} Thinking text stays in the metadata when the client fills the {@code thinking} key.
     *
     * @param prompt The prompt is the assembled request.
     * @param modelId The model id is the trimmed caller model. An empty id keeps the bean model.
     * @param option The option is the think option, or null when think stays unset.
     * @return The return value is the completion.
     * @throws Exception The method throws this exception when the client returns nothing.
     */
    private ChatResponse callOnce(Prompt prompt, String modelId, ThinkOption option) throws Exception {
        Prompt request = prompt;
        if (modelId.length() > 0 || numCtx > 0 || option != null) {
            OllamaChatOptions options = new OllamaChatOptions();
            if (modelId.length() > 0) {
                options.setModel(modelId);
            }
            if (numCtx > 0) {
                options.setNumCtx(Integer.valueOf(numCtx));
            }
            if (option != null) {
                options.setThinkOption(option);
            }
            request = new Prompt(prompt.getInstructions(), options);
        }
        ChatResponse response = chatModel.call(request);
        if (response == null) {
            throw new Exception("Ollama returned no response.");
        }
        return response;
    }

    /**
     * The {@code streamThink} method pulls each frame on this thread.
     * The sink runs inside {@link #acceptStreamChunk} before the next frame is taken. Closing the stream cancels the HTTP call. An empty pull returns empty content and does not throw.
     * A runtime exception from the pull is passed to {@link #raiseStreamFailure}. The reply is built by {@link #replyOf}, so the generation metadata has no thinking text.
     *
     * @param prompt The prompt is a text-only request.
     * @param modelId The model id is the trimmed caller model.
     * @param option The option is the set think option.
     * @param sink The sink receives kind {@code t} pieces.
     * @return The return value is the completion with empty thinking metadata.
     * @throws Exception The method throws this exception when no model is defined, the read is stopped, or the client fails.
     */
    private ChatResponse streamThink(Prompt prompt, String modelId, ThinkOption option, GrokLlmService.DeltaSink sink)
            throws Exception {
        OllamaChatOptions options = streamOptions(modelId, option);
        OllamaApi.ChatRequest request = OllamaApi.ChatRequest.builder(options.getModel())
                .stream(true)
                .messages(ollamaMessages(prompt))
                .options(options)
                .think(option)
                .build();
        StringBuilder content = new StringBuilder();
        try (Stream<OllamaApi.ChatResponse> frames = ollamaApi.streamingChat(request).limitRate(1).toStream()) {
            Iterator<OllamaApi.ChatResponse> it = frames.iterator();
            while (it.hasNext()) {
                acceptStreamChunk(it.next(), content, sink);
            }
        } catch (RuntimeException ex) {
            raiseStreamFailure(ex);
        }
        return replyOf(content.toString());
    }

    /**
     * The {@code streamOptions} method resolves the model and copies the stream options.
     * The caller model is used first. If that text is empty, the chat-model default is used. If that text is also empty, {@code spring.ai.ollama.chat.options.model} is used. If every one of those is empty, the method throws {@link GrokLlmService.NoModelException}.
     * {@code num_ctx} is set when the configured integer is greater than {@code 0}. The think option is set on the copy. When the chat-model defaults are Ollama chat options, the copy starts from those defaults. Other bean defaults stay on that copy.
     *
     * @param callerModel The caller model is the trimmed model id from the caller.
     * @param option The option is the set think option.
     * @return The return value is the options object for the stream request.
     * @throws GrokLlmService.NoModelException The method throws this exception when no model is defined.
     */
    private OllamaChatOptions streamOptions(String callerModel, ThinkOption option)
            throws GrokLlmService.NoModelException {
        OllamaChatOptions options = new OllamaChatOptions();
        ChatOptions defaults = chatModel.getDefaultOptions();
        if (defaults instanceof OllamaChatOptions ollamaDefaults) {
            options = OllamaChatOptions.fromOptions(ollamaDefaults);
        }
        String resolved = callerModel == null ? "" : callerModel.trim();
        if (resolved.length() == 0 && options.getModel() != null) {
            resolved = options.getModel().trim();
        }
        if (resolved.length() == 0) {
            resolved = configuredModel.trim();
        }
        if (resolved.length() == 0) {
            throw new GrokLlmService.NoModelException();
        }
        options.setModel(resolved);
        if (numCtx > 0) {
            options.setNumCtx(Integer.valueOf(numCtx));
        }
        options.setThinkOption(option);
        return options;
    }

    /**
     * The {@code ollamaMessages} method copies plain-text roles into Ollama messages.
     * A null prompt or a null instruction list returns an empty list. A null message is skipped. System, user, and assistant types keep those roles. Any other type throws an illegal-argument exception whose message starts with {@code Unsupported message type: }. A null message text is stored as an empty string.
     *
     * @param prompt The prompt is a text-only request.
     * @return The return value is the list of Ollama messages.
     */
    private static List<OllamaApi.Message> ollamaMessages(Prompt prompt) {
        ArrayList<OllamaApi.Message> out = new ArrayList<OllamaApi.Message>();
        List<Message> messages = prompt == null ? null : prompt.getInstructions();
        if (messages == null) {
            return out;
        }
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message == null) {
                continue;
            }
            MessageType type = message.getMessageType();
            OllamaApi.Message.Role role;
            if (type == MessageType.SYSTEM) {
                role = OllamaApi.Message.Role.SYSTEM;
            } else if (type == MessageType.USER) {
                role = OllamaApi.Message.Role.USER;
            } else if (type == MessageType.ASSISTANT) {
                role = OllamaApi.Message.Role.ASSISTANT;
            } else {
                throw new IllegalArgumentException("Unsupported message type: " + type);
            }
            String text = message.getText();
            out.add(OllamaApi.Message.builder(role).content(text == null ? "" : text).build());
        }
        return out;
    }
}
/*****************************************************************************************
 * End of Class: SpringAiOllamaLlmService.java 082426
 ****************************************************************************************/
