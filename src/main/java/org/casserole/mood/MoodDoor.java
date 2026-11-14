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

package org.casserole.mood;

import java.util.List;

import org.springframework.ai.chat.messages.Message;

// @aa-sdd PK-1094 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md
/**
 * The {@code MoodDoor} type is the contract for one mood line on a stored message list.
 * The shared implementation is {@link MoodDesk}.
 * The {@link #seat} method rebuilds the list as the configured system prompt, then the opener, the constructed mood, and the closer, then the remaining turns.
 * The method removes a prior triple, including a triple whose mood text is not the line now stored.
 * The method also removes a stored pair that still uses {@link MoodDesk#LEGACY}.
 * The method leaves the turns in place when the next line is blank.
 * The {@link #adopt} method reads the constructed mood that is already stored.
 * The {@link #porchRules} method returns the mood rules text and leaves the configured system prompt out.
 * // @aa-sdd L40 aa-sdd-source-prose.md#MoodDoor
 *
 * @author clayl (2026-11-03)
 */
// @aa-sdd PK-964 docs/specs/java/org/casserole/mood/mood.spec.md
// @aa-sdd PK-691 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-692 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-1386 docs/specs/java/org/casserole/mood/mood.spec.md
// @aa-sdd PK-964 docs/specs/java/org/casserole/mood/mood.spec.md
// @aa-sdd PK-1094 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-643 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-644 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-401 docs/specs/java/org/casserole/mood/mood.spec.md
public interface MoodDoor {

    /**
     * The {@code seat} method returns the system prompt, then one mood triple, then the turns.
     * The triple is {@link MoodDesk#OPENER}, the constructed mood, and {@link MoodDesk#CLOSER}.
     * The method removes the triple when the next line is blank.
     * The method removes a prior triple, including a triple whose mood is not {@code nextLine}.
     * The method removes a stored pair that still uses {@link MoodDesk#LEGACY}.
     * The turns stay in the result.
     *
     * @param stored The current messages, or null when the list is absent.
     * @param hostPrompt The configured system prompt, or empty when no prompt is configured.
     * @param previousLine The previous mood line, or null.
     * @param nextLine The mood line to store, or null to clear the triple.
     * @return The replacement transcript.
     */
    List<Message> seat(List<Message> stored, String hostPrompt, String previousLine, String nextLine);

    /**
     * The {@code adopt} method returns the mood line already stored after the system prompt, or at the head when that prompt is absent.
     *
     * @param stored The current messages, or null when the list is absent.
     * @return The trimmed line, or null when no mood is stored.
     */
    String adopt(List<Message> stored);

    /**
     * The {@code porchRules} method returns the mood rules for a pool session.
     * The pair sits after a system prompt when a prompt is present, and at the head when the prompt is blank.
     * The system prompt stays out of the returned text.
     * The method returns an empty string when the pair is absent.
     *
     * @param messages The prompt messages or the stored region.
     * @return The rules text, or an empty string.
     */
    String porchRules(List<Message> messages);
}
