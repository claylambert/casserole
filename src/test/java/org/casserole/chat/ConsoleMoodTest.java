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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.casserole.mood.MoodDesk;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
/**
 * The class checks {@link ChatSession#seatMood} and {@link ChatSession#unseatMood}.
 * The mood lines sit after the pinned system line.
 * The test does not open a stored transcript.
 * The test does not open a shell session.
 *
 * @author clayl (2026-10-29)
 */
// @aa-sdd GK-1548 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-1555 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-771 docs/specs/common/stages.spec.md
// @aa-sdd GK-1576 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-1094 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-648 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1083 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1083 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-652 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-691 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-692 docs/specs/common/pre-plan.spec.md
// @aa-sdd GK-1987 aetheris-pre-plan-f-196.001.md
// @aa-sdd GK-1991 aetheris-pre-plan-f-198.001.md
// @aa-sdd GK-2419 rust-aetheris-blank-host.draft.001.md
// @aa-sdd GK-2439 rust-aetheris-client.draft.001.md
// @aa-sdd GK-2494 rust-aetheris-ssh-mood-finesse.draft.001.md
// @aa-sdd GK-2496 rust-aetheris-ssh-mood-finesse.draft.001.md
// @aa-sdd GK-2499 rust-aetheris-ssh-mood-finesse.draft.001.md
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
class ConsoleMoodTest {

    private static final String OPENER = MoodDesk.OPENER;

    private static final String CLOSER = MoodDesk.CLOSER;

    /**
     * The method arranges the sample list from {@code sample}.
     * The method calls {@link ChatSession#seatMood} with null and then with three spaces.
     * The list still holds three messages.
     * The texts stay {@code host line}, {@code earlier}, and {@code now}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBlankMoodLeavesThePrompt() {
        List<Message> messages = sample();
        ChatSession.seatMood(messages, null);
        ChatSession.seatMood(messages, "   ");
        assertEquals(3, messages.size());
        assertEquals("host line", messages.get(0).getText());
        assertEquals("earlier", messages.get(1).getText());
        assertEquals("now", messages.get(2).getText());
    }

    /**
     * The method arranges the sample list and the mood text {@code lantern-keep} with surrounding spaces.
     * The method calls {@link ChatSession#seatMood}.
     * The list holds six messages.
     * The texts are {@code host line}, {@link MoodDesk#OPENER}, {@code lantern-keep}, {@link MoodDesk#CLOSER}, {@code earlier}, and {@code now}.
     * The opener, the mood line, and the closer are each a {@link SystemMessage}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theMoodSitsAfterThePinnedSystem() {
        List<Message> messages = sample();
        ChatSession.seatMood(messages, " lantern-keep ");
        assertEquals(6, messages.size());
        assertEquals("host line", messages.get(0).getText());
        assertEquals(OPENER, messages.get(1).getText());
        assertEquals("lantern-keep", messages.get(2).getText());
        assertEquals(CLOSER, messages.get(3).getText());
        assertEquals("earlier", messages.get(4).getText());
        assertEquals("now", messages.get(5).getText());
        assertTrue(messages.get(1) instanceof SystemMessage);
        assertTrue(messages.get(2) instanceof SystemMessage);
        assertTrue(messages.get(3) instanceof SystemMessage);
    }

    /**
     * The method arranges the sample list.
     * The method calls {@link ChatSession#seatMood} twice with {@code lantern-keep}.
     * The list holds six messages.
     * The second, third, and fourth texts are the opener, {@code lantern-keep}, and the closer.
     *
     * @return The method returns nothing.
     */
    @Test
    void aSecondSeatLeavesThePair() {
        List<Message> messages = sample();
        ChatSession.seatMood(messages, "lantern-keep");
        ChatSession.seatMood(messages, "lantern-keep");
        assertEquals(6, messages.size());
        assertEquals(OPENER, messages.get(1).getText());
        assertEquals("lantern-keep", messages.get(2).getText());
        assertEquals(CLOSER, messages.get(3).getText());
    }

    /**
     * The method arranges the sample list, seats {@code lantern-keep}, and then seats {@code spare, dry}.
     * The method calls {@link ChatSession#unseatMood}.
     * The list holds three messages.
     * The texts are {@code host line}, {@code earlier}, and {@code now}.
     *
     * @return The method returns nothing.
     */
    @Test
    void anOffPromptDropsThePairAndKeepsTheHost() {
        List<Message> messages = sample();
        ChatSession.seatMood(messages, "lantern-keep");
        ChatSession.seatMood(messages, "spare, dry");
        ChatSession.unseatMood(messages);
        assertEquals(3, messages.size());
        assertEquals("host line", messages.get(0).getText());
        assertEquals("earlier", messages.get(1).getText());
        assertEquals("now", messages.get(2).getText());
    }

    /**
     * The method arranges one {@link UserMessage} whose text is {@code now}.
     * The method calls {@link ChatSession#seatMood} with {@code lantern-keep}.
     * The texts are the opener, {@code lantern-keep}, the closer, and {@code now}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aPromptWithNoSystemTakesTheMoodFirst() {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new UserMessage("now"));
        ChatSession.seatMood(messages, "lantern-keep");
        assertEquals(OPENER, messages.get(0).getText());
        assertEquals("lantern-keep", messages.get(1).getText());
        assertEquals(CLOSER, messages.get(2).getText());
        assertEquals("now", messages.get(3).getText());
    }

    /**
     * The method arranges one {@link UserMessage} whose text is {@code now}.
     * The method calls {@link ChatSession#seatMood} twice with {@code lantern-keep}, then {@link ChatSession#unseatMood}.
     * After the second seat the list holds four messages.
     * Those texts are the opener, {@code lantern-keep}, the closer, and {@code now}.
     * After the unseat the list holds one message.
     * That text is {@code now}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aLeadingPairSeatsOnceAndUnseatsClean() {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new UserMessage("now"));
        ChatSession.seatMood(messages, "lantern-keep");
        ChatSession.seatMood(messages, "lantern-keep");
        assertEquals(4, messages.size());
        assertEquals(OPENER, messages.get(0).getText());
        assertEquals("lantern-keep", messages.get(1).getText());
        assertEquals(CLOSER, messages.get(2).getText());
        assertEquals("now", messages.get(3).getText());
        ChatSession.unseatMood(messages);
        assertEquals(1, messages.size());
        assertEquals("now", messages.get(0).getText());
    }

    /**
     * The method builds a system message {@code host line} and two user messages.
     * The user texts are {@code earlier} and {@code now}.
     *
     * @return The method returns that three-message list.
     */
    private static List<Message> sample() {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new SystemMessage("host line"));
        messages.add(new UserMessage("earlier"));
        messages.add(new UserMessage("now"));
        return messages;
    }
}
