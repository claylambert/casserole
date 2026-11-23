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
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.roux.RouxProperties;
import org.roux.page.Page;
import org.roux.page.PageHeader;
import org.roux.session.RegionManifest;
import org.springframework.stereotype.Component;

// @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md FINESSE:f-170
/**
 * The RegionPack is the random-access {@code pages.bin} file for one region.
 * Slot {@code P} starts at {@code P} times the page size from the settings.
 * {@link #publishPage} writes the used bytes, pads to the alignment, and forces the channel.
 * A page publish waits until the force returns for the one copy on this disk.
 * The page stays mapped.
 * One channel stays open per region.
 * A bound segment names the live pack folder, and the lock file stays on the username directory.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#RegionPack roux-v1.md
 *
 * @author clayl (2026-08-25)
 */
@Component
// @aa-sdd PK-688 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-800 docs/specs/common/roux.spec.md
// @aa-sdd PK-803 docs/specs/common/roux.spec.md
// @aa-sdd PK-527 docs/specs/java/org/roux/spill/spill.spec.md
// @aa-sdd PK-528 docs/specs/java/org/roux/spill/spill.spec.md
// @aa-sdd PK-529 docs/specs/java/org/roux/spill/spill.spec.md
// @aa-sdd PK-1383 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-638 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-530 docs/specs/java/org/roux/spill/spill.spec.md
// @aa-sdd PK-531 docs/specs/java/org/roux/spill/spill.spec.md
public class RegionPack {

    private final RouxProperties properties;
    private final Map<String, FileChannel> channels;
    private final Map<String, FileChannel> lockChannels;
    private final Map<String, FileLock> locks;
    private final Map<String, String> folders;
    private final Set<String> sparseMarked;

    private static final int FSCTL_SET_SPARSE = 0x000900C4;
    private static final int FSCTL_SET_ZERO_DATA = 0x000980C8;
    private static final int GENERIC_READ_WRITE = 0xC0000000;
    private static final int SHARE_ALL = 7;
    private static final int OPEN_EXISTING = 3;
    private static final int FILE_ATTRIBUTE_NORMAL = 128;

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
        this.folders = new HashMap<String, String>();
        this.sparseMarked = new HashSet<String>();
    }

    /**
     * The method stores the live pack segment for one region.
     * A null segment stores the region id.
     * An empty segment stores the region id.
     * The lock file is not moved.
     *
     * @param regionId The region id is the username.
     * @param folderSegment The segment is the live folder name, or empty for the username folder.
     */
    // @aa-sdd PK-1465 docs/specs/java/org/roux/roux.spec.md
    public synchronized void bindFolder(String regionId, String folderSegment) {
        if (regionId == null || regionId.length() == 0) {
            return;
        }
        if (folderSegment == null || folderSegment.length() == 0) {
            folders.put(regionId, regionId);
            return;
        }
        folders.put(regionId, folderSegment);
    }

    /**
     * The method returns the live pack directory.
     * An unbound region returns the username directory.
     * A bound region returns the spill root plus the stored segment.
     * The lock directory is not this path.
     *
     * @param regionId The region id is the username.
     * @return The path is the live pack directory.
     */
    // @aa-sdd PK-1465 docs/specs/java/org/roux/roux.spec.md
    public synchronized Path packDir(String regionId) {
        String segment = folders.get(regionId);
        if (segment == null || segment.length() == 0) {
            return regionDir(regionId);
        }
        Path home = regionDir(regionId);
        Path root = home.getParent();
        if (root == null) {
            return home;
        }
        return root.resolve(segment);
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
        return packDir(regionId).resolve("pages.bin");
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
        return Files.isRegularFile(RegionManifest.file(packDir(regionId)));
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
            Files.deleteIfExists(RegionManifest.file(packDir(regionId)));
            Files.deleteIfExists(packDir(regionId).resolve("manifest.bin.tmp"));
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
     * The count is read before any byte is written.
     * The channel is still forced before the method returns.
     * The page stays mapped.
     * A write that returns a negative count throws {@link IOException} with {@code pack write EOF}.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RegionPack roux-v1.md spillover-semantics.md
     *
     * @param regionId The region id is the folder name.
     * @param page The page is the page with a live segment.
     * @throws IOException The page has no mapped bytes, the copy count is below 1, or the write failed.
     */
    // @aa-sdd PK-1467 docs/specs/java/org/roux/roux.spec.md
    public void publishPage(String regionId, Page page) throws IOException {
        if (page.getSegment() == null) {
            throw new IOException("page has no RAM segment");
        }
        try {
            properties.ackCopies();
        } catch (IllegalArgumentException ex) {
            throw new IOException(ex.getMessage());
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
        Path dir = packDir(regionId);
        Files.createDirectories(dir);
        Path file = packFile(regionId);
        FileChannel channel = FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.READ,
                StandardOpenOption.WRITE);
        channels.put(regionId, channel);
        return channel;
    }

    /**
     * The method releases one slot.
     * A tail slot is truncated back to the slot offset.
     * Any other slot is written as zeros and then punched.
     * The logical length of a hole stays.
     * A punch that fails leaves the zeros in place.
     * The channel is forced before the method returns.
     *
     * @param regionId The region id is the folder name.
     * @param pageId The page id is the slot to release.
     * @param tail The flag is true when the slot end is the file length.
     * @throws IOException The truncate, the zero write, or the force failed.
     */
    // @aa-sdd PK-1469 docs/specs/java/org/roux/roux.spec.md
    public synchronized void releaseSlot(String regionId, int pageId, boolean tail) throws IOException {
        FileChannel channel = open(regionId);
        long off = slotOffset(pageId);
        if (tail) {
            channel.truncate(off);
        } else {
            int pageBytes = properties.getPageBytes();
            byte[] zeros = new byte[pageBytes];
            ByteBuffer buf = ByteBuffer.wrap(zeros);
            while (buf.hasRemaining()) {
                int n = channel.write(buf, off + buf.position());
                if (n < 0) {
                    throw new IOException("pack write EOF");
                }
            }
            punch(regionId, off, off + pageBytes);
        }
        channel.force(true);
    }

    /**
     * The method marks the pack sparse and punches one range.
     * The first hole in this process marks the file sparse.
     * A failed call leaves the zeros that were already written.
     *
     * @param regionId The region id is the folder name.
     * @param offset The offset is the first byte of the hole.
     * @param beyond The beyond value is the first byte after the hole.
     */
    private void punch(String regionId, long offset, long beyond) {
        String path = packFile(regionId).toAbsolutePath().toString();
        Linker linker = Linker.nativeLinker();
        Arena arena = Arena.ofConfined();
        try {
            SymbolLookup kernel = SymbolLookup.libraryLookup("kernel32", arena);
            MemorySegment createSym = kernel.find("CreateFileW").orElse(null);
            MemorySegment controlSym = kernel.find("DeviceIoControl").orElse(null);
            MemorySegment closeSym = kernel.find("CloseHandle").orElse(null);
            if (createSym == null || controlSym == null || closeSym == null) {
                return;
            }
            MethodHandle create = linker.downcallHandle(createSym, FunctionDescriptor.of(
                    ValueLayout.ADDRESS,
                    ValueLayout.ADDRESS,
                    ValueLayout.JAVA_INT,
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS,
                    ValueLayout.JAVA_INT,
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS));
            MethodHandle control = linker.downcallHandle(controlSym, FunctionDescriptor.of(
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS,
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS,
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS,
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS,
                    ValueLayout.ADDRESS));
            MethodHandle close = linker.downcallHandle(closeSym, FunctionDescriptor.of(
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS));
            MemorySegment name = arena.allocateFrom(path, StandardCharsets.UTF_16LE);
            MemorySegment handle = (MemorySegment) create.invoke(name, Integer.valueOf(GENERIC_READ_WRITE),
                    Integer.valueOf(SHARE_ALL), MemorySegment.NULL, Integer.valueOf(OPEN_EXISTING),
                    Integer.valueOf(FILE_ATTRIBUTE_NORMAL), MemorySegment.NULL);
            if (handle == null || handle.address() == -1L) {
                return;
            }
            try {
                MemorySegment returned = arena.allocate(ValueLayout.JAVA_INT);
                if (!sparseMarked.contains(regionId)) {
                    int marked = (Integer) control.invoke(handle, Integer.valueOf(FSCTL_SET_SPARSE), MemorySegment.NULL,
                            Integer.valueOf(0), MemorySegment.NULL, Integer.valueOf(0), returned, MemorySegment.NULL);
                    if (marked != 0) {
                        sparseMarked.add(regionId);
                    }
                }
                MemorySegment range = arena.allocate(16, 8);
                range.set(ValueLayout.JAVA_LONG, 0, offset);
                range.set(ValueLayout.JAVA_LONG, 8, beyond);
                control.invoke(handle, Integer.valueOf(FSCTL_SET_ZERO_DATA), range, Integer.valueOf(16),
                        MemorySegment.NULL, Integer.valueOf(0), returned, MemorySegment.NULL);
            } finally {
                try {
                    close.invoke(handle);
                } catch (Throwable ignored) {
                    // the zeros stay
                }
            }
        } catch (Throwable ignored) {
            // the zeros stay. The sweep still counts the release.
        } finally {
            arena.close();
        }
    }
}
/*****************************************************************************************
 * End of Class: RegionPack.java 082626
 ****************************************************************************************/
