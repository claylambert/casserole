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

package org.casserole.format;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code FormatContext} type holds the settings for one format pass.
 * The type stores the column width, the table style, and the optional SGR sequences.
 * SGR is the select-graphic-rendition sequence that sets terminal color.
 * The type does not refer to SSH or to Spring.
 * SSH is the secure-shell protocol, and Spring is the application framework.
 * The column width starts at {@code 80}.
 * The table style starts at {@code ascii}.
 * Each SGR string starts empty.
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-734 docs/specs/common/stages.spec.md
// @aa-sdd PK-782 docs/specs/common/stages.spec.md
// @aa-sdd PK-345 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-343 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-350 docs/specs/java/org/casserole/format/format.spec.md
public class FormatContext {

    private int columns = 80;
    private String tableStyle = "ascii";
    private String sgrBorder = "";
    private String sgrHeader = "";
    private String sgrCell = "";
    private String sgrReset = "";

    /**
     * The {@code getColumns} method returns the wrap and table width.
     *
     * @return The column count stored for this pass.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * The {@code setColumns} method stores the wrap and table width.
     * If {@code columns} is below {@code 20}, the stored width is {@code 80}.
     *
     * @param columns The requested wrap and table width.
     */
    public void setColumns(int columns) {
        this.columns = columns < 20 ? 80 : columns;
    }

    /**
     * The {@code getTableStyle} method returns the table drawing style.
     * The expected values are {@code ascii} and {@code box}.
     *
     * @return The stored table style.
     */
    public String getTableStyle() {
        return tableStyle;
    }

    /**
     * The {@code setTableStyle} method stores the table drawing style.
     * If {@code tableStyle} is {@code null}, the stored style is {@code ascii}.
     *
     * @param tableStyle The style {@code ascii} or {@code box}, or {@code null} to store {@code ascii}.
     */
    public void setTableStyle(String tableStyle) {
        this.tableStyle = tableStyle == null ? "ascii" : tableStyle;
    }

    /**
     * The {@code getSgrBorder} method returns the SGR sequence for table borders.
     * A {@code null} stored value is returned as the empty string.
     *
     * @return The border sequence, or the empty string.
     */
    public String getSgrBorder() {
        return sgrBorder == null ? "" : sgrBorder;
    }

    /**
     * The {@code setSgrBorder} method stores the SGR sequence for table borders.
     *
     * @param sgrBorder The border sequence to store.
     */
    public void setSgrBorder(String sgrBorder) {
        this.sgrBorder = sgrBorder;
    }

    /**
     * The {@code getSgrHeader} method returns the SGR sequence for table header cells.
     * A {@code null} stored value is returned as the empty string.
     *
     * @return The header sequence, or the empty string.
     */
    public String getSgrHeader() {
        return sgrHeader == null ? "" : sgrHeader;
    }

    /**
     * The {@code setSgrHeader} method stores the SGR sequence for table header cells.
     *
     * @param sgrHeader The header sequence to store.
     */
    public void setSgrHeader(String sgrHeader) {
        this.sgrHeader = sgrHeader;
    }

    /**
     * The {@code getSgrCell} method returns the SGR sequence for table body cells.
     * A {@code null} stored value is returned as the empty string.
     *
     * @return The body-cell sequence, or the empty string.
     */
    public String getSgrCell() {
        return sgrCell == null ? "" : sgrCell;
    }

    /**
     * The {@code setSgrCell} method stores the SGR sequence for table body cells.
     *
     * @param sgrCell The body-cell sequence to store.
     */
    public void setSgrCell(String sgrCell) {
        this.sgrCell = sgrCell;
    }

    /**
     * The {@code getSgrReset} method returns the SGR sequence that clears color.
     * A {@code null} stored value is returned as the empty string.
     *
     * @return The reset sequence, or the empty string.
     */
    public String getSgrReset() {
        return sgrReset == null ? "" : sgrReset;
    }

    /**
     * The {@code setSgrReset} method stores the SGR sequence that clears color.
     *
     * @param sgrReset The reset sequence to store.
     */
    public void setSgrReset(String sgrReset) {
        this.sgrReset = sgrReset;
    }
}
/*****************************************************************************************
 * End of Class: FormatContext.java 082526
 ****************************************************************************************/
