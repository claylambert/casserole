# struct ThinkPaneManager

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **chrome**

**Source** `rust/aetheris/aetheris-client/src/chrome.rs`

```rust
struct ThinkPaneManager
```

The struct `ThinkPaneManager` is the one store of thinking panes.
A canvas key is the user, a separator, and `k:` plus the canvas id.
An SSH key is the user, a separator, and `s:` plus the pane id.
The same key returns the same seat. A different user has a different seat.
`offer` writes that seat and opens it. A fold stays folded.
`finish` ends the block and leaves the seat. `hide` closes the chrome and leaves the seat.
`release` drops the seat. Close does not call `release`. A reply does not call `release`.

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| std::collections::HashMap<String | [`seats`](#member-seats) |  |
| > | [`ThinkSeat`](#member-thinkseat) |  |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`new`](#method-new) | The function `new` builds an empty manager. |
| fn | [`canvas_key`](#method-canvas_key) | The function `canvas_key` joins a user and a canvas id. |
| fn | [`ssh_key`](#method-ssh_key) | The function `ssh_key` joins a user and an SSH pane id. |
| fn | [`contains`](#method-contains) | The function `contains` reports whether `key` has a seat. |
| fn | [`acquire_canvas`](#method-acquire_canvas) | The function `acquire_canvas` returns the canvas seat for `key`, creating it once. |
| fn | [`acquire_ssh`](#method-acquire_ssh) | The function `acquire_ssh` returns the SSH seat for `key`, creating it once. |
| fn | [`insert_canvas`](#method-insert_canvas) | The function `insert_canvas` stores a loaded canvas seat at `key`. |
| fn | [`rekey`](#method-rekey) | The function `rekey` moves a seat from `from` to `to`. |
| fn | [`blocks`](#method-blocks) | The function `blocks` copies the log blocks for `key`. |
| fn | [`chrome_copy`](#method-chrome_copy) | The function `chrome_copy` copies the chrome for `key`. |
| fn | [`write_chrome`](#method-write_chrome) | The function `write_chrome` stores `chrome` on an existing seat. |
| fn | [`offer`](#method-offer) | The function `offer` writes one chunk on the seat for `key` and opens that seat. |
| fn | [`finish`](#method-finish) | The function `finish` ends the open block on `key`. |
| fn | [`hide`](#method-hide) | The function `hide` takes the seat off the plate. |
| fn | [`clear_log`](#method-clear_log) | The function `clear_log` wipes the text and leaves the strip open. |
| fn | [`release`](#method-release) | The function `release` drops the seat for `key`. |
| fn | [`log_text`](#method-log_text) | The function `log_text` renders the log for `key`. |

## Field Detail

### seats

<a id="member-seats"></a>

```rust
seats: std::collections::HashMap<String
```

### ThinkSeat

<a id="member-thinkseat"></a>

```rust
ThinkSeat>
```

## Method Detail

### new

<a id="method-new"></a>

```rust
fn new() -> Self
```

The function `new` builds an empty manager.

### canvas_key

<a id="method-canvas_key"></a>

```rust
fn canvas_key(user: &str, canvas_id: &str) -> String
```

The function `canvas_key` joins a user and a canvas id.
`user` is the sanitized user. `canvas_id` is the canvas id.
The separator is U+0001. The canvas mark is `k:`.

### ssh_key

<a id="method-ssh_key"></a>

```rust
fn ssh_key(user: &str, surface_id: &str) -> String
```

The function `ssh_key` joins a user and an SSH pane id.
`user` is the sanitized user. `surface_id` is the pane id.
The separator is U+0001. The pane mark is `s:`.

### contains

<a id="method-contains"></a>

```rust
fn contains(&self, key: &str) -> bool
```

The function `contains` reports whether `key` has a seat.

### acquire_canvas

<a id="method-acquire_canvas"></a>

```rust
fn acquire_canvas(&mut self, key: &str) -> &mut ThinkSeat
```

The function `acquire_canvas` returns the canvas seat for `key`, creating it once.

### acquire_ssh

<a id="method-acquire_ssh"></a>

```rust
fn acquire_ssh(&mut self, key: &str, seed_height: f32) -> &mut ThinkSeat
```

The function `acquire_ssh` returns the SSH seat for `key`, creating it once.
`seed_height` is the file height used the first time. A later call keeps the seat height.

### insert_canvas

<a id="method-insert_canvas"></a>

```rust
fn insert_canvas(&mut self, key: &str, log: ThinkLog, chrome: ThinkChrome)
```

The function `insert_canvas` stores a loaded canvas seat at `key`.
A key that already has a seat keeps that seat.

### rekey

<a id="method-rekey"></a>

```rust
fn rekey(&mut self, from: &str, to: &str)
```

The function `rekey` moves a seat from `from` to `to`.
A missing source stays. A destination that already has a seat stays.
The same two keys stay.

### blocks

<a id="method-blocks"></a>

```rust
fn blocks(&self, key: &str) -> Vec<ThinkBlock>
```

The function `blocks` copies the log blocks for `key`.
A missing key returns an empty list and does not create a seat.

### chrome_copy

<a id="method-chrome_copy"></a>

```rust
fn chrome_copy(&self, key: &str) -> ThinkChrome
```

The function `chrome_copy` copies the chrome for `key`.
A missing key returns a closed [`ThinkChrome`](struct.ThinkChrome.md) and does not create a seat.

### write_chrome

<a id="method-write_chrome"></a>

```rust
fn write_chrome(&mut self, key: &str, chrome: ThinkChrome)
```

The function `write_chrome` stores `chrome` on an existing seat.
A missing key returns without a new seat.

### offer

<a id="method-offer"></a>

```rust
fn offer(&mut self, key: &str, cell: [u8; 16], model: &str, text: &str)
```

The function `offer` writes one chunk on the seat for `key` and opens that seat.
The write goes through [`ThinkPipe::offer`](struct.ThinkPipe.md#method-offer). Empty text does not open the seat.
A fold stays folded. The seat stays in the manager.

### finish

<a id="method-finish"></a>

```rust
fn finish(&mut self, key: &str)
```

The function `finish` ends the open block on `key`.
A missing key returns. The seat stays. The chrome stays.

### hide

<a id="method-hide"></a>

```rust
fn hide(&mut self, key: &str)
```

The function `hide` takes the seat off the plate.
A missing key returns. The log stays. The seat stays.

### clear_log

<a id="method-clear_log"></a>

```rust
fn clear_log(&mut self, key: &str)
```

The function `clear_log` wipes the text and leaves the strip open.
A missing key returns. A fold stays folded.

### release

<a id="method-release"></a>

```rust
fn release(&mut self, key: &str)
```

The function `release` drops the seat for `key`.
A missing key returns. This is the session end.

### log_text

<a id="method-log_text"></a>

```rust
fn log_text(&self, key: &str) -> String
```

The function `log_text` renders the log for `key`.
A missing key returns an empty string and does not create a seat.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
