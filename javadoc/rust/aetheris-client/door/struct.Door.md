# struct Door

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **door**

**Source** `aetheris/rust/aetheris-client/src/door.rs`

```rust
pub struct Door
```

The [`Door`](struct.Door.md) type is the SSH client settings store.
It holds the host text, the port, the user name, the password, the remember flag, and the account rows. It also holds the YAML map so keys this type does not edit stay in the file.

`Debug` prints the host, the port, the user name, the remember flag, and the account count. A non-empty password is printed as `<redacted>`.

| | |
| --- | --- |
| **Specification** | GK-0142 aetheris-plan-f-107.001.md FINESSE:f-107 |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| Option<PathBuf> | [`path`](#member-path) |  |
| Mapping | [`map`](#member-map) |  |
| String | [`host`](#member-host) |  |
| u16 | [`port`](#member-port) |  |
| String | [`username`](#member-username) |  |
| String | [`password`](#member-password) |  |
| bool | [`remember`](#member-remember) |  |
| Vec<Account> | [`accounts`](#member-accounts) |  |
| bool | [`writable`](#member-writable) |  |
| Option<&'static str> | [`write_warning`](#member-write_warning) |  |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`memory_only`](#method-memory_only) | The `memory_only` method returns a [`Door`] that is held in memory. |
| fn | [`has_file`](#method-has_file) | The `has_file` method reports whether this [`Door`] has a path. |
| fn | [`load_home`](#method-load_home) | The `load_home` method loads `ssh-client.yaml` from the home directory. |
| fn | [`open`](#method-open) | The `open` method loads `yaml`, or imports `legacy` once when the YAML file is missing. |
| fn | [`host`](#method-host) | The `host` method returns the SSH host text this [`Door`] holds. |
| fn | [`port`](#method-port) | The `port` method returns the SSH port this [`Door`] holds. |
| fn | [`username`](#method-username) | The `username` method returns the user name this [`Door`] holds. |
| fn | [`password`](#method-password) | The `password` method returns the password this [`Door`] holds. |
| fn | [`remember`](#method-remember) | The `remember` method returns the remember flag this [`Door`] holds. |
| fn | [`connection_mode`](#method-connection_mode) | The `connection_mode` method reads `ui.connection.mode`. |
| fn | [`inspector_divider`](#method-inspector_divider) | The `inspector_divider` method returns the saved canvas share. |
| fn | [`wrap_columns`](#method-wrap_columns) | The `wrap_columns` method returns the inspector wrap width. |
| fn | [`save_wrap_columns`](#method-save_wrap_columns) | The `save_wrap_columns` method writes the wrap column count. |
| fn | [`line_chars`](#method-line_chars) | The `line_chars` method returns the inspector line length. |
| fn | [`save_line_chars`](#method-save_line_chars) | The `save_line_chars` method writes the inspector line length. |
| fn | [`view_rows`](#method-view_rows) | The `view_rows` method returns the open-card row count. |
| fn | [`save_view_rows`](#method-save_view_rows) | The `save_view_rows` method writes the open-card row count. |
| fn | [`product_tab_is_aetheris`](#method-product_tab_is_aetheris) | The `product_tab_is_aetheris` method reports the stored product tab. |
| fn | [`save_product_tab`](#method-save_product_tab) | The `save_product_tab` method stores `ssh` or `aetheris`. |
| fn | [`pin_bar_open`](#method-pin_bar_open) | The `pin_bar_open` method reports whether the pin bar is showing. |
| fn | [`save_pin_bar_open`](#method-save_pin_bar_open) | The `save_pin_bar_open` method stores `true` or `false`. |
| fn | [`chooser_bounds`](#method-chooser_bounds) | The `chooser_bounds` method returns the saved dialog rectangle. |
| fn | [`chooser_directory`](#method-chooser_directory) | The `chooser_directory` method returns the last approved folder. |
| fn | [`save_chooser_bounds`](#method-save_chooser_bounds) | The `save_chooser_bounds` method writes the four dialog bounds. |
| fn | [`font_size`](#method-font_size) | The `font_size` method returns the terminal font size. |
| fn | [`save_font_size`](#method-save_font_size) | The `save_font_size` method writes the terminal font size. |
| fn | [`frame_bounds`](#method-frame_bounds) | The `frame_bounds` method returns the saved frame rectangle. |
| fn | [`frame_maximized_word`](#method-frame_maximized_word) | The `frame_maximized_word` method returns the maximized flag when that key is present. |
| fn | [`save_frame`](#method-save_frame) | The `save_frame` method writes the restored rectangle and the maximized flag. |
| fn | [`exit_pos`](#method-exit_pos) | The `exit_pos` method returns the saved Confirm Exit origin. |
| fn | [`save_exit_pos`](#method-save_exit_pos) | The `save_exit_pos` method writes the Confirm Exit origin. |
| fn | [`write_key`](#method-write_key) | The `write_key` method stores one string and writes the map. |
| fn | [`write_map`](#method-write_map) | The `write_map` method writes the current map. |
| fn | [`write_at`](#method-write_at) | The `write_at` method dumps the map and replaces `path`. |
| fn | [`save_chooser_directory`](#method-save_chooser_directory) | The `save_chooser_directory` method writes the last approved folder. |
| fn | [`save_inspector_divider`](#method-save_inspector_divider) | The `save_inspector_divider` method writes one per-mille. |
| fn | [`set_host`](#method-set_host) | The `set_host` method stores the SSH host text. |
| fn | [`set_port`](#method-set_port) | The `set_port` method stores the SSH port. |
| fn | [`set_username`](#method-set_username) | The `set_username` method stores the user name. |
| fn | [`set_password`](#method-set_password) | The `set_password` method stores the password. |
| fn | [`set_remember`](#method-set_remember) | The `set_remember` method stores the remember flag. |
| fn | [`can_save`](#method-can_save) | The `can_save` method reports whether `save` can write a file. |
| fn | [`write_warning`](#method-write_warning) | The `write_warning` method returns the warning from a failed import write. |
| fn | [`account_names`](#method-account_names) | The `account_names` method returns the stored user names. |
| fn | [`password_for`](#method-password_for) | The `password_for` method returns the stored password for `username`. |
| fn | [`save`](#method-save) | The `save` method writes the connection keys. |
| fn | [`fresh`](#method-fresh) | The `fresh` method builds a [`Door`] with the default connection. |
| fn | [`from_map`](#method-from_map) | The `from_map` method builds a [`Door`] from a YAML map. |

## Field Detail

### path

<a id="member-path"></a>

```rust
path: Option<PathBuf>
```

### map

<a id="member-map"></a>

```rust
map: Mapping
```

### host

<a id="member-host"></a>

```rust
host: String
```

### port

<a id="member-port"></a>

```rust
port: u16
```

### username

<a id="member-username"></a>

```rust
username: String
```

### password

<a id="member-password"></a>

```rust
password: String
```

### remember

<a id="member-remember"></a>

```rust
remember: bool
```

### accounts

<a id="member-accounts"></a>

```rust
accounts: Vec<Account>
```

### writable

<a id="member-writable"></a>

```rust
writable: bool
```

### write_warning

<a id="member-write_warning"></a>

```rust
write_warning: Option<&'static str>
```

## Method Detail

### memory_only

<a id="method-memory_only"></a>

```rust
pub fn memory_only() -> Self
```

The `memory_only` method returns a [`Door`](struct.Door.md) that is held in memory.
The host text starts as `localhost`. The port starts as 2222. `save` does not create a file.

### has_file

<a id="method-has_file"></a>

```rust
pub fn has_file(&self) -> bool
```

The `has_file` method reports whether this [`Door`](struct.Door.md) has a path.
A value from `memory_only` has no path. That value does not read a file and does not write a file.

### load_home

<a id="method-load_home"></a>

```rust
pub fn load_home() -> Result<Self, DoorError>
```

The `load_home` method loads `ssh-client.yaml` from the home directory.
The home directory is `USERPROFILE` or, if that is absent, `HOME`. A missing home directory returns [`DoorError::Read`](enum.DoorError.md#member-read). Tests do not call this method. `chrome::run` does.

### open

<a id="method-open"></a>

```rust
pub fn open(yaml: &Path, legacy: &Path) -> Result<Self, DoorError>
```

The `open` method loads `yaml`, or imports `legacy` once when the YAML file is missing.
An existing YAML file is parsed as a mapping. A parse failure returns [`DoorError::Read`](enum.DoorError.md#member-read) and does not write. A missing YAML file with an existing legacy file reads that file as properties, then tries to write the YAML path.

A failed first write still returns the loaded value. The warning text on that failure is `door file could not be written`. Both files missing returns a fresh value aimed at the YAML path.

### host

<a id="method-host"></a>

```rust
pub fn host(&self) -> &str
```

The `host` method returns the SSH host text this [`Door`](struct.Door.md) holds.
The text is the value loaded from the file or the value last passed to `set_host`.

### port

<a id="method-port"></a>

```rust
pub fn port(&self) -> u16
```

The `port` method returns the SSH port this [`Door`](struct.Door.md) holds.
The value is the port loaded from the file or the value last passed to `set_port`.

### username

<a id="method-username"></a>

```rust
pub fn username(&self) -> &str
```

The `username` method returns the user name this [`Door`](struct.Door.md) holds.
The text is the value loaded from the file or the value last passed to `set_username`.

### password

<a id="method-password"></a>

```rust
pub fn password(&self) -> &str
```

The `password` method returns the password this [`Door`](struct.Door.md) holds.
The text is empty when the remember flag was off at load. `set_password` replaces it.

### remember

<a id="method-remember"></a>

```rust
pub fn remember(&self) -> bool
```

The `remember` method returns the remember flag this [`Door`](struct.Door.md) holds.
The flag is true only when the stored word was `true`, ignoring ASCII case.

### connection_mode

<a id="method-connection_mode"></a>

```rust
pub fn connection_mode(&self) -> &'static str
```

The `connection_mode` method reads `ui.connection.mode`.
The word `pty`, ignoring ASCII case and surrounding spaces, returns `pty`. A missing key returns `vt100`. Any other token returns `vt100`.

### inspector_divider

<a id="method-inspector_divider"></a>

```rust
pub fn inspector_divider(&self) -> Option<u16>
```

The `inspector_divider` method returns the saved canvas share.
The key is `ui.aetheris.inspector.divider`. The number is a per-mille from 0 through 1000. An absent key returns `None`. A negative number returns `None`. A number above 1000 returns 1000.

### wrap_columns

<a id="method-wrap_columns"></a>

```rust
pub fn wrap_columns(&self) -> u32
```

The `wrap_columns` method returns the inspector wrap width.
A missing key returns 80. Unreadable text returns 80. A value under 1 returns 1.

### save_wrap_columns

<a id="method-save_wrap_columns"></a>

```rust
pub fn save_wrap_columns(&mut self, columns: u32) -> Result<(), DoorError>
```

The `save_wrap_columns` method writes the wrap column count.
A count under 1 is stored as 1. The same stored text does not rewrite the file. Other keys stay.

No path returns [`DoorError::Write`](enum.DoorError.md#member-write). A value that is not writable returns [`DoorError::Write`](enum.DoorError.md#member-write). The key is `ui.aetheris.inspector.wrap.columns`.

### line_chars

<a id="method-line_chars"></a>

```rust
pub fn line_chars(&self) -> u32
```

The `line_chars` method returns the inspector line length.
A missing key returns 80. Unreadable text returns 80. A value under 1 returns 1.

### save_line_chars

<a id="method-save_line_chars"></a>

```rust
pub fn save_line_chars(&mut self, count: u32) -> Result<(), DoorError>
```

The `save_line_chars` method writes the inspector line length.
A count under 1 is stored as 1. The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`](enum.DoorError.md#member-write).

### view_rows

<a id="method-view_rows"></a>

```rust
pub fn view_rows(&self) -> u32
```

The `view_rows` method returns the open-card row count.
A missing key returns 4. Unreadable text returns 4. A value under 1 returns 1.

### save_view_rows

<a id="method-save_view_rows"></a>

```rust
pub fn save_view_rows(&mut self, rows: u32) -> Result<(), DoorError>
```

The `save_view_rows` method writes the open-card row count.
A count under 1 is stored as 1. The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`](enum.DoorError.md#member-write).

### product_tab_is_aetheris

<a id="method-product_tab_is_aetheris"></a>

```rust
pub fn product_tab_is_aetheris(&self) -> bool
```

The `product_tab_is_aetheris` method reports the stored product tab.
It returns true only when the stored word is `aetheris` after trim. A missing key returns false, which is the SSH tab.

### save_product_tab

<a id="method-save_product_tab"></a>

```rust
pub fn save_product_tab(&mut self, aetheris: bool) -> Result<(), DoorError>
```

The `save_product_tab` method stores `ssh` or `aetheris`.
True stores `aetheris`. False stores `ssh`. The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`](enum.DoorError.md#member-write).

### pin_bar_open

<a id="method-pin_bar_open"></a>

```rust
pub fn pin_bar_open(&self) -> bool
```

The `pin_bar_open` method reports whether the pin bar is showing.
A missing key returns false, which is folded. Only the word `true`, after trim and ignoring ASCII case, returns true. Swing `getAetherisPinBarOpen` uses that rule.

### save_pin_bar_open

<a id="method-save_pin_bar_open"></a>

```rust
pub fn save_pin_bar_open(&mut self, open: bool) -> Result<(), DoorError>
```

The `save_pin_bar_open` method stores `true` or `false`.
The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`](enum.DoorError.md#member-write).

### chooser_bounds

<a id="method-chooser_bounds"></a>

```rust
pub fn chooser_bounds(&self) -> Option<(i32, i32, i32, i32)>
```

The `chooser_bounds` method returns the saved dialog rectangle.
The order is x, y, width, and height. Width and height must both be present and at least 1. A missing or unreadable x or y is 0. A missing width or height returns `None`.

### chooser_directory

<a id="method-chooser_directory"></a>

```rust
pub fn chooser_directory(&self) -> String
```

The `chooser_directory` method returns the last approved folder.
An absent key returns an empty string.

### save_chooser_bounds

<a id="method-save_chooser_bounds"></a>

```rust
pub fn save_chooser_bounds(
    &mut self,
    x: i32,
    y: i32,
    width: i32,
    height: i32,
) -> Result<(), DoorError>
```

The `save_chooser_bounds` method writes the four dialog bounds.
The same four texts do not rewrite the file. Other keys stay. A width under 1 writes nothing and returns `Ok`. A height under 1 writes nothing and returns `Ok`. No path returns [`DoorError::Write`](enum.DoorError.md#member-write).

### font_size

<a id="method-font_size"></a>

```rust
pub fn font_size(&self) -> i32
```

The `font_size` method returns the terminal font size.
A missing or unreadable key is 14. A stored value outside 8 through 36 clamps onto that range. Swing's spinner uses that range.

### save_font_size

<a id="method-save_font_size"></a>

```rust
pub fn save_font_size(&mut self, size: i32) -> Result<(), DoorError>
```

The `save_font_size` method writes the terminal font size.
A value outside 8 through 36 is clamped before the write. The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`](enum.DoorError.md#member-write).

### frame_bounds

<a id="method-frame_bounds"></a>

```rust
pub fn frame_bounds(&self) -> Option<(i32, i32, i32, i32)>
```

The `frame_bounds` method returns the saved frame rectangle.
The result is absent when either origin key was never stored. A missing width is 1024. A missing height is 968. A width under 1 is absent. A height under 1 is absent. An unreadable origin is 0.

### frame_maximized_word

<a id="method-frame_maximized_word"></a>

```rust
pub fn frame_maximized_word(&self) -> Option<bool>
```

The `frame_maximized_word` method returns the maximized flag when that key is present.
An absent key returns `None`. The word `true`, after trim and ignoring ASCII case, returns `Some(true)`. Any other stored text returns `Some(false)`.

### save_frame

<a id="method-save_frame"></a>

```rust
pub fn save_frame(
    &mut self,
    origin: Option<(i32, i32, i32, i32)>,
    maximized: bool,
) -> Result<(), DoorError>
```

The `save_frame` method writes the restored rectangle and the maximized flag.
The same texts do not rewrite the file. Other keys stay. A width under 1 leaves the size keys as they are and still writes the flag. A height under 1 does the same. A missing `origin` does the same.

No path returns [`DoorError::Write`](enum.DoorError.md#member-write). A value that is not writable returns [`DoorError::Write`](enum.DoorError.md#member-write).

### exit_pos

<a id="method-exit_pos"></a>

```rust
pub fn exit_pos(&self) -> Option<(i32, i32)>
```

The `exit_pos` method returns the saved Confirm Exit origin.
The result is absent when either key was never stored. An unreadable key is also absent.

### save_exit_pos

<a id="method-save_exit_pos"></a>

```rust
pub fn save_exit_pos(&mut self, x: i32, y: i32) -> Result<(), DoorError>
```

The `save_exit_pos` method writes the Confirm Exit origin.
The same pair does not rewrite the file. Other keys stay. A [`Door`](struct.Door.md) that cannot write returns [`DoorError::Write`](enum.DoorError.md#member-write).

### write_key

<a id="method-write_key"></a>

```rust
fn write_key(&mut self, key: &str, value: &str) -> Result<(), DoorError>
```

The `write_key` method stores one string and writes the map.
No path returns [`DoorError::Write`](enum.DoorError.md#member-write). A value that is not writable returns [`DoorError::Write`](enum.DoorError.md#member-write).

### write_map

<a id="method-write_map"></a>

```rust
fn write_map(&mut self) -> Result<(), DoorError>
```

The `write_map` method writes the current map.
No path returns [`DoorError::Write`](enum.DoorError.md#member-write). A value that is not writable returns [`DoorError::Write`](enum.DoorError.md#member-write). The map is not changed by this method.

### write_at

<a id="method-write_at"></a>

```rust
fn write_at(&mut self, path: &Path) -> Result<(), DoorError>
```

The `write_at` method dumps the map and replaces `path`.
The parent directory is created when it is missing. A dump failure returns [`DoorError::Write`](enum.DoorError.md#member-write). A replace failure returns [`DoorError::Write`](enum.DoorError.md#member-write).

### save_chooser_directory

<a id="method-save_chooser_directory"></a>

```rust
pub fn save_chooser_directory(&mut self, directory: &str) -> Result<(), DoorError>
```

The `save_chooser_directory` method writes the last approved folder.
An empty string writes nothing and returns `Ok`. The same stored text does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`](enum.DoorError.md#member-write).

### save_inspector_divider

<a id="method-save_inspector_divider"></a>

```rust
pub fn save_inspector_divider(&mut self, per_mille: u16) -> Result<(), DoorError>
```

The `save_inspector_divider` method writes one per-mille.
A value above 1000 is stored as 1000. The same value does not rewrite the file. Other keys stay. No path returns [`DoorError::Write`](enum.DoorError.md#member-write).

### set_host

<a id="method-set_host"></a>

```rust
pub fn set_host(&mut self, host: String)
```

The `set_host` method stores the SSH host text.
Surrounding spaces are removed. The file is not written until `save`.

### set_port

<a id="method-set_port"></a>

```rust
pub fn set_port(&mut self, port: u16)
```

The `set_port` method stores the SSH port.
A port of 0 is ignored. Any other port replaces the stored port. The file is not written until `save`.

### set_username

<a id="method-set_username"></a>

```rust
pub fn set_username(&mut self, username: String)
```

The `set_username` method stores the user name.
Surrounding spaces are removed. The file is not written until `save`.

### set_password

<a id="method-set_password"></a>

```rust
pub fn set_password(&mut self, password: String)
```

The `set_password` method stores the password.
The text is kept as given, including spaces. The file is not written until `save`.

### set_remember

<a id="method-set_remember"></a>

```rust
pub fn set_remember(&mut self, remember: bool)
```

The `set_remember` method stores the remember flag.
The file is not written until `save`.

### can_save

<a id="method-can_save"></a>

```rust
pub fn can_save(&self) -> bool
```

The `can_save` method reports whether `save` can write a file.
The result is true only when the value is writable and has a path.

### write_warning

<a id="method-write_warning"></a>

```rust
pub fn write_warning(&self) -> Option<&'static str>
```

The `write_warning` method returns the warning from a failed import write.
`None` means no warning is stored. A later successful `save` clears it.

### account_names

<a id="method-account_names"></a>

```rust
pub fn account_names(&self) -> Vec<String>
```

The `account_names` method returns the stored user names.
The order is the order of the account rows. Passwords are not included.

### password_for

<a id="method-password_for"></a>

```rust
pub fn password_for(&self, username: &str) -> Option<&str>
```

The `password_for` method returns the stored password for `username`.
The match is the exact user name. An empty stored password returns `None`. A missing name returns `None`.

### save

<a id="method-save"></a>

```rust
pub fn save(&mut self) -> Result<(), DoorError>
```

The `save` method writes the connection keys.
The host, the port, the user name, and the remember flag are written. The password key is written only when remember is on and the password is not empty. Otherwise that key is removed.

Other keys stay. Other account rows stay. The matching account row is updated. No path returns [`DoorError::Write`](enum.DoorError.md#member-write). A successful write clears the warning.

### fresh

<a id="method-fresh"></a>

```rust
fn fresh(path: Option<PathBuf>, writable: bool) -> Self
```

The `fresh` method builds a [`Door`](struct.Door.md) with the default connection.
The host text is `localhost`. The port is 2222. The user name comes from `os_user`. The password is empty. The remember flag is false. The map is empty.

### from_map

<a id="method-from_map"></a>

```rust
fn from_map(
    path: Option<PathBuf>,
    map: Mapping,
    writable: bool,
    write_warning: Option<&'static str>,
) -> Self
```

The `from_map` method builds a [`Door`](struct.Door.md) from a YAML map.
An empty host becomes `localhost`. A missing or unreadable port becomes 2222. An empty user name becomes `os_user`. The remember flag is true only for the word `true`, ignoring ASCII case.

The password is read only when remember is true. Otherwise the password is empty even if the key is present. Account rows are read from the map.

## impl std::fmt::Debug for Door

```rust
impl std::fmt::Debug for Door
```

### fmt

<a id="method-fmt"></a>

```rust
fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result
```

The `fmt` method prints a [`Door`](struct.Door.md) without the password text.
A non-empty password is shown as `<redacted>`. The account list is shown as a count.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
