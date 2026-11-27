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

/**
 * One notice carries the card and the mark.
 * The words are excluded and included.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1476 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public interface GraphModelListener {

    /**
     * The model speaks once for one card whose graph mark changed.
     *
     * @param card the card reference the evidence already stores
     * @param mark excluded or included
     */
    void notice(String card, String mark);
}
