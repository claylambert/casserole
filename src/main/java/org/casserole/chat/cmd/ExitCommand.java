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

package org.casserole.chat.cmd;

/**
 * The {@code ExitCommand} type is the {@code exit} command.
 * The alias is {@code quit}.
 * The command closes the SSH session.
 * SSH is the secure-shell protocol the client used to connect.
 * The command does not return the terminal to chat mode.
 *
 * @author clayl (2026-08-24)
 */
public class ExitCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code exit}.
     *
     * @return The text {@code exit}.
     */
    @Override
    public String getName() {
        return "exit";
    }

    /**
     * The {@code getAliases} method returns the alternate name {@code quit}.
     *
     * @return An array whose only entry is {@code quit}.
     */
    @Override
    public String[] getAliases() {
        return new String[] { "quit" };
    }

    /**
     * The {@code getHelp} method returns the one-line summary for {@code help}.
     * The summary says this SSH session closes.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Close this SSH session.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the command prints goodbye and hangs up the VT100 and SSH connection.
     * VT100 is the terminal control set the chat shell paints with.
     * The text says the command does not return to chat mode.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Prints goodbye and hangs up the VT100 / SSH connection. "
                + "Does not return to chat mode.";
    }

    /**
     * The {@code execute} method writes {@code Goodbye.} and then requests that the session close.
     * The written line ends with a carriage return and a line feed.
     * The close happens after the command returns.
     * The {@code args} text is ignored.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     */
    @Override
    public void execute(CommandContext ctx, String args) {
        ctx.write("Goodbye.\r\n");
        ctx.requestExit();
    }
}
/*****************************************************************************************
 * End of Class: ExitCommand.java 082426
 ****************************************************************************************/
