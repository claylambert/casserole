# struct Grid

[Rust tree](../../index.md) · [aetheris-term](../index.md) · **grid**

**Source** `rust/aetheris/aetheris-term/src/grid.rs`

```rust
pub struct Grid
```

The [`Grid`](struct.Grid.md) struct is the terminal grid.
The caller serializes `feed` and paint. `cols` and `rows` are the visible size. `scroll_cap` is the scrollback limit. `primary` and `alternate` are the two screens. `alt` selects the alternate screen. `parser` is the `vte` parser. `reply` holds bytes for the caller to write. `bell`, `dirty`, `paste`, and `decckm` are the flags.

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| usize | [`cols`](#member-cols) |  |
| usize | [`rows`](#member-rows) |  |
| usize | [`scroll_cap`](#member-scroll_cap) |  |
| Screen | [`primary`](#member-primary) |  |
| Screen | [`alternate`](#member-alternate) |  |
| bool | [`alt`](#member-alt) |  |
| vte::Parser | [`parser`](#member-parser) |  |
| Vec<u8> | [`reply`](#member-reply) |  |
| bool | [`bell`](#member-bell) |  |
| bool | [`dirty`](#member-dirty) |  |
| bool | [`paste`](#member-paste) |  |
| bool | [`decckm`](#member-decckm) |  |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`new`](#method-new) | The `new` method returns a grid with the default scrollback cap. |
| fn | [`with_scrollback`](#method-with_scrollback) | The `with_scrollback` method returns a grid whose ring keeps at most `scroll_cap` lines. |
| fn | [`feed`](#method-feed) | The `feed` method advances the parser. |
| fn | [`resize`](#method-resize) | The `resize` method changes the visible size. |
| fn | [`cols`](#method-cols) | The `cols` method returns the visible column count. |
| fn | [`rows`](#method-rows) | The `rows` method returns the visible row count. |
| fn | [`cursor`](#method-cursor) | The `cursor` method returns the cursor cell. |
| fn | [`bell`](#method-bell) | The `bell` method reports the bell flag. |
| fn | [`take_bell`](#method-take_bell) | The `take_bell` method returns the bell flag and clears it. |
| fn | [`paste_mode`](#method-paste_mode) | The `paste_mode` method reports private mode 2004. |
| fn | [`decckm`](#method-decckm) | The `decckm` method reports private mode 1. |
| fn | [`dirty`](#method-dirty) | The `dirty` method reports that the grid changed. |
| fn | [`clear_dirty`](#method-clear_dirty) | The `clear_dirty` method clears the dirty flag. |
| fn | [`clear_buffer`](#method-clear_buffer) | The `clear_buffer` method clears the active screen and its scrollback. |
| fn | [`alternate_screen`](#method-alternate_screen) | The `alternate_screen` method reports the alternate screen. |
| fn | [`clear_scrollback_keep_line`](#method-clear_scrollback_keep_line) | The `clear_scrollback_keep_line` method clears history and keeps the cursor line. |
| fn | [`take_reply`](#method-take_reply) | The `take_reply` method returns bytes the grid wants the caller to write. |
| fn | [`scrollback_len`](#method-scrollback_len) | The `scrollback_len` method returns the scrollback length of the active screen. |
| fn | [`scrollback_text`](#method-scrollback_text) | The `scrollback_text` method returns one scrollback line as text. |
| fn | [`visible_text`](#method-visible_text) | The `visible_text` method returns one visible row as text. |
| fn | [`visible_cell`](#method-visible_cell) | The `visible_cell` method returns one visible cell. |
| fn | [`history_len`](#method-history_len) | The `history_len` method returns the lines paint can walk. |
| fn | [`history_line`](#method-history_line) | The `history_line` method returns one row of the ring plus the screen. |
| fn | [`active`](#method-active) | The `active` method returns the screen that is showing. |
| fn | [`active_mut`](#method-active_mut) | The `active_mut` method returns the showing screen for mutation. |
| fn | [`mark`](#method-mark) | The `mark` method sets the dirty flag. |
| fn | [`carriage_return`](#method-carriage_return) | The `carriage_return` method moves the cursor to column 0. |
| fn | [`line_feed`](#method-line_feed) | The `line_feed` method moves the cursor down one row. |
| fn | [`reverse_index`](#method-reverse_index) | The `reverse_index` method moves the cursor up one row. |
| fn | [`backspace`](#method-backspace) | The `backspace` method moves the cursor left one column. |
| fn | [`move_col`](#method-move_col) | The `move_col` method adds `delta` to the column. |
| fn | [`move_row`](#method-move_row) | The `move_row` method adds `delta` to the row. |
| fn | [`place`](#method-place) | The `place` method sets the cursor. |
| fn | [`print_char`](#method-print_char) | The `print_char` method writes one character. |
| fn | [`note_wrap`](#method-note_wrap) | The `note_wrap` method marks the row being left as continued on the next row. |
| fn | [`attach`](#method-attach) | The `attach` method adds a zero-width character to the previous cell. |
| fn | [`put_glyph`](#method-put_glyph) | The `put_glyph` method stores `ch` at the cursor. |
| fn | [`delete_lines`](#method-delete_lines) | The `delete_lines` method deletes `n` lines at the cursor. |
| fn | [`erase_line`](#method-erase_line) | The `erase_line` method clears cells on the cursor row. |
| fn | [`erase_display`](#method-erase_display) | The `erase_display` method clears rows. |
| fn | [`clear_row`](#method-clear_row) | The `clear_row` method blanks one visible row. |
| fn | [`save_cursor`](#method-save_cursor) | The `save_cursor` method stores the cursor and the SGR state. |
| fn | [`restore_cursor`](#method-restore_cursor) | The `restore_cursor` method puts the saved cursor back. |
| fn | [`enter_alt`](#method-enter_alt) | The `enter_alt` method shows the alternate screen. |
| fn | [`leave_alt`](#method-leave_alt) | The `leave_alt` method shows the primary screen. |
| fn | [`reset_attrs`](#method-reset_attrs) | The `reset_attrs` method restores the default SGR state. |
| fn | [`sgr`](#method-sgr) | The `sgr` method applies a CSI `m` sequence. |
| fn | [`set_private`](#method-set_private) | The `set_private` method applies one private mode. |

## Field Detail

### cols

<a id="member-cols"></a>

```rust
cols: usize
```

### rows

<a id="member-rows"></a>

```rust
rows: usize
```

### scroll_cap

<a id="member-scroll_cap"></a>

```rust
scroll_cap: usize
```

### primary

<a id="member-primary"></a>

```rust
primary: Screen
```

### alternate

<a id="member-alternate"></a>

```rust
alternate: Screen
```

### alt

<a id="member-alt"></a>

```rust
alt: bool
```

### parser

<a id="member-parser"></a>

```rust
parser: vte::Parser
```

### reply

<a id="member-reply"></a>

```rust
reply: Vec<u8>
```

### bell

<a id="member-bell"></a>

```rust
bell: bool
```

### dirty

<a id="member-dirty"></a>

```rust
dirty: bool
```

### paste

<a id="member-paste"></a>

```rust
paste: bool
```

### decckm

<a id="member-decckm"></a>

```rust
decckm: bool
```

## Method Detail

### new

<a id="method-new"></a>

```rust
pub fn new(cols: usize, rows: usize) -> Self
```

The `new` method returns a grid with the default scrollback cap.
The cap is [`SCROLLBACK_CAP`](../grid.md#constant-scrollback_cap). A column count of 0 becomes 1. A row count of 0 becomes 1.

### with_scrollback

<a id="method-with_scrollback"></a>

```rust
pub fn with_scrollback(cols: usize, rows: usize, scroll_cap: usize) -> Self
```

The `with_scrollback` method returns a grid whose ring keeps at most `scroll_cap` lines.
A column count of 0 becomes 1. A row count of 0 becomes 1. The primary screen is showing. The reply is empty. Bell, dirty, paste, and cursor-key mode are off.

### feed

<a id="method-feed"></a>

```rust
pub fn feed(&mut self, bytes: &[u8])
```

The `feed` method advances the parser.
The byte slice is borrowed for this call only. The parser is moved out, advanced, and moved back. The cells stay owned by the grid.

### resize

<a id="method-resize"></a>

```rust
pub fn resize(&mut self, cols: usize, rows: usize)
```

The `resize` method changes the visible size.
A zero column count returns and keeps the last positive size. A zero row count does the same. A primary screen reflows a soft wrap when the column count changes. The alternate screen pads or clips.

A cursor on the last row stays on the last row. A taller screen fills above that row. A shorter screen keeps that row and moves the lines above it into the scrollback. Any other cursor keeps that row. The grid is marked dirty.

### cols

<a id="method-cols"></a>

```rust
pub fn cols(&self) -> usize
```

The `cols` method returns the visible column count.
The value is the count stored after the last accepted resize.

### rows

<a id="method-rows"></a>

```rust
pub fn rows(&self) -> usize
```

The `rows` method returns the visible row count.
The value is the count stored after the last accepted resize.

### cursor

<a id="method-cursor"></a>

```rust
pub fn cursor(&self) -> (usize, usize)
```

The `cursor` method returns the cursor cell.
The pair is clamped into the visible screen. Row and column are 0-based.

### bell

<a id="method-bell"></a>

```rust
pub fn bell(&self) -> bool
```

The `bell` method reports the bell flag.
True means a C0 bell has arrived since the last take.

### take_bell

<a id="method-take_bell"></a>

```rust
pub fn take_bell(&mut self) -> bool
```

The `take_bell` method returns the bell flag and clears it.
A second call returns false until the next bell.

### paste_mode

<a id="method-paste_mode"></a>

```rust
pub fn paste_mode(&self) -> bool
```

The `paste_mode` method reports private mode 2004.
True means bracketed paste is on.

### decckm

<a id="method-decckm"></a>

```rust
pub fn decckm(&self) -> bool
```

The `decckm` method reports private mode 1.
True means cursor-key mode is on.

### dirty

<a id="method-dirty"></a>

```rust
pub fn dirty(&self) -> bool
```

The `dirty` method reports that the grid changed.
True means paint has not cleared the flag.

### clear_dirty

<a id="method-clear_dirty"></a>

```rust
pub fn clear_dirty(&mut self)
```

The `clear_dirty` method clears the dirty flag.
The cells stay as they are.

### clear_buffer

<a id="method-clear_buffer"></a>

```rust
pub fn clear_buffer(&mut self)
```

The `clear_buffer` method clears the active screen and its scrollback.
The cursor goes home. Wrap is cleared. The saved cursor is dropped. SGR returns to the default. Paste mode stays. The keyboard mode stays. The other screen stays. The grid is marked dirty.

### alternate_screen

<a id="method-alternate_screen"></a>

```rust
pub fn alternate_screen(&self) -> bool
```

The `alternate_screen` method reports the alternate screen.
True means that screen is showing.

### clear_scrollback_keep_line

<a id="method-clear_scrollback_keep_line"></a>

```rust
pub fn clear_scrollback_keep_line(&mut self) -> bool
```

The `clear_scrollback_keep_line` method clears history and keeps the cursor line.
The alternate screen is left alone and the method returns false. On the primary screen the cursor line moves to the top. The column stays. Wrap is cleared. The grid is marked dirty. The method returns true.

### take_reply

<a id="method-take_reply"></a>

```rust
pub fn take_reply(&mut self) -> Vec<u8>
```

The `take_reply` method returns bytes the grid wants the caller to write.
Device attributes and a cursor-position report use this buffer. The buffer is empty afterward.

### scrollback_len

<a id="method-scrollback_len"></a>

```rust
pub fn scrollback_len(&self) -> usize
```

The `scrollback_len` method returns the scrollback length of the active screen.
The count does not include the visible rows.

### scrollback_text

<a id="method-scrollback_text"></a>

```rust
pub fn scrollback_text(&self, index: usize) -> String
```

The `scrollback_text` method returns one scrollback line as text.
Index 0 is the oldest line. A missing index returns an empty string.

### visible_text

<a id="method-visible_text"></a>

```rust
pub fn visible_text(&self, row: usize) -> String
```

The `visible_text` method returns one visible row as text.
Row 0 is the top of the screen. A missing row returns an empty string.

### visible_cell

<a id="method-visible_cell"></a>

```rust
pub fn visible_cell(&self, row: usize, col: usize) -> Option<&Cell>
```

The `visible_cell` method returns one visible cell.
A row or column outside the screen returns `None`.

### history_len

<a id="method-history_len"></a>

```rust
pub fn history_len(&self) -> usize
```

The `history_len` method returns the lines paint can walk.
The count is the scrollback length plus the visible row count. Index 0 is the oldest scrollback line.

### history_line

<a id="method-history_line"></a>

```rust
pub fn history_line(&self, index: usize) -> Option<&[Cell]>
```

The `history_line` method returns one row of the ring plus the screen.
Index 0 is the oldest line. An index past the last visible row returns `None`.

### active

<a id="method-active"></a>

```rust
fn active(&self) -> &Screen
```

The `active` method returns the screen that is showing.
The alternate screen is returned when `alt` is set. Otherwise the primary screen is returned.

### active_mut

<a id="method-active_mut"></a>

```rust
fn active_mut(&mut self) -> &mut Screen
```

The `active_mut` method returns the showing screen for mutation.
The choice is the same as `active`.

### mark

<a id="method-mark"></a>

```rust
fn mark(&mut self)
```

The `mark` method sets the dirty flag.
The cells are not changed here.

### carriage_return

<a id="method-carriage_return"></a>

```rust
fn carriage_return(&mut self)
```

The `carriage_return` method moves the cursor to column 0.
Wrap pending is cleared.

### line_feed

<a id="method-line_feed"></a>

```rust
fn line_feed(&mut self)
```

The `line_feed` method moves the cursor down one row.
A cursor that is not on the last row only changes the row. A cursor on the last row scrolls the visible lines up. On the primary screen the top line enters the scrollback. A scrollback past the cap drops the oldest line. The alternate screen discards that top line. Wrap pending is cleared.

### reverse_index

<a id="method-reverse_index"></a>

```rust
fn reverse_index(&mut self)
```

The `reverse_index` method moves the cursor up one row.
A cursor on row 0 inserts a blank line at the top and drops the bottom line. Wrap pending is cleared. The scrollback is not used.

### backspace

<a id="method-backspace"></a>

```rust
fn backspace(&mut self)
```

The `backspace` method moves the cursor left one column.
A pending wrap is cleared and the column stays. A column of 0 stays at 0.

### move_col

<a id="method-move_col"></a>

```rust
fn move_col(&mut self, delta: isize)
```

The `move_col` method adds `delta` to the column.
The column is clamped to the visible screen. Wrap pending is cleared.

### move_row

<a id="method-move_row"></a>

```rust
fn move_row(&mut self, delta: isize)
```

The `move_row` method adds `delta` to the row.
The row is clamped to the visible screen. Wrap pending is cleared. The screen does not scroll.

### place

<a id="method-place"></a>

```rust
fn place(&mut self, row: usize, col: usize)
```

The `place` method sets the cursor.
The row and the column are clamped into the visible screen. Wrap pending is cleared.

### print_char

<a id="method-print_char"></a>

```rust
fn print_char(&mut self, ch: char)
```

The `print_char` method writes one character.
A width of 0 attaches the character to the previous cell. A pending wrap, or a glyph that does not fit, marks the row as wrapped and feeds a line. The glyph is then stored. The grid is marked dirty.

### note_wrap

<a id="method-note_wrap"></a>

```rust
fn note_wrap(&mut self)
```

The `note_wrap` method marks the row being left as continued on the next row.
A cursor already at column 0 with no pending wrap returns. `used` is the column count, or the full width when wrap is already pending.

### attach

<a id="method-attach"></a>

```rust
fn attach(&mut self, ch: char)
```

The `attach` method adds a zero-width character to the previous cell.
A cursor at column 0 returns. A spacer cell attaches to the cell before it. The grid is marked dirty when a cell is changed.

### put_glyph

<a id="method-put_glyph"></a>

```rust
fn put_glyph(&mut self, ch: char, width: u8)
```

The `put_glyph` method stores `ch` at the cursor.
The cell copies the current SGR state. A width of 2 writes an empty spacer in the next column. A glyph that ends on the last column sets wrap pending. Any other glyph advances the column by `width`.

### delete_lines

<a id="method-delete_lines"></a>

```rust
fn delete_lines(&mut self, n: usize)
```

The `delete_lines` method deletes `n` lines at the cursor.
A count of 0 returns. Lines below shift up. Blank lines fill the bottom of the screen. The cursor stays. Deleted lines stay out of the scrollback. The screen is the region. The row above the deletion loses its wrap flag.

### erase_line

<a id="method-erase_line"></a>

```rust
fn erase_line(&mut self, mode: usize)
```

The `erase_line` method clears cells on the cursor row.
Mode 1 clears from the start through the cursor. Mode 2 clears the whole row. Any other mode clears from the cursor to the end. Mode 1 keeps the wrap flag. The other modes clear it. Blank cells copy the current SGR colors.

### erase_display

<a id="method-erase_display"></a>

```rust
fn erase_display(&mut self, mode: usize)
```

The `erase_display` method clears rows.
Mode 1 clears the cursor line through the cursor and every row above it. Mode 2 clears every visible row. Any other mode clears from the cursor through the end of the screen.

### clear_row

<a id="method-clear_row"></a>

```rust
fn clear_row(&mut self, row: usize)
```

The `clear_row` method blanks one visible row.
The wrap flag is cleared. Each cell copies the current SGR colors. A missing row returns.

### save_cursor

<a id="method-save_cursor"></a>

```rust
fn save_cursor(&mut self)
```

The `save_cursor` method stores the cursor and the SGR state.
A later save replaces the stored value.

### restore_cursor

<a id="method-restore_cursor"></a>

```rust
fn restore_cursor(&mut self)
```

The `restore_cursor` method puts the saved cursor back.
The row and the column are clamped. Wrap pending is cleared. A screen with no saved cursor is left as it is.

### enter_alt

<a id="method-enter_alt"></a>

```rust
fn enter_alt(&mut self, clear: bool)
```

The `enter_alt` method shows the alternate screen.
`clear` replaces that screen with a blank screen of the current size. The grid is marked dirty.

### leave_alt

<a id="method-leave_alt"></a>

```rust
fn leave_alt(&mut self)
```

The `leave_alt` method shows the primary screen.
The alternate screen is kept. The grid is marked dirty.

### reset_attrs

<a id="method-reset_attrs"></a>

```rust
fn reset_attrs(&mut self)
```

The `reset_attrs` method restores the default SGR state.
Colors are unset. Bold and reverse are off.

### sgr

<a id="method-sgr"></a>

```rust
fn sgr(&mut self, params: &Params)
```

The `sgr` method applies a CSI `m` sequence.
An empty parameter list resets SGR. Code 0 resets. Code 1 sets bold. Code 22 clears bold. Code 7 sets reverse. Code 27 clears reverse. Codes 30 through 37 and 90 through 97 set an indexed foreground. Codes 40 through 47 and 100 through 107 set an indexed background. Code 39 restores the default foreground. Code 49 restores the default background. Codes 38 and 48 read an indexed or RGB color. Any other code is ignored.

### set_private

<a id="method-set_private"></a>

```rust
fn set_private(&mut self, mode: u16, on: bool)
```

The `set_private` method applies one private mode.
Mode 1 sets the cursor-key flag. Mode 2004 sets the paste flag. Modes 47 and 1047 enter or leave the alternate screen without clearing it. Mode 1049 saves the cursor, enters a cleared alternate screen, and restores the cursor on the way out. Any other mode is ignored.

## impl Perform for Grid

```rust
impl Perform for Grid
```

### print

<a id="method-print"></a>

```rust
fn print(&mut self, ch: char)
```

The `print` method is the `vte` print callback.
The character is passed to `print_char`.

### execute

<a id="method-execute"></a>

```rust
fn execute(&mut self, byte: u8)
```

The `execute` method handles one C0 byte.
`0x07` sets the bell. `0x08` backspaces. `0x09` moves to the next tab stop of 8 columns. `0x0A`, `0x0B`, and `0x0C` feed a line. `0x0D` returns the carriage. Any other byte is ignored. A handled byte marks the grid dirty.

### hook

<a id="method-hook"></a>

```rust
fn hook(&mut self, _params: &Params, _intermediates: &[u8], _ignore: bool, _action: char)
```

The `hook` method consumes a DCS start.
The bytes change nothing.

### put

<a id="method-put"></a>

```rust
fn put(&mut self, _byte: u8)
```

The `put` method consumes one DCS byte.
The byte changes nothing.

### unhook

<a id="method-unhook"></a>

```rust
fn unhook(&mut self)
```

The `unhook` method consumes a DCS end.
The grid is unchanged.

### osc_dispatch

<a id="method-osc_dispatch"></a>

```rust
fn osc_dispatch(&mut self, _params: &[&[u8]], _bell_terminated: bool)
```

The `osc_dispatch` method consumes an OSC string.
The string changes nothing.

### csi_dispatch

<a id="method-csi_dispatch"></a>

```rust
fn csi_dispatch(&mut self, params: &Params, intermediates: &[u8], _ignore: bool, action: char)
```

The `csi_dispatch` method honors one CSI sequence.
`A` and `B` move the row. `C` and `D` move the column. `H` and `f` place the cursor from 1-based parameters. `J` erases the display. `K` erases the line. `M` deletes lines. `m` applies SGR. `c` appends the bytes ESC `[?1;0c` to the reply. `n` with parameter 6 appends a cursor-position report. `h` and `l` with a question mark set private modes. `s` and `u` without a question mark save and restore the cursor. Any other action is ignored. The grid is marked dirty.

### esc_dispatch

<a id="method-esc_dispatch"></a>

```rust
fn esc_dispatch(&mut self, intermediates: &[u8], _ignore: bool, byte: u8)
```

The `esc_dispatch` method honors one ESC sequence that is not CSI.
A sequence with intermediates returns. `7` saves the cursor. `8` restores it. `D` feeds a line. `M` reverse-indexes. `E` returns the carriage and feeds a line. Any other byte is ignored. The grid is marked dirty.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
