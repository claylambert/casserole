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

package org.roux.token;

import org.springframework.stereotype.Component;

// @aa-sdd PK-0791 docs/specs/java/org/roux/roux.spec.md
/**
 * The HeuristicTokenCounter estimates a message body from its length.
 * A null body returns {@code 1}.
 * An empty body returns {@code 1}.
 * Any other body returns {@code (length + 3) / 4}.
 *
 * That division uses integer arithmetic.
 * The result of a non-empty body is at least {@code 1}.
 *
 * @aa-sdd PK-2467 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
@Component
public class HeuristicTokenCounter implements TokenCounter {

    /**
     * The method counts one message body.
     * A null body returns {@code 1}.
     * An empty body returns {@code 1}.
     * Any other body returns {@code (length + 3) / 4}.
     *
     * @aa-sdd PK-2467 docs/specs/java/org/roux/roux.spec.md
     *
     * @param text The text is the message body, or null.
     * @return The count is at least {@code 1}.
     */
    @Override
    public int count(String text) {
        if (text == null || text.length() == 0) {
            return 1;
        }
        return (text.length() + 3) / 4;
    }
}
/*****************************************************************************************
 * End of Class: HeuristicTokenCounter.java 082526
 ****************************************************************************************/
