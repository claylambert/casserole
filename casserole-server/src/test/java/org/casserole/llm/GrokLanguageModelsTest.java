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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

// @aa-sdd PK-8253 docs/specs/java/org/casserole/llm/llm.spec.md
/**
 * The class checks the language-model catalog parser.
 * The test uses fixture text.
 * The test does not call the network.
 * The test does not read a key.
 *
 * @author clayl (2026-11-08)
 */
class GrokLanguageModelsTest {

    /**
     * The method arranges a {@code models} array with an id, a repeated alias, a broken id, and a name.
     * The method calls {@link GrokLlmService#languageModelIds}.
     * The list keeps the trimmed id, the new alias, the name, and the later id.
     * A duplicate is absent.
     * An id that contains a newline is absent.
     *
     * @return The method returns nothing.
     */
    @Test
    void aModelsArrayKeepsTheIdThenTheAlias() {
        String body = "{\"models\":["
                + "{\"id\":\" grok-4.7 \",\"aliases\":[\"grok-4.7\",\"alias-a\",\"bad\\nid\"]},"
                + "{\"id\":\"has\\nbreak\"},"
                + "{\"name\":\"only-name\"},"
                + "{\"id\":\"second\"}"
                + "]}";
        List<String> ids = GrokLlmService.languageModelIds(body);
        assertEquals(Arrays.asList("grok-4.7", "alias-a", "only-name", "second"), ids);
    }

    /**
     * The method arranges a {@code data} array and a top-level array of strings.
     * The method calls {@link GrokLlmService#languageModelIds}.
     * The data id is kept.
     * The top-level list drops a blank, a duplicate, and an id that contains a carriage return.
     *
     * @return The method returns nothing.
     */
    @Test
    void aDataArrayAndATopLevelArrayAreRead() {
        List<String> data = GrokLlmService.languageModelIds("{\"data\":[{\"id\":\"from-data\"}]}");
        assertEquals(Collections.singletonList("from-data"), data);
        List<String> top = GrokLlmService.languageModelIds("[\"a\",\"a\",\"\",\"b\\nx\"]");
        assertEquals(Collections.singletonList("a"), top);
    }

    /**
     * The method arranges text that is not a catalog.
     * The method calls {@link GrokLlmService#languageModelIds}.
     * Each call returns an empty list.
     *
     * @return The method returns nothing.
     */
    @Test
    void garbageReturnsAnEmptyList() {
        assertEquals(0, GrokLlmService.languageModelIds(null).size());
        assertEquals(0, GrokLlmService.languageModelIds("not-json").size());
        assertEquals(0, GrokLlmService.languageModelIds("{\"nope\":1}").size());
        assertEquals(0, GrokLlmService.languageModelIds("{\"models\":{\"id\":\"nope\"}}").size());
    }

    /**
     * The method arranges an empty id list and a two-id list.
     * The method calls {@link GrokLlmService#catalogText}.
     * The empty list is the header only.
     * The two-id list has one {@code model:} line for each id.
     *
     * @return The method returns nothing.
     */
    @Test
    void theCatalogBodyIsHeaderOnlyWhenTheListIsEmpty() {
        assertEquals("grok_models_result:\n", GrokLlmService.catalogText(Collections.<String>emptyList()));
        assertEquals("grok_models_result:\n", GrokLlmService.catalogText(null));
        String body = GrokLlmService.catalogText(Arrays.asList("grok-4.7", "alias-a"));
        assertEquals("grok_models_result:\nmodel: grok-4.7\nmodel: alias-a\n", body);
        assertFalse(body.contains("key"));
    }
}
