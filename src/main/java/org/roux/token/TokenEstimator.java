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

/**
 * The TokenEstimator is one way to turn a {@link Transcript} into an integer.
 * A caller uses the integer this method returns.
 *
 * // @aa-sdd PK-1105 docs/specs/common/formal-plan.spec.md FINESSE:f-174
 *
 * @author clayl (2026-11-02)
 */
// @aa-sdd GK-1530 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-548 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-557 docs/specs/java/org/roux/token/token.spec.md
public interface TokenEstimator {

    /**
     * The method estimates one transcript.
     *
     * @param transcript The transcript is the text to estimate, or null.
     * @return The integer is the estimate.
     */
    int estimate(Transcript transcript);
}
/*****************************************************************************************
 * End of Class: TokenEstimator.java 110226
 ****************************************************************************************/
