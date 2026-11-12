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
 * The MemoryTelemetry is a frozen copy of the process counters.
 * The object stores pages allocated, pages spilled, frames appended, and index rows.
 * The object stores reader retries, checksum failures, and stale reads.
 * The object stores spill bytes, spill time, and lock-wait time.
 * The object stores hot used bytes and spilled used bytes.
 * The object stores a fragmentation index.
 *
 * Later increments stay on the live counters.
 * This object does not change after it is built.
 *
 * @aa-sdd PK-0758 PK-0759 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
public class MemoryTelemetry {

    private final long pagesAllocated;
    private final long pagesSpilled;
    private final long framesAppended;
    private final long indexEntries;
    private final long seqlockRetries;
    private final long dirtyFrames;
    private final long staleViews;
    private final long spillBytes;
    private final long spillNanos;
    private final long lockWaitNanos;
    private final long usedBytesHot;
    private final long usedBytesSpilled;
    private final double fragmentationIndex;

    /**
     * The constructor stores one snapshot of the process counters.
     *
     * @param pagesAllocated The count is the number of pages created.
     * @param pagesSpilled The count is the number of pages written out of memory.
     * @param framesAppended The count is the number of frames written.
     * @param indexEntries The count is the number of index rows.
     * @param seqlockRetries The count is the number of reader retries.
     * @param dirtyFrames The count is the number of checksum failures.
     * @param staleViews The count is the number of reads that missed a live page.
     * @param spillBytes The count is the number of bytes written out of memory.
     * @param spillNanos The time is the nanoseconds spent writing those bytes.
     * @param lockWaitNanos The time is the nanoseconds spent waiting for a lock.
     * @param usedBytesHot The count is the hot used bytes at the snapshot.
     * @param usedBytesSpilled The count is the spilled used bytes at the snapshot.
     * @param fragmentationIndex The index is the fragmentation value the caller computed.
     */
    public MemoryTelemetry(long pagesAllocated, long pagesSpilled, long framesAppended, long indexEntries,
            long seqlockRetries, long dirtyFrames, long staleViews, long spillBytes, long spillNanos,
            long lockWaitNanos, long usedBytesHot, long usedBytesSpilled, double fragmentationIndex) {
        this.pagesAllocated = pagesAllocated;
        this.pagesSpilled = pagesSpilled;
        this.framesAppended = framesAppended;
        this.indexEntries = indexEntries;
        this.seqlockRetries = seqlockRetries;
        this.dirtyFrames = dirtyFrames;
        this.staleViews = staleViews;
        this.spillBytes = spillBytes;
        this.spillNanos = spillNanos;
        this.lockWaitNanos = lockWaitNanos;
        this.usedBytesHot = usedBytesHot;
        this.usedBytesSpilled = usedBytesSpilled;
        this.fragmentationIndex = fragmentationIndex;
    }

    /**
     * The method returns the stored page-allocation count.
     *
     * @return The count is the number of pages created.
     */
    public long getPagesAllocated() {
        return pagesAllocated;
    }

    /**
     * The method returns the stored spill count.
     *
     * @return The count is the number of pages written out of memory.
     */
    public long getPagesSpilled() {
        return pagesSpilled;
    }

    /**
     * The method returns the stored frame count.
     *
     * @return The count is the number of frames written.
     */
    public long getFramesAppended() {
        return framesAppended;
    }

    /**
     * The method returns the stored index-row count.
     *
     * @return The count is the number of index rows.
     */
    public long getIndexEntries() {
        return indexEntries;
    }

    /**
     * The method returns the stored reader-retry count.
     *
     * @return The count is the number of reader retries.
     */
    public long getSeqlockRetries() {
        return seqlockRetries;
    }

    /**
     * The method returns the stored checksum-failure count.
     *
     * @return The count is the number of checksum failures.
     */
    public long getDirtyFrames() {
        return dirtyFrames;
    }

    /**
     * The method returns the stored stale-read count.
     *
     * @return The count is the number of reads that missed a live page.
     */
    public long getStaleViews() {
        return staleViews;
    }

    /**
     * The method returns the stored spill-byte count.
     *
     * @return The count is the number of bytes written out of memory.
     */
    public long getSpillBytes() {
        return spillBytes;
    }

    /**
     * The method returns the stored spill time.
     *
     * @return The time is the nanoseconds spent writing bytes out of memory.
     */
    public long getSpillNanos() {
        return spillNanos;
    }

    /**
     * The method returns the stored lock-wait time.
     *
     * @return The time is the nanoseconds spent waiting for a lock.
     */
    public long getLockWaitNanos() {
        return lockWaitNanos;
    }

    /**
     * The method returns the stored hot used-byte count.
     *
     * @return The count is the hot used bytes at the snapshot.
     */
    public long getUsedBytesHot() {
        return usedBytesHot;
    }

    /**
     * The method returns the stored spilled used-byte count.
     *
     * @return The count is the spilled used bytes at the snapshot.
     */
    public long getUsedBytesSpilled() {
        return usedBytesSpilled;
    }

    /**
     * The method returns the stored fragmentation index.
     *
     * @return The index is the fragmentation value the caller computed.
     */
    public double getFragmentationIndex() {
        return fragmentationIndex;
    }
}
/*****************************************************************************************
 * End of Class: MemoryTelemetry.java 082526
 ****************************************************************************************/
