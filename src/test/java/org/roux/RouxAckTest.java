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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.Test;
import org.roux.page.Page;
import org.roux.spill.RegionPack;

/**
 * The class checks the acknowledgement count.
 * A missing count and a count of 1 wait for one copy.
 * A count below 1 is refused before any byte is written.
 * A count above 1 is logged and the runtime count is 1.
 */
class RouxAckTest {

    private static final String CEILING = "only 1 copy is presently supported";
    private static final String FLOOR = "ack copies below 1";

    /**
     * The catcher stores one throwable message from the ack logger.
     * The count is the number of throwable events.
     */
    private static final class LogCatch extends AbstractAppender {

        private int count;
        private String message;

        /**
         * The constructor names the appender.
         */
        private LogCatch() {
            super("roux-ack", null, null, true);
        }

        /**
         * The method stores the throwable message.
         *
         * @param event The event is one log record.
         */
        @Override
        public void append(LogEvent event) {
            Throwable thrown = event.getThrown();
            if (thrown != null) {
                count = count + 1;
                message = thrown.getMessage();
            }
        }
    }

    /**
     * The method checks a missing count and a count of 1.
     * Neither call logs the ceiling message.
     */
    @Test
    void aMissingCountAndACountOfOneReturnOne() {
        LogCatch catcher = attach();
        try {
            RouxProperties missing = new RouxProperties();
            assertEquals(1, missing.ackCopies());
            RouxProperties one = new RouxProperties();
            one.getAck().setCopies(1);
            assertEquals(1, one.ackCopies());
            assertEquals(0, catcher.count);
        } finally {
            detach(catcher);
        }
    }

    /**
     * The method checks a count below 1.
     * The publish throws before pages.bin exists.
     */
    @Test
    void aCountBelowOneIsRefused() throws IOException {
        RouxProperties zero = new RouxProperties();
        zero.getAck().setCopies(0);
        IllegalArgumentException zeroEx = callAckCopies(zero);
        assertEquals(FLOOR, zeroEx.getMessage());
        RouxProperties negative = new RouxProperties();
        negative.getAck().setCopies(-1);
        IllegalArgumentException negativeEx = callAckCopies(negative);
        assertEquals(FLOOR, negativeEx.getMessage());
        Path dir = Files.createTempDirectory("roux-ack-floor");
        RouxProperties properties = new RouxProperties();
        properties.getSpill().setDir(dir.toString());
        properties.getAck().setCopies(0);
        RegionPack pack = new RegionPack(properties);
        Page page = new Page(0, properties.getPageBytes());
        try {
            IOException ex = callPublish(pack, page);
            assertEquals(FLOOR, ex.getMessage());
            assertFalse(Files.isRegularFile(pack.packFile("clay")));
        } finally {
            page.unmapRam();
            pack.closeChannel("clay");
            deleteTree(dir);
        }
    }

    /**
     * The method checks a count above 1.
     * The catch logs the filed message once.
     * The publish still writes the used bytes.
     */
    @Test
    void aCountAboveOneIsLoggedAndTheRuntimeCountIsOne() throws IOException {
        LogCatch catcher = attach();
        Path dir = Files.createTempDirectory("roux-ack-ceiling");
        try {
            RouxProperties properties = new RouxProperties();
            properties.getAck().setCopies(2);
            assertEquals(1, properties.ackCopies());
            assertEquals(1, catcher.count);
            assertEquals(CEILING, catcher.message);
            assertEquals(1, properties.ackCopies());
            assertEquals(1, catcher.count);
            properties.getSpill().setDir(dir.toString());
            RegionPack pack = new RegionPack(properties);
            Page page = new Page(0, properties.getPageBytes());
            try {
                pack.publishPage("clay", page);
                assertRoux(pack.packFile("clay"), page.usedBytes());
            } finally {
                page.unmapRam();
                pack.closeChannel("clay");
            }
        } finally {
            detach(catcher);
            deleteTree(dir);
        }
    }

    /**
     * The method checks a count of 1.
     * publishPage returns with the used bytes in pages.bin.
     */
    @Test
    void aCountOfOnePublishesTheUsedBytes() throws IOException {
        Path dir = Files.createTempDirectory("roux-ack-one");
        RouxProperties properties = new RouxProperties();
        properties.getSpill().setDir(dir.toString());
        properties.getAck().setCopies(1);
        RegionPack pack = new RegionPack(properties);
        Page page = new Page(0, properties.getPageBytes());
        try {
            pack.publishPage("clay", page);
            assertRoux(pack.packFile("clay"), page.usedBytes());
        } finally {
            page.unmapRam();
            pack.closeChannel("clay");
            deleteTree(dir);
        }
    }

    /**
     * The method calls the resolver and returns the floor exception.
     * A missing exception fails the row.
     *
     * @param properties The properties hold the stored count.
     * @return The exception is the refused count.
     */
    private IllegalArgumentException callAckCopies(RouxProperties properties) {
        try {
            properties.ackCopies();
        } catch (IllegalArgumentException ex) {
            return ex;
        }
        throw new AssertionError(FLOOR);
    }

    /**
     * The method publishes one page and returns the floor exception.
     * A missing exception fails the row.
     *
     * @param pack The pack is the region file.
     * @param page The page is the hot page.
     * @return The exception is the refused publish.
     */
    private IOException callPublish(RegionPack pack, Page page) {
        try {
            pack.publishPage("clay", page);
        } catch (IOException ex) {
            return ex;
        }
        throw new AssertionError(FLOOR);
    }

    /**
     * The method attaches a catcher to the ack logger.
     *
     * @return The catcher is the attached appender.
     */
    private LogCatch attach() {
        org.apache.logging.log4j.core.Logger logger =
                (org.apache.logging.log4j.core.Logger) LogManager.getLogger(RouxProperties.class);
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
                (org.apache.logging.log4j.core.Logger) LogManager.getLogger(RouxProperties.class);
        logger.removeAppender(catcher);
        catcher.stop();
    }

    /**
     * The method checks the magic and the used bytes in pages.bin.
     *
     * @param file The file is pages.bin.
     * @param used The used count is the header used-byte count.
     * @throws IOException The read failed.
     */
    private void assertRoux(Path file, int used) throws IOException {
        assertTrue(Files.isRegularFile(file));
        assertTrue(Files.size(file) >= used);
        FileChannel channel = FileChannel.open(file, StandardOpenOption.READ);
        try {
            ByteBuffer buf = ByteBuffer.allocate(4);
            int n = channel.read(buf);
            assertEquals(4, n);
            buf.flip();
            assertEquals((byte) 'R', buf.get());
            assertEquals((byte) 'O', buf.get());
            assertEquals((byte) 'U', buf.get());
            assertEquals((byte) 'X', buf.get());
        } finally {
            channel.close();
        }
    }

    /**
     * The method deletes a temporary tree.
     *
     * @param dir The directory is the tree to delete.
     * @throws IOException The delete failed.
     */
    private void deleteTree(Path dir) throws IOException {
        if (!Files.isDirectory(dir)) {
            return;
        }
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
        try {
            for (Path path : stream) {
                if (Files.isDirectory(path)) {
                    deleteTree(path);
                } else {
                    Files.deleteIfExists(path);
                }
            }
        } finally {
            stream.close();
        }
        Files.deleteIfExists(dir);
    }
}
