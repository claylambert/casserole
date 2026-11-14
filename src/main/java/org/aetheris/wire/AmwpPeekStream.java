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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * The {@code AmwpPeekStream} class is the only reader of an SSH byte stream.
 * A daemon thread named {@code amwp-byte-pump} blocks on the source and queues each byte.
 * A timed peek does not call {@code available()}.
 * Apache MINA pipes often report 0 from {@code available()} until a blocking read.
 * // @aa-sdd G-18 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd PK-837 docs/specs/common/server.spec.md
public final class AmwpPeekStream extends InputStream {

    /**
     * The constant means {@link #pollByte} found no byte before the timeout.
     * The value is {@code -2}.
     */
    public static final int TIMEOUT = -2;

    private final InputStream src;
    private final LinkedBlockingQueue<Integer> queue;
    private final Thread pump;
    private volatile Integer unread;
    private volatile boolean closed;

    /**
     * The constructor starts the pump thread on the source stream.
     * This object becomes the only reader of that stream.
     * The thread is a daemon.
     *
     * @param src The source is the SSH byte stream.
     */
    public AmwpPeekStream(InputStream src) {
        this.src = src;
        this.queue = new LinkedBlockingQueue<Integer>();
        /**
         * The pump runnable reads the source until it closes and queues each byte.
         */
        this.pump = new Thread(new Runnable() {
            // @aa-sdd L39 aetheris-stages-1-3.finalize.md
            @Override
            public void run() {
                pumpLoop();
            }
        }, "amwp-byte-pump");
        this.pump.setDaemon(true);
        this.pump.start();
    }

    /**
     * The method waits up to the timeout for the next byte.
     * A byte pushed back by {@link #unread} is returned first.
     * No byte before the timeout returns {@link #TIMEOUT}.
     * An interrupt returns {@link #TIMEOUT} and restores the interrupt flag.
     * End of stream is {@code -1}.
     *
     * @param timeoutMs The timeout is the wait in milliseconds.
     * @return The return value is 0 through 255, {@code -1} at end of stream, or {@link #TIMEOUT}.
     */
    public int pollByte(long timeoutMs) {
        if (unread != null) {
            int b = unread.intValue();
            unread = null;
            return b;
        }
        try {
            Integer v = queue.poll(timeoutMs, TimeUnit.MILLISECONDS);
            if (v == null) {
                return TIMEOUT;
            }
            return v.intValue();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return TIMEOUT;
        }
    }

    /**
     * The method pushes one byte back for the next {@link #read()} or {@link #pollByte}.
     * A later push replaces the earlier one.
     *
     * @param b The value is a byte, or {@code -1} for end of stream.
     */
    public void unread(int b) {
        unread = Integer.valueOf(b);
    }

    /**
     * The method blocks until the next queued byte is available.
     * A byte pushed back by {@link #unread} is returned first.
     * An interrupt returns {@code -1} and restores the interrupt flag.
     *
     * @return The return value is the next byte, or {@code -1} at end of stream or on interrupt.
     * @throws IOException The method declares this to match {@link InputStream#read()}.
     */
    @Override
    public int read() throws IOException {
        if (unread != null) {
            int b = unread.intValue();
            unread = null;
            return b;
        }
        try {
            Integer v = queue.take();
            return v.intValue();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return -1;
        }
    }

    /**
     * The method fills a buffer from the queue without waiting after the first byte.
     * A null buffer returns 0.
     * A length of 0 or less returns 0.
     * End of stream on the first byte returns -1.
     * A later queued end of stream is pushed back and stops the copy.
     *
     * @param buf The buffer receives the bytes, and it may be null.
     * @param off The offset is the first index to write.
     * @param len The length is the maximum number of bytes to write.
     * @return The return value is the number of bytes written, or -1 at end of stream.
     * @throws IOException The method declares this to match {@link InputStream#read(byte[], int, int)}.
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
        while (n < len) {
            Integer v = queue.poll();
            if (v == null) {
                break;
            }
            int b = v.intValue();
            if (b < 0) {
                unread = Integer.valueOf(-1);
                break;
            }
            buf[off + n] = (byte) b;
            n++;
        }
        return n;
    }

    /**
     * The method returns how many bytes are already queued.
     * A pushed-back byte adds one to that count.
     * The count does not ask the source stream.
     *
     * @return The return value is that count.
     */
    @Override
    public int available() {
        int n = queue.size();
        if (unread != null) {
            n++;
        }
        return n;
    }

    /**
     * The method marks the stream closed, interrupts the pump, and closes the source.
     *
     * @throws IOException The method throws this when the source close fails.
     */
    @Override
    public void close() throws IOException {
        closed = true;
        pump.interrupt();
        src.close();
    }

    /**
     * The method blocks on the source and queues each byte as 0 through 255.
     * End of stream queues {@code -1} and returns.
     * A closed stream stops the loop before the next read.
     * Any other failure also queues {@code -1}.
     * An interrupt while queueing that failure restores the interrupt flag.
     */
    private void pumpLoop() {
        try {
            while (!closed) {
                int b = src.read();
                if (b < 0) {
                    queue.put(Integer.valueOf(-1));
                    return;
                }
                queue.put(Integer.valueOf(b & 0xFF));
            }
        } catch (Exception ex) {
            try {
                queue.put(Integer.valueOf(-1));
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
/*****************************************************************************************
 * End of Class: AmwpPeekStream.java 090226
 ****************************************************************************************/
