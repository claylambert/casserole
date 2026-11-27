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

package org.casserole.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.aetheris.lcarsgraph.GraphDoor;
import org.aetheris.lcarsgraph.GraphDslDriver;
import org.aetheris.lcarsgraph.GraphModel;
import org.aetheris.lcarsgraph.GraphTurn;
import org.casserole.app.ui.ssh.GraphGrokDoor;

/**
 * The class asks the graph driver through the Grok door.
 * It is a forensics probe. It is not part of the test suite.
 * The argument {@code simple} sends two short turns. The argument {@code clay} reads that login's canvas and does not write it.
 * The key is not printed. The window text is not printed.
 */
public class GraphDslProbe {

    /**
     * The method runs one probe.
     * A missing argument uses {@code simple}.
     *
     * @param args The args are the optional mode.
     * @throws Exception The method throws this exception when the door cannot send.
     */
    public static void main(String[] args) throws Exception {
        String mode = args.length > 0 ? args[0] : "simple";
        if ("clay".equals(mode)) {
            clay();
            return;
        }
        simple();
    }

    private static void simple() throws Exception {
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1",
                "The porch light is out. The license file still needs a line."));
        turns.add(new GraphTurn("card-2", "turn-2",
                "GitHub shows the casserole build is green. Movie night is Friday."));
        run("simple", turns);
    }

    private static void clay() throws Exception {
        String home = System.getProperty("user.home");
        String path = home + "/.aetheris/clay/vault/vault";
        String url = "jdbc:h2:file:" + path + ";IFEXISTS=TRUE;ACCESS_MODE_DATA=r;FILE_LOCK=NO";
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(url, "sa", "");
        try {
            connection.setReadOnly(true);
            String canvas = canvasId(connection);
            if (canvas == null) {
                System.out.println("clay canvas 0");
                return;
            }
            List<GraphTurn> turns = turns(connection, canvas);
            System.out.println("clay turns " + turns.size());
            run("clay", turns);
        } finally {
            connection.close();
        }
    }

    private static void run(String mode, List<GraphTurn> turns) throws Exception {
        GraphModel model = new GraphModel(mode);
        Count door = new Count(new GraphGrokDoor("grok-4.7"));
        long started = System.currentTimeMillis();
        int applied = 0;
        try {
            applied = GraphDslDriver.importEmpty(model, turns, door, null);
        } catch (Exception ex) {
            System.out.println(mode + " door " + ex.getClass().getSimpleName());
            return;
        }
        long elapsed = System.currentTimeMillis() - started;
        List<String> ids = model.intentIds();
        System.out.println(mode + " applied " + applied + " intents " + ids.size()
                + " asks " + door.asks + " repairs " + door.repairs + " ms " + elapsed);
        for (int i = 0; i < door.faults.size(); i++) {
            System.out.println("fault " + door.faults.get(i));
        }
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            System.out.println("intent " + id + " meats " + model.meatWords(id).size());
        }
    }

    private static String canvasId(Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        try {
            ResultSet rows = statement.executeQuery(
                    "SELECT canvas_id, COUNT(*) AS n FROM cell GROUP BY canvas_id ORDER BY n DESC");
            try {
                if (rows.next()) {
                    return rows.getString(1);
                }
                return null;
            } finally {
                rows.close();
            }
        } finally {
            statement.close();
        }
    }

    private static List<GraphTurn> turns(Connection connection, String canvas) throws Exception {
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        Statement statement = connection.createStatement();
        try {
            ResultSet rows = statement.executeQuery(
                    "SELECT id, is_ephemeral, user_clob, assistant_clob FROM cell WHERE canvas_id='"
                            + canvas.replace("'", "") + "' ORDER BY turn_index");
            try {
                while (rows.next()) {
                    if (rows.getBoolean(2)) {
                        continue;
                    }
                    String words = flat(rows.getString(3), rows.getString(4));
                    if (words.length() == 0) {
                        continue;
                    }
                    String card = rows.getString(1);
                    turns.add(new GraphTurn(card == null ? "" : card, "turn-" + (turns.size() + 1), words));
                }
            } finally {
                rows.close();
            }
        } finally {
            statement.close();
        }
        return turns;
    }

    private static String flat(String user, String assistant) {
        StringBuilder sb = new StringBuilder();
        append(sb, user);
        append(sb, assistant);
        return sb.toString().trim();
    }

    private static void append(StringBuilder sb, String text) {
        if (text == null) {
            return;
        }
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n' || c == '\r' || c == '\t') {
                c = ' ';
            }
            if (c == ' ' && (sb.length() == 0 || sb.charAt(sb.length() - 1) == ' ')) {
                continue;
            }
            sb.append(c);
        }
    }

    private static final class Count implements GraphDoor {
        private final GraphDoor door;
        private int asks;
        private int repairs;
        private final List<String> faults = new ArrayList<String>();

        private Count(GraphDoor door) {
            this.door = door;
        }

        @Override
        public String ask(String brief) throws Exception {
            asks++;
            if (brief != null && brief.startsWith("intent-repair:")) {
                repairs++;
                String fault = "repair";
                int at = brief.indexOf("fault: ");
                if (at >= 0) {
                    int end = brief.indexOf('\n', at);
                    fault = end < 0 ? brief.substring(at + 7) : brief.substring(at + 7, end);
                }
                faults.add(fault.trim());
            }
            return door.ask(brief);
        }
    }
}
