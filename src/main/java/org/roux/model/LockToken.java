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
 * The LockToken is the handle returned when an operator lock is taken.
 * The object stores the lock id.
 * The object stores the region id.
 * The object stores the lock level.
 * The object stores the time the lock was taken, in epoch milliseconds.
 *
 * A caller passes this handle to release the lock.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#LockToken roux-design.md
 *
 * @author clayl (2026-08-25)
 */
public class LockToken {

    private final String lockId;
    private final String regionId;
    private final int level;
    private final long timestampEpochMs;

    /**
     * The constructor stores the lock id, the region id, the level, and the time.
     *
     * @param lockId The lock id is the text that identifies this lock.
     * @param regionId The region id is the name of the locked region.
     * @param level The level is a code from {@link LockLevel}.
     * @param timestampEpochMs The time is the epoch milliseconds when the lock was taken.
     */
    public LockToken(String lockId, String regionId, int level, long timestampEpochMs) {
        this.lockId = lockId;
        this.regionId = regionId;
        this.level = level;
        this.timestampEpochMs = timestampEpochMs;
    }

    /**
     * The method returns the stored lock id.
     *
     * @return The id is the text that identifies this lock.
     */
    public String getLockId() {
        return lockId;
    }

    /**
     * The method returns the stored region id.
     *
     * @return The id is the name of the locked region.
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * The method returns the stored lock level.
     *
     * @return The level is a code from {@link LockLevel}.
     */
    public int getLevel() {
        return level;
    }

    /**
     * The method returns the stored acquire time.
     *
     * @return The time is the epoch milliseconds when the lock was taken.
     */
    public long getTimestampEpochMs() {
        return timestampEpochMs;
    }
}
/*****************************************************************************************
 * End of Class: LockToken.java 082526
 ****************************************************************************************/
