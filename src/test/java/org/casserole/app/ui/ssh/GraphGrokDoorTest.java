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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.aetheris.lcarsgraph.GraphDoor;
import org.casserole.llm.GrokCallOptions;
import org.casserole.llm.GrokLlmService;
import org.casserole.pool.PoolChat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * The class checks the graph door.
 * The test does not open a socket.
 *
 * @author clayl (2026-11-24)
 */
class GraphGrokDoorTest {

    /**
     * The method calls {@link GraphGrokDoor#callOptions} with thinking off.
     * {@code thinkStream} stays false.
     * {@code sendReasoningEffort} stays false.
     * {@code maxCompletionTokens} stays 4000.
     */
    @Test
    void aQuietDoorLeavesTheStreamOff() {
        GrokCallOptions options = GraphGrokDoor.callOptions(false);
        assertFalse(options.thinkStream);
        assertFalse(options.sendReasoningEffort);
        assertEquals(4000, options.maxCompletionTokens);
    }

    /**
     * The method calls {@link GraphGrokDoor#callOptions} with thinking on.
     * {@code thinkStream} is true.
     * {@code sendReasoningEffort} stays false.
     * {@code maxCompletionTokens} stays 4000.
     */
    @Test
    void aThinkingDoorStreams() {
        GrokCallOptions options = GraphGrokDoor.callOptions(true);
        assertTrue(options.thinkStream);
        assertFalse(options.sendReasoningEffort);
        assertEquals(4000, options.maxCompletionTokens);
    }

    /**
     * The method keeps the stored model options on the door.
     * The effort, the cap, and the temperature stay.
     * A quiet stored stream stays off.
     */
    @Test
    void aSeatedDoorKeepsTheModelOptions() {
        GrokCallOptions options = new GrokCallOptions();
        options.sendReasoningEffort = true;
        options.reasoningEffort = "low";
        options.maxCompletionTokens = 2222;
        options.temperature = 0.25d;
        options.thinkStream = false;
        GraphGrokDoor door = new GraphGrokDoor("grok/grok-4.7", null, null, options);
        GrokCallOptions seated = door.seatedOptions();
        assertEquals("low", seated.reasoningEffort);
        assertTrue(seated.sendReasoningEffort);
        assertEquals(2222, seated.maxCompletionTokens);
        assertEquals(0.25d, seated.temperature, 0.0001d);
        assertFalse(seated.thinkStream);
        assertEquals("grok-4.7", GraphGrokDoor.modelOf("grok/grok-4.7"));
    }

    /**
     * The method checks the frontier rewrite stays inside the Grok door.
     * The word pool still reads as {@code grok-4.7} there.
     * A grok family id stays that id.
     */
    @Test
    void modelOfKeepsTheFrontierRewrite() {
        assertEquals(GraphGrokDoor.FRONTIER, GraphGrokDoor.modelOf("pool"));
        assertEquals("grok-4.7", GraphGrokDoor.modelOf("grok/grok-4.7"));
    }

    /**
     * The method checks the word pool opens the pool door.
     * A grok word and a llama word stay on the Grok door.
     * The method does not call ask on the Grok door.
     */
    @Test
    void poolWordOpensThePoolDoor() {
        Fake fake = new Fake();
        GraphDoor pool = GraphDoors.open(" pool ", "ada", false, null, null, fake);
        GraphDoor slashed = GraphDoors.open("pool/local", "ada", false, null, null, fake);
        GraphDoor grok = GraphDoors.open("grok/grok-4.7", "ada", false, null, null, fake);
        GraphDoor llama = GraphDoors.open("llama", "ada", false, null, null, fake);
        assertTrue(pool instanceof GraphPoolDoor);
        assertTrue(slashed instanceof GraphPoolDoor);
        assertTrue(grok instanceof GraphGrokDoor);
        assertTrue(llama instanceof GraphGrokDoor);
    }

    /**
     * The method checks one pool ask returns the done message.
     * The prompt starts with the system teaching.
     * The seat is the shell seat. The boot and the rules are empty.
     */
    @Test
    void aPoolAskReturnsTheDoneMessage() throws Exception {
        Fake fake = new Fake();
        fake.message = "intent-pass: 1";
        GraphDoor door = GraphDoors.open("pool", "ada", false, null, null, fake);
        String reply = door.ask("window lines");
        assertEquals("intent-pass: 1", reply);
        assertEquals("ada", fake.user);
        assertEquals(PoolChat.Seat.SSH, fake.seat);
        assertEquals("", fake.boot);
        assertEquals("", fake.rules);
        assertFalse(fake.think);
        assertTrue(fake.text.startsWith("You assign intent."));
        assertTrue(fake.text.endsWith("\nwindow lines"));
    }

    /**
     * The method checks a non-empty done reason throws.
     * The end runnable still runs.
     */
    @Test
    void aPoolFaultThrows() {
        Fake fake = new Fake();
        fake.reason = "pool down";
        fake.message = "no";
        Mark end = new Mark();
        GraphDoor door = GraphDoors.open("pool", "ada", false, null, end, fake);
        Exception thrown = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                door.ask("window lines");
            }
        });
        assertEquals("pool down", thrown.getMessage());
        assertTrue(end.ran);
    }

    /**
     * The method checks a thought chunk is forwarded when thinking is on.
     * The kind is {@code t}.
     */
    @Test
    void aPoolThoughtIsForwarded() throws Exception {
        Fake fake = new Fake();
        fake.thought = "spark";
        fake.message = "intent-pass: 1";
        Heard heard = new Heard();
        GraphDoor door = GraphDoors.open("pool", "ada", true, heard, null, fake);
        assertEquals("intent-pass: 1", door.ask("window lines"));
        assertTrue(fake.think);
        assertEquals('t', heard.kind);
        assertEquals("spark", heard.text);
    }

    /**
     * The method checks a null injected chat throws.
     * The ask does not build an engine.
     */
    @Test
    void aMissingChatDoesNotAskGrok() {
        GraphDoor door = GraphDoors.open("pool", "ada", false, null, null, null);
        Exception thrown = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                door.ask("window lines");
            }
        });
        assertEquals("pool", thrown.getMessage());
    }

    /**
     * The class records one prompt and completes it.
     */
    private static final class Fake implements PoolChat {

        private String text;
        private String boot;
        private String rules;
        private String user;
        private PoolChat.Seat seat;
        private boolean think;
        private String thought;
        private String reason = "";
        private String message = "";

        @Override
        public void start(String user, PoolChat.Seat seat) {
        }

        @Override
        public void prompt(String user, PoolChat.Seat seat, String text, String boot, boolean think, String rules,
                PoolChat.Sink sink) throws Exception {
            this.user = user;
            this.seat = seat;
            this.text = text;
            this.boot = boot;
            this.think = think;
            this.rules = rules;
            if (thought != null) {
                sink.thought(thought);
            }
            sink.done(reason, message);
        }

        @Override
        public void cancel(String user, PoolChat.Seat seat) {
        }

        @Override
        public PoolChat.Health health(String user, PoolChat.Seat seat) {
            return PoolChat.Health.DOWN;
        }

        @Override
        public void shutdown() {
        }
    }

    /**
     * The class records one thought chunk.
     */
    private static final class Heard implements GrokLlmService.DeltaSink {

        private char kind;
        private String text;

        @Override
        public void delta(char kind, String text) {
            this.kind = kind;
            this.text = text;
        }
    }

    /**
     * The class records that the end runnable ran.
     */
    private static final class Mark implements Runnable {

        private boolean ran;

        @Override
        public void run() {
            ran = true;
        }
    }
}
