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

// @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md FINESSE:f-170
/**
 * The HeuristicTokenCounter estimates a message body from its length.
 * A null body returns {@code 1}.
 * An empty body returns {@code 1}.
 * Any other body returns {@code (length + 3) / 4}.
 *
 * That division uses integer arithmetic.
 * The result of a non-empty body is at least {@code 1}.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#HeuristicTokenCounter roux-v1.md
 *
 * @author clayl (2026-08-25)
 */
@Component
// @aa-sdd PK-535 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-536 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-537 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-538 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-539 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-778 docs/specs/common/stages.spec.md
// @aa-sdd PK-540 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-1119 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-637 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-541 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-982 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-640 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-546 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd GK-1920 aetheris-pre-plan-f-148.001.md
// @aa-sdd PK-983 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-560 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-561 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-562 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-563 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-564 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-810 docs/specs/common/roux.spec.md
// @aa-sdd PK-566 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-638 docs/specs/common/formal-plan.spec.md
public class HeuristicTokenCounter implements TokenCounter {

    /**
     * The method counts one message body.
     * A null body returns {@code 1}.
     * An empty body returns {@code 1}.
     * Any other body returns {@code (length + 3) / 4}.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#HeuristicTokenCounter roux-v1.md
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
