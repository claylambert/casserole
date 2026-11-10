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

package org.aetheris.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.aetheris.swing.AetherisNotebookPanel.TokenModel;
import org.aetheris.swing.AetherisNotebookPanel.TokenRing;
import org.aetheris.swing.GrokSettingsDocument;
import org.junit.jupiter.api.Test;

// @aa-sdd PK-0224 PK-0343 docs/specs/java/org/aetheris/swing/swing.spec.md
/**
 * The class checks the token window under the inspector in {@link AetherisNotebookPanel}.
 * The test does not open a window.
 *
 * @author clayl (2026-10-29)
 */
class TokenRingTest {

    /**
     * The method arranges no objects.
     * The method calls {@link AetherisNotebookPanel#tokenLine}.
     * {@code 0} returns {@code est. tokens: 0}.
     * {@code -4} returns {@code est. tokens: 0}.
     * {@code 42} returns {@code est. tokens: 42}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theEstimateSitsOnItsOwnLine() {
        assertEquals("est. tokens: 0", AetherisNotebookPanel.tokenLine(0));
        assertEquals("est. tokens: 0", AetherisNotebookPanel.tokenLine(-4));
        assertEquals("est. tokens: 42", AetherisNotebookPanel.tokenLine(42));
    }

    /**
     * The method arranges no objects and calls the ring math on {@link AetherisNotebookPanel}.
     * {@link AetherisNotebookPanel#tokenFill} returns {@code 0} for ten tokens against a zero window and for zero tokens against {@code 1000}.
     * Fill is {@code 0.5} for {@code 500} of {@code 1000}, and {@code 1} for {@code 1500} of {@code 1000}.
     * {@link AetherisNotebookPanel#ringSweep} returns {@code 0} for a zero count, {@code 180} for half of {@code 1000}, and {@code 360} at the cap and past the cap.
     * {@link AetherisNotebookPanel#tokensLeft} returns {@code 130952} for {@code 120} of {@code 131072}, {@code 0} when the count is past {@code 1000}, and {@code -1} when the window is {@code 0}.
     * {@link AetherisNotebookPanel#tokensOver} returns {@code 0} inside {@code 131072} and {@code 400} for {@code 1400} of {@code 1000}.
     * {@link AetherisNotebookPanel#percentUsed} returns {@code 0}, {@code 50}, {@code 140}, and {@code -1} for those same edges.
     * {@link AetherisNotebookPanel#percentFree} returns {@code 50} at half, {@code 0} when past the cap, and {@code -1} when the window is {@code 0}.
     * {@link AetherisNotebookPanel#ringInk} for a known window is {@code 0x3cb371} at half, {@code 0xf0a202} at three quarters, and {@code 0xe53935} at {@code 0.95}.
     * An unknown window at half is {@code 0x2a3440}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theRingMeasuresTheRoom() {
        assertEquals(0d, AetherisNotebookPanel.tokenFill(10, 0), 0.0001d);
        assertEquals(0d, AetherisNotebookPanel.tokenFill(0, 1000), 0.0001d);
        assertEquals(0.5d, AetherisNotebookPanel.tokenFill(500, 1000), 0.0001d);
        assertEquals(1d, AetherisNotebookPanel.tokenFill(1500, 1000), 0.0001d);
        assertEquals(0, AetherisNotebookPanel.ringSweep(0, 131072));
        assertEquals(180, AetherisNotebookPanel.ringSweep(500, 1000));
        assertEquals(360, AetherisNotebookPanel.ringSweep(1000, 1000));
        assertEquals(360, AetherisNotebookPanel.ringSweep(1400, 1000));
        assertEquals(130952, AetherisNotebookPanel.tokensLeft(120, 131072));
        assertEquals(0, AetherisNotebookPanel.tokensLeft(1400, 1000));
        assertEquals(-1, AetherisNotebookPanel.tokensLeft(10, 0));
        assertEquals(0, AetherisNotebookPanel.tokensOver(120, 131072));
        assertEquals(400, AetherisNotebookPanel.tokensOver(1400, 1000));
        assertEquals(0, AetherisNotebookPanel.percentUsed(0, 1000));
        assertEquals(50, AetherisNotebookPanel.percentUsed(500, 1000));
        assertEquals(140, AetherisNotebookPanel.percentUsed(1400, 1000));
        assertEquals(-1, AetherisNotebookPanel.percentUsed(10, 0));
        assertEquals(50, AetherisNotebookPanel.percentFree(500, 1000));
        assertEquals(0, AetherisNotebookPanel.percentFree(1400, 1000));
        assertEquals(-1, AetherisNotebookPanel.percentFree(10, 0));
        assertEquals(0x3cb371, AetherisNotebookPanel.ringInk(0.5d, true).getRGB() & 0xffffff);
        assertEquals(0xf0a202, AetherisNotebookPanel.ringInk(0.75d, true).getRGB() & 0xffffff);
        assertEquals(0xe53935, AetherisNotebookPanel.ringInk(0.95d, true).getRGB() & 0xffffff);
        assertEquals(0x2a3440, AetherisNotebookPanel.ringInk(0.5d, false).getRGB() & 0xffffff);
    }

    /**
     * The method arranges targets {@code ollama/qwen2.5-coder:7b}, a blank target, {@code grok/grok-4.3}, and {@code pool}.
     * The method calls {@link AetherisNotebookPanel#tokenModels}, {@link AetherisNotebookPanel#tokenModelIndex}, and {@link AetherisNotebookPanel#selectedHeatBudget}.
     * The first label is {@code Ollama session} with a known window of {@code 131072} and an unknown completion cap.
     * {@code ollama/qwen2.5-coder:7b} appears once, after that session row, with window {@code 131072}.
     * {@code grok/grok-4.3} has an unknown window, a known completion cap of {@code 64000}, and that index from {@link AetherisNotebookPanel#tokenModelIndex}.
     * {@code grok/grok-4.7} uses the completion cap from a new {@link GrokSettingsDocument.Entry}.
     * {@code pool} appears once with an unknown window and an unknown completion cap.
     * An empty selection index is {@code 0}.
     * The heat budget is {@code 0} for {@code grok/grok-4.3}, {@code 131072} for the ollama target and for an empty selection, and {@code 0} for {@code pool}.
     *
     * A named grok window of {@code 1000000} and a pool window of {@code 256000} change those heat budgets.
     * The grok selection then budgets {@code 1000000}.
     * The pool selection then budgets {@code 256000}.
     * An empty selection still budgets {@code 131072}.
     * A call with the session flag false leaves the first window unknown.
     * That quiet list still contains {@code grok/grok-4.3}.
     * Its completion cap matches a new {@link GrokSettingsDocument.Entry}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theModelsComeFromTheConfig() {
        String[] targets = new String[] {
            "ollama/qwen2.5-coder:7b",
            "ollama/qwen2.5-coder:7b",
            "",
            "grok/grok-4.3",
            "pool"
        };
        ArrayList<TokenModel> rows = AetherisNotebookPanel.tokenModels(
                true, 131072, targets, "grok/grok-4.3", 64000, 0, null);
        assertEquals("Ollama session", rows.get(0).label);
        assertTrue(rows.get(0).windowKnown);
        assertEquals(131072, rows.get(0).window);
        assertFalse(rows.get(0).completionKnown);
        int ollama = indexOf(rows, "ollama/qwen2.5-coder:7b");
        assertTrue(ollama > 0);
        assertEquals(131072, rows.get(ollama).window);
        assertEquals(1, countOf(rows, "ollama/qwen2.5-coder:7b"));
        int focused = indexOf(rows, "grok/grok-4.3");
        assertTrue(focused > 0);
        assertFalse(rows.get(focused).windowKnown);
        assertTrue(rows.get(focused).completionKnown);
        assertEquals(64000, rows.get(focused).completionCap);
        int other = indexOf(rows, "grok/grok-4.7");
        assertEquals(new GrokSettingsDocument.Entry().maxCompletionTokens, rows.get(other).completionCap);
        assertEquals(1, countOf(rows, "pool"));
        assertFalse(rows.get(indexOf(rows, "pool")).windowKnown);
        assertFalse(rows.get(indexOf(rows, "pool")).completionKnown);
        assertEquals(focused, AetherisNotebookPanel.tokenModelIndex(rows, "grok/grok-4.3"));
        assertEquals(ollama, AetherisNotebookPanel.tokenModelIndex(rows, "ollama/qwen2.5-coder:7b"));
        assertEquals(indexOf(rows, "pool"), AetherisNotebookPanel.tokenModelIndex(rows, "pool"));
        assertEquals(0, AetherisNotebookPanel.tokenModelIndex(rows, ""));
        assertEquals(0, AetherisNotebookPanel.selectedHeatBudget(rows, "grok/grok-4.3"));
        assertEquals(131072, AetherisNotebookPanel.selectedHeatBudget(rows, "ollama/qwen2.5-coder:7b"));
        assertEquals(0, AetherisNotebookPanel.selectedHeatBudget(rows, "pool"));
        assertEquals(131072, AetherisNotebookPanel.selectedHeatBudget(rows, ""));
        Map<String, Integer> grokWindows = new LinkedHashMap<String, Integer>();
        grokWindows.put("grok-4.3", Integer.valueOf(1000000));
        ArrayList<TokenModel> named = AetherisNotebookPanel.tokenModels(
                true, 131072, targets, "grok/grok-4.3", 64000, 256000, grokWindows);
        assertEquals(1000000, AetherisNotebookPanel.selectedHeatBudget(named, "grok/grok-4.3"));
        assertEquals(256000, AetherisNotebookPanel.selectedHeatBudget(named, "pool"));
        assertEquals(131072, AetherisNotebookPanel.selectedHeatBudget(named, ""));
        ArrayList<TokenModel> quiet = AetherisNotebookPanel.tokenModels(false, 0, null, "", 0, 0, null);
        assertFalse(quiet.get(0).windowKnown);
        assertTrue(indexOf(quiet, "grok/grok-4.3") > 0);
        assertEquals(new GrokSettingsDocument.Entry().maxCompletionTokens,
                quiet.get(indexOf(quiet, "grok/grok-4.3")).completionCap);
    }

    /**
     * The method arranges models for {@code ollama/deepseek-r1:7b}, {@code grok/grok-4.3}, {@code pool}, and an unreported session.
     * The method calls {@link AetherisNotebookPanel#tokenStats}.
     * The open ollama text contains the estimate {@code 250}, {@code 1000} characters, {@code 3} blocks, that model, window {@code 1000}, remaining {@code 750}, used {@code 25%}, free {@code 75%}, and {@code source: session num-ctx}.
     * That text contains {@code This open does not ask the host.}
     * That text does not contain {@code over:}.
     * The past-cap text contains remaining {@code 0}, over {@code 400}, and free {@code 0%}.
     * The grok text contains {@code context window: not reported}, {@code source: no context window on the model config}, and {@code completion cap:}.
     * The pool text contains {@code source: pool has no context window here}.
     * The pool text does not contain {@code completion cap:}.
     * The unreported session contains {@code source: session num-ctx was not reported}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theStatsNameTheRoom() {
        ArrayList<TokenModel> rows = AetherisNotebookPanel.tokenModels(
                true, 1000, new String[] {"ollama/deepseek-r1:7b"}, "ollama/deepseek-r1:7b", 0, 0, null);
        TokenModel ollama = rows.get(indexOf(rows, "ollama/deepseek-r1:7b"));
        String open = AetherisNotebookPanel.tokenStats(250, 1000, 3, ollama);
        assertTrue(open.contains("est. tokens: 250\n"));
        assertTrue(open.contains("characters: 1000\n"));
        assertTrue(open.contains("blocks: 3\n"));
        assertTrue(open.contains("model: ollama/deepseek-r1:7b\n"));
        assertTrue(open.contains("context window: 1000\n"));
        assertTrue(open.contains("remaining: 750\n"));
        assertTrue(open.contains("used: 25%\n"));
        assertTrue(open.contains("free: 75%\n"));
        assertTrue(open.contains("source: session num-ctx\n"));
        assertTrue(open.contains("This open does not ask the host.\n"));
        assertFalse(open.contains("over:"));
        String past = AetherisNotebookPanel.tokenStats(1400, 5600, 8, ollama);
        assertTrue(past.contains("remaining: 0\n"));
        assertTrue(past.contains("over: 400\n"));
        assertTrue(past.contains("free: 0%\n"));
        TokenModel grok = rows.get(indexOf(rows, "grok/grok-4.3"));
        String cloud = AetherisNotebookPanel.tokenStats(80, 320, 1, grok);
        assertTrue(cloud.contains("context window: not reported\n"));
        assertTrue(cloud.contains("source: no context window on the model config\n"));
        assertTrue(cloud.contains("completion cap: "));
        TokenModel pool = rows.get(indexOf(rows, "pool"));
        String heavy = AetherisNotebookPanel.tokenStats(80, 320, 1, pool);
        assertTrue(heavy.contains("source: pool has no context window here\n"));
        assertFalse(heavy.contains("completion cap:"));
        TokenModel unreported = AetherisNotebookPanel.tokenModels(false, 0, null, "", 0, 0, null).get(0);
        String missing = AetherisNotebookPanel.tokenStats(4, 16, 1, unreported);
        assertTrue(missing.contains("source: session num-ctx was not reported\n"));
    }

    /**
     * The method arranges yaml context windows and calls {@link AetherisNotebookPanel#tokenBudgetsFromYaml}, {@link AetherisNotebookPanel#classpathTokenBudgets}, {@link AetherisNotebookPanel#tokenModels}, and {@link AetherisNotebookPanel#tokenStats}.
     * The parsed pool window is {@code 256000}.
     * The parsed grok map holds eight ids.
     * {@code grok-4.7} is {@code 500000}, {@code grok-4.3} is {@code 1000000}, and {@code grok-build-0.1} is {@code 256000}.
     * Broken yaml {@code nope: [} yields a pool window of {@code 0} and an empty grok map.
     * The classpath pool window is {@code 256000}.
     * The classpath window for {@code grok-4.7} is {@code 500000}.
     * The classpath window for {@code grok-4.6} is {@code 500000}.
     * The classpath window for {@code grok-4.5} is {@code 500000}.
     * The classpath window for {@code grok-4.3} is {@code 1000000}.
     * The classpath window for {@code grok-4.20-0309-reasoning} is {@code 1000000}.
     * The classpath window for {@code grok-4.20-0309-non-reasoning} is {@code 1000000}.
     * The classpath window for {@code grok-build-0.1} is {@code 256000}.
     * The classpath window for {@code grok-4.20-multi-agent-0309} is {@code 1000000}.
     * A focused {@code grok/grok-4.3} row knows window {@code 1000000} and completion cap {@code 64000}.
     * {@code grok/grok-4.7} knows window {@code 500000}.
     * {@code grok/missing-id} does not know a window.
     * {@code pool} knows window {@code 256000} and does not know a completion cap.
     * The focused stats contain window {@code 1000000}, {@code source: application.yaml}, and completion cap {@code 64000}.
     * Those stats do not contain {@code not reported}.
     * The pool stats contain window {@code 256000} and {@code source: application.yaml}.
     * The pool stats do not contain {@code completion cap:}.
     * The missing model contains {@code source: no context window on the model config}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theBudgetsComeFromYaml() {
        String yaml = ""
                + "casserole:\n"
                + "  grok:\n"
                + "    context-windows:\n"
                + "      grok-4.7: 500000\n"
                + "      grok-4.6: 500000\n"
                + "      grok-4.5: 500000\n"
                + "      grok-4.3: 1000000\n"
                + "      grok-4.20-0309-reasoning: 1000000\n"
                + "      grok-4.20-0309-non-reasoning: 1000000\n"
                + "      grok-build-0.1: 256000\n"
                + "      grok-4.20-multi-agent-0309: 1000000\n"
                + "  pool:\n"
                + "    context-window: 256000\n";
        AetherisNotebookPanel.TokenBudgets budgets = AetherisNotebookPanel.tokenBudgetsFromYaml(yaml);
        assertEquals(256000, budgets.poolWindow);
        assertEquals(8, budgets.grokWindows.size());
        assertEquals(500000, budgets.grokWindows.get("grok-4.7").intValue());
        assertEquals(1000000, budgets.grokWindows.get("grok-4.3").intValue());
        assertEquals(256000, budgets.grokWindows.get("grok-build-0.1").intValue());
        AetherisNotebookPanel.TokenBudgets blank = AetherisNotebookPanel.tokenBudgetsFromYaml("nope: [");
        assertEquals(0, blank.poolWindow);
        assertEquals(0, blank.grokWindows.size());
        AetherisNotebookPanel.TokenBudgets seated = AetherisNotebookPanel.classpathTokenBudgets();
        assertEquals(256000, seated.poolWindow);
        assertEquals(500000, seated.grokWindows.get("grok-4.7").intValue());
        assertEquals(500000, seated.grokWindows.get("grok-4.6").intValue());
        assertEquals(500000, seated.grokWindows.get("grok-4.5").intValue());
        assertEquals(1000000, seated.grokWindows.get("grok-4.3").intValue());
        assertEquals(1000000, seated.grokWindows.get("grok-4.20-0309-reasoning").intValue());
        assertEquals(1000000, seated.grokWindows.get("grok-4.20-0309-non-reasoning").intValue());
        assertEquals(256000, seated.grokWindows.get("grok-build-0.1").intValue());
        assertEquals(1000000, seated.grokWindows.get("grok-4.20-multi-agent-0309").intValue());
        Map<String, Integer> windows = new LinkedHashMap<String, Integer>();
        windows.put("grok-4.3", Integer.valueOf(1000000));
        windows.put("grok-4.7", Integer.valueOf(500000));
        String[] targets = new String[] {"grok/grok-4.3", "pool", "grok/missing-id"};
        ArrayList<TokenModel> rows = AetherisNotebookPanel.tokenModels(
                true, 131072, targets, "grok/grok-4.3", 64000, 256000, windows);
        TokenModel focused = rows.get(indexOf(rows, "grok/grok-4.3"));
        assertTrue(focused.windowKnown);
        assertEquals(1000000, focused.window);
        assertEquals(64000, focused.completionCap);
        TokenModel other = rows.get(indexOf(rows, "grok/grok-4.7"));
        assertTrue(other.windowKnown);
        assertEquals(500000, other.window);
        TokenModel missing = rows.get(indexOf(rows, "grok/missing-id"));
        assertFalse(missing.windowKnown);
        TokenModel pool = rows.get(indexOf(rows, "pool"));
        assertTrue(pool.windowKnown);
        assertEquals(256000, pool.window);
        assertFalse(pool.completionKnown);
        String cloud = AetherisNotebookPanel.tokenStats(80, 320, 1, focused);
        assertTrue(cloud.contains("context window: 1000000\n"));
        assertTrue(cloud.contains("source: application.yaml\n"));
        assertTrue(cloud.contains("completion cap: 64000\n"));
        assertFalse(cloud.contains("not reported"));
        String heavy = AetherisNotebookPanel.tokenStats(80, 320, 1, pool);
        assertTrue(heavy.contains("context window: 256000\n"));
        assertTrue(heavy.contains("source: application.yaml\n"));
        assertFalse(heavy.contains("completion cap:"));
        String absent = AetherisNotebookPanel.tokenStats(80, 320, 1, missing);
        assertTrue(absent.contains("source: no context window on the model config\n"));
    }

    /**
     * The method arranges three {@link TokenRing} components at {@code 240} pixels and paints each one.
     * An open ring at {@code 250} of {@code 1000} is greener at the top edge than at the half-turn edge.
     * The top green channel is more than {@code 40} above that half-turn sample.
     * That top green channel is above {@code 120}.
     * A full ring at {@code 1000} of {@code 1000} has a red channel more than {@code 40} above the green channel at the crown.
     * A quiet ring with an unknown window stays under green {@code 100} at the top edge.
     *
     * @return The method returns nothing.
     */
    @Test
    void theRingPaintsTheUsedArc() {
        int size = 240;
        TokenRing open = new TokenRing();
        open.setSize(size, size);
        open.show(250, 1000, true);
        BufferedImage image = paint(open, size);
        int top = greenNear(image, AetherisNotebookPanel.ringEdge(size, size, 0d));
        int bottom = greenNear(image, AetherisNotebookPanel.ringEdge(size, size, 0.5d));
        assertTrue(top > bottom + 40);
        assertTrue(top > 120);
        TokenRing full = new TokenRing();
        full.setSize(size, size);
        full.show(1000, 1000, true);
        BufferedImage red = paint(full, size);
        Point crown = AetherisNotebookPanel.ringEdge(size, size, 0d);
        int rgb = red.getRGB(crown.x, crown.y);
        int redChannel = (rgb >> 16) & 0xff;
        int greenChannel = (rgb >> 8) & 0xff;
        assertTrue(redChannel > greenChannel + 40);
        TokenRing quiet = new TokenRing();
        quiet.setSize(size, size);
        quiet.show(250, 0, false);
        BufferedImage bare = paint(quiet, size);
        assertTrue(greenNear(bare, AetherisNotebookPanel.ringEdge(size, size, 0d)) < 100);
    }

    /**
     * The method paints {@code ring} into a new image of the given size.
     * The image type is {@code TYPE_INT_RGB}.
     *
     * @param ring The ring is the component whose {@code paint} method runs.
     * @param size The size is both the width and the height in pixels.
     * @return The method returns the painted image.
     */
    private static BufferedImage paint(TokenRing ring, int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        try {
            ring.paint(graphics);
        } finally {
            graphics.dispose();
        }
        return image;
    }

    /**
     * The method reads the green channel in a seven-pixel square around {@code point}.
     * A pixel outside the image is skipped.
     *
     * @param image The image is the painted ring.
     * @param point The point is the center of the sample.
     * @return The method returns the highest green channel in that square.
     */
    private static int greenNear(BufferedImage image, Point point) {
        int best = 0;
        for (int dy = -3; dy <= 3; dy++) {
            for (int dx = -3; dx <= 3; dx++) {
                int x = point.x + dx;
                int y = point.y + dy;
                if (x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight()) {
                    continue;
                }
                int green = (image.getRGB(x, y) >> 8) & 0xff;
                if (green > best) {
                    best = green;
                }
            }
        }
        return best;
    }

    /**
     * The method finds the first row whose label equals {@code label}.
     *
     * @param rows The list is the model rows.
     * @param label The label is the text to match.
     * @return The method returns that index, or {@code -1} when no row matches.
     */
    private static int indexOf(ArrayList<TokenModel> rows, String label) {
        for (int i = 0; i < rows.size(); i++) {
            if (label.equals(rows.get(i).label)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * The method counts rows whose label equals {@code label}.
     *
     * @param rows The list is the model rows.
     * @param label The label is the text to match.
     * @return The method returns that count.
     */
    private static int countOf(ArrayList<TokenModel> rows, String label) {
        int n = 0;
        for (int i = 0; i < rows.size(); i++) {
            if (label.equals(rows.get(i).label)) {
                n = n + 1;
            }
        }
        return n;
    }
}
