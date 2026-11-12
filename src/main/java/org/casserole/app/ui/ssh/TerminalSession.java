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

// @aa-sdd PK-4153 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0273 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
// @aa-sdd PK-0282 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
// @aa-sdd PK-4158 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-3600 docs/specs/java/org/aetheris/ssh/ssh.spec.md
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
 * @aa-sdd PK-4166 docs/specs/java/org/aetheris/swing/swing.spec.md
 *
 * @author clayl (2026-08-26)
 */
public class TerminalSession {

    // @aa-sdd PK-4166 docs/specs/java/org/aetheris/swing/swing.spec.md
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
     * The engineLabel field is the selected model for this SSH pane.
     * The empty string means this pane has not reported a model.
     * A canvas cell is not this word.
     * The shell file is the copy that survives a restart. This field is the copy the strip paints.
     */
    private String engineLabel = "";
    /**
     * The engineAsked field records whether this connection has been asked for its saved model.
     * A disconnect clears the flag so the next connection asks again.
     */
    private boolean engineAsked;

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
            // @aa-sdd PK-4159 docs/specs/java/org/aetheris/swing/swing.spec.md
            @Override
            public void onConnecting() {
                self.state = SshConnectionState.CONNECTING;
                /**
                 * The task tells the host that this session is connecting.
                 */
                hop(new Runnable() {
                    // @aa-sdd PK-4166 docs/specs/java/org/aetheris/swing/swing.spec.md
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
                    // @aa-sdd PK-4166 docs/specs/java/org/aetheris/swing/swing.spec.md
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
                    // @aa-sdd PK-4166 docs/specs/java/org/aetheris/swing/swing.spec.md
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
                    // @aa-sdd PK-4166 docs/specs/java/org/aetheris/swing/swing.spec.md
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
     * The engineLabel method returns the selected model for this SSH pane.
     * The method returns a null field as an empty string.
     * The empty string means this pane has not reported a model.
     *
     * @return The return value is that model word, or an empty string.
     */
    public String engineLabel() {
        return engineLabel == null ? "" : engineLabel;
    }

    /**
     * The seatEngineLabel method stores the selected model for this SSH pane.
     * The method stores a null word as an empty string.
     * A canvas cell is not this word.
     *
     * @param label The model word. The value may be null.
     */
    public void seatEngineLabel(String label) {
        engineLabel = label == null ? "" : label;
    }

    /**
     * The engineAsked method reports whether this connection has been asked for its saved model.
     *
     * @return The return value is true when the ask has been sent.
     */
    public boolean engineAsked() {
        return engineAsked;
    }

    /**
     * The markEngineAsked method records that this connection has been asked for its saved model.
     */
    public void markEngineAsked() {
        engineAsked = true;
    }

    /**
     * The clearEngineAsked method lets the next connection ask for its saved model.
     */
    public void clearEngineAsked() {
        engineAsked = false;
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
