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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.aetheris.config.AetherisYaml;
import org.casserole.mood.MoodDesk;
import org.aetheris.swing.AetherisNotebookPanel;
import org.aetheris.swing.AetherisWorker;
import org.aetheris.wire.AmwpClientListener;
import org.aetheris.wire.AmwpCodec;
import org.aetheris.wire.AmwpConstants;
import org.aetheris.wire.AmwpFrame;
import org.apache.logging.log4j.core.config.Configurator;
import org.roux.token.HeuristicTokenCounter;
import org.roux.token.StoredCountEstimator;
import org.roux.token.Transcript;

import com.jediterm.core.util.TermSize;

// @aa-sdd PK-0041 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-3668 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-0050 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0054 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-3680 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-0058 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0060 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-3684 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-0084 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0101 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-3691 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-0107 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0120 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-4121 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-3700 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-0287 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-4157 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0839 PK-0886 PK-0933 PK-0980 PK-1027 PK-1074 PK-1121 PK-1168 PK-1215 PK-1262 PK-1309 PK-1356 PK-1403 PK-1450 PK-1497 PK-1544 PK-1592 PK-1639 PK-1686 PK-1733 PK-1780 PK-1827 PK-1874 PK-1921 PK-1968 PK-2015 PK-2063 PK-2110 PK-2157 PK-2204 PK-2252 PK-2300 PK-2348 PK-2395 PK-2442 PK-2490 PK-2537 PK-2584 PK-2631 PK-2679 PK-2726 PK-2774 PK-2821 PK-2868 PK-2915 PK-2962 PK-3009 PK-3056 PK-3103 PK-3150 PK-3198 PK-3245 PK-3292 PK-3340 PK-3387 PK-3434 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-4442 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-3500 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3910 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-4044 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-3516 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3595 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3613 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-0022 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-3621 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3624 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3655 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4672 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
// @aa-sdd PK-3501 docs/specs/java/org/aetheris/ssh/ssh.spec.md
/**
 * The {@code SshClientApp} window is the dark SSH client.
 * JediTerm is the terminal widget inside each pane.
 * The frame holds the {@link FoldingPanel}, the {@link SshConnectionPanel}, the {@link TerminalSession} list, and the {@link AetherisNotebookPanel}.
 * Connect and disconnect go through {@link SshSessionController}.
 * The window uses the Metal look from {@link SshClientTheme}.
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
public class SshClientApp extends JFrame
    implements SshConnectionPanel.Listener, TerminalSession.Host, AetherisNotebookPanel.Host {

    /**
     * The {@code MAX_PASTE_CHARS} limit is the longest paste this window will send.
     * The value is {@link ConsolePaste#MAX_CHARS}.
     */
    private static final int MAX_PASTE_CHARS = ConsolePaste.MAX_CHARS;
    /**
     * The {@code LARGE_FILE} text is the notice before a large import.
     * The import still runs after the notice.
     * The text is {@code This file is large. The import continues.}
     */
    private static final String LARGE_FILE = "This file is large. The import continues.";

    private final SshClientProperties properties;
    private final SshClientTheme theme;
    private final SshConnectionPanel connectionPanel;
    private final DarkJediTermSettings termSettings;
    private final ArrayList<TerminalSession> sessions;
    private TerminalSession focused;
    private final JPanel terminalHost;
    private final JTabbedPane productTabs;
    private final FoldingPanel foldingPanel;
    private final JLabel statusLabel;
    private final JLabel statusUserLabel;
    /**
     * The {@code composerHeat} bar shows the token estimate on the SSH status row.
     * The notebook keeps a separate token display.
     */
    private ComposerHeatbar composerHeat;
    private JLabel composerWord;
    private OllamaTokenDip composerDip;
    private Timer composerHeatTimer;
    /**
     * The {@code sshEdits} queue counts SSH token edits off the Swing thread.
     * The keyboard path only posts an edit.
     */
    private final SshEditQueue sshEdits = new SshEditQueue();
    /**
     * The {@code sshEditSeq} value is the newest edit number.
     * A result from an older number does not paint.
     */
    private long sshEditSeq;
    /**
     * The {@code linkWord} text is the link word on the status line.
     * It starts as {@code Disconnected}.
     * The Aetheris tab keeps its own status line.
     */
    private String linkWord = "Disconnected";
    private String linkHost = "";
    private String linkPort = "";
    /**
     * The {@code aetherisStatusText} value is the last Aetheris status line.
     * A missing value falls back to the notebook strip.
     */
    private String aetherisStatusText;
    private final Timer resizeSaveTimer;
    private final AetherisYaml aetherisYaml;
    private final AetherisWorker aetherisWorker;
    private final AetherisNotebookPanel aetherisPanel;
    /**
     * The {@code aimedSessions} set records connectors that already have a listener.
     * A later aim does not remove that listener.
     */
    private final HashSet<SshTtyConnector> aimedSessions = new HashSet<SshTtyConnector>();
    private int aetherisEpoch;
    /**
     * The {@code suppressProductTabWrite} flag blocks a programmatic tab change from storing the product tab.
     */
    private boolean suppressProductTabWrite;
    /**
     * The {@code restoreAetheris} flag asks the next successful connection to select the Aetheris tab.
     */
    private boolean restoreAetheris;

    /**
     * The {@code normalFrame} rectangle is the restored window bounds.
     * A maximized frame does not replace it.
     */
    private Rectangle normalFrame;

    /**
     * The constructor builds the window.
     * A null yaml, or a blank title, uses {@link AetherisYaml#DEFAULT_TITLE}.
     * Any other title is trimmed.
     * A null yaml is replaced with a new {@link AetherisYaml}.
     * The notebook, the first {@link TerminalSession}, and the connection panel are created here.
     *
     * The close operation does nothing until the confirm dialog runs.
     * The SSH tab is enabled.
     * The Aetheris tab starts disabled.
     * The status word starts as {@code est. tokens: 1}.
     * The token timer repeats every 200 milliseconds.
     * The resize timer waits 400 milliseconds and does not repeat.
     * The minimum size is 640 by 480.
     *
     * A saved frame that meets a screen is restored.
     * Any other open uses {@link #seatOpeningFrame}.
     * A saved maximized flag sets both maximize bits.
     * A stored Aetheris product tab sets {@code restoreAetheris}.
     *
     * @param properties The persistence store.
     * @param theme The dark painter.
     * @param yaml The Aetheris settings. A null value is replaced.
     */
    public SshClientApp(SshClientProperties properties, SshClientTheme theme, AetherisYaml yaml) {
        super(yaml == null || yaml.getTitle() == null || yaml.getTitle().trim().length() == 0
                ? AetherisYaml.DEFAULT_TITLE : yaml.getTitle().trim());
        this.properties = properties;
        this.theme = theme;
        this.aetherisYaml = yaml == null ? new AetherisYaml() : yaml;
        this.aetherisWorker = new AetherisWorker();
        this.aetherisPanel = new AetherisNotebookPanel(theme, aetherisWorker, this.aetherisYaml, this,
                properties.getFontSize());
        this.termSettings = new DarkJediTermSettings(properties);
        this.sessions = new ArrayList<TerminalSession>();
        this.connectionPanel = new SshConnectionPanel(properties, theme, this);
        this.focused = new TerminalSession(termSettings, theme, this);
        this.sessions.add(this.focused);
        wireConsoleImport(this.focused);
        this.terminalHost = new JPanel(new BorderLayout());
        theme.applyPanel(this.terminalHost);
        this.terminalHost.add(this.focused.getPanel(), BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(theme.getFrameBackground());
        getContentPane().setBackground(theme.getFrameBackground());
        setLayout(new BorderLayout());

        productTabs = new JTabbedPane();
        productTabs.addTab("SSH", terminalHost);
        productTabs.addTab("Aetheris", aetherisPanel);
        productTabs.setEnabledAt(1, false);
        /**
         * The listener stores the product tab and repaints the status.
         * A suppressed write skips the store and clears nothing.
         * Any other change clears {@code restoreAetheris}.
         * Index {@code 1} stores the Aetheris product tab.
         * Any other index stores the SSH product tab.
         * {@link FoldingPanel#syncPlates} runs when the folding panel exists.
         */
        productTabs.addChangeListener(new ChangeListener() {
            // @aa-sdd PK-4443 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-3517 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-3614 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!suppressProductTabWrite) {
                    restoreAetheris = false;
                    String word = productTabs.getSelectedIndex() == 1
                            ? SshClientProperties.PRODUCT_TAB_AETHERIS
                            : SshClientProperties.PRODUCT_TAB_SSH;
                    properties.setProductTab(word);
                }
                paintFrameStatus();
                if (foldingPanel != null) {
                    foldingPanel.syncPlates();
                }
            }
        });
        /**
         * The listener clears a pending Aetheris restore when SSH is pressed.
         * A suppressed write returns immediately.
         * A false restore flag returns immediately.
         * A press that is not on tab {@code 0} returns immediately.
         * The restore flag is cleared.
         * The stored product tab becomes SSH.
         */
        productTabs.addMouseListener(new MouseAdapter() {
            // @aa-sdd PK-4444 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-3518 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void mousePressed(MouseEvent e) {
                if (suppressProductTabWrite || !restoreAetheris) {
                    return;
                }
                int index = productTabs.indexAtLocation(e.getX(), e.getY());
                if (index != 0) {
                    return;
                }
                restoreAetheris = false;
                properties.setProductTab(SshClientProperties.PRODUCT_TAB_SSH);
            }
        });
        /**
         * The picker selects a product tab from the folded row.
         * A suppressed write returns immediately.
         * Index {@code 0} during a pending restore stores the SSH product tab and clears the flag.
         * An index below zero returns immediately.
         * An index past the last tab returns immediately.
         * A disabled tab returns immediately.
         * The already selected index returns immediately.
         * Any other index is selected.
         */
        foldingPanel = new FoldingPanel(connectionPanel, productTabs, theme, new FoldingPanel.BarPick() {
            // @aa-sdd PK-3615 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void pressed(int index) {
                if (suppressProductTabWrite) {
                    return;
                }
                if (index == 0 && restoreAetheris) {
                    restoreAetheris = false;
                    properties.setProductTab(SshClientProperties.PRODUCT_TAB_SSH);
                }
                if (index < 0 || productTabs.getTabCount() <= index) {
                    return;
                }
                if (!productTabs.isEnabledAt(index)) {
                    return;
                }
                if (productTabs.getSelectedIndex() == index) {
                    return;
                }
                productTabs.setSelectedIndex(index);
            }
        });
        add(foldingPanel, BorderLayout.CENTER);
        installMenu();
        // @aa-sdd PK-3708 docs/specs/java/org/aetheris/ssh/ssh.spec.md
        foldingPanel.seatThink(aetherisPanel.thinkCluster());

        statusLabel = new JLabel();
        statusUserLabel = new JLabel("");
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(theme.getStatusBackground());
        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        statusLabel.setForeground(theme.getStatusForeground());
        statusLabel.setBackground(theme.getStatusBackground());
        statusUserLabel.setForeground(theme.getStatusForeground());
        statusUserLabel.setBackground(theme.getStatusBackground());
        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(statusUserLabel, BorderLayout.EAST);
        JPanel statusColumn = new JPanel(new BorderLayout());
        statusColumn.setBackground(theme.getStatusBackground());
        JPanel heatRow = new JPanel(new BorderLayout());
        heatRow.setBackground(theme.getStatusBackground());
        heatRow.setBorder(BorderFactory.createEmptyBorder(2, 8, 0, 8));
        composerHeat = new ComposerHeatbar();
        composerWord = new JLabel("est. tokens: 1");
        composerWord.setForeground(ComposerHeatbar.GREEN);
        heatRow.add(composerHeat, BorderLayout.CENTER);
        heatRow.add(composerWord, BorderLayout.EAST);
        statusColumn.add(heatRow, BorderLayout.NORTH);
        statusColumn.add(statusBar, BorderLayout.CENTER);
        add(statusColumn, BorderLayout.SOUTH);
        /**
         * The callback repaints the token word after a count settles.
         * The settled count is not written into the word.
         * {@link #feedComposerHeat} paints the stored integer.
         */
        composerDip = new OllamaTokenDip(new OllamaTokenDip.Seat() {
            // @aa-sdd PK-0102 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-3519 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void settled(int count) {
                // The word stays the ring tally. A settled host count does not replace it.
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
                feedComposerHeat();
            }
        });
        /**
         * The timer posts an SSH edit and repaints the token bar.
         * An Aetheris tab skips the edit.
         * A missing focused session skips the edit.
         * {@link #feedComposerHeat} runs on every tick.
         */
        composerHeatTimer = new Timer(200, new ActionListener() {
            // @aa-sdd PK-0103 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0125 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-3616 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void actionPerformed(ActionEvent event) {
                // The tick paints the stored integer. It does not count.
                // @aa-sdd PK-3774 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                if (productTabs != null
                        && ToolsThinking.front(productTabs.getSelectedIndex()) != ToolsThinking.Plate.AETHERIS
                        && focused != null) {
                    offerSshEdit(focused);
                }
                feedComposerHeat();
            }
        });
        composerHeatTimer.start();

        setStatusDisconnected();

        setMinimumSize(new Dimension(640, 480));
        Rectangle placed = properties.getFrameBounds();
        if (frameOnScreen(placed)) {
            setBounds(placed);
        } else {
            seatOpeningFrame();
        }
        normalFrame = new Rectangle(getBounds());
        if (properties.isFrameMaximized()) {
            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }

        /**
         * The timer saves the frame after a resize or a move.
         * {@link #saveFrameState} does the write.
         */
        resizeSaveTimer = new Timer(400, new ActionListener() {
            // @aa-sdd PK-3625 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-3502 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFrameState();
            }
        });
        resizeSaveTimer.setRepeats(false);

        /**
         * The listener restarts the save timer when the frame changes.
         * A resize also reports the new width and height to {@link FoldingPanel#noteSpan}.
         * A move only restarts the timer.
         */
        addComponentListener(new ComponentAdapter() {
            // @aa-sdd PK-4445 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-3626 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-3503 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void componentResized(ComponentEvent e) {
                resizeSaveTimer.restart();
                // @aa-sdd PK-3496 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                foldingPanel.noteSpan(getWidth(), getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                resizeSaveTimer.restart();
            }
        });

        /**
         * The listener confirms an exit and paints the caption when the window opens.
         * A closing event calls {@link #confirmAndExit}.
         * An opened event calls {@link #paintCaption}.
         */
        addWindowListener(new WindowAdapter() {
            // @aa-sdd PK-0664 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-3504 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void windowClosing(WindowEvent e) {
                confirmAndExit();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                paintCaption();
            }
        });
        /**
         * The listener restarts the save timer when the window state changes.
         * {@link #paintCaption} runs after that restart.
         */
        addWindowStateListener(new WindowAdapter() {
            // @aa-sdd PK-3520 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void windowStateChanged(WindowEvent e) {
                resizeSaveTimer.restart();
                paintCaption();
            }
        });
        applyWindowIcon();
        installPasteInterceptor();
        installPaneClickTracker();
        selectSession(this.focused);
        if (SshClientProperties.PRODUCT_TAB_AETHERIS.equals(properties.getProductTab())) {
            restoreAetheris = true;
        }
    }

    /**
     * The {@code printConsoleBanner} method prints the console banner.
     * The text comes from the resource {@code /aetheris-banner.txt}.
     * A missing resource returns immediately.
     * The bytes are printed as UTF-8.
     * A read failure leaves the window able to open.
     * The stream is closed afterward.
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
    private static void printConsoleBanner() {
        InputStream in = SshClientApp.class.getResourceAsStream("/aetheris-banner.txt");
        if (in == null) {
            return;
        }
        try {
            ByteArrayOutputStream raw = new ByteArrayOutputStream();
            byte[] chunk = new byte[1024];
            int n;
            while ((n = in.read(chunk)) >= 0) {
                if (n == 0) {
                    break;
                }
                raw.write(chunk, 0, n);
            }
            System.out.println(new String(raw.toByteArray(), StandardCharsets.UTF_8));
        } catch (IOException ignored) {
            // the window still opens
        } finally {
            try {
                in.close();
            } catch (IOException ignored) {
                // close
            }
        }
    }

    /**
     * The {@code main} method starts the window.
     * {@link #printConsoleBanner} runs first.
     * Log4j is initialized from {@code classpath:log4j2-shell.properties}.
     * A runtime failure from that initialize is ignored.
     * {@link AetherisYaml#ensureHomeFile} writes the home file when it is absent.
     * {@link AetherisYaml#load} reads the arguments.
     * {@link SshClientProperties#load} reads the client file.
     * {@link SshClientProperties#saveIfFileMissing} writes a missing file.
     * The theme defaults are installed before the window is created.
     * The window is created and shown on the Swing thread.
     *
     * @param args The program arguments. {@link AetherisYaml#load} reads them.
     */
    public static void main(String[] args) {
        printConsoleBanner();
        try {
            Configurator.initialize(null, "classpath:log4j2-shell.properties");
        } catch (RuntimeException ignored) {
        }
        AetherisYaml.ensureHomeFile();
        final AetherisYaml yaml = AetherisYaml.load(args);
        SshClientProperties properties = SshClientProperties.load();
        properties.saveIfFileMissing();
        final SshClientTheme theme = new SshClientTheme(properties);
        theme.installLookAndFeelDefaults();
        final SshClientProperties props = properties;
        /**
         * The runnable creates the window and shows it.
         * The properties, the theme, and the yaml from {@code main} are used.
         */
        SwingUtilities.invokeLater(new Runnable() {
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
            @Override
            public void run() {
                SshClientApp app = new SshClientApp(props, theme, yaml);
                app.setVisible(true);
            }
        });
    }

    /**
     * The {@code connectRequested} method connects the focused pane.
     * A live username that already matches the form returns immediately.
     * An empty live name does not take that shortcut.
     * The form is stored as the last request.
     * A connected pane reconnects.
     * Any other state starts a connect.
     */
    @Override
    public void connectRequested() {
        TerminalSession session = requireFocused();
        SshSessionController.ConnectRequest request = buildConnectRequest(session);
        String live = liveUserName(session);
        if (live.length() > 0 && live.equals(request.getUsername())) {
            return;
        }
        session.setLastRequest(request);
        if (session.getState() == SshConnectionState.CONNECTED) {
            session.getController().reconnect(request);
            return;
        }
        session.getController().connect(request);
    }

    /**
     * The {@code usernameChosen} method opens the connection fields.
     * A missing folding panel returns immediately.
     * {@link FoldingPanel#showFields} opens the bar.
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
    @Override
    public void usernameChosen() {
        if (foldingPanel == null) {
            return;
        }
        foldingPanel.showFields();
    }

    /**
     * The {@code liveUserName} method returns the username on a live link.
     * A null session returns an empty string.
     * A session that is not connected returns an empty string.
     * A missing connector returns an empty string.
     * A connector that is not connected returns an empty string.
     * A missing last request, or a null username, returns an empty string.
     *
     * @param session The focused pane.
     * @return The username, or an empty string.
     */
    private String liveUserName(TerminalSession session) {
        if (session == null || session.getState() != SshConnectionState.CONNECTED) {
            return "";
        }
        SshTtyConnector connector = session.getLiveConnector();
        if (connector == null || !connector.isConnected()) {
            return "";
        }
        SshSessionController.ConnectRequest last = session.getLastRequest();
        if (last == null || last.getUsername() == null) {
            return "";
        }
        return last.getUsername();
    }

    /**
     * The {@code disconnectRequested} method disconnects the focused pane.
     */
    @Override
    public void disconnectRequested() {
        requireFocused().getController().disconnect();
    }

    /**
     * The {@code reconnectRequested} method reconnects the focused pane.
     * The form is stored as the last request.
     * The controller then reconnects.
     */
    @Override
    public void reconnectRequested() {
        TerminalSession session = requireFocused();
        SshSessionController.ConnectRequest request = buildConnectRequest(session);
        session.setLastRequest(request);
        session.getController().reconnect(request);
    }

    /**
     * The {@code fontSizeChanged} method applies one font size.
     * The connection bar, the menu, every terminal pane, and the notebook all receive it.
     *
     * @param fontSize The size from the font control, in points.
     */
    @Override
    public void fontSizeChanged(int fontSize) {
        connectionPanel.applyBarFont(fontSize);
        foldingPanel.applyMenuFont(fontSize);
        for (int i = 0; i < sessions.size(); i++) {
            sessions.get(i).getPanel().setFontSize(fontSize);
        }
        aetherisPanel.setUiFontSize(fontSize);
    }

    /**
     * The {@code modeChangeRequested} method stores the mode and may reconnect.
     * A connected pane reconnects with the current form.
     * A connecting pane reconnects with the current form.
     * Any other state does not reconnect.
     * The status line is repainted.
     *
     * @param mode The mode now selected on the bar. The form read includes it.
     */
    @Override
    public void modeChangeRequested(SshSessionMode mode) {
        TerminalSession session = requireFocused();
        if (session.getState() == SshConnectionState.CONNECTED
            || session.getState() == SshConnectionState.CONNECTING) {
            SshSessionController.ConnectRequest request = buildConnectRequest(session);
            session.setLastRequest(request);
            session.getController().reconnect(request);
        }
        paintFrameStatus();
    }

    /**
     * The {@code clearTerminalRequested} method clears the focused terminal.
     * Focus returns to that terminal.
     */
    @Override
    public void clearTerminalRequested() {
        TerminalSession session = requireFocused();
        session.getPanel().clearTerminal();
        session.getPanel().requestTerminalFocus();
    }

    /**
     * The {@code newTermRequested} method opens the split dialog.
     * {@link #promptAndSplit} asks horizontal or vertical.
     */
    @Override
    public void newTermRequested() {
        promptAndSplit();
    }

    /**
     * The {@code closePaneRequested} method closes the focused pane.
     * {@link #closeFocusedPane} keeps the last pane.
     */
    @Override
    public void closePaneRequested() {
        closeFocusedPane();
    }

    /**
     * The {@code sendFileRequested} method loads a file into the paste editor.
     * A cancelled dialog returns immediately.
     * The read runs on a daemon thread named {@code ssh-send-file}.
     */
    @Override
    public void sendFileRequested() {
        final File file = chooseRememberedFile(this);
        if (file == null) {
            return;
        }
        /**
         * The runnable reads the chosen file as a paste.
         * {@link #sendFileAsPaste} opens the editor.
         */
        Thread worker = new Thread(new Runnable() {
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
            @Override
            public void run() {
                sendFileAsPaste(file);
            }
        }, "ssh-send-file");
        worker.setDaemon(true);
        worker.start();
    }

    /**
     * The {@code wireConsoleImport} method connects one pane to import, model, transfer, and draft callbacks.
     * The import callback calls {@link #importIntoSession}.
     * The model callback selects the pane and asks the notebook for the console model.
     * The transfer callback selects the pane and transfers from the console.
     * The draft callback posts an SSH edit.
     * An edit is also posted once when the wiring finishes.
     *
     * @param session The pane that owns the callbacks.
     */
    private void wireConsoleImport(final TerminalSession session) {
        /**
         * The runnable imports a file into this pane.
         */
        session.getPanel().setImportRequest(new Runnable() {
            // @aa-sdd PK-0023 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-4469 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            @Override
            public void run() {
                importIntoSession(session);
            }
        });
        /**
         * The runnable selects this pane.
         * The notebook is asked for the console model.
         */
        session.getPanel().setSelectModelRequest(new Runnable() {
            // @aa-sdd PK-0024 docs/specs/java/org/aetheris/swing/swing.spec.md
            @Override
            public void run() {
                selectSession(session);
                aetherisPanel.requestConsoleModel();
            }
        });
        /**
         * The runnable selects this pane.
         * The notebook transfers from the console.
         */
        session.getPanel().setTransferRequest(new Runnable() {
            // @aa-sdd PK-0025 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-3505 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void run() {
                selectSession(session);
                aetherisPanel.transferFromConsole();
            }
        });
        /**
         * The runnable posts an SSH edit for this pane.
         * The count is not computed on this call.
         */
        session.getPanel().setDraftBeat(new Runnable() {
            // @aa-sdd PK-4150 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0270 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0280 docs/specs/java/org/aetheris/swing/swing.spec.md
            @Override
            public void run() {
                // The key wrote the line. This posts. The count is not on this call.
                // @aa-sdd PK-3775 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                offerSshEdit(session);
            }
        });
        offerSshEdit(session);
    }

    /**
     * The {@code importIntoSession} method inserts a file at the console cursor.
     * A null session returns immediately.
     * The pane is selected.
     * A pane that is not connected shows {@code Not connected.} and returns.
     * A cancelled file dialog returns immediately.
     * The read runs on a daemon thread named {@code ssh-import}.
     *
     * @param session The pane that receives the file.
     */
    private void importIntoSession(final TerminalSession session) {
        if (session == null) {
            return;
        }
        selectSession(session);
        if (session.getState() != SshConnectionState.CONNECTED) {
            showNotice("Import", "Not connected.");
            return;
        }
        final File file = chooseRememberedFile(this);
        if (file == null) {
            return;
        }
        /**
         * The runnable reads the import file off the Swing thread.
         * A file longer than {@link #MAX_PASTE_CHARS} shows {@link #LARGE_FILE} first.
         * A refusal of that notice stops the import.
         * A length check that throws shows the notice and stops.
         * A null read stops.
         * The paste is sent later on the Swing thread.
         */
        Thread worker = new Thread(new Runnable() {
            // @aa-sdd PK-3506 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void run() {
                boolean large = false;
                try {
                    large = file.length() > MAX_PASTE_CHARS;
                } catch (Throwable ex) {
                    showNotice("Import", noticeMessage(ex));
                    return;
                }
                if (large) {
                    if (!showPolite("Import", LARGE_FILE)) {
                        return;
                    }
                }
                final String text = readImportStream(file, "Import");
                if (text == null) {
                    return;
                }
                /**
                 * The runnable sends the imported text as a paste.
                 * Focus returns to the terminal.
                 * A failure shows a notice titled {@code Import}.
                 */
                SwingUtilities.invokeLater(new Runnable() {
                    // @aa-sdd PK-3507 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                    @Override
                    public void run() {
                        try {
                            session.getController().sendPaste(text);
                            session.getPanel().requestTerminalFocus();
                        } catch (Throwable ex) {
                            showNotice("Import", noticeMessage(ex));
                        }
                    }
                });
            }
        }, "ssh-import");
        worker.setDaemon(true);
        worker.start();
    }

    /**
     * The {@code sendFileAsPaste} method reads a file and opens the paste editor.
     * A refused read returns immediately.
     * The editor does not submit the text by itself.
     *
     * @param file The chosen file.
     */
    private void sendFileAsPaste(File file) {
        String text = readConsoleText(file, "Load File");
        if (text == null) {
            return;
        }
        openPasteDialog(text);
    }

    /**
     * The {@code readImportStream} method reads an import file in chunks.
     * The chunk size is 8192 bytes.
     * The first 4096 bytes are kept as a sample.
     * A sample that looks binary shows {@code File looks binary. Send a UTF-8 text file.} and returns null.
     * The bytes are decoded as UTF-8.
     * Newlines are normalized.
     * An empty text shows {@code File is empty.} and returns null.
     * A file longer than {@link #MAX_PASTE_CHARS} is still returned.
     * A thrown failure shows the notice and returns null.
     * The stream is closed.
     *
     * @param file The chosen file.
     * @param title The dialog title when the file is refused.
     * @return The normalized text, or null when the caller should stop.
     */
    private String readImportStream(File file, String title) {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            ByteArrayOutputStream raw = new ByteArrayOutputStream();
            byte[] chunk = new byte[8192];
            byte[] sample = new byte[4096];
            int filled = 0;
            int n;
            while ((n = in.read(chunk)) >= 0) {
                if (n == 0) {
                    break;
                }
                if (filled < sample.length) {
                    int take = Math.min(sample.length - filled, n);
                    System.arraycopy(chunk, 0, sample, filled, take);
                    filled += take;
                }
                raw.write(chunk, 0, n);
            }
            byte[] head = new byte[filled];
            if (filled > 0) {
                System.arraycopy(sample, 0, head, 0, filled);
            }
            if (looksBinary(head)) {
                showNotice(title, "File looks binary. Send a UTF-8 text file.");
                return null;
            }
            String text = new String(raw.toByteArray(), StandardCharsets.UTF_8);
            text = normalizeNewlines(text);
            if (text.length() == 0) {
                showNotice(title, "File is empty.");
                return null;
            }
            return text;
        } catch (Throwable ex) {
            showNotice(title, noticeMessage(ex));
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                    // close
                }
            }
        }
    }

    /**
     * The {@code showPolite} method shows an information dialog before a large import.
     * On the Swing thread the dialog runs immediately.
     * Off that thread the call waits until the dialog returns.
     * A failure shows an error notice and returns false.
     * The return is true only after the dialog has been shown.
     *
     * @param title The dialog title.
     * @param message The notice text.
     * @return True when the dialog was shown.
     */
    private boolean showPolite(final String title, final String message) {
        final boolean[] shown = new boolean[1];
        /**
         * The runnable shows the information dialog.
         * The shown flag becomes true after the dialog returns.
         */
        Runnable show = new Runnable() {
            // @aa-sdd PK-0026 docs/specs/java/org/aetheris/swing/swing.spec.md
            @Override
            public void run() {
                JOptionPane.showMessageDialog(
                        SshClientApp.this,
                        message,
                        title,
                        JOptionPane.INFORMATION_MESSAGE);
                shown[0] = true;
            }
        };
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                show.run();
            } else {
                SwingUtilities.invokeAndWait(show);
            }
        } catch (Throwable ex) {
            showNotice(title, noticeMessage(ex));
            return false;
        }
        return shown[0];
    }

    /**
     * The {@code readConsoleText} method reads a whole file for Load File.
     * A binary sample shows {@code File looks binary. Send a UTF-8 text file.} and returns null.
     * The bytes are decoded as UTF-8 and the newlines are normalized.
     * An empty text shows {@code File is empty.} and returns null.
     * A text longer than {@link #MAX_PASTE_CHARS} shows the limit and returns null.
     * A thrown failure shows the notice and returns null.
     *
     * @param file The chosen file.
     * @param title The dialog title when the file is refused.
     * @return The normalized text, or null when the caller should stop.
     */
    private String readConsoleText(File file, String title) {
        try {
            byte[] raw = Files.readAllBytes(file.toPath());
            if (looksBinary(raw)) {
                showNotice(title, "File looks binary. Send a UTF-8 text file.");
                return null;
            }
            String text = new String(raw, StandardCharsets.UTF_8);
            text = normalizeNewlines(text);
            if (text.length() == 0) {
                showNotice(title, "File is empty.");
                return null;
            }
            if (text.length() > MAX_PASTE_CHARS) {
                showNotice(title, "File exceeds " + MAX_PASTE_CHARS + " characters.");
                return null;
            }
            return text;
        } catch (Exception ex) {
            showNotice(title, noticeMessage(ex));
            return null;
        }
    }

    /**
     * The {@code noticeMessage} method chooses a short failure line.
     * A null message uses the simple class name.
     * An empty message uses the simple class name.
     * Any other message is returned as it is.
     *
     * @param ex The failure.
     * @return The line shown in the dialog.
     */
    private String noticeMessage(Throwable ex) {
        String message = ex.getMessage();
        if (message == null || message.length() == 0) {
            return ex.getClass().getSimpleName();
        }
        return message;
    }

    /**
     * The {@code looksBinary} method rejects a sample that should not be pasted.
     * A null array returns false.
     * An empty array returns false.
     * The sample is at most 4096 bytes.
     * A zero byte returns true.
     * A byte below 9 counts as a control.
     * A byte above 13 and below 32 counts as a control.
     * The sample is binary when the control count times ten is greater than the sample length.
     *
     * @param raw The file bytes.
     * @return True when the file should not be pasted.
     */
    private boolean looksBinary(byte[] raw) {
        if (raw == null || raw.length == 0) {
            return false;
        }
        int control = 0;
        int sample = raw.length < 4096 ? raw.length : 4096;
        for (int i = 0; i < sample; i++) {
            int b = raw[i] & 0xFF;
            if (b == 0) {
                return true;
            }
            if (b < 9 || (b > 13 && b < 32)) {
                control++;
            }
        }
        return control * 10 > sample;
    }

    /**
     * The {@code normalizeNewlines} method normalizes newlines for a paste.
     * {@link ConsolePaste#normalize} does the work.
     *
     * @param text The file text.
     * @return The normalized text.
     */
    private String normalizeNewlines(String text) {
        return ConsolePaste.normalize(text);
    }

    /**
     * The {@code applyWindowIcon} method sets the frame icons.
     * The first resource is {@code /icons/aetheris.jpg}.
     * A missing file tries {@code /icons/casserole.jpg}.
     * A missing second file returns immediately.
     * The image list holds the master, a 32-pixel copy, and a 16-pixel copy.
     *
     * // @aa-sdd PK-3495 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     */
    private void applyWindowIcon() {
        URL url = SshClientApp.class.getResource("/icons/aetheris.jpg");
        if (url == null) {
            url = SshClientApp.class.getResource("/icons/casserole.jpg");
        }
        if (url == null) {
            return;
        }
        Image master = Toolkit.getDefaultToolkit().getImage(url);
        ArrayList<Image> icons = new ArrayList<Image>();
        icons.add(master);
        icons.add(master.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        icons.add(master.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        setIconImages(icons);
    }

    /**
     * The {@code installPasteInterceptor} method installs one keyboard dispatcher.
     * Ctrl+V and Ctrl+Shift+V open the paste editor.
     * Ctrl+Shift+D splits side by side.
     * Ctrl+Shift+E splits one above the other.
     * Ctrl+Shift+W closes the focused pane.
     */
    private void installPasteInterceptor() {
        /**
         * The dispatcher handles terminal chords on key press.
         * Any other event id returns false.
         * Ctrl+Shift+D consumes the event and splits side by side.
         * Ctrl+Shift+E consumes the event and splits vertically.
         * Ctrl+Shift+W consumes the event and closes the focused pane.
         *
         * A copy chord with the terminal focused copies the highlight and returns true.
         * A copy chord without terminal focus returns false.
         * Ctrl+Z without Alt, and with the terminal focused, writes the undo byte.
         * Ctrl+Shift+Z without Alt writes the redo bytes.
         * That undo chord without terminal focus returns false.
         *
         * A press that is not Ctrl+V returns false.
         * Ctrl+V without terminal focus returns false.
         * Ctrl+V with terminal focus opens the paste editor and returns true.
         */
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            // @aa-sdd PK-4216 docs/specs/java/org/aetheris/swing/swing.spec.md
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() != KeyEvent.KEY_PRESSED) {
                    return false;
                }
                boolean ctrl = (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0;
                boolean shift = (e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) != 0;
                if (ctrl && shift && e.getKeyCode() == KeyEvent.VK_D) {
                    e.consume();
                    /**
                     * The runnable splits the focused pane side by side.
                     */
                    SwingUtilities.invokeLater(new Runnable() {
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
                        @Override
                        public void run() {
                            splitFocused(true);
                        }
                    });
                    return true;
                }
                if (ctrl && shift && e.getKeyCode() == KeyEvent.VK_E) {
                    e.consume();
                    /**
                     * The runnable splits the focused pane one above the other.
                     */
                    SwingUtilities.invokeLater(new Runnable() {
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
                        @Override
                        public void run() {
                            splitFocused(false);
                        }
                    });
                    return true;
                }
                if (ctrl && shift && e.getKeyCode() == KeyEvent.VK_W) {
                    e.consume();
                    /**
                     * The runnable closes the focused pane.
                     */
                    SwingUtilities.invokeLater(new Runnable() {
                        // @aa-sdd PK-3508 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                        @Override
                        public void run() {
                            closeFocusedPane();
                        }
                    });
                    return true;
                }
                if (ConsoleCopyChord.kind(e.getKeyCode(), ctrl, shift) == ConsoleCopyChord.Kind.COPY) {
                    if (!isTerminalFocused()) {
                        return false;
                    }
                    e.consume();
                    copyConsoleHighlight();
                    return true;
                }
                boolean alt = (e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) != 0;
                if (ctrl && !alt && e.getKeyCode() == KeyEvent.VK_Z) {
                    if (!isTerminalFocused()) {
                        return false;
                    }
                    e.consume();
                    if (shift) {
                        writeRedoChord();
                    } else {
                        writeUndoChord();
                    }
                    return true;
                }
                if (e.getKeyCode() != KeyEvent.VK_V || !ctrl) {
                    return false;
                }
                if (!isTerminalFocused()) {
                    return false;
                }
                e.consume();
                pasteFromClipboardViaDialog();
                return true;
            }
        });
    }

    /**
     * The {@code isTerminalFocused} method reports whether a terminal pane owns the keyboard focus.
     * The result is true when {@link #sessionForFocus} finds a pane.
     *
     * @return True when a terminal pane has focus.
     */
    private boolean isTerminalFocused() {
        return sessionForFocus() != null;
    }

    /**
     * The {@code copyConsoleHighlight} method copies the terminal highlight.
     * A null clipboard text returns immediately.
     * The text is placed on the system clipboard.
     * A busy clipboard leaves the clipboard unchanged.
     */
    private void copyConsoleHighlight() {
        String text = ConsoleCopyChord.textForClipboard(terminalSelection());
        if (text == null) {
            return;
        }
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        } catch (IllegalStateException busy) {
            // The clipboard stays as it is. The console still writes nothing.
        }
    }

    /**
     * The {@code pasteFromClipboardViaDialog} method opens the editor with the clipboard text.
     * A missing clipboard returns immediately.
     * A clipboard without string data returns immediately.
     * A null or empty string returns immediately.
     * The text is normalized and then opened.
     * A clipboard failure is ignored.
     */
    private void pasteFromClipboardViaDialog() {
        try {
            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (t == null || !t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return;
            }
            String text = (String) t.getTransferData(DataFlavor.stringFlavor);
            if (text == null || text.length() == 0) {
                return;
            }
            openPasteDialog(normalizeNewlines(text));
        } catch (Exception ignored) {
            // clipboard unavailable
        }
    }

    /**
     * The {@code openPasteDialog} method shows the paste editor.
     * On the Swing thread the editor runs immediately.
     * Off that thread the editor is posted to the Swing thread.
     * OK sends the edited text as a paste.
     * A cancel does nothing.
     *
     * @param text The initial text.
     */
    private void openPasteDialog(final String text) {
        /**
         * The runnable shows the paste editor.
         * A dialog that is not accepted returns immediately.
         * A null edit returns immediately.
         * An empty edit returns immediately.
         * A text longer than {@link #MAX_PASTE_CHARS} shows a Load File error.
         * An accepted text is sent as a paste and the terminal takes focus.
         * A send failure shows a Load File error.
         */
        Runnable show = new Runnable() {
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
            @Override
            public void run() {
                Font editorFont = requireFocused().getPanel().getTerminalFont().deriveFont(
                        Font.PLAIN, (float) connectionPanel.getFontSize());
                PasteEditorDialog dialog = new PasteEditorDialog(
                        SshClientApp.this, theme, text, editorFont);
                dialog.setVisible(true);
                if (!dialog.isAccepted()) {
                    return;
                }
                String edited = dialog.getEditedText();
                if (edited == null) {
                    return;
                }
                edited = normalizeNewlines(edited);
                if (edited.length() == 0) {
                    return;
                }
                if (edited.length() > MAX_PASTE_CHARS) {
                    showSendFileError("Paste exceeds " + MAX_PASTE_CHARS + " characters.");
                    return;
                }
                try {
                    requireFocused().getController().sendPaste(edited);
                    requireFocused().getPanel().requestTerminalFocus();
                } catch (Exception ex) {
                    String message = ex.getMessage();
                    if (message == null || message.length() == 0) {
                        message = ex.getClass().getSimpleName();
                    }
                    showSendFileError(message);
                }
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            show.run();
        } else {
            SwingUtilities.invokeLater(show);
        }
    }

    /**
     * The {@code showSendFileError} method shows a Load File error.
     * The title is {@code Load File}.
     *
     * @param message The reason shown to the user.
     */
    private void showSendFileError(final String message) {
        showNotice("Load File", message);
    }

    /**
     * The {@code showNotice} method shows an error dialog on the Swing thread.
     *
     * @param title The dialog title.
     * @param message The reason shown to the user.
     */
    private void showNotice(final String title, final String message) {
        /**
         * The runnable shows the error dialog.
         * The message type is an error.
         */
        SwingUtilities.invokeLater(new Runnable() {
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
            @Override
            public void run() {
                JOptionPane.showMessageDialog(
                        SshClientApp.this,
                        message,
                        title,
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * The {@code sessionConnecting} method marks a pane as connecting.
     * A pane that is not focused only refreshes the Aetheris user list.
     * The focused pane is selected.
     * The link word becomes {@code Connecting}.
     * The status line and the focused user are refreshed.
     *
     * @param session The pane that is opening a connection.
     */
    @Override
    public void sessionConnecting(TerminalSession session) {
        if (session != focused) {
            refreshAetherisUsers();
            return;
        }
        selectSession(session);
        linkWord = "Connecting";
        paintFrameStatus();
        refreshFocusedUser();
    }

    /**
     * The {@code sessionConnected} method attaches a live connector.
     * The terminal attaches the connector.
     * {@link #aimSession} arms the listener when the connector speaks AMWP.
     * A pane that is not focused only refreshes the Aetheris user list.
     *
     * The focused pane is selected.
     * A connected connector applies {@link FoldingPanel.Beat#CONNECTED}.
     * Any other result opens the connection fields.
     * The remote server and the port are remembered.
     * The terminal takes focus.
     * The Aetheris tab is refreshed and may be restored.
     * The focused user and the Aetheris user list are refreshed.
     *
     * @param session The pane that connected.
     * @param connector The live connector. It may be null.
     * @param host The remote server address.
     * @param port The remote port.
     */
    @Override
    public void sessionConnected(TerminalSession session, SshTtyConnector connector, String host, int port) {
        session.getPanel().attachSession(connector);
        aimSession(session, connector);
        if (session != focused) {
            refreshAetherisUsers();
            return;
        }
        selectSession(session);
        if (connector != null && connector.isConnected()) {
            foldingPanel.apply(FoldingPanel.Beat.CONNECTED);
        } else if (foldingPanel != null) {
            foldingPanel.showFields();
        }
        rememberConnected(host, port);
        session.getPanel().requestTerminalFocus();
        refreshAetherisTab();
        maybeRestoreAetherisTab();
        refreshFocusedUser();
        refreshAetherisUsers();
    }

    /**
     * The {@code sessionDisconnected} method stops one pane.
     * {@link #dropSession} drops the listener record.
     * The terminal stops the session.
     * A pane that is not focused only refreshes the Aetheris user list.
     *
     * The focused pane is selected and the Aetheris tab is refreshed.
     * A null message sets the status to disconnected.
     * A blank message sets the status to disconnected.
     * Any other message is stored as {@code Disconnected: } plus the message.
     * That message is also shown in a warning dialog titled {@code Disconnected}.
     * The focused user is refreshed.
     *
     * @param session The pane that disconnected.
     * @param connector The connector captured before it was cleared.
     * @param message The disconnect reason. A null or blank value shows no dialog.
     */
    @Override
    public void sessionDisconnected(TerminalSession session, SshTtyConnector connector, String message) {
        dropSession(session, connector);
        session.getPanel().stopSession();
        if (session != focused) {
            refreshAetherisUsers();
            return;
        }
        selectSession(session);
        refreshAetherisTab();
        if (message == null || message.trim().length() == 0) {
            setStatusDisconnected();
        } else {
            rememberLink("Disconnected: " + message);
            JOptionPane.showMessageDialog(
                SshClientApp.this,
                message,
                "Disconnected",
                JOptionPane.WARNING_MESSAGE);
        }
        refreshFocusedUser();
    }

    /**
     * The {@code sessionError} method reports a failed connection.
     * {@link #dropSession} drops the listener record.
     * The terminal stops the session.
     * A pane that is not focused only refreshes the Aetheris user list.
     * The focused pane is selected.
     * The link word becomes {@code Disconnected: } plus the message.
     * The focused user and the Aetheris user list are refreshed.
     * An error dialog titled {@code Connection failed} shows the message.
     *
     * @param session The pane that failed.
     * @param connector The connector captured before it was cleared.
     * @param message The failure text.
     */
    @Override
    public void sessionError(TerminalSession session, SshTtyConnector connector, String message) {
        dropSession(session, connector);
        session.getPanel().stopSession();
        if (session != focused) {
            refreshAetherisUsers();
            return;
        }
        selectSession(session);
        rememberLink("Disconnected: " + message);
        refreshFocusedUser();
        refreshAetherisUsers();
        JOptionPane.showMessageDialog(
            SshClientApp.this,
            message,
            "Connection failed",
            JOptionPane.ERROR_MESSAGE);
    }

    /**
     * The {@code buildConnectRequest} method copies the connection form.
     * The columns and rows come from the pane's preferred terminal size.
     * The remote server, port, username, password, and mode come from the connection panel.
     * The idle timeout comes from the properties.
     *
     * @param session The pane whose terminal size is read.
     * @return The request passed to the session controller.
     */
    private SshSessionController.ConnectRequest buildConnectRequest(TerminalSession session) {
        TermSize size = session.getPanel().getPreferredTermSize();
        return new SshSessionController.ConnectRequest(
            connectionPanel.getHost(),
            connectionPanel.getPort(),
            connectionPanel.getUsername(),
            connectionPanel.getPassword(),
            connectionPanel.getMode(),
            size.getColumns(),
            size.getRows(),
            properties.getIdleTimeoutMs());
    }

    /**
     * The {@code setStatusDisconnected} method shows a disconnected client.
     * The link word becomes {@code Disconnected}.
     * The focused user label is refreshed.
     */
    private void setStatusDisconnected() {
        rememberLink("Disconnected");
        refreshFocusedUser();
    }

    /**
     * The {@code rememberConnected} method stores a live link.
     * The link word becomes {@code Connected}.
     * A null server address is stored as an empty string.
     * The port is stored as a decimal string.
     * The status line is repainted.
     *
     * @param host The remote server address. A null value is stored as empty.
     * @param port The remote port.
     */
    private void rememberConnected(String host, int port) {
        linkWord = "Connected";
        linkHost = host == null ? "" : host;
        linkPort = Integer.toString(port);
        paintFrameStatus();
    }

    /**
     * The {@code rememberLink} method stores the link word.
     * A null word becomes {@code Disconnected}.
     * The status line is repainted.
     *
     * @param word The link word.
     */
    private void rememberLink(String word) {
        linkWord = word == null ? "Disconnected" : word;
        paintFrameStatus();
    }

    /**
     * The {@code paintFrameStatus} method paints the status line for the selected product tab.
     * A missing status label, tab strip, notebook, or connection panel returns immediately.
     * The folding panel receives the link word when it exists.
     *
     * The Aetheris tab uses the stored Aetheris line.
     * A missing stored line uses the notebook strip.
     * The SSH tab joins the west link text, the mood gate, and the facts.
     * The facts use the mode property value, the shell air word, and the shell engine word.
     * The mood gate is true when the focused vault user has an SSH mood line.
     * {@link #feedComposerHeat} runs for either tab.
     */
    private void paintFrameStatus() {
        if (statusLabel == null || productTabs == null || aetherisPanel == null || connectionPanel == null) {
            return;
        }
        if (foldingPanel != null) {
            foldingPanel.showLink(linkWord);
        }
        if (productTabs.getSelectedIndex() == 1) {
            String line = aetherisStatusText;
            if (line == null) {
                line = aetherisPanel.aetherisStripLine();
            }
            statusLabel.setText(line);
            feedComposerHeat();
            return;
        }
        String mode = connectionPanel.getMode().getPropertyValue();
        String facts = Vt100StatusLine.facts(mode, aetherisPanel.shellAirWord(), aetherisPanel.shellEngineWord());
        String west = Vt100StatusLine.west(linkWord, linkHost, linkPort);
        boolean moodGate = aetherisPanel.sshMoodLineFor(focusedVaultUser()) != null;
        String joined = Vt100StatusLine.join(west, moodGate, facts);
        statusLabel.setText(joined);
        feedComposerHeat();
    }

    /**
     * The {@code feedComposerHeat} method paints the token estimate.
     * A missing bar, notebook, or tab strip returns immediately.
     * The Aetheris tab uses the notebook ring count and the ring window.
     *
     * The SSH tab uses {@link #focusedLiveCount}.
     * A managed token count above zero becomes the budget.
     * Otherwise an engine word that contains {@code ollama} uses a budget of 131072.
     * Any other SSH engine uses a budget of zero.
     * The bar shows that guess and budget.
     * The word label uses the bar's word and ink.
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
    private void feedComposerHeat() {
        if (composerHeat == null || aetherisPanel == null || productTabs == null) {
            return;
        }
        int guess;
        int budget;
        if (ToolsThinking.front(productTabs.getSelectedIndex()) == ToolsThinking.Plate.AETHERIS) {
            guess = aetherisPanel.tokenRingUsed();
            budget = aetherisPanel.tokenRingWindow();
        } else {
            // The focused pane's stored integer. The walk posted it.
            // @aa-sdd PK-3776 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            guess = focusedLiveCount();
            int managed = aetherisPanel.managedTokens();
            String engine = aetherisPanel.shellEngineWord();
            budget = 0;
            if (managed > 0) {
                budget = managed;
            } else if (engine != null && engine.indexOf("ollama") >= 0) {
                budget = 131072;
            }
        }
        composerHeat.showHeuristic(guess, budget);
        composerWord.setText(composerHeat.word());
        composerWord.setForeground(composerHeat.ink());
    }

    /**
     * The {@code sessionMemoryText} method returns the focused pane's session text.
     * A missing pane or panel returns an empty string.
     * A null panel text returns an empty string.
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
     * @return The session text, or an empty string.
     */
    private String sessionMemoryText() {
        if (focused == null || focused.getPanel() == null) {
            return "";
        }
        String text = focused.getPanel().sessionMemoryText();
        if (text == null) {
            return "";
        }
        return text;
    }

    /**
     * The {@code sshLiveCount} method measures the focused SSH pane.
     * The unsent line comes from the focused panel.
     * A missing panel uses an empty draft.
     * A held Roux tally uses {@link StoredCountEstimator} on an empty transcript as the base.
     * That path adds the draft through {@link #countDraft}.
     * Any other path counts the session text and the draft.
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
     * @return The integer the token window opens on.
     */
    private int sshLiveCount() {
        String draft = "";
        if (focused != null && focused.getPanel() != null) {
            draft = focused.getPanel().liveLine();
        }
        String user = focusedVaultUser();
        if (aetherisPanel.rouxSessionHeld(user)) {
            // @aa-sdd PK-4127 docs/specs/java/org/aetheris/swing/swing.spec.md
            int base = new StoredCountEstimator(user, aetherisPanel.rouxSessionUsed(user))
                    .estimate(Transcript.empty());
            return countDraft(true, base, "", draft);
        }
        return countDraft(false, 0, sessionMemoryText(), draft);
    }

    /**
     * The {@code focusedLiveCount} method returns the stored estimate for the focused pane.
     * A missing pane or panel returns one.
     * The value is the panel's live count.
     *
     * // @aa-sdd PK-3777 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     *
     * @return The focused pane's stored estimate.
     */
    private int focusedLiveCount() {
        if (focused == null || focused.getPanel() == null) {
            return 1;
        }
        return focused.getPanel().liveCount();
    }

    /**
     * The {@code countDraft} method turns one snapshot into an estimate.
     * A null draft becomes an empty string.
     * A null memory becomes an empty string.
     * A held tally with an empty draft returns the base.
     * A held tally with a draft adds {@link HeuristicTokenCounter} of that draft to the base.
     *
     * An unheld draft that is not already at the end of the memory is counted with the memory.
     * Any other unheld snapshot counts the memory alone.
     * Those unheld counts use {@link Vt100TokenWindow#heuristic}.
     *
     * // @aa-sdd PK-3778 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     *
     * @param held True when a Roux tally is the base.
     * @param base The held integer.
     * @param memory The session text.
     * @param draft The unsent line.
     * @return The estimate.
     */
    private static int countDraft(boolean held, int base, String memory, String draft) {
        if (draft == null) {
            draft = "";
        }
        if (memory == null) {
            memory = "";
        }
        if (held) {
            if (draft.length() == 0) {
                return base;
            }
            return base + new HeuristicTokenCounter().count(draft);
        }
        if (draft.length() > 0 && !memory.endsWith(draft)) {
            return Vt100TokenWindow.heuristic(memory + draft);
        }
        return Vt100TokenWindow.heuristic(memory);
    }

    /**
     * The {@code sessionVaultUser} method returns the vault name for one pane.
     * A null session returns an empty string.
     * A missing notebook returns an empty string.
     * A missing last request, or a null username, returns an empty string.
     * Any other username is passed through the notebook vault name.
     *
     * @param session The pane.
     * @return The vault name, or an empty string.
     */
    private String sessionVaultUser(TerminalSession session) {
        if (session == null || aetherisPanel == null) {
            return "";
        }
        SshSessionController.ConnectRequest last = session.getLastRequest();
        if (last == null || last.getUsername() == null) {
            return "";
        }
        return aetherisPanel.vaultName(last.getUsername());
    }

    /**
     * The {@code offerSshEdit} method posts one edit for a pane.
     * A null session returns immediately.
     * A missing panel returns immediately.
     * The queue is started.
     * The edit number increases by one.
     * Older edits that have not started are dropped by the queue.
     *
     * // @aa-sdd PK-3779 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     *
     * @param session The pane whose line changed, or the pane now in front.
     */
    private void offerSshEdit(TerminalSession session) {
        if (session == null || session.getPanel() == null) {
            return;
        }
        sshEdits.ensureStarted();
        long sequence = ++sshEditSeq;
        sshEdits.offer(new SshEdit(sequence, session));
    }

    /**
     * The {@code copySshSeat} method copies one pane on the Swing thread.
     * A null edit returns null.
     * An edit older than {@code sshEditSeq} returns null.
     * A missing session or panel returns null.
     * The copy holds the unsent line, the session text, and the vault user.
     * A held Roux tally stores the estimator result as the base.
     * Any other copy stores a base of zero.
     *
     * @param edit The posted pane.
     * @return The snapshot, or null when a later post owns the count.
     */
    private SshSeat copySshSeat(SshEdit edit) {
        if (edit == null || edit.sequence < sshEditSeq || edit.session == null || edit.session.getPanel() == null) {
            return null;
        }
        String draft = edit.session.getPanel().liveLine();
        String memory = edit.session.getPanel().sessionMemoryText();
        String user = sessionVaultUser(edit.session);
        boolean held = aetherisPanel != null && aetherisPanel.rouxSessionHeld(user);
        int base = 0;
        if (held) {
            base = new StoredCountEstimator(user, aetherisPanel.rouxSessionUsed(user))
                    .estimate(Transcript.empty());
        }
        return new SshSeat(edit.sequence, edit.session, held, base, memory, draft);
    }

    // @aa-sdd PK-3780 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code SshEdit} is one posted SSH edit.
     * The sequence is the newest number at the time it was offered.
     * The session is the pane to copy.
     */
    private static final class SshEdit {
        private final long sequence;
        private final TerminalSession session;

        /**
         * The constructor stores the sequence and the pane.
         *
         * @param sequence The edit number.
         * @param session The pane to copy.
         */
        private SshEdit(long sequence, TerminalSession session) {
            this.sequence = sequence;
            this.session = session;
        }
    }

    // @aa-sdd PK-3781 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code SshSeat} is the text snapshot a count runs on.
     * The strings are copied before the worker counts them.
     * A null memory is stored as an empty string.
     * A null draft is stored as an empty string.
     */
    private static final class SshSeat {
        private final long sequence;
        private final TerminalSession session;
        private final boolean held;
        private final int base;
        private final String memory;
        private final String draft;

        /**
         * The constructor stores one snapshot.
         * A null memory becomes an empty string.
         * A null draft becomes an empty string.
         *
         * @param sequence The edit number.
         * @param session The pane the text came from.
         * @param held True when a Roux tally is the base.
         * @param base The held integer.
         * @param memory The session text.
         * @param draft The unsent line.
         */
        private SshSeat(long sequence, TerminalSession session, boolean held, int base, String memory, String draft) {
            this.sequence = sequence;
            this.session = session;
            this.held = held;
            this.base = base;
            this.memory = memory == null ? "" : memory;
            this.draft = draft == null ? "" : draft;
        }
    }

    /**
     * The {@code SshEditQueue} counts SSH line edits off the Swing thread.
     * The queue keeps only the newest edit that has not started.
     * The worker copies the snapshot on the Swing thread.
     * The worker does not paint.
     * The keyboard path does not wait for the worker.
     *
     * // @aa-sdd PK-3782 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     */
    private final class SshEditQueue implements Runnable {
        private final ArrayDeque<SshEdit> pending = new ArrayDeque<SshEdit>();
        private final Object gate = new Object();
        private final Thread thread;
        private boolean started;

        /**
         * The constructor creates the daemon thread.
         * The thread name is {@code ssh-token-edit}.
         * The thread is not started here.
         */
        SshEditQueue() {
            thread = new Thread(this, "ssh-token-edit");
            thread.setDaemon(true);
        }

        /**
         * The {@code ensureStarted} method starts the worker once.
         * A started queue returns immediately.
         * The check is repeated under the gate before {@code start} is called.
         */
        void ensureStarted() {
            if (started) {
                return;
            }
            synchronized (gate) {
                if (started) {
                    return;
                }
                thread.start();
                started = true;
            }
        }

        /**
         * The {@code offer} method keeps only the newest edit.
         * The call is expected on the Swing thread.
         * The pending queue is cleared.
         * The new edit is added.
         * Waiting workers are notified.
         *
         * @param edit The pane post.
         */
        void offer(SshEdit edit) {
            synchronized (gate) {
                pending.clear();
                pending.addLast(edit);
                gate.notifyAll();
            }
        }

        /**
         * The {@code run} method counts edits until the process ends.
         * A null take is skipped.
         * An edit that already has a newer one waiting is skipped.
         * A null snapshot is skipped.
         * A snapshot older than {@code sshEditSeq} is skipped.
         * A count that throws is skipped.
         * A snapshot that became old during the count is skipped.
         * {@link #paintLater} posts the count to the Swing thread.
         */
        @Override
        public void run() {
            while (true) {
                SshEdit edit = take();
                if (edit == null || newerWaiting()) {
                    continue;
                }
                SshSeat seat = capture(edit);
                if (seat == null || seat.sequence < sshEditSeq) {
                    continue;
                }
                int count;
                try {
                    count = countDraft(seat.held, seat.base, seat.memory, seat.draft);
                } catch (Exception ex) {
                    continue;
                }
                if (seat.sequence < sshEditSeq) {
                    continue;
                }
                paintLater(seat, count);
            }
        }

        /**
         * The {@code take} method waits for the next edit.
         * An empty queue waits on the gate.
         * An interrupt leaves the wait and tries again.
         * The first pending edit is removed and returned.
         *
         * @return The next edit.
         */
        private SshEdit take() {
            synchronized (gate) {
                while (pending.isEmpty()) {
                    try {
                        gate.wait();
                    } catch (InterruptedException ex) {
                        // The frame lives with the app. The seat stays up.
                    }
                }
                return pending.removeFirst();
            }
        }

        /**
         * The {@code newerWaiting} method reports a newer edit already queued.
         * The result is true when the pending queue is not empty.
         *
         * @return True when a newer edit is waiting.
         */
        private boolean newerWaiting() {
            synchronized (gate) {
                return !pending.isEmpty();
            }
        }

        /**
         * The {@code capture} method copies the pane on the Swing thread and waits.
         * A failure returns null.
         *
         * @param edit The edit to copy.
         * @return The snapshot, or null.
         */
        private SshSeat capture(final SshEdit edit) {
            final SshSeat[] held = new SshSeat[1];
            try {
                /**
                 * The runnable copies the pane.
                 * {@link SshClientApp#copySshSeat} stores the snapshot.
                 */
                SwingUtilities.invokeAndWait(new Runnable() {
                    // @aa-sdd PK-3783 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                    @Override
                    public void run() {
                        held[0] = copySshSeat(edit);
                    }
                });
            } catch (Exception ex) {
                return null;
            }
            return held[0];
        }

        /**
         * The {@code paintLater} method posts the count to the Swing thread.
         *
         * @param seat The snapshot that was counted.
         * @param count The estimate.
         */
        private void paintLater(final SshSeat seat, final int count) {
            /**
             * The runnable stores the count on the pane.
             * A snapshot older than {@code sshEditSeq} returns immediately.
             * A missing session or panel returns immediately.
             * The panel stores the count.
             * The focused pane also repaints the token bar.
             */
            SwingUtilities.invokeLater(new Runnable() {
                // @aa-sdd PK-3784 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                @Override
                public void run() {
                    if (seat.sequence < sshEditSeq) {
                        return;
                    }
                    if (seat.session == null || seat.session.getPanel() == null) {
                        return;
                    }
                    seat.session.getPanel().seatLiveCount(count);
                    if (focused == seat.session) {
                        feedComposerHeat();
                    }
                }
            });
        }
    }

    /**
     * The {@code relayArmedMoods} method sends the console mood to connected AMWP panes.
     * A missing notebook returns immediately.
     * A pane that is not connected is skipped.
     * A missing connector is skipped.
     * A connector that is not AMWP is skipped.
     * A missing username is skipped.
     *
     * {@link MoodDesk#announceBody} builds the body from the told flag, the held mood, and the wanted line.
     * A null body sends nothing.
     * The body is written as UTF-8 on channel A.
     * A successful write stores the wanted line and marks the mood as told.
     * A null wanted line is stored as an empty string.
     * A write failure is ignored.
     * The status line is repainted after the loop.
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
    @Override
    public void relayArmedMoods() {
        if (aetherisPanel == null) {
            return;
        }
        for (int i = 0; i < sessions.size(); i++) {
            TerminalSession session = sessions.get(i);
            if (session.getState() != SshConnectionState.CONNECTED) {
                continue;
            }
            SshTtyConnector connector = session.getLiveConnector();
            if (connector == null || !connector.isAmwp()) {
                continue;
            }
            SshSessionController.ConnectRequest last = session.getLastRequest();
            if (last == null || last.getUsername() == null || last.getUsername().length() == 0) {
                continue;
            }
            String user = aetherisPanel.vaultName(last.getUsername());
            String want = aetherisPanel.sshMoodLineFor(user);
            String body = MoodDesk.announceBody(session.moodTold(), session.moodHeld(), want);
            if (body == null) {
                continue;
            }
            try {
                byte[] utf = body.getBytes(StandardCharsets.UTF_8);
                byte[] payload = AmwpCodec.scopeAPayload(0, 1, null, utf);
                connector.writeAmwp(AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_DATA, payload);
                session.setMoodHeld(want == null ? "" : want);
                session.setMoodTold(true);
            } catch (Exception ignored) {
                // the next beat sends the frame again
            }
        }
        // The strip follows the seat. The switch off clears SSH mood. The checkbox only makes it available.
        // @aa-sdd PK-4177 docs/specs/java/org/aetheris/swing/swing.spec.md
        paintFrameStatus();
    }

    /**
     * The {@code sessionMemoryBlocks} method counts non-blank rows.
     * A null text returns zero.
     * An empty text returns zero.
     * Each line is trimmed.
     * A line that still has characters adds one.
     *
     * @param text The session text.
     * @return The row count.
     */
    private static int sessionMemoryBlocks(String text) {
        if (text == null || text.length() == 0) {
            return 0;
        }
        int count = 0;
        int start = 0;
        for (int i = 0; i <= text.length(); i++) {
            if (i == text.length() || text.charAt(i) == '\n') {
                String one = text.substring(start, i).trim();
                if (one.length() > 0) {
                    count = count + 1;
                }
                start = i + 1;
            }
        }
        return count;
    }

    /**
     * The {@code saveFrameState} method stores the frame rectangle and the maximized flag.
     * A maximized frame does not replace the restored rectangle.
     * An iconified frame does not replace the restored rectangle.
     * A normal frame smaller than 400 by 300 does not replace the restored rectangle.
     * The restored origin, width, and height are written when a rectangle exists.
     * The properties file is saved.
     */
    private void saveFrameState() {
        int state = getExtendedState();
        boolean maximized = (state & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH;
        if ((state & JFrame.ICONIFIED) == 0 && !maximized) {
            Rectangle now = getBounds();
            if (now != null && now.width >= 400 && now.height >= 300) {
                normalFrame = new Rectangle(now);
            }
        }
        if (normalFrame != null) {
            properties.setFrameWidth(normalFrame.width);
            properties.setFrameHeight(normalFrame.height);
            properties.setFrameOrigin(normalFrame.x, normalFrame.y);
        }
        properties.setFrameMaximized(maximized);
        properties.save();
    }

    /**
     * The {@code seatOpeningFrame} method places the frame on first open.
     * The preferred size is 1200 by 760.
     * A margin of 32 pixels is kept on the usable screen when the room stays at least 640 by 480.
     * A tighter room uses the full usable width or height.
     * A room below one pixel becomes 640 wide or 480 tall.
     * The preferred size is reduced to that room.
     * A width below 640 is raised to 640 when the usable width allows it.
     * A height below 480 is raised to 480 when the usable height allows it.
     * The size is then limited to the usable screen.
     * A size below one pixel becomes one.
     * The frame is centered in the usable screen.
     */
    private void seatOpeningFrame() {
        Rectangle usable = usableScreen();
        int margin = 32;
        int width = 1200;
        int height = 760;
        int roomW = usable.width - (margin * 2);
        int roomH = usable.height - (margin * 2);
        if (roomW < 640) {
            roomW = usable.width;
        }
        if (roomH < 480) {
            roomH = usable.height;
        }
        if (roomW < 1) {
            roomW = 640;
        }
        if (roomH < 1) {
            roomH = 480;
        }
        if (width > roomW) {
            width = roomW;
        }
        if (height > roomH) {
            height = roomH;
        }
        if (width < 640 && usable.width >= 640) {
            width = 640;
        }
        if (height < 480 && usable.height >= 480) {
            height = 480;
        }
        if (width > usable.width) {
            width = usable.width;
        }
        if (height > usable.height) {
            height = usable.height;
        }
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        int x = usable.x + ((usable.width - width) / 2);
        int y = usable.y + ((usable.height - height) / 2);
        setBounds(x, y, width, height);
    }

    /**
     * The {@code usableScreen} method returns the screen minus the taskbar insets.
     * A missing graphics configuration uses the default screen.
     * A width below one pixel ignores the horizontal insets.
     * A height below one pixel ignores the vertical insets.
     *
     * @return The usable rectangle.
     */
    private Rectangle usableScreen() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        if (gc == null) {
            gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();
        }
        Rectangle bounds = gc.getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
        int x = bounds.x + insets.left;
        int y = bounds.y + insets.top;
        int width = bounds.width - insets.left - insets.right;
        int height = bounds.height - insets.top - insets.bottom;
        if (width < 1) {
            x = bounds.x;
            width = bounds.width;
        }
        if (height < 1) {
            y = bounds.y;
            height = bounds.height;
        }
        return new Rectangle(x, y, width, height);
    }

    /**
     * The {@code frameOnScreen} method tests a saved frame rectangle.
     * A null rectangle returns false.
     * A width below 400 returns false.
     * A height below 300 returns false.
     * A rectangle that intersects any screen returns true.
     * Any other rectangle returns false.
     *
     * @param box The saved frame rectangle.
     * @return True when the rectangle meets a screen and the minimum size.
     */
    private boolean frameOnScreen(Rectangle box) {
        if (box == null || box.width < 400 || box.height < 300) {
            return false;
        }
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (int i = 0; i < devices.length; i++) {
            Rectangle screen = devices[i].getDefaultConfiguration().getBounds();
            if (screen != null && screen.intersects(box)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The {@code confirmAndExit} method asks before closing.
     * The question is {@code Exit the SSH client?}.
     * The title is {@code Confirm exit}.
     * Any answer other than yes returns immediately.
     * Product-tab writes are suppressed.
     * A false restore flag stores the selected product tab.
     * The restore flag is cleared.
     * The frame, the connection fields, and the vault are saved or closed.
     * The worker is shut down.
     * Every session is disposed.
     * The frame is disposed.
     * {@code System.exit} is called with zero.
     */
    private void confirmAndExit() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Exit the SSH client?",
            "Confirm exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        suppressProductTabWrite = true;
        if (!restoreAetheris) {
            String tabWord = productTabs.getSelectedIndex() == 1
                    ? SshClientProperties.PRODUCT_TAB_AETHERIS
                    : SshClientProperties.PRODUCT_TAB_SSH;
            properties.setProductTab(tabWord);
        }
        restoreAetheris = false;
        saveFrameState();
        connectionPanel.persistConnectionFields();
        aetherisPanel.closeVault();
        aetherisWorker.shutdown();
        for (int i = 0; i < sessions.size(); i++) {
            sessions.get(i).dispose();
        }
        dispose();
        System.exit(0);
    }

    /**
     * The {@code requireFocused} method returns the focused pane.
     * A null focus selects the first session.
     * A focus that is no longer in the list selects the first session.
     *
     * @return The focused session.
     */
    private TerminalSession requireFocused() {
        if (focused == null || !sessions.contains(focused)) {
            focused = sessions.get(0);
        }
        return focused;
    }

    /**
     * The {@code selectSession} method points the window at one pane.
     * A null session returns immediately.
     * A session that is not in the list returns immediately.
     * A switch posts an SSH edit.
     * The connection bar follows the pane state.
     * Close Pane is enabled when more than one pane exists.
     * A last request copies the remote server, the port, the username, and the password onto the bar.
     *
     * A connected pane remembers that link.
     * A switch onto a connected pane applies {@link FoldingPanel.Beat#CONNECTED}.
     * A connecting pane sets the link word to {@code Connecting}.
     * Any other state sets the status to disconnected and applies {@link FoldingPanel.Beat#DISCONNECTED}.
     * The focused user, the Aetheris users, and the pane outline are refreshed.
     * A connected pane asks the notebook for session tokens.
     *
     * @param session The selected pane.
     */
    private void selectSession(TerminalSession session) {
        if (session == null || !sessions.contains(session)) {
            return;
        }
        boolean switched = focused != session;
        focused = session;
        if (switched) {
            offerSshEdit(session);
        }
        connectionPanel.applyConnectionState(session.getState());
        connectionPanel.setClosePaneEnabled(sessions.size() > 1);
        SshSessionController.ConnectRequest last = session.getLastRequest();
        if (last != null) {
            connectionPanel.setHost(last.getHost());
            connectionPanel.setPort(last.getPort());
            connectionPanel.setUsername(last.getUsername());
            connectionPanel.setPassword(last.getPassword());
        }
        connectionPanel.noteLiveUser(liveUserName(session));
        refreshAetherisTab();
        syncAetherisVault(session);
        if (session.getState() == SshConnectionState.CONNECTED) {
            SshSessionController.ConnectRequest held = session.getLastRequest();
            if (held != null) {
                rememberConnected(held.getHost(), held.getPort());
            } else {
                linkWord = "Connected";
                paintFrameStatus();
            }
            if (switched) {
                foldingPanel.apply(FoldingPanel.Beat.CONNECTED);
            }
        } else if (session.getState() == SshConnectionState.CONNECTING) {
            linkWord = "Connecting";
            paintFrameStatus();
        } else {
            setStatusDisconnected();
            foldingPanel.apply(FoldingPanel.Beat.DISCONNECTED);
        }
        refreshFocusedUser();
        refreshAetherisUsers();
        markPaneFocus(session);
        if (aetherisPanel != null && session.getState() == SshConnectionState.CONNECTED) {
            // One ask when this username's mux is up.
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
            aetherisPanel.requestSessionTokens();
        }
    }

    /**
     * The {@code markPaneFocus} method draws the focus outline.
     * More than one pane lights the focused pane.
     * A single pane clears every outline.
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
     * @param session The focused pane.
     */
    private void markPaneFocus(TerminalSession session) {
        boolean several = sessions.size() > 1;
        for (int i = 0; i < sessions.size(); i++) {
            TerminalSession each = sessions.get(i);
            boolean on = several && each == session;
            each.getPanel().seatPaneFocus(on);
        }
    }

    /**
     * The {@code installPaneClickTracker} method selects a pane when its terminal is pressed.
     */
    private void installPaneClickTracker() {
        /**
         * The listener selects the pane under a mouse press.
         * An event that is not a mouse press returns immediately.
         * A missing component returns immediately.
         * A press that is not inside a terminal pane returns immediately.
         * A press on the already focused pane returns immediately.
         * Any other pane is selected on the Swing thread.
         */
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
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
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event.getID() != MouseEvent.MOUSE_PRESSED) {
                    return;
                }
                if (!(event instanceof MouseEvent)) {
                    return;
                }
                MouseEvent mouse = (MouseEvent) event;
                Component source = mouse.getComponent();
                if (source == null) {
                    return;
                }
                TerminalSession hit = sessionContaining(source);
                if (hit == null || hit == focused) {
                    return;
                }
                final TerminalSession chosen = hit;
                /**
                 * The runnable selects the pane that was pressed.
                 */
                SwingUtilities.invokeLater(new Runnable() {
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
                    @Override
                    public void run() {
                        selectSession(chosen);
                    }
                });
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }

    /**
     * The {@code sessionForFocus} method finds the pane that owns the keyboard focus.
     * The search starts at the focus owner and climbs through parents.
     * A component that is a terminal panel, or lies inside one, returns that session.
     * A search that finds none returns null.
     *
     * @return The session under keyboard focus, or null.
     */
    private TerminalSession sessionForFocus() {
        Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        while (c != null) {
            for (int i = 0; i < sessions.size(); i++) {
                if (c == sessions.get(i).getPanel() || isChildOf(c, sessions.get(i).getPanel())) {
                    return sessions.get(i);
                }
            }
            c = c.getParent();
        }
        return null;
    }

    /**
     * The {@code isChildOf} method tests whether a component lies inside another.
     * The search includes the child itself.
     * A null child returns false.
     *
     * @param child The node to test.
     * @param parent The ancestor to find.
     * @return True when the child is the parent or lies under it.
     */
    private static boolean isChildOf(Component child, Component parent) {
        Component c = child;
        while (c != null) {
            if (c == parent) {
                return true;
            }
            c = c.getParent();
        }
        return false;
    }

    /**
     * The {@code promptAndSplit} method asks how to split the focused terminal.
     * The choices are {@code Horizontal}, {@code Vertical}, and {@code Cancel}.
     * The title is {@code New Term}.
     * The message is {@code Split the focused terminal:}.
     * The first choice splits side by side.
     * The second choice splits one above the other.
     * Cancel does nothing.
     */
    private void promptAndSplit() {
        Object[] options = new Object[] { "Horizontal", "Vertical", "Cancel" };
        int choice = JOptionPane.showOptionDialog(
                this,
                "Split the focused terminal:",
                "New Term",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (choice == 0) {
            splitFocused(true);
        } else if (choice == 1) {
            splitFocused(false);
        }
    }

    /**
     * The {@code splitFocused} method splits the focused pane.
     * A pane with no parent returns immediately.
     * The new pane uses the current font size.
     * Import callbacks are wired on the new pane.
     * The split is marked with {@code ssh.pane.split}.
     * One-touch expansion and continuous layout are on.
     * The resize weight is one half.
     * The old pane stays on the left.
     * The new pane is on the right.
     * The new pane becomes the focus.
     *
     * @param horizontal True for a side-by-side split. False for a vertical split.
     */
    private void splitFocused(boolean horizontal) {
        TerminalSession current = requireFocused();
        SshTerminalPanel oldPanel = current.getPanel();
        Container oldParent = oldPanel.getParent();
        if (oldParent == null) {
            return;
        }
        int slot = childSlot(oldParent, oldPanel);

        TerminalSession created = new TerminalSession(termSettings, theme, this);
        created.getPanel().setFontSize(connectionPanel.getFontSize());
        wireConsoleImport(created);
        sessions.add(created);

        oldParent.remove(oldPanel);

        JSplitPane inner = new JSplitPane(
                horizontal ? JSplitPane.HORIZONTAL_SPLIT : JSplitPane.VERTICAL_SPLIT);
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
        inner.putClientProperty("ssh.pane.split", Boolean.TRUE);
        theme.applySplitPane(inner);
        inner.setOneTouchExpandable(true);
        inner.setContinuousLayout(true);
        inner.setResizeWeight(0.5);
        inner.setLeftComponent(oldPanel);
        inner.setRightComponent(created.getPanel());
        putChild(oldParent, inner, slot);
        oldParent.invalidate();
        oldParent.validate();
        oldParent.repaint();
        terminalHost.revalidate();
        terminalHost.repaint();

        selectSession(created);
        final JSplitPane split = inner;
        final boolean sideBySide = horizontal;
        final SshTerminalPanel newPanel = created.getPanel();
        /**
         * The runnable centers the new divider.
         * A span above 32 pixels puts the divider at half that span.
         * The new pane then takes terminal focus.
         */
        SwingUtilities.invokeLater(new Runnable() {
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
            @Override
            public void run() {
                int span = sideBySide ? split.getWidth() : split.getHeight();
                if (span > 32) {
                    split.setDividerLocation(span / 2);
                }
                split.revalidate();
                split.repaint();
                newPanel.requestTerminalFocus();
            }
        });
    }

    /**
     * The {@code closeFocusedPane} method closes the focused pane.
     * A single pane returns immediately.
     * A parent that is not a split pane returns immediately.
     * A missing grandparent or sibling returns immediately.
     * The closing pane is disposed and removed from the list.
     * The sibling replaces the split.
     * The remaining pane is selected and takes focus.
     * A sibling that owns no session selects the first session.
     */
    private void closeFocusedPane() {
        if (sessions.size() <= 1) {
            return;
        }
        TerminalSession closing = requireFocused();
        Container splitParent = closing.getPanel().getParent();
        if (!(splitParent instanceof JSplitPane)) {
            return;
        }
        JSplitPane sp = (JSplitPane) splitParent;
        Component sibling = sp.getLeftComponent() == closing.getPanel()
                ? sp.getRightComponent() : sp.getLeftComponent();
        Container grand = sp.getParent();
        if (grand == null || sibling == null) {
            return;
        }
        int slot = childSlot(grand, sp);
        sp.remove(closing.getPanel());
        sp.remove(sibling);
        closing.dispose();
        sessions.remove(closing);
        grand.remove(sp);
        putChild(grand, sibling, slot);
        grand.revalidate();
        grand.repaint();
        TerminalSession remaining = sessionOwning(sibling);
        if (remaining == null) {
            remaining = sessions.get(0);
        }
        selectSession(remaining);
        remaining.getPanel().requestTerminalFocus();
    }

    /**
     * The {@code childSlot} method finds where a child sits.
     * The left side of a split returns zero.
     * The right side of a split returns one.
     * Any other child returns two.
     *
     * @param parent The container.
     * @param child The direct child.
     * @return Zero for left, one for right, and two otherwise.
     */
    private static int childSlot(Container parent, Component child) {
        if (parent instanceof JSplitPane) {
            JSplitPane sp = (JSplitPane) parent;
            if (sp.getLeftComponent() == child) {
                return 0;
            }
            if (sp.getRightComponent() == child) {
                return 1;
            }
        }
        return 2;
    }

    /**
     * The {@code putChild} method installs a child.
     * On a split pane, slot zero sets the left component.
     * Any other slot on a split pane sets the right component.
     * A parent that is not a split pane adds the child in the center.
     *
     * @param parent The container.
     * @param child The component to install.
     * @param slot Zero for the left side of a split. Any other value is the right side.
     */
    private static void putChild(Container parent, Component child, int slot) {
        if (parent instanceof JSplitPane) {
            JSplitPane sp = (JSplitPane) parent;
            if (slot == 0) {
                sp.setLeftComponent(child);
            } else {
                sp.setRightComponent(child);
            }
            return;
        }
        parent.add(child, BorderLayout.CENTER);
    }

    /**
     * The {@code sessionOwning} method finds the session inside a node.
     * A panel that is the node, or lies under it, matches.
     * The first match is returned.
     * No match returns null.
     *
     * @param node The split child.
     * @return The session, or null.
     */
    private TerminalSession sessionOwning(Component node) {
        for (int i = 0; i < sessions.size(); i++) {
            if (isChildOf(sessions.get(i).getPanel(), node) || sessions.get(i).getPanel() == node) {
                return sessions.get(i);
            }
        }
        return null;
    }

    /**
     * The {@code sessionContaining} method finds the pane that contains a component.
     * The search climbs from the component through its parents.
     * The first terminal panel on that climb is returned.
     * No panel returns null.
     *
     * @param component A component inside a terminal pane.
     * @return The owning session, or null.
     */
    private TerminalSession sessionContaining(Component component) {
        Component walk = component;
        while (walk != null) {
            for (int i = 0; i < sessions.size(); i++) {
                if (walk == sessions.get(i).getPanel()) {
                    return sessions.get(i);
                }
            }
            walk = walk.getParent();
        }
        return null;
    }

    /**
     * The {@code refreshFocusedUser} method sets the east status name.
     * The name stays empty unless the focused session is connected.
     * The connector must be AMWP.
     * The last request must have a username.
     * That username is shown as the vault name.
     */
    private void refreshFocusedUser() {
        String name = "";
        if (focused != null && focused.getState() == SshConnectionState.CONNECTED) {
            SshTtyConnector connector = focused.getLiveConnector();
            SshSessionController.ConnectRequest last = focused.getLastRequest();
            if (connector != null && connector.isAmwp() && last != null
                    && last.getUsername() != null && last.getUsername().length() > 0) {
                name = aetherisPanel.vaultName(last.getUsername());
            }
        }
        statusUserLabel.setText(name);
    }

    /**
     * The {@code refreshAetherisUsers} method fills the connected-user list.
     * Only connected AMWP panes with a username are included.
     * A repeated vault name is added once.
     * The focused pane's name is the selection.
     * A focus that is not in the list leaves the selection null.
     */
    private void refreshAetherisUsers() {
        ArrayList<String> names = new ArrayList<String>();
        String selected = null;
        for (int i = 0; i < sessions.size(); i++) {
            TerminalSession session = sessions.get(i);
            if (session.getState() != SshConnectionState.CONNECTED) {
                continue;
            }
            SshTtyConnector connector = session.getLiveConnector();
            if (connector == null || !connector.isAmwp()) {
                continue;
            }
            SshSessionController.ConnectRequest last = session.getLastRequest();
            if (last == null || last.getUsername() == null || last.getUsername().length() == 0) {
                continue;
            }
            String name = aetherisPanel.vaultName(last.getUsername());
            if (!names.contains(name)) {
                names.add(name);
            }
            if (session == focused) {
                selected = name;
            }
        }
        aetherisPanel.setConnectedUsers(names, selected);
    }

    /**
     * The {@code shellViewRows} method returns the stored Aetheris view rows.
     * @return The stored row count.
     */
    @Override
    public int shellViewRows() {
        return properties.getAetherisViewRows();
    }

    /**
     * The {@code shellViewRowsChanged} method stores the Aetheris view rows.
     * @param rows The row count to store.
     */
    @Override
    public void shellViewRowsChanged(int rows) {
        properties.setAetherisViewRows(rows);
    }

    /**
     * The {@code shellWrapColumns} method returns the stored wrap columns.
     * @return The stored column count.
     */
    @Override
    public int shellWrapColumns() {
        return properties.getAetherisWrapColumns();
    }

    /**
     * The {@code shellWrapColumnsChanged} method stores the wrap columns.
     * @param columns The column count to store.
     */
    @Override
    public void shellWrapColumnsChanged(int columns) {
        properties.setAetherisWrapColumns(columns);
    }

    /**
     * The {@code shellInspectorDivider} method returns the stored inspector divider.
     * @return The stored divider, in thousandths.
     */
    @Override
    public int shellInspectorDivider() {
        return properties.getAetherisInspectorDivider();
    }

    /**
     * The {@code shellInspectorDividerChanged} method stores the inspector divider.
     * @param perMille The divider position, in thousandths.
     */
    @Override
    public void shellInspectorDividerChanged(int perMille) {
        properties.setAetherisInspectorDivider(perMille);
    }

    /**
     * The {@code shellInspectorLineChars} method returns the stored inspector line length.
     * @return The stored character count.
     */
    @Override
    public int shellInspectorLineChars() {
        return properties.getAetherisInspectorLineChars();
    }

    /**
     * The {@code shellInspectorLineCharsChanged} method stores the inspector line length.
     * @param count The character count to store.
     */
    @Override
    public void shellInspectorLineCharsChanged(int count) {
        properties.setAetherisInspectorLineChars(count);
    }

    /**
     * The {@code shellPinBarOpen} method returns whether the pin bar is open.
     * @return True when the stored pin bar is open.
     */
    @Override
    public boolean shellPinBarOpen() {
        return properties.getAetherisPinBarOpen();
    }

    /**
     * The {@code shellPinBarOpenChanged} method stores the pin bar.
     * @param open True to leave the pin bar open.
     */
    @Override
    public void shellPinBarOpenChanged(boolean open) {
        properties.setAetherisPinBarOpen(open);
    }

    /**
     * The {@code focusConnectedUser} method selects the pane for one vault user.
     * A null username returns immediately.
     * An empty username returns immediately.
     * The name is passed through the vault name.
     * A focused connected AMWP pane with that vault name is selected and the search stops.
     * Otherwise the first other connected AMWP pane with that vault name is selected.
     * No match leaves the focus unchanged.
     *
     * @param username The user to focus.
     */
    @Override
    public void focusConnectedUser(String username) {
        if (username == null || username.length() == 0) {
            return;
        }
        String want = aetherisPanel.vaultName(username);
        if (focused != null && focused.getState() == SshConnectionState.CONNECTED) {
            SshTtyConnector connector = focused.getLiveConnector();
            SshSessionController.ConnectRequest last = focused.getLastRequest();
            if (connector != null && connector.isAmwp() && last != null
                    && want.equals(aetherisPanel.vaultName(last.getUsername()))) {
                selectSession(focused);
                return;
            }
        }
        for (int i = 0; i < sessions.size(); i++) {
            TerminalSession session = sessions.get(i);
            if (session.getState() != SshConnectionState.CONNECTED) {
                continue;
            }
            SshTtyConnector connector = session.getLiveConnector();
            if (connector == null || !connector.isAmwp()) {
                continue;
            }
            SshSessionController.ConnectRequest last = session.getLastRequest();
            if (last == null) {
                continue;
            }
            if (want.equals(aetherisPanel.vaultName(last.getUsername()))) {
                selectSession(session);
                return;
            }
        }
    }

    /**
     * The {@code chooseRememberedFile} method opens the remembered file dialog.
     * A stored directory is used when it is a directory.
     * Approve records the selected file.
     * Cancel records no file.
     * A dialog rectangle with a positive width and height is stored.
     * An approved file stores its parent directory when that parent is a directory.
     * The dialog is disposed.
     * A cancel, or a null file, returns null.
     *
     * @param parent The component that owns the dialog.
     * @return The selected file, or null.
     */
    @Override
    public File chooseRememberedFile(Component parent) {
        final RememberedChooser chooser = new RememberedChooser();
        String dirPath = properties.getChooserDirectory();
        if (dirPath != null && dirPath.length() > 0) {
            File dir = new File(dirPath);
            if (dir.isDirectory()) {
                chooser.setCurrentDirectory(dir);
            }
        }
        final JDialog dialog = chooser.openDialog(parent);
        final boolean[] approved = new boolean[1];
        /**
         * The listener closes the file dialog.
         * Approve sets the accepted flag.
         * Cancel clears the accepted flag.
         * The dialog is hidden either way.
         */
        chooser.addActionListener(new ActionListener() {
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
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
                    approved[0] = true;
                } else if (JFileChooser.CANCEL_SELECTION.equals(e.getActionCommand())) {
                    approved[0] = false;
                }
                dialog.setVisible(false);
            }
        });
        Rectangle saved = properties.getChooserBounds();
        if (chooserBoundsUsable(saved)) {
            dialog.setBounds(saved);
        }
        dialog.setVisible(true);
        Rectangle bounds = dialog.getBounds();
        if (bounds != null && bounds.width > 0 && bounds.height > 0) {
            properties.setChooserBounds(bounds);
        }
        File selected = null;
        if (approved[0]) {
            selected = chooser.getSelectedFile();
            if (selected != null) {
                File folder = selected.getParentFile();
                if (folder != null && folder.isDirectory()) {
                    properties.setChooserDirectory(folder.getAbsolutePath());
                }
            }
        }
        dialog.dispose();
        if (!approved[0] || selected == null) {
            return null;
        }
        return selected;
    }

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
    /**
     * The {@code RememberedChooser} opens a file dialog.
     * {@code JFileChooser.createDialog} is protected, so this subclass calls it.
     */
    private static final class RememberedChooser extends JFileChooser {
        /**
         * The {@code openDialog} method creates the file dialog.
         *
         * @param parent The component that owns the dialog.
         * @return The dialog.
         */
        private JDialog openDialog(Component parent) {
            return createDialog(parent);
        }
    }

    /**
     * The {@code chooserBoundsUsable} method tests a saved file-dialog rectangle.
     * A null rectangle returns false.
     * A width below 240 returns false.
     * A height below 180 returns false.
     * A rectangle that intersects any screen returns true.
     *
     * @param box The saved dialog rectangle.
     * @return True when the rectangle meets a screen and the minimum size.
     */
    private boolean chooserBoundsUsable(Rectangle box) {
        if (box == null || box.width < 240 || box.height < 180) {
            return false;
        }
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (int i = 0; i < devices.length; i++) {
            Rectangle screen = devices[i].getDefaultConfiguration().getBounds();
            if (screen != null && screen.intersects(box)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The {@code focusedAmwpConnector} method returns the focused AMWP connector.
     * A missing focus returns null.
     * A missing connector returns null.
     * A connector that is not AMWP returns null.
     *
     * @return The live AMWP connector, or null.
     */
    @Override
    public SshTtyConnector focusedAmwpConnector() {
        TerminalSession session = focused;
        if (session == null) {
            return null;
        }
        SshTtyConnector connector = session.getLiveConnector();
        if (connector == null || !connector.isAmwp()) {
            return null;
        }
        return connector;
    }

    /**
     * The {@code aetherisStatus} method stores the Aetheris status line.
     * A null text returns immediately.
     * The status line is repainted.
     *
     * @param text The status line.
     */
    @Override
    public void aetherisStatus(String text) {
        if (text == null) {
            return;
        }
        aetherisStatusText = text;
        paintFrameStatus();
    }

    /**
     * The {@code aetherisVaultShown} method finishes opening a vault.
     * A missing focus returns immediately.
     * A focus that is not connected returns immediately.
     * A username that is not the open username returns immediately.
     * A last request that is not that open user returns immediately.
     * An AMWP connector is aimed at the username.
     * Pending work for that username is applied.
     * The run for that username is shown.
     *
     * @param username The vault that was shown.
     */
    @Override
    public void aetherisVaultShown(String username) {
        if (focused == null || focused.getState() != SshConnectionState.CONNECTED) {
            return;
        }
        if (username == null || !username.equals(aetherisPanel.openUsername())) {
            return;
        }
        SshSessionController.ConnectRequest last = focused.getLastRequest();
        if (last == null || !aetherisPanel.isOpenUser(last.getUsername())) {
            return;
        }
        SshTtyConnector connector = focused.getLiveConnector();
        if (connector != null && connector.isAmwp()) {
            aimAetherisListener(connector, username);
        }
        aetherisPanel.applyPending(username);
        aetherisPanel.showRunFor(username);
    }

    /**
     * The {@code focusedVaultUser} method returns the focused vault name.
     * A missing focus returns an empty string.
     * A missing last request, or a null username, returns an empty string.
     *
     * @return The vault name, or an empty string.
     */
    @Override
    public String focusedVaultUser() {
        if (focused == null) {
            return "";
        }
        SshSessionController.ConnectRequest last = focused.getLastRequest();
        if (last == null || last.getUsername() == null) {
            return "";
        }
        return aetherisPanel.vaultName(last.getUsername());
    }

    /**
     * The {@code terminalSelection} method returns the focused highlight.
     * A missing pane or panel returns an empty string.
     *
     * @return The selected text, or an empty string.
     */
    @Override
    public String terminalSelection() {
        if (focused == null || focused.getPanel() == null) {
            return "";
        }
        return focused.getPanel().selectedText();
    }

    /**
     * The {@code terminalLastLine} method returns the last screen line of the focused pane.
     * A missing pane or panel returns an empty string.
     *
     * @return The last screen line, or an empty string.
     */
    @Override
    public String terminalLastLine() {
        if (focused == null || focused.getPanel() == null) {
            return "";
        }
        return focused.getPanel().lastScreenLine();
    }

    /**
     * The {@code confirmPtyWrite} method asks before writing a cell to the terminal.
     * The title is {@code Terminal}.
     * The message starts with {@code Write this cell to the terminal?}.
     * A null preview adds an empty line.
     * OK returns true.
     * Cancel returns false.
     *
     * @param parent The component that owns the dialog.
     * @param preview The cell text shown under the question.
     * @return True when the user presses OK.
     */
    @Override
    public boolean confirmPtyWrite(Component parent, String preview) {
        int choice = JOptionPane.showConfirmDialog(
                parent,
                "Write this cell to the terminal?\n" + (preview == null ? "" : preview),
                "Terminal",
                JOptionPane.OK_CANCEL_OPTION);
        return choice == JOptionPane.OK_OPTION;
    }

    /**
     * The {@code writeUndoChord} method sends the undo byte.
     * The byte is {@code 0x1A}.
     * A failure is ignored.
     *
     * // @aa-sdd PK-4217 docs/specs/java/org/aetheris/swing/swing.spec.md
     */
    private void writeUndoChord() {
        try {
            writePty(new byte[] { 0x1A });
        } catch (IOException ignored) {
            // a quiet terminal has no line to undo
        }
    }

    /**
     * The {@code writeRedoChord} method sends the redo sequence.
     * The bytes are escape, {@code [}, {@code 1}, {@code ;}, {@code 6}, and {@code Z}.
     * A failure is ignored.
     */
    private void writeRedoChord() {
        try {
            writePty(new byte[] { 0x1B, (byte) '[', (byte) '1', (byte) ';', (byte) '6', (byte) 'Z' });
        } catch (IOException ignored) {
            // a quiet terminal has no line to redo
        }
    }

    /**
     * The {@code writePty} method writes bytes to the focused connector.
     * A missing focus throws {@code IOException} with {@code no terminal}.
     * A missing connector throws that same exception.
     *
     * @param bytes The bytes to write.
     * @throws IOException The focused pane has no connector.
     */
    @Override
    public void writePty(byte[] bytes) throws IOException {
        if (focused == null || focused.getLiveConnector() == null) {
            throw new IOException("no terminal");
        }
        focused.getLiveConnector().write(bytes);
    }

    /**
     * The {@code sendTerminalPaste} method sends a paste to the focused pane.
     * A missing focus throws {@code IOException} with {@code no terminal}.
     * A missing connector throws that same exception.
     * The text is normalized.
     * A text longer than {@link #MAX_PASTE_CHARS} throws {@code IOException}.
     * That message starts with {@code Paste exceeds }.
     * Any other text is sent as a paste.
     *
     * @param text The paste text.
     * @throws IOException The pane has no connector, or the paste is too long.
     */
    @Override
    public void sendTerminalPaste(String text) throws IOException {
        if (focused == null || focused.getLiveConnector() == null) {
            throw new IOException("no terminal");
        }
        String normalized = ConsolePaste.normalize(text);
        if (normalized.length() > MAX_PASTE_CHARS) {
            throw new IOException("Paste exceeds " + MAX_PASTE_CHARS + " characters.");
        }
        focused.getController().sendPaste(normalized);
    }

    /**
     * The {@code aimFocusedAmwp} method aims the listener at the focused AMWP pane.
     * A missing connector returns immediately.
     * A missing focus returns immediately.
     * A missing username returns immediately.
     * The vault name is passed to {@link #aimAetherisListener}.
     */
    @Override
    public void aimFocusedAmwp() {
        SshTtyConnector connector = focusedAmwpConnector();
        if (connector == null || focused == null) {
            return;
        }
        SshSessionController.ConnectRequest last = focused.getLastRequest();
        if (last == null || last.getUsername() == null) {
            return;
        }
        aimAetherisListener(connector, aetherisPanel.vaultName(last.getUsername()));
    }

    /**
     * The {@code syncAetherisVault} method loads the vault for a connected pane.
     * A null session returns immediately.
     * A session that is not connected returns immediately.
     * A missing connector, or a connector that is not AMWP, returns immediately.
     * A missing username returns immediately.
     * The listener is aimed at the vault name.
     * The epoch increases by one.
     * An already open vault applies pending work and shows the run.
     * Any other user is bound with the new epoch.
     *
     * @param session The focused pane.
     */
    private void syncAetherisVault(TerminalSession session) {
        if (session == null || session.getState() != SshConnectionState.CONNECTED) {
            return;
        }
        SshTtyConnector connector = session.getLiveConnector();
        if (connector == null || !connector.isAmwp()) {
            return;
        }
        SshSessionController.ConnectRequest last = session.getLastRequest();
        if (last == null || last.getUsername() == null || last.getUsername().length() == 0) {
            return;
        }
        String want = aetherisPanel.vaultName(last.getUsername());
        aimAetherisListener(connector, want);
        aetherisEpoch++;
        if (aetherisPanel.retainIfOpen(want, aetherisEpoch)) {
            aetherisPanel.applyPending(want);
            aetherisPanel.showRunFor(want);
            return;
        }
        aetherisPanel.bindUser(last.getUsername(), aetherisEpoch);
    }

    /**
     * The {@code panelFor} method finds the panel for a connector.
     * A null connector returns null.
     * The first session whose live connector is that object is returned.
     * No match returns null.
     *
     * @param connector The session connector.
     * @return The panel, or null.
     */
    private SshTerminalPanel panelFor(SshTtyConnector connector) {
        if (connector == null) {
            return null;
        }
        for (int i = 0; i < sessions.size(); i++) {
            TerminalSession session = sessions.get(i);
            if (connector == session.getLiveConnector()) {
                return session.getPanel();
            }
        }
        return null;
    }

    /**
     * The {@code aimAetherisListener} method installs one listener.
     * A null connector returns immediately.
     * A null or empty username returns immediately.
     * A connector that is already aimed returns immediately.
     * A {@link UserFrameListener} is installed.
     * The connector is then recorded.
     *
     * @param connector The live connector.
     * @param username The vault name.
     */
    private void aimAetherisListener(SshTtyConnector connector, String username) {
        if (connector == null || username == null || username.length() == 0) {
            return;
        }
        if (aimedSessions.contains(connector)) {
            return;
        }
        connector.setAmwpListener(new UserFrameListener(username, connector));
        aimedSessions.add(connector);
    }

    /**
     * The {@code aimSession} method aims a pane that just connected.
     * A null session returns immediately.
     * A null connector returns immediately.
     * A connector that is not AMWP returns immediately.
     * A missing username returns immediately.
     * The listener is aimed at the vault name.
     * {@link #relayArmedMoods} runs after that.
     *
     * @param session The pane.
     * @param connector The live connector.
     */
    private void aimSession(TerminalSession session, SshTtyConnector connector) {
        if (session == null || connector == null || !connector.isAmwp()) {
            return;
        }
        SshSessionController.ConnectRequest last = session.getLastRequest();
        if (last == null || last.getUsername() == null || last.getUsername().length() == 0) {
            return;
        }
        aimAetherisListener(connector, aetherisPanel.vaultName(last.getUsername()));
        relayArmedMoods();
    }

    /**
     * The {@code dropSession} method drops a closed connector.
     * A non-null connector is removed from the aimed set.
     * A null session returns immediately.
     * A missing username returns immediately.
     * The notebook is told that the vault session closed.
     *
     * @param session The pane that closed.
     * @param connector The connector captured before it was cleared.
     */
    private void dropSession(TerminalSession session, SshTtyConnector connector) {
        if (connector != null) {
            aimedSessions.remove(connector);
        }
        if (session == null) {
            return;
        }
        SshSessionController.ConnectRequest last = session.getLastRequest();
        if (last == null || last.getUsername() == null) {
            return;
        }
        aetherisPanel.sessionClosed(aetherisPanel.vaultName(last.getUsername()), connector, null);
    }

    /**
     * The {@code refreshAetherisTab} method enables the Aetheris tab for a focused AMWP session.
     * The tab is enabled only when {@link #focusedAmwpConnector} returns a connector.
     * A disabled tab that is selected is switched back to SSH.
     * That switch does not store the product tab.
     * The folded buttons are refreshed when the folding panel exists.
     *
     * // @aa-sdd PK-3492 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-3809 docs/specs/java/org/aetheris/swing/swing.spec.md
     */
    private void refreshAetherisTab() {
        SshTtyConnector connector = focusedAmwpConnector();
        boolean on = connector != null;
        if (productTabs.getTabCount() > 1) {
            productTabs.setEnabledAt(1, on);
            if (!on && productTabs.getSelectedIndex() == 1) {
                suppressProductTabWrite = true;
                productTabs.setSelectedIndex(0);
                suppressProductTabWrite = false;
            }
        }
        if (foldingPanel != null) {
            foldingPanel.syncPlates();
        }
    }

    /**
     * The {@code maybeRestoreAetherisTab} method selects Aetheris after a requested return.
     * A false restore flag returns immediately.
     * A missing second tab returns immediately.
     * A disabled Aetheris tab returns immediately.
     * The restore flag is then cleared.
     * A tab that is already selected returns immediately.
     * The select does not store the product tab.
     * The notebook scrolls to the newest turn.
     */
    private void maybeRestoreAetherisTab() {
        if (!restoreAetheris) {
            return;
        }
        if (productTabs.getTabCount() < 2 || !productTabs.isEnabledAt(1)) {
            return;
        }
        restoreAetheris = false;
        if (productTabs.getSelectedIndex() == 1) {
            return;
        }
        suppressProductTabWrite = true;
        productTabs.setSelectedIndex(1);
        suppressProductTabWrite = false;
        aetherisPanel.landOnNewest();
    }

    /**
     * The {@code installMenu} method builds File, Tools, the token item, and Help.
     * File holds New Term, Close Pane, Import, Settings, and Exit.
     * Tools holds Show Thinking.
     * The token item opens the token window.
     * Help holds Help and About.
     * The menu bar is given to the folding panel.
     * The current font size is applied to that menu.
     */
    private void installMenu() {
        JMenuBar bar = new JMenuBar();
        bar.setBackground(theme.getPanelBackground());
        bar.setForeground(theme.getMenuForeground());
        UIManager.put("Menu.foreground", theme.getMenuForeground());
        UIManager.put("MenuItem.foreground", theme.getMenuForeground());
        JMenu file = new JMenu("File");
        file.setToolTipText("File. New Term, Close Pane, Import, Settings, and Exit.");
        JMenuItem newTerm = new JMenuItem("New Term...");
        newTerm.setToolTipText("Split the focused pane. Horizontal or vertical.");
        /**
         * The listener opens the split dialog.
         */
        newTerm.addActionListener(new java.awt.event.ActionListener() {
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
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                promptAndSplit();
            }
        });
        JMenuItem closePane = new JMenuItem("Close Pane  (Ctrl+Shift+W)");
        closePane.setToolTipText("Close the focused pane. The last pane stays.");
        /**
         * The listener closes the focused pane.
         */
        closePane.addActionListener(new java.awt.event.ActionListener() {
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
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                closeFocusedPane();
            }
        });
        JMenuItem exit = new JMenuItem("Exit");
        exit.setToolTipText("Close Aetheris. A confirm comes first.");
        /**
         * The listener asks to exit.
         */
        exit.addActionListener(new java.awt.event.ActionListener() {
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
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                confirmAndExit();
            }
        });
        JMenuItem importFile = new JMenuItem("Import...");
        importFile.setToolTipText("Read a file into the focused terminal.");
        /**
         * The listener imports a file into the focused pane.
         */
        importFile.addActionListener(new java.awt.event.ActionListener() {
            // @aa-sdd PK-3509 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                importIntoSession(requireFocused());
            }
        });
        file.add(newTerm);
        file.add(closePane);
        file.add(importFile);
        file.addSeparator();
        // @aa-sdd PK-4247 docs/specs/java/org/aetheris/swing/swing.spec.md
        JMenuItem settings = new JMenuItem("Settings...");
        settings.setToolTipText("Settings opens the global switches for this window.");
        /**
         * The listener opens settings.
         * A missing notebook does nothing.
         */
        settings.addActionListener(new java.awt.event.ActionListener() {
            // @aa-sdd PK-4248 docs/specs/java/org/aetheris/swing/swing.spec.md
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (aetherisPanel != null) {
                    aetherisPanel.openSettings();
                }
            }
        });
        file.add(settings);
        file.add(exit);
        JMenu help = new JMenu("Help");
        help.setToolTipText("Help and About.");
        JMenuItem helpItem = new JMenuItem("Help...");
        helpItem.setToolTipText("Open the help text.");
        /**
         * The listener opens the help text.
         */
        helpItem.addActionListener(new java.awt.event.ActionListener() {
            // @aa-sdd PK-0055 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                showHelp();
            }
        });
        JMenuItem about = new JMenuItem("About Aetheris...");
        about.setToolTipText("Open About Aetheris.");
        /**
         * The listener opens About.
         */
        about.addActionListener(new java.awt.event.ActionListener() {
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
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                showAbout();
            }
        });
        help.add(helpItem);
        help.add(about);
        JMenu tools = new JMenu(ToolsThinking.MENU);
        tools.setToolTipText("Tools. Show Thinking reveals the plate in front.");
        JMenuItem showThinking = new JMenuItem(ToolsThinking.SHOW);
        showThinking.setToolTipText("Show the thinking pane on the plate in front.");
        /**
         * The listener shows thinking on the plate in front.
         */
        showThinking.addActionListener(new java.awt.event.ActionListener() {
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
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                revealFocusedThinking();
            }
        });
        tools.add(showThinking);
        JMenuItem tokenCalc = new JMenuItem(Vt100TokenWindow.ITEM);
        tokenCalc.setToolTipText("Open the token window for the plate in front.");
        /**
         * The listener opens the token window.
         */
        tokenCalc.addActionListener(new java.awt.event.ActionListener() {
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
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                openTokenCalc();
            }
        });
        // FINESSE:f-150. applyMenuFont owns the live width. A cap stored here clips the title.
        bar.add(file);
        bar.add(tools);
        bar.add(tokenCalc);
        bar.add(help);
        theme.applyPopup(file.getPopupMenu());
        theme.applyPopup(tools.getPopupMenu());
        theme.applyPopup(help.getPopupMenu());
        theme.applyMenuBar(bar);
        foldingPanel.seatMenu(bar);
        foldingPanel.applyMenuFont(connectionPanel.getFontSize());
    }

    /**
     * The {@code openTokenCalc} method opens the token window for the selected tab.
     * A missing notebook returns immediately.
     * The Aetheris tab reveals the notebook token window.
     * A held Roux tally reveals the stored character count and block count.
     * Any other SSH pane reveals the session-text length and the non-blank row count.
     * The SSH count comes from {@link #sshLiveCount}.
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
    private void openTokenCalc() {
        int index = productTabs == null ? 0 : productTabs.getSelectedIndex();
        if (aetherisPanel == null) {
            return;
        }
        if (ToolsThinking.front(index) == ToolsThinking.Plate.AETHERIS) {
            aetherisPanel.revealTokenWindow();
            return;
        }
        String user = focusedVaultUser();
        int used = sshLiveCount();
        if (aetherisPanel.rouxSessionHeld(user)) {
            aetherisPanel.revealRouxTokenWindow(
                    used,
                    aetherisPanel.rouxSessionChars(user),
                    aetherisPanel.rouxSessionBlocks(user));
            return;
        }
        String memory = sessionMemoryText();
        aetherisPanel.revealRouxTokenWindow(used, memory.length(), sessionMemoryBlocks(memory));
    }

    /**
     * The {@code revealFocusedThinking} method shows thinking on the selected tab.
     * The Aetheris tab asks the notebook.
     * A missing notebook on that tab returns immediately.
     * The SSH tab asks the focused panel.
     * A missing pane or panel returns immediately.
     */
    private void revealFocusedThinking() {
        int index = productTabs == null ? 0 : productTabs.getSelectedIndex();
        if (ToolsThinking.front(index) == ToolsThinking.Plate.AETHERIS) {
            if (aetherisPanel != null) {
                aetherisPanel.revealThinking();
            }
            return;
        }
        if (focused == null || focused.getPanel() == null) {
            return;
        }
        focused.getPanel().revealThinking();
    }

    /**
     * The {@code ABOUT_APP_PX} constant is the About mark edge, in pixels.
     * The value is 64.
     */
    private static final int ABOUT_APP_PX = 64;

    /**
     * The {@code ABOUT_TEXT} constant is the text on the About box.
     */
    private static final String ABOUT_TEXT = "Aetheris Shell\n"
            + "SSH terminal (DarkJediTerm) for the Casserole/Roux host.\n"
            + "Java + Apache MINA SSHD + JediTerm.\n"
            + "\n"
            + "Author: Clay Lambert\n"
            + "Copyright (C) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>\n"
            + "Licensed under the MIT License.";

    /**
     * The {@code showAbout} method shows the About dialog.
     * The mark is loaded from {@code /icons/aetheris.jpg} at {@link #ABOUT_APP_PX} pixels.
     * The ground behind the mark is black.
     * Line wrap is off.
     * The column count follows the widest line, measured in widths of the letter m, plus one.
     * The row count is the number of lines.
     * The dialog title is {@code About Aetheris}.
     * The dialog is modal and stays above other windows.
     * The dialog is disposed after it closes.
     */
    private void showAbout() {
        JPanel box = new JPanel(new BorderLayout(0, 12));
        box.setBackground(theme.getPanelBackground());
        JPanel marks = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        marks.setOpaque(false);
        marks.add(aboutMark("/icons/aetheris.jpg", ABOUT_APP_PX));
        box.add(marks, BorderLayout.NORTH);
        JTextArea area = new JTextArea(ABOUT_TEXT);
        area.setEditable(false);
        // Every About line stays on one row. The width follows the copyright line.
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
        area.setLineWrap(false);
        area.setWrapStyleWord(false);
        java.awt.FontMetrics aboutMetrics = area.getFontMetrics(area.getFont());
        String[] aboutLines = ABOUT_TEXT.split("\n", -1);
        int aboutRows = aboutLines.length;
        int aboutCols = 1;
        int aboutEm = aboutMetrics.charWidth('m');
        if (aboutEm < 1) {
            aboutEm = 1;
        }
        for (int aboutIndex = 0; aboutIndex < aboutLines.length; aboutIndex++) {
            int wide = aboutMetrics.stringWidth(aboutLines[aboutIndex]);
            int cols = (wide + aboutEm - 1) / aboutEm;
            if (cols + 1 > aboutCols) {
                aboutCols = cols + 1;
            }
        }
        area.setRows(aboutRows);
        area.setColumns(aboutCols);
        area.setBackground(theme.getPanelBackground());
        area.setForeground(theme.getPanelForeground());
        box.add(area, BorderLayout.CENTER);
        JOptionPane pane = new JOptionPane(box, JOptionPane.PLAIN_MESSAGE);
        JDialog dialog = pane.createDialog(this, "About Aetheris");
        dialog.setAlwaysOnTop(true);
        dialog.setModal(true);
        /**
         * The listener brings the About dialog forward when it opens.
         */
        dialog.addWindowListener(new WindowAdapter() {
            // @aa-sdd PK-0056 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            @Override
            public void windowOpened(WindowEvent event) {
                dialog.toFront();
            }
        });
        dialog.setVisible(true);
        dialog.dispose();
    }

    /**
     * The {@code aboutMark} method builds one About image.
     * A missing resource returns an empty label.
     * An icon with a width or height below one returns an empty label.
     * The plate is black and square.
     * The image is drawn with bilinear interpolation.
     * {@link #fillIconGround} turns the light corners black.
     *
     * @param path The resource path.
     * @param edge The square edge, in pixels.
     * @return The label, empty when the file is missing.
     */
    private JLabel aboutMark(String path, int edge) {
        JLabel label = new JLabel();
        URL url = SshClientApp.class.getResource(path);
        if (url == null) {
            return label;
        }
        ImageIcon raw = new ImageIcon(url);
        if (raw.getIconWidth() < 1 || raw.getIconHeight() < 1) {
            return label;
        }
        BufferedImage plate = new BufferedImage(edge, edge, BufferedImage.TYPE_INT_ARGB);
        Graphics2D pen = plate.createGraphics();
        pen.setColor(Color.BLACK);
        pen.fillRect(0, 0, edge, edge);
        pen.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        pen.drawImage(raw.getImage(), 0, 0, edge, edge, null);
        pen.dispose();
        fillIconGround(plate);
        label.setIcon(new ImageIcon(plate));
        return label;
    }

    /**
     * The {@code iconGround} method tests one pixel for the rounded ground.
     * The spread is the highest channel minus the lowest channel.
     * A spread below 28, with a highest channel above 24, returns true.
     *
     * @param rgb The packed pixel.
     * @return True when the pixel is ground.
     */
    private static boolean iconGround(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        int max = Math.max(r, Math.max(g, b));
        int min = Math.min(r, Math.min(g, b));
        return (max - min) < 28 && max > 24;
    }

    /**
     * The {@code fillIconGround} method turns the rounded ground black.
     * A width of zero returns immediately.
     * A height of zero returns immediately.
     * The search starts at the four corners.
     * A pixel that fails {@link #iconGround} stops that branch.
     * A ground pixel becomes opaque black.
     * The search continues to the four neighboring pixels.
     *
     * @param plate The scaled mark.
     */
    private static void fillIconGround(BufferedImage plate) {
        int w = plate.getWidth();
        int h = plate.getHeight();
        if (w == 0 || h == 0) {
            return;
        }
        boolean[] seen = new boolean[w * h];
        int[] stack = new int[w * h];
        int top = 0;
        stack[top] = 0;
        top = top + 1;
        stack[top] = w - 1;
        top = top + 1;
        stack[top] = (h - 1) * w;
        top = top + 1;
        stack[top] = (h - 1) * w + (w - 1);
        top = top + 1;
        while (top > 0) {
            top = top - 1;
            int index = stack[top];
            if (seen[index]) {
                continue;
            }
            seen[index] = true;
            int x = index % w;
            int y = index / w;
            if (!iconGround(plate.getRGB(x, y))) {
                continue;
            }
            plate.setRGB(x, y, 0xff000000);
            if (x > 0) {
                stack[top] = index - 1;
                top = top + 1;
            }
            if (x + 1 < w) {
                stack[top] = index + 1;
                top = top + 1;
            }
            if (y > 0) {
                stack[top] = index - w;
                top = top + 1;
            }
            if (y + 1 < h) {
                stack[top] = index + w;
                top = top + 1;
            }
        }
    }

    /**
     * The {@code showHelp} method shows the client help text.
     * The text area wraps words.
     * The scroll pane prefers 520 by 360.
     * The overlay scroll bars are installed.
     * The dialog title is {@code Help}.
     */
    private void showHelp() {
        String text = "Aetheris Shell\n\n"
                + "Connect: fill host/port/user/password and Connect.\n"
                + "Username combo remembers accounts (if Remember password).\n"
                + "New Term: split the focused pane Horizontal or Vertical.\n"
                + "  Ctrl+Shift+D horizontal   Ctrl+Shift+E vertical   Ctrl+Shift+W close pane\n"
                + "Font size applies to every pane.\n"
                + "Paste: Ctrl+V / Ctrl+Shift+V opens the paste editor. OK inserts the text. Enter in the terminal submits. Load File is the same path.\n"
                + "Import: File menu or the console right-click menu. The file dialog inserts the file at the terminal cursor.\n"
                + "Restart always starts with one pane.\n\n"
                + "On the server: Esc toggles cmd> (medium blue). Type help for commands.\n"
                + "message list is terse; message list verbose is full.\n"
                + "message find by 'text' scans this session (case-insensitive).\n"
                + "One disk-backed login per username; change the combo before Connect on a new pane.";
        JTextArea area = new JTextArea(text);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(theme.getPanelBackground());
        area.setForeground(theme.getPanelForeground());
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new java.awt.Dimension(520, 360));
        theme.applyScrollPane(scroll);
        JOptionPane.showMessageDialog(this, scroll, "Help", JOptionPane.PLAIN_MESSAGE);
    }

    // @aa-sdd PK-0665 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-3596 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code UserFrameListener} receives AMWP frames for one SSH user.
     * Frames are accepted under the stored username.
     * Console thought is offered to the pane that owns the connector.
     */
    private final class UserFrameListener implements AmwpClientListener {
        private final String username;
        private final SshTtyConnector connector;

        /**
         * The constructor stores the username and the connector.
         *
         * @param username The vault name.
         * @param connector The connector that owns the frames.
         */
        private UserFrameListener(String username, SshTtyConnector connector) {
            this.username = username;
            this.connector = connector;
        }

        /**
         * The {@code onAmwpFrame} method accepts one frame.
         * The notebook accepts the frame under the stored username.
         * A frame with no thought text and no end marker returns immediately.
         * Any other frame updates the console thought on the Swing thread.
         *
         * @param frame The AMWP frame.
         */
        @Override
        public void onAmwpFrame(final AmwpFrame frame) {
            aetherisPanel.acceptFrame(username, frame);
            final String chunk = aetherisPanel.nilThinkText(frame);
            final boolean end = aetherisPanel.nilThinkEnd(frame);
            if (chunk == null && !end) {
                return;
            }
            /**
             * The runnable offers console thought to the open user.
             * A username that is not open returns immediately.
             * A missing panel returns immediately.
             * A closed thought pane finishes the thought when the frame ends.
             * An open thought pane offers a non-null chunk with the notebook tag.
             * An end marker finishes the thought.
             */
            SwingUtilities.invokeLater(new Runnable() {
                // @aa-sdd PK-0666 docs/specs/java/org/casserole/llm/llm.spec.md
                // @aa-sdd PK-3597 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                @Override
                public void run() {
                    if (!username.equals(aetherisPanel.openUsername())) {
                        return;
                    }
                    SshTerminalPanel panel = panelFor(connector);
                    if (panel == null) {
                        return;
                    }
                    if (!aetherisPanel.consoleThoughtOpen()) {
                        if (end) {
                            panel.finishConsoleThought();
                        }
                        return;
                    }
                    if (chunk != null) {
                        panel.offerConsoleThought(chunk, aetherisPanel.consoleThoughtTag());
                    }
                    if (end) {
                        panel.finishConsoleThought();
                    }
                }
            });
        }

        /**
         * The {@code onAmwpClosed} method drops this connector.
         * The connector is removed from the aimed set.
         * The notebook is told that the session closed.
         *
         * @param reason The close reason.
         */
        @Override
        public void onAmwpClosed(String reason) {
            aimedSessions.remove(connector);
            aetherisPanel.sessionClosed(username, connector, reason);
        }

        /**
         * The {@code onConsoleModel} method forwards a console model onto the Swing thread.
         *
         * @param model The model name.
         * @param think The thought text.
         */
        @Override
        public void onConsoleModel(final String model, final String think) {
            /**
             * The runnable gives the model and the thought to the notebook.
             */
            SwingUtilities.invokeLater(new Runnable() {
                // @aa-sdd PK-0667 docs/specs/java/org/casserole/llm/llm.spec.md
                @Override
                public void run() {
                    aetherisPanel.onConsoleModel(model, think);
                }
            });
        }
    }

    /**
     * The {@code paintCaption} method sets the native title-bar colors on Windows.
     * A missing operating-system name returns immediately.
     * A name that does not contain {@code windows} returns immediately.
     * A handle of zero returns immediately.
     * {@link #applyCaption} writes the attributes.
     * A failure leaves the window open.
     */
    private void paintCaption() {
        try {
            String os = System.getProperty("os.name");
            if (os == null || os.toLowerCase(Locale.ROOT).indexOf("windows") < 0) {
                return;
            }
            long hwnd = captionHandle();
            if (hwnd == 0L) {
                return;
            }
            applyCaption(hwnd);
        } catch (Throwable ignored) {
        }
    }

    /**
     * The {@code captionHandle} method finds this frame's native window.
     * A null title returns zero.
     * An empty title returns zero.
     * The lookup uses {@code user32} and {@code FindWindowW}.
     * A missing symbol returns zero.
     * The class name is {@code SunAwtFrame}.
     * The class name and the title are UTF-16LE.
     * A null result returns zero.
     *
     * @return The native handle, or zero.
     * @throws Throwable The native lookup failed.
     */
    private long captionHandle() throws Throwable {
        String title = getTitle();
        if (title == null || title.length() == 0) {
            return 0L;
        }
        Linker linker = Linker.nativeLinker();
        try (Arena arena = Arena.ofConfined()) {
            SymbolLookup user = SymbolLookup.libraryLookup("user32", arena);
            MemorySegment symbol = user.find("FindWindowW").orElse(null);
            if (symbol == null) {
                return 0L;
            }
            FunctionDescriptor desc = FunctionDescriptor.of(
                    ValueLayout.ADDRESS,
                    ValueLayout.ADDRESS,
                    ValueLayout.ADDRESS);
            MethodHandle call = linker.downcallHandle(symbol, desc);
            MemorySegment className = arena.allocateFrom("SunAwtFrame", StandardCharsets.UTF_16LE);
            MemorySegment windowName = arena.allocateFrom(title, StandardCharsets.UTF_16LE);
            MemorySegment found = (MemorySegment) call.invoke(className, windowName);
            if (found == null) {
                return 0L;
            }
            return found.address();
        }
    }

    /**
     * The {@code applyCaption} method writes four window attributes.
     * The library is {@code dwmapi}.
     * The call is {@code DwmSetWindowAttribute}.
     * A missing symbol returns immediately.
     * Attribute 20 is set to 1.
     * Attribute 35 is set to -1.
     * Attribute 34 is set to -1.
     * Attribute 36 is set to -1.
     *
     * @param hwnd The native window.
     * @throws Throwable The native call failed.
     */
    private static void applyCaption(long hwnd) throws Throwable {
        Linker linker = Linker.nativeLinker();
        try (Arena arena = Arena.ofConfined()) {
            SymbolLookup dwm = SymbolLookup.libraryLookup("dwmapi", arena);
            MemorySegment symbol = dwm.find("DwmSetWindowAttribute").orElse(null);
            if (symbol == null) {
                return;
            }
            FunctionDescriptor desc = FunctionDescriptor.of(
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS,
                    ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS,
                    ValueLayout.JAVA_INT);
            MethodHandle call = linker.downcallHandle(symbol, desc);
            MemorySegment window = MemorySegment.ofAddress(hwnd);
            captionDword(call, window, 20, 1, arena);
            captionDword(call, window, 35, -1, arena);
            captionDword(call, window, 34, -1, arena);
            captionDword(call, window, 36, -1, arena);
        }
    }

    /**
     * The {@code captionDword} method writes one integer attribute.
     * The value is stored in a four-byte native integer.
     * The call passes that pointer and a size of four.
     *
     * @param call The native method handle.
     * @param window The native window address.
     * @param attribute The attribute number.
     * @param value The integer to write.
     * @param arena The arena that holds the integer.
     * @throws Throwable The native call failed.
     */
    private static void captionDword(
            MethodHandle call, MemorySegment window, int attribute, int value, Arena arena) throws Throwable {
        MemorySegment attr = arena.allocate(ValueLayout.JAVA_INT);
        attr.set(ValueLayout.JAVA_INT, 0, value);
        call.invoke(window, attribute, attr, 4);
    }

    /**
     * The {@code colorRef} method packs a color as {@code 0x00BBGGRR}.
     * Red stays in the low byte.
     * Green is shifted eight bits.
     * Blue is shifted sixteen bits.
     *
     * @param color The color to pack.
     * @return The packed value.
     */
    private static int colorRef(Color color) {
        int red = color.getRed() & 0xff;
        int green = color.getGreen() & 0xff;
        int blue = color.getBlue() & 0xff;
        return red | (green << 8) | (blue << 16);
    }
}
/*****************************************************************************************
 * End of Class: SshClientApp.java 082626
 ****************************************************************************************/
/*****************************************************************************************
 * End of Class: SshClientApp.java 082426
 ****************************************************************************************/
