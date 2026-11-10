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

package org.casserole.pool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

// @aa-sdd PK-8252 docs/specs/java/org/casserole/pool/pool.spec.md
/**
 * The {@code PoolLink} class is one ACP pipe.
 * A local link is the stdin and stdout of {@code grok agent stdio}.
 * A remote link is one WebSocket to {@code grok agent serve}.
 * Each WebSocket text frame is one JSON-RPC object. The pool still writes one JSON line and reads one JSON line.
 * A newline on the output stream sends that line as one text frame, without the newline.
 * Each inbound text frame is offered to the reader as that text plus a newline.
 * The serve token, when present, is the {@code Authorization} header {@code Bearer} plus the token.
 * The token is not placed in the URL. The token is not written into an exception message.
 * A failed dial throws {@link IOException} with the message {@code spawn}.
 *
 * @author clayl (2026-11-08)
 */
final class PoolLink {

    private static final int LINE_CAP = 1024 * 1024;
    private static final int QUEUE_CAP = 1024;

    private final Process process;
    private final WebSocket socket;
    private final InputStream input;
    private final OutputStream output;
    private final InputStream error;
    private final AtomicBoolean open;
    private final LineStream lines;

    private PoolLink(Process process, WebSocket socket, InputStream input, OutputStream output,
            InputStream error, AtomicBoolean open, LineStream lines) {
        this.process = process;
        this.socket = socket;
        this.input = input;
        this.output = output;
        this.error = error;
        this.open = open;
        this.lines = lines;
    }

    /**
     * The {@code local} method wraps one child process.
     *
     * @param process The process is {@code grok agent stdio}.
     * @return The return value is the local link.
     */
    static PoolLink local(Process process) {
        return new PoolLink(process, null, process.getInputStream(), process.getOutputStream(),
                process.getErrorStream(), null, null);
    }

    /**
     * The {@code remote} method dials one serve WebSocket.
     * The connect wait is 10 seconds. A blank secret sends no Authorization header.
     *
     * @param url The url is {@code ws://host:port/ws}. It does not carry the secret.
     * @param secret The secret is the serve token, or empty.
     * @return The return value is the open link.
     * @throws IOException The method throws this exception with the message {@code spawn} when the dial fails.
     */
    static PoolLink remote(String url, String secret) throws IOException {
        LineStream lines = new LineStream();
        AtomicBoolean open = new AtomicBoolean(true);
        Ear ear = new Ear(lines, open);
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        WebSocket.Builder builder = client.newWebSocketBuilder().connectTimeout(Duration.ofSeconds(10));
        if (secret != null && secret.length() > 0) {
            builder.header("Authorization", "Bearer " + secret);
        }
        WebSocket socket;
        try {
            socket = builder.buildAsync(URI.create(url), ear).get(10, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IOException("spawn");
        } catch (ExecutionException ex) {
            throw new IOException("spawn");
        } catch (TimeoutException ex) {
            throw new IOException("spawn");
        } catch (IllegalArgumentException ex) {
            throw new IOException("spawn");
        }
        return new PoolLink(null, socket, lines, new FrameOut(socket), emptyError(), open, lines);
    }

    /**
     * The {@code input} method returns the stream the stdout pump reads.
     *
     * @return The return value is the inbound JSON lines.
     */
    InputStream input() {
        return input;
    }

    /**
     * The {@code output} method returns the stream the writer uses.
     *
     * @return The return value is the outbound JSON lines.
     */
    OutputStream output() {
        return output;
    }

    /**
     * The {@code error} method returns the stderr stream.
     * A remote link returns an empty stream that ends at once.
     *
     * @return The return value is the error stream.
     */
    InputStream error() {
        return error;
    }

    /**
     * The {@code alive} method reports whether the seat can still carry a turn.
     *
     * @return The return value is true when the process is alive, or the socket is still open.
     */
    boolean alive() {
        if (process != null) {
            return process.isAlive();
        }
        return open != null && open.get();
    }

    /**
     * The {@code exitCode} method returns the process exit code.
     * A live process returns {@code -1}. A socket returns {@code -1}.
     *
     * @return The return value is the exit code, or {@code -1}.
     */
    int exitCode() {
        if (process == null || process.isAlive()) {
            return -1;
        }
        try {
            return process.exitValue();
        } catch (IllegalThreadStateException ex) {
            return -1;
        }
    }

    /**
     * The {@code destroy} method closes the seat.
     * A process is force-stopped and the method waits up to 2 seconds.
     * A socket is closed and then aborted if the close does not finish.
     * An interrupt restores the thread interrupt flag.
     */
    void destroy() {
        if (process != null) {
            process.destroyForcibly();
            try {
                process.waitFor(2, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return;
        }
        if (open != null) {
            open.set(false);
        }
        if (lines != null) {
            lines.end();
        }
        if (socket != null) {
            try {
                socket.sendClose(WebSocket.NORMAL_CLOSURE, "").get(2, TimeUnit.SECONDS);
            } catch (Exception ex) {
                socket.abort();
                if (ex instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * The {@code emptyError} method returns a stream that is already finished.
     *
     * @return The return value is an empty stream.
     */
    private static InputStream emptyError() {
        return new InputStream() {
            @Override
            public int read() {
                return -1;
            }
        };
    }

    /**
     * The {@code LineStream} class is the inbound side of one WebSocket.
     * {@link #accept} offers one frame. {@link #read} blocks until a frame, or until {@link #end}.
     */
    private static final class LineStream extends InputStream {
        private final ArrayBlockingQueue<byte[]> queue = new ArrayBlockingQueue<byte[]>(QUEUE_CAP);
        private final AtomicBoolean closed = new AtomicBoolean(false);
        private byte[] current = new byte[0];
        private int pos;

        /**
         * The {@code accept} method offers one text frame as a line.
         * A newline is added when the frame does not already end with one.
         * A full queue ends the stream.
         *
         * @param text The text is one JSON-RPC object.
         */
        void accept(String text) {
            if (text == null || closed.get()) {
                return;
            }
            byte[] raw = text.getBytes(StandardCharsets.UTF_8);
            byte[] line;
            if (raw.length > 0 && raw[raw.length - 1] == '\n') {
                line = raw;
            } else {
                line = new byte[raw.length + 1];
                System.arraycopy(raw, 0, line, 0, raw.length);
                line[raw.length] = '\n';
            }
            if (!queue.offer(line)) {
                end();
            }
        }

        /**
         * The {@code end} method lets a blocked read finish.
         */
        void end() {
            closed.set(true);
        }

        /**
         * The {@code read} method returns the next byte.
         * The method waits up to 200 milliseconds between checks so a close can finish the stream.
         *
         * @return The return value is the next byte, or {@code -1} at the end.
         * @throws IOException The method throws this exception when the wait is interrupted.
         */
        @Override
        public int read() throws IOException {
            while (pos >= current.length) {
                byte[] next = null;
                try {
                    next = queue.poll(200, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new IOException("spawn");
                }
                if (next == null) {
                    if (closed.get() && queue.isEmpty()) {
                        return -1;
                    }
                    continue;
                }
                current = next;
                pos = 0;
                if (current.length == 0) {
                    continue;
                }
            }
            return current[pos++] & 0xff;
        }
    }

    /**
     * The {@code FrameOut} class sends one JSON line as one WebSocket text frame.
     * A carriage return is skipped. A newline sends the buffered line and clears the buffer.
     * A line over 1 MiB throws {@link IOException} with the message {@code spawn}.
     * The secret is not part of this stream.
     */
    private static final class FrameOut extends OutputStream {
        private final WebSocket socket;
        private final ByteArrayOutputStream buf = new ByteArrayOutputStream();

        /**
         * The constructor stores the socket.
         *
         * @param socket The socket receives one text frame per line.
         */
        private FrameOut(WebSocket socket) {
            this.socket = socket;
        }

        /**
         * The {@code write} method stores one byte, or sends the line when the byte is a newline.
         *
         * @param b The byte is one UTF-8 byte of the JSON line.
         * @throws IOException The method throws this exception when the line is too long or the send fails.
         */
        @Override
        public void write(int b) throws IOException {
            if (b == '\n') {
                send();
                return;
            }
            if (b == '\r') {
                return;
            }
            if (buf.size() >= LINE_CAP) {
                throw new IOException("spawn");
            }
            buf.write(b);
        }

        /**
         * The {@code flush} method sends a buffered line that has no newline yet.
         *
         * @throws IOException The method throws this exception when the send fails.
         */
        @Override
        public void flush() throws IOException {
            send();
        }

        /**
         * The {@code send} method writes the buffer as one complete text frame.
         * An empty buffer returns. The wait is 30 seconds. A failure throws {@code spawn} and does not include the token.
         *
         * @throws IOException The method throws this exception when the send fails.
         */
        private void send() throws IOException {
            if (buf.size() == 0) {
                return;
            }
            String text = buf.toString(StandardCharsets.UTF_8);
            buf.reset();
            try {
                socket.sendText(text, true).get(30, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IOException("spawn");
            } catch (ExecutionException ex) {
                throw new IOException("spawn");
            } catch (TimeoutException ex) {
                throw new IOException("spawn");
            }
        }
    }

    /**
     * The {@code Ear} class receives WebSocket text and offers each complete frame to the line stream.
     * A fragmented frame is joined until {@code last} is true. The next message is requested after each event.
     */
    private static final class Ear implements WebSocket.Listener {
        private final LineStream lines;
        private final AtomicBoolean open;
        private final StringBuilder buf = new StringBuilder();

        /**
         * The constructor stores the line stream and the open flag.
         *
         * @param lines The lines receive complete text frames.
         * @param open The flag is cleared when the socket ends.
         */
        private Ear(LineStream lines, AtomicBoolean open) {
            this.lines = lines;
            this.open = open;
        }

        /**
         * The {@code onOpen} method asks for the first message.
         *
         * @param webSocket The socket is the open serve connection.
         */
        @Override
        public void onOpen(WebSocket webSocket) {
            webSocket.request(1);
        }

        /**
         * The {@code onText} method joins one fragment and offers a complete frame.
         *
         * @param webSocket The socket is the serve connection.
         * @param data The data is one fragment.
         * @param last The flag is true on the last fragment of the frame.
         * @return The return value is null. The listener does not delay the next frame.
         */
        @Override
        public java.util.concurrent.CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            buf.append(data);
            if (last) {
                String text = buf.toString();
                buf.setLength(0);
                lines.accept(text);
            }
            webSocket.request(1);
            return null;
        }

        /**
         * The {@code onClose} method ends the line stream.
         *
         * @param webSocket The socket is closing.
         * @param statusCode The status code is the close code.
         * @param reason The reason is the close reason. It is not logged.
         * @return The return value is null.
         */
        @Override
        public java.util.concurrent.CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            open.set(false);
            lines.end();
            return null;
        }

        /**
         * The {@code onError} method ends the line stream.
         * The error text is not logged, so a token on a failed handshake is not written out.
         *
         * @param webSocket The socket has failed.
         * @param error The error is the failure. Its message is not logged.
         */
        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            open.set(false);
            lines.end();
        }
    }
}
