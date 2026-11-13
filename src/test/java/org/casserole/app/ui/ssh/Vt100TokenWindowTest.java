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

package org.casserole.app.ui.ssh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * The class checks the token estimate in {@link Vt100TokenWindow}.
 * The test does not open a window.
 * The test does not contact a model server.
 * // @aa-sdd L40 aa-sdd-source-prose.md#Vt100TokenWindowTest
 *
 * @author clayl (2026-10-31)
 */
// @aa-sdd GK-1186 aetheris-audit-slice-c.draft.001.md
// @aa-sdd PK-640 docs/specs/common/formal-plan.spec.md
class Vt100TokenWindowTest {

    /**
     * The method arranges lines with a stored count, a short text, an empty text, and a null entry.
     * The method calls {@link Vt100TokenWindow#heuristic}, {@link Vt100TokenWindow#sum}, {@link Vt100TokenWindow#messageCount}, and {@link Vt100TokenWindow#tally}.
     * A null text, an empty text, and {@code abcd} each have heuristic {@code 1}.
     * {@code abcde} has heuristic {@code 2}.
     * The arranged lines sum to {@code 9}.
     * The stored line counts as {@code 7}.
     * The four-character line counts as {@code 1}.
     * The empty line counts as {@code 1}.
     * A null line counts as {@code 0}.
     * The tally used count is {@code 9}.
     * The tally character count is {@code 8}.
     * The tally block count is {@code 3}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aStoredCountSumsAndAMissingCountUsesTheHeuristic() {
        assertEquals(1, Vt100TokenWindow.heuristic(null));
        assertEquals(1, Vt100TokenWindow.heuristic(""));
        assertEquals(1, Vt100TokenWindow.heuristic("abcd"));
        assertEquals(2, Vt100TokenWindow.heuristic("abcde"));
        Vt100TokenWindow.Line[] chat = new Vt100TokenWindow.Line[] {
            new Vt100TokenWindow.Line(7, "kept"),
            new Vt100TokenWindow.Line("abcd"),
            new Vt100TokenWindow.Line(""),
            null
        };
        assertEquals(9, Vt100TokenWindow.sum(chat));
        assertEquals(7, Vt100TokenWindow.messageCount(chat[0]));
        assertEquals(1, Vt100TokenWindow.messageCount(chat[1]));
        assertEquals(1, Vt100TokenWindow.messageCount(chat[2]));
        assertEquals(0, Vt100TokenWindow.messageCount(null));
        Vt100TokenWindow.Tally tally = Vt100TokenWindow.tally(chat);
        assertEquals(9, tally.used);
        assertEquals(4 + 4, tally.chars);
        assertEquals(3, tally.blocks);
    }

    /**
     * The method arranges a null line array, an empty line array, and a {@link Vt100TokenWindow.Book}.
     * The method calls {@link Vt100TokenWindow#sum}, {@link Vt100TokenWindow#characters}, {@link Vt100TokenWindow#blocks}, and {@link Vt100TokenWindow.Book#tally}.
     * A null array and an empty array each sum to {@code 0}.
     * A null array has {@code 0} characters and {@code 0} blocks.
     * A missing book entry for {@code admin} tallies {@code 0} used, {@code 0} characters, and {@code 0} blocks.
     * A stored line of count {@code 4} tallies {@code 4} for {@code admin}.
     * A line stored under a null key tallies {@code 2} for null and for an empty key.
     * Replacing the {@code admin} entry with null tallies {@code 0}.
     *
     * @return The method returns nothing.
     */
    @Test
    void anEmptyChatSumsToZero() {
        assertEquals(0, Vt100TokenWindow.sum(null));
        assertEquals(0, Vt100TokenWindow.sum(new Vt100TokenWindow.Line[0]));
        assertEquals(0, Vt100TokenWindow.characters(null));
        assertEquals(0, Vt100TokenWindow.blocks(null));
        Vt100TokenWindow.Book book = new Vt100TokenWindow.Book();
        Vt100TokenWindow.Tally missing = book.tally("admin");
        assertEquals(0, missing.used);
        assertEquals(0, missing.chars);
        assertEquals(0, missing.blocks);
        book.put("admin", new Vt100TokenWindow.Line[] { new Vt100TokenWindow.Line(4, "abcd") });
        book.put(null, new Vt100TokenWindow.Line[] { new Vt100TokenWindow.Line(2, "ab") });
        assertEquals(4, book.tally("admin").used);
        assertEquals(2, book.tally(null).used);
        assertEquals(2, book.tally("").used);
        book.put("admin", null);
        assertEquals(0, book.tally("admin").used);
    }

    /**
     * The method arranges no objects.
     * The method calls {@link Vt100TokenWindow#available}.
     * A budget of {@code 0} returns {@code -1}.
     * A negative budget returns {@code -1}.
     * Used {@code 3} against budget {@code 10} returns {@code 7}.
     * Used {@code 10} against budget {@code 8} returns {@code 0}.
     * A negative used count against budget {@code 10} returns {@code 10}.
     *
     * @return The method returns nothing.
     */
    @Test
    void availableIsTheBudgetMinusTheSum() {
        assertEquals(-1, Vt100TokenWindow.available(9, 0));
        assertEquals(-1, Vt100TokenWindow.available(9, -3));
        assertEquals(7, Vt100TokenWindow.available(3, 10));
        assertEquals(0, Vt100TokenWindow.available(10, 8));
        assertEquals(10, Vt100TokenWindow.available(-4, 10));
    }

    /**
     * The method arranges the strip {@code Connected 10.0.0.2:22  vault: disk  VT100  air-gap: true  engine: ollama}.
     * The method calls {@link Vt100TokenWindow#estimateWord} and {@link Vt100TokenWindow#withEstimate}.
     * {@code 42} becomes {@code est. tokens: 42}.
     * {@code 0} and {@code -4} both become {@code est. tokens: 0}.
     * The strip plus used {@code 42} appends {@code est. tokens: 42}.
     * An empty strip with used {@code 1} returns {@code est. tokens: 1}.
     * A null strip with used {@code 1} returns {@code est. tokens: 1}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theStripCarriesTheEstimate() {
        assertEquals("est. tokens: 42", Vt100TokenWindow.estimateWord(42));
        assertEquals("est. tokens: 0", Vt100TokenWindow.estimateWord(0));
        assertEquals("est. tokens: 0", Vt100TokenWindow.estimateWord(-4));
        String strip = "Connected 10.0.0.2:22  vault: disk  VT100  air-gap: true  engine: ollama";
        String counted = Vt100TokenWindow.withEstimate(strip, 42);
        assertEquals(strip + "  est. tokens: 42", counted);
        assertEquals("est. tokens: 1", Vt100TokenWindow.withEstimate("", 1));
        assertEquals("est. tokens: 1", Vt100TokenWindow.withEstimate(null, 1));
    }

    /**
     * The method arranges the bar from {@link ToolsThinking#bar}.
     * The method calls {@link Vt100TokenWindow#seat}.
     * The seated bar holds four entries.
     * The first entry is {@link ToolsThinking#FILE}.
     * The second entry is {@link ToolsThinking#MENU}.
     * The third entry is {@code Token Calc}.
     * The fourth entry is {@link ToolsThinking#HELP}.
     * The unseated bar holds three entries and none of them equal {@code Token Calc}.
     * Seating the seated bar again keeps four entries and keeps {@code Token Calc} in the third place.
     * {@link Vt100TokenWindow#ITEM} is {@code Token Calc}.
     *
     * @return The method returns nothing.
     */
    @Test
    void tokenCalcSitsBeforeHelp() {
        String[] seated = Vt100TokenWindow.seat(ToolsThinking.bar());
        assertEquals(4, seated.length);
        assertEquals(ToolsThinking.FILE, seated[0]);
        assertEquals(ToolsThinking.MENU, seated[1]);
        assertEquals("Token Calc", seated[2]);
        assertEquals(ToolsThinking.HELP, seated[3]);
        String[] tools = ToolsThinking.bar();
        assertEquals(3, tools.length);
        int i = 0;
        while (i < tools.length) {
            assertFalse("Token Calc".equals(tools[i]));
            i = i + 1;
        }
        String[] again = Vt100TokenWindow.seat(seated);
        assertEquals(4, again.length);
        assertEquals("Token Calc", again[2]);
        assertEquals("Token Calc", Vt100TokenWindow.ITEM);
    }

    /**
     * The method arranges no objects.
     * The method calls {@link Vt100TokenWindow#usedFor}.
     * The canvas flag true with canvas count {@code 250} returns {@code 250}.
     * The canvas flag false with the other count {@code 9} returns {@code 9}.
     * A negative canvas count returns {@code 0} when the canvas flag is true.
     * A negative other count returns {@code 0} when the canvas flag is false.
     *
     * @return The method returns nothing.
     */
    @Test
    void theAetherisPlateKeepsTheCanvasEstimate() {
        assertEquals(250, Vt100TokenWindow.usedFor(true, 9, 250));
        assertEquals(9, Vt100TokenWindow.usedFor(false, 9, 250));
        assertEquals(0, Vt100TokenWindow.usedFor(true, 9, -1));
        assertEquals(0, Vt100TokenWindow.usedFor(false, -3, 250));
    }
}
