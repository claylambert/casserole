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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.accessibility.AccessibleContext;
import javax.swing.JComponent;

// @aa-sdd PK-1066 docs/specs/common/formal-plan.spec.md FINESSE:f-226
/**
 * The {@code HeadLockMark} class paints the head lock for one canvas card.
 * The mark is a padlock image.
 * An unlocked padlock is grey and the shackle is open.
 * A locked padlock is green and the shackle is closed.
 * The control sits at the far left of the card button bar, after the turn label.
 * A divider sits between this control and the marks I, E, P, and O.
 * This control is not one of those marks.
 * It does not set {@code aetheris.cellmark}.
 * It does not join the mark list.
 * A left press is reported by the listener the card passes in.
 * The card calls {@link AetherisNotebookPanel} to keep the packed prefix.
 */
// @aa-sdd PK-915 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd GK-2062 aetheris-pre-plan-f-226.001.md
// @aa-sdd PK-666 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1066 docs/specs/common/formal-plan.spec.md
final class HeadLockMark {

    /** The control is 16 pixels wide and 16 pixels tall. */
    private static final int FACE = 16;

    /** Unlocked ink. The bytes are 138, 138, and 138. */
    private static final int OPEN_INK = 0xFF8A8A8A;

    /** Locked ink. The bytes are 0, 255, and 102. */
    private static final int LATCH_INK = 0xFF00FF66;

    /** Divider ink. The bytes are 110, 110, and 110. */
    private static final Color DIVIDER_INK = new Color(0x6e, 0x6e, 0x6e);

    /**
     * Open shackle. The right leg stops at the crown.
     * A hash is ink. A dot is clear.
     */
    private static final String[] OPEN = {
        "......####......",
        ".....#....#.....",
        ".....#..........",
        ".....#..........",
        "................",
        "................",
        "....########....",
        "...#........#...",
        "...#........#...",
        "...#...##...#...",
        "...#...##...#...",
        "...#........#...",
        "....########....",
        "................",
        "................",
        "................"
    };

    /**
     * Closed shackle. Both legs meet the body.
     * A hash is ink. A dot is clear.
     */
    private static final String[] LATCHED = {
        "................",
        "................",
        "......####......",
        ".....#....#.....",
        ".....#....#.....",
        ".....#....#.....",
        "....########....",
        "...#........#...",
        "...#........#...",
        "...#...##...#...",
        "...#...##...#...",
        "...#........#...",
        "....########....",
        "................",
        "................",
        "................"
    };

    /**
     * The constructor is unused.
     * The card calls {@link #control(boolean, MouseListener)} and {@link #divider()}.
     */
    private HeadLockMark() {
    }

    /**
     * The method {@code control} builds the padlock for one card.
     * The image is 16 by 16.
     * The preferred size, the minimum size, and the maximum size are 16 by 16.
     * The control does not take focus.
     * The client property {@code aetheris.headlock} is true.
     * The tip says the lock is separate from I, E, P, and O.
     * The tip says a press keeps a packed prefix from the top of the deck.
     * The tip says excluding a locked turn drops that lock.
     * A non-null listener is added to the control.
     *
     * @param locked true when the shackle is closed and the ink is green
     * @param press the left-press listener, or null when the card adds none
     * @return the padlock control
     */
    static JComponent control(boolean locked, MouseListener press) {
        Face face = new Face(locked);
        if (press != null) {
            face.addMouseListener(press);
        }
        return face;
    }

    /**
     * The method {@code divider} builds the line between the padlock and the marks.
     * The line is one pixel wide.
     * The component is 8 pixels wide and 16 pixels tall.
     * The line is not a button and it does not take focus.
     *
     * @return the divider
     */
    static JComponent divider() {
        return new Divider();
    }

    /**
     * The method {@code image} paints the padlock into a clear image.
     * A locked padlock uses the green ink.
     * An unlocked padlock uses the grey ink.
     * A hash in the row becomes that ink.
     * A dot stays clear.
     *
     * @param locked true when the shackle is closed
     * @return the 16 by 16 image
     */
    private static BufferedImage image(boolean locked) {
        String[] rows = locked ? LATCHED : OPEN;
        int ink = locked ? LATCH_INK : OPEN_INK;
        BufferedImage image = new BufferedImage(FACE, FACE, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < FACE; y++) {
            String row = rows[y];
            for (int x = 0; x < FACE; x++) {
                if (row.charAt(x) == '#') {
                    image.setRGB(x, y, ink);
                }
            }
        }
        return image;
    }

    /**
     * The {@code Face} class is the padlock control.
     * The image is painted at the origin.
     * The control is flat.
     */
    private static final class Face extends JComponent {

        private final BufferedImage plate;

        /**
         * The constructor stores the padlock and the tip.
         * JDK 26 leaves {@link JComponent#getAccessibleContext()} null, so this control creates the context.
         *
         * @param locked true when the shackle is closed
         */
        private Face(boolean locked) {
            this.plate = image(locked);
            setOpaque(false);
            setFocusable(false);
            setRequestFocusEnabled(false);
            Dimension size = new Dimension(FACE, FACE);
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            putClientProperty("aetheris.headlock", Boolean.TRUE);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            AccessibleContext access = new AccessibleJComponent() {
            };
            this.accessibleContext = access;
            access.setAccessibleName(locked ? "Locked" : "Unlocked");
            setToolTipText("<html>The lock is separate from I, E, P, and O."
                    + "<br>A press keeps a packed prefix from the top of the deck."
                    + "<br>Excluding a locked turn drops that lock.</html>");
        }

        /**
         * The method paints the stored padlock.
         * Nearest-neighbor keeps the pixels square.
         * The graphics copy is disposed.
         *
         * @param g the graphics to paint into
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g2.drawImage(plate, 0, 0, FACE, FACE, null);
            } finally {
                g2.dispose();
            }
        }
    }

    /**
     * The {@code Divider} class is the line between the padlock and the marks.
     * The line is not a mark button.
     */
    // @aa-sdd GK-1274 aetheris-plan-stage-1-tweaks.003.md
    // @aa-sdd PK-31 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-45 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-746 docs/specs/common/stages.spec.md
    // @aa-sdd PK-119 docs/specs/java/org/aetheris/swing/swing.spec.md
    private static final class Divider extends JComponent {

        /**
         * The constructor fixes the size at 8 by 16.
         */
        private Divider() {
            setOpaque(false);
            setFocusable(false);
            Dimension size = new Dimension(8, FACE);
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
        }

        /**
         * The method paints one vertical line.
         * The line sits at the center of the component.
         * The graphics copy is disposed.
         *
         * @param g the graphics to paint into
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setColor(DIVIDER_INK);
                int x = getWidth() / 2;
                int bottom = getHeight() - 3;
                if (bottom < 2) {
                    bottom = 2;
                }
                g2.drawLine(x, 2, x, bottom);
            } finally {
                g2.dispose();
            }
        }
    }
}
