# struct MoodDesk

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **chrome**

**Source** `aetheris/rust/aetheris-client/src/chrome.rs`

```rust
struct MoodDesk
```

The struct `MoodDesk` is the mood glass for one selected card.
`open` is true while the dialog is showing. `card_index` is that card's place in the deck. `card_cell` is the card's sixteen-byte id. `turns` is the chat. `line` is the typed send. `draft` is the mood-string field.
`tries` counts replies that did not carry a mood line. `busy` is true while a reply is in flight. `current` is the wire id of that send. `wires` keeps every mood wire id. Those ids stay after the dialog closes so a late frame stays off the cell.
`started` is the instant of the send that is in flight. `place` asks the next paint to center the dialog. `generation` changes each time the glass opens on a card. `arm_line` asks the next paint to focus the typed line. `last_size` is the width and height kept for the next open.

| | |
| --- | --- |
| **Specification** | L40 aa-sdd-source-prose.md#MoodDesk |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| bool | [`open`](#member-open) |  |
| usize | [`card_index`](#member-card_index) |  |
| [u8 16] | [`card_cell`](#member-card_cell) |  |
| Vec<MoodTurn> | [`turns`](#member-turns) |  |
| String | [`line`](#member-line) |  |
| String | [`draft`](#member-draft) |  |
| u32 | [`tries`](#member-tries) |  |
| bool | [`busy`](#member-busy) |  |
| Option<[u8 16]> | [`current`](#member-current) |  |
| Vec<[u8 16]> | [`wires`](#member-wires) |  |
| Option<Instant> | [`started`](#member-started) |  |
| i32 | [`slide`](#member-slide) | The field `slide` is the animated height of the rising mood pane. |
| bool | [`place`](#member-place) |  |
| u64 | [`generation`](#member-generation) |  |
| bool | [`arm_line`](#member-arm_line) |  |
| Option<(i32, i32)> | [`last_size`](#member-last_size) |  |
| bool | [`clear_ask`](#member-clear_ask) | The field `clear_ask` is true while the clear question is showing. |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`shut`](#method-shut) | The method `shut` returns a [`MoodDesk`] with the glass closed. |
| fn | [`open_on`](#method-open_on) | The method `open_on` opens the mood glass on one card and drops the previous chat. |
| fn | [`close_glass`](#method-close_glass) | The method `close_glass` drops the chat and closes the dialog. |
| fn | [`clear_chat`](#method-clear_chat) | The method `clear_chat` drops this chat and leaves the dialog open. |

## Field Detail

### open

<a id="member-open"></a>

```rust
open: bool
```

### card_index

<a id="member-card_index"></a>

```rust
card_index: usize
```

### card_cell

<a id="member-card_cell"></a>

```rust
card_cell: [u8; 16]
```

### turns

<a id="member-turns"></a>

```rust
turns: Vec<MoodTurn>
```

### line

<a id="member-line"></a>

```rust
line: String
```

### draft

<a id="member-draft"></a>

```rust
draft: String
```

### tries

<a id="member-tries"></a>

```rust
tries: u32
```

### busy

<a id="member-busy"></a>

```rust
busy: bool
```

### current

<a id="member-current"></a>

```rust
current: Option<[u8; 16]>
```

### wires

<a id="member-wires"></a>

```rust
wires: Vec<[u8; 16]>
```

### started

<a id="member-started"></a>

```rust
started: Option<Instant>
```

### slide

<a id="member-slide"></a>

```rust
slide: i32
```

The field `slide` is the animated height of the rising mood pane.
The value `-1` means the pane is hidden. A value of 0 is the shut height before the pane hides. Any higher value is the pane's current height in pixels.

### place

<a id="member-place"></a>

```rust
place: bool
```

### generation

<a id="member-generation"></a>

```rust
generation: u64
```

### arm_line

<a id="member-arm_line"></a>

```rust
arm_line: bool
```

### last_size

<a id="member-last_size"></a>

```rust
last_size: Option<(i32, i32)>
```

### clear_ask

<a id="member-clear_ask"></a>

```rust
clear_ask: bool
```

The field `clear_ask` is true while the clear question is showing.
The glass behind that question does not send, approve, cancel, or clear.

## Method Detail

### shut

<a id="method-shut"></a>

```rust
fn shut() -> Self
```

The method `shut` returns a [`MoodDesk`](struct.MoodDesk.md) with the glass closed.
The dialog is closed. The card index is 0. The card id is sixteen zero bytes. The chat, the typed line, and the draft are empty. The try count is 0. No reply is in flight. No wire id is current. The wire list is empty.
No send instant is stored. The rising pane is hidden. The next open will place the dialog. The generation is 0. The typed line is not armed. No size is kept. The clear question is closed.

### open_on

<a id="method-open_on"></a>

```rust
fn open_on(&mut self, index: usize, cell: [u8; 16])
```

The method `open_on` opens the mood glass on one card and drops the previous chat.
`index` is the card's place in the deck. `cell` is that card's sixteen-byte id. The wire list is kept. The generation increases by 1, with wrap. Every other field is reset through `shut`, and then the wires and the generation are put back.
The dialog is opened. The card index and the card id are stored. The next paint places the dialog. The next paint arms the typed line.

### close_glass

<a id="method-close_glass"></a>

```rust
fn close_glass(&mut self)
```

The method `close_glass` drops the chat and closes the dialog.
The wire list stays, so a late frame stays off the cell. The generation stays. Every other field is reset through `shut`, and then the wires and the generation are put back.
The saved mood string stays. The mood switch stays. This method does not write the mood file.

### clear_chat

<a id="method-clear_chat"></a>

```rust
fn clear_chat(&mut self)
```

The method `clear_chat` drops this chat and leaves the dialog open.
The turns, the typed line, and the draft are cleared. The try count is 0. The in-flight flag is cleared. The current wire id is cleared. The send instant is cleared. The rising pane is hidden. The clear question is closed.
The wire list stays. The kept size stays. The card index and the card id stay. A reply already in flight stays off the glass, because the current wire id no longer matches it.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
