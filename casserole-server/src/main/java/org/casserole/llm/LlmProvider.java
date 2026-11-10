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

/**
 * The {@code LlmProvider} interface is one language-model backend the router can call.
 * The provider has an id. The provider turns one prompt into one chat response.
 * // @aa-sdd PK-0561 PK-0681 PK-0685 PK-0728 PK-0729 PK-0731 docs/specs/java/org/casserole/llm/llm.spec.md
 *
 * @author clayl (2026-09-02)
 */
public interface LlmProvider {

    /**
     * The {@code getId} method returns the provider id.
     * One implementation returns {@code ollama}. Another implementation returns {@code grok}.
     *
     * @return The return value is the provider id.
     */
    String getId();

    /**
     * The {@code complete} method sends one prompt and returns the completion.
     *
     * @param prompt The prompt is the assembled request.
     * @return The return value is the completion.
     * @throws Exception The method throws this exception when the backend fails.
     */
    ChatResponse complete(Prompt prompt) throws Exception;
}
/*****************************************************************************************
 * End of Class: LlmProvider.java 090226
 ****************************************************************************************/
