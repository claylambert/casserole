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

package org.aetheris.ssh;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

import org.aetheris.config.AetherisYaml;
import org.aetheris.swing.AetherisStage2;
import org.aetheris.wire.AmwpCodec;
import org.aetheris.wire.AmwpConstants;
import org.aetheris.wire.AmwpFrame;
import org.aetheris.wire.AmwpToon;
import org.casserole.chat.ChatSession;
import org.casserole.config.CasseroleProperties;
import org.casserole.lamina.Lamina;
import org.casserole.lamina.LaminaHandle;
import org.casserole.lamina.LaminaTask;
import org.casserole.lamina.Lane;
import org.casserole.llm.GrokCallOptions;
import org.casserole.llm.GrokLlmService;
import org.casserole.llm.LlmRouter;
import org.casserole.llm.SpringAiOllamaLlmService;
import org.casserole.mood.MoodDesk;
import org.casserole.pool.PoolChat;
import org.casserole.pool.PoolChatServer;
import org.roux.RouxEngine;
import org.roux.model.Role;
import org.roux.session.OpenResult;
import org.roux.token.HeuristicTokenCounter;
import org.roux.session.RegionOwner;
import org.roux.spring.RouxChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

// @aa-sdd PK-0563 PK-0686 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0826 PK-0873 PK-0920 PK-0967 PK-1014 PK-1061 PK-1108 PK-1155 PK-1202 PK-1249 PK-1296 PK-1343 PK-1390 PK-1437 PK-1484 PK-1531 PK-1579 PK-1626 PK-1673 PK-1720 PK-1767 PK-1814 PK-1861 PK-1908 PK-1955 PK-2002 PK-2050 PK-2097 PK-2144 PK-2191 PK-2239 PK-2287 PK-2335 PK-2382 PK-2429 PK-2477 PK-2524 PK-2571 PK-2618 PK-2666 PK-2713 PK-2761 PK-2808 PK-2855 PK-2902 PK-2949 PK-2996 PK-3043 PK-3090 PK-3137 PK-3185 PK-3232 PK-3279 PK-3327 PK-3374 PK-3421 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-0605 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0622 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0633 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0490 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-0499 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-3524 PK-3554 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-3579 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-0477 docs/specs/java/org/aetheris/wire/wire.spec.md
/**
 * The {@code AmwpFacade} class is the server AMWP Scope A handler.
 * Bantha Roux is RAM only.
 * Cell turns go to that RAM region.
 * Cells never enter the VT100 Roux.
 * The execute semaphore holds 3 permits.
 * {@link Lane#LLM_EXEC} stays a pool of 4 threads.
 * {@link ChatShell} does not take this semaphore.
 * A caller sends frames through {@link #handle}.
 * A caller sends one console turn through {@link #completeTurn}.
 * A tool frame is refused.
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0472 PK-0473 PK-0474 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-3469 PK-3474 PK-3484 PK-3470 PK-3475 PK-3485 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-09-02)
 */
@Component
public class AmwpFacade {

    /**
     * The depth is the day-one tool depth.
     * The depth is one hop, then stop.
     * The allowlist is empty.
     */
    private static final int TOOL_DEPTH = 1;

    private final RouxChatMemory rouxMemory;
    private final LlmRouter llmRouter;
    private final Lamina lamina;
    private final CasseroleProperties properties;
    private final int numCtx;
    /**
     * The value is {@code spring.ai.ollama.base-url} with no trailing slash.
     */
    private final String ollamaBaseUrl;
    /**
     * The value is {@code spring.ai.ollama.chat.options.model}.
     * Empty means a blank show id stays blank.
     */
    private final String ollamaModel;
    private final ConcurrentHashMap<String, LaminaHandle> inFlight;
    /**
     * The map holds the token of the execute that is still allowed to reply.
     * Cancel removes that token.
     */
    private final ConcurrentHashMap<String, Long> liveToken;
    private final ConcurrentHashMap<String, Object> regionLocks;
    private final Semaphore executeSlots;
    private final AtomicLong nextToken;
    /**
     * The map holds cell prompt HTTP calls.
     * The probe future stays on {@link #probeGate}.
     */
    private final ConcurrentHashMap<String, GrokLlmService.HttpSlot> cellHttp;
    private final ProbeGate probeGate;
    /**
     * The chat keeps one seat per SSH user.
     * A blank host is a local stdio child. A set host is a WebSocket dial.
     * The chat is not a Spring bean.
     */
    private final PoolChat poolChat;
    /**
     * The service reads the Grok key and the language-model catalog.
     * The catalog ask does not run on the execute path.
     */
    private final GrokLlmService grokLlm;
    /**
     * The map holds the mux writer for a console thought.
     * The key is the sanitized SSH user.
     */
    private final ConcurrentHashMap<String, AmwpReplySink> consoleSinks;

    /**
     * The constructor stores the collaborators and builds the maps.
     * The execute semaphore starts with 3 permits.
     * A null Ollama model is stored as empty.
     * A model value is trimmed.
     * The base URL loses one trailing slash through {@link #trimSlash}.
     * The pool chat is a new {@link PoolChatServer} for these properties.
     *
     * @param rouxMemory The memory is the Roux chat memory.
     * @param llmRouter The router is the provider map.
     * @param lamina The lamina is the lane pool.
     * @param properties The properties are the server settings.
     * @param grokLlm The service lists Grok language models. A null service answers the catalog with the header only.
     * @param numCtx The number is {@code spring.ai.ollama.chat.options.num-ctx}. {@code 0} omits {@code num_ctx}.
     * @param ollamaBaseUrl The URL is {@code spring.ai.ollama.base-url}. Empty uses {@code http://localhost:11434}.
     * @param ollamaModel The model is {@code spring.ai.ollama.chat.options.model}. Empty stays empty.
     */
    public AmwpFacade(RouxChatMemory rouxMemory, LlmRouter llmRouter, Lamina lamina,
            CasseroleProperties properties, GrokLlmService grokLlm,
            @Value("${spring.ai.ollama.chat.options.num-ctx:0}") int numCtx,
            @Value("${spring.ai.ollama.base-url:http://localhost:11434}") String ollamaBaseUrl,
            @Value("${spring.ai.ollama.chat.options.model:}") String ollamaModel) {
        this.rouxMemory = rouxMemory;
        this.llmRouter = llmRouter;
        this.lamina = lamina;
        this.properties = properties;
        this.numCtx = numCtx;
        this.ollamaBaseUrl = trimSlash(ollamaBaseUrl);
        this.ollamaModel = ollamaModel == null ? "" : ollamaModel.trim();
        this.inFlight = new ConcurrentHashMap<String, LaminaHandle>();
        this.liveToken = new ConcurrentHashMap<String, Long>();
        this.regionLocks = new ConcurrentHashMap<String, Object>();
        this.executeSlots = new Semaphore(3);
        this.nextToken = new AtomicLong();
        this.cellHttp = new ConcurrentHashMap<String, GrokLlmService.HttpSlot>();
        this.consoleSinks = new ConcurrentHashMap<String, AmwpReplySink>();
        this.probeGate = new ProbeGate();
        this.grokLlm = grokLlm;
        // @aa-sdd PK-8252 docs/specs/java/org/casserole/casserole.spec.md
        this.poolChat = new PoolChatServer(lamina, properties);
    }

    /**
     * The {@code initFacts} method builds the init body for this process.
     * The air-gap word comes from {@link AetherisYaml#readProcessHome}.
     * The body also carries the server num-ctx integer, the managed-token count, and the head count.
     * The bytes are {@link AmwpToon#encodeFacts}.
     *
     * @return The return value is the session-facts text.
     */
    public byte[] initFacts() {
        AetherisYaml home = AetherisYaml.readProcessHome();
        return AmwpToon.encodeFacts(home.airGapWord(), numCtx, properties.getChat().getManagedTokens(),
                properties.getChat().getHeadCount());
    }

    /**
     * The {@code banthaRegion} method builds the Bantha region id.
     * The id is the user name, a dot, and {@code aetheris}.
     * // @aa-sdd PK-0472 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-3469 PK-3474 PK-3484 docs/specs/java/org/roux/roux.spec.md
     *
     * @param user The user is the sanitized user name.
     * @return The return value is {@code user.aetheris}.
     */
    public static String banthaRegion(String user) {
        return user + ".aetheris";
    }

    /**
     * The {@code openBantha} method opens the RAM Bantha region for this user.
     * The method does not take a file lock.
     * {@link RegionOwner#sanitize} prepares the user name.
     * An {@link IllegalArgumentException} returns {@link OpenResult#refused} with {@code invalid username}.
     * Any other user opens {@link #banthaRegion} through {@link RouxChatMemory#openRam}.
     * // @aa-sdd PK-0472 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-3469 PK-3474 PK-3484 docs/specs/java/org/roux/roux.spec.md
     *
     * @param user The user is the SSH user.
     * @return The return value is the open result.
     */
    public OpenResult openBantha(String user) {
        String sanitized;
        try {
            sanitized = RegionOwner.sanitize(user);
        } catch (IllegalArgumentException ex) {
            return OpenResult.refused("invalid username");
        }
        return rouxMemory.openRam(banthaRegion(sanitized));
    }

    /**
     * The {@code handle} method accepts one Scope A, stream, or tool frame.
     * A null frame returns without a reply.
     * A null sink returns without a reply.
     * A keepalive returns without a reply.
     * A tool frame replies {@code failed} and {@code no tool is invocable}, then returns.
     * A cancel drops the live token, cancels the cell HTTP call, and cancels the in-flight task.
     * A type other than data returns without a reply.
     * A channel A body that starts with {@code show:} is answered off the execute path.
     * A channel A body that starts with {@code tags:} is answered off the execute path.
     * A channel A body that starts with {@code grok_models:} is answered off the execute path.
     * A cell that is already running replies {@code cell-busy}.
     * Any other data frame takes a new token and waits for an execute permit.
     * The waiter thread is named {@code aetheris-execute-wait} and is a daemon.
     * The task lane is {@link Lane#LLM_EXEC} and the task name is {@code aetheris-execute}.
     * The task releases the permit in {@code finally}.
     *
     * @param user The user is the SSH user.
     * @param frame The frame is the inbound frame.
     * @param replyTo The sink is the mux writer.
     */
    public void handle(final String user, AmwpFrame frame, final AmwpReplySink replyTo) {
        if (frame == null || replyTo == null) {
            return;
        }
        if (frame.getType() == AmwpConstants.TYPE_KEEPALIVE) {
            return;
        }
        if (frame.getChannel() == AmwpConstants.CHANNEL_TOOL) {
            byte[] toolCell = AmwpCodec.cellIdOf(frame.getPayload());
            if (!toolAllowed("")) {
                replyReason(replyTo, toolCell, "failed", "no tool is invocable");
            }
            return;
        }
        if (frame.getType() == AmwpConstants.TYPE_CANCEL) {
            byte[] id = AmwpCodec.cellIdOf(frame.getPayload());
            String key = uuidString(id);
            liveToken.remove(key);
            GrokLlmService.HttpSlot slot = cellHttp.remove(key);
            if (slot != null && slot.future != null) {
                slot.future.cancel(true);
            }
            LaminaHandle handle = inFlight.remove(key);
            if (handle != null) {
                handle.cancel(true);
            }
            return;
        }
        if (frame.getType() != AmwpConstants.TYPE_DATA) {
            return;
        }
        final byte[] body = AmwpCodec.stripFragPrefix(frame.getPayload());
        final byte[] cellId = AmwpCodec.cellIdOf(frame.getPayload());
        if (frame.getChannel() == AmwpConstants.CHANNEL_A) {
            String showModel = showId(body);
            if (showModel != null) {
                answerShow(replyTo, cellId, showModel);
                return;
            }
            if (tagsAsk(body)) {
                answerTags(replyTo, cellId);
                return;
            }
            if (grokModelsAsk(body)) {
                answerGrokModels(replyTo, cellId);
                return;
            }
        }
        final String key = uuidString(cellId);
        LaminaHandle existing = inFlight.get(key);
        if (existing != null && !existing.isDone()) {
            replyReason(replyTo, cellId, "cell-busy", "cell-busy");
            return;
        }
        final long token = nextToken.incrementAndGet();
        liveToken.put(key, Long.valueOf(token));
        final LaminaHandle placeholder = new LaminaHandle(new OpenFuture());
        inFlight.put(key, placeholder);
        /**
         * The runnable calls {@link AmwpFacade#execute}.
         * The permit is released afterward.
         * {@link AmwpFacade#releaseMine} drops this token when it is still live.
         */
        final LaminaTask task = new LaminaTask(Lane.LLM_EXEC, "aetheris-execute", new Runnable() {
            // @aa-sdd PK-0478 docs/specs/java/org/aetheris/wire/wire.spec.md
            @Override
            public void run() {
                try {
                    execute(user, key, token, cellId, body, replyTo);
                } finally {
                    executeSlots.release();
                    releaseMine(key, token);
                }
            }
        });
        /**
         * The runnable waits for an execute permit.
         * A token that is no longer live releases the permit and drops the placeholder.
         * A live token submits the task and leaves the permit with that task.
         * An interrupt restores the interrupt flag and drops the placeholder when the task was not submitted.
         * A runtime failure replies {@code failed} and drops the placeholder when the task was not submitted.
         */
        Thread waiter = new Thread(new Runnable() {
            // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
            // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
            // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
            // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
            // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
            // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            @Override
            public void run() {
                boolean held = false;
                boolean submitted = false;
                try {
                    executeSlots.acquire();
                    held = true;
                    if (!stillMine(key, token)) {
                        executeSlots.release();
                        held = false;
                        inFlight.remove(key, placeholder);
                        return;
                    }
                    inFlight.put(key, lamina.submit(task));
                    submitted = true;
                    held = false;
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    if (held && !submitted) {
                        executeSlots.release();
                    }
                    liveToken.remove(key);
                    inFlight.remove(key, placeholder);
                } catch (RuntimeException ex) {
                    if (held && !submitted) {
                        executeSlots.release();
                    }
                    replyReason(replyTo, cellId, "failed", garnered(ex));
                    liveToken.remove(key);
                    inFlight.remove(key, placeholder);
                }
            }
        }, "aetheris-execute-wait");
        waiter.setDaemon(true);
        waiter.start();
    }

    /**
     * The {@code toolAllowed} method reports whether a tool may run.
     * The depth is 1.
     * The allowlist is empty.
     * No name passes.
     * A depth below 1 returns false.
     * A null name returns false.
     * An empty name returns false.
     * Every other name returns false.
     *
     * @param toolName The tool name is the tool.
     * @return The return value is false.
     */
    private boolean toolAllowed(String toolName) {
        if (TOOL_DEPTH < 1 || toolName == null || toolName.length() == 0) {
            return false;
        }
        return false;
    }

    /**
     * The {@code showId} method reads a model id from a {@code show:} line.
     * A null body returns null.
     * An empty body returns null.
     * The first line is the text before the first newline.
     * A first line that does not start with {@code show:} returns null.
     * {@code cell_id:} is not a show.
     * The returned id is the trimmed text after {@code show:}.
     * That id may be empty.
     *
     * @param body The body is the stripped payload.
     * @return The return value is the model id, or null when this frame is not a show.
     */
    private static String showId(byte[] body) {
        if (body == null || body.length == 0) {
            return null;
        }
        String text = new String(body, StandardCharsets.UTF_8);
        int nl = text.indexOf('\n');
        String first = nl < 0 ? text : text.substring(0, nl);
        if (!first.startsWith("show:")) {
            return null;
        }
        return first.substring("show:".length()).trim();
    }

    /**
     * The {@code answerShow} method posts {@code /api/show} off the execute path.
     * The worker thread is named {@code aetheris-show} and is a daemon.
     * The reply is channel A type data.
     * The text is {@link #showResult}.
     * A send failure is ignored.
     * The base URL is not written on the reply.
     *
     * @param replyTo The sink is the mux writer.
     * @param cellId The cell id is the cell that asked.
     * @param modelId The model id is the text after {@code show:}.
     */
    private void answerShow(final AmwpReplySink replyTo, final byte[] cellId, final String modelId) {
        /**
         * The runnable sends the show result on channel A.
         */
        Thread worker = new Thread(new Runnable() {
            // @aa-sdd PK-0564 PK-0687 docs/specs/java/org/casserole/llm/llm.spec.md
            @Override
            public void run() {
                String text = showResult(modelId);
                try {
                    byte[] utf = text.getBytes(StandardCharsets.UTF_8);
                    byte[] payload = AmwpCodec.scopeAPayload(0, 1, cellId, utf);
                    replyTo.send(AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_DATA, payload);
                } catch (Exception ignored) {
                }
            }
        }, "aetheris-show");
        worker.setDaemon(true);
        worker.start();
    }

    /**
     * The {@code showResult} method builds a {@code show_result:} body.
     * A null id is empty.
     * An empty id uses the configured Ollama model.
     * The first line is {@code show_result:} plus the id.
     * A blank id returns that line and does not call Ollama.
     * The call is a POST to the base URL plus {@code /api/show}.
     * The timeout is 10 seconds.
     * The JSON body is {@code model} set to the id.
     * A status outside 200 through 299 returns the header only.
     * Value lines are the textual levels from {@code thinking.values}.
     * An empty level is skipped.
     * A level that contains a newline or a carriage return is skipped.
     * {@code true} is skipped.
     * {@code false} is skipped.
     * Each kept level is {@code value:} plus the level.
     * An exception returns the header only.
     * The base URL is not written on the reply.
     *
     * @param modelId The model id is the requested id.
     * @return The return value is the {@code show_result:} body.
     */
    private String showResult(String modelId) {
        String id = modelId == null ? "" : modelId.trim();
        if (id.length() == 0) {
            id = ollamaModel;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("show_result: ").append(id).append('\n');
        if (id.length() == 0) {
            return sb.toString();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectNode root = mapper.createObjectNode();
            root.put("model", id);
            String json = mapper.writeValueAsString(root);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ollamaBaseUrl + "/api/show"))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();
            HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                return sb.toString();
            }
            JsonNode values = mapper.readTree(response.body()).path("thinking").path("values");
            if (!values.isArray()) {
                return sb.toString();
            }
            for (int i = 0; i < values.size(); i++) {
                JsonNode one = values.get(i);
                if (one == null || !one.isTextual()) {
                    continue;
                }
                String level = one.asText().trim();
                if (level.length() == 0 || level.indexOf('\n') >= 0 || level.indexOf('\r') >= 0) {
                    continue;
                }
                if ("true".equals(level) || "false".equals(level)) {
                    continue;
                }
                sb.append("value: ").append(level).append('\n');
            }
        } catch (Exception ignored) {
            return "show_result: " + id + "\n";
        }
        return sb.toString();
    }

    /**
     * The {@code tagsAsk} method reports whether the body asks for Ollama models.
     * A null body returns false.
     * An empty body returns false.
     * The first line must start with {@code tags:}.
     *
     * @param body The body is the stripped payload.
     * @return The return value is true when this frame is a tags ask.
     */
    private static boolean tagsAsk(byte[] body) {
        if (body == null || body.length == 0) {
            return false;
        }
        String text = new String(body, StandardCharsets.UTF_8);
        int nl = text.indexOf('\n');
        String first = nl < 0 ? text : text.substring(0, nl);
        return first.startsWith("tags:");
    }

    /**
     * The {@code answerTags} method gets {@code /api/tags} off the execute path.
     * The worker thread is named {@code aetheris-tags} and is a daemon.
     * The reply is channel A type data.
     * The text is {@link #tagsResult}.
     * A send failure is ignored.
     * A miss carries no model lines.
     * The base URL is not written on the reply.
     *
     * @param replyTo The sink is the mux writer.
     * @param cellId The cell id is the cell that asked.
     */
    private void answerTags(final AmwpReplySink replyTo, final byte[] cellId) {
        /**
         * The runnable sends the tags result on channel A.
         */
        Thread worker = new Thread(new Runnable() {
            // @aa-sdd PK-0606 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-0623 docs/specs/java/org/casserole/llm/llm.spec.md
            @Override
            public void run() {
                String text = tagsResult();
                try {
                    byte[] utf = text.getBytes(StandardCharsets.UTF_8);
                    byte[] payload = AmwpCodec.scopeAPayload(0, 1, cellId, utf);
                    replyTo.send(AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_DATA, payload);
                } catch (Exception ignored) {
                }
            }
        }, "aetheris-tags");
        worker.setDaemon(true);
        worker.start();
    }

    /**
     * The {@code tagsResult} method builds a {@code tags_result:} body.
     * The call is a GET of the base URL plus {@code /api/tags}.
     * The timeout is 10 seconds.
     * A status outside 200 through 299 returns the header only.
     * A body whose {@code models} field is not an array returns the header only.
     * Each textual {@code name} without a newline or a carriage return becomes {@code model:} plus that name.
     * An empty name is skipped.
     * An exception returns the header only.
     *
     * @return The return value is the {@code tags_result:} body.
     */
    private String tagsResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("tags_result:\n");
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ollamaBaseUrl + "/api/tags"))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();
            HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                return sb.toString();
            }
            JsonNode models = mapper.readTree(response.body()).path("models");
            if (!models.isArray()) {
                return sb.toString();
            }
            for (int i = 0; i < models.size(); i++) {
                JsonNode one = models.get(i);
                if (one == null) {
                    continue;
                }
                JsonNode name = one.get("name");
                if (name == null || !name.isTextual()) {
                    continue;
                }
                String tag = name.asText().trim();
                if (tag.length() == 0 || tag.indexOf('\n') >= 0 || tag.indexOf('\r') >= 0) {
                    continue;
                }
                sb.append("model: ").append(tag).append('\n');
            }
        } catch (Exception ignored) {
            return "tags_result:\n";
        }
        return sb.toString();
    }

    /**
     * The {@code grokModelsAsk} method reports whether the body asks for Grok language models.
     * A null body returns false.
     * An empty body returns false.
     * The first line must start with {@code grok_models:}.
     * A first line that starts with {@code grok_models_result:} returns false.
     *
     * @param body The body is the stripped payload.
     * @return The return value is true when this frame is a Grok model ask.
     */
    private static boolean grokModelsAsk(byte[] body) {
        if (body == null || body.length == 0) {
            return false;
        }
        String text = new String(body, StandardCharsets.UTF_8);
        int nl = text.indexOf('\n');
        String first = nl < 0 ? text : text.substring(0, nl);
        if (first.startsWith("grok_models_result:")) {
            return false;
        }
        return first.startsWith("grok_models:");
    }

    /**
     * The {@code answerGrokModels} method gets the language-model catalog off the execute path.
     * The worker thread is named {@code aetheris-grok-models} and is a daemon.
     * The reply is channel A type data.
     * The text is {@link #grokModelsResult}.
     * A send failure is ignored.
     * A miss carries no model lines.
     * The key is not written on the reply.
     *
     * @param replyTo The sink is the mux writer.
     * @param cellId The cell id is the cell that asked.
     */
    private void answerGrokModels(final AmwpReplySink replyTo, final byte[] cellId) {
        /**
         * The runnable sends the Grok model result on channel A.
         */
        Thread worker = new Thread(new Runnable() {
            // @aa-sdd PK-8253 docs/specs/java/org/casserole/llm/llm.spec.md
            @Override
            public void run() {
                String text = grokModelsResult();
                try {
                    byte[] utf = text.getBytes(StandardCharsets.UTF_8);
                    byte[] payload = AmwpCodec.scopeAPayload(0, 1, cellId, utf);
                    replyTo.send(AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_DATA, payload);
                } catch (Exception ignored) {
                }
            }
        }, "aetheris-grok-models");
        worker.setDaemon(true);
        worker.start();
    }

    /**
     * The {@code grokModelsResult} method builds a {@code grok_models_result:} body.
     * {@link GrokLlmService#catalogBody} reads the catalog.
     * A null service returns the header only.
     * An empty catalog returns the header only.
     * The key is not written on the reply.
     *
     * @return The return value is the {@code grok_models_result:} body.
     */
    private String grokModelsResult() {
        if (grokLlm == null) {
            return "grok_models_result:\n";
        }
        return grokLlm.catalogBody();
    }

    /**
     * The {@code trimSlash} method returns a base URL without one trailing slash.
     * A null URL uses {@code http://localhost:11434}.
     * An empty URL uses that same default.
     * A URL that ends with {@code /} loses that one character.
     *
     * @param url The URL is the base.
     * @return The return value is the URL without a trailing slash.
     */
    private static String trimSlash(String url) {
        String base = url == null ? "" : url.trim();
        if (base.length() == 0) {
            return "http://localhost:11434";
        }
        if (base.endsWith("/")) {
            return base.substring(0, base.length() - 1);
        }
        return base;
    }

    /**
     * The {@code execute} method runs one cell turn.
     * A token that is no longer live returns without a reply.
     * {@link #doorBanner} stops the turn when it returns text.
     * That text is sent only when the token is still live.
     * {@link #assembleMessages} builds the prompt.
     * An unknown role replies with the exception message, or {@code unknown role} when the message is null.
     * {@link #holdRoux} false returns without a provider call.
     * {@link #routedComplete} runs the provider.
     * {@link QuietStop} and {@link GrokLlmService.CallStopped} return without another frame.
     * A null reply is sent as empty.
     * A reply that does not fit replies {@code too-large}.
     * A fitting reply is channel stream type data, then type EOF with an empty body.
     * The EOF is skipped when the token is no longer live.
     * Any other exception replies {@code failed} with {@link #garnered} when the token is still live.
     *
     * @param user The user is the SSH user.
     * @param cellKey The cell key is the live-token key.
     * @param token The token is this execute.
     * @param cellId The cell id is the wire id.
     * @param body The body is the stripped payload.
     * @param replyTo The sink is the mux writer.
     */
    private void execute(String user, String cellKey, long token, byte[] cellId, byte[] body,
            final AmwpReplySink replyTo) {
        try {
            if (!stillMine(cellKey, token)) {
                return;
            }
            AmwpToon.ExecuteBody req = AmwpToon.decodeExecute(body);
            String banner = doorBanner(user, req);
            if (banner != null) {
                if (stillMine(cellKey, token)) {
                    replyBanner(replyTo, cellId, banner);
                }
                return;
            }
            List<Message> promptMessages;
            try {
                promptMessages = assembleMessages(req);
            } catch (Exception ex) {
                if (stillMine(cellKey, token)) {
                    replyBanner(replyTo, cellId, ex.getMessage() == null ? "unknown role" : ex.getMessage());
                }
                return;
            }
            if (!holdRoux(user, cellKey, token, promptMessages)) {
                return;
            }
            if (!stillMine(cellKey, token)) {
                return;
            }
            String text;
            try {
                text = routedComplete(user, cellKey, token, cellId, replyTo, req, promptMessages, true);
            } catch (QuietStop ex) {
                return;
            }
            if (text == null) {
                text = "";
            }
            if (!stillMine(cellKey, token)) {
                return;
            }
            byte[] utf = text.getBytes(StandardCharsets.UTF_8);
            if (!AmwpCodec.fits(utf)) {
                replyReason(replyTo, cellId, "too-large", "too-large");
                return;
            }
            byte[] payload = AmwpCodec.scopeAPayload(0, 1, cellId, utf);
            replyTo.send(AmwpConstants.CHANNEL_STREAM, AmwpConstants.TYPE_DATA, payload);
            if (!stillMine(cellKey, token)) {
                return;
            }
            replyTo.send(AmwpConstants.CHANNEL_STREAM, AmwpConstants.TYPE_EOF,
                    AmwpCodec.scopeAPayload(0, 1, cellId, new byte[0]));
        } catch (GrokLlmService.CallStopped ex) {
            return;
        } catch (QuietStop ex) {
            return;
        } catch (Exception ex) {
            if (!stillMine(cellKey, token)) {
                return;
            }
            replyReason(replyTo, cellId, "failed", garnered(ex));
        }
    }

    /**
     * The {@code completeTurn} method runs one console turn.
     * The method does not take an execute permit.
     * The method does not send an AMWP frame.
     * {@link #doorBanner} throws {@link Exception} when it returns text.
     * A null message list is built by {@link #assembleMessages}.
     * Any other list is copied, and {@link #placeHost} inserts the configured system prompt when that text is absent.
     * {@link #holdRoux} stores the prompt with a null cell key.
     * {@link #routedComplete} runs with the cell flag false.
     * An empty reply is returned.
     * The caller decides that the text is empty.
     *
     * @param user The user is the SSH user.
     * @param req The request holds the family, the options, and the shell pool boot.
     * @param messages The messages are the console transcript.
     * @return The return value is the assistant text.
     * @throws Exception The exception is the banner, or the provider failure.
     */
    public String completeTurn(String user, AmwpToon.ExecuteBody req, List<Message> messages) throws Exception {
        String banner = doorBanner(user, req);
        if (banner != null) {
            throw new Exception(banner);
        }
        List<Message> prompt = new ArrayList<Message>();
        if (messages != null) {
            prompt.addAll(messages);
            placeHost(prompt);
        } else {
            prompt = assembleMessages(req);
        }
        holdRoux(user, null, 0L, prompt);
        return routedComplete(user, null, 0L, null, null, req, prompt, false);
    }

    /**
     * The {@code cancelPool} method stops the in-flight pool prompt for this SSH user.
     * A null user returns without a call.
     * An empty user returns without a call.
     * The child stays up.
     *
     * @param user The user is the SSH user.
     */
    public void cancelPool(String user) {
        if (user == null || user.length() == 0) {
            return;
        }
        poolChat.cancel(user);
    }

    /**
     * The {@code doorBanner} method returns the banner that stops a turn.
     * Null means the turn may run.
     * A null request returns {@code failed}.
     * A Grok target with no model returns {@code no model defined}.
     * {@link AetherisYaml#grokDoor} is checked first.
     * A false result tries {@link AetherisYaml#poolDoor}.
     * A cloud target that fails both checks returns {@code air-gap} when {@link AetherisYaml#refusesCloud} is true.
     * An ephemeral Grok turn is not refused as {@code air-gap} here.
     * An ephemeral pool turn returns {@code burner denied}.
     * An ephemeral Grok turn without {@code burnerCloud} returns {@code burner denied}.
     * Another ephemeral cloud turn returns {@code burner denied} when {@link AetherisYaml#burnerCloudAllowed} is false.
     * {@link #openBantha} then opens the RAM region.
     * A refused open returns its reason, or {@code failed} when that reason is empty.
     *
     * @param user The user is the SSH user.
     * @param req The request is the execute body.
     * @return The return value is the banner, or null.
     */
    private String doorBanner(String user, AmwpToon.ExecuteBody req) {
        if (req == null) {
            return "failed";
        }
        String provider = LlmRouter.providerId(req.executionTarget);
        boolean cloud = !"ollama".equals(provider);
        String model = LlmRouter.modelAfterSlash(req.executionTarget);
        boolean modelPresent = model.length() > 0;
        AetherisYaml home = AetherisYaml.readProcessHome();
        if ("grok".equals(provider) && !modelPresent) {
            return "no model defined";
        }
        boolean grokBurner = "grok".equals(provider) && req.ephemeral;
        boolean door = AetherisYaml.grokDoor(provider, modelPresent, req.ephemeral, home.airGapWord());
        if (!door) {
            door = AetherisYaml.poolDoor(provider, req.ephemeral, home.airGapWord());
        }
        if (cloud && !door && !grokBurner && home.refusesCloud()) {
            return "air-gap";
        }
        if (req.ephemeral && cloud) {
            if ("pool".equals(provider)) {
                return "burner denied";
            }
            if ("grok".equals(provider)) {
                if (!req.burnerCloud) {
                    return "burner denied";
                }
            } else if (!home.burnerCloudAllowed()) {
                return "burner denied";
            }
        }
        OpenResult open = openBantha(user);
        if (!open.isOk()) {
            String reason = open.getReason();
            if (reason == null || reason.length() == 0) {
                return "failed";
            }
            return reason;
        }
        return null;
    }

    /**
     * The {@code replyBanner} method maps a banner onto the cell reason codes.
     * {@code no model defined} replies {@code no-model}.
     * {@code air-gap} replies {@code air-gap}.
     * {@code unknown role} replies {@code failed} and {@code unknown role}.
     * Every other banner calls {@link #replyProbeMiss}.
     *
     * @param replyTo The sink is the mux writer.
     * @param cellId The cell id is the cell.
     * @param banner The banner is the text from {@link #doorBanner}.
     */
    private void replyBanner(AmwpReplySink replyTo, byte[] cellId, String banner) {
        if ("no model defined".equals(banner)) {
            replyReason(replyTo, cellId, "no-model", "no model defined");
            return;
        }
        if ("air-gap".equals(banner)) {
            replyReason(replyTo, cellId, "air-gap", "air-gap");
            return;
        }
        if ("unknown role".equals(banner)) {
            replyReason(replyTo, cellId, "failed", "unknown role");
            return;
        }
        replyProbeMiss(replyTo, cellId, banner);
    }

    /**
     * The {@code assembleMessages} method turns a working set into Spring messages.
     * {@link #seatWorkingSet} places the configured system prompt.
     * {@link #trimCanvas} then drops omitted turns when a head is designated.
     *
     * @param req The request is the execute body.
     * @return The return value is the message list.
     * @throws Exception The exception is {@code unknown role}.
     */
    private List<Message> assembleMessages(AmwpToon.ExecuteBody req) throws Exception {
        ArrayList<Boolean> heads = new ArrayList<Boolean>();
        List<Message> messages = seatWorkingSet(hostPrompt(), req.workingSet, heads);
        return trimCanvas(messages, heads);
    }

    /**
     * The {@code seatWorkingSet} method builds Spring messages for a working set.
     * The call uses the three-argument {@link #seatWorkingSet(String, List, List)} with a null head list.
     * An empty first system line drops when the prompt is blank.
     * The vestige sentence {@link AetherisStage2#HOST_SYSTEM} drops when the prompt is blank.
     * A configured prompt fills that first system line.
     * Any other first system line stays when the prompt is blank.
     * That other line takes the prompt when one is configured.
     *
     * @param hostLine The line is the configured prompt, or empty.
     * @param workingSet The working set is the canvas or client turns.
     * @return The return value is the Spring messages.
     * @throws Exception The exception is {@code unknown role}.
     */
    static List<Message> seatWorkingSet(String hostLine, List<AmwpToon.Turn> workingSet) throws Exception {
        return seatWorkingSet(hostLine, workingSet, null);
    }

    /**
     * The {@code seatWorkingSet} method builds Spring messages and optional head flags.
     * A null line is empty.
     * A null turn is skipped.
     * The role is trimmed and lowercased.
     * {@code system}, {@code user}, and {@code assistant} are the accepted roles.
     * Any other role throws {@link Exception} with {@code unknown role}.
     * Only the first system turn is replaced by the configured prompt.
     * An empty first system text drops when the prompt is empty.
     * {@link AetherisStage2#HOST_SYSTEM} drops when the prompt is empty.
     * Either of those two texts becomes the prompt when the prompt is non-empty.
     * Any other first system text becomes the prompt when the prompt is non-empty.
     * That other text stays when the prompt is empty.
     * A missing system turn inserts the prompt at index 0 when the prompt is non-empty.
     * That inserted head flag is false.
     * A non-null head list receives one flag per kept message.
     *
     * @param hostLine The line is the configured prompt, or empty.
     * @param workingSet The working set is the canvas or client turns.
     * @param heads The heads receive the designation of each kept message, or stay unused when null.
     * @return The return value is the Spring messages.
     * @throws Exception The exception is {@code unknown role}.
     */
    static List<Message> seatWorkingSet(String hostLine, List<AmwpToon.Turn> workingSet, List<Boolean> heads)
            throws Exception {
        String host = hostLine == null ? "" : hostLine;
        List<Message> promptMessages = new ArrayList<Message>();
        boolean hostPlaced = false;
        if (workingSet != null) {
            for (int i = 0; i < workingSet.size(); i++) {
                AmwpToon.Turn turn = workingSet.get(i);
                if (turn == null) {
                    continue;
                }
                String role = turn.role == null ? "" : turn.role.trim().toLowerCase();
                String text = turn.text == null ? "" : turn.text;
                if ("system".equals(role) && !hostPlaced) {
                    hostPlaced = true;
                    boolean vestigeSeat = text.length() == 0 || AetherisStage2.HOST_SYSTEM.equals(text);
                    if (vestigeSeat) {
                        if (host.length() == 0) {
                            continue;
                        }
                        text = host;
                    } else if (host.length() > 0) {
                        text = host;
                    }
                }
                Message message;
                if ("system".equals(role)) {
                    message = new SystemMessage(text);
                } else if ("user".equals(role)) {
                    message = new UserMessage(text);
                } else if ("assistant".equals(role)) {
                    message = new AssistantMessage(text);
                } else {
                    throw new Exception("unknown role");
                }
                promptMessages.add(message);
                if (heads != null) {
                    heads.add(Boolean.valueOf(turn.head));
                }
            }
        }
        if (!hostPlaced && host.length() > 0) {
            promptMessages.add(0, new SystemMessage(host));
            if (heads != null) {
                heads.add(0, Boolean.FALSE);
            }
        }
        return promptMessages;
    }

    /**
     * The {@code trimCanvas} method drops omitted turns when the token gate is open.
     * A managed-token count at or below 0 returns the list unchanged.
     * A null or empty message list returns unchanged.
     * A null head list returns unchanged.
     * A head list with no true flag returns unchanged.
     * {@link HeuristicTokenCounter} counts each message.
     * A user line that {@link MoodDesk#lineAtTurnHead} recognizes is marked to stay.
     * The last user message with no later assistant message is marked live.
     * That live sequence number is 0.
     * {@link RouxEngine#selectCut} chooses the kept ordinals.
     * A null cut returns the list unchanged.
     * A null ordinal array returns the list unchanged.
     * The mood splice stays.
     * The live line stays.
     *
     * @param messages The messages are the prompt after {@link #seatWorkingSet}.
     * @param heads The heads are the designation parallel to {@code messages}.
     * @return The return value is the wire list.
     */
    private List<Message> trimCanvas(List<Message> messages, List<Boolean> heads) {
        int managed = properties.getChat().getManagedTokens();
        if (managed <= 0 || messages == null || messages.size() == 0 || heads == null) {
            return messages;
        }
        boolean designated = false;
        for (int i = 0; i < heads.size(); i++) {
            if (Boolean.TRUE.equals(heads.get(i))) {
                designated = true;
                break;
            }
        }
        if (!designated) {
            return messages;
        }
        int count = messages.size();
        long[] seqs = new long[count];
        byte[] roles = new byte[count];
        int[] tokens = new int[count];
        boolean[] headFlags = new boolean[count];
        boolean[] stays = new boolean[count];
        boolean[] lives = new boolean[count];
        HeuristicTokenCounter counter = new HeuristicTokenCounter();
        int liveAt = -1;
        for (int i = 0; i < count; i++) {
            Message message = messages.get(i);
            String text = message == null || message.getText() == null ? "" : message.getText();
            seqs[i] = i + 1L;
            if (message instanceof SystemMessage) {
                roles[i] = Role.SYSTEM;
            } else if (message instanceof AssistantMessage) {
                roles[i] = Role.ASSISTANT;
            } else {
                roles[i] = Role.USER;
                if (MoodDesk.lineAtTurnHead(text) != null) {
                    stays[i] = true;
                }
            }
            tokens[i] = counter.count(text);
            headFlags[i] = i < heads.size() && Boolean.TRUE.equals(heads.get(i));
        }
        for (int i = 0; i < count; i++) {
            if (!(messages.get(i) instanceof UserMessage)) {
                continue;
            }
            boolean assistantAfter = false;
            for (int j = i + 1; j < count; j++) {
                if (messages.get(j) instanceof AssistantMessage) {
                    assistantAfter = true;
                    break;
                }
            }
            if (!assistantAfter) {
                liveAt = i;
            }
        }
        if (liveAt >= 0) {
            lives[liveAt] = true;
            seqs[liveAt] = 0L;
        }
        RouxEngine.SinkCut cut = RouxEngine.selectCut(managed, seqs, roles, tokens, headFlags, stays, lives);
        if (cut == null || cut.ordinals == null) {
            return messages;
        }
        ArrayList<Message> trimmed = new ArrayList<Message>();
        for (int i = 0; i < cut.ordinals.length; i++) {
            int at = cut.ordinals[i];
            if (at >= 0 && at < count) {
                trimmed.add(messages.get(at));
            }
        }
        return trimmed;
    }

    /**
     * The {@code placeHost} method inserts the configured system prompt when this transcript does not already carry that text.
     * An empty prompt returns without a change.
     * A null list returns without a change.
     * A system message whose text equals the prompt returns without a change.
     * Otherwise the prompt is inserted at index 0.
     *
     * @param messages The messages are the caller transcript.
     */
    private void placeHost(List<Message> messages) {
        String hostLine = hostPrompt();
        if (hostLine.length() == 0 || messages == null) {
            return;
        }
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message instanceof SystemMessage && hostLine.equals(message.getText())) {
                return;
            }
        }
        messages.add(0, new SystemMessage(hostLine));
    }

    /**
     * The {@code hostPrompt} method returns the configured system prompt.
     * A null prompt returns empty.
     *
     * @return The return value is that prompt, or empty.
     */
    private String hostPrompt() {
        String hostLine = properties.getChat().getSystemPrompt();
        if (hostLine == null) {
            return "";
        }
        return hostLine;
    }

    /**
     * The {@code holdRoux} method clears the Bantha region and adds the prompt.
     * The region is {@link #banthaRegion} of the sanitized user.
     * The clear and the add run under the region lock.
     * The lock is released before the provider call.
     * A null cell key is the console turn and does not read the live-token map.
     * A cell key that is no longer live returns false inside the lock.
     *
     * @param user The user is the SSH user.
     * @param cellKey The cell key is the live-token key, or null for the console.
     * @param token The token is this execute.
     * @param messages The messages are the prompt.
     * @return The return value is false when this cell execute was cancelled inside the lock.
     */
    private boolean holdRoux(String user, String cellKey, long token, List<Message> messages) {
        String region = banthaRegion(RegionOwner.sanitize(user));
        Object lock = regionLock(region);
        synchronized (lock) {
            rouxMemory.clear(region);
            if (messages != null && messages.size() > 0) {
                rouxMemory.add(region, messages);
            }
            if (cellKey != null && !stillMine(cellKey, token)) {
                return false;
            }
        }
        return true;
    }

    /**
     * The {@code routedComplete} method is the provider call shared by a cell and a console turn.
     * A pool cell calls {@link #runPool} and then throws {@link QuietStop}.
     * A pool console collects text in a {@link TextSink}.
     * Pool think is on only when the think string equals {@code true}.
     * A pool {@link PoolChat.TooLarge} throws {@link Exception} {@code too-large}.
     * A pool interrupt cancels the user, restores the interrupt flag, and throws {@code cancelled}.
     * An opened console thought is closed in {@code finally}.
     * A Grok call asks {@link ProbeGate#admit} before the completion.
     * {@link ProbeOutcome#SKIP} throws {@link QuietStop} for a cell and {@code probe in flight} for a console.
     * A cell whose token is no longer live takes that same skip path.
     * {@link ProbeOutcome#FAIL} replies through {@link #replyProbeMiss} and throws {@link QuietStop} for a cell.
     * A console failure throws the probe message, or {@code grok empty content} when that message is empty.
     * A cell attaches a {@link CellDelta} and stores the HTTP slot.
     * A console attaches a {@link ConsoleThinkSink} only when {@link #consoleCarriesThought} is true.
     * {@link LlmRouter.UnknownProviderException} replies {@code unknown provider} for a cell and throws that text for a console.
     * {@link GrokLlmService.NoModelException} replies {@code no-model} for a cell and throws {@code no model defined} for a console.
     * {@link GrokLlmService.KeyMissingException} replies {@code grok key missing} for a cell and throws that text for a console.
     * A status 400 that rejected the effort retries once with send turned off.
     * A cell retry that succeeds sends {@link #sendEffortOff}.
     * A status 401 clears the probe valid flag.
     * A cell status failure replies {@code failed} and throws {@link QuietStop}.
     * A console status failure throws {@link #garnered}.
     * A cell whose token died during the retry throws {@link QuietStop} without a second reply.
     * An Ollama exception replies {@code engine-down} for a cell and throws {@code engine-down} for a console.
     * {@link GrokLlmService.CallStopped} is thrown again.
     * The cell HTTP slot is removed in {@code finally}.
     * An opened console thought is closed in {@code finally}.
     * A live Ollama cell with a non-empty think string sends kind {@code t} when {@link SpringAiOllamaLlmService#thinkingText} is non-empty.
     * A null response returns empty.
     *
     * @param user The user is the SSH user.
     * @param cellKey The cell key is the live-token key, or null.
     * @param token The token is this execute.
     * @param cellId The cell id is the wire id, or null.
     * @param replyTo The sink is the mux writer, or null.
     * @param req The request is the execute body.
     * @param messages The messages are the prompt.
     * @param cell The flag is true when this call sends cell frames for pool and for a refusal.
     * @return The return value is the assistant text when this call did not already finish the cell.
     * @throws Exception The exception is a refusal, a provider failure, or {@link QuietStop}.
     */
    private String routedComplete(String user, String cellKey, long token, byte[] cellId,
            AmwpReplySink replyTo, AmwpToon.ExecuteBody req, List<Message> messages, boolean cell)
            throws Exception {
        String provider = LlmRouter.providerId(req.executionTarget);
        if ("pool".equals(provider)) {
            if (cell) {
                runPool(user, cellKey, token, cellId, replyTo, req, messages);
                throw new QuietStop();
            }
            TextSink collected = new TextSink(this, user);
            String poolThink = req.think == null ? "" : req.think;
            String boot = req.poolBoot == null ? "" : req.poolBoot;
            try {
                poolChat.prompt(user, poolPromptText(messages), boot, "true".equals(poolThink),
                        ChatSession.porchRules(messages), collected);
            } catch (PoolChat.TooLarge ex) {
                throw new Exception("too-large");
            } catch (InterruptedException ex) {
                poolChat.cancel(user);
                Thread.currentThread().interrupt();
                throw new Exception("cancelled");
            } finally {
                if (collected.opened()) {
                    endConsoleThink(user);
                }
            }
            return collected.text();
        }
        String model = LlmRouter.modelAfterSlash(req.executionTarget);
        if ("grok".equals(provider)) {
            ProbeOutcome outcome = probeGate.admit(model, req.reasoningEffort, req.sendReasoningEffort);
            if (outcome.kind == ProbeOutcome.SKIP || (cell && !stillMine(cellKey, token))) {
                if (cell) {
                    throw new QuietStop();
                }
                throw new Exception("probe in flight");
            }
            if (outcome.kind == ProbeOutcome.FAIL) {
                if (cell) {
                    replyProbeMiss(replyTo, cellId, outcome.message);
                    throw new QuietStop();
                }
                String message = outcome.message;
                if (message == null || message.length() == 0) {
                    message = "grok empty content";
                }
                throw new Exception(message);
            }
        }
        GrokCallOptions options = callOptions(req);
        String think = req.think == null ? "" : req.think;
        GrokLlmService.HttpSlot slot = null;
        GrokLlmService.DeltaSink sink = null;
        ConsoleThinkSink consoleThink = null;
        if (cell) {
            slot = new GrokLlmService.HttpSlot();
            cellHttp.put(cellKey, slot);
            sink = new CellDelta(this, replyTo, cellId, cellKey, token);
        } else if (consoleCarriesThought(provider, think)) {
            consoleThink = new ConsoleThinkSink(this, user);
            sink = consoleThink;
        }
        ChatResponse response = null;
        try {
            response = llmRouter.complete(req.executionTarget, new Prompt(messages), options, think, slot, sink);
        } catch (GrokLlmService.CallStopped ex) {
            throw ex;
        } catch (LlmRouter.UnknownProviderException ex) {
            if (cell) {
                replyReason(replyTo, cellId, "failed", "unknown provider");
                throw new QuietStop();
            }
            throw new Exception("unknown provider");
        } catch (GrokLlmService.NoModelException ex) {
            if (cell) {
                replyReason(replyTo, cellId, "no-model", "no model defined");
                throw new QuietStop();
            }
            throw new Exception("no model defined");
        } catch (GrokLlmService.KeyMissingException ex) {
            if (cell) {
                replyReason(replyTo, cellId, "failed", "grok key missing");
                throw new QuietStop();
            }
            throw new Exception("grok key missing");
        } catch (GrokLlmService.StatusException ex) {
            if (ex.status() == 400 && options.sendReasoningEffort
                    && GrokLlmService.rejectedEffort(ex.getMessage())) {
                options.sendReasoningEffort = false;
                req.sendReasoningEffort = false;
                GrokLlmService.HttpSlot again = null;
                if (cell) {
                    again = new GrokLlmService.HttpSlot();
                    cellHttp.put(cellKey, again);
                }
                try {
                    response = llmRouter.complete(req.executionTarget, new Prompt(messages), options, think,
                            again, sink);
                    if (cell) {
                        sendEffortOff(replyTo, cellId);
                    }
                } catch (GrokLlmService.CallStopped stopped) {
                    throw stopped;
                } catch (GrokLlmService.StatusException second) {
                    if (second.status() == 401) {
                        probeGate.clearValid();
                    }
                    if (cell) {
                        replyReason(replyTo, cellId, "failed", garnered(second));
                        throw new QuietStop();
                    }
                    throw new Exception(garnered(second));
                } catch (Exception second) {
                    if (cell && !stillMine(cellKey, token)) {
                        throw new QuietStop();
                    }
                    if (cell) {
                        replyReason(replyTo, cellId, "failed", garnered(second));
                        throw new QuietStop();
                    }
                    if ("ollama".equals(provider)) {
                        throw new Exception("engine-down");
                    }
                    throw new Exception(garnered(second));
                } finally {
                    if (cell && again != null) {
                        cellHttp.remove(cellKey, again);
                    }
                }
            } else {
                if (ex.status() == 401) {
                    probeGate.clearValid();
                }
                if (cell) {
                    replyReason(replyTo, cellId, "failed", garnered(ex));
                    throw new QuietStop();
                }
                throw new Exception(garnered(ex));
            }
        } catch (Exception ex) {
            if (cell && !stillMine(cellKey, token)) {
                throw new QuietStop();
            }
            if (cell) {
                if ("ollama".equals(provider)) {
                    replyReason(replyTo, cellId, "engine-down", "engine-down");
                } else {
                    replyReason(replyTo, cellId, "failed", garnered(ex));
                }
                throw new QuietStop();
            }
            if ("ollama".equals(provider)) {
                throw new Exception("engine-down");
            }
            throw ex;
        } finally {
            if (cell && slot != null) {
                cellHttp.remove(cellKey, slot);
            }
            if (consoleThink != null && consoleThink.opened()) {
                endConsoleThink(user);
            }
        }
        if (cell && think.length() > 0 && "ollama".equals(provider) && stillMine(cellKey, token)) {
            String thinking = SpringAiOllamaLlmService.thinkingText(response);
            if (thinking.length() > 0) {
                sendDelta(replyTo, cellId, 't', thinking);
            }
        }
        String text = "";
        if (response != null && response.getResult() != null && response.getResult().getOutput() != null) {
            text = response.getResult().getOutput().getText();
            if (text == null) {
                text = "";
            }
        }
        return text;
    }

    // @aa-sdd PK-3525 PK-3555 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code QuietStop} class stops a cell that already answered.
     * The probe skip uses the same exception.
     * A second frame must not leave.
     */
    private static final class QuietStop extends Exception {

        private static final long serialVersionUID = 1L;

        /**
         * The constructor creates the stop with no message.
         */
        QuietStop() {
        }
    }

    /**
     * The id is the nil cell.
     * The console already uses these sixteen zeros.
     */
    private static final byte[] CONSOLE_ID = new byte[16];

    // @aa-sdd PK-3526 PK-3556 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-3580 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code TextSink} class collects a console pool reply.
     * A thought rides the stream channel.
     * The terminal stays the reply.
     * A reason becomes the banner.
     */
    private static final class TextSink implements PoolChat.Sink {

        private final AmwpFacade facade;
        private final String user;
        private final StringBuilder text = new StringBuilder();
        private String failure;
        private volatile boolean opened;

        /**
         * The constructor stores the facade and the user.
         * A null user is stored as empty.
         *
         * @param facade The facade is the console mux.
         * @param user The user is the sanitized SSH user.
         */
        TextSink(AmwpFacade facade, String user) {
            this.facade = facade;
            this.user = user == null ? "" : user;
        }

        /**
         * The {@code thought} method writes one thought chunk on the nil cell.
         * A null or empty chunk returns without writing.
         * A null facade returns without writing.
         * A missing console sink returns without writing.
         * A successful write sets the opened flag.
         * A send failure is ignored.
         * A closed mux leaves the VT100 reply intact.
         *
         * @param chunk The chunk is the thought text.
         */
        @Override
        public void thought(String chunk) {
            if (chunk == null || chunk.length() == 0 || facade == null) {
                return;
            }
            AmwpReplySink sink = facade.consoleSink(user);
            if (sink == null) {
                return;
            }
            try {
                sendDelta(sink, CONSOLE_ID, 't', chunk);
                opened = true;
            } catch (Exception ignored) {
                // a closed mux leaves the VT100 reply intact
            }
        }

        /**
         * The {@code message} method appends one reply chunk.
         * A null chunk is skipped.
         *
         * @param chunk The chunk is the reply text.
         */
        @Override
        public void message(String chunk) {
            if (chunk != null) {
                text.append(chunk);
            }
        }

        /**
         * The {@code done} method finishes the console pool turn.
         * A non-empty reason stores the message, or the reason when the message is empty.
         * That stored text becomes the failure.
         * A non-empty message with an empty reason replaces the collected text.
         *
         * @param reason The reason is the pool reason, or empty on success.
         * @param message The message is the final text, or the failure text.
         */
        @Override
        public void done(String reason, String message) {
            if (reason != null && reason.length() > 0) {
                failure = message == null || message.length() == 0 ? reason : message;
                return;
            }
            if (message != null && message.length() > 0) {
                text.setLength(0);
                text.append(message);
            }
        }

        /**
         * The {@code text} method returns the collected assistant text.
         * A stored failure throws {@link Exception} with that failure.
         *
         * @return The return value is the assistant text.
         * @throws Exception The exception is the pool reason.
         */
        String text() throws Exception {
            if (failure != null) {
                throw new Exception(failure);
            }
            return text.toString();
        }

        /**
         * The {@code opened} method reports whether a thought was written on the stream channel.
         *
         * @return The return value is true when a thought frame was sent.
         */
        boolean opened() {
            return opened;
        }
    }

    /**
     * The {@code consoleCarriesThought} method reports whether a console turn attaches the nil-cell sink.
     * Ollama carries a thought when the think string is set.
     * Grok carries one when that string is {@code true}.
     * Pool uses its own sink.
     * A null string carries none.
     * A blank string carries none.
     * {@code false} carries none.
     * Any other provider returns false.
     *
     * @param provider The provider is the console family.
     * @param think The think string is the think string on this turn.
     * @return The return value is true when the nil-cell sink is attached.
     */
    static boolean consoleCarriesThought(String provider, String think) {
        if (think == null) {
            return false;
        }
        String level = think.trim();
        if (level.length() == 0 || "false".equals(level)) {
            return false;
        }
        if ("ollama".equals(provider)) {
            return true;
        }
        return "grok".equals(provider) && "true".equals(level);
    }

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    /**
     * The {@code ConsoleThinkSink} class sends a console thought.
     * Kind {@code t} rides the nil cell.
     * The terminal stays the reply.
     * A missing mux drops the chunk and leaves the block closed.
     */
    private static final class ConsoleThinkSink implements GrokLlmService.DeltaSink {

        private final AmwpFacade facade;
        private final String user;
        private volatile boolean opened;

        /**
         * The constructor stores the facade and the user.
         * A null user is stored as empty.
         *
         * @param facade The facade is the console mux.
         * @param user The user is the sanitized SSH user.
         */
        ConsoleThinkSink(AmwpFacade facade, String user) {
            this.facade = facade;
            this.user = user == null ? "" : user;
        }

        /**
         * The {@code delta} method writes a thought chunk on the nil cell.
         * A kind other than {@code t} returns without writing.
         * A null or empty text returns without writing.
         * A null facade returns without writing.
         * A missing console sink returns without writing.
         * The opened flag is set only when {@link AmwpFacade#sendDelta} returns true.
         * A send failure is ignored.
         * A closed mux leaves the VT100 reply intact.
         *
         * @param kind The kind is the delta kind.
         * @param text The text is the chunk.
         */
        @Override
        public void delta(char kind, String text) {
            if (kind != 't' || text == null || text.length() == 0 || facade == null) {
                return;
            }
            AmwpReplySink sink = facade.consoleSink(user);
            if (sink == null) {
                return;
            }
            try {
                if (sendDelta(sink, CONSOLE_ID, 't', text)) {
                    opened = true;
                }
            } catch (Exception ignored) {
                // a closed mux leaves the VT100 reply intact
            }
        }

        /**
         * The {@code opened} method reports whether a thought was written on the stream channel.
         *
         * @return The return value is true when a thought frame was sent.
         */
        boolean opened() {
            return opened;
        }
    }

    /**
     * The {@code bindConsole} method stores the mux writer for a console thought.
     * A null sink releases the user.
     * The method is package-private.
     * The method is not a public method.
     * A null user returns without a change.
     * An empty user returns without a change.
     *
     * @param user The user is the sanitized SSH user.
     * @param sink The sink is the mux writer, or null to release.
     */
    void bindConsole(String user, AmwpReplySink sink) {
        if (user == null || user.length() == 0) {
            return;
        }
        if (sink == null) {
            consoleSinks.remove(user);
            return;
        }
        consoleSinks.put(user, sink);
    }

    /**
     * The {@code sendConsoleText} method writes channel A text for a console.
     * The VT100 grid is not written.
     * The caller passes the sanitized user.
     * This method does not sanitize again.
     * A null or empty user returns without writing.
     * A null text returns without writing.
     * A missing sink returns without writing.
     * The payload is {@link AmwpCodec#scopeAPayload} with a null cell id.
     * A send failure is ignored.
     * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     *
     * @param user The user is the sanitized SSH user.
     * @param text The text is the reply body.
     */
    public void sendConsoleText(String user, String text) {
        if (user == null || user.length() == 0 || text == null) {
            return;
        }
        AmwpReplySink sink = consoleSink(user);
        if (sink == null) {
            return;
        }
        byte[] utf = text.getBytes(StandardCharsets.UTF_8);
        try {
            sink.send(AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_DATA,
                    AmwpCodec.scopeAPayload(0, 1, null, utf));
        } catch (Exception ignored) {
            // mux closing
        }
    }

    /**
     * The {@code consoleSink} method returns the bound mux writer.
     * A null user returns null.
     * An empty user returns null.
     *
     * @param user The user is the sanitized SSH user.
     * @return The return value is the bound mux writer, or null.
     */
    private AmwpReplySink consoleSink(String user) {
        if (user == null || user.length() == 0) {
            return null;
        }
        return consoleSinks.get(user);
    }

    /**
     * The {@code endConsoleThink} method closes the console thought block.
     * A missing sink returns without writing.
     * The frame is channel stream type EOF on the nil cell.
     * A closed mux leaves the VT100 reply intact.
     *
     * @param user The user is the sanitized SSH user.
     */
    private void endConsoleThink(String user) {
        AmwpReplySink sink = consoleSink(user);
        if (sink == null) {
            return;
        }
        try {
            sink.send(AmwpConstants.CHANNEL_STREAM, AmwpConstants.TYPE_EOF,
                    AmwpCodec.scopeAPayload(0, 1, CONSOLE_ID, new byte[0]));
        } catch (Exception ignored) {
            // a closed mux leaves the VT100 reply intact
        }
    }

    // @aa-sdd PK-3527 PK-3557 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    /**
     * The {@code CellDelta} class forwards cell Grok deltas.
     * A cancelled execute stops the HTTP call.
     */
    private static final class CellDelta implements GrokLlmService.DeltaSink {

        private final AmwpFacade facade;
        private final AmwpReplySink replyTo;
        private final byte[] cellId;
        private final String cellKey;
        private final long token;

        /**
         * The constructor stores the facade, the sink, and the live token.
         *
         * @param facade The facade checks the live token.
         * @param replyTo The sink is the mux writer.
         * @param cellId The cell id is the wire id.
         * @param cellKey The cell key is the live-token key.
         * @param token The token is this execute.
         */
        CellDelta(AmwpFacade facade, AmwpReplySink replyTo, byte[] cellId, String cellKey, long token) {
            this.facade = facade;
            this.replyTo = replyTo;
            this.cellId = cellId;
            this.cellKey = cellKey;
            this.token = token;
        }

        /**
         * The {@code delta} method forwards one chunk.
         * A token that is no longer live throws {@link GrokLlmService.CallStopped}.
         * A live token calls {@link AmwpFacade#sendDelta}.
         *
         * @param kind The kind is the delta kind.
         * @param text The text is the chunk.
         * @throws Exception The exception is {@link GrokLlmService.CallStopped}, or a write failure.
         */
        @Override
        public void delta(char kind, String text) throws Exception {
            if (!facade.stillMine(cellKey, token)) {
                throw new GrokLlmService.CallStopped();
            }
            sendDelta(replyTo, cellId, kind, text);
        }
    }

    /**
     * The {@code runPool} method runs pool chat for this cell.
     * Frames leave through {@link PoolSink}.
     * This method does not send a second frame.
     * Think is on only when the think string equals {@code true}.
     * {@link PoolChat.TooLarge} returns because the sink already reported the cell.
     * An interrupt cancels the user and restores the interrupt flag.
     * Another exception returns when the sink already sent a frame.
     * Another exception returns when the token is no longer live.
     * An {@link IOException} replies {@code pool down}.
     * Any other exception replies {@code pool payload}.
     * The detail is the exception class name.
     *
     * @param user The user is the SSH user.
     * @param cellKey The cell key is the live-token key.
     * @param token The token is this execute.
     * @param cellId The cell id is the wire id.
     * @param replyTo The sink is the mux writer.
     * @param req The request is the execute body.
     * @param messages The messages are the Roux messages already assembled.
     */
    private void runPool(String user, String cellKey, long token, byte[] cellId, AmwpReplySink replyTo,
            AmwpToon.ExecuteBody req, List<Message> messages) {
        String poolThink = req == null || req.think == null ? "" : req.think;
        boolean thinkOn = "true".equals(poolThink);
        PoolSink sink = new PoolSink(this, replyTo, cellId, cellKey, token);
        String boot = "";
        if (req != null && req.poolBoot != null) {
            boot = req.poolBoot;
        }
        try {
            poolChat.prompt(user, poolPromptText(messages), boot, thinkOn, ChatSession.porchRules(messages), sink);
        } catch (PoolChat.TooLarge ex) {
            return;
        } catch (InterruptedException ex) {
            poolChat.cancel(user);
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            if (sink.sent() || !stillMine(cellKey, token)) {
                return;
            }
            String reason = ex instanceof IOException ? "pool down" : "pool payload";
            String detail = ex.getClass().getSimpleName();
            replyReason(replyTo, cellId, reason, capPool(reason, detail));
        }
    }

    /**
     * The {@code poolPromptText} method writes plain role lines in working-set order.
     * A null list returns empty.
     * A null message is skipped.
     * A system message uses {@code system}.
     * An assistant message uses {@code assistant}.
     * Every other message uses {@code user}.
     * Each kept turn is the role word, a newline, the turn text, and a blank line.
     * A null turn text is empty.
     *
     * @param messages The messages are the Spring messages.
     * @return The return value is the prompt body.
     */
    private static String poolPromptText(List<Message> messages) {
        StringBuilder sb = new StringBuilder();
        if (messages == null) {
            return "";
        }
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message == null) {
                continue;
            }
            String role = "user";
            if (message instanceof SystemMessage) {
                role = "system";
            } else if (message instanceof AssistantMessage) {
                role = "assistant";
            }
            String text = message.getText();
            if (text == null) {
                text = "";
            }
            sb.append(role);
            sb.append('\n');
            sb.append(text);
            sb.append('\n');
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * The {@code capPool} method builds one pool error line.
     * A carriage return becomes a space.
     * A newline becomes a space.
     * An empty detail becomes the reason.
     * A detail that does not start with the reason is prefixed with the reason.
     * The line is capped at 240 characters.
     *
     * @param reason The reason is the pool reason.
     * @param detail The detail is the extra text.
     * @return The return value is that one line.
     */
    private static String capPool(String reason, String detail) {
        String head = reason == null ? "" : reason;
        String line = detail == null ? "" : detail;
        line = line.replace('\r', ' ').replace('\n', ' ').trim();
        if (line.length() == 0) {
            line = head;
        } else if (head.length() > 0 && !line.startsWith(head)) {
            line = head + " " + line;
        }
        if (line.length() > 240) {
            line = line.substring(0, 240);
        }
        return line;
    }

    /**
     * The {@code callOptions} method copies cell fields from the execute frame.
     * A null request returns a fresh {@link GrokCallOptions}.
     * Absent sampling stays at the factory pair.
     * A present temperature is copied.
     * A present completion cap is copied.
     * The prompt itself is not copied here.
     *
     * @param req The request is the execute frame.
     * @return The return value is the call options.
     */
    private static GrokCallOptions callOptions(AmwpToon.ExecuteBody req) {
        GrokCallOptions options = new GrokCallOptions();
        if (req == null) {
            return options;
        }
        options.thinkStream = req.thinkStream;
        if (req.reasoningEffort != null) {
            options.reasoningEffort = req.reasoningEffort;
        }
        options.sendReasoningEffort = req.sendReasoningEffort;
        if (req.temperaturePresent) {
            options.temperature = req.temperature;
        }
        if (req.maxCompletionPresent) {
            options.maxCompletionTokens = req.maxCompletionTokens;
        }
        return options;
    }

    /**
     * The {@code sendEffortOff} method tells the client this cell's model refused {@code reasoning_effort}.
     * The body is {@code effort_off:} plus a newline.
     * The frame is channel A type data.
     * A send failure is ignored.
     *
     * @param replyTo The sink is the mux writer.
     * @param cellId The cell id is the cell that retried.
     */
    private void sendEffortOff(AmwpReplySink replyTo, byte[] cellId) {
        try {
            byte[] utf = "effort_off:\n".getBytes(StandardCharsets.UTF_8);
            byte[] payload = AmwpCodec.scopeAPayload(0, 1, cellId, utf);
            replyTo.send(AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_DATA, payload);
        } catch (Exception ignored) {
        }
    }

    /**
     * The {@code replyProbeMiss} method replies to a probe failure.
     * {@code no model defined} replies {@code no-model}.
     * {@code grok key missing} replies {@code failed} and that same text.
     * A null or empty message replies {@code failed} and {@code grok empty content}.
     * Every other message replies {@code failed} and that message.
     *
     * @param replyTo The sink is the mux writer.
     * @param cellId The cell id is the cell.
     * @param message The message is the probe failure.
     */
    private void replyProbeMiss(AmwpReplySink replyTo, byte[] cellId, String message) {
        if ("no model defined".equals(message)) {
            replyReason(replyTo, cellId, "no-model", "no model defined");
            return;
        }
        if ("grok key missing".equals(message)) {
            replyReason(replyTo, cellId, "failed", "grok key missing");
            return;
        }
        replyReason(replyTo, cellId, "failed", message == null || message.length() == 0
                ? "grok empty content" : message);
    }

    /**
     * The {@code sendDelta} method writes a kind byte and then UTF-8.
     * The method does not store the cell.
     * The method does not end the run.
     * A null chunk returns false and writes nothing.
     * An empty chunk returns false and writes nothing.
     * A chunk that does not fit returns false and writes nothing.
     * A fitting chunk is channel stream type delta.
     *
     * @param replyTo The sink is the mux writer.
     * @param cellId The cell id is the cell.
     * @param kind The kind is {@code t} or {@code c}.
     * @param text The text is the chunk.
     * @return The return value is true when the frame was written.
     * @throws Exception The exception is a write failure.
     */
    private static boolean sendDelta(AmwpReplySink replyTo, byte[] cellId, char kind, String text) throws Exception {
        if (text == null || text.length() == 0) {
            return false;
        }
        byte[] utf = text.getBytes(StandardCharsets.UTF_8);
        byte[] raw = new byte[utf.length + 1];
        raw[0] = (byte) kind;
        System.arraycopy(utf, 0, raw, 1, utf.length);
        if (!AmwpCodec.fits(raw)) {
            return false;
        }
        replyTo.send(AmwpConstants.CHANNEL_STREAM, AmwpConstants.TYPE_DELTA,
                AmwpCodec.scopeAPayload(0, 1, cellId, raw));
        return true;
    }

    /**
     * The {@code garnered} method builds one error line.
     * A null exception becomes {@code execute failed}.
     * An empty message becomes {@code execute failed}.
     * A carriage return becomes a space.
     * A newline becomes a space.
     * The line is capped at 240 characters.
     * The key is already redacted by the Grok service when the message is a {@code Grok HTTP} line.
     *
     * @param ex The exception is the failure.
     * @return The return value is one line, at most 240 characters.
     */
    private static String garnered(Exception ex) {
        String message = "";
        if (ex != null && ex.getMessage() != null) {
            message = ex.getMessage();
        }
        message = message.replace('\r', ' ').replace('\n', ' ').trim();
        if (message.length() == 0) {
            return "execute failed";
        }
        if (message.length() > 240) {
            message = message.substring(0, 240);
        }
        return message;
    }

    /**
     * The {@code regionLock} method returns the lock for one region.
     * The first caller stores a new lock.
     * A later caller receives that same lock.
     *
     * @param region The region is the Bantha region id.
     * @return The return value is that lock.
     */
    private Object regionLock(String region) {
        Object created = new Object();
        Object prior = regionLocks.putIfAbsent(region, created);
        return prior == null ? created : prior;
    }

    /**
     * The {@code stillMine} method reports whether this execute is still the live one.
     * A missing token returns false.
     * A different token returns false.
     *
     * @param key The key is the cell id.
     * @param token The token is the execute generation.
     * @return The return value is true when this execute is still the live one.
     */
    private boolean stillMine(String key, long token) {
        Long current = liveToken.get(key);
        return current != null && current.longValue() == token;
    }

    /**
     * The {@code releaseMine} method drops this execute from the maps when it is still the live one.
     * A different live token is left in place.
     *
     * @param key The key is the cell id.
     * @param token The token is the execute generation.
     */
    private void releaseMine(String key, long token) {
        Long current = liveToken.get(key);
        if (current != null && current.longValue() == token) {
            liveToken.remove(key, current);
            inFlight.remove(key);
        }
    }

    /**
     * The {@code replyReason} method sends one channel A error.
     * The body is {@link AmwpToon#encodeError}.
     * A body that does not fit is replaced by the bytes of {@code too-large}.
     * A send failure is ignored.
     *
     * @param replyTo The sink is the mux writer.
     * @param cellId The cell id is the cell.
     * @param reason The reason is the error code.
     * @param message The message is the error text.
     */
    private void replyReason(AmwpReplySink replyTo, byte[] cellId, String reason, String message) {
        byte[] body = AmwpToon.encodeError(reason, message);
        if (!AmwpCodec.fits(body)) {
            body = "too-large".getBytes(StandardCharsets.UTF_8);
        }
        byte[] payload = AmwpCodec.scopeAPayload(0, 1, cellId, body);
        try {
            replyTo.send(AmwpConstants.CHANNEL_A, AmwpConstants.TYPE_ERROR, payload);
        } catch (Exception ignored) {
            // mux closing
        }
    }

    /**
     * The {@code uuidString} method turns 16 bytes into a UUID string.
     * A null id returns a new random id.
     * An id whose length is not 16 returns a new random id.
     * The first 8 bytes are the most significant bits.
     * The last 8 bytes are the least significant bits.
     *
     * @param id The id is the wire cell id.
     * @return The return value is the UUID text.
     */
    private static String uuidString(byte[] id) {
        if (id == null || id.length != 16) {
            return UUID.randomUUID().toString();
        }
        long msb = 0L;
        long lsb = 0L;
        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (id[i] & 0xFF);
        }
        for (int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (id[i] & 0xFF);
        }
        return new UUID(msb, lsb).toString();
    }

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    /**
     * The {@code OpenFuture} class is the in-flight placeholder before submit.
     * The future is not done until it is cancelled.
     */
    private static final class OpenFuture implements Future<Object> {

        private volatile boolean cancelled;

        /**
         * The {@code cancel} method marks the placeholder cancelled.
         * The interrupt flag is not used.
         *
         * @param mayInterruptIfRunning The flag is ignored.
         * @return The return value is true.
         */
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            cancelled = true;
            return true;
        }

        /**
         * The {@code isCancelled} method reports the cancelled flag.
         *
         * @return The return value is true after {@link #cancel}.
         */
        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        /**
         * The {@code isDone} method reports the cancelled flag.
         * An uncancelled placeholder is not done.
         *
         * @return The return value is true after {@link #cancel}.
         */
        @Override
        public boolean isDone() {
            return cancelled;
        }

        /**
         * The {@code get} method refuses to return a value.
         * The thrown cause is {@link IllegalStateException} with {@code placeholder}.
         *
         * @return The method does not return.
         * @throws InterruptedException The method declares {@link InterruptedException}.
         * @throws ExecutionException The exception wraps {@code placeholder}.
         */
        @Override
        public Object get() throws InterruptedException, ExecutionException {
            throw new ExecutionException(new IllegalStateException("placeholder"));
        }

        /**
         * The {@code get} method refuses to return a value.
         * The timeout is not waited.
         * The thrown cause is {@link IllegalStateException} with {@code placeholder}.
         *
         * @param timeout The timeout is ignored.
         * @param unit The unit is ignored.
         * @return The method does not return.
         * @throws InterruptedException The method declares {@link InterruptedException}.
         * @throws ExecutionException The exception wraps {@code placeholder}.
         * @throws TimeoutException The method declares {@link TimeoutException}.
         */
        @Override
        public Object get(long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException, TimeoutException {
            throw new ExecutionException(new IllegalStateException("placeholder"));
        }
    }

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    /**
     * The {@code ProbeOutcome} class is the probe result for one run.
     * {@link #SEND} means the completion may run.
     * {@link #FAIL} carries the failure text.
     * {@link #SKIP} does not send the cell prompt and does not store a failure.
     */
    private static final class ProbeOutcome {
        static final int SEND = 0;
        static final int FAIL = 1;
        static final int SKIP = 2;

        final int kind;
        final String message;

        /**
         * The constructor stores the kind and the message.
         * A null message is stored as empty.
         *
         * @param kind The kind is {@link #SEND}, {@link #FAIL}, or {@link #SKIP}.
         * @param message The message is the failure text, or empty.
         */
        ProbeOutcome(int kind, String message) {
            this.kind = kind;
            this.message = message == null ? "" : message;
        }
    }

    // @aa-sdd PK-0565 PK-0688 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0634 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0491 docs/specs/java/org/casserole/casserole.spec.md
    /**
     * The {@code ProbeGate} class runs one Grok probe for this server process.
     * The valid flag matches the key that passed.
     * The flag is not written to a file.
     * The shell does not keep a copy.
     * A restart clears it.
     * The probe future stays here.
     * Cancelling the leader does not cancel it.
     */
    private final class ProbeGate {
        private static final int NONE = 0;
        private static final int FLIGHT = 1;
        private static final int VALID = 2;

        private final Object lock = new Object();
        private int state = NONE;
        /**
         * The key passed the probe.
         * It is not logged.
         * It is not written out.
         */
        private String validKey = "";
        private String lastMiss = "";
        /**
         * The future is the shared probe.
         * It is not cancelled with the leader.
         */
        private CompletableFuture<Void> probeFuture;

        /**
         * The {@code admit} method checks the probe and claims the leader under the lock.
         * The lock is released before {@link LlmRouter#startGrokProbe}.
         * The region lock is not held.
         * The probe does not pin that user's region.
         * An empty model returns {@link ProbeOutcome#FAIL} and {@code no model defined}.
         * An empty key returns {@link ProbeOutcome#FAIL} and {@code grok key missing}.
         * A valid flag for the same key returns {@link ProbeOutcome#SEND}.
         * A valid flag for a different key is cleared.
         * A later caller waits while a probe is in flight.
         * An interrupt during that wait returns {@link ProbeOutcome#SKIP}.
         * A later cell does not probe again when the waited probe is valid for the same key.
         * A waited failure returns the last failure text, or {@code grok empty content}.
         * The leader effort is the level sent on this probe.
         * {@code sendEffort} false leaves {@code reasoning_effort} off the probe body.
         *
         * @param model The model is the target id.
         * @param effort The effort is the leader probe level.
         * @param sendEffort The flag is false when {@code reasoning_effort} stays off the probe body.
         * @return The return value is send, fail, or skip.
         */
        ProbeOutcome admit(String model, String effort, boolean sendEffort) {
            String modelId = model == null ? "" : model.trim();
            if (modelId.length() == 0) {
                return new ProbeOutcome(ProbeOutcome.FAIL, "no model defined");
            }
            final String key = llmRouter.currentGrokKey();
            if (key.length() == 0) {
                return new ProbeOutcome(ProbeOutcome.FAIL, "grok key missing");
            }
            boolean leader = false;
            synchronized (lock) {
                if (state == VALID && key.equals(validKey)) {
                    return new ProbeOutcome(ProbeOutcome.SEND, "");
                }
                if (state == VALID) {
                    state = NONE;
                    validKey = "";
                }
                if (state == NONE) {
                    state = FLIGHT;
                    leader = true;
                } else {
                    try {
                        while (state == FLIGHT) {
                            lock.wait();
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        return new ProbeOutcome(ProbeOutcome.SKIP, "");
                    }
                    if (state == VALID && key.equals(validKey)) {
                        return new ProbeOutcome(ProbeOutcome.SEND, "");
                    }
                    String miss = lastMiss.length() == 0 ? "grok empty content" : lastMiss;
                    return new ProbeOutcome(ProbeOutcome.FAIL, miss);
                }
            }
            if (!leader) {
                return new ProbeOutcome(ProbeOutcome.FAIL, "grok empty content");
            }
            final CompletableFuture<Void> future;
            try {
                future = llmRouter.startGrokProbe(modelId, effort, sendEffort);
            } catch (GrokLlmService.NoModelException ex) {
                publish(false, key, "no model defined");
                return new ProbeOutcome(ProbeOutcome.FAIL, "no model defined");
            } catch (GrokLlmService.KeyMissingException ex) {
                publish(false, key, "grok key missing");
                return new ProbeOutcome(ProbeOutcome.FAIL, "grok key missing");
            } catch (Exception ex) {
                String miss = garnered(ex);
                publish(false, key, miss);
                return new ProbeOutcome(ProbeOutcome.FAIL, miss);
            }
            synchronized (lock) {
                probeFuture = future;
            }
            /**
             * The consumer publishes the probe result.
             * A null error publishes success for this key.
             * Any other error publishes {@link ProbeGate#missOf}.
             *
             * @param ok The value is unused.
             * @param err The error is the probe failure, or null.
             */
            future.whenComplete(new BiConsumer<Void, Throwable>() {
                // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
                // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
                // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
                // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
                // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
                // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
                // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
                // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
                // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
                // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
                @Override
                public void accept(Void ok, Throwable err) {
                    if (err == null) {
                        publish(true, key, "");
                    } else {
                        publish(false, key, missOf(err));
                    }
                }
            });
            try {
                future.get();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return new ProbeOutcome(ProbeOutcome.SKIP, "");
            } catch (ExecutionException ex) {
                String miss;
                synchronized (lock) {
                    miss = lastMiss.length() == 0 ? missOf(ex) : lastMiss;
                }
                return new ProbeOutcome(ProbeOutcome.FAIL, miss);
            }
            return new ProbeOutcome(ProbeOutcome.SEND, "");
        }

        /**
         * The {@code clearValid} method clears a valid probe after a 401 on a later call.
         * An in-flight probe is left alone.
         */
        void clearValid() {
            synchronized (lock) {
                if (state == VALID) {
                    state = NONE;
                    validKey = "";
                }
            }
        }

        /**
         * The {@code publish} method stores the probe result and wakes waiters.
         * Success sets the valid flag and the key.
         * Success clears the last failure.
         * A failure clears the valid flag and stores the failure text.
         * A null failure text is stored as empty.
         * The probe future is cleared.
         *
         * @param ok The flag is true when the probe passed.
         * @param key The key is the key that was probed.
         * @param miss The miss is the failure text.
         */
        private void publish(boolean ok, String key, String miss) {
            synchronized (lock) {
                if (ok) {
                    state = VALID;
                    validKey = key;
                    lastMiss = "";
                } else {
                    state = NONE;
                    validKey = "";
                    lastMiss = miss == null ? "" : miss;
                }
                probeFuture = null;
                lock.notifyAll();
            }
        }

        /**
         * The {@code missOf} method turns a probe error into one line.
         * An {@link ExecutionException} uses its cause when that cause is present.
         * An {@link Exception} cause uses {@link AmwpFacade#garnered}.
         * Any other throwable returns {@code grok empty content}.
         *
         * @param err The error is the probe failure.
         * @return The return value is that line.
         */
        private String missOf(Throwable err) {
            Throwable cause = err;
            if (cause instanceof ExecutionException && cause.getCause() != null) {
                cause = cause.getCause();
            }
            if (cause instanceof Exception) {
                return garnered((Exception) cause);
            }
            return "grok empty content";
        }
    }

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    /**
     * The {@code AmwpReplySink} interface is the mux writer for replies.
     */
    public interface AmwpReplySink {
        /**
         * The {@code send} method writes one reply frame.
         *
         * @param channel The channel is the AMWP channel.
         * @param type The type is the AMWP frame type.
         * @param payload The payload is the frame body.
         * @throws Exception The exception is a write failure.
         */
        void send(int channel, int type, byte[] payload) throws Exception;
    }

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    /**
     * The {@code PoolSink} class sends frames for one pool prompt.
     * A chunk that does not fit sends {@code too-large} and stops the prompt.
     * Success sends the concatenated text, then EOF.
     * A failure sends one error.
     * A second frame does not leave.
     */
    private static final class PoolSink implements PoolChat.Sink {

        private final AmwpFacade facade;
        private final AmwpReplySink replyTo;
        private final byte[] cellId;
        private final String cellKey;
        private final long token;
        private boolean framed;

        /**
         * The constructor stores the facade, the sink, and the live token.
         *
         * @param facade The facade holds the reply helpers.
         * @param replyTo The sink is the mux writer.
         * @param cellId The cell id is the wire id.
         * @param cellKey The cell key is the live-token key.
         * @param token The token is this execute.
         */
        PoolSink(AmwpFacade facade, AmwpReplySink replyTo, byte[] cellId, String cellKey, long token) {
            this.facade = facade;
            this.replyTo = replyTo;
            this.cellId = cellId;
            this.cellKey = cellKey;
            this.token = token;
        }

        /**
         * The {@code sent} method reports whether a finishing frame already left.
         * The fit check that reported the cell also counts as sent.
         *
         * @return The return value is true when that frame already left.
         */
        boolean sent() {
            return framed;
        }

        /**
         * The {@code thought} method forwards one thought chunk as kind {@code t}.
         *
         * @param text The text is the chunk.
         * @throws Exception The exception is {@link PoolChat.TooLarge}, or a write failure.
         */
        @Override
        public void thought(String text) throws Exception {
            forward('t', text);
        }

        /**
         * The {@code message} method forwards one reply chunk as kind {@code c}.
         *
         * @param text The text is the chunk.
         * @throws Exception The exception is {@link PoolChat.TooLarge}, or a write failure.
         */
        @Override
        public void message(String text) throws Exception {
            forward('c', text);
        }

        /**
         * The {@code done} method finishes the pool prompt.
         * A frame that already left returns without another frame.
         * A token that is no longer live returns without another frame.
         * An empty reason sends the message as channel stream type data, then type EOF.
         * A message that does not fit replies {@code too-large} and does not send EOF.
         * A non-empty reason sends one error through {@link AmwpFacade#capPool}.
         *
         * @param reason The reason is the pool reason, or empty on success.
         * @param message The message is the final text, or the failure text.
         * @throws Exception The exception is a write failure.
         */
        @Override
        public void done(String reason, String message) throws Exception {
            if (framed || !facade.stillMine(cellKey, token)) {
                return;
            }
            framed = true;
            if (reason == null || reason.length() == 0) {
                String text = message == null ? "" : message;
                byte[] utf = text.getBytes(StandardCharsets.UTF_8);
                if (!AmwpCodec.fits(utf)) {
                    facade.replyReason(replyTo, cellId, "too-large", "too-large");
                    return;
                }
                byte[] payload = AmwpCodec.scopeAPayload(0, 1, cellId, utf);
                replyTo.send(AmwpConstants.CHANNEL_STREAM, AmwpConstants.TYPE_DATA, payload);
                if (!facade.stillMine(cellKey, token)) {
                    return;
                }
                replyTo.send(AmwpConstants.CHANNEL_STREAM, AmwpConstants.TYPE_EOF,
                        AmwpCodec.scopeAPayload(0, 1, cellId, new byte[0]));
                return;
            }
            facade.replyReason(replyTo, cellId, reason, capPool(reason, message));
        }

        /**
         * The {@code forward} method sends one pool chunk.
         * A null or empty text returns without writing.
         * A token that is no longer live returns without writing.
         * A chunk that does not fit replies {@code too-large} once and throws {@link PoolChat.TooLarge}.
         * A fitting chunk calls {@link AmwpFacade#sendDelta}.
         *
         * @param kind The kind is {@code t} or {@code c}.
         * @param text The text is the chunk.
         * @throws Exception The exception is {@link PoolChat.TooLarge}, or a write failure.
         */
        private void forward(char kind, String text) throws Exception {
            if (text == null || text.length() == 0) {
                return;
            }
            if (!facade.stillMine(cellKey, token)) {
                return;
            }
            byte[] utf = text.getBytes(StandardCharsets.UTF_8);
            byte[] raw = new byte[utf.length + 1];
            raw[0] = (byte) kind;
            System.arraycopy(utf, 0, raw, 1, utf.length);
            if (!AmwpCodec.fits(raw)) {
                if (!framed) {
                    framed = true;
                    facade.replyReason(replyTo, cellId, "too-large", "too-large");
                }
                throw new PoolChat.TooLarge();
            }
            sendDelta(replyTo, cellId, kind, text);
        }
    }
}
/*****************************************************************************************
 * End of Class: AmwpFacade.java 090226
 ****************************************************************************************/
