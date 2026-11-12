// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The [`Door`] type reads and writes the SSH client settings file.
//!
//! The file is `ssh-client.yaml` under `.casserole` in the home directory. Unknown keys stay in the stored map. A password is written only when the remember flag is on. A failed parse does not overwrite the file. `chrome::run` loads the home path through [`Door::load_home`]. Tests pass temporary paths to [`Door::open`].
//!
//! A missing YAML file can be filled once from `.ssh-client.properties` in the home directory. That older file is left in place. The imported map is written to the YAML path.
//!
//! @author clayl (2026-10-23)

use std::fs;
use std::path::{Path, PathBuf};

use serde_yaml::{Mapping, Value};

const HOST_KEY: &str = "ui.connection.host";
const PORT_KEY: &str = "ui.connection.port";
const USER_KEY: &str = "ui.connection.username";
const PASS_KEY: &str = "ui.connection.password";
const REMEMBER_KEY: &str = "ui.connection.rememberPassword";
const ACCOUNTS_KEY: &str = "accounts";
const DIVIDER_KEY: &str = "ui.aetheris.inspector.divider";
const WRAP_KEY: &str = "ui.aetheris.inspector.wrap.columns";
const LINE_CHARS_KEY: &str = "ui.aetheris.inspector.line.chars";
const VIEW_ROWS_KEY: &str = "ui.aetheris.view.rows";
const PRODUCT_TAB_KEY: &str = "ui.product.tab";
const PRODUCT_TAB_SSH: &str = "ssh";
const PRODUCT_TAB_AETHERIS: &str = "aetheris";
const PIN_BAR_KEY: &str = "ui.aetheris.pin.bar.open";
const CHOOSER_X_KEY: &str = "ui.chooser.x";
const CHOOSER_Y_KEY: &str = "ui.chooser.y";
const CHOOSER_W_KEY: &str = "ui.chooser.width";
const CHOOSER_H_KEY: &str = "ui.chooser.height";
const CHOOSER_DIR_KEY: &str = "ui.chooser.directory";
const FONT_KEY: &str = "ui.terminal.font.size";
const MODE_KEY: &str = "ui.connection.mode";
const FRAME_X_KEY: &str = "ui.frame.x";
const FRAME_Y_KEY: &str = "ui.frame.y";
const FRAME_W_KEY: &str = "ui.frame.width";
const FRAME_H_KEY: &str = "ui.frame.height";
const FRAME_MAX_KEY: &str = "ui.frame.maximized";
const EXIT_X_KEY: &str = "ui.exit.x";
const EXIT_Y_KEY: &str = "ui.exit.y";
/// `FONT_DEFAULT` is the font size used when the font key is missing or unreadable.
/// The value is 14. A stored size that parses stays as written, then `font_size` clamps it.
const FONT_DEFAULT: i32 = 14;
const FONT_MIN: i32 = 8;
const FONT_MAX: i32 = 36;
const FRAME_DEFAULT_W: i32 = 1024;
const FRAME_DEFAULT_H: i32 = 968;
const HEADER: &str = "# Casserole standalone SSH client\n";
/// `WRAP_COLUMNS` is the wrap width used when that key is missing.
/// The value is 80. Swing `getAetherisWrapColumns` uses the same number.
const WRAP_COLUMNS: u32 = 80;
/// `LINE_CHARS` is the inspector line length used when that key is missing.
/// The value is 80. Swing `getAetherisInspectorLineChars` uses the same number.
const LINE_CHARS: u32 = 80;
/// `VIEW_ROWS` is the open-card row count used when that key is missing.
/// The value is 4. Swing `getAetherisViewRows` uses the same number.
const VIEW_ROWS: u32 = 4;

/// The [`DoorError`] enum is why a [`Door`] read or write stopped.
/// The value carries no file body.
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub enum DoorError {
    /// The `Read` variant means the YAML text could not be parsed.
    /// The file is left as it was.
    Read,
    /// The `Write` variant means the YAML text could not be written.
    /// A [`Door`] with no path also returns this variant from `save`.
    Write,
}

/// The `Account` struct is one stored user name and password.
/// A row with an empty user name is skipped on read. The password may be empty.
struct Account {
    username: String,
    password: String,
}

/// @aa-sdd PK-4475 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/// The [`Door`] type is the SSH client settings store.
/// It holds the host text, the port, the user name, the password, the remember flag, and the account rows. It also holds the YAML map so keys this type does not edit stay in the file.
///
/// `Debug` prints the host, the port, the user name, the remember flag, and the account count. A non-empty password is printed as `<redacted>`.
pub struct Door {
    path: Option<PathBuf>,
    map: Mapping,
    host: String,
    port: u16,
    username: String,
    password: String,
    remember: bool,
    accounts: Vec<Account>,
    writable: bool,
    write_warning: Option<&'static str>,
}

impl std::fmt::Debug for Door {
    /// The `fmt` method prints a [`Door`] without the password text.
    /// A non-empty password is shown as `<redacted>`. The account list is shown as a count.
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        let mut dbg = f.debug_struct("Door");
        dbg.field("host", &self.host)
            .field("port", &self.port)
            .field("username", &self.username);
        if !self.password.is_empty() {
            dbg.field("password", &"<redacted>");
        }
        dbg.field("remember", &self.remember)
            .field("accounts", &self.accounts.len())
            .finish()
    }
}

impl Door {
    /// The `memory_only` method returns a [`Door`] that is held in memory.
    /// The host text starts as `localhost`. The port starts as 2222. `save` does not create a file.
    pub fn memory_only() -> Self {
        Self::fresh(None, true)
    }

    /// The `has_file` method reports whether this [`Door`] has a path.
    /// A value from `memory_only` has no path. That value does not read a file and does not write a file.
    pub fn has_file(&self) -> bool {
        self.path.is_some()
    }

    /// The `load_home` method loads `ssh-client.yaml` from the home directory.
    /// The home directory is `USERPROFILE` or, if that is absent, `HOME`. A missing home directory returns [`DoorError::Read`]. Tests do not call this method. `chrome::run` does.
    pub fn load_home() -> Result<Self, DoorError> {
        let Some((yaml, legacy)) = home_paths() else {
            return Err(DoorError::Read);
        };
        Self::open(&yaml, &legacy)
    }

    /// The `open` method loads `yaml`, or imports `legacy` once when the YAML file is missing.
    /// An existing YAML file is parsed as a mapping. A parse failure returns [`DoorError::Read`] and does not write. A missing YAML file with an existing legacy file reads that file as properties, then tries to write the YAML path.
    ///
    /// A failed first write still returns the loaded value. The warning text on that failure is `door file could not be written`. Both files missing returns a fresh value aimed at the YAML path.
    pub fn open(yaml: &Path, legacy: &Path) -> Result<Self, DoorError> {
        if yaml.exists() {
            let text = fs::read(yaml).map_err(|_| DoorError::Read)?;
            let text = String::from_utf8(text).map_err(|_| DoorError::Read)?;
            let map = parse_yaml(&text)?;
            return Ok(Self::from_map(Some(yaml.to_path_buf()), map, true, None));
        }
        if legacy.exists() {
            let text = fs::read(legacy).map_err(|_| DoorError::Read)?;
            let text = String::from_utf8(text).map_err(|_| DoorError::Read)?;
            let map = parse_properties(&text);
            let mut door = Self::from_map(Some(yaml.to_path_buf()), map, true, None);
            if door.save().is_err() {
                door.write_warning = Some("door file could not be written");
            }
            return Ok(door);
        }
        Ok(Self::fresh(Some(yaml.to_path_buf()), true))
    }

    /// The `host` method returns the SSH host text this [`Door`] holds.
    /// The text is the value loaded from the file or the value last passed to `set_host`.
    pub fn host(&self) -> &str {
        &self.host
    }

    /// The `port` method returns the SSH port this [`Door`] holds.
    /// The value is the port loaded from the file or the value last passed to `set_port`.
    pub fn port(&self) -> u16 {
        self.port
    }

    /// The `username` method returns the user name this [`Door`] holds.
    /// The text is the value loaded from the file or the value last passed to `set_username`.
    pub fn username(&self) -> &str {
        &self.username
    }

    /// The `password` method returns the password this [`Door`] holds.
    /// The text is empty when the remember flag was off at load. `set_password` replaces it.
    pub fn password(&self) -> &str {
        &self.password
    }

    /// The `remember` method returns the remember flag this [`Door`] holds.
    /// The flag is true only when the stored word was `true`, ignoring ASCII case.
    pub fn remember(&self) -> bool {
        self.remember
    }

    /// The `connection_mode` method reads `ui.connection.mode`.
    /// The word `pty`, ignoring ASCII case and surrounding spaces, returns `pty`. A missing key returns `vt100`. Any other token returns `vt100`.
    pub fn connection_mode(&self) -> &'static str {
        match text_of(self.map.get(MODE_KEY)) {
            Some(text) if text.trim().eq_ignore_ascii_case("pty") => "pty",
            _ => "vt100",
        }
    }

    /// The `inspector_divider` method returns the saved canvas share.
    /// The key is `ui.aetheris.inspector.divider`. The number is a per-mille from 0 through 1000. An absent key returns `None`. A negative number returns `None`. A number above 1000 returns 1000.
    pub fn inspector_divider(&self) -> Option<u16> {
        let text = text_of(self.map.get(DIVIDER_KEY))?;
        let value: i32 = text.parse().ok()?;
        if value < 0 {
            return None;
        }
        if value > 1000 {
            return Some(1000);
        }
        Some(value as u16)
    }

    /// The `wrap_columns` method returns the inspector wrap width.
    /// A missing key returns 80. Unreadable text returns 80. A value under 1 returns 1.
    pub fn wrap_columns(&self) -> u32 {
        let Some(text) = text_of(self.map.get(WRAP_KEY)) else {
            return WRAP_COLUMNS;
        };
        parse_wrap_columns(&text)
    }

    /// The `save_wrap_columns` method writes the wrap column count.
    /// A count under 1 is stored as 1. The same stored text does not rewrite the file. Other keys stay.
    ///
    /// No path returns [`DoorError::Write`]. A value that is not writable returns [`DoorError::Write`]. The key is `ui.aetheris.inspector.wrap.columns`.
    pub fn save_wrap_columns(&mut self, columns: u32) -> Result<(), DoorError> {
        let value = columns.max(1);
        let text = value.to_string();
        if text_of(self.map.get(WRAP_KEY)).as_deref() == Some(text.as_str()) {
            return Ok(());
        }
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, WRAP_KEY, &text);
        let dumped = dump_map(&self.map).map_err(|_| DoorError::Write)?;
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|_| DoorError::Write)?;
        }
        replace_file(&path, dumped.as_bytes()).map_err(|_| DoorError::Write)?;
        Ok(())
    }

    /// The `line_chars` method returns the inspector line length.
    /// A missing key returns 80. Unreadable text returns 80. A value under 1 returns 1.
    pub fn line_chars(&self) -> u32 {
        let Some(text) = text_of(self.map.get(LINE_CHARS_KEY)) else {
            return LINE_CHARS;
        };
        parse_line_chars(&text)
    }

    /// The `save_line_chars` method writes the inspector line length.
    /// A count under 1 is stored as 1. The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`].
    pub fn save_line_chars(&mut self, count: u32) -> Result<(), DoorError> {
        let value = count.max(1);
        let text = value.to_string();
        if text_of(self.map.get(LINE_CHARS_KEY)).as_deref() == Some(text.as_str()) {
            return Ok(());
        }
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, LINE_CHARS_KEY, &text);
        let dumped = dump_map(&self.map).map_err(|_| DoorError::Write)?;
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|_| DoorError::Write)?;
        }
        replace_file(&path, dumped.as_bytes()).map_err(|_| DoorError::Write)?;
        Ok(())
    }

    /// The `view_rows` method returns the open-card row count.
    /// A missing key returns 4. Unreadable text returns 4. A value under 1 returns 1.
    pub fn view_rows(&self) -> u32 {
        let Some(text) = text_of(self.map.get(VIEW_ROWS_KEY)) else {
            return VIEW_ROWS;
        };
        parse_view_rows(&text)
    }

    /// The `save_view_rows` method writes the open-card row count.
    /// A count under 1 is stored as 1. The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`].
    pub fn save_view_rows(&mut self, rows: u32) -> Result<(), DoorError> {
        let value = rows.max(1);
        let text = value.to_string();
        if text_of(self.map.get(VIEW_ROWS_KEY)).as_deref() == Some(text.as_str()) {
            return Ok(());
        }
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, VIEW_ROWS_KEY, &text);
        let dumped = dump_map(&self.map).map_err(|_| DoorError::Write)?;
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|_| DoorError::Write)?;
        }
        replace_file(&path, dumped.as_bytes()).map_err(|_| DoorError::Write)?;
        Ok(())
    }

    /// The `product_tab_is_aetheris` method reports the stored product tab.
    /// It returns true only when the stored word is `aetheris` after trim. A missing key returns false, which is the SSH tab.
    pub fn product_tab_is_aetheris(&self) -> bool {
        text_of(self.map.get(PRODUCT_TAB_KEY))
            .as_deref()
            .is_some_and(|text| text.trim() == PRODUCT_TAB_AETHERIS)
    }

    /// The `save_product_tab` method stores `ssh` or `aetheris`.
    /// True stores `aetheris`. False stores `ssh`. The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`].
    pub fn save_product_tab(&mut self, aetheris: bool) -> Result<(), DoorError> {
        let text = if aetheris {
            PRODUCT_TAB_AETHERIS
        } else {
            PRODUCT_TAB_SSH
        };
        if text_of(self.map.get(PRODUCT_TAB_KEY)).as_deref() == Some(text) {
            return Ok(());
        }
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, PRODUCT_TAB_KEY, text);
        let dumped = dump_map(&self.map).map_err(|_| DoorError::Write)?;
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|_| DoorError::Write)?;
        }
        replace_file(&path, dumped.as_bytes()).map_err(|_| DoorError::Write)?;
        Ok(())
    }

    /// The `pin_bar_open` method reports whether the pin bar is showing.
    /// A missing key returns false, which is folded. Only the word `true`, after trim and ignoring ASCII case, returns true. Swing `getAetherisPinBarOpen` uses that rule.
    pub fn pin_bar_open(&self) -> bool {
        text_of(self.map.get(PIN_BAR_KEY))
            .as_deref()
            .is_some_and(pin_bar_flag)
    }

    /// The `save_pin_bar_open` method stores `true` or `false`.
    /// The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`].
    pub fn save_pin_bar_open(&mut self, open: bool) -> Result<(), DoorError> {
        let text = if open { "true" } else { "false" };
        if text_of(self.map.get(PIN_BAR_KEY)).as_deref() == Some(text) {
            return Ok(());
        }
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, PIN_BAR_KEY, text);
        let dumped = dump_map(&self.map).map_err(|_| DoorError::Write)?;
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|_| DoorError::Write)?;
        }
        replace_file(&path, dumped.as_bytes()).map_err(|_| DoorError::Write)?;
        Ok(())
    }

    /// The `chooser_bounds` method returns the saved dialog rectangle.
    /// The order is x, y, width, and height. Width and height must both be present and at least 1. A missing or unreadable x or y is 0. A missing width or height returns `None`.
    pub fn chooser_bounds(&self) -> Option<(i32, i32, i32, i32)> {
        let width = chooser_int(text_of(self.map.get(CHOOSER_W_KEY)).as_deref())?;
        let height = chooser_int(text_of(self.map.get(CHOOSER_H_KEY)).as_deref())?;
        if width < 1 || height < 1 {
            return None;
        }
        let x = chooser_int(text_of(self.map.get(CHOOSER_X_KEY)).as_deref()).unwrap_or(0);
        let y = chooser_int(text_of(self.map.get(CHOOSER_Y_KEY)).as_deref()).unwrap_or(0);
        Some((x, y, width, height))
    }

    /// The `chooser_directory` method returns the last approved folder.
    /// An absent key returns an empty string.
    pub fn chooser_directory(&self) -> String {
        text_of(self.map.get(CHOOSER_DIR_KEY)).unwrap_or_default()
    }

    /// The `save_chooser_bounds` method writes the four dialog bounds.
    /// The same four texts do not rewrite the file. Other keys stay. A width under 1 writes nothing and returns `Ok`. A height under 1 writes nothing and returns `Ok`. No path returns [`DoorError::Write`].
    pub fn save_chooser_bounds(
        &mut self,
        x: i32,
        y: i32,
        width: i32,
        height: i32,
    ) -> Result<(), DoorError> {
        if width < 1 || height < 1 {
            return Ok(());
        }
        let xs = x.to_string();
        let ys = y.to_string();
        let ws = width.to_string();
        let hs = height.to_string();
        if text_of(self.map.get(CHOOSER_X_KEY)).as_deref() == Some(xs.as_str())
            && text_of(self.map.get(CHOOSER_Y_KEY)).as_deref() == Some(ys.as_str())
            && text_of(self.map.get(CHOOSER_W_KEY)).as_deref() == Some(ws.as_str())
            && text_of(self.map.get(CHOOSER_H_KEY)).as_deref() == Some(hs.as_str())
        {
            return Ok(());
        }
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, CHOOSER_X_KEY, &xs);
        put_str(&mut self.map, CHOOSER_Y_KEY, &ys);
        put_str(&mut self.map, CHOOSER_W_KEY, &ws);
        put_str(&mut self.map, CHOOSER_H_KEY, &hs);
        let dumped = dump_map(&self.map).map_err(|_| DoorError::Write)?;
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|_| DoorError::Write)?;
        }
        replace_file(&path, dumped.as_bytes()).map_err(|_| DoorError::Write)?;
        Ok(())
    }

    /// The `font_size` method returns the terminal font size.
    /// A missing or unreadable key is 14. A stored value outside 8 through 36 clamps onto that range. Swing's spinner uses that range.
    pub fn font_size(&self) -> i32 {
        let stored = chooser_int(text_of(self.map.get(FONT_KEY)).as_deref()).unwrap_or(FONT_DEFAULT);
        stored.clamp(FONT_MIN, FONT_MAX)
    }

    /// The `save_font_size` method writes the terminal font size.
    /// A value outside 8 through 36 is clamped before the write. The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`].
    pub fn save_font_size(&mut self, size: i32) -> Result<(), DoorError> {
        let size = size.clamp(FONT_MIN, FONT_MAX);
        let text = size.to_string();
        if text_of(self.map.get(FONT_KEY)).as_deref() == Some(text.as_str()) {
            return Ok(());
        }
        self.write_key(FONT_KEY, &text)
    }

    /// The `frame_bounds` method returns the saved frame rectangle.
    /// The result is absent when either origin key was never stored. A missing width is 1024. A missing height is 968. A width under 1 is absent. A height under 1 is absent. An unreadable origin is 0.
    pub fn frame_bounds(&self) -> Option<(i32, i32, i32, i32)> {
        if self.map.get(FRAME_X_KEY).is_none() || self.map.get(FRAME_Y_KEY).is_none() {
            return None;
        }
        let x = chooser_int(text_of(self.map.get(FRAME_X_KEY)).as_deref()).unwrap_or(0);
        let y = chooser_int(text_of(self.map.get(FRAME_Y_KEY)).as_deref()).unwrap_or(0);
        let width = chooser_int(text_of(self.map.get(FRAME_W_KEY)).as_deref()).unwrap_or(FRAME_DEFAULT_W);
        let height = chooser_int(text_of(self.map.get(FRAME_H_KEY)).as_deref()).unwrap_or(FRAME_DEFAULT_H);
        if width < 1 || height < 1 {
            return None;
        }
        Some((x, y, width, height))
    }

    /// The `frame_maximized_word` method returns the maximized flag when that key is present.
    /// An absent key returns `None`. The word `true`, after trim and ignoring ASCII case, returns `Some(true)`. Any other stored text returns `Some(false)`.
    pub fn frame_maximized_word(&self) -> Option<bool> {
        let text = text_of(self.map.get(FRAME_MAX_KEY))?;
        Some(pin_bar_flag(&text))
    }

    /// The `save_frame` method writes the restored rectangle and the maximized flag.
    /// The same texts do not rewrite the file. Other keys stay. A width under 1 leaves the size keys as they are and still writes the flag. A height under 1 does the same. A missing `origin` does the same.
    ///
    /// No path returns [`DoorError::Write`]. A value that is not writable returns [`DoorError::Write`].
    pub fn save_frame(
        &mut self,
        origin: Option<(i32, i32, i32, i32)>,
        maximized: bool,
    ) -> Result<(), DoorError> {
        let max_text = if maximized { "true" } else { "false" };
        let size_ok = origin.is_some_and(|(_, _, width, height)| width >= 1 && height >= 1);
        let (xs, ys, ws, hs) = if size_ok {
            let (x, y, width, height) = origin.unwrap_or((0, 0, 0, 0));
            (
                Some(x.to_string()),
                Some(y.to_string()),
                Some(width.to_string()),
                Some(height.to_string()),
            )
        } else {
            (None, None, None, None)
        };
        let same_size = match (&xs, &ys, &ws, &hs) {
            (Some(xs), Some(ys), Some(ws), Some(hs)) => {
                text_of(self.map.get(FRAME_X_KEY)).as_deref() == Some(xs.as_str())
                    && text_of(self.map.get(FRAME_Y_KEY)).as_deref() == Some(ys.as_str())
                    && text_of(self.map.get(FRAME_W_KEY)).as_deref() == Some(ws.as_str())
                    && text_of(self.map.get(FRAME_H_KEY)).as_deref() == Some(hs.as_str())
            }
            _ => true,
        };
        let same_max = text_of(self.map.get(FRAME_MAX_KEY)).as_deref() == Some(max_text);
        if same_size && same_max {
            return Ok(());
        }
        if self.path.is_none() || !self.writable {
            return Err(DoorError::Write);
        }
        if let (Some(xs), Some(ys), Some(ws), Some(hs)) = (&xs, &ys, &ws, &hs) {
            put_str(&mut self.map, FRAME_X_KEY, xs);
            put_str(&mut self.map, FRAME_Y_KEY, ys);
            put_str(&mut self.map, FRAME_W_KEY, ws);
            put_str(&mut self.map, FRAME_H_KEY, hs);
        }
        put_str(&mut self.map, FRAME_MAX_KEY, max_text);
        self.write_map()
    }

    /// The `exit_pos` method returns the saved Confirm Exit origin.
    /// The result is absent when either key was never stored. An unreadable key is also absent.
    pub fn exit_pos(&self) -> Option<(i32, i32)> {
        let x = chooser_int(text_of(self.map.get(EXIT_X_KEY)).as_deref())?;
        let y = chooser_int(text_of(self.map.get(EXIT_Y_KEY)).as_deref())?;
        Some((x, y))
    }

    /// The `save_exit_pos` method writes the Confirm Exit origin.
    /// The same pair does not rewrite the file. Other keys stay. A [`Door`] that cannot write returns [`DoorError::Write`].
    pub fn save_exit_pos(&mut self, x: i32, y: i32) -> Result<(), DoorError> {
        let xs = x.to_string();
        let ys = y.to_string();
        if text_of(self.map.get(EXIT_X_KEY)).as_deref() == Some(xs.as_str())
            && text_of(self.map.get(EXIT_Y_KEY)).as_deref() == Some(ys.as_str())
        {
            return Ok(());
        }
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, EXIT_X_KEY, &xs);
        put_str(&mut self.map, EXIT_Y_KEY, &ys);
        self.write_at(&path)
    }

    /// The `write_key` method stores one string and writes the map.
    /// No path returns [`DoorError::Write`]. A value that is not writable returns [`DoorError::Write`].
    fn write_key(&mut self, key: &str, value: &str) -> Result<(), DoorError> {
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, key, value);
        self.write_at(&path)
    }

    /// The `write_map` method writes the current map.
    /// No path returns [`DoorError::Write`]. A value that is not writable returns [`DoorError::Write`]. The map is not changed by this method.
    fn write_map(&mut self) -> Result<(), DoorError> {
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        self.write_at(&path)
    }

    /// The `write_at` method dumps the map and replaces `path`.
    /// The parent directory is created when it is missing. A dump failure returns [`DoorError::Write`]. A replace failure returns [`DoorError::Write`].
    fn write_at(&mut self, path: &Path) -> Result<(), DoorError> {
        let dumped = dump_map(&self.map).map_err(|_| DoorError::Write)?;
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|_| DoorError::Write)?;
        }
        replace_file(path, dumped.as_bytes()).map_err(|_| DoorError::Write)?;
        Ok(())
    }

    /// The `save_chooser_directory` method writes the last approved folder.
    /// An empty string writes nothing and returns `Ok`. The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`].
    pub fn save_chooser_directory(&mut self, directory: &str) -> Result<(), DoorError> {
        if directory.is_empty() {
            return Ok(());
        }
        if text_of(self.map.get(CHOOSER_DIR_KEY)).as_deref() == Some(directory) {
            return Ok(());
        }
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, CHOOSER_DIR_KEY, directory);
        let dumped = dump_map(&self.map).map_err(|_| DoorError::Write)?;
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|_| DoorError::Write)?;
        }
        replace_file(&path, dumped.as_bytes()).map_err(|_| DoorError::Write)?;
        Ok(())
    }

    /// The `save_inspector_divider` method writes one per-mille.
    /// A value above 1000 is stored as 1000. The same value does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`].
    pub fn save_inspector_divider(&mut self, per_mille: u16) -> Result<(), DoorError> {
        let value = u16::min(per_mille, 1000);
        if self.inspector_divider() == Some(value) {
            return Ok(());
        }
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, DIVIDER_KEY, &value.to_string());
        let text = dump_map(&self.map).map_err(|_| DoorError::Write)?;
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|_| DoorError::Write)?;
        }
        replace_file(&path, text.as_bytes()).map_err(|_| DoorError::Write)?;
        Ok(())
    }

    /// The `set_host` method stores the SSH host text.
    /// Surrounding spaces are removed. The file is not written until `save`.
    pub fn set_host(&mut self, host: String) {
        self.host = host.trim().to_string();
    }

    /// The `set_port` method stores the SSH port.
    /// A port of 0 is ignored. Any other port replaces the stored port. The file is not written until `save`.
    pub fn set_port(&mut self, port: u16) {
        if port > 0 {
            self.port = port;
        }
    }

    /// The `set_username` method stores the user name.
    /// Surrounding spaces are removed. The file is not written until `save`.
    pub fn set_username(&mut self, username: String) {
        self.username = username.trim().to_string();
    }

    /// The `set_password` method stores the password.
    /// The text is kept as given, including spaces. The file is not written until `save`.
    pub fn set_password(&mut self, password: String) {
        self.password = password;
    }

    /// The `set_remember` method stores the remember flag.
    /// The file is not written until `save`.
    pub fn set_remember(&mut self, remember: bool) {
        self.remember = remember;
    }

    /// The `can_save` method reports whether `save` can write a file.
    /// The result is true only when the value is writable and has a path.
    pub fn can_save(&self) -> bool {
        self.writable && self.path.is_some()
    }

    /// The `write_warning` method returns the warning from a failed import write.
    /// `None` means no warning is stored. A later successful `save` clears it.
    pub fn write_warning(&self) -> Option<&'static str> {
        self.write_warning
    }

    /// The `account_names` method returns the stored user names.
    /// The order is the order of the account rows. Passwords are not included.
    pub fn account_names(&self) -> Vec<String> {
        let mut names = Vec::with_capacity(self.accounts.len());
        for account in &self.accounts {
            names.push(account.username.clone());
        }
        names
    }

    /// The `password_for` method returns the stored password for `username`.
    /// The match is the exact user name. An empty stored password returns `None`. A missing name returns `None`.
    pub fn password_for(&self, username: &str) -> Option<&str> {
        for account in &self.accounts {
            if account.username == username && !account.password.is_empty() {
                return Some(&account.password);
            }
        }
        None
    }

    /// The `save` method writes the connection keys.
    /// The host, the port, the user name, and the remember flag are written. The password key is written only when remember is on and the password is not empty. Otherwise that key is removed.
    ///
    /// Other keys stay. Other account rows stay. The matching account row is updated. No path returns [`DoorError::Write`]. A successful write clears the warning.
    pub fn save(&mut self) -> Result<(), DoorError> {
        let Some(path) = self.path.clone() else {
            return Err(DoorError::Write);
        };
        if !self.writable {
            return Err(DoorError::Write);
        }
        put_str(&mut self.map, HOST_KEY, &self.host);
        put_str(&mut self.map, PORT_KEY, &self.port.to_string());
        put_str(&mut self.map, USER_KEY, &self.username);
        put_str(&mut self.map, REMEMBER_KEY, if self.remember { "true" } else { "false" });
        if self.remember && !self.password.is_empty() {
            put_str(&mut self.map, PASS_KEY, &self.password);
        } else {
            self.map.remove(PASS_KEY);
        }
        touch_account(&mut self.map, &self.username, &self.password, self.remember);
        self.accounts = accounts_of(&self.map);
        let text = dump_map(&self.map).map_err(|_| DoorError::Write)?;
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).map_err(|_| DoorError::Write)?;
        }
        replace_file(&path, text.as_bytes()).map_err(|_| DoorError::Write)?;
        self.write_warning = None;
        Ok(())
    }

    /// The `fresh` method builds a [`Door`] with the default connection.
    /// The host text is `localhost`. The port is 2222. The user name comes from `os_user`. The password is empty. The remember flag is false. The map is empty.
    fn fresh(path: Option<PathBuf>, writable: bool) -> Self {
        Self {
            path,
            map: Mapping::new(),
            host: "localhost".to_string(),
            port: 2222,
            username: os_user(),
            password: String::new(),
            remember: false,
            accounts: Vec::new(),
            writable,
            write_warning: None,
        }
    }

    /// The `from_map` method builds a [`Door`] from a YAML map.
    /// An empty host becomes `localhost`. A missing or unreadable port becomes 2222. An empty user name becomes `os_user`. The remember flag is true only for the word `true`, ignoring ASCII case.
    ///
    /// The password is read only when remember is true. Otherwise the password is empty even if the key is present. Account rows are read from the map.
    fn from_map(
        path: Option<PathBuf>,
        map: Mapping,
        writable: bool,
        write_warning: Option<&'static str>,
    ) -> Self {
        let host = text_of(map.get(HOST_KEY))
            .filter(|s| !s.is_empty())
            .unwrap_or_else(|| "localhost".to_string());
        let port = text_of(map.get(PORT_KEY))
            .and_then(|s| parse_port(&s))
            .unwrap_or(2222);
        let username = text_of(map.get(USER_KEY))
            .filter(|s| !s.is_empty())
            .unwrap_or_else(os_user);
        let remember = text_of(map.get(REMEMBER_KEY))
            .map(|s| s.eq_ignore_ascii_case("true"))
            .unwrap_or(false);
        let password = if remember {
            text_of(map.get(PASS_KEY)).unwrap_or_default()
        } else {
            String::new()
        };
        let accounts = accounts_of(&map);
        Self {
            path,
            map,
            host,
            port,
            username,
            password,
            remember,
            accounts,
            writable,
            write_warning,
        }
    }
}

/// The `home_paths` function returns the YAML path and the legacy properties path.
/// The home directory is `USERPROFILE` or else `HOME`. The YAML path is `.casserole/ssh-client.yaml` under that directory. The legacy path is `.ssh-client.properties` under that directory. No home directory returns `None`.
fn home_paths() -> Option<(PathBuf, PathBuf)> {
    let home = std::env::var_os("USERPROFILE").or_else(|| std::env::var_os("HOME"))?;
    let home = PathBuf::from(home);
    let yaml = home.join(".casserole").join("ssh-client.yaml");
    let legacy = home.join(".ssh-client.properties");
    Some((yaml, legacy))
}

/// The `os_user` function returns the operating-system user name.
/// `USERNAME` is tried first. `USER` is tried next. Both missing returns `user`.
fn os_user() -> String {
    std::env::var("USERNAME")
        .or_else(|_| std::env::var("USER"))
        .unwrap_or_else(|_| "user".to_string())
}

/// The `parse_port` function reads a port from text.
/// Surrounding spaces are ignored. A whole number from 1 through 65535 returns `Some`. Any other text returns `None`.
fn parse_port(text: &str) -> Option<u16> {
    let value: u32 = text.trim().parse().ok()?;
    if (1..=65535).contains(&value) {
        Some(value as u16)
    } else {
        None
    }
}

/// The `parse_yaml` function reads a YAML mapping.
/// A parse failure returns [`DoorError::Read`]. A YAML value that is not a mapping returns [`DoorError::Read`].
fn parse_yaml(text: &str) -> Result<Mapping, DoorError> {
    let value: Value = serde_yaml::from_str(text).map_err(|_| DoorError::Read)?;
    match value {
        Value::Mapping(map) => Ok(map),
        _ => Err(DoorError::Read),
    }
}

/// The `parse_properties` function reads a Java properties file into a map.
/// A line that ends in one backslash continues onto the next line. An empty line is skipped. A line that starts with `#` or `!` is skipped. A line with no separator is skipped.
fn parse_properties(text: &str) -> Mapping {
    let mut map = Mapping::new();
    let mut continued = String::new();
    for line in text.lines() {
        let piece = if continued.is_empty() {
            line.trim().to_string()
        } else {
            let mut joined = continued.clone();
            joined.push_str(line.trim());
            continued.clear();
            joined
        };
        if piece.ends_with('\\') && !piece.ends_with("\\\\") {
            continued = piece[..piece.len() - 1].to_string();
            continue;
        }
        if piece.is_empty() || piece.starts_with('#') || piece.starts_with('!') {
            continue;
        }
        let Some((key, value)) = split_property(&piece) else {
            continue;
        };
        put_str(&mut map, &key, &value);
    }
    map
}

/// The `split_property` function splits one properties line on `=` or `:`.
/// A backslash skips the next byte, so an escaped separator is not the split. The key and the value are trimmed. An empty key returns `None`. No separator returns `None`.
fn split_property(line: &str) -> Option<(String, String)> {
    let bytes = line.as_bytes();
    let mut i = 0;
    while i < bytes.len() {
        if bytes[i] == b'\\' {
            i += 2;
            continue;
        }
        if bytes[i] == b'=' || bytes[i] == b':' {
            let key = line[..i].trim().to_string();
            let value = line[i + 1..].trim().to_string();
            if key.is_empty() {
                return None;
            }
            return Some((key, value));
        }
        i += 1;
    }
    None
}

/// The `accounts_of` function reads the `accounts` sequence.
/// A missing key returns an empty list. A value that is not a sequence returns an empty list. A row without a user name is skipped. A blank user name is skipped. A missing password is empty.
fn accounts_of(map: &Mapping) -> Vec<Account> {
    let Some(Value::Sequence(rows)) = map.get(ACCOUNTS_KEY) else {
        return Vec::new();
    };
    let mut accounts = Vec::new();
    for row in rows {
        let Value::Mapping(row) = row else {
            continue;
        };
        let Some(username) = text_of(row.get("username")) else {
            continue;
        };
        let username = username.trim().to_string();
        if username.is_empty() {
            continue;
        }
        let password = text_of(row.get("password")).unwrap_or_default();
        accounts.push(Account { username, password });
    }
    accounts
}

/// The `touch_account` function updates one account row in the map.
/// An empty user name returns without a change. A matching row stores the password only when remember is on and the password is not empty. Otherwise the password key is removed from that row.
///
/// No matching row appends a new row. The new row stores a password only under that same rule. Rows that are not mappings are left in the sequence.
fn touch_account(map: &mut Mapping, user: &str, password: &str, remember: bool) {
    if user.is_empty() {
        return;
    }
    let mut rows = match map.get(ACCOUNTS_KEY).cloned() {
        Some(Value::Sequence(rows)) => rows,
        _ => Vec::new(),
    };
    let mut found = false;
    for row in &mut rows {
        let Value::Mapping(row_map) = row else {
            continue;
        };
        let Some(name) = text_of(row_map.get("username")) else {
            continue;
        };
        if name != user {
            continue;
        }
        found = true;
        if remember && !password.is_empty() {
            put_str(row_map, "password", password);
        } else {
            row_map.remove("password");
        }
        break;
    }
    if !found {
        let mut row = Mapping::new();
        put_str(&mut row, "username", user);
        if remember && !password.is_empty() {
            put_str(&mut row, "password", password);
        }
        rows.push(Value::Mapping(row));
    }
    map.insert(Value::String(ACCOUNTS_KEY.to_string()), Value::Sequence(rows));
}

/// The `parse_view_rows` function reads an open-card row count.
/// Swing `getAetherisViewRows` is the rule. Unreadable text returns 4. A count under 1 returns 1. Any other parsed integer is returned as that count.
fn parse_view_rows(text: &str) -> u32 {
    match text.trim().parse::<i32>() {
        Ok(value) if value < 1 => 1,
        Ok(value) => value as u32,
        Err(_) => VIEW_ROWS,
    }
}

/// The `parse_wrap_columns` function reads a wrap width.
/// Swing `getAetherisWrapColumns` is the rule. Unreadable text returns 80. A count under 1 returns 1. Any other parsed integer is returned as that count.
fn parse_wrap_columns(text: &str) -> u32 {
    match text.trim().parse::<i32>() {
        Ok(value) if value < 1 => 1,
        Ok(value) => value as u32,
        Err(_) => WRAP_COLUMNS,
    }
}

/// The `parse_line_chars` function reads an inspector line length.
/// Swing `getAetherisInspectorLineChars` is the rule. Unreadable text returns 80. A count under 1 returns 1. Any other parsed integer is returned as that count.
fn parse_line_chars(text: &str) -> u32 {
    match text.trim().parse::<i32>() {
        Ok(value) if value < 1 => 1,
        Ok(value) => value as u32,
        Err(_) => LINE_CHARS,
    }
}

/// The `pin_bar_flag` function reads a boolean the way Swing `Boolean.parseBoolean` does after trim.
/// Only the word `true`, ignoring ASCII case, returns true. Every other text returns false.
fn pin_bar_flag(text: &str) -> bool {
    text.trim().eq_ignore_ascii_case("true")
}

/// The `chooser_int` function parses an optional integer.
/// `None` returns `None`. Surrounding spaces are ignored. Text that is not an `i32` returns `None`.
fn chooser_int(text: Option<&str>) -> Option<i32> {
    text?.trim().parse().ok()
}

/// The `text_of` function reads a YAML scalar as text.
/// A string is copied. A number is printed with its YAML number text. A boolean becomes `true` or `false`. Any other value, including a missing key, returns `None`.
fn text_of(value: Option<&Value>) -> Option<String> {
    match value? {
        Value::String(text) => Some(text.clone()),
        Value::Number(number) => Some(number.to_string()),
        Value::Bool(flag) => Some(if *flag { "true" } else { "false" }.to_string()),
        _ => None,
    }
}

/// The `put_str` function stores one string under `key`.
/// An existing value for that key is replaced. Other keys stay.
fn put_str(map: &mut Mapping, key: &str, value: &str) {
    map.insert(
        Value::String(key.to_string()),
        Value::String(value.to_string()),
    );
}

/// The `dump_map` function writes the map as YAML text.
/// A leading `---` line from the serializer is removed. The text starts with `# Casserole standalone SSH client` and a newline. A serializer failure returns `Err`.
fn dump_map(map: &Mapping) -> Result<String, ()> {
    let body = serde_yaml::to_string(&Value::Mapping(map.clone())).map_err(|_| ())?;
    let body = body.trim_start_matches("---\n");
    Ok(format!("{HEADER}{body}"))
}

/// The `replace_file` function replaces `path` by writing a temporary file and renaming it.
/// The temporary name ends in `yaml.writing`. A failed rename deletes `path` and tries the rename again. A failed write returns `Err`. A second failed rename returns `Err`.
fn replace_file(path: &Path, bytes: &[u8]) -> Result<(), ()> {
    let tmp = path.with_extension("yaml.writing");
    fs::write(&tmp, bytes).map_err(|_| ())?;
    if fs::rename(&tmp, path).is_ok() {
        return Ok(());
    }
    let _ = fs::remove_file(path);
    fs::rename(&tmp, path).map_err(|_| ())
}

#[cfg(test)]
/// These functions check the SSH client settings file.
mod tests {
    use super::{Door, DoorError};
    use serde_yaml::{Mapping, Value};
    use std::fs;
    use std::sync::atomic::{AtomicU64, Ordering};

    static NEXT: AtomicU64 = AtomicU64::new(1);

    /// The `scratch` function creates a temporary directory for one test.
    /// An existing directory of that name is removed first. The name includes `name` and a counter.
    fn scratch(name: &str) -> std::path::PathBuf {
        let n = NEXT.fetch_add(1, Ordering::Relaxed);
        let dir = std::env::temp_dir().join(format!("aetheris-door-{name}-{n}"));
        let _ = fs::remove_dir_all(&dir);
        fs::create_dir_all(&dir).unwrap();
        dir
    }

    /// The `yaml_map` function parses `text` as a YAML map.
    /// A root that is not a map panics.
    fn yaml_map(text: &str) -> Mapping {
        match serde_yaml::from_str::<Value>(text).unwrap() {
            Value::Mapping(map) => map,
            other => panic!("yaml root {other:?}"),
        }
    }

    /// The `a_missing_file_stays_missing_until_save` test opens a missing YAML file.
    /// The file is still missing. The host is `localhost`. The port is 2222. Remember is off. The mode is `vt100`. The password is empty. The debug text does not contain `password:`.
    #[test]
    fn a_missing_file_stays_missing_until_save() {
        let dir = scratch("missing");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(!yaml.exists());
        assert_eq!(door.host(), "localhost");
        assert_eq!(door.port(), 2222);
        assert!(!door.remember());
        assert_eq!(door.connection_mode(), "vt100");
        assert!(door.password().is_empty());
        assert!(!format!("{door:?}").contains("password:"));
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `a_stored_port_including_22_wins_and_a_bad_port_does_not` test checks the port and the mode.
    /// A stored port of 22 is kept. A port of 70000 becomes 2222. The mode `pty` is kept in either case. The mode `nope` becomes `vt100`.
    #[test]
    fn a_stored_port_including_22_wins_and_a_bad_port_does_not() {
        let dir = scratch("port");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &yaml,
            "ui.connection.host: h\nui.connection.port: 22\nui.connection.username: ada\n",
        )
        .unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.port(), 22);
        fs::write(&yaml, "ui.connection.port: 70000\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.port(), 2222);
        fs::write(&yaml, "ui.connection.port: 2222\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.port(), 2222);
        assert_eq!(door.connection_mode(), "vt100");
        fs::write(&yaml, "ui.connection.mode: pty\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.connection_mode(), "pty");
        fs::write(&yaml, "ui.connection.mode: PTY\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.connection_mode(), "pty");
        fs::write(&yaml, "ui.connection.mode: nope\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.connection_mode(), "vt100");
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `remember_off_removes_the_password_key_and_keeps_unrelated_keys` test turns remember off.
    /// The password key is removed. The key `theme.extra` stays. The account note `stay` stays. The debug text contains `<redacted>` and does not contain the password.
    #[test]
    fn remember_off_removes_the_password_key_and_keeps_unrelated_keys() {
        let dir = scratch("round");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        let mut seed = Mapping::new();
        seed.insert(Value::String("theme.extra".into()), Value::String("kept".into()));
        seed.insert(Value::String("ui.connection.host".into()), Value::String("h".into()));
        seed.insert(Value::String("ui.connection.port".into()), Value::String("2222".into()));
        seed.insert(Value::String("ui.connection.username".into()), Value::String("ada".into()));
        seed.insert(
            Value::String("ui.connection.rememberPassword".into()),
            Value::String("true".into()),
        );
        seed.insert(
            Value::String("ui.connection.password".into()),
            Value::String("s3cret-token".into()),
        );
        let mut bob = Mapping::new();
        bob.insert(Value::String("username".into()), Value::String("bob".into()));
        bob.insert(Value::String("note".into()), Value::String("stay".into()));
        seed.insert(Value::String("accounts".into()), Value::Sequence(vec![Value::Mapping(bob)]));
        fs::write(&yaml, serde_yaml::to_string(&Value::Mapping(seed)).unwrap()).unwrap();

        let mut door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.password().len(), "s3cret-token".len());
        assert!(format!("{door:?}").contains("<redacted>"));
        assert!(!format!("{door:?}").contains("s3cret-token"));
        door.set_remember(false);
        door.save().unwrap();

        let text = fs::read_to_string(&yaml).unwrap();
        let map = yaml_map(&text);
        assert!(map.get("ui.connection.password").is_none());
        assert_eq!(
            map.get("theme.extra").and_then(|v| v.as_str()),
            Some("kept")
        );
        let rows = map.get("accounts").and_then(|v| v.as_sequence()).unwrap();
        assert!(rows.len() >= 2);
        let bob = rows.iter().find(|row| {
            row.as_mapping()
                .and_then(|m| m.get("username"))
                .and_then(|v| v.as_str())
                == Some("bob")
        });
        assert!(bob.is_some());
        assert_eq!(
            bob.unwrap().as_mapping().unwrap().get("note").and_then(|v| v.as_str()),
            Some("stay")
        );
        let ada = rows.iter().find(|row| {
            row.as_mapping()
                .and_then(|m| m.get("username"))
                .and_then(|v| v.as_str())
                == Some("ada")
        });
        assert!(ada.unwrap().as_mapping().unwrap().get("password").is_none());
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `remember_on_writes_the_password_key_as_text` test saves with remember on.
    /// The password key is present. Remember is the text `true`. The port is the text `2222`.
    #[test]
    fn remember_on_writes_the_password_key_as_text() {
        let dir = scratch("on");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        let mut door = Door::open(&yaml, &legacy).unwrap();
        door.set_host("h".into());
        door.set_username("ada".into());
        door.set_remember(true);
        door.set_password("s3cret-token".into());
        door.save().unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert!(map.get("ui.connection.password").is_some());
        assert_eq!(
            map.get("ui.connection.rememberPassword").and_then(|v| v.as_str()),
            Some("true")
        );
        assert_eq!(
            map.get("ui.connection.port").and_then(|v| v.as_str()),
            Some("2222")
        );
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `a_bad_yaml_is_left_in_place` test opens a file whose text is `[`.
    /// The error is [`DoorError::Read`]. The file bytes stay `[` and a newline.
    #[test]
    fn a_bad_yaml_is_left_in_place() {
        let dir = scratch("bad");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        let original = b"[\n";
        fs::write(&yaml, original).unwrap();
        let err = Door::open(&yaml, &legacy).unwrap_err();
        assert_eq!(err, DoorError::Read);
        assert_eq!(fs::read(&yaml).unwrap(), original);
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `legacy_properties_import_once_and_the_legacy_file_stays` test fills a missing YAML file from a properties file.
    /// The host is `legacy-host`. The port is 22. Both files exist afterward. The password key is absent.
    #[test]
    fn legacy_properties_import_once_and_the_legacy_file_stays() {
        let dir = scratch("legacy");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &legacy,
            "ui.connection.host=legacy-host\nui.connection.port=22\nui.connection.username=ada\nui.connection.rememberPassword=false\n",
        )
        .unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.host(), "legacy-host");
        assert_eq!(door.port(), 22);
        assert!(yaml.exists());
        assert!(legacy.exists());
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert!(map.get("ui.connection.password").is_none());
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `a_number_or_bool_is_accepted_on_read` test reads a numeric port and a bool remember flag.
    /// The port is 22. Remember is on.
    #[test]
    fn a_number_or_bool_is_accepted_on_read() {
        let dir = scratch("scalar");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &yaml,
            "ui.connection.port: 22\nui.connection.rememberPassword: true\nui.connection.username: ada\n",
        )
        .unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.port(), 22);
        assert!(door.remember());
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `the_divider_per_mille_stays_with_the_other_keys` test stores the inspector divider.
    /// A stored 250 is read. Saving 720 writes the text `720` and keeps the other keys. Saving the same value does not rewrite the file. A value of 1500 is read back as 1000. A value of -3 is missing. A missing key is missing.
    #[test]
    fn the_divider_per_mille_stays_with_the_other_keys() {
        let dir = scratch("divider");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &yaml,
            "theme.extra: kept\nui.connection.host: h\nui.connection.password: kept\nui.aetheris.inspector.divider: 250\n",
        )
        .unwrap();
        let mut door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.inspector_divider(), Some(250));
        door.save_inspector_divider(720).unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert_eq!(
            map.get("ui.aetheris.inspector.divider").and_then(|v| v.as_str()),
            Some("720")
        );
        assert_eq!(map.get("theme.extra").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(map.get("ui.connection.host").and_then(|v| v.as_str()), Some("h"));
        assert_eq!(
            map.get("ui.connection.password").and_then(|v| v.as_str()),
            Some("kept")
        );
        let bytes = fs::read(&yaml).unwrap();
        door.save_inspector_divider(720).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        door.save_inspector_divider(1500).unwrap();
        assert_eq!(door.inspector_divider(), Some(1000));
        fs::write(&yaml, "ui.aetheris.inspector.divider: -3\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.inspector_divider(), None);
        fs::write(&yaml, "ui.aetheris.inspector.divider: 1500\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.inspector_divider(), Some(1000));
        fs::write(&yaml, "theme.extra: kept\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.inspector_divider(), None);
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `the_wrap_columns_stay_with_the_other_keys` test stores the wrap width.
    /// A missing key reads as 80. Saving 36 is read back as 36. The word `nope` reads as 80. A value of -2 reads as 1. A value of 0 reads as 1. The other keys stay.
    #[test]
    fn the_wrap_columns_stay_with_the_other_keys() {
        let dir = scratch("wrap");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(&yaml, "theme.extra: kept\nui.connection.password: kept\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.wrap_columns(), 80);
        let mut door = door;
        door.save_wrap_columns(80).unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert_eq!(
            map.get("ui.aetheris.inspector.wrap.columns").and_then(|v| v.as_str()),
            Some("80")
        );
        assert_eq!(map.get("theme.extra").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(
            map.get("ui.connection.password").and_then(|v| v.as_str()),
            Some("kept")
        );
        assert!(map.get("ui.aetheris.inspector.softwrap").is_none());
        let bytes = fs::read(&yaml).unwrap();
        door.save_wrap_columns(80).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        door.save_wrap_columns(36).unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.wrap_columns(), 36);
        fs::write(&yaml, "ui.aetheris.inspector.wrap.columns: nope\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.wrap_columns(), 80);
        fs::write(&yaml, "ui.aetheris.inspector.wrap.columns: -2\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.wrap_columns(), 1);
        fs::write(&yaml, "ui.aetheris.inspector.wrap.columns: 0\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.wrap_columns(), 1);
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `the_line_chars_stay_with_the_other_keys` test stores the inspector line length.
    /// A missing key reads as 80. Saving 24 is read back as 24. The wrap width and the view rows stay. The word `nope` reads as 80. A value of 0 reads as 1.
    #[test]
    fn the_line_chars_stay_with_the_other_keys() {
        let dir = scratch("line");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &yaml,
            "theme.extra: kept\nui.connection.password: kept\nui.aetheris.inspector.wrap.columns: 36\nui.aetheris.view.rows: 6\n",
        )
        .unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.line_chars(), 80);
        assert_eq!(door.wrap_columns(), 36);
        assert_eq!(door.view_rows(), 6);
        let mut door = door;
        door.save_line_chars(80).unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert_eq!(
            map.get("ui.aetheris.inspector.line.chars")
                .and_then(|v| v.as_str()),
            Some("80")
        );
        assert_eq!(map.get("theme.extra").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(
            map.get("ui.connection.password").and_then(|v| v.as_str()),
            Some("kept")
        );
        let wrap = map
            .get("ui.aetheris.inspector.wrap.columns")
            .map(|value| match value {
                Value::String(text) => text.clone(),
                Value::Number(number) => number.to_string(),
                _ => String::new(),
            });
        assert_eq!(wrap.as_deref(), Some("36"));
        let bytes = fs::read(&yaml).unwrap();
        door.save_line_chars(80).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        door.save_line_chars(24).unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.line_chars(), 24);
        assert_eq!(door.wrap_columns(), 36);
        assert_eq!(door.view_rows(), 6);
        fs::write(&yaml, "ui.aetheris.inspector.line.chars: 36\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.line_chars(), 36);
        fs::write(&yaml, "ui.aetheris.inspector.line.chars: nope\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.line_chars(), 80);
        fs::write(&yaml, "ui.aetheris.inspector.line.chars: -2\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.line_chars(), 1);
        fs::write(&yaml, "ui.aetheris.inspector.line.chars: 0\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.line_chars(), 1);
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `the_view_rows_stay_with_the_other_keys` test stores the open-card row count.
    /// A missing key reads as 4. Saving 6 is read back as 6. The wrap width stays. The word `nope` reads as 4. A value of 0 reads as 1.
    #[test]
    fn the_view_rows_stay_with_the_other_keys() {
        let dir = scratch("rows");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &yaml,
            "theme.extra: kept\nui.connection.password: kept\nui.aetheris.inspector.wrap.columns: 36\n",
        )
        .unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.view_rows(), 4);
        assert_eq!(door.wrap_columns(), 36);
        let mut door = door;
        door.save_view_rows(4).unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert_eq!(
            map.get("ui.aetheris.view.rows").and_then(|v| v.as_str()),
            Some("4")
        );
        assert_eq!(map.get("theme.extra").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(
            map.get("ui.connection.password").and_then(|v| v.as_str()),
            Some("kept")
        );
        let wrap = map.get("ui.aetheris.inspector.wrap.columns").map(|value| match value {
            Value::String(text) => text.clone(),
            Value::Number(number) => number.to_string(),
            _ => String::new(),
        });
        assert_eq!(wrap.as_deref(), Some("36"));
        let bytes = fs::read(&yaml).unwrap();
        door.save_view_rows(4).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        door.save_view_rows(6).unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.view_rows(), 6);
        assert_eq!(door.wrap_columns(), 36);
        fs::write(&yaml, "ui.aetheris.view.rows: nope\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.view_rows(), 4);
        fs::write(&yaml, "ui.aetheris.view.rows: -2\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.view_rows(), 1);
        fs::write(&yaml, "ui.aetheris.view.rows: 0\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.view_rows(), 1);
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `the_product_tab_stays_with_the_other_keys` test stores the product tab.
    /// A missing key is not the Aetheris tab. Saving true writes `aetheris`. Saving false writes `ssh`. The text ` aetheris ` is the Aetheris tab. The text `Aetheris` is not. A memory-only save returns [`DoorError::Write`] and the flag stays off.
    #[test]
    fn the_product_tab_stays_with_the_other_keys() {
        let dir = scratch("tab");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &yaml,
            "theme.extra: kept\nui.connection.password: kept\nui.aetheris.inspector.divider: 250\nui.aetheris.inspector.wrap.columns: 36\n",
        )
        .unwrap();
        let mut door = Door::open(&yaml, &legacy).unwrap();
        assert!(!door.product_tab_is_aetheris());
        door.save_product_tab(true).unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert_eq!(map.get("ui.product.tab").and_then(|v| v.as_str()), Some("aetheris"));
        assert_eq!(map.get("theme.extra").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(
            map.get("ui.connection.password").and_then(|v| v.as_str()),
            Some("kept")
        );
        assert!(map.get("ui.aetheris.inspector.divider").is_some());
        assert!(map.get("ui.aetheris.inspector.wrap.columns").is_some());
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(door.product_tab_is_aetheris());
        assert_eq!(door.inspector_divider(), Some(250));
        assert_eq!(door.wrap_columns(), 36);
        let mut door = door;
        let bytes = fs::read(&yaml).unwrap();
        door.save_product_tab(true).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        door.save_product_tab(false).unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert_eq!(map.get("ui.product.tab").and_then(|v| v.as_str()), Some("ssh"));
        assert_eq!(map.get("theme.extra").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(
            map.get("ui.connection.password").and_then(|v| v.as_str()),
            Some("kept")
        );
        fs::write(&yaml, "ui.product.tab: aetheris\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(door.product_tab_is_aetheris());
        fs::write(&yaml, "ui.product.tab: nope\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(!door.product_tab_is_aetheris());
        fs::write(&yaml, "ui.product.tab: Aetheris\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(!door.product_tab_is_aetheris());
        fs::write(&yaml, "ui.product.tab: \" aetheris \"\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(door.product_tab_is_aetheris());
        let mut memory = Door::memory_only();
        assert!(!memory.product_tab_is_aetheris());
        assert_eq!(memory.save_product_tab(true), Err(DoorError::Write));
        assert!(!memory.product_tab_is_aetheris());
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `the_pin_bar_starts_folded` test stores the pin-bar flag.
    /// A missing key is folded. The text `TRUE` and ` true ` are open. The word `nope` is folded. A memory-only save returns [`DoorError::Write`].
    #[test]
    fn the_pin_bar_starts_folded() {
        let dir = scratch("pin-bar");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &yaml,
            "theme.extra: kept\nui.connection.password: kept\nui.product.tab: aetheris\n",
        )
        .unwrap();
        let mut door = Door::open(&yaml, &legacy).unwrap();
        assert!(!door.pin_bar_open());
        door.save_pin_bar_open(false).unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert_eq!(
            map.get("ui.aetheris.pin.bar.open").and_then(|v| v.as_str()),
            Some("false")
        );
        assert_eq!(map.get("theme.extra").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(
            map.get("ui.connection.password").and_then(|v| v.as_str()),
            Some("kept")
        );
        assert_eq!(map.get("ui.product.tab").and_then(|v| v.as_str()), Some("aetheris"));
        let bytes = fs::read(&yaml).unwrap();
        door.save_pin_bar_open(false).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        door.save_pin_bar_open(true).unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(door.pin_bar_open());
        assert!(door.product_tab_is_aetheris());
        let mut door = door;
        let bytes = fs::read(&yaml).unwrap();
        door.save_pin_bar_open(true).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        door.save_pin_bar_open(false).unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(!door.pin_bar_open());
        fs::write(&yaml, "ui.aetheris.pin.bar.open: TRUE\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(door.pin_bar_open());
        fs::write(&yaml, "ui.aetheris.pin.bar.open: true\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(door.pin_bar_open());
        fs::write(&yaml, "ui.aetheris.pin.bar.open: \" true \"\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(door.pin_bar_open());
        fs::write(&yaml, "ui.aetheris.pin.bar.open: false\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(!door.pin_bar_open());
        fs::write(&yaml, "ui.aetheris.pin.bar.open: nope\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(!door.pin_bar_open());
        fs::write(&yaml, "theme.extra: kept\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert!(!door.pin_bar_open());
        let mut memory = Door::memory_only();
        assert!(!memory.pin_bar_open());
        assert_eq!(memory.save_pin_bar_open(true), Err(DoorError::Write));
        assert!(!memory.pin_bar_open());
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `the_chooser_memory_stays_with_the_other_keys` test stores the chooser bounds and directory.
    /// A width of 0 or a bad width returns no bounds. A repeated save of the same bounds does not rewrite the file. A memory-only save returns [`DoorError::Write`].
    #[test]
    fn the_chooser_memory_stays_with_the_other_keys() {
        let dir = scratch("chooser");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &yaml,
            "theme.extra: kept\nui.connection.password: kept\nui.aetheris.inspector.divider: 250\nui.chooser.width: 400\nui.chooser.height: 300\n",
        )
        .unwrap();
        let mut door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.chooser_bounds(), Some((0, 0, 400, 300)));
        assert_eq!(door.chooser_directory(), "");
        door.save_chooser_bounds(0, 0, 400, 300).unwrap();
        let bytes = fs::read(&yaml).unwrap();
        door.save_chooser_bounds(0, 0, 400, 300).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        door.save_chooser_bounds(12, -4, 480, 320).unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert_eq!(map.get("ui.chooser.x").and_then(|v| v.as_str()), Some("12"));
        assert_eq!(map.get("ui.chooser.y").and_then(|v| v.as_str()), Some("-4"));
        assert_eq!(map.get("ui.chooser.width").and_then(|v| v.as_str()), Some("480"));
        assert_eq!(
            map.get("ui.chooser.height").and_then(|v| v.as_str()),
            Some("320")
        );
        assert_eq!(map.get("theme.extra").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(
            map.get("ui.connection.password").and_then(|v| v.as_str()),
            Some("kept")
        );
        assert!(map.get("ui.aetheris.inspector.divider").is_some());
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.chooser_bounds(), Some((12, -4, 480, 320)));
        assert_eq!(door.inspector_divider(), Some(250));
        fs::write(&yaml, "ui.chooser.width: 0\nui.chooser.height: 300\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.chooser_bounds(), None);
        fs::write(&yaml, "ui.chooser.width: nope\nui.chooser.height: 300\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.chooser_bounds(), None);
        fs::write(&yaml, "ui.chooser.height: 300\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.chooser_bounds(), None);
        let mut door = Door::open(&yaml, &legacy).unwrap();
        door.save_chooser_directory("").unwrap();
        assert_eq!(door.chooser_directory(), "");
        let folder = dir.join("papers");
        fs::create_dir_all(&folder).unwrap();
        let text = folder.to_str().unwrap();
        door.save_chooser_directory(text).unwrap();
        let bytes = fs::read(&yaml).unwrap();
        door.save_chooser_directory(text).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.chooser_directory(), text);
        assert_eq!(door.chooser_bounds(), None);
        let mut memory = Door::memory_only();
        assert_eq!(memory.chooser_bounds(), None);
        assert_eq!(memory.chooser_directory(), "");
        assert_eq!(memory.save_chooser_bounds(1, 2, 240, 180), Err(DoorError::Write));
        assert_eq!(memory.chooser_bounds(), None);
        assert_eq!(memory.save_chooser_directory(text), Err(DoorError::Write));
        assert_eq!(memory.chooser_directory(), "");
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `the_shell_font_and_frame_stay_with_the_other_keys` test stores the font size and the frame.
    /// A missing font reads as 14. A stored 99 reads as 36. The word `nope` reads as 14. A partial frame fills the missing size with 1024 by 968. A memory-only save returns [`DoorError::Write`].
    #[test]
    fn the_shell_font_and_frame_stay_with_the_other_keys() {
        let dir = scratch("shell-frame");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &yaml,
            "theme.extra: kept\nui.connection.password: kept\nui.chooser.directory: C:/papers\n",
        )
        .unwrap();
        let mut door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.font_size(), 14);
        assert_eq!(door.frame_bounds(), None);
        assert_eq!(door.frame_maximized_word(), None);
        door.save_font_size(8).unwrap();
        let bytes = fs::read(&yaml).unwrap();
        door.save_font_size(8).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        door.save_font_size(14).unwrap();
        door.save_frame(Some((48, 64, 860, 660)), true).unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert_eq!(map.get("ui.terminal.font.size").and_then(|v| v.as_str()), Some("14"));
        assert_eq!(map.get("ui.frame.x").and_then(|v| v.as_str()), Some("48"));
        assert_eq!(map.get("ui.frame.y").and_then(|v| v.as_str()), Some("64"));
        assert_eq!(map.get("ui.frame.width").and_then(|v| v.as_str()), Some("860"));
        assert_eq!(map.get("ui.frame.height").and_then(|v| v.as_str()), Some("660"));
        assert_eq!(map.get("ui.frame.maximized").and_then(|v| v.as_str()), Some("true"));
        assert_eq!(map.get("theme.extra").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(map.get("ui.connection.password").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(map.get("ui.chooser.directory").and_then(|v| v.as_str()), Some("C:/papers"));
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.font_size(), 14);
        assert_eq!(door.frame_bounds(), Some((48, 64, 860, 660)));
        assert_eq!(door.frame_maximized_word(), Some(true));
        fs::write(&yaml, "ui.terminal.font.size: 99\nui.frame.x: 1\nui.frame.y: 2\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.font_size(), 36);
        assert_eq!(door.frame_bounds(), Some((1, 2, 1024, 968)));
        fs::write(&yaml, "ui.terminal.font.size: nope\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.font_size(), 14);
        let mut memory = Door::memory_only();
        assert_eq!(memory.font_size(), 14);
        assert_eq!(memory.save_font_size(12), Err(DoorError::Write));
        assert_eq!(memory.font_size(), 14);
        assert_eq!(memory.save_frame(Some((1, 2, 400, 300)), false), Err(DoorError::Write));
        assert_eq!(memory.frame_bounds(), None);
        let _ = fs::remove_dir_all(&dir);
    }

    /// The `the_exit_position_stays_with_the_other_keys` test stores the exit position.
    /// A missing pair reads as none. Saving 90 and 120 is read back. A file with only `ui.exit.x` reads as none. A memory-only save returns [`DoorError::Write`].
    #[test]
    fn the_exit_position_stays_with_the_other_keys() {
        let dir = scratch("exit-pos");
        let yaml = dir.join("ssh-client.yaml");
        let legacy = dir.join("legacy.properties");
        fs::write(
            &yaml,
            "theme.extra: kept\nui.connection.password: kept\nui.frame.x: \"12\"\n",
        )
        .unwrap();
        let mut door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.exit_pos(), None);
        door.save_exit_pos(40, 80).unwrap();
        let bytes = fs::read(&yaml).unwrap();
        door.save_exit_pos(40, 80).unwrap();
        assert_eq!(fs::read(&yaml).unwrap(), bytes);
        door.save_exit_pos(90, 120).unwrap();
        let map = yaml_map(&fs::read_to_string(&yaml).unwrap());
        assert_eq!(map.get("ui.exit.x").and_then(|v| v.as_str()), Some("90"));
        assert_eq!(map.get("ui.exit.y").and_then(|v| v.as_str()), Some("120"));
        assert_eq!(map.get("theme.extra").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(map.get("ui.connection.password").and_then(|v| v.as_str()), Some("kept"));
        assert_eq!(map.get("ui.frame.x").and_then(|v| v.as_str()), Some("12"));
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.exit_pos(), Some((90, 120)));
        fs::write(&yaml, "ui.exit.x: 4\n").unwrap();
        let door = Door::open(&yaml, &legacy).unwrap();
        assert_eq!(door.exit_pos(), None);
        let mut memory = Door::memory_only();
        assert_eq!(memory.save_exit_pos(1, 2), Err(DoorError::Write));
        assert_eq!(memory.exit_pos(), None);
        let _ = fs::remove_dir_all(&dir);
    }
}
