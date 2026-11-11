# struct EditMemory

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **edit**

**Source** `aetheris/rust/aetheris-client/src/edit.rs`

```rust
pub(crate) struct EditMemory
```

The [`EditMemory`](struct.EditMemory.md) struct is the undo stack for one editor.
[`EditDesk`](struct.EditDesk.md) keeps one of these for each canvas cell. `steps` is the stack. `next` is the index of the next redo. `pending` is the queue. `last` is the text. `last_cursor` is the caret. `applying` is true while undo or redo is writing the text back.

| | |
| --- | --- |
| **Specification** | GK-0146 aetheris-plan-f-214.001.md FINESSE:f-214 |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| Vec<Step> | [`steps`](#member-steps) |  |
| usize | [`next`](#member-next) |  |
| Vec<Span> | [`pending`](#member-pending) |  |
| String | [`last`](#member-last) |  |
| usize | [`last_cursor`](#member-last_cursor) |  |
| bool | [`applying`](#member-applying) |  |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`new`](#method-new) | The `new` method returns an empty [`EditMemory`]. |
| fn | [`applying`](#method-applying) | The `applying` method reports whether undo or redo is writing the text back. |
| fn | [`end_apply`](#method-end_apply) | The `end_apply` method clears the applying flag. |
| fn | [`text`](#method-text) | The `text` method returns the text this memory last stored. |
| fn | [`cursor`](#method-cursor) | The `cursor` method returns the caret clamped to the text. |
| fn | [`sync`](#method-sync) | The `sync` method remembers `text` without pushing a step. |
| fn | [`discard`](#method-discard) | The `discard` method drops the queue, the stack, and the applying flag. |
| fn | [`note_span`](#method-note_span) | The `note_span` method queues one insert or remove. |
| fn | [`note`](#method-note) | The `note` method pushes one whole-text step. |
| fn | [`undo`](#method-undo) | The `undo` method rewinds one step. |
| fn | [`redo`](#method-redo) | The `redo` method replays one step. |
| fn | [`can_redo`](#method-can_redo) | The `can_redo` method reports whether a redo step is waiting. |
| fn | [`drain`](#method-drain) | The `drain` method moves every queued span onto the stack. |
| fn | [`add_span`](#method-add_span) | The `add_span` method pushes one span as a stack step. |
| fn | [`add_step`](#method-add_step) | The `add_step` method pushes `step` and drops the redo tail. |

## Field Detail

### steps

<a id="member-steps"></a>

```rust
steps: Vec<Step>
```

### next

<a id="member-next"></a>

```rust
next: usize
```

### pending

<a id="member-pending"></a>

```rust
pending: Vec<Span>
```

### last

<a id="member-last"></a>

```rust
last: String
```

### last_cursor

<a id="member-last_cursor"></a>

```rust
last_cursor: usize
```

### applying

<a id="member-applying"></a>

```rust
applying: bool
```

## Method Detail

### new

<a id="method-new"></a>

```rust
fn new() -> Self
```

The `new` method returns an empty [`EditMemory`](struct.EditMemory.md).
The text is empty. The caret is 0. The stack and the queue are empty.

### applying

<a id="method-applying"></a>

```rust
fn applying(&self) -> bool
```

The `applying` method reports whether undo or redo is writing the text back.
True means a new span is not recorded.

### end_apply

<a id="method-end_apply"></a>

```rust
fn end_apply(&mut self)
```

The `end_apply` method clears the applying flag.
The stack and the text are left as they are.

### text

<a id="method-text"></a>

```rust
fn text(&self) -> &str
```

The `text` method returns the text this memory last stored.
That text is the value after the last recorded edit, undo, or redo.

### cursor

<a id="method-cursor"></a>

```rust
fn cursor(&self) -> usize
```

The `cursor` method returns the caret clamped to the text.
The index counts characters. A stored caret past the end becomes the character count.

### sync

<a id="method-sync"></a>

```rust
fn sync(&mut self, text: &str, cursor: usize)
```

The `sync` method remembers `text` without pushing a step.
The caret is clamped to the character count of `text`. The stack is left as it is.

### discard

<a id="method-discard"></a>

```rust
fn discard(&mut self)
```

The `discard` method drops the queue, the stack, and the applying flag.
The stored text is left as it is.

### note_span

<a id="method-note_span"></a>

```rust
fn note_span(
    &mut self,
    offset: usize,
    removed: &str,
    inserted: &str,
    cursor_before: usize,
    cursor_after: usize,
) -> bool
```

The `note_span` method queues one insert or remove.
The caller updates the live widget and returns. The stack receives the span when undo or redo runs, or sooner when a redo is still available or the queue passes [`LIMIT`](../edit.md#constant-limit).

The method returns false while applying. It returns false when `offset` is past the stored text. It returns false when the removed text runs past the stored text. It returns false when both the removed text and the inserted text are empty. A true result stores the spliced text and the caret after the change.

### note

<a id="method-note"></a>

```rust
fn note(&mut self, text: &str, cursor: usize)
```

The `note` method pushes one whole-text step.
The same text only moves the caret. While applying, the text and the caret are stored and no step is pushed. A real change drains the queue, drops any redo tail, and pushes a `Note`.

### undo

<a id="method-undo"></a>

```rust
fn undo(&mut self) -> bool
```

The `undo` method rewinds one step.
The queue is drained first. No step to rewind returns false. A rewind sets applying and returns true. The stored text and caret become the values from before that step.

### redo

<a id="method-redo"></a>

```rust
fn redo(&mut self) -> bool
```

The `redo` method replays one step.
The queue is drained first. No step to replay returns false. A replay sets applying and returns true. The stored text and caret become the values from after that step.

### can_redo

<a id="method-can_redo"></a>

```rust
fn can_redo(&self) -> bool
```

The `can_redo` method reports whether a redo step is waiting.
True means `next` is still inside `steps`.

### drain

<a id="method-drain"></a>

```rust
fn drain(&mut self)
```

The `drain` method moves every queued span onto the stack.
The queue is empty afterward. Each span is added in order.

### add_span

<a id="method-add_span"></a>

```rust
fn add_span(&mut self, span: Span)
```

The `add_span` method pushes one span as a stack step.
The redo tail is dropped by `add_step`.

### add_step

<a id="method-add_step"></a>

```rust
fn add_step(&mut self, step: Step)
```

The `add_step` method pushes `step` and drops the redo tail.
Steps past [`LIMIT`](../edit.md#constant-limit) leave from the front. `next` moves with that drop so it still points at the end.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
