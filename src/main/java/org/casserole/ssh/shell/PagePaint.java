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
 * The {@code PagePaint} class builds the bytes that take the screen from one input page to the next.
 * A glyph edit rewrites the damaged tail and returns that rewrite as one string. Erase-to-end of the screen stays off this path.
 * @aa-sdd PK-0759 docs/specs/java/org/roux/roux.spec.md
 * @aa-sdd PK-3462 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-10-25)
 */
final class PagePaint {

    // @aa-sdd PK-0759 docs/specs/java/org/roux/roux.spec.md
    /**
     * The {@code Patch} class is one flush of terminal bytes plus the cursor landing place.
     * The cursor cell is the {@link InputPage} cell for the cursor index.
     */
    static final class Patch {
        final String ansi;
        final int rows;
        final int cursorRow;
        final int cursorCol;

        /**
         * The constructor stores the flush text, the row count, and the cursor cell.
         *
         * @param ansi The text is the terminal bytes for this flush.
         * @param rows The row count is the number of rows in the page after the edit.
         * @param cursorRow The cursor row is the visual row inside that page.
         * @param cursorCol The cursor column is the visual column inside that page.
         */
        private Patch(String ansi, int rows, int cursorRow, int cursorCol) {
            this.ansi = ansi;
            this.rows = rows;
            this.cursorRow = cursorRow;
            this.cursorCol = cursorCol;
        }
    }

    private final StringBuilder out = new StringBuilder();
    private final boolean commandGreen;
    private int row;
    private int col;

    /**
     * The constructor stores the cursor row, the cursor column, and the command-green flag.
     *
     * @param row The row is the cursor row already on the screen.
     * @param col The column is the cursor column already on the screen.
     * @param commandGreen The flag is true when each echoed glyph is wrapped in command green.
     */
    private PagePaint(int row, int col, boolean commandGreen) {
        this.row = row;
        this.col = col;
        this.commandGreen = commandGreen;
    }

    /**
     * The {@code update} method builds one flush from the text already on screen to the text the screen should show.
     * A null text is treated as empty. Equal texts move the cursor and do not rewrite glyphs. Unequal texts rewrite from the first damaged index.
     *
     * A hard newline erases to the end of the line before the row break. A wrap that is not a hard newline does not erase that line. Extra rows that the old page still has are deleted. The cursor then moves to the landing cell.
     *
     * @param beforeText The before text is the text already on screen.
     * @param beforeCursor The before cursor is the cursor index already on screen.
     * @param screenRow The screen row is the cursor row inside that page.
     * @param screenCol The screen column is the cursor column inside that page.
     * @param afterText The after text is the text the screen should show.
     * @param afterCursor The after cursor is the cursor index to land on.
     * @param promptVis The prompt width is the number of visible prompt columns.
     * @param cols The column count is the wrap width.
     * @param commandGreen The flag is true when each echoed glyph is wrapped in command green.
     * @return The return value is the patch.
     */
    static Patch update(
            String beforeText,
            int beforeCursor,
            int screenRow,
            int screenCol,
            String afterText,
            int afterCursor,
            int promptVis,
            int cols,
            boolean commandGreen) {
        String before = beforeText == null ? "" : beforeText;
        String after = afterText == null ? "" : afterText;
        InputPage beforePage = InputPage.measure(before, promptVis, cols);
        InputPage afterPage = InputPage.measure(after, promptVis, cols);
        int rows = afterPage.rows;
        InputPage.Cell landed = afterPage.cells[clamp(afterCursor, afterPage.cells.length)];
        PagePaint paint = new PagePaint(screenRow, screenCol, commandGreen);
        if (before.equals(after)) {
            paint.moveTo(landed.row, landed.col);
            return new Patch(paint.out.toString(), rows, landed.row, landed.col);
        }
        int damage = damageIndex(before, after, beforePage, afterPage);
        int writeAt = damage;
        if (writeAt > after.length()) {
            writeAt = after.length();
        }
        InputPage.Cell start = afterPage.cells[writeAt];
        paint.moveTo(start.row, start.col);
        boolean wrapped = false;
        for (int i = writeAt; i < after.length(); i++) {
            char c = after.charAt(i);
            boolean hard = c == '\n' || c == '\r';
            if (!hard) {
                paint.glyph(c);
                if (!afterPage.breakAfter[i]) {
                    paint.col++;
                }
            }
            if (afterPage.breakAfter[i]) {
                if (hard) {
                    paint.el();
                }
                paint.newLine();
                wrapped = !hard;
            } else {
                wrapped = false;
            }
        }
        if (!wrapped && paint.col > 0) {
            paint.el();
        }
        int extra = beforePage.rows - afterPage.rows;
        if (extra > 0) {
            paint.moveTo(afterPage.rows, 0);
            paint.out.append(Ansi.dl(extra));
        }
        paint.moveTo(landed.row, landed.col);
        return new Patch(paint.out.toString(), rows, landed.row, landed.col);
    }

    /**
     * The method returns the first index where the texts or their row breaks differ.
     * The scan stops at the shorter length. Equal texts return that shared length.
     *
     * @param before The before text is the text already on screen.
     * @param after The after text is the text the screen should show.
     * @param beforePage The before page is the measurement of the old text.
     * @param afterPage The after page is the measurement of the new text.
     * @return The return value is the first damaged buffer index.
     */
    private static int damageIndex(String before, String after, InputPage beforePage, InputPage afterPage) {
        int n = before.length();
        if (after.length() < n) {
            n = after.length();
        }
        for (int i = 0; i < n; i++) {
            if (before.charAt(i) != after.charAt(i)) {
                return i;
            }
            if (beforePage.breakAfter[i] != afterPage.breakAfter[i]) {
                return i;
            }
        }
        return n;
    }

    /**
     * The method clamps an index into a cell array.
     * A negative index becomes {@code 0}. An index at or past the length becomes the last cell.
     *
     * @param index The index is the requested cell.
     * @param length The length is the number of cells.
     * @return The return value is an index inside the array.
     */
    private static int clamp(int index, int length) {
        if (index < 0) {
            return 0;
        }
        if (index >= length) {
            return length - 1;
        }
        return index;
    }

    /**
     * The method appends the cursor motion from the current cell to the target cell.
     * A larger row number moves the cursor down. A smaller row number moves the cursor up. The same column stops there.
     *
     * A move to the right on the same row uses cursor-forward. A move to the left on the same row uses cursor-back. Any other column returns to column {@code 0} and then moves forward.
     *
     * @param targetRow The target row is the visual row to land on.
     * @param targetCol The target column is the visual column to land on.
     */
    private void moveTo(int targetRow, int targetCol) {
        if (targetRow > row) {
            out.append(Ansi.cud(targetRow - row));
            row = targetRow;
        } else if (targetRow < row) {
            out.append(Ansi.cuu(row - targetRow));
            row = targetRow;
        }
        if (targetCol == col) {
            return;
        }
        if (targetRow == row && targetCol > col) {
            out.append(Ansi.cuf(targetCol - col));
            col = targetCol;
            return;
        }
        if (targetRow == row && targetCol < col) {
            out.append(Ansi.cub(col - targetCol));
            col = targetCol;
            return;
        }
        out.append(Ansi.CR);
        col = 0;
        if (targetCol > 0) {
            out.append(Ansi.cuf(targetCol));
        }
        col = targetCol;
    }

    /**
     * The method appends one character.
     * Command green wraps the character in green and then resets the color.
     *
     * @param c The character is the glyph to write.
     */
    private void glyph(char c) {
        if (commandGreen) {
            out.append(Ansi.GREEN);
        }
        out.append(c);
        if (commandGreen) {
            out.append(Ansi.RESET);
        }
    }

    /**
     * The method appends erase-to-end-of-line.
     * The sequence is {@link Ansi#EL}.
     */
    private void el() {
        out.append(Ansi.EL);
    }

    /**
     * The method appends a carriage return and a line feed.
     * The painter then sits at column {@code 0} of the next row.
     */
    private void newLine() {
        out.append("\r\n");
        row++;
        col = 0;
    }
}
