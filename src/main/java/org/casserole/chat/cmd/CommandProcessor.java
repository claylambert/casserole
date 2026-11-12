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

import java.util.ArrayList;
import java.util.List;

// @aa-sdd PK-0544 PK-0546 docs/specs/java/org/casserole/casserole.spec.md
/**
 * The {@code CommandProcessor} type is the command registry for one session.
 * The registry matches a full command name, and the longest match wins.
 * The remainder of the line is then passed to {@link Command#execute}.
 *
 * @author clayl (2026-08-24)
 */
public class CommandProcessor {

    private final List<Command> commands;

    /**
     * The constructor creates a registry with no commands.
     */
    public CommandProcessor() {
        this.commands = new ArrayList<Command>();
    }

    /**
     * The {@code register} method appends one command in registration order.
     *
     * @param command The command to add.
     */
    public void register(Command command) {
        commands.add(command);
    }

    /**
     * The {@code getCommands} method returns the registered commands.
     * The order is the order of {@link #register}.
     * The returned list is the live list, so a later change to it changes the registry.
     *
     * @return The registered commands in registration order.
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * The {@code execute} method runs {@code line} when it matches a registered name or alias.
     * If {@code line} is {@code null}, the method returns {@code false}.
     * {@link #normalize(String)} lowercases the line and collapses its whitespace.
     * If that result is empty, the method returns {@code false}.
     * {@link #findLongestMatch} picks the longest name or alias that matches.
     * If nothing matches, the method returns {@code false}.
     * A match calls {@link Command#execute} with the remainder and then returns {@code true}.
     * The caller formats a thrown {@code Exception} as a red error.
     *
     * @param ctx The shell resources for this connection.
     * @param line The full command line, already trimmed.
     * @return {@code true} when a command ran, or {@code false} when nothing matched.
     * @throws Exception The matched command threw.
     */
    public boolean execute(CommandContext ctx, String line) throws Exception {
        if (line == null) {
            return false;
        }
        String normalized = normalize(line);
        if (normalized.length() == 0) {
            return false;
        }
        CommandMatch match = findLongestMatch(normalized);
        if (match == null) {
            return false;
        }
        match.command.execute(ctx, match.args);
        return true;
    }

    /**
     * The {@code findByName} method looks up a command by its canonical name or an alias.
     * {@code help} uses this lookup.
     * If {@code name} is {@code null}, the method returns {@code null}.
     * The comparison uses {@link #normalize(String)} on the supplied name and on each registered name and alias.
     * A {@code null} alias list is skipped.
     * If no name or alias matches, the method returns {@code null}.
     *
     * @param name The user-supplied name.
     * @return The matching command, or {@code null} when nothing matches.
     */
    public Command findByName(String name) {
        if (name == null) {
            return null;
        }
        String normalized = normalize(name);
        for (int i = 0; i < commands.size(); i++) {
            Command command = commands.get(i);
            if (normalized.equals(normalize(command.getName()))) {
                return command;
            }
            String[] aliases = command.getAliases();
            if (aliases == null) {
                continue;
            }
            for (int a = 0; a < aliases.length; a++) {
                if (normalized.equals(normalize(aliases[a]))) {
                    return command;
                }
            }
        }
        return null;
    }

    /**
     * The {@code findLongestMatch} method chooses the longest registered name or alias.
     * A candidate matches when the line equals that name, or when the line begins with that name and a space.
     * Each command name is considered, and then each alias.
     * A {@code null} alias list is skipped.
     * A longer match replaces a shorter one.
     * If no candidate matches, the method returns {@code null}.
     *
     * @param normalized The line already passed through {@link #normalize(String)}.
     * @return The longest match, or {@code null} when nothing matches.
     */
    private CommandMatch findLongestMatch(String normalized) {
        CommandMatch best = null;
        int bestLength = -1;
        for (int i = 0; i < commands.size(); i++) {
            Command command = commands.get(i);
            int length = matchLength(normalized, command.getName());
            if (length > bestLength) {
                bestLength = length;
                best = new CommandMatch(command, argsAfter(normalized, length));
            }
            String[] aliases = command.getAliases();
            if (aliases == null) {
                continue;
            }
            for (int a = 0; a < aliases.length; a++) {
                int aliasLength = matchLength(normalized, aliases[a]);
                if (aliasLength > bestLength) {
                    bestLength = aliasLength;
                    best = new CommandMatch(command, argsAfter(normalized, aliasLength));
                }
            }
        }
        return best;
    }

    /**
     * The {@code matchLength} method measures one candidate name against the line.
     * If {@code name} is {@code null}, the method returns {@code -1}.
     * If the normalized name is empty, the method returns {@code -1}.
     * If the line equals the name, the method returns the name length.
     * If the line starts with the name and a space, the method returns the name length.
     * Any other line returns {@code -1}.
     *
     * @param normalized The line already passed through {@link #normalize(String)}.
     * @param name The candidate name or alias.
     * @return The matched character count, or {@code -1} when the candidate does not match.
     */
    private int matchLength(String normalized, String name) {
        if (name == null) {
            return -1;
        }
        String token = normalize(name);
        if (token.length() == 0) {
            return -1;
        }
        if (normalized.equals(token)) {
            return token.length();
        }
        if (normalized.startsWith(token + " ")) {
            return token.length();
        }
        return -1;
    }

    /**
     * The {@code argsAfter} method returns the text that follows a matched name.
     * If {@code nameLength} is negative, the method returns the empty string.
     * If {@code nameLength} is at least the line length, the method returns the empty string.
     * Otherwise the method returns the substring after the name, with surrounding spaces removed.
     *
     * @param normalized The full normalized line.
     * @param nameLength The matched name length.
     * @return The remainder after the name.
     */
    private String argsAfter(String normalized, int nameLength) {
        if (nameLength < 0 || nameLength >= normalized.length()) {
            return "";
        }
        return normalized.substring(nameLength).trim();
    }

    /**
     * The {@code normalize} method lowercases a line and collapses its whitespace.
     * Leading and trailing spaces are removed first.
     * A run of spaces or tab characters becomes one space.
     * That space is omitted when it would be the first character.
     * Other characters are appended in lower case.
     *
     * @param raw The raw line.
     * @return The normalized line.
     */
    public static String normalize(String raw) {
        StringBuilder out = new StringBuilder();
        boolean pendingSpace = false;
        String trimmed = raw.trim();
        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if (c == ' ' || c == '\t') {
                pendingSpace = true;
            } else {
                if (pendingSpace && out.length() > 0) {
                    out.append(' ');
                }
                pendingSpace = false;
                out.append(Character.toLowerCase(c));
            }
        }
        return out.toString();
    }

    // @aa-sdd PK-0544 PK-0546 docs/specs/java/org/casserole/casserole.spec.md
    /**
     * The {@code CommandMatch} type holds one matched command and the remainder of the line.
     * {@link CommandProcessor#findLongestMatch} builds the instance.
     * The type is private to the processor.
     */
    private static class CommandMatch {
        private final Command command;
        private final String args;

        /**
         * The constructor stores the matched command and the remainder.
         *
         * @param command The command that matched.
         * @param args The remainder of the line after the matched name.
         */
        private CommandMatch(Command command, String args) {
            this.command = command;
            this.args = args;
        }
    }
}
/*****************************************************************************************
 * End of Class: CommandProcessor.java 082426
 ****************************************************************************************/
