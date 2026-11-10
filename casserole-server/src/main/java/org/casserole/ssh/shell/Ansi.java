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

// @aa-sdd PK-3510 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
/**
 * The {@code Ansi} class holds the VT100 color and cursor sequences the shell writes.
 * A sequence is a short string of control bytes. The class does not choose which mode the terminal is in.
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
 * @author clayl (2026-08-26)
 */
public final class Ansi {

    public static final String GREEN = "\u001B[32m";
    /**
     * The value is the control sequence that erases the screen from the cursor to the end.
     * The bytes are {@code ESC [ J}.
     */
    public static final String ED = "\u001B[J";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[93m";
    public static final String RESET = "\u001B[0m";
    /**
     * The value is electric blue, xterm 256-color index {@code 45}.
     * That index is about {@code #00d7ff}.
     */
    public static final String ELECTRIC_BLUE = "\u001B[38;5;45m";
    /**
     * The value is the green used for the spinner glyph.
     * The red, green, and blue parts are {@code 0}, {@code 255}, and {@code 102}, which is {@code #00FF66}.
     */
    public static final String EMERALD = "\u001B[38;2;0;255;102m";
    /**
     * The value is medium blue for the {@code cmd>} prompt.
     * The color is xterm 256-color index {@code 33}.
     */
    public static final String MEDIUM_BLUE = "\u001B[38;5;33m";
    /**
     * The value is dim gray, also called bright black, for box drawing.
     * The sequence is {@code ESC [ 90 m}.
     */
    public static final String DIM_GRAY = "\u001B[90m";
    /**
     * The value is a slightly brighter gray for the text inside a header.
     * The color is xterm 256-color index {@code 250}.
     */
    public static final String SOFT_GRAY = "\u001B[38;5;250m";
    /**
     * The value is hot pink for status values.
     * The color is xterm 256-color index {@code 198}.
     */
    public static final String HOT_PINK = "\u001B[38;5;198m";
    /**
     * The value is a carriage return, which reuses the current line.
     * The byte is {@code \r}.
     */
    public static final String CR = "\r";
    /**
     * The value is the control sequence that erases from the cursor to the end of the line.
     * The bytes are {@code ESC [ K}.
     */
    public static final String EL = "\u001B[K";

    /**
     * The constructor is private so callers use the static methods.
     * The class holds no instance state.
     */
    private Ansi() {
    }

    /**
     * The method wraps text in green and then resets the color.
     *
     * @param text The text is the raw characters to color.
     * @return The return value is the green-wrapped text.
     */
    public static String green(String text) {
        return GREEN + text + RESET;
    }

    /**
     * The method wraps text in red and then resets the color.
     *
     * @param text The text is the raw characters to color.
     * @return The return value is the red-wrapped text.
     */
    public static String red(String text) {
        return RED + text + RESET;
    }

    /**
     * The method wraps text in bright yellow and then resets the color.
     *
     * @param text The text is the raw characters to color.
     * @return The return value is the bright-yellow text.
     */
    public static String yellow(String text) {
        return YELLOW + text + RESET;
    }

    /**
     * The method wraps text in medium blue and then resets the color.
     *
     * @param text The text is the raw characters to color.
     * @return The return value is the medium-blue text.
     */
    public static String mediumBlue(String text) {
        return MEDIUM_BLUE + text + RESET;
    }

    /**
     * The method returns the cursor-up sequence for {@code n} rows.
     * A count of {@code 0} or less returns an empty string. A positive count returns {@code ESC [ n A}.
     *
     * @param n The count is the number of rows to move up.
     * @return The return value is the control sequence, or an empty string when the count is not positive.
     */
    public static String cuu(int n) {
        if (n <= 0) {
            return "";
        }
        return "\u001B[" + n + "A";
    }

    /**
     * The method returns the cursor-down sequence for {@code n} rows.
     * A count of {@code 0} or less returns an empty string. A positive count returns {@code ESC [ n B}.
     *
     * @param n The count is the number of rows to move down.
     * @return The return value is the control sequence, or an empty string when the count is not positive.
     */
    public static String cud(int n) {
        if (n <= 0) {
            return "";
        }
        return "\u001B[" + n + "B";
    }

    /**
     * The method returns the cursor-forward sequence for {@code n} columns.
     * A count of {@code 0} or less returns an empty string. A positive count returns {@code ESC [ n C}.
     *
     * @param n The count is the number of columns to move forward.
     * @return The return value is the control sequence, or an empty string when the count is not positive.
     */
    public static String cuf(int n) {
        if (n <= 0) {
            return "";
        }
        return "\u001B[" + n + "C";
    }

    /**
     * The method returns the cursor-back sequence for {@code n} columns.
     * A count of {@code 0} or less returns an empty string. A positive count returns {@code ESC [ n D}.
     *
     * @param n The count is the number of columns to move back.
     * @return The return value is the control sequence, or an empty string when the count is not positive.
     */
    public static String cub(int n) {
        if (n <= 0) {
            return "";
        }
        return "\u001B[" + n + "D";
    }

    /**
     * The method returns the delete-lines sequence for {@code n} lines at the cursor.
     * Lines below the cursor shift up. A count of {@code 0} or less returns an empty string. A positive count returns {@code ESC [ n M}.
     *
     * @param n The count is the number of lines to delete.
     * @return The return value is the control sequence, or an empty string when the count is not positive.
     */
    public static String dl(int n) {
        if (n <= 0) {
            return "";
        }
        return "\u001B[" + n + "M";
    }

    /**
     * The method builds a status line in the form {@code key: value}.
     * The key is bright green. The value is hot pink. A null key or a null value becomes an empty string. The result has no carriage return.
     *
     * @param key The key is the label on the left.
     * @param value The value is the text on the right.
     * @return The return value is the colored line without a line ending.
     */
    public static String kv(String key, String value) {
        String k = key == null ? "" : key;
        String v = value == null ? "" : value;
        return BRIGHT_GREEN + k + RESET + ": " + HOT_PINK + v + RESET;
    }

    /**
     * The method builds a two-row dim box whose inner text is the clock text, a middle dot, and the model id.
     * The clock text uses the form {@code yyyy-MM-dd HH:mm:ss}. A width below {@code 24} becomes {@code 80}. A null clock text or a null model becomes empty.
     *
     * The inner text is shortened by dropping characters from the end of the model until it fits. If it still does not fit, the inner text itself is cut. The top row is a box edge around that text. The bottom row is a box edge of the full width. Each row ends with a carriage return and a line feed.
     *
     * @param stamp The clock text is the time in the form {@code yyyy-MM-dd HH:mm:ss}.
     * @param model The model is the live model id.
     * @param columns The column count is the terminal width.
     * @return The return value is two colored rows, each ended by a carriage return and a line feed.
     */
    public static String replyHeader(String stamp, String model, int columns) {
        int cols = columns;
        if (cols < 24) {
            cols = 80;
        }
        String ts = stamp == null ? "" : stamp;
        String m = model == null ? "" : model;
        String inner = " " + ts + " · " + m + " ";
        int budget = cols - 4;
        if (budget < 8) {
            budget = 8;
        }
        while (inner.length() > budget && m.length() > 0) {
            m = m.substring(0, m.length() - 1);
            inner = " " + ts + " · " + m + " ";
        }
        if (inner.length() > budget) {
            inner = inner.substring(0, budget);
        }
        int rest = cols - 2 - inner.length();
        if (rest < 0) {
            rest = 0;
        }
        int left = 2;
        if (left > rest) {
            left = rest;
        }
        int right = rest - left;
        String top = DIM_GRAY + "┌" + repeatBox(left) + RESET + SOFT_GRAY + inner + RESET + DIM_GRAY
                + repeatBox(right) + "┐" + RESET + "\r\n";
        String bottom = DIM_GRAY + "└" + repeatBox(cols - 2) + "┘" + RESET + "\r\n";
        return top + bottom;
    }

    /**
     * The method repeats the box-drawing dash {@code n} times.
     * A count of {@code 0} or less returns an empty string.
     *
     * @param n The count is the number of dashes.
     * @return The return value is that many box-drawing dashes.
     */
    private static String repeatBox(int n) {
        if (n <= 0) {
            return "";
        }
        StringBuilder out = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            out.append('─');
        }
        return out.toString();
    }
}
/*****************************************************************************************
 * End of Class: Ansi.java 082626
 ****************************************************************************************/
