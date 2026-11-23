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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.roux.crypto.CryptoSink;
import org.roux.frame.FrameHeader;
import org.roux.id.RouxIdGenerator;
import org.roux.model.FrameType;
import org.roux.model.MessageInspect;
import org.roux.model.OperationResult;
import org.roux.model.Role;
import org.roux.page.PageHeader;
import org.roux.session.OpenResult;
import org.roux.spill.RegionPack;
import org.roux.tel.RouxCounters;
import org.roux.toon.ToonCodec;
import org.roux.token.HeuristicTokenCounter;

/**
 * The class checks an edit that lands as a later frame.
 * The index row points at that later frame.
 * A continuation of the same sequence leaves the row.
 *
 * @author clayl (2026-11-22)
 */
class RouxRetargetTest {

    private static final String USER = "clay";
    private static final long TIME = 1710000000000L;
    private static final int PAGE = 512;

    private final ArrayList<Path> dirs = new ArrayList<Path>();
    private final ArrayList<RouxEngine> engines = new ArrayList<RouxEngine>();

    /**
     * The method closes every engine the test opened and deletes the temporary directories.
     *
     * @throws IOException The directory delete can fail.
     */
    @AfterEach
    void cleanup() throws IOException {
        for (int i = 0; i < engines.size(); i++) {
            engines.get(i).shutdown(true);
        }
        engines.clear();
        for (int i = 0; i < dirs.size(); i++) {
            deleteTree(dirs.get(i));
        }
        dirs.clear();
    }

    /**
     * The method checks that an edit returns the new text.
     * The identity, the role, and the timestamp stay.
     * The index size stays.
     */
    @Test
    void p2_01_editKeepsTheIdentity() {
        RouxEngine engine = engine("p201");
        open(engine);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        MessageInspect before = engine.inspect(USER, 1L);
        assertTrue(engine.editMessage(USER, 1L, "two").isSuccess());
        MessageInspect after = engine.inspect(USER, 1L);
        assertEquals("two", engine.readMessage(USER, 1L).getText());
        assertEquals(before.getRouxId(), after.getRouxId());
        assertEquals(before.getRole(), after.getRole());
        assertEquals(before.getTimestampEpochMs(), after.getTimestampEpochMs());
        assertEquals(1L, engine.lastMessageId(USER));
    }

    /**
     * The method checks that the row names the later frame.
     * The row count stays the same.
     */
    @Test
    void p2_02_rowPointsAtTheLaterFrame() {
        RouxEngine engine = engine("p202");
        open(engine);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        MessageInspect before = engine.inspect(USER, 1L);
        assertTrue(engine.editMessage(USER, 1L, "two").isSuccess());
        MessageInspect after = engine.inspect(USER, 1L);
        assertTrue(after.getOffset() > before.getOffset());
        assertEquals(before.getPageId(), after.getPageId());
        assertEquals(1L, engine.lastMessageId(USER));
    }

    /**
     * The method checks that the first frame still holds the older bytes.
     * The live row names the later offset.
     */
    @Test
    void p2_03_olderBytesStay() {
        RouxEngine engine = engine("p203");
        open(engine);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        MessageInspect before = engine.inspect(USER, 1L);
        byte[] older = engine.peek(USER, before.getPageId(), before.getOffset(), before.getLength());
        assertTrue(engine.editMessage(USER, 1L, "two").isSuccess());
        MessageInspect after = engine.inspect(USER, 1L);
        byte[] still = engine.peek(USER, before.getPageId(), before.getOffset(), before.getLength());
        assertArrayEquals(older, still);
        assertTrue(after.getOffset() != before.getOffset());
    }

    /**
     * The method checks the later frame header.
     * The length is the padded header plus the payload.
     * The type is a message, and the message id is the old sequence.
     */
    @Test
    void p2_04_headerStaysFiftySix() {
        RouxEngine engine = engine("p204");
        open(engine);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        assertTrue(engine.editMessage(USER, 1L, "two").isSuccess());
        MessageInspect after = engine.inspect(USER, 1L);
        int tokens = new HeuristicTokenCounter().count("two");
        byte[] payload = ToonCodec.encode(Role.USER, TIME, tokens, "two");
        assertEquals(FrameHeader.totalLength(payload.length), after.getLength());
        assertTrue(after.getLength() >= FrameHeader.SIZE);
        assertEquals(FrameType.MSG, after.getType());
        byte[] frame = engine.peek(USER, after.getPageId(), after.getOffset(), after.getLength());
        assertNotNull(frame);
        assertEquals(FrameType.MSG, frame[FrameHeader.OFF_TYPE]);
        long id = ByteBuffer.wrap(frame).order(ByteOrder.LITTLE_ENDIAN).getLong(FrameHeader.OFF_MESSAGE_ID);
        assertEquals(1L, id);
    }

    /**
     * The method checks that an edit of one message leaves the other message in place.
     * The index size stays two.
     */
    @Test
    void p2_05_otherSequenceStays() {
        RouxEngine engine = engine("p205");
        open(engine);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, "other").isSuccess());
        assertTrue(engine.editMessage(USER, 1L, "two").isSuccess());
        assertEquals("two", engine.readMessage(USER, 1L).getText());
        assertEquals("other", engine.readMessage(USER, 2L).getText());
        assertEquals(2L, engine.lastMessageId(USER));
    }

    /**
     * The method plants a continuation after the edited frame and a later message after that.
     * The reopen leaves the edited row on the message frame.
     * The later message is still read, so the scan advanced past the continuation.
     *
     * @throws IOException The pack plant can fail.
     */
    @Test
    void p2_06_continuationLeavesTheRow() throws IOException {
        Path dir = Files.createTempDirectory("roux-retarget-p206");
        dirs.add(dir);
        RouxEngine first = engineOn(dir);
        open(first);
        assertTrue(first.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        assertTrue(first.editMessage(USER, 1L, "two").isSuccess());
        MessageInspect edited = first.inspect(USER, 1L);
        first.shutdown(true);
        plant(dir);
        RouxEngine again = engineOn(dir);
        OpenResult restored = again.openRegion(USER);
        assertEquals(OpenResult.RESTORED, restored.getKind());
        MessageInspect row = again.inspect(USER, 1L);
        assertEquals(edited.getPageId(), row.getPageId());
        assertEquals(edited.getOffset(), row.getOffset());
        assertEquals(edited.getLength(), row.getLength());
        assertEquals("two", again.readMessage(USER, 1L).getText());
        assertEquals("planted", again.readMessage(USER, 2L).getText());
        assertEquals(2L, again.lastMessageId(USER));
    }

    /**
     * The method checks that an empty body and an unknown sequence leave the live offset.
     */
    @Test
    void refusalsLeaveTheLiveOffset() {
        RouxEngine engine = engine("refuse");
        open(engine);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        int offset = engine.inspect(USER, 1L).getOffset();
        OperationResult empty = engine.editMessage(USER, 1L, "");
        assertTrue(!empty.isSuccess());
        assertEquals("An empty body is refused.", empty.getMessage());
        assertEquals(offset, engine.inspect(USER, 1L).getOffset());
        OperationResult missing = engine.editMessage(USER, 9L, "nope");
        assertTrue(!missing.isSuccess());
        assertEquals("No message seq 9", missing.getMessage());
        assertEquals(offset, engine.inspect(USER, 1L).getOffset());
    }

    /**
     * The method writes one continuation and one following message onto the closed pack.
     * The page checksum covers the new used bytes.
     *
     * @param dir The directory is the spill root.
     * @throws IOException The pack read or write can fail.
     */
    private static void plant(Path dir) throws IOException {
        Path pages = dir.resolve(USER).resolve("pages.bin");
        byte[] file = Files.readAllBytes(pages);
        Arena arena = Arena.ofConfined();
        try {
            MemorySegment seg = arena.allocate(PAGE, 8);
            int copy = file.length < PAGE ? file.length : PAGE;
            MemorySegment.copy(file, 0, seg, ValueLayout.JAVA_BYTE, 0, copy);
            int used = PageHeader.usedBytes(seg);
            byte[] contPayload = ToonCodec.encode(Role.USER, TIME, 1, "cont");
            byte[] msgPayload = ToonCodec.encode(Role.USER, TIME, 1, "planted");
            int contTotal = FrameHeader.totalLength(contPayload.length);
            int msgTotal = FrameHeader.totalLength(msgPayload.length);
            assertTrue(used + contTotal + msgTotal <= PAGE);
            FrameHeader.write(seg, used, contTotal, FrameType.CONT, Role.USER, 1L, TIME, 1, null, contPayload);
            FrameHeader.write(seg, used + contTotal, msgTotal, FrameType.MSG, Role.USER, 2L, TIME, 1, null,
                    msgPayload);
            PageHeader.setUsedBytes(seg, used + contTotal + msgTotal);
            PageHeader.setFrameCount(seg, PageHeader.frameCount(seg) + 2);
            PageHeader.writeCrc(seg);
            byte[] page = new byte[PAGE];
            MemorySegment.copy(seg, ValueLayout.JAVA_BYTE, 0, page, 0, PAGE);
            if (file.length >= PAGE) {
                System.arraycopy(page, 0, file, 0, PAGE);
                Files.write(pages, file);
            } else {
                Files.write(pages, page);
            }
        } finally {
            arena.close();
        }
    }

    /**
     * The method opens a new username region.
     *
     * @param engine The engine receives the region.
     */
    private static void open(RouxEngine engine) {
        OpenResult opened = engine.openRegion(USER);
        assertEquals(OpenResult.NEW, opened.getKind());
    }

    /**
     * The method builds a disk engine over a new temporary directory.
     *
     * @param name The name is the directory suffix.
     * @return The engine is open for a later region.
     */
    private RouxEngine engine(String name) {
        Path dir;
        try {
            dir = Files.createTempDirectory("roux-retarget-" + name);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        dirs.add(dir);
        return engineOn(dir);
    }

    /**
     * The method builds a disk engine over a directory the test already owns.
     *
     * @param dir The directory is the spill root.
     * @return The engine writes into that directory.
     */
    private RouxEngine engineOn(Path dir) {
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(4);
        properties.setDurability("commit");
        properties.setPageBytes(PAGE);
        properties.getSpill().setEnabled(true);
        properties.getSpill().setDir(dir.toString());
        properties.getSession().setPersist(true);
        properties.getSession().setRestore(true);
        RouxEngine engine = new RouxEngine(properties, new RegionPack(properties), new RouxCounters(),
                new CryptoSink(properties), new RouxIdGenerator());
        engines.add(engine);
        return engine;
    }

    /**
     * The method deletes a temporary directory and the files inside it.
     *
     * @param dir The directory is the spill root.
     * @throws IOException A file delete can fail.
     */
    private static void deleteTree(Path dir) throws IOException {
        if (dir == null || !Files.exists(dir)) {
            return;
        }
        if (Files.isDirectory(dir)) {
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            try {
                for (Path child : stream) {
                    deleteTree(child);
                }
            } finally {
                stream.close();
            }
        }
        Files.deleteIfExists(dir);
    }
}
