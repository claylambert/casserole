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
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicMenuItemUI;
import javax.swing.plaf.basic.BasicMenuUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalComboBoxButton;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

// @aa-sdd PK-0044 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-3666 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3676 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4172 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-3622 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3635 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3650 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3652 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3653 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4104 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-4656 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
// @aa-sdd PK-3662 docs/specs/java/org/aetheris/ssh/ssh.spec.md
/**
 * The {@code SshClientTheme} is the dark Metal look for the SSH window and the Aetheris window.
 * Metal is the Swing look and feel this class customizes.
 * The colors come from {@link SshClientProperties}.
 * A menu hover paints a grey tab.
 * A canvas scroll pane uses the quiet bevel.
 *
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 *
 * @author clayl (2026-08-24)
 */
public class SshClientTheme {

    /**
     * The {@code DIVIDER_SIZE} constant is the split-bar thickness, in pixels.
     * The value is eleven.
     * {@link #applySplitPane} uses it while one-touch expansion is on.
     */
    public static final int DIVIDER_SIZE = 11;

    /**
     * The {@code BEVEL_HIGHLIGHT} color is the lit edge of a raised bevel.
     * The red, green, and blue values are 10, 10, and 10.
     */
    private static final Color BEVEL_HIGHLIGHT = new Color(0x0a, 0x0a, 0x0a);

    /**
     * The {@code BEVEL_SHADOW} color is the dark edge of a raised bevel.
     * The red, green, and blue values are 0, 0, and 0.
     */
    private static final Color BEVEL_SHADOW = new Color(0x00, 0x00, 0x00);

    /**
     * The {@code BUTTON_HOVER_INK} color is the word color while the pointer is on a button.
     * The red, green, and blue values are 0, 255, and 102.
     */
    private static final Color BUTTON_HOVER_INK = new Color(0x00, 0xff, 0x66);

    /**
     * The {@code MENU_HOVER} color is the menu hover tab.
     * The red, green, and blue values are 70, 70, and 70.
     */
    private static final Color MENU_HOVER = new Color(70, 70, 70);

    /**
     * The {@code MENU_HOVER_ARC} constant is the corner arc of a menu hover tab, in pixels.
     * The value is six.
     */
    private static final int MENU_HOVER_ARC = 6;

    /**
     * The {@code CHECK_SAPPHIRE} color is the check outline.
     * The red, green, and blue values are 115, 174, and 255.
     */
    private static final Color CHECK_SAPPHIRE = new Color(115, 174, 255);

    /**
     * The {@code CHECK_EMERALD} color is the check mark.
     * The red, green, and blue values are 0, 255, and 65.
     */
    private static final Color CHECK_EMERALD = new Color(0x00, 0xff, 0x41);

    /**
     * The {@code BUTTON_INK} key stores a button's resting foreground.
     * The key text is {@code aetheris.button.ink}.
     * The pointer listener writes the key while the pointer is on the button.
     */
    private static final String BUTTON_INK = "aetheris.button.ink";

    /**
     * The {@code hoverSeatInstalled} flag records that the pointer listener is already installed.
     * The tooltip delay is lengthened in that same one-time install.
     */
    private static boolean hoverSeatInstalled;

    private final Color frameBackground;
    private final Color panelBackground;
    private final Color panelForeground;
    private final Color fieldBackground;
    private final Color fieldForeground;
    private final Color buttonBackground;
    private final Color buttonForeground;
    private final Color statusBackground;
    private final Color statusForeground;
    private final Color splitBackground;
    private final Color menuForeground;

    /**
     * The constructor copies colors from the properties store.
     * {@link #darkGround} remaps a frame background that is still {@code 0x1e1e1e} to {@code 0x0c0c0c}.
     * A panel background that is still {@code 0x2d2d2d} becomes {@code 0x121212}.
     * A status background that is still {@code 0x171717} becomes {@code 0x0c0c0c}.
     * A split background that is still {@code 0x1e1e1e} becomes {@code 0x0c0c0c}.
     *
     * The panel foreground is stored as loaded.
     * The field background and the field foreground are stored as loaded.
     * The button background and the button foreground are stored as loaded.
     * The status foreground is stored as loaded.
     * The menu foreground is stored as loaded.
     *
     * @param properties The loaded client properties.
     */
    public SshClientTheme(SshClientProperties properties) {
        this.frameBackground = darkGround(properties.getColor(
                SshClientProperties.UI_COLOR_FRAME_BACKGROUND,
                SshClientProperties.DEFAULT_FRAME_BACKGROUND),
                0x1e1e1e, 0x0c0c0c);
        this.panelBackground = darkGround(properties.getColor(
                SshClientProperties.UI_COLOR_PANEL_BACKGROUND,
                SshClientProperties.DEFAULT_PANEL_BACKGROUND),
                0x2d2d2d, 0x121212);
        this.panelForeground = properties.getColor(
                SshClientProperties.UI_COLOR_PANEL_FOREGROUND,
                SshClientProperties.DEFAULT_PANEL_FOREGROUND);
        this.fieldBackground = properties.getColor(
                SshClientProperties.UI_COLOR_FIELD_BACKGROUND,
                SshClientProperties.DEFAULT_FIELD_BACKGROUND);
        this.fieldForeground = properties.getColor(
                SshClientProperties.UI_COLOR_FIELD_FOREGROUND,
                SshClientProperties.DEFAULT_FIELD_FOREGROUND);
        this.buttonBackground = properties.getColor(
                SshClientProperties.UI_COLOR_BUTTON_BACKGROUND,
                SshClientProperties.DEFAULT_BUTTON_BACKGROUND);
        this.buttonForeground = properties.getColor(
                SshClientProperties.UI_COLOR_BUTTON_FOREGROUND,
                SshClientProperties.DEFAULT_BUTTON_FOREGROUND);
        this.statusBackground = darkGround(properties.getColor(
                SshClientProperties.UI_COLOR_STATUS_BACKGROUND,
                SshClientProperties.DEFAULT_STATUS_BACKGROUND),
                0x171717, 0x0c0c0c);
        this.statusForeground = properties.getColor(
                SshClientProperties.UI_COLOR_STATUS_FOREGROUND,
                SshClientProperties.DEFAULT_STATUS_FOREGROUND);
        this.splitBackground = darkGround(properties.getColor(
                SshClientProperties.UI_COLOR_SPLIT_BACKGROUND,
                SshClientProperties.DEFAULT_SPLIT_BACKGROUND),
                0x1e1e1e, 0x0c0c0c);
        this.menuForeground = properties.getColor(
                SshClientProperties.UI_COLOR_MENU_FOREGROUND,
                SshClientProperties.DEFAULT_MENU_FOREGROUND);
    }

    /**
     * The {@code installLookAndFeelDefaults} method writes dark defaults into the Swing UI manager.
     * Call it before the components are created.
     * {@code Panel.background} and {@code Label.background} receive the panel background.
     * {@code Panel.foreground} and {@code Label.foreground} receive the panel foreground.
     * {@code Button.background} receives the button background.
     * {@code Button.foreground} receives the button foreground.
     *
     * {@code TextField.background} receives the field background.
     * {@code TextField.foreground} and {@code TextField.caretForeground} receive the field foreground.
     * {@code PasswordField.background} receives the field background.
     * {@code PasswordField.foreground} and {@code PasswordField.caretForeground} receive the field foreground.
     * {@code ComboBox.background} receives the field background.
     * {@code ComboBox.foreground} receives the field foreground.
     *
     * {@code CheckBox.background} receives the panel background.
     * {@code CheckBox.foreground} receives the panel foreground.
     * {@code Spinner.background} receives the field background.
     * {@code Spinner.foreground} receives the field foreground.
     * {@code OptionPane.background} receives the panel background.
     * {@code OptionPane.messageForeground} and {@code OptionPane.foreground} receive the panel foreground.
     * {@code OptionPane.buttonForeground} receives the panel foreground.
     *
     * {@code PopupMenu.background} receives the panel background.
     * {@code PopupMenu.foreground} receives the menu foreground.
     * {@code Menu.background} and {@code MenuItem.background} receive the panel background.
     * {@code Menu.foreground} and {@code MenuItem.foreground} receive the menu foreground.
     * {@code SplitPane.background} receives the split background.
     * {@code ScrollBar.background} receives the panel background.
     *
     * The key {@code control} receives the panel background.
     * The key {@code text} receives the field foreground.
     * The key {@code nimbusBase} receives the panel background.
     * The key {@code nimbusFocus} receives the button background.
     * {@code SplitPane.border} becomes a raised bevel using the highlight and the shadow.
     * {@code SplitPaneDivider.border} becomes an empty border of zero on every side.
     * {@link #installQuietEdges} runs next.
     * {@link #seatButtonHover} runs last.
     */
    public void installLookAndFeelDefaults() {
        UIManager.put("Panel.background", panelBackground);
        UIManager.put("Panel.foreground", panelForeground);
        UIManager.put("Label.background", panelBackground);
        UIManager.put("Label.foreground", panelForeground);
        UIManager.put("Button.background", buttonBackground);
        UIManager.put("Button.foreground", buttonForeground);
        UIManager.put("TextField.background", fieldBackground);
        UIManager.put("TextField.foreground", fieldForeground);
        UIManager.put("TextField.caretForeground", fieldForeground);
        UIManager.put("PasswordField.background", fieldBackground);
        UIManager.put("PasswordField.foreground", fieldForeground);
        UIManager.put("PasswordField.caretForeground", fieldForeground);
        UIManager.put("ComboBox.background", fieldBackground);
        UIManager.put("ComboBox.foreground", fieldForeground);
        UIManager.put("CheckBox.background", panelBackground);
        UIManager.put("CheckBox.foreground", panelForeground);
        UIManager.put("Spinner.background", fieldBackground);
        UIManager.put("Spinner.foreground", fieldForeground);
        UIManager.put("OptionPane.background", panelBackground);
        UIManager.put("OptionPane.messageForeground", panelForeground);
        UIManager.put("OptionPane.foreground", panelForeground);
        // Modal buttons take the panel foreground.
        // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
        // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
        // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
        // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
        // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
        // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
        // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
        // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
        // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
        // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
        UIManager.put("OptionPane.buttonForeground", panelForeground);
        UIManager.put("PopupMenu.background", panelBackground);
        UIManager.put("PopupMenu.foreground", menuForeground);
        UIManager.put("Menu.background", panelBackground);
        UIManager.put("Menu.foreground", menuForeground);
        UIManager.put("MenuItem.background", panelBackground);
        UIManager.put("MenuItem.foreground", menuForeground);
        UIManager.put("SplitPane.background", splitBackground);
        UIManager.put("ScrollBar.background", panelBackground);
        UIManager.put("control", panelBackground);
        UIManager.put("text", fieldForeground);
        UIManager.put("nimbusBase", panelBackground);
        UIManager.put("nimbusFocus", buttonBackground);
        UIManager.put("SplitPane.border", new BevelBorder(
                BevelBorder.RAISED, BEVEL_HIGHLIGHT, BEVEL_SHADOW));
        UIManager.put("SplitPaneDivider.border", new EmptyBorder(0, 0, 0, 0));
        installQuietEdges();
        seatButtonHover();
    }

    /**
     * The {@code seatButtonHover} method installs the button hover colors once.
     * A second call returns immediately.
     * The shared tooltip delay grows by fifty milliseconds.
     * One listener watches mouse enter and mouse exit.
     * Another listener watches a component being added.
     */
    private void seatButtonHover() {
        if (hoverSeatInstalled) {
            return;
        }
        hoverSeatInstalled = true;
        ToolTipManager tips = ToolTipManager.sharedInstance();
        tips.setInitialDelay(tips.getInitialDelay() + 50);
        /**
         * The listener turns a button word green while the pointer is on it.
         * An event that is not a mouse event returns immediately.
         * An event other than enter or exit returns immediately.
         * A source that is not a button returns immediately.
         *
         * A plain button is accepted.
         * A toggle button is accepted when it is not a check box.
         * Any other button returns immediately.
         * Entering a disabled button returns immediately.
         * Entering a button with no saved ink stores the current foreground under {@code aetheris.button.ink}.
         * The foreground then becomes red 0, green 255, blue 102.
         *
         * Leaving a button restores a saved color.
         * A saved value that is not a color is left unused.
         * The saved ink key is cleared on exit.
         */
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            // @aa-sdd PK-0045 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-3663 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void eventDispatched(AWTEvent event) {
                if (!(event instanceof MouseEvent)) {
                    return;
                }
                MouseEvent mouse = (MouseEvent) event;
                int id = mouse.getID();
                if (id != MouseEvent.MOUSE_ENTERED && id != MouseEvent.MOUSE_EXITED) {
                    return;
                }
                Object source = event.getSource();
                if (!(source instanceof AbstractButton)) {
                    return;
                }
                AbstractButton button = (AbstractButton) source;
                boolean mark = button instanceof JToggleButton && !(button instanceof JCheckBox);
                if (!(button instanceof JButton) && !mark) {
                    return;
                }
                if (id == MouseEvent.MOUSE_ENTERED) {
                    if (!button.isEnabled()) {
                        return;
                    }
                    if (button.getClientProperty(BUTTON_INK) == null) {
                        button.putClientProperty(BUTTON_INK, button.getForeground());
                    }
                    button.setForeground(BUTTON_HOVER_INK);
                    return;
                }
                Object saved = button.getClientProperty(BUTTON_INK);
                if (saved instanceof Color) {
                    button.setForeground((Color) saved);
                }
                button.putClientProperty(BUTTON_INK, null);
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
        /**
         * The listener paints a command face on a button that just joined a container.
         * An event that is not a container event returns immediately.
         * An event other than a component add returns immediately.
         * A child that is not a button returns immediately.
         * The face is applied later on the Swing event thread.
         */
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            // @aa-sdd PK-3677 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void eventDispatched(AWTEvent event) {
                if (!(event instanceof ContainerEvent)) {
                    return;
                }
                if (event.getID() != ContainerEvent.COMPONENT_ADDED) {
                    return;
                }
                Component child = ((ContainerEvent) event).getChild();
                if (!(child instanceof AbstractButton)) {
                    return;
                }
                final AbstractButton button = (AbstractButton) child;
                /**
                 * The runnable applies the command face.
                 * {@link #seatPlateFace} receives the button that was added.
                 */
                SwingUtilities.invokeLater(new Runnable() {
                    // @aa-sdd PK-3678 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                    @Override
                    public void run() {
                        seatPlateFace(button);
                    }
                });
            }
        }, AWTEvent.CONTAINER_EVENT_MASK);
    }

    /**
     * The {@code seatPlateFace} method gives a command button the plate face.
     * {@link #plateSkip} leaves a button that already has its own face.
     * A button with no saved ink uses the theme button foreground.
     * The background becomes the idle plate color.
     * The button is not opaque.
     * The content area is not filled by the stock painter.
     * The focus ring is not painted.
     * Rollover is enabled.
     * The border is an empty border of four, ten, four, and ten.
     * A button that is not already using {@link PlateFaceUi} receives that painter.
     *
     * @param button The button that just joined a container.
     */
    private void seatPlateFace(AbstractButton button) {
        if (plateSkip(button)) {
            return;
        }
        if (button.getClientProperty(BUTTON_INK) == null) {
            button.setForeground(buttonForeground);
        }
        button.setBackground(PLATE_IDLE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);
        button.setBorder(new EmptyBorder(4, 10, 4, 10));
        if (!(button.getUI() instanceof PlateFaceUi)) {
            button.setUI(new PlateFaceUi());
        }
    }

    /**
     * The {@code plateSkip} method reports a button that should keep its own face.
     * A component that is neither a plain button nor a toggle button returns true.
     * A check box, a radio button, or a menu item returns true.
     * A true {@code aetheris.rust} property returns true.
     * A true {@code aetheris.mark} property returns true.
     * A true {@code aetheris.choice} property returns true.
     *
     * A non-null {@code aetheris.plate.selected} property returns true.
     * A button whose painter is {@link PlateButtonUi} returns true.
     * A class name that contains {@code Arrow} returns true.
     * A preferred size of zero width and zero height returns true.
     * Every other button returns false.
     *
     * @param button The candidate button.
     * @return True when this button keeps its own face.
     */
    private static boolean plateSkip(AbstractButton button) {
        if (!(button instanceof JButton) && !(button instanceof JToggleButton)) {
            return true;
        }
        if (button instanceof JCheckBox || button instanceof JRadioButton || button instanceof JMenuItem) {
            return true;
        }
        if (Boolean.TRUE.equals(button.getClientProperty("aetheris.rust"))) {
            return true;
        }
        if (Boolean.TRUE.equals(button.getClientProperty("aetheris.mark"))) {
            return true;
        }
        if (Boolean.TRUE.equals(button.getClientProperty("aetheris.choice"))) {
            return true;
        }
        if (button.getClientProperty("aetheris.plate.selected") != null) {
            return true;
        }
        if (button.getUI() instanceof PlateButtonUi) {
            return true;
        }
        String typeName = button.getClass().getName();
        if (typeName.indexOf("Arrow") >= 0) {
            return true;
        }
        Dimension size = button.getPreferredSize();
        if (size != null && size.width == 0 && size.height == 0) {
            return true;
        }
        return false;
    }

    /**
     * The {@code installQuietEdges} method puts the quiet grey on Metal edges.
     * The current Metal theme becomes a {@link QuietOcean}.
     * The raised bevel uses the highlight and the shadow.
     * The line border uses the highlight.
     *
     * {@code TabbedPane.borderHightlightColor} receives the highlight.
     * {@code TabbedPane.contentAreaColor} receives the highlight.
     * {@code TabbedPane.unselectedBackground} receives the highlight.
     * {@code TabbedPane.tabAreaBackground} receives the highlight.
     * {@code TabbedPane.background} receives the highlight.
     * {@code TabbedPane.light}, {@code TabbedPane.highlight}, {@code TabbedPane.selectHighlight}, and {@code TabbedPane.focus} receive the highlight.
     * {@code TabbedPane.selected} receives the panel background.
     * {@code TabbedPane.shadow} and {@code TabbedPane.darkShadow} receive the shadow.
     *
     * {@code MenuBar.borderColor} receives the highlight.
     * {@code PopupMenu.border}, {@code Menu.border}, and {@code MenuItem.border} receive the line border.
     * {@code Menu.selectionBackground} and {@code MenuItem.selectionBackground} receive the panel background.
     * {@code Menu.selectionForeground} and {@code MenuItem.selectionForeground} receive the menu foreground.
     * {@code Button.border} receives the raised bevel.
     * {@code Spinner.border} receives the line border.
     * {@code Spinner.arrowButtonBorder} receives the raised bevel.
     *
     * {@code TextField.border}, {@code PasswordField.border}, and {@code FormattedTextField.border} receive the line border.
     * {@code CheckBox.gradient} receives {@link #quietFill}.
     * {@code CheckBox.rollover} is false.
     * {@code ComboBox.selectionBackground} receives the panel background.
     * {@code ComboBox.selectionForeground} receives the field foreground.
     * {@code List.focusCellHighlightBorder} and {@code Table.focusCellHighlightBorder} receive the line border.
     *
     * {@code RootPane.plainDialogBorder} receives the line border.
     * {@code RootPane.informationDialogBorder} receives the line border.
     * {@code RootPane.errorDialogBorder} receives the line border.
     * {@code RootPane.questionDialogBorder} receives the line border.
     * {@code RootPane.warningDialogBorder} receives the line border.
     * {@code RootPane.fileChooserDialogBorder} receives the line border.
     * {@code RootPane.colorChooserDialogBorder} receives the line border.
     */
    private void installQuietEdges() {
        MetalLookAndFeel.setCurrentTheme(new QuietOcean());
        BevelBorder bevel = new BevelBorder(BevelBorder.RAISED, BEVEL_HIGHLIGHT, BEVEL_SHADOW);
        LineBorder lip = new LineBorder(BEVEL_HIGHLIGHT);
        UIManager.put("TabbedPane.borderHightlightColor", BEVEL_HIGHLIGHT);
        UIManager.put("TabbedPane.contentAreaColor", BEVEL_HIGHLIGHT);
        UIManager.put("TabbedPane.unselectedBackground", BEVEL_HIGHLIGHT);
        UIManager.put("TabbedPane.tabAreaBackground", BEVEL_HIGHLIGHT);
        UIManager.put("TabbedPane.background", BEVEL_HIGHLIGHT);
        UIManager.put("TabbedPane.light", BEVEL_HIGHLIGHT);
        UIManager.put("TabbedPane.highlight", BEVEL_HIGHLIGHT);
        UIManager.put("TabbedPane.selectHighlight", BEVEL_HIGHLIGHT);
        UIManager.put("TabbedPane.focus", BEVEL_HIGHLIGHT);
        UIManager.put("TabbedPane.selected", panelBackground);
        UIManager.put("TabbedPane.shadow", BEVEL_SHADOW);
        UIManager.put("TabbedPane.darkShadow", BEVEL_SHADOW);
        UIManager.put("MenuBar.borderColor", BEVEL_HIGHLIGHT);
        UIManager.put("PopupMenu.border", lip);
        UIManager.put("Menu.border", lip);
        UIManager.put("MenuItem.border", lip);
        UIManager.put("Menu.selectionBackground", panelBackground);
        UIManager.put("MenuItem.selectionBackground", panelBackground);
        UIManager.put("Menu.selectionForeground", menuForeground);
        UIManager.put("MenuItem.selectionForeground", menuForeground);
        UIManager.put("Button.border", bevel);
        UIManager.put("Spinner.border", lip);
        UIManager.put("Spinner.arrowButtonBorder", bevel);
        UIManager.put("TextField.border", lip);
        UIManager.put("PasswordField.border", lip);
        UIManager.put("FormattedTextField.border", lip);
        UIManager.put("CheckBox.gradient", quietFill());
        UIManager.put("CheckBox.rollover", Boolean.FALSE);
        UIManager.put("ComboBox.selectionBackground", panelBackground);
        UIManager.put("ComboBox.selectionForeground", fieldForeground);
        UIManager.put("List.focusCellHighlightBorder", lip);
        UIManager.put("Table.focusCellHighlightBorder", lip);
        UIManager.put("RootPane.plainDialogBorder", lip);
        UIManager.put("RootPane.informationDialogBorder", lip);
        UIManager.put("RootPane.errorDialogBorder", lip);
        UIManager.put("RootPane.questionDialogBorder", lip);
        UIManager.put("RootPane.warningDialogBorder", lip);
        UIManager.put("RootPane.fileChooserDialogBorder", lip);
        UIManager.put("RootPane.colorChooserDialogBorder", lip);
    }

    /**
     * The {@code quietFill} method builds the flat check-box gradient.
     * The first value is {@code 0.3}.
     * The second value is {@code 0}.
     * The next three values are the bevel highlight.
     *
     * @return The gradient list Metal reads.
     */
    private static List<Object> quietFill() {
        List<Object> fill = new ArrayList<Object>();
        fill.add(Float.valueOf(0.3f));
        fill.add(Float.valueOf(0f));
        fill.add(BEVEL_HIGHLIGHT);
        fill.add(BEVEL_HIGHLIGHT);
        fill.add(BEVEL_HIGHLIGHT);
        return fill;
    }

    /**
     * The {@code darkGround} method replaces an old default ground.
     * The comparison ignores the alpha byte.
     * A loaded color whose remaining bytes match {@code oldRgb} returns {@code nextRgb} as an opaque color.
     * Any other loaded color is returned unchanged.
     *
     * @param loaded The color from the properties store.
     * @param oldRgb The previous default, without alpha.
     * @param nextRgb The dark ground, without alpha.
     * @return The dark ground, or the loaded color when it is not the old default.
     */
    private static Color darkGround(Color loaded, int oldRgb, int nextRgb) {
        if ((loaded.getRGB() & 0x00FFFFFF) == (oldRgb & 0x00FFFFFF)) {
            return new Color(nextRgb);
        }
        return loaded;
    }

    /**
     * The {@code getFrameBackground} method returns the frame background.
     * @return The frame background.
     */
    public Color getFrameBackground() {
        return frameBackground;
    }

    /**
     * The {@code getPanelBackground} method returns the panel background.
     * @return The panel background.
     */
    public Color getPanelBackground() {
        return panelBackground;
    }

    /**
     * The {@code getPanelForeground} method returns the panel foreground.
     * @return The panel foreground.
     */
    public Color getPanelForeground() {
        return panelForeground;
    }

    /**
     * The {@code getStatusBackground} method returns the status background.
     * @return The status background.
     */
    public Color getStatusBackground() {
        return statusBackground;
    }

    /**
     * The {@code getStatusForeground} method returns the status foreground.
     * @return The status foreground.
     */
    public Color getStatusForeground() {
        return statusForeground;
    }

    /**
     * The {@code getSplitBackground} method returns the split background.
     * @return The split background.
     */
    public Color getSplitBackground() {
        return splitBackground;
    }

    /**
     * The {@code getMenuForeground} method returns the menu foreground.
     * @return The menu foreground loaded from the properties.
     */
    public Color getMenuForeground() {
        return menuForeground;
    }

    /**
     * The {@code applyPanel} method paints a panel.
     * The background is the panel background.
     * The foreground is the panel foreground.
     * The panel is opaque.
     *
     * @param panel The panel to paint.
     */
    public void applyPanel(JPanel panel) {
        panel.setBackground(panelBackground);
        panel.setForeground(panelForeground);
        panel.setOpaque(true);
    }

    /**
     * The {@code applyLabel} method paints a label.
     * The background is the panel background.
     * The foreground is the panel foreground.
     * The label is not opaque.
     *
     * @param label The label to paint.
     */
    public void applyLabel(JLabel label) {
        label.setBackground(panelBackground);
        label.setForeground(panelForeground);
        label.setOpaque(false);
    }

    /**
     * The {@code applyPopup} method paints a context menu.
     * A null menu returns immediately.
     * The menu is opaque.
     * The background is the panel background.
     * The foreground is the menu foreground.
     * The border is a line in the button background.
     * The painter is a basic popup painter.
     * {@link #paintMenuComponent} then paints every child.
     *
     * @param menu The menu to paint.
     */
    public void applyPopup(JPopupMenu menu) {
        if (menu == null) {
            return;
        }
        menu.setOpaque(true);
        menu.setBackground(panelBackground);
        menu.setForeground(menuForeground);
        menu.setBorder(new LineBorder(buttonBackground));
        menu.setUI(new BasicPopupMenuUI());
        Component[] items = menu.getComponents();
        for (int i = 0; i < items.length; i++) {
            paintMenuComponent(items[i]);
        }
    }

    /**
     * The {@code paintMenuComponent} method paints one context-menu row.
     * A nested menu is not opaque.
     * That menu uses the panel background and the menu foreground.
     * {@link #seatMenuHover} installs its hover tab.
     * {@link #applyPopup} paints the nested popup.
     *
     * A menu item that is not a menu uses the same background and foreground.
     * That item is not opaque.
     * An item whose class is exactly {@code JMenuItem} receives the hover tab.
     * A subclass of {@code JMenuItem} does not receive that tab.
     * A separator uses the panel background and the menu foreground.
     * Any other component is left unchanged.
     *
     * @param component The row to paint.
     */
    private void paintMenuComponent(Component component) {
        if (component instanceof JMenu) {
            JMenu nested = (JMenu) component;
            nested.setOpaque(false);
            nested.setBackground(panelBackground);
            nested.setForeground(menuForeground);
            seatMenuHover(nested);
            applyPopup(nested.getPopupMenu());
            return;
        }
        if (component instanceof JMenuItem) {
            JMenuItem item = (JMenuItem) component;
            item.setOpaque(false);
            item.setBackground(panelBackground);
            item.setForeground(menuForeground);
            if (component.getClass() == JMenuItem.class) {
                seatMenuHover(item);
            }
            return;
        }
        if (component instanceof JSeparator) {
            component.setBackground(panelBackground);
            component.setForeground(menuForeground);
        }
    }

    /**
     * The {@code applyTextField} method paints a text field.
     * The background is the field background.
     * The foreground, the caret, and the selected text use the field colors.
     * The selection color is the button background.
     * The selected text color is the button foreground.
     * The border is a button-background line around an empty inset of three, four, three, and four.
     * The field is opaque.
     *
     * @param field The field to paint.
     */
    public void applyTextField(JTextField field) {
        field.setBackground(fieldBackground);
        field.setForeground(fieldForeground);
        field.setCaretColor(fieldForeground);
        field.setSelectionColor(buttonBackground);
        field.setSelectedTextColor(buttonForeground);
        field.setBorder(new CompoundBorder(new LineBorder(buttonBackground), new EmptyBorder(3, 4, 3, 4)));
        field.setOpaque(true);
    }

    /**
     * The {@code applyPasswordField} method paints a password field.
     * The field is passed to {@link #applyTextField}.
     *
     * @param field The password field to paint.
     */
    public void applyPasswordField(JPasswordField field) {
        applyTextField(field);
    }

    /**
     * The {@code applyButton} method paints a button with the plate face.
     * {@link #seatPlateFace} does the work.
     *
     * @param button The button to paint.
     */
    public void applyButton(javax.swing.AbstractButton button) {
        seatPlateFace(button);
    }

    /**
     * The {@code applyFlatButtonBorder} method sets the flat button border.
     * The outer line uses the field background.
     * The inner empty border is four, ten, four, and ten.
     *
     * @param button The button whose border is replaced.
     */
    public void applyFlatButtonBorder(javax.swing.AbstractButton button) {
        button.setBorder(new CompoundBorder(new LineBorder(fieldBackground), new EmptyBorder(4, 10, 4, 10)));
    }

    /**
     * The {@code applyScrollPane} method arms the overlay scroll bars.
     * {@link AppleScrollBar#install} receives the pane.
     *
     * @param pane The scroll pane.
     */
    public void applyScrollPane(JScrollPane pane) {
        AppleScrollBar.install(pane);
    }

    /**
     * The {@code applyCanvasScroll} method paints the scroll pane under the canvas tab.
     * A null pane returns immediately.
     * {@link #applyScrollPane} arms the overlay bars.
     * The pane background is the panel background.
     * The border is a raised bevel.
     * The lit edge is the bevel highlight.
     * The dark edge is the bevel shadow.
     * The viewport border is removed.
     *
     * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     *
     * @param pane The scroll pane under the canvas tab.
     */
    public void applyCanvasScroll(JScrollPane pane) {
        if (pane == null) {
            return;
        }
        applyScrollPane(pane);
        pane.setBackground(getPanelBackground());
        pane.setBorder(new BevelBorder(BevelBorder.RAISED, BEVEL_HIGHLIGHT, BEVEL_SHADOW));
        pane.setViewportBorder(null);
    }

    /**
     * The {@code applyCheckBox} method paints a check box.
     * The background is the panel background.
     * The foreground is the panel foreground.
     * The box is opaque.
     * The focus ring is not painted.
     * One {@link SapphireCheck} is used for the normal, selected, rollover, pressed, and disabled icons.
     *
     * @param checkBox The check box to paint.
     */
    public void applyCheckBox(JCheckBox checkBox) {
        checkBox.setBackground(panelBackground);
        checkBox.setForeground(panelForeground);
        checkBox.setOpaque(true);
        checkBox.setFocusPainted(false);
        SapphireCheck check = new SapphireCheck();
        checkBox.setIcon(check);
        checkBox.setSelectedIcon(check);
        checkBox.setRolloverIcon(check);
        checkBox.setRolloverSelectedIcon(check);
        checkBox.setPressedIcon(check);
        checkBox.setDisabledIcon(check);
        checkBox.setDisabledSelectedIcon(check);
    }

    /**
     * The {@code applyMenuBar} method paints the window menu.
     * A null bar returns immediately.
     * The bar is opaque.
     * The background is the panel background.
     * Each menu item is not opaque.
     * Each menu item uses the menu foreground.
     * {@link #seatMenuHover} installs the grey hover tab on each menu item.
     * A child that is not a menu item is skipped.
     *
     * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     *
     * @param bar The menu bar.
     */
    public void applyMenuBar(JMenuBar bar) {
        if (bar == null) {
            return;
        }
        bar.setOpaque(true);
        bar.setBackground(panelBackground);
        Component[] items = bar.getComponents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] instanceof JMenuItem) {
                JMenuItem item = (JMenuItem) items[i];
                item.setOpaque(false);
                item.setForeground(menuForeground);
                seatMenuHover(item);
            }
        }
    }

    /**
     * The {@code seatMenuHover} method installs the grey hover tab.
     * Rollover is enabled.
     * A menu receives {@link HoverMenuUi}.
     * Any other item receives {@link HoverMenuItemUi}.
     *
     * @param item The menu title or the menu row.
     */
    private static void seatMenuHover(JMenuItem item) {
        item.setRolloverEnabled(true);
        if (item instanceof JMenu) {
            item.setUI(new HoverMenuUi());
        } else {
            item.setUI(new HoverMenuItemUi());
        }
    }

    /**
     * The {@code applyComboBox} method paints a combo box.
     * The background is the field background.
     * The foreground is the field foreground.
     * The combo box is opaque.
     * {@link #seatComboArrow} replaces the arrow.
     *
     * @param comboBox The combo box to paint.
     */
    public void applyComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(fieldBackground);
        comboBox.setForeground(fieldForeground);
        comboBox.setOpaque(true);
        seatComboArrow(comboBox);
    }

    /**
     * The {@code seatComboArrow} method replaces each Metal arrow button.
     * A child that is not a Metal combo button is skipped.
     * The arrow is a triangle in the hover green.
     * The field colors are left unchanged.
     *
     * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     *
     * @param comboBox The combo box whose arrow is replaced.
     */
    private void seatComboArrow(final JComboBox<?> comboBox) {
        int count = comboBox.getComponentCount();
        for (int i = 0; i < count; i++) {
            Component child = comboBox.getComponent(i);
            if (!(child instanceof MetalComboBoxButton)) {
                continue;
            }
            MetalComboBoxButton button = (MetalComboBoxButton) child;
            /**
             * The icon paints a downward triangle.
             * The color is red 0, green 255, blue 102.
             * The width is ten pixels.
             * The height is five pixels.
             * The corners are the top left, the top right, and the bottom center.
             * The graphics copy is disposed.
             */
            button.setComboIcon(new Icon() {
                // @aa-sdd PK-0112 docs/specs/java/org/aetheris/swing/swing.spec.md
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D pen = (Graphics2D) g.create();
                    pen.setColor(BUTTON_HOVER_INK);
                    int width = getIconWidth();
                    int height = getIconHeight();
                    int[] xs = new int[] { x, x + width - 1, x + (width / 2) };
                    int[] ys = new int[] { y, y, y + height - 1 };
                    pen.fillPolygon(xs, ys, 3);
                    pen.dispose();
                }

                @Override
                public int getIconWidth() {
                    return 10;
                }

                @Override
                public int getIconHeight() {
                    return 5;
                }
            });
        }
    }

    /**
     * The {@code applySpinner} method paints a spinner.
     * The background is the field background.
     * The foreground is the field foreground.
     * The spinner is opaque.
     * A default editor has its text field passed to {@link #applyTextField}.
     * Any other editor is left unchanged.
     *
     * @param spinner The spinner to paint.
     */
    public void applySpinner(JSpinner spinner) {
        spinner.setBackground(fieldBackground);
        spinner.setForeground(fieldForeground);
        spinner.setOpaque(true);
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField field = ((JSpinner.DefaultEditor) editor).getTextField();
            applyTextField(field);
        }
    }

    /**
     * The {@code applySplitPane} method paints a split pane.
     * The background is the split background.
     * The pane is opaque.
     * The border is removed.
     * One-touch expansion is turned on.
     * The left and right components are remembered.
     * The painter becomes a {@link DarkSplitUi}.
     * A non-null left component is put back.
     * A non-null right component is put back.
     * One-touch expansion is turned on again.
     * The divider size becomes {@link #DIVIDER_SIZE}.
     *
     * @param splitPane The split pane to paint.
     */
    public void applySplitPane(JSplitPane splitPane) {
        splitPane.setBackground(splitBackground);
        splitPane.setOpaque(true);
        splitPane.setBorder(null);
        splitPane.setOneTouchExpandable(true);
        Component left = splitPane.getLeftComponent();
        Component right = splitPane.getRightComponent();
        splitPane.setUI(new DarkSplitUi(this));
        if (left != null) {
            splitPane.setLeftComponent(left);
        }
        if (right != null) {
            splitPane.setRightComponent(right);
        }
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerSize(DIVIDER_SIZE);
    }

    /**
     * The {@code installPlateTabs} method installs the plate tab painter.
     * The painter is a {@link PlateTabUi}.
     * The strip is opaque.
     * The background is red 13, green 13, blue 13.
     * The border is removed.
     *
     * @param tabs The product tabs or the canvas tabs.
     */
    public void installPlateTabs(JTabbedPane tabs) {
        tabs.setUI(new PlateTabUi());
        tabs.setOpaque(true);
        tabs.setBackground(PLATE);
        tabs.setBorder(null);
    }

    /**
     * The {@code installPlateButton} method paints a folded SSH or Aetheris button.
     * The property {@code aetheris.plate.selected} stores the selected flag.
     * The button is not opaque.
     * The content area is not filled by the stock painter.
     * The focus ring is not painted.
     * The border is an empty border of two, eight, two, and eight.
     * Rollover is enabled.
     * A button that is not already using {@link PlateButtonUi} receives that painter.
     * A button that already uses it is repainted.
     *
     * @param button The folded product button.
     * @param selected True when this button matches the selected tab.
     */
    public void installPlateButton(JButton button, boolean selected) {
        button.putClientProperty("aetheris.plate.selected", Boolean.valueOf(selected));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(2, 8, 2, 8));
        button.setRolloverEnabled(true);
        if (!(button.getUI() instanceof PlateButtonUi)) {
            button.setUI(new PlateButtonUi());
        } else {
            button.repaint();
        }
    }

    // @aa-sdd PK-3693 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code DarkSplitUi} builds the dark divider.
     * The bar fill comes from the theme, or from the SSH divider color.
     * The edges use that same fill.
     */
    private static final class DarkSplitUi extends BasicSplitPaneUI {

        private final SshClientTheme theme;

        /**
         * The constructor stores the theme.
         * The divider is created later.
         *
         * @param theme The theme that supplies the split background.
         */
        private DarkSplitUi(SshClientTheme theme) {
            this.theme = theme;
        }

        /**
         * The {@code createDefaultDivider} method builds the grab bar.
         * The property {@code ssh.pane.split} marks an SSH terminal split.
         * A null split pane is not an SSH split.
         * A true property selects the SSH divider color.
         * Any other pane uses the theme split background.
         *
         * @return The dark divider.
         */
        @Override
        public BasicSplitPaneDivider createDefaultDivider() {
            JSplitPane pane = getSplitPane();
            boolean ssh = pane != null
                    && Boolean.TRUE.equals(pane.getClientProperty("ssh.pane.split"));
            return new DarkDivider(this, theme, ssh);
        }
    }

    /**
     * The {@code SSH_DIVIDER} color is the SSH terminal split bar.
     * The red, green, and blue values are 58, 58, and 58.
     *
     * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     */
    private static final Color SSH_DIVIDER = new Color(0x3a, 0x3a, 0x3a);

    // @aa-sdd PK-3667 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-3694 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-3651 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code DarkDivider} paints the grab bar.
     * An SSH terminal split uses {@link SshClientTheme} divider color {@code 0x3a3a3a}.
     * Any other split uses the theme split background.
     * The border stays off.
     */
    private static final class DarkDivider extends BasicSplitPaneDivider {

        private final Color fill;

        /**
         * The constructor chooses the fill and clears the border.
         * A true SSH flag uses the SSH divider color.
         * A false flag uses the theme split background.
         * The background is that fill.
         *
         * @param ui The split painter that owns this divider.
         * @param theme The theme that supplies the ordinary split color.
         * @param sshSplit True for an SSH terminal split.
         */
        private DarkDivider(BasicSplitPaneUI ui, SshClientTheme theme, boolean sshSplit) {
            super(ui);
            this.fill = sshSplit ? SSH_DIVIDER : theme.getSplitBackground();
            setBackground(this.fill);
            setBorder(null);
        }

        /**
         * The {@code paint} method fills the grab bar.
         * The border is cleared again.
         * The fill covers the full width and height.
         * The graphics copy is disposed.
         * The stock paint then draws the one-touch controls.
         *
         * @param g The graphics context.
         */
        @Override
        public void paint(Graphics g) {
            setBorder(null);
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            g2.setColor(fill);
            g2.fillRect(0, 0, w, h);
            g2.dispose();
            super.paint(g);
        }
    }

    // @aa-sdd PK-3654 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code QuietOcean} theme keeps Metal and replaces the light edges.
     * The secondary colors and the white color become the bevel highlight.
     * Disabled menu text uses the inactive control text color.
     * Disabled control color uses that same inactive text color.
     */
    private static final class QuietOcean extends OceanTheme {

        private final ColorUIResource lip = new ColorUIResource(BEVEL_HIGHLIGHT);

        /**
         * The {@code getSecondary1} method returns the bevel highlight.
         * @return The quiet edge color.
         */
        @Override
        protected ColorUIResource getSecondary1() {
            return lip;
        }

        /**
         * The {@code getSecondary2} method returns the bevel highlight.
         * @return The quiet edge color.
         */
        @Override
        protected ColorUIResource getSecondary2() {
            return lip;
        }

        /**
         * The {@code getSecondary3} method returns the bevel highlight.
         * @return The quiet edge color.
         */
        @Override
        protected ColorUIResource getSecondary3() {
            return lip;
        }

        /**
         * The {@code getWhite} method returns the bevel highlight.
         * @return The quiet edge color.
         */
        @Override
        protected ColorUIResource getWhite() {
            return lip;
        }

        /**
         * The {@code getInactiveSystemTextColor} method returns the inactive control text color.
         * The value comes from the Metal theme.
         *
         * @return The inactive control text color.
         */
        @Override
        public ColorUIResource getInactiveSystemTextColor() {
            return getInactiveControlTextColor();
        }

        /**
         * The {@code getMenuDisabledForeground} method returns the inactive control text color.
         *
         * @return The inactive control text color.
         */
        @Override
        public ColorUIResource getMenuDisabledForeground() {
            return getInactiveControlTextColor();
        }

        /**
         * The {@code getControlDisabled} method returns the inactive control text color.
         *
         * @return The inactive control text color.
         */
        @Override
        public ColorUIResource getControlDisabled() {
            return getInactiveControlTextColor();
        }
    }

    /**
     * The {@code PLATE} color is the tab strip behind the product words.
     * The red, green, and blue values are 13, 13, and 13.
     */
    private static final Color PLATE = new Color(0x0d, 0x0d, 0x0d);
    /**
     * The {@code PLATE_SELECT} color is the selected tab fill.
     * The red, green, and blue values are 0, 92, and 128.
     */
    private static final Color PLATE_SELECT = new Color(0x00, 0x5c, 0x80);
    /**
     * The {@code PLATE_SELECT_INK} color is the selected tab word.
     * The red, green, and blue values are 192, 222, and 255.
     */
    private static final Color PLATE_SELECT_INK = new Color(0xc0, 0xde, 0xff);
    /**
     * The {@code PLATE_IDLE} color is the idle tab fill.
     * The red, green, and blue values are 50, 50, and 50.
     */
    private static final Color PLATE_IDLE = new Color(0x32, 0x32, 0x32);
    /**
     * The {@code PLATE_IDLE_INK} color is the idle tab word.
     * The red, green, and blue values are 215, 215, and 215.
     */
    private static final Color PLATE_IDLE_INK = new Color(0xd7, 0xd7, 0xd7);
    /**
     * The {@code PLATE_HOVER} color is the hover fill.
     * The red, green, and blue values are 64, 64, and 64.
     */
    private static final Color PLATE_HOVER = new Color(0x40, 0x40, 0x40);
    /**
     * The {@code PLATE_HOVER_EDGE} color is the hover outline.
     * The red, green, and blue values are 150, 150, and 150.
     */
    private static final Color PLATE_HOVER_EDGE = new Color(0x96, 0x96, 0x96);
    /**
     * The {@code PLATE_HOVER_INK} color is the hover word.
     * The red, green, and blue values are 245, 245, and 245.
     */
    private static final Color PLATE_HOVER_INK = new Color(0xf5, 0xf5, 0xf5);
    /**
     * The {@code PLATE_PRESS} color is the pressed command fill.
     * The red, green, and blue values are 74, 74, and 74.
     * The word color is not changed by this fill.
     */
    private static final Color PLATE_PRESS = new Color(0x4a, 0x4a, 0x4a);

    // @aa-sdd PK-4105 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-4657 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    /**
     * The {@code PlateTabUi} paints the product tabs.
     * A selected tab uses the sapphire fill.
     * An idle tab uses the grey fill.
     * A hover on an unselected tab lightens that face and draws an edge.
     * A disabled tab is drawn at half opacity.
     * The content border is not painted.
     */
    private static final class PlateTabUi extends BasicTabbedPaneUI {

        /**
         * The {@code installDefaults} method keeps the stock tab defaults and then sets the insets.
         * The content border inset is zero on every side.
         * The tab inset is two, eight, two, and eight.
         * The selected-tab pad is zero on every side.
         * The tab-area inset is two, two, zero, and two.
         */
        @Override
        protected void installDefaults() {
            super.installDefaults();
            contentBorderInsets = new Insets(0, 0, 0, 0);
            tabInsets = new Insets(2, 8, 2, 8);
            selectedTabPadInsets = new Insets(0, 0, 0, 0);
            tabAreaInsets = new Insets(2, 2, 0, 2);
        }

        /**
         * The {@code paintTabArea} method fills the strip and then paints the tabs.
         * A null tab pane skips the fill.
         * The fill is red 13, green 13, blue 13.
         * The rectangle starts inside the pane insets.
         * The height is the tab-area height.
         * A bottom placement moves the rectangle up from the bottom inset.
         * A negative width or height is painted as zero.
         * The stock tab paint runs after the fill.
         *
         * @param g The graphics context.
         * @param tabPlacement The tab edge.
         * @param selectedIndex The selected tab.
         */
        @Override
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
            if (tabPane != null) {
                Insets insets = tabPane.getInsets();
                int x = insets.left;
                int y = insets.top;
                int w = tabPane.getWidth() - insets.left - insets.right;
                int h = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                if (tabPlacement == BOTTOM) {
                    y = tabPane.getHeight() - insets.bottom - h;
                }
                g.setColor(PLATE);
                g.fillRect(x, y, Math.max(0, w), Math.max(0, h));
            }
            super.paintTabArea(g, tabPlacement, selectedIndex);
        }

        /**
         * The {@code paintTabBackground} method paints one rounded tab.
         * Antialiasing is turned on.
         * {@link #plateAlpha} halves a disabled tab.
         * A selected tab uses the sapphire fill.
         * An unselected hover uses the hover fill.
         * Any other tab uses the idle fill.
         * The corner arc is twelve pixels.
         * An unselected hover also strokes the hover edge with a one-pixel stroke.
         * The graphics copy is disposed.
         *
         * @param g The graphics context.
         * @param tabPlacement The tab edge. This method does not read it.
         * @param tabIndex The tab being painted.
         * @param x The left edge.
         * @param y The top edge.
         * @param w The width.
         * @param h The height.
         * @param isSelected True when this tab is selected.
         */
        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                plateAlpha(g2, tabIndex);
                boolean hover = getRolloverTab() == tabIndex;
                Color fill = PLATE_IDLE;
                if (isSelected) {
                    fill = PLATE_SELECT;
                } else if (hover) {
                    fill = PLATE_HOVER;
                }
                RoundRectangle2D box = new RoundRectangle2D.Double(x, y, w, h, 12, 12);
                g2.setColor(fill);
                g2.fill(box);
                if (hover && !isSelected) {
                    g2.setColor(PLATE_HOVER_EDGE);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(box);
                }
            } finally {
                g2.dispose();
            }
        }

        /**
         * The {@code paintTabBorder} method draws no border.
         *
         * @param g The graphics context. This method does not use it.
         * @param tabPlacement The tab edge. This method ignores it.
         * @param tabIndex The tab. This method ignores it.
         * @param x The left edge. This method ignores it.
         * @param y The top edge. This method ignores it.
         * @param w The width. This method ignores it.
         * @param h The height. This method ignores it.
         * @param isSelected The selected flag. This method ignores it.
         */
        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                int x, int y, int w, int h, boolean isSelected) {
        }

        /**
         * The {@code paintContentBorder} method draws nothing.
         *
         * @param g The graphics context. This method does not use it.
         * @param tabPlacement The tab edge. This method ignores it.
         * @param selectedIndex The selected tab. This method ignores it.
         */
        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        }

        /**
         * The {@code paintFocusIndicator} method draws nothing.
         *
         * @param g The graphics context. This method does not use it.
         * @param tabPlacement The tab edge. This method ignores it.
         * @param rects The tab rectangles. This method ignores them.
         * @param tabIndex The tab. This method ignores it.
         * @param iconRect The icon rectangle. This method ignores it.
         * @param textRect The text rectangle. This method ignores it.
         * @param isSelected The selected flag. This method ignores it.
         */
        @Override
        protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects,
                int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        }

        /**
         * The {@code paintText} method paints one tab title.
         * A null title returns immediately.
         * A null text rectangle returns immediately.
         * A null metrics object returns immediately.
         * {@link #plateAlpha} halves a disabled tab.
         * The font is the supplied font.
         *
         * A selected tab uses the sapphire word color.
         * An unselected hover uses the hover word color.
         * Any other tab uses the idle word color.
         * The baseline is the text rectangle's top plus the font ascent.
         * The text starts at the text rectangle's left.
         * The graphics copy is disposed.
         *
         * @param g The graphics context.
         * @param tabPlacement The tab edge. This method does not read it.
         * @param font The tab font.
         * @param metrics The font metrics.
         * @param tabIndex The tab being painted.
         * @param title The tab title.
         * @param textRect The text rectangle.
         * @param isSelected True when this tab is selected.
         */
        @Override
        protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics,
                int tabIndex, String title, Rectangle textRect, boolean isSelected) {
            if (title == null || textRect == null || metrics == null) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                plateAlpha(g2, tabIndex);
                g2.setFont(font);
                boolean hover = getRolloverTab() == tabIndex;
                Color ink = PLATE_IDLE_INK;
                if (isSelected) {
                    ink = PLATE_SELECT_INK;
                } else if (hover) {
                    ink = PLATE_HOVER_INK;
                }
                g2.setColor(ink);
                int baseline = textRect.y + metrics.getAscent();
                g2.drawString(title, textRect.x, baseline);
            } finally {
                g2.dispose();
            }
        }

        /**
         * The {@code plateAlpha} method halves a disabled tab.
         * A null tab pane does nothing.
         * An enabled tab does nothing.
         * A disabled tab uses source-over compositing at one half.
         *
         * @param g2 The graphics copy.
         * @param tabIndex The tab to test.
         */
        private void plateAlpha(Graphics2D g2, int tabIndex) {
            if (tabPane != null && !tabPane.isEnabledAt(tabIndex)) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
        }
    }

    // @aa-sdd PK-4106 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-4658 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-3664 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code PlateButtonUi} paints a folded product button.
     * The fills match {@link PlateTabUi}.
     * A saved hover ink replaces the word color.
     * A disabled button is drawn at half opacity.
     */
    private static final class PlateButtonUi extends BasicButtonUI {

        /**
         * The {@code update} method paints the button without the stock background pass.
         * {@link #paint} does the fill and the word.
         *
         * @param g The graphics context.
         * @param c The button.
         */
        @Override
        public void update(Graphics g, JComponent c) {
            paint(g, c);
        }

        /**
         * The {@code paint} method fills the folded button and centers its word.
         * The component is cast to a button.
         * Antialiasing is turned on.
         * A disabled button uses source-over compositing at one half.
         * The property {@code aetheris.plate.selected} selects the sapphire fill and the sapphire word.
         *
         * An unselected rollover uses the hover fill, the hover word, and the hover edge.
         * Any other button uses the idle fill and the idle word.
         * A saved {@code aetheris.button.ink} color replaces the word color with the hover green.
         * The rounded rectangle is inset by half a pixel.
         * The corner arc is twelve pixels.
         * A negative width or height is painted as zero.
         * A hover edge is stroked at one pixel.
         *
         * A null label is painted as an empty string.
         * A missing font skips the word.
         * A present font centers the word in the button.
         * The graphics copy is disposed.
         *
         * @param g The graphics context.
         * @param c The folded button.
         */
        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton button = (AbstractButton) c;
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (!button.isEnabled()) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                }
                boolean selected = Boolean.TRUE.equals(button.getClientProperty("aetheris.plate.selected"));
                ButtonModel model = button.getModel();
                boolean hover = model != null && model.isRollover();
                Color fill = PLATE_IDLE;
                Color ink = PLATE_IDLE_INK;
                Color edge = null;
                if (selected) {
                    fill = PLATE_SELECT;
                    ink = PLATE_SELECT_INK;
                } else if (hover) {
                    fill = PLATE_HOVER;
                    ink = PLATE_HOVER_INK;
                    edge = PLATE_HOVER_EDGE;
                }
                if (button.getClientProperty(BUTTON_INK) instanceof Color) {
                    ink = BUTTON_HOVER_INK;
                }
                int w = c.getWidth();
                int h = c.getHeight();
                RoundRectangle2D box = new RoundRectangle2D.Double(0.5, 0.5, Math.max(0, w - 1), Math.max(0, h - 1), 12, 12);
                g2.setColor(fill);
                g2.fill(box);
                if (edge != null) {
                    g2.setColor(edge);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(box);
                }
                String text = button.getText();
                if (text == null) {
                    text = "";
                }
                Font font = c.getFont();
                if (font != null) {
                    g2.setFont(font);
                    FontMetrics metrics = g2.getFontMetrics(font);
                    int tx = (w - metrics.stringWidth(text)) / 2;
                    int ty = (h - metrics.getHeight()) / 2 + metrics.getAscent();
                    g2.setColor(ink);
                    g2.drawString(text, tx, ty);
                }
            } finally {
                g2.dispose();
            }
        }
    }

    // @aa-sdd PK-3679 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4173 docs/specs/java/org/aetheris/swing/swing.spec.md
    /**
     * The {@code PlateFaceUi} paints a command button.
     * The idle fill is the grey plate.
     * A hover uses the grey fill and the hover edge.
     * A press uses the darker fill.
     * The word stays the button foreground, including the green hover color.
     * A true {@code aetheris.cellmark.seated} property uses the sapphire fill and the sapphire word while the mouse is up.
     */
    private static final class PlateFaceUi extends BasicButtonUI {

        /**
         * The {@code installDefaults} method keeps the stock button defaults.
         * The pressed-text shift is zero.
         *
         * @param b The button receiving the defaults.
         */
        @Override
        protected void installDefaults(AbstractButton b) {
            super.installDefaults(b);
            defaultTextShiftOffset = 0;
        }

        /**
         * The {@code update} method paints the command fill and then the word.
         * Antialiasing is turned on.
         * A hover counts only while the button is enabled.
         * A press counts when the model is armed and pressed.
         * The property {@code aetheris.cellmark.seated} is the sapphire case.
         *
         * A press uses the darker fill and no edge.
         * A hover uses the hover fill and the hover edge.
         * A sapphire property, with the mouse up, uses the sapphire fill.
         * Any other state uses the idle fill.
         * The rounded rectangle is inset by half a pixel.
         * The corner arc is twelve pixels.
         * The graphics copy is disposed before the word is painted.
         *
         * @param g The graphics context.
         * @param c The command button.
         */
        @Override
        public void update(Graphics g, JComponent c) {
            AbstractButton button = (AbstractButton) c;
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ButtonModel model = button.getModel();
                boolean hover = model != null && model.isRollover() && button.isEnabled();
                boolean pressed = model != null && model.isArmed() && model.isPressed();
                boolean seated = Boolean.TRUE.equals(button.getClientProperty("aetheris.cellmark.seated"));
                Color fill = PLATE_IDLE;
                Color edge = null;
                if (pressed) {
                    fill = PLATE_PRESS;
                } else if (hover) {
                    fill = PLATE_HOVER;
                    edge = PLATE_HOVER_EDGE;
                } else if (seated) {
                    fill = PLATE_SELECT;
                }
                int w = c.getWidth();
                int h = c.getHeight();
                RoundRectangle2D box = new RoundRectangle2D.Double(
                        0.5, 0.5, Math.max(0, w - 1), Math.max(0, h - 1), 12, 12);
                g2.setColor(fill);
                g2.fill(box);
                if (edge != null) {
                    g2.setColor(edge);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(box);
                }
            } finally {
                g2.dispose();
            }
            paint(g, c);
        }

        /**
         * The {@code paintText} method paints the command word.
         * A null word returns immediately.
         * An empty word returns immediately.
         * A null text rectangle returns immediately.
         * A present font is applied.
         * A null foreground becomes white.
         *
         * A sapphire property uses the sapphire word while the button is not hovered and not pressed.
         * A disabled button uses source-over compositing at one half.
         * The baseline is the text rectangle's top plus the font ascent.
         * The graphics copy is disposed.
         *
         * @param g The graphics context.
         * @param b The command button.
         * @param textRect The text rectangle.
         * @param text The word to paint.
         */
        @Override
        protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
            if (text == null || text.isEmpty() || textRect == null) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                Font font = b.getFont();
                if (font != null) {
                    g2.setFont(font);
                }
                Color ink = b.getForeground();
                if (ink == null) {
                    ink = Color.WHITE;
                }
                ButtonModel model = b.getModel();
                boolean hover = model != null && model.isRollover() && b.isEnabled();
                boolean pressed = model != null && model.isArmed() && model.isPressed();
                boolean seated = Boolean.TRUE.equals(b.getClientProperty("aetheris.cellmark.seated"));
                if (seated && !hover && !pressed) {
                    ink = PLATE_SELECT_INK;
                }
                if (!b.isEnabled()) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                }
                g2.setColor(ink);
                FontMetrics metrics = g2.getFontMetrics();
                g2.drawString(text, textRect.x, textRect.y + metrics.getAscent());
            } finally {
                g2.dispose();
            }
        }
    }

    // @aa-sdd PK-3685 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code HoverMenuUi} paints a menu title on the bar.
     * A hover paints the grey tab.
     * An open menu paints the same tab.
     */
    private static final class HoverMenuUi extends BasicMenuUI {
        /**
         * The {@code paintBackground} method paints the grey tab.
         * The supplied background color is ignored.
         * {@link SshClientTheme#paintMenuTab} does the paint.
         *
         * @param g The graphics context.
         * @param menuItem The menu title.
         * @param bgColor The stock background. This method ignores it.
         */
        @Override
        protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
            paintMenuTab(g, menuItem);
        }
    }

    // @aa-sdd PK-3686 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code HoverMenuItemUi} paints a row inside a menu.
     * The same grey tab is used on hover.
     */
    private static final class HoverMenuItemUi extends BasicMenuItemUI {
        /**
         * The {@code paintBackground} method paints the grey tab.
         * The supplied background color is ignored.
         * {@link SshClientTheme#paintMenuTab} does the paint.
         *
         * @param g The graphics context.
         * @param menuItem The menu row.
         * @param bgColor The stock background. This method ignores it.
         */
        @Override
        protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
            paintMenuTab(g, menuItem);
        }
    }

    /**
     * The {@code paintMenuTab} method paints the grey hover tab.
     * A null item returns immediately.
     * A null graphics context returns immediately.
     * The tab is painted when the model is in rollover, armed, or selected.
     * An idle row returns without painting, so the bar ground shows through.
     *
     * Antialiasing is turned on.
     * The color is red 70, green 70, blue 70.
     * The rectangle starts one pixel in from the top and the left.
     * The width and the height are two pixels smaller than the item.
     * A negative size is painted as zero.
     * The corner arc is six pixels.
     * The graphics copy is disposed.
     *
     * @param g The menu graphics.
     * @param item The row.
     */
    private static void paintMenuTab(Graphics g, JMenuItem item) {
        if (item == null || g == null) {
            return;
        }
        ButtonModel model = item.getModel();
        boolean hot = model != null && (model.isRollover() || model.isArmed() || model.isSelected());
        if (!hot) {
            return;
        }
        Graphics2D pen = (Graphics2D) g.create();
        try {
            pen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            pen.setColor(MENU_HOVER);
            int w = Math.max(0, item.getWidth() - 2);
            int h = Math.max(0, item.getHeight() - 2);
            pen.fillRoundRect(1, 1, w, h, MENU_HOVER_ARC, MENU_HOVER_ARC);
        } finally {
            pen.dispose();
        }
    }

    /**
     * The {@code SapphireCheck} icon paints the check area.
     * The outline is the sapphire color.
     * The mark is the emerald color.
     * The icon is fourteen pixels on each side.
     *
     * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     */
    private static final class SapphireCheck implements Icon {
        private static final int EDGE = 14;

        /**
         * The {@code paintIcon} method draws the check outline and the mark.
         * Antialiasing is turned on.
         * The outline is inset by one pixel.
         * The outline size is eleven pixels.
         * An unselected component draws no mark.
         * A component that is not a button draws no mark.
         *
         * A selected button draws the emerald mark.
         * The stroke width is two pixels.
         * The cap and the join are round.
         * The mark runs through three points.
         * Those points are offset by three and seven, six and eleven, and eleven and four.
         * The graphics copy is disposed.
         *
         * @param c The check box.
         * @param g The graphics context.
         * @param x The left edge.
         * @param y The top edge.
         */
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D pen = (Graphics2D) g.create();
            try {
                pen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                pen.setColor(CHECK_SAPPHIRE);
                pen.drawRect(x + 1, y + 1, EDGE - 3, EDGE - 3);
                boolean on = c instanceof AbstractButton && ((AbstractButton) c).isSelected();
                if (!on) {
                    return;
                }
                pen.setColor(CHECK_EMERALD);
                pen.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int[] xs = new int[] { x + 3, x + 6, x + 11 };
                int[] ys = new int[] { y + 7, y + 11, y + 4 };
                pen.drawPolyline(xs, ys, 3);
            } finally {
                pen.dispose();
            }
        }

        /**
         * The {@code getIconWidth} method returns the icon width.
         * The width is fourteen pixels.
         *
         * @return Fourteen.
         */
        @Override
        public int getIconWidth() {
            return EDGE;
        }

        /**
         * The {@code getIconHeight} method returns the icon height.
         * The height is fourteen pixels.
         *
         * @return Fourteen.
         */
        @Override
        public int getIconHeight() {
            return EDGE;
        }
    }
}
/*****************************************************************************************
 * End of Class: SshClientTheme.java 082626
 ****************************************************************************************/
