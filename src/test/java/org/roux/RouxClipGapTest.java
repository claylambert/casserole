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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32C;

import org.casserole.chat.ChatSession;
import org.casserole.chat.ShellMode;
import org.casserole.chat.cmd.CommandContext;
import org.casserole.chat.cmd.CommandProcessor;
import org.casserole.chat.cmd.StatusCommand;
import org.casserole.config.CasseroleProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.roux.crypto.CryptoSink;
import org.roux.frame.FrameHeader;
import org.roux.id.RouxIdGenerator;
import org.roux.model.FrameType;
import org.roux.model.MessageInspect;
import org.roux.model.Role;
import org.roux.page.Page;
import org.roux.session.ClipRecord;
import org.roux.session.OpenResult;
import org.roux.spill.RegionPack;
import org.roux.spring.RouxChatMemory;
import org.roux.tel.RouxCounters;
import org.roux.toon.ToonCodec;
import org.roux.token.HeuristicTokenCounter;

/**
 * The class checks the clip branches the plate tests leave open.
 * A hot page stays hot when its last id sits behind the origin.
 * A quiet rewind roll leaves the file bytes in place.
 * A continued message rewinds as one sequence.
 *
 * @author clayl (2026-11-23)
 */
class RouxClipGapTest {

    private static final String USER = "clay";
    private static final long TIME = 1710000000000L;
    private static final int PAGE = 4096;

    private final java.util.ArrayList<Path> dirs = new java.util.ArrayList<Path>();
    private final java.util.ArrayList<RouxEngine> engines = new java.util.ArrayList<RouxEngine>();

    /**
     * The method closes every engine and deletes every temporary directory.
     *
     * @return The method returns nothing.
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
     * The method stores a system row and two user rows, then rolls.
     * Page 0 stays hot.
     * The head line is {@code head: 4 auto 20 15 5}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aLeadingSystemPageStaysHot() {
        Seat seat = seat("sys");
        assertTrue(seat.engine.appendMessage(USER, Role.SYSTEM, TIME, 10, "init").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "a").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "b").isSuccess());
        seat.engine.rollHead(USER, 0, 0, 20, 5, null);
        assertEquals(4L, seat.engine.clipOrigin(USER));
        assertEquals("head: 4 auto 20 15 5", seat.engine.headLine(USER));
        assertTrue(has(window(seat.engine, USER), 1L));
        assertEquals(10, seat.engine.inspect(USER, 1L).getTokenCount());
        assertFalse(seat.pack.packExists(USER));
        assertEquals("init", seat.engine.readMessage(USER, 1L).getText());
        assertEquals("a", seat.engine.readMessage(USER, 2L).getText());
        assertEquals("b", seat.engine.readMessage(USER, 3L).getText());
    }

    /**
     * The method puts the mood row behind a rewind, then rolls.
     * The page stays hot.
     * The head line is {@code head: 4 auto 20 5 15}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aMoodMarkPageStaysHot() {
        Seat seat = seat("mood");
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 10, "MOOD line").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "mid").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "tail").isSuccess());
        assertNull(seat.engine.rewind(USER, false, 2L));
        assertTrue(seat.engine.headLine(USER).startsWith("head: 2 rewind "));
        seat.engine.rollHead(USER, 0, 0, 20, 5, "MOOD");
        assertEquals(4L, seat.engine.clipOrigin(USER));
        assertEquals("head: 4 auto 20 5 15", seat.engine.headLine(USER));
        assertFalse(seat.pack.packExists(USER));
        assertEquals("MOOD line", seat.engine.readMessage(USER, 1L).getText());
    }

    /**
     * The method rolls through a pack whose publish throws.
     * The in-memory clip stands.
     * {@code clip.bin} remains a regular file.
     *
     * @return The method returns nothing.
     */
    @Test
    void aPublishThrowLeavesTheClip() {
        Seat seat = throwing("throw");
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "a").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "b").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "c").isSuccess());
        seat.engine.rollHead(USER, 0, 0, 20, 5, null);
        assertEquals(4L, seat.engine.clipOrigin(USER));
        assertEquals("head: 4 auto 20 5 15", seat.engine.headLine(USER));
        assertFalse(seat.pack.packExists(USER));
        assertTrue(Files.isRegularFile(clip(seat)));
        assertEquals("a", seat.engine.readMessage(USER, 1L).getText());
        assertEquals("b", seat.engine.readMessage(USER, 2L).getText());
        assertEquals("c", seat.engine.readMessage(USER, 3L).getText());
    }

    /**
     * The method clears a region that already holds a clip file.
     * The origin returns to 0.
     * The next append starts at sequence 1.
     *
     * @return The method returns nothing.
     */
    @Test
    void clearRegionDeletesTheClipFile() {
        Seat seat = seat("clear");
        three(seat);
        assertTrue(Files.isRegularFile(clip(seat)));
        assertEquals(4L, seat.engine.clipOrigin(USER));
        seat.engine.clearRegion(USER);
        assertEquals(0L, seat.engine.clipOrigin(USER));
        assertEquals("head: 0 auto 0 0 0", seat.engine.headLine(USER));
        assertFalse(Files.exists(clip(seat)));
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "after").isSuccess());
        assertEquals(1L, seat.engine.lastMessageId(USER));
        assertEquals("after", seat.engine.readMessage(USER, 1L).getText());
    }

    /**
     * The method rolls a tight head and then a looser head.
     * The origin stays 4.
     * The unfloored window still contains sequence 1.
     *
     * @return The method returns nothing.
     */
    @Test
    void anAutoOriginDoesNotMoveBackward() {
        Seat seat = seat("back");
        three(seat);
        assertTrue(has(seat.engine.windowIds(USER, 0, true, 0), 1L));
        seat.engine.rollHead(USER, 0, 0, 5000, 0, null);
        assertEquals("head: 4 auto 5000 0 5000", seat.engine.headLine(USER));
        ClipRecord record = ClipRecord.read(seat.pack.packDir(USER));
        assertNotNull(record);
        assertTrue(record.isLive());
        assertFalse(record.isRewind());
        assertEquals(4L, record.getOrigin());
        assertEquals(5000, record.getCapacity());
    }

    /**
     * The method rewinds, then rolls a budget that drops nothing.
     * The file bytes stay equal to the saved array.
     *
     * @return The method returns nothing.
     * @throws IOException The file read can fail.
     */
    @Test
    void aQuietRewindRollWritesNothing() throws IOException {
        Seat seat = seat("quiet");
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 10, "one").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 10, "two").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 10, "three").isSuccess());
        assertNull(seat.engine.rewind(USER, false, 3L));
        assertTrue(seat.engine.headLine(USER).startsWith("head: 3 rewind "));
        byte[] saved = Files.readAllBytes(clip(seat));
        seat.engine.rollHead(USER, 0, 0, 5000, 0, null);
        assertArrayEquals(saved, Files.readAllBytes(clip(seat)));
        assertEquals(3L, seat.engine.clipOrigin(USER));
        assertTrue(seat.engine.headLine(USER).startsWith("head: 3 rewind "));
    }

    /**
     * The method stores one message that occupies a MSG frame and a CONT frame.
     * Rewind of sequence 1 keeps that text.
     * Rewind of sequence 2 returns {@code No message seq 2}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aContinuedMessageRewindsAsOneSeq() {
        Seat seat = wide("cont");
        String text = textForChunks(2, 64);
        byte[] enc = ToonCodec.encode(Role.USER, TIME, 1, text);
        assertTrue(enc.length >= 65);
        assertTrue(enc.length <= 128);
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, text).isSuccess());
        assertEquals(1L, seat.engine.lastMessageId(USER));
        MessageInspect row = seat.engine.inspect(USER, 1L);
        byte[] msg = seat.engine.peek(USER, row.getPageId(), row.getOffset(), row.getLength());
        assertEquals(FrameType.MSG, msg[FrameHeader.OFF_TYPE]);
        ByteBuffer msgBuf = ByteBuffer.wrap(msg).order(ByteOrder.LITTLE_ENDIAN);
        assertEquals(2, msgBuf.getInt(FrameHeader.OFF_CHUNK));
        byte[] cont = seat.engine.peek(USER, row.getPageId(), row.getOffset() + row.getLength(), FrameHeader.SIZE);
        assertEquals(FrameType.CONT, cont[FrameHeader.OFF_TYPE]);
        ByteBuffer contBuf = ByteBuffer.wrap(cont).order(ByteOrder.LITTLE_ENDIAN);
        assertEquals(msgBuf.getLong(FrameHeader.OFF_MESSAGE_ID), contBuf.getLong(FrameHeader.OFF_MESSAGE_ID));
        assertEquals(text, seat.engine.readMessage(USER, 1L).getText());
        assertNull(seat.engine.rewind(USER, false, 1L));
        assertEquals(1L, seat.engine.clipOrigin(USER));
        assertTrue(seat.engine.headLine(USER).startsWith("head: 1 rewind "));
        assertEquals(text, seat.engine.readMessage(USER, 1L).getText());
        assertEquals("No message seq 2", seat.engine.rewind(USER, false, 2L));
    }

    /**
     * The method rejects a null directory, a missing file, a short file, a long file, a wrong magic, and a wrong version.
     *
     * @return The method returns nothing.
     * @throws IOException The file write can fail.
     */
    @Test
    void readRejectsAShortFileAWrongMagicAndAWrongVersion() throws IOException {
        assertNull(ClipRecord.read(null));
        Path absent = directory("absent");
        assertNull(ClipRecord.read(absent));
        assertNull(ClipRecord.read(plant(directory("short"), new byte[8])));
        assertNull(ClipRecord.read(plant(directory("long"), new byte[65])));
        byte[] magic = new byte[64];
        magic[0] = (byte) 'X';
        assertNull(ClipRecord.read(plant(directory("magic"), magic)));
        byte[] version = new byte[64];
        version[0] = (byte) 'C';
        version[1] = (byte) 'L';
        version[2] = (byte) 'I';
        version[3] = (byte) 'P';
        ByteBuffer.wrap(version).order(ByteOrder.LITTLE_ENDIAN).putShort(4, (short) 2).putShort(6, (short) 64);
        assertNull(ClipRecord.read(plant(directory("version"), version)));
    }

    /**
     * The method checks the header of an auto record and of a rewind record.
     * The checksum covers bytes 0 through 39.
     *
     * @return The method returns nothing.
     * @throws IOException The file read can fail.
     */
    @Test
    void aGoodRecordKeepsTheHeaderFields() throws IOException {
        Seat seat = seat("good");
        three(seat);
        byte[] raw = Files.readAllBytes(clip(seat));
        assertEquals(64, raw.length);
        assertEquals((byte) 'C', raw[0]);
        assertEquals((byte) 'L', raw[1]);
        assertEquals((byte) 'I', raw[2]);
        assertEquals((byte) 'P', raw[3]);
        ByteBuffer buf = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN);
        assertEquals(1, buf.getShort(4));
        assertEquals(64, buf.getShort(6));
        assertEquals(ClipRecord.LIVE, buf.getInt(8));
        assertEquals(seat.engine.clipOrigin(USER), buf.getLong(12));
        CRC32C crc = new CRC32C();
        crc.update(raw, 0, 40);
        assertEquals((int) crc.getValue(), buf.getInt(40));
        ClipRecord record = ClipRecord.read(seat.pack.packDir(USER));
        assertNotNull(record);
        assertTrue(record.isLive());
        assertFalse(record.isRewind());
        assertEquals(seat.engine.clipOrigin(USER), record.getOrigin());
        Seat second = seat("rewind");
        assertTrue(second.engine.appendMessage(USER, Role.USER, TIME, 10, "a").isSuccess());
        assertTrue(second.engine.appendMessage(USER, Role.USER, TIME, 10, "b").isSuccess());
        assertTrue(second.engine.appendMessage(USER, Role.USER, TIME, 10, "c").isSuccess());
        assertNull(second.engine.rewind(USER, false, 3L));
        byte[] rewound = Files.readAllBytes(clip(second));
        ByteBuffer flags = ByteBuffer.wrap(rewound).order(ByteOrder.LITTLE_ENDIAN);
        assertEquals(ClipRecord.LIVE | ClipRecord.REWIND, flags.getInt(8));
        ClipRecord rewind = ClipRecord.read(second.pack.packDir(USER));
        assertNotNull(rewind);
        assertTrue(rewind.isRewind());
        assertEquals(3L, rewind.getOrigin());
    }

    /**
     * The method writes one auto record.
     * {@code clip.bin} is 64 bytes.
     * {@code clip.bin.tmp} is absent.
     *
     * @return The method returns nothing.
     * @throws IOException The write can fail.
     */
    @Test
    void writeLeavesTheRecordAndDropsTheTemp() throws IOException {
        Path dir = directory("write");
        ClipRecord.write(dir, ClipRecord.auto(7L, 20, 4, 16, TIME));
        Path file = dir.resolve(ClipRecord.FILE_NAME);
        assertTrue(Files.isRegularFile(file));
        assertEquals(64L, Files.size(file));
        assertFalse(Files.exists(dir.resolve("clip.bin.tmp")));
        ClipRecord record = ClipRecord.read(dir);
        assertNotNull(record);
        assertEquals(7L, record.getOrigin());
        assertEquals(20, record.getCapacity());
        assertEquals(4, record.getEstimate());
        assertEquals(16, record.getRemaining());
        assertTrue(record.isLive());
        assertFalse(record.isRewind());
    }

    /**
     * The method runs {@link StatusCommand} after the three-user roll.
     * The builder contains the raw head line and a carriage return.
     *
     * @return The method returns nothing.
     */
    @Test
    void statusWritesTheHeadLine() {
        Seat seat = seat("status");
        three(seat);
        assertEquals("head: 4 auto 20 5 15", seat.engine.headLine(USER));
        RouxChatMemory memory = new RouxChatMemory(seat.engine, new HeuristicTokenCounter());
        ChatSession session = ChatSession.forUser(USER, seat.open, "", memory);
        Tape tape = new Tape(session, USER);
        new StatusCommand().execute(tape, "");
        assertTrue(tape.text().contains("head: 4 auto 20 5 15\r\n"));
    }

    /**
     * The method stores three user rows and rolls them behind the origin.
     *
     * @param seat The seat is the open engine.
     * @return The method returns nothing.
     */
    private static void three(Seat seat) {
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "a").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "b").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "c").isSuccess());
        seat.engine.rollHead(USER, 0, 0, 20, 5, null);
        assertEquals("head: 4 auto 20 5 15", seat.engine.headLine(USER));
        assertEquals(4L, seat.engine.clipOrigin(USER));
    }

    /**
     * The method opens a disk region.
     *
     * @param name The name is the directory suffix.
     * @return The seat is the open engine.
     */
    private Seat seat(String name) {
        return open(name, properties(PAGE), false);
    }

    /**
     * The method opens a disk region whose frame cap is 120 bytes.
     *
     * @param name The name is the directory suffix.
     * @return The seat is the open engine.
     */
    private Seat wide(String name) {
        RouxProperties properties = properties(PAGE);
        properties.setMaxFrameBytes(120);
        return open(name, properties, false);
    }

    /**
     * The method opens a disk region whose pack throws on publish.
     *
     * @param name The name is the directory suffix.
     * @return The seat is the open engine.
     */
    private Seat throwing(String name) {
        return open(name, properties(PAGE), true);
    }

    /**
     * The method builds the disk properties.
     *
     * @param page The page size is the byte count.
     * @return The properties name a commit region.
     */
    private RouxProperties properties(int page) {
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(8);
        properties.setDurability("commit");
        properties.setPageBytes(page);
        properties.getSpill().setEnabled(true);
        properties.getSpill().setAlignBytes(page);
        properties.getSession().setPersist(true);
        properties.getSession().setRestore(true);
        return properties;
    }

    /**
     * The method opens the region on a fresh directory.
     *
     * @param name The name is the directory suffix.
     * @param properties The properties are the region settings.
     * @param throwing The flag selects the pack that throws.
     * @return The seat is the open engine.
     */
    private Seat open(String name, RouxProperties properties, boolean throwing) {
        Path dir = directory(name);
        properties.getSpill().setDir(dir.toString());
        RegionPack pack = throwing ? new ThrowingPack(properties) : new RegionPack(properties);
        RouxEngine engine = new RouxEngine(properties, pack, new RouxCounters(), new CryptoSink(properties),
                new RouxIdGenerator());
        engines.add(engine);
        OpenResult open = engine.openRegion(USER);
        assertEquals(OpenResult.NEW, open.getKind());
        return new Seat(engine, pack, open);
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
            dir = Files.createTempDirectory("roux-clip-gap-" + name);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        dirs.add(dir);
        return dir;
    }

    /**
     * The method writes {@code clip.bin} into a directory.
     *
     * @param dir The dir receives the file.
     * @param raw The raw bytes are the file body.
     * @return The directory is the same directory.
     * @throws IOException The write can fail.
     */
    private static Path plant(Path dir, byte[] raw) throws IOException {
        Files.write(dir.resolve(ClipRecord.FILE_NAME), raw);
        return dir;
    }

    /**
     * The method returns the clip path.
     *
     * @param seat The seat holds the pack.
     * @return The path is {@code clip.bin}.
     */
    private static Path clip(Seat seat) {
        return seat.pack.packDir(USER).resolve(ClipRecord.FILE_NAME);
    }

    /**
     * The method returns the clipped window.
     *
     * @param engine The engine holds the region.
     * @param region The region is the conversation id.
     * @return The ids are the clipped window.
     */
    private static long[] window(RouxEngine engine, String region) {
        long floor = engine.clipOrigin(region);
        if (floor > 0L) {
            return engine.windowIds(region, 0, true, 0, floor);
        }
        return engine.windowIds(region, 0, true, 0);
    }

    /**
     * The method reports whether an id is in the window.
     *
     * @param ids The ids are the window.
     * @param id The id is the candidate.
     * @return The flag is true when the id is present.
     */
    private static boolean has(long[] ids, long id) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method reports whether a slot has the page magic.
     *
     * @param seat The seat holds the pack.
     * @param pageId The page id is the slot.
     * @return The flag is true when the magic is present.
     * @throws IOException The read failed.
     */
    static boolean magic(Seat seat, int pageId) throws IOException {
        byte[] raw = Files.readAllBytes(seat.pack.packFile(USER));
        int at = pageId * PAGE;
        if (at + 4 > raw.length) {
            return false;
        }
        return raw[at] == (byte) 'R' && raw[at + 1] == (byte) 'O' && raw[at + 2] == (byte) 'U'
                && raw[at + 3] == (byte) 'X';
    }

    /**
     * The method builds a body whose encoding needs the given chunk count.
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
                java.util.Iterator<Path> children = stream.iterator();
                while (children.hasNext()) {
                    deleteTree(children.next());
                }
            } finally {
                stream.close();
            }
        }
        Files.deleteIfExists(dir);
    }

    /**
     * The pack throws from {@link RegionPack#publishPage}.
     */
    private static final class ThrowingPack extends RegionPack {

        /**
         * The constructor stores the properties.
         *
         * @param properties The properties are the region settings.
         */
        private ThrowingPack(RouxProperties properties) {
            super(properties);
        }

        /**
         * The method refuses the publish.
         *
         * @param regionId The region id is the folder name.
         * @param page The page is the page being published.
         * @throws IOException The page has no RAM segment.
         */
        @Override
        public void publishPage(String regionId, Page page) throws IOException {
            throw new IOException("page has no RAM segment");
        }
    }

    /**
     * The Seat type holds one engine and its pack.
     */
    private static final class Seat {
        private final RouxEngine engine;
        private final RegionPack pack;
        private final OpenResult open;

        /**
         * The constructor stores the collaborators.
         *
         * @param engine The engine is the open engine.
         * @param pack The pack is the region file.
         * @param open The open result is the region open.
         */
        private Seat(RouxEngine engine, RegionPack pack, OpenResult open) {
            this.engine = engine;
            this.pack = pack;
            this.open = open;
        }
    }

    /**
     * The class records the text a command writes.
     * The other shell methods stay unused by this test.
     */
    private static final class Tape implements CommandContext {
        private final ChatSession session;
        private final String user;
        private final StringBuilder written = new StringBuilder();

        /**
         * The constructor stores the session.
         *
         * @param session The session is the disk conversation.
         * @param user The user is the SSH name.
         */
        private Tape(ChatSession session, String user) {
            this.session = session;
            this.user = user;
        }

        /**
         * The method returns the recorded text.
         *
         * @return The text is everything {@link #write} received.
         */
        private String text() {
            return written.toString();
        }

        @Override
        public ChatSession getSession() {
            return session;
        }

        @Override
        public ShellMode getShellMode() {
            return ShellMode.COMMAND;
        }

        @Override
        public CasseroleProperties getProperties() {
            return null;
        }

        @Override
        public String getModelName() {
            return "";
        }

        @Override
        public String getConsoleThink() {
            return "";
        }

        @Override
        public String getConsoleProvider() {
            return "ollama";
        }

        @Override
        public void writeModelStatus() {
        }

        @Override
        public boolean selectConsoleFamily(String family) {
            return false;
        }

        @Override
        public String getSshUsername() {
            return user;
        }

        @Override
        public CommandProcessor getProcessor() {
            return null;
        }

        @Override
        public void write(String text) {
            written.append(text);
        }

        @Override
        public void writeError(String text) {
        }

        @Override
        public void setChatActive(boolean active) {
        }

        @Override
        public void requestExit() {
        }

        @Override
        public void setModelName(String modelName) {
        }

        @Override
        public void collectMessageEdit(long seq) {
        }
    }
}
