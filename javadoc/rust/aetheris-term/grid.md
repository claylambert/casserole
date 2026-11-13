# Module grid

[Rust tree](../index.md) · **aetheris-term**

**Source** `rust/aetheris/aetheris-term/src/grid.rs`

The module owns one terminal grid.

The `feed` method advances the `vte` parser. The parser writes cells this grid owns. The caller serializes `feed` and paint. The honored sequences are library serial section 8 and the VT100 plan. A C0 bell sets the bell flag. Backspace, tab, line feed, vertical tab, form feed, and carriage return move the cursor. CSI cursor motion, erase, delete-line, and SGR are honored. CSI `c` answers with the device-attributes bytes. CSI `n` with parameter 6 answers with the cursor position. Private mode 1 is the cursor-key flag. Private mode 2004 is the paste flag. Private modes 47 and 1047 show the alternate screen without clearing it. Private mode 1049 shows the alternate screen, clears it, and saves the cursor.

Origin mode is consumed and changes nothing. Insert mode is consumed and changes nothing. OSC is consumed and changes nothing. DCS is consumed and changes nothing. Sixel is consumed and changes nothing. A charset switch is consumed and changes nothing.

| | |
| --- | --- |
| **Specification** | L37 rust-terminal-library.draft.001.md |
| **Author** | clayl (2026-10-23) |

## Type Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| enum | [`Color`](grid/enum.Color.md) | The [`Color`] enum is a color a cell stores. |
| struct | [`Cell`](grid/struct.Cell.md) | The [`Cell`] struct is one screen cell. |
| struct | [`Attrs`](grid/struct.Attrs.md) | The `Attrs` struct is the current SGR state of one screen. |
| struct | [`Saved`](grid/struct.Saved.md) | The `Saved` struct is a cursor saved by DECSC or CSI `s`. |
| struct | [`Line`](grid/struct.Line.md) | The `Line` struct is one screen row. |
| struct | [`Screen`](grid/struct.Screen.md) | The `Screen` struct is one buffer, primary or alternate. |
| struct | [`Grid`](grid/struct.Grid.md) | The [`Grid`] struct is the terminal grid. |

## Constant Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| const | [`DEFAULT_COLS`](#constant-default_cols) | The `DEFAULT_COLS` constant is the visible column count of a fresh grid. |
| const | [`DEFAULT_ROWS`](#constant-default_rows) | The `DEFAULT_ROWS` constant is the visible row count of a fresh grid. |
| const | [`SCROLLBACK_CAP`](#constant-scrollback_cap) | The `SCROLLBACK_CAP` constant is the number of lines kept above the visible screen. |
| const | [`COMBINING_CAP`](#constant-combining_cap) | The `COMBINING_CAP` constant is the extra combining marks one cell will keep. |

## Function Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`blank_line`](#function-blank_line) | The `blank_line` function returns a row of `cols` blank cells. |
| fn | [`blank_with`](#function-blank_with) | The `blank_with` function returns an empty cell that copies `attrs`. |
| fn | [`push_mark`](#function-push_mark) | The `push_mark` function appends a combining mark. |
| fn | [`line_text`](#function-line_text) | The `line_text` function joins a row into text. |
| fn | [`resize_screen`](#function-resize_screen) | The `resize_screen` function resizes one screen. |
| fn | [`fit_line`](#function-fit_line) | The `fit_line` function pads or clips one line to `cols`. |
| fn | [`reflow_primary`](#function-reflow_primary) | The `reflow_primary` function rewraps the primary screen to a new column count. |
| fn | [`is_pad`](#function-is_pad) | The `is_pad` function reports a line whose cells have no text. |
| fn | [`content_of`](#function-content_of) | The `content_of` function returns the cells that belong to the text. |
| fn | [`take_columns`](#function-take_columns) | The `take_columns` function copies cells until `limit` columns are taken. |
| fn | [`content_width`](#function-content_width) | The `content_width` function sums cell widths. |
| fn | [`rewrap_lines`](#function-rewrap_lines) | The `rewrap_lines` function rewraps joined lines to `cols`. |
| fn | [`col_in_content`](#function-col_in_content) | The `col_in_content` function maps a cursor column into the content width. |
| fn | [`rewrap_cells`](#function-rewrap_cells) | The `rewrap_cells` function wraps `cells` into lines of `cols` columns. |
| fn | [`finish_line`](#function-finish_line) | The `finish_line` function pads or clips `cells` into one [`Line`]. |
| fn | [`locate_in_piece`](#function-locate_in_piece) | The `locate_in_piece` function finds `target` inside a rewrapped run. |
| fn | [`seat_window`](#function-seat_window) | The `seat_window` function chooses the visible window after a reflow. |
| fn | [`flatten`](#function-flatten) | The `flatten` function copies CSI parameters into one list. |
| fn | [`param_at`](#function-param_at) | The `param_at` function returns one CSI parameter. |
| fn | [`motion`](#function-motion) | The `motion` function returns a cursor distance. |
| fn | [`read_color`](#function-read_color) | The `read_color` function reads an SGR 38 or 48 color. |

## Constant Detail

### DEFAULT_COLS

<a id="constant-default_cols"></a>

```rust
pub const DEFAULT_COLS: usize = 80;
```

The `DEFAULT_COLS` constant is the visible column count of a fresh grid.
The value is 80.

### DEFAULT_ROWS

<a id="constant-default_rows"></a>

```rust
pub const DEFAULT_ROWS: usize = 24;
```

The `DEFAULT_ROWS` constant is the visible row count of a fresh grid.
The value is 24.

### SCROLLBACK_CAP

<a id="constant-scrollback_cap"></a>

```rust
pub const SCROLLBACK_CAP: usize = 10_000;
```

The `SCROLLBACK_CAP` constant is the number of lines kept above the visible screen.
The value is 10000. A line feed past that count drops the oldest line.

### COMBINING_CAP

<a id="constant-combining_cap"></a>

```rust
const COMBINING_CAP: usize = 8;
```

The `COMBINING_CAP` constant is the extra combining marks one cell will keep.
The value is 8. The base character is not part of that count.

## Function Detail

### blank_line

<a id="function-blank_line"></a>

```rust
fn blank_line(cols: usize) -> Line
```

The `blank_line` function returns a row of `cols` blank cells.
`used` is 0. The row is not wrapped.

### blank_with

<a id="function-blank_with"></a>

```rust
fn blank_with(attrs: &Attrs) -> Cell
```

The `blank_with` function returns an empty cell that copies `attrs`.
The width is 1. The text is empty.

### push_mark

<a id="function-push_mark"></a>

```rust
fn push_mark(cell: &mut Cell, ch: char)
```

The `push_mark` function appends a combining mark.
Marks already on the cell are counted after the first character. A cell that already has [`COMBINING_CAP`](#constant-combining_cap) extra marks ignores `ch`.

### line_text

<a id="function-line_text"></a>

```rust
fn line_text(line: &[Cell]) -> String
```

The `line_text` function joins a row into text.
A spacer cell is skipped. An empty cell becomes a space. Trailing spaces are removed.

### resize_screen

<a id="function-resize_screen"></a>

```rust
fn resize_screen(screen: &mut Screen, cols: usize, rows: usize, scroll_cap: usize, alt: bool)
```

The `resize_screen` function resizes one screen.
A primary screen whose column count changed is reflowed. Every other screen pads or clips each line. A taller screen with the cursor on the last row fills above that row. A taller screen with any other cursor adds blank rows at the bottom. A shorter screen whose cursor would fall off keeps that row and moves the lines above it into the scrollback. A shorter screen with any other cursor drops the rows below the new bottom. The cursor and a saved cursor are clamped.

### fit_line

<a id="function-fit_line"></a>

```rust
fn fit_line(line: &mut Line, cols: usize)
```

The `fit_line` function pads or clips one line to `cols`.
Added cells are blank. `used` and `wrapped` stay as they were.

### reflow_primary

<a id="function-reflow_primary"></a>

```rust
fn reflow_primary(screen: &mut Screen, cols: usize, rows: usize, scroll_cap: usize)
```

The `reflow_primary` function rewraps the primary screen to a new column count.
Scrollback and the visible rows are joined. Trailing pad rows after the cursor are dropped. Wrapped runs are rewrapped. The cursor and a saved cursor are placed again. A cursor that was on the last row stays on the last row of the new window.

### is_pad

<a id="function-is_pad"></a>

```rust
fn is_pad(line: &Line) -> bool
```

The `is_pad` function reports a line whose cells have no text.
A spacer cell with empty text still counts as a pad.

### content_of

<a id="function-content_of"></a>

```rust
fn content_of(line: &Line) -> Vec<Cell>
```

The `content_of` function returns the cells that belong to the text.
A wrapped line with `used` greater than 0 keeps that many columns. An unwrapped line drops trailing blank cells. A spacer cell is left out of the result.

### take_columns

<a id="function-take_columns"></a>

```rust
fn take_columns(cells: &[Cell], limit: usize) -> Vec<Cell>
```

The `take_columns` function copies cells until `limit` columns are taken.
A spacer cell is skipped. A wide cell counts as its width.

### content_width

<a id="function-content_width"></a>

```rust
fn content_width(cells: &[Cell]) -> usize
```

The `content_width` function sums cell widths.
A width of 0 counts as 1.

### rewrap_lines

<a id="function-rewrap_lines"></a>

```rust
fn rewrap_lines(
    lines: Vec<Line>,
    cols: usize,
    cursor_abs: usize,
    cursor_col: usize,
    pending: bool,
    saved_pos: Option<(usize, usize)>,
) -> (Vec<Line>, usize, usize, bool, Option<(usize, usize)>)
```

The `rewrap_lines` function rewraps joined lines to `cols`.
A wrapped line continues the same run. A hard break ends the run. The cursor and a saved cursor are mapped into the new lines. An empty result is one blank line.

### col_in_content

<a id="function-col_in_content"></a>

```rust
fn col_in_content(line: &Line, col: usize, pending: bool) -> usize
```

The `col_in_content` function maps a cursor column into the content width.
A pending wrap returns the whole content width. Any other column is clamped to that width.

### rewrap_cells

<a id="function-rewrap_cells"></a>

```rust
fn rewrap_cells(cells: &[Cell], cols: usize) -> Vec<Line>
```

The `rewrap_cells` function wraps `cells` into lines of `cols` columns.
An empty input is one blank line. A wide cell that does not fit starts a new wrapped line. A width of 2 adds a spacer. A line that fills the width is wrapped. The last line is not wrapped.

### finish_line

<a id="function-finish_line"></a>

```rust
fn finish_line(mut cells: Vec<Cell>, cols: usize, wrapped: bool, used: usize) -> Line
```

The `finish_line` function pads or clips `cells` into one [`Line`](grid/struct.Line.md).
`wrapped` and `used` are stored as given.

### locate_in_piece

<a id="function-locate_in_piece"></a>

```rust
fn locate_in_piece(piece: &[Line], target: usize) -> (usize, usize, bool)
```

The `locate_in_piece` function finds `target` inside a rewrapped run.
The result is a row, a column, and whether wrap is pending. An empty piece returns row 0, column 0, and no pending wrap. A target past the end lands on the last line.

### seat_window

<a id="function-seat_window"></a>

```rust
fn seat_window(
    mut lines: Vec<Line>,
    mut cursor_abs: usize,
    cursor_col: usize,
    mut pending: bool,
    rows: usize,
    on_tail: bool,
    cols: usize,
    scroll_cap: usize,
    mut saved_hit: Option<(usize, usize)>,
) -> (VecDeque<Line>, Vec<Line>, usize, usize, bool, Option<(usize, usize)>)
```

The `seat_window` function chooses the visible window after a reflow.
A short transcript grows blank lines. A cursor that was on the last row grows those lines above it. Lines above the window become scrollback and the cap drops the oldest. A cursor outside the window is placed on row 0 and wrap is cleared. A saved cursor outside the window is placed on row 0.

### flatten

<a id="function-flatten"></a>

```rust
fn flatten(params: &Params) -> Vec<u16>
```

The `flatten` function copies CSI parameters into one list.
An empty group becomes 0. An empty parameter list stays empty.

### param_at

<a id="function-param_at"></a>

```rust
fn param_at(params: &Params, index: usize, default: usize) -> usize
```

The `param_at` function returns one CSI parameter.
A missing group returns `default`. An empty group returns 0.

### motion

<a id="function-motion"></a>

```rust
fn motion(params: &Params) -> usize
```

The `motion` function returns a cursor distance.
A missing parameter becomes 1. A parameter of 0 becomes 1.

### read_color

<a id="function-read_color"></a>

```rust
fn read_color(flat: &[u16]) -> Option<(Color, usize)>
```

The `read_color` function reads an SGR 38 or 48 color.
A selector of 5 returns an indexed color and a used count of 3. A selector of 2 returns an RGB color and a used count of 5. Any other shape returns `None`.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
