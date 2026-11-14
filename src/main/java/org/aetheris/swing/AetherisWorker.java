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

package org.aetheris.swing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * The {@code AetherisWorker} class is the single background thread the shell uses for database work and AMWP client reads.
 * AMWP is the framed protocol on the connection.
 * The thread is not the server executor in {@link org.casserole.lamina.Lamina}.
 * The thread is not the Swing event thread.
 * The event thread is the thread that paints the window.
 * // @aa-sdd G-12 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd PK-29 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-30 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-34 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-39 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-736 docs/specs/common/stages.spec.md
// @aa-sdd PK-43 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-744 docs/specs/common/stages.spec.md
// @aa-sdd PK-44 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-46 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-60 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-61 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-918 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-600 docs/specs/common/stages.spec.md
// @aa-sdd PK-62 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-920 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-361 docs/specs/java/org/casserole/lamina/lamina.spec.md
// @aa-sdd PK-117 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-729 docs/specs/common/server.spec.md
// @aa-sdd PK-63 docs/specs/java/org/aetheris/swing/swing.spec.md
public final class AetherisWorker {

    private final ExecutorService executor;

    /**
     * The constructor starts one daemon thread named {@code aetheris-worker}.
     * The thread comes from a single-thread executor.
     */
    public AetherisWorker() {
        this.executor = Executors.newSingleThreadExecutor(new WorkerFactory());
    }

    /**
     * The method queues one task on the worker thread.
     * A null task is ignored.
     *
     * @param task The task is database work or an AMWP client read, and it may be null.
     */
    public void submit(Runnable task) {
        if (task == null) {
            return;
        }
        executor.execute(task);
    }

    /**
     * The method stops the worker when the window closes.
     * Running work is interrupted.
     * The method waits up to two seconds for the thread to end.
     * An interrupt while waiting restores the interrupt flag on the calling thread.
     */
    public void shutdown() {
        executor.shutdownNow();
        try {
            executor.awaitTermination(2L, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    // @aa-sdd L40 aetheris-plan-stage-1.001.md
    /**
     * The {@code WorkerFactory} class names the single worker thread.
     * The thread is a daemon, so it does not keep the process alive.
     */
    private static final class WorkerFactory implements ThreadFactory {
        /**
         * The method builds the daemon thread named {@code aetheris-worker}.
         *
         * @param r The runnable is the executor task the thread will run.
         * @return The return value is that daemon thread.
         */
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "aetheris-worker");
            t.setDaemon(true);
            return t;
        }
    }
}
/*****************************************************************************************
 * End of Class: AetherisWorker.java 090226
 ****************************************************************************************/
