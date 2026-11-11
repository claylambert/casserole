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

package org.casserole.app.ui.ssh;

import java.util.ArrayList;

import org.roux.token.HeuristicTokenCounter;

/**
 * The {@code Vt100TokenWindow} type counts tokens for the VT100 console.
 * The name VT100 means the console whose chat this window sums.
 * The used side for a Roux user is that user's chat, and Roux is the chat these messages belong to.
 * The stored count is the count Roux kept when the message was appended.
 * The message with no stored count uses {@link #heuristic} on its text.
 * The heuristic count is {@code 1} when the text is empty.
 * The heuristic count is {@code (length + 3) / 4} for any other text.
 * The Aetheris tab, the canvas tab, keeps the canvas estimate instead of the Roux sum.
 * The private constructor refuses an instance.
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 *
 * @author clayl (2026-10-31)
 */
public final class Vt100TokenWindow {

    /**
     * The {@code ITEM} constant is the menu item that opens the token window.
     * The text is {@code Token Calc}.
     */
    public static final String ITEM = "Token Calc";

    /**
     * The {@code ESTIMATE} constant is the words in front of the count.
     * The text is {@code est. tokens: }, including the trailing space.
     */
    public static final String ESTIMATE = "est. tokens: ";

    /**
     * The constructor is private so a caller cannot create an instance.
     * The caller uses the static methods on this type.
     */
    private Vt100TokenWindow() {
    }

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
     * The {@code Line} type is one Roux message.
     * The name Roux means the chat the message belongs to.
     * The {@code held} flag is true when Roux stored {@code stored}.
     * The line that is not held has no stored count.
     * The text is kept as it is given, including null.
     */
    public static final class Line {

        public final boolean held;
        public final int stored;
        public final String text;

        /**
         * The constructor builds a message with no stored count.
         * The held flag is false.
         * The stored count is {@code 0}.
         *
         * @param text The message text. The value may be null.
         */
        public Line(String text) {
            this.held = false;
            this.stored = 0;
            this.text = text;
        }

        /**
         * The constructor builds a message Roux already counted.
         * The held flag is true.
         * The stored count is kept even when it is negative.
         *
         * @param stored The stored token count.
         * @param text The message text. The value may be null.
         */
        public Line(int stored, String text) {
            this.held = true;
            this.stored = stored;
            this.text = text;
        }
    }

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
     * The {@code Tally} type is the used-token count, the character count, and the message count for one chat.
     * The constructor is private.
     * The method {@link Vt100TokenWindow#tally} builds one.
     */
    public static final class Tally {

        public final int used;
        public final int chars;
        public final int blocks;

        /**
         * The constructor stores the three counts as they are given.
         *
         * @param used The used-token count.
         * @param chars The character count.
         * @param blocks The message count.
         */
        private Tally(int used, int chars, int blocks) {
            this.used = used;
            this.chars = chars;
            this.blocks = blocks;
        }
    }

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
     * The {@code Book} type holds the Roux lines for this window, one chat per user.
     * The name Roux means the chat the lines belong to.
     * The missing user is an empty chat.
     * The later {@link #put} replaces the whole chat for that user.
     */
    public static final class Book {

        private final ArrayList<String> names = new ArrayList<String>();
        private final ArrayList<Line[]> chats = new ArrayList<Line[]>();

        /**
         * The put method stores one user's chat.
         * The method stores a null user under an empty name.
         * The method stores a null row array as an empty array.
         * The stored array is a new array.
         * The {@link Line} objects in that array are the same objects that were passed in.
         * The user who is already present has that chat replaced.
         * The new user is appended.
         *
         * @param user The Roux user. The value may be null.
         * @param rows The messages for that user. The null array is an empty chat.
         */
        public void put(String user, Line[] rows) {
            String key = user == null ? "" : user;
            Line[] copy = copyOf(rows);
            int at = index(key);
            if (at < 0) {
                names.add(key);
                chats.add(copy);
                return;
            }
            chats.set(at, copy);
        }

        /**
         * The tally method sums one user's chat.
         * The method looks up a null user under an empty name.
         * The user with no stored chat returns the tally of a null line array.
         * The empty tally has {@code 0} used tokens.
         * The empty tally has {@code 0} characters.
         * The empty tally has {@code 0} messages.
         *
         * @param user The Roux user. The value may be null.
         * @return The return value is the sum for that chat.
         */
        public Tally tally(String user) {
            String key = user == null ? "" : user;
            int at = index(key);
            if (at < 0) {
                return Vt100TokenWindow.tally((Line[]) null);
            }
            return Vt100TokenWindow.tally(chats.get(at));
        }

        /**
         * The index method finds the stored name that equals the key.
         * The method returns the matching index.
         * The method returns {@code -1} when no name matches.
         *
         * @param key The user name to find. The caller does not pass null.
         * @return The return value is the index, or {@code -1}.
         */
        private int index(String key) {
            for (int i = 0; i < names.size(); i++) {
                if (key.equals(names.get(i))) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * The heuristic method delegates to {@link HeuristicTokenCounter#count}.
     * The count is {@code 1} when the text is null.
     * The count is {@code 1} when the text is empty.
     * The count is {@code (length + 3) / 4} for any other text.
     *
     * @param text The message text. The value may be null.
     * @return The return value is the heuristic count.
     */
    public static int heuristic(String text) {
        // @aa-sdd PK-4128 docs/specs/java/org/aetheris/swing/swing.spec.md
        return new HeuristicTokenCounter().count(text);
    }

    /**
     * The messageCount method returns the token count for one message.
     * The method returns {@code 0} when the line is null.
     * The method returns the stored count when the line is held and the stored count is {@code 0} or higher.
     * The method returns {@link #heuristic} when the line is held and the stored count is negative.
     * The method returns {@link #heuristic} when the line is not held.
     *
     * @param line The message. The value may be null.
     * @return The return value is the stored count, or the heuristic when the count was not stored.
     */
    public static int messageCount(Line line) {
        if (line == null) {
            return 0;
        }
        if (line.held && line.stored >= 0) {
            return line.stored;
        }
        return heuristic(line.text);
    }

    /**
     * The sum method adds {@link #messageCount} across the chat.
     * The method returns {@code 0} when the array is null.
     * The null line adds {@code 0}.
     * The running total is a long.
     * The method returns {@code Integer.MAX_VALUE} when the total passes that limit.
     *
     * @param lines The Roux chat. The value may be null.
     * @return The return value is the sum of the message counts.
     */
    public static int sum(Line[] lines) {
        if (lines == null) {
            return 0;
        }
        long total = 0L;
        for (int i = 0; i < lines.length; i++) {
            total = total + messageCount(lines[i]);
            if (total > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
        }
        return (int) total;
    }

    /**
     * The characters method adds the text length across the chat.
     * The method returns {@code 0} when the array is null.
     * The method skips a null line.
     * The method skips a line with null text.
     * The running total is a long.
     * The method returns {@code Integer.MAX_VALUE} when the total passes that limit.
     *
     * @param lines The Roux chat. The value may be null.
     * @return The return value is the character count.
     */
    public static int characters(Line[] lines) {
        if (lines == null) {
            return 0;
        }
        long total = 0L;
        for (int i = 0; i < lines.length; i++) {
            Line line = lines[i];
            if (line == null || line.text == null) {
                continue;
            }
            total = total + line.text.length();
            if (total > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
        }
        return (int) total;
    }

    /**
     * The blocks method counts the messages in the chat.
     * The method returns {@code 0} when the array is null.
     * The method does not count a null slot.
     * The method still counts a line with null text.
     *
     * @param lines The Roux chat. The value may be null.
     * @return The return value is the number of messages.
     */
    public static int blocks(Line[] lines) {
        if (lines == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] != null) {
                count = count + 1;
            }
        }
        return count;
    }

    /**
     * The tally method builds one {@link Tally} for the chat.
     * The used count is {@link #sum}.
     * The character count is {@link #characters}.
     * The message count is {@link #blocks}.
     * The null array produces zero for the used count.
     * The null array produces zero for the character count.
     * The null array produces zero for the message count.
     *
     * @param lines The Roux chat. The value may be null.
     * @return The return value is the used count, the character count, and the message count.
     */
    public static Tally tally(Line[] lines) {
        return new Tally(sum(lines), characters(lines), blocks(lines));
    }

    /**
     * The available method subtracts the used count from the budget.
     * The budget under {@code 1} is unknown.
     * The method returns {@code -1} for an unknown budget.
     * The method treats a negative used count as {@code 0}.
     * The method returns {@code 0} when the used count is past the budget.
     * The method returns {@code Integer.MAX_VALUE} when the difference is past that limit.
     *
     * @param used The Roux sum.
     * @param budget The context window the token window already shows.
     * @return The return value is the available approximation, or {@code -1} when the budget is unknown.
     */
    public static int available(int used, int budget) {
        if (budget < 1) {
            return -1;
        }
        int held = used < 0 ? 0 : used;
        long left = (long) budget - held;
        if (left < 0L) {
            return 0;
        }
        if (left > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) left;
    }

    /**
     * The estimateWord method builds the estimate label for a used count.
     * The method paints a negative count as {@code 0}.
     * The text is {@link #ESTIMATE} followed by that count.
     *
     * @param used The Roux sum. The negative value paints as {@code 0}.
     * @return The return value is {@code est. tokens: } and the count.
     */
    public static String estimateWord(int used) {
        int n = used < 0 ? 0 : used;
        return ESTIMATE + n;
    }

    /**
     * The withEstimate method appends the estimate to a status strip.
     * The method returns the estimate alone when the strip is null.
     * The method returns the estimate alone when the strip is empty.
     * The method returns the strip, two spaces, and the estimate for every other strip.
     *
     * @param strip The link, the facts, and the words already on the line. The value may be null.
     * @param used The Roux sum.
     * @return The return value is the strip with the estimate.
     */
    public static String withEstimate(String strip, int used) {
        String word = estimateWord(used);
        if (strip == null || strip.length() == 0) {
            return word;
        }
        return strip + "  " + word;
    }

    /**
     * The usedFor method chooses the used count for the plate in front.
     * The Aetheris plate keeps the canvas estimate.
     * The SSH plate uses the Roux sum.
     * The negative canvas estimate becomes {@code 0} on the Aetheris branch.
     * The negative Roux sum becomes {@code 0} on the SSH branch.
     *
     * @param aetheris The flag is true when the Aetheris tab is in front.
     * @param roux The Roux sum.
     * @param canvas The inspector estimate for the canvas.
     * @return The return value is the used side for the plate in front.
     */
    public static int usedFor(boolean aetheris, int roux, int canvas) {
        if (aetheris) {
            return canvas < 0 ? 0 : canvas;
        }
        return roux < 0 ? 0 : roux;
    }

    /**
     * The seat method places {@link #ITEM} on the menu bar.
     * The item text is {@code Token Calc}.
     * The null bar returns a one-element array that holds only that item.
     * The method returns a copy of the bar when {@code Token Calc} is already present.
     * The copy keeps the existing order.
     * The method appends the item when {@link ToolsThinking#HELP} is absent.
     * The method inserts the item immediately before the last {@code Help} name when Help is present.
     *
     * @param bar The names already on the bar. The value may be null.
     * @return The return value is the bar with {@code Token Calc} included once.
     */
    public static String[] seat(String[] bar) {
        if (bar == null) {
            return new String[] { ITEM };
        }
        int helpAt = -1;
        int calcAt = -1;
        for (int i = 0; i < bar.length; i++) {
            if (ITEM.equals(bar[i])) {
                calcAt = i;
            }
            if (ToolsThinking.HELP.equals(bar[i])) {
                helpAt = i;
            }
        }
        if (calcAt >= 0) {
            return copyNames(bar);
        }
        String[] out = new String[bar.length + 1];
        if (helpAt < 0) {
            for (int i = 0; i < bar.length; i++) {
                out[i] = bar[i];
            }
            out[bar.length] = ITEM;
            return out;
        }
        for (int i = 0; i < helpAt; i++) {
            out[i] = bar[i];
        }
        out[helpAt] = ITEM;
        for (int i = helpAt; i < bar.length; i++) {
            out[i + 1] = bar[i];
        }
        return out;
    }

    /**
     * The copyNames method returns a new array with the same names in the same order.
     * The method copies a null slot as null.
     *
     * @param bar The names to copy. The caller does not pass null.
     * @return The return value is the new array.
     */
    private static String[] copyNames(String[] bar) {
        String[] copy = new String[bar.length];
        for (int i = 0; i < bar.length; i++) {
            copy[i] = bar[i];
        }
        return copy;
    }

    /**
     * The copyOf method copies a chat array.
     * The method turns a null array into a new empty array.
     * The copy is a new array.
     * The {@link Line} objects are the same objects.
     *
     * @param rows The messages to copy. The value may be null.
     * @return The return value is the copied array.
     */
    private static Line[] copyOf(Line[] rows) {
        if (rows == null) {
            return new Line[0];
        }
        Line[] copy = new Line[rows.length];
        for (int i = 0; i < rows.length; i++) {
            copy[i] = rows[i];
        }
        return copy;
    }
}
