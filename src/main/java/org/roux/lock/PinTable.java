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

package org.roux.lock;

import java.util.HashMap;
import java.util.Map;

// @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
/**
 * The PinTable counts how many reads are using each page.
 * A pin is that in-flight count.
 * {@link #pin} raises the count.
 * {@link #unpin} lowers the count and drops a zero.
 * {@link #waitUnpinned} waits until the count is zero or the wait times out.
 *
 * A later spill can wait on that count.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#PinTable roux-v1.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-706 docs/specs/common/roux.spec.md
// @aa-sdd PK-809 docs/specs/common/roux.spec.md
// @aa-sdd PK-827 docs/specs/common/roux.spec.md
public class PinTable {

    private final Map<Integer, Integer> counts;

    /**
     * The constructor starts an empty table.
     */
    public PinTable() {
        this.counts = new HashMap<Integer, Integer>();
    }

    /**
     * The method raises the pin count for one page.
     * A missing page starts at zero and then becomes one.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#PinTable roux-v1.md
     *
     * @param pageId The page id is the page being read.
     */
    public synchronized void pin(int pageId) {
        Integer cur = counts.get(Integer.valueOf(pageId));
        int n = cur == null ? 0 : cur.intValue();
        counts.put(Integer.valueOf(pageId), Integer.valueOf(n + 1));
    }

    /**
     * The method lowers the pin count for one page.
     * A missing page is left unchanged.
     * A count that falls to zero or below is removed.
     *
     * @param pageId The page id is the page that finished a read.
     */
    public synchronized void unpin(int pageId) {
        Integer cur = counts.get(Integer.valueOf(pageId));
        if (cur == null) {
            return;
        }
        int n = cur.intValue() - 1;
        if (n <= 0) {
            counts.remove(Integer.valueOf(pageId));
        } else {
            counts.put(Integer.valueOf(pageId), Integer.valueOf(n));
        }
    }

    /**
     * The method returns the pin count for one page.
     * A missing page returns {@code 0}.
     *
     * @param pageId The page id is the page to inspect.
     * @return The count is the number of in-flight reads.
     */
    public synchronized int count(int pageId) {
        Integer cur = counts.get(Integer.valueOf(pageId));
        return cur == null ? 0 : cur.intValue();
    }

    /**
     * The method waits until the page has no pins.
     * The wait sleeps for {@code 5} milliseconds between checks.
     * The wait stops when the deadline passes.
     * An interrupt stops the wait and sets the interrupt flag again.
     *
     * @param pageId The page id is the page to wait for.
     * @param timeoutMs The timeout is the maximum wait in milliseconds.
     * @return The flag is true when the count reached zero.
     */
    public boolean waitUnpinned(int pageId, long timeoutMs) {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (count(pageId) > 0) {
            if (System.currentTimeMillis() >= deadline) {
                return false;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return true;
    }
}
/*****************************************************************************************
 * End of Class: PinTable.java 082526
 ****************************************************************************************/
