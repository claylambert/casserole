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
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
