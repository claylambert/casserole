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

// @aa-sdd GK-1087 aetheris-stage-2-finessing.001.md
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
