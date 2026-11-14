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
 * The MemoryAddress is a byte range inside one page.
 * The object stores the region id.
 * The object stores the page id.
 * The object stores the byte offset.
 * The object stores the byte length.
 *
 * A raw read copies that range into the caller's segment.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#MemoryAddress roux-design.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-492 docs/specs/java/org/roux/model/model.spec.md
public class MemoryAddress {

    private final String regionId;
    private final int pageId;
    private final long offset;
    private final long length;

    /**
     * The constructor stores the region, the page, the offset, and the length.
     *
     * @param regionId The region id is the name of the region.
     * @param pageId The page id is the number of the page.
     * @param offset The offset is the first byte of the range in the page.
     * @param length The length is the number of bytes in the range.
     */
    public MemoryAddress(String regionId, int pageId, long offset, long length) {
        this.regionId = regionId;
        this.pageId = pageId;
        this.offset = offset;
        this.length = length;
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
     * The method returns the stored page id.
     *
     * @return The id is the number of the page.
     */
    public int getPageId() {
        return pageId;
    }

    /**
     * The method returns the stored byte offset.
     *
     * @return The offset is the first byte of the range in the page.
     */
    public long getOffset() {
        return offset;
    }

    /**
     * The method returns the stored byte length.
     *
     * @return The length is the number of bytes in the range.
     */
    public long getLength() {
        return length;
    }
}
/*****************************************************************************************
 * End of Class: MemoryAddress.java 082526
 ****************************************************************************************/
