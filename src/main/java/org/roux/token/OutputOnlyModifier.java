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
 * The OutputOnlyModifier keeps the assistant half and drops the user half.
 * {@link #keepUser} returns false.
 * {@link #keepAssistant} returns true.
 *
 * The user text stays out of the counted string.
 *
 * // @aa-sdd PK-1105 docs/specs/common/formal-plan.spec.md FINESSE:f-174
 *
 * @author clayl (2026-11-02)
 */
// @aa-sdd GK-1537 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-552 docs/specs/java/org/roux/token/token.spec.md
public final class OutputOnlyModifier implements StateModifier {

    /**
     * The method keeps the user half out.
     *
     * @return The flag is false.
     */
    @Override
    public boolean keepUser() {
        return false;
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
 * End of Class: OutputOnlyModifier.java 110226
 ****************************************************************************************/
