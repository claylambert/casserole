# struct Demux

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **amwp**

**Source** `rust/aetheris/aetheris-client/src/amwp.rs`

```rust
pub struct Demux
```

The [`Demux`](struct.Demux.md) struct peels init-or-raw, then paints channel T.
`mode` is the state. `buf` holds bytes that are not yet a whole frame. `scope_a` holds pieces kept since the last take.

| | |
| --- | --- |
| **Specification** | L39 aetheris-stages-1-3.finalize.md |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| Mode | [`mode`](#member-mode) |  |
| Vec<u8> | [`buf`](#member-buf) |  |
| Vec<ScopePiece> | [`scope_a`](#member-scope_a) |  |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`new`](#method-new) | The `new` method returns a [`Demux`] in the wait state. |
| fn | [`is_wait`](#method-is_wait) | The `is_wait` method reports the wait state. |
| fn | [`is_mux`](#method-is_mux) | The `is_mux` method reports the mux state. |
| fn | [`is_raw`](#method-is_raw) | The `is_raw` method reports the raw state. |
| fn | [`is_broken`](#method-is_broken) | The `is_broken` method reports the broken state. |
| fn | [`take_scope_a`](#method-take_scope_a) | The `take_scope_a` method returns the channel pieces queued since the last take. |
| fn | [`keep_scope_a`](#method-keep_scope_a) | The `keep_scope_a` method stores one frame when it is a piece the canvas keeps. |
| fn | [`push`](#method-push) | The `push` method appends `bytes` and returns the bytes to paint now. |
| fn | [`timed_out`](#method-timed_out) | The `timed_out` method ends the init wait when no complete mux frame arrived. |

## Field Detail

### mode

<a id="member-mode"></a>

```rust
mode: Mode
```

### buf

<a id="member-buf"></a>

```rust
buf: Vec<u8>
```

### scope_a

<a id="member-scope_a"></a>

```rust
scope_a: Vec<ScopePiece>
```

## Method Detail

### new

<a id="method-new"></a>

```rust
pub fn new() -> Self
```

The `new` method returns a [`Demux`](struct.Demux.md) in the wait state.
The buffer is empty. No piece is kept.

### is_wait

<a id="method-is_wait"></a>

```rust
pub fn is_wait(&self) -> bool
```

The `is_wait` method reports the wait state.
True means no path has been chosen yet.

### is_mux

<a id="method-is_mux"></a>

```rust
pub fn is_mux(&self) -> bool
```

The `is_mux` method reports the mux state.
True means a first init or data frame was accepted.

### is_raw

<a id="method-is_raw"></a>

```rust
pub fn is_raw(&self) -> bool
```

The `is_raw` method reports the raw state.
Tests read this state. The read thread sends unframed bytes whenever the demux is not mux, which includes raw.

### is_broken

<a id="method-is_broken"></a>

```rust
pub fn is_broken(&self) -> bool
```

The `is_broken` method reports the broken state.
True means a frame failed after the mux was up.

### take_scope_a

<a id="method-take_scope_a"></a>

```rust
pub fn take_scope_a(&mut self) -> Vec<ScopePiece>
```

The `take_scope_a` method returns the channel pieces queued since the last take.
The queue is empty afterward. An empty vector means none arrived.

### keep_scope_a

<a id="method-keep_scope_a"></a>

```rust
fn keep_scope_a(&mut self, frame: &Frame)
```

The `keep_scope_a` method stores one frame when it is a piece the canvas keeps.
A stream end-of-file stores `End`. Stream data with a non-empty body stores `Reply`. A stream delta with a non-empty body stores `Delta`. The first byte of that delta body is stored as the kind. The rest of that body is stored as the piece body. An empty stream data body is skipped. An empty stream delta body is skipped.

A frame that is not channel A returns after the stream check. A channel A type other than init, data, or error returns. An empty channel A body returns. Init stores `Init`. Error stores `Fault`. Data stores `Later`.

### push

<a id="method-push"></a>

```rust
pub fn push(&mut self, bytes: &[u8]) -> Vec<u8>
```

The `push` method appends `bytes` and returns the bytes to paint now.
A broken demux returns an empty vector. A raw demux returns a copy of `bytes`. An incomplete frame stays in the buffer and adds nothing to the paint.

In the wait state, a first byte other than the magic paints the buffer and selects raw. A bad frame does the same. A frame whose type is neither init nor data does the same. An accepted frame selects mux. Only a non-empty channel T payload from that frame is painted.

In the mux state, a bad frame clears the buffer and selects broken. A keepalive is skipped. Any other frame may store a piece. A non-empty channel T payload is painted.

### timed_out

<a id="method-timed_out"></a>

```rust
pub fn timed_out(&mut self) -> Vec<u8>
```

The `timed_out` method ends the init wait when no complete mux frame arrived.
The state becomes raw. The returned bytes are the peeked buffer. A call that is not in the wait state returns an empty vector and leaves the state.

## impl Default for Demux

```rust
impl Default for Demux
```

### default

<a id="method-default"></a>

```rust
fn default() -> Self
```

The `default` method returns the same value as `new`.
The state is wait.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
