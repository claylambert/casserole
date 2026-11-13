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

package org.aetheris.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.casserole.app.ui.ssh.SshClientTheme;
import org.casserole.app.ui.ssh.ToolsThinking;

// @aa-sdd GK-0698 aetheris-plan-f-102.001.md FINESSE:f-102
// @aa-sdd GK-0699 aetheris-plan-f-104.001.md FINESSE:f-104
// @aa-sdd GK-0700 aetheris-plan-f-111.001.md FINESSE:f-111
// @aa-sdd GK-0701 aetheris-plan-f-124.001.md FINESSE:f-124
// @aa-sdd GK-0702 aetheris-plan-f-219.001.md FINESSE:f-219
// @aa-sdd GK-0703 aetheris-plan-f-73.001.md FINESSE:f-73
// @aa-sdd GK-0704 aetheris-plan-f-74.001.md FINESSE:f-74
// @aa-sdd GK-0705 aetheris-plan-f-91.001.md FINESSE:f-91
// @aa-sdd GK-0706 aetheris-plan-f-95.001.md FINESSE:f-95
// @aa-sdd GK-0707 aetheris-plan-f-97.001.md FINESSE:f-97
/**
 * The {@code ThinkStrip} class is the thinking pane.
 * One instance slides up from the bottom on the notebook.
 * That instance puts the grip on the north edge.
 * Another instance slides down from the top on a VT100 panel.
 * That instance puts the grip on the south edge.
 * The strip paints, folds, and slides.
 * The {@link Host} decides which thought arrives.
 * The {@link Host} also decides whether the log is written.
 * The strip does not read a frame, a cell, or a channel.
 */
public final class ThinkStrip extends JPanel {

    private static final Color FIELD_BG = new Color(0x1a, 0x1a, 0x1a);
    private static final Color THINK_CYAN = new Color(0x00, 0xD7, 0xFF);
    /**
     * The color is the armed card and the clear mark.
     * {@link #VT_THINK_BODY} lightens this color.
     */
    static final Color SELECTION_SAPPHIRE = new Color(0x2e, 0x86, 0xff);
    /**
     * The color is the thinking body.
     * It is {@link #SELECTION_SAPPHIRE} lightened 33 percent toward white.
     * The notebook strip and the VT100 strip both use it.
     */
    static final Color VT_THINK_BODY = lightenTowardWhite(SELECTION_SAPPHIRE, 33);

    // @aa-sdd GK-0708 aetheris-plan-f-73.001.md FINESSE:f-73
    /**
     * The {@code Host} interface holds the facts the strip does not own.
     * A notebook host writes the vault log and the notebook chrome.
     * A VT100 host writes its own chrome file and leaves the vault log alone.
     */
    public interface Host {

        /**
         * The {@code maxHeight} method returns the tallest strip that still leaves the host surface in reach.
         *
         * @return The return value is that height in pixels.
         */
        int maxHeight();

        /**
         * The {@code relayout} method lays the host out again after a slide or a drag.
         */
        void relayout();

        /**
         * The {@code saveLog} method stores the pane text.
         *
         * @param text The text is the pane text, or empty after Clear.
         */
        void saveLog(String text);

        /**
         * The {@code loadLog} method returns the stored log.
         *
         * @return The return value is that log, or empty when this host keeps none.
         */
        String loadLog();

        /**
         * The {@code noteChrome} method remembers the chrome.
         * A write waits for {@link #saveChrome}.
         *
         * @param closed The flag is true when Close hid the pane.
         * @param height The height is the dragged height, or {@code 0} for the three-line height.
         */
        void noteChrome(boolean closed, int height);

        /**
         * The {@code saveChrome} method writes the chrome the host remembered.
         */
        void saveChrome();

        /**
         * The {@code cleared} method hears that Clear wiped the pane.
         * The host drops its open block.
         */
        void cleared();
    }

    private final SshClientTheme theme;
    private final Host host;
    private final boolean keepsLog;
    private final boolean alongTop;
    /**
     * The font is the app or terminal face for the output.
     * It stays null until {@link #setBodyFont(Font)}.
     */
    private Font bodyFont;
    private final JTextPane pane;
    private final JScrollPane scroll;
    private final JButton toggle;
    private final JButton closeButton;
    private final JButton clearButton;
    private final JPanel inner;
    private final JPanel grip;
    /**
     * The panel holds C and then Close.
     * It is used only while the strip is folded onto the grip.
     */
    private final JPanel foldedEast;
    private int chromeSize;
    private int userHeight;
    private int naturalHeight = 72;
    private int slideHeight = -1;
    private int dragY;
    private int dragH;
    private boolean minimized;
    private boolean closed;
    /**
     * The flag is true when the pane is up this session.
     * The file closed mark is a separate fact.
     */
    private boolean sessionOpen;
    private boolean opening;
    private Timer timer;

    /**
     * The constructor builds a hidden strip.
     * A chrome size below 8 is stored as 8.
     * The closed flag is stored as given.
     * The pane is not editable.
     * The pane background is {@code 0x1a1a1a}.
     * The caret color is {@code 0x00D7FF}.
     * A null body font sizes the scroll pane from the current face, or from a 12-point monospaced face.
     * That scroll height is three line heights plus 8.
     * The natural height is the preferred height while the user height is temporarily 0.
     * A natural height below 48 becomes 48.
     * The strip starts invisible.
     * {@code keepsLog} true says the log stays between sessions and is not stored on the turn.
     * {@code keepsLog} false says the text is not written to the vault log.
     * {@code alongTop} true puts the grip on the south edge with a south-resize cursor.
     * {@code alongTop} false puts the grip on the north edge with a north-resize cursor.
     *
     * @param theme The theme is the dark painter.
     * @param bodyFont The body font is the monospaced body, or null.
     * @param chromeSize The chrome size is the point size for the buttons.
     * @param keepsLog The flag is true when the vault log is written and the tips say so.
     * @param userHeight The user height is the dragged height, or {@code 0}.
     * @param closed The flag is true when a closed pane stays hidden until a thought opens it.
     * @param alongTop The flag is true when the grip sits on the south edge and the pane grows downward.
     * @param host The host owns the log and the chrome.
     */
    public ThinkStrip(SshClientTheme theme, Font bodyFont, int chromeSize, boolean keepsLog,
            int userHeight, boolean closed, boolean alongTop, Host host) {
        super(new BorderLayout());
        this.theme = theme;
        this.host = host;
        this.keepsLog = keepsLog;
        this.alongTop = alongTop;
        this.chromeSize = chromeSize < 8 ? 8 : chromeSize;
        this.userHeight = userHeight;
        this.closed = closed;
        theme.applyPanel(this);
        inner = new JPanel(new BorderLayout(6, 0));
        theme.applyPanel(inner);
        toggle = new JButton("v");
        toggle.setToolTipText(tip(keepsLog
                ? "Fold or restore the thinking text."
                        + "<br>Close hides the pane and keeps the log."
                        + " The log stays between sessions."
                        + " A closed pane stays closed when the app opens again."
                        + " It is not stored on the turn."
                : "Fold or restore the thinking text."
                        + "<br>Close hides the pane and keeps the height."
                        + " A closed pane stays closed when the app opens again."
                        + " The text is not written to the vault log."));
        fitButton(toggle);
        /**
         * The listener calls {@link ThinkStrip#fold}.
         *
         * @param e The event is the toggle click.
         */
        toggle.addActionListener(new ActionListener() {
            // @aa-sdd GK-0709 aetheris-plan-f-73.001.md FINESSE:f-73
            @Override
            public void actionPerformed(ActionEvent e) {
                fold();
            }
        });
        closeButton = new JButton("Close");
        closeButton.setToolTipText(tip(keepsLog
                ? "Close the thinking pane."
                        + "<br>The log stays."
                        + " A closed pane stays closed when the app opens again."
                        + " Clear wipes the pane and the saved log."
                : "Close the thinking pane."
                        + "<br>The height stays."
                        + " A closed pane stays closed when the app opens again."
                        + " The text is not written to the vault log."));
        fitButton(closeButton);
        /**
         * The listener calls {@link ThinkStrip#hideStrip}.
         *
         * @param e The event is the Close click.
         */
        closeButton.addActionListener(new ActionListener() {
            // @aa-sdd GK-0710 aetheris-plan-f-73.001.md FINESSE:f-73
            @Override
            public void actionPerformed(ActionEvent e) {
                hideStrip();
            }
        });
        pane = new JTextPane();
        pane.setEditable(false);
        pane.setBackground(FIELD_BG);
        pane.setForeground(bodyColor(alongTop));
        pane.setCaretColor(THINK_CYAN);
        scroll = new JScrollPane(pane);
        if (bodyFont != null) {
            setBodyFont(bodyFont);
        } else {
            Font face = pane.getFont();
            FontMetrics metrics = pane.getFontMetrics(face == null
                    ? new Font(Font.MONOSPACED, Font.PLAIN, 12) : face);
            scroll.setPreferredSize(new Dimension(240, metrics.getHeight() * 3 + 8));
        }
        theme.applyScrollPane(scroll);
        // The system scroll frame is the white line around this text.
        scroll.setBorder(null);
        scroll.setViewportBorder(null);
        scroll.getViewport().setBackground(FIELD_BG);
        /**
         * The panel paints the gray drag line across the grip.
         * A folded grip starts the line after the toggle.
         * A folded grip ends the line before the clear button.
         * A line with no room is not drawn.
         *
         * @param g The graphics is the grip surface.
         */
        grip = new JPanel(new BorderLayout()) {
            // @aa-sdd GK-0711 aetheris-plan-f-73.001.md FINESSE:f-73
            // @aa-sdd GK-0712 aetheris-plan-f-95.001.md FINESSE:f-95
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D pen = (Graphics2D) g.create();
                pen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                pen.setColor(new Color(0x8a, 0x8a, 0x8a));
                int y = getHeight() / 2;
                int left = 16;
                int right = getWidth() - 28;
                if (minimized && toggle.getParent() == grip && clearButton.getParent() != null) {
                    left = toggle.getWidth() + 6;
                    Point at = SwingUtilities.convertPoint(
                            clearButton.getParent(), clearButton.getX(), 0, grip);
                    right = at.x - 6;
                }
                if (right > left) {
                    pen.drawLine(left, y, right, y);
                }
                pen.dispose();
            }
        };
        theme.applyPanel(grip);
        grip.setCursor(Cursor.getPredefinedCursor(alongTop ? Cursor.S_RESIZE_CURSOR : Cursor.N_RESIZE_CURSOR));
        grip.setToolTipText(tip(alongTop
                ? "Drag the south edge to set the height of the thinking pane."
                : "Drag the north edge to set the height of the thinking pane."));
        /**
         * The listener presses and releases the grip.
         * Press calls {@link ThinkStrip#pressGrip}.
         * Release calls {@link ThinkStrip#releaseGrip}.
         *
         * @param e The event is the mouse event on the grip.
         */
        grip.addMouseListener(new MouseAdapter() {
            // @aa-sdd GK-0713 aetheris-plan-f-73.001.md FINESSE:f-73
            @Override
            public void mousePressed(MouseEvent e) {
                pressGrip(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                releaseGrip(e);
            }
        });
        /**
         * The listener drags the grip.
         * The drag calls {@link ThinkStrip#dragGrip}.
         *
         * @param e The event is the drag event.
         */
        grip.addMouseMotionListener(new MouseMotionAdapter() {
            // @aa-sdd GK-0714 aetheris-plan-f-73.001.md FINESSE:f-73
            @Override
            public void mouseDragged(MouseEvent e) {
                dragGrip(e);
            }
        });
        clearButton = new JButton("C");
        clearButton.setToolTipText(tip(keepsLog
                ? "Clear the thinking log."
                        + "<br>The saved log clears too."
                        + " The next thought accumulates from here."
                : "Clear the thinking text."
                        + "<br>The next thought accumulates from here."
                        + " It is not written to the vault log."));
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        clearButton.setMargin(new Insets(0, 6, 0, 6));
        clearButton.setFocusable(false);
        paintClear(clearButton);
        foldedEast = new JPanel(new BorderLayout(8, 0));
        foldedEast.setOpaque(false);
        /**
         * The listener calls {@link ThinkStrip#clearLog}.
         *
         * @param e The event is the clear click.
         */
        clearButton.addActionListener(new ActionListener() {
            // @aa-sdd GK-0715 aetheris-plan-f-73.001.md FINESSE:f-73
            @Override
            public void actionPerformed(ActionEvent e) {
                clearLog();
            }
        });
        grip.add(clearButton, BorderLayout.EAST);
        inner.add(toggle, BorderLayout.WEST);
        inner.add(scroll, BorderLayout.CENTER);
        inner.add(closeButton, BorderLayout.EAST);
        add(grip, alongTop ? BorderLayout.SOUTH : BorderLayout.NORTH);
        add(inner, BorderLayout.CENTER);
        int kept = this.userHeight;
        this.userHeight = 0;
        naturalHeight = getPreferredSize().height;
        if (naturalHeight < 48) {
            naturalHeight = 48;
        }
        this.userHeight = kept;
        setVisible(false);
    }

    /**
     * The {@code naturalHeight} method returns the stored three-line height.
     * The constructor stores at least 48.
     *
     * @return The return value is that height.
     */
    public int naturalHeight() {
        return naturalHeight;
    }

    /**
     * The {@code loadStoredLog} method reads the host log into the pane.
     * A strip that does not keep a log stays hidden.
     * A null host stays hidden.
     * A null or empty log stays hidden.
     * The text is inserted at the start with {@link #bodyAttrs}.
     * {@link #restyleTags} then paints lines that begin with the box-drawing character.
     * A {@link BadLocationException} hides the pane.
     * A closed pane stays hidden.
     * Any other stored log sets the session open and shows the pane.
     */
    public void loadStoredLog() {
        if (!keepsLog || host == null) {
            sessionOpen = false;
            setVisible(false);
            return;
        }
        String text = host.loadLog();
        if (text == null || text.length() == 0) {
            sessionOpen = false;
            setVisible(false);
            return;
        }
        try {
            StyledDocument doc = pane.getStyledDocument();
            doc.insertString(0, text, bodyAttrs());
            restyleTags();
            pinTail();
        } catch (BadLocationException ex) {
            sessionOpen = false;
            setVisible(false);
            return;
        }
        if (closed) {
            sessionOpen = false;
            setVisible(false);
            return;
        }
        sessionOpen = true;
        setVisible(true);
        scroll.setVisible(true);
    }

    /**
     * The {@code begin} method opens a block with the tag and the first chunk.
     * A null pane returns without writing.
     * {@link #pinTail} runs before the insert.
     * A blank tag becomes the word {@code model}.
     * The tag line is two box-drawing characters, the word, and two more.
     * A pane that already has text gets a blank line first.
     * A non-empty chunk is written in the body face.
     * A {@link BadLocationException} returns without opening the tail.
     * {@link #showTail} opens the pane.
     *
     * @param tag The tag is the model word.
     * @param text The text is the first chunk.
     */
    public void begin(String tag, String text) {
        if (pane == null) {
            return;
        }
        pinTail();
        String word = tagWord(tag);
        String line = "\u2500\u2500 " + word + " \u2500\u2500\n";
        try {
            StyledDocument doc = pane.getStyledDocument();
            int at = doc.getLength();
            if (at > 0) {
                doc.insertString(at, "\n", bodyAttrs());
            }
            doc.insertString(doc.getLength(), line, tagAttrs());
            if (text != null && text.length() > 0) {
                doc.insertString(doc.getLength(), text, bodyAttrs());
            }
        } catch (BadLocationException ex) {
            return;
        }
        showTail();
    }

    /**
     * The {@code append} method writes a later chunk of the open block.
     * A null pane returns without writing.
     * A null or empty chunk returns without writing.
     * {@link #pinTail} runs before the insert.
     * A {@link BadLocationException} returns without opening the tail.
     * {@link #showTail} opens the pane.
     *
     * @param text The text is the chunk.
     */
    public void append(String text) {
        if (pane == null || text == null || text.length() == 0) {
            return;
        }
        pinTail();
        try {
            StyledDocument doc = pane.getStyledDocument();
            doc.insertString(doc.getLength(), text, bodyAttrs());
        } catch (BadLocationException ex) {
            return;
        }
        showTail();
    }

    /**
     * The {@code text} method returns the pane text.
     * A null pane returns an empty string.
     * A {@link BadLocationException} returns an empty string.
     *
     * @return the text now in the pane
     */
    String text() {
        if (pane == null) {
            return "";
        }
        try {
            StyledDocument doc = pane.getStyledDocument();
            return doc.getText(0, doc.getLength());
        } catch (BadLocationException ex) {
            return "";
        }
    }

    /**
     * The {@code showLog} method replaces the pane text with one stored log.
     * The method does not write the log back.
     * An empty log hides the strip.
     * A closed strip stays hidden.
     * A log with text opens the strip unless it is closed, and the fold stays folded.
     * {@link #restyleTags} paints the banner lines.
     *
     * @param text the stored log, or empty
     */
    void showLog(String text) {
        // @aa-sdd GK-1132 aetheris-plan-f-225.001.md FINESSE:f-225
        if (pane == null) {
            return;
        }
        String body = text == null ? "" : text;
        try {
            StyledDocument doc = pane.getStyledDocument();
            doc.remove(0, doc.getLength());
            if (body.length() > 0) {
                doc.insertString(0, body, bodyAttrs());
                restyleTags();
                pinTail();
            }
        } catch (BadLocationException ex) {
            return;
        }
        if (body.length() == 0 || closed) {
            sessionOpen = false;
            setVisible(false);
            return;
        }
        sessionOpen = true;
        setVisible(true);
        if (scroll != null) {
            scroll.setVisible(!minimized);
        }
    }

    /**
     * The {@code finish} method marks the open block closed by doing nothing.
     * The method body is empty.
     * The pane stays up.
     * Close is the dismiss.
     */
    public void finish() {
    }

    /**
     * The {@code setBodyFont} method gives the output this face.
     * A null font returns without a change.
     * A null pane returns without a change.
     * Text already in the pane takes the family and the size.
     * The three-line scroll height follows the new size.
     * The scroll width stays the current preferred width when that width is above 0.
     * Otherwise the scroll width is 240.
     *
     * @param font The font is the monospaced body at the app size.
     */
    public void setBodyFont(Font font) {
        if (font == null || pane == null) {
            return;
        }
        this.bodyFont = font;
        pane.setFont(font);
        StyledDocument doc = pane.getStyledDocument();
        Style def = doc.getStyle(StyleContext.DEFAULT_STYLE);
        if (def != null) {
            StyleConstants.setFontFamily(def, font.getFamily());
            StyleConstants.setFontSize(def, font.getSize());
        }
        int n = doc.getLength();
        if (n > 0) {
            SimpleAttributeSet face = new SimpleAttributeSet();
            StyleConstants.setFontFamily(face, font.getFamily());
            StyleConstants.setFontSize(face, font.getSize());
            doc.setCharacterAttributes(0, n, face, false);
        }
        if (scroll != null) {
            FontMetrics metrics = pane.getFontMetrics(font);
            int lines = metrics.getHeight() * 3 + 8;
            Dimension pref = scroll.getPreferredSize();
            int width = 240;
            if (pref != null && pref.width > 0) {
                width = pref.width;
            }
            scroll.setPreferredSize(new Dimension(width, lines));
        }
    }

    /**
     * The {@code setChromeSize} method fits the buttons at one point size.
     * A size below 8 is stored as 8.
     * The toggle and Close are fitted.
     * The clear button is painted at the same size.
     *
     * @param size The size is the point size.
     */
    public void setChromeSize(int size) {
        chromeSize = size < 8 ? 8 : size;
        fitButton(toggle);
        fitButton(closeButton);
        paintClear(clearButton);
        revalidate();
    }

    /**
     * The {@code getPreferredSize} method returns the height the strip is using.
     * A slide height at or above 0 wins.
     * An open strip with a user height above 0 uses that height.
     * That height is capped by {@link #ceiling}.
     * That height is also raised to {@link #naturalHeight} when it is shorter.
     * Every other case returns the superclass size.
     *
     * @return The return value is that size.
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension pref = super.getPreferredSize();
        if (slideHeight >= 0) {
            return new Dimension(pref.width, slideHeight);
        }
        if (!minimized && userHeight > 0) {
            int h = userHeight;
            int max = ceiling();
            if (h > max) {
                h = max;
            }
            if (naturalHeight > 0 && h < naturalHeight) {
                h = naturalHeight;
            }
            return new Dimension(pref.width, h);
        }
        return pref;
    }

    /**
     * The {@code pinTail} method moves the viewport to the last line already in the buffer.
     * A null scroll pane returns without moving.
     * {@link #begin} and {@link #append} call this before the new block.
     * {@link #showTail} calls it after the insert.
     * The scrollbar is set to its maximum now.
     * A later runnable sets that maximum again after validation.
     */
    private void pinTail() {
        if (scroll == null) {
            return;
        }
        scroll.validate();
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
        final JScrollPane host = scroll;
        /**
         * The runnable validates the scroll pane and moves the bar to its maximum.
         */
        SwingUtilities.invokeLater(new Runnable() {
            // @aa-sdd GK-0716 aetheris-plan-f-102.001.md FINESSE:f-102
            // @aa-sdd GK-0717 aetheris-plan-f-219.001.md FINESSE:f-219
            // @aa-sdd GK-0718 aetheris-plan-f-73.001.md FINESSE:f-73
            // @aa-sdd GK-0719 aetheris-plan-f-95.001.md FINESSE:f-95
            @Override
            public void run() {
                host.validate();
                host.getVerticalScrollBar().setValue(host.getVerticalScrollBar().getMaximum());
            }
        });
    }

    /**
     * The {@code showTail} method marks the session open and shows the tail.
     * {@link #pinTail} runs again.
     * {@link #persistLog} writes the pane when this strip keeps a log.
     * A folded strip becomes visible and stays folded.
     * An open strip calls {@link #openStrip}.
     * A host lays the surface out again.
     */
    private void showTail() {
        sessionOpen = true;
        pinTail();
        persistLog();
        if (minimized) {
            setVisible(true);
        } else {
            openStrip();
        }
        revalidate();
        if (host != null) {
            host.relayout();
        }
    }

    /**
     * The {@code fold} method toggles the folded row.
     * A running slide timer is stopped.
     * The slide height is cleared.
     * A folded toggle reads {@code ^}.
     * An open toggle reads {@code v}.
     * The scroll pane is hidden while folded.
     * {@link #seatFoldedRow} moves the buttons.
     * A host lays the surface out again.
     */
    private void fold() {
        if (timer != null) {
            timer.stop();
        }
        opening = false;
        slideHeight = -1;
        minimized = !minimized;
        toggle.setText(minimized ? "^" : "v");
        holdShortLabel(toggle);
        scroll.setVisible(!minimized);
        seatFoldedRow();
        revalidate();
        if (host != null) {
            host.relayout();
        }
    }

    /**
     * The {@code seatFoldedRow} method places one folded row.
     * A folded strip hides the text row.
     * The mark, the drag line, C, and Close sit on the grip.
     * An open strip puts the mark and Close back on the text row.
     * C stays on the grip.
     */
    private void seatFoldedRow() {
        if (minimized) {
            inner.setVisible(false);
            scroll.setVisible(false);
            detach(toggle);
            grip.add(toggle, BorderLayout.WEST);
            detach(clearButton);
            detach(closeButton);
            foldedEast.removeAll();
            foldedEast.add(clearButton, BorderLayout.WEST);
            foldedEast.add(closeButton, BorderLayout.EAST);
            detach(foldedEast);
            grip.add(foldedEast, BorderLayout.EAST);
        } else {
            if (foldedEast.getParent() == grip) {
                grip.remove(foldedEast);
            }
            detach(clearButton);
            grip.add(clearButton, BorderLayout.EAST);
            detach(toggle);
            detach(scroll);
            detach(closeButton);
            inner.add(toggle, BorderLayout.WEST);
            inner.add(scroll, BorderLayout.CENTER);
            inner.add(closeButton, BorderLayout.EAST);
            inner.setVisible(true);
            scroll.setVisible(true);
        }
        revalidate();
    }

    /**
     * The {@code detach} method removes a child from its parent.
     * A null child returns without a change.
     * A child with no parent returns without a change.
     *
     * @param child The child is the component to detach.
     */
    private static void detach(Component child) {
        if (child == null) {
            return;
        }
        Container parent = child.getParent();
        if (parent != null) {
            parent.remove(child);
        }
    }

    /**
     * The {@code pressGrip} method records the drag start.
     * A button other than button 1 returns without a change.
     * A running slide timer is stopped.
     * A folded strip records the user height, or the natural height when the user height is 0.
     * An open strip records the current height, raised to the natural height when it is shorter.
     *
     * @param e The event is the mouse press.
     */
    private void pressGrip(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        if (timer != null) {
            timer.stop();
        }
        opening = false;
        dragY = e.getYOnScreen();
        if (minimized) {
            dragH = userHeight > 0 ? userHeight : naturalHeight;
            return;
        }
        dragH = getHeight();
        if (dragH < naturalHeight) {
            dragH = naturalHeight;
        }
    }

    /**
     * The {@code releaseGrip} method ends a drag.
     * A button other than button 1 returns without a change.
     * The slide height is cleared.
     * {@link #note} remembers the chrome.
     * A host writes that chrome.
     * A host lays the surface out again.
     *
     * @param e The event is the mouse release.
     */
    private void releaseGrip(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        slideHeight = -1;
        note();
        if (host != null) {
            host.saveChrome();
        }
        revalidate();
        if (host != null) {
            host.relayout();
        }
    }

    /**
     * The {@code dragGrip} method sets the height from the pointer.
     * A top strip grows when the pointer moves down.
     * A bottom strip grows when the pointer moves up.
     * The height stays at least the natural height.
     * The height stays at most {@link #ceiling}.
     * A folded strip stores the height and does not slide.
     * An open strip uses the height as the slide height and lays the host out again.
     *
     * @param e The event is the drag.
     */
    private void dragGrip(MouseEvent e) {
        int delta = alongTop ? e.getYOnScreen() - dragY : dragY - e.getYOnScreen();
        int next = dragH + delta;
        if (next < naturalHeight) {
            next = naturalHeight;
        }
        int max = ceiling();
        if (next > max) {
            next = max;
        }
        userHeight = next;
        if (minimized) {
            slideHeight = -1;
            note();
            return;
        }
        slideHeight = next;
        if (!isVisible()) {
            setVisible(true);
        }
        note();
        revalidate();
        if (host != null) {
            host.relayout();
        }
    }

    /**
     * The {@code clearLog} method wipes the pane.
     * A {@link BadLocationException} is ignored.
     * The next thought still appends.
     * {@link #persistLog} writes the empty pane when this strip keeps a log.
     * A host drops its open block.
     * The session stays open.
     * The scroll pane stays hidden while the strip is folded.
     */
    private void clearLog() {
        try {
            StyledDocument doc = pane.getStyledDocument();
            doc.remove(0, doc.getLength());
        } catch (BadLocationException ex) {
            // the next thought still appends
        }
        persistLog();
        if (host != null) {
            host.cleared();
        }
        sessionOpen = true;
        setVisible(true);
        scroll.setVisible(!minimized);
        revalidate();
        if (host != null) {
            host.relayout();
        }
    }

    /**
     * The {@code revealFromMenu} method answers Tools, Show Thinking.
     * {@link ToolsThinking#show} receives the session-open flag and the slide height.
     * A closed pane rises.
     * A fold opens so the text is visible.
     * A pane that is already showing stays at its height.
     * The log stays.
     * The closed mark on the file stays.
     * A pane that was closed stops a running timer and slides open.
     * The slide target is the user height, or the natural height, and at least 1.
     * The target is capped by {@link #ceiling}.
     */
    public void revealFromMenu() {
        boolean open = sessionOpen;
        ToolsThinking.Seat seat = ToolsThinking.show(open, slideHeight);
        sessionOpen = seat.open;
        if (!open && timer != null) {
            timer.stop();
            opening = false;
        }
        minimized = seat.folded;
        toggle.setText(minimized ? "^" : "v");
        holdShortLabel(toggle);
        scroll.setVisible(!minimized);
        seatFoldedRow();
        if (!open) {
            setVisible(true);
            slideHeight = seat.slide;
            opening = true;
            int target = userHeight > 0 ? userHeight : naturalHeight;
            if (target < 1) {
                target = getPreferredSize().height;
            }
            if (target < 1) {
                target = 1;
            }
            int max = ceiling();
            if (target > max) {
                target = max;
            }
            slide(target, true);
            return;
        }
        setVisible(true);
        revalidate();
        if (host != null) {
            host.relayout();
        }
    }

    /**
     * The {@code openStrip} method shows an open strip.
     * The session is marked open.
     * A folded strip returns without sliding.
     * A visible strip with a height above 0 and no slide returns without sliding.
     * A slide that is already opening returns without starting another.
     * A closed mark is cleared, remembered, and written.
     * A strip that was hidden starts the slide at height 0.
     * The target is the user height, or the natural height, and at least 1.
     * The target is capped by {@link #ceiling}.
     */
    private void openStrip() {
        sessionOpen = true;
        if (minimized) {
            return;
        }
        if (isVisible() && getHeight() > 0 && slideHeight < 0) {
            return;
        }
        if (opening && timer != null && timer.isRunning()) {
            return;
        }
        boolean wasClosed = !isVisible() || getHeight() == 0;
        if (closed) {
            closed = false;
            note();
            if (host != null) {
                host.saveChrome();
            }
        }
        slideHeight = -1;
        setVisible(true);
        scroll.setVisible(true);
        int target = userHeight > 0 ? userHeight : naturalHeight;
        if (target < 1) {
            target = getPreferredSize().height;
        }
        if (target < 1) {
            target = 1;
        }
        int max = ceiling();
        if (target > max) {
            target = max;
        }
        if (wasClosed) {
            slideHeight = 0;
            opening = true;
            slide(target, true);
        }
    }

    /**
     * The {@code hideStrip} method closes the pane.
     * The session is marked closed.
     * A strip that is already hidden returns without writing.
     * The closed mark is set, remembered, and written.
     * A missing slide height starts from the current height.
     * The slide target is 0.
     */
    private void hideStrip() {
        sessionOpen = false;
        if (!isVisible()) {
            return;
        }
        closed = true;
        note();
        if (host != null) {
            host.saveChrome();
        }
        opening = false;
        if (slideHeight < 0) {
            slideHeight = getHeight();
        }
        slide(0, false);
    }

    /**
     * The {@code slide} method moves the height toward a target.
     * An existing timer is stopped.
     * A missing slide height starts from the current height.
     * The timer ticks every 16 milliseconds.
     * Each step is at least 12 pixels, or one quarter of the remaining span.
     * A rising slide stops at the target.
     * A falling slide stops at the target, hides the strip, and restores the open row.
     * A host lays the surface out again on each tick and when the slide ends.
     *
     * @param target The target is the height in pixels.
     * @param rising The flag is true when the pane is growing.
     */
    private void slide(final int target, final boolean rising) {
        if (timer != null) {
            timer.stop();
        }
        if (slideHeight < 0) {
            slideHeight = getHeight();
        }
        /**
         * The listener steps the slide height toward the target.
         * The timer stops when the height reaches the target.
         *
         * @param e The event is the timer tick.
         */
        timer = new Timer(16, new ActionListener() {
            // @aa-sdd GK-0720 aetheris-plan-f-73.001.md FINESSE:f-73
            @Override
            public void actionPerformed(ActionEvent e) {
                int h = slideHeight < 0 ? getHeight() : slideHeight;
                int span = target > h ? target - h : h - target;
                int step = Math.max(12, span / 4);
                int next = rising ? h + step : h - step;
                if (rising && next > target) {
                    next = target;
                }
                if (!rising && next < target) {
                    next = target;
                }
                slideHeight = next;
                revalidate();
                if (host != null) {
                    host.relayout();
                }
                if (next == target) {
                    ((Timer) e.getSource()).stop();
                    opening = false;
                    if (!rising) {
                        setVisible(false);
                        slideHeight = -1;
                        minimized = false;
                        toggle.setText("v");
                        holdShortLabel(toggle);
                        scroll.setVisible(true);
                        seatFoldedRow();
                    } else {
                        slideHeight = -1;
                    }
                    revalidate();
                    if (host != null) {
                        host.relayout();
                    }
                }
            }
        });
        timer.start();
    }

    /**
     * The {@code ceiling} method returns the tallest allowed height.
     * A null host returns 480.
     * A host height below the natural height returns the natural height.
     *
     * @return The return value is that height.
     */
    private int ceiling() {
        if (host == null) {
            return 480;
        }
        int max = host.maxHeight();
        if (max < naturalHeight) {
            return naturalHeight;
        }
        return max;
    }

    /**
     * The {@code note} method asks the host to remember the chrome.
     * A null host returns without a call.
     * The height passed is the user height.
     * A user height of 0 means the three-line height.
     */
    private void note() {
        if (host != null) {
            host.noteChrome(closed, userHeight);
        }
    }

    /**
     * The {@code persistLog} method writes the pane text when this strip keeps a log.
     * A strip that does not keep a log returns without writing.
     * A null host returns without writing.
     * A {@link BadLocationException} returns without writing.
     */
    private void persistLog() {
        if (!keepsLog || host == null) {
            return;
        }
        String text;
        try {
            StyledDocument doc = pane.getStyledDocument();
            text = doc.getText(0, doc.getLength());
        } catch (BadLocationException ex) {
            return;
        }
        host.saveLog(text);
    }

    /**
     * The {@code restyleTags} method paints each tag line in the tag face.
     * A line that begins with the box-drawing character takes {@link #tagAttrs}.
     * The newline is not part of that run.
     * A {@link BadLocationException} returns and leaves the rest of the pane alone.
     */
    private void restyleTags() {
        StyledDocument doc = pane.getStyledDocument();
        int n = doc.getLength();
        int i = 0;
        SimpleAttributeSet tag = tagAttrs();
        while (i < n) {
            String rest;
            try {
                rest = doc.getText(i, n - i);
            } catch (BadLocationException ex) {
                return;
            }
            int nl = rest.indexOf('\n');
            int len = nl < 0 ? rest.length() : nl;
            if (len > 0 && rest.charAt(0) == '\u2500') {
                doc.setCharacterAttributes(i, len, tag, false);
            }
            if (nl < 0) {
                break;
            }
            i = i + len + 1;
        }
    }

    /**
     * The {@code tagWord} method turns a model id into the tag word.
     * A null id is empty.
     * A newline is dropped.
     * A carriage return is dropped.
     * A box-drawing character is dropped.
     * A blank result returns {@code model}.
     * A word longer than 80 characters keeps the first 80.
     *
     * @param model The model is the model id.
     * @return The return value is that word.
     */
    private static String tagWord(String model) {
        String raw = model == null ? "" : model;
        StringBuffer word = new StringBuffer();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == '\n' || c == '\r' || c == '\u2500') {
                continue;
            }
            word.append(c);
        }
        String text = word.toString().trim();
        if (text.length() == 0) {
            return "model";
        }
        if (text.length() > 80) {
            return text.substring(0, 80);
        }
        return text;
    }

    /**
     * The {@code tagAttrs} method builds the tag face.
     * The foreground is the cyan {@code 0x00D7FF}.
     * The run is bold.
     * {@link #seatFace} adds the body family and size.
     *
     * @return The return value is that attribute set.
     */
    private SimpleAttributeSet tagAttrs() {
        SimpleAttributeSet tag = new SimpleAttributeSet();
        StyleConstants.setForeground(tag, THINK_CYAN);
        StyleConstants.setBold(tag, true);
        seatFace(tag);
        return tag;
    }

    /**
     * The {@code bodyColor} method returns the thinking body color.
     * The notebook strip and the VT100 strip wear the lighter sapphire.
     * {@code alongTop} does not change the color.
     * Both branches return {@link #VT_THINK_BODY}.
     *
     * @param alongTop The flag is true when the strip sits on a VT100 panel.
     * @return The return value is {@link #VT_THINK_BODY}.
     */
    static Color bodyColor(boolean alongTop) {
        if (alongTop) {
            return VT_THINK_BODY;
        }
        return VT_THINK_BODY;
    }

    /**
     * The {@code seatFace} method puts the live body face on one run.
     * The font is {@code bodyFont} when it is set.
     * Otherwise the font is the pane font.
     * A missing font returns without a change.
     * Color already on the run stays.
     * Weight already on the run stays.
     *
     * @param attrs The attributes are the run being written.
     */
    private void seatFace(SimpleAttributeSet attrs) {
        Font font = bodyFont;
        if (font == null && pane != null) {
            font = pane.getFont();
        }
        if (font == null) {
            return;
        }
        StyleConstants.setFontFamily(attrs, font.getFamily());
        StyleConstants.setFontSize(attrs, font.getSize());
    }

    /**
     * The {@code lightenTowardWhite} method mixes a percent of the way from a color toward white.
     * Each channel is {@link #lightenChannel}.
     * Each channel rounds half up.
     *
     * @param color The color is the source.
     * @param percent The percent is 0 through 100.
     * @return The return value is the lighter color.
     */
    static Color lightenTowardWhite(Color color, int percent) {
        return new Color(
                lightenChannel(color.getRed(), percent),
                lightenChannel(color.getGreen(), percent),
                lightenChannel(color.getBlue(), percent));
    }

    /**
     * The {@code lightenChannel} method moves one channel toward 255.
     * The added amount is the remaining distance times the percent, plus 50, divided by 100.
     * A result above 255 returns 255.
     *
     * @param value The value is the channel from 0 through 255.
     * @param percent The percent is the mix toward white.
     * @return The return value is the lighter channel.
     */
    private static int lightenChannel(int value, int percent) {
        int toward = 255 - value;
        int add = (toward * percent + 50) / 100;
        int next = value + add;
        if (next > 255) {
            return 255;
        }
        return next;
    }

    /**
     * The {@code bodyAttrs} method builds the body face.
     * The foreground is {@link #bodyColor}.
     * {@link #seatFace} adds the body family and size.
     *
     * @return The return value is that attribute set.
     */
    private SimpleAttributeSet bodyAttrs() {
        SimpleAttributeSet body = new SimpleAttributeSet();
        StyleConstants.setForeground(body, bodyColor(alongTop));
        seatFace(body);
        return body;
    }

    /**
     * The {@code fitButton} method shortens one chrome button.
     * A null button returns without a change.
     * The client property {@code aetheris.short} is set to true.
     * The target height is 70 percent of the preferred height, and at least 1.
     * The font shrinks while it is above 8 and the preferred height is still above that target.
     * {@link #holdShortLabel} then keeps the word readable.
     *
     * @param button The button is a chrome button.
     */
    private void fitButton(AbstractButton button) {
        if (button == null) {
            return;
        }
        button.putClientProperty("aetheris.short", Boolean.TRUE);
        button.setPreferredSize(null);
        button.setMaximumSize(null);
        Font current = button.getFont();
        if (current == null) {
            current = new Font(Font.SANS_SERIF, Font.PLAIN, chromeSize);
        }
        button.setFont(current.deriveFont((float) chromeSize));
        theme.applyFlatButtonBorder(button);
        int full = button.getPreferredSize().height;
        int target = (int) Math.round(full * 0.7);
        if (target < 1) {
            target = 1;
        }
        theme.applyButton(button);
        int size = chromeSize;
        button.setFont(button.getFont().deriveFont((float) size));
        while (size > 8 && button.getPreferredSize().height > target) {
            size--;
            button.setFont(button.getFont().deriveFont((float) size));
        }
        Dimension pref = button.getPreferredSize();
        button.setPreferredSize(new Dimension(pref.width, target));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, target));
        holdShortLabel(button);
    }

    /**
     * The {@code holdShortLabel} method keeps a short label wide enough for its word.
     * The short fit can lock a width under the word.
     * The short fit can lock a height under the word.
     * A label of one or two characters takes two ems after the border is on.
     * A label already under its word takes those two ems as well.
     * The height stays the short fit when the word fits.
     * The height rises when the short fit is under the word.
     * The button text stays the specified text.
     * A null button returns without a change.
     * A width below 1 becomes 1.
     * A height below 1 becomes 1.
     *
     * @param button The button is a short chrome button.
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
     * The {@code paintRustClear} method paints C on the canvas strip and on the VT100 strip.
     * The client property {@code aetheris.mark} is set to true.
     * The client property {@code aetheris.rust} is set to true.
     * {@link AetherisNotebookPanel#seatRustLetter} paints the letter.
     * The fill is sapphire.
     * The letter is white.
     * The stroke is one white pixel.
     * The corner radius is 2.
     * The box follows the Rust pin face at {@code pointSize}.
     * The box is at least 22 by 22.
     * The background is then {@link #SELECTION_SAPPHIRE}.
     * The foreground is then white.
     *
     * @param button The button is the clear mark.
     * @param pointSize The point size is the app point size.
     */
    static void paintRustClear(AbstractButton button, int pointSize) {
        button.putClientProperty("aetheris.mark", Boolean.TRUE);
        button.putClientProperty("aetheris.rust", Boolean.TRUE);
        AetherisNotebookPanel.seatRustLetter(button, pointSize);
        button.setBackground(SELECTION_SAPPHIRE);
        button.setForeground(Color.WHITE);
    }

    /**
     * The {@code paintClear} method paints the clear mark at the chrome size.
     * The call is {@link #paintRustClear}.
     *
     * @param button The button is the clear mark.
     */
    private void paintClear(AbstractButton button) {
        paintRustClear(button, chromeSize);
    }

    /**
     * The {@code tip} method wraps one tooltip body in HTML.
     *
     * @param body The body is the tooltip text.
     * @return The return value is {@code <html>} plus the body plus {@code </html>}.
     */
    private static String tip(String body) {
        return "<html>" + body + "</html>";
    }
}
