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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The picture paints one graph back to front.
 * Hints do not move a coordinate.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1474 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
// @aa-sdd L40 aetheris-plan-f-309.001.md FINESSE:f-309
// @aa-sdd L40 aetheris-plan-f-313.001.md FINESSE:f-313
// @aa-sdd L40 aetheris-plan-f-314.001.md FINESSE:f-314
// @aa-sdd L40 aetheris-plan-f-317.001.md FINESSE:f-317
// @aa-sdd L40 aetheris-plan-f-318.001.md FINESSE:f-318
// @aa-sdd L40 aetheris-plan-f-319.001.md FINESSE:f-319
// @aa-sdd L40 aetheris-plan-f-320.001.md FINESSE:f-320
// @aa-sdd L40 aetheris-plan-f-321.001.md FINESSE:f-321
// @aa-sdd L40 aetheris-plan-f-322.001.md FINESSE:f-322
public final class GraphPicture extends JComponent {

    private static final Color GROUND = new Color(0x0C0C0C);
    private static final Color FIELD = new Color(0x121212);
    private static final Color FRAME = new Color(0xFF6600);
    private static final Color CHIP_FILL = new Color(0xE4DED6);
    private static final Color CHIP_EDGE = new Color(0xC8C2B8);
    private static final Color JOIN = new Color(0xFFC14D);
    private static final Color LETTER = new Color(0xE0F4FF);
    private static final String[] RING = {
        "#6B93FF", "#E879F9", "#00C853", "#D9F26A", "#A07BFF", "#67E8F9"
    };
    private static final String[] WELL = {
        "#73AEFF", "#F0A202", "#00FF66", "#2E86FF", "#FFFF55", "#C0DEFF"
    };
    private static final int LEGIBLE = 32;
    private static final String PAGE_TEXT = "#F0A202";
    private static final String[] RELATIONS = {
        "supports", "refines", "contains", "pulls against"
    };
    private static final int ORIGIN_X = 220;
    private static final int ORIGIN_Y = 160;

    private final GraphPane pane;
    private boolean antialias;
    private boolean textAntialias;
    private String dragId;
    private int pressScreenX;
    private int pressScreenY;
    private int lastPhysicalX;
    private int lastPhysicalY;
    private int coastX;
    private int coastY;
    private Timer coast;
    private Timer placeClick;
    private boolean focusPress;
    private int pendingX;
    private int pendingY;
    private boolean frameHeld;
    private int heldZoom = 100;
    private int heldPanX;
    private int heldPanY;
    private int heldShiftX;
    private int heldShiftY;
    private int heldGrownW;
    private int heldGrownH;
    private int heldViewX;
    private int heldViewY;
    private boolean heldView;
    private String closeFor;
    private final List<String> closeIds = new ArrayList<String>();
    private final List<Integer> closeX = new ArrayList<Integer>();
    private final List<Integer> closeY = new ArrayList<Integer>();
    private final List<Boolean> closeHeld = new ArrayList<Boolean>();
    private boolean dragging;
    private volatile int panX;
    private volatile int panY;
    private volatile int shiftX;
    private volatile int shiftY;
    private volatile int grownWide;
    private volatile int grownHigh;
    private volatile int canvasWide = 640;
    private volatile int canvasHigh = 480;
    private volatile int zoomPercent = 100;
    private volatile int measureWalks;
    private volatile Map<String, Integer> postedReach;
    private Map<String, String> paintHex;
    private Map<String, String> paintRelation;
    private int typePoints = 20;
    private JMenuItem excludeItem;
    private JMenuItem colorItem;

    public GraphPicture(GraphPane pane) {
        this.pane = pane;
        setOpaque(true);
        setToolTipText("Drag a node. The drop keeps that seat.");
        coast = new Timer(32, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                coastStep();
            }
        });
        coast.setRepeats(true);
        int gap = 500;
        Object clickGap = Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
        if (clickGap instanceof Integer) {
            gap = ((Integer) clickGap).intValue() + 80;
        }
        placeClick = new Timer(gap, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                pane.click(pendingX, pendingY);
            }
        });
        placeClick.setRepeats(false);
        JPopupMenu menu = new JPopupMenu();
        excludeItem = new JMenuItem("Exclude");
        JMenuItem exclude = excludeItem;
        exclude.setToolTipText("Exclude. Seat E on the cards this intent names.");
        exclude.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String selected = pane.selected();
                if (selected == null || !pane.model().hasIntent(selected)) {
                    return;
                }
                if (pane.model().excluded(selected)) {
                    pane.model().include(selected);
                } else {
                    pane.model().exclude(selected);
                }
                GraphDesk.written(pane.model());
                repaint();
            }
        });
        colorItem = new JMenuItem("Color");
        JMenuItem color = colorItem;
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String selected = pane.selected();
                String ink = pane.stored("intent");
                if (selected == null || ink == null) {
                    return;
                }
                pane.model().setColor(selected, ink);
                repaint();
            }
        });
        menu.add(exclude);
        menu.add(color);
        MouseAdapter pointer = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    String hit = intentAt(laid(event.getX()), laid(event.getY()));
                    if (hit == null) {
                        return;
                    }
                    pane.select(hit);
                    if (pane.model().excluded(hit)) {
                        exclude.setText("Include");
                        exclude.setToolTipText("Include. Seat I on the cards this include releases.");
                    } else {
                        exclude.setText("Exclude");
                        exclude.setToolTipText("Exclude. Seat E on the cards this intent names.");
                    }
                    menu.show(GraphPicture.this, event.getX(), event.getY());
                    return;
                }
                if (placeClick != null) {
                    placeClick.stop();
                }
                if (event.getClickCount() >= 2) {
                    String hit = intentAt(laid(event.getX()), laid(event.getY()));
                    if (hit != null) {
                        focusPress = true;
                        dragging = false;
                        dragId = null;
                        stopCoast();
                        pane.focusIntent(hit);
                        return;
                    }
                }
                Point at = pointer(event);
                pressScreenX = at.x;
                pressScreenY = at.y;
                lastPhysicalX = at.x;
                lastPhysicalY = at.y;
                dragId = nodeAt(laid(event.getX()), laid(event.getY()));
                dragging = false;
                stopCoast();
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    return;
                }
                Point at = pointer(event);
                int physicalX = at.x;
                int physicalY = at.y;
                if (Math.abs(physicalX - pressScreenX) + Math.abs(physicalY - pressScreenY) > 3) {
                    dragging = true;
                }
                if (!dragging) {
                    return;
                }
                int ddx = physicalX - lastPhysicalX;
                int ddy = physicalY - lastPhysicalY;
                lastPhysicalX = physicalX;
                lastPhysicalY = physicalY;
                if (dragId != null) {
                    follow(dragId, ddx, ddy);
                    armCoast(physicalX, physicalY);
                } else {
                    stopCoast();
                    panView(ddx, ddy);
                }
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    return;
                }
                stopCoast();
                if (focusPress) {
                    focusPress = false;
                    dragId = null;
                    dragging = false;
                    return;
                }
                if (!dragging) {
                    int x = laid(event.getX());
                    int y = laid(event.getY());
                    if (pane.page() == 10 && intentAt(x, y) != null) {
                        pendingX = x;
                        pendingY = y;
                        placeClick.restart();
                    } else if (pane.page() == 10) {
                        pane.click(x, y);
                    } else {
                        aim(x, y);
                    }
                } else if (dragId != null) {
                    if (!pane.focused()) {
                        pane.keepSeats();
                    }
                    repaint();
                }
                dragId = null;
                dragging = false;
            }
        };
        addMouseListener(pointer);
        addMouseMotionListener(pointer);
    }

    /**
     * The method moves one dragged node with the pointer.
     * The move stays on the event thread.
     * A second thread would put the pointer behind a hop.
     * The picture thread places a generation. It does not drag.
     *
     * @param id The id is the dragged node.
     * @param ddx The ddx is the pointer move in viewport pixels.
     * @param ddy The ddy is the pointer move in viewport pixels.
     */
    private void follow(String id, int ddx, int ddy) {
        int lx = uncomp(ddx);
        int ly = uncomp(ddy);
        if (lx != 0 || ly != 0) {
            if (pane.focused()) {
                shiftClose(id, lx, ly);
            } else {
                pane.nudge(id, lx, ly);
            }
        }
        reveal(id);
    }

    /**
     * The method keeps the dragged node inside the viewport.
     * A move that crosses the edge scrolls that axis.
     * A margin that grows keeps the same screen seat.
     *
     * @param id The id is the dragged node.
     */
    private void reveal(String id) {
        int cxBefore = comp(spotX(id));
        int cyBefore = comp(spotY(id));
        Dimension size = getPreferredSize();
        int cx = comp(spotX(id));
        int cy = comp(spotY(id));
        JViewport port = port();
        if (port == null) {
            applySize(size);
            repaint();
            return;
        }
        int vx = port.getViewPosition().x + (cx - cxBefore);
        int vy = port.getViewPosition().y + (cy - cyBefore);
        int vpw = port.getWidth();
        int vph = port.getHeight();
        if (vpw < 1 || vph < 1) {
            applySize(size);
            repaint();
            return;
        }
        int edge = 4;
        int screenX = cx - vx;
        int screenY = cy - vy;
        if (vpw > edge * 2) {
            if (screenX < edge) {
                vx = cx - edge;
            } else if (screenX > vpw - edge) {
                vx = cx - (vpw - edge);
            }
        }
        if (vph > edge * 2) {
            if (screenY < edge) {
                vy = cy - edge;
            } else if (screenY > vph - edge) {
                vy = cy - (vph - edge);
            }
        }
        int wide = size.width;
        int high = size.height;
        if (vx + vpw > wide) {
            wide = vx + vpw;
        }
        if (vy + vph > high) {
            high = vy + vph;
        }
        if (vx < 0) {
            vx = 0;
        }
        if (vy < 0) {
            vy = 0;
        }
        Dimension next = new Dimension(wide, high);
        applySize(next);
        int maxX = Math.max(0, next.width - vpw);
        int maxY = Math.max(0, next.height - vph);
        if (vx > maxX) {
            vx = maxX;
        }
        if (vy > maxY) {
            vy = maxY;
        }
        if (vx != port.getViewPosition().x || vy != port.getViewPosition().y) {
            port.setViewPosition(new Point(vx, vy));
        }
        repaint();
    }

    private void panView(int ddx, int ddy) {
        JViewport port = port();
        if (port == null) {
            panX += ddx;
            panY += ddy;
            repaint();
            return;
        }
        Dimension size = getPreferredSize();
        int vpw = Math.max(port.getWidth(), 1);
        int vph = Math.max(port.getHeight(), 1);
        int maxX = Math.max(0, size.width - vpw);
        int maxY = Math.max(0, size.height - vph);
        int nx = port.getViewPosition().x - ddx;
        int ny = port.getViewPosition().y - ddy;
        if (nx < 0) {
            nx = 0;
        }
        if (ny < 0) {
            ny = 0;
        }
        if (nx > maxX) {
            nx = maxX;
        }
        if (ny > maxY) {
            ny = maxY;
        }
        port.setViewPosition(new Point(nx, ny));
        repaint();
    }

    private void armCoast(int physicalX, int physicalY) {
        JViewport port = port();
        coastX = 0;
        coastY = 0;
        if (port == null || dragId == null) {
            stopCoast();
            return;
        }
        int vpw = port.getWidth();
        int vph = port.getHeight();
        if (vpw > 0) {
            if (physicalX < 0) {
                coastX = -1;
            } else if (physicalX >= vpw) {
                coastX = 1;
            }
        }
        if (vph > 0) {
            if (physicalY < 0) {
                coastY = -1;
            } else if (physicalY >= vph) {
                coastY = 1;
            }
        }
        if (coastX == 0 && coastY == 0) {
            stopCoast();
            return;
        }
        if (!coast.isRunning()) {
            coast.start();
        }
    }

    private void coastStep() {
        if (!dragging || dragId == null || (coastX == 0 && coastY == 0)) {
            stopCoast();
            return;
        }
        follow(dragId, coastX * 16, coastY * 16);
    }

    private void stopCoast() {
        coastX = 0;
        coastY = 0;
        if (coast != null && coast.isRunning()) {
            coast.stop();
        }
    }

    /**
     * The method returns the picture to the origin.
     * A reset uses this seat.
     */
    void home() {
        stopCoast();
        if (placeClick != null) {
            placeClick.stop();
        }
        frameHeld = false;
        shiftX = 0;
        shiftY = 0;
        panX = 0;
        panY = 0;
        grownWide = 0;
        grownHigh = 0;
        JViewport port = port();
        if (port != null) {
            port.setViewPosition(new Point(0, 0));
        }
    }

    /**
     * The method remembers the zoomed-out frame.
     * A later double-click keeps this frame.
     */
    void holdFrame() {
        if (frameHeld) {
            return;
        }
        frameHeld = true;
        heldZoom = zoomPercent;
        heldPanX = panX;
        heldPanY = panY;
        heldShiftX = shiftX;
        heldShiftY = shiftY;
        heldGrownW = grownWide;
        heldGrownH = grownHigh;
        heldView = false;
        JViewport port = port();
        if (port != null) {
            Point at = port.getViewPosition();
            heldViewX = at.x;
            heldViewY = at.y;
            heldView = true;
        }
    }

    /**
     * The method seats the closer picture on the focused intent.
     * The zoom moves to 200 when the open zoom is smaller.
     * A showing viewport puts that intent at the center.
     */
    void frameFocus() {
        String id = pane.focusId();
        if (id == null) {
            return;
        }
        if (zoomPercent < 200) {
            zoomPercent = 200;
        }
        JViewport port = port();
        if (port != null && port.getWidth() > 0 && port.getHeight() > 0) {
            int zx = spotX(id) * zoomPercent / 100;
            int zy = spotY(id) * zoomPercent / 100;
            int vx = zx - port.getWidth() / 2;
            int vy = zy - port.getHeight() / 2;
            if (vx < 0) {
                panX = panX + (port.getWidth() / 2 - zx) * 100 / zoomPercent;
                zx = spotX(id) * zoomPercent / 100;
                vx = zx - port.getWidth() / 2;
            }
            if (vy < 0) {
                panY = panY + (port.getHeight() / 2 - zy) * 100 / zoomPercent;
                zy = spotY(id) * zoomPercent / 100;
                vy = zy - port.getHeight() / 2;
            }
            if (vx < 0) {
                vx = 0;
            }
            if (vy < 0) {
                vy = 0;
            }
            port.setViewPosition(new Point(vx, vy));
        }
        revalidate();
        repaint();
    }

    /**
     * The method returns the zoomed-out frame.
     */
    void restoreFrame() {
        dropCluster();
        if (!frameHeld) {
            return;
        }
        zoomPercent = heldZoom;
        panX = heldPanX;
        panY = heldPanY;
        shiftX = heldShiftX;
        shiftY = heldShiftY;
        grownWide = heldGrownW;
        grownHigh = heldGrownH;
        frameHeld = false;
        JViewport port = port();
        if (port != null && heldView) {
            port.setViewPosition(new Point(heldViewX, heldViewY));
        }
        revalidate();
        repaint();
    }

    /**
     * The method forgets a held frame.
     * A reset lays the picture out from the origin.
     */
    void dropFrame() {
        frameHeld = false;
        dropCluster();
    }

    /**
     * The method forgets the close ring.
     * The next closer picture seats the doors again.
     */
    void dropCluster() {
        closeFor = null;
        closeIds.clear();
        closeX.clear();
        closeY.clear();
        closeHeld.clear();
    }

    private void applySize(Dimension size) {
        if (size.width > grownWide) {
            grownWide = size.width;
        }
        if (size.height > grownHigh) {
            grownHigh = size.height;
        }
        if (getWidth() == size.width && getHeight() == size.height) {
            return;
        }
        setPreferredSize(size);
        JViewport held = port();
        if (held != null) {
            held.setViewSize(size);
        } else {
            setSize(size);
        }
        revalidate();
    }

    /**
     * The method reads the pointer in viewport pixels.
     * A scroll of the picture does not move that point.
     * A hidden picture uses the component point minus the view.
     *
     * @param event The event is the pointer event.
     * @return The return value is that point.
     */
    private Point pointer(MouseEvent event) {
        JViewport held = port();
        if (held != null && isShowing() && held.isShowing()) {
            Point origin = held.getLocationOnScreen();
            return new Point(event.getXOnScreen() - origin.x, event.getYOnScreen() - origin.y);
        }
        return new Point(event.getX() - viewX(), event.getY() - viewY());
    }

    private JViewport port() {
        if (getParent() instanceof JViewport) {
            return (JViewport) getParent();
        }
        return null;
    }

    private int viewX() {
        JViewport port = port();
        if (port == null) {
            return 0;
        }
        return port.getViewPosition().x;
    }

    private int viewY() {
        JViewport port = port();
        if (port == null) {
            return 0;
        }
        return port.getViewPosition().y;
    }

    private int comp(int spot) {
        return (int) Math.round(spot * (zoomPercent / 100.0));
    }

    private int uncomp(int pixel) {
        return (int) Math.round(pixel * (100.0 / zoomPercent));
    }

    public boolean antialias() {
        return antialias;
    }

    public boolean textAntialias() {
        return textAntialias;
    }

    public int wordDot() {
        return 12;
    }

    public int intentDot() {
        return 10;
    }

    public String shape(String id) {
        GraphModel model = pane.model();
        if (pane.focused()) {
            if (model.hasIntent(id)) {
                return "intent";
            }
            return "chip";
        }
        if (model.hasIntent(id)) {
            return "intent";
        }
        if (model.tether(id) == null && model.wordEdges(id).isEmpty()) {
            return "none";
        }
        int page = pane.page();
        if (page >= 10 && page <= 13) {
            return "circle";
        }
        return "chip";
    }

    public Color fill(String id) {
        GraphModel model = pane.model();
        if (model.hasIntent(id)) {
            if (model.excluded(id)) {
                return Color.DARK_GRAY;
            }
            return ink(id);
        }
        if (joined(id)) {
            return JOIN;
        }
        String parent = parentIntent(id);
        if (parent != null) {
            return fill(parent);
        }
        return CHIP_FILL;
    }

    public Color stroke(String id) {
        if (pane.model().hasIntent(id) || parentIntent(id) != null || joined(id)) {
            return fill(id);
        }
        return CHIP_EDGE;
    }

    public int width(String id) {
        return legible(rawWide(id), rawHigh(id))[0];
    }

    public int height(String id) {
        return legible(rawWide(id), rawHigh(id))[1];
    }

    private int rawWide(String id) {
        if ("chip".equals(shape(id))) {
            return 56;
        }
        return diameter(id);
    }

    private int rawHigh(String id) {
        if ("chip".equals(shape(id))) {
            return 32;
        }
        return diameter(id);
    }

    /**
     * The method lifts a glyph so the painted span stays clickable.
     * The floor is 32 screen pixels.
     * Density still widens an intent above that floor.
     * The stored seats stay.
     *
     * @param wide The wide span is the natural width.
     * @param high The high span is the natural height.
     * @return The return value is the lifted width and height.
     */
    private int[] legible(int wide, int high) {
        int floor = legibleFloor();
        int small = wide < high ? wide : high;
        if (small >= floor || small < 1) {
            return new int[] {wide, high};
        }
        int nextWide = wide * floor / small;
        int nextHigh = high * floor / small;
        if (nextWide < 1) {
            nextWide = 1;
        }
        if (nextHigh < 1) {
            nextHigh = 1;
        }
        return new int[] {nextWide, nextHigh};
    }

    /**
     * The method returns the layout span that paints as 32 screen pixels.
     *
     * @return The return value is that span.
     */
    private int legibleFloor() {
        int zoom = zoomPercent;
        if (zoom < 1) {
            zoom = 1;
        }
        return (LEGIBLE * 100 + zoom - 1) / zoom;
    }

    public int diameter(String id) {
        String drawn = shape(id);
        if ("dot".equals(drawn)) {
            if (pane.model().hasIntent(id)) {
                return intentDot();
            }
            return wordDot();
        }
        if ("circle".equals(drawn)) {
            return 32;
        }
        if ("chip".equals(drawn)) {
            return 56;
        }
        if (pane.model().hasIntent(id)) {
            return 48 + 8 * reach(id);
        }
        return 16;
    }

    /**
     * The method returns the reach the picture thread already counted.
     * A walk in flight does not count again on the event thread.
     *
     * @param id The id is one intent.
     * @return The return value is that count.
     */
    private int reach(String id) {
        if (EventQueue.isDispatchThread() && GraphDesk.passing(pane)) {
            Map<String, Integer> held = postedReach;
            if (held != null) {
                Integer count = held.get(id);
                if (count != null) {
                    return count.intValue();
                }
            }
            return 0;
        }
        return gather(id);
    }

    public float alpha(String id) {
        if (pane.model().excluded(id)) {
            return 1f;
        }
        if (pane.model().quiet(id)) {
            return 0.28f;
        }
        return 1f;
    }

    public Color ink(String id) {
        if (!pane.model().hasIntent(id)) {
            return CHIP_FILL;
        }
        boolean opened = paintHex == null;
        if (opened) {
            openPalette();
        }
        String hex = paintHex.get(id);
        if (hex == null) {
            hex = RING[0];
        }
        Color color = decode(hex);
        if (opened) {
            closePalette();
        }
        return color;
    }

    public Color relationInk(String relation) {
        boolean opened = paintHex == null;
        if (opened) {
            openPalette();
        }
        String hex = paintRelation.get(relation);
        if (hex == null) {
            hex = paintRelation.get(RELATIONS[RELATIONS.length - 1]);
        }
        Color color = decode(hex);
        if (opened) {
            closePalette();
        }
        return color;
    }

    public int x(String id) {
        return pane.layoutX(id);
    }

    public int y(String id) {
        return pane.layoutY(id);
    }

    /**
     * The method seats one point size on the node letters and the right-click items.
     *
     * @param size The size is the seated point size.
     */
    public void seatType(int size) {
        typePoints = size;
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, size);
        setFont(font);
        if (excludeItem != null) {
            excludeItem.setFont(font);
        }
        if (colorItem != null) {
            colorItem.setFont(font);
        }
        repaint();
    }

    /**
     * The method reports the seated point size.
     *
     * @return The return value is that size.
     */
    public int typePoints() {
        return typePoints;
    }

    /**
     * The method seats the canvas scale.
     * The scale is not stored.
     * A percent below 25 seats as 25.
     * A percent above 400 seats as 400.
     * A selected intent stays at the center of the viewport.
     * The layout seats stay.
     *
     * @param percent The percent is the scale.
     */
    void seatZoom(int percent) {
        int prior = zoomPercent;
        int next = percent;
        if (next < 25) {
            next = 25;
        }
        if (next > 400) {
            next = 400;
        }
        if (prior > 0 && next != prior) {
            grownWide = grownWide * next / prior;
            grownHigh = grownHigh * next / prior;
        }
        zoomPercent = next;
        if (next != prior) {
            settleCenter();
        }
        revalidate();
        repaint();
    }

    /**
     * The method puts the selected intent at the center of the viewport.
     * A missing selection leaves the view where it is.
     * A hidden picture leaves the view where it is.
     * The layout seats stay.
     */
    private void settleCenter() {
        String id = pane.selected();
        if (id == null || pane.model() == null || !pane.model().hasIntent(id)) {
            return;
        }
        JViewport port = port();
        if (port == null || port.getWidth() < 1 || port.getHeight() < 1) {
            return;
        }
        int halfW = port.getWidth() / 2;
        int halfH = port.getHeight() / 2;
        int pass = 0;
        while (pass < 3) {
            int zx = spotX(id) * zoomPercent / 100;
            int zy = spotY(id) * zoomPercent / 100;
            boolean nudged = false;
            if (zx < halfW) {
                int delta = halfW - zx;
                int add = (delta * 100 + zoomPercent - 1) / zoomPercent;
                if (add < 1) {
                    add = 1;
                }
                panX = panX + add;
                nudged = true;
            }
            if (zy < halfH) {
                int delta = halfH - zy;
                int add = (delta * 100 + zoomPercent - 1) / zoomPercent;
                if (add < 1) {
                    add = 1;
                }
                panY = panY + add;
                nudged = true;
            }
            fit();
            if (!nudged) {
                break;
            }
            pass = pass + 1;
        }
        int zx = spotX(id) * zoomPercent / 100;
        int zy = spotY(id) * zoomPercent / 100;
        int needW = zx + port.getWidth() - halfW;
        int needH = zy + port.getHeight() - halfH;
        if (needW < 1) {
            needW = 1;
        }
        if (needH < 1) {
            needH = 1;
        }
        if (canvasWide < needW) {
            grownWide = needW;
            canvasWide = needW;
        }
        if (canvasHigh < needH) {
            grownHigh = needH;
            canvasHigh = needH;
        }
        Dimension size = new Dimension(canvasWide, canvasHigh);
        applySize(size);
        port.setViewSize(size);
        int vx = zx - halfW;
        int vy = zy - halfH;
        if (vx < 0) {
            vx = 0;
        }
        if (vy < 0) {
            vy = 0;
        }
        port.setViewPosition(new Point(vx, vy));
    }

    /**
     * The method reports the canvas scale.
     *
     * @return The return value is the percent.
     */
    int zoomPercent() {
        return zoomPercent;
    }

    /**
     * The method maps a component pixel into layout space.
     * The zoom percent divides the pixel.
     *
     * @param pixel The pixel is the component coordinate.
     * @return The return value is the layout coordinate.
     */
    private int laid(int pixel) {
        return pixel * 100 / zoomPercent;
    }

    @Override
    public Dimension getPreferredSize() {
        // @aa-sdd L40 aetheris-plan-f-326.001.md FINESSE:f-326
        if (!walkPaints()) {
            fit();
        }
        return new Dimension(canvasWide, canvasHigh);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        // @aa-sdd L40 aetheris-plan-f-328.001.md FINESSE:f-328
        openPalette();
        try {
            paintField(graphics);
        } finally {
            closePalette();
        }
    }

    /**
     * The method paints the field with the colors this paint already chose.
     *
     * @param graphics The graphics is the event thread's context.
     */
    private void paintField(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        try {
            if (!walkPaints()) {
                fit();
            }
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            antialias = RenderingHints.VALUE_ANTIALIAS_ON.equals(
                    g.getRenderingHint(RenderingHints.KEY_ANTIALIASING));
            textAntialias = RenderingHints.VALUE_TEXT_ANTIALIAS_ON.equals(
                    g.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING));
            int width = Math.max(1, getWidth());
            int height = Math.max(1, getHeight());
            g.setColor(GROUND);
            g.fillRect(0, 0, width, height);
            g.setColor(FIELD);
            g.fillRoundRect(8, 8, Math.max(1, width - 16), Math.max(1, height - 16), 16, 16);
            Graphics2D nodes = (Graphics2D) g.create();
            try {
                double scale = zoomPercent / 100.0;
                nodes.scale(scale, scale);
                paintEdges(nodes);
                paintIntents(nodes);
                paintWords(nodes);
                paintLetters(nodes);
            } finally {
                nodes.dispose();
            }
            g.setColor(FRAME);
            g.setStroke(new BasicStroke(1f));
            g.drawRoundRect(1, 1, Math.max(1, width - 3), Math.max(1, height - 3), 16, 16);
        } finally {
            g.dispose();
        }
    }

    private void paintEdges(Graphics2D g) {
        GraphModel model = pane.model();
        boolean arrow = pane.focused() || (pane.page() >= 1 && pane.page() <= 8);
        for (String id : model.intentIds()) {
            for (String relation : RELATIONS) {
                Color color = relationInk(relation);
                for (String target : model.targets(id, relation)) {
                    boolean disabled = model.excluded(id) || model.excluded(target);
                    paintEdge(g, id, target, disabled ? darker(color) : color, arrow);
                }
            }
        }
        for (String id : model.intentIds()) {
            for (String word : model.meatWords(id)) {
                paintWordEdges(g, word, arrow);
            }
        }
    }

    private void paintWordEdges(Graphics2D g, String word, boolean arrow) {
        List<String> ends = new ArrayList<>();
        String tether = pane.model().tether(word);
        if (tether != null) {
            ends.add(tether);
        }
        for (String end : pane.model().wordEdges(word)) {
            if (!ends.contains(end)) {
                ends.add(end);
            }
        }
        for (String end : ends) {
            Color color = ink(end);
            if (pane.model().excluded(end)) {
                color = darker(color);
            }
            paintEdge(g, word, end, color, arrow);
        }
    }

    private void paintEdge(Graphics2D g, String from, String to, Color color, boolean arrow) {
        if (!pane.shown(from) || !pane.shown(to)) {
            return;
        }
        int x1 = spotX(from);
        int y1 = spotY(from);
        int x2 = spotX(to);
        int y2 = spotY(to);
        double dx = x2 - x1;
        double dy = y2 - y1;
        double len = Math.hypot(dx, dy);
        if (len < 1d) {
            return;
        }
        double ux = dx / len;
        double uy = dy / len;
        double start = rim(from, ux, uy);
        double stop = len - rim(to, ux, uy);
        if (stop - start < 2d) {
            return;
        }
        int sx = (int) Math.round(x1 + ux * start);
        int sy = (int) Math.round(y1 + uy * start);
        int ex = (int) Math.round(x1 + ux * stop);
        int ey = (int) Math.round(y1 + uy * stop);
        g.setColor(color);
        g.drawLine(sx, sy, ex, ey);
        if (!arrow) {
            return;
        }
        int ax = (int) Math.round(ex - ux * 12d - uy * 5d);
        int ay = (int) Math.round(ey - uy * 12d + ux * 5d);
        int bx = (int) Math.round(ex - ux * 12d + uy * 5d);
        int by = (int) Math.round(ey - uy * 12d - ux * 5d);
        g.drawLine(ex, ey, ax, ay);
        g.drawLine(ex, ey, bx, by);
    }

    private double rim(String id, double ux, double uy) {
        String drawn = shape(id);
        boolean meat = !pane.model().hasIntent(id);
        boolean stone = "chip".equals(drawn) || "circle".equals(drawn) || "dot".equals(drawn);
        if (!"intent".equals(drawn) && !(meat && stone)) {
            return 0d;
        }
        double rx = width(id) / 2.0;
        double ry = height(id) / 2.0;
        double den = Math.hypot(ry * ux, rx * uy);
        if (den < 0.001d) {
            return 0d;
        }
        return (rx * ry) / den;
    }

    private boolean joined(String id) {
        return intentEnds(id).size() >= 2;
    }

    private String parentIntent(String id) {
        List<String> ends = intentEnds(id);
        if (ends.isEmpty()) {
            return null;
        }
        return ends.get(0);
    }

    private List<String> intentEnds(String id) {
        List<String> ends = new ArrayList<>();
        GraphModel model = pane.model();
        String tether = model.tether(id);
        if (tether != null && model.hasIntent(tether)) {
            ends.add(tether);
        }
        List<String> links = model.wordEdges(id);
        for (int i = 0; i < links.size(); i++) {
            String end = links.get(i);
            if (model.hasIntent(end) && !ends.contains(end)) {
                ends.add(end);
            }
        }
        return ends;
    }

    private void paintIntents(Graphics2D g) {
        for (String id : pane.model().intentIds()) {
            if (!pane.shown(id)) {
                continue;
            }
            paintNode(g, id);
        }
    }

    private void paintWords(Graphics2D g) {
        for (String id : pane.model().intentIds()) {
            for (String word : pane.model().meatWords(id)) {
                if (!pane.shown(word)) {
                    continue;
                }
                paintNode(g, word);
            }
        }
    }

    private void paintNode(Graphics2D g, String id) {
        Composite prior = g.getComposite();
        float alpha = alpha(id);
        if (alpha < 1f) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }
        int cx = spotX(id);
        int cy = spotY(id);
        int w = width(id);
        int h = height(id);
        int left = cx - w / 2;
        int top = cy - h / 2;
        String drawn = shape(id);
        boolean meat = !pane.model().hasIntent(id);
        if (meat && ("chip".equals(drawn) || "circle".equals(drawn) || "dot".equals(drawn))) {
            paintMeatStone(g, left, top, w, h, fill(id), id.equals(pane.selected()));
        } else if ("intent".equals(drawn)) {
            paintIntentRing(g, left, top, w, fill(id), id.equals(pane.selected()));
        } else {
            g.setColor(fill(id));
            g.fillOval(left, top, w, h);
            g.setColor(stroke(id));
            g.drawOval(left, top, w, h);
        }
        if (pane.lost(id)) {
            g.setColor(FRAME);
            g.drawOval(left - 3, top - 3, w + 6, h + 6);
        }
        g.setComposite(prior);
    }

    private void paintIntentRing(Graphics2D g, int left, int top, int d, Color ink, boolean chosen) {
        int band = d / 3;
        if (band < 10) {
            band = 10;
        }
        int room = d / 2 - 8;
        if (room < 4) {
            room = 4;
        }
        if (band > room) {
            band = room;
        }
        int oval = d - band;
        int x = left + (d - oval) / 2;
        int y = top + (d - oval) / 2;
        Stroke keptStroke = g.getStroke();
        Paint keptPaint = g.getPaint();
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setStroke(new BasicStroke(band, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(0, 0, 0, chosen ? 150 : 100));
        g.drawOval(x + 3, y + 4, oval, oval);
        float lit = chosen ? 0.58f : 0.40f;
        float kept = chosen ? 0.50f : 0.36f;
        g.setPaint(new GradientPaint(left, top, lift(ink, lit), left + d, top + d, shade(ink, kept)));
        g.drawOval(x, y, oval, oval);
        // Java arcs start at 3 o'clock and run counter-clockwise. Light is upper left.
        int lip = Math.max(2, band / 5);
        g.setStroke(new BasicStroke(lip, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int ox = left + lip / 2;
        int oy = top + lip / 2;
        int od = d - lip;
        g.setColor(shade(ink, 0.22f));
        g.drawArc(ox, oy, od, od, 230, 120);
        Color gloss = lift(ink, chosen ? 0.94f : 0.86f);
        int glossAlpha = chosen ? 235 : 190;
        g.setColor(new Color(gloss.getRed(), gloss.getGreen(), gloss.getBlue(), glossAlpha));
        g.drawArc(ox, oy, od, od, 70, 120);
        int hole = oval - band;
        if (hole < 4) {
            hole = 4;
        }
        int hx = left + (d - hole) / 2;
        int hy = top + (d - hole) / 2;
        g.setColor(new Color(0, 0, 0, chosen ? 170 : 130));
        g.drawArc(hx, hy, hole, hole, 70, 120);
        int well = chosen ? 170 : 115;
        g.setColor(new Color(255, 255, 255, well));
        g.drawArc(hx, hy, hole, hole, 230, 120);
        int shine = Math.max(3, band / 3);
        g.setStroke(new BasicStroke(shine, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(255, 255, 255, chosen ? 210 : 145));
        g.drawArc(x, y, oval, oval, 112, 58);
        g.setStroke(keptStroke);
        g.setPaint(keptPaint);
    }

    private void paintMeatStone(Graphics2D g, int left, int top, int w, int h, Color ink, boolean chosen) {
        Stroke keptStroke = g.getStroke();
        Paint keptPaint = g.getPaint();
        boolean disc = w == h;
        int arc = Math.min(w, h) / 2;
        if (arc < 8) {
            arc = 8;
        }
        int dropX = w < 12 ? 1 : 2;
        int dropY = h < 12 ? 1 : 3;
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setColor(new Color(0, 0, 0, chosen ? 145 : 100));
        if (disc) {
            g.fillOval(left + dropX, top + dropY, w, h);
        } else {
            g.fillRoundRect(left + dropX, top + dropY, w, h, arc, arc);
        }
        float lit = chosen ? 0.40f : 0.22f;
        float kept = chosen ? 0.58f : 0.72f;
        g.setPaint(new GradientPaint(left, top, lift(ink, lit), left + w, top + h, shade(ink, kept)));
        if (disc) {
            g.fillOval(left, top, w, h);
        } else {
            g.fillRoundRect(left, top, w, h, arc, arc);
        }
        if (disc) {
            int lip = Math.max(1, Math.min(w, h) / 8);
            g.setStroke(new BasicStroke(lip, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int ix = left + lip / 2;
            int iy = top + lip / 2;
            int iw = w - lip;
            int ih = h - lip;
            if (iw < 2) {
                iw = 2;
            }
            if (ih < 2) {
                ih = 2;
            }
            g.setColor(shade(ink, 0.30f));
            g.drawArc(ix, iy, iw, ih, 215, 150);
            Color gloss = lift(ink, chosen ? 0.94f : 0.82f);
            int glossAlpha = chosen ? 230 : 188;
            g.setColor(new Color(gloss.getRed(), gloss.getGreen(), gloss.getBlue(), glossAlpha));
            g.drawArc(ix, iy, iw, ih, 45, 140);
            if (Math.min(w, h) >= 12) {
                int shine = Math.max(2, Math.min(w, h) / 6);
                g.setStroke(new BasicStroke(shine, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.setColor(new Color(255, 255, 255, chosen ? 200 : 145));
                int inset = Math.max(2, Math.min(w, h) / 5);
                int sw = w - inset * 2;
                int sh = h - inset * 2;
                if (sw >= 4 && sh >= 4) {
                    g.drawArc(left + inset, top + inset, sw, sh, 108, 46);
                }
            }
        } else {
            Shape priorClip = g.getClip();
            g.clip(new RoundRectangle2D.Float(left, top, w, h, arc, arc));
            Color gloss = lift(ink, chosen ? 0.94f : 0.86f);
            g.setColor(new Color(gloss.getRed(), gloss.getGreen(), gloss.getBlue(), chosen ? 170 : 125));
            g.fillOval(left + 2, top + 1, Math.max(4, w / 2), Math.max(4, h / 2));
            g.setColor(new Color(255, 255, 255, chosen ? 180 : 130));
            g.fillOval(left + 4, top + 2, Math.max(3, h / 3), Math.max(3, h / 4));
            Color deep = shade(ink, 0.28f);
            g.setColor(new Color(deep.getRed(), deep.getGreen(), deep.getBlue(), chosen ? 120 : 90));
            g.fillOval(left + 1, top + (h * 2) / 3, w - 2, Math.max(4, h / 2));
            g.setClip(priorClip);
        }
        g.setStroke(keptStroke);
        g.setPaint(keptPaint);
    }

    private static int mix(int channel, int toward, float amount) {
        int value = (int) Math.round(channel + (toward - channel) * amount);
        if (value < 0) {
            return 0;
        }
        if (value > 255) {
            return 255;
        }
        return value;
    }

    private static Color lift(Color color, float amount) {
        return new Color(
                mix(color.getRed(), 255, amount),
                mix(color.getGreen(), 255, amount),
                mix(color.getBlue(), 255, amount));
    }

    private static Color shade(Color color, float keep) {
        float toward = 1f - keep;
        return new Color(
                mix(color.getRed(), 0, toward),
                mix(color.getGreen(), 0, toward),
                mix(color.getBlue(), 0, toward));
    }

    private void paintLetters(Graphics2D g) {
        g.setColor(LETTER);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, typePoints));
        for (String id : pane.model().intentIds()) {
            if (pane.shown(id)) {
                g.drawString(id, spotX(id) + 8, spotY(id) - 8);
            }
            for (String word : pane.model().meatWords(id)) {
                if (!pane.shown(word)) {
                    continue;
                }
                g.drawString(word, spotX(word) + width(word) / 2 + 4, spotY(word) + 4);
            }
        }
    }

    int spotX(String id) {
        return ORIGIN_X + panX + shiftX + seatX(id);
    }

    int spotY(String id) {
        return ORIGIN_Y + panY + shiftY + seatY(id);
    }

    /**
     * The method reads the horizontal seat the closer picture draws.
     * A door intent sits on a ring around the focused intent.
     * A drag in that picture stays on the close canvas.
     * The stored seat stays.
     *
     * @param id The id is an intent or a meat word.
     * @return The return value is the horizontal seat.
     */
    private int seatX(String id) {
        return closeSeat(id, true);
    }

    /**
     * The method reads the vertical seat the closer picture draws.
     * A door intent sits on a ring around the focused intent.
     * A drag in that picture stays on the close canvas.
     * The stored seat stays.
     *
     * @param id The id is an intent or a meat word.
     * @return The return value is the vertical seat.
     */
    private int seatY(String id) {
        return closeSeat(id, false);
    }

    /**
     * The method moves one node on the close canvas.
     * The stored layout seat stays.
     *
     * @param id The id is an intent or a meat word.
     * @param dx The dx is the horizontal move in layout units.
     * @param dy The dy is the vertical move in layout units.
     */
    void shiftClose(String id, int dx, int dy) {
        if (!pane.focused() || id == null) {
            return;
        }
        ensureClose();
        int index = closeIds.indexOf(id);
        int x = pane.layoutX(id);
        int y = pane.layoutY(id);
        if (index >= 0) {
            x = closeX.get(index).intValue();
            y = closeY.get(index).intValue();
        }
        x += dx;
        y += dy;
        if (index < 0) {
            putClose(id, x, y, true);
            return;
        }
        closeX.set(index, Integer.valueOf(x));
        closeY.set(index, Integer.valueOf(y));
        closeHeld.set(index, Boolean.TRUE);
    }

    private int closeSeat(String id, boolean horizontal) {
        int raw = horizontal ? pane.layoutX(id) : pane.layoutY(id);
        if (!pane.focused()) {
            if (closeFor != null) {
                dropCluster();
            }
            return raw;
        }
        ensureClose();
        int index = closeIds.indexOf(id);
        if (index < 0) {
            return raw;
        }
        return horizontal ? closeX.get(index).intValue() : closeY.get(index).intValue();
    }

    private void ensureClose() {
        String focus = pane.focusId();
        if (focus == null) {
            return;
        }
        if (focus.equals(closeFor)) {
            return;
        }
        placeClose(focus);
    }

    private void placeClose(String focus) {
        List<String> priorIds = new ArrayList<String>(closeIds);
        List<Integer> priorX = new ArrayList<Integer>(closeX);
        List<Integer> priorY = new ArrayList<Integer>(closeY);
        List<Boolean> priorHeld = new ArrayList<Boolean>(closeHeld);
        closeIds.clear();
        closeX.clear();
        closeY.clear();
        closeHeld.clear();
        closeFor = focus;
        int focusX = pane.layoutX(focus);
        int focusY = pane.layoutY(focus);
        int kept = indexOf(priorIds, focus);
        boolean focusHeld = false;
        if (kept >= 0) {
            focusX = priorX.get(kept).intValue();
            focusY = priorY.get(kept).intValue();
            focusHeld = priorHeld.get(kept).booleanValue();
        }
        putClose(focus, focusX, focusY, focusHeld);
        GraphModel model = pane.model();
        int layoutX = pane.layoutX(focus);
        int layoutY = pane.layoutY(focus);
        List<String> words = model.meatWords(focus);
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            int at = indexOf(priorIds, word);
            if (at >= 0 && priorHeld.get(at).booleanValue()) {
                putClose(word, priorX.get(at).intValue(), priorY.get(at).intValue(), true);
                continue;
            }
            if (!pane.shown(word)) {
                continue;
            }
            putClose(word,
                    focusX + pane.layoutX(word) - layoutX,
                    focusY + pane.layoutY(word) - layoutY,
                    false);
        }
        List<String> doors = new ArrayList<String>();
        List<String> ids = model.intentIds();
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            if (id.equals(focus)) {
                continue;
            }
            if (pane.shown(id)) {
                doors.add(id);
            }
        }
        int count = doors.size();
        int radius = 96;
        for (int i = 0; i < words.size(); i++) {
            int dx = pane.layoutX(words.get(i)) - layoutX;
            int dy = pane.layoutY(words.get(i)) - layoutY;
            int need = (int) Math.round(Math.hypot(dx, dy)) + 64;
            if (need > radius) {
                radius = need;
            }
        }
        if (count > 1) {
            double sin = Math.sin(Math.PI / count);
            if (sin > 0.0) {
                int chord = (int) Math.ceil(36.0 / sin);
                if (chord > radius) {
                    radius = chord;
                }
            }
        }
        for (int i = 0; i < count; i++) {
            String door = doors.get(i);
            int at = indexOf(priorIds, door);
            if (at >= 0 && priorHeld.get(at).booleanValue()) {
                putClose(door, priorX.get(at).intValue(), priorY.get(at).intValue(), true);
                continue;
            }
            double angle = (-Math.PI / 2.0) + ((2.0 * Math.PI * i) / count);
            int ringX = (int) Math.round(Math.cos(angle) * radius);
            int ringY = (int) Math.round(Math.sin(angle) * radius);
            putClose(door, focusX + ringX, focusY + ringY, false);
        }
        for (int i = 0; i < priorIds.size(); i++) {
            if (!priorHeld.get(i).booleanValue()) {
                continue;
            }
            if (closeIds.contains(priorIds.get(i))) {
                continue;
            }
            putClose(priorIds.get(i), priorX.get(i).intValue(), priorY.get(i).intValue(), true);
        }
    }

    private void putClose(String id, int x, int y, boolean held) {
        closeIds.add(id);
        closeX.add(Integer.valueOf(x));
        closeY.add(Integer.valueOf(y));
        closeHeld.add(Boolean.valueOf(held));
    }

    private static int indexOf(List<String> ids, String id) {
        for (int i = 0; i < ids.size(); i++) {
            if (id.equals(ids.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * The picture thread measures one generation.
     * The event thread paints that size while a walk is in flight.
     *
     * @param source The snapshot the model thread handed over.
     */
    void measure(GraphModel source) {
        GraphModel model = source == null ? pane.model() : source;
        Map<String, Integer> reach = new HashMap<String, Integer>();
        if (model != null) {
            List<String> ids = model.intentIds();
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                reach.put(id, Integer.valueOf(GraphSpan.gather(model, id)));
            }
        }
        fit(model, reach);
        postedReach = reach;
    }

    /**
     * The method counts how many times this picture measured the model.
     * The picture thread measures. The event thread does not, while a walk is in flight.
     *
     * @return The return value is that count.
     */
    int measureWalks() {
        return measureWalks;
    }

    /**
     * The method reports that the event thread should paint the measured size.
     * A walk in flight is that case. Any other call measures in line.
     *
     * @return True when the event thread keeps the measured size.
     */
    private boolean walkPaints() {
        return EventQueue.isDispatchThread() && GraphDesk.passing(pane);
    }

    private void fit() {
        fit(pane.model(), null);
    }

    private void fit(GraphModel model, Map<String, Integer> reach) {
        measureWalks = measureWalks + 1;
        int points = typePoints < 1 ? 1 : typePoints;
        int minLeft = 0;
        int minTop = 0;
        int maxRight = 0;
        int maxBottom = 0;
        if (model != null) {
            List<String> ids = model.intentIds();
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                if (!pane.shown(id)) {
                    continue;
                }
                int reached = 0;
                if (reach != null) {
                    Integer counted = reach.get(id);
                    if (counted != null) {
                        reached = counted.intValue();
                    }
                } else {
                    reached = GraphSpan.gather(model, id);
                }
                int[] held = hold(id, true, reached, points);
                if (held[0] < minLeft) {
                    minLeft = held[0];
                }
                if (held[1] < minTop) {
                    minTop = held[1];
                }
                if (held[2] > maxRight) {
                    maxRight = held[2];
                }
                if (held[3] > maxBottom) {
                    maxBottom = held[3];
                }
                List<String> words = model.meatWords(id);
                for (int w = 0; w < words.size(); w++) {
                    if (!pane.shown(words.get(w))) {
                        continue;
                    }
                    int[] word = hold(words.get(w), false, 0, points);
                    if (word[0] < minLeft) {
                        minLeft = word[0];
                    }
                    if (word[1] < minTop) {
                        minTop = word[1];
                    }
                    if (word[2] > maxRight) {
                        maxRight = word[2];
                    }
                    if (word[3] > maxBottom) {
                        maxBottom = word[3];
                    }
                }
            }
        }
        int needX = minLeft < 32 ? 32 - minLeft : 0;
        int needY = minTop < 32 ? 32 - minTop : 0;
        if (needX > shiftX) {
            shiftX = needX;
        }
        if (needY > shiftY) {
            shiftY = needY;
        }
        int wide = maxRight + shiftX + 48;
        int high = maxBottom + shiftY + 48;
        if (wide < 640) {
            wide = 640;
        }
        if (high < 480) {
            high = 480;
        }
        wide = wide * zoomPercent / 100;
        high = high * zoomPercent / 100;
        if (wide < 1) {
            wide = 1;
        }
        if (high < 1) {
            high = 1;
        }
        if (wide < grownWide) {
            wide = grownWide;
        }
        if (high < grownHigh) {
            high = grownHigh;
        }
        canvasWide = wide;
        canvasHigh = high;
    }

    private int[] hold(String id, boolean intent, int reached, int points) {
        int sx = ORIGIN_X + panX + seatX(id);
        int sy = ORIGIN_Y + panY + seatY(id);
        int[] box = GraphSpan.box(id, intent, reached, points);
        return new int[] {
            sx - box[0],
            sy - box[2],
            sx + box[1],
            sy + box[3]
        };
    }

    void aim(int x, int y) {
        String hit = nodeAt(x, y);
        if (hit == null) {
            return;
        }
        if (pane.model().hasIntent(hit)) {
            pane.select(hit);
            return;
        }
        pane.selectWord(hit);
    }

    private String nodeAt(int x, int y) {
        String intent = intentAt(x, y);
        if (intent != null) {
            return intent;
        }
        List<String> ids = pane.model().intentIds();
        for (int i = ids.size() - 1; i >= 0; i--) {
            List<String> meat = pane.model().meatWords(ids.get(i));
            for (int w = meat.size() - 1; w >= 0; w--) {
                String word = meat.get(w);
                if (covers(word, x, y)) {
                    return word;
                }
            }
        }
        return null;
    }

    private int gather(String start) {
        GraphModel model = pane.model();
        List<String> seen = new ArrayList<>();
        List<String> queue = new ArrayList<>();
        seen.add(start);
        queue.add(start);
        int count = 0;
        while (!queue.isEmpty()) {
            String id = queue.remove(0);
            for (String relation : RELATIONS) {
                for (String target : model.targets(id, relation)) {
                    if (seen.contains(target)) {
                        continue;
                    }
                    seen.add(target);
                    queue.add(target);
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * The method chooses each intent color once, then each relation color once.
     * A later intent does not recount the earlier ones.
     * A stored color stays that color.
     * The ring colors stay on the first intents.
     * A paint keeps this choice until it ends.
     */
    private void openPalette() {
        // @aa-sdd L40 aetheris-plan-f-328.001.md FINESSE:f-328
        if (paintHex != null) {
            return;
        }
        Map<String, String> hex = new HashMap<String, String>();
        List<String> worn = new ArrayList<String>();
        worn.add(PAGE_TEXT);
        GraphModel model = pane.model();
        if (model != null) {
            List<String> ids = model.intentIds();
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                String stored = model.color(id);
                String chosen;
                if (stored != null && !stored.isEmpty()) {
                    chosen = stored.toUpperCase(Locale.ROOT);
                } else if (i < RING.length) {
                    chosen = RING[i];
                } else {
                    chosen = nextWell(worn);
                }
                hex.put(id, chosen);
                worn.add(chosen);
            }
        }
        Map<String, String> relations = new HashMap<String, String>();
        for (int i = 0; i < RELATIONS.length; i++) {
            String chosen = nextWell(worn);
            worn.add(chosen);
            relations.put(RELATIONS[i], chosen);
        }
        paintHex = hex;
        paintRelation = relations;
    }

    /**
     * The method drops the colors this paint chose.
     * The next paint chooses again.
     */
    private void closePalette() {
        paintHex = null;
        paintRelation = null;
    }

    private static String nextWell(List<String> worn) {
        for (String ink : WELL) {
            if (!wears(worn, ink)) {
                return ink;
            }
        }
        return WELL[WELL.length - 1];
    }

    private static boolean wears(List<String> worn, String ink) {
        for (String held : worn) {
            if (held != null && held.equalsIgnoreCase(ink)) {
                return true;
            }
        }
        return false;
    }

    private static Color decode(String hex) {
        String text = hex;
        if (text.startsWith("#")) {
            text = text.substring(1);
        }
        return new Color(Integer.parseInt(text, 16));
    }

    private static Color darker(Color color) {
        return new Color(color.getRed() / 3, color.getGreen() / 3, color.getBlue() / 3);
    }

    private String intentAt(int x, int y) {
        List<String> ids = pane.model().intentIds();
        for (int i = ids.size() - 1; i >= 0; i--) {
            String id = ids.get(i);
            if (covers(id, x, y)) {
                return id;
            }
        }
        return null;
    }

    private boolean covers(String id, int x, int y) {
        int wide = width(id);
        int high = height(id);
        int left = spotX(id) - wide / 2;
        int top = spotY(id) - high / 2;
        return x >= left && x < left + wide && y >= top && y < top + high;
    }
}
