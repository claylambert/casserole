# struct ParkedPane

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **chrome**

**Source** `rust/aetheris/aetheris-client/src/chrome.rs`

```rust
struct ParkedPane
```

The `ParkedPane` struct is one terminal that is not the pane in front.
The focused pane keeps its grid and session on [`App`](struct.App.md). This struct keeps the same kind of state for every other pane.

`id` is the pane id. `grid` is that pane's [`Grid`](../../aetheris-term/grid/struct.Grid.md). `session` is the SSH session, or `None` when the pane is down. `link` is the [`PaneLink`](enum.PaneLink.md). `at_tail` is true when the view sits on the tail. `anchor` is the selection start. `current` is the selection end. Either end is `None` when that end is absent.

`scroll_lines` is a menu page or line motion that is still waiting. A positive count moves toward the tail. `scroll_base` is the offset that motion starts from. `None` uses the focused scroll area. `menu_jump` is true when Clear Buffer asked the next paint to sit on the tail.

`scope_stash` holds scope pieces waiting for the canvas. The bool is true once this pane offered the console thought. `paint_stash` holds paint bytes waiting for the grid. `osc_hold` holds the tail of a split model sequence. `last` is the [`ConnectSnap`](struct.ConnectSnap.md) from the last connect on this pane.

`vt_think` is this session's thought text. `vt_open` is true when this session's strip is showing. `vt_slide` is the rise from the top. `0` starts shut. `-1` is settled. `vt_folded` is true when the strip is folded. `vt_height` is the dragged height. Zero uses the three-line view. `vt_think_on` is true when this session's thinking is on.

`roux_used` is the Roux session count. `None` means the reply has not been held. `roux_chars` is the character count in those messages. `roux_blocks` is the stored message count. `tokens_asked` is true after the `session_tokens` ask has been sent. `live_line` is the text typed on this pane and not yet submitted.
`engine_label` is the model this pane last reported. Empty prints as ollama. A canvas cell is not this word. `engine_asked` is true after this connection has been asked for that model. A disconnect clears the ask and leaves the word.

| | |
| --- | --- |
| **Specification** | PK-1104 docs/specs/common/formal-plan.spec.md |
| **Specification** | PK-1103 docs/specs/common/formal-plan.spec.md |
| **Specification** | PK-1102 docs/specs/common/formal-plan.spec.md |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| u64 | [`id`](#member-id) |  |
| Grid | [`grid`](#member-grid) |  |
| Option<Session> | [`session`](#member-session) |  |
| PaneLink | [`link`](#member-link) |  |
| bool | [`at_tail`](#member-at_tail) |  |
| Option<(usize, usize)> | [`anchor`](#member-anchor) |  |
| Option<(usize, usize)> | [`current`](#member-current) |  |
| i32 | [`scroll_lines`](#member-scroll_lines) | Menu Page or Line motion waiting for this pane. Positive moves toward the tail. |
| Option<f32> | [`scroll_base`](#member-scroll_base) | Offset that motion starts from. None uses the focused scroll area. |
| bool | [`menu_jump`](#member-menu_jump) | Clear Buffer asked the next paint to sit on the tail. |
| VecDeque<(ScopePiece, bool)> | [`scope_stash`](#member-scope_stash) | Scope waiting for the canvas. The bool is true once this pane offered the console thought. |
| VecDeque<Vec<u8>> | [`paint_stash`](#member-paint_stash) |  |
| crate::model::OscHold | [`osc_hold`](#member-osc_hold) |  |
| Option<ConnectSnap> | [`last`](#member-last) |  |
| ThinkLog | [`vt_think`](#member-vt_think) | This session's thought text. The height stays on this session. |
| bool | [`vt_open`](#member-vt_open) | This session's strip is on the plate. A thought opens it. |
| f32 | [`vt_slide`](#member-vt_slide) | Rise from the top of this session. `0` starts shut. `-1` is settled. |
| bool | [`vt_folded`](#member-vt_folded) | This session's strip is folded. Close returns the mark to `v` on this session. |
| f32 | [`vt_height`](#member-vt_height) | This session's dragged height. Zero uses the three-line view. |
| bool | [`vt_think_on`](#member-vt_think_on) | This session's thinking is on. Another session's shell result does not clear it. |
| Option<i32> | [`roux_used`](#member-roux_used) | Roux session count for this pane. None until the reply is held. |
| i32 | [`roux_chars`](#member-roux_chars) | Characters in the held Roux messages. |
| i32 | [`roux_blocks`](#member-roux_blocks) | Stored Roux messages in the held count. |
| bool | [`tokens_asked`](#member-tokens_asked) | The session_tokens ask has been sent for this pane. |
| String | [`live_line`](#member-live_line) | Characters typed on this pane and not yet submitted. |
| String | [`engine_label`](#member-engine_label) | This pane's selected model. Empty prints as ollama. A canvas cell is not this word. |
| bool | [`engine_asked`](#member-engine_asked) | This connection has been asked for the saved model. A disconnect clears it. |

## Field Detail

### id

<a id="member-id"></a>

```rust
id: u64
```

### grid

<a id="member-grid"></a>

```rust
grid: Grid
```

### session

<a id="member-session"></a>

```rust
session: Option<Session>
```

### link

<a id="member-link"></a>

```rust
link: PaneLink
```

### at_tail

<a id="member-at_tail"></a>

```rust
at_tail: bool
```

### anchor

<a id="member-anchor"></a>

```rust
anchor: Option<(usize, usize)>
```

### current

<a id="member-current"></a>

```rust
current: Option<(usize, usize)>
```

### scroll_lines

<a id="member-scroll_lines"></a>

```rust
scroll_lines: i32
```

Menu Page or Line motion waiting for this pane. Positive moves toward the tail.

### scroll_base

<a id="member-scroll_base"></a>

```rust
scroll_base: Option<f32>
```

Offset that motion starts from. None uses the focused scroll area.

### menu_jump

<a id="member-menu_jump"></a>

```rust
menu_jump: bool
```

Clear Buffer asked the next paint to sit on the tail.

### scope_stash

<a id="member-scope_stash"></a>

```rust
scope_stash: VecDeque<(ScopePiece, bool)>
```

Scope waiting for the canvas. The bool is true once this pane offered the console thought.

### paint_stash

<a id="member-paint_stash"></a>

```rust
paint_stash: VecDeque<Vec<u8>>
```

### osc_hold

<a id="member-osc_hold"></a>

```rust
osc_hold: crate::model::OscHold
```

### last

<a id="member-last"></a>

```rust
last: Option<ConnectSnap>
```

### vt_think

<a id="member-vt_think"></a>

```rust
vt_think: ThinkLog
```

This session's thought text. The height stays on this session.

### vt_open

<a id="member-vt_open"></a>

```rust
vt_open: bool
```

This session's strip is on the plate. A thought opens it.

### vt_slide

<a id="member-vt_slide"></a>

```rust
vt_slide: f32
```

Rise from the top of this session. `0` starts shut. `-1` is settled.

### vt_folded

<a id="member-vt_folded"></a>

```rust
vt_folded: bool
```

This session's strip is folded. Close returns the mark to `v` on this session.

### vt_height

<a id="member-vt_height"></a>

```rust
vt_height: f32
```

This session's dragged height. Zero uses the three-line view.

### vt_think_on

<a id="member-vt_think_on"></a>

```rust
vt_think_on: bool
```

This session's thinking is on. Another session's shell result does not clear it.

### roux_used

<a id="member-roux_used"></a>

```rust
roux_used: Option<i32>
```

Roux session count for this pane. None until the reply is held.

| | |
| --- | --- |
| **Specification** | L40 aa-sdd-source-prose.md FINESSE:f-163 |

### roux_chars

<a id="member-roux_chars"></a>

```rust
roux_chars: i32
```

Characters in the held Roux messages.

### roux_blocks

<a id="member-roux_blocks"></a>

```rust
roux_blocks: i32
```

Stored Roux messages in the held count.

### tokens_asked

<a id="member-tokens_asked"></a>

```rust
tokens_asked: bool
```

The session_tokens ask has been sent for this pane.

### live_line

<a id="member-live_line"></a>

```rust
live_line: String
```

Characters typed on this pane and not yet submitted.

### engine_label

<a id="member-engine_label"></a>

```rust
engine_label: String
```

This pane's selected model. Empty prints as ollama. A canvas cell is not this word.

### engine_asked

<a id="member-engine_asked"></a>

```rust
engine_asked: bool
```

This connection has been asked for the saved model. A disconnect clears it.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
