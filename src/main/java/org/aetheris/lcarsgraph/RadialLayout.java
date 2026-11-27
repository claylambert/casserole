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
 * RADIAL writes rings around the selected intent.
 * No selection keeps the positions already stored.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1473 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
// @aa-sdd L40 aetheris-plan-f-316.001.md FINESSE:f-316
public final class RadialLayout {

    private static final String[] RELATIONS = {
        "supports", "refines", "contains", "pulls against"
    };

    private final Object gate = new Object();
    private final Map<String, Integer> xs = new LinkedHashMap<>();
    private final Map<String, Integer> ys = new LinkedHashMap<>();

    public void place(GraphModel model, String selectedIntent) {
        place(model, selectedIntent, 20);
    }

    /**
     * The method writes rings at this point size.
     * A missing selection keeps the seats already stored.
     * The selected intent sits at the origin.
     * Its meat words sit on a halo around that origin.
     * The first ring is the neighborhood.
     * The next ring is the meat words of that neighborhood.
     * Each later hop takes the next ring.
     *
     * @param model The model supplies the ids.
     * @param selectedIntent The selected intent sits at the origin.
     * @param points The points are the seated font size.
     */
    public void place(GraphModel model, String selectedIntent, int points) {
        if (model == null || selectedIntent == null || !model.hasIntent(selectedIntent)) {
            return;
        }
        int size = points < 1 ? 1 : points;
        Map<String, Integer> nextX = new LinkedHashMap<String, Integer>();
        Map<String, Integer> nextY = new LinkedHashMap<String, Integer>();
        List<int[]> occupied = new ArrayList<int[]>();
        int[] hub = GraphSpan.box(selectedIntent, true, GraphSpan.gather(model, selectedIntent), size);
        nextX.put(selectedIntent, 0);
        nextY.put(selectedIntent, 0);
        occupy(occupied, 0, 0, hub);
        seatWords(model, selectedIntent, 0, 0, -Math.PI / 2.0, true, size, nextX, nextY, occupied);
        List<String> seen = new ArrayList<String>();
        List<String> frontier = new ArrayList<String>();
        seen.add(selectedIntent);
        frontier.add(selectedIntent);
        List<String> ids = model.intentIds();
        while (seen.size() < ids.size()) {
            List<String> ring = new ArrayList<String>();
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                if (seen.contains(id)) {
                    continue;
                }
                if (touches(model, id, frontier)) {
                    ring.add(id);
                }
            }
            if (ring.isEmpty()) {
                for (int i = 0; i < ids.size(); i++) {
                    if (!seen.contains(ids.get(i))) {
                        ring.add(ids.get(i));
                        break;
                    }
                }
            }
            seatRing(model, ring, size, nextX, nextY, occupied);
            frontier = ring;
            for (int i = 0; i < ring.size(); i++) {
                seen.add(ring.get(i));
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
            xs.put(id, x(id) + dx);
            ys.put(id, y(id) + dy);
        }
    }

    private void seatRing(
            GraphModel model,
            List<String> ring,
            int size,
            Map<String, Integer> nextX,
            Map<String, Integer> nextY,
            List<int[]> occupied) {
        int count = ring.size();
        int[][] boxes = new int[count][];
        int reach = 1;
        for (int i = 0; i < count; i++) {
            boxes[i] = GraphSpan.box(ring.get(i), true, GraphSpan.gather(model, ring.get(i)), size);
            reach = farther(reach, boxes[i]);
        }
        int radius = outer(occupied) + reach + GraphSpan.PAD;
        if (count > 1) {
            double sin = Math.sin(Math.PI / count);
            if (sin > 0.0) {
                int chord = (int) Math.ceil((reach + (GraphSpan.PAD / 2.0)) / sin);
                if (chord > radius) {
                    radius = chord;
                }
            }
        }
        for (int guard = 0; guard < 48; guard++) {
            if (ringClears(boxes, radius, occupied)) {
                break;
            }
            radius += GraphSpan.PAD;
        }
        int[] cx = new int[count];
        int[] cy = new int[count];
        for (int i = 0; i < count; i++) {
            double angle = ringAngle(i, count);
            cx[i] = (int) Math.round(Math.cos(angle) * radius);
            cy[i] = (int) Math.round(Math.sin(angle) * radius);
            nextX.put(ring.get(i), cx[i]);
            nextY.put(ring.get(i), cy[i]);
            occupy(occupied, cx[i], cy[i], boxes[i]);
        }
        for (int i = 0; i < count; i++) {
            seatWords(model, ring.get(i), cx[i], cy[i], ringAngle(i, count), false, size, nextX, nextY, occupied);
        }
    }

    private void seatWords(
            GraphModel model,
            String id,
            int cx,
            int cy,
            double facing,
            boolean around,
            int size,
            Map<String, Integer> nextX,
            Map<String, Integer> nextY,
            List<int[]> occupied) {
        List<String> words = model.meatWords(id);
        int count = words.size();
        double span = around ? (2.0 * Math.PI) : (Math.PI / 3.0);
        for (int i = 0; i < count; i++) {
            double angle = around ? ringAngle(i, count) : fanAngle(facing, span, i, count);
            int[] box = GraphSpan.box(words.get(i), false, 0, size);
            int distance = clearOf(cx, cy, angle, box, occupied);
            int wx = cx + (int) Math.round(Math.cos(angle) * distance);
            int wy = cy + (int) Math.round(Math.sin(angle) * distance);
            nextX.put(words.get(i), wx);
            nextY.put(words.get(i), wy);
            occupy(occupied, wx, wy, box);
        }
    }

    private static double ringAngle(int index, int count) {
        if (count <= 1) {
            return -Math.PI / 2.0;
        }
        return (-Math.PI / 2.0) + ((2.0 * Math.PI * index) / count);
    }

    private static double fanAngle(double facing, double span, int index, int count) {
        if (count <= 1) {
            return facing;
        }
        return facing - (span / 2.0) + ((span * index) / (count - 1));
    }

    private static boolean ringClears(int[][] boxes, int radius, List<int[]> occupied) {
        int count = boxes.length;
        List<int[]> trial = new ArrayList<int[]>();
        for (int i = 0; i < count; i++) {
            double angle = ringAngle(i, count);
            int cx = (int) Math.round(Math.cos(angle) * radius);
            int cy = (int) Math.round(Math.sin(angle) * radius);
            if (hits(cx, cy, boxes[i], occupied) || hits(cx, cy, boxes[i], trial)) {
                return false;
            }
            trial.add(edges(cx, cy, boxes[i]));
        }
        return true;
    }

    private static int clearOf(int ox, int oy, double angle, int[] box, List<int[]> occupied) {
        int distance = 0;
        while (distance < 8000) {
            int x = ox + (int) Math.round(Math.cos(angle) * distance);
            int y = oy + (int) Math.round(Math.sin(angle) * distance);
            if (!hits(x, y, box, occupied)) {
                return distance;
            }
            distance++;
        }
        return distance;
    }

    private static boolean hits(int cx, int cy, int[] box, List<int[]> occupied) {
        int left = cx - box[0];
        int right = cx + box[1];
        int top = cy - box[2];
        int bottom = cy + box[3];
        for (int i = 0; i < occupied.size(); i++) {
            int[] other = occupied.get(i);
            if (left < other[1] + GraphSpan.PAD && other[0] < right + GraphSpan.PAD
                    && top < other[3] + GraphSpan.PAD && other[2] < bottom + GraphSpan.PAD) {
                return true;
            }
        }
        return false;
    }

    private static void occupy(List<int[]> occupied, int cx, int cy, int[] box) {
        occupied.add(edges(cx, cy, box));
    }

    private static int[] edges(int cx, int cy, int[] box) {
        return new int[] {cx - box[0], cx + box[1], cy - box[2], cy + box[3]};
    }

    private static int farther(int reach, int[] box) {
        int next = reach;
        for (int i = 0; i < box.length; i++) {
            if (box[i] > next) {
                next = box[i];
            }
        }
        return next;
    }

    private static int outer(List<int[]> occupied) {
        int far = 0;
        for (int i = 0; i < occupied.size(); i++) {
            int[] edge = occupied.get(i);
            int left = Math.abs(edge[0]);
            int right = Math.abs(edge[1]);
            int top = Math.abs(edge[2]);
            int bottom = Math.abs(edge[3]);
            if (left > far) {
                far = left;
            }
            if (right > far) {
                far = right;
            }
            if (top > far) {
                far = top;
            }
            if (bottom > far) {
                far = bottom;
            }
        }
        return far;
    }

    private static boolean touches(GraphModel model, String id, List<String> frontier) {
        for (int i = 0; i < frontier.size(); i++) {
            if (shares(model, id, frontier.get(i))) {
                return true;
            }
        }
        return false;
    }

    private void swap(Map<String, Integer> nextX, Map<String, Integer> nextY) {
        synchronized (gate) {
            xs.clear();
            ys.clear();
            xs.putAll(nextX);
            ys.putAll(nextY);
        }
    }

    private static boolean shares(GraphModel model, String left, String right) {
        if (outgoing(model, left).contains(right)) {
            return true;
        }
        return outgoing(model, right).contains(left);
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
