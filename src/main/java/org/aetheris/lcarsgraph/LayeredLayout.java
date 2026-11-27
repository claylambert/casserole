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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * LAYERED writes ranks.
 * A cycle's feedback edge is for the picture only.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1473 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public final class LayeredLayout {

    private static final String[] RELATIONS = {
        "supports", "refines", "contains", "pulls against"
    };

    private final Object gate = new Object();
    private final Map<String, Integer> xs = new LinkedHashMap<>();
    private final Map<String, Integer> ys = new LinkedHashMap<>();

    public void place(GraphModel model) {
        place(model, 20);
    }

    /**
     * The method writes ranks at this point size.
     * Each rung packs the intent clusters.
     * Each meat word sits in a halo under its intent.
     *
     * @param model The model supplies the ids.
     * @param points The points are the seated font size.
     */
    public void place(GraphModel model, int points) {
        Map<String, Integer> nextX = new LinkedHashMap<String, Integer>();
        Map<String, Integer> nextY = new LinkedHashMap<String, Integer>();
        if (model == null) {
            swap(nextX, nextY);
            return;
        }
        List<String> ids = model.intentIds();
        Map<String, List<String>> forward = new LinkedHashMap<>();
        for (String id : ids) {
            forward.put(id, new ArrayList<>());
        }
        List<String> stack = new ArrayList<>();
        List<String> seen = new ArrayList<>();
        for (String id : ids) {
            if (!seen.contains(id)) {
                collect(model, id, stack, seen, forward);
            }
        }
        Map<String, Integer> rank = new LinkedHashMap<>();
        for (String id : ids) {
            rank.put(id, 0);
        }
        List<String> order = topo(ids, forward);
        for (String id : order) {
            int base = rank.get(id);
            for (String next : forward.get(id)) {
                int candidate = base + 1;
                if (candidate > rank.get(next)) {
                    rank.put(next, candidate);
                }
            }
        }
        int max = 0;
        for (String id : ids) {
            if (rank.get(id) > max) {
                max = rank.get(id);
            }
        }
        int size = points < 1 ? 1 : points;
        int shelfTop = 0;
        for (int rung = 0; rung <= max; rung++) {
            int edge = 0;
            int floor = shelfTop;
            int align = shelfTop;
            boolean open = false;
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                if (rank.get(id) != rung) {
                    continue;
                }
                int[] box = GraphSpan.box(id, true, GraphSpan.gather(model, id), size);
                List<String> words = model.meatWords(id);
                int[][] wordBoxes = GraphSpan.wordBoxes(words, size);
                int[][] halo = GraphSpan.wordSeats(box, wordBoxes);
                int[] bounds = GraphSpan.bounds(box, wordBoxes, halo);
                int cx;
                int cy;
                if (!open) {
                    if (rung == 0) {
                        cx = 0;
                        cy = 0;
                        align = bounds[2];
                        edge = bounds[1] + GraphSpan.PAD;
                        floor = bounds[3];
                    } else {
                        cx = -bounds[0];
                        cy = shelfTop - bounds[2];
                        align = shelfTop;
                        edge = cx + bounds[1] + GraphSpan.PAD;
                        floor = cy + bounds[3];
                    }
                    open = true;
                } else {
                    cx = edge - bounds[0];
                    cy = align - bounds[2];
                    edge = cx + bounds[1] + GraphSpan.PAD;
                    int bot = cy + bounds[3];
                    if (bot > floor) {
                        floor = bot;
                    }
                }
                nextX.put(id, cx);
                nextY.put(id, cy);
                for (int w = 0; w < words.size(); w++) {
                    nextX.put(words.get(w), cx + halo[w][0]);
                    nextY.put(words.get(w), cy + halo[w][1]);
                }
            }
            shelfTop = floor + GraphSpan.PAD;
        }
        swap(nextX, nextY);
    }

    public int x(String id) {
        synchronized (gate) {
            Integer value = xs.get(id);
            return value == null ? 0 : value;
        }
    }

    public int y(String id) {
        synchronized (gate) {
            Integer value = ys.get(id);
            return value == null ? 0 : value;
        }
    }

    public boolean placed(String id) {
        synchronized (gate) {
            return xs.containsKey(id);
        }
    }

    /**
     * The method seats one id.
     *
     * @param id The id is an intent or a meat word.
     * @param px The px is the horizontal seat.
     * @param py The py is the vertical seat.
     */
    public void seat(String id, int px, int py) {
        if (id == null) {
            return;
        }
        synchronized (gate) {
            put(id, px, py);
        }
    }

    /**
     * The method clears the seats this layout holds.
     */
    public void clearSeats() {
        synchronized (gate) {
            xs.clear();
            ys.clear();
        }
    }

    /**
     * The method returns the ids this layout holds.
     *
     * @return The return value is those ids.
     */
    public List<String> seatedIds() {
        synchronized (gate) {
            return new ArrayList<String>(xs.keySet());
        }
    }

    /**
     * The method moves one seated id.
     * A missing id is seated at the delta.
     *
     * @param id The id is an intent or a meat word.
     * @param dx The dx is the horizontal move.
     * @param dy The dy is the vertical move.
     */
    public void nudge(String id, int dx, int dy) {
        if (id == null) {
            return;
        }
        synchronized (gate) {
            xs.put(id, x(id) + dx);
            ys.put(id, y(id) + dy);
        }
    }

    private void swap(Map<String, Integer> nextX, Map<String, Integer> nextY) {
        synchronized (gate) {
            xs.clear();
            ys.clear();
            xs.putAll(nextX);
            ys.putAll(nextY);
        }
    }

    private void collect(
            GraphModel model,
            String id,
            List<String> stack,
            List<String> seen,
            Map<String, List<String>> forward) {
        seen.add(id);
        stack.add(id);
        for (String next : outgoing(model, id)) {
            if (stack.contains(next)) {
                continue;
            }
            List<String> links = forward.get(id);
            if (!links.contains(next)) {
                links.add(next);
            }
            if (!seen.contains(next)) {
                collect(model, next, stack, seen, forward);
            }
        }
        stack.remove(stack.size() - 1);
    }

    private static List<String> topo(List<String> ids, Map<String, List<String>> forward) {
        Map<String, Integer> indegree = new LinkedHashMap<>();
        for (String id : ids) {
            indegree.put(id, 0);
        }
        for (String id : ids) {
            for (String next : forward.get(id)) {
                indegree.put(next, indegree.get(next) + 1);
            }
        }
        List<String> ready = new ArrayList<>();
        for (String id : ids) {
            if (indegree.get(id) == 0) {
                ready.add(id);
            }
        }
        List<String> order = new ArrayList<>();
        int cursor = 0;
        while (cursor < ready.size()) {
            String id = ready.get(cursor);
            cursor++;
            order.add(id);
            for (String next : forward.get(id)) {
                int left = indegree.get(next) - 1;
                indegree.put(next, left);
                if (left == 0) {
                    ready.add(next);
                }
            }
        }
        for (String id : ids) {
            if (!order.contains(id)) {
                order.add(id);
            }
        }
        return order;
    }

    private static List<String> outgoing(GraphModel model, String from) {
        List<String> found = new ArrayList<>();
        for (String relation : RELATIONS) {
            for (String target : model.targets(from, relation)) {
                if (!found.contains(target)) {
                    found.add(target);
                }
            }
        }
        return found;
    }

    private void put(String id, int px, int py) {
        xs.put(id, px);
        ys.put(id, py);
    }
}
