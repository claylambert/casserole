// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The module keeps an undo stack in memory for one editor.
//!
//! A span waits on a queue until undo or redo asks for the stack. The stack remembers the text and the caret. The caller writes both into the live widget. Nothing here is saved. A restart drops it. The caret index counts characters, the same count the prompt caret uses.
//!
//! @aa-sdd PK-4291 docs/specs/java/org/aetheris/swing/swing.spec.md
//! @author clayl (2026-11-04)

/// `LIMIT` is the longest undo stack.
/// Oldest edits leave once the stack is longer than 100 steps.
pub(crate) const LIMIT: usize = 100;

/// @aa-sdd PK-4292 docs/specs/java/org/aetheris/swing/swing.spec.md
/// The `Span` struct is one queued insert or remove.
/// `at` is the character index. `removed` is the text that left. `inserted` is the text that arrived. `cursor_before` and `cursor_after` are caret indexes. The stack receives the span when the queue is drained.
struct Span {
    at: usize,
    removed: String,
    inserted: String,
    cursor_before: usize,
    cursor_after: usize,
}

/// @aa-sdd PK-4293 docs/specs/java/org/aetheris/swing/swing.spec.md
/// The `Step` enum is one entry on the undo stack.
/// `Span` is a contiguous insert or remove. `Note` is a whole-text change.
enum Step {
    Span(Span),
    /// The `Note` variant is a whole-text change.
    /// Import uses this shape. The SSH line in the Java client uses the same shape. `before` and `after` are the full strings. The cursor fields are caret indexes.
    Note {
        before: String,
        before_cursor: usize,
        after: String,
        after_cursor: usize,
    },
}

/// @aa-sdd PK-4294 docs/specs/java/org/aetheris/swing/swing.spec.md
/// The [`EditMemory`] struct is the undo stack for one editor.
/// [`EditDesk`] keeps one of these for each canvas cell. `steps` is the stack. `next` is the index of the next redo. `pending` is the queue. `last` is the text. `last_cursor` is the caret. `applying` is true while undo or redo is writing the text back.
pub(crate) struct EditMemory {
    steps: Vec<Step>,
    next: usize,
    pending: Vec<Span>,
    last: String,
    last_cursor: usize,
    applying: bool,
}

impl EditMemory {
    /// The `new` method returns an empty [`EditMemory`].
    /// The text is empty. The caret is 0. The stack and the queue are empty.
    fn new() -> Self {
        Self {
            steps: Vec::new(),
            next: 0,
            pending: Vec::new(),
            last: String::new(),
            last_cursor: 0,
            applying: false,
        }
    }

    /// The `applying` method reports whether undo or redo is writing the text back.
    /// True means a new span is not recorded.
    fn applying(&self) -> bool {
        self.applying
    }

    /// The `end_apply` method clears the applying flag.
    /// The stack and the text are left as they are.
    fn end_apply(&mut self) {
        self.applying = false;
    }

    /// The `text` method returns the text this memory last stored.
    /// That text is the value after the last recorded edit, undo, or redo.
    fn text(&self) -> &str {
        &self.last
    }

    /// The `cursor` method returns the caret clamped to the text.
    /// The index counts characters. A stored caret past the end becomes the character count.
    fn cursor(&self) -> usize {
        clamp_cursor(self.last_cursor, self.last.chars().count())
    }

    /// The `sync` method remembers `text` without pushing a step.
    /// The caret is clamped to the character count of `text`. The stack is left as it is.
    fn sync(&mut self, text: &str, cursor: usize) {
        self.last = text.to_string();
        self.last_cursor = clamp_cursor(cursor, self.last.chars().count());
    }

    /// The `discard` method drops the queue, the stack, and the applying flag.
    /// The stored text is left as it is.
    fn discard(&mut self) {
        self.pending.clear();
        self.steps.clear();
        self.next = 0;
        self.applying = false;
    }

    /// The `note_span` method queues one insert or remove.
    /// The caller updates the live widget and returns. The stack receives the span when undo or redo runs, or sooner when a redo is still available or the queue passes [`LIMIT`].
    ///
    /// The method returns false while applying. It returns false when `offset` is past the stored text. It returns false when the removed text runs past the stored text. It returns false when both the removed text and the inserted text are empty. A true result stores the spliced text and the caret after the change.
    fn note_span(
        &mut self,
        offset: usize,
        removed: &str,
        inserted: &str,
        cursor_before: usize,
        cursor_after: usize,
    ) -> bool {
        if self.applying() {
            return false;
        }
        let cut = removed.to_string();
        let put = inserted.to_string();
        let last_chars = self.last.chars().count();
        if offset > last_chars {
            return false;
        }
        if offset + cut.chars().count() > last_chars {
            return false;
        }
        if cut.is_empty() && put.is_empty() {
            return false;
        }
        let before = clamp_cursor(cursor_before, last_chars);
        let next = splice(&self.last, offset, cut.chars().count(), &put);
        let after = clamp_cursor(cursor_after, next.chars().count());
        let span = Span {
            at: offset,
            removed: cut,
            inserted: put,
            cursor_before: before,
            cursor_after: after,
        };
        if self.can_redo() {
            self.drain();
            self.add_span(span);
        } else {
            self.pending.push(span);
            if self.pending.len() > LIMIT {
                self.drain();
            }
        }
        self.last = next;
        self.last_cursor = after;
        true
    }

    /// The `note` method pushes one whole-text step.
    /// The same text only moves the caret. While applying, the text and the caret are stored and no step is pushed. A real change drains the queue, drops any redo tail, and pushes a `Note`.
    fn note(&mut self, text: &str, cursor: usize) {
        let next = text.to_string();
        let at = clamp_cursor(cursor, next.chars().count());
        if self.applying {
            self.last = next;
            self.last_cursor = at;
            return;
        }
        if next == self.last {
            self.last_cursor = at;
            return;
        }
        self.drain();
        let step = Step::Note {
            before: self.last.clone(),
            before_cursor: self.last_cursor,
            after: next.clone(),
            after_cursor: at,
        };
        self.add_step(step);
        self.last = next;
        self.last_cursor = at;
    }

    /// The `undo` method rewinds one step.
    /// The queue is drained first. No step to rewind returns false. A rewind sets applying and returns true. The stored text and caret become the values from before that step.
    fn undo(&mut self) -> bool {
        self.drain();
        if self.next == 0 {
            return false;
        }
        self.applying = true;
        self.next -= 1;
        self.steps[self.next].undo(&mut self.last, &mut self.last_cursor);
        true
    }

    /// The `redo` method replays one step.
    /// The queue is drained first. No step to replay returns false. A replay sets applying and returns true. The stored text and caret become the values from after that step.
    fn redo(&mut self) -> bool {
        self.drain();
        if self.next >= self.steps.len() {
            return false;
        }
        self.applying = true;
        self.steps[self.next].redo(&mut self.last, &mut self.last_cursor);
        self.next += 1;
        true
    }

    /// The `can_redo` method reports whether a redo step is waiting.
    /// True means `next` is still inside `steps`.
    fn can_redo(&self) -> bool {
        self.next < self.steps.len()
    }

    /// The `drain` method moves every queued span onto the stack.
    /// The queue is empty afterward. Each span is added in order.
    fn drain(&mut self) {
        let pending = std::mem::take(&mut self.pending);
        for span in pending {
            self.add_span(span);
        }
    }

    /// The `add_span` method pushes one span as a stack step.
    /// The redo tail is dropped by `add_step`.
    fn add_span(&mut self, span: Span) {
        self.add_step(Step::Span(span));
    }

    /// The `add_step` method pushes `step` and drops the redo tail.
    /// Steps past [`LIMIT`] leave from the front. `next` moves with that drop so it still points at the end.
    fn add_step(&mut self, step: Step) {
        self.steps.truncate(self.next);
        self.steps.push(step);
        self.next = self.steps.len();
        while self.steps.len() > LIMIT {
            self.steps.remove(0);
            if self.next > 0 {
                self.next -= 1;
            }
        }
    }
}

impl Step {
    /// The `undo` method writes the text from before this step.
    /// A span splices the inserted text back out and restores `cursor_before`. A note copies `before` and `before_cursor`. The caret is clamped to the resulting text.
    fn undo(&self, last: &mut String, cursor: &mut usize) {
        match self {
            Step::Span(span) => {
                *last = splice(last, span.at, span.inserted.chars().count(), &span.removed);
                *cursor = clamp_cursor(span.cursor_before, last.chars().count());
            }
            Step::Note {
                before,
                before_cursor,
                ..
            } => {
                *last = before.clone();
                *cursor = clamp_cursor(*before_cursor, last.chars().count());
            }
        }
    }

    /// The `redo` method writes the text from after this step.
    /// A span splices the removed text out and puts the inserted text back. A note copies `after` and `after_cursor`. The caret is clamped to the resulting text.
    fn redo(&self, last: &mut String, cursor: &mut usize) {
        match self {
            Step::Span(span) => {
                *last = splice(last, span.at, span.removed.chars().count(), &span.inserted);
                *cursor = clamp_cursor(span.cursor_after, last.chars().count());
            }
            Step::Note {
                after,
                after_cursor,
                ..
            } => {
                *last = after.clone();
                *cursor = clamp_cursor(*after_cursor, last.chars().count());
            }
        }
    }
}

/// The `caret_seats` function returns the caret before an edit and the caret after it.
/// `dot` is the caret after the change. The counts are characters.
///
/// An insert with no removal treats a caret at or past the inserted text as having started that many characters earlier. A caret inside that insert started at `dot` and ends at `dot` plus the insert. A caret before the insert started at `at` and ends at `at` plus the insert.
///
/// A removal with no insert keeps a caret at or past the removed text and then moves it back by that count. Any other caret on a pure removal ends at `at`. Every other edit starts the caret at `at`. The caret after that edit is `dot` when `dot` is at or past `at`, and otherwise `at` plus the inserted count.
pub(crate) fn caret_seats(at: usize, removed: usize, inserted: usize, dot: usize) -> (usize, usize) {
    if removed == 0 && inserted > 0 {
        if dot >= at + inserted {
            (dot - inserted, dot)
        } else if at <= dot {
            (dot, dot + inserted)
        } else {
            (at, at + inserted)
        }
    } else if inserted == 0 && removed > 0 {
        if dot >= at + removed {
            (dot, dot - removed)
        } else {
            (dot, at)
        }
    } else {
        let after = if dot >= at { dot } else { at + inserted };
        (at, after)
    }
}

/// The `clamp_cursor` function limits a caret to `len`.
/// A caret past `len` becomes `len`. Any other caret is returned as given.
fn clamp_cursor(cursor: usize, len: usize) -> usize {
    if cursor > len { len } else { cursor }
}

/// The `split_chars` function splits `text` after `chars` characters.
/// The split is on a character boundary, not a byte boundary. A count past the end returns the whole text and an empty tail.
fn split_chars(text: &str, chars: usize) -> (&str, &str) {
    let mut count = 0;
    for (index, _) in text.char_indices() {
        if count == chars {
            return text.split_at(index);
        }
        count += 1;
    }
    (text, "")
}

/// The `splice` function replaces `drop` characters at `at` with `put`.
/// The indexes count characters. The result is a new string.
fn splice(text: &str, at: usize, drop: usize, put: &str) -> String {
    let (head, rest) = split_chars(text, at);
    let (_, tail) = split_chars(rest, drop);
    let mut out = String::with_capacity(head.len() + put.len() + tail.len());
    out.push_str(head);
    out.push_str(put);
    out.push_str(tail);
    out
}

/// The `diff_span` function finds one contiguous change between two strings.
/// The shared prefix and the shared suffix are skipped. The result is the character index, the removed text, and the inserted text. Matching texts return `None`.
fn diff_span(before: &str, after: &str) -> Option<(usize, String, String)> {
    let older: Vec<char> = before.chars().collect();
    let newer: Vec<char> = after.chars().collect();
    let mut pre = 0;
    while pre < older.len() && pre < newer.len() && older[pre] == newer[pre] {
        pre += 1;
    }
    let mut suf = 0;
    while suf < older.len() - pre
        && suf < newer.len() - pre
        && older[older.len() - 1 - suf] == newer[newer.len() - 1 - suf]
    {
        suf += 1;
    }
    let removed: String = older[pre..older.len() - suf].iter().collect();
    let inserted: String = newer[pre..newer.len() - suf].iter().collect();
    if removed.is_empty() && inserted.is_empty() {
        None
    } else {
        Some((pre, removed, inserted))
    }
}

/// @aa-sdd PK-4295 docs/specs/java/org/aetheris/swing/swing.spec.md
/// The [`EditDesk`] struct keeps one [`EditMemory`] for each canvas cell.
/// The pair is the cell id and that memory. `applying` is true after undo or redo until `end_apply`. `caret` is the caret from the last undo or redo, kept until one read. A deleted cell drops its stack.
pub(crate) struct EditDesk {
    seats: Vec<([u8; 16], EditMemory)>,
    applying: bool,
    caret: Option<([u8; 16], usize)>,
}

impl EditDesk {
    /// The `new` method returns an empty [`EditDesk`].
    /// No cell has a stack. Applying is false. No caret is waiting.
    pub(crate) fn new() -> Self {
        Self {
            seats: Vec::new(),
            applying: false,
            caret: None,
        }
    }

    /// The `applying` method reports whether the desk is writing an undo or a redo back.
    /// True means `note_prompt` and `note` return without recording.
    pub(crate) fn applying(&self) -> bool {
        self.applying
    }

    /// The `seat` method returns the [`EditMemory`] for `cell`.
    /// A new id gets an empty memory. An id already stored returns that memory.
    fn seat(&mut self, cell: [u8; 16]) -> &mut EditMemory {
        if let Some(index) = self.seats.iter().position(|(id, _)| *id == cell) {
            return &mut self.seats[index].1;
        }
        self.seats.push((cell, EditMemory::new()));
        let last = self.seats.len() - 1;
        &mut self.seats[last].1
    }

    /// The `find` method returns the [`EditMemory`] for `cell` when one exists.
    /// A missing id returns `None`. No memory is created.
    fn find(&mut self, cell: [u8; 16]) -> Option<&mut EditMemory> {
        let index = self.seats.iter().position(|(id, _)| *id == cell)?;
        Some(&mut self.seats[index].1)
    }

    /// The `note_prompt` method records one prompt change as a span.
    /// The caret after the change is `dot`. Applying returns without a record. Matching texts return without a record. A memory whose text is not `before` is synced to `before` with the caret at 0 before the span is queued.
    pub(crate) fn note_prompt(&mut self, cell: [u8; 16], before: &str, after: &str, dot: usize) {
        if self.applying {
            return;
        }
        let Some((at, removed, inserted)) = diff_span(before, after) else {
            return;
        };
        let (pre, post) = caret_seats(
            at,
            removed.chars().count(),
            inserted.chars().count(),
            dot,
        );
        let memory = self.seat(cell);
        if memory.text() != before {
            memory.sync(before, 0);
        }
        memory.note_span(at, &removed, &inserted, pre, post);
    }

    /// The `note` method records a whole-text change as one step.
    /// Import uses this. Applying returns without a record. Matching texts return without a record. The caret stored with the change is the character count of `after`.
    pub(crate) fn note(&mut self, cell: [u8; 16], before: &str, after: &str) {
        if self.applying || before == after {
            return;
        }
        let memory = self.seat(cell);
        if memory.text() != before {
            memory.sync(before, 0);
        }
        let cursor = after.chars().count();
        memory.note(after, cursor);
    }

    /// The `undo` method rewinds the stack for `cell`.
    /// A missing cell returns `None`. No step to rewind returns `None`. A rewind returns the text and the caret. Applying becomes true. The caret is kept for `take_caret`.
    pub(crate) fn undo(&mut self, cell: [u8; 16]) -> Option<(String, usize)> {
        let stepped = {
            let memory = self.find(cell)?;
            if !memory.undo() {
                return None;
            }
            (memory.text().to_string(), memory.cursor())
        };
        self.applying = true;
        self.caret = Some((cell, stepped.1));
        Some(stepped)
    }

    /// The `redo` method replays the stack for `cell`.
    /// A missing cell returns `None`. No step to replay returns `None`. A replay returns the text and the caret. Applying becomes true. The caret is kept for `take_caret`.
    pub(crate) fn redo(&mut self, cell: [u8; 16]) -> Option<(String, usize)> {
        let stepped = {
            let memory = self.find(cell)?;
            if !memory.redo() {
                return None;
            }
            (memory.text().to_string(), memory.cursor())
        };
        self.applying = true;
        self.caret = Some((cell, stepped.1));
        Some(stepped)
    }

    /// The `take_caret` method returns the caret from the last undo or redo for `cell`.
    /// One read clears it. A caret stored for a different cell returns `None` and is left in place.
    pub(crate) fn take_caret(&mut self, cell: [u8; 16]) -> Option<usize> {
        if self.caret.is_some_and(|(id, _)| id == cell) {
            self.caret.take().map(|(_, cursor)| cursor)
        } else {
            None
        }
    }

    /// The `end_apply` method clears applying on the desk and on every memory.
    /// The stacks stay. A stored caret stays.
    pub(crate) fn end_apply(&mut self) {
        self.applying = false;
        for (_, memory) in &mut self.seats {
            memory.end_apply();
        }
    }

    /// The `drop_cell` method removes the stack for `cell`.
    /// A caret stored for that cell is cleared. Other cells stay.
    pub(crate) fn drop_cell(&mut self, cell: &[u8; 16]) {
        self.seats.retain(|(id, _)| id != cell);
        if self.caret.is_some_and(|(id, _)| id == *cell) {
            self.caret = None;
        }
    }

    /// The `clear` method drops every cell stack.
    /// Applying becomes false. No caret remains.
    pub(crate) fn clear(&mut self) {
        self.seats.clear();
        self.applying = false;
        self.caret = None;
    }
}

#[cfg(test)]
/// These functions check the in-memory undo stack.
mod tests {
    use super::*;

    /// The `cell` function returns a 16-byte id whose first byte is `n`.
    /// The other bytes are 0.
    fn cell(n: u8) -> [u8; 16] {
        let mut id = [0u8; 16];
        id[0] = n;
        id
    }

    /// The `the_undo_seats_the_caret_where_the_insert_began` test undoes two inserts.
    /// The first undo returns `h` and cursor 1. The second undo returns an empty string and cursor 0. Redo returns `h` and cursor 1.
    #[test]
    fn the_undo_seats_the_caret_where_the_insert_began() {
        let mut desk = EditDesk::new();
        let id = cell(1);
        desk.note_prompt(id, "", "h", 1);
        desk.note_prompt(id, "h", "hi", 2);
        let (text, cursor) = desk.undo(id).expect("undo");
        assert_eq!(text, "h");
        assert_eq!(cursor, 1);
        let (text, cursor) = desk.undo(id).expect("second");
        assert_eq!(text, "");
        assert_eq!(cursor, 0);
        assert!(desk.undo(id).is_none());
        let (text, cursor) = desk.redo(id).expect("redo");
        assert_eq!(text, "h");
        assert_eq!(cursor, 1);
    }

    /// The `the_backspace_undo_restores_the_character` test undoes a deletion.
    /// The text returns to `ab`. The cursor is 1.
    #[test]
    fn the_backspace_undo_restores_the_character() {
        let mut desk = EditDesk::new();
        let id = cell(1);
        desk.note_prompt(id, "ab", "a", 1);
        let (text, cursor) = desk.undo(id).expect("undo");
        assert_eq!(text, "ab");
        assert_eq!(cursor, 1);
    }

    /// The `the_paste_is_one_span` test undoes one paste.
    /// `aXYZc` returns to `ac` in one undo. A second undo is `None`.
    #[test]
    fn the_paste_is_one_span() {
        let mut desk = EditDesk::new();
        let id = cell(1);
        desk.note_prompt(id, "ac", "aXYZc", 4);
        let (text, cursor) = desk.undo(id).expect("undo");
        assert_eq!(text, "ac");
        assert_eq!(cursor, 1);
        assert!(desk.undo(id).is_none());
    }

    /// The `the_new_edit_drops_redo` test records a new edit after undo.
    /// Redo is `None`. The next undo returns `a` and cursor 1.
    #[test]
    fn the_new_edit_drops_redo() {
        let mut desk = EditDesk::new();
        let id = cell(1);
        desk.note_prompt(id, "", "a", 1);
        desk.note_prompt(id, "a", "ab", 2);
        let (text, _) = desk.undo(id).expect("undo");
        assert_eq!(text, "a");
        desk.end_apply();
        desk.note_prompt(id, "a", "ac", 2);
        assert!(desk.redo(id).is_none());
        let (text, cursor) = desk.undo(id).expect("undo c");
        assert_eq!(text, "a");
        assert_eq!(cursor, 1);
    }

    /// The `the_limit_drops_the_oldest` test records one more edit than [`LIMIT`].
    /// Undo stops after [`LIMIT`] steps. The remaining text is one character.
    #[test]
    fn the_limit_drops_the_oldest() {
        let mut desk = EditDesk::new();
        let id = cell(1);
        let mut prior = String::new();
        for n in 0..(LIMIT + 1) {
            let mut next = prior.clone();
            next.push('x');
            desk.note_prompt(id, &prior, &next, n + 1);
            prior = next;
        }
        assert_eq!(prior.chars().count(), LIMIT + 1);
        let mut left = prior;
        for _ in 0..LIMIT {
            let (text, _) = desk.undo(id).expect("undo");
            left = text;
        }
        assert!(desk.undo(id).is_none());
        assert_eq!(left.chars().count(), 1);
    }

    /// The `the_apply_records_nothing` test notes text while an undo is still applying.
    /// The note is not recorded. Redo still returns `a`.
    #[test]
    fn the_apply_records_nothing() {
        let mut desk = EditDesk::new();
        let id = cell(1);
        desk.note_prompt(id, "", "a", 1);
        let _ = desk.undo(id);
        desk.note_prompt(id, "a", "ab", 2);
        desk.end_apply();
        let (text, _) = desk.redo(id).expect("redo still there");
        assert_eq!(text, "a");
    }

    /// The `the_same_text_moves_the_cursor` test notes the same text twice.
    /// Undo returns the empty string and cursor 0. The second note did not add a step.
    #[test]
    fn the_same_text_moves_the_cursor() {
        let mut memory = EditMemory::new();
        memory.note("hi", 2);
        memory.note("hi", 1);
        assert!(memory.undo());
        assert_eq!(memory.text(), "");
        assert_eq!(memory.cursor(), 0);
    }

    /// The `the_import_note_restores_the_old_line` test undoes an import note.
    /// Undo returns `old` and cursor 0. Redo returns `imported` and a cursor at the end of that word.
    #[test]
    fn the_import_note_restores_the_old_line() {
        let mut desk = EditDesk::new();
        let id = cell(1);
        desk.note(id, "old", "imported");
        let (text, cursor) = desk.undo(id).expect("undo");
        assert_eq!(text, "old");
        assert_eq!(cursor, 0);
        let (text, cursor) = desk.redo(id).expect("redo");
        assert_eq!(text, "imported");
        assert_eq!(cursor, "imported".chars().count());
    }

    /// The `the_cells_keep_their_own_stacks` test records two cells.
    /// Undoing the first cell returns an empty string. Undoing the second cell returns an empty string.
    #[test]
    fn the_cells_keep_their_own_stacks() {
        let mut desk = EditDesk::new();
        desk.note_prompt(cell(1), "", "a", 1);
        desk.note_prompt(cell(2), "", "b", 1);
        let (text, _) = desk.undo(cell(1)).expect("one");
        assert_eq!(text, "");
        let (text, _) = desk.undo(cell(2)).expect("two");
        assert_eq!(text, "");
    }

    /// The `the_discard_drops_the_stack` test discards a queued span.
    /// After `sync`, undo returns false. The text stays empty.
    #[test]
    fn the_discard_drops_the_stack() {
        let mut memory = EditMemory::new();
        assert!(memory.note_span(0, "", "a", 0, 1));
        memory.discard();
        memory.sync("", 0);
        assert!(!memory.undo());
        assert_eq!(memory.text(), "");
    }

    /// The `the_import_sits_above_the_queued_spans` test notes an import over two queued spans.
    /// The first undo returns `ab`. The second undo returns `a` and cursor 1.
    #[test]
    fn the_import_sits_above_the_queued_spans() {
        let mut desk = EditDesk::new();
        let id = cell(1);
        desk.note_prompt(id, "", "a", 1);
        desk.note_prompt(id, "a", "ab", 2);
        desk.note(id, "ab", "imported");
        let (text, _) = desk.undo(id).expect("import");
        assert_eq!(text, "ab");
        let (text, cursor) = desk.undo(id).expect("b");
        assert_eq!(text, "a");
        assert_eq!(cursor, 1);
    }

    /// The `the_queue_holds_the_span_until_undo` test leaves two spans pending.
    /// The step list is empty until undo. Undo returns `a` and cursor 1. The pending list is then empty. The step list has two entries.
    #[test]
    fn the_queue_holds_the_span_until_undo() {
        let mut memory = EditMemory::new();
        assert!(memory.note_span(0, "", "a", 0, 1));
        assert!(memory.note_span(1, "", "b", 1, 2));
        assert!(memory.pending.len() == 2);
        assert!(memory.steps.is_empty());
        assert!(memory.undo());
        assert_eq!(memory.text(), "a");
        assert_eq!(memory.cursor(), 1);
        assert!(memory.pending.is_empty());
        assert_eq!(memory.steps.len(), 2);
    }
}
