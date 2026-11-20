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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

// @aa-sdd aetheris-plan-f-278.001.md
/**
 * The {@code LlamaCppLlmService} class is the llama.cpp backend for one chat completion.
 * The class implements {@link LlmProvider}.
 * The id is {@code llama-openapi}.
 * The chat URL is the base URL plus {@code /v1/chat/completions}.
 * Every body includes {@code model}.
 * An empty model throws {@link GrokLlmService.NoModelException} before the POST.
 * The connect timeout is 10 seconds.
 * The chat timeout is 120 seconds.
 * A blank API key omits {@code Authorization}.
 * The provider does not start {@code llama-server}.
 * The provider does not download a model.
 *
 * @author clayl (2026-11-19)
 */
@Component
public class LlamaCppLlmService implements LlmProvider {

    /** The mapper reads and writes the chat JSON. Server-sent lines use this same mapper. */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** The field is the provider id. */
    static final String ID = "llama-openapi";

    /** The field is the chat path. */
    static final String CHAT_PATH = "/v1/chat/completions";

    /** The field is the tokenize path. */
    public static final String TOKENIZE_PATH = "/tokenize";

    /** The field is the default base URL. */
    static final String DEFAULT_BASE = "http://127.0.0.1:3333";

    /** The field is the base URL with no trailing slash. */
    private final String baseUrl;

    /** The field is the API key. A blank value omits the header. */
    private final String apiKey;

    /** The field is the HTTP client. The connect timeout is 10 seconds. */
    private final HttpClient http;

    /**
     * The constructor stores the base URL and the API key.
     * A null properties object uses {@code http://127.0.0.1:3333} and a blank key.
     * A blank base URL uses that same default.
     *
     * @param properties The properties bind {@code spring.ai.llama-openapi}.
     */
    public LlamaCppLlmService(LlamaOpenApiProperties properties) {
        String base = DEFAULT_BASE;
        String key = "";
        if (properties != null) {
            if (properties.getBaseUrl() != null && properties.getBaseUrl().trim().length() > 0) {
                base = trimSlash(properties.getBaseUrl().trim());
            }
            if (properties.getApiKey() != null) {
                key = properties.getApiKey().trim();
            }
        }
        this.baseUrl = base;
        this.apiKey = key;
        this.http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    }

    /**
     * The {@code getId} method returns the provider id.
     *
     * @return The return value is {@code llama-openapi}.
     */
    @Override
    public String getId() {
        return ID;
    }

    /**
     * The {@code complete} method refuses a call that has no model.
     * The method does not POST.
     *
     * @param prompt The prompt is the assembled request.
     * @return The method does not return a completion.
     * @throws Exception The method throws {@link GrokLlmService.NoModelException}.
     */
    @Override
    public ChatResponse complete(Prompt prompt) throws Exception {
        throw new GrokLlmService.NoModelException();
    }

    /**
     * The {@code completeFor} method sends one chat completion.
     * An empty model throws {@link GrokLlmService.NoModelException} before the POST.
     * A null sink sends {@code stream} false and reads {@code choices[0].message.content}.
     * A non-null sink sends {@code stream} true and appends {@code delta.content}.
     * A status outside 2xx throws {@link GrokLlmService.StatusException}.
     * Empty assistant text throws an exception with the message {@code llama empty content}.
     *
     * @param model The model is the id after {@code llama-openapi/}.
     * @param prompt The prompt supplies {@code getInstructions()}.
     * @param options The options are the set fields. A null value sends no extra fields.
     * @param slot The slot receives the HTTP future. A null slot skips that store.
     * @param sink The sink receives kind {@code c}. A null sink is the one-shot call.
     * @return The return value is the completion.
     * @throws Exception The method throws this exception for an empty model, an empty prompt, or a failed call.
     */
    public ChatResponse completeFor(String model, Prompt prompt, LlamaCppCallOptions options,
            GrokLlmService.HttpSlot slot, GrokLlmService.DeltaSink sink) throws Exception {
        if (model == null || model.trim().length() == 0) {
            throw new GrokLlmService.NoModelException();
        }
        if (prompt == null) {
            throw new Exception("llama empty prompt");
        }
        boolean stream = sink != null;
        LlamaCppCallOptions held = options == null ? new LlamaCppCallOptions() : options;
        String json = requestBody(model.trim(), prompt.getInstructions(), held, stream);
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(java.net.URI.create(baseUrl + CHAT_PATH))
                .timeout(Duration.ofSeconds(120))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8));
        if (apiKey.length() > 0) {
            builder.header("Authorization", "Bearer " + apiKey);
        }
        HttpRequest request = builder.build();
        if (stream) {
            return completeStream(request, slot, sink);
        }
        return completeOnce(request, slot);
    }

    /**
     * The {@code requestBody} method builds the completion JSON.
     * {@code model} is always written.
     * A null message list throws an exception with the message {@code llama empty prompt}.
     * A null message, a null message type, or a role other than {@code system}, {@code user}, or {@code assistant} throws an exception with the message {@code llama unknown role}.
     * A null message text is stored as an empty string.
     * Unset option fields stay off the body.
     *
     * @param model The model is the model id.
     * @param messages The messages are the prompt instructions.
     * @param options The options are the set fields. A null value adds no extra fields.
     * @param stream The flag is true when a sink is attached.
     * @return The return value is the JSON body.
     * @throws Exception The method throws this exception for an empty prompt or an unknown role.
     */
    public static String requestBody(String model, List<Message> messages, LlamaCppCallOptions options,
            boolean stream) throws Exception {
        if (messages == null) {
            throw new Exception("llama empty prompt");
        }
        ObjectNode root = MAPPER.createObjectNode();
        root.put("model", model == null ? "" : model);
        ArrayNode wire = root.putArray("messages");
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message == null || message.getMessageType() == null) {
                throw new Exception("llama unknown role");
            }
            String role = message.getMessageType().getValue();
            if (!"system".equals(role) && !"user".equals(role) && !"assistant".equals(role)) {
                throw new Exception("llama unknown role");
            }
            String text = message.getText();
            if (text == null) {
                text = "";
            }
            ObjectNode one = wire.addObject();
            one.put("role", role);
            one.put("content", text);
        }
        if (options != null) {
            options.apply(root);
        }
        root.put("model", model == null ? "" : model);
        root.put("stream", stream);
        try {
            return MAPPER.writeValueAsString(root);
        } catch (Exception ex) {
            throw new Exception("llama content shape");
        }
    }

    /**
     * The {@code catalogText} method builds a {@code llama_models_result:} body from one models document.
     * The alias passed onward is blank.
     * A blank alias leaves each id unchanged.
     *
     * @param body The body is the {@code GET /v1/models} text.
     * @return The return value is the catalog reply.
     */
    public static String catalogText(String body) {
        return catalogText(body, "");
    }

    // @aa-sdd aetheris-plan-f-280.001.md
    /**
     * The {@code catalogText} method builds a {@code llama_models_result:} body from one models document.
     * A null body returns the header only.
     * A body that is not JSON returns the header only.
     * A missing {@code data} array returns the header only.
     * Each textual non-empty {@code data[].id} becomes one {@code model:} line.
     * A path-shaped id becomes the trimmed alias when that alias is non-blank.
     * A repeated name is one line.
     * Reply order is kept.
     * An empty id is skipped.
     *
     * @param body The body is the {@code GET /v1/models} text.
     * @param alias The alias replaces a path-shaped id. A null alias is blank.
     * @return The return value is the catalog reply.
     */
    public static String catalogText(String body, String alias) {
        String header = "llama_models_result:\n";
        String shown = alias == null ? "" : alias.trim();
        if (body == null) {
            return header;
        }
        JsonNode root;
        try {
            root = MAPPER.readTree(body);
        } catch (Exception ex) {
            return header;
        }
        if (root == null) {
            return header;
        }
        JsonNode data = root.get("data");
        if (data == null || !data.isArray()) {
            return header;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(header);
        ArrayList<String> written = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            JsonNode one = data.get(i);
            if (one == null || !one.isObject()) {
                continue;
            }
            JsonNode id = one.get("id");
            if (id == null || !id.isTextual()) {
                continue;
            }
            String name = id.asText().trim();
            if (name.length() == 0 || name.indexOf('\n') >= 0 || name.indexOf('\r') >= 0) {
                continue;
            }
            if (shown.length() > 0 && pathShaped(name)) {
                name = shown;
            }
            boolean seen = false;
            for (int j = 0; j < written.size(); j++) {
                if (name.equals(written.get(j))) {
                    seen = true;
                }
            }
            if (seen) {
                continue;
            }
            written.add(name);
            sb.append("model: ").append(name).append('\n');
        }
        return sb.toString();
    }

    // @aa-sdd aetheris-plan-f-281.001.md
    /**
     * The {@code tokenizeBody} method writes one tokenize document.
     * The document has one field, {@code content}.
     * A null text is stored as an empty string.
     * The method does not open a socket.
     *
     * @param text The text is the prompt to tokenize. The value may be null.
     * @return The return value is the JSON body.
     */
    public static String tokenizeBody(String text) {
        ObjectNode root = MAPPER.createObjectNode();
        root.put("content", text == null ? "" : text);
        try {
            return MAPPER.writeValueAsString(root);
        } catch (Exception ex) {
            return "{\"content\":\"\"}";
        }
    }

    // @aa-sdd aetheris-plan-f-281.001.md
    /**
     * The {@code tokenCount} method reads the length of a {@code tokens} array.
     * A null body returns {@code -1}.
     * A body that is not JSON returns {@code -1}.
     * A missing {@code tokens} array returns {@code -1}.
     * An empty array returns {@code 0}.
     * The method does not open a socket.
     *
     * @param json The json is the tokenize reply. The value may be null.
     * @return The return value is the array length, or {@code -1} when that array is missing.
     */
    public static int tokenCount(String json) {
        if (json == null) {
            return -1;
        }
        JsonNode root;
        try {
            root = MAPPER.readTree(json);
        } catch (Exception ex) {
            return -1;
        }
        if (root == null || !root.isObject()) {
            return -1;
        }
        JsonNode tokens = root.get("tokens");
        if (tokens == null || !tokens.isArray()) {
            return -1;
        }
        return tokens.size();
    }

    // @aa-sdd aetheris-plan-f-282.001.md
    /**
     * The {@code applyTemplateBody} method writes one apply-template document.
     * The document has one field, {@code messages}.
     * Each message writes {@code role} and {@code content}.
     * A null message list throws an exception with the message {@code llama empty prompt}.
     * A null message, a null message type, or a role other than {@code system}, {@code user}, or {@code assistant} throws an exception with the message {@code llama unknown role}.
     * A null message text is stored as an empty string.
     * The method does not open a socket.
     *
     * @param messages The messages are the prompt. The value may be null.
     * @return The return value is the JSON body.
     * @throws Exception The method throws this exception for an empty prompt or an unknown role.
     */
    public static String applyTemplateBody(List<Message> messages) throws Exception {
        if (messages == null) {
            throw new Exception("llama empty prompt");
        }
        ObjectNode root = MAPPER.createObjectNode();
        ArrayNode wire = root.putArray("messages");
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message == null || message.getMessageType() == null) {
                throw new Exception("llama unknown role");
            }
            String role = message.getMessageType().getValue();
            if (!"system".equals(role) && !"user".equals(role) && !"assistant".equals(role)) {
                throw new Exception("llama unknown role");
            }
            String text = message.getText();
            if (text == null) {
                text = "";
            }
            ObjectNode one = wire.addObject();
            one.put("role", role);
            one.put("content", text);
        }
        try {
            return MAPPER.writeValueAsString(root);
        } catch (Exception ex) {
            throw new Exception("llama content shape");
        }
    }

    // @aa-sdd aetheris-plan-f-282.001.md
    /**
     * The {@code templatePrompt} method reads the {@code prompt} field.
     * A null body returns null.
     * A body that is not JSON returns null.
     * A missing {@code prompt} field returns null.
     * A prompt that is not text returns null.
     * The method does not open a socket.
     *
     * @param json The json is the apply-template reply. The value may be null.
     * @return The return value is the prompt text, or null when that field is missing.
     */
    public static String templatePrompt(String json) {
        if (json == null) {
            return null;
        }
        JsonNode root;
        try {
            root = MAPPER.readTree(json);
        } catch (Exception ex) {
            return null;
        }
        if (root == null || !root.isObject()) {
            return null;
        }
        JsonNode prompt = root.get("prompt");
        if (prompt == null || !prompt.isTextual()) {
            return null;
        }
        return prompt.asText();
    }

    // @aa-sdd aetheris-plan-f-280.001.md
    /**
     * The {@code pathShaped} method reports whether a model id is a file path.
     * A null id is not a path.
     * An id that contains {@code /} is a path.
     * An id that contains {@code \} is a path.
     * An id that ends with {@code .gguf} is a path.
     * The suffix compare ignores case.
     *
     * @param id The id is the catalog model id. The value may be null.
     * @return The return value is true when the id is path-shaped.
     */
    public static boolean pathShaped(String id) {
        if (id == null) {
            return false;
        }
        String name = id.trim();
        if (name.indexOf('/') >= 0 || name.indexOf('\\') >= 0) {
            return true;
        }
        return name.length() >= 5 && name.regionMatches(true, name.length() - 5, ".gguf", 0, 5);
    }

    /**
     * The {@code completeOnce} method sends the request and reads one JSON body.
     * When the slot is present, the HTTP future is stored on it before the wait.
     * A status outside 2xx throws {@link GrokLlmService.StatusException}.
     * Empty assistant content throws an exception with the message {@code llama empty content}.
     *
     * @param request The request is the POST to send.
     * @param slot The slot receives the HTTP future. A null slot skips that store.
     * @return The return value is the completion.
     * @throws Exception The method throws this exception when the status is outside 2xx or the content is empty.
     */
    private ChatResponse completeOnce(HttpRequest request, GrokLlmService.HttpSlot slot) throws Exception {
        CompletableFuture<HttpResponse<String>> call = http.sendAsync(request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (slot != null) {
            slot.future = call;
        }
        HttpResponse<String> response = waitString(call);
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new GrokLlmService.StatusException(response.statusCode(),
                    publicFailure(response.statusCode(), response.body()));
        }
        String content = assistantContent(response.body());
        if (content.length() == 0) {
            throw new Exception("llama empty content");
        }
        return chat(content);
    }

    /**
     * The {@code completeStream} method sends the request and reads server-sent lines.
     * When the slot is present, the HTTP future is stored on it before the wait.
     * A status outside 2xx throws {@link GrokLlmService.StatusException}.
     * Only {@code delta.content} is appended.
     * Empty joined content throws an exception with the message {@code llama empty content}.
     *
     * @param request The request is the POST to send.
     * @param slot The slot receives the HTTP future. A null slot skips that store.
     * @param sink The sink receives kind {@code c}.
     * @return The return value is the completion whose text is the joined content.
     * @throws Exception The method throws this exception when the call is stopped or the read fails.
     */
    private ChatResponse completeStream(HttpRequest request, GrokLlmService.HttpSlot slot,
            GrokLlmService.DeltaSink sink) throws Exception {
        CompletableFuture<HttpResponse<InputStream>> call = http.sendAsync(request,
                HttpResponse.BodyHandlers.ofInputStream());
        if (slot != null) {
            slot.future = call;
        }
        HttpResponse<InputStream> response;
        try {
            response = call.get();
        } catch (CancellationException ex) {
            throw new GrokLlmService.CallStopped();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new GrokLlmService.CallStopped();
        } catch (ExecutionException ex) {
            if (call.isCancelled()) {
                throw new GrokLlmService.CallStopped();
            }
            throw transportOf(ex);
        }
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new GrokLlmService.StatusException(response.statusCode(),
                    publicFailure(response.statusCode(), ""));
        }
        String content = readSse(response.body(), call, sink);
        if (content.length() == 0) {
            throw new Exception("llama empty content");
        }
        return chat(content);
    }

    /**
     * The {@code readSse} method reads server-sent lines until {@code data: [DONE]}.
     * {@code delta.content} is kind {@code c} and is appended.
     * {@code reasoning_content} is not read.
     * A cancelled HTTP future throws {@link GrokLlmService.CallStopped}.
     *
     * @param in The stream is the response body.
     * @param call The future is the HTTP call.
     * @param sink The sink receives each content chunk. A null sink drops the text.
     * @return The return value is the joined content.
     * @throws Exception The method throws this exception for a bad shape, a transport failure, or a stopped call.
     */
    private static String readSse(InputStream in, CompletableFuture<?> call, GrokLlmService.DeltaSink sink)
            throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        StringBuilder joined = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (call != null && call.isCancelled()) {
                    throw new GrokLlmService.CallStopped();
                }
                String trimmed = line.trim();
                if (trimmed.length() == 0 || trimmed.startsWith(":")) {
                    continue;
                }
                if (!trimmed.startsWith("data:")) {
                    continue;
                }
                String payload = trimmed.substring("data:".length()).trim();
                if ("[DONE]".equals(payload) || payload.length() == 0) {
                    if ("[DONE]".equals(payload)) {
                        break;
                    }
                    continue;
                }
                JsonNode root;
                try {
                    root = MAPPER.readTree(payload);
                } catch (Exception ex) {
                    throw new Exception("llama content shape");
                }
                JsonNode choices = root.get("choices");
                if (choices == null || !choices.isArray() || choices.size() == 0) {
                    continue;
                }
                JsonNode first = choices.get(0);
                if (first == null || !first.isObject()) {
                    continue;
                }
                JsonNode delta = first.get("delta");
                if (delta == null || !delta.isObject()) {
                    continue;
                }
                emitContent(sink, joined, delta.get("content"));
            }
        } catch (GrokLlmService.CallStopped ex) {
            throw ex;
        } catch (IOException ex) {
            if (call != null && call.isCancelled()) {
                throw new GrokLlmService.CallStopped();
            }
            throw new Exception(transportFailure(ex));
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
            }
        }
        return joined.toString();
    }

    /**
     * The {@code emitContent} method appends one content chunk.
     * A null node or a JSON null returns.
     * A node that is not text throws an exception with the message {@code llama content shape}.
     * An empty string returns.
     *
     * @param sink The sink receives kind {@code c}. A null sink drops the text.
     * @param joined The builder holds the joined reply text.
     * @param node The node is {@code delta.content}.
     * @throws Exception The method throws this exception when the node is not text or the sink stops the read.
     */
    private static void emitContent(GrokLlmService.DeltaSink sink, StringBuilder joined, JsonNode node)
            throws Exception {
        if (node == null || node.isNull()) {
            return;
        }
        if (!node.isTextual()) {
            throw new Exception("llama content shape");
        }
        String text = node.asText();
        if (text.length() == 0) {
            return;
        }
        joined.append(text);
        if (sink != null) {
            sink.delta('c', text);
        }
    }

    /**
     * The {@code assistantContent} method reads {@code choices[0].message.content}.
     * A missing content node returns an empty string.
     * A content node that is not text throws an exception with the message {@code llama content shape}.
     *
     * @param body The body is the response text.
     * @return The return value is the assistant content.
     * @throws Exception The method throws this exception when the body cannot be read.
     */
    private static String assistantContent(String body) throws Exception {
        if (body == null || body.trim().length() == 0) {
            throw new Exception("llama empty content");
        }
        JsonNode root;
        try {
            root = MAPPER.readTree(body);
        } catch (Exception ex) {
            throw new Exception("llama content shape");
        }
        JsonNode choices = root.get("choices");
        if (choices == null || !choices.isArray() || choices.size() == 0) {
            throw new Exception("llama empty content");
        }
        JsonNode first = choices.get(0);
        if (first == null || !first.isObject()) {
            throw new Exception("llama empty content");
        }
        JsonNode message = first.get("message");
        if (message == null || !message.isObject()) {
            throw new Exception("llama empty content");
        }
        JsonNode content = message.get("content");
        if (content == null || content.isNull()) {
            return "";
        }
        if (!content.isTextual()) {
            throw new Exception("llama content shape");
        }
        return content.asText();
    }

    /**
     * The {@code chat} method wraps assistant text in one chat response.
     *
     * @param content The content is the assistant text.
     * @return The return value is the completion.
     */
    private static ChatResponse chat(String content) {
        ArrayList<Generation> generations = new ArrayList<Generation>();
        generations.add(new Generation(new AssistantMessage(content)));
        return new ChatResponse(generations);
    }

    /**
     * The {@code waitString} method waits for the string response.
     * Cancel throws {@link GrokLlmService.CallStopped}.
     * Interrupt sets the thread interrupt flag and throws {@link GrokLlmService.CallStopped}.
     *
     * @param call The future is the HTTP call.
     * @return The return value is the HTTP response.
     * @throws Exception The method throws this exception when the wait is stopped or the send fails.
     */
    private static HttpResponse<String> waitString(CompletableFuture<HttpResponse<String>> call) throws Exception {
        try {
            return call.get();
        } catch (CancellationException ex) {
            throw new GrokLlmService.CallStopped();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new GrokLlmService.CallStopped();
        } catch (ExecutionException ex) {
            if (call.isCancelled()) {
                throw new GrokLlmService.CallStopped();
            }
            throw transportOf(ex);
        }
    }

    /**
     * The {@code transportOf} method turns a send failure into an exception.
     * {@link GrokLlmService.CallStopped} and {@link GrokLlmService.StatusException} are returned as themselves.
     *
     * @param ex The exception is the send failure.
     * @return The return value is the exception to throw.
     */
    private static Exception transportOf(ExecutionException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof GrokLlmService.CallStopped) {
            return (GrokLlmService.CallStopped) cause;
        }
        if (cause instanceof GrokLlmService.StatusException) {
            return (GrokLlmService.StatusException) cause;
        }
        if (cause instanceof Exception) {
            return new Exception(transportFailure((Exception) cause));
        }
        return new Exception(transportFailure(ex));
    }

    /**
     * The {@code publicFailure} method builds a status line.
     * The prefix is {@code llama HTTP}, the status, and a space.
     * The key text is replaced before the message is returned.
     *
     * @param status The status is the HTTP status code.
     * @param body The body is the response text.
     * @return The return value is the exception message.
     */
    private String publicFailure(int status, String body) {
        String shown = body == null ? "" : body;
        if (apiKey.length() > 0 && shown.indexOf(apiKey) >= 0) {
            shown = shown.replace(apiKey, "[redacted]");
        }
        shown = shown.replace('\r', ' ').replace('\n', ' ');
        if (shown.length() > 180) {
            shown = shown.substring(0, 180);
        }
        return "llama HTTP " + status + " " + shown;
    }

    /**
     * The {@code transportFailure} method builds a transport line with no HTTP status.
     *
     * @param ex The exception is the send failure.
     * @return The return value is the exception message.
     */
    private static String transportFailure(Exception ex) {
        String name = ex == null ? "error" : ex.getClass().getSimpleName();
        String detail = ex == null || ex.getMessage() == null ? "" : ex.getMessage();
        String shown = (name + " " + detail).replace('\r', ' ').replace('\n', ' ');
        if (shown.length() > 180) {
            shown = shown.substring(0, 180);
        }
        return "llama " + shown;
    }

    /**
     * The {@code trimSlash} method drops one trailing slash.
     *
     * @param url The URL is the base.
     * @return The return value is the URL without one trailing slash.
     */
    private static String trimSlash(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }
}
/*****************************************************************************************
 * End of Class: LlamaCppLlmService.java 111926
 ****************************************************************************************/
