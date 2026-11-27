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
import java.util.Locale;
import java.util.Map;

/**
 * One conversation keeps one model.
 * A later update does not replace it.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1472 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public final class GraphModel {

    private static final List<String> GLUE = List.of("and", "a", "the", "then");
    private static final List<String> MENTIONS = List.of("it", "that", "this");

    private final Object conversation;
    private final Map<String, Intent> intents = new LinkedHashMap<>();
    private final Map<String, Referent> referents = new LinkedHashMap<>();
    private final Map<String, String> wordIntent = new LinkedHashMap<>();
    private final Map<String, String> wordReferent = new LinkedHashMap<>();
    private final Map<String, List<String>> wordLinks = new LinkedHashMap<>();
    private final Map<String, String> bindings = new LinkedHashMap<>();
    private final List<Edge> edges = new ArrayList<>();
    private final List<Evidence> evidence = new ArrayList<>();
    private final List<String> quietWords = new ArrayList<>();
    private GraphModelListener listener;

    public GraphModel(Object conversation) {
        this.conversation = conversation;
    }

    public Object conversation() {
        return conversation;
    }

    public GraphModel model() {
        return this;
    }

    public void addIntent(String id) {
        if (id == null || intents.containsKey(id)) {
            return;
        }
        intents.put(id, new Intent(id));
    }

    public boolean hasIntent(String id) {
        return intents.containsKey(id);
    }

    public int intentCount() {
        return intents.size();
    }

    public List<String> intentIds() {
        return new ArrayList<>(intents.keySet());
    }

    public void takeWords(String intentId, String phrase) {
        Intent intent = intents.get(intentId);
        if (intent == null) {
            return;
        }
        readWords(intent, phrase, false);
    }

    public void update(String intentId, String phrase) {
        Intent intent = intents.get(intentId);
        if (intent == null) {
            return;
        }
        readWords(intent, phrase, true);
    }

    public List<String> meatWords(String intentId) {
        Intent intent = intents.get(intentId);
        if (intent == null) {
            return List.of();
        }
        return new ArrayList<>(intent.words);
    }

    public String binding(String mention) {
        if (mention == null) {
            return null;
        }
        return bindings.get(mention.toLowerCase(Locale.ROOT));
    }

    public boolean node(String name) {
        return intents.containsKey(name) || wordIntent.containsKey(name) || wordLinks.containsKey(name);
    }

    public void referent(String referentId, String intentId, String... surfaces) {
        if (referentId == null || !intents.containsKey(intentId) || surfaces == null) {
            return;
        }
        Referent referent = referents.get(referentId);
        if (referent == null) {
            referent = new Referent(referentId, intentId);
            referents.put(referentId, referent);
        }
        referent.intentId = intentId;
        Intent intent = intents.get(intentId);
        for (String surface : surfaces) {
            if (surface == null) {
                continue;
            }
            if (!referent.surfaces.contains(surface)) {
                referent.surfaces.add(surface);
            }
            wordReferent.put(surface, referentId);
            wordIntent.put(surface, intentId);
            if (!intent.words.contains(surface)) {
                intent.words.add(surface);
            }
        }
    }

    public String referentOf(String word) {
        return wordReferent.get(word);
    }

    public List<String> surfaces(String referentId) {
        Referent referent = referents.get(referentId);
        if (referent == null) {
            return List.of();
        }
        return new ArrayList<>(referent.surfaces);
    }

    public void relate(String relation, String from, String... to) {
        if (relation == null || !intents.containsKey(from) || to == null) {
            return;
        }
        for (String target : to) {
            if (!intents.containsKey(target)) {
                continue;
            }
            edges.add(new Edge(from, relation, target));
        }
    }

    public List<String> targets(String from, String relation) {
        List<String> found = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.from.equals(from) && edge.relation.equals(relation)) {
                found.add(edge.to);
            }
        }
        return found;
    }

    public void linkWord(String word, String first, String second) {
        if (word == null || first == null || second == null) {
            return;
        }
        List<String> links = wordLinks.get(word);
        if (links == null) {
            links = new ArrayList<>();
            wordLinks.put(word, links);
        }
        if (!links.contains(first)) {
            links.add(first);
        }
        if (!links.contains(second)) {
            links.add(second);
        }
    }

    public List<String> wordEdges(String word) {
        List<String> links = wordLinks.get(word);
        if (links == null) {
            return List.of();
        }
        return new ArrayList<>(links);
    }

    public void evidence(String intentId, String cardId, String word) {
        if (intentId == null || cardId == null || word == null) {
            return;
        }
        evidence.add(new Evidence(intentId, cardId, word));
    }

    public void setQuiet(String id, boolean quiet) {
        Intent intent = intents.get(id);
        if (intent != null) {
            intent.quiet = quiet;
            return;
        }
        if (quiet) {
            if (!quietWords.contains(id)) {
                quietWords.add(id);
            }
            return;
        }
        quietWords.remove(id);
    }

    public boolean quiet(String id) {
        Intent intent = intents.get(id);
        if (intent != null) {
            return intent.quiet;
        }
        return quietWords.contains(id);
    }

    public void setColor(String intentId, String color) {
        Intent intent = intents.get(intentId);
        if (intent == null) {
            return;
        }
        intent.color = color;
    }

    public String color(String intentId) {
        Intent intent = intents.get(intentId);
        if (intent == null) {
            return null;
        }
        return intent.color;
    }

    public String mark(String id) {
        Intent intent = intents.get(id);
        if (intent == null) {
            return null;
        }
        return intent.mark;
    }

    public void setMark(String id, String mark) {
        Intent intent = intents.get(id);
        if (intent == null) {
            return;
        }
        if (!"stated".equals(mark) && !"inferred".equals(mark)) {
            throw new IllegalArgumentException("mark");
        }
        intent.mark = mark;
    }

    public boolean pin(String id) {
        Intent intent = intents.get(id);
        if (intent == null) {
            return false;
        }
        intent.pinned = !intent.pinned;
        return intent.pinned;
    }

    public boolean pinned(String id) {
        Intent intent = intents.get(id);
        return intent != null && intent.pinned;
    }

    public boolean reject(String id) {
        Intent intent = intents.get(id);
        if (intent == null) {
            return false;
        }
        intent.rejected = !intent.rejected;
        if (intent.rejected) {
            intent.quiet = true;
        }
        return intent.rejected;
    }

    public boolean rejected(String id) {
        Intent intent = intents.get(id);
        return intent != null && intent.rejected;
    }

    public boolean drop(String id) {
        Intent intent = intents.get(id);
        if (intent == null || intent.pinned) {
            return false;
        }
        intents.remove(id);
        List<String> words = new ArrayList<>(intent.words);
        for (String word : words) {
            if (id.equals(wordIntent.get(word))) {
                wordIntent.remove(word);
                String referentId = wordReferent.remove(word);
                if (referentId != null) {
                    Referent referent = referents.get(referentId);
                    if (referent != null) {
                        referent.surfaces.remove(word);
                        if (referent.surfaces.isEmpty()) {
                            referents.remove(referentId);
                        }
                    }
                }
            }
        }
        List<Edge> keptEdges = new ArrayList<>();
        for (Edge edge : edges) {
            if (!edge.from.equals(id) && !edge.to.equals(id)) {
                keptEdges.add(edge);
            }
        }
        edges.clear();
        edges.addAll(keptEdges);
        List<Evidence> keptEvidence = new ArrayList<>();
        for (Evidence row : evidence) {
            if (!row.intent.equals(id)) {
                keptEvidence.add(row);
            }
        }
        evidence.clear();
        evidence.addAll(keptEvidence);
        return true;
    }

    /**
     * One model keeps one listener.
     * A later call replaces that listener.
     * A null argument clears it.
     *
     * @param listener the notebook body, or null
     */
    public void hear(GraphModelListener listener) {
        this.listener = listener;
    }

    public void exclude(String id) {
        Intent intent = intents.get(id);
        if (intent == null || intent.excluded) {
            return;
        }
        intent.excluded = true;
        speak(intent, "excluded");
    }

    public void include(String id) {
        Intent intent = intents.get(id);
        if (intent == null || !intent.excluded) {
            return;
        }
        intent.excluded = false;
        speak(intent, "included");
    }

    public boolean excluded(String id) {
        Intent intent = intents.get(id);
        return intent != null && intent.excluded;
    }

    public boolean split(String word) {
        String referentId = wordReferent.get(word);
        String intentId = wordIntent.get(word);
        if (referentId == null || intentId == null) {
            return false;
        }
        Referent old = referents.get(referentId);
        old.surfaces.remove(word);
        if (old.surfaces.isEmpty()) {
            referents.remove(referentId);
        }
        String createdId = word + "-referent";
        Referent created = new Referent(createdId, intentId);
        created.surfaces.add(word);
        referents.put(createdId, created);
        wordReferent.put(word, createdId);
        return true;
    }

    public boolean merge(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        String intentId = wordIntent.get(first);
        if (intentId == null || !wordIntent.containsKey(second)) {
            return false;
        }
        String referentId = wordReferent.get(first);
        Referent target = referentId == null ? null : referents.get(referentId);
        if (target == null) {
            referentId = first + "-referent";
            target = new Referent(referentId, intentId);
            target.surfaces.add(first);
            referents.put(referentId, target);
            wordReferent.put(first, referentId);
        }
        String otherId = wordReferent.get(second);
        if (otherId != null && !otherId.equals(referentId)) {
            Referent other = referents.get(otherId);
            if (other != null) {
                other.surfaces.remove(second);
                if (other.surfaces.isEmpty()) {
                    referents.remove(otherId);
                }
            }
        }
        if (!target.surfaces.contains(second)) {
            target.surfaces.add(second);
        }
        wordReferent.put(second, referentId);
        return true;
    }

    public boolean promote(String word) {
        if (word == null || (!wordIntent.containsKey(word) && !wordLinks.containsKey(word))) {
            return false;
        }
        if (!intents.containsKey(word)) {
            intents.put(word, new Intent(word));
        }
        return true;
    }

    public boolean pressed(String id, String act) {
        if ("pin".equals(act)) {
            return pinned(id);
        }
        if ("reject".equals(act)) {
            return rejected(id);
        }
        return false;
    }

    public void retire(String id) {
        Intent intent = intents.get(id);
        if (intent == null) {
            return;
        }
        intent.quiet = true;
    }

    public List<String> walk(String intentId) {
        List<String> steps = new ArrayList<>();
        Intent intent = intents.get(intentId);
        if (intent == null) {
            return steps;
        }
        for (String word : intent.words) {
            steps.add(word);
            for (Evidence row : evidence) {
                if (row.intent.equals(intentId) && row.word.equals(word)) {
                    steps.add(row.card);
                }
            }
        }
        for (Edge edge : edges) {
            if (edge.from.equals(intentId)) {
                steps.add(edge.to);
            }
        }
        return steps;
    }

    public String tether(String word) {
        return wordIntent.get(word);
    }

    void vacant() {
        intents.clear();
        referents.clear();
        wordIntent.clear();
        wordReferent.clear();
        wordLinks.clear();
        bindings.clear();
        edges.clear();
        evidence.clear();
        quietWords.clear();
    }

    void seatPinned(String id, boolean pinned) {
        Intent intent = intents.get(id);
        if (intent == null) {
            return;
        }
        intent.pinned = pinned;
    }

    void seatRejected(String id, boolean rejected) {
        Intent intent = intents.get(id);
        if (intent == null) {
            return;
        }
        intent.rejected = rejected;
    }

    List<String[]> heldEdges() {
        List<String[]> rows = new ArrayList<String[]>();
        for (Edge edge : edges) {
            rows.add(new String[] {edge.from, edge.relation, edge.to});
        }
        return rows;
    }

    List<String[]> heldEvidence() {
        List<String[]> rows = new ArrayList<String[]>();
        for (Evidence row : evidence) {
            rows.add(new String[] {row.intent, row.card, row.word});
        }
        return rows;
    }

    private void readWords(Intent intent, String phrase, boolean replace) {
        if (replace) {
            List<String> prior = new ArrayList<>(intent.words);
            intent.words.clear();
            for (String word : prior) {
                if (intent.id.equals(wordIntent.get(word))) {
                    wordIntent.remove(word);
                }
                quietWords.remove(word);
            }
        }
        if (phrase == null || phrase.isBlank()) {
            return;
        }
        String last = null;
        if (!intent.words.isEmpty()) {
            last = intent.words.get(intent.words.size() - 1);
        }
        String[] tokens = phrase.trim().split("\\s+");
        for (String token : tokens) {
            String lower = token.toLowerCase(Locale.ROOT);
            if (MENTIONS.contains(lower)) {
                if (last != null) {
                    bindings.put(lower, last);
                }
                continue;
            }
            if (GLUE.contains(lower)) {
                continue;
            }
            if (!intent.words.contains(token)) {
                intent.words.add(token);
            }
            wordIntent.put(token, intent.id);
            last = token;
        }
    }

    private void speak(Intent intent, String mark) {
        if (listener == null) {
            return;
        }
        List<String> cards = cardsOf(intent);
        for (int i = 0; i < cards.size(); i++) {
            String card = cards.get(i);
            if (heldByAnother(intent, card)) {
                continue;
            }
            listener.notice(card, mark);
        }
    }

    private List<String> cardsOf(Intent intent) {
        List<String> cards = new ArrayList<>();
        for (int i = 0; i < evidence.size(); i++) {
            Evidence row = evidence.get(i);
            if (!names(intent, row)) {
                continue;
            }
            if (row.card == null || cards.contains(row.card)) {
                continue;
            }
            cards.add(row.card);
        }
        return cards;
    }

    private boolean names(Intent intent, Evidence row) {
        if (row.intent.equals(intent.id)) {
            return true;
        }
        for (int i = 0; i < intent.words.size(); i++) {
            if (intent.words.get(i).equals(row.word)) {
                return true;
            }
        }
        return false;
    }

    private boolean heldByAnother(Intent intent, String card) {
        for (Intent other : intents.values()) {
            if (other.id.equals(intent.id) || !other.excluded) {
                continue;
            }
            if (cardsOf(other).contains(card)) {
                return true;
            }
        }
        return false;
    }

    private static final class Intent {
        private final String id;
        private final List<String> words = new ArrayList<>();
        private String mark;
        private String color;
        private boolean quiet;
        private boolean excluded;
        private boolean pinned;
        private boolean rejected;

        private Intent(String id) {
            this.id = id;
            this.mark = "stated";
        }
    }

    private static final class Referent {
        private final String id;
        private String intentId;
        private final List<String> surfaces = new ArrayList<>();

        private Referent(String id, String intentId) {
            this.id = id;
            this.intentId = intentId;
        }
    }

    private static final class Edge {
        private final String from;
        private final String relation;
        private final String to;

        private Edge(String from, String relation, String to) {
            this.from = from;
            this.relation = relation;
            this.to = to;
        }
    }

    private static final class Evidence {
        private final String intent;
        private final String card;
        private final String word;

        private Evidence(String intent, String card, String word) {
            this.intent = intent;
            this.card = card;
            this.word = word;
        }
    }
}
