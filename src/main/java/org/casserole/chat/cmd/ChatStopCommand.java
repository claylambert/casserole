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

// @aa-sdd L40 app-design.md
/**
 * The {@code ChatStopCommand} type is the {@code chat stop} command.
 * The command deactivates chat and keeps the in-memory history.
 * The command has no aliases.
 *
 * @author clayl (2026-08-24)
 */
public class ChatStopCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code chat stop}.
     *
     * @return The text {@code chat stop}.
     */
    @Override
    public String getName() {
        return "chat stop";
    }

    /**
     * The {@code getAliases} method returns no alternate names.
     *
     * @return An empty array.
     */
    @Override
    public String[] getAliases() {
        return new String[0];
    }

    /**
     * The {@code getHelp} method returns the one-line summary for {@code help}.
     * The summary says chat stops, the history is kept, and the terminal goes idle.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Stop chat (keep history). Terminal goes idle.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the session is marked inactive so Enter no longer calls the model.
     * The text says the history stays available to message list and status.
     * The text says the SSH connection stays open.
     * SSH is the secure-shell protocol the client used to connect.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Marks the session inactive so Enter no longer calls the model. "
                + "History is retained for message list / status. SSH stays open.";
    }

    /**
     * The {@code execute} method stops the session and marks chat inactive.
     * The method then writes {@code Chat session stopped. History kept. Type 'chat start' or press Escape for commands.}
     * The written line ends with a carriage return and a line feed.
     * The {@code args} text is ignored.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     */
    @Override
    public void execute(CommandContext ctx, String args) {
        ctx.getSession().stop();
        ctx.setChatActive(false);
        ctx.write("Chat session stopped. History kept. Type 'chat start' or press Escape for commands.\r\n");
    }
}
/*****************************************************************************************
 * End of Class: ChatStopCommand.java 082426
 ****************************************************************************************/
