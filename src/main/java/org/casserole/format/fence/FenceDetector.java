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

package org.casserole.format.fence;

import org.casserole.format.FormatDetector;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code FenceDetector} type matches a reply that contains one fenced block with a matching closer.
 * A fence is a line of three backticks or three tildes.
 * An opening fence with no later closer does not match.
 *
 * @author clayl (2026-08-25)
 */
public class FenceDetector implements FormatDetector {

    /**
     * The {@code matches} method looks for an opening fence and a later closer that uses the same mark.
     * A {@code null} reply returns {@code false}.
     * The first fence line becomes the opener.
     * A later line with the same mark returns {@code true}.
     * A different mark does not close the opener.
     * If no closer appears, the method returns {@code false}.
     *
     * @param raw The full assistant text.
     * @return {@code true} when a fence has a matching closer later in the reply.
     */
    @Override
    public boolean matches(String raw) {
        if (raw == null) {
            return false;
        }
        String[] lines = splitLines(raw);
        int opener = -1;
        String mark = null;
        for (int i = 0; i < lines.length; i++) {
            String fence = fenceMark(lines[i]);
            if (fence == null) {
                continue;
            }
            if (opener < 0) {
                opener = i;
                mark = fence;
            } else if (fence.equals(mark) && i > opener) {
                return true;
            }
        }
        return false;
    }

    /**
     * The {@code fenceMark} method classifies one line as a fence.
     * The line is trimmed before the test.
     * A line that starts with three backticks returns three backticks.
     * A line that starts with three tildes returns three tildes.
     * Any other line returns {@code null}.
     *
     * @param line One line of the reply.
     * @return Three backticks, three tildes, or {@code null}.
     */
    static String fenceMark(String line) {
        String t = line.trim();
        if (t.startsWith("```")) {
            for (int i = 0; i < t.length(); i++) {
                char c = t.charAt(i);
                if (c != '`' && i < 3) {
                    return null;
                }
            }
            return "```";
        }
        if (t.startsWith("~~~")) {
            return "~~~";
        }
        return null;
    }

    /**
     * The {@code splitLines} method splits a reply into lines.
     * A carriage-return line-feed pair becomes a line feed first.
     * A lone carriage return also becomes a line feed.
     * The split keeps a trailing empty line.
     *
     * @param raw The reply text.
     * @return The lines, including a trailing empty line when the text ends with a line break.
     */
    static String[] splitLines(String raw) {
        return raw.replace("\r\n", "\n").replace('\r', '\n').split("\n", -1);
    }
}
/*****************************************************************************************
 * End of Class: FenceDetector.java 082526
 ****************************************************************************************/
