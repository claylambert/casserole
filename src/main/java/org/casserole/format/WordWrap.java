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
 * The {@code WordWrap} type hard-wraps text at spaces.
 * Structured renderers call it.
 * The unmatched identity path does not call it.
 * The type has no instances.
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-1333 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-774 docs/specs/common/stages.spec.md
// @aa-sdd PK-696 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-350 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-654 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-655 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-774 docs/specs/common/stages.spec.md
// @aa-sdd PK-344 docs/specs/java/org/casserole/format/format.spec.md
public final class WordWrap {

    /**
     * The constructor is private so a caller uses {@link #wrap}.
     */
    private WordWrap() {
    }

    /**
     * The {@code wrap} method flows paragraphs and then hard-wraps each run.
     * A {@code null} or empty text returns the empty string.
     * {@link ParagraphFlow#flow} keeps a blank line as a paragraph break and turns a single line feed into a space.
     * If the flowed text is empty, the method returns the empty string.
     * A width below {@code 8} becomes {@code 8}.
     * A carriage return is skipped.
     * Each flowed line, including the end of the text, is passed to {@link #flushLine}.
     * A line feed in the flowed text is written after that line.
     * The result uses line feeds, not carriage returns.
     * // @aa-sdd PK-1075 docs/specs/common/formal-plan.spec.md
     *
     * @param text The source text.
     * @param width The column width.
     * @return The wrapped text, using line feeds.
     */
    public static String wrap(String text, int width) {
        if (text == null || text.length() == 0) {
            return "";
        }
        text = ParagraphFlow.flow(text);
        if (text.length() == 0) {
            return "";
        }
        if (width < 8) {
            width = 8;
        }
        StringBuilder out = new StringBuilder(text.length() + 32);
        StringBuilder line = new StringBuilder();
        int i = 0;
        while (i <= text.length()) {
            boolean end = i == text.length();
            char c = end ? '\n' : text.charAt(i);
            if (c == '\r') {
                i++;
                continue;
            }
            if (end || c == '\n') {
                flushLine(out, line.toString(), width);
                if (!end) {
                    out.append('\n');
                }
                line.setLength(0);
                i++;
                continue;
            }
            line.append(c);
            i++;
        }
        return out.toString();
    }

    /**
     * The {@code flushLine} method wraps one flowed line at word boundaries.
     * An empty line writes nothing.
     * A space or tab is copied as one space only when the current column is already past zero and still under the width.
     * If the next word does not fit on the current column, the method writes a line feed and starts at column zero.
     * If the column is already past zero and the word fits, the method writes one space before the word.
     * A word longer than the width, starting at column zero, is cut into pieces of that width and a line feed is written between pieces.
     * A shorter word is written whole.
     *
     * @param out The destination.
     * @param line One flowed line.
     * @param width The column width.
     */
    private static void flushLine(StringBuilder out, String line, int width) {
        if (line.length() == 0) {
            return;
        }
        int col = 0;
        int i = 0;
        while (i < line.length()) {
            while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                if (col > 0 && col < width) {
                    out.append(' ');
                    col++;
                }
                i++;
            }
            if (i >= line.length()) {
                return;
            }
            int wordEnd = i;
            while (wordEnd < line.length()
                    && line.charAt(wordEnd) != ' '
                    && line.charAt(wordEnd) != '\t') {
                wordEnd++;
            }
            int wordLen = wordEnd - i;
            if (col > 0 && col + 1 + wordLen > width) {
                out.append('\n');
                col = 0;
            } else if (col > 0) {
                out.append(' ');
                col++;
            }
            if (wordLen > width && col == 0) {
                while (i < wordEnd) {
                    int take = wordEnd - i;
                    if (take > width) {
                        take = width;
                    }
                    out.append(line.substring(i, i + take));
                    i += take;
                    if (i < wordEnd) {
                        out.append('\n');
                    } else {
                        col = take;
                    }
                }
            } else {
                out.append(line.substring(i, wordEnd));
                col += wordLen;
                i = wordEnd;
            }
        }
    }
}
/*****************************************************************************************
 * End of Class: WordWrap.java 082526
 ****************************************************************************************/
