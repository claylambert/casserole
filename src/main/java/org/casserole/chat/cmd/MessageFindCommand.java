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

import org.casserole.chat.ChatSession;
import org.roux.model.MessageInspect;
import org.roux.model.Role;

// @aa-sdd L40 app-design.md
/**
 * The {@code MessageFindCommand} type is the {@code message find} command.
 * One form looks up a Roux identity pair, written as a high half and a low half.
 * Roux is the store that holds the conversation messages.
 * The other form lists every message whose text contains a substring.
 * The command has no aliases.
 *
 * @author clayl (2026-08-26)
 */
// @aa-sdd PK-331 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
public class MessageFindCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code message find}.
     *
     * @return The text {@code message find}.
     */
    @Override
    public String getName() {
        return "message find";
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
     * The summary says the command finds by an identity pair, or by a case-insensitive substring after {@code by}.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Find by identity pair, or 'by' a substring (case-insensitive).";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the command looks up a Roux id high and low half, or lists every message whose text contains a string.
     * The examples in the text are {@code message find 123 456} and {@code message find by 'hello'}.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Looks up RouxId hi/lo, or lists every message whose text contains "
                + "a string. Examples: message find 123 456   message find by 'hello'";
    }

    /**
     * The {@code execute} method finds one message by identity or several messages by text.
     * If {@code args} is {@code null} or blank, the method throws {@code Exception} with the usage that names both forms.
     * If the trimmed text is {@code by}, or starts with {@code by } , the rest is the substring search.
     * {@link #stripQuotes} removes one matching quote pair from that substring.
     * If the substring is empty, the method throws {@code Exception} with the message {@code Usage: message find by <text>}.
     * Otherwise {@link #findByText} lists the matches.
     * The identity form requires two whitespace-separated tokens.
     * If either token is not a decimal long, the method throws the two-form usage exception.
     * If {@link ChatSession#inspectById} returns {@code null}, the method throws {@code Exception} with the message {@code No message with that id}.
     * A found card is printed by {@link MessageShowCommand#writeCard}.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The usage was wrong, or no message has that identity.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        if (args == null) {
            throw new Exception("Usage: message find <idHi> <idLo>  |  message find by <text>");
        }
        String trimmed = args.trim();
        if (trimmed.length() == 0) {
            throw new Exception("Usage: message find <idHi> <idLo>  |  message find by <text>");
        }
        if (trimmed.equals("by") || trimmed.startsWith("by ")) {
            String needle = trimmed.equals("by") ? "" : trimmed.substring(3).trim();
            needle = stripQuotes(needle);
            if (needle.length() == 0) {
                throw new Exception("Usage: message find by <text>");
            }
            findByText(ctx, needle);
            return;
        }
        String[] parts = trimmed.split("\\s+");
        if (parts.length < 2) {
            throw new Exception("Usage: message find <idHi> <idLo>  |  message find by <text>");
        }
        long hi;
        long lo;
        try {
            hi = Long.parseLong(parts[0]);
            lo = Long.parseLong(parts[1]);
        } catch (NumberFormatException ex) {
            throw new Exception("Usage: message find <idHi> <idLo>  |  message find by <text>");
        }
        ChatSession session = ctx.getSession();
        MessageInspect card = session.inspectById(hi, lo);
        if (card == null) {
            throw new Exception("No message with that id");
        }
        MessageShowCommand.writeCard(ctx, card);
    }

    /**
     * The {@code findByText} method lists messages whose text contains a substring.
     * The comparison is case-insensitive.
     * Sequence numbers run from one through the current message count.
     * A {@code null} inspect result is skipped.
     * A {@code null} text is treated as empty.
     * Each hit writes {@code [}, the sequence number, {@code] }, the role name, {@code : }, and the text cut by {@link MessageListCommand#truncate}.
     * The preview cap comes from {@link MessageListCommand#previewCap} with verbose off.
     * If no message matches, the method writes {@code No messages containing that text.}
     * Each line ends with a carriage return and a line feed.
     *
     * @param ctx The shell resources for this connection.
     * @param needle The substring to find.
     */
    private void findByText(CommandContext ctx, String needle) {
        ChatSession session = ctx.getSession();
        int total = session.getMessages().size();
        int cap = MessageListCommand.previewCap(ctx, false);
        String want = needle.toLowerCase();
        int shown = 0;
        for (int seq = 1; seq <= total; seq++) {
            MessageInspect card = session.inspect(seq);
            if (card == null) {
                continue;
            }
            String text = card.getText() == null ? "" : card.getText();
            if (text.toLowerCase().indexOf(want) < 0) {
                continue;
            }
            shown++;
            ctx.write("[" + card.getSeq() + "] " + Role.nameOf(card.getRole()) + ": "
                    + MessageListCommand.truncate(text, cap) + "\r\n");
        }
        if (shown == 0) {
            ctx.write("No messages containing that text.\r\n");
        }
    }

    /**
     * The {@code stripQuotes} method removes one matching quote pair from a search string.
     * The pair is removed only when the text has at least two characters and both ends are {@code '} or both ends are {@code "}.
     * Any other text is returned unchanged.
     *
     * @param raw The search text, which may be quoted.
     * @return The text without one matching quote pair, or the original text.
     */
    private static String stripQuotes(String raw) {
        if (raw.length() >= 2) {
            char a = raw.charAt(0);
            char b = raw.charAt(raw.length() - 1);
            if ((a == '\'' && b == '\'') || (a == '"' && b == '"')) {
                return raw.substring(1, raw.length() - 1);
            }
        }
        return raw;
    }
}
/*****************************************************************************************
 * End of Class: MessageFindCommand.java 082626
 ****************************************************************************************/
