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
 * FORCE writes a neighborhood around a place.
 * A pinned intent still moves.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1473 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public final class ForceLayout {

    private final Object gate = new Object();
    private final Map<String, Integer> xs = new LinkedHashMap<>();
    private final Map<String, Integer> ys = new LinkedHashMap<>();

    public void place(GraphModel model, int atX, int atY) {
        place(model, atX, atY, 20);
    }

    /**
     * The method writes a fresh force picture at this point size.
     * The first intent keeps the place.
     * Each meat word sits in a halo under its intent.
     * Each later intent takes the next clear compass ray.
     *
     * @param model The model supplies the ids.
     * @param atX The atX is the place.
     * @param atY The atY is the place.
     * @param points The points are the seated font size.
     */
    public void place(GraphModel model, int atX, int atY, int points) {
        Map<String, Integer> nextX = new LinkedHashMap<String, Integer>();
        Map<String, Integer> nextY = new LinkedHashMap<String, Integer>();
        if (model != null) {
            int size = points < 1 ? 1 : points;
            List<String> ids = model.intentIds();
            List<int[]> placed = new ArrayList<int[]>();
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                int[] box = GraphSpan.box(id, true, GraphSpan.gather(model, id), size);
                List<String> words = model.meatWords(id);
                int[][] wordBoxes = GraphSpan.wordBoxes(words, size);
                int[][] halo = GraphSpan.wordSeats(box, wordBoxes);
                int[] bounds = GraphSpan.bounds(box, wordBoxes, halo);
                int cx = atX;
                int cy = atY;
                if (i > 0) {
                    int[] spot = GraphSpan.ray(atX, atY, (i - 1) % 4, bounds, placed);
                    cx = spot[0];
                    cy = spot[1];
                }
                nextX.put(id, cx);
                nextY.put(id, cy);
                for (int w = 0; w < words.size(); w++) {
                    nextX.put(words.get(w), cx + halo[w][0]);
                    nextY.put(words.get(w), cy + halo[w][1]);
                }
                placed.add(new int[] {cx, cy, bounds[0], bounds[1], bounds[2], bounds[3]});
            }
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
            put(id, x(id) + dx, y(id) + dy);
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

    private void put(String id, int px, int py) {
        xs.put(id, px);
        ys.put(id, py);
    }
}
