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
 * The class checks the bytes {@link PagePaint} emits for one page edit.
 * The test does not open a pseudo-terminal.
 * // @aa-sdd L40 aa-sdd-source-prose.md#PagePaintTest
 *
 * @author clayl (2026-10-25)
 */
// @aa-sdd GK-2169 BF-011.still-page.md
// @aa-sdd GK-2433 rust-aetheris-client.draft.001.md
class PagePaintTest {

    private static final String ED = "\u001B[J";

    /**
     * The method arranges the page {@code alpha-beta-gamma-delta-epsilon} and drops the last character.
     * The method calls {@code paint} with a prompt of {@code 6} and {@code 80} columns.
     * The bytes do not contain the erase-down sequence.
     * The bytes do not contain {@code alpha-beta}.
     * The bytes contain the erase-to-end sequence.
     * The byte length is below {@code 32}.
     * The cursor matches the shortened text.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBackspaceAtTheEndDoesNotRedrawThePage() {
        String page = "alpha-beta-gamma-delta-epsilon";
        PagePaint.Patch patch = paint(page, page.length(), page.substring(0, page.length() - 1), page.length() - 1, 6, 80);
        assertFalse(patch.ansi.contains(ED));
        assertFalse(patch.ansi.contains("alpha-beta"));
        assertTrue(patch.ansi.contains("\u001B[K"));
        assertTrue(patch.ansi.length() < 32);
        assertCursor(patch, page.substring(0, page.length() - 1), page.length() - 1, 6, 80);
    }

    /**
     * The method arranges a prefix of ten {@code p} characters and twenty {@code q} characters, then drops ten {@code q} characters.
     * The method calls {@code paint} at column width {@code 80}.
     * The bytes do not contain the erase-down sequence.
     * The bytes do not contain the prefix.
     * The byte length is below {@code 32}.
     * The cursor matches the shortened text.
     *
     * @return The method returns nothing.
     */
    @Test
    void tenDeletesAreOneShortUpdate() {
        String prefix = "pppppppppp";
        String before = prefix + "qqqqqqqqqqqqqqqqqqqq";
        String after = prefix + "qqqqqqqqqq";
        PagePaint.Patch patch = paint(before, before.length(), after, after.length(), 0, 80);
        assertFalse(patch.ansi.contains(ED));
        assertFalse(patch.ansi.contains(prefix));
        assertTrue(patch.ansi.length() < 32);
        assertCursor(patch, after, after.length(), 0, 80);
    }

    /**
     * The method arranges {@code 0123456789} and drops the last digit, at width {@code 10}.
     * The method calls {@code paint}.
     * The bytes do not contain the erase-down sequence.
     * The bytes do not contain the shortened text.
     * The bytes contain the delete-one-row sequence and the erase-to-end sequence.
     * The patch reports one row.
     * The cursor matches the shortened text.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBackspaceThatClosesAWrapDeletesTheRow() {
        String before = "0123456789";
        String after = "012345678";
        PagePaint.Patch patch = paint(before, before.length(), after, after.length(), 0, 10);
        assertFalse(patch.ansi.contains(ED));
        assertFalse(patch.ansi.contains(after));
        assertTrue(patch.ansi.contains("\u001B[1M"));
        assertTrue(patch.ansi.contains("\u001B[K"));
        assertEquals(1, patch.rows);
        assertCursor(patch, after, after.length(), 0, 10);
    }

    /**
     * The method arranges {@code AB}, a line feed, and {@code C}, then the joined text {@code ABC}.
     * The method calls {@code paint}.
     * The bytes do not contain the erase-down sequence.
     * The bytes do not contain {@code AB}.
     * The bytes contain {@code C} and the delete-one-row sequence.
     * The cursor matches {@code ABC} at index {@code 3}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aDeletedNewlineRewritesFromTheJoin() {
        PagePaint.Patch patch = paint("AB\nC", 4, "ABC", 3, 0, 80);
        assertFalse(patch.ansi.contains(ED));
        assertFalse(patch.ansi.contains("AB"));
        assertTrue(patch.ansi.contains("C"));
        assertTrue(patch.ansi.contains("\u001B[1M"));
        assertCursor(patch, "ABC", 3, 0, 80);
    }

    /**
     * The method arranges {@code hello-page-already-shown} and appends {@code Z}.
     * The method calls {@code paint} with a prompt of {@code 6}.
     * The bytes do not contain the erase-down sequence.
     * The bytes do not contain the prefix.
     * The bytes contain {@code Z}.
     * The cursor matches the text with {@code Z} appended.
     *
     * @return The method returns nothing.
     */
    @Test
    void aTypedCharacterAtTheEndDoesNotCopyThePrefix() {
        String prefix = "hello-page-already-shown";
        PagePaint.Patch patch = paint(prefix, prefix.length(), prefix + "Z", prefix.length() + 1, 6, 80);
        assertFalse(patch.ansi.contains(ED));
        assertFalse(patch.ansi.contains(prefix));
        assertTrue(patch.ansi.contains("Z"));
        assertCursor(patch, prefix + "Z", prefix.length() + 1, 6, 80);
    }

    /**
     * The method arranges {@code 012345678} and appends {@code 9}, at width {@code 10}.
     * The method calls {@code paint}.
     * The bytes do not contain the erase-down sequence.
     * The bytes do not contain the nine-digit prefix.
     * The bytes contain {@code 9} followed by a carriage return and a line feed.
     * The patch reports two rows.
     * The cursor row is {@code 1}.
     * The cursor column is {@code 0}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aFillingCharacterParksWithAReturn() {
        String before = "012345678";
        String after = before + "9";
        PagePaint.Patch patch = paint(before, before.length(), after, after.length(), 0, 10);
        assertFalse(patch.ansi.contains(ED));
        assertFalse(patch.ansi.contains(before));
        assertTrue(patch.ansi.contains("9\r\n"));
        assertEquals(2, patch.rows);
        assertEquals(1, patch.cursorRow);
        assertEquals(0, patch.cursorCol);
    }

    /**
     * The method arranges {@code 0123456789} and appends a line feed, at width {@code 10}.
     * The method calls {@code paint}.
     * The bytes are empty.
     * The patch reports two rows.
     * The cursor matches the text that ends with the line feed.
     *
     * @return The method returns nothing.
     */
    @Test
    void aNewlineAfterAFullRowAddsNoSecondBreak() {
        String before = "0123456789";
        String after = before + "\n";
        PagePaint.Patch patch = paint(before, before.length(), after, after.length(), 0, 10);
        assertEquals("", patch.ansi);
        assertEquals(2, patch.rows);
        assertCursor(patch, after, after.length(), 0, 10);
    }

    /**
     * The method arranges {@code abcdef} and moves the cursor to index {@code 2}.
     * The method calls {@code paint}.
     * The bytes do not contain the erase-down sequence.
     * The bytes do not contain the erase-to-end sequence.
     * The bytes do not contain {@code abcdef}.
     * The byte length is greater than {@code 0}.
     * The cursor matches index {@code 2}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aCursorMoveDoesNotErase() {
        String text = "abcdef";
        PagePaint.Patch patch = paint(text, text.length(), text, 2, 0, 80);
        assertFalse(patch.ansi.contains(ED));
        assertFalse(patch.ansi.contains("\u001B[K"));
        assertFalse(patch.ansi.contains("abcdef"));
        assertTrue(patch.ansi.length() > 0);
        assertCursor(patch, text, 2, 0, 80);
    }

    /**
     * The method arranges an empty page and the typed glyph {@code Z} at width {@code 80}.
     * The method calls {@link PagePaint#update} with the green flag true.
     * The bytes contain the green color sequence, {@code Z}, and the reset sequence.
     * The bytes do not contain the erase-down sequence.
     *
     * @return The method returns nothing.
     */
    @Test
    void commandGreenWrapsTheTypedGlyph() {
        PagePaint.Patch patch = PagePaint.update("", 0, 0, 5, "Z", 1, 5, 80, true);
        assertTrue(patch.ansi.contains("\u001B[32mZ\u001B[0m"));
        assertFalse(patch.ansi.contains(ED));
    }

    /**
     * The method measures {@code before} and calls {@link PagePaint#update} with the green flag false.
     * The cursor cell comes from the measured page at {@code beforeCursor}.
     * An index below {@code 0} becomes {@code 0}.
     * An index past the last cell becomes the last cell.
     *
     * @param before The text is the page before the edit.
     * @param beforeCursor The index is the cursor before the edit.
     * @param after The text is the page after the edit.
     * @param afterCursor The index is the cursor after the edit.
     * @param prompt The count is the prompt width in columns.
     * @param cols The count is the page width in columns.
     * @return The method returns the patch from {@link PagePaint#update}.
     */
    private static PagePaint.Patch paint(
            String before, int beforeCursor, String after, int afterCursor, int prompt, int cols) {
        InputPage page = InputPage.measure(before, prompt, cols);
        int index = beforeCursor;
        if (index < 0) {
            index = 0;
        }
        if (index >= page.cells.length) {
            index = page.cells.length - 1;
        }
        InputPage.Cell cursor = page.cells[index];
        return PagePaint.update(before, beforeCursor, cursor.row, cursor.col, after, afterCursor, prompt, cols, false);
    }

    /**
     * The method measures {@code text} and compares the patch cursor with that page.
     * An index below {@code 0} becomes {@code 0}.
     * An index past the last cell becomes the last cell.
     * The patch row equals the measured cell row.
     * The patch column equals the measured cell column.
     * The patch row count equals the measured row count.
     *
     * @param patch The patch is the edit result under test.
     * @param text The text is the page after the edit.
     * @param cursor The index is the cursor after the edit.
     * @param prompt The count is the prompt width in columns.
     * @param cols The count is the page width in columns.
     * @return The method returns nothing.
     */
    private static void assertCursor(PagePaint.Patch patch, String text, int cursor, int prompt, int cols) {
        InputPage page = InputPage.measure(text, prompt, cols);
        int index = cursor;
        if (index < 0) {
            index = 0;
        }
        if (index >= page.cells.length) {
            index = page.cells.length - 1;
        }
        assertEquals(page.cells[index].row, patch.cursorRow);
        assertEquals(page.cells[index].col, patch.cursorCol);
        assertEquals(page.rows, patch.rows);
    }
}
