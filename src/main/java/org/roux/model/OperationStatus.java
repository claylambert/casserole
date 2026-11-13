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
 * The OperationStatus type holds the integer codes a changing call can return.
 * The {@code SUCCESS} code is {@code 0}.
 * The {@code BLOCKED_BY_PIN} code is {@code 1}.
 * The {@code RESOURCE_BUSY} code is {@code 2}.
 * The {@code CORRUPTED_SEGMENT} code is {@code 3}.
 * The {@code LOCK_TIMEOUT} code is {@code 4}.
 * The {@code STALE_VIEW} code is {@code 5}.
 * The {@code UNSUPPORTED} code is {@code 6}.
 * The {@code NOT_FOUND} code is {@code 7}.
 *
 * A caller reads the code from an {@link OperationResult}.
 * The type refuses construction.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#OperationStatus roux-design.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-489 docs/specs/java/org/roux/model/model.spec.md
public final class OperationStatus {

    public static final int SUCCESS = 0;
    public static final int BLOCKED_BY_PIN = 1;
    public static final int RESOURCE_BUSY = 2;
    public static final int CORRUPTED_SEGMENT = 3;
    public static final int LOCK_TIMEOUT = 4;
    public static final int STALE_VIEW = 5;
    public static final int UNSUPPORTED = 6;
    public static final int NOT_FOUND = 7;

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private OperationStatus() {
    }
}
/*****************************************************************************************
 * End of Class: OperationStatus.java 082526
 ****************************************************************************************/
