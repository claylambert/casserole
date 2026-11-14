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

import java.nio.file.Path;

/**
 * The SnapshotOptions type holds the choices for one snapshot.
 * The object stores the region ids to include.
 * The object stores whether writes stop during the capture.
 * The object stores whether the capture is copy-on-write.
 * Copy-on-write means later writes keep the captured bytes unchanged.
 * The object stores a storage path.
 *
 * The current engine does not run a capture from these options.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#SnapshotOptions roux-design.md
 *
 * @author clayl (2026-08-25)
 */
public class SnapshotOptions {

    private final String[] targetRegionIds;
    private final boolean freezeWritesDuringCapture;
    private final boolean copyOnWrite;
    private final Path storagePath;

    /**
     * The constructor stores the region ids, the freeze flag, the copy-on-write flag, and the path.
     *
     * @param targetRegionIds The ids name the regions to include.
     * @param freezeWritesDuringCapture The flag is true when writes stop during the capture.
     * @param copyOnWrite The flag is true when the capture keeps the original bytes after later writes.
     * @param storagePath The path is the place the capture would be stored.
     */
    public SnapshotOptions(String[] targetRegionIds, boolean freezeWritesDuringCapture, boolean copyOnWrite,
            Path storagePath) {
        this.targetRegionIds = targetRegionIds;
        this.freezeWritesDuringCapture = freezeWritesDuringCapture;
        this.copyOnWrite = copyOnWrite;
        this.storagePath = storagePath;
    }

    /**
     * The method returns the stored region ids.
     *
     * @return The array is the ids the constructor stored.
     */
    public String[] getTargetRegionIds() {
        return targetRegionIds;
    }

    /**
     * The method returns the stored freeze flag.
     *
     * @return The flag is true when writes stop during the capture.
     */
    public boolean isFreezeWritesDuringCapture() {
        return freezeWritesDuringCapture;
    }

    /**
     * The method returns the stored copy-on-write flag.
     *
     * @return The flag is true when the capture keeps the original bytes after later writes.
     */
    public boolean isCopyOnWrite() {
        return copyOnWrite;
    }

    /**
     * The method returns the stored storage path.
     *
     * @return The path is the place the capture would be stored.
     */
    public Path getStoragePath() {
        return storagePath;
    }
}
/*****************************************************************************************
 * End of Class: SnapshotOptions.java 082526
 ****************************************************************************************/
