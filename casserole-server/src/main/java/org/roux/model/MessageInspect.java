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
