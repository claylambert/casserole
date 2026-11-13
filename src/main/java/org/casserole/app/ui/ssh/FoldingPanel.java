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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

// @aa-sdd PK-1154 docs/specs/common/formal-plan.spec.md FINESSE:f-102
// @aa-sdd PK-1149 docs/specs/common/formal-plan.spec.md FINESSE:f-108
// @aa-sdd PK-1159 docs/specs/common/stages.spec.md FINESSE:f-157
// @aa-sdd PK-1017 docs/specs/common/formal-plan.spec.md FINESSE:f-78
// @aa-sdd PK-1000 docs/specs/common/formal-plan.spec.md FINESSE:f-95
// @aa-sdd PK-997 docs/specs/common/formal-plan.spec.md FINESSE:f-98
/**
 * The {@code FoldingPanel} is the connection bar that can fold.
 * Open, the mark button shows {@code v}.
 * Folded, the mark button shows {@code ^}.
 * The panel holds the {@link SshConnectionPanel}, the product tabs, and the {@link SshClientTheme}.
 * A {@link BarPick} receives a press on the folded SSH button or the folded Aetheris button.
 *
 * The open bar places the menu above the connection fields.
 * A one-pixel line sits under those fields.
 * The folded bar is one row.
 * That row shows the mark, the link word, the SSH button, the Aetheris button, a divider, and the same menu.
 * The tab strip stays in the center in both layouts.
 * The bar starts open.
 * The link starts down.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#FoldingPanel
 */
// @aa-sdd PK-766 docs/specs/common/stages.spec.md
// @aa-sdd PK-939 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-940 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-941 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-202 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-1117 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-943 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-660 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-578 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
// @aa-sdd PK-661 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-662 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-663 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-663 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-664 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-578 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
// @aa-sdd PK-665 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-666 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-667 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-699 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-700 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-665 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-578 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
// @aa-sdd PK-667 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-634 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-197 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-214 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-228 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-237 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-241 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
final class FoldingPanel extends JPanel {

    /**
     * The {@code FOLD_TIP} is the tooltip while the bar is open.
     * The text says a click folds the connection bar and the fields leave the window.
     * The text says the canvas then uses that height.
     * The text says {@code ^} brings the bar back.
     *
     * The text says Connect folds the bar.
     * The text says a resize while connected folds the bar.
     * The text says Disconnect opens the bar.
     * The text says the choice is not saved.
     */
    static final String FOLD_TIP = "Fold the connection bar. The fields leave the window. "
            + "The canvas uses that height. ^ brings the bar back. Connect folds the bar. "
            + "A resize while connected folds it. Disconnect opens it. The choice is not saved.";

    /**
     * The {@code SHOW_TIP} is the tooltip while the bar is folded.
     * The text says a click shows the connection bar.
     * The text says the remote server, the port, the user, the password, Connect, and Disconnect sit on that row.
     *
     * The text says Connect folds the bar.
     * The text says a resize while connected folds the bar.
     * The text says Disconnect opens the bar.
     * The text says the choice is not saved.
     */
    static final String SHOW_TIP = "Show the connection bar. Host, port, user, password, "
            + "Connect, and Disconnect sit on this row. Connect folds the bar. "
            + "A resize while connected folds it. Disconnect opens it. The choice is not saved.";

    private static final Color CONNECTED_FACE = new Color(0x3c, 0xb3, 0x71);
    private static final Color CONNECTED_HI = new Color(0x9a, 0xe6, 0xbc);
    private static final Color CONNECTED_LO = new Color(0x1b, 0x5c, 0x38);

    // @aa-sdd PK-1017 docs/specs/common/formal-plan.spec.md FINESSE:f-78
    /**
     * The {@code Beat} names one change the bar can apply.
     * {@code CONNECTED} means the link came up.
     * {@code DISCONNECTED} means the link went down.
     * {@code RESIZED} means the frame size changed after the first stored size.
     * {@code MARK} means the mark button was pressed.
     * Connecting is not a value in this enum.
     */
    enum Beat {
        CONNECTED,
        DISCONNECTED,
        RESIZED,
        MARK
    }

    // @aa-sdd PK-1017 docs/specs/common/formal-plan.spec.md FINESSE:f-78
    /**
     * The {@code BarPick} receives a press on a folded product button.
     * The panel does not change the selected tab itself.
     * {@link #pressed(int)} carries the button index.
     */
    interface BarPick {
        /**
         * The {@code pressed} method reports which folded button was pressed.
         *
         * @param index {@code 0} is the SSH button. {@code 1} is the Aetheris button.
         */
        void pressed(int index);
    }

    private final SshClientTheme theme;
    private final SshConnectionPanel connection;
    private final JTabbedPane tabs;
    private final BarPick pick;
    private final JButton markButton;
    private final JPanel markWrap;
    private final JPanel openHead;
    private final JPanel foldedHead;
    private final JPanel foldedLeft;
    private final JPanel rule;
    /**
     * The {@code tabRule} is the line under the open connection fields.
     * The line is one pixel tall and uses the split background from the theme.
     * The folded divider is a different panel.
     */
    private final JPanel tabRule;
    private final LinkChip chip;
    private final JButton sshButton;
    private final JButton aetherisButton;
    /**
     * The {@code platePoints} value is the font size used for SSH, Aetheris, and the tab strip.
     * A later fold paints the buttons again and puts this size back.
     * {@code 0} means no size has been stored yet.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-164
     */
    private int platePoints;
    private JMenuBar menuBar;
    /**
     * The {@code thinkCluster} is the spinner and clock passed to {@link #seatThink}.
     * While the bar is open, the cluster stays with the connection fields.
     * While the bar is folded, the cluster sits on the east side of the folded row.
     */
    private JComponent thinkCluster;
    private boolean open = true;
    private boolean linkUp;
    private boolean spanNoted;
    private int spanW;
    private int spanH;

    /**
     * The constructor builds the open bar and the folded bar.
     * The panel uses a border layout.
     * The theme paints this panel, the mark wrap, the field row, the open head, the folded row, and the folded head.
     *
     * The mark button starts as {@code v} with {@link #FOLD_TIP}.
     * A press on the mark applies {@link Beat#MARK}.
     * The open head places the mark on the west and the connection fields in the center.
     * The gap between those two parts is four pixels.
     * {@code tabRule} sits on the south edge of the open head.
     * Its preferred size and minimum size are one by one.
     * Its maximum height stays one pixel.
     *
     * The constructor creates a link chip, an SSH button at index {@code 0}, and an Aetheris button at index {@code 1}.
     * The folded divider is one pixel wide and eighteen pixels tall.
     * The divider uses the split background.
     * The folded left row uses a left flow layout with a six-pixel horizontal gap and a two-pixel vertical gap.
     * The tab strip is added in the center.
     * The theme installs the plate tab painter.
     * {@link #showChrome} applies the open layout.
     *
     * @param connection The connection fields shown on the open bar.
     * @param tabs The product tabs. Index {@code 0} is SSH. Index {@code 1} is Aetheris.
     * @param theme The dark painter for this panel and its buttons.
     * @param pick The receiver for a folded SSH or Aetheris press. The constructor stores a null receiver.
     */
    FoldingPanel(SshConnectionPanel connection, JTabbedPane tabs, SshClientTheme theme, BarPick pick) {
        super(new BorderLayout());
        this.theme = theme;
        this.connection = connection;
        this.tabs = tabs;
        this.pick = pick;
        theme.applyPanel(this);

        markButton = new JButton(mark(true));
        markButton.setToolTipText(FOLD_TIP);
        theme.applyButton(markButton);
        /**
         * The listener folds or opens the bar.
         * Each press applies {@link Beat#MARK}.
         */
        markButton.addActionListener(new ActionListener() {
            // @aa-sdd PK-1017 docs/specs/common/formal-plan.spec.md FINESSE:f-78
            @Override
            public void actionPerformed(ActionEvent e) {
                apply(Beat.MARK);
            }
        });

        markWrap = new JPanel(new BorderLayout());
        theme.applyPanel(markWrap);
        markWrap.add(markButton, BorderLayout.NORTH);

        JPanel fieldRow = new JPanel(new BorderLayout(4, 0));
        theme.applyPanel(fieldRow);
        fieldRow.add(markWrap, BorderLayout.WEST);
        fieldRow.add(connection, BorderLayout.CENTER);

        openHead = new JPanel(new BorderLayout());
        theme.applyPanel(openHead);
        openHead.add(fieldRow, BorderLayout.CENTER);
        tabRule = new JPanel();
        tabRule.setOpaque(true);
        tabRule.setBackground(theme.getSplitBackground());
        tabRule.setPreferredSize(new Dimension(1, 1));
        tabRule.setMinimumSize(new Dimension(1, 1));
        tabRule.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        openHead.add(tabRule, BorderLayout.SOUTH);

        chip = new LinkChip();
        sshButton = plateButton("SSH", 0);
        aetherisButton = plateButton("Aetheris", 1);
        rule = new JPanel();
        rule.setOpaque(true);
        rule.setBackground(theme.getSplitBackground());
        rule.setPreferredSize(new Dimension(1, 18));

        foldedLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        theme.applyPanel(foldedLeft);
        foldedHead = new JPanel(new BorderLayout());
        theme.applyPanel(foldedHead);
        foldedHead.add(foldedLeft, BorderLayout.WEST);

        add(tabs, BorderLayout.CENTER);
        theme.installPlateTabs(tabs);
        showChrome();
    }

    /**
     * The {@code mark} method chooses the one-character label.
     * An open bar returns {@code v}.
     * A folded bar returns {@code ^}.
     *
     * @param open The value is true while the connection fields are on screen.
     * @return {@code v} while open, and {@code ^} while folded.
     */
    static String mark(boolean open) {
        if (open) {
            return "v";
        }
        return "^";
    }

    /**
     * The {@code holdShortLabel} method gives a short mark enough width to stay readable.
     * A null button returns immediately.
     * The method remembers the current preferred size, then clears the preferred, minimum, and maximum sizes.
     * A null label is treated as an empty string.
     * A null font uses SansSerif, plain, at twelve points.
     * A null inset set is treated as zero on every side.
     *
     * An em is the width of the letter M in that font.
     * An em below one pixel becomes one pixel.
     * The text width is the label width plus the left and right insets.
     * A label of two characters or fewer gains two ems.
     * A longer label gains two ems only when the remembered width is below the text width.
     * A remembered preferred width that is larger than that result is kept.
     *
     * The height is the larger of the remembered height and the font height plus the top and bottom insets.
     * A width below one pixel becomes one pixel.
     * A height below one pixel becomes one pixel.
     * The preferred size and the minimum size become that width and height.
     * The maximum width is {@code Integer.MAX_VALUE}.
     * The maximum height stays the chosen height.
     * The button text is left unchanged.
     *
     * @param button The mark button whose sizes are replaced.
     */
    private static void holdShortLabel(AbstractButton button) {
        if (button == null) {
            return;
        }
        int keptWidth = 0;
        int keptHeight = 0;
        Dimension fitted = button.getPreferredSize();
        if (fitted != null) {
            keptWidth = fitted.width;
            keptHeight = fitted.height;
        }
        button.setPreferredSize(null);
        button.setMinimumSize(null);
        button.setMaximumSize(null);
        String label = button.getText();
        if (label == null) {
            label = "";
        }
        Font font = button.getFont();
        FontMetrics metrics;
        if (font == null) {
            metrics = button.getFontMetrics(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        } else {
            metrics = button.getFontMetrics(font);
        }
        Insets insets = button.getInsets();
        if (insets == null) {
            insets = new Insets(0, 0, 0, 0);
        }
        int spare = metrics.charWidth('M');
        if (spare < 1) {
            spare = 1;
        }
        int textWidth = metrics.stringWidth(label);
        int width = textWidth + insets.left + insets.right;
        if (label.length() <= 2 || keptWidth < width) {
            width = width + (spare * 2);
        }
        if (keptWidth > width) {
            width = keptWidth;
        }
        int textHeight = metrics.getHeight() + insets.top + insets.bottom;
        int height = keptHeight;
        if (textHeight > height) {
            height = textHeight;
        }
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        Dimension size = new Dimension(width, height);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
    }

    /**
     * The {@code openAfter} method decides whether the fields stay on screen.
     * {@link Beat#CONNECTED} returns false, so the bar folds.
     * {@link Beat#DISCONNECTED} returns true, so the bar opens.
     * {@link Beat#RESIZED} returns false when the link is up.
     * The same beat returns the current open value when the link is down.
     * {@link Beat#MARK} returns the opposite of the current open value.
     * A null beat returns the current open value.
     *
     * @param open The current open value. True means the fields are on screen.
     * @param linkUp True when the link is up.
     * @param beat The change that just happened.
     * @return The next open value.
     */
    static boolean openAfter(boolean open, boolean linkUp, Beat beat) {
        if (beat == Beat.CONNECTED) {
            return false;
        }
        if (beat == Beat.DISCONNECTED) {
            return true;
        }
        if (beat == Beat.RESIZED) {
            if (linkUp) {
                return false;
            }
            return open;
        }
        if (beat == Beat.MARK) {
            return !open;
        }
        return open;
    }

    /**
     * The {@code linkAfter} method decides whether the link stays up.
     * {@link Beat#CONNECTED} returns true.
     * {@link Beat#DISCONNECTED} returns false.
     * {@link Beat#RESIZED} returns the current value.
     * {@link Beat#MARK} returns the current value.
     * A null beat returns the current value.
     *
     * @param linkUp The current link value. True means the link is up.
     * @param beat The change that just happened.
     * @return The next link value.
     */
    static boolean linkAfter(boolean linkUp, Beat beat) {
        if (beat == Beat.CONNECTED) {
            return true;
        }
        if (beat == Beat.DISCONNECTED) {
            return false;
        }
        return linkUp;
    }

    /**
     * The {@code seatMenu} method stores the window menu.
     * The same menu object moves between the open bar and the folded bar.
     * {@link #showChrome} places it.
     * A null bar is stored.
     *
     * @param bar The menu bar built by the window.
     */
    void seatMenu(JMenuBar bar) {
        menuBar = bar;
        showChrome();
    }

    /**
     * The {@code applyMenuFont} method sizes the menu from the font control.
     * A missing menu returns immediately.
     * A point size below one returns immediately.
     * A missing menu font becomes SansSerif, plain, at the requested size.
     * An existing menu font is derived as plain at that size.
     *
     * {@link #paintMenuFont} applies the font to the bar and to the menu rows.
     * {@link #fitMenuWidth} clears stale sizes and may grow the frame.
     * {@link #seatPlateFont} stores the same size on SSH, Aetheris, and the tabs.
     * The menu is then revalidated and repainted.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-144
     *
     * @param points The live font-control size, in points.
     */
    void applyMenuFont(int points) {
        if (menuBar == null || points < 1) {
            return;
        }
        Font base = menuBar.getFont();
        Font font;
        if (base == null) {
            font = new Font(Font.SANS_SERIF, Font.PLAIN, points);
        } else {
            font = base.deriveFont(Font.PLAIN, (float) points);
        }
        paintMenuFont(menuBar, font);
        fitMenuWidth();
        // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-164
        seatPlateFont(points);
        menuBar.revalidate();
        menuBar.repaint();
    }

    /**
     * The {@code seatPlateFont} method applies the control-bar size to SSH, Aetheris, and the tabs.
     * A size below eight becomes eight.
     * A size above thirty-six becomes thirty-six.
     * The clamped size is stored in {@code platePoints}.
     * The face is SansSerif, plain, at that size.
     *
     * The preferred, minimum, and maximum sizes of both buttons are cleared.
     * The panel is revalidated and repainted.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-164
     *
     * @param points The requested control-bar size, in points.
     */
    private void seatPlateFont(int points) {
        int size = points;
        if (size < 8) {
            size = 8;
        }
        if (size > 36) {
            size = 36;
        }
        platePoints = size;
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, size);
        sshButton.setFont(font);
        aetherisButton.setFont(font);
        sshButton.setPreferredSize(null);
        sshButton.setMinimumSize(null);
        sshButton.setMaximumSize(null);
        aetherisButton.setPreferredSize(null);
        aetherisButton.setMinimumSize(null);
        aetherisButton.setMaximumSize(null);
        tabs.setFont(font);
        revalidate();
        repaint();
    }

    /**
     * The {@code paintMenuFont} method sets one font on a menu component.
     * A null component returns immediately.
     * A null font returns immediately.
     * A {@code JMenu} then receives the same font on every component it keeps.
     * That pass includes the rows under the menu.
     *
     * Any other container sets the font only on children that are menu items.
     * Those menu items receive the same call.
     * A child that is not a menu item is left unchanged.
     *
     * @param node The bar, the menu, or one row.
     * @param font The live menu face.
     */
    private void paintMenuFont(Component node, Font font) {
        if (node == null || font == null) {
            return;
        }
        node.setFont(font);
        if (node instanceof JMenu) {
            JMenu menu = (JMenu) node;
            int count = menu.getMenuComponentCount();
            for (int i = 0; i < count; i++) {
                paintMenuFont(menu.getMenuComponent(i), font);
            }
            return;
        }
        if (node instanceof Container) {
            Component[] kids = ((Container) node).getComponents();
            for (int i = 0; i < kids.length; i++) {
                if (kids[i] instanceof JMenuItem) {
                    paintMenuFont(kids[i], font);
                }
            }
        }
    }

    /**
     * The {@code fitMenuWidth} method sizes the menu from the live font.
     * A missing menu returns immediately.
     * Each menu component that is a Swing component has its minimum, preferred, and maximum sizes cleared.
     * A menu popup in that first pass also has its preferred size cleared and is invalidated.
     *
     * The second pass measures each Swing component.
     * A missing preferred size is treated as one by one.
     * A preferred width or height below one becomes one.
     * The minimum size and the preferred size become that width and height.
     * The maximum width stays that width.
     * The maximum height is 32767.
     * The widths are added into one sum.
     *
     * A menu popup is measured again after that size is set.
     * A popup with a positive preferred width and height is given that size.
     * A popup with a missing or non-positive preferred size is left unset.
     *
     * A side sibling uses west, east, line start, or line end.
     * The parent must be using a border layout.
     * North, south, and center siblings are not counted.
     * A side sibling with a positive preferred width adds that width.
     *
     * The method then climbs from the menu parent up to the frame.
     * Each container inset on that climb adds its left and right padding.
     * The frame insets add their left and right padding.
     * The needed width is the menu sum plus that chrome.
     *
     * The climb stops when the window is not a frame.
     * The width floor starts at 640.
     * The height floor starts at 480.
     * A frame minimum that is already larger replaces that floor on that axis.
     * When the needed width is above the width floor, the frame minimum becomes the needed width and the height floor.
     * When the current frame width is positive and below the needed width, the frame is resized to the needed width.
     * That resize uses the current height when it is already at least the height floor.
     * A shorter frame is raised to the height floor.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-150
     */
    private void fitMenuWidth() {
        if (menuBar == null) {
            return;
        }
        int count = menuBar.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component node = menuBar.getComponent(i);
            if (!(node instanceof JComponent)) {
                continue;
            }
            JComponent item = (JComponent) node;
            item.setMinimumSize(null);
            item.setPreferredSize(null);
            item.setMaximumSize(null);
            if (item instanceof JMenu) {
                JPopupMenu popup = ((JMenu) item).getPopupMenu();
                if (popup != null) {
                    popup.setPreferredSize(null);
                    popup.invalidate();
                }
            }
        }
        int sum = 0;
        for (int i = 0; i < count; i++) {
            Component node = menuBar.getComponent(i);
            if (!(node instanceof JComponent)) {
                continue;
            }
            JComponent item = (JComponent) node;
            Dimension pref = item.getPreferredSize();
            int width = 1;
            int height = 1;
            if (pref != null) {
                if (pref.width > width) {
                    width = pref.width;
                }
                if (pref.height > height) {
                    height = pref.height;
                }
            }
            item.setMinimumSize(new Dimension(width, height));
            item.setPreferredSize(new Dimension(width, height));
            item.setMaximumSize(new Dimension(width, 32767));
            sum += width;
            if (item instanceof JMenu) {
                JPopupMenu popup = ((JMenu) item).getPopupMenu();
                if (popup != null) {
                    popup.setPreferredSize(null);
                    popup.invalidate();
                    Dimension pop = popup.getPreferredSize();
                    if (pop != null && pop.width > 0 && pop.height > 0) {
                        popup.setSize(new Dimension(pop.width, pop.height));
                    }
                }
            }
        }
        int beside = 0;
        Container row = menuBar.getParent();
        if (row != null && row.getLayout() instanceof BorderLayout) {
            BorderLayout layout = (BorderLayout) row.getLayout();
            Component[] kin = row.getComponents();
            for (int i = 0; i < kin.length; i++) {
                if (kin[i] == menuBar) {
                    continue;
                }
                Object where = layout.getConstraints(kin[i]);
                boolean side = BorderLayout.WEST.equals(where) || BorderLayout.EAST.equals(where)
                        || BorderLayout.LINE_START.equals(where) || BorderLayout.LINE_END.equals(where);
                if (!side) {
                    continue;
                }
                Dimension pref = kin[i].getPreferredSize();
                if (pref != null && pref.width > 0) {
                    beside += pref.width;
                }
            }
        }
        Window window = SwingUtilities.getWindowAncestor(menuBar);
        if (!(window instanceof Frame)) {
            return;
        }
        Frame frame = (Frame) window;
        int chrome = beside;
        Container walk = menuBar.getParent();
        while (walk != null && walk != frame) {
            Insets pad = walk.getInsets();
            if (pad != null) {
                chrome += pad.left + pad.right;
            }
            walk = walk.getParent();
        }
        Insets insets = frame.getInsets();
        if (insets != null) {
            chrome += insets.left + insets.right;
        }
        int need = sum + chrome;
        Dimension min = frame.getMinimumSize();
        int floorW = 640;
        int floorH = 480;
        if (min != null) {
            if (min.width > floorW) {
                floorW = min.width;
            }
            if (min.height > floorH) {
                floorH = min.height;
            }
        }
        if (need > floorW) {
            frame.setMinimumSize(new Dimension(need, floorH));
        }
        int wide = frame.getWidth();
        if (wide > 0 && wide < need) {
            int high = frame.getHeight();
            if (high < floorH) {
                high = floorH;
            }
            frame.setSize(need, high);
        }
    }

    /**
     * The {@code apply} method records one beat.
     * {@link #linkAfter} updates the stored link.
     * {@link #openAfter} chooses the next open value.
     * An unchanged open value returns without painting.
     * A changed value is stored.
     * {@link #showChrome} then rebuilds the visible bar.
     * Connecting does not call this method.
     *
     * @param beat The change that just happened.
     */
    void apply(Beat beat) {
        linkUp = linkAfter(linkUp, beat);
        boolean next = openAfter(open, linkUp, beat);
        if (next == open) {
            return;
        }
        open = next;
        showChrome();
    }

    /**
     * The {@code showFields} method opens the connection fields.
     * An already open bar returns immediately.
     * The stored link word is left unchanged.
     * {@link #showChrome} rebuilds the open bar.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-132
     */
    void showFields() {
        if (open) {
            return;
        }
        open = true;
        showChrome();
    }

    /**
     * The {@code noteSpan} method watches the frame size.
     * A width of zero or less returns immediately.
     * A height of zero or less returns immediately.
     * The first positive size is stored and does not fold the bar.
     *
     * A later call stores the new size.
     * A width change or a height change applies {@link Beat#RESIZED}.
     * An unchanged size does not apply a beat.
     *
     * @param width The frame width, in pixels.
     * @param height The frame height, in pixels.
     */
    void noteSpan(int width, int height) {
        if (width <= 0 || height <= 0) {
            return;
        }
        if (!spanNoted) {
            spanNoted = true;
            spanW = width;
            spanH = height;
            return;
        }
        boolean changed = spanW != width || spanH != height;
        spanW = width;
        spanH = height;
        if (changed) {
            apply(Beat.RESIZED);
        }
    }

    /**
     * The {@code showLink} method sets the word on the folded row.
     * A null word becomes an empty string.
     * The exact text {@code Connected} paints the green chip.
     * Any other word is passed through {@link Vt100StatusLine#west}.
     * The second argument and the third argument of that call are empty strings.
     * The chip stores the resulting text and the green flag.
     *
     * @param word The link word. A null value is shown as empty text.
     */
    void showLink(String word) {
        String text = word == null ? "" : word;
        boolean green = "Connected".equals(text);
        String shown;
        if (green) {
            shown = "Connected";
        } else {
            shown = Vt100StatusLine.west(text, "", "");
        }
        chip.setWord(shown, green);
    }

    /**
     * The {@code syncPlates} method matches the folded buttons to the tab strip.
     * The SSH button is always enabled.
     * The SSH button is selected when the selected tab index is {@code 0}.
     * The Aetheris button is enabled only when the strip has more than one tab and index {@code 1} is enabled.
     * The Aetheris button is selected when the selected tab index is {@code 1}.
     */
    void syncPlates() {
        int index = tabs.getSelectedIndex();
        boolean aetheris = tabs.getTabCount() > 1 && tabs.isEnabledAt(1);
        paintPlate(sshButton, index == 0, true);
        paintPlate(aetherisButton, index == 1, aetheris);
    }

    /**
     * The {@code plateButton} method builds one folded product button.
     * The theme installs the plate face with the button unselected.
     * The label {@code SSH} gets the tooltip {@code Show the SSH terminal.}
     * The label {@code Aetheris} gets the tooltip {@code Show the Aetheris canvas. The mux has to be up.}
     * Any other label gets no tooltip.
     *
     * A press calls {@link BarPick#pressed} when a receiver was stored.
     * A null receiver skips that call.
     * The press then calls {@link #syncPlates}.
     *
     * @param label The button text.
     * @param index The index passed to {@link BarPick#pressed}.
     * @return The new button.
     */
    private JButton plateButton(final String label, final int index) {
        final JButton button = new JButton(label);
        theme.installPlateButton(button, false);
        if ("SSH".equals(label)) {
            button.setToolTipText("Show the SSH terminal.");
        } else if ("Aetheris".equals(label)) {
            button.setToolTipText("Show the Aetheris canvas. The mux has to be up.");
        }
        /**
         * The listener reports the folded button and refreshes both buttons.
         * A stored {@link BarPick} receives {@code index}.
         * A null receiver is skipped.
         * {@link #syncPlates} runs after that call.
         */
        button.addActionListener(new ActionListener() {
            // @aa-sdd PK-1017 docs/specs/common/formal-plan.spec.md FINESSE:f-78
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pick != null) {
                    pick.pressed(index);
                }
                syncPlates();
            }
        });
        return button;
    }

    /**
     * The {@code paintPlate} method updates one folded button.
     * The enabled flag is applied first.
     * The theme then installs the plate face for the selected flag.
     *
     * @param button The SSH button or the Aetheris button.
     * @param selected True when this button matches the selected tab.
     * @param enabled True when the button accepts a press.
     */
    private void paintPlate(JButton button, boolean selected, boolean enabled) {
        button.setEnabled(enabled);
        theme.installPlateButton(button, selected);
    }

    /**
     * The {@code showChrome} method rebuilds the visible bar from the stored open flag.
     * The mark text becomes {@link #mark}.
     * {@link #holdShortLabel} sizes that button.
     *
     * An open bar uses {@link #FOLD_TIP}.
     * A tab strip that is still using {@link HiddenTabs} gets the plate tab painter again.
     * The mark returns to the north of its wrap.
     * The menu returns to the north of the open head.
     * A folded head that is still on this panel is removed.
     * The open head is added on the north when it is not already there.
     *
     * A folded bar uses {@link #SHOW_TIP}.
     * A tab strip that is not using {@link HiddenTabs} is given that painter.
     * The folded left row is cleared.
     * The row then adds the mark, the link chip, SSH, Aetheris, and the divider, in that order.
     * The menu moves to the center of the folded head.
     * An open head that is still on this panel is removed.
     * The folded head is added on the north when it is not already there.
     *
     * The tab strip is added in the center when it is not already on this panel.
     * A stored {@code platePoints} value above zero is applied again.
     * {@link #placeThink} moves the spinner and clock.
     * {@link #syncPlates} refreshes the two product buttons.
     * The panel is revalidated and repainted.
     */
    private void showChrome() {
        markButton.setText(mark(open));
        holdShortLabel(markButton);
        if (open) {
            markButton.setToolTipText(FOLD_TIP);
            if (tabs.getUI() instanceof HiddenTabs) {
                theme.installPlateTabs(tabs);
            }
            seat(markButton, markWrap, BorderLayout.NORTH);
            seat(menuBar, openHead, BorderLayout.NORTH);
            if (foldedHead.getParent() == this) {
                remove(foldedHead);
            }
            if (openHead.getParent() != this) {
                add(openHead, BorderLayout.NORTH);
            }
        } else {
            markButton.setToolTipText(SHOW_TIP);
            if (!(tabs.getUI() instanceof HiddenTabs)) {
                tabs.setUI(new HiddenTabs());
            }
            foldedLeft.removeAll();
            foldedLeft.add(markButton);
            foldedLeft.add(chip);
            foldedLeft.add(sshButton);
            foldedLeft.add(aetherisButton);
            foldedLeft.add(rule);
            seat(menuBar, foldedHead, BorderLayout.CENTER);
            if (openHead.getParent() == this) {
                remove(openHead);
            }
            if (foldedHead.getParent() != this) {
                add(foldedHead, BorderLayout.NORTH);
            }
        }
        if (tabs.getParent() != this) {
            add(tabs, BorderLayout.CENTER);
        }
        // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-164
        if (platePoints > 0) {
            seatPlateFont(platePoints);
        }
        placeThink();
        syncPlates();
        revalidate();
        repaint();
    }

    /**
     * The {@code seatThink} method stores the thinking cluster.
     * {@link SshConnectionPanel#holdThink} keeps that cluster for the open bar.
     * {@link #placeThink} moves it to the row that is visible now.
     * A null cluster clears the stored cluster.
     *
     * // @aa-sdd PK-1099 docs/specs/common/formal-plan.spec.md FINESSE:f-180
     *
     * @param cluster The spinner and clock, or null.
     */
    void seatThink(JComponent cluster) {
        thinkCluster = cluster;
        connection.holdThink(cluster);
        placeThink();
    }

    /**
     * The {@code placeThink} method moves one cluster between the two bars.
     * A null cluster returns immediately.
     * An open bar calls {@link SshConnectionPanel#parkThink}.
     * A folded bar places the cluster on the east side of the folded head.
     */
    private void placeThink() {
        if (thinkCluster == null) {
            return;
        }
        if (open) {
            connection.parkThink();
        } else {
            seat(thinkCluster, foldedHead, BorderLayout.EAST);
        }
    }

    /**
     * The {@code seat} method moves a child onto a parent.
     * A null child returns immediately.
     * A null parent returns immediately.
     * A child that is already on that parent stays there.
     * A child that has another parent is removed from that parent first.
     * The child is then added with the given layout constraint.
     *
     * @param child The component to move.
     * @param parent The container that should hold the child.
     * @param where The layout constraint passed to the parent.
     */
    private static void seat(Component child, Container parent, String where) {
        if (child == null || parent == null) {
            return;
        }
        if (child.getParent() == parent) {
            return;
        }
        if (child.getParent() != null) {
            child.getParent().remove(child);
        }
        parent.add(child, where);
    }

    // @aa-sdd PK-1017 docs/specs/common/formal-plan.spec.md FINESSE:f-78
    // @aa-sdd PK-1000 docs/specs/common/formal-plan.spec.md FINESSE:f-95
    /**
     * The {@code HiddenTabs} painter removes the tab strip while the bar is folded.
     * The product names stay on the folded row.
     * The content border inset is set to zero.
     * The tab area height is zero.
     * The content border is not painted.
     */
    private static final class HiddenTabs extends BasicTabbedPaneUI {
        /**
         * The {@code installDefaults} method keeps the stock tab defaults and then clears the content border.
         * The content border inset becomes zero on every side.
         */
        @Override
        protected void installDefaults() {
            super.installDefaults();
            contentBorderInsets = new Insets(0, 0, 0, 0);
        }

        /**
         * The {@code calculateTabAreaHeight} method gives the folded tab strip no height.
         * The return value is always zero.
         *
         * @param tabPlacement The stock placement value. This method ignores it.
         * @param horizRunCount The stock run count. This method ignores it.
         * @param maxTabHeight The stock tab height. This method ignores it.
         * @return Zero.
         */
        @Override
        protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
            return 0;
        }

        /**
         * The {@code paintContentBorder} method draws nothing.
         *
         * @param g The graphics context. This method does not use it.
         * @param tabPlacement The stock placement value. This method ignores it.
         * @param selectedIndex The selected tab. This method ignores it.
         */
        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        }
    }

    // @aa-sdd PK-1017 docs/specs/common/formal-plan.spec.md FINESSE:f-78
    /**
     * The {@code LinkChip} paints the link word on the folded row.
     * The exact word {@code Connected} uses a green chip and white text.
     * Every other word uses the panel foreground and no chip.
     * The stored text starts empty.
     * The green flag starts false.
     */
    private final class LinkChip extends JComponent {
        private String text = "";
        private boolean green;

        /**
         * The {@code setWord} method stores the folded link word.
         * A null word becomes an empty string.
         * The tooltip becomes that same text.
         * The chip is revalidated and repainted.
         *
         * @param shown The text to paint.
         * @param connected True when the chip should use the green face.
         */
        private void setWord(String shown, boolean connected) {
            text = shown == null ? "" : shown;
            green = connected;
            setToolTipText(text);
            revalidate();
            repaint();
        }

        /**
         * The {@code getPreferredSize} method sizes the chip from the current text.
         * The font comes from {@link #chipFont}.
         * A green chip adds sixteen pixels of horizontal padding and six pixels of height.
         * Any other word adds two pixels of horizontal padding and no extra height.
         * The width is the text width plus that padding.
         * The height is the font height plus the extra green height.
         *
         * @return The preferred chip size.
         */
        @Override
        public Dimension getPreferredSize() {
            FontMetrics metrics = getFontMetrics(chipFont());
            int pad = green ? 16 : 2;
            int height = metrics.getHeight() + (green ? 6 : 0);
            return new Dimension(metrics.stringWidth(text) + pad, height);
        }

        /**
         * The {@code chipFont} method returns the chip font.
         * A missing font becomes SansSerif, plain, at twelve points.
         *
         * @return The font used to measure and paint the word.
         */
        private Font chipFont() {
            Font font = getFont();
            if (font == null) {
                font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
            }
            return font;
        }

        /**
         * The {@code paintComponent} method paints the link word.
         * The text baseline is vertically centered in the chip.
         *
         * A green chip fills a rounded rectangle.
         * The face color is red 60, green 179, blue 113.
         * The corner arc is four pixels.
         * A zero or negative width or height is painted as zero.
         * The top edge and the left edge use red 154, green 230, blue 188.
         * The bottom edge and the right edge use red 27, green 92, blue 56.
         * The text is white.
         * The text starts eight pixels from the left.
         *
         * Any other word uses the panel foreground.
         * That text starts at the left edge.
         * The graphics copy is disposed.
         *
         * @param graphics The graphics context for this chip.
         */
        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D pen = (Graphics2D) graphics.create();
            pen.setFont(chipFont());
            FontMetrics metrics = pen.getFontMetrics();
            int textY = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
            if (green) {
                pen.setColor(CONNECTED_FACE);
                pen.fillRoundRect(0, 0, Math.max(0, getWidth() - 1), Math.max(0, getHeight() - 1), 4, 4);
                pen.setColor(CONNECTED_HI);
                pen.drawLine(0, 0, getWidth() - 1, 0);
                pen.drawLine(0, 0, 0, getHeight() - 1);
                pen.setColor(CONNECTED_LO);
                pen.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1);
                pen.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);
                pen.setColor(Color.WHITE);
                pen.drawString(text, 8, textY);
            } else {
                pen.setColor(theme.getPanelForeground());
                pen.drawString(text, 0, textY);
            }
            pen.dispose();
        }
    }
}
