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

package org.aetheris.ssh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.aetheris.swing.AetherisStage2;
import org.casserole.mood.MoodDesk;
import org.aetheris.wire.AmwpToon;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
/**
 * The class checks {@link AmwpFacade#seatWorkingSet} when the host prompt is blank or filled.
 * A blank prompt drops an empty system turn and drops {@link AetherisStage2#HOST_SYSTEM}.
 * A prompt that holds text fills that first place and leaves the mood lines in place.
 * The test does not open a window.
 * The test does not run a pool turn.
 * The test does not open a shell session.
 * // @aa-sdd L40 aa-sdd-source-prose.md#BlankHostSeatTest
 *
 * @author clayl (2026-10-29)
 */
// @aa-sdd GK-1547 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-1554 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-1094 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-691 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-692 docs/specs/common/pre-plan.spec.md
// @aa-sdd GK-2418 rust-aetheris-blank-host.draft.001.md
// @aa-sdd GK-2438 rust-aetheris-client.draft.001.md
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
class BlankHostSeatTest {

    private static final String OPENER = MoodDesk.OPENER;

    private static final String CLOSER = MoodDesk.CLOSER;

    /**
     * The method arranges turns whose texts are empty, {@link MoodDesk#OPENER}, {@code lantern-keep}, {@link MoodDesk#CLOSER}, and {@code next}.
     * The method calls {@link AmwpFacade#seatWorkingSet} with an empty host prompt.
     * The result holds four messages.
     * The texts are the opener, {@code lantern-keep}, the closer, and {@code next}.
     * The first message is a {@link SystemMessage}.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because {@link AmwpFacade#seatWorkingSet} declares a checked failure.
     */
    @Test
    void aBlankPromptDropsTheEmptySeat() throws Exception {
        List<AmwpToon.Turn> turns = new ArrayList<AmwpToon.Turn>();
        turns.add(turn("system", ""));
        turns.add(turn("system", OPENER));
        turns.add(turn("system", "lantern-keep"));
        turns.add(turn("system", CLOSER));
        turns.add(turn("user", "next"));
        List<Message> seated = AmwpFacade.seatWorkingSet("", turns);
        assertEquals(4, seated.size());
        assertEquals(OPENER, seated.get(0).getText());
        assertEquals("lantern-keep", seated.get(1).getText());
        assertEquals(CLOSER, seated.get(2).getText());
        assertEquals("next", seated.get(3).getText());
        assertTrue(seated.get(0) instanceof SystemMessage);
    }

    /**
     * The method arranges a first system turn whose text is {@link AetherisStage2#HOST_SYSTEM}, then the opener, {@code lantern-keep}, the closer, and {@code next}.
     * The method calls {@link AmwpFacade#seatWorkingSet} with an empty host prompt.
     * The result holds four messages.
     * The first text is {@link MoodDesk#OPENER}.
     * The second text is {@code lantern-keep}.
     * No message text equals {@link AetherisStage2#HOST_SYSTEM}.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because {@link AmwpFacade#seatWorkingSet} declares a checked failure.
     */
    @Test
    void aBlankPromptDropsTheVestigeSentence() throws Exception {
        List<AmwpToon.Turn> turns = new ArrayList<AmwpToon.Turn>();
        turns.add(turn("system", AetherisStage2.HOST_SYSTEM));
        turns.add(turn("system", OPENER));
        turns.add(turn("system", "lantern-keep"));
        turns.add(turn("system", CLOSER));
        turns.add(turn("user", "next"));
        List<Message> seated = AmwpFacade.seatWorkingSet("", turns);
        assertEquals(4, seated.size());
        assertEquals(OPENER, seated.get(0).getText());
        assertEquals("lantern-keep", seated.get(1).getText());
        assertEquals(0, countText(seated, AetherisStage2.HOST_SYSTEM));
    }

    /**
     * The method arranges an empty system turn, the opener, {@code lantern-keep}, the closer, and {@code next}.
     * The method calls {@link AmwpFacade#seatWorkingSet} with the host prompt {@code custom host}.
     * The result holds five messages.
     * The texts are {@code custom host}, the opener, {@code lantern-keep}, the closer, and {@code next}.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because {@link AmwpFacade#seatWorkingSet} declares a checked failure.
     */
    @Test
    void aConfiguredPromptFillsTheSeat() throws Exception {
        List<AmwpToon.Turn> turns = new ArrayList<AmwpToon.Turn>();
        turns.add(turn("system", ""));
        turns.add(turn("system", OPENER));
        turns.add(turn("system", "lantern-keep"));
        turns.add(turn("system", CLOSER));
        turns.add(turn("user", "next"));
        List<Message> seated = AmwpFacade.seatWorkingSet("custom host", turns);
        assertEquals(5, seated.size());
        assertEquals("custom host", seated.get(0).getText());
        assertEquals(OPENER, seated.get(1).getText());
        assertEquals("lantern-keep", seated.get(2).getText());
        assertEquals(CLOSER, seated.get(3).getText());
        assertEquals("next", seated.get(4).getText());
    }

    /**
     * The method arranges a system turn {@code shape line} and a user turn {@code next}.
     * The method calls {@link AmwpFacade#seatWorkingSet} once with an empty host prompt and once with {@code custom host}.
     * The empty prompt returns two messages.
     * The first text of that result is {@code shape line}.
     * The configured prompt returns {@code custom host} in the first message.
     * The second message of that result is {@code next}.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because {@link AmwpFacade#seatWorkingSet} declares a checked failure.
     */
    @Test
    void aBlankPromptLeavesAnotherFirstSystemLine() throws Exception {
        List<AmwpToon.Turn> turns = new ArrayList<AmwpToon.Turn>();
        turns.add(turn("system", "shape line"));
        turns.add(turn("user", "next"));
        List<Message> seated = AmwpFacade.seatWorkingSet("", turns);
        assertEquals(2, seated.size());
        assertEquals("shape line", seated.get(0).getText());
        List<Message> replaced = AmwpFacade.seatWorkingSet("custom host", turns);
        assertEquals("custom host", replaced.get(0).getText());
        assertEquals("next", replaced.get(1).getText());
    }

    /**
     * The method arranges one user turn whose text is {@code next}.
     * The method calls {@link AmwpFacade#seatWorkingSet} once with an empty host prompt and once with {@code custom host}.
     * The empty prompt returns one message.
     * That message text is {@code next}.
     * The configured prompt returns {@code custom host} in the first message.
     * The second message of that result is {@code next}.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because {@link AmwpFacade#seatWorkingSet} declares a checked failure.
     */
    @Test
    void aBlankPromptAddsNoHostToTurns() throws Exception {
        List<AmwpToon.Turn> turns = new ArrayList<AmwpToon.Turn>();
        turns.add(turn("user", "next"));
        List<Message> seated = AmwpFacade.seatWorkingSet("", turns);
        assertEquals(1, seated.size());
        assertEquals("next", seated.get(0).getText());
        List<Message> hosted = AmwpFacade.seatWorkingSet("custom host", turns);
        assertEquals("custom host", hosted.get(0).getText());
        assertEquals("next", hosted.get(1).getText());
    }

    /**
     * The method builds one {@link AmwpToon.Turn}.
     * The method stores {@code role} on that turn.
     * The method stores {@code text} on that turn.
     *
     * @param role The role is the string stored in the turn role field.
     * @param text The text is the string stored in the turn text field.
     * @return The method returns the new turn.
     */
    private static AmwpToon.Turn turn(String role, String text) {
        AmwpToon.Turn turn = new AmwpToon.Turn();
        turn.role = role;
        turn.text = text;
        return turn;
    }

    /**
     * The method counts entries whose text equals {@code text}.
     * The count skips a null message.
     *
     * @param messages The list is the message sequence the method scans.
     * @param text The text is the value compared with each message.
     * @return The method returns the number of matches.
     */
    private static int countText(List<Message> messages, String text) {
        int count = 0;
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message != null && text.equals(message.getText())) {
                count = count + 1;
            }
        }
        return count;
    }
}
