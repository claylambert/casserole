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
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.roux.toon.ToonCodec;

// @aa-sdd PK-4474 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/**
 * The class checks the console mood frame on {@link ChatShell}.
 * A model frame stays a model frame.
 * The test does not open a pseudo-terminal.
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 *
 * @author clayl (2026-10-29)
 */
class ShellMoodFrameTest {

    /**
     * The method arranges mood frames, export bodies, and a message table.
     * The method calls {@link ChatShell#consoleMoodLine}, {@link ChatShell#sessionExportReply}, {@link ChatShell#replaceResult}, {@link ToonCodec#parseMessageTable}, {@link ToonCodec#pairConversation}, and {@link ChatShell#tableAfterCommand}.
     *
     * A null frame returns null.
     * A {@code shell_model} frame returns null.
     * A {@code shell_model_set} frame returns null.
     * A {@code shell_mood_clear} frame returns an empty string.
     * A {@code shell_mood} frame with no line returns an empty string.
     * A {@code shell_mood} frame whose line is blank returns an empty string.
     * A {@code shell_mood} frame returns {@code lantern-keep} when that is the line.
     * A {@code shell_mood} frame returns {@code spare, dry} when that is the line.
     *
     * A null export body and an empty export body each return {@code session_export_result:} plus an empty message table header.
     * A one-row table is returned with that same result prefix.
     * A body of {@code 1024} times {@code 1024} copies of {@code x} returns the failure line that says the session does not fit in one frame.
     *
     * A successful replace with an empty detail returns {@code session_replace_result:} and {@code ok}.
     * A failed replace returns {@code replace failed: the table is not a message table}.
     * {@link ToonCodec#parseMessageTable} returns null for {@code nope}.
     * A three-row table yields {@code user} and {@code Hello} in the flat cells.
     * {@link ToonCodec#pairConversation} yields {@code Hello} and {@code Hi, there}.
     * {@link ChatShell#tableAfterCommand} returns null for a {@code shell_model} frame.
     * The same method returns the empty message table header from a {@code session_replace} frame.
     *
     * @return The method returns nothing.
     */
    @Test
    void aMoodFrameCarriesTheLineAndAClearFrameIsEmpty() {
        assertNull(ChatShell.consoleMoodLine(null));
        assertNull(ChatShell.consoleMoodLine("shell_model:\n"));
        assertNull(ChatShell.consoleMoodLine("shell_model_set:\nprovider: ollama\n"));
        assertEquals("", ChatShell.consoleMoodLine("shell_mood_clear:\n"));
        assertEquals("", ChatShell.consoleMoodLine("shell_mood:\n"));
        assertEquals("", ChatShell.consoleMoodLine("shell_mood:\n   \n"));
        assertEquals("lantern-keep", ChatShell.consoleMoodLine("shell_mood:\nlantern-keep\n"));
        assertEquals("spare, dry", ChatShell.consoleMoodLine("shell_mood:\nspare, dry\n"));
        assertEquals(
                "session_export_result:\nmessages[0]{r,ts,tok,x}:\n",
                ChatShell.sessionExportReply(null));
        assertEquals(
                "session_export_result:\nmessages[0]{r,ts,tok,x}:\n",
                ChatShell.sessionExportReply(""));
        String table = "messages[1]{r,ts,tok,x}:\nuser,1,1,Hello\n";
        assertEquals("session_export_result:\n" + table, ChatShell.sessionExportReply(table));
        String huge = "x".repeat(1024 * 1024);
        assertEquals(
                "session_export_result:\nexport failed: the session does not fit in one frame\n",
                ChatShell.sessionExportReply(huge));
        assertEquals("session_replace_result:\nok\n", ChatShell.replaceResult(true, ""));
        assertEquals(
                "session_replace_result:\nreplace failed: the table is not a message table\n",
                ChatShell.replaceResult(false, "the table is not a message table"));
        assertNull(ToonCodec.parseMessageTable("nope"));
        String[] flat = ToonCodec.parseMessageTable(
                "messages[3]{r,ts,tok,x}:\nsystem,1,1,host\nuser,2,1,Hello\nassistant,3,1,\"Hi, there\"\n");
        assertEquals("user", flat[2]);
        assertEquals("Hello", flat[3]);
        String[] cells = ToonCodec.pairConversation(flat);
        assertEquals("Hello", cells[0]);
        assertEquals("Hi, there", cells[1]);
        assertNull(ChatShell.tableAfterCommand("shell_model:\n", "session_replace:"));
        assertEquals("messages[0]{r,ts,tok,x}:\n",
                ChatShell.tableAfterCommand("session_replace:\nmessages[0]{r,ts,tok,x}:\n", "session_replace:"));
    }
}
