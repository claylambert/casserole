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

package org.roux.toon;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.roux.model.DecodedFrame;
import org.roux.model.Role;

// @aa-sdd PK-0756 PK-0792 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-4473 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
/**
 * The ToonCodec writes and reads the text payload of one message.
 * One encoded payload starts with the line {@code m[1]{r,ts,tok,x}:}.
 * The next line is the role word, the time, the token count, and the text, separated by commas.
 * {@link #decode} keeps the role, the time, and the token count from the frame header.
 * The text comes from the payload.
 *
 * The type refuses construction.
 *
 * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
 * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
 * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
 * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
 * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
 * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
 * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
 * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
 * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
 * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
 * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
 * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
 * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
 * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
 * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
 * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
 * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
 * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
 *
 * @author clayl (2026-08-25)
 */
public final class ToonCodec {

    private static final String HEADER = "m[1]{r,ts,tok,x}:";

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private ToonCodec() {
    }

    /**
     * The method encodes one message as UTF-8 bytes.
     * A null text is written as an empty body.
     * The role byte is written as its role word.
     *
     * // @aa-sdd PK-0475 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-0733 docs/specs/java/org/roux/model/model.spec.md
     *
     * @param role The role is the role byte.
     * @param timestampEpochMs The time is the epoch milliseconds stored with the message.
     * @param tokenCount The token count is the number stored with the message.
     * @param text The text is the message body, or null.
     * @return The bytes are the encoded payload.
     */
    public static byte[] encode(byte role, long timestampEpochMs, int tokenCount, String text) {
        String body = text == null ? "" : text;
        StringBuilder sb = new StringBuilder(64 + body.length());
        sb.append(HEADER);
        sb.append('\n');
        sb.append(Role.nameOf(role));
        sb.append(',');
        sb.append(timestampEpochMs);
        sb.append(',');
        sb.append(tokenCount);
        sb.append(',');
        appendQuoted(sb, body);
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * The method builds a {@link DecodedFrame} from a payload.
     * A null payload is read as an empty string.
     * The role, the time, and the token count come from the arguments.
     * The text is the field after the third comma.
     *
     * @param payload The payload is the encoded bytes, or null.
     * @param messageId The message id is the number that locates the frame.
     * @param headerRole The role is the role byte from the frame header.
     * @param headerTs The time is the epoch milliseconds from the frame header.
     * @param headerTokens The token count is the number from the frame header.
     * @return The frame is the decoded message.
     */
    public static DecodedFrame decode(byte[] payload, long messageId, byte headerRole, long headerTs,
            int headerTokens) {
        String raw = payload == null ? "" : new String(payload, StandardCharsets.UTF_8);
        String text = extractText(raw);
        return new DecodedFrame(messageId, headerRole, headerTs, headerTokens, text);
    }

    /**
     * The method appends one table row.
     * An empty builder first receives the line {@code messages[0]{r,ts,tok,x}:}.
     * A null text is written as an empty body.
     * The row ends with a newline.
     *
     * @param out The builder receives the row.
     * @param role The role is the role word.
     * @param ts The time is the epoch milliseconds.
     * @param tok The token count is the number stored with the message.
     * @param text The text is the message body, or null.
     */
    public static void appendTableRow(StringBuilder out, String role, long ts, int tok, String text) {
        if (out.length() == 0) {
            out.append("messages[0]{r,ts,tok,x}:\n");
        }
        out.append(role);
        out.append(',');
        out.append(ts);
        out.append(',');
        out.append(tok);
        out.append(',');
        appendQuoted(out, text == null ? "" : text);
        out.append('\n');
    }

    /**
     * The method replaces {@code messages[0]} with the real row count.
     *
     * @param out The builder holds the table.
     * @param count The count is the number of rows.
     * @return The table is the text with the count filled in.
     */
    public static String finishTable(StringBuilder out, int count) {
        String s = out.toString();
        return s.replace("messages[0]{", "messages[" + count + "]{");
    }

    /**
     * The method appends text, quoting it when a comma or a special character is present.
     * An empty string is quoted.
     * A quote, a backslash, a newline, a carriage return, and a tab are escaped inside quotes.
     *
     * @param sb The builder receives the text.
     * @param text The text is the raw body.
     */
    private static void appendQuoted(StringBuilder sb, String text) {
        boolean quote = needsQuote(text);
        if (!quote) {
            sb.append(text);
            return;
        }
        sb.append('"');
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\\' || c == '"') {
                sb.append('\\');
                sb.append(c);
            } else if (c == '\n') {
                sb.append('\\');
                sb.append('n');
            } else if (c == '\r') {
                sb.append('\\');
                sb.append('r');
            } else if (c == '\t') {
                sb.append('\\');
                sb.append('t');
            } else {
                sb.append(c);
            }
        }
        sb.append('"');
    }

    /**
     * The method reports whether a body must be quoted.
     * An empty string must be quoted.
     * A comma, a quote, a newline, a carriage return, a colon, or a bracket must be quoted.
     *
     * @param text The text is the body to inspect.
     * @return The flag is true when quotes are required.
     */
    private static boolean needsQuote(String text) {
        if (text.length() == 0) {
            return true;
        }
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ',' || c == '"' || c == '\n' || c == '\r' || c == ':' || c == '[' || c == ']') {
                return true;
            }
        }
        return false;
    }

    /**
     * The method returns the text field of one payload.
     * The field is the text after the third comma on the line after the first newline.
     * A payload with no newline uses the whole string.
     * Fewer than three commas returns an empty string.
     * A trailing newline or carriage return is removed.
     *
     * @param raw The raw value is the payload string.
     * @return The text is the message body, or an empty string.
     */
    private static String extractText(String raw) {
        int line = raw.indexOf('\n');
        String row = line >= 0 ? raw.substring(line + 1) : raw;
        int comma = 0;
        int seen = 0;
        for (int i = 0; i < row.length(); i++) {
            if (row.charAt(i) == ',') {
                seen++;
                if (seen == 3) {
                    comma = i;
                    break;
                }
            }
        }
        if (seen < 3) {
            return "";
        }
        String field = row.substring(comma + 1);
        if (field.endsWith("\n")) {
            field = field.substring(0, field.length() - 1);
        }
        if (field.endsWith("\r")) {
            field = field.substring(0, field.length() - 1);
        }
        return unquote(field);
    }

    /**
     * The method removes quotes and escape sequences from one field.
     * A field that does not start with a quote is returned unchanged.
     * {@code \\n} becomes a newline.
     * {@code \\r} becomes a carriage return.
     * {@code \\t} becomes a tab.
     * A quoted backslash or quote keeps the following character.
     *
     * @param field The field is the text that may be quoted.
     * @return The text is the unescaped body.
     */
    private static String unquote(String field) {
        if (field.length() < 2 || field.charAt(0) != '"') {
            return field;
        }
        String inner = field.substring(1, field.charAt(field.length() - 1) == '"' ? field.length() - 1 : field.length());
        StringBuilder sb = new StringBuilder(inner.length());
        for (int i = 0; i < inner.length(); i++) {
            char c = inner.charAt(i);
            if (c == '\\' && i + 1 < inner.length()) {
                char n = inner.charAt(i + 1);
                if (n == 'n') {
                    sb.append('\n');
                    i++;
                } else if (n == 'r') {
                    sb.append('\r');
                    i++;
                } else if (n == 't') {
                    sb.append('\t');
                    i++;
                } else if (n == '"' || n == '\\') {
                    sb.append(n);
                    i++;
                } else {
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * The method reads a message table into role and text pairs.
     * A null table returns null.
     * Lines are split on a newline, and a trailing carriage return is removed.
     * A blank line after the header is skipped.
     * The header must start with {@code messages[} and end with {@code ]{r,ts,tok,x}:}.
     * The number between the brackets is the row count.
     * A header that is not that shape, a count that is not a number, or a negative count returns null.
     * A row that {@link #splitMessageRow} cannot split returns null.
     * A row count that does not match the data rows returns null.
     * A count of zero and no data rows returns a zero-length array.
     * Each pair is the role, then the text.
     *
     * @param table The table is the {@code messages[N]{r,ts,tok,x}:} text, or null.
     * @return The array is role, text, role, text, or null when the table is refused.
     */
    public static String[] parseMessageTable(String table) {
        if (table == null) {
            return null;
        }
        ArrayList<String> lines = new ArrayList<String>();
        int start = 0;
        while (start <= table.length()) {
            int nl = table.indexOf('\n', start);
            String line = nl < 0 ? table.substring(start) : table.substring(start, nl);
            if (line.endsWith("\r")) {
                line = line.substring(0, line.length() - 1);
            }
            if (nl < 0) {
                if (line.length() > 0) {
                    lines.add(line);
                }
                break;
            }
            lines.add(line);
            start = nl + 1;
            if (start == table.length()) {
                break;
            }
        }
        if (lines.size() == 0) {
            return null;
        }
        String header = lines.get(0);
        if (!header.startsWith("messages[") || !header.endsWith("]{r,ts,tok,x}:")) {
            return null;
        }
        int open = header.indexOf('[');
        int close = header.indexOf(']');
        if (open < 0 || close < open + 1) {
            return null;
        }
        int count;
        try {
            count = Integer.parseInt(header.substring(open + 1, close));
        } catch (NumberFormatException ex) {
            return null;
        }
        if (count < 0) {
            return null;
        }
        ArrayList<String> flat = new ArrayList<String>();
        int rows = 0;
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.length() == 0) {
                continue;
            }
            String[] pair = splitMessageRow(line);
            if (pair == null) {
                return null;
            }
            flat.add(pair[0]);
            flat.add(pair[1]);
            rows++;
        }
        if (rows != count) {
            return null;
        }
        String[] out = new String[flat.size()];
        for (int i = 0; i < flat.size(); i++) {
            out[i] = flat.get(i);
        }
        return out;
    }

    /**
     * The method pairs user text with the assistant text that follows it.
     * A null array returns null.
     * An odd length returns null because the values are not role and text pairs.
     * A null text is read as an empty string.
     * A user row and the assistant row after it are one cell.
     * A user row with no assistant yet is a cell with an empty reply.
     * An assistant row with no user ahead of it is a cell with an empty prompt.
     * A system row, a tool row, and any other role are left out.
     * A list with no user or assistant row returns a zero-length array.
     *
     * @param flat The flat array is role, text pairs from {@link #parseMessageTable}, or null.
     * @return The array is prompt, reply, prompt, reply, or null when the input is not pairs.
     */
    public static String[] pairConversation(String[] flat) {
        if (flat == null || (flat.length % 2) != 0) {
            return null;
        }
        ArrayList<String> cells = new ArrayList<String>();
        String pending = null;
        boolean hold = false;
        for (int i = 0; i < flat.length; i += 2) {
            String role = flat[i];
            String text = flat[i + 1] == null ? "" : flat[i + 1];
            if ("user".equals(role)) {
                if (hold) {
                    cells.add(pending);
                    cells.add("");
                }
                pending = text;
                hold = true;
            } else if ("assistant".equals(role)) {
                if (hold) {
                    cells.add(pending);
                    cells.add(text);
                    hold = false;
                } else {
                    cells.add("");
                    cells.add(text);
                }
            }
        }
        if (hold) {
            cells.add(pending);
            cells.add("");
        }
        String[] out = new String[cells.size()];
        for (int i = 0; i < cells.size(); i++) {
            out[i] = cells.get(i);
        }
        return out;
    }

    /**
     * The method writes the table that {@link #parseMessageTable} reads.
     * A null role list or a null text list returns null.
     * Lists of different lengths return null.
     * Two empty lists return {@code messages[0]{r,ts,tok,x}:} and a newline.
     * Each row uses a timestamp of zero and a token count of zero.
     * A null text is written as an empty body.
     *
     * @param roles The roles are the role words, one per row.
     * @param texts The texts are the row bodies, one per role.
     * @return The table is the encoded text, or null when the lists disagree.
     */
    public static String conversationTable(List<String> roles, List<String> texts) {
        if (roles == null || texts == null || roles.size() != texts.size()) {
            return null;
        }
        if (roles.size() == 0) {
            return "messages[0]{r,ts,tok,x}:\n";
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < roles.size(); i++) {
            String text = texts.get(i) == null ? "" : texts.get(i);
            appendTableRow(out, roles.get(i), 0L, 0, text);
        }
        return finishTable(out, roles.size());
    }

    /**
     * The method splits one table row into a role and a text.
     * The role is the text before the first comma.
     * The text is the field after the third comma.
     * A comma inside quotes stays in the text.
     * A backslash inside quotes skips the next character.
     * An empty role, a missing first comma, or a missing third comma returns null.
     * A role character that is not a lowercase letter or an underscore returns null.
     * The text is passed through {@link #unquote}.
     *
     * @param line The line is one table row.
     * @return The pair is the role and the text, or null when the row is refused.
     */
    private static String[] splitMessageRow(String line) {
        int first = -1;
        int third = -1;
        int commas = 0;
        boolean quote = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (quote) {
                if (c == '\\' && i + 1 < line.length()) {
                    i++;
                    continue;
                }
                if (c == '"') {
                    quote = false;
                }
                continue;
            }
            if (c == '"') {
                quote = true;
                continue;
            }
            if (c == ',') {
                commas++;
                if (commas == 1) {
                    first = i;
                }
                if (commas == 3) {
                    third = i;
                    break;
                }
            }
        }
        if (first <= 0 || third < 0) {
            return null;
        }
        String role = line.substring(0, first);
        for (int i = 0; i < role.length(); i++) {
            char c = role.charAt(i);
            boolean ok = (c >= 'a' && c <= 'z') || c == '_';
            if (!ok) {
                return null;
            }
        }
        String[] pair = new String[2];
        pair[0] = role;
        pair[1] = unquote(line.substring(third + 1));
        return pair;
    }
}
/*****************************************************************************************
 * End of Class: ToonCodec.java 082526
 ****************************************************************************************/
