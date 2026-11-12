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

import org.roux.id.RouxId;

/**
 * The IndexEntry is one index row copied onto the heap.
 * The off-heap index remains the store.
 * This object is the copy a caller reads.
 * The object stores the message id, which is the number that locates the frame.
 * The object stores the page id, the frame offset, and the frame length.
 * The object stores the token count, the time, the role byte, and the type byte.
 * The object stores the flags.
 * The object stores the {@link RouxId}.
 *
 * The identity may be null.
 *
 *
 * @author clayl (2026-08-25)
 */
public class IndexEntry {

    private final long messageId;
    private final int pageId;
    private final int offset;
    private final int length;
    private final int tokenCount;
    private final long timestamp;
    private final byte role;
    private final byte type;
    private final short flags;
    private final RouxId rouxId;

    /**
     * The constructor stores one index row on the heap.
     *
     * @param messageId The message id is the number that locates the frame.
     * @param pageId The page id is the page that holds the frame.
     * @param offset The offset is the first byte of the frame in that page.
     * @param length The length is the frame size in bytes.
     * @param tokenCount The token count is the number stored with the message.
     * @param timestamp The time is the epoch milliseconds stored with the message.
     * @param role The role is a byte from {@link Role}.
     * @param type The type is a byte from {@link FrameType}.
     * @param flags The flags are the short stored on the row.
     * @param rouxId The identity is the {@link RouxId}, or null.
     */
    public IndexEntry(long messageId, int pageId, int offset, int length, int tokenCount, long timestamp,
            byte role, byte type, short flags, RouxId rouxId) {
        this.messageId = messageId;
        this.pageId = pageId;
        this.offset = offset;
        this.length = length;
        this.tokenCount = tokenCount;
        this.timestamp = timestamp;
        this.role = role;
        this.type = type;
        this.flags = flags;
        this.rouxId = rouxId;
    }

    /**
     * The method returns the stored message id.
     *
     * @return The id is the number that locates the frame.
     */
    public long getMessageId() {
        return messageId;
    }

    /**
     * The method returns the stored page id.
     *
     * @return The id is the page that holds the frame.
     */
    public int getPageId() {
        return pageId;
    }

    /**
     * The method returns the stored frame offset.
     *
     * @return The offset is the first byte of the frame in the page.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * The method returns the stored frame length.
     *
     * @return The length is the frame size in bytes.
     */
    public int getLength() {
        return length;
    }

    /**
     * The method returns the stored token count.
     *
     * @return The count is the number stored with the message.
     */
    public int getTokenCount() {
        return tokenCount;
    }

    /**
     * The method returns the stored time.
     *
     * @return The time is the epoch milliseconds stored with the message.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * The method returns the stored role byte.
     *
     * @return The role is a byte from {@link Role}.
     */
    public byte getRole() {
        return role;
    }

    /**
     * The method returns the stored type byte.
     *
     * @return The type is a byte from {@link FrameType}.
     */
    public byte getType() {
        return type;
    }

    /**
     * The method returns the stored flags.
     *
     * @return The flags are the short stored on the row.
     */
    public short getFlags() {
        return flags;
    }

    /**
     * The method returns the stored identity.
     *
     * @return The identity is the {@link RouxId}, or null.
     */
    public RouxId getRouxId() {
        return rouxId;
    }
}
/*****************************************************************************************
 * End of Class: IndexEntry.java 082526
 ****************************************************************************************/
