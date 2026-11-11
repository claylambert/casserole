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
 * // @aa-sdd PK-3808 PK-4412 PK-4418 docs/specs/java/org/aetheris/swing/swing.spec.md
 *
 * @author clayl (2026-09-02)
 */
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

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
