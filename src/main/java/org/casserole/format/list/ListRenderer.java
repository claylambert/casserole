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

import org.casserole.format.FormatContext;
import org.casserole.format.FormatRenderer;
import org.casserole.format.FormatResult;
import org.casserole.format.WordWrap;

/**
 * The {@code ListRenderer} type paints a hanging-indent list.
 * Each item is rewritten with a hyphen and a space.
 * The item body is wrapped to the column width minus two.
 * Continuation lines start with two spaces.
 *
 * @author clayl (2026-08-25)
 */
public class ListRenderer implements FormatRenderer {

    /**
     * The {@code render} method rewrites every non-blank line as a hanging bullet.
     * If {@code ctx} is {@code null}, the body width is {@code 78}.
     * Otherwise the body width is {@link FormatContext#getColumns} minus two.
     * If that width is below {@code 8}, the width becomes {@code 8}.
     * Carriage returns are normalized to line feeds before the split.
     * A blank line is skipped.
     * {@link #stripMarker} removes the original marker, and {@link WordWrap#wrap} wraps the body.
     * The first wrapped line is prefixed with {@code - }.
     * Later wrapped lines are prefixed with two spaces.
     * Each painted line ends with a line feed.
     * The result has body color on.
     *
     * @param raw The original assistant text.
     * @param ctx The render settings, or {@code null} to wrap bodies at {@code 78} columns.
     * @return The hanging-indent list.
     */
    @Override
    public FormatResult render(String raw, FormatContext ctx) {
        int width = ctx == null ? 78 : ctx.getColumns() - 2;
        if (width < 8) {
            width = 8;
        }
        String[] lines = raw.replace("\r\n", "\n").replace('\r', '\n').split("\n", -1);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.trim().length() == 0) {
                continue;
            }
            String body = stripMarker(line);
            String wrapped = WordWrap.wrap(body, width);
            String[] parts = wrapped.split("\n", -1);
            for (int p = 0; p < parts.length; p++) {
                if (p == 0) {
                    out.append("- ").append(parts[p]).append('\n');
                } else {
                    out.append("  ").append(parts[p]).append('\n');
                }
            }
        }
        return new FormatResult(out.toString(), true);
    }

    /**
     * The {@code stripMarker} method returns the text after a list marker.
     * Leading spaces and tabs are skipped.
     * A marker of {@code -}, {@code *}, or {@code +} is removed, and one following space or tab is removed when present.
     * If the line is not a bullet, leading digits are skipped, then one {@code .} or {@code )} is skipped, then one space or tab is skipped.
     * The remainder of the line is returned.
     *
     * @param line One item line.
     * @return The text after the marker.
     */
    private String stripMarker(String line) {
        int i = 0;
        while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
            i++;
        }
        if (i < line.length() && (line.charAt(i) == '-' || line.charAt(i) == '*' || line.charAt(i) == '+')) {
            i++;
            if (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                i++;
            }
            return line.substring(i);
        }
        while (i < line.length() && line.charAt(i) >= '0' && line.charAt(i) <= '9') {
            i++;
        }
        if (i < line.length() && (line.charAt(i) == '.' || line.charAt(i) == ')')) {
            i++;
        }
        if (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
            i++;
        }
        return line.substring(i);
    }
}
/*****************************************************************************************
 * End of Class: ListRenderer.java 082526
 ****************************************************************************************/
