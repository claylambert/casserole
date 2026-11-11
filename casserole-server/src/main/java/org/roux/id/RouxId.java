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
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
