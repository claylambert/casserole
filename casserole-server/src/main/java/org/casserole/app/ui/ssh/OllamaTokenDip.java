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
