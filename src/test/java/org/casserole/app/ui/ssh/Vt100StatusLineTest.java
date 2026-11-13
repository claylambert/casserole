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

package org.casserole.app.ui.ssh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * The class checks the status text from {@link Vt100StatusLine}.
 * The test does not open a window.
 * The test does not open a pseudo-terminal.
 * // @aa-sdd L40 aa-sdd-source-prose.md#Vt100StatusLineTest
 *
 * @author clayl (2026-10-31)
 */
// @aa-sdd GK-1184 aetheris-audit-slice-b.draft.001.md
// @aa-sdd PK-775 docs/specs/common/stages.spec.md
// @aa-sdd PK-668 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-669 docs/specs/common/formal-plan.spec.md
// @aa-sdd GK-2073 aetheris-pre-plan-f-228.001.md
class Vt100StatusLineTest {

    /**
     * The method arranges the engine token {@code ollama/deepseek-r1:7b} and air-gap {@code true}.
     * The method calls {@link Vt100StatusLine#facts} and {@link Vt100StatusLine#modeWord}.
     * The mode token {@code vt100} returns {@code vault: disk  VT100  air-gap: true  engine: ollama/deepseek-r1:7b}.
     * That line does not contain {@code AMWP}.
     * {@code AMWP} is the wire-protocol token this line must not print.
     * That line does not contain {@code raw}.
     * The mode token {@code pty} returns the same line with {@code PTY} in place of {@code VT100}.
     * That pty line does not contain {@code AMWP}.
     * {@link Vt100StatusLine#modeWord} returns {@code PTY} for {@code PTY}.
     * An empty token returns {@code VT100}.
     * A null token returns {@code VT100}.
     *
     * @return The method returns nothing.
     */
    @Test
    void ptyAndVt100NameTheWire() {
        String vt = Vt100StatusLine.facts("vt100", "true", "ollama/deepseek-r1:7b");
        assertEquals("vault: disk  VT100  air-gap: true  engine: ollama/deepseek-r1:7b", vt);
        assertFalse(vt.contains("AMWP"));
        assertFalse(vt.contains("raw"));
        String pty = Vt100StatusLine.facts("pty", "true", "ollama/deepseek-r1:7b");
        assertEquals("vault: disk  PTY  air-gap: true  engine: ollama/deepseek-r1:7b", pty);
        assertFalse(pty.contains("AMWP"));
        assertEquals("PTY", Vt100StatusLine.modeWord("PTY"));
        assertEquals("VT100", Vt100StatusLine.modeWord(""));
        assertEquals("VT100", Vt100StatusLine.modeWord(null));
    }

    /**
     * The method arranges blank air-gap and engine strings.
     * The method calls {@link Vt100StatusLine#facts} and {@link Vt100StatusLine#engineWord}.
     * Mode {@code PTY} with an empty air-gap and an empty engine returns {@code vault: disk  PTY  air-gap: unset  engine: ollama}.
     * Mode {@code pty} with a null air-gap and a blank engine returns that same line.
     * {@link Vt100StatusLine#engineWord} trims {@code grok/m} down to {@code grok/m}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBlankAirAndEngineUseTheStandingWords() {
        assertEquals(
                "vault: disk  PTY  air-gap: unset  engine: ollama",
                Vt100StatusLine.facts("PTY", "", ""));
        assertEquals(
                "vault: disk  PTY  air-gap: unset  engine: ollama",
                Vt100StatusLine.facts("pty", null, "   "));
        assertEquals("grok/m", Vt100StatusLine.engineWord("  grok/m  "));
    }

    /**
     * The method arranges the unknown mode token {@code nope} and the engine token {@code grok/m}.
     * The method calls {@link Vt100StatusLine#facts} and {@link SshSessionMode#fromPropertyValue}.
     * The facts line is {@code vault: disk  VT100  air-gap: true  engine: grok/m}.
     * {@link SshSessionMode#VT100} reports the term type {@code vt100}.
     * {@link SshSessionMode#PTY} reports the term type {@code xterm-256color}.
     * A null property value returns {@link SshSessionMode#VT100}.
     * The value {@code nope} returns {@link SshSessionMode#VT100}.
     * The value {@code PTY} returns {@link SshSessionMode#PTY}.
     *
     * @return The method returns nothing.
     */
    @Test
    void anUnknownTokenStaysVt100() {
        assertEquals(
                "vault: disk  VT100  air-gap: true  engine: grok/m",
                Vt100StatusLine.facts("nope", "true", "grok/m"));
        assertEquals("vt100", SshSessionMode.VT100.getTermType());
        assertEquals("xterm-256color", SshSessionMode.PTY.getTermType());
        assertEquals(SshSessionMode.VT100, SshSessionMode.fromPropertyValue(null));
        assertEquals(SshSessionMode.VT100, SshSessionMode.fromPropertyValue("nope"));
        assertEquals(SshSessionMode.PTY, SshSessionMode.fromPropertyValue("PTY"));
    }

    /**
     * The method arranges link words and the address {@code 10.0.0.2} with port {@code 22}.
     * The method calls {@link Vt100StatusLine#west}.
     * {@code Connecting} returns {@code Connecting}.
     * {@code Connected} returns {@code Connected 10.0.0.2:22}.
     * A status that already contains {@code Connected 10.0.0.2:22} returns that same text.
     * {@code Disconnected} returns {@code Disconnected}.
     * {@code Disconnected: read thread did not stop} returns {@code Disconnected}, an em dash, and {@code read thread did not stop}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theLinkWordKeepsTheHostAndTheReason() {
        assertEquals("Connecting", Vt100StatusLine.west("Connecting", "10.0.0.2", "22"));
        assertEquals("Connected 10.0.0.2:22", Vt100StatusLine.west("Connected", "10.0.0.2", "22"));
        assertEquals(
                "Connected 10.0.0.2:22",
                Vt100StatusLine.west("Connected 10.0.0.2:22", "other", "1"));
        assertEquals("Disconnected", Vt100StatusLine.west("Disconnected", "h", "22"));
        assertEquals(
                "Disconnected \u2014 read thread did not stop",
                Vt100StatusLine.west("Disconnected: read thread did not stop", "h", "22"));
    }

    /**
     * The method arranges vt100 facts for {@code ollama/deepseek-r1:7b} and the west text for {@code Connected}.
     * The method calls {@link Vt100StatusLine#join}.
     * A true mood flag places {@code mood active} after the connected address and before the vt100 facts.
     * A false mood flag omits {@code mood active}.
     * A true mood flag with pty facts places {@code mood active} and uses {@code PTY}.
     *
     * @return The method returns nothing.
     */
    @Test
    void moodActiveFollowsTheLink() {
        String facts = Vt100StatusLine.facts("vt100", "true", "ollama/deepseek-r1:7b");
        String west = Vt100StatusLine.west("Connected", "10.0.0.2", "22");
        assertEquals(
                "Connected 10.0.0.2:22  mood active  vault: disk  VT100  air-gap: true  engine: ollama/deepseek-r1:7b",
                Vt100StatusLine.join(west, true, facts));
        assertEquals(
                "Connected 10.0.0.2:22  vault: disk  VT100  air-gap: true  engine: ollama/deepseek-r1:7b",
                Vt100StatusLine.join(west, false, facts));
        String pty = Vt100StatusLine.facts("pty", "true", "ollama/deepseek-r1:7b");
        assertEquals(
                "Connected 10.0.0.2:22  mood active  vault: disk  PTY  air-gap: true  engine: ollama/deepseek-r1:7b",
                Vt100StatusLine.join(west, true, pty));
    }

    /**
     * The method arranges empty facts and joins them with west text.
     * The method calls {@link Vt100StatusLine#facts}, {@link Vt100StatusLine#west}, and {@link Vt100StatusLine#join}.
     * {@code Connecting} with the mood flag false returns {@code Connecting  vault: disk  VT100  air-gap: unset  engine: ollama}.
     * A disconnect reason with the mood flag false keeps the em dash and the words {@code read thread did not stop}.
     * That reason line does not contain {@code mood active}.
     * That reason line does not contain {@code AMWP}.
     * That reason line does not contain {@code lantern-keep}.
     * {@code Disconnected} with the mood flag true contains {@code mood active} before the empty facts.
     *
     * @return The method returns nothing.
     */
    @Test
    void theMoodStringStaysOffTheLine() {
        String quiet = Vt100StatusLine.facts("", "", "");
        String connecting = Vt100StatusLine.join(
                Vt100StatusLine.west("Connecting", "10.0.0.2", "22"), false, quiet);
        assertEquals("Connecting  vault: disk  VT100  air-gap: unset  engine: ollama", connecting);
        String reason = Vt100StatusLine.join(
                Vt100StatusLine.west("Disconnected: read thread did not stop", "h", "22"),
                false,
                quiet);
        assertEquals(
                "Disconnected \u2014 read thread did not stop  vault: disk  VT100  air-gap: unset  engine: ollama",
                reason);
        assertFalse(reason.contains("mood active"));
        assertFalse(reason.contains("AMWP"));
        assertFalse(reason.contains("lantern-keep"));
        assertEquals(
                "Disconnected  mood active  vault: disk  VT100  air-gap: unset  engine: ollama",
                Vt100StatusLine.join(Vt100StatusLine.west("Disconnected", "h", "22"), true, quiet));
    }

    /**
     * The method arranges an Ollama id, a Grok id, and pool.
     * The method calls {@link Vt100StatusLine#engineFromConsole}.
     * Ollama plus {@code deepseek-r1:7b} returns {@code ollama/deepseek-r1:7b}.
     * A blank Ollama id returns {@code ollama}. A null family returns {@code ollama}.
     * An id that already starts with {@code ollama/} stays that word.
     * Grok plus {@code grok-4.3} returns {@code grok/grok-4.3}. A blank Grok id returns {@code grok}.
     * Pool returns {@code pool}.
     *
     * @return The method returns nothing.
     */
    @Test
    void thePaneEngineNamesTheSelectedModel() {
        assertEquals("ollama/deepseek-r1:7b", Vt100StatusLine.engineFromConsole("ollama", "deepseek-r1:7b", ""));
        assertEquals("ollama", Vt100StatusLine.engineFromConsole("ollama", "", "grok-4.3"));
        assertEquals("ollama", Vt100StatusLine.engineFromConsole(null, null, null));
        assertEquals("ollama/deepseek-r1:7b", Vt100StatusLine.engineFromConsole("", "ollama/deepseek-r1:7b", ""));
        assertEquals("grok/grok-4.3", Vt100StatusLine.engineFromConsole("grok", "deepseek-r1:7b", "grok-4.3"));
        assertEquals("grok", Vt100StatusLine.engineFromConsole("grok", "deepseek-r1:7b", "  "));
        assertEquals("pool", Vt100StatusLine.engineFromConsole("pool", "deepseek-r1:7b", "grok-4.3"));
    }

    /**
     * The method arranges a {@code shell_model_result:} body for Grok and one for Ollama.
     * The method calls {@link Vt100StatusLine#engineFromShellResult} and {@link Vt100StatusLine#facts}.
     * The Grok body returns {@code grok/grok-4.3}. The facts line ends with {@code engine: grok/grok-4.3}.
     * That line does not contain {@code ollama}.
     * The Ollama body uses {@code model:} when {@code ollama_model:} is empty and returns {@code ollama/deepseek-r1:7b}.
     * A canvas sentence returns null. A null body returns null.
     *
     * @return The method returns nothing.
     */
    @Test
    void aShellResultPaintsThatPaneAndNotTheCell() {
        String grok = "shell_model_result:\n"
                + "provider: grok\n"
                + "model: deepseek-r1:7b\n"
                + "ollama_model: deepseek-r1:7b\n"
                + "grok_model: grok-4.3\n";
        assertEquals("grok/grok-4.3", Vt100StatusLine.engineFromShellResult(grok));
        String line = Vt100StatusLine.facts("vt100", "false", Vt100StatusLine.engineFromShellResult(grok));
        assertEquals("vault: disk  VT100  air-gap: false  engine: grok/grok-4.3", line);
        assertFalse(line.contains("ollama"));
        String ollama = "shell_model_result:\r\n"
                + "provider: ollama\r\n"
                + "model: deepseek-r1:7b\r\n"
                + "ollama_model:\r\n"
                + "grok_model: grok-4.3\r\n";
        assertEquals("ollama/deepseek-r1:7b", Vt100StatusLine.engineFromShellResult(ollama));
        assertEquals("pool", Vt100StatusLine.engineFromShellResult("shell_model_result:\nprovider: pool\n"));
        assertNull(Vt100StatusLine.engineFromShellResult("vault: disk  engine: grok/m"));
        assertNull(Vt100StatusLine.engineFromShellResult(null));
    }
}
