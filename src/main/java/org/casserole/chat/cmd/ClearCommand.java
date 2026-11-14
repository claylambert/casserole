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
 * The {@code ClearCommand} type is the {@code clear} command.
 * The alias is {@code history clear}.
 * The command drops user and assistant turns and keeps a configured system prompt.
 * A blank prompt leaves no system frame.
 * The session stays alive.
 *
 * @author clayl (2026-08-24)
 */
// @aa-sdd PK-730 docs/specs/common/stages.spec.md
// @aa-sdd PK-743 docs/specs/common/stages.spec.md
// @aa-sdd PK-745 docs/specs/common/stages.spec.md
// @aa-sdd PK-751 docs/specs/common/stages.spec.md
// @aa-sdd PK-754 docs/specs/common/stages.spec.md
// @aa-sdd PK-754 docs/specs/common/stages.spec.md
// @aa-sdd PK-755 docs/specs/common/stages.spec.md
public class ClearCommand implements Command {

    /**
     * The {@code getName} method returns the canonical name {@code clear}.
     *
     * @return The text {@code clear}.
     */
    @Override
    public String getName() {
        return "clear";
    }

    /**
     * The {@code getAliases} method returns the alternate name {@code history clear}.
     *
     * @return An array whose only entry is {@code history clear}.
     */
    @Override
    public String[] getAliases() {
        return new String[] { "history clear" };
    }

    /**
     * The {@code getHelp} method returns the one-line summary for {@code help}.
     * The summary says user and assistant history is cleared, a configured system prompt stays, and a blank prompt leaves no system frame.
     *
     * @return The one-line help text.
     */
    @Override
    public String getHelp() {
        return "Clear user/assistant history. A configured system prompt stays. A blank prompt leaves no system frame.";
    }

    /**
     * The {@code getDescription} method returns the longer help text.
     * The text says user and assistant messages are removed, the system prompt rule above still applies, and the session stays alive.
     *
     * @return The longer description.
     */
    @Override
    public String getDescription() {
        return "Removes user and assistant messages from this session. "
                + "A configured system prompt stays. A blank prompt leaves no system frame. The session stays alive.";
    }

    /**
     * The {@code execute} method clears the turns and writes {@code History cleared.}
     * The written line ends with a carriage return and a line feed.
     * The {@code args} text is ignored.
     *
     * @param ctx The shell resources for this connection.
     * @param args The remainder of the line after the command name.
     */
    @Override
    public void execute(CommandContext ctx, String args) {
        ctx.getSession().clearTurns();
        ctx.write("History cleared.\r\n");
    }
}
/*****************************************************************************************
 * End of Class: ClearCommand.java 082426
 ****************************************************************************************/
