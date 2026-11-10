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

package org.casserole.format.table;

import java.util.List;

import org.casserole.format.FormatContext;

// @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
// @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
// @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
// @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
// @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
// @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
// @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
// @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
// @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
// @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
// @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
// @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
// @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
// @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
// @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
// @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
// @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
// @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
/**
 * The {@code TextTableDrawer} type draws a {@link TableModel} as text.
 * The ASCII style uses {@code +}, {@code -}, and {@code |}.
 * The box style uses Unicode box-drawing characters.
 * Optional SGR sequences color the border, the header, and the body cells.
 * SGR is the select-graphic-rendition sequence that sets terminal color.
 *
 * @author clayl (2026-08-25)
 */
public class TextTableDrawer {

    /**
     * The {@code draw} method paints one table and separates the lines with line feeds.
     * If the model has no columns, the method returns the empty string.
     * The column budget is {@link FormatContext#getColumns}, and a budget below {@code 16} becomes {@code 16}.
     * {@link #fillWidths} measures the cells, and {@link #shrinkWidths} reduces the widest columns until the table fits.
     * The style {@code box}, ignoring case, selects the Unicode box characters.
     * Any other style selects ASCII.
     * The painted order is a top rule, the header, a middle rule, each data row, and a bottom rule.
     *
     * @param model The table to draw.
     * @param ctx The column budget, the style, and the SGR sequences.
     * @return The drawn table, with a line feed after every rule and every row.
     */
    public String draw(TableModel model, FormatContext ctx) {
        int cols = model.getColumnCount();
        if (cols < 1) {
            return "";
        }
        int budget = ctx.getColumns();
        if (budget < 16) {
            budget = 16;
        }
        int[] widths = new int[cols];
        fillWidths(model, widths);
        shrinkWidths(widths, budget);
        boolean box = ctx.getTableStyle() != null && ctx.getTableStyle().equalsIgnoreCase("box");
        StringBuilder out = new StringBuilder();
        String border = ctx.getSgrBorder();
        String header = ctx.getSgrHeader();
        String cell = ctx.getSgrCell();
        String reset = ctx.getSgrReset();
        out.append(rule(widths, box, true, false, border, reset));
        out.append('\n');
        out.append(dataRow(model.getHeader(), widths, box, header, border, reset));
        out.append('\n');
        out.append(rule(widths, box, false, false, border, reset));
        out.append('\n');
        List<List<String>> rows = model.getRows();
        for (int r = 0; r < rows.size(); r++) {
            out.append(dataRow(rows.get(r), widths, box, cell, border, reset));
            out.append('\n');
        }
        out.append(rule(widths, box, false, true, border, reset));
        out.append('\n');
        return out.toString();
    }

    /**
     * The {@code fillWidths} method stores the widest text length in each column.
     * The header length is the starting width.
     * A data cell that is longer replaces that width.
     * A column whose measured width is below one is stored as one.
     *
     * @param model The table being measured.
     * @param widths The array that receives one width per column.
     */
    private void fillWidths(TableModel model, int[] widths) {
        for (int c = 0; c < widths.length; c++) {
            widths[c] = model.getHeader().get(c).length();
        }
        List<List<String>> rows = model.getRows();
        for (int r = 0; r < rows.size(); r++) {
            List<String> row = rows.get(r);
            for (int c = 0; c < widths.length && c < row.size(); c++) {
                int len = row.get(c).length();
                if (len > widths[c]) {
                    widths[c] = len;
                }
            }
        }
        for (int c = 0; c < widths.length; c++) {
            if (widths[c] < 1) {
                widths[c] = 1;
            }
        }
    }

    /**
     * The {@code shrinkWidths} method reduces column widths until the table fits the budget.
     * The chrome cost is one plus three times the column count.
     * The available content width is the budget minus that chrome.
     * If that available width is below the column count, it becomes the column count.
     * While the widths sum to more than the available width, the widest column loses one.
     * If every widest column is already one, the method returns and leaves the overflow.
     *
     * @param widths The column widths, changed in place.
     * @param budget The terminal column count, including borders.
     */
    private void shrinkWidths(int[] widths, int budget) {
        int chrome = 1 + widths.length * 3;
        int available = budget - chrome;
        if (available < widths.length) {
            available = widths.length;
        }
        while (sum(widths) > available) {
            int maxI = 0;
            for (int i = 1; i < widths.length; i++) {
                if (widths[i] > widths[maxI]) {
                    maxI = i;
                }
            }
            if (widths[maxI] <= 1) {
                return;
            }
            widths[maxI]--;
        }
    }

    /**
     * The {@code sum} method adds the column widths.
     *
     * @param widths The column widths.
     * @return The total of those widths.
     */
    private int sum(int[] widths) {
        int s = 0;
        for (int i = 0; i < widths.length; i++) {
            s += widths[i];
        }
        return s;
    }

    /**
     * The {@code rule} method builds one horizontal rule.
     * If {@code box} is {@code true}, the line uses Unicode box characters, with a different corner set for the top, the bottom, and a middle rule.
     * If {@code box} is {@code false}, the line uses {@code +} and {@code -}.
     * Each column contributes its width plus two dash characters.
     * A joint character is written between columns.
     * The border SGR sequence is written before the rule, and the reset sequence is written after it.
     *
     * @param widths The column widths.
     * @param box {@code true} for Unicode box characters, or {@code false} for ASCII.
     * @param top {@code true} when this is the top rule.
     * @param bottom {@code true} when this is the bottom rule.
     * @param sgr The border color sequence.
     * @param reset The sequence that clears color.
     * @return One rule line, without a trailing line feed.
     */
    private String rule(int[] widths, boolean box, boolean top, boolean bottom,
            String sgr, String reset) {
        char left;
        char mid;
        char right;
        char dash;
        if (box) {
            dash = '\u2500';
            if (top) {
                left = '\u250C';
                mid = '\u252C';
                right = '\u2510';
            } else if (bottom) {
                left = '\u2514';
                mid = '\u2534';
                right = '\u2518';
            } else {
                left = '\u251C';
                mid = '\u253C';
                right = '\u2524';
            }
        } else {
            dash = '-';
            left = '+';
            mid = '+';
            right = '+';
        }
        StringBuilder sb = new StringBuilder();
        sb.append(sgr);
        sb.append(left);
        for (int c = 0; c < widths.length; c++) {
            for (int i = 0; i < widths[c] + 2; i++) {
                sb.append(dash);
            }
            if (c < widths.length - 1) {
                sb.append(mid);
            }
        }
        sb.append(right);
        sb.append(reset);
        return sb.toString();
    }

    /**
     * The {@code dataRow} method builds one header or data line.
     * If {@code box} is {@code true}, the vertical bar is the Unicode box bar.
     * If {@code box} is {@code false}, the vertical bar is {@code |}.
     * A missing cell is an empty string.
     * A cell longer than its width is cut to that width.
     * The cell is padded with spaces out to the width, with one space on each side.
     * The border sequence colors each bar, and the cell sequence colors the padded text.
     * The reset sequence is written after each colored span.
     *
     * @param cells The row cells.
     * @param widths The column widths.
     * @param box {@code true} for a Unicode bar, or {@code false} for {@code |}.
     * @param cellSgr The color sequence for the cell text.
     * @param borderSgr The color sequence for the bars.
     * @param reset The sequence that clears color.
     * @return One data line, without a trailing line feed.
     */
    private String dataRow(List<String> cells, int[] widths, boolean box,
            String cellSgr, String borderSgr, String reset) {
        char bar = box ? '\u2502' : '|';
        StringBuilder sb = new StringBuilder();
        sb.append(borderSgr);
        sb.append(bar);
        sb.append(reset);
        for (int c = 0; c < widths.length; c++) {
            String value = c < cells.size() ? cells.get(c) : "";
            if (value.length() > widths[c]) {
                value = value.substring(0, widths[c]);
            }
            sb.append(cellSgr);
            sb.append(' ');
            sb.append(value);
            for (int p = value.length(); p < widths[c]; p++) {
                sb.append(' ');
            }
            sb.append(' ');
            sb.append(reset);
            sb.append(borderSgr);
            sb.append(bar);
            sb.append(reset);
        }
        return sb.toString();
    }
}
/*****************************************************************************************
 * End of Class: TextTableDrawer.java 082526
 ****************************************************************************************/
