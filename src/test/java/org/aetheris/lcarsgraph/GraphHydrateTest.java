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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * The hydrate proof runs in memory.
 * The door is a script. The proof does not call a model.
 *
 * @author clayl (2026-11-24)
 */
class GraphHydrateTest {

    @Test
    void anEmptyGraphTakesTheConversation() throws Exception {
        GraphModel model = new GraphModel("conversation-1");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "the bird sees the house"));
        Script door = new Script();
        door.add(block("turn-1", "shelter", "bird"));
        Kept kept = new Kept();

        int applied = GraphDslDriver.importEmpty(model, turns, door, kept);

        assertEquals(1, applied);
        assertEquals(1, door.asked);
        assertTrue(model.hasIntent("shelter"));
        assertEquals("inferred", model.mark("shelter"));
        assertEquals(List.of("bird"), model.meatWords("shelter"));
        assertEquals(1, kept.feeds.size());
        assertEquals("card-1", kept.feeds.get(0)[0]);
        assertEquals("shelter", kept.feeds.get(0)[1]);
    }

    @Test
    void aVaultGraphIsNotReplaced() throws Exception {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("house");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "bird"));
        Script door = new Script();
        door.add(block("turn-1", "shelter", "bird"));

        int applied = GraphDslDriver.importEmpty(model, turns, door, null);

        assertEquals(0, applied);
        assertEquals(0, door.asked);
        assertTrue(model.hasIntent("house"));
        assertFalse(model.hasIntent("shelter"));
    }

    @Test
    void oneRepairFollowsAMissingAct() throws Exception {
        GraphModel model = new GraphModel("conversation-1");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "bird"));
        Script door = new Script();
        door.add("talk sits above\nintent-pass: 1\nturn: turn-1\n");
        door.add(block("turn-1", "shelter", "bird"));
        Kept kept = new Kept();

        int applied = GraphDslDriver.importEmpty(model, turns, door, kept);

        assertEquals(1, applied);
        assertEquals(2, door.asked);
        assertTrue(model.hasIntent("shelter"));
        assertTrue(door.repairs.get(0).startsWith("intent-repair: 1\nfault: missing act\n"));
        assertFalse(door.repairs.get(0).contains("talk sits above"));
    }

    @Test
    void aSecondFaultLeavesTheStepOff() throws Exception {
        GraphModel model = new GraphModel("conversation-1");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "bird"));
        Script door = new Script();
        door.add("intent-pass: 1\nturn: turn-1\n");
        door.add("intent-pass: 1\nturn: turn-1\nshift:\n- none\n");

        int applied = GraphDslDriver.importEmpty(model, turns, door, null);

        assertEquals(0, applied);
        assertEquals(0, model.intentCount());
        assertEquals(2, door.asked);
    }

    @Test
    void aGlueWordIsDroppedAndACellLineDoesNotBirth() throws Exception {
        GraphModel model = new GraphModel("conversation-1");
        String reply = "intent-pass: 1\n"
                + "act:\n- shelter born inferred\n"
                + "shift:\n- shelter added\n"
                + "meat:\n- the on shelter\n- bird on shelter\n"
                + "evidence:\n- shelter sees bird\n"
                + "cell:\n- turn-1 shelter\n- turn-1 missing\n";
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "the bird"));
        List<String[]> feeds = new ArrayList<String[]>();

        GraphDslDriver.apply(model, turns, turns, reply, feeds);

        assertEquals(List.of("bird"), model.meatWords("shelter"));
        assertFalse(model.hasIntent("missing"));
        assertEquals(1, feeds.size());
    }

    @Test
    void aFreshDropIsAFault() {
        GraphModel model = new GraphModel("conversation-1");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "bird"));
        String reply = "intent-pass: 1\n"
                + "act:\n- shelter retired\n"
                + "shift:\n- shelter dropped\n";

        assertEquals("dropped on fresh", GraphDslDriver.fault(model, turns, turns, "fresh", reply));
    }

    @Test
    void aStoredPinMustBeEchoed() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.pin("shelter");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "bird"));
        String missing = "intent-pass: 1\nact:\n- none\nshift:\n- none\n";
        String echoed = missing + "constraint-kept:\n- pin shelter\n";

        assertEquals("missing echo", GraphDslDriver.fault(model, turns, turns, "import", missing));
        assertEquals(null, GraphDslDriver.fault(model, turns, turns, "import", echoed));
    }

    @Test
    void aMapPhraseIsNotAKeptGraph() throws Exception {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("conversation-map");
        model.takeWords("conversation-map", "github yeehaw");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "the bird sees the house"));
        Script door = new Script();
        door.add(block("turn-1", "shelter", "bird"));

        int applied = GraphDslDriver.importEmpty(model, turns, door, null);

        assertEquals(1, applied);
        assertEquals(1, door.asked);
        assertTrue(model.hasIntent("shelter"));
        assertFalse(model.hasIntent("conversation-map"));
    }

    @Test
    void aMapNameIsAFault() {
        GraphModel model = new GraphModel("conversation-1");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "bird"));
        String reply = block("turn-1", "conversation-map", "bird");

        assertEquals("name", GraphDslDriver.fault(model, turns, turns, "import", reply));
        assertTrue(GraphDslDriver.phraseGraph(model) == false);
        model.addIntent("conversation-map");
        assertTrue(GraphDslDriver.phraseGraph(model));
        model.addIntent("shelter");
        assertTrue(GraphDslDriver.phraseGraph(model) == false);
    }

    @Test
    void aPhraseAimIsKept() {
        GraphModel model = new GraphModel("conversation-1");
        String reply = "intent-pass: 1\n"
                + "act:\n- [porch light] born inferred\n"
                + "shift:\n- [porch light] added\n"
                + "meat:\n- bulb on [porch light]\n"
                + "evidence:\n- [porch light] sees bulb\n"
                + "cell:\n- turn-1 [porch light]\n";
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "the porch light"));
        List<String[]> feeds = new ArrayList<String[]>();

        assertEquals(null, GraphDslDriver.fault(model, turns, turns, "fresh", reply));
        GraphDslDriver.apply(model, turns, turns, reply, feeds);

        assertTrue(model.hasIntent("porch light"));
        assertEquals(List.of("bulb"), model.meatWords("porch light"));
        assertEquals(1, feeds.size());
        assertEquals("porch light", feeds.get(0)[1]);
    }

    @Test
    void anUnbracketedPhraseIsTheSameAim() {
        GraphModel model = new GraphModel("conversation-1");
        String reply = "intent-pass: 1\n"
                + "act:\n- porch light born inferred\n"
                + "shift:\n- porch light added\n"
                + "meat:\n- bulb on porch light\n"
                + "cell:\n- turn-1 porch light\n";
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "the porch light"));

        assertEquals(null, GraphDslDriver.fault(model, turns, turns, "fresh", reply));
        GraphDslDriver.apply(model, turns, turns, reply, new ArrayList<String[]>());
        assertTrue(model.hasIntent("porch light"));
        assertEquals(List.of("bulb"), model.meatWords("porch light"));
    }

    @Test
    void fourWordsIsALineFault() {
        GraphModel model = new GraphModel("conversation-1");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "bird"));
        String reply = "intent-pass: 1\n"
                + "act:\n- one two three four born inferred\n"
                + "shift:\n- none\n";

        assertEquals("line", GraphDslDriver.fault(model, turns, turns, "fresh", reply));
    }

    @Test
    void manyMeatWordsAndARelationStay() {
        GraphModel model = new GraphModel("conversation-1");
        String reply = "intent-pass: 1\n"
                + "act:\n- shelter born inferred\n- lane born inferred\n"
                + "shift:\n- shelter added\n- lane added\n"
                + "meat:\n- bird on shelter\n- roof on shelter\n- nest on shelter\n- sky on shelter\n"
                + "- bird on lane\n- stone on lane\n"
                + "relation:\n- shelter supports lane\n"
                + "evidence:\n- shelter sees bird\n- lane sees stone\n"
                + "cell:\n- turn-1 shelter\n- turn-1 lane\n";
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "bird roof nest sky stone lane"));

        assertNull(GraphDslDriver.fault(model, turns, turns, "fresh", reply));
        GraphDslDriver.apply(model, turns, turns, reply, new ArrayList<String[]>());

        assertEquals(List.of("bird", "roof", "nest", "sky"), model.meatWords("shelter"));
        assertEquals(List.of("bird", "stone"), model.meatWords("lane"));
        assertEquals(List.of("lane"), model.targets("shelter", "supports"));
        assertTrue(model.wordEdges("bird").contains("shelter"));
        assertTrue(model.wordEdges("bird").contains("lane"));
    }

    @Test
    void aLongConversationStaysInSteps() {
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", words(7000)));
        turns.add(new GraphTurn("card-2", "turn-2", words(7000)));
        turns.add(new GraphTurn("card-3", "turn-3", "lane"));

        List<List<GraphTurn>> plan = GraphDslDriver.steps(turns);

        assertEquals(2, plan.size());
        assertEquals(1, plan.get(0).size());
        assertEquals(2, plan.get(1).size());
    }

    @Test
    void aFreshGraphGrowsTurnByTurn() throws Exception {
        GraphModel model = new GraphModel("born");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "bird"));
        turns.add(new GraphTurn("card-2", "turn-2", "lane"));
        final List<String> briefs = new ArrayList<String>();
        GraphDoor door = new GraphDoor() {
            @Override
            public String ask(String brief) {
                briefs.add(brief);
                if (briefs.size() == 1) {
                    return block("turn-1", "shelter", "bird");
                }
                return block("turn-2", "porch", "lane");
            }
        };
        int applied = GraphDslDriver.grow(model, turns, door, null);
        assertEquals(2, applied);
        assertEquals(2, briefs.size());
        assertTrue(briefs.get(0).contains("opening: fresh\n"));
        assertTrue(briefs.get(0).contains("role: name each aim this cell holds\n"));
        assertTrue(briefs.get(1).contains("opening: turn\n"));
        assertTrue(briefs.get(1).contains(
                "role: read the wire window and name an add, a drop, or a change\n"));
        assertTrue(briefs.get(1).contains("already-read:\n- none\n"));
        assertTrue(model.hasIntent("shelter"));
        assertTrue(model.hasIntent("porch"));
    }

    @Test
    void aHeldGraphGrowsOnlyTheNewestTurn() throws Exception {
        GraphModel model = new GraphModel("held");
        model.addIntent("shelter");
        List<GraphTurn> turns = new ArrayList<GraphTurn>();
        turns.add(new GraphTurn("card-1", "turn-1", "bird"));
        turns.add(new GraphTurn("card-2", "turn-2", "lane"));
        final List<String> briefs = new ArrayList<String>();
        GraphDoor door = new GraphDoor() {
            @Override
            public String ask(String brief) {
                briefs.add(brief);
                return block("turn-2", "porch", "lane");
            }
        };
        int applied = GraphDslDriver.grow(model, turns, door, null);
        assertEquals(1, applied);
        assertEquals(1, briefs.size());
        assertTrue(briefs.get(0).contains("opening: turn\n"));
        assertTrue(model.hasIntent("shelter"));
        assertTrue(model.hasIntent("porch"));
        assertEquals(0, GraphDslDriver.importEmpty(model, turns, door, null));
    }

    private static String words(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append('x');
        }
        return sb.toString();
    }

    private static String block(String card, String intent, String word) {
        return "intent-pass: 1\n"
                + "turn: " + card + "\n"
                + "act:\n- " + intent + " born inferred\n"
                + "shift:\n- " + intent + " added\n"
                + "meat:\n- " + word + " on " + intent + "\n"
                + "evidence:\n- " + intent + " sees " + word + "\n"
                + "cell:\n- " + card + " " + intent + "\n";
    }

    private static final class Script implements GraphDoor {
        private final List<String> replies = new ArrayList<String>();
        private final List<String> repairs = new ArrayList<String>();
        private int asked;

        private void add(String reply) {
            replies.add(reply);
        }

        @Override
        public String ask(String brief) {
            int at = asked;
            asked++;
            if (brief != null && brief.startsWith("intent-repair:")) {
                repairs.add(brief);
            }
            return replies.get(at);
        }
    }

    private static final class Kept implements GraphDslDriver.Land {
        private final List<String[]> feeds = new ArrayList<String[]>();

        @Override
        public void land(List<String[]> rows) {
            feeds.addAll(rows);
        }
    }
}
