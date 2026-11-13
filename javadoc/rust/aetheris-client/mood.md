# Module mood

[Rust tree](../index.md) · **aetheris-client**

**Source** `rust/aetheris/aetheris-client/src/mood.rs`

The module builds the mood invitation and chooses which turn carries it.

This module does not paint. The egui window stays in the chrome module. Canvas, group, and pipe call `splice_turn` to put the invitation on one user turn. The shell frame stays `shell_mood` and the line. The shaping sentences stay [`OPENER`](#constant-opener) and [`CLOSER`](#constant-closer). A missing pin is the text `head`. A pending define is a move that the next send consumes through `consume_pin`.

| | |
| --- | --- |
| **Specification** | L40 aa-sdd-source-prose.md#RustMoodDesk |
| **Specification** | GK-0151 aetheris-plan-f-200.001.md FINESSE:f-200 |
| **Author** | clayl (2026-11-03) |

## Type Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| struct | [`SendTurn`](mood/struct.SendTurn.md) | The [`SendTurn`] struct is one turn the send may carry. |

## Constant Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| const | [`OPENER`](#constant-opener) | [`OPENER`] is the sentence placed before the constructed mood in the older splice, and after `shape:` in the current invitation. |
| const | [`CLOSER`](#constant-closer) | [`CLOSER`] is the sentence placed after the constructed mood. |
| const | [`LEGACY`](#constant-legacy) | [`LEGACY`] is the activate sentence a transcript stored before the current invitation. |

## Function Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`normalize`](#function-normalize) | The `normalize` function trims `line` and returns that text. |
| fn | [`invite_head`](#function-invite_head) | The `invite_head` function builds the invitation that sits at the start of a turn. |
| fn | [`splice_turn`](#function-splice_turn) | The `splice_turn` function puts one invitation at the start of `turn`. |
| fn | [`unsplice_turn`](#function-unsplice_turn) | The `unsplice_turn` function returns `turn` with one spliced mood removed. |
| fn | [`line_at_turn_head`](#function-line_at_turn_head) | The `line_at_turn_head` function returns the mood line at the start of `turn`. |
| fn | [`invited_line`](#function-invited_line) | The `invited_line` function reads the current invitation at the start of `turn`. |
| fn | [`older_splice_line`](#function-older_splice_line) | The `older_splice_line` function reads the older invitation at the start of `turn`. |
| fn | [`debug_lines`](#function-debug_lines) | The `debug_lines` function returns the physical lines of one invitation. |
| fn | [`pin_index`](#function-pin_index) | The `pin_index` function returns the index of `pin` in `ordered`. |
| fn | [`place`](#function-place) | The `place` function returns the first user turn at or after the pin. |
| fn | [`consume_pin`](#function-consume_pin) | The `consume_pin` function returns the pin a pending define stores. |
| fn | [`walked_pin`](#function-walked_pin) | The `walked_pin` function returns the pin after a delete that removed it. |

## Constant Detail

### OPENER

<a id="constant-opener"></a>

```rust
pub const OPENER: &str =
"I'm opening a window with this atmosphere\u{2026} would you like to step into it with me?";
```

[`OPENER`](#constant-opener) is the sentence placed before the constructed mood in the older splice, and after `shape:` in the current invitation.
Canvas and the chrome window both use this line. The text asks whether to step into the atmosphere.

### CLOSER

<a id="constant-closer"></a>

```rust
pub const CLOSER: &str =
"Shape your replies around this mood \u{2014} let it breathe through the words.";
```

[`CLOSER`](#constant-closer) is the sentence placed after the constructed mood.
Canvas and the chrome window both use this line. The text asks the reply to follow the mood.

### LEGACY

<a id="constant-legacy"></a>

```rust
pub const LEGACY: &str =
"Activate the mood above. Use that mood as the shape of your responses.";
```

[`LEGACY`](#constant-legacy) is the activate sentence a transcript stored before the current invitation.
A fresh splice does not write this sentence. The tests check that the new text does not contain it.

## Function Detail

### normalize

<a id="function-normalize"></a>

```rust
pub fn normalize(line: &str) -> Option<&str>
```

The `normalize` function trims `line` and returns that text.
An empty result after trim returns `None`. Any other result returns `Some` with the trimmed text.

### invite_head

<a id="function-invite_head"></a>

```rust
pub fn invite_head(line: &str) -> String
```

The `invite_head` function builds the invitation that sits at the start of a turn.
The text is `mood:`, a space, `line`, a newline, `shape:`, a newline, [`OPENER`](#constant-opener), a newline, [`CLOSER`](#constant-closer), and a newline.

### splice_turn

<a id="function-splice_turn"></a>

```rust
pub fn splice_turn(turn: &str, line: Option<&str>) -> String
```

The `splice_turn` function puts one invitation at the start of `turn`.
The function strips a stored invitation first, including the older order of [`OPENER`](#constant-opener), the line, and [`CLOSER`](#constant-closer). A missing line returns that stripped turn. A line that trims to empty returns that stripped turn. Any other line prepends `invite_head` of the trimmed line.

### unsplice_turn

<a id="function-unsplice_turn"></a>

```rust
pub fn unsplice_turn(turn: &str) -> String
```

The `unsplice_turn` function returns `turn` with one spliced mood removed.
No mood at the start returns a copy of `turn`. A current invitation prefix is removed. Otherwise the older prefix of [`OPENER`](#constant-opener), the line, and [`CLOSER`](#constant-closer) is removed. A recognized line whose prefix does not match returns the original `turn`.

### line_at_turn_head

<a id="function-line_at_turn_head"></a>

```rust
pub fn line_at_turn_head(turn: &str) -> Option<String>
```

The `line_at_turn_head` function returns the mood line at the start of `turn`.
The current `mood:` form is tried first. The older form that starts with [`OPENER`](#constant-opener) is tried second. Neither form returns `None`.

### invited_line

<a id="function-invited_line"></a>

```rust
fn invited_line(turn: &str) -> Option<String>
```

The `invited_line` function reads the current invitation at the start of `turn`.
The text must start with `mood: `. The mood is the rest of that first line, after trim. The following text must be `shape:`, [`OPENER`](#constant-opener), and [`CLOSER`](#constant-closer), alone or with more text after a newline. Any other shape returns `None`.

### older_splice_line

<a id="function-older_splice_line"></a>

```rust
fn older_splice_line(turn: &str) -> Option<String>
```

The `older_splice_line` function reads the older invitation at the start of `turn`.
The text must start with [`OPENER`](#constant-opener) and a newline. The next line is the mood. The text after that line must be [`CLOSER`](#constant-closer), alone or with more text after a newline. Any other shape returns `None`.

### debug_lines

<a id="function-debug_lines"></a>

```rust
pub fn debug_lines(line: &str) -> Vec<String>
```

The `debug_lines` function returns the physical lines of one invitation.
A line that trims to empty returns an empty vector. Any other line splices that mood onto an empty turn and keeps each non-empty line. The trailing newline is not a line.

### pin_index

<a id="function-pin_index"></a>

```rust
pub fn pin_index(ordered: &[&str], pin: &str) -> Option<usize>
```

The `pin_index` function returns the index of `pin` in `ordered`.
An empty pin returns `None`. The pin text `head` returns `None`. A pin that `ordered` does not hold returns `None`. Any other pin returns `Some` with that index.

### place

<a id="function-place"></a>

```rust
pub fn place<'a>(send: &[SendTurn<'a>], pin_at: Option<usize>) -> Option<&'a str>
```

The `place` function returns the first user turn at or after the pin.
A missing pin, which is `None`, is the first user turn in `send`. A turn whose `user` flag is false is skipped. A turn whose `at` is before `pin_at` is skipped. No matching turn returns `None`.

### consume_pin

<a id="function-consume_pin"></a>

```rust
pub fn consume_pin(prior_users: usize, target_id: &str) -> String
```

The `consume_pin` function returns the pin a pending define stores.
More than one prior user turn returns `target_id`. One prior user turn returns `head`. Zero prior user turns returns `head`. This function does not clear a flag. The caller drops the pending mark after it reads this pin.

### walked_pin

<a id="function-walked_pin"></a>

```rust
pub fn walked_pin(ordered: &[&str], removed: &[&str], pin: &str) -> String
```

The `walked_pin` function returns the pin after a delete that removed it.
An empty pin stays. The pin text `head` stays. A pin that is not in `removed` stays. The cell before the first removed id keeps the pin. No cell before that hole returns `head`.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
