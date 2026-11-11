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

/**
 * The {@code ParagraphFlow} type joins the lines of one paragraph into one run.
 * A blank line stays the break between paragraphs.
 * The type does not wrap to a column width.
 * The type has no instances.
 *
 * @author clayl (2026-11-04)
 */
final class ParagraphFlow {

    /**
     * The constructor is private so a caller uses {@link #flow}.
     */
    private ParagraphFlow() {
    }

    /**
     * The {@code flow} method turns single line breaks inside a paragraph into spaces.
     * A {@code null} or empty text returns the empty string.
     * A carriage return is skipped.
     * One line feed between characters becomes one space when neither side already has a space.
     * A line feed at the end of the text adds no space.
     * Two or more line feeds, ignoring carriage returns between them, end the paragraph and write two line feeds.
     * The method does not wrap to a width.
     * // @aa-sdd PK-0129 docs/specs/java/org/casserole/format/format.spec.md
     *
     * @param text The source text.
     * @return The flowed text.
     */
    static String flow(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder(text.length());
        StringBuilder para = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            char c = text.charAt(i);
            if (c == '\r') {
                i++;
                continue;
            }
            if (c == '\n') {
                int j = i + 1;
                int extra = 0;
                while (j < text.length()) {
                    char n = text.charAt(j);
                    if (n == '\r') {
                        j++;
                        continue;
                    }
                    if (n == '\n') {
                        extra++;
                        j++;
                        continue;
                    }
                    break;
                }
                if (extra > 0) {
                    appendParagraph(out, para);
                    out.append('\n');
                    out.append('\n');
                    para.setLength(0);
                    i = j;
                    continue;
                }
                if (j >= text.length()) {
                    i = j;
                    continue;
                }
                if (para.length() > 0
                        && para.charAt(para.length() - 1) != ' '
                        && text.charAt(j) != ' ') {
                    para.append(' ');
                }
                i = j;
                continue;
            }
            para.append(c);
            i++;
        }
        appendParagraph(out, para);
        return out.toString();
    }

    /**
     * The {@code appendParagraph} method appends one finished paragraph.
     * If the paragraph buffer is empty, the method writes nothing.
     * If the destination is non-empty and does not already end with a line feed, the method writes one line feed first.
     *
     * @param out The destination.
     * @param para The paragraph buffer.
     */
    private static void appendParagraph(StringBuilder out, StringBuilder para) {
        if (para.length() == 0) {
            return;
        }
        if (out.length() > 0 && out.charAt(out.length() - 1) != '\n') {
            out.append('\n');
        }
        out.append(para);
    }
}
/*****************************************************************************************
 * End of Class: ParagraphFlow.java 110426
 ****************************************************************************************/
