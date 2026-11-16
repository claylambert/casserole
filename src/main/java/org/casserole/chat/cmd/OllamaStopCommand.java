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

/**
 * The {@code OllamaStopCommand} type is the {@code ollama stop} command.
 * The command unloads the configured Ollama model from memory.
 * Ollama is the program that runs a local model.
 * The command has no aliases.
 * The model name comes from the shell, which reads the casserole chat setting or {@code spring.ai.ollama.chat.model}.
 *
 * @author clayl (2026-08-25)
 */
public class OllamaStopCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code ollama stop}.
     *
     * @return The text {@code ollama stop}.
     */
    @Override
    public String getName() {
        return "ollama stop";
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
     * The summary says the command unloads the configured Ollama model from memory.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Unload the configured Ollama model from memory.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the command runs {@code ollama stop} plus the model through a process builder, not a shell.
     * The text says the model name is the casserole chat setting or {@code spring.ai.ollama.chat.model}.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Runs `ollama stop <model>` via ProcessBuilder (no shell). "
                + "The model name is casserole.chat / spring.ai.ollama.chat.model.";
    }

    /**
     * The {@code execute} method unloads the configured model.
     * The method writes {@code Stopping } plus the model name plus {@code ...} before the process starts.
     * {@link OllamaProcess#run} is called with the tokens {@code stop} and the model name, and with a timeout of 30000 milliseconds.
     * The captured output is written after {@link LineDiscipline#toCrlf} turns bare line feeds into carriage-return pairs.
     * If the captured output does not already end with a line feed, the method writes one more carriage return and line feed.
     * The {@code args} text is ignored.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The Ollama process timed out, failed, or rejected its arguments.
     */
    @Override
    public void execute(CommandContext ctx, String args) throws Exception {
        String model = ctx.getModelName();
        ctx.write("Stopping " + model + " ...\r\n");
        String out = OllamaProcess.run(new String[] { "stop", model }, 30000L);
        ctx.write(LineDiscipline.toCrlf(out));
        if (!out.endsWith("\n")) {
            ctx.write("\r\n");
        }
    }
}
/*****************************************************************************************
 * End of Class: OllamaStopCommand.java 082526
 ****************************************************************************************/
