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
 * @author clayl (2026-10-31)
 */
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
