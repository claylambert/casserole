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

package org.roux.model;

/**
 * The FrameType type holds the byte that names what a frame contains.
 * The {@code MSG} byte is {@code 1}.
 * The {@code POLICY} byte is {@code 2}.
 * The {@code TELEM} byte is {@code 3}.
 * The {@code ACL} byte is {@code 4}.
 * The {@code CONT} byte is {@code 5}.
 *
 * A message writer passes {@code MSG}.
 * This type does not write a frame itself.
 * The type refuses construction.
 *
 *
 * @author clayl (2026-08-25)
 */
public final class FrameType {

    public static final byte MSG = 1;
    public static final byte POLICY = 2;
    public static final byte TELEM = 3;
    public static final byte ACL = 4;
    public static final byte CONT = 5;

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private FrameType() {
    }
}
/*****************************************************************************************
 * End of Class: FrameType.java 082526
 ****************************************************************************************/
