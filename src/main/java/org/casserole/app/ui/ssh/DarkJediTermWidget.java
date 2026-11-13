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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;

import org.jetbrains.annotations.NotNull;

import com.jediterm.terminal.model.SelectionUtil;
import com.jediterm.terminal.model.StyleState;
import com.jediterm.terminal.model.TerminalSelection;
import com.jediterm.terminal.model.TerminalTextBuffer;
import com.jediterm.terminal.ui.JediTermWidget;
import com.jediterm.terminal.ui.TerminalActionProvider;
import com.jediterm.terminal.ui.TerminalPanel;
import com.jediterm.terminal.ui.settings.SettingsProvider;

// @aa-sdd PK-1056 docs/specs/common/formal-plan.spec.md FINESSE:f-36
// @aa-sdd PK-1013 docs/specs/common/formal-plan.spec.md FINESSE:f-82
// @aa-sdd PK-1008 docs/specs/common/formal-plan.spec.md FINESSE:f-87
// @aa-sdd PK-1227 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-1227 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
/**
 * The {@code DarkJediTermWidget} type is the JediTerm widget with a public font refresh.
 * The name JediTerm means the terminal widget this class extends.
 * The upstream font refresh is protected, so this type exposes {@link #applyFontSize}.
 * The context menu adds Import, Transfer Conversation, and Select model beside the JediTerm copy and paste items.
 * The menu action does nothing until the matching request is set.
 * // @aa-sdd L40 aa-sdd-source-prose.md#DarkJediTermWidget
 *
 * @author clayl (2026-08-24)
 */
// @aa-sdd PK-1362 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-271 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-274 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-225 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-235 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
public class DarkJediTermWidget extends JediTermWidget {

    /**
     * The importRequest field is the action for Import on the console context menu.
     * The field stays null until the application sets it.
     */
    private Runnable importRequest;

    /**
     * The selectModelRequest field is the action for Select model on the console context menu.
     * The field stays null until the application sets it.
     */
    private Runnable selectModelRequest;

    /**
     * The transferRequest field is the action for Transfer Conversation on the console context menu.
     * The field stays null until the application sets it.
     */
    private Runnable transferRequest;

    /**
     * The menuTheme field is the theme for the console context menu.
     * The field stays null until the panel sets it.
     */
    private SshClientTheme menuTheme;

    /**
     * The constructor builds the JediTerm widget with the given grid and settings.
     * The call passes those three values to the superclass.
     *
     * @param columns The initial column count.
     * @param lines The initial row count.
     * @param settingsProvider The dark terminal settings.
     */
    public DarkJediTermWidget(int columns, int lines, SettingsProvider settingsProvider) {
        super(columns, lines, settingsProvider);
    }

    /**
     * The createScrollBar method arms the terminal scroll bar with the Apple strip.
     * The Apple strip is the scroll-bar look from {@link AppleScrollBar}.
     * The JediTerm widget calls this method while the widget is still being built.
     * The method asks the superclass for the bar, then passes this widget and that bar to {@link AppleScrollBar#armStrip}.
     *
     * @return The return value is the armed scroll bar.
     */
    @Override
    protected JScrollBar createScrollBar() {
        JScrollBar bar = super.createScrollBar();
        AppleScrollBar.armStrip(this, bar);
        return bar;
    }

    /**
     * The setImportRequest method stores the action for the Import menu item.
     * The method stores a null request.
     * The later click does nothing when the request is null.
     *
     * @param request The action called when the console context menu chooses Import. The value may be null.
     */
    public void setImportRequest(Runnable request) {
        this.importRequest = request;
    }

    /**
     * The setSelectModelRequest method stores the action for the Select model menu item.
     * The method stores a null request.
     * The later click does nothing when the request is null.
     *
     * @param request The action called when the console context menu chooses Select model. The value may be null.
     */
    public void setSelectModelRequest(Runnable request) {
        this.selectModelRequest = request;
    }

    /**
     * The setTransferRequest method stores the action for the Transfer Conversation menu item.
     * The method stores a null request.
     * The later click does nothing when the request is null.
     *
     * @param request The action called when the console context menu chooses Transfer Conversation. The value may be null.
     */
    public void setTransferRequest(Runnable request) {
        this.transferRequest = request;
    }

    /**
     * The setMenuTheme method stores the theme for the console context menu.
     * The method stores a null theme.
     * The menu is then left unpainted by this widget.
     *
     * @param theme The theme for the console context menu. The value may be null.
     */
    public void setMenuTheme(SshClientTheme theme) {
        this.menuTheme = theme;
    }

    /**
     * The createTerminalPanel method builds the dark panel this widget owns.
     * The panel is a {@link DarkTerminalPanel}.
     *
     * @param settingsProvider The settings JediTerm passes in.
     * @param styleState The default style state JediTerm passes in.
     * @param terminalTextBuffer The text buffer JediTerm passes in.
     * @return The return value is the new dark panel.
     */
    @Override
    protected TerminalPanel createTerminalPanel(
            @NotNull SettingsProvider settingsProvider,
            @NotNull StyleState styleState,
            @NotNull TerminalTextBuffer terminalTextBuffer) {
        return new DarkTerminalPanel(settingsProvider, terminalTextBuffer, styleState, this);
    }

    /**
     * The applyFontSize method refreshes glyph metrics and resizes the grid for the current font size.
     * The method does nothing when the terminal panel is missing.
     * The method does nothing when the panel is not a {@link DarkTerminalPanel}.
     * The method calls {@link DarkTerminalPanel#applyFontAndResize} for a dark panel.
     */
    public void applyFontSize() {
        TerminalPanel panel = getTerminalPanel();
        if (panel instanceof DarkTerminalPanel) {
            ((DarkTerminalPanel) panel).applyFontAndResize();
        }
    }

    /**
     * The selectedText method returns the highlighted terminal text.
     * The method returns an empty string when the panel is missing.
     * The method returns an empty string when no selection is stored.
     * The method returns an empty string when the selection text is null.
     * The method returns the selection text for every other case.
     *
     * @return The return value is the highlight, or an empty string when nothing is highlighted.
     */
    public String selectedText() {
        TerminalPanel panel = getTerminalPanel();
        if (panel == null) {
            return "";
        }
        TerminalSelection selection = panel.getSelection();
        if (selection == null) {
            return "";
        }
        String text = SelectionUtil.getSelectionText(selection, panel.getTerminalTextBuffer());
        if (text == null) {
            return "";
        }
        return text;
    }

    /**
     * The lastScreenLine method returns the last non-empty row on the screen.
     * The blank row is a row whose trimmed text is empty.
     * The blank prompt line is skipped.
     * The method returns an empty string when the panel is missing.
     * The method returns an empty string when the text buffer is missing.
     * The method returns an empty string when the screen text is null or empty.
     * The method walks rows from the end and returns the first row that still has text after trimming.
     * The returned row is the trimmed text.
     * The method returns an empty string when every row is blank.
     *
     * @return The return value is that one line, or an empty string.
     */
    public String lastScreenLine() {
        TerminalPanel panel = getTerminalPanel();
        if (panel == null || panel.getTerminalTextBuffer() == null) {
            return "";
        }
        String screen = panel.getTerminalTextBuffer().getScreenLines();
        if (screen == null || screen.length() == 0) {
            return "";
        }
        int end = screen.length();
        while (end > 0) {
            int start = screen.lastIndexOf('\n', end - 1);
            int from = start < 0 ? 0 : start + 1;
            String one = screen.substring(from, end).trim();
            if (one.length() > 0) {
                return one;
            }
            if (start < 0) {
                break;
            }
            end = start;
        }
        return "";
    }

    /**
     * The sessionMemoryText method joins the scrollback and then the screen for the token window.
     * The rows run from the oldest history line through the last screen line.
     * The method stores a null row as an empty string.
     * The trailing blank rows stay off the returned text.
     * The method returns an empty string when the panel is missing or the buffer is missing.
     * The method returns an empty string for an empty pane.
     * The method {@link org.roux.token.HeuristicTokenCounter#count} counts that empty string as {@code 1}.
     * The buffer is locked while the rows are read, and the lock is released before the method returns.
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-160
     *
     * @return The return value is the session text the token window counts.
     */
    public String sessionMemoryText() {
        TerminalPanel panel = getTerminalPanel();
        if (panel == null || panel.getTerminalTextBuffer() == null) {
            return "";
        }
        TerminalTextBuffer buffer = panel.getTerminalTextBuffer();
        buffer.lock();
        try {
            int history = buffer.getHistoryLinesCount();
            int height = buffer.getHeight();
            StringBuilder sb = new StringBuilder();
            boolean any = false;
            for (int index = -history; index < height; index++) {
                String line = buffer.getLine(index).getText();
                if (line == null) {
                    line = "";
                }
                if (any) {
                    sb.append('\n');
                }
                sb.append(line);
                any = true;
            }
            return dropTrailingBlankLines(sb.toString());
        } finally {
            buffer.unlock();
        }
    }

    /**
     * The dropTrailingBlankLines method removes trailing blank rows from the joined session text.
     * The blank row is a row whose trimmed text is empty.
     * The method returns an empty string when the text is null or empty.
     * The method returns an empty string when every row is blank.
     * The method keeps the last row that still has text, and it does not trim that row.
     * The text before that row stays in the result.
     *
     * @param text The joined lines. The value may be null.
     * @return The return value is the text with trailing blank rows removed.
     */
    private static String dropTrailingBlankLines(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        int end = text.length();
        while (end > 0) {
            int start = text.lastIndexOf('\n', end - 1);
            int from = start < 0 ? 0 : start + 1;
            String one = text.substring(from, end).trim();
            if (one.length() > 0) {
                return text.substring(0, end);
            }
            if (start < 0) {
                return "";
            }
            end = start;
        }
        return "";
    }

    // @aa-sdd PK-1159 docs/specs/common/stages.spec.md FINESSE:f-157
    // @aa-sdd PK-1056 docs/specs/common/formal-plan.spec.md FINESSE:f-36
    // @aa-sdd PK-1054 docs/specs/common/formal-plan.spec.md FINESSE:f-38
    // @aa-sdd PK-1013 docs/specs/common/formal-plan.spec.md FINESSE:f-82
    // @aa-sdd PK-1228 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-1228 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    /**
     * The {@code DarkTerminalPanel} type is the terminal panel with a public font refresh.
     * The protected upstream refresh is {@code reinitFontAndResize}.
     * The context menu adds Import, Transfer Conversation, and Select model after the JediTerm items.
     */
    // @aa-sdd PK-267 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    static class DarkTerminalPanel extends TerminalPanel {

        private final DarkJediTermWidget widget;

        /**
         * The constructor stores the owning widget and passes the buffer settings to the superclass.
         * The widget is kept so the menu can run the import, transfer, and model actions.
         *
         * @param settingsProvider The terminal settings.
         * @param terminalTextBuffer The terminal text buffer.
         * @param styleState The default style state.
         * @param widget The owning widget. The menu reads the actions from this widget.
         */
        DarkTerminalPanel(
                @NotNull SettingsProvider settingsProvider,
                @NotNull TerminalTextBuffer terminalTextBuffer,
                @NotNull StyleState styleState,
                @NotNull DarkJediTermWidget widget) {
            super(settingsProvider, terminalTextBuffer, styleState);
            this.widget = widget;
        }

        /**
         * The createPopupMenu method starts from the JediTerm menu and adds three items.
         * The method adds a separator.
         * The method then adds Import.
         * The Import label is {@code Import...}.
         * The Import tooltip says Import reads a file and inserts it at the terminal cursor.
         * The method adds a second separator.
         * The method then adds Transfer Conversation.
         * The Transfer tooltip says the item copies this Roux session, the chat on this console, onto the canvas after OK.
         * The Transfer tooltip says the canvas turns become the chat.
         * The Transfer tooltip says Cancel leaves both sides as they are.
         * The method adds Select model after Transfer.
         * The method adds no extra separator before Select model.
         * The Select model tooltip says the item opens the Model Config modal for this console.
         * The Select model tooltip says the family is Ollama, Grok, or Pool.
         * The Select model tooltip says the next console turn uses that family.
         * The click runs the stored action when that action is not null.
         * The click does nothing when that action is null.
         * The menu is painted with the stored theme when the theme is not null.
         * The null theme leaves the menu colors unchanged.
         *
         * @param actionProvider The JediTerm action provider for the base menu.
         * @return The return value is the popup menu.
         */
        @Override
        protected @NotNull JPopupMenu createPopupMenu(@NotNull TerminalActionProvider actionProvider) {
            JPopupMenu popup = super.createPopupMenu(actionProvider);
            popup.addSeparator();
            JMenuItem item = new JMenuItem("Import...");
            item.setToolTipText("Import reads a file and inserts it at the terminal cursor.");
            /**
             * The listener runs the stored import action.
             * The listener does nothing when that action is null.
             */
            item.addActionListener(new ActionListener() {
                // @aa-sdd PK-1229 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
                @Override
                public void actionPerformed(ActionEvent e) {
                    Runnable request = widget.importRequest;
                    if (request != null) {
                        request.run();
                    }
                }
            });
            popup.add(item);
            popup.addSeparator();
            JMenuItem transfer = new JMenuItem("Transfer Conversation");
            transfer.setToolTipText("<html>Transfer Conversation copies this Roux session onto the canvas after OK."
                    + "<br>The canvas turns become the chat."
                    + "<br>Cancel leaves both sides as they are.</html>");
            /**
             * The listener runs the stored transfer action.
             * The listener does nothing when that action is null.
             */
            transfer.addActionListener(new ActionListener() {
                // @aa-sdd PK-1056 docs/specs/common/formal-plan.spec.md FINESSE:f-36
                // @aa-sdd PK-1230 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
                @Override
                public void actionPerformed(ActionEvent e) {
                    Runnable request = widget.transferRequest;
                    if (request != null) {
                        request.run();
                    }
                }
            });
            popup.add(transfer);
            JMenuItem selectModel = new JMenuItem("Select model");
            selectModel.setToolTipText("<html>Select model opens the Model Config modal for this console."
                    + "<br>Family is Ollama, Grok, or Pool."
                    + "<br>The next console turn uses that family.</html>");
            /**
             * The listener runs the stored model action.
             * The listener does nothing when that action is null.
             */
            selectModel.addActionListener(new ActionListener() {
                // @aa-sdd PK-1056 docs/specs/common/formal-plan.spec.md FINESSE:f-36
                @Override
                public void actionPerformed(ActionEvent e) {
                    Runnable request = widget.selectModelRequest;
                    if (request != null) {
                        request.run();
                    }
                }
            });
            popup.add(selectModel);
            if (widget.menuTheme != null) {
                widget.menuTheme.applyPopup(popup);
            }
            return popup;
        }

        /**
         * The applyFontAndResize method calls the protected {@code reinitFontAndResize}.
         * The call recomputes the glyph metrics and resizes the grid.
         */
        void applyFontAndResize() {
            reinitFontAndResize();
        }
    }
}
/*****************************************************************************************
 * End of Class: DarkJediTermWidget.java 082426
 ****************************************************************************************/
