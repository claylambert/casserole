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
 * The {@code MessageRewindCommand} type is the {@code message rewind} command.
 * A bare line clears the head origin.
 * A sequence sets the origin for the next prompt.
 * The command has no aliases.
 *
 * @author clayl (2026-11-23)
 */
// @aa-sdd PK-1470 docs/specs/java/org/roux/roux.spec.md
public class MessageRewindCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code message rewind}.
     *
     * @return The text {@code message rewind}.
     */
    @Override
    public String getName() {
        return "message rewind";
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
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Move the head origin, or clear it.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The example in the text is {@code message rewind 4}.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Sets the next prompt floor to one sequence, or clears that floor. Example: message rewind 4";
    }

    /**
     * The {@code execute} method moves or clears the head origin.
     * A blank remainder clears the origin.
     * A non-decimal remainder throws {@code Usage: message rewind <seq>}.
     * A missing sequence throws {@code No message seq } plus the number.
     * A sequence in the leading system run throws {@code seq is in the leading system run}.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The usage was wrong, or the sequence cannot be the origin.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        String trimmed = args == null ? "" : args.trim();
        if (trimmed.length() == 0) {
            String error = ctx.getSession().rewindBare();
            if (error != null) {
                throw new Exception(error);
            }
            return;
        }
        long seq;
        try {
            seq = Long.parseLong(trimmed);
        } catch (NumberFormatException ex) {
            throw new Exception("Usage: message rewind <seq>");
        }
        String error = ctx.getSession().rewindTo(seq);
        if (error != null) {
            throw new Exception(error);
        }
    }
}
/*****************************************************************************************
 * End of Class: MessageRewindCommand.java 112326
 ****************************************************************************************/
