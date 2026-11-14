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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * The {@code NotebookHeatbar} class paints the token fill against the context window.
 * The track is 5 pixels tall.
 * The needle is 2 pixels wide and 9 pixels tall.
 * An amber post sits at 70 percent.
 * A red post sits at 90 percent.
 * The needle rests on the fill and lunges when the count changes.
 * Crossing a post flashes the head ink once.
 * A settled model count uses that same flash.
 * Keystrokes update the estimate, not the settled count.
 *
 * @aa-sdd F-149
 * @author clayl (2026-11-01)
 */
// @aa-sdd PK-767 docs/specs/common/stages.spec.md
// @aa-sdd PK-68 docs/specs/java/org/aetheris/swing/swing.spec.md
final class NotebookHeatbar extends JComponent {

    static final Color TRACK = new Color(0x2a, 0x34, 0x40);
    static final Color GREEN = new Color(0x3c, 0xb3, 0x71);
    static final Color AMBER = new Color(0xf0, 0xa2, 0x02);
    static final Color RED = new Color(0xe5, 0x39, 0x35);
    static final Color HEAD = new Color(0xdc, 0xdc, 0xdc);

    private static final int TRACK_H = 5;
    private static final int MARK_H = 9;
    private static final int NEEDLE_W = 2;
    private static final double LUNGE = 6d;

    private int shown;
    private int budget;
    private boolean modelHeld;
    private int heuristicTokens = -1;
    private boolean seated;
    private double lunge;
    private int flashTicks;
    private int flashPost;
    private final Timer timer;

    /**
     * The constructor sizes the bar and starts no timer until a count arrives.
     * The preferred width is 240 and the height is 9.
     * The minimum width is 80.
     * The tooltip names the fill, the 70 percent post, and the 90 percent post.
     */
    NotebookHeatbar() {
        setOpaque(false);
        setPreferredSize(new Dimension(240, MARK_H));
        setMinimumSize(new Dimension(80, MARK_H));
        setToolTipText("The beat is the token fill against the context window. "
                + "Amber is 70 percent. Red is 90 percent. The needle rests on the estimate.");
        /**
         * The timer listener advances the lunge and the flash every 16 milliseconds.
         */
        timer = new Timer(16, new ActionListener() {
            // @aa-sdd PK-1118 docs/specs/common/formal-plan.spec.md
            @Override
            public void actionPerformed(ActionEvent event) {
                tick();
            }
        });
    }

    /**
     * The method shows the estimate from keystrokes.
     * A negative token count is stored as 0.
     * A negative window is stored as 0.
     * A window of 0 paints an empty fill and still draws the posts.
     * The same estimate and window, while a model count is held, returns without a change.
     * The same estimate and window, while no model count is held, also returns without a change.
     * Any other call clears the model hold and seats the estimate.
     *
     * @param tokens The tokens value is the estimate, and a negative count is stored as 0.
     * @param window The window is the context size, and 0 paints an empty fill.
     */
    void showHeuristic(int tokens, int window) {
        int next = tokens < 0 ? 0 : tokens;
        int gate = window < 0 ? 0 : window;
        if (seated && modelHeld && next == heuristicTokens && gate == budget) {
            return;
        }
        if (seated && !modelHeld && next == shown && gate == budget) {
            return;
        }
        boolean moved = seated && next != shown;
        heuristicTokens = next;
        budget = gate;
        modelHeld = false;
        seat(next, moved, false, 0);
    }

    /**
     * The method shows the model count after the text has settled.
     * A negative count is stored as 0.
     * The model hold stays on.
     * This method does not call a host.
     * A band change flashes that post.
     * The settled flash runs even when the band does not change.
     *
     * @param tokens The tokens value is {@code prompt_eval_count}.
     */
    void showSettled(int tokens) {
        int next = tokens < 0 ? 0 : tokens;
        boolean moved = seated && next != shown;
        int post = band(fill(next, budget));
        int prior = band(fill(shown, budget));
        modelHeld = true;
        seat(next, moved, true, post != prior ? post : 0);
    }

    /**
     * The method returns the label for the count now on the needle.
     *
     * @return The return value is {@code est. tokens:} followed by the shown count.
     */
    String word() {
        return "est. tokens: " + shown;
    }

    /**
     * The method returns the ink for the current fill.
     * A flash in progress returns {@link #HEAD}.
     * Otherwise the ink follows the band of the shown fill.
     *
     * @return The return value is that color.
     */
    Color ink() {
        if (flashTicks > 0) {
            return HEAD;
        }
        return bandInk(band(fill(shown, budget)));
    }

    /**
     * The method stores the shown count and starts motion when needed.
     * The first seat only repaints.
     * A moved count sets the lunge to 6.
     * A band that rises above 0 flashes that post when no post was passed in.
     * A post above 0, or a settled flash, runs for 8 ticks.
     * The timer is armed and the bar repaints.
     *
     * @param tokens The tokens value is the count to show.
     * @param moved The flag is true when the count changed after the first seat.
     * @param settledFlash The flag is true when a settled count should flash.
     * @param post The post is the band to flash, or 0 when no post was named.
     */
    private void seat(int tokens, boolean moved, boolean settledFlash, int post) {
        int priorBand = band(fill(shown, budget));
        shown = tokens;
        if (!seated) {
            seated = true;
            repaint();
            return;
        }
        if (moved) {
            lunge = LUNGE;
        }
        int nextBand = band(fill(shown, budget));
        if (post == 0 && nextBand != priorBand && nextBand > 0) {
            post = nextBand;
        }
        if (post > 0 || settledFlash) {
            flashTicks = 8;
            flashPost = post;
        }
        arm();
        repaint();
    }

    /**
     * The method starts the 16 millisecond timer when it is not already running.
     */
    private void arm() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * The method decays the lunge and the flash.
     * A lunge above 0.4 is multiplied by 0.72.
     * A smaller lunge becomes 0.
     * A remaining flash tick is reduced by 1.
     * The bar repaints.
     * The timer stops when neither the lunge nor the flash is still live.
     */
    private void tick() {
        boolean live = false;
        if (lunge > 0.4d) {
            lunge = lunge * 0.72d;
            live = true;
        } else {
            lunge = 0d;
        }
        if (flashTicks > 0) {
            flashTicks = flashTicks - 1;
            live = true;
        }
        repaint();
        if (!live) {
            timer.stop();
        }
    }

    /**
     * The method returns the fill as a fraction of the window.
     * A window below 1 returns 0.
     * A used count of 0 or less returns 0.
     * A used count at or above the window returns 1.
     *
     * @param used The used value is the token count.
     * @param window The window is the context size.
     * @return The return value is the fraction from 0 through 1.
     */
    private static double fill(int used, int window) {
        if (window < 1 || used <= 0) {
            return 0d;
        }
        if (used >= window) {
            return 1d;
        }
        return used / (double) window;
    }

    /**
     * The method maps a fill onto a band.
     * A fill of 0 or less is band 0.
     * A fill of 0.9 or more is band 3.
     * A fill of 0.7 or more is band 2.
     * Any smaller positive fill is band 1.
     *
     * @param amount The amount is the fill fraction.
     * @return The return value is the band number.
     */
    private static int band(double amount) {
        if (amount <= 0d) {
            return 0;
        }
        if (amount >= 0.9d) {
            return 3;
        }
        if (amount >= 0.7d) {
            return 2;
        }
        return 1;
    }

    /**
     * The method returns the ink for a band.
     * Band 3 and above is {@link #RED}.
     * Band 2 is {@link #AMBER}.
     * Band 1 is {@link #GREEN}.
     * Band 0 is {@link #TRACK}.
     *
     * @param band The band is the number from {@link #band}.
     * @return The return value is that color.
     */
    private static Color bandInk(int band) {
        if (band >= 3) {
            return RED;
        }
        if (band == 2) {
            return AMBER;
        }
        if (band == 1) {
            return GREEN;
        }
        return TRACK;
    }

    /**
     * The method paints the track, the fill, the two posts, and the needle.
     * A width below 8 returns without painting.
     * A height below 9 returns without painting.
     * The fill uses {@link #HEAD} while a flash is running.
     * The needle is offset by the current lunge and stays inside the width.
     *
     * @param graphics The graphics context is the Swing paint surface.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            int width = getWidth();
            int height = getHeight();
            if (width < 8 || height < MARK_H) {
                return;
            }
            int top = (height - MARK_H) / 2;
            int trackTop = top + (MARK_H - TRACK_H) / 2;
            double amount = fill(shown, budget);
            int edge = (int) Math.round(amount * (width - 1));
            if (edge < 0) {
                edge = 0;
            }
            if (edge > width - 1) {
                edge = width - 1;
            }
            int needleX = edge + (int) Math.round(lunge);
            if (needleX > width - NEEDLE_W) {
                needleX = width - NEEDLE_W;
            }
            if (needleX < 0) {
                needleX = 0;
            }
            g2.setColor(TRACK);
            g2.fillRect(0, trackTop, width, TRACK_H);
            Color fillInk = flashTicks > 0 ? HEAD : bandInk(band(amount));
            if (edge > 0 && band(amount) > 0) {
                g2.setColor(fillInk);
                g2.fillRect(0, trackTop, edge, TRACK_H);
            }
            paintPost(g2, width, top, 0.7d, AMBER, 2);
            paintPost(g2, width, top, 0.9d, RED, 3);
            g2.setColor(HEAD);
            g2.fillRect(needleX, top, NEEDLE_W, MARK_H);
        } finally {
            g2.dispose();
        }
    }

    /**
     * The method paints one vertical post.
     * The x position is the fraction times the last pixel.
     * A flash whose post matches this band uses {@link #HEAD}.
     *
     * @param g2 The graphics context is the paint surface.
     * @param width The width is the component width in pixels.
     * @param top The top is the y of the 9-pixel mark.
     * @param at The fraction is where the post sits, such as 0.7 or 0.9.
     * @param ink The ink is the resting color of the post.
     * @param postBand The band is 2 for amber or 3 for red.
     */
    private void paintPost(Graphics2D g2, int width, int top, double at, Color ink, int postBand) {
        int x = (int) Math.round(at * (width - 1));
        if (x < 0) {
            x = 0;
        }
        if (x > width - 1) {
            x = width - 1;
        }
        Color color = ink;
        if (flashTicks > 0 && flashPost == postBand) {
            color = HEAD;
        }
        g2.setColor(color);
        g2.fillRect(x, top, 1, MARK_H);
    }

    /**
     * The method stops the timer after the frame closes.
     */
    void stopBeat() {
        timer.stop();
    }
}
