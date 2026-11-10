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
 *
 * @author clayl (2026-10-31)
 */
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
