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

package org.aetheris.ssh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.roux.RouxEngine;
import org.roux.model.Role;

// @aa-sdd aetheris-plan-f-283.001.md
// @aa-sdd aetheris-plan-f-284.001.md
/**
 * The class checks the llama slot weight correction.
 * The test does not open a window.
 * The test does not open a socket.
 *
 * @author clayl (2026-11-19)
 */
class LlamaSlotFitTest {

    /**
     * The method checks that a gap lands on the earliest included weights.
     * The included weights sum to the rendered count.
     * An omitted weight stays as it was.
     */
    @Test
    void aGapLandsOnTheEarliestLines() {
        int[] weights = new int[] { 2, 9, 2 };
        boolean[] include = new boolean[] { true, false, true };
        AmwpFacade.spreadGap(weights, include, 8);
        assertEquals(4, weights[0]);
        assertEquals(9, weights[1]);
        assertEquals(4, weights[2]);
    }

    /**
     * The method checks that a rendered count under the weight sum leaves the weights.
     * A null include array includes every index.
     * The remainder of a gap lands on the first indexes.
     */
    @Test
    void aShortCountStaysAndARemainderLeads() {
        int[] under = new int[] { 5, 5 };
        AmwpFacade.spreadGap(under, null, 4);
        assertEquals(5, under[0]);
        assertEquals(5, under[1]);
        int[] weights = new int[] { 1, 1, 1 };
        AmwpFacade.spreadGap(weights, null, 5);
        assertEquals(2, weights[0]);
        assertEquals(2, weights[1]);
        assertEquals(1, weights[2]);
    }

    /**
     * The method checks that one corrected walk keeps the pin and the live line inside the allowance.
     * The character guesses sit under the allowance.
     * The rendered total sits over it.
     * The corrected weights make that one walk drop the oldest tail.
     */
    @Test
    void oneCorrectedWalkFitsTheSlot() {
        int allowance = 30;
        int[] weights = new int[] { 4, 4, 4, 4, 4, 4 };
        AmwpFacade.spreadGap(weights, null, 60);
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum = sum + weights[i];
        }
        assertEquals(60, sum);
        long[] seqs = new long[] { 1L, 2L, 3L, 4L, 5L, 0L };
        byte[] roles = new byte[] { Role.SYSTEM, Role.USER, Role.ASSISTANT, Role.USER, Role.ASSISTANT, Role.USER };
        boolean[] lives = new boolean[] { false, false, false, false, false, true };
        RouxEngine.SinkCut cut = RouxEngine.selectSlot(allowance, seqs, roles, weights, null, null, lives);
        assertNotNull(cut);
        assertNotNull(cut.ordinals);
        assertTrue(cut.ordinals.length < weights.length);
        int kept = 0;
        boolean sawPin = false;
        boolean sawLive = false;
        for (int i = 0; i < cut.ordinals.length; i++) {
            int ordinal = cut.ordinals[i];
            kept = kept + weights[ordinal];
            if (ordinal == 0) {
                sawPin = true;
            }
            if (ordinal == 5) {
                sawLive = true;
            }
        }
        assertTrue(kept <= allowance);
        assertTrue(sawPin);
        assertTrue(sawLive);
    }

    /**
     * The method checks that a smaller cut walks again.
     * The first confirm is over the allowance.
     * The next walk keeps fewer pieces.
     * That cut holds still only when the count does not fall.
     */
    @Test
    void aShrinkingCutWalksAgain() {
        int allowance = 20;
        int[] weights = new int[] { 4, 6, 6, 6, 6, 2 };
        long[] seqs = new long[] { 1L, 2L, 3L, 4L, 5L, 0L };
        byte[] roles = new byte[] { Role.SYSTEM, Role.USER, Role.ASSISTANT, Role.USER, Role.ASSISTANT, Role.USER };
        boolean[] lives = new boolean[] { false, false, false, false, false, true };
        RouxEngine.SinkCut first = RouxEngine.selectSlot(allowance, seqs, roles, weights, null, null, lives);
        assertNotNull(first);
        assertNotNull(first.ordinals);
        assertEquals(4, first.ordinals.length);
        assertTrue(!AmwpFacade.cutHolds(-1, first.ordinals.length));
        boolean[] include = new boolean[] { false, false, false, false, false, false };
        for (int i = 0; i < first.ordinals.length; i++) {
            include[first.ordinals[i]] = true;
        }
        AmwpFacade.spreadGap(weights, include, 26);
        RouxEngine.SinkCut second = RouxEngine.selectSlot(allowance, seqs, roles, weights, null, null, lives);
        assertNotNull(second);
        assertNotNull(second.ordinals);
        assertTrue(second.ordinals.length < first.ordinals.length);
        assertTrue(!AmwpFacade.cutHolds(first.ordinals.length, second.ordinals.length));
        int kept = 0;
        for (int i = 0; i < second.ordinals.length; i++) {
            kept = kept + weights[second.ordinals[i]];
        }
        assertTrue(kept <= allowance);
    }

    /**
     * The method checks that a cut which holds the same count is the stop.
     * The pin and the live line stay.
     * A second walk of that same pair holds the count.
     */
    @Test
    void aCutThatHoldsStillIsLocked() {
        assertTrue(AmwpFacade.cutHolds(41, 41));
        assertTrue(!AmwpFacade.cutHolds(45, 41));
        int allowance = 15;
        int[] weights = new int[] { 10, 10 };
        long[] seqs = new long[] { 1L, 0L };
        byte[] roles = new byte[] { Role.SYSTEM, Role.USER };
        boolean[] lives = new boolean[] { false, true };
        RouxEngine.SinkCut first = RouxEngine.selectSlot(allowance, seqs, roles, weights, null, null, lives);
        assertNotNull(first);
        assertNotNull(first.ordinals);
        assertEquals(2, first.ordinals.length);
        AmwpFacade.spreadGap(weights, null, 22);
        RouxEngine.SinkCut second = RouxEngine.selectSlot(allowance, seqs, roles, weights, null, null, lives);
        assertNotNull(second);
        assertNotNull(second.ordinals);
        assertEquals(first.ordinals.length, second.ordinals.length);
        assertTrue(AmwpFacade.cutHolds(first.ordinals.length, second.ordinals.length));
    }
}
