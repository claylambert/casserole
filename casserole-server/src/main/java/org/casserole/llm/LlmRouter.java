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

// @aa-sdd PK-0603 PK-0726 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0647 docs/specs/java/org/casserole/llm/llm.spec.md
/**
 * The {@code LlmRouter} class is the map from a provider id to a {@link LlmProvider}.
 * The caller supplies the prompt. The complete methods do not take a cell record. The router does not read stored chat memory. The router does not add a mood line.
 * // @aa-sdd PK-0561 PK-0681 PK-0685 PK-0728 PK-0729 PK-0731 docs/specs/java/org/casserole/llm/llm.spec.md
 *
 * @author clayl (2026-10-20)
 */
@Component
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

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
