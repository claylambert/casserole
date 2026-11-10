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

import javax.swing.SwingUtilities;

// @aa-sdd PK-3727 PK-3764 PK-4153 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0149 PK-0273 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0159 PK-0282 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-4158 docs/specs/java/org/casserole/mood/mood.spec.md
// @aa-sdd PK-3600 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
/**
 * The {@code TerminalSession} type is one SSH shell and one JediTerm canvas.
 * The name SSH means the secure shell.
 * The name JediTerm means the terminal widget that draws the shell.
 * The frame keeps a tree of these sessions.
 * The {@link Host} type is the frame callback.
 * The callback runs on the Swing event thread.
 * The name Swing means the Java library that owns that thread.
 * The disconnect clears the live connector.
 * The disconnect sets the mood-told flag to false.
 * The error path does the same clear.
 * The stored mood line is not cleared by those paths.
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
 * @author clayl (2026-08-26)
 */
public class TerminalSession {

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The {@code Host} type is the frame callback for one session.
     * The callback runs on the Swing event dispatch thread.
     * The session hops onto that thread when the worker is on another thread.
     */
    public interface Host {

        /**
         * The sessionConnecting method reports that a connect attempt has started.
         *
         * @param session The session that changed.
         */
        void sessionConnecting(TerminalSession session);

        /**
         * The sessionConnected method reports that the shell channel is open.
         * The connector is the live TTY, the terminal stream wired to JediTerm.
         *
         * @param session The session that changed.
         * @param connector The live TTY connector.
         * @param host The connected host.
         * @param port The connected port.
         */
        void sessionConnected(TerminalSession session, SshTtyConnector connector, String host, int port);

        /**
         * The sessionDisconnected method reports that the session is gone.
         * The connector is the one just cleared, or null when none was stored.
         * The message is the reason, or null when no reason was given.
         *
         * @param session The session that changed.
         * @param connector The connector just cleared. The value may be null.
         * @param message The reason. The value may be null.
         */
        void sessionDisconnected(TerminalSession session, SshTtyConnector connector, String message);

        /**
         * The sessionError method reports that connect or authentication failed.
         * The connector is the one just cleared, or null when none was stored.
         *
         * @param session The session that changed.
         * @param connector The connector just cleared. The value may be null.
         * @param message The error text.
         */
        void sessionError(TerminalSession session, SshTtyConnector connector, String message);
    }

    private final SshTerminalPanel panel;
    private final SshSessionController controller;
    private SshConnectionState state;
    private SshSessionController.ConnectRequest lastRequest;
    private SshTtyConnector liveConnector;
    /**
     * The moodHeld field is the console mood line last stored for this pane.
     * The empty string means no line is stored.
     */
    private String moodHeld = "";
    /**
     * The moodTold field records whether the announce frame was accepted.
     * The disconnect sets the flag to false.
     * The error path sets the flag to false.
     */
    private boolean moodTold;

    /**
     * The constructor builds a disconnected session and wires the controller listener.
     * The state starts as {@link SshConnectionState#DISCONNECTED}.
     * The connecting callback stores that state, then runs {@link Host#sessionConnecting} on the event thread.
     * The connected callback stores the connector and {@link SshConnectionState#CONNECTED}, then runs {@link Host#sessionConnected}.
     * The disconnected callback clears the connector, sets the mood-told flag to false, stores {@link SshConnectionState#DISCONNECTED}, and runs {@link Host#sessionDisconnected}.
     * The error callback does that same clear and runs {@link Host#sessionError}.
     * The mood line is left as it was on both of those clears.
     *
     * @param settings The shared font and colors.
     * @param theme The dark painter for the thinking strip.
     * @param host The frame callback.
     */
    public TerminalSession(DarkJediTermSettings settings, SshClientTheme theme, final Host host) {
        this.panel = new SshTerminalPanel(settings, theme);
        this.state = SshConnectionState.DISCONNECTED;
        final TerminalSession self = this;
        /**
         * The listener records the connection state and forwards each result to the host.
         * The forward runs on the Swing event thread.
         */
        this.controller = new SshSessionController(new SshSessionController.Listener() {
            // @aa-sdd PK-4159 docs/specs/java/org/casserole/mood/mood.spec.md
            @Override
            public void onConnecting() {
                self.state = SshConnectionState.CONNECTING;
                /**
                 * The task tells the host that this session is connecting.
                 */
                hop(new Runnable() {
                    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
                    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
                    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
                    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
                    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
                    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
                    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
                    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
                    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
                    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
                    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
                    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
                    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
                    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
                    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
                    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
                    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
                    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
                    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
                    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
                    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
                    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
                    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
                    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
                    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
                    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
                    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
                    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
                    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
                    @Override
                    public void run() {
                        host.sessionConnecting(self);
                    }
                });
            }

            @Override
            public void onConnected(final SshTtyConnector connector, final String hostName, final int port) {
                self.liveConnector = connector;
                self.state = SshConnectionState.CONNECTED;
                /**
                 * The task tells the host that this session is connected.
                 */
                hop(new Runnable() {
                    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
                    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
                    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
                    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
                    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
                    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
                    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
                    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
                    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
                    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
                    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
                    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
                    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
                    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
                    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
                    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
                    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
                    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
                    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
                    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
                    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
                    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
                    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
                    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
                    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
                    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
                    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
                    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
                    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
                    @Override
                    public void run() {
                        host.sessionConnected(self, connector, hostName, port);
                    }
                });
            }

            @Override
            public void onDisconnected(final String message) {
                final SshTtyConnector gone = self.liveConnector;
                self.liveConnector = null;
                self.moodTold = false;
                self.state = SshConnectionState.DISCONNECTED;
                /**
                 * The task tells the host that this session disconnected.
                 */
                hop(new Runnable() {
                    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
                    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
                    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
                    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
                    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
                    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
                    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
                    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
                    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
                    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
                    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
                    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
                    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
                    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
                    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
                    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
                    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
                    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
                    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
                    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
                    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
                    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
                    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
                    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
                    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
                    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
                    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
                    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
                    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
                    @Override
                    public void run() {
                        host.sessionDisconnected(self, gone, message);
                    }
                });
            }

            @Override
            public void onError(final String message) {
                final SshTtyConnector gone = self.liveConnector;
                self.liveConnector = null;
                self.moodTold = false;
                self.state = SshConnectionState.DISCONNECTED;
                /**
                 * The task tells the host that this session failed.
                 */
                hop(new Runnable() {
                    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
                    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
                    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
                    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
                    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
                    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
                    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
                    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
                    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
                    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
                    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
                    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
                    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
                    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
                    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
                    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
                    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
                    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
                    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
                    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
                    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
                    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
                    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
                    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
                    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
                    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
                    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
                    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
                    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
                    @Override
                    public void run() {
                        host.sessionError(self, gone, message);
                    }
                });
            }
        });
    }

    /**
     * The moodHeld method returns the mood line last stored on this pane.
     * The method returns a null field as an empty string.
     * The empty string means no line is stored.
     *
     * @return The return value is that line, or an empty string.
     */
    public String moodHeld() {
        return moodHeld == null ? "" : moodHeld;
    }

    /**
     * The setMoodHeld method stores the mood line for this pane.
     * The method stores a null line as an empty string.
     *
     * @param line The line to store. The empty string clears the stored line. The value may be null.
     */
    public void setMoodHeld(String line) {
        moodHeld = line == null ? "" : line;
    }

    /**
     * The moodTold method reports whether the announce frame was accepted.
     *
     * @return The return value is true when the frame was accepted.
     */
    public boolean moodTold() {
        return moodTold;
    }

    /**
     * The setMoodTold method stores whether the announce frame was accepted.
     *
     * @param told The flag is true when the announce frame was accepted.
     */
    public void setMoodTold(boolean told) {
        moodTold = told;
    }

    /**
     * The getPanel method returns the terminal panel for this session.
     * The panel is the JediTerm surface created in the constructor.
     *
     * @return The return value is that panel.
     */
    public SshTerminalPanel getPanel() {
        return panel;
    }

    /**
     * The getController method returns the session controller.
     * The controller opens and closes the SSH connection off the event thread.
     *
     * @return The return value is that controller.
     */
    public SshSessionController getController() {
        return controller;
    }

    /**
     * The getState method returns the connection state stored by the listener.
     *
     * @return The return value is the current {@link SshConnectionState}.
     */
    public SshConnectionState getState() {
        return state;
    }

    /**
     * The getLastRequest method returns the last connect snapshot stored for this session.
     * The result is null until {@link #setLastRequest} stores one.
     *
     * @return The return value is that snapshot, or null.
     */
    public SshSessionController.ConnectRequest getLastRequest() {
        return lastRequest;
    }

    /**
     * The setLastRequest method stores the connect snapshot.
     * The method stores a null snapshot.
     *
     * @param request The snapshot from the last connect. The value may be null.
     */
    public void setLastRequest(SshSessionController.ConnectRequest request) {
        this.lastRequest = request;
    }

    /**
     * The getLiveConnector method returns the connector stored while the session is connected.
     * The result is null before connect and after a disconnect or an error.
     *
     * @return The return value is the live connector, or null.
     */
    public SshTtyConnector getLiveConnector() {
        return liveConnector;
    }

    /**
     * The dispose method tears down the SSH connection and then the widget.
     * The controller disconnects and the caller waits for that close.
     * The panel then disposes the JediTerm widget.
     */
    public void dispose() {
        controller.disconnectAndWait();
        panel.disposeWidget();
    }

    /**
     * The hop method runs a task on the Swing event dispatch thread.
     * The method runs the task immediately when the caller is already on that thread.
     * The method schedules the task with {@code invokeLater} on every other thread.
     *
     * @param task The work that must run on the event thread.
     */
    private static void hop(Runnable task) {
        if (SwingUtilities.isEventDispatchThread()) {
            task.run();
        } else {
            SwingUtilities.invokeLater(task);
        }
    }
}
/*****************************************************************************************
 * End of Class: TerminalSession.java 082626
 ****************************************************************************************/
