# struct TurnCard

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **chrome**

**Source** `rust/aetheris/aetheris-client/src/chrome.rs`

```rust
struct TurnCard
```

One in-memory card. The cell id lasts for the connect.
`group` 0 is an ordinary card. A merge is `group != 0` and `column == 0`.
A member is `group != 0` and `column > 0`.

| | |
| --- | --- |
| **Specification** | GK-0021 aetheris-stage-2-finessing.001.md FINESSE:f-222 |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| [u8 16] | [`cell`](#member-cell) |  |
| String | [`prompt`](#member-prompt) |  |
| String | [`reply`](#member-reply) |  |
| String | [`fault`](#member-fault) |  |
| bool | [`running`](#member-running) |  |
| Option<Instant> | [`started`](#member-started) | When this run started. The digits are painted from it. |
| Mark | [`mark`](#member-mark) |  |
| u64 | [`group`](#member-group) |  |
| u32 | [`column`](#member-column) |  |
| bool | [`collapsed`](#member-collapsed) | Folded for this window. The prompt and the reply stay stored. |
| bool | [`locked`](#member-locked) | Locked head. Both halves this card sends ride with the head. |
| bool | [`ephemeral`](#member-ephemeral) | Burner. The prompt and the reply stay. A cloud run is denied. |
| String | [`target`](#member-target) | Execution word. Empty sends as ollama and is not written back as that name. |
| bool | [`ollama_on`](#member-ollama_on) | Ollama Thinking for this window. Off leaves `think` unset. |
| String | [`ollama_level`](#member-ollama_level) | Ollama effort level. Empty sends `think: true` when the box is on. |
| String | [`ollama_id`](#member-ollama_id) | Ollama id remembered when this cell leaves that family. |
| bool | [`grok_on`](#member-grok_on) | Grok Thinking for this window. Off drops kind `t` for Grok. |
| bool | [`pool_on`](#member-pool_on) | Pool Thinking for this window. |
| bool | [`bar_open`](#member-bar_open) | The group row is open for this window. It starts closed. |
| bool | [`expanded`](#member-expanded) | The reply shows in full. Unchecked, the reply window matches the prompt window. A fresh canvas and Clear start unchecked. New keeps the prior card's choice. A real cell id is saved. |
| String | [`parent_cell_id`](#member-parent_cell_id) | Vault parent. Empty is none. Export still writes an empty parent. |
| String | [`branch_id`](#member-branch_id) | Vault branch. A fresh card is main. |
| i64 | [`created_ms`](#member-created_ms) | Vault clock. A fresh card stamps now. |
| String | [`vault_status`](#member-vault_status) | Last status token written for this card. |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`fresh`](#method-fresh) | The method `fresh` returns a new [`TurnCard`] for one counter. |

## Field Detail

### cell

<a id="member-cell"></a>

```rust
cell: [u8; 16]
```

### prompt

<a id="member-prompt"></a>

```rust
prompt: String
```

### reply

<a id="member-reply"></a>

```rust
reply: String
```

### fault

<a id="member-fault"></a>

```rust
fault: String
```

### running

<a id="member-running"></a>

```rust
running: bool
```

### started

<a id="member-started"></a>

```rust
started: Option<Instant>
```

When this run started. The digits are painted from it.

### mark

<a id="member-mark"></a>

```rust
mark: Mark
```

### group

<a id="member-group"></a>

```rust
group: u64
```

### column

<a id="member-column"></a>

```rust
column: u32
```

### collapsed

<a id="member-collapsed"></a>

```rust
collapsed: bool
```

Folded for this window. The prompt and the reply stay stored.

### locked

<a id="member-locked"></a>

```rust
locked: bool
```

Locked head. Both halves this card sends ride with the head.

### ephemeral

<a id="member-ephemeral"></a>

```rust
ephemeral: bool
```

Burner. The prompt and the reply stay. A cloud run is denied.

### target

<a id="member-target"></a>

```rust
target: String
```

Execution word. Empty sends as ollama and is not written back as that name.

### ollama_on

<a id="member-ollama_on"></a>

```rust
ollama_on: bool
```

Ollama Thinking for this window. Off leaves `think` unset.

### ollama_level

<a id="member-ollama_level"></a>

```rust
ollama_level: String
```

Ollama effort level. Empty sends `think: true` when the box is on.

### ollama_id

<a id="member-ollama_id"></a>

```rust
ollama_id: String
```

Ollama id remembered when this cell leaves that family.

### grok_on

<a id="member-grok_on"></a>

```rust
grok_on: bool
```

Grok Thinking for this window. Off drops kind `t` for Grok.

### pool_on

<a id="member-pool_on"></a>

```rust
pool_on: bool
```

Pool Thinking for this window.

### bar_open

<a id="member-bar_open"></a>

```rust
bar_open: bool
```

The group row is open for this window. It starts closed.

### expanded

<a id="member-expanded"></a>

```rust
expanded: bool
```

The reply shows in full. Unchecked, the reply window matches the prompt window. A fresh canvas and Clear start unchecked. New keeps the prior card's choice. A real cell id is saved.

### parent_cell_id

<a id="member-parent_cell_id"></a>

```rust
parent_cell_id: String
```

Vault parent. Empty is none. Export still writes an empty parent.

### branch_id

<a id="member-branch_id"></a>

```rust
branch_id: String
```

Vault branch. A fresh card is main.

### created_ms

<a id="member-created_ms"></a>

```rust
created_ms: i64
```

Vault clock. A fresh card stamps now.

### vault_status

<a id="member-vault_status"></a>

```rust
vault_status: String
```

Last status token written for this card.

## Method Detail

### fresh

<a id="method-fresh"></a>

```rust
fn fresh(n: u64) -> Self
```

The method `fresh` returns a new [`TurnCard`](struct.TurnCard.md) for one counter.
The counter `n` is passed to `mint_cell`. That result is the cell id.

The prompt is empty. The reply is empty. The fault is empty. The target is empty. `running` is false. `started` is absent.
The mark is [`Mark::Included`](enum.Mark.md#member-included). `group` is zero. `column` is zero. The card is not collapsed. The card is not locked. The card is not ephemeral.
The Ollama switch is off. The Grok switch is off. The pool switch is off. The Ollama level is empty. The remembered Ollama id is empty.
The group bar is closed. Expanded view is unchecked. The vault parent is empty. The branch id is `main`.
`created_ms` comes from `card_stamp`. That call reads milliseconds since the Unix epoch. A clock before the epoch yields zero. `vault_status` is empty.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
