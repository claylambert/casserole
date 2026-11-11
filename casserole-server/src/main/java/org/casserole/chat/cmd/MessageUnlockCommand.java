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

// @aa-sdd PK-0846 PK-0893 PK-0940 PK-0987 PK-1034 PK-1081 PK-1128 PK-1175 PK-1222 PK-1269 PK-1316 PK-1363 PK-1410 PK-1457 PK-1504 PK-1551 PK-1599 PK-1646 PK-1693 PK-1740 PK-1787 PK-1834 PK-1881 PK-1928 PK-1975 PK-2022 PK-2070 PK-2117 PK-2164 PK-2211 PK-2259 PK-2307 PK-2355 PK-2402 PK-2449 PK-2497 PK-2544 PK-2591 PK-2638 PK-2686 PK-2733 PK-2781 PK-2828 PK-2875 PK-2922 PK-2969 PK-3016 PK-3063 PK-3110 PK-3157 PK-3205 PK-3252 PK-3299 PK-3347 PK-3394 PK-3441 docs/specs/java/org/roux/roux.spec.md
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
