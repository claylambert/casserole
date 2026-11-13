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
 * The IncludedModifier keeps both halves in the counted string.
 * {@link #keepUser} returns true.
 * {@link #keepAssistant} returns true.
 *
 * // @aa-sdd GK-1064 aetheris-plan-f-174.001.md FINESSE:f-174
 *
 * @author clayl (2026-11-02)
 */
// @aa-sdd GK-1533 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-542 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-555 docs/specs/java/org/roux/token/token.spec.md
public final class IncludedModifier implements StateModifier {

    /**
     * The method keeps the user half.
     *
     * @return The flag is true.
     */
    @Override
    public boolean keepUser() {
        return true;
    }

    /**
     * The method keeps the assistant half.
     *
     * @return The flag is true.
     */
    @Override
    public boolean keepAssistant() {
        return true;
    }
}
/*****************************************************************************************
 * End of Class: IncludedModifier.java 110226
 ****************************************************************************************/
