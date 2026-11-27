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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 * The inspector lists intents.
 * It does not send.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1474 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public final class GraphInspector extends JComponent {

    private static final Color PANE = new Color(0x2A1B42);
    private static final Color INK = new Color(0xE0F4FF);

    private final GraphPane pane;
    private int typePoints = 20;

    public GraphInspector(GraphPane pane) {
        this.pane = pane;
        setOpaque(true);
        setBackground(PANE);
        setToolTipText("Inspector. Read the intents on this graph.");
    }

    public List<String> names() {
        List<String> ids = pane.model().intentIds();
        if (!"REICHENBACH".equals(pane.filter())) {
            return ids;
        }
        List<String> shown = new ArrayList<>();
        for (String id : ids) {
            if (pane.allowed(id)) {
                shown.add(id);
            }
        }
        return shown;
    }

    /**
     * The method seats one point size on the inspector lines.
     *
     * @param size The size is the seated point size.
     */
    public void seatType(int size) {
        typePoints = size;
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

    @Override
    public Dimension getPreferredSize() {
        int lines = Math.max(1, names().size());
        int step = typePoints + 6;
        int wide = Math.max(180, typePoints * 12);
        int high = Math.max(240, 8 + lines * step);
        return new Dimension(wide, high);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.setColor(PANE);
        graphics.fillRect(0, 0, Math.max(1, getWidth()), Math.max(1, getHeight()));
        graphics.setColor(INK);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, typePoints));
        List<String> shown = names();
        int y = typePoints + 4;
        int step = typePoints + 6;
        for (String name : shown) {
            graphics.drawString(name, 8, y);
            y = y + step;
        }
    }
}
