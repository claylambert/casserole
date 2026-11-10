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

package org.aetheris.vault;

// @aa-sdd PK-0833 PK-0880 PK-0927 PK-0974 PK-1021 PK-1068 PK-1115 PK-1162 PK-1209 PK-1256 PK-1303 PK-1350 PK-1397 PK-1444 PK-1491 PK-1538 PK-1586 PK-1633 PK-1680 PK-1727 PK-1774 PK-1821 PK-1868 PK-1915 PK-1962 PK-2009 PK-2057 PK-2104 PK-2151 PK-2198 PK-2246 PK-2294 PK-2342 PK-2389 PK-2436 PK-2484 PK-2531 PK-2578 PK-2625 PK-2673 PK-2720 PK-2768 PK-2815 PK-2862 PK-2909 PK-2956 PK-3003 PK-3050 PK-3097 PK-3144 PK-3192 PK-3239 PK-3286 PK-3334 PK-3381 PK-3428 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
/**
 * The {@code CellRecord} class is one cell on a canvas.
 * A burner cell is an ephemeral cell, and an ephemeral cell stores null text columns.
 * The {@code id} and {@code canvasId} fields start empty.
 * The {@code branchId} field starts as {@code main}.
 * The {@code state} field starts as {@link CellState#INCLUDED}.
 * The {@code userText} and {@code assistantText} fields start empty.
 * // @aa-sdd PK-3818 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-4416 PK-4423 docs/specs/java/org/aetheris/vault/vault.spec.md
 *
 * @author clayl (2026-09-02)
 */
public final class CellRecord {

    public String id = "";
    public String canvasId = "";
    public int turnIndex;
    public String parentCellId = "";
    public String branchId = "main";
    public int columnPosition;
    public String executionTarget = "";
    public CellState state = CellState.INCLUDED;
    public boolean collapsed;
    /**
     * The flag is the packed-prefix lock on this cell.
     * An old row that has no stored lock reads as unlocked.
     */
    public boolean headLock;
    public boolean ephemeral;
    /**
     * The flag is false when the stored state token is not one of the four states.
     * A false flag leaves the cell out of the source list.
     */
    public boolean readable = true;
    /**
     * The id groups cells that were sent together.
     * An empty id means the cell stands alone.
     * Cells that share a non-empty id are one group.
     */
    public String groupId = "";
    /**
     * The status is the last run of this cell.
     * An empty value means the cell has not run.
     * The stored tokens are {@code COMPLETED}, {@code FAILED}, and {@code CANCELLED}.
     */
    public String executionStatus = "";
    public String userText = "";
    public String assistantText = "";
    public long createdMs;
}
/*****************************************************************************************
 * End of Class: CellRecord.java 090226
 ****************************************************************************************/
