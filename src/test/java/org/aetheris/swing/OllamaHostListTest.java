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

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * The class checks the model rows {@link AetherisNotebookPanel} builds from a tags reply.
 * The test does not open a window.
 * The test does not open a socket.
 *
 * @author clayl (2026-10-28)
 */
class OllamaHostListTest {

    /**
     * The method arranges a tags reply with two {@code qwen3.5:9b} lines, a blank model line, and {@code llama3.2}.
     * The method calls {@link AetherisNotebookPanel#tagsResultModels}.
     * The list holds two names.
     * The first name is {@code qwen3.5:9b}.
     * The second name is {@code llama3.2}.
     *
     * @return The method returns nothing.
     */
    @Test
    void modelLinesAreTheHostNames() {
        ArrayList<String> names = AetherisNotebookPanel.tagsResultModels(
                "tags_result:\nmodel: qwen3.5:9b\nmodel: qwen3.5:9b\nmodel:\nmodel: llama3.2\r\n");
        assertEquals(2, names.size());
        assertEquals("qwen3.5:9b", names.get(0));
        assertEquals("llama3.2", names.get(1));
    }

    /**
     * The method arranges a null reply and a reply that holds only {@code tags_result:}.
     * The method calls {@link AetherisNotebookPanel#tagsResultModels}.
     * The null reply returns an empty list.
     * The header-only reply returns an empty list.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBlankReplyListsNoModel() {
        assertEquals(0, AetherisNotebookPanel.tagsResultModels(null).size());
        assertEquals(0, AetherisNotebookPanel.tagsResultModels("tags_result:\n").size());
    }

    /**
     * The method arranges a tags reply that lists {@code llama3.2} and a typed id {@code qwen3.5:9b}.
     * The method calls {@link AetherisNotebookPanel#ollamaHostRows}.
     * The list holds two rows.
     * The first row is {@code llama3.2}.
     * The second row is {@code qwen3.5:9b}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aTypedIdStaysWhenItIsNotOnTheHost() {
        ArrayList<String> rows = AetherisNotebookPanel.ollamaHostRows(
                "tags_result:\nmodel: llama3.2\n", "qwen3.5:9b");
        assertEquals(2, rows.size());
        assertEquals("llama3.2", rows.get(0));
        assertEquals("qwen3.5:9b", rows.get(1));
    }

    /**
     * The method arranges a tags reply that already lists {@code qwen3.5:9b} and {@code llama3.2}.
     * The typed id is {@code qwen3.5:9b}.
     * The method calls {@link AetherisNotebookPanel#ollamaHostRows}.
     * The list holds two rows.
     * The first row is {@code qwen3.5:9b}.
     * The second row is {@code llama3.2}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aTypedIdAlreadyOnTheHostIsNotRepeated() {
        ArrayList<String> rows = AetherisNotebookPanel.ollamaHostRows(
                "tags_result:\nmodel: qwen3.5:9b\nmodel: llama3.2\n", "qwen3.5:9b");
        assertEquals(2, rows.size());
        assertEquals("qwen3.5:9b", rows.get(0));
        assertEquals("llama3.2", rows.get(1));
    }

    /**
     * The method arranges a tags reply that lists {@code llama3.2} and a typed field of spaces.
     * The method calls {@link AetherisNotebookPanel#ollamaHostRows}.
     * The list holds one row.
     * That row is {@code llama3.2}.
     *
     * @return The method returns nothing.
     */
    @Test
    void anEmptyFieldAddsNoRow() {
        ArrayList<String> rows = AetherisNotebookPanel.ollamaHostRows(
                "tags_result:\nmodel: llama3.2\n", "  ");
        assertEquals(1, rows.size());
        assertEquals("llama3.2", rows.get(0));
    }

    /**
     * The method arranges no cards.
     * The method calls {@link AetherisNotebookPanel#sameDialogCell}.
     * The same id matches when one copy is upper case.
     * A null id does not match a filled id.
     * Two different ids do not match.
     *
     * @return The method returns nothing.
     */
    @Test
    void theOpenCellMatchesIgnoringCase() {
        assertTrue(AetherisNotebookPanel.sameDialogCell(
                "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee",
                "AAAAAAAA-BBBB-CCCC-DDDD-EEEEEEEEEEEE"));
        assertFalse(AetherisNotebookPanel.sameDialogCell(null, "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee"));
        assertFalse(AetherisNotebookPanel.sameDialogCell(
                "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee",
                "00000000-0000-0000-0000-000000000000"));
    }

    // @aa-sdd PK-8253 docs/specs/java/org/casserole/llm/llm.spec.md
    /**
     * The method arranges a Grok catalog reply with no model lines, then one with one id.
     * The method calls {@link AetherisNotebookPanel#grokHostRows}.
     * The empty reply returns no rows.
     * An unknown current id is first.
     * A current id that is already listed is not repeated.
     *
     * @return The method returns nothing.
     */
    @Test
    void aGrokReplyKeepsAnUnknownIdFirst() {
        ArrayList<String> empty = AetherisNotebookPanel.grokHostRows("grok_models_result:\n", "grok-4.7");
        assertEquals(0, empty.size());
        ArrayList<String> rows = AetherisNotebookPanel.grokHostRows(
                "grok_models_result:\nmodel: remote-only\nmodel: remote-only\n", "kept-local");
        assertEquals(2, rows.size());
        assertEquals("kept-local", rows.get(0));
        assertEquals("remote-only", rows.get(1));
        ArrayList<String> known = AetherisNotebookPanel.grokHostRows(
                "grok_models_result:\nmodel: remote-only\n", "remote-only");
        assertEquals(1, known.size());
        assertEquals("remote-only", known.get(0));
    }
}
