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

package org.roux.spill;

import java.io.IOException;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.roux.RouxProperties;
import org.roux.page.Page;
import org.roux.page.PageHeader;
import org.roux.session.RegionManifest;
import org.springframework.stereotype.Component;

// @aa-sdd PK-0789 docs/specs/java/org/roux/roux.spec.md
/**
 * The RegionPack is the random-access {@code pages.bin} file for one region.
 * Slot {@code P} starts at {@code P} times the page size from the settings.
 * {@link #publishPage} writes the used bytes, pads to the alignment, and forces the channel.
 * The page stays mapped.
 * One channel stays open per region.
 *
 * @aa-sdd PK-0815 PK-3317 PK-3467 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
@Component
public class RegionPack {

    private final RouxProperties properties;
    private final Map<String, FileChannel> channels;
    private final Map<String, FileChannel> lockChannels;
    private final Map<String, FileLock> locks;

    /**
     * The constructor stores the settings and starts with no open files.
     *
     * @param properties The properties are the settings this pack reads.
     */
    public RegionPack(RouxProperties properties) {
        this.properties = properties;
        this.channels = new HashMap<String, FileChannel>();
        this.lockChannels = new HashMap<String, FileChannel>();
        this.locks = new HashMap<String, FileLock>();
    }

    /**
     * The method reports whether spill is enabled.
     * A missing spill block returns false.
     *
     * @return The flag is true when a publish may write.
     */
    public boolean isEnabled() {
        return properties.getSpill() != null && properties.getSpill().isEnabled();
    }

    /**
     * The method returns the directory for one region.
     * A missing directory setting uses the user home plus {@code /.casserole/roux}.
     *
     * @param regionId The region id is the folder name under that directory.
     * @return The path is the region directory.
     */
    public Path regionDir(String regionId) {
        String root = properties.getSpill() == null ? null : properties.getSpill().getDir();
        if (root == null || root.length() == 0) {
            root = System.getProperty("user.home") + "/.casserole/roux";
        }
        return Path.of(root, regionId);
    }

    /**
     * The method returns the path of {@code pages.bin}.
     *
     * @param regionId The region id is the folder name.
     * @return The path is {@code pages.bin} in that folder.
     */
    public Path packFile(String regionId) {
        return regionDir(regionId).resolve("pages.bin");
    }

    /**
     * The method returns the path of the lock file.
     * The file name is {@code .lock}.
     *
     * @param regionId The region id is the folder name.
     * @return The path is {@code .lock} in that folder.
     */
    public Path lockFile(String regionId) {
        return regionDir(regionId).resolve(".lock");
    }

    /**
     * The method reports whether {@code pages.bin} exists as a regular file.
     *
     * @param regionId The region id is the folder name.
     * @return The flag is true when that file exists.
     */
    public boolean packExists(String regionId) {
        return Files.isRegularFile(packFile(regionId));
    }

    /**
     * The method reports whether {@code manifest.bin} exists as a regular file.
     *
     * @param regionId The region id is the folder name.
     * @return The flag is true when that file exists.
     */
    public boolean manifestExists(String regionId) {
        return Files.isRegularFile(RegionManifest.file(regionDir(regionId)));
    }

    /**
     * The method tries to take an exclusive lock on {@code .lock}.
     * A lock this process already holds returns true.
     * The directory is created when it is absent.
     * An overlapping lock returns false.
     * A lock that another process holds returns false.
     * An input-output error returns false.
     *
     * @param regionId The region id is the folder name.
     * @return The flag is true when this process holds the lock.
     */
    public synchronized boolean tryLock(String regionId) {
        FileLock existing = locks.get(regionId);
        if (existing != null && existing.isValid()) {
            return true;
        }
        try {
            Path dir = regionDir(regionId);
            Files.createDirectories(dir);
            FileChannel channel = FileChannel.open(lockFile(regionId), StandardOpenOption.CREATE,
                    StandardOpenOption.READ, StandardOpenOption.WRITE);
            FileLock lock;
            try {
                lock = channel.tryLock();
            } catch (OverlappingFileLockException ex) {
                channel.close();
                return false;
            }
            if (lock == null) {
                channel.close();
                return false;
            }
            lockChannels.put(regionId, channel);
            locks.put(regionId, lock);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * The method releases the exclusive lock.
     * A region with no lock is left unchanged.
     * An error while releasing or closing is ignored.
     *
     * @param regionId The region id is the folder name.
     */
    public synchronized void unlock(String regionId) {
        FileLock lock = locks.remove(regionId);
        if (lock != null) {
            try {
                if (lock.isValid()) {
                    lock.release();
                }
            } catch (IOException ignored) {
                // ignore
            }
        }
        FileChannel channel = lockChannels.remove(regionId);
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException ignored) {
                // ignore
            }
        }
    }

    /**
     * The method deletes {@code pages.bin}, {@code manifest.bin}, and {@code manifest.bin.tmp}.
     * The directory stays.
     * The lock stays.
     * The open pack channel is closed first.
     * An input-output error is ignored.
     *
     * @param regionId The region id is the folder name.
     */
    public synchronized void wipePack(String regionId) {
        closeChannel(regionId);
        try {
            Files.deleteIfExists(packFile(regionId));
            Files.deleteIfExists(RegionManifest.file(regionDir(regionId)));
            Files.deleteIfExists(regionDir(regionId).resolve("manifest.bin.tmp"));
        } catch (IOException ignored) {
            // ignore
        }
    }

    /**
     * The method returns the file offset of one page slot.
     * The offset is the page id times the page size from the settings.
     *
     * @param pageId The page id is the slot number.
     * @return The offset is the first byte of that slot.
     */
    public long slotOffset(int pageId) {
        return (long) pageId * properties.getPageBytes();
    }

    /**
     * The method writes one page into its slot.
     * A page with no mapped bytes throws {@link IOException} with {@code page has no RAM segment}.
     * The checksum is written first.
     * The used bytes are padded up to the alignment, and not past the page size.
     * A missing spill block, or an alignment of {@code 1} or less, uses {@code 4096}.
     * The channel is forced.
     * The page stays mapped.
     * A write that returns a negative count throws {@link IOException} with {@code pack write EOF}.
     *
     * @aa-sdd PK-0815 PK-3317 PK-3467 docs/specs/java/org/roux/roux.spec.md
     *
     * @param regionId The region id is the folder name.
     * @param page The page is the page with a live segment.
     * @throws IOException The page has no mapped bytes, or the write failed.
     */
    public void publishPage(String regionId, Page page) throws IOException {
        if (page.getSegment() == null) {
            throw new IOException("page has no RAM segment");
        }
        PageHeader.writeCrc(page.getSegment());
        int used = page.usedBytes();
        int align = 4096;
        if (properties.getSpill() != null && properties.getSpill().getAlignBytes() > 1) {
            align = properties.getSpill().getAlignBytes();
        }
        int padded = used;
        if (align > 1) {
            padded = (used + align - 1) / align * align;
            if (padded > properties.getPageBytes()) {
                padded = properties.getPageBytes();
            }
        }
        byte[] copy = new byte[padded];
        MemorySegment.copy(page.getSegment(), ValueLayout.JAVA_BYTE, 0, copy, 0, used);
        FileChannel channel = open(regionId);
        long pos = slotOffset(page.getPageId());
        ByteBuffer buf = ByteBuffer.wrap(copy);
        while (buf.hasRemaining()) {
            int n = channel.write(buf, pos + buf.position());
            if (n < 0) {
                throw new IOException("pack write EOF");
            }
        }
        channel.force(true);
    }

    /**
     * The method copies a range from one slot onto the heap.
     * A short read leaves the unread tail as zeros.
     *
     * @param regionId The region id is the folder name.
     * @param pageId The page id is the slot number.
     * @param offsetInPage The offset is the first byte inside the page.
     * @param length The length is the number of bytes to copy.
     * @return The array is the heap copy.
     * @throws IOException The read failed.
     */
    public byte[] readRange(String regionId, int pageId, int offsetInPage, int length) throws IOException {
        byte[] dest = new byte[length];
        FileChannel channel = open(regionId);
        long pos = slotOffset(pageId) + offsetInPage;
        ByteBuffer buf = ByteBuffer.wrap(dest);
        while (buf.hasRemaining()) {
            int n = channel.read(buf, pos + buf.position());
            if (n < 0) {
                break;
            }
        }
        return dest;
    }

    /**
     * The method closes the pack channel, releases the lock, and deletes the region directory.
     * A missing directory returns without an error.
     * An input-output error is ignored.
     *
     * @param regionId The region id is the folder name.
     */
    public void deleteRegion(String regionId) {
        closeChannel(regionId);
        unlock(regionId);
        Path dir = regionDir(regionId);
        if (!Files.isDirectory(dir)) {
            return;
        }
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            try {
                for (Path path : stream) {
                    Files.deleteIfExists(path);
                }
            } finally {
                stream.close();
            }
            Files.deleteIfExists(dir);
        } catch (IOException ignored) {
            // ignore
        }
    }

    /**
     * The method closes the open pack channel for one region.
     * A region with no channel is left unchanged.
     * An error while closing is ignored.
     *
     * @param regionId The region id is the folder name.
     */
    public synchronized void closeChannel(String regionId) {
        FileChannel ch = channels.remove(regionId);
        if (ch != null) {
            try {
                ch.close();
            } catch (IOException ignored) {
                // ignore
            }
        }
    }

    /**
     * The method returns the open channel for {@code pages.bin}.
     * An open channel is reused.
     * The directory is created when it is absent.
     *
     * @param regionId The region id is the folder name.
     * @return The channel is the open file.
     * @throws IOException The file could not be opened.
     */
    private synchronized FileChannel open(String regionId) throws IOException {
        FileChannel existing = channels.get(regionId);
        if (existing != null && existing.isOpen()) {
            return existing;
        }
        Path dir = regionDir(regionId);
        Files.createDirectories(dir);
        Path file = packFile(regionId);
        FileChannel channel = FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.READ,
                StandardOpenOption.WRITE);
        channels.put(regionId, channel);
        return channel;
    }
}
/*****************************************************************************************
 * End of Class: RegionPack.java 082626
 ****************************************************************************************/
