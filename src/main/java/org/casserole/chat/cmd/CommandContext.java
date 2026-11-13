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
import org.casserole.chat.ShellMode;
import org.casserole.config.CasseroleProperties;

// @aa-sdd GK-0953 aetheris-stage-2-finessing.001.md FINESSE:f-222
// @aa-sdd PK-1056 docs/specs/common/formal-plan.spec.md FINESSE:f-36
// @aa-sdd PK-1054 docs/specs/common/formal-plan.spec.md FINESSE:f-38
// @aa-sdd PK-1053 docs/specs/common/formal-plan.spec.md FINESSE:f-39
// @aa-sdd PK-1052 docs/specs/common/formal-plan.spec.md FINESSE:f-40
/**
 * The {@code CommandContext} type is the set of resources one command may use.
 * The chat shell implements the type for the connection that typed the command.
 * The shell supplies the session, the writers, the model selection, and the request to close.
 *
 * @author clayl (2026-08-24)
 */
// @aa-sdd GK-2087 aetheris-pre-plan-f-36.001.md
// @aa-sdd PK-320 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-760 docs/specs/common/stages.spec.md
// @aa-sdd PK-761 docs/specs/common/stages.spec.md
// @aa-sdd PK-762 docs/specs/common/stages.spec.md
// @aa-sdd PK-763 docs/specs/common/stages.spec.md
// @aa-sdd PK-670 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-672 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-674 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-675 docs/specs/common/formal-plan.spec.md
public interface CommandContext {

    /**
     * The {@code getSession} method returns the chat session for this connection.
     *
     * @return The {@link ChatSession} bound to this connection.
     */
    ChatSession getSession();

    /**
     * The {@code getShellMode} method returns the current shell mode.
     * The mode says whether Enter sends a chat turn, runs a command, or stays idle.
     *
     * @return The current {@link ShellMode}.
     */
    ShellMode getShellMode();

    /**
     * The {@code getProperties} method returns the bound program configuration.
     *
     * @return The {@link CasseroleProperties} for this process.
     */
    CasseroleProperties getProperties();

    /**
     * The {@code getModelName} method returns the configured Ollama model name.
     * Ollama is the program that runs a local model.
     * The result stays the Ollama id even when another family is selected.
     *
     * @return The configured Ollama model name, when that name is known.
     */
    String getModelName();

    /**
     * The {@code getConsoleThink} method returns the think string for this console.
     * The think string is the setting that asks the model to expose its reasoning.
     * The empty string means that setting is off.
     * The string is not a column in the conversation store.
     *
     * @return The trimmed think string, or the empty string when thinking is off.
     */
    String getConsoleThink();

    /**
     * The {@code getConsoleProvider} method returns the family word for this console.
     * The family word is {@code ollama}, {@code grok}, or {@code pool}.
     * The missing word is reported as {@code ollama}.
     *
     * @return The family word, or {@code ollama} when no other family is stored.
     */
    String getConsoleProvider();

    /**
     * The {@code writeModelStatus} method prints the selected family, the target, and that family's stored options.
     * {@link #getModelName()} stays the Ollama id while this method prints the selected family.
     */
    void writeModelStatus();

    /**
     * The {@code selectConsoleFamily} method switches the console family.
     * The accepted words are {@code ollama}, {@code grok}, and {@code pool}.
     * The stored options for each family stay in place.
     * An unknown word writes nothing and is not stored.
     *
     * @param family The family word to store.
     * @return {@code true} when the family was stored, or {@code false} when the word was not stored.
     */
    boolean selectConsoleFamily(String family);

    /**
     * The {@code getSshUsername} method returns the SSH login name for this connection.
     * SSH is the secure-shell protocol the client used to connect.
     * The empty string means no login name is stored.
     *
     * @return The SSH login name, or the empty string.
     */
    String getSshUsername();

    /**
     * The {@code getProcessor} method returns the processor that owns this command set.
     * {@code help} uses that processor to list and look up commands.
     *
     * @return The {@link CommandProcessor} for this connection.
     */
    CommandProcessor getProcessor();

    /**
     * The {@code write} method sends default-color text to the VT100 standard output.
     * VT100 is the terminal control set the chat shell paints with.
     * The method does not add a carriage return or a line feed.
     *
     * @param text The text to write.
     */
    void write(String text);

    /**
     * The {@code writeError} method sends one error line in red.
     * The implementation adds the color and the newline.
     *
     * @param text The message to write.
     */
    void writeError(String text);

    /**
     * The {@code setChatActive} method switches this connection between chat and idle.
     * {@code chat start} and {@code chat stop} call this method.
     * If {@code active} is {@code true}, later Enter lines may go to the language model.
     * The language model is the program that writes the assistant reply.
     * If {@code active} is {@code false}, Enter does not call the language model.
     *
     * @param active {@code true} when chat turns should go to the language model, or {@code false} when they should not.
     */
    void setChatActive(boolean active);

    /**
     * The {@code requestExit} method closes the SSH session after the command returns.
     * SSH is the secure-shell protocol the client used to connect.
     */
    void requestExit();

    /**
     * The {@code setModelName} method stores the live model name for this shell.
     * The {@code swap} command calls this method when the console family is Ollama.
     * // @aa-sdd G-15 aetheris-stages-1-3.finalize.md
     *
     * @param modelName The Ollama model tag to store.
     */
    void setModelName(String modelName);

    /**
     * The {@code collectMessageEdit} method reads following lines as the body of {@code message edit}.
     * Reading stops at a line whose text is {@code <.>}.
     * The {@code seq} value is the stored sequence number of the message to revise.
     *
     * @param seq The stored sequence number of the message to revise.
     */
    void collectMessageEdit(long seq);
}
/*****************************************************************************************
 * End of Class: CommandContext.java 082426
 ****************************************************************************************/
