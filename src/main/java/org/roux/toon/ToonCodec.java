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

// @aa-sdd PK-0792 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-4473 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/**
 * The ToonCodec writes and reads the text payload of one message.
 * One encoded payload starts with the line {@code m[1]{r,ts,tok,x}:}.
 * The next line is the role word, the time, the token count, and the text, separated by commas.
 * {@link #decode} keeps the role, the time, and the token count from the frame header.
 * The text comes from the payload.
 *
 * The type refuses construction.
 *
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
