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

package org.aetheris.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;

// @aa-sdd PK-1152 docs/specs/common/formal-plan.spec.md FINESSE:f-104
/**
 * The class checks the console-family switch on {@link AetherisNotebookPanel} and the thought colors on {@link ThinkStrip}.
 * Grok opens when the think word is {@code true}.
 * The test does not open a window.
 *
 * @author clayl (2026-10-29)
 */
// @aa-sdd GK-2446 rust-aetheris-client.draft.001.md
// @aa-sdd GK-2488 rust-aetheris-ssh-grok-think.draft.001.md
// @aa-sdd GK-2529 rust-aetheris-vt100-think-ink.draft.001.md
// @aa-sdd PK-1152 docs/specs/common/formal-plan.spec.md
class ConsoleThoughtGateTest {

    /**
     * The method arranges a sapphire color and calls {@link AetherisNotebookPanel#consoleFamilyOpen}, {@link AetherisNotebookPanel#consoleFamilyTag}, and the {@link ThinkStrip} color methods.
     * {@code grok} stays closed when the pool flag is true, even if the ollama word is {@code true}.
     * {@code grok} stays closed when the grok word is {@code false}.
     * {@code grok} opens when the grok word is {@code true} or {@code true} with surrounding spaces.
     * {@code ollama} opens when the ollama word is {@code high}.
     * {@code ollama} stays closed when the ollama word is {@code false}.
     * An empty provider opens when the ollama word is {@code true}.
     * {@code pool} opens when the pool flag is true.
     * {@code pool} stays closed when the pool flag is false.
     * {@link AetherisNotebookPanel#consoleFamilyTag} returns the grok model {@code grok-4.3} for {@code grok}.
     * A blank grok model returns the word {@code grok}.
     * {@code ollama} returns the ollama model {@code qwen} when that model is set.
     * An empty ollama model returns the word {@code ollama}.
     * {@code pool} returns the word {@code pool}.
     * {@link ThinkStrip#SELECTION_SAPPHIRE} equals red {@code 0x2e}, green {@code 0x86}, and blue {@code 0xff}.
     * {@link ThinkStrip#VT_THINK_BODY} equals that sapphire lightened by {@code 33}.
     * The body channels are {@code 115}, {@code 174}, and {@code 255}.
     * {@link ThinkStrip#bodyColor} returns that body color for both true and false.
     *
     * @return The method returns nothing.
     */
    @Test
    void grokOpensWhenTheThinkWordIsTrue() {
        assertFalse(AetherisNotebookPanel.consoleFamilyOpen("grok", true, "", "true"));
        assertFalse(AetherisNotebookPanel.consoleFamilyOpen("grok", false, "false", "true"));
        assertTrue(AetherisNotebookPanel.consoleFamilyOpen("grok", false, "true", ""));
        assertTrue(AetherisNotebookPanel.consoleFamilyOpen("grok", false, " true ", ""));
        assertTrue(AetherisNotebookPanel.consoleFamilyOpen("ollama", false, "", "high"));
        assertFalse(AetherisNotebookPanel.consoleFamilyOpen("ollama", false, "true", "false"));
        assertTrue(AetherisNotebookPanel.consoleFamilyOpen("", false, "", "true"));
        assertTrue(AetherisNotebookPanel.consoleFamilyOpen("pool", true, "", ""));
        assertFalse(AetherisNotebookPanel.consoleFamilyOpen("pool", false, "true", "true"));
        assertEquals("grok-4.3", AetherisNotebookPanel.consoleFamilyTag("grok", "grok-4.3", "qwen"));
        assertEquals("grok", AetherisNotebookPanel.consoleFamilyTag("grok", "  ", "qwen"));
        assertEquals("qwen", AetherisNotebookPanel.consoleFamilyTag("ollama", "grok-4.3", "qwen"));
        assertEquals("ollama", AetherisNotebookPanel.consoleFamilyTag("ollama", "grok-4.3", ""));
        assertEquals("pool", AetherisNotebookPanel.consoleFamilyTag("pool", "grok-4.3", "qwen"));
        Color sapphire = new Color(0x2e, 0x86, 0xff);
        assertEquals(sapphire, ThinkStrip.SELECTION_SAPPHIRE);
        assertEquals(ThinkStrip.lightenTowardWhite(sapphire, 33), ThinkStrip.VT_THINK_BODY);
        assertEquals(115, ThinkStrip.VT_THINK_BODY.getRed());
        assertEquals(174, ThinkStrip.VT_THINK_BODY.getGreen());
        assertEquals(255, ThinkStrip.VT_THINK_BODY.getBlue());
        assertEquals(ThinkStrip.VT_THINK_BODY, ThinkStrip.bodyColor(true));
        assertEquals(ThinkStrip.VT_THINK_BODY, ThinkStrip.bodyColor(false));
    }
}
