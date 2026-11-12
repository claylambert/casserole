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

package org.roux.api;

import org.roux.model.LockToken;
import org.roux.model.OperationResult;

/**
 * The MemoryStateController is the call set that appends messages and takes locks.
 * {@link #appendMessage} stores one message frame.
 * {@link #clearRegion} drops the frames and keeps the region.
 * {@link #tryAcquireLock} takes the region lock for an operator.
 * {@link #releaseLock} drops that operator lock.
 *
 * An append also takes the same region lock.
 *
 * @aa-sdd PK-0758 PK-0759 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-25)
 */
public interface MemoryStateController {

    /**
     * The method stores one message frame.
     * The payload is the text encoding written by {@link org.roux.toon.ToonCodec}.
     * The frame type is {@link org.roux.model.FrameType#MSG}.
     *
     * @param regionId The region id is the name of the region.
     * @param role The role is a byte from {@link org.roux.model.Role}.
     * @param timestampEpochMs The time is the epoch milliseconds stored with the message.
     * @param tokenCount The token count is the number stored with the message.
     * @param text The text is the message body.
     * @return The result is an {@link OperationResult}.
     */
    OperationResult appendMessage(String regionId, byte role, long timestampEpochMs, int tokenCount, String text);

    /**
     * The method drops the frames and keeps the region.
     * The region then has a new empty hot page.
     *
     * @param regionId The region id is the name of the region.
     */
    void clearRegion(String regionId);

    /**
     * The method tries to take the region lock.
     * The wait stops when the timeout elapses.
     *
     * @param regionId The region id is the name of the region.
     * @param level The level is a code from {@link org.roux.model.LockLevel}.
     * @param timeoutMs The timeout is the maximum wait in milliseconds.
     * @return The token is the lock handle, or null when the lock was not taken.
     */
    LockToken tryAcquireLock(String regionId, int level, long timeoutMs);

    /**
     * The method releases an operator lock.
     *
     * @param token The token is the handle from {@link #tryAcquireLock}.
     * @return The flag is true when this token was the lock that was held.
     */
    boolean releaseLock(LockToken token);
}
/*****************************************************************************************
 * End of Class: MemoryStateController.java 082526
 ****************************************************************************************/
