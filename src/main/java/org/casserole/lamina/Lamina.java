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

package org.casserole.lamina;

// @aa-sdd L40 app-design.md
/**
 * The {@code Lamina} type is the thread-executor model for named work.
 * A caller submits a {@link LaminaTask} onto the {@link Lane} that task names.
 * A lane is one named pool of worker threads.
 * The caller receives a {@link LaminaHandle} that can cancel that work or report whether it has finished.
 * {@link #shutdown()} stops the pools.
 * A second call to {@link #shutdown()} does not start the pools again.
 *
 * @author clayl (2026-08-30)
 */
public interface Lamina {

    /**
     * The {@code submit} method places one named task on the lane that task names.
     *
     * @param task The named work to run.
     * @return The handle that can cancel the work or report whether it has finished.
     */
    LaminaHandle submit(LaminaTask task);

    /**
     * The {@code shutdown} method stops the pools.
     * A second call does not start the pools again.
     */
    void shutdown();
}
/*****************************************************************************************
 * End of Class: Lamina.java 083026
 ****************************************************************************************/
