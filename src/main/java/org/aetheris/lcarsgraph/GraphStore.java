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

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * One canvas owns one graph.
 * The store writes the rows the model holds and reads them back on the same connection.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1475 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public final class GraphStore {

    private final Connection connection;

    /**
     * The constructor keeps the connection the vault already opened.
     *
     * @param connection The open connection.
     */
    public GraphStore(Connection connection) {
        this.connection = connection;
    }

    /**
     * The method writes one canvas and removes a row the model dropped.
     * A length fault is thrown before any delete.
     * A failed commit rolls the write back and reloads the last committed rows.
     *
     * @param model The model that holds the graph.
     * @param canvasId The canvas that owns the rows.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void save(GraphModel model, String canvasId) throws SQLException {
        if (model == null || canvasId == null) {
            throw new IllegalArgumentException("length");
        }
        fit(canvasId, 36);
        check(model);
        boolean prior = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);
            List<String[]> heldSeats = read(GraphSql.SELECT_SEAT_ALL, canvasId, 4);
            deleteChildren(canvasId, false);
            insert(model, canvasId);
            writeSeats(canvasId, heldSeats, model);
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
                // the thrown failure is the one the caller sees
            }
            try {
                load(model, canvasId);
            } catch (SQLException ignored) {
                // the reload matches the tables when the read succeeds
            }
            throw ex;
        } finally {
            try {
                connection.setAutoCommit(prior);
            } catch (SQLException ignored) {
                // the connection returns to the caller
            }
        }
    }

    /**
     * The method reads one canvas into the model.
     * A canvas with no rows leaves the model empty.
     * Node names are read in name order.
     *
     * @param model The model that receives the rows.
     * @param canvasId The canvas that owns the rows.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void load(GraphModel model, String canvasId) throws SQLException {
        if (model == null || canvasId == null) {
            throw new IllegalArgumentException("length");
        }
        pour(model, capture(canvasId));
    }

    /**
     * The method reads one canvas into a snapshot.
     * The snapshot is the rows the model thread pours.
     * The connection stays on the caller.
     *
     * @param canvasId The canvas that owns the rows.
     * @return The return value is that snapshot.
     * @throws SQLException The exception is a JDBC failure.
     */
    Shot capture(String canvasId) throws SQLException {
        if (canvasId == null) {
            throw new IllegalArgumentException("length");
        }
        Shot shot = new Shot();
        shot.nodes = read(GraphSql.SELECT_NODE, canvasId, 5);
        shot.referents = read(GraphSql.SELECT_REFERENT, canvasId, 2);
        shot.words = read(GraphSql.SELECT_WORD, canvasId, 2);
        shot.relations = read(GraphSql.SELECT_RELATION, canvasId, 3);
        shot.ends = read(GraphSql.SELECT_RELATION_END, canvasId, 3);
        shot.evidence = read(GraphSql.SELECT_EVIDENCE, canvasId, 3);
        shot.acts = read(GraphSql.SELECT_ACT, canvasId, 2);
        return shot;
    }

    /**
     * The method copies one model into a snapshot.
     * The copy is the rows a later save writes.
     * The caller keeps the model.
     *
     * @param model The model that holds the graph.
     * @return The return value is that snapshot.
     */
    static Shot snap(GraphModel model) {
        Shot shot = new Shot();
        if (model == null) {
            return shot;
        }
        List<String> seenReferent = new ArrayList<String>();
        List<String> seenRelation = new ArrayList<String>();
        List<String> ids = model.intentIds();
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            shot.nodes.add(new String[] {
                id,
                model.mark(id),
                model.quiet(id) ? "1" : "0",
                model.excluded(id) ? "1" : "0",
                model.color(id)
            });
            List<String> meat = model.meatWords(id);
            for (int w = 0; w < meat.size(); w++) {
                String word = meat.get(w);
                String referent = model.referentOf(word);
                if (referent == null) {
                    referent = stableReferent(id, word);
                }
                if (!seenReferent.contains(referent)) {
                    shot.referents.add(new String[] {referent, id});
                    seenReferent.add(referent);
                }
                shot.words.add(new String[] {referent, word});
            }
            if (model.pinned(id)) {
                shot.acts.add(new String[] {"pin", id});
            }
            if (model.rejected(id)) {
                shot.acts.add(new String[] {"reject", id});
            }
        }
        List<String[]> edges = model.heldEdges();
        for (int i = 0; i < edges.size(); i++) {
            String[] edge = edges.get(i);
            String relationKey = edge[0] + "\n" + edge[1];
            if (!seenRelation.contains(relationKey)) {
                shot.relations.add(new String[] {edge[0], edge[1], null});
                seenRelation.add(relationKey);
            }
            shot.ends.add(new String[] {edge[0], edge[1], edge[2]});
        }
        List<String[]> evidence = model.heldEvidence();
        for (int i = 0; i < evidence.size(); i++) {
            String[] row = evidence.get(i);
            shot.evidence.add(new String[] {row[0], row[1], row[2]});
        }
        return shot;
    }

    /**
     * The method writes one snapshot.
     * The live model stays unread.
     * A failed commit reloads the copy, not the live model.
     *
     * @param shot The snapshot the model thread handed over.
     * @param canvasId The canvas that owns the rows.
     * @throws SQLException The exception is a JDBC failure.
     */
    void saveShot(Shot shot, String canvasId) throws SQLException {
        GraphModel copy = new GraphModel("shot");
        pour(copy, shot);
        save(copy, canvasId);
    }

    /**
     * The method fills one model from a snapshot.
     * A null snapshot leaves the model empty.
     *
     * @param model The model that receives the rows.
     * @param shot The snapshot the vault thread read.
     */
    static void pour(GraphModel model, Shot shot) {
        if (model == null) {
            return;
        }
        if (shot == null) {
            model.vacant();
            return;
        }
        List<String[]> nodes = shot.nodes;
        List<String[]> referents = shot.referents;
        List<String[]> words = shot.words;
        List<String[]> relations = shot.relations;
        List<String[]> ends = shot.ends;
        List<String[]> evidence = shot.evidence;
        List<String[]> acts = shot.acts;
        model.vacant();
        for (int i = 0; i < nodes.size(); i++) {
            String[] row = nodes.get(i);
            String name = row[0];
            model.addIntent(name);
            model.setMark(name, row[1]);
            model.setQuiet(name, "1".equals(row[2]) || "TRUE".equalsIgnoreCase(row[2]));
            if ("1".equals(row[3]) || "TRUE".equalsIgnoreCase(row[3])) {
                model.exclude(name);
            }
            model.setColor(name, row[4]);
        }
        Map<String, List<String>> surfaces = new LinkedHashMap<String, List<String>>();
        for (int i = 0; i < words.size(); i++) {
            String[] row = words.get(i);
            List<String> list = surfaces.get(row[0]);
            if (list == null) {
                list = new ArrayList<String>();
                surfaces.put(row[0], list);
            }
            list.add(row[1]);
        }
        for (int i = 0; i < referents.size(); i++) {
            String[] row = referents.get(i);
            List<String> list = surfaces.get(row[0]);
            if (list == null) {
                continue;
            }
            String[] array = list.toArray(new String[list.size()]);
            model.referent(row[0], row[1], array);
        }
        for (int i = 0; i < relations.size(); i++) {
            String[] relation = relations.get(i);
            List<String> targets = new ArrayList<String>();
            for (int e = 0; e < ends.size(); e++) {
                String[] end = ends.get(e);
                if (end[0].equals(relation[0]) && end[1].equals(relation[1])) {
                    targets.add(end[2]);
                }
            }
            if (targets.isEmpty()) {
                continue;
            }
            String[] array = targets.toArray(new String[targets.size()]);
            model.relate(relation[1], relation[0], array);
        }
        for (int i = 0; i < evidence.size(); i++) {
            String[] row = evidence.get(i);
            model.evidence(row[0], row[1], row[2]);
        }
        for (int i = 0; i < acts.size(); i++) {
            String[] row = acts.get(i);
            if ("pin".equals(row[0])) {
                model.seatPinned(row[1], true);
            } else if ("reject".equals(row[0])) {
                model.seatRejected(row[1], true);
            }
        }
    }

    /**
     * The method stores one cell feed row.
     * The feed is not an intent node.
     *
     * @param cellId The cell that owns the row.
     * @param nodeName The intent name on that cell.
     * @param canvasId The canvas that owns the cell.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void insertFeed(String cellId, String nodeName, String canvasId) throws SQLException {
        fit(cellId, 36);
        fit(nodeName, 64);
        fit(canvasId, 36);
        PreparedStatement st = connection.prepareStatement(GraphSql.INSERT_FEED);
        try {
            st.setString(1, cellId);
            st.setString(2, nodeName);
            st.setString(3, canvasId);
            st.executeUpdate();
        } finally {
            st.close();
        }
    }

    /**
     * The method reports whether one feed row is present.
     *
     * @param cellId The cell id.
     * @param nodeName The intent name.
     * @return True when that row is stored.
     * @throws SQLException The exception is a JDBC failure.
     */
    public boolean feedHeld(String cellId, String nodeName) throws SQLException {
        PreparedStatement st = connection.prepareStatement(GraphSql.SELECT_FEED);
        try {
            st.setString(1, cellId);
            st.setString(2, nodeName);
            ResultSet rs = st.executeQuery();
            try {
                return rs.next();
            } finally {
                rs.close();
            }
        } finally {
            st.close();
        }
    }

    /**
     * The method removes the feed rows for one cell.
     * The call joins the transaction the caller already opened.
     *
     * @param cellId The cell whose feed rows go.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void deleteFeed(String cellId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(GraphSql.DELETE_FEED_CELL);
        try {
            st.setString(1, cellId);
            st.executeUpdate();
        } finally {
            st.close();
        }
    }

    /**
     * The method removes the feed rows for one canvas.
     * The call joins the transaction the caller already opened.
     *
     * @param canvasId The canvas whose feed rows go.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void deleteFeedForCanvas(String canvasId) throws SQLException {
        execute(GraphSql.DELETE_FEED_CANVAS, canvasId);
    }

    /**
     * The method removes every graph row for one canvas.
     * The caller that removes a canvas uses this method.
     *
     * @param canvasId The canvas whose graph goes.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void deleteCanvas(String canvasId) throws SQLException {
        if (canvasId == null) {
            throw new IllegalArgumentException("length");
        }
        boolean prior = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);
            deleteChildren(canvasId, true);
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
                // the thrown failure is the one the caller sees
            }
            throw ex;
        } finally {
            try {
                connection.setAutoCommit(prior);
            } catch (SQLException ignored) {
                // the connection returns to the caller
            }
        }
    }

    private void check(GraphModel model) {
        List<String> ids = model.intentIds();
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            fit(id, 64);
            String mark = model.mark(id);
            if (!"stated".equals(mark) && !"inferred".equals(mark)) {
                throw new IllegalArgumentException("mark");
            }
            fitOrNull(model.color(id), 16);
            List<String> words = model.meatWords(id);
            for (int w = 0; w < words.size(); w++) {
                String word = words.get(w);
                fit(word, 64);
                String referent = model.referentOf(word);
                if (referent != null) {
                    fit(referent, 36);
                }
            }
        }
        List<String[]> edges = model.heldEdges();
        for (int i = 0; i < edges.size(); i++) {
            String[] edge = edges.get(i);
            fit(edge[0], 64);
            fit(edge[1], 16);
            fit(edge[2], 64);
        }
        List<String[]> evidence = model.heldEvidence();
        for (int i = 0; i < evidence.size(); i++) {
            String[] row = evidence.get(i);
            if (!model.hasIntent(row[0])) {
                continue;
            }
            fit(row[0], 64);
            fit(row[1], 36);
            fit(row[2], 64);
        }
    }

    private void insert(GraphModel model, String canvasId) throws SQLException {
        List<String> ids = model.intentIds();
        PreparedStatement nodes = connection.prepareStatement(GraphSql.INSERT_NODE);
        try {
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                nodes.setString(1, canvasId);
                nodes.setString(2, id);
                nodes.setString(3, model.mark(id));
                nodes.setBoolean(4, model.quiet(id));
                nodes.setBoolean(5, model.excluded(id));
                String color = model.color(id);
                if (color == null) {
                    nodes.setNull(6, Types.VARCHAR);
                } else {
                    nodes.setString(6, color);
                }
                nodes.executeUpdate();
            }
        } finally {
            nodes.close();
        }
        List<String> seenReferent = new ArrayList<String>();
        List<String> seenWord = new ArrayList<String>();
        PreparedStatement referents = connection.prepareStatement(GraphSql.INSERT_REFERENT);
        PreparedStatement words = connection.prepareStatement(GraphSql.INSERT_WORD);
        try {
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                List<String> meat = model.meatWords(id);
                for (int w = 0; w < meat.size(); w++) {
                    String word = meat.get(w);
                    String referent = model.referentOf(word);
                    if (referent == null) {
                        referent = stableReferent(id, word);
                    }
                    if (!seenReferent.contains(referent)) {
                        referents.setString(1, canvasId);
                        referents.setString(2, referent);
                        referents.setString(3, id);
                        referents.executeUpdate();
                        seenReferent.add(referent);
                    }
                    String key = referent + "\n" + word;
                    if (!seenWord.contains(key)) {
                        words.setString(1, canvasId);
                        words.setString(2, referent);
                        words.setString(3, word);
                        words.executeUpdate();
                        seenWord.add(key);
                    }
                }
            }
        } finally {
            words.close();
            referents.close();
        }
        List<String> seenRelation = new ArrayList<String>();
        List<String> seenEnd = new ArrayList<String>();
        PreparedStatement relations = connection.prepareStatement(GraphSql.INSERT_RELATION);
        PreparedStatement ends = connection.prepareStatement(GraphSql.INSERT_RELATION_END);
        try {
            List<String[]> edges = model.heldEdges();
            for (int i = 0; i < edges.size(); i++) {
                String[] edge = edges.get(i);
                String relationKey = edge[0] + "\n" + edge[1];
                if (!seenRelation.contains(relationKey)) {
                    relations.setString(1, canvasId);
                    relations.setString(2, edge[0]);
                    relations.setString(3, edge[1]);
                    relations.setNull(4, Types.VARCHAR);
                    relations.executeUpdate();
                    seenRelation.add(relationKey);
                }
                String endKey = relationKey + "\n" + edge[2];
                if (!seenEnd.contains(endKey)) {
                    ends.setString(1, canvasId);
                    ends.setString(2, edge[0]);
                    ends.setString(3, edge[1]);
                    ends.setString(4, edge[2]);
                    ends.executeUpdate();
                    seenEnd.add(endKey);
                }
            }
        } finally {
            ends.close();
            relations.close();
        }
        PreparedStatement evidence = connection.prepareStatement(GraphSql.INSERT_EVIDENCE);
        try {
            List<String> seen = new ArrayList<String>();
            List<String[]> rows = model.heldEvidence();
            for (int i = 0; i < rows.size(); i++) {
                String[] row = rows.get(i);
                if (!model.hasIntent(row[0])) {
                    continue;
                }
                String key = row[0] + "\n" + row[1] + "\n" + row[2];
                if (seen.contains(key)) {
                    continue;
                }
                evidence.setString(1, canvasId);
                evidence.setString(2, row[0]);
                evidence.setString(3, row[1]);
                evidence.setString(4, row[2]);
                evidence.executeUpdate();
                seen.add(key);
            }
        } finally {
            evidence.close();
        }
        PreparedStatement acts = connection.prepareStatement(GraphSql.INSERT_ACT);
        try {
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                if (model.pinned(id)) {
                    bindAct(acts, canvasId, "pin", id);
                }
                if (model.rejected(id)) {
                    bindAct(acts, canvasId, "reject", id);
                }
                if (model.excluded(id)) {
                    bindAct(acts, canvasId, "exclude", id);
                }
            }
        } finally {
            acts.close();
        }
    }

    private static void bindAct(PreparedStatement acts, String canvasId, String act, String id) throws SQLException {
        acts.setString(1, canvasId);
        acts.setString(2, act);
        acts.setString(3, id);
        acts.setString(4, "");
        acts.setString(5, "");
        acts.executeUpdate();
    }

    private void deleteChildren(String canvasId, boolean feed) throws SQLException {
        execute(GraphSql.DELETE_WORD, canvasId);
        execute(GraphSql.DELETE_REFERENT, canvasId);
        execute(GraphSql.DELETE_RELATION_END, canvasId);
        execute(GraphSql.DELETE_RELATION, canvasId);
        execute(GraphSql.DELETE_EVIDENCE, canvasId);
        execute(GraphSql.DELETE_ACT, canvasId);
        if (feed) {
            execute(GraphSql.DELETE_FEED_CANVAS, canvasId);
        }
        execute(GraphSql.DELETE_SEAT, canvasId);
        execute(GraphSql.DELETE_NODE, canvasId);
    }

    /**
     * The method replaces the seats of one layout.
     * A length fault is thrown before any delete.
     *
     * @param canvasId The canvas that owns the rows.
     * @param layout The layout name is layered, force, or radial.
     * @param rows Each row is the painted id, the x, and the y.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void keep(String canvasId, String layout, List<String[]> rows) throws SQLException {
        if (canvasId == null || layout == null || rows == null) {
            throw new IllegalArgumentException("length");
        }
        fit(canvasId, 36);
        fit(layout, 16);
        for (int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row == null || row.length < 3) {
                throw new IllegalArgumentException("length");
            }
            fit(row[0], 64);
            parseSeat(row[1]);
            parseSeat(row[2]);
        }
        boolean prior = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);
            PreparedStatement drop = connection.prepareStatement(GraphSql.DELETE_SEAT_LAYOUT);
            try {
                drop.setString(1, canvasId);
                drop.setString(2, layout);
                drop.executeUpdate();
            } finally {
                drop.close();
            }
            PreparedStatement insert = connection.prepareStatement(GraphSql.INSERT_SEAT);
            try {
                for (int i = 0; i < rows.size(); i++) {
                    String[] row = rows.get(i);
                    insert.setString(1, canvasId);
                    insert.setString(2, layout);
                    insert.setString(3, row[0]);
                    insert.setLong(4, parseSeat(row[1]));
                    insert.setLong(5, parseSeat(row[2]));
                    insert.executeUpdate();
                }
            } finally {
                insert.close();
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
                // the thrown failure is the one the caller sees
            }
            throw ex;
        } finally {
            try {
                connection.setAutoCommit(prior);
            } catch (SQLException ignored) {
                // the connection returns to the caller
            }
        }
    }

    /**
     * The method reads the seats of one layout.
     *
     * @param canvasId The canvas that owns the rows.
     * @param layout The layout name is layered, force, or radial.
     * @return Each row is the painted id, the x, and the y.
     * @throws SQLException The exception is a JDBC failure.
     */
    public List<String[]> readSeats(String canvasId, String layout) throws SQLException {
        if (canvasId == null || layout == null) {
            throw new IllegalArgumentException("length");
        }
        List<String[]> rows = new ArrayList<String[]>();
        PreparedStatement st = connection.prepareStatement(GraphSql.SELECT_SEAT);
        try {
            st.setString(1, canvasId);
            st.setString(2, layout);
            ResultSet rs = st.executeQuery();
            try {
                while (rs.next()) {
                    rows.add(new String[] {rs.getString(1), rs.getString(2), rs.getString(3)});
                }
            } finally {
                rs.close();
            }
        } finally {
            st.close();
        }
        return rows;
    }

    /**
     * The method deletes the seats of one layout.
     *
     * @param canvasId The canvas that owns the rows.
     * @param layout The layout name is layered, force, or radial.
     * @throws SQLException The exception is a JDBC failure.
     */
    public void dropLayout(String canvasId, String layout) throws SQLException {
        if (canvasId == null || layout == null) {
            return;
        }
        PreparedStatement st = connection.prepareStatement(GraphSql.DELETE_SEAT_LAYOUT);
        try {
            st.setString(1, canvasId);
            st.setString(2, layout);
            st.executeUpdate();
        } finally {
            st.close();
        }
    }

    private void writeSeats(String canvasId, List<String[]> held, GraphModel model) throws SQLException {
        PreparedStatement insert = connection.prepareStatement(GraphSql.INSERT_SEAT);
        try {
            for (int i = 0; i < held.size(); i++) {
                String[] row = held.get(i);
                if (row == null || row.length < 4 || row[0] == null || row[1] == null) {
                    continue;
                }
                if (!stillPainted(model, row[1])) {
                    continue;
                }
                insert.setString(1, canvasId);
                insert.setString(2, row[0]);
                insert.setString(3, row[1]);
                insert.setLong(4, parseSeat(row[2]));
                insert.setLong(5, parseSeat(row[3]));
                insert.executeUpdate();
            }
        } finally {
            insert.close();
        }
    }

    private static boolean stillPainted(GraphModel model, String name) {
        if (model.hasIntent(name)) {
            return true;
        }
        List<String> ids = model.intentIds();
        for (int i = 0; i < ids.size(); i++) {
            if (model.meatWords(ids.get(i)).contains(name)) {
                return true;
            }
        }
        return false;
    }

    private static long parseSeat(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("length");
        }
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("length");
        }
    }

    private void execute(String sql, String canvasId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(sql);
        try {
            st.setString(1, canvasId);
            st.executeUpdate();
        } finally {
            st.close();
        }
    }

    private List<String[]> read(String sql, String canvasId, int columns) throws SQLException {
        List<String[]> rows = new ArrayList<String[]>();
        PreparedStatement st = connection.prepareStatement(sql);
        try {
            st.setString(1, canvasId);
            ResultSet rs = st.executeQuery();
            try {
                while (rs.next()) {
                    String[] row = new String[columns];
                    for (int c = 0; c < columns; c++) {
                        row[c] = rs.getString(c + 1);
                    }
                    rows.add(row);
                }
            } finally {
                rs.close();
            }
        } finally {
            st.close();
        }
        return rows;
    }

    private static String stableReferent(String intentId, String word) {
        String key = intentId + "\n" + word;
        return UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8)).toString();
    }

    private static void fit(String value, int max) {
        if (value == null || value.length() > max) {
            throw new IllegalArgumentException("length");
        }
    }

    private static void fitOrNull(String value, int max) {
        if (value != null && value.length() > max) {
            throw new IllegalArgumentException("length");
        }
    }

    /**
     * One snapshot is the rows of one canvas.
     * The vault thread reads it. The model thread pours it. The vault thread writes a copy of it.
     */
    static final class Shot {
        private List<String[]> nodes = new ArrayList<String[]>();
        private List<String[]> referents = new ArrayList<String[]>();
        private List<String[]> words = new ArrayList<String[]>();
        private List<String[]> relations = new ArrayList<String[]>();
        private List<String[]> ends = new ArrayList<String[]>();
        private List<String[]> evidence = new ArrayList<String[]>();
        private List<String[]> acts = new ArrayList<String[]>();
    }
}
