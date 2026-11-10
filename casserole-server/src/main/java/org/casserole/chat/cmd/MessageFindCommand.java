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
 * The {@code MessageFindCommand} type is the {@code message find} command.
 * One form looks up a Roux identity pair, written as a high half and a low half.
 * Roux is the store that holds the conversation messages.
 * The other form lists every message whose text contains a substring.
 * The command has no aliases.
 *
 * @author clayl (2026-08-26)
 */
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
