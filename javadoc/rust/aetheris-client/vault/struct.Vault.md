# struct Vault

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **vault**

**Source** `rust/aetheris/aetheris-client/src/vault.rs`

```rust
pub(crate) struct Vault
```

The [`Vault`](struct.Vault.md) struct is the open file for one sanitized SSH user.
The connection stays until this value drops. `_db` is held so the file stays open until that drop.

| | |
| --- | --- |
| **Specification** | GK-0155 aetheris-stage-2-finessing.001.md FINESSE:f-222 |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| turso::Connection | [`conn`](#member-conn) |  |
| turso::Database | [`_db`](#member-_db) | The `_db` field is held so the file stays open until this vault drops. |
| String | [`username`](#member-username) |  |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`open`](#method-open) | The `open` method opens one local file and applies the translated DDL. |
| fn | [`username`](#method-username) | The `username` method returns the sanitized user name. |
| fn | [`load_or_create`](#method-load_or_create) | The `load_or_create` method loads the canvases for this user. |
| fn | [`load`](#method-load) | The `load` method reads the first workspace for this user, then its canvases, oldest first. |
| fn | [`save_cell`](#method-save_cell) | The `save_cell` method updates the row. |
| fn | [`sync_deck`](#method-sync_deck) | The `sync_deck` method writes the cells of one canvas. |
| fn | [`save_order`](#method-save_order) | The `save_order` method stores the cell order as comma-joined ids. |
| fn | [`search`](#method-search) | The `search` method returns cell ids whose prompt or reply contains `needle`. |
| fn | [`migrate`](#method-migrate) | The `migrate` method creates the tables and applies the later columns. |
| fn | [`ensure_collapsed`](#method-ensure_collapsed) | The `ensure_collapsed` method adds `collapsed` when that column is missing. |
| fn | [`ensure_head_lock`](#method-ensure_head_lock) | The `ensure_head_lock` method adds `head_lock` when that column is missing. |
| fn | [`ensure_schema_version`](#method-ensure_schema_version) | The `ensure_schema_version` method moves the file to schema version 2. |
| fn | [`schema_version`](#method-schema_version) | The `schema_version` method reads the stored version. |
| fn | [`v2_columns_present`](#method-v2_columns_present) | The `v2_columns_present` method reports whether `group_id` and `execution_status` can be selected. |
| fn | [`add_column_quiet`](#method-add_column_quiet) | The `add_column_quiet` method runs `sql` and ignores the result. |
| fn | [`load_cells`](#method-load_cells) | The `load_cells` method reads the cells of one canvas. |
| fn | [`insert_cell`](#method-insert_cell) | The `insert_cell` method inserts one cell row. |
| fn | [`exec`](#method-exec) | The `exec` method runs one statement. |

## Field Detail

### conn

<a id="member-conn"></a>

```rust
conn: turso::Connection
```

### _db

<a id="member-_db"></a>

```rust
_db: turso::Database
```

The `_db` field is held so the file stays open until this vault drops.
The connection used for queries is `conn`.

### username

<a id="member-username"></a>

```rust
username: String
```

## Method Detail

### open

<a id="method-open"></a>

```rust
pub(crate) async fn open(path: &Path, username: &str) -> Result<Self, String>
```

The `open` method opens one local file and applies the translated DDL.
Sync stays off. A parent directory is created when the path is not `:memory:`. A path that is not UTF-8 returns an error. The stored user name is sanitized.

### username

<a id="method-username"></a>

```rust
pub(crate) fn username(&self) -> &str
```

The `username` method returns the sanitized user name.
That is the name stored when the vault was opened.

### load_or_create

<a id="method-load_or_create"></a>

```rust
pub(crate) async fn load_or_create(&self) -> Result<Vec<CanvasRow>, String>
```

The `load_or_create` method loads the canvases for this user.
An empty file gains one workspace named `default` and one canvas named `Canvas 1`. That canvas starts with one included cell. The cell id is the canvas order.

### load

<a id="method-load"></a>

```rust
pub(crate) async fn load(&self) -> Result<Vec<CanvasRow>, String>
```

The `load` method reads the first workspace for this user, then its canvases, oldest first.
No workspace returns an empty list. A stored `RUNNING` status is written back as `INTERRUPTED`.

### save_cell

<a id="method-save_cell"></a>

```rust
pub(crate) async fn save_cell(&self, cell: &CellRow) -> Result<(), String>
```

The `save_cell` method updates the row.
A missing id is inserted. An ephemeral cell writes both payloads as null. Empty parent, target, group, and status texts are stored as null.

### sync_deck

<a id="method-sync_deck"></a>

```rust
pub(crate) async fn sync_deck(
    &self,
    canvas_id: &str,
    cells: &[CellRow],
    hold: &[String],
) -> Result<(), String>
```

The `sync_deck` method writes the cells of one canvas.
A cell that left is removed, and its traces go with it. Turn indexes start at 1 in this order. Parent pointers stay as given. An id in `hold` stays. Another canvas stays.

### save_order

<a id="method-save_order"></a>

```rust
pub(crate) async fn save_order(&self, canvas_id: &str, order: &[String]) -> Result<(), String>
```

The `save_order` method stores the cell order as comma-joined ids.
An empty list stores an empty order.

### search

<a id="method-search"></a>

```rust
pub(crate) async fn search(&self, canvas_id: &str, needle: &str) -> Result<Vec<String>, String>
```

The `search` method returns cell ids whose prompt or reply contains `needle`.
The match is limited to one canvas. An empty canvas id returns no rows. An empty needle returns no rows. Typed `%` and `_` stay literals.

### migrate

<a id="method-migrate"></a>

```rust
async fn migrate(&self) -> Result<(), String>
```

The `migrate` method creates the tables and applies the later columns.
The workspace, canvas, cell, and trace tables are created if missing. The collapsed column, the head-lock column, and schema version 2 follow.

### ensure_collapsed

<a id="method-ensure_collapsed"></a>

```rust
async fn ensure_collapsed(&self) -> Result<(), String>
```

The `ensure_collapsed` method adds `collapsed` when that column is missing.
A column that is already present is left alone. The added column is an integer with default 0.

### ensure_head_lock

<a id="method-ensure_head_lock"></a>

```rust
async fn ensure_head_lock(&self) -> Result<(), String>
```

The `ensure_head_lock` method adds `head_lock` when that column is missing.
A column that is already present is left alone. The added column is an integer with default 0.

### ensure_schema_version

<a id="method-ensure_schema_version"></a>

```rust
async fn ensure_schema_version(&self) -> Result<(), String>
```

The `ensure_schema_version` method moves the file to schema version 2.
A version under 2, or missing version-2 columns, adds `group_id` and `execution_status`. Those adds ignore an error from a column that already exists. Columns still missing after that return an error. Success stores version 2.

### schema_version

<a id="method-schema_version"></a>

```rust
async fn schema_version(&self) -> Result<i64, String>
```

The `schema_version` method reads the stored version.
No row returns 0.

### v2_columns_present

<a id="method-v2_columns_present"></a>

```rust
async fn v2_columns_present(&self) -> bool
```

The `v2_columns_present` method reports whether `group_id` and `execution_status` can be selected.
A failed probe returns false.

### add_column_quiet

<a id="method-add_column_quiet"></a>

```rust
async fn add_column_quiet(&self, sql: &str)
```

The `add_column_quiet` method runs `sql` and ignores the result.
A duplicate column is not reported to the caller.

### load_cells

<a id="method-load_cells"></a>

```rust
async fn load_cells(&self, canvas_id: &str) -> Result<Vec<CellRow>, String>
```

The `load_cells` method reads the cells of one canvas.
The order is `turn_index`. Each row is parsed by `cell_from_row`.

### insert_cell

<a id="method-insert_cell"></a>

```rust
async fn insert_cell(&self, cell: &CellRow) -> Result<(), String>
```

The `insert_cell` method inserts one cell row.
An ephemeral cell writes both payloads as null. Empty optional texts are stored as null.

### exec

<a id="method-exec"></a>

```rust
async fn exec(&self, sql: &str, params: impl turso::params::IntoParams) -> Result<u64, String>
```

The `exec` method runs one statement.
The returned number is the count from the driver. A driver error becomes the error string.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
