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

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.swing.SwingUtilities;

/**
 * Each login keeps one graph.
 * A different login keeps a different pane and a different model.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1474 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
// @aa-sdd L40 aetheris-plan-f-308.001.md FINESSE:f-308
// @aa-sdd L40 aetheris-plan-f-310.001.md FINESSE:f-310
public final class GraphDesk {

    private static final Map<String, GraphPane> PANES = new LinkedHashMap<>();
    private static final Map<String, Seat> SEATS = new LinkedHashMap<>();
    private static final Map<String, Boolean> PASSING = new LinkedHashMap<>();
    private static final Map<String, Lanes> LANES = new LinkedHashMap<>();
    private static final ThreadLocal<String> LANE = new ThreadLocal<String>();

    private GraphDesk() {
    }

    /**
     * The method marks a vault seat as on the way.
     * The event thread calls this before the vault thread borrows.
     * A walk that arrives early waits for that seat.
     *
     * @param username The login that owns the graph.
     */
    public static void hold(String username) {
        if (username == null || username.isEmpty()) {
            return;
        }
        Lanes lanes = lanes(username);
        synchronized (lanes) {
            lanes.seatPending = true;
        }
    }

    /**
     * The method runs one job on the model thread.
     * The event thread returns.
     * A call already on the model thread runs the job in line.
     *
     * @param username The login that owns the graph.
     * @param job The job reads turns, parses, or applies. It may be null.
     */
    public static void model(String username, Runnable job) {
        if (job == null || username == null || username.isEmpty()) {
            return;
        }
        submit(lanes(username).model, "model", job);
    }

    /**
     * The method runs one job on the vault thread.
     * The event thread returns.
     * A call already on the vault thread runs the job in line.
     *
     * @param username The login that owns the graph.
     * @param job The job borrows, reads, or writes. It may be null.
     */
    public static void vault(String username, Runnable job) {
        if (job == null || username == null || username.isEmpty()) {
            return;
        }
        submit(lanes(username).vault, "vault", job);
    }

    /**
     * The method runs one settle on the picture thread.
     * A pane that is not seated still leaves the event thread.
     * A call already on the picture thread runs the job in line.
     *
     * @param pane The pane that owns the picture.
     * @param job The job places and then posts a paint.
     */
    static void place(GraphPane pane, Runnable job) {
        if (job == null) {
            return;
        }
        String username = usernameOf(pane);
        if (username == null) {
            Thread worker = new Thread(job, "graph-picture");
            worker.setDaemon(true);
            worker.start();
            return;
        }
        submit(lanes(username).picture, "picture", job);
    }

    /**
     * The method seats the open connection and the focused canvas for one login.
     * A load runs when that login already has a pane and the canvas changed.
     * The event thread enqueues the load and returns.
     * A null connection releases the hold and does not borrow.
     * The shelf stays keyed by username.
     *
     * @param username The login that owns the graph.
     * @param connection The connection the vault already opened.
     * @param canvasId The canvas that owns the rows.
     */
    public static void seat(String username, Connection connection, String canvasId) {
        if (connection == null) {
            if (username == null || username.isEmpty()) {
                return;
            }
            Lanes lanes = lanes(username);
            synchronized (lanes) {
                lanes.seatPending = false;
            }
            submit(lanes.model, "model", new Runnable() {
                @Override
                public void run() {
                    flushWalk(username);
                }
            });
            return;
        }
        if (username == null || username.isEmpty() || canvasId == null || canvasId.isEmpty()) {
            closeQuiet(connection);
            return;
        }
        Seat prior = SEATS.get(username);
        Connection held = connection;
        boolean changed = true;
        if (prior != null) {
            changed = !canvasId.equals(prior.canvasId) || prior.connection == null;
            if (prior.connection != null && prior.connection != connection && open(prior.connection)) {
                closeQuiet(connection);
                held = prior.connection;
            } else if (prior.connection != null && prior.connection != connection) {
                closeQuiet(prior.connection);
            }
        }
        SEATS.put(username, new Seat(held, canvasId));
        Lanes lanes = lanes(username);
        synchronized (lanes) {
            lanes.seatPending = false;
        }
        GraphPane pane = PANES.get(username);
        if (!changed || pane == null || pane.model() == null) {
            submit(lanes.model, "model", new Runnable() {
                @Override
                public void run() {
                    flushWalk(username);
                }
            });
            return;
        }
        if (!EventQueue.isDispatchThread() && !"vault".equals(LANE.get())) {
            try {
                new GraphStore(held).load(pane.model(), canvasId);
            } catch (SQLException ex) {
                // the pane keeps the shelf until a later load
            }
            pane.recallCanvas();
            pane.repaint();
            return;
        }
        final Connection reading = held;
        final int epoch;
        synchronized (lanes) {
            lanes.loading = true;
            lanes.epoch = lanes.epoch + 1;
            epoch = lanes.epoch;
        }
        submit(lanes.vault, "vault", new Runnable() {
            @Override
            public void run() {
                readCanvas(username, reading, canvasId, epoch);
            }
        });
    }

    static void written(final GraphModel model) {
        if (model == null) {
            return;
        }
        final String username = usernameOf(model);
        if (username == null) {
            return;
        }
        if (EventQueue.isDispatchThread() || "picture".equals(LANE.get())) {
            submit(lanes(username).model, "model", new Runnable() {
                @Override
                public void run() {
                    written(model);
                }
            });
            return;
        }
        if ("model".equals(LANE.get())) {
            handSave(username, GraphStore.snap(model));
            return;
        }
        Seat seat = SEATS.get(username);
        if (seat == null || seat.connection == null || seat.canvasId == null) {
            return;
        }
        try {
            new GraphStore(seat.connection).save(model, seat.canvasId);
        } catch (SQLException ex) {
            // save reloads the last committed rows
        } catch (RuntimeException ex) {
            // a length fault leaves the prior rows
        }
    }

    /**
     * The method writes every painted id on the active layout.
     *
     * @param pane The pane that holds the seats.
     */
    static void keep(final GraphPane pane) {
        final Seat seat = seatOf(pane);
        if (seat == null) {
            return;
        }
        List<String> ids = pane.paintedIds();
        final List<String[]> rows = new ArrayList<String[]>();
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            rows.add(new String[] {
                id,
                Integer.toString(pane.layoutX(id)),
                Integer.toString(pane.layoutY(id))
            });
        }
        final String layout = pane.layoutName();
        pane.holdSeats(layout, rows);
        if (EventQueue.isDispatchThread() || "picture".equals(LANE.get())) {
            final String username = usernameOf(pane);
            submit(lanes(username).vault, "vault", new Runnable() {
                @Override
                public void run() {
                    writeSeats(seat, layout, rows);
                }
            });
            return;
        }
        writeSeats(seat, layout, rows);
    }

    /**
     * The method reads the stored seats of the active layout.
     *
     * @param pane The pane that owns the canvas.
     * @return Each row is the painted id, the x, and the y.
     */
    static List<String[]> seats(GraphPane pane) {
        List<String[]> empty = new ArrayList<String[]>();
        if (pane != null && "picture".equals(LANE.get())) {
            return pane.heldSeats(pane.layoutName());
        }
        Seat seat = seatOf(pane);
        if (seat == null) {
            return empty;
        }
        try {
            List<String[]> rows = new GraphStore(seat.connection).readSeats(seat.canvasId, pane.layoutName());
            pane.holdSeats(pane.layoutName(), rows);
            return rows;
        } catch (SQLException ex) {
            return empty;
        }
    }

    /**
     * The method deletes the stored seats of one layout.
     *
     * @param pane The pane that owns the canvas.
     * @param layout The layout name is layered, force, or radial.
     */
    static void drop(final GraphPane pane, final String layout) {
        final Seat seat = seatOf(pane);
        if (seat == null || layout == null) {
            return;
        }
        pane.holdSeats(layout, new ArrayList<String[]>());
        if (EventQueue.isDispatchThread() || "picture".equals(LANE.get())) {
            final String username = usernameOf(pane);
            submit(lanes(username).vault, "vault", new Runnable() {
                @Override
                public void run() {
                    dropSeats(seat, layout);
                }
            });
            return;
        }
        dropSeats(seat, layout);
    }

    private static Seat seatOf(GraphPane pane) {
        if (pane == null) {
            return null;
        }
        String username = null;
        for (Map.Entry<String, GraphPane> entry : PANES.entrySet()) {
            if (entry.getValue() == pane) {
                username = entry.getKey();
                break;
            }
        }
        if (username == null) {
            return null;
        }
        Seat seat = SEATS.get(username);
        if (seat == null || seat.connection == null || seat.canvasId == null) {
            return null;
        }
        return seat;
    }

    /**
     * The method returns the pane for one username.
     * The same username returns the same pane.
     * A null or empty username returns nothing and stores nothing.
     *
     * @param username The login that owns the graph.
     * @param conversation The vault name for that login.
     * @return The pane, or null when the username is missing.
     */
    public static GraphPane pane(String username, Object conversation) {
        if (username == null || username.isEmpty()) {
            return null;
        }
        GraphPane existing = PANES.get(username);
        if (existing != null) {
            return existing;
        }
        GraphModel model = new GraphModel(conversation);
        GraphPane created = new GraphPane(model);
        PANES.put(username, created);
        Seat seat = SEATS.get(username);
        if (seat != null && seat.connection != null && seat.canvasId != null) {
            if (EventQueue.isDispatchThread()) {
                Lanes lanes = lanes(username);
                final int epoch;
                synchronized (lanes) {
                    lanes.loading = true;
                    lanes.epoch = lanes.epoch + 1;
                    epoch = lanes.epoch;
                }
                final String canvasId = seat.canvasId;
                final Connection reading = seat.connection;
                submit(lanes.vault, "vault", new Runnable() {
                    @Override
                    public void run() {
                        readCanvas(username, reading, canvasId, epoch);
                    }
                });
            } else {
                try {
                    new GraphStore(seat.connection).load(model, seat.canvasId);
                } catch (SQLException ex) {
                    // the pane is still the login's pane
                }
                created.recallCanvas();
            }
        }
        return created;
    }

    /**
     * The method fills an empty graph from the conversation.
     * A model that already holds an aim returns.
     * A model whose every intent is the map phrase is cleared and asked.
     * A pass already in flight returns.
     * The ask runs on the model thread. The first turn is fresh. Each later turn is its own ask.
     * Each passing turn is saved on the vault thread. The picture thread measures that turn.
     * The event thread paints the measured size. The picture paints before the next ask.
     *
     * @param username The login that owns the graph.
     * @param turns The turns are the conversation, oldest first.
     * @param door The door sends one brief.
     */
    public static void hydrate(final String username, final List<GraphTurn> turns, final GraphDoor door) {
        if (username == null || username.isEmpty() || turns == null || turns.isEmpty()) {
            return;
        }
        if (!"model".equals(LANE.get())) {
            final Lanes lanes = lanes(username);
            final int token;
            synchronized (lanes) {
                token = lanes.haltToken;
            }
            submit(lanes.model, "model", new Runnable() {
                @Override
                public void run() {
                    int now;
                    synchronized (lanes) {
                        now = lanes.haltToken;
                    }
                    if (now != token) {
                        return;
                    }
                    hydrate(username, turns, door);
                }
            });
            return;
        }
        Lanes lanes = lanes(username);
        int token;
        synchronized (lanes) {
            token = lanes.haltToken;
        }
        pass(username, turns, door, false, token, false);
    }

    /**
     * The method clears this login's graph and infers the conversation again.
     * A held aim does not stop the walk.
     * Pins, rejects, and feed rows for that canvas go with the clear.
     * A pass already in flight returns.
     * An empty conversation clears the graph and does not ask.
     * The ask runs on the model thread. The first turn is fresh. Each later turn is its own ask.
     * Each passing turn is saved on the vault thread. The picture thread measures that turn.
     * The event thread paints the measured size. The picture paints before the next ask.
     *
     * @param username The login that owns the graph.
     * @param turns The turns are the conversation, oldest first.
     * @param door The door sends one brief.
     */
    public static void reload(final String username, final List<GraphTurn> turns, final GraphDoor door) {
        if (!"model".equals(LANE.get())) {
            if (username == null || username.isEmpty()) {
                return;
            }
            final Lanes lanes = lanes(username);
            final int token;
            synchronized (lanes) {
                token = lanes.haltToken;
            }
            submit(lanes.model, "model", new Runnable() {
                @Override
                public void run() {
                    int now;
                    synchronized (lanes) {
                        now = lanes.haltToken;
                    }
                    if (now != token) {
                        return;
                    }
                    reload(username, turns, door);
                }
            });
            return;
        }
        if (username == null || username.isEmpty()) {
            return;
        }
        Lanes lanes = lanes(username);
        int token;
        synchronized (lanes) {
            token = lanes.haltToken;
        }
        pass(username, turns, door, true, token, false);
    }

    /**
     * The method zeros one canvas graph and arms the per-turn cycle.
     * The model for that canvas is vacated. The vault rows for that canvas go.
     * A walk already in flight finishes its current ask and does not paint over the zero.
     * The next sealed turn on that canvas is opening fresh. Each later sealed turn is opening turn.
     * A null fallback is closed only when the seat has no connection of its own.
     *
     * @param username The login that owns the graph.
     * @param canvasId The canvas whose graph goes.
     * @param fallback A connection to use when that login has no seated connection. It may be null.
     */
    public static void zero(final String username, final String canvasId, final Connection fallback) {
        if (username == null || username.isEmpty() || canvasId == null || canvasId.isEmpty()) {
            closeQuiet(fallback);
            return;
        }
        final Lanes lanes = lanes(username);
        final int token;
        final int epoch;
        synchronized (lanes) {
            lanes.haltToken = lanes.haltToken + 1;
            lanes.armedTurns = null;
            lanes.armedDoor = null;
            lanes.armedForce = false;
            lanes.armedGrow = false;
            lanes.born = true;
            lanes.bornCanvas = canvasId;
            lanes.epoch = lanes.epoch + 1;
            token = lanes.haltToken;
            epoch = lanes.epoch;
        }
        submit(lanes.model, "model", new Runnable() {
            @Override
            public void run() {
                int now;
                synchronized (lanes) {
                    now = lanes.haltToken;
                }
                if (now != token) {
                    closeQuiet(fallback);
                    return;
                }
                Seat seat = SEATS.get(username);
                GraphPane pane = PANES.get(username);
                boolean showing = seat != null && canvasId.equals(seat.canvasId);
                if ((showing || seat == null) && pane != null && pane.model() != null) {
                    pane.model().vacant();
                    pane.rendering(false);
                }
                Connection mine = seat == null ? null : seat.connection;
                final Connection used = mine != null ? mine : fallback;
                final boolean closeAfter = used != null && used != mine;
                if (fallback != null && fallback != used) {
                    closeQuiet(fallback);
                }
                if (used != null) {
                    submit(lanes.vault, "vault", new Runnable() {
                        @Override
                        public void run() {
                            try {
                                new GraphStore(used).deleteCanvas(canvasId);
                            } catch (SQLException ex) {
                                // the memory graph is already empty
                            } finally {
                                if (closeAfter) {
                                    closeQuiet(used);
                                }
                            }
                        }
                    });
                }
                if (showing && pane != null) {
                    submit(lanes.picture, "picture", new Runnable() {
                        @Override
                        public void run() {
                            paintPicture(username, canvasId, epoch, new GraphModel("picture"));
                        }
                    });
                }
            }
        });
    }

    /**
     * The method zeros one canvas graph and arms the per-turn cycle.
     * The seated connection deletes the rows. A login with no seat only vacates a showing model.
     *
     * @param username The login that owns the graph.
     * @param canvasId The canvas whose graph goes.
     */
    public static void zero(String username, String canvasId) {
        zero(username, canvasId, null);
    }

    /**
     * The method folds the newest sealed turns into a graph that Clear Turns armed.
     * A login that was not zeroed returns. A different canvas returns.
     * An empty model walks opening fresh, then opening turn for each later turn.
     * A model that already holds an aim walks opening turn for the newest turn only.
     * The ask runs on the model thread. The import walk is not this method.
     *
     * @param username The login that owns the graph.
     * @param turns The turns are the conversation, oldest first.
     * @param door The door sends one brief.
     */
    public static void grow(final String username, final List<GraphTurn> turns, final GraphDoor door) {
        if (username == null || username.isEmpty() || turns == null || turns.isEmpty() || door == null) {
            return;
        }
        Lanes held;
        synchronized (LANES) {
            held = LANES.get(username);
        }
        if (held == null) {
            return;
        }
        final List<GraphTurn> copy = new ArrayList<GraphTurn>();
        for (int i = 0; i < turns.size(); i++) {
            GraphTurn turn = turns.get(i);
            if (turn != null) {
                copy.add(turn);
            }
        }
        if (copy.isEmpty()) {
            return;
        }
        if (!"model".equals(LANE.get())) {
            final Lanes lanes = held;
            final int token;
            synchronized (lanes) {
                if (!lanes.born) {
                    return;
                }
                token = lanes.haltToken;
            }
            submit(lanes.model, "model", new Runnable() {
                @Override
                public void run() {
                    int now;
                    synchronized (lanes) {
                        now = lanes.haltToken;
                    }
                    if (now != token) {
                        return;
                    }
                    grow(username, copy, door);
                }
            });
            return;
        }
        final Lanes lanes = held;
        final int token;
        String bornCanvas;
        synchronized (lanes) {
            if (!lanes.born) {
                return;
            }
            token = lanes.haltToken;
            bornCanvas = lanes.bornCanvas;
        }
        Seat seat = SEATS.get(username);
        if (seat == null || seat.canvasId == null || !seat.canvasId.equals(bornCanvas)) {
            return;
        }
        synchronized (PASSING) {
            if (PASSING.containsKey(username)) {
                synchronized (lanes) {
                    if (lanes.haltToken != token) {
                        return;
                    }
                    lanes.armedTurns = copy;
                    lanes.armedDoor = door;
                    lanes.armedForce = false;
                    lanes.armedGrow = true;
                    lanes.armedToken = token;
                }
                return;
            }
        }
        pass(username, copy, door, false, token, true);
    }

    /**
     * The method stops the live graph load for one login.
     * The ask already in flight finishes. A later ask does not start.
     * The rendering spinner stops when that walk returns.
     * The three seats stay up. A later reload still asks.
     *
     * @param username The login that owns the graph.
     */
    public static void halt(String username) {
        if (username == null || username.isEmpty()) {
            return;
        }
        Lanes lanes = lanes(username);
        synchronized (lanes) {
            lanes.haltToken = lanes.haltToken + 1;
            lanes.armedTurns = null;
            lanes.armedDoor = null;
            lanes.armedForce = false;
            lanes.armedGrow = false;
            if (lanes.walking) {
                lanes.epoch = lanes.epoch + 1;
            }
        }
    }

    /**
     * The method stops the live graph load for the login that owns the pane.
     * A pane that is not seated returns.
     *
     * @param pane The pane is that login's graph.
     */
    static void stop(GraphPane pane) {
        halt(usernameOf(pane));
    }

    /**
     * The method reports whether a walk for this login is in flight.
     *
     * @param username The login that owns the graph.
     * @return True while that walk has the seat.
     */
    static boolean passing(String username) {
        synchronized (PASSING) {
            return username != null && PASSING.containsKey(username);
        }
    }

    /**
     * The method reports whether a walk for this pane is in flight.
     * The event thread paints the size the picture thread already measured.
     *
     * @param pane The pane is that login's graph.
     * @return True while that walk has the seat.
     */
    static boolean passing(GraphPane pane) {
        return passing(usernameOf(pane));
    }

    private static void pass(final String username, final List<GraphTurn> turns, final GraphDoor door,
            final boolean force, final int token, final boolean bornWalk) {
        if (username == null || username.isEmpty() || turns == null || door == null) {
            return;
        }
        final Lanes lanes = lanes(username);
        boolean wait;
        synchronized (lanes) {
            if (lanes.haltToken != token) {
                return;
            }
            wait = lanes.seatPending || lanes.loading;
            if (wait) {
                lanes.armedTurns = turns;
                lanes.armedDoor = door;
                lanes.armedForce = force;
                lanes.armedGrow = bornWalk;
                lanes.armedToken = token;
            }
        }
        if (wait) {
            return;
        }
        final GraphPane pane = PANES.get(username);
        if (pane == null || pane.model() == null) {
            return;
        }
        final boolean phrase = GraphDslDriver.phraseGraph(pane.model());
        if (!bornWalk && !force && pane.model().intentCount() > 0 && !phrase) {
            return;
        }
        if (bornWalk) {
            String bornCanvas;
            synchronized (lanes) {
                bornCanvas = lanes.bornCanvas;
            }
            Seat bornSeat = SEATS.get(username);
            if (bornSeat == null || bornSeat.canvasId == null || !bornSeat.canvasId.equals(bornCanvas)) {
                return;
            }
        }
        final Seat seat = SEATS.get(username);
        if (seat == null || seat.connection == null || seat.canvasId == null) {
            return;
        }
        synchronized (PASSING) {
            if (PASSING.containsKey(username)) {
                return;
            }
            PASSING.put(username, Boolean.TRUE);
        }
        final String canvas = seat.canvasId;
        final boolean clear = !bornWalk && (force || phrase);
        final int epoch;
        boolean committed = false;
        synchronized (lanes) {
            if (lanes.haltToken == token) {
                lanes.walking = true;
                committed = true;
                if (clear) {
                    lanes.epoch = lanes.epoch + 1;
                }
            }
            epoch = lanes.epoch;
        }
        if (!committed) {
            synchronized (PASSING) {
                PASSING.remove(username);
            }
            return;
        }
        pane.rendering(true);
        try {
            if (clear) {
                pane.model().vacant();
                submit(lanes.vault, "vault", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new GraphStore(seat.connection).deleteCanvas(canvas);
                        } catch (SQLException ex) {
                            // the walk still fills the model
                        }
                    }
                });
                submit(lanes.picture, "picture", new Runnable() {
                    @Override
                    public void run() {
                        paintPicture(username, canvas, epoch, new GraphModel("picture"));
                    }
                });
            }
            if (turns.isEmpty()) {
                paintStep(username, canvas, pane, null, epoch);
                return;
            }
            GraphDoor gate = new GraphDoor() {
                @Override
                public String ask(String brief) throws Exception {
                    synchronized (lanes) {
                        if (lanes.haltToken != token) {
                            throw new IllegalStateException("stop");
                        }
                    }
                    return door.ask(brief);
                }
            };
            GraphDslDriver.Land step = new GraphDslDriver.Land() {
                @Override
                public void land(final List<String[]> feeds) {
                    paintStep(username, canvas, pane, feeds, epoch);
                }
            };
            // @aa-sdd L40 aetheris-plan-f-325.001.md FINESSE:f-325
            GraphDslDriver.grow(pane.model(), turns, gate, step);
        } catch (Exception ex) {
            // a door that does not send leaves the graph as it stands
        } finally {
            int settle = -1;
            synchronized (lanes) {
                lanes.walking = false;
                if (lanes.haltToken != token) {
                    settle = lanes.epoch;
                }
            }
            if (settle >= 0) {
                paintStep(username, canvas, pane, null, settle);
            }
            pane.rendering(false);
            submit(lanes.vault, "vault", new Runnable() {
                @Override
                public void run() {
                    synchronized (PASSING) {
                        PASSING.remove(username);
                    }
                    submit(lanes.model, "model", new Runnable() {
                        @Override
                        public void run() {
                            flushWalk(username);
                        }
                    });
                }
            });
        }
    }

    private static void paintStep(final String username, final String canvas, final GraphPane pane,
            final List<String[]> feeds, final int epoch) {
        Lanes lanes = LANES.get(username);
        if (lanes == null) {
            return;
        }
        synchronized (lanes) {
            if (epoch != lanes.epoch) {
                return;
            }
        }
        Seat now = SEATS.get(username);
        if (now == null || !canvas.equals(now.canvasId)) {
            return;
        }
        final GraphStore.Shot shot = GraphStore.snap(pane.model());
        final GraphModel frozen = new GraphModel("picture");
        GraphStore.pour(frozen, shot);
        final List<String[]> copy = new ArrayList<String[]>();
        if (feeds != null) {
            for (int i = 0; i < feeds.size(); i++) {
                String[] row = feeds.get(i);
                if (row == null || row.length < 2) {
                    continue;
                }
                copy.add(new String[] {row[0], row[1]});
            }
        }
        final Connection connection = now.connection;
        submit(lanes.vault, "vault", new Runnable() {
            @Override
            public void run() {
                Seat current = SEATS.get(username);
                if (current == null || !canvas.equals(current.canvasId)) {
                    return;
                }
                synchronized (lanes) {
                    if (epoch != lanes.epoch) {
                        return;
                    }
                }
                try {
                    new GraphStore(connection).saveShot(shot, canvas);
                    writeFeeds(current, copy);
                } catch (SQLException ex) {
                    // the step stays in memory when the desk cannot write it
                } catch (RuntimeException ex) {
                    // a length fault leaves the prior rows
                }
            }
        });
        submit(lanes.picture, "picture", new Runnable() {
            @Override
            public void run() {
                paintPicture(username, canvas, epoch, frozen);
            }
        });
    }

    private static void paintPicture(final String username, final String canvas, final int epoch,
            final GraphModel frozen) {
        Lanes lanes = LANES.get(username);
        final GraphPane pane = PANES.get(username);
        Seat seat = SEATS.get(username);
        if (lanes == null || pane == null || seat == null || !canvas.equals(seat.canvasId)) {
            return;
        }
        synchronized (lanes) {
            if (epoch != lanes.epoch) {
                return;
            }
        }
        pane.landPicture(frozen);
        // @aa-sdd L40 aetheris-plan-f-326.001.md FINESSE:f-326
        pane.picture().measure(frozen);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Lanes current = LANES.get(username);
                Seat now = SEATS.get(username);
                if (current == null || now == null || !canvas.equals(now.canvasId)) {
                    return;
                }
                synchronized (current) {
                    if (epoch != current.epoch) {
                        return;
                    }
                }
                pane.repaint();
            }
        });
    }

    private static void writeFeeds(Seat seat, List<String[]> feeds) {
        if (seat == null || feeds == null) {
            return;
        }
        GraphStore store = new GraphStore(seat.connection);
        for (int i = 0; i < feeds.size(); i++) {
            String[] row = feeds.get(i);
            if (row == null || row.length < 2) {
                continue;
            }
            try {
                if (!store.feedHeld(row[0], row[1])) {
                    store.insertFeed(row[0], row[1], seat.canvasId);
                }
            } catch (SQLException ex) {
                // the node save already landed
            }
        }
    }

    private static void readCanvas(final String username, Connection connection, final String canvasId,
            final int epoch) {
        final Lanes lanes = lanes(username);
        GraphStore.Shot shot = null;
        List<String[]> layered = new ArrayList<String[]>();
        List<String[]> force = new ArrayList<String[]>();
        List<String[]> radial = new ArrayList<String[]>();
        boolean failed = false;
        try {
            GraphStore store = new GraphStore(connection);
            shot = store.capture(canvasId);
            layered = store.readSeats(canvasId, "layered");
            force = store.readSeats(canvasId, "force");
            radial = store.readSeats(canvasId, "radial");
        } catch (SQLException ex) {
            failed = true;
        }
        final GraphStore.Shot taken = shot;
        final List<String[]> lay = layered;
        final List<String[]> foc = force;
        final List<String[]> rad = radial;
        final boolean miss = failed;
        submit(lanes.model, "model", new Runnable() {
            @Override
            public void run() {
                synchronized (lanes) {
                    lanes.loading = false;
                }
                boolean current;
                synchronized (lanes) {
                    current = epoch == lanes.epoch;
                }
                Seat now = SEATS.get(username);
                GraphPane pane = PANES.get(username);
                if (!miss && taken != null && current && pane != null && pane.model() != null
                        && now != null && canvasId.equals(now.canvasId)) {
                    GraphStore.pour(pane.model(), taken);
                    pane.holdSeats("layered", lay);
                    pane.holdSeats("force", foc);
                    pane.holdSeats("radial", rad);
                    final GraphModel frozen = new GraphModel("picture");
                    GraphStore.pour(frozen, taken);
                    submit(lanes.picture, "picture", new Runnable() {
                        @Override
                        public void run() {
                            paintPicture(username, canvasId, epoch, frozen);
                        }
                    });
                }
                flushWalk(username);
            }
        });
    }

    private static void flushWalk(String username) {
        Lanes lanes = LANES.get(username);
        if (lanes == null) {
            return;
        }
        List<GraphTurn> turns;
        GraphDoor door;
        boolean force;
        boolean growWalk = false;
        int token;
        synchronized (lanes) {
            if (lanes.seatPending || lanes.loading || lanes.armedDoor == null) {
                return;
            }
            if (lanes.armedToken != lanes.haltToken) {
                lanes.armedTurns = null;
                lanes.armedDoor = null;
                lanes.armedForce = false;
                lanes.armedGrow = false;
                return;
            }
            turns = lanes.armedTurns;
            door = lanes.armedDoor;
            force = lanes.armedForce;
            token = lanes.armedToken;
            growWalk = lanes.armedGrow;
            lanes.armedTurns = null;
            lanes.armedDoor = null;
            lanes.armedForce = false;
            lanes.armedGrow = false;
        }
        if (growWalk) {
            grow(username, turns, door);
        } else {
            pass(username, turns, door, force, token, false);
        }
    }

    private static void handSave(final String username, final GraphStore.Shot shot) {
        if (shot == null) {
            return;
        }
        Seat seat = SEATS.get(username);
        if (seat == null || seat.connection == null || seat.canvasId == null) {
            return;
        }
        final Lanes lanes = lanes(username);
        final Connection connection = seat.connection;
        final String canvas = seat.canvasId;
        final int epoch;
        synchronized (lanes) {
            epoch = lanes.epoch;
        }
        submit(lanes.vault, "vault", new Runnable() {
            @Override
            public void run() {
                Seat now = SEATS.get(username);
                if (now == null || !canvas.equals(now.canvasId)) {
                    return;
                }
                synchronized (lanes) {
                    if (epoch != lanes.epoch) {
                        return;
                    }
                }
                try {
                    new GraphStore(connection).saveShot(shot, canvas);
                } catch (SQLException ex) {
                    // the step stays in memory when the desk cannot write it
                } catch (RuntimeException ex) {
                    // a length fault leaves the prior rows
                }
            }
        });
    }

    private static void writeSeats(Seat seat, String layout, List<String[]> rows) {
        try {
            new GraphStore(seat.connection).keep(seat.canvasId, layout, rows);
        } catch (SQLException ex) {
            // the picture keeps the seats in memory
        } catch (RuntimeException ex) {
            // a length fault leaves the prior rows
        }
    }

    private static void dropSeats(Seat seat, String layout) {
        try {
            new GraphStore(seat.connection).dropLayout(seat.canvasId, layout);
        } catch (SQLException ex) {
            // the next place still runs
        }
    }

    private static void submit(ExecutorService exec, final String lane, final Runnable job) {
        if (job == null || exec == null) {
            return;
        }
        if (lane.equals(LANE.get())) {
            job.run();
            return;
        }
        exec.execute(new Runnable() {
            @Override
            public void run() {
                LANE.set(lane);
                try {
                    job.run();
                } finally {
                    LANE.remove();
                }
            }
        });
    }

    private static Lanes lanes(String username) {
        synchronized (LANES) {
            Lanes lanes = LANES.get(username);
            if (lanes == null) {
                lanes = new Lanes(username);
                LANES.put(username, lanes);
            }
            return lanes;
        }
    }

    private static String usernameOf(GraphPane pane) {
        if (pane == null) {
            return null;
        }
        for (Map.Entry<String, GraphPane> entry : PANES.entrySet()) {
            if (entry.getValue() == pane) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static String usernameOf(GraphModel model) {
        if (model == null) {
            return null;
        }
        for (Map.Entry<String, GraphPane> entry : PANES.entrySet()) {
            GraphPane pane = entry.getValue();
            if (pane != null && pane.model() == model) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static boolean open(Connection connection) {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException ex) {
            return false;
        }
    }

    private static void closeQuiet(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            // the pool takes the connection back on a later borrow
        }
    }

    private static final class Seat {
        private final Connection connection;
        private final String canvasId;

        private Seat(Connection connection, String canvasId) {
            this.connection = connection;
            this.canvasId = canvasId;
        }
    }

    /**
     * One login keeps three serial seats.
     * The model seat parses and asks. The vault seat reads and writes. The picture seat places.
     * None of them is the event thread.
     */
    private static final class Lanes {
        private final ExecutorService model;
        private final ExecutorService vault;
        private final ExecutorService picture;
        private int epoch;
        private int haltToken;
        private boolean walking;
        private boolean loading;
        private boolean seatPending;
        private List<GraphTurn> armedTurns;
        private GraphDoor armedDoor;
        private boolean armedForce;
        private boolean armedGrow;
        private int armedToken;
        private boolean born;
        private String bornCanvas;

        private Lanes(String username) {
            this.model = lane("graph-model-" + username);
            this.vault = lane("graph-vault-" + username);
            this.picture = lane("graph-picture-" + username);
        }

        private static ExecutorService lane(final String name) {
            return Executors.newSingleThreadExecutor(new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    Thread thread = new Thread(runnable, name);
                    thread.setDaemon(true);
                    return thread;
                }
            });
        }
    }
}
