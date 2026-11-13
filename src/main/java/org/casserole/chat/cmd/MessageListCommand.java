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

import java.util.List;

import org.casserole.chat.ChatSession;
import org.roux.model.MessageInspect;
import org.roux.model.Role;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;

// @aa-sdd GK-0962 aetheris-stage-2-finessing.001.md FINESSE:f-222
/**
 * The {@code MessageListCommand} type is the {@code message list} command.
 * The alias is {@code messages}.
 * The command prints this session's history.
 * An optional filter keeps {@code all}, {@code user}, {@code assistant}, or {@code system}.
 * The word {@code verbose} prints full text.
 * The word {@code meta} prints the stored locator beside each message.
 *
 * @author clayl (2026-08-26)
 */
// @aa-sdd PK-323 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-330 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
public class MessageListCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code message list}.
     *
     * @return The text {@code message list}.
     */
    @Override
    public String getName() {
        return "message list";
    }

    /**
     * The {@code getAliases} method returns the alternate name {@code messages}.
     *
     * @return An array whose only entry is {@code messages}.
     */
    @Override
    public String[] getAliases() {
        return new String[] { "messages" };
    }

    /**
     * The {@code getHelp} method returns the one-line summary for {@code help}.
     * The summary says the history is terse, {@code verbose} shows full text, and the filter is {@code all}, {@code user}, {@code assistant}, or {@code system}.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Show history (terse). verbose for full text. Filter: all | user | assistant | system.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the command prints this session's unmarshalled conversation.
     * The default is terse and uses {@code list-preview-chars}.
     * The examples in the text are {@code message list}, {@code message list verbose}, {@code message list user}, and {@code message list meta verbose}.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Prints this session's conversation (unmarshalled). Default is terse "
                + "(list-preview-chars). Examples: message list, message list verbose, "
                + "message list user, message list meta verbose.";
    }

    /**
     * The {@code execute} method prints the session history.
     * {@link #parseFlags} reads {@code meta}, {@code verbose}, and the optional filter.
     * {@link #previewCap} chooses how many characters of each message to show.
     * If {@code meta} is set, the method walks sequence numbers from one through the message count.
     * A {@code null} inspect result is skipped.
     * A filter that does not match the stored role is skipped.
     * Each meta hit writes the sequence number, the role, the head mark, {@code id=}, the Roux id or {@code 0 0}, {@code page=}, the page id, and the residency.
     * The next line is the text cut by {@link #truncate}.
     * If {@code meta} is not set, the method walks {@link ChatSession#getMessages}.
     * A filter that does not match the message type is skipped.
     * Each hit writes the one-based index, the type name, and the head mark, then the cut text.
     * If no row is shown, the method writes {@code No messages.}
     * Each line ends with a carriage return and a line feed.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The filter word is not recognized.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        Flags flags = parseFlags(args);
        ChatSession session = ctx.getSession();
        int cap = previewCap(ctx, flags.verbose);
        if (flags.meta) {
            int shown = 0;
            int total = session.getMessages().size();
            for (int seq = 1; seq <= total; seq++) {
                MessageInspect card = session.inspect(seq);
                if (card == null) {
                    continue;
                }
                if (flags.filter != null && !roleMatches(card.getRole(), flags.filter)) {
                    continue;
                }
                shown++;
                String id = card.getRouxId() == null ? "0 0"
                        : card.getRouxId().getHi() + " " + card.getRouxId().getLo();
                ctx.write("[" + card.getSeq() + "] " + Role.nameOf(card.getRole())
                        + session.headMark(card.getSeq(), ctx.getProperties().getChat().getHeadCount())
                        + " id=" + id
                        + " page=" + card.getPageId()
                        + " " + card.getResidency()
                        + "\r\n");
                ctx.write(truncate(card.getText(), cap) + "\r\n");
            }
            if (shown == 0) {
                ctx.write("No messages.\r\n");
            }
            return;
        }
        List<Message> messages = session.getMessages();
        int shown = 0;
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (flags.filter != null && message.getMessageType() != flags.filter) {
                continue;
            }
            shown++;
            ctx.write("[" + (i + 1) + "] " + message.getMessageType().name()
                    + session.headMark(i + 1L, ctx.getProperties().getChat().getHeadCount()) + ":\r\n");
            String text = message.getText();
            if (text == null) {
                text = "";
            }
            ctx.write(truncate(text, cap) + "\r\n");
        }
        if (shown == 0) {
            ctx.write("No messages.\r\n");
        }
    }

    /**
     * The {@code previewCap} method chooses how many characters of a message to print.
     * If {@code verbose} is {@code true}, the method returns {@code 0}, which means no cut.
     * Otherwise the method reads {@code list-preview-chars} from the chat properties.
     * If that value is negative, the method returns {@code 120}.
     *
     * @param ctx The shell resources for this connection.
     * @param verbose {@code true} when the caller asked for full text.
     * @return The character cap, or {@code 0} when the text should not be cut.
     */
    static int previewCap(CommandContext ctx, boolean verbose) {
        if (verbose) {
            return 0;
        }
        int n = ctx.getProperties().getChat().getListPreviewChars();
        if (n < 0) {
            return 120;
        }
        return n;
    }

    /**
     * The {@code truncate} method shortens text to a character cap.
     * A {@code null} text becomes the empty string.
     * If {@code cap} is {@code 0} or negative, the text is returned unchanged.
     * If the text is no longer than the cap, the text is returned unchanged.
     * Otherwise the method returns the first {@code cap} characters plus the ellipsis character.
     *
     * @param text The raw message text.
     * @param cap The maximum number of characters, or {@code 0} when the text should not be cut.
     * @return The original text, or the cut text with an ellipsis.
     */
    static String truncate(String text, int cap) {
        String t = text == null ? "" : text;
        if (cap <= 0 || t.length() <= cap) {
            return t;
        }
        return t.substring(0, cap) + "…";
    }

    /**
     * The {@code roleMatches} method compares a stored Roux role with a Spring message type.
     * Roux is the store that holds the conversation messages.
     * {@code MessageType.SYSTEM} matches {@code Role.SYSTEM}.
     * {@code MessageType.USER} matches {@code Role.USER}.
     * {@code MessageType.ASSISTANT} matches {@code Role.ASSISTANT}.
     * Any other filter matches every role.
     *
     * @param role The stored Roux role byte.
     * @param filter The Spring message type selected by the caller.
     * @return {@code true} when the role matches the filter.
     */
    private boolean roleMatches(byte role, MessageType filter) {
        if (filter == MessageType.SYSTEM) {
            return role == Role.SYSTEM;
        }
        if (filter == MessageType.USER) {
            return role == Role.USER;
        }
        if (filter == MessageType.ASSISTANT) {
            return role == Role.ASSISTANT;
        }
        return true;
    }

    /**
     * The {@code parseFlags} method reads {@code meta}, {@code verbose}, and the filter from the remainder.
     * A {@code null} remainder is treated as empty.
     * The words {@code meta} and {@code verbose} may appear in either order, each as a whole token.
     * The leftover text is passed to {@link #parseFilter}.
     *
     * @param args The remainder of the line after the command name.
     * @return The parsed {@link Flags}.
     * @throws Exception The leftover filter word is not recognized.
     */
    private Flags parseFlags(String args) throws Exception {
        boolean meta = false;
        boolean verbose = false;
        String rest = args == null ? "" : args.trim();
        while (rest.length() > 0) {
            if (rest.equals("meta") || rest.startsWith("meta ")) {
                meta = true;
                rest = rest.equals("meta") ? "" : rest.substring(5).trim();
                continue;
            }
            if (rest.equals("verbose") || rest.startsWith("verbose ")) {
                verbose = true;
                rest = rest.equals("verbose") ? "" : rest.substring(8).trim();
                continue;
            }
            break;
        }
        Flags flags = new Flags();
        flags.meta = meta;
        flags.verbose = verbose;
        flags.filter = parseFilter(rest);
        return flags;
    }

    /**
     * The {@code parseFilter} method turns the leftover text into a message type.
     * A {@code null} value, an empty value, or {@code all} returns {@code null}, which keeps every message.
     * The word {@code user} returns {@code MessageType.USER}.
     * The word {@code assistant} returns {@code MessageType.ASSISTANT}.
     * The word {@code system} returns {@code MessageType.SYSTEM}.
     * Any other word throws {@code Exception} with {@code Unknown filter: }, the word, and {@code (use all, user, assistant, system)}.
     *
     * @param args The leftover filter token.
     * @return The type to keep, or {@code null} when every type is kept.
     * @throws Exception The filter word is not recognized.
     */
    private MessageType parseFilter(String args) throws Exception {
        if (args == null || args.length() == 0 || "all".equals(args)) {
            return null;
        }
        if ("user".equals(args)) {
            return MessageType.USER;
        }
        if ("assistant".equals(args)) {
            return MessageType.ASSISTANT;
        }
        if ("system".equals(args)) {
            return MessageType.SYSTEM;
        }
        throw new Exception("Unknown filter: " + args + " (use all, user, assistant, system)");
    }

    // @aa-sdd L40 app-design.md
    /**
     * The {@code Flags} type holds the options parsed from one {@code message list} line.
     * {@code meta} selects the locator form.
     * {@code verbose} selects full text.
     * {@code filter} is the message type to keep, or {@code null} when every type is kept.
     */
    // @aa-sdd PK-322 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd GK-2310 roux-clip-range.md
    // @aa-sdd GK-2325 roux-clip-range.md
    private static final class Flags {
        private boolean meta;
        private boolean verbose;
        private MessageType filter;
    }
}
/*****************************************************************************************
 * End of Class: MessageListCommand.java 082626
 ****************************************************************************************/
