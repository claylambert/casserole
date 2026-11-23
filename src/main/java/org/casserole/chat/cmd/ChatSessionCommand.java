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

import org.roux.session.OpenResult;

// @aa-sdd PK-1465 docs/specs/java/org/roux/roux.spec.md
/**
 * The {@code ChatSessionCommand} type is the {@code chat session} command.
 * No token returns this login to the username pack and writes no banner.
 * One token leaves the live pack and opens that name.
 * A new named pack writes {@code New session } and the name.
 * A restored named pack writes {@code Restored }, the count, the SSH user, and the name.
 * More than one token is refused with {@code invalid session name}.
 * A name the shelf refuses is refused with that same text.
 * A refusal writes the reason and leaves the pack the engine left mapped.
 * The command does not add a system line to a model send.
 * The command has no aliases.
 *
 * @author clayl (2026-11-22)
 */
public class ChatSessionCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code chat session}.
     *
     * @return The text {@code chat session}.
     */
    @Override
    public String getName() {
        return "chat session";
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
     * The summary says the command opens a named pack for this login.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Open a named pack for this login, or return to the username pack.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says one name leaves the live pack and opens that name.
     * The text says no name returns to the username pack.
     * The text says clear and chat start still wipe only the live pack.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "One name leaves the live pack and opens that name. "
                + "No name returns to the username pack. "
                + "Clear and chat start still wipe only the live pack.";
    }

    /**
     * The {@code execute} method switches the live pack and writes the banner.
     * The argument is the remainder of the line, trimmed.
     * No token returns to the username pack and writes no banner.
     * One token calls {@link org.casserole.chat.ChatSession#switchPack}.
     * More than one token writes {@code invalid session name}.
     * A new named pack writes {@code New session }, the name, and a carriage return and a line feed.
     * A restored named pack writes {@code Restored }, the count, {@code messages for }, the SSH user,
     * {@code session }, the name, and the same line ending.
     * A refusal writes the reason and that line ending.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     */
    @Override
    public void execute(CommandContext ctx, String args) {
        String trimmed = args == null ? "" : args.trim();
        if (trimmed.length() == 0) {
            OpenResult result = ctx.getSession().switchPack("");
            if (result == null || !result.isOk()) {
                String reason = result == null ? "invalid session name" : result.getReason();
                ctx.write(reason + "\r\n");
            }
            return;
        }
        if (!oneToken(trimmed)) {
            ctx.write("invalid session name\r\n");
            return;
        }
        OpenResult result = ctx.getSession().switchPack(trimmed);
        if (result == null || !result.isOk()) {
            String reason = result == null ? "invalid session name" : result.getReason();
            ctx.write(reason + "\r\n");
            return;
        }
        if (result.getKind() == OpenResult.NEW) {
            ctx.write("New session " + trimmed + "\r\n");
            return;
        }
        ctx.write("Restored " + result.getMessageCount() + " messages for " + ctx.getSshUsername()
                + " session " + trimmed + "\r\n");
    }

    /**
     * The method reports whether the text is a single token.
     * A space or a tab means more than one token.
     *
     * @param text The text is the trimmed remainder of the line.
     * @return The flag is true when the text holds one token.
     */
    private static boolean oneToken(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ' || c == '\t') {
                return false;
            }
        }
        return true;
    }
}
/*****************************************************************************************
 * End of Class: ChatSessionCommand.java 112226
 ****************************************************************************************/
