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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.aetheris.swing.ThinkStrip;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.jediterm.core.util.TermSize;
import com.jediterm.terminal.TtyConnector;
import com.jediterm.terminal.ui.TerminalPanel;

// @aa-sdd PK-4151 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0271 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
// @aa-sdd PK-0668 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-3598 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3604 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3623 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3797 PK-3798 PK-3801 PK-3805 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4470 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/**
 * The {@code SshTerminalPanel} is one JediTerm host for a single SSH pane.
 * JediTerm is the terminal widget that paints the session.
 * A new SSH session builds a new widget.
 * Disconnect leaves that widget so the last buffer stays on the screen.
 *
 * @aa-sdd PK-3695 PK-3696 PK-3754 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * @aa-sdd PK-0126 PK-0283 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 *
 * @author clayl (2026-08-24)
 */
public class SshTerminalPanel extends JPanel {

    private final DarkJediTermSettings settings;
    private final SshClientTheme theme;
    private final ThinkStrip thinkStrip;
    private DarkJediTermWidget widget;
    private Runnable importRequest;
    private Runnable selectModelRequest;
    private Runnable transferRequest;
    /**
     * The flag is true while a console thought block is still receiving chunks.
     */
    private boolean consoleThinkLive;
    /**
     * The flag is true after the shared VT100 chrome file has been read once.
     * That file is used by every VT100 strip.
     * The notebook chrome file is a different file.
     */
    private static boolean vtChromeLoaded;
    private static boolean vtChromeBlocked;
    private static int vtChromeHeight;
    private static boolean vtChromeClosed;
    /**
     * The flag is true while this pane is the focused leaf among several panes.
     * A single pane does not set the flag.
     */
    private boolean paneFocus;
    /**
     * The color is the focus stroke painted around the focused pane.
     * The bytes are {@code 0x00D7FF}.
     */
    private static final Color PANE_FOCUS = new Color(0x00, 0xD7, 0xFF);
    /**
     * The border reserves one pixel on each edge.
     * The terminal cannot cover that pixel.
     */
    private static final EmptyBorder PANE_FOCUS_INSET = new EmptyBorder(1, 1, 1, 1);
    /**
     * The string is the text typed on this pane and not yet submitted.
     */
    private String liveLine = "";
    /**
     * The integer is the last token estimate stored for this pane.
     * The heat bar reads this integer.
     * The key handler does not write it.
     */
    private int liveCount = 1;
    /**
     * The hook runs after a key changes the unsent line.
     * The hook posts the change.
     * The hook does not count tokens.
     */
    private Runnable draftBeat;

    /**
     * The constructor builds one dark terminal pane and a thinking strip above it.
     * The font size comes from the shared settings, or {@code 12} when that font is missing.
     * {@link #ensureVtChrome} loads the shared VT100 chrome file before the strip is built.
     * The strip host reports height, saves chrome, and clears the live thought flag.
     * A new widget is created at 80 columns and 24 rows and placed in the center.
     *
     * @param settings The settings object holds the shared JediTerm font and colors, and the font size is changed in place.
     * @param theme The theme is the dark painter used by the thinking strip.
     */
    public SshTerminalPanel(DarkJediTermSettings settings, SshClientTheme theme) {
        super(new BorderLayout());
        this.settings = settings;
        this.theme = theme;
        setOpaque(true);
        final SshTerminalPanel panel = this;
        Font body = settings.getTerminalFont();
        int size = body == null ? 12 : body.getSize();
        ensureVtChrome();
        this.thinkStrip = new ThinkStrip(theme, body, size, false, vtChromeHeight, vtChromeClosed, true,
                /**
                 * The host answers the thinking strip for this pane.
                 * {@code maxHeight} returns the strip's natural height when the pane is shorter than that height plus 160 pixels.
                 * A pane with no height yet returns {@code 480}.
                 * {@code relayout} revalidates this pane.
                 * {@code saveLog} does nothing.
                 * {@code loadLog} returns an empty string.
                 * {@code noteChrome} stores the closed flag and the height for every VT100 strip.
                 * {@code saveChrome} writes that chrome file.
                 * {@code cleared} marks the console thought block as finished.
                 */
                new ThinkStrip.Host() {
                    // @aa-sdd PK-3599 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                    // @aa-sdd PK-3605 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public int maxHeight() {
                int natural = thinkStrip == null ? 48 : thinkStrip.naturalHeight();
                int h = panel.getHeight();
                if (h < 1) {
                    return 480;
                }
                int room = h - 160;
                if (room < natural) {
                    return natural;
                }
                return room;
            }

            @Override
            public void relayout() {
                panel.revalidate();
            }

            @Override
            public void saveLog(String text) {
            }

            @Override
            public String loadLog() {
                return "";
            }

            @Override
            public void noteChrome(boolean closed, int height) {
                vtChromeClosed = closed;
                vtChromeHeight = height;
            }

            @Override
            public void saveChrome() {
                writeVtChrome();
            }

            @Override
            public void cleared() {
                consoleThinkLive = false;
            }
        });
        this.widget = newWidget();
        seatDraftKeys(this.widget);
        add(this.thinkStrip, BorderLayout.NORTH);
        add(this.widget, BorderLayout.CENTER);
    }

    /**
     * The method shows or hides the focus stroke on this pane.
     * An unchanged value returns without a repaint.
     * A true value installs a one-pixel inset so the terminal cannot cover the stroke.
     * A false value clears the border.
     * The caller passes true only for the focused leaf among several panes.
     * A single pane stays bare.
     *
     * @aa-sdd PK-3695 PK-3696 PK-3754 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * @aa-sdd PK-0126 PK-0283 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     *
     * @param on The flag is true when this pane is the focused leaf among several panes.
     */
    void seatPaneFocus(boolean on) {
        if (paneFocus == on) {
            return;
        }
        paneFocus = on;
        if (on) {
            setBorder(PANE_FOCUS_INSET);
        } else {
            setBorder(null);
        }
        revalidate();
        repaint();
    }

    /**
     * The method paints the one-pixel focus stroke after the normal border.
     * The stroke is skipped when this pane is not the focused leaf.
     * A width or height below {@code 1} returns without a stroke.
     * The top edge is always one pixel when the stroke is on.
     * The bottom edge is painted when the height is greater than {@code 1}.
     * The side edges are painted when the width is greater than {@code 1}.
     * A later child paint cannot cover that ring.
     *
     * @aa-sdd PK-3695 PK-3696 PK-3754 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * @aa-sdd PK-0126 PK-0283 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     *
     * @param g The graphics object is the border drawing surface.
     */
    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
        if (!paneFocus) {
            return;
        }
        int w = getWidth();
        int h = getHeight();
        if (w < 1 || h < 1) {
            return;
        }
        g.setColor(PANE_FOCUS);
        g.fillRect(0, 0, w, 1);
        if (h > 1) {
            g.fillRect(0, h - 1, w, 1);
        }
        if (w > 1) {
            g.fillRect(0, 0, 1, h);
            g.fillRect(w - 1, 0, 1, h);
        }
    }

    /**
     * The method offers a pool thought on this session.
     * The tag passed to the strip is {@code pool}.
     * The terminal bytes stay the reply.
     *
     * @param text The text is the thought chunk.
     */
    public void offerConsoleThought(String text) {
        offerConsoleThought(text, "pool");
    }

    /**
     * The method offers a console thought on this session.
     * A missing strip, a null chunk, or an empty chunk returns without a change.
     * A live block appends the chunk.
     * A new block starts with the tag and the chunk.
     * A null or blank tag is sent as {@code ollama}.
     * The terminal bytes stay the reply.
     *
     * @param text The text is the thought chunk.
     * @param tag The tag is the model word for the turn.
     */
    public void offerConsoleThought(String text, String tag) {
        if (thinkStrip == null || text == null || text.length() == 0) {
            return;
        }
        if (consoleThinkLive) {
            thinkStrip.append(text);
        } else {
            consoleThinkLive = true;
            String word = tag == null ? "" : tag.trim();
            if (word.length() == 0) {
                word = "ollama";
            }
            thinkStrip.begin(word, text);
        }
    }

    /**
     * The method closes the console thought block.
     * The strip stays on screen.
     * A missing strip only clears the live flag.
     */
    public void finishConsoleThought() {
        consoleThinkLive = false;
        if (thinkStrip != null) {
            thinkStrip.finish();
        }
    }

    /**
     * The method shows the thinking strip for this session.
     * A different pane keeps its own strip.
     * The closed mark stored in the chrome file is not changed.
     */
    public void revealThinking() {
        if (thinkStrip != null) {
            thinkStrip.revealFromMenu();
        }
    }

    /**
     * The method stores the console Import action.
     * A live widget receives the same action.
     * A later widget built by {@link #newWidget} receives it too.
     *
     * @param request The request runs from the console Import menu.
     */
    public void setImportRequest(Runnable request) {
        this.importRequest = request;
        if (widget != null) {
            widget.setImportRequest(request);
        }
    }

    /**
     * The method stores the console Select Model action.
     * A live widget receives the same action.
     * A later widget receives it too.
     *
     * @param request The request runs from the console Select Model menu.
     */
    public void setSelectModelRequest(Runnable request) {
        this.selectModelRequest = request;
        if (widget != null) {
            widget.setSelectModelRequest(request);
        }
    }

    /**
     * The method stores the console Transfer Conversation action.
     * A live widget receives the same action.
     * A later widget receives it too.
     *
     * @param request The request runs from the console Transfer Conversation menu.
     */
    public void setTransferRequest(Runnable request) {
        this.transferRequest = request;
        if (widget != null) {
            widget.setTransferRequest(request);
        }
    }

    /**
     * The method stores the hook that runs when the unsent line changes.
     * A null hook stops those posts.
     *
     * @param beat The beat is the paint hook, or null.
     */
    public void setDraftBeat(Runnable beat) {
        this.draftBeat = beat;
    }

    /**
     * The method returns the line being typed on this pane.
     * The line is empty after Enter.
     * A null store returns an empty string.
     *
     * @aa-sdd PK-3695 PK-3696 PK-3754 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * @aa-sdd PK-0126 PK-0283 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     *
     * @return The return value is the unsent line.
     */
    public String liveLine() {
        return liveLine == null ? "" : liveLine;
    }

    /**
     * The method returns the token estimate last stored for this pane.
     *
     * @aa-sdd PK-3785 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     *
     * @return The return value is that stored estimate.
     */
    int liveCount() {
        return liveCount;
    }

    /**
     * The method stores the token estimate for this pane.
     * The key handler does not call this method.
     *
     * @aa-sdd PK-3786 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     *
     * @param count The count is the estimate to store.
     */
    void seatLiveCount(int count) {
        liveCount = count;
    }

    /**
     * The method counts keys on this pane before the echo lands.
     * A null widget returns without a listener.
     * A widget with no terminal panel returns without a listener.
     * A typed character at or above space, other than delete, is appended to the unsent line.
     * {@code CHAR_UNDEFINED}, a character below space, or delete is ignored on the typed event.
     * Backspace drops one character when the line is not empty.
     * Enter with Shift keeps the line and adds a newline.
     * Enter without Shift clears the line.
     *
     * @param term The term is the widget that owns the keys.
     */
    private void seatDraftKeys(DarkJediTermWidget term) {
        if (term == null || term.getTerminalPanel() == null) {
            return;
        }
        /**
         * The listener updates the unsent line from keys on this pane.
         * Printable characters are appended.
         * Backspace drops one character.
         * Shift-Enter inserts a newline.
         * Enter without Shift clears the line.
         */
        term.getTerminalPanel().addKeyListener(new KeyAdapter() {
            // @aa-sdd PK-4152 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0272 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            // @aa-sdd PK-0281 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            @Override
            public void keyTyped(KeyEvent event) {
                char ch = event.getKeyChar();
                if (ch == KeyEvent.CHAR_UNDEFINED || ch < 32 || ch == 127) {
                    return;
                }
                pushDraft(liveLine + ch);
            }

            @Override
            public void keyPressed(KeyEvent event) {
                int code = event.getKeyCode();
                if (code == KeyEvent.VK_BACK_SPACE) {
                    if (liveLine.length() > 0) {
                        pushDraft(liveLine.substring(0, liveLine.length() - 1));
                    }
                    return;
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (event.isShiftDown()) {
                        pushDraft(liveLine + "\n");
                    } else {
                        pushDraft("");
                    }
                }
            }
        });
    }

    /**
     * The method stores the unsent line and runs the draft hook.
     * A null line is stored as empty.
     * A missing hook does not run.
     *
     * @param next The next value is the new unsent line.
     */
    private void pushDraft(String next) {
        liveLine = next == null ? "" : next;
        if (draftBeat != null) {
            draftBeat.run();
        }
    }

    /**
     * The method builds a dark widget at 80 columns and 24 rows.
     * The current import, select-model, and transfer hooks are copied onto it.
     * The menu theme is the pane theme.
     *
     * @return The return value is that widget.
     */
    private DarkJediTermWidget newWidget() {
        DarkJediTermWidget created = new DarkJediTermWidget(80, 24, settings);
        created.setImportRequest(importRequest);
        created.setSelectModelRequest(selectModelRequest);
        created.setTransferRequest(transferRequest);
        created.setMenuTheme(theme);
        return created;
    }

    /**
     * The method replaces the current widget with a new session on the connector.
     * The old widget is closed and removed.
     * The new widget is started, given the draft keys, and focused.
     *
     * @param connector The connector is the live SSH terminal connector.
     */
    public void attachSession(TtyConnector connector) {
        disposeCurrentWidget();
        widget = newWidget();
        widget.createTerminalSession(connector);
        widget.start();
        seatDraftKeys(widget);
        add(widget, BorderLayout.CENTER);
        revalidate();
        repaint();
        widget.requestFocusInWindow();
    }

    /**
     * The method stops the emulator and leaves the canvas in place.
     * The last output remains.
     * A missing widget returns without a call.
     * A widget that is not running is not stopped.
     */
    public void stopSession() {
        if (widget == null) {
            return;
        }
        if (widget.isSessionRunning()) {
            widget.stop();
        }
    }

    /**
     * The method clears the visible terminal buffer.
     * A missing widget returns without a call.
     * A widget with no terminal panel returns without a call.
     */
    public void clearTerminal() {
        if (widget == null) {
            return;
        }
        TerminalPanel panel = widget.getTerminalPanel();
        if (panel != null) {
            panel.clearBuffer();
        }
    }

    /**
     * The method applies a new point size to the live canvas at once.
     * The shared settings store the size.
     * A live widget repaints with that size.
     * The thinking strip takes the same terminal font and chrome size.
     *
     * @param fontSize The font size is the point size.
     */
    public void setFontSize(int fontSize) {
        settings.setTerminalFontSize(fontSize);
        if (widget != null) {
            widget.applyFontSize();
        }
        if (thinkStrip != null) {
            thinkStrip.setBodyFont(settings.getTerminalFont());
            thinkStrip.setChromeSize(fontSize);
        }
    }

    /**
     * The method returns the JediTerm typeface at the live point size.
     *
     * @return The return value is the terminal font from the settings.
     */
    public Font getTerminalFont() {
        return settings.getTerminalFont();
    }

    /**
     * The method returns the PTY size taken from the current widget.
     * A missing widget returns 80 columns and 24 rows.
     * A missing panel returns that same fallback.
     * A size with a column or row count that is not positive returns that same fallback.
     *
     * @return The return value is the column and row count.
     */
    public TermSize getPreferredTermSize() {
        if (widget != null) {
            TerminalPanel panel = widget.getTerminalPanel();
            if (panel != null) {
                TermSize fromComponent = panel.getTerminalSizeFromComponent();
                if (fromComponent != null
                        && fromComponent.getColumns() > 0
                        && fromComponent.getRows() > 0) {
                    return fromComponent;
                }
            }
        }
        return new TermSize(80, 24);
    }

    /**
     * The method asks the terminal canvas to take the keyboard focus.
     * A missing widget does nothing.
     */
    public void requestTerminalFocus() {
        if (widget != null) {
            widget.requestFocusInWindow();
        }
    }

    /**
     * The method returns the highlighted terminal text.
     * A missing widget returns an empty string.
     *
     * @return The return value is the highlight, or empty.
     */
    public String selectedText() {
        if (widget == null) {
            return "";
        }
        return widget.selectedText();
    }

    /**
     * The method returns the last non-empty screen row.
     * A missing widget returns an empty string.
     *
     * @return The return value is that row, or empty.
     */
    public String lastScreenLine() {
        if (widget == null) {
            return "";
        }
        return widget.lastScreenLine();
    }

    /**
     * The method returns the scrollback and the screen.
     * Trailing blank rows stay off that text.
     * A missing widget returns an empty string.
     * An empty pane is an empty string.
     *
     * @aa-sdd PK-3695 PK-3696 PK-3754 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * @aa-sdd PK-0126 PK-0283 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     *
     * @return The return value is the session text the token window counts.
     */
    public String sessionMemoryText() {
        if (widget == null) {
            return "";
        }
        return widget.sessionMemoryText();
    }

    /**
     * The method disposes the widget when the application exits.
     * The call closes and removes the current widget.
     */
    public void disposeWidget() {
        disposeCurrentWidget();
    }

    /**
     * The method closes and removes the current JediTerm widget.
     * A missing widget returns at once.
     * A close error is ignored.
     * The widget field is then cleared.
     */
    private void disposeCurrentWidget() {
        if (widget == null) {
            return;
        }
        try {
            widget.close();
        } catch (Exception ignored) {
            // JediTerm close is not always re-entrant
        }
        remove(widget);
        widget = null;
    }

    /**
     * The method returns the smallest size this pane accepts.
     * The size is 200 pixels wide and 120 pixels tall.
     *
     * @return The return value is that minimum size.
     */
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200, 120);
    }

    /**
     * The method reads version 1 of {@code ~/.casserole/aetheris-vt100-think.yaml}.
     * A second call returns without reading again.
     * A missing file leaves the stored height and closed flag unchanged.
     * A root that is not a map blocks later writes.
     * Any version other than {@code 1} blocks later writes and leaves the values unread.
     * A numeric height from {@code 48} through {@code 2000} is stored.
     * The closed flag accepts a boolean, {@code yes}, {@code true}, or {@code 1}.
     * A read error blocks later writes.
     * The notebook file {@code aetheris-think.yaml} is not this file.
     */
    private static void ensureVtChrome() {
        if (vtChromeLoaded) {
            return;
        }
        vtChromeLoaded = true;
        File file = new File(vtHome(), "aetheris-vt100-think.yaml");
        if (!file.isFile()) {
            return;
        }
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            Object loaded = new Yaml().load(reader);
            if (!(loaded instanceof Map)) {
                vtChromeBlocked = true;
                return;
            }
            Map<?, ?> root = (Map<?, ?>) loaded;
            if (!vtVersionOne(root.get("version"))) {
                vtChromeBlocked = true;
                return;
            }
            Object height = root.get("height");
            if (height instanceof Number) {
                int h = ((Number) height).intValue();
                if (h >= 48 && h <= 2000) {
                    vtChromeHeight = h;
                }
            }
            vtChromeClosed = vtFlag(root.get("closed"));
        } catch (Exception ex) {
            vtChromeBlocked = true;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    // the fields keep what they already read
                }
            }
        }
    }

    /**
     * The method writes the VT100 height and the closed flag.
     * A blocked file is not overwritten.
     * The thought text is not written.
     * The file is {@code aetheris-vt100-think.yaml} under the user {@code .casserole} folder.
     * The version written is {@code 1}.
     * A height of {@code 0} or less is omitted.
     * A write error returns without a further report.
     */
    private static void writeVtChrome() {
        if (vtChromeBlocked) {
            return;
        }
        File dir = vtHome();
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        LinkedHashMap<String, Object> root = new LinkedHashMap<String, Object>();
        root.put("version", Integer.valueOf(1));
        root.put("closed", Boolean.valueOf(vtChromeClosed));
        if (vtChromeHeight > 0) {
            root.put("height", Integer.valueOf(vtChromeHeight));
        }
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(new File(dir, "aetheris-vt100-think.yaml")),
                    StandardCharsets.UTF_8);
            new Yaml(options).dump(root, writer);
        } catch (Exception ex) {
            return;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ignored) {
                    // a missed close still left the bytes already written
                }
            }
        }
    }

    /**
     * The method returns the {@code .casserole} folder under the user home directory.
     *
     * @return The return value is that folder.
     */
    private static File vtHome() {
        return new File(System.getProperty("user.home"), ".casserole");
    }

    /**
     * The method reports whether a YAML version value is {@code 1}.
     * A number whose integer value is {@code 1} returns true.
     * The trimmed text {@code 1} returns true.
     * A null value returns false.
     *
     * @param value The value is the YAML version field.
     * @return The return value is true only for version 1.
     */
    private static boolean vtVersionOne(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue() == 1;
        }
        return value != null && "1".equals(String.valueOf(value).trim());
    }

    /**
     * The method reads a YAML flag.
     * A boolean returns that boolean.
     * A null value returns false.
     * The text {@code true}, {@code yes}, or {@code 1} returns true.
     * {@code true} and {@code yes} ignore case.
     *
     * @param value The value is the YAML flag field.
     * @return The return value is true for a boolean true, yes, or 1.
     */
    private static boolean vtFlag(Object value) {
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        if (value == null) {
            return false;
        }
        String text = String.valueOf(value).trim();
        return "true".equalsIgnoreCase(text) || "yes".equalsIgnoreCase(text) || "1".equals(text);
    }
}
/*****************************************************************************************
 * End of Class: SshTerminalPanel.java 082426
 ****************************************************************************************/
