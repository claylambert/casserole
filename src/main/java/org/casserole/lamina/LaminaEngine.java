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

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.casserole.log.CasseroleLog;
import org.apache.logging.log4j.Logger;

// @aa-sdd L40 app-design.md
/**
 * The {@code LaminaEngine} type is the live set of {@link Lane} pools.
 * {@link Lane#SSH_IO} is a cached pool, so each connection can receive its own thread.
 * {@link Lane#LLM_EXEC} is a fixed pool of four threads.
 * {@link Lane#GENERAL} is a fixed pool of two threads.
 * {@link Lane#TOOL_EXEC} is a fixed pool of one thread.
 * {@link Lane#POOL_IO} is a cached pool.
 * The pools are ordinary platform threads.
 * The pools are not virtual threads.
 * {@link #submit(LaminaTask)} places a task on the pool for that task's lane and returns a {@link LaminaHandle}.
 * A {@link LaminaHandle} wraps the {@code Future} that tracks the task.
 *
 * @author clayl (2026-08-30)
 */
// @aa-sdd PK-738 docs/specs/common/stages.spec.md
// @aa-sdd PK-358 docs/specs/java/org/casserole/lamina/lamina.spec.md
// @aa-sdd PK-727 docs/specs/common/server.spec.md
public class LaminaEngine implements Lamina {

    private static final Logger LOG = CasseroleLog.get(LaminaEngine.class);

    private final Map<Lane, ExecutorService> pools;
    private final LaminaMetrics metrics;
    private volatile boolean down;

    /**
     * The constructor builds one pool for each {@link Lane} and starts the metrics counters.
     * The {@code metricsEnabled} flag is the {@code casserole.lamina.metrics} setting.
     * {@link Lane#SSH_IO} uses a cached pool whose threads are named {@code lamina-ssh-io-} plus a number.
     * {@link Lane#LLM_EXEC} uses a fixed pool of four threads named {@code lamina-llm-exec-} plus a number.
     * {@link Lane#GENERAL} uses a fixed pool of two threads named {@code lamina-general-} plus a number.
     * {@link Lane#TOOL_EXEC} uses a fixed pool of one thread named {@code lamina-tool-exec-} plus a number.
     * {@link Lane#POOL_IO} uses a cached pool whose threads are named {@code lamina-pool-io-} plus a number.
     * Each worker thread is not a daemon thread.
     * The engine writes {@code Lamina started metrics=} and the flag to the info log.
     * The engine is not shut down after this constructor returns.
     *
     * @param metricsEnabled {@code true} when {@link LaminaMetrics} should count work, or {@code false} when counting stays off.
     */
    public LaminaEngine(boolean metricsEnabled) {
        this.metrics = new LaminaMetrics(metricsEnabled);
        this.pools = new EnumMap<Lane, ExecutorService>(Lane.class);
        this.pools.put(Lane.SSH_IO, Executors.newCachedThreadPool(namedFactory("ssh-io")));
        this.pools.put(Lane.LLM_EXEC, Executors.newFixedThreadPool(4, namedFactory("llm-exec")));
        this.pools.put(Lane.GENERAL, Executors.newFixedThreadPool(2, namedFactory("general")));
        this.pools.put(Lane.TOOL_EXEC, Executors.newFixedThreadPool(1, namedFactory("tool-exec")));
        this.pools.put(Lane.POOL_IO, Executors.newCachedThreadPool(namedFactory("pool-io")));
        this.down = false;
        LOG.info("Lamina started metrics={}", metricsEnabled);
    }

    /**
     * The {@code submit} method places one task on the pool for that task's lane.
     * If {@code task} is {@code null}, the method throws {@code IllegalArgumentException} with the message {@code task}.
     * If {@link #shutdown()} has already run, the method throws {@code IllegalStateException} with the message {@code Lamina is shut down}.
     * If the lane has no pool, the method throws {@code IllegalStateException} with the message {@code no pool for } followed by the lane.
     * A successful submit calls {@link LaminaMetrics#recordSubmit()} before the body starts.
     * The returned {@link LaminaHandle} wraps the {@code Future} for that body.
     *
     * @param task The named work to run.
     * @return The handle that can cancel the work or report whether it has finished.
     * @throws IllegalArgumentException The task was {@code null}.
     * @throws IllegalStateException The engine is shut down, or the task's lane has no pool.
     */
    @Override
    public LaminaHandle submit(LaminaTask task) {
        if (task == null) {
            throw new IllegalArgumentException("task");
        }
        if (down) {
            throw new IllegalStateException("Lamina is shut down");
        }
        ExecutorService exec = pools.get(task.getLane());
        if (exec == null) {
            throw new IllegalStateException("no pool for " + task.getLane());
        }
        metrics.recordSubmit();
        final LaminaMetrics laneMetrics = metrics;
        /**
         * The runnable is the body the pool actually starts.
         * A normal finish calls {@link LaminaMetrics#recordComplete()}.
         * A {@code RuntimeException} calls {@link LaminaMetrics#recordFail()} and is thrown again.
         */
        Future<?> future = exec.submit(new Runnable() {
            // @aa-sdd L40 app-design.md
            @Override
            public void run() {
                try {
                    task.getWork().run();
                    laneMetrics.recordComplete();
                } catch (RuntimeException ex) {
                    laneMetrics.recordFail();
                    throw ex;
                }
            }
        });
        return new LaminaHandle(future);
    }

    /**
     * The {@code shutdown} method stops every pool.
     * The engine is marked shut down before the pools are stopped, so a later {@link #submit(LaminaTask)} fails.
     * Each pool is stopped with {@code shutdownNow}, which interrupts running workers.
     * The method then waits up to five seconds for each pool to finish.
     * If that wait is interrupted, the method sets the interrupt flag on the current thread and continues with the next pool.
     * The engine writes {@code Lamina stopped} to the info log after every pool has been asked to stop.
     * A second call stops the pools again and does not create new pools.
     */
    @Override
    public void shutdown() {
        down = true;
        for (ExecutorService exec : pools.values()) {
            exec.shutdownNow();
            try {
                exec.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        LOG.info("Lamina stopped");
    }

    /**
     * The {@code namedFactory} method builds a {@code ThreadFactory} for one lane name.
     * A {@code ThreadFactory} is the Java object that creates worker threads.
     * Each thread is named {@code lamina-}, then the lane name, then {@code -}, then a number that starts at one.
     * The thread is not a daemon thread.
     *
     * @param lane The short lane name placed in the thread name.
     * @return The factory that names the threads for that lane.
     */
    private static ThreadFactory namedFactory(final String lane) {
        final AtomicInteger n = new AtomicInteger(1);
        /**
         * The factory creates one non-daemon thread for each worker.
         * The thread name is {@code lamina-}, the lane name, {@code -}, and the next number.
         */
        return new ThreadFactory() {
            // @aa-sdd L40 app-design.md
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "lamina-" + lane + "-" + n.getAndIncrement());
                thread.setDaemon(false);
                return thread;
            }
        };
    }
}
/*****************************************************************************************
 * End of Class: LaminaEngine.java 083026
 ****************************************************************************************/
