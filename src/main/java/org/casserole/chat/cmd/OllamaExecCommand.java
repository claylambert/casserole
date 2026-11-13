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

import org.casserole.format.LineDiscipline;
import org.casserole.ollama.OllamaProcess;

// @aa-sdd L40 app-design.md
/**
 * The {@code OllamaExecCommand} type is the {@code ollama} command.
 * The command runs the {@code ollama} program with the remaining tokens as arguments.
 * The command does not start a shell.
 * The command has no aliases.
 * {@code ollama load} and {@code ollama stop} are separate commands.
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-751 docs/specs/common/stages.spec.md
// @aa-sdd GK-1441 aetheris-stage-1-tweaks.002.md
public class OllamaExecCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code ollama}.
     *
     * @return The text {@code ollama}.
     */
    @Override
    public String getName() {
        return "ollama";
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
     * The summary says the command runs an ollama command such as {@code ollama list}, and that no shell is used.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Run an ollama CLI command, e.g. ollama list. No shell.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the remaining tokens become arguments, with examples {@code ollama list} and {@code ollama ps}.
     * The text says metacharacters are rejected.
     * The text says {@code ollama load} and {@code ollama stop} are separate commands.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Invokes the ollama binary with the remaining tokens as arguments. "
                + "Example: ollama list , ollama ps . Metacharacters are rejected. "
                + "ollama load / ollama stop are separate commands.";
    }

    /**
     * The {@code execute} method runs one ollama subcommand.
     * If {@code args} is {@code null} or blank, the method throws {@code Exception} with the message {@code Usage: ollama <subcommand> [args...]}.
     * {@link #splitArgs} splits the remainder on spaces and tabs.
     * {@link OllamaProcess#run} receives those tokens and a timeout of 30000 milliseconds.
     * The captured output is written after {@link LineDiscipline#toCrlf}.
     * If the captured output does not already end with a line feed, the method writes one more carriage return and line feed.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The remainder was empty, or the Ollama process failed.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        if (args == null || args.trim().length() == 0) {
            throw new Exception("Usage: ollama <subcommand> [args...]");
        }
        String[] tokens = splitArgs(args.trim());
        String out = OllamaProcess.run(tokens, 30000L);
        ctx.write(LineDiscipline.toCrlf(out));
        if (!out.endsWith("\n")) {
            ctx.write("\r\n");
        }
    }

    /**
     * The {@code splitArgs} method splits a remainder into tokens.
     * A space or a tab ends the current token.
     * Repeated whitespace does not create an empty token.
     * Quote characters are kept as ordinary characters.
     *
     * @param args The remainder after the command name, already trimmed by the caller.
     * @return The tokens in order.
     */
    private String[] splitArgs(String args) {
        java.util.ArrayList<String> list = new java.util.ArrayList<String>();
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < args.length(); i++) {
            char c = args.charAt(i);
            if (c == ' ' || c == '\t') {
                if (cur.length() > 0) {
                    list.add(cur.toString());
                    cur.setLength(0);
                }
            } else {
                cur.append(c);
            }
        }
        if (cur.length() > 0) {
            list.add(cur.toString());
        }
        return list.toArray(new String[0]);
    }
}
/*****************************************************************************************
 * End of Class: OllamaExecCommand.java 082526
 ****************************************************************************************/
