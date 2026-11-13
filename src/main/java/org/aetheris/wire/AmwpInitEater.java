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

import java.io.IOException;
import java.io.InputStream;

/**
 * The {@code AmwpInitEater} class wraps a raw terminal input stream.
 * The first read may discard one leading AMWP Init frame.
 * AMWP is the framed protocol on the connection.
 * That frame would otherwise be echoed as {@code chat> N} followed by U+FFFD, because the CRC byte is {@code 0x4E}.
 * The guard runs only on the first read.
 * Later octets pass through unchanged.
 * The class does not write an acknowledgement.
 * // @aa-sdd G-18 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd GK-2154 bugs-fixed.md
public final class AmwpInitEater extends InputStream {

    private final InputStream src;
    private boolean armed;

    /**
     * The constructor arms the guard on the raw input stream.
     * The source is the standard input of {@link org.casserole.ssh.shell.ChatShell} on the raw path.
     *
     * @param src The source is that input stream.
     */
    public AmwpInitEater(InputStream src) {
        this.src = src;
        this.armed = true;
    }

    /**
     * The method reads one byte from the source.
     * The first call disarms the guard.
     * End of stream on that first byte is returned as is.
     * A first byte that is not AMWP magic is returned as is.
     * A first byte that is magic is parsed as a frame.
     * An Init frame is discarded, and the method returns the next source byte.
     * A magic frame that is not a successful Init is also consumed, and the method returns the next source byte.
     * Later calls return the next source byte unchanged.
     *
     * @return The return value is the next byte, or -1 at end of stream.
     * @throws IOException The method throws this when the source read fails.
     */
    @Override
    public int read() throws IOException {
        if (armed) {
            armed = false;
            int first = src.read();
            if (first < 0) {
                return first;
            }
            if (!AmwpCodec.isMagic(first)) {
                return first;
            }
            if (!consumeInit(first)) {
                return src.read();
            }
            return src.read();
        }
        return src.read();
    }

    /**
     * The method reads into a buffer, starting with {@link #read()}.
     * A null buffer returns 0.
     * A length of 0 or less returns 0.
     * End of stream on the first byte returns -1.
     * Further bytes are copied only while the source reports bytes already available.
     *
     * @param buf The buffer receives the bytes, and it may be null.
     * @param off The offset is the first index to write.
     * @param len The length is the maximum number of bytes to write.
     * @return The return value is the number of bytes written, or -1 at end of stream.
     * @throws IOException The method throws this when the source read fails.
     */
    @Override
    public int read(byte[] buf, int off, int len) throws IOException {
        if (buf == null || len <= 0) {
            return 0;
        }
        int first = read();
        if (first < 0) {
            return -1;
        }
        buf[off] = (byte) first;
        int n = 1;
        if (n < len && src.available() > 0) {
            int more = src.read(buf, off + 1, len - 1);
            if (more > 0) {
                n += more;
            }
        }
        return n;
    }

    /**
     * The method returns the count reported by the source stream.
     *
     * @return The return value is that count.
     * @throws IOException The method throws this when the source fails.
     */
    @Override
    public int available() throws IOException {
        return src.available();
    }

    /**
     * The method closes the source stream.
     *
     * @throws IOException The method throws this when the source close fails.
     */
    @Override
    public void close() throws IOException {
        src.close();
    }

    /**
     * The method reads the rest of a header that began with a magic byte and parses the frame.
     * End of stream before the header is complete returns false.
     * A parse failure returns false.
     * A parsed frame whose type is Init returns true.
     * Any other parsed type returns false.
     * The header and payload bytes are consumed either way.
     *
     * @param first The first value is the magic byte already read.
     * @return The return value is true when an Init frame was discarded.
     * @throws IOException The method declares this because the source read declares it.
     */
    private boolean consumeInit(int first) throws IOException {
        byte[] header = new byte[AmwpConstants.HEADER_BYTES];
        header[0] = (byte) first;
        int got = 1;
        while (got < header.length) {
            int n = src.read(header, got, header.length - got);
            if (n < 0) {
                return false;
            }
            got += n;
        }
        try {
            AmwpFrame frame = AmwpCodec.parse(header, src);
            return frame.getType() == AmwpConstants.TYPE_INIT;
        } catch (IOException ex) {
            return false;
        }
    }
}
/*****************************************************************************************
 * End of Class: AmwpInitEater.java 090226
 ****************************************************************************************/
