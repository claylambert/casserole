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

package org.casserole.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// @aa-sdd L40 app-design.md
/**
 * The {@code CasseroleLog} type is the named Log4j2 accessor for this program.
 * Log4j2 is the logging library that writes the program log.
 * The type does not use Lombok.
 * Lombok is a library that generates constructors and accessors, and this type does not use it.
 * A caller asks for a logger by class or by a name string.
 *
 * @author clayl (2026-08-30)
 */
// @aa-sdd PK-400 docs/specs/java/org/casserole/log/log.spec.md
public final class CasseroleLog {

    /**
     * The constructor is private so a caller uses {@link #get(Class)} or {@link #get(String)}.
     */
    private CasseroleLog() {
    }

    /**
     * The {@code get} method returns the Log4j2 logger whose name is the class name.
     *
     * @param type The class whose name becomes the logger name.
     * @return The Log4j2 logger for that class.
     */
    public static Logger get(Class<?> type) {
        return LogManager.getLogger(type);
    }

    /**
     * The {@code get} method returns the Log4j2 logger for a name.
     *
     * @param name The logger name.
     * @return The Log4j2 logger for that name.
     */
    public static Logger get(String name) {
        return LogManager.getLogger(name);
    }
}
/*****************************************************************************************
 * End of Class: CasseroleLog.java 083026
 ****************************************************************************************/
