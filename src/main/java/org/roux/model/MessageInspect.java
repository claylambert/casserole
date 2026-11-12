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
 * The MessageInspect is a read-only view of one stored message.
 * The object stores the sequence number, which is the message id that locates the frame.
 * The object stores the {@link RouxId}, the role, the time, and the token count.
 * The object stores the frame type, the page id, the offset, and the length.
 * The object stores the residency word taken before the bytes are read.
 * Residency is {@code HOT}, {@code RESIDENT}, or {@code SPILLED}.
 * The object stores whether the payload checksum matched.
 * The checksum is the CRC32C value stored on the frame.
 * The object stores the message text.
 *
 * A null residency is stored as an empty string.
 * A null text is stored as an empty string.
 *
 * @aa-sdd PK-2040 PK-2277 PK-3460 PK-3465 PK-3480 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
public class MessageInspect {

    private final long seq;
    private final RouxId rouxId;
    private final byte role;
    private final long timestampEpochMs;
    private final int tokenCount;
    private final byte type;
    private final int pageId;
    private final int offset;
    private final int length;
    private final String residency;
    private final boolean crcOk;
    private final String text;

    /**
     * The constructor stores the inspect fields for one message.
     * A null residency is stored as an empty string.
     * A null text is stored as an empty string.
     *
     * @param seq The sequence number is the message id that locates the frame.
     * @param rouxId The identity is the {@link RouxId}.
     * @param role The role is a byte from {@link Role}.
     * @param timestampEpochMs The time is the epoch milliseconds stored with the message.
     * @param tokenCount The token count is the number stored with the message.
     * @param type The type is a byte from {@link FrameType}.
     * @param pageId The page id is the page that holds the frame.
     * @param offset The offset is the first byte of the frame in that page.
     * @param length The length is the frame size in bytes.
     * @param residency The residency is {@code HOT}, {@code RESIDENT}, or {@code SPILLED}.
     * @param crcOk The flag is true when the payload checksum matched.
     * @param text The text is the message body, or an empty string.
     */
    public MessageInspect(long seq, RouxId rouxId, byte role, long timestampEpochMs, int tokenCount, byte type,
            int pageId, int offset, int length, String residency, boolean crcOk, String text) {
        this.seq = seq;
        this.rouxId = rouxId;
        this.role = role;
        this.timestampEpochMs = timestampEpochMs;
        this.tokenCount = tokenCount;
        this.type = type;
        this.pageId = pageId;
        this.offset = offset;
        this.length = length;
        this.residency = residency == null ? "" : residency;
        this.crcOk = crcOk;
        this.text = text == null ? "" : text;
    }

    /**
     * The method returns the stored sequence number.
     *
     * @return The number is the message id that locates the frame.
     */
    public long getSeq() {
        return seq;
    }

    /**
     * The method returns the stored identity.
     *
     * @return The identity is the {@link RouxId}.
     */
    public RouxId getRouxId() {
        return rouxId;
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
     * The method returns the stored time.
     *
     * @return The time is the epoch milliseconds stored with the message.
     */
    public long getTimestampEpochMs() {
        return timestampEpochMs;
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
     * The method returns the stored frame type.
     *
     * @return The type is a byte from {@link FrameType}.
     */
    public byte getType() {
        return type;
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
     * The method returns the stored residency word.
     *
     * @return The word is {@code HOT}, {@code RESIDENT}, {@code SPILLED}, or an empty string.
     */
    public String getResidency() {
        return residency;
    }

    /**
     * The method returns the stored checksum flag.
     *
     * @return The flag is true when the payload checksum matched.
     */
    public boolean isCrcOk() {
        return crcOk;
    }

    /**
     * The method returns the stored message text.
     *
     * @return The text is the message body, or an empty string.
     */
    public String getText() {
        return text;
    }
}
/*****************************************************************************************
 * End of Class: MessageInspect.java 082526
 ****************************************************************************************/
