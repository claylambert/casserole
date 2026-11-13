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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * The class checks the Tools menu and the Show Thinking state in {@link ToolsThinking}.
 * The test does not open a window.
 * The test does not build a pane.
 * // @aa-sdd L40 aa-sdd-source-prose.md#ToolsThinkingTest
 *
 * @author clayl (2026-10-31)
 */
// @aa-sdd GK-1188 aetheris-audit-slice-d.draft.001.md
class ToolsThinkingTest {

    /**
     * The method arranges no objects.
     * The method calls {@link ToolsThinking#bar} and reads {@link ToolsThinking#SHOW}.
     * The bar holds three entries.
     * The first entry is {@link ToolsThinking#FILE}.
     * The second entry is {@code Tools}.
     * The third entry is {@link ToolsThinking#HELP}.
     * {@link ToolsThinking#SHOW} is {@code Show Thinking}.
     * No bar entry equals {@code Token Calc}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theBarCarriesToolsBetweenFileAndHelp() {
        String[] bar = ToolsThinking.bar();
        assertEquals(3, bar.length);
        assertEquals(ToolsThinking.FILE, bar[0]);
        assertEquals("Tools", bar[1]);
        assertEquals(ToolsThinking.HELP, bar[2]);
        assertEquals("Show Thinking", ToolsThinking.SHOW);
        int i = 0;
        while (i < bar.length) {
            assertFalse("Token Calc".equals(bar[i]));
            i = i + 1;
        }
    }

    /**
     * The method arranges no pane.
     * The method calls {@link ToolsThinking#show} with open false.
     * A slide of {@code -1} returns an open state.
     * That slide is {@code 0}.
     * That state is not folded.
     * A slide of {@code 4} returns an open state.
     * That slide is {@code 0}.
     * That state is not folded.
     *
     * @return The method returns nothing.
     */
    @Test
    void aClosedPaneRises() {
        ToolsThinking.Seat fromHidden = ToolsThinking.show(false, -1);
        assertTrue(fromHidden.open);
        assertEquals(0, fromHidden.slide);
        assertFalse(fromHidden.folded);
        ToolsThinking.Seat fromOldSlide = ToolsThinking.show(false, 4);
        assertTrue(fromOldSlide.open);
        assertEquals(0, fromOldSlide.slide);
        assertFalse(fromOldSlide.folded);
    }

    /**
     * The method arranges no pane.
     * The method calls {@link ToolsThinking#show} with open true.
     * A slide of {@code 12} stays {@code 12}.
     * That state stays open.
     * That state is not folded.
     * A slide of {@code -1} stays {@code -1}.
     * That state stays open.
     * That state is not folded.
     *
     * @return The method returns nothing.
     */
    @Test
    void anOpenPaneKeepsItsSlide() {
        ToolsThinking.Seat rising = ToolsThinking.show(true, 12);
        assertTrue(rising.open);
        assertEquals(12, rising.slide);
        assertFalse(rising.folded);
        ToolsThinking.Seat settled = ToolsThinking.show(true, -1);
        assertTrue(settled.open);
        assertEquals(-1, settled.slide);
        assertFalse(settled.folded);
    }

    /**
     * The method arranges an ssh state that is closed at slide {@code -1} and folded, and a canvas state at slide {@code 12} that is folded.
     * The method calls {@link ToolsThinking#reveal} with {@link ToolsThinking.Plate#SSH}.
     * The returned canvas object is the same object that was passed in.
     * The returned ssh state is open, its slide is {@code 0}, and it is not folded.
     * The canvas slide stays {@code 12}.
     * The canvas state stays folded.
     *
     * @return The method returns nothing.
     */
    @Test
    void theSshPlateLeavesTheCanvas() {
        ToolsThinking.Seat ssh = new ToolsThinking.Seat(false, -1, true);
        ToolsThinking.Seat canvas = new ToolsThinking.Seat(false, 12, true);
        ToolsThinking.Pair pair = ToolsThinking.reveal(ToolsThinking.Plate.SSH, ssh, canvas);
        assertSame(canvas, pair.canvas);
        assertTrue(pair.ssh.open);
        assertEquals(0, pair.ssh.slide);
        assertFalse(pair.ssh.folded);
        assertEquals(12, pair.canvas.slide);
        assertTrue(pair.canvas.folded);
    }

    /**
     * The method arranges an ssh state that is open at slide {@code 4} and folded, and a canvas state at slide {@code -1} that is folded.
     * The method calls {@link ToolsThinking#reveal} with {@link ToolsThinking.Plate#AETHERIS}.
     * The returned ssh object is the same object that was passed in.
     * The returned canvas state is open, its slide is {@code 0}, and it is not folded.
     * The ssh slide stays {@code 4}.
     * The ssh state stays folded.
     *
     * @return The method returns nothing.
     */
    @Test
    void theCanvasPlateLeavesTheSshStrip() {
        ToolsThinking.Seat ssh = new ToolsThinking.Seat(true, 4, true);
        ToolsThinking.Seat canvas = new ToolsThinking.Seat(false, -1, true);
        ToolsThinking.Pair pair = ToolsThinking.reveal(ToolsThinking.Plate.AETHERIS, ssh, canvas);
        assertSame(ssh, pair.ssh);
        assertTrue(pair.canvas.open);
        assertEquals(0, pair.canvas.slide);
        assertFalse(pair.canvas.folded);
        assertEquals(4, pair.ssh.slide);
        assertTrue(pair.ssh.folded);
    }

    /**
     * The method arranges no objects.
     * The method calls {@link ToolsThinking#front}.
     * Tab {@code 0} returns {@link ToolsThinking.Plate#SSH}.
     * Tab {@code 1} returns {@link ToolsThinking.Plate#AETHERIS}.
     * Tab {@code -1} returns {@link ToolsThinking.Plate#SSH}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theFrontTabNamesThePlate() {
        assertEquals(ToolsThinking.Plate.SSH, ToolsThinking.front(0));
        assertEquals(ToolsThinking.Plate.AETHERIS, ToolsThinking.front(1));
        assertEquals(ToolsThinking.Plate.SSH, ToolsThinking.front(-1));
    }
}
