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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.casserole.config.CasseroleProperties;
import org.junit.jupiter.api.Test;

// @aa-sdd PK-8252 docs/specs/java/org/casserole/casserole.spec.md
/**
 * The class checks the pool address a blank host and a set host produce.
 * The test does not start a process.
 * The test does not open a socket.
 *
 * @author clayl (2026-11-08)
 */
class PoolAddressTest {

    /**
     * The method arranges a blank host, a port, and a token.
     * The method calls {@link PoolAddress#from}.
     * The seat is local.
     * The WebSocket URL is empty.
     * The token is kept.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBlankHostStaysLocal() {
        CasseroleProperties.Pool pool = new CasseroleProperties.Pool();
        pool.setHost("  ");
        pool.setPort(2419);
        pool.setSecret("token");
        PoolAddress address = PoolAddress.from(pool);
        assertFalse(address.remote);
        assertEquals("", address.host);
        assertEquals("", address.websocketUrl);
        assertEquals("token", address.secret);
    }

    /**
     * The method arranges a host, a port of 0, and a padded token.
     * The method calls {@link PoolAddress#from}.
     * The URL uses port 2419.
     * The token is trimmed.
     * The token is not in the URL.
     *
     * @return The method returns nothing.
     */
    @Test
    void aHostWithNoPortUsesTheServePort() {
        CasseroleProperties.Pool pool = new CasseroleProperties.Pool();
        pool.setHost(" 192.168.1.20 ");
        pool.setPort(0);
        pool.setSecret(" desk ");
        PoolAddress address = PoolAddress.from(pool);
        assertTrue(address.remote);
        assertEquals(2419, address.port);
        assertEquals("ws://192.168.1.20:2419/ws", address.websocketUrl);
        assertEquals("desk", address.secret);
        assertFalse(address.websocketUrl.contains("desk"));
    }

    /**
     * The method arranges a host and a port above 65535.
     * The method calls {@link PoolAddress#from}.
     * The URL uses port 2419.
     * A port of 2500 stays 2500.
     *
     * @return The method returns nothing.
     */
    @Test
    void aPortOutsideTheRangeUsesTheServePort() {
        CasseroleProperties.Pool wide = new CasseroleProperties.Pool();
        wide.setHost("192.168.1.20");
        wide.setPort(70000);
        PoolAddress address = PoolAddress.from(wide);
        assertEquals(2419, address.port);
        assertEquals("ws://192.168.1.20:2419/ws", address.websocketUrl);
        CasseroleProperties.Pool named = new CasseroleProperties.Pool();
        named.setHost("192.168.1.20");
        named.setPort(2500);
        assertEquals(2500, PoolAddress.from(named).port);
        assertEquals("ws://192.168.1.20:2500/ws", PoolAddress.from(named).websocketUrl);
    }

    /**
     * The method arranges a token that contains a newline.
     * The method calls {@link PoolAddress#from}.
     * The stored token is empty.
     * The URL has no token.
     *
     * @return The method returns nothing.
     */
    @Test
    void aBrokenSecretBecomesEmpty() {
        CasseroleProperties.Pool pool = new CasseroleProperties.Pool();
        pool.setHost("10.0.0.2");
        pool.setPort(2419);
        pool.setSecret("ab\ncd");
        PoolAddress address = PoolAddress.from(pool);
        assertEquals("", address.secret);
        assertEquals("ws://10.0.0.2:2419/ws", address.websocketUrl);
    }

    /**
     * The method arranges an IPv6 host.
     * The method calls {@link PoolAddress#from}.
     * The host in the URL is wrapped in brackets.
     * A host that is already wrapped stays wrapped once.
     *
     * @return The method returns nothing.
     */
    @Test
    void anIpv6HostIsBracketed() {
        CasseroleProperties.Pool pool = new CasseroleProperties.Pool();
        pool.setHost("::1");
        pool.setPort(2419);
        pool.setSecret("desk-token");
        PoolAddress address = PoolAddress.from(pool);
        assertEquals("ws://[::1]:2419/ws", address.websocketUrl);
        assertFalse(address.websocketUrl.contains("desk-token"));
        CasseroleProperties.Pool wrapped = new CasseroleProperties.Pool();
        wrapped.setHost("[::1]");
        wrapped.setPort(2419);
        assertEquals("ws://[::1]:2419/ws", PoolAddress.from(wrapped).websocketUrl);
    }
}
