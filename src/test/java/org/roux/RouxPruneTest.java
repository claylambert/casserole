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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.roux.crypto.CryptoSink;
import org.roux.id.RouxIdGenerator;
import org.roux.model.DecodedFrame;
import org.roux.model.Role;
import org.roux.session.OpenResult;
import org.roux.session.RegionManifest;
import org.roux.spill.RegionPack;
import org.roux.tel.RouxCounters;

/**
 * The class checks a sweep that releases a spilled page no live span keeps.
 * The test does not open a socket and it does not boot the server.
 *
 * @author clayl (2026-11-22)
 */
class RouxPruneTest {

    private static final String USER = "clay";
    private static final long TIME = 1710000000000L;
    private static final int PAGE = 4096;
    private static final int TIGHT = 184;
    private static final int CAP = 120;
    private static final String INTERVAL = "prune interval below 1 minute";

    private final java.util.ArrayList<Path> dirs = new java.util.ArrayList<Path>();
    private final java.util.ArrayList<RouxEngine> engines = new java.util.ArrayList<RouxEngine>();

    /**
     * The catcher counts one interval sentence.
     */
    private static final class LogCatch extends AbstractAppender {

        private int count;

        /**
         * The constructor names the appender.
         */
        private LogCatch() {
            super("roux-prune", null, null, true);
        }

        /**
         * The method counts the interval sentence.
         *
         * @param event The event is one log record.
         */
        @Override
        public void append(LogEvent event) {
            if (INTERVAL.equals(event.getMessage().getFormattedMessage())) {
                count = count + 1;
            }
        }
    }

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
     * The method checks a dead page and a page that still has one live frame.
     *
     * @throws IOException The pack read can fail.
     */
    @Test
    void p5_01_aDeadPageIsReleasedAndOneLiveFrameKeepsTheSlot() throws IOException {
        Seat kept = seat("p501keep", PAGE, 0);
        assertTrue(kept.engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        assertTrue(kept.engine.appendMessage(USER, Role.USER, TIME, 1, "two").isSuccess());
        assertTrue(kept.engine.growHotPage(USER).isSuccess());
        assertTrue(kept.engine.appendMessage(USER, Role.USER, TIME, 1, "three").isSuccess());
        assertTrue(kept.engine.editMessage(USER, 1L, "one-b").isSuccess());
        kept.engine.checkpoint(USER);
        assertEquals(0, kept.engine.sweepDead(USER));
        assertEquals(0L, kept.counters.pagesReleased().get());
        assertTrue(magic(kept, 0));
        assertEquals("two", kept.engine.readMessage(USER, 2L).getText());

        Seat dead = seat("p501dead", PAGE, 0);
        assertTrue(dead.engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        assertTrue(dead.engine.growHotPage(USER).isSuccess());
        assertTrue(dead.engine.appendMessage(USER, Role.USER, TIME, 1, "two").isSuccess());
        assertTrue(dead.engine.editMessage(USER, 1L, "one-b").isSuccess());
        dead.engine.checkpoint(USER);
        long length = Files.size(dead.pack.packFile(USER));
        assertEquals(1, dead.engine.sweepDead(USER));
        assertEquals(1L, dead.counters.pagesReleased().get());
        assertEquals(length, Files.size(dead.pack.packFile(USER)));
        assertTrue(cleared(dead, 0));
        assertTrue(magic(dead, 1));
        assertEquals("one-b", dead.engine.readMessage(USER, 1L).getText());
        assertEquals("two", dead.engine.readMessage(USER, 2L).getText());
    }

    /**
     * The method checks the hot page, a pin, and a region with no pack.
     *
     * @throws IOException The pack read can fail.
     */
    @Test
    void p5_02_hotStaysPinWaitsAndNoPackSkips() throws IOException {
        Seat seat = seat("p502", PAGE, 0);
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        assertTrue(seat.engine.growHotPage(USER).isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "two").isSuccess());
        assertTrue(seat.engine.editMessage(USER, 1L, "one-b").isSuccess());
        seat.engine.checkpoint(USER);
        seat.engine.pinPage(USER, 0);
        assertEquals(0, seat.engine.sweepDead(USER));
        assertTrue(magic(seat, 0));
        seat.engine.unpinPage(USER, 0);
        assertEquals(1, seat.engine.sweepDead(USER));
        assertTrue(cleared(seat, 0));
        assertTrue(magic(seat, 1));
        assertEquals("two", seat.engine.readMessage(USER, 2L).getText());

        Path dir = directory("p502ram");
        RouxProperties properties = new RouxProperties();
        properties.setDurability("commit");
        properties.setPageBytes(PAGE);
        properties.getSpill().setEnabled(false);
        properties.getSpill().setDir(dir.toString());
        RouxCounters counters = new RouxCounters();
        RegionPack pack = new RegionPack(properties);
        RouxEngine engine = new RouxEngine(properties, pack, counters, new CryptoSink(properties),
                new RouxIdGenerator());
        engines.add(engine);
        assertEquals(OpenResult.NEW, engine.openRegion(USER).getKind());
        assertTrue(engine.appendMessage(USER, Role.USER, TIME, 1, "ram").isSuccess());
        assertEquals(0, engine.sweepDead(USER));
        assertFalse(pack.packExists(USER));
        assertEquals(0L, counters.pagesReleased().get());
    }

    /**
     * The method checks a middle hole and a tail truncate.
     *
     * @throws IOException The pack read can fail.
     */
    @Test
    void p5_03_aTailTruncatesAndAMiddleSlotBecomesAHole() throws IOException {
        Seat hole = seat("p503hole", PAGE, 0);
        assertTrue(hole.engine.appendMessage(USER, Role.USER, TIME, 1, "a").isSuccess());
        assertTrue(hole.engine.growHotPage(USER).isSuccess());
        assertTrue(hole.engine.appendMessage(USER, Role.USER, TIME, 1, "b").isSuccess());
        assertTrue(hole.engine.growHotPage(USER).isSuccess());
        assertTrue(hole.engine.appendMessage(USER, Role.USER, TIME, 1, "c").isSuccess());
        assertTrue(hole.engine.editMessage(USER, 2L, "b2").isSuccess());
        hole.engine.checkpoint(USER);
        long length = Files.size(hole.pack.packFile(USER));
        assertEquals(1, hole.engine.sweepDead(USER));
        assertEquals(length, Files.size(hole.pack.packFile(USER)));
        assertTrue(magic(hole, 0));
        assertTrue(cleared(hole, 1));
        assertTrue(magic(hole, 2));
        assertEquals("a", hole.engine.readMessage(USER, 1L).getText());
        assertEquals("b2", hole.engine.readMessage(USER, 2L).getText());
        assertEquals("c", hole.engine.readMessage(USER, 3L).getText());
        assertTrue(hole.engine.persistClose(USER).isSuccess());
        assertEquals(RegionManifest.SIZE, Files.size(manifest(hole)));

        Seat tail = seat("p503tail", PAGE, 0);
        assertTrue(tail.engine.appendMessage(USER, Role.USER, TIME, 1, "a").isSuccess());
        assertTrue(tail.engine.growHotPage(USER).isSuccess());
        assertTrue(tail.engine.appendMessage(USER, Role.USER, TIME, 1, "b").isSuccess());
        tail.engine.checkpoint(USER);
        assertEquals(2L * PAGE, Files.size(tail.pack.packFile(USER)));
        assertTrue(tail.engine.persistClose(USER).isSuccess());
        assertEquals(RegionManifest.SIZE, Files.size(manifest(tail)));
        tail.pack.releaseSlot(USER, 1, true);
        assertEquals((long) PAGE, Files.size(tail.pack.packFile(USER)));
        assertEquals(RegionManifest.SIZE, Files.size(manifest(tail)));
    }

    /**
     * The method checks the interval and the sweep thread.
     */
    @Test
    void p5_04_theIntervalAndTheThread() {
        RouxProperties properties = new RouxProperties();
        assertEquals("20m", properties.getPrune().getInterval());
        assertEquals(20, properties.getPrune().minutes());
        RouxCounters counters = new RouxCounters();
        RegionPack pack = new RegionPack(properties);
        RouxEngine engine = new RouxEngine(properties, pack, counters, new CryptoSink(properties),
                new RouxIdGenerator());
        engines.add(engine);
        DeadPagePrune bean = new DeadPagePrune(engine, properties);
        LogCatch catcher = attach();
        try {
            bean.start();
            assertTrue(bean.isRunning());
            bean.stop();
            assertFalse(bean.isRunning());
            properties.getPrune().setInterval("5m");
            assertEquals(5, properties.getPrune().minutes());
            properties.getPrune().setInterval("0m");
            bean.start();
            assertFalse(bean.isRunning());
            bean.start();
            assertEquals(1, catcher.count);
            properties.getPrune().setEnabled(false);
            properties.getPrune().setInterval("20m");
            bean.start();
            assertFalse(bean.isRunning());
            assertEquals(1, catcher.count);
        } finally {
            bean.stop();
            detach(catcher);
        }
    }

    /**
     * The method checks that a stored span keeps the later page.
     *
     * @throws IOException The pack read can fail.
     */
    @Test
    void p5_05_aStoredSpanKeepsTheLaterPage() throws IOException {
        Seat seat = seat("p505", TIGHT, CAP);
        String text = spanText();
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, text).isSuccess());
        int last = seat.engine.lastPage(USER, 1L);
        assertTrue(last != 0);
        seat.engine.checkpoint(USER);
        assertEquals(0, seat.engine.sweepDead(USER));
        assertEquals(0L, seat.counters.pagesReleased().get());
        assertTrue(magic(seat, 0));
        assertTrue(magic(seat, last));
        DecodedFrame read = seat.engine.readMessage(USER, 1L);
        assertNotNull(read);
        assertEquals(text, read.getText());
    }

    /**
     * The method checks that a sweep before any retarget releases nothing.
     *
     * @throws IOException The pack read can fail.
     */
    @Test
    void p5_06_beforeRetargetTheSweepIsEmpty() throws IOException {
        Seat seat = seat("p506", PAGE, 0);
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        assertTrue(seat.engine.growHotPage(USER).isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "two").isSuccess());
        seat.engine.checkpoint(USER);
        long length = Files.size(seat.pack.packFile(USER));
        byte[] before = Files.readAllBytes(seat.pack.packFile(USER));
        assertEquals(0, seat.engine.sweepDead(USER));
        assertEquals(0L, seat.counters.pagesReleased().get());
        assertEquals(length, Files.size(seat.pack.packFile(USER)));
        assertTrue(same(before, Files.readAllBytes(seat.pack.packFile(USER))));
        assertEquals("one", seat.engine.readMessage(USER, 1L).getText());
        assertEquals("two", seat.engine.readMessage(USER, 2L).getText());
    }

    /**
     * The method checks that a sweep before the checkpoint releases nothing.
     *
     * @throws IOException The pack read can fail.
     */
    @Test
    void p5_07_aSweepBeforeTheCheckpointReleasesNothing() throws IOException {
        Seat seat = seat("p507", PAGE, 0);
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        assertTrue(seat.engine.growHotPage(USER).isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "two").isSuccess());
        assertTrue(seat.engine.editMessage(USER, 1L, "one-b").isSuccess());
        assertEquals(0, seat.engine.sweepDead(USER));
        assertEquals(0L, seat.counters.pagesReleased().get());
        assertTrue(magic(seat, 0));
        seat.engine.checkpoint(USER);
        assertEquals(1, seat.engine.sweepDead(USER));
        assertTrue(cleared(seat, 0));
        assertEquals("one-b", seat.engine.readMessage(USER, 1L).getText());
    }

    /**
     * The method checks that a region opened from a pack releases nothing.
     *
     * @throws IOException The pack read can fail.
     */
    @Test
    void p5_08_aRestoredRegionReleasesNothing() throws IOException {
        Seat seat = seat("p508", PAGE, 0);
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "one").isSuccess());
        assertTrue(seat.engine.growHotPage(USER).isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "two").isSuccess());
        assertTrue(seat.engine.editMessage(USER, 1L, "one-b").isSuccess());
        seat.engine.checkpoint(USER);
        assertTrue(seat.engine.persistClose(USER).isSuccess());
        RouxEngine again = reopen(seat);
        assertEquals(OpenResult.RESTORED, again.openRegion(USER).getKind());
        assertEquals(0, again.sweepDead(USER));
        assertTrue(magic(seat, 0));
        assertEquals("one-b", again.readMessage(USER, 1L).getText());
        assertEquals("two", again.readMessage(USER, 2L).getText());
    }

    /**
     * The method builds a body that crosses one tight page.
     *
     * @return The text is the message body.
     */
    private static String spanText() {
        String text = "";
        for (int i = 0; i < 80; i++) {
            text = text + "a";
        }
        return text;
    }

    /**
     * The method opens a disk region.
     *
     * @param name The name is the directory suffix.
     * @param pageBytes The page size is the slot length.
     * @param maxFrame The cap is the max frame, or 0 for the page cap.
     * @return The seat is the open engine.
     */
    private Seat seat(String name, int pageBytes, int maxFrame) {
        Path dir = directory(name);
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(8);
        properties.setDurability("commit");
        properties.setPageBytes(pageBytes);
        if (maxFrame > 0) {
            properties.setMaxFrameBytes(maxFrame);
        }
        properties.getSpill().setEnabled(true);
        properties.getSpill().setDir(dir.toString());
        properties.getSpill().setAlignBytes(pageBytes);
        properties.getSession().setPersist(true);
        properties.getSession().setRestore(true);
        RouxCounters counters = new RouxCounters();
        RegionPack pack = new RegionPack(properties);
        RouxEngine engine = new RouxEngine(properties, pack, counters, new CryptoSink(properties),
                new RouxIdGenerator());
        engines.add(engine);
        assertEquals(OpenResult.NEW, engine.openRegion(USER).getKind());
        return new Seat(engine, pack, counters, pageBytes);
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
        properties.setPageBytes(seat.pageBytes);
        properties.getSpill().setEnabled(true);
        properties.getSpill().setDir(seat.pack.packDir(USER).getParent().toString());
        properties.getSpill().setAlignBytes(seat.pageBytes);
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
            dir = Files.createTempDirectory("roux-prune-" + name);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        dirs.add(dir);
        return dir;
    }

    /**
     * The method reports whether a slot still has the page magic.
     *
     * @param seat The seat holds the pack.
     * @param pageId The page id is the slot.
     * @return The flag is true when the magic is present.
     * @throws IOException The read failed.
     */
    private static boolean magic(Seat seat, int pageId) throws IOException {
        byte[] raw = Files.readAllBytes(seat.pack.packFile(USER));
        int at = pageId * seat.pageBytes;
        if (at + 4 > raw.length) {
            return false;
        }
        return raw[at] == (byte) 'R'
                && raw[at + 1] == (byte) 'O'
                && raw[at + 2] == (byte) 'U'
                && raw[at + 3] == (byte) 'X';
    }

    /**
     * The method reports whether one slot is zeros.
     *
     * @param seat The seat holds the pack.
     * @param pageId The page id is the slot.
     * @return The flag is true when the slot is zeros.
     * @throws IOException The read failed.
     */
    private static boolean cleared(Seat seat, int pageId) throws IOException {
        byte[] raw = Files.readAllBytes(seat.pack.packFile(USER));
        int at = pageId * seat.pageBytes;
        for (int i = 0; i < seat.pageBytes; i++) {
            if (raw[at + i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method returns the manifest path.
     *
     * @param seat The seat holds the pack.
     * @return The path is {@code manifest.bin}.
     */
    private static Path manifest(Seat seat) {
        return RegionManifest.file(seat.pack.packDir(USER));
    }

    /**
     * The method compares two arrays.
     *
     * @param left The left array is the earlier file.
     * @param right The right array is the later file.
     * @return The flag is true when the bytes match.
     */
    private static boolean same(byte[] left, byte[] right) {
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
     * The method attaches a catcher to the prune logger.
     *
     * @return The catcher is the attached appender.
     */
    private LogCatch attach() {
        org.apache.logging.log4j.core.Logger logger =
                (org.apache.logging.log4j.core.Logger) LogManager.getLogger(DeadPagePrune.class);
        Configurator.setLevel(logger.getName(), Level.ALL);
        LogCatch catcher = new LogCatch();
        catcher.start();
        logger.addAppender(catcher);
        return catcher;
    }

    /**
     * The method removes the catcher.
     *
     * @param catcher The catcher is the appender to remove.
     */
    private void detach(LogCatch catcher) {
        org.apache.logging.log4j.core.Logger logger =
                (org.apache.logging.log4j.core.Logger) LogManager.getLogger(DeadPagePrune.class);
        logger.removeAppender(catcher);
        catcher.stop();
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
        private final RouxCounters counters;
        private final int pageBytes;

        /**
         * The constructor stores the collaborators.
         *
         * @param engine The engine is the open engine.
         * @param pack The pack is the region file.
         * @param counters The counters hold the released-page count.
         * @param pageBytes The page size is the slot length.
         */
        private Seat(RouxEngine engine, RegionPack pack, RouxCounters counters, int pageBytes) {
            this.engine = engine;
            this.pack = pack;
            this.counters = counters;
            this.pageBytes = pageBytes;
        }
    }
}
/*****************************************************************************************
 * End of Class: RouxPruneTest.java 112226
 ****************************************************************************************/
