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

package org.roux.token;

import java.util.ArrayList;

/**
 * The Transcript is the ordered text that a token estimate counts.
 * Turns are stored from oldest to newest.
 * A mood head is the text stored before the first turn.
 * A mood head is not a turn.
 * {@link #rendered} writes a non-empty half and then one newline.
 * An empty half adds nothing.
 *
 * // @aa-sdd PK-0777 PK-0812 PK-4142 docs/specs/java/org/roux/token/token.spec.md
 *
 * @author clayl (2026-11-02)
 */
public final class Transcript {

    // @aa-sdd PK-0778 PK-0813 PK-4143 docs/specs/java/org/roux/token/token.spec.md
    /**
     * The Turn is one user half and one assistant half.
     * Either half may be empty.
     * A null half is stored as an empty string.
     */
    public static final class Turn {

        private final String user;
        private final String assistant;

        /**
         * The constructor stores the user half and the assistant half.
         * A null half is stored as an empty string.
         *
         * @param user The user half is the user text, or null.
         * @param assistant The assistant half is the assistant text, or null.
         */
        public Turn(String user, String assistant) {
            this.user = user == null ? "" : user;
            this.assistant = assistant == null ? "" : assistant;
        }

        /**
         * The method returns the stored user half.
         *
         * @return The text is the user half.
         */
        public String user() {
            return user;
        }

        /**
         * The method returns the stored assistant half.
         *
         * @return The text is the assistant half.
         */
        public String assistant() {
            return assistant;
        }
    }

    private final String moodHead;
    private final ArrayList<Turn> turns = new ArrayList<Turn>();

    /**
     * The constructor stores the mood head.
     * A null head is left off.
     * An empty head is left off.
     *
     * @param moodHead The mood head is the text placed before the turns.
     */
    public Transcript(String moodHead) {
        if (moodHead == null || moodHead.length() == 0) {
            this.moodHead = null;
        } else {
            this.moodHead = moodHead;
        }
    }

    /**
     * The method builds a transcript with no mood head and no turns.
     *
     * @return The transcript is empty.
     */
    public static Transcript empty() {
        return new Transcript(null);
    }

    /**
     * The method appends one turn.
     * A null turn is ignored.
     *
     * @param turn The turn is one kept construct.
     */
    public void add(Turn turn) {
        if (turn != null) {
            turns.add(turn);
        }
    }

    /**
     * The method returns how many turns are stored.
     *
     * @return The count is the number of turns.
     */
    public int turnCount() {
        return turns.size();
    }

    /**
     * The method reports whether a mood head is stored.
     *
     * @return The flag is true when the constructor stored a mood head.
     */
    public boolean hasMoodHead() {
        return moodHead != null;
    }

    /**
     * The method builds the counted string.
     * A stored mood head is written first.
     * Each non-empty half is written next, followed by one newline.
     * Zero turns and no mood head return an empty string.
     *
     * @return The string is the rendered transcript.
     */
    public String rendered() {
        StringBuilder sb = new StringBuilder();
        if (moodHead != null) {
            sb.append(moodHead);
        }
        for (int i = 0; i < turns.size(); i++) {
            Turn turn = turns.get(i);
            if (turn == null) {
                continue;
            }
            appendHalf(sb, turn.user);
            appendHalf(sb, turn.assistant);
        }
        return sb.toString();
    }

    /**
     * The method appends one half and a newline.
     * A null half adds nothing.
     * An empty half adds nothing.
     *
     * @param sb The builder receives the half.
     * @param half The half is the user text or the assistant text.
     */
    private static void appendHalf(StringBuilder sb, String half) {
        if (half == null || half.length() == 0) {
            return;
        }
        sb.append(half);
        sb.append('\n');
    }
}
/*****************************************************************************************
 * End of Class: Transcript.java 110226
 ****************************************************************************************/
