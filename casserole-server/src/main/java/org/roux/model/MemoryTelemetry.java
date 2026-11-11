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
