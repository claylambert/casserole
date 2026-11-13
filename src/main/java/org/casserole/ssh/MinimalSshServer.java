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
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * The {@code MinimalSshServer} class is a standalone Apache MINA SSH listener on port {@code 2222}.
 * Apache MINA is the library that accepts the SSH connection. This class is not the Spring bean. The Spring listener is {@link org.casserole.ssh.shell.SshServer}.
 *
 * The host key file is {@code hostkey.ser} in the working directory. {@link #main} accepts only the name {@code clay} with the password {@code butter}. The shell echoes typed lines and reports escape sequences.
 * // @aa-sdd L40 aa-sdd-source-prose.md#MinimalSshServer
 */
// @aa-sdd PK-477 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-478 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-973 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-1313 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-974 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-865 docs/specs/common/stages.spec.md
// @aa-sdd PK-1120 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-479 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-1351 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-480 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-722 docs/specs/common/server.spec.md
// @aa-sdd PK-481 docs/specs/java/org/casserole/ssh/ssh.spec.md
public class MinimalSshServer {

    /**
     * The method starts the echo listener and waits until the process is interrupted.
     * The port is {@code 2222}. The host key file is {@code hostkey.ser}. The only accepted name is {@code clay} and the only accepted password is {@code butter}.
     *
     * The method prints the port, the connect line {@code ssh -p 2222 clay@localhost}, and the password line. An interrupt sets the interrupt flag again.
     *
     * @param args The arguments are unused.
     * @throws IOException The method throws this exception when the listener cannot start.
     */
    public static void main(String[] args) throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(2222);

        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(
                Paths.get("hostkey.ser")));

        /**
         * The authenticator accepts only the name {@code clay} with the password {@code butter}.
         * Any other pair returns false.
         */
        sshd.setPasswordAuthenticator(new PasswordAuthenticator() {
            // @aa-sdd L40 aa-sdd-source-prose.md#MinimalSshServer
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

        sshd.setShellFactory(new EchoShellFactory());

        sshd.start();
        System.out.println("SSH Echo Server started on port " + sshd.getPort());
        System.out.println("Connect with: ssh -p 2222 clay@localhost");
        System.out.println("Password: butter");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // @aa-sdd L40 aa-sdd-source-prose.md#MinimalSshServer
    /**
     * The {@code EchoShellFactory} class builds one {@link EchoCommand} for each channel.
     */
    static class EchoShellFactory implements ShellFactory {
        /**
         * The method returns a new echo command.
         *
         * @param channel The channel is the SSH channel that needs a shell.
         * @return The return value is the echo command.
         */
        @Override
        public Command createShell(ChannelSession channel) {
            return new EchoCommand();
        }
    }

    // @aa-sdd L40 aa-sdd-source-prose.md#MinimalSshServer
    /**
     * The {@code EchoCommand} class reads the SSH input, echoes printable characters, and reports escape sequences.
     * Enter runs {@link #onCommand}. The prompt is {@code casserole> }.
     */
    static class EchoCommand implements Command, Runnable {

        private InputStream in;
        private OutputStream out;
        private OutputStream err;
        private ExitCallback callback;
        private Thread thread;

        // Line buffer for normal Enter-based commands
        private final StringBuilder lineBuffer = new StringBuilder();

        // Simple escape sequence buffer
        private final StringBuilder escapeBuffer = new StringBuilder();
        private boolean inEscapeSequence = false;

        /**
         * The method stores the input stream the read loop will use.
         *
         * @param in The input stream is the SSH input for this channel.
         */
        @Override
        public void setInputStream(InputStream in) {
            this.in = in;
        }

        /**
         * The method stores the output stream that {@link #write} uses.
         *
         * @param out The output stream is the SSH output for this channel.
         */
        @Override
        public void setOutputStream(OutputStream out) {
            this.out = out;
        }

        /**
         * The method stores the error stream.
         * The echo path does not write to this stream.
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
         * The method writes the welcome lines and starts the daemon read thread.
         * The thread name is {@code echo-shell}.
         *
         * @param channel The channel is the SSH channel that started this command.
         * @param env The environment is the channel environment. The method does not read it.
         * @throws IOException The method throws this exception when a welcome write fails.
         */
        @Override
        public void start(ChannelSession channel, Environment env) throws IOException {
            write("Welcome to Casserole Command Shell\r\n");
            write("• Type a command and press Enter\r\n");
            write("• Special keys / Escape sequences are trapped immediately\r\n");
            write("• Type 'exit' to quit\r\n\r\n");
            write("casserole> ");

            thread = new Thread(this, "echo-shell");
            thread.setDaemon(true);
            thread.start();
        }

        /**
         * The method interrupts the read thread.
         * A missing thread does nothing.
         *
         * @param channel The channel is the SSH channel being closed.
         */
        @Override
        public void destroy(ChannelSession channel) {
            if (thread != null) {
                thread.interrupt();
            }
        }

        /**
         * The method reads bytes until the input ends, and handles each byte.
         * Escape bytes collect until {@link #isCompleteEscapeSequence} accepts them. Enter trims the line, calls {@link #onCommand} when the line is not empty, and writes the prompt. Backspace deletes one buffered character and erases one screen cell. A printable byte is echoed. Other control bytes are ignored.
         *
         * A normal end calls the exit callback with status {@code 0}. Any other failure calls it with status {@code 1} and the exception message.
         */
        @Override
        public void run() {
            try {
                byte[] buffer = new byte[1024];
                int n;

                while ((n = in.read(buffer)) != -1) {
                    for (int i = 0; i < n; i++) {
                        int b = buffer[i] & 0xFF;   // unsigned
                        char c = (char) b;

                        // ------------------------------------------------
                        // 1. Always inspect every incoming character here
                        // ------------------------------------------------
                        onRawCharacter(b, c);

                        // ------------------------------------------------
                        // 2. Escape sequence handling (immediate commands)
                        // ------------------------------------------------
                        if (inEscapeSequence) {
                            escapeBuffer.append(c);

                            // Very simple detection – extend as needed
                            if (isCompleteEscapeSequence(escapeBuffer)) {
                                String seq = escapeBuffer.toString();
                                escapeBuffer.setLength(0);
                                inEscapeSequence = false;

                                onEscapeSequence(seq);
                            }
                            continue;
                        }

                        if (b == 0x1B) {          // ESC
                            inEscapeSequence = true;
                            escapeBuffer.setLength(0);
                            escapeBuffer.append(c);
                            continue;
                        }

                        // ------------------------------------------------
                        // 3. Normal character / line handling
                        // ------------------------------------------------
                        if (c == '\r' || c == '\n') {
                            String line = lineBuffer.toString().trim();
                            lineBuffer.setLength(0);

                            write("\r\n");

                            if (!line.isEmpty()) {
                                onCommand(line);          // Enter-based command
                            }

                            write("casserole> ");
                        }
                        else if (c == 127 || c == 8) {   // Backspace
                            if (lineBuffer.length() > 0) {
                                lineBuffer.setLength(lineBuffer.length() - 1);
                                write("\b \b");
                            }
                        }
                        else if (c >= 32 && c < 127) {   // Printable
                            lineBuffer.append(c);
                            write(String.valueOf(c));    // echo
                        }
                        // other control characters are ignored for now
                    }
                }

                callback.onExit(0);

            } catch (Exception e) {
                callback.onExit(1, e.getMessage());
            }
        }

        // =====================================================================
        //  Extension points – put your logic in these methods
        // =====================================================================

        /**
         * The method sees every raw byte before line editing.
         * Byte {@code 3}, which is Ctrl+C, writes {@code ^C} and the prompt. A write failure is ignored.
         *
         * @param b The byte is the unsigned value from {@code 0} to {@code 255}.
         * @param c The character is that same byte as a character.
         */
        private void onRawCharacter(int b, char c) {
            // Example: trap Ctrl+C immediately
            if (b == 3) {   // Ctrl+C
                try {
                    write("\r\n^C\r\n");
                    write("casserole> ");
                } catch (IOException ignored) {}
            }

            // You can add more single-key traps here
        }

        /**
         * The method writes the escape sequence as hex bytes and then writes the prompt.
         * The sequence fires as soon as it is complete. Enter is not required.
         *
         * @param sequence The sequence is the complete escape text, including the escape byte.
         * @throws IOException The method throws this exception when the write fails.
         */
        private void onEscapeSequence(String sequence) throws IOException {
            write("\r\n[Escape sequence received: ");
            for (char ch : sequence.toCharArray()) {
                write(String.format("\\x%02X", (int) ch));
            }
            write("]\r\n");
            write("casserole> ");

            // Example of mapping a sequence to an action:
            // if (sequence.equals("\u001B[A")) { ... }  // Up arrow
        }

        /**
         * The method handles one line after Enter.
         * {@code exit} or {@code quit}, ignoring case, writes {@code Goodbye.} and ends the channel with status {@code 0}. Any other line writes {@code Command received:} and that line.
         *
         * @param line The line is the trimmed command text.
         * @throws IOException The method throws this exception when the write fails.
         */
        private void onCommand(String line) throws IOException {
            if ("exit".equalsIgnoreCase(line) || "quit".equalsIgnoreCase(line)) {
                write("Goodbye.\r\n");
                callback.onExit(0);
                return;
            }

            write("Command received: " + line + "\r\n");
        }

        // =====================================================================
        //  Helpers
        // =====================================================================

        /**
         * The method reports whether the escape buffer looks complete.
         * A buffer shorter than {@code 2} is incomplete. A last character that is a letter or {@code ~} is complete. A two-character sequence whose second character is not {@code [} is complete. Every other buffer is incomplete.
         *
         * @param buf The buffer is the escape text collected so far.
         * @return The return value is true when the sequence should be handled now.
         */
        private boolean isCompleteEscapeSequence(StringBuilder buf) {
            // Very basic detection – good enough to get started
            if (buf.length() < 2) return false;

            // CSI sequences usually end with a letter
            char last = buf.charAt(buf.length() - 1);
            if (last >= 'A' && last <= 'Z') return true;
            if (last >= 'a' && last <= 'z') return true;
            if (last == '~') return true;          // many function keys

            // Simple single-character ESC sequences (rare)
            if (buf.length() == 2 && buf.charAt(1) != '[') return true;

            return false;
        }

        /**
         * The method writes the text as UTF-8 and flushes the output stream.
         *
         * @param text The text is the characters to send.
         * @throws IOException The method throws this exception when the write or the flush fails.
         */
        private void write(String text) throws IOException {
            out.write(text.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }
}