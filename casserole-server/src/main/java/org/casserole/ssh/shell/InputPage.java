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
 * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
 * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
 * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
 * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
 * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
 * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
 * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
 * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
 * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
 * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
 * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
 * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
 * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
 * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
 * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
 * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
 * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
 * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
 * // @aa-sdd PK-3462 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 *
 * @author clayl (2026-10-25)
 */
final class InputPage {

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
