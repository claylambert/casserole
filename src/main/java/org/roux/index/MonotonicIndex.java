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

package org.roux.index;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import org.roux.id.RouxId;
import org.roux.layout.RouxLayout;
import org.roux.model.IndexEntry;
import org.roux.model.Role;

// @aa-sdd PK-0786 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-0858 PK-0905 PK-0952 PK-0999 PK-1046 PK-1093 PK-1140 PK-1187 PK-1234 PK-1281 PK-1328 PK-1375 PK-1422 PK-1469 PK-1516 PK-1563 PK-1611 PK-1658 PK-1705 PK-1752 PK-1799 PK-1846 PK-1893 PK-1940 PK-1987 PK-2034 PK-2082 PK-2129 PK-2176 PK-2223 PK-2271 PK-2319 PK-2367 PK-2414 PK-2461 PK-2509 PK-2556 PK-2603 PK-2650 PK-2698 PK-2745 PK-2793 PK-2840 PK-2887 PK-2934 PK-2981 PK-3028 PK-3075 PK-3122 PK-3169 PK-3217 PK-3264 PK-3311 PK-3359 PK-3406 PK-3453 docs/specs/java/org/roux/roux.spec.md
/**
 * The MonotonicIndex is the off-heap list of message rows.
 * A message id starts at {@code 1}.
 * The slot for that id is {@code id - 1}.
 * Each row is {@code 56} bytes.
 * A row stores the message id, the page, the offset, the length, and the token count.
 * A row stores the time, the role, the type, the flags, and the two identity longs.
 * The index stays in memory.
 *
 * A new index holds {@code 256} rows.
 * The backing memory grows by doubling.
 *
 * @aa-sdd PK-1569 PK-2325 PK-3464 PK-3479 PK-3489 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
public class MonotonicIndex {

    public static final int STRIDE = 56;
    private static final int OFF_ID = 0;
    private static final int OFF_PAGE = 8;
    private static final int OFF_OFFSET = 12;
    private static final int OFF_LENGTH = 16;
    private static final int OFF_TOKENS = 20;
    private static final int OFF_TS = 24;
    private static final int OFF_ROLE = 32;
    private static final int OFF_TYPE = 33;
    private static final int OFF_FLAGS = 34;
    /**
     * The bytes at 36 through 39 are padding.
     * The identity longs start at byte 40 so each long sits on an 8-byte boundary.
     */
    private static final int OFF_ID_HI = 40;
    private static final int OFF_ID_LO = 48;

    private Arena arena;
    private MemorySegment segment;
    private int capacity;
    private int size;

    /**
     * The constructor allocates an empty index.
     * The first capacity is {@code 256} rows.
     * The rows are aligned to {@code 8} bytes.
     */
    public MonotonicIndex() {
        this.capacity = 256;
        this.arena = Arena.ofShared();
        this.segment = arena.allocate((long) capacity * STRIDE, 8);
        this.size = 0;
    }

    /**
     * The method returns the number of rows.
     *
     * @return The count is the number of stored rows.
     */
    public int size() {
        return size;
    }

    /**
     * The method appends one row and then increases the size by one.
     * A full index doubles its capacity before the write.
     * A null identity leaves the identity bytes unchanged.
     *
     * @aa-sdd PK-1569 PK-2325 PK-3464 PK-3479 PK-3489 docs/specs/java/org/roux/roux.spec.md
     *
     * @param messageId The message id is the number stored on the row.
     * @param pageId The page id is the page that holds the frame.
     * @param offset The offset is the first byte of the frame.
     * @param length The length is the frame size in bytes.
     * @param tokenCount The token count is the number stored with the message.
     * @param timestamp The time is the epoch milliseconds stored with the message.
     * @param role The role is the role byte.
     * @param type The type is the frame type byte.
     * @param flags The flags are the short stored on the row.
     * @param rouxId The identity is the {@link RouxId}, or null.
     */
    public void append(long messageId, int pageId, int offset, int length, int tokenCount, long timestamp,
            byte role, byte type, short flags, RouxId rouxId) {
        if (size >= capacity) {
            grow();
        }
        long base = (long) size * STRIDE;
        segment.set(RouxLayout.LONG_LE, base + OFF_ID, messageId);
        segment.set(RouxLayout.INT_LE, base + OFF_PAGE, pageId);
        segment.set(RouxLayout.INT_LE, base + OFF_OFFSET, offset);
        segment.set(RouxLayout.INT_LE, base + OFF_LENGTH, length);
        segment.set(RouxLayout.INT_LE, base + OFF_TOKENS, tokenCount);
        segment.set(RouxLayout.LONG_LE, base + OFF_TS, timestamp);
        segment.set(RouxLayout.BYTE, base + OFF_ROLE, role);
        segment.set(RouxLayout.BYTE, base + OFF_TYPE, type);
        segment.set(RouxLayout.SHORT_LE, base + OFF_FLAGS, flags);
        if (rouxId != null) {
            rouxId.write(segment, base + OFF_ID_HI);
        }
        size++;
    }

    /**
     * The method points an existing row at a new frame.
     * The size stays the same.
     * The message id, the role, the identity, the time, the type, and the flags stay.
     * A message id below {@code 1} returns without a change.
     * A message id above the size returns without a change.
     *
     * @aa-sdd PK-1569 PK-2325 PK-3464 PK-3479 PK-3489 docs/specs/java/org/roux/roux.spec.md
     *
     * @param messageId The message id is the existing row to retarget.
     * @param pageId The page id is the page of the new frame.
     * @param offset The offset is the first byte of the new frame.
     * @param length The length is the new frame size in bytes.
     * @param tokenCount The token count is the count of the new text.
     */
    public void retarget(long messageId, int pageId, int offset, int length, int tokenCount) {
        if (messageId < 1 || messageId > size) {
            return;
        }
        long base = (messageId - 1) * STRIDE;
        segment.set(RouxLayout.INT_LE, base + OFF_PAGE, pageId);
        segment.set(RouxLayout.INT_LE, base + OFF_OFFSET, offset);
        segment.set(RouxLayout.INT_LE, base + OFF_LENGTH, length);
        segment.set(RouxLayout.INT_LE, base + OFF_TOKENS, tokenCount);
    }

    /**
     * The method copies one row onto the heap.
     * A message id below {@code 1} returns null.
     * A message id above the size returns null.
     *
     * @param messageId The message id is the row to read.
     * @return The entry is the heap copy, or null.
     */
    public IndexEntry get(long messageId) {
        if (messageId < 1 || messageId > size) {
            return null;
        }
        long base = (messageId - 1) * STRIDE;
        RouxId rid = RouxId.read(segment, base + OFF_ID_HI);
        return new IndexEntry(
                segment.get(RouxLayout.LONG_LE, base + OFF_ID),
                segment.get(RouxLayout.INT_LE, base + OFF_PAGE),
                segment.get(RouxLayout.INT_LE, base + OFF_OFFSET),
                segment.get(RouxLayout.INT_LE, base + OFF_LENGTH),
                segment.get(RouxLayout.INT_LE, base + OFF_TOKENS),
                segment.get(RouxLayout.LONG_LE, base + OFF_TS),
                segment.get(RouxLayout.BYTE, base + OFF_ROLE),
                segment.get(RouxLayout.BYTE, base + OFF_TYPE),
                segment.get(RouxLayout.SHORT_LE, base + OFF_FLAGS),
                rid);
    }

    /**
     * The method counts rows with one role byte.
     *
     * @param role The role is the byte to match.
     * @return The count is the number of matching rows.
     */
    public int countByRole(byte role) {
        int n = 0;
        for (int i = 0; i < size; i++) {
            long base = (long) i * STRIDE;
            if (segment.get(RouxLayout.BYTE, base + OFF_ROLE) == role) {
                n++;
            }
        }
        return n;
    }

    /**
     * The method returns the last system message id.
     * The scan starts at the newest row.
     * No system row returns {@code 0}.
     *
     * @return The id is the last system message, or {@code 0}.
     */
    public long lastSystemId() {
        for (int i = size - 1; i >= 0; i--) {
            long base = (long) i * STRIDE;
            if (segment.get(RouxLayout.BYTE, base + OFF_ROLE) == Role.SYSTEM) {
                return segment.get(RouxLayout.LONG_LE, base + OFF_ID);
            }
        }
        return 0L;
    }

    /**
     * The method clears every row and sets the size to zero.
     * The same backing memory stays open.
     */
    public void reset() {
        size = 0;
        segment.fill((byte) 0);
    }

    /**
     * The method zeros the rows and closes the backing memory.
     * The size becomes zero.
     * A later use needs a new index.
     */
    public void close() {
        size = 0;
        if (arena != null && arena.scope().isAlive()) {
            segment.fill((byte) 0);
            arena.close();
        }
        arena = null;
        segment = null;
    }

    /**
     * The method doubles the row capacity.
     * The existing rows are copied.
     * The previous backing memory is closed.
     */
    private void grow() {
        int next = capacity * 2;
        Arena nextArena = Arena.ofShared();
        MemorySegment nextSeg = nextArena.allocate((long) next * STRIDE, 8);
        MemorySegment.copy(segment, 0, nextSeg, 0, (long) size * STRIDE);
        Arena old = arena;
        arena = nextArena;
        segment = nextSeg;
        capacity = next;
        if (old != null && old.scope().isAlive()) {
            old.close();
        }
    }
}
/*****************************************************************************************
 * End of Class: MonotonicIndex.java 082526
 ****************************************************************************************/
