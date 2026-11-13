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

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JTextArea;
import javax.swing.text.View;

/**
 * The {@code SoftWrapArea} class is a text area that wraps at a character column while soft wrap is on.
 * Word wrap starts on.
 * The column count starts at 80.
 * A column count below 1 is stored as 1, so the area does not force a zero width.
 * Soft wrap off leaves the text on one line.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#SoftWrapArea
 */
// @aa-sdd PK-732 docs/specs/common/stages.spec.md
// @aa-sdd GK-1285 aetheris-plan-stage-1-tweaks.004.md
// @aa-sdd PK-733 docs/specs/common/stages.spec.md
// @aa-sdd PK-37 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd GK-1395 aetheris-pre-plan-stage-1-tweaks.004.md
// @aa-sdd PK-50 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-120 docs/specs/java/org/aetheris/swing/swing.spec.md
public class SoftWrapArea extends JTextArea {

    private boolean softWrap;
    private int columns;
    private boolean sizing;

    /**
     * The constructor turns word wrap on at 80 columns.
     * Line wrap and word wrap are both set on the text area.
     */
    public SoftWrapArea() {
        this.softWrap = true;
        this.columns = 80;
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    /**
     * The method turns soft wrap on or off.
     * Line wrap and word wrap follow the same flag.
     * The area is revalidated after the change.
     *
     * @param on The flag is true when the text wraps on words at the column width.
     */
    public void setSoftWrap(boolean on) {
        this.softWrap = on;
        setLineWrap(on);
        setWrapStyleWord(on);
        revalidate();
    }

    /**
     * The method reports whether soft wrap is on.
     *
     * @return The return value is true when wrap is on.
     */
    public boolean isSoftWrap() {
        return softWrap;
    }

    /**
     * The method stores the wrap width in characters.
     * A count below 1 is stored as 1.
     * The area is revalidated after the change.
     *
     * @param count The count is the number of characters in the wrap width.
     */
    public void setWrapColumns(int count) {
        this.columns = count < 1 ? 1 : count;
        revalidate();
    }

    /**
     * The method returns the wrap width in characters.
     * A stored count below 1 is reported as 1.
     *
     * @return The return value is the column count, and it is at least 1.
     */
    public int getWrapColumns() {
        return columns < 1 ? 1 : columns;
    }

    /**
     * The method tells a scroll pane not to stretch this area to the viewport width.
     * The column width is the wrap edge.
     *
     * @return The return value is false.
     */
    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    /**
     * The method ignores a request to scroll this area into view.
     * The inspector pane owns the scroll.
     * Replacing the text does not pull the view back to this area.
     *
     * @param rect The rectangle is the area Swing asked to show, and it is ignored.
     */
    @Override
    public void scrollRectToVisible(Rectangle rect) {
    }

    /**
     * The method returns the preferred size of the area.
     * Soft wrap off returns the superclass size.
     * A nested call that arrives while this method is already sizing also returns the superclass size.
     * Soft wrap on uses the column count in the live font as the width.
     * The height is the wrapped span at that width.
     *
     * @return The return value is that size.
     */
    @Override
    public Dimension getPreferredSize() {
        if (!softWrap || sizing) {
            return super.getPreferredSize();
        }
        sizing = true;
        try {
            int width = columnPixelWidth();
            int height = wrappedHeight(width);
            return new Dimension(width, height);
        } finally {
            sizing = false;
        }
    }

    /**
     * The method returns the wrap width in pixels.
     * The width is the column count times the live character width, plus the left and right insets.
     * A character width below 1 is treated as 8.
     *
     * @return The return value is that pixel width.
     */
    private int columnPixelWidth() {
        int charW = getColumnWidth();
        if (charW < 1) {
            charW = 8;
        }
        Insets insets = getInsets();
        return getWrapColumns() * charW + insets.left + insets.right;
    }

    /**
     * The method returns the wrapped height at the given pixel width.
     * The floor is one row plus the top and bottom insets.
     * A missing UI, or a missing root view, returns that floor.
     * The root view is sized to the width and an unbounded height.
     * A preferred vertical span taller than the floor replaces the floor.
     *
     * @param width The width is the pixel width the text wraps inside.
     * @return The return value is the taller of the floor and the wrapped span.
     */
    private int wrappedHeight(int width) {
        Insets insets = getInsets();
        int height = getRowHeight() + insets.top + insets.bottom;
        if (getUI() == null) {
            return height;
        }
        View root = getUI().getRootView(this);
        if (root == null) {
            return height;
        }
        root.setSize(width, Integer.MAX_VALUE);
        int span = (int) Math.ceil(root.getPreferredSpan(View.Y_AXIS));
        span += insets.top + insets.bottom;
        if (span > height) {
            return span;
        }
        return height;
    }
}
