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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;

/**
 * The class checks {@link ConsoleCopyChord}.
 * The copy chord and the interrupt stay apart.
 * The test does not open a window.
 * The test does not open a pseudo-terminal.
 * @aa-sdd PK-3546 PK-3548 PK-3552 PK-3576 PK-3582 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 *
 * @author clayl (2026-10-31)
 */
class ConsoleCopyChordTest {

    /**
     * The method arranges no objects.
     * The method calls {@link ConsoleCopyChord#kind} for {@code C} with control and shift both true.
     * The result is {@link ConsoleCopyChord.Kind#COPY}.
     *
     * @return The method returns nothing.
     */
    @Test
    void ctrlShiftCCopiesAndWritesNothing() {
        assertEquals(ConsoleCopyChord.Kind.COPY, ConsoleCopyChord.kind(KeyEvent.VK_C, true, true));
    }

    /**
     * The method arranges no objects.
     * The method calls {@link ConsoleCopyChord#kind} for {@code C} with control true and shift false.
     * The result is {@link ConsoleCopyChord.Kind#INTERRUPT}.
     *
     * @return The method returns nothing.
     */
    @Test
    void ctrlCStaysTheInterrupt() {
        assertEquals(ConsoleCopyChord.Kind.INTERRUPT, ConsoleCopyChord.kind(KeyEvent.VK_C, true, false));
    }

    /**
     * The method arranges no objects.
     * The method calls {@link ConsoleCopyChord#kind} for {@code C} with control false.
     * Shift false returns {@link ConsoleCopyChord.Kind#PASS}.
     * Shift true returns {@link ConsoleCopyChord.Kind#PASS}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aPlainCPasses() {
        assertEquals(ConsoleCopyChord.Kind.PASS, ConsoleCopyChord.kind(KeyEvent.VK_C, false, false));
        assertEquals(ConsoleCopyChord.Kind.PASS, ConsoleCopyChord.kind(KeyEvent.VK_C, false, true));
    }

    /**
     * The method arranges no objects.
     * The method calls {@link ConsoleCopyChord#kind} for {@code V} with control true.
     * Shift true returns {@link ConsoleCopyChord.Kind#PASS}.
     * Shift false returns {@link ConsoleCopyChord.Kind#PASS}.
     *
     * @return The method returns nothing.
     */
    @Test
    void anotherCtrlChordPasses() {
        assertEquals(ConsoleCopyChord.Kind.PASS, ConsoleCopyChord.kind(KeyEvent.VK_V, true, true));
        assertEquals(ConsoleCopyChord.Kind.PASS, ConsoleCopyChord.kind(KeyEvent.VK_V, true, false));
    }

    /**
     * The method arranges no clipboard.
     * The method calls {@link ConsoleCopyChord#textForClipboard}.
     * A null highlight returns null.
     * An empty highlight returns null.
     *
     * @return The method returns nothing.
     */
    @Test
    void anEmptyHighlightLeavesTheClipboard() {
        assertNull(ConsoleCopyChord.textForClipboard(null));
        assertNull(ConsoleCopyChord.textForClipboard(""));
    }

    /**
     * The method arranges the highlight {@code alpha}.
     * The method calls {@link ConsoleCopyChord#textForClipboard}.
     * The result is {@code alpha}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aHighlightIsTheClipboardText() {
        assertEquals("alpha", ConsoleCopyChord.textForClipboard("alpha"));
    }
}
