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

// @aa-sdd GK-0721 aetheris-stage-2-finessing.001.md FINESSE:f-222
/**
 * The {@code CanvasHead} class decides which cells may show a lock.
 * A lock is a packed prefix of the eligible cells.
 * Index 0 is the top of the canvas.
 * A new cell is appended after the last index.
 * A null state is skipped.
 * {@link CellState#EXCLUDED} is skipped.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#CanvasHead reichenbach-sink.draft.001.md
 *
 * @author clayl (2026-11-04)
 */
// @aa-sdd PK-667 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-667 docs/specs/common/formal-plan.spec.md
public final class CanvasHead {

    /**
     * The constructor stops a caller from creating an instance.
     * The lock checks are static.
     */
    private CanvasHead() {
    }

    /**
     * The method reports whether one cell may show a lock.
     * The cell must be one of the eligible indexes.
     * An index that is not eligible returns false.
     *
     * @param states The states are the cells from the top of the canvas, and index 0 is the top.
     * @param headCount The count is how many eligible cells may lock.
     * @param index The index is the cell being asked about.
     * @return The return value is true when that cell is eligible for a lock.
     */
    public static boolean showsLock(CellState[] states, int headCount, int index) {
        int[] eligible = eligible(states, headCount);
        for (int i = 0; i < eligible.length; i++) {
            if (eligible[i] == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method keeps only a locked prefix of the eligible cells.
     * Locks after the first unlocked eligible cell are cleared.
     * A cell that is not eligible is cleared.
     * A null lock array is treated as all unlocked.
     * A short lock array is padded with false.
     *
     * @param states The states are the cells from the top of the canvas.
     * @param locked The locked flags are the current locks, and the array may be null.
     * @param headCount The count is the cap on eligible cells.
     * @return The return value is a new array of the repaired locks.
     */
    public static boolean[] repair(CellState[] states, boolean[] locked, int headCount) {
        boolean[] next = copy(locked, states == null ? 0 : states.length);
        int[] eligible = eligible(states, headCount);
        int prefix = 0;
        while (prefix < eligible.length && next[eligible[prefix]]) {
            prefix++;
        }
        for (int i = 0; i < next.length; i++) {
            next[i] = false;
        }
        for (int i = 0; i < prefix; i++) {
            next[eligible[i]] = true;
        }
        return next;
    }

    /**
     * The method applies one press to the lock prefix.
     * The locks are repaired before the press is read.
     * An index below 0 returns the repaired locks.
     * A null state array returns the repaired locks.
     * An index past the last cell returns the repaired locks.
     * A press on a cell that is not eligible returns the repaired locks.
     * A press on the next unlocked eligible cell locks that cell.
     * A press on the last locked eligible cell unlocks that cell.
     * Any other press leaves the repaired prefix.
     *
     * @param states The states are the cells from the top of the canvas.
     * @param locked The locked flags are the current locks.
     * @param headCount The count is the cap on eligible cells.
     * @param index The index is the pressed cell.
     * @return The return value is a new array of the locks after the press.
     */
    public static boolean[] press(CellState[] states, boolean[] locked, int headCount, int index) {
        boolean[] next = repair(states, locked, headCount);
        int[] eligible = eligible(states, headCount);
        if (index < 0 || states == null || index >= states.length) {
            return next;
        }
        boolean shown = false;
        int place = -1;
        for (int i = 0; i < eligible.length; i++) {
            if (eligible[i] == index) {
                shown = true;
                place = i;
                break;
            }
        }
        if (!shown) {
            return next;
        }
        int open = -1;
        for (int i = 0; i < eligible.length; i++) {
            if (!next[eligible[i]]) {
                open = i;
                break;
            }
        }
        if (open == place) {
            next[index] = true;
            return next;
        }
        int last = -1;
        for (int i = 0; i < eligible.length; i++) {
            if (next[eligible[i]]) {
                last = i;
            }
        }
        if (last == place) {
            next[index] = false;
        }
        return next;
    }

    /**
     * The method lists the eligible cell indexes from the top.
     * A null state array returns an empty list.
     * A head count of 0 or less returns an empty list.
     * A null state is skipped.
     * {@link CellState#EXCLUDED} is skipped.
     * The list stops at {@code headCount} eligible cells.
     *
     * @param states The states are the cells from the top of the canvas.
     * @param headCount The count is the cap on eligible cells.
     * @return The return value is the eligible indexes, top first.
     */
    private static int[] eligible(CellState[] states, int headCount) {
        if (states == null || headCount <= 0) {
            return new int[0];
        }
        int[] ids = new int[states.length];
        int n = 0;
        for (int i = 0; i < states.length && n < headCount; i++) {
            CellState state = states[i];
            if (state == null || state == CellState.EXCLUDED) {
                continue;
            }
            ids[n++] = i;
        }
        int[] out = new int[n];
        System.arraycopy(ids, 0, out, 0, n);
        return out;
    }

    /**
     * The method copies lock flags into a new array of the given size.
     * A null source returns an array of false.
     * A source shorter than the size copies only the flags it has.
     * A source longer than the size copies only the first flags.
     *
     * @param locked The locked flags are the current locks, and the array may be null.
     * @param size The size is the number of cells.
     * @return The return value is the copied flags.
     */
    private static boolean[] copy(boolean[] locked, int size) {
        boolean[] next = new boolean[size];
        if (locked == null) {
            return next;
        }
        int n = locked.length < size ? locked.length : size;
        System.arraycopy(locked, 0, next, 0, n);
        return next;
    }
}
/*****************************************************************************************
 * End of Class: CanvasHead.java 110426
 ****************************************************************************************/
