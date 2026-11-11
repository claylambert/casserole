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

// @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
// @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
// @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
