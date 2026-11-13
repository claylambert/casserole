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

import java.util.concurrent.Future;

// @aa-sdd L40 app-design.md
/**
 * The {@code LaminaHandle} type is the cancel and status token for work that {@link Lamina} has submitted.
 * The handle holds the {@code Future} returned by the pool.
 * A {@code Future} is the Java object that tracks one submitted task.
 * If that object is missing, cancel does nothing and the work is reported as finished.
 *
 * @author clayl (2026-08-30)
 */
// @aa-sdd PK-352 docs/specs/java/org/casserole/lamina/lamina.spec.md
// @aa-sdd PK-353 docs/specs/java/org/casserole/lamina/lamina.spec.md
// @aa-sdd PK-355 docs/specs/java/org/casserole/lamina/lamina.spec.md
// @aa-sdd PK-356 docs/specs/java/org/casserole/lamina/lamina.spec.md
// @aa-sdd GK-1707 aetheris-stages-1-3.finalize.md
public class LaminaHandle {

    private final Future<?> future;

    /**
     * The constructor stores the pool's {@code Future} for one submitted task.
     *
     * @param future The {@code Future} from the executor, or {@code null} when no task was tracked.
     */
    public LaminaHandle(Future<?> future) {
        this.future = future;
    }

    /**
     * The {@code cancel} method asks the pool to cancel the task.
     * If the stored {@code Future} is {@code null}, the method returns {@code false} and does not cancel anything.
     * If {@code mayInterrupt} is {@code true}, the pool may interrupt the worker thread.
     * If {@code mayInterrupt} is {@code false}, the pool is asked to cancel without interrupting the worker.
     *
     * @param mayInterrupt {@code true} when the worker thread may be interrupted, or {@code false} when it may not.
     * @return {@code true} when cancellation was issued, or {@code false} when there is no task to cancel.
     */
    public boolean cancel(boolean mayInterrupt) {
        if (future == null) {
            return false;
        }
        return future.cancel(mayInterrupt);
    }

    /**
     * The {@code isDone} method reports whether the work has finished.
     * If the stored {@code Future} is {@code null}, the method returns {@code true}.
     * Otherwise the method returns the {@code Future}'s own finished flag.
     *
     * @return {@code true} when the work has finished or when no task was tracked.
     */
    public boolean isDone() {
        if (future == null) {
            return true;
        }
        return future.isDone();
    }
}
/*****************************************************************************************
 * End of Class: LaminaHandle.java 083026
 ****************************************************************************************/
