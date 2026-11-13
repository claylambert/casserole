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

// @aa-sdd L40 app-design.md
/**
 * The {@code LlmService} interface is the boundary for language-model backend communication.
 * Spring AI is used on this boundary. The caller passes one prompt and receives one chat response.
 *
 * The prompt is the assembled history plus the new user turn. The chat response is the completion from the backend. The response is never null.
 *
 * @author clayl (2026-08-24)
 */
// @aa-sdd GK-1203 aetheris-grok-api.draft.001.md
// @aa-sdd PK-683 docs/specs/common/grok.spec.md
// @aa-sdd PK-735 docs/specs/common/stages.spec.md
// @aa-sdd PK-736 docs/specs/common/stages.spec.md
// @aa-sdd GK-1404 aetheris-pre-plan-stage-1.001.md
// @aa-sdd PK-961 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-894 docs/specs/common/stages.spec.md
// @aa-sdd PK-780 docs/specs/common/stages.spec.md
// @aa-sdd GK-1705 aetheris-stages-1-3.finalize.md
// @aa-sdd PK-781 docs/specs/common/stages.spec.md
// @aa-sdd GK-1732 aetheris-technical-specification.finalize.md
// @aa-sdd PK-392 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-399 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-393 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-721 docs/specs/common/server.spec.md
// @aa-sdd PK-722 docs/specs/common/server.spec.md
// @aa-sdd PK-723 docs/specs/common/server.spec.md
// @aa-sdd PK-724 docs/specs/common/server.spec.md
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
