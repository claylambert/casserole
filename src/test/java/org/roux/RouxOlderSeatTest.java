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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.roux.crypto.CryptoSink;
import org.roux.id.RouxId;
import org.roux.id.RouxIdGenerator;
import org.roux.model.HealthReport;
import org.roux.model.LockToken;
import org.roux.model.OperationResult;
import org.roux.model.OperationStatus;
import org.roux.model.Role;
import org.roux.session.OpenResult;
import org.roux.spill.RegionPack;
import org.roux.tel.RouxCounters;

/**
 * The class checks the older engine seats the plate tests leave open.
 * A reeled row stays in the toon table.
 * A pinned region stays when force is false.
 * The unbuilt seats return the v1 refusal sentences.
 *
 * @author clayl (2026-11-23)
 */
class RouxOlderSeatTest {

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
     * The method stores two rows and rolls them behind the origin.
     * The toon table still contains the reeled user line.
     * A missing region returns an empty string.
     *
     * @return The method returns nothing.
     */
    @Test
    void exportToonKeepsAReeledRow() {
        Seat seat = seat("toon");
        assertEquals("", seat.engine.exportToon("missing"));
        assertEquals("messages[0]{r,ts,tok,x}:\n", seat.engine.exportToon(USER));
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 100, "old").isSuccess());
        assertTrue(seat.engine.appendMessage(USER, Role.ASSISTANT, TIME, 10, "new").isSuccess());
        assertEquals("messages[2]{r,ts,tok,x}:\n"
                + "user,1710000000000,100,old\n"
                + "assistant,1710000000000,10,new\n", seat.engine.exportToon(USER));
        seat.engine.rollHead(USER, 0, 0, 20, 5, null);
        assertTrue(seat.engine.clipOrigin(USER) > 1L);
        assertTrue(seat.engine.exportToon(USER).indexOf("user,1710000000000,100,old") >= 0);
    }

    /**
     * The method resolves a stored identity to sequence 1.
     * A null id returns -1.
     * An unknown id returns -1.
     * A missing region returns -1.
     *
     * @return The method returns nothing.
     */
    @Test
    void findSeqResolvesAStoredId() {
        Seat seat = seat("seq");
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "row").isSuccess());
        RouxId stored = seat.engine.inspect(USER, 1L).getRouxId();
        assertNotNull(stored);
        assertEquals(1L, seat.engine.findSeq(USER, stored));
        assertEquals(-1L, seat.engine.findSeq(USER, null));
        assertEquals(-1L, seat.engine.findSeq(USER, new RouxId(1L, 2L)));
        assertEquals(-1L, seat.engine.findSeq("missing", new RouxId(3L, 4L)));
    }

    /**
     * The method pins a region and refuses a close without force.
     * The stored row stays readable.
     * A missing region is already closed.
     *
     * @return The method returns nothing.
     */
    @Test
    void aPinnedRegionStays() {
        Seat seat = seat("pin");
        OperationResult closed = seat.engine.safeFreeRegion("missing", false);
        assertTrue(closed.isSuccess());
        assertEquals("already closed", closed.getMessage());
        assertTrue(seat.engine.appendMessage(USER, Role.USER, TIME, 1, "stay").isSuccess());
        seat.engine.setPinned(USER, true);
        OperationResult pinned = seat.engine.safeFreeRegion(USER, false);
        assertFalse(pinned.isSuccess());
        assertEquals(OperationStatus.BLOCKED_BY_PIN, pinned.getStatus());
        assertEquals("region pinned", pinned.getMessage());
        assertEquals("stay", seat.engine.readMessage(USER, 1L).getText());
    }

    /**
     * The method reports a missing region as not found.
     * A new hot page is healthy.
     * A published reel page stays healthy.
     *
     * @return The method returns nothing.
     * @throws IOException The page read can fail.
     */
    @Test
    void verifyIntegrityReportsTheHotPage() throws IOException {
        Seat seat = seat("hot");
        HealthReport missing = seat.engine.verifyIntegrity("missing");
        assertFalse(missing.isHealthy());
        assertEquals("not found", missing.getDetail());
        assertEquals("missing", missing.getRegionId());
        HealthReport opened = seat.engine.verifyIntegrity(USER);
        assertTrue(opened.isHealthy());
        assertEquals("", opened.getDetail());
        three(seat);
        assertTrue(magic(seat, 0));
        HealthReport after = seat.engine.verifyIntegrity(USER);
        assertTrue(after.isHealthy());
        assertEquals("", after.getDetail());
    }

    /**
     * The method returns null for a missing region.
     * A free region returns a token and the release succeeds.
     * A lock held on another thread returns null when the timeout is 0.
     *
     * @return The method returns nothing.
     * @throws InterruptedException The holder wait can be interrupted.
     */
    @Test
    void tryAcquireLockOnAMissingRegionReturnsNull() throws InterruptedException {
        Seat seat = seat("lock");
        assertNull(seat.engine.tryAcquireLock("missing", 1, 0L));
        LockToken token = seat.engine.tryAcquireLock(USER, 1, 1000L);
        assertNotNull(token);
        assertTrue(seat.engine.releaseLock(token));
        CountDownLatch held = new CountDownLatch(1);
        CountDownLatch release = new CountDownLatch(1);
        Holder holder = new Holder(seat.engine, held, release);
        Thread thread = new Thread(holder);
        thread.start();
        held.await();
        try {
            assertNull(seat.engine.tryAcquireLock(USER, 1, 0L));
        } finally {
            release.countDown();
            thread.join();
        }
    }

    /**
     * The method refuses a snapshot, a defragment, a restore, and a heap dump.
     * The region id is not read.
     * The path is not read.
     *
     * @return The method returns nothing.
     */
    @Test
    void theUnbuiltSeatsRefuse() {
        RouxProperties properties = new RouxProperties();
        properties.setDurability("hot-only");
        properties.getSpill().setEnabled(false);
        RouxEngine engine = new RouxEngine(properties, new RegionPack(properties), new RouxCounters(),
                new CryptoSink(properties), new RouxIdGenerator());
        engines.add(engine);
        assertNull(engine.createAtomicSnapshot(null, "label"));
        OperationResult missing = engine.defragment("missing");
        assertFalse(missing.isSuccess());
        assertEquals(OperationStatus.UNSUPPORTED, missing.getStatus());
        assertEquals("defragment not implemented in v1", missing.getMessage());
        OperationResult named = engine.defragment(USER);
        assertFalse(named.isSuccess());
        assertEquals(OperationStatus.UNSUPPORTED, named.getStatus());
        assertEquals("defragment not implemented in v1", named.getMessage());
        OperationResult restore = engine.restoreSnapshot(null);
        assertFalse(restore.isSuccess());
        assertEquals(OperationStatus.UNSUPPORTED, restore.getStatus());
        assertEquals("snapshot restore not implemented in v1", restore.getMessage());
        OperationResult dump = engine.exportHeapDump(null);
        assertFalse(dump.isSuccess());
        assertEquals(OperationStatus.UNSUPPORTED, dump.getStatus());
        assertEquals("heap dump not implemented in v1", dump.getMessage());
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
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(8);
        properties.setDurability("commit");
        properties.setPageBytes(PAGE);
        properties.getSpill().setEnabled(true);
        properties.getSpill().setAlignBytes(PAGE);
        properties.getSession().setPersist(true);
        properties.getSession().setRestore(true);
        Path dir;
        try {
            dir = Files.createTempDirectory("roux-older-" + name);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        dirs.add(dir);
        properties.getSpill().setDir(dir.toString());
        RegionPack pack = new RegionPack(properties);
        RouxEngine engine = new RouxEngine(properties, pack, new RouxCounters(), new CryptoSink(properties),
                new RouxIdGenerator());
        engines.add(engine);
        OpenResult open = engine.openRegion(USER);
        assertEquals(OpenResult.NEW, open.getKind());
        return new Seat(engine, pack, open);
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
     * The holder takes the region lock on another thread.
     */
    private static final class Holder implements Runnable {
        private final RouxEngine engine;
        private final CountDownLatch held;
        private final CountDownLatch release;

        /**
         * The constructor stores the engine and the two latches.
         *
         * @param engine The engine holds the region.
         * @param held The latch opens after the lock is taken.
         * @param release The latch opens when the test is ready to unlock.
         */
        private Holder(RouxEngine engine, CountDownLatch held, CountDownLatch release) {
            this.engine = engine;
            this.held = held;
            this.release = release;
        }

        /**
         * The method takes the lock, waits, and then releases it.
         *
         * @return The method returns nothing.
         */
        @Override
        public void run() {
            LockToken token = engine.tryAcquireLock(USER, 1, 1000L);
            held.countDown();
            try {
                release.await();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            engine.releaseLock(token);
        }
    }
}
