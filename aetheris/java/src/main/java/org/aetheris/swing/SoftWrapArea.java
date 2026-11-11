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
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 */
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
