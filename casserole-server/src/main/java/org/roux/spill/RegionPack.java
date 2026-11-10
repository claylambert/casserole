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

// @aa-sdd PK-0753 PK-0789 docs/specs/java/org/roux/roux.spec.md
/**
 * The RegionPack is the random-access {@code pages.bin} file for one region.
 * Slot {@code P} starts at {@code P} times the page size from the settings.
 * {@link #publishPage} writes the used bytes, pads to the alignment, and forces the channel.
 * The page stays mapped.
 * One channel stays open per region.
 *
 * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
 * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
 * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
 * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
 * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
 * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
 * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
 * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
 * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
 * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
 * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
 * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
 * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
 * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
 * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
 * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
 * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
 * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
