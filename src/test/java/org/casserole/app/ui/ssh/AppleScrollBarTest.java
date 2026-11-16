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

package org.casserole.app.ui.ssh;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The class checks the thumb length in {@link AppleScrollBar}.
 * The handle is forty percent of the pane span.
 * The test does not open a window.
 *
 * @author clayl (2026-11-06)
 */
class AppleScrollBarTest {

    /**
     * The method arranges no objects.
     * The method calls {@link AppleScrollBar#thumbSpan} and reads {@link AppleScrollBar#THUMB_SHARE}.
     * A track of {@code 0} returns {@code 0}.
     * A track of {@code -8} returns {@code 0}.
     * A track of {@code 1} returns {@code 1}.
     * A track of {@code 2} returns {@code 1}.
     * A track of {@code 10} returns {@code 4}.
     * A track of {@code 100} returns {@code 40}.
     * A track of {@code 1000} returns {@code 400}.
     * {@link AppleScrollBar#THUMB_SHARE} equals {@code 0.40f}.
     *
     * @return The method returns nothing.
     */
    @Test
    void theHandleIsFortyPercentOfThePane() {
        assertEquals(0, AppleScrollBar.thumbSpan(0));
        assertEquals(0, AppleScrollBar.thumbSpan(-8));
        assertEquals(1, AppleScrollBar.thumbSpan(1));
        assertEquals(1, AppleScrollBar.thumbSpan(2));
        assertEquals(4, AppleScrollBar.thumbSpan(10));
        assertEquals(40, AppleScrollBar.thumbSpan(100));
        assertEquals(400, AppleScrollBar.thumbSpan(1000));
        assertEquals(0.40f, AppleScrollBar.THUMB_SHARE);
    }
}
