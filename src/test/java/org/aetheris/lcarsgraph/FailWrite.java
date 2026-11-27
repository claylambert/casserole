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

package org.aetheris.lcarsgraph;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.api.Trigger;

/**
 * The trigger blocks one delete so a commit can fail.
 */
public final class FailWrite implements Trigger {

    @Override
    public void init(Connection connection, String schema, String trigger, String table, boolean before, int type) {
    }

    @Override
    public void fire(Connection connection, Object[] oldRow, Object[] newRow) throws SQLException {
        throw new SQLException("blocked");
    }
}
