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

package org.casserole.ssh.shell;

/**
 * The {@code InputPage} class is one measurement of the unsent terminal page.
 * {@link PagePaint} and the cursor both read this result, so a hard newline cannot disagree about the row count.
 *
 * A row that fills the width places the next character on the next row. When the next buffer character is a hard newline, that newline is that placement and does not add a second row. The cursor never rests in the last column.
 * @aa-sdd PK-0759 docs/specs/java/org/roux/roux.spec.md
 * @aa-sdd PK-3462 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-10-25)
 */
final class InputPage {

    // @aa-sdd PK-0759 docs/specs/java/org/roux/roux.spec.md
    /**
     * The {@code Cell} class is the row and column where the cursor sits for one buffer index.
     * Index {@code length} is the position after the last character.
     */
    static final class Cell {
        final int row;
        final int col;

        /**
         * The constructor stores the row and the column for one buffer index.
         *
         * @param row The row is the zero-based visual row.
         * @param col The column is the zero-based column on that row.
         */
        Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    final int rows;
    final Cell[] cells;
    /**
     * The flags mark buffer indexes after which a carriage return and line feed are written.
     * The array has one entry for each character in the buffer.
     */
    final boolean[] breakAfter;

    /**
     * The constructor stores the row count, the cursor cells, and the break flags.
     *
     * @param rows The row count is the number of visual rows, including the row that holds the cursor.
     * @param cells The cells are the cursor position for each buffer index, including the end.
     * @param breakAfter The break flags mark where a new visual row begins after a character.
     */
    private InputPage(int rows, Cell[] cells, boolean[] breakAfter) {
        this.rows = rows;
        this.cells = cells;
        this.breakAfter = breakAfter;
    }

    /**
     * The {@code measure} method reads the buffer once and records a cursor cell for every index.
     * The width is at least {@code 1}. A negative prompt width is treated as {@code 0}. The prompt width itself wraps onto later rows before the first character.
     *
     * A hard newline is {@code \n} or {@code \r}. A hard newline moves to column {@code 0} of the next row, unless the previous character already filled the width and marked that newline as absorbed. An absorbed newline does not add a second row. A character that fills the width sets the break flag and moves to the next row.
     *
     * @param text The text is the buffer, including hard newlines.
     * @param promptVis The prompt width is the number of visible prompt columns before the buffer.
     * @param cols The column count is the wrap width. A value below {@code 1} becomes {@code 1}.
     * @return The return value is the measured page.
     */
    static InputPage measure(CharSequence text, int promptVis, int cols) {
        int width = cols < 1 ? 1 : cols;
        int n = text.length();
        Cell[] cells = new Cell[n + 1];
        boolean[] breakAfter = new boolean[n];
        int row = 0;
        int col = promptVis < 0 ? 0 : promptVis;
        while (col >= width) {
            row++;
            col -= width;
        }
        cells[0] = new Cell(row, col);
        boolean absorbed = false;
        for (int i = 0; i < n; i++) {
            char c = text.charAt(i);
            if (c == '\n' || c == '\r') {
                if (absorbed) {
                    absorbed = false;
                    cells[i + 1] = new Cell(row, col);
                    continue;
                }
                row++;
                col = 0;
                breakAfter[i] = true;
                cells[i + 1] = new Cell(row, col);
                continue;
            }
            col++;
            if (col >= width) {
                boolean nextHard = i + 1 < n && isBreak(text.charAt(i + 1));
                row++;
                col = 0;
                breakAfter[i] = true;
                absorbed = nextHard;
            } else {
                absorbed = false;
            }
            cells[i + 1] = new Cell(row, col);
        }
        return new InputPage(row + 1, cells, breakAfter);
    }

    /**
     * The {@code pageOpen} method reports whether the buffer contains a hard newline.
     * Up and Down move a visual row when this method returns true. A buffer with no {@code \n} and no {@code \r} returns false.
     *
     * @param text The text is the buffer to scan.
     * @return The return value is true when Up and Down should move a visual row.
     */
    static boolean pageOpen(CharSequence text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n' || c == '\r') {
                return true;
            }
        }
        return false;
    }

    /**
     * The {@code moveRow} method moves to the same column on the row {@code delta} steps away.
     * The cursor index is clamped into the cell array. A target row before the first row or past the last row returns the clamped index. A target row with no cells returns the clamped index.
     *
     * The wanted column is clamped to the columns that exist on the target row. The return value is the last buffer index on that row whose column matches.
     *
     * @param page The page is the measured page.
     * @param cursor The cursor is the current buffer index.
     * @param delta The delta is the row step, usually {@code -1} or {@code 1}.
     * @return The return value is the buffer index to sit on.
     */
    static int moveRow(InputPage page, int cursor, int delta) {
        if (page.cells.length == 0) {
            return 0;
        }
        int index = cursor;
        if (index < 0) {
            index = 0;
        }
        if (index >= page.cells.length) {
            index = page.cells.length - 1;
        }
        Cell here = page.cells[index];
        int target = here.row + delta;
        if (target < 0 || target >= page.rows) {
            return index;
        }
        int min = Integer.MAX_VALUE;
        int max = -1;
        for (int i = 0; i < page.cells.length; i++) {
            if (page.cells[i].row != target) {
                continue;
            }
            int column = page.cells[i].col;
            if (column < min) {
                min = column;
            }
            if (column > max) {
                max = column;
            }
        }
        if (max < 0) {
            return index;
        }
        int want = here.col;
        if (want < min) {
            want = min;
        }
        if (want > max) {
            want = max;
        }
        int best = index;
        for (int i = 0; i < page.cells.length; i++) {
            if (page.cells[i].row == target && page.cells[i].col == want) {
                best = i;
            }
        }
        return best;
    }

    /**
     * The method reports whether a character is a hard newline.
     * A hard newline is {@code \n} or {@code \r}.
     *
     * @param c The character is one buffer character.
     * @return The return value is true when the character is a hard newline.
     */
    private static boolean isBreak(char c) {
        return c == '\n' || c == '\r';
    }
}
