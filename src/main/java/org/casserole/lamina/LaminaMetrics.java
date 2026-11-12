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

import java.util.concurrent.atomic.AtomicLong;

import org.casserole.log.CasseroleLog;
import org.apache.logging.log4j.Logger;

/**
 * The {@code LaminaMetrics} type counts submits, completions, and failures for {@link Lamina}.
 * Collection is off unless the constructor is given {@code true}.
 * The counts are written only to the program log.
 * The type does not send the counts to OpenTelemetry.
 * OpenTelemetry is an external metrics system, and this type does not call it.
 *
 * @author clayl (2026-08-30)
 */
public class LaminaMetrics {

    private static final Logger LOG = CasseroleLog.get(LaminaMetrics.class);

    private final boolean enabled;
    private final AtomicLong submits;
    private final AtomicLong completes;
    private final AtomicLong fails;

    /**
     * The constructor stores whether counting is on and starts each counter at zero.
     * If {@code enabled} is {@code false}, {@link #recordSubmit()}, {@link #recordComplete()}, and {@link #recordFail()} do nothing.
     *
     * @param enabled {@code true} when the counters should move, or {@code false} when every record call does nothing.
     */
    public LaminaMetrics(boolean enabled) {
        this.enabled = enabled;
        this.submits = new AtomicLong();
        this.completes = new AtomicLong();
        this.fails = new AtomicLong();
    }

    /**
     * The {@code isEnabled} method reports whether counting is on.
     *
     * @return {@code true} when record calls change the counters, or {@code false} when they do nothing.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * The {@code recordSubmit} method counts one submitted task.
     * If counting is off, the method returns without changing the counter.
     * If counting is on, the submit counter increases by one.
     * The new submit count is written to the debug log as {@code lamina submit count=} followed by that number.
     */
    public void recordSubmit() {
        if (!enabled) {
            return;
        }
        long n = submits.incrementAndGet();
        LOG.debug("lamina submit count={}", n);
    }

    /**
     * The {@code recordComplete} method counts one task that finished without throwing.
     * If counting is off, the method returns without changing the counter.
     * If counting is on, the complete counter increases by one.
     * The method does not write a log line.
     */
    public void recordComplete() {
        if (!enabled) {
            return;
        }
        completes.incrementAndGet();
    }

    /**
     * The {@code recordFail} method counts one task that threw {@code RuntimeException}.
     * If counting is off, the method returns without changing the counter.
     * If counting is on, the fail counter increases by one.
     * The new fail count is written to the debug log as {@code lamina fail count=} followed by that number.
     */
    public void recordFail() {
        if (!enabled) {
            return;
        }
        fails.incrementAndGet();
        LOG.debug("lamina fail count={}", fails.get());
    }
}
/*****************************************************************************************
 * End of Class: LaminaMetrics.java 083026
 ****************************************************************************************/
