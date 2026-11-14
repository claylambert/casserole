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

package org.casserole.ssh.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.aetheris.wire.AmwpToon;
import org.junit.jupiter.api.Test;

/**
 * The class checks the think switch that {@link ChatShell#seatConsoleGrokThink} writes onto an {@link AmwpToon.ExecuteBody}.
 * The test does not open a pseudo-terminal.
 * // @aa-sdd L40 aa-sdd-source-prose.md#ConsoleGrokThinkTest
 *
 * @author clayl (2026-10-29)
 */
// @aa-sdd GK-2445 rust-aetheris-client.draft.001.md
// @aa-sdd GK-2487 rust-aetheris-ssh-grok-think.draft.001.md
class ConsoleGrokThinkTest {

    /**
     * The method arranges an {@link AmwpToon.ExecuteBody} whose {@code thinkStream} flag starts false.
     * The method calls {@link ChatShell#seatConsoleGrokThink} with a null string, with {@code false}, and with {@code true} surrounded by spaces.
     * A null string leaves {@code thinkStream} false and leaves {@code think} empty.
     * The string {@code false} leaves {@code thinkStream} false and leaves {@code think} empty.
     * The string {@code true} with surrounding spaces sets {@code thinkStream} true.
     * That same call sets {@code think} to {@code true}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aGrokThinkSwitchStreamsTheTurn() {
        AmwpToon.ExecuteBody req = new AmwpToon.ExecuteBody();
        req.thinkStream = false;
        ChatShell.seatConsoleGrokThink(req, null);
        assertFalse(req.thinkStream);
        assertEquals("", req.think);
        ChatShell.seatConsoleGrokThink(req, "false");
        assertFalse(req.thinkStream);
        assertEquals("", req.think);
        ChatShell.seatConsoleGrokThink(req, " true ");
        assertTrue(req.thinkStream);
        assertEquals("true", req.think);
    }
}
