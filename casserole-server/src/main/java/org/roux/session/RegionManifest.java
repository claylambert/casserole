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

package org.roux.session;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.zip.CRC32C;

import org.roux.layout.RouxLayout;

/**
 * The RegionManifest is the 112-byte file named {@code manifest.bin}.
 * The file sits beside {@code pages.bin}.
 * The record stores the page size, the page count, and the hot page id.
 * The record stores the hot used bytes, the next message id, and the last write time.
 * The record stores the creation time, the page-header version, the region id, and the owner.
 * The magic bytes are {@code R}, {@code X}, {@code M}, and {@code 1}.
 * The meta version is {@code 1}.
 * The checksum covers bytes {@code 0} through {@code 103}.
 * A close that keeps files writes this record and leaves the directory.
 *
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 *
 * @author clayl (2026-08-26)
 */
public final class RegionManifest {

    public static final int SIZE = 112;
    public static final int CRC_OFF = 104;
    public static final short META_VERSION = 1;
    public static final String FILE_NAME = "manifest.bin";

    private int pageBytes;
    private int pageCount;
    private int hotPageId;
    private int hotUsedBytes;
    private long nextSeq;
    private long lastWriteEpochMs;
    private long createdEpochMs;
    private int formatVersion;
    private UUID regionUuid;
    private String owner;

    /**
     * The constructor starts an empty manifest.
     * The region id is a zero UUID.
     * The owner is an empty string.
     * The page-header version starts at {@code 2}.
     */
    public RegionManifest() {
        this.regionUuid = new UUID(0L, 0L);
        this.owner = "";
        this.formatVersion = 2;
    }

    /**
     * The method returns the stored page size.
     *
     * @return The size is the page capacity in bytes.
     */
    public int getPageBytes() {
        return pageBytes;
    }

    /**
     * The method stores the page size.
     *
     * @param pageBytes The size is the page capacity in bytes.
     */
    public void setPageBytes(int pageBytes) {
        this.pageBytes = pageBytes;
    }

    /**
     * The method returns the stored page count.
     *
     * @return The count is the number of pages recorded for the region.
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * The method stores the page count.
     *
     * @param pageCount The count is the number of pages to record.
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * The method returns the stored hot page id.
     *
     * @return The id is the hot page recorded in the file.
     */
    public int getHotPageId() {
        return hotPageId;
    }

    /**
     * The method stores the hot page id.
     *
     * @param hotPageId The id is the hot page to record.
     */
    public void setHotPageId(int hotPageId) {
        this.hotPageId = hotPageId;
    }

    /**
     * The method returns the stored hot used-byte count.
     *
     * @return The count is the hot used bytes recorded in the file.
     */
    public int getHotUsedBytes() {
        return hotUsedBytes;
    }

    /**
     * The method stores the hot used-byte count.
     *
     * @param hotUsedBytes The count is the hot used bytes to record.
     */
    public void setHotUsedBytes(int hotUsedBytes) {
        this.hotUsedBytes = hotUsedBytes;
    }

    /**
     * The method returns the stored next message id.
     *
     * @return The id is the next message id recorded in the file.
     */
    public long getNextSeq() {
        return nextSeq;
    }

    /**
     * The method stores the next message id.
     *
     * @param nextSeq The id is the next message id to record.
     */
    public void setNextSeq(long nextSeq) {
        this.nextSeq = nextSeq;
    }

    /**
     * The method returns the stored last-write time.
     *
     * @return The time is the epoch milliseconds of the last write.
     */
    public long getLastWriteEpochMs() {
        return lastWriteEpochMs;
    }

    /**
     * The method stores the last-write time.
     *
     * @param lastWriteEpochMs The time is the epoch milliseconds of the last write.
     */
    public void setLastWriteEpochMs(long lastWriteEpochMs) {
        this.lastWriteEpochMs = lastWriteEpochMs;
    }

    /**
     * The method returns the stored creation time.
     *
     * @return The time is the epoch milliseconds when the region was created.
     */
    public long getCreatedEpochMs() {
        return createdEpochMs;
    }

    /**
     * The method stores the creation time.
     *
     * @param createdEpochMs The time is the epoch milliseconds when the region was created.
     */
    public void setCreatedEpochMs(long createdEpochMs) {
        this.createdEpochMs = createdEpochMs;
    }

    /**
     * The method returns the stored page-header version.
     *
     * @return The version is the page-header version recorded in the file.
     */
    public int getFormatVersion() {
        return formatVersion;
    }

    /**
     * The method stores the page-header version.
     *
     * @param formatVersion The version is the page-header version to record.
     */
    public void setFormatVersion(int formatVersion) {
        this.formatVersion = formatVersion;
    }

    /**
     * The method returns the stored region identifier.
     *
     * @return The id is the region identifier.
     */
    public UUID getRegionUuid() {
        return regionUuid;
    }

    /**
     * The method stores the region identifier.
     * A null id is stored as a zero UUID.
     *
     * @param regionUuid The id is the region identifier, or null.
     */
    public void setRegionUuid(UUID regionUuid) {
        this.regionUuid = regionUuid == null ? new UUID(0L, 0L) : regionUuid;
    }

    /**
     * The method returns the stored owner.
     *
     * @return The owner is the user name recorded in the file.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * The method stores the owner.
     * A null owner is stored as an empty string.
     *
     * @param owner The owner is the user name to record.
     */
    public void setOwner(String owner) {
        this.owner = owner == null ? "" : owner;
    }

    /**
     * The method returns the path of {@code manifest.bin} in a directory.
     *
     * @param dir The directory is the region directory.
     * @return The path is {@code manifest.bin} inside that directory.
     */
    public static Path file(Path dir) {
        return dir.resolve(FILE_NAME);
    }

    /**
     * The method reads {@code manifest.bin}.
     * A missing file returns null.
     * A file shorter than {@code 112} bytes throws {@link IOException} with {@code manifest too short}.
     * A bad magic throws {@link IOException} with {@code manifest magic}.
     * A meta version other than {@code 1} throws {@link IOException}.
     * A checksum mismatch throws {@link IOException} with {@code manifest crc}.
     * The owner is the UTF-8 text up to the first zero byte, in a 32-byte field.
     *
     * @param dir The directory is the region directory.
     * @return The manifest is the parsed record, or null when the file is missing.
     * @throws IOException The file is present and cannot be parsed.
     */
    public static RegionManifest read(Path dir) throws IOException {
        Path path = file(dir);
        if (!Files.isRegularFile(path)) {
            return null;
        }
        byte[] raw = Files.readAllBytes(path);
        if (raw.length < SIZE) {
            throw new IOException("manifest too short");
        }
        Arena arena = Arena.ofConfined();
        try {
            MemorySegment seg = arena.allocate(SIZE, 8);
            MemorySegment.copy(raw, 0, seg, ValueLayout.JAVA_BYTE, 0, SIZE);
            if (seg.get(RouxLayout.BYTE, 0) != (byte) 'R'
                    || seg.get(RouxLayout.BYTE, 1) != (byte) 'X'
                    || seg.get(RouxLayout.BYTE, 2) != (byte) 'M'
                    || seg.get(RouxLayout.BYTE, 3) != (byte) '1') {
                throw new IOException("manifest magic");
            }
            short meta = seg.get(RouxLayout.SHORT_LE, 4);
            if (meta != META_VERSION) {
                throw new IOException("manifest meta version " + meta);
            }
            int storedCrc = seg.get(RouxLayout.INT_LE, CRC_OFF);
            if (crcOf(seg) != storedCrc) {
                throw new IOException("manifest crc");
            }
            RegionManifest m = new RegionManifest();
            m.pageBytes = seg.get(RouxLayout.INT_LE, 8);
            m.pageCount = seg.get(RouxLayout.INT_LE, 12);
            m.hotPageId = seg.get(RouxLayout.INT_LE, 16);
            m.hotUsedBytes = seg.get(RouxLayout.INT_LE, 20);
            m.nextSeq = seg.get(RouxLayout.LONG_LE, 24);
            m.lastWriteEpochMs = seg.get(RouxLayout.LONG_LE, 32);
            m.createdEpochMs = seg.get(RouxLayout.LONG_LE, 40);
            m.formatVersion = seg.get(RouxLayout.INT_LE, 48);
            long hi = seg.get(RouxLayout.LONG_LE, 56);
            long lo = seg.get(RouxLayout.LONG_LE, 64);
            m.regionUuid = new UUID(hi, lo);
            byte[] ownerBytes = new byte[32];
            MemorySegment.copy(seg, ValueLayout.JAVA_BYTE, 72, ownerBytes, 0, 32);
            m.owner = utf8z(ownerBytes);
            return m;
        } finally {
            arena.close();
        }
    }

    /**
     * The method writes {@code manifest.bin}.
     * The directory is created when it is absent.
     * The bytes go to {@code manifest.bin.tmp}, the channel is forced, and the temp file replaces the destination.
     * A failed atomic move deletes the destination and moves the temp file again.
     * An owner longer than {@code 32} bytes is cut to {@code 32} bytes.
     * A null region id is written as a zero UUID.
     * A write that returns a negative count throws {@link IOException} with {@code manifest write EOF}.
     *
     * @param dir The directory is the region directory.
     * @param m The manifest is the record to write.
     * @throws IOException The write or the move failed.
     */
    public static void write(Path dir, RegionManifest m) throws IOException {
        Files.createDirectories(dir);
        Path tmp = dir.resolve("manifest.bin.tmp");
        Path dest = file(dir);
        Arena arena = Arena.ofConfined();
        try {
            MemorySegment seg = arena.allocate(SIZE, 8);
            seg.fill((byte) 0);
            seg.set(RouxLayout.BYTE, 0, (byte) 'R');
            seg.set(RouxLayout.BYTE, 1, (byte) 'X');
            seg.set(RouxLayout.BYTE, 2, (byte) 'M');
            seg.set(RouxLayout.BYTE, 3, (byte) '1');
            seg.set(RouxLayout.SHORT_LE, 4, META_VERSION);
            seg.set(RouxLayout.SHORT_LE, 6, (short) 0);
            seg.set(RouxLayout.INT_LE, 8, m.pageBytes);
            seg.set(RouxLayout.INT_LE, 12, m.pageCount);
            seg.set(RouxLayout.INT_LE, 16, m.hotPageId);
            seg.set(RouxLayout.INT_LE, 20, m.hotUsedBytes);
            seg.set(RouxLayout.LONG_LE, 24, m.nextSeq);
            seg.set(RouxLayout.LONG_LE, 32, m.lastWriteEpochMs);
            seg.set(RouxLayout.LONG_LE, 40, m.createdEpochMs);
            seg.set(RouxLayout.INT_LE, 48, m.formatVersion);
            UUID uuid = m.regionUuid == null ? new UUID(0L, 0L) : m.regionUuid;
            seg.set(RouxLayout.LONG_LE, 56, uuid.getMostSignificantBits());
            seg.set(RouxLayout.LONG_LE, 64, uuid.getLeastSignificantBits());
            byte[] ownerBytes = new byte[32];
            byte[] src = (m.owner == null ? "" : m.owner).getBytes(StandardCharsets.UTF_8);
            int n = src.length;
            if (n > 32) {
                n = 32;
            }
            System.arraycopy(src, 0, ownerBytes, 0, n);
            MemorySegment.copy(ownerBytes, 0, seg, ValueLayout.JAVA_BYTE, 72, 32);
            seg.set(RouxLayout.INT_LE, CRC_OFF, crcOf(seg));
            byte[] raw = new byte[SIZE];
            MemorySegment.copy(seg, ValueLayout.JAVA_BYTE, 0, raw, 0, SIZE);
            FileChannel channel = FileChannel.open(tmp, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            try {
                ByteBuffer buf = ByteBuffer.wrap(raw);
                while (buf.hasRemaining()) {
                    int w = channel.write(buf);
                    if (w < 0) {
                        throw new IOException("manifest write EOF");
                    }
                }
                channel.force(true);
            } finally {
                channel.close();
            }
            try {
                Files.move(tmp, dest, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException ex) {
                Files.deleteIfExists(dest);
                Files.move(tmp, dest, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            arena.close();
            Files.deleteIfExists(tmp);
        }
    }

    /**
     * The method computes the CRC32C checksum of the first {@code 104} bytes.
     *
     * @param seg The segment is the 112-byte record.
     * @return The value is the checksum of bytes {@code 0} through {@code 103}.
     */
    private static int crcOf(MemorySegment seg) {
        byte[] prefix = new byte[CRC_OFF];
        MemorySegment.copy(seg, ValueLayout.JAVA_BYTE, 0, prefix, 0, CRC_OFF);
        CRC32C crc = new CRC32C();
        crc.update(prefix);
        return (int) crc.getValue();
    }

    /**
     * The method reads UTF-8 text up to the first zero byte.
     *
     * @param raw The bytes are the padded owner field.
     * @return The text is the owner name.
     */
    private static String utf8z(byte[] raw) {
        int end = raw.length;
        for (int i = 0; i < raw.length; i++) {
            if (raw[i] == 0) {
                end = i;
                break;
            }
        }
        return new String(raw, 0, end, StandardCharsets.UTF_8);
    }
}
/*****************************************************************************************
 * End of Class: RegionManifest.java 082626
 ****************************************************************************************/
