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
 * The BitAddress is a view of one range inside a page.
 * The object stores the region id.
 * The object stores the page id.
 * The object stores the byte offset of that range.
 * The object stores a bit offset inside the first byte.
 * The object stores the length of the span in bits.
 *
 * The payload bytes stay on the page.
 * This object does not copy those bytes.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#BitAddress roux-design.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-488 docs/specs/java/org/roux/model/model.spec.md
public class BitAddress {

    private final String regionId;
    private final int pageId;
    private final long byteOffset;
    private final int bitOffsetWithinByte;
    private final long bitLength;

    /**
     * The constructor stores the region, the page, the byte offset, the bit offset, and the bit length.
     *
     * @param regionId The region id is the name of the region.
     * @param pageId The page id is the number of the page.
     * @param byteOffset The byte offset is the start of the range in the page.
     * @param bitOffsetWithinByte The bit offset is the bit inside that first byte.
     * @param bitLength The bit length is the number of bits in the span.
     */
    public BitAddress(String regionId, int pageId, long byteOffset, int bitOffsetWithinByte, long bitLength) {
        this.regionId = regionId;
        this.pageId = pageId;
        this.byteOffset = byteOffset;
        this.bitOffsetWithinByte = bitOffsetWithinByte;
        this.bitLength = bitLength;
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
     * @return The offset is the start of the range in the page.
     */
    public long getByteOffset() {
        return byteOffset;
    }

    /**
     * The method returns the stored bit offset inside the first byte.
     *
     * @return The offset is the bit position inside that byte.
     */
    public int getBitOffsetWithinByte() {
        return bitOffsetWithinByte;
    }

    /**
     * The method returns the stored bit length.
     *
     * @return The length is the number of bits in the span.
     */
    public long getBitLength() {
        return bitLength;
    }
}
/*****************************************************************************************
 * End of Class: BitAddress.java 082526
 ****************************************************************************************/
