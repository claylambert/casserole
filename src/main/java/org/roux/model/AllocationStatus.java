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
 * The AllocationStatus type holds the integer codes an allocation can return.
 * The {@code SUCCESS} code is {@code 0}.
 * The {@code OUT_OF_ADDRESS_SPACE} code is {@code 1}.
 * The {@code SEVERE_FRAGMENTATION} code is {@code 2}.
 * The {@code FRAME_TOO_LARGE} code is {@code 3}.
 *
 * A caller compares the code on an {@link AllocationResult}.
 * The type refuses construction, so the codes stay the only values.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#AllocationStatus roux-design.md
 *
 * @author clayl (2026-08-25)
 */
public final class AllocationStatus {

    public static final int SUCCESS = 0;
    public static final int OUT_OF_ADDRESS_SPACE = 1;
    public static final int SEVERE_FRAGMENTATION = 2;
    public static final int FRAME_TOO_LARGE = 3;

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private AllocationStatus() {
    }
}
/*****************************************************************************************
 * End of Class: AllocationStatus.java 082526
 ****************************************************************************************/
