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

// @aa-sdd PK-0832 PK-0879 PK-0926 PK-0973 PK-1020 PK-1067 PK-1114 PK-1161 PK-1208 PK-1255 PK-1302 PK-1349 PK-1396 PK-1443 PK-1490 PK-1537 PK-1585 PK-1632 PK-1679 PK-1726 PK-1773 PK-1820 PK-1867 PK-1914 PK-1961 PK-2008 PK-2056 PK-2103 PK-2150 PK-2197 PK-2245 PK-2293 PK-2341 PK-2388 PK-2435 PK-2483 PK-2530 PK-2577 PK-2624 PK-2672 PK-2719 PK-2767 PK-2814 PK-2861 PK-2908 PK-2955 PK-3002 PK-3049 PK-3096 PK-3143 PK-3191 PK-3238 PK-3285 PK-3333 PK-3380 PK-3427 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
/**
 * The {@code CanvasHead} class decides which cells may show a lock.
 * A lock is a packed prefix of the eligible cells.
 * Index 0 is the top of the canvas.
 * A new cell is appended after the last index.
 * A null state is skipped.
 * {@link CellState#EXCLUDED} is skipped.
 *
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
 * @author clayl (2026-11-04)
 */
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
