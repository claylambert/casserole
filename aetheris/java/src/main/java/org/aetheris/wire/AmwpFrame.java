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
 * The {@code AmwpFrame} class is one decoded AMWP frame.
 * AMWP is the framed protocol on the connection.
 * The payload is the raw octets from the frame.
 * The payload is not decoded as UTF-8.
 * // @aa-sdd PK-0471 PK-0480 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-3491 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 *
 * @author clayl (2026-09-02)
 */
public final class AmwpFrame {

    private final int channel;
    private final int type;
    private final int flags;
    private final byte[] payload;

    /**
     * The constructor stores one decoded frame.
     * A null payload is stored as a new empty array.
     *
     * @param channel The channel is the frame channel id.
     * @param type The type is the frame type byte.
     * @param flags The flags are the frame flag byte.
     * @param payload The payload is the raw octets, and a null array is stored empty.
     */
    public AmwpFrame(int channel, int type, int flags, byte[] payload) {
        this.channel = channel;
        this.type = type;
        this.flags = flags;
        this.payload = payload == null ? new byte[0] : payload;
    }

    /**
     * The method returns the channel id stored on this frame.
     *
     * @return The return value is the channel id.
     */
    public int getChannel() {
        return channel;
    }

    /**
     * The method returns the type byte stored on this frame.
     *
     * @return The return value is the type byte.
     */
    public int getType() {
        return type;
    }

    /**
     * The method returns the flag byte stored on this frame.
     *
     * @return The return value is the flag byte.
     */
    public int getFlags() {
        return flags;
    }

    /**
     * The method returns the payload array stored on this frame.
     * The array is the same array the constructor stored.
     *
     * @return The return value is the payload octets, and it is never null.
     */
    public byte[] getPayload() {
        return payload;
    }

    /**
     * The method reports whether the more-fragments flag is set.
     * The flag is {@link AmwpConstants#FLAG_FRAGMENT}.
     *
     * @return The return value is true when that bit is set on the flag byte.
     */
    public boolean isFragment() {
        return (flags & AmwpConstants.FLAG_FRAGMENT) != 0;
    }
}
/*****************************************************************************************
 * End of Class: AmwpFrame.java 090226
 ****************************************************************************************/
