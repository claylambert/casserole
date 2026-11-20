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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

// @aa-sdd aetheris-plan-f-278.001.md
/**
 * The {@code LlamaCppCallOptions} class holds the chat fields a llama.cpp server accepts.
 * A null field stays off the JSON.
 * {@link #apply(ObjectNode)} writes each set field.
 * {@code model}, {@code messages}, and {@code stream} stay with {@link LlamaCppLlmService}.
 * {@code best_of} and {@code suffix} are not fields.
 * {@code num_ctx}, thread count, and slot count are not fields.
 *
 * @author clayl (2026-11-19)
 */
public final class LlamaCppCallOptions {

    /**
     * The field is the model id.
     * The service writes the id from the target.
     * This field is not copied by {@link #apply(ObjectNode)}.
     */
    public String model;

    /**
     * The field is the stream flag.
     * The service writes {@code stream} from the sink.
     * This field is not copied by {@link #apply(ObjectNode)}.
     */
    public Boolean stream;

    /**
     * The field is {@code messages} when a caller already built that array.
     * The service writes {@code messages} from the prompt.
     * This field is not copied by {@link #apply(ObjectNode)}.
     */
    public JsonNode messages;

    /** The field is {@code max_tokens}. A null value stays off the body. */
    public Integer maxTokens;

    /** The field is {@code n_predict}. A null value stays off the body. */
    public Integer nPredict;

    /** The field is {@code temperature}. A null value stays off the body. */
    public Double temperature;

    /** The field is {@code dynatemp_range}. A null value stays off the body. */
    public Double dynatempRange;

    /** The field is {@code dynatemp_exponent}. A null value stays off the body. */
    public Double dynatempExponent;

    /** The field is {@code top_k}. A null value stays off the body. */
    public Integer topK;

    /** The field is {@code top_p}. A null value stays off the body. */
    public Double topP;

    /** The field is {@code min_p}. A null value stays off the body. */
    public Double minP;

    /** The field is {@code typical_p}. A null value stays off the body. */
    public Double typicalP;

    /** The field is {@code seed}. A null value stays off the body. */
    public Long seed;

    /** The field is {@code ignore_eos}. A null value stays off the body. */
    public Boolean ignoreEos;

    /** The field is {@code logit_bias}. A null value stays off the body. */
    public JsonNode logitBias;

    /** The field is {@code samplers}. A null value stays off the body. */
    public JsonNode samplers;

    /** The field is {@code min_keep}. A null value stays off the body. */
    public Integer minKeep;

    /** The field is {@code n_indent}. A null value stays off the body. */
    public Integer nIndent;

    /** The field is {@code n_keep}. A null value stays off the body. */
    public Integer nKeep;

    /** The field is {@code n_cmpl}. A null value stays off the body. */
    public Integer nCmpl;

    /** The field is {@code t_max_predict_ms}. A null value stays off the body. */
    public Integer tMaxPredictMs;

    /** The field is {@code repeat_penalty}. A null value stays off the body. */
    public Double repeatPenalty;

    /** The field is {@code repeat_last_n}. A null value stays off the body. */
    public Integer repeatLastN;

    /** The field is {@code presence_penalty}. A null value stays off the body. */
    public Double presencePenalty;

    /** The field is {@code frequency_penalty}. A null value stays off the body. */
    public Double frequencyPenalty;

    /** The field is {@code dry_multiplier}. A null value stays off the body. */
    public Double dryMultiplier;

    /** The field is {@code dry_base}. A null value stays off the body. */
    public Double dryBase;

    /** The field is {@code dry_allowed_length}. A null value stays off the body. */
    public Integer dryAllowedLength;

    /** The field is {@code dry_penalty_last_n}. A null value stays off the body. */
    public Integer dryPenaltyLastN;

    /** The field is {@code dry_sequence_breakers}. A null value stays off the body. */
    public JsonNode drySequenceBreakers;

    /** The field is {@code xtc_probability}. A null value stays off the body. */
    public Double xtcProbability;

    /** The field is {@code xtc_threshold}. A null value stays off the body. */
    public Double xtcThreshold;

    /** The field is {@code mirostat}. A null value stays off the body. */
    public Integer mirostat;

    /** The field is {@code mirostat_tau}. A null value stays off the body. */
    public Double mirostatTau;

    /** The field is {@code mirostat_eta}. A null value stays off the body. */
    public Double mirostatEta;

    /** The field is {@code grammar}. A null value stays off the body. */
    public String grammar;

    /** The field is {@code json_schema}. A null value stays off the body. */
    public JsonNode jsonSchema;

    /** The field is {@code response_format}. A null value stays off the body. */
    public JsonNode responseFormat;

    /**
     * The field is {@code stop}.
     * One string or an array is written.
     * A null value stays off the body.
     * An empty array stays off the body.
     */
    public JsonNode stop;

    /** The field is {@code cache_prompt}. A null value stays off the body. */
    public Boolean cachePrompt;

    /** The field is {@code n_cache_reuse}. A null value stays off the body. */
    public Integer nCacheReuse;

    /** The field is {@code id_slot}. A null value stays off the body. */
    public Integer idSlot;

    /** The field is {@code return_tokens}. A null value stays off the body. */
    public Boolean returnTokens;

    /** The field is {@code timings_per_token}. A null value stays off the body. */
    public Boolean timingsPerToken;

    /** The field is {@code return_progress}. A null value stays off the body. */
    public Boolean returnProgress;

    /** The field is {@code sse_ping_interval}. A null value stays off the body. */
    public Integer ssePingInterval;

    /** The field is {@code post_sampling_probs}. A null value stays off the body. */
    public Boolean postSamplingProbs;

    /** The field is {@code response_fields}. A null value stays off the body. */
    public JsonNode responseFields;

    /** The field is {@code lora}. A null value stays off the body. */
    public JsonNode lora;

    /** The field is {@code n_probs}. A null value stays off the body. */
    public Integer nProbs;

    /** The field is {@code chat_template_kwargs}. A null value stays off the body. */
    public JsonNode chatTemplateKwargs;

    /** The field is {@code add_generation_prompt}. A null value stays off the body. */
    public Boolean addGenerationPrompt;

    /** The field is {@code continue_final_message}. A null value stays off the body. */
    public Boolean continueFinalMessage;

    /** The field is {@code generation_prompt}. A null value stays off the body. */
    public String generationPrompt;

    /** The field is {@code parse_tool_calls}. A null value stays off the body. */
    public Boolean parseToolCalls;

    /** The field is {@code tools}. A null value stays off the body. */
    public JsonNode tools;

    /** The field is {@code tool_choice}. A null value stays off the body. */
    public JsonNode toolChoice;

    /** The field is {@code parallel_tool_calls}. A null value stays off the body. */
    public Boolean parallelToolCalls;

    /** The field is {@code reasoning_format}. A null value stays off the body. */
    public String reasoningFormat;

    /** The field is {@code reasoning_control}. A null value stays off the body. */
    public String reasoningControl;

    /** The field is {@code thinking_budget_tokens}. A null value stays off the body. */
    public Integer thinkingBudgetTokens;

    /** The field is {@code logprobs}. A null value stays off the body. */
    public Boolean logprobs;

    /** The field is {@code top_logprobs}. A null value stays off the body. */
    public Integer topLogprobs;

    /**
     * The constructor leaves every field unset.
     * An unset field stays off the JSON.
     */
    public LlamaCppCallOptions() {
    }

    /**
     * The {@code apply} method writes each set field onto the object.
     * A null root returns.
     * {@code model}, {@code messages}, and {@code stream} are left to the service.
     * An empty {@code stop} array is left off.
     *
     * @param root The root is the chat object being built.
     */
    public void apply(ObjectNode root) {
        if (root == null) {
            return;
        }
        putInt(root, "max_tokens", maxTokens);
        putInt(root, "n_predict", nPredict);
        putDouble(root, "temperature", temperature);
        putDouble(root, "dynatemp_range", dynatempRange);
        putDouble(root, "dynatemp_exponent", dynatempExponent);
        putInt(root, "top_k", topK);
        putDouble(root, "top_p", topP);
        putDouble(root, "min_p", minP);
        putDouble(root, "typical_p", typicalP);
        putLong(root, "seed", seed);
        putBoolean(root, "ignore_eos", ignoreEos);
        putNode(root, "logit_bias", logitBias);
        putNode(root, "samplers", samplers);
        putInt(root, "min_keep", minKeep);
        putInt(root, "n_indent", nIndent);
        putInt(root, "n_keep", nKeep);
        putInt(root, "n_cmpl", nCmpl);
        putInt(root, "t_max_predict_ms", tMaxPredictMs);
        putDouble(root, "repeat_penalty", repeatPenalty);
        putInt(root, "repeat_last_n", repeatLastN);
        putDouble(root, "presence_penalty", presencePenalty);
        putDouble(root, "frequency_penalty", frequencyPenalty);
        putDouble(root, "dry_multiplier", dryMultiplier);
        putDouble(root, "dry_base", dryBase);
        putInt(root, "dry_allowed_length", dryAllowedLength);
        putInt(root, "dry_penalty_last_n", dryPenaltyLastN);
        putNode(root, "dry_sequence_breakers", drySequenceBreakers);
        putDouble(root, "xtc_probability", xtcProbability);
        putDouble(root, "xtc_threshold", xtcThreshold);
        putInt(root, "mirostat", mirostat);
        putDouble(root, "mirostat_tau", mirostatTau);
        putDouble(root, "mirostat_eta", mirostatEta);
        putText(root, "grammar", grammar);
        putNode(root, "json_schema", jsonSchema);
        putNode(root, "response_format", responseFormat);
        putStop(root, stop);
        putBoolean(root, "cache_prompt", cachePrompt);
        putInt(root, "n_cache_reuse", nCacheReuse);
        putInt(root, "id_slot", idSlot);
        putBoolean(root, "return_tokens", returnTokens);
        putBoolean(root, "timings_per_token", timingsPerToken);
        putBoolean(root, "return_progress", returnProgress);
        putInt(root, "sse_ping_interval", ssePingInterval);
        putBoolean(root, "post_sampling_probs", postSamplingProbs);
        putNode(root, "response_fields", responseFields);
        putNode(root, "lora", lora);
        putInt(root, "n_probs", nProbs);
        putNode(root, "chat_template_kwargs", chatTemplateKwargs);
        putBoolean(root, "add_generation_prompt", addGenerationPrompt);
        putBoolean(root, "continue_final_message", continueFinalMessage);
        putText(root, "generation_prompt", generationPrompt);
        putBoolean(root, "parse_tool_calls", parseToolCalls);
        putNode(root, "tools", tools);
        putNode(root, "tool_choice", toolChoice);
        putBoolean(root, "parallel_tool_calls", parallelToolCalls);
        putText(root, "reasoning_format", reasoningFormat);
        putText(root, "reasoning_control", reasoningControl);
        putInt(root, "thinking_budget_tokens", thinkingBudgetTokens);
        putBoolean(root, "logprobs", logprobs);
        putInt(root, "top_logprobs", topLogprobs);
    }

    /**
     * The {@code putInt} method writes one integer when the value is set.
     *
     * @param root The root is the chat object.
     * @param name The name is the JSON field.
     * @param value The value is the integer. A null value is skipped.
     */
    private static void putInt(ObjectNode root, String name, Integer value) {
        if (value != null) {
            root.put(name, value.intValue());
        }
    }

    /**
     * The {@code putLong} method writes one long when the value is set.
     *
     * @param root The root is the chat object.
     * @param name The name is the JSON field.
     * @param value The value is the long. A null value is skipped.
     */
    private static void putLong(ObjectNode root, String name, Long value) {
        if (value != null) {
            root.put(name, value.longValue());
        }
    }

    /**
     * The {@code putDouble} method writes one double when the value is set.
     *
     * @param root The root is the chat object.
     * @param name The name is the JSON field.
     * @param value The value is the double. A null value is skipped.
     */
    private static void putDouble(ObjectNode root, String name, Double value) {
        if (value != null) {
            root.put(name, value.doubleValue());
        }
    }

    /**
     * The {@code putBoolean} method writes one boolean when the value is set.
     *
     * @param root The root is the chat object.
     * @param name The name is the JSON field.
     * @param value The value is the boolean. A null value is skipped.
     */
    private static void putBoolean(ObjectNode root, String name, Boolean value) {
        if (value != null) {
            root.put(name, value.booleanValue());
        }
    }

    /**
     * The {@code putText} method writes one string when the value is set.
     *
     * @param root The root is the chat object.
     * @param name The name is the JSON field.
     * @param value The value is the text. A null value is skipped.
     */
    private static void putText(ObjectNode root, String name, String value) {
        if (value != null) {
            root.put(name, value);
        }
    }

    /**
     * The {@code putNode} method writes one JSON value when the node is set.
     *
     * @param root The root is the chat object.
     * @param name The name is the JSON field.
     * @param value The value is the node. A null value is skipped.
     */
    private static void putNode(ObjectNode root, String name, JsonNode value) {
        if (value != null) {
            root.set(name, value);
        }
    }

    /**
     * The {@code putStop} method writes {@code stop} when it is set.
     * An empty array is skipped.
     *
     * @param root The root is the chat object.
     * @param value The value is the stop node. A null value is skipped.
     */
    private static void putStop(ObjectNode root, JsonNode value) {
        if (value == null) {
            return;
        }
        if (value.isArray() && value.size() == 0) {
            return;
        }
        root.set("stop", value);
    }
}
/*****************************************************************************************
 * End of Class: LlamaCppCallOptions.java 111926
 ****************************************************************************************/
