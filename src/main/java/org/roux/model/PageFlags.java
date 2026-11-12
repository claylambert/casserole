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
 * The PageFlags type holds the bit values stored in a page header.
 * The {@code HOT} bit is {@code 1}.
 * The {@code SPILLED} bit is {@code 2}.
 * The {@code RETIRED} bit is {@code 4}.
 * The {@code DIRTY_SUSPECT} bit is {@code 8}.
 *
 * A new page starts with the {@code HOT} bit set.
 * A caller stores the bits in the page header.
 * The type refuses construction.
 *
 * @aa-sdd PK-0758 PK-0759 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
public final class PageFlags {

    public static final int HOT = 1;
    public static final int SPILLED = 2;
    public static final int RETIRED = 4;
    public static final int DIRTY_SUSPECT = 8;

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private PageFlags() {
    }
}
/*****************************************************************************************
 * End of Class: PageFlags.java 082526
 ****************************************************************************************/
