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

package org.casserole.app.ui.ssh;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.aetheris.wire.AmwpClientListener;
import org.aetheris.wire.AmwpCodec;
import org.aetheris.wire.AmwpConstants;
import org.aetheris.wire.AmwpFrame;
import org.aetheris.wire.AmwpToon;
import org.aetheris.wire.AmwpPeekStream;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelShell;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;

import com.jediterm.core.util.TermSize;
import com.jediterm.terminal.TtyConnector;

// @aa-sdd PK-0669 docs/specs/java/org/casserole/llm/llm.spec.md
/**
 * The {@code SshTtyConnector} connects JediTerm, the terminal widget, to an Apache MINA SSHD shell channel.
 * Keyboard bytes are written to the channel's inverted standard input.
 * Remote output is read from the channel's inverted standard output.
 * AMWP is the framed protocol carried on that same channel.
 * An Init reply that is not a valid {@code 0xAE} frame leaves the connector in raw mode.
 * In AMWP mode, JediTerm sees only Scope T octets.
 * Scope T is the terminal channel inside an AMWP frame.
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
 * // @aa-sdd PK-0471 PK-0480 PK-0476 PK-0481 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-3491 PK-3493 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 *
 * @author clayl (2026-08-24)
 */
public class SshTtyConnector implements TtyConnector {

    private final String name;
    private final SshClient client;
    private final ClientSession session;
    private final ChannelShell channel;
    private final InputStream invertedOut;
    private final OutputStream invertedIn;
    private final AtomicBoolean closed;
    private final LinkedBlockingQueue<byte[]> tChunks;
    private final Object writeLock;
    /**
     * The field is the lock that guards the Scope A listener and the held frames.
     * Scope A is the notebook channel inside an AMWP frame.
     * Frames stay here while no listener is stored on this session.
     */
    private final Object amwpLock;
    private final ArrayDeque<AmwpFrame> heldFrames;
    private static final int HELD_FRAME_CAP = 64;
    /**
     * The field is the opening of the private console OSC sequence {@code ESC ] 7 7 7 ; select-model ;}.
     * An OSC sequence is a terminal control string.
     * A sequence split across chunks stays in a hold until BEL arrives.
     * The hold stops at 256 bytes.
     */
    private static final byte[] OSC_OPEN = "\u001b]777;select-model;".getBytes(StandardCharsets.UTF_8);
    private static final int OSC_HOLD_CAP = 256;
    private byte[] oscHold = new byte[0];

    private volatile boolean amwp;
    private volatile AmwpToon.SessionFacts sessionFacts;
    private AmwpClientListener amwpListener;
    private InputStreamReader reader;
    private Thread demuxThread;
    private Timer keepaliveTimer;
    private AmwpPeekStream peek;

    /**
     * The constructor stores the open shell and prepares a raw UTF-8 reader on its inverted output.
     * AMWP is off until {@link #tryAmwpInit} succeeds.
     * The held-frame list starts empty.
     *
     * @param name The name is the display name, usually the user, the remote address, and the port.
     * @param client The client is the started Apache MINA SSHD client.
     * @param session The session is the authenticated SSH session.
     * @param channel The channel is the open shell channel that uses inverted streams.
     */
    public SshTtyConnector(String name, SshClient client, ClientSession session, ChannelShell channel) {
        this.name = name;
        this.client = client;
        this.session = session;
        this.channel = channel;
        this.invertedOut = channel.getInvertedOut();
        this.invertedIn = channel.getInvertedIn();
        this.closed = new AtomicBoolean(false);
        this.tChunks = new LinkedBlockingQueue<byte[]>();
        this.writeLock = new Object();
        this.amwpLock = new Object();
        this.heldFrames = new ArrayDeque<AmwpFrame>();
        this.amwp = false;
        this.reader = new InputStreamReader(this.invertedOut, StandardCharsets.UTF_8);
    }

    /**
     * The method sends a client Init frame and switches to AMWP only when a valid reply arrives.
     * The reply magic byte is {@code 0xAE}.
     * A timeout, a closed stream, or any other failure leaves a raw PTY reader.
     * A PTY is the remote terminal allocated for the shell.
     *
     * The method writes an Init frame on Scope A.
     * The method waits up to the Init wait for the first byte.
     * A timeout or a negative read installs a raw reader on the peeked stream and returns.
     * A first byte that is not magic is pushed back onto that raw stream.
     * A magic byte is completed into a header.
     * A header read that ends early installs the raw reader and returns.
     * An Init frame stores session facts from {@link org.aetheris.wire.AmwpToon#decodeFacts}.
     * An empty Init body is not reported as facts.
     * An Init frame or a Data frame calls {@link #enterMux}.
     * A Scope T payload on that first frame is queued for JediTerm.
     * Any other parsed type is not treated as a successful handshake.
     * An exception clears the AMWP flag and leaves the raw reader.
     *
     * // @aa-sdd PK-0476 PK-0481 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-3493 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     */
    public void tryAmwpInit() {
        peek = new AmwpPeekStream(invertedOut);
        try {
            AmwpCodec.write(invertedIn, AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_INIT, 0,
                    AmwpCodec.scopeAPayload(0, 1, null, new byte[0]));
            int first = peek.pollByte(AmwpConstants.INIT_WAIT_MS);
            if (first == AmwpPeekStream.TIMEOUT || first < 0) {
                reader = new InputStreamReader(peek, StandardCharsets.UTF_8);
                return;
            }
            if (AmwpCodec.isMagic(first)) {
                byte[] header = new byte[AmwpConstants.HEADER_BYTES];
                header[0] = (byte) first;
                int got = 1;
                while (got < header.length) {
                    int n = peek.read(header, got, header.length - got);
                    if (n < 0) {
                        reader = new InputStreamReader(peek, StandardCharsets.UTF_8);
                        return;
                    }
                    got += n;
                }
                AmwpFrame frame = AmwpCodec.parse(header, peek);
                if (frame.getType() == AmwpConstants.TYPE_INIT
                        || frame.getType() == AmwpConstants.TYPE_DATA) {
                    if (frame.getType() == AmwpConstants.TYPE_INIT) {
                        sessionFacts = AmwpToon.decodeFacts(AmwpCodec.stripFragPrefix(frame.getPayload()));
                    }
                    enterMux();
                    if (frame.getChannel() == AmwpConstants.CHANNEL_T
                            && frame.getPayload().length > 0) {
                        tChunks.offer(frame.getPayload());
                    }
                    return;
                }
            }
            peek.unread(first);
        } catch (Exception ex) {
            amwp = false;
        }
        reader = new InputStreamReader(peek, StandardCharsets.UTF_8);
    }

    /**
     * The method switches JediTerm reads onto the Scope T queue.
     * The reader becomes a {@link ScopeTStream}.
     * A daemon thread named {@code aetheris-amwp-demux} runs {@link #demuxLoop}.
     * A daemon timer named {@code aetheris-amwp-ka} calls {@link #sendKeepalive} on the keepalive interval.
     */
    private void enterMux() {
        amwp = true;
        reader = new InputStreamReader(new ScopeTStream(), StandardCharsets.UTF_8);
        /**
         * The runnable runs {@link #demuxLoop} on the demux thread.
         * The thread name is {@code aetheris-amwp-demux}.
         */
        demuxThread = new Thread(new Runnable() {
            // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
            // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
            // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
            // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
            // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
            // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
            // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
            // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            @Override
            public void run() {
                demuxLoop();
            }
        }, "aetheris-amwp-demux");
        demuxThread.setDaemon(true);
        demuxThread.start();
        keepaliveTimer = new Timer("aetheris-amwp-ka", true);
        /**
         * The task calls {@link #sendKeepalive} on each keepalive tick.
         */
        keepaliveTimer.scheduleAtFixedRate(new TimerTask() {
            // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
            // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
            // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
            // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
            // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
            // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
            // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
            // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            @Override
            public void run() {
                sendKeepalive();
            }
        }, AmwpConstants.KEEPALIVE_MS, AmwpConstants.KEEPALIVE_MS);
    }

    /**
     * The method reads AMWP frames until the stream ends or the connector closes.
     * A null frame ends the loop.
     * A keepalive frame is skipped.
     * A Scope T frame with a payload is passed to {@link #queueScopeT}.
     * Every other frame is passed to {@link #deliverOrHold}.
     * An exception notifies the listener with {@code onAmwpClosed} when a listener is stored and the connector is not already closed.
     * Bytes still held for a split OSC sequence are queued after the loop.
     * An empty chunk is queued so {@link ScopeTStream} can finish.
     */
    private void demuxLoop() {
        try {
            while (!closed.get() && isConnected()) {
                AmwpFrame frame = AmwpCodec.read(peek == null ? invertedOut : peek);
                if (frame == null) {
                    break;
                }
                if (frame.getType() == AmwpConstants.TYPE_KEEPALIVE) {
                    continue;
                }
                if (frame.getChannel() == AmwpConstants.CHANNEL_T) {
                    if (frame.getPayload().length > 0) {
                        queueScopeT(frame.getPayload());
                    }
                    continue;
                }
                deliverOrHold(frame);
            }
        } catch (Exception ex) {
            AmwpClientListener listener;
            synchronized (amwpLock) {
                listener = amwpListener;
            }
            if (listener != null && !closed.get()) {
                listener.onAmwpClosed(ex.getMessage());
            }
        }
        if (oscHold.length > 0) {
            tChunks.offer(oscHold);
            oscHold = new byte[0];
        }
        tChunks.offer(new byte[0]);
    }

    /**
     * The method queues Scope T bytes after the private select-model OSC sequence is removed.
     * The sequence opens with {@code ESC ] 7 7 7 ; select-model ;} and ends at BEL.
     * Bytes before that sequence are painted to JediTerm.
     * A matching sequence is not painted.
     * The text before the first semicolon is the model id.
     * The text after that semicolon is the think string.
     * Both strings are passed to {@link #notifyConsoleModel}.
     * A prefix cut off at the end of the chunk is held.
     * A full opening with no BEL is held when the pending tail is at most 256 bytes.
     * A longer unfinished tail is painted instead of held.
     * A non-matching escape byte is painted and the scan continues.
     * Painted bytes are placed on the Scope T queue.
     * The queue put can throw {@link InterruptedException} while the terminal queue is closing.
     *
     * @param chunk The chunk is one Scope T payload.
     * @throws InterruptedException The exception is thrown when the queue put is interrupted.
     */
    private void queueScopeT(byte[] chunk) throws InterruptedException {
        byte[] data = concat(oscHold, chunk);
        oscHold = new byte[0];
        ByteArrayOutputStream paint = new ByteArrayOutputStream();
        int i = 0;
        while (i < data.length) {
            int esc = indexOf(data, (byte) 0x1B, i);
            if (esc < 0) {
                paint.write(data, i, data.length - i);
                break;
            }
            if (esc > i) {
                paint.write(data, i, esc - i);
            }
            int matched = prefixLen(data, esc, OSC_OPEN);
            if (matched < OSC_OPEN.length) {
                if (matched > 0 && esc + matched == data.length) {
                    holdTail(data, esc);
                    break;
                }
                paint.write(data[esc]);
                i = esc + 1;
                continue;
            }
            int bel = indexOf(data, (byte) 0x07, esc + OSC_OPEN.length);
            if (bel < 0) {
                int pending = data.length - esc;
                if (pending > OSC_HOLD_CAP) {
                    paint.write(data, esc, pending);
                } else {
                    holdTail(data, esc);
                }
                break;
            }
            int bodyStart = esc + OSC_OPEN.length;
            String body = new String(data, bodyStart, bel - bodyStart, StandardCharsets.UTF_8);
            int semi = body.indexOf(';');
            String model = semi < 0 ? body : body.substring(0, semi);
            String think = semi < 0 ? "" : body.substring(semi + 1);
            notifyConsoleModel(model, think);
            i = bel + 1;
        }
        if (paint.size() > 0) {
            tChunks.put(paint.toByteArray());
        }
    }

    /**
     * The method tells the stored listener about a select-model sequence.
     * A missing listener returns without a call.
     * A null model is passed as an empty string.
     * A null think string is passed as an empty string.
     *
     * @param model The model is the Ollama id inside the OSC sequence.
     * @param think The think value is the think string inside the OSC sequence.
     */
    private void notifyConsoleModel(String model, String think) {
        AmwpClientListener listener;
        synchronized (amwpLock) {
            listener = amwpListener;
        }
        if (listener == null) {
            return;
        }
        listener.onConsoleModel(model == null ? "" : model, think == null ? "" : think);
    }

    /**
     * The method stores the unfinished tail that starts at {@code from}.
     * A tail of zero bytes is ignored.
     * A tail longer than 256 bytes is ignored.
     *
     * @param data The data array is the chunk being scanned.
     * @param from The from index is the first byte of the unfinished tail.
     */
    private void holdTail(byte[] data, int from) {
        int n = data.length - from;
        if (n <= 0 || n > OSC_HOLD_CAP) {
            return;
        }
        byte[] hold = new byte[n];
        System.arraycopy(data, from, hold, 0, n);
        oscHold = hold;
    }

    /**
     * The method joins two byte arrays.
     * A null array counts as empty.
     * An empty left side returns the right array, or an empty array when the right side is also null.
     * An empty right side returns the left array.
     *
     * @param left The left array is the earlier bytes.
     * @param right The right array is the later bytes.
     * @return The return value is the joined array.
     */
    private static byte[] concat(byte[] left, byte[] right) {
        int a = left == null ? 0 : left.length;
        int b = right == null ? 0 : right.length;
        if (a == 0) {
            return right == null ? new byte[0] : right;
        }
        if (b == 0) {
            return left;
        }
        byte[] both = new byte[a + b];
        System.arraycopy(left, 0, both, 0, a);
        System.arraycopy(right, 0, both, a, b);
        return both;
    }

    /**
     * The method finds the next matching byte.
     * A null array returns {@code -1}.
     * A negative start is treated as zero.
     * A missing byte returns {@code -1}.
     *
     * @param data The data array is the buffer to scan.
     * @param want The want value is the byte to find.
     * @param from The from index is the first position to check.
     * @return The return value is the index of the byte, or {@code -1}.
     */
    private static int indexOf(byte[] data, byte want, int from) {
        if (data == null) {
            return -1;
        }
        int start = from < 0 ? 0 : from;
        for (int i = start; i < data.length; i++) {
            if (data[i] == want) {
                return i;
            }
        }
        return -1;
    }

    /**
     * The method counts how many leading prefix bytes match at {@code from}.
     * A null array, a null prefix, or a negative start returns {@code 0}.
     * The count stops at the first mismatch or at the end of either array.
     *
     * @param data The data array is the buffer to compare.
     * @param from The from index is the start of the comparison.
     * @param prefix The prefix is the byte sequence to match.
     * @return The return value is the number of matched bytes.
     */
    private static int prefixLen(byte[] data, int from, byte[] prefix) {
        if (data == null || prefix == null || from < 0) {
            return 0;
        }
        int n = 0;
        while (n < prefix.length && from + n < data.length) {
            if (data[from + n] != prefix[n]) {
                break;
            }
            n++;
        }
        return n;
    }

    /**
     * The method writes an AMWP keepalive on Scope A.
     * The method returns when AMWP is off.
     * The method returns when the connector is not connected.
     * A write failure is ignored.
     * The next read reports a dead channel.
     */
    private void sendKeepalive() {
        if (!amwp || !isConnected()) {
            return;
        }
        try {
            synchronized (writeLock) {
                AmwpCodec.write(invertedIn, AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_KEEPALIVE, 0,
                        new byte[0]);
            }
        } catch (IOException ignored) {
            // next read will surface the dead channel
        }
    }

    /**
     * The method reports whether the AMWP handshake succeeded.
     *
     * @return The return value is true when {@link #enterMux} has run.
     */
    public boolean isAmwp() {
        return amwp;
    }

    /**
     * The method returns the server facts taken from an Init frame.
     * The value stays null until a server answers with Init.
     * {@link org.aetheris.wire.AmwpToon#decodeFacts} does not report an empty body.
     *
     * @return The return value is the stored facts, or null.
     */
    public AmwpToon.SessionFacts sessionFacts() {
        return sessionFacts;
    }

    /**
     * The method stores the Scope A listener.
     * A null listener holds later frames on this session.
     * A non-null listener receives every frame that was held while it was away.
     * Those held frames are cleared before they are delivered.
     *
     * @param listener The listener is the Scope A sink, or null to hold frames.
     */
    public void setAmwpListener(AmwpClientListener listener) {
        ArrayList<AmwpFrame> drained = new ArrayList<AmwpFrame>();
        synchronized (amwpLock) {
            this.amwpListener = listener;
            if (listener != null) {
                drained.addAll(heldFrames);
                heldFrames.clear();
            }
        }
        for (int i = 0; i < drained.size(); i++) {
            listener.onAmwpFrame(drained.get(i));
        }
    }

    /**
     * The method delivers one Scope A frame, or holds it until a listener is stored.
     * A missing listener appends the frame when fewer than 64 frames are held.
     * A frame past that cap is dropped.
     * A stored listener receives the frame outside the lock.
     *
     * @param frame The frame is a non-keepalive frame that is not on Scope T.
     */
    private void deliverOrHold(AmwpFrame frame) {
        AmwpClientListener listener;
        synchronized (amwpLock) {
            listener = amwpListener;
            if (listener == null) {
                if (heldFrames.size() < HELD_FRAME_CAP) {
                    heldFrames.addLast(frame);
                }
                return;
            }
        }
        listener.onAmwpFrame(frame);
    }

    /**
     * The method writes one Scope A frame.
     * The channel is the Scope A channel.
     * Raw mode does not write the frame.
     * Raw mode throws {@link IOException}.
     *
     * @param type The type is the AMWP frame type.
     * @param payload The payload is the frame body.
     * @throws IOException The exception is thrown when AMWP was not negotiated or the write fails.
     */
    public void writeAmwp(int type, byte[] payload) throws IOException {
        writeAmwp(AmwpConstants.CHANNEL_A, type, payload);
    }

    /**
     * The method writes one AMWP frame on the given channel.
     * Scope A execute stays on channel {@code 0x0002}.
     * Tool frames use channel {@code 0x0004}.
     * The method throws {@link IOException} with the text {@code AMWP not negotiated} when AMWP is off.
     * The write is serialized on the write lock.
     *
     * @param channel The channel is the AMWP channel number.
     * @param type The type is the AMWP frame type.
     * @param payload The payload is the frame body.
     * @throws IOException The exception is thrown when AMWP is off or the write fails.
     */
    public void writeAmwp(int channel, int type, byte[] payload) throws IOException {
        if (!amwp) {
            throw new IOException("AMWP not negotiated");
        }
        synchronized (writeLock) {
            AmwpCodec.write(invertedIn, channel, type, 0, payload);
        }
    }

    /**
     * The method reads characters for JediTerm from the current reader.
     * In raw mode the reader is the shell output.
     * In AMWP mode the reader is the Scope T queue.
     *
     * @param buf The buffer receives the characters.
     * @param offset The offset is the first index to fill.
     * @param length The length is the maximum number of characters to read.
     * @return The return value is the count from the reader, or {@code -1} at the end of the stream.
     * @throws IOException The exception is thrown when the reader throws.
     */
    @Override
    public int read(char[] buf, int offset, int length) throws IOException {
        return reader.read(buf, offset, length);
    }

    /**
     * The method writes terminal bytes to the shell.
     * A null array returns without a write.
     * An empty array returns without a write.
     * AMWP mode wraps the bytes as a Scope T data frame.
     * Raw mode writes the bytes to the inverted input and flushes that stream.
     *
     * @param bytes The bytes are the terminal payload.
     * @throws IOException The exception is thrown when the write fails.
     */
    @Override
    public synchronized void write(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            return;
        }
        synchronized (writeLock) {
            if (amwp) {
                AmwpCodec.write(invertedIn, AmwpConstants.CHANNEL_T, AmwpConstants.TYPE_DATA, 0, bytes);
            } else {
                invertedIn.write(bytes);
                invertedIn.flush();
            }
        }
    }

    /**
     * The method encodes the string as UTF-8 and writes those bytes.
     *
     * @param string The string is the text to write.
     * @throws IOException The exception is thrown when {@link #write(byte[])} throws.
     */
    @Override
    public synchronized void write(String string) throws IOException {
        write(string.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * The method reports whether the shell is still open.
     * A closed connector returns false.
     * Otherwise both the channel and the session must be open.
     *
     * @return The return value is true only when the connector is open and both SSH objects are open.
     */
    @Override
    public boolean isConnected() {
        if (closed.get()) {
            return false;
        }
        return channel.isOpen() && session.isOpen();
    }

    /**
     * The method tells the server the new terminal size.
     * A null size returns without a request.
     * A connector that is not connected returns without a request.
     * A failed window-change write is ignored.
     * A dead channel shows up on the next read.
     *
     * @param termSize The term size is the column and row count JediTerm wants.
     */
    @Override
    public void resize(TermSize termSize) {
        if (termSize == null || !isConnected()) {
            return;
        }
        try {
            channel.sendWindowChange(termSize.getColumns(), termSize.getRows());
        } catch (IOException ex) {
            // Resize is best-effort; a dead channel will surface on the next read.
        }
    }

    /**
     * The method waits until the shell channel is closed.
     * The wait uses no extra timeout.
     * A missing exit status returns {@code 0}.
     *
     * @return The return value is the channel exit status, or {@code 0} when the status is absent.
     * @throws InterruptedException The exception is thrown when the wait is interrupted.
     */
    @Override
    public int waitFor() throws InterruptedException {
        channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);
        Integer status = channel.getExitStatus();
        if (status == null) {
            return 0;
        }
        return status.intValue();
    }

    /**
     * The method reports whether the current reader has characters waiting.
     *
     * @return The return value is the reader's ready flag.
     * @throws IOException The exception is thrown when the reader throws.
     */
    @Override
    public boolean ready() throws IOException {
        return reader.ready();
    }

    /**
     * The method returns the display name stored by the constructor.
     *
     * @return The return value is that name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * The method closes the connector once.
     * A second call returns immediately.
     * The keepalive timer is cancelled when one exists.
     * An empty chunk is queued so a blocked Scope T read can finish.
     * The demux thread is interrupted when it exists.
     * {@link #closeQuietly} then closes the channel, the session, and the client.
     */
    @Override
    public void close() {
        if (!closed.compareAndSet(false, true)) {
            return;
        }
        if (keepaliveTimer != null) {
            keepaliveTimer.cancel();
        }
        tChunks.offer(new byte[0]);
        if (demuxThread != null) {
            demuxThread.interrupt();
        }
        closeQuietly();
    }

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    /**
     * The {@code ScopeTStream} is the input stream of concatenated Scope T payloads for JediTerm.
     * An empty chunk is skipped while the connector is open.
     * An empty chunk after close ends the stream.
     */
    private final class ScopeTStream extends InputStream {
        private byte[] cur = new byte[0];
        private int pos;

        /**
         * The method returns the next Scope T byte.
         * The end of the stream returns {@code -1}.
         *
         * @return The return value is the unsigned byte, or {@code -1}.
         * @throws IOException The exception is declared for {@link InputStream} and is not thrown by the queue read.
         */
        @Override
        public int read() throws IOException {
            if (!fill()) {
                return -1;
            }
            int b = cur[pos] & 0xFF;
            pos++;
            return b;
        }

        /**
         * The method copies the next available Scope T bytes into the buffer.
         * The copy stops at the end of the current chunk.
         * The end of the stream returns {@code -1}.
         *
         * @param buf The buffer receives the bytes.
         * @param off The off index is the first index to fill.
         * @param len The len value is the maximum number of bytes to copy.
         * @return The return value is the number of bytes copied, or {@code -1}.
         * @throws IOException The exception is declared for {@link InputStream}.
         */
        @Override
        public int read(byte[] buf, int off, int len) throws IOException {
            if (!fill()) {
                return -1;
            }
            int n = Math.min(len, cur.length - pos);
            System.arraycopy(cur, pos, buf, off, n);
            pos += n;
            return n;
        }

        /**
         * The method loads the next non-empty Scope T chunk.
         * A null chunk ends the stream.
         * An empty chunk after close ends the stream.
         * An empty chunk before close is skipped.
         * An interrupt sets the thread interrupt flag and ends the stream.
         *
         * @return The return value is true when a chunk is ready to read.
         * @throws IOException The exception is declared and is not thrown by this method.
         */
        private boolean fill() throws IOException {
            while (pos >= cur.length) {
                try {
                    byte[] next = tChunks.take();
                    if (next == null) {
                        return false;
                    }
                    if (next.length == 0 && closed.get()) {
                        return false;
                    }
                    if (next.length == 0) {
                        continue;
                    }
                    cur = next;
                    pos = 0;
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * The method closes the channel, the session, the client, the reader, and the inverted input.
     * Each close swallows its own error.
     * The client is stopped rather than closed.
     */
    private void closeQuietly() {
        try {
            channel.close(false);
        } catch (Exception ignored) {
            // already closing
        }
        try {
            session.close(false);
        } catch (Exception ignored) {
            // already closing
        }
        try {
            client.stop();
        } catch (Exception ignored) {
            // already stopping
        }
        try {
            reader.close();
        } catch (IOException ignored) {
            // ignore
        }
        try {
            invertedIn.close();
        } catch (IOException ignored) {
            // ignore
        }
    }
}
/*****************************************************************************************
 * End of Class: SshTtyConnector.java 082426
 ****************************************************************************************/
