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

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

// @aa-sdd PK-1072 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1065 docs/specs/common/formal-plan.spec.md
/**
 * The {@code LlmRouter} class is the map from a provider id to a {@link LlmProvider}.
 * The caller supplies the prompt. The complete methods do not take a cell record. The router does not read stored chat memory. The router does not add a mood line.
 * // @aa-sdd G-05 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-10-20)
 */
@Component
// @aa-sdd PK-1299 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-363 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-364 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-686 docs/specs/common/grok.spec.md
// @aa-sdd PK-737 docs/specs/common/stages.spec.md
// @aa-sdd PK-375 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-849 docs/specs/common/stages.spec.md
// @aa-sdd PK-378 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-759 docs/specs/common/stages.spec.md
// @aa-sdd PK-1335 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-380 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-381 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-896 docs/specs/common/stages.spec.md
// @aa-sdd PK-383 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-697 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-391 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-397 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-1376 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-398 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-729 docs/specs/common/server.spec.md
// @aa-sdd PK-656 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-658 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-386 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-387 docs/specs/java/org/casserole/llm/llm.spec.md
public class LlmRouter {

    private final HashMap<String, LlmProvider> providers;
    private final SpringAiOllamaLlmService ollama;
    private final GrokLlmService grok;

    /**
     * The constructor stores the Ollama provider and the Grok provider in the map.
     * The map key is each provider's id.
     *
     * @param ollama The Ollama argument is the local provider.
     * @param grok The Grok argument is the cloud provider.
     */
    public LlmRouter(SpringAiOllamaLlmService ollama, GrokLlmService grok) {
        this.providers = new HashMap<String, LlmProvider>();
        this.providers.put(ollama.getId(), ollama);
        this.providers.put(grok.getId(), grok);
        this.ollama = ollama;
        this.grok = grok;
    }

    /**
     * The method completes a prompt with factory Grok options and no thinking sink.
     * The method does not take a cell record. An empty target selects {@code ollama}.
     *
     * @param target The target is a provider id, or {@code provider/model}.
     * @param prompt The prompt is the caller-built prompt.
     * @return The return value is the completion.
     * @throws Exception The method throws this exception for an unknown provider or a backend failure.
     */
    public ChatResponse complete(String target, Prompt prompt) throws Exception {
        return complete(target, prompt, new GrokCallOptions(), "", null, null);
    }

    /**
     * The method sends the prompt to Grok, to Ollama, or to another mapped provider.
     * The id {@code grok} calls {@link GrokLlmService#completeFor}. The id {@code ollama} calls {@link SpringAiOllamaLlmService#complete(Prompt, String, String, GrokLlmService.DeltaSink)}. Any other id uses {@link LlmProvider#complete(Prompt)}. A missing provider throws {@link UnknownProviderException}.
     *
     * Grok uses {@code options}, the slot, and the sink. Ollama uses the model text after the slash, the think string, and the sink. A null think string is sent as empty. A set think string streams kind {@code t}. A null sink keeps the one-shot call. This method does not take a cell record.
     *
     * @param target The target is a provider id, or {@code provider/model}.
     * @param prompt The prompt is the caller-built prompt.
     * @param options The options are the Grok body fields. Other providers ignore them.
     * @param think The think text is empty when thinking is off, {@code true} for the checkbox, or an Ollama level.
     * @param slot The slot receives the chat HTTP future. The probe future is not this slot.
     * @param sink The sink receives thinking chunks. The sink is null when thinking is off.
     * @return The return value is the completion.
     * @throws Exception The method throws this exception for an unknown provider or a backend failure.
     */
    public ChatResponse complete(String target, Prompt prompt, GrokCallOptions options, String think,
            GrokLlmService.HttpSlot slot, GrokLlmService.DeltaSink sink) throws Exception {
        String id = providerId(target);
        if ("grok".equals(id)) {
            return grok.completeFor(modelAfterSlash(target), prompt, options, slot, sink);
        }
        if ("ollama".equals(id)) {
            return ollama.complete(prompt, modelAfterSlash(target), think == null ? "" : think, sink);
        }
        LlmProvider provider = providers.get(id);
        if (provider == null) {
            throw new UnknownProviderException(id);
        }
        return provider.complete(prompt);
    }

    /**
     * The method returns the key the Grok service would send.
     * The method does not write the key.
     *
     * @return The return value is the key text, or an empty string when no key is available.
     */
    public String currentGrokKey() {
        return grok.currentKey();
    }

    /**
     * The method starts one Hello probe for this server process.
     * The returned future is the probe future. An empty effort becomes {@code high} inside the probe body when the field is sent. A false send flag leaves {@code reasoning_effort} off the Hello body.
     *
     * @param model The model is the model id.
     * @param effort The effort is this request's level.
     * @param sendEffort The flag is false when the Hello body should omit {@code reasoning_effort}.
     * @return The return value is the probe future.
     * @throws Exception The method throws this exception for an empty model, an empty key, or a body that could not be built.
     */
    public CompletableFuture<Void> startGrokProbe(String model, String effort, boolean sendEffort) throws Exception {
        return grok.startProbe(model, effort, sendEffort);
    }

    /**
     * The method returns the text after the first {@code /} in a target.
     * A null target returns empty. A target with no slash, or with nothing after the slash, returns empty. The returned text is trimmed.
     *
     * @param target The target is the stored target string.
     * @return The return value is the text after {@code /}, or an empty string.
     */
    public static String modelAfterSlash(String target) {
        if (target == null) {
            return "";
        }
        int slash = target.indexOf('/');
        if (slash < 0 || slash + 1 >= target.length()) {
            return "";
        }
        return target.substring(slash + 1).trim();
    }

    /**
     * The method returns the provider id in front of the first {@code /}.
     * A null target or a blank target returns {@code ollama}. The id is converted to lower case.
     *
     * @param target The target is a stored or outbound target string.
     * @return The return value is the token before {@code /}, in lower case.
     */
    public static String providerId(String target) {
        if (target == null) {
            return "ollama";
        }
        String trimmed = target.trim();
        if (trimmed.length() == 0) {
            return "ollama";
        }
        int slash = trimmed.indexOf('/');
        String id = slash < 0 ? trimmed : trimmed.substring(0, slash);
        return id.toLowerCase();
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code UnknownProviderException} class is the failure for a provider id that is not in the map.
     * The message is {@code unknown provider}. The facade turns this failure into {@code TYPE_ERROR} with reason {@code failed}.
     */
    public static final class UnknownProviderException extends Exception {

        private static final long serialVersionUID = 1L;

        /**
         * The constructor stores the message {@code unknown provider}.
         * The id is not written into the message.
         *
         * @param id The id is the provider token that was not in the map.
         */
        public UnknownProviderException(String id) {
            super("unknown provider");
        }
    }
}
/*****************************************************************************************
 * End of Class: LlmRouter.java 102026
 ****************************************************************************************/
