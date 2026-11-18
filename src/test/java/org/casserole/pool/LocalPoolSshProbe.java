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
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelShell;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;

/**
 * The class opens one SSH shell to a running Casserole and sends one short line.
 * It is a forensics probe. It is not part of the test suite. Casserole must already be listening on port {@code 2222}.
 * The channel is a shell. It does not request a PTY. The password is read from {@code users.yaml} and is not printed.
 * The run waits up to {@link #HOLD_MS} and writes the timing to standard out. The second argument {@code tool} asks for a shell command. Any other argument asks for one word and no tools. The wait ends when the prompt returns.
 */
public class LocalPoolSshProbe {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 2222;
    private static final long HOLD_MS = 120_000L;
    private static final String PLAIN = "Reply with the single word pong. Do not use tools.";
    private static final String TOOL = "Run a shell command that prints the single word pong. Then reply with that word.";

    /**
     * The method runs one probe.
     * The first argument is the SSH user. A missing argument uses {@code alice}. The second argument {@code tool} sends the shell line. Any other value sends the plain line.
     *
     * @param args The args are the optional user name and the optional mode.
     * @throws Exception The method throws this exception when the SSH client cannot open the shell.
     */
    public static void main(String[] args) throws Exception {
        String user = args.length > 0 ? args[0] : "alice";
        String mode = args.length > 1 ? args[1] : "plain";
        int code = new LocalPoolSshProbe().run(user, mode);
        System.exit(code);
    }

    /**
     * The method logs in, sends one line, and records when the pane answers.
     * A missing welcome returns {@code 1}. A {@code pool down} line returns {@code 2}. An empty reply returns {@code 3}. A turn that does not return returns {@code 4}. A finished reply returns {@code 0}.
     *
     * @param user The user is the SSH login name.
     * @param mode The mode selects the line. {@code tool} asks for a shell command.
     * @return The return value is the process status.
     * @throws Exception The method throws this exception when the SSH client cannot open the shell.
     */
    public int run(String user, String mode) throws Exception {
        String line = "tool".equals(mode) ? TOOL : PLAIN;
        String password = passwordFor(user);
        if (password.length() == 0) {
            System.out.println("probe no password for " + user);
            return 1;
        }
        long opened = System.currentTimeMillis();
        SshClient client = SshClient.setUpDefaultClient();
        client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
        client.start();
        try (ClientSession session = client.connect(user, HOST, PORT).verify(15, TimeUnit.SECONDS).getSession()) {
            session.addPasswordIdentity(password);
            session.auth().verify(15, TimeUnit.SECONDS);
            long authed = System.currentTimeMillis();
            ChannelShell shell = session.createShellChannel();
            shell.setUsePty(false);
            ByteArrayOutputStream captured = new ByteArrayOutputStream();
            shell.setOut(captured);
            shell.setErr(captured);
            shell.open().verify(15, TimeUnit.SECONDS);
            long welcomeAt = waitFor(captured, "Welcome to Casserole chat", 15_000L);
            if (!shell.isOpen()) {
                System.out.println("probe channel closed before send bytes=" + captured.size());
                System.out.println("probe early=" + strip(captured.toString(StandardCharsets.UTF_8)));
                return 1;
            }
            if (welcomeAt < 0) {
                System.out.println("probe no welcome bytes=" + captured.size());
                System.out.println("probe early=" + strip(captured.toString(StandardCharsets.UTF_8)));
                return 1;
            }
            if (waitForCount(captured, "chat> ", 1, 15_000L) < 0) {
                System.out.println("probe no prompt bytes=" + captured.size());
                return 1;
            }
            OutputStream stdin = shell.getInvertedIn();
            int prompts = countOf(captured, "chat> ");
            stdin.write((line + "\r").getBytes(StandardCharsets.UTF_8));
            stdin.flush();
            long sent = System.currentTimeMillis();
            long doneAt = waitForCount(captured, "chat> ", prompts + 1, HOLD_MS);
            long closed = System.currentTimeMillis();
            shell.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 200L);
            String text = strip(captured.toString(StandardCharsets.UTF_8));
            boolean poolDown = text.contains("pool down");
            boolean empty = text.contains("The engine returned an empty reply.");
            boolean header = text.contains(" · ");
            System.out.println("probe user=" + user
                    + " mode=" + ("tool".equals(mode) ? "tool" : "plain")
                    + " authMs=" + (authed - opened)
                    + " welcomeMs=" + (welcomeAt - opened)
                    + " sentMs=" + (sent - opened)
                    + " replyMs=" + (doneAt < 0 ? "none" : Long.toString(doneAt - sent))
                    + " holdMs=" + (closed - sent)
                    + " bytes=" + captured.size()
                    + " hasPoolDown=" + poolDown
                    + " hasEmpty=" + empty
                    + " hasHeader=" + header
                    + " hasPong=" + text.contains("pong"));
            if (poolDown) {
                return 2;
            }
            if (empty) {
                return 3;
            }
            if (doneAt >= 0 && header) {
                return 0;
            }
            return 4;
        } finally {
            client.stop();
        }
    }

    /**
     * The method waits until the captured text contains the marker.
     * A timeout returns {@code -1}. The clock is the wall clock.
     *
     * @param captured The buffer is the shell output.
     * @param marker The marker is the text to find.
     * @param budgetMs The budget is the maximum wait.
     * @return The return value is the clock time when the marker appeared, or {@code -1}.
     * @throws InterruptedException The method throws this exception when the sleep is interrupted.
     */
    private static long waitFor(ByteArrayOutputStream captured, String marker, long budgetMs) throws InterruptedException {
        long deadline = System.currentTimeMillis() + budgetMs;
        while (System.currentTimeMillis() < deadline) {
            String text = captured.toString(StandardCharsets.UTF_8);
            if (text.contains(marker)) {
                return System.currentTimeMillis();
            }
            Thread.sleep(200L);
        }
        return -1L;
    }

    /**
     * The method counts one marker in the captured text.
     *
     * @param captured The buffer is the shell output.
     * @param marker The marker is the text to count.
     * @return The return value is the count.
     */
    private static int countOf(ByteArrayOutputStream captured, String marker) {
        String text = captured.toString(StandardCharsets.UTF_8);
        int count = 0;
        int at = 0;
        while (true) {
            int found = text.indexOf(marker, at);
            if (found < 0) {
                return count;
            }
            count = count + 1;
            at = found + marker.length();
        }
    }

    /**
     * The method waits until the marker has been seen enough times.
     * A timeout returns {@code -1}. The clock is the wall clock.
     *
     * @param captured The buffer is the shell output.
     * @param marker The marker is the text to count.
     * @param need The need is the count that ends the wait.
     * @param budgetMs The budget is the maximum wait.
     * @return The return value is the clock time when the count was reached, or {@code -1}.
     * @throws InterruptedException The method throws this exception when the sleep is interrupted.
     */
    private static long waitForCount(ByteArrayOutputStream captured, String marker, int need, long budgetMs)
            throws InterruptedException {
        long deadline = System.currentTimeMillis() + budgetMs;
        while (System.currentTimeMillis() < deadline) {
            if (countOf(captured, marker) >= need) {
                return System.currentTimeMillis();
            }
            Thread.sleep(200L);
        }
        return -1L;
    }

    /**
     * The method reads the password for one name from {@code users.yaml}.
     * A missing name returns an empty string. The password is not logged.
     *
     * @param name The name is the SSH user.
     * @return The return value is the password, or an empty string.
     * @throws Exception The method throws this exception when the file cannot be read.
     */
    private static String passwordFor(String name) throws Exception {
        InputStream in = LocalPoolSshProbe.class.getClassLoader().getResourceAsStream("users.yaml");
        if (in == null) {
            return "";
        }
        String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        in.close();
        String[] lines = text.split("\n");
        String current = "";
        for (String raw : lines) {
            String row = raw.trim();
            if (row.startsWith("- name:")) {
                current = row.substring("- name:".length()).trim();
            } else if (row.startsWith("password:") && name.equals(current)) {
                return row.substring("password:".length()).trim();
            }
        }
        return "";
    }

    /**
     * The method removes ANSI color sequences from the shell text.
     *
     * @param raw The raw text is the captured shell output.
     * @return The return value is the text without those sequences.
     */
    private static String strip(String raw) {
        return raw.replaceAll("\u001B\\[[0-9;]*m", "");
    }
}
