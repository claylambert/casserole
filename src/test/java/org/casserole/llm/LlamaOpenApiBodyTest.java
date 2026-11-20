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

package org.casserole.llm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// @aa-sdd aetheris-plan-f-278.001.md
/**
 * The class checks the llama-openapi chat body and the model-list text.
 * The test does not open a window.
 * The test does not open a socket.
 *
 * @author clayl (2026-11-19)
 */
class LlamaOpenApiBodyTest {

    /** The mapper reads the body the service built. */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * The method builds a body with an unset temperature.
     * The body contains {@code model}.
     * The body does not contain {@code temperature}.
     * The body does not contain {@code max_tokens}.
     * The body does not contain {@code n_predict}.
     *
     * @return The method returns nothing.
     * @throws Exception The method throws this exception when the body cannot be built.
     */
    @Test
    void anUnsetTemperatureStaysOffTheBody() throws Exception {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new UserMessage("Hello"));
        String json = LlamaCppLlmService.requestBody("Falcon3-3B", messages, new LlamaCppCallOptions(), false);
        JsonNode root = MAPPER.readTree(json);
        assertEquals("Falcon3-3B", root.get("model").asText());
        assertFalse(root.has("temperature"));
        assertFalse(root.has("max_tokens"));
        assertFalse(root.has("n_predict"));
        assertFalse(root.get("stream").asBoolean());
    }

    /**
     * The method builds a body with temperature and a completion cap set.
     * The body contains that temperature.
     * The body contains {@code max_tokens}.
     * The body does not contain {@code n_predict}.
     *
     * @return The method returns nothing.
     * @throws Exception The method throws this exception when the body cannot be built.
     */
    @Test
    void aSetTemperatureIsOnTheBody() throws Exception {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new UserMessage("Hello"));
        LlamaCppCallOptions options = new LlamaCppCallOptions();
        options.temperature = Double.valueOf(0.8d);
        options.maxTokens = Integer.valueOf(512);
        String json = LlamaCppLlmService.requestBody("Falcon3-3B", messages, options, true);
        JsonNode root = MAPPER.readTree(json);
        assertEquals(0.8d, root.get("temperature").asDouble(), 0.0001d);
        assertEquals(512, root.get("max_tokens").asInt());
        assertFalse(root.has("n_predict"));
        assertTrue(root.get("stream").asBoolean());
    }

    /**
     * The method builds a body from a system line, a user line, and an assistant line.
     * Each message keeps its role.
     * Each message keeps its text.
     *
     * @return The method returns nothing.
     * @throws Exception The method throws this exception when the body cannot be built.
     */
    @Test
    void messagesKeepTheirRoles() throws Exception {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new SystemMessage("seat"));
        messages.add(new UserMessage("Hello"));
        messages.add(new AssistantMessage("Hi"));
        String json = LlamaCppLlmService.requestBody("Falcon3-3B", messages, new LlamaCppCallOptions(), false);
        JsonNode wire = MAPPER.readTree(json).get("messages");
        assertEquals(3, wire.size());
        assertEquals("system", wire.get(0).get("role").asText());
        assertEquals("seat", wire.get(0).get("content").asText());
        assertEquals("user", wire.get(1).get("role").asText());
        assertEquals("Hello", wire.get(1).get("content").asText());
        assertEquals("assistant", wire.get(2).get("role").asText());
        assertEquals("Hi", wire.get(2).get("content").asText());
    }

    /**
     * The method passes a null instruction list.
     * The call fails with {@code llama empty prompt}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aNullInstructionListFails() {
        try {
            LlamaCppLlmService.requestBody("Falcon3-3B", null, new LlamaCppCallOptions(), false);
            fail("a null list must fail");
        } catch (Exception ex) {
            assertEquals("llama empty prompt", ex.getMessage());
        }
    }

    /**
     * The method passes a message whose role is not system, user, or assistant.
     * The call fails with {@code llama unknown role}.
     *
     * @return The method returns nothing.
     */
    @Test
    void anUnknownRoleFails() {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message() {
            /**
             * The method returns the tool role.
             *
             * @return The return value is {@link MessageType#TOOL}.
             */
            @Override
            public MessageType getMessageType() {
                return MessageType.TOOL;
            }

            /**
             * The method returns the message text.
             *
             * @return The return value is {@code x}.
             */
            @Override
            public String getText() {
                return "x";
            }

            /**
             * The method returns an empty metadata map.
             *
             * @return The return value is an empty map.
             */
            @Override
            public Map<String, Object> getMetadata() {
                return Collections.emptyMap();
            }
        });
        try {
            LlamaCppLlmService.requestBody("Falcon3-3B", messages, new LlamaCppCallOptions(), false);
            fail("an unknown role must fail");
        } catch (Exception ex) {
            assertEquals("llama unknown role", ex.getMessage());
        }
    }

    /**
     * The method parses a models document with two ids.
     * The reply keeps reply order.
     * Each id is one {@code model:} line.
     *
     * @return The method returns nothing.
     */
    @Test
    void dataIdsBecomeModelLines() {
        String text = LlamaCppLlmService.catalogText(
                "{\"data\":[{\"id\":\"Falcon3-3B\"},{\"id\":\"\"},{\"id\":\"Falcon3-10B\"}]}");
        assertEquals("llama_models_result:\nmodel: Falcon3-3B\nmodel: Falcon3-10B\n", text);
    }

    /**
     * The method parses a body with no {@code data} array and a null body.
     * Each result is the header only.
     *
     * @return The method returns nothing.
     */
    @Test
    void aMissingDataArrayReturnsTheHeaderOnly() {
        assertEquals("llama_models_result:\n", LlamaCppLlmService.catalogText("{\"object\":\"list\"}"));
        assertEquals("llama_models_result:\n", LlamaCppLlmService.catalogText(null));
    }

    /**
     * The method parses path ids and a short id.
     * A path id becomes the alias.
     * A second path becomes that same alias and stays one line.
     * A short id stays.
     * A blank alias keeps the path.
     * A name that ends with {@code .gguf} becomes the alias.
     *
     * @return The method returns nothing.
     */
    @Test
    void aPathIdBecomesTheAlias() {
        String body = "{\"data\":[{\"id\":\"C:/models/Falcon3-3B.gguf\"},"
                + "{\"id\":\"D:\\\\models\\\\other.gguf\"},{\"id\":\"Falcon3-10B\"}]}";
        String text = LlamaCppLlmService.catalogText(body, "Falcon3-3B");
        assertEquals("llama_models_result:\nmodel: Falcon3-3B\nmodel: Falcon3-10B\n", text);
        String kept = LlamaCppLlmService.catalogText(
                "{\"data\":[{\"id\":\"C:/models/Falcon3-3B.gguf\"}]}", "  ");
        assertEquals("llama_models_result:\nmodel: C:/models/Falcon3-3B.gguf\n", kept);
        String gguf = LlamaCppLlmService.catalogText(
                "{\"data\":[{\"id\":\"Falcon3-3B.gguf\"}]}", "Falcon3-3B");
        assertEquals("llama_models_result:\nmodel: Falcon3-3B\n", gguf);
        assertEquals(true, LlamaCppLlmService.pathShaped("C:/models/Falcon3-3B.gguf"));
        assertEquals(true, LlamaCppLlmService.pathShaped("Falcon3-3B.GGUF"));
        assertEquals(false, LlamaCppLlmService.pathShaped("Falcon3-3B"));
        assertEquals(false, LlamaCppLlmService.pathShaped(null));
    }

    /**
     * The method writes a tokenize body and reads a tokens array.
     * The body has one {@code content} field.
     * A tokens array returns its length.
     * A missing array returns {@code -1}.
     * The method does not open a socket.
     *
     * @return The method returns nothing.
     */
    @Test
    void aTokenizeBodyCountsTheTokensArray() throws Exception {
        JsonNode body = new ObjectMapper().readTree(LlamaCppLlmService.tokenizeBody("say \"hi\""));
        assertEquals("say \"hi\"", body.get("content").asText());
        assertEquals(1, body.size());
        assertEquals("{\"content\":\"\"}", LlamaCppLlmService.tokenizeBody(null));
        assertEquals(1, LlamaCppLlmService.tokenCount("{\"tokens\":[1]}"));
        assertEquals(0, LlamaCppLlmService.tokenCount("{\"tokens\":[]}"));
        assertEquals(-1, LlamaCppLlmService.tokenCount("{\"ok\":true}"));
        assertEquals(-1, LlamaCppLlmService.tokenCount(null));
    }

    /**
     * The method writes an apply-template body and reads the prompt field.
     * The body has {@code messages} and no {@code model}.
     * A missing prompt field returns null.
     * The method does not open a socket.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because the body writer declares it.
     */
    @Test
    void anApplyTemplateBodyReadsThePrompt() throws Exception {
        List<Message> messages = new ArrayList<Message>();
        messages.add(new SystemMessage("a"));
        messages.add(new UserMessage("b"));
        JsonNode body = MAPPER.readTree(LlamaCppLlmService.applyTemplateBody(messages));
        assertEquals(1, body.size());
        assertFalse(body.has("model"));
        assertFalse(body.has("stream"));
        assertEquals("system", body.get("messages").get(0).get("role").asText());
        assertEquals("a", body.get("messages").get(0).get("content").asText());
        assertEquals("user", body.get("messages").get(1).get("role").asText());
        assertEquals("b", body.get("messages").get(1).get("content").asText());
        assertEquals("hello", LlamaCppLlmService.templatePrompt("{\"prompt\":\"hello\"}"));
        assertNull(LlamaCppLlmService.templatePrompt("{\"ok\":true}"));
        assertNull(LlamaCppLlmService.templatePrompt(null));
        assertNull(LlamaCppLlmService.templatePrompt("not-json"));
    }
}
