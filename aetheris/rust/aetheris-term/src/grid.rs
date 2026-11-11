// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The module owns one terminal grid.
//!
//! The `feed` method advances the `vte` parser. The parser writes cells this grid owns. The caller serializes `feed` and paint. The honored sequences are library serial section 8 and the VT100 plan. A C0 bell sets the bell flag. Backspace, tab, line feed, vertical tab, form feed, and carriage return move the cursor. CSI cursor motion, erase, delete-line, and SGR are honored. CSI `c` answers with the device-attributes bytes. CSI `n` with parameter 6 answers with the cursor position. Private mode 1 is the cursor-key flag. Private mode 2004 is the paste flag. Private modes 47 and 1047 show the alternate screen without clearing it. Private mode 1049 shows the alternate screen, clears it, and saves the cursor.
//!
//! Origin mode is consumed and changes nothing. Insert mode is consumed and changes nothing. OSC is consumed and changes nothing. DCS is consumed and changes nothing. Sixel is consumed and changes nothing. A charset switch is consumed and changes nothing.
//!
//! @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
//! @author clayl (2026-10-23)

use std::collections::VecDeque;
use std::ops::{Deref, DerefMut, Index, IndexMut};

use unicode_width::UnicodeWidthChar;
use vte::{Params, Perform};

/// The `DEFAULT_COLS` constant is the visible column count of a fresh grid.
/// The value is 80.
pub const DEFAULT_COLS: usize = 80;
/// The `DEFAULT_ROWS` constant is the visible row count of a fresh grid.
/// The value is 24.
pub const DEFAULT_ROWS: usize = 24;
/// The `SCROLLBACK_CAP` constant is the number of lines kept above the visible screen.
/// The value is 10000. A line feed past that count drops the oldest line.
pub const SCROLLBACK_CAP: usize = 10_000;
/// The `COMBINING_CAP` constant is the extra combining marks one cell will keep.
/// The value is 8. The base character is not part of that count.
const COMBINING_CAP: usize = 8;

/// @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/// The [`Color`] enum is a color a cell stores.
/// `Default` is the unset color. `Index` is one palette index. `Rgb` is a red byte, a green byte, and a blue byte.
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub enum Color {
    Default,
    Index(u8),
    Rgb(u8, u8, u8),
}

/// @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/// The [`Cell`] struct is one screen cell.
/// The text is owned. A wide glyph is followed by a spacer cell. `width` is the column count. A width of 0 is that spacer. `fg` is the foreground. `bg` is the background. `bold` and `reverse` are the SGR flags.
#[derive(Clone, Debug, PartialEq, Eq)]
pub struct Cell {
    text: String,
    width: u8,
    fg: Color,
    bg: Color,
    bold: bool,
    reverse: bool,
}

impl Cell {
    /// The `blank` method returns an empty cell.
    /// The width is 1. The colors are [`Color::Default`]. Bold and reverse are off.
    fn blank() -> Self {
        Self {
            text: String::new(),
            width: 1,
            fg: Color::Default,
            bg: Color::Default,
            bold: false,
            reverse: false,
        }
    }

    /// The `text` method returns the characters this cell owns.
    /// Combining marks are included.
    pub fn text(&self) -> &str {
        &self.text
    }

    /// The `width` method returns the column width.
    /// Zero is a spacer after a wide glyph.
    pub fn width(&self) -> u8 {
        self.width
    }

    /// The `fg` method returns the foreground color.
    /// The value is the color stored on the cell.
    pub fn fg(&self) -> Color {
        self.fg
    }

    /// The `bg` method returns the background color.
    /// The value is the color stored on the cell.
    pub fn bg(&self) -> Color {
        self.bg
    }

    /// The `bold` method reports the bold flag.
    /// True means SGR bold is on for this cell.
    pub fn bold(&self) -> bool {
        self.bold
    }

    /// The `reverse` method reports the reverse flag.
    /// True means SGR reverse is on for this cell.
    pub fn reverse(&self) -> bool {
        self.reverse
    }
}

/// @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/// The `Attrs` struct is the current SGR state of one screen.
/// `fg` is the foreground. `bg` is the background. `bold` and `reverse` are the flags a new glyph copies.
#[derive(Clone, Debug)]
struct Attrs {
    fg: Color,
    bg: Color,
    bold: bool,
    reverse: bool,
}

impl Default for Attrs {
    /// The `default` method returns unset colors and both flags off.
    /// This is the state after SGR 0.
    fn default() -> Self {
        Self {
            fg: Color::Default,
            bg: Color::Default,
            bold: false,
            reverse: false,
        }
    }
}

/// @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/// The `Saved` struct is a cursor saved by DECSC or CSI `s`.
/// `row` and `col` are 0-based. `attrs` is the SGR state at the save.
#[derive(Clone, Debug)]
struct Saved {
    row: usize,
    col: usize,
    attrs: Attrs,
}

// @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/// The `Line` struct is one screen row.
/// `cells` are the columns. `wrapped` means the next row continues this one. `used` is how many columns of this row belong to the text.
#[derive(Clone, Debug)]
struct Line {
    cells: Vec<Cell>,
    /// The `used` field is the columns of this row that belong to the text.
    /// Pads after that stay off a join.
    used: usize,
    wrapped: bool,
}

impl Deref for Line {
    /// The target is the vector of [`Cell`] values in this row.
    type Target = Vec<Cell>;

    /// The `deref` method returns the cells of this row.
    /// The caller can index the line as a vector.
    fn deref(&self) -> &Vec<Cell> {
        &self.cells
    }
}

impl DerefMut for Line {
    /// The `deref_mut` method returns the cells for mutation.
    /// A write changes the row in place.
    fn deref_mut(&mut self) -> &mut Vec<Cell> {
        &mut self.cells
    }
}

impl Index<usize> for Line {
    /// The output is the [`Cell`] at one column of this row.
    type Output = Cell;

    /// The `index` method returns the cell at `index`.
    /// The index is a column in this row.
    fn index(&self, index: usize) -> &Cell {
        &self.cells[index]
    }
}

impl IndexMut<usize> for Line {
    /// The `index_mut` method returns the cell at `index` for mutation.
    /// The index is a column in this row.
    fn index_mut(&mut self, index: usize) -> &mut Cell {
        &mut self.cells[index]
    }
}

/// @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/// The `Screen` struct is one buffer, primary or alternate.
/// `scrollback` holds lines above the visible rows. `visible` is the screen. `row` and `col` are the cursor. `wrap_pending` means the next glyph wraps. `attrs` is the current SGR state. `saved` is the last saved cursor.
#[derive(Clone, Debug)]
struct Screen {
    scrollback: VecDeque<Line>,
    visible: Vec<Line>,
    row: usize,
    col: usize,
    wrap_pending: bool,
    attrs: Attrs,
    saved: Option<Saved>,
}

impl Screen {
    /// The `new` method returns a blank screen of `cols` by `rows`.
    /// The cursor is at 0, 0. No cursor is saved. Wrap is clear.
    fn new(cols: usize, rows: usize) -> Self {
        Self {
            scrollback: VecDeque::new(),
            visible: vec![blank_line(cols); rows],
            row: 0,
            col: 0,
            wrap_pending: false,
            attrs: Attrs::default(),
            saved: None,
        }
    }
}

// @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/// The [`Grid`] struct is the terminal grid.
/// The caller serializes `feed` and paint. `cols` and `rows` are the visible size. `scroll_cap` is the scrollback limit. `primary` and `alternate` are the two screens. `alt` selects the alternate screen. `parser` is the `vte` parser. `reply` holds bytes for the caller to write. `bell`, `dirty`, `paste`, and `decckm` are the flags.
pub struct Grid {
    cols: usize,
    rows: usize,
    scroll_cap: usize,
    primary: Screen,
    alternate: Screen,
    alt: bool,
    parser: vte::Parser,
    reply: Vec<u8>,
    bell: bool,
    dirty: bool,
    paste: bool,
    decckm: bool,
}

impl Grid {
    /// The `new` method returns a grid with the default scrollback cap.
    /// The cap is [`SCROLLBACK_CAP`]. A column count of 0 becomes 1. A row count of 0 becomes 1.
    pub fn new(cols: usize, rows: usize) -> Self {
        Self::with_scrollback(cols, rows, SCROLLBACK_CAP)
    }

    /// The `with_scrollback` method returns a grid whose ring keeps at most `scroll_cap` lines.
    /// A column count of 0 becomes 1. A row count of 0 becomes 1. The primary screen is showing. The reply is empty. Bell, dirty, paste, and cursor-key mode are off.
    pub fn with_scrollback(cols: usize, rows: usize, scroll_cap: usize) -> Self {
        let cols = cols.max(1);
        let rows = rows.max(1);
        Self {
            cols,
            rows,
            scroll_cap,
            primary: Screen::new(cols, rows),
            alternate: Screen::new(cols, rows),
            alt: false,
            parser: vte::Parser::new(),
            reply: Vec::new(),
            bell: false,
            dirty: false,
            paste: false,
            decckm: false,
        }
    }

    /// The `feed` method advances the parser.
    /// The byte slice is borrowed for this call only. The parser is moved out, advanced, and moved back. The cells stay owned by the grid.
    pub fn feed(&mut self, bytes: &[u8]) {
        let mut parser = std::mem::replace(&mut self.parser, vte::Parser::new());
        parser.advance(self, bytes);
        self.parser = parser;
    }

    /// The `resize` method changes the visible size.
    /// A zero column count returns and keeps the last positive size. A zero row count does the same. A primary screen reflows a soft wrap when the column count changes. The alternate screen pads or clips.
    ///
    /// A cursor on the last row stays on the last row. A taller screen fills above that row. A shorter screen keeps that row and moves the lines above it into the scrollback. Any other cursor keeps that row. The grid is marked dirty.
    pub fn resize(&mut self, cols: usize, rows: usize) {
        if cols == 0 || rows == 0 {
            return;
        }
        self.cols = cols;
        self.rows = rows;
        let cap = self.scroll_cap;
        resize_screen(&mut self.primary, cols, rows, cap, false);
        resize_screen(&mut self.alternate, cols, rows, cap, true);
        self.dirty = true;
    }

    /// The `cols` method returns the visible column count.
    /// The value is the count stored after the last accepted resize.
    pub fn cols(&self) -> usize {
        self.cols
    }

    /// The `rows` method returns the visible row count.
    /// The value is the count stored after the last accepted resize.
    pub fn rows(&self) -> usize {
        self.rows
    }

    /// The `cursor` method returns the cursor cell.
    /// The pair is clamped into the visible screen. Row and column are 0-based.
    pub fn cursor(&self) -> (usize, usize) {
        let screen = self.active();
        let row = screen.row.min(self.rows.saturating_sub(1));
        let col = screen.col.min(self.cols.saturating_sub(1));
        (row, col)
    }

    /// The `bell` method reports the bell flag.
    /// True means a C0 bell has arrived since the last take.
    pub fn bell(&self) -> bool {
        self.bell
    }

    /// The `take_bell` method returns the bell flag and clears it.
    /// A second call returns false until the next bell.
    pub fn take_bell(&mut self) -> bool {
        let bell = self.bell;
        self.bell = false;
        bell
    }

    /// The `paste_mode` method reports private mode 2004.
    /// True means bracketed paste is on.
    /// @aa-sdd PK-6945 docs/specs/rust/aetheris/aetheris-term/src/grid.spec.md
    pub fn paste_mode(&self) -> bool {
        self.paste
    }

    /// The `decckm` method reports private mode 1.
    /// True means cursor-key mode is on.
    pub fn decckm(&self) -> bool {
        self.decckm
    }

    /// The `dirty` method reports that the grid changed.
    /// True means paint has not cleared the flag.
    pub fn dirty(&self) -> bool {
        self.dirty
    }

    /// The `clear_dirty` method clears the dirty flag.
    /// The cells stay as they are.
    pub fn clear_dirty(&mut self) {
        self.dirty = false;
    }

    /// The `clear_buffer` method clears the active screen and its scrollback.
    /// The cursor goes home. Wrap is cleared. The saved cursor is dropped. SGR returns to the default. Paste mode stays. The keyboard mode stays. The other screen stays. The grid is marked dirty.
    pub fn clear_buffer(&mut self) {
        let cols = self.cols;
        let rows = self.rows;
        let screen = self.active_mut();
        screen.scrollback.clear();
        screen.visible = vec![blank_line(cols); rows];
        screen.row = 0;
        screen.col = 0;
        screen.wrap_pending = false;
        screen.saved = None;
        screen.attrs = Attrs::default();
        self.dirty = true;
    }

    /// The `alternate_screen` method reports the alternate screen.
    /// True means that screen is showing.
    pub fn alternate_screen(&self) -> bool {
        self.alt
    }

    /// The `clear_scrollback_keep_line` method clears history and keeps the cursor line.
    /// The alternate screen is left alone and the method returns false. On the primary screen the cursor line moves to the top. The column stays. Wrap is cleared. The grid is marked dirty. The method returns true.
    pub fn clear_scrollback_keep_line(&mut self) -> bool {
        if self.alt {
            return false;
        }
        let cols = self.cols;
        let rows = self.rows;
        let screen = &mut self.primary;
        let row = screen.row.min(rows.saturating_sub(1));
        let col = screen.col.min(cols.saturating_sub(1));
        let kept = screen
            .visible
            .get(row)
            .cloned()
            .unwrap_or_else(|| blank_line(cols));
        screen.scrollback.clear();
        screen.visible = vec![blank_line(cols); rows];
        if let Some(line) = screen.visible.first_mut() {
            *line = kept;
            line.wrapped = false;
        }
        screen.row = 0;
        screen.col = col;
        screen.wrap_pending = false;
        self.dirty = true;
        true
    }

    /// The `take_reply` method returns bytes the grid wants the caller to write.
    /// Device attributes and a cursor-position report use this buffer. The buffer is empty afterward.
    pub fn take_reply(&mut self) -> Vec<u8> {
        std::mem::take(&mut self.reply)
    }

    /// The `scrollback_len` method returns the scrollback length of the active screen.
    /// The count does not include the visible rows.
    pub fn scrollback_len(&self) -> usize {
        self.active().scrollback.len()
    }

    /// The `scrollback_text` method returns one scrollback line as text.
    /// Index 0 is the oldest line. A missing index returns an empty string.
    pub fn scrollback_text(&self, index: usize) -> String {
        self.active()
            .scrollback
            .get(index)
            .map(|line| line_text(line))
            .unwrap_or_default()
    }

    /// The `visible_text` method returns one visible row as text.
    /// Row 0 is the top of the screen. A missing row returns an empty string.
    pub fn visible_text(&self, row: usize) -> String {
        self.active()
            .visible
            .get(row)
            .map(|line| line_text(line))
            .unwrap_or_default()
    }

    /// The `visible_cell` method returns one visible cell.
    /// A row or column outside the screen returns `None`.
    pub fn visible_cell(&self, row: usize, col: usize) -> Option<&Cell> {
        self.active().visible.get(row).and_then(|line| line.get(col))
    }

    /// The `history_len` method returns the lines paint can walk.
    /// The count is the scrollback length plus the visible row count. Index 0 is the oldest scrollback line.
    pub fn history_len(&self) -> usize {
        self.active().scrollback.len() + self.rows
    }

    /// The `history_line` method returns one row of the ring plus the screen.
    /// Index 0 is the oldest line. An index past the last visible row returns `None`.
    pub fn history_line(&self, index: usize) -> Option<&[Cell]> {
        let screen = self.active();
        let back = screen.scrollback.len();
        if index < back {
            screen.scrollback.get(index).map(|line| line.as_slice())
        } else {
            screen.visible.get(index - back).map(|line| line.as_slice())
        }
    }

    /// The `active` method returns the screen that is showing.
    /// The alternate screen is returned when `alt` is set. Otherwise the primary screen is returned.
    fn active(&self) -> &Screen {
        if self.alt {
            &self.alternate
        } else {
            &self.primary
        }
    }

    /// The `active_mut` method returns the showing screen for mutation.
    /// The choice is the same as `active`.
    fn active_mut(&mut self) -> &mut Screen {
        if self.alt {
            &mut self.alternate
        } else {
            &mut self.primary
        }
    }

    /// The `mark` method sets the dirty flag.
    /// The cells are not changed here.
    fn mark(&mut self) {
        self.dirty = true;
    }

    /// The `carriage_return` method moves the cursor to column 0.
    /// Wrap pending is cleared.
    fn carriage_return(&mut self) {
        let screen = self.active_mut();
        screen.col = 0;
        screen.wrap_pending = false;
    }

    /// The `line_feed` method moves the cursor down one row.
    /// A cursor that is not on the last row only changes the row. A cursor on the last row scrolls the visible lines up. On the primary screen the top line enters the scrollback. A scrollback past the cap drops the oldest line. The alternate screen discards that top line. Wrap pending is cleared.
    fn line_feed(&mut self) {
        let rows = self.rows;
        let cols = self.cols;
        let cap = self.scroll_cap;
        let alt = self.alt;
        let screen = self.active_mut();
        screen.wrap_pending = false;
        if screen.row + 1 < rows {
            screen.row += 1;
            return;
        }
        let top = std::mem::replace(&mut screen.visible[0], blank_line(cols));
        screen.visible.remove(0);
        screen.visible.push(blank_line(cols));
        if !alt {
            screen.scrollback.push_back(top);
            while screen.scrollback.len() > cap {
                screen.scrollback.pop_front();
            }
        }
    }

    /// The `reverse_index` method moves the cursor up one row.
    /// A cursor on row 0 inserts a blank line at the top and drops the bottom line. Wrap pending is cleared. The scrollback is not used.
    fn reverse_index(&mut self) {
        let cols = self.cols;
        let screen = self.active_mut();
        screen.wrap_pending = false;
        if screen.row > 0 {
            screen.row -= 1;
            return;
        }
        screen.visible.pop();
        screen.visible.insert(0, blank_line(cols));
    }

    /// The `backspace` method moves the cursor left one column.
    /// A pending wrap is cleared and the column stays. A column of 0 stays at 0.
    fn backspace(&mut self) {
        let screen = self.active_mut();
        if screen.wrap_pending {
            screen.wrap_pending = false;
            return;
        }
        if screen.col > 0 {
            screen.col -= 1;
        }
    }

    /// The `move_col` method adds `delta` to the column.
    /// The column is clamped to the visible screen. Wrap pending is cleared.
    fn move_col(&mut self, delta: isize) {
        let cols = self.cols;
        let screen = self.active_mut();
        screen.wrap_pending = false;
        let next = screen.col as isize + delta;
        let max = cols.saturating_sub(1) as isize;
        screen.col = next.clamp(0, max) as usize;
    }

    /// The `move_row` method adds `delta` to the row.
    /// The row is clamped to the visible screen. Wrap pending is cleared. The screen does not scroll.
    fn move_row(&mut self, delta: isize) {
        let rows = self.rows;
        let screen = self.active_mut();
        screen.wrap_pending = false;
        let next = screen.row as isize + delta;
        let max = rows.saturating_sub(1) as isize;
        screen.row = next.clamp(0, max) as usize;
    }

    /// The `place` method sets the cursor.
    /// The row and the column are clamped into the visible screen. Wrap pending is cleared.
    fn place(&mut self, row: usize, col: usize) {
        let rows = self.rows;
        let cols = self.cols;
        let screen = self.active_mut();
        screen.wrap_pending = false;
        screen.row = row.min(rows.saturating_sub(1));
        screen.col = col.min(cols.saturating_sub(1));
    }

    /// The `print_char` method writes one character.
    /// A width of 0 attaches the character to the previous cell. A pending wrap, or a glyph that does not fit, marks the row as wrapped and feeds a line. The glyph is then stored. The grid is marked dirty.
    fn print_char(&mut self, ch: char) {
        let width = UnicodeWidthChar::width(ch).unwrap_or(0);
        if width == 0 {
            self.attach(ch);
            return;
        }
        let cols = self.cols;
        if self.active().wrap_pending || self.active().col + width > cols {
            self.note_wrap();
            self.carriage_return();
            self.line_feed();
            self.mark();
        }
        self.put_glyph(ch, width as u8);
        self.mark();
    }

    /// The `note_wrap` method marks the row being left as continued on the next row.
    /// A cursor already at column 0 with no pending wrap returns. `used` is the column count, or the full width when wrap is already pending.
    fn note_wrap(&mut self) {
        let cols = self.cols;
        let screen = self.active_mut();
        if screen.col == 0 && !screen.wrap_pending {
            return;
        }
        let used = if screen.wrap_pending { cols } else { screen.col };
        let row = screen.row;
        if let Some(line) = screen.visible.get_mut(row) {
            line.wrapped = true;
            line.used = used;
        }
    }

    /// The `attach` method adds a zero-width character to the previous cell.
    /// A cursor at column 0 returns. A spacer cell attaches to the cell before it. The grid is marked dirty when a cell is changed.
    fn attach(&mut self, ch: char) {
        let cols = self.cols;
        let screen = self.active_mut();
        let mut col = screen.col;
        if screen.wrap_pending {
            col = cols;
        }
        if col == 0 {
            return;
        }
        let target = col - 1;
        let row = screen.row;
        let Some(line) = screen.visible.get_mut(row) else {
            return;
        };
        let Some(cell) = line.get_mut(target) else {
            return;
        };
        if cell.width == 0 && target > 0 {
            if let Some(base) = line.get_mut(target - 1) {
                push_mark(base, ch);
            }
            return;
        }
        push_mark(cell, ch);
        self.dirty = true;
    }

    /// The `put_glyph` method stores `ch` at the cursor.
    /// The cell copies the current SGR state. A width of 2 writes an empty spacer in the next column. A glyph that ends on the last column sets wrap pending. Any other glyph advances the column by `width`.
    fn put_glyph(&mut self, ch: char, width: u8) {
        let cols = self.cols;
        let screen = self.active_mut();
        let row = screen.row;
        let col = screen.col;
        let attrs = screen.attrs.clone();
        if let Some(line) = screen.visible.get_mut(row) {
            if col < line.len() {
                line[col] = Cell {
                    text: ch.to_string(),
                    width,
                    fg: attrs.fg,
                    bg: attrs.bg,
                    bold: attrs.bold,
                    reverse: attrs.reverse,
                };
            }
            if width == 2 && col + 1 < line.len() {
                line[col + 1] = Cell {
                    text: String::new(),
                    width: 0,
                    fg: attrs.fg,
                    bg: attrs.bg,
                    bold: false,
                    reverse: false,
                };
            }
        }
        let step = width as usize;
        if col + step >= cols {
            screen.col = cols.saturating_sub(1);
            screen.wrap_pending = true;
        } else {
            screen.col = col + step;
            screen.wrap_pending = false;
        }
    }

    /// The `delete_lines` method deletes `n` lines at the cursor.
    /// A count of 0 returns. Lines below shift up. Blank lines fill the bottom of the screen. The cursor stays. Deleted lines stay out of the scrollback. The screen is the region. The row above the deletion loses its wrap flag.
    fn delete_lines(&mut self, n: usize) {
        if n == 0 {
            return;
        }
        let cols = self.cols;
        let rows = self.rows;
        let screen = self.active_mut();
        screen.wrap_pending = false;
        let row = screen.row.min(rows.saturating_sub(1));
        let count = n.min(rows.saturating_sub(row));
        for _ in 0..count {
            if row < screen.visible.len() {
                screen.visible.remove(row);
            }
        }
        while screen.visible.len() < rows {
            screen.visible.push(blank_line(cols));
        }
        if screen.visible.len() > rows {
            screen.visible.truncate(rows);
        }
        if count > 0 && row > 0 {
            if let Some(prev) = screen.visible.get_mut(row - 1) {
                prev.wrapped = false;
            }
        }
    }

    /// The `erase_line` method clears cells on the cursor row.
    /// Mode 1 clears from the start through the cursor. Mode 2 clears the whole row. Any other mode clears from the cursor to the end. Mode 1 keeps the wrap flag. The other modes clear it. Blank cells copy the current SGR colors.
    fn erase_line(&mut self, mode: usize) {
        let cols = self.cols;
        let screen = self.active_mut();
        let row = screen.row;
        let col = screen.col.min(cols.saturating_sub(1));
        let attrs = screen.attrs.clone();
        let Some(line) = screen.visible.get_mut(row) else {
            return;
        };
        if mode != 1 {
            line.wrapped = false;
        }
        match mode {
            1 => {
                for cell in line.iter_mut().take(col + 1) {
                    *cell = blank_with(&attrs);
                }
            }
            2 => {
                for cell in line.iter_mut() {
                    *cell = blank_with(&attrs);
                }
            }
            _ => {
                for cell in line.iter_mut().skip(col) {
                    *cell = blank_with(&attrs);
                }
            }
        }
    }

    /// The `erase_display` method clears rows.
    /// Mode 1 clears the cursor line through the cursor and every row above it. Mode 2 clears every visible row. Any other mode clears from the cursor through the end of the screen.
    fn erase_display(&mut self, mode: usize) {
        let rows = self.rows;
        let row = self.active().row;
        match mode {
            1 => {
                self.erase_line(1);
                for r in 0..row {
                    self.clear_row(r);
                }
            }
            2 => {
                for r in 0..rows {
                    self.clear_row(r);
                }
            }
            _ => {
                self.erase_line(0);
                for r in (row + 1)..rows {
                    self.clear_row(r);
                }
            }
        }
    }

    /// The `clear_row` method blanks one visible row.
    /// The wrap flag is cleared. Each cell copies the current SGR colors. A missing row returns.
    fn clear_row(&mut self, row: usize) {
        let screen = self.active_mut();
        let attrs = screen.attrs.clone();
        if let Some(line) = screen.visible.get_mut(row) {
            line.wrapped = false;
            for cell in line.iter_mut() {
                *cell = blank_with(&attrs);
            }
        }
    }

    /// The `save_cursor` method stores the cursor and the SGR state.
    /// A later save replaces the stored value.
    fn save_cursor(&mut self) {
        let screen = self.active_mut();
        screen.saved = Some(Saved {
            row: screen.row,
            col: screen.col,
            attrs: screen.attrs.clone(),
        });
    }

    /// The `restore_cursor` method puts the saved cursor back.
    /// The row and the column are clamped. Wrap pending is cleared. A screen with no saved cursor is left as it is.
    fn restore_cursor(&mut self) {
        let rows = self.rows;
        let cols = self.cols;
        let screen = self.active_mut();
        if let Some(saved) = screen.saved.clone() {
            screen.row = saved.row.min(rows.saturating_sub(1));
            screen.col = saved.col.min(cols.saturating_sub(1));
            screen.attrs = saved.attrs;
            screen.wrap_pending = false;
        }
    }

    /// The `enter_alt` method shows the alternate screen.
    /// `clear` replaces that screen with a blank screen of the current size. The grid is marked dirty.
    fn enter_alt(&mut self, clear: bool) {
        if clear {
            self.alternate = Screen::new(self.cols, self.rows);
        }
        self.alt = true;
        self.mark();
    }

    /// The `leave_alt` method shows the primary screen.
    /// The alternate screen is kept. The grid is marked dirty.
    fn leave_alt(&mut self) {
        self.alt = false;
        self.mark();
    }

    /// The `reset_attrs` method restores the default SGR state.
    /// Colors are unset. Bold and reverse are off.
    fn reset_attrs(&mut self) {
        self.active_mut().attrs = Attrs::default();
    }

    /// The `sgr` method applies a CSI `m` sequence.
    /// An empty parameter list resets SGR. Code 0 resets. Code 1 sets bold. Code 22 clears bold. Code 7 sets reverse. Code 27 clears reverse. Codes 30 through 37 and 90 through 97 set an indexed foreground. Codes 40 through 47 and 100 through 107 set an indexed background. Code 39 restores the default foreground. Code 49 restores the default background. Codes 38 and 48 read an indexed or RGB color. Any other code is ignored.
    fn sgr(&mut self, params: &Params) {
        let flat = flatten(params);
        if flat.is_empty() {
            self.reset_attrs();
            return;
        }
        let mut i = 0;
        while i < flat.len() {
            let code = flat[i];
            match code {
                0 => self.reset_attrs(),
                1 => self.active_mut().attrs.bold = true,
                22 => self.active_mut().attrs.bold = false,
                7 => self.active_mut().attrs.reverse = true,
                27 => self.active_mut().attrs.reverse = false,
                39 => self.active_mut().attrs.fg = Color::Default,
                49 => self.active_mut().attrs.bg = Color::Default,
                30..=37 => {
                    self.active_mut().attrs.fg = Color::Index((code - 30) as u8);
                }
                40..=47 => {
                    self.active_mut().attrs.bg = Color::Index((code - 40) as u8);
                }
                90..=97 => {
                    self.active_mut().attrs.fg = Color::Index((code - 90 + 8) as u8);
                }
                100..=107 => {
                    self.active_mut().attrs.bg = Color::Index((code - 100 + 8) as u8);
                }
                38 | 48 => {
                    if let Some((color, used)) = read_color(&flat[i..]) {
                        if code == 38 {
                            self.active_mut().attrs.fg = color;
                        } else {
                            self.active_mut().attrs.bg = color;
                        }
                        i += used;
                        continue;
                    }
                }
                _ => {}
            }
            i += 1;
        }
    }

    /// The `set_private` method applies one private mode.
    /// Mode 1 sets the cursor-key flag. Mode 2004 sets the paste flag. Modes 47 and 1047 enter or leave the alternate screen without clearing it. Mode 1049 saves the cursor, enters a cleared alternate screen, and restores the cursor on the way out. Any other mode is ignored.
    fn set_private(&mut self, mode: u16, on: bool) {
        match mode {
            1 => self.decckm = on,
            2004 => self.paste = on,
            47 | 1047 => {
                if on {
                    self.enter_alt(false);
                } else {
                    self.leave_alt();
                }
            }
            1049 => {
                if on {
                    self.save_cursor();
                    self.enter_alt(true);
                } else {
                    self.leave_alt();
                    self.restore_cursor();
                }
            }
            _ => {}
        }
    }
}

impl Perform for Grid {
    /// The `print` method is the `vte` print callback.
    /// The character is passed to `print_char`.
    fn print(&mut self, ch: char) {
        self.print_char(ch);
    }

    /// The `execute` method handles one C0 byte.
    /// `0x07` sets the bell. `0x08` backspaces. `0x09` moves to the next tab stop of 8 columns. `0x0A`, `0x0B`, and `0x0C` feed a line. `0x0D` returns the carriage. Any other byte is ignored. A handled byte marks the grid dirty.
    fn execute(&mut self, byte: u8) {
        match byte {
            0x07 => {
                self.bell = true;
                self.mark();
            }
            0x08 => {
                self.backspace();
                self.mark();
            }
            0x09 => {
                let col = self.active().col;
                let stop = ((col / 8) + 1) * 8;
                self.place(self.active().row, stop.min(self.cols.saturating_sub(1)));
                self.mark();
            }
            0x0A | 0x0B | 0x0C => {
                self.line_feed();
                self.mark();
            }
            0x0D => {
                self.carriage_return();
                self.mark();
            }
            _ => {}
        }
    }

    /// The `hook` method consumes a DCS start.
    /// The bytes change nothing.
    fn hook(&mut self, _params: &Params, _intermediates: &[u8], _ignore: bool, _action: char) {}

    /// The `put` method consumes one DCS byte.
    /// The byte changes nothing.
    fn put(&mut self, _byte: u8) {}

    /// The `unhook` method consumes a DCS end.
    /// The grid is unchanged.
    fn unhook(&mut self) {}

    /// The `osc_dispatch` method consumes an OSC string.
    /// The string changes nothing.
    fn osc_dispatch(&mut self, _params: &[&[u8]], _bell_terminated: bool) {}

    /// The `csi_dispatch` method honors one CSI sequence.
    /// `A` and `B` move the row. `C` and `D` move the column. `H` and `f` place the cursor from 1-based parameters. `J` erases the display. `K` erases the line. `M` deletes lines. `m` applies SGR. `c` appends the bytes ESC `[?1;0c` to the reply. `n` with parameter 6 appends a cursor-position report. `h` and `l` with a question mark set private modes. `s` and `u` without a question mark save and restore the cursor. Any other action is ignored. The grid is marked dirty.
    fn csi_dispatch(&mut self, params: &Params, intermediates: &[u8], _ignore: bool, action: char) {
        let private = intermediates.contains(&b'?');
        match action {
            'A' => self.move_row(-(motion(params) as isize)),
            'B' => self.move_row(motion(params) as isize),
            'C' => self.move_col(motion(params) as isize),
            'D' => self.move_col(-(motion(params) as isize)),
            'H' | 'f' => {
                let row = param_at(params, 0, 1).saturating_sub(1);
                let col = param_at(params, 1, 1).saturating_sub(1);
                self.place(row, col);
            }
            'J' => self.erase_display(param_at(params, 0, 0)),
            'K' => self.erase_line(param_at(params, 0, 0)),
            'M' => self.delete_lines(motion(params)),
            'm' => self.sgr(params),
            'c' => {
                self.reply.extend_from_slice(b"\x1b[?1;0c");
            }
            'n' => {
                if param_at(params, 0, 0) == 6 {
                    let (row, col) = self.cursor();
                    let report = format!("\x1b[{};{}R", row + 1, col + 1);
                    self.reply.extend_from_slice(report.as_bytes());
                }
            }
            'h' | 'l' => {
                let on = action == 'h';
                if private {
                    for group in params.iter() {
                        let mode = group.first().copied().unwrap_or(0);
                        self.set_private(mode, on);
                    }
                }
            }
            's' => {
                if !private {
                    self.save_cursor();
                }
            }
            'u' => {
                if !private {
                    self.restore_cursor();
                }
            }
            _ => {}
        }
        self.mark();
    }

    /// The `esc_dispatch` method honors one ESC sequence that is not CSI.
    /// A sequence with intermediates returns. `7` saves the cursor. `8` restores it. `D` feeds a line. `M` reverse-indexes. `E` returns the carriage and feeds a line. Any other byte is ignored. The grid is marked dirty.
    fn esc_dispatch(&mut self, intermediates: &[u8], _ignore: bool, byte: u8) {
        if !intermediates.is_empty() {
            return;
        }
        match byte {
            b'7' => self.save_cursor(),
            b'8' => self.restore_cursor(),
            b'D' => self.line_feed(),
            b'M' => self.reverse_index(),
            b'E' => {
                self.carriage_return();
                self.line_feed();
            }
            _ => {}
        }
        self.mark();
    }
}

/// The `blank_line` function returns a row of `cols` blank cells.
/// `used` is 0. The row is not wrapped.
fn blank_line(cols: usize) -> Line {
    Line {
        cells: vec![Cell::blank(); cols],
        used: 0,
        wrapped: false,
    }
}

/// The `blank_with` function returns an empty cell that copies `attrs`.
/// The width is 1. The text is empty.
fn blank_with(attrs: &Attrs) -> Cell {
    Cell {
        text: String::new(),
        width: 1,
        fg: attrs.fg,
        bg: attrs.bg,
        bold: attrs.bold,
        reverse: attrs.reverse,
    }
}

/// The `push_mark` function appends a combining mark.
/// Marks already on the cell are counted after the first character. A cell that already has [`COMBINING_CAP`] extra marks ignores `ch`.
fn push_mark(cell: &mut Cell, ch: char) {
    let extra = cell.text.chars().count().saturating_sub(1);
    if extra < COMBINING_CAP {
        cell.text.push(ch);
    }
}

/// The `line_text` function joins a row into text.
/// A spacer cell is skipped. An empty cell becomes a space. Trailing spaces are removed.
fn line_text(line: &[Cell]) -> String {
    let mut out = String::new();
    for cell in line {
        if cell.width == 0 {
            continue;
        }
        if cell.text.is_empty() {
            out.push(' ');
        } else {
            out.push_str(&cell.text);
        }
    }
    out.trim_end().to_string()
}

/// The `resize_screen` function resizes one screen.
/// A primary screen whose column count changed is reflowed. Every other screen pads or clips each line. A taller screen with the cursor on the last row fills above that row. A taller screen with any other cursor adds blank rows at the bottom. A shorter screen whose cursor would fall off keeps that row and moves the lines above it into the scrollback. A shorter screen with any other cursor drops the rows below the new bottom. The cursor and a saved cursor are clamped.
fn resize_screen(screen: &mut Screen, cols: usize, rows: usize, scroll_cap: usize, alt: bool) {
    let old_cols = screen
        .visible
        .first()
        .map(|line| line.cells.len())
        .unwrap_or(cols);
    if !alt && old_cols != cols {
        reflow_primary(screen, cols, rows, scroll_cap);
    } else {
        for line in screen.scrollback.iter_mut() {
            fit_line(line, cols);
        }
        for line in screen.visible.iter_mut() {
            fit_line(line, cols);
        }
    }
    let old = screen.visible.len();
    if old < rows {
        let extra = rows - old;
        let on_tail = old > 0 && screen.row + 1 >= old;
        if on_tail {
            let mut above = Vec::with_capacity(extra);
            for _ in 0..extra {
                let line = if alt {
                    None
                } else {
                    screen.scrollback.pop_back()
                };
                above.push(line.unwrap_or_else(|| blank_line(cols)));
            }
            above.reverse();
            above.append(&mut screen.visible);
            screen.visible = above;
            screen.row += extra;
            if let Some(saved) = screen.saved.as_mut() {
                saved.row += extra;
            }
        } else {
            for _ in 0..extra {
                screen.visible.push(blank_line(cols));
            }
        }
    } else if old > rows {
        let extra = old - rows;
        let cursor = screen.row.min(old.saturating_sub(1));
        if cursor >= rows {
            for _ in 0..extra {
                let line = screen.visible.remove(0);
                if !alt {
                    screen.scrollback.push_back(line);
                    while screen.scrollback.len() > scroll_cap {
                        screen.scrollback.pop_front();
                    }
                }
            }
            screen.row = cursor.saturating_sub(extra);
            if let Some(saved) = screen.saved.as_mut() {
                saved.row = saved.row.saturating_sub(extra);
            }
        } else {
            screen.visible.truncate(rows);
        }
    }
    let last = rows.saturating_sub(1);
    screen.row = screen.row.min(last);
    screen.col = screen.col.min(cols.saturating_sub(1));
    if let Some(saved) = screen.saved.as_mut() {
        saved.row = saved.row.min(last);
        saved.col = saved.col.min(cols.saturating_sub(1));
    }
}

/// The `fit_line` function pads or clips one line to `cols`.
/// Added cells are blank. `used` and `wrapped` stay as they were.
fn fit_line(line: &mut Line, cols: usize) {
    if line.cells.len() < cols {
        line.cells.resize(cols, Cell::blank());
    } else if line.cells.len() > cols {
        line.cells.truncate(cols);
    }
}

/// The `reflow_primary` function rewraps the primary screen to a new column count.
/// Scrollback and the visible rows are joined. Trailing pad rows after the cursor are dropped. Wrapped runs are rewrapped. The cursor and a saved cursor are placed again. A cursor that was on the last row stays on the last row of the new window.
/// @aa-sdd PK-6915 docs/specs/rust/aetheris/aetheris-term/src/grid.spec.md
fn reflow_primary(screen: &mut Screen, cols: usize, rows: usize, scroll_cap: usize) {
    let old_rows = screen.visible.len().max(1);
    let on_tail = screen.row + 1 >= screen.visible.len().max(1);
    let old_row = screen.row.min(old_rows - 1);
    let old_col = screen.col;
    let pending = screen.wrap_pending;
    let saved = screen.saved.clone();

    let mut lines = Vec::new();
    lines.extend(screen.scrollback.drain(..));
    let sb_len = lines.len();
    lines.append(&mut screen.visible);

    let cursor_abs = sb_len + old_row;
    let saved_pos = saved.as_ref().map(|mark| {
        (sb_len + mark.row.min(old_rows - 1), mark.col)
    });
    let keep = cursor_abs.max(saved_pos.map(|(abs, _)| abs).unwrap_or(cursor_abs));
    while lines.len() > keep + 1 {
        let pad = lines
            .last()
            .is_some_and(|line| !line.wrapped && is_pad(line));
        if pad {
            lines.pop();
        } else {
            break;
        }
    }

    let (lines, cursor_abs, cursor_col, pending, saved_hit) = rewrap_lines(
        lines,
        cols,
        cursor_abs,
        old_col,
        pending,
        saved_pos,
    );
    let (scroll, visible, row, col, pending, saved_seat) =
        seat_window(lines, cursor_abs, cursor_col, pending, rows, on_tail, cols, scroll_cap, saved_hit);
    screen.scrollback = scroll;
    screen.visible = visible;
    screen.row = row;
    screen.col = col;
    screen.wrap_pending = pending;
    if let (Some(mark), Some((srow, scol))) = (screen.saved.as_mut(), saved_seat) {
        mark.row = srow;
        mark.col = scol;
    }
}

/// The `is_pad` function reports a line whose cells have no text.
/// A spacer cell with empty text still counts as a pad.
fn is_pad(line: &Line) -> bool {
    line.cells.iter().all(|cell| cell.text.is_empty())
}

/// The `content_of` function returns the cells that belong to the text.
/// A wrapped line with `used` greater than 0 keeps that many columns. An unwrapped line drops trailing blank cells. A spacer cell is left out of the result.
fn content_of(line: &Line) -> Vec<Cell> {
    if line.wrapped && line.used > 0 {
        return take_columns(&line.cells, line.used);
    }
    let mut end = line.cells.len();
    if !line.wrapped {
        while end > 0 {
            let cell = &line.cells[end - 1];
            if cell.width == 0 {
                break;
            }
            if cell.text.is_empty()
                && !cell.bold
                && !cell.reverse
                && matches!(cell.fg, Color::Default)
                && matches!(cell.bg, Color::Default)
            {
                end -= 1;
                continue;
            }
            break;
        }
    }
    line.cells[..end]
        .iter()
        .filter(|cell| cell.width != 0)
        .cloned()
        .collect()
}

/// The `take_columns` function copies cells until `limit` columns are taken.
/// A spacer cell is skipped. A wide cell counts as its width.
fn take_columns(cells: &[Cell], limit: usize) -> Vec<Cell> {
    let mut out = Vec::new();
    let mut seen = 0usize;
    for cell in cells {
        if cell.width == 0 {
            continue;
        }
        if seen >= limit {
            break;
        }
        out.push(cell.clone());
        seen += cell.width.max(1) as usize;
    }
    out
}

/// The `content_width` function sums cell widths.
/// A width of 0 counts as 1.
fn content_width(cells: &[Cell]) -> usize {
    cells.iter().map(|cell| cell.width.max(1) as usize).sum()
}

/// The `rewrap_lines` function rewraps joined lines to `cols`.
/// A wrapped line continues the same run. A hard break ends the run. The cursor and a saved cursor are mapped into the new lines. An empty result is one blank line.
fn rewrap_lines(
    lines: Vec<Line>,
    cols: usize,
    cursor_abs: usize,
    cursor_col: usize,
    pending: bool,
    saved_pos: Option<(usize, usize)>,
) -> (Vec<Line>, usize, usize, bool, Option<(usize, usize)>) {
    let cols = cols.max(1);
    let mut out = Vec::new();
    let mut run: Vec<Cell> = Vec::new();
    let mut cursor_mark: Option<usize> = None;
    let mut saved_mark: Option<usize> = None;
    let mut cursor_hit: Option<(usize, usize, bool)> = None;
    let mut saved_hit: Option<(usize, usize)> = None;

    for (index, line) in lines.iter().enumerate() {
        if index == cursor_abs {
            cursor_mark = Some(content_width(&run) + col_in_content(line, cursor_col, pending));
        }
        if saved_pos.is_some_and(|(abs, _)| abs == index) {
            let scol = saved_pos.map(|(_, col)| col).unwrap_or(0);
            saved_mark = Some(content_width(&run) + col_in_content(line, scol, false));
        }
        run.extend(content_of(line));
        if !line.wrapped || index + 1 == lines.len() {
            let piece = rewrap_cells(&run, cols);
            if let Some(mark) = cursor_mark.take() {
                let (row, col, pend) = locate_in_piece(&piece, mark);
                cursor_hit = Some((out.len() + row, col, pend));
            }
            if let Some(mark) = saved_mark.take() {
                let (row, col, _) = locate_in_piece(&piece, mark);
                saved_hit = Some((out.len() + row, col));
            }
            out.extend(piece);
            run.clear();
        }
    }
    if out.is_empty() {
        out.push(blank_line(cols));
    }
    let (cursor_abs, cursor_col, pending) = cursor_hit.unwrap_or((0, 0, false));
    let cursor_abs = cursor_abs.min(out.len() - 1);
    (out, cursor_abs, cursor_col, pending, saved_hit)
}

/// The `col_in_content` function maps a cursor column into the content width.
/// A pending wrap returns the whole content width. Any other column is clamped to that width.
fn col_in_content(line: &Line, col: usize, pending: bool) -> usize {
    let width = content_width(&content_of(line));
    if pending {
        width
    } else {
        col.min(width)
    }
}

/// The `rewrap_cells` function wraps `cells` into lines of `cols` columns.
/// An empty input is one blank line. A wide cell that does not fit starts a new wrapped line. A width of 2 adds a spacer. A line that fills the width is wrapped. The last line is not wrapped.
fn rewrap_cells(cells: &[Cell], cols: usize) -> Vec<Line> {
    if cells.is_empty() {
        return vec![blank_line(cols)];
    }
    let mut lines = Vec::new();
    let mut current: Vec<Cell> = Vec::new();
    let mut used = 0usize;
    for cell in cells {
        if cell.width == 0 {
            continue;
        }
        let width = cell.width.max(1) as usize;
        if !current.is_empty() && used + width > cols {
            let width_here = used;
            lines.push(finish_line(std::mem::take(&mut current), cols, true, width_here));
            used = 0;
        }
        current.push(cell.clone());
        if cell.width == 2 && cols >= 2 {
            let mut spacer = cell.clone();
            spacer.text.clear();
            spacer.width = 0;
            spacer.bold = false;
            spacer.reverse = false;
            current.push(spacer);
        }
        used += width;
        if used >= cols {
            let width_here = used;
            lines.push(finish_line(std::mem::take(&mut current), cols, true, width_here));
            used = 0;
        }
    }
    if current.is_empty() {
        if let Some(last) = lines.last_mut() {
            last.wrapped = false;
        }
    } else {
        lines.push(finish_line(current, cols, false, used));
    }
    if lines.is_empty() {
        lines.push(blank_line(cols));
    }
    lines
}

/// The `finish_line` function pads or clips `cells` into one [`Line`].
/// `wrapped` and `used` are stored as given.
fn finish_line(mut cells: Vec<Cell>, cols: usize, wrapped: bool, used: usize) -> Line {
    if cells.len() < cols {
        cells.resize(cols, Cell::blank());
    } else if cells.len() > cols {
        cells.truncate(cols);
    }
    Line { cells, wrapped, used }
}

/// The `locate_in_piece` function finds `target` inside a rewrapped run.
/// The result is a row, a column, and whether wrap is pending. An empty piece returns row 0, column 0, and no pending wrap. A target past the end lands on the last line.
fn locate_in_piece(piece: &[Line], target: usize) -> (usize, usize, bool) {
    if piece.is_empty() {
        return (0, 0, false);
    }
    let mut seen = 0usize;
    for (index, line) in piece.iter().enumerate() {
        let width = content_width(&content_of(line));
        if target < seen + width {
            return (index, target - seen, false);
        }
        if target == seen + width {
            let last = index + 1 == piece.len();
            if !last {
                seen += width;
                continue;
            }
            if width > 0 && width >= line.cells.len() {
                return (index, width.saturating_sub(1), true);
            }
            let col = width.min(line.cells.len().saturating_sub(1));
            return (index, col, false);
        }
        seen += width;
    }
    let last = piece.len() - 1;
    let width = content_width(&content_of(&piece[last]));
    let pending = width > 0 && width >= piece[last].cells.len();
    let col = if pending {
        piece[last].cells.len().saturating_sub(1)
    } else {
        width.min(piece[last].cells.len().saturating_sub(1))
    };
    (last, col, pending)
}

/// The `seat_window` function chooses the visible window after a reflow.
/// A short transcript grows blank lines. A cursor that was on the last row grows those lines above it. Lines above the window become scrollback and the cap drops the oldest. A cursor outside the window is placed on row 0 and wrap is cleared. A saved cursor outside the window is placed on row 0.
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
) -> (VecDeque<Line>, Vec<Line>, usize, usize, bool, Option<(usize, usize)>) {
    let rows = rows.max(1);
    let cols = cols.max(1);
    if lines.is_empty() {
        lines.push(blank_line(cols));
    }
    if cursor_abs >= lines.len() {
        cursor_abs = lines.len() - 1;
    }
    if lines.len() < rows {
        let missing = rows - lines.len();
        if on_tail {
            for _ in 0..missing {
                lines.insert(0, blank_line(cols));
            }
            cursor_abs += missing;
            if let Some((abs, _)) = saved_hit.as_mut() {
                *abs += missing;
            }
        } else {
            for _ in 0..missing {
                lines.push(blank_line(cols));
            }
        }
    }
    let start = if lines.len() > rows {
        lines.len() - rows
    } else {
        0
    };
    let mut scroll: VecDeque<Line> = lines[..start].iter().cloned().collect();
    while scroll.len() > scroll_cap {
        scroll.pop_front();
    }
    let visible = lines[start..start + rows].to_vec();
    let on_screen = cursor_abs >= start && cursor_abs < start + rows;
    let row = if on_screen {
        cursor_abs - start
    } else {
        0
    };
    if !on_screen {
        pending = false;
    }
    let col = cursor_col.min(cols.saturating_sub(1));
    let saved_seat = saved_hit.map(|(abs, scol)| {
        let srow = if abs >= start && abs < start + rows {
            abs - start
        } else {
            0
        };
        (srow, scol.min(cols.saturating_sub(1)))
    });
    (scroll, visible, row, col, pending, saved_seat)
}

/// The `flatten` function copies CSI parameters into one list.
/// An empty group becomes 0. An empty parameter list stays empty.
fn flatten(params: &Params) -> Vec<u16> {
    let mut flat = Vec::new();
    if params.is_empty() {
        return flat;
    }
    for group in params.iter() {
        if group.is_empty() {
            flat.push(0);
        } else {
            flat.extend_from_slice(group);
        }
    }
    flat
}

/// The `param_at` function returns one CSI parameter.
/// A missing group returns `default`. An empty group returns 0.
fn param_at(params: &Params, index: usize, default: usize) -> usize {
    let group = params.iter().nth(index);
    match group {
        Some(values) => values.first().copied().unwrap_or(0) as usize,
        None => default,
    }
}

/// The `motion` function returns a cursor distance.
/// A missing parameter becomes 1. A parameter of 0 becomes 1.
fn motion(params: &Params) -> usize {
    let n = param_at(params, 0, 1);
    if n == 0 {
        1
    } else {
        n
    }
}

/// The `read_color` function reads an SGR 38 or 48 color.
/// A selector of 5 returns an indexed color and a used count of 3. A selector of 2 returns an RGB color and a used count of 5. Any other shape returns `None`.
fn read_color(flat: &[u16]) -> Option<(Color, usize)> {
    if flat.len() >= 3 && flat[1] == 5 {
        return Some((Color::Index(flat[2] as u8), 3));
    }
    if flat.len() >= 5 && flat[1] == 2 {
        return Some((
            Color::Rgb(flat[2] as u8, flat[3] as u8, flat[4] as u8),
            5,
        ));
    }
    None
}

#[cfg(test)]
/// These functions check the terminal grid.
mod tests {
    use super::{Color, Grid};

    /// The `grid` function returns a test grid.
    /// The size is 10 columns by 4 rows.
    fn grid() -> Grid {
        Grid::new(10, 4)
    }

    /// The `cr_lf_lands_on_the_next_row` test feeds `ab`, a return, a line feed, and `cd`.
    /// Row 0 is `ab`. Row 1 is `cd`.
    #[test]
    fn cr_lf_lands_on_the_next_row() {
        let mut g = grid();
        g.feed(b"ab\r\ncd");
        assert_eq!(g.visible_text(0), "ab");
        assert_eq!(g.visible_text(1), "cd");
    }

    /// The `backspace_moves_the_cursor` test feeds `AB` and a backspace.
    /// The cursor is column 1. The text stays `AB`.
    #[test]
    fn backspace_moves_the_cursor() {
        let mut g = grid();
        g.feed(b"AB\x08");
        assert_eq!(g.cursor(), (0, 1));
        assert_eq!(g.visible_text(0), "AB");
    }

    /// The `sgr_color_and_reset` test feeds bold red `Hi` and then a reset `Z`.
    /// The `H` cell is bold with index 1. The `Z` cell is not bold and uses the default foreground.
    #[test]
    fn sgr_color_and_reset() {
        let mut g = grid();
        g.feed(b"\x1b[1;31mHi\x1b[0mZ");
        let h = g.visible_cell(0, 0).unwrap();
        assert!(h.bold());
        assert_eq!(h.fg(), Color::Index(1));
        let z = g.visible_cell(0, 2).unwrap();
        assert!(!z.bold());
        assert_eq!(z.fg(), Color::Default);
    }

    /// The `indexed_and_rgb_colors_are_stored` test stores an indexed foreground and an RGB background.
    /// The `A` cell foreground is index 200. The `B` cell background is red 1, green 2, and blue 3.
    #[test]
    fn indexed_and_rgb_colors_are_stored() {
        let mut g = grid();
        g.feed(b"\x1b[38;5;200mA\x1b[48;2;1;2;3mB");
        assert_eq!(g.visible_cell(0, 0).unwrap().fg(), Color::Index(200));
        assert_eq!(
            g.visible_cell(0, 1).unwrap().bg(),
            Color::Rgb(1, 2, 3)
        );
    }

    /// The `cursor_address_and_erase_redraw_a_line` test addresses column 2 and erases to the end.
    /// The visible text is `h`. The cursor is column 1 of row 0.
    #[test]
    fn cursor_address_and_erase_redraw_a_line() {
        let mut g = grid();
        g.feed(b"hello\x1b[1;2H\x1b[K");
        assert_eq!(g.visible_text(0), "h");
        assert_eq!(g.cursor(), (0, 1));
    }

    /// The `movement_keys` test addresses row 3 column 4 and then moves.
    /// Up, right, down by 2, and left leave the cursor at row 3 column 3.
    #[test]
    fn movement_keys() {
        let mut g = grid();
        g.feed(b"\x1b[3;4H\x1b[A\x1b[C\x1b[2B\x1b[D");
        assert_eq!(g.cursor(), (3, 3));
    }

    /// The `utf8_split_across_feeds_prints_one_character` test splits the UTF-8 bytes of `é`.
    /// The first feed is `0xC3`. The second feed is `0xA9`. The cell text is `é`.
    #[test]
    fn utf8_split_across_feeds_prints_one_character() {
        let mut g = grid();
        g.feed(&[0xC3]);
        g.feed(&[0xA9]);
        assert_eq!(g.visible_text(0), "é");
        assert_eq!(g.visible_cell(0, 0).unwrap().text(), "é");
    }

    /// The `the_cell_owns_its_text_after_the_buffer_is_gone` test drops the input buffer.
    /// The cell text is still `H`.
    #[test]
    fn the_cell_owns_its_text_after_the_buffer_is_gone() {
        let mut g = grid();
        {
            let mut bytes = b"Hi".to_vec();
            g.feed(&bytes);
            bytes.clear();
            bytes.shrink_to_fit();
        }
        assert_eq!(g.visible_cell(0, 0).unwrap().text(), "H");
    }

    /// The `unknown_csi_is_consumed` test feeds an unknown CSI action and then `A`.
    /// The visible text is `A`. The cursor is column 1.
    #[test]
    fn unknown_csi_is_consumed() {
        let mut g = grid();
        g.feed(b"\x1b[999zA");
        assert_eq!(g.visible_text(0), "A");
        assert_eq!(g.cursor(), (0, 1));
    }

    /// The `resize_pads_and_clips` test changes the size without a soft wrap across columns of a hard break.
    /// `abcd` on a 4 by 2 grid becomes two rows of `ab` and `cd` at width 2. A 0 by 0 resize keeps 2 by 2. A 5 by 3 resize keeps `abcd` on the last row. A cursor on the last row of a taller screen stays on the last row. A shorter screen moves the line above that row into the scrollback.
    #[test]
    fn resize_pads_and_clips() {
        let mut g = Grid::new(4, 2);
        g.feed(b"abcd");
        g.resize(2, 2);
        assert_eq!(g.visible_text(0), "ab");
        assert_eq!(g.visible_text(1), "cd");
        g.resize(0, 0);
        assert_eq!(g.cols(), 2);
        assert_eq!(g.rows(), 2);
        g.resize(5, 3);
        assert_eq!(g.cols(), 5);
        assert_eq!(g.rows(), 3);
        assert_eq!(g.visible_text(0), "");
        assert_eq!(g.visible_text(2), "abcd");
        assert_eq!(g.cursor().0, 2);
        let mut tail = Grid::new(4, 3);
        tail.feed(b"top\r\nmid\r\nhi");
        assert_eq!(tail.cursor(), (2, 2));
        assert_eq!(tail.visible_text(2), "hi");
        tail.resize(4, 5);
        assert_eq!(tail.rows(), 5);
        assert_eq!(tail.cursor().0, 4);
        assert_eq!(tail.visible_text(4), "hi");
        assert_eq!(tail.visible_text(2), "top");
        tail.resize(4, 3);
        assert_eq!(tail.cursor(), (2, 2));
        assert_eq!(tail.visible_text(2), "hi");
        assert_eq!(tail.visible_text(0), "top");
        tail.resize(4, 2);
        assert_eq!(tail.cursor().0, 1);
        assert_eq!(tail.visible_text(1), "hi");
        assert_eq!(tail.scrollback_text(tail.scrollback_len() - 1), "top");
    }

    /// The `shown` function collects non-empty scrollback lines and then non-empty visible rows.
    /// An empty line is left out.
    fn shown(g: &Grid) -> Vec<String> {
        let mut out = Vec::new();
        for index in 0..g.scrollback_len() {
            let text = g.scrollback_text(index);
            if !text.is_empty() {
                out.push(text);
            }
        }
        for row in 0..g.rows() {
            let text = g.visible_text(row);
            if !text.is_empty() {
                out.push(text);
            }
        }
        out
    }

    /// The `resize_reflows_the_session` test reflows soft wraps, hard breaks, a wide glyph, SGR, the alternate screen, and scrollback.
    /// `abcde` at width 2 is `ab`, `cd`, and `e`, and width 8 joins it again. A hard break stays a hard break. The wide glyph `你` keeps width 2 and a spacer. Bold red survives the reflow. The alternate screen clips without joining the primary text. A widened scroll joins `abcdefghij`.
    #[test]
    fn resize_reflows_the_session() {
        let mut soft = Grid::new(4, 6);
        soft.feed(b"abcde");
        soft.resize(2, 6);
        assert_eq!(soft.visible_text(0), "ab");
        assert_eq!(soft.visible_text(1), "cd");
        assert_eq!(soft.visible_text(2), "e");
        soft.resize(8, 6);
        assert_eq!(soft.visible_text(0), "abcde");
        assert_eq!(soft.visible_text(1), "");

        let mut hard = Grid::new(4, 6);
        hard.feed(b"abcd\r\nxy");
        hard.resize(2, 6);
        assert_eq!(hard.visible_text(0), "ab");
        assert_eq!(hard.visible_text(1), "cd");
        assert_eq!(hard.visible_text(2), "xy");
        hard.resize(6, 6);
        assert_eq!(hard.visible_text(0), "abcd");
        assert_eq!(hard.visible_text(1), "xy");

        let mut wide = Grid::new(4, 4);
        wide.feed("ab你cd".as_bytes());
        wide.resize(3, 4);
        assert_eq!(wide.visible_text(0), "ab");
        assert_eq!(wide.visible_text(1), "你c");
        assert_eq!(wide.visible_cell(1, 0).unwrap().width(), 2);
        assert_eq!(wide.visible_cell(1, 1).unwrap().width(), 0);
        assert_eq!(wide.visible_text(2), "d");
        wide.resize(4, 4);
        assert_eq!(wide.visible_text(0), "ab你");
        assert_eq!(wide.visible_text(1), "cd");

        let mut ink = Grid::new(4, 3);
        ink.feed(b"\x1b[1;31mabcd");
        ink.resize(2, 3);
        assert!(ink.visible_cell(0, 0).unwrap().bold());
        assert_eq!(ink.visible_cell(0, 0).unwrap().fg(), Color::Index(1));
        assert_eq!(ink.visible_cell(1, 1).unwrap().fg(), Color::Index(1));
        assert!(ink.visible_cell(1, 1).unwrap().bold());

        let mut alt = Grid::new(4, 3);
        alt.feed(b"abcd");
        alt.feed(b"\x1b[?1049h");
        alt.feed(b"WXYZ");
        alt.resize(2, 3);
        assert_eq!(alt.visible_text(0), "WX");
        alt.feed(b"\x1b[?1049l");
        assert_eq!(alt.visible_text(0), "ab");
        assert_eq!(alt.visible_text(1), "cd");

        let mut scrolled = Grid::new(4, 2);
        scrolled.feed(b"abcdefghij");
        scrolled.resize(10, 2);
        assert_eq!(shown(&scrolled).concat(), "abcdefghij");
    }

    /// The `scrollback_drops_the_oldest_line` test uses a cap of 2.
    /// After four line feeds the scrollback length is at most 2. The oldest text is not `one`.
    #[test]
    fn scrollback_drops_the_oldest_line() {
        let mut g = Grid::with_scrollback(4, 2, 2);
        g.feed(b"one\r\ntwo\r\nthree\r\nfour\r\n");
        assert!(g.scrollback_len() <= 2);
        assert_ne!(g.scrollback_text(0), "one");
    }

    /// The `cursor_position_report_matches_the_grid` test asks for the cursor position.
    /// The reply is ESC `[2;3R`.
    #[test]
    fn cursor_position_report_matches_the_grid() {
        let mut g = grid();
        g.feed(b"\x1b[2;3H\x1b[6n");
        assert_eq!(g.take_reply(), b"\x1b[2;3R");
    }

    /// The `device_attributes_answer_vt100` test asks for device attributes.
    /// The reply is ESC `[?1;0c`.
    #[test]
    fn device_attributes_answer_vt100() {
        let mut g = grid();
        g.feed(b"\x1b[c");
        assert_eq!(g.take_reply(), b"\x1b[?1;0c");
    }

    /// The `alternate_screen_restores_the_primary_transcript` test enters mode 1049 and leaves it.
    /// The alternate text is `ALT`. The primary text `chat` returns.
    #[test]
    fn alternate_screen_restores_the_primary_transcript() {
        let mut g = grid();
        g.feed(b"chat\r\n");
        g.feed(b"\x1b[?1049h");
        g.feed(b"ALT");
        assert_eq!(g.visible_text(0), "ALT");
        g.feed(b"\x1b[?1049l");
        assert_eq!(g.visible_text(0), "chat");
    }

    /// The `decsc_and_decrc` test saves the cursor with ESC `7` and restores it with ESC `8`.
    /// The cursor returns to row 1 column 1.
    #[test]
    fn decsc_and_decrc() {
        let mut g = grid();
        g.feed(b"\x1b[2;2H\x1b7\x1b[1;1H\x1b8");
        assert_eq!(g.cursor(), (1, 1));
    }

    /// The `combining_mark_attaches_to_the_previous_cell` test feeds `e` and a combining acute.
    /// The cell text is that pair. The cursor is column 1.
    #[test]
    fn combining_mark_attaches_to_the_previous_cell() {
        let mut g = grid();
        g.feed("e\u{0301}".as_bytes());
        assert_eq!(g.visible_cell(0, 0).unwrap().text(), "e\u{0301}");
        assert_eq!(g.cursor(), (0, 1));
    }

    /// The `bell_sets_a_flag` test feeds a C0 bell.
    /// The first take returns true. The flag is then clear.
    #[test]
    fn bell_sets_a_flag() {
        let mut g = grid();
        g.feed(b"\x07");
        assert!(g.take_bell());
        assert!(!g.bell());
    }

    /// The `paste_mode_and_decckm` test sets modes 2004 and 1.
    /// Both flags are on. Clearing 2004 clears paste. Mode 25 does not set paste.
    #[test]
    fn paste_mode_and_decckm() {
        let mut g = grid();
        g.feed(b"\x1b[?2004h\x1b[?1h");
        assert!(g.paste_mode());
        assert!(g.decckm());
        g.feed(b"\x1b[?2004l");
        assert!(!g.paste_mode());
        g.feed(b"\x1b[?25h");
        assert!(!g.paste_mode());
    }

    /// The `autowrap_moves_the_next_glyph` test feeds five letters into four columns.
    /// Row 0 is `abcd`. Row 1 is `e`.
    #[test]
    fn autowrap_moves_the_next_glyph() {
        let mut g = Grid::new(4, 3);
        g.feed(b"abcde");
        assert_eq!(g.visible_text(0), "abcd");
        assert_eq!(g.visible_text(1), "e");
    }

    /// The `delete_line_closes_a_wrap` test deletes a line under a wrapped row.
    /// The first row becomes `012345678`. The next row is `ZZZZ`. The cursor ends at column 9 of row 0.
    #[test]
    fn delete_line_closes_a_wrap() {
        let mut g = Grid::new(10, 4);
        g.feed(b"0123456789\r\n\x1b[1B\rZZZZ\r\x1b[1A");
        assert_eq!(g.cursor(), (1, 0));
        assert_eq!(g.visible_text(2), "ZZZZ");
        g.feed(b"\x1b[1A\x1b[9C\x1b[K\x1b[1B\x1b[9D\x1b[1M\x1b[1A\x1b[9C");
        assert_eq!(g.visible_text(0), "012345678");
        assert_eq!(g.visible_text(1), "ZZZZ");
        assert_eq!(g.visible_text(2), "");
        assert_eq!(g.cursor(), (0, 9));
    }

    /// The `delete_line_joins_a_newline` test deletes the line under a hard break.
    /// The first row becomes `ABC`. The next row is `ZZZZ`. The cursor ends at column 3 of row 0.
    #[test]
    fn delete_line_joins_a_newline() {
        let mut g = Grid::new(10, 4);
        g.feed(b"AB\r\nC\x1b[1B\rZZZZ\r\x1b[1A\x1b[1C");
        assert_eq!(g.cursor(), (1, 1));
        g.feed(b"\x1b[1A\x1b[1CC\x1b[K\x1b[1B\x1b[3D\x1b[1M\x1b[1A\x1b[3C");
        assert_eq!(g.visible_text(0), "ABC");
        assert_eq!(g.visible_text(1), "ZZZZ");
        assert_eq!(g.cursor(), (0, 3));
    }

    /// The `delete_line_keeps_the_cursor` test deletes lines and leaves the cursor.
    /// The cursor stays at row 1 column 0. The deleted line does not enter the scrollback. A second delete of two lines clears that row.
    #[test]
    fn delete_line_keeps_the_cursor() {
        let mut g = grid();
        g.feed(b"AA\r\nBB\r\nCC\x1b[2;1H");
        assert_eq!(g.cursor(), (1, 0));
        let kept = g.scrollback_len();
        g.feed(b"\x1b[M");
        assert_eq!(g.visible_text(1), "CC");
        assert_eq!(g.visible_text(2), "");
        assert_eq!(g.cursor(), (1, 0));
        assert_eq!(g.scrollback_len(), kept);
        g.feed(b"\x1b[2M");
        assert_eq!(g.visible_text(1), "");
        assert_eq!(g.cursor(), (1, 0));
    }

    /// The `esc_m_reverse_indexes` test feeds `AB` and then ESC `M`.
    /// `AB` moves to row 1. Row 0 is empty. The cursor is column 2 of row 0.
    #[test]
    fn esc_m_reverse_indexes() {
        let mut g = grid();
        g.feed(b"AB\x1bM");
        assert_eq!(g.visible_text(1), "AB");
        assert_eq!(g.visible_text(0), "");
        assert_eq!(g.cursor(), (0, 2));
    }
}
