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
 * // @aa-sdd PK-0767 PK-0802 PK-4132 docs/specs/java/org/roux/token/token.spec.md
 *
 * @author clayl (2026-11-02)
 */
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
