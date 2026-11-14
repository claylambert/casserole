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

import org.casserole.format.FormatDetector;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code TableDetector} type matches a whole reply that is one GitHub-flavored pipe table.
 * A pipe table has a header row, a separator row, and at least one data row.
 * Every row must have the same number of columns.
 *
 * @author clayl (2026-08-25)
 */
public class TableDetector implements FormatDetector {

    /**
     * The {@code matches} method reports whether {@link #parse} accepts the reply.
     *
     * @param raw The full assistant text.
     * @return {@code true} when {@link #parse} returns a model.
     */
    @Override
    public boolean matches(String raw) {
        return parse(raw) != null;
    }

    /**
     * The {@code parse} method builds a {@link TableModel} from a whole pipe table.
     * A {@code null} reply returns {@code null}.
     * Carriage returns are normalized to line feeds, and blank lines are dropped.
     * Fewer than three non-blank lines returns {@code null}.
     * The first separator row must have at least one row before it and one row after it.
     * The header is the row immediately before that separator.
     * An empty header returns {@code null}.
     * A separator whose cell count differs from the header returns {@code null}.
     * A later row with no {@code |} returns {@code null}.
     * A later row whose cell count differs from the header returns {@code null}.
     * If no data row remains, the method returns {@code null}.
     *
     * @param raw The reply text.
     * @return The table model, or {@code null} when the reply is not a pipe table.
     */
    public static TableModel parse(String raw) {
        if (raw == null) {
            return null;
        }
        String[] lines = raw.replace("\r\n", "\n").replace('\r', '\n').split("\n", -1);
        List<String> nonempty = new ArrayList<String>();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim().length() > 0) {
                nonempty.add(lines[i].trim());
            }
        }
        if (nonempty.size() < 3) {
            return null;
        }
        int sep = -1;
        for (int i = 0; i < nonempty.size(); i++) {
            if (isSeparator(nonempty.get(i))) {
                sep = i;
                break;
            }
        }
        if (sep < 1 || sep >= nonempty.size() - 1) {
            return null;
        }
        List<String> header = splitRow(nonempty.get(sep - 1));
        if (header.size() < 1) {
            return null;
        }
        List<String> sepCells = splitRow(nonempty.get(sep));
        if (sepCells.size() != header.size()) {
            return null;
        }
        List<List<String>> rows = new ArrayList<List<String>>();
        for (int i = sep + 1; i < nonempty.size(); i++) {
            if (!nonempty.get(i).contains("|")) {
                return null;
            }
            List<String> cells = splitRow(nonempty.get(i));
            if (cells.size() != header.size()) {
                return null;
            }
            rows.add(cells);
        }
        if (rows.size() < 1) {
            return null;
        }
        return new TableModel(header, rows);
    }

    /**
     * The {@code isSeparator} method tests one trimmed line as a pipe-table separator.
     * The line is split into cells by {@link #splitRow}.
     * An empty cell list returns {@code false}.
     * Each cell must be non-empty after trimming and may contain only {@code -} and {@code :}.
     * Each cell must contain at least one {@code -}.
     * Any other cell returns {@code false}.
     *
     * @param line The trimmed line.
     * @return {@code true} when every cell is a separator cell.
     */
    static boolean isSeparator(String line) {
        List<String> cells = splitRow(line);
        if (cells.size() < 1) {
            return false;
        }
        for (int i = 0; i < cells.size(); i++) {
            String c = cells.get(i).trim();
            if (c.length() == 0) {
                return false;
            }
            for (int p = 0; p < c.length(); p++) {
                char ch = c.charAt(p);
                if (ch != '-' && ch != ':') {
                    return false;
                }
            }
            if (c.indexOf('-') < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * The {@code splitRow} method splits one pipe row into trimmed cells.
     * A leading {@code |} is not a cell.
     * A trailing {@code |} is not a cell.
     * Each remaining {@code |} starts the next cell.
     * The text of each cell has its surrounding spaces removed.
     *
     * @param line The row text.
     * @return The cells, in order.
     */
    static List<String> splitRow(String line) {
        List<String> cells = new ArrayList<String>();
        String t = line.trim();
        int start = 0;
        if (t.startsWith("|")) {
            start = 1;
        }
        int end = t.length();
        if (t.endsWith("|") && end > start) {
            end--;
        }
        int i = start;
        StringBuilder cur = new StringBuilder();
        while (i < end) {
            char ch = t.charAt(i);
            if (ch == '|') {
                cells.add(cur.toString().trim());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
            i++;
        }
        cells.add(cur.toString().trim());
        return cells;
    }
}
/*****************************************************************************************
 * End of Class: TableDetector.java 082526
 ****************************************************************************************/
