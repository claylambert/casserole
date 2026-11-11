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
