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

package org.casserole.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * The class checks the undo queue in {@link EditMemory}.
 * One call to {@link EditMemory#noteSpan} leaves {@link EditMemory#foldDue} clear.
 * A call to {@link EditMemory#note} also leaves that flag clear.
 * The queue marks itself ready to fold after the recorded keystrokes pass {@link EditMemory#LIMIT}.
 * The test does not open a window.
 *
 * @author clayl (2026-11-06)
 */
class EditMemoryTest {

    /**
     * The method arranges a new {@link EditMemory} and records the span that inserts {@code a}.
     * The method calls {@link EditMemory#noteSpan}, {@link EditMemory#undo}, {@link EditMemory#endApply}, and {@link EditMemory#redo}.
     * {@link EditMemory#noteSpan} returns true.
     * The text is {@code a} before the undo.
     * The cursor is {@code 1} before the undo.
     * {@link EditMemory#foldDue} is false before the undo.
     * {@link EditMemory#undo} returns true.
     * The text is empty after {@link EditMemory#endApply}.
     * The cursor is {@code 0} after that call.
     * {@link EditMemory#foldDue} is true after the undo.
     * {@link EditMemory#redo} returns true.
     * The text is {@code a} after the redo apply.
     *
     * @return The method returns nothing.
     */
    @Test
    void aKeyQueuesAndUndoFolds() {
        EditMemory memory = new EditMemory();
        assertTrue(memory.noteSpan(0, "", "a", 0, 1));
        assertEquals("a", memory.text());
        assertEquals(1, memory.cursor());
        assertFalse(memory.foldDue());
        assertTrue(memory.undo());
        memory.endApply();
        assertEquals("", memory.text());
        assertEquals(0, memory.cursor());
        assertTrue(memory.foldDue());
        assertTrue(memory.redo());
        memory.endApply();
        assertEquals("a", memory.text());
    }

    /**
     * The method arranges a new {@link EditMemory}, records {@code a}, and undoes that span.
     * The method then calls {@link EditMemory#noteSpan} for {@code b}, {@link EditMemory#fold}, {@link EditMemory#redo}, and {@link EditMemory#undo}.
     * The text is {@code b} after the second span.
     * {@link EditMemory#foldDue} is true before {@link EditMemory#fold} and false after it.
     * The text stays {@code b} after the fold.
     * {@link EditMemory#redo} returns false.
     * {@link EditMemory#undo} returns true.
     * The text is empty after the undo apply.
     *
     * @return The method returns nothing.
     */
    @Test
    void aTypeAfterUndoWaitsToClearRedo() {
        EditMemory memory = new EditMemory();
        assertTrue(memory.noteSpan(0, "", "a", 0, 1));
        assertTrue(memory.undo());
        memory.endApply();
        assertTrue(memory.noteSpan(0, "", "b", 0, 1));
        assertEquals("b", memory.text());
        assertTrue(memory.foldDue());
        memory.fold();
        assertFalse(memory.foldDue());
        assertEquals("b", memory.text());
        assertFalse(memory.redo());
        assertTrue(memory.undo());
        memory.endApply();
        assertEquals("", memory.text());
    }

    /**
     * The method arranges a new {@link EditMemory} and records {@link EditMemory#LIMIT} plus one span of {@code x}.
     * Each {@link EditMemory#noteSpan} call returns true.
     * The method then calls {@link EditMemory#fold}, {@link EditMemory#undo}, and {@link EditMemory#endApply}.
     * The text length is {@link EditMemory#LIMIT} plus one before the fold.
     * {@link EditMemory#foldDue} is true before {@link EditMemory#fold} and false after it.
     * {@link EditMemory#undo} returns true.
     * The text length is {@link EditMemory#LIMIT} after the undo apply.
     *
     * @return The method returns nothing.
     */
    @Test
    void aFullQueueFoldsOffTheKey() {
        EditMemory memory = new EditMemory();
        for (int i = 0; i < EditMemory.LIMIT + 1; i++) {
            assertTrue(memory.noteSpan(i, "", "x", i, i + 1));
        }
        assertEquals(EditMemory.LIMIT + 1, memory.text().length());
        assertTrue(memory.foldDue());
        memory.fold();
        assertFalse(memory.foldDue());
        assertTrue(memory.undo());
        memory.endApply();
        assertEquals(EditMemory.LIMIT, memory.text().length());
    }

    /**
     * The method arranges a new {@link EditMemory} and calls {@link EditMemory#note} with {@code hi} and cursor {@code 2}.
     * The method then calls {@link EditMemory#undo}, {@link EditMemory#endApply}, and {@link EditMemory#redo}.
     * The text is {@code hi} after the snapshot.
     * {@link EditMemory#foldDue} is false after the snapshot.
     * {@link EditMemory#undo} returns true.
     * The text is empty after the undo apply.
     * {@link EditMemory#redo} returns true.
     * The text is {@code hi} after the redo apply.
     *
     * @return The method returns nothing.
     */
    @Test
    void aSnapshotStillFoldsOnTheCaller() {
        EditMemory memory = new EditMemory();
        memory.note("hi", 2);
        assertEquals("hi", memory.text());
        assertFalse(memory.foldDue());
        assertTrue(memory.undo());
        memory.endApply();
        assertEquals("", memory.text());
        assertTrue(memory.redo());
        memory.endApply();
        assertEquals("hi", memory.text());
    }
}
