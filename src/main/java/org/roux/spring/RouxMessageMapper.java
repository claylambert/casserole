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

package org.roux.spring;

import org.roux.model.DecodedFrame;
import org.roux.model.Role;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

// @aa-sdd GK-1059 aetheris-plan-f-170.001.md FINESSE:f-170
/**
 * The RouxMessageMapper converts a Spring chat message to and from a stored role.
 * The message text is read with {@code getText}.
 * A missing message becomes the user role.
 * A missing message type becomes the user role.
 * Encoding the payload stays on {@link org.roux.toon.ToonCodec}.
 *
 * The type refuses construction.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#RouxMessageMapper roux-design.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd GK-2406 spillover-semantics.md
// @aa-sdd PK-638 docs/specs/common/formal-plan.spec.md
// @aa-sdd GK-3062 roux-design.md
// @aa-sdd GK-3077 roux-design.md
public final class RouxMessageMapper {

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private RouxMessageMapper() {
    }

    /**
     * The method maps a Spring message type onto a role byte.
     * A system message returns {@link Role#SYSTEM}.
     * A user message returns {@link Role#USER}.
     * An assistant message returns {@link Role#ASSISTANT}.
     * A tool message returns {@link Role#TOOL}.
     * Any other type returns {@link Role#USER}.
     * A null message returns {@link Role#USER}.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxMessageMapper roux-design.md
     *
     * @param message The message is the Spring chat message, or null.
     * @return The role is the byte stored with the message.
     */
    public static byte roleOf(Message message) {
        if (message == null || message.getMessageType() == null) {
            return Role.USER;
        }
        MessageType type = message.getMessageType();
        if (type == MessageType.SYSTEM) {
            return Role.SYSTEM;
        }
        if (type == MessageType.USER) {
            return Role.USER;
        }
        if (type == MessageType.ASSISTANT) {
            return Role.ASSISTANT;
        }
        if (type == MessageType.TOOL) {
            return Role.TOOL;
        }
        return Role.USER;
    }

    /**
     * The method returns the text of a Spring message.
     * A null message returns an empty string.
     * A null text returns an empty string.
     *
     * @param message The message is the Spring chat message, or null.
     * @return The text is the message body, or an empty string.
     */
    public static String textOf(Message message) {
        if (message == null || message.getText() == null) {
            return "";
        }
        return message.getText();
    }

    /**
     * The method builds a Spring message from a decoded frame.
     * A null frame returns a user message with an empty body.
     * A system role returns a system message.
     * An assistant role returns an assistant message.
     * A tool role returns a user message.
     * A tool-call role returns a user message.
     * Any other role returns a user message.
     *
     * @param frame The frame is the decoded message, or null.
     * @return The message is the Spring chat message.
     */
    public static Message toSpring(DecodedFrame frame) {
        if (frame == null) {
            return new UserMessage("");
        }
        String text = frame.getText();
        byte role = frame.getRole();
        if (role == Role.SYSTEM) {
            return new SystemMessage(text);
        }
        if (role == Role.ASSISTANT) {
            return new AssistantMessage(text);
        }
        if (role == Role.TOOL || role == Role.TOOL_CALL) {
            return new UserMessage(text);
        }
        return new UserMessage(text);
    }
}
/*****************************************************************************************
 * End of Class: RouxMessageMapper.java 082526
 ****************************************************************************************/
