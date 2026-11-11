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
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 */
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
            // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
            // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
            // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
            // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
            // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
            // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
