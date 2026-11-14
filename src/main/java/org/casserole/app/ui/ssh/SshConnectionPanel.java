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
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// @aa-sdd PK-1145 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1134 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1130 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1106 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-776 docs/specs/common/stages.spec.md
/**
 * The {@code SshConnectionPanel} is the connection bar.
 * The bar holds the host, the port, the user, the password, and the session mode.
 * Connect, disconnect, and the other buttons call the {@link Listener}.
 * The frame owns the panel.
 * The bottom pad is 4 pixels, which keeps the buttons clear of the joint.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
 *
 * @author clayl (2026-08-24)
 */
// @aa-sdd PK-192 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-768 docs/specs/common/stages.spec.md
// @aa-sdd PK-776 docs/specs/common/stages.spec.md
// @aa-sdd PK-206 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-1095 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-947 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-690 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-776 docs/specs/common/stages.spec.md
// @aa-sdd PK-264 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-271 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-277 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-200 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-1130 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-207 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-213 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-776 docs/specs/common/stages.spec.md
// @aa-sdd PK-776 docs/specs/common/stages.spec.md
public class SshConnectionPanel extends JPanel {

    // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
    /**
     * The {@code Listener} receives the actions raised by the connection bar.
     * The owning frame implements this type.
     * The methods are ordinary methods, not lambdas.
     */
    // @aa-sdd PK-280 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    public interface Listener {

        /**
         * The method runs when Connect is pressed.
         */
        void connectRequested();

        /**
         * The method runs when Disconnect is pressed.
         */
        void disconnectRequested();

        /**
         * The method runs when Reconnect is pressed.
         */
        void reconnectRequested();

        /**
         * The method runs when the font-size incrementor changes.
         *
         * @param fontSize The font size is the new point size.
         */
        void fontSizeChanged(int fontSize);

        /**
         * The method runs after the user confirms a VT100 or PTY mode change.
         *
         * @param mode The mode is the newly selected mode.
         */
        void modeChangeRequested(SshSessionMode mode);

        /**
         * The method runs when Clear Terminal is pressed.
         */
        void clearTerminalRequested();

        /**
         * The method runs when Load File is pressed.
         * The file is opened in the paste editor.
         */
        void sendFileRequested();

        /**
         * The method runs when New Term is pressed.
         * The action splits the focused pane.
         */
        void newTermRequested();

        /**
         * The method runs when Close Pane is pressed.
         * The focused extra pane is disconnected and dropped from the splitter.
         */
        void closePaneRequested();

        /**
         * The method runs when the username combo moves to a user who is not the live session.
         * Connect may proceed for that user.
         * The bar stays open until a connect lands.
         */
        void usernameChosen();
    }

    private final SshClientProperties properties;
    private final SshClientTheme theme;
    private final Listener listener;

    private final JTextField hostField;
    private final JTextField portField;
    private final JComboBox<String> usernameCombo;
    private final JPasswordField passwordField;
    private final JCheckBox rememberPasswordCheck;
    private final JComboBox<SshSessionMode> modeCombo;
    private final JButton fontMinus;
    private final JButton fontPlus;
    private final JTextField fontField;
    private final JPanel fontControl;
    private final Timer fontTimer;
    private int fontSize;
    private boolean fontSuppress;
    private final JButton connectButton;
    private final JButton disconnectButton;
    private final JButton reconnectButton;
    private final JButton clearButton;
    private final JButton sendFileButton;
    private final JButton newTermButton;
    private final JButton closePaneButton;
    /**
     * The field is the button row.
     * The thinking cluster sits after the font control while the bar is open.
     */
    private FlowRow buttonRow;
    /**
     * The field is the spinner and clock cluster.
     * The folded menu row may be holding it.
     */
    private JComponent thinkCluster;

    private boolean ignoreModeEvent;
    private boolean ignoreUserEvent;
    private String liveUser = "";
    private SshConnectionState shownState = SshConnectionState.DISCONNECTED;

    /**
     * The constructor builds the connection bar from the stored fields.
     * The password is filled only when remember-password is already set.
     * The font control is a minus button, the size, and a plus button.
     * The size runs from {@code 8} to {@code 36}.
     * The bar starts in the disconnected state.
     * Close Pane starts disabled.
     *
     * @param properties The properties object is the persistence store.
     * @param theme The theme is the dark painter.
     * @param listener The listener receives the button actions.
     */
    public SshConnectionPanel(SshClientProperties properties, SshClientTheme theme, Listener listener) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(0, 0, 4, 0));
        this.properties = properties;
        this.theme = theme;
        this.listener = listener;

        theme.applyPanel(this);

        hostField = new JTextField(properties.getHost(), 18);
        portField = new JTextField(Integer.toString(properties.getPort()), 5);
        usernameCombo = new JComboBox<String>(properties.getAccountUsernames());
        usernameCombo.setEditable(true);
        usernameCombo.setSelectedItem(properties.getUsername());
        passwordField = new JPasswordField(18);
        if (properties.isRememberPassword()) {
            passwordField.setText(properties.getPassword());
        }
        rememberPasswordCheck = new JCheckBox("Remember password", properties.isRememberPassword());
        modeCombo = new JComboBox<SshSessionMode>(SshSessionMode.values());
        modeCombo.setSelectedItem(properties.getMode());
        int storedFont = properties.getFontSize();
        if (storedFont < FONT_MIN) {
            storedFont = FONT_MIN;
        }
        if (storedFont > FONT_MAX) {
            storedFont = FONT_MAX;
        }
        fontSize = storedFont;
        fontMinus = new JButton("-");
        fontPlus = new JButton("+");
        fontField = new JTextField(Integer.toString(fontSize), 4);
        fontControl = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        fontControl.add(fontMinus);
        fontControl.add(fontField);
        fontControl.add(fontPlus);
        /**
         * The timer listener applies a typed font size when the wait ends.
         */
        fontTimer = new Timer(800, new ActionListener() {
            // @aa-sdd PK-776 docs/specs/common/stages.spec.md
            @Override
            public void actionPerformed(ActionEvent e) {
                applyTypedFont();
            }
        });
        fontTimer.setRepeats(false);

        connectButton = new JButton("Connect");
        disconnectButton = new JButton("Disconnect");
        reconnectButton = new JButton("Reconnect");
        clearButton = new JButton("Clear Terminal");
        sendFileButton = new JButton("Load File");
        newTermButton = new JButton("New Term");
        closePaneButton = new JButton("Close Pane");

        applyTheme();
        layoutControls();
        wireEvents();
        applyBarFont(getFontSize());
        applyConnectionState(SshConnectionState.DISCONNECTED);
    }

    /**
     * The method enables Connect, Disconnect, Reconnect, and Load File from the session state.
     * A null state is treated as disconnected.
     * A connected state turns Connect off and turns Disconnect, Reconnect, and Load File on.
     * A connecting state turns those four buttons off.
     * Any other state turns Connect on and turns Disconnect, Reconnect, and Load File off.
     * New Term and Clear Terminal stay enabled.
     * {@link #armConnectForChoice} may turn Connect back on for a different username.
     *
     * @param state The state is the current connection state.
     */
    public void applyConnectionState(SshConnectionState state) {
        shownState = state == null ? SshConnectionState.DISCONNECTED : state;
        if (state == SshConnectionState.CONNECTED) {
            connectButton.setEnabled(false);
            disconnectButton.setEnabled(true);
            reconnectButton.setEnabled(true);
            sendFileButton.setEnabled(true);
        } else if (state == SshConnectionState.CONNECTING) {
            connectButton.setEnabled(false);
            disconnectButton.setEnabled(false);
            reconnectButton.setEnabled(false);
            sendFileButton.setEnabled(false);
        } else {
            connectButton.setEnabled(true);
            disconnectButton.setEnabled(false);
            reconnectButton.setEnabled(false);
            sendFileButton.setEnabled(false);
        }
        newTermButton.setEnabled(true);
        clearButton.setEnabled(true);
        armConnectForChoice();
    }

    /**
     * The method records the user this pane is actually holding.
     * An empty value means the link is down.
     * A null value is stored as empty.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-132
     *
     * @param user The user is the live username, or empty.
     */
    void noteLiveUser(String user) {
        liveUser = user == null ? "" : user;
        armConnectForChoice();
    }

    /**
     * The method enables Connect when the chosen name differs from the live name.
     * The live name leaves Connect off while the session is connected.
     * A connecting session is left alone.
     */
    private void armConnectForChoice() {
        if (shownState == SshConnectionState.CONNECTING) {
            return;
        }
        String chosen = getUsername();
        boolean same = liveUser.length() > 0 && liveUser.equals(chosen);
        if (same && shownState == SshConnectionState.CONNECTED) {
            connectButton.setEnabled(false);
            return;
        }
        if (!same) {
            connectButton.setEnabled(true);
        }
    }

    /**
     * The method enables or disables Close Pane.
     * The last pane cannot be closed, so the caller passes false for that pane.
     *
     * @param enabled The flag is true when Close Pane is usable.
     */
    public void setClosePaneEnabled(boolean enabled) {
        closePaneButton.setEnabled(enabled);
    }

    /**
     * The method returns the trimmed host field.
     *
     * @return The return value is that text.
     */
    public String getHost() {
        return hostField.getText().trim();
    }

    /**
     * The method returns the port field as an integer.
     * Text that is not an integer returns {@link SshClientProperties#DEFAULT_PORT}.
     *
     * @return The return value is the parsed port, or the default port.
     */
    public int getPort() {
        try {
            return Integer.parseInt(portField.getText().trim());
        } catch (NumberFormatException ex) {
            return SshClientProperties.DEFAULT_PORT;
        }
    }

    /**
     * The method returns the trimmed username from the combo editor.
     * A null editor item uses the selected item.
     * A null item returns an empty string.
     *
     * @return The return value is that username.
     */
    public String getUsername() {
        Object item = usernameCombo.getEditor().getItem();
        if (item == null) {
            item = usernameCombo.getSelectedItem();
        }
        return item == null ? "" : String.valueOf(item).trim();
    }

    /**
     * The method sets the username combo without treating the change as a user choice.
     * A null value is shown as empty.
     *
     * @param username The username is the combo value.
     */
    public void setUsername(String username) {
        ignoreUserEvent = true;
        usernameCombo.setSelectedItem(username == null ? "" : username);
        ignoreUserEvent = false;
    }

    /**
     * The method sets the host field.
     * A null value is shown as empty.
     *
     * @param host The host is the field text.
     */
    public void setHost(String host) {
        hostField.setText(host == null ? "" : host);
    }

    /**
     * The method sets the port field from the integer.
     *
     * @param port The port is the field value.
     */
    public void setPort(int port) {
        portField.setText(Integer.toString(port));
    }

    /**
     * The method sets the password field.
     * A null value is shown as empty.
     *
     * @param password The password is the field text.
     */
    public void setPassword(String password) {
        passwordField.setText(password == null ? "" : password);
    }

    /**
     * The method returns the password field contents.
     *
     * @return The return value is that text.
     */
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    /**
     * The method reports whether Remember password is selected.
     *
     * @return The return value is true when the check is selected.
     */
    public boolean isRememberPassword() {
        return rememberPasswordCheck.isSelected();
    }

    /**
     * The method returns the selected session mode.
     * A selection that is not a {@link SshSessionMode} returns {@link SshSessionMode#VT100}.
     *
     * @return The return value is that mode.
     */
    public SshSessionMode getMode() {
        Object selected = modeCombo.getSelectedItem();
        if (selected instanceof SshSessionMode) {
            return (SshSessionMode) selected;
        }
        return SshSessionMode.VT100;
    }

    /**
     * The method returns the font size the incrementor last applied.
     * The range is {@code 8} to {@code 36}.
     *
     * @return The return value is the point size.
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * The method stores the host, the port, the username, the remember flag, and the password.
     * The password is kept only when Remember password is selected.
     * The account list is updated from those same fields.
     * The username combo is reloaded without losing the typed name.
     * The store is written to disk.
     */
    public void persistConnectionFields() {
        properties.setHost(getHost());
        properties.setPort(getPort());
        properties.setUsername(getUsername());
        properties.setPassword(getPassword(), isRememberPassword());
        properties.upsertAccount(getUsername(), getPassword(), isRememberPassword());
        refreshUsernameChoices();
        properties.save();
    }

    /**
     * The method reloads the combo items from the store.
     * The typed name stays selected.
     * The reload does not fire a user-choice event.
     */
    public void refreshUsernameChoices() {
        String current = getUsername();
        ignoreUserEvent = true;
        usernameCombo.removeAllItems();
        String[] names = properties.getAccountUsernames();
        for (int i = 0; i < names.length; i++) {
            usernameCombo.addItem(names[i]);
        }
        usernameCombo.setSelectedItem(current);
        ignoreUserEvent = false;
    }

    /**
     * The method stores the selected mode and writes the file.
     */
    public void persistMode() {
        properties.setMode(getMode());
        properties.save();
    }

    /**
     * The method stores the current font size and writes the file.
     */
    public void persistFontSize() {
        properties.setFontSize(getFontSize());
        properties.save();
    }

    /**
     * The method applies the dark colors to every field, combo, check, and button on the bar.
     */
    private void applyTheme() {
        theme.applyTextField(hostField);
        theme.applyTextField(portField);
        theme.applyComboBox(usernameCombo);
        theme.applyPasswordField(passwordField);
        theme.applyCheckBox(rememberPasswordCheck);
        theme.applyComboBox(modeCombo);
        theme.applyPanel(fontControl);
        theme.applyButton(fontMinus);
        theme.applyTextField(fontField);
        theme.applyButton(fontPlus);
        theme.applyButton(connectButton);
        theme.applyButton(disconnectButton);
        theme.applyButton(reconnectButton);
        theme.applyButton(clearButton);
        theme.applyButton(sendFileButton);
        theme.applyButton(newTermButton);
        theme.applyButton(closePaneButton);
    }

    /**
     * The method sets the bar to the application point size.
     * A size below {@code 8} becomes {@code 8}.
     * A size above {@code 36} becomes {@code 36}.
     * Labels, fields, buttons, and the thinking cluster take that face together.
     * Tool tips and the Load File width are applied again.
     *
     * @param fontSize The font size is the incrementor value.
     */
    void applyBarFont(int fontSize) {
        int size = fontSize;
        if (size < 8) {
            size = 8;
        }
        if (size > 36) {
            size = 36;
        }
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, size);
        seatBarFont(this, font);
        if (thinkCluster != null) {
            seatBarFont(thinkCluster, font);
        }
        seatBarTips();
        seatSendFileWidth();
        revalidate();
        repaint();
    }

    /**
     * The text is the tool tip for the host field and its label.
     */
    private static final String HOST_TIP = "Host the SSH session dials.";

    /**
     * The text is the tool tip for the port field and its label.
     */
    private static final String PORT_TIP = "Port the SSH session dials.";

    /**
     * The text is the tool tip for the username combo and its label.
     */
    private static final String USER_TIP = "User the SSH session logs in as. The list remembers accounts.";

    /**
     * The text is the tool tip for the password field and its label.
     */
    private static final String PASSWORD_TIP = "Password for that user.";

    /**
     * The text is the tool tip for the remember-password check.
     */
    private static final String REMEMBER_TIP = "Remember password keeps the password for this user.";

    /**
     * The text is the tool tip for the session-mode combo and its label.
     */
    private static final String MODE_TIP = "Mode for this session.";

    /**
     * The smallest font size the incrementor applies.
     */
    private static final int FONT_MIN = 8;

    /**
     * The largest font size the incrementor applies.
     */
    private static final int FONT_MAX = 36;

    /**
     * The text is the tool tip for the font label and the size field.
     */
    private static final String FONT_TIP = "Font size for the panes and this bar. The range is 8 to 36.";

    /**
     * The text is the tool tip for the smaller-font button.
     */
    private static final String FONT_MINUS_TIP = "Smaller font. The panes and this bar take the new size.";

    /**
     * The text is the tool tip for the larger-font button.
     */
    private static final String FONT_PLUS_TIP = "Larger font. The panes and this bar take the new size.";

    /**
     * The text is the tool tip for Connect.
     */
    private static final String CONNECT_TIP = "Connect opens the SSH session with the host, port, user, and password on this bar.";

    /**
     * The text is the tool tip for Disconnect.
     */
    private static final String DISCONNECT_TIP = "Disconnect closes the SSH session. The bar opens.";

    /**
     * The text is the tool tip for Reconnect.
     */
    private static final String RECONNECT_TIP = "Reconnect closes the session and opens it again with the fields on this bar.";

    /**
     * The text is the tool tip for Clear Terminal.
     */
    private static final String CLEAR_TERM_TIP = "Clear Terminal wipes the focused pane.";

    /**
     * The text is the tool tip for Load File.
     */
    private static final String SEND_FILE_TIP = "Load File reads a file and opens it in the paste editor. OK inserts the text.";

    /**
     * The text is the tool tip for New Term.
     */
    private static final String NEW_TERM_TIP = "New Term splits the focused pane. Horizontal or vertical.";

    /**
     * The text is the tool tip for Close Pane.
     */
    private static final String CLOSE_PANE_TIP = "Close Pane closes the focused pane. The last pane stays.";

    /**
     * The method sets a tool tip on every control on the connection bar.
     * The font incrementor buttons and the size field each receive a tip.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-157
     */
    private void seatBarTips() {
        modeCombo.setToolTipText(MODE_TIP);
        hostField.setToolTipText(HOST_TIP);
        portField.setToolTipText(PORT_TIP);
        usernameCombo.setToolTipText(USER_TIP);
        passwordField.setToolTipText(PASSWORD_TIP);
        rememberPasswordCheck.setToolTipText(REMEMBER_TIP);
        fontMinus.setToolTipText(FONT_MINUS_TIP);
        fontPlus.setToolTipText(FONT_PLUS_TIP);
        fontField.setToolTipText(FONT_TIP);
        connectButton.setToolTipText(CONNECT_TIP);
        disconnectButton.setToolTipText(DISCONNECT_TIP);
        reconnectButton.setToolTipText(RECONNECT_TIP);
        clearButton.setToolTipText(CLEAR_TERM_TIP);
        sendFileButton.setToolTipText(SEND_FILE_TIP);
        newTermButton.setToolTipText(NEW_TERM_TIP);
        closePaneButton.setToolTipText(CLOSE_PANE_TIP);
    }

    /**
     * The method sizes Load File so the full word stays visible.
     * The width is the text, the insets, and three ems.
     * The extra ems clear the round edge.
     * The height follows the font and the insets.
     * A missing measurement becomes {@code 1}.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-157
     */
    private void seatSendFileWidth() {
        sendFileButton.setPreferredSize(null);
        sendFileButton.setMinimumSize(null);
        sendFileButton.setMaximumSize(null);
        Font font = sendFileButton.getFont();
        FontMetrics metrics;
        if (font == null) {
            metrics = sendFileButton.getFontMetrics(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        } else {
            metrics = sendFileButton.getFontMetrics(font);
        }
        Insets insets = sendFileButton.getInsets();
        if (insets == null) {
            insets = new Insets(0, 0, 0, 0);
        }
        int spare = metrics.charWidth('M');
        if (spare < 1) {
            spare = 1;
        }
        String label = sendFileButton.getText();
        if (label == null) {
            label = "";
        }
        int width = metrics.stringWidth(label) + insets.left + insets.right + (spare * 3);
        int height = metrics.getHeight() + insets.top + insets.bottom;
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        Dimension size = new Dimension(width, height);
        sendFileButton.setPreferredSize(size);
        sendFileButton.setMinimumSize(size);
        sendFileButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
    }

    /**
     * The method sets the font on one bar control and then on its children.
     * Labels, buttons, text fields, and combos take the font.
     * A combo editor takes the font.
     *
     * @param node The node is a control or a row.
     * @param font The font is the application face.
     */
    private void seatBarFont(Component node, Font font) {
        if (node instanceof JLabel || node instanceof AbstractButton || node instanceof JTextField
                || node instanceof JComboBox) {
            node.setFont(font);
        }
        if (node instanceof JComboBox) {
            JComboBox<?> combo = (JComboBox<?>) node;
            Component editor = combo.getEditor().getEditorComponent();
            if (editor != null) {
                editor.setFont(font);
            }
        }
        if (node instanceof Container) {
            Component[] kids = ((Container) node).getComponents();
            for (int i = 0; i < kids.length; i++) {
                seatBarFont(kids[i], font);
            }
        }
    }

    /**
     * The method lays the host fields on the first row.
     * The button row sits under that row.
     * The font control sits on the button row, after Close Pane.
     * A thin rule separates Close Pane from the font label.
     */
    private void layoutControls() {
        FlowRow host = new FlowRow();
        theme.applyPanel(host);
        host.add(flowLabel("Mode", MODE_TIP));
        host.add(modeCombo);
        host.add(flowLabel("Host", HOST_TIP));
        host.add(hostField);
        host.add(flowLabel("Port", PORT_TIP));
        host.add(portField);
        host.add(flowLabel("Username", USER_TIP));
        host.add(usernameCombo);
        host.add(flowLabel("Password", PASSWORD_TIP));
        host.add(passwordField);
        host.add(rememberPasswordCheck);

        buttonRow = new FlowRow();
        theme.applyPanel(buttonRow);
        buttonRow.add(connectButton);
        buttonRow.add(disconnectButton);
        buttonRow.add(reconnectButton);
        buttonRow.add(clearButton);
        buttonRow.add(sendFileButton);
        buttonRow.add(newTermButton);
        buttonRow.add(closePaneButton);
        buttonRow.add(barRule());
        buttonRow.add(flowLabel("Font size", FONT_TIP));
        buttonRow.add(fontControl);

        host.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(host);
        add(buttonRow);
    }

    /**
     * The method remembers the thinking cluster.
     * The folded row may become its parent.
     *
     * // @aa-sdd PK-1099 docs/specs/common/formal-plan.spec.md
     *
     * @param cluster The cluster is the spinner and clock, or null.
     */
    void holdThink(JComponent cluster) {
        thinkCluster = cluster;
    }

    /**
     * The method places the thinking cluster to the right of the font control.
     * A missing cluster or a missing button row returns.
     * A cluster that is already on the button row returns.
     * The cluster font is clamped from {@code 8} to {@code 36}.
     */
    void parkThink() {
        if (thinkCluster == null || buttonRow == null) {
            return;
        }
        if (thinkCluster.getParent() == buttonRow) {
            return;
        }
        if (thinkCluster.getParent() != null) {
            thinkCluster.getParent().remove(thinkCluster);
        }
        buttonRow.add(thinkCluster);
        int size = getFontSize();
        if (size < 8) {
            size = 8;
        }
        if (size > 36) {
            size = 36;
        }
        seatBarFont(thinkCluster, new Font(Font.SANS_SERIF, Font.PLAIN, size));
        buttonRow.revalidate();
        buttonRow.repaint();
    }

    /**
     * The method builds the thin rule on the button row.
     * The font control follows Close Pane.
     * The rule is 2 pixels wide and 22 pixels tall.
     * The color is red {@code 138}, green {@code 138}, and blue {@code 138}.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-172
     *
     * @return The return value is that rule.
     */
    private JComponent barRule() {
        JPanel rule = new JPanel();
        rule.setOpaque(true);
        rule.setPreferredSize(new Dimension(2, 22));
        rule.setBackground(new Color(0x8a, 0x8a, 0x8a));
        return rule;
    }

    /**
     * The method builds a themed label for the host row.
     * The label shows the tool tip.
     *
     * @param text The text is the label word.
     * @param tip The tip is the tool tip for that label.
     * @return The return value is the label.
     */
    private JLabel flowLabel(String text, String tip) {
        JLabel label = new JLabel(text);
        theme.applyLabel(label);
        label.setToolTipText(tip);
        return label;
    }

    /**
     * The method wires the buttons, the focus-lost save, the mode confirm, and the font incrementor.
     * Connect and Reconnect save the fields before they call the listener.
     * Disconnect, Clear Terminal, Load File, New Term, and Close Pane call the listener without an extra save.
     * Close Pane starts disabled.
     * Leaving the host, the port, or the username editor saves the fields.
     * Pressing Enter in the host or the port saves the fields.
     * Choosing a remembered username fills its password and selects Remember password when a password is stored.
     * A chosen name that is not the live user calls {@link Listener#usernameChosen}.
     * A mode change asks for confirmation in {@link #handleModeSelection}.
     * A font change saves the size and calls {@link Listener#fontSizeChanged}.
     */
    private void wireEvents() {
        /**
         * The listener saves the connection fields and calls {@link Listener#connectRequested}.
         */
        connectButton.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                persistConnectionFields();
                listener.connectRequested();
            }
        });
        /**
         * The listener calls {@link Listener#disconnectRequested}.
         */
        disconnectButton.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.disconnectRequested();
            }
        });
        /**
         * The listener saves the connection fields and calls {@link Listener#reconnectRequested}.
         */
        reconnectButton.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                persistConnectionFields();
                listener.reconnectRequested();
            }
        });
        /**
         * The listener calls {@link Listener#clearTerminalRequested}.
         */
        clearButton.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.clearTerminalRequested();
            }
        });
        /**
         * The listener calls {@link Listener#sendFileRequested}.
         */
        sendFileButton.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.sendFileRequested();
            }
        });
        /**
         * The listener calls {@link Listener#newTermRequested}.
         */
        newTermButton.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.newTermRequested();
            }
        });
        /**
         * The listener calls {@link Listener#closePaneRequested}.
         */
        closePaneButton.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.closePaneRequested();
            }
        });
        closePaneButton.setEnabled(false);

        /**
         * The listener saves the connection fields when focus leaves the host, the port, or the username editor.
         */
        FocusAdapter persistOnFocusLost = new FocusAdapter() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void focusLost(FocusEvent e) {
                persistConnectionFields();
            }
        };
        hostField.addFocusListener(persistOnFocusLost);
        portField.addFocusListener(persistOnFocusLost);
        usernameCombo.getEditor().getEditorComponent().addFocusListener(persistOnFocusLost);
        /**
         * The listener saves the connection fields when Enter is pressed in the host field.
         */
        hostField.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                persistConnectionFields();
            }
        });
        /**
         * The listener saves the connection fields when Enter is pressed in the port field.
         */
        portField.addActionListener(new ActionListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void actionPerformed(ActionEvent e) {
                persistConnectionFields();
            }
        });
        /**
         * The listener reacts to a selected username.
         * A stored password for that name fills the password field and selects Remember password.
         * The fields are saved.
         * A name that is not the live user calls {@link Listener#usernameChosen}.
         */
        usernameCombo.addItemListener(new ItemListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ignoreUserEvent || e.getStateChange() != ItemEvent.SELECTED) {
                    return;
                }
                String user = getUsername();
                String stored = properties.passwordFor(user);
                if (stored.length() > 0) {
                    passwordField.setText(stored);
                    rememberPasswordCheck.setSelected(true);
                }
                persistConnectionFields();
                boolean other = liveUser.length() == 0 || !liveUser.equals(getUsername());
                armConnectForChoice();
                if (other) {
                    listener.usernameChosen();
                }
            }
        });

        /**
         * The listener saves the connection fields when Remember password changes.
         */
        rememberPasswordCheck.addItemListener(new ItemListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void itemStateChanged(ItemEvent e) {
                persistConnectionFields();
            }
        });

        ignoreModeEvent = true;
        modeCombo.setSelectedItem(properties.getMode());
        ignoreModeEvent = false;
        /**
         * The listener confirms a newly selected session mode.
         * A programmatic change is ignored.
         */
        modeCombo.addItemListener(new ItemListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionPanel
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ignoreModeEvent) {
                    return;
                }
                if (e.getStateChange() != ItemEvent.SELECTED) {
                    return;
                }
                handleModeSelection();
            }
        });

        /**
         * The minus listener subtracts one from the font size at once.
         */
        fontMinus.addActionListener(new ActionListener() {
            // @aa-sdd PK-776 docs/specs/common/stages.spec.md
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFontNow(fontSize - 1);
            }
        });
        /**
         * The plus listener adds one to the font size at once.
         */
        fontPlus.addActionListener(new ActionListener() {
            // @aa-sdd PK-776 docs/specs/common/stages.spec.md
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFontNow(fontSize + 1);
            }
        });
        /**
         * The document listener restarts the 800 millisecond wait after each edit.
         */
        fontField.getDocument().addDocumentListener(new DocumentListener() {
            // @aa-sdd PK-776 docs/specs/common/stages.spec.md
            @Override
            public void insertUpdate(DocumentEvent e) {
                scheduleFont();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                scheduleFont();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                scheduleFont();
            }
        });
    }

    /**
     * The method restarts the 800 millisecond font timer.
     * A field update that this class itself wrote does not restart the timer.
     */
    private void scheduleFont() {
        if (fontSuppress) {
            return;
        }
        fontTimer.restart();
    }

    /**
     * The method stores a font size at once and stops the timer.
     * A size below {@code 8} is stored as {@code 8}.
     * A size above {@code 36} is stored as {@code 36}.
     * A size equal to the current size only refreshes the field.
     * A different size updates the field, saves it, and reports it.
     *
     * @param next The next value is the size to apply.
     */
    private void applyFontNow(int next) {
        fontTimer.stop();
        int value = next;
        if (value < FONT_MIN) {
            value = FONT_MIN;
        }
        if (value > FONT_MAX) {
            value = FONT_MAX;
        }
        if (value == fontSize) {
            showFont();
            return;
        }
        fontSize = value;
        showFont();
        persistFontSize();
        listener.fontSizeChanged(fontSize);
    }

    /**
     * The method applies the text in the font field.
     * A null or blank field restores the current size and does not notify.
     * A value that is not an integer restores the current size and does not notify.
     * An integer is passed to {@link #applyFontNow}.
     */
    private void applyTypedFont() {
        String text = fontField.getText();
        if (text == null) {
            text = "";
        }
        text = text.trim();
        if (text.length() == 0) {
            showFont();
            return;
        }
        try {
            applyFontNow(Integer.parseInt(text));
        } catch (NumberFormatException ex) {
            showFont();
        }
    }

    /**
     * The method writes the current font size into the field.
     * A field that already shows that size is left alone.
     * The write does not restart the timer.
     */
    private void showFont() {
        String text = Integer.toString(fontSize);
        if (text.equals(fontField.getText())) {
            return;
        }
        fontSuppress = true;
        fontField.setText(text);
        fontSuppress = false;
    }

    /**
     * The method confirms a mode change before it is stored.
     * A selection that matches the stored mode returns.
     * The dialog says the session will disconnect and reconnect when a session is connected.
     * Cancel restores the stored mode without another event.
     * Yes stores the mode and calls {@link Listener#modeChangeRequested}.
     */
    private void handleModeSelection() {
        SshSessionMode selected = getMode();
        SshSessionMode previous = properties.getMode();
        if (selected == previous) {
            return;
        }
        int result = JOptionPane.showConfirmDialog(
                this,
                "Switch terminal mode to " + selected.getDisplayName() + "?\n"
                        + "If you are connected, the session will disconnect and reconnect.",
                "Change terminal mode",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result != JOptionPane.YES_OPTION) {
            ignoreModeEvent = true;
            modeCombo.setSelectedItem(previous);
            ignoreModeEvent = false;
            return;
        }
        persistMode();
        listener.modeChangeRequested(selected);
    }

    // @aa-sdd PK-1145 docs/specs/common/formal-plan.spec.md
    /**
     * The {@code FlowRow} is one wrapping row.
     * The height follows the width the bar actually has.
     */
    // @aa-sdd PK-243 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-1145 docs/specs/common/formal-plan.spec.md
    // @aa-sdd PK-200 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    private static final class FlowRow extends JPanel {

        private int laidWidth;

        /**
         * The constructor lays children from the left with an 8-pixel gap and a 2-pixel row gap.
         */
        private FlowRow() {
            super(new FlowLayout(FlowLayout.LEFT, 8, 2));
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        /**
         * The method returns the wrapped size for the current width.
         * A width that is not positive uses the parent width.
         * A width that is still not positive returns the ordinary preferred size.
         *
         * @return The return value is that size.
         */
        @Override
        public Dimension getPreferredSize() {
            int width = getWidth();
            if (width <= 0 && getParent() != null) {
                width = getParent().getWidth();
            }
            if (width <= 0) {
                return super.getPreferredSize();
            }
            return wrapped(width);
        }

        /**
         * The method returns an unlimited width and the preferred height.
         *
         * @return The return value is that maximum size.
         */
        @Override
        public Dimension getMaximumSize() {
            Dimension pref = getPreferredSize();
            return new Dimension(Integer.MAX_VALUE, pref.height);
        }

        /**
         * The method stores the bounds and asks for another layout when the width changes the wrapped height.
         * A width that is not positive returns after the bounds are stored.
         * An unchanged width returns after the bounds are stored.
         *
         * @param x The x value is the left edge.
         * @param y The y value is the top edge.
         * @param width The width is the row width in pixels.
         * @param height The height is the height the layout assigned.
         */
        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            if (width <= 0 || width == laidWidth) {
                return;
            }
            laidWidth = width;
            int need = wrapped(width).height;
            if (need != height) {
                revalidate();
            }
        }

        /**
         * The method measures this row when the children wrap inside the width.
         * Hidden children are skipped.
         * A child that does not fit the remaining width starts a new line.
         * The height adds the tallest child on each line, the vertical gap, and the insets.
         *
         * @param width The width is the bar width in pixels.
         * @return The return value is the wrapped size.
         */
        private Dimension wrapped(int width) {
            FlowLayout layout = (FlowLayout) getLayout();
            Insets insets = getInsets();
            int max = width - insets.left - insets.right;
            if (max < 1) {
                max = 1;
            }
            int x = 0;
            int rowH = 0;
            int h = insets.top;
            int used = 0;
            Component[] kids = getComponents();
            for (int i = 0; i < kids.length; i++) {
                Component kid = kids[i];
                if (!kid.isVisible()) {
                    continue;
                }
                Dimension pref = kid.getPreferredSize();
                int gap = x == 0 ? 0 : layout.getHgap();
                if (x > 0 && x + gap + pref.width > max) {
                    h = h + rowH + layout.getVgap();
                    x = 0;
                    rowH = 0;
                    gap = 0;
                }
                x = x + gap + pref.width;
                if (x > used) {
                    used = x;
                }
                if (pref.height > rowH) {
                    rowH = pref.height;
                }
            }
            h = h + rowH + insets.bottom;
            return new Dimension(used + insets.left + insets.right, h);
        }
    }
}
/*****************************************************************************************
 * End of Class: SshConnectionPanel.java 082426
 ****************************************************************************************/
