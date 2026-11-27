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
import java.util.List;

/**
 * The span is the gap a fresh layout leaves between nodes.
 * The box covers the glyph and the letters at the seated point size.
 *
 * @author clayl (2026-11-24)
 */
final class GraphSpan {

    private static final String[] RELATIONS = {
        "supports", "refines", "contains", "pulls against"
    };

    private GraphSpan() {
    }

    static final int PAD = 16;

    /**
     * The method returns the widest box in this model.
     * A fresh layout does not use this width as its gap.
     * A point size below 1 is read as 1.
     *
     * @param model The model supplies the ids.
     * @param points The points are the seated font size.
     * @return The return value is that width.
     */
    static int step(GraphModel model, int points) {
        int size = points < 1 ? 1 : points;
        int maxLeft = 1;
        int maxRight = 1;
        int maxUp = 1;
        int maxDown = 1;
        if (model == null) {
            return maxLeft + maxRight;
        }
        List<String> ids = model.intentIds();
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            int[] box = box(id, true, gather(model, id), size);
            if (box[0] > maxLeft) {
                maxLeft = box[0];
            }
            if (box[1] > maxRight) {
                maxRight = box[1];
            }
            if (box[2] > maxUp) {
                maxUp = box[2];
            }
            if (box[3] > maxDown) {
                maxDown = box[3];
            }
            List<String> words = model.meatWords(id);
            for (int w = 0; w < words.size(); w++) {
                int[] word = box(words.get(w), false, 0, size);
                if (word[0] > maxLeft) {
                    maxLeft = word[0];
                }
                if (word[1] > maxRight) {
                    maxRight = word[1];
                }
                if (word[2] > maxUp) {
                    maxUp = word[2];
                }
                if (word[3] > maxDown) {
                    maxDown = word[3];
                }
            }
        }
        int wide = maxLeft + maxRight;
        int high = maxUp + maxDown;
        if (high > wide) {
            return high;
        }
        return wide;
    }

    /**
     * The method returns the extents around one center.
     * The order is left, right, up, down.
     *
     * @param id The id is the painted name.
     * @param intent The intent flag selects the intent glyph.
     * @param reached The reached count widens an intent.
     * @param points The points are the seated font size.
     * @return The return value is those four extents.
     */
    static int[] box(String id, boolean intent, int reached, int points) {
        int size = points < 1 ? 1 : points;
        int letters = 0;
        if (id != null) {
            letters = size * id.length();
        }
        int glyph = intent ? 48 + 8 * reached : 28;
        int radius = glyph / 2;
        return new int[] {
            radius,
            radius + 8 + letters,
            radius + 8 + size,
            radius
        };
    }

    /**
     * The method counts the intents reached from one start.
     *
     * @param model The model holds the relations.
     * @param start The start is one intent id.
     * @return The return value is that count.
     */
    static int gather(GraphModel model, String start) {
        if (model == null || start == null) {
            return 0;
        }
        List<String> seen = new ArrayList<String>();
        List<String> queue = new ArrayList<String>();
        seen.add(start);
        queue.add(start);
        int count = 0;
        while (!queue.isEmpty()) {
            String id = queue.remove(0);
            for (int r = 0; r < RELATIONS.length; r++) {
                List<String> targets = model.targets(id, RELATIONS[r]);
                for (int t = 0; t < targets.size(); t++) {
                    String next = targets.get(t);
                    if (seen.contains(next)) {
                        continue;
                    }
                    seen.add(next);
                    queue.add(next);
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * The method returns the spiral cell for one index.
     * Index 0 is the origin.
     *
     * @param index The index is the cell number.
     * @return The return value is the column and the row.
     */
    static int[] spiral(int index) {
        int x = 0;
        int y = 0;
        int dx = 1;
        int dy = 0;
        int leg = 1;
        int walked = 0;
        int turns = 0;
        int cursor = 0;
        if (index < 0) {
            index = 0;
        }
        while (cursor < index) {
            x += dx;
            y += dy;
            walked++;
            cursor++;
            if (walked == leg) {
                walked = 0;
                int turn = dx;
                dx = -dy;
                dy = turn;
                turns++;
                if (turns == 2) {
                    turns = 0;
                    leg++;
                }
            }
        }
        return new int[] {x, y};
    }

    /**
     * The method seats meat words in one row under the intent.
     * The row is centered on the intent.
     * The gap is the facing extents plus the pad.
     *
     * @param intentBox The intent box is left, right, up, down.
     * @param wordBoxes Each word box uses that same order.
     * @return Each row is the horizontal and vertical shift.
     */
    static int[][] wordSeats(int[] intentBox, int[][] wordBoxes) {
        int count = wordBoxes == null ? 0 : wordBoxes.length;
        int[][] seats = new int[count][2];
        if (count == 0 || intentBox == null) {
            return seats;
        }
        int total = 0;
        int drop = 0;
        for (int i = 0; i < count; i++) {
            total += wordBoxes[i][0] + wordBoxes[i][1];
            if (i > 0) {
                total += PAD;
            }
            int gap = intentBox[3] + wordBoxes[i][2] + PAD;
            if (gap > drop) {
                drop = gap;
            }
        }
        int cursor = -(total / 2);
        for (int i = 0; i < count; i++) {
            int dx = cursor + wordBoxes[i][0];
            seats[i][0] = dx;
            seats[i][1] = drop;
            cursor = dx + wordBoxes[i][1] + PAD;
        }
        return seats;
    }

    /**
     * The method returns the boxes of the meat words.
     *
     * @param words The words are the meat ids.
     * @param points The points are the seated font size.
     * @return Each row is left, right, up, down.
     */
    static int[][] wordBoxes(List<String> words, int points) {
        int count = words == null ? 0 : words.size();
        int[][] boxes = new int[count][];
        for (int i = 0; i < count; i++) {
            boxes[i] = box(words.get(i), false, 0, points);
        }
        return boxes;
    }

    /**
     * The method returns the cluster around one intent center.
     * The order is minimum x, maximum x, minimum y, maximum y.
     *
     * @param intentBox The intent box is left, right, up, down.
     * @param wordBoxes The word boxes use that same order.
     * @param seats The seats are the word shifts.
     * @return The return value is that cluster.
     */
    static int[] bounds(int[] intentBox, int[][] wordBoxes, int[][] seats) {
        int minX = -intentBox[0];
        int maxX = intentBox[1];
        int minY = -intentBox[2];
        int maxY = intentBox[3];
        if (wordBoxes == null) {
            return new int[] {minX, maxX, minY, maxY};
        }
        for (int i = 0; i < wordBoxes.length; i++) {
            int left = seats[i][0] - wordBoxes[i][0];
            int right = seats[i][0] + wordBoxes[i][1];
            int up = seats[i][1] - wordBoxes[i][2];
            int down = seats[i][1] + wordBoxes[i][3];
            if (left < minX) {
                minX = left;
            }
            if (right > maxX) {
                maxX = right;
            }
            if (up < minY) {
                minY = up;
            }
            if (down > maxY) {
                maxY = down;
            }
        }
        return new int[] {minX, maxX, minY, maxY};
    }

    /**
     * The method walks one compass ray until the cluster clears.
     * East, south, west, and north are directions 0 through 3.
     *
     * @param ox The ox is the anchor.
     * @param oy The oy is the anchor.
     * @param dir The dir selects the ray.
     * @param bounds The bounds are the cluster being seated.
     * @param placed Each placed row is the center and its bounds.
     * @return The return value is the clear center.
     */
    static int[] ray(int ox, int oy, int dir, int[] bounds, List<int[]> placed) {
        int sx = 0;
        int sy = 0;
        if (dir == 0) {
            sx = 1;
        } else if (dir == 1) {
            sy = 1;
        } else if (dir == 2) {
            sx = -1;
        } else {
            sy = -1;
        }
        int cx = ox;
        int cy = oy;
        int guard = 0;
        while (guard < 64) {
            guard++;
            int push = 0;
            for (int i = 0; i < placed.size(); i++) {
                int pen = penetration(cx, cy, bounds, placed.get(i), sx, sy);
                if (pen > push) {
                    push = pen;
                }
            }
            if (push == 0) {
                return new int[] {cx, cy};
            }
            cx += sx * push;
            cy += sy * push;
        }
        return new int[] {cx, cy};
    }

    private static int penetration(int cx, int cy, int[] bounds, int[] other, int sx, int sy) {
        int thisLeft = cx + bounds[0];
        int thisRight = cx + bounds[1];
        int thisTop = cy + bounds[2];
        int thisBot = cy + bounds[3];
        int otherLeft = other[0] + other[2];
        int otherRight = other[0] + other[3];
        int otherTop = other[1] + other[4];
        int otherBot = other[1] + other[5];
        boolean across = thisLeft < otherRight + PAD && otherLeft < thisRight + PAD;
        boolean down = thisTop < otherBot + PAD && otherTop < thisBot + PAD;
        if (!across || !down) {
            return 0;
        }
        if (sx > 0) {
            return (otherRight + PAD) - thisLeft;
        }
        if (sx < 0) {
            return thisRight - (otherLeft - PAD);
        }
        if (sy > 0) {
            return (otherBot + PAD) - thisTop;
        }
        return thisBot - (otherTop - PAD);
    }
}
