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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import org.casserole.app.ui.ssh.AppleScrollBar;
import org.casserole.chat.ReasonSpinner;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Test;

/**
 * The paint proof runs off the window.
 *
 * @author clayl (2026-11-24)
 */
class GraphPaintTest {

    @Test
    void pt1ChipOnACloserView() {
        GraphPane pane = fixture("pt1");
        pane.showPage(1);
        GraphPicture picture = pane.picture();
        assertEquals("chip", picture.shape("home"));
        assertEquals(new Color(0xE4DED6), picture.fill("home"));
        assertEquals(picture.fill("home"), picture.stroke("home"));
        assertEquals(56, picture.width("home"));
        assertEquals(32, picture.height("home"));
    }

    @Test
    void pt2PageTenUsesTheSmallerCircle() {
        GraphPane pane = fixture("pt2");
        GraphPicture picture = pane.picture();
        pane.showPage(10);
        assertCircle(picture);
        pane.showPage(11);
        assertCircle(picture);
        pane.showPage(12);
        assertCircle(picture);
        pane.showPage(13);
        assertCircle(picture);
    }

    @Test
    void pt3EverythingOpensPageTen() {
        GraphPane pane = fixture("pt3");
        pane.strip().press("EVERYTHING");
        assertEquals(10, pane.page());
        assertFalse(pane.opened(9));
        assertEquals("WHOLE", pane.filter());
        pane.click(10, 20);
        assertEquals(1, pane.page());
        assertEquals(10, pane.forceLayout().x("house"));
        assertEquals(20, pane.forceLayout().y("house"));
        assertFalse(pane.strip().visible("LAYERED"));
    }

    @Test
    void pt4IntentIsLargerWhenTheNeighborhoodIsLarger() {
        GraphPane pane = fixture("pt4");
        GraphPicture picture = pane.picture();
        int house = picture.diameter("house");
        int lane = picture.diameter("lane");
        assertTrue(house > 28);
        assertTrue(house > 16);
        assertTrue(house > picture.wordDot());
        assertTrue(house > picture.intentDot());
        assertTrue(house > lane);
        assertTrue(lane > 28);
    }

    @Test
    void pt5QuietAndExcluded() {
        GraphPane pane = fixture("pt5");
        GraphPicture picture = pane.picture();
        assertEquals(0.28f, picture.alpha("lane"), 0.001f);
        pane.model().exclude("house");
        assertEquals(Color.DARK_GRAY, picture.fill("house"));
        pane.model().include("house");
        assertEquals(new Color(0xE4DED6), picture.fill("house"));
    }

    @Test
    void pt6HintsDoNotMoveTheLayout() throws Exception {
        GraphPane pane = fixture("pt6");
        ForceLayout force = pane.forceLayout();
        force.place(pane.model(), 10, 20);
        int x = force.x("house");
        int y = force.y("house");
        GraphPicture picture = pane.picture();
        picture.setSize(640, 480);
        BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        picture.paint(graphics);
        graphics.dispose();
        assertTrue(picture.antialias());
        assertTrue(picture.textAntialias());
        assertEquals(x, force.x("house"));
        assertEquals(y, force.y("house"));
        assertEquals(x, picture.x("house"));
        assertEquals(y, picture.y("house"));
        assertTrue(picture.getToolTipText().contains("Drag"));
    }

    @Test
    void pt7ThemeRingStaysWhenPaletteStores() {
        GraphPane pane = fixture("pt7");
        pane.model().setColor("house", null);
        pane.strip().store("badge", "#FFFFFF");
        GraphPicture picture = pane.picture();
        assertEquals(new Color(0x6B93FF), picture.ink("house"));
        assertEquals(new Color(0xE879F9), picture.ink("bird"));
        assertEquals(new Color(0x00C853), picture.ink("lane"));
        assertEquals("#FFFFFF", pane.stored("badge"));
    }

    @Test
    void pt8LaterIntentAndRelationTakeTheWell() {
        GraphPane pane = fixture("pt8");
        pane.model().addIntent("n4");
        pane.model().addIntent("n5");
        pane.model().addIntent("n6");
        pane.model().addIntent("n7");
        GraphPicture picture = pane.picture();
        assertEquals(new Color(0x73AEFF), picture.ink("n7"));
        assertEquals(new Color(0x00FF66), picture.relationInk("supports"));
    }

    @Test
    void actButtonsWearTheDarkRed() {
        GraphPane pane = fixture("pt9");
        pane.showPage(1);
        GraphStrip strip = pane.strip();
        assertEquals(new Color(0x7A4444), strip.fill("SPLIT"));
        assertEquals(new Color(0x7A4444), strip.fill("MERGE"));
        assertEquals(new Color(0x7A4444), strip.fill("REJECT"));
        assertEquals(new Color(0x7A4444), strip.fill("PROMOTE"));
        assertEquals(new Color(0x7A4444), strip.fill("PIN"));
        assertEquals(new Color(0xFFFFFF), strip.ink("PIN"));
        pane.select("house");
        assertEquals(new Color(0x522E2E), strip.fill("PIN"));
        assertEquals(new Color(0xFFFFFF), strip.ink("PIN"));
        assertTrue(strip.tip("PIN").contains("PIN"));
        assertTrue(strip.tip("EVERYTHING").contains("EVERYTHING"));
        assertFalse(strip.bridgeVisible());
    }

    @Test
    void theGraphBarsWearPurpleAndRed() {
        GraphPane pane = fixture("ft-bars");
        pane.showPage(1);
        pane.strip().sync();
        GraphStrip strip = pane.strip();
        assertEquals(new Color(0x547AAA), strip.fill("WHOLE"));
        assertEquals(new Color(0xFFFFFF), strip.ink("WHOLE"));
        assertEquals(new Color(0x7A447A), strip.fill("REICHENBACH"));
        assertEquals(new Color(0xFFFFFF), strip.ink("REICHENBACH"));
        assertEquals(new Color(0x7A447A), strip.fill("MODEL"));
        assertEquals(new Color(0x7A447A), strip.fill("PALETTE"));
        assertEquals(new Color(0x7A447A), strip.fill("LAYERED"));
        assertEquals(new Color(0xFFFFFF), strip.ink("LAYERED"));
        pane.showPage(12);
        assertEquals(new Color(0x547AAA), strip.fill("FORCE"));
        assertEquals(new Color(0xFFFFFF), strip.ink("FORCE"));
        assertEquals(new Color(0x7A447A), strip.fill("RADIAL"));
        assertEquals(new Color(0x7A4444), strip.fill("RESET LAYOUT"));
        assertEquals(new Color(0xFFFFFF), strip.ink("RESET LAYOUT"));
        assertEquals(new Color(0x7A4444), strip.fill("SPLIT"));
        JButton fold = (JButton) named(strip, "graph-bb1-fold");
        JButton tools = (JButton) named(strip, "graph-bb2-fold");
        assertEquals(new Color(0x7A447A), fold.getBackground());
        assertEquals(new Color(0xFFFFFF), fold.getForeground());
        assertEquals(new Color(0x7A447A), tools.getBackground());
        assertEquals(new Color(0xFFFFFF), tools.getForeground());
        JButton minus = (JButton) named(strip, "graph-zoom-minus");
        JButton plus = (JButton) named(strip, "graph-zoom-plus");
        assertEquals(new Color(0x7A4444), minus.getBackground());
        assertEquals(new Color(0xFFFFFF), minus.getForeground());
        assertEquals(new Color(0x7A4444), plus.getBackground());
        assertEquals(new Color(0xFFFFFF), plus.getForeground());
        pane.select("house");
        assertEquals(new Color(0x522E2E), strip.fill("PIN"));
        assertEquals(new Color(0xFFFFFF), strip.ink("REJECT"));
        pane.model().reject("house");
        pane.strip().sync();
        assertEquals(new Color(0x522E2E), strip.fill("REJECT"));
        assertEquals(new Color(0xFFFFFF), strip.ink("REJECT"));
    }

    @Test
    void pt10DividerKeepsItsShare() throws Exception {
        GraphPane pane = fixture("pt10");
        GraphDivider divider = pane.divider();
        assertEquals(11, divider.breadth());
        assertEquals(11, divider.getPreferredSize().width);
        assertEquals(0.72d, divider.share(), 0.0001d);
        assertNull(divider.shellKey());
        assertEquals(new Color(0x3A3A3A), divider.fillColor());
        assertEquals(new Color(0x8A8A8A), divider.edgeColor());
        String source = Files.readString(new File(
                "src/main/java/org/aetheris/lcarsgraph/GraphDivider.java").toPath(),
                StandardCharsets.UTF_8);
        assertFalse(source.contains("ui.aetheris.inspector.divider"));
        assertTrue(source.contains("0x5C5C5C"));
        assertTrue(source.contains("0xDCDCDC"));
        assertFalse(source.contains("new Timer"));
        int before = divider.perMille();
        divider.move(0, 400);
        assertEquals(before, divider.perMille());
        assertEquals(720, divider.pictureEdge(1011));
        divider.move(16, 400);
        assertEquals(400, divider.perMille());
        assertEquals(0.4d, divider.share(), 0.0001d);
        assertEquals(400, divider.pictureEdge(1011));
        assertEquals(472, divider.pictureEdge(1111));
        divider.pressUpper();
        assertEquals(0, divider.perMille());
        assertTrue(divider.pictureHidden());
        divider.pressUpper();
        assertEquals(0, divider.perMille());
        divider.pressLower();
        assertEquals(429, divider.perMille());
        assertFalse(divider.pictureHidden());
        assertFalse(divider.inspectorHidden());
        divider.pressLower();
        assertEquals(1000, divider.perMille());
        assertTrue(divider.inspectorHidden());
        divider.pressLower();
        assertEquals(1000, divider.perMille());
        divider.pressUpper();
        assertEquals(429, divider.perMille());
        assertNull(divider.shellKey());
    }

    @Test
    void aDividerDragSnapsAndScrolls() throws Exception {
        GraphPane pane = fixture("pt-split");
        JScrollPane scroll = pane.inspectorScroll();
        assertEquals(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, scroll.getHorizontalScrollBarPolicy());
        assertEquals(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, scroll.getVerticalScrollBarPolicy());
        assertSame(pane.inspector(), scroll.getViewport().getView());
        assertTrue(scroll.isFocusable());
        assertTrue(scroll.isRequestFocusEnabled());
        assertNull(scroll.getBorder());
        pane.setSize(1111, 280);
        layoutTree(pane);
        int open = scroll.getWidth();
        assertTrue(open > 0);
        int added = 0;
        while (pane.inspector().getPreferredSize().height <= scroll.getHeight() && added < 40) {
            pane.model().addIntent("row-" + added);
            added = added + 1;
        }
        assertTrue(pane.inspector().getPreferredSize().height > scroll.getHeight());
        pane.divider().pressLower();
        layoutTree(pane);
        assertEquals(0, scroll.getWidth());
        assertTrue(pane.divider().inspectorHidden());
        pane.divider().pressUpper();
        layoutTree(pane);
        assertEquals(open, scroll.getWidth());
        assertFalse(pane.divider().inspectorHidden());
        pane.divider().move(16, 400);
        layoutTree(pane);
        assertEquals(400, pane.divider().perMille());
        assertTrue(scroll.getWidth() != open);
    }

    @Test
    void pt11PaneOpensOnForce() {
        GraphPane pane = fixture("pt11");
        assertEquals(12, pane.page());
        assertEquals("force", pane.layoutName());
        assertEquals("INTENT SHAPE GRAPH", pane.label());
        assertTrue(pane.strip().visible("FORCE"));
        assertTrue(pane.strip().visible("PIN"));
    }

    @Test
    void actsSitOnTheForcePage() {
        GraphPane pane = fixture("pt-acts");
        assertEquals(12, pane.page());
        assertTrue(pane.strip().visible("PIN"));
        assertTrue(pane.strip().visible("REJECT"));
        assertTrue(pane.strip().visible("SPLIT"));
        assertTrue(pane.strip().visible("MERGE"));
        assertTrue(pane.strip().visible("PROMOTE"));
        assertFalse(pane.model().pin("house"));
        pane.showPage(12);
        pane.picture().aim(pane.picture().spotX("house"), pane.picture().spotY("house"));
        assertEquals("house", pane.selected());
        pane.strip().press("PIN");
        assertTrue(pane.model().pinned("house"));
    }

    @Test
    void resetLayoutLaysTheNodesAgain() {
        GraphPane pane = fixture("pt-reset");
        pane.showPage(12);
        assertTrue(pane.strip().visible("RESET LAYOUT"));
        assertTrue(pane.picture().getParent() instanceof JViewport);
        int house = pane.forceLayout().x("house");
        int bird = pane.forceLayout().y("bird");
        pane.forceLayout().seat("house", 4000, 4000);
        pane.forceLayout().seat("bird", 4200, 4000);
        pane.forceLayout().seat("lane", 4400, 4000);
        pane.forceLayout().seat("home", 4600, 4000);
        pane.strip().press("RESET LAYOUT");
        assertEquals(house, pane.forceLayout().x("house"));
        assertEquals(bird, pane.forceLayout().y("bird"));
        int wide = pane.picture().getPreferredSize().width;
        pane.picture().seatZoom(200);
        assertEquals(200, pane.picture().zoomPercent());
        assertTrue(pane.picture().getPreferredSize().width > wide);
        pane.picture().seatZoom(100);
    }

    @Test
    void zoomButtonsScaleTheNodes() {
        GraphPane pane = fixture("pt-zoom");
        pane.showPage(12);
        JTextField field = zoomField(pane);
        assertEquals("Zoom", ((JLabel) named(pane.strip(), "graph-zoom-label")).getText());
        assertEquals("100", field.getText());
        assertFalse(pane.strip().visible("ZOOM IN"));
        assertFalse(pane.strip().visible("ZOOM OUT"));
        GraphPicture picture = pane.picture();
        picture.getPreferredSize();
        int spotX = picture.spotX("house");
        int spotY = picture.spotY("house");
        int wide = picture.getPreferredSize().width;
        int center = centerPixel(picture, spotX, spotY);
        zoomIn(pane);
        assertEquals(125, picture.zoomPercent());
        assertEquals("125", field.getText());
        assertEquals(spotX, picture.spotX("house"));
        assertTrue(picture.getPreferredSize().width > wide);
        zoomOut(pane);
        assertEquals(100, picture.zoomPercent());
        assertEquals("100", field.getText());
        assertEquals(wide, picture.getPreferredSize().width);
        picture.seatZoom(200);
        assertEquals(center, centerPixel(picture, spotX * 2, spotY * 2));
        picture.dispatchEvent(new MouseEvent(
                picture,
                MouseEvent.MOUSE_RELEASED,
                System.currentTimeMillis(),
                0,
                spotX * 2,
                spotY * 2,
                1,
                false,
                MouseEvent.BUTTON1));
        assertEquals("house", pane.selected());
        picture.seatZoom(400);
        zoomIn(pane);
        assertEquals(400, picture.zoomPercent());
        assertEquals("400", field.getText());
        picture.seatZoom(25);
        zoomOut(pane);
        assertEquals(25, picture.zoomPercent());
        assertEquals("25", field.getText());
        picture.seatZoom(100);
    }

    @Test
    void aTypedZoomShowsTheClampedPercent() {
        GraphPane pane = fixture("pt-zoom-type");
        pane.showPage(12);
        GraphPicture picture = pane.picture();
        JTextField field = zoomField(pane);
        assertEquals("100", field.getText());
        zoomIn(pane);
        assertEquals(125, picture.zoomPercent());
        assertEquals("125", field.getText());
        zoomOut(pane);
        assertEquals(100, picture.zoomPercent());
        assertEquals("100", field.getText());
        zoomOut(pane);
        assertEquals(75, picture.zoomPercent());
        assertEquals("75", field.getText());
        picture.seatZoom(400);
        pane.strip().sync();
        assertEquals("400", field.getText());
        zoomIn(pane);
        assertEquals(400, picture.zoomPercent());
        assertEquals("400", field.getText());
        picture.seatZoom(25);
        pane.strip().sync();
        assertEquals("25", field.getText());
        zoomOut(pane);
        assertEquals(25, picture.zoomPercent());
        assertEquals("25", field.getText());
        field.setText("150");
        assertEquals(25, picture.zoomPercent());
        pane.strip().applyZoomText();
        assertEquals(150, picture.zoomPercent());
        assertEquals("150", field.getText());
        field.setText("10");
        pane.strip().applyZoomText();
        assertEquals(25, picture.zoomPercent());
        assertEquals("25", field.getText());
        field.setText("500");
        pane.strip().applyZoomText();
        assertEquals(400, picture.zoomPercent());
        assertEquals("400", field.getText());
        field.setText("nope");
        pane.strip().applyZoomText();
        assertEquals(400, picture.zoomPercent());
        assertEquals("400", field.getText());
        field.setText("   ");
        pane.strip().applyZoomText();
        assertEquals(400, picture.zoomPercent());
        assertEquals("400", field.getText());
        field.setText("0150");
        pane.strip().applyZoomText();
        assertEquals(150, picture.zoomPercent());
        assertEquals("150", field.getText());
    }

    @Test
    void aSelectedNodeStaysCenteredOnZoom() {
        GraphPane pane = fixture("pt-center");
        pane.showPage(12);
        GraphPicture picture = pane.picture();
        picture.getPreferredSize();
        JScrollPane scroll = pane.canvasScroll();
        scroll.setSize(480, 360);
        scroll.doLayout();
        JViewport port = scroll.getViewport();
        int spotX = picture.spotX("house");
        int spotY = picture.spotY("house");
        int layoutX = pane.forceLayout().x("house");
        int layoutY = pane.forceLayout().y("house");
        int birdX = pane.forceLayout().x("bird");
        int birdY = pane.forceLayout().y("bird");
        int laneX = pane.forceLayout().x("lane");
        int homeY = pane.forceLayout().y("home");
        Point quiet = port.getViewPosition();
        JTextField field = zoomField(pane);
        zoomIn(pane);
        assertEquals(125, picture.zoomPercent());
        assertEquals("125", field.getText());
        assertEquals(spotX, picture.spotX("house"));
        assertEquals(quiet.x, port.getViewPosition().x);
        assertEquals(quiet.y, port.getViewPosition().y);
        zoomOut(pane);
        assertEquals(100, picture.zoomPercent());
        assertEquals("100", field.getText());
        picture.dispatchEvent(new MouseEvent(
                picture,
                MouseEvent.MOUSE_RELEASED,
                System.currentTimeMillis(),
                0,
                spotX,
                spotY,
                1,
                false,
                MouseEvent.BUTTON1));
        assertEquals("house", pane.selected());
        zoomIn(pane);
        assertEquals(125, picture.zoomPercent());
        assertEquals("125", field.getText());
        assertEquals(layoutX, pane.forceLayout().x("house"));
        assertEquals(layoutY, pane.forceLayout().y("house"));
        assertEquals(birdX, pane.forceLayout().x("bird"));
        assertEquals(birdY, pane.forceLayout().y("bird"));
        assertEquals(laneX, pane.forceLayout().x("lane"));
        assertEquals(homeY, pane.forceLayout().y("home"));
        assertEquals(birdX - layoutX, picture.spotX("bird") - picture.spotX("house"));
        assertEquals(birdY - layoutY, picture.spotY("bird") - picture.spotY("house"));
        assertCentered(picture, port, "house");
        zoomIn(pane);
        assertEquals(150, picture.zoomPercent());
        assertEquals("150", field.getText());
        assertEquals(layoutX, pane.forceLayout().x("house"));
        assertEquals(birdX - layoutX, picture.spotX("bird") - picture.spotX("house"));
        assertCentered(picture, port, "house");
        zoomOut(pane);
        zoomOut(pane);
        zoomOut(pane);
        assertEquals(75, picture.zoomPercent());
        assertEquals("75", field.getText());
        assertEquals(layoutX, pane.forceLayout().x("house"));
        assertEquals(laneX, pane.forceLayout().x("lane"));
        assertEquals(birdX - layoutX, picture.spotX("bird") - picture.spotX("house"));
        assertCentered(picture, port, "house");
        pane.showPage(11);
        int layeredX = pane.layeredLayout().x("house");
        int layeredBird = pane.layeredLayout().x("bird");
        zoomIn(pane);
        assertEquals(100, picture.zoomPercent());
        assertEquals("100", field.getText());
        assertEquals("layered", pane.layoutName());
        assertEquals(layeredX, pane.layeredLayout().x("house"));
        assertEquals(layeredBird, pane.layeredLayout().x("bird"));
        assertEquals(layeredBird - layeredX, picture.spotX("bird") - picture.spotX("house"));
        assertCentered(picture, port, "house");
    }

    private static void assertCentered(GraphPicture picture, JViewport port, String id) {
        int zx = picture.spotX(id) * picture.zoomPercent() / 100;
        int zy = picture.spotY(id) * picture.zoomPercent() / 100;
        Point view = port.getViewPosition();
        assertEquals(port.getWidth() / 2, zx - view.x);
        assertEquals(port.getHeight() / 2, zy - view.y);
    }

    @Test
    void theCanvasScrollsPastTheViewport() {
        GraphPane pane = fixture("pt-field");
        pane.showPage(12);
        JScrollPane scroll = pane.canvasScroll();
        assertEquals(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, scroll.getHorizontalScrollBarPolicy());
        assertEquals(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, scroll.getVerticalScrollBarPolicy());
        GraphPicture picture = pane.picture();
        picture.getPreferredSize();
        int wide = picture.getPreferredSize().width;
        int high = picture.getPreferredSize().height;
        int seated = pane.forceLayout().x("house");
        int sx = picture.spotX("house");
        int sy = picture.spotY("house");
        scroll.setSize(180, 120);
        scroll.doLayout();
        drag(picture, sx, sy, sx + 2400, sy + 1800);
        assertTrue(picture.getPreferredSize().width > wide);
        assertTrue(picture.getPreferredSize().height > high);
        assertTrue(pane.forceLayout().x("house") != seated);
        Point view = scroll.getViewport().getViewPosition();
        assertTrue(view.x > 0);
        assertTrue(view.y > 0);
        int house = pane.forceLayout().x("house");
        int lane = pane.forceLayout().x("lane");
        drag(picture, 2, 2, 90, 50);
        assertEquals(house, pane.forceLayout().x("house"));
        assertEquals(lane, pane.forceLayout().x("lane"));
    }

    @Test
    void aDragKeepsTheNodeOnThePointer() {
        GraphPane pane = fixture("pt-drag");
        pane.showPage(12);
        GraphPicture picture = pane.picture();
        picture.getPreferredSize();
        JScrollPane scroll = pane.canvasScroll();
        scroll.setSize(900, 700);
        scroll.doLayout();
        int sx = picture.spotX("house");
        int sy = picture.spotY("house");
        int layoutX = pane.forceLayout().x("house");
        int layoutY = pane.forceLayout().y("house");
        int home = pane.forceLayout().x("home");
        Point view = scroll.getViewport().getViewPosition();
        drag(picture, sx, sy, sx + 36, sy + 18);
        assertEquals(layoutX + 36, pane.forceLayout().x("house"));
        assertEquals(layoutY + 18, pane.forceLayout().y("house"));
        assertEquals(home + 36, pane.forceLayout().x("home"));
        assertEquals(sx + 36, picture.spotX("house"));
        assertEquals(sy + 18, picture.spotY("house"));
        assertEquals(view.x, scroll.getViewport().getViewPosition().x);
        assertEquals(view.y, scroll.getViewport().getViewPosition().y);
        picture.getPreferredSize();
        assertEquals(layoutX + 36, pane.forceLayout().x("house"));
        assertEquals(sx + 36, picture.spotX("house"));
        drag(picture, sx + 36, sy + 18, sx - 4, sy + 18);
        assertEquals(layoutX - 4, pane.forceLayout().x("house"));
        assertEquals(sx - 4, picture.spotX("house"));
        picture.getPreferredSize();
        assertEquals(layoutX - 4, pane.forceLayout().x("house"));
        assertEquals(sx - 4, picture.spotX("house"));
    }

    @Test
    void aDragOffTheSideScrollsThatAxis() {
        GraphPane pane = fixture("pt-side");
        pane.showPage(12);
        GraphPicture picture = pane.picture();
        picture.getPreferredSize();
        JScrollPane scroll = pane.canvasScroll();
        scroll.setSize(220, 220);
        scroll.doLayout();
        int sx = picture.spotX("house");
        int sy = picture.spotY("house");
        int layoutX = pane.forceLayout().x("house");
        scroll.getViewport().setViewPosition(new Point(Math.max(0, sx - 100), Math.max(0, sy - 100)));
        int viewY = scroll.getViewport().getViewPosition().y;
        int viewX = scroll.getViewport().getViewPosition().x;
        drag(picture, sx, sy, sx + 640, sy);
        assertTrue(pane.forceLayout().x("house") > layoutX);
        assertEquals(pane.forceLayout().x("house"), layoutX + 640);
        Point view = scroll.getViewport().getViewPosition();
        assertTrue(view.x > viewX);
        assertEquals(viewY, view.y);
        int screen = picture.spotX("house") - view.x;
        assertTrue(screen >= 0);
        assertTrue(screen < scroll.getViewport().getWidth());
        int kept = pane.forceLayout().x("house");
        int keptSpot = picture.spotX("house");
        picture.getPreferredSize();
        assertEquals(kept, pane.forceLayout().x("house"));
        assertEquals(keptSpot, picture.spotX("house"));
    }

    @Test
    void aDropKeepsTheGrownCanvas() throws Exception {
        final GraphPane pane = fixture("pt-grown");
        pane.showPage(12);
        final GraphPicture picture = pane.picture();
        final JScrollPane scroll = pane.canvasScroll();
        final int steps = 24;
        final int stepX = 48;
        final int[] held = new int[6];
        onEdt(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("pt-grown");
                frame.setLocation(-2400, -2400);
                frame.getContentPane().add(scroll);
                frame.setSize(420, 320);
                frame.setVisible(true);
                try {
                    AppleScrollBar.install(scroll);
                    frame.validate();
                    int spotX = picture.spotX("house");
                    int spotY = picture.spotY("house");
                    JViewport port = scroll.getViewport();
                    port.setViewPosition(new Point(Math.max(0, spotX - 70), Math.max(0, spotY - 50)));
                    frame.validate();
                    int layoutX = pane.forceLayout().x("house");
                    int prefW = picture.getPreferredSize().width;
                    int viewX = port.getViewPosition().x;
                    int viewY = port.getViewPosition().y;
                    int pointerX = spotX - viewX;
                    int pointerY = spotY - viewY;
                    long when = System.currentTimeMillis();
                    MouseEvent[] burst = new MouseEvent[steps + 2];
                    burst[0] = pointerEvent(picture, MouseEvent.MOUSE_PRESSED, when,
                            pointerX + viewX, pointerY + viewY);
                    for (int i = 1; i <= steps; i++) {
                        pointerX = pointerX + stepX;
                        pointerY = pointerY + 32;
                        burst[i] = pointerEvent(picture, MouseEvent.MOUSE_DRAGGED, when,
                                pointerX + viewX, pointerY + viewY);
                    }
                    burst[steps + 1] = pointerEvent(picture, MouseEvent.MOUSE_RELEASED, when,
                            pointerX + viewX, pointerY + viewY);
                    for (int i = 0; i < burst.length; i++) {
                        picture.dispatchEvent(burst[i]);
                    }
                    frame.validate();
                    scroll.validate();
                    int screen = picture.spotX("house") - port.getViewPosition().x;
                    held[0] = layoutX;
                    held[1] = pane.forceLayout().x("house");
                    held[2] = prefW;
                    held[3] = picture.getPreferredSize().width;
                    held[4] = picture.getWidth();
                    held[5] = screen;
                } finally {
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        assertEquals(held[0] + steps * stepX, held[1],
                "layout " + held[0] + " -> " + held[1] + " pref " + held[2] + " -> " + held[3]);
        assertTrue(held[3] > held[2], "pref " + held[2] + " -> " + held[3]);
        assertTrue(held[4] >= held[3], "wide " + held[4] + " pref " + held[3]);
        assertTrue(held[5] >= 0 && held[5] < 420, "screen " + held[5]);
    }

    private static MouseEvent pointerEvent(Component source, int id, long when, int x, int y) {
        return new MouseEvent(source, id, when, 0, x, y, 1, false, MouseEvent.BUTTON1);
    }

    @Test
    void theRenderingSpinnerKeepsOneLine() throws Exception {
        final GraphPane pane = fixture("pt-east");
        pane.showPage(12);
        assertFalse(pane.renderingOn());
        JPanel bar = (JPanel) pane.getComponent(0);
        BorderLayout layout = (BorderLayout) bar.getLayout();
        assertNull(layout.getLayoutComponent(BorderLayout.WEST));
        Component east = layout.getLayoutComponent(BorderLayout.EAST);
        assertTrue(east != null);
        assertFalse(east instanceof ReasonSpinner);
        final ReasonSpinner spin = (ReasonSpinner) pane.renderingCluster();
        assertNull(spin.getParent());
        onEdt(new Runnable() {
            @Override
            public void run() {
                spin.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                pane.rendering(true);
                assertTrue(pane.renderingOn());
                String text = spin.wordsLabel().getText();
                assertTrue(text.startsWith("rendering "));
                assertFalse(text.indexOf('<') >= 0);
                assertFalse(text.indexOf('\n') >= 0);
                FontMetrics metrics = spin.wordsLabel().getFontMetrics(spin.wordsLabel().getFont());
                assertEquals(metrics.getHeight(), spin.wordsLabel().getPreferredSize().height);
                assertTrue(spin.wordsLabel().getPreferredSize().width >= metrics.stringWidth(text));
                assertTrue(spin.getPreferredSize().height <= metrics.getHeight() + 2);
                assertTrue(spin.getPreferredSize().width >= metrics.stringWidth(text));
                assertTrue(spin.getLayout() instanceof BorderLayout);
                assertFalse(spin.getLayout() instanceof FlowLayout);
                Dimension preferred = spin.getPreferredSize();
                spin.setSize(preferred);
                spin.doLayout();
                assertEquals(spin.glyphLabel().getY(), spin.wordsLabel().getY());
                assertTrue(spin.wordsLabel().getX() > spin.glyphLabel().getX());
                int line = preferred.height;
                spin.setSize(640, line);
                spin.doLayout();
                assertEquals(line, spin.getHeight());
                assertEquals(line, spin.getPreferredSize().height);
                assertEquals(spin.glyphLabel().getY(), spin.wordsLabel().getY());
                assertTrue(spin.wordsLabel().getWidth() > spin.wordsLabel().getPreferredSize().width);
                assertEquals(line, spin.getMaximumSize().height);
                assertTrue(spin.getMaximumSize().width > spin.getPreferredSize().width);
                ReasonSpinner thinking = new ReasonSpinner("thinking");
                thinking.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                thinking.startSpin(new ReasonSpinner.Start() {
                    @Override
                    public long startedAtMs() {
                        return 0L;
                    }
                });
                String think = thinking.wordsLabel().getText();
                assertEquals("thinking 0:00", think);
                FontMetrics thinkMetrics = thinking.wordsLabel().getFontMetrics(thinking.wordsLabel().getFont());
                assertEquals(thinkMetrics.getHeight(), thinking.getPreferredSize().height);
                assertTrue(thinking.getPreferredSize().width >= thinkMetrics.stringWidth(think));
                assertTrue(thinking.getLayout() instanceof BorderLayout);
                assertFalse(thinking.getLayout() instanceof FlowLayout);
                Dimension thinkPreferred = thinking.getPreferredSize();
                thinking.setSize(640, thinkPreferred.height);
                thinking.doLayout();
                assertEquals(thinkPreferred.height, thinking.getHeight());
                assertEquals(thinking.glyphLabel().getY(), thinking.wordsLabel().getY());
                assertTrue(thinking.wordsLabel().getWidth() > thinking.wordsLabel().getPreferredSize().width);
                thinking.stopSpin();
            }
        });
        onEdt(new Runnable() {
            @Override
            public void run() {
                pane.rendering(false);
                assertFalse(pane.renderingOn());
            }
        });
    }

    @Test
    void eachGraphKeepsItsOwnRenderingSpinner() throws Exception {
        final GraphPane one = fixture("pt-own-a");
        final GraphPane two = fixture("pt-own-b");
        assertNotSame(one.renderingCluster(), two.renderingCluster());
        onEdt(new Runnable() {
            @Override
            public void run() {
                one.rendering(true);
                assertTrue(one.renderingOn());
                assertFalse(two.renderingOn());
            }
        });
        onEdt(new Runnable() {
            @Override
            public void run() {
                one.rendering(false);
                assertFalse(one.renderingOn());
                assertFalse(two.renderingOn());
            }
        });
    }

    private static void drag(GraphPicture picture, int x0, int y0, int x1, int y1) {
        long when = System.currentTimeMillis();
        picture.dispatchEvent(new MouseEvent(
                picture, MouseEvent.MOUSE_PRESSED, when, 0, x0, y0, 1, false, MouseEvent.BUTTON1));
        picture.dispatchEvent(new MouseEvent(
                picture, MouseEvent.MOUSE_DRAGGED, when, 0, x1, y1, 1, false, MouseEvent.BUTTON1));
        picture.dispatchEvent(new MouseEvent(
                picture, MouseEvent.MOUSE_RELEASED, when, 0, x1, y1, 1, false, MouseEvent.BUTTON1));
    }

    private static int centerPixel(GraphPicture picture, int x, int y) {
        Dimension size = picture.getPreferredSize();
        picture.setSize(size);
        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        try {
            picture.paint(graphics);
        } finally {
            graphics.dispose();
        }
        return image.getRGB(x, y);
    }

    @Test
    void g1EachLoginKeepsItsOwnGraph() {
        GraphPane ada = GraphDesk.pane("g1-ada", "canvas-ada");
        GraphPane grace = GraphDesk.pane("g1-grace", "canvas-grace");
        assertNotSame(ada, grace);
        assertNotSame(ada.model(), grace.model());
        assertEquals("canvas-ada", ada.model().conversation());
        assertEquals("canvas-grace", grace.model().conversation());
    }

    @Test
    void g2SameLoginReturnsTheSamePane() {
        GraphPane first = GraphDesk.pane("g2-user", "canvas-one");
        GraphPane second = GraphDesk.pane("g2-user", "canvas-two");
        assertSame(first, second);
        assertSame(first.model(), second.model());
        assertEquals("canvas-one", first.model().conversation());
    }

    @Test
    void g3EmptyUsernameReturnsNothing() {
        assertNull(GraphDesk.pane(null, "canvas"));
        assertNull(GraphDesk.pane("", "canvas"));
        assertNull(GraphDesk.pane(null, "canvas"));
    }

    @Test
    void g4GraphPackageDoesNotImportTheShell() throws Exception {
        File folder = new File("src/main/java/org/aetheris/lcarsgraph");
        File[] files = folder.listFiles();
        assertTrue(files != null && files.length > 0);
        for (File file : files) {
            if (!file.getName().endsWith(".java")) {
                continue;
            }
            String source = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            String[] lines = source.split("\n");
            for (String line : lines) {
                String trimmed = line.trim();
                if (!trimmed.startsWith("import ")) {
                    continue;
                }
                assertFalse(trimmed.contains("AetherisNotebookPanel"), file.getName());
                assertFalse(trimmed.contains("org.casserole.app.ui.ssh"), file.getName());
                assertFalse(trimmed.contains("org.aetheris.swing"), file.getName());
                assertFalse(trimmed.contains("org.aetheris.vault"), file.getName());
                assertFalse(trimmed.contains("CellRecord"), file.getName());
                assertFalse(trimmed.contains("CellCard"), file.getName());
                assertFalse(trimmed.contains("CanvasRecord"), file.getName());
            }
        }
    }

    @Test
    void g5WindowStaysClosed() throws Exception {
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            assertFalse(window.isVisible());
        }
        File folder = new File("src/main/java/org/aetheris/lcarsgraph");
        File[] files = folder.listFiles();
        for (File file : files) {
            if (!file.getName().endsWith(".java")) {
                continue;
            }
            String source = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            assertFalse(source.contains("JFrame"), file.getName());
            assertFalse(source.contains("JDialog"), file.getName());
            assertFalse(source.contains("JColorChooser"), file.getName());
        }
    }

    private static void assertCircle(GraphPicture picture) {
        assertEquals("circle", picture.shape("home"));
        assertEquals(32, picture.diameter("home"));
        assertEquals(new Color(0xE4DED6), picture.fill("home"));
    }

    @Test
    void typeSizeReachesTheGraph() {
        GraphPane pane = fixture("pt-type");
        pane.typeSize(28);
        assertEquals(28, pane.typePoints());
        assertEquals(28, pane.strip().buttonPoints("MODEL"));
        assertEquals(28, pane.strip().buttonPoints("RELOAD"));
        assertEquals(28, zoomField(pane).getFont().getSize());
        assertEquals(28, ((JLabel) named(pane.strip(), "graph-zoom-label")).getFont().getSize());
        assertEquals(28, ((JButton) named(pane.strip(), "graph-zoom-minus")).getFont().getSize());
        assertEquals(28, ((JButton) named(pane.strip(), "graph-zoom-plus")).getFont().getSize());
        assertEquals(28, pane.picture().typePoints());
        assertEquals(28, pane.inspector().typePoints());
        pane.typeSize(4);
        assertEquals(8, pane.typePoints());
        assertEquals(8, pane.strip().buttonPoints("WHOLE"));
        assertEquals(8, zoomField(pane).getFont().getSize());
        assertEquals(8, pane.picture().typePoints());
        assertEquals(8, pane.inspector().typePoints());
    }

    @Test
    void reloadSitsOnTheBar() {
        GraphPane pane = fixture("pt-reload");
        GraphStrip strip = pane.strip();
        assertTrue(strip.visible("RELOAD"));
        assertTrue(strip.tip("RELOAD").startsWith("RELOAD"));
        assertTrue(strip.visible("STOP"));
        assertTrue(strip.tip("STOP").startsWith("STOP"));
        strip.press("STOP");
        final int[] hits = new int[1];
        pane.hearReload(new Runnable() {
            @Override
            public void run() {
                hits[0]++;
            }
        });
        strip.press("RELOAD");
        assertEquals(1, hits[0]);
        pane.showPage(1);
        assertTrue(strip.visible("RELOAD"));
        assertTrue(strip.visible("MODEL"));
    }

    @Test
    void renderingShowsOnTheBar() throws Exception {
        final GraphPane pane = fixture("pt-render");
        try {
            onEdt(new Runnable() {
                @Override
                public void run() {
                    pane.rendering(true);
                    assertTrue(pane.renderingOn());
                    assertTrue(pane.renderingWide() >= 80);
                }
            });
        } finally {
            onEdt(new Runnable() {
                @Override
                public void run() {
                    pane.rendering(false);
                    assertFalse(pane.renderingOn());
                }
            });
        }
    }

    @Test
    void aFootSitsOnTheSouth() throws Exception {
        final GraphPane pane = fixture("pt-foot");
        final JPanel band = new JPanel();
        onEdt(new Runnable() {
            @Override
            public void run() {
                pane.foot(band);
                assertSame(band, pane.footBand());
                assertSame(pane, band.getParent());
                assertSame(band, ((BorderLayout) pane.getLayout()).getLayoutComponent(BorderLayout.SOUTH));
                pane.foot(band);
                assertSame(pane, band.getParent());
                pane.foot(null);
                assertNull(pane.footBand());
                assertNull(band.getParent());
            }
        });
    }

    @Test
    void theGraphBarFoldsInTwoRows() {
        GraphPane pane = fixture("pt-bars");
        assertEquals(12, pane.page());
        assertEquals("WHOLE", pane.filter());
        JPanel north = (JPanel) pane.getComponent(0);
        BorderLayout northLayout = (BorderLayout) north.getLayout();
        GraphStrip strip = pane.strip();
        assertSame(strip, northLayout.getLayoutComponent(BorderLayout.CENTER));
        assertTrue(strip.getLayout() instanceof BorderLayout);
        Component east = northLayout.getLayoutComponent(BorderLayout.EAST);
        assertTrue(east instanceof JPanel);
        JPanel markSeat = (JPanel) east;
        assertFalse(markSeat.isOpaque());
        assertTrue(markSeat.getLayout() instanceof BorderLayout);
        Component mark = ((BorderLayout) markSeat.getLayout()).getLayoutComponent(BorderLayout.NORTH);
        assertTrue(mark != null);
        markSeat.setSize(200, 90);
        markSeat.doLayout();
        assertEquals(0, mark.getY());
        assertTrue(mark.getHeight() > 0);
        assertTrue(mark.getHeight() < markSeat.getHeight());
        JPanel bars = (JPanel) named(strip, "graph-bars");
        JPanel bb1 = (JPanel) named(strip, "graph-bb1");
        JPanel bb2 = (JPanel) named(strip, "graph-bb2");
        JButton parent = (JButton) named(strip, "graph-bb1-fold");
        JButton tools = (JButton) named(strip, "graph-bb2-fold");
        JPanel line = (JPanel) named(strip, "graph-bb1-fold-line");
        assertTrue(bars != null);
        assertTrue(bb1 != null);
        assertTrue(bb2 != null);
        assertTrue(parent != null);
        assertTrue(tools != null);
        assertTrue(line != null);
        assertTrue(bars.isVisible());
        assertFalse(bb2.isVisible());
        assertFalse(line.isVisible());
        assertEquals("v", parent.getText());
        assertEquals("v", tools.getText());
        assertSame(bb1, parent.getParent());
        assertSame(bb1, tools.getParent());
        FlowLayout lead = (FlowLayout) bb1.getLayout();
        FlowLayout rest = (FlowLayout) bb2.getLayout();
        assertEquals(FlowLayout.LEFT, lead.getAlignment());
        assertEquals(4, lead.getHgap());
        assertEquals(4, lead.getVgap());
        assertEquals(FlowLayout.LEFT, rest.getAlignment());
        assertEquals(4, rest.getHgap());
        assertEquals(4, rest.getVgap());
        String[] first = {
            "WHOLE", "REICHENBACH", "MODEL", "RELOAD", "STOP", "PALETTE",
            "EVERYTHING", "LAYERED", "FORCE", "RADIAL"
        };
        String[] second = {
            "RESET LAYOUT",
            "PIN", "SPLIT", "MERGE", "REJECT", "PROMOTE"
        };
        for (String name : first) {
            assertTrue(indexOfText(bb1, name) >= 0, name);
            assertEquals(-1, indexOfText(bb2, name), name);
        }
        for (String name : second) {
            assertTrue(indexOfText(bb2, name) >= 0, name);
            assertEquals(-1, indexOfText(bb1, name), name);
        }
        int whole = indexOfText(bb1, "WHOLE");
        int radial = indexOfText(bb1, "RADIAL");
        int toolsAt = indexOfName(bb1, "graph-bb2-fold");
        assertTrue(whole < radial);
        assertTrue(radial < toolsAt);
        assertTrue(indexOfText(bb1, "RELOAD") < indexOfText(bb1, "STOP"));
        assertTrue(indexOfText(bb1, "STOP") < indexOfText(bb1, "PALETTE"));
        int resetAt = indexOfText(bb2, "RESET LAYOUT");
        int zoomAt = indexOfName(bb2, "graph-zoom");
        int bridgeAt = indexOfTip(bb2, "Divider. The mark separates the layout icons from the act buttons.");
        assertTrue(resetAt < zoomAt);
        assertTrue(zoomAt < bridgeAt);
        assertTrue(bridgeAt < indexOfText(bb2, "PIN"));
        assertEquals(-1, indexOfText(bb2, "ZOOM IN"));
        assertEquals(-1, indexOfText(bb2, "ZOOM OUT"));
        JPanel zoom = (JPanel) named(bb2, "graph-zoom");
        FlowLayout zoomLead = (FlowLayout) zoom.getLayout();
        assertEquals(FlowLayout.LEFT, zoomLead.getAlignment());
        assertEquals(4, zoomLead.getHgap());
        assertEquals(0, zoomLead.getVgap());
        assertEquals("Zoom", ((JLabel) zoom.getComponent(0)).getText());
        assertEquals("-", ((JButton) zoom.getComponent(1)).getText());
        assertEquals("100", ((JTextField) zoom.getComponent(2)).getText());
        assertEquals("+", ((JButton) zoom.getComponent(3)).getText());
        assertTrue(tools.getToolTipText().contains("Zoom"));
        assertFalse(tools.getToolTipText().contains("ZOOM IN"));
        assertFalse(tools.getToolTipText().contains("ZOOM OUT"));
        assertEquals(-1, indexOfTip(bb1, "Divider. The mark separates the layout icons from the act buttons."));
        assertTrue(strip.visible("PIN"));
        assertTrue(strip.visible("RADIAL"));
        assertFalse(strip.bridgeVisible());
        tools.doClick();
        assertTrue(bb2.isVisible());
        assertEquals("^", tools.getText());
        assertEquals(12, pane.page());
        assertEquals("WHOLE", pane.filter());
        strip.setSize(1400, 160);
        layoutTree(strip);
        assertTrue(bb2.getY() >= bb1.getY() + bb1.getHeight() - 1);
        parent.doClick();
        assertFalse(bars.isVisible());
        assertTrue(line.isVisible());
        assertEquals("^", parent.getText());
        assertSame(line, parent.getParent());
        assertEquals(12, pane.page());
        assertEquals("WHOLE", pane.filter());
        assertEquals(100, pane.picture().zoomPercent());
        parent.doClick();
        assertTrue(bars.isVisible());
        assertFalse(line.isVisible());
        assertEquals("v", parent.getText());
        assertSame(bb1, parent.getParent());
        assertTrue(bb2.isVisible());
        assertEquals("^", tools.getText());
        tools.doClick();
        assertFalse(bb2.isVisible());
        assertEquals("v", tools.getText());
        pane.typeSize(16);
        assertEquals(16, parent.getFont().getSize());
        assertEquals(16, tools.getFont().getSize());
        assertEquals(16, strip.buttonPoints("WHOLE"));
        assertEquals(16, ((JTextField) zoom.getComponent(2)).getFont().getSize());
        assertEquals(16, ((JLabel) zoom.getComponent(0)).getFont().getSize());
        assertEquals(16, ((JButton) zoom.getComponent(1)).getFont().getSize());
        assertEquals(16, ((JButton) zoom.getComponent(3)).getFont().getSize());
    }

    @Test
    void aDoubleClickFocusesTheIntentAndItsChildren() throws Exception {
        final GraphPane pane = fixture("pt-focus");
        pane.showPage(12);
        pane.model().linkWord("home", "house", "lane");
        final GraphPicture picture = pane.picture();
        final int seat = pane.layoutX("house");
        final int[] center = new int[2];
        onEdt(new Runnable() {
            @Override
            public void run() {
                int x = picture.spotX("house");
                int y = picture.spotY("house");
                long when = System.currentTimeMillis();
                picture.dispatchEvent(new MouseEvent(
                        picture, MouseEvent.MOUSE_PRESSED, when, 0, x, y, 2, false, MouseEvent.BUTTON1));
                picture.dispatchEvent(new MouseEvent(
                        picture, MouseEvent.MOUSE_RELEASED, when, 0, x, y, 2, false, MouseEvent.BUTTON1));
                center[0] = pane.page();
                center[1] = picture.zoomPercent();
            }
        });
        assertEquals("house", pane.focusId());
        assertEquals(12, center[0]);
        assertEquals(200, center[1]);
        assertEquals("200", zoomField(pane).getText());
        assertTrue(pane.shown("house"));
        assertTrue(pane.shown("home"));
        assertTrue(pane.shown("bird"));
        assertTrue(pane.shown("lane"));
        assertEquals("chip", picture.shape("home"));
        assertEquals("intent", picture.shape("house"));
        assertTrue(pane.strip().visible("FORCE"));
        assertTrue(pane.inspector().names().contains("lane"));
        assertEquals(seat, pane.layoutX("house"));
        pane.focusIntent("bird");
        assertEquals("bird", pane.focusId());
        assertTrue(pane.shown("bird"));
        assertTrue(pane.shown("house"));
        assertFalse(pane.shown("home"));
        assertFalse(pane.shown("lane"));
        pane.strip().press("FORCE");
        assertNull(pane.focusId());
        assertTrue(pane.shown("lane"));
        assertTrue(pane.shown("home"));
        assertEquals(12, pane.page());
        assertEquals("force", pane.layoutName());
        assertEquals(seat, pane.layoutX("house"));
        assertEquals(100, picture.zoomPercent());
        assertEquals("100", zoomField(pane).getText());
        pane.showPage(11);
        final int layered = pane.layoutX("house");
        pane.focusIntent("house");
        pane.strip().press("FORCE");
        assertNull(pane.focusId());
        assertEquals(11, pane.page());
        assertEquals("layered", pane.layoutName());
        assertEquals(layered, pane.layoutX("house"));
        assertEquals(100, picture.zoomPercent());
    }

    @Test
    void anIntentWearsAHollowRing() {
        GraphPane pane = GraphDesk.pane("ft-ring", "conversation");
        pane.model().addIntent("mark");
        pane.showPage(1);
        GraphPicture picture = pane.picture();
        assertEquals("intent", picture.shape("mark"));
        assertEquals(48, picture.diameter("mark"));
        BufferedImage image = paintOf(picture);
        int cx = picture.spotX("mark");
        int cy = picture.spotY("mark");
        int radius = picture.diameter("mark") / 2;
        int hole = image.getRGB(cx, cy);
        int band = image.getRGB(cx - (radius - 6), cy);
        int outside = image.getRGB(cx - radius - 8, cy);
        assertTrue(near(hole, 0x121212) || near(hole, 0x0C0C0C), hex(hole));
        assertTrue(dist(band, 0x6B93FF) < dist(band, 0x121212), hex(band));
        assertTrue(dist(outside, 0x121212) < dist(outside, 0x6B93FF), hex(outside));
        pane.model().takeWords("mark", "home");
        pane.showPage(12);
        pane.showPage(1);
        assertEquals("chip", picture.shape("home"));
        BufferedImage chips = paintOf(picture);
        int chip = chips.getRGB(picture.spotX("home"), picture.spotY("home"));
        assertTrue(dist(chip, 0x6B93FF) < dist(chip, 0x121212), hex(chip));
    }

    @Test
    void aMeatWordWearsASolidStone() {
        GraphPane pane = GraphDesk.pane("ft-stone", "conversation");
        pane.model().addIntent("mark");
        pane.model().takeWords("mark", "home kiln");
        pane.showPage(1);
        GraphPicture picture = pane.picture();
        assertEquals("chip", picture.shape("home"));
        assertEquals("chip", picture.shape("kiln"));
        assertEquals(new Color(0x6B93FF), picture.fill("home"));
        assertEquals(picture.fill("home"), picture.stroke("home"));
        assertEquals(56, picture.width("home"));
        assertEquals(32, picture.height("home"));
        BufferedImage chips = paintOf(picture);
        int cx = picture.spotX("home");
        int cy = picture.spotY("home");
        int body = chips.getRGB(cx, cy);
        int lit = chips.getRGB(cx - 6, cy - 3);
        int shade = chips.getRGB(cx + 6, cy + 3);
        assertTrue(dist(body, 0x6B93FF) < dist(body, 0x121212), hex(body));
        assertTrue(lum(lit) > lum(shade), hex(lit) + " " + hex(shade));
        pane.showPage(12);
        assertEquals("circle", picture.shape("home"));
        assertEquals(32, picture.diameter("home"));
        BufferedImage beads = paintOf(picture);
        int bx = picture.spotX("home");
        int by = picture.spotY("home");
        int core = beads.getRGB(bx, by);
        int up = beads.getRGB(bx - 3, by - 3);
        int down = beads.getRGB(bx + 3, by + 3);
        assertTrue(dist(core, 0x6B93FF) < dist(core, 0x121212), hex(core));
        assertTrue(lum(up) > lum(down), hex(up) + " " + hex(down));
        assertEquals("intent", picture.shape("mark"));
        int hole = beads.getRGB(picture.spotX("mark"), picture.spotY("mark"));
        assertTrue(near(hole, 0x121212) || near(hole, 0x0C0C0C), hex(hole));
    }

    @Test
    void aJoinedMeatWordWearsTheJoinColor() {
        GraphPane pane = GraphDesk.pane("ft-join", "conversation");
        pane.model().addIntent("mark");
        pane.model().addIntent("kiln");
        pane.model().takeWords("mark", "home");
        pane.model().takeWords("kiln", "clay");
        pane.model().takeWords("mark", "shared");
        pane.model().linkWord("shared", "mark", "kiln");
        pane.showPage(1);
        GraphPicture picture = pane.picture();
        assertEquals(new Color(0x6B93FF), picture.fill("home"));
        assertEquals(new Color(0xE879F9), picture.fill("clay"));
        assertEquals(new Color(0xFFC14D), picture.fill("shared"));
        assertEquals(picture.fill("shared"), picture.stroke("shared"));
        assertEquals(56, picture.width("shared"));
        assertEquals(32, picture.height("shared"));
        pane.showPage(12);
        pane.showPage(1);
        BufferedImage chips = paintOf(picture);
        int shared = chips.getRGB(picture.spotX("shared"), picture.spotY("shared"));
        int home = chips.getRGB(picture.spotX("home"), picture.spotY("home"));
        assertTrue(dist(shared, 0xFFC14D) < dist(shared, 0x6B93FF), hex(shared));
        assertTrue(dist(shared, 0xFFC14D) < dist(shared, 0x121212), hex(shared));
        assertTrue(dist(home, 0x6B93FF) < dist(home, 0xFFC14D), hex(home));
    }

    @Test
    void aFocusedDoorSitsNearTheIntent() {
        GraphPane pane = fixture("ft-cluster");
        pane.showPage(12);
        pane.model().linkWord("home", "house", "lane");
        pane.nudge("bird", 900, 0);
        pane.nudge("lane", 0, 900);
        int houseX = pane.layoutX("house");
        int houseY = pane.layoutY("house");
        int birdX = pane.layoutX("bird");
        int birdY = pane.layoutY("bird");
        int laneX = pane.layoutX("lane");
        int laneY = pane.layoutY("lane");
        int homeX = pane.layoutX("home");
        int homeY = pane.layoutY("home");
        int layoutBird = span(birdX - houseX, birdY - houseY);
        int layoutLane = span(laneX - houseX, laneY - houseY);
        assertTrue(layoutBird > 800 * 800);
        assertTrue(layoutLane > 800 * 800);
        pane.focusIntent("house");
        GraphPicture picture = pane.picture();
        assertEquals(birdX, pane.layoutX("bird"));
        assertEquals(laneX, pane.layoutX("lane"));
        assertEquals(houseX, pane.layoutX("house"));
        int hx = picture.spotX("house");
        int hy = picture.spotY("house");
        int bx = picture.spotX("bird");
        int by = picture.spotY("bird");
        int lx = picture.spotX("lane");
        int ly = picture.spotY("lane");
        int nearBird = span(bx - hx, by - hy);
        int nearLane = span(lx - hx, ly - hy);
        assertTrue(nearBird < 400 * 400);
        assertTrue(nearLane < 400 * 400);
        assertTrue(nearBird > 70 * 70);
        assertTrue(nearLane > 70 * 70);
        assertTrue(span(bx - lx, by - ly) > 70 * 70);
        assertEquals(homeX - houseX, picture.spotX("home") - hx);
        assertEquals(homeY - houseY, picture.spotY("home") - hy);
        BufferedImage image = paintOf(picture);
        int drawnX = picture.spotX("bird");
        int drawnY = picture.spotY("bird");
        int zx = drawnX * picture.zoomPercent() / 100;
        int zy = drawnY * picture.zoomPercent() / 100;
        int bandX = zx - (18 * picture.zoomPercent() / 100);
        assertTrue(bandX >= 0 && zy >= 0 && bandX < image.getWidth() && zy < image.getHeight(),
                bandX + "," + zy + " in " + image.getWidth() + "x" + image.getHeight());
        int band = image.getRGB(bandX, zy);
        assertTrue(dist(band, 0x121212) > 800, hex(band));
        int laneSpot = picture.spotX("lane");
        picture.shiftClose("bird", 48, 0);
        assertEquals(birdX, pane.layoutX("bird"));
        assertEquals(laneX, pane.layoutX("lane"));
        assertEquals(houseX, pane.layoutX("house"));
        assertEquals(homeX, pane.layoutX("home"));
        assertEquals(drawnX + 48, picture.spotX("bird"));
        assertEquals(laneSpot, picture.spotX("lane"));
        pane.focusIntent("lane");
        assertEquals(drawnX + 48, picture.spotX("bird"));
        assertEquals(birdX, pane.layoutX("bird"));
        assertEquals(laneX, pane.layoutX("lane"));
        pane.strip().press("FORCE");
        assertNull(pane.focusId());
        assertEquals(birdX, pane.layoutX("bird"));
        assertEquals(laneX, pane.layoutX("lane"));
        assertEquals(pane.layoutX("bird") - pane.layoutX("house"), picture.spotX("bird") - picture.spotX("house"));
        assertEquals(pane.layoutY("lane") - pane.layoutY("house"), picture.spotY("lane") - picture.spotY("house"));
    }

    private static int span(int dx, int dy) {
        return dx * dx + dy * dy;
    }

    private static BufferedImage paintOf(GraphPicture picture) {
        Dimension size = picture.getPreferredSize();
        picture.setSize(size);
        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        try {
            picture.paint(graphics);
        } finally {
            graphics.dispose();
        }
        return image;
    }

    private static boolean near(int rgb, int ink) {
        return dist(rgb, ink) < 48;
    }

    private static int lum(int rgb) {
        int red = (rgb >> 16) & 255;
        int green = (rgb >> 8) & 255;
        int blue = rgb & 255;
        return red * 3 + green * 6 + blue;
    }

    private static int dist(int rgb, int ink) {
        int dr = ((rgb >> 16) & 255) - ((ink >> 16) & 255);
        int dg = ((rgb >> 8) & 255) - ((ink >> 8) & 255);
        int db = (rgb & 255) - (ink & 255);
        return dr * dr + dg * dg + db * db;
    }

    private static String hex(int rgb) {
        return Integer.toHexString(rgb & 0xFFFFFF);
    }

    private static JTextField zoomField(GraphPane pane) {
        return (JTextField) named(pane.strip(), "graph-zoom-field");
    }

    private static void zoomIn(GraphPane pane) {
        ((JButton) named(pane.strip(), "graph-zoom-plus")).doClick();
    }

    private static void zoomOut(GraphPane pane) {
        ((JButton) named(pane.strip(), "graph-zoom-minus")).doClick();
    }

    private static Component named(Component root, String name) {
        if (name.equals(root.getName())) {
            return root;
        }
        if (root instanceof Container) {
            Container box = (Container) root;
            int count = box.getComponentCount();
            for (int i = 0; i < count; i++) {
                Component found = named(box.getComponent(i), name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private static int indexOfText(Container row, String text) {
        int count = row.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component child = row.getComponent(i);
            if (child instanceof JButton && text.equals(((JButton) child).getText())) {
                return i;
            }
        }
        return -1;
    }

    private static int indexOfName(Container row, String name) {
        int count = row.getComponentCount();
        for (int i = 0; i < count; i++) {
            if (name.equals(row.getComponent(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    private static int indexOfTip(Container row, String tip) {
        int count = row.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component child = row.getComponent(i);
            if (child instanceof JComponent && tip.equals(((JComponent) child).getToolTipText())) {
                return i;
            }
        }
        return -1;
    }

    private static void layoutTree(Component component) {
        if (!(component instanceof Container)) {
            return;
        }
        Container box = (Container) component;
        box.doLayout();
        int count = box.getComponentCount();
        for (int i = 0; i < count; i++) {
            layoutTree(box.getComponent(i));
        }
    }

    private static void onEdt(Runnable work) throws Exception {
        if (EventQueue.isDispatchThread()) {
            work.run();
            return;
        }
        SwingUtilities.invokeAndWait(work);
    }

    @Test
    void aLayoutKeepsALegibleNode() {
        GraphPane pane = fixture("ft-floor");
        pane.showPage(12);
        pane.select("house");
        pane.nudge("bird", 900, 0);
        GraphPicture picture = pane.picture();
        assertEquals("intent", picture.shape("bird"));
        assertEquals("intent", picture.shape("lane"));
        assertEquals("circle", picture.shape("home"));
        assertTrue(picture.diameter("house") > picture.diameter("lane"));
        assertTrue(picture.diameter("bird") > picture.intentDot());
        int houseX = pane.layoutX("house");
        int houseY = pane.layoutY("house");
        pane.showPage(11);
        assertEquals("intent", picture.shape("bird"));
        assertEquals("circle", picture.shape("home"));
        pane.showPage(13);
        assertEquals("intent", picture.shape("lane"));
        assertEquals("circle", picture.shape("home"));
        houseX = pane.layoutX("house");
        houseY = pane.layoutY("house");
        picture.seatZoom(80);
        assertEquals(houseX, pane.layoutX("house"));
        assertEquals(houseY, pane.layoutY("house"));
        assertTrue(picture.width("house") > picture.width("lane"));
        assertTrue(picture.width("lane") * picture.zoomPercent() / 100 >= 32);
        picture.seatZoom(25);
        assertEquals(houseX, pane.layoutX("house"));
        assertEquals(houseY, pane.layoutY("house"));
        assertTrue(picture.width("bird") * picture.zoomPercent() / 100 >= 32);
        assertTrue(picture.width("lane") * picture.zoomPercent() / 100 >= 32);
        assertTrue(picture.width("home") * picture.zoomPercent() / 100 >= 32);
        assertTrue(picture.height("home") * picture.zoomPercent() / 100 >= 32);
        picture.seatZoom(100);
        pane.showPage(1);
        picture.seatZoom(25);
        assertEquals(56, picture.width("home") * picture.zoomPercent() / 100);
        assertEquals(32, picture.height("home") * picture.zoomPercent() / 100);
        picture.seatZoom(100);
        assertEquals(56, picture.width("home"));
        assertEquals(32, picture.height("home"));
        assertEquals("intent", picture.shape("house"));
    }

    private static GraphPane fixture(String username) {
        GraphPane pane = GraphDesk.pane(username, "conversation");
        GraphModel model = pane.model();
        model.addIntent("house");
        model.addIntent("bird");
        model.addIntent("lane");
        model.relate("supports", "house", "bird");
        model.relate("supports", "bird", "house");
        model.takeWords("house", "home");
        model.pin("house");
        model.setColor("house", "#E4DED6");
        model.setQuiet("lane", true);
        return pane;
    }
}
