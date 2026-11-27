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

import javax.swing.SwingUtilities;

/**
 * The model lane keeps the reasoning text.
 * One paint waits on the event thread.
 * A later chunk joins the text that paint will write.
 * The event thread paints a coarse update.
 */
final class GraphThoughtDrain {

    /**
     * The poster places one paint on the event thread.
     */
    static class Poster {
        /**
         * The method posts one paint.
         * The event thread is the default.
         *
         * @param task the paint
         */
        void post(Runnable task) {
            SwingUtilities.invokeLater(task);
        }
    }

    private final StringBuilder pending = new StringBuilder();
    private final Runnable publish;
    private final Poster poster;
    private boolean queued;

    /**
     * The constructor posts the paint with {@link SwingUtilities#invokeLater}.
     *
     * @param publish the paint that drains the text
     */
    GraphThoughtDrain(Runnable publish) {
        this(publish, new Poster());
    }

    /**
     * The constructor posts the paint through {@code poster}.
     *
     * @param publish the paint that drains the text
     * @param poster the poster, or the event-thread poster
     */
    GraphThoughtDrain(Runnable publish, Poster poster) {
        this.publish = publish;
        this.poster = poster;
    }

    /**
     * The method keeps one chunk on the model lane.
     * A null or empty chunk returns without a post.
     * A paint that is already waiting is not posted again.
     *
     * @param chunk the reasoning text
     */
    void offer(String chunk) {
        // @aa-sdd L40 aetheris-plan-f-327.001.md FINESSE:f-327
        if (chunk == null || chunk.length() == 0 || publish == null || poster == null) {
            return;
        }
        boolean post;
        synchronized (pending) {
            pending.append(chunk);
            post = !queued;
            queued = true;
        }
        if (post) {
            poster.post(publish);
        }
    }

    /**
     * The method returns the text that arrived and clears the wait.
     * A later chunk can post one new paint.
     *
     * @return the text, or empty
     */
    String take() {
        synchronized (pending) {
            String text = pending.toString();
            pending.setLength(0);
            queued = false;
            return text;
        }
    }
}
