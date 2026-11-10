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

import org.junit.jupiter.api.Test;

/**
 * The class checks the status text from {@link Vt100StatusLine}.
 * The test does not open a window.
 * The test does not open a pseudo-terminal.
 * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
 * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
 * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
 * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
 * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
 * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
 * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
 * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
 * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
 * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
 * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
 * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
 * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
 * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
 * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
 * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
 * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
 * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
 *
 * @author clayl (2026-10-31)
 */
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
}
