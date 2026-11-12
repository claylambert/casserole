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

import java.lang.foreign.MemorySegment;
import java.util.List;

import org.roux.model.HealthReport;
import org.roux.model.MemoryAddress;
import org.roux.model.MemoryTelemetry;
import org.roux.model.RegionDescriptor;

/**
 * The MemoryInspector is the read-only view of regions and counters.
 * A caller can read a {@link MemoryTelemetry} snapshot.
 * A caller can list the live regions.
 * A caller can read one {@link RegionDescriptor}.
 * A caller can copy a raw byte range.
 * A caller can read a {@link HealthReport}.
 *
 * None of these calls change the stored frames.
 *
 * @aa-sdd PK-0758 PK-0759 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
public interface MemoryInspector {

    /**
     * The method returns a snapshot of the process counters.
     *
     * @return The snapshot is a {@link MemoryTelemetry}.
     */
    MemoryTelemetry getTelemetry();

    /**
     * The method returns the regions that are open.
     *
     * @return The list contains one {@link RegionDescriptor} for each open region.
     */
    List<RegionDescriptor> listRegions();

    /**
     * The method returns the descriptor for one region.
     *
     * @param regionId The region id is the name of the region.
     * @return The descriptor is the region, or null when that region is absent.
     */
    RegionDescriptor getRegion(String regionId);

    /**
     * The method copies raw bytes into the caller's segment.
     * The copy starts at the {@link MemoryAddress}.
     * The copy stops at the end of the destination when the destination is shorter than the address.
     *
     * @param address The address is the source range.
     * @param dest The segment is the caller-provided destination.
     */
    void readRaw(MemoryAddress address, MemorySegment dest);

    /**
     * The method checks one region and returns a health report.
     *
     * @param regionId The region id is the name of the region.
     * @return The report is a {@link HealthReport}.
     */
    HealthReport verifyIntegrity(String regionId);
}
/*****************************************************************************************
 * End of Class: MemoryInspector.java 082526
 ****************************************************************************************/
