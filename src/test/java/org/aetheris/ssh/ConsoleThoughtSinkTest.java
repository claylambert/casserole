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

package org.aetheris.ssh;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * The class checks {@link AmwpFacade#consoleCarriesThought}.
 * A true result attaches the nil-cell thought sink.
 * A nil cell is the empty cell that holds a thought apart from the reply text.
 * The test does not build a multiplexer.
 * // @aa-sdd L40 aa-sdd-source-prose.md#ConsoleThoughtSinkTest
 *
 * @author clayl (2026-10-29)
 */
// @aa-sdd GK-2447 rust-aetheris-client.draft.001.md
// @aa-sdd GK-2489 rust-aetheris-ssh-grok-think.draft.001.md
class ConsoleThoughtSinkTest {

    /**
     * The method arranges no objects.
     * The method calls {@link AmwpFacade#consoleCarriesThought} for the provider strings {@code grok}, {@code ollama}, and {@code pool}.
     * {@code grok} returns false for a null string, an empty string, {@code false}, and {@code high}.
     * {@code grok} returns true for {@code true} and for {@code true} with surrounding spaces.
     * {@code ollama} returns true for {@code high} and for {@code true}.
     * {@code ollama} returns false for {@code false} and for an empty string.
     * {@code pool} returns false for {@code true}.
     *
     * @return The method returns nothing.
     */
    @Test
    void grokCarriesAThoughtWhenTheSwitchIsOn() {
        assertFalse(AmwpFacade.consoleCarriesThought("grok", null));
        assertFalse(AmwpFacade.consoleCarriesThought("grok", ""));
        assertFalse(AmwpFacade.consoleCarriesThought("grok", "false"));
        assertFalse(AmwpFacade.consoleCarriesThought("grok", "high"));
        assertTrue(AmwpFacade.consoleCarriesThought("grok", "true"));
        assertTrue(AmwpFacade.consoleCarriesThought("grok", " true "));
        assertTrue(AmwpFacade.consoleCarriesThought("ollama", "high"));
        assertTrue(AmwpFacade.consoleCarriesThought("ollama", "true"));
        assertFalse(AmwpFacade.consoleCarriesThought("ollama", "false"));
        assertFalse(AmwpFacade.consoleCarriesThought("ollama", ""));
        assertFalse(AmwpFacade.consoleCarriesThought("pool", "true"));
    }
}
