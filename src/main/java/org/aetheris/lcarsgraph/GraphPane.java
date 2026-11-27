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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.casserole.chat.ReasonSpinner;

/**
 * The graph is its own pane.
 * It opens on the force picture.
 * The bar ground keeps its hue.
 * Saturation and brightness are four fifths of the source.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1474 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
// @aa-sdd L40 aetheris-plan-f-308.001.md FINESSE:f-308
// @aa-sdd L40 aetheris-plan-f-309.001.md FINESSE:f-309
// @aa-sdd L40 aetheris-plan-f-311.001.md FINESSE:f-311
// @aa-sdd L40 aetheris-plan-f-313.001.md FINESSE:f-313
// @aa-sdd L40 aetheris-plan-f-320.001.md FINESSE:f-320
// @aa-sdd L40 aetheris-plan-f-321.001.md FINESSE:f-321
// @aa-sdd L40 aetheris-plan-f-331.001.md FINESSE:f-331
public final class GraphPane extends JPanel {

    public static final String LABEL = "INTENT SHAPE GRAPH";

    private final GraphModel model;
    private final LayeredLayout layered;
    private final ForceLayout force;
    private final RadialLayout radial;
    private final GraphPicture picture;
    private final GraphDivider divider;
    private final GraphInspector inspector;
    private final GraphStrip strip;
    private final JPanel north;
    private final ShareRow share;
    private final ReasonSpinner renderingSpin;
    private JComponent foot;
    private Runnable modelAsk;
    private Runnable reloadAsk;
    private final Map<String, String> palette = new LinkedHashMap<>();
    private final List<String> allowed = new ArrayList<>();
    private final List<String> words = new ArrayList<>();
    private int page = 12;
    private String active = "force";
    private String filter = "WHOLE";
    private String selected;
    private String focusId;
    private String heldLayout = "force";
    private int heldPage = 12;
    private int typePoints = 20;
    private int forceX;
    private int forceY;
    private boolean nineOpened;
    private volatile boolean placing;
    private volatile int placeWave;
    private volatile int waveMode;
    private final Map<String, List<String[]>> heldSeats = new LinkedHashMap<String, List<String[]>>();

    public GraphPane(GraphModel model) {
        super(new BorderLayout());
        this.model = model;
        this.layered = new LayeredLayout();
        this.force = new ForceLayout();
        this.radial = new RadialLayout();
        this.picture = new GraphPicture(this);
        this.divider = new GraphDivider(this);
        this.inspector = new GraphInspector(this);
        this.strip = new GraphStrip(this);
        this.renderingSpin = new ReasonSpinner("rendering");
        this.renderingSpin.setOpaque(false);
        setOpaque(true);
        setBackground(new Color(0x0C0C0C));
        this.north = new JPanel(new BorderLayout());
        north.setOpaque(true);
        north.setBackground(GraphStrip.deepen(new Color(0x3A78C2)));
        JPanel markSeat = new JPanel(new BorderLayout());
        markSeat.setOpaque(false);
        markSeat.add(new GraphMark(), BorderLayout.NORTH);
        north.add(strip, BorderLayout.CENTER);
        north.add(markSeat, BorderLayout.EAST);
        add(north, BorderLayout.NORTH);
        this.share = new ShareRow();
        add(share, BorderLayout.CENTER);
        placeActive();
        strip.sync();
    }

    public GraphModel model() {
        return model;
    }

    /**
     * The method places the picture again after a fill.
     */
    void settle() {
        placeActive();
    }

    /**
     * The method keeps the opener for the MODEL control.
     * A null opener leaves the control quiet.
     *
     * @param ask The ask opens Model Config for the graph walk.
     */
    public void hearModel(Runnable ask) {
        modelAsk = ask;
    }

    /**
     * The method keeps the opener for the Reload control.
     * A null opener leaves the control quiet.
     *
     * @param ask The ask clears this graph and infers the conversation again.
     */
    public void hearReload(Runnable ask) {
        reloadAsk = ask;
    }

    /**
     * The method shows the rendering spinner while a walk is in flight.
     * A call off the event thread posts itself and returns.
     *
     * @param on True shows the spinner. False hides it.
     */
    public void rendering(final boolean on) {
        if (!EventQueue.isDispatchThread()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    rendering(on);
                }
            });
            return;
        }
        if (on) {
            renderingSpin.startAt(System.currentTimeMillis());
        } else {
            renderingSpin.stopSpin();
        }
        if (renderingSpin.getParent() != null) {
            renderingSpin.getParent().revalidate();
            renderingSpin.getParent().repaint();
        }
        revalidate();
        repaint();
    }

    /**
     * The method returns the rendering spinner.
     * The host shows this spinner only while the graph tab is in front.
     * Another graph keeps its own spinner.
     * The host seats it to the right of the menu bar while the control bar is folded.
     * The host seats it on the lower button bar while the control bar is open.
     * The graph button bar does not hold it.
     *
     * @return The return value is that spinner.
     */
    public JComponent renderingCluster() {
        return renderingSpin;
    }

    /**
     * The method seats one band on the south of the graph pane.
     * The same band returns without a swap.
     * A null band clears the south.
     * A call off the event thread posts itself and returns.
     *
     * @param band The band is the thinking pane, or null.
     */
    public void foot(final JComponent band) {
        if (!EventQueue.isDispatchThread()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    foot(band);
                }
            });
            return;
        }
        if (band == foot) {
            return;
        }
        if (foot != null) {
            remove(foot);
        }
        foot = band;
        if (band != null) {
            add(band, BorderLayout.SOUTH);
        }
        revalidate();
        repaint();
    }

    /**
     * The method reports whether the rendering spinner is showing.
     *
     * @return The return value is true when the spinner is visible.
     */
    boolean renderingOn() {
        return renderingSpin.isVisible();
    }

    /**
     * The method reports the width reserved for the word and the clock.
     *
     * @return The return value is that width in pixels.
     */
    int renderingWide() {
        return renderingSpin.getPreferredSize().width;
    }

    /**
     * The method returns the band seated on the south.
     *
     * @return The return value is that band, or null.
     */
    JComponent footBand() {
        return foot;
    }

    /**
     * The method moves one node on the active layout.
     * An intent takes its meat words with it.
     *
     * @param id The id is an intent or a meat word.
     * @param dx The dx is the horizontal move.
     * @param dy The dy is the vertical move.
     */
    public void nudge(String id, int dx, int dy) {
        if (id == null) {
            return;
        }
        nudgeOne(id, dx, dy);
        if (model.hasIntent(id)) {
            List<String> meat = model.meatWords(id);
            for (int i = 0; i < meat.size(); i++) {
                nudgeOne(meat.get(i), dx, dy);
            }
        }
        picture.repaint();
    }

    /**
     * The method writes every painted id on the active layout.
     */
    public void keepSeats() {
        List<String> ids = paintedIds();
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            seatOn(active, id, layoutX(id), layoutY(id));
        }
        GraphDesk.keep(this);
    }

    /**
     * The method clears the three layouts and reads the active layout.
     */
    void recallCanvas() {
        layered.clearSeats();
        force.clearSeats();
        radial.clearSeats();
        runPlace();
        picture.repaint();
    }

    /**
     * The method keeps the seats the vault thread already read.
     * The picture thread applies this copy.
     * A null layout is ignored.
     *
     * @param layout The layout name is layered, force, or radial.
     * @param rows Each row is the painted id, the x, and the y.
     */
    void holdSeats(String layout, List<String[]> rows) {
        if (layout == null) {
            return;
        }
        List<String[]> copy = new ArrayList<String[]>();
        if (rows != null) {
            for (int i = 0; i < rows.size(); i++) {
                String[] row = rows.get(i);
                if (row == null || row.length < 3) {
                    continue;
                }
                copy.add(new String[] {row[0], row[1], row[2]});
            }
        }
        heldSeats.put(layout, copy);
    }

    /**
     * The method returns the seats already held for one layout.
     * The picture thread reads this copy.
     * A missing layout returns an empty list.
     *
     * @param layout The layout name is layered, force, or radial.
     * @return Each row is the painted id, the x, and the y.
     */
    List<String[]> heldSeats(String layout) {
        List<String[]> rows = heldSeats.get(layout);
        if (rows == null) {
            return new ArrayList<String[]>();
        }
        return rows;
    }

    /**
     * The method reports whether this layout's seats were read.
     *
     * @param layout The layout name is layered, force, or radial.
     * @return The return value is true when a read has landed.
     */
    boolean knowsSeats(String layout) {
        return layout != null && heldSeats.containsKey(layout);
    }

    /**
     * The method places one frozen generation.
     * The picture thread calls this method.
     * The live model stays unread.
     * Seats already held stay. A missing id takes a fresh place.
     *
     * @param frozen The snapshot the model thread handed over.
     */
    void landPicture(GraphModel frozen) {
        if (knowsSeats(active)) {
            applySeats(heldSeats(active));
        }
        GraphModel source = frozen == null ? model : frozen;
        if (coveredModel(source)) {
            return;
        }
        List<String[]> prior = seatedNow();
        placeFresh(source);
        applySeats(prior);
    }

    private boolean coveredModel(GraphModel source) {
        if (source == null) {
            return false;
        }
        List<String> ids = source.intentIds();
        if (ids.isEmpty()) {
            return false;
        }
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            if (!placedOn(active, id)) {
                return false;
            }
            List<String> meat = source.meatWords(id);
            for (int w = 0; w < meat.size(); w++) {
                if (!placedOn(active, meat.get(w))) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<String[]> seatedNow() {
        List<String[]> rows = new ArrayList<String[]>();
        List<String> ids;
        if ("layered".equals(active)) {
            ids = layered.seatedIds();
        } else if ("radial".equals(active)) {
            ids = radial.seatedIds();
        } else {
            ids = force.seatedIds();
        }
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            rows.add(new String[] {
                id,
                Integer.toString(layoutX(id)),
                Integer.toString(layoutY(id))
            });
        }
        return rows;
    }

    List<String> paintedIds() {
        List<String> ids = new ArrayList<String>();
        List<String> intents = model.intentIds();
        for (int i = 0; i < intents.size(); i++) {
            String id = intents.get(i);
            if (!ids.contains(id)) {
                ids.add(id);
            }
            List<String> meat = model.meatWords(id);
            for (int w = 0; w < meat.size(); w++) {
                String word = meat.get(w);
                if (!ids.contains(word)) {
                    ids.add(word);
                }
            }
        }
        return ids;
    }

    private void nudgeOne(String id, int dx, int dy) {
        if ("layered".equals(active)) {
            layered.nudge(id, dx, dy);
            return;
        }
        if ("radial".equals(active)) {
            radial.nudge(id, dx, dy);
            return;
        }
        force.nudge(id, dx, dy);
    }

    public int page() {
        return page;
    }

    public String label() {
        return LABEL;
    }

    public String layoutName() {
        return active;
    }

    public String filter() {
        return filter;
    }

    public String selected() {
        return selected;
    }

    public void select(String id) {
        selected = id;
        strip.sync();
    }

    /**
     * The method opens the closer picture on one intent.
     * The picture keeps that intent, its meat words, and a door intent.
     * A door intent draws alone, on a ring around the focused intent.
     * A drag in this picture stays on the close canvas.
     * The seats of the open layout stay.
     *
     * @param id The id is the intent under the double-click.
     */
    void focusIntent(String id) {
        if (id == null || !model.hasIntent(id)) {
            return;
        }
        if (focusId == null) {
            heldLayout = active;
            heldPage = pageFor(active);
            picture.holdFrame();
        }
        focusId = id;
        selected = id;
        page = heldPage;
        picture.frameFocus();
        strip.sync();
        picture.repaint();
    }

    /**
     * The method leaves the closer picture.
     * The zoomed-out page of the layout that was open before the double-click returns.
     */
    void releaseFocus() {
        if (focusId == null) {
            return;
        }
        int back = heldPage;
        focusId = null;
        picture.restoreFrame();
        showPage(back);
    }

    String focusId() {
        return focusId;
    }

    boolean focused() {
        return focusId != null;
    }

    /**
     * The method reports whether the closer picture draws this id.
     * An open picture draws every id.
     *
     * @param id The id is one intent or one meat word.
     * @return The return value is true when the picture draws it.
     */
    boolean shown(String id) {
        if (focusId == null) {
            return true;
        }
        if (id == null) {
            return false;
        }
        if (id.equals(focusId)) {
            return true;
        }
        if (model.hasIntent(id)) {
            return door(id);
        }
        return model.meatWords(focusId).contains(id);
    }

    private boolean door(String id) {
        String[] relations = new String[] {
            "supports", "refines", "contains", "pulls against"
        };
        for (int r = 0; r < relations.length; r++) {
            if (model.targets(focusId, relations[r]).contains(id)) {
                return true;
            }
            if (model.targets(id, relations[r]).contains(focusId)) {
                return true;
            }
        }
        List<String> words = model.meatWords(focusId);
        for (int i = 0; i < words.size(); i++) {
            if (model.wordEdges(words.get(i)).contains(id)) {
                return true;
            }
        }
        return false;
    }

    private static int pageFor(String layout) {
        if ("layered".equals(layout)) {
            return 11;
        }
        if ("radial".equals(layout)) {
            return 13;
        }
        return 12;
    }

    public void selectWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        if (!words.contains(word)) {
            words.add(word);
        }
        if (words.size() > 2) {
            words.remove(0);
        }
    }

    public List<String> selectedWords() {
        return new ArrayList<>(words);
    }

    public void allow(List<String> ids) {
        allowed.clear();
        if (ids == null) {
            return;
        }
        for (String id : ids) {
            if (id != null && !allowed.contains(id)) {
                allowed.add(id);
            }
        }
        inspector.repaint();
        picture.repaint();
    }

    public boolean allowed(String id) {
        return allowed.contains(id);
    }

    public boolean lost(String id) {
        if (!"REICHENBACH".equals(filter)) {
            return false;
        }
        return !allowed.contains(id);
    }

    public boolean opened(int number) {
        if (number == 9) {
            return nineOpened;
        }
        return page == number;
    }

    public void store(String group, String ink) {
        if (group == null || ink == null) {
            return;
        }
        palette.put(group, ink);
    }

    public String stored(String group) {
        return palette.get(group);
    }

    /**
     * The method returns the scroll pane around the node picture.
     * The bars show when the picture is larger than the view.
     *
     * @return The return value is that scroll pane.
     */
    public JScrollPane canvasScroll() {
        return share.pictureScroll;
    }

    /**
     * The method returns the scroll pane around the inspector.
     * The bars show when the list is larger than the view.
     *
     * @return The return value is that scroll pane.
     */
    public JScrollPane inspectorScroll() {
        return share.inspectorScroll;
    }

    public GraphPicture picture() {
        return picture;
    }

    public GraphStrip strip() {
        return strip;
    }

    public GraphDivider divider() {
        return divider;
    }

    public GraphInspector inspector() {
        return inspector;
    }

    /**
     * The method seats one point size on the graph text.
     * A size below 8 seats as 8.
     * The buttons, the zoom field, the node letters, the mark, the inspector, and the rendering word take that size.
     *
     * @param size The size is the master font size, in points.
     */
    public void typeSize(int size) {
        int seated = size < 8 ? 8 : size;
        typePoints = seated;
        strip.seatType(seated);
        picture.seatType(seated);
        inspector.seatType(seated);
        renderingSpin.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, seated));
        north.revalidate();
        revalidate();
        repaint();
    }

    /**
     * The method reports the seated point size.
     *
     * @return The return value is that size.
     */
    public int typePoints() {
        return typePoints;
    }

    public ForceLayout forceLayout() {
        return force;
    }

    public LayeredLayout layeredLayout() {
        return layered;
    }

    public RadialLayout radialLayout() {
        return radial;
    }

    public int layoutX(String id) {
        if ("layered".equals(active)) {
            return layered.x(id);
        }
        if ("radial".equals(active)) {
            return radial.x(id);
        }
        return force.x(id);
    }

    public int layoutY(String id) {
        if ("layered".equals(active)) {
            return layered.y(id);
        }
        if ("radial".equals(active)) {
            return radial.y(id);
        }
        return force.y(id);
    }

    public void showPage(int next) {
        if (next == 9 || next < 1 || next > 13) {
            return;
        }
        page = next;
        boolean layout = false;
        if (next == 11) {
            active = "layered";
            layout = true;
        } else if (next == 12) {
            active = "force";
            forceX = 0;
            forceY = 0;
            layout = true;
        } else if (next == 13) {
            active = "radial";
            layout = true;
        }
        if (layout) {
            picture.dropCluster();
            beginLayout(0);
            if (EventQueue.isDispatchThread()) {
                return;
            }
        }
        strip.sync();
        picture.revalidate();
        picture.repaint();
    }

    public void click(int x, int y) {
        if (page != 10) {
            return;
        }
        forceX = x;
        forceY = y;
        active = "force";
        page = 1;
        beginLayout(2);
        if (EventQueue.isDispatchThread()) {
            return;
        }
        strip.sync();
        picture.repaint();
    }

    public void onControl(String name) {
        boolean wrote = false;
        if ("WHOLE".equals(name)) {
            filter = "WHOLE";
        } else if ("REICHENBACH".equals(name)) {
            filter = "REICHENBACH";
        } else if ("MODEL".equals(name)) {
            if (modelAsk != null) {
                modelAsk.run();
            }
            strip.sync();
            return;
        } else if ("RELOAD".equals(name)) {
            if (reloadAsk != null) {
                reloadAsk.run();
            }
            strip.sync();
            return;
        } else if ("STOP".equals(name)) {
            GraphDesk.stop(this);
            strip.sync();
            return;
        } else if ("PALETTE".equals(name)) {
            strip.sync();
            return;
        } else if ("EVERYTHING".equals(name)) {
            showPage(10);
            return;
        } else if ("LAYERED".equals(name)) {
            showPage(11);
            return;
        } else if ("FORCE".equals(name)) {
            if (focusId != null) {
                releaseFocus();
                return;
            }
            showPage(12);
            return;
        } else if ("RADIAL".equals(name)) {
            showPage(13);
            return;
        } else if ("RESET LAYOUT".equals(name)) {
            if (focusId != null) {
                focusId = null;
                picture.dropFrame();
            }
            beginLayout(1);
            if (EventQueue.isDispatchThread()) {
                return;
            }
            picture.home();
            strip.sync();
            picture.revalidate();
            picture.repaint();
            inspector.repaint();
            return;
        } else if ("PIN".equals(name)) {
            if (selected == null || !model.hasIntent(selected)) {
                return;
            }
            model.pin(selected);
            wrote = true;
        } else if ("REJECT".equals(name)) {
            if (selected == null || !model.hasIntent(selected)) {
                return;
            }
            model.reject(selected);
            wrote = true;
        } else if ("SPLIT".equals(name)) {
            String word = lastWord();
            if (word == null) {
                return;
            }
            model.split(word);
            wrote = true;
        } else if ("MERGE".equals(name)) {
            if (words.size() < 2) {
                return;
            }
            model.merge(words.get(0), words.get(1));
            wrote = true;
        } else if ("PROMOTE".equals(name)) {
            String word = lastWord();
            if (word == null) {
                return;
            }
            model.promote(word);
            wrote = true;
        }
        if (wrote) {
            GraphDesk.written(model);
        }
        strip.sync();
        picture.repaint();
        inspector.repaint();
    }

    private String lastWord() {
        if (words.isEmpty()) {
            return null;
        }
        return words.get(words.size() - 1);
    }

    private String radialCenter() {
        if (selected != null && model.hasIntent(selected)) {
            return selected;
        }
        List<String> ids = model.intentIds();
        if (ids.isEmpty()) {
            return null;
        }
        return ids.get(0);
    }

    private void beginLayout(final int mode) {
        if (!EventQueue.isDispatchThread()) {
            runLayout(mode);
            return;
        }
        waveMode = mode;
        placeWave++;
        final int token = placeWave;
        GraphDesk.place(this, new Runnable() {
            @Override
            public void run() {
                if (token != placeWave) {
                    return;
                }
                runLayout(waveMode);
                final int seen = placeWave;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (seen != placeWave) {
                            return;
                        }
                        paintLayout(waveMode);
                    }
                });
            }
        });
    }

    private void runLayout(int mode) {
        if (mode == 1) {
            GraphDesk.drop(this, active);
            placeFresh();
            keepSeats();
            return;
        }
        if (mode == 2) {
            GraphDesk.drop(this, "force");
            placeFresh();
            return;
        }
        runPlace();
    }

    private void paintLayout(int mode) {
        if (mode == 1) {
            picture.home();
        }
        strip.sync();
        picture.revalidate();
        picture.repaint();
        if (mode == 1) {
            inspector.repaint();
        }
    }

    private void placeActive() {
        runPlace();
    }

    private void runPlace() {
        if (covered(active)) {
            return;
        }
        List<String[]> stored = GraphDesk.seats(this);
        if (!stored.isEmpty()) {
            applySeats(stored);
            if (covered(active)) {
                return;
            }
            placeFresh();
            applySeats(stored);
            return;
        }
        placeFresh();
    }

    private void clearActive() {
        if ("layered".equals(active)) {
            layered.clearSeats();
            return;
        }
        if ("radial".equals(active)) {
            radial.clearSeats();
            return;
        }
        force.clearSeats();
    }

    private void placeFresh() {
        placeFresh(model);
    }

    private void placeFresh(GraphModel source) {
        GraphModel use = source == null ? model : source;
        int points = typePoints;
        if ("layered".equals(active)) {
            layered.place(use, points);
            return;
        }
        if ("radial".equals(active)) {
            String center = selected;
            if (center == null || !use.hasIntent(center)) {
                List<String> ids = use.intentIds();
                center = ids.isEmpty() ? null : ids.get(0);
            }
            radial.place(use, center, points);
            return;
        }
        force.place(use, forceX, forceY, points);
    }

    private boolean covered(String layout) {
        List<String> ids = paintedIds();
        if (ids.isEmpty()) {
            return false;
        }
        for (int i = 0; i < ids.size(); i++) {
            if (!placedOn(layout, ids.get(i))) {
                return false;
            }
        }
        return true;
    }

    private void applySeats(List<String[]> stored) {
        for (int i = 0; i < stored.size(); i++) {
            String[] row = stored.get(i);
            if (row == null || row.length < 3 || row[0] == null) {
                continue;
            }
            seatOn(active, row[0], parseSeat(row[1]), parseSeat(row[2]));
        }
    }

    private static int parseSeat(String raw) {
        if (raw == null) {
            return 0;
        }
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private boolean placedOn(String layout, String id) {
        if ("layered".equals(layout)) {
            return layered.placed(id);
        }
        if ("radial".equals(layout)) {
            return radial.placed(id);
        }
        return force.placed(id);
    }

    private void seatOn(String layout, String id, int px, int py) {
        if ("layered".equals(layout)) {
            layered.seat(id, px, py);
            return;
        }
        if ("radial".equals(layout)) {
            radial.seat(id, px, py);
            return;
        }
        force.seat(id, px, py);
    }

    private final class GraphMark extends JComponent {

        @Override
        public Dimension getPreferredSize() {
            Font font = new Font(Font.SANS_SERIF, Font.PLAIN, typePoints);
            FontMetrics metrics = getFontMetrics(font);
            int wide = metrics.stringWidth(LABEL) + 16;
            int high = metrics.getHeight() + 8;
            if (wide < 176) {
                wide = 176;
            }
            if (high < 24) {
                high = 24;
            }
            return new Dimension(wide, high);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics;
            int width = Math.max(1, getWidth());
            int height = Math.max(1, getHeight());
            g.setPaint(new GradientPaint(0, 0, new Color(0x994444), 0, height, new Color(0x662E2E)));
            g.fillRoundRect(0, 0, width, height, 8, 8);
            g.setColor(new Color(0xFF6600));
            g.drawRoundRect(0, 0, width - 1, height - 1, 8, 8);
            g.setColor(new Color(0xE0F4FF));
            Font font = new Font(Font.SANS_SERIF, Font.PLAIN, typePoints);
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);
            int baseline = (height + metrics.getAscent() - metrics.getDescent()) / 2;
            g.drawString(LABEL, 8, baseline);
        }
    }

    private final class ShareRow extends JPanel {

        private final JScrollPane pictureScroll;
        private final JScrollPane inspectorScroll;

        private ShareRow() {
            setOpaque(false);
            setLayout(null);
            pictureScroll = new JScrollPane(picture);
            pictureScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            pictureScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            pictureScroll.setBorder(null);
            pictureScroll.setOpaque(false);
            pictureScroll.getViewport().setOpaque(true);
            pictureScroll.getViewport().setBackground(new Color(0x0C0C0C));
            inspectorScroll = new JScrollPane(inspector);
            inspectorScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            inspectorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            inspectorScroll.setBorder(null);
            inspectorScroll.setFocusable(true);
            inspectorScroll.setRequestFocusEnabled(true);
            inspectorScroll.setOpaque(false);
            inspectorScroll.getViewport().setOpaque(true);
            inspectorScroll.getViewport().setBackground(new Color(0x2A1B42));
            inspector.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent event) {
                    inspectorScroll.requestFocusInWindow();
                }
            });
            add(pictureScroll);
            add(divider);
            add(inspectorScroll);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(640, 420);
        }

        @Override
        public void doLayout() {
            int span = getWidth();
            int height = getHeight();
            int pictureWidth = divider.pictureEdge(span);
            int bar = GraphDivider.BREADTH;
            if (bar > span) {
                bar = span;
            }
            int inspectorWidth = span - pictureWidth - bar;
            if (inspectorWidth < 0) {
                inspectorWidth = 0;
            }
            pictureScroll.setBounds(0, 0, pictureWidth, height);
            divider.setBounds(pictureWidth, 0, bar, height);
            inspectorScroll.setBounds(pictureWidth + bar, 0, inspectorWidth, height);
        }
    }
}
