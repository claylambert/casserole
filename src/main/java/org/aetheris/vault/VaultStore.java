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

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.aetheris.config.AetherisYaml;
import org.roux.session.RegionOwner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// @aa-sdd GK-0726 aetheris-stage-2-finessing.001.md FINESSE:f-10
// @aa-sdd GK-0727 aetheris-stage-2-finessing.001.md FINESSE:f-222
// @aa-sdd GK-0728 aetheris-plan-f-35.001.md FINESSE:f-35
// @aa-sdd GK-0729 aetheris-stage-2-finessing.001.md FINESSE:f-9
// @aa-sdd GK-0730 FEAT-007.transfer-conversation.md
/**
 * The {@code VaultStore} class is the H2 vault for the shell process.
 * The store talks to H2 with JDBC statements and a Hikari pool.
 * The default store is a file.
 * The ram store is the in-memory vault used for testing.
 * {@link #open} creates the tables and migrates missing columns.
 * A later {@link #load} selects the canvases.
 * Version 2 is the {@code group_id} column and the {@code execution_status} column.
 * A caller loads canvases, saves cells, and deletes turns through this type.
 * // @aa-sdd G-12 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd PK-685 docs/specs/common/grok.spec.md
// @aa-sdd PK-124 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd GK-1318 aetheris-plan-stage-2.001.md
// @aa-sdd PK-125 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-128 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-130 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-136 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-139 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-140 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd GK-1678 aetheris-stage-2.draft.001.md
// @aa-sdd GK-1702 aetheris-stage-2.draft.001.md
// @aa-sdd GK-2081 aetheris-pre-plan-f-35.001.md
// @aa-sdd GK-2121 BF-005.vault-default-disk.md
// @aa-sdd PK-146 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-147 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-717 docs/specs/common/server.spec.md
// @aa-sdd PK-725 docs/specs/common/server.spec.md
public final class VaultStore {

    private final AetherisYaml yaml;
    private final String username;
    private final boolean ram;
    private HikariDataSource dataSource;

    /**
     * The constructor stores the yaml and the sanitized user name.
     * A null yaml becomes a fresh {@link AetherisYaml}.
     * A null user name is sanitized as empty.
     * {@link RegionOwner#sanitize} supplies the stored name.
     * An {@link IllegalArgumentException} from that sanitize stores {@code anon}.
     * The ram flag comes from {@link AetherisYaml#isRamVault}.
     *
     * @param yaml The yaml is the parsed aetheris settings, or null.
     * @param username The user name is the SSH user, or null.
     */
    public VaultStore(AetherisYaml yaml, String username) {
        this.yaml = yaml == null ? new AetherisYaml() : yaml;
        String user;
        try {
            user = RegionOwner.sanitize(username == null ? "" : username);
        } catch (IllegalArgumentException ex) {
            user = "anon";
        }
        this.username = user;
        this.ram = this.yaml.isRamVault();
    }

    /**
     * The {@code isRam} method reports which vault this store opened.
     *
     * @return The return value is true for the in-memory vault.
     */
    public boolean isRam() {
        return ram;
    }

    /**
     * The {@code getUsername} method returns the stored user name.
     *
     * @return The return value is the sanitized name, or {@code anon}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * The {@code open} method opens the pool and applies the table statements.
     * The driver name is {@code org.h2.Driver}.
     * The ram URL is {@code jdbc:h2:mem:aetheris;DB_CLOSE_DELAY=-1}.
     * The file URL is {@code jdbc:h2:file:} plus the absolute path of {@code vault} under {@link #diskDir}.
     * A missing disk directory is created.
     * Backslashes in that path become slashes.
     * The database user is {@code sa}.
     * The password is empty.
     * The pool size is {@link AetherisYaml#getPoolMax}, and a size below 1 becomes 1.
     * The pool name is {@code aetheris-vault}.
     * The method then runs the workspace, canvas, cell, and trace statements from {@link VaultSql}.
     * It adds {@code collapsed} and {@code head_lock} when those columns are missing.
     * It then runs {@link #ensureSchemaVersion}.
     *
     * @throws SQLException The exception is a failure to open the pool or to run a statement.
     */
    public void open() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.h2.Driver");
        if (ram) {
            config.setJdbcUrl("jdbc:h2:mem:aetheris;DB_CLOSE_DELAY=-1");
        } else {
            File dir = diskDir();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File db = new File(dir, "vault");
            config.setJdbcUrl("jdbc:h2:file:" + db.getAbsolutePath().replace('\\', '/'));
        }
        config.setUsername("sa");
        config.setPassword("");
        int pool = yaml.getPoolMax();
        if (pool < 1) {
            pool = 1;
        }
        config.setMaximumPoolSize(pool);
        config.setPoolName("aetheris-vault");
        dataSource = new HikariDataSource(config);
        Connection c = dataSource.getConnection();
        try {
            Statement st = c.createStatement();
            try {
                st.execute(VaultSql.DDL_WORKSPACE);
                st.execute(VaultSql.DDL_CANVAS);
                st.execute(VaultSql.DDL_CELL);
                st.execute(VaultSql.DDL_TRACE);
                ensureCollapsedColumn(st);
                ensureHeadLockColumn(st);
            } finally {
                st.close();
            }
            ensureSchemaVersion(c);
        } finally {
            c.close();
        }
    }

    /**
     * The {@code close} method closes the pool.
     * A store that was never opened returns without closing.
     * The pool field becomes null after a close.
     */
    public void close() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }

    /**
     * The {@code loadOrCreate} method returns the canvases, creating the first ones when none exist.
     * A {@link #load} that already has a canvas returns that list.
     * An empty load inserts a workspace named {@code default} for this user.
     * That insert also creates a canvas named {@code Canvas 1}.
     * The canvas order starts as the id of one new included cell.
     * The method then loads again and returns that list.
     *
     * @return The return value is the canvases with their cells.
     * @throws SQLException The exception is a JDBC failure.
     */
    public List<CanvasRecord> loadOrCreate() throws SQLException {
        List<CanvasRecord> canvases = load();
        if (canvases.size() > 0) {
            return canvases;
        }
        String wsId = UUID.randomUUID().toString();
        String canvasId = UUID.randomUUID().toString();
        long now = System.currentTimeMillis();
        Connection c = dataSource.getConnection();
        try {
            PreparedStatement ws = c.prepareStatement(VaultSql.INSERT_WORKSPACE);
            try {
                ws.setString(1, wsId);
                ws.setString(2, username);
                ws.setString(3, "default");
                ws.setLong(4, now);
                ws.executeUpdate();
            } finally {
                ws.close();
            }
            CellRecord cell = newCell(canvasId, 1);
            PreparedStatement cv = c.prepareStatement(VaultSql.INSERT_CANVAS);
            try {
                cv.setString(1, canvasId);
                cv.setString(2, wsId);
                cv.setString(3, "Canvas 1");
                cv.setString(4, cell.id);
                cv.setLong(5, now);
                cv.executeUpdate();
            } finally {
                cv.close();
            }
            insertCell(c, cell);
        } finally {
            c.close();
        }
        return load();
    }

    /**
     * The {@code load} method selects canvases for this user.
     * A missing workspace returns an empty list.
     * Each canvas receives its id, name, created time, and cell order.
     * {@link #loadCells} then fills the cells.
     *
     * @return The return value is those canvases.
     * @throws SQLException The exception is a JDBC failure.
     */
    public List<CanvasRecord> load() throws SQLException {
        List<CanvasRecord> out = new ArrayList<CanvasRecord>();
        Connection c = dataSource.getConnection();
        try {
            String workspaceId = null;
            PreparedStatement ws = c.prepareStatement(VaultSql.SELECT_WORKSPACE);
            try {
                ws.setString(1, username);
                ResultSet rs = ws.executeQuery();
                try {
                    if (rs.next()) {
                        workspaceId = rs.getString("id");
                    }
                } finally {
                    rs.close();
                }
            } finally {
                ws.close();
            }
            if (workspaceId == null) {
                return out;
            }
            PreparedStatement cv = c.prepareStatement(VaultSql.SELECT_CANVAS);
            try {
                cv.setString(1, workspaceId);
                ResultSet rs = cv.executeQuery();
                try {
                    while (rs.next()) {
                        CanvasRecord canvas = new CanvasRecord();
                        canvas.id = rs.getString("id");
                        canvas.workspaceId = workspaceId;
                        canvas.name = rs.getString("name");
                        canvas.createdMs = rs.getLong("created_ms");
                        parseOrder(canvas, rs.getString("cell_order"));
                        out.add(canvas);
                    }
                } finally {
                    rs.close();
                }
            } finally {
                cv.close();
            }
            for (int i = 0; i < out.size(); i++) {
                loadCells(c, out.get(i));
            }
        } finally {
            c.close();
        }
        return out;
    }

    /**
     * The {@code saveCell} method writes one cell.
     * An update that changes no row inserts the cell.
     * An ephemeral cell stores null text columns.
     *
     * @param cell The cell is the row to store.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void saveCell(CellRecord cell) throws SQLException {
        Connection c = dataSource.getConnection();
        try {
            PreparedStatement upd = c.prepareStatement(VaultSql.UPDATE_CELL);
            try {
                bindUpdate(upd, cell);
                int n = upd.executeUpdate();
                if (n == 0) {
                    insertCell(c, cell);
                }
            } finally {
                upd.close();
            }
        } finally {
            c.close();
        }
    }

    /**
     * The {@code saveOrder} method stores the canvas cell order.
     * The ids are joined by {@link #joinOrder}.
     *
     * @param canvasId The canvas id is the canvas to update.
     * @param order The order is the cell ids in display order.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void saveOrder(String canvasId, List<String> order) throws SQLException {
        Connection c = dataSource.getConnection();
        try {
            PreparedStatement st = c.prepareStatement(VaultSql.UPDATE_CANVAS_ORDER);
            try {
                st.setString(1, joinOrder(order));
                st.setString(2, canvasId);
                st.executeUpdate();
            } finally {
                st.close();
            }
        } finally {
            c.close();
        }
    }

    /**
     * The {@code clearCanvas} method deletes every turn on one canvas.
     * Trace rows for those cells are deleted first.
     * Ephemeral cells are deleted with the rest.
     * The method then inserts one empty included cell at turn 1.
     *
     * @param canvasId The canvas id is the canvas to clear.
     * @return The return value is that new cell.
     * @throws SQLException The exception is a JDBC failure.
     */
    public CellRecord clearCanvas(String canvasId) throws SQLException {
        Connection c = dataSource.getConnection();
        try {
            PreparedStatement traces = c.prepareStatement(VaultSql.DELETE_TRACE_FOR_CANVAS);
            try {
                traces.setString(1, canvasId);
                traces.executeUpdate();
            } finally {
                traces.close();
            }
            PreparedStatement cells = c.prepareStatement(VaultSql.DELETE_CELLS_FOR_CANVAS);
            try {
                cells.setString(1, canvasId);
                cells.executeUpdate();
            } finally {
                cells.close();
            }
            CellRecord cell = newCell(canvasId, 1);
            insertCell(c, cell);
            return cell;
        } finally {
            c.close();
        }
    }

    /**
     * The {@code replaceCanvas} method replaces every turn on one canvas.
     * A null prompt list throws {@link SQLException}.
     * A null reply list throws the same exception.
     * Lists of different lengths throw the same exception.
     * The message is {@code replace failed: the table is not a message table}.
     * The copy is one transaction.
     * Trace rows for the canvas are deleted.
     * The old cells are deleted.
     * An empty prompt list leaves one empty included cell.
     * Any other list inserts one cell per pair, in order.
     * A null prompt is stored as empty.
     * A null reply is stored as empty.
     * A failure rolls the transaction back.
     * The thrown failure is the one the caller sees.
     *
     * @param canvasId The canvas id is the canvas to replace.
     * @param prompts The prompts are the cell prompts, paired with replies.
     * @param replies The replies are the cell replies.
     * @return The return value is the new cells, in order.
     * @throws SQLException The exception is a JDBC failure, or the two lists disagree.
     */
    public List<CellRecord> replaceCanvas(String canvasId, List<String> prompts, List<String> replies)
            throws SQLException {
        if (prompts == null || replies == null || prompts.size() != replies.size()) {
            throw new SQLException("replace failed: the table is not a message table");
        }
        Connection c = dataSource.getConnection();
        boolean prior = c.getAutoCommit();
        try {
            c.setAutoCommit(false);
            PreparedStatement traces = c.prepareStatement(VaultSql.DELETE_TRACE_FOR_CANVAS);
            try {
                traces.setString(1, canvasId);
                traces.executeUpdate();
            } finally {
                traces.close();
            }
            PreparedStatement cells = c.prepareStatement(VaultSql.DELETE_CELLS_FOR_CANVAS);
            try {
                cells.setString(1, canvasId);
                cells.executeUpdate();
            } finally {
                cells.close();
            }
            ArrayList<CellRecord> born = new ArrayList<CellRecord>();
            if (prompts.size() == 0) {
                CellRecord cell = newCell(canvasId, 1);
                insertCell(c, cell);
                born.add(cell);
            } else {
                for (int i = 0; i < prompts.size(); i++) {
                    CellRecord cell = newCell(canvasId, i + 1);
                    cell.userText = prompts.get(i) == null ? "" : prompts.get(i);
                    cell.assistantText = replies.get(i) == null ? "" : replies.get(i);
                    insertCell(c, cell);
                    born.add(cell);
                }
            }
            c.commit();
            return born;
        } catch (SQLException ex) {
            try {
                c.rollback();
            } catch (SQLException ignored) {
                // the thrown failure is the one the caller sees
            }
            throw ex;
        } finally {
            try {
                c.setAutoCommit(prior);
            } catch (SQLException ignored) {
                // the connection is closing
            }
            c.close();
        }
    }

    /**
     * The {@code deleteTurn} method removes one cell on this canvas.
     * The call is {@link #deleteTurns} with that single id.
     * Trace rows for that id go with it.
     * The cells that remain store turn indexes from 1, in this order.
     * Parent pointers are not rewritten.
     * Prompt text is not rewritten.
     *
     * @param canvasId The canvas id is the canvas that owns the row.
     * @param cellId The cell id is the row to remove.
     * @param remaining The remaining cells stay, in canvas order.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void deleteTurn(String canvasId, String cellId, List<CellRecord> remaining) throws SQLException {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(cellId);
        deleteTurns(canvasId, ids, remaining);
    }

    /**
     * The {@code deleteTurns} method removes each id on this canvas in one transaction.
     * A null id list skips the deletes.
     * A null id is skipped.
     * An empty id is skipped.
     * Trace rows for a removed id are deleted.
     * The cell delete matches both the cell id and the canvas id.
     * Each remaining cell stores a turn index from 1 in list order.
     * A null remaining list writes an empty canvas order.
     * Parent pointers are not rewritten.
     * Prompt text is not rewritten.
     * The canvas {@code cell_order} becomes the remaining ids.
     * A failure rolls the transaction back.
     *
     * @param canvasId The canvas id is the canvas that owns the rows.
     * @param cellIds The cell ids are the rows to remove.
     * @param remaining The remaining cells stay, in canvas order.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void deleteTurns(String canvasId, List<String> cellIds, List<CellRecord> remaining) throws SQLException {
        Connection c = dataSource.getConnection();
        boolean prior = c.getAutoCommit();
        try {
            c.setAutoCommit(false);
            if (cellIds != null) {
                for (int i = 0; i < cellIds.size(); i++) {
                    String cellId = cellIds.get(i);
                    if (cellId == null || cellId.length() == 0) {
                        continue;
                    }
                    PreparedStatement traces = c.prepareStatement(VaultSql.DELETE_TRACE_FOR_CELL);
                    try {
                        traces.setString(1, cellId);
                        traces.executeUpdate();
                    } finally {
                        traces.close();
                    }
                    PreparedStatement drop = c.prepareStatement(VaultSql.DELETE_CELL);
                    try {
                        drop.setString(1, cellId);
                        drop.setString(2, canvasId);
                        drop.executeUpdate();
                    } finally {
                        drop.close();
                    }
                }
            }
            ArrayList<String> order = new ArrayList<String>();
            if (remaining != null) {
                for (int i = 0; i < remaining.size(); i++) {
                    CellRecord one = remaining.get(i);
                    one.turnIndex = i + 1;
                    PreparedStatement upd = c.prepareStatement(VaultSql.UPDATE_CELL);
                    try {
                        bindUpdate(upd, one);
                        upd.executeUpdate();
                    } finally {
                        upd.close();
                    }
                    order.add(one.id);
                }
            }
            PreparedStatement ord = c.prepareStatement(VaultSql.UPDATE_CANVAS_ORDER);
            try {
                ord.setString(1, joinOrder(order));
                ord.setString(2, canvasId);
                ord.executeUpdate();
            } finally {
                ord.close();
            }
            c.commit();
        } catch (SQLException ex) {
            try {
                c.rollback();
            } catch (SQLException ignored) {
                // the thrown failure is the one that matters
            }
            throw ex;
        } finally {
            try {
                c.setAutoCommit(prior);
            } catch (SQLException ignored) {
                // pool return
            }
            c.close();
        }
    }

    /**
     * The {@code ensureCollapsedColumn} method adds {@code collapsed} when that column is missing.
     * The probe reads {@code INFORMATION_SCHEMA.COLUMNS} for {@code CELL} and {@code COLLAPSED}.
     * A probe row means the column is already present.
     * A missing row runs the alter that adds a boolean defaulting to false.
     *
     * @param st The statement is an open statement on this vault.
     * @throws SQLException The exception is a JDBC failure.
     */
    private void ensureCollapsedColumn(Statement st) throws SQLException {
        ResultSet rs = st.executeQuery(VaultSql.PROBE_COLLAPSED);
        try {
            if (rs.next()) {
                return;
            }
        } finally {
            rs.close();
        }
        st.execute(VaultSql.ALTER_COLLAPSED);
    }

    /**
     * The {@code ensureHeadLockColumn} method adds {@code head_lock} when that column is missing.
     * The probe reads {@code INFORMATION_SCHEMA.COLUMNS} for {@code CELL} and {@code HEAD_LOCK}.
     * A probe row means the column is already present.
     * A missing row runs the alter that adds a boolean defaulting to false.
     *
     * @param st The statement is an open statement on this vault.
     * @throws SQLException The exception is a JDBC failure.
     */
    private void ensureHeadLockColumn(Statement st) throws SQLException {
        ResultSet rs = st.executeQuery(VaultSql.PROBE_HEAD_LOCK);
        try {
            if (rs.next()) {
                return;
            }
        } finally {
            rs.close();
        }
        st.execute(VaultSql.ALTER_HEAD_LOCK);
    }

    /**
     * The {@code ensureSchemaVersion} method records schema version 2.
     * The method creates {@code schema_version} when that table is missing.
     * Version 1 is the tables and no version row.
     * A missing row is read as version 0.
     * A version below 2 adds {@code group_id} and {@code execution_status}.
     * A version row that names 2 while a column is missing runs those alters again.
     * A column that is still missing throws {@link SQLException}.
     * The message is {@code vault version 2 columns are missing}.
     * A successful alter writes version 2 on row id 1.
     *
     * @param c The connection is an open connection on this vault.
     * @throws SQLException The exception is a JDBC failure, or the version-2 columns are still missing.
     */
    private void ensureSchemaVersion(Connection c) throws SQLException {
        Statement ddl = c.createStatement();
        try {
            ddl.execute(VaultSql.DDL_SCHEMA_VERSION);
        } finally {
            ddl.close();
        }
        int version = 0;
        Statement read = c.createStatement();
        try {
            ResultSet rs = read.executeQuery(VaultSql.SELECT_SCHEMA_VERSION);
            try {
                if (rs.next()) {
                    version = rs.getInt(1);
                }
            } finally {
                rs.close();
            }
        } finally {
            read.close();
        }
        if (version < 2 || !v2ColumnsPresent(c)) {
            addColumnQuiet(c, VaultSql.ALTER_GROUP_ID);
            addColumnQuiet(c, VaultSql.ALTER_EXECUTION_STATUS);
            if (!v2ColumnsPresent(c)) {
                throw new SQLException("vault version 2 columns are missing");
            }
            PreparedStatement merge = c.prepareStatement(VaultSql.MERGE_SCHEMA_VERSION);
            try {
                merge.setInt(1, 2);
                merge.executeUpdate();
            } finally {
                merge.close();
            }
        }
    }

    /**
     * The {@code v2ColumnsPresent} method reports whether both version-2 columns answer a select.
     * The select is {@link VaultSql#PROBE_V2_COLUMNS}.
     * A {@link SQLException} returns false.
     *
     * @param c The connection is an open connection on this vault.
     * @return The return value is true when that select succeeds.
     */
    private boolean v2ColumnsPresent(Connection c) {
        Statement st = null;
        try {
            st = c.createStatement();
            ResultSet rs = st.executeQuery(VaultSql.PROBE_V2_COLUMNS);
            rs.close();
            return true;
        } catch (SQLException missing) {
            return false;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ignored) {
                    // probe only
                }
            }
        }
    }

    /**
     * The {@code addColumnQuiet} method runs one alter and ignores a failure.
     * A column that is already present is the usual failure.
     * The statement is closed afterward.
     *
     * @param c The connection is an open connection on this vault.
     * @param sql The sql is the alter statement.
     */
    private void addColumnQuiet(Connection c, String sql) {
        Statement st = null;
        try {
            st = c.createStatement();
            st.execute(sql);
        } catch (SQLException already) {
            // the other column of this step may already be present
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ignored) {
                    // alter only
                }
            }
        }
    }

    /**
     * The {@code addTurn} method inserts one new cell and returns it.
     * The cell comes from {@link #newCell}.
     *
     * @param canvasId The canvas id is the canvas that owns the cell.
     * @param turn The turn is the next turn index.
     * @return The return value is that cell.
     * @throws SQLException The exception is a JDBC failure.
     */
    public CellRecord addTurn(String canvasId, int turn) throws SQLException {
        CellRecord cell = newCell(canvasId, turn);
        Connection c = dataSource.getConnection();
        try {
            insertCell(c, cell);
        } finally {
            c.close();
        }
        return cell;
    }

    /**
     * The {@code search} method returns cell ids whose text contains the needle.
     * A null canvas id returns no ids.
     * An empty canvas id returns no ids.
     * A null needle returns no ids.
     * An empty needle returns no ids.
     * The match uses {@code LIKE} with escape {@code \} on {@code user_clob} and {@code assistant_clob}.
     * A typed {@code %} stays a literal.
     * A typed {@code _} stays a literal.
     *
     * @param canvasId The canvas id is the open canvas.
     * @param needle The needle is the operator text.
     * @return The return value is the matching cell ids.
     * @throws SQLException The exception is a JDBC failure.
     */
    public List<String> search(String canvasId, String needle) throws SQLException {
        List<String> ids = new ArrayList<String>();
        if (canvasId == null || canvasId.length() == 0 || needle == null || needle.length() == 0) {
            return ids;
        }
        Connection c = dataSource.getConnection();
        try {
            PreparedStatement st = c.prepareStatement(VaultSql.SEARCH_CELLS);
            try {
                String like = likeContains(needle);
                st.setString(1, canvasId);
                st.setString(2, like);
                st.setString(3, like);
                ResultSet rs = st.executeQuery();
                try {
                    while (rs.next()) {
                        ids.add(rs.getString("id"));
                    }
                } finally {
                    rs.close();
                }
            } finally {
                st.close();
            }
        } finally {
            c.close();
        }
        return ids;
    }

    /**
     * The {@code insertTrace} method stores one trace row for a tool outcome.
     * A burner cell does not call this method.
     * A null tool name is stored as empty.
     * A null outcome is stored as empty.
     * The body is {@code tool: } plus the name, a newline, {@code outcome: }, and the outcome word.
     *
     * @param cellId The cell id is the cell that owns the trace.
     * @param toolName The tool name is the tool.
     * @param outcome The outcome is the short outcome word.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void insertTrace(String cellId, String toolName, String outcome) throws SQLException {
        Connection c = dataSource.getConnection();
        try {
            PreparedStatement st = c.prepareStatement(VaultSql.INSERT_TRACE);
            try {
                st.setString(1, UUID.randomUUID().toString());
                st.setString(2, cellId);
                st.setLong(3, System.currentTimeMillis());
                st.setString(4, "tool: " + (toolName == null ? "" : toolName)
                        + "\noutcome: " + (outcome == null ? "" : outcome));
                st.executeUpdate();
            } finally {
                st.close();
            }
        } finally {
            c.close();
        }
    }

    /**
     * The {@code likeContains} method wraps a literal needle for a contains match.
     * The pattern starts and ends with {@code %}.
     * A backslash is escaped.
     * {@code %} is escaped.
     * {@code _} is escaped.
     *
     * @param needle The needle is the operator text.
     * @return The return value is the {@code LIKE} pattern.
     */
    static String likeContains(String needle) {
        StringBuilder sb = new StringBuilder();
        sb.append('%');
        for (int i = 0; i < needle.length(); i++) {
            char ch = needle.charAt(i);
            if (ch == '\\' || ch == '%' || ch == '_') {
                sb.append('\\');
            }
            sb.append(ch);
        }
        sb.append('%');
        return sb.toString();
    }

    /**
     * The {@code newCell} method builds one empty included cell.
     * The id is a new random id.
     * The state is {@link CellState#INCLUDED}.
     * The created time is the current time in milliseconds.
     * Fields this method does not set stay at the {@link CellRecord} defaults.
     *
     * @param canvasId The canvas id is the canvas that will own the cell.
     * @param turn The turn is the display turn index.
     * @return The return value is that cell.
     */
    public static CellRecord newCell(String canvasId, int turn) {
        CellRecord cell = new CellRecord();
        cell.id = UUID.randomUUID().toString();
        cell.canvasId = canvasId;
        cell.turnIndex = turn;
        cell.state = CellState.INCLUDED;
        cell.createdMs = System.currentTimeMillis();
        return cell;
    }

    /**
     * The {@code loadCells} method fills one canvas from {@link VaultSql#SELECT_CELLS_V2}.
     * An empty branch id becomes {@code main}.
     * {@link CellState#knownToken} sets {@code readable}.
     * {@link CellState#fromToken} sets the state.
     * {@code collapsed} and {@code head_lock} are copied from the row.
     * An ephemeral cell loads empty user text and empty assistant text.
     * A null parent id, group id, or execution status becomes empty.
     *
     * @param c The connection is an open connection on this vault.
     * @param canvas The canvas receives the cells.
     * @throws SQLException The exception is a JDBC failure.
     */
    private void loadCells(Connection c, CanvasRecord canvas) throws SQLException {
        PreparedStatement st = c.prepareStatement(VaultSql.SELECT_CELLS_V2);
        try {
            st.setString(1, canvas.id);
            ResultSet rs = st.executeQuery();
            try {
                while (rs.next()) {
                    CellRecord cell = new CellRecord();
                    cell.id = rs.getString("id");
                    cell.canvasId = canvas.id;
                    cell.turnIndex = rs.getInt("turn_index");
                    cell.parentCellId = nullToEmpty(rs.getString("parent_cell_id"));
                    cell.branchId = nullToEmpty(rs.getString("branch_id"));
                    if (cell.branchId.length() == 0) {
                        cell.branchId = "main";
                    }
                    cell.columnPosition = rs.getInt("column_position");
                    cell.executionTarget = nullToEmpty(rs.getString("execution_target"));
                    String stateToken = rs.getString("context_state");
                    cell.readable = CellState.knownToken(stateToken);
                    cell.state = CellState.fromToken(stateToken);
                    cell.ephemeral = rs.getBoolean("is_ephemeral");
                    cell.collapsed = rs.getBoolean("collapsed");
                    cell.headLock = rs.getBoolean("head_lock");
                    cell.userText = cell.ephemeral ? "" : nullToEmpty(rs.getString("user_clob"));
                    cell.assistantText = cell.ephemeral ? "" : nullToEmpty(rs.getString("assistant_clob"));
                    cell.createdMs = rs.getLong("created_ms");
                    cell.groupId = nullToEmpty(rs.getString("group_id"));
                    cell.executionStatus = nullToEmpty(rs.getString("execution_status"));
                    canvas.cells.add(cell);
                }
            } finally {
                rs.close();
            }
        } finally {
            st.close();
        }
    }

    /**
     * The {@code insertCell} method inserts one cell row.
     * An empty parent id is stored as null.
     * An empty group id is stored as null.
     * An empty execution status is stored as null.
     * An ephemeral cell stores null text columns.
     *
     * @param c The connection is an open connection on this vault.
     * @param cell The cell is the row to insert.
     * @throws SQLException The exception is a JDBC failure.
     */
    private void insertCell(Connection c, CellRecord cell) throws SQLException {
        PreparedStatement st = c.prepareStatement(VaultSql.INSERT_CELL);
        try {
            st.setString(1, cell.id);
            st.setString(2, cell.canvasId);
            st.setInt(3, cell.turnIndex);
            st.setString(4, emptyToNull(cell.parentCellId));
            st.setString(5, cell.branchId);
            st.setInt(6, cell.columnPosition);
            st.setString(7, cell.executionTarget);
            st.setString(8, cell.state.name());
            st.setBoolean(9, cell.ephemeral);
            st.setBoolean(10, cell.collapsed);
            st.setBoolean(11, cell.headLock);
            if (cell.ephemeral) {
                st.setString(12, null);
                st.setString(13, null);
            } else {
                st.setString(12, cell.userText);
                st.setString(13, cell.assistantText);
            }
            st.setLong(14, cell.createdMs);
            st.setString(15, emptyToNull(cell.groupId));
            st.setString(16, emptyToNull(cell.executionStatus));
            st.executeUpdate();
        } finally {
            st.close();
        }
    }

    /**
     * The {@code bindUpdate} method binds one cell onto {@link VaultSql#UPDATE_CELL}.
     * An ephemeral cell binds null text columns.
     * An empty parent id is bound as null.
     * An empty group id is bound as null.
     * An empty execution status is bound as null.
     *
     * @param st The statement is the update statement.
     * @param cell The cell is the row to bind.
     * @throws SQLException The exception is a JDBC failure.
     */
    private void bindUpdate(PreparedStatement st, CellRecord cell) throws SQLException {
        st.setInt(1, cell.turnIndex);
        st.setString(2, cell.state.name());
        st.setBoolean(3, cell.ephemeral);
        st.setBoolean(4, cell.collapsed);
        st.setBoolean(5, cell.headLock);
        st.setString(6, cell.executionTarget);
        if (cell.ephemeral) {
            st.setString(7, null);
            st.setString(8, null);
        } else {
            st.setString(7, cell.userText);
            st.setString(8, cell.assistantText);
        }
        st.setString(9, emptyToNull(cell.parentCellId));
        st.setString(10, cell.branchId);
        st.setInt(11, cell.columnPosition);
        st.setString(12, emptyToNull(cell.groupId));
        st.setString(13, emptyToNull(cell.executionStatus));
        st.setString(14, cell.id);
    }

    /**
     * The {@code diskDir} method returns the directory for the file vault.
     * An empty {@link AetherisYaml#getVaultPath} uses {@code .aetheris} under the home directory, then the user name, then {@code vault}.
     * {@code ${user.home}} is replaced with the home directory.
     * {@code ${user}} is replaced with the sanitized user name.
     *
     * @return The return value is that directory.
     */
    private File diskDir() {
        String template = yaml.getVaultPath();
        if (template == null || template.trim().length() == 0) {
            template = System.getProperty("user.home") + File.separator + ".aetheris"
                    + File.separator + username + File.separator + "vault";
        }
        String path = template.replace("${user.home}", System.getProperty("user.home"));
        path = path.replace("${user}", username);
        return new File(path);
    }

    /**
     * The {@code parseOrder} method splits a stored cell order into ids.
     * A null value adds nothing.
     * A blank value adds nothing.
     * The text is split on commas.
     * A blank piece is skipped.
     *
     * @param canvas The canvas receives the ids on {@code cellOrder}.
     * @param raw The raw text is the stored {@code cell_order}.
     */
    private static void parseOrder(CanvasRecord canvas, String raw) {
        if (raw == null || raw.trim().length() == 0) {
            return;
        }
        String[] parts = raw.split(",");
        for (int i = 0; i < parts.length; i++) {
            String id = parts[i].trim();
            if (id.length() > 0) {
                canvas.cellOrder.add(id);
            }
        }
    }

    /**
     * The {@code joinOrder} method joins cell ids with commas.
     * A null list returns empty.
     * An empty list returns empty.
     *
     * @param order The order is the cell ids.
     * @return The return value is the comma-joined text.
     */
    private static String joinOrder(List<String> order) {
        if (order == null || order.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < order.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(order.get(i));
        }
        return sb.toString();
    }

    /**
     * The {@code nullToEmpty} method turns a null string into empty text.
     *
     * @param raw The raw text may be null.
     * @return The return value is empty when {@code raw} is null, and otherwise {@code raw}.
     */
    private static String nullToEmpty(String raw) {
        return raw == null ? "" : raw;
    }

    /**
     * The {@code emptyToNull} method turns empty text into null.
     * A null value stays null.
     *
     * @param raw The raw text may be null or empty.
     * @return The return value is null when {@code raw} is null or empty, and otherwise {@code raw}.
     */
    private static String emptyToNull(String raw) {
        if (raw == null || raw.length() == 0) {
            return null;
        }
        return raw;
    }
}
/*****************************************************************************************
 * End of Class: VaultStore.java 090226
 ****************************************************************************************/
