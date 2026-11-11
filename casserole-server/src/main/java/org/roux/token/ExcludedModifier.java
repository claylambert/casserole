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
 * // @aa-sdd PK-4129 docs/specs/java/org/aetheris/swing/swing.spec.md
 *
 * @author clayl (2026-11-02)
 */
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
