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
 * // @aa-sdd L40 aa-sdd-source-prose.md#RegionManifest roux-v1.md
 *
 * @author clayl (2026-08-26)
 */
// @aa-sdd PK-797 docs/specs/common/roux.spec.md
// @aa-sdd PK-523 docs/specs/java/org/roux/session/session.spec.md
// @aa-sdd PK-524 docs/specs/java/org/roux/session/session.spec.md
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
