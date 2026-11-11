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

// @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
// @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
// @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
