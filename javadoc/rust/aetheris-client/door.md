# Module door

[Rust tree](../index.md) · **aetheris-client**

**Source** `rust/aetheris/aetheris-client/src/door.rs`

The [`Door`](door/struct.Door.md) type reads and writes the SSH client settings file.

The file is `ssh-client.yaml` under `.casserole` in the home directory. Unknown keys stay in the stored map. A password is written only when the remember flag is on. A failed parse does not overwrite the file. `chrome::run` loads the home path through [`Door::load_home`](door/struct.Door.md#method-load_home). Tests pass temporary paths to [`Door::open`](door/struct.Door.md#method-open).

A missing YAML file can be filled once from `.ssh-client.properties` in the home directory. That older file is left in place. The imported map is written to the YAML path.

| | |
| --- | --- |
| **Specification** | L37 rust-aetheris-client.draft.001.md |
| **Specification** | L40 aa-sdd-source-prose.md#RustSshDoor |
| **Author** | clayl (2026-10-23) |

## Type Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| enum | [`DoorError`](door/enum.DoorError.md) | The [`DoorError`] enum is why a [`Door`] read or write stopped. |
| struct | [`Account`](door/struct.Account.md) | The `Account` struct is one stored user name and password. |
| struct | [`Door`](door/struct.Door.md) | The [`Door`] type is the SSH client settings store. |

## Constant Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| const | [`HOST_KEY`](#constant-host_key) |  |
| const | [`PORT_KEY`](#constant-port_key) |  |
| const | [`USER_KEY`](#constant-user_key) |  |
| const | [`PASS_KEY`](#constant-pass_key) |  |
| const | [`REMEMBER_KEY`](#constant-remember_key) |  |
| const | [`ACCOUNTS_KEY`](#constant-accounts_key) |  |
| const | [`DIVIDER_KEY`](#constant-divider_key) |  |
| const | [`WRAP_KEY`](#constant-wrap_key) |  |
| const | [`LINE_CHARS_KEY`](#constant-line_chars_key) |  |
| const | [`VIEW_ROWS_KEY`](#constant-view_rows_key) |  |
| const | [`PRODUCT_TAB_KEY`](#constant-product_tab_key) |  |
| const | [`PRODUCT_TAB_SSH`](#constant-product_tab_ssh) |  |
| const | [`PRODUCT_TAB_AETHERIS`](#constant-product_tab_aetheris) |  |
| const | [`PIN_BAR_KEY`](#constant-pin_bar_key) |  |
| const | [`CHOOSER_X_KEY`](#constant-chooser_x_key) |  |
| const | [`CHOOSER_Y_KEY`](#constant-chooser_y_key) |  |
| const | [`CHOOSER_W_KEY`](#constant-chooser_w_key) |  |
| const | [`CHOOSER_H_KEY`](#constant-chooser_h_key) |  |
| const | [`CHOOSER_DIR_KEY`](#constant-chooser_dir_key) |  |
| const | [`FONT_KEY`](#constant-font_key) |  |
| const | [`MODE_KEY`](#constant-mode_key) |  |
| const | [`FRAME_X_KEY`](#constant-frame_x_key) |  |
| const | [`FRAME_Y_KEY`](#constant-frame_y_key) |  |
| const | [`FRAME_W_KEY`](#constant-frame_w_key) |  |
| const | [`FRAME_H_KEY`](#constant-frame_h_key) |  |
| const | [`FRAME_MAX_KEY`](#constant-frame_max_key) |  |
| const | [`EXIT_X_KEY`](#constant-exit_x_key) |  |
| const | [`EXIT_Y_KEY`](#constant-exit_y_key) |  |
| const | [`FONT_DEFAULT`](#constant-font_default) | `FONT_DEFAULT` is the font size used when the font key is missing or unreadable. |
| const | [`FONT_MIN`](#constant-font_min) |  |
| const | [`FONT_MAX`](#constant-font_max) |  |
| const | [`FRAME_DEFAULT_W`](#constant-frame_default_w) |  |
| const | [`FRAME_DEFAULT_H`](#constant-frame_default_h) |  |
| const | [`HEADER`](#constant-header) |  |
| const | [`WRAP_COLUMNS`](#constant-wrap_columns) | `WRAP_COLUMNS` is the wrap width used when that key is missing. |
| const | [`LINE_CHARS`](#constant-line_chars) | `LINE_CHARS` is the inspector line length used when that key is missing. |
| const | [`VIEW_ROWS`](#constant-view_rows) | `VIEW_ROWS` is the open-card row count used when that key is missing. |

## Function Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`home_paths`](#function-home_paths) | The `home_paths` function returns the YAML path and the legacy properties path. |
| fn | [`os_user`](#function-os_user) | The `os_user` function returns the operating-system user name. |
| fn | [`parse_port`](#function-parse_port) | The `parse_port` function reads a port from text. |
| fn | [`parse_yaml`](#function-parse_yaml) | The `parse_yaml` function reads a YAML mapping. |
| fn | [`parse_properties`](#function-parse_properties) | The `parse_properties` function reads a Java properties file into a map. |
| fn | [`split_property`](#function-split_property) | The `split_property` function splits one properties line on `=` or `:`. |
| fn | [`accounts_of`](#function-accounts_of) | The `accounts_of` function reads the `accounts` sequence. |
| fn | [`touch_account`](#function-touch_account) | The `touch_account` function updates one account row in the map. |
| fn | [`parse_view_rows`](#function-parse_view_rows) | The `parse_view_rows` function reads an open-card row count. |
| fn | [`parse_wrap_columns`](#function-parse_wrap_columns) | The `parse_wrap_columns` function reads a wrap width. |
| fn | [`parse_line_chars`](#function-parse_line_chars) | The `parse_line_chars` function reads an inspector line length. |
| fn | [`pin_bar_flag`](#function-pin_bar_flag) | The `pin_bar_flag` function reads a boolean the way Swing `Boolean.parseBoolean` does after trim. |
| fn | [`chooser_int`](#function-chooser_int) | The `chooser_int` function parses an optional integer. |
| fn | [`text_of`](#function-text_of) | The `text_of` function reads a YAML scalar as text. |
| fn | [`put_str`](#function-put_str) | The `put_str` function stores one string under `key`. |
| fn | [`dump_map`](#function-dump_map) | The `dump_map` function writes the map as YAML text. |
| fn | [`replace_file`](#function-replace_file) | The `replace_file` function replaces `path` by writing a temporary file and renaming it. |

## Constant Detail

### HOST_KEY

<a id="constant-host_key"></a>

```rust
const HOST_KEY: &str = "ui.connection.host";
```

### PORT_KEY

<a id="constant-port_key"></a>

```rust
const PORT_KEY: &str = "ui.connection.port";
```

### USER_KEY

<a id="constant-user_key"></a>

```rust
const USER_KEY: &str = "ui.connection.username";
```

### PASS_KEY

<a id="constant-pass_key"></a>

```rust
const PASS_KEY: &str = "ui.connection.password";
```

### REMEMBER_KEY

<a id="constant-remember_key"></a>

```rust
const REMEMBER_KEY: &str = "ui.connection.rememberPassword";
```

### ACCOUNTS_KEY

<a id="constant-accounts_key"></a>

```rust
const ACCOUNTS_KEY: &str = "accounts";
```

### DIVIDER_KEY

<a id="constant-divider_key"></a>

```rust
const DIVIDER_KEY: &str = "ui.aetheris.inspector.divider";
```

### WRAP_KEY

<a id="constant-wrap_key"></a>

```rust
const WRAP_KEY: &str = "ui.aetheris.inspector.wrap.columns";
```

### LINE_CHARS_KEY

<a id="constant-line_chars_key"></a>

```rust
const LINE_CHARS_KEY: &str = "ui.aetheris.inspector.line.chars";
```

### VIEW_ROWS_KEY

<a id="constant-view_rows_key"></a>

```rust
const VIEW_ROWS_KEY: &str = "ui.aetheris.view.rows";
```

### PRODUCT_TAB_KEY

<a id="constant-product_tab_key"></a>

```rust
const PRODUCT_TAB_KEY: &str = "ui.product.tab";
```

### PRODUCT_TAB_SSH

<a id="constant-product_tab_ssh"></a>

```rust
const PRODUCT_TAB_SSH: &str = "ssh";
```

### PRODUCT_TAB_AETHERIS

<a id="constant-product_tab_aetheris"></a>

```rust
const PRODUCT_TAB_AETHERIS: &str = "aetheris";
```

### PIN_BAR_KEY

<a id="constant-pin_bar_key"></a>

```rust
const PIN_BAR_KEY: &str = "ui.aetheris.pin.bar.open";
```

### CHOOSER_X_KEY

<a id="constant-chooser_x_key"></a>

```rust
const CHOOSER_X_KEY: &str = "ui.chooser.x";
```

### CHOOSER_Y_KEY

<a id="constant-chooser_y_key"></a>

```rust
const CHOOSER_Y_KEY: &str = "ui.chooser.y";
```

### CHOOSER_W_KEY

<a id="constant-chooser_w_key"></a>

```rust
const CHOOSER_W_KEY: &str = "ui.chooser.width";
```

### CHOOSER_H_KEY

<a id="constant-chooser_h_key"></a>

```rust
const CHOOSER_H_KEY: &str = "ui.chooser.height";
```

### CHOOSER_DIR_KEY

<a id="constant-chooser_dir_key"></a>

```rust
const CHOOSER_DIR_KEY: &str = "ui.chooser.directory";
```

### FONT_KEY

<a id="constant-font_key"></a>

```rust
const FONT_KEY: &str = "ui.terminal.font.size";
```

### MODE_KEY

<a id="constant-mode_key"></a>

```rust
const MODE_KEY: &str = "ui.connection.mode";
```

### FRAME_X_KEY

<a id="constant-frame_x_key"></a>

```rust
const FRAME_X_KEY: &str = "ui.frame.x";
```

### FRAME_Y_KEY

<a id="constant-frame_y_key"></a>

```rust
const FRAME_Y_KEY: &str = "ui.frame.y";
```

### FRAME_W_KEY

<a id="constant-frame_w_key"></a>

```rust
const FRAME_W_KEY: &str = "ui.frame.width";
```

### FRAME_H_KEY

<a id="constant-frame_h_key"></a>

```rust
const FRAME_H_KEY: &str = "ui.frame.height";
```

### FRAME_MAX_KEY

<a id="constant-frame_max_key"></a>

```rust
const FRAME_MAX_KEY: &str = "ui.frame.maximized";
```

### EXIT_X_KEY

<a id="constant-exit_x_key"></a>

```rust
const EXIT_X_KEY: &str = "ui.exit.x";
```

### EXIT_Y_KEY

<a id="constant-exit_y_key"></a>

```rust
const EXIT_Y_KEY: &str = "ui.exit.y";
```

### FONT_DEFAULT

<a id="constant-font_default"></a>

```rust
const FONT_DEFAULT: i32 = 14;
```

`FONT_DEFAULT` is the font size used when the font key is missing or unreadable.
The value is 14. A stored size that parses stays as written, then `font_size` clamps it.

### FONT_MIN

<a id="constant-font_min"></a>

```rust
const FONT_MIN: i32 = 8;
```

### FONT_MAX

<a id="constant-font_max"></a>

```rust
const FONT_MAX: i32 = 36;
```

### FRAME_DEFAULT_W

<a id="constant-frame_default_w"></a>

```rust
const FRAME_DEFAULT_W: i32 = 1024;
```

### FRAME_DEFAULT_H

<a id="constant-frame_default_h"></a>

```rust
const FRAME_DEFAULT_H: i32 = 968;
```

### HEADER

<a id="constant-header"></a>

```rust
const HEADER: &str = "# Casserole standalone SSH client\n";
```

### WRAP_COLUMNS

<a id="constant-wrap_columns"></a>

```rust
const WRAP_COLUMNS: u32 = 80;
```

`WRAP_COLUMNS` is the wrap width used when that key is missing.
The value is 80. Swing `getAetherisWrapColumns` uses the same number.

### LINE_CHARS

<a id="constant-line_chars"></a>

```rust
const LINE_CHARS: u32 = 80;
```

`LINE_CHARS` is the inspector line length used when that key is missing.
The value is 80. Swing `getAetherisInspectorLineChars` uses the same number.

### VIEW_ROWS

<a id="constant-view_rows"></a>

```rust
const VIEW_ROWS: u32 = 4;
```

`VIEW_ROWS` is the open-card row count used when that key is missing.
The value is 4. Swing `getAetherisViewRows` uses the same number.

## Function Detail

### home_paths

<a id="function-home_paths"></a>

```rust
fn home_paths() -> Option<(PathBuf, PathBuf)>
```

The `home_paths` function returns the YAML path and the legacy properties path.
The home directory is `USERPROFILE` or else `HOME`. The YAML path is `.casserole/ssh-client.yaml` under that directory. The legacy path is `.ssh-client.properties` under that directory. No home directory returns `None`.

### os_user

<a id="function-os_user"></a>

```rust
fn os_user() -> String
```

The `os_user` function returns the operating-system user name.
`USERNAME` is tried first. `USER` is tried next. Both missing returns `user`.

### parse_port

<a id="function-parse_port"></a>

```rust
fn parse_port(text: &str) -> Option<u16>
```

The `parse_port` function reads a port from text.
Surrounding spaces are ignored. A whole number from 1 through 65535 returns `Some`. Any other text returns `None`.

### parse_yaml

<a id="function-parse_yaml"></a>

```rust
fn parse_yaml(text: &str) -> Result<Mapping, DoorError>
```

The `parse_yaml` function reads a YAML mapping.
A parse failure returns [`DoorError::Read`](door/enum.DoorError.md#member-read). A YAML value that is not a mapping returns [`DoorError::Read`](door/enum.DoorError.md#member-read).

### parse_properties

<a id="function-parse_properties"></a>

```rust
fn parse_properties(text: &str) -> Mapping
```

The `parse_properties` function reads a Java properties file into a map.
A line that ends in one backslash continues onto the next line. An empty line is skipped. A line that starts with `#` or `!` is skipped. A line with no separator is skipped.

### split_property

<a id="function-split_property"></a>

```rust
fn split_property(line: &str) -> Option<(String, String)>
```

The `split_property` function splits one properties line on `=` or `:`.
A backslash skips the next byte, so an escaped separator is not the split. The key and the value are trimmed. An empty key returns `None`. No separator returns `None`.

### accounts_of

<a id="function-accounts_of"></a>

```rust
fn accounts_of(map: &Mapping) -> Vec<Account>
```

The `accounts_of` function reads the `accounts` sequence.
A missing key returns an empty list. A value that is not a sequence returns an empty list. A row without a user name is skipped. A blank user name is skipped. A missing password is empty.

### touch_account

<a id="function-touch_account"></a>

```rust
fn touch_account(map: &mut Mapping, user: &str, password: &str, remember: bool)
```

The `touch_account` function updates one account row in the map.
An empty user name returns without a change. A matching row stores the password only when remember is on and the password is not empty. Otherwise the password key is removed from that row.

No matching row appends a new row. The new row stores a password only under that same rule. Rows that are not mappings are left in the sequence.

### parse_view_rows

<a id="function-parse_view_rows"></a>

```rust
fn parse_view_rows(text: &str) -> u32
```

The `parse_view_rows` function reads an open-card row count.
Swing `getAetherisViewRows` is the rule. Unreadable text returns 4. A count under 1 returns 1. Any other parsed integer is returned as that count.

### parse_wrap_columns

<a id="function-parse_wrap_columns"></a>

```rust
fn parse_wrap_columns(text: &str) -> u32
```

The `parse_wrap_columns` function reads a wrap width.
Swing `getAetherisWrapColumns` is the rule. Unreadable text returns 80. A count under 1 returns 1. Any other parsed integer is returned as that count.

### parse_line_chars

<a id="function-parse_line_chars"></a>

```rust
fn parse_line_chars(text: &str) -> u32
```

The `parse_line_chars` function reads an inspector line length.
Swing `getAetherisInspectorLineChars` is the rule. Unreadable text returns 80. A count under 1 returns 1. Any other parsed integer is returned as that count.

### pin_bar_flag

<a id="function-pin_bar_flag"></a>

```rust
fn pin_bar_flag(text: &str) -> bool
```

The `pin_bar_flag` function reads a boolean the way Swing `Boolean.parseBoolean` does after trim.
Only the word `true`, ignoring ASCII case, returns true. Every other text returns false.

### chooser_int

<a id="function-chooser_int"></a>

```rust
fn chooser_int(text: Option<&str>) -> Option<i32>
```

The `chooser_int` function parses an optional integer.
`None` returns `None`. Surrounding spaces are ignored. Text that is not an `i32` returns `None`.

### text_of

<a id="function-text_of"></a>

```rust
fn text_of(value: Option<&Value>) -> Option<String>
```

The `text_of` function reads a YAML scalar as text.
A string is copied. A number is printed with its YAML number text. A boolean becomes `true` or `false`. Any other value, including a missing key, returns `None`.

### put_str

<a id="function-put_str"></a>

```rust
fn put_str(map: &mut Mapping, key: &str, value: &str)
```

The `put_str` function stores one string under `key`.
An existing value for that key is replaced. Other keys stay.

### dump_map

<a id="function-dump_map"></a>

```rust
fn dump_map(map: &Mapping) -> Result<String, ()>
```

The `dump_map` function writes the map as YAML text.
A leading `---` line from the serializer is removed. The text starts with `# Casserole standalone SSH client` and a newline. A serializer failure returns `Err`.

### replace_file

<a id="function-replace_file"></a>

```rust
fn replace_file(path: &Path, bytes: &[u8]) -> Result<(), ()>
```

The `replace_file` function replaces `path` by writing a temporary file and renaming it.
The temporary name ends in `yaml.writing`. A failed rename deletes `path` and tries the rename again. A failed write returns `Err`. A second failed rename returns `Err`.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
