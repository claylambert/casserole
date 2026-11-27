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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingUtilities;

import org.aetheris.vault.VaultStore;
import org.junit.jupiter.api.Test;

/**
 * The vault proof runs on one memory database.
 *
 * @author clayl (2026-11-24)
 */
class GraphVaultTest {

    @Test
    void v1RestartRestoresTheGraph() throws Exception {
        Connection connection = open("v1");
        try {
            GraphModel model = new GraphModel("talk");
            model.addIntent("house");
            model.addIntent("bird");
            model.setMark("bird", "inferred");
            model.takeWords("house", "home");
            model.referent("bird-place", "bird", "wing");
            model.relate("supports", "house", "bird");
            model.relate("pulls against", "bird", "house");
            model.evidence("house", "card-1", "home");
            model.setColor("house", "#E4DED6");
            model.setColor("bird", null);
            model.pin("house");
            model.reject("bird");
            model.setQuiet("bird", false);
            model.exclude("house");
            model.setQuiet("lane", true);
            model.linkWord("and", "house", "bird");
            GraphStore store = new GraphStore(connection);
            store.save(model, "canvas-a");
            GraphModel again = new GraphModel("talk");
            store.load(again, "canvas-a");
            String referent = again.referentOf("home");
            assertTrue(referent != null && referent.length() <= 36);
            assertEquals("bird", again.intentIds().get(0));
            assertEquals("house", again.intentIds().get(1));
            assertEquals("stated", again.mark("house"));
            assertEquals("inferred", again.mark("bird"));
            assertTrue(again.meatWords("house").contains("home"));
            assertEquals(referent, again.referentOf("home"));
            assertTrue(again.surfaces("bird-place").contains("wing"));
            assertEquals("bird", again.targets("house", "supports").get(0));
            assertEquals("house", again.targets("bird", "pulls against").get(0));
            assertTrue(again.walk("house").contains("card-1"));
            assertEquals("#E4DED6", again.color("house"));
            assertNull(again.color("bird"));
            assertTrue(again.pinned("house"));
            assertTrue(again.rejected("bird"));
            assertFalse(again.quiet("bird"));
            assertTrue(again.excluded("house"));
            assertFalse(again.quiet("lane"));
            assertTrue(again.wordEdges("and").isEmpty());
            store.save(again, "canvas-a");
            GraphModel third = new GraphModel("talk");
            store.load(third, "canvas-a");
            assertEquals(referent, third.referentOf("home"));
        } finally {
            connection.close();
        }
    }

    @Test
    void v2FailedCommitAndALongName() throws Exception {
        Connection connection = open("v2");
        try {
            GraphStore store = new GraphStore(connection);
            GraphModel model = new GraphModel("talk");
            model.addIntent("house");
            store.save(model, "canvas-a");
            model.addIntent("bird");
            Statement block = connection.createStatement();
            try {
                block.execute(
                        "CREATE TRIGGER fail_node BEFORE DELETE ON graph_node FOR EACH ROW"
                                + " CALL \"org.aetheris.lcarsgraph.FailWrite\"");
            } finally {
                block.close();
            }
            boolean failed = false;
            try {
                store.save(model, "canvas-a");
            } catch (SQLException ex) {
                failed = true;
            }
            assertTrue(failed);
            Statement clear = connection.createStatement();
            try {
                clear.execute("DROP TRIGGER fail_node");
            } finally {
                clear.close();
            }
            assertTrue(model.hasIntent("house"));
            assertFalse(model.hasIntent("bird"));
            assertEquals(1, count(connection, "SELECT COUNT(*) FROM graph_node"));
            GraphModel prior = new GraphModel("talk");
            prior.addIntent("house");
            store.save(prior, "canvas-b");
            StringBuilder longName = new StringBuilder();
            for (int i = 0; i < 65; i++) {
                longName.append('n');
            }
            prior.addIntent(longName.toString());
            boolean length = false;
            try {
                store.save(prior, "canvas-b");
            } catch (IllegalArgumentException ex) {
                length = true;
            }
            assertTrue(length);
            assertEquals(1, count(connection, "SELECT COUNT(*) FROM graph_node WHERE canvas_id='canvas-b'"));
            assertEquals(1, count(connection, "SELECT COUNT(*) FROM graph_node WHERE canvas_id='canvas-a'"));
        } finally {
            connection.close();
        }
    }

    @Test
    void v3EmptyCanvasStaysEmpty() throws Exception {
        Connection connection = open("v3");
        try {
            GraphModel model = new GraphModel("talk");
            model.addIntent("house");
            new GraphStore(connection).load(model, "canvas-empty");
            assertEquals(0, model.intentCount());
        } finally {
            connection.close();
        }
    }

    @Test
    void v4PictureIsNotStored() throws Exception {
        String sql = joined();
        assertFalse(sql.contains("position"));
        assertFalse(source("GraphSql.java").contains("position"));
    }

    @Test
    void v5VersionMovesToFour() throws Exception {
        Connection connection = open("v5");
        try {
            assertEquals(4, count(connection, "SELECT version FROM schema_version WHERE id=1"));
            new VaultStore(null, "vault").applySchema(connection);
            assertEquals(4, count(connection, "SELECT version FROM schema_version WHERE id=1"));
            assertTrue(count(connection, "SELECT COUNT(*) FROM graph_node") >= 0);
            String store = source("GraphStore.java");
            String sql = source("GraphSql.java");
            assertFalse(store.contains("schema_version"));
            assertFalse(sql.contains("schema_version"));
        } finally {
            connection.close();
        }
    }

    @Test
    void v6GraphTablesStayAmongThemselves() throws Exception {
        String sql = joined();
        assertFalse(sql.contains("REFERENCES workspace"));
        assertFalse(sql.contains("REFERENCES canvas"));
        assertFalse(sql.contains("REFERENCES cell"));
        assertFalse(sql.contains("REFERENCES trace"));
    }

    @Test
    void v7OneMemoryFile() throws Exception {
        String sql = joined();
        assertFalse(sql.contains("CLOB"));
        assertFalse(sql.contains("BLOB"));
        Connection connection = open("v7");
        try {
            assertTrue(connection.getMetaData().getURL().startsWith("jdbc:h2:mem:"));
        } finally {
            connection.close();
        }
    }

    @Test
    void v8StoreDoesNotAsk() throws Exception {
        Class.forName("org.aetheris.lcarsgraph.GraphDslDriver");
        String store = source("GraphStore.java");
        assertFalse(store.contains("GraphDslDriver"));
        GraphModel model = new GraphModel("talk");
        model.addIntent("house");
        int applied = GraphDslDriver.importEmpty(model, java.util.List.of(
                new GraphTurn("card-1", "turn-1", "bird")), new GraphDoor() {
                    @Override
                    public String ask(String brief) {
                        return "intent-pass: 1\nact:\n- shelter born inferred\nshift:\n- shelter added\n";
                    }
                }, null);
        assertEquals(0, applied);
        assertFalse(model.hasIntent("shelter"));
    }

    @Test
    void feedDeleteLeavesTheIntent() throws Exception {
        Connection connection = open("feed");
        try {
            GraphStore store = new GraphStore(connection);
            GraphModel model = new GraphModel("talk");
            model.addIntent("house");
            store.save(model, "canvas-a");
            store.insertFeed("cell-1", "house", "canvas-a");
            assertTrue(store.feedHeld("cell-1", "house"));
            store.deleteFeed("cell-1");
            assertFalse(store.feedHeld("cell-1", "house"));
            assertEquals(1, count(connection, "SELECT COUNT(*) FROM graph_node"));
        } finally {
            connection.close();
        }
    }

    @Test
    void referentIdMustFit() throws Exception {
        Connection connection = open("referent");
        try {
            GraphStore store = new GraphStore(connection);
            GraphModel model = new GraphModel("talk");
            model.addIntent("house");
            store.save(model, "canvas-a");
            StringBuilder id = new StringBuilder();
            for (int i = 0; i < 37; i++) {
                id.append('r');
            }
            model.referent(id.toString(), "house", "home");
            boolean length = false;
            try {
                store.save(model, "canvas-a");
            } catch (IllegalArgumentException ex) {
                length = true;
            }
            assertTrue(length);
            assertEquals(1, count(connection, "SELECT COUNT(*) FROM graph_node"));
            assertEquals(0, count(connection, "SELECT COUNT(*) FROM graph_referent"));
        } finally {
            connection.close();
        }
    }

    @Test
    void twoCanvasesDoNotShareRows() throws Exception {
        Connection connection = open("canvases");
        try {
            GraphStore store = new GraphStore(connection);
            GraphModel first = new GraphModel("talk");
            first.addIntent("house");
            store.save(first, "canvas-a");
            GraphModel second = new GraphModel("talk");
            second.addIntent("bird");
            store.save(second, "canvas-b");
            GraphModel loaded = new GraphModel("talk");
            store.load(loaded, "canvas-a");
            assertTrue(loaded.hasIntent("house"));
            assertFalse(loaded.hasIntent("bird"));
            store.load(loaded, "canvas-b");
            assertTrue(loaded.hasIntent("bird"));
            assertFalse(loaded.hasIntent("house"));
            store.deleteCanvas("canvas-a");
            assertEquals(0, count(connection, "SELECT COUNT(*) FROM graph_node WHERE canvas_id='canvas-a'"));
            assertEquals(1, count(connection, "SELECT COUNT(*) FROM graph_node WHERE canvas_id='canvas-b'"));
        } finally {
            connection.close();
        }
    }

    @Test
    void aReloadClearsAHeldGraph() throws Exception {
        Connection connection = open("reload");
        try {
            GraphStore store = new GraphStore(connection);
            GraphModel seed = new GraphModel("talk");
            seed.addIntent("house");
            store.save(seed, "canvas-r");
            store.insertFeed("card-1", "house", "canvas-r");
            GraphModel other = new GraphModel("talk");
            other.addIntent("bird");
            store.save(other, "canvas-b");
            GraphDesk.seat("reload-user", connection, "canvas-r");
            GraphPane pane = GraphDesk.pane("reload-user", "talk");
            assertTrue(pane.model().hasIntent("house"));
            List<GraphTurn> turns = new ArrayList<GraphTurn>();
            turns.add(new GraphTurn("card-1", "turn-1", "bird"));
            final int[] asked = new int[1];
            GraphDoor door = new GraphDoor() {
                @Override
                public String ask(String brief) {
                    asked[0]++;
                    return "intent-pass: 1\n"
                            + "turn: turn-1\n"
                            + "act:\n- shelter born inferred\n"
                            + "shift:\n- shelter added\n"
                            + "meat:\n- bird on shelter\n"
                            + "evidence:\n- shelter sees bird\n"
                            + "cell:\n- turn-1 shelter\n";
                }
            };
            GraphDesk.hydrate("reload-user", turns, door);
            assertEquals(0, asked[0]);
            assertTrue(pane.model().hasIntent("house"));
            GraphDesk.reload("reload-user", turns, door);
            long end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end) {
                if (!GraphDesk.passing("reload-user") && pane.model().hasIntent("shelter")) {
                    break;
                }
                Thread.sleep(20L);
            }
            assertFalse(GraphDesk.passing("reload-user"));
            assertEquals(1, asked[0]);
            assertTrue(pane.model().hasIntent("shelter"));
            assertFalse(pane.model().hasIntent("house"));
            GraphModel loaded = new GraphModel("talk");
            store.load(loaded, "canvas-r");
            assertTrue(loaded.hasIntent("shelter"));
            assertFalse(loaded.hasIntent("house"));
            assertFalse(store.feedHeld("card-1", "house"));
            assertTrue(store.feedHeld("card-1", "shelter"));
            GraphModel kept = new GraphModel("talk");
            store.load(kept, "canvas-b");
            assertTrue(kept.hasIntent("bird"));
            assertFalse(kept.hasIntent("shelter"));
        } finally {
            connection.close();
        }
    }

    @Test
    void aReloadOfAnEmptyConversationClearsTheGraph() throws Exception {
        Connection connection = open("reload-empty");
        try {
            GraphStore store = new GraphStore(connection);
            GraphModel seed = new GraphModel("talk");
            seed.addIntent("house");
            store.save(seed, "canvas-e");
            GraphDesk.seat("reload-empty", connection, "canvas-e");
            GraphPane pane = GraphDesk.pane("reload-empty", "talk");
            assertTrue(pane.model().hasIntent("house"));
            final int[] asked = new int[1];
            GraphDoor door = new GraphDoor() {
                @Override
                public String ask(String brief) {
                    asked[0]++;
                    return "";
                }
            };
            GraphDesk.reload("reload-empty", new ArrayList<GraphTurn>(), door);
            long end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end) {
                if (!GraphDesk.passing("reload-empty") && pane.model().intentCount() == 0) {
                    break;
                }
                Thread.sleep(20L);
            }
            assertFalse(GraphDesk.passing("reload-empty"));
            assertEquals(0, asked[0]);
            assertEquals(0, pane.model().intentCount());
            GraphModel loaded = new GraphModel("talk");
            store.load(loaded, "canvas-e");
            assertEquals(0, loaded.intentCount());
        } finally {
            connection.close();
        }
    }

    @Test
    void aStopLeavesTheKeptStep() throws Exception {
        Connection connection = open("stop");
        try {
            GraphStore store = new GraphStore(connection);
            GraphModel seed = new GraphModel("talk");
            seed.addIntent("house");
            store.save(seed, "canvas-s");
            GraphDesk.seat("stop-user", connection, "canvas-s");
            GraphPane pane = GraphDesk.pane("stop-user", "talk");
            assertTrue(pane.model().hasIntent("house"));
            StringBuilder longWords = new StringBuilder();
            for (int i = 0; i < 12001; i++) {
                longWords.append('a');
            }
            List<GraphTurn> turns = new ArrayList<GraphTurn>();
            turns.add(new GraphTurn("card-a", "turn-a", longWords.toString()));
            turns.add(new GraphTurn("card-b", "turn-b", "bird"));
            final int[] asked = new int[1];
            final boolean[] release = new boolean[1];
            final Object gate = new Object();
            GraphDoor door = new GraphDoor() {
                @Override
                public String ask(String brief) {
                    int n;
                    synchronized (gate) {
                        asked[0]++;
                        n = asked[0];
                        if (n == 1) {
                            while (!release[0]) {
                                try {
                                    gate.wait();
                                } catch (InterruptedException ex) {
                                    // the test releases this ask
                                }
                            }
                        }
                    }
                    if (n == 1) {
                        return passLine("turn-a", "shelter", "seed");
                    }
                    return passLine("turn-b", "porch", "bird");
                }
            };
            GraphDesk.reload("stop-user", turns, door);
            long end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end && asked[0] < 1) {
                Thread.sleep(20L);
            }
            assertEquals(1, asked[0]);
            GraphDesk.halt("stop-user");
            synchronized (gate) {
                release[0] = true;
                gate.notifyAll();
            }
            end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end) {
                if (!GraphDesk.passing("stop-user") && pane.model().hasIntent("shelter")) {
                    break;
                }
                Thread.sleep(20L);
            }
            assertFalse(GraphDesk.passing("stop-user"));
            assertEquals(1, asked[0]);
            assertTrue(pane.model().hasIntent("shelter"));
            assertFalse(pane.model().hasIntent("porch"));
            assertFalse(pane.model().hasIntent("house"));
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    // the spinner stop posts onto the event thread
                }
            });
            assertFalse(pane.renderingOn());
            GraphModel loaded = new GraphModel("talk");
            store.load(loaded, "canvas-s");
            assertTrue(loaded.hasIntent("shelter"));
            assertFalse(loaded.hasIntent("house"));
            assertFalse(loaded.hasIntent("porch"));
            final int[] ran = new int[1];
            GraphDesk.model("stop-user", new Runnable() {
                @Override
                public void run() {
                    ran[0]++;
                }
            });
            end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end && ran[0] < 1) {
                Thread.sleep(20L);
            }
            assertEquals(1, ran[0]);
            final int[] again = new int[1];
            GraphDoor second = new GraphDoor() {
                @Override
                public String ask(String brief) {
                    synchronized (gate) {
                        again[0]++;
                    }
                    if (brief.indexOf("\nturn: turn-b\n") >= 0) {
                        return passLine("turn-b", "porch", "bird");
                    }
                    return passLine("turn-a", "shelter", "seed");
                }
            };
            GraphDesk.reload("stop-user", turns, second);
            end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end) {
                if (!GraphDesk.passing("stop-user") && pane.model().hasIntent("porch")) {
                    break;
                }
                Thread.sleep(20L);
            }
            assertFalse(GraphDesk.passing("stop-user"));
            assertEquals(2, again[0]);
            assertTrue(pane.model().hasIntent("shelter"));
            assertTrue(pane.model().hasIntent("porch"));
        } finally {
            connection.close();
        }
    }

    @Test
    void aClearZerosTheGraphAndTheNextTurnIsFresh() throws Exception {
        Connection connection = open("zero-cycle");
        try {
            GraphStore store = new GraphStore(connection);
            GraphModel seed = new GraphModel("talk");
            seed.addIntent("house");
            store.save(seed, "canvas-z");
            GraphDesk.seat("zero-user", connection, "canvas-z");
            GraphPane pane = GraphDesk.pane("zero-user", "talk");
            assertTrue(pane.model().hasIntent("house"));
            final List<String> briefs = new ArrayList<String>();
            GraphDoor door = new GraphDoor() {
                @Override
                public String ask(String brief) {
                    briefs.add(brief);
                    if (brief.indexOf("\nturn: turn-2\n") >= 0) {
                        return passLine("turn-2", "porch", "lane");
                    }
                    return passLine("turn-1", "shelter", "bird");
                }
            };
            List<GraphTurn> quiet = new ArrayList<GraphTurn>();
            quiet.add(new GraphTurn("card-1", "turn-1", "bird"));
            GraphDesk.grow("zero-user", quiet, door);
            final int[] ran = new int[1];
            GraphDesk.model("zero-user", new Runnable() {
                @Override
                public void run() {
                    ran[0]++;
                }
            });
            long end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end && ran[0] < 1) {
                Thread.sleep(20L);
            }
            assertEquals(1, ran[0]);
            assertEquals(0, briefs.size());
            assertTrue(pane.model().hasIntent("house"));
            GraphDesk.zero("zero-user", "canvas-z");
            end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end) {
                GraphModel loaded = new GraphModel("talk");
                store.load(loaded, "canvas-z");
                if (pane.model().intentCount() == 0 && loaded.intentCount() == 0) {
                    break;
                }
                Thread.sleep(20L);
            }
            assertEquals(0, pane.model().intentCount());
            GraphModel cleared = new GraphModel("talk");
            store.load(cleared, "canvas-z");
            assertEquals(0, cleared.intentCount());
            List<GraphTurn> first = new ArrayList<GraphTurn>();
            first.add(new GraphTurn("card-1", "turn-1", "bird"));
            GraphDesk.grow("zero-user", first, door);
            end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end) {
                if (!GraphDesk.passing("zero-user") && pane.model().hasIntent("shelter")) {
                    break;
                }
                Thread.sleep(20L);
            }
            assertFalse(GraphDesk.passing("zero-user"));
            assertTrue(pane.model().hasIntent("shelter"));
            assertFalse(pane.model().hasIntent("house"));
            assertTrue(briefs.get(0).contains("opening: fresh\n"));
            List<GraphTurn> second = new ArrayList<GraphTurn>();
            second.add(new GraphTurn("card-1", "turn-1", "bird"));
            second.add(new GraphTurn("card-2", "turn-2", "lane"));
            GraphDesk.grow("zero-user", second, door);
            end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end) {
                if (!GraphDesk.passing("zero-user") && pane.model().hasIntent("porch")) {
                    break;
                }
                Thread.sleep(20L);
            }
            assertFalse(GraphDesk.passing("zero-user"));
            assertTrue(pane.model().hasIntent("shelter"));
            assertTrue(pane.model().hasIntent("porch"));
            assertTrue(briefs.get(briefs.size() - 1).contains("opening: turn\n"));
            assertTrue(briefs.get(briefs.size() - 1).contains("already-read:\n- none\n"));
        } finally {
            connection.close();
        }
    }

    @Test
    void anEmptyLoadAsksEachTurn() throws Exception {
        Connection connection = open("empty-load");
        try {
            GraphDesk.seat("empty-load", connection, "canvas-empty");
            final GraphPane pane = GraphDesk.pane("empty-load", "talk");
            assertEquals(0, pane.model().intentCount());
            List<GraphTurn> turns = new ArrayList<GraphTurn>();
            turns.add(new GraphTurn("card-1", "turn-1", "bird"));
            turns.add(new GraphTurn("card-2", "turn-2", "lane"));
            final List<String> briefs = new ArrayList<String>();
            final List<Integer> seen = new ArrayList<Integer>();
            GraphDoor door = new GraphDoor() {
                @Override
                public String ask(String brief) {
                    briefs.add(brief);
                    seen.add(Integer.valueOf(pane.model().intentCount()));
                    if (brief.indexOf("\nturn: turn-2\n") >= 0) {
                        return passLine("turn-2", "porch", "lane");
                    }
                    return passLine("turn-1", "shelter", "bird");
                }
            };
            GraphDesk.hydrate("empty-load", turns, door);
            long end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end) {
                if (!GraphDesk.passing("empty-load") && pane.model().hasIntent("porch")) {
                    break;
                }
                Thread.sleep(20L);
            }
            assertFalse(GraphDesk.passing("empty-load"));
            assertEquals(2, briefs.size());
            assertEquals(Integer.valueOf(0), seen.get(0));
            assertTrue(seen.get(1).intValue() >= 1);
            assertTrue(briefs.get(0).contains("opening: fresh\n"));
            assertTrue(briefs.get(1).contains("opening: turn\n"));
            assertTrue(briefs.get(1).contains("shelter"));
            assertTrue(pane.model().hasIntent("shelter"));
            assertTrue(pane.model().hasIntent("porch"));
            GraphModel loaded = new GraphModel("talk");
            new GraphStore(connection).load(loaded, "canvas-empty");
            assertTrue(loaded.hasIntent("shelter"));
            assertTrue(loaded.hasIntent("porch"));
        } finally {
            connection.close();
        }
    }

    @Test
    void aWalkLeavesTheMeasureOnThePicture() throws Exception {
        Connection connection = open("picture-walk");
        try {
            GraphDesk.seat("picture-walk", connection, "canvas-picture");
            final GraphPane pane = GraphDesk.pane("picture-walk", "talk");
            List<GraphTurn> turns = new ArrayList<GraphTurn>();
            turns.add(new GraphTurn("card-1", "turn-1", "bird"));
            turns.add(new GraphTurn("card-2", "turn-2", "lane"));
            GraphDoor door = new GraphDoor() {
                @Override
                public String ask(String brief) throws Exception {
                    if (brief.indexOf("\nturn: turn-2\n") >= 0) {
                        long end = System.currentTimeMillis() + 4000L;
                        int seen = pane.picture().measureWalks();
                        while (System.currentTimeMillis() < end
                                && pane.picture().measureWalks() == seen) {
                            Thread.sleep(10L);
                        }
                        final int before = pane.picture().measureWalks();
                        assertTrue(before > seen);
                        final int[] wide = new int[1];
                        SwingUtilities.invokeAndWait(new Runnable() {
                            @Override
                            public void run() {
                                wide[0] = pane.picture().getPreferredSize().width;
                            }
                        });
                        assertEquals(before, pane.picture().measureWalks());
                        assertTrue(wide[0] >= 640);
                        assertTrue(GraphDesk.passing("picture-walk"));
                        return passLine("turn-2", "porch", "lane");
                    }
                    return passLine("turn-1", "shelter", "bird");
                }
            };
            GraphDesk.hydrate("picture-walk", turns, door);
            long end = System.currentTimeMillis() + 8000L;
            while (System.currentTimeMillis() < end) {
                if (!GraphDesk.passing("picture-walk") && pane.model().hasIntent("porch")) {
                    break;
                }
                Thread.sleep(20L);
            }
            assertFalse(GraphDesk.passing("picture-walk"));
            assertTrue(pane.model().hasIntent("shelter"));
            assertTrue(pane.model().hasIntent("porch"));
        } finally {
            connection.close();
        }
    }

    private static String passLine(String label, String aim, String word) {
        return "intent-pass: 1\n"
                + "turn: " + label + "\n"
                + "act:\n- " + aim + " born inferred\n"
                + "shift:\n- " + aim + " added\n"
                + "meat:\n- " + word + " on " + aim + "\n"
                + "evidence:\n- " + aim + " sees " + word + "\n"
                + "cell:\n- " + label + " " + aim + "\n";
    }

    private static Connection open(String name) throws Exception {
        Connection connection = DriverManager.getConnection(
                "jdbc:h2:mem:" + name + ";DB_CLOSE_DELAY=-1", "sa", "");
        Statement statement = connection.createStatement();
        try {
            statement.execute(
                    "CREATE TABLE cell (id VARCHAR(36) PRIMARY KEY, group_id VARCHAR(36),"
                            + " execution_status VARCHAR(32))");
            statement.execute("CREATE TABLE schema_version (id INT PRIMARY KEY, version INT NOT NULL)");
            statement.execute("INSERT INTO schema_version (id, version) VALUES (1, 2)");
        } finally {
            statement.close();
        }
        new VaultStore(null, "vault").applySchema(connection);
        return connection;
    }

    private static int count(Connection connection, String sql) throws Exception {
        Statement statement = connection.createStatement();
        try {
            ResultSet rs = statement.executeQuery(sql);
            try {
                rs.next();
                return rs.getInt(1);
            } finally {
                rs.close();
            }
        } finally {
            statement.close();
        }
    }

    private static String joined() {
        return GraphSql.DDL_NODE
                + GraphSql.DDL_REFERENT
                + GraphSql.DDL_WORD
                + GraphSql.DDL_RELATION
                + GraphSql.DDL_RELATION_END
                + GraphSql.DDL_EVIDENCE
                + GraphSql.DDL_CELL_FEED
                + GraphSql.DDL_CELL_FEED_INDEX
                + GraphSql.DDL_ACT
                + GraphSql.DDL_SEAT;
    }

    @Test
    void seatsStayPerLayout() throws Exception {
        Connection connection = open("seats");
        try {
            GraphModel model = new GraphModel("talk");
            model.addIntent("house");
            model.addIntent("bird");
            model.referent("r-roof", "house", "roof");
            GraphStore store = new GraphStore(connection);
            store.save(model, "canvas-s");
            GraphDesk.seat("seat-user", connection, "canvas-s");
            GraphPane pane = GraphDesk.pane("seat-user", "talk");
            int houseX = pane.layoutX("house");
            int houseY = pane.layoutY("house");
            int roofX = pane.layoutX("roof");
            int roofY = pane.layoutY("roof");
            pane.nudge("house", 17, 19);
            pane.keepSeats();
            List<String[]> force = store.readSeats("canvas-s", "force");
            assertEquals(3, force.size());
            assertEquals(houseX + 17, seatAt(force, "house", 1));
            assertEquals(houseY + 19, seatAt(force, "house", 2));
            assertEquals(roofX + 17, seatAt(force, "roof", 1));
            assertEquals(roofY + 19, seatAt(force, "roof", 2));
            GraphDesk.seat("seat-two", connection, "canvas-s");
            GraphPane again = GraphDesk.pane("seat-two", "talk");
            assertEquals(houseX + 17, again.layoutX("house"));
            assertEquals(roofX + 17, again.layoutX("roof"));
            pane.showPage(11);
            int birdX = pane.layoutX("bird");
            int birdY = pane.layoutY("bird");
            pane.nudge("bird", 4, 6);
            pane.keepSeats();
            pane.showPage(12);
            assertEquals(houseX + 17, pane.layoutX("house"));
            GraphDesk.seat("seat-three", connection, "canvas-s");
            GraphPane third = GraphDesk.pane("seat-three", "talk");
            third.showPage(11);
            assertEquals("layered", third.layoutName());
            assertEquals(birdX + 4, third.layoutX("bird"));
            assertEquals(birdY + 6, third.layoutY("bird"));
            model.setMark("house", "inferred");
            store.save(model, "canvas-s");
            assertEquals(houseX + 17, seatAt(store.readSeats("canvas-s", "force"), "house", 1));
            assertEquals(birdX + 4, seatAt(store.readSeats("canvas-s", "layered"), "bird", 1));
            GraphModel other = new GraphModel("talk");
            other.addIntent("barn");
            store.save(other, "canvas-b");
            List<String[]> barn = new ArrayList<String[]>();
            barn.add(new String[] {"barn", "8", "9"});
            store.keep("canvas-b", "force", barn);
            store.deleteCanvas("canvas-s");
            assertEquals(0, count(connection, "SELECT COUNT(*) FROM graph_seat WHERE canvas_id='canvas-s'"));
            assertEquals(1, count(connection, "SELECT COUNT(*) FROM graph_seat WHERE canvas_id='canvas-b'"));
            assertEquals(8, seatAt(store.readSeats("canvas-b", "force"), "barn", 1));
        } finally {
            connection.close();
        }
    }

    private static int seatAt(List<String[]> rows, String name, int column) {
        for (int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row != null && name.equals(row[0])) {
                return Integer.parseInt(row[column]);
            }
        }
        return -1;
    }

    private static String source(String name) throws Exception {
        Path path = Path.of("src", "main", "java", "org", "aetheris", "lcarsgraph", name);
        return Files.readString(path, StandardCharsets.UTF_8);
    }
}
