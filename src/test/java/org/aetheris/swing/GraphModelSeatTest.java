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

package org.aetheris.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.casserole.llm.GrokCallOptions;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

/**
 * The seat proof stays off the home directory.
 * The test does not open a socket.
 *
 * @author clayl (2026-11-26)
 */
class GraphModelSeatTest {

    /**
     * The method reads a stored engine word.
     * The options are not kept.
     * The DSL call options stay absent.
     */
    @Test
    void aLegacyWordKeepsTheEngineOnly() {
        GraphModelSeat seat = GraphModelSeat.parse("grok/grok-4.7");
        assertEquals("grok/grok-4.7", seat.engine());
        assertFalse(seat.kept());
        assertNull(seat.callOptions(true));
        assertEquals("grok/grok-4.7", seat.yamlValue());
    }

    /**
     * The method stores the Grok options and reads them back from yaml.
     * The call uses the stored effort, the cap, and the temperature.
     * Thinking on forces the stream on.
     */
    @Test
    void aKeptGraphModelReturnsTheSameOptions() {
        GraphModelSeat seat = new GraphModelSeat();
        seat.engine("grok/grok-4.7");
        seat.keepGrok("grok-4.7", "low", true, false, true, 0.25d, 2222, true);
        seat.keepOllama(true, "medium");
        seat.keepPool(true);
        seat.keepLlama(true, 0.4d, true, 512);
        Object node = seat.yamlValue();
        Object loaded = new Yaml().load(new Yaml().dump(node));
        GraphModelSeat again = GraphModelSeat.parse(loaded);
        assertTrue(again.kept());
        assertEquals("grok/grok-4.7", again.engine());
        assertEquals("low", again.effort());
        assertTrue(again.sendEffort());
        assertFalse(again.stream());
        assertTrue(again.burner());
        assertEquals(0.25d, again.temperature(), 0.0001d);
        assertEquals(2222, again.maxTokens());
        assertTrue(again.grokOn());
        assertTrue(again.ollamaOn());
        assertEquals("medium", again.ollamaLevel());
        assertTrue(again.poolOn());
        assertTrue(again.llamaTemperaturePresent());
        assertEquals(0.4d, again.llamaTemperature(), 0.0001d);
        assertTrue(again.llamaMaxPresent());
        assertEquals(512, again.llamaMaxTokens());
        GrokCallOptions quiet = again.callOptions(false);
        assertEquals("low", quiet.reasoningEffort);
        assertTrue(quiet.sendReasoningEffort);
        assertEquals(2222, quiet.maxCompletionTokens);
        assertEquals(0.25d, quiet.temperature, 0.0001d);
        assertFalse(quiet.thinkStream);
        GrokCallOptions thinking = again.callOptions(true);
        assertTrue(thinking.thinkStream);
        assertNull(poolSeat().callOptions(true));
    }

    /**
     * The method reads a map that names only the engine.
     * The options stay unkept.
     */
    @Test
    void anEngineMapWithoutOptionsStaysAWord() {
        Map<String, String> row = new java.util.LinkedHashMap<String, String>();
        row.put("engine", "pool");
        GraphModelSeat seat = GraphModelSeat.parse(row);
        assertEquals("pool", seat.engine());
        assertFalse(seat.kept());
        assertEquals("pool", seat.yamlValue());
    }

    private static GraphModelSeat poolSeat() {
        GraphModelSeat seat = new GraphModelSeat();
        seat.engine("pool");
        seat.keepPool(true);
        return seat;
    }
}
