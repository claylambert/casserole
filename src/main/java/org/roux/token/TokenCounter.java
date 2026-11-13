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

/**
 * The TokenCounter is the count stored with a message when it is written.
 * The frame stores that count.
 * The index stores that same count.
 * The count is at least 1.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#TokenCounter roux-v1.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-541 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-546 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-559 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-808 docs/specs/common/roux.spec.md
// @aa-sdd PK-565 docs/specs/java/org/roux/token/token.spec.md
public interface TokenCounter {

    /**
     * The method counts one message body.
     *
     * @param text The text is the message body.
     * @return The count is at least 1.
     */
    int count(String text);
}
/*****************************************************************************************
 * End of Class: TokenCounter.java 082526
 ****************************************************************************************/
