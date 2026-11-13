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

package org.casserole.mood;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

// @aa-sdd PK-1094 docs/specs/common/formal-plan.spec.md FINESSE:f-185
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md FINESSE:f-187
// @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md FINESSE:f-196
// @aa-sdd PK-1083 docs/specs/common/formal-plan.spec.md FINESSE:f-198
/**
 * The class checks {@link MoodDesk}.
 * One desk builds the canvas prefix, the shell announce, and the stored transcript.
 * The test does not open a window.
 * The test does not open a shell session.
 *
 * @author clayl (2026-11-03)
 */
// @aa-sdd GK-1549 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-1556 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-1565 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-771 docs/specs/common/stages.spec.md
// @aa-sdd GK-1577 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-1089 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1089 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-691 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-692 docs/specs/common/pre-plan.spec.md
// @aa-sdd GK-1981 aetheris-pre-plan-f-190.001.md
// @aa-sdd GK-1988 aetheris-pre-plan-f-196.001.md
// @aa-sdd GK-1992 aetheris-pre-plan-f-198.001.md
// @aa-sdd GK-2452 rust-aetheris-client.draft.001.md
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
class MoodDeskTest {

    /**
     * The method arranges the line {@code lantern-keep} and calls {@link MoodDesk#canvasSystems} and {@link MoodDesk#moodHead}.
     * A true switch and that line return the opener, {@code lantern-keep}, and the closer.
     * {@link MoodDesk#moodHead} joins those three lines and ends with a line feed.
     * A false switch returns an empty list.
     * A null line returns an empty list.
     * A blank line returns an empty list.
     * {@link MoodDesk#moodHead} returns null for a false switch.
     * {@link MoodDesk#moodHead} returns null for an empty line.
     *
     * @return The method returns nothing.
     */
    @Test
    void canvasSystemsFollowTheSwitchAndTheLine() {
        List<String> on = MoodDesk.canvasSystems(true, " lantern-keep ");
        assertEquals(3, on.size());
        assertEquals(MoodDesk.OPENER, on.get(0));
        assertEquals("lantern-keep", on.get(1));
        assertEquals(MoodDesk.CLOSER, on.get(2));
        assertEquals(MoodDesk.OPENER + "\n" + "lantern-keep\n" + MoodDesk.CLOSER + "\n",
                MoodDesk.moodHead(true, "lantern-keep"));

        assertEquals(0, MoodDesk.canvasSystems(false, "lantern-keep").size());
        assertEquals(0, MoodDesk.canvasSystems(true, null).size());
        assertEquals(0, MoodDesk.canvasSystems(true, "   ").size());
        assertNull(MoodDesk.moodHead(false, "lantern-keep"));
        assertNull(MoodDesk.moodHead(true, ""));
    }

    /**
     * The method arranges the line {@code lantern-keep} and calls {@link MoodDesk#sshLine}.
     * Both flags true return {@code lantern-keep}.
     * A false second flag returns null.
     * A false first flag returns null.
     * Both flags false return null.
     * A blank line returns null.
     * A null line returns null.
     *
     * @return The method returns nothing.
     */
    @Test
    void sshLineReadsTheCheckboxTheSwitchAndTheLine() {
        assertEquals("lantern-keep", MoodDesk.sshLine(true, true, " lantern-keep "));
        assertNull(MoodDesk.sshLine(true, false, "lantern-keep"));
        assertNull(MoodDesk.sshLine(false, true, "lantern-keep"));
        assertNull(MoodDesk.sshLine(false, false, "lantern-keep"));
        assertNull(MoodDesk.sshLine(true, true, "   "));
        assertNull(MoodDesk.sshLine(true, true, null));
    }

    /**
     * The method arranges mood lines and calls {@link MoodDesk#announceBody}.
     * A fresh pane with an empty line returns {@code shell_mood_clear:} and a line feed.
     * A fresh pane with a null previous line and a blank next line returns that same clear frame.
     * A fresh pane with {@code lantern-keep} returns {@code shell_mood:} and that line.
     * A fresh pane returns that frame even when the previous line already matches.
     * A repeat on a pane that is not fresh returns null when the line still matches.
     * A repeat of an empty line on a pane that is not fresh returns null.
     * A blank previous line and a null next line return null when the pane is not fresh.
     * A changed line {@code spare, dry} returns {@code shell_mood:} and that line.
     * Clearing a previous {@code lantern-keep} returns {@code shell_mood_clear:}.
     *
     * @return The method returns nothing.
     */
    @Test
    void announceBodyTellsAFreshPaneAndSkipsAMatch() {
        assertEquals("shell_mood_clear:\n", MoodDesk.announceBody(false, "", ""));
        assertEquals("shell_mood_clear:\n", MoodDesk.announceBody(false, null, "   "));
        assertEquals("shell_mood:\nlantern-keep\n", MoodDesk.announceBody(false, "", " lantern-keep "));
        assertEquals("shell_mood:\nlantern-keep\n", MoodDesk.announceBody(false, "lantern-keep", "lantern-keep"));
        assertNull(MoodDesk.announceBody(true, "lantern-keep", " lantern-keep "));
        assertNull(MoodDesk.announceBody(true, "", ""));
        assertNull(MoodDesk.announceBody(true, "   ", null));
        assertEquals("shell_mood:\nspare, dry\n", MoodDesk.announceBody(true, "lantern-keep", "spare, dry"));
        assertEquals("shell_mood_clear:\n", MoodDesk.announceBody(true, "lantern-keep", ""));
        assertEquals("shell_mood:\nlantern-keep\n", MoodDesk.announceBody(false, "lantern-keep", "lantern-keep"));
    }

    /**
     * The method calls {@link MoodDesk#parseShell} on frames from {@link MoodDesk#announceBody}, and it calls {@link MoodDesk#normalize}.
     * A set frame parses back to {@code lantern-keep}.
     * A clear frame parses back to an empty string.
     * {@code spare, dry} parses back to that line.
     * A clear frame literal parses to an empty string.
     * A {@code shell_model} frame returns null.
     * A null frame returns null.
     * {@link MoodDesk#normalize} returns null for null and for spaces.
     * {@link MoodDesk#normalize} trims {@code lantern-keep}.
     *
     * @return The method returns nothing.
     */
    @Test
    void parseShellReadsTheFrameTheDeskWrites() {
        assertEquals("lantern-keep", MoodDesk.parseShell(MoodDesk.announceBody(false, "", "lantern-keep")));
        assertEquals("", MoodDesk.parseShell(MoodDesk.announceBody(false, "lantern-keep", "")));
        assertEquals("spare, dry", MoodDesk.parseShell("shell_mood:\nspare, dry\n"));
        assertEquals("", MoodDesk.parseShell("shell_mood_clear:\n"));
        assertNull(MoodDesk.parseShell("shell_model:\n"));
        assertNull(MoodDesk.parseShell(null));
        assertNull(MoodDesk.normalize(null));
        assertNull(MoodDesk.normalize("  "));
        assertEquals("lantern-keep", MoodDesk.normalize(" lantern-keep "));
    }

    /**
     * The method arranges the turn {@code next} and calls {@link MoodDesk#spliceTurn}, {@link MoodDesk#lineAtTurnHead}, {@link MoodDesk#unspliceTurn}, and {@link MoodDesk#porchRules}.
     * The spliced text starts with {@code mood: lantern-keep}, {@code shape:}, the opener, and the closer.
     * The spliced text does not place {@code lantern-keep} on its own line before the opener.
     * The spliced text ends with {@code next}.
     * {@link MoodDesk#lineAtTurnHead} reads {@code lantern-keep}.
     * {@link MoodDesk#unspliceTurn} returns {@code next}.
     * A blank line returns {@code next} unchanged.
     * An older triple that still names {@code lantern-keep} reads that line.
     * Splicing that older text again writes the new head in front of {@code next}.
     * {@link MoodDesk#porchRules} on a prompt that ends with the splice equals the head without its last line feed.
     * {@link MoodDesk#adopt} on that prompt returns {@code lantern-keep}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aSplicedTurnWearsTheTripleAtItsHead() {
        String spliced = MoodDesk.spliceTurn("next", " lantern-keep ");
        String head = "mood: lantern-keep\nshape:\n" + MoodDesk.OPENER + "\n" + MoodDesk.CLOSER + "\n";
        assertTrue(spliced.startsWith(head));
        assertFalse(spliced.contains("\nlantern-keep\n" + MoodDesk.OPENER));
        assertTrue(spliced.endsWith("next"));
        assertEquals("lantern-keep", MoodDesk.lineAtTurnHead(spliced));
        assertEquals("next", MoodDesk.unspliceTurn(spliced));
        assertEquals("next", MoodDesk.spliceTurn("next", "  "));
        String older = MoodDesk.OPENER + "\nlantern-keep\n" + MoodDesk.CLOSER + "\nnext";
        assertEquals("lantern-keep", MoodDesk.lineAtTurnHead(older));
        assertEquals(head + "next", MoodDesk.spliceTurn(older, "lantern-keep"));
        List<Message> prompt = new ArrayList<Message>();
        prompt.add(new UserMessage("earlier"));
        prompt.add(new UserMessage(spliced));
        assertEquals(head.substring(0, head.length() - 1), MoodDesk.DESK.porchRules(prompt));
        assertEquals("lantern-keep", MoodDesk.DESK.adopt(prompt));
    }

    /**
     * The method arranges a host message {@code host line}, a user message {@code earlier}, and an assistant message {@code reply}.
     * The method calls {@link MoodDesk#seat} with a null previous line and next line {@code lantern-keep}.
     * The result holds six messages.
     * The host object stays first.
     * The next three texts are the opener, {@code lantern-keep}, and the closer.
     * The user object and the assistant object stay after that triple.
     * Those three mood messages are {@link SystemMessage} objects.
     *
     * @return The method returns nothing.
     */
    @Test
    void seatPutsThePairAfterTheHostAndKeepsTheTurns() {
        SystemMessage host = new SystemMessage("host line");
        UserMessage earlier = new UserMessage("earlier");
        AssistantMessage reply = new AssistantMessage("reply");
        List<Message> stored = new ArrayList<Message>();
        stored.add(host);
        stored.add(earlier);
        stored.add(reply);
        List<Message> seated = MoodDesk.DESK.seat(stored, "host line", null, " lantern-keep ");
        assertEquals(6, seated.size());
        assertSame(host, seated.get(0));
        assertEquals(MoodDesk.OPENER, seated.get(1).getText());
        assertEquals("lantern-keep", seated.get(2).getText());
        assertEquals(MoodDesk.CLOSER, seated.get(3).getText());
        assertSame(earlier, seated.get(4));
        assertSame(reply, seated.get(5));
        assertTrue(seated.get(1) instanceof SystemMessage);
        assertTrue(seated.get(2) instanceof SystemMessage);
        assertTrue(seated.get(3) instanceof SystemMessage);
    }

    /**
     * The method arranges one user message {@code earlier} and an empty host prompt.
     * The method calls {@link MoodDesk#seat} with next line {@code lantern-keep}.
     * The result holds four messages.
     * The texts are the opener, {@code lantern-keep}, the closer, and the same user object.
     *
     * @return The method returns nothing.
     */
    @Test
    void seatOnABlankHostPutsThePairFirst() {
        UserMessage earlier = new UserMessage("earlier");
        List<Message> stored = new ArrayList<Message>();
        stored.add(earlier);
        List<Message> seated = MoodDesk.DESK.seat(stored, "", null, "lantern-keep");
        assertEquals(4, seated.size());
        assertEquals(MoodDesk.OPENER, seated.get(0).getText());
        assertEquals("lantern-keep", seated.get(1).getText());
        assertEquals(MoodDesk.CLOSER, seated.get(2).getText());
        assertSame(earlier, seated.get(3));
    }

    /**
     * The method arranges a host, a stray {@code spare, dry}, {@link MoodDesk#LEGACY}, the user {@code earlier}, another stray, and the user {@code now}.
     * The method calls {@link MoodDesk#seat} with previous line {@code spare, dry} and next line {@code lantern-keep}.
     * The result holds six messages.
     * The host stays first.
     * The next three texts are the opener, {@code lantern-keep}, and the closer.
     * The two user objects stay after that triple.
     * No text equals {@code spare, dry}.
     * No text equals {@link MoodDesk#LEGACY}.
     * {@code lantern-keep} appears once.
     * The closer appears once.
     *
     * @return The method returns nothing.
     */
    @Test
    void seatReplacesADifferentPairAndDropsAStrayCopy() {
        SystemMessage host = new SystemMessage("host line");
        UserMessage earlier = new UserMessage("earlier");
        UserMessage now = new UserMessage("now");
        List<Message> stored = new ArrayList<Message>();
        stored.add(host);
        stored.add(new SystemMessage("spare, dry"));
        stored.add(new SystemMessage(MoodDesk.LEGACY));
        stored.add(earlier);
        stored.add(new SystemMessage("spare, dry"));
        stored.add(now);
        List<Message> seated = MoodDesk.DESK.seat(stored, "host line", "spare, dry", "lantern-keep");
        assertEquals(6, seated.size());
        assertSame(host, seated.get(0));
        assertEquals(MoodDesk.OPENER, seated.get(1).getText());
        assertEquals("lantern-keep", seated.get(2).getText());
        assertEquals(MoodDesk.CLOSER, seated.get(3).getText());
        assertSame(earlier, seated.get(4));
        assertSame(now, seated.get(5));
        assertEquals(0, countText(seated, "spare, dry"));
        assertEquals(0, countText(seated, MoodDesk.LEGACY));
        assertEquals(1, countText(seated, "lantern-keep"));
        assertEquals(1, countText(seated, MoodDesk.CLOSER));
    }

    /**
     * The method arranges a host, the opener, {@code lantern-keep}, the closer, and the user {@code earlier}.
     * The method calls {@link MoodDesk#seat} with previous line {@code lantern-keep} and a null next line.
     * The result holds the same host object and the same user object.
     * The opener is gone.
     * The closer is gone.
     *
     * @return The method returns nothing.
     */
    @Test
    void aClearLeavesTheHostAndTheTurns() {
        SystemMessage host = new SystemMessage("host line");
        UserMessage earlier = new UserMessage("earlier");
        List<Message> stored = new ArrayList<Message>();
        stored.add(host);
        stored.add(new SystemMessage(MoodDesk.OPENER));
        stored.add(new SystemMessage("lantern-keep"));
        stored.add(new SystemMessage(MoodDesk.CLOSER));
        stored.add(earlier);
        List<Message> cleared = MoodDesk.DESK.seat(stored, "host line", "lantern-keep", null);
        assertEquals(2, cleared.size());
        assertSame(host, cleared.get(0));
        assertSame(earlier, cleared.get(1));
        assertEquals(0, countText(cleared, MoodDesk.OPENER));
        assertEquals(0, countText(cleared, MoodDesk.CLOSER));
    }

    /**
     * The method arranges a system text {@code old-mood} and the user {@code earlier}.
     * The method calls {@link MoodDesk#seat} with host prompt {@code host line}, previous line {@code old-mood}, and next line {@code lantern-keep}.
     * The result holds five messages and starts with {@code host line}.
     * The next texts are the opener, {@code lantern-keep}, and the closer.
     * The user object stays last.
     * {@code old-mood} is absent.
     *
     * An empty host prompt returns four messages.
     * Those texts start with the opener, {@code lantern-keep}, and the closer.
     * The user object stays last.
     * {@code old-mood} is absent from that result too.
     *
     * @return The method returns nothing.
     */
    @Test
    void anOrphanPriorMoodIsNotKeptAsTheHost() {
        UserMessage earlier = new UserMessage("earlier");
        List<Message> stored = new ArrayList<Message>();
        stored.add(new SystemMessage("old-mood"));
        stored.add(earlier);
        List<Message> seated = MoodDesk.DESK.seat(stored, "host line", "old-mood", "lantern-keep");
        assertEquals(5, seated.size());
        assertEquals("host line", seated.get(0).getText());
        assertEquals(MoodDesk.OPENER, seated.get(1).getText());
        assertEquals("lantern-keep", seated.get(2).getText());
        assertEquals(MoodDesk.CLOSER, seated.get(3).getText());
        assertSame(earlier, seated.get(4));
        assertEquals(0, countText(seated, "old-mood"));

        List<Message> blankHost = MoodDesk.DESK.seat(stored, "", "old-mood", "lantern-keep");
        assertEquals(4, blankHost.size());
        assertEquals(MoodDesk.OPENER, blankHost.get(0).getText());
        assertEquals("lantern-keep", blankHost.get(1).getText());
        assertEquals(MoodDesk.CLOSER, blankHost.get(2).getText());
        assertSame(earlier, blankHost.get(3));
        assertEquals(0, countText(blankHost, "old-mood"));
    }

    /**
     * The method arranges a host {@code host line} and a user {@code earlier}.
     * The method calls {@link MoodDesk#seat} once with a null previous line, then again with previous and next both {@code lantern-keep}.
     * The two results have the same length.
     * Each text matches the text at the same index.
     * The host object is the same object.
     * The last object is the same object.
     *
     * @return The method returns nothing.
     */
    @Test
    void aRepeatOfTheSameLineKeepsTheSameTexts() {
        List<Message> stored = new ArrayList<Message>();
        stored.add(new SystemMessage("host line"));
        stored.add(new UserMessage("earlier"));
        List<Message> once = MoodDesk.DESK.seat(stored, "host line", null, "lantern-keep");
        List<Message> twice = MoodDesk.DESK.seat(once, "host line", "lantern-keep", "lantern-keep");
        assertEquals(once.size(), twice.size());
        for (int i = 0; i < once.size(); i++) {
            assertEquals(once.get(i).getText(), twice.get(i).getText());
        }
        assertSame(once.get(0), twice.get(0));
        assertSame(once.get(4), twice.get(4));
    }

    /**
     * The method arranges a triple after a host, a triple at the head, a legacy pair, and a list with no pair.
     * The method calls {@link MoodDesk#adopt}.
     * The triple after the host returns {@code lantern-keep}.
     * The triple at the head returns {@code lantern-keep}.
     * The legacy pair returns {@code lantern-keep}.
     * The list with no pair returns null.
     * A null list returns null.
     * An empty list returns null.
     *
     * @return The method returns nothing.
     */
    @Test
    void adoptReadsThePairAfterTheHostAndAtTheHead() {
        List<Message> afterHost = new ArrayList<Message>();
        afterHost.add(new SystemMessage("host line"));
        afterHost.add(new SystemMessage(MoodDesk.OPENER));
        afterHost.add(new SystemMessage(" lantern-keep "));
        afterHost.add(new SystemMessage(MoodDesk.CLOSER));
        afterHost.add(new UserMessage("earlier"));
        assertEquals("lantern-keep", MoodDesk.DESK.adopt(afterHost));

        List<Message> atHead = new ArrayList<Message>();
        atHead.add(new SystemMessage(MoodDesk.OPENER));
        atHead.add(new SystemMessage("lantern-keep"));
        atHead.add(new SystemMessage(MoodDesk.CLOSER));
        atHead.add(new UserMessage("earlier"));
        assertEquals("lantern-keep", MoodDesk.DESK.adopt(atHead));

        List<Message> legacy = new ArrayList<Message>();
        legacy.add(new SystemMessage("host line"));
        legacy.add(new SystemMessage("lantern-keep"));
        legacy.add(new SystemMessage(MoodDesk.LEGACY));
        assertEquals("lantern-keep", MoodDesk.DESK.adopt(legacy));

        List<Message> bare = new ArrayList<Message>();
        bare.add(new SystemMessage("host line"));
        bare.add(new UserMessage("earlier"));
        assertNull(MoodDesk.DESK.adopt(bare));
        assertNull(MoodDesk.DESK.adopt(null));
        assertNull(MoodDesk.DESK.adopt(new ArrayList<Message>()));
    }

    /**
     * The method arranges a triple after a host, a leading triple, a legacy pair, and a list with no pair.
     * The method calls {@link MoodDesk#porchRules}.
     * The triple after the host returns the opener, a blank line, {@code lantern-keep}, a blank line, and the closer.
     * The leading triple returns that same text.
     * The legacy pair returns {@code lantern-keep}, a blank line, and {@link MoodDesk#LEGACY}.
     * The list with no pair returns an empty string.
     * A null list returns an empty string.
     *
     * @return The method returns nothing.
     */
    @Test
    void porchRulesKeepTheHostOut() {
        List<Message> seated = new ArrayList<Message>();
        seated.add(new SystemMessage("host line"));
        seated.add(new SystemMessage(MoodDesk.OPENER));
        seated.add(new SystemMessage(" lantern-keep "));
        seated.add(new SystemMessage(MoodDesk.CLOSER));
        seated.add(new UserMessage("next"));
        assertEquals(MoodDesk.OPENER + "\n\nlantern-keep\n\n" + MoodDesk.CLOSER, MoodDesk.DESK.porchRules(seated));

        List<Message> leading = new ArrayList<Message>();
        leading.add(new SystemMessage(MoodDesk.OPENER));
        leading.add(new SystemMessage("lantern-keep"));
        leading.add(new SystemMessage(MoodDesk.CLOSER));
        assertEquals(MoodDesk.OPENER + "\n\nlantern-keep\n\n" + MoodDesk.CLOSER, MoodDesk.DESK.porchRules(leading));

        List<Message> legacy = new ArrayList<Message>();
        legacy.add(new SystemMessage("host line"));
        legacy.add(new SystemMessage("lantern-keep"));
        legacy.add(new SystemMessage(MoodDesk.LEGACY));
        assertEquals("lantern-keep\n\n" + MoodDesk.LEGACY, MoodDesk.DESK.porchRules(legacy));

        List<Message> off = new ArrayList<Message>();
        off.add(new SystemMessage("host line"));
        off.add(new UserMessage("next"));
        assertEquals("", MoodDesk.DESK.porchRules(off));
        assertEquals("", MoodDesk.DESK.porchRules(null));
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
