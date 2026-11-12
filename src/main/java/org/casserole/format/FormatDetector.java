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

package org.casserole.format;

/**
 * The {@code FormatDetector} type decides whether one whole reply is a single format.
 * The decision is strict and deterministic.
 * The type does not return a score.
 *
 * @author clayl (2026-08-25)
 */
public interface FormatDetector {

    /**
     * The {@code matches} method tests one whole assistant reply.
     * The result is {@code true} only when this format is unambiguous.
     *
     * @param raw The full assistant text.
     * @return {@code true} only when this format is unambiguous.
     */
    boolean matches(String raw);
}
/*****************************************************************************************
 * End of Class: FormatDetector.java 082526
 ****************************************************************************************/
