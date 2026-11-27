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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * The control strip sits on the north of the pane.
 * BB1 holds the controls through RADIAL.
 * BB2 holds the rest and starts hidden.
 * A mark at the left folds both bars.
 * A mark at the end of BB1 shows BB2.
 * The choices are not saved.
 * A bar button wears a round corner.
 * The corner arc is twelve pixels, the same arc the Aetheris bar uses.
 * The text keeps four pixels above it and four pixels below it.
 * The row keeps four pixels around each button.
 * A bar fill keeps its hue.
 * The saturation is four fifths of the source.
 * The brightness is four fifths of the source.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1474 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
// @aa-sdd L40 aetheris-plan-f-308.001.md FINESSE:f-308
// @aa-sdd L40 aetheris-plan-f-315.001.md FINESSE:f-315
// @aa-sdd L40 aetheris-plan-f-323.001.md FINESSE:f-323
// @aa-sdd L40 aetheris-plan-f-329.001.md FINESSE:f-329
// @aa-sdd L40 aetheris-plan-f-330.001.md FINESSE:f-330
public final class GraphStrip extends JPanel {

    private static final int CORNER = 12;
    private static final int TEXT_PAD = 4;
    private static final int SIDE_PAD = 10;
    private static final int BUTTON_GAP = 4;

    private static final Color PURPLE_FILL = deepen(new Color(0x994499));
    private static final Color PURPLE_EDGE = new Color(0x7C2BFF);
    private static final Color RED_FILL = deepen(new Color(0x994444));
    private static final Color RED_DEEP = deepen(new Color(0x662E2E));
    private static final Color RED_EDGE = new Color(0xFF6600);
    private static final Color BUTTON_INK = new Color(0xFFFFFF);
    private static final Color SELECTED_FILL = deepen(new Color(0x4E8AD4));

    /**
     * The method darkens one bar fill from its own color.
     * The hue stays.
     * The saturation is four fifths of the source.
     * The brightness is four fifths of the source.
     *
     * @param color The color is the source background.
     * @return The method returns the darker fill.
     */
    static Color deepen(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1] * 0.80f, hsb[2] * 0.80f));
    }

    private static final String[] ACTS = {"PIN", "SPLIT", "MERGE", "REJECT", "PROMOTE"};
    private static final String[] LAYOUTS = {"LAYERED", "FORCE", "RADIAL"};

    private final GraphPane pane;
    private final Map<String, JButton> buttons = new LinkedHashMap<>();
    private final Map<String, Color> fills = new LinkedHashMap<>();
    private final JPanel openBody;
    private final JPanel bb1;
    private final JPanel bb2;
    private final JButton parentMark;
    private final JButton toolsMark;
    private final JPanel foldLine;
    private final JComponent bridge;
    private final ZoomDial zoom;
    private boolean barsOpen = true;
    private boolean toolsOpen = false;

    public GraphStrip(GraphPane pane) {
        super(new BorderLayout());
        this.pane = pane;
        setOpaque(false);
        openBody = new JPanel(new BorderLayout());
        openBody.setName("graph-bars");
        openBody.setOpaque(false);
        bb1 = row("graph-bb1");
        bb2 = row("graph-bb2");
        bb2.setVisible(false);
        parentMark = mark("graph-bb1-fold");
        toolsMark = mark("graph-bb2-fold");
        parentMark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                barsOpen = !barsOpen;
                seatParent();
            }
        });
        toolsMark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                toolsOpen = !toolsOpen;
                seatTools();
            }
        });
        bb1.add(rail("graph-bb1-fold-rail"));
        button("WHOLE", "WHOLE. Show every intent.", bb1);
        button("REICHENBACH", "REICHENBACH. Show the intents the sink allows.", bb1);
        button("MODEL", "MODEL. Leave the filter as it is.", bb1);
        button("RELOAD", "RELOAD. Clear this graph and infer the conversation again.", bb1);
        button("STOP", "STOP. Stop this graph load.", bb1);
        button("PALETTE", "PALETTE. Store a chosen ink.", bb1);
        button("EVERYTHING", "EVERYTHING. Open the clustered wrap.", bb1);
        button("LAYERED", "LAYERED. Open the layered reference.", bb1);
        button("FORCE", "FORCE. Open the force reference.", bb1);
        button("RADIAL", "RADIAL. Open the radial reference.", bb1);
        bb1.add(rail("graph-bb1-tools-rail"));
        bb1.add(toolsMark);
        button("RESET LAYOUT", "RESET LAYOUT. Lay the selected layout out again.", bb2);
        zoom = new ZoomDial(pane);
        bb2.add(zoom);
        bridge = new JComponent() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(8, 18);
            }

            @Override
            protected void paintComponent(Graphics graphics) {
                graphics.setColor(new Color(0x8A8A8A));
                graphics.fillRect(3, 0, 2, getHeight());
            }
        };
        bridge.setToolTipText("Divider. The mark separates the layout icons from the act buttons.");
        bb2.add(bridge);
        button("PIN", "PIN. Store or clear the pin on the selected intent.", bb2);
        button("SPLIT", "SPLIT. Split the selected meat word.", bb2);
        button("MERGE", "MERGE. Merge the two selected meat words.", bb2);
        button("REJECT", "REJECT. Store or clear the reject on the selected intent.", bb2);
        button("PROMOTE", "PROMOTE. Promote the selected meat word.", bb2);
        JPanel stack = new JPanel(new BorderLayout());
        stack.setName("graph-bar-stack");
        stack.setOpaque(false);
        stack.add(bb1, BorderLayout.NORTH);
        stack.add(bb2, BorderLayout.SOUTH);
        openBody.add(stack, BorderLayout.CENTER);
        foldLine = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getPreferredSize() {
                int height = 1;
                Dimension markSize = parentMark.getPreferredSize();
                if (markSize != null && markSize.height > height) {
                    height = markSize.height;
                }
                return new Dimension(120, height);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMaximumSize() {
                Dimension preferred = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE, preferred.height);
            }

            @Override
            protected void paintComponent(Graphics graphics) {
                if (parentMark.getParent() != this) {
                    return;
                }
                graphics.setColor(new Color(0x8A8A8A));
                int y = getHeight() / 2;
                int leftX = parentMark.getWidth() + 6;
                int rightX = getWidth() - 16;
                if (rightX > leftX) {
                    graphics.drawLine(leftX, y, rightX, y);
                }
            }
        };
        foldLine.setName("graph-bb1-fold-line");
        foldLine.setOpaque(false);
        foldLine.setVisible(false);
        add(openBody, BorderLayout.CENTER);
        add(foldLine, BorderLayout.SOUTH);
        seatParent();
        seatTools();
    }

    public void press(String name) {
        pane.onControl(name);
    }

    public void store(String group, String ink) {
        pane.store(group, ink);
    }

    public Color fill(String name) {
        Color color = fills.get(name);
        if (color == null) {
            return PURPLE_FILL;
        }
        return color;
    }

    public Color ink(String name) {
        JButton button = buttons.get(name);
        if (button == null) {
            return BUTTON_INK;
        }
        return button.getForeground();
    }

    public String tip(String name) {
        JButton button = buttons.get(name);
        if (button == null) {
            return null;
        }
        return button.getToolTipText();
    }

    public boolean visible(String name) {
        JButton button = buttons.get(name);
        return button != null && button.isVisible();
    }

    public boolean bridgeVisible() {
        return bridge.isVisible();
    }

    /**
     * The method seats one point size on every strip button.
     *
     * @param size The size is the seated point size.
     */
    public void seatType(int size) {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, size);
        for (JButton button : buttons.values()) {
            button.setFont(font);
        }
        parentMark.setFont(font);
        toolsMark.setFont(font);
        zoom.seatFont(font);
        revalidate();
        repaint();
    }

    /**
     * The method applies the percent typed in the zoom field.
     * A blank field restores the current percent.
     * A value that is not an integer restores the current percent.
     */
    void applyZoomText() {
        zoom.finishTyped();
    }

    /**
     * The method reads the point size of one named button.
     * A missing name returns 0.
     *
     * @param name The name is the button label.
     * @return The return value is that point size.
     */
    public int buttonPoints(String name) {
        JButton button = buttons.get(name);
        if (button == null || button.getFont() == null) {
            return 0;
        }
        return button.getFont().getSize();
    }

    public void sync() {
        int page = pane.page();
        boolean layoutPages = page >= 10 && page <= 13;
        boolean closer = page >= 1 && page <= 8;
        for (String name : LAYOUTS) {
            JButton button = buttons.get(name);
            button.setVisible(layoutPages);
        }
        for (String name : ACTS) {
            JButton button = buttons.get(name);
            button.setVisible(closer || layoutPages);
        }
        bridge.setVisible(layoutPages && closer);
        String filter = pane.filter();
        paintIdle("WHOLE", "WHOLE".equals(filter));
        paintIdle("REICHENBACH", "REICHENBACH".equals(filter));
        paintIdle("MODEL", false);
        paintIdle("RELOAD", false);
        paintIdle("STOP", false);
        paintIdle("PALETTE", false);
        paintIdle("EVERYTHING", false);
        paintIdle("LAYERED", "layered".equals(pane.layoutName()) && layoutPages);
        paintIdle("FORCE", "force".equals(pane.layoutName()) && layoutPages);
        paintIdle("RADIAL", "radial".equals(pane.layoutName()) && layoutPages);
        paint(buttons.get("RESET LAYOUT"), "RESET LAYOUT", RED_FILL, BUTTON_INK, RED_EDGE);
        for (String name : ACTS) {
            paintAct(name);
        }
        zoom.showPercent();
        revalidate();
        repaint();
    }

    /**
     * The method places the parent fold around both graph bars.
     * An open bar shows v and keeps the mark at the left of BB1.
     * A folded bar shows ^ and keeps that mark on the line.
     * BB2 keeps the open or hidden state it already had.
     */
    private void seatParent() {
        if (barsOpen) {
            parentMark.setText("v");
            parentMark.setToolTipText(
                    "Fold the graph bar. BB1 and BB2 leave the window."
                            + " The folded line keeps the unfold mark."
                            + " The choice is not saved.");
        } else {
            parentMark.setText("^");
            parentMark.setToolTipText(
                    "Show the graph bar. The fold mark sits at the left of BB1."
                            + " BB2 stays on its own mark."
                            + " The choice is not saved.");
        }
        Container parent = parentMark.getParent();
        if (parent != null) {
            parent.remove(parentMark);
        }
        if (barsOpen) {
            foldLine.setVisible(false);
            openBody.setVisible(true);
            bb1.add(parentMark, 0);
        } else {
            openBody.setVisible(false);
            foldLine.setVisible(true);
            foldLine.add(parentMark, BorderLayout.WEST);
        }
        revalidate();
        repaint();
    }

    /**
     * The method shows or hides BB2 under BB1.
     * An open bar shows ^ and a closed bar shows v.
     * The choice is not saved.
     */
    private void seatTools() {
        if (toolsOpen) {
            toolsMark.setText("^");
            toolsMark.setToolTipText(
                    "Hide the second graph bar."
                            + " RESET LAYOUT, Zoom, PIN, SPLIT, MERGE, REJECT,"
                            + " and PROMOTE fold away."
                            + " The choice is not saved.");
        } else {
            toolsMark.setText("v");
            toolsMark.setToolTipText(
                    "Show the second graph bar."
                            + " RESET LAYOUT, Zoom, PIN, SPLIT, MERGE, REJECT,"
                            + " and PROMOTE sit under this row."
                            + " The bar starts hidden. The choice is not saved.");
        }
        bb2.setVisible(toolsOpen);
        revalidate();
        repaint();
    }

    private JPanel row(String name) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, BUTTON_GAP, BUTTON_GAP));
        row.setName(name);
        row.setOpaque(false);
        return row;
    }

    private JComponent rail(String name) {
        JComponent rail = new JComponent() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(8, 18);
            }

            @Override
            protected void paintComponent(Graphics graphics) {
                graphics.setColor(new Color(0x8A8A8A));
                graphics.fillRect(3, 0, 2, getHeight());
            }
        };
        rail.setName(name);
        rail.setToolTipText("Divider. The mark separates the graph controls.");
        return rail;
    }

    private JButton mark(String name) {
        JButton button = new JButton("v");
        button.setName(name);
        seatFace(button, PURPLE_FILL, BUTTON_INK, PURPLE_EDGE);
        return button;
    }

    private void paintAct(String name) {
        JButton button = buttons.get(name);
        boolean deep = false;
        String selected = pane.selected();
        if (selected != null && pane.model().hasIntent(selected)) {
            if ("PIN".equals(name) && pane.model().pinned(selected)) {
                deep = true;
            }
            if ("REJECT".equals(name) && pane.model().rejected(selected)) {
                deep = true;
            }
        }
        if (deep) {
            paint(button, name, RED_DEEP, BUTTON_INK, RED_EDGE);
            return;
        }
        paint(button, name, RED_FILL, BUTTON_INK, RED_EDGE);
    }

    private void paintIdle(String name, boolean selected) {
        JButton button = buttons.get(name);
        if (selected) {
            paint(button, name, SELECTED_FILL, BUTTON_INK, RED_EDGE);
            return;
        }
        paint(button, name, PURPLE_FILL, BUTTON_INK, PURPLE_EDGE);
    }

    private void paint(JButton button, String name, Color fill, Color ink, Color edge) {
        fills.put(name, fill);
        button.setBackground(fill);
        button.setForeground(ink);
        button.putClientProperty("aetheris.graph.edge", edge);
        button.setBorder(new EmptyBorder(TEXT_PAD, SIDE_PAD, TEXT_PAD, SIDE_PAD));
    }

    /**
     * The method seats the round face on one graph button.
     * The client property {@code aetheris.graph} keeps the plate face off this button.
     * The corner arc is {@link #CORNER}.
     * The empty border keeps {@link #TEXT_PAD} above the text and below the text.
     *
     * @param button The button is the control that wears the face.
     * @param fill The fill is the button ground.
     * @param ink The ink is the word color.
     * @param edge The edge is the one-pixel stroke.
     */
    private static void seatFace(JButton button, Color fill, Color ink, Color edge) {
        button.putClientProperty("aetheris.graph", Boolean.TRUE);
        button.setUI(new RoundFace());
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        button.setBackground(fill);
        button.setForeground(ink);
        button.putClientProperty("aetheris.graph.edge", edge);
        button.setBorder(new EmptyBorder(TEXT_PAD, SIDE_PAD, TEXT_PAD, SIDE_PAD));
    }

    private void button(final String name, String tip, JPanel row) {
        JButton button = new JButton(name);
        if (row == bb1) {
            seatFace(button, PURPLE_FILL, BUTTON_INK, PURPLE_EDGE);
            fills.put(name, PURPLE_FILL);
        } else {
            seatFace(button, RED_FILL, BUTTON_INK, RED_EDGE);
            fills.put(name, RED_FILL);
        }
        button.setToolTipText(tip);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                press(name);
            }
        });
        buttons.put(name, button);
        row.add(button);
    }

    /**
     * The zoom unit sits on BB2.
     * Minus is on the left, the percent is in the middle, and plus is on the right.
     * Plus and minus step the scale by 25 at once.
     * A typed percent waits 800 milliseconds, and then the field shows the scale that was seated.
     * The scale stays 25 through 400.
     * The percent is not stored.
     */
    private static final class ZoomDial extends JPanel {

        private final GraphPane pane;
        private final JLabel label;
        private final JButton minusButton;
        private final JButton plusButton;
        private final JTextField field;
        private final Timer timer;
        private boolean suppress;

        private ZoomDial(GraphPane pane) {
            super(new FlowLayout(FlowLayout.LEFT, 4, 0));
            this.pane = pane;
            setName("graph-zoom");
            setOpaque(false);
            label = new JLabel("Zoom");
            label.setName("graph-zoom-label");
            label.setOpaque(false);
            label.setForeground(new Color(0xFFF8E8));
            label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            label.setToolTipText("Zoom. The percent is not stored.");
            minusButton = stepButton("graph-zoom-minus", "-");
            minusButton.setToolTipText("Zoom out. Make the nodes smaller.");
            plusButton = stepButton("graph-zoom-plus", "+");
            plusButton.setToolTipText("Zoom in. Make the nodes larger.");
            field = new JTextField(Integer.toString(pane.picture().zoomPercent()), 4);
            field.setName("graph-zoom-field");
            field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            field.setToolTipText("Zoom percent. Type a percent. The scale stays 25 through 400.");
            timer = new Timer(800, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    applyTyped();
                }
            });
            timer.setRepeats(false);
            minusButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    step(-25);
                }
            });
            plusButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    step(25);
                }
            });
            field.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent event) {
                    schedule();
                }

                @Override
                public void removeUpdate(DocumentEvent event) {
                    schedule();
                }

                @Override
                public void changedUpdate(DocumentEvent event) {
                    schedule();
                }
            });
            add(label);
            add(minusButton);
            add(field);
            add(plusButton);
        }

        private void seatFont(Font font) {
            label.setFont(font);
            minusButton.setFont(font);
            field.setFont(font);
            plusButton.setFont(font);
        }

        private void finishTyped() {
            timer.stop();
            applyTyped();
        }

        private void step(int delta) {
            timer.stop();
            pane.picture().seatZoom(pane.picture().zoomPercent() + delta);
            pane.strip().sync();
        }

        private void schedule() {
            if (suppress) {
                return;
            }
            timer.restart();
        }

        private void applyTyped() {
            timer.stop();
            String text = field.getText();
            if (text == null) {
                text = "";
            }
            text = text.trim();
            if (text.length() == 0) {
                showPercent();
                return;
            }
            try {
                pane.picture().seatZoom(Integer.parseInt(text));
            } catch (NumberFormatException ex) {
                showPercent();
                return;
            }
            pane.strip().sync();
        }

        private void showPercent() {
            String text = Integer.toString(pane.picture().zoomPercent());
            if (text.equals(field.getText())) {
                return;
            }
            suppress = true;
            field.setText(text);
            suppress = false;
        }

        private JButton stepButton(String name, String text) {
            JButton button = new JButton(text);
            button.setName(name);
            seatFace(button, RED_FILL, BUTTON_INK, RED_EDGE);
            return button;
        }
    }

    /**
     * The face paints a graph button with a round corner.
     * The corner arc is twelve pixels.
     * The fill is the button background.
     * The stroke is the stored edge color.
     * The word is painted after the fill.
     */
    private static final class RoundFace extends BasicButtonUI {

        /**
         * The method keeps the stock defaults and clears the pressed-text shift.
         *
         * @param b The button receiving the defaults.
         */
        @Override
        protected void installDefaults(AbstractButton b) {
            super.installDefaults(b);
            defaultTextShiftOffset = 0;
        }

        /**
         * The method paints the round fill and the edge, then the word.
         * Antialiasing is on.
         * A missing background skips the fill.
         * A missing edge skips the stroke.
         * The graphics copy is disposed before the word is painted.
         *
         * @param g The graphics Swing passed for this button.
         * @param c The button.
         */
        @Override
        public void update(Graphics g, JComponent c) {
            AbstractButton button = (AbstractButton) c;
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = c.getWidth();
                int h = c.getHeight();
                RoundRectangle2D box = new RoundRectangle2D.Double(
                        0.5, 0.5, Math.max(0, w - 1), Math.max(0, h - 1), CORNER, CORNER);
                if (button.getBackground() != null) {
                    g2.setColor(button.getBackground());
                    g2.fill(box);
                }
                Object edge = button.getClientProperty("aetheris.graph.edge");
                if (edge instanceof Color) {
                    g2.setColor((Color) edge);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(box);
                }
            } finally {
                g2.dispose();
            }
            paint(g, c);
        }
    }
}
