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
 * The AllocationResult is the object returned by a region allocation or a hot-page growth.
 * The hot page is the page that currently receives new frames.
 * The object stores whether the call succeeded.
 * The object stores a {@link RegionDescriptor}.
 * The object stores a status code from {@link AllocationStatus}.
 * The object stores an error message.
 *
 * A caller reads those four values after the call.
 * A null error message is stored as an empty string.
 * The descriptor may be null when the call fails.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#AllocationResult roux-design.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-493 docs/specs/java/org/roux/model/model.spec.md
// @aa-sdd GK-3078 roux-design.md
public class AllocationResult {

    private final boolean success;
    private final RegionDescriptor descriptor;
    private final int status;
    private final String errorMessage;

    /**
     * The constructor stores the four values from an allocation or a growth.
     * A null error message is stored as an empty string.
     *
     * @param success The flag is true when the call succeeded.
     * @param descriptor The descriptor is the region after the call, and it may be null.
     * @param status The status is a code from {@link AllocationStatus}.
     * @param errorMessage The error message is the failure text, or an empty string.
     */
    public AllocationResult(boolean success, RegionDescriptor descriptor, int status, String errorMessage) {
        this.success = success;
        this.descriptor = descriptor;
        this.status = status;
        this.errorMessage = errorMessage == null ? "" : errorMessage;
    }

    /**
     * The method returns the stored success flag.
     *
     * @return The flag is true when the call succeeded.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * The method returns the stored region descriptor.
     *
     * @return The descriptor is the region after the call, or null.
     */
    public RegionDescriptor getDescriptor() {
        return descriptor;
    }

    /**
     * The method returns the stored status code.
     *
     * @return The code is a value from {@link AllocationStatus}.
     */
    public int getStatus() {
        return status;
    }

    /**
     * The method returns the stored error message.
     *
     * @return The message is the failure text, or an empty string.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
/*****************************************************************************************
 * End of Class: AllocationResult.java 082526
 ****************************************************************************************/
