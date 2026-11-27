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

import java.awt.EventQueue;
import java.lang.reflect.Method;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * The layout proof runs in memory.
 *
 * @author clayl (2026-11-24)
 */
class GraphLayoutTest {

    @Test
    void l1SameFixtureYieldsThreePositionSets() {
        GraphModel model = fixture();
        LayeredLayout layered = new LayeredLayout();
        ForceLayout force = new ForceLayout();
        RadialLayout radial = new RadialLayout();
        layered.place(model);
        force.place(model, 10, 20);
        radial.place(model, "bird");

        String layeredPicture = picture(layered, "house", "bird", "lane", "home");
        String forcePicture = picture(force, "house", "bird", "lane", "home");
        String radialPicture = picture(radial, "house", "bird", "lane", "home");
        assertEquals("house 0,0 bird 28,172 lane 176,-4 home -44,86", layeredPicture);
        assertEquals("house 10,20 bird 190,20 lane 10,188 home -34,106", forcePicture);
        assertEquals("house 0,-268 bird 0,0 lane 0,-524 home 0,-354", radialPicture);
        assertFalse(layeredPicture.equals(forcePicture));
        assertFalse(layeredPicture.equals(radialPicture));
        assertFalse(forcePicture.equals(radialPicture));
        assertMeaning(model);
    }

    @Test
    void l2SettleRunsOffTheSwingThread() {
        GraphModel model = fixture();
        assertFalse(EventQueue.isDispatchThread());
        LayeredLayout layered = new LayeredLayout();
        layered.place(model);
        assertFalse(EventQueue.isDispatchThread());
        ForceLayout force = new ForceLayout();
        force.place(model, 10, 20);
        assertFalse(EventQueue.isDispatchThread());
        RadialLayout radial = new RadialLayout();
        radial.place(model, "bird");
        assertFalse(EventQueue.isDispatchThread());
        assertTrue(layered.placed("house"));
        assertTrue(force.placed("house"));
        assertTrue(radial.placed("bird"));
    }

    @Test
    void l3PinnedIntentReceivesANewPosition() {
        GraphModel model = fixture();
        assertTrue(model.pinned("house"));
        assertTrue(model.hasIntent("house"));
        assertEquals("house", model.tether("home"));
        ForceLayout force = new ForceLayout();
        assertFalse(force.placed("house"));
        force.place(model, 10, 20);
        assertTrue(force.placed("house"));
        assertEquals(10, force.x("house"));
        assertEquals(20, force.y("house"));
        assertTrue(model.hasIntent("house"));
        assertTrue(model.pinned("house"));
        assertEquals("house", model.tether("home"));
        assertEquals(-34, force.x("home"));
        assertEquals(106, force.y("home"));
    }

    @Test
    void l4ForceKeepsThePlace() {
        GraphModel model = fixture();
        ForceLayout force = new ForceLayout();
        force.place(model, 10, 20);
        assertEquals(10, force.x("house"));
        assertEquals(20, force.y("house"));
        assertEquals(190, force.x("bird"));
        assertEquals(20, force.y("bird"));
        assertEquals(10, force.x("house"));
        assertEquals(20, force.y("house"));
        for (Method method : ForceLayout.class.getDeclaredMethods()) {
            String name = method.getName().toLowerCase(Locale.ROOT);
            assertFalse(name.contains("icon"));
            assertFalse(name.contains("bar"));
        }
    }

    @Test
    void l5RadialKeepsShownPositions() {
        GraphModel model = fixture();
        RadialLayout radial = new RadialLayout();
        radial.place(model, null);
        assertFalse(radial.placed("bird"));
        radial.place(model, "missing");
        assertFalse(radial.placed("bird"));
        radial.place(model, "bird");
        assertEquals(0, radial.x("bird"));
        assertEquals(0, radial.y("bird"));
        assertEquals(0, radial.x("house"));
        assertEquals(-268, radial.y("house"));
        radial.place(model, null);
        assertEquals(0, radial.x("bird"));
        assertEquals(0, radial.y("bird"));
        assertEquals(0, radial.x("house"));
        assertEquals(-268, radial.y("house"));
        assertTrue(radial.placed("lane"));
    }

    @Test
    void layoutsDoNotOverlap() {
        GraphModel model = crowd();
        int[] sizes = new int[] {20, 36};
        for (int s = 0; s < sizes.length; s++) {
            int points = sizes[s];
            LayeredLayout layered = new LayeredLayout();
            layered.place(model, points);
            assertApart(model, points, seats(layered, model));
            ForceLayout force = new ForceLayout();
            force.place(model, 0, 0, points);
            assertApart(model, points, seats(force, model));
            RadialLayout radial = new RadialLayout();
            radial.place(model, "bird", points);
            assertApart(model, points, seats(radial, model));
            ForceLayout smaller = new ForceLayout();
            smaller.place(model, 0, 0, 20);
            int near = Math.abs(smaller.x("notebook") - smaller.x("house"))
                    + Math.abs(smaller.y("notebook") - smaller.y("house"));
            int far = Math.abs(force.x("notebook") - force.x("house"))
                    + Math.abs(force.y("notebook") - force.y("house"));
            if (points > 20) {
                assertTrue(far > near);
            }
        }
    }

    @Test
    void aLayoutChangePublishesOnePicture() throws Exception {
        final GraphPane pane = new GraphPane(fixture());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                pane.showPage(11);
                assertEquals("layered", pane.layoutName());
            }
        });
        waitFor(pane, "bird", 28, 172);
        assertEquals(-44, pane.layeredLayout().x("home"));
        assertEquals(86, pane.layeredLayout().y("home"));
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                pane.layeredLayout().seat("house", 4000, 4000);
                pane.strip().press("RESET LAYOUT");
            }
        });
        waitFor(pane, "house", 0, 0);
        assertEquals(172, pane.layeredLayout().y("bird"));
    }

    @Test
    void meatSitsNearItsIntent() {
        GraphModel model = fixture();
        LayeredLayout layered = new LayeredLayout();
        layered.place(model);
        assertNear(layered.x("house"), layered.y("house"), layered.x("home"), layered.y("home"),
                layered.x("lane"), layered.y("lane"));
        ForceLayout force = new ForceLayout();
        force.place(model, 10, 20);
        assertNear(force.x("house"), force.y("house"), force.x("home"), force.y("home"),
                force.x("bird"), force.y("bird"));
        RadialLayout radial = new RadialLayout();
        radial.place(model, "bird");
        assertNear(radial.x("house"), radial.y("house"), radial.x("home"), radial.y("home"),
                radial.x("lane"), radial.y("lane"));
        int widest = GraphSpan.step(model, 20);
        int meat = Math.abs(force.x("home") - force.x("house"))
                + Math.abs(force.y("home") - force.y("house"));
        assertTrue(meat < widest);
    }

    @Test
    void radialRingsCircleTheCenter() {
        GraphModel model = new GraphModel("conversation");
        model.addIntent("hub");
        model.addIntent("north");
        model.addIntent("east");
        model.addIntent("south");
        model.relate("supports", "hub", "north");
        model.relate("supports", "hub", "east");
        model.relate("supports", "hub", "south");
        model.takeWords("hub", "core");
        model.takeWords("north", "cap");
        RadialLayout radial = new RadialLayout();
        radial.place(model, "hub");
        assertEquals(0, radial.x("hub"));
        assertEquals(0, radial.y("hub"));
        int north = distance(radial, "north");
        int east = distance(radial, "east");
        int south = distance(radial, "south");
        assertTrue(Math.abs(north - east) <= 1);
        assertTrue(Math.abs(north - south) <= 1);
        assertTrue(north > 0);
        assertTrue(radial.x("east") != 0 && radial.y("east") != 0);
        assertTrue(radial.y("north") < 0);
        int core = distance(radial, "core");
        int cap = distance(radial, "cap");
        assertTrue(core < north);
        assertTrue(cap > north);
        int meat = Math.abs(radial.x("cap") - radial.x("north"))
                + Math.abs(radial.y("cap") - radial.y("north"));
        int other = Math.abs(radial.x("east") - radial.x("north"))
                + Math.abs(radial.y("east") - radial.y("north"));
        assertTrue(meat < other);
    }

    @Test
    void l6MeaningStaysOnTheModel() {
        GraphModel model = fixture();
        assertMeaning(model);
        new LayeredLayout().place(model);
        assertMeaning(model);
        new ForceLayout().place(model, 10, 20);
        assertMeaning(model);
        new RadialLayout().place(model, "bird");
        assertMeaning(model);
        assertFalse(hasPositionReader(GraphModel.class));
    }

    @Test
    void l7TheUnnamedSeatsStayUnnamed() {
        GraphModel model = fixture();
        new LayeredLayout().place(model);
        new ForceLayout().place(model, 0, 0);
        new RadialLayout().place(model, "house");
        assertEquals("GraphModel", GraphModel.class.getSimpleName());
        assertEquals("LayeredLayout", LayeredLayout.class.getSimpleName());
        assertEquals("ForceLayout", ForceLayout.class.getSimpleName());
        assertEquals("RadialLayout", RadialLayout.class.getSimpleName());
        assertEquals("org.aetheris.lcarsgraph", LayeredLayout.class.getPackageName());
    }

    private static GraphModel crowd() {
        GraphModel model = new GraphModel("conversation");
        String[] names = {
            "house", "bird", "lane", "porchlight", "licensefile", "casserole", "movienight", "notebook"
        };
        for (int i = 0; i < names.length; i++) {
            model.addIntent(names[i]);
        }
        model.relate("supports", "house", "bird");
        model.relate("supports", "bird", "house");
        model.relate("contains", "house", "porchlight");
        model.relate("refines", "lane", "licensefile");
        model.takeWords("house", "home dwelling");
        model.takeWords("bird", "wing");
        model.takeWords("notebook", "pageone pagetwo pagethree");
        return model;
    }

    private static List<String> painted(GraphModel model) {
        List<String> ids = new ArrayList<String>();
        List<String> intents = model.intentIds();
        for (int i = 0; i < intents.size(); i++) {
            String id = intents.get(i);
            if (!ids.contains(id)) {
                ids.add(id);
            }
            List<String> words = model.meatWords(id);
            for (int w = 0; w < words.size(); w++) {
                if (!ids.contains(words.get(w))) {
                    ids.add(words.get(w));
                }
            }
        }
        return ids;
    }

    private static Map<String, int[]> seats(LayeredLayout layout, GraphModel model) {
        Map<String, int[]> seats = new LinkedHashMap<String, int[]>();
        List<String> ids = painted(model);
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            seats.put(id, new int[] {layout.x(id), layout.y(id)});
        }
        return seats;
    }

    private static Map<String, int[]> seats(ForceLayout layout, GraphModel model) {
        Map<String, int[]> seats = new LinkedHashMap<String, int[]>();
        List<String> ids = painted(model);
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            seats.put(id, new int[] {layout.x(id), layout.y(id)});
        }
        return seats;
    }

    private static Map<String, int[]> seats(RadialLayout layout, GraphModel model) {
        Map<String, int[]> seats = new LinkedHashMap<String, int[]>();
        List<String> ids = painted(model);
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            seats.put(id, new int[] {layout.x(id), layout.y(id)});
        }
        return seats;
    }

    private static void assertApart(GraphModel model, int points, Map<String, int[]> seats) {
        List<String> ids = painted(model);
        for (int i = 0; i < ids.size(); i++) {
            for (int j = i + 1; j < ids.size(); j++) {
                String left = ids.get(i);
                String right = ids.get(j);
                assertFalse(overlaps(model, points, left, seats.get(left), right, seats.get(right)),
                        left + " overlaps " + right + " at " + points);
            }
        }
    }

    private static boolean overlaps(
            GraphModel model,
            int points,
            String leftId,
            int[] left,
            String rightId,
            int[] right) {
        int[] a = span(model, points, leftId, left);
        int[] b = span(model, points, rightId, right);
        return a[0] < b[2] && b[0] < a[2] && a[1] < b[3] && b[1] < a[3];
    }

    private static int[] span(GraphModel model, int points, String id, int[] seat) {
        boolean intent = model.hasIntent(id);
        int reached = intent ? GraphSpan.gather(model, id) : 0;
        int[] box = GraphSpan.box(id, intent, reached, points);
        return new int[] {
            seat[0] - box[0],
            seat[1] - box[2],
            seat[0] + box[1],
            seat[1] + box[3]
        };
    }

    private static GraphModel fixture() {
        GraphModel model = new GraphModel("conversation");
        model.addIntent("house");
        model.addIntent("bird");
        model.addIntent("lane");
        model.relate("supports", "house", "bird");
        model.relate("supports", "bird", "house");
        model.takeWords("house", "home");
        model.pin("house");
        model.setColor("house", "#E4DED6");
        model.setQuiet("lane", true);
        return model;
    }

    private static void waitFor(GraphPane pane, String id, int x, int y) throws Exception {
        long start = System.currentTimeMillis();
        while (pane.layoutX(id) != x || pane.layoutY(id) != y) {
            if (System.currentTimeMillis() - start > 2000) {
                break;
            }
            Thread.sleep(15);
        }
        assertEquals(x, pane.layoutX(id));
        assertEquals(y, pane.layoutY(id));
    }

    private static int distance(RadialLayout layout, String id) {
        double wide = layout.x(id);
        double high = layout.y(id);
        return (int) Math.round(Math.sqrt((wide * wide) + (high * high)));
    }

    private static void assertNear(int hx, int hy, int mx, int my, int ox, int oy) {
        int meat = Math.abs(mx - hx) + Math.abs(my - hy);
        int other = Math.abs(ox - hx) + Math.abs(oy - hy);
        assertTrue(meat < other);
        assertTrue(meat <= 130);
    }

    private static void assertMeaning(GraphModel model) {
        assertEquals(List.of("house", "bird", "lane"), model.intentIds());
        assertTrue(model.pinned("house"));
        assertFalse(model.pinned("bird"));
        assertEquals("#E4DED6", model.color("house"));
        assertNull(model.color("bird"));
        assertTrue(model.quiet("lane"));
        assertFalse(model.quiet("house"));
        assertEquals(List.of("home"), model.meatWords("house"));
        assertEquals(List.of("bird"), model.targets("house", "supports"));
        assertEquals(List.of("house"), model.targets("bird", "supports"));
        assertEquals("house", model.tether("home"));
    }

    private static String picture(LayeredLayout layout, String... ids) {
        StringBuilder built = new StringBuilder();
        for (String id : ids) {
            if (built.length() > 0) {
                built.append(' ');
            }
            built.append(id).append(' ').append(layout.x(id)).append(',').append(layout.y(id));
        }
        return built.toString();
    }

    private static String picture(ForceLayout layout, String... ids) {
        StringBuilder built = new StringBuilder();
        for (String id : ids) {
            if (built.length() > 0) {
                built.append(' ');
            }
            built.append(id).append(' ').append(layout.x(id)).append(',').append(layout.y(id));
        }
        return built.toString();
    }

    private static String picture(RadialLayout layout, String... ids) {
        StringBuilder built = new StringBuilder();
        for (String id : ids) {
            if (built.length() > 0) {
                built.append(' ');
            }
            built.append(id).append(' ').append(layout.x(id)).append(',').append(layout.y(id));
        }
        return built.toString();
    }

    private static boolean hasPositionReader(Class<?> type) {
        for (Method method : type.getDeclaredMethods()) {
            String name = method.getName();
            if ("x".equals(name) || "y".equals(name) || "placed".equals(name)) {
                return true;
            }
        }
        return false;
    }
}
