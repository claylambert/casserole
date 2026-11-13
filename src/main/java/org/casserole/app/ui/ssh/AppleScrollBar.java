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

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicScrollPaneUI;

// @aa-sdd GK-0741 aetheris-plan-f-84.001.md FINESSE:f-84
// @aa-sdd GK-0742 aetheris-plan-f-85.001.md FINESSE:f-85
// @aa-sdd GK-0743 aetheris-plan-f-86.001.md FINESSE:f-86
// @aa-sdd GK-0744 aetheris-plan-f-87.001.md FINESSE:f-87
/**
 * The {@code AppleScrollBar} paints overlay scroll bars.
 * A bar floats on the edge of a {@code JScrollPane} instead of reserving a gutter.
 * {@link #install} arms the vertical bar and the horizontal bar of one pane.
 * {@link #armStrip} arms one bar whose parent sets the bounds.
 *
 * The wide bar is {@link #BAR_PX} pixels.
 * The thin bar is {@link #THIN_PX} pixels.
 * The handle length is {@link #thumbSpan}.
 * The fade lasts {@link #ANIM_SEC} seconds.
 * The painted fade uses a cubic-out curve.
 * The track color is {@link #PLATE}.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#AppleScrollBar
 *
 * @author clayl (2026-10-31)
 */
// @aa-sdd PK-186 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd GK-1497 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-1498 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-1499 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-248 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-249 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-249 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-250 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd GK-2984 aetheris-plan-f-84.001.md
// @aa-sdd PK-232 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd GK-2987 aetheris-plan-f-85.001.md
// @aa-sdd PK-233 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd GK-2989 aetheris-plan-f-86.001.md
// @aa-sdd PK-233 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd GK-2991 aetheris-plan-f-87.001.md
// @aa-sdd PK-234 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
public final class AppleScrollBar {

    /**
     * The {@code BAR_PX} constant is the wide bar thickness, in pixels.
     * The value is fifteen.
     * A pointer on the bar fades the thickness toward this width.
     * The hit strip for a parent-laid bar uses this same thickness.
     */
    public static final int BAR_PX = 15;

    /**
     * The {@code THIN_PX} constant is the thin bar thickness, in pixels.
     * The value is two.
     * A pointer on the pane, and off the bar, fades the thickness toward this width.
     */
    public static final float THIN_PX = 2f;

    /**
     * The {@code HANDLE_MIN} constant is twelve pixels.
     * {@link #thumbSpan} chooses the painted handle length.
     */
    public static final int HANDLE_MIN = 12;

    /**
     * The {@code THUMB_SHARE} constant is the handle's share of the track.
     * The value is forty percent.
     * The handle length does not follow the scroll range.
     * {@link AppleBarUi#seatThumb} still maps the scroll value along the remaining travel.
     */
    public static final float THUMB_SHARE = 0.40f;

    /**
     * The {@code thumbSpan} method returns the handle length for one track.
     * A track below one pixel returns zero.
     * Any other track multiplies its length by {@link #THUMB_SHARE} and rounds to the nearest pixel.
     * A rounded length below one pixel becomes one pixel.
     * A rounded length above the track becomes the track length.
     *
     * @param track The track length on this axis, in pixels.
     * @return The handle length, in pixels.
     */
    public static int thumbSpan(int track) {
        if (track < 1) {
            return 0;
        }
        int span = Math.round(track * THUMB_SHARE);
        if (span < 1) {
            span = 1;
        }
        if (span > track) {
            span = track;
        }
        return span;
    }

    /**
     * The {@code RADIUS_PX} constant is the corner radius, in pixels.
     * The value is six.
     * {@link #fill} uses twice this value as the arc, then clamps the arc to the painted width and height.
     */
    public static final float RADIUS_PX = 6f;

    /**
     * The {@code ANIM_SEC} constant is the fade time, in seconds.
     * The value is two tenths of a second.
     * {@link #step} moves the linear fade by the elapsed time divided by this value.
     */
    public static final float ANIM_SEC = 0.2f;

    /**
     * The {@code PLATE} constant is the track color.
     * The value is {@code 0x0d0d0d}.
     * The painted track uses this color.
     * A parent-laid bar also uses it as the bar background.
     */
    public static final int PLATE = 0x0d0d0d;

    private static final String MARK = "casserole.apple.scroll";

    private static final String STRIP = "casserole.apple.strip";

    private static final String MOTION = "casserole.apple.motion";

    private static final int INACTIVE = 215;

    private static final int HOVERED = 245;

    private static final int ACTIVE = 255;

    private static final float ACTIVE_HANDLE = 0.6f;

    private static final float ACTIVE_TRACK = 0.4f;

    private static final float INTERACT_TRACK = 0.7f;

    private static final float DT_CAP = 0.05f;

    private static final List<WeakReference<JComponent>> PANES = new ArrayList<WeakReference<JComponent>>();

    private static AWTEventListener ear;

    /**
     * The constructor stops callers from creating an instance.
     * The body does nothing.
     * Callers use {@link #install} or {@link #armStrip}.
     */
    private AppleScrollBar() {
    }

    /**
     * The {@code install} method arms both bars of one scroll pane.
     * A null pane returns immediately.
     * A pane whose client property {@code casserole.apple.scroll} is already true returns immediately.
     * Any other pane stores true in that property.
     * The pane UI becomes an {@link ApplePaneUi}.
     *
     * @param pane The scroll pane to arm, or null.
     */
    public static void install(JScrollPane pane) {
        if (pane == null) {
            return;
        }
        if (Boolean.TRUE.equals(pane.getClientProperty(MARK))) {
            return;
        }
        pane.putClientProperty(MARK, Boolean.TRUE);
        pane.setUI(new ApplePaneUi());
    }

    /**
     * The {@code armStrip} method arms one bar whose parent sets the bounds.
     * A null component returns immediately.
     * A null bar returns immediately.
     * A bar whose client property {@code casserole.apple.strip} is already true returns immediately.
     *
     * Any other bar stores true in that property.
     * The bar UI becomes an {@link AppleBarUi}.
     * The bar is opaque.
     * The background is {@link #PLATE}.
     * The border is removed.
     * The bar does not take focus.
     * {@link AppleMotion#watchStrip} starts the fade for that component and bar.
     *
     * @param host The component the pointer is tested against.
     * @param bar The bar to arm.
     */
    static void armStrip(JComponent host, JScrollBar bar) {
        if (host == null || bar == null) {
            return;
        }
        if (Boolean.TRUE.equals(bar.getClientProperty(STRIP))) {
            return;
        }
        bar.putClientProperty(STRIP, Boolean.TRUE);
        bar.setUI(new AppleBarUi());
        bar.setOpaque(true);
        bar.setBackground(new Color(PLATE));
        bar.setBorder(null);
        bar.setFocusable(false);
        bar.setRequestFocusEnabled(false);
        AppleMotion.watchStrip(host, bar);
    }

    // @aa-sdd GK-0745 aetheris-plan-f-84.001.md FINESSE:f-84
    // @aa-sdd GK-0746 aetheris-plan-f-85.001.md FINESSE:f-85
    /**
     * The {@code AppleLayout} lays a scroll pane out so the bars float.
     * The viewport keeps the full inner area.
     * A visible bar is placed on the edge of that area.
     * The four corner components are given empty bounds.
     */
    private static final class AppleLayout extends javax.swing.ScrollPaneLayout {

        /**
         * The {@code layoutContainer} method runs the stock layout and then pulls the bars back over the viewport.
         * A null viewport returns after the stock layout.
         * {@link #absorb} extends the viewport across a bar that sits outside it.
         * The vertical bar is absorbed first.
         * The horizontal bar is absorbed second.
         * The viewport bounds are then replaced with that extended rectangle.
         *
         * {@link #place} puts the vertical bar on the left or the right from the component orientation.
         * The horizontal bar is placed on the bottom.
         * That horizontal call does not consult the component orientation.
         * {@link #raise} brings the horizontal bar forward, then the vertical bar.
         * The vertical bar ends in front when both moves run.
         * {@link #zero} clears the lower-left, lower-right, upper-left, and upper-right corners.
         *
         * @param parent The scroll pane being laid out.
         */
        @Override
        public void layoutContainer(Container parent) {
            super.layoutContainer(parent);
            if (viewport == null) {
                return;
            }
            Rectangle vb = viewport.getBounds();
            absorb(vb, vsb, true);
            absorb(vb, hsb, false);
            viewport.setBounds(vb);
            place(vsb, vb, true, parent.getComponentOrientation().isLeftToRight());
            place(hsb, vb, false, true);
            // The viewport is added first, so it paints last and takes the hit.
            // A floating bar has to sit above that view or the strip never sees the pointer.
            raise(parent, hsb);
            raise(parent, vsb);
            zero(lowerLeft);
            zero(lowerRight);
            zero(upperLeft);
            zero(upperRight);
        }

        /**
         * The {@code absorb} method extends the viewport rectangle across a bar that the stock layout parked outside it.
         * A null bar returns immediately.
         * An invisible bar returns immediately.
         *
         * A vertical bar with a positive width that starts at or beyond the viewport's right edge extends the viewport width to the bar's right edge.
         * A vertical bar with a positive width that ends at or before the viewport's left edge moves the viewport's left edge to the bar and keeps the old right edge.
         * A vertical bar that overlaps the viewport leaves the rectangle unchanged.
         *
         * A horizontal bar with a positive height that starts at or beyond the viewport's bottom edge extends the viewport height to the bar's bottom edge.
         * A horizontal bar above the viewport, or overlapping it, leaves the rectangle unchanged.
         *
         * @param vb The viewport rectangle. The method writes into this object.
         * @param bar The bar that may sit outside the viewport.
         * @param vertical True for the upright bar. False for the bottom bar.
         */
        private static void absorb(Rectangle vb, JScrollBar bar, boolean vertical) {
            if (bar == null || !bar.isVisible()) {
                return;
            }
            Rectangle b = bar.getBounds();
            if (vertical) {
                if (b.width > 0 && b.x >= vb.x + vb.width) {
                    vb.width = b.x + b.width - vb.x;
                } else if (b.width > 0 && b.x + b.width <= vb.x) {
                    int right = vb.x + vb.width;
                    vb.x = b.x;
                    vb.width = right - vb.x;
                }
            } else if (b.height > 0 && b.y >= vb.y + vb.height) {
                vb.height = b.y + b.height - vb.y;
            }
        }

        /**
         * The {@code place} method sets the bar bounds on the viewport edge.
         * A null bar returns immediately.
         * An invisible bar is given bounds of zero on every side and then returns.
         *
         * A vertical bar uses a thickness of {@link #BAR_PX}, limited to the viewport width.
         * A thickness below one pixel returns without changing the bar.
         * A left-to-right pane puts that bar on the right edge.
         * A right-to-left pane puts that bar on the left edge.
         * The vertical bar's height is the viewport height.
         *
         * A horizontal bar uses a thickness of {@link #BAR_PX}, limited to the viewport height.
         * A thickness below one pixel returns without changing the bar.
         * The bar sits on the bottom edge.
         * The {@code leftToRight} flag is ignored for a horizontal bar.
         * The horizontal bar's width is the viewport width.
         *
         * @param bar The bar to move.
         * @param vb The viewport rectangle after {@link #absorb}.
         * @param vertical True for the upright bar.
         * @param leftToRight True when the upright bar belongs on the right.
         */
        private static void place(JScrollBar bar, Rectangle vb, boolean vertical, boolean leftToRight) {
            if (bar == null) {
                return;
            }
            if (!bar.isVisible()) {
                bar.setBounds(0, 0, 0, 0);
                return;
            }
            if (vertical) {
                int thick = BAR_PX;
                if (thick > vb.width) {
                    thick = vb.width;
                }
                if (thick < 1) {
                    return;
                }
                if (leftToRight) {
                    bar.setBounds(vb.x + vb.width - thick, vb.y, thick, vb.height);
                } else {
                    bar.setBounds(vb.x, vb.y, thick, vb.height);
                }
            } else {
                int thick = BAR_PX;
                if (thick > vb.height) {
                    thick = vb.height;
                }
                if (thick < 1) {
                    return;
                }
                bar.setBounds(vb.x, vb.y + vb.height - thick, vb.width, thick);
            }
        }

        /**
         * The {@code raise} method moves a visible bar to the front of its parent.
         * A null parent returns immediately.
         * A null bar returns immediately.
         * An invisible bar returns immediately.
         * A bar whose parent is different returns immediately.
         * A bar whose z-order is already zero stays there.
         * Any other bar is moved to z-order zero.
         *
         * @param parent The scroll pane.
         * @param bar The bar to bring forward.
         */
        private static void raise(Container parent, JScrollBar bar) {
            if (parent == null || bar == null || !bar.isVisible()) {
                return;
            }
            if (bar.getParent() != parent) {
                return;
            }
            if (parent.getComponentZOrder(bar) > 0) {
                parent.setComponentZOrder(bar, 0);
            }
        }

        /**
         * The {@code zero} method gives a corner empty bounds.
         * A null corner returns immediately.
         * Any other corner is set to zero width and zero height at the origin.
         *
         * @param corner The corner component, or null.
         */
        private static void zero(Component corner) {
            if (corner != null) {
                corner.setBounds(0, 0, 0, 0);
            }
        }
    }

    // @aa-sdd GK-0747 aetheris-plan-f-84.001.md FINESSE:f-84
    /**
     * The {@code ApplePaneUi} installs the overlay layout and both bar painters.
     * {@link #installUI} replaces the layout, arms both bars, and starts the fade.
     * {@link #uninstallUI} stops the fade before the stock uninstall.
     */
    private static final class ApplePaneUi extends BasicScrollPaneUI {

        /**
         * The {@code installUI} method keeps the stock scroll-pane install and then switches to the overlay.
         * The component is cast to a scroll pane.
         * The layout becomes an {@link AppleLayout}.
         * {@link #arm} paints the vertical bar.
         * {@link #arm} paints the horizontal bar.
         * {@link AppleMotion#watch} starts the fade for the pane.
         *
         * @param c The scroll pane. The method casts it.
         */
        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            JScrollPane pane = (JScrollPane) c;
            pane.setLayout(new AppleLayout());
            arm(pane.getVerticalScrollBar());
            arm(pane.getHorizontalScrollBar());
            AppleMotion.watch(pane);
        }

        /**
         * The {@code uninstallUI} method stops the fade and then runs the stock uninstall.
         * A scroll pane is passed to {@link AppleMotion#forget}.
         * Any other component skips that call.
         *
         * @param c The component losing this UI.
         */
        @Override
        public void uninstallUI(JComponent c) {
            if (c instanceof JScrollPane) {
                AppleMotion.forget((JScrollPane) c);
            }
            super.uninstallUI(c);
        }

        /**
         * The {@code arm} method installs the overlay painter on one pane bar.
         * A null bar returns immediately.
         * The bar UI becomes an {@link AppleBarUi}.
         * The bar is not opaque.
         * The border is removed.
         * The bar does not take focus.
         *
         * @param bar The vertical bar or the horizontal bar.
         */
        private static void arm(JScrollBar bar) {
            if (bar == null) {
                return;
            }
            bar.setUI(new AppleBarUi());
            bar.setOpaque(false);
            bar.setBorder(null);
            bar.setFocusable(false);
            bar.setRequestFocusEnabled(false);
        }
    }

    // @aa-sdd GK-0748 aetheris-plan-f-84.001.md FINESSE:f-84
    // @aa-sdd GK-0749 aetheris-plan-f-87.001.md FINESSE:f-87
    /**
     * The {@code AppleBarUi} paints one floating bar.
     * A press on the handle grabs that handle.
     * A press on the track jumps the handle so the pointer is at its center.
     * The buttons at the ends of the bar are hidden.
     */
    private static final class AppleBarUi extends BasicScrollBarUI {

        private boolean hot;

        private boolean pressed;

        private int grab;

        private float linear;

        /**
         * The {@code installDefaults} method keeps the stock defaults and then clears the chrome.
         * The bar is not opaque.
         * The border is removed.
         * The bar does not take focus.
         */
        @Override
        protected void installDefaults() {
            super.installDefaults();
            scrollbar.setOpaque(false);
            scrollbar.setBorder(null);
            scrollbar.setFocusable(false);
            scrollbar.setRequestFocusEnabled(false);
        }

        /**
         * The {@code getPreferredSize} method reports a thickness only for a parent-laid bar.
         * A null component returns zero width and zero height.
         * A component whose client property {@code casserole.apple.strip} is not true returns zero width and zero height.
         *
         * A horizontal bar with that property returns zero width and a height of {@link #BAR_PX}.
         * Any other component with that property returns a width of {@link #BAR_PX} and zero height.
         *
         * @param c The bar, or null.
         * @return The preferred size.
         */
        @Override
        public Dimension getPreferredSize(JComponent c) {
            if (c != null && Boolean.TRUE.equals(c.getClientProperty(STRIP))) {
                int thick = BAR_PX;
                if (c instanceof JScrollBar && ((JScrollBar) c).getOrientation() == JScrollBar.HORIZONTAL) {
                    return new Dimension(0, thick);
                }
                return new Dimension(thick, 0);
            }
            return new Dimension(0, 0);
        }

        /**
         * The {@code getMinimumThumbSize} method allows a one-pixel handle.
         * The returned width is one.
         * The returned height is one.
         * The painted length still comes from {@link AppleScrollBar#thumbSpan}.
         *
         * @return A one-by-one size.
         */
        @Override
        protected Dimension getMinimumThumbSize() {
            return new Dimension(1, 1);
        }

        /**
         * The {@code layoutVScrollbar} method runs the stock vertical layout.
         * {@link #seatThumb} then sets the upright handle.
         *
         * @param sb The upright bar.
         */
        @Override
        protected void layoutVScrollbar(JScrollBar sb) {
            super.layoutVScrollbar(sb);
            seatThumb(true);
        }

        /**
         * The {@code layoutHScrollbar} method runs the stock horizontal layout.
         * {@link #seatThumb} then sets the bottom handle.
         *
         * @param sb The bottom bar.
         */
        @Override
        protected void layoutHScrollbar(JScrollBar sb) {
            super.layoutHScrollbar(sb);
            seatThumb(false);
        }

        /**
         * The {@code seatThumb} method keeps the handle at {@link AppleScrollBar#thumbSpan} and maps the value along the travel.
         * A null bar returns immediately.
         * A null track returns immediately.
         * The track length is the track height for an upright bar.
         * The track length is the track width for a bottom bar.
         *
         * The travel is the track length minus the handle length.
         * The value minimum is the model minimum.
         * The value maximum is the model maximum minus the extent.
         * The handle starts at the track origin when the travel is not positive.
         * The handle also starts at the track origin when the value maximum is not above the minimum.
         *
         * A positive travel with room in the model computes a fraction from the current value.
         * A fraction below zero becomes zero.
         * A fraction above one becomes one.
         * The handle origin is the track origin plus that fraction times the travel, rounded to the nearest pixel.
         *
         * An upright handle is placed at x zero.
         * Its width is the bar width, or one pixel when the bar width is smaller.
         * Its height is the handle length.
         * A bottom handle is placed at y zero.
         * Its height is the bar height, or one pixel when the bar height is smaller.
         * Its width is the handle length.
         *
         * @param vertical True for the upright bar.
         */
        private void seatThumb(boolean vertical) {
            if (scrollbar == null) {
                return;
            }
            Rectangle track = getTrackBounds();
            if (track == null) {
                return;
            }
            int trackLen = vertical ? track.height : track.width;
            int span = thumbSpan(trackLen);
            int travel = trackLen - span;
            BoundedRangeModel model = scrollbar.getModel();
            int min = model.getMinimum();
            int max = model.getMaximum() - model.getExtent();
            int origin = vertical ? track.y : track.x;
            int pos = origin;
            if (travel > 0 && max > min) {
                float t = (model.getValue() - min) / (float) (max - min);
                if (t < 0f) {
                    t = 0f;
                }
                if (t > 1f) {
                    t = 1f;
                }
                pos = origin + Math.round(t * travel);
            }
            if (vertical) {
                setThumbBounds(0, pos, Math.max(scrollbar.getWidth(), 1), span);
            } else {
                setThumbBounds(pos, 0, span, Math.max(scrollbar.getHeight(), 1));
            }
        }

        /**
         * The {@code createDecreaseButton} method returns a hidden button.
         * The orientation is ignored.
         * The button comes from {@link #quietButton}.
         *
         * @param orientation The stock arrow direction. This method ignores it.
         * @return A button with empty bounds that is not visible.
         */
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return quietButton();
        }

        /**
         * The {@code createIncreaseButton} method returns a hidden button.
         * The orientation is ignored.
         * The button comes from {@link #quietButton}.
         *
         * @param orientation The stock arrow direction. This method ignores it.
         * @return A button with empty bounds that is not visible.
         */
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return quietButton();
        }

        /**
         * The {@code createTrackListener} method returns the pointer listener for this bar.
         * Press, drag, release, and move are forwarded to this painter.
         * Entering the bar marks it hot.
         * Leaving the bar clears that mark only while the button is up.
         *
         * @return The track listener.
         */
        @Override
        protected TrackListener createTrackListener() {
            /**
             * The listener forwards bar pointer events to this painter.
             * A press calls {@link #onPress}.
             * A drag calls {@link #onDrag}.
             * A release calls {@link #onRelease}.
             * A move calls {@link #onMove}.
             *
             * Entering the bar sets the hot flag and calls {@link #wake}.
             * Leaving the bar while the button is up clears the hot flag.
             * That leave also clears the thumb rollover and calls {@link #wake}.
             * Leaving the bar while the button is down does nothing.
             */
            return new TrackListener() {
                // @aa-sdd GK-0750 aetheris-plan-f-84.001.md FINESSE:f-84
                @Override
                public void mousePressed(MouseEvent e) {
                    onPress(e);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    onDrag(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    onRelease(e);
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    onMove(e);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    hot = true;
                    wake();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!pressed) {
                        hot = false;
                        setThumbRollover(false);
                        wake();
                    }
                }
            };
        }

        /**
         * The {@code paint} method draws the track and the handle.
         * A null bar returns immediately.
         * A null graphics context returns immediately.
         * The fade state comes from {@link AppleScrollBar#motionOf} on the bar.
         * A missing fade treats the pointer as outside and the eased amount as zero.
         *
         * The bar is live while the pointer is on it or the button is down.
         * The thickness moves from {@link #THIN_PX} toward {@link #BAR_PX} by the eased live amount.
         * A live handle is fully opaque.
         * A handle that is not live uses the eased inside amount times six tenths.
         * A live track uses seven tenths opacity.
         * A track with the pointer on the pane, and the bar not live, uses four tenths opacity.
         * Any other track uses zero opacity.
         * Zero handle opacity and zero track opacity return without drawing.
         *
         * The handle gray is 215 while the button is up and the pointer is off the handle.
         * A pressed button uses gray 255.
         * A thumb rollover, while the button is up, uses gray 245.
         * The upright test uses the bar orientation.
         * The left-to-right test uses the bar's component orientation.
         * Antialiasing is turned on for the copy of the graphics context.
         *
         * An upright left-to-right bar draws from the right edge inward.
         * An upright right-to-left bar draws from the left edge.
         * The track uses {@link #PLATE}.
         * The handle uses the chosen gray.
         * A bottom bar draws from the bottom edge upward.
         * That bottom bar does not consult the component orientation.
         * The graphics copy is disposed.
         *
         * @param g The graphics context.
         * @param c The bar component. This method paints from the scroll bar field.
         */
        @Override
        public void paint(Graphics g, JComponent c) {
            if (scrollbar == null || g == null) {
                return;
            }
            AppleMotion motion = motionOf(scrollbar);
            boolean inside = motion != null && motion.insideTarget;
            float insideEased = motion == null ? 0f : motion.easedInside();
            boolean live = hot || pressed;
            float thick = THIN_PX + (BAR_PX - THIN_PX) * eased(linear, live);
            float handleOpacity = live ? 1f : insideEased * ACTIVE_HANDLE;
            float trackOpacity = 0f;
            if (live) {
                trackOpacity = INTERACT_TRACK;
            } else if (inside) {
                trackOpacity = ACTIVE_TRACK;
            }
            if (handleOpacity <= 0f && trackOpacity <= 0f) {
                return;
            }
            int gray = INACTIVE;
            if (pressed) {
                gray = ACTIVE;
            } else if (isThumbRollover()) {
                gray = HOVERED;
            }
            boolean vertical = scrollbar.getOrientation() == JScrollBar.VERTICAL;
            boolean leftToRight = scrollbar.getComponentOrientation().isLeftToRight();
            Rectangle track = getTrackBounds();
            Rectangle thumb = getThumbBounds();
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (vertical) {
                float x = leftToRight ? scrollbar.getWidth() - thick : 0f;
                fill(g2, x, track.y, thick, track.height, PLATE, trackOpacity);
                fill(g2, x, thumb.y, thick, thumb.height, grayRgb(gray), handleOpacity);
            } else {
                float y = scrollbar.getHeight() - thick;
                fill(g2, track.x, y, track.width, thick, PLATE, trackOpacity);
                fill(g2, thumb.x, y, thumb.width, thick, grayRgb(gray), handleOpacity);
            }
            g2.dispose();
        }

        /**
         * The {@code tick} method advances this bar's thickness fade.
         * The target is fully wide while the bar is hot or the button is down.
         * The target is fully thin otherwise.
         * {@link AppleScrollBar#step} applies the elapsed time.
         *
         * @param dt The elapsed time, in seconds.
         */
        void tick(float dt) {
            linear = step(linear, hot || pressed, dt);
        }

        /**
         * The {@code moving} method reports whether the thickness fade is still running.
         * The end value is one while the bar is hot or the button is down.
         * The end value is zero otherwise.
         * The method returns true when the linear value is not that end.
         *
         * @return True while the thickness is still changing.
         */
        boolean moving() {
            float end = (hot || pressed) ? 1f : 0f;
            return linear != end;
        }

        /**
         * The {@code onPress} method starts a drag or jumps the handle.
         * A null bar returns immediately.
         * A disabled bar returns immediately.
         * A null event returns immediately.
         * A button other than the left button returns immediately.
         *
         * The pressed flag and the hot flag are set.
         * A press inside a handle with positive length stores the grab offset from the handle start.
         * That press also marks the thumb rollover.
         * Any other press centers the handle on the pointer and clamps it to the track.
         * That jump clears the thumb rollover and calls {@link #moveThumb}.
         * The grab offset becomes the distance from the pointer to the jumped handle.
         *
         * The model is marked as adjusting.
         * {@link #wake} starts the fade.
         *
         * @param e The mouse press.
         */
        private void onPress(MouseEvent e) {
            if (scrollbar == null || !scrollbar.isEnabled() || e == null) {
                return;
            }
            if (e.getButton() != MouseEvent.BUTTON1) {
                return;
            }
            pressed = true;
            hot = true;
            int pos = verticalPoint(e);
            Rectangle thumb = getThumbBounds();
            int thumbLen = span(thumb);
            int thumbStart = start(thumb);
            if (thumbLen > 0 && thumb.contains(e.getPoint())) {
                grab = pos - thumbStart;
                setThumbRollover(true);
            } else {
                Rectangle track = getTrackBounds();
                int trackStart = start(track);
                int trackLen = span(track);
                int newTop = (int) (pos - thumbLen * 0.5f);
                int maxTop = trackStart + trackLen - thumbLen;
                if (newTop < trackStart) {
                    newTop = trackStart;
                }
                if (newTop > maxTop) {
                    newTop = maxTop;
                }
                grab = pos - newTop;
                setThumbRollover(false);
                moveThumb(newTop);
            }
            scrollbar.setValueIsAdjusting(true);
            wake();
        }

        /**
         * The {@code onDrag} method moves the handle by the stored grab offset.
         * A null bar returns immediately.
         * A null event returns immediately.
         * A bar that is not adjusting returns immediately.
         * A button other than the left button returns immediately.
         *
         * {@link #moveThumb} receives the pointer minus the grab offset.
         * The thumb rollover follows whether the pointer is still inside the handle.
         * {@link #wake} keeps the fade running.
         *
         * @param e The mouse drag.
         */
        private void onDrag(MouseEvent e) {
            if (scrollbar == null || e == null || !scrollbar.getValueIsAdjusting()) {
                return;
            }
            if (!SwingUtilities.isLeftMouseButton(e)) {
                return;
            }
            int pos = verticalPoint(e);
            moveThumb(pos - grab);
            setThumbRollover(getThumbBounds().contains(e.getPoint()));
            wake();
        }

        /**
         * The {@code onRelease} method ends the drag.
         * A live bar clears the adjusting flag.
         * The pressed flag is cleared.
         * A release outside the bar clears the hot flag and the thumb rollover.
         * A null event leaves the hot flag as it was.
         * A release inside the bar also leaves the hot flag as it was.
         * {@link #wake} runs in every case that reaches it.
         *
         * @param e The mouse release, or null.
         */
        private void onRelease(MouseEvent e) {
            if (scrollbar != null) {
                scrollbar.setValueIsAdjusting(false);
            }
            pressed = false;
            if (e != null && scrollbar != null && !scrollbar.contains(e.getPoint())) {
                hot = false;
                setThumbRollover(false);
            }
            wake();
        }

        /**
         * The {@code onMove} method tracks the pointer on the bar.
         * A null bar returns immediately.
         * A null event returns immediately.
         * The hot flag is set.
         * The thumb rollover follows whether the pointer is inside the handle.
         * {@link #wake} starts the fade.
         *
         * @param e The mouse move.
         */
        private void onMove(MouseEvent e) {
            if (scrollbar == null || e == null) {
                return;
            }
            hot = true;
            setThumbRollover(getThumbBounds().contains(e.getPoint()));
            wake();
        }

        /**
         * The {@code moveThumb} method moves the handle and writes the model value.
         * The position is clamped to the track.
         * An upright handle keeps its x, width, and height.
         * A bottom handle keeps its y, width, and height.
         *
         * No travel sets the value to the model minimum and returns.
         * A handle on the maximum end sets the value to the model maximum minus the extent and returns.
         * Any other position maps the handle fraction across the value range.
         * That value adds one half before the cast, so the fraction rounds to the nearest integer.
         * The stored value is that integer plus the model minimum.
         *
         * @param thumbPos The requested handle start, in pixels.
         */
        private void moveThumb(int thumbPos) {
            BoundedRangeModel model = scrollbar.getModel();
            Rectangle track = getTrackBounds();
            Rectangle thumb = getThumbBounds();
            boolean vertical = scrollbar.getOrientation() == JScrollBar.VERTICAL;
            int thumbMin = vertical ? track.y : track.x;
            int thumbLen = vertical ? thumb.height : thumb.width;
            int trackLen = vertical ? track.height : track.width;
            int thumbMax = thumbMin + trackLen - thumbLen;
            if (thumbPos < thumbMin) {
                thumbPos = thumbMin;
            }
            if (thumbPos > thumbMax) {
                thumbPos = thumbMax;
            }
            if (vertical) {
                setThumbBounds(thumb.x, thumbPos, thumb.width, thumb.height);
            } else {
                setThumbBounds(thumbPos, thumb.y, thumb.width, thumb.height);
            }
            if (thumbMax <= thumbMin) {
                scrollbar.setValue(model.getMinimum());
                return;
            }
            if (thumbPos == thumbMax) {
                scrollbar.setValue(model.getMaximum() - model.getExtent());
                return;
            }
            float valueMax = model.getMaximum() - model.getExtent();
            float valueRange = valueMax - model.getMinimum();
            float thumbRange = thumbMax - thumbMin;
            int value = (int) (0.5f + ((thumbPos - thumbMin) / thumbRange) * valueRange);
            scrollbar.setValue(value + model.getMinimum());
        }

        /**
         * The {@code verticalPoint} method reads the pointer along the bar.
         * An upright bar returns the y coordinate.
         * A bottom bar returns the x coordinate.
         *
         * @param e The mouse event. The method reads the point.
         * @return The coordinate along the track.
         */
        private int verticalPoint(MouseEvent e) {
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                return e.getY();
            }
            return e.getX();
        }

        /**
         * The {@code span} method reads the length of a rectangle along the bar.
         * An upright bar returns the height.
         * A bottom bar returns the width.
         *
         * @param rect The track or the handle.
         * @return The length along the track.
         */
        private int span(Rectangle rect) {
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                return rect.height;
            }
            return rect.width;
        }

        /**
         * The {@code start} method reads the origin of a rectangle along the bar.
         * An upright bar returns y.
         * A bottom bar returns x.
         *
         * @param rect The track or the handle.
         * @return The origin along the track.
         */
        private int start(Rectangle rect) {
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                return rect.y;
            }
            return rect.x;
        }

        /**
         * The {@code wake} method asks the fade to paint this bar.
         * A fade found by {@link AppleScrollBar#motionOf} is started.
         * A missing fade repaints the bar when the bar is not null.
         */
        private void wake() {
            AppleMotion motion = motionOf(scrollbar);
            if (motion != null) {
                motion.wake();
            } else if (scrollbar != null) {
                scrollbar.repaint();
            }
        }

        /**
         * The {@code quietButton} method builds a hidden arrow button.
         * The preferred, minimum, and maximum sizes are zero.
         * The button does not take focus.
         * The button is not opaque.
         * The border is removed.
         * The button is not visible.
         *
         * @return The hidden button.
         */
        private static JButton quietButton() {
            JButton button = new JButton();
            Dimension none = new Dimension(0, 0);
            button.setPreferredSize(none);
            button.setMinimumSize(none);
            button.setMaximumSize(none);
            button.setFocusable(false);
            button.setOpaque(false);
            button.setBorder(null);
            button.setVisible(false);
            return button;
        }
    }

    // @aa-sdd GK-0751 aetheris-plan-f-84.001.md FINESSE:f-84
    // @aa-sdd GK-0752 aetheris-plan-f-87.001.md FINESSE:f-87
    /**
     * The {@code AppleMotion} fades the bars while the pointer is over one component.
     * A scroll pane shares one fade across both bars.
     * A parent-laid bar stores the single bar on the fade.
     * The timer fires every sixteen milliseconds.
     */
    private static final class AppleMotion {

        private final JComponent host;

        private final JScrollBar stripBar;

        private final Timer timer;

        private boolean insideTarget;

        private float insideLinear;

        private long last;

        /**
         * The constructor stores the component and the optional single bar.
         * A null bar is stored as null.
         * The timer delay is sixteen milliseconds.
         * The timer repeats.
         * The timer coalesces queued events.
         * Each tick calls {@link #tick}.
         *
         * @param host The component the pointer is tested against.
         * @param stripBar The single parent-laid bar, or null for a scroll pane.
         */
        private AppleMotion(JComponent host, JScrollBar stripBar) {
            this.host = host;
            this.stripBar = stripBar;
            /**
             * The timer listener advances the fade.
             * Each event calls {@link #tick}.
             */
            this.timer = new Timer(16, new ActionListener() {
                // @aa-sdd GK-0753 aetheris-plan-f-84.001.md FINESSE:f-84
                @Override
                public void actionPerformed(ActionEvent e) {
                    tick();
                }
            });
            timer.setRepeats(true);
            timer.setCoalesce(true);
        }

        /**
         * The {@code watch} method starts a fade for both bars of a scroll pane.
         * The stored bar is null.
         * {@link #arm} records the fade on the pane.
         *
         * @param pane The scroll pane.
         */
        private static void watch(JScrollPane pane) {
            AppleMotion motion = new AppleMotion(pane, null);
            motion.arm();
        }

        /**
         * The {@code watchStrip} method starts a fade for one parent-laid bar.
         * A component that already stores an {@code AppleMotion} returns immediately.
         * Any other component gets a new fade that remembers the bar.
         * {@link #arm} records that fade.
         *
         * @param host The component the pointer is tested against.
         * @param bar The single bar.
         */
        private static void watchStrip(JComponent host, JScrollBar bar) {
            if (host.getClientProperty(MOTION) instanceof AppleMotion) {
                return;
            }
            AppleMotion motion = new AppleMotion(host, bar);
            motion.arm();
        }

        /**
         * The {@code arm} method records this fade and listens for show changes.
         * The client property {@code casserole.apple.motion} stores this object.
         * A show or hide event calls {@link #setInside} from the current pointer.
         * The component is added to the weak list.
         * {@link AppleScrollBar#ensureEar} installs the shared pointer listener.
         */
        private void arm() {
            final JComponent host = this.host;
            final AppleMotion motion = this;
            host.putClientProperty(MOTION, motion);
            /**
             * The listener updates the inside flag when the component is shown or hidden.
             * A null event returns immediately.
             * An event that does not include {@code SHOWING_CHANGED} returns immediately.
             * A component whose motion property is no longer this fade returns immediately.
             * Any other event calls {@link #setInside} with {@link AppleScrollBar#underPointer}.
             */
            host.addHierarchyListener(new HierarchyListener() {
                // @aa-sdd GK-0754 aetheris-plan-f-84.001.md FINESSE:f-84
                // @aa-sdd GK-0755 aetheris-plan-f-85.001.md FINESSE:f-85
                // @aa-sdd GK-0756 aetheris-plan-f-87.001.md FINESSE:f-87
                @Override
                public void hierarchyChanged(HierarchyEvent event) {
                    if (event == null) {
                        return;
                    }
                    if ((event.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == 0) {
                        return;
                    }
                    if (host.getClientProperty(MOTION) != motion) {
                        return;
                    }
                    motion.setInside(underPointer(host));
                }
            });
            PANES.add(new WeakReference<JComponent>(host));
            ensureEar();
        }

        /**
         * The {@code forget} method stops the fade for a scroll pane.
         * A stored {@code AppleMotion} has its timer stopped.
         * The motion property is cleared.
         * The weak list drops a cleared reference.
         * The weak list also drops this pane.
         * An empty list removes the shared pointer listener and clears it.
         *
         * @param pane The scroll pane that is losing the overlay.
         */
        private static void forget(JScrollPane pane) {
            Object motion = pane.getClientProperty(MOTION);
            if (motion instanceof AppleMotion) {
                ((AppleMotion) motion).timer.stop();
            }
            pane.putClientProperty(MOTION, null);
            int i = 0;
            while (i < PANES.size()) {
                JComponent one = PANES.get(i).get();
                if (one == null || one == pane) {
                    PANES.remove(i);
                } else {
                    i = i + 1;
                }
            }
            if (PANES.isEmpty() && ear != null) {
                Toolkit.getDefaultToolkit().removeAWTEventListener(ear);
                ear = null;
            }
        }

        /**
         * The {@code setInside} method stores whether the pointer is over the component.
         * An unchanged value returns immediately.
         * A changed value is stored.
         * {@link #wake} starts the timer.
         *
         * @param over True when the pointer is over the component.
         */
        private void setInside(boolean over) {
            if (over == insideTarget) {
                return;
            }
            insideTarget = over;
            wake();
        }

        /**
         * The {@code wake} method starts the timer when it is stopped.
         * A running timer is left alone.
         * A start records the current time in nanoseconds before the timer starts.
         */
        private void wake() {
            if (!timer.isRunning()) {
                last = System.nanoTime();
                timer.start();
            }
        }

        /**
         * The {@code tick} method advances one frame of the fade.
         * The elapsed time is the nanosecond gap divided by one billion.
         * A negative gap becomes zero.
         * A gap above five hundredths of a second becomes five hundredths.
         * {@link AppleScrollBar#step} moves the inside amount toward the inside flag.
         *
         * A single stored bar is advanced with {@link #tickOne}.
         * A scroll pane advances the vertical bar and the horizontal bar.
         * A component that is neither a single bar nor a scroll pane advances only the inside amount.
         * The timer stops when the inside amount and every advanced bar have reached their ends.
         */
        private void tick() {
            long now = System.nanoTime();
            float dt = (now - last) / 1000000000f;
            last = now;
            if (dt < 0f) {
                dt = 0f;
            }
            if (dt > DT_CAP) {
                dt = DT_CAP;
            }
            insideLinear = step(insideLinear, insideTarget, dt);
            boolean live = moving(insideLinear, insideTarget);
            if (stripBar != null) {
                if (tickOne(stripBar, dt)) {
                    live = true;
                }
            } else if (host instanceof JScrollPane) {
                JScrollPane pane = (JScrollPane) host;
                if (tickOne(pane.getVerticalScrollBar(), dt)) {
                    live = true;
                }
                if (tickOne(pane.getHorizontalScrollBar(), dt)) {
                    live = true;
                }
            }
            if (!live) {
                timer.stop();
            }
        }

        /**
         * The {@code tickOne} method advances one bar and repaints it.
         * An {@link AppleBarUi} receives {@link AppleBarUi#tick}.
         * A missing painter skips that call.
         * A non-null bar is repainted.
         * The return is true when the painter is still moving.
         *
         * @param bar The bar to advance. A null bar returns false.
         * @param dt The elapsed time, in seconds.
         * @return True while that bar's thickness is still changing.
         */
        private boolean tickOne(JScrollBar bar, float dt) {
            AppleBarUi ui = barUi(bar);
            if (ui != null) {
                ui.tick(dt);
            }
            if (bar != null) {
                bar.repaint();
            }
            return ui != null && ui.moving();
        }

        /**
         * The {@code easedInside} method returns the cubic-out amount for the pointer-over fade.
         * The value comes from {@link AppleScrollBar#eased}.
         *
         * @return The eased amount from zero to one.
         */
        private float easedInside() {
            return eased(insideLinear, insideTarget);
        }
    }

    /**
     * The {@code ensureEar} method installs one shared pointer listener.
     * A listener that is already installed returns immediately.
     * The new listener receives mouse events and mouse-motion events.
     */
    private static void ensureEar() {
        if (ear != null) {
            return;
        }
        /**
         * The listener watches mouse events for every armed component.
         * A mouse event is passed to {@link #onMouse}.
         * Any other event is ignored.
         */
        ear = new AWTEventListener() {
            // @aa-sdd GK-0757 aetheris-plan-f-87.001.md FINESSE:f-87
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent) {
                    onMouse((MouseEvent) event);
                }
            }
        };
        Toolkit.getDefaultToolkit().addAWTEventListener(
                ear, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    /**
     * The {@code onMouse} method updates every armed component.
     * A source that is not a component returns immediately.
     * A cleared weak reference is removed from the list.
     * A live component tells its fade whether the pointer is over it.
     * A component with no fade is skipped.
     *
     * An empty list after that pass removes the shared listener.
     * The listener field is then cleared.
     *
     * @param e The mouse event.
     */
    private static void onMouse(MouseEvent e) {
        Object src = e.getSource();
        if (!(src instanceof Component)) {
            return;
        }
        Component source = (Component) src;
        int i = 0;
        while (i < PANES.size()) {
            JComponent host = PANES.get(i).get();
            if (host == null) {
                PANES.remove(i);
                continue;
            }
            AppleMotion motion = motionOf(host);
            if (motion != null) {
                motion.setInside(pointerOver(host, source, e.getPoint()));
            }
            i = i + 1;
        }
        if (PANES.isEmpty() && ear != null) {
            Toolkit.getDefaultToolkit().removeAWTEventListener(ear);
            ear = null;
        }
    }

    /**
     * The {@code pointerOver} method tests one event point against a component.
     * A null component, source, or point returns false.
     * A component that is not showing returns false.
     * A source that is not showing returns false.
     * The point is converted into the component.
     * The method returns whether the component contains that point.
     *
     * @param host The component to test.
     * @param source The component that produced the point.
     * @param point The point in the source.
     * @return True when the point lies inside the component.
     */
    private static boolean pointerOver(JComponent host, Component source, Point point) {
        if (host == null || source == null || point == null || !host.isShowing() || !source.isShowing()) {
            return false;
        }
        Point local = SwingUtilities.convertPoint(source, point, host);
        return host.contains(local);
    }

    /**
     * The {@code underPointer} method tests the current screen pointer.
     * A null component returns false.
     * A component that is not showing returns false.
     * A missing pointer location returns false.
     * The screen point is converted into the component.
     * The method returns whether the component contains that point.
     *
     * @param host The component to test.
     * @return True when the screen pointer lies inside the component.
     */
    private static boolean underPointer(JComponent host) {
        if (host == null || !host.isShowing()) {
            return false;
        }
        PointerInfo info = MouseInfo.getPointerInfo();
        if (info == null) {
            return false;
        }
        Point screen = info.getLocation();
        if (screen == null) {
            return false;
        }
        Point local = new Point(screen);
        SwingUtilities.convertPointFromScreen(local, host);
        return host.contains(local);
    }

    /**
     * The {@code motionOf} method finds the fade for a component.
     * The search starts at the component and climbs through parents.
     * The first Swing component whose {@code casserole.apple.motion} property is an {@code AppleMotion} is returned.
     * A search that finds none returns null.
     *
     * @param component The starting component. A null value returns null.
     * @return The fade, or null.
     */
    private static AppleMotion motionOf(Component component) {
        Component cursor = component;
        while (cursor != null) {
            if (cursor instanceof JComponent) {
                Object motion = ((JComponent) cursor).getClientProperty(MOTION);
                if (motion instanceof AppleMotion) {
                    return (AppleMotion) motion;
                }
            }
            cursor = cursor.getParent();
        }
        return null;
    }

    /**
     * The {@code barUi} method reads the overlay painter from a bar.
     * A null bar returns null.
     * A bar whose UI is not an {@link AppleBarUi} returns null.
     *
     * @param bar The bar to read.
     * @return The painter, or null.
     */
    private static AppleBarUi barUi(JScrollBar bar) {
        if (bar != null && bar.getUI() instanceof AppleBarUi) {
            return (AppleBarUi) bar.getUI();
        }
        return null;
    }

    /**
     * The {@code step} method moves a linear fade toward zero or one.
     * The step size is the elapsed time divided by {@link #ANIM_SEC}.
     * A fade time that is not positive uses a step of zero.
     * A negative step becomes zero.
     * A true target adds the step and stops at one.
     * A false target subtracts the step and stops at zero.
     *
     * @param linear The current amount, from zero to one.
     * @param target True to move toward one. False to move toward zero.
     * @param dt The elapsed time, in seconds.
     * @return The next linear amount.
     */
    private static float step(float linear, boolean target, float dt) {
        float delta = 0f;
        if (ANIM_SEC > 0f) {
            delta = dt / ANIM_SEC;
        }
        if (delta < 0f) {
            delta = 0f;
        }
        if (target) {
            linear = linear + delta;
            if (linear > 1f) {
                linear = 1f;
            }
        } else {
            linear = linear - delta;
            if (linear < 0f) {
                linear = 0f;
            }
        }
        return linear;
    }

    /**
     * The {@code moving} method reports whether a linear fade has reached its end.
     * The end is one when the target is true.
     * The end is zero when the target is false.
     * The method returns true when the linear value is not that end.
     *
     * @param linear The current amount.
     * @param target True when the fade is heading toward one.
     * @return True while the amount is still changing.
     */
    private static boolean moving(float linear, boolean target) {
        float end = target ? 1f : 0f;
        return linear != end;
    }

    /**
     * The {@code eased} method applies the cubic-out curve.
     * A fade toward one returns {@link #cubicOut} of the linear amount.
     * A fade toward zero returns one minus {@link #cubicOut} of one minus the linear amount.
     *
     * @param linear The current amount, from zero to one.
     * @param target True when the fade is heading toward one.
     * @return The eased amount.
     */
    private static float eased(float linear, boolean target) {
        if (target) {
            return cubicOut(linear);
        }
        return 1f - cubicOut(1f - linear);
    }

    /**
     * The {@code cubicOut} method computes one cubic-out value.
     * The result is the cube of {@code t} minus one, plus one.
     *
     * @param t The linear amount.
     * @return The eased amount.
     */
    private static float cubicOut(float t) {
        float f = t - 1f;
        return f * f * f + 1f;
    }

    /**
     * The {@code grayRgb} method repeats one gray level in red, green, and blue.
     * The level is shifted into each byte.
     * The method does not clamp the level.
     *
     * @param gray The gray level.
     * @return The packed color.
     */
    private static int grayRgb(int gray) {
        return (gray << 16) | (gray << 8) | gray;
    }

    /**
     * The {@code fill} method paints one rounded rectangle.
     * A width at or below zero returns immediately.
     * A height at or below zero returns immediately.
     * An opacity at or below zero returns immediately.
     * An opacity above one becomes one.
     *
     * The alpha is the opacity times 255, plus one half, then cast to an integer.
     * An alpha below one returns immediately.
     * An alpha above 255 becomes 255.
     * Red, green, and blue are taken from the packed color.
     *
     * The arc starts at twice {@link #RADIUS_PX}.
     * An arc wider than the rectangle becomes the rectangle width.
     * An arc taller than the rectangle becomes the rectangle height.
     * The rectangle is filled with that color and alpha.
     *
     * @param g2 The graphics copy.
     * @param x The left edge.
     * @param y The top edge.
     * @param w The width.
     * @param h The height.
     * @param rgb The packed red, green, and blue.
     * @param opacity The opacity from zero to one.
     */
    private static void fill(Graphics2D g2, float x, float y, float w, float h, int rgb, float opacity) {
        if (w <= 0f || h <= 0f || opacity <= 0f) {
            return;
        }
        if (opacity > 1f) {
            opacity = 1f;
        }
        int alpha = (int) (255f * opacity + 0.5f);
        if (alpha < 1) {
            return;
        }
        if (alpha > 255) {
            alpha = 255;
        }
        int red = (rgb >> 16) & 0xff;
        int green = (rgb >> 8) & 0xff;
        int blue = rgb & 0xff;
        float arc = RADIUS_PX + RADIUS_PX;
        if (arc > w) {
            arc = w;
        }
        if (arc > h) {
            arc = h;
        }
        g2.setColor(new Color(red, green, blue, alpha));
        g2.fill(new RoundRectangle2D.Float(x, y, w, h, arc, arc));
    }
}
