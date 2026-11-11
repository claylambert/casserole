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

// @aa-sdd PK-0841 PK-0888 PK-0935 PK-0982 PK-1029 PK-1076 PK-1123 PK-1170 PK-1217 PK-1264 PK-1311 PK-1358 PK-1405 PK-1452 PK-1499 PK-1546 PK-1594 PK-1641 PK-1688 PK-1735 PK-1782 PK-1829 PK-1876 PK-1923 PK-1970 PK-2017 PK-2065 PK-2112 PK-2159 PK-2206 PK-2254 PK-2302 PK-2350 PK-2397 PK-2444 PK-2492 PK-2539 PK-2586 PK-2633 PK-2681 PK-2728 PK-2776 PK-2823 PK-2870 PK-2917 PK-2964 PK-3011 PK-3058 PK-3105 PK-3152 PK-3200 PK-3247 PK-3294 PK-3342 PK-3389 PK-3436 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-0670 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-3538 PK-3568 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3539 PK-3549 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3569 docs/specs/java/org/aetheris/ssh/ssh.spec.md
/**
 * The {@code CommandContext} type is the set of resources one command may use.
 * The chat shell implements the type for the connection that typed the command.
 * The shell supplies the session, the writers, the model selection, and the request to close.
 *
 * @author clayl (2026-08-24)
 */
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
     * // @aa-sdd PK-0484 PK-0536 docs/specs/java/org/casserole/casserole.spec.md
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
