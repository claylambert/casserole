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

// @aa-sdd GK-0961 aetheris-stage-2-finessing.001.md FINESSE:f-222
/**
 * The {@code MessageEditCommand} type is the {@code message edit} command.
 * The command revises one stored turn and does not send that turn to the model.
 * A body on the same line is the new text.
 * A sequence number with no body reads following lines until a line whose text is {@code <.>}.
 * The command has no aliases.
 *
 * @author clayl (2026-11-04)
 */
public class MessageEditCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code message edit}.
     *
     * @return The text {@code message edit}.
     */
    @Override
    public String getName() {
        return "message edit";
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
     * The summary says the command revises one sequence number, and a bare sequence number reads until {@code <.>}.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Revise one seq. A bare seq reads until <.>.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the command writes a new frame for that sequence number and leaves the lock.
     * The example in the text is {@code message edit 2 the new line}.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Writes a new frame for that seq and leaves the lock. "
                + "Example: message edit 2 the new line";
    }

    /**
     * The {@code execute} method revises one stored message or starts a multi-line read.
     * If {@code args} is {@code null} or blank, the method throws {@code Exception} with the message {@code Usage: message edit <seq>}.
     * The first token must be a decimal sequence number.
     * If that token is not a number, the method throws the same usage exception.
     * If the line has no text after the sequence number, the method calls {@link CommandContext#collectMessageEdit} and returns.
     * If the text after the sequence number is empty, the method throws {@code Exception} with the message {@code An empty body is refused.}
     * Otherwise the method stores that text with {@link ChatSession#editMessage} and writes {@code edited } plus the sequence number.
     * The written line ends with a carriage return and a line feed.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The usage was wrong, the body was empty, or the session rejected the edit.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        if (args == null || args.trim().length() == 0) {
            throw new Exception("Usage: message edit <seq>");
        }
        String rest = args.trim();
        int space = rest.indexOf(' ');
        String seqText = space < 0 ? rest : rest.substring(0, space);
        long seq;
        try {
            seq = Long.parseLong(seqText);
        } catch (NumberFormatException ex) {
            throw new Exception("Usage: message edit <seq>");
        }
        if (space < 0) {
            ctx.collectMessageEdit(seq);
            return;
        }
        String body = rest.substring(space + 1);
        if (body.length() == 0) {
            throw new Exception("An empty body is refused.");
        }
        ctx.getSession().editMessage(seq, body);
        ctx.write("edited " + seq + "\r\n");
    }

    /**
     * The {@code joinBody} method joins body lines into one string.
     * A line whose text is {@code <.>} ends the body and is not included.
     * A line whose text is {@code .} is body text.
     * A {@code null} entry is treated as an empty line.
     * Lines are joined with a newline character.
     * If {@code lines} is {@code null}, the method throws {@code Exception} with the message {@code An empty body is refused.}
     * If no body line was kept, or the joined text is empty, the method throws the same exception.
     *
     * @param lines The lines after the bare sequence number, including the terminator.
     * @return The joined body.
     * @throws Exception The body was missing or empty.
     */
    public static String joinBody(String[] lines) throws Exception {
        if (lines == null) {
            throw new Exception("An empty body is refused.");
        }
        StringBuilder body = new StringBuilder();
        boolean any = false;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i] == null ? "" : lines[i];
            if ("<.>".equals(line)) {
                break;
            }
            if (any) {
                body.append('\n');
            }
            body.append(line);
            any = true;
        }
        if (!any || body.length() == 0) {
            throw new Exception("An empty body is refused.");
        }
        return body.toString();
    }
}
/*****************************************************************************************
 * End of Class: MessageEditCommand.java 110426
 ****************************************************************************************/
