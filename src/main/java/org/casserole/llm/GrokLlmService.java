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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
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
import java.util.function.BiConsumer;

import org.casserole.config.CasseroleProperties;
import org.springframework.ai.chat.messages.AssistantMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

// @aa-sdd PK-1072 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1065 docs/specs/common/formal-plan.spec.md
/**
 * The {@code GrokLlmService} class is the Grok backend for one chat completion.
 * The class implements {@link LlmProvider}. It does not send {@code num_ctx}.
 * The key order is {@code casserole.grok.api-key}, then the environment variable {@code XAI_API_KEY}, then {@code ~/.aetheris/grok.key}, then {@code ~/.casserole/grok.key}.
 * A blank property value is a miss. An empty model throws {@link NoModelException} with the message {@code no model defined} before the key is read. A missing key or an empty URL throws {@link KeyMissingException} with the message {@code grok key missing}.
 * The connect timeout is 10 seconds. The HTTP timeout is 1800 seconds. The request URL is {@code https://api.x.ai/v1/chat/completions}.
 * The model list is {@code GET https://api.x.ai/v1/language-models}. A miss returns an empty list. The list call follows no redirect and uses HTTP/1.1.
 * The key is not logged. The key is not written into an exception message.
 * // @aa-sdd G-05 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-10-20)
 */
@Component
// @aa-sdd PK-790 docs/specs/common/grok.spec.md
// @aa-sdd PK-371 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-372 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-739 docs/specs/common/stages.spec.md
// @aa-sdd PK-758 docs/specs/common/stages.spec.md
// @aa-sdd PK-963 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-697 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-698 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-394 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-729 docs/specs/common/server.spec.md
// @aa-sdd PK-1299 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-682 docs/specs/common/grok.spec.md
// @aa-sdd PK-363 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-366 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-683 docs/specs/common/grok.spec.md
// @aa-sdd PK-960 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-882 docs/specs/common/stages.spec.md
// @aa-sdd PK-656 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-657 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-387 docs/specs/java/org/casserole/llm/llm.spec.md
public class GrokLlmService implements LlmProvider {

    /** The mapper reads and writes external JSON. Server-sent event lines use this same mapper. TOON and EN-IR stay on their own readers. */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** The field is the environment variable name. An empty or absent value is a miss. */
    static final String ENV_NAME = "XAI_API_KEY";

    /** The field is the bound {@code casserole.grok.api-key}. A blank value is a miss. The value is not logged. */
    private final String yamlKey;

    /** The field is the file name under {@code ~/.aetheris/} and {@code ~/.casserole/}. */
    static final String KEY_FILE = "grok.key";

    /** The field is the chat completions URL. The request does not send {@code num_ctx}. */
    static final String ENDPOINT = "https://api.x.ai/v1/chat/completions";

    /** The field is the language-model catalog URL. The call is a GET. The key rides on the Authorization header. */
    static final String LANGUAGE_MODELS = "https://api.x.ai/v1/language-models";

    /** The field is the probe user text. The characters are exact. */
    static final String HELLO = "Hello";

    /**
     * The constructor stores the bound Grok key.
     * A null properties object, a null Grok block, or a null key is stored as an empty string. The stored text is trimmed.
     *
     * @param properties The properties object carries {@code casserole.*}. A null Grok block is a miss.
     */
    public GrokLlmService(CasseroleProperties properties) {
        String value = "";
        if (properties != null && properties.getGrok() != null && properties.getGrok().getApiKey() != null) {
            value = properties.getGrok().getApiKey().trim();
        }
        this.yamlKey = value;
    }

    /**
     * The {@code getId} method returns the provider id.
     *
     * @return The return value is {@code grok}.
     */
    @Override
    public String getId() {
        return "grok";
    }

    /**
     * The {@code complete} method forwards the prompt with an empty model id.
     * The empty model fails in {@link #completeFor(String, Prompt)} before the key is read.
     *
     * @param prompt The prompt is the assembled request.
     * @return The return value is the completion from {@link #completeFor(String, Prompt)}.
     * @throws Exception The method throws this exception when the model is empty, the key is missing, or the call fails.
     */
    @Override
    public ChatResponse complete(Prompt prompt) throws Exception {
        return completeFor("", prompt);
    }

    /**
     * The {@code currentKey} method returns the key {@link #readKey} would return.
     * The text is not logged.
     *
     * @return The return value is the key text, or an empty string when every source misses.
     */
    public String currentKey() {
        return readKey();
    }

    /**
     * The {@code startProbe} method sends one user message whose text is {@code Hello}.
     * The body leaves temperature and {@code top_p} off. The greeting text is not returned. The future completes with null when the assistant text is non-empty.
     * {@link org.aetheris.ssh.AmwpFacade} stores that future. A cancel frame does not cancel it.
     *
     * @param model The model is the model id. An empty id fails before the key is read.
     * @param effort The effort is the reasoning level. An empty level is sent as {@code high} when the field is sent.
     * @param sendEffort The flag is false when {@code reasoning_effort} stays off the Hello body.
     * @return The return value is the future that completes when the greeting text was readable.
     * @throws NoModelException The method throws this exception when the model is empty.
     * @throws KeyMissingException The method throws this exception when the key is empty.
     * @throws Exception The method throws this exception when the body cannot be built.
     */
    public CompletableFuture<Void> startProbe(String model, String effort, boolean sendEffort) throws Exception {
        String modelId = model == null ? "" : model.trim();
        if (modelId.length() == 0) {
            throw new NoModelException();
        }
        final String key = readKey();
        if (key.length() == 0) {
            throw new KeyMissingException();
        }
        final CompletableFuture<Void> done = new CompletableFuture<Void>();
        dispatchProbe(modelId, effort, sendEffort, key, done, true);
        return done;
    }

    /**
     * The {@code dispatchProbe} method sends one Hello and completes the shared future from the callback.
     * A status {@code 400} whose message names the reasoning field retries once with the field left off the body.
     *
     * @param modelId The model id is the model to send.
     * @param effort The effort is the reasoning level for this attempt.
     * @param sendEffort The flag is true when this attempt sends {@code reasoning_effort}.
     * @param key The key is the secret. It is not logged.
     * @param done The future is shared with the caller of {@link #startProbe}.
     * @param allowRetry The flag is false on the second attempt.
     * @throws Exception The method throws this exception when the body cannot be built.
     */
    private void dispatchProbe(final String modelId, final String effort, final boolean sendEffort,
            final String key, final CompletableFuture<Void> done, final boolean allowRetry) throws Exception {
        String body = probeBody(modelId, effort, sendEffort);
        HttpRequest request = request(key, body);
        final CompletableFuture<HttpResponse<String>> http = client().sendAsync(request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        /**
         * The callback finishes the shared probe future from the HTTP result.
         * A status {@code 400} that names the reasoning field retries once with that field left off the body.
         */
        http.whenComplete(new BiConsumer<HttpResponse<String>, Throwable>() {
            // @aa-sdd PK-1065 docs/specs/common/formal-plan.spec.md
            /**
             * The {@code accept} method finishes the probe future from one HTTP result.
             * If the future is already done, the method returns. A non-null error completes the future with {@link #transportOf}.
             * A status outside 2xx builds a redacted message. If this attempt may retry, the field was sent, the status is {@code 400}, and {@link #rejectedEffort} is true, the method calls {@link #dispatchProbe} once with the field off. A failure from that second call completes the future exceptionally. Any other status outside 2xx completes the future with {@link StatusException}.
             * A 2xx body with empty assistant content completes the future with an exception whose message is {@code grok empty content}. A 2xx body with text completes the future with null. A read failure completes the future with that exception.
             *
             * @param response The response is the HTTP result. It is unused when {@code err} is non-null.
             * @param err The throwable is the send failure, or null when a response arrived.
             */
            @Override
            public void accept(HttpResponse<String> response, Throwable err) {
                if (done.isDone()) {
                    return;
                }
                if (err != null) {
                    done.completeExceptionally(transportOf(err, key));
                    return;
                }
                if (response.statusCode() < 200 || response.statusCode() >= 300) {
                    String message = publicFailure(response.statusCode(), response.body(), key);
                    if (allowRetry && sendEffort && response.statusCode() == 400 && rejectedEffort(message)) {
                        try {
                            dispatchProbe(modelId, effort, false, key, done, false);
                        } catch (Exception ex) {
                            done.completeExceptionally(ex);
                        }
                        return;
                    }
                    done.completeExceptionally(new StatusException(response.statusCode(), message));
                    return;
                }
                try {
                    String content = assistantContent(response.body());
                    if (content.length() == 0) {
                        done.completeExceptionally(new Exception("grok empty content"));
                        return;
                    }
                    done.complete(null);
                } catch (Exception ex) {
                    done.completeExceptionally(ex);
                }
            }
        });
    }

    /**
     * The {@code rejectedEffort} method reports whether a status line refuses the reasoning field.
     * A null line returns false. The line must contain {@code does not support parameter}. It must also contain {@code reasoningeffort} or {@code reasoning_effort}. The match ignores case.
     *
     * @param text The text is the status line.
     * @return The return value is true when the line refuses the reasoning field.
     */
    public static boolean rejectedEffort(String text) {
        if (text == null) {
            return false;
        }
        String lower = text.toLowerCase();
        if (lower.indexOf("does not support parameter") < 0) {
            return false;
        }
        return lower.indexOf("reasoningeffort") >= 0 || lower.indexOf("reasoning_effort") >= 0;
    }

    /**
     * The {@code completeFor} method sends the prompt with factory call options and no slot and no sink.
     * An empty model fails before the key is read. An exception message from the call has no key text.
     *
     * @param model The model is the model id after {@code /}.
     * @param prompt The prompt is the assembled request.
     * @return The return value is the completion.
     * @throws Exception The method throws this exception when the model is empty, the key is missing, or the call fails.
     */
    public ChatResponse completeFor(String model, Prompt prompt) throws Exception {
        return completeFor(model, prompt, new GrokCallOptions(), null, null);
    }

    /**
     * The {@code completeFor} method sends one prompt and returns the joined assistant text.
     * {@code stream} false reads {@code choices[0].message.content}. {@code stream} true reads server-sent lines with the same mapper and joins {@code delta.content}. The probe text is not this prompt.
     * A null options object uses a new {@link GrokCallOptions}. A null prompt throws an exception with the message {@code grok empty prompt}. An empty model throws {@link NoModelException} before the key is read. An empty key or an empty URL throws {@link KeyMissingException}.
     * {@link EmptyContent} retries the same request once. A second empty result throws an exception with the message {@code grok empty content}. If the slot future is already cancelled, the retry throws {@link CallStopped} instead. A stop is not retried. Text already delivered to the sink is not retried, because that path throws the empty-content message directly.
     *
     * @param model The model is the model id. An empty id fails before the key is read.
     * @param prompt The prompt is the assembled request. The probe text is not this prompt.
     * @param options The options carry sampling and the stream flag. A null value uses a new {@link GrokCallOptions}.
     * @param slot The slot receives the HTTP future before the wait. A null slot skips that store.
     * @param sink The sink receives kind {@code t} and kind {@code c}. A null sink drops the chunks. The joined content is still returned.
     * @return The return value is the completion whose text is the joined content.
     * @throws CallStopped The method throws this exception when the slot was cancelled or the sink stopped the read.
     * @throws NoModelException The method throws this exception when the model is empty.
     * @throws KeyMissingException The method throws this exception when the key or the URL is empty.
     * @throws StatusException The method throws this exception when the HTTP status is outside 2xx. The message is the redacted line.
     * @throws Exception The method throws this exception when the call fails. The message has no key text.
     */
    public ChatResponse completeFor(String model, Prompt prompt, GrokCallOptions options, HttpSlot slot,
            DeltaSink sink) throws Exception {
        String modelId = model == null ? "" : model.trim();
        if (modelId.length() == 0) {
            throw new NoModelException();
        }
        String key = readKey();
        String url = endpoint();
        if (key.length() == 0 || url.length() == 0) {
            throw new KeyMissingException();
        }
        if (prompt == null) {
            throw new Exception("grok empty prompt");
        }
        GrokCallOptions call = options == null ? new GrokCallOptions() : options;
        String body = requestBody(modelId, prompt, call);
        HttpRequest request = request(key, body);
        // A blank session is tried once more. A stop is not. Text already shown is not.
        // @aa-sdd PK-1078 docs/specs/common/formal-plan.spec.md
        int attempt = 0;
        while (true) {
            try {
                if (call.thinkStream) {
                    return completeStream(request, key, slot, sink);
                }
                return completeOnce(request, key, slot);
            } catch (EmptyContent empty) {
                attempt++;
                if (attempt >= 2) {
                    throw new Exception("grok empty content");
                }
                if (slot != null && slot.future != null && slot.future.isCancelled()) {
                    throw new CallStopped();
                }
            }
        }
    }

    /**
     * The {@code readKey} method returns the first key that is present.
     * The order is the yaml property, then the environment, then {@code ~/.aetheris/grok.key}, then {@code ~/.casserole/grok.key}.
     *
     * @return The return value is the key text, or an empty string when every source misses.
     */
    String readKey() {
        if (yamlKey.length() > 0) {
            return yamlKey;
        }
        String env = namedEnv();
        if (env.length() > 0) {
            return env;
        }
        String home = System.getProperty("user.home");
        String primary = namedFile(home, ".aetheris");
        if (primary.length() > 0) {
            return primary;
        }
        return namedFile(home, ".casserole");
    }

    /**
     * The {@code namedEnv} method reads {@code XAI_API_KEY}.
     * A null value returns an empty string. Any other value is trimmed.
     *
     * @return The return value is the trimmed environment value, or an empty string.
     */
    private static String namedEnv() {
        String value = System.getenv(ENV_NAME);
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    /**
     * The {@code namedFile} method reads the first non-empty, non-comment line of {@code grok.key}.
     * An empty home, an empty directory, a missing file, or a read error returns an empty string. A blank line and a line that starts with {@code #} are skipped. The text is not logged.
     *
     * @param home The home is the process home directory.
     * @param dir The directory is {@code .aetheris} or {@code .casserole}.
     * @return The return value is the file text, or an empty string.
     */
    private static String namedFile(String home, String dir) {
        if (home == null || home.length() == 0 || dir == null || dir.length() == 0) {
            return "";
        }
        File file = new File(new File(home, dir), KEY_FILE);
        if (!file.isFile()) {
            return "";
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.length() == 0 || trimmed.startsWith("#")) {
                    continue;
                }
                return trimmed;
            }
            return "";
        } catch (IOException ignored) {
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignoredClose) {
                }
            }
        }
    }

    /**
     * The {@code endpoint} method returns the chat completions URL.
     *
     * @return The return value is {@code https://api.x.ai/v1/chat/completions}.
     */
    static String endpoint() {
        return ENDPOINT;
    }

    /**
     * The {@code client} method builds an HTTP client for one call.
     * The connect timeout is 10 seconds.
     *
     * @return The return value is a new HTTP client.
     */
    private static HttpClient client() {
        return HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    }

    /**
     * The {@code request} method builds one POST for the chat completions URL.
     * The timeout is 1800 seconds. The content type is {@code application/json}. The authorization header is {@code Bearer} plus the key. The body is UTF-8.
     *
     * @param key The key is the secret placed on the authorization header.
     * @param body The body is the JSON request text.
     * @return The return value is the HTTP request.
     */
    private static HttpRequest request(String key, String body) {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint()))
                .timeout(Duration.ofSeconds(1800))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + key)
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
    }

    /**
     * The {@code probeBody} method builds the Hello JSON.
     * The body has one user message whose text is {@code Hello}. {@code stream} is false. {@code max_completion_tokens} is {@code 16}. Temperature and {@code top_p} are absent.
     * When {@code sendEffort} is true, {@code reasoning_effort} is the trimmed level, or {@code high} when that level is empty. When the flag is false, the field stays off the body. A write failure throws an exception with the message {@code grok content shape}.
     *
     * @param model The model is the model id.
     * @param effort The effort is the reasoning level. An empty level becomes {@code high} when the field is sent.
     * @param sendEffort The flag is false when the field stays off the body.
     * @return The return value is the JSON body.
     * @throws Exception The method throws this exception when the body cannot be written.
     */
    private static String probeBody(String model, String effort, boolean sendEffort) throws Exception {
        ObjectNode root = MAPPER.createObjectNode();
        root.put("model", model);
        ArrayNode wire = root.putArray("messages");
        ObjectNode one = wire.addObject();
        one.put("role", "user");
        one.put("content", HELLO);
        if (sendEffort) {
            String level = effort == null ? "" : effort.trim();
            if (level.length() == 0) {
                level = "high";
            }
            root.put("reasoning_effort", level);
        }
        root.put("stream", false);
        root.put("max_completion_tokens", 16);
        try {
            return MAPPER.writeValueAsString(root);
        } catch (Exception ex) {
            throw new Exception("grok content shape");
        }
    }

    /**
     * The {@code requestBody} method builds the completion JSON from the prompt.
     * {@code top_p} is absent. Tools are absent. Deferred is absent. Search is absent. {@code num_ctx} is absent. Temperature and {@code max_completion_tokens} are copied from the options. {@code stream} is {@link GrokCallOptions#thinkStream}.
     * A null message list throws an exception with the message {@code grok empty prompt}. A null message, a null message type, or a role other than {@code system}, {@code user}, or {@code assistant} throws an exception with the message {@code unknown role}. A null message text is stored as an empty string.
     * When {@link GrokCallOptions#sendReasoningEffort} is true, an empty effort is sent as {@code high}. When the flag is false, {@code reasoning_effort} stays off the body. A write failure throws an exception with the message {@code grok content shape}.
     *
     * @param model The model is the model id.
     * @param prompt The prompt is the assembled request.
     * @param options The options carry sampling and the stream flag.
     * @return The return value is the JSON body.
     * @throws Exception The method throws this exception for an unknown role or an empty prompt.
     */
    private static String requestBody(String model, Prompt prompt, GrokCallOptions options) throws Exception {
        List<Message> messages = prompt.getInstructions();
        if (messages == null) {
            throw new Exception("grok empty prompt");
        }
        ObjectNode root = MAPPER.createObjectNode();
        root.put("model", model);
        ArrayNode wire = root.putArray("messages");
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message == null || message.getMessageType() == null) {
                throw new Exception("unknown role");
            }
            String role = message.getMessageType().getValue();
            if (!"system".equals(role) && !"user".equals(role) && !"assistant".equals(role)) {
                throw new Exception("unknown role");
            }
            String text = message.getText();
            if (text == null) {
                text = "";
            }
            ObjectNode one = wire.addObject();
            one.put("role", role);
            one.put("content", text);
        }
        root.put("temperature", options.temperature);
        root.put("max_completion_tokens", options.maxCompletionTokens);
        if (options.sendReasoningEffort) {
            String effort = options.reasoningEffort == null ? "" : options.reasoningEffort.trim();
            if (effort.length() == 0) {
                effort = "high";
            }
            root.put("reasoning_effort", effort);
        }
        root.put("stream", options.thinkStream);
        try {
            return MAPPER.writeValueAsString(root);
        } catch (Exception ex) {
            throw new Exception("grok content shape");
        }
    }

    /**
     * The {@code completeOnce} method sends the request and reads one JSON body.
     * When the slot is present, the HTTP future is stored on it before the wait. A status outside 2xx throws {@link StatusException} with the redacted line. Empty assistant content throws {@link EmptyContent}. Any other body returns {@link #chat}.
     *
     * @param request The request is the POST to send.
     * @param key The key is the secret used only to redact a failure.
     * @param slot The slot receives the HTTP future. A null slot skips that store.
     * @return The return value is the completion.
     * @throws Exception The method throws this exception when the status is outside 2xx, the content is empty, or the read fails.
     */
    private ChatResponse completeOnce(HttpRequest request, String key, HttpSlot slot) throws Exception {
        CompletableFuture<HttpResponse<String>> http = client().sendAsync(request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (slot != null) {
            slot.future = http;
        }
        HttpResponse<String> response = waitString(http, key);
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new StatusException(response.statusCode(),
                    publicFailure(response.statusCode(), response.body(), key));
        }
        String content = assistantContent(response.body());
        if (content.length() == 0) {
            throw new EmptyContent();
        }
        return chat(content);
    }

    /**
     * The {@code completeStream} method sends the request and reads server-sent lines.
     * When the slot is present, the HTTP future is stored on it before the wait. Cancel, interrupt, or a cancelled execution throws {@link CallStopped}. An interrupt also sets the thread interrupt flag. Any other execution failure is passed to {@link #transportOf}.
     * A status outside 2xx throws {@link StatusException}. The error body is at most one 256-byte read. Empty joined content throws {@code grok empty content} when a chunk was delivered to the sink. Empty joined content throws {@link EmptyContent} when no chunk was delivered.
     *
     * @param request The request is the POST to send.
     * @param key The key is the secret used only to redact a failure.
     * @param slot The slot receives the HTTP future. A null slot skips that store.
     * @param sink The sink receives kind {@code t} and kind {@code c}. A null sink drops the chunks.
     * @return The return value is the completion whose text is the joined content.
     * @throws Exception The method throws this exception when the call is stopped, the status is outside 2xx, or the read fails.
     */
    private ChatResponse completeStream(HttpRequest request, final String key, HttpSlot slot, final DeltaSink sink)
            throws Exception {
        final CompletableFuture<HttpResponse<InputStream>> http = client().sendAsync(request,
                HttpResponse.BodyHandlers.ofInputStream());
        if (slot != null) {
            slot.future = http;
        }
        HttpResponse<InputStream> response;
        try {
            response = http.get();
        } catch (CancellationException ex) {
            throw new CallStopped();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new CallStopped();
        } catch (ExecutionException ex) {
            if (http.isCancelled()) {
                throw new CallStopped();
            }
            throw transportOf(ex, key);
        }
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            String raw = readLimited(response.body());
            throw new StatusException(response.statusCode(), publicFailure(response.statusCode(), raw, key));
        }
        boolean[] seen = new boolean[1];
        String content = readSse(response.body(), key, http, sink, seen);
        if (content.length() == 0) {
            if (seen[0]) {
                throw new Exception("grok empty content");
            }
            throw new EmptyContent();
        }
        return chat(content);
    }

    /**
     * The {@code readSse} method reads server-sent lines until {@code data: [DONE]}.
     * A line is {@code data:} plus one JSON object. A blank line and a line that starts with {@code :} are skipped. A line that does not start with {@code data:} is skipped. An empty payload is skipped.
     * {@code delta.reasoning_content} is kind {@code t}. {@code delta.content} is kind {@code c}. A missing choices array, an empty choices array, a missing delta, or a non-object delta is skipped. Unreadable JSON throws an exception with the message {@code grok content shape}.
     * A cancelled HTTP future throws {@link CallStopped}. An I/O failure on a cancelled future also throws {@link CallStopped}. Any other I/O failure throws an exception whose message is the redacted transport line.
     *
     * @param in The stream is the response body.
     * @param key The key is the secret to redact.
     * @param http The future is the HTTP call. A cancelled read becomes {@link CallStopped}.
     * @param sink The sink receives each chunk. A null sink drops the text. Content is still joined.
     * @param seen The flag array records whether a chunk was delivered to the sink.
     * @return The return value is the joined content.
     * @throws Exception The method throws this exception for a bad shape, a transport failure, or a stopped call.
     */
    private static String readSse(InputStream in, String key, CompletableFuture<?> http, DeltaSink sink,
            boolean[] seen) throws Exception {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(in, StandardCharsets.UTF_8));
        StringBuilder joined = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (http != null && http.isCancelled()) {
                    throw new CallStopped();
                }
                String trimmed = line.trim();
                if (trimmed.length() == 0 || trimmed.startsWith(":")) {
                    continue;
                }
                if (!trimmed.startsWith("data:")) {
                    continue;
                }
                String payload = trimmed.substring("data:".length()).trim();
                if ("[DONE]".equals(payload)) {
                    break;
                }
                if (payload.length() == 0) {
                    continue;
                }
                JsonNode root;
                try {
                    root = MAPPER.readTree(payload);
                } catch (Exception ex) {
                    throw new Exception("grok content shape");
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
                emitDelta(sink, joined, delta.get("reasoning_content"), 't', seen);
                emitDelta(sink, joined, delta.get("content"), 'c', seen);
            }
        } catch (CallStopped ex) {
            throw ex;
        } catch (IOException ex) {
            if (http != null && http.isCancelled()) {
                throw new CallStopped();
            }
            throw new Exception(transportFailure(ex, key));
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
            }
        }
        return joined.toString();
    }

    /**
     * The {@code emitDelta} method handles one delta field.
     * A null node or a JSON null returns. A node that is not text throws an exception with the message {@code grok content shape}. An empty string returns. Kind {@code c} is appended to the joined reply. Kind {@code t} is not appended.
     * When the sink is present, the seen flag becomes true and the sink receives the kind and the text.
     *
     * @param sink The sink receives the chunk. A null sink drops the text.
     * @param joined The builder holds the joined reply text.
     * @param node The node is one delta field.
     * @param kind The kind is {@code t} or {@code c}.
     * @param seen The flag array records whether a chunk was delivered. A null array skips that store.
     * @throws Exception The method throws this exception when the node is not text or the sink stops the read.
     */
    private static void emitDelta(DeltaSink sink, StringBuilder joined, JsonNode node, char kind, boolean[] seen)
            throws Exception {
        if (node == null || node.isNull()) {
            return;
        }
        if (!node.isTextual()) {
            throw new Exception("grok content shape");
        }
        String text = node.asText();
        if (text.length() == 0) {
            return;
        }
        if (kind == 'c') {
            joined.append(text);
        }
        if (sink != null) {
            if (seen != null) {
                seen[0] = true;
            }
            sink.delta(kind, text);
        }
    }

    /**
     * The {@code waitString} method waits for the string response.
     * Cancel throws {@link CallStopped}. Interrupt sets the thread interrupt flag and throws {@link CallStopped}. A cancelled execution throws {@link CallStopped}. Any other execution failure is passed to {@link #transportOf}.
     *
     * @param http The future is the HTTP call.
     * @param key The key is the secret used only to redact a failure.
     * @return The return value is the HTTP response.
     * @throws Exception The method throws this exception when the wait is stopped or the send fails.
     */
    private static HttpResponse<String> waitString(CompletableFuture<HttpResponse<String>> http, String key)
            throws Exception {
        try {
            return http.get();
        } catch (CancellationException ex) {
            throw new CallStopped();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new CallStopped();
        } catch (ExecutionException ex) {
            if (http.isCancelled()) {
                throw new CallStopped();
            }
            throw transportOf(ex, key);
        }
    }

    /**
     * The {@code transportOf} method turns a send failure into an exception whose message has no key text.
     * One {@code ExecutionException} cause and one {@code CompletionException} cause are unwrapped when present. {@link CallStopped}, {@link StatusException}, {@link NoModelException}, and {@link KeyMissingException} are returned as themselves.
     * An exception whose message starts with {@code grok } or {@code Grok }, or whose message is {@code unknown role}, is returned as itself. Any other exception is wrapped with {@link #transportFailure}.
     *
     * @param err The throwable is the send failure.
     * @param key The key is the secret to redact.
     * @return The return value is the exception to complete or throw.
     */
    private static Exception transportOf(Throwable err, String key) {
        Throwable cause = err;
        if (cause instanceof ExecutionException && cause.getCause() != null) {
            cause = cause.getCause();
        }
        if (cause instanceof java.util.concurrent.CompletionException && cause.getCause() != null) {
            cause = cause.getCause();
        }
        if (cause instanceof CallStopped) {
            return (CallStopped) cause;
        }
        if (cause instanceof Exception) {
            Exception ex = (Exception) cause;
            if (ex instanceof StatusException || ex instanceof NoModelException || ex instanceof KeyMissingException) {
                return ex;
            }
            if (ex.getMessage() != null && (ex.getMessage().startsWith("grok ")
                    || ex.getMessage().startsWith("Grok ")
                    || "unknown role".equals(ex.getMessage()))) {
                return ex;
            }
            return new Exception(transportFailure(ex, key));
        }
        return new Exception(transportFailure(new Exception(String.valueOf(cause)), key));
    }

    /**
     * The {@code readLimited} method reads at most 256 bytes from an error body.
     * A null stream returns an empty string. A read error returns an empty string. The stream is closed. The bytes are decoded as UTF-8.
     *
     * @param in The stream is the error body.
     * @return The return value is the short body text, or an empty string.
     */
    private static String readLimited(InputStream in) {
        if (in == null) {
            return "";
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[256];
        try {
            int n = in.read(buf);
            if (n > 0) {
                out.write(buf, 0, n);
            }
        } catch (IOException ignored) {
            return "";
        } finally {
            try {
                in.close();
            } catch (IOException ignoredClose) {
            }
        }
        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }

    /**
     * The {@code chat} method wraps assistant text in one chat response.
     * The response has one generation. The generation message is that text.
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
     * The {@code assistantContent} method reads {@code choices[0].message.content}.
     * A null body or a blank body throws an exception with the message {@code grok response has no content}. A body that is not one JSON object throws an exception with the message {@code grok content shape}. A missing choices array, an empty choices array, a missing message, or a message that is not an object uses the same no-content message.
     * A null content node or a JSON null returns an empty string. A content node that is not text throws {@code grok content shape}. A {@code content} key anywhere else is left unread. This reader does not read {@code reasoning_content}. A server-sent body is not one JSON object, so it fails as {@code grok content shape}.
     *
     * @param body The body is the response text.
     * @return The return value is the assistant content. An empty string is a failed completion for the caller.
     * @throws Exception The method throws this exception when the content is missing or unreadable. The message has no key text.
     */
    private static String assistantContent(String body) throws Exception {
        if (body == null || body.trim().length() == 0) {
            throw new Exception("grok response has no content");
        }
        JsonNode root;
        try {
            root = MAPPER.readTree(body);
        } catch (Exception ex) {
            throw new Exception("grok content shape");
        }
        JsonNode choices = root.get("choices");
        if (choices == null || !choices.isArray() || choices.size() == 0) {
            throw new Exception("grok response has no content");
        }
        JsonNode first = choices.get(0);
        if (first == null || !first.isObject()) {
            throw new Exception("grok response has no content");
        }
        JsonNode message = first.get("message");
        if (message == null || !message.isObject()) {
            throw new Exception("grok response has no content");
        }
        JsonNode content = message.get("content");
        if (content == null || content.isNull()) {
            return "";
        }
        if (!content.isTextual()) {
            throw new Exception("grok content shape");
        }
        return content.asText();
    }

    /**
     * The {@code publicFailure} method builds a status line and a short body.
     * Key text is replaced before the message is returned. The prefix is {@code Grok HTTP}, the status, and a space.
     *
     * @param status The status is the HTTP status code.
     * @param body The body is the response text.
     * @param key The key is the secret to redact.
     * @return The return value is the exception message with no key text.
     */
    private static String publicFailure(int status, String body, String key) {
        return redact(body, key, "Grok HTTP " + status + " ");
    }

    /**
     * The {@code transportFailure} method builds a transport line with no HTTP status.
     * The line starts with the exception class name, or {@code error} when the exception is null. The key text is still replaced. The prefix is {@code Grok }.
     *
     * @param ex The exception is the send failure.
     * @param key The key is the secret to redact.
     * @return The return value is the exception message with no key text.
     */
    private static String transportFailure(Exception ex, String key) {
        String name = ex == null ? "error" : ex.getClass().getSimpleName();
        String detail = ex == null || ex.getMessage() == null ? "" : ex.getMessage();
        return redact(name + " " + detail, key, "Grok ");
    }

    /**
     * The {@code redact} method hides the key and shortens the shown text.
     * A null body is shown as empty. Each copy of the key becomes {@code [redacted]}. Carriage return and line feed become spaces. Text longer than 180 characters is cut to 180 characters. The prefix is placed in front of that text.
     *
     * @param body The body is the raw text.
     * @param key The key is the secret to redact.
     * @param prefix The prefix is the words in front of the body.
     * @return The return value is the prefix plus at most 180 characters, with key text replaced.
     */
    private static String redact(String body, String key, String prefix) {
        String shown = body == null ? "" : body;
        if (key != null && key.length() > 0 && shown.indexOf(key) >= 0) {
            shown = shown.replace(key, "[redacted]");
        }
        shown = shown.replace('\r', ' ').replace('\n', ' ');
        if (shown.length() > 180) {
            shown = shown.substring(0, 180);
        }
        return prefix + shown;
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code HttpSlot} class holds the HTTP future for one completion.
     * {@link org.aetheris.ssh.AmwpFacade} cancels {@link #future} when it handles {@link org.aetheris.wire.AmwpConstants#TYPE_CANCEL}. The probe future is not stored here.
     */
    public static final class HttpSlot {
        public volatile CompletableFuture<?> future;
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code DeltaSink} interface receives one server-sent chunk.
     * Kind {@code t} is thinking text. Kind {@code c} is reply text.
     */
    public interface DeltaSink {
        /**
         * The {@code delta} method accepts one chunk.
         * This type does not store the text.
         *
         * @param kind The kind is {@code t} or {@code c}.
         * @param text The text is the chunk. This type does not store it.
         * @throws Exception The method throws this exception when the caller stops the read.
         */
        void delta(char kind, String text) throws Exception;
    }

    // @aa-sdd PK-1078 docs/specs/common/formal-plan.spec.md
    /**
     * The {@code EmptyContent} class means the completion returned no text and the sink was not shown a chunk.
     * {@link #completeFor(String, Prompt, GrokCallOptions, HttpSlot, DeltaSink)} tries the same request once more, then throws an exception with the message {@code grok empty content}.
     */
    // @aa-sdd PK-882 docs/specs/common/stages.spec.md
    // @aa-sdd PK-1355 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-1078 docs/specs/common/formal-plan.spec.md
    private static final class EmptyContent extends Exception {

        private static final long serialVersionUID = 1L;

        /**
         * The constructor stores the message {@code grok empty content}.
         */
        private EmptyContent() {
            super("grok empty content");
        }
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code CallStopped} class means the HTTP call was cancelled, or the caller stopped the read.
     * The reply is not stored. The exception message is empty.
     */
    // @aa-sdd PK-684 docs/specs/common/grok.spec.md
    // @aa-sdd PK-1078 docs/specs/common/formal-plan.spec.md
    // @aa-sdd PK-384 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-389 docs/specs/java/org/casserole/llm/llm.spec.md
    public static final class CallStopped extends Exception {

        private static final long serialVersionUID = 1L;

        /**
         * The constructor stores an empty message.
         * One catch in {@link org.aetheris.ssh.AmwpFacade} returns without a reply. Other catches throw this exception again.
         */
        public CallStopped() {
            super("");
        }
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code NoModelException} class means the model id is empty.
     * The key was not read.
     */
    public static final class NoModelException extends Exception {

        private static final long serialVersionUID = 1L;

        /**
         * The constructor stores the message {@code no model defined}.
         * When {@link org.aetheris.ssh.AmwpFacade} is serving a framed turn, it replies with reason {@code no-model} and that text. A console turn throws an exception with that text.
         */
        public NoModelException() {
            super("no model defined");
        }
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code StatusException} class means the HTTP status is outside 2xx.
     * {@link #status()} is the code. The message is the redacted public line.
     */
    public static final class StatusException extends Exception {

        private static final long serialVersionUID = 1L;

        private final int status;

        /**
         * The constructor stores the status code and the redacted line.
         *
         * @param status The status is the HTTP status code.
         * @param message The message is the redacted line.
         */
        public StatusException(int status, String message) {
            super(message);
            this.status = status;
        }

        /**
         * The {@code status} method returns the HTTP status code.
         *
         * @return The return value is the HTTP status code.
         */
        public int status() {
            return status;
        }
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code KeyMissingException} class means the key is missing.
     * The message does not contain key text. When {@link org.aetheris.ssh.AmwpFacade} is serving a framed turn, it replies with reason {@code failed} and the text {@code grok key missing}.
     */
    // @aa-sdd PK-365 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-788 docs/specs/common/grok.spec.md
    // @aa-sdd PK-371 docs/specs/java/org/casserole/llm/llm.spec.md
    public static final class KeyMissingException extends Exception {

        private static final long serialVersionUID = 1L;

        /**
         * The constructor stores the message {@code grok key missing}.
         * The message does not contain key text.
         */
        public KeyMissingException() {
            super("grok key missing");
        }
    }

    /**
     * The {@code catalogBody} method builds a {@code grok_models_result:} body from the live catalog.
     * {@link #listLanguageModels} supplies the ids. Each id becomes one {@code model:} line.
     * An empty list returns the header only. The key is not written on the body.
     *
     * @return The return value is the catalog reply.
     */
    public String catalogBody() {
        return catalogText(listLanguageModels());
    }

    /**
     * The {@code catalogText} method builds a {@code grok_models_result:} body from ids that are already parsed.
     * A null list returns the header only. A null id is skipped. Each remaining id becomes one {@code model:} line.
     *
     * @param ids The ids are the catalog, in reply order.
     * @return The return value is the catalog reply.
     */
    static String catalogText(List<String> ids) {
        StringBuilder sb = new StringBuilder();
        sb.append("grok_models_result:\n");
        if (ids == null) {
            return sb.toString();
        }
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            if (id == null) {
                continue;
            }
            sb.append("model: ").append(id).append('\n');
        }
        return sb.toString();
    }

    /**
     * The {@code listLanguageModels} method GETs the language-model catalog.
     * A missing key returns an empty list and does not call the network. The connect timeout is 10 seconds. The request timeout is 10 seconds.
     * The client uses HTTP/1.1 and follows no redirect. The authorization header is {@code Bearer} plus the key.
     * A status outside 200 through 299 returns an empty list. A parse miss returns an empty list. Any exception returns an empty list.
     * The key is not logged. The body is not logged.
     *
     * @return The return value is the model ids, or an empty list.
     */
    List<String> listLanguageModels() {
        try {
            String key = readKey();
            if (key.length() == 0) {
                return new ArrayList<String>();
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(LANGUAGE_MODELS))
                    .timeout(Duration.ofSeconds(10))
                    .header("Authorization", "Bearer " + key)
                    .GET()
                    .build();
            HttpClient http = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .followRedirects(HttpClient.Redirect.NEVER)
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                return new ArrayList<String>();
            }
            return languageModelIds(response.body());
        } catch (Exception ignored) {
            return new ArrayList<String>();
        }
    }

    // @aa-sdd PK-1275 docs/specs/java/org/casserole/llm/llm.spec.md
    /**
     * The {@code languageModelIds} method reads model ids from a catalog body.
     * A null body returns an empty list. A body that is not JSON returns an empty list.
     * A top-level array is the list. An object uses {@code models} when that field is an array, and otherwise {@code data} when that field is an array.
     * A textual element is one id. An object uses a textual {@code id}, then each textual alias, in that order. An object with no textual {@code id} uses a textual {@code name}.
     * Each id is trimmed. An empty id is skipped. An id that contains a newline or a carriage return is skipped. A repeated id is skipped. The order is the order in the body.
     *
     * @param body The body is the JSON text. The text is not logged.
     * @return The return value is the ids, or an empty list.
     */
    static List<String> languageModelIds(String body) {
        ArrayList<String> ids = new ArrayList<String>();
        if (body == null || body.trim().length() == 0) {
            return ids;
        }
        try {
            JsonNode root = MAPPER.readTree(body);
            JsonNode values = catalogValues(root);
            if (values == null) {
                return ids;
            }
            for (int i = 0; i < values.size(); i++) {
                addCatalogElement(ids, values.get(i));
            }
        } catch (Exception ignored) {
            return new ArrayList<String>();
        }
        return ids;
    }

    /**
     * The {@code catalogValues} method chooses the array that holds the models.
     * A top-level array is that array. An object returns {@code models} when it is an array. Otherwise the object returns {@code data} when it is an array. Any other shape returns null.
     *
     * @param root The root is the parsed JSON.
     * @return The return value is the model array, or null.
     */
    private static JsonNode catalogValues(JsonNode root) {
        if (root == null) {
            return null;
        }
        if (root.isArray()) {
            return root;
        }
        if (!root.isObject()) {
            return null;
        }
        JsonNode models = root.get("models");
        if (models != null && models.isArray()) {
            return models;
        }
        JsonNode data = root.get("data");
        if (data != null && data.isArray()) {
            return data;
        }
        return null;
    }

    /**
     * The {@code addCatalogElement} method appends one element.
     * A null element is skipped. A textual element is one id. An object with a textual {@code id} appends that id and then each textual alias. An object with no textual {@code id} appends a textual {@code name} when one is present.
     *
     * @param ids The ids are the list being filled.
     * @param one The element is one catalog value.
     */
    private static void addCatalogElement(List<String> ids, JsonNode one) {
        if (one == null) {
            return;
        }
        if (one.isTextual()) {
            addModelId(ids, one.asText());
            return;
        }
        if (!one.isObject()) {
            return;
        }
        JsonNode id = one.get("id");
        if (id != null && id.isTextual()) {
            addModelId(ids, id.asText());
            JsonNode aliases = one.get("aliases");
            if (aliases != null && aliases.isArray()) {
                for (int a = 0; a < aliases.size(); a++) {
                    JsonNode alias = aliases.get(a);
                    if (alias != null && alias.isTextual()) {
                        addModelId(ids, alias.asText());
                    }
                }
            }
            return;
        }
        JsonNode name = one.get("name");
        if (name != null && name.isTextual()) {
            addModelId(ids, name.asText());
        }
    }

    /**
     * The {@code addModelId} method appends one id when it is safe to put on a wire line.
     * A null id is skipped. The id is trimmed. An empty id is skipped. An id that contains a newline or a carriage return is skipped. An id already in the list is skipped.
     *
     * @param ids The ids are the list being filled.
     * @param raw The raw value is the text from the catalog.
     */
    private static void addModelId(List<String> ids, String raw) {
        if (raw == null) {
            return;
        }
        String id = raw.trim();
        if (id.length() == 0 || id.indexOf('\n') >= 0 || id.indexOf('\r') >= 0) {
            return;
        }
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).equals(id)) {
                return;
            }
        }
        ids.add(id);
    }
}
/*****************************************************************************************
 * End of Class: GrokLlmService.java 102026
 ****************************************************************************************/
