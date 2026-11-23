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

package org.roux;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * The DeadPagePrune type sweeps open regions on one thread.
 * The thread starts with the context and stops with the context.
 * A refused interval does not start the thread.
 * An enabled flag that is false does not start the thread.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#DeadPagePrune roux-dead-page-prune.md
 *
 * @author clayl (2026-11-22)
 */
@Component
// @aa-sdd PK-1469 docs/specs/java/org/roux/roux.spec.md
class DeadPagePrune implements SmartLifecycle {

    private static final Logger LOG = LogManager.getLogger(DeadPagePrune.class);
    private static final String INTERVAL = "prune interval below 1 minute";

    private final RouxEngine engine;
    private final RouxProperties properties;
    private final Object gate;
    private final Sweep sweep;
    private volatile boolean stop;
    private volatile boolean running;
    private boolean intervalLogged;
    private Thread thread;

    /**
     * The constructor stores the engine and the settings.
     *
     * @param engine The engine releases the dead slots.
     * @param properties The properties hold the prune block.
     */
    public DeadPagePrune(RouxEngine engine, RouxProperties properties) {
        this.engine = engine;
        this.properties = properties;
        this.gate = new Object();
        this.sweep = new Sweep();
    }

    /**
     * The method reports a stop request.
     * The engine reads the flag between slots.
     *
     * @return The flag is true after stop.
     */
    boolean stopRequested() {
        return stop;
    }

    /**
     * The method sweeps one region.
     * The bean lock keeps a second sweep from starting.
     *
     * @param regionId The region id is the conversation id.
     */
    void sweepOne(String regionId) {
        synchronized (gate) {
            engine.sweepDead(regionId);
        }
    }

    /**
     * The method starts the sweep thread when the interval is legal.
     * A false enabled flag returns without a log line.
     * A refused interval logs once and does not start the thread.
     */
    @Override
    public void start() {
        if (running) {
            return;
        }
        if (properties.getPrune() == null || !properties.getPrune().isEnabled()) {
            return;
        }
        int minutes = properties.getPrune().minutes();
        if (minutes < 1) {
            if (!intervalLogged) {
                LOG.error(INTERVAL);
                intervalLogged = true;
            }
            return;
        }
        engine.attachPrune(this);
        stop = false;
        thread = new Thread(sweep);
        thread.setDaemon(true);
        thread.setName("roux-prune");
        running = true;
        thread.start();
    }

    /**
     * The method stops the sweep thread.
     * The slot already in hand finishes.
     * The thread does not start another slot.
     */
    @Override
    public void stop() {
        stop = true;
        Thread current = thread;
        if (current != null) {
            current.interrupt();
            try {
                current.join(5000L);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        running = false;
    }

    /**
     * The method reports whether the thread is running.
     *
     * @return The flag is true after a legal start and before stop.
     */
    @Override
    public boolean isRunning() {
        return running;
    }

    /**
     * The method sweeps every open region while the bean lock is held.
     */
    private void sweepAll() {
        synchronized (gate) {
            List<String> ids = engine.openRegionIds();
            for (int i = 0; i < ids.size(); i++) {
                if (stop) {
                    return;
                }
                engine.sweepDead(ids.get(i));
            }
        }
    }

    /**
     * The Sweep type is the one prune thread.
     * The thread sweeps, then sleeps for the interval.
     */
    private final class Sweep implements Runnable {

        /**
         * The method sweeps until stop.
         * A tick that elapses during a sweep waits until the next sleep.
         */
        @Override
        public void run() {
            while (!stop) {
                sweepAll();
                if (stop) {
                    return;
                }
                long wait = (long) properties.getPrune().minutes() * 60000L;
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}
/*****************************************************************************************
 * End of Class: DeadPagePrune.java 112226
 ****************************************************************************************/
