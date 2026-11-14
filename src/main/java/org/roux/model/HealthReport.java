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
 * The HealthReport is the object returned by an integrity check of one region.
 * The object stores the region id.
 * The object stores whether the region is usable.
 * The object stores a detail string.
 *
 * A caller reads those three values.
 * A null detail is stored as an empty string.
 * A usable region can carry an empty detail.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#HealthReport roux-design.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-490 docs/specs/java/org/roux/model/model.spec.md
public class HealthReport {

    private final String regionId;
    private final boolean healthy;
    private final String detail;

    /**
     * The constructor stores the region id, the usable flag, and the detail.
     * A null detail is stored as an empty string.
     *
     * @param regionId The region id is the name of the checked region.
     * @param healthy The flag is true when the region is usable.
     * @param detail The detail is the failure text, or an empty string.
     */
    public HealthReport(String regionId, boolean healthy, String detail) {
        this.regionId = regionId;
        this.healthy = healthy;
        this.detail = detail == null ? "" : detail;
    }

    /**
     * The method returns the stored region id.
     *
     * @return The id is the name of the checked region.
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * The method returns the stored usable flag.
     *
     * @return The flag is true when the region is usable.
     */
    public boolean isHealthy() {
        return healthy;
    }

    /**
     * The method returns the stored detail.
     *
     * @return The detail is the failure text, or an empty string.
     */
    public String getDetail() {
        return detail;
    }
}
/*****************************************************************************************
 * End of Class: HealthReport.java 082526
 ****************************************************************************************/
