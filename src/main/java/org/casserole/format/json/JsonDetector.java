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

import org.casserole.format.FormatDetector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code JsonDetector} type matches a whole reply that is one JSON object or one JSON array.
 * JSON is the text format of objects, arrays, and scalars.
 * A scalar does not match.
 * A reply that is not valid JSON does not match.
 *
 * @author clayl (2026-08-25)
 */
public class JsonDetector implements FormatDetector {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * The {@code matches} method tests whether the whole reply is a JSON object or array.
     * A {@code null} reply returns {@code false}.
     * Trimmed text shorter than two characters returns {@code false}.
     * Text that does not start with a left brace or with {@code [} returns {@code false}.
     * Text that the parser cannot read returns {@code false}.
     * A parsed node matches only when it is an object or an array.
     *
     * @param raw The full assistant text.
     * @return {@code true} only when the trimmed text is one JSON object or one JSON array.
     */
    @Override
    public boolean matches(String raw) {
        if (raw == null) {
            return false;
        }
        String trimmed = raw.trim();
        if (trimmed.length() < 2) {
            return false;
        }
        char first = trimmed.charAt(0);
        if (first != '{' && first != '[') {
            return false;
        }
        try {
            JsonNode node = MAPPER.readTree(trimmed);
            return node != null && (node.isObject() || node.isArray());
        } catch (Exception ex) {
            return false;
        }
    }
}
/*****************************************************************************************
 * End of Class: JsonDetector.java 082526
 ****************************************************************************************/
