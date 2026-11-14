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

// @aa-sdd PK-1256 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
/**
 * The {@code Ansi} class holds the VT100 color and cursor sequences the shell writes.
 * A sequence is a short string of control bytes. The class does not choose which mode the terminal is in.
 * // @aa-sdd L40 aa-sdd-source-prose.md#Ansi
 *
 * @author clayl (2026-08-26)
 */
// @aa-sdd PK-427 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
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
