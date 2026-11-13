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

package org.casserole.ollama;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// @aa-sdd L40 app-design.md
/**
 * The {@code OllamaProcess} type runs the {@code ollama} command with a fixed argument list.
 * The command is started directly, not through a shell.
 * Standard output and standard error are combined into one captured string.
 * The capture stops at 65536 bytes.
 * The type has no instances.
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-402 docs/specs/java/org/casserole/ollama/ollama.spec.md
public final class OllamaProcess {

    private static final int MAX_OUTPUT = 65536;

    /**
     * The constructor is private so a caller uses {@link #run(String[], long)} and {@link #validate(String[])}.
     */
    private OllamaProcess() {
    }

    /**
     * The {@code run} method starts {@code ollama} and returns the combined output.
     * The {@code args} array holds the tokens that follow the program name, such as {@code stop} or {@code list}.
     * {@link #validate(String[])} rejects a missing list, an empty token, {@code ..}, and the characters {@code ;}, {@code |}, {@code &}, {@code <}, {@code >}, newline, and carriage return.
     * The process redirects standard error into standard output.
     * The method reads that stream until the process exits or {@code timeoutMs} milliseconds have passed.
     * Captured text stops growing at 65536 bytes.
     * The method sleeps 50 milliseconds between reads while the process is still alive.
     * If the process is still alive at the deadline, the method destroys it and throws {@code Exception} with the message {@code ollama timed out after } plus the timeout plus {@code  ms.}
     * If the process exits with a code other than zero and the captured text is blank, the method throws {@code Exception} with the message {@code ollama exited } plus that code.
     * If the process exits with a code other than zero and the captured text is not blank, the method returns that text, a newline, and {@code (exit } plus the code plus {@code )}.
     * If the process exits with code zero, the method returns the captured text alone.
     *
     * @param args The tokens that follow the {@code ollama} program name.
     * @param timeoutMs The number of milliseconds to wait before the process is destroyed.
     * @return The combined output, with an exit suffix when the process exits non-zero and still printed text.
     * @throws Exception The arguments were rejected, the wait timed out, the process failed with no text, or reading the process failed.
     */
    public static String run(String[] args, long timeoutMs) throws Exception {
        validate(args);
        List<String> command = new ArrayList<String>();
        command.add("ollama");
        for (int i = 0; i < args.length; i++) {
            command.add(args[i]);
        }
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        InputStream in = process.getInputStream();
        byte[] buf = new byte[4096];
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            while (in.available() > 0) {
                int n = in.read(buf);
                if (n < 0) {
                    break;
                }
                int room = MAX_OUTPUT - captured.size();
                if (room <= 0) {
                    break;
                }
                captured.write(buf, 0, n < room ? n : room);
            }
            if (!process.isAlive()) {
                drainRemaining(in, captured);
                break;
            }
            Thread.sleep(50);
        }
        if (process.isAlive()) {
            process.destroyForcibly();
            throw new Exception("ollama timed out after " + timeoutMs + " ms.");
        }
        int code = process.waitFor();
        String out = captured.toString(StandardCharsets.UTF_8);
        if (code != 0 && out.trim().length() == 0) {
            throw new Exception("ollama exited " + code);
        }
        if (code != 0) {
            return out + "\n(exit " + code + ")";
        }
        return out;
    }

    /**
     * The {@code drainRemaining} method copies bytes that are already waiting on the process stream.
     * Copying stops when the capture reaches 65536 bytes or when no more bytes are waiting.
     * Any exception from the stream is ignored.
     *
     * @param in The combined output stream of the process.
     * @param captured The buffer that already holds earlier output.
     */
    private static void drainRemaining(InputStream in, ByteArrayOutputStream captured) {
        try {
            byte[] buf = new byte[4096];
            int n;
            while (captured.size() < MAX_OUTPUT && in.available() > 0 && (n = in.read(buf)) > 0) {
                int room = MAX_OUTPUT - captured.size();
                captured.write(buf, 0, n < room ? n : room);
            }
        } catch (Exception ignored) {
            // ignore
        }
    }

    /**
     * The {@code validate} method rejects arguments that could change the command or walk a path.
     * If {@code args} is {@code null}, the method throws {@code Exception} with the message {@code No ollama arguments.}
     * If a token is {@code null} or empty, the method throws {@code Exception} with the message {@code Empty ollama argument.}
     * If a token contains {@code ..}, the method throws {@code Exception} with the message {@code Illegal ollama argument.}
     * If a token contains {@code ;}, {@code |}, {@code &}, {@code <}, {@code >}, a newline, or a carriage return, the method throws {@code Exception} with the message {@code Illegal ollama argument.}
     *
     * @param args The tokens that would follow the {@code ollama} program name.
     * @throws Exception The list was missing, a token was empty, or a token contained a rejected sequence.
     */
    public static void validate(String[] args) throws Exception {
        if (args == null) {
            throw new Exception("No ollama arguments.");
        }
        for (int i = 0; i < args.length; i++) {
            String a = args[i];
            if (a == null || a.length() == 0) {
                throw new Exception("Empty ollama argument.");
            }
            if (a.indexOf("..") >= 0) {
                throw new Exception("Illegal ollama argument.");
            }
            for (int p = 0; p < a.length(); p++) {
                char c = a.charAt(p);
                if (c == ';' || c == '|' || c == '&' || c == '<' || c == '>' || c == '\n' || c == '\r') {
                    throw new Exception("Illegal ollama argument.");
                }
            }
        }
    }
}
/*****************************************************************************************
 * End of Class: OllamaProcess.java 082526
 ****************************************************************************************/
