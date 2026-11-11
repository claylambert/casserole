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
 * The RegionDescriptor is a copy of one region's layout for a caller to read.
 * The hot page is the page that currently receives new frames.
 * The object stores the region id and the hot page id.
 * The object stores the hot page capacity in bytes.
 * The object stores the used bytes on the hot page.
 * The object stores the spilled-byte count the caller passed.
 * The object stores the message count.
 * The object stores whether the region is pinned.
 * The object stores the page flags.
 *
 * A caller reads these values and does not change the region through this object.
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
public class RegionDescriptor {

    private final String regionId;
    private final int hotPageId;
    private final long capacityBytes;
    private final long usedBytesHot;
    private final long usedBytesSpilled;
    private final long messageCount;
    private final boolean pinned;
    private final int flags;

    /**
     * The constructor stores the layout values for one region.
     *
     * @param regionId The region id is the name of the region.
     * @param hotPageId The hot page id is the page that currently receives new frames.
     * @param capacityBytes The capacity is the hot page size in bytes.
     * @param usedBytesHot The used count is the bytes already written on the hot page.
     * @param usedBytesSpilled The spilled count is the bytes recorded as spilled.
     * @param messageCount The message count is the number of frames in the index.
     * @param pinned The flag is true when the region is pinned.
     * @param flags The flags are the page flag bits.
     */
    public RegionDescriptor(String regionId, int hotPageId, long capacityBytes, long usedBytesHot,
            long usedBytesSpilled, long messageCount, boolean pinned, int flags) {
        this.regionId = regionId;
        this.hotPageId = hotPageId;
        this.capacityBytes = capacityBytes;
        this.usedBytesHot = usedBytesHot;
        this.usedBytesSpilled = usedBytesSpilled;
        this.messageCount = messageCount;
        this.pinned = pinned;
        this.flags = flags;
    }

    /**
     * The method returns the stored region id.
     *
     * @return The id is the name of the region.
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * The method returns the stored hot page id.
     *
     * @return The id is the page that currently receives new frames.
     */
    public int getHotPageId() {
        return hotPageId;
    }

    /**
     * The method returns the stored hot page capacity.
     *
     * @return The capacity is the hot page size in bytes.
     */
    public long getCapacityBytes() {
        return capacityBytes;
    }

    /**
     * The method returns the stored hot used-byte count.
     *
     * @return The count is the bytes already written on the hot page.
     */
    public long getUsedBytesHot() {
        return usedBytesHot;
    }

    /**
     * The method returns the stored spilled-byte count.
     *
     * @return The count is the number the constructor stored.
     */
    public long getUsedBytesSpilled() {
        return usedBytesSpilled;
    }

    /**
     * The method returns the stored message count.
     *
     * @return The count is the number of frames in the index.
     */
    public long getMessageCount() {
        return messageCount;
    }

    /**
     * The method returns the stored pin flag.
     *
     * @return The flag is true when the region is pinned.
     */
    public boolean isPinned() {
        return pinned;
    }

    /**
     * The method returns the stored page flags.
     *
     * @return The flags are the page flag bits.
     */
    public int getFlags() {
        return flags;
    }
}
/*****************************************************************************************
 * End of Class: RegionDescriptor.java 082526
 ****************************************************************************************/
