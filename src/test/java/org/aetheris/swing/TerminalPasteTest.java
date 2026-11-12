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

import org.junit.jupiter.api.Test;

/**
 * The class checks the body choice in {@link TerminalPaste#decide}.
 * The test does not open a window.
 * The test does not open a pseudo-terminal.
 *
 * @author clayl (2026-10-25)
 */
class TerminalPasteTest {

    /**
     * The method arranges a text one character longer than {@link TerminalPaste#MAX_CHARS}.
     * The method calls {@link TerminalPaste#decide} with the ephemeral flag true and that text in both body arguments.
     * The kind is {@link TerminalPaste.Kind#EPHEMERAL}.
     * The body is empty.
     *
     * @return The method returns nothing.
     */
    @Test
    void burnerIsRefusedBeforeTheBody() {
        String huge = "x".repeat(TerminalPaste.MAX_CHARS + 1);
        TerminalPaste.Decision decision = TerminalPaste.decide(true, huge, huge);
        assertEquals(TerminalPaste.Kind.EPHEMERAL, decision.kind);
        assertEquals("", decision.body);
    }

    /**
     * The method arranges the reply {@code line} plus a carriage return and {@code two}, and the prompt {@code prompt}.
     * The method calls {@link TerminalPaste#decide} with the ephemeral flag false.
     * The kind is {@link TerminalPaste.Kind#READY}.
     * The body is a space, {@code line}, a line feed, {@code two}, and a space.
     *
     * @return The method returns nothing.
     */
    @Test
    void replyWinsWhenItsTrimIsNonEmpty() {
        TerminalPaste.Decision decision = TerminalPaste.decide(false, " line\r\ntwo ", "prompt");
        assertEquals(TerminalPaste.Kind.READY, decision.kind);
        assertEquals(" line\ntwo ", decision.body);
    }

    /**
     * The method arranges a reply of two line feeds and a prompt of spaces around {@code prompt}.
     * The method calls {@link TerminalPaste#decide} with the ephemeral flag false.
     * The kind is {@link TerminalPaste.Kind#READY}.
     * The body is the prompt text, including the surrounding spaces.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBlankReplyFallsThroughToThePrompt() {
        TerminalPaste.Decision decision = TerminalPaste.decide(false, "\n\n", "  prompt  ");
        assertEquals(TerminalPaste.Kind.READY, decision.kind);
        assertEquals("  prompt  ", decision.body);
    }

    /**
     * The method arranges a reply of spaces and a carriage return, and a prompt of three spaces.
     * The method calls {@link TerminalPaste#decide} with the ephemeral flag false.
     * The kind is {@link TerminalPaste.Kind#READY}.
     * The body is those three spaces.
     *
     * @return The method returns nothing.
     */
    @Test
    void aSpacesOnlyPromptIsSent() {
        TerminalPaste.Decision decision = TerminalPaste.decide(false, " \r\n ", "   ");
        assertEquals(TerminalPaste.Kind.READY, decision.kind);
        assertEquals("   ", decision.body);
    }

    /**
     * The method arranges null texts, empty texts, a line-feed reply, and a spaces-only reply with a null prompt.
     * The method calls {@link TerminalPaste#decide} with the ephemeral flag false.
     * Each of those calls returns {@link TerminalPaste.Kind#EMPTY}.
     * The spaces-only reply returns an empty body.
     *
     * @return The method returns nothing.
     */
    @Test
    void anEmptyCellStops() {
        assertEquals(TerminalPaste.Kind.EMPTY, TerminalPaste.decide(false, null, null).kind);
        assertEquals(TerminalPaste.Kind.EMPTY, TerminalPaste.decide(false, "", "").kind);
        assertEquals(TerminalPaste.Kind.EMPTY, TerminalPaste.decide(false, "\n", "").kind);
        TerminalPaste.Decision blankReply = TerminalPaste.decide(false, "  ", null);
        assertEquals(TerminalPaste.Kind.EMPTY, blankReply.kind);
        assertEquals("", blankReply.body);
    }

    /**
     * The method arranges an empty reply and a prompt that is one line feed.
     * The method calls {@link TerminalPaste#decide} with the ephemeral flag false.
     * The kind is {@link TerminalPaste.Kind#READY}.
     * The body is that line feed.
     *
     * @return The method returns nothing.
     */
    @Test
    void aNewlineOnlyPromptIsSent() {
        TerminalPaste.Decision decision = TerminalPaste.decide(false, "", "\n");
        assertEquals(TerminalPaste.Kind.READY, decision.kind);
        assertEquals("\n", decision.body);
    }

    /**
     * The method arranges a reply of {@link TerminalPaste#MAX_CHARS} letters and a reply one letter longer.
     * The prompt is {@code short}.
     * The method calls {@link TerminalPaste#decide} with the ephemeral flag false.
     * The exact length returns {@link TerminalPaste.Kind#READY}.
     * That body length is {@link TerminalPaste#MAX_CHARS}.
     * The longer reply returns {@link TerminalPaste.Kind#OVER_CAP}.
     * That body is empty.
     *
     * @return The method returns nothing.
     */
    @Test
    void overCapIsRefusedAndExactCapIsReady() {
        String exact = "a".repeat(TerminalPaste.MAX_CHARS);
        TerminalPaste.Decision ready = TerminalPaste.decide(false, exact, "short");
        assertEquals(TerminalPaste.Kind.READY, ready.kind);
        assertEquals(TerminalPaste.MAX_CHARS, ready.body.length());

        String over = exact + "b";
        TerminalPaste.Decision refused = TerminalPaste.decide(false, over, "short");
        assertEquals(TerminalPaste.Kind.OVER_CAP, refused.kind);
        assertEquals("", refused.body);
    }

    /**
     * The method arranges a reply of {@code a} plus a carriage-return pair repeated to the cap, and one pair past the cap.
     * The method calls {@link TerminalPaste#decide} with an empty prompt and the ephemeral flag false.
     * The capped reply returns {@link TerminalPaste.Kind#READY}.
     * That body length is {@link TerminalPaste#MAX_CHARS}.
     * That body contains no carriage return.
     * The longer reply returns {@link TerminalPaste.Kind#OVER_CAP}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aCarriageReturnCountsAsOneCharacter() {
        String exact = "a" + "\r\n".repeat(TerminalPaste.MAX_CHARS - 1);
        TerminalPaste.Decision ready = TerminalPaste.decide(false, exact, "");
        assertEquals(TerminalPaste.Kind.READY, ready.kind);
        assertEquals(TerminalPaste.MAX_CHARS, ready.body.length());
        assertFalse(ready.body.contains("\r"));

        String over = "a" + "\r\n".repeat(TerminalPaste.MAX_CHARS);
        assertEquals(TerminalPaste.Kind.OVER_CAP, TerminalPaste.decide(false, over, "").kind);
    }

    /**
     * The method arranges the reply {@code a}, a carriage-return pair, {@code b}, a carriage return, {@code c}, and a line feed.
     * The prompt is {@code ignored}.
     * The method calls {@link TerminalPaste#decide} with the ephemeral flag false.
     * The kind is {@link TerminalPaste.Kind#READY}.
     * The body is {@code a}, a line feed, {@code b}, a line feed, {@code c}, and a line feed.
     * The body does not end with a carriage return.
     *
     * @return The method returns nothing.
     */
    @Test
    void newlinesStayAndEnterIsNotAdded() {
        TerminalPaste.Decision decision = TerminalPaste.decide(false, "a\r\nb\rc\n", "ignored");
        assertEquals(TerminalPaste.Kind.READY, decision.kind);
        assertEquals("a\nb\nc\n", decision.body);
        assertFalse(decision.body.endsWith("\r"));
    }
}
