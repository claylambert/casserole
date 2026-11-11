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

// @aa-sdd PK-0672 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-3540 PK-3570 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3571 docs/specs/java/org/aetheris/ssh/ssh.spec.md
/**
 * The {@code SelectModelCommand} type is the {@code select-model} command.
 * A bare command opens the Model Config modal for this console.
 * One family word switches that family and keeps the options stored for it.
 * The opening signal is a private OSC sequence.
 * OSC is an operating-system command, which is a terminal control sequence wrapped in ESC and BEL.
 * A raw terminal that does not understand the sequence still shows the receipt line {@code Model Config}.
 * The command has no aliases.
 *
 * @author clayl (2026-10-22)
 */
public class SelectModelCommand implements Command {

    private static final char ESC = 0x1B;
    private static final char BEL = 0x07;

    /**
     * The {@code getName} method returns the canonical name {@code select-model}.
     *
     * @return The text {@code select-model}.
     */
    @Override
    public String getName() {
        return "select-model";
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
     * The summary says the command opens the modal, or switches {@code grok}, {@code ollama}, or {@code pool}.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Open the modal, or switch grok, ollama, or pool.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says a bare {@code select-model} opens the Model Config modal.
     * The text says one family word switches the family and keeps the options stored for it.
     * The text says any other argument is refused.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "A bare select-model opens the Model Config modal. "
                + "One family word switches the family and keeps the options stored for it. "
                + "Any other argument is refused.";
    }

    /**
     * The {@code execute} method opens the modal or switches the family.
     * A {@code null} remainder is treated as empty.
     * If the trimmed remainder is empty, the method writes a private OSC sequence and then {@code Model Config}.
     * The sequence is ESC, {@code ]777;select-model;}, the model field, {@code ;}, the think field, and BEL.
     * {@link #oscField} strips separators from those two fields.
     * If the word is not {@code grok}, {@code ollama}, or {@code pool}, the method throws {@code Exception} with {@code usage: select-model {grok, ollama, pool}}.
     * If {@link CommandContext#selectConsoleFamily} returns {@code false}, the method throws the same usage exception.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The word was not a family, or the family was not stored.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        String word = args == null ? "" : args.trim();
        if (word.length() == 0) {
            String model = ctx.getModelName();
            String think = ctx.getConsoleThink();
            ctx.write("\u001b]777;select-model;" + oscField(model) + ";" + oscField(think) + "\u0007");
            ctx.write("Model Config\r\n");
            return;
        }
        if (!"grok".equals(word) && !"ollama".equals(word) && !"pool".equals(word)) {
            throw new Exception("usage: select-model {grok, ollama, pool}");
        }
        if (!ctx.selectConsoleFamily(word)) {
            throw new Exception("usage: select-model {grok, ollama, pool}");
        }
    }

    /**
     * The {@code oscField} method copies one value into an OSC field.
     * A {@code null} or empty value returns the empty string.
     * The characters semicolon, carriage return, line feed, ESC, and BEL are dropped.
     * The stored model and think strings are not changed.
     *
     * @param raw The model name or the think string.
     * @return The field text with those separators removed.
     */
    private static String oscField(String raw) {
        if (raw == null || raw.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == ';' || c == '\r' || c == '\n' || c == ESC || c == BEL) {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
/*****************************************************************************************
 * End of Class: SelectModelCommand.java 102226
 ****************************************************************************************/
