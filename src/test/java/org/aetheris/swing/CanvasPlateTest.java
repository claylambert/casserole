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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JViewport;

import org.junit.jupiter.api.Test;

/**
 * The class checks inspector scrolling, caret rest, and painted-border settings on {@link AetherisNotebookPanel}.
 * The test does not open a window.
 *
 * @author clayl (2026-11-06)
 */
class CanvasPlateTest {

    /**
     * The method arranges the yaml text {@code version: 1} and a newline.
     * The method calls {@link AetherisNotebookPanel#parseSettingsYaml}.
     * {@code blocked} is false.
     * {@code borders} is false.
     * {@code tooltips} is true.
     * {@code debug} is false.
     *
     * @return The method returns nothing.
     */
    @Test
    void aMissingBorderKeyStaysOff() {
        AetherisNotebookPanel.SettingsSeat seat = AetherisNotebookPanel.parseSettingsYaml("version: 1\n");
        assertFalse(seat.blocked);
        assertFalse(seat.borders);
        assertTrue(seat.tooltips);
        assertFalse(seat.debug);
    }

    /**
     * The method arranges yaml that sets {@code painted_borders} true, {@code debug_mood} false, {@code tooltips} true, and an extra line {@code keep: 1}.
     * The method calls {@link AetherisNotebookPanel#parseSettingsYaml} and {@link AetherisNotebookPanel#renderSettingsYaml}.
     * The first parse leaves {@code blocked} false and {@code borders} true.
     * The extra text is {@code keep: 1} plus a newline.
     * The rendered text matches the arranged yaml.
     * A second parse of that rendered text keeps borders, leaves debug false, keeps tooltips, and keeps the extra line.
     *
     * @return The method returns nothing.
     */
    @Test
    void paintedBordersRoundTrip() {
        AetherisNotebookPanel.SettingsSeat seat = AetherisNotebookPanel.parseSettingsYaml(
                "version: 1\npainted_borders: true\ndebug_mood: false\ntooltips: true\nkeep: 1\n");
        assertFalse(seat.blocked);
        assertTrue(seat.borders);
        assertEquals("keep: 1\n", seat.extras);
        String written = AetherisNotebookPanel.renderSettingsYaml(seat);
        assertEquals(
                "version: 1\npainted_borders: true\ndebug_mood: false\ntooltips: true\nkeep: 1\n",
                written);
        AetherisNotebookPanel.SettingsSeat again = AetherisNotebookPanel.parseSettingsYaml(written);
        assertTrue(again.borders);
        assertFalse(again.debug);
        assertTrue(again.tooltips);
        assertEquals("keep: 1\n", again.extras);
    }

    /**
     * The method arranges three yaml texts.
     * The method calls {@link AetherisNotebookPanel#parseSettingsYaml} on each text.
     * Version {@code 2} leaves {@code blocked} true.
     * A repeated {@code painted_borders} key leaves {@code blocked} true.
     * The value {@code maybe} leaves {@code blocked} true.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBadBorderFileStaysBlocked() {
        assertTrue(AetherisNotebookPanel.parseSettingsYaml("version: 2\n").blocked);
        assertTrue(AetherisNotebookPanel.parseSettingsYaml(
                "version: 1\npainted_borders: true\npainted_borders: false\n").blocked);
        assertTrue(AetherisNotebookPanel.parseSettingsYaml("version: 1\npainted_borders: maybe\n").blocked);
    }

    /**
     * The method arranges a view rectangle of width {@code 200} and height {@code 80}, plus caret rectangles.
     * The method calls {@link AetherisNotebookPanel#caretRests} with an inset of {@code 4}.
     * A caret at {@code (20, 20)} with size {@code 2} by {@code 16} returns true.
     * A caret on the left edge returns false.
     * A caret on the top edge returns false.
     * A caret that crosses the right inset returns false.
     * A null view returns true for a one-pixel caret.
     *
     * @return The method returns nothing.
     */
    @Test
    void theCaretRestsInsideThePane() {
        Rectangle view = new Rectangle(0, 0, 200, 80);
        assertTrue(AetherisNotebookPanel.caretRests(view, new Rectangle(20, 20, 2, 16), 4));
        assertFalse(AetherisNotebookPanel.caretRests(view, new Rectangle(0, 20, 2, 16), 4));
        assertFalse(AetherisNotebookPanel.caretRests(view, new Rectangle(20, 0, 2, 16), 4));
        assertFalse(AetherisNotebookPanel.caretRests(view, new Rectangle(190, 20, 8, 16), 4));
        assertTrue(AetherisNotebookPanel.caretRests(null, new Rectangle(0, 0, 1, 1), 4));
    }

    /**
     * The method arranges a {@link SoftWrapArea} whose text is {@code mood line}, inside a {@link JViewport}.
     * The view size is {@code 80} by {@code 400}.
     * The extent size is {@code 80} by {@code 40}.
     * The view position starts at {@code (0, 80)}.
     * The method calls {@link SoftWrapArea#scrollRectToVisible} with a rectangle at the origin.
     * The view position y stays {@code 80}.
     * The view position x stays {@code 0}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theInspectorBodyDoesNotPullTheView() {
        SoftWrapArea area = new SoftWrapArea();
        area.setText("mood line\n");
        JViewport port = new JViewport();
        port.setView(area);
        port.setViewSize(new Dimension(80, 400));
        port.setExtentSize(new Dimension(80, 40));
        port.setViewPosition(new Point(0, 80));
        area.scrollRectToVisible(new Rectangle(0, 0, 8, 8));
        assertEquals(80, port.getViewPosition().y);
        assertEquals(0, port.getViewPosition().x);
    }
}
