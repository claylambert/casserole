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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.zip.CRC32C;

/**
 * The ClipRecord is the 64-byte file named {@code clip.bin}.
 * The file sits beside {@code manifest.bin}.
 * The record stores the head origin for the next prompt.
 * An absent file means the next prompt uses the present walk.
 * A checksum miss means the same walk.
 * The magic bytes are {@code C}, {@code L}, {@code I}, and {@code P}.
 * The version is {@code 1}.
 * The checksum covers bytes {@code 0} through {@code 39}.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#ClipRecord roux-plan-clip.001.md
 *
 * @author clayl (2026-11-23)
 */
// @aa-sdd PK-1470 docs/specs/java/org/roux/roux.spec.md
public final class ClipRecord {

    public static final int SIZE = 64;
    public static final int CRC_OFF = 40;
    public static final short VERSION = 1;
    public static final String FILE_NAME = "clip.bin";
    public static final int LIVE = 1;
    public static final int REWIND = 2;

    private int flags;
    private long origin;
    private int capacity;
    private int estimate;
    private int remaining;
    private long epochMs;

    /**
     * The constructor starts an empty record.
     * The flags are clear.
     * The origin is {@code 0}.
     */
    public ClipRecord() {
        this.flags = 0;
        this.origin = 0L;
        this.capacity = 0;
        this.estimate = 0;
        this.remaining = 0;
        this.epochMs = 0L;
    }

    /**
     * The method builds a live auto record.
     * Bit 0 is set.
     * Bit 1 is clear.
     *
     * @param origin The origin is the head sequence, or {@code 0} when there is no floor.
     * @param capacity The capacity is the token window, or {@code 0}.
     * @param estimate The estimate is the token count of the head.
     * @param remaining The remaining count is the unused capacity.
     * @param epochMs The epoch is the roll time in milliseconds.
     * @return The record is the live auto clip.
     */
    public static ClipRecord auto(long origin, int capacity, int estimate, int remaining, long epochMs) {
        ClipRecord record = new ClipRecord();
        record.flags = LIVE;
        record.origin = origin;
        record.capacity = capacity;
        record.estimate = estimate;
        record.remaining = remaining;
        record.epochMs = epochMs;
        return record;
    }

    /**
     * The method builds a live rewind record.
     * Bit 0 is set.
     * Bit 1 is set.
     *
     * @param origin The origin is the rewind sequence.
     * @param capacity The capacity is the token window, or {@code 0}.
     * @param estimate The estimate is the token count of the head.
     * @param remaining The remaining count is the unused capacity.
     * @param epochMs The epoch is the rewind time in milliseconds.
     * @return The record is the live rewind clip.
     */
    public static ClipRecord rewind(long origin, int capacity, int estimate, int remaining, long epochMs) {
        ClipRecord record = auto(origin, capacity, estimate, remaining, epochMs);
        record.flags = LIVE | REWIND;
        return record;
    }

    /**
     * The method reports whether the record is live.
     *
     * @return The flag is true when bit 0 is set.
     */
    public boolean isLive() {
        return (flags & LIVE) != 0;
    }

    /**
     * The method reports whether the origin is a rewind.
     *
     * @return The flag is true when bit 1 is set.
     */
    public boolean isRewind() {
        return (flags & REWIND) != 0;
    }

    /**
     * The method returns the stored origin.
     *
     * @return The origin is the head sequence, or {@code 0}.
     */
    public long getOrigin() {
        return origin;
    }

    /**
     * The method returns the stored capacity.
     *
     * @return The capacity is the token window.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * The method returns the stored estimate.
     *
     * @return The estimate is the token count of the head.
     */
    public int getEstimate() {
        return estimate;
    }

    /**
     * The method returns the stored remaining count.
     *
     * @return The remaining count is the unused capacity.
     */
    public int getRemaining() {
        return remaining;
    }

    /**
     * The method reads {@code clip.bin}.
     * An absent file returns null.
     * A short file, a wrong magic, a wrong version, or a checksum miss returns null.
     *
     * @param dir The directory is the region directory.
     * @return The record is the clip, or null when the file is absent or unread.
     */
    public static ClipRecord read(Path dir) {
        if (dir == null) {
            return null;
        }
        Path file = dir.resolve(FILE_NAME);
        if (!Files.isRegularFile(file)) {
            return null;
        }
        byte[] raw;
        try {
            raw = Files.readAllBytes(file);
        } catch (IOException ex) {
            return null;
        }
        if (raw.length != SIZE) {
            return null;
        }
        if (raw[0] != (byte) 'C' || raw[1] != (byte) 'L' || raw[2] != (byte) 'I' || raw[3] != (byte) 'P') {
            return null;
        }
        ByteBuffer buf = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN);
        int version = buf.getShort(4) & 0xFFFF;
        int size = buf.getShort(6) & 0xFFFF;
        if (version != VERSION || size != SIZE) {
            return null;
        }
        if (buf.getInt(CRC_OFF) != crcOf(raw)) {
            return null;
        }
        ClipRecord record = new ClipRecord();
        record.flags = buf.getInt(8);
        record.origin = buf.getLong(12);
        record.capacity = buf.getInt(20);
        record.estimate = buf.getInt(24);
        record.remaining = buf.getInt(28);
        record.epochMs = buf.getLong(32);
        return record;
    }

    /**
     * The method writes {@code clip.bin}.
     * The directory is created when it is absent.
     * The bytes go to {@code clip.bin.tmp}, the channel is forced, and the temp file replaces the destination.
     * A failed atomic move deletes the destination and moves the temp file again.
     * The origin long sits at offset {@code 12} and is stored without an eight-byte alignment.
     *
     * @param dir The directory is the region directory.
     * @param record The record is the clip to write.
     * @throws IOException The write or the move failed.
     */
    public static void write(Path dir, ClipRecord record) throws IOException {
        Files.createDirectories(dir);
        Path tmp = dir.resolve("clip.bin.tmp");
        Path dest = dir.resolve(FILE_NAME);
        byte[] raw = new byte[SIZE];
        ByteBuffer buf = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN);
        buf.put(0, (byte) 'C');
        buf.put(1, (byte) 'L');
        buf.put(2, (byte) 'I');
        buf.put(3, (byte) 'P');
        buf.putShort(4, VERSION);
        buf.putShort(6, (short) SIZE);
        buf.putInt(8, record.flags);
        buf.putLong(12, record.origin);
        buf.putInt(20, record.capacity);
        buf.putInt(24, record.estimate);
        buf.putInt(28, record.remaining);
        buf.putLong(32, record.epochMs);
        buf.putInt(CRC_OFF, crcOf(raw));
        try {
            FileChannel channel = FileChannel.open(tmp, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            try {
                ByteBuffer out = ByteBuffer.wrap(raw);
                while (out.hasRemaining()) {
                    int wrote = channel.write(out);
                    if (wrote < 0) {
                        throw new IOException("clip write EOF");
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
            Files.deleteIfExists(tmp);
        }
    }

    /**
     * The method computes the CRC32C checksum of the first {@code 40} bytes.
     *
     * @param raw The bytes are the 64-byte record.
     * @return The value is the checksum of bytes {@code 0} through {@code 39}.
     */
    private static int crcOf(byte[] raw) {
        CRC32C crc = new CRC32C();
        crc.update(raw, 0, CRC_OFF);
        return (int) crc.getValue();
    }
}
/*****************************************************************************************
 * End of Class: ClipRecord.java 112326
 ****************************************************************************************/
