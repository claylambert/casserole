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

package org.roux;

import java.util.ArrayList;
import java.util.Arrays;

import org.roux.model.Role;

// @aa-sdd PK-0850 PK-0897 PK-0944 PK-0991 PK-1038 PK-1085 PK-1132 PK-1179 PK-1226 PK-1273 PK-1320 PK-1367 PK-1414 PK-1461 PK-1508 PK-1555 PK-1603 PK-1650 PK-1697 PK-1744 PK-1791 PK-1838 PK-1885 PK-1932 PK-1979 PK-2026 PK-2074 PK-2121 PK-2168 PK-2215 PK-2263 PK-2311 PK-2359 PK-2406 PK-2453 PK-2501 PK-2548 PK-2595 PK-2642 PK-2690 PK-2737 PK-2785 PK-2832 PK-2879 PK-2926 PK-2973 PK-3020 PK-3067 PK-3114 PK-3161 PK-3209 PK-3256 PK-3303 PK-3351 PK-3398 PK-3445 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
/**
 * The ReichenbachSink selects which turns fit inside a token allowance.
 * The result order is the system pin, then the packed head, then the tail that fits, then the live line.
 * A refused selection returns null.
 * The type holds no region and writes no page.
 *
 * The type refuses construction.
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
final class ReichenbachSink {

    // @aa-sdd PK-0851 PK-0898 PK-0945 PK-0992 PK-1039 PK-1086 PK-1133 PK-1180 PK-1227 PK-1274 PK-1321 PK-1368 PK-1415 PK-1462 PK-1509 PK-1556 PK-1604 PK-1651 PK-1698 PK-1745 PK-1792 PK-1839 PK-1886 PK-1933 PK-1980 PK-2027 PK-2075 PK-2122 PK-2169 PK-2216 PK-2264 PK-2312 PK-2360 PK-2407 PK-2454 PK-2502 PK-2549 PK-2596 PK-2643 PK-2691 PK-2738 PK-2786 PK-2833 PK-2880 PK-2927 PK-2974 PK-3021 PK-3068 PK-3115 PK-3162 PK-3210 PK-3257 PK-3304 PK-3352 PK-3399 PK-3446 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    /**
     * The Piece is one turn the selector can see.
     * The ordinal is the position the caller assigned.
     * The seq is the message id, and a seq of {@code 0} is the live line.
     * The role is the role byte.
     * The tokens value is the token count of the turn.
     * The head flag marks a turn in the packed head.
     * The stay flag marks a turn that joins the pin.
     * The live flag marks the live line.
     */
    static final class Piece {

        final int ordinal;
        final long seq;
        final byte role;
        final int tokens;
        final boolean head;
        final boolean stay;
        final boolean live;

        /**
         * The constructor stores one turn.
         *
         * @param ordinal The ordinal is the position the caller assigned.
         * @param seq The seq is the message id, and {@code 0} is the live line.
         * @param role The role is the role byte.
         * @param tokens The tokens value is the token count of the turn.
         * @param head The head flag marks a turn in the packed head.
         * @param stay The stay flag marks a turn that joins the pin.
         * @param live The live flag marks the live line.
         */
        Piece(int ordinal, long seq, byte role, int tokens, boolean head, boolean stay, boolean live) {
            this.ordinal = ordinal;
            this.seq = seq;
            this.role = role;
            this.tokens = tokens;
            this.head = head;
            this.stay = stay;
            this.live = live;
        }
    }

    // @aa-sdd PK-0852 PK-0899 PK-0946 PK-0993 PK-1040 PK-1087 PK-1134 PK-1181 PK-1228 PK-1275 PK-1322 PK-1369 PK-1416 PK-1463 PK-1510 PK-1557 PK-1605 PK-1652 PK-1699 PK-1746 PK-1793 PK-1840 PK-1887 PK-1934 PK-1981 PK-2028 PK-2076 PK-2123 PK-2170 PK-2217 PK-2265 PK-2313 PK-2361 PK-2408 PK-2455 PK-2503 PK-2550 PK-2597 PK-2644 PK-2692 PK-2739 PK-2787 PK-2834 PK-2881 PK-2928 PK-2975 PK-3022 PK-3069 PK-3116 PK-3163 PK-3211 PK-3258 PK-3305 PK-3353 PK-3400 PK-3447 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    /**
     * The Transcript is the selection result.
     * The pieces array is the pin, then the head, then the tail, then the live line.
     * The headOver flag is true when the pin tokens plus the head tokens exceed the allowance.
     * That comparison does not include the live line.
     * The tokenSum is the sum of every piece in the array, and that sum includes the live line when it is present.
     */
    static final class Transcript {

        final Piece[] pieces;
        final boolean headOver;
        final int tokenSum;

        /**
         * The constructor stores the selection.
         *
         * @param pieces The pieces are the selected turns in result order.
         * @param headOver The headOver flag is true when the pin and the head exceed the allowance.
         * @param tokenSum The tokenSum is the sum of the selected turns.
         */
        Transcript(Piece[] pieces, boolean headOver, int tokenSum) {
            this.pieces = pieces;
            this.headOver = headOver;
            this.tokenSum = tokenSum;
        }
    }

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private ReichenbachSink() {
    }

    /**
     * The method selects the pieces that fit in a token allowance.
     * An allowance of zero or less returns null.
     * A null piece array returns null.
     * An empty piece array returns null.
     * An array with no piece whose head flag is true returns null.
     * A null piece is skipped.
     *
     * A piece whose live flag is true, or whose message id is {@code 0}, is the live line.
     * The last such piece is kept when more than one is present.
     * The stored pieces are sorted by message id, then by ordinal.
     *
     * The pin starts as the leading run of {@link Role#SYSTEM} pieces.
     * An empty leading run uses the last system piece instead.
     * A piece whose stay flag is true is added when its ordinal is not already in the pin.
     * The pin is sorted again by message id, then by ordinal.
     *
     * The head holds every remaining piece whose head flag is true.
     * The headOver flag is true when the pin tokens plus the head tokens exceed the allowance.
     * The live line is not included in that comparison.
     *
     * A true headOver flag leaves the tail empty.
     * Otherwise the tail is filled from the end of the stored pieces.
     * An assistant piece takes the previous piece when the message ids are adjacent and that role is {@link Role#USER}, {@link Role#TOOL}, or {@link Role#TOOL_CALL}.
     * A piece already in the pin or the head is skipped.
     * The charged total starts as the pin, the head, and the live line.
     * An atom is added only when that total plus the atom still fits in the allowance.
     * An atom that would pass the allowance is left out, and the scan stops.
     * The tail is then placed in ascending message order.
     *
     * The result order is the pin, then the head, then the tail, then the live line.
     * The token sum adds every piece in that result, including the live line.
     *
     * @param managedTokens The allowance is the token limit. Zero or less returns null.
     * @param pieces The pieces are the stored turns and at most one live line.
     * @return The transcript is the selection, or null when the selection is refused.
     */
    static Transcript select(int managedTokens, Piece[] pieces) {
        if (managedTokens <= 0 || pieces == null || pieces.length == 0) {
            return null;
        }
        boolean designated = false;
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] != null && pieces[i].head) {
                designated = true;
                break;
            }
        }
        if (!designated) {
            return null;
        }
        Piece live = null;
        ArrayList<Piece> stored = new ArrayList<Piece>();
        for (int i = 0; i < pieces.length; i++) {
            Piece piece = pieces[i];
            if (piece == null) {
                continue;
            }
            if (piece.live || piece.seq == 0L) {
                live = piece;
                continue;
            }
            stored.add(piece);
        }
        Piece[] ascending = stored.toArray(new Piece[stored.size()]);
        /**
         * The comparator orders stored pieces by message id, then by ordinal.
         */
        Arrays.sort(ascending, new java.util.Comparator<Piece>() {
            // @aa-sdd PK-0853 PK-0900 PK-0947 PK-0994 PK-1041 PK-1088 PK-1135 PK-1182 PK-1229 PK-1276 PK-1323 PK-1370 PK-1417 PK-1464 PK-1511 PK-1558 PK-1606 PK-1653 PK-1700 PK-1747 PK-1794 PK-1841 PK-1888 PK-1935 PK-1982 PK-2029 PK-2077 PK-2124 PK-2171 PK-2218 PK-2266 PK-2314 PK-2362 PK-2409 PK-2456 PK-2504 PK-2551 PK-2598 PK-2645 PK-2693 PK-2740 PK-2788 PK-2835 PK-2882 PK-2929 PK-2976 PK-3023 PK-3070 PK-3117 PK-3164 PK-3212 PK-3259 PK-3306 PK-3354 PK-3401 PK-3448 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
            /**
             * The method orders two pieces.
             * A smaller message id comes first.
             * Equal message ids use the difference of the ordinals.
             *
             * @param left The left piece is the first piece.
             * @param right The right piece is the second piece.
             * @return The value is negative when the left piece comes first, positive when the right piece comes first, and the ordinal difference when the message ids match.
             */
            @Override
            public int compare(Piece left, Piece right) {
                if (left.seq < right.seq) {
                    return -1;
                }
                if (left.seq > right.seq) {
                    return 1;
                }
                return left.ordinal - right.ordinal;
            }
        });
        ArrayList<Piece> pin = new ArrayList<Piece>();
        int lead = 0;
        while (lead < ascending.length && ascending[lead].role == Role.SYSTEM) {
            pin.add(ascending[lead]);
            lead++;
        }
        if (pin.isEmpty()) {
            Piece lastSystem = null;
            for (int i = 0; i < ascending.length; i++) {
                if (ascending[i].role == Role.SYSTEM) {
                    lastSystem = ascending[i];
                }
            }
            if (lastSystem != null) {
                pin.add(lastSystem);
            }
        }
        for (int i = 0; i < ascending.length; i++) {
            Piece piece = ascending[i];
            if (piece.stay && !holds(pin, piece)) {
                pin.add(piece);
            }
        }
        sortPieces(pin);
        ArrayList<Piece> head = new ArrayList<Piece>();
        for (int i = 0; i < ascending.length; i++) {
            Piece piece = ascending[i];
            if (piece.head && !holds(pin, piece)) {
                head.add(piece);
            }
        }
        int pinTokens = sum(pin);
        int headTokens = sum(head);
        boolean headOver = pinTokens + headTokens > managedTokens;
        int charged = pinTokens + headTokens + (live == null ? 0 : live.tokens);
        ArrayList<Piece> tail = new ArrayList<Piece>();
        if (!headOver) {
            boolean[] taken = new boolean[ascending.length];
            for (int i = 0; i < ascending.length; i++) {
                if (holds(pin, ascending[i]) || holds(head, ascending[i])) {
                    taken[i] = true;
                }
            }
            for (int n = ascending.length - 1; n >= 0;) {
                if (taken[n]) {
                    n--;
                    continue;
                }
                int atomStart = n;
                int atomCount = 1;
                Piece current = ascending[n];
                if (current.role == Role.ASSISTANT && n - 1 >= 0 && !taken[n - 1]) {
                    Piece previous = ascending[n - 1];
                    if (previous.seq + 1L == current.seq
                            && (previous.role == Role.USER || previous.role == Role.TOOL
                                    || previous.role == Role.TOOL_CALL)) {
                        atomStart = n - 1;
                        atomCount = 2;
                    }
                }
                int atomTokens = 0;
                for (int a = 0; a < atomCount; a++) {
                    atomTokens += ascending[atomStart + a].tokens;
                }
                if (charged + atomTokens > managedTokens) {
                    break;
                }
                for (int a = atomCount - 1; a >= 0; a--) {
                    tail.add(ascending[atomStart + a]);
                    taken[atomStart + a] = true;
                }
                charged += atomTokens;
                n = atomStart - 1;
            }
            reverse(tail);
        }
        ArrayList<Piece> wire = new ArrayList<Piece>();
        wire.addAll(pin);
        wire.addAll(head);
        wire.addAll(tail);
        if (live != null) {
            wire.add(live);
        }
        int tokenSum = 0;
        Piece[] out = new Piece[wire.size()];
        for (int i = 0; i < wire.size(); i++) {
            out[i] = wire.get(i);
            tokenSum += wire.get(i).tokens;
        }
        return new Transcript(out, headOver, tokenSum);
    }

    /**
     * The method reports whether a list already holds a piece.
     * The match is the ordinal.
     *
     * @param list The list is the pin, the head, or another piece list.
     * @param piece The piece is the turn to find.
     * @return The flag is true when one stored ordinal matches.
     */
    private static boolean holds(ArrayList<Piece> list, Piece piece) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).ordinal == piece.ordinal) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method adds the token counts in a list.
     *
     * @param list The list is the pin, the head, or another piece list.
     * @return The total is the sum of the token counts.
     */
    private static int sum(ArrayList<Piece> list) {
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i).tokens;
        }
        return total;
    }

    /**
     * The method sorts a list by message id, then by ordinal.
     * The same list object receives the sorted pieces.
     *
     * @param list The list is replaced with the sorted pieces.
     */
    private static void sortPieces(ArrayList<Piece> list) {
        Piece[] raw = list.toArray(new Piece[list.size()]);
        /**
         * The comparator orders pin pieces by message id, then by ordinal.
         */
        Arrays.sort(raw, new java.util.Comparator<Piece>() {
            // @aa-sdd PK-0854 PK-0901 PK-0948 PK-0995 PK-1042 PK-1089 PK-1136 PK-1183 PK-1230 PK-1277 PK-1324 PK-1371 PK-1418 PK-1465 PK-1512 PK-1559 PK-1607 PK-1654 PK-1701 PK-1748 PK-1795 PK-1842 PK-1889 PK-1936 PK-1983 PK-2030 PK-2078 PK-2125 PK-2172 PK-2219 PK-2267 PK-2315 PK-2363 PK-2410 PK-2457 PK-2505 PK-2552 PK-2599 PK-2646 PK-2694 PK-2741 PK-2789 PK-2836 PK-2883 PK-2930 PK-2977 PK-3024 PK-3071 PK-3118 PK-3165 PK-3213 PK-3260 PK-3307 PK-3355 PK-3402 PK-3449 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
            /**
             * The method orders two pieces.
             * A smaller message id comes first.
             * Equal message ids use the difference of the ordinals.
             *
             * @param left The left piece is the first piece.
             * @param right The right piece is the second piece.
             * @return The value is negative when the left piece comes first, positive when the right piece comes first, and the ordinal difference when the message ids match.
             */
            @Override
            public int compare(Piece left, Piece right) {
                if (left.seq < right.seq) {
                    return -1;
                }
                if (left.seq > right.seq) {
                    return 1;
                }
                return left.ordinal - right.ordinal;
            }
        });
        list.clear();
        for (int i = 0; i < raw.length; i++) {
            list.add(raw[i]);
        }
    }

    /**
     * The method reverses a list in place.
     *
     * @param list The list is the tail before it is placed in ascending order.
     */
    private static void reverse(ArrayList<Piece> list) {
        for (int i = 0, j = list.size() - 1; i < j; i++, j--) {
            Piece swap = list.get(i);
            list.set(i, list.get(j));
            list.set(j, swap);
        }
    }
}
/*****************************************************************************************
 * End of Class: ReichenbachSink.java 110426
 ****************************************************************************************/
