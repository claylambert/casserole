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

package org.aetheris.lcarsgraph;

/**
 * The graph tables live in the vault file the shell already stores.
 * The statements are plain JDBC strings.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1475 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public final class GraphSql {

    public static final String DDL_NODE =
            "CREATE TABLE IF NOT EXISTS graph_node ("
                    + " canvas_id VARCHAR(36) NOT NULL,"
                    + " name VARCHAR(64) NOT NULL,"
                    + " mark VARCHAR(16) NOT NULL,"
                    + " quiet BOOLEAN NOT NULL DEFAULT FALSE,"
                    + " excluded BOOLEAN NOT NULL DEFAULT FALSE,"
                    + " color VARCHAR(16),"
                    + " PRIMARY KEY (canvas_id, name))";

    public static final String DDL_REFERENT =
            "CREATE TABLE IF NOT EXISTS graph_referent ("
                    + " canvas_id VARCHAR(36) NOT NULL,"
                    + " referent_id VARCHAR(36) NOT NULL,"
                    + " node_name VARCHAR(64) NOT NULL,"
                    + " PRIMARY KEY (canvas_id, referent_id),"
                    + " FOREIGN KEY (canvas_id, node_name) REFERENCES graph_node (canvas_id, name))";

    public static final String DDL_WORD =
            "CREATE TABLE IF NOT EXISTS graph_word ("
                    + " canvas_id VARCHAR(36) NOT NULL,"
                    + " referent_id VARCHAR(36) NOT NULL,"
                    + " word VARCHAR(64) NOT NULL,"
                    + " PRIMARY KEY (canvas_id, referent_id, word),"
                    + " FOREIGN KEY (canvas_id, referent_id) REFERENCES graph_referent (canvas_id, referent_id))";

    public static final String DDL_RELATION =
            "CREATE TABLE IF NOT EXISTS graph_relation ("
                    + " canvas_id VARCHAR(36) NOT NULL,"
                    + " from_name VARCHAR(64) NOT NULL,"
                    + " relation VARCHAR(16) NOT NULL,"
                    + " set_ink VARCHAR(16),"
                    + " PRIMARY KEY (canvas_id, from_name, relation),"
                    + " FOREIGN KEY (canvas_id, from_name) REFERENCES graph_node (canvas_id, name))";

    public static final String DDL_RELATION_END =
            "CREATE TABLE IF NOT EXISTS graph_relation_end ("
                    + " canvas_id VARCHAR(36) NOT NULL,"
                    + " from_name VARCHAR(64) NOT NULL,"
                    + " relation VARCHAR(16) NOT NULL,"
                    + " to_name VARCHAR(64) NOT NULL,"
                    + " PRIMARY KEY (canvas_id, from_name, relation, to_name),"
                    + " FOREIGN KEY (canvas_id, from_name, relation)"
                    + " REFERENCES graph_relation (canvas_id, from_name, relation),"
                    + " FOREIGN KEY (canvas_id, to_name) REFERENCES graph_node (canvas_id, name))";

    public static final String DDL_EVIDENCE =
            "CREATE TABLE IF NOT EXISTS graph_evidence ("
                    + " canvas_id VARCHAR(36) NOT NULL,"
                    + " node_name VARCHAR(64) NOT NULL,"
                    + " cell_id VARCHAR(36) NOT NULL,"
                    + " word VARCHAR(64) NOT NULL,"
                    + " PRIMARY KEY (canvas_id, node_name, cell_id, word),"
                    + " FOREIGN KEY (canvas_id, node_name) REFERENCES graph_node (canvas_id, name))";

    public static final String DDL_CELL_FEED =
            "CREATE TABLE IF NOT EXISTS graph_cell_feed ("
                    + " cell_id VARCHAR(36) NOT NULL,"
                    + " node_name VARCHAR(64) NOT NULL,"
                    + " canvas_id VARCHAR(36) NOT NULL,"
                    + " PRIMARY KEY (cell_id, node_name))";

    public static final String DDL_CELL_FEED_INDEX =
            "CREATE INDEX IF NOT EXISTS graph_cell_feed_canvas ON graph_cell_feed (canvas_id)";

    public static final String DDL_ACT =
            "CREATE TABLE IF NOT EXISTS graph_act ("
                    + " canvas_id VARCHAR(36) NOT NULL,"
                    + " act VARCHAR(16) NOT NULL,"
                    + " node_name VARCHAR(64) NOT NULL DEFAULT '',"
                    + " word VARCHAR(64) NOT NULL DEFAULT '',"
                    + " other_word VARCHAR(64) NOT NULL DEFAULT '',"
                    + " PRIMARY KEY (canvas_id, act, node_name, word, other_word))";

    public static final String DDL_SEAT =
            "CREATE TABLE IF NOT EXISTS graph_seat ("
                    + " canvas_id VARCHAR(36) NOT NULL,"
                    + " layout VARCHAR(16) NOT NULL,"
                    + " node_name VARCHAR(64) NOT NULL,"
                    + " x BIGINT NOT NULL,"
                    + " y BIGINT NOT NULL,"
                    + " PRIMARY KEY (canvas_id, layout, node_name))";

    public static final String DELETE_WORD =
            "DELETE FROM graph_word WHERE canvas_id=?";
    public static final String DELETE_REFERENT =
            "DELETE FROM graph_referent WHERE canvas_id=?";
    public static final String DELETE_RELATION_END =
            "DELETE FROM graph_relation_end WHERE canvas_id=?";
    public static final String DELETE_RELATION =
            "DELETE FROM graph_relation WHERE canvas_id=?";
    public static final String DELETE_EVIDENCE =
            "DELETE FROM graph_evidence WHERE canvas_id=?";
    public static final String DELETE_ACT =
            "DELETE FROM graph_act WHERE canvas_id=?";
    public static final String DELETE_FEED_CANVAS =
            "DELETE FROM graph_cell_feed WHERE canvas_id=?";
    public static final String DELETE_FEED_CELL =
            "DELETE FROM graph_cell_feed WHERE cell_id=?";
    public static final String DELETE_NODE =
            "DELETE FROM graph_node WHERE canvas_id=?";
    public static final String DELETE_SEAT =
            "DELETE FROM graph_seat WHERE canvas_id=?";
    public static final String DELETE_SEAT_LAYOUT =
            "DELETE FROM graph_seat WHERE canvas_id=? AND layout=?";

    public static final String INSERT_NODE =
            "INSERT INTO graph_node (canvas_id, name, mark, quiet, excluded, color)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";
    public static final String INSERT_REFERENT =
            "INSERT INTO graph_referent (canvas_id, referent_id, node_name) VALUES (?, ?, ?)";
    public static final String INSERT_WORD =
            "INSERT INTO graph_word (canvas_id, referent_id, word) VALUES (?, ?, ?)";
    public static final String INSERT_RELATION =
            "INSERT INTO graph_relation (canvas_id, from_name, relation, set_ink) VALUES (?, ?, ?, ?)";
    public static final String INSERT_RELATION_END =
            "INSERT INTO graph_relation_end (canvas_id, from_name, relation, to_name) VALUES (?, ?, ?, ?)";
    public static final String INSERT_EVIDENCE =
            "INSERT INTO graph_evidence (canvas_id, node_name, cell_id, word) VALUES (?, ?, ?, ?)";
    public static final String INSERT_ACT =
            "INSERT INTO graph_act (canvas_id, act, node_name, word, other_word) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_FEED =
            "INSERT INTO graph_cell_feed (cell_id, node_name, canvas_id) VALUES (?, ?, ?)";
    public static final String INSERT_SEAT =
            "INSERT INTO graph_seat (canvas_id, layout, node_name, x, y) VALUES (?, ?, ?, ?, ?)";

    public static final String SELECT_NODE =
            "SELECT name, mark, quiet, excluded, color FROM graph_node"
                    + " WHERE canvas_id=? ORDER BY name";
    public static final String SELECT_REFERENT =
            "SELECT referent_id, node_name FROM graph_referent"
                    + " WHERE canvas_id=? ORDER BY referent_id";
    public static final String SELECT_WORD =
            "SELECT referent_id, word FROM graph_word WHERE canvas_id=? ORDER BY referent_id, word";
    public static final String SELECT_RELATION =
            "SELECT from_name, relation, set_ink FROM graph_relation WHERE canvas_id=?";
    public static final String SELECT_RELATION_END =
            "SELECT from_name, relation, to_name FROM graph_relation_end"
                    + " WHERE canvas_id=? ORDER BY to_name";
    public static final String SELECT_EVIDENCE =
            "SELECT node_name, cell_id, word FROM graph_evidence"
                    + " WHERE canvas_id=? ORDER BY cell_id, word";
    public static final String SELECT_ACT =
            "SELECT act, node_name FROM graph_act WHERE canvas_id=?";
    public static final String SELECT_FEED =
            "SELECT node_name FROM graph_cell_feed WHERE cell_id=? AND node_name=?";
    public static final String SELECT_SEAT =
            "SELECT node_name, x, y FROM graph_seat WHERE canvas_id=? AND layout=?";
    public static final String SELECT_SEAT_ALL =
            "SELECT layout, node_name, x, y FROM graph_seat WHERE canvas_id=?";

    private GraphSql() {
    }
}
