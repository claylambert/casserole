# Module vault

[Rust tree](../index.md) · **aetheris-client**

**Source** `aetheris/rust/aetheris-client/src/vault.rs`

The module stores one local vault for one SSH user.

Java `VaultStore` and `VaultSql` are the rule. The engine is embedded Turso. The file is `aetheris.db` beside the Java `vault` directory. This module does not open `vault.mv.db`. Cloud sync stays off. `push` stays off. `pull` stays off.

| | |
| --- | --- |
| **Specification** | G-12 rust-aetheris-client.draft.001.md |
| **Author** | clayl (2026-10-27) |

## Type Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| struct | [`CellRow`](vault/struct.CellRow.md) | The [`CellRow`] struct is one canvas cell. |
| struct | [`CanvasRow`](vault/struct.CanvasRow.md) | The [`CanvasRow`] struct is one canvas and the cells that belong to it. |
| struct | [`Vault`](vault/struct.Vault.md) | The [`Vault`] struct is the open file for one sanitized SSH user. |

## Constant Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| const | [`DDL_WORKSPACE`](#constant-ddl_workspace) |  |
| const | [`DDL_CANVAS`](#constant-ddl_canvas) |  |
| const | [`DDL_CELL`](#constant-ddl_cell) | `DDL_CELL` creates the version-1 `cell` table when it is missing. |
| const | [`DDL_TRACE`](#constant-ddl_trace) |  |
| const | [`DDL_SCHEMA_VERSION`](#constant-ddl_schema_version) |  |
| const | [`PROBE_COLLAPSED`](#constant-probe_collapsed) |  |
| const | [`ALTER_COLLAPSED`](#constant-alter_collapsed) |  |
| const | [`PROBE_HEAD_LOCK`](#constant-probe_head_lock) |  |
| const | [`ALTER_HEAD_LOCK`](#constant-alter_head_lock) |  |
| const | [`SELECT_SCHEMA_VERSION`](#constant-select_schema_version) |  |
| const | [`MERGE_SCHEMA_VERSION`](#constant-merge_schema_version) |  |
| const | [`PROBE_V2`](#constant-probe_v2) |  |
| const | [`ALTER_GROUP_ID`](#constant-alter_group_id) |  |
| const | [`ALTER_EXECUTION_STATUS`](#constant-alter_execution_status) |  |
| const | [`INSERT_WORKSPACE`](#constant-insert_workspace) |  |
| const | [`INSERT_CANVAS`](#constant-insert_canvas) |  |
| const | [`INSERT_CELL`](#constant-insert_cell) |  |
| const | [`UPDATE_CELL`](#constant-update_cell) |  |
| const | [`UPDATE_CANVAS_ORDER`](#constant-update_canvas_order) |  |
| const | [`DELETE_TRACE_FOR_CELL`](#constant-delete_trace_for_cell) |  |
| const | [`DELETE_CELL`](#constant-delete_cell) |  |
| const | [`SELECT_WORKSPACE`](#constant-select_workspace) |  |
| const | [`SELECT_CANVAS`](#constant-select_canvas) |  |
| const | [`SELECT_CELLS_V2`](#constant-select_cells_v2) |  |
| const | [`SEARCH_CELLS`](#constant-search_cells) |  |

## Function Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`sanitize_user`](#function-sanitize_user) | The `sanitize_user` function returns a path-safe folder name. |
| fn | [`vault_file`](#function-vault_file) | The `vault_file` function returns `home/.aetheris/{user}/aetheris.db`. |
| fn | [`cell_from_row`](#function-cell_from_row) | The `cell_from_row` function builds a [`CellRow`] from one query row. |
| fn | [`known_token`](#function-known_token) | The `known_token` function reports whether a context word is recognized. |
| fn | [`from_token`](#function-from_token) | The `from_token` function maps a stored context word to the word this crate keeps. |
| fn | [`like_contains`](#function-like_contains) | The `like_contains` function builds a `LIKE` pattern for a contains match. |
| fn | [`parse_order`](#function-parse_order) | The `parse_order` function splits a comma-joined id list. |
| fn | [`join_order`](#function-join_order) | The `join_order` function joins ids with commas. |
| fn | [`empty_to_null`](#function-empty_to_null) | The `empty_to_null` function turns an empty string into SQL null. |
| fn | [`payload`](#function-payload) | The `payload` function chooses the text stored for a prompt or a reply. |
| fn | [`flag`](#function-flag) | The `flag` function stores a boolean as an integer. |
| fn | [`text_col`](#function-text_col) | The `text_col` function reads one column as text. |
| fn | [`i64_col`](#function-i64_col) | The `i64_col` function reads one column as an integer. |
| fn | [`value_is_null`](#function-value_is_null) | The `value_is_null` function reports a column that is not text, integer, or real. |
| fn | [`now_ms`](#function-now_ms) | The `now_ms` function returns the current time in milliseconds since the Unix epoch. |
| fn | [`mint_id`](#function-mint_id) | The `mint_id` function returns a new id in UUID text form. |

## Constant Detail

### DDL_WORKSPACE

<a id="constant-ddl_workspace"></a>

```rust
const DDL_WORKSPACE: &str = "CREATE TABLE IF NOT EXISTS workspace (\
id VARCHAR(36) PRIMARY KEY,\
username VARCHAR(128) NOT NULL,\
name VARCHAR(256) NOT NULL,\
created_ms BIGINT NOT NULL)";
```

### DDL_CANVAS

<a id="constant-ddl_canvas"></a>

```rust
const DDL_CANVAS: &str = "CREATE TABLE IF NOT EXISTS canvas (\
id VARCHAR(36) PRIMARY KEY,\
workspace_id VARCHAR(36) NOT NULL,\
name VARCHAR(256) NOT NULL,\
cell_order TEXT,\
created_ms BIGINT NOT NULL)";
```

### DDL_CELL

<a id="constant-ddl_cell"></a>

```rust
const DDL_CELL: &str = "CREATE TABLE IF NOT EXISTS cell (\
id VARCHAR(36) PRIMARY KEY,\
canvas_id VARCHAR(36) NOT NULL,\
turn_index INT NOT NULL,\
parent_cell_id VARCHAR(36),\
branch_id VARCHAR(64) DEFAULT 'main',\
column_position INT DEFAULT 0,\
execution_target VARCHAR(256),\
context_state VARCHAR(32) NOT NULL,\
is_ephemeral INTEGER NOT NULL DEFAULT 0,\
collapsed INTEGER NOT NULL DEFAULT 0,\
head_lock INTEGER NOT NULL DEFAULT 0,\
user_clob TEXT,\
assistant_clob TEXT,\
created_ms BIGINT NOT NULL)";
```

`DDL_CELL` creates the version-1 `cell` table when it is missing.
`group_id` and `execution_status` arrive in the version-2 step. They are not in this statement.

### DDL_TRACE

<a id="constant-ddl_trace"></a>

```rust
const DDL_TRACE: &str = "CREATE TABLE IF NOT EXISTS trace (\
id VARCHAR(36) PRIMARY KEY,\
cell_id VARCHAR(36) NOT NULL,\
created_ms BIGINT NOT NULL,\
body TEXT)";
```

### DDL_SCHEMA_VERSION

<a id="constant-ddl_schema_version"></a>

```rust
const DDL_SCHEMA_VERSION: &str = "CREATE TABLE IF NOT EXISTS schema_version (\
id INT PRIMARY KEY,\
version INT NOT NULL)";
```

### PROBE_COLLAPSED

<a id="constant-probe_collapsed"></a>

```rust
const PROBE_COLLAPSED: &str = "SELECT 1 FROM pragma_table_info('cell') WHERE name='collapsed'";
```

### ALTER_COLLAPSED

<a id="constant-alter_collapsed"></a>

```rust
const ALTER_COLLAPSED: &str =
"ALTER TABLE cell ADD COLUMN collapsed INTEGER NOT NULL DEFAULT 0";
```

### PROBE_HEAD_LOCK

<a id="constant-probe_head_lock"></a>

```rust
const PROBE_HEAD_LOCK: &str = "SELECT 1 FROM pragma_table_info('cell') WHERE name='head_lock'";
```

### ALTER_HEAD_LOCK

<a id="constant-alter_head_lock"></a>

```rust
const ALTER_HEAD_LOCK: &str =
"ALTER TABLE cell ADD COLUMN head_lock INTEGER NOT NULL DEFAULT 0";
```

### SELECT_SCHEMA_VERSION

<a id="constant-select_schema_version"></a>

```rust
const SELECT_SCHEMA_VERSION: &str = "SELECT version FROM schema_version WHERE id=1";
```

### MERGE_SCHEMA_VERSION

<a id="constant-merge_schema_version"></a>

```rust
const MERGE_SCHEMA_VERSION: &str = "INSERT INTO schema_version (id, version) VALUES (1, ?1) \
ON CONFLICT(id) DO UPDATE SET version=excluded.version";
```

### PROBE_V2

<a id="constant-probe_v2"></a>

```rust
const PROBE_V2: &str = "SELECT group_id, execution_status FROM cell WHERE 1=0";
```

### ALTER_GROUP_ID

<a id="constant-alter_group_id"></a>

```rust
const ALTER_GROUP_ID: &str = "ALTER TABLE cell ADD COLUMN group_id VARCHAR(36)";
```

### ALTER_EXECUTION_STATUS

<a id="constant-alter_execution_status"></a>

```rust
const ALTER_EXECUTION_STATUS: &str = "ALTER TABLE cell ADD COLUMN execution_status VARCHAR(32)";
```

### INSERT_WORKSPACE

<a id="constant-insert_workspace"></a>

```rust
const INSERT_WORKSPACE: &str =
"INSERT INTO workspace (id, username, name, created_ms) VALUES (?1, ?2, ?3, ?4)";
```

### INSERT_CANVAS

<a id="constant-insert_canvas"></a>

```rust
const INSERT_CANVAS: &str = "INSERT INTO canvas (id, workspace_id, name, cell_order, created_ms) \
VALUES (?1, ?2, ?3, ?4, ?5)";
```

### INSERT_CELL

<a id="constant-insert_cell"></a>

```rust
const INSERT_CELL: &str = "INSERT INTO cell (id, canvas_id, turn_index, parent_cell_id, branch_id, \
column_position, execution_target, context_state, is_ephemeral, collapsed, head_lock, user_clob, \
assistant_clob, created_ms, group_id, execution_status) \
VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16)";
```

### UPDATE_CELL

<a id="constant-update_cell"></a>

```rust
const UPDATE_CELL: &str = "UPDATE cell SET turn_index=?1, context_state=?2, is_ephemeral=?3, \
collapsed=?4, head_lock=?5, execution_target=?6, user_clob=?7, assistant_clob=?8, parent_cell_id=?9, \
branch_id=?10, column_position=?11, group_id=?12, execution_status=?13 WHERE id=?14";
```

### UPDATE_CANVAS_ORDER

<a id="constant-update_canvas_order"></a>

```rust
const UPDATE_CANVAS_ORDER: &str = "UPDATE canvas SET cell_order=?1 WHERE id=?2";
```

### DELETE_TRACE_FOR_CELL

<a id="constant-delete_trace_for_cell"></a>

```rust
const DELETE_TRACE_FOR_CELL: &str = "DELETE FROM trace WHERE cell_id=?1";
```

### DELETE_CELL

<a id="constant-delete_cell"></a>

```rust
const DELETE_CELL: &str = "DELETE FROM cell WHERE id=?1 AND canvas_id=?2";
```

### SELECT_WORKSPACE

<a id="constant-select_workspace"></a>

```rust
const SELECT_WORKSPACE: &str =
"SELECT id, username, name, created_ms FROM workspace WHERE username=?1 ORDER BY created_ms";
```

### SELECT_CANVAS

<a id="constant-select_canvas"></a>

```rust
const SELECT_CANVAS: &str = "SELECT id, workspace_id, name, cell_order, created_ms FROM canvas \
WHERE workspace_id=?1 ORDER BY created_ms";
```

### SELECT_CELLS_V2

<a id="constant-select_cells_v2"></a>

```rust
const SELECT_CELLS_V2: &str = "SELECT id, canvas_id, turn_index, parent_cell_id, branch_id, \
column_position, execution_target, context_state, is_ephemeral, collapsed, head_lock, user_clob, \
assistant_clob, created_ms, group_id, execution_status FROM cell WHERE canvas_id=?1 \
ORDER BY turn_index";
```

### SEARCH_CELLS

<a id="constant-search_cells"></a>

```rust
const SEARCH_CELLS: &str = "SELECT id FROM cell WHERE canvas_id=?1 AND (user_clob LIKE ?2 \
ESCAPE '\\' OR assistant_clob LIKE ?3 ESCAPE '\\')";
```

## Function Detail

### sanitize_user

<a id="function-sanitize_user"></a>

```rust
pub(crate) fn sanitize_user(raw: &str) -> String
```

The `sanitize_user` function returns a path-safe folder name.
Empty text becomes `anon`. `.` becomes `anon`. `..` becomes `anon`. Any character other than an ASCII letter, a digit, `.`, `_`, or `-` makes the whole name `anon`. Any other text is kept.

### vault_file

<a id="function-vault_file"></a>

```rust
pub(crate) fn vault_file(home: &Path, username: &str) -> PathBuf
```

The `vault_file` function returns `home/.aetheris/{user}/aetheris.db`.
The user folder is `sanitize_user` of `username`. The Java H2 file stays in `vault/`.

### cell_from_row

<a id="function-cell_from_row"></a>

```rust
fn cell_from_row(row: &turso::Row) -> Result<CellRow, String>
```

The `cell_from_row` function builds a [`CellRow`](vault/struct.CellRow.md) from one query row.
An empty branch becomes `main`. `GHOST` is stored as `EXCLUDED`. An unknown state becomes `INCLUDED` and is not readable. An ephemeral row returns empty prompt and reply text.

### known_token

<a id="function-known_token"></a>

```rust
fn known_token(raw: &str) -> bool
```

The `known_token` function reports whether a context word is recognized.
Empty text is recognized. The words are `INCLUDED`, `EXCLUDED`, `GHOST`, `INPUT_ONLY`, and `OUTPUT_ONLY`, ignoring ASCII case.

### from_token

<a id="function-from_token"></a>

```rust
fn from_token(raw: &str) -> String
```

The `from_token` function maps a stored context word to the word this crate keeps.
`EXCLUDED` and `GHOST` become `EXCLUDED`. `INPUT_ONLY` and `OUTPUT_ONLY` stay. Every other word, including empty text, becomes `INCLUDED`.

### like_contains

<a id="function-like_contains"></a>

```rust
fn like_contains(needle: &str) -> String
```

The `like_contains` function builds a `LIKE` pattern for a contains match.
The pattern starts and ends with `%`. `\`, `%`, and `_` in the needle are escaped with a backslash.

### parse_order

<a id="function-parse_order"></a>

```rust
fn parse_order(raw: &str) -> Vec<String>
```

The `parse_order` function splits a comma-joined id list.
Each piece is trimmed. Empty pieces are dropped.

### join_order

<a id="function-join_order"></a>

```rust
fn join_order(order: &[String]) -> String
```

The `join_order` function joins ids with commas.
An empty list returns an empty string.

### empty_to_null

<a id="function-empty_to_null"></a>

```rust
fn empty_to_null(raw: &str) -> Option<String>
```

The `empty_to_null` function turns an empty string into SQL null.
Any other string is kept.

### payload

<a id="function-payload"></a>

```rust
fn payload(raw: &str, ephemeral: bool) -> Option<String>
```

The `payload` function chooses the text stored for a prompt or a reply.
An ephemeral cell returns null. Any other cell stores `raw`, including an empty string.

### flag

<a id="function-flag"></a>

```rust
fn flag(on: bool) -> i64
```

The `flag` function stores a boolean as an integer.
True is 1. False is 0.

### text_col

<a id="function-text_col"></a>

```rust
fn text_col(row: &turso::Row, index: usize) -> Result<String, String>
```

The `text_col` function reads one column as text.
A text value is copied. Any other value, including null, returns an empty string. A driver error is returned.

### i64_col

<a id="function-i64_col"></a>

```rust
fn i64_col(row: &turso::Row, index: usize) -> Result<i64, String>
```

The `i64_col` function reads one column as an integer.
An integer value is copied. A text value is parsed. Any other value returns an error.

### value_is_null

<a id="function-value_is_null"></a>

```rust
fn value_is_null(row: &turso::Row, index: usize) -> bool
```

The `value_is_null` function reports a column that is not text, integer, or real.
A driver error returns false.

### now_ms

<a id="function-now_ms"></a>

```rust
fn now_ms() -> i64
```

The `now_ms` function returns the current time in milliseconds since the Unix epoch.
A clock error returns 0.

### mint_id

<a id="function-mint_id"></a>

```rust
fn mint_id() -> String
```

The `mint_id` function returns a new id in UUID text form.
The time supplies the front of the id. A process counter supplies the end. The version nibble is 4.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
