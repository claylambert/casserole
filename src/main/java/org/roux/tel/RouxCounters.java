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

package org.roux.tel;

import java.util.concurrent.atomic.AtomicLong;

import org.roux.model.MemoryTelemetry;
import org.springframework.stereotype.Component;

/**
 * The RouxCounters type holds the live counters for the memory engine.
 * Each counter is an atomic long in this process.
 * {@link #snapshot} copies them into a {@link MemoryTelemetry}.
 * Later increments stay on these counters.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#RouxCounters roux-design.md
 *
 * @author clayl (2026-08-25)
 */
@Component
// @aa-sdd PK-688 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-534 docs/specs/java/org/roux/tel/tel.spec.md
public class RouxCounters {

    private final AtomicLong pagesAllocated = new AtomicLong();
    private final AtomicLong pagesSpilled = new AtomicLong();
    private final AtomicLong framesAppended = new AtomicLong();
    private final AtomicLong indexEntries = new AtomicLong();
    private final AtomicLong seqlockRetries = new AtomicLong();
    private final AtomicLong dirtyFrames = new AtomicLong();
    private final AtomicLong staleViews = new AtomicLong();
    private final AtomicLong spillBytes = new AtomicLong();
    private final AtomicLong spillNanos = new AtomicLong();
    private final AtomicLong lockWaitNanos = new AtomicLong();
    private final AtomicLong usedBytesHot = new AtomicLong();
    private final AtomicLong usedBytesSpilled = new AtomicLong();

    /**
     * The method copies the counters into a {@link MemoryTelemetry}.
     * The fragmentation index is one minus the hot used bytes divided by the capacity.
     * The capacity is the allocated-page count times {@code 1048576}.
     * A capacity below {@code 1} is treated as {@code 1}.
     * A negative index is stored as {@code 0}.
     *
     * @return The snapshot is the copied counters.
     */
    public MemoryTelemetry snapshot() {
        long hot = usedBytesHot.get();
        long spilled = usedBytesSpilled.get();
        long cap = pagesAllocated.get() * 1048576L;
        if (cap < 1) {
            cap = 1;
        }
        double frag = 1.0 - ((double) hot / (double) cap);
        if (frag < 0) {
            frag = 0;
        }
        return new MemoryTelemetry(
                pagesAllocated.get(),
                pagesSpilled.get(),
                framesAppended.get(),
                indexEntries.get(),
                seqlockRetries.get(),
                dirtyFrames.get(),
                staleViews.get(),
                spillBytes.get(),
                spillNanos.get(),
                lockWaitNanos.get(),
                hot,
                spilled,
                frag);
    }

    /**
     * The method returns the live page-allocation counter.
     *
     * @return The counter is the number of pages created.
     */
    public AtomicLong pagesAllocated() {
        return pagesAllocated;
    }

    /**
     * The method returns the live spill counter.
     *
     * @return The counter is the number of pages written out of memory.
     */
    public AtomicLong pagesSpilled() {
        return pagesSpilled;
    }

    /**
     * The method returns the live frame counter.
     *
     * @return The counter is the number of frames written.
     */
    public AtomicLong framesAppended() {
        return framesAppended;
    }

    /**
     * The method returns the live index-row counter.
     *
     * @return The counter is the number of index rows.
     */
    public AtomicLong indexEntries() {
        return indexEntries;
    }

    /**
     * The method returns the live reader-retry counter.
     *
     * @return The counter is the number of reader retries.
     */
    public AtomicLong seqlockRetries() {
        return seqlockRetries;
    }

    /**
     * The method returns the live checksum-failure counter.
     *
     * @return The counter is the number of checksum failures.
     */
    public AtomicLong dirtyFrames() {
        return dirtyFrames;
    }

    /**
     * The method returns the live stale-read counter.
     *
     * @return The counter is the number of reads that missed a live page.
     */
    public AtomicLong staleViews() {
        return staleViews;
    }

    /**
     * The method returns the live spill-byte counter.
     *
     * @return The counter is the number of bytes written out of memory.
     */
    public AtomicLong spillBytes() {
        return spillBytes;
    }

    /**
     * The method returns the live spill-time counter.
     *
     * @return The counter is the nanoseconds spent writing bytes out of memory.
     */
    public AtomicLong spillNanos() {
        return spillNanos;
    }

    /**
     * The method returns the live lock-wait counter.
     *
     * @return The counter is the nanoseconds spent waiting for a lock.
     */
    public AtomicLong lockWaitNanos() {
        return lockWaitNanos;
    }

    /**
     * The method returns the live hot used-byte counter.
     *
     * @return The counter is the hot used bytes.
     */
    public AtomicLong usedBytesHot() {
        return usedBytesHot;
    }

    /**
     * The method returns the live spilled used-byte counter.
     *
     * @return The counter is the spilled used bytes.
     */
    public AtomicLong usedBytesSpilled() {
        return usedBytesSpilled;
    }
}
/*****************************************************************************************
 * End of Class: RouxCounters.java 082526
 ****************************************************************************************/
