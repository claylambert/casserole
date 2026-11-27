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

package org.casserole.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * One spinner for a wait.
 * The word is thinking on a canvas send and rendering on a graph walk.
 * The layout keeps the glyph and the word on one row, and the row can go wide.
 * The glyph cycles {@code |}, {@code /}, {@code -}, and {@code \} every {@code 120} milliseconds.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1478 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public final class ReasonSpinner extends JPanel {

    /**
     * The clock a host already started.
     * A graph walk does not use this. It passes a millisecond to {@link #startAt(long)}.
     */
    public interface Start {
        /**
         * @return the millisecond the wait began, or {@code 0} when the clock has not started
         */
        long startedAtMs();
    }

    private static final Color GLYPH_INK = new Color(0x00FF66);
    private static final Color WORD_INK = new Color(0x00D7FF);

    private final JLabel glyph;
    private final JLabel words;
    private final Timer timer;
    private final String word;
    private final String[] frames = new String[] {"|", "/", "-", "\\"};
    private int frame;
    private long ownStartedAtMs;
    private Start follow;

    /**
     * The constructor builds the glyph, the words, and the timer.
     * The panel stays hidden until a start method runs.
     *
     * @param word the word beside the clock. A blank word is thinking.
     */
    public ReasonSpinner(String word) {
        super(new BorderLayout(6, 0));
        this.word = word == null || word.trim().length() == 0 ? "thinking" : word.trim();
        glyph = new JLabel("|");
        words = new JLabel(this.word + " 0:00");
        glyph.setHorizontalAlignment(SwingConstants.CENTER);
        words.setHorizontalAlignment(SwingConstants.LEFT);
        glyph.setForeground(GLYPH_INK);
        words.setForeground(WORD_INK);
        add(glyph, BorderLayout.WEST);
        add(words, BorderLayout.CENTER);
        fit();
        setVisible(false);
        timer = new Timer(120, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                paintFrame();
            }
        });
    }

    /**
     * The method keeps the glyph and the word on one row at the new face.
     * The row can go wide.
     *
     * @param font the face. A null face is ignored.
     */
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (glyph == null || words == null || font == null) {
            return;
        }
        glyph.setFont(font);
        words.setFont(font);
        fit();
    }

    /**
     * @return the glyph label, so a host can apply its theme
     */
    public JLabel glyphLabel() {
        return glyph;
    }

    /**
     * @return the words label, so a host can apply its theme
     */
    public JLabel wordsLabel() {
        return words;
    }

    /**
     * The method shows the spinner on a clock the host already started.
     * A timer that is already running returns without a reset.
     *
     * @param start the clock. A null clock formats as zero.
     */
    public void startSpin(Start start) {
        if (timer.isRunning()) {
            return;
        }
        follow = start;
        ownStartedAtMs = 0L;
        frame = 0;
        setVisible(true);
        paintFrame();
        timer.start();
        revalidate();
    }

    /**
     * The method shows the spinner on a clock for this wait.
     * A timer that is already running keeps running and takes the new start.
     *
     * @param startedAtMs the {@link System#currentTimeMillis()} value for this wait
     */
    public void startAt(long startedAtMs) {
        follow = null;
        ownStartedAtMs = startedAtMs;
        frame = 0;
        setVisible(true);
        paintFrame();
        if (!timer.isRunning()) {
            timer.start();
        }
        revalidate();
    }

    /**
     * The method paints the glyph and the word for this frame.
     */
    private void paintFrame() {
        long at = follow == null ? ownStartedAtMs : follow.startedAtMs();
        glyph.setForeground(GLYPH_INK);
        words.setForeground(WORD_INK);
        glyph.setText(frames[frame % frames.length]);
        words.setText(word + " " + ThinkingClock.format(at));
        frame++;
        fit();
        repaint();
    }

    /**
     * The method sizes the glyph and the word to one row of the current face.
     * The row can go wide. The height stays one line.
     */
    private void fit() {
        if (glyph == null || words == null) {
            return;
        }
        Font font = words.getFont();
        if (font == null) {
            return;
        }
        FontMetrics metrics = words.getFontMetrics(font);
        String shown = words.getText();
        if (shown == null || shown.length() == 0) {
            shown = word + " 0:00";
        }
        int high = metrics.getHeight();
        if (high < 1) {
            high = 1;
        }
        int wordsWide = metrics.stringWidth(shown) + 4;
        if (wordsWide < 1) {
            wordsWide = 1;
        }
        Dimension wordsBox = new Dimension(wordsWide, high);
        words.setPreferredSize(wordsBox);
        words.setMinimumSize(wordsBox);
        words.setMaximumSize(new Dimension(Integer.MAX_VALUE, high));
        Font glyphFont = glyph.getFont() == null ? font : glyph.getFont();
        FontMetrics glyphMetrics = glyph.getFontMetrics(glyphFont);
        int box = glyphMetrics.charWidth('M');
        for (int i = 0; i < frames.length; i++) {
            int wide = glyphMetrics.stringWidth(frames[i]);
            if (wide > box) {
                box = wide;
            }
        }
        Dimension fixed = new Dimension(box + 2, high);
        glyph.setPreferredSize(fixed);
        glyph.setMinimumSize(fixed);
        glyph.setMaximumSize(fixed);
        Dimension self = new Dimension(fixed.width + 6 + wordsWide, high);
        setPreferredSize(self);
        setMinimumSize(self);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, high));
    }

    /**
     * The method hides the spinner and clears its clock.
     */
    public void stopSpin() {
        ownStartedAtMs = 0L;
        follow = null;
        timer.stop();
        glyph.setText("");
        words.setText("");
        setVisible(false);
        revalidate();
    }
}
