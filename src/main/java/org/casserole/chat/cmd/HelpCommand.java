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

// @aa-sdd L40 app-design.md
/**
 * The {@code HelpCommand} type is the {@code help} command.
 * The alias is {@code ?}.
 * With no argument, the command lists every command and its one-line help.
 * With an argument, the command prints the longer description for that command.
 *
 * @author clayl (2026-08-24)
 */
public class HelpCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code help}.
     *
     * @return The text {@code help}.
     */
    @Override
    public String getName() {
        return "help";
    }

    /**
     * The {@code getAliases} method returns the alternate name {@code ?}.
     *
     * @return An array whose only entry is {@code ?}.
     */
    @Override
    public String[] getAliases() {
        return new String[] { "?" };
    }

    /**
     * The {@code getHelp} method returns the one-line summary for {@code help}.
     * The summary says the command lists commands, or accepts {@code help} plus a name for details.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "List commands, or 'help <name>' for details.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says no arguments print every command and its one-line help.
     * The text says an argument prints the longer description, for example {@code help chat start}.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "With no arguments, prints every command and its one-line help. "
                + "With an argument, prints the longer description for that command "
                + "(for example: help chat start).";
    }

    /**
     * The {@code execute} method lists every command, or prints one command's detail.
     * If {@code args} is {@code null} or empty, the method calls {@link #listAll}.
     * Otherwise the method looks the text up with {@link CommandProcessor#findByName}.
     * If no command matches, the method throws {@code Exception} with the message {@code Unknown command: } plus the text.
     * A match writes the name, a space, an em dash, a space, and {@link Command#getHelp()}, then the description.
     * If the match has at least one alias, the method writes {@code Aliases:} and each alias separated by a space.
     * Each of those lines ends with a carriage return and a line feed.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name, or empty when the caller wants the full list.
     * @throws Exception No command matches the supplied name.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        if (args == null || args.length() == 0) {
            listAll(ctx);
            return;
        }
        Command command = ctx.getProcessor().findByName(args);
        if (command == null) {
            throw new Exception("Unknown command: " + args);
        }
        ctx.write(command.getName() + " — " + command.getHelp() + "\r\n");
        ctx.write(command.getDescription() + "\r\n");
        String[] aliases = command.getAliases();
        if (aliases != null && aliases.length > 0) {
            ctx.write("Aliases:");
            for (int i = 0; i < aliases.length; i++) {
                ctx.write(" " + aliases[i]);
            }
            ctx.write("\r\n");
        }
    }

    /**
     * The {@code listAll} method writes every registered command and its one-line help.
     * The first line is {@code Commands:}.
     * Each later line is two spaces, the command name, two spaces, an em dash, two spaces, and the help text.
     * The last line says Escape toggles command mode and Ctrl+C cancels a line or an in-flight reply.
     * Each line ends with a carriage return and a line feed.
     *
     * @param ctx The shell resources for this connection.
     */
    private void listAll(CommandContext ctx) {
        List<Command> commands = ctx.getProcessor().getCommands();
        ctx.write("Commands:\r\n");
        for (int i = 0; i < commands.size(); i++) {
            Command command = commands.get(i);
            ctx.write("  " + command.getName() + "  —  " + command.getHelp() + "\r\n");
        }
        ctx.write("Escape toggles command mode. Ctrl+C cancels a line or an in-flight reply.\r\n");
    }
}
/*****************************************************************************************
 * End of Class: HelpCommand.java 082426
 ****************************************************************************************/
