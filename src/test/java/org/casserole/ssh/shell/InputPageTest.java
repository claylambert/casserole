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
