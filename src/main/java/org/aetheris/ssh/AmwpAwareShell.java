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

package org.aetheris.ssh;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.aetheris.wire.AmwpCodec;
import org.aetheris.wire.AmwpConstants;
import org.aetheris.wire.AmwpFrame;
import org.aetheris.wire.AmwpInitEater;
import org.aetheris.wire.AmwpPeekStream;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.casserole.lamina.Lamina;
import org.casserole.ssh.shell.ChatShell;
import org.casserole.lamina.LaminaTask;
import org.casserole.lamina.Lane;

// @aa-sdd PK-1056 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1054 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1023 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1183 docs/specs/java/org/aetheris/ssh/ssh.spec.md
/**
 * The {@code AmwpAwareShell} class is the mux around {@link ChatShell} pipes.
 * The shell is an SSH {@link Command}.
 * It holds the inner command, the {@link AmwpFacade}, the {@link Lamina} pool, and the SSH user name.
 * A client that sends an AMWP init frame is muxed onto channel A and channel T.
 * A client that does not send that frame stays a raw PTY.
 * OpenSSH stays a raw PTY.
 * {@link #start} returns before the peek, so the client can send the init frame.
 * {@link #destroy} unbinds the console and closes the mux.
 * // @aa-sdd L40 aa-sdd-source-prose.md#AmwpAwareShell
 * // @aa-sdd G-07 G-18 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd PK-641 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1362 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-714 docs/specs/common/server.spec.md
// @aa-sdd PK-715 docs/specs/common/server.spec.md
// @aa-sdd PK-905 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-23 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-1385 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-707 docs/specs/common/rust.spec.md
// @aa-sdd PK-708 docs/specs/common/rust.spec.md
// @aa-sdd PK-711 docs/specs/common/rust.spec.md
// @aa-sdd PK-17 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-678 docs/specs/common/formal-plan.spec.md
public class AmwpAwareShell implements Command {

    private final Command inner;
    private final AmwpFacade facade;
    private final Lamina lamina;
    private final String sshUser;
    private final AtomicBoolean muxed;
    private final AtomicBoolean closed;

    private InputStream in;
    private AmwpPeekStream peek;
    private OutputStream out;
    private OutputStream err;
    private ExitCallback callback;
    private Timer keepaliveTimer;
    private Thread demuxThread;
    private PipedOutputStream tToShell;
    private PipedInputStream shellIn;
    private ChannelSession pendingChannel;
    private Environment pendingEnv;

    /**
     * The constructor stores the inner command and does not start it.
     * A null user name is stored as empty.
     * The mux flag starts false.
     * The closed flag starts false.
     *
     * @param inner The inner command is the {@link ChatShell}.
     * @param facade The facade is the Scope A handler.
     * @param lamina The lamina is the pool that runs {@link Lane#SSH_IO}.
     * @param sshUser The user name is the SSH user, or null.
     */
    public AmwpAwareShell(Command inner, AmwpFacade facade, Lamina lamina, String sshUser) {
        this.inner = inner;
        this.facade = facade;
        this.lamina = lamina;
        this.sshUser = sshUser == null ? "" : sshUser;
        this.muxed = new AtomicBoolean(false);
        this.closed = new AtomicBoolean(false);
    }

    /**
     * The {@code setInputStream} method stores the SSH input stream.
     * {@link #start} wraps that stream in an {@link AmwpPeekStream}.
     *
     * @param in The input is the SSH input stream.
     */
    @Override
    public void setInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * The {@code setOutputStream} method stores the SSH output stream.
     * Mux replies and channel T frames are written to this stream.
     *
     * @param out The output is the SSH output stream.
     */
    @Override
    public void setOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * The {@code setErrorStream} method stores the SSH error stream.
     * The raw path and the mux path both pass this stream to the inner command.
     *
     * @param err The error stream is the SSH error stream.
     */
    @Override
    public void setErrorStream(OutputStream err) {
        this.err = err;
    }

    /**
     * The {@code setExitCallback} method stores the exit callback.
     * The same callback is set on the inner command.
     *
     * @param callback The callback receives {@code onExit}.
     */
    @Override
    public void setExitCallback(ExitCallback callback) {
        this.callback = callback;
        inner.setExitCallback(callback);
    }

    /**
     * The {@code start} method stores the channel and returns before the peek.
     * The input stream is wrapped in an {@link AmwpPeekStream}.
     * A {@link LaminaTask} on {@link Lane#SSH_IO} named {@code aetheris-amwp-hs} runs {@link #handshakeThenStart}.
     * A blocking peek inside this method would stall {@code open().verify()}, which waits for {@code start}.
     *
     * @param channel The channel is the SSH channel session.
     * @param env The environment is the SSH environment.
     * @throws IOException The method declares {@link IOException}.
     */
    @Override
    public void start(ChannelSession channel, Environment env) throws IOException {
        this.pendingChannel = channel;
        this.pendingEnv = env;
        peek = new AmwpPeekStream(in);
        /**
         * The runnable calls {@link AmwpAwareShell#handshakeThenStart} on the SSH lane.
         */
        LaminaTask task = new LaminaTask(Lane.SSH_IO, "aetheris-amwp-hs", new Runnable() {
            // @aa-sdd L40 aa-sdd-source-prose.md#AmwpAwareShell
            @Override
            public void run() {
                handshakeThenStart();
            }
        });
        lamina.submit(task);
    }

    /**
     * The {@code handshakeThenStart} method peeks for init after {@link #start} has returned.
     * A closed shell returns without starting the inner command.
     * A null channel returns without starting the inner command.
     * {@link #peekInit} true calls {@link #startMux}.
     * A false peek wraps the stream in an {@link AmwpInitEater} and starts the inner command on the raw streams.
     * A close that arrives during the peek returns before either start.
     * An exception calls {@code onExit} with status 1 and the exception message when a callback is set.
     * // @aa-sdd G-18 aetheris-stages-1-3.finalize.md
     */
    private void handshakeThenStart() {
        if (closed.get()) {
            return;
        }
        ChannelSession channel = pendingChannel;
        Environment env = pendingEnv;
        if (channel == null) {
            return;
        }
        try {
            boolean amwp = peekInit();
            if (closed.get()) {
                return;
            }
            if (amwp) {
                startMux(channel, env);
            } else {
                inner.setInputStream(new AmwpInitEater(peek));
                inner.setOutputStream(out);
                inner.setErrorStream(err);
                inner.start(channel, env);
            }
        } catch (Exception ex) {
            if (callback != null) {
                callback.onExit(1, ex.getMessage());
            }
        }
    }

    /**
     * The {@code destroy} method closes the mux and the inner command.
     * The closed flag is set first.
     * {@link AmwpFacade#bindConsole} is called with a null sink for this user.
     * A keepalive timer is cancelled.
     * The demux thread is interrupted.
     * The channel T pipe and the peek stream are closed.
     * A close failure is ignored.
     * {@code inner.destroy} is called, and a failure there is ignored.
     *
     * @param channel The channel is the SSH channel session.
     */
    @Override
    public void destroy(ChannelSession channel) {
        closed.set(true);
        facade.bindConsole(sshUser, null);
        if (keepaliveTimer != null) {
            keepaliveTimer.cancel();
        }
        if (demuxThread != null) {
            demuxThread.interrupt();
        }
        try {
            if (tToShell != null) {
                tToShell.close();
            }
        } catch (IOException ignored) {
        }
        try {
            if (peek != null) {
                peek.close();
            }
        } catch (IOException ignored) {
        }
        try {
            inner.destroy(channel);
        } catch (Exception ignored) {
        }
    }

    /**
     * The {@code peekInit} method waits for the first octet and engages the mux only for init.
     * A null peek returns false.
     * {@link AmwpPeekStream#pollByte} waits {@link AmwpConstants#SERVER_PEEK_MS}.
     * {@link AmwpPeekStream#TIMEOUT} returns false.
     * A negative read returns false.
     * A first byte that is not magic is pushed back with {@link AmwpPeekStream#unread}, and the method returns false.
     * {@code available()} on MINA pipes is not a delivery signal.
     * Polling that count left the init frame on {@link ChatShell} stdin as {@code N} plus U+FFFD from CRC {@code 07 4e 91 d2}.
     * The rest of the {@link AmwpConstants#HEADER_BYTES} header is read next.
     * A short header returns false.
     * {@link AmwpCodec#parse} reads the payload.
     * Type {@code init} writes channel A type init with flag 0.
     * That payload is {@link AmwpCodec#scopeAPayload} of index 0, count 1, a null cell id, and {@link AmwpFacade#initFacts}.
     * The mux flag is then set, and the method returns true.
     * Any other type returns false.
     * Any exception returns false.
     * // @aa-sdd G-18 aetheris-stages-1-3.finalize.md
     *
     * @return The return value is true when the mux engaged.
     */
    private boolean peekInit() {
        if (peek == null) {
            return false;
        }
        try {
            int first = peek.pollByte(AmwpConstants.SERVER_PEEK_MS);
            if (first == AmwpPeekStream.TIMEOUT || first < 0) {
                return false;
            }
            if (!AmwpCodec.isMagic(first)) {
                peek.unread(first);
                return false;
            }
            byte[] header = new byte[AmwpConstants.HEADER_BYTES];
            header[0] = (byte) first;
            int got = 1;
            while (got < header.length) {
                int n = peek.read(header, got, header.length - got);
                if (n < 0) {
                    return false;
                }
                got += n;
            }
            AmwpFrame frame = AmwpCodec.parse(header, peek);
            if (frame.getType() == AmwpConstants.TYPE_INIT) {
                byte[] facts = facade.initFacts();
                AmwpCodec.write(out, AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_INIT, 0,
                        AmwpCodec.scopeAPayload(0, 1, null, facts));
                muxed.set(true);
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * The {@code startMux} method starts the inner command on mux pipes.
     * Channel T bytes travel through a pipe whose buffer is 65536 bytes.
     * The inner input is that pipe.
     * The inner output is a {@link ScopeTOut} on the SSH output.
     * The inner error stream stays the SSH error stream.
     * {@link AmwpFacade#openBantha} opens the session for this user.
     * {@link AmwpFacade#bindConsole} stores a sink that writes each reply with flag 0.
     * The demux thread is named {@code aetheris-amwp-server} and is a daemon.
     * The keepalive timer is named {@code aetheris-amwp-server-ka} and is a daemon.
     * The timer first fires after {@link AmwpConstants#KEEPALIVE_MS} and then repeats at that same period.
     * A {@link ChatShell} inner command sends {@link ChatShell#sessionTokenReply} before the first client paint.
     *
     * @param channel The channel is the SSH channel session.
     * @param env The environment is the SSH environment.
     * @throws IOException The exception is a failure to start the inner command.
     */
    private void startMux(ChannelSession channel, Environment env) throws IOException {
        tToShell = new PipedOutputStream();
        shellIn = new PipedInputStream(tToShell, 65536);
        inner.setInputStream(shellIn);
        inner.setOutputStream(new ScopeTOut(out));
        inner.setErrorStream(err);
        inner.start(channel, env);
        facade.openBantha(sshUser);
        /**
         * The sink writes one reply frame on the SSH output.
         * The write is synchronized on that output.
         * The flags are 0.
         *
         * @param ch The channel is the AMWP channel.
         * @param type The type is the AMWP frame type.
         * @param payload The payload is the frame body.
         * @throws Exception The exception is a write failure.
         */
        facade.bindConsole(sshUser, new AmwpFacade.AmwpReplySink() {
            // @aa-sdd PK-1023 docs/specs/common/formal-plan.spec.md
            @Override
            public void send(int ch, int type, byte[] payload) throws Exception {
                synchronized (out) {
                    AmwpCodec.write(out, ch, type, 0, payload);
                }
            }
        });
        /**
         * The runnable reads frames in {@link AmwpAwareShell#demuxLoop}.
         */
        demuxThread = new Thread(new Runnable() {
            // @aa-sdd L40 aa-sdd-source-prose.md#AmwpAwareShell
            @Override
            public void run() {
                demuxLoop();
            }
        }, "aetheris-amwp-server");
        demuxThread.setDaemon(true);
        demuxThread.start();
        keepaliveTimer = new Timer("aetheris-amwp-server-ka", true);
        /**
         * The task calls {@link AmwpAwareShell#sendKeepalive} on each tick.
         */
        keepaliveTimer.scheduleAtFixedRate(new TimerTask() {
            // @aa-sdd L40 aa-sdd-source-prose.md#AmwpAwareShell
            @Override
            public void run() {
                sendKeepalive();
            }
        }, AmwpConstants.KEEPALIVE_MS, AmwpConstants.KEEPALIVE_MS);
        // The Roux count is on the wire before the first client paint settles.
        // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-163
        if (inner instanceof ChatShell) {
            writeConsoleReply(((ChatShell) inner).sessionTokenReply());
        }
    }

    /**
     * The {@code demuxLoop} method reads frames until the peek ends or the shell closes.
     * A null frame ends the loop.
     * A keepalive frame is skipped.
     * A channel T frame with a non-empty payload is written to the shell pipe and flushed.
     * A channel A data frame that {@link #consoleModelFrame} accepts is not passed to the facade.
     * Every other frame is passed to {@link AmwpFacade#handle} with a sink that writes flag 0.
     * An exception calls {@code onExit} with status 1 when the shell is still open and a callback is set.
     */
    private void demuxLoop() {
        try {
            while (!closed.get()) {
                AmwpFrame frame = AmwpCodec.read(peek);
                if (frame == null) {
                    break;
                }
                if (frame.getType() == AmwpConstants.TYPE_KEEPALIVE) {
                    continue;
                }
                if (frame.getChannel() == AmwpConstants.CHANNEL_T) {
                    if (frame.getPayload().length > 0 && tToShell != null) {
                        tToShell.write(frame.getPayload());
                        tToShell.flush();
                    }
                    continue;
                }
                if (frame.getChannel() == AmwpConstants.CHANNEL_A
                        && frame.getType() == AmwpConstants.TYPE_DATA
                        && consoleModelFrame(frame)) {
                    continue;
                }
                /**
                 * The sink writes one reply frame on the SSH output.
                 * The write is synchronized on that output.
                 * The flags are 0.
                 *
                 * @param ch The channel is the AMWP channel.
                 * @param type The type is the AMWP frame type.
                 * @param payload The payload is the frame body.
                 * @throws Exception The exception is a write failure.
                 */
                facade.handle(sshUser, frame, new AmwpFacade.AmwpReplySink() {
                    // @aa-sdd L40 aa-sdd-source-prose.md#AmwpAwareShell
                    @Override
                    public void send(int ch, int type, byte[] payload) throws Exception {
                        synchronized (out) {
                            AmwpCodec.write(out, ch, type, 0, payload);
                        }
                    }
                });
            }
        } catch (Exception ex) {
            if (!closed.get() && callback != null) {
                callback.onExit(1, ex.getMessage());
            }
        }
    }

    /**
     * The {@code consoleModelFrame} method handles console frames that stay off the cell execute path.
     * The body is {@link AmwpCodec#stripFragPrefix} read as UTF-8.
     * The first line is {@link #firstLine}.
     * {@code shell_mood:} calls {@link ChatShell#applyConsoleMood} and does not paint.
     * {@code shell_mood_clear:} does the same.
     * {@code session_export:} replies with this connection's Roux table and does not paint.
     * {@code session_replace:} replaces the turns from the table and does not paint.
     * An exact {@code session_tokens:} replies with the Roux count and does not paint.
     * {@code shell_model:} and {@code shell_model_set:} call {@link #applyConsoleFrame}.
     * Each of those prefixes returns true even when the inner command is not a {@link ChatShell}.
     * A true return means {@link #demuxLoop} does not pass the frame to the facade.
     * Any other first line returns false.
     * A false return leaves the frame for {@link AmwpFacade#handle}.
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-163
     *
     * @param frame The frame is a channel A data frame.
     * @return The return value is true when this method handled the frame.
     */
    private boolean consoleModelFrame(AmwpFrame frame) {
        byte[] body = AmwpCodec.stripFragPrefix(frame.getPayload());
        String text = new String(body, StandardCharsets.UTF_8);
        String first = firstLine(text);
        if (first.startsWith("shell_mood:") || first.startsWith("shell_mood_clear:")) {
            if (inner instanceof ChatShell) {
                ((ChatShell) inner).applyConsoleMood(text);
            }
            return true;
        }
        if (first.startsWith("session_export:")) {
            if (inner instanceof ChatShell) {
                replySessionExport((ChatShell) inner);
            }
            return true;
        }
        if (first.startsWith("session_replace:")) {
            if (inner instanceof ChatShell) {
                replySessionReplace((ChatShell) inner, text);
            }
            return true;
        }
        if ("session_tokens:".equals(first)) {
            // The reply is the Roux count. The VT100 grid is not written.
            // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-163
            if (inner instanceof ChatShell) {
                writeConsoleReply(((ChatShell) inner).sessionTokenReply());
            }
            return true;
        }
        if (!first.startsWith("shell_model:") && !first.startsWith("shell_model_set:")) {
            return false;
        }
        if (inner instanceof ChatShell) {
            applyConsoleFrame((ChatShell) inner, text, first.startsWith("shell_model_set:"));
        }
        return true;
    }

    /**
     * The {@code applyConsoleFrame} method reads or writes the shell model, then replies.
     * A set frame calls {@link ChatShell#applyConsoleTarget}.
     * That path writes {@code model }, the console target label, and a carriage return plus newline.
     * The VT100 line is written before the reply lock.
     * The reply is {@code shell_model_result:} plus a newline plus {@link ChatShell#consoleModelBody}.
     * A null output returns without writing the reply.
     * The reply is channel A type data with flag 0.
     * An {@link IOException} is ignored.
     *
     * @param shell The shell is the console.
     * @param text The text is the stripped body.
     * @param set The flag is true for {@code shell_model_set:}.
     */
    private void applyConsoleFrame(ChatShell shell, String text, boolean set) {
        if (set) {
            shell.applyConsoleTarget(text);
            shell.write("model " + shell.consoleTargetLabel() + "\r\n");
        }
        String reply = "shell_model_result:\n" + shell.consoleModelBody();
        byte[] utf = reply.getBytes(StandardCharsets.UTF_8);
        if (out == null) {
            return;
        }
        try {
            synchronized (out) {
                AmwpCodec.write(out, AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_DATA, 0,
                        AmwpCodec.scopeAPayload(0, 1, null, utf));
            }
        } catch (IOException ignored) {
            // mux closing
        }
    }

    /**
     * The {@code replySessionExport} method replies with this connection's Roux table.
     * The text is {@link ChatShell#sessionExportReply} of {@link ChatShell#exportSessionToon}.
     * The VT100 grid is not written.
     *
     * @param shell The shell is the console.
     */
    private void replySessionExport(ChatShell shell) {
        String reply = ChatShell.sessionExportReply(shell.exportSessionToon());
        writeConsoleReply(reply);
    }

    /**
     * The {@code replySessionReplace} method replaces this connection's turns.
     * The reply is {@link ChatShell#applySessionReplace}.
     * A bad table leaves the region as it stands.
     * The VT100 grid is not written.
     *
     * @param shell The shell is the console.
     * @param text The text is the stripped body.
     */
    private void replySessionReplace(ChatShell shell, String text) {
        writeConsoleReply(shell.applySessionReplace(text));
    }

    /**
     * The {@code writeConsoleReply} method writes one channel A text reply.
     * The text is UTF-8.
     * A null output returns without writing.
     * The frame is type data with flag 0.
     * The payload is {@link AmwpCodec#scopeAPayload} of index 0, count 1, and a null cell id.
     * The write is synchronized on the output.
     * An {@link IOException} is ignored.
     *
     * @param reply The reply is the channel A text.
     */
    private void writeConsoleReply(String reply) {
        byte[] utf = reply.getBytes(StandardCharsets.UTF_8);
        if (out == null) {
            return;
        }
        try {
            synchronized (out) {
                AmwpCodec.write(out, AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_DATA, 0,
                        AmwpCodec.scopeAPayload(0, 1, null, utf));
            }
        } catch (IOException ignored) {
            // mux closing
        }
    }

    /**
     * The {@code firstLine} method returns the first line without one trailing carriage return.
     * A null body returns empty.
     * An empty body returns empty.
     * The line ends at the first newline.
     * A body with no newline is the whole body.
     *
     * @param text The text is the body.
     * @return The return value is that line.
     */
    private static String firstLine(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        int nl = text.indexOf('\n');
        String line = nl < 0 ? text : text.substring(0, nl);
        if (line.endsWith("\r")) {
            return line.substring(0, line.length() - 1);
        }
        return line;
    }

    /**
     * The {@code lineValue} method returns the trimmed value of one prefixed line.
     * A null body returns empty.
     * A null key returns empty.
     * Each line drops one trailing carriage return.
     * The key includes the colon.
     * A missing line returns empty.
     *
     * @param text The text is the body.
     * @param key The key is the line prefix, including the colon.
     * @return The return value is the trimmed value, or empty.
     */
    private static String lineValue(String text, String key) {
        if (text == null || key == null) {
            return "";
        }
        int start = 0;
        while (start <= text.length()) {
            int nl = text.indexOf('\n', start);
            String line = nl < 0 ? text.substring(start) : text.substring(start, nl);
            if (line.endsWith("\r")) {
                line = line.substring(0, line.length() - 1);
            }
            if (line.startsWith(key)) {
                return line.substring(key.length()).trim();
            }
            if (nl < 0) {
                break;
            }
            start = nl + 1;
        }
        return "";
    }

    /**
     * The {@code sendKeepalive} method writes one keepalive frame.
     * A shell that is not muxed returns without writing.
     * A null output returns without writing.
     * A closed shell returns without writing.
     * The frame is channel A type keepalive with flag 0 and an empty payload.
     * An {@link IOException} is ignored.
     */
    private void sendKeepalive() {
        if (!muxed.get() || out == null || closed.get()) {
            return;
        }
        try {
            synchronized (out) {
                AmwpCodec.write(out, AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_KEEPALIVE, 0, new byte[0]);
            }
        } catch (IOException ignored) {
        }
    }

    // @aa-sdd L40 aa-sdd-source-prose.md#AmwpAwareShell
    /**
     * The {@code ScopeTOut} class turns {@link ChatShell} writes into channel T frames.
     * Each write is type data with flag 0.
     * The payload is the written bytes, not a Scope A prefix.
     */
    private static final class ScopeTOut extends FilterOutputStream {
        /**
         * The constructor stores the SSH output stream.
         *
         * @param out The output is the SSH output stream.
         */
        private ScopeTOut(OutputStream out) {
            super(out);
        }

        /**
         * The {@code write} method sends one byte as a channel T frame.
         * The call uses the three-argument {@link #write(byte[], int, int)}.
         *
         * @param b The byte is the single octet.
         * @throws IOException The exception is a write failure.
         */
        @Override
        public void write(int b) throws IOException {
            write(new byte[] { (byte) b }, 0, 1);
        }

        /**
         * The {@code write} method sends a slice as one channel T data frame.
         * A null buffer returns without writing.
         * A length below 1 returns without writing.
         * The slice is copied before the write.
         * The write is synchronized on the SSH output.
         *
         * @param buf The buffer holds the bytes.
         * @param off The offset is the first byte in the buffer.
         * @param len The length is the number of bytes to send.
         * @throws IOException The exception is a write failure.
         */
        @Override
        public void write(byte[] buf, int off, int len) throws IOException {
            if (buf == null || len <= 0) {
                return;
            }
            byte[] slice = new byte[len];
            System.arraycopy(buf, off, slice, 0, len);
            synchronized (out) {
                AmwpCodec.write(out, AmwpConstants.CHANNEL_T, AmwpConstants.TYPE_DATA, 0, slice);
            }
        }
    }

}
/*****************************************************************************************
 * End of Class: AmwpAwareShell.java 090226
 ****************************************************************************************/
