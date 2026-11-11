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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.KeyStroke;

import com.jediterm.terminal.TerminalColor;
import com.jediterm.terminal.TextStyle;
import com.jediterm.terminal.emulator.ColorPalette;
import com.jediterm.terminal.emulator.ColorPaletteImpl;
import com.jediterm.terminal.ui.TerminalActionPresentation;
import com.jediterm.terminal.ui.settings.DefaultSettingsProvider;

/**
 * The {@code DarkJediTermSettings} type is the JediTerm settings object for the dark terminal.
 * The name JediTerm means the terminal widget these settings configure.
 * The font size can change without building a new widget.
 * The new size stays unused until the terminal panel runs {@code reinitFontAndResize}.
 * The method {@link DarkJediTermWidget#applyFontSize} makes that call.
 * The scrollback stays at {@link #SCROLLBACK_LINES} lines.
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
public class DarkJediTermSettings extends DefaultSettingsProvider {

    /**
     * The {@code SCROLLBACK_LINES} constant is the number of history lines the buffer keeps.
     * The value is {@code 10000}.
     */
    public static final int SCROLLBACK_LINES = 10000;

    private final TerminalColor foreground;
    private final TerminalColor background;
    private final TextStyle selectionStyle;
    private float fontSize;

    /**
     * The constructor reads the terminal colors and the font size from the properties.
     * The constructor reads each color from {@link SshClientProperties#getColor} with the matching default hex.
     * The foreground key is {@link SshClientProperties#UI_TERMINAL_COLOR_FOREGROUND}.
     * The background key is {@link SshClientProperties#UI_TERMINAL_COLOR_BACKGROUND}.
     * The selection foreground key is {@link SshClientProperties#UI_TERMINAL_COLOR_SELECTION_FOREGROUND}.
     * The selection background key is {@link SshClientProperties#UI_TERMINAL_COLOR_SELECTION_BACKGROUND}.
     * The stored color keeps the red, green, and blue channels.
     * The stored color drops the alpha channel.
     * The font size is the int from {@link SshClientProperties#getFontSize}, stored as a float.
     *
     * @param properties The color and font source.
     */
    public DarkJediTermSettings(SshClientProperties properties) {
        Color fg = properties.getColor(
                SshClientProperties.UI_TERMINAL_COLOR_FOREGROUND,
                SshClientProperties.DEFAULT_TERMINAL_FOREGROUND);
        Color bg = properties.getColor(
                SshClientProperties.UI_TERMINAL_COLOR_BACKGROUND,
                SshClientProperties.DEFAULT_TERMINAL_BACKGROUND);
        Color selFg = properties.getColor(
                SshClientProperties.UI_TERMINAL_COLOR_SELECTION_FOREGROUND,
                SshClientProperties.DEFAULT_SELECTION_FOREGROUND);
        Color selBg = properties.getColor(
                SshClientProperties.UI_TERMINAL_COLOR_SELECTION_BACKGROUND,
                SshClientProperties.DEFAULT_SELECTION_BACKGROUND);
        this.foreground = TerminalColor.rgb(fg.getRed(), fg.getGreen(), fg.getBlue());
        this.background = TerminalColor.rgb(bg.getRed(), bg.getGreen(), bg.getBlue());
        this.selectionStyle = new TextStyle(
                TerminalColor.rgb(selFg.getRed(), selFg.getGreen(), selFg.getBlue()),
                TerminalColor.rgb(selBg.getRed(), selBg.getGreen(), selBg.getBlue()));
        this.fontSize = properties.getFontSize();
    }

    /**
     * The setTerminalFontSize method stores a new point size.
     * The method does not resize the terminal grid.
     * The grid changes when the panel runs {@code reinitFontAndResize}.
     * The method {@link DarkJediTermWidget#applyFontSize} makes that call.
     *
     * @param fontSize The point size to store.
     */
    public void setTerminalFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * The getTerminalFontSize method returns the stored point size.
     *
     * @return The return value is the point size last stored.
     */
    @Override
    public float getTerminalFontSize() {
        return fontSize;
    }

    /**
     * The getCopyActionPresentation method names the copy action and its key.
     * The action name is {@code Copy}.
     * The key is control, shift, and C.
     * The presentation does not bind control and C without shift.
     * The chord stays the interrupt.
     *
     * @return The return value is the copy action presentation.
     */
    @Override
    public TerminalActionPresentation getCopyActionPresentation() {
        List<KeyStroke> keys = new ArrayList<KeyStroke>();
        keys.add(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        return new TerminalActionPresentation("Copy", keys);
    }

    /**
     * The getPasteActionPresentation method names the paste action and its keys.
     * The action name is {@code Paste}.
     * The first key is control, shift, and V.
     * The second key is control and V.
     * The presentation does not bind control and C.
     * The chord stays the interrupt.
     *
     * @return The return value is the paste action presentation.
     */
    @Override
    public TerminalActionPresentation getPasteActionPresentation() {
        List<KeyStroke> keys = new ArrayList<KeyStroke>();
        keys.add(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        keys.add(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        return new TerminalActionPresentation("Paste", keys);
    }

    /**
     * The getTerminalFont method picks a plain monospaced font for the operating system.
     * The name comes from the {@code os.name} property, read in lower case, and a missing property is an empty string.
     * The method uses {@code Consolas} when that name contains {@code win}.
     * The method uses {@code Menlo} when that name contains {@code mac}.
     * The method uses {@code Monospaced} for every other name.
     * The style is plain.
     * The size is the stored point size cast to an int, which drops the fraction.
     *
     * @return The return value is that font.
     */
    @Override
    public Font getTerminalFont() {
        String fontName;
        String os = System.getProperty("os.name", "").toLowerCase();
        if (os.indexOf("win") >= 0) {
            fontName = "Consolas";
        } else if (os.indexOf("mac") >= 0) {
            fontName = "Menlo";
        } else {
            fontName = "Monospaced";
        }
        return new Font(fontName, Font.PLAIN, (int) getTerminalFontSize());
    }

    /**
     * The getTerminalColorPalette method returns the xterm palette JediTerm ships.
     * The palette is {@code ColorPaletteImpl.XTERM_PALETTE}.
     *
     * @return The return value is that palette.
     */
    @Override
    public ColorPalette getTerminalColorPalette() {
        return ColorPaletteImpl.XTERM_PALETTE;
    }

    /**
     * The getDefaultForeground method returns the foreground color stored by the constructor.
     *
     * @return The return value is that color.
     */
    @Override
    public TerminalColor getDefaultForeground() {
        return foreground;
    }

    /**
     * The getDefaultBackground method returns the background color stored by the constructor.
     *
     * @return The return value is that color.
     */
    @Override
    public TerminalColor getDefaultBackground() {
        return background;
    }

    /**
     * The getDefaultStyle method builds a text style from the stored foreground and background.
     * The method builds a new style object on each call.
     *
     * @return The return value is that style.
     */
    @Override
    public TextStyle getDefaultStyle() {
        return new TextStyle(foreground, background);
    }

    /**
     * The getSelectionColor method returns the selection style stored by the constructor.
     *
     * @return The return value is that style.
     */
    @Override
    public TextStyle getSelectionColor() {
        return selectionStyle;
    }

    /**
     * The getFoundPatternColor method returns the color used for a found pattern.
     * The foreground is black.
     * The background is red {@code 255}, green {@code 255}, and blue {@code 0}.
     *
     * @return The return value is a new style with those colors.
     */
    @Override
    public TextStyle getFoundPatternColor() {
        return new TextStyle(TerminalColor.BLACK, TerminalColor.rgb(255, 255, 0));
    }

    /**
     * The getHyperlinkColor method returns the color used for a hyperlink.
     * The foreground is red {@code 180}, green {@code 80}, and blue {@code 255}.
     * The background is the stored terminal background.
     *
     * @return The return value is a new style with those colors.
     */
    @Override
    public TextStyle getHyperlinkColor() {
        return new TextStyle(TerminalColor.rgb(180, 80, 255), background);
    }

    /**
     * The useInverseSelectionColor method says the selection does not invert the cell colors.
     *
     * @return The return value is false.
     */
    @Override
    public boolean useInverseSelectionColor() {
        return false;
    }

    /**
     * The DECCompatibilityMode method turns on DEC compatibility for the terminal.
     * The name DEC means the older terminal behavior JediTerm can emulate.
     *
     * @return The return value is true.
     */
    @Override
    public boolean DECCompatibilityMode() {
        return true;
    }

    /**
     * The getBufferMaxLinesCount method returns the scrollback limit.
     *
     * @return The return value is {@link #SCROLLBACK_LINES}.
     */
    @Override
    public int getBufferMaxLinesCount() {
        return SCROLLBACK_LINES;
    }
}
/*****************************************************************************************
 * End of Class: DarkJediTermSettings.java 082426
 ****************************************************************************************/
