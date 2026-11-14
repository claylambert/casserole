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

// @aa-sdd PK-1065 docs/specs/common/formal-plan.spec.md
/**
 * The {@code GrokCallOptions} class holds the body fields for one Grok chat request.
 * {@link org.aetheris.ssh.AmwpFacade} fills an instance from the execute TOON. The execute TOON is the request record for one turn. The class has no {@code top_p} field. The class has no {@code num_ctx} field.
 *
 * The probe is the short Hello request that checks a model. The probe does not send {@link #temperature}. The probe does not send {@link #maxCompletionTokens}. The probe sends a completion cap of {@code 16}.
 * // @aa-sdd G-05 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-10-22)
 */
// @aa-sdd PK-388 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-387 docs/specs/java/org/casserole/llm/llm.spec.md
public final class GrokCallOptions {

    /**
     * The value is the temperature used for an ordinary chat request.
     * The number is {@code 1.0}. The probe does not send this value.
     */
    public static final double FACTORY_TEMPERATURE = 1.0d;

    /**
     * The value is the completion cap used for an ordinary chat request.
     * The number is {@code 128000}. The probe sends {@code 16} instead.
     */
    public static final int FACTORY_MAX_COMPLETION_TOKENS = 128000;

    /**
     * The flag is true when the chat body sets {@code stream} to true.
     * A false flag sends {@code stream} as false.
     */
    public boolean thinkStream;

    /**
     * The text is the reasoning level for the chat body.
     * An empty string becomes {@code high} when {@link #sendReasoningEffort} is true.
     */
    public String reasoningEffort = "";

    /**
     * The flag chooses whether the chat body includes {@code reasoning_effort}.
     * A false flag leaves that field off the request. A true flag sends the level.
     */
    public boolean sendReasoningEffort = true;

    /**
     * The value is the sampling temperature for a chat request.
     * The probe leaves temperature unset.
     */
    public double temperature = FACTORY_TEMPERATURE;

    /**
     * The value is the completion cap for a chat request.
     * The probe does not use this value.
     */
    public int maxCompletionTokens = FACTORY_MAX_COMPLETION_TOKENS;

    /**
     * The constructor leaves the ordinary chat values in place.
     * Thinking stays off because {@link #thinkStream} starts false. {@link #sendReasoningEffort} starts true.
     */
    public GrokCallOptions() {
    }
}
/*****************************************************************************************
 * End of Class: GrokCallOptions.java 102226
 ****************************************************************************************/
