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
 * The StoredCountEstimator returns a count the caller already holds.
 * The object stores a user name.
 * The object stores that integer.
 * {@link #estimate} does not read the {@link Transcript}.
 * The method does not call {@link HeuristicTokenCounter}.
 *
 * // @aa-sdd PK-1105 docs/specs/common/formal-plan.spec.md
 *
 * @author clayl (2026-11-02)
 */
// @aa-sdd PK-1325 docs/specs/java/org/roux/token/token.spec.md
// @aa-sdd PK-639 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-549 docs/specs/java/org/roux/token/token.spec.md
public final class StoredCountEstimator implements TokenEstimator {

    private final String user;
    private final int used;

    /**
     * The constructor stores the user name and the count.
     * A null user name is stored as an empty string.
     *
     * @param user The user is the name the caller passed.
     * @param used The used value is the integer the caller already counted.
     */
    public StoredCountEstimator(String user, int used) {
        this.user = user == null ? "" : user;
        this.used = used;
    }

    /**
     * The method returns the stored user name.
     *
     * @return The name is the user the constructor stored.
     */
    public String user() {
        return user;
    }

    /**
     * The method returns the stored integer.
     * The transcript is not read.
     *
     * @param transcript The transcript is ignored.
     * @return The integer is the count the constructor stored.
     */
    @Override
    public int estimate(Transcript transcript) {
        return used;
    }
}
/*****************************************************************************************
 * End of Class: StoredCountEstimator.java 110226
 ****************************************************************************************/
