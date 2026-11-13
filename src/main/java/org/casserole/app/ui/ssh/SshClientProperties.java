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
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

// @aa-sdd GK-0881 aetheris-plan-f-114.001.md FINESSE:f-114
// @aa-sdd GK-0882 aetheris-plan-f-37.001.md FINESSE:f-37
// @aa-sdd GK-0883 aetheris-plan-f-62.001.md FINESSE:f-62
// @aa-sdd GK-0884 aetheris-stage-2-finessing.001.md FINESSE:f-7
// @aa-sdd GK-0885 aetheris-plan-f-91.001.md FINESSE:f-91
// @aa-sdd GK-0886 FEAT-002.inspector-line-chars.md
/**
 * The {@code SshClientProperties} store is the per-user SSH client file.
 * The file is {@code ssh-client.yaml} inside the {@code .casserole} folder under the user home directory.
 * The first load may import {@code .ssh-client.properties} from the user home directory once.
 * That import runs only when the YAML file is not already there.
 * The theme reads its colors from this store.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#SshClientProperties
 *
 * @author clayl (2026-08-24)
 */
// @aa-sdd GK-1204 aetheris-grok-api.draft.001.md
// @aa-sdd PK-172 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-174 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd GK-1291 aetheris-plan-stage-1-tweaks.005.md
// @aa-sdd PK-176 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-183 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-194 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd GK-2095 aetheris-pre-plan-f-37.001.md
// @aa-sdd PK-262 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-264 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-271 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-710 docs/specs/common/rust.spec.md
// @aa-sdd GK-2522 rust-aetheris-vt100-slice.draft.001.md
// @aa-sdd PK-276 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd GK-2583 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-2920 aetheris-plan-f-37.001.md
// @aa-sdd PK-226 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-677 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-681 docs/specs/common/formal-plan.spec.md
public class SshClientProperties {

    /**
     * The constant is the absolute path of the per-user YAML file.
     * The path joins the user home directory, {@code .casserole}, and {@code ssh-client.yaml}.
     */
    public static final String FILE_PATH =
            System.getProperty("user.home") + File.separator + ".casserole" + File.separator
                    + "ssh-client.yaml";

    public static final String LEGACY_FILE_PATH =
            System.getProperty("user.home") + File.separator + ".ssh-client.properties";

    public static final String UI_FRAME_X = "ui.frame.x";
    public static final String UI_FRAME_Y = "ui.frame.y";
    public static final String UI_FRAME_WIDTH = "ui.frame.width";
    public static final String UI_FRAME_HEIGHT = "ui.frame.height";
    public static final String UI_FRAME_MAXIMIZED = "ui.frame.maximized";
    public static final String UI_PRODUCT_TAB = "ui.product.tab";
    public static final String PRODUCT_TAB_SSH = "ssh";
    public static final String PRODUCT_TAB_AETHERIS = "aetheris";

    public static final String UI_CONNECTION_HOST = "ui.connection.host";
    public static final String UI_CONNECTION_PORT = "ui.connection.port";
    public static final String UI_CONNECTION_USERNAME = "ui.connection.username";
    public static final String UI_CONNECTION_PASSWORD = "ui.connection.password";
    public static final String UI_CONNECTION_REMEMBER_PASSWORD = "ui.connection.rememberPassword";
    public static final String UI_CONNECTION_MODE = "ui.connection.mode";
    public static final String UI_CONNECTION_IDLE_TIMEOUT_MS = "ui.connection.idle-timeout-ms";

    public static final String UI_TERMINAL_FONT_SIZE = "ui.terminal.font.size";

    public static final String UI_COLOR_FRAME_BACKGROUND = "ui.color.frame.background";
    public static final String UI_COLOR_PANEL_BACKGROUND = "ui.color.panel.background";
    public static final String UI_COLOR_PANEL_FOREGROUND = "ui.color.panel.foreground";
    public static final String UI_COLOR_FIELD_BACKGROUND = "ui.color.field.background";
    public static final String UI_COLOR_FIELD_FOREGROUND = "ui.color.field.foreground";
    public static final String UI_COLOR_BUTTON_BACKGROUND = "ui.color.button.background";
    public static final String UI_COLOR_BUTTON_FOREGROUND = "ui.color.button.foreground";
    public static final String UI_COLOR_STATUS_BACKGROUND = "ui.color.status.background";
    public static final String UI_COLOR_STATUS_FOREGROUND = "ui.color.status.foreground";
    public static final String UI_COLOR_SPLIT_BACKGROUND = "ui.color.split.background";

    public static final String UI_TERMINAL_COLOR_BACKGROUND = "ui.terminal.color.background";
    public static final String UI_TERMINAL_COLOR_FOREGROUND = "ui.terminal.color.foreground";
    public static final String UI_TERMINAL_COLOR_SELECTION_BACKGROUND = "ui.terminal.color.selection.background";
    public static final String UI_TERMINAL_COLOR_SELECTION_FOREGROUND = "ui.terminal.color.selection.foreground";
    /** // @aa-sdd G-25 */
    public static final String UI_TERMINAL_COLOR_CURSOR = "ui.terminal.color.cursor";
    public static final String UI_TERMINAL_COLOR_CURSOR_INACTIVE = "ui.terminal.color.cursor.inactive";
    public static final String UI_COLOR_MENU_FOREGROUND = "ui.color.menu.foreground";
    public static final String UI_PROMPT_CHAT_FOREGROUND = "ui.prompt.chat.foreground";
    public static final String UI_PROMPT_CMD_FOREGROUND = "ui.prompt.cmd.foreground";
    public static final String UI_AETHERIS_VIEW_ROWS = "ui.aetheris.view.rows";
    public static final String UI_AETHERIS_WRAP_COLUMNS = "ui.aetheris.inspector.wrap.columns";
    public static final String UI_AETHERIS_INSPECTOR_DIVIDER = "ui.aetheris.inspector.divider";
    public static final String UI_AETHERIS_INSPECTOR_LINE_CHARS = "ui.aetheris.inspector.line.chars";
    public static final String UI_AETHERIS_PIN_BAR_OPEN = "ui.aetheris.pin.bar.open";
    public static final String UI_CHOOSER_X = "ui.chooser.x";
    public static final String UI_CHOOSER_Y = "ui.chooser.y";
    public static final String UI_CHOOSER_WIDTH = "ui.chooser.width";
    public static final String UI_CHOOSER_HEIGHT = "ui.chooser.height";
    public static final String UI_CHOOSER_DIRECTORY = "ui.chooser.directory";

    public static final int DEFAULT_FRAME_WIDTH = 1024;
    public static final int DEFAULT_FRAME_HEIGHT = 968;
    public static final int DEFAULT_PORT = 22;
    /**
     * The constant is the font size used when the font key is missing or unreadable.
     * A size already stored in the file stays as written.
     */
    public static final int DEFAULT_FONT_SIZE = 20;

    public static final String DEFAULT_FRAME_BACKGROUND = "#0c0c0c";
    public static final String DEFAULT_PANEL_BACKGROUND = "#121212";
    public static final String DEFAULT_PANEL_FOREGROUND = "#dcdcdc";
    public static final String DEFAULT_FIELD_BACKGROUND = "#1a1a1a";
    public static final String DEFAULT_FIELD_FOREGROUND = "#e6e6e6";
    public static final String DEFAULT_BUTTON_BACKGROUND = "#3c3c3c";
    public static final String DEFAULT_BUTTON_FOREGROUND = "#f0f0f0";
    public static final String DEFAULT_STATUS_BACKGROUND = "#0c0c0c";
    public static final String DEFAULT_STATUS_FOREGROUND = "#b4b4b4";
    public static final String DEFAULT_SPLIT_BACKGROUND = "#0c0c0c";
    public static final String DEFAULT_TERMINAL_BACKGROUND = "#0d0d0d";
    public static final String DEFAULT_TERMINAL_FOREGROUND = "#dcdcdc";
    public static final String DEFAULT_SELECTION_BACKGROUND = "#345a8c";
    public static final String DEFAULT_SELECTION_FOREGROUND = "#ffffff";
    public static final String DEFAULT_CURSOR = "#9900d7ff";
    public static final String DEFAULT_CURSOR_INACTIVE = "#664080a0";
    public static final String DEFAULT_MENU_FOREGROUND = "#00ff41";
    public static final String DEFAULT_PROMPT_CHAT = "#00c853";
    public static final String DEFAULT_PROMPT_CMD = "#2196f3";

    private final Properties properties;
    private final File file;
    private final List<SavedAccount> accounts;

    /**
     * The constructor creates an empty store pointed at the per-user YAML file.
     * No accounts are stored yet.
     */
    public SshClientProperties() {
        this.properties = new Properties();
        this.file = new File(FILE_PATH);
        this.accounts = new ArrayList<SavedAccount>();
    }

    /**
     * The method loads the store from disk and fills any missing known keys.
     * A missing file is not an error.
     * The returned store is never null.
     *
     * @return The return value is the loaded store.
     */
    public static SshClientProperties load() {
        SshClientProperties store = new SshClientProperties();
        store.readFromDisk();
        store.applyDefaultsForMissingKeys();
        return store;
    }

    /**
     * The method reads the YAML file when that file exists.
     * A missing YAML file is not an error.
     * A missing YAML file may still import the legacy properties file.
     * The legacy file is {@code .ssh-client.properties} in the user home directory.
     * A legacy username that is not empty is stored as an account.
     * That account keeps the legacy password and the legacy remember flag.
     * A read error prints {@code Unable to read } plus the path and the exception message.
     */
    public void readFromDisk() {
        if (file.exists()) {
            readYaml(file);
            return;
        }
        File legacy = new File(LEGACY_FILE_PATH);
        if (legacy.exists()) {
            Reader reader = null;
            try {
                reader = new InputStreamReader(new FileInputStream(legacy), StandardCharsets.UTF_8);
                properties.load(reader);
                String user = getUsername();
                if (user.length() > 0) {
                    upsertAccount(user, getPassword(), isRememberPassword());
                }
            } catch (IOException ex) {
                System.err.println("Unable to read " + legacy.getAbsolutePath() + ": " + ex.getMessage());
            } finally {
                closeReader(reader);
            }
        }
    }

    /**
     * The method writes the current store to the YAML file as UTF-8.
     * A missing parent folder is created.
     * Property keys are written in sorted order.
     * Each account is written as a username and a password.
     * A null account password is written as an empty string.
     * The file begins with the line {@code # Casserole standalone SSH client}.
     * A write error prints {@code Unable to write } plus the path and the exception message.
     */
    public synchronized void save() {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        LinkedHashMap<String, Object> root = new LinkedHashMap<String, Object>();
        ArrayList<String> keys = new ArrayList<String>();
        for (Object keyObj : properties.keySet()) {
            keys.add(String.valueOf(keyObj));
        }
        java.util.Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            root.put(key, properties.getProperty(key));
        }
        ArrayList<LinkedHashMap<String, String>> acc = new ArrayList<LinkedHashMap<String, String>>();
        for (int i = 0; i < accounts.size(); i++) {
            SavedAccount row = accounts.get(i);
            LinkedHashMap<String, String> m = new LinkedHashMap<String, String>();
            m.put("username", row.username);
            m.put("password", row.password == null ? "" : row.password);
            acc.add(m);
        }
        root.put("accounts", acc);
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write("# Casserole standalone SSH client\n");
            yaml.dump(root, writer);
        } catch (IOException ex) {
            System.err.println("Unable to write " + file.getAbsolutePath() + ": " + ex.getMessage());
        } finally {
            closeWriter(writer);
        }
    }

    /**
     * The method fills missing keys with the version 1 defaults.
     * A key that is already present is left as stored.
     * The frame size defaults are 1024 by 968.
     * The product tab defaults to {@code ssh}.
     * The host defaults to {@code localhost}.
     * The port defaults to {@code 22}.
     * The username defaults to the {@code user.name} property, or empty when that property is missing.
     * Remember-password defaults to false.
     * The mode defaults to the VT100 property value.
     * The idle timeout defaults to {@code 0}, which does not disconnect for idle time.
     * The font size defaults to {@code 20}.
     * Each color key defaults to its {@code DEFAULT_} hex constant.
     */
    public void applyDefaultsForMissingKeys() {
        putIfAbsent(UI_FRAME_WIDTH, Integer.toString(DEFAULT_FRAME_WIDTH));
        putIfAbsent(UI_FRAME_HEIGHT, Integer.toString(DEFAULT_FRAME_HEIGHT));
        putIfAbsent(UI_PRODUCT_TAB, PRODUCT_TAB_SSH);
        putIfAbsent(UI_CONNECTION_HOST, "localhost");
        putIfAbsent(UI_CONNECTION_PORT, Integer.toString(DEFAULT_PORT));
        putIfAbsent(UI_CONNECTION_USERNAME, System.getProperty("user.name", ""));
        putIfAbsent(UI_CONNECTION_REMEMBER_PASSWORD, "false");
        putIfAbsent(UI_CONNECTION_MODE, SshSessionMode.VT100.getPropertyValue());
        putIfAbsent(UI_CONNECTION_IDLE_TIMEOUT_MS, "0");
        putIfAbsent(UI_TERMINAL_FONT_SIZE, Integer.toString(DEFAULT_FONT_SIZE));
        putIfAbsent(UI_COLOR_FRAME_BACKGROUND, DEFAULT_FRAME_BACKGROUND);
        putIfAbsent(UI_COLOR_PANEL_BACKGROUND, DEFAULT_PANEL_BACKGROUND);
        putIfAbsent(UI_COLOR_PANEL_FOREGROUND, DEFAULT_PANEL_FOREGROUND);
        putIfAbsent(UI_COLOR_FIELD_BACKGROUND, DEFAULT_FIELD_BACKGROUND);
        putIfAbsent(UI_COLOR_FIELD_FOREGROUND, DEFAULT_FIELD_FOREGROUND);
        putIfAbsent(UI_COLOR_BUTTON_BACKGROUND, DEFAULT_BUTTON_BACKGROUND);
        putIfAbsent(UI_COLOR_BUTTON_FOREGROUND, DEFAULT_BUTTON_FOREGROUND);
        putIfAbsent(UI_COLOR_STATUS_BACKGROUND, DEFAULT_STATUS_BACKGROUND);
        putIfAbsent(UI_COLOR_STATUS_FOREGROUND, DEFAULT_STATUS_FOREGROUND);
        putIfAbsent(UI_COLOR_SPLIT_BACKGROUND, DEFAULT_SPLIT_BACKGROUND);
        putIfAbsent(UI_TERMINAL_COLOR_BACKGROUND, DEFAULT_TERMINAL_BACKGROUND);
        putIfAbsent(UI_TERMINAL_COLOR_FOREGROUND, DEFAULT_TERMINAL_FOREGROUND);
        putIfAbsent(UI_TERMINAL_COLOR_SELECTION_BACKGROUND, DEFAULT_SELECTION_BACKGROUND);
        putIfAbsent(UI_TERMINAL_COLOR_SELECTION_FOREGROUND, DEFAULT_SELECTION_FOREGROUND);
        putIfAbsent(UI_TERMINAL_COLOR_CURSOR, DEFAULT_CURSOR);
        putIfAbsent(UI_TERMINAL_COLOR_CURSOR_INACTIVE, DEFAULT_CURSOR_INACTIVE);
        putIfAbsent(UI_COLOR_MENU_FOREGROUND, DEFAULT_MENU_FOREGROUND);
        putIfAbsent(UI_PROMPT_CHAT_FOREGROUND, DEFAULT_PROMPT_CHAT);
        putIfAbsent(UI_PROMPT_CMD_FOREGROUND, DEFAULT_PROMPT_CMD);
    }

    /**
     * The method writes the defaults the first time the YAML file is created.
     * An existing file is not rewritten.
     * The colors are then present for a person to edit.
     */
    public void saveIfFileMissing() {
        if (!file.exists()) {
            save();
        }
    }

    /**
     * The method returns the stored remote server address.
     * A missing key returns {@code localhost}.
     *
     * @return The return value is that address.
     */
    public String getHost() {
        return getString(UI_CONNECTION_HOST, "localhost");
    }

    /**
     * The method stores the remote server address.
     * A null value is stored as empty.
     * The stored text is trimmed.
     *
     * @param host The host is the remote server address.
     */
    public void setHost(String host) {
        setString(UI_CONNECTION_HOST, host == null ? "" : host.trim());
    }

    /**
     * The method returns the stored SSH port.
     * A missing or unreadable key returns {@code 22}.
     *
     * @return The return value is that port.
     */
    public int getPort() {
        return getInt(UI_CONNECTION_PORT, DEFAULT_PORT);
    }

    /**
     * The method stores the SSH port as text.
     * The method does not clamp the number.
     *
     * @param port The port is the SSH port to store.
     */
    public void setPort(int port) {
        setString(UI_CONNECTION_PORT, Integer.toString(port));
    }

    /**
     * The method returns the stored SSH username.
     * A missing key returns {@code user.name}, or empty when that property is missing.
     *
     * @return The return value is that username.
     */
    public String getUsername() {
        return getString(UI_CONNECTION_USERNAME, System.getProperty("user.name", ""));
    }

    /**
     * The method stores the SSH username.
     * A null value is stored as empty.
     * The stored text is trimmed.
     *
     * @param username The username is the SSH account name.
     */
    public void setUsername(String username) {
        setString(UI_CONNECTION_USERNAME, username == null ? "" : username.trim());
    }

    /**
     * The method returns the remembered account names in stored order.
     *
     * @return The return value is a new array of those names.
     */
    public String[] getAccountUsernames() {
        String[] names = new String[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) {
            names[i] = accounts.get(i).username;
        }
        return names;
    }

    /**
     * The method returns the password stored for one account.
     * A null username returns an empty string.
     * A missing account returns an empty string.
     * A null password on a matching account returns an empty string.
     *
     * @param username The username is the account name to find.
     * @return The return value is the stored password, or empty.
     */
    public String passwordFor(String username) {
        if (username == null) {
            return "";
        }
        for (int i = 0; i < accounts.size(); i++) {
            SavedAccount row = accounts.get(i);
            if (username.equals(row.username)) {
                return row.password == null ? "" : row.password;
            }
        }
        return "";
    }

    /**
     * The method adds or updates one remembered account.
     * A null or blank username returns without a change.
     * The stored name is trimmed.
     * The password is stored only when remember is true and the password is not null.
     * Otherwise the stored password is empty.
     * An existing name has its password replaced.
     * A new name is appended.
     *
     * @param username The username is the account name.
     * @param password The password is the secret to store when remember is true.
     * @param remember The flag is true when the password should be kept.
     */
    public void upsertAccount(String username, String password, boolean remember) {
        if (username == null || username.trim().length() == 0) {
            return;
        }
        String name = username.trim();
        String secret = remember && password != null ? password : "";
        for (int i = 0; i < accounts.size(); i++) {
            if (name.equals(accounts.get(i).username)) {
                accounts.get(i).password = secret;
                return;
            }
        }
        SavedAccount row = new SavedAccount();
        row.username = name;
        row.password = secret;
        accounts.add(row);
    }

    /**
     * The method returns the password stored on the connection key.
     * A missing key returns an empty string.
     *
     * @return The return value is that password, or empty.
     */
    public String getPassword() {
        return getString(UI_CONNECTION_PASSWORD, "");
    }

    /**
     * The method stores or removes the connection password.
     * A non-empty password is stored when remember is true.
     * Any other case removes the password key.
     * The remember flag is stored after that choice.
     *
     * @param password The password is the raw password.
     * @param remember The flag is true when the password should be kept.
     */
    public void setPassword(String password, boolean remember) {
        if (remember && password != null && password.length() > 0) {
            setString(UI_CONNECTION_PASSWORD, password);
        } else {
            properties.remove(UI_CONNECTION_PASSWORD);
        }
        setRememberPassword(remember);
    }

    /**
     * The method reports whether the password should be kept.
     * A missing key returns false.
     *
     * @return The return value is the stored remember flag.
     */
    public boolean isRememberPassword() {
        return getBoolean(UI_CONNECTION_REMEMBER_PASSWORD, false);
    }

    /**
     * The method stores the remember-password flag.
     * A false flag also removes the connection password.
     *
     * @param remember The flag is true when the password should be kept.
     */
    public void setRememberPassword(boolean remember) {
        setString(UI_CONNECTION_REMEMBER_PASSWORD, Boolean.toString(remember));
        if (!remember) {
            properties.remove(UI_CONNECTION_PASSWORD);
        }
    }

    /**
     * The method returns the last session mode.
     * A missing or unknown token returns {@link SshSessionMode#VT100}.
     *
     * @return The return value is that mode.
     */
    public SshSessionMode getMode() {
        return SshSessionMode.fromPropertyValue(getString(UI_CONNECTION_MODE, SshSessionMode.VT100.getPropertyValue()));
    }

    /**
     * The method stores the session mode token.
     * A null mode is stored as VT100.
     *
     * @param mode The mode is the session mode to store.
     */
    public void setMode(SshSessionMode mode) {
        SshSessionMode value = mode == null ? SshSessionMode.VT100 : mode;
        setString(UI_CONNECTION_MODE, value.getPropertyValue());
    }

    /**
     * The method returns the client idle timeout in milliseconds.
     * A missing key returns {@code 0}.
     * A value of {@code 0} means the client does not disconnect for idle time.
     *
     * @return The return value is that timeout.
     */
    public long getIdleTimeoutMs() {
        return getInt(UI_CONNECTION_IDLE_TIMEOUT_MS, 0);
    }

    /**
     * The method stores the idle timeout in milliseconds.
     * A negative value is stored as {@code 0}.
     * {@code 0} disables idle disconnect.
     *
     * @param idleTimeoutMs The idle timeout is the limit in milliseconds.
     */
    public void setIdleTimeoutMs(long idleTimeoutMs) {
        long value = idleTimeoutMs < 0L ? 0L : idleTimeoutMs;
        setString(UI_CONNECTION_IDLE_TIMEOUT_MS, Long.toString(value));
    }

    /**
     * The method returns the last frame width in pixels.
     * A missing or unreadable key returns {@code 1024}.
     *
     * @return The return value is that width.
     */
    public int getFrameWidth() {
        return getInt(UI_FRAME_WIDTH, DEFAULT_FRAME_WIDTH);
    }

    /**
     * The method stores the frame width in pixels.
     *
     * @param width The width is the frame width to store.
     */
    public void setFrameWidth(int width) {
        setString(UI_FRAME_WIDTH, Integer.toString(width));
    }

    /**
     * The method returns the last frame height in pixels.
     * A missing or unreadable key returns {@code 968}.
     *
     * @return The return value is that height.
     */
    public int getFrameHeight() {
        return getInt(UI_FRAME_HEIGHT, DEFAULT_FRAME_HEIGHT);
    }

    /**
     * The method stores the frame height in pixels.
     *
     * @param height The height is the frame height to store.
     */
    public void setFrameHeight(int height) {
        setString(UI_FRAME_HEIGHT, Integer.toString(height));
    }

    /**
     * The method returns the saved frame rectangle.
     * A missing origin key returns null.
     * A width or height below {@code 1} returns null.
     *
     * @return The return value is the rectangle, or null when the origin was never stored.
     */
    public Rectangle getFrameBounds() {
        if (!properties.containsKey(UI_FRAME_X) || !properties.containsKey(UI_FRAME_Y)) {
            return null;
        }
        int x = getInt(UI_FRAME_X, 0);
        int y = getInt(UI_FRAME_Y, 0);
        int width = getFrameWidth();
        int height = getFrameHeight();
        if (width < 1 || height < 1) {
            return null;
        }
        return new Rectangle(x, y, width, height);
    }

    /**
     * The method stores the frame origin.
     * The width and the height are left as they are.
     *
     * @param x The x value is the left edge in pixels.
     * @param y The y value is the top edge in pixels.
     */
    public void setFrameOrigin(int x, int y) {
        setString(UI_FRAME_X, Integer.toString(x));
        setString(UI_FRAME_Y, Integer.toString(y));
    }

    /**
     * The method reports whether the shell window was maximized.
     * A missing key returns false.
     *
     * @return The return value is that flag.
     */
    public boolean isFrameMaximized() {
        return getBoolean(UI_FRAME_MAXIMIZED, false);
    }

    /**
     * The method stores the maximized flag.
     *
     * @param maximized The flag is true when the frame was maximized.
     */
    public void setFrameMaximized(boolean maximized) {
        setString(UI_FRAME_MAXIMIZED, Boolean.toString(maximized));
    }

    /**
     * The method returns the terminal font size in points.
     * A missing or unreadable key returns {@code 20}.
     * A stored size is returned as written.
     *
     * @return The return value is that point size.
     */
    public int getFontSize() {
        return getInt(UI_TERMINAL_FONT_SIZE, DEFAULT_FONT_SIZE);
    }

    /**
     * The method stores the terminal font size in points.
     * The method does not clamp the number.
     *
     * @param size The size is the point size to store.
     */
    public void setFontSize(int size) {
        setString(UI_TERMINAL_FONT_SIZE, Integer.toString(size));
    }

    /**
     * The method parses a color property.
     * An unreadable stored value falls back to {@code defaultHex}.
     * An unreadable fallback returns {@link java.awt.Color#DARK_GRAY}.
     * The result is never null.
     *
     * @param key The key is the property name.
     * @param defaultHex The default hex is the {@code #rrggbb} color used when the key is missing or unreadable.
     * @return The return value is the AWT color.
     */
    public Color getColor(String key, String defaultHex) {
        String raw = getString(key, defaultHex);
        Color parsed = parseHexColor(raw);
        if (parsed == null) {
            parsed = parseHexColor(defaultHex);
        }
        if (parsed == null) {
            return Color.DARK_GRAY;
        }
        return parsed;
    }

    /**
     * The method stores a color as {@code #rrggbb}.
     * A null color returns without a write.
     * The alpha channel is not stored.
     *
     * @param key The key is the property name.
     * @param color The color is the AWT color to store.
     */
    public void setColor(String key, Color color) {
        if (color == null) {
            return;
        }
        setString(key, toHex(color));
    }

    /**
     * The method returns a stored string.
     * A missing key returns the fallback.
     * An empty stored string is returned as empty.
     *
     * @param key The key is the property name.
     * @param defaultValue The default value is the fallback.
     * @return The return value is the stored string or the fallback.
     */
    public String getString(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * The method stores a string property.
     * A null value removes the key.
     *
     * @param key The key is the property name.
     * @param value The value is the text to store.
     */
    public void setString(String key, String value) {
        if (value == null) {
            properties.remove(key);
        } else {
            properties.setProperty(key, value);
        }
    }

    /**
     * The method returns the YAML file this store writes.
     *
     * @return The return value is that file.
     */
    public File getFile() {
        return file;
    }

    /**
     * The method reads one YAML file into the property table.
     * A root that is not a map returns without a change.
     * The {@code accounts} entry is passed to {@link #loadAccounts}.
     * Every other non-null value is stored as text.
     * A read error prints {@code Unable to read } plus the path and the exception message.
     *
     * @param yamlFile The file is the YAML store to read.
     */
    private void readYaml(File yamlFile) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(yamlFile), StandardCharsets.UTF_8);
            Yaml yaml = new Yaml();
            Object root = yaml.load(reader);
            if (!(root instanceof Map)) {
                return;
            }
            @SuppressWarnings("unchecked")
            Map<Object, Object> map = (Map<Object, Object>) root;
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                String key = String.valueOf(entry.getKey());
                if ("accounts".equals(key)) {
                    loadAccounts(entry.getValue());
                    continue;
                }
                if (entry.getValue() != null) {
                    properties.setProperty(key, String.valueOf(entry.getValue()));
                }
            }
        } catch (IOException ex) {
            System.err.println("Unable to read " + yamlFile.getAbsolutePath() + ": " + ex.getMessage());
        } finally {
            closeReader(reader);
        }
    }

    /**
     * The method replaces the account list from the YAML {@code accounts} value.
     * A value that is not a list clears the list and returns.
     * A row that is not a map is skipped.
     * A blank username is skipped.
     * A null password is stored as empty.
     *
     * @param raw The raw value is the YAML accounts node.
     */
    private void loadAccounts(Object raw) {
        accounts.clear();
        if (!(raw instanceof List)) {
            return;
        }
        @SuppressWarnings("unchecked")
        List<Object> rows = (List<Object>) raw;
        for (int i = 0; i < rows.size(); i++) {
            Object rowObj = rows.get(i);
            if (!(rowObj instanceof Map)) {
                continue;
            }
            @SuppressWarnings("unchecked")
            Map<Object, Object> row = (Map<Object, Object>) rowObj;
            String name = row.get("username") == null ? "" : String.valueOf(row.get("username")).trim();
            String password = row.get("password") == null ? "" : String.valueOf(row.get("password"));
            if (name.length() == 0) {
                continue;
            }
            SavedAccount acc = new SavedAccount();
            acc.username = name;
            acc.password = password;
            accounts.add(acc);
        }
    }

    /**
     * The method parses {@code #rrggbb} or {@code rrggbb}.
     * A null or blank string returns null.
     * A missing {@code #} is added before decoding.
     * A nine-character value is read as {@code #aarrggbb}.
     * Any other length is passed to {@code Color.decode}.
     * A number that cannot be parsed returns null.
     *
     * @param raw The raw text is the hex string.
     * @return The return value is the color, or null when the text cannot be parsed.
     */
    public static Color parseHexColor(String raw) {
        if (raw == null) {
            return null;
        }
        String text = raw.trim();
        if (text.length() == 0) {
            return null;
        }
        if (text.charAt(0) != '#') {
            text = "#" + text;
        }
        try {
            if (text.length() == 9) {
                int a = Integer.parseInt(text.substring(1, 3), 16);
                int r = Integer.parseInt(text.substring(3, 5), 16);
                int g = Integer.parseInt(text.substring(5, 7), 16);
                int b = Integer.parseInt(text.substring(7, 9), 16);
                return new Color(r, g, b, a);
            }
            return Color.decode(text);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * The method formats a color as {@code #rrggbb}.
     * The letters are lower case.
     * The alpha channel is not included.
     *
     * @param color The color is the AWT color to format.
     * @return The return value is that hex text.
     */
    public static String toHex(Color color) {
        return String.format("#%02x%02x%02x",
                Integer.valueOf(color.getRed()),
                Integer.valueOf(color.getGreen()),
                Integer.valueOf(color.getBlue()));
    }

    /**
     * The method stores the default only when the key is missing.
     * A key that is already present is left as stored.
     *
     * @param key The key is the property name.
     * @param value The value is the default text.
     */
    private void putIfAbsent(String key, String value) {
        if (!properties.containsKey(key)) {
            properties.setProperty(key, value);
        }
    }

    /**
     * The method returns the open-card row count.
     * A missing key uses {@code 4}.
     * A value below {@code 1} returns {@code 1}.
     *
     * @return The return value is that row count.
     */
    public int getAetherisViewRows() {
        int rows = getInt(UI_AETHERIS_VIEW_ROWS, 4);
        if (rows < 1) {
            return 1;
        }
        return rows;
    }

    /**
     * The method stores the open-card row count and writes the file.
     * A value below {@code 1} is stored as {@code 1}.
     *
     * @param rows The rows value is the open-card row count.
     */
    public void setAetherisViewRows(int rows) {
        int value = rows < 1 ? 1 : rows;
        setString(UI_AETHERIS_VIEW_ROWS, Integer.toString(value));
        save();
    }

    /**
     * The method returns the stored product tab.
     * The word {@code aetheris} is returned only when that exact word is stored.
     * Every other value returns {@code ssh}.
     *
     * @return The return value is {@code aetheris} or {@code ssh}.
     */
    public String getProductTab() {
        String word = getString(UI_PRODUCT_TAB, PRODUCT_TAB_SSH);
        if (PRODUCT_TAB_AETHERIS.equals(word)) {
            return PRODUCT_TAB_AETHERIS;
        }
        return PRODUCT_TAB_SSH;
    }

    /**
     * The method stores {@code ssh} or {@code aetheris} and writes the file.
     * Only the exact word {@code aetheris} is stored as {@code aetheris}.
     * Every other value is stored as {@code ssh}.
     *
     * @param tab The tab is the product-tab word.
     */
    public void setProductTab(String tab) {
        String word = PRODUCT_TAB_AETHERIS.equals(tab) ? PRODUCT_TAB_AETHERIS : PRODUCT_TAB_SSH;
        setString(UI_PRODUCT_TAB, word);
        save();
    }

    /**
     * The method returns the inspector wrap width in characters.
     * A missing key uses {@code 80}.
     * A value below {@code 1} returns {@code 1}.
     *
     * @return The return value is that character count.
     */
    public int getAetherisWrapColumns() {
        int columns = getInt(UI_AETHERIS_WRAP_COLUMNS, 80);
        if (columns < 1) {
            return 1;
        }
        return columns;
    }

    /**
     * The method stores the inspector wrap width and writes the file.
     * A value below {@code 1} is stored as {@code 1}.
     *
     * @param columns The columns value is the wrap width in characters.
     */
    public void setAetherisWrapColumns(int columns) {
        int value = columns < 1 ? 1 : columns;
        setString(UI_AETHERIS_WRAP_COLUMNS, Integer.toString(value));
        save();
    }

    /**
     * The method returns the canvas and inspector divider as per mille of the span.
     * A missing key returns {@code -1}.
     * A stored value below {@code 0} returns {@code -1}.
     * A stored value above {@code 1000} returns {@code 1000}.
     *
     * @return The return value is that per-mille share, or {@code -1} when the key is absent.
     */
    public int getAetherisInspectorDivider() {
        if (!properties.containsKey(UI_AETHERIS_INSPECTOR_DIVIDER)) {
            return -1;
        }
        int value = getInt(UI_AETHERIS_INSPECTOR_DIVIDER, -1);
        if (value < 0) {
            return -1;
        }
        if (value > 1000) {
            return 1000;
        }
        return value;
    }

    /**
     * The method stores the divider share and writes the file.
     * A value below {@code 0} is stored as {@code 0}.
     * A value above {@code 1000} is stored as {@code 1000}.
     *
     * @param perMille The per-mille value is the left share of the span, from 0 through 1000.
     */
    public void setAetherisInspectorDivider(int perMille) {
        int value = perMille;
        if (value < 0) {
            value = 0;
        }
        if (value > 1000) {
            value = 1000;
        }
        setString(UI_AETHERIS_INSPECTOR_DIVIDER, Integer.toString(value));
        save();
    }

    /**
     * The method returns the inspector body width in characters.
     * A missing key uses {@code 80}.
     * A value below {@code 1} returns {@code 1}.
     *
     * @return The return value is that character count.
     */
    public int getAetherisInspectorLineChars() {
        int value = getInt(UI_AETHERIS_INSPECTOR_LINE_CHARS, 80);
        if (value < 1) {
            return 1;
        }
        return value;
    }

    /**
     * The method stores the inspector line width and writes the file.
     * A value below {@code 1} is stored as {@code 1}.
     *
     * @param count The count is the number of body characters shown on one inspector line.
     */
    public void setAetherisInspectorLineChars(int count) {
        int value = count < 1 ? 1 : count;
        setString(UI_AETHERIS_INSPECTOR_LINE_CHARS, Integer.toString(value));
        save();
    }

    /**
     * The method reports whether the pin bar is open.
     * A missing key means the bar is folded and returns false.
     *
     * @return The return value is true when the pin controls are showing.
     */
    public boolean getAetherisPinBarOpen() {
        if (!properties.containsKey(UI_AETHERIS_PIN_BAR_OPEN)) {
            return false;
        }
        return getBoolean(UI_AETHERIS_PIN_BAR_OPEN, false);
    }

    /**
     * The method stores the pin-bar flag as {@code true} or {@code false} and writes the file.
     *
     * @param open The flag is true when the pin controls are showing.
     */
    public void setAetherisPinBarOpen(boolean open) {
        setString(UI_AETHERIS_PIN_BAR_OPEN, open ? "true" : "false");
        save();
    }

    /**
     * The method returns the saved file-chooser rectangle.
     * A missing width key or height key returns null.
     * A width or height below {@code 1} returns null.
     * A missing origin uses {@code 0}.
     *
     * @return The return value is the rectangle, or null when the width key is absent.
     */
    public Rectangle getChooserBounds() {
        if (!properties.containsKey(UI_CHOOSER_WIDTH) || !properties.containsKey(UI_CHOOSER_HEIGHT)) {
            return null;
        }
        int width = getInt(UI_CHOOSER_WIDTH, -1);
        int height = getInt(UI_CHOOSER_HEIGHT, -1);
        int x = getInt(UI_CHOOSER_X, 0);
        int y = getInt(UI_CHOOSER_Y, 0);
        if (width < 1 || height < 1) {
            return null;
        }
        return new Rectangle(x, y, width, height);
    }

    /**
     * The method stores the chooser bounds and writes the file.
     * A null rectangle returns without a write.
     *
     * @param bounds The bounds are the dialog rectangle to keep.
     */
    public void setChooserBounds(Rectangle bounds) {
        if (bounds == null) {
            return;
        }
        setString(UI_CHOOSER_X, Integer.toString(bounds.x));
        setString(UI_CHOOSER_Y, Integer.toString(bounds.y));
        setString(UI_CHOOSER_WIDTH, Integer.toString(bounds.width));
        setString(UI_CHOOSER_HEIGHT, Integer.toString(bounds.height));
        save();
    }

    /**
     * The method returns the last approved chooser folder.
     * A missing key returns an empty string.
     *
     * @return The return value is that folder, or empty.
     */
    public String getChooserDirectory() {
        String dir = getString(UI_CHOOSER_DIRECTORY, "");
        if (dir == null) {
            return "";
        }
        return dir;
    }

    /**
     * The method stores the folder the chooser should open next time and writes the file.
     * A null or empty directory returns without a write.
     *
     * @param directory The directory is that folder.
     */
    public void setChooserDirectory(String directory) {
        if (directory == null || directory.length() == 0) {
            return;
        }
        setString(UI_CHOOSER_DIRECTORY, directory);
        save();
    }

    /**
     * The method parses an integer property.
     * A missing key returns the fallback.
     * Text that is not an integer returns the fallback.
     *
     * @param key The key is the property name.
     * @param defaultValue The default value is the fallback.
     * @return The return value is the parsed integer or the fallback.
     */
    private int getInt(String key, int defaultValue) {
        String raw = properties.getProperty(key);
        if (raw == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    /**
     * The method parses a boolean property.
     * A missing key returns the fallback.
     * The parse uses {@code Boolean.parseBoolean} on the trimmed text.
     *
     * @param key The key is the property name.
     * @param defaultValue The default value is the fallback.
     * @return The return value is the parsed boolean or the fallback.
     */
    private boolean getBoolean(String key, boolean defaultValue) {
        String raw = properties.getProperty(key);
        if (raw == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(raw.trim());
    }

    /**
     * The method closes a reader and ignores a close error.
     * A null reader returns at once.
     *
     * @param reader The reader is the reader to close.
     */
    private void closeReader(Reader reader) {
        if (reader == null) {
            return;
        }
        try {
            reader.close();
        } catch (IOException ignored) {
            // ignore
        }
    }

    /**
     * The method closes a writer and ignores a close error.
     * A null writer returns at once.
     *
     * @param writer The writer is the writer to close.
     */
    private void closeWriter(Writer writer) {
        if (writer == null) {
            return;
        }
        try {
            writer.close();
        } catch (IOException ignored) {
            // ignore
        }
    }

    // @aa-sdd L40 aa-sdd-source-prose.md#SshClientProperties
    /**
     * The {@code SavedAccount} is one remembered SSH account.
     * The account holds a username and a password.
     * An empty password means the password was not kept.
     */
    public static class SavedAccount {
        private String username = "";
        private String password = "";
    }
}
/*****************************************************************************************
 * End of Class: SshClientProperties.java 082626
 ****************************************************************************************/
