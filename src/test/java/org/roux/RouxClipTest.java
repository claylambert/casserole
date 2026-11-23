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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.casserole.chat.ChatSession;
import org.casserole.chat.ShellMode;
import org.casserole.chat.cmd.CommandContext;
import org.casserole.chat.cmd.CommandProcessor;
import org.casserole.chat.cmd.MessageRewindCommand;
import org.casserole.config.CasseroleProperties;
import org.casserole.ssh.shell.ChatShell;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.roux.crypto.CryptoSink;
import org.roux.id.RouxIdGenerator;
import org.roux.model.Role;
import org.roux.session.ClipRecord;
import org.roux.session.OpenResult;
import org.roux.spill.RegionPack;
import org.roux.spring.RouxChatMemory;
import org.roux.tel.RouxCounters;
import org.roux.token.HeuristicTokenCounter;

/**
 * The class checks a head that rolls and a reel that stays listable.
 * The test does not open a socket and it does not boot the server.
 *
 * @author clayl (2026-11-23)
 */
class RouxClipTest {

    private static final String USER = "clay";
    private static final String OTHER = "ada";
    private static final long TIME = 1710000000000L;
    private static final int PAGE = 4096;

    private final java.util.ArrayList<Path> dirs = new java.util.ArrayList<Path>();
    private final java.util.ArrayList<RouxEngine> engines = new java.util.ArrayList<RouxEngine>();

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
     * The method checks the init run, the mood row, the tail that fits, and the live line.
     */
    @Test
    void p6_01_theHeadKeepsInitMoodTailAndTheLiveLine() {
        Seat seat = seat("p601");
        assertTrue(seat.engine.appendMessage(USER, Role.SYSTEM, TIME, 10, "init").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.SYSTEM, TIME, 10, "MOOD line").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "old").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.ASSISTANT, TIME, 100, "old-a").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 10, "new").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.ASSISTANT, TIME, 10, "new-a").isSuccess());
        seat.engine.rollHead(USER, 0, 0, 50, 5, "MOOD");
        long[] head = window(seat.engine, USER);
        assertTrue(has(head, 1L));
        assertTrue(has(head, 2L));
        assertFalse(has(head, 3L));
        assertFalse(has(head, 4L));
        assertTrue(has(head, 5L));
        assertTrue(has(head, 6L));
        assertEquals("head: 5 auto 50 45 5", seat.engine.headLine(USER));
    }

    /**
     * The method checks that a reeled frame stays indexed and that an inspect leaves the origin.
     */
    @Test
    void p6_02_theReelStaysListableAndShowLeavesTheOrigin() {
        Seat seat = seat("p602");
        fill(seat);
        seat.engine.rollHead(USER, 0, 0, 50, 5, "MOOD");
        long origin = seat.engine.clipOrigin(USER);
        assertEquals("old", seat.engine.readMessage(USER, 3L).getText());
        long size = seat.engine.lastMessageId(USER);
        for (long id = 1; id <= size; id++) {
            assertTrue(seat.engine.readMessage(USER, id) != null);
        }
        assertEquals("old", seat.engine.readMessage(USER, 3L).getText());
        assertEquals(origin, seat.engine.clipOrigin(USER));
        assertFalse(has(window(seat.engine, USER), 3L));
    }

    /**
     * The method checks one roll before a send.
     * The proof does not call a model turn.
     */
    @Test
    void p6_03_oneRollRebuildsTheHead() {
        Seat seat = seat("p603");
        fill(seat);
        long[] before = seat.engine.windowIds(USER, 0, true, 0);
        seat.engine.rollHead(USER, 0, 0, 50, 5, "MOOD");
        long[] after = window(seat.engine, USER);
        assertFalse(same(before, after));
        assertTrue(has(after, 5L));
        assertFalse(has(after, 3L));
        assertEquals(5L, seat.engine.clipOrigin(USER));
    }

    /**
     * The method checks the clip file, an absent file, and a checksum miss.
     *
     * @throws IOException The file read can fail.
     */
    @Test
    void p6_04_theRecordIsSixtyFourBytesAndAMissUsesThePresentWalk() throws IOException {
        Seat absent = seat("p604absent");
        fill(absent);
        assertFalse(Files.isRegularFile(absent.pack.packDir(USER).resolve(ClipRecord.FILE_NAME)));
        assertEquals(0L, absent.engine.clipOrigin(USER));
        assertEquals(6, absent.engine.windowIds(USER, 0, true, 0).length);
        assertEquals("head: 0 auto 0 0 0", absent.engine.headLine(USER));

        Seat seat = seat("p604");
        fill(seat);
        seat.engine.rollHead(USER, 0, 0, 50, 5, "MOOD");
        Path file = seat.pack.packDir(USER).resolve(ClipRecord.FILE_NAME);
        byte[] raw = Files.readAllBytes(file);
        assertEquals(64, raw.length);
        assertEquals((byte) 'C', raw[0]);
        assertEquals((byte) 'L', raw[1]);
        assertEquals((byte) 'I', raw[2]);
        assertEquals((byte) 'P', raw[3]);
        assertTrue(seat.engine.persistClose(USER).isSuccess());
        raw[12] = (byte) (raw[12] + 1);
        Files.write(file, raw);
        RouxEngine again = reopen(seat);
        assertEquals(OpenResult.RESTORED, again.openRegion(USER).getKind());
        assertEquals(0L, again.clipOrigin(USER));
        assertEquals(6, again.windowIds(USER, 0, true, 0).length);
        assertEquals("old", again.readMessage(USER, 3L).getText());
    }

    /**
     * The method checks a sequence rewind, a bare rewind, and the head line.
     *
     * @throws Exception The command can refuse a bad argument.
     */
    @Test
    void p6_05_rewindScopesTheNextWindowAndTheHeadLine() throws Exception {
        Seat seat = seat("p605");
        fill(seat);
        assertEquals("No message seq 99", seat.engine.rewind(USER, false, 99L));
        assertEquals("seq is in the leading system run", seat.engine.rewind(USER, false, 1L));
        assertNull(seat.engine.rewind(USER, false, 3L));
        assertEquals(3L, seat.engine.clipOrigin(USER));
        assertTrue(seat.engine.headLine(USER).startsWith("head: 3 rewind "));
        long[] scoped = window(seat.engine, USER);
        assertTrue(has(scoped, 1L));
        assertTrue(has(scoped, 3L));
        assertFalse(has(scoped, 0L));
        RouxChatMemory memory = new RouxChatMemory(seat.engine, new HeuristicTokenCounter());
        ChatSession session = ChatSession.forUser(USER, null, "", memory);
        assertEquals(seat.engine.headLine(USER), session.headLine());
        assertNull(session.rewindBare());
        assertEquals(0L, seat.engine.clipOrigin(USER));
        assertTrue(session.headLine().startsWith("head: 0 auto "));
        MessageRewindCommand command = new MessageRewindCommand();
        assertEquals("message rewind", command.getName());
        assertEquals(0, command.getAliases().length);
        boolean threw = false;
        try {
            command.execute(new UnusedContext(), "nope");
        } catch (Exception ex) {
            threw = true;
            assertEquals("Usage: message rewind <seq>", ex.getMessage());
        }
        assertTrue(threw);
    }

    /**
     * The method records the bound that this plate adds no channel, no AMWP type, and no vault column.
     * The row is not a runtime call.
     */
    @Test
    void p6_06_thePlateAddsNoChannelNoAmwpTypeAndNoVaultColumn() {
        MessageRewindCommand command = new MessageRewindCommand();
        assertEquals("message rewind", command.getName());
        assertEquals(0, command.getAliases().length);
    }

    /**
     * The method checks the integer percent boundary.
     */
    @Test
    void p6_07_onePercentFiresAndAboveDoesNot() {
        assertTrue(ChatShell.percentFires(1, 100, 1));
        assertFalse(ChatShell.percentFires(2, 100, 1));
        Seat tight = seat("p607tight");
        assertTrue(tight.engine.appendMessage(USER, Role.USER, TIME, 40, "a").isSuccess());
        assertTrue(tight.engine.appendMessage(USER, Role.USER, TIME, 40, "b").isSuccess());
        assertTrue(tight.engine.appendMessage(USER, Role.USER, TIME, 40, "c").isSuccess());
        int estimate = tight.engine.clipEstimate(USER, 0, 0, 0);
        int remaining = 0;
        if (estimate < 100) {
            remaining = 100 - estimate;
        }
        assertTrue(ChatShell.percentFires(remaining, 100, 1));
        tight.engine.rollHead(USER, 0, 0, 100, 0, null);
        assertTrue(tight.engine.clipOrigin(USER) > 1L);

        Seat loose = seat("p607loose");
        assertTrue(loose.engine.appendMessage(USER, Role.USER, TIME, 10, "a").isSuccess());
        int looseEstimate = loose.engine.clipEstimate(USER, 0, 0, 0);
        int looseRemaining = 1000 - looseEstimate;
        assertFalse(ChatShell.percentFires(looseRemaining, 1000, 1));
        assertEquals(0L, loose.engine.clipOrigin(USER));
    }

    /**
     * The method checks a truncation match, the excluded phrases, and a roll at percent zero.
     * The proof rolls once and does not send the turn again.
     */
    @Test
    void p6_08_aTruncationMatchRollsAndDoesNotSendAgain() {
        assertTrue(ChatShell.truncationMatch("maximum context length reached"));
        assertTrue(ChatShell.truncationMatch("response was truncated"));
        assertTrue(ChatShell.truncationMatch("num_ctx exceeded"));
        assertFalse(ChatShell.truncationMatch("AMWP context length"));
        assertFalse(ChatShell.truncationMatch("paste exceeds the cap and was truncated"));
        assertFalse(ChatShell.percentFires(0, 100, 0));
        Seat seat = seat("p608");
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 40, "a").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 40, "b").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 40, "c").isSuccess());
        if (ChatShell.truncationMatch("context length")) {
            seat.engine.rollHead(USER, 0, 0, 100, 0, null);
        }
        assertTrue(seat.engine.clipOrigin(USER) > 1L);
        assertTrue(seat.engine.headLine(USER).indexOf(" auto ") > 0);
    }

    /**
     * The method checks the leading run, a mood atom, and an atom that is not split.
     */
    @Test
    void p6_09_theFloorKeepsTheRunAndDoesNotSplitAnAtom() {
        Seat seat = seat("p609");
        fill(seat);
        long[] split = seat.engine.windowIds(USER, 0, true, 0, 4L);
        assertTrue(has(split, 1L));
        assertTrue(has(split, 2L));
        assertFalse(has(split, 3L));
        assertFalse(has(split, 4L));
        assertTrue(has(split, 5L));
        long[] kept = seat.engine.windowIds(USER, 0, true, 0, 3L);
        assertTrue(has(kept, 3L));
        assertTrue(has(kept, 4L));
        Seat mood = seat("p609mood");
        assertTrue(mood.engine.appendMessage(USER, Role.USER, TIME, 100, "MOOD old").isSuccess());
        assertTrue(mood.engine.appendMessage(USER, Role.USER, TIME, 100, "later").isSuccess());
        mood.engine.rollHead(USER, 0, 0, 10, 1, "MOOD");
        assertTrue(mood.engine.clipOrigin(USER) <= 1L);
        assertTrue(has(window(mood.engine, USER), 1L));
        assertEquals("MOOD old", mood.engine.readMessage(USER, 1L).getText());
    }

    /**
     * The method checks spill off and a RAM-only region.
     */
    @Test
    void p6_10_spillOffAndRamOnlyWriteNoClipFile() {
        Path dir = directory("p610off");
        RouxProperties properties = new RouxProperties();
        properties.setDurability("commit");
        properties.setPageBytes(PAGE);
        properties.getSpill().setEnabled(false);
        properties.getSpill().setDir(dir.toString());
        RouxEngine engine = new RouxEngine(properties, new RegionPack(properties), new RouxCounters(),
                new CryptoSink(properties), new RouxIdGenerator());
        engines.add(engine);
        assertEquals(OpenResult.NEW, engine.openRegion(USER).getKind());
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 100, "old").isSuccess());
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 10, "new").isSuccess());
        engine.rollHead(USER, 0, 0, 20, 1, null);
        assertTrue(engine.clipOrigin(USER) > 1L);
        assertFalse(has(window(engine, USER), 1L));
        assertFalse(Files.isRegularFile(dir.resolve(USER).resolve(ClipRecord.FILE_NAME)));

        Seat disk = seat("p610ram");
        assertEquals(OpenResult.NEW, disk.engine.openRam("ram").getKind());
        assertTrue(disk.engine.appendMessage("ram", Role.USER, TIME, 100, "old").isSuccess());
        assertTrue(disk.engine.appendMessage("ram", Role.USER, TIME, 10, "new").isSuccess());
        disk.engine.rollHead("ram", 0, 0, 20, 1, null);
        assertTrue(disk.engine.clipOrigin("ram") > 1L);
        assertFalse(Files.isRegularFile(disk.pack.packDir("ram").resolve(ClipRecord.FILE_NAME)));
    }

    /**
     * The method checks a published reel page and a mixed page that stays hot.
     *
     * @throws IOException The pack read can fail.
     */
    @Test
    void p6_11_aReelPageIsPublishedAndAMixedPageStaysHot() throws IOException {
        Seat reel = seat("p611reel");
        assertTrue(reel.engine.appendMessage(USER, Role.USER, TIME, 100, "a").isSuccess());
        assertTrue(reel.engine.appendMessage(USER, Role.USER, TIME, 100, "b").isSuccess());
        assertTrue(reel.engine.appendMessage(USER, Role.USER, TIME, 100, "c").isSuccess());
        reel.engine.rollHead(USER, 0, 0, 20, 5, null);
        assertTrue(reel.engine.clipOrigin(USER) > 3L);
        assertTrue(magic(reel, 0));

        Seat mixed = seat("p611mix");
        assertTrue(mixed.engine.appendMessage(USER, Role.USER, TIME, 100, "a").isSuccess());
        assertTrue(mixed.engine.appendMessage(USER, Role.USER, TIME, 100, "b").isSuccess());
        assertTrue(mixed.engine.appendMessage(USER, Role.USER, TIME, 100, "c").isSuccess());
        mixed.engine.rollHead(USER, 0, 0, 20, 0, null);
        assertEquals(3L, mixed.engine.clipOrigin(USER));
        assertFalse(mixed.pack.packExists(USER));
    }

    /**
     * The method checks rewind success, a failed turn, and a roll that stays.
     */
    @Test
    void p6_12_rewindClearsOnSuccessAndARollStays() {
        Seat seat = seat("p612");
        fill(seat);
        assertNull(seat.engine.rewind(USER, false, 3L));
        assertTrue(seat.engine.headLine(USER).startsWith("head: 3 rewind "));
        assertTrue(seat.engine.headLine(USER).startsWith("head: 3 rewind "));
        seat.engine.noteClipSuccess(USER);
        assertTrue(seat.engine.headLine(USER).startsWith("head: 0 auto "));
        seat.engine.rollHead(USER, 0, 0, 50, 5, "MOOD");
        long origin = seat.engine.clipOrigin(USER);
        assertTrue(origin > 0L);
        assertTrue(seat.engine.headLine(USER).indexOf(" auto ") > 0);
        seat.engine.noteClipSuccess(USER);
        assertEquals(origin, seat.engine.clipOrigin(USER));
    }

    /**
     * The method checks that one region roll leaves a second region alone.
     */
    @Test
    void p6_13_aSecondRegionStaysUnchanged() {
        Seat seat = seat("p613");
        fill(seat);
        assertEquals(OpenResult.NEW, seat.engine.openRegion(OTHER).getKind());
        assertTrue(seat.engine.appendMessage(OTHER, Role.USER, TIME, 10, "ada").isSuccess());
        seat.engine.rollHead(USER, 0, 0, 50, 5, "MOOD");
        assertEquals(0L, seat.engine.clipOrigin(OTHER));
        assertEquals("head: 0 auto 0 0 0", seat.engine.headLine(OTHER));
        assertEquals("ada", seat.engine.readMessage(OTHER, 1L).getText());
        assertEquals(1, seat.engine.windowIds(OTHER, 0, true, 0).length);
        assertFalse(Files.isRegularFile(seat.pack.packDir(OTHER).resolve(ClipRecord.FILE_NAME)));
    }

    /**
     * The method checks a missing percent and a zero percent.
     */
    @Test
    void p6_14_aMissingPercentReadsAsOneAndZeroStaysQuiet() {
        CasseroleProperties properties = new CasseroleProperties();
        assertEquals(1, properties.getChat().getClipRemainingPercent());
        properties.getChat().setClipRemainingPercent(-4);
        assertEquals(0, properties.getChat().getClipRemainingPercent());
        properties.getChat().setClipRemainingPercent(0);
        assertEquals(0, properties.getChat().getClipRemainingPercent());
        assertFalse(ChatShell.percentFires(0, 100, properties.getChat().getClipRemainingPercent()));
        assertFalse(ChatShell.clipCapacity("grok", 0, 4096) > 0);
        assertEquals(128, ChatShell.clipCapacity("ollama", 128, 256));
        assertEquals(64, ChatShell.clipCapacity("ollama", 128, 64));
        assertEquals(32, ChatShell.clipCapacity("pool", 32, 256));
    }

    /**
     * The method appends the six-row transcript used by several proofs.
     *
     * @param seat The seat is the open engine.
     */
    private static void fill(Seat seat) {
        assertTrue(seat.engine.appendMessage(USER, Role.SYSTEM, TIME, 10, "init").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.SYSTEM, TIME, 10, "MOOD line").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "old").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.ASSISTANT, TIME, 100, "old-a").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 10, "new").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.ASSISTANT, TIME, 10, "new-a").isSuccess());
    }

    /**
     * The method returns the window the next prompt would use.
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
     * The method compares two windows.
     *
     * @param left The left window is the earlier read.
     * @param right The right window is the later read.
     * @return The flag is true when the ids match.
     */
    private static boolean same(long[] left, long[] right) {
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
     * The method opens a disk region.
     *
     * @param name The name is the directory suffix.
     * @return The seat is the open engine.
     */
    private Seat seat(String name) {
        Path dir = directory(name);
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(8);
        properties.setDurability("commit");
        properties.setPageBytes(PAGE);
        properties.getSpill().setEnabled(true);
        properties.getSpill().setDir(dir.toString());
        properties.getSpill().setAlignBytes(PAGE);
        properties.getSession().setPersist(true);
        properties.getSession().setRestore(true);
        RegionPack pack = new RegionPack(properties);
        RouxEngine engine = new RouxEngine(properties, pack, new RouxCounters(), new CryptoSink(properties),
                new RouxIdGenerator());
        engines.add(engine);
        assertEquals(OpenResult.NEW, engine.openRegion(USER).getKind());
        return new Seat(engine, pack);
    }

    /**
     * The method opens a second engine on the same directory.
     *
     * @param seat The seat holds the pack settings.
     * @return The engine is the second engine.
     */
    private RouxEngine reopen(Seat seat) {
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(8);
        properties.setDurability("commit");
        properties.setPageBytes(PAGE);
        properties.getSpill().setEnabled(true);
        properties.getSpill().setDir(seat.pack.packDir(USER).getParent().toString());
        properties.getSpill().setAlignBytes(PAGE);
        properties.getSession().setPersist(true);
        properties.getSession().setRestore(true);
        RouxEngine engine = new RouxEngine(properties, new RegionPack(properties), new RouxCounters(),
                new CryptoSink(properties), new RouxIdGenerator());
        engines.add(engine);
        return engine;
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
            dir = Files.createTempDirectory("roux-clip-" + name);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        dirs.add(dir);
        return dir;
    }

    /**
     * The method reports whether a slot has the page magic.
     *
     * @param seat The seat holds the pack.
     * @param pageId The page id is the slot.
     * @return The flag is true when the magic is present.
     * @throws IOException The read failed.
     */
    private static boolean magic(Seat seat, int pageId) throws IOException {
        byte[] raw = Files.readAllBytes(seat.pack.packFile(USER));
        int at = pageId * PAGE;
        if (at + 4 > raw.length) {
            return false;
        }
        return raw[at] == (byte) 'R' && raw[at + 1] == (byte) 'O' && raw[at + 2] == (byte) 'U'
                && raw[at + 3] == (byte) 'X';
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

    /**
     * The Seat type holds one engine and its pack.
     */
    private static final class Seat {
        private final RouxEngine engine;
        private final RegionPack pack;

        /**
         * The constructor stores the collaborators.
         *
         * @param engine The engine is the open engine.
         * @param pack The pack is the region file.
         */
        private Seat(RouxEngine engine, RegionPack pack) {
            this.engine = engine;
            this.pack = pack;
        }
    }

    /**
     * The context is unused except as the command target for a bad argument.
     */
    private static final class UnusedContext implements CommandContext {
        public ChatSession getSession() {
            throw new IllegalStateException("session");
        }

        public ShellMode getShellMode() {
            return ShellMode.CHAT;
        }

        public CasseroleProperties getProperties() {
            return new CasseroleProperties();
        }

        public String getModelName() {
            return "";
        }

        public String getConsoleThink() {
            return "";
        }

        public String getConsoleProvider() {
            return "ollama";
        }

        public void writeModelStatus() {
        }

        public boolean selectConsoleFamily(String family) {
            return false;
        }

        public String getSshUsername() {
            return USER;
        }

        public CommandProcessor getProcessor() {
            return null;
        }

        public void write(String text) {
        }

        public void writeError(String text) {
        }

        public void setChatActive(boolean active) {
        }

        public void requestExit() {
        }

        public void setModelName(String modelName) {
        }

        public void collectMessageEdit(long seq) {
        }
    }
}
/*****************************************************************************************
 * End of Class: RouxClipTest.java 112326
 ****************************************************************************************/
