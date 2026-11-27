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

import org.aetheris.lcarsgraph.GraphDoor;
import org.casserole.llm.GrokLlmService;
import org.casserole.pool.PoolChat;

/**
 * The door sends one intent brief through one pool chat.
 * The prompt is one string. The seat is the shell seat on this chat only.
 *
 * @author clayl (2026-11-24)
 */
final class GraphPoolDoor implements GraphDoor {

    private final PoolChat chat;
    private final boolean lazy;
    private final String user;
    private final boolean thinking;
    private final GrokLlmService.DeltaSink sink;
    private final Runnable end;

    /**
     * The constructor keeps the chat and the thought ear.
     * A null chat with {@code lazy} builds the chat on the first ask.
     * A null chat without {@code lazy} throws on ask and does not build a Grok door.
     *
     * @param chat The chat receives the prompt. The value may be null.
     * @param lazy The flag is true when a null chat is built on the first ask.
     * @param user The user is the open SSH name. The value may be empty.
     * @param thinking The flag is true when thought chunks are forwarded.
     * @param sink The sink hears a reasoning chunk. The value may be null.
     * @param end The end closes the thinking block. The value may be null.
     */
    GraphPoolDoor(PoolChat chat, boolean lazy, String user, boolean thinking,
            GrokLlmService.DeltaSink sink, Runnable end) {
        this.chat = chat;
        this.lazy = lazy;
        this.user = user == null ? "" : user;
        this.thinking = thinking;
        this.sink = sink;
        this.end = end;
    }

    /**
     * The method sends the brief and returns the done message.
     * The text is the system teaching, a newline, and the brief.
     * The boot is empty. The rules are empty.
     * A non-empty done reason throws.
     * {@code end} runs when the ask returns, including when the call throws.
     *
     * @param brief The brief is the intent ask.
     * @return The return value is the done message.
     * @throws Exception The exception is a missing chat or a non-empty done reason.
     */
    @Override
    public String ask(String brief) throws Exception {
        try {
            PoolChat held = chat;
            if (held == null) {
                if (!lazy) {
                    throw new Exception("pool");
                }
                held = GraphDoors.supply();
            }
            Ear ear = new Ear();
            String text = GraphGrokDoor.systemText() + "\n" + (brief == null ? "" : brief);
            held.prompt(user, PoolChat.Seat.SSH, text, "", thinking, "", ear);
            if (ear.reason != null && ear.reason.length() > 0) {
                throw new Exception(ear.reason);
            }
            if (ear.message == null) {
                return "";
            }
            return ear.message;
        } finally {
            if (end != null) {
                end.run();
            }
        }
    }

    /**
     * The class keeps the done pair and forwards a thought.
     * A message chunk is not collected.
     */
    private final class Ear implements PoolChat.Sink {

        private String reason;
        private String message;

        /**
         * The method forwards kind {@code t} when thinking is on.
         *
         * @param text The text is the thought chunk.
         * @throws Exception The method throws this exception when the ear rejects the chunk.
         */
        @Override
        public void thought(String text) throws Exception {
            if (!thinking || sink == null || text == null || text.length() == 0) {
                return;
            }
            sink.delta('t', text);
        }

        /**
         * The method drops a message chunk.
         * The done message is the reply.
         *
         * @param text The text is the message chunk.
         */
        @Override
        public void message(String text) {
        }

        /**
         * The method stores the completion.
         *
         * @param reason The reason is empty on success.
         * @param message The message is the assistant text.
         */
        @Override
        public void done(String reason, String message) {
            this.reason = reason;
            this.message = message;
        }
    }
}
