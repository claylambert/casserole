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

package org.casserole.pool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.casserole.chat.ChatSession;
import org.casserole.mood.MoodDesk;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1089 docs/specs/common/formal-plan.spec.md
/**
 * The class checks {@link ChatSession#porchRules} and the session parameters from {@link PoolChatServer}.
 * A placed mood pair becomes porch rules.
 * An empty pair leaves {@code _meta} off those parameters.
 * The test does not open a window.
 * The test does not run a pool turn.
 *
 * @author clayl (2026-10-29)
 */
// @aa-sdd PK-964 docs/specs/java/org/casserole/mood/mood.spec.md
// @aa-sdd PK-1430 docs/specs/common/stages.spec.md
// @aa-sdd PK-871 docs/specs/common/stages.spec.md
// @aa-sdd PK-771 docs/specs/common/stages.spec.md
// @aa-sdd PK-1431 docs/specs/common/stages.spec.md
// @aa-sdd PK-1094 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-648 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1083 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1083 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-652 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-691 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-692 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-694 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1089 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1089 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1089 docs/specs/common/formal-plan.spec.md
class PoolMoodRulesTest { // @aa-sdd PK-1083 PK-1084 PK-1089 PK-1092 PK-1094 docs/specs/common/formal-plan.spec.md

    private static final String RULES =
            MoodDesk.OPENER + "\n\nlantern-keep\n\n" + MoodDesk.CLOSER;

    /**
     * The method arranges a host line, a mood triple, a user line, a list with no mood, a stray triple after a user line, and a leading triple.
     * The method calls {@link ChatSession#porchRules}.
     * The host plus the triple {@code lantern-keep} returns the opener, a blank line, {@code lantern-keep}, a blank line, and the closer.
     * The host plus a user line returns an empty string.
     * A null list returns an empty string.
     * A user line between the host and the triple returns an empty string.
     * A leading triple returns the same rules text as the host-plus-triple case.
     *
     * @return The method returns nothing.
     */
    @Test
    void aSeatedPairBecomesPorchRules() {
        List<Message> seated = new ArrayList<Message>();
        seated.add(new SystemMessage("host line"));
        seated.add(new SystemMessage(MoodDesk.OPENER));
        seated.add(new SystemMessage(" lantern-keep "));
        seated.add(new SystemMessage(MoodDesk.CLOSER));
        seated.add(new UserMessage("next"));
        assertEquals(RULES, ChatSession.porchRules(seated));

        List<Message> off = new ArrayList<Message>();
        off.add(new SystemMessage("host line"));
        off.add(new UserMessage("next"));
        assertEquals("", ChatSession.porchRules(off));
        assertEquals("", ChatSession.porchRules(null));

        List<Message> stray = new ArrayList<Message>();
        stray.add(new SystemMessage("host line"));
        stray.add(new UserMessage("between"));
        stray.add(new SystemMessage(MoodDesk.OPENER));
        stray.add(new SystemMessage("lantern-keep"));
        stray.add(new SystemMessage(MoodDesk.CLOSER));
        assertEquals("", ChatSession.porchRules(stray));

        List<Message> leading = new ArrayList<Message>();
        leading.add(new SystemMessage(MoodDesk.OPENER));
        leading.add(new SystemMessage(" lantern-keep "));
        leading.add(new SystemMessage(MoodDesk.CLOSER));
        leading.add(new UserMessage("next"));
        assertEquals(RULES, ChatSession.porchRules(leading));
    }

    /**
     * The method arranges an {@link com.fasterxml.jackson.databind.ObjectMapper} and the rules text.
     * The method calls {@link PoolChatServer#porchSessionParams} with the directory {@code C:\pool}.
     * The filled rules set {@code cwd} to that directory.
     * {@code mcpServers} is an empty array.
     * {@code _meta.rules} equals the rules text.
     * {@code _meta} does not contain {@code yoloMode}, {@code autoMode}, or {@code systemPromptOverride}.
     * An empty rules string omits {@code _meta}.
     * A blank rules string omits {@code _meta}.
     * A null rules string omits {@code _meta}.
     *
     * @return The method returns nothing.
     */
    @Test
    void sessionNewCarriesTheRules() {
        ObjectMapper mapper = new ObjectMapper();
        String rules = RULES;
        ObjectNode on = PoolChatServer.porchSessionParams(mapper, "C:\\pool", rules);
        assertEquals("C:\\pool", on.get("cwd").asText());
        assertTrue(on.get("mcpServers").isArray());
        assertEquals(0, on.get("mcpServers").size());
        assertEquals(rules, on.get("_meta").get("rules").asText());
        assertFalse(on.get("_meta").has("yoloMode"));
        assertFalse(on.get("_meta").has("autoMode"));
        assertFalse(on.get("_meta").has("systemPromptOverride"));

        ObjectNode off = PoolChatServer.porchSessionParams(mapper, "C:\\pool", "");
        assertFalse(off.has("_meta"));
        ObjectNode blank = PoolChatServer.porchSessionParams(mapper, "C:\\pool", "  ");
        assertFalse(blank.has("_meta"));
        ObjectNode missing = PoolChatServer.porchSessionParams(mapper, "C:\\pool", null);
        assertFalse(missing.has("_meta"));
    }

    /**
     * The method arranges the rules text and the same text with {@code lantern-keep} replaced by {@code spare, dry}.
     * The method calls {@link PoolChatServer#rulesHold}.
     * A null stored value does not hold the rules.
     * A null stored value does not hold an empty string.
     * The rules do not hold the spare text, an empty string, or null.
     * An empty stored value does not hold the rules.
     * The rules hold an equal copy.
     * An empty stored value holds an empty string.
     * An empty stored value holds null.
     *
     * @return The method returns nothing.
     */
    @Test
    void aMoodChangeRefusesTheTail() {
        String spare = RULES.replace("lantern-keep", "spare, dry");
        assertFalse(PoolChatServer.rulesHold(null, RULES));
        assertFalse(PoolChatServer.rulesHold(null, ""));
        assertFalse(PoolChatServer.rulesHold(RULES, spare));
        assertFalse(PoolChatServer.rulesHold(RULES, ""));
        assertFalse(PoolChatServer.rulesHold(RULES, null));
        assertFalse(PoolChatServer.rulesHold("", RULES));
        assertTrue(PoolChatServer.rulesHold(RULES, RULES));
        assertTrue(PoolChatServer.rulesHold("", ""));
        assertTrue(PoolChatServer.epochAllows(0, 0));
        assertTrue(PoolChatServer.epochAllows(2, 2));
        assertFalse(PoolChatServer.epochAllows(1, 2));
        assertTrue(PoolChatServer.rulesHold("", null));
        assertTrue(PoolChatServer.cwdHolds("/pool", "/pool"));
        assertFalse(PoolChatServer.cwdHolds("/pool", "/pool/clay/SSH"));
        assertFalse(PoolChatServer.cwdHolds("/pool", "/pool/alice/CANVAS"));
        assertFalse(PoolChatServer.cwdHolds(null, "/pool"));
        assertFalse(PoolChatServer.cwdHolds("/pool", ""));
        java.util.ArrayList<String> live = new java.util.ArrayList<String>();
        live.add(PoolChatServer.seatKey("clay", PoolChat.Seat.SSH));
        live.add(PoolChatServer.seatKey("alice", PoolChat.Seat.SSH));
        live.add(PoolChatServer.seatKey("bob", PoolChat.Seat.CANVAS));
        live.add(PoolChatServer.seatKey("joe", PoolChat.Seat.SSH));
        assertTrue(PoolChatServer.admitsLogin(live, PoolChatServer.seatKey("clay", PoolChat.Seat.CANVAS), 4));
        assertFalse(PoolChatServer.admitsLogin(live, PoolChatServer.seatKey("ada", PoolChat.Seat.SSH), 4));
        assertTrue(PoolChatServer.admitsLogin(live, PoolChatServer.seatKey("ada", PoolChat.Seat.SSH), 6));
        assertTrue(PoolChatServer.ceilingLine(4).startsWith("pool ceiling"));
        assertTrue(PoolChatServer.ceilingLine(6).contains("6"));
        org.casserole.config.CasseroleProperties.Pool pool = new org.casserole.config.CasseroleProperties.Pool();
        assertEquals(4, pool.getCap());
        pool.setCap(6);
        assertEquals(6, pool.getCap());
        pool.setCap(0);
        assertEquals(4, pool.getCap());
    }

    /**
     * The method keeps a tool turn open until the agent says the turn ended.
     * A finished turn does not wait. A tool call waits even when a short line is already assembled. An empty assembly waits.
     */
    @Test
    void thePoolTurnWaitsForTheToolReply() {
        assertFalse(PoolChatServer.holdForTurnEnd(true, true, 0));
        assertTrue(PoolChatServer.holdForTurnEnd(false, true, 100));
        assertTrue(PoolChatServer.holdForTurnEnd(false, false, 0));
    }

    /**
     * The method creates the one directory the agent accepts.
     * The directory is created when it is missing. A second call creates nothing. No login folder is created under it.
     *
     * @throws Exception The method throws this exception when the temporary directory cannot be prepared.
     */
    @Test
    void theAcceptedDirectoryStaysOneFolder() throws Exception {
        File parent = Files.createTempDirectory("pool-one").toFile();
        File root = new File(parent, "pool-cwd");
        try {
            assertTrue(PoolChatServer.ensureAcceptedDirectory(root.getAbsolutePath()));
            assertTrue(root.isDirectory());
            assertEquals(0, root.list().length);
            assertFalse(PoolChatServer.ensureAcceptedDirectory(root.getAbsolutePath()));
            assertFalse(new File(root, "clay").exists());
            assertFalse(new File(root, "alice").exists());
        } finally {
            deleteTree(parent);
        }
    }

    /**
     * The method deletes a temporary tree.
     * A file is deleted. A directory is deleted after its children.
     *
     * @param file The file is the tree to remove.
     */
    private static void deleteTree(File file) {
        File[] kids = file.listFiles();
        if (kids != null) {
            for (int i = 0; i < kids.length; i++) {
                deleteTree(kids[i]);
            }
        }
        file.delete();
    }
}
