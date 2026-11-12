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

/**
 * The {@code Lane} type names the worker pools that {@link LaminaEngine} creates.
 * {@link #SSH_IO} is a cached pool, so a new connection can receive its own thread.
 * {@link #LLM_EXEC} is a fixed pool of four threads for language-model work.
 * A language model is the program that writes an assistant reply.
 * {@link #GENERAL} is a fixed pool of two threads for other work.
 * {@link #TOOL_EXEC} is a fixed pool of one thread.
 * A second tool task waits on that one thread.
 * The waiting task does not fail.
 * An allowlist is the set of tool names that may run, and that set is empty, so no tool can be invoked.
 * {@link #POOL_IO} is a cached pool.
 * Threads on {@link #POOL_IO} are named {@code lamina-pool-io} followed by a number.
 * The standard-output reader and the standard-error drain for the {@code grok} process run on {@link #POOL_IO}.
 * {@link #POOL_IO} is not the pool that runs a model execute.
 *
 * @author clayl (2026-08-30)
 */
public enum Lane {

    /**
     * The {@code SSH_IO} constant is the cached pool for secure-shell connection work.
     * SSH is the secure-shell protocol a chat client uses to connect.
     */
    SSH_IO,

    /**
     * The {@code LLM_EXEC} constant is the fixed pool of four threads for language-model work.
     */
    LLM_EXEC,

    /**
     * The {@code GENERAL} constant is the fixed pool of two threads for other work.
     */
    GENERAL,

    /**
     * The {@code TOOL_EXEC} constant is the fixed pool of one thread for tool work.
     * A second tool task waits on that thread and does not fail.
     * The allowlist of tool names is empty, so no tool can be invoked.
     */
    TOOL_EXEC,

    /**
     * The {@code POOL_IO} constant is the cached pool for the {@code grok} process streams.
     * The standard-output reader and the standard-error drain run here.
     * Model execute work does not run here.
     */
    POOL_IO
}
/*****************************************************************************************
 * End of Class: Lane.java 083026
 ****************************************************************************************/
