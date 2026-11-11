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

// @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/**
 * The {@code MessageShowCommand} type is the {@code message show} command.
 * The command prints a debug card for one stored message.
 * The card includes the locator, the identity pair, the page pointer, the residency, the checksum, and the text.
 * The command has no aliases.
 *
 * @author clayl (2026-08-25)
 */
public class MessageShowCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code message show}.
     *
     * @return The text {@code message show}.
     */
    @Override
    public String getName() {
        return "message show";
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
     * The summary says the command inspects one message by sequence number and shows ids, page, residency, crc, and text.
     * The crc value is the checksum result for that stored message.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Inspect one message by seq: ids, page, residency, crc, text.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the command prints the Roux locator, the identity pair, the page pointer, the residency, and the unmarshalled text.
     * Roux is the store that holds the conversation messages.
     * The example in the text is {@code message show 1}.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Prints Roux locator, identity pair, page pointer, residency, and unmarshalled text. "
                + "Example: message show 1";
    }

    /**
     * The {@code execute} method prints the card for one sequence number.
     * If {@code args} is {@code null} or blank, the method throws {@code Exception} with the message {@code Usage: message show <seq>}.
     * If the trimmed text is not a decimal long, the method throws the same usage exception.
     * If {@link ChatSession#inspect} returns {@code null}, the method throws {@code Exception} with {@code No message seq } plus the number.
     * A found card is printed by {@link #writeCard}.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The usage was wrong, or no message has that sequence number.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        if (args == null || args.trim().length() == 0) {
            throw new Exception("Usage: message show <seq>");
        }
        long seq;
        try {
            seq = Long.parseLong(args.trim());
        } catch (NumberFormatException ex) {
            throw new Exception("Usage: message show <seq>");
        }
        ChatSession session = ctx.getSession();
        MessageInspect card = session.inspect(seq);
        if (card == null) {
            throw new Exception("No message seq " + seq);
        }
        writeCard(ctx, card);
    }

    /**
     * The {@code writeCard} method writes one inspect card to the shell.
     * The first line is {@code seq: } plus the sequence number.
     * If the Roux id is present, the next line is {@code id: } plus the high half, a space, and the low half.
     * The following lines are the role name, {@code ts: } plus the epoch milliseconds, {@code tokens: } plus the token count, and {@code type: } plus the type.
     * The page line is {@code page: }, the page id, {@code   offset: }, the offset, {@code   length: }, and the length.
     * The next lines are {@code residency: } plus the residency, and {@code crc: ok} or {@code crc: fail}.
     * The last lines are {@code text:} and then the stored text.
     * Each line ends with a carriage return and a line feed.
     *
     * @param ctx The shell resources that receive the card.
     * @param card The inspect record to print.
     */
    static void writeCard(CommandContext ctx, MessageInspect card) {
        ctx.write("seq: " + card.getSeq() + "\r\n");
        if (card.getRouxId() != null) {
            ctx.write("id: " + card.getRouxId().getHi() + " " + card.getRouxId().getLo() + "\r\n");
        }
        ctx.write("role: " + Role.nameOf(card.getRole()) + "\r\n");
        ctx.write("ts: " + card.getTimestampEpochMs() + "\r\n");
        ctx.write("tokens: " + card.getTokenCount() + "\r\n");
        ctx.write("type: " + card.getType() + "\r\n");
        ctx.write("page: " + card.getPageId() + "  offset: " + card.getOffset() + "  length: " + card.getLength()
                + "\r\n");
        ctx.write("residency: " + card.getResidency() + "\r\n");
        ctx.write("crc: " + (card.isCrcOk() ? "ok" : "fail") + "\r\n");
        ctx.write("text:\r\n");
        ctx.write(card.getText() + "\r\n");
    }
}
/*****************************************************************************************
 * End of Class: MessageShowCommand.java 082526
 ****************************************************************************************/
