// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The module encodes and decodes AMWP frames for the VT100 client.
//!
//! The layout matches `AmwpCodec`. The magic byte is `0xAE`. The version is 1. The header is 16 bytes. The header fields are little-endian. The CRC32C checksum covers the first 12 header bytes and the payload. Scope T is the terminal channel. A Scope T payload is painted. Channel A is the canvas channel. A channel A init body is kept for the canvas facts. Channel A data is kept as later text. A channel A error is kept for the card. Stream data is kept for the turn. A stream end-of-file is kept for the turn. A stream delta is kept as a thought chunk. Any other channel is read and then dropped.
//!
//! A failed parse while waiting becomes raw bytes. A failed parse after the mux is up marks the session broken. A complete init frame moves the wait state to the mux state. A complete data frame does the same. A later frame that passes the check stays in the mux state.
//!
//! @aa-sdd PK-0479 PK-0482 PK-0471 PK-0480 docs/specs/java/org/aetheris/wire/wire.spec.md
//! @aa-sdd PK-4440 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
//! @aa-sdd PK-3491 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
//! @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
//! @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
//! @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
//! @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
//! @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
//! @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
//! @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
//! @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
//! @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
//! @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
//! @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
//! @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
//! @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
//! @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
//! @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
//! @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
//! @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
//! @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
//! @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
//! @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
//! @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
//! @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
//! @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
//! @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
//! @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
//! @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
//! @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
//! @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
//! @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
//! @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
//! @author clayl (2026-10-23)

/// `MAGIC` is the first header byte.
/// The value is `0xAE`. Any other first byte is not a frame.
pub const MAGIC: u8 = 0xAE;
/// `VERSION` is the second header byte.
/// The value is 1. Any other version is not a frame.
pub const VERSION: u8 = 1;
/// `HEADER_LEN` is the header size in bytes.
/// The payload begins at this offset.
pub const HEADER_LEN: usize = 16;
/// `MAX_PAYLOAD` is the largest payload `encode` will accept.
/// The limit is 1 mebibyte. A longer payload returns `None`.
pub const MAX_PAYLOAD: usize = 1024 * 1024;
/// `CHANNEL_T` is the terminal channel.
/// The value is `0x0001`. A non-empty payload on this channel is painted.
pub const CHANNEL_T: u16 = 0x0001;
/// `CHANNEL_A` is the canvas channel.
/// The value is `0x0002`. Init, data, and error bodies on this channel are kept.
pub const CHANNEL_A: u16 = 0x0002;
/// `CHANNEL_STREAM` is the turn channel.
/// The value is `0x0003`. Data, end-of-file, and delta frames on this channel are kept.
pub const CHANNEL_STREAM: u16 = 0x0003;
/// `TYPE_INIT` is the init frame type.
/// The value is `0x00`.
pub const TYPE_INIT: u8 = 0x00;
/// `TYPE_DATA` is the data frame type.
/// The value is `0x01`.
pub const TYPE_DATA: u8 = 0x01;
/// `TYPE_EOF` is the end-of-file frame type.
/// The value is `0x03`.
pub const TYPE_EOF: u8 = 0x03;
/// `TYPE_ERROR` is the error frame type.
/// The value is `0x04`.
pub const TYPE_ERROR: u8 = 0x04;
/// `TYPE_CANCEL` is the cancel frame type.
/// The value is `0x05`.
pub const TYPE_CANCEL: u8 = 0x05;
/// `TYPE_DELTA` is the thought-chunk frame type.
/// The value is `0x06`.
pub const TYPE_DELTA: u8 = 0x06;
/// `TYPE_KEEPALIVE` is the keepalive frame type.
/// The value is `0x09`. A mux keepalive is not painted and is not kept.
pub const TYPE_KEEPALIVE: u8 = 0x09;
/// `FRAG_PREFIX` is the fragment prefix length.
/// The cell id occupies bytes 8 through 23. The body starts at byte 24.
pub const FRAG_PREFIX: usize = 24;
/// `INIT_WAIT_MS` is how long the session waits for the first mux frame.
/// The wait is 1500 milliseconds.
pub const INIT_WAIT_MS: u64 = 1500;
/// `KEEPALIVE_MS` is the gap between keepalive frames once the mux is up.
/// The gap is 2000 milliseconds.
pub const KEEPALIVE_MS: u64 = 2000;

/// @aa-sdd PK-0479 PK-0482 docs/specs/java/org/aetheris/wire/wire.spec.md
/// @aa-sdd PK-4440 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
/// The [`Frame`] struct is one decoded frame.
/// The payload is owned. `channel`, `typ`, and `flags` are the header fields. Bytes 6 and 7 of the header are not stored.
#[derive(Clone, Debug, PartialEq, Eq)]
pub struct Frame {
    pub channel: u16,
    pub typ: u8,
    pub flags: u8,
    pub payload: Vec<u8>,
}

/// The `init_frame` function builds the client init frame.
/// The channel is channel A. The type is init. The flags are 0. The payload is 24 bytes. Byte 4 of that prefix is 1, which is fragment 0 of 1. The cell id is zeros. The body is empty.
pub fn init_frame() -> Vec<u8> {
    let mut payload = vec![0u8; FRAG_PREFIX];
    payload[4] = 1;
    encode(CHANNEL_A, TYPE_INIT, 0, &payload).expect("init fits")
}

/// The `keepalive_frame` function builds a keepalive on channel A.
/// The payload is empty. There is no fragment prefix.
pub fn keepalive_frame() -> Vec<u8> {
    encode(CHANNEL_A, TYPE_KEEPALIVE, 0, &[]).expect("keepalive fits")
}

/// The `scope_t_data` function wraps keystrokes once the mux is up.
/// The channel is channel T. The type is data. The flags are 0. A payload over 1 mebibyte returns `None`.
pub fn scope_t_data(bytes: &[u8]) -> Option<Vec<u8>> {
    encode(CHANNEL_T, TYPE_DATA, 0, bytes)
}

/// The `scope_a_body` function returns the body after the 24-byte fragment prefix.
/// A shorter payload is returned as the body.
pub fn scope_a_body(payload: &[u8]) -> &[u8] {
    if payload.len() >= FRAG_PREFIX {
        &payload[FRAG_PREFIX..]
    } else {
        payload
    }
}

/// @aa-sdd PK-0479 PK-0482 docs/specs/java/org/aetheris/wire/wire.spec.md
/// @aa-sdd PK-4440 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
/// The [`ScopePiece`] enum is one body kept off the terminal grid.
/// `Init` is the facts body. `Later` is channel A data. `Fault` is a channel A error. `Reply` and `End` are the stream turn. `Delta` is a stream thought chunk. The first byte of a delta body is the kind.
#[derive(Clone, Debug, PartialEq, Eq)]
pub enum ScopePiece {
    Init(Vec<u8>),
    /// The `Later` variant is channel A data.
    /// The cell is the fragment id. A short payload uses a zero id.
    Later { cell: [u8; 16], body: Vec<u8> },
    Fault { cell: [u8; 16], body: Vec<u8> },
    Reply { cell: [u8; 16], body: Vec<u8> },
    End { cell: [u8; 16] },
    Delta { cell: [u8; 16], kind: u8, body: Vec<u8> },
}

/// The `cell_of` function returns the 16-byte cell id inside a fragment prefix.
/// A payload shorter than 24 bytes returns zeros.
pub fn cell_of(payload: &[u8]) -> [u8; 16] {
    let mut id = [0u8; 16];
    if payload.len() >= FRAG_PREFIX {
        id.copy_from_slice(&payload[8..24]);
    }
    id
}

/// The `cell_text` function prints the cell id as UUID text.
/// The prefix on the wire carries the same 16 bytes. The text uses lowercase hex and the UUID hyphens.
pub fn cell_text(id: &[u8; 16]) -> String {
    format!(
        "{:02x}{:02x}{:02x}{:02x}-{:02x}{:02x}-{:02x}{:02x}-{:02x}{:02x}-{:02x}{:02x}{:02x}{:02x}{:02x}{:02x}",
        id[0], id[1], id[2], id[3], id[4], id[5], id[6], id[7], id[8], id[9], id[10], id[11],
        id[12], id[13], id[14], id[15]
    )
}

/// The `esc_toon` function escapes one field for the execute body.
/// A backslash becomes two backslashes. A newline becomes `\n`. A carriage return is dropped. Every other character is copied.
fn esc_toon(raw: &str) -> String {
    let mut out = String::new();
    for c in raw.chars() {
        match c {
            '\\' => out.push_str("\\\\"),
            '\n' => out.push_str("\\n"),
            '\r' => {}
            _ => out.push(c),
        }
    }
    out
}

/// The `scope_payload` function builds a fragment prefix and then the body.
/// The layout matches `AmwpCodec.scopeAPayload`. Byte 4 is 1, which is fragment 0 of 1. Bytes 8 through 23 are the cell id.
pub fn scope_payload(cell: &[u8; 16], body: &[u8]) -> Vec<u8> {
    let mut out = vec![0u8; FRAG_PREFIX + body.len()];
    out[4] = 1;
    out[8..24].copy_from_slice(cell);
    if !body.is_empty() {
        out[FRAG_PREFIX..].copy_from_slice(body);
    }
    out
}

/// @aa-sdd PK-0817 PK-0864 PK-0911 PK-0958 PK-1005 PK-1052 PK-1099 PK-1146 PK-1193 PK-1240 PK-1287 PK-1334 PK-1381 PK-1428 PK-1475 PK-1522 PK-1570 PK-1617 PK-1664 PK-1711 PK-1758 PK-1805 PK-1852 PK-1899 PK-1946 PK-1993 PK-2041 PK-2088 PK-2135 PK-2182 PK-2230 PK-2278 PK-2326 PK-2373 PK-2420 PK-2468 PK-2515 PK-2562 PK-2609 PK-2657 PK-2704 PK-2752 PK-2799 PK-2846 PK-2893 PK-2940 PK-2987 PK-3034 PK-3081 PK-3128 PK-3176 PK-3223 PK-3270 PK-3318 PK-3365 PK-3412 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
/// The [`SetLine`] struct is one working-set line.
/// `role` is `user` or `assistant`. `text` is the line. `head` is the canvas designation. A false `head` is absent on the wire.
pub struct SetLine<'a> {
    pub role: &'a str,
    pub text: &'a str,
    pub head: bool,
}

/// @aa-sdd PK-0479 PK-0482 docs/specs/java/org/aetheris/wire/wire.spec.md
/// @aa-sdd PK-4440 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
/// The [`ExecuteTune`] struct is the extra lines on an execute.
/// An empty string stays off the wire, except `target`, which is always written. `send_reasoning_effort` writes a line only when it is false. Mood shaping leaves `think` empty and `think_stream` false.
#[derive(Clone, Debug, PartialEq)]
pub struct ExecuteTune {
    pub target: String,
    pub ephemeral: bool,
    pub burner_cloud: bool,
    pub think_stream: bool,
    pub send_reasoning_effort: bool,
    pub reasoning_effort: String,
    pub think: String,
    pub temperature: Option<f64>,
    pub max_completion_tokens: Option<i32>,
    pub pool_boot: String,
}

impl ExecuteTune {
    /// The `ollama` method returns a tune aimed at Ollama with no extras.
    /// The target is the word `ollama`. `ephemeral` is stored as given. `send_reasoning_effort` starts true. An empty stored word is already the word `ollama` before this value is built.
    pub fn ollama(ephemeral: bool) -> Self {
        Self {
            target: "ollama".to_string(),
            ephemeral,
            burner_cloud: false,
            think_stream: false,
            send_reasoning_effort: true,
            reasoning_effort: String::new(),
            think: String::new(),
            temperature: None,
            max_completion_tokens: None,
            pool_boot: String::new(),
        }
    }
}

/// The `wire_double` function prints a finite value the way Java `Double.toString` does.
/// `1` is written `1.0`. A non-finite value is `NaN`, `Infinity`, or `-Infinity`. A value whose default text has no dot and no exponent gains `.0`.
pub fn wire_double(value: f64) -> String {
    if value.is_nan() {
        return "NaN".to_string();
    }
    if value.is_infinite() {
        return if value.is_sign_negative() {
            "-Infinity".to_string()
        } else {
            "Infinity".to_string()
        };
    }
    let text = format!("{value}");
    if text.contains('.') || text.contains('e') || text.contains('E') {
        text
    } else {
        format!("{text}.0")
    }
}

/// The `execute_set` function builds channel A data for an Ollama working set.
/// The target is the word `ollama`. The ephemeral flag is false. The working set is `lines`.
pub fn execute_set(cell: &[u8; 16], lines: &[SetLine<'_>]) -> Option<Vec<u8>> {
    execute_tuned(cell, &ExecuteTune::ollama(false), lines)
}

/// The `execute_tuned` function builds channel A data for one execute.
/// The field order matches `AmwpToon.encodeExecute`. `cell_id` and `execution_target` are always written. `working_set` is always written. A line writes `head: true` only when `head` is true.
///
/// `ephemeral`, `burner_cloud`, and `think_stream` are written only when true. `send_reasoning_effort` is written only when false. Empty `reasoning_effort`, `think`, and `pool_boot` are omitted. Temperature uses `wire_double`. A body over 1 mebibyte returns `None`.
pub fn execute_tuned(cell: &[u8; 16], tune: &ExecuteTune, lines: &[SetLine<'_>]) -> Option<Vec<u8>> {
    let mut toon = String::new();
    toon.push_str("cell_id: ");
    toon.push_str(&cell_text(cell));
    toon.push('\n');
    toon.push_str("execution_target: ");
    toon.push_str(&esc_toon(&tune.target));
    toon.push('\n');
    if tune.ephemeral {
        toon.push_str("ephemeral: true\n");
    }
    if tune.burner_cloud {
        toon.push_str("burner_cloud: true\n");
    }
    if tune.think_stream {
        toon.push_str("think_stream: true\n");
    }
    if !tune.send_reasoning_effort {
        toon.push_str("send_reasoning_effort: false\n");
    }
    if !tune.reasoning_effort.is_empty() {
        toon.push_str("reasoning_effort: ");
        toon.push_str(&esc_toon(&tune.reasoning_effort));
        toon.push('\n');
    }
    if !tune.think.is_empty() {
        toon.push_str("think: ");
        toon.push_str(&esc_toon(&tune.think));
        toon.push('\n');
    }
    if let Some(temperature) = tune.temperature {
        toon.push_str("temperature: ");
        toon.push_str(&wire_double(temperature));
        toon.push('\n');
    }
    if let Some(max) = tune.max_completion_tokens {
        toon.push_str("max_completion_tokens: ");
        toon.push_str(&max.to_string());
        toon.push('\n');
    }
    if !tune.pool_boot.is_empty() {
        toon.push_str("pool_boot: ");
        toon.push_str(&esc_toon(&tune.pool_boot));
        toon.push('\n');
    }
    toon.push_str("working_set:\n");
    for line in lines {
        toon.push_str("- role: ");
        toon.push_str(&esc_toon(line.role));
        toon.push('\n');
        toon.push_str("  text: ");
        toon.push_str(&esc_toon(line.text));
        toon.push('\n');
        if line.head {
            toon.push_str("  head: true\n");
        }
    }
    let body = toon.into_bytes();
    let payload = scope_payload(cell, &body);
    encode(CHANNEL_A, TYPE_DATA, 0, &payload)
}

/// The `text_frame` function builds channel A data whose body is plain text.
/// Tags, show, and the console doorbell use this. A frame over 1 mebibyte returns `None`.
pub fn text_frame(cell: &[u8; 16], text: &str) -> Option<Vec<u8>> {
    let payload = scope_payload(cell, text.as_bytes());
    encode(CHANNEL_A, TYPE_DATA, 0, &payload)
}

/// The `execute_frame` function builds channel A data for one user turn.
/// The role is `user`. The text is `prompt`. The head flag is false. The target is the word `ollama`.
pub fn execute_frame(cell: &[u8; 16], prompt: &str) -> Option<Vec<u8>> {
    execute_set(
        cell,
        &[SetLine {
            role: "user",
            text: prompt,
            head: false,
        }],
    )
}

/// The `cancel_frame` function builds a channel A cancel.
/// The prefix names the cell. The body is empty.
pub fn cancel_frame(cell: &[u8; 16]) -> Vec<u8> {
    let payload = scope_payload(cell, &[]);
    encode(CHANNEL_A, TYPE_CANCEL, 0, &payload).expect("cancel fits")
}

/// The `encode` function builds a header plus a payload.
/// `None` is returned when the payload exceeds 1 mebibyte. The checksum is CRC32C over the first 12 header bytes and the payload. The length is stored at bytes 8 through 11.
pub fn encode(channel: u16, typ: u8, flags: u8, payload: &[u8]) -> Option<Vec<u8>> {
    if payload.len() > MAX_PAYLOAD {
        return None;
    }
    let mut out = vec![0u8; HEADER_LEN + payload.len()];
    out[0] = MAGIC;
    out[1] = VERSION;
    out[2..4].copy_from_slice(&channel.to_le_bytes());
    out[4] = typ;
    out[5] = flags;
    out[8..12].copy_from_slice(&(payload.len() as u32).to_le_bytes());
    let mut crc = crc32c::crc32c_append(0, &out[..12]);
    if !payload.is_empty() {
        crc = crc32c::crc32c_append(crc, payload);
    }
    out[12..16].copy_from_slice(&crc.to_le_bytes());
    if !payload.is_empty() {
        out[HEADER_LEN..].copy_from_slice(payload);
    }
    Some(out)
}

/// @aa-sdd PK-0479 PK-0482 docs/specs/java/org/aetheris/wire/wire.spec.md
/// @aa-sdd PK-4440 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
/// The `Take` enum is the result of reading one frame from a buffer.
/// `NeedMore` means the buffer is short. `Bad` means the bytes are not a frame. `Frame` is one checked frame and the number of bytes it used.
enum Take {
    NeedMore,
    Bad,
    Frame { len: usize, frame: Frame },
}

/// The `take_frame` function reads one frame from the front of `buf`.
/// Fewer than 16 bytes returns `NeedMore`. A bad magic or version returns `Bad`. A length over 1 mebibyte returns `Bad`. A buffer shorter than the frame returns `NeedMore`. A CRC32C mismatch returns `Bad`.
fn take_frame(buf: &[u8]) -> Take {
    if buf.len() < HEADER_LEN {
        return Take::NeedMore;
    }
    if buf[0] != MAGIC || buf[1] != VERSION {
        return Take::Bad;
    }
    let channel = u16::from_le_bytes([buf[2], buf[3]]);
    let typ = buf[4];
    let flags = buf[5];
    let length = u32::from_le_bytes([buf[8], buf[9], buf[10], buf[11]]) as usize;
    if length > MAX_PAYLOAD {
        return Take::Bad;
    }
    let total = HEADER_LEN + length;
    if buf.len() < total {
        return Take::NeedMore;
    }
    let payload = buf[HEADER_LEN..total].to_vec();
    let mut crc = crc32c::crc32c_append(0, &buf[..12]);
    if !payload.is_empty() {
        crc = crc32c::crc32c_append(crc, &payload);
    }
    let expect = u32::from_le_bytes([buf[12], buf[13], buf[14], buf[15]]);
    if crc != expect {
        return Take::Bad;
    }
    Take::Frame {
        len: total,
        frame: Frame {
            channel,
            typ,
            flags,
            payload,
        },
    }
}

/// @aa-sdd PK-0479 PK-0482 docs/specs/java/org/aetheris/wire/wire.spec.md
/// @aa-sdd PK-4440 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
/// The `Mode` enum is the demux state.
/// `Wait` is before the first accepted frame. `Mux` is after that frame. `Raw` means the bytes are painted as themselves. `Broken` means a later frame failed its check.
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
enum Mode {
    Wait,
    Mux,
    Raw,
    Broken,
}

/// @aa-sdd PK-0479 PK-0482 docs/specs/java/org/aetheris/wire/wire.spec.md
/// @aa-sdd PK-4440 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
/// The [`Demux`] struct peels init-or-raw, then paints channel T.
/// `mode` is the state. `buf` holds bytes that are not yet a whole frame. `scope_a` holds pieces kept since the last take.
pub struct Demux {
    mode: Mode,
    buf: Vec<u8>,
    scope_a: Vec<ScopePiece>,
}

impl Demux {
    /// The `new` method returns a [`Demux`] in the wait state.
    /// The buffer is empty. No piece is kept.
    pub fn new() -> Self {
        Self {
            mode: Mode::Wait,
            buf: Vec::new(),
            scope_a: Vec::new(),
        }
    }

    /// The `is_wait` method reports the wait state.
    /// True means no path has been chosen yet.
    pub fn is_wait(&self) -> bool {
        self.mode == Mode::Wait
    }

    /// The `is_mux` method reports the mux state.
    /// True means a first init or data frame was accepted.
    pub fn is_mux(&self) -> bool {
        self.mode == Mode::Mux
    }

    /// The `is_raw` method reports the raw state.
    /// Tests read this state. The read thread sends unframed bytes whenever the demux is not mux, which includes raw.
    #[cfg_attr(not(test), allow(dead_code))]
    pub fn is_raw(&self) -> bool {
        self.mode == Mode::Raw
    }

    /// The `is_broken` method reports the broken state.
    /// True means a frame failed after the mux was up.
    pub fn is_broken(&self) -> bool {
        self.mode == Mode::Broken
    }

    /// The `take_scope_a` method returns the channel pieces queued since the last take.
    /// The queue is empty afterward. An empty vector means none arrived.
    pub fn take_scope_a(&mut self) -> Vec<ScopePiece> {
        std::mem::take(&mut self.scope_a)
    }

    /// The `keep_scope_a` method stores one frame when it is a piece the canvas keeps.
    /// A stream end-of-file stores `End`. Stream data with a non-empty body stores `Reply`. A stream delta with a non-empty body stores `Delta`. The first byte of that delta body is stored as the kind. The rest of that body is stored as the piece body. An empty stream data body is skipped. An empty stream delta body is skipped.
    ///
    /// A frame that is not channel A returns after the stream check. A channel A type other than init, data, or error returns. An empty channel A body returns. Init stores `Init`. Error stores `Fault`. Data stores `Later`.
    fn keep_scope_a(&mut self, frame: &Frame) {
        if frame.channel == CHANNEL_STREAM {
            let cell = cell_of(&frame.payload);
            if frame.typ == TYPE_EOF {
                self.scope_a.push(ScopePiece::End { cell });
            } else if frame.typ == TYPE_DATA {
                let body = scope_a_body(&frame.payload);
                if !body.is_empty() {
                    self.scope_a.push(ScopePiece::Reply {
                        cell,
                        body: body.to_vec(),
                    });
                }
            } else if frame.typ == TYPE_DELTA {
                let raw = scope_a_body(&frame.payload);
                if !raw.is_empty() {
                    self.scope_a.push(ScopePiece::Delta {
                        cell,
                        kind: raw[0],
                        body: raw[1..].to_vec(),
                    });
                }
            }
            return;
        }
        if frame.channel != CHANNEL_A {
            return;
        }
        if frame.typ != TYPE_INIT && frame.typ != TYPE_DATA && frame.typ != TYPE_ERROR {
            return;
        }
        let body = scope_a_body(&frame.payload);
        if body.is_empty() {
            return;
        }
        let cell = cell_of(&frame.payload);
        let piece = if frame.typ == TYPE_INIT {
            ScopePiece::Init(body.to_vec())
        } else if frame.typ == TYPE_ERROR {
            ScopePiece::Fault {
                cell,
                body: body.to_vec(),
            }
        } else {
            ScopePiece::Later {
                cell,
                body: body.to_vec(),
            }
        };
        self.scope_a.push(piece);
    }

    /// The `push` method appends `bytes` and returns the bytes to paint now.
    /// A broken demux returns an empty vector. A raw demux returns a copy of `bytes`. An incomplete frame stays in the buffer and adds nothing to the paint.
    ///
    /// In the wait state, a first byte other than the magic paints the buffer and selects raw. A bad frame does the same. A frame whose type is neither init nor data does the same. An accepted frame selects mux. Only a non-empty channel T payload from that frame is painted.
    ///
    /// In the mux state, a bad frame clears the buffer and selects broken. A keepalive is skipped. Any other frame may store a piece. A non-empty channel T payload is painted.
    pub fn push(&mut self, bytes: &[u8]) -> Vec<u8> {
        if self.mode == Mode::Broken {
            return Vec::new();
        }
        if self.mode == Mode::Raw {
            return bytes.to_vec();
        }
        self.buf.extend_from_slice(bytes);
        let mut paint = Vec::new();
        loop {
            match self.mode {
                Mode::Wait => {
                    if self.buf.is_empty() {
                        break;
                    }
                    if self.buf[0] != MAGIC {
                        paint.extend_from_slice(&self.buf);
                        self.buf.clear();
                        self.mode = Mode::Raw;
                        break;
                    }
                    match take_frame(&self.buf) {
                        Take::NeedMore => break,
                        Take::Bad => {
                            paint.extend_from_slice(&self.buf);
                            self.buf.clear();
                            self.mode = Mode::Raw;
                            break;
                        }
                        Take::Frame { len, frame } => {
                            if frame.typ != TYPE_INIT && frame.typ != TYPE_DATA {
                                paint.extend_from_slice(&self.buf);
                                self.buf.clear();
                                self.mode = Mode::Raw;
                                break;
                            }
                            self.buf.drain(..len);
                            self.mode = Mode::Mux;
                            self.keep_scope_a(&frame);
                            if frame.channel == CHANNEL_T && !frame.payload.is_empty() {
                                paint.extend_from_slice(&frame.payload);
                            }
                        }
                    }
                }
                Mode::Mux => {
                    if self.buf.is_empty() {
                        break;
                    }
                    match take_frame(&self.buf) {
                        Take::NeedMore => break,
                        Take::Bad => {
                            self.buf.clear();
                            self.mode = Mode::Broken;
                            break;
                        }
                        Take::Frame { len, frame } => {
                            self.buf.drain(..len);
                            if frame.typ == TYPE_KEEPALIVE {
                                continue;
                            }
                            self.keep_scope_a(&frame);
                            if frame.channel == CHANNEL_T && !frame.payload.is_empty() {
                                paint.extend_from_slice(&frame.payload);
                            }
                        }
                    }
                }
                Mode::Raw | Mode::Broken => break,
            }
        }
        paint
    }

    /// The `timed_out` method ends the init wait when no complete mux frame arrived.
    /// The state becomes raw. The returned bytes are the peeked buffer. A call that is not in the wait state returns an empty vector and leaves the state.
    pub fn timed_out(&mut self) -> Vec<u8> {
        if self.mode != Mode::Wait {
            return Vec::new();
        }
        self.mode = Mode::Raw;
        std::mem::take(&mut self.buf)
    }
}

impl Default for Demux {
    /// The `default` method returns the same value as `new`.
    /// The state is wait.
    fn default() -> Self {
        Self::new()
    }
}

#[cfg(test)]
/// These functions check frame encode, frame decode, and the demux.
mod tests {
    use super::{
        cancel_frame, encode, execute_frame, execute_set, init_frame, keepalive_frame, scope_t_data,
        Demux, SetLine,
        ScopePiece, CHANNEL_A, CHANNEL_STREAM, CHANNEL_T, HEADER_LEN, MAX_PAYLOAD, TYPE_CANCEL,
        TYPE_DATA, TYPE_DELTA, TYPE_EOF, TYPE_INIT, TYPE_KEEPALIVE,
    };

    /// The `decode_round_trip` function feeds `bytes` to a new [`Demux`] and returns the frame `take_frame` reads.
    /// The paint from that push is discarded. A buffer that is not one frame panics with `frame did not decode`.
    fn decode_round_trip(bytes: &[u8]) -> super::Frame {
        let mut demux = Demux::new();
        let _ = demux.push(bytes);
        match super::take_frame(bytes) {
            super::Take::Frame { frame, .. } => frame,
            _ => panic!("frame did not decode"),
        }
    }

    /// The `init_and_keepalive_round_trip_without_a_socket` test checks the init frame and the keepalive frame.
    /// The init frame uses channel A, type init, flags 0, and a 24-byte payload. The first eight payload bytes are four zeros, a 1, and three zeros. The rest of that payload is zeros. The keepalive frame is one header. Its magic is `0xAE`. Its version is 1. Its channel is channel A. Its type is keepalive. Its payload is empty.
    #[test]
    fn init_and_keepalive_round_trip_without_a_socket() {
        let init = init_frame();
        let frame = decode_round_trip(&init);
        assert_eq!(frame.channel, CHANNEL_A);
        assert_eq!(frame.typ, TYPE_INIT);
        assert_eq!(frame.flags, 0);
        assert_eq!(frame.payload.len(), 24);
        assert_eq!(&frame.payload[..8], &[0, 0, 0, 0, 1, 0, 0, 0]);
        assert!(frame.payload[8..].iter().all(|b| *b == 0));

        let ka = keepalive_frame();
        assert_eq!(ka.len(), HEADER_LEN);
        assert_eq!(ka[0], 0xAE);
        assert_eq!(ka[1], 1);
        assert_eq!(&ka[2..4], &CHANNEL_A.to_le_bytes());
        assert_eq!(ka[4], TYPE_KEEPALIVE);
        let frame = decode_round_trip(&ka);
        assert!(frame.payload.is_empty());
    }

    /// The `channel_t_is_painted_and_channel_a_stays_off_the_grid` test checks paint and kept pieces.
    /// An init frame paints nothing and keeps no piece. The demux is then mux. Channel T data `hi` is painted. A short channel A data body `no` is kept as `Later` with a zero cell. A keepalive paints nothing and keeps nothing. The demux is not broken.
    #[test]
    fn channel_t_is_painted_and_channel_a_stays_off_the_grid() {
        let mut demux = Demux::new();
        let paint = demux.push(&init_frame());
        assert!(paint.is_empty());
        assert!(demux.take_scope_a().is_empty());
        assert!(demux.is_mux());
        let data = scope_t_data(b"hi").unwrap();
        assert_eq!(demux.push(&data), b"hi");
        assert!(demux.take_scope_a().is_empty());
        let other = encode(CHANNEL_A, TYPE_DATA, 0, b"no").unwrap();
        assert!(demux.push(&other).is_empty());
        assert_eq!(
            demux.take_scope_a(),
            vec![ScopePiece::Later {
                cell: [0u8; 16],
                body: b"no".to_vec(),
            }]
        );
        let ka = keepalive_frame();
        assert!(demux.push(&ka).is_empty());
        assert!(demux.take_scope_a().is_empty());
        assert!(!demux.is_broken());
    }

    /// The `the_scope_a_body_is_the_pipe` test checks init, error, and stream data.
    /// An init body `air-gap: on\n` is kept as `Init`. An error body `reason: down\n` is kept as `Fault` with a zero cell. Stream data `token` is kept as `Reply` with a zero cell. Channel T data `ok` is painted.
    #[test]
    fn the_scope_a_body_is_the_pipe() {
        let mut payload = vec![0u8; super::FRAG_PREFIX];
        payload[4] = 1;
        payload.extend_from_slice(b"air-gap: on\n");
        let init = encode(CHANNEL_A, TYPE_INIT, 0, &payload).unwrap();
        let mut demux = Demux::new();
        assert!(demux.push(&init).is_empty());
        assert_eq!(
            demux.take_scope_a(),
            vec![ScopePiece::Init(b"air-gap: on\n".to_vec())]
        );

        let mut err = vec![0u8; super::FRAG_PREFIX];
        err[4] = 1;
        err.extend_from_slice(b"reason: down\n");
        let error = encode(CHANNEL_A, super::TYPE_ERROR, 0, &err).unwrap();
        assert!(demux.push(&error).is_empty());
        assert_eq!(
            demux.take_scope_a(),
            vec![ScopePiece::Fault {
                cell: [0u8; 16],
                body: b"reason: down\n".to_vec(),
            }]
        );

        let stream = encode(0x0003, TYPE_DATA, 0, b"token").unwrap();
        assert!(demux.push(&stream).is_empty());
        assert_eq!(
            demux.take_scope_a(),
            vec![ScopePiece::Reply {
                cell: [0u8; 16],
                body: b"token".to_vec(),
            }]
        );
        assert_eq!(demux.push(&scope_t_data(b"ok").unwrap()), b"ok");
    }

    /// The `the_execute_frame_is_channel_a_and_the_stream_reply_stays_off_the_grid` test checks an execute, a cancel, a reply, an end, and a delta.
    /// The cell id is sixteen bytes of `0x11`. The execute body names that cell, the target `ollama`, the role `user`, and the text `hi`. The cancel type is cancel. The cancel keeps no piece. The stream reply body is `hello`. The stream end names the same cell. The delta kind is `t`. The delta body is `thought`. Channel T data `ok` is painted after the end and after the delta.
    #[test]
    fn the_execute_frame_is_channel_a_and_the_stream_reply_stays_off_the_grid() {
        let cell = [0x11u8; 16];
        let frame = execute_frame(&cell, "hi").expect("execute fits");
        let mut demux = Demux::new();
        demux.push(&init_frame());
        assert!(demux.push(&frame).is_empty());
        match &demux.take_scope_a()[0] {
            ScopePiece::Later { cell: id, body } => {
                assert_eq!(id[0], 0x11);
                let text = String::from_utf8(body.clone()).expect("toon");
                assert!(text.contains("cell_id: 11111111-1111-1111-1111-111111111111\n"));
                assert!(text.contains("execution_target: ollama\n"));
                assert!(text.contains("- role: user\n"));
                assert!(text.contains("  text: hi\n"));
            }
            _ => panic!("execute"),
        }
        let cancel = cancel_frame(&cell);
        assert_eq!(cancel[4], TYPE_CANCEL);
        assert!(demux.push(&cancel).is_empty());
        assert!(demux.take_scope_a().is_empty());

        let mut payload = vec![0u8; super::FRAG_PREFIX];
        payload[4] = 1;
        payload[8] = 0x11;
        payload.extend_from_slice(b"hello");
        let reply = encode(CHANNEL_STREAM, TYPE_DATA, 0, &payload).unwrap();
        assert!(demux.push(&reply).is_empty());
        match &demux.take_scope_a()[0] {
            ScopePiece::Reply { cell: id, body } => {
                assert_eq!(id[0], 0x11);
                assert_eq!(body, b"hello");
            }
            _ => panic!("reply"),
        }
        let mut eof_payload = vec![0u8; super::FRAG_PREFIX];
        eof_payload[4] = 1;
        eof_payload[8] = 0x11;
        let eof = encode(CHANNEL_STREAM, TYPE_EOF, 0, &eof_payload).unwrap();
        assert!(demux.push(&eof).is_empty());
        match &demux.take_scope_a()[0] {
            ScopePiece::End { cell: id } => assert_eq!(id[0], 0x11),
            _ => panic!("end"),
        }
        assert_eq!(demux.push(&scope_t_data(b"ok").unwrap()), b"ok");

        let mut delta = vec![0u8; super::FRAG_PREFIX];
        delta[4] = 1;
        delta[8] = 0x11;
        delta.push(b't');
        delta.extend_from_slice("thought".as_bytes());
        let frame = encode(CHANNEL_STREAM, TYPE_DELTA, 0, &delta).unwrap();
        assert!(demux.push(&frame).is_empty());
        match &demux.take_scope_a()[0] {
            ScopePiece::Delta { cell: id, kind, body } => {
                assert_eq!(id[0], 0x11);
                assert_eq!(*kind, b't');
                assert_eq!(body, b"thought");
            }
            _ => panic!("delta"),
        }
        assert_eq!(demux.push(&scope_t_data(b"ok").unwrap()), b"ok");
    }

    /// The `the_working_set_keeps_the_earlier_turn` test checks a working set of three lines.
    /// The cell id is sixteen bytes of `0x22`. The body names that cell and the target `ollama`. The lines are user `hi`, assistant `yo`, and user `next`. Channel T data `ok` is painted.
    #[test]
    fn the_working_set_keeps_the_earlier_turn() {
        let cell = [0x22u8; 16];
        let lines = [
            SetLine {
                role: "user",
                text: "hi",
                head: false,
            },
            SetLine {
                role: "assistant",
                text: "yo",
                head: false,
            },
            SetLine {
                role: "user",
                text: "next",
                head: false,
            },
        ];
        let frame = execute_set(&cell, &lines).expect("set fits");
        let mut demux = Demux::new();
        demux.push(&init_frame());
        assert!(demux.push(&frame).is_empty());
        match &demux.take_scope_a()[0] {
            ScopePiece::Later { cell: id, body } => {
                assert_eq!(id[0], 0x22);
                let text = String::from_utf8(body.clone()).expect("toon");
                assert!(text.contains("cell_id: 22222222-2222-2222-2222-222222222222\n"));
                assert!(text.contains("execution_target: ollama\n"));
                assert!(text.contains("- role: user\n  text: hi\n"));
                assert!(text.contains("- role: assistant\n  text: yo\n"));
                assert!(text.contains("- role: user\n  text: next\n"));
            }
            _ => panic!("set"),
        }
        assert_eq!(demux.push(&scope_t_data(b"ok").unwrap()), b"ok");
    }

    /// The `a_first_byte_that_is_not_magic_is_raw` test checks a buffer that does not start with the magic byte.
    /// The first push paints `hello` and selects raw. The next push paints ` more`.
    #[test]
    fn a_first_byte_that_is_not_magic_is_raw() {
        let mut demux = Demux::new();
        assert_eq!(demux.push(b"hello"), b"hello");
        assert!(demux.is_raw());
        assert_eq!(demux.push(b" more"), b" more");
    }

    /// The `a_crc_miss_while_waiting_paints_the_buffer_as_raw` test flips the last init byte.
    /// The push paints that buffer. The demux is raw. The demux is not broken.
    #[test]
    fn a_crc_miss_while_waiting_paints_the_buffer_as_raw() {
        let mut bad = init_frame();
        let last = bad.len() - 1;
        bad[last] ^= 0xFF;
        let mut demux = Demux::new();
        assert_eq!(demux.push(&bad), bad);
        assert!(demux.is_raw());
        assert!(!demux.is_broken());
    }

    /// The `a_crc_miss_on_the_mux_marks_it_broken` test flips the last byte of a channel T frame after init.
    /// The push paints nothing. The demux is broken. A later push of `more` paints nothing.
    #[test]
    fn a_crc_miss_on_the_mux_marks_it_broken() {
        let mut demux = Demux::new();
        demux.push(&init_frame());
        let mut bad = scope_t_data(b"hi").unwrap();
        let last = bad.len() - 1;
        bad[last] ^= 0xFF;
        assert!(demux.push(&bad).is_empty());
        assert!(demux.is_broken());
        assert!(demux.push(b"more").is_empty());
    }

    /// The `a_frame_split_across_pushes_still_decodes` test splits the init frame.
    /// The first ten init bytes leave the demux waiting. The rest of the init frame plus channel T data `ab` paints `ab`. The demux is then mux.
    #[test]
    fn a_frame_split_across_pushes_still_decodes() {
        let frame = scope_t_data(b"ab").unwrap();
        let mut demux = Demux::new();
        assert!(demux.push(&init_frame()[..10]).is_empty());
        assert!(demux.is_wait());
        let mut rest = init_frame()[10..].to_vec();
        rest.extend_from_slice(&frame);
        assert_eq!(demux.push(&rest), b"ab");
        assert!(demux.is_mux());
    }

    /// The `timeout_moves_wait_to_raw_and_paints_the_peek` test ends the wait on an incomplete header.
    /// The peeked bytes are `0xAE` and `0x01`. The demux is raw. A later push paints `x`.
    #[test]
    fn timeout_moves_wait_to_raw_and_paints_the_peek() {
        let mut demux = Demux::new();
        assert!(demux.push(&[0xAE, 0x01]).is_empty());
        assert_eq!(demux.timed_out(), vec![0xAE, 0x01]);
        assert!(demux.is_raw());
        assert_eq!(demux.push(b"x"), b"x");
    }

    /// The `a_payload_over_one_mebibyte_is_refused` test asks `encode` for a payload one byte past the limit.
    /// The result is `None`.
    #[test]
    fn a_payload_over_one_mebibyte_is_refused() {
        let big = vec![0u8; MAX_PAYLOAD + 1];
        assert!(encode(CHANNEL_T, TYPE_DATA, 0, &big).is_none());
    }
}
