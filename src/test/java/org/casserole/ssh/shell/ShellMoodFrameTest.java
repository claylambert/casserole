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
