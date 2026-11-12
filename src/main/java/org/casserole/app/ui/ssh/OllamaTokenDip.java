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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * The {@code OllamaTokenDip} type reads one settled token count from Ollama, the program that runs the model.
 * The request is {@code POST} to {@link #HOST}.
 * The model name is {@code deepseek-r1:7b}.
 * The option {@code num_predict} is {@code 1}.
 * The method reads the reply only to take {@code prompt_eval_count}.
 * The generated text in that reply is not kept.
 * The failed call does not invoke {@link Seat#settled}.
 * The caller then keeps the count it already has.
 * The text longer than {@link #MAX_CHARS} characters does not start a request.
 * The empty text does not start a request.
 * The type does not paint.
 *
 * @aa-sdd PK-0104 docs/specs/java/org/aetheris/swing/swing.spec.md
 * @author clayl (2026-11-01)
 */
final class OllamaTokenDip {

    /**
     * The {@code HOST} constant is the only request address this type opens.
     * The address is {@code http://192.168.1.162:11434/api/generate}.
     */
    static final String HOST = "http://192.168.1.162:11434/api/generate";

    /**
     * The {@code MODEL} constant is the model name sent in the request body.
     * The name is {@code deepseek-r1:7b}.
     */
    static final String MODEL = "deepseek-r1:7b";

    /**
     * The {@code MAX_CHARS} constant is the longest text this type will send.
     * The value is {@code 8000} characters.
     * The longer text stays on the caller's heuristic.
     * The method starts no request for that text.
     */
    static final int MAX_CHARS = 8000;

    // @aa-sdd PK-0097 docs/specs/java/org/aetheris/swing/swing.spec.md
    /**
     * The {@code Seat} type receives one settled count.
     * The count is {@code prompt_eval_count} from a finished reply.
     */
    interface Seat {
        /**
         * The settled method accepts one model count.
         * The count is {@code prompt_eval_count} from the done frame of the reply.
         *
         * @param count The {@code prompt_eval_count} value from that frame.
         */
        void settled(int count);
    }

    private final Seat seat;
    private final Timer quiet;
    private String pending = "";
    private int generation;

    /**
     * The constructor stores the callback and builds a one-shot timer.
     * The timer waits {@code 700} milliseconds.
     * The timer does not repeat.
     *
     * @param seat The callback that receives a settled count.
     */
    OllamaTokenDip(Seat seat) {
        this.seat = seat;
        /**
         * The listener is the quiet timer.
         * The pulse calls {@link #fire} once.
         */
        quiet = new Timer(700, new ActionListener() {
            // @aa-sdd PK-0098 docs/specs/java/org/aetheris/swing/swing.spec.md
            @Override
            public void actionPerformed(ActionEvent event) {
                fire();
            }
        });
        quiet.setRepeats(false);
    }

    /**
     * The watch method arms a settle for this text.
     * The method stores a null text as an empty string.
     * The method returns without touching the timer when the text equals the text already pending.
     * The method stores the text and adds one to the generation on every other call.
     * The method stops the timer when the text is empty.
     * The method stops the timer when the text is longer than {@link #MAX_CHARS}.
     * The method restarts the {@code 700} millisecond timer for every other text.
     *
     * @param text The line or the counted body. The value may be null.
     */
    void watch(String text) {
        String next = text == null ? "" : text;
        if (next.equals(pending)) {
            return;
        }
        pending = next;
        generation = generation + 1;
        if (next.length() == 0 || next.length() > MAX_CHARS) {
            quiet.stop();
            return;
        }
        quiet.restart();
    }

    /**
     * The fire method starts one background read for the pending text.
     * The method returns without a thread when the pending text is empty.
     * The method returns without a thread when the pending text is longer than {@link #MAX_CHARS}.
     * The worker thread is a daemon named {@code ollama-token-dip}.
     * The worker reads the count, then schedules the callback on the Swing event thread.
     * The callback does nothing when the generation has changed.
     * The callback does nothing when the count is under {@code 1}.
     * The callback calls {@link Seat#settled} for any other count.
     */
    private void fire() {
        final String text = pending;
        final int ticket = generation;
        if (text.length() == 0 || text.length() > MAX_CHARS) {
            return;
        }
        /**
         * The worker reads the count off the Swing event thread.
         * The later callback delivers that count on the event thread.
         */
        Thread worker = new Thread(new Runnable() {
            // @aa-sdd PK-0099 docs/specs/java/org/aetheris/swing/swing.spec.md
            @Override
            public void run() {
                final int count = readCount(text);
                /**
                 * The callback runs on the Swing event thread.
                 * The callback drops the count when the generation changed.
                 * The callback drops the count when it is under {@code 1}.
                 * The callback calls {@link Seat#settled} for any other count.
                 */
                SwingUtilities.invokeLater(new Runnable() {
                    // @aa-sdd PK-0100 docs/specs/java/org/aetheris/swing/swing.spec.md
                    @Override
                    public void run() {
                        if (ticket != generation || count < 1) {
                            return;
                        }
                        seat.settled(count);
                    }
                });
            }
        });
        worker.setDaemon(true);
        worker.setName("ollama-token-dip");
        worker.start();
    }

    /**
     * The readCount method posts the text and returns {@code prompt_eval_count}.
     * The request method is {@code POST}.
     * The connect timeout is {@code 4000} milliseconds.
     * The read timeout is {@code 20000} milliseconds.
     * The content type is {@code application/json; charset=utf-8}.
     * The method returns {@code 0} when the status is outside {@code 200} through {@code 299}.
     * The method returns {@code 0} when the response stream is null.
     * The method returns {@code 0} when any exception is thrown.
     * The connection is disconnected before the method returns.
     *
     * @param text The prompt text already checked for length.
     * @return The return value is {@code prompt_eval_count}, or {@code 0} on a miss.
     */
    private static int readCount(String text) {
        HttpURLConnection connection = null;
        try {
            byte[] body = bodyFor(text).getBytes(StandardCharsets.UTF_8);
            connection = (HttpURLConnection) new URL(HOST).openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(4000);
            connection.setReadTimeout(20000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            OutputStream out = connection.getOutputStream();
            try {
                out.write(body);
            } finally {
                out.close();
            }
            int status = connection.getResponseCode();
            InputStream in = status >= 200 && status < 300
                    ? connection.getInputStream()
                    : connection.getErrorStream();
            if (in == null || status < 200 || status >= 300) {
                return 0;
            }
            ByteArrayOutputStream raw = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = in.read(buf);
            while (n >= 0) {
                if (n > 0) {
                    raw.write(buf, 0, n);
                }
                n = in.read(buf);
            }
            in.close();
            return promptEvalCount(new String(raw.toByteArray(), StandardCharsets.UTF_8));
        } catch (Exception ex) {
            return 0;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * The bodyFor method builds the JSON request body for one prompt.
     * The model field is {@link #MODEL}.
     * The prompt field is the JSON string from {@link #jsonString}.
     * The stream field is false.
     * The keep-alive field is {@code 300}.
     * The option {@code num_predict} is {@code 1}.
     *
     * @param text The prompt text placed in the body.
     * @return The return value is one JSON object.
     */
    static String bodyFor(String text) {
        return "{\"model\":\"" + MODEL + "\",\"prompt\":" + jsonString(text)
                + ",\"stream\":false,\"keep_alive\":300,\"options\":{\"num_predict\":1}}";
    }

    /**
     * The jsonString method wraps one prompt in JSON quotes.
     * The method writes a backslash as two backslash characters.
     * The method writes a quote as a backslash and the quote.
     * The method writes a line feed as a backslash and {@code n}.
     * The method writes a carriage return as a backslash and {@code r}.
     * The method writes any other character under {@code U+0020} as a space.
     * The method writes every other character as itself.
     *
     * @param text The prompt text. The caller does not pass null.
     * @return The return value is a quoted JSON string.
     */
    static String jsonString(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append('"');
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\\' || c == '"') {
                sb.append('\\');
                sb.append(c);
            } else if (c == '\n') {
                sb.append("\\n");
            } else if (c == '\r') {
                sb.append("\\r");
            } else if (c < 0x20) {
                sb.append(' ');
            } else {
                sb.append(c);
            }
        }
        sb.append('"');
        return sb.toString();
    }

    /**
     * The promptEvalCount method reads {@code prompt_eval_count} from a reply body.
     * The method returns {@code 0} when the body is null.
     * The method returns {@code 0} when the key {@code "prompt_eval_count"} is absent.
     * The scan skips spaces, colons, and line feeds after that key.
     * The method returns {@code 0} when no digit follows.
     * The method accumulates the following digits into an int and returns that int.
     *
     * @param body The reply body. The value may be null.
     * @return The return value is the count, or {@code 0} when the count is missing.
     */
    static int promptEvalCount(String body) {
        if (body == null) {
            return 0;
        }
        String key = "\"prompt_eval_count\"";
        int at = body.indexOf(key);
        if (at < 0) {
            return 0;
        }
        int i = at + key.length();
        while (i < body.length() && (body.charAt(i) == ' ' || body.charAt(i) == ':' || body.charAt(i) == '\n')) {
            i = i + 1;
        }
        int value = 0;
        boolean digit = false;
        while (i < body.length()) {
            char c = body.charAt(i);
            if (c < '0' || c > '9') {
                break;
            }
            digit = true;
            value = value * 10 + (c - '0');
            i = i + 1;
        }
        return digit ? value : 0;
    }
}
