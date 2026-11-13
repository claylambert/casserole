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
 * The InputOnlyModifier keeps the user half and drops the assistant half.
 * {@link #keepUser} returns true.
 * {@link #keepAssistant} returns false.
 *
 * A caller that counts the user half still counts that text.
 *
 * // @aa-sdd GK-1065 aetheris-plan-f-174.001.md FINESSE:f-174
 *
 * @author clayl (2026-11-02)
 */
// @aa-sdd GK-1534 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-551 docs/specs/java/org/roux/token/token.spec.md
public final class InputOnlyModifier implements StateModifier {

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
     * The method keeps the assistant half out.
     *
     * @return The flag is false.
     */
    @Override
    public boolean keepAssistant() {
        return false;
    }
}
/*****************************************************************************************
 * End of Class: InputOnlyModifier.java 110226
 ****************************************************************************************/
