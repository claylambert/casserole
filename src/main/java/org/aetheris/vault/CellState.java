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

package org.aetheris.vault;

/**
 * The {@code CellState} enum is the context state stored on a cell.
 * The four constants are {@code INCLUDED}, {@code EXCLUDED}, {@code INPUT_ONLY}, and {@code OUTPUT_ONLY}.
 * The token {@code GHOST} means {@code EXCLUDED}.
 *
 * @author clayl (2026-09-02)
 */
public enum CellState {

    INCLUDED,
    EXCLUDED,
    INPUT_ONLY,
    OUTPUT_ONLY;

    /**
     * The method maps a stored token onto one of the four states.
     * A null token returns {@code INCLUDED}.
     * The comparison uses the trimmed token in upper case.
     * {@code EXCLUDED} and {@code GHOST} return {@code EXCLUDED}.
     * {@code INPUT_ONLY} returns {@code INPUT_ONLY}.
     * {@code OUTPUT_ONLY} returns {@code OUTPUT_ONLY}.
     * {@code INCLUDED} returns {@code INCLUDED}.
     * Any other token also returns {@code INCLUDED}.
     *
     * @param raw The raw value is the token from a file or a database column, and it may be null.
     * @return The return value is the mapped state, and an unknown token is {@code INCLUDED}.
     */
    public static CellState fromToken(String raw) {
        if (raw == null) {
            return INCLUDED;
        }
        String n = raw.trim().toUpperCase();
        if (n.equals("EXCLUDED") || n.equals("GHOST")) {
            return EXCLUDED;
        }
        if (n.equals("INPUT_ONLY")) {
            return INPUT_ONLY;
        }
        if (n.equals("OUTPUT_ONLY")) {
            return OUTPUT_ONLY;
        }
        if (n.equals("INCLUDED")) {
            return INCLUDED;
        }
        return INCLUDED;
    }

    /**
     * The method reports whether a stored token is a known context state.
     * A null token is known.
     * A blank token is known.
     * {@code INCLUDED}, {@code EXCLUDED}, {@code GHOST}, {@code INPUT_ONLY}, and {@code OUTPUT_ONLY} are known after trim and upper case.
     * Any other non-blank token is not a state.
     * The loader sets {@code readable} false when this method returns false.
     * The method does not throw.
     *
     * @param raw The raw value is the stored token, and it may be null.
     * @return The return value is true for a blank token, {@code GHOST}, or one of the four states.
     */
    public static boolean knownToken(String raw) {
        if (raw == null || raw.trim().length() == 0) {
            return true;
        }
        String n = raw.trim().toUpperCase();
        return n.equals("INCLUDED") || n.equals("EXCLUDED") || n.equals("GHOST")
                || n.equals("INPUT_ONLY") || n.equals("OUTPUT_ONLY");
    }
}
/*****************************************************************************************
 * End of Class: CellState.java 090226
 ****************************************************************************************/
