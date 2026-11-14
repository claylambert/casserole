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

package org.casserole.chat;

// @aa-sdd L40 app-design.md
/**
 * The {@code ShellMode} type names how the VT100 shell treats the Enter key.
 * VT100 is the terminal control set the chat shell paints with.
 * {@link #CHAT} sends the entered line as a user turn to the language model.
 * A language model is the program that writes the assistant reply.
 * {@link #COMMAND} submits the entered line as a command from the green line editor.
 * {@link #IDLE} follows the {@code chat stop} command, and Enter does not call the language model.
 *
 * @author clayl (2026-08-24)
 */
public enum ShellMode {

    /**
     * The {@code CHAT} constant sends Enter as a user turn to the language model.
     */
    CHAT,

    /**
     * The {@code COMMAND} constant submits Enter as a command from the green line editor.
     */
    COMMAND,

    /**
     * The {@code IDLE} constant follows {@code chat stop}.
     * Enter does not call the language model.
     */
    IDLE
}
/*****************************************************************************************
 * End of Class: ShellMode.java 082426
 ****************************************************************************************/
