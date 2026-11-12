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

/**
 * The {@code SshSessionMode} type names the remote terminal request for one SSH session.
 * The name SSH means the secure shell this client opens.
 * The {@link #VT100} constant names the remote type {@code vt100}.
 * The {@link #PTY} constant names the remote type {@code xterm-256color}.
 * The pseudoterminal is the remote terminal the server allocates for the shell.
 * The name JediTerm means the terminal widget that draws the session on this computer.
 * The JediTerm widget stays the local emulator.
 * The type does not change that widget.
 *
 * @author clayl (2026-08-24)
 */
public enum SshSessionMode {

    /**
     * The {@code VT100} constant is the default remote terminal request.
     * The stored token is {@code vt100}.
     * The label in the mode combo, the control that lists these modes, is {@code VT100}.
     * The remote terminal type is {@code vt100}.
     */
    VT100("vt100", "VT100"),

    /**
     * The {@code PTY} constant is the standard remote terminal request.
     * The name PTY means a pseudoterminal, the remote terminal the server allocates.
     * The stored token is {@code pty}.
     * The label in the mode combo is {@code Standard PTY}.
     * The remote terminal type is {@code xterm-256color}.
     */
    PTY("pty", "Standard PTY");

    private final String propertyValue;
    private final String displayName;

    /**
     * The constructor stores the property token and the combo label.
     * The mode combo is the control that lists these modes.
     *
     * @param propertyValue The token written in the properties file.
     * @param displayName The label shown in the mode combo.
     */
    SshSessionMode(String propertyValue, String displayName) {
        this.propertyValue = propertyValue;
        this.displayName = displayName;
    }

    /**
     * The getPropertyValue method returns the token stored for this mode.
     * The token is the value under {@link SshClientProperties#UI_CONNECTION_MODE}.
     *
     * @return The return value is {@code vt100} or {@code pty}.
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * The getDisplayName method returns the label shown in the mode combo.
     * The mode combo is the control that lists these modes.
     *
     * @return The return value is {@code VT100} or {@code Standard PTY}.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * The getTermType method returns the terminal type requested of the server.
     * The value is the TERM string on the remote pseudoterminal, the terminal the server allocates.
     * The method returns {@code xterm-256color} when this mode is {@link #PTY}.
     * The method returns {@code vt100} for every other mode.
     *
     * @return The return value is {@code vt100} or {@code xterm-256color}.
     */
    public String getTermType() {
        if (this == PTY) {
            return "xterm-256color";
        }
        return "vt100";
    }

    /**
     * The fromPropertyValue method reads a stored token back into a mode.
     * The comparison ignores letter case.
     * The method returns {@link #VT100} when the token is null.
     * The method returns {@link #VT100} when the token matches no mode.
     *
     * @param value The token from the properties file. The value may be null.
     * @return The return value is the matching mode. The value is never null.
     */
    public static SshSessionMode fromPropertyValue(String value) {
        if (value == null) {
            return VT100;
        }
        SshSessionMode[] modes = values();
        for (int i = 0; i < modes.length; i++) {
            if (modes[i].propertyValue.equalsIgnoreCase(value)) {
                return modes[i];
            }
        }
        return VT100;
    }

    /**
     * The toString method returns the combo label for this mode.
     * The label is the same text {@link #getDisplayName} returns.
     *
     * @return The return value is {@code VT100} or {@code Standard PTY}.
     */
    @Override
    public String toString() {
        return displayName;
    }
}
/*****************************************************************************************
 * End of Class: SshSessionMode.java 082426
 ****************************************************************************************/
