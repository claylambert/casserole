# struct EditDesk

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **edit**

**Source** `rust/aetheris/aetheris-client/src/edit.rs`

```rust
pub(crate) struct EditDesk
```

The [`EditDesk`](struct.EditDesk.md) struct keeps one [`EditMemory`](struct.EditMemory.md) for each canvas cell.
The pair is the cell id and that memory. `applying` is true after undo or redo until `end_apply`. `caret` is the caret from the last undo or redo, kept until one read. A deleted cell drops its stack.

| | |
| --- | --- |
| **Specification** | PK-1172 docs/specs/rust/aetheris/aetheris-client/src/edit.spec.md |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| Vec<([u8 16], EditMemory)> | [`seats`](#member-seats) |  |
| bool | [`applying`](#member-applying) |  |
| Option<([u8 16], usize)> | [`caret`](#member-caret) |  |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`new`](#method-new) | The `new` method returns an empty [`EditDesk`]. |
| fn | [`applying`](#method-applying) | The `applying` method reports whether the desk is writing an undo or a redo back. |
| fn | [`seat`](#method-seat) | The `seat` method returns the [`EditMemory`] for `cell`. |
| fn | [`find`](#method-find) | The `find` method returns the [`EditMemory`] for `cell` when one exists. |
| fn | [`note_prompt`](#method-note_prompt) | The `note_prompt` method records one prompt change as a span. |
| fn | [`note`](#method-note) | The `note` method records a whole-text change as one step. |
| fn | [`undo`](#method-undo) | The `undo` method rewinds the stack for `cell`. |
| fn | [`redo`](#method-redo) | The `redo` method replays the stack for `cell`. |
| fn | [`take_caret`](#method-take_caret) | The `take_caret` method returns the caret from the last undo or redo for `cell`. |
| fn | [`end_apply`](#method-end_apply) | The `end_apply` method clears applying on the desk and on every memory. |
| fn | [`drop_cell`](#method-drop_cell) | The `drop_cell` method removes the stack for `cell`. |
| fn | [`clear`](#method-clear) | The `clear` method drops every cell stack. |

## Field Detail

### seats

<a id="member-seats"></a>

```rust
seats: Vec<([u8; 16], EditMemory)>
```

### applying

<a id="member-applying"></a>

```rust
applying: bool
```

### caret

<a id="member-caret"></a>

```rust
caret: Option<([u8; 16], usize)>
```

## Method Detail

### new

<a id="method-new"></a>

```rust
pub(crate) fn new() -> Self
```

The `new` method returns an empty [`EditDesk`](struct.EditDesk.md).
No cell has a stack. Applying is false. No caret is waiting.

### applying

<a id="method-applying"></a>

```rust
pub(crate) fn applying(&self) -> bool
```

The `applying` method reports whether the desk is writing an undo or a redo back.
True means `note_prompt` and `note` return without recording.

### seat

<a id="method-seat"></a>

```rust
fn seat(&mut self, cell: [u8; 16]) -> &mut EditMemory
```

The `seat` method returns the [`EditMemory`](struct.EditMemory.md) for `cell`.
A new id gets an empty memory. An id already stored returns that memory.

### find

<a id="method-find"></a>

```rust
fn find(&mut self, cell: [u8; 16]) -> Option<&mut EditMemory>
```

The `find` method returns the [`EditMemory`](struct.EditMemory.md) for `cell` when one exists.
A missing id returns `None`. No memory is created.

### note_prompt

<a id="method-note_prompt"></a>

```rust
pub(crate) fn note_prompt(&mut self, cell: [u8; 16], before: &str, after: &str, dot: usize)
```

The `note_prompt` method records one prompt change as a span.
The caret after the change is `dot`. Applying returns without a record. Matching texts return without a record. A memory whose text is not `before` is synced to `before` with the caret at 0 before the span is queued.

### note

<a id="method-note"></a>

```rust
pub(crate) fn note(&mut self, cell: [u8; 16], before: &str, after: &str)
```

The `note` method records a whole-text change as one step.
Import uses this. Applying returns without a record. Matching texts return without a record. The caret stored with the change is the character count of `after`.

### undo

<a id="method-undo"></a>

```rust
pub(crate) fn undo(&mut self, cell: [u8; 16]) -> Option<(String, usize)>
```

The `undo` method rewinds the stack for `cell`.
A missing cell returns `None`. No step to rewind returns `None`. A rewind returns the text and the caret. Applying becomes true. The caret is kept for `take_caret`.

### redo

<a id="method-redo"></a>

```rust
pub(crate) fn redo(&mut self, cell: [u8; 16]) -> Option<(String, usize)>
```

The `redo` method replays the stack for `cell`.
A missing cell returns `None`. No step to replay returns `None`. A replay returns the text and the caret. Applying becomes true. The caret is kept for `take_caret`.

### take_caret

<a id="method-take_caret"></a>

```rust
pub(crate) fn take_caret(&mut self, cell: [u8; 16]) -> Option<usize>
```

The `take_caret` method returns the caret from the last undo or redo for `cell`.
One read clears it. A caret stored for a different cell returns `None` and is left in place.

### end_apply

<a id="method-end_apply"></a>

```rust
pub(crate) fn end_apply(&mut self)
```

The `end_apply` method clears applying on the desk and on every memory.
The stacks stay. A stored caret stays.

### drop_cell

<a id="method-drop_cell"></a>

```rust
pub(crate) fn drop_cell(&mut self, cell: &[u8; 16])
```

The `drop_cell` method removes the stack for `cell`.
A caret stored for that cell is cleared. Other cells stay.

### clear

<a id="method-clear"></a>

```rust
pub(crate) fn clear(&mut self)
```

The `clear` method drops every cell stack.
Applying becomes false. No caret remains.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
