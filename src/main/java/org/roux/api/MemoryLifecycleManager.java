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

import java.nio.file.Path;

import org.roux.model.OperationResult;
import org.roux.model.SnapshotHandle;
import org.roux.model.SnapshotOptions;

/**
 * The MemoryLifecycleManager is the call set for snapshots and shutdown.
 * {@link #createAtomicSnapshot} does not create a snapshot.
 * {@link #restoreSnapshot} refuses the restore.
 * {@link #exportHeapDump} refuses the export.
 * {@link #shutdown} closes every open region.
 *
 * A pin does not stop shutdown.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#MemoryLifecycleManager roux-design.md
 *
 * @author clayl (2026-08-25)
 */
public interface MemoryLifecycleManager {

    /**
     * The method does not create a snapshot.
     *
     * @param options The options are the snapshot choices.
     * @param label The label is the name the caller gave the snapshot.
     * @return The handle is null.
     */
    SnapshotHandle createAtomicSnapshot(SnapshotOptions options, String label);

    /**
     * The method refuses to restore a snapshot.
     * The refusal status is {@link org.roux.model.OperationStatus#UNSUPPORTED}.
     *
     * @param handle The handle is the snapshot to restore.
     * @return The result is a failure.
     */
    OperationResult restoreSnapshot(SnapshotHandle handle);

    /**
     * The method refuses to export a heap dump.
     * The refusal status is {@link org.roux.model.OperationStatus#UNSUPPORTED}.
     *
     * @param destinationPath The path is the file the dump would have used.
     * @return The result is a failure.
     */
    OperationResult exportHeapDump(Path destinationPath);

    /**
     * The method closes every open region.
     * A pin does not stop the close.
     *
     * @param forceUnmap The flag asks for a close that ignores pins.
     */
    void shutdown(boolean forceUnmap);
}
/*****************************************************************************************
 * End of Class: MemoryLifecycleManager.java 082526
 ****************************************************************************************/
