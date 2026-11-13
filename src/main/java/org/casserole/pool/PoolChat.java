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

package org.casserole.pool;

// @aa-sdd PK-1059 docs/specs/common/formal-plan.spec.md FINESSE:f-33
// @aa-sdd PK-1283 docs/specs/java/org/casserole/pool/pool.spec.md
/**
 * The {@code PoolChat} interface is the server-side contract for one pool seat per SSH user.
 * A blank host keeps the local {@code grok agent stdio} child. A set host dials {@code grok agent serve}.
 * The contract has no SSH types and no Swing types.
 * // @aa-sdd L40 aa-sdd-source-prose.md#PoolChat
 *
 * @author clayl (2026-10-22)
 */
// @aa-sdd PK-769 docs/specs/common/stages.spec.md
// @aa-sdd PK-1101 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-965 docs/specs/java/org/casserole/pool/pool.spec.md
// @aa-sdd PK-406 docs/specs/java/org/casserole/pool/pool.spec.md
public interface PoolChat {

    // @aa-sdd L40 aa-sdd-source-prose.md#PoolChat
    /**
     * The {@code Health} enum says whether that user's child can take a prompt.
     * {@link #READY} means a live seat is up. {@link #DOWN} means no live seat. {@link #AUTH} means the seat reported an authentication failure.
     */
    // @aa-sdd PK-405 docs/specs/java/org/casserole/pool/pool.spec.md
    enum Health {
        /**
         * The value means a live process is up.
         */
        READY,
        /**
         * The value means no live process is up.
         */
        DOWN,
        /**
         * The value means the child reported an authentication failure.
         */
        AUTH
    }

    // @aa-sdd L40 aa-sdd-source-prose.md#PoolChat
    /**
     * The {@code Sink} interface receives chunks and then one completion.
     * {@link #done} carries a reason and a message. An empty reason is success, and the message is the concatenated assistant text.
     */
    // @aa-sdd PK-411 docs/specs/java/org/casserole/pool/pool.spec.md
    interface Sink {
        /**
         * The method receives one thought chunk.
         *
         * @param text The text is the thought chunk.
         * @throws Exception The method throws this exception when the caller rejects the chunk.
         */
        void thought(String text) throws Exception;

        /**
         * The method receives one message chunk.
         *
         * @param text The text is the message chunk.
         * @throws Exception The method throws this exception when the caller rejects the chunk.
         */
        void message(String text) throws Exception;

        /**
         * The method receives the completion for one prompt.
         * An empty reason means success. Any other reason is {@code pool down}, {@code pool payload}, {@code pool auth}, {@code pool tool refused}, or {@code pool parse}.
         *
         * @param reason The reason is empty on success. Otherwise it is one of the five pool reasons.
         * @param message The message is the assistant text, or the short failure line.
         * @throws Exception The method throws this exception when the frame could not be sent.
         */
        void done(String reason, String message) throws Exception;
    }

    // @aa-sdd L40 aa-sdd-source-prose.md#PoolChat
    /**
     * The {@code TooLarge} class is the failure for a chunk that does not fit the wire.
     * The caller has already sent {@code too-large}. The binding cancels the prompt and does not call {@link Sink#done}.
     */
    final class TooLarge extends Exception {

        private static final long serialVersionUID = 1L;

        /**
         * The constructor stores the message {@code too-large}.
         * The fit check has already reported the chunk.
         */
        public TooLarge() {
            super("too-large");
        }
    }

    /**
     * The method brings that user's child to a ready process.
     *
     * @param user The user is the SSH user name.
     * @throws Exception The method throws this exception when the process did not start.
     */
    void start(String user) throws Exception;

    /**
     * The method runs one prompt and blocks until {@link Sink#done} has run, or until the fit check stops the prompt.
     * A non-empty boot may reuse the session and send only the tail. The text is the whole working set of plain role lines.
     *
     * @param user The user is the SSH user name.
     * @param text The text is the whole working set of plain role lines.
     * @param boot The boot is the desktop boot id. An empty string means the client sent none.
     * @param think The flag is true when thought chunks are of interest.
     * @param rules The rules are the extra rule text for the next session. An empty string means the mood pair is off.
     * @param sink The sink receives the frames.
     * @throws Exception The method throws this exception on interrupt, or for a failure the sink did not already take.
     */
    void prompt(String user, String text, String boot, boolean think, String rules, Sink sink) throws Exception;

    /**
     * The method cancels the in-flight prompt for that user.
     * The child stays up. A second call is safe.
     *
     * @param user The user is the SSH user name.
     */
    void cancel(String user);

    /**
     * The method reports whether that user's child can take a prompt.
     * The result is ready, down, or auth. The result contains no secret.
     *
     * @param user The user is the SSH user name.
     * @return The return value is {@link Health#READY}, {@link Health#DOWN}, or {@link Health#AUTH}.
     */
    Health health(String user);

    /**
     * The method destroys every child process.
     */
    void shutdown();
}
/*****************************************************************************************
 * End of Class: PoolChat.java 102226
 ****************************************************************************************/
