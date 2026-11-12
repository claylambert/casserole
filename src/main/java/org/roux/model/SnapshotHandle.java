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
 * The SnapshotHandle is the id of one stored snapshot.
 * The object stores the snapshot id.
 * The object stores a label.
 * The object stores the time the snapshot was created, in epoch milliseconds.
 *
 * A caller would pass this handle to restore the snapshot.
 * The current engine does not create a handle.
 *
 * @aa-sdd PK-0758 PK-0759 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
public class SnapshotHandle {

    private final String snapshotId;
    private final String label;
    private final long timestampEpochMs;

    /**
     * The constructor stores the snapshot id, the label, and the creation time.
     *
     * @param snapshotId The snapshot id is the text that identifies the snapshot.
     * @param label The label is the name the caller gave the snapshot.
     * @param timestampEpochMs The time is the epoch milliseconds when the snapshot was created.
     */
    public SnapshotHandle(String snapshotId, String label, long timestampEpochMs) {
        this.snapshotId = snapshotId;
        this.label = label;
        this.timestampEpochMs = timestampEpochMs;
    }

    /**
     * The method returns the stored snapshot id.
     *
     * @return The id is the text that identifies the snapshot.
     */
    public String getSnapshotId() {
        return snapshotId;
    }

    /**
     * The method returns the stored label.
     *
     * @return The label is the name the caller gave the snapshot.
     */
    public String getLabel() {
        return label;
    }

    /**
     * The method returns the stored creation time.
     *
     * @return The time is the epoch milliseconds when the snapshot was created.
     */
    public long getTimestampEpochMs() {
        return timestampEpochMs;
    }
}
/*****************************************************************************************
 * End of Class: SnapshotHandle.java 082526
 ****************************************************************************************/
