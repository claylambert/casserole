# struct FooterHit

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **chrome**

**Source** `rust/aetheris/aetheris-client/src/chrome.rs`

```rust
struct FooterHit
```

The struct `FooterHit` is what the canvas footer asked for this frame.
The flags record clicks and edits. The positions record where the rows were painted.

| | |
| --- | --- |
| **Specification** | PK-1123 docs/specs/common/formal-plan.spec.md FINESSE:f-144 |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| bool | [`make_run`](#member-make_run) |  |
| bool | [`make_stop`](#member-make_stop) |  |
| bool | [`make_new`](#member-make_new) |  |
| bool | [`make_clear`](#member-make_clear) |  |
| bool | [`make_delete`](#member-make_delete) |  |
| bool | [`make_collapse`](#member-make_collapse) |  |
| bool | [`make_expand`](#member-make_expand) |  |
| bool | [`search_now`](#member-search_now) |  |
| bool | [`clear_search`](#member-clear_search) | The field `clear_search` is true when the red x cleared the search field and the find filter. The flag is for this frame. |
| bool | [`export_now`](#member-export_now) |  |
| bool | [`canvas_import_now`](#member-canvas_import_now) |  |
| bool | [`pull_now`](#member-pull_now) |  |
| bool | [`terminal_now`](#member-terminal_now) |  |
| bool | [`mood_flip`](#member-mood_flip) | The field `mood_flip` is true when the mood switch was clicked while a string is saved. The flag is for this frame. |
| bool | [`mood_open`](#member-mood_open) | The field `mood_open` is true when the Mood button was clicked. The flag is for this frame. |
| bool | [`make_hide`](#member-make_hide) | The field `make_hide` is true when Hide Collapsed was clicked. Folded cards leave the deck. |
| bool | [`make_show`](#member-make_show) | The field `make_show` is true when Show Collapsed was clicked. Folded cards come back. |
| bool | [`row_minus`](#member-row_minus) | The field `row_minus` is true when minus was clicked on the row count. The flag is for this frame. |
| bool | [`row_plus`](#member-row_plus) | The field `row_plus` is true when plus was clicked on the row count. The flag is for this frame. |
| bool | [`row_typed`](#member-row_typed) | The field `row_typed` is true when the row field changed. The count waits 800 ms. |
| f32 | [`master_top`](#member-master_top) | The field `master_top` is the upper edge of the master row. A smaller value is higher on the plate. |
| Option<f32> | [`tool_top`](#member-tool_top) | The field `tool_top` is the upper edge of the tool row. `None` means that row is hidden. |
| Option<String> | [`user_pick`](#member-user_pick) | The field `user_pick` is a name that was chosen. `None` means the list is only being filled. |
| egui::Rect | [`bar`](#member-bar) | The field `bar` is the outer rectangle of the rounded bar. The rectangle includes the margin outside the stroke. |

## Field Detail

### make_run

<a id="member-make_run"></a>

```rust
make_run: bool
```

### make_stop

<a id="member-make_stop"></a>

```rust
make_stop: bool
```

### make_new

<a id="member-make_new"></a>

```rust
make_new: bool
```

### make_clear

<a id="member-make_clear"></a>

```rust
make_clear: bool
```

### make_delete

<a id="member-make_delete"></a>

```rust
make_delete: bool
```

### make_collapse

<a id="member-make_collapse"></a>

```rust
make_collapse: bool
```

### make_expand

<a id="member-make_expand"></a>

```rust
make_expand: bool
```

### search_now

<a id="member-search_now"></a>

```rust
search_now: bool
```

### clear_search

<a id="member-clear_search"></a>

```rust
clear_search: bool
```

The field `clear_search` is true when the red x cleared the search field and the find filter. The flag is for this frame.

### export_now

<a id="member-export_now"></a>

```rust
export_now: bool
```

### canvas_import_now

<a id="member-canvas_import_now"></a>

```rust
canvas_import_now: bool
```

### pull_now

<a id="member-pull_now"></a>

```rust
pull_now: bool
```

### terminal_now

<a id="member-terminal_now"></a>

```rust
terminal_now: bool
```

### mood_flip

<a id="member-mood_flip"></a>

```rust
mood_flip: bool
```

The field `mood_flip` is true when the mood switch was clicked while a string is saved. The flag is for this frame.

### mood_open

<a id="member-mood_open"></a>

```rust
mood_open: bool
```

The field `mood_open` is true when the Mood button was clicked. The flag is for this frame.

### make_hide

<a id="member-make_hide"></a>

```rust
make_hide: bool
```

The field `make_hide` is true when Hide Collapsed was clicked. Folded cards leave the deck.

### make_show

<a id="member-make_show"></a>

```rust
make_show: bool
```

The field `make_show` is true when Show Collapsed was clicked. Folded cards come back.

### row_minus

<a id="member-row_minus"></a>

```rust
row_minus: bool
```

The field `row_minus` is true when minus was clicked on the row count. The flag is for this frame.

### row_plus

<a id="member-row_plus"></a>

```rust
row_plus: bool
```

The field `row_plus` is true when plus was clicked on the row count. The flag is for this frame.

### row_typed

<a id="member-row_typed"></a>

```rust
row_typed: bool
```

The field `row_typed` is true when the row field changed. The count waits 800 ms.

### master_top

<a id="member-master_top"></a>

```rust
master_top: f32
```

The field `master_top` is the upper edge of the master row. A smaller value is higher on the plate.

### tool_top

<a id="member-tool_top"></a>

```rust
tool_top: Option<f32>
```

The field `tool_top` is the upper edge of the tool row. `None` means that row is hidden.

### user_pick

<a id="member-user_pick"></a>

```rust
user_pick: Option<String>
```

The field `user_pick` is a name that was chosen. `None` means the list is only being filled.

### bar

<a id="member-bar"></a>

```rust
bar: egui::Rect
```

The field `bar` is the outer rectangle of the rounded bar. The rectangle includes the margin outside the stroke.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
