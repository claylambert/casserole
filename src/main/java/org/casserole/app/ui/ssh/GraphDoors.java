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

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

import org.aetheris.lcarsgraph.GraphDoor;
import org.casserole.config.CasseroleProperties;
import org.casserole.lamina.LaminaEngine;
import org.casserole.llm.GrokCallOptions;
import org.casserole.llm.GrokLlmService;
import org.casserole.pool.PoolChat;
import org.casserole.pool.PoolChatServer;
import org.yaml.snakeyaml.Yaml;

/**
 * The class chooses the door for the engine word.
 * The word pool opens the pool door. Any other word opens the Grok door.
 *
 * @author clayl (2026-11-24)
 */
final class GraphDoors {

    private static LaminaEngine engine;
    private static PoolChat chat;

    private GraphDoors() {
    }

    /**
     * The method reports whether the engine word is pool.
     * The compare is lowercase after trim.
     * A word that starts with {@code pool/} is pool.
     *
     * @param engineWord The engine is the card's execution target.
     * @return The return value is true when the word is pool.
     */
    static boolean poolWord(String engineWord) {
        if (engineWord == null) {
            return false;
        }
        String trimmed = engineWord.trim().toLowerCase(Locale.ROOT);
        if ("pool".equals(trimmed)) {
            return true;
        }
        return trimmed.startsWith("pool/");
    }

    /**
     * The method opens the door for the engine word.
     * The word pool returns a pool door that builds its chat on the first ask.
     * Any other word returns the Grok door.
     *
     * @param engineWord The engine is the card's execution target.
     * @param user The user is the open SSH name.
     * @param thinking The flag is true when a thought ear is present.
     * @param sink The sink hears a reasoning chunk. The value may be null.
     * @param end The end closes the thinking block. The value may be null.
     * @return The door for this walk.
     */
    static GraphDoor open(String engineWord, String user, boolean thinking,
            GrokLlmService.DeltaSink sink, Runnable end) {
        if (poolWord(engineWord)) {
            return new GraphPoolDoor(null, true, user, thinking, sink, end);
        }
        return grok(engineWord, thinking, sink, end);
    }

    /**
     * The method opens the door and uses an injected pool chat.
     * A null chat on the pool word throws on ask and does not build an engine.
     * A Grok word ignores the injected chat.
     *
     * @param engineWord The engine is the card's execution target.
     * @param user The user is the open SSH name.
     * @param thinking The flag is true when a thought ear is present.
     * @param sink The sink hears a reasoning chunk. The value may be null.
     * @param end The end closes the thinking block. The value may be null.
     * @param injected The chat the pool door uses. The value may be null.
     * @return The door for this walk.
     */
    static GraphDoor open(String engineWord, String user, boolean thinking,
            GrokLlmService.DeltaSink sink, Runnable end, PoolChat injected) {
        if (poolWord(engineWord)) {
            return new GraphPoolDoor(injected, false, user, thinking, sink, end);
        }
        return grok(engineWord, thinking, sink, end);
    }

    /**
     * The method opens the door and keeps the stored model options.
     * A null options value leaves the Grok door on its own call options.
     * The word pool still opens the pool door.
     *
     * @param engineWord The engine is the card's execution target.
     * @param user The user is the open SSH name.
     * @param thinking The flag is true when a thought ear is present.
     * @param sink The sink hears a reasoning chunk. The value may be null.
     * @param end The end closes the thinking block. The value may be null.
     * @param options The options are the stored model options. The value may be null.
     * @return The door for this walk.
     */
    // @aa-sdd L40 aetheris-plan-f-324.001.md FINESSE:f-324
    static GraphDoor openSeated(String engineWord, String user, boolean thinking,
            GrokLlmService.DeltaSink sink, Runnable end, GrokCallOptions options) {
        if (poolWord(engineWord)) {
            return new GraphPoolDoor(null, true, user, thinking, sink, end);
        }
        if (thinking) {
            return new GraphGrokDoor(engineWord, sink, end, options);
        }
        return new GraphGrokDoor(engineWord, null, null, options);
    }

    /**
     * The method builds the pool chat on the first ask.
     * The engine has metrics off. The chat is one {@link PoolChatServer}.
     * A later call returns the same chat.
     *
     * @return The chat for a pool ask.
     */
    static synchronized PoolChat supply() {
        if (chat != null) {
            return chat;
        }
        LaminaEngine built = new LaminaEngine(false);
        try {
            chat = new PoolChatServer(built, poolProperties());
            engine = built;
            return chat;
        } catch (RuntimeException ex) {
            built.shutdown();
            throw ex;
        }
    }

    /**
     * The method shuts a chat that {@link #supply} built.
     * A second call does nothing. The method does not throw.
     */
    static synchronized void release() {
        PoolChat held = chat;
        LaminaEngine lane = engine;
        chat = null;
        engine = null;
        try {
            if (held != null) {
                held.shutdown();
            }
        } finally {
            if (lane != null) {
                lane.shutdown();
            }
        }
    }

    /**
     * The method opens the Grok door.
     * Thinking off leaves the call unstreamed.
     *
     * @param engineWord The engine is the card's execution target.
     * @param thinking The flag is true when a thought ear is present.
     * @param sink The sink hears a reasoning chunk. The value may be null.
     * @param end The end closes the thinking block. The value may be null.
     * @return The Grok door.
     */
    private static GraphDoor grok(String engineWord, boolean thinking,
            GrokLlmService.DeltaSink sink, Runnable end) {
        if (thinking) {
            return new GraphGrokDoor(engineWord, sink, end);
        }
        return new GraphGrokDoor(engineWord);
    }

    /**
     * The method reads {@code casserole.pool} from the classpath yaml.
     * A missing block keeps the local child.
     * The serve token is not written to a log.
     *
     * @return The return value is the properties the pool chat reads.
     */
    private static CasseroleProperties poolProperties() {
        CasseroleProperties properties = new CasseroleProperties();
        CasseroleProperties.Pool pool = new CasseroleProperties.Pool();
        InputStream in = GraphDoors.class.getResourceAsStream("/application.yaml");
        if (in == null) {
            properties.setPool(pool);
            return properties;
        }
        try {
            Object loaded = new Yaml().load(in);
            Map<?, ?> block = poolBlock(loaded);
            if (block != null) {
                Object host = block.get("host");
                if (host instanceof String) {
                    pool.setHost((String) host);
                }
                if (block.containsKey("port")) {
                    pool.setPort(number(block.get("port"), 0));
                }
                Object secret = block.get("secret");
                if (secret instanceof String) {
                    pool.setSecret((String) secret);
                }
                Object cwd = block.get("cwd");
                if (cwd instanceof String) {
                    pool.setCwd((String) cwd);
                }
                if (block.containsKey("cap")) {
                    pool.setCap(number(block.get("cap"), 4));
                }
            }
        } catch (RuntimeException ex) {
            // The local child stays.
        } finally {
            try {
                in.close();
            } catch (IOException closed) {
                // The text already left the stream.
            }
        }
        properties.setPool(pool);
        return properties;
    }

    /**
     * The method reads the pool map.
     *
     * @param loaded The loaded value is the yaml root.
     * @return The return value is the pool map, or null.
     */
    private static Map<?, ?> poolBlock(Object loaded) {
        if (!(loaded instanceof Map<?, ?>)) {
            return null;
        }
        Object casserole = ((Map<?, ?>) loaded).get("casserole");
        if (!(casserole instanceof Map<?, ?>)) {
            return null;
        }
        Object pool = ((Map<?, ?>) casserole).get("pool");
        if (!(pool instanceof Map<?, ?>)) {
            return null;
        }
        return (Map<?, ?>) pool;
    }

    /**
     * The method reads an integer.
     * A value that is not a number returns the fallback.
     *
     * @param value The value is the yaml field.
     * @param fallback The fallback is used when the field is not a number.
     * @return The return value is the integer.
     */
    private static int number(Object value, int fallback) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt(((String) value).trim());
            } catch (NumberFormatException ex) {
                return fallback;
            }
        }
        return fallback;
    }
}
