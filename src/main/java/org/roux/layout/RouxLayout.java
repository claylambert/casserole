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

package org.roux.layout;

import java.lang.foreign.ValueLayout;
import java.nio.ByteOrder;

/**
 * The RouxLayout type holds the foreign-memory layouts used to read a page and an index row.
 * A foreign-memory layout is the Java description of one primitive in off-heap bytes.
 * {@code BYTE} reads one byte.
 * {@code SHORT_LE} reads a little-endian short.
 * {@code INT_LE} reads a little-endian int.
 * {@code LONG_LE} reads a little-endian long.
 *
 * A header field and an index field use these same layouts.
 * The type refuses construction.
 *
 * @aa-sdd PK-0758 PK-0759 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
public final class RouxLayout {

    public static final ValueLayout.OfByte BYTE = ValueLayout.JAVA_BYTE;
    public static final ValueLayout.OfShort SHORT_LE =
            ValueLayout.JAVA_SHORT.withOrder(ByteOrder.LITTLE_ENDIAN);
    public static final ValueLayout.OfInt INT_LE =
            ValueLayout.JAVA_INT.withOrder(ByteOrder.LITTLE_ENDIAN);
    public static final ValueLayout.OfLong LONG_LE =
            ValueLayout.JAVA_LONG.withOrder(ByteOrder.LITTLE_ENDIAN);

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private RouxLayout() {
    }
}
/*****************************************************************************************
 * End of Class: RouxLayout.java 082526
 ****************************************************************************************/
