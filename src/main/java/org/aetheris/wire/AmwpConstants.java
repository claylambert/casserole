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

package org.aetheris.wire;

/**
 * The {@code AmwpConstants} class holds the numbers for an AMWP frame.
 * AMWP is the framed protocol on the connection.
 * A multi-byte integer is little-endian.
 * The first header byte is the magic value {@code 0xAE}.
 * The header is {@code 16} bytes.
 * The payload cap is 1 mebibyte.
 * // @aa-sdd G-07 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd GK-1199 aetheris-grok-api.draft.001.md
// @aa-sdd GK-1348 aetheris-plan-stage-2.001.md
// @aa-sdd PK-152 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd GK-1636 aetheris-stage-2.draft.001.md
// @aa-sdd PK-729 docs/specs/common/server.spec.md
// @aa-sdd PK-169 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-170 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-709 docs/specs/common/rust.spec.md
// @aa-sdd PK-712 docs/specs/common/rust.spec.md
public final class AmwpConstants {

    public static final int MAGIC = 0xAE;
    public static final int VERSION = 1;
    public static final int HEADER_BYTES = 16;
    public static final int MAX_PAYLOAD = 1024 * 1024;
    public static final int CHANNEL_T = 0x0001;
    public static final int CHANNEL_A = 0x0002;
    public static final int CHANNEL_STREAM = 0x0003;
    /**
     * The constant is the channel for a tool frame.
     * The value {@code 0x0100} is not allocated.
     */
    public static final int CHANNEL_TOOL = 0x0004;
    public static final int TYPE_INIT = 0x00;
    public static final int TYPE_DATA = 0x01;
    public static final int TYPE_PRUNE = 0x02;
    public static final int TYPE_EOF = 0x03;
    public static final int TYPE_ERROR = 0x04;
    public static final int TYPE_CANCEL = 0x05;
    /**
     * The constant is one thinking chunk or one content chunk.
     * The value {@code 0x0100} stays unallocated.
     */
    public static final int TYPE_DELTA = 0x06;
    public static final int TYPE_KEEPALIVE = 0x09;
    public static final int FLAG_FRAGMENT = 0x04;
    public static final int FRAG_PREFIX_BYTES = 24;
    public static final long KEEPALIVE_MS = 2000L;
    public static final long INIT_WAIT_MS = 1500L;
    public static final long SERVER_PEEK_MS = 400L;

    /**
     * The constructor stops a caller from creating an instance.
     * The numbers are static fields.
     */
    private AmwpConstants() {
    }
}
/*****************************************************************************************
 * End of Class: AmwpConstants.java 090226
 ****************************************************************************************/
