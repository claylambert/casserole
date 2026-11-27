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

package org.aetheris.swing;

import java.util.LinkedHashMap;
import java.util.Map;

import org.casserole.llm.GrokCallOptions;

/**
 * The seat is one user's graph model and the options chosen with it.
 * A word alone is the engine. A kept seat also holds the options.
 * The think file stores the seat under that user.
 *
 * @author clayl (2026-11-26)
 */
// @aa-sdd L40 aetheris-plan-f-324.001.md FINESSE:f-324
final class GraphModelSeat {

    private String engine = "";
    private boolean kept;
    private String effort = "high";
    private boolean sendEffort = true;
    private boolean stream;
    private boolean burner;
    private double temperature = 1.0d;
    private int maxTokens = 128000;
    private boolean grokOn;
    private boolean ollamaOn;
    private String ollamaLevel = "";
    private boolean poolOn;
    private boolean llamaTemperaturePresent;
    private double llamaTemperature;
    private boolean llamaMaxPresent;
    private int llamaMaxTokens;

    /**
     * The method reads one yaml value.
     * A string is the engine word and the options are not kept.
     * A map with an option key keeps the options.
     * A null value returns null.
     *
     * @param value The value is the stored yaml node.
     * @return The return value is the seat, or null.
     */
    static GraphModelSeat parse(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            GraphModelSeat seat = new GraphModelSeat();
            seat.engine = ((String) value).trim();
            return seat;
        }
        if (!(value instanceof Map)) {
            String word = String.valueOf(value).trim();
            if (word.length() == 0 || "null".equals(word)) {
                return null;
            }
            GraphModelSeat seat = new GraphModelSeat();
            seat.engine = word;
            return seat;
        }
        Map<?, ?> row = (Map<?, ?>) value;
        GraphModelSeat seat = new GraphModelSeat();
        seat.engine = text(row.get("engine"));
        if (hasOption(row)) {
            seat.kept = true;
            seat.effort = text(row.get("reasoning_effort"));
            if (seat.effort.length() == 0) {
                seat.effort = "high";
            }
            seat.sendEffort = !row.containsKey("send_reasoning_effort") || flag(row.get("send_reasoning_effort"));
            seat.stream = flag(row.get("stream"));
            seat.burner = flag(row.get("burner_cloud"));
            if (row.get("temperature") instanceof Number) {
                seat.temperature = ((Number) row.get("temperature")).doubleValue();
            }
            if (row.get("max_completion_tokens") instanceof Number) {
                seat.maxTokens = ((Number) row.get("max_completion_tokens")).intValue();
            }
            seat.grokOn = flag(row.get("grok_on"));
            seat.ollamaOn = flag(row.get("ollama_on"));
            seat.ollamaLevel = text(row.get("ollama_level"));
            seat.poolOn = flag(row.get("pool_on"));
            if (row.get("llama_temperature") instanceof Number) {
                seat.llamaTemperaturePresent = true;
                seat.llamaTemperature = ((Number) row.get("llama_temperature")).doubleValue();
            }
            if (row.get("llama_max_tokens") instanceof Number) {
                seat.llamaMaxPresent = true;
                seat.llamaMaxTokens = ((Number) row.get("llama_max_tokens")).intValue();
            }
        }
        return seat;
    }

    /**
     * The method writes the seat for the think file.
     * A seat that has not kept options writes the engine word.
     * A kept seat writes the engine and the options.
     * An empty engine returns null.
     *
     * @return The return value is the yaml node, or null.
     */
    Object yamlValue() {
        if (engine == null || engine.trim().length() == 0) {
            return null;
        }
        if (!kept) {
            return engine.trim();
        }
        LinkedHashMap<String, Object> row = new LinkedHashMap<String, Object>();
        row.put("engine", engine.trim());
        row.put("reasoning_effort", effort == null ? "high" : effort);
        row.put("send_reasoning_effort", Boolean.valueOf(sendEffort));
        row.put("stream", Boolean.valueOf(stream));
        row.put("burner_cloud", Boolean.valueOf(burner));
        row.put("temperature", Double.valueOf(temperature));
        row.put("max_completion_tokens", Integer.valueOf(maxTokens));
        row.put("grok_on", Boolean.valueOf(grokOn));
        row.put("ollama_on", Boolean.valueOf(ollamaOn));
        row.put("ollama_level", ollamaLevel == null ? "" : ollamaLevel);
        row.put("pool_on", Boolean.valueOf(poolOn));
        if (llamaTemperaturePresent) {
            row.put("llama_temperature", Double.valueOf(llamaTemperature));
        }
        if (llamaMaxPresent) {
            row.put("llama_max_tokens", Integer.valueOf(llamaMaxTokens));
        }
        return row;
    }

    /**
     * The method reports whether the options were stored.
     *
     * @return The return value is true when the options are kept.
     */
    boolean kept() {
        return kept;
    }

    /**
     * The method returns the engine word.
     *
     * @return The return value is the trimmed word, or empty.
     */
    String engine() {
        return engine == null ? "" : engine.trim();
    }

    /**
     * The method stores the engine word.
     * The options stay as they are.
     *
     * @param word The word is the engine. A null word stores empty.
     */
    void engine(String word) {
        engine = word == null ? "" : word.trim();
    }

    /**
     * The method stores the Grok options and marks them kept.
     *
     * @param modelId The model id normalizes the effort.
     * @param effortWord The effort is the level text.
     * @param send The flag sends the effort.
     * @param streamOn The flag streams the call.
     * @param burnerOn The flag allows a burner hop.
     * @param temp The value is the temperature.
     * @param max The value is the completion cap.
     * @param thinking The flag is the Grok thinking checkbox.
     */
    void keepGrok(String modelId, String effortWord, boolean send, boolean streamOn, boolean burnerOn,
            double temp, int max, boolean thinking) {
        effort = GrokSettingsDocument.normalizeEffort(modelId, effortWord);
        sendEffort = send;
        stream = streamOn;
        burner = burnerOn;
        temperature = temp;
        if (max >= 1) {
            maxTokens = max;
        }
        grokOn = thinking;
        kept = true;
    }

    /**
     * The method stores the Ollama thinking options and marks them kept.
     *
     * @param on The flag is the Ollama thinking checkbox.
     * @param level The level is the effort text.
     */
    void keepOllama(boolean on, String level) {
        ollamaOn = on;
        ollamaLevel = level == null ? "" : level.trim();
        kept = true;
    }

    /**
     * The method stores the pool thinking flag and marks the options kept.
     *
     * @param on The flag is the pool thinking checkbox.
     */
    void keepPool(boolean on) {
        poolOn = on;
        kept = true;
    }

    /**
     * The method stores the llama temperature and cap and marks the options kept.
     *
     * @param tempPresent The flag is true when a temperature was stored.
     * @param temp The value is the temperature.
     * @param maxPresent The flag is true when a cap was stored.
     * @param max The value is the cap.
     */
    void keepLlama(boolean tempPresent, double temp, boolean maxPresent, int max) {
        llamaTemperaturePresent = tempPresent;
        llamaTemperature = temp;
        llamaMaxPresent = maxPresent;
        llamaMaxTokens = max;
        kept = true;
    }

    /**
     * The method builds the DSL call options for a kept Grok engine.
     * Any other engine returns null.
     * A seat that has not kept options returns null.
     * Thinking on forces the stream on.
     * A stored stream also turns the stream on.
     *
     * @param thinking The flag is true when the thinking ear is open.
     * @return The return value is the call options, or null.
     */
    GrokCallOptions callOptions(boolean thinking) {
        if (!kept || !"grok".equals(AetherisStage2.providerId(engine))) {
            return null;
        }
        GrokCallOptions options = new GrokCallOptions();
        options.reasoningEffort = effort == null ? "" : effort;
        options.sendReasoningEffort = sendEffort;
        options.temperature = temperature;
        options.maxCompletionTokens = maxTokens < 1 ? 4000 : maxTokens;
        options.thinkStream = thinking || stream;
        return options;
    }

    String effort() {
        return effort == null ? "" : effort;
    }

    boolean sendEffort() {
        return sendEffort;
    }

    boolean stream() {
        return stream;
    }

    boolean burner() {
        return burner;
    }

    double temperature() {
        return temperature;
    }

    int maxTokens() {
        return maxTokens;
    }

    boolean grokOn() {
        return grokOn;
    }

    boolean ollamaOn() {
        return ollamaOn;
    }

    String ollamaLevel() {
        return ollamaLevel == null ? "" : ollamaLevel;
    }

    boolean poolOn() {
        return poolOn;
    }

    boolean llamaTemperaturePresent() {
        return llamaTemperaturePresent;
    }

    double llamaTemperature() {
        return llamaTemperature;
    }

    boolean llamaMaxPresent() {
        return llamaMaxPresent;
    }

    int llamaMaxTokens() {
        return llamaMaxTokens;
    }

    private static boolean hasOption(Map<?, ?> row) {
        return row.containsKey("reasoning_effort")
                || row.containsKey("send_reasoning_effort")
                || row.containsKey("stream")
                || row.containsKey("burner_cloud")
                || row.containsKey("temperature")
                || row.containsKey("max_completion_tokens")
                || row.containsKey("grok_on")
                || row.containsKey("ollama_on")
                || row.containsKey("ollama_level")
                || row.containsKey("pool_on")
                || row.containsKey("llama_temperature")
                || row.containsKey("llama_max_tokens");
    }

    private static String text(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value).trim();
    }

    private static boolean flag(Object value) {
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        if (value == null) {
            return false;
        }
        String word = String.valueOf(value).trim();
        return "true".equalsIgnoreCase(word) || "yes".equalsIgnoreCase(word) || "1".equals(word);
    }
}
