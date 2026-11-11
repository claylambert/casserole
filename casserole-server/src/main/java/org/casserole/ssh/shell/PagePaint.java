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
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-3462 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-10-25)
 */
final class PagePaint {

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
