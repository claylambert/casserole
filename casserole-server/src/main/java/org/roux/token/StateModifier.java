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
 * The StateModifier decides which halves of a turn stay in the counted string.
 * A turn has a user half and an assistant half.
 * A false return means that half stays out.
 *
 * // @aa-sdd PK-4138 docs/specs/java/org/aetheris/swing/swing.spec.md
 *
 * @author clayl (2026-11-02)
 */
public interface StateModifier {

    /**
     * The method says whether the user half stays.
     *
     * @return The flag is true when the user half stays in the counted string.
     */
    boolean keepUser();

    /**
     * The method says whether the assistant half stays.
     *
     * @return The flag is true when the assistant half stays in the counted string.
     */
    boolean keepAssistant();
}
/*****************************************************************************************
 * End of Class: StateModifier.java 110226
 ****************************************************************************************/
