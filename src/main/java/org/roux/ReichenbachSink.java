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

// @aa-sdd PK-1259 docs/specs/java/org/roux/roux.spec.md FINESSE:f-222
/**
 * The ReichenbachSink selects which turns fit inside a token allowance.
 * The result order is the system pin, then the packed head, then the tail that fits, then the live line.
 * A refused selection returns null.
 * The type holds no region and writes no page.
 *
 * The type refuses construction.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#ReichenbachSink reichenbach-sink.draft.001.md
 *
 * @author clayl (2026-11-04)
 */
// @aa-sdd PK-505 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-701 docs/specs/common/roux.spec.md
// @aa-sdd PK-506 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-702 docs/specs/common/roux.spec.md
// @aa-sdd PK-796 docs/specs/common/roux.spec.md
// @aa-sdd PK-507 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-508 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-510 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-511 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-799 docs/specs/common/roux.spec.md
final class ReichenbachSink {

    // @aa-sdd PK-1260 docs/specs/java/org/roux/roux.spec.md FINESSE:f-222
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

    // @aa-sdd PK-1261 docs/specs/java/org/roux/roux.spec.md FINESSE:f-222
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
            // @aa-sdd PK-1262 docs/specs/java/org/roux/roux.spec.md FINESSE:f-222
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
            // @aa-sdd PK-1262 docs/specs/java/org/roux/roux.spec.md FINESSE:f-222
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
