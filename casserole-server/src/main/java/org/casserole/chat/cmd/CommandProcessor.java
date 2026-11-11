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

// @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
// @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
// @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
