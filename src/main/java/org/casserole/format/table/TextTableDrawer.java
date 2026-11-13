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

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
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
