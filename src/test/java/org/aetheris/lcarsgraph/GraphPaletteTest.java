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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * A paint chooses each color once.
 * Forty uncolored intents still return.
 * The test does not open a window.
 *
 * @author clayl (2026-11-27)
 */
class GraphPaletteTest {

    /**
     * The first intent keeps the first ring color.
     * The seventh intent takes the first well color.
     * The fortieth intent returns on this call.
     */
    @Test
    void fortyIntentsChooseEachColorOnce() {
        GraphPane pane = GraphDesk.pane("palette", "conversation");
        GraphModel model = pane.model();
        for (int i = 0; i < 40; i++) {
            model.addIntent(String.format("n%02d", Integer.valueOf(i)));
        }
        GraphPicture picture = pane.picture();
        long started = System.nanoTime();
        Color first = picture.ink("n00");
        Color seventh = picture.ink("n06");
        Color last = picture.ink("n39");
        Color supports = picture.relationInk("supports");
        long elapsed = System.nanoTime() - started;
        assertEquals(new Color(0x6B93FF), first);
        assertEquals(new Color(0x73AEFF), seventh);
        assertEquals(last, picture.ink("n39"));
        assertEquals(new Color(0xC0DEFF), supports);
        assertTrue(elapsed < 1000000000L);
    }
}
