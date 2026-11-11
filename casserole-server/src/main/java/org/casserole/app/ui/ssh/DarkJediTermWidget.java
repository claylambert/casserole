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

// @aa-sdd PK-0660 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-3619 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3646 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3497 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4466 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/**
 * The {@code DarkJediTermWidget} type is the JediTerm widget with a public font refresh.
 * The name JediTerm means the terminal widget this class extends.
 * The upstream font refresh is protected, so this type exposes {@link #applyFontSize}.
 * The context menu adds Import, Transfer Conversation, and Select model beside the JediTerm copy and paste items.
 * The menu action does nothing until the matching request is set.
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

    // @aa-sdd PK-0117 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0661 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-3537 PK-3567 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-3620 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-3498 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4467 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    /**
     * The {@code DarkTerminalPanel} type is the terminal panel with a public font refresh.
     * The protected upstream refresh is {@code reinitFontAndResize}.
     * The context menu adds Import, Transfer Conversation, and Select model after the JediTerm items.
     */
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
                // @aa-sdd PK-3499 docs/specs/java/org/aetheris/ssh/ssh.spec.md
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
                // @aa-sdd PK-0662 docs/specs/java/org/casserole/llm/llm.spec.md
                // @aa-sdd PK-4468 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
                // @aa-sdd PK-0663 docs/specs/java/org/casserole/llm/llm.spec.md
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
