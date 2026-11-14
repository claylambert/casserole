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
 * // @aa-sdd L40 aa-sdd-source-prose.md#DarkJediTermSettings
 *
 * @author clayl (2026-08-24)
 */
// @aa-sdd PK-281 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-285 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
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
