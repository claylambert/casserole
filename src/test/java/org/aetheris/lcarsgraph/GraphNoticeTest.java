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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * The notice proof runs in memory.
 *
 * @author clayl (2026-11-24)
 */
class GraphNoticeTest {

    @Test
    void n1AnExcludeSpeaksOnceForEachChangedCard() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.evidence("shelter", "card-a", "house");
        model.evidence("shelter", "card-b", "lane");
        model.evidence("shelter", "card-a", "house");
        Heard heard = new Heard();
        model.hear(heard);

        model.exclude("shelter");

        assertEquals(List.of("card-a", "card-b"), heard.cards);
        assertEquals(List.of("excluded", "excluded"), heard.marks);
        assertTrue(model.excluded("shelter"));
    }

    @Test
    void n1AMeatWordCitesACard() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.takeWords("shelter", "house");
        model.addIntent("hearth");
        model.evidence("hearth", "cited", "house");
        Heard heard = new Heard();
        model.hear(heard);

        model.exclude("shelter");

        assertEquals(List.of("cited"), heard.cards);
        assertEquals(List.of("excluded"), heard.marks);
        assertFalse(model.excluded("hearth"));
    }

    @Test
    void n2ASharedCardSeatsOnceAndTheOtherIntentStaysLit() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.addIntent("hearth");
        model.evidence("shelter", "shared", "house");
        model.evidence("shelter", "only-shelter", "lane");
        model.evidence("hearth", "shared", "fire");
        model.evidence("hearth", "only-hearth", "fire");
        Heard heard = new Heard();
        model.hear(heard);

        model.exclude("shelter");

        assertEquals(List.of("shared", "only-shelter"), heard.cards);
        assertFalse(model.excluded("hearth"));

        model.exclude("hearth");

        assertEquals(List.of("shared", "only-shelter", "only-hearth"), heard.cards);
        assertEquals("excluded", heard.marks.get(2));
    }

    @Test
    void n3ASecondExcludeSendsNothing() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.evidence("shelter", "card-a", "house");
        Heard heard = new Heard();
        model.hear(heard);
        model.exclude("shelter");

        model.exclude("shelter");

        assertEquals(1, heard.cards.size());
        assertTrue(model.excluded("shelter"));
    }

    @Test
    void n4IncludeReleasesOnlyTheCardsNoOtherExcludedIntentNames() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.addIntent("hearth");
        model.evidence("shelter", "shared", "house");
        model.evidence("shelter", "only-shelter", "lane");
        model.evidence("hearth", "shared", "fire");
        model.evidence("hearth", "only-hearth", "fire");
        Heard heard = new Heard();
        model.hear(heard);
        model.exclude("shelter");
        model.exclude("hearth");
        int before = heard.cards.size();

        model.include("shelter");

        assertEquals(before + 1, heard.cards.size());
        assertEquals("only-shelter", heard.cards.get(heard.cards.size() - 1));
        assertEquals("included", heard.marks.get(heard.marks.size() - 1));
        assertFalse(model.excluded("shelter"));
        assertTrue(model.excluded("hearth"));

        model.include("hearth");

        assertEquals(List.of("shared", "only-hearth"), heard.cards.subList(before + 1, heard.cards.size()));
        assertEquals(List.of("included", "included"), heard.marks.subList(before + 1, heard.marks.size()));
        assertFalse(model.excluded("hearth"));
    }

    @Test
    void n5AMissingListenerLeavesTheMark() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.evidence("shelter", "card-a", "house");

        model.exclude("shelter");

        assertTrue(model.excluded("shelter"));
    }

    @Test
    void n6ALaterHearReplacesTheListener() {
        GraphModel model = new GraphModel("conversation-1");
        model.addIntent("shelter");
        model.evidence("shelter", "card-a", "house");
        Heard first = new Heard();
        Heard second = new Heard();
        model.hear(first);
        model.hear(second);

        model.exclude("shelter");

        assertEquals(0, first.cards.size());
        assertEquals(List.of("card-a"), second.cards);
        model.include("shelter");
        model.hear(null);
        model.exclude("shelter");
        assertTrue(model.excluded("shelter"));
        assertEquals(List.of("card-a", "card-a"), second.cards);
        assertEquals(List.of("excluded", "included"), second.marks);
    }

    private static final class Heard implements GraphModelListener {
        private final List<String> cards = new ArrayList<>();
        private final List<String> marks = new ArrayList<>();

        @Override
        public void notice(String card, String mark) {
            cards.add(card);
            marks.add(mark);
        }
    }
}
