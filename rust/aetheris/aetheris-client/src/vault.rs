// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The module stores one local vault for one SSH user.
//!
//! Java `VaultStore` and `VaultSql` are the rule. The engine is embedded Turso. The file is `aetheris.db` beside the Java `vault` directory. This module does not open `vault.mv.db`. Cloud sync stays off. `push` stays off. `pull` stays off.
//!
//! @aa-sdd G-12 rust-aetheris-client.draft.001.md
//! @author clayl (2026-10-27)

use std::path::{Path, PathBuf};
use std::sync::atomic::{AtomicU64, Ordering};
use std::time::{SystemTime, UNIX_EPOCH};

use turso::Builder;

const DDL_WORKSPACE: &str = "CREATE TABLE IF NOT EXISTS workspace (\
     id VARCHAR(36) PRIMARY KEY,\
     username VARCHAR(128) NOT NULL,\
     name VARCHAR(256) NOT NULL,\
     created_ms BIGINT NOT NULL)";

const DDL_CANVAS: &str = "CREATE TABLE IF NOT EXISTS canvas (\
     id VARCHAR(36) PRIMARY KEY,\
     workspace_id VARCHAR(36) NOT NULL,\
     name VARCHAR(256) NOT NULL,\
     cell_order TEXT,\
     created_ms BIGINT NOT NULL)";

/// `DDL_CELL` creates the version-1 `cell` table when it is missing.
/// `group_id` and `execution_status` arrive in the version-2 step. They are not in this statement.
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

const DDL_TRACE: &str = "CREATE TABLE IF NOT EXISTS trace (\
     id VARCHAR(36) PRIMARY KEY,\
     cell_id VARCHAR(36) NOT NULL,\
     created_ms BIGINT NOT NULL,\
     body TEXT)";

const DDL_SCHEMA_VERSION: &str = "CREATE TABLE IF NOT EXISTS schema_version (\
     id INT PRIMARY KEY,\
     version INT NOT NULL)";

const PROBE_COLLAPSED: &str = "SELECT 1 FROM pragma_table_info('cell') WHERE name='collapsed'";
const ALTER_COLLAPSED: &str =
    "ALTER TABLE cell ADD COLUMN collapsed INTEGER NOT NULL DEFAULT 0";
const PROBE_HEAD_LOCK: &str = "SELECT 1 FROM pragma_table_info('cell') WHERE name='head_lock'";
const ALTER_HEAD_LOCK: &str =
    "ALTER TABLE cell ADD COLUMN head_lock INTEGER NOT NULL DEFAULT 0";
const SELECT_SCHEMA_VERSION: &str = "SELECT version FROM schema_version WHERE id=1";
const MERGE_SCHEMA_VERSION: &str = "INSERT INTO schema_version (id, version) VALUES (1, ?1) \
     ON CONFLICT(id) DO UPDATE SET version=excluded.version";
const PROBE_V2: &str = "SELECT group_id, execution_status FROM cell WHERE 1=0";
const ALTER_GROUP_ID: &str = "ALTER TABLE cell ADD COLUMN group_id VARCHAR(36)";
const ALTER_EXECUTION_STATUS: &str = "ALTER TABLE cell ADD COLUMN execution_status VARCHAR(32)";

const INSERT_WORKSPACE: &str =
    "INSERT INTO workspace (id, username, name, created_ms) VALUES (?1, ?2, ?3, ?4)";
const INSERT_CANVAS: &str = "INSERT INTO canvas (id, workspace_id, name, cell_order, created_ms) \
     VALUES (?1, ?2, ?3, ?4, ?5)";
const INSERT_CELL: &str = "INSERT INTO cell (id, canvas_id, turn_index, parent_cell_id, branch_id, \
     column_position, execution_target, context_state, is_ephemeral, collapsed, head_lock, user_clob, \
     assistant_clob, created_ms, group_id, execution_status) \
     VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16)";
const UPDATE_CELL: &str = "UPDATE cell SET turn_index=?1, context_state=?2, is_ephemeral=?3, \
     collapsed=?4, head_lock=?5, execution_target=?6, user_clob=?7, assistant_clob=?8, parent_cell_id=?9, \
     branch_id=?10, column_position=?11, group_id=?12, execution_status=?13 WHERE id=?14";
const UPDATE_CANVAS_ORDER: &str = "UPDATE canvas SET cell_order=?1 WHERE id=?2";
const DELETE_TRACE_FOR_CELL: &str = "DELETE FROM trace WHERE cell_id=?1";
const DELETE_CELL: &str = "DELETE FROM cell WHERE id=?1 AND canvas_id=?2";
const SELECT_WORKSPACE: &str =
    "SELECT id, username, name, created_ms FROM workspace WHERE username=?1 ORDER BY created_ms";
const SELECT_CANVAS: &str = "SELECT id, workspace_id, name, cell_order, created_ms FROM canvas \
     WHERE workspace_id=?1 ORDER BY created_ms";
const SELECT_CELLS_V2: &str = "SELECT id, canvas_id, turn_index, parent_cell_id, branch_id, \
     column_position, execution_target, context_state, is_ephemeral, collapsed, head_lock, user_clob, \
     assistant_clob, created_ms, group_id, execution_status FROM cell WHERE canvas_id=?1 \
     ORDER BY turn_index";
const SEARCH_CELLS: &str = "SELECT id FROM cell WHERE canvas_id=?1 AND (user_clob LIKE ?2 \
     ESCAPE '\\' OR assistant_clob LIKE ?3 ESCAPE '\\')";

/// @aa-sdd GK-0154 aetheris-stage-2-finessing.001.md FINESSE:f-222
/// The [`CellRow`] struct is one canvas cell.
/// An ephemeral cell stores the row and writes both text payloads as null. `state` is the context word. `readable` is true when that word is a known token. `user_text` and `assistant_text` are empty when the row is ephemeral.
#[derive(Clone, Debug, PartialEq, Eq)]
pub(crate) struct CellRow {
    pub id: String,
    pub canvas_id: String,
    pub turn_index: i32,
    pub parent_cell_id: String,
    pub branch_id: String,
    pub column_position: i32,
    pub execution_target: String,
    pub state: String,
    pub collapsed: bool,
    pub head_lock: bool,
    pub ephemeral: bool,
    pub readable: bool,
    pub group_id: String,
    pub execution_status: String,
    pub user_text: String,
    pub assistant_text: String,
    pub created_ms: i64,
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The [`CanvasRow`] struct is one canvas and the cells that belong to it.
/// `cell_order` is the comma-separated id list, split and trimmed. `cells` is filled after the canvas row is read.
#[derive(Clone, Debug, PartialEq, Eq)]
pub(crate) struct CanvasRow {
    pub id: String,
    pub workspace_id: String,
    pub name: String,
    pub cell_order: Vec<String>,
    pub created_ms: i64,
    pub cells: Vec<CellRow>,
}

/// @aa-sdd GK-0155 aetheris-stage-2-finessing.001.md FINESSE:f-222
/// The [`Vault`] struct is the open file for one sanitized SSH user.
/// The connection stays until this value drops. `_db` is held so the file stays open until that drop.
pub(crate) struct Vault {
    conn: turso::Connection,
    /// The `_db` field is held so the file stays open until this vault drops.
    /// The connection used for queries is `conn`.
    _db: turso::Database,
    username: String,
}

impl CellRow {
    /// The `included` method returns an empty included cell.
    /// The id is new. The branch is `main`. The state is `INCLUDED`. The turn index is `turn`. The canvas id is `canvas_id`.
    pub(crate) fn included(canvas_id: &str, turn: i32) -> Self {
        Self {
            id: mint_id(),
            canvas_id: canvas_id.to_string(),
            turn_index: turn,
            parent_cell_id: String::new(),
            branch_id: "main".to_string(),
            column_position: 0,
            execution_target: String::new(),
            state: "INCLUDED".to_string(),
            collapsed: false,
            head_lock: false,
            ephemeral: false,
            readable: true,
            group_id: String::new(),
            execution_status: String::new(),
            user_text: String::new(),
            assistant_text: String::new(),
            created_ms: now_ms(),
        }
    }
}

/// The `sanitize_user` function returns a path-safe folder name.
/// Empty text becomes `anon`. `.` becomes `anon`. `..` becomes `anon`. Any character other than an ASCII letter, a digit, `.`, `_`, or `-` makes the whole name `anon`. Any other text is kept.
pub(crate) fn sanitize_user(raw: &str) -> String {
    if raw.is_empty() || raw == "." || raw == ".." {
        return "anon".to_string();
    }
    let ok = raw.chars().all(|ch| {
        ch.is_ascii_alphanumeric() || ch == '.' || ch == '_' || ch == '-'
    });
    if ok { raw.to_string() } else { "anon".to_string() }
}

/// The `vault_file` function returns `home/.aetheris/{user}/aetheris.db`.
/// The user folder is `sanitize_user` of `username`. The Java H2 file stays in `vault/`.
/// @aa-sdd GK-1150 aetheris-rust-client.md
pub(crate) fn vault_file(home: &Path, username: &str) -> PathBuf {
    home.join(".aetheris")
        .join(sanitize_user(username))
        .join("aetheris.db")
}

impl Vault {
    // @aa-sdd PK-601 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-602 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-603 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-604 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-605 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-606 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-607 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-608 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-609 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-610 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-744 docs/specs/common/stages.spec.md
    // @aa-sdd PK-746 docs/specs/common/stages.spec.md
    // @aa-sdd PK-611 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-753 docs/specs/common/stages.spec.md
    // @aa-sdd PK-612 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-613 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd GK-1680 aetheris-stage-2.draft.001.md
    // @aa-sdd GK-1704 aetheris-stages-1-3.finalize.md
    // @aa-sdd PK-614 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-615 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd GK-1731 aetheris-technical-specification.finalize.md
    // @aa-sdd PK-647 docs/specs/common/formal-plan.spec.md
    // @aa-sdd GK-1823 aetheris-plan-f-197.001.md
    // @aa-sdd PK-651 docs/specs/common/formal-plan.spec.md
    // @aa-sdd PK-616 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-617 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-618 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd GK-2113 BF-003.amwp-tab-grey.md
    // @aa-sdd GK-2120 BF-005.vault-default-disk.md
    // @aa-sdd PK-619 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-620 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd GK-2435 rust-aetheris-client.draft.001.md
    // @aa-sdd GK-2463 rust-aetheris-client.draft.001.md
    // @aa-sdd PK-601 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-602 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-603 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-604 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-605 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-606 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-607 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-608 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-609 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-610 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-744 docs/specs/common/stages.spec.md
    // @aa-sdd PK-746 docs/specs/common/stages.spec.md
    // @aa-sdd PK-611 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-753 docs/specs/common/stages.spec.md
    // @aa-sdd PK-612 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-613 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd GK-1680 aetheris-stage-2.draft.001.md
    // @aa-sdd GK-1704 aetheris-stages-1-3.finalize.md
    // @aa-sdd PK-614 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-615 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd GK-1731 aetheris-technical-specification.finalize.md
    // @aa-sdd PK-647 docs/specs/common/formal-plan.spec.md
    // @aa-sdd GK-1823 aetheris-plan-f-197.001.md
    // @aa-sdd PK-651 docs/specs/common/formal-plan.spec.md
    // @aa-sdd PK-616 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-617 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-618 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd GK-2113 BF-003.amwp-tab-grey.md
    // @aa-sdd GK-2120 BF-005.vault-default-disk.md
    // @aa-sdd PK-619 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-620 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd GK-2435 rust-aetheris-client.draft.001.md
    // @aa-sdd GK-2463 rust-aetheris-client.draft.001.md
    // @aa-sdd PK-601 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-602 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-603 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    // @aa-sdd PK-604 docs/specs/rust/aetheris/aetheris-client/src/vault.spec.md
    /// The `open` method opens one local file and applies the translated DDL.
    /// Sync stays off. A parent directory is created when the path is not `:memory:`. A path that is not UTF-8 returns an error. The stored user name is sanitized.
    /// @aa-sdd GK-1151 aetheris-rust-client.md
    pub(crate) async fn open(path: &Path, username: &str) -> Result<Self, String> {
        if path != Path::new(":memory:") {
            if let Some(parent) = path.parent() {
                if !parent.as_os_str().is_empty() {
                    std::fs::create_dir_all(parent).map_err(|err| err.to_string())?;
                }
            }
        }
        let text = path.to_str().ok_or("vault path is not utf-8")?;
        let db = Builder::new_local(text)
            .build()
            .await
            .map_err(|err| err.to_string())?;
        let conn = db.connect().map_err(|err| err.to_string())?;
        let vault = Self {
            conn,
            _db: db,
            username: sanitize_user(username),
        };
        vault.migrate().await?;
        Ok(vault)
    }

    /// The `username` method returns the sanitized user name.
    /// That is the name stored when the vault was opened.
    pub(crate) fn username(&self) -> &str {
        &self.username
    }

    /// The `load_or_create` method loads the canvases for this user.
    /// An empty file gains one workspace named `default` and one canvas named `Canvas 1`. That canvas starts with one included cell. The cell id is the canvas order.
    pub(crate) async fn load_or_create(&self) -> Result<Vec<CanvasRow>, String> {
        let existing = self.load().await?;
        if !existing.is_empty() {
            return Ok(existing);
        }
        let now = now_ms();
        let workspace_id = mint_id();
        let canvas_id = mint_id();
        let cell = CellRow::included(&canvas_id, 1);
        self.exec(
            INSERT_WORKSPACE,
            turso::params![
                workspace_id.clone(),
                self.username.clone(),
                "default".to_string(),
                now
            ],
        )
        .await?;
        self.exec(
            INSERT_CANVAS,
            turso::params![
                canvas_id,
                workspace_id,
                "Canvas 1".to_string(),
                cell.id.clone(),
                now
            ],
        )
        .await?;
        self.insert_cell(&cell).await?;
        self.load().await
    }

    /// The `load` method reads the first workspace for this user, then its canvases, oldest first.
    /// No workspace returns an empty list. A stored `RUNNING` status is written back as `INTERRUPTED`.
    pub(crate) async fn load(&self) -> Result<Vec<CanvasRow>, String> {
        let mut rows = self
            .conn
            .query(
                SELECT_WORKSPACE,
                turso::params![self.username.clone()],
            )
            .await
            .map_err(|err| err.to_string())?;
        let Some(row) = rows.next().await.map_err(|err| err.to_string())? else {
            return Ok(Vec::new());
        };
        let workspace_id = text_col(&row, 0)?;
        drop(rows);

        let mut canvases = Vec::new();
        let mut rows = self
            .conn
            .query(SELECT_CANVAS, turso::params![workspace_id.clone()])
            .await
            .map_err(|err| err.to_string())?;
        while let Some(row) = rows.next().await.map_err(|err| err.to_string())? {
            canvases.push(CanvasRow {
                id: text_col(&row, 0)?,
                workspace_id: workspace_id.clone(),
                name: text_col(&row, 2)?,
                cell_order: parse_order(&text_col(&row, 3)?),
                created_ms: i64_col(&row, 4)?,
                cells: Vec::new(),
            });
        }
        drop(rows);

        for canvas in &mut canvases {
            canvas.cells = self.load_cells(&canvas.id).await?;
        }
        for canvas in &mut canvases {
            for cell in &mut canvas.cells {
                if cell.execution_status == "RUNNING" {
                    cell.execution_status = "INTERRUPTED".to_string();
                    let stored = cell.clone();
                    self.save_cell(&stored).await?;
                }
            }
        }
        Ok(canvases)
    }

    /// The `save_cell` method updates the row.
    /// A missing id is inserted. An ephemeral cell writes both payloads as null. Empty parent, target, group, and status texts are stored as null.
    pub(crate) async fn save_cell(&self, cell: &CellRow) -> Result<(), String> {
        let n = self
            .exec(
                UPDATE_CELL,
                turso::params![
                    cell.turn_index as i64,
                    cell.state.clone(),
                    flag(cell.ephemeral),
                    flag(cell.collapsed),
                    flag(cell.head_lock),
                    empty_to_null(&cell.execution_target),
                    payload(&cell.user_text, cell.ephemeral),
                    payload(&cell.assistant_text, cell.ephemeral),
                    empty_to_null(&cell.parent_cell_id),
                    cell.branch_id.clone(),
                    cell.column_position as i64,
                    empty_to_null(&cell.group_id),
                    empty_to_null(&cell.execution_status),
                    cell.id.clone(),
                ],
            )
            .await?;
        if n == 0 {
            self.insert_cell(cell).await?;
        }
        Ok(())
    }

    /// The `sync_deck` method writes the cells of one canvas.
    /// A cell that left is removed, and its traces go with it. Turn indexes start at 1 in this order. Parent pointers stay as given. An id in `hold` stays. Another canvas stays.
    pub(crate) async fn sync_deck(
        &self,
        canvas_id: &str,
        cells: &[CellRow],
        hold: &[String],
    ) -> Result<(), String> {
        let existing = self.load_cells(canvas_id).await?;
        for old in &existing {
            let kept = cells.iter().any(|cell| cell.id == old.id)
                || hold.iter().any(|id| id == &old.id);
            if !kept {
                self.exec(DELETE_TRACE_FOR_CELL, turso::params![old.id.clone()])
                    .await?;
                self.exec(
                    DELETE_CELL,
                    turso::params![old.id.clone(), canvas_id.to_string()],
                )
                .await?;
            }
        }
        let mut order = Vec::new();
        for (index, cell) in cells.iter().enumerate() {
            let mut stored = cell.clone();
            stored.canvas_id = canvas_id.to_string();
            stored.turn_index = (index as i32) + 1;
            self.save_cell(&stored).await?;
            order.push(stored.id);
        }
        self.save_order(canvas_id, &order).await?;
        Ok(())
    }

    /// The `save_order` method stores the cell order as comma-joined ids.
    /// An empty list stores an empty order.
    pub(crate) async fn save_order(&self, canvas_id: &str, order: &[String]) -> Result<(), String> {
        self.exec(
            UPDATE_CANVAS_ORDER,
            turso::params![join_order(order), canvas_id.to_string()],
        )
        .await?;
        Ok(())
    }

    /// The `search` method returns cell ids whose prompt or reply contains `needle`.
    /// The match is limited to one canvas. An empty canvas id returns no rows. An empty needle returns no rows. Typed `%` and `_` stay literals.
    pub(crate) async fn search(&self, canvas_id: &str, needle: &str) -> Result<Vec<String>, String> {
        if canvas_id.is_empty() || needle.is_empty() {
            return Ok(Vec::new());
        }
        let like = like_contains(needle);
        let mut rows = self
            .conn
            .query(
                SEARCH_CELLS,
                turso::params![canvas_id.to_string(), like.clone(), like],
            )
            .await
            .map_err(|err| err.to_string())?;
        let mut ids = Vec::new();
        while let Some(row) = rows.next().await.map_err(|err| err.to_string())? {
            ids.push(text_col(&row, 0)?);
        }
        Ok(ids)
    }

    /// The `migrate` method creates the tables and applies the later columns.
    /// The workspace, canvas, cell, and trace tables are created if missing. The collapsed column, the head-lock column, and schema version 2 follow.
    async fn migrate(&self) -> Result<(), String> {
        self.exec(DDL_WORKSPACE, ()).await?;
        self.exec(DDL_CANVAS, ()).await?;
        self.exec(DDL_CELL, ()).await?;
        self.exec(DDL_TRACE, ()).await?;
        self.ensure_collapsed().await?;
        self.ensure_head_lock().await?;
        self.ensure_schema_version().await?;
        Ok(())
    }

    /// The `ensure_collapsed` method adds `collapsed` when that column is missing.
    /// A column that is already present is left alone. The added column is an integer with default 0.
    async fn ensure_collapsed(&self) -> Result<(), String> {
        let mut rows = self
            .conn
            .query(PROBE_COLLAPSED, ())
            .await
            .map_err(|err| err.to_string())?;
        let present = rows.next().await.map_err(|err| err.to_string())?.is_some();
        drop(rows);
        if !present {
            self.exec(ALTER_COLLAPSED, ()).await?;
        }
        Ok(())
    }

    /// The `ensure_head_lock` method adds `head_lock` when that column is missing.
    /// A column that is already present is left alone. The added column is an integer with default 0.
    async fn ensure_head_lock(&self) -> Result<(), String> {
        let mut rows = self
            .conn
            .query(PROBE_HEAD_LOCK, ())
            .await
            .map_err(|err| err.to_string())?;
        let present = rows.next().await.map_err(|err| err.to_string())?.is_some();
        drop(rows);
        if !present {
            self.exec(ALTER_HEAD_LOCK, ()).await?;
        }
        Ok(())
    }

    /// The `ensure_schema_version` method moves the file to schema version 2.
    /// A version under 2, or missing version-2 columns, adds `group_id` and `execution_status`. Those adds ignore an error from a column that already exists. Columns still missing after that return an error. Success stores version 2.
    async fn ensure_schema_version(&self) -> Result<(), String> {
        self.exec(DDL_SCHEMA_VERSION, ()).await?;
        let version = self.schema_version().await?;
        if version < 2 || !self.v2_columns_present().await {
            self.add_column_quiet(ALTER_GROUP_ID).await;
            self.add_column_quiet(ALTER_EXECUTION_STATUS).await;
            if !self.v2_columns_present().await {
                return Err("vault version 2 columns are missing".to_string());
            }
            self.exec(MERGE_SCHEMA_VERSION, turso::params![2i64]).await?;
        }
        Ok(())
    }

    /// The `schema_version` method reads the stored version.
    /// No row returns 0.
    async fn schema_version(&self) -> Result<i64, String> {
        let mut rows = self
            .conn
            .query(SELECT_SCHEMA_VERSION, ())
            .await
            .map_err(|err| err.to_string())?;
        let Some(row) = rows.next().await.map_err(|err| err.to_string())? else {
            return Ok(0);
        };
        i64_col(&row, 0)
    }

    /// The `v2_columns_present` method reports whether `group_id` and `execution_status` can be selected.
    /// A failed probe returns false.
    async fn v2_columns_present(&self) -> bool {
        self.conn.query(PROBE_V2, ()).await.is_ok()
    }

    /// The `add_column_quiet` method runs `sql` and ignores the result.
    /// A duplicate column is not reported to the caller.
    async fn add_column_quiet(&self, sql: &str) {
        let _ = self.exec(sql, ()).await;
    }

    /// The `load_cells` method reads the cells of one canvas.
    /// The order is `turn_index`. Each row is parsed by `cell_from_row`.
    async fn load_cells(&self, canvas_id: &str) -> Result<Vec<CellRow>, String> {
        let mut rows = self
            .conn
            .query(SELECT_CELLS_V2, turso::params![canvas_id.to_string()])
            .await
            .map_err(|err| err.to_string())?;
        let mut cells = Vec::new();
        while let Some(row) = rows.next().await.map_err(|err| err.to_string())? {
            cells.push(cell_from_row(&row)?);
        }
        Ok(cells)
    }

    /// The `insert_cell` method inserts one cell row.
    /// An ephemeral cell writes both payloads as null. Empty optional texts are stored as null.
    async fn insert_cell(&self, cell: &CellRow) -> Result<(), String> {
        self.exec(
            INSERT_CELL,
            turso::params![
                cell.id.clone(),
                cell.canvas_id.clone(),
                cell.turn_index as i64,
                empty_to_null(&cell.parent_cell_id),
                cell.branch_id.clone(),
                cell.column_position as i64,
                empty_to_null(&cell.execution_target),
                cell.state.clone(),
                flag(cell.ephemeral),
                flag(cell.collapsed),
                flag(cell.head_lock),
                payload(&cell.user_text, cell.ephemeral),
                payload(&cell.assistant_text, cell.ephemeral),
                cell.created_ms,
                empty_to_null(&cell.group_id),
                empty_to_null(&cell.execution_status),
            ],
        )
        .await?;
        Ok(())
    }

    /// The `exec` method runs one statement.
    /// The returned number is the count from the driver. A driver error becomes the error string.
    async fn exec(&self, sql: &str, params: impl turso::params::IntoParams) -> Result<u64, String> {
        self.conn
            .execute(sql, params)
            .await
            .map_err(|err| err.to_string())
    }
}

/// The `cell_from_row` function builds a [`CellRow`] from one query row.
/// An empty branch becomes `main`. `GHOST` is stored as `EXCLUDED`. An unknown state becomes `INCLUDED` and is not readable. An ephemeral row returns empty prompt and reply text.
fn cell_from_row(row: &turso::Row) -> Result<CellRow, String> {
    let state = text_col(row, 7)?;
    let ephemeral = i64_col(row, 8)? != 0;
    let mut branch = text_col(row, 4)?;
    if branch.is_empty() {
        branch = "main".to_string();
    }
    Ok(CellRow {
        id: text_col(row, 0)?,
        canvas_id: text_col(row, 1)?,
        turn_index: i64_col(row, 2)? as i32,
        parent_cell_id: text_col(row, 3)?,
        branch_id: branch,
        column_position: i64_col(row, 5)? as i32,
        execution_target: text_col(row, 6)?,
        readable: known_token(&state),
        state: from_token(&state),
        ephemeral,
        collapsed: i64_col(row, 9)? != 0,
        head_lock: i64_col(row, 10)? != 0,
        user_text: if ephemeral {
            String::new()
        } else {
            text_col(row, 11)?
        },
        assistant_text: if ephemeral {
            String::new()
        } else {
            text_col(row, 12)?
        },
        created_ms: i64_col(row, 13)?,
        group_id: text_col(row, 14)?,
        execution_status: text_col(row, 15)?,
    })
}

/// The `known_token` function reports whether a context word is recognized.
/// Empty text is recognized. The words are `INCLUDED`, `EXCLUDED`, `GHOST`, `INPUT_ONLY`, and `OUTPUT_ONLY`, ignoring ASCII case.
fn known_token(raw: &str) -> bool {
    let token = raw.trim();
    if token.is_empty() {
        return true;
    }
    matches!(
        token.to_ascii_uppercase().as_str(),
        "INCLUDED" | "EXCLUDED" | "GHOST" | "INPUT_ONLY" | "OUTPUT_ONLY"
    )
}

/// The `from_token` function maps a stored context word to the word this crate keeps.
/// `EXCLUDED` and `GHOST` become `EXCLUDED`. `INPUT_ONLY` and `OUTPUT_ONLY` stay. Every other word, including empty text, becomes `INCLUDED`.
fn from_token(raw: &str) -> String {
    match raw.trim().to_ascii_uppercase().as_str() {
        "EXCLUDED" | "GHOST" => "EXCLUDED".to_string(),
        "INPUT_ONLY" => "INPUT_ONLY".to_string(),
        "OUTPUT_ONLY" => "OUTPUT_ONLY".to_string(),
        _ => "INCLUDED".to_string(),
    }
}

/// The `like_contains` function builds a `LIKE` pattern for a contains match.
/// The pattern starts and ends with `%`. `\`, `%`, and `_` in the needle are escaped with a backslash.
fn like_contains(needle: &str) -> String {
    let mut out = String::from("%");
    for ch in needle.chars() {
        if ch == '\\' || ch == '%' || ch == '_' {
            out.push('\\');
        }
        out.push(ch);
    }
    out.push('%');
    out
}

/// The `parse_order` function splits a comma-joined id list.
/// Each piece is trimmed. Empty pieces are dropped.
fn parse_order(raw: &str) -> Vec<String> {
    raw.split(',')
        .map(str::trim)
        .filter(|part| !part.is_empty())
        .map(str::to_string)
        .collect()
}

/// The `join_order` function joins ids with commas.
/// An empty list returns an empty string.
fn join_order(order: &[String]) -> String {
    order.join(",")
}

/// The `empty_to_null` function turns an empty string into SQL null.
/// Any other string is kept.
fn empty_to_null(raw: &str) -> Option<String> {
    if raw.is_empty() {
        None
    } else {
        Some(raw.to_string())
    }
}

/// The `payload` function chooses the text stored for a prompt or a reply.
/// An ephemeral cell returns null. Any other cell stores `raw`, including an empty string.
fn payload(raw: &str, ephemeral: bool) -> Option<String> {
    if ephemeral { None } else { Some(raw.to_string()) }
}

/// The `flag` function stores a boolean as an integer.
/// True is 1. False is 0.
fn flag(on: bool) -> i64 {
    if on { 1 } else { 0 }
}

/// The `text_col` function reads one column as text.
/// A text value is copied. Any other value, including null, returns an empty string. A driver error is returned.
fn text_col(row: &turso::Row, index: usize) -> Result<String, String> {
    let value = row.get_value(index).map_err(|err| err.to_string())?;
    if let Some(text) = value.as_text() {
        return Ok(text.clone());
    }
    Ok(String::new())
}

/// The `i64_col` function reads one column as an integer.
/// An integer value is copied. A text value is parsed. Any other value returns an error.
fn i64_col(row: &turso::Row, index: usize) -> Result<i64, String> {
    let value = row.get_value(index).map_err(|err| err.to_string())?;
    if let Some(n) = value.as_integer() {
        return Ok(*n);
    }
    if let Some(text) = value.as_text() {
        return text.parse::<i64>().map_err(|err| err.to_string());
    }
    Err("vault column is not an integer".to_string())
}

/// The `value_is_null` function reports a column that is not text, integer, or real.
/// A driver error returns false.
fn value_is_null(row: &turso::Row, index: usize) -> bool {
    let Ok(value) = row.get_value(index) else {
        return false;
    };
    value.as_text().is_none() && value.as_integer().is_none() && value.as_real().is_none()
}

/// The `now_ms` function returns the current time in milliseconds since the Unix epoch.
/// A clock error returns 0.
fn now_ms() -> i64 {
    SystemTime::now()
        .duration_since(UNIX_EPOCH)
        .map(|dur| dur.as_millis() as i64)
        .unwrap_or(0)
}

/// The `mint_id` function returns a new id in UUID text form.
/// The time supplies the front of the id. A process counter supplies the end. The version nibble is 4.
fn mint_id() -> String {
    static N: AtomicU64 = AtomicU64::new(1);
    let n = N.fetch_add(1, Ordering::Relaxed);
    let t = SystemTime::now()
        .duration_since(UNIX_EPOCH)
        .map(|dur| dur.as_nanos())
        .unwrap_or(0);
    format!(
        "{:08x}-{:04x}-4{:03x}-{:04x}-{:012x}",
        (t & 0xffff_ffff) as u32,
        ((t >> 32) & 0xffff) as u16,
        ((t >> 48) & 0x0fff) as u16,
        (0x8000 | (n & 0x3fff)) as u16,
        n & 0xffff_ffff_ffff
    )
}

#[cfg(test)]
/// These functions check the local vault.
mod tests {
    use super::*;

    /// @aa-sdd L37 rust-aetheris-client.draft.001.md
    /// The `DirGuard` struct deletes a temporary directory when it drops.
    /// The path is the directory, not the database file.
    struct DirGuard(PathBuf);

    impl Drop for DirGuard {
        /// The `drop` method deletes the temporary directory.
        /// A delete error is ignored.
        fn drop(&mut self) {
            let _ = std::fs::remove_dir_all(&self.0);
        }
    }

    /// The `scratch` function creates a temporary directory and returns the database path inside it.
    /// An existing directory of that name is removed first. The database file is `aetheris.db`.
    fn scratch(name: &str) -> (DirGuard, PathBuf) {
        let dir = std::env::temp_dir().join(format!(
            "aetheris-vault-{name}-{}-{}",
            std::process::id(),
            now_ms()
        ));
        let _ = std::fs::remove_dir_all(&dir);
        std::fs::create_dir_all(&dir).unwrap();
        let path = dir.join("aetheris.db");
        (DirGuard(dir), path)
    }

    /// The `the_rust_file_sits_beside_the_h2_directory` test checks the database path.
    /// The file is `aetheris.db` under `.aetheris` and the user name. The path does not end in `vault.mv.db`. An empty name, `.`, `..`, and a name with a slash become `anon`.
    #[test]
    fn the_rust_file_sits_beside_the_h2_directory() {
        let home = Path::new("C:/Users/example");
        let path = vault_file(home, "ada");
        assert_eq!(
            path,
            home.join(".aetheris").join("ada").join("aetheris.db")
        );
        assert!(!path.ends_with("vault.mv.db"));
        assert_eq!(sanitize_user(""), "anon");
        assert_eq!(sanitize_user("."), "anon");
        assert_eq!(sanitize_user(".."), "anon");
        assert_eq!(sanitize_user("a/b"), "anon");
        assert_eq!(sanitize_user("a.b_c-1"), "a.b_c-1");
    }

    /// The `one_canvas_goes_in_and_comes_out` test saves one cell and loads it again.
    /// A new file has one canvas named `Canvas 1` and one cell. The saved text, group, status, and target come back. The state is `INCLUDED`. The schema version is 2. No `vault.mv.db` file is created.
    #[tokio::test]
    async fn one_canvas_goes_in_and_comes_out() {
        let (guard, path) = scratch("round");
        let vault = Vault::open(&path, "hannibal").await.unwrap();
        assert_eq!(vault.username(), "hannibal");
        let canvases = vault.load_or_create().await.unwrap();
        assert_eq!(canvases.len(), 1);
        assert_eq!(canvases[0].name, "Canvas 1");
        assert_eq!(canvases[0].cells.len(), 1);
        let mut cell = canvases[0].cells[0].clone();
        cell.user_text = "in".to_string();
        cell.assistant_text = "out".to_string();
        cell.group_id = "4".to_string();
        cell.execution_status = "COMPLETED".to_string();
        cell.execution_target = "ollama".to_string();
        vault.save_cell(&cell).await.unwrap();
        cell.user_text = "second".to_string();
        vault.save_cell(&cell).await.unwrap();
        vault
            .save_order(&canvases[0].id, &[cell.id.clone()])
            .await
            .unwrap();
        drop(vault);

        let again = Vault::open(&path, "hannibal").await.unwrap();
        let loaded = again.load().await.unwrap();
        assert_eq!(loaded.len(), 1);
        assert_eq!(loaded[0].name, "Canvas 1");
        assert_eq!(loaded[0].cells.len(), 1);
        assert_eq!(loaded[0].cells[0].id, cell.id);
        assert_eq!(loaded[0].cells[0].user_text, "second");
        assert_eq!(loaded[0].cells[0].assistant_text, "out");
        assert_eq!(loaded[0].cells[0].group_id, "4");
        assert_eq!(loaded[0].cells[0].execution_status, "COMPLETED");
        assert_eq!(loaded[0].cells[0].execution_target, "ollama");
        assert_eq!(loaded[0].cells[0].state, "INCLUDED");
        assert_eq!(loaded[0].cell_order, vec![cell.id.clone()]);
        assert!(path.is_file());
        assert!(!guard.0.join("vault.mv.db").exists());
        assert!(!guard.0.join("vault").exists());
        assert_eq!(again.schema_version().await.unwrap(), 2);
    }

    /// The `burner_stores_metadata_and_empty_payloads` test saves an ephemeral cell.
    /// The stored user payload is null. The stored assistant payload is null. The loaded text is empty. The target, the state `EXCLUDED`, and the turn index are kept.
    #[tokio::test]
    async fn burner_stores_metadata_and_empty_payloads() {
        let (_guard, path) = scratch("burner");
        let vault = Vault::open(&path, "hannibal").await.unwrap();
        let canvases = vault.load_or_create().await.unwrap();
        let mut cell = canvases[0].cells[0].clone();
        cell.ephemeral = true;
        cell.collapsed = true;
        cell.execution_target = "grok/grok-4.3".to_string();
        cell.state = "EXCLUDED".to_string();
        cell.user_text = "secret prompt".to_string();
        cell.assistant_text = "secret reply".to_string();
        cell.turn_index = 3;
        vault.save_cell(&cell).await.unwrap();
        drop(vault);

        let again = Vault::open(&path, "hannibal").await.unwrap();
        let mut rows = again
            .conn
            .query(
                "SELECT user_clob, assistant_clob FROM cell WHERE id=?1",
                turso::params![cell.id.clone()],
            )
            .await
            .unwrap();
        let row = rows.next().await.unwrap().unwrap();
        assert!(value_is_null(&row, 0));
        assert!(value_is_null(&row, 1));
        drop(rows);
        let loaded = again.load().await.unwrap();
        let got = &loaded[0].cells[0];
        assert!(got.ephemeral);
        assert!(got.collapsed);
        assert_eq!(got.execution_target, "grok/grok-4.3");
        assert_eq!(got.state, "EXCLUDED");
        assert_eq!(got.turn_index, 3);
        assert_eq!(got.user_text, "");
        assert_eq!(got.assistant_text, "");
        assert_eq!(got.id, cell.id);
    }

    /// The `search_keeps_wildcards_literal` test searches for `%` and `_` as literal text.
    /// `100%` matches only the percent cell. `_` matches only the underscored cell. An empty query returns nothing. A different canvas id returns nothing.
    #[tokio::test]
    async fn search_keeps_wildcards_literal() {
        let (_guard, path) = scratch("search");
        let vault = Vault::open(&path, "hannibal").await.unwrap();
        let canvases = vault.load_or_create().await.unwrap();
        let canvas_id = canvases[0].id.clone();
        let mut percent = canvases[0].cells[0].clone();
        percent.user_text = "score 100% done".to_string();
        vault.save_cell(&percent).await.unwrap();
        let mut underscored = CellRow::included(&canvas_id, 2);
        underscored.assistant_text = "a_b".to_string();
        vault.save_cell(&underscored).await.unwrap();
        let mut plain = CellRow::included(&canvas_id, 3);
        plain.user_text = "plain words".to_string();
        vault.save_cell(&plain).await.unwrap();

        let hit = vault.search(&canvas_id, "100%").await.unwrap();
        assert_eq!(hit, vec![percent.id.clone()]);
        let under = vault.search(&canvas_id, "_").await.unwrap();
        assert_eq!(under, vec![underscored.id.clone()]);
        let none = vault.search(&canvas_id, "").await.unwrap();
        assert!(none.is_empty());
        let other = vault.search("other-canvas", "plain").await.unwrap();
        assert!(other.is_empty());
        let back = vault.search(&canvas_id, "100X").await.unwrap();
        assert!(back.is_empty());
    }

    /// The `a_crash_of_running_becomes_interrupted` test reloads a cell whose status is `RUNNING`.
    /// The loaded status is `INTERRUPTED`. The user text stays. A second load stays `INTERRUPTED`.
    #[tokio::test]
    async fn a_crash_of_running_becomes_interrupted() {
        let (_guard, path) = scratch("crash");
        let vault = Vault::open(&path, "hannibal").await.unwrap();
        let canvases = vault.load_or_create().await.unwrap();
        let mut cell = canvases[0].cells[0].clone();
        cell.execution_status = "RUNNING".to_string();
        cell.user_text = "still open".to_string();
        vault.save_cell(&cell).await.unwrap();
        drop(vault);

        let again = Vault::open(&path, "hannibal").await.unwrap();
        let loaded = again.load().await.unwrap();
        assert_eq!(loaded[0].cells[0].execution_status, "INTERRUPTED");
        assert_eq!(loaded[0].cells[0].user_text, "still open");
        drop(again);

        let third = Vault::open(&path, "hannibal").await.unwrap();
        let stayed = third.load().await.unwrap();
        assert_eq!(stayed[0].cells[0].execution_status, "INTERRUPTED");
    }

    /// The `an_old_file_gains_the_version_columns` test opens a cell table that has no later columns.
    /// The schema version becomes 2. A collapsed flag and a group id can then be saved and loaded. Head lock stays false.
    #[tokio::test]
    async fn an_old_file_gains_the_version_columns() {
        let (_guard, path) = scratch("migrate");
        let db = Builder::new_local(path.to_str().unwrap()).build().await.unwrap();
        let conn = db.connect().unwrap();
        conn.execute(
            "CREATE TABLE cell (id VARCHAR(36) PRIMARY KEY, canvas_id VARCHAR(36) NOT NULL, \
             turn_index INT NOT NULL, parent_cell_id VARCHAR(36), branch_id VARCHAR(64) DEFAULT 'main', \
             column_position INT DEFAULT 0, execution_target VARCHAR(256), context_state VARCHAR(32) NOT NULL, \
             is_ephemeral INTEGER NOT NULL DEFAULT 0, user_clob TEXT, assistant_clob TEXT, \
             created_ms BIGINT NOT NULL)",
            (),
        )
        .await
        .unwrap();
        drop(conn);
        drop(db);

        let vault = Vault::open(&path, "hannibal").await.unwrap();
        assert_eq!(vault.schema_version().await.unwrap(), 2);
        let canvases = vault.load_or_create().await.unwrap();
        let mut cell = canvases[0].cells[0].clone();
        cell.collapsed = true;
        cell.group_id = "9".to_string();
        vault.save_cell(&cell).await.unwrap();
        let loaded = vault.load().await.unwrap();
        assert!(loaded[0].cells[0].collapsed);
        assert_eq!(loaded[0].cells[0].group_id, "9");
        assert!(!loaded[0].cells[0].head_lock);
    }

    /// The `a_head_lock_round_trips` test saves a cell with head lock on.
    /// The next load still has head lock. The user text is `kept`.
    #[tokio::test]
    async fn a_head_lock_round_trips() {
        let (_guard, path) = scratch("head-lock");
        let vault = Vault::open(&path, "hannibal").await.unwrap();
        let canvases = vault.load_or_create().await.unwrap();
        let mut cell = canvases[0].cells[0].clone();
        cell.head_lock = true;
        cell.user_text = "kept".to_string();
        vault.save_cell(&cell).await.unwrap();
        drop(vault);
        let again = Vault::open(&path, "hannibal").await.unwrap();
        let loaded = again.load().await.unwrap();
        assert!(loaded[0].cells[0].head_lock);
        assert_eq!(loaded[0].cells[0].user_text, "kept");
    }

    /// The `a_deleted_turn_stays_gone` test syncs a deck that omits one cell.
    /// That cell row is gone. Its trace row is gone. The child cell stays and keeps the old parent id. A second canvas stays. An ephemeral cell added by the next sync stores null payloads and is not found by search.
    #[tokio::test]
    async fn a_deleted_turn_stays_gone() {
        let (_guard, path) = scratch("delete");
        let vault = Vault::open(&path, "hannibal").await.unwrap();
        let canvases = vault.load_or_create().await.unwrap();
        let canvas_id = canvases[0].id.clone();
        let workspace_id = canvases[0].workspace_id.clone();
        let mut kept = canvases[0].cells[0].clone();
        kept.user_text = "kept".to_string();
        vault.save_cell(&kept).await.unwrap();
        let mut gone = CellRow::included(&canvas_id, 2);
        gone.user_text = "gone".to_string();
        vault.save_cell(&gone).await.unwrap();
        vault
            .exec(
                "INSERT INTO trace (id, cell_id, created_ms, body) VALUES (?1, ?2, ?3, ?4)",
                turso::params![mint_id(), gone.id.clone(), now_ms(), "trace".to_string()],
            )
            .await
            .unwrap();
        let mut child = CellRow::included(&canvas_id, 3);
        child.parent_cell_id = gone.id.clone();
        child.user_text = "child".to_string();
        vault.save_cell(&child).await.unwrap();
        let other_id = mint_id();
        let other_cell = CellRow::included(&other_id, 1);
        vault
            .exec(
                INSERT_CANVAS,
                turso::params![
                    other_id.clone(),
                    workspace_id,
                    "Canvas 2".to_string(),
                    other_cell.id.clone(),
                    canvases[0].created_ms + 1
                ],
            )
            .await
            .unwrap();
        vault.save_cell(&other_cell).await.unwrap();

        vault
            .sync_deck(&canvas_id, &[kept.clone(), child.clone()], &[])
            .await
            .unwrap();
        let loaded = vault.load().await.unwrap();
        assert_eq!(loaded.len(), 2);
        assert_eq!(loaded[0].id, canvas_id);
        assert_eq!(loaded[0].cells.len(), 2);
        assert_eq!(loaded[0].cells[0].id, kept.id);
        assert_eq!(loaded[0].cells[0].turn_index, 1);
        assert_eq!(loaded[0].cells[0].user_text, "kept");
        assert_eq!(loaded[0].cells[1].id, child.id);
        assert_eq!(loaded[0].cells[1].turn_index, 2);
        assert_eq!(loaded[0].cells[1].parent_cell_id, gone.id);
        assert_eq!(loaded[0].cells[1].user_text, "child");
        assert_eq!(loaded[1].name, "Canvas 2");
        assert_eq!(loaded[1].cells[0].id, other_cell.id);
        let mut rows = vault
            .conn
            .query(
                "SELECT id FROM cell WHERE id=?1",
                turso::params![gone.id.clone()],
            )
            .await
            .unwrap();
        assert!(rows.next().await.unwrap().is_none());
        drop(rows);
        let mut traces = vault
            .conn
            .query(
                "SELECT id FROM trace WHERE cell_id=?1",
                turso::params![gone.id.clone()],
            )
            .await
            .unwrap();
        assert!(traces.next().await.unwrap().is_none());
        drop(traces);
        let mut burner = CellRow::included(&canvas_id, 4);
        burner.ephemeral = true;
        burner.user_text = "secret prompt".to_string();
        burner.assistant_text = "secret reply".to_string();
        vault
            .sync_deck(
                &canvas_id,
                &[kept.clone(), child.clone(), burner.clone()],
                &[],
            )
            .await
            .unwrap();
        let mut raw = vault
            .conn
            .query(
                "SELECT user_clob, assistant_clob FROM cell WHERE id=?1",
                turso::params![burner.id.clone()],
            )
            .await
            .unwrap();
        let row = raw.next().await.unwrap().unwrap();
        assert!(value_is_null(&row, 0));
        assert!(value_is_null(&row, 1));
        drop(row);
        drop(raw);
        assert!(vault.search(&canvas_id, "secret").await.unwrap().is_empty());
        assert!(!path.with_file_name("vault.mv.db").exists());
    }
}
