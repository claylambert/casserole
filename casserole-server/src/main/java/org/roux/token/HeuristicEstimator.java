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
 * The HeuristicEstimator counts one rendered {@link Transcript}.
 * The method does not add a separate count for each turn.
 * A null transcript returns {@code 0}.
 * Zero turns and no mood head return {@code 0}.
 * That empty case does not call {@link HeuristicTokenCounter#count}.
 *
 * // @aa-sdd PK-4131 docs/specs/java/org/aetheris/swing/swing.spec.md
 *
 * @author clayl (2026-11-02)
 */
public final class HeuristicEstimator implements TokenEstimator {

    /**
     * The method renders the transcript once and counts that string.
     * A null transcript returns {@code 0}.
     * Zero turns and no mood head return {@code 0}.
     * A mood head with zero turns is still counted.
     * Turns that render to an empty string are still passed to {@link HeuristicTokenCounter#count}.
     * That empty string counts as {@code 1}.
     *
     * @param transcript The transcript is the text to estimate, or null.
     * @return The integer is the estimate.
     */
    @Override
    public int estimate(Transcript transcript) {
        if (transcript == null) {
            return 0;
        }
        if (transcript.turnCount() == 0 && !transcript.hasMoodHead()) {
            return 0;
        }
        return new HeuristicTokenCounter().count(transcript.rendered());
    }
}
/*****************************************************************************************
 * End of Class: HeuristicEstimator.java 110226
 ****************************************************************************************/
