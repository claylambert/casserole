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

package org.casserole.chat.cmd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// @aa-sdd PK-0862 PK-0909 PK-0956 PK-1003 PK-1050 PK-1097 PK-1144 PK-1191 PK-1238 PK-1285 PK-1332 PK-1379 PK-1426 PK-1473 PK-1520 PK-1567 PK-1615 PK-1662 PK-1709 PK-1756 PK-1803 PK-1850 PK-1897 PK-1944 PK-1991 PK-2038 PK-2086 PK-2133 PK-2180 PK-2227 PK-2275 PK-2323 PK-2371 PK-2418 PK-2465 PK-2513 PK-2560 PK-2607 PK-2654 PK-2702 PK-2749 PK-2797 PK-2844 PK-2891 PK-2938 PK-2985 PK-3032 PK-3079 PK-3126 PK-3173 PK-3221 PK-3268 PK-3315 PK-3363 PK-3410 PK-3457 docs/specs/java/org/roux/roux.spec.md
/**
 * The class checks the sequence number that {@link MessageLockCommand#seq} reads from a lock line.
 * A line with one number returns that number.
 * A missing number is refused.
 * A word is refused.
 *
 * @author clayl (2026-11-04)
 */
class MessageHeadLineTest {

    /**
     * The method arranges no objects beyond the strings it passes.
     * The method calls {@link MessageLockCommand#seq}.
     * The text {@code 12} with surrounding spaces returns {@code 12} when the command word is {@code message lock}.
     * A null text throws {@link Exception}.
     * The message on that exception is {@code Usage: message lock <seq>}.
     * A blank text throws {@link Exception} with that same message.
     * The text {@code abc} throws {@link Exception} when the command word is {@code message unlock}.
     * The message on that exception is {@code Usage: message unlock <seq>}.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because {@link MessageLockCommand#seq} declares a checked failure.
     */
    @Test
    void aLockLineNamesOneSeq() throws Exception {
        assertEquals(12L, MessageLockCommand.seq(" 12 ", "message lock"));
        Exception missing = assertThrows(Exception.class, () -> MessageLockCommand.seq(null, "message lock"));
        assertEquals("Usage: message lock <seq>", missing.getMessage());
        Exception blank = assertThrows(Exception.class, () -> MessageLockCommand.seq("   ", "message lock"));
        assertEquals("Usage: message lock <seq>", blank.getMessage());
        Exception word = assertThrows(Exception.class, () -> MessageLockCommand.seq("abc", "message unlock"));
        assertEquals("Usage: message unlock <seq>", word.getMessage());
    }
}
/*****************************************************************************************
 * End of Class: MessageHeadLineTest.java 110426
 ****************************************************************************************/
