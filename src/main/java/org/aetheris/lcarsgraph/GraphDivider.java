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

package org.aetheris.lcarsgraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;

/**
 * The divider is eleven pixels between the picture and the inspector.
 * Two arrow buttons sit around the vertical center.
 * A drag on the bar body moves the share at once.
 * The share stays on the instance.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1474 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
// @aa-sdd L40 aetheris-plan-f-307.001.md FINESSE:f-307
// @aa-sdd L40 aetheris-plan-f-311.001.md FINESSE:f-311
public final class GraphDivider extends JComponent {

    public static final int BREADTH = 11;

    private static final double WEIGHT = 0.72;
    private static final int ARROW_H = 14;
    private static final int ARROW_GAP = 4;
    private static final int ARROW_INSET = 2;
    private static final Color FILL = new Color(0x3A3A3A);
    private static final Color EDGE = new Color(0x8A8A8A);
    private static final Color ARROW_FILL = new Color(0x5C5C5C);
    private static final Color ARROW_HOT = new Color(0x6E6E6E);
    private static final Color ARROW_EDGE = new Color(0x9A9A9A);
    private static final Color ARROW_ICON = new Color(0xDCDCDC);

    private final GraphPane pane;
    private double ratio = WEIGHT;
    private double parked = WEIGHT;
    private boolean parkedHeld;
    private int lastSpan = -1;
    private boolean hotPicture;
    private boolean hotInspector;
    private int pressZone;
    private int pressX;

    public GraphDivider(GraphPane pane) {
        this.pane = pane;
        setOpaque(true);
        setBackground(FILL);
        setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        setToolTipText(" ");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (event.getButton() != MouseEvent.BUTTON1) {
                    return;
                }
                pressX = event.getXOnScreen();
                if (pictureArrow().contains(event.getPoint())) {
                    pressZone = 1;
                } else if (inspectorArrow().contains(event.getPoint())) {
                    pressZone = 2;
                } else {
                    pressZone = 3;
                }
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                if (event.getButton() != MouseEvent.BUTTON1) {
                    return;
                }
                if (pressZone == 1 && pictureArrow().contains(event.getPoint())) {
                    pressUpper();
                } else if (pressZone == 2 && inspectorArrow().contains(event.getPoint())) {
                    pressLower();
                }
                pressZone = 0;
            }

            @Override
            public void mouseExited(MouseEvent event) {
                if (hotPicture || hotInspector) {
                    hotPicture = false;
                    hotInspector = false;
                    repaint();
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                if (pressZone != 3) {
                    return;
                }
                int dx = event.getXOnScreen() - pressX;
                pressX = event.getXOnScreen();
                dragBy(dx);
            }

            @Override
            public void mouseMoved(MouseEvent event) {
                boolean picture = pictureArrow().contains(event.getPoint());
                boolean inspector = inspectorArrow().contains(event.getPoint());
                if (picture != hotPicture || inspector != hotInspector) {
                    hotPicture = picture;
                    hotInspector = inspector;
                    repaint();
                }
                if (picture || inspector) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                }
            }
        });
    }

    public int breadth() {
        return BREADTH;
    }

    public double share() {
        return ratio;
    }

    public int perMille() {
        int value = (int) Math.round(ratio * 1000.0);
        if (value < 0) {
            return 0;
        }
        if (value > 1000) {
            return 1000;
        }
        return value;
    }

    public String shellKey() {
        return null;
    }

    public Color fillColor() {
        return FILL;
    }

    public Color edgeColor() {
        return EDGE;
    }

    public boolean pictureHidden() {
        return ratio <= 0.0;
    }

    public boolean inspectorHidden() {
        return ratio >= 1.0;
    }

    /**
     * A span below 1 does not write.
     * Any other span stores the share at once.
     *
     * @param span The width the caller measured.
     * @param nextPerMille The share to store, from 0 through 1000.
     */
    public void move(int span, int nextPerMille) {
        if (span < 1) {
            return;
        }
        int stored = nextPerMille;
        if (stored < 0) {
            stored = 0;
        }
        if (stored > 1000) {
            stored = 1000;
        }
        ratio = stored / 1000.0;
        settle();
    }

    public void pressUpper() {
        if (ratio >= 1.0) {
            ratio = restoreShare();
            parkedHeld = false;
        } else if (ratio <= 0.0) {
            ratio = 0.0;
        } else {
            parked = ratio;
            parkedHeld = true;
            ratio = 0.0;
        }
        settle();
    }

    public void pressLower() {
        if (ratio <= 0.0) {
            ratio = restoreShare();
            parkedHeld = false;
        } else if (ratio >= 1.0) {
            ratio = 1.0;
        } else {
            parked = ratio;
            parkedHeld = true;
            ratio = 1.0;
        }
        settle();
    }

    /**
     * The method returns the picture width for one host width.
     * A wider host gives 72 percent of the extra width to the picture while both sides are open.
     * A closed side keeps its share.
     *
     * @param hostWidth The host width is the row width in pixels.
     * @return The method returns the picture width.
     */
    int pictureEdge(int hostWidth) {
        int span = hostWidth - BREADTH;
        if (span < 0) {
            span = 0;
        }
        if (lastSpan >= 0 && span != lastSpan) {
            if (ratio > 0.0 && ratio < 1.0 && lastSpan > 0 && span > 0) {
                int oldLeft = (int) Math.round(lastSpan * ratio);
                int extra = span - lastSpan;
                int neu = oldLeft + (int) Math.round(extra * WEIGHT);
                if (neu < 1) {
                    neu = 1;
                }
                if (neu > span - 1) {
                    neu = span - 1;
                }
                ratio = neu / (double) span;
            }
            lastSpan = span;
        } else {
            lastSpan = span;
        }
        int left = span <= 0 ? 0 : (int) Math.round(span * ratio);
        if (left < 0) {
            return 0;
        }
        if (left > span) {
            return span;
        }
        return left;
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        if (event == null) {
            return null;
        }
        if (pictureArrow().contains(event.getPoint())) {
            return "Hide the picture. The inspector takes the width.";
        }
        if (inspectorArrow().contains(event.getPoint())) {
            return "Hide the inspector. The picture takes the width.";
        }
        return "Drag the bar. The picture and the inspector share the width.";
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BREADTH, 160);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(BREADTH, 32);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(BREADTH, Integer.MAX_VALUE);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
            g2.setColor(FILL);
            g2.fillRect(0, 0, width, height);
            g2.setColor(EDGE);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRect(0, 0, width - 1, height - 1);
            paintArrow(g2, pictureArrow(), true, hotPicture);
            paintArrow(g2, inspectorArrow(), false, hotInspector);
        } finally {
            g2.dispose();
        }
    }

    private void dragBy(int dx) {
        int span = openSpan();
        if (span < 1) {
            return;
        }
        ratio = clampShare(ratio + (dx / (double) span));
        int left = (int) Math.round(span * ratio);
        if (left <= 0) {
            ratio = 0.0;
        }
        if (left >= span) {
            ratio = 1.0;
        }
        settle();
    }

    private double restoreShare() {
        if (parkedHeld && parked > 0.0 && parked < 1.0) {
            return parked;
        }
        return WEIGHT;
    }

    private void settle() {
        repaint();
        if (getParent() != null) {
            getParent().revalidate();
            getParent().repaint();
        } else if (pane != null) {
            pane.revalidate();
        }
    }

    private int openSpan() {
        if (getParent() == null) {
            return 0;
        }
        int span = getParent().getWidth() - BREADTH;
        if (span < 0) {
            return 0;
        }
        return span;
    }

    private Rectangle pictureArrow() {
        return arrowAt(true);
    }

    private Rectangle inspectorArrow() {
        return arrowAt(false);
    }

    private Rectangle arrowAt(boolean upper) {
        int width = getWidth();
        int height = getHeight();
        int arrowWidth = width;
        if (arrowWidth > BREADTH) {
            arrowWidth = BREADTH;
        }
        if (arrowWidth < 1) {
            arrowWidth = 1;
        }
        int step = (ARROW_H + ARROW_GAP) / 2;
        int centerX = width / 2;
        int centerY = height / 2;
        int x = centerX - (arrowWidth / 2);
        int y;
        if (upper) {
            y = centerY - step - (ARROW_H / 2);
        } else {
            y = centerY + step - (ARROW_H / 2);
        }
        return new Rectangle(x, y, arrowWidth, ARROW_H);
    }

    private void paintArrow(Graphics2D g2, Rectangle rect, boolean pointsLeft, boolean hot) {
        if (rect.width < 2 || rect.height < 2) {
            return;
        }
        RoundRectangle2D box = new RoundRectangle2D.Double(
                rect.x, rect.y, rect.width, rect.height, 4, 4);
        if (hot) {
            g2.setColor(ARROW_HOT);
        } else {
            g2.setColor(ARROW_FILL);
        }
        g2.fill(box);
        g2.setColor(ARROW_EDGE);
        g2.setStroke(new BasicStroke(1f));
        g2.draw(box);
        int left = rect.x + ARROW_INSET;
        int top = rect.y + ARROW_INSET;
        int right = rect.x + rect.width - ARROW_INSET;
        int bottom = rect.y + rect.height - ARROW_INSET;
        int mid = (top + bottom) / 2;
        int[] xs;
        int[] ys;
        if (pointsLeft) {
            xs = new int[] {left, right, right};
            ys = new int[] {mid, bottom, top};
        } else {
            xs = new int[] {left, right, left};
            ys = new int[] {top, mid, bottom};
        }
        g2.setColor(ARROW_ICON);
        g2.fillPolygon(xs, ys, 3);
    }

    private static double clampShare(double value) {
        if (value < 0.0) {
            return 0.0;
        }
        if (value > 1.0) {
            return 1.0;
        }
        return value;
    }
}
