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

// @aa-sdd PK-4183 docs/specs/java/org/aetheris/swing/swing.spec.md
/**
 * The class checks {@link AmwpFacade#seatWorkingSet} when the host prompt is blank or filled.
 * A blank prompt drops an empty system turn and drops {@link AetherisStage2#HOST_SYSTEM}.
 * A prompt that holds text fills that first place and leaves the mood lines in place.
 * The test does not open a window.
 * The test does not run a pool turn.
 * The test does not open a shell session.
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
 * @author clayl (2026-10-29)
 */
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
