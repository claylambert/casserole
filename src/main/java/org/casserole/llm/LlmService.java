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

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

// @aa-sdd PK-0535 PK-0537 docs/specs/java/org/casserole/casserole.spec.md
/**
 * The {@code LlmService} interface is the boundary for language-model backend communication.
 * Spring AI is used on this boundary. The caller passes one prompt and receives one chat response.
 *
 * The prompt is the assembled history plus the new user turn. The chat response is the completion from the backend. The response is never null.
 *
 * @author clayl (2026-08-24)
 */
public interface LlmService {

    /**
     * The {@code complete} method sends one assembled prompt and waits for one completion.
     * The method throws an exception when the backend is unreachable. The method throws an exception when the call times out. The method throws an exception when the backend returns nothing usable.
     *
     * @param prompt The prompt is the assembled history plus the new user turn.
     * @return The return value is the model response.
     * @throws Exception The method throws this exception when the backend cannot return a usable completion.
     */
    ChatResponse complete(Prompt prompt) throws Exception;
}
/*****************************************************************************************
 * End of Class: LlmService.java 082426
 ****************************************************************************************/
