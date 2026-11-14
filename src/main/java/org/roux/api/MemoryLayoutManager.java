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

package org.roux.api;

import org.roux.model.AllocationResult;
import org.roux.model.OperationResult;

/**
 * The MemoryLayoutManager is the call set that opens, grows, and closes a region.
 * The hot page is the page that currently receives new frames.
 * {@link #allocateRegion} opens a region.
 * {@link #growHotPage} replaces the hot page.
 * {@link #safeFreeRegion} closes a region.
 * {@link #defragment} is not implemented.
 * {@link #setPinned} stores the pin flag on the region.
 *
 * A pin keeps a close from running until the caller forces it.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#MemoryLayoutManager roux-design.md
 *
 * @author clayl (2026-08-25)
 */
public interface MemoryLayoutManager {

    /**
     * The method opens a region.
     * The capacity hint is not used.
     * The page size comes from the settings.
     *
     * @param regionId The region id is the name of the region.
     * @param capacityHintBytes The hint is accepted and is not used to size the page.
     * @return The result is an {@link AllocationResult}.
     */
    AllocationResult allocateRegion(String regionId, long capacityHintBytes);

    /**
     * The method retires the hot page and opens a new hot page.
     * A disk region writes the old page before the new page becomes hot.
     * A memory-only region keeps the old page mapped when the resident cap allows it.
     * A failed call leaves the previous hot page in place.
     *
     * @param regionId The region id is the name of the region.
     * @return The result is an {@link AllocationResult}.
     */
    AllocationResult growHotPage(String regionId);

    /**
     * The method closes a region.
     * A pinned region stays open when the force flag is false.
     *
     * @param regionId The region id is the name of the region.
     * @param forceIfPinned The flag is true when a pin must not block the close.
     * @return The result is an {@link OperationResult}.
     */
    OperationResult safeFreeRegion(String regionId, boolean forceIfPinned);

    /**
     * The method refuses to defragment a region.
     * The refusal status is {@link org.roux.model.OperationStatus#UNSUPPORTED}.
     *
     * @param regionId The region id is the name of the region.
     * @return The result is a failure.
     */
    OperationResult defragment(String regionId);

    /**
     * The method stores the pin flag on a region.
     *
     * @param regionId The region id is the name of the region.
     * @param pinned The flag is true when the region is pinned.
     */
    void setPinned(String regionId, boolean pinned);
}
/*****************************************************************************************
 * End of Class: MemoryLayoutManager.java 082526
 ****************************************************************************************/
