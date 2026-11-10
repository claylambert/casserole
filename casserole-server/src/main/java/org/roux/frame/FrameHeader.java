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

// @aa-sdd PK-0748 PK-0784 docs/specs/java/org/roux/roux.spec.md
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
