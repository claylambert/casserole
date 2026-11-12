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

package org.roux.id;

import java.lang.foreign.MemorySegment;

import org.roux.layout.RouxLayout;

/**
 * The RouxId is the identity of one message, stored as two longs.
 * The high long is the first 64 bits.
 * The low long is the second 64 bits.
 * The message id locates the frame.
 * These two longs stay with the frame when a page moves.
 * The values are kept as longs.
 * This type does not build a UUID object.
 *
 * @aa-sdd PK-0758 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
public final class RouxId {

    private final long hi;
    private final long lo;

    /**
     * The constructor stores the high long and the low long.
     *
     * @param hi The high long is the first 64 bits.
     * @param lo The low long is the second 64 bits.
     */
    public RouxId(long hi, long lo) {
        this.hi = hi;
        this.lo = lo;
    }

    /**
     * The method returns the high long.
     *
     * @return The long is the first 64 bits.
     */
    public long getHi() {
        return hi;
    }

    /**
     * The method returns the low long.
     *
     * @return The long is the second 64 bits.
     */
    public long getLo() {
        return lo;
    }

    /**
     * The method writes the two longs into a segment.
     * The high long is written at the offset.
     * The low long is written eight bytes later.
     * Both writes use the little-endian layout from {@link RouxLayout}.
     *
     * @param segment The segment is the destination.
     * @param offset The offset is the first byte of the high long.
     */
    public void write(MemorySegment segment, long offset) {
        segment.set(RouxLayout.LONG_LE, offset, hi);
        segment.set(RouxLayout.LONG_LE, offset + 8, lo);
    }

    /**
     * The method reads two longs from a segment.
     * The high long is read at the offset.
     * The low long is read eight bytes later.
     *
     * @param segment The segment is the source.
     * @param offset The offset is the first byte of the high long.
     * @return The identity is the two longs that were read.
     */
    public static RouxId read(MemorySegment segment, long offset) {
        return new RouxId(
                segment.get(RouxLayout.LONG_LE, offset),
                segment.get(RouxLayout.LONG_LE, offset + 8));
    }

    /**
     * The method compares two identities as unsigned longs.
     * The high longs are compared first.
     * Equal high longs are compared by the low longs.
     *
     * @param a The first identity is the left side.
     * @param b The second identity is the right side.
     * @return The result is negative, zero, or positive.
     */
    public static int compare(RouxId a, RouxId b) {
        int c = Long.compareUnsigned(a.hi, b.hi);
        if (c != 0) {
            return c;
        }
        return Long.compareUnsigned(a.lo, b.lo);
    }

    /**
     * The method reports whether another object is the same identity.
     * The same instance returns true.
     * An object that is not a RouxId returns false.
     * Equal high and low longs return true.
     *
     * @param obj The object is the value to compare.
     * @return The flag is true when both longs match.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RouxId)) {
            return false;
        }
        RouxId other = (RouxId) obj;
        return hi == other.hi && lo == other.lo;
    }

    /**
     * The method returns the integer computed from the two longs.
     * The high long is mixed first.
     * The low long is added after that.
     *
     * @return The code is derived from both longs.
     */
    @Override
    public int hashCode() {
        return Long.hashCode(hi) * 31 + Long.hashCode(lo);
    }
}
/*****************************************************************************************
 * End of Class: RouxId.java 082526
 ****************************************************************************************/
