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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * The class checks {@link ChatShell#softReturn}.
 * A lone line feed keeps the input line.
 * A carriage return still submits.
 * A line feed that follows a carriage return stays swallowed.
 * // @aa-sdd L40 aa-sdd-source-prose.md#SoftReturnTest
 *
 * @author clayl (2026-10-29)
 */
// @aa-sdd GK-2451 rust-aetheris-client.draft.001.md
// @aa-sdd GK-2482 rust-aetheris-soft-return-finesse.draft.001.md
// @aa-sdd GK-2483 rust-aetheris-soft-return-finesse.draft.001.md
class SoftReturnTest {

    /**
     * The method arranges no objects.
     * The method calls {@link ChatShell#softReturn} with a line feed, a carriage return, and the letter {@code a}.
     * A line feed returns true when the previous character was not a carriage return.
     * A line feed returns false when the previous character was a carriage return.
     * A carriage return returns false.
     * The letter {@code a} returns false.
     *
     * @return The method returns nothing.
     */
    @Test
    void aLoneLineFeedKeepsTheLine() {
        assertTrue(ChatShell.softReturn('\n', false));
        assertFalse(ChatShell.softReturn('\n', true));
        assertFalse(ChatShell.softReturn('\r', false));
        assertFalse(ChatShell.softReturn('a', false));
    }
}
