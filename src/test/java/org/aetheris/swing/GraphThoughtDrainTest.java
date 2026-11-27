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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Font;
import java.util.concurrent.atomic.AtomicInteger;

import org.casserole.app.ui.ssh.SshClientProperties;
import org.casserole.app.ui.ssh.SshClientTheme;
import org.junit.jupiter.api.Test;

/**
 * The drain keeps reasoning text until one paint takes it.
 * The live strip inserts that text and leaves the log alone.
 * The test does not open a window.
 *
 * @author clayl (2026-11-26)
 */
class GraphThoughtDrainTest {

    /**
     * Many chunks post one paint.
     * The paint reads every chunk in order.
     * A chunk after that paint posts again.
     */
    @Test
    void onePaintCoversTheChunksThatArrived() {
        final AtomicInteger posts = new AtomicInteger();
        GraphThoughtDrain drain = new GraphThoughtDrain(new Runnable() {
            @Override
            public void run() {
            }
        }, new GraphThoughtDrain.Poster() {
            @Override
            void post(Runnable task) {
                posts.incrementAndGet();
            }
        });
        drain.offer(null);
        drain.offer("");
        assertEquals(0, posts.get());
        drain.offer("al");
        drain.offer("ph");
        drain.offer("a");
        assertEquals(1, posts.get());
        assertEquals("alpha", drain.take());
        drain.offer("beta");
        assertEquals(2, posts.get());
        assertEquals("beta", drain.take());
        assertEquals("", drain.take());
    }

    /**
     * A live block is inserted once and pinned on the strip.
     * The host does not store the log.
     * The host does not lay the picture out.
     * A later ordinary offer still stores the log.
     * The next block opens its own banner.
     */
    @Test
    void theLiveStripLeavesTheLogUntilTheBlockEnds() {
        final int[] saves = new int[1];
        final int[] layouts = new int[1];
        ThinkStrip.Host host = new ThinkStrip.Host() {
            @Override
            public int maxHeight() {
                return 480;
            }

            @Override
            public void relayout() {
                layouts[0] = layouts[0] + 1;
            }

            @Override
            public void saveLog(String text) {
                saves[0] = saves[0] + 1;
            }

            @Override
            public String loadLog() {
                return "";
            }

            @Override
            public void noteChrome(boolean closed, int height) {
            }

            @Override
            public void saveChrome() {
            }

            @Override
            public void cleared() {
            }

            @Override
            public void noteSeat(boolean shut, boolean folded) {
            }
        };
        SshClientTheme theme = new SshClientTheme(new SshClientProperties());
        ThinkStrip strip = new ThinkStrip(theme, new Font(Font.MONOSPACED, Font.PLAIN, 12), 12, true, 0, false,
                false, host);
        ThinkStream stream = new ThinkStream();
        stream.offerLive(strip, "cell", "grok", "alpha");
        stream.offerLive(strip, "cell", "grok", "beta");
        String text = strip.text();
        assertTrue(text.contains("alpha"));
        assertTrue(text.contains("beta"));
        assertEquals(1, banners(text, "grok"));
        assertEquals(0, saves[0]);
        assertEquals(0, layouts[0]);
        stream.offer(strip, "cell", "grok", "gamma");
        assertTrue(strip.text().contains("gamma"));
        assertEquals(1, banners(strip.text(), "grok"));
        assertEquals(1, saves[0]);
        assertTrue(layouts[0] > 0);
        int layoutsAfterOffer = layouts[0];
        stream.finish(strip);
        stream.offerLive(strip, "cell", "grok", "delta");
        assertTrue(strip.text().contains("delta"));
        assertEquals(2, banners(strip.text(), "grok"));
        assertEquals(1, saves[0]);
        assertEquals(layoutsAfterOffer, layouts[0]);
    }

    /**
     * The method counts banner lines for one word.
     *
     * @param text the pane text
     * @param word the banner word
     * @return the count
     */
    private static int banners(String text, String word) {
        String mark = "\u2500\u2500 " + word + " ";
        int count = 0;
        int at = 0;
        while (at < text.length()) {
            int nl = text.indexOf('\n', at);
            String line = nl < 0 ? text.substring(at) : text.substring(at, nl);
            if (line.startsWith(mark)) {
                count = count + 1;
            }
            if (nl < 0) {
                break;
            }
            at = nl + 1;
        }
        return count;
    }
}
