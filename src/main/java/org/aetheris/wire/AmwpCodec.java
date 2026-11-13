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

package org.aetheris.wire;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CRC32C;

/**
 * The {@code AmwpCodec} class encodes and decodes AMWP frames.
 * AMWP is the framed protocol on the connection.
 * A multi-byte integer is little-endian.
 * The class does not decode the framed stream as UTF-8.
 * // @aa-sdd G-07 G-18 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd GK-1198 aetheris-grok-api.draft.001.md
// @aa-sdd PK-738 docs/specs/common/stages.spec.md
// @aa-sdd PK-151 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-153 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd GK-1644 aetheris-stage-2.draft.001.md
// @aa-sdd GK-1651 aetheris-stage-2.draft.001.md
// @aa-sdd PK-778 docs/specs/common/stages.spec.md
// @aa-sdd PK-156 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-159 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-161 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-164 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-702 docs/specs/common/roux.spec.md
// @aa-sdd PK-166 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-167 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-168 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd GK-2476 rust-aetheris-scope-a-slice.draft.001.md
// @aa-sdd PK-168 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-709 docs/specs/common/rust.spec.md
public final class AmwpCodec {

    /**
     * The constructor stops a caller from creating an instance.
     * The codec methods are static.
     */
    private AmwpCodec() {
    }

    /**
     * The method builds one frame as a header plus the payload.
     * A null payload is encoded as an empty body.
     * A body longer than {@link AmwpConstants#MAX_PAYLOAD} throws {@link IllegalArgumentException}.
     * The header is 16 bytes.
     * Byte 0 is the magic value.
     * Byte 1 is the version.
     * Bytes 2 and 3 are the channel.
     * Byte 4 is the type.
     * Byte 5 is the flags.
     * Bytes 6 and 7 are zero.
     * Bytes 8 through 11 are the payload length.
     * Bytes 12 through 15 are the CRC32C of the first 12 header bytes plus the body.
     *
     * @param channel The channel is the frame channel id.
     * @param type The type is the frame type byte.
     * @param flags The flags are the frame flag byte.
     * @param payload The payload is the body octets, and it may be null.
     * @return The return value is the 16-byte header followed by the payload.
     * @throws IllegalArgumentException The method throws this when the payload is longer than 1 mebibyte.
     */
    public static byte[] encode(int channel, int type, int flags, byte[] payload) {
        byte[] body = payload == null ? new byte[0] : payload;
        if (body.length > AmwpConstants.MAX_PAYLOAD) {
            throw new IllegalArgumentException("AMWP payload exceeds 1 MiB");
        }
        byte[] out = new byte[AmwpConstants.HEADER_BYTES + body.length];
        out[0] = (byte) AmwpConstants.MAGIC;
        out[1] = (byte) AmwpConstants.VERSION;
        putU16(out, 2, channel);
        out[4] = (byte) (type & 0xFF);
        out[5] = (byte) (flags & 0xFF);
        out[6] = 0;
        out[7] = 0;
        putU32(out, 8, body.length);
        CRC32C crc = new CRC32C();
        crc.update(out, 0, 12);
        if (body.length > 0) {
            crc.update(body, 0, body.length);
        }
        putU32(out, 12, (int) crc.getValue());
        if (body.length > 0) {
            System.arraycopy(body, 0, out, AmwpConstants.HEADER_BYTES, body.length);
        }
        return out;
    }

    /**
     * The method writes one encoded frame and flushes the stream.
     *
     * @param out The stream is the sink that receives the frame.
     * @param channel The channel is the frame channel id.
     * @param type The type is the frame type byte.
     * @param flags The flags are the frame flag byte.
     * @param payload The payload is the body octets, and it may be null.
     * @throws IOException The method throws this when the write or the flush fails.
     */
    public static void write(OutputStream out, int channel, int type, int flags, byte[] payload)
            throws IOException {
        byte[] framed = encode(channel, type, flags, payload);
        out.write(framed);
        out.flush();
    }

    /**
     * The method blocks until one frame is read.
     * End of stream before the first byte returns null.
     * Any later short read is a truncated frame.
     *
     * @param in The stream is the framed byte stream.
     * @return The return value is the frame, or null on a clean end of stream.
     * @throws IOException The method throws this when the frame is truncated or corrupt.
     */
    public static AmwpFrame read(InputStream in) throws IOException {
        int first = in.read();
        if (first < 0) {
            return null;
        }
        byte[] header = new byte[AmwpConstants.HEADER_BYTES];
        header[0] = (byte) first;
        readFully(in, header, 1, AmwpConstants.HEADER_BYTES - 1);
        return parse(header, in);
    }

    /**
     * The method parses a 16-byte header and reads the payload from the stream.
     * A null header throws {@link IOException}.
     * A header shorter than 16 bytes throws {@link IOException}.
     * A magic byte other than {@code 0xAE} throws {@link IOException}.
     * A version other than {@code 1} throws {@link IOException}.
     * A payload length below 0 or above {@link AmwpConstants#MAX_PAYLOAD} throws {@link IOException}.
     * A CRC32C that does not match the header throws {@link IOException}.
     * The checksum covers the first 12 header bytes and the payload.
     *
     * @param header The header is the 16-byte header, with byte 0 already filled.
     * @param in The stream supplies the payload bytes.
     * @return The return value is the decoded frame.
     * @throws IOException The method throws this when the header or the payload is corrupt.
     */
    public static AmwpFrame parse(byte[] header, InputStream in) throws IOException {
        if (header == null || header.length < AmwpConstants.HEADER_BYTES) {
            throw new IOException("AMWP header truncated");
        }
        int magic = header[0] & 0xFF;
        if (magic != AmwpConstants.MAGIC) {
            throw new IOException("AMWP magic mismatch");
        }
        int version = header[1] & 0xFF;
        if (version != AmwpConstants.VERSION) {
            throw new IOException("AMWP version " + version);
        }
        int channel = getU16(header, 2);
        int type = header[4] & 0xFF;
        int flags = header[5] & 0xFF;
        int length = getU32(header, 8);
        if (length < 0 || length > AmwpConstants.MAX_PAYLOAD) {
            throw new IOException("AMWP payload length " + length);
        }
        byte[] payload = new byte[length];
        if (length > 0) {
            readFully(in, payload, 0, length);
        }
        CRC32C crc = new CRC32C();
        crc.update(header, 0, 12);
        if (length > 0) {
            crc.update(payload, 0, length);
        }
        int expect = getU32(header, 12);
        int actual = (int) crc.getValue();
        if (expect != actual) {
            throw new IOException("AMWP CRC32C mismatch");
        }
        return new AmwpFrame(channel, type, flags, payload);
    }

    /**
     * The method reports whether a byte is the AMWP magic value.
     * The comparison uses the low 8 bits.
     *
     * @param b The value is the first byte, and it may be wider than 8 bits.
     * @return The return value is true when those bits equal {@link AmwpConstants#MAGIC}.
     */
    public static boolean isMagic(int b) {
        return (b & 0xFF) == AmwpConstants.MAGIC;
    }

    /**
     * The method builds a Scope A payload.
     * Scope A is channel A, the notebook channel.
     * The prefix is {@link AmwpConstants#FRAG_PREFIX_BYTES} bytes.
     * The first 4 bytes are the fragment index.
     * The next 4 bytes are the fragment count.
     * The next 16 bytes are the cell id.
     * A null cell id, or an id whose length is not 16, is stored as 16 zero bytes.
     * A null body is stored as empty.
     * The body follows the prefix.
     *
     * @param fragIndex The index is the fragment number.
     * @param fragCount The count is how many fragments make the message.
     * @param cellId The cell id is 16 bytes, or null.
     * @param body The body is the remaining octets, and it may be null.
     * @return The return value is the prefix followed by the body.
     */
    public static byte[] scopeAPayload(int fragIndex, int fragCount, byte[] cellId, byte[] body) {
        byte[] id = cellId == null || cellId.length != 16 ? new byte[16] : cellId;
        byte[] rest = body == null ? new byte[0] : body;
        byte[] out = new byte[AmwpConstants.FRAG_PREFIX_BYTES + rest.length];
        putU32(out, 0, fragIndex);
        putU32(out, 4, fragCount);
        System.arraycopy(id, 0, out, 8, 16);
        if (rest.length > 0) {
            System.arraycopy(rest, 0, out, AmwpConstants.FRAG_PREFIX_BYTES, rest.length);
        }
        return out;
    }

    /**
     * The method reports whether a Scope A body fits in one frame.
     * The measured length is {@link AmwpConstants#FRAG_PREFIX_BYTES} plus the body length.
     * A null body counts as length 0.
     * The method does not call {@link #encode}, so it does not throw for a long body.
     *
     * @param body The body is the reply bytes without the fragment prefix, and it may be null.
     * @return The return value is true when that total is at most {@link AmwpConstants#MAX_PAYLOAD}.
     */
    public static boolean fits(byte[] body) {
        int n = body == null ? 0 : body.length;
        long total = (long) AmwpConstants.FRAG_PREFIX_BYTES + (long) n;
        return total <= (long) AmwpConstants.MAX_PAYLOAD;
    }

    /**
     * The method returns the body after the Scope A fragment prefix.
     * A null payload returns a new empty array.
     * A payload shorter than {@link AmwpConstants#FRAG_PREFIX_BYTES} is returned unchanged.
     *
     * @param payload The payload is a Scope A payload, and it may be null.
     * @return The return value is the body, or the original payload when it is too short.
     */
    public static byte[] stripFragPrefix(byte[] payload) {
        if (payload == null || payload.length < AmwpConstants.FRAG_PREFIX_BYTES) {
            return payload == null ? new byte[0] : payload;
        }
        byte[] body = new byte[payload.length - AmwpConstants.FRAG_PREFIX_BYTES];
        System.arraycopy(payload, AmwpConstants.FRAG_PREFIX_BYTES, body, 0, body.length);
        return body;
    }

    /**
     * The method copies the 16-byte cell id from a Scope A payload.
     * The id starts at offset 8.
     * A null payload returns 16 zero bytes.
     * A payload shorter than the fragment prefix returns 16 zero bytes.
     *
     * @param payload The payload is a Scope A payload, and it may be null.
     * @return The return value is a new 16-byte array.
     */
    public static byte[] cellIdOf(byte[] payload) {
        byte[] id = new byte[16];
        if (payload != null && payload.length >= AmwpConstants.FRAG_PREFIX_BYTES) {
            System.arraycopy(payload, 8, id, 0, 16);
        }
        return id;
    }

    /**
     * The method reads until {@code len} bytes are filled.
     * End of stream before that count throws {@link java.io.EOFException}.
     *
     * @param in The stream supplies the bytes.
     * @param buf The buffer receives the bytes.
     * @param off The offset is the first index to write.
     * @param len The length is the number of bytes required.
     * @throws IOException The method throws this when the stream ends early or the read fails.
     */
    private static void readFully(InputStream in, byte[] buf, int off, int len) throws IOException {
        int got = 0;
        while (got < len) {
            int n = in.read(buf, off + got, len - got);
            if (n < 0) {
                throw new EOFException("AMWP truncated");
            }
            got += n;
        }
    }

    /**
     * The method writes a 16-bit integer in little-endian order.
     *
     * @param buf The buffer receives the bytes.
     * @param off The offset is the first index to write.
     * @param value The value is the integer, and only the low 16 bits are stored.
     */
    private static void putU16(byte[] buf, int off, int value) {
        buf[off] = (byte) (value & 0xFF);
        buf[off + 1] = (byte) ((value >> 8) & 0xFF);
    }

    /**
     * The method writes a 32-bit integer in little-endian order.
     *
     * @param buf The buffer receives the bytes.
     * @param off The offset is the first index to write.
     * @param value The value is the integer.
     */
    private static void putU32(byte[] buf, int off, int value) {
        buf[off] = (byte) (value & 0xFF);
        buf[off + 1] = (byte) ((value >> 8) & 0xFF);
        buf[off + 2] = (byte) ((value >> 16) & 0xFF);
        buf[off + 3] = (byte) ((value >> 24) & 0xFF);
    }

    /**
     * The method reads a 16-bit little-endian integer.
     *
     * @param buf The buffer holds the bytes.
     * @param off The offset is the first index to read.
     * @return The return value is that integer.
     */
    private static int getU16(byte[] buf, int off) {
        return (buf[off] & 0xFF) | ((buf[off + 1] & 0xFF) << 8);
    }

    /**
     * The method reads a 32-bit little-endian integer.
     * The high bit is kept as a signed Java int.
     *
     * @param buf The buffer holds the bytes.
     * @param off The offset is the first index to read.
     * @return The return value is that integer.
     */
    private static int getU32(byte[] buf, int off) {
        return (buf[off] & 0xFF)
                | ((buf[off + 1] & 0xFF) << 8)
                | ((buf[off + 2] & 0xFF) << 16)
                | ((buf[off + 3] & 0xFF) << 24);
    }
}
/*****************************************************************************************
 * End of Class: AmwpCodec.java 090226
 ****************************************************************************************/
