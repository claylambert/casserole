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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * One empty graph hydrates from the conversation.
 * A graph that already holds an intent is left as the vault restored it.
 * The ask is the EN-IR brief. One repair follows a fault.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1477 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
// @aa-sdd L40 aetheris-plan-f-310.001.md FINESSE:f-310
public final class GraphDslDriver {

    /** One step stays inside this many characters. A longer turn stands alone. */
    static final int STEP_CHARS = 12000;

    private static final String[] GLUE = {"and", "a", "the", "then"};
    private static final String[] RELATIONS = {"pulls against", "supports", "refines", "contains"};
    private static final String[] ACTS = {"born", "reinforced", "revised", "retired"};
    private static final String[] SHIFTS = {"added", "dropped", "changed"};

    private GraphDslDriver() {
    }

    /**
     * The method receives one committed step.
     * The feeds are the card and node pairs that step wrote.
     */
    public interface Land {
        /**
         * The method stores the step that just passed.
         *
         * @param feeds The feeds are card and node pairs.
         */
        void land(List<String[]> feeds);
    }

    /**
     * The method fills an empty model from the turns.
     * A model that already holds an aim returns zero and does not ask.
     * A model whose every intent is the map phrase is not a kept map. That model is cleared and asked.
     * A missing door, a missing model, or an empty turn list returns zero.
     * Each passing step is applied before the next ask.
     * A second fault leaves that step off the model.
     *
     * @param model The model receives a passing block.
     * @param turns The turns are the conversation, oldest first.
     * @param door The door sends one brief.
     * @param land The land stores one passing step. A null land only fills the model.
     * @return The return value is the number of steps applied.
     * @throws Exception The exception is a door that does not send.
     */
    public static int importEmpty(GraphModel model, List<GraphTurn> turns, GraphDoor door, Land land)
            throws Exception {
        if (model == null || door == null || turns == null || turns.isEmpty()) {
            return 0;
        }
        if (phraseGraph(model)) {
            model.vacant();
        }
        if (model.intentCount() > 0) {
            return 0;
        }
        List<List<GraphTurn>> plan = steps(turns);
        String opening = turns.size() == 1 ? "fresh" : "import";
        int applied = 0;
        for (int s = 0; s < plan.size(); s++) {
            List<GraphTurn> window = plan.get(s);
            String reply = door.ask(brief(model, turns, window, s + 1, plan.size(), opening));
            String problem = fault(model, turns, window, opening, reply);
            if (problem != null) {
                String again = brief(model, turns, window, s + 1, plan.size(), opening);
                reply = door.ask(repair(problem, again));
                problem = fault(model, turns, window, opening, reply);
            }
            if (problem != null) {
                continue;
            }
            List<String[]> feeds = new ArrayList<String[]>();
            apply(model, turns, window, reply, feeds);
            if (land != null) {
                land.land(feeds);
            }
            applied++;
        }
        return applied;
    }

    /**
     * The method walks a fresh graph, then each later turn.
     * An empty model births the first turn and folds each later turn.
     * A model that already holds an aim folds only the newest turn.
     * The import walk stays on {@link #importEmpty}.
     *
     * @param model The model receives a passing block.
     * @param turns The turns are the conversation, oldest first.
     * @param door The door sends one brief.
     * @param land The land stores one passing step. A null land only fills the model.
     * @return The return value is the number of steps applied.
     * @throws Exception The exception is a door that does not send.
     */
    public static int grow(GraphModel model, List<GraphTurn> turns, GraphDoor door, Land land)
            throws Exception {
        if (model == null || door == null || turns == null || turns.isEmpty()) {
            return 0;
        }
        if (phraseGraph(model)) {
            model.vacant();
        }
        int applied = 0;
        if (model.intentCount() == 0) {
            for (int i = 0; i < turns.size(); i++) {
                GraphTurn turn = turns.get(i);
                if (turn == null) {
                    continue;
                }
                List<GraphTurn> window = new ArrayList<GraphTurn>();
                window.add(turn);
                String opening = applied == 0 ? "fresh" : "turn";
                List<GraphTurn> known = "fresh".equals(opening) ? window : turns;
                applied += askOne(model, known, window, opening, door, land);
            }
            return applied;
        }
        GraphTurn newest = null;
        for (int i = turns.size() - 1; i >= 0; i--) {
            if (turns.get(i) != null) {
                newest = turns.get(i);
                break;
            }
        }
        if (newest == null) {
            return 0;
        }
        List<GraphTurn> window = new ArrayList<GraphTurn>();
        window.add(newest);
        return askOne(model, turns, window, "turn", door, land);
    }

    private static int askOne(GraphModel model, List<GraphTurn> known, List<GraphTurn> window,
            String opening, GraphDoor door, Land land) throws Exception {
        String reply = door.ask(brief(model, known, window, 1, 1, opening));
        String problem = fault(model, known, window, opening, reply);
        if (problem != null) {
            String again = brief(model, known, window, 1, 1, opening);
            reply = door.ask(repair(problem, again));
            problem = fault(model, known, window, opening, reply);
        }
        if (problem != null) {
            return 0;
        }
        List<String[]> feeds = new ArrayList<String[]>();
        apply(model, known, window, reply, feeds);
        if (land != null) {
            land.land(feeds);
        }
        return 1;
    }

    /**
     * The method groups the next unread turns while their words fit in one step.
     * A turn longer than the step stands in its own group.
     *
     * @param turns The turns are oldest first.
     * @return The return value is the steps.
     */
    public static List<List<GraphTurn>> steps(List<GraphTurn> turns) {
        List<List<GraphTurn>> out = new ArrayList<List<GraphTurn>>();
        if (turns == null) {
            return out;
        }
        List<GraphTurn> step = new ArrayList<GraphTurn>();
        int chars = 0;
        for (int i = 0; i < turns.size(); i++) {
            GraphTurn turn = turns.get(i);
            int size = turn == null || turn.words() == null ? 0 : turn.words().length();
            if (!step.isEmpty() && chars + size > STEP_CHARS) {
                out.add(step);
                step = new ArrayList<GraphTurn>();
                chars = 0;
            }
            if (turn != null) {
                step.add(turn);
                chars += size;
            }
        }
        if (!step.isEmpty()) {
            out.add(step);
        }
        return out;
    }

    /**
     * The method builds one intent ask.
     *
     * @param model The model is the graph already accepted.
     * @param all The all list is the whole conversation.
     * @param window The window is this step.
     * @param step The step is one-based.
     * @param count The count is the number of steps.
     * @param opening The opening is fresh, import, or turn.
     * @return The return value is the brief.
     */
    public static String brief(GraphModel model, List<GraphTurn> all, List<GraphTurn> window,
            int step, int count, String opening) {
        StringBuilder sb = new StringBuilder();
        sb.append("intent-ask: 1\n");
        sb.append("opening: ").append(opening).append('\n');
        if ("import".equals(opening)) {
            sb.append("step: ").append(step).append(" of ").append(count).append('\n');
            sb.append("role: name each aim this step holds\n");
        } else if ("turn".equals(opening)) {
            sb.append("role: read the wire window and name an add, a drop, or a change\n");
        } else {
            sb.append("role: name each aim this cell holds\n");
        }
        GraphTurn newest = window.get(window.size() - 1);
        sb.append("turn: ").append(newest.label()).append('\n');
        sb.append("window:\n");
        for (int i = 0; i < window.size(); i++) {
            GraphTurn turn = window.get(i);
            sb.append("- ").append(turn.label()).append(' ').append(turn.words()).append('\n');
        }
        sb.append("already-read:\n");
        if ("turn".equals(opening)) {
            sb.append("- none\n");
        } else {
            boolean read = false;
            if (all != null) {
                for (int i = 0; i < all.size(); i++) {
                    GraphTurn turn = all.get(i);
                    if (turn == null || inWindow(window, turn)) {
                        continue;
                    }
                    if (indexOf(all, turn) >= indexOf(all, window.get(0))) {
                        continue;
                    }
                    sb.append("- ").append(turn.label()).append('\n');
                    read = true;
                }
            }
            if (!read) {
                sb.append("- none\n");
            }
        }
        sb.append("graph:\n");
        boolean named = false;
        if (model != null) {
            List<String> ids = model.intentIds();
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                if (model.quiet(id)) {
                    continue;
                }
                sb.append("- ").append(bracket(id)).append(' ').append(markOf(model, id)).append(" carried meat");
                List<String> words = model.meatWords(id);
                for (int w = 0; w < words.size(); w++) {
                    sb.append(' ').append(words.get(w));
                }
                sb.append('\n');
                named = true;
            }
        }
        if (!named) {
            sb.append("- none\n");
        }
        sb.append("fallen:\n- none\n");
        sb.append("quiet:\n");
        boolean quiet = false;
        if (model != null) {
            List<String> ids = model.intentIds();
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                if (model.quiet(id)) {
                    sb.append("- ").append(id).append('\n');
                    quiet = true;
                }
            }
        }
        if (!quiet) {
            sb.append("- none\n");
        }
        sb.append("constraint:\n");
        boolean held = false;
        if (model != null) {
            List<String> ids = model.intentIds();
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                if (model.pinned(id)) {
                    sb.append("- pin ").append(id).append('\n');
                    held = true;
                }
                if (model.excluded(id)) {
                    sb.append("- exclude ").append(id).append('\n');
                    held = true;
                }
                if (model.rejected(id)) {
                    sb.append("- reject ").append(id).append('\n');
                    held = true;
                }
            }
        }
        if (!held) {
            sb.append("- none\n");
        }
        sb.append("glue: and a the then\n");
        sb.append("mention-words: it that this\n");
        sb.append("acts: born reinforced revised retired\n");
        sb.append("relations: supports refines contains pulls against\n");
        sb.append("meat-count: every word on that aim. no cap\n");
        sb.append("relation-line: one aim, one relation, one later aim. a shared meat word is written on each aim\n");
        sb.append("shift-words: added dropped changed\n");
        sb.append("aim: one, two, or three words. two or three words sit in brackets\n");
        sb.append("forbid: conversation-map intent-pass intent-ask intent-repair\n");
        sb.append("several: name each distinct aim this window holds\n");
        sb.append("copy: a shape in the system text is not an aim unless this window says that word\n");
        sb.append("reply-with: an intent-pass block\n");
        return sb.toString();
    }

    /**
     * The method builds the one repair.
     * The original ask follows the fault. The first reply's talk does not.
     *
     * @param problem The problem is the fault name.
     * @param brief The brief is the ask that faulted.
     * @return The return value is the repair brief.
     */
    public static String repair(String problem, String brief) {
        String rule = ruleFor(problem);
        String ask = brief == null ? "" : brief;
        return "intent-repair: 1\nfault: " + problem + "\nrule: " + rule + "\n" + ask;
    }

    /**
     * The method checks one reply.
     * A null return is a passing block.
     *
     * @param model The model is the graph already accepted.
     * @param known The known list is every turn in the conversation.
     * @param window The window is this step.
     * @param opening The opening is fresh, import, or turn.
     * @param reply The reply is the model text.
     * @return The return value is the fault, or null when the block passes.
     */
    public static String fault(GraphModel model, List<GraphTurn> known, List<GraphTurn> window,
            String opening, String reply) {
        Pass pass = read(reply);
        if (pass == null) {
            return "missing block";
        }
        if (pass.color) {
            return "color";
        }
        if (!pass.sawAct) {
            return "missing act";
        }
        if (!pass.sawShift) {
            return "missing shift";
        }
        if (pass.badLine) {
            return "line";
        }
        if (forbiddenAim(pass)) {
            return "name";
        }
        if (pass.badShift) {
            return "shift";
        }
        if ("fresh".equals(opening)) {
            if (hasShift(pass, "dropped") || hasWordDrop(pass)) {
                return "dropped on fresh";
            }
            if (hasShift(pass, "changed")) {
                return "changed on fresh";
            }
        }
        String paired = pairs(model, pass);
        if (paired != null) {
            return paired;
        }
        String cited = cites(pass, known, window);
        if (cited != null) {
            return cited;
        }
        return echo(model, pass);
    }

    /**
     * The method applies a passing block.
     * A cell line does not create an intent.
     * A glue word on a meat line is dropped.
     * A meat word already tethered to another aim links those aims.
     * There is no cap on the meat lines one aim holds.
     *
     * @param model The model receives the block.
     * @param known The known list maps a protocol label to a vault card.
     * @param window The window is this step.
     * @param reply The reply is the passing text.
     * @param feeds The feeds receive card and node pairs.
     */
    public static void apply(GraphModel model, List<GraphTurn> known, List<GraphTurn> window,
            String reply, List<String[]> feeds) {
        Pass pass = read(reply);
        if (pass == null || model == null) {
            return;
        }
        for (int i = 0; i < pass.acts.size(); i++) {
            String[] act = pass.acts.get(i);
            if (!"born".equals(act[1])) {
                continue;
            }
            if (act[0].length() > 64 || model.hasIntent(act[0]) || forbidden(act[0])) {
                continue;
            }
            if (alreadyHeld(model, pass, act[0])) {
                continue;
            }
            model.addIntent(act[0]);
            String mark = act[2];
            if (!"stated".equals(mark)) {
                model.setMark(act[0], "inferred");
            }
        }
        for (int i = 0; i < pass.acts.size(); i++) {
            String[] act = pass.acts.get(i);
            if (!model.hasIntent(act[0])) {
                continue;
            }
            if ("revised".equals(act[1]) && ("stated".equals(act[2]) || "inferred".equals(act[2]))) {
                model.setMark(act[0], act[2]);
            }
            if ("retired".equals(act[1])) {
                model.setQuiet(act[0], true);
            }
        }
        for (int i = 0; i < pass.referents.size(); i++) {
            String[] row = pass.referents.get(i);
            String intent = model.tether(row[1]);
            if (intent == null) {
                continue;
            }
            String ref = model.referentOf(row[1]);
            if (ref == null) {
                ref = referentId(intent, row[1]);
                model.referent(ref, intent, row[1]);
            }
            model.referent(ref, intent, row[0]);
        }
        for (int i = 0; i < pass.meats.size(); i++) {
            String[] row = pass.meats.get(i);
            if (glue(row[0]) || row[1].length() > 64 || !model.hasIntent(row[1])) {
                continue;
            }
            String prior = model.tether(row[0]);
            if (prior != null && !prior.equals(row[1])) {
                model.linkWord(row[0], prior, row[1]);
            }
            String ref = model.referentOf(row[0]);
            if (ref == null) {
                ref = referentId(row[1], row[0]);
            }
            model.referent(ref, row[1], row[0]);
        }
        for (int i = 0; i < pass.mentions.size(); i++) {
            String[] row = pass.mentions.get(i);
            String intent = model.tether(row[1]);
            if (intent != null) {
                model.takeWords(intent, row[1] + " " + row[0]);
            }
        }
        for (int i = 0; i < pass.relations.size(); i++) {
            String[] row = pass.relations.get(i);
            String[] targets = new String[row.length - 2];
            for (int t = 2; t < row.length; t++) {
                targets[t - 2] = row[t];
            }
            model.relate(row[1], row[0], targets);
        }
        String card = window == null || window.isEmpty() ? "" : window.get(window.size() - 1).card();
        for (int i = 0; i < pass.evidence.size(); i++) {
            String[] row = pass.evidence.get(i);
            String cited = row.length > 2 ? cardOf(known, row[2]) : card;
            if (cited == null || cited.length() == 0) {
                cited = card;
            }
            if (model.hasIntent(row[0])) {
                model.evidence(row[0], cited, row[1]);
            }
        }
        for (int i = 0; i < pass.quiet.size(); i++) {
            model.setQuiet(pass.quiet.get(i), true);
        }
        for (int i = 0; i < pass.wordDrops.size(); i++) {
            String[] row = pass.wordDrops.get(i);
            detach(model, row[1], row[0]);
        }
        if (feeds == null) {
            return;
        }
        for (int i = 0; i < pass.cells.size(); i++) {
            String[] row = pass.cells.get(i);
            String id = cardOf(known, row[0]);
            if (id != null && model.hasIntent(row[1])) {
                feeds.add(new String[] {id, row[1]});
            }
        }
    }

    private static boolean alreadyHeld(GraphModel model, Pass pass, String intent) {
        boolean any = false;
        for (int i = 0; i < pass.meats.size(); i++) {
            String[] row = pass.meats.get(i);
            if (!intent.equals(row[1]) || glue(row[0])) {
                continue;
            }
            any = true;
            if (model.referentOf(row[0]) == null) {
                return false;
            }
        }
        return any;
    }

    private static void detach(GraphModel model, String intent, String word) {
        if (!model.hasIntent(intent)) {
            return;
        }
        List<String> words = model.meatWords(intent);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equals(word)) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(words.get(i));
        }
        model.update(intent, sb.toString());
    }

    private static String referentId(String intent, String word) {
        String key = intent + "\n" + word;
        return UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8)).toString();
    }

    private static String echo(GraphModel model, Pass pass) {
        if (model == null) {
            return null;
        }
        List<String> ids = model.intentIds();
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            if (model.pinned(id) && !kept(pass, "pin", id)) {
                return "missing echo";
            }
            if (model.excluded(id) && !kept(pass, "exclude", id)) {
                return "missing echo";
            }
            if (model.rejected(id) && !kept(pass, "reject", id)) {
                return "missing echo";
            }
            if (model.pinned(id) && hasIntentShift(pass, id, "dropped")) {
                return "pinned drop";
            }
        }
        return null;
    }

    private static boolean kept(Pass pass, String act, String id) {
        for (int i = 0; i < pass.kept.size(); i++) {
            String[] row = pass.kept.get(i);
            if (act.equals(row[0]) && id.equals(row[1])) {
                return true;
            }
        }
        return false;
    }

    private static String cites(Pass pass, List<GraphTurn> known, List<GraphTurn> window) {
        for (int i = 0; i < pass.cells.size(); i++) {
            if (cardOf(known, pass.cells.get(i)[0]) == null) {
                return "unknown card";
            }
        }
        for (int i = 0; i < pass.evidence.size(); i++) {
            String[] row = pass.evidence.get(i);
            if (row.length > 2 && cardOf(known, row[2]) == null) {
                return "unknown card";
            }
        }
        for (int i = 0; i < pass.acts.size(); i++) {
            String[] act = pass.acts.get(i);
            if (!"born".equals(act[1])) {
                continue;
            }
            if (!citesWindow(pass, act[0], window)) {
                return "birth card";
            }
        }
        return null;
    }

    private static boolean citesWindow(Pass pass, String intent, List<GraphTurn> window) {
        if (window == null || window.isEmpty()) {
            return false;
        }
        for (int i = 0; i < pass.cells.size(); i++) {
            String[] row = pass.cells.get(i);
            if (intent.equals(row[1]) && inLabels(window, row[0])) {
                return true;
            }
        }
        for (int i = 0; i < pass.evidence.size(); i++) {
            String[] row = pass.evidence.get(i);
            if (!intent.equals(row[0])) {
                continue;
            }
            if (row.length <= 2 || inLabels(window, row[2])) {
                return true;
            }
        }
        return false;
    }

    private static boolean inLabels(List<GraphTurn> window, String token) {
        for (int i = 0; i < window.size(); i++) {
            GraphTurn turn = window.get(i);
            if (turn.label().equals(token) || turn.card().equals(token)) {
                return true;
            }
        }
        return false;
    }

    private static String cardOf(List<GraphTurn> known, String token) {
        if (known == null || token == null) {
            return null;
        }
        for (int i = 0; i < known.size(); i++) {
            GraphTurn turn = known.get(i);
            if (turn != null && (token.equals(turn.label()) || token.equals(turn.card()))) {
                return turn.card();
            }
        }
        return null;
    }

    private static String pairs(GraphModel model, Pass pass) {
        for (int i = 0; i < pass.acts.size(); i++) {
            String[] act = pass.acts.get(i);
            if (act[0].length() > 64) {
                return "length";
            }
            if ("born".equals(act[1]) && !hasIntentShift(pass, act[0], "added")) {
                return "born";
            }
            if ("revised".equals(act[1]) && !hasIntentShift(pass, act[0], "changed")) {
                return "revised";
            }
            if ("retired".equals(act[1]) && !hasIntentShift(pass, act[0], "dropped")) {
                return "retired";
            }
            if ("reinforced".equals(act[1]) && (model == null || !model.hasIntent(act[0]))) {
                return "reinforced";
            }
            if ("reinforced".equals(act[1]) && hasIntentShift(pass, act[0], "added")) {
                return "reinforced";
            }
            if ("reinforced".equals(act[1]) && hasIntentShift(pass, act[0], "dropped")) {
                return "reinforced";
            }
            if ("reinforced".equals(act[1]) && hasIntentShift(pass, act[0], "changed")) {
                return "reinforced";
            }
        }
        for (int i = 0; i < pass.shifts.size(); i++) {
            String[] shift = pass.shifts.get(i);
            if (!"intent".equals(shift[0])) {
                continue;
            }
            if (shift[1].length() > 64) {
                return "length";
            }
            if ("added".equals(shift[2]) && !hasAct(pass, shift[1], "born")) {
                return "added";
            }
            if ("changed".equals(shift[2]) && !hasAct(pass, shift[1], "revised")) {
                return "changed";
            }
            if ("dropped".equals(shift[2]) && !hasAct(pass, shift[1], "retired")) {
                return "dropped";
            }
            if ("dropped".equals(shift[2]) && model != null && !model.hasIntent(shift[1]) && !hasAct(pass, shift[1], "born")) {
                return "dropped unknown";
            }
            if ("dropped".equals(shift[2]) && (model == null || !model.hasIntent(shift[1]))) {
                return "dropped unknown";
            }
        }
        return null;
    }

    private static boolean hasAct(Pass pass, String intent, String act) {
        for (int i = 0; i < pass.acts.size(); i++) {
            String[] row = pass.acts.get(i);
            if (intent.equals(row[0]) && act.equals(row[1])) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasIntentShift(Pass pass, String intent, String kind) {
        for (int i = 0; i < pass.shifts.size(); i++) {
            String[] row = pass.shifts.get(i);
            if ("intent".equals(row[0]) && intent.equals(row[1]) && kind.equals(row[2])) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasShift(Pass pass, String kind) {
        for (int i = 0; i < pass.shifts.size(); i++) {
            if (kind.equals(pass.shifts.get(i)[2]) && "intent".equals(pass.shifts.get(i)[0])) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasWordDrop(Pass pass) {
        return !pass.wordDrops.isEmpty();
    }

    private static Pass read(String reply) {
        if (reply == null) {
            return null;
        }
        String[] lines = reply.split("\n", -1);
        int start = -1;
        for (int i = 0; i < lines.length; i++) {
            String trimmed = lines[i].trim();
            if (trimmed.startsWith("intent-pass:")) {
                start = i;
                break;
            }
        }
        if (start < 0) {
            return null;
        }
        Pass pass = new Pass();
        String group = "";
        for (int i = start; i < lines.length; i++) {
            String trimmed = lines[i].trim();
            if (trimmed.startsWith("```")) {
                continue;
            }
            if (trimmed.length() == 0) {
                break;
            }
            if (trimmed.startsWith("- ")) {
                if (!takeList(pass, group, trimmed.substring(2).trim())) {
                    if ("shift".equals(group)) {
                        pass.badShift = true;
                    } else {
                        pass.badLine = true;
                    }
                }
                continue;
            }
            int colon = trimmed.indexOf(':');
            if (colon < 1) {
                break;
            }
            String key = trimmed.substring(0, colon).trim().toLowerCase(Locale.ROOT);
            String value = trimmed.substring(colon + 1).trim();
            if (!known(key)) {
                break;
            }
            if ("color".equals(key)) {
                pass.color = true;
                break;
            }
            if ("intent-pass".equals(key)) {
                pass.sawPass = "1".equals(value);
                if (!pass.sawPass) {
                    return null;
                }
                group = "";
                continue;
            }
            if ("none".equalsIgnoreCase(value)) {
                if ("act".equals(key)) {
                    pass.sawAct = true;
                    pass.actNone = true;
                }
                if ("shift".equals(key)) {
                    pass.sawShift = true;
                    pass.shiftNone = true;
                }
                group = "";
                continue;
            }
            if ("act".equals(key)) {
                pass.sawAct = true;
            }
            if ("shift".equals(key)) {
                pass.sawShift = true;
            }
            group = key;
        }
        if (!pass.sawPass) {
            return null;
        }
        return pass;
    }

    private static boolean takeList(Pass pass, String group, String line) {
        if ("none".equalsIgnoreCase(line)) {
            return true;
        }
        String[] parts = split(line);
        if ("act".equals(group)) {
            int verb = verbAt(parts, ACTS);
            if (verb <= 0 || verb + 1 < parts.length - 1) {
                return false;
            }
            String aim = joinRange(parts, 0, verb);
            if (!aimOk(aim)) {
                return false;
            }
            String mark = verb + 1 < parts.length ? parts[verb + 1].toLowerCase(Locale.ROOT) : "";
            pass.acts.add(new String[] {aim, parts[verb].toLowerCase(Locale.ROOT), mark});
            return true;
        }
        if ("shift".equals(group)) {
            int drop = verbAt(parts, new String[] {"dropped"});
            if (drop > 0 && drop + 3 < parts.length && "word".equalsIgnoreCase(parts[drop + 1])
                    && "on".equalsIgnoreCase(parts[drop + 2])) {
                String from = joinRange(parts, 0, drop);
                String on = joinRange(parts, drop + 3, parts.length);
                if (!aimOk(from) || !aimOk(on)) {
                    return false;
                }
                pass.wordDrops.add(new String[] {from, on});
                pass.sawShift = true;
                return true;
            }
            int verb = verbAt(parts, SHIFTS);
            if (verb <= 0 || verb != parts.length - 1) {
                return false;
            }
            String aim = joinRange(parts, 0, verb);
            if (!aimOk(aim)) {
                return false;
            }
            pass.shifts.add(new String[] {"intent", aim, parts[verb].toLowerCase(Locale.ROOT)});
            return true;
        }
        if ("meat".equals(group) && parts.length >= 3 && "on".equalsIgnoreCase(parts[1])) {
            if (parts[0].indexOf(' ') >= 0) {
                return false;
            }
            String aim = joinRange(parts, 2, parts.length);
            if (!aimOk(aim)) {
                return false;
            }
            pass.meats.add(new String[] {parts[0], aim});
            return true;
        }
        if ("cell".equals(group) && parts.length >= 2) {
            String aim = joinRange(parts, 1, parts.length);
            if (!aimOk(aim)) {
                return false;
            }
            pass.cells.add(new String[] {parts[0], aim});
            return true;
        }
        if ("evidence".equals(group)) {
            int sees = verbAt(parts, new String[] {"sees"});
            if (sees > 0 && sees + 1 < parts.length) {
                String aim = joinRange(parts, 0, sees);
                if (!aimOk(aim)) {
                    return false;
                }
                int rest = parts.length - (sees + 1);
                if (rest == 1) {
                    if (parts[sees + 1].indexOf(' ') >= 0) {
                        return false;
                    }
                    pass.evidence.add(new String[] {aim, parts[sees + 1]});
                    return true;
                }
                if (rest == 2 && parts[sees + 1].indexOf(' ') < 0) {
                    pass.evidence.add(new String[] {aim, parts[sees + 1], parts[sees + 2]});
                    return true;
                }
                return false;
            }
            if (parts.length == 2) {
                if (!aimOk(parts[0])) {
                    return false;
                }
                pass.evidence.add(new String[] {parts[0], parts[1]});
                return true;
            }
            return false;
        }
        if ("relation".equals(group)) {
            String[] row = relation(parts);
            if (row == null) {
                return false;
            }
            pass.relations.add(row);
            return true;
        }
        if ("referent".equals(group)) {
            int same = verbAt(parts, new String[] {"same"});
            if (same <= 0 || same + 1 >= parts.length) {
                return false;
            }
            String left = joinRange(parts, 0, same);
            String right = joinRange(parts, same + 1, parts.length);
            if (!aimOk(left) || !aimOk(right)) {
                return false;
            }
            pass.referents.add(new String[] {left, right});
            return true;
        }
        if ("mention".equals(group) && parts.length == 3 && "sees".equalsIgnoreCase(parts[1])) {
            pass.mentions.add(new String[] {parts[0], parts[2]});
            return true;
        }
        if ("constraint-kept".equals(group) && parts.length >= 2) {
            String aim = joinRange(parts, 1, parts.length);
            if (!aimOk(aim)) {
                return false;
            }
            pass.kept.add(new String[] {parts[0].toLowerCase(Locale.ROOT), aim});
            return true;
        }
        if ("quiet".equals(group) && parts.length >= 1) {
            String aim = joinRange(parts, 0, parts.length);
            if (!aimOk(aim)) {
                return false;
            }
            pass.quiet.add(aim);
            return true;
        }
        return false;
    }

    private static String[] relation(String[] parts) {
        if (parts == null || parts.length < 3) {
            return null;
        }
        int at = -1;
        int width = 1;
        String name = null;
        for (int i = 1; i < parts.length - 1; i++) {
            if ("pulls".equalsIgnoreCase(parts[i]) && i + 1 < parts.length - 1
                    && "against".equalsIgnoreCase(parts[i + 1])) {
                at = i;
                width = 2;
                name = "pulls against";
                break;
            }
            if (word(new String[] {"supports", "refines", "contains"}, parts[i]) && i < parts.length - 1) {
                at = i;
                width = 1;
                name = parts[i].toLowerCase(Locale.ROOT);
                break;
            }
        }
        if (at <= 0 || name == null || at + width >= parts.length) {
            return null;
        }
        String from = joinRange(parts, 0, at);
        if (!aimOk(from)) {
            return null;
        }
        int targets = parts.length - (at + width);
        String[] row = new String[targets + 2];
        row[0] = from;
        row[1] = name;
        for (int t = 0; t < targets; t++) {
            String aim = parts[at + width + t];
            if (!aimOk(aim)) {
                return null;
            }
            row[t + 2] = aim;
        }
        return row;
    }

    private static String join(String[] parts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(parts[i]);
        }
        return sb.toString();
    }

    private static String[] split(String line) {
        List<String> out = new ArrayList<String>();
        if (line == null) {
            return new String[0];
        }
        int i = 0;
        int n = line.length();
        while (i < n) {
            char c = line.charAt(i);
            if (c == ' ' || c == '\t') {
                i++;
                continue;
            }
            if (c == '[') {
                int close = line.indexOf(']', i + 1);
                if (close < 0) {
                    out.add(line.substring(i).trim());
                    break;
                }
                out.add(collapse(line.substring(i + 1, close)));
                i = close + 1;
                continue;
            }
            int j = i;
            while (j < n && line.charAt(j) != ' ' && line.charAt(j) != '\t') {
                j++;
            }
            out.add(line.substring(i, j));
            i = j;
        }
        return out.toArray(new String[out.size()]);
    }

    private static String collapse(String text) {
        StringBuilder sb = new StringBuilder();
        boolean gap = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ' || c == '\t') {
                gap = sb.length() > 0;
                continue;
            }
            if (gap) {
                sb.append(' ');
                gap = false;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String joinRange(String[] parts, int from, int to) {
        StringBuilder sb = new StringBuilder();
        for (int i = from; i < to && i < parts.length; i++) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(parts[i]);
        }
        return sb.toString();
    }

    private static String bracket(String id) {
        if (id != null && id.indexOf(' ') >= 0) {
            return "[" + id + "]";
        }
        return id;
    }

    private static boolean aimOk(String aim) {
        if (aim == null) {
            return false;
        }
        String text = aim.trim();
        if (text.length() == 0 || text.indexOf('[') >= 0 || text.indexOf(']') >= 0) {
            return false;
        }
        int words = 1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                words++;
                if (words > 3) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int verbAt(String[] parts, String[] verbs) {
        if (parts == null) {
            return -1;
        }
        for (int i = 0; i < parts.length; i++) {
            if (word(verbs, parts[i])) {
                return i;
            }
        }
        return -1;
    }

    private static boolean known(String key) {
        return "intent-pass".equals(key) || "turn".equals(key) || "mention".equals(key)
                || "referent".equals(key) || "meat".equals(key) || "relation".equals(key)
                || "evidence".equals(key) || "constraint-kept".equals(key) || "quiet".equals(key)
                || "cell".equals(key) || "act".equals(key) || "shift".equals(key);
    }

    private static boolean word(String[] words, String token) {
        if (token == null) {
            return false;
        }
        String lower = token.toLowerCase(Locale.ROOT);
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(lower)) {
                return true;
            }
        }
        return false;
    }

    private static boolean glue(String word) {
        return word(GLUE, word);
    }

    private static String markOf(GraphModel model, String id) {
        String mark = model.mark(id);
        if ("inferred".equals(mark)) {
            return "inferred";
        }
        return "stated";
    }

    private static boolean inWindow(List<GraphTurn> window, GraphTurn turn) {
        for (int i = 0; i < window.size(); i++) {
            if (window.get(i) == turn) {
                return true;
            }
        }
        return false;
    }

    private static int indexOf(List<GraphTurn> turns, GraphTurn turn) {
        for (int i = 0; i < turns.size(); i++) {
            if (turns.get(i) == turn) {
                return i;
            }
        }
        return -1;
    }

    /**
     * The method reports a graph that holds only the map phrase.
     * A real aim keeps the graph. An empty graph is not this case.
     *
     * @param model The model is the graph already restored.
     * @return The return value is true when every intent is forbidden.
     */
    public static boolean phraseGraph(GraphModel model) {
        if (model == null || model.intentCount() == 0) {
            return false;
        }
        List<String> ids = model.intentIds();
        for (int i = 0; i < ids.size(); i++) {
            if (!forbidden(ids.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean forbiddenAim(Pass pass) {
        for (int i = 0; i < pass.acts.size(); i++) {
            if (forbidden(pass.acts.get(i)[0])) {
                return true;
            }
        }
        for (int i = 0; i < pass.shifts.size(); i++) {
            String[] shift = pass.shifts.get(i);
            if (shift.length > 1 && forbidden(shift[1])) {
                return true;
            }
        }
        for (int i = 0; i < pass.meats.size(); i++) {
            String[] meat = pass.meats.get(i);
            if (meat.length > 1 && forbidden(meat[1])) {
                return true;
            }
        }
        for (int i = 0; i < pass.cells.size(); i++) {
            String[] cell = pass.cells.get(i);
            if (cell.length > 1 && forbidden(cell[1])) {
                return true;
            }
        }
        for (int i = 0; i < pass.evidence.size(); i++) {
            if (forbidden(pass.evidence.get(i)[0])) {
                return true;
            }
        }
        return false;
    }

    private static boolean forbidden(String id) {
        String name = flat(id);
        return "conversation map".equals(name)
                || "intent pass".equals(name)
                || "intent ask".equals(name)
                || "intent repair".equals(name);
    }

    private static String flat(String id) {
        if (id == null) {
            return "";
        }
        String lower = id.toLowerCase(Locale.ROOT);
        StringBuilder sb = new StringBuilder();
        boolean gap = false;
        for (int i = 0; i < lower.length(); i++) {
            char c = lower.charAt(i);
            if (c == '-' || c == ' ') {
                gap = sb.length() > 0;
                continue;
            }
            if (gap) {
                sb.append(' ');
                gap = false;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String ruleFor(String problem) {
        if ("missing act".equals(problem)) {
            return "name one act line or the line act none";
        }
        if ("missing shift".equals(problem)) {
            return "name one shift line or the line shift none";
        }
        if ("color".equals(problem)) {
            return "a color line is a fault";
        }
        if ("dropped on fresh".equals(problem)) {
            return "a fresh canvas has no dropped line";
        }
        if ("changed on fresh".equals(problem)) {
            return "a fresh canvas has no changed line";
        }
        if ("pinned drop".equals(problem)) {
            return "a pinned intent stays on the graph";
        }
        if ("unknown card".equals(problem)) {
            return "name a card the conversation already holds";
        }
        if ("birth card".equals(problem)) {
            return "a birth cites a card inside the window";
        }
        if ("missing echo".equals(problem)) {
            return "echo every stored user act";
        }
        if ("length".equals(problem)) {
            return "a name stays within 64 characters";
        }
        if ("line".equals(problem)) {
            return "a meat line is one word, the word on, and one aim. Write every meat line. There is no cap. An aim is at most three words. Two or three words sit in brackets";
        }
        if ("reinforced".equals(problem)) {
            return "a new intent is born and added. Reinforced names an intent the graph already holds";
        }
        if ("name".equals(problem)) {
            return "an aim is one to three words the window holds. conversation-map is not an aim. name each distinct aim";
        }
        if ("dropped unknown".equals(problem)) {
            return "a node the walk has not accepted cannot be dropped";
        }
        return "pair each act with its shift";
    }

    private static final class Pass {
        private boolean sawPass;
        private boolean sawAct;
        private boolean actNone;
        private boolean sawShift;
        private boolean shiftNone;
        private boolean badShift;
        private boolean badLine;
        private boolean color;
        private final List<String[]> acts = new ArrayList<String[]>();
        private final List<String[]> shifts = new ArrayList<String[]>();
        private final List<String[]> meats = new ArrayList<String[]>();
        private final List<String[]> relations = new ArrayList<String[]>();
        private final List<String[]> evidence = new ArrayList<String[]>();
        private final List<String[]> cells = new ArrayList<String[]>();
        private final List<String[]> kept = new ArrayList<String[]>();
        private final List<String[]> referents = new ArrayList<String[]>();
        private final List<String[]> mentions = new ArrayList<String[]>();
        private final List<String[]> wordDrops = new ArrayList<String[]>();
        private final List<String> quiet = new ArrayList<String>();
    }
}
