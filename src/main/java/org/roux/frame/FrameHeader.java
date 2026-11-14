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

package org.roux.frame;

import java.lang.foreign.MemorySegment;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32C;

import org.roux.id.RouxId;
import org.roux.layout.RouxLayout;

// @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
/**
 * The FrameHeader type reads and writes the 56-byte header in front of a message payload.
 * The payload is the text encoding that follows the header.
 * The header stores the padded length, the type, the flags, and the role.
 * The header stores the message id, the time, the token count, and the payload length.
 * The header stores a CRC32C checksum of the payload.
 * The header stores the two identity longs.
 * Those longs start on an 8-byte boundary.
 * Pad bytes after the payload bring the frame to a multiple of {@code 8}.
 *
 * The type refuses construction.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#FrameHeader roux-v1.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-818 docs/specs/common/roux.spec.md
// @aa-sdd PK-638 docs/specs/common/formal-plan.spec.md
public final class FrameHeader {

    public static final int SIZE = 56;
    public static final int OFF_LENGTH = 0;
    public static final int OFF_TYPE = 4;
    public static final int OFF_FLAGS = 5;
    public static final int OFF_ROLE = 6;
    public static final int OFF_PAD = 7;
    public static final int OFF_MESSAGE_ID = 8;
    public static final int OFF_TS = 16;
    public static final int OFF_TOKENS = 24;
    public static final int OFF_PAYLOAD_LEN = 28;
    public static final int OFF_CRC = 32;
    /**
     * The bytes at 36 through 39 are unused.
     * The next long starts at byte 40 so it sits on an 8-byte boundary.
     */
    public static final int OFF_ID_HI = 40;
    public static final int OFF_ID_LO = 48;

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private FrameHeader() {
    }

    /**
     * The method rounds a length up to a multiple of {@code 8}.
     *
     * @param n The length is the unpadded size.
     * @return The length is the next multiple of {@code 8}.
     */
    public static int align8(int n) {
        return (n + 7) & ~7;
    }

    /**
     * The method returns the padded size of one frame.
     * The size includes the 56-byte header and the payload.
     *
     * @param payloadLen The payload length is the number of payload bytes.
     * @return The length is the frame size including the pad.
     */
    public static int totalLength(int payloadLen) {
        return align8(SIZE + payloadLen);
    }

    /**
     * The method writes one frame at the given offset.
     * The flags byte is set to {@code 1}.
     * The pad byte in the header is set to {@code 0}.
     * The checksum is the CRC32C value of the payload.
     * A null identity writes two zero longs.
     * Bytes after the payload are set to {@code 0} until the padded length.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#FrameHeader roux-v1.md
     *
     * @param segment The segment is the page.
     * @param offset The offset is the first byte of the frame.
     * @param total The total is the padded frame length.
     * @param type The type is the frame type byte.
     * @param role The role is the role byte.
     * @param messageId The message id is the number that locates the frame.
     * @param ts The time is the epoch milliseconds stored with the message.
     * @param tokens The token count is the number stored with the message.
     * @param rouxId The identity is the {@link RouxId}, or null.
     * @param payload The payload is the encoded message bytes.
     */
    public static void write(MemorySegment segment, long offset, int total, byte type, byte role, long messageId,
            long ts, int tokens, RouxId rouxId, byte[] payload) {
        CRC32C crc = new CRC32C();
        crc.update(payload);
        segment.set(RouxLayout.INT_LE, offset + OFF_LENGTH, total);
        segment.set(RouxLayout.BYTE, offset + OFF_TYPE, type);
        segment.set(RouxLayout.BYTE, offset + OFF_FLAGS, (byte) 1);
        segment.set(RouxLayout.BYTE, offset + OFF_ROLE, role);
        segment.set(RouxLayout.BYTE, offset + OFF_PAD, (byte) 0);
        segment.set(RouxLayout.LONG_LE, offset + OFF_MESSAGE_ID, messageId);
        segment.set(RouxLayout.LONG_LE, offset + OFF_TS, ts);
        segment.set(RouxLayout.INT_LE, offset + OFF_TOKENS, tokens);
        segment.set(RouxLayout.INT_LE, offset + OFF_PAYLOAD_LEN, payload.length);
        segment.set(RouxLayout.INT_LE, offset + OFF_CRC, (int) crc.getValue());
        if (rouxId != null) {
            rouxId.write(segment, offset + OFF_ID_HI);
        } else {
            segment.set(RouxLayout.LONG_LE, offset + OFF_ID_HI, 0L);
            segment.set(RouxLayout.LONG_LE, offset + OFF_ID_LO, 0L);
        }
        if (payload.length > 0) {
            MemorySegment.copy(payload, 0, segment, RouxLayout.BYTE, offset + SIZE, payload.length);
        }
        int pad = total - SIZE - payload.length;
        for (int i = 0; i < pad; i++) {
            segment.set(RouxLayout.BYTE, offset + SIZE + payload.length + i, (byte) 0);
        }
    }

    /**
     * The method returns the padded frame length.
     *
     * @param segment The segment is the page.
     * @param offset The offset is the first byte of the frame.
     * @return The length is the padded size stored in the header.
     */
    public static int length(MemorySegment segment, long offset) {
        return segment.get(RouxLayout.INT_LE, offset + OFF_LENGTH);
    }

    /**
     * The method returns the type byte.
     *
     * @param segment The segment is the page.
     * @param offset The offset is the first byte of the frame.
     * @return The type is the byte stored in the header.
     */
    public static byte type(MemorySegment segment, long offset) {
        return segment.get(RouxLayout.BYTE, offset + OFF_TYPE);
    }

    /**
     * The method returns the role byte.
     *
     * @param segment The segment is the page.
     * @param offset The offset is the first byte of the frame.
     * @return The role is the byte stored in the header.
     */
    public static byte role(MemorySegment segment, long offset) {
        return segment.get(RouxLayout.BYTE, offset + OFF_ROLE);
    }

    /**
     * The method returns the message id.
     *
     * @param segment The segment is the page.
     * @param offset The offset is the first byte of the frame.
     * @return The id is the number stored in the header.
     */
    public static long messageId(MemorySegment segment, long offset) {
        return segment.get(RouxLayout.LONG_LE, offset + OFF_MESSAGE_ID);
    }

    /**
     * The method returns the stored time.
     *
     * @param segment The segment is the page.
     * @param offset The offset is the first byte of the frame.
     * @return The time is the epoch milliseconds stored in the header.
     */
    public static long timestamp(MemorySegment segment, long offset) {
        return segment.get(RouxLayout.LONG_LE, offset + OFF_TS);
    }

    /**
     * The method returns the stored token count.
     *
     * @param segment The segment is the page.
     * @param offset The offset is the first byte of the frame.
     * @return The count is the number stored in the header.
     */
    public static int tokens(MemorySegment segment, long offset) {
        return segment.get(RouxLayout.INT_LE, offset + OFF_TOKENS);
    }

    /**
     * The method returns the payload length.
     *
     * @param segment The segment is the page.
     * @param offset The offset is the first byte of the frame.
     * @return The length is the payload size stored in the header.
     */
    public static int payloadLen(MemorySegment segment, long offset) {
        return segment.get(RouxLayout.INT_LE, offset + OFF_PAYLOAD_LEN);
    }

    /**
     * The method returns the stored checksum.
     *
     * @param segment The segment is the page.
     * @param offset The offset is the first byte of the frame.
     * @return The value is the CRC32C checksum stored in the header.
     */
    public static int crc(MemorySegment segment, long offset) {
        return segment.get(RouxLayout.INT_LE, offset + OFF_CRC);
    }

    /**
     * The method checks a payload against an expected checksum.
     * The checksum is the CRC32C value of the payload bytes.
     *
     * @param payload The payload is the bytes to check.
     * @param expected The expected value is the checksum stored in the header.
     * @return The flag is true when the checksum matches.
     */
    public static boolean crcMatches(byte[] payload, int expected) {
        CRC32C crc = new CRC32C();
        crc.update(payload);
        return (int) crc.getValue() == expected;
    }

    /**
     * The method copies the payload out of a heap frame.
     * A negative payload length returns an empty array.
     * A payload that runs past the buffer returns an empty array.
     *
     * @param buffer The buffer is the frame bytes on the heap.
     * @return The array is the payload, or an empty array.
     */
    public static byte[] payloadFromHeap(byte[] buffer) {
        ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
        int payloadLen = bb.getInt(OFF_PAYLOAD_LEN);
        if (payloadLen < 0 || SIZE + payloadLen > buffer.length) {
            return new byte[0];
        }
        byte[] payload = new byte[payloadLen];
        System.arraycopy(buffer, SIZE, payload, 0, payloadLen);
        return payload;
    }
}
/*****************************************************************************************
 * End of Class: FrameHeader.java 082526
 ****************************************************************************************/
