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

import java.awt.event.KeyEvent;

/**
 * The {@code ConsoleCopyChord} type classifies one key for the focused console, the terminal pane that has the keyboard.
 * The copy chord is control, shift, and C together.
 * The chord of control and C without shift stays the interrupt, byte {@code 0x03}.
 * The {@link #textForClipboard} method returns null when the highlight is empty.
 * The caller then leaves the clipboard as it is.
 * The type writes no byte on the console.
 * // @aa-sdd L40 aa-sdd-source-prose.md#ConsoleCopyChord
 *
 * @author clayl (2026-10-31)
 */
// @aa-sdd GK-1181 aetheris-audit-slice-a.draft.001.md
public final class ConsoleCopyChord {

    // @aa-sdd L40 aa-sdd-source-prose.md#ConsoleCopyChord
    /**
     * The {@code Kind} type names the act for one key on the focused console, the terminal pane that has the keyboard.
     */
    public enum Kind {
        /**
         * The {@code COPY} constant means copy the highlight.
         * The console writes no byte for this key.
         */
        COPY,
        /**
         * The {@code INTERRUPT} constant means send the interrupt.
         * The interrupt is byte {@code 0x03}.
         * The type does not write that byte.
         */
        INTERRUPT,
        /**
         * The {@code PASS} constant means this key is not the copy chord.
         */
        PASS
    }

    /**
     * The constructor is private so a caller cannot create an instance.
     * The caller uses {@link #kind} and {@link #textForClipboard}.
     */
    private ConsoleCopyChord() {
    }

    /**
     * The kind method classifies one key for the focused console, the terminal pane that has the keyboard.
     * The key code is a Swing code, and Swing is the Java library that reports the key.
     * The method returns {@link Kind#PASS} when the key is not C or the control flag is false.
     * The method returns {@link Kind#COPY} when the key is C and both control and shift are true.
     * The method returns {@link Kind#INTERRUPT} when the key is C, control is true, and shift is false.
     * The method does not read any other modifier flag.
     *
     * @param keyCode The Swing key code for the pressed key.
     * @param ctrl The flag is true when the control key is down.
     * @param shift The flag is true when the shift key is down.
     * @return The return value is the {@link Kind} for that key.
     */
    public static Kind kind(int keyCode, boolean ctrl, boolean shift) {
        if (keyCode != KeyEvent.VK_C || !ctrl) {
            return Kind.PASS;
        }
        if (shift) {
            return Kind.COPY;
        }
        return Kind.INTERRUPT;
    }

    /**
     * The textForClipboard method chooses the text that belongs on the clipboard, the system buffer a later paste reads.
     * The method returns null when the highlight is null.
     * The method returns null when the highlight is empty.
     * The method returns the same characters for any other highlight.
     * The method does not set the clipboard.
     * The null return leaves the clipboard as it is.
     *
     * @param highlight The console highlight. The value may be null.
     * @return The return value is the highlight, or null when the highlight is null or empty.
     */
    public static String textForClipboard(String highlight) {
        if (highlight == null || highlight.isEmpty()) {
            return null;
        }
        return highlight;
    }
}
