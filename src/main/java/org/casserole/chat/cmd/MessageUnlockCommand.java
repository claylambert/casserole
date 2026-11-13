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

// @aa-sdd PK-1249 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md FINESSE:f-222
/**
 * The {@code MessageUnlockCommand} type is the {@code message unlock} command.
 * The command releases the last locked sequence number.
 * A miss leaves the packed prefix unchanged.
 * The command has no aliases.
 *
 * @author clayl (2026-11-04)
 */
public class MessageUnlockCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code message unlock}.
     *
     * @return The text {@code message unlock}.
     */
    @Override
    public String getName() {
        return "message unlock";
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
     * The summary says the command releases the last locked sequence number.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Release the last locked seq.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the command unlocks the last sequence number in the packed prefix.
     * The text says a miss leaves the prefix.
     * The example in the text is {@code message unlock 2}.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Unlocks the last seq in the packed prefix. A miss leaves the prefix. "
                + "Example: message unlock 2";
    }

    /**
     * The {@code execute} method unlocks one sequence number.
     * {@link MessageLockCommand#seq} parses the argument for {@code message unlock}.
     * The head count comes from the chat properties.
     * If {@link ChatSession#unlockMessage} returns a message, the method throws {@code Exception} with that message.
     * If the unlock succeeds, the method writes {@code unlocked } plus the sequence number.
     * The written line ends with a carriage return and a line feed.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The sequence number was missing, or the session refused the unlock.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        long seq = MessageLockCommand.seq(args, "message unlock");
        int headCount = ctx.getProperties().getChat().getHeadCount();
        String miss = ctx.getSession().unlockMessage(seq, headCount);
        if (miss != null) {
            throw new Exception(miss);
        }
        ctx.write("unlocked " + seq + "\r\n");
    }
}
/*****************************************************************************************
 * End of Class: MessageUnlockCommand.java 110426
 ****************************************************************************************/
