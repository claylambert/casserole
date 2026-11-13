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
 * The ExcludedModifier keeps both halves out of the counted string.
 * {@link #keepUser} returns false.
 * {@link #keepAssistant} returns false.
 *
 * // @aa-sdd PK-1105 docs/specs/common/formal-plan.spec.md FINESSE:f-174
 *
 * @author clayl (2026-11-02)
 */
// @aa-sdd PK-981 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-543 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-555 docs/specs/java/org/roux/token/token.spec.md
public final class ExcludedModifier implements StateModifier {

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
 * End of Class: ExcludedModifier.java 110226
 ****************************************************************************************/
