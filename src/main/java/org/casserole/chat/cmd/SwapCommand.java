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

// @aa-sdd PK-1054 docs/specs/common/formal-plan.spec.md FINESSE:f-38
// @aa-sdd PK-1251 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
/**
 * The {@code SwapCommand} type is the {@code swap} command.
 * The form is {@code swap} followed by a from word and a to word.
 * The command stores the to word when this console family is Ollama, or when no family is stored.
 * Ollama is the program that runs a local model.
 * The from word is required.
 * The from word is not sent to a process.
 * A family of {@code grok} or {@code pool} leaves the stored target unchanged.
 * // @aa-sdd G-15 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd PK-751 docs/specs/common/stages.spec.md
// @aa-sdd PK-754 docs/specs/common/stages.spec.md
// @aa-sdd PK-754 docs/specs/common/stages.spec.md
// @aa-sdd PK-755 docs/specs/common/stages.spec.md
// @aa-sdd PK-310 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-697 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-321 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
public class SwapCommand implements Command {

    private final String baseUrl;

    /**
     * The {@code numCtx} field is the token window stored from configuration.
     * A value of {@code 0} means {@code num_ctx} would be omitted.
     * {@link #execute} does not read this field.
     */
    private final int numCtx;

    /**
     * The constructor stores the Ollama HTTP base and the token window.
     * If {@code baseUrl} is {@code null} or empty, the stored base is {@code http://localhost:11434}.
     * {@link #trimSlash} removes one trailing slash from any other base.
     * {@link #execute} does not read the stored base or the stored window.
     *
     * @param baseUrl The Ollama HTTP base.
     * @param numCtx The token window, where {@code 0} means {@code num_ctx} is not sent.
     */
    public SwapCommand(String baseUrl, int numCtx) {
        this.baseUrl = baseUrl == null || baseUrl.length() == 0
                ? "http://localhost:11434" : trimSlash(baseUrl);
        this.numCtx = numCtx;
    }

    /**
     * The {@code getName} method returns the canonical name {@code swap}.
     *
     * @return The text {@code swap}.
     */
    @Override
    public String getName() {
        return "swap";
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
     * The summary says {@code swap} takes a from word and a to word, and stores the to word when the console family is {@code ollama}.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "swap <from> <to> stores to when the console family is ollama.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the from word is required and is not sent to a process.
     * The text says family {@code ollama} stores the to word.
     * The text says family {@code grok} or {@code pool} leaves the target.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "The from word is required and is not sent to a process. "
                + "Family ollama stores the to word. Family grok or pool leaves the target.";
    }

    /**
     * The {@code execute} method stores a model name or reports the current family.
     * A {@code null} remainder is treated as empty.
     * The line must contain a from word, a space, and a to word.
     * If either word is missing, the method throws {@code Exception} with the message {@code usage: swap <from> <to>}.
     * If the console family is {@code null}, empty, or {@code ollama}, the method stores the to word and writes {@code model } plus that word.
     * Any other family writes {@code The console family is } plus the family word and does not change the stored target.
     * Each written line ends with a carriage return and a line feed.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The from word or the to word was missing.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        String trimmed = args == null ? "" : args.trim();
        int space = trimmed.indexOf(' ');
        if (space <= 0 || space >= trimmed.length() - 1) {
            throw new Exception("usage: swap <from> <to>");
        }
        String from = trimmed.substring(0, space).trim();
        String to = trimmed.substring(space + 1).trim();
        if (from.length() == 0 || to.length() == 0) {
            throw new Exception("usage: swap <from> <to>");
        }
        String family = ctx.getConsoleProvider();
        if (family == null || family.length() == 0 || "ollama".equals(family)) {
            ctx.setModelName(to);
            ctx.write("model " + to + "\r\n");
            return;
        }
        ctx.write("The console family is " + family + "\r\n");
    }

    /**
     * The {@code trimSlash} method removes one trailing slash from a base URL.
     * If the text does not end with {@code /}, the method returns it unchanged.
     *
     * @param url The base URL.
     * @return The URL without a trailing slash.
     */
    private static String trimSlash(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }
}
/*****************************************************************************************
 * End of Class: SwapCommand.java 090226
 ****************************************************************************************/
