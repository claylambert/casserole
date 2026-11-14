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

package org.casserole.ssh;

import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.ShellFactory;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code SshServerTest} class is a local Apache MINA listener that pumps bytes between the SSH streams and a PTY.
 * A PTY is a pseudo-terminal. This class is not the Spring bean.
 * // @aa-sdd L40 aa-sdd-source-prose.md#SshServerTest
 */
// @aa-sdd PK-477 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-478 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd GK-1263 aetheris-plan-stage-0.001.md
// @aa-sdd GK-1406 aetheris-pre-plan-stage-1.001.md
// @aa-sdd PK-480 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-722 docs/specs/common/server.spec.md
// @aa-sdd PK-481 docs/specs/java/org/casserole/ssh/ssh.spec.md
public class SshServerTest {

    /**
     * The method starts the listener and waits until the process is interrupted.
     * The port is {@code 2222}. The host key file is {@code hostkey.ser}. The only accepted name is {@code clay} and the only accepted password is {@code butter}. The shell is a PTY running {@code ollama run qwen2.5-coder:7b}.
     *
     * The method prints the port, the connect line {@code ssh -p 2222 clay@localhost}, and the password line. An interrupt sets the interrupt flag again.
     *
     * @param args The arguments are unused.
     * @throws IOException The method throws this exception when the listener cannot start.
     */
    public static void main(String[] args) throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(2222);

        // Host key
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(
                Paths.get("hostkey.ser")));

        // Password auth
        /**
         * The authenticator accepts only the name {@code clay} with the password {@code butter}.
         * Any other pair returns false.
         */
        sshd.setPasswordAuthenticator(new PasswordAuthenticator() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshServerTest
            /**
             * The method accepts only the name {@code clay} with the password {@code butter}.
             *
             * @param username The username is the offered name.
             * @param password The password is the offered password.
             * @param session The session is the server session. The method does not read it.
             * @return The return value is true only for that name and password.
             */
            @Override
            public boolean authenticate(String username, String password, ServerSession session) {
                return "clay".equals(username) && "butter".equals(password);
            }
        });

        // Use pty4j for a real PTY (fixes echo)
        sshd.setShellFactory(new PtyShellFactory());

        sshd.start();
        System.out.println("SSH server started on port " + sshd.getPort());
        System.out.println("Connect with: ssh -p 2222 clay@localhost");
        System.out.println("Password: butter");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // @aa-sdd L40 aa-sdd-source-prose.md#SshServerTest
    /**
     * The {@code PtyShellFactory} class builds one {@link PtyCommand} for each channel.
     * pty4j is the library that creates the pseudo-terminal.
     */
    static class PtyShellFactory implements ShellFactory {
        /**
         * The method returns a new PTY command.
         *
         * @param channel The channel is the SSH channel that needs a shell.
         * @return The return value is the PTY command.
         */
        @Override
        public Command createShell(ChannelSession channel) {
            return new PtyCommand();
        }
    }

    // @aa-sdd L40 aa-sdd-source-prose.md#SshServerTest
    /**
     * The {@code PtyCommand} class launches {@code ollama run qwen2.5-coder:7b} inside a pty4j pseudo-terminal.
     * One thread copies SSH input into the PTY. The command thread copies PTY output back to SSH.
     */
    static class PtyCommand implements Command, Runnable {

        private InputStream in;
        private OutputStream out;
        private OutputStream err;
        private ExitCallback callback;
        private PtyProcess process;
        private Thread pumpThread;

        /**
         * The method stores the input stream copied into the PTY.
         *
         * @param in The input stream is the SSH input for this channel.
         */
        @Override
        public void setInputStream(InputStream in) {
            this.in = in;
        }

        /**
         * The method stores the output stream that receives PTY output.
         *
         * @param out The output stream is the SSH output for this channel.
         */
        @Override
        public void setOutputStream(OutputStream out) {
            this.out = out;
        }

        /**
         * The method stores the error stream.
         * The PTY redirects its error stream into its output, so this stream is not written.
         *
         * @param err The error stream is the SSH error output for this channel.
         */
        @Override
        public void setErrorStream(OutputStream err) {
            this.err = err;
        }

        /**
         * The method stores the exit callback.
         *
         * @param callback The callback is the hook that ends the channel.
         */
        @Override
        public void setExitCallback(ExitCallback callback) {
            this.callback = callback;
        }

        /**
         * The method starts {@code ollama run qwen2.5-coder:7b} in a pseudo-terminal and starts the pump thread.
         * The process environment is a copy of the SSH environment. {@code TERM} is set to {@code xterm-256color}. Error output is redirected into the PTY output. Windows ANSI color is enabled. The console flag is false. The pump thread is a daemon named {@code pty-pump}.
         *
         * @param channel The channel is the SSH channel that started this command. The method does not read it.
         * @param env The environment is the SSH environment copied into the process.
         * @throws IOException The method throws this exception when the process cannot start.
         */
        @Override
        public void start(ChannelSession channel, Environment env) throws IOException {
            Map<String, String> environment = new HashMap<>(env.getEnv());

            // Overlay anything the SSH client sent
            environment.putAll(env.getEnv());

            // Force a reasonable terminal type
            environment.put("TERM", "xterm-256color");

            String[] command = {"ollama", "run", "qwen2.5-coder:7b"};

            PtyProcessBuilder builder = new PtyProcessBuilder(command)
                    .setEnvironment(environment)
                    .setRedirectErrorStream(true)
                    .setWindowsAnsiColorEnabled(true)
                    .setConsole(false);

            process = builder.start();

            // Start pumping data between SSH streams and the PTY
            pumpThread = new Thread(this, "pty-pump");
            pumpThread.setDaemon(true);
            pumpThread.start();
        }

        /**
         * The method destroys the process and interrupts the pump thread.
         * A missing process or a missing thread is skipped.
         *
         * @param channel The channel is the SSH channel being closed.
         */
        @Override
        public void destroy(ChannelSession channel) {
            if (process != null) {
                process.destroy();
            }
            if (pumpThread != null) {
                pumpThread.interrupt();
            }
        }

        /**
         * The method copies SSH input into the PTY and copies PTY output back to SSH.
         * The input copy runs on a daemon thread named {@code ssh-to-pty} and uses an {@code 8192} byte buffer. The output copy uses the same buffer size. When the PTY ends, the exit callback receives the process status. Any other failure uses status {@code 1} and the exception message.
         */
        @Override
        public void run() {
            try {
                // Pump SSH → PTY
                Thread t1 = new Thread(() -> {
                    try {
                        byte[] buf = new byte[8192];
                        int n;
                        while ((n = in.read(buf)) != -1) {
                            process.getOutputStream().write(buf, 0, n);
                            process.getOutputStream().flush();
                        }
                    } catch (Exception ignored) {}
                }, "ssh-to-pty");
                t1.setDaemon(true);
                t1.start();

                // Pump PTY → SSH
                byte[] buf = new byte[8192];
                int n;
                while ((n = process.getInputStream().read(buf)) != -1) {
                    out.write(buf, 0, n);
                    out.flush();
                }

                int exitCode = process.waitFor();
                callback.onExit(exitCode);

            } catch (Exception e) {
                callback.onExit(1, e.getMessage());
            }
        }
    }
}