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

// @aa-sdd GK-1055 aetheris-stage-2-finessing.001.md FINESSE:f-222
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
 * // @aa-sdd L40 aa-sdd-source-prose.md#PageHeader roux-design.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-496 docs/specs/java/org/roux/page/page.spec.md
// @aa-sdd PK-498 docs/specs/java/org/roux/page/page.spec.md
// @aa-sdd PK-814 docs/specs/common/roux.spec.md
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
