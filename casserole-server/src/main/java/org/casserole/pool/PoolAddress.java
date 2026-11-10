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

import org.casserole.config.CasseroleProperties;

// @aa-sdd PK-8252 docs/specs/java/org/casserole/pool/pool.spec.md
/**
 * The {@code PoolAddress} class is the ACP seat for one pool.
 * A blank host keeps the local {@code grok agent stdio} child.
 * A host names a {@code grok agent serve} process on another machine.
 * The WebSocket URL is {@code ws://host:port/ws}.
 * A port below 1 or above 65535 becomes 2419, which is the serve default.
 * The secret is the serve token. It is not placed in the URL. It is not written to the log by this class.
 * A token that contains a carriage return or a line feed is stored as empty, so it cannot break the handshake header.
 *
 * @author clayl (2026-11-08)
 */
final class PoolAddress {

    /** The field is the port {@code grok agent serve} listens on when the yaml port is absent. */
    static final int SERVE_PORT = 2419;

    /** The field is the trimmed host. Empty means the local child. */
    final String host;

    /** The field is the port used when {@link #remote} is true. */
    final int port;

    /** The field is the trimmed serve token. Empty means the handshake sends no Authorization header. */
    final String secret;

    /** The field is true when the pool dials the WebSocket instead of starting a child. */
    final boolean remote;

    /** The field is {@code ws://host:port/ws} when remote, and empty when the seat is local. */
    final String websocketUrl;

    private PoolAddress(String host, int port, String secret, boolean remote, String websocketUrl) {
        this.host = host;
        this.port = port;
        this.secret = secret;
        this.remote = remote;
        this.websocketUrl = websocketUrl;
    }

    /**
     * The {@code from} method reads one pool section.
     * A null section is the local seat.
     * A blank host is the local seat, and the port is ignored.
     * A set host is the remote seat.
     *
     * @param pool The pool is {@code casserole.pool}, or null.
     * @return The return value is the seat. The secret is not in {@link #websocketUrl}.
     */
    static PoolAddress from(CasseroleProperties.Pool pool) {
        String host = "";
        int port = 0;
        String secret = "";
        if (pool != null) {
            host = cleanHost(pool.getHost());
            port = pool.getPort();
            secret = cleanSecret(pool.getSecret());
        }
        if (host.length() == 0) {
            return new PoolAddress("", 0, secret, false, "");
        }
        int use = port;
        if (use < 1 || use > 65535) {
            use = SERVE_PORT;
        }
        String url = "ws://" + bracketHost(host) + ":" + use + "/ws";
        return new PoolAddress(host, use, secret, true, url);
    }

    /**
     * The {@code cleanHost} method trims a host.
     * A null host is empty.
     *
     * @param host The host is the raw property.
     * @return The return value is the trimmed host, or empty.
     */
    private static String cleanHost(String host) {
        if (host == null) {
            return "";
        }
        return host.trim();
    }

    /**
     * The {@code bracketHost} method wraps an IPv6 host in brackets.
     * A host that already starts with {@code [} is kept. A host with no colon is kept. Any other host is wrapped, so the port stays a separate URL field.
     *
     * @param host The host is the trimmed property.
     * @return The return value is the host text used in {@link #websocketUrl}.
     */
    private static String bracketHost(String host) {
        if (host.indexOf(':') < 0 || host.startsWith("[")) {
            return host;
        }
        return "[" + host + "]";
    }

    /**
     * The {@code cleanSecret} method trims a serve token.
     * A null token is empty. A token that contains a carriage return or a line feed is empty.
     *
     * @param secret The secret is the raw property.
     * @return The return value is the token, or empty.
     */
    private static String cleanSecret(String secret) {
        if (secret == null) {
            return "";
        }
        String trimmed = secret.trim();
        if (trimmed.indexOf('\n') >= 0 || trimmed.indexOf('\r') >= 0) {
            return "";
        }
        return trimmed;
    }
}
