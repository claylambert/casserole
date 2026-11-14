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

package org.casserole.format.json;

import org.casserole.format.FormatContext;
import org.casserole.format.FormatRenderer;
import org.casserole.format.FormatResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code JsonRenderer} type pretty-prints a JSON reply.
 * JSON is the text format of objects and arrays.
 * The printer indents the tree.
 * The renderer does not word-wrap the result.
 *
 * @author clayl (2026-08-25)
 */
public class JsonRenderer implements FormatRenderer {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * The {@code render} method pretty-prints the trimmed reply.
     * The column width in {@code ctx} is not used.
     * If the parser accepts the text, the method returns the indented form with body color on.
     * If the parser throws, the method returns the original text with body color on.
     *
     * @param raw The original assistant text.
     * @param ctx The render settings, which this method does not read.
     * @return The indented JSON, or the original text when parsing fails.
     */
    @Override
    public FormatResult render(String raw, FormatContext ctx) {
        try {
            JsonNode node = MAPPER.readTree(raw.trim());
            String pretty = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(node);
            return new FormatResult(pretty, true);
        } catch (Exception ex) {
            return new FormatResult(raw, true);
        }
    }
}
/*****************************************************************************************
 * End of Class: JsonRenderer.java 082526
 ****************************************************************************************/
