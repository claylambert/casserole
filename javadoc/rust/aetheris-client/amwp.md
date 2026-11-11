# Module amwp

[Rust tree](../index.md) · **aetheris-client**

**Source** `aetheris/rust/aetheris-client/src/amwp.rs`

The module encodes and decodes AMWP frames for the VT100 client.

The layout matches `AmwpCodec`. The magic byte is `0xAE`. The version is 1. The header is 16 bytes. The header fields are little-endian. The CRC32C checksum covers the first 12 header bytes and the payload. Scope T is the terminal channel. A Scope T payload is painted. Channel A is the canvas channel. A channel A init body is kept for the canvas facts. Channel A data is kept as later text. A channel A error is kept for the card. Stream data is kept for the turn. A stream end-of-file is kept for the turn. A stream delta is kept as a thought chunk. Any other channel is read and then dropped.

A failed parse while waiting becomes raw bytes. A failed parse after the mux is up marks the session broken. A complete init frame moves the wait state to the mux state. A complete data frame does the same. A later frame that passes the check stays in the mux state.

| | |
| --- | --- |
| **Specification** | L39 G-07 aetheris-stages-1-3.finalize.md |
| **Specification** | L40 aa-sdd-source-prose.md#RustAmwp |
| **Author** | clayl (2026-10-23) |

## Type Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| struct | [`Frame`](amwp/struct.Frame.md) | The [`Frame`] struct is one decoded frame. |
| enum | [`ScopePiece`](amwp/enum.ScopePiece.md) | The [`ScopePiece`] enum is one body kept off the terminal grid. |
| struct | [`SetLine`](amwp/struct.SetLine.md) | The [`SetLine`] struct is one working-set line. |
| struct | [`ExecuteTune`](amwp/struct.ExecuteTune.md) | The [`ExecuteTune`] struct is the extra lines on an execute. |
| enum | [`Take`](amwp/enum.Take.md) | The `Take` enum is the result of reading one frame from a buffer. |
| enum | [`Mode`](amwp/enum.Mode.md) | The `Mode` enum is the demux state. |
| struct | [`Demux`](amwp/struct.Demux.md) | The [`Demux`] struct peels init-or-raw, then paints channel T. |

## Constant Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| const | [`MAGIC`](#constant-magic) | `MAGIC` is the first header byte. |
| const | [`VERSION`](#constant-version) | `VERSION` is the second header byte. |
| const | [`HEADER_LEN`](#constant-header_len) | `HEADER_LEN` is the header size in bytes. |
| const | [`MAX_PAYLOAD`](#constant-max_payload) | `MAX_PAYLOAD` is the largest payload `encode` will accept. |
| const | [`CHANNEL_T`](#constant-channel_t) | `CHANNEL_T` is the terminal channel. |
| const | [`CHANNEL_A`](#constant-channel_a) | `CHANNEL_A` is the canvas channel. |
| const | [`CHANNEL_STREAM`](#constant-channel_stream) | `CHANNEL_STREAM` is the turn channel. |
| const | [`TYPE_INIT`](#constant-type_init) | `TYPE_INIT` is the init frame type. |
| const | [`TYPE_DATA`](#constant-type_data) | `TYPE_DATA` is the data frame type. |
| const | [`TYPE_EOF`](#constant-type_eof) | `TYPE_EOF` is the end-of-file frame type. |
| const | [`TYPE_ERROR`](#constant-type_error) | `TYPE_ERROR` is the error frame type. |
| const | [`TYPE_CANCEL`](#constant-type_cancel) | `TYPE_CANCEL` is the cancel frame type. |
| const | [`TYPE_DELTA`](#constant-type_delta) | `TYPE_DELTA` is the thought-chunk frame type. |
| const | [`TYPE_KEEPALIVE`](#constant-type_keepalive) | `TYPE_KEEPALIVE` is the keepalive frame type. |
| const | [`FRAG_PREFIX`](#constant-frag_prefix) | `FRAG_PREFIX` is the fragment prefix length. |
| const | [`INIT_WAIT_MS`](#constant-init_wait_ms) | `INIT_WAIT_MS` is how long the session waits for the first mux frame. |
| const | [`KEEPALIVE_MS`](#constant-keepalive_ms) | `KEEPALIVE_MS` is the gap between keepalive frames once the mux is up. |

## Function Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`init_frame`](#function-init_frame) | The `init_frame` function builds the client init frame. |
| fn | [`keepalive_frame`](#function-keepalive_frame) | The `keepalive_frame` function builds a keepalive on channel A. |
| fn | [`scope_t_data`](#function-scope_t_data) | The `scope_t_data` function wraps keystrokes once the mux is up. |
| fn | [`scope_a_body`](#function-scope_a_body) | The `scope_a_body` function returns the body after the 24-byte fragment prefix. |
| fn | [`cell_of`](#function-cell_of) | The `cell_of` function returns the 16-byte cell id inside a fragment prefix. |
| fn | [`cell_text`](#function-cell_text) | The `cell_text` function prints the cell id as UUID text. |
| fn | [`esc_toon`](#function-esc_toon) | The `esc_toon` function escapes one field for the execute body. |
| fn | [`scope_payload`](#function-scope_payload) | The `scope_payload` function builds a fragment prefix and then the body. |
| fn | [`wire_double`](#function-wire_double) | The `wire_double` function prints a finite value the way Java `Double.toString` does. |
| fn | [`execute_set`](#function-execute_set) | The `execute_set` function builds channel A data for an Ollama working set. |
| fn | [`execute_tuned`](#function-execute_tuned) | The `execute_tuned` function builds channel A data for one execute. |
| fn | [`text_frame`](#function-text_frame) | The `text_frame` function builds channel A data whose body is plain text. |
| fn | [`execute_frame`](#function-execute_frame) | The `execute_frame` function builds channel A data for one user turn. |
| fn | [`cancel_frame`](#function-cancel_frame) | The `cancel_frame` function builds a channel A cancel. |
| fn | [`encode`](#function-encode) | The `encode` function builds a header plus a payload. |
| fn | [`take_frame`](#function-take_frame) | The `take_frame` function reads one frame from the front of `buf`. |

## Constant Detail

### MAGIC

<a id="constant-magic"></a>

```rust
pub const MAGIC: u8 = 0xAE;
```

`MAGIC` is the first header byte.
The value is `0xAE`. Any other first byte is not a frame.

### VERSION

<a id="constant-version"></a>

```rust
pub const VERSION: u8 = 1;
```

`VERSION` is the second header byte.
The value is 1. Any other version is not a frame.

### HEADER_LEN

<a id="constant-header_len"></a>

```rust
pub const HEADER_LEN: usize = 16;
```

`HEADER_LEN` is the header size in bytes.
The payload begins at this offset.

### MAX_PAYLOAD

<a id="constant-max_payload"></a>

```rust
pub const MAX_PAYLOAD: usize = 1024 * 1024;
```

`MAX_PAYLOAD` is the largest payload `encode` will accept.
The limit is 1 mebibyte. A longer payload returns `None`.

### CHANNEL_T

<a id="constant-channel_t"></a>

```rust
pub const CHANNEL_T: u16 = 0x0001;
```

`CHANNEL_T` is the terminal channel.
The value is `0x0001`. A non-empty payload on this channel is painted.

### CHANNEL_A

<a id="constant-channel_a"></a>

```rust
pub const CHANNEL_A: u16 = 0x0002;
```

`CHANNEL_A` is the canvas channel.
The value is `0x0002`. Init, data, and error bodies on this channel are kept.

### CHANNEL_STREAM

<a id="constant-channel_stream"></a>

```rust
pub const CHANNEL_STREAM: u16 = 0x0003;
```

`CHANNEL_STREAM` is the turn channel.
The value is `0x0003`. Data, end-of-file, and delta frames on this channel are kept.

### TYPE_INIT

<a id="constant-type_init"></a>

```rust
pub const TYPE_INIT: u8 = 0x00;
```

`TYPE_INIT` is the init frame type.
The value is `0x00`.

### TYPE_DATA

<a id="constant-type_data"></a>

```rust
pub const TYPE_DATA: u8 = 0x01;
```

`TYPE_DATA` is the data frame type.
The value is `0x01`.

### TYPE_EOF

<a id="constant-type_eof"></a>

```rust
pub const TYPE_EOF: u8 = 0x03;
```

`TYPE_EOF` is the end-of-file frame type.
The value is `0x03`.

### TYPE_ERROR

<a id="constant-type_error"></a>

```rust
pub const TYPE_ERROR: u8 = 0x04;
```

`TYPE_ERROR` is the error frame type.
The value is `0x04`.

### TYPE_CANCEL

<a id="constant-type_cancel"></a>

```rust
pub const TYPE_CANCEL: u8 = 0x05;
```

`TYPE_CANCEL` is the cancel frame type.
The value is `0x05`.

### TYPE_DELTA

<a id="constant-type_delta"></a>

```rust
pub const TYPE_DELTA: u8 = 0x06;
```

`TYPE_DELTA` is the thought-chunk frame type.
The value is `0x06`.

### TYPE_KEEPALIVE

<a id="constant-type_keepalive"></a>

```rust
pub const TYPE_KEEPALIVE: u8 = 0x09;
```

`TYPE_KEEPALIVE` is the keepalive frame type.
The value is `0x09`. A mux keepalive is not painted and is not kept.

### FRAG_PREFIX

<a id="constant-frag_prefix"></a>

```rust
pub const FRAG_PREFIX: usize = 24;
```

`FRAG_PREFIX` is the fragment prefix length.
The cell id occupies bytes 8 through 23. The body starts at byte 24.

### INIT_WAIT_MS

<a id="constant-init_wait_ms"></a>

```rust
pub const INIT_WAIT_MS: u64 = 1500;
```

`INIT_WAIT_MS` is how long the session waits for the first mux frame.
The wait is 1500 milliseconds.

### KEEPALIVE_MS

<a id="constant-keepalive_ms"></a>

```rust
pub const KEEPALIVE_MS: u64 = 2000;
```

`KEEPALIVE_MS` is the gap between keepalive frames once the mux is up.
The gap is 2000 milliseconds.

## Function Detail

### init_frame

<a id="function-init_frame"></a>

```rust
pub fn init_frame() -> Vec<u8>
```

The `init_frame` function builds the client init frame.
The channel is channel A. The type is init. The flags are 0. The payload is 24 bytes. Byte 4 of that prefix is 1, which is fragment 0 of 1. The cell id is zeros. The body is empty.

### keepalive_frame

<a id="function-keepalive_frame"></a>

```rust
pub fn keepalive_frame() -> Vec<u8>
```

The `keepalive_frame` function builds a keepalive on channel A.
The payload is empty. There is no fragment prefix.

### scope_t_data

<a id="function-scope_t_data"></a>

```rust
pub fn scope_t_data(bytes: &[u8]) -> Option<Vec<u8>>
```

The `scope_t_data` function wraps keystrokes once the mux is up.
The channel is channel T. The type is data. The flags are 0. A payload over 1 mebibyte returns `None`.

### scope_a_body

<a id="function-scope_a_body"></a>

```rust
pub fn scope_a_body(payload: &[u8]) -> &[u8]
```

The `scope_a_body` function returns the body after the 24-byte fragment prefix.
A shorter payload is returned as the body.

### cell_of

<a id="function-cell_of"></a>

```rust
pub fn cell_of(payload: &[u8]) -> [u8; 16]
```

The `cell_of` function returns the 16-byte cell id inside a fragment prefix.
A payload shorter than 24 bytes returns zeros.

### cell_text

<a id="function-cell_text"></a>

```rust
pub fn cell_text(id: &[u8; 16]) -> String
```

The `cell_text` function prints the cell id as UUID text.
The prefix on the wire carries the same 16 bytes. The text uses lowercase hex and the UUID hyphens.

### esc_toon

<a id="function-esc_toon"></a>

```rust
fn esc_toon(raw: &str) -> String
```

The `esc_toon` function escapes one field for the execute body.
A backslash becomes two backslashes. A newline becomes `\n`. A carriage return is dropped. Every other character is copied.

### scope_payload

<a id="function-scope_payload"></a>

```rust
pub fn scope_payload(cell: &[u8; 16], body: &[u8]) -> Vec<u8>
```

The `scope_payload` function builds a fragment prefix and then the body.
The layout matches `AmwpCodec.scopeAPayload`. Byte 4 is 1, which is fragment 0 of 1. Bytes 8 through 23 are the cell id.

### wire_double

<a id="function-wire_double"></a>

```rust
pub fn wire_double(value: f64) -> String
```

The `wire_double` function prints a finite value the way Java `Double.toString` does.
`1` is written `1.0`. A non-finite value is `NaN`, `Infinity`, or `-Infinity`. A value whose default text has no dot and no exponent gains `.0`.

### execute_set

<a id="function-execute_set"></a>

```rust
pub fn execute_set(cell: &[u8; 16], lines: &[SetLine<'_>]) -> Option<Vec<u8>>
```

The `execute_set` function builds channel A data for an Ollama working set.
The target is the word `ollama`. The ephemeral flag is false. The working set is `lines`.

### execute_tuned

<a id="function-execute_tuned"></a>

```rust
pub fn execute_tuned(cell: &[u8; 16], tune: &ExecuteTune, lines: &[SetLine<'_>]) -> Option<Vec<u8>>
```

The `execute_tuned` function builds channel A data for one execute.
The field order matches `AmwpToon.encodeExecute`. `cell_id` and `execution_target` are always written. `working_set` is always written. A line writes `head: true` only when `head` is true.

`ephemeral`, `burner_cloud`, and `think_stream` are written only when true. `send_reasoning_effort` is written only when false. Empty `reasoning_effort`, `think`, and `pool_boot` are omitted. Temperature uses `wire_double`. A body over 1 mebibyte returns `None`.

### text_frame

<a id="function-text_frame"></a>

```rust
pub fn text_frame(cell: &[u8; 16], text: &str) -> Option<Vec<u8>>
```

The `text_frame` function builds channel A data whose body is plain text.
Tags, show, and the console doorbell use this. A frame over 1 mebibyte returns `None`.

### execute_frame

<a id="function-execute_frame"></a>

```rust
pub fn execute_frame(cell: &[u8; 16], prompt: &str) -> Option<Vec<u8>>
```

The `execute_frame` function builds channel A data for one user turn.
The role is `user`. The text is `prompt`. The head flag is false. The target is the word `ollama`.

### cancel_frame

<a id="function-cancel_frame"></a>

```rust
pub fn cancel_frame(cell: &[u8; 16]) -> Vec<u8>
```

The `cancel_frame` function builds a channel A cancel.
The prefix names the cell. The body is empty.

### encode

<a id="function-encode"></a>

```rust
pub fn encode(channel: u16, typ: u8, flags: u8, payload: &[u8]) -> Option<Vec<u8>>
```

The `encode` function builds a header plus a payload.
`None` is returned when the payload exceeds 1 mebibyte. The checksum is CRC32C over the first 12 header bytes and the payload. The length is stored at bytes 8 through 11.

### take_frame

<a id="function-take_frame"></a>

```rust
fn take_frame(buf: &[u8]) -> Take
```

The `take_frame` function reads one frame from the front of `buf`.
Fewer than 16 bytes returns `NeedMore`. A bad magic or version returns `Bad`. A length over 1 mebibyte returns `Bad`. A buffer shorter than the frame returns `NeedMore`. A CRC32C mismatch returns `Bad`.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
