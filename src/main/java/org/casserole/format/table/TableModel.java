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

package org.casserole.format.table;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code TableModel} type holds a header row and the data rows of one table.
 * The column count is the size of the header.
 * The header and the row lists are the lists passed to the constructor.
 *
 * @author clayl (2026-08-25)
 */
public class TableModel {

    private final List<String> header;
    private final List<List<String>> rows;

    /**
     * The constructor stores the header cells and the data rows.
     *
     * @param header The header cells.
     * @param rows The data rows.
     */
    public TableModel(List<String> header, List<List<String>> rows) {
        this.header = header;
        this.rows = rows;
    }

    /**
     * The {@code getHeader} method returns the header cells.
     *
     * @return The header list stored by the constructor.
     */
    public List<String> getHeader() {
        return header;
    }

    /**
     * The {@code getRows} method returns the data rows.
     *
     * @return The row list stored by the constructor.
     */
    public List<List<String>> getRows() {
        return rows;
    }

    /**
     * The {@code getColumnCount} method returns the number of header cells.
     *
     * @return The size of the header list.
     */
    public int getColumnCount() {
        return header.size();
    }

    /**
     * The {@code empty} method returns a model with no header cells and no data rows.
     *
     * @return A model whose header and row lists are empty.
     */
    public static TableModel empty() {
        return new TableModel(new ArrayList<String>(), new ArrayList<List<String>>());
    }
}
/*****************************************************************************************
 * End of Class: TableModel.java 082526
 ****************************************************************************************/
