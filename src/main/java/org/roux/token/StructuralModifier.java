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

package org.roux.token;

import java.util.List;

/**
 * The StructuralModifier turns one construct into {@link Transcript.Turn} values.
 * A construct is the user text and the assistant text before they are counted.
 * The structure chooses the turns.
 * A {@link StateModifier} then chooses which half of each turn stays.
 *
 * // @aa-sdd GK-1072 aetheris-plan-f-174.001.md FINESSE:f-174
 *
 * @author clayl (2026-11-02)
 */
// @aa-sdd GK-1540 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-544 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-554 docs/specs/java/org/roux/token/token.spec.md
public interface StructuralModifier {

    /**
     * The method returns the turns this construct keeps.
     * An empty list means no half stayed.
     *
     * @return The list contains the kept turns, or it is empty.
     */
    List<Transcript.Turn> turns();
}
/*****************************************************************************************
 * End of Class: StructuralModifier.java 110226
 ****************************************************************************************/
