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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.casserole.app.ui.ssh.SshClientProperties;
import org.casserole.app.ui.ssh.SshClientTheme;
import org.junit.jupiter.api.Test;

/**
 * A graph button wears a round corner.
 * The text keeps four pixels above it and four pixels below it.
 * The row keeps four pixels around each button.
 * The test does not open a window.
 *
 * @author clayl (2026-11-27)
 */
class GraphBarShapeTest {

    /**
     * BB1 and BB2 keep four pixels around each button.
     * Each bar button keeps four pixels above the text and four pixels below it.
     * The corner of the painted button is outside the fill.
     * The plate face leaves the round face in place.
     * The bar ground keeps its hue.
     */
    @Test
    void aGraphButtonWearsARoundCorner() throws Exception {
        GraphPane pane = GraphDesk.pane("round", "conversation");
        pane.showPage(1);
        pane.strip().sync();
        JPanel ground = (JPanel) pane.getComponent(0);
        Color darker = GraphStrip.deepen(new Color(0x3A78C2));
        assertEquals(new Color(0x446C9B), darker);
        assertEquals(darker, ground.getBackground());
        JPanel bb1 = (JPanel) named(pane.strip(), "graph-bb1");
        JPanel bb2 = (JPanel) named(pane.strip(), "graph-bb2");
        FlowLayout lead = (FlowLayout) bb1.getLayout();
        FlowLayout rest = (FlowLayout) bb2.getLayout();
        assertTrue(lead.getHgap() >= 4);
        assertTrue(lead.getVgap() >= 4);
        assertTrue(rest.getHgap() >= 4);
        assertTrue(rest.getVgap() >= 4);
        JButton whole = buttonText(pane.strip(), "WHOLE");
        JButton reset = buttonText(pane.strip(), "RESET LAYOUT");
        JButton fold = (JButton) named(pane.strip(), "graph-bb1-fold");
        JButton tools = (JButton) named(pane.strip(), "graph-bb2-fold");
        JButton minus = (JButton) named(pane.strip(), "graph-zoom-minus");
        JButton plus = (JButton) named(pane.strip(), "graph-zoom-plus");
        JButton[] bars = {whole, reset, fold, tools, minus, plus};
        SshClientTheme theme = new SshClientTheme(new SshClientProperties());
        Method seat = SshClientTheme.class.getDeclaredMethod("seatPlateFace", AbstractButton.class);
        seat.setAccessible(true);
        for (int i = 0; i < bars.length; i++) {
            Insets insets = bars[i].getInsets();
            assertTrue(insets.top >= 3, bars[i].getText());
            assertTrue(insets.bottom >= 3, bars[i].getText());
            assertTrue(bars[i].getUI().getClass().getName().endsWith("RoundFace"));
            assertFalse(bars[i].isOpaque());
            int before = bars[i].getBackground().getRGB();
            seat.invoke(theme, bars[i]);
            assertEquals(before, bars[i].getBackground().getRGB());
            assertTrue(bars[i].getUI().getClass().getName().endsWith("RoundFace"));
            BufferedImage image = plate(bars[i]);
            int pad = image.getRGB(image.getWidth() / 2, 2);
            int corner = image.getRGB(0, 0);
            assertEquals(before, pad);
            assertNotEquals(before, corner);
        }
    }

    private static BufferedImage plate(JButton button) {
        int width = button.getPreferredSize().width;
        int height = button.getPreferredSize().height;
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        button.setSize(width, height);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D graphics = image.createGraphics();
        try {
            button.paint(graphics);
        } finally {
            graphics.dispose();
        }
        return image;
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

    private static JButton buttonText(Component root, String text) {
        if (root instanceof JButton && text.equals(((JButton) root).getText())) {
            return (JButton) root;
        }
        if (root instanceof Container) {
            Container box = (Container) root;
            int count = box.getComponentCount();
            for (int i = 0; i < count; i++) {
                JButton found = buttonText(box.getComponent(i), text);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
