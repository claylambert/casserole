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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * The model proof runs in memory.
 *
 * @author clayl (2026-11-24)
 */
class GraphModelTest {

    @Test
    void m1OneInstanceHoldsOneConversation() {
        Object conversation = "conversation-1";
        GraphModel model = new GraphModel(conversation);
        GraphModel other = new GraphModel("conversation-2");

        assertSame(conversation, model.conversation());
        assertSame(model, model.model());
        model.addIntent("shelter");
        assertTrue(model.hasIntent("shelter"));
        assertFalse(other.hasIntent("shelter"));
        assertEquals("conversation-2", other.conversation());
    }

    @Test
    void m2TheModelHoldsTheGraphAndNoCellOrPosition() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.takeWords("shelter", "house");
        model.referent("home-place", "shelter", "house", "home");
        model.relate("supports", "shelter", "shelter");
        model.evidence("shelter", "card-1", "house");
        model.setQuiet("house", true);
        model.exclude("shelter");
        model.setColor("shelter", "#6B93FF");
        model.pin("shelter");

        assertEquals(List.of("house", "home"), model.meatWords("shelter"));
        assertEquals("home-place", model.referentOf("home"));
        assertEquals(List.of("shelter"), model.targets("shelter", "supports"));
        assertTrue(model.quiet("house"));
        assertTrue(model.excluded("shelter"));
        assertEquals("#6B93FF", model.color("shelter"));
        assertTrue(model.pinned("shelter"));
        for (Method method : GraphModel.class.getDeclaredMethods()) {
            String name = method.getName().toLowerCase();
            assertFalse(name.contains("cell"), name);
            assertFalse(name.contains("position"), name);
            assertFalse(name.contains("coordinate"), name);
        }
    }

    @Test
    void m3APinnedDropIsRefused() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.takeWords("shelter", "lane");
        model.setQuiet("lane", true);
        model.evidence("shelter", "card-7", "lane");
        model.addIntent("place");
        model.relate("supports", "shelter", "place");
        assertTrue(model.pin("shelter"));

        assertFalse(model.drop("shelter"));
        assertTrue(model.hasIntent("shelter"));
        assertEquals(List.of("lane", "card-7", "place"), model.walk("shelter"));
        assertEquals("shelter", model.tether("lane"));
        assertFalse(model.walk("place").contains("shelter"));
    }

    @Test
    void m4PullsAgainstHasNoReturnEdge() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.addIntent("place");
        model.relate("pulls against", "shelter", "place");

        assertEquals(List.of("place"), model.targets("shelter", "pulls against"));
        assertEquals(List.of(), model.targets("place", "pulls against"));
    }

    @Test
    void m5AnUpdateKeepsColorAndPin() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.setColor("shelter", "#6B93FF");
        model.pin("shelter");
        model.update("shelter", "the red barn");

        assertEquals(List.of("red", "barn"), model.meatWords("shelter"));
        assertEquals("#6B93FF", model.color("shelter"));
        assertTrue(model.pinned("shelter"));
    }

    @Test
    void m6GlueLeavesAndAMentionBindsFirst() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.takeWords("shelter", "the Bird and a house then lane red nice tangent");

        assertEquals(List.of("Bird", "house", "lane", "red", "nice", "tangent"), model.meatWords("shelter"));
        assertFalse(model.node("the"));
        assertFalse(model.node("and"));

        model.update("shelter", "Bird and it");
        assertEquals(List.of("Bird"), model.meatWords("shelter"));
        assertEquals("Bird", model.binding("it"));
        assertFalse(model.node("it"));
        assertFalse(model.node("and"));
    }

    @Test
    void m7OneReferentKeepsManySurfaceWords() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.referent("dwelling", "shelter", "house", "home", "place");

        assertEquals(1, model.intentCount());
        assertEquals("dwelling", model.referentOf("house"));
        assertEquals("dwelling", model.referentOf("home"));
        assertEquals("dwelling", model.referentOf("place"));
        assertEquals(List.of("house", "home", "place"), model.surfaces("dwelling"));
    }

    @Test
    void m8ARelationKeepsTheNamedIntents() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.addIntent("place");
        model.addIntent("path");
        model.addIntent("wing");
        model.relate("contains", "shelter", "place", "path");

        assertEquals(List.of("place", "path"), model.targets("shelter", "contains"));
        assertTrue(model.hasIntent("wing"));
        assertEquals(4, model.intentCount());
    }

    @Test
    void m9AWordKeepsOneEdgeToEachIntent() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.addIntent("place");
        model.linkWord("lane", "shelter", "place");

        assertEquals(List.of("shelter", "place"), model.wordEdges("lane"));
    }

    @Test
    void m10PinAndRejectStayPressed() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.referent("dwelling", "shelter", "house", "home");

        assertTrue(model.pin("shelter"));
        assertTrue(model.pressed("shelter", "pin"));
        assertFalse(model.pin("shelter"));
        assertFalse(model.pressed("shelter", "pin"));

        assertTrue(model.reject("shelter"));
        assertTrue(model.pressed("shelter", "reject"));
        assertTrue(model.quiet("shelter"));
        assertFalse(model.reject("shelter"));
        assertFalse(model.pressed("shelter", "reject"));
        assertTrue(model.quiet("shelter"));

        assertTrue(model.split("house"));
        assertFalse(model.pressed("house", "split"));
        assertEquals("house-referent", model.referentOf("house"));
        assertTrue(model.merge("house", "home"));
        assertFalse(model.pressed("house", "merge"));
        assertEquals(model.referentOf("house"), model.referentOf("home"));
        assertTrue(model.promote("home"));
        assertTrue(model.hasIntent("home"));
        assertFalse(model.pressed("home", "promote"));
    }

    @Test
    void m11ExcludeIsNotReject() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.exclude("shelter");
        assertTrue(model.excluded("shelter"));
        assertFalse(model.rejected("shelter"));
        model.include("shelter");
        assertFalse(model.excluded("shelter"));

        model.reject("shelter");
        assertTrue(model.rejected("shelter"));
        assertTrue(model.quiet("shelter"));
        assertFalse(model.excluded("shelter"));

        model.retire("shelter");
        assertTrue(model.hasIntent("shelter"));
        assertTrue(model.quiet("shelter"));
    }
}
