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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.aetheris.wire.AmwpToon;
import org.junit.jupiter.api.Test;

/**
 * The class checks the inspector facts line and the snapped control row on {@link AetherisNotebookPanel}.
 * The test does not open a window.
 *
 * @author clayl (2026-10-28)
 */
class InspectorHeaderTest {

    /**
     * The method arranges a null facts object and a new {@link AmwpToon.SessionFacts}.
     * The method calls {@link AetherisNotebookPanel#factsLine}.
     * Both calls return {@code init: not reported}.
     *
     * @return The method returns nothing.
     */
    @Test
    void anUnreportedInitStaysOneLine() {
        assertEquals("init: not reported", AetherisNotebookPanel.factsLine(null));
        assertEquals("init: not reported", AetherisNotebookPanel.factsLine(new AmwpToon.SessionFacts()));
    }

    /**
     * The method arranges an {@link AmwpToon.SessionFacts} with {@code reported} true, air-gap {@code true}, and context {@code 131072}.
     * The method calls {@link AetherisNotebookPanel#factsLine}.
     * The line is {@code air-gap: true  num-ctx: 131072}.
     *
     * @return The method returns nothing.
     */
    @Test
    void reportedFactsSitOnOneLine() {
        AmwpToon.SessionFacts facts = new AmwpToon.SessionFacts();
        facts.reported = true;
        facts.airGap = "true";
        facts.numCtxPresent = true;
        facts.numCtx = 131072;
        assertEquals("air-gap: true  num-ctx: 131072", AetherisNotebookPanel.factsLine(facts));
    }

    /**
     * The method arranges reported facts with air-gap {@code false} and no context value.
     * The method calls {@link AetherisNotebookPanel#factsLine}.
     * The line is {@code air-gap: false  num-ctx: not reported}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aMissingWindowSaysNotReported() {
        AmwpToon.SessionFacts facts = new AmwpToon.SessionFacts();
        facts.reported = true;
        facts.airGap = "false";
        facts.numCtxPresent = false;
        assertEquals("air-gap: false  num-ctx: not reported", AetherisNotebookPanel.factsLine(facts));
    }

    /**
     * The method arranges a monospaced plain font at {@code 16} points and the line {@code air-gap: true  num-ctx: 131072}.
     * The method calls {@link AetherisNotebookPanel#factsPointSize}.
     * A width of {@code 4000} keeps the size {@code 16}.
     * A width of {@code 80} returns a size below {@code 16} and at least {@code 8}.
     * When that size is above {@code 8}, the fitted font width is at most {@code 80}.
     * When that size is below {@code 16}, one point larger is wider than {@code 80}.
     * A width of {@code 1} returns {@code 8}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theFactsFontShrinksToTheColumn() {
        Font base = new Font(Font.MONOSPACED, Font.PLAIN, 16);
        String text = "air-gap: true  num-ctx: 131072";
        assertEquals(16, AetherisNotebookPanel.factsPointSize(base, text, 4000));
        int narrow = AetherisNotebookPanel.factsPointSize(base, text, 80);
        assertTrue(narrow < 16);
        assertTrue(narrow >= 8);
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        try {
            if (narrow > 8) {
                FontMetrics fitted = graphics.getFontMetrics(base.deriveFont((float) narrow));
                assertTrue(fitted.stringWidth(text) <= 80);
            }
            if (narrow < 16) {
                FontMetrics bigger = graphics.getFontMetrics(base.deriveFont((float) (narrow + 1)));
                assertTrue(bigger.stringWidth(text) > 80);
            }
        } finally {
            graphics.dispose();
        }
        assertEquals(8, AetherisNotebookPanel.factsPointSize(base, text, 1));
    }

    /**
     * The method arranges control widths {@code 40}, {@code 90}, and {@code 90}, each with height {@code 18}.
     * The method calls {@link AetherisNotebookPanel#snappedRowHeight} with gap {@code 8} and inset {@code 4}.
     * A row width of {@code 400} returns {@code 18}.
     * A row width of {@code 120} returns {@code 62}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aNarrowRowKeepsEveryControl() {
        int[] widths = new int[] {40, 90, 90};
        int[] heights = new int[] {18, 18, 18};
        assertEquals(18, AetherisNotebookPanel.snappedRowHeight(400, 8, 4, widths, heights));
        assertEquals(62, AetherisNotebookPanel.snappedRowHeight(120, 8, 4, widths, heights));
    }
}
