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
 * The Role type holds the byte stored for who sent a message.
 * The {@code NONE} byte is {@code 0}.
 * The {@code SYSTEM} byte is {@code 1}.
 * The {@code USER} byte is {@code 2}.
 * The {@code ASSISTANT} byte is {@code 3}.
 * The {@code TOOL} byte is {@code 4}.
 * The {@code TOOL_CALL} byte is {@code 5}.
 *
 * {@link #nameOf} turns a byte into the word written beside the message text.
 * {@link #fromName} turns that word back into a byte.
 * The type refuses construction.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#Role roux-design.md
 *
 * @author clayl (2026-08-25)
 */
public final class Role {

    public static final byte NONE = 0;
    public static final byte SYSTEM = 1;
    public static final byte USER = 2;
    public static final byte ASSISTANT = 3;
    public static final byte TOOL = 4;
    public static final byte TOOL_CALL = 5;

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private Role() {
    }

    /**
     * The method returns the word for a role byte.
     * The word {@code system} matches {@code SYSTEM}.
     * The word {@code user} matches {@code USER}.
     * The word {@code assistant} matches {@code ASSISTANT}.
     * The word {@code tool} matches {@code TOOL}.
     * The word {@code tool_call} matches {@code TOOL_CALL}.
     * Any other byte returns the word {@code none}.
     *
     * @param code The code is the role byte from a frame or an index row.
     * @return The word is the text stored with the message.
     */
    public static String nameOf(byte code) {
        switch (code) {
            case SYSTEM:
                return "system";
            case USER:
                return "user";
            case ASSISTANT:
                return "assistant";
            case TOOL:
                return "tool";
            case TOOL_CALL:
                return "tool_call";
            default:
                return "none";
        }
    }

    /**
     * The method returns the role byte for a word.
     * A null word returns {@code NONE}.
     * The word {@code system} returns {@code SYSTEM}.
     * The word {@code user} returns {@code USER}.
     * The word {@code assistant} returns {@code ASSISTANT}.
     * The word {@code tool} returns {@code TOOL}.
     * The word {@code tool_call} returns {@code TOOL_CALL}.
     * Any other word returns {@code NONE}.
     *
     * @param name The name is the role word, or null.
     * @return The byte is the matching role, or {@code NONE}.
     */
    public static byte fromName(String name) {
        if (name == null) {
            return NONE;
        }
        if ("system".equals(name)) {
            return SYSTEM;
        }
        if ("user".equals(name)) {
            return USER;
        }
        if ("assistant".equals(name)) {
            return ASSISTANT;
        }
        if ("tool".equals(name)) {
            return TOOL;
        }
        if ("tool_call".equals(name)) {
            return TOOL_CALL;
        }
        return NONE;
    }
}
/*****************************************************************************************
 * End of Class: Role.java 082526
 ****************************************************************************************/
