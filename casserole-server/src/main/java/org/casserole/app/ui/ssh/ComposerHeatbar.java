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
 * The {@code ComposerHeatbar} type is the Swing component that paints a token fill against a context window.
 * The name Swing means the Java library that draws the component.
 * The track is {@code 5} pixels tall.
 * The needle is {@code 2} pixels wide.
 * The needle is {@code 9} pixels tall.
 * The amber post marks {@code 70} percent of the window.
 * The red post marks {@code 90} percent of the window.
 * The needle rests on the true fill.
 * The needle lunges to the right when the count changes.
 * The head ink flashes for {@code 8} timer pulses when the fill crosses into a higher band.
 * The same flash marks a settled model count after the first paint.
 * The keystroke path stays on the character heuristic.
 * The type does not call the network.
 *
 * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
 * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
 * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
 * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
 * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
 * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
 * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
 * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
 * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
 * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
 * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
 * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
 * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
 * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
 * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
 * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
 * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
 * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
 * @author clayl (2026-11-01)
 */
final class ComposerHeatbar extends JComponent {

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
     * The constructor builds an empty beat with no count stored yet.
     * The component is not opaque.
     * The preferred size is {@code 240} by {@code 9} pixels.
     * The minimum size is {@code 80} by {@code 9} pixels.
     * The tooltip says the beat is the token fill against the context window.
     * The tooltip says amber is {@code 70} percent and red is {@code 90} percent.
     * The tooltip says the needle rests on the estimate.
     * The timer pulses every {@code 16} milliseconds.
     */
    ComposerHeatbar() {
        setOpaque(false);
        setPreferredSize(new Dimension(240, MARK_H));
        setMinimumSize(new Dimension(80, MARK_H));
        setToolTipText("The beat is the token fill against the context window. "
                + "Amber is 70 percent. Red is 90 percent. The needle rests on the estimate.");
        /**
         * The listener is the timer pulse.
         * The pulse calls {@link #tick}.
         */
        timer = new Timer(16, new ActionListener() {
            // @aa-sdd PK-0096 PK-0396 PK-0437 docs/specs/java/org/casserole/casserole.spec.md
            @Override
            public void actionPerformed(ActionEvent event) {
                tick();
            }
        });
    }

    /**
     * The showHeuristic method stores a character-count estimate on the needle.
     * The method stores a negative token count as {@code 0}.
     * The method stores a negative window as {@code 0}.
     * The method returns without a change when a model count is already held, the estimate matches the last estimate, and the window matches the stored budget.
     * The method returns without a change when no model count is held and both the shown count and the budget are unchanged.
     * The method clears the model-held flag on every update that is not returned early.
     * The first call paints the count and does not lunge.
     * The later call lunges when the count differs.
     * The window value {@code 0} paints an empty fill.
     * The posts are still drawn for that window.
     *
     * @param tokens The heuristic token count.
     * @param window The context window. The value {@code 0} paints an empty fill and still draws the posts.
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
     * The showSettled method stores a model count after the text has settled.
     * The count is {@code prompt_eval_count} from the model reply.
     * The method stores a negative count as {@code 0}.
     * The method does not change the stored budget.
     * The method does not open a network call.
     * The method marks the count as a model count.
     * The first call paints the count and does not flash.
     * The later call flashes the head ink even when the count is unchanged.
     * The flash also recolors the amber post or the red post when the band changes to that post.
     *
     * @param tokens The {@code prompt_eval_count} value from the model reply.
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
     * The word method returns the label for the count now on the needle.
     * The text is {@code est. tokens: } followed by the stored count.
     * The method does not clamp a negative count, because the show methods already store {@code 0}.
     *
     * @return The return value is that label.
     */
    String word() {
        return "est. tokens: " + shown;
    }

    /**
     * The ink method returns the color of the fill that is on the needle now.
     * The method returns {@link #HEAD} while the flash pulses remain.
     * The method returns the band color for the current fill when the flash is finished.
     * The empty band uses {@link #TRACK}.
     *
     * @return The return value is the fill color, or the head color while the beat is flashing.
     */
    Color ink() {
        if (flashTicks > 0) {
            return HEAD;
        }
        return bandInk(band(fill(shown, budget)));
    }

    /**
     * The seat method stores the count and starts the lunge or the flash.
     * The method records the band before it replaces the shown count.
     * The first call marks the bar as shown, repaints, and returns without a lunge or a flash.
     * The method sets the lunge distance to {@code 6} when the count moved.
     * The method adopts the new band as the flash post when the caller passed {@code 0}, the band changed, and the new band is above {@code 0}.
     * The method starts an {@code 8} pulse flash when the post is above {@code 0} or the settled flag is true.
     * The method then starts the timer and repaints.
     *
     * @param tokens The count to store on the needle.
     * @param moved The count differs from the count already shown when the flag is true.
     * @param settledFlash The settled model path wants a flash even without a post change when the flag is true.
     * @param post The post band to flash. The value {@code 0} means no post was chosen yet.
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
     * The arm method starts the timer when it is not already running.
     * The running timer is left as it is.
     */
    private void arm() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * The tick method advances one timer pulse.
     * The lunge is multiplied by {@code 0.72} while it is above {@code 0.4}.
     * The lunge becomes {@code 0} when it is not above {@code 0.4}.
     * The method reduces a remaining flash pulse by one.
     * The method repaints on every pulse.
     * The method stops the timer when the lunge is finished and no flash pulse remains.
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
     * The fill method returns the fraction of the context window that the count uses.
     * The method returns {@code 0} when the window is under {@code 1}.
     * The method returns {@code 0} when the count is {@code 0} or negative.
     * The method returns {@code 1} when the count is at least the window.
     * The method returns the count divided by the window for every other pair.
     *
     * @param used The token count on the needle.
     * @param window The context window.
     * @return The return value is a fraction from {@code 0} through {@code 1}.
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
     * The band method names the color band for a fill fraction.
     * The method returns {@code 0} when the fraction is {@code 0} or negative.
     * The method returns {@code 3} when the fraction is {@code 0.9} or higher.
     * The method returns {@code 2} when the fraction is {@code 0.7} or higher and under {@code 0.9}.
     * The method returns {@code 1} for every other positive fraction.
     *
     * @param amount The fill fraction from {@link #fill}.
     * @return The return value is {@code 0}, {@code 1}, {@code 2}, or {@code 3}.
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
     * The bandInk method returns the color for a band number.
     * The method returns {@link #RED} when the band is {@code 3} or higher.
     * The method returns {@link #AMBER} when the band is {@code 2}.
     * The method returns {@link #GREEN} when the band is {@code 1}.
     * The method returns {@link #TRACK} for every other band.
     *
     * @param band The band number from {@link #band}.
     * @return The return value is the color for that band.
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
     * The paintComponent method draws the track, the fill, the two posts, and the needle.
     * The paint turns antialiasing off.
     * The method returns without drawing when the width is under {@code 8} pixels.
     * The method returns without drawing when the height is under {@code 9} pixels.
     * The {@code 9} pixel mark is centered in the height.
     * The track is centered inside that mark.
     * The fill edge is the fill fraction times the last pixel column, rounded and clamped to the width.
     * The needle sits at that edge plus the rounded lunge, and it is clamped so the {@code 2} pixel needle stays inside the width.
     * The track is drawn across the full width in {@link #TRACK}.
     * The fill is drawn only when the edge is past {@code 0} and the band is above {@code 0}.
     * The fill uses {@link #HEAD} while a flash is running.
     * The fill uses the band color when no flash is running.
     * The amber post is drawn at {@code 70} percent.
     * The red post is drawn at {@code 90} percent.
     * The needle is drawn in {@link #HEAD}.
     *
     * @param graphics The Swing graphics context for this component.
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
     * The paintPost method draws one vertical post.
     * The column is the fraction times the last pixel, rounded and clamped to the width.
     * The post is {@code 1} pixel wide and {@code 9} pixels tall.
     * The post uses {@link #HEAD} when a flash is running and the flash post equals this post band.
     * The post uses the given ink on every other call.
     *
     * @param g2 The graphics context that receives the rectangle.
     * @param width The component width in pixels.
     * @param top The top pixel of the {@code 9} pixel mark.
     * @param at The fraction along the width. The amber post uses {@code 0.7} and the red post uses {@code 0.9}.
     * @param ink The ordinary color of this post.
     * @param postBand The band number this post flashes on. Amber is {@code 2} and red is {@code 3}.
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
     * The stopBeat method stops the timer.
     * The caller uses this when the frame closes so the timer does not keep pulsing.
     */
    void stopBeat() {
        timer.stop();
    }
}
