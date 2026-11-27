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

package org.aetheris.lcarsgraph;

/**
 * One ask sends one brief and returns the reply text.
 * The desk does not open a second wire.
 *
 * @author clayl (2026-11-24)
 */
// @aa-sdd PK-1477 docs/specs/java/org/aetheris/lcarsgraph/lcarsgraph.spec.md
public interface GraphDoor {

    /**
     * The method sends one brief on the door the selected model already uses.
     * A door that cannot send throws.
     *
     * @param brief The brief is the intent ask.
     * @return The return value is the reply text.
     * @throws Exception The exception is a door that does not send.
     */
    String ask(String brief) throws Exception;
}
