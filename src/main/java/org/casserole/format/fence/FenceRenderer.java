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

import org.casserole.format.FormatContext;
import org.casserole.format.FormatRenderer;
import org.casserole.format.FormatResult;
import org.casserole.format.WordWrap;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code FenceRenderer} type wraps the prose around a fence and leaves the fenced lines unwrapped.
 * A fence is a line of three backticks or three tildes.
 * Lines inside a matched fence are copied with a trailing line feed.
 *
 * @author clayl (2026-08-25)
 */
public class FenceRenderer implements FormatRenderer {

    /**
     * The {@code render} method wraps prose and copies fenced lines.
     * If {@code ctx} is {@code null}, the wrap width is {@code 80}.
     * Otherwise the wrap width is {@link FormatContext#getColumns}.
     * Prose collected before an opening fence is wrapped by {@link #flushProse} when that fence starts.
     * The opening line, the inner lines, and the closer that uses the same mark are copied with a trailing line feed.
     * A different mark inside an open fence is copied as an inner line.
     * If a fence is still open at the end, no extra prose buffer is wrapped.
     * If no fence is open at the end, the remaining prose is wrapped.
     * The result has body color on.
     *
     * @param raw The original assistant text.
     * @param ctx The render settings, or {@code null} to wrap at {@code 80} columns.
     * @return The mixed wrapped prose and unwrapped fence text.
     */
    @Override
    public FormatResult render(String raw, FormatContext ctx) {
        int width = ctx == null ? 80 : ctx.getColumns();
        String[] lines = FenceDetector.splitLines(raw);
        StringBuilder out = new StringBuilder();
        StringBuilder prose = new StringBuilder();
        boolean inFence = false;
        String mark = null;
        for (int i = 0; i < lines.length; i++) {
            String fence = FenceDetector.fenceMark(lines[i]);
            if (!inFence && fence != null) {
                flushProse(out, prose, width);
                inFence = true;
                mark = fence;
                out.append(lines[i]).append('\n');
                continue;
            }
            if (inFence && fence != null && fence.equals(mark)) {
                out.append(lines[i]).append('\n');
                inFence = false;
                mark = null;
                continue;
            }
            if (inFence) {
                out.append(lines[i]).append('\n');
            } else {
                if (prose.length() > 0) {
                    prose.append('\n');
                }
                prose.append(lines[i]);
            }
        }
        if (inFence) {
            flushProse(out, new StringBuilder(), width);
        } else {
            flushProse(out, prose, width);
        }
        return new FormatResult(out.toString(), true);
    }

    /**
     * The {@code flushProse} method wraps accumulated prose and appends it.
     * If {@code prose} is empty, the method returns without writing.
     * {@link WordWrap#wrap} receives {@link Integer#MAX_VALUE}, so a paragraph is not broken at {@code width}.
     * If the destination does not already end with a line feed, the method appends one.
     * The prose buffer is then cleared.
     *
     * @param out The destination that receives the wrapped prose.
     * @param prose The accumulated prose.
     * @param width The column width passed to {@link WordWrap#wrap}.
     */
    private void flushProse(StringBuilder out, StringBuilder prose, int width) {
        if (prose.length() == 0) {
            return;
        }
        out.append(WordWrap.wrap(prose.toString(), Integer.MAX_VALUE));
        if (out.length() > 0 && out.charAt(out.length() - 1) != '\n') {
            out.append('\n');
        }
        prose.setLength(0);
    }
}
/*****************************************************************************************
 * End of Class: FenceRenderer.java 082526
 ****************************************************************************************/
