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
import org.casserole.ssh.shell.Ansi;
import org.springframework.ai.chat.messages.MessageType;

// @aa-sdd PK-1053 docs/specs/common/formal-plan.spec.md FINESSE:f-39
/**
 * The {@code StatusCommand} type is the {@code status} command.
 * The command prints the session state and the selected model.
 * The command has no aliases.
 *
 * @author clayl (2026-08-26)
 */
// @aa-sdd PK-324 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-327 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
// @aa-sdd PK-329 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
public class StatusCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code status}.
     *
     * @return The text {@code status}.
     */
    @Override
    public String getName() {
        return "status";
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
     * The summary says the command shows session state, message counts, mode, and the selected model.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Show session state, message counts, mode, and the selected model.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says the command prints whether chat is active or idle, the shell mode, and the counts by message type.
     * The text also names the SSH user, the selected family, and the options stored for that family.
     * SSH is the secure-shell protocol the client used to connect.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Prints whether chat is active or idle, the current shell mode, "
                + "counts by message type, the SSH user, the selected family, "
                + "and the options stored for that family.";
    }

    /**
     * The {@code execute} method prints the session lines.
     * The {@code chat} line is {@code active} or {@code idle}.
     * The {@code mode} line is the shell mode name.
     * The {@code conversation} line is the conversation id.
     * If the region id is present, the {@code region-uuid} line is that id.
     * The {@code ssh-user} line is the SSH login name.
     * {@link CommandContext#writeModelStatus} prints the selected family and its stored options.
     * The {@code messages} line reports the total and the system, user, and assistant counts.
     * The {@code roux} line is {@link ChatSession#memoryStatusLine}.
     * Roux is the store that holds the conversation messages.
     * The {@code args} text is ignored.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     */
    @Override
    public void execute(CommandContext ctx, String args) {
        ChatSession session = ctx.getSession();
        line(ctx, "chat", session.isActive() ? "active" : "idle");
        line(ctx, "mode", ctx.getShellMode().name());
        line(ctx, "conversation", session.getConversationId());
        if (session.getRegionUuid() != null) {
            line(ctx, "region-uuid", session.getRegionUuid().toString());
        }
        line(ctx, "ssh-user", ctx.getSshUsername());
        ctx.writeModelStatus();
        line(ctx, "messages", "total=" + session.getMessages().size()
                + " system=" + session.countByType(MessageType.SYSTEM)
                + " user=" + session.countByType(MessageType.USER)
                + " assistant=" + session.countByType(MessageType.ASSISTANT));
        line(ctx, "roux", session.memoryStatusLine());
    }

    /**
     * The {@code line} method writes one key and value through {@link Ansi#kv}.
     * The line ends with a carriage return and a line feed.
     *
     * @param ctx The shell resources that receive the line.
     * @param key The label on the left.
     * @param value The text on the right.
     */
    private static void line(CommandContext ctx, String key, String value) {
        ctx.write(Ansi.kv(key, value) + "\r\n");
    }
}
/*****************************************************************************************
 * End of Class: StatusCommand.java 082626
 ****************************************************************************************/
