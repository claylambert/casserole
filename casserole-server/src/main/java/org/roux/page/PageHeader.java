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

package org.roux.page;

import java.lang.foreign.MemorySegment;
import java.util.zip.CRC32C;

import org.roux.layout.RouxLayout;
import org.roux.model.PageFlags;

// @aa-sdd PK-0859 PK-0906 PK-0953 PK-1000 PK-1047 PK-1094 PK-1141 PK-1188 PK-1235 PK-1282 PK-1329 PK-1376 PK-1423 PK-1470 PK-1517 PK-1564 PK-1612 PK-1659 PK-1706 PK-1753 PK-1800 PK-1847 PK-1894 PK-1941 PK-1988 PK-2035 PK-2083 PK-2130 PK-2177 PK-2224 PK-2272 PK-2320 PK-2368 PK-2415 PK-2462 PK-2510 PK-2557 PK-2604 PK-2651 PK-2699 PK-2746 PK-2794 PK-2841 PK-2888 PK-2935 PK-2982 PK-3029 PK-3076 PK-3123 PK-3170 PK-3218 PK-3265 PK-3312 PK-3360 PK-3407 PK-3454 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
/**
 * The PageHeader type reads and writes the 64-byte header at the start of a page.
 * {@link #init} writes the bytes {@code R}, {@code O}, {@code U}, and {@code X}.
 * The version written is {@code 2}.
 * A new page starts with the hot flag from {@link PageFlags}.
 * The header stores the page id, the generation, the used bytes, and a checksum.
 * The checksum is a CRC32C value.
 * The header stores the frame count, the first message id, the last message id, and the next page id.
 *
 * The type refuses construction.
 *
 * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
 * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
 * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
 * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
 * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
 * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
 * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
 * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
 * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
 * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
 * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
 * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
 * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
 * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
 * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
 * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
 * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
 * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
 *
 * @author clayl (2026-08-25)
 */
public final class PageHeader {

    public static final int SIZE = 64;
    public static final int VERSION = 2;
    public static final int OFF_PAGE_ID = 8;
    public static final int OFF_GENERATION = 12;
    public static final int OFF_FLAGS = 16;
    public static final int OFF_USED = 20;
    public static final int OFF_CRC = 24;
    public static final int OFF_FRAME_COUNT = 28;
    public static final int OFF_FIRST_ID = 32;
    public static final int OFF_LAST_ID = 40;
    public static final int OFF_NEXT_PAGE = 48;

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private PageHeader() {
    }

    /**
     * The method clears the page and writes a new header.
     * The magic bytes are {@code R}, {@code O}, {@code U}, and {@code X}.
     * The version is {@code 2}.
     * The used-byte count starts at the header size.
     * The capacity argument is accepted and is not written.
     *
     * @param segment The segment is the page.
     * @param pageId The page id is the number stored in the header.
     * @param pageBytes The capacity is accepted and is not written.
     */
    public static void init(MemorySegment segment, int pageId, int pageBytes) {
        segment.fill((byte) 0);
        segment.set(RouxLayout.BYTE, 0, (byte) 'R');
        segment.set(RouxLayout.BYTE, 1, (byte) 'O');
        segment.set(RouxLayout.BYTE, 2, (byte) 'U');
        segment.set(RouxLayout.BYTE, 3, (byte) 'X');
        segment.set(RouxLayout.SHORT_LE, 4, (short) VERSION);
        segment.set(RouxLayout.SHORT_LE, 6, (short) SIZE);
        segment.set(RouxLayout.INT_LE, OFF_PAGE_ID, pageId);
        segment.set(RouxLayout.INT_LE, OFF_GENERATION, 0);
        segment.set(RouxLayout.INT_LE, OFF_FLAGS, PageFlags.HOT);
        segment.set(RouxLayout.INT_LE, OFF_USED, SIZE);
        segment.set(RouxLayout.INT_LE, OFF_CRC, 0);
        segment.set(RouxLayout.INT_LE, OFF_FRAME_COUNT, 0);
        segment.set(RouxLayout.LONG_LE, OFF_FIRST_ID, 0L);
        segment.set(RouxLayout.LONG_LE, OFF_LAST_ID, 0L);
        segment.set(RouxLayout.INT_LE, OFF_NEXT_PAGE, 0);
    }

    /**
     * The method checks the four magic bytes.
     *
     * @param segment The segment is the page.
     * @return The flag is true when the bytes are {@code R}, {@code O}, {@code U}, and {@code X}.
     */
    public static boolean hasMagic(MemorySegment segment) {
        return segment.get(RouxLayout.BYTE, 0) == (byte) 'R'
                && segment.get(RouxLayout.BYTE, 1) == (byte) 'O'
                && segment.get(RouxLayout.BYTE, 2) == (byte) 'U'
                && segment.get(RouxLayout.BYTE, 3) == (byte) 'X';
    }

    /**
     * The method returns the page id.
     *
     * @param segment The segment is the page.
     * @return The id is the number stored in the header.
     */
    public static int pageId(MemorySegment segment) {
        return segment.get(RouxLayout.INT_LE, OFF_PAGE_ID);
    }

    /**
     * The method returns the generation.
     *
     * @param segment The segment is the page.
     * @return The generation is the counter stored in the header.
     */
    public static int generation(MemorySegment segment) {
        return segment.get(RouxLayout.INT_LE, OFF_GENERATION);
    }

    /**
     * The method stores the generation.
     *
     * @param segment The segment is the page.
     * @param generation The generation is the counter to store.
     */
    public static void setGeneration(MemorySegment segment, int generation) {
        segment.set(RouxLayout.INT_LE, OFF_GENERATION, generation);
    }

    /**
     * The method returns the flag bits.
     *
     * @param segment The segment is the page.
     * @return The flags are the bits stored in the header.
     */
    public static int flags(MemorySegment segment) {
        return segment.get(RouxLayout.INT_LE, OFF_FLAGS);
    }

    /**
     * The method stores the flag bits.
     *
     * @param segment The segment is the page.
     * @param flags The flags are the bits to store.
     */
    public static void setFlags(MemorySegment segment, int flags) {
        segment.set(RouxLayout.INT_LE, OFF_FLAGS, flags);
    }

    /**
     * The method returns the used-byte count.
     *
     * @param segment The segment is the page.
     * @return The count is the used bytes stored in the header.
     */
    public static int usedBytes(MemorySegment segment) {
        return segment.get(RouxLayout.INT_LE, OFF_USED);
    }

    /**
     * The method stores the used-byte count.
     *
     * @param segment The segment is the page.
     * @param used The used count is the number of bytes to store.
     */
    public static void setUsedBytes(MemorySegment segment, int used) {
        segment.set(RouxLayout.INT_LE, OFF_USED, used);
    }

    /**
     * The method returns the frame count.
     *
     * @param segment The segment is the page.
     * @return The count is the number of frames stored in the header.
     */
    public static int frameCount(MemorySegment segment) {
        return segment.get(RouxLayout.INT_LE, OFF_FRAME_COUNT);
    }

    /**
     * The method stores the frame count.
     *
     * @param segment The segment is the page.
     * @param count The count is the number of frames to store.
     */
    public static void setFrameCount(MemorySegment segment, int count) {
        segment.set(RouxLayout.INT_LE, OFF_FRAME_COUNT, count);
    }

    /**
     * The method stores the first message id.
     *
     * @param segment The segment is the page.
     * @param id The id is the first message on the page.
     */
    public static void setFirstId(MemorySegment segment, long id) {
        segment.set(RouxLayout.LONG_LE, OFF_FIRST_ID, id);
    }

    /**
     * The method stores the last message id.
     *
     * @param segment The segment is the page.
     * @param id The id is the last message on the page.
     */
    public static void setLastId(MemorySegment segment, long id) {
        segment.set(RouxLayout.LONG_LE, OFF_LAST_ID, id);
    }

    /**
     * The method returns the last message id.
     *
     * @param segment The segment is the page.
     * @return The id is the last message stored in the header.
     */
    public static long lastId(MemorySegment segment) {
        return segment.get(RouxLayout.LONG_LE, OFF_LAST_ID);
    }

    /**
     * The method stores the next page id.
     *
     * @param segment The segment is the page.
     * @param nextPageId The next page id is the page that follows this one.
     */
    public static void setNextPageId(MemorySegment segment, int nextPageId) {
        segment.set(RouxLayout.INT_LE, OFF_NEXT_PAGE, nextPageId);
    }

    /**
     * The method writes a CRC32C checksum of the used bytes.
     * The checksum skips the four checksum bytes themselves.
     * The covered range is the used prefix of the page.
     *
     * @param segment The segment is the page.
     */
    public static void writeCrc(MemorySegment segment) {
        int used = usedBytes(segment);
        CRC32C crc = new CRC32C();
        byte[] prefix = new byte[OFF_CRC];
        MemorySegment.copy(segment, RouxLayout.BYTE, 0, prefix, 0, OFF_CRC);
        crc.update(prefix);
        int rest = used - (OFF_CRC + 4);
        if (rest > 0) {
            byte[] tail = new byte[rest];
            MemorySegment.copy(segment, RouxLayout.BYTE, OFF_CRC + 4, tail, 0, rest);
            crc.update(tail);
        }
        segment.set(RouxLayout.INT_LE, OFF_CRC, (int) crc.getValue());
    }
}
/*****************************************************************************************
 * End of Class: PageHeader.java 082526
 ****************************************************************************************/
