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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.roux.RouxEngine;
import org.roux.RouxProperties;
import org.roux.crypto.CryptoSink;
import org.roux.id.RouxIdGenerator;
import org.roux.session.OpenResult;
import org.roux.spill.RegionPack;
import org.roux.spring.RouxChatMemory;
import org.roux.tel.RouxCounters;
import org.roux.token.HeuristicTokenCounter;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;

// @aa-sdd PK-1094 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1083 docs/specs/common/formal-plan.spec.md
/**
 * The class checks an established {@link ChatSession} that keeps the mood in {@link RouxChatMemory} while the line is set.
 * The test does not open a window.
 * The test does not open a shell connection.
 *
 * @author clayl (2026-10-29)
 */
// @aa-sdd GK-1552 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-1559 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-1567 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-771 docs/specs/common/stages.spec.md
// @aa-sdd GK-1579 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-1089 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1089 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-691 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-692 docs/specs/common/pre-plan.spec.md
// @aa-sdd GK-1982 aetheris-pre-plan-f-190.001.md
// @aa-sdd GK-1990 aetheris-pre-plan-f-196.001.md
// @aa-sdd GK-1994 aetheris-pre-plan-f-198.001.md
// @aa-sdd GK-2421 rust-aetheris-blank-host.draft.001.md
// @aa-sdd GK-2441 rust-aetheris-client.draft.001.md
// @aa-sdd GK-2498 rust-aetheris-ssh-mood-finesse.draft.001.md
// @aa-sdd GK-2501 rust-aetheris-ssh-mood-finesse.draft.001.md
// @aa-sdd PK-1094 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-648 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-649 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1083 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1083 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-652 docs/specs/common/formal-plan.spec.md
class SshMoodMemoryTest {

    private static final String OPENER = MoodDesk.OPENER;

    private static final String CLOSER = MoodDesk.CLOSER;

    private RouxEngine engine;
    private RouxChatMemory memory;

    /**
     * The method arranges {@link RouxProperties} with durability {@code hot-only} and spill disabled.
     * The method builds a {@link RouxEngine} and a {@link RouxChatMemory} that counts tokens with {@link HeuristicTokenCounter}.
     *
     * @return The method returns nothing.
     */
    @BeforeEach
    void ramOnly() {
        RouxProperties properties = new RouxProperties();
        properties.setDurability("hot-only");
        properties.getSpill().setEnabled(false);
        engine = new RouxEngine(properties, new RegionPack(properties), new RouxCounters(),
                new CryptoSink(properties), new RouxIdGenerator());
        memory = new RouxChatMemory(engine, new HeuristicTokenCounter());
    }

    /**
     * The method shuts the engine down when the field is not null.
     * The argument to {@link RouxEngine#shutdown} is true.
     *
     * @return The method returns nothing.
     */
    @AfterEach
    void closeEngine() {
        if (engine != null) {
            engine.shutdown(true);
        }
    }

    /**
     * The method arranges a {@link ChatSession} with host text {@code host line} and several turns.
     * The method calls {@link ChatSession#setMoodLine}, {@link ChatSession#buildPrompt}, and {@link RouxChatMemory#getWindow}.
     * One system message is present before the mood line is set.
     * After {@code lantern-keep} the stored list holds six messages, still with one system message.
     * The ends of that list are {@code host line} and {@code three}.
     * A prompt budget of {@code 40} holds seven instructions.
     * The last instruction wears {@code lantern-keep} and ends with {@code next}.
     * A prompt budget of {@code 1} still starts with {@code host line} and wears {@code lantern-keep} on the last instruction.
     * A memory window of one message pins {@code host line} and ends with {@code three}.
     * Setting the same mood line again leaves six stored messages.
     * Setting {@code spare, dry} leaves {@code earlier} in the second stored message and stores no copy of {@code spare, dry}.
     * The next prompt wears {@code spare, dry} and holds no {@code lantern-keep}.
     *
     * @return The method returns nothing.
     */
    @Test
    void anEstablishedSessionKeepsTheMood() {
        ChatSession session = new ChatSession("host line", memory);
        try {
            session.appendUser("earlier");
            session.appendAssistant("reply");
            session.appendUser("one");
            session.appendUser("two");
            session.appendUser("three");
            assertEquals(1, session.countByType(MessageType.SYSTEM));

            session.setMoodLine(" lantern-keep ");
            List<Message> stored = session.getMessages();
            assertEquals(6, stored.size());
            assertEquals("host line", stored.get(0).getText());
            assertEquals("earlier", stored.get(1).getText());
            assertEquals("three", stored.get(5).getText());
            assertEquals(1, session.countByType(MessageType.SYSTEM));

            Prompt prompt = session.buildPrompt(40, "next");
            List<Message> instructions = prompt.getInstructions();
            assertEquals(7, instructions.size());
            assertEquals("host line", instructions.get(0).getText());
            assertEquals("earlier", instructions.get(1).getText());
            assertTrue(wears(instructions.get(6).getText(), "lantern-keep"));
            assertTrue(instructions.get(6).getText().endsWith("next"));

            Prompt narrow = session.buildPrompt(1, "ask");
            List<Message> narrowLines = narrow.getInstructions();
            assertEquals("host line", narrowLines.get(0).getText());
            assertTrue(wears(narrowLines.get(narrowLines.size() - 1).getText(), "lantern-keep"));

            List<Message> window = memory.getWindow(session.getConversationId(), 1, true, 0);
            assertEquals("host line", window.get(0).getText());
            assertEquals("three", window.get(window.size() - 1).getText());

            session.setMoodLine("lantern-keep");
            assertEquals(6, session.getMessages().size());

            session.setMoodLine("spare, dry");
            stored = session.getMessages();
            assertEquals("earlier", stored.get(1).getText());
            assertEquals(0, countText(stored, "spare, dry"));
            Prompt moved = session.buildPrompt(40, "later");
            List<Message> movedLines = moved.getInstructions();
            assertTrue(wears(movedLines.get(movedLines.size() - 1).getText(), "spare, dry"));
            assertEquals(0, countText(movedLines, "lantern-keep"));
        } finally {
            session.close();
        }
    }

    /**
     * The method arranges a session with {@code earlier}, {@code reply}, and mood {@code lantern-keep}.
     * The method calls {@link ChatSession#setMoodLine} with an empty string.
     * The stored list holds three messages: {@code host line}, {@code earlier}, and {@code reply}.
     * The next prompt holds four instructions and ends with {@code next}.
     * That prompt contains neither {@code lantern-keep} nor {@link MoodDesk#CLOSER}.
     * Setting {@code lantern-keep} again stores it on the second message and keeps {@code reply} third.
     *
     * @return The method returns nothing.
     */
    @Test
    void aClearDropsTheMoodAndKeepsTheTurns() {
        ChatSession session = new ChatSession("host line", memory);
        try {
            session.appendUser("earlier");
            session.appendAssistant("reply");
            session.setMoodLine("lantern-keep");
            session.setMoodLine("");
            List<Message> stored = session.getMessages();
            assertEquals(3, stored.size());
            assertEquals("host line", stored.get(0).getText());
            assertEquals("earlier", stored.get(1).getText());
            assertEquals("reply", stored.get(2).getText());
            Prompt prompt = session.buildPrompt(40, "next");
            assertEquals(4, prompt.getInstructions().size());
            assertEquals("host line", prompt.getInstructions().get(0).getText());
            assertEquals("earlier", prompt.getInstructions().get(1).getText());
            assertEquals("next", prompt.getInstructions().get(3).getText());
            assertEquals(0, countText(prompt.getInstructions(), "lantern-keep"));
            assertEquals(0, countText(prompt.getInstructions(), CLOSER));
            session.setMoodLine("lantern-keep");
            stored = session.getMessages();
            assertEquals("host line", stored.get(0).getText());
            assertTrue(wears(stored.get(1).getText(), "lantern-keep"));
            assertEquals("reply", stored.get(2).getText());
        } finally {
            session.close();
        }
    }

    /**
     * The method arranges a session, appends {@code earlier}, and sets {@code lantern-keep}.
     * The method calls {@link ChatSession#reset} and then {@link ChatSession#clearTurns}.
     * After reset the stored list holds only {@code host line}.
     * {@link ChatSession#isActive} is true.
     * The next prompt still wears {@code lantern-keep}.
     * After clearing turns the stored list again holds only {@code host line}.
     *
     * @return The method returns nothing.
     */
    @Test
    void chatStartKeepsTheSeatedMood() {
        ChatSession session = new ChatSession("host line", memory);
        try {
            session.appendUser("earlier");
            session.setMoodLine("lantern-keep");
            session.reset();
            List<Message> stored = session.getMessages();
            assertEquals(1, stored.size());
            assertEquals("host line", stored.get(0).getText());
            assertTrue(session.isActive());
            Prompt prompt = session.buildPrompt(40, "next");
            assertTrue(wears(prompt.getInstructions().get(prompt.getInstructions().size() - 1).getText(),
                    "lantern-keep"));
            session.clearTurns();
            stored = session.getMessages();
            assertEquals(1, stored.size());
            assertEquals("host line", stored.get(0).getText());
        } finally {
            session.close();
        }
    }

    /**
     * The method arranges a RAM region {@code ssh-mood-user}, stores {@code earlier}, and sets {@code lantern-keep}.
     * The method opens a second {@link ChatSession} on that same region.
     * The restored prompt holds three instructions.
     * Those texts are {@code host line}, a line that wears {@code lantern-keep}, and {@code next}.
     * Setting the same mood line again does not change the stored size.
     * Setting {@code spare, dry} stores that line second, keeps {@code host line} first, and puts {@code spare, dry} on the next prompt before {@code next}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aRestoredSessionKeepsTheSeat() {
        OpenResult open = memory.openRam("ssh-mood-user");
        ChatSession seated = ChatSession.forUser("ssh-mood-user", open, "host line", memory);
        ChatSession restored = null;
        try {
            seated.appendUser("earlier");
            seated.setMoodLine("lantern-keep");
            restored = ChatSession.forUser("ssh-mood-user", open, "host line", memory);
            Prompt prompt = restored.buildPrompt(40, "next");
            List<Message> instructions = prompt.getInstructions();
            assertEquals(3, instructions.size());
            assertEquals("host line", instructions.get(0).getText());
            assertTrue(wears(instructions.get(1).getText(), "lantern-keep"));
            assertEquals("next", instructions.get(2).getText());
            int held = restored.getMessages().size();
            restored.setMoodLine("lantern-keep");
            assertEquals(held, restored.getMessages().size());
            restored.setMoodLine("spare, dry");
            List<Message> stored = restored.getMessages();
            assertTrue(wears(stored.get(1).getText(), "spare, dry"));
            assertEquals("host line", stored.get(0).getText());
            instructions = restored.buildPrompt(40, "next").getInstructions();
            assertTrue(wears(instructions.get(1).getText(), "spare, dry"));
            assertEquals("next", instructions.get(2).getText());
        } finally {
            seated.close();
            if (restored != null) {
                restored.close();
            }
        }
    }

    /**
     * The method arranges region {@code ssh-mood-override} with {@code earlier}, {@code reply}, and mood {@code spare, dry}.
     * The method restores a second session and then sets {@code lantern-keep}.
     * The restored prompt wears {@code spare, dry} and ends with {@code next}.
     * After the change the stored second text wears {@code lantern-keep}.
     * The first stored text stays {@code host line}.
     * The third stored text stays {@code reply}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aRestoredSessionReplacesADifferentStoredMood() {
        OpenResult open = memory.openRam("ssh-mood-override");
        ChatSession seated = ChatSession.forUser("ssh-mood-override", open, "host line", memory);
        ChatSession restored = null;
        try {
            seated.appendUser("earlier");
            seated.appendAssistant("reply");
            seated.setMoodLine("spare, dry");
            restored = ChatSession.forUser("ssh-mood-override", open, "host line", memory);
            List<Message> restoredLines = restored.buildPrompt(40, "next").getInstructions();
            assertTrue(wears(restoredLines.get(1).getText(), "spare, dry"));
            assertEquals("next", restoredLines.get(restoredLines.size() - 1).getText());
            restored.setMoodLine("lantern-keep");
            List<Message> stored = restored.getMessages();
            assertTrue(wears(stored.get(1).getText(), "lantern-keep"));
            assertEquals("host line", stored.get(0).getText());
            assertEquals("reply", stored.get(2).getText());
        } finally {
            seated.close();
            if (restored != null) {
                restored.close();
            }
        }
    }

    /**
     * The method arranges region {@code ssh-mood-blank} with an empty host prompt, {@code earlier}, and mood {@code lantern-keep}.
     * The method restores a second session on that region.
     * The stored list holds one message, and that message wears {@code lantern-keep}.
     * The next prompt wears {@code lantern-keep} and then {@code next}.
     * The prompt does not contain {@code host line}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aRestoredBlankPromptKeepsThePairFirst() {
        OpenResult open = memory.openRam("ssh-mood-blank");
        ChatSession seated = ChatSession.forUser("ssh-mood-blank", open, "", memory);
        ChatSession restored = null;
        try {
            seated.appendUser("earlier");
            seated.setMoodLine("lantern-keep");
            restored = ChatSession.forUser("ssh-mood-blank", open, "", memory);
            List<Message> stored = restored.getMessages();
            assertEquals(1, stored.size());
            assertTrue(wears(stored.get(0).getText(), "lantern-keep"));
            List<Message> lines = restored.buildPrompt(40, "next").getInstructions();
            assertTrue(wears(lines.get(0).getText(), "lantern-keep"));
            assertEquals("next", lines.get(1).getText());
            assertEquals(0, countText(lines, "host line"));
        } finally {
            seated.close();
            if (restored != null) {
                restored.close();
            }
        }
    }

    /**
     * The method arranges region {@code mood-fallback} with a user line, an assistant line, and a later system line {@code later-host}.
     * The method calls {@link RouxChatMemory#add} and {@link RouxChatMemory#getWindow} with the pin flag true.
     * The window holds three messages.
     * The first text is {@code later-host}.
     * The second text is {@code earlier}.
     * The third text is {@code reply}.
     * The method then calls {@link RouxEngine#persistClose}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aLogThatDoesNotOpenOnSystemStillPinsTheLastSystem() {
        String region = "mood-fallback";
        List<Message> batch = new ArrayList<Message>();
        batch.add(new UserMessage("earlier"));
        batch.add(new AssistantMessage("reply"));
        batch.add(new SystemMessage("later-host"));
        memory.add(region, batch);
        List<Message> window = memory.getWindow(region, 10, true, 0);
        assertEquals(3, window.size());
        assertEquals("later-host", window.get(0).getText());
        assertEquals("earlier", window.get(1).getText());
        assertEquals("reply", window.get(2).getText());
        engine.persistClose(region);
    }

    /**
     * The method arranges a {@link ChatSession} with an empty host prompt.
     * The system count starts at {@code 0}.
     * After {@code earlier} and {@code lantern-keep} the stored list holds one message that wears {@code lantern-keep}.
     * {@link ChatSession#porchRules} returns {@code mood: lantern-keep}, {@code shape:}, the opener, and the closer.
     * Setting the same line again leaves one stored message.
     * The next prompt wears {@code lantern-keep} and ends with {@code next}.
     * {@link ChatSession#reset} leaves an empty stored list.
     * An empty mood line and {@link ChatSession#clearTurns} leave the stored list empty.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBlankPromptStartsWithoutASystemFrame() {
        ChatSession session = new ChatSession("", memory);
        try {
            assertEquals(0, session.countByType(MessageType.SYSTEM));
            session.appendUser("earlier");
            session.setMoodLine("lantern-keep");
            List<Message> stored = session.getMessages();
            assertEquals(1, stored.size());
            assertTrue(wears(stored.get(0).getText(), "lantern-keep"));
            assertEquals("mood: lantern-keep\nshape:\n" + MoodDesk.OPENER + "\n" + MoodDesk.CLOSER,
                    ChatSession.porchRules(stored));
            session.setMoodLine("lantern-keep");
            assertEquals(1, session.getMessages().size());
            Prompt prompt = session.buildPrompt(40, "next");
            List<Message> lines = prompt.getInstructions();
            assertTrue(wears(lines.get(0).getText(), "lantern-keep"));
            assertEquals("next", lines.get(lines.size() - 1).getText());
            session.reset();
            stored = session.getMessages();
            assertEquals(0, stored.size());
            session.setMoodLine("");
            session.clearTurns();
            assertEquals(0, session.getMessages().size());
        } finally {
            session.close();
        }
    }

    /**
     * The method checks the mood prefix on {@code text}.
     * The prefix is {@code mood:}, the line, {@code shape:}, {@link MoodDesk#OPENER}, and {@link MoodDesk#CLOSER}.
     *
     * @param text The text is the message body, and it may be null.
     * @param line The line is the mood text expected after {@code mood:}.
     * @return The method returns true when {@code text} starts with that prefix.
     */
    private static boolean wears(String text, String line) {
        return text != null && text.startsWith("mood: " + line + "\nshape:\n" + OPENER + "\n" + CLOSER + "\n");
    }

    /**
     * The method counts entries whose text equals {@code text}.
     * The count skips a null message.
     *
     * @param messages The list is the message sequence the method reads.
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
