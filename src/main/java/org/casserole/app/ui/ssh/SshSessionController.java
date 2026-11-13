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

import java.io.IOException;
import java.time.Duration;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelShell;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;
import org.apache.sshd.common.channel.ChannelListener;
import org.apache.sshd.common.channel.PtyChannelConfiguration;
import org.apache.sshd.common.channel.PtyMode;
import org.apache.sshd.core.CoreModuleProperties;

/**
 * The {@code SshSessionController} connects, disconnects, and reconnects one SSH session on a worker thread.
 * The network work stays off the Swing event thread.
 * The worker starts an Apache MINA SSHD client, which is the library that opens the SSH session.
 * The same worker authenticates that session and opens a shell channel.
 * A {@link Listener} receives the result.
 * {@link TerminalSession} moves that result onto the Swing event thread.
 * {@link #disconnect} does not open another session.
 * A remote close does not open another session either.
 * // @aa-sdd L40 aa-sdd-source-prose.md#SshSessionController
 *
 * @author clayl (2026-08-24)
 */
// @aa-sdd GK-1362 aetheris-pre-plan-stage-0.001.md
// @aa-sdd PK-264 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-268 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-273 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-278 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-284 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
public class SshSessionController {

    private static final Duration VERIFY_TIMEOUT = Duration.ofSeconds(15);

    private final AtomicReference<SshConnectionState> state;
    private final AtomicReference<SshTtyConnector> connectorRef;
    private final AtomicBoolean userDisconnect;
    private final Object workerLock;

    // @aa-sdd L40 aa-sdd-source-prose.md#SshSessionController
    /**
     * The {@code Listener} receives the results of one session.
     * {@link #connect} calls {@link #onConnecting} on the caller thread.
     * The other callbacks run on the worker thread.
     */
    // @aa-sdd PK-278 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    public interface Listener {

        /**
         * The method reports that a connect attempt is starting.
         * The state is already {@link SshConnectionState#CONNECTING} when this method runs.
         */
        void onConnecting();

        /**
         * The method reports that the shell channel is open.
         * JediTerm is the terminal widget that can attach to the connector.
         *
         * @param connector The connector is the live {@link SshTtyConnector} for this session.
         * @param host The host is the remote server address used for this connection.
         * @param port The port is the remote TCP port used for this connection.
         */
        void onConnected(SshTtyConnector connector, String host, int port);

        /**
         * The method reports that the session is gone.
         * A null message means the caller did not supply a reason.
         * {@link SshSessionController#disconnect} passes null.
         * A connect that stops before the channel stays open also passes null.
         *
         * @param message The message is the reason text, or null when the caller has no reason text.
         */
        void onDisconnected(String message);

        /**
         * The method reports that the connect or the authentication failed.
         * The controller has already set the state to {@link SshConnectionState#DISCONNECTED}.
         * The window should show that disconnected state.
         *
         * @param message The message is the error text shown to the user.
         */
        void onError(String message);
    }

    // @aa-sdd L40 aa-sdd-source-prose.md#SshSessionController
    /**
     * The {@code ConnectRequest} is a snapshot of the connection fields taken when Connect or Reconnect is pressed.
     * The worker reads this snapshot.
     * The worker does not read the window fields again.
     */
    // @aa-sdd GK-1710 aetheris-stages-1-3.finalize.md
    // @aa-sdd PK-278 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    public static class ConnectRequest {

        private final String host;
        private final int port;
        private final String username;
        private final String password;
        private final SshSessionMode mode;
        private final int columns;
        private final int rows;
        private final long idleTimeoutMs;

        /**
         * The constructor stores a connection snapshot that does not disconnect for idle time.
         * The idle timeout is {@code 0}.
         *
         * @param host The host is the remote server address.
         * @param port The port is the remote TCP port.
         * @param username The username is the SSH account name.
         * @param password The password is the SSH password for that account.
         * @param mode The mode is the {@link SshSessionMode} that chooses VT100 or standard PTY.
         * @param columns The columns value is the initial PTY width in characters.
         * @param rows The rows value is the initial PTY height in characters.
         */
        public ConnectRequest(String host, int port, String username, String password,
                SshSessionMode mode, int columns, int rows) {
            this(host, port, username, password, mode, columns, rows, 0L);
        }

        /**
         * The constructor stores every field of one connection snapshot.
         * A later read returns these same values.
         *
         * @param host The host is the remote server address.
         * @param port The port is the remote TCP port.
         * @param username The username is the SSH account name.
         * @param password The password is the SSH password for that account.
         * @param mode The mode is the {@link SshSessionMode} that chooses VT100 or standard PTY.
         * @param columns The columns value is the initial PTY width in characters.
         * @param rows The rows value is the initial PTY height in characters.
         * @param idleTimeoutMs The idle timeout is the idle limit in milliseconds, and {@code 0} means the client does not disconnect for idle time.
         */
        public ConnectRequest(String host, int port, String username, String password,
                SshSessionMode mode, int columns, int rows, long idleTimeoutMs) {
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
            this.mode = mode;
            this.columns = columns;
            this.rows = rows;
            this.idleTimeoutMs = idleTimeoutMs;
        }

        /**
         * The method returns the remote server address stored on this request.
         *
         * @return The return value is the host string passed to the constructor.
         */
        public String getHost() {
            return host;
        }

        /**
         * The method returns the remote TCP port stored on this request.
         *
         * @return The return value is the port passed to the constructor.
         */
        public int getPort() {
            return port;
        }

        /**
         * The method returns the SSH account name stored on this request.
         *
         * @return The return value is the username passed to the constructor.
         */
        public String getUsername() {
            return username;
        }

        /**
         * The method returns the SSH password stored on this request.
         *
         * @return The return value is the password passed to the constructor.
         */
        public String getPassword() {
            return password;
        }

        /**
         * The method returns the session mode stored on this request.
         * The mode chooses VT100 or standard PTY.
         *
         * @return The return value is the {@link SshSessionMode} passed to the constructor.
         */
        public SshSessionMode getMode() {
            return mode;
        }

        /**
         * The method returns the initial PTY width stored on this request.
         * The width is a count of characters.
         *
         * @return The return value is the column count passed to the constructor.
         */
        public int getColumns() {
            return columns;
        }

        /**
         * The method returns the initial PTY height stored on this request.
         * The height is a count of text lines.
         *
         * @return The return value is the row count passed to the constructor.
         */
        public int getRows() {
            return rows;
        }

        /**
         * The method returns the idle timeout stored on this request.
         * A value of {@code 0} means the client does not disconnect for idle time.
         *
         * @return The return value is the idle timeout in milliseconds.
         */
        public long getIdleTimeoutMs() {
            return idleTimeoutMs;
        }
    }

    private final Listener listener;

    /**
     * The constructor stores the listener and starts in {@link SshConnectionState#DISCONNECTED}.
     * No connector is stored.
     * The user-disconnect flag starts clear.
     *
     * @param listener The listener receives the connect and close results.
     */
    public SshSessionController(Listener listener) {
        this.listener = listener;
        this.state = new AtomicReference<SshConnectionState>(SshConnectionState.DISCONNECTED);
        this.connectorRef = new AtomicReference<SshTtyConnector>();
        this.userDisconnect = new AtomicBoolean(false);
        this.workerLock = new Object();
    }

    /**
     * The method returns the connection state.
     * The value is {@link SshConnectionState#DISCONNECTED}, {@link SshConnectionState#CONNECTING}, or {@link SshConnectionState#CONNECTED}.
     *
     * @return The return value is the state stored on this controller.
     */
    public SshConnectionState getState() {
        return state.get();
    }

    /**
     * The method starts a connect on a worker thread.
     * The method returns at once when the state is not {@link SshConnectionState#DISCONNECTED}.
     * A start clears the user-disconnect flag.
     * The method calls {@link Listener#onConnecting} on the caller thread.
     * The worker thread is named {@code ssh-client-connect}.
     *
     * @param request The request is the connection snapshot the worker uses.
     */
    public void connect(ConnectRequest request) {
        if (!state.compareAndSet(SshConnectionState.DISCONNECTED, SshConnectionState.CONNECTING)) {
            return;
        }
        userDisconnect.set(false);
        listener.onConnecting();
        /**
         * The runnable calls {@link #doConnect} for this request.
         * The call runs on the thread named {@code ssh-client-connect}.
         */
        startWorker("ssh-client-connect", new Runnable() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshSessionController
            @Override
            public void run() {
                doConnect(request);
            }
        });
    }

    /**
     * The method closes any live session on a worker thread.
     * The thread is named {@code ssh-client-disconnect}.
     * The close does not open another session.
     */
    public void disconnect() {
        /**
         * The runnable marks the close as a user disconnect and drops the live connector.
         * The state becomes {@link SshConnectionState#DISCONNECTED}.
         * {@link Listener#onDisconnected} is called with null.
         */
        startWorker("ssh-client-disconnect", new Runnable() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshSessionController
            @Override
            public void run() {
                userDisconnect.set(true);
                closeCurrentConnector();
                state.set(SshConnectionState.DISCONNECTED);
                listener.onDisconnected(null);
            }
        });
    }

    /**
     * The method closes any live session and then connects with the supplied snapshot.
     * The work runs on a thread named {@code ssh-client-reconnect}.
     *
     * @param request The request is the connection snapshot used for the new connect.
     */
    public void reconnect(ConnectRequest request) {
        /**
         * The runnable closes the live connector and then opens the session in the request.
         * The user-disconnect flag is set only while the old connector closes.
         * The flag is cleared before {@link #doConnect} runs, so a later remote close is reported.
         * A disconnected state becomes connecting.
         * A connected state becomes connecting.
         * A state that is already connecting stays connecting.
         * {@link Listener#onConnecting} runs on this worker before {@link #doConnect}.
         */
        startWorker("ssh-client-reconnect", new Runnable() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshSessionController
            @Override
            public void run() {
                userDisconnect.set(true);
                closeCurrentConnector();
                userDisconnect.set(false);
                if (!state.compareAndSet(SshConnectionState.DISCONNECTED, SshConnectionState.CONNECTING)
                        && !state.compareAndSet(SshConnectionState.CONNECTED, SshConnectionState.CONNECTING)) {
                    state.set(SshConnectionState.CONNECTING);
                }
                listener.onConnecting();
                doConnect(request);
            }
        });
    }

    /**
     * The method closes the live connector on the caller thread and waits until that close returns.
     * The method does not start a worker.
     * The method does not call {@link Listener}.
     * {@link TerminalSession#dispose} calls this method, and the window exit path calls dispose.
     * The user-disconnect flag is set first so a remote-close callback from this close is ignored.
     * The state becomes {@link SshConnectionState#DISCONNECTED}.
     */
    public void disconnectAndWait() {
        userDisconnect.set(true);
        closeCurrentConnector();
        state.set(SshConnectionState.DISCONNECTED);
    }

    /**
     * The method opens the SSH session described by the request.
     * The client is an Apache MINA SSHD client.
     * The method checks the request with {@link #validateRequest} before it uses the network.
     * The method accepts every server key.
     * The method applies the idle timeout and then starts the client.
     * The method connects to the host and the port on the request.
     * The connect waits up to 15 seconds.
     * A null password is replaced with an empty string.
     * Authentication waits up to 15 seconds.
     * The shell channel uses a PTY, which is the remote terminal the server allocates.
     * The PTY type comes from the session mode.
     * The environment variable {@code TERM} is that same terminal type.
     * The channel open waits up to 15 seconds.
     *
     * The method closes the channel, the session, and the client when the channel is not open.
     * The method closes those same objects when the state is no longer connecting.
     * Either of those closes stores no connector.
     * If the state is still connecting after that close, the state becomes disconnected.
     * That branch calls {@link Listener#onDisconnected} with null.
     *
     * A successful open stores an {@link SshTtyConnector}.
     * The connector name is the username, an {@code @}, the host, a colon, and the port.
     * The method then calls {@link SshTtyConnector#tryAmwpInit}.
     * A channel listener calls {@link #handleRemoteClose} when the remote side closes the channel.
     * {@link Listener#onConnected} runs after that listener is installed.
     *
     * Any other failure is caught as {@code Exception}.
     * The method closes the channel, the session, and the client.
     * The method clears the connector and sets the state to disconnected.
     * The method calls {@link Listener#onError} with the text from {@link #formatError}.
     *
     * @param request The request is the connection snapshot this attempt uses.
     */
    private void doConnect(ConnectRequest request) {
        SshClient client = null;
        ClientSession session = null;
        ChannelShell channel = null;
        try {
            validateRequest(request);

            client = SshClient.setUpDefaultClient();
            client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
            applyIdleTimeout(client, request.getIdleTimeoutMs());
            client.start();

            session = client.connect(request.getUsername(), request.getHost(), request.getPort())
                    .verify(VERIFY_TIMEOUT)
                    .getSession();
            session.addPasswordIdentity(request.getPassword() == null ? "" : request.getPassword());
            session.auth().verify(VERIFY_TIMEOUT);

            PtyChannelConfiguration ptyConfig = buildPtyConfiguration(request);
            Map<String, String> env = new HashMap<String, String>();
            env.put("TERM", request.getMode().getTermType());

            channel = session.createShellChannel(ptyConfig, env);
            channel.open().verify(VERIFY_TIMEOUT);
            if (!channel.isOpen()
                    || !state.compareAndSet(SshConnectionState.CONNECTING, SshConnectionState.CONNECTED)) {
                closeQuietly(channel, session, client);
                connectorRef.set(null);
                if (state.get() == SshConnectionState.CONNECTING) {
                    state.set(SshConnectionState.DISCONNECTED);
                    listener.onDisconnected(null);
                }
                return;
            }

            final SshTtyConnector connector = new SshTtyConnector(
                    request.getUsername() + "@" + request.getHost() + ":" + request.getPort(),
                    client,
                    session,
                    channel);
            connector.tryAmwpInit();
            connectorRef.set(connector);

            /**
             * The listener reports a remote close of the shell channel.
             * {@link #handleRemoteClose} receives the close reason, which may be null.
             */
            channel.addChannelListener(new ChannelListener() {
                // @aa-sdd L40 aa-sdd-source-prose.md#SshSessionController
                @Override
                public void channelClosed(Channel ch, Throwable reason) {
                    handleRemoteClose(reason);
                }
            });

            listener.onConnected(connector, request.getHost(), request.getPort());
        } catch (Exception ex) {
            closeQuietly(channel, session, client);
            connectorRef.set(null);
            state.set(SshConnectionState.DISCONNECTED);
            listener.onError(formatError(ex));
        }
    }

    /**
     * The method handles a close that the remote side started.
     * The method returns without a callback when the user-disconnect flag is set.
     * The method returns without a callback when the state is already {@link SshConnectionState#DISCONNECTED}.
     * Otherwise the method closes the live connector.
     * The state becomes disconnected.
     * A null reason uses the text {@code Connection closed by remote host.}
     * Any other reason uses {@code Connection closed: } plus {@code reason.getMessage()}.
     *
     * @param reason The reason is the SSHD close cause, and it may be null.
     */
    private void handleRemoteClose(Throwable reason) {
        if (userDisconnect.get()) {
            return;
        }
        if (state.get() == SshConnectionState.DISCONNECTED) {
            return;
        }
        closeCurrentConnector();
        state.set(SshConnectionState.DISCONNECTED);
        if (reason == null) {
            listener.onDisconnected("Connection closed by remote host.");
        } else {
            listener.onDisconnected("Connection closed: " + reason.getMessage());
        }
    }

    /**
     * The method writes the text as a bracketed paste.
     * The method does not write Enter after the paste.
     * A missing connector throws {@link IOException} with the text {@code Not connected.}
     * A connector that is not connected throws that same exception.
     * A null text is sent as an empty payload.
     * The payload is encoded as UTF-8.
     * The write begins with the bytes {@code ESC[200~}.
     * The write ends with the bytes {@code ESC[201~}.
     *
     * @param text The text is the payload, with newlines already normalized to {@code \n}.
     * @throws IOException The exception is thrown when the connector is missing, the connector is not connected, or the write fails.
     */
    public void sendPaste(String text) throws IOException {
        SshTtyConnector connector = connectorRef.get();
        if (connector == null || !connector.isConnected()) {
            throw new IOException("Not connected.");
        }
        if (text == null) {
            text = "";
        }
        byte[] start = "\u001B[200~".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        byte[] payload = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        byte[] end = "\u001B[201~".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        byte[] wrapped = new byte[start.length + payload.length + end.length];
        System.arraycopy(start, 0, wrapped, 0, start.length);
        System.arraycopy(payload, 0, wrapped, start.length, payload.length);
        System.arraycopy(end, 0, wrapped, start.length + payload.length, end.length);
        connector.write(wrapped);
    }

    /**
     * The method sends a bracketed paste and then writes a carriage return.
     * The paste editor calls {@link #sendPaste} and does not call this method.
     * A connector that is gone after the paste does not receive the carriage return.
     *
     * @param text The text is the payload passed to {@link #sendPaste}.
     * @throws IOException The exception is thrown when {@link #sendPaste} throws.
     */
    public void sendPasteAndSubmit(String text) throws IOException {
        sendPaste(text);
        SshTtyConnector connector = connectorRef.get();
        if (connector != null && connector.isConnected()) {
            connector.write("\r");
        }
    }

    /**
     * The method closes the live connector when one is stored.
     * The stored connector is cleared before the close runs.
     * A missing connector is left alone.
     */
    private void closeCurrentConnector() {
        SshTtyConnector connector = connectorRef.getAndSet(null);
        if (connector != null) {
            connector.close();
        }
    }

    /**
     * The method sets the idle timeout on the SSH client.
     * A timeout of {@code 0} or less becomes a zero duration, and that duration does not disconnect for idle time.
     * A positive timeout is that many milliseconds.
     * The value is stored as the MINA {@code IDLE_TIMEOUT} on the client.
     * // @aa-sdd G-23 aetheris-stages-1-3.finalize.md
     *
     * @param client The client is the Apache MINA SSHD client that receives the timeout.
     * @param idleTimeoutMs The idle timeout is the limit in milliseconds, and {@code 0} disables idle disconnect.
     */
    private void applyIdleTimeout(SshClient client, long idleTimeoutMs) {
        Duration idle;
        if (idleTimeoutMs <= 0L) {
            idle = Duration.ZERO;
        } else {
            idle = Duration.ofMillis(idleTimeoutMs);
        }
        CoreModuleProperties.IDLE_TIMEOUT.set(client, idle);
    }

    /**
     * The method builds the PTY type, the size, and the mode map for the shell channel.
     * The PTY type is {@link SshSessionMode#getTermType} for the mode on the request.
     * A column count of {@code 0} or less becomes {@code 80}.
     * A row count of {@code 0} or less becomes {@code 24}.
     * The mode map is always the raw map from {@link #buildRawPtyModes}.
     * ChatShell is the remote shell that does its own echo and line editing.
     * Canonical echo would mangle a bracketed paste.
     * {@code ISIG} would turn Ctrl+C into a PTY signal.
     *
     * @param request The request supplies the mode, the columns, and the rows.
     * @return The return value is the SSHD PTY configuration for the shell channel.
     */
    private PtyChannelConfiguration buildPtyConfiguration(ConnectRequest request) {
        PtyChannelConfiguration config = new PtyChannelConfiguration();
        config.setPtyType(request.getMode().getTermType());
        int columns = request.getColumns() > 0 ? request.getColumns() : 80;
        int rows = request.getRows() > 0 ? request.getRows() : 24;
        config.setPtyColumns(columns);
        config.setPtyLines(rows);
        // Raw: ChatShell does its own echo/line editing. Canonical echo+ISIG
        // mangles bracketed paste and turns Ctrl+C into a PTY signal.
        config.setPtyModes(buildRawPtyModes());
        return config;
    }

    /**
     * The method builds a raw PTY mode map.
     * The map sets {@code ECHO}, {@code ICANON}, {@code ISIG}, {@code IEXTEN}, {@code ECHOE}, {@code ECHOK}, {@code ECHOCTL}, and {@code OPOST} to {@code 0}.
     * The map sets {@code CS8} to {@code 1}.
     * Those flags let the program receive bracketed-paste wrappers and Ctrl+C as data.
     *
     * @return The return value is the mode map passed to the PTY configuration.
     */
    private Map<PtyMode, Integer> buildRawPtyModes() {
        EnumMap<PtyMode, Integer> modes = new EnumMap<PtyMode, Integer>(PtyMode.class);
        modes.put(PtyMode.ECHO, Integer.valueOf(0));
        modes.put(PtyMode.ICANON, Integer.valueOf(0));
        modes.put(PtyMode.ISIG, Integer.valueOf(0));
        modes.put(PtyMode.IEXTEN, Integer.valueOf(0));
        modes.put(PtyMode.ECHOE, Integer.valueOf(0));
        modes.put(PtyMode.ECHOK, Integer.valueOf(0));
        modes.put(PtyMode.ECHOCTL, Integer.valueOf(0));
        modes.put(PtyMode.OPOST, Integer.valueOf(0));
        modes.put(PtyMode.CS8, Integer.valueOf(1));
        return modes;
    }

    /**
     * The method rejects a request that cannot be used on the network.
     * An empty or blank host throws {@link IOException} with the text {@code Host is required.}
     * An empty or blank username throws {@link IOException} with the text {@code Username is required.}
     * A port below {@code 1} or above {@code 65535} throws {@link IOException} with the text {@code Port must be between 1 and 65535.}
     *
     * @param request The request is the snapshot to check.
     * @throws IOException The exception is thrown when the host, the username, or the port is unusable.
     */
    private void validateRequest(ConnectRequest request) throws IOException {
        if (request.getHost() == null || request.getHost().trim().length() == 0) {
            throw new IOException("Host is required.");
        }
        if (request.getUsername() == null || request.getUsername().trim().length() == 0) {
            throw new IOException("Username is required.");
        }
        if (request.getPort() <= 0 || request.getPort() > 65535) {
            throw new IOException("Port must be between 1 and 65535.");
        }
    }

    /**
     * The method turns an exception into a short status string.
     * A null or blank exception message uses {@code Connection failed: } plus the simple class name.
     * Any other message uses {@code Connection failed: } plus that message.
     *
     * @param ex The exception is the failure to describe.
     * @return The return value is the text shown for the failure.
     */
    private String formatError(Exception ex) {
        String message = ex.getMessage();
        if (message == null || message.trim().length() == 0) {
            return "Connection failed: " + ex.getClass().getSimpleName();
        }
        return "Connection failed: " + message;
    }

    /**
     * The method closes a half-open connect attempt and swallows close errors.
     * A non-null channel is closed.
     * A non-null session is closed.
     * A non-null client is stopped.
     * A null argument is skipped.
     *
     * @param channel The channel is the shell channel, and it may be null.
     * @param session The session is the client session, and it may be null.
     * @param client The client is the SSH client, and it may be null.
     */
    private void closeQuietly(ChannelShell channel, ClientSession session, SshClient client) {
        if (channel != null) {
            try {
                channel.close(true);
            } catch (Exception ignored) {
                // ignore
            }
        }
        if (session != null) {
            try {
                session.close(true);
            } catch (Exception ignored) {
                // ignore
            }
        }
        if (client != null) {
            try {
                client.stop();
            } catch (Exception ignored) {
                // ignore
            }
        }
    }

    /**
     * The method starts a daemon worker thread.
     * The thread runs the work while it holds the worker lock.
     * The lock keeps connect, disconnect, and reconnect from using the session at the same time.
     *
     * @param name The name is the thread name.
     * @param runnable The runnable is the work that runs under the lock.
     */
    private void startWorker(String name, final Runnable runnable) {
        /**
         * The runnable holds the worker lock and then runs the supplied work.
         * Connect, disconnect, and reconnect share that lock.
         */
        Thread thread = new Thread(new Runnable() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshSessionController
            @Override
            public void run() {
                synchronized (workerLock) {
                    runnable.run();
                }
            }
        }, name);
        thread.setDaemon(true);
        thread.start();
    }
}
/*****************************************************************************************
 * End of Class: SshSessionController.java 082426
 ****************************************************************************************/
