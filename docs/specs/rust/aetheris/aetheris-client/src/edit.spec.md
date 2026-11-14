# rust.aetheris.aetheris-client.src.edit

## 4. The seat

The Rust canvas prompt keeps its own undo seat. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo puts the caret where the edit began. Redo restores the caret after the edit. A paste is one span. Import is one whole-text step. The limit is 100. The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The widget timer undoer is cleared. [edit.rs](../../../../../../rust/aetheris/aetheris-client/src/edit.rs) holds EditMemory and EditDesk. LIMIT is 100. note_span queues a span. undo and redo drain the queue, then step. A span undo seats cursor_before. A span redo seats cursor_after. note drains first, so an import sits above any queued spans. The same text only moves the caret. note_prompt diffs one contiguous span. A paste or a selection replace is one span. take_caret returns the armed caret for that cell. The prompt seats that caret before the widget shows and again after it shows, then clears the widget undoer. Applying writes nothing. Each cell keeps its own stack.
**semantic-key:** PK-591

## A canvas prompt keeps a span queue and a caret seat

A canvas prompt keeps a span queue and a caret seat. Undo puts the caret where the edit began. Redo restores the caret after the edit.

**semantic-key:** PK-1172
**semantic-key:** PK-1172
**semantic-key:** PK-1172
**semantic-key:** PK-1172
**semantic-key:** PK-1172
**semantic-key:** PK-1172
**semantic-key:** PK-1172
