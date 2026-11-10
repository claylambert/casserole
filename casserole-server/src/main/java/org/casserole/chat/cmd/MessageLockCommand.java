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

// @aa-sdd PK-0845 PK-0892 PK-0939 PK-0986 PK-1033 PK-1080 PK-1127 PK-1174 PK-1221 PK-1268 PK-1315 PK-1362 PK-1409 PK-1456 PK-1503 PK-1550 PK-1598 PK-1645 PK-1692 PK-1739 PK-1786 PK-1833 PK-1880 PK-1927 PK-1974 PK-2021 PK-2069 PK-2116 PK-2163 PK-2210 PK-2258 PK-2306 PK-2354 PK-2401 PK-2448 PK-2496 PK-2543 PK-2590 PK-2637 PK-2685 PK-2732 PK-2780 PK-2827 PK-2874 PK-2921 PK-2968 PK-3015 PK-3062 PK-3109 PK-3156 PK-3204 PK-3251 PK-3298 PK-3346 PK-3393 PK-3440 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
/**
 * The {@code MessageLockCommand} type is the {@code message lock} command.
 * The command locks the next open sequence number in the eligible run.
 * A miss leaves the packed prefix unchanged.
 * The command has no aliases.
 *
 * @author clayl (2026-11-04)
 */
public class MessageLockCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code message lock}.
     *
     * @return The text {@code message lock}.
     */
    @Override
    public String getName() {
        return "message lock";
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
     * The summary says the command locks the next open sequence number in the eligible run.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Lock the next open seq in the eligible run.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the command locks the first eligible sequence number that is still open.
     * The text says a miss leaves the prefix.
     * The example in the text is {@code message lock 2}.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Locks the first eligible seq that is still open. A miss leaves the prefix. "
                + "Example: message lock 2";
    }

    /**
     * The {@code execute} method locks one sequence number.
     * {@link #seq} parses the argument as the sequence number for {@code message lock}.
     * The head count comes from the chat properties.
     * The head count is how many leading messages stay packed.
     * If {@link ChatSession#lockMessage} returns a message, the method throws {@code Exception} with that message.
     * If the lock succeeds, the method writes {@code locked } plus the sequence number.
     * The written line ends with a carriage return and a line feed.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The sequence number was missing, or the session refused the lock.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        long seq = seq(args, "message lock");
        int headCount = ctx.getProperties().getChat().getHeadCount();
        String miss = ctx.getSession().lockMessage(seq, headCount);
        if (miss != null) {
            throw new Exception(miss);
        }
        ctx.write("locked " + seq + "\r\n");
    }

    /**
     * The {@code seq} method parses one sequence number from a command remainder.
     * If {@code args} is {@code null} or blank, the method throws {@code Exception} with {@code Usage: }, the command name, and {@code  <seq>}.
     * If the trimmed text is not a decimal long, the method throws the same usage exception.
     *
     * @param args The remainder of the line after the command name.
     * @param command The command name placed in the usage message.
     * @return The parsed sequence number.
     * @throws Exception The remainder was blank or was not a number.
     */
    static long seq(String args, String command) throws Exception {
        if (args == null || args.trim().length() == 0) {
            throw new Exception("Usage: " + command + " <seq>");
        }
        try {
            return Long.parseLong(args.trim());
        } catch (NumberFormatException ex) {
            throw new Exception("Usage: " + command + " <seq>");
        }
    }
}
/*****************************************************************************************
 * End of Class: MessageLockCommand.java 110426
 ****************************************************************************************/
