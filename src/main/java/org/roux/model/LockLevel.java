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

package org.roux.model;

/**
 * The LockLevel type holds the integer stored on an operator lock.
 * The {@code EXCLUSIVE_WRITE} level is {@code 1}.
 * The {@code READ_ONLY_SNAPSHOT} level is {@code 2}.
 * The {@code DRAIN_AND_HALT} level is {@code 3}.
 *
 * A caller passes one of these integers when it asks for a {@link LockToken}.
 * The type refuses construction.
 *
 * @aa-sdd PK-0758 PK-0759 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
public final class LockLevel {

    public static final int EXCLUSIVE_WRITE = 1;
    public static final int READ_ONLY_SNAPSHOT = 2;
    public static final int DRAIN_AND_HALT = 3;

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private LockLevel() {
    }
}
/*****************************************************************************************
 * End of Class: LockLevel.java 082526
 ****************************************************************************************/
