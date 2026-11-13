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

package org.casserole.format;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code LineDiscipline} type rewrites text to VT100 line endings.
 * VT100 is the terminal control set the chat shell paints with.
 * The rewrite is applied on every path, including the path that does not match a format.
 * The type has no instances.
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-346 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-350 docs/specs/java/org/casserole/format/format.spec.md
public final class LineDiscipline {

    /**
     * The constructor is private so a caller uses {@link #toCrlf} and {@link #ensureTrailingCrlf}.
     */
    private LineDiscipline() {
    }

    /**
     * The {@code toCrlf} method rewrites line endings to carriage return plus line feed.
     * A {@code null} or empty text returns the empty string.
     * A carriage return that is already followed by a line feed is kept as that pair.
     * A lone carriage return gains a line feed.
     * A lone line feed becomes a carriage return plus a line feed.
     * Every other character is copied.
     *
     * @param text The source text.
     * @return The text with VT100 line endings, or the empty string when the source is missing.
     */
    public static String toCrlf(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder(text.length() + 16);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\r') {
                out.append('\r');
                if (i + 1 < text.length() && text.charAt(i + 1) == '\n') {
                    out.append('\n');
                    i++;
                } else {
                    out.append('\n');
                }
            } else if (c == '\n') {
                out.append("\r\n");
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

    /**
     * The {@code ensureTrailingCrlf} method makes a non-empty text end with a carriage return and a line feed.
     * A {@code null} or empty text returns the empty string.
     * Text that already ends with that pair is returned unchanged.
     * Text that ends with a lone line feed replaces that line feed with the pair.
     * Any other non-empty text gains the pair at the end.
     *
     * @param text The source text.
     * @return The text ending with a carriage return and a line feed, or the empty string when the source is missing.
     */
    public static String ensureTrailingCrlf(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        if (text.endsWith("\r\n")) {
            return text;
        }
        if (text.endsWith("\n")) {
            return text.substring(0, text.length() - 1) + "\r\n";
        }
        return text + "\r\n";
    }
}
/*****************************************************************************************
 * End of Class: LineDiscipline.java 082526
 ****************************************************************************************/
