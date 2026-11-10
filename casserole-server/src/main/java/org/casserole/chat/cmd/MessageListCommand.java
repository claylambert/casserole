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

// @aa-sdd PK-0844 PK-0891 PK-0938 PK-0985 PK-1032 PK-1079 PK-1126 PK-1173 PK-1220 PK-1267 PK-1314 PK-1361 PK-1408 PK-1455 PK-1502 PK-1549 PK-1597 PK-1644 PK-1691 PK-1738 PK-1785 PK-1832 PK-1879 PK-1926 PK-1973 PK-2020 PK-2068 PK-2115 PK-2162 PK-2209 PK-2257 PK-2305 PK-2353 PK-2400 PK-2447 PK-2495 PK-2542 PK-2589 PK-2636 PK-2684 PK-2731 PK-2779 PK-2826 PK-2873 PK-2920 PK-2967 PK-3014 PK-3061 PK-3108 PK-3155 PK-3203 PK-3250 PK-3297 PK-3345 PK-3392 PK-3439 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
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

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The {@code Flags} type holds the options parsed from one {@code message list} line.
     * {@code meta} selects the locator form.
     * {@code verbose} selects full text.
     * {@code filter} is the message type to keep, or {@code null} when every type is kept.
     */
    private static final class Flags {
        private boolean meta;
        private boolean verbose;
        private MessageType filter;
    }
}
/*****************************************************************************************
 * End of Class: MessageListCommand.java 082626
 ****************************************************************************************/
