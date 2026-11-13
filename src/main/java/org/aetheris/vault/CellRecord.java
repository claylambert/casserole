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

// @aa-sdd PK-1223 docs/specs/java/org/aetheris/vault/vault.spec.md FINESSE:f-222
/**
 * The {@code CellRecord} class is one cell on a canvas.
 * A burner cell is an ephemeral cell, and an ephemeral cell stores null text columns.
 * The {@code id} and {@code canvasId} fields start empty.
 * The {@code branchId} field starts as {@code main}.
 * The {@code state} field starts as {@link CellState#INCLUDED}.
 * The {@code userText} and {@code assistantText} fields start empty.
 * // @aa-sdd S1-09 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd PK-786 docs/specs/common/grok.spec.md
// @aa-sdd PK-686 docs/specs/common/grok.spec.md
// @aa-sdd PK-126 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-127 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-132 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-751 docs/specs/common/stages.spec.md
// @aa-sdd PK-752 docs/specs/common/stages.spec.md
// @aa-sdd PK-753 docs/specs/common/stages.spec.md
// @aa-sdd PK-754 docs/specs/common/stages.spec.md
// @aa-sdd PK-754 docs/specs/common/stages.spec.md
// @aa-sdd PK-755 docs/specs/common/stages.spec.md
// @aa-sdd PK-756 docs/specs/common/stages.spec.md
// @aa-sdd PK-134 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-138 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-891 docs/specs/common/stages.spec.md
// @aa-sdd PK-142 docs/specs/java/org/aetheris/vault/vault.spec.md
// @aa-sdd PK-145 docs/specs/java/org/aetheris/vault/vault.spec.md
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
