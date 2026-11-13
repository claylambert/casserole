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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.aetheris.vault.CanvasHead;
import org.aetheris.vault.CellState;
import org.aetheris.wire.AmwpToon;
import org.casserole.chat.ChatSession;
import org.casserole.chat.cmd.MessageEditCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.roux.crypto.CryptoSink;
import org.roux.id.RouxIdGenerator;
import org.roux.model.MessageInspect;
import org.roux.model.OperationResult;
import org.roux.model.Role;
import org.roux.session.HeadFile;
import org.roux.spill.RegionPack;
import org.roux.spring.RouxChatMemory;
import org.roux.tel.RouxCounters;
import org.roux.token.HeuristicTokenCounter;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;

// @aa-sdd GK-1096 aetheris-stage-2-finessing.001.md FINESSE:f-222
/**
 * The class checks {@link RouxEngine#selectCut} and a locked message head.
 * The selection runs when the allowance is set and a head is locked.
 * A closed selection leaves the recent turns in the prompt.
 * The crossing atom stays out of the selected ordinals.
 *
 * @author clayl (2026-11-04)
 */
class ReichenbachSinkTest {

    private static final long STAMP = 1710000000000L;

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
     * The method arranges two user messages and calls {@link RouxEngine#selectCut}.
     * An allowance of {@code 0} returns null.
     * An allowance of {@code 40} with both open flags false returns null.
     * A negative allowance returns null even when the first open flag is true.
     * An allowance of {@code 40} with a roles array of a different length returns null.
     *
     * @return The method returns nothing.
     */
    @Test
    void aClosedGateReturnsNull() {
        long[] seqs = new long[] { 1L, 2L };
        byte[] roles = new byte[] { Role.USER, Role.USER };
        int[] tokens = new int[] { 1, 1 };
        boolean[] open = new boolean[] { false, false };
        assertNull(RouxEngine.selectCut(0, seqs, roles, tokens, open, null, null));
        assertNull(RouxEngine.selectCut(40, seqs, roles, tokens, open, null, null));
        assertNull(RouxEngine.selectCut(-1, seqs, roles, tokens, new boolean[] { true, false }, null, null));
        assertNull(RouxEngine.selectCut(40, seqs, new byte[] { Role.USER }, tokens, open, null, null));
    }

    /**
     * The method arranges four messages and an allowance of {@code 3}.
     * The method calls {@link RouxEngine#selectCut} with the last message marked live.
     * The cut is not null.
     * {@code headOver} is false.
     * The token sum is {@code 3}.
     * The ordinals are {@code 0}, {@code 1}, and {@code 3}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theCrossingAtomStaysOffTheWire() {
        RouxEngine.SinkCut cut = RouxEngine.selectCut(3,
                new long[] { 1L, 2L, 3L, 0L },
                new byte[] { Role.USER, Role.ASSISTANT, Role.USER, Role.USER },
                new int[] { 1, 1, 2, 1 },
                new boolean[] { true, true, false, false },
                null,
                new boolean[] { false, false, false, true });
        assertNotNull(cut);
        assertFalse(cut.headOver);
        assertEquals(3, cut.tokenSum);
        assertArrayEquals(new int[] { 0, 1, 3 }, cut.ordinals);
    }

    /**
     * The method arranges a system message and three user messages with an allowance of {@code 10}.
     * The method calls {@link RouxEngine#selectCut} with the last message marked live.
     * The cut is not null.
     * {@code headOver} is true.
     * The token sum is {@code 21}.
     * The ordinals are {@code 0}, {@code 1}, and {@code 3}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aHeadOverTheAllowanceKeepsTheLiveLine() {
        RouxEngine.SinkCut cut = RouxEngine.selectCut(10,
                new long[] { 1L, 2L, 3L, 0L },
                new byte[] { Role.SYSTEM, Role.USER, Role.USER, Role.USER },
                new int[] { 8, 4, 1, 9 },
                new boolean[] { false, true, false, false },
                null,
                new boolean[] { false, false, false, true });
        assertNotNull(cut);
        assertTrue(cut.headOver);
        assertEquals(21, cut.tokenSum);
        assertArrayEquals(new int[] { 0, 1, 3 }, cut.ordinals);
    }

    /**
     * The method arranges a system message and two user messages with an allowance of {@code 10}.
     * The first open flag is true, and the last message is live.
     * The method calls {@link RouxEngine#selectCut}.
     * {@code headOver} is false.
     * The ordinals are {@code 0}, {@code 1}, and {@code 2}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aSystemHeadStaysInThePinOnce() {
        RouxEngine.SinkCut cut = RouxEngine.selectCut(10,
                new long[] { 1L, 2L, 0L },
                new byte[] { Role.SYSTEM, Role.USER, Role.USER },
                new int[] { 2, 1, 1 },
                new boolean[] { true, false, false },
                null,
                new boolean[] { false, false, true });
        assertNotNull(cut);
        assertFalse(cut.headOver);
        assertArrayEquals(new int[] { 0, 1, 2 }, cut.ordinals);
    }

    /**
     * The method arranges a user message, a system message, and a live user message.
     * The allowance is {@code 10}.
     * The method calls {@link RouxEngine#selectCut}.
     * The ordinals are {@code 1}, {@code 0}, and {@code 2}.
     *
     * @return The method returns nothing.
     */
    @Test
    void anEmptyLeadingRunPinsTheLastSystem() {
        RouxEngine.SinkCut cut = RouxEngine.selectCut(10,
                new long[] { 1L, 2L, 0L },
                new byte[] { Role.USER, Role.SYSTEM, Role.USER },
                new int[] { 1, 1, 1 },
                new boolean[] { true, false, false },
                null,
                new boolean[] { false, false, true });
        assertNotNull(cut);
        assertArrayEquals(new int[] { 1, 0, 2 }, cut.ordinals);
    }

    /**
     * The method arranges a system message, a stay message of two tokens, a ten-token message, and a live message.
     * The allowance is {@code 5}.
     * The method calls {@link RouxEngine#selectCut}.
     * {@code headOver} is false.
     * The token sum is {@code 4}.
     * The ordinals are {@code 0}, {@code 1}, and {@code 3}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aStayPieceRidesWithThePin() {
        RouxEngine.SinkCut cut = RouxEngine.selectCut(5,
                new long[] { 1L, 2L, 3L, 0L },
                new byte[] { Role.SYSTEM, Role.USER, Role.USER, Role.USER },
                new int[] { 1, 2, 10, 1 },
                new boolean[] { true, false, false, false },
                new boolean[] { false, true, false, false },
                new boolean[] { false, false, false, true });
        assertNotNull(cut);
        assertFalse(cut.headOver);
        assertEquals(4, cut.tokenSum);
        assertArrayEquals(new int[] { 0, 1, 3 }, cut.ordinals);
    }

    /**
     * The method arranges a user message and a user-plus-assistant pair.
     * The method calls {@link RouxEngine#selectCut} twice.
     * An allowance of {@code 5} keeps only ordinal {@code 0}.
     * An allowance of {@code 7} keeps ordinals {@code 0}, {@code 1}, and {@code 2}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aTurnAtomStaysWhole() {
        RouxEngine.SinkCut tight = RouxEngine.selectCut(5,
                new long[] { 1L, 2L, 3L },
                new byte[] { Role.USER, Role.USER, Role.ASSISTANT },
                new int[] { 1, 3, 3 },
                new boolean[] { true, false, false },
                null,
                null);
        assertNotNull(tight);
        assertArrayEquals(new int[] { 0 }, tight.ordinals);
        RouxEngine.SinkCut room = RouxEngine.selectCut(7,
                new long[] { 1L, 2L, 3L },
                new byte[] { Role.USER, Role.USER, Role.ASSISTANT },
                new int[] { 1, 3, 3 },
                new boolean[] { true, false, false },
                null,
                null);
        assertNotNull(room);
        assertArrayEquals(new int[] { 0, 1, 2 }, room.ordinals);
    }

    /**
     * The method arranges region {@code dano.aetheris} with a system line and three later lines.
     * The method calls {@link RouxEngine#applyHeadCount}, {@link RouxEngine#lockMessage}, {@link RouxEngine#unlockMessage}, {@link RouxEngine#headMark}, {@link RouxEngine#editMessage}, and {@link RouxEngine#peek}.
     * A head count of {@code 2} refuses a lock on sequence {@code 1} because a system turn stays in the pin.
     * A lock on sequence {@code 3} is refused because a lock keeps a packed prefix.
     * Sequence {@code 1} has an empty mark, sequence {@code 2} may lock, and sequence {@code 4} has an empty mark.
     * Locking sequence {@code 2} returns null, marks it locked, and leaves sequence {@code 3} able to lock.
     * {@link RouxEngine#headDesignated} is true, and {@link RouxEngine#selectTape} returns a cut.
     * Sequence {@code 4} is outside the eligible run.
     * Locking sequence {@code 3} returns null.
     * Unlocking sequence {@code 2} is refused because an unlock keeps a packed prefix.
     * Unlocking sequence {@code 3} returns null and leaves sequence {@code 2} locked.
     *
     * A head count of {@code 3} refuses a lock on sequence {@code 4} until sequence {@code 3} is locked.
     * Both of those locks then return null, the head is designated, and sequence {@code 4} is locked.
     *
     * Editing sequence {@code 2} to {@code ONE} succeeds and the read text is {@code ONE}.
     * The bytes at the old page offset stay equal to the peek taken before the edit.
     * Sequence {@code 2} stays locked.
     * An empty edit fails with {@code An empty body is refused.}
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because the region calls can throw a checked failure.
     */
    @Test
    void thePackedPrefixLocksAndEdits() throws Exception {
        String region = "dano.aetheris";
        assertNotNull(engine.openRam(region));
        assertTrue(engine.appendMessage(region, Role.SYSTEM, STAMP, 1, "host").isSuccess());
        assertTrue(engine.appendMessage(region, Role.USER, STAMP, 1, "one").isSuccess());
        assertTrue(engine.appendMessage(region, Role.ASSISTANT, STAMP, 1, "two").isSuccess());
        assertTrue(engine.appendMessage(region, Role.USER, STAMP, 2, "three").isSuccess());
        engine.applyHeadCount(region, 2);
        assertEquals("A system turn stays in the pin.", engine.lockMessage(region, 1L));
        assertEquals("A lock keeps a packed prefix.", engine.lockMessage(region, 3L));
        assertEquals("", engine.headMark(region, 1L));
        assertEquals(" may lock", engine.headMark(region, 2L));
        assertEquals("", engine.headMark(region, 4L));
        assertNull(engine.lockMessage(region, 2L));
        assertEquals(" locked", engine.headMark(region, 2L));
        assertEquals(" may lock", engine.headMark(region, 3L));
        assertTrue(engine.headDesignated(region));
        assertNotNull(engine.selectTape(region, 10, 1));
        assertEquals("Seq 4 is outside the eligible run.", engine.lockMessage(region, 4L));
        assertNull(engine.lockMessage(region, 3L));
        assertEquals("An unlock keeps a packed prefix.", engine.unlockMessage(region, 2L));
        assertNull(engine.unlockMessage(region, 3L));
        assertEquals(" locked", engine.headMark(region, 2L));
        assertEquals(" may lock", engine.headMark(region, 3L));

        engine.applyHeadCount(region, 3);
        assertEquals("A lock keeps a packed prefix.", engine.lockMessage(region, 4L));
        assertNull(engine.lockMessage(region, 3L));
        assertNull(engine.lockMessage(region, 4L));
        assertTrue(engine.headDesignated(region));
        assertEquals(" locked", engine.headMark(region, 4L));

        MessageInspect before = engine.inspect(region, 2L);
        byte[] prior = engine.peek(region, before.getPageId(), before.getOffset(), before.getLength());
        assertNotNull(prior);
        assertTrue(engine.editMessage(region, 2L, "ONE").isSuccess());
        assertEquals("ONE", engine.readMessage(region, 2L).getText());
        assertArrayEquals(prior, engine.peek(region, before.getPageId(), before.getOffset(), before.getLength()));
        assertEquals(" locked", engine.headMark(region, 2L));
        OperationResult empty = engine.editMessage(region, 2L, "");
        assertFalse(empty.isSuccess());
        assertEquals("An empty body is refused.", empty.getMessage());
    }

    /**
     * The method arranges region {@code prefix.aetheris} with three user lines and a head count of {@code 3}.
     * {@link RouxEngine#selectTape} with a negative head returns null before any lock.
     * Locking sequence {@code 1} returns null.
     * The head is designated.
     * Sequence {@code 1} is locked, and sequence {@code 2} may lock.
     * A later tape with allowance {@code 1} keeps only ordinal {@code 0}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aShorterPrefixIsStillAHead() {
        String region = "prefix.aetheris";
        assertNotNull(engine.openRam(region));
        assertTrue(engine.appendMessage(region, Role.USER, STAMP, 1, "one").isSuccess());
        assertTrue(engine.appendMessage(region, Role.USER, STAMP, 1, "two").isSuccess());
        assertTrue(engine.appendMessage(region, Role.USER, STAMP, 1, "three").isSuccess());
        engine.applyHeadCount(region, 3);
        assertNull(engine.selectTape(region, 10, -1));
        assertNull(engine.lockMessage(region, 1L));
        assertTrue(engine.headDesignated(region));
        assertEquals(" locked", engine.headMark(region, 1L));
        assertEquals(" may lock", engine.headMark(region, 2L));
        RouxEngine.SinkCut cut = engine.selectTape(region, 1, -1);
        assertNotNull(cut);
        assertArrayEquals(new int[] { 0 }, cut.ordinals);
    }

    /**
     * The method arranges three chat sessions and calls {@link ChatSession#buildPrompt}, {@link ChatSession#lockMessage}, and {@link ChatSession#editMessage}.
     * Before a lock, a narrow prompt of budget {@code 1} holds {@code three}, {@code four}, and {@code ask}.
     * A managed prompt with the same budget holds those same texts.
     * {@link ChatSession#headOverAllowance} is false.
     *
     * After sequences {@code 1} and {@code 2} lock, a wide prompt still holds {@code one}, {@code two}, {@code three}, and {@code five}.
     * A selected prompt with allowance {@code 3} holds {@code one}, {@code two}, and {@code five}.
     * The allowance flag stays false.
     * Both locked sequences are marked locked, and sequence {@code 3} has an empty mark.
     *
     * A host of forty {@code x} characters plus two user lines locks sequence {@code 2}.
     * A wide prompt holds the host, {@code one}, {@code three}, and {@code z}.
     * A selected prompt with allowance {@code 8} holds the host, {@code one}, and {@code z}.
     * {@link ChatSession#headOverAllowance} is then true.
     * An empty edit throws {@link Exception} with {@code An empty body is refused.}
     *
     * @return The method returns nothing.
     */
    @Test
    void theSessionWalkStaysUntilAHeadIsLocked() {
        ChatSession session = new ChatSession("", memory);
        session.appendUser("one");
        session.appendAssistant("two");
        session.appendUser("three");
        session.appendAssistant("four");
        List<String> narrow = texts(session.buildPrompt(1, "ask"));
        assertEquals(list("three", "four", "ask"), narrow);
        assertFalse(session.headOverAllowance());
        List<String> managed = texts(session.buildPrompt(1, 0, 40, 2, "ask"));
        assertEquals(narrow, managed);
        assertFalse(session.headOverAllowance());

        ChatSession sunk = new ChatSession("", memory);
        sunk.appendUser("one");
        sunk.appendAssistant("two");
        sunk.appendUser("three");
        assertNull(sunk.lockMessage(1L, 2));
        assertNull(sunk.lockMessage(2L, 2));
        assertEquals(" locked", sunk.headMark(1L, 2));
        assertEquals(" locked", sunk.headMark(2L, 2));
        assertEquals("", sunk.headMark(3L, 2));
        List<String> today = texts(sunk.buildPrompt(40, "five"));
        assertEquals(list("one", "two", "three", "five"), today);
        assertFalse(sunk.headOverAllowance());
        List<String> selected = texts(sunk.buildPrompt(40, 0, 3, 2, "five"));
        assertEquals(list("one", "two", "five"), selected);
        assertFalse(sunk.headOverAllowance());

        String host = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        ChatSession over = new ChatSession(host, memory);
        over.appendUser("one");
        over.appendUser("three");
        assertNull(over.lockMessage(2L, 1));
        List<String> wide = texts(over.buildPrompt(40, "z"));
        assertEquals(list(host, "one", "three", "z"), wide);
        assertFalse(over.headOverAllowance());
        List<String> kept = texts(over.buildPrompt(40, 0, 8, 1, "z"));
        assertEquals(list(host, "one", "z"), kept);
        assertTrue(over.headOverAllowance());
        Exception refused = assertThrows(Exception.class, () -> over.editMessage(2L, ""));
        assertEquals("An empty body is refused.", refused.getMessage());
    }

    /**
     * The method arranges included and excluded cell states and calls {@link CanvasHead#showsLock}, {@link CanvasHead#press}, and {@link CanvasHead#repair}.
     * Lock marks show at columns {@code 0} and {@code 2} of a head count {@code 2}, and not at {@code 1} or {@code 3}.
     * Pressing column {@code 0} sets only the first flag.
     * Pressing column {@code 2} sets the first and third flags.
     * Pressing column {@code 3} or column {@code 0} again leaves that pair.
     * Pressing column {@code 2} again returns to the first flag only.
     * A hole at the third cell repairs to no flags.
     * A flag on an excluded cell repairs to the first flag only.
     * A short prefix on three included cells stays that short prefix.
     * The last included cell of that run shows a lock.
     *
     * @return The method returns nothing.
     */
    @Test
    void theCanvasLockIsAPackedPrefix() {
        CellState[] states = new CellState[] {
                CellState.INCLUDED, CellState.EXCLUDED, CellState.INCLUDED, CellState.INCLUDED };
        assertTrue(CanvasHead.showsLock(states, 2, 0));
        assertFalse(CanvasHead.showsLock(states, 2, 1));
        assertTrue(CanvasHead.showsLock(states, 2, 2));
        assertFalse(CanvasHead.showsLock(states, 2, 3));
        boolean[] none = new boolean[] { false, false, false, false };
        boolean[] first = CanvasHead.press(states, none, 2, 0);
        assertArrayEquals(new boolean[] { true, false, false, false }, first);
        boolean[] both = CanvasHead.press(states, first, 2, 2);
        assertArrayEquals(new boolean[] { true, false, true, false }, both);
        assertArrayEquals(both, CanvasHead.press(states, both, 2, 3));
        assertArrayEquals(both, CanvasHead.press(states, both, 2, 0));
        assertArrayEquals(first, CanvasHead.press(states, both, 2, 2));
        boolean[] hole = new boolean[] { false, false, true, false };
        assertArrayEquals(none, CanvasHead.repair(states, hole, 2));
        boolean[] onExcluded = new boolean[] { true, true, false, false };
        assertArrayEquals(first, CanvasHead.repair(states, onExcluded, 2));
        CellState[] run = new CellState[] { CellState.INCLUDED, CellState.INCLUDED, CellState.INCLUDED };
        boolean[] shortPrefix = new boolean[] { true, false, false };
        assertArrayEquals(shortPrefix, CanvasHead.repair(run, shortPrefix, 3));
        assertTrue(CanvasHead.showsLock(run, 3, 2));
    }

    /**
     * The method arranges a temporary directory and calls {@link HeadFile#read} and {@link HeadFile#write}.
     * A missing file reads count {@code 0} and an empty sequence list.
     * A write of count {@code 4} and sequence {@code 2} reads back those values.
     * The file starts with {@code version: 1} and contains {@code count: 4} and {@code - 2}.
     * A file whose sequence list is empty reads count {@code 0} and an empty list.
     * Version {@code 2} also reads count {@code 0} and an empty list.
     * That version {@code 2} file is left byte for byte as it was written.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because the file calls can throw a checked failure.
     */
    @Test
    void headYamlKeepsAShorterPrefix() throws Exception {
        Path dir = Files.createTempDirectory("roux-f222-head");
        try {
            assertEquals(0, HeadFile.read(dir).count);
            assertEquals(0, HeadFile.read(dir).seqs.length);
            HeadFile.write(dir, 4, new long[] { 2L });
            HeadFile shorter = HeadFile.read(dir);
            assertEquals(4, shorter.count);
            assertArrayEquals(new long[] { 2L }, shorter.seqs);
            String text = new String(Files.readAllBytes(dir.resolve(HeadFile.FILE_NAME)), StandardCharsets.UTF_8);
            assertTrue(text.startsWith("version: 1\n"));
            assertTrue(text.contains("count: 4\n"));
            assertTrue(text.contains("- 2\n"));

            Path emptyList = dir.resolve(HeadFile.FILE_NAME);
            String bare = "version: 1\ncount: 4\nseqs:\n";
            Files.write(emptyList, bare.getBytes(StandardCharsets.UTF_8));
            HeadFile none = HeadFile.read(dir);
            assertEquals(0, none.count);
            assertEquals(0, none.seqs.length);

            String foreign = "version: 2\ncount: 4\nseqs:\n- 2\n";
            byte[] foreignBytes = foreign.getBytes(StandardCharsets.UTF_8);
            Files.write(emptyList, foreignBytes);
            HeadFile unread = HeadFile.read(dir);
            assertEquals(0, unread.count);
            assertEquals(0, unread.seqs.length);
            assertArrayEquals(foreignBytes, Files.readAllBytes(emptyList));
        } finally {
            deleteTree(dir);
        }
    }

    /**
     * The method arranges facts text with air-gap {@code false} and context {@code 4096}, and no managed-token line.
     * The method calls {@link AmwpToon#decodeFacts} and {@link AmwpToon#encodeFacts}.
     * The decoded air-gap is {@code false}.
     * The decoded context is {@code 4096} and the context is present.
     * The managed-token count is {@code 0}.
     * The head count is {@code 0}.
     * Encoding air-gap {@code false}, context {@code 4096}, managed tokens {@code 30}, and head count {@code 2} writes those four lines.
     * Decoding that text returns managed tokens {@code 30} and head count {@code 2}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aMissingFactsLineIsZero() {
        byte[] body = "air-gap: false\nnum-ctx: 4096\n".getBytes(StandardCharsets.UTF_8);
        AmwpToon.SessionFacts old = AmwpToon.decodeFacts(body);
        assertEquals("false", old.airGap);
        assertEquals(4096, old.numCtx);
        assertTrue(old.numCtxPresent);
        assertEquals(0, old.managedTokens);
        assertEquals(0, old.headCount);
        String written = new String(AmwpToon.encodeFacts("false", 4096, 30, 2), StandardCharsets.UTF_8);
        assertTrue(written.contains("air-gap: false\n"));
        assertTrue(written.contains("num-ctx: 4096\n"));
        assertTrue(written.contains("managed-tokens: 30\n"));
        assertTrue(written.contains("head-count: 2\n"));
        AmwpToon.SessionFacts back = AmwpToon.decodeFacts(written.getBytes(StandardCharsets.UTF_8));
        assertEquals(30, back.managedTokens);
        assertEquals(2, back.headCount);
    }

    /**
     * The method arranges body tokens and calls {@link MessageEditCommand#joinBody}.
     * {@code keep}, {@code the line}, and {@code <.>} join as {@code keep} plus a line feed plus {@code the line}.
     * A lone dot before {@code <.>} returns {@code .}.
     * A body that is only {@code <.>} throws {@link Exception} with {@code An empty body is refused.}
     * A null body throws {@link Exception} with that same message.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because {@link MessageEditCommand#joinBody} declares a checked failure.
     */
    @Test
    void joinBodyKeepsADotAndRefusesEmpty() throws Exception {
        assertEquals("keep\nthe line", MessageEditCommand.joinBody(new String[] { "keep", "the line", "<.>" }));
        assertEquals(".", MessageEditCommand.joinBody(new String[] { ".", "<.>" }));
        Exception bare = assertThrows(Exception.class, () -> MessageEditCommand.joinBody(new String[] { "<.>" }));
        assertEquals("An empty body is refused.", bare.getMessage());
        Exception blank = assertThrows(Exception.class, () -> MessageEditCommand.joinBody(null));
        assertEquals("An empty body is refused.", blank.getMessage());
    }

    /**
     * The method copies each instruction text from {@code prompt}.
     *
     * @param prompt The prompt is the chat prompt whose instructions are read.
     * @return The method returns those texts in order.
     */
    private static List<String> texts(Prompt prompt) {
        List<String> lines = new ArrayList<String>();
        for (Message message : prompt.getInstructions()) {
            lines.add(message.getText());
        }
        return lines;
    }

    /**
     * The method copies {@code lines} into a new list.
     *
     * @param lines The lines are the texts to copy.
     * @return The method returns that list.
     */
    private static List<String> list(String... lines) {
        List<String> out = new ArrayList<String>();
        for (int i = 0; i < lines.length; i++) {
            out.add(lines[i]);
        }
        return out;
    }

    /**
     * The method deletes {@code root} and every path under it.
     * A null root is left alone.
     * A path that does not exist is left alone.
     *
     * @param root The root is the directory to delete.
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because the file deletes can throw a checked failure.
     */
    private static void deleteTree(Path root) throws Exception {
        if (root == null || !Files.exists(root)) {
            return;
        }
        ArrayList<Path> paths = new ArrayList<Path>();
        collect(root, paths);
        for (int i = paths.size() - 1; i >= 0; i--) {
            Files.deleteIfExists(paths.get(i));
        }
    }

    /**
     * The method adds {@code dir} to {@code paths} and then adds each child.
     * A path that is not a directory adds no children.
     *
     * @param dir The directory is the path to record.
     * @param paths The list receives {@code dir} and its children.
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because reading the directory can throw a checked failure.
     */
    private static void collect(Path dir, ArrayList<Path> paths) throws Exception {
        paths.add(dir);
        if (!Files.isDirectory(dir)) {
            return;
        }
        java.nio.file.DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
        try {
            java.util.Iterator<Path> it = stream.iterator();
            while (it.hasNext()) {
                collect(it.next(), paths);
            }
        } finally {
            stream.close();
        }
    }
}
/*****************************************************************************************
 * End of Class: ReichenbachSinkTest.java 110426
 ****************************************************************************************/
