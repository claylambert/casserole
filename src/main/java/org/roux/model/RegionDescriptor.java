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
 * @aa-sdd PK-0758 PK-0759 docs/specs/java/org/roux/roux.spec.md
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
