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

package org.casserole.format.list;

import org.casserole.format.FormatDetector;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code ListDetector} type matches a reply whose every non-empty line is a list item.
 * An item is a bullet or a numbered line.
 * The reply must contain at least two items.
 * One non-item line rejects the reply.
 *
 * @author clayl (2026-08-25)
 */
public class ListDetector implements FormatDetector {

    /**
     * The {@code matches} method tests whether the reply is a list of at least two items.
     * A {@code null} reply returns {@code false}.
     * Blank lines are skipped.
     * If any non-blank line fails {@link #isItem}, the method returns {@code false}.
     * If fewer than two items remain, the method returns {@code false}.
     *
     * @param raw The full assistant text.
     * @return {@code true} when every non-blank line is an item and at least two items are present.
     */
    @Override
    public boolean matches(String raw) {
        if (raw == null) {
            return false;
        }
        String[] lines = raw.replace("\r\n", "\n").replace('\r', '\n').split("\n", -1);
        int items = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.trim().length() == 0) {
                continue;
            }
            if (!isItem(line)) {
                return false;
            }
            items++;
        }
        return items >= 2;
    }

    /**
     * The {@code isItem} method tests one line as a bullet or a numbered item.
     * Leading spaces and tabs are skipped.
     * A line that is only whitespace returns {@code false}.
     * A bullet is {@code -}, {@code *}, or {@code +}, then a space or tab, then at least one more character.
     * A numbered item is one or more digits, then {@code .} or {@code )}, then a space or tab, then at least one more character.
     * Any other line returns {@code false}.
     *
     * @param line One line of the reply.
     * @return {@code true} when the line is a bullet or a numbered item.
     */
    static boolean isItem(String line) {
        int i = 0;
        while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
            i++;
        }
        if (i >= line.length()) {
            return false;
        }
        char c = line.charAt(i);
        if (c == '-' || c == '*' || c == '+') {
            return i + 1 < line.length() && (line.charAt(i + 1) == ' ' || line.charAt(i + 1) == '\t')
                    && i + 2 < line.length();
        }
        int digits = 0;
        while (i < line.length() && line.charAt(i) >= '0' && line.charAt(i) <= '9') {
            digits++;
            i++;
        }
        if (digits == 0 || i >= line.length()) {
            return false;
        }
        char mark = line.charAt(i);
        if (mark != '.' && mark != ')') {
            return false;
        }
        i++;
        if (i >= line.length() || (line.charAt(i) != ' ' && line.charAt(i) != '\t')) {
            return false;
        }
        i++;
        return i < line.length();
    }
}
/*****************************************************************************************
 * End of Class: ListDetector.java 082526
 ****************************************************************************************/
