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

import java.util.ArrayList;
import java.util.List;

// @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/swing/swing.spec.md
/**
 * The {@code CanvasRecord} class is one canvas and the cells that belong to it.
 * The {@code id} field is the canvas id.
 * The {@code workspaceId} field is the workspace that owns the canvas.
 * The {@code name} field starts as {@code Canvas}.
 * The {@code cellOrder} list holds cell ids in the stored comma-separated order.
 * The {@code cells} list holds the {@link CellRecord} rows for that canvas.
 * A caller fills both lists.
 * The class does not open a database.
 *
 * @author clayl (2026-09-02)
 */
public final class CanvasRecord {

    public String id = "";
    public String workspaceId = "";
    public String name = "Canvas";
    public final List<String> cellOrder = new ArrayList<String>();
    public final List<CellRecord> cells = new ArrayList<CellRecord>();
    public long createdMs;
}
/*****************************************************************************************
 * End of Class: CanvasRecord.java 090226
 ****************************************************************************************/
