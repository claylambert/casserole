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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * The class checks the row math in {@link InputPage}.
 * The test does not open a pseudo-terminal.
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
 *
 * @author clayl (2026-10-25)
 */
class InputPageTest {

    /**
     * The method arranges the text {@code a}, {@code b}, and {@code c} on three lines, with a prompt of {@code 6} and {@code 80} columns.
     * The method calls {@link InputPage#measure}.
     * The page has three rows.
     * The first cell is row {@code 0} and column {@code 6}.
     * The last cell is row {@code 2} and column {@code 1}.
     * The page records two breaks.
     *
     * @return The method returns nothing.
     */
    @Test
    void threeLinesCountThreeRows() {
        InputPage page = InputPage.measure("a\nb\nc", 6, 80);
        assertEquals(3, page.rows);
        assertEquals(0, page.cells[0].row);
        assertEquals(6, page.cells[0].col);
        assertEquals(2, page.cells[page.cells.length - 1].row);
        assertEquals(1, page.cells[page.cells.length - 1].col);
        assertEquals(2, breaks(page));
    }

    /**
     * The method arranges twenty digits, a prompt of {@code 6}, and {@code 20} columns.
     * The method calls {@link InputPage#measure}.
     * The page has two rows.
     * Cell {@code 14} is row {@code 1} and column {@code 0}.
     * Cell {@code 20} is row {@code 1} and column {@code 6}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aLongRowWrapsUnderThePrompt() {
        InputPage page = InputPage.measure("01234567890123456789", 6, 20);
        assertEquals(2, page.rows);
        assertEquals(1, page.cells[14].row);
        assertEquals(0, page.cells[14].col);
        assertEquals(1, page.cells[20].row);
        assertEquals(6, page.cells[20].col);
    }

    /**
     * The method arranges ten digits, a prompt of {@code 0}, and {@code 10} columns.
     * The method calls {@link InputPage#measure}.
     * The page has two rows.
     * Cell {@code 10} is row {@code 1} and column {@code 0}.
     * The break after index {@code 9} is true.
     *
     * @return The method returns nothing.
     */
    @Test
    void exactWidthParksOffTheLastColumn() {
        InputPage page = InputPage.measure("0123456789", 0, 10);
        assertEquals(2, page.rows);
        assertEquals(1, page.cells[10].row);
        assertEquals(0, page.cells[10].col);
        assertTrue(page.breakAfter[9]);
    }

    /**
     * The method arranges ten digits, a line feed, and {@code B}, with {@code 10} columns.
     * The method calls {@link InputPage#measure}.
     * The page has two rows.
     * The page records one break.
     * Cell {@code 10} and cell {@code 11} are both row {@code 1} and column {@code 0}.
     * Cell {@code 12} is column {@code 1}.
     *
     * @return The method returns nothing.
     */
    @Test
    void exactWidthThenNewlineIsOneBreak() {
        InputPage page = InputPage.measure("0123456789\nB", 0, 10);
        assertEquals(2, page.rows);
        assertEquals(1, breaks(page));
        assertEquals(1, page.cells[10].row);
        assertEquals(0, page.cells[10].col);
        assertEquals(1, page.cells[11].row);
        assertEquals(0, page.cells[11].col);
        assertEquals(1, page.cells[12].col);
    }

    /**
     * The method arranges {@code ab} and a trailing line feed, with a prompt of {@code 6}.
     * The method calls {@link InputPage#measure}.
     * The page has two rows.
     * The last cell is row {@code 1} and column {@code 0}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aTrailingNewlineKeepsItsRow() {
        InputPage page = InputPage.measure("ab\n", 6, 80);
        assertEquals(2, page.rows);
        assertEquals(1, page.cells[page.cells.length - 1].row);
        assertEquals(0, page.cells[page.cells.length - 1].col);
    }

    /**
     * The method arranges {@code ab} plus a line feed plus {@code c}, and the joined text {@code abc}.
     * The method calls {@link InputPage#measure} with a prompt of {@code 6} and {@code 80} columns.
     * The text with the line feed has two rows.
     * The joined text has one row.
     *
     * @return The method returns nothing.
     */
    @Test
    void joiningANewlineDropsARow() {
        assertEquals(2, InputPage.measure("ab\nc", 6, 80).rows);
        assertEquals(1, InputPage.measure("abc", 6, 80).rows);
    }

    /**
     * The method arranges the text {@code hi} and a prompt of {@code 6}.
     * The method calls {@link InputPage#measure}.
     * The page has one row.
     * Cell {@code 2} is column {@code 8}.
     *
     * @return The method returns nothing.
     */
    @Test
    void thePromptCountsOnTheFirstRow() {
        InputPage page = InputPage.measure("hi", 6, 80);
        assertEquals(1, page.rows);
        assertEquals(8, page.cells[2].col);
    }

    /**
     * The method arranges the two-line text {@code a} and {@code b}.
     * The method calls {@link InputPage#moveRow}.
     * Moving up from index {@code 0} stays at {@code 0}.
     * Moving down from the last cell stays on that last cell.
     *
     * @return The method returns nothing.
     */
    @Test
    void upOnTheFirstRowHoldsAndDownOnTheLastRowHolds() {
        InputPage page = InputPage.measure("a\nb", 0, 80);
        assertEquals(0, InputPage.moveRow(page, 0, -1));
        int end = page.cells.length - 1;
        assertEquals(end, InputPage.moveRow(page, end, 1));
    }

    /**
     * The method arranges {@code abcd} and a second line {@code xy}.
     * The method calls {@link InputPage#moveRow} from index {@code 3} downward.
     * The result is index {@code 7}.
     *
     * @return The method returns nothing.
     */
    @Test
    void downClampsToTheShortRow() {
        InputPage page = InputPage.measure("abcd\nxy", 0, 80);
        assertEquals(7, InputPage.moveRow(page, 3, 1));
    }

    /**
     * The method arranges {@code abcd} and a second line {@code xy}.
     * The method calls {@link InputPage#moveRow} from index {@code 6} upward.
     * The result is index {@code 1}.
     *
     * @return The method returns nothing.
     */
    @Test
    void upKeepsTheColumn() {
        InputPage page = InputPage.measure("abcd\nxy", 0, 80);
        assertEquals(1, InputPage.moveRow(page, 6, -1));
    }

    /**
     * The method arranges {@code abcd} and a second line {@code x}, with a prompt of {@code 6}.
     * The method calls {@link InputPage#moveRow} upward from the cell after {@code x}.
     * The result is index {@code 0}.
     *
     * @return The method returns nothing.
     */
    @Test
    void upFromUnderThePromptClampsToTheText() {
        InputPage page = InputPage.measure("abcd\nx", 6, 80);
        int afterX = page.cells.length - 1;
        assertEquals(0, InputPage.moveRow(page, afterX, -1));
    }

    /**
     * The method arranges texts with a line feed, a carriage return, neither, and an empty string.
     * The method calls {@link InputPage#pageOpen}.
     * {@code a} plus a line feed plus {@code b} returns true.
     * {@code a} plus a carriage return plus {@code b} returns true.
     * {@code abc} returns false.
     * An empty string returns false.
     *
     * @return The method returns nothing.
     */
    @Test
    void aPageSeesANewlineOrAReturn() {
        assertTrue(InputPage.pageOpen("a\nb"));
        assertTrue(InputPage.pageOpen("a\rb"));
        assertFalse(InputPage.pageOpen("abc"));
        assertFalse(InputPage.pageOpen(""));
    }

    /**
     * The method counts true entries in {@code breakAfter}.
     *
     * @param page The page is the measured input page.
     * @return The method returns that count.
     */
    private static int breaks(InputPage page) {
        int n = 0;
        for (boolean brk : page.breakAfter) {
            if (brk) {
                n++;
            }
        }
        return n;
    }
}
