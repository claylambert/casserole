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

import java.util.ArrayList;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * The {@code EditMemory} type is the in-memory undo record for one editor.
 * The stack remembers the text and the cursor.
 * The caller writes {@link #text()} into the live widget.
 * The type saves nothing to disk.
 * The span waits on a queue until {@link #undo()} or {@link #redo()} asks for the stack.
 * // @aa-sdd PK-1163 docs/specs/java/org/casserole/edit/edit.spec.md FINESSE:f-191
 * // @aa-sdd PK-1080 docs/specs/common/formal-plan.spec.md FINESSE:f-203
 *
 * @author clayl (2026-11-03)
 */
public final class EditMemory {

    /**
     * The oldest edits leave once the undo stack reaches this length.
     * The value is 100.
     */
    public static final int LIMIT = 100;

    private final UndoManager manager;
    private final ArrayList<Span> pending;
    private String last;
    private int lastCursor;
    private boolean applying;

    /**
     * The constructor starts an empty line and an empty stack.
     * The stored text is an empty string.
     * The stored cursor is 0.
     * The {@link #applying} flag is false.
     * The undo manager limit is {@link #LIMIT}.
     */
    public EditMemory() {
        this.manager = new UndoManager();
        this.manager.setLimit(LIMIT);
        this.pending = new ArrayList<Span>();
        this.last = "";
        this.lastCursor = 0;
        this.applying = false;
    }

    /**
     * The {@code applying} method reports whether an undo or a redo is writing the stored text.
     * The flag stays true until {@link #endApply} or until undo or redo fails.
     *
     * @return Whether an undo or a redo is writing the stored text.
     */
    public boolean applying() {
        return applying;
    }

    /**
     * The {@code endApply} method clears the {@link #applying} flag.
     * The listener has applied {@link #text()}.
     * Later changes may record again.
     */
    public void endApply() {
        applying = false;
    }

    /**
     * The {@code text} method returns the text the live widget should show.
     *
     * @return The stored text.
     */
    public String text() {
        return last;
    }

    /**
     * The {@code cursor} method returns the cursor the live widget should show.
     * The method returns 0 when the stored cursor is negative.
     * The method returns the text length when the stored cursor is past the end.
     *
     * @return The cursor clamped to the stored text.
     */
    public int cursor() {
        if (lastCursor < 0) {
            return 0;
        }
        if (lastCursor > last.length()) {
            return last.length();
        }
        return lastCursor;
    }

    /**
     * The {@code sync} method remembers this text without pushing a step.
     * The rebuilt widget uses this method.
     * The method stores a null text as an empty string.
     * The method stores a negative cursor as 0.
     * The method stores a cursor past the end as the text length.
     *
     * @param text The current text.
     * @param cursor The current cursor.
     */
    public void sync(String text, int cursor) {
        last = text == null ? "" : text;
        lastCursor = cursor < 0 ? 0 : cursor;
        if (lastCursor > last.length()) {
            lastCursor = last.length();
        }
    }

    /**
     * The {@code discard} method drops the queue and the undo stack.
     * The {@link #applying} flag becomes false.
     * {@link #text()} stays until {@link #sync(String, int)}.
     */
    public void discard() {
        pending.clear();
        manager.discardAllEdits();
        applying = false;
    }

    /**
     * The {@code noteSpan} method queues one insert or one remove.
     * The method updates the stored text and returns.
     * The undo stack receives the span when {@link #undo()} or {@link #redo()} runs.
     * The undo stack also receives the span when a later call runs {@link #fold()} because redo is available or the queue holds more than {@link #LIMIT} spans.
     * {@code cursorBefore} is the caret before this change.
     * {@code cursorAfter} is the caret after it.
     * The method returns false when {@link #applying} is true.
     * The method treats a null removed string as empty.
     * The method treats a null inserted string as empty.
     * The method returns false when {@code offset} is outside the stored text.
     * The method returns false when the removed text runs past the end.
     * The method returns false when both the removed text and the inserted text are empty.
     * The caret before the change is clamped to the stored text.
     * The caret after the change is clamped to the text after the edit.
     * The stored text becomes the prefix, the inserted text, and the suffix after the removed text.
     *
     * @param offset The start of the change in the text before it.
     * @param removed The text that left, or empty on an insert.
     * @param inserted The text that arrived, or empty on a remove.
     * @param cursorBefore The caret before the change.
     * @param cursorAfter The caret after the change.
     * @return Whether the span was queued.
     */
    public boolean noteSpan(int offset, String removed, String inserted, int cursorBefore, int cursorAfter) {
        if (applying) {
            return false;
        }
        final String cut = removed == null ? "" : removed;
        final String put = inserted == null ? "" : inserted;
        if (offset < 0 || offset > last.length()) {
            return false;
        }
        if (offset + cut.length() > last.length()) {
            return false;
        }
        if (cut.length() == 0 && put.length() == 0) {
            return false;
        }
        final int at = offset;
        int before = cursorBefore;
        if (before < 0) {
            before = 0;
        }
        if (before > last.length()) {
            before = last.length();
        }
        final String next = last.substring(0, at) + put + last.substring(at + cut.length());
        int after = cursorAfter;
        if (after < 0) {
            after = 0;
        }
        if (after > next.length()) {
            after = next.length();
        }
        final int beforeSeat = before;
        final int afterSeat = after;
        // The key only queues. Undo, redo, and a later fold write the stack.
        pending.add(new Span(at, cut, put, beforeSeat, afterSeat));
        last = next;
        lastCursor = afterSeat;
        return true;
    }

    /**
     * The {@code foldDue} method reports whether a later call should fold the queue into the stack.
     * The method returns true when redo is available.
     * The method returns true when the queue holds more than {@link #LIMIT} spans.
     *
     * @return Whether {@link #fold()} should run.
     */
    public boolean foldDue() {
        return manager.canRedo() || pending.size() > LIMIT;
    }

    /**
     * The {@code fold} method folds queued spans into the undo stack.
     * The method calls {@link #drain}.
     * The key path does not call this method.
     */
    public void fold() {
        drain();
    }

    /**
     * The {@code drain} method writes every queued span onto the undo stack.
     * Each span is passed to {@link #addSpan}.
     * The queue is cleared after those writes.
     */
    private void drain() {
        for (int i = 0; i < pending.size(); i++) {
            Span span = pending.get(i);
            addSpan(span.offset, span.removed, span.inserted, span.cursorBefore, span.cursorAfter);
        }
        pending.clear();
    }

    /**
     * The {@code addSpan} method pushes one span onto the undo manager.
     * The undo step restores the removed text at {@code at} and the caret from before the change.
     * The redo step restores the inserted text at {@code at} and the caret from after the change.
     *
     * @param at The start of the change.
     * @param cut The text that left.
     * @param put The text that arrived.
     * @param beforeSeat The caret before the change.
     * @param afterSeat The caret after the change.
     */
    private void addSpan(final int at, final String cut, final String put, final int beforeSeat, final int afterSeat) {
        /**
         * The edit restores one queued span.
         * The {@code undo} method calls the superclass undo, writes the removed text back at {@code at}, and stores {@code beforeSeat}.
         * The {@code redo} method calls the superclass redo, writes the inserted text back at {@code at}, and stores {@code afterSeat}.
         *
         * @throws javax.swing.undo.CannotUndoException The superclass undo refuses the step.
         * @throws javax.swing.undo.CannotRedoException The superclass redo refuses the step.
         */
        manager.addEdit(new AbstractUndoableEdit() {
            // @aa-sdd PK-1163 docs/specs/java/org/casserole/edit/edit.spec.md FINESSE:f-191
            // @aa-sdd PK-1080 docs/specs/common/formal-plan.spec.md FINESSE:f-203
            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                last = last.substring(0, at) + cut + last.substring(at + put.length());
                lastCursor = beforeSeat;
            }

            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                last = last.substring(0, at) + put + last.substring(at + cut.length());
                lastCursor = afterSeat;
            }
        });
    }

    /**
     * The {@code note} method records one whole-text change.
     * The method treats a null text as an empty string.
     * The method stores a negative cursor as 0.
     * The method stores a cursor past the end as the text length.
     * The method stores the text and the cursor and pushes no step when {@link #applying} is true.
     * The method stores only the cursor when the text equals the stored text.
     * The method pushes one undo step for any other text and then stores the new text and cursor.
     * The undo of that step restores the previous text and cursor.
     * The redo of that step restores the text and cursor passed to this call.
     *
     * @param text The text after the change.
     * @param cursor The cursor after the change.
     */
    public void note(String text, int cursor) {
        String next = text == null ? "" : text;
        int at = cursor < 0 ? 0 : cursor;
        if (at > next.length()) {
            at = next.length();
        }
        if (applying) {
            last = next;
            lastCursor = at;
            return;
        }
        if (next.equals(last)) {
            lastCursor = at;
            return;
        }
        final String before = last;
        final int beforeCursor = lastCursor;
        final String after = next;
        final int afterCursor = at;
        /**
         * The edit restores one whole-text step.
         * The {@code undo} method calls the superclass undo and stores the text and cursor from before this call.
         * The {@code redo} method calls the superclass redo and stores the text and cursor from this call.
         *
         * @throws javax.swing.undo.CannotUndoException The superclass undo refuses the step.
         * @throws javax.swing.undo.CannotRedoException The superclass redo refuses the step.
         */
        manager.addEdit(new AbstractUndoableEdit() {
            // @aa-sdd PK-1163 docs/specs/java/org/casserole/edit/edit.spec.md FINESSE:f-191
            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                last = before;
                lastCursor = beforeCursor;
            }

            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                last = after;
                lastCursor = afterCursor;
            }
        });
        last = after;
        lastCursor = afterCursor;
    }

    /**
     * The {@code undo} method steps back one change.
     * The method folds the queue first through {@link #drain}.
     * The method returns false when the stack has nothing to undo.
     * The {@link #applying} flag becomes true before the step.
     * The caller then writes {@link #text()}.
     * The flag becomes false and the method returns false when the undo throws {@code CannotUndoException}.
     *
     * @return Whether one change was undone.
     */
    public boolean undo() {
        drain();
        if (!manager.canUndo()) {
            return false;
        }
        applying = true;
        try {
            manager.undo();
            return true;
        } catch (CannotUndoException ex) {
            applying = false;
            return false;
        }
    }

    /**
     * The {@code redo} method steps forward one undone change.
     * The method folds the queue first through {@link #drain}.
     * The method returns false when the stack has nothing to redo.
     * The {@link #applying} flag becomes true before the step.
     * The caller then writes {@link #text()}.
     * The flag becomes false and the method returns false when the redo throws {@code CannotRedoException}.
     *
     * @return Whether one change was redone.
     */
    public boolean redo() {
        drain();
        if (!manager.canRedo()) {
            return false;
        }
        applying = true;
        try {
            manager.redo();
            return true;
        } catch (CannotRedoException ex) {
            applying = false;
            return false;
        }
    }

    // @aa-sdd PK-1080 docs/specs/common/formal-plan.spec.md FINESSE:f-203
    /**
     * The {@code Span} type is one queued insert or remove.
     * The {@link EditMemory#drain} method writes the span onto the undo stack.
     * The offset is the start of the change in the text before it.
     * The removed text is empty on an insert.
     * The inserted text is empty on a remove.
     */
    private static final class Span {
        private final int offset;
        private final String removed;
        private final String inserted;
        private final int cursorBefore;
        private final int cursorAfter;

        /**
         * The constructor stores one queued change.
         *
         * @param offset The start of the change in the text before it.
         * @param removed The text that left, or empty on an insert.
         * @param inserted The text that arrived, or empty on a remove.
         * @param cursorBefore The caret before the change.
         * @param cursorAfter The caret after the change.
         */
        private Span(int offset, String removed, String inserted, int cursorBefore, int cursorAfter) {
            this.offset = offset;
            this.removed = removed;
            this.inserted = inserted;
            this.cursorBefore = cursorBefore;
            this.cursorAfter = cursorAfter;
        }
    }
}
