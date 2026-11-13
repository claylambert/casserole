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

package org.aetheris.swing;

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
 * The {@code NotebookDip} class asks for one settled token count after the text stops changing.
 * The call is {@code POST} to the URL in {@code HOST}.
 * The model id is {@code MODEL}.
 * {@code num_predict} is 1.
 * The reply text is not kept.
 * {@code prompt_eval_count} is the count.
 * A failed call returns 0, and {@link Seat#settled} is not called, so the estimate stays in place.
 * Text longer than {@link #MAX_CHARS} characters is not sent.
 * This type does not paint.
 *
 * @aa-sdd F-149
 * @author clayl (2026-11-01)
 */
// @aa-sdd PK-767 docs/specs/common/stages.spec.md
// @aa-sdd PK-69 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-1105 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-68 docs/specs/java/org/aetheris/swing/swing.spec.md
final class NotebookDip {

    /**
     * The constant is the generate URL this type posts to.
     */
    static final String HOST = "http://192.168.1.162:11434/api/generate";

    static final String MODEL = "deepseek-r1:7b";

    /**
     * The constant is the longest text this type will send.
     * A longer text leaves the estimate in place.
     */
    static final int MAX_CHARS = 8000;

    // @aa-sdd PK-1118 docs/specs/common/formal-plan.spec.md FINESSE:f-149
    /**
     * The {@code Seat} interface receives a settled token count.
     */
    interface Seat {
        /**
         * The method receives {@code prompt_eval_count} from the finished reply.
         *
         * @param count The count is that token total.
         */
        void settled(int count);
    }

    private final Seat seat;
    private final Timer quiet;
    private String pending = "";
    private int generation;

    /**
     * The constructor stores the callback and arms a one-shot timer of 700 milliseconds.
     *
     * @param seat The seat is the callback that receives the count.
     */
    NotebookDip(Seat seat) {
        this.seat = seat;
        /**
         * The timer listener calls {@link #fire} when the quiet wait ends.
         */
        quiet = new Timer(700, new ActionListener() {
            // @aa-sdd PK-1118 docs/specs/common/formal-plan.spec.md FINESSE:f-149
            @Override
            public void actionPerformed(ActionEvent event) {
                fire();
            }
        });
        quiet.setRepeats(false);
    }

    /**
     * The method arms a settle for this text.
     * A null text is stored as empty.
     * The same text as the pending text does not re-arm the timer.
     * An empty text stops the timer.
     * Text longer than {@link #MAX_CHARS} stops the timer.
     * Any other change bumps the generation and restarts the 700 millisecond timer.
     *
     * @param text The text is the line or the counted body, and it may be null.
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
     * The method starts a daemon thread named {@code ollama-token-dip}.
     * Empty text returns without a call.
     * Text longer than {@link #MAX_CHARS} returns without a call.
     * The thread reads the count off the Swing event thread.
     * {@link Seat#settled} runs back on the event thread.
     * A newer {@link #watch} call drops the result.
     * A count below 1 does not call {@link Seat#settled}.
     */
    private void fire() {
        final String text = pending;
        final int ticket = generation;
        if (text.length() == 0 || text.length() > MAX_CHARS) {
            return;
        }
        /**
         * The worker reads the count and posts the result onto the Swing event thread.
         */
        Thread worker = new Thread(new Runnable() {
            // @aa-sdd PK-1118 docs/specs/common/formal-plan.spec.md FINESSE:f-149
            @Override
            public void run() {
                final int count = readCount(text);
                /**
                 * The event-thread runnable delivers the count when this request is still current.
                 */
                SwingUtilities.invokeLater(new Runnable() {
                    // @aa-sdd PK-1118 docs/specs/common/formal-plan.spec.md FINESSE:f-149
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
     * The method posts the generate body and reads {@code prompt_eval_count}.
     * The connect timeout is 4000 milliseconds.
     * The read timeout is 20000 milliseconds.
     * A status outside 200 through 299 returns 0.
     * A missing stream returns 0.
     * Any thrown exception returns 0.
     * The connection is disconnected before the method returns.
     *
     * @param text The text is the prompt sent in the body.
     * @return The return value is the count, or 0 when the call fails.
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
     * The method builds the JSON body for one generate call.
     * The model is {@link #MODEL}.
     * {@code stream} is false.
     * {@code keep_alive} is 300.
     * {@code num_predict} is 1.
     *
     * @param text The text is the prompt placed in the JSON string.
     * @return The return value is that JSON object.
     */
    static String bodyFor(String text) {
        return "{\"model\":\"" + MODEL + "\",\"prompt\":" + jsonString(text)
                + ",\"stream\":false,\"keep_alive\":300,\"options\":{\"num_predict\":1}}";
    }

    /**
     * The method quotes one string for JSON.
     * A backslash or a quote is escaped with a backslash.
     * A newline becomes {@code \\n}.
     * A carriage return becomes {@code \\r}.
     * A character below {@code 0x20} becomes a space.
     * Any other character is copied.
     *
     * @param text The text is the prompt to quote.
     * @return The return value is the quoted JSON string, including the quotes.
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
     * The method reads {@code prompt_eval_count} from a reply body.
     * A null body returns 0.
     * A missing key returns 0.
     * Spaces, colons, and newlines after the key are skipped.
     * The following digits are the count.
     * No digit returns 0.
     *
     * @param body The body is the reply text, and it may be null.
     * @return The return value is the count, or 0 when the key is missing.
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
