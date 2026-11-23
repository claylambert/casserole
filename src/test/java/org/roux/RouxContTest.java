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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.roux.id.RouxId;
import org.roux.id.RouxIdGenerator;
import org.roux.layout.RouxLayout;
import org.roux.model.DecodedFrame;
import org.roux.model.FrameType;
import org.roux.model.MessageInspect;
import org.roux.model.OperationResult;
import org.roux.model.OperationStatus;
import org.roux.model.Role;
import org.roux.page.PageHeader;
import org.roux.session.OpenResult;
import org.roux.spill.RegionPack;
import org.roux.tel.RouxCounters;
import org.roux.toon.ToonCodec;

/**
 * The class checks a message that is cut into chunks.
 * The reader joins the payloads and decodes once.
 *
 * @author clayl (2026-11-22)
 */
class RouxContTest {

    private static final String USER = "clay";
    private static final long TIME = 1710000000000L;
    private static final int WIDE = 4096;
    private static final int TIGHT = 184;
    private static final int CAP = 120;
    private static final int ROOM = 64;
    private static final int KIND_ZERO = 1;
    private static final int KIND_SHORT = 2;
    private static final int KIND_CRC = 3;
    private static final int KIND_TYPE = 4;
    private static final int KIND_ID = 5;
    private static final int KIND_PLACE = 6;

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
     * The method checks that one frame stores chunk word 1.
     * A stored 0 on that message still reads as one frame.
     *
     * @throws IOException The pack rewrite can fail.
     */
    @Test
    void p4_01_oneFrameStoresOne() throws IOException {
        Path dir = directory("p401");
        RouxEngine engine = engineOn(dir, WIDE, 0, new RouxCounters());
        open(engine);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, "hi").isSuccess());
        MessageInspect row = engine.inspect(USER, 1L);
        byte[] frame = engine.peek(USER, row.getPageId(), row.getOffset(), row.getLength());
        assertNotNull(frame);
        assertEquals(1, chunk(frame));
        assertEquals("hi", engine.readMessage(USER, 1L).getText());
        engine.shutdown(true);
        rewrite(dir, WIDE, KIND_ZERO);
        RouxEngine again = reopen(dir, WIDE, 0);
        assertEquals("hi", again.readMessage(USER, 1L).getText());
    }

    /**
     * The method checks that two chunks share one sequence.
     * An edit into two chunks keeps that sequence.
     */
    @Test
    void p4_02_sequenceAdvancesOnce() {
        Path dir = directory("p402");
        RouxEngine engine = engineOn(dir, WIDE, CAP, new RouxCounters());
        open(engine);
        String text = textForChunks(2, ROOM);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, text).isSuccess());
        assertEquals(1L, engine.lastMessageId(USER));
        MessageInspect row = engine.inspect(USER, 1L);
        byte[] msg = engine.peek(USER, row.getPageId(), row.getOffset(), row.getLength());
        assertNotNull(msg);
        assertEquals(FrameType.MSG, msg[FrameHeader.OFF_TYPE]);
        assertEquals(2, chunk(msg));
        int contAt = row.getOffset() + row.getLength();
        byte[] head = engine.peek(USER, row.getPageId(), contAt, FrameHeader.SIZE);
        assertNotNull(head);
        int contTotal = ByteBuffer.wrap(head).order(ByteOrder.LITTLE_ENDIAN).getInt(FrameHeader.OFF_LENGTH);
        byte[] cont = engine.peek(USER, row.getPageId(), contAt, contTotal);
        assertNotNull(cont);
        assertEquals(FrameType.CONT, cont[FrameHeader.OFF_TYPE]);
        assertEquals(1, chunk(cont));
        assertEquals(messageId(msg), messageId(cont));
        assertEquals(msg[FrameHeader.OFF_ROLE], cont[FrameHeader.OFF_ROLE]);
        assertEquals(idHi(msg), idHi(cont));
        assertEquals(idLo(msg), idLo(cont));
        assertEquals(text, engine.readMessage(USER, 1L).getText());

        Path editDir = directory("p402e");
        RouxEngine edit = engineOn(editDir, WIDE, CAP, new RouxCounters());
        open(edit);
        assertTrue(edit.appendMessage(USER, Role.USER, TIME, 1, "hi").isSuccess());
        assertTrue(edit.editMessage(USER, 1L, text).isSuccess());
        assertEquals(1L, edit.lastMessageId(USER));
        assertEquals(text, edit.readMessage(USER, 1L).getText());
        MessageInspect edited = edit.inspect(USER, 1L);
        byte[] editedFrame = edit.peek(USER, edited.getPageId(), edited.getOffset(), edited.getLength());
        assertNotNull(editedFrame);
        assertEquals(2, chunk(editedFrame));
    }

    /**
     * The method checks that a continuation leaves the one row.
     * A next-id continuation stops the page.
     * A present identity that differs stops the page.
     *
     * @throws IOException The pack plant can fail.
     */
    @Test
    void p4_03_scanLeavesTheRow() throws IOException {
        Path dir = directory("p403");
        RouxEngine engine = engineOn(dir, WIDE, CAP, new RouxCounters());
        open(engine);
        String text = textForChunks(2, ROOM);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, text).isSuccess());
        MessageInspect live = engine.inspect(USER, 1L);
        engine.shutdown(true);
        RouxEngine again = reopen(dir, WIDE, CAP);
        MessageInspect row = again.inspect(USER, 1L);
        assertEquals(live.getOffset(), row.getOffset());
        assertEquals(FrameType.MSG, row.getType());
        assertEquals(1L, again.lastMessageId(USER));
        assertEquals(text, again.readMessage(USER, 1L).getText());

        Path next = directory("p403n");
        RouxEngine planted = engineOn(next, WIDE, 0, new RouxCounters());
        open(planted);
        assertTrue(planted.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        planted.shutdown(true);
        plant(next, WIDE, true);
        RouxEngine stopped = reopen(next, WIDE, 0);
        assertEquals(1L, stopped.lastMessageId(USER));
        assertNull(stopped.readMessage(USER, 2L));

        Path mismatch = directory("p403m");
        RouxEngine foreign = engineOn(mismatch, WIDE, 0, new RouxCounters());
        open(foreign);
        assertTrue(foreign.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        foreign.shutdown(true);
        plant(mismatch, WIDE, false);
        RouxEngine held = reopen(mismatch, WIDE, 0);
        assertEquals(1L, held.lastMessageId(USER));
        assertEquals("one", held.readMessage(USER, 1L).getText());
        assertNull(held.readMessage(USER, 2L));
    }

    /**
     * The method checks that a read joins chunks across a page boundary.
     * The joined bytes are the one encoding.
     */
    @Test
    void p4_04_readJoinsAcrossThePage() {
        Path dir = directory("p404");
        RouxEngine engine = engineOn(dir, TIGHT, CAP, new RouxCounters());
        open(engine);
        String text = textForChunks(2, ROOM);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, text).isSuccess());
        assertEquals(text, engine.readMessage(USER, 1L).getText());
        MessageInspect row = engine.inspect(USER, 1L);
        byte[] msg = engine.peek(USER, row.getPageId(), row.getOffset(), row.getLength());
        byte[] header = engine.peek(USER, row.getPageId(), 0, PageHeader.SIZE);
        assertNotNull(msg);
        assertNotNull(header);
        int next = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN).getInt(PageHeader.OFF_NEXT_PAGE);
        assertTrue(next != 0);
        byte[] contHead = engine.peek(USER, next, PageHeader.SIZE, FrameHeader.SIZE);
        assertNotNull(contHead);
        int contTotal = ByteBuffer.wrap(contHead).order(ByteOrder.LITTLE_ENDIAN).getInt(FrameHeader.OFF_LENGTH);
        byte[] cont = engine.peek(USER, next, PageHeader.SIZE, contTotal);
        assertNotNull(cont);
        byte[] first = FrameHeader.payloadFromHeap(msg);
        byte[] second = FrameHeader.payloadFromHeap(cont);
        byte[] joined = new byte[first.length + second.length];
        System.arraycopy(first, 0, joined, 0, first.length);
        System.arraycopy(second, 0, joined, first.length, second.length);
        byte[] encoded = ToonCodec.encode(Role.USER, TIME, 1, text);
        assertTrue(sameBytes(joined, encoded));
    }

    /**
     * The method checks that a bad walk returns null and leaves the row.
     *
     * @throws IOException The pack rewrite can fail.
     */
    @Test
    void p4_05_badWalkReturnsNull() throws IOException {
        int[] kinds = new int[] {KIND_SHORT, KIND_CRC, KIND_TYPE, KIND_ID, KIND_PLACE};
        for (int i = 0; i < kinds.length; i++) {
            Path dir = directory("p405" + i);
            RouxCounters counters = new RouxCounters();
            RouxEngine engine = engineOn(dir, WIDE, CAP, counters);
            open(engine);
            String text = textForChunks(2, ROOM);
            assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, text).isSuccess());
            MessageInspect live = engine.inspect(USER, 1L);
            engine.shutdown(true);
            rewrite(dir, WIDE, kinds[i]);
            RouxCounters againCounters = new RouxCounters();
            RouxEngine again = reopen(dir, WIDE, CAP, againCounters);
            assertNull(again.readMessage(USER, 1L));
            MessageInspect row = again.inspect(USER, 1L);
            assertEquals(live.getOffset(), row.getOffset());
            assertEquals(FrameType.MSG, row.getType());
            assertEquals(1L, again.lastMessageId(USER));
            long dirty = againCounters.dirtyFrames().get();
            if (kinds[i] == KIND_CRC) {
                assertEquals(1L, dirty);
            } else {
                assertEquals(0L, dirty);
            }
        }
    }

    /**
     * The method checks the stored last page id.
     * Zero means the chunks share the page the row names.
     */
    @Test
    void p4_06_lastPage() {
        Path one = directory("p406a");
        RouxEngine same = engineOn(one, WIDE, CAP, new RouxCounters());
        open(same);
        String text = textForChunks(2, ROOM);
        assertTrue(same.appendMessage(USER, Role.USER, TIME, 1, text).isSuccess());
        assertEquals(0, same.lastPage(USER, 1L));

        Path two = directory("p406b");
        RouxEngine span = engineOn(two, TIGHT, CAP, new RouxCounters());
        open(span);
        assertTrue(span.appendMessage(USER, Role.USER, TIME, 1, text).isSuccess());
        MessageInspect row = span.inspect(USER, 1L);
        byte[] header = span.peek(USER, row.getPageId(), 0, PageHeader.SIZE);
        assertNotNull(header);
        int next = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN).getInt(PageHeader.OFF_NEXT_PAGE);
        assertEquals(next, span.lastPage(USER, 1L));
        assertTrue(row.getPageId() != next);
    }

    /**
     * The method checks that 33 chunks are refused before any byte of that message.
     * A message of 32 chunks is written and reads back.
     */
    @Test
    void p4_07_capRefusesThirtyThree() {
        Path dir = directory("p407");
        RouxEngine engine = engineOn(dir, TIGHT, CAP, new RouxCounters());
        open(engine);
        byte[] header = engine.peek(USER, 0, 0, PageHeader.SIZE);
        assertNotNull(header);
        int used = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN).getInt(PageHeader.OFF_USED);
        String tooMany = textForChunks(33, ROOM);
        OperationResult refused = engine.appendMessage(USER, Role.USER, TIME, 1, tooMany);
        assertTrue(!refused.isSuccess());
        assertEquals(OperationStatus.CORRUPTED_SEGMENT, refused.getStatus());
        assertEquals("more than 32 chunks", refused.getMessage());
        assertEquals(0L, engine.lastMessageId(USER));
        byte[] after = engine.peek(USER, 0, 0, PageHeader.SIZE);
        assertNotNull(after);
        int usedAfter = ByteBuffer.wrap(after).order(ByteOrder.LITTLE_ENDIAN).getInt(PageHeader.OFF_USED);
        assertEquals(used, usedAfter);

        String fits = textForChunks(32, ROOM);
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, fits).isSuccess());
        DecodedFrame read = engine.readMessage(USER, 1L);
        assertNotNull(read);
        assertEquals(fits, read.getText());
    }

    /**
     * The method builds a body whose encoding needs the given chunk count.
     * The room is the payload cap of one chunk.
     *
     * @param chunks The count is the number of chunks.
     * @param room The room is the most payload bytes in one chunk.
     * @return The text encodes into that many chunks.
     */
    private static String textForChunks(int chunks, int room) {
        int low = (chunks - 1) * room + 1;
        int high = chunks * room;
        String text = "";
        byte[] enc = ToonCodec.encode(Role.USER, TIME, 1, text);
        while (enc.length < low) {
            text = text + "a";
            enc = ToonCodec.encode(Role.USER, TIME, 1, text);
        }
        if (enc.length > high) {
            throw new AssertionError("chunk size");
        }
        return text;
    }

    /**
     * The method returns the chunk word stored in a heap frame.
     *
     * @param frame The frame is the bytes on the heap.
     * @return The word is the little-endian int at byte 36.
     */
    private static int chunk(byte[] frame) {
        return ByteBuffer.wrap(frame).order(ByteOrder.LITTLE_ENDIAN).getInt(FrameHeader.OFF_CHUNK);
    }

    /**
     * The method returns the message id stored in a heap frame.
     *
     * @param frame The frame is the bytes on the heap.
     * @return The id is the message id.
     */
    private static long messageId(byte[] frame) {
        return ByteBuffer.wrap(frame).order(ByteOrder.LITTLE_ENDIAN).getLong(FrameHeader.OFF_MESSAGE_ID);
    }

    /**
     * The method returns the high identity long stored in a heap frame.
     *
     * @param frame The frame is the bytes on the heap.
     * @return The long is the high half of the identity.
     */
    private static long idHi(byte[] frame) {
        return ByteBuffer.wrap(frame).order(ByteOrder.LITTLE_ENDIAN).getLong(FrameHeader.OFF_ID_HI);
    }

    /**
     * The method returns the low identity long stored in a heap frame.
     *
     * @param frame The frame is the bytes on the heap.
     * @return The long is the low half of the identity.
     */
    private static long idLo(byte[] frame) {
        return ByteBuffer.wrap(frame).order(ByteOrder.LITTLE_ENDIAN).getLong(FrameHeader.OFF_ID_LO);
    }

    /**
     * The method reports whether two arrays hold the same bytes.
     *
     * @param left The left array is the joined payload.
     * @param right The right array is the one encoding.
     * @return The flag is true when the lengths and the bytes match.
     */
    private static boolean sameBytes(byte[] left, byte[] right) {
        if (left.length != right.length) {
            return false;
        }
        for (int i = 0; i < left.length; i++) {
            if (left[i] != right[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method writes one continuation and one later message onto the closed pack.
     *
     * @param dir The directory is the spill root.
     * @param pageBytes The page size is the slot length.
     * @param nextId The flag plants a next-id continuation when it is true.
     * @throws IOException The pack read or write can fail.
     */
    private static void plant(Path dir, int pageBytes, boolean nextId) throws IOException {
        Path pages = dir.resolve(USER).resolve("pages.bin");
        byte[] file = Files.readAllBytes(pages);
        Arena arena = Arena.ofConfined();
        try {
            MemorySegment seg = arena.allocate(pageBytes, 8);
            int copy = file.length < pageBytes ? file.length : pageBytes;
            MemorySegment.copy(file, 0, seg, ValueLayout.JAVA_BYTE, 0, copy);
            int used = PageHeader.usedBytes(seg);
            byte[] contPayload = ToonCodec.encode(Role.USER, TIME, 1, "cont");
            byte[] msgPayload = ToonCodec.encode(Role.USER, TIME, 1, "later");
            int contTotal = FrameHeader.totalLength(contPayload.length);
            int msgTotal = FrameHeader.totalLength(msgPayload.length);
            long contSeq = nextId ? 2L : 1L;
            long msgSeq = nextId ? 3L : 2L;
            RouxId contId = nextId ? null : new RouxId(9L, 9L);
            FrameHeader.write(seg, used, contTotal, FrameType.CONT, Role.USER, contSeq, TIME, 1, contId, contPayload);
            FrameHeader.write(seg, used + contTotal, msgTotal, FrameType.MSG, Role.USER, msgSeq, TIME, 1, null,
                    msgPayload);
            PageHeader.setUsedBytes(seg, used + contTotal + msgTotal);
            PageHeader.setFrameCount(seg, PageHeader.frameCount(seg) + 2);
            PageHeader.writeCrc(seg);
            writePage(pages, file, seg, pageBytes);
        } finally {
            arena.close();
        }
    }

    /**
     * The method changes one frame on the closed pack and rewrites the page checksum.
     *
     * @param dir The directory is the spill root.
     * @param pageBytes The page size is the slot length.
     * @param kind The kind selects the byte that changes.
     * @throws IOException The pack read or write can fail.
     */
    private static void rewrite(Path dir, int pageBytes, int kind) throws IOException {
        Path pages = dir.resolve(USER).resolve("pages.bin");
        byte[] file = Files.readAllBytes(pages);
        Arena arena = Arena.ofConfined();
        try {
            MemorySegment seg = arena.allocate(pageBytes, 8);
            int copy = file.length < pageBytes ? file.length : pageBytes;
            MemorySegment.copy(file, 0, seg, ValueLayout.JAVA_BYTE, 0, copy);
            if (kind == KIND_ZERO || kind == KIND_SHORT) {
                int word = kind == KIND_ZERO ? 0 : 3;
                FrameHeader.setChunk(seg, PageHeader.SIZE, word);
            } else {
                int cont = nextFrame(seg, PageHeader.SIZE);
                if (kind == KIND_CRC) {
                    int payloadAt = cont + FrameHeader.SIZE;
                    byte bit = seg.get(RouxLayout.BYTE, payloadAt);
                    seg.set(RouxLayout.BYTE, payloadAt, (byte) (bit ^ 1));
                } else if (kind == KIND_TYPE) {
                    seg.set(RouxLayout.BYTE, cont + FrameHeader.OFF_TYPE, (byte) 9);
                } else if (kind == KIND_ID) {
                    new RouxId(4L, 5L).write(seg, cont + FrameHeader.OFF_ID_HI);
                } else if (kind == KIND_PLACE) {
                    FrameHeader.setChunk(seg, cont, 9);
                }
            }
            PageHeader.writeCrc(seg);
            writePage(pages, file, seg, pageBytes);
        } finally {
            arena.close();
        }
    }

    /**
     * The method returns the offset of the frame after the frame at the given offset.
     *
     * @param seg The segment is the page.
     * @param offset The offset is the first byte of the current frame.
     * @return The offset is the first byte of the next frame.
     */
    private static int nextFrame(MemorySegment seg, int offset) {
        int total = FrameHeader.length(seg, offset);
        return offset + total;
    }

    /**
     * The method writes one page back into the pack file.
     *
     * @param pages The path is the pack file.
     * @param file The file is the previous pack bytes.
     * @param seg The segment is the rewritten page.
     * @param pageBytes The page size is the slot length.
     * @throws IOException The pack write can fail.
     */
    private static void writePage(Path pages, byte[] file, MemorySegment seg, int pageBytes) throws IOException {
        byte[] page = new byte[pageBytes];
        MemorySegment.copy(seg, ValueLayout.JAVA_BYTE, 0, page, 0, pageBytes);
        if (file.length >= pageBytes) {
            System.arraycopy(page, 0, file, 0, pageBytes);
            Files.write(pages, file);
        } else {
            Files.write(pages, page);
        }
    }

    /**
     * The method opens a restored region.
     *
     * @param dir The directory is the spill root.
     * @param pageBytes The page size is the slot length.
     * @param maxFrame The cap is the max frame, or 0 for the page cap.
     * @return The engine has the region open.
     */
    private RouxEngine reopen(Path dir, int pageBytes, int maxFrame) {
        return reopen(dir, pageBytes, maxFrame, new RouxCounters());
    }

    /**
     * The method opens a restored region on a caller-owned counter.
     *
     * @param dir The directory is the spill root.
     * @param pageBytes The page size is the slot length.
     * @param maxFrame The cap is the max frame, or 0 for the page cap.
     * @param counters The counters receive the dirty-frame count.
     * @return The engine has the region open.
     */
    private RouxEngine reopen(Path dir, int pageBytes, int maxFrame, RouxCounters counters) {
        RouxEngine again = engineOn(dir, pageBytes, maxFrame, counters);
        OpenResult restored = again.openRegion(USER);
        assertEquals(OpenResult.RESTORED, restored.getKind());
        return again;
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
     * The method creates a temporary spill directory.
     *
     * @param name The name is the directory suffix.
     * @return The directory is empty.
     */
    private Path directory(String name) {
        Path dir;
        try {
            dir = Files.createTempDirectory("roux-cont-" + name);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        dirs.add(dir);
        return dir;
    }

    /**
     * The method builds a disk engine over a directory the test already owns.
     *
     * @param dir The directory is the spill root.
     * @param pageBytes The page size is the slot length.
     * @param maxFrame The cap is the max frame, or 0 for the page cap.
     * @param counters The counters receive the engine events.
     * @return The engine writes into that directory.
     */
    private RouxEngine engineOn(Path dir, int pageBytes, int maxFrame, RouxCounters counters) {
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(8);
        properties.setDurability("commit");
        properties.setPageBytes(pageBytes);
        if (maxFrame > 0) {
            properties.setMaxFrameBytes(maxFrame);
        }
        properties.getSpill().setEnabled(true);
        properties.getSpill().setDir(dir.toString());
        properties.getSession().setPersist(true);
        properties.getSession().setRestore(true);
        RouxEngine engine = new RouxEngine(properties, new RegionPack(properties), counters,
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
