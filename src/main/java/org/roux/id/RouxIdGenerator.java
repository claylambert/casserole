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

package org.roux.id;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

// @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
/**
 * The RouxIdGenerator mints the next {@link RouxId} for this process.
 * Each call advances a millisecond and a 12-bit counter.
 * The high long stores that millisecond, a version nibble of {@code 7}, and the counter.
 * The low long sets the variant bits and fills the rest from a random long.
 * The generator keeps no node id.
 * The generator does not build a UUID object.
 * The message id is assigned later, when the frame is appended.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#RouxIdGenerator roux-message-identity.md
 *
 * @author clayl (2026-08-25)
 */
@Component
// @aa-sdd PK-688 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-638 docs/specs/common/formal-plan.spec.md
public class RouxIdGenerator {

    private static final int COUNTER_MASK = 0xFFF;
    private static final long VARIANT = 0x8000000000000000L;

    private final AtomicLong lastMsAndCounter;

    /**
     * The constructor starts the generator at zero.
     */
    public RouxIdGenerator() {
        this.lastMsAndCounter = new AtomicLong(0L);
    }

    /**
     * The method mints the next identity.
     * A later clock millisecond starts a new counter from a random 12-bit value.
     * The same millisecond increments the counter.
     * A counter that wraps waits for the next millisecond and then takes a new random counter.
     * Two callers cannot publish the same packed millisecond and counter.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxIdGenerator roux-message-identity.md
     *
     * @return The identity is a new {@link RouxId}.
     */
    public RouxId next() {
        while (true) {
            long now = System.currentTimeMillis();
            long packed = lastMsAndCounter.get();
            long lastMs = packed >>> 12;
            int counter = (int) (packed & COUNTER_MASK);
            long nextMs;
            int nextCounter;
            if (now > lastMs) {
                nextMs = now;
                nextCounter = ThreadLocalRandom.current().nextInt() & COUNTER_MASK;
            } else {
                nextMs = lastMs;
                nextCounter = (counter + 1) & COUNTER_MASK;
                if (nextCounter == 0) {
                    nextMs = waitNextMillis(lastMs);
                    nextCounter = ThreadLocalRandom.current().nextInt() & COUNTER_MASK;
                }
            }
            long nextPacked = (nextMs << 12) | nextCounter;
            if (lastMsAndCounter.compareAndSet(packed, nextPacked)) {
                long hi = (nextMs << 16) | (0x7L << 12) | nextCounter;
                long rand = ThreadLocalRandom.current().nextLong();
                long lo = VARIANT | (rand & 0x3FFFFFFFFFFFFFFFL);
                return new RouxId(hi, lo);
            }
        }
    }

    /**
     * The method waits until the clock moves past the given millisecond.
     *
     * @param lastMs The last millisecond is the time that must pass.
     * @return The millisecond is later than {@code lastMs}.
     */
    private long waitNextMillis(long lastMs) {
        long now = System.currentTimeMillis();
        while (now <= lastMs) {
            now = System.currentTimeMillis();
        }
        return now;
    }
}
/*****************************************************************************************
 * End of Class: RouxIdGenerator.java 082526
 ****************************************************************************************/
