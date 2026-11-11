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

package org.aetheris.wire;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// @aa-sdd PK-0600 PK-0723 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0836 PK-0883 PK-0930 PK-0977 PK-1024 PK-1071 PK-1118 PK-1165 PK-1212 PK-1259 PK-1306 PK-1353 PK-1400 PK-1447 PK-1494 PK-1541 PK-1589 PK-1636 PK-1683 PK-1730 PK-1777 PK-1824 PK-1871 PK-1918 PK-1965 PK-2012 PK-2060 PK-2107 PK-2154 PK-2201 PK-2249 PK-2297 PK-2345 PK-2392 PK-2439 PK-2487 PK-2534 PK-2581 PK-2628 PK-2676 PK-2723 PK-2771 PK-2818 PK-2865 PK-2912 PK-2959 PK-3006 PK-3053 PK-3100 PK-3147 PK-3195 PK-3242 PK-3289 PK-3337 PK-3384 PK-3431 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-0642 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0501 docs/specs/java/org/casserole/casserole.spec.md
/**
 * The {@code AmwpToon} class reads and writes the line text used inside an AMWP execute frame.
 * TOON is that line text.
 * The text is not JSON.
 * The class does not use the Roux message codec.
 * // @aa-sdd PK-0475 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0733 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-09-02)
 */
public final class AmwpToon {

    /**
     * The constructor stops a caller from creating an instance.
     * The encode and decode methods are static.
     */
    private AmwpToon() {
    }

    // @aa-sdd PK-0601 PK-0724 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0643 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0502 docs/specs/java/org/casserole/casserole.spec.md
    /**
     * The {@code ExecuteBody} class is one execute request.
     * {@link #encodeExecute} writes it.
     * {@link #decodeExecute} reads it.
     * A boolean that stays false is left off the wire, except {@code sendReasoningEffort}.
     */
    public static final class ExecuteBody {
        public String cellId = "";
        public String executionTarget = "";
        /**
         * The flag marks an ephemeral cell.
         * A false flag is left off the wire.
         * A missing line means false.
         */
        public boolean ephemeral;
        /**
         * The flag asks for tool calling.
         * A false flag is left off the wire.
         * A missing line means false.
         * This type does not set the flag to true by itself.
         */
        public boolean toolCallingEnabled;
        /**
         * The flag is the Grok burner hop.
         * A burner is an ephemeral request that may use a cloud target.
         * The flag is not {@code notebook.burner-key}.
         * A false flag is left off the wire.
         * A missing line means false.
         */
        public boolean burnerCloud;
        /**
         * The flag is the Grok {@code stream} setting.
         * It is not the Ollama think setting.
         * A false flag is left off the wire.
         * A missing line means false.
         */
        public boolean thinkStream;
        /**
         * The value is the Grok reasoning effort.
         * An empty value stays off the wire.
         * A missing line stays empty.
         * The service maps an empty Grok value to {@code high} when the send flag is on.
         */
        public String reasoningEffort = "";
        /**
         * The flag starts true, so a missing line means the effort is sent.
         * A false flag writes {@code send_reasoning_effort: false}.
         * That false leaves {@code reasoning_effort} off the Grok body.
         */
        public boolean sendReasoningEffort = true;
        /**
         * The value is the Ollama think setting.
         * An empty value means off and is left off the wire.
         * A missing line stays empty.
         * {@code true} is the checkbox.
         * Any other value is the level.
         * Off does not send {@code false}.
         */
        public String think = "";
        /**
         * The flag is true when {@code temperature} should be written.
         * A false flag leaves the cell factory temperature on the server.
         */
        public boolean temperaturePresent;
        public double temperature;
        /**
         * The flag is true when {@code maxCompletionTokens} should be written.
         * A false flag leaves the cell factory cap on the server.
         */
        public boolean maxCompletionPresent;
        public int maxCompletionTokens;
        /**
         * The value is the desktop boot text for the pool tail.
         * An empty value stays off the wire.
         * A missing line stays empty.
         * An empty value keeps a new pool session on every prompt.
         */
        public String poolBoot = "";
        public final List<Turn> workingSet = new ArrayList<Turn>();
    }

    // @aa-sdd PK-0479 PK-0482 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-4440 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    /**
     * The {@code ErrorBody} class is the body of a {@link AmwpConstants#TYPE_ERROR} frame.
     * A body that does not start with {@code reason:} is a plain banner.
     * {@code structured} is true only for a body that starts with {@code reason:}.
     */
    public static final class ErrorBody {
        public boolean structured;
        public String reason = "";
        public String message = "";
    }

    // @aa-sdd PK-0837 PK-0884 PK-0931 PK-0978 PK-1025 PK-1072 PK-1119 PK-1166 PK-1213 PK-1260 PK-1307 PK-1354 PK-1401 PK-1448 PK-1495 PK-1542 PK-1590 PK-1637 PK-1684 PK-1731 PK-1778 PK-1825 PK-1872 PK-1919 PK-1966 PK-2013 PK-2061 PK-2108 PK-2155 PK-2202 PK-2250 PK-2298 PK-2346 PK-2393 PK-2440 PK-2488 PK-2535 PK-2582 PK-2629 PK-2677 PK-2724 PK-2772 PK-2819 PK-2866 PK-2913 PK-2960 PK-3007 PK-3054 PK-3101 PK-3148 PK-3196 PK-3243 PK-3290 PK-3338 PK-3385 PK-3432 docs/specs/java/org/roux/roux.spec.md
    /**
     * The {@code SessionFacts} class holds the facts from an Init body.
     * An empty body leaves {@code reported} false.
     * That absence is not the integer 0.
     */
    public static final class SessionFacts {
        public boolean reported;
        public String airGap = "unset";
        public boolean numCtxPresent;
        public int numCtx;
        /**
         * The count is the sink allowance.
         * A missing line leaves the count at 0.
         */
        public int managedTokens;
        /**
         * The count is how many turns may lock.
         * A missing line leaves the count at 0.
         */
        public int headCount;
    }

    // @aa-sdd PK-0838 PK-0885 PK-0932 PK-0979 PK-1026 PK-1073 PK-1120 PK-1167 PK-1214 PK-1261 PK-1308 PK-1355 PK-1402 PK-1449 PK-1496 PK-1543 PK-1591 PK-1638 PK-1685 PK-1732 PK-1779 PK-1826 PK-1873 PK-1920 PK-1967 PK-2014 PK-2062 PK-2109 PK-2156 PK-2203 PK-2251 PK-2299 PK-2347 PK-2394 PK-2441 PK-2489 PK-2536 PK-2583 PK-2630 PK-2678 PK-2725 PK-2773 PK-2820 PK-2867 PK-2914 PK-2961 PK-3008 PK-3055 PK-3102 PK-3149 PK-3197 PK-3244 PK-3291 PK-3339 PK-3386 PK-3433 docs/specs/java/org/roux/roux.spec.md
    /**
     * The {@code Turn} class is one row in the working set.
     * The {@code role} field starts as {@code user}.
     * The {@code text} field starts empty.
     */
    public static final class Turn {
        public String role = "user";
        public String text = "";
        /**
         * The flag marks this turn as designated.
         * A false flag is left off the wire.
         * A missing line means the turn is not designated.
         */
        public boolean head;
    }

    /**
     * The method writes an execute request as UTF-8 lines.
     * {@code cell_id} and {@code execution_target} are always written.
     * A true ephemeral, tool-calling, burner-cloud, or think-stream flag writes its line.
     * A false {@code sendReasoningEffort} writes {@code send_reasoning_effort: false}.
     * A non-empty reasoning effort, think value, or pool boot writes its line.
     * A present temperature writes {@code temperature}.
     * A present completion cap writes {@code max_completion_tokens}.
     * The working set is written after {@code working_set:}.
     * Each turn writes {@code role} and {@code text}.
     * A designated turn also writes {@code head: true}.
     *
     * @param body The body is the execute request.
     * @return The return value is the UTF-8 text.
     */
    public static byte[] encodeExecute(ExecuteBody body) {
        StringBuilder sb = new StringBuilder();
        sb.append("cell_id: ").append(esc(body.cellId)).append("\n");
        sb.append("execution_target: ").append(esc(body.executionTarget)).append("\n");
        if (body.ephemeral) {
            sb.append("ephemeral: true\n");
        }
        if (body.toolCallingEnabled) {
            sb.append("tool_calling_enabled: true\n");
        }
        if (body.burnerCloud) {
            sb.append("burner_cloud: true\n");
        }
        if (body.thinkStream) {
            sb.append("think_stream: true\n");
        }
        if (!body.sendReasoningEffort) {
            sb.append("send_reasoning_effort: false\n");
        }
        if (body.reasoningEffort != null && body.reasoningEffort.length() > 0) {
            sb.append("reasoning_effort: ").append(esc(body.reasoningEffort)).append("\n");
        }
        if (body.think != null && body.think.length() > 0) {
            sb.append("think: ").append(esc(body.think)).append("\n");
        }
        if (body.temperaturePresent) {
            sb.append("temperature: ").append(Double.toString(body.temperature)).append("\n");
        }
        if (body.maxCompletionPresent) {
            sb.append("max_completion_tokens: ").append(body.maxCompletionTokens).append("\n");
        }
        if (body.poolBoot != null && body.poolBoot.length() > 0) {
            sb.append("pool_boot: ").append(esc(body.poolBoot)).append("\n");
        }
        sb.append("working_set:\n");
        for (int i = 0; i < body.workingSet.size(); i++) {
            Turn turn = body.workingSet.get(i);
            sb.append("- role: ").append(esc(turn.role)).append("\n");
            sb.append("  text: ").append(esc(turn.text)).append("\n");
            if (turn.head) {
                sb.append("  head: true\n");
            }
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * The method reads an execute request from UTF-8 lines.
     * A null or empty array returns a new {@link ExecuteBody} with the field defaults.
     * Each known key fills the matching field.
     * A temperature or completion cap that is not a number leaves that present flag false.
     * A line that starts with {@code - role:} begins a new {@link Turn}.
     * A later {@code text:} or {@code head:} line fills the current turn.
     * A text or head line before any role is ignored.
     * An unknown line is ignored.
     *
     * @param raw The raw bytes are the UTF-8 text, and they may be null.
     * @return The return value is the parsed request.
     */
    public static ExecuteBody decodeExecute(byte[] raw) {
        ExecuteBody body = new ExecuteBody();
        if (raw == null || raw.length == 0) {
            return body;
        }
        String text = new String(raw, StandardCharsets.UTF_8);
        String[] lines = text.split("\n", -1);
        Turn current = null;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("cell_id:")) {
                body.cellId = unesc(line.substring("cell_id:".length()).trim());
                continue;
            }
            if (line.startsWith("execution_target:")) {
                body.executionTarget = unesc(line.substring("execution_target:".length()).trim());
                continue;
            }
            if (line.startsWith("ephemeral:")) {
                body.ephemeral = truth(line.substring("ephemeral:".length()).trim());
                continue;
            }
            if (line.startsWith("tool_calling_enabled:")) {
                body.toolCallingEnabled = truth(line.substring("tool_calling_enabled:".length()).trim());
                continue;
            }
            if (line.startsWith("burner_cloud:")) {
                body.burnerCloud = truth(line.substring("burner_cloud:".length()).trim());
                continue;
            }
            if (line.startsWith("think_stream:")) {
                body.thinkStream = truth(line.substring("think_stream:".length()).trim());
                continue;
            }
            if (line.startsWith("send_reasoning_effort:")) {
                body.sendReasoningEffort = truth(line.substring("send_reasoning_effort:".length()).trim());
                continue;
            }
            if (line.startsWith("reasoning_effort:")) {
                body.reasoningEffort = unesc(line.substring("reasoning_effort:".length()).trim());
                continue;
            }
            if (line.startsWith("think:")) {
                body.think = unesc(line.substring("think:".length()).trim());
                continue;
            }
            if (line.startsWith("temperature:")) {
                try {
                    body.temperature = Double.parseDouble(line.substring("temperature:".length()).trim());
                    body.temperaturePresent = true;
                } catch (NumberFormatException ignored) {
                    body.temperaturePresent = false;
                }
                continue;
            }
            if (line.startsWith("max_completion_tokens:")) {
                try {
                    body.maxCompletionTokens = Integer.parseInt(
                            line.substring("max_completion_tokens:".length()).trim());
                    body.maxCompletionPresent = true;
                } catch (NumberFormatException ignored) {
                    body.maxCompletionPresent = false;
                }
                continue;
            }
            if (line.startsWith("pool_boot:")) {
                body.poolBoot = unesc(line.substring("pool_boot:".length()).trim());
                continue;
            }
            String trimmed = line.trim();
            if (trimmed.startsWith("- role:")) {
                current = new Turn();
                current.role = unesc(trimmed.substring("- role:".length()).trim());
                body.workingSet.add(current);
                continue;
            }
            if (current != null && trimmed.startsWith("text:")) {
                current.text = unesc(trimmed.substring("text:".length()).trim());
                continue;
            }
            if (current != null && trimmed.startsWith("head:")) {
                current.head = truth(trimmed.substring("head:".length()).trim());
            }
        }
        return body;
    }

    /**
     * The method writes an error body as two UTF-8 lines.
     * The first line is {@code reason:}.
     * The second line is {@code message:}.
     *
     * @param reason The reason is the token stored after {@code reason:}.
     * @param message The message is the banner text stored after {@code message:}.
     * @return The return value is the UTF-8 text.
     */
    public static byte[] encodeError(String reason, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("reason: ").append(esc(reason)).append("\n");
        sb.append("message: ").append(esc(message)).append("\n");
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * The method reads an error body.
     * A null body returns an empty message and leaves {@code structured} false.
     * A body that does not start with {@code reason:} stores the whole text as the message.
     * A body that starts with {@code reason:} sets {@code structured} and reads {@code reason} and {@code message} lines.
     * Other lines in a structured body are ignored.
     *
     * @param raw The raw text is the error payload, and it may be null.
     * @return The return value is the parsed error, or a plain banner.
     */
    public static ErrorBody decodeError(String raw) {
        ErrorBody body = new ErrorBody();
        if (raw == null) {
            body.message = "";
            return body;
        }
        String text = raw;
        if (!text.startsWith("reason:")) {
            body.message = text;
            return body;
        }
        body.structured = true;
        String[] lines = text.split("\n", -1);
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("reason:")) {
                body.reason = unesc(line.substring("reason:".length()).trim());
            } else if (line.startsWith("message:")) {
                body.message = unesc(line.substring("message:".length()).trim());
            }
        }
        return body;
    }

    /**
     * The method writes Init facts with the token counts left at 0.
     * It calls {@link #encodeFacts(String, int, int, int)}.
     *
     * @param airGap The word is {@code true}, {@code false}, or {@code unset}.
     * @param numCtx The integer is the server context size, and a new server always writes it.
     * @return The return value is the UTF-8 text.
     */
    public static byte[] encodeFacts(String airGap, int numCtx) {
        return encodeFacts(airGap, numCtx, 0, 0);
    }

    /**
     * The method writes four Init fact lines.
     * A null or empty air-gap word is written as {@code unset}.
     * The lines are {@code air-gap}, {@code num-ctx}, {@code managed-tokens}, and {@code head-count}.
     * A managed-token count of 0 means unset.
     * A head count of 0 means unset.
     *
     * @param airGap The word is {@code true}, {@code false}, or {@code unset}.
     * @param numCtx The integer is the server context size.
     * @param managedTokens The count is the sink allowance, and 0 means unset.
     * @param headCount The count is how many turns may lock, and 0 means unset.
     * @return The return value is the UTF-8 text.
     */
    public static byte[] encodeFacts(String airGap, int numCtx, int managedTokens, int headCount) {
        String word = airGap == null || airGap.length() == 0 ? "unset" : airGap;
        StringBuilder sb = new StringBuilder();
        sb.append("air-gap: ").append(esc(word)).append("\n");
        sb.append("num-ctx: ").append(numCtx).append("\n");
        sb.append("managed-tokens: ").append(managedTokens).append("\n");
        sb.append("head-count: ").append(headCount).append("\n");
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * The method reads Init facts.
     * A null or empty body returns facts with {@code reported} false.
     * Any other body sets {@code reported}.
     * {@code air-gap} stores the word.
     * {@code num-ctx} stores the integer and sets {@code numCtxPresent}.
     * A {@code num-ctx} value that is not an integer clears {@code numCtxPresent}.
     * {@code managed-tokens} stores the integer, and a bad integer stores 0.
     * {@code head-count} stores the integer, and a bad integer stores 0.
     * An unknown line is ignored.
     *
     * @param raw The raw bytes are the Init body, and they may be null.
     * @return The return value is the parsed facts.
     */
    public static SessionFacts decodeFacts(byte[] raw) {
        SessionFacts facts = new SessionFacts();
        if (raw == null || raw.length == 0) {
            return facts;
        }
        facts.reported = true;
        String text = new String(raw, StandardCharsets.UTF_8);
        String[] lines = text.split("\n", -1);
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("air-gap:")) {
                facts.airGap = unesc(line.substring("air-gap:".length()).trim());
            } else if (line.startsWith("num-ctx:")) {
                facts.numCtxPresent = true;
                try {
                    facts.numCtx = Integer.parseInt(line.substring("num-ctx:".length()).trim());
                } catch (NumberFormatException ex) {
                    facts.numCtxPresent = false;
                }
            } else if (line.startsWith("managed-tokens:")) {
                try {
                    facts.managedTokens = Integer.parseInt(line.substring("managed-tokens:".length()).trim());
                } catch (NumberFormatException ex) {
                    facts.managedTokens = 0;
                }
            } else if (line.startsWith("head-count:")) {
                try {
                    facts.headCount = Integer.parseInt(line.substring("head-count:".length()).trim());
                } catch (NumberFormatException ex) {
                    facts.headCount = 0;
                }
            }
        }
        return facts;
    }

    /**
     * The method reads a boolean token from a line.
     * {@code true} and {@code yes} match in any letter case.
     * The token {@code 1} matches only that exact character.
     * Any other token returns false.
     *
     * @param raw The raw value is the text after the colon.
     * @return The return value is true for {@code true}, {@code yes}, or {@code 1}.
     */
    private static boolean truth(String raw) {
        return "true".equalsIgnoreCase(raw) || "yes".equalsIgnoreCase(raw) || "1".equals(raw);
    }

    /**
     * The method escapes one field for a line.
     * A null value returns an empty string.
     * A backslash becomes two backslashes.
     * A newline becomes {@code \\n}.
     * A carriage return is removed.
     *
     * @param raw The raw value is the field text, and it may be null.
     * @return The return value is the escaped text.
     */
    private static String esc(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "");
    }

    /**
     * The method reverses {@link #esc}.
     * A null value returns an empty string.
     * {@code \\n} becomes a newline.
     * A doubled backslash becomes one backslash.
     * Any other backslash is kept, including the following character.
     *
     * @param raw The raw value is the escaped field, and it may be null.
     * @return The return value is the decoded text.
     */
    private static String unesc(String raw) {
        if (raw == null) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == '\\' && i + 1 < raw.length()) {
                char n = raw.charAt(i + 1);
                if (n == 'n') {
                    out.append('\n');
                    i++;
                    continue;
                }
                if (n == '\\') {
                    out.append('\\');
                    i++;
                    continue;
                }
            }
            out.append(c);
        }
        return out.toString();
    }
}
/*****************************************************************************************
 * End of Class: AmwpToon.java 090226
 ****************************************************************************************/
