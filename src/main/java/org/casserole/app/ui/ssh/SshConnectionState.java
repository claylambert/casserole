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

package org.casserole.app.ui.ssh;

/**
 * The {@code SshConnectionState} type names the state of the SSH client connection.
 * The name SSH means the secure shell this client opens.
 * The {@link #DISCONNECTED} constant means no session is open.
 * The {@link #CONNECTING} constant means a worker thread is still in the handshake, the exchange that opens the shell.
 * The {@link #CONNECTED} constant means the shell channel is open and wired to JediTerm, the local terminal widget that shows the shell.
 * // @aa-sdd L40 aa-sdd-source-prose.md#SshConnectionState
 *
 * @author clayl (2026-08-24)
 */
public enum SshConnectionState {

    /**
     * The {@code DISCONNECTED} constant means no SSH session is open.
     */
    DISCONNECTED,

    /**
     * The {@code CONNECTING} constant means a connect attempt is in progress on a worker thread.
     */
    CONNECTING,

    /**
     * The {@code CONNECTED} constant means an SSH shell channel is open and wired to JediTerm.
     * The name JediTerm means the local terminal widget that shows the shell.
     */
    CONNECTED
}
/*****************************************************************************************
 * End of Class: SshConnectionState.java 082426
 ****************************************************************************************/
