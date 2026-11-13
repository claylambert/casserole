/*
 * Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
 * All rights reserved.
 *
 * Author: Clay Lambert
 *
 * Licensed under the MIT License.
 * If you use or modify this source, you must include this copyright notice
 * and the MIT License permission notice in all copies or substantial portions
 * of the software, including modifications.
 */

package org.aetheris.vault;

// @aa-sdd GK-0723 aetheris-stage-2-finessing.001.md FINESSE:f-10
// @aa-sdd GK-0724 aetheris-stage-2-finessing.001.md FINESSE:f-222
// @aa-sdd GK-0725 aetheris-stage-2-finessing.001.md FINESSE:f-9
/**
 * The {@code VaultSql} class holds the SQL text for the vault.
 * The statements are plain JDBC strings.
 * The class does not use Flyway.
 * The class does not use JPA.
 * Version 2 adds {@code group_id} and {@code execution_status} in the same step.
 * The tables are {@code workspace}, {@code canvas}, {@code cell}, {@code trace}, and {@code schema_version}.
 * // @aa-sdd G-12 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd PK-789 docs/specs/common/grok.spec.md
// @aa-sdd PK-843 docs/specs/common/stages.spec.md
// @aa-sdd PK-743 docs/specs/common/stages.spec.md
// @aa-sdd PK-131 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-135 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-929 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-893 docs/specs/common/stages.spec.md
// @aa-sdd PK-930 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-899 docs/specs/common/stages.spec.md
public final class VaultSql {

    public static final String DDL_WORKSPACE =
            "CREATE TABLE IF NOT EXISTS workspace ("
                    + " id VARCHAR(36) PRIMARY KEY,"
                    + " username VARCHAR(128) NOT NULL,"
                    + " name VARCHAR(256) NOT NULL,"
                    + " created_ms BIGINT NOT NULL)";

    public static final String DDL_CANVAS =
            "CREATE TABLE IF NOT EXISTS canvas ("
                    + " id VARCHAR(36) PRIMARY KEY,"
                    + " workspace_id VARCHAR(36) NOT NULL,"
                    + " name VARCHAR(256) NOT NULL,"
                    + " cell_order CLOB,"
                    + " created_ms BIGINT NOT NULL)";

    public static final String DDL_CELL =
            "CREATE TABLE IF NOT EXISTS cell ("
                    + " id VARCHAR(36) PRIMARY KEY,"
                    + " canvas_id VARCHAR(36) NOT NULL,"
                    + " turn_index INT NOT NULL,"
                    + " parent_cell_id VARCHAR(36),"
                    + " branch_id VARCHAR(64) DEFAULT 'main',"
                    + " column_position INT DEFAULT 0,"
                    + " execution_target VARCHAR(256),"
                    + " context_state VARCHAR(32) NOT NULL,"
                    + " is_ephemeral BOOLEAN NOT NULL DEFAULT FALSE,"
                    + " collapsed BOOLEAN NOT NULL DEFAULT FALSE,"
                    + " head_lock BOOLEAN NOT NULL DEFAULT FALSE,"
                    + " user_clob CLOB,"
                    + " assistant_clob CLOB,"
                    + " created_ms BIGINT NOT NULL)";

    public static final String DDL_TRACE =
            "CREATE TABLE IF NOT EXISTS trace ("
                    + " id VARCHAR(36) PRIMARY KEY,"
                    + " cell_id VARCHAR(36) NOT NULL,"
                    + " created_ms BIGINT NOT NULL,"
                    + " body CLOB)";

    public static final String INSERT_WORKSPACE =
            "INSERT INTO workspace (id, username, name, created_ms) VALUES (?, ?, ?, ?)";
    public static final String INSERT_CANVAS =
            "INSERT INTO canvas (id, workspace_id, name, cell_order, created_ms) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_CELL =
            "INSERT INTO cell (id, canvas_id, turn_index, parent_cell_id, branch_id, column_position,"
                    + " execution_target, context_state, is_ephemeral, collapsed, head_lock, user_clob, assistant_clob,"
                    + " created_ms, group_id, execution_status)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_CELL =
            "UPDATE cell SET turn_index=?, context_state=?, is_ephemeral=?, collapsed=?, head_lock=?,"
                    + " execution_target=?, user_clob=?, assistant_clob=?, parent_cell_id=?, branch_id=?,"
                    + " column_position=?, group_id=?, execution_status=? WHERE id=?";
    public static final String PROBE_COLLAPSED =
            "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS"
                    + " WHERE TABLE_NAME='CELL' AND COLUMN_NAME='COLLAPSED'";
    public static final String ALTER_COLLAPSED =
            "ALTER TABLE cell ADD COLUMN collapsed BOOLEAN NOT NULL DEFAULT FALSE";
    public static final String PROBE_HEAD_LOCK =
            "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS"
                    + " WHERE TABLE_NAME='CELL' AND COLUMN_NAME='HEAD_LOCK'";
    public static final String ALTER_HEAD_LOCK =
            "ALTER TABLE cell ADD COLUMN head_lock BOOLEAN NOT NULL DEFAULT FALSE";
    public static final String DELETE_TRACE_FOR_CANVAS =
            "DELETE FROM trace WHERE cell_id IN (SELECT id FROM cell WHERE canvas_id=?)";
    public static final String DELETE_CELLS_FOR_CANVAS =
            "DELETE FROM cell WHERE canvas_id=?";
    /**
     * The statement deletes trace rows for one cell id.
     * The tables have no foreign key, so the caller deletes the traces itself.
     */
    public static final String DELETE_TRACE_FOR_CELL =
            "DELETE FROM trace WHERE cell_id=?";
    /**
     * The statement deletes one cell on one canvas.
     * The same cell id on another canvas is a different row and is left in place.
     */
    public static final String DELETE_CELL =
            "DELETE FROM cell WHERE id=? AND canvas_id=?";
    public static final String UPDATE_CANVAS_ORDER =
            "UPDATE canvas SET cell_order=? WHERE id=?";
    public static final String SELECT_WORKSPACE =
            "SELECT id, username, name, created_ms FROM workspace WHERE username=? ORDER BY created_ms";
    public static final String SELECT_CANVAS =
            "SELECT id, workspace_id, name, cell_order, created_ms FROM canvas WHERE workspace_id=? ORDER BY created_ms";
    /**
     * The statement is the version-1 column list for one canvas.
     * The list does not include {@code group_id}.
     * A load after migration uses {@link #SELECT_CELLS_V2}.
     */
    public static final String SELECT_CELLS =
            "SELECT id, canvas_id, turn_index, parent_cell_id, branch_id, column_position,"
                    + " execution_target, context_state, is_ephemeral, collapsed, user_clob, assistant_clob, created_ms"
                    + " FROM cell WHERE canvas_id=? ORDER BY turn_index";
    public static final String SELECT_CELLS_V2 =
            "SELECT id, canvas_id, turn_index, parent_cell_id, branch_id, column_position,"
                    + " execution_target, context_state, is_ephemeral, collapsed, user_clob, assistant_clob, created_ms,"
                    + " group_id, execution_status, head_lock"
                    + " FROM cell WHERE canvas_id=? ORDER BY turn_index";
    public static final String DDL_SCHEMA_VERSION =
            "CREATE TABLE IF NOT EXISTS schema_version ("
                    + " id INT PRIMARY KEY,"
                    + " version INT NOT NULL)";
    public static final String SELECT_SCHEMA_VERSION =
            "SELECT version FROM schema_version WHERE id=1";
    public static final String MERGE_SCHEMA_VERSION =
            "MERGE INTO schema_version (id, version) KEY(id) VALUES (1, ?)";
    /**
     * The statement checks the version-2 columns with one select.
     * The select asks for {@code group_id} and {@code execution_status} and returns no rows.
     * It is not a separate information-schema probe for each column.
     */
    public static final String PROBE_V2_COLUMNS =
            "SELECT group_id, execution_status FROM cell WHERE 1=0";
    public static final String ALTER_GROUP_ID =
            "ALTER TABLE cell ADD COLUMN group_id VARCHAR(36)";
    public static final String ALTER_EXECUTION_STATUS =
            "ALTER TABLE cell ADD COLUMN execution_status VARCHAR(32)";
    public static final String SEARCH_CELLS =
            "SELECT id FROM cell WHERE canvas_id=?"
                    + " AND (user_clob LIKE ? ESCAPE '\\' OR assistant_clob LIKE ? ESCAPE '\\')";
    public static final String INSERT_TRACE =
            "INSERT INTO trace (id, cell_id, created_ms, body) VALUES (?, ?, ?, ?)";

    /**
     * The constructor stops a caller from creating an instance.
     * The statements are static fields.
     */
    private VaultSql() {
    }
}
/*****************************************************************************************
 * End of Class: VaultSql.java 090226
 ****************************************************************************************/
