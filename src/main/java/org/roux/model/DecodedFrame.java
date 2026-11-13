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

/**
 * The DecodedFrame is one message copied onto the heap from a stored frame.
 * The object stores the message id, which is the number that locates the frame.
 * The object stores the role byte.
 * The object stores the time in epoch milliseconds.
 * The object stores the token count that was saved with the message.
 * The object stores the message text.
 *
 * {@link org.roux.toon.ToonCodec} builds this object when a caller reads a payload.
 * A null text is stored as an empty string.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#DecodedFrame roux-v1.md
 *
 * @author clayl (2026-08-25)
 */
public class DecodedFrame {

    private final long messageId;
    private final byte role;
    private final long timestampEpochMs;
    private final int tokenCount;
    private final String text;

    /**
     * The constructor stores the message id, the role, the time, the token count, and the text.
     * A null text is stored as an empty string.
     *
     * @param messageId The message id is the number that locates the frame.
     * @param role The role is the byte from {@link Role}.
     * @param timestampEpochMs The time is the epoch milliseconds stored with the message.
     * @param tokenCount The token count is the number stored with the message.
     * @param text The text is the message body, or an empty string.
     */
    public DecodedFrame(long messageId, byte role, long timestampEpochMs, int tokenCount, String text) {
        this.messageId = messageId;
        this.role = role;
        this.timestampEpochMs = timestampEpochMs;
        this.tokenCount = tokenCount;
        this.text = text == null ? "" : text;
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
     * The method returns the stored message text.
     *
     * @return The text is the message body, or an empty string.
     */
    public String getText() {
        return text;
    }
}
/*****************************************************************************************
 * End of Class: DecodedFrame.java 082526
 ****************************************************************************************/
