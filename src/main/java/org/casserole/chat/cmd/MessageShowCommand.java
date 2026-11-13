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
