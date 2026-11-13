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

// @aa-sdd L40 app-design.md
/**
 * The {@code Command} type is one command the chat shell can run.
 * The type supplies the match text, the help text, and the execute logic.
 * {@link CommandProcessor} matches the name or an alias and then calls {@link #execute}.
 *
 * @author clayl (2026-08-24)
 */
// @aa-sdd GK-1725 aetheris-technical-specification.finalize.md
// @aa-sdd PK-309 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-834 docs/specs/common/rust.spec.md
// @aa-sdd PK-310 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-311 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-312 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-864 docs/specs/common/stages.spec.md
// @aa-sdd PK-315 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-316 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd GK-2157 bugs-fixed.md
// @aa-sdd PK-325 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-326 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd GK-2428 rust-aetheris-client-finesse.draft.001.md
// @aa-sdd PK-328 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-317 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-319 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-722 docs/specs/common/server.spec.md
public interface Command {

    /**
     * The {@code getName} method returns the canonical command name.
     * The name may contain spaces, as in {@code chat start}.
     *
     * @return The canonical name.
     */
    String getName();

    /**
     * The {@code getAliases} method returns the alternate names for this command.
     * The result is never {@code null}.
     * The empty array means the command has no alternate name.
     *
     * @return The alternate names, and never {@code null}.
     */
    String[] getAliases();

    /**
     * The {@code getHelp} method returns the one-line summary printed by {@code help}.
     *
     * @return The one-line summary.
     */
    String getHelp();

    /**
     * The {@code getDescription} method returns the longer text printed by {@code help} followed by a name.
     *
     * @return The longer description.
     */
    String getDescription();

    /**
     * The {@code execute} method runs this command against the current shell.
     * The {@code args} text is the remainder of the line after the command name.
     * {@link CommandProcessor} passes a remainder that is never {@code null}.
     * The processor reports a thrown {@code Exception} as a red VT100 error.
     * VT100 is the terminal control set the chat shell paints with.
     *
     * @param ctx The session, the writers, and the lifecycle hooks for this connection.
     * @param args The remainder of the line after the command name.
     * @throws Exception The command rejected the line, or a step it called failed.
     */
    void execute(CommandContext ctx, String args) throws Exception;
}
/*****************************************************************************************
 * End of Class: Command.java 082426
 ****************************************************************************************/
