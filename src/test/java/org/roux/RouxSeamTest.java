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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.casserole.chat.ChatSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.roux.crypto.CryptoSink;
import org.roux.id.RouxIdGenerator;
import org.roux.model.Role;
import org.roux.spill.RegionPack;
import org.roux.spring.RouxChatMemory;
import org.roux.tel.RouxCounters;
import org.roux.token.HeuristicTokenCounter;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;

/**
 * The class checks the seam between a clip origin and a sink cut.
 * A positive allowance returns the sink cut.
 * An empty head uses the clip floor.
 * A tool row and the following assistant row drop together.
 *
 * @author clayl (2026-11-23)
 */
class RouxSeamTest {

    private static final long TIME = 1710000000000L;

    private RouxEngine engine;
    private RouxChatMemory memory;

    /**
     * The method arranges durability {@code hot-only} and spill disabled.
     * The method builds a {@link RouxEngine} and a {@link RouxChatMemory}.
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
     * The method shuts the engine down when the field is present.
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
     * The method stores four turns, rewinds to sequence 3, and locks sequence 1.
     * The managed prompt contains the reeled rows and the live line.
     * The floored window omits the reeled rows.
     * The origin stays 3.
     *
     * @return The method returns nothing.
     */
    @Test
    void aSinkCutIgnoresTheClipFloor() {
        ChatSession session = new ChatSession("", memory);
        plantFour(session);
        String id = session.getConversationId();
        assertNull(session.rewindTo(3L));
        assertTrue(session.headLine().startsWith("head: 3 rewind "));
        assertEquals(3L, memory.clipOrigin(id));
        assertNull(session.lockMessage(1L, 1));
        List<String> lines = texts(session.buildPrompt(40, 0, 40, 1, "ask"));
        assertTrue(contains(lines, "old"));
        assertTrue(contains(lines, "old-a"));
        assertTrue(contains(lines, "new"));
        assertTrue(contains(lines, "new-a"));
        assertTrue(contains(lines, "ask"));
        List<String> floored = texts(memory.getWindow(id, 0, true, 0, 3L));
        assertFalse(contains(floored, "old"));
        assertFalse(contains(floored, "old-a"));
        assertTrue(contains(floored, "new"));
        assertTrue(contains(floored, "new-a"));
        assertEquals(3L, memory.clipOrigin(id));
        assertFalse(session.headOverAllowance());
    }

    /**
     * The method stores four turns and rewinds to sequence 3.
     * The method does not lock a head.
     * {@link RouxEngine#selectTape} returns null.
     * The prompt matches the floored window plus the live line.
     *
     * @return The method returns nothing.
     */
    @Test
    void anEmptyHeadUsesTheClipFloor() {
        ChatSession session = new ChatSession("", memory);
        plantFour(session);
        String id = session.getConversationId();
        assertNull(session.rewindTo(3L));
        List<String> lines = texts(session.buildPrompt(40, 0, 40, 2, "ask"));
        assertNull(engine.selectTape(id, 40, 1));
        List<String> floored = texts(memory.getWindow(id, 40, true, 0, 3L));
        floored.add("ask");
        assertEquals(floored, lines);
        assertTrue(contains(lines, "new"));
        assertTrue(contains(lines, "new-a"));
        assertTrue(contains(lines, "ask"));
        assertFalse(contains(lines, "old"));
        assertFalse(contains(lines, "old-a"));
        assertEquals(3L, memory.clipOrigin(id));
    }

    /**
     * The method stores four turns, rewinds to sequence 3, and locks sequence 1.
     * The cut maps to sequences 1, 2, 3, and 4.
     * Sequence 1 sits behind the origin.
     * The live piece is message id 0.
     *
     * @return The method returns nothing.
     */
    @Test
    void selectTapeKeepsAReeledId() {
        ChatSession session = new ChatSession("", memory);
        plantFour(session);
        String id = session.getConversationId();
        assertNull(session.rewindTo(3L));
        assertNull(session.lockMessage(1L, 1));
        RouxEngine.SinkCut cut = engine.selectTape(id, 40, 1);
        assertNotNull(cut);
        assertFalse(cut.headOver);
        boolean sawOne = false;
        boolean sawLive = false;
        for (int i = 0; i < cut.ordinals.length; i++) {
            long seq = engine.messageSeqAt(id, cut.ordinals[i]);
            if (seq == 1L) {
                sawOne = true;
            }
            if (seq == 2L || seq == 3L || seq == 4L) {
                continue;
            }
            if (seq == 0L) {
                sawLive = true;
            }
        }
        assertTrue(sawOne);
        assertTrue(sawLive);
        assertTrue(maps(id, cut, 2L));
        assertTrue(maps(id, cut, 3L));
        assertTrue(maps(id, cut, 4L));
    }

    /**
     * The method stores four exact token counts and locks sequence 1.
     * {@code rollHead} stores origin 5.
     * The next managed prompt contains {@code old} and {@code ask}.
     * The origin stays 5.
     *
     * @return The method returns nothing.
     */
    @Test
    void aRollThenASinkCutKeepsTheOrigin() {
        ChatSession session = new ChatSession("", memory);
        String id = session.getConversationId();
        assertTrue(engine.appendMessage(id, Role.USER, TIME, 100, "old").isSuccess());
        assertTrue(engine.appendMessage(id, Role.ASSISTANT, TIME, 100, "old-a").isSuccess());
        assertTrue(engine.appendMessage(id, Role.USER, TIME, 10, "new").isSuccess());
        assertTrue(engine.appendMessage(id, Role.ASSISTANT, TIME, 10, "new-a").isSuccess());
        assertNull(session.lockMessage(1L, 1));
        session.rollHead(0, 0, 20, 5, null);
        assertEquals(5L, memory.clipOrigin(id));
        assertTrue(session.headLine().startsWith("head: 5 auto "));
        List<String> lines = texts(session.buildPrompt(40, 0, 400, 1, "ask"));
        assertTrue(contains(lines, "old"));
        assertTrue(contains(lines, "ask"));
        List<String> floored = texts(memory.getWindow(id, 0, true, 0, 5L));
        assertFalse(contains(floored, "old"));
        assertEquals(5L, memory.clipOrigin(id));
    }

    /**
     * The method plants a tool row, the following assistant row, and a live line.
     * Allowance 6 keeps the live ordinal.
     * Allowance 9 keeps ordinals 0, 1, and 2.
     * {@link RouxEngine#selectSlot} returns that same cut.
     * {@code headOver} is false.
     *
     * @return The method returns nothing.
     */
    @Test
    void aToolAtomDropsTogether() {
        ReichenbachSink.Piece[] pieces = toolPair(Role.TOOL);
        ReichenbachSink.Transcript tight = ReichenbachSink.selectSlot(6, pieces);
        assertArrayEquals(new int[] { 2 }, ordinals(tight));
        ReichenbachSink.Transcript room = ReichenbachSink.selectSlot(9, pieces);
        assertArrayEquals(new int[] { 0, 1, 2 }, ordinals(room));
        RouxEngine.SinkCut cut = RouxEngine.selectSlot(9, seqs(pieces), roles(pieces), tokens(pieces),
                heads(pieces), null, lives(pieces));
        assertNotNull(cut);
        assertArrayEquals(ordinals(room), cut.ordinals);
        assertFalse(cut.headOver);
    }

    /**
     * The method repeats the tool pair with {@link Role#TOOL_CALL}.
     * Allowance 6 keeps the live ordinal.
     * Allowance 9 keeps the tool-call ordinal, the assistant ordinal, and the live ordinal.
     *
     * @return The method returns nothing.
     */
    @Test
    void aToolCallAtomDropsTogether() {
        ReichenbachSink.Piece[] pieces = toolPair(Role.TOOL_CALL);
        assertArrayEquals(new int[] { 2 }, ordinals(ReichenbachSink.selectSlot(6, pieces)));
        assertArrayEquals(new int[] { 0, 1, 2 }, ordinals(ReichenbachSink.selectSlot(9, pieces)));
    }

    /**
     * The method plants a tool row, a user row, an assistant row, and a live line.
     * Allowance 6 keeps the user row, the assistant row, and the live line.
     * The tool ordinal is absent.
     *
     * @return The method returns nothing.
     */
    @Test
    void aGapBetweenToolAndAssistantDoesNotGlue() {
        ReichenbachSink.Piece[] pieces = new ReichenbachSink.Piece[] {
                piece(0, 2L, Role.TOOL, 4, false),
                piece(1, 3L, Role.USER, 1, false),
                piece(2, 4L, Role.ASSISTANT, 4, false),
                piece(3, 0L, Role.USER, 1, true)
        };
        int[] kept = ordinals(ReichenbachSink.selectSlot(6, pieces));
        assertArrayEquals(new int[] { 1, 2, 3 }, kept);
    }

    /**
     * The method stores a tool row and an assistant reply under a locked head.
     * The sink messages contain the head, the tool text, and the reply.
     * A second plant stores {@code tool_call}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aStoredToolRoleStaysInTheCut() {
        ChatSession session = new ChatSession("", memory);
        String id = session.getConversationId();
        assertTrue(engine.appendMessage(id, Role.USER, TIME, 1, "head").isSuccess());
        assertTrue(engine.appendMessage(id, Role.TOOL, TIME, 4, "wrench").isSuccess());
        assertTrue(engine.appendMessage(id, Role.ASSISTANT, TIME, 4, "used").isSuccess());
        assertNull(session.lockMessage(1L, 1));
        RouxEngine.SinkCut cut = engine.selectTape(id, 20, 1);
        assertNotNull(cut);
        List<String> lines = texts(memory.sinkMessages(id, cut));
        assertTrue(contains(lines, "head"));
        assertTrue(contains(lines, "wrench"));
        assertTrue(contains(lines, "used"));
        assertEquals("tool", Role.nameOf(engine.inspect(id, 2L).getRole()));
        ChatSession second = new ChatSession("", memory);
        String againId = second.getConversationId();
        assertTrue(engine.appendMessage(againId, Role.USER, TIME, 1, "head").isSuccess());
        assertTrue(engine.appendMessage(againId, Role.TOOL_CALL, TIME, 4, "call").isSuccess());
        assertTrue(engine.appendMessage(againId, Role.ASSISTANT, TIME, 4, "used").isSuccess());
        assertNull(second.lockMessage(1L, 1));
        RouxEngine.SinkCut again = engine.selectTape(againId, 20, 1);
        assertNotNull(again);
        assertTrue(contains(texts(memory.sinkMessages(againId, again)), "call"));
        assertEquals("tool_call", Role.nameOf(engine.inspect(againId, 2L).getRole()));
    }

    /**
     * The method stores the four heuristic turns.
     *
     * @param session The session is the ram conversation.
     * @return The method returns nothing.
     */
    private static void plantFour(ChatSession session) {
        session.appendUser("old");
        session.appendAssistant("old-a");
        session.appendUser("new");
        session.appendAssistant("new-a");
    }

    /**
     * The method builds the tool atom and the live line.
     *
     * @param role The role is {@link Role#TOOL} or {@link Role#TOOL_CALL}.
     * @return The pieces are the three turns.
     */
    private static ReichenbachSink.Piece[] toolPair(byte role) {
        return new ReichenbachSink.Piece[] {
                piece(0, 2L, role, 4, false),
                piece(1, 3L, Role.ASSISTANT, 4, false),
                piece(2, 0L, Role.USER, 1, true)
        };
    }

    /**
     * The method stores one piece.
     *
     * @param ordinal The ordinal is the caller index.
     * @param seq The seq is the message id.
     * @param role The role is the role byte.
     * @param tokens The tokens value is the token count.
     * @param live The live flag marks the live line.
     * @return The piece is the stored turn.
     */
    private static ReichenbachSink.Piece piece(int ordinal, long seq, byte role, int tokens, boolean live) {
        return new ReichenbachSink.Piece(ordinal, seq, role, tokens, false, false, live);
    }

    /**
     * The method copies the piece ordinals.
     *
     * @param transcript The transcript is the selection.
     * @return The ordinals follow the selection order.
     */
    private static int[] ordinals(ReichenbachSink.Transcript transcript) {
        int[] out = new int[transcript.pieces.length];
        for (int i = 0; i < transcript.pieces.length; i++) {
            out[i] = transcript.pieces[i].ordinal;
        }
        return out;
    }

    /**
     * The method copies the sequence ids.
     *
     * @param pieces The pieces are the planted turns.
     * @return The ids follow the piece order.
     */
    private static long[] seqs(ReichenbachSink.Piece[] pieces) {
        long[] out = new long[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            out[i] = pieces[i].seq;
        }
        return out;
    }

    /**
     * The method copies the role bytes.
     *
     * @param pieces The pieces are the planted turns.
     * @return The roles follow the piece order.
     */
    private static byte[] roles(ReichenbachSink.Piece[] pieces) {
        byte[] out = new byte[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            out[i] = pieces[i].role;
        }
        return out;
    }

    /**
     * The method copies the token counts.
     *
     * @param pieces The pieces are the planted turns.
     * @return The counts follow the piece order.
     */
    private static int[] tokens(ReichenbachSink.Piece[] pieces) {
        int[] out = new int[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            out[i] = pieces[i].tokens;
        }
        return out;
    }

    /**
     * The method copies the head flags.
     *
     * @param pieces The pieces are the planted turns.
     * @return The flags follow the piece order.
     */
    private static boolean[] heads(ReichenbachSink.Piece[] pieces) {
        boolean[] out = new boolean[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            out[i] = pieces[i].head;
        }
        return out;
    }

    /**
     * The method copies the live flags.
     *
     * @param pieces The pieces are the planted turns.
     * @return The flags follow the piece order.
     */
    private static boolean[] lives(ReichenbachSink.Piece[] pieces) {
        boolean[] out = new boolean[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            out[i] = pieces[i].live;
        }
        return out;
    }

    /**
     * The method reports whether a cut maps to one sequence.
     *
     * @param id The id is the region.
     * @param cut The cut is the selection.
     * @param seq The seq is the stored message id.
     * @return The flag is true when one ordinal maps to that id.
     */
    private boolean maps(String id, RouxEngine.SinkCut cut, long seq) {
        for (int i = 0; i < cut.ordinals.length; i++) {
            if (engine.messageSeqAt(id, cut.ordinals[i]) == seq) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method copies each instruction text.
     *
     * @param prompt The prompt is the chat prompt.
     * @return The texts stay in order.
     */
    private static List<String> texts(Prompt prompt) {
        return texts(prompt.getInstructions());
    }

    /**
     * The method copies each message text.
     *
     * @param messages The messages are the stored turns.
     * @return The texts stay in order.
     */
    private static List<String> texts(List<Message> messages) {
        List<String> lines = new ArrayList<String>();
        for (int i = 0; i < messages.size(); i++) {
            lines.add(messages.get(i).getText());
        }
        return lines;
    }

    /**
     * The method reports whether a text is present.
     *
     * @param lines The lines are the collected texts.
     * @param text The text is the candidate.
     * @return The flag is true when one line equals the text.
     */
    private static boolean contains(List<String> lines, String text) {
        for (int i = 0; i < lines.size(); i++) {
            if (text.equals(lines.get(i))) {
                return true;
            }
        }
        return false;
    }
}
