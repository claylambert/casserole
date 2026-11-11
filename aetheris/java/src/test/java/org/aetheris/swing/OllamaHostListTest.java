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

// @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
