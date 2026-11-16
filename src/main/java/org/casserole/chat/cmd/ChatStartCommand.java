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
 * The {@code ChatStartCommand} type is the {@code chat start} command.
 * The command resets this connection's chat history back to the system prompt and then activates chat.
 * The command has no aliases.
 *
 * @author clayl (2026-08-24)
 */
public class ChatStartCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code chat start}.
     *
     * @return The text {@code chat start}.
     */
    @Override
    public String getName() {
        return "chat start";
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
     * The summary says the command resets this connection's chat session and enters chat mode.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Reset this connection's chat session and enter chat mode.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says user and assistant turns are cleared.
     * The text says a configured system prompt stays, and a blank prompt leaves no system frame.
     * The text says the session is marked active and the terminal returns to {@code chat>}.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Clears user and assistant turns. A configured system prompt stays. "
                + "A blank prompt leaves no system frame. Marks the session active, and returns the terminal to chat>.";
    }

    /**
     * The {@code execute} method resets the session, marks chat active, and writes {@code Chat session started.}
     * The written line ends with a carriage return and a line feed.
     * The {@code args} text is ignored.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     */
    @Override
    public void execute(CommandContext ctx, String args) {
        ctx.getSession().reset();
        ctx.setChatActive(true);
        ctx.write("Chat session started.\r\n");
    }
}
/*****************************************************************************************
 * End of Class: ChatStartCommand.java 082426
 ****************************************************************************************/
