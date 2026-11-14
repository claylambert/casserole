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

package org.casserole.chat;

// @aa-sdd L40 app-design.md
/**
 * The {@code SpinnerStyle} type names the in-place thinking animation for a VT100 terminal.
 * VT100 is the terminal control set the chat shell paints with.
 * The property {@code casserole.chat.spinner} selects the style.
 * {@link #BRAILLE} cycles ten Braille dot glyphs, and the client font must contain those glyphs.
 * {@link #ASCII} cycles {@code |}, {@code /}, {@code -}, and {@code \\}.
 * {@link #ASCII} is the style used when the property is missing or unrecognized.
 * {@link #NONE} stores no frames, so the animation does not run.
 *
 * @author clayl (2026-08-24)
 */
public enum SpinnerStyle {

    /**
     * The {@code BRAILLE} constant cycles ten Braille dot glyphs.
     * The client font must contain those glyphs.
     */
    BRAILLE(new String[] {
            "\u280B", "\u2819", "\u2839", "\u2838", "\u283C",
            "\u2834", "\u2826", "\u2827", "\u2807", "\u280F"
    }),

    /**
     * The {@code ASCII} constant cycles {@code |}, {@code /}, {@code -}, and {@code \\}.
     * This constant is the default.
     */
    ASCII(new String[] { "|", "/", "-", "\\" }),

    /**
     * The {@code NONE} constant stores no frames.
     * The animation does not run.
     */
    NONE(new String[0]);

    private final String[] frames;

    /**
     * The constructor stores the glyphs this style shows on each tick.
     *
     * @param frames The glyphs in cycle order, or an empty array when the style has no animation.
     */
    SpinnerStyle(String[] frames) {
        this.frames = frames;
    }

    /**
     * The {@code getFrames} method returns the glyphs this style cycles.
     * {@link #NONE} returns an empty array.
     *
     * @return The frame glyphs, or an empty array for {@link #NONE}.
     */
    public String[] getFrames() {
        return frames;
    }

    /**
     * The {@code fromProperty} method reads the {@code casserole.chat.spinner} property.
     * If {@code raw} is {@code null}, the result is {@link #ASCII}.
     * Leading and trailing spaces are removed before the text is compared.
     * If the remaining text is {@code braille}, ignoring case, the result is {@link #BRAILLE}.
     * If the remaining text is {@code none}, ignoring case, the result is {@link #NONE}.
     * Any other text selects {@link #ASCII}.
     *
     * @param raw The property value, or {@code null} when the property is absent.
     * @return The matching style, and never {@code null}.
     */
    public static SpinnerStyle fromProperty(String raw) {
        if (raw == null) {
            return ASCII;
        }
        String value = raw.trim();
        if (value.equalsIgnoreCase("braille")) {
            return BRAILLE;
        }
        if (value.equalsIgnoreCase("none")) {
            return NONE;
        }
        return ASCII;
    }
}
/*****************************************************************************************
 * End of Class: SpinnerStyle.java 082426
 ****************************************************************************************/
