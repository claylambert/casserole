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

package org.casserole.pool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.casserole.config.CasseroleProperties;
import org.casserole.lamina.Lamina;
import org.casserole.lamina.LaminaTask;
import org.casserole.lamina.Lane;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

// @aa-sdd PK-0488 docs/specs/java/org/casserole/pool/pool.spec.md
// @aa-sdd PK-0489 docs/specs/java/org/casserole/pool/pool.spec.md
// @aa-sdd PK-0504 docs/specs/java/org/casserole/pool/pool.spec.md
// @aa-sdd PK-8252 docs/specs/java/org/casserole/pool/pool.spec.md
/**
 * The {@code PoolChatServer} class is the pool of ACP seats.
 * The class implements {@link PoolChat}. It keeps one child for each user. It keeps at most three children.
 * Stdout and stderr run on {@link Lane#POOL_IO}. {@link #prompt} blocks the caller until the turn finishes.
 * A blank {@code casserole.pool.host} keeps the local child. The working directory is {@code ~/.casserole/pool-cwd}. Windows starts {@code cmd.exe /c grok agent stdio}. Any other operating system starts {@code grok agent stdio}. The child environment does not receive {@code XAI_API_KEY}.
 * A set host dials {@code ws://host:port/ws} on {@code grok agent serve}. The JSON-RPC lines stay the same. The serve token is not logged.
 * An idle child is destroyed after 15 minutes. One stdout line is capped at 1 MiB.
 * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
 * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
 * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
 * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
 * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
 * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
 * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
 * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
 * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
 * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
 * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
 * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
 * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
 * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
 * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
 * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
 * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
 * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
 *
 * @author clayl (2026-10-22)
 */
public final class PoolChatServer implements PoolChat {

    private static final int CAP = 3;
    private static final long IDLE_MS = 15L * 60L * 1000L;
    private static final int LINE_CAP = 1024 * 1024;
    private static final int QUOTE_CAP = 80;
    private static final int MESSAGE_CAP = 240;

    private final Lamina lamina;
    private final ObjectMapper mapper;
    private final File cwd;
    private final PoolAddress address;
    private final ConcurrentHashMap<String, Child> children;
    private final Object capLock;
    private final java.util.Timer idleTimer;
    private final Thread hook;
    private volatile boolean shut;

    /**
     * The constructor stores the lane submitter and prepares an empty child map.
     * The working directory is {@code ~/.casserole/pool-cwd}. The idle timer is a daemon named {@code pool-idle}. A shutdown hook named {@code pool-chat-shutdown} calls {@link #shutdown}.
     * A null properties object, or a blank host, keeps the local {@code grok agent stdio} child.
     *
     * @param lamina The lamina submits stdout and stderr work. {@code POOL_IO} must already exist.
     * @param properties The properties carry {@code casserole.pool.host}, {@code casserole.pool.port}, and {@code casserole.pool.secret}.
     */
    public PoolChatServer(Lamina lamina, CasseroleProperties properties) {
        this.lamina = lamina;
        this.mapper = new ObjectMapper();
        CasseroleProperties.Pool pool = properties == null ? null : properties.getPool();
        this.address = PoolAddress.from(pool);
        String home = System.getProperty("user.home", "");
        this.cwd = new File(new File(home, ".casserole"), "pool-cwd");
        this.children = new ConcurrentHashMap<String, Child>();
        this.capLock = new Object();
        this.idleTimer = new java.util.Timer("pool-idle", true);
        this.hook = new Thread(new ShutdownWork(this), "pool-chat-shutdown");
        Runtime.getRuntime().addShutdownHook(hook);
    }

    /**
     * The {@code start} method makes sure a child exists for the user.
     *
     * @param user The user is the user name.
     * @throws Exception The method throws this exception when the child cannot be started.
     */
    @Override
    public void start(String user) throws Exception {
        ensure(user);
    }

    /**
     * The {@code prompt} method runs one turn on the user's child and blocks until that turn finishes.
     * A null sink returns at once. A null user is stored as an empty name. A null text and a null boot are stored as empty strings. A null rules string is stored as an empty string.
     * If the server is shut down, the sink receives {@code pool down}. If the child cannot be created, the sink receives {@code pool down}. An interrupt restores the thread interrupt flag and is thrown again.
     * A dead child is started again before the turn. The method then calls {@link #runPrompt}. An interrupt during that call cancels the session, drops the hold, restores the interrupt flag, and is thrown again.
     * The prompt flags are cleared when the call leaves the gate. The idle timer is armed after the turn.
     *
     * @param user The user is the user name.
     * @param text The text is the working-set string for this turn.
     * @param boot The boot is the boot id. An empty boot does not keep a hold.
     * @param think The flag is true when thought chunks are forwarded.
     * @param rules The rules are sent on the next {@code session/new}. An empty string leaves {@code _meta} off.
     * @param sink The sink receives thought text, reply text, and the done call.
     * @throws Exception The method throws this exception when the wait is interrupted.
     */
    @Override
    public void prompt(String user, String text, String boot, boolean think, String rules, Sink sink)
            throws Exception {
        if (sink == null) {
            return;
        }
        String key = user == null ? "" : user;
        if (shut) {
            sink.done("pool down", clip("pool down", "spawn", "", null));
            return;
        }
        Child child;
        try {
            child = ensure(key);
        } catch (IOException ex) {
            sink.done("pool down", clip("pool down", "spawn", ex.getClass().getSimpleName(), null));
            return;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw ex;
        }
        synchronized (child.promptGate) {
            if (shut) {
                sink.done("pool down", clip("pool down", "spawn", "", child));
                return;
            }
            if (!child.alive) {
                try {
                    respawn(child);
                } catch (IOException ex) {
                    sink.done("pool down", clip("pool down", "spawn", ex.getClass().getSimpleName(), child));
                    return;
                }
            }
            child.inPrompt = true;
            child.promptOpen = true;
            child.forwarded = false;
            child.retried = false;
            child.cancelAsked = false;
            child.cancelSent = false;
            child.skipDone = false;
            child.notes = 0;
            child.think = think;
            child.porchRules = rules == null ? "" : rules;
            child.sink = sink;
            child.assembled = new StringBuilder();
            child.failReason = null;
            child.failMessage = null;
            child.step = "session/prompt";
            bumpIdle(child);
            try {
                runPrompt(child, text == null ? "" : text, boot == null ? "" : boot);
            } catch (InterruptedException ex) {
                sendCancel(child);
                dropHold(child);
                Thread.currentThread().interrupt();
                throw ex;
            } finally {
                child.promptOpen = false;
                child.inPrompt = false;
                child.sink = null;
                armIdle(child);
            }
        }
    }

    /**
     * The {@code cancel} method asks the user's child to stop the current turn.
     * A null user is treated as an empty name. A missing child returns. The cancel flag is set and waiting readers are woken. {@link #sendCancel} writes {@code session/cancel} when a session is open.
     *
     * @param user The user is the user name.
     */
    @Override
    public void cancel(String user) {
        String key = user == null ? "" : user;
        Child child = children.get(key);
        if (child == null) {
            return;
        }
        synchronized (child.io) {
            child.cancelAsked = true;
            child.io.notifyAll();
        }
        sendCancel(child);
    }

    /**
     * The {@code health} method reports the user's child.
     * A null user is treated as an empty name. A missing child or a child that is not alive returns {@link Health#DOWN}. A child that failed authentication returns {@link Health#AUTH}. Any other live child returns {@link Health#READY}.
     *
     * @param user The user is the user name.
     * @return The return value is the health value.
     */
    @Override
    public Health health(String user) {
        String key = user == null ? "" : user;
        Child child = children.get(key);
        if (child == null || !child.alive) {
            return Health.DOWN;
        }
        if (child.authFailed) {
            return Health.AUTH;
        }
        return Health.READY;
    }

    /**
     * The {@code shutdown} method stops the pool.
     * A second call returns at once. The idle timer is cancelled. Every child is destroyed and the map is cleared. Waiting cap checks are woken.
     * When the caller is not the shutdown hook, the hook is removed. An illegal-state exception from that removal is ignored because the runtime may already be exiting.
     */
    @Override
    public void shutdown() {
        if (shut) {
            return;
        }
        shut = true;
        idleTimer.cancel();
        synchronized (capLock) {
            Iterator<Child> it = children.values().iterator();
            while (it.hasNext()) {
                destroyProcess(it.next());
            }
            children.clear();
            capLock.notifyAll();
        }
        if (Thread.currentThread() != hook) {
            try {
                Runtime.getRuntime().removeShutdownHook(hook);
            } catch (IllegalStateException ex) {
                // JVM is already exiting.
            }
        }
    }

    /**
     * The {@code runPrompt} method sends one working set to the child.
     * A held session that {@link #canExtend} accepts sends only the tail after the held text. Every other prompt drops the hold, opens a session, and sends the whole working set.
     * {@link PoolChat.TooLarge} cancels the session and drops the hold. The sink done call is left to the forward path, which sets the skip flag. An interrupt cancels, drops the hold, and is thrown again.
     * An I/O failure drops the hold and, unless done is skipped or cancel was asked, calls the sink with {@code pool down}. Any other exception uses {@code pool payload}.
     *
     * @param child The child is the live process for this user.
     * @param full The full text is the whole working-set string.
     * @param boot The boot is the boot id. An empty boot does not keep a hold.
     * @throws Exception The method throws this exception when the wait is interrupted.
     */
    private void runPrompt(Child child, String full, String boot) throws Exception {
        String safeBoot = boot == null ? "" : boot;
        boolean extend = canExtend(child, full, safeBoot);
        String body = full;
        if (extend) {
            body = full.substring(child.flatHeld.length());
        }
        try {
            if (!prepareProcess(child, full, safeBoot)) {
                return;
            }
            if (!extend) {
                dropHold(child);
                if (!openSession(child, full, safeBoot)) {
                    return;
                }
            }
            sendTurn(child, body, full, safeBoot);
        } catch (PoolChat.TooLarge ex) {
            sendCancel(child);
            dropHold(child);
        } catch (InterruptedException ex) {
            sendCancel(child);
            dropHold(child);
            throw ex;
        } catch (IOException ex) {
            dropHold(child);
            if (child.sink != null && !child.skipDone && !child.cancelAsked) {
                child.sink.done("pool down", clip("pool down", child.step, ex.getClass().getSimpleName(), child));
            }
        } catch (Exception ex) {
            dropHold(child);
            if (child.sink != null && !child.skipDone && !child.cancelAsked) {
                child.sink.done("pool payload", exceptionLine(child, ex));
            }
        }
    }

    /**
     * The {@code prepareProcess} method sends {@code initialize} once for this process.
     * A child that is already initialized returns true. {@link #stopHere} cancels, drops the hold, and returns false. {@link Outcome#DEAD} calls {@link #retry} and returns false. {@link Outcome#FAIL} drops the hold, finishes the failure, and returns false. Any other result marks the child initialized and returns true.
     *
     * @param child The child is the live process.
     * @param full The full text is the working set passed to a retry.
     * @param boot The boot is the boot id passed to a retry.
     * @return The return value is false when this prompt is already finished.
     * @throws Exception The method throws this exception when the child call fails.
     */
    private boolean prepareProcess(Child child, String full, String boot) throws Exception {
        if (child.initialized) {
            return true;
        }
        child.step = "initialize";
        Outcome opened = call(child, "initialize", initializeParams(), false);
        if (stopHere(child, opened)) {
            sendCancel(child);
            dropHold(child);
            return false;
        }
        if (opened == Outcome.DEAD) {
            retry(child, full, boot);
            return false;
        }
        if (opened == Outcome.FAIL) {
            dropHold(child);
            finishFail(child);
            return false;
        }
        child.initialized = true;
        return true;
    }

    /**
     * The {@code openSession} method sends {@code session/new} with the child's rules.
     * {@link #stopHere} cancels, drops the hold, and returns false. {@link Outcome#DEAD} calls {@link #retry} and returns false. {@link Outcome#FAIL} drops the hold and finishes the failure. Any other result that is not {@link Outcome#OK} drops the hold and, when a sink is present, calls it with {@code pool down}. {@link Outcome#OK} returns true.
     *
     * @param child The child is the live process.
     * @param full The full text is the working set passed to a retry.
     * @param boot The boot is the boot id passed to a retry.
     * @return The return value is false when this prompt is already finished.
     * @throws Exception The method throws this exception when the child call fails.
     */
    private boolean openSession(Child child, String full, String boot) throws Exception {
        child.step = "session/new";
        Outcome newer = call(child, "session/new", sessionNewParams(child.porchRules), false);
        if (stopHere(child, newer)) {
            sendCancel(child);
            dropHold(child);
            return false;
        }
        if (newer == Outcome.DEAD) {
            retry(child, full, boot);
            return false;
        }
        if (newer != Outcome.OK) {
            dropHold(child);
            if (newer == Outcome.FAIL) {
                finishFail(child);
            } else if (child.sink != null) {
                child.sink.done("pool down", deathMessage(child, false));
            }
            return false;
        }
        return true;
    }

    /**
     * The {@code sendTurn} method sends {@code session/prompt} and finishes the sink.
     * {@link #stopHere} cancels and drops the hold. {@link Outcome#DEAD} retries when nothing was forwarded and this is the first try. Otherwise it drops the hold and, when a sink is present, calls it with {@code pool down}. {@link Outcome#FAIL} drops the hold and finishes the failure.
     * {@link Outcome#OK} calls the sink with an empty reason and the assembled answer, then {@link #align} stores the hold.
     *
     * @param child The child is the live process.
     * @param body The body is the text sent on this prompt. It is the tail when the session extends.
     * @param full The full text is the whole working set stored on success.
     * @param boot The boot is the boot id stored on success.
     * @throws Exception The method throws this exception when the child call fails.
     */
    private void sendTurn(Child child, String body, String full, String boot) throws Exception {
        child.step = "session/prompt";
        child.assembled = new StringBuilder();
        Outcome turn = call(child, "session/prompt", promptParams(child, body), true);
        if (stopHere(child, turn)) {
            sendCancel(child);
            dropHold(child);
            return;
        }
        if (turn == Outcome.DEAD) {
            if (!child.forwarded && !child.retried) {
                retry(child, full, boot);
            } else {
                dropHold(child);
                if (child.sink != null) {
                    child.sink.done("pool down", deathMessage(child, child.retried));
                }
            }
            return;
        }
        if (turn == Outcome.FAIL) {
            dropHold(child);
            finishFail(child);
            return;
        }
        String answer;
        synchronized (child.io) {
            answer = child.assembled.toString();
        }
        child.sink.done("", answer);
        align(child, full, boot, answer);
    }

    /**
     * The {@code canExtend} method reports whether this prompt may send only the tail.
     * The hold must be valid. The boot must be non-empty and equal the held boot. {@link #rulesHold} must match. The process must be initialized, alive, and still running. The session id must be set. The held text must be non-empty. The full text must start with that held text and be longer.
     * A different rules string, including a change to empty, returns false, so the whole working set is sent once. The next success with the same rules may send the tail again.
     * // @aa-sdd PK-0467 PK-0517 PK-0526 docs/specs/java/org/casserole/pool/pool.spec.md
     *
     * @param child The child is the process that may already hold a session.
     * @param full The full text is the whole working set.
     * @param boot The boot is the boot id for this prompt.
     * @return The return value is true when only the tail should be sent.
     */
    private boolean canExtend(Child child, String full, String boot) {
        if (!child.holdValid || boot.length() == 0 || !boot.equals(child.heldBoot)) {
            return false;
        }
        if (!rulesHold(child.heldRules, child.porchRules)) {
            return false;
        }
        if (!child.initialized || !child.alive || child.link == null || !child.link.alive()) {
            return false;
        }
        if (child.sessionId == null || child.sessionId.length() == 0) {
            return false;
        }
        if (child.flatHeld == null || child.flatHeld.length() == 0 || full == null) {
            return false;
        }
        return full.startsWith(child.flatHeld) && full.length() > child.flatHeld.length();
    }

    /**
     * The {@code rulesHold} method reports whether the held rules still match this prompt.
     * A null held value returns false, because that hold does not remember rules. A null next value is compared as an empty string. An empty next matches an empty hold. A different string does not extend.
     * // @aa-sdd PK-0468 PK-0518 PK-0527 docs/specs/java/org/casserole/pool/pool.spec.md
     *
     * @param held The held text is the rules stored when this session opened, or null.
     * @param next The next text is the rules for this prompt. An empty string is the off value.
     * @return The return value is true when the rules still match.
     */
    static boolean rulesHold(String held, String next) {
        if (held == null) {
            return false;
        }
        String want = next == null ? "" : next;
        return held.equals(want);
    }

    /**
     * The {@code align} method stores the working set and the reply for a later tail send.
     * An empty boot drops the hold and returns. A non-empty answer is stored after the working set as {@code assistant}, a newline, the answer, and two newlines. An empty answer stores the working set alone. The held rules become the current rules string, or empty when that string is null. The hold is then valid.
     *
     * @param child The child receives the hold fields.
     * @param full The full text is the working set that was sent.
     * @param boot The boot is the boot id this hold belongs to.
     * @param answer The answer is the assembled reply text.
     */
    private void align(Child child, String full, String boot, String answer) {
        if (boot == null || boot.length() == 0) {
            dropHold(child);
            return;
        }
        String block = "";
        if (answer != null && answer.length() > 0) {
            block = "assistant\n" + answer + "\n\n";
        }
        child.heldBoot = boot;
        child.flatHeld = full + block;
        child.heldRules = child.porchRules == null ? "" : child.porchRules;
        child.holdValid = true;
    }

    /**
     * The {@code dropHold} method clears the held session.
     * The next prompt opens a session. The process stays up. The hold flag, the held text, the held boot, the held rules, and the session id are cleared.
     *
     * @param child The child loses its hold.
     */
    private void dropHold(Child child) {
        child.holdValid = false;
        child.flatHeld = null;
        child.heldBoot = null;
        child.heldRules = null;
        child.sessionId = null;
    }

    /**
     * The {@code retry} method restarts the process once and sends the whole working set on a new session.
     * If text was already forwarded, a retry already ran, cancel was asked, or done is skipped, the method cancels, drops the hold, and may call the sink with {@code pool down}. It then returns false.
     * A spawn failure drops the hold, calls the sink with {@code pool down}, and returns false. Initialize and {@code session/new} follow the same stop, fail, and down branches and return false.
     * The new prompt uses the whole text. A failed or dead turn drops the hold and finishes the sink. Success calls the sink with an empty reason and the answer, then stores the hold. Every path returns false.
     *
     * @param child The child is restarted.
     * @param text The text is the whole working set.
     * @param boot The boot is the boot id stored on success.
     * @return The return value is false on every path.
     * @throws Exception The method throws this exception when a child call fails.
     */
    private boolean retry(Child child, String text, String boot) throws Exception {
        if (child.forwarded || child.retried || child.cancelAsked || child.skipDone) {
            sendCancel(child);
            dropHold(child);
            if (!child.skipDone && !child.cancelAsked && child.sink != null) {
                child.sink.done("pool down", deathMessage(child, child.retried));
            }
            return false;
        }
        child.retried = true;
        try {
            respawn(child);
        } catch (IOException ex) {
            dropHold(child);
            child.sink.done("pool down", deathMessage(child, true));
            return false;
        }
        child.step = "initialize";
        Outcome opened = call(child, "initialize", initializeParams(), false);
        if (stopHere(child, opened)) {
            sendCancel(child);
            dropHold(child);
            return false;
        }
        if (opened != Outcome.OK) {
            dropHold(child);
            if (opened == Outcome.FAIL) {
                finishFail(child);
            } else if (child.sink != null) {
                child.sink.done("pool down", deathMessage(child, true));
            }
            return false;
        }
        child.initialized = true;
        child.step = "session/new";
        Outcome newer = call(child, "session/new", sessionNewParams(child.porchRules), false);
        if (stopHere(child, newer)) {
            sendCancel(child);
            dropHold(child);
            return false;
        }
        if (newer != Outcome.OK) {
            dropHold(child);
            if (newer == Outcome.FAIL) {
                finishFail(child);
            } else if (child.sink != null) {
                child.sink.done("pool down", deathMessage(child, true));
            }
            return false;
        }
        child.step = "session/prompt";
        child.assembled = new StringBuilder();
        child.forwarded = false;
        Outcome turn = call(child, "session/prompt", promptParams(child, text), true);
        if (stopHere(child, turn)) {
            sendCancel(child);
            dropHold(child);
            return false;
        }
        if (turn == Outcome.FAIL) {
            dropHold(child);
            finishFail(child);
            return false;
        }
        if (turn == Outcome.DEAD) {
            dropHold(child);
            child.sink.done("pool down", deathMessage(child, true));
            return false;
        }
        String answer;
        synchronized (child.io) {
            answer = child.assembled.toString();
        }
        child.sink.done("", answer);
        align(child, text, boot, answer);
        return false;
    }

    /**
     * The {@code stopHere} method reports whether the wait should end without a normal result.
     * The outcome {@link Outcome#STOP}, a skipped done, a cancel request, or a shut server returns true.
     *
     * @param child The child carries the skip and cancel flags.
     * @param outcome The outcome is the wait result.
     * @return The return value is true when the caller should stop this prompt.
     */
    private boolean stopHere(Child child, Outcome outcome) {
        return outcome == Outcome.STOP || child.skipDone || child.cancelAsked || shut;
    }

    /**
     * The {@code finishFail} method delivers the stored failure to the sink.
     * A null sink, a skipped done, or a cancel request returns without a call. An empty stored reason is sent as {@code pool payload}. A null stored message is sent as an empty string.
     *
     * @param child The child holds the failure reason and message.
     * @throws Exception The method throws this exception when the sink throws it.
     */
    private void finishFail(Child child) throws Exception {
        if (child.sink == null || child.skipDone || child.cancelAsked) {
            return;
        }
        String reason = child.failReason == null || child.failReason.length() == 0
                ? "pool payload" : child.failReason;
        String message = child.failMessage == null ? "" : child.failMessage;
        child.sink.done(reason, message);
    }

    /**
     * The {@code call} method writes one request and waits for its id.
     * The prompt flag is accepted and is not read by this method.
     *
     * @param child The child receives the request.
     * @param method The method is the JSON-RPC method name.
     * @param params The params are the JSON-RPC parameters.
     * @param prompt The flag is unused by this method.
     * @return The return value is the wait outcome.
     * @throws Exception The method throws this exception when the write fails or the wait is interrupted.
     */
    private Outcome call(Child child, String method, ObjectNode params, boolean prompt) throws Exception {
        int id = request(child, method, params);
        return waitFor(child, id);
    }

    /**
     * The {@code waitFor} method waits until the child's outcome id matches the request.
     * Shutdown, cancel, a skipped done, or a dead child also ends the wait. Those three flags return {@link Outcome#STOP}. A dead child returns {@link Outcome#DEAD}. A matching id returns the stored outcome.
     *
     * @param child The child is the process being waited on.
     * @param id The id is the request id this wait expects.
     * @return The return value is the outcome.
     * @throws InterruptedException The method throws this exception when the wait is interrupted.
     */
    private Outcome waitFor(Child child, int id) throws InterruptedException {
        synchronized (child.io) {
            while (!shut && !child.cancelAsked && !child.skipDone && child.outcomeId != id && child.alive) {
                child.io.wait();
            }
            if (child.skipDone || child.cancelAsked || shut) {
                return Outcome.STOP;
            }
            if (!child.alive) {
                return Outcome.DEAD;
            }
            return child.outcome;
        }
    }

    /**
     * The {@code request} method writes one JSON-RPC request and returns its id.
     * The envelope uses {@code jsonrpc} {@code 2.0}, the next id, the method, and the params. The expected id and method are stored before the write. The stored outcome is cleared to {@link Outcome#NONE}.
     *
     * @param child The child receives the line.
     * @param method The method is the JSON-RPC method name.
     * @param params The params are the JSON-RPC parameters.
     * @return The return value is the request id.
     * @throws IOException The method throws this exception when the write fails.
     */
    private int request(Child child, String method, ObjectNode params) throws IOException {
        synchronized (child.writeLock) {
            int id = child.nextId;
            child.nextId = id + 1;
            synchronized (child.io) {
                child.expectId = id;
                child.expectMethod = method;
                child.outcomeId = -1;
                child.outcome = Outcome.NONE;
            }
            ObjectNode envelope = mapper.createObjectNode();
            envelope.put("jsonrpc", "2.0");
            envelope.put("id", id);
            envelope.put("method", method);
            envelope.set("params", params);
            writeLocked(child, mapper.writeValueAsString(envelope));
            return id;
        }
    }

    /**
     * The {@code sendCancel} method writes {@code session/cancel} once.
     * If a cancel was already sent, the session id is missing, the process is missing, or the process is not alive, the sent flag is set and the method returns. Otherwise it writes a JSON-RPC request whose method is {@code session/cancel} and whose params carry the session id. A write failure is ignored.
     *
     * @param child The child receives the cancel line.
     */
    private void sendCancel(Child child) {
        synchronized (child.writeLock) {
            if (child.cancelSent || child.sessionId == null || child.link == null || !child.link.alive()) {
                child.cancelSent = true;
                return;
            }
            child.cancelSent = true;
            int id = child.nextId;
            child.nextId = id + 1;
            child.cancelId = id;
            ObjectNode params = mapper.createObjectNode();
            params.put("sessionId", child.sessionId);
            ObjectNode envelope = mapper.createObjectNode();
            envelope.put("jsonrpc", "2.0");
            envelope.put("id", id);
            envelope.put("method", "session/cancel");
            envelope.set("params", params);
            try {
                writeLocked(child, mapper.writeValueAsString(envelope));
            } catch (IOException ex) {
                // The child is already going away. The cell still ends without a second error.
            }
        }
    }

    /**
     * The {@code denyPermission} method answers one permission request with a refusal.
     * {@link #rejectOption} prefers {@code reject_once}, then {@code reject_always}. A found id is sent as outcome {@code selected}. No id is sent as outcome {@code cancelled}. The tool title, the command, and the input stay off this reply.
     * A missing id is written as JSON null. A write failure calls {@link #fail} with reason {@code pool tool refused} and place {@code session/request_permission}.
     *
     * @param child The child receives the reply.
     * @param node The node is the child's request.
     */
    private void denyPermission(Child child, JsonNode node) {
        JsonNode id = node.get("id");
        ObjectNode outcome = mapper.createObjectNode();
        String optionId = rejectOption(node);
        if (optionId.length() > 0) {
            outcome.put("outcome", "selected");
            outcome.put("optionId", optionId);
        } else {
            outcome.put("outcome", "cancelled");
        }
        ObjectNode result = mapper.createObjectNode();
        result.set("outcome", outcome);
        ObjectNode envelope = mapper.createObjectNode();
        envelope.put("jsonrpc", "2.0");
        if (id == null || id.isNull()) {
            envelope.putNull("id");
        } else {
            envelope.set("id", id);
        }
        envelope.set("result", result);
        try {
            String json = mapper.writeValueAsString(envelope);
            synchronized (child.writeLock) {
                writeLocked(child, json);
            }
        } catch (IOException ex) {
            fail(child, "pool tool refused", "session/request_permission", ex.getClass().getSimpleName());
        }
    }

    /**
     * The {@code rejectOption} method picks a refusal id from the permission options.
     * A missing options array returns an empty string. The first option whose kind is {@code reject_once} is returned at once. If none exists, the first kind {@code reject_always} is returned. An option with an empty id is skipped.
     *
     * @param node The node is the child's permission request.
     * @return The return value is the reject option id, or an empty string when the list has no reject kind.
     */
    private static String rejectOption(JsonNode node) {
        JsonNode params = node.get("params");
        JsonNode options = params == null ? null : params.get("options");
        if (options == null || !options.isArray()) {
            return "";
        }
        String always = "";
        for (int i = 0; i < options.size(); i++) {
            JsonNode option = options.get(i);
            if (option == null) {
                continue;
            }
            String kind = text(option.get("kind"));
            String id = text(option.get("optionId"));
            if (id.length() == 0) {
                continue;
            }
            if ("reject_once".equals(kind)) {
                return id;
            }
            if (always.length() == 0 && "reject_always".equals(kind)) {
                always = id;
            }
        }
        return always;
    }

    /**
     * The {@code writeLocked} method writes one JSON line and flushes it.
     * A null writer throws an I/O exception with the message {@code spawn}.
     *
     * @param child The child owns the writer.
     * @param json The json is one JSON object without a trailing newline.
     * @throws IOException The method throws this exception when the writer is missing or the write fails.
     */
    private void writeLocked(Child child, String json) throws IOException {
        OutputStreamWriter writer = child.writer;
        if (writer == null) {
            throw new IOException("spawn");
        }
        writer.write(json);
        writer.write('\n');
        writer.flush();
    }

    /**
     * The {@code initializeParams} method builds the {@code initialize} parameters.
     * {@code protocolVersion} is {@code 1}. {@code clientCapabilities} is an empty object.
     *
     * @return The return value is the parameters object.
     */
    private ObjectNode initializeParams() {
        ObjectNode params = mapper.createObjectNode();
        params.put("protocolVersion", 1);
        params.set("clientCapabilities", mapper.createObjectNode());
        return params;
    }

    /**
     * The {@code sessionNewParams} method builds {@code session/new} parameters for this server.
     * The working directory is the absolute pool directory. The rules string is passed through.
     *
     * @param rules The rules are placed in {@code _meta.rules} when the trimmed text is non-empty.
     * @return The return value is the parameters object from {@link #porchSessionParams}.
     */
    private ObjectNode sessionNewParams(String rules) {
        return porchSessionParams(mapper, cwd.getAbsolutePath(), rules);
    }

    /**
     * The {@code porchSessionParams} method builds {@code session/new} parameters.
     * A null directory is stored as an empty {@code cwd}. {@code mcpServers} is an empty array. A non-blank rules string is stored as {@code _meta.rules}. A null or blank string leaves {@code _meta} off. {@code yoloMode}, {@code autoMode}, and {@code systemPromptOverride} are not set.
     *
     * @param mapper The mapper creates the JSON objects.
     * @param cwd The cwd is the absolute pool directory.
     * @param rules The rules are the text for {@code _meta.rules}, or empty.
     * @return The return value is the parameters object.
     */
    static ObjectNode porchSessionParams(ObjectMapper mapper, String cwd, String rules) {
        ObjectNode params = mapper.createObjectNode();
        params.put("cwd", cwd == null ? "" : cwd);
        params.set("mcpServers", mapper.createArrayNode());
        if (rules != null && rules.trim().length() > 0) {
            ObjectNode meta = mapper.createObjectNode();
            meta.put("rules", rules);
            params.set("_meta", meta);
        }
        return params;
    }

    /**
     * The {@code promptParams} method builds {@code session/prompt} parameters.
     * The prompt is one block whose type is {@code text}. A null session id is sent as an empty string.
     *
     * @param child The child supplies the session id.
     * @param text The text is the prompt body.
     * @return The return value is the parameters object.
     */
    private ObjectNode promptParams(Child child, String text) {
        ArrayNode prompt = mapper.createArrayNode();
        ObjectNode block = mapper.createObjectNode();
        block.put("type", "text");
        block.put("text", text);
        prompt.add(block);
        ObjectNode params = mapper.createObjectNode();
        params.put("sessionId", child.sessionId == null ? "" : child.sessionId);
        params.set("prompt", prompt);
        return params;
    }

    /**
     * The {@code ensure} method returns a live child for the user, creating one when needed.
     * An existing live process is returned. A dead entry is destroyed and removed. When three children already exist, the method waits. The pool directory is created when it is missing. A failed create throws an I/O exception with the message {@code spawn}.
     * The new child starts at generation {@code 1}, and its pumps are submitted. After shutdown the method throws {@code spawn}.
     *
     * @param user The user is the map key.
     * @return The return value is the live child.
     * @throws IOException The method throws this exception when the directory or the process cannot be started.
     * @throws InterruptedException The method throws this exception when the cap wait is interrupted.
     */
    private Child ensure(String user) throws IOException, InterruptedException {
        synchronized (capLock) {
            while (!shut) {
                Child existing = children.get(user);
                if (existing != null && existing.alive && existing.link != null && existing.link.alive()) {
                    return existing;
                }
                if (existing != null) {
                    destroyProcess(existing);
                    children.remove(user, existing);
                }
                if (children.size() >= CAP) {
                    capLock.wait();
                    continue;
                }
                if (!cwd.isDirectory() && !cwd.mkdirs()) {
                    throw new IOException("spawn");
                }
                Child created = new Child(user);
                created.link = openLink();
                created.writer = new OutputStreamWriter(created.link.output(), StandardCharsets.UTF_8);
                created.alive = true;
                created.generation = 1;
                children.put(user, created);
                startPumps(created);
                return created;
            }
        }
        throw new IOException("spawn");
    }

    /**
     * The {@code respawn} method replaces the child's process.
     * The old process is destroyed. The pool directory is created when it is missing. The generation increases by one. The child is marked alive and not initialized. The hold is dropped. The stderr count and the cancel-sent flag are cleared. New pumps are submitted.
     *
     * @param child The child receives the new process.
     * @throws IOException The method throws this exception when the directory or the process cannot be started.
     */
    private void respawn(Child child) throws IOException {
        destroyProcess(child);
        if (!cwd.isDirectory() && !cwd.mkdirs()) {
            throw new IOException("spawn");
        }
        PoolLink link = openLink();
        child.generation = child.generation + 1;
        child.link = link;
        child.writer = new OutputStreamWriter(link.output(), StandardCharsets.UTF_8);
        child.alive = true;
        child.initialized = false;
        dropHold(child);
        child.stderrBytes = 0;
        child.cancelSent = false;
        startPumps(child);
    }

    /**
     * The {@code openLink} method opens the seat configured for this server.
     * A remote address dials the WebSocket. Any other address starts {@code grok agent stdio}.
     * A remote dial does not create the pool directory. The directory is still created by the caller before this method.
     *
     * @return The return value is the open link.
     * @throws IOException The method throws this exception when the process or the socket cannot start.
     */
    private PoolLink openLink() throws IOException {
        if (address != null && address.remote) {
            return PoolLink.remote(address.websocketUrl, address.secret);
        }
        return PoolLink.local(startProcess());
    }

    /**
     * The {@code startProcess} method starts {@code grok agent stdio}.
     * Windows uses {@code cmd.exe /c grok agent stdio}. Any other operating system uses {@code grok agent stdio}. The working directory is the pool directory. Standard input, output, and error are pipes. {@code XAI_API_KEY} is removed from the child environment without regard to case.
     *
     * @return The return value is the started process.
     * @throws IOException The method throws this exception when the process cannot start.
     */
    private Process startProcess() throws IOException {
        String os = System.getProperty("os.name", "");
        ProcessBuilder builder;
        if (os.toLowerCase().startsWith("windows")) {
            builder = new ProcessBuilder("cmd.exe", "/c", "grok", "agent", "stdio");
        } else {
            builder = new ProcessBuilder("grok", "agent", "stdio");
        }
        builder.directory(cwd);
        builder.redirectInput(ProcessBuilder.Redirect.PIPE);
        builder.redirectOutput(ProcessBuilder.Redirect.PIPE);
        builder.redirectError(ProcessBuilder.Redirect.PIPE);
        Map<String, String> env = builder.environment();
        Iterator<String> keys = env.keySet().iterator();
        while (keys.hasNext()) {
            String name = keys.next();
            if (name != null && name.equalsIgnoreCase("XAI_API_KEY")) {
                keys.remove();
            }
        }
        return builder.start();
    }

    /**
     * The {@code startPumps} method submits the stdout and stderr readers.
     * Both tasks use {@link Lane#POOL_IO}. The task names are {@code pool-stdout} and {@code pool-stderr}. Each task keeps the child's current generation.
     *
     * @param child The child supplies the process and the generation.
     */
    private void startPumps(Child child) {
        int generation = child.generation;
        lamina.submit(new LaminaTask(Lane.POOL_IO, "pool-stdout", new StdoutWork(this, child, generation)));
        lamina.submit(new LaminaTask(Lane.POOL_IO, "pool-stderr", new StderrWork(child, generation)));
    }

    /**
     * The {@code destroyProcess} method force-stops the child's process.
     * A null process returns. The method waits up to 2 seconds. An interrupt restores the thread interrupt flag.
     *
     * @param child The child owns the process.
     */
    private void destroyProcess(Child child) {
        PoolLink link = child.link;
        if (link == null) {
            return;
        }
        link.destroy();
    }

    /**
     * The {@code bumpIdle} method advances the idle token so an older timer does not reap this child.
     *
     * @param child The child owns the idle token.
     */
    private void bumpIdle(Child child) {
        child.idleToken = child.idleToken + 1;
    }

    /**
     * The {@code armIdle} method schedules a reap 15 minutes out.
     * A shut server returns. The idle token advances, and the timer stores that new token.
     *
     * @param child The child is the process the timer may destroy.
     */
    private void armIdle(Child child) {
        if (shut) {
            return;
        }
        child.idleToken = child.idleToken + 1;
        idleTimer.schedule(new IdleReap(this, child, child.idleToken), IDLE_MS);
    }

    /**
     * The {@code dispatch} method routes one stdout line.
     * A generation mismatch or a null line returns. A blank line returns. Text that is not JSON, or JSON that is not an object, fails with reason {@code pool parse} and place {@code stdout}.
     * An object with an id and a result or an error goes to {@link #onResponse}. An object with a method goes to {@link #onMethod}.
     *
     * @param child The child receives the parsed frame.
     * @param generation The generation is the process generation that read the line.
     * @param line The line is one stdout record.
     */
    private void dispatch(Child child, int generation, String line) {
        if (child.generation != generation || line == null) {
            return;
        }
        String trimmed = line.trim();
        if (trimmed.length() == 0) {
            return;
        }
        JsonNode node;
        try {
            node = mapper.readTree(trimmed);
        } catch (Exception ex) {
            fail(child, "pool parse", "stdout", quote(trimmed));
            return;
        }
        if (node == null || !node.isObject()) {
            fail(child, "pool parse", "stdout", quote(trimmed));
            return;
        }
        if (node.has("id") && (node.has("result") || node.has("error"))) {
            onResponse(child, node);
            return;
        }
        if (node.has("method")) {
            onMethod(child, node);
        }
    }

    /**
     * The {@code onResponse} method records one JSON-RPC response.
     * A missing id is read as {@code -1}. The cancel id is ignored. An id other than the expected id fails with reason {@code pool payload} and detail {@code id}. A non-null error goes to {@link #onErrorLocked}.
     * A {@code session/new} result with an empty session id fails with place {@code session/new} and detail {@code sessionId}. Any other accepted result stores {@link Outcome#OK} and wakes the waiter.
     *
     * @param child The child stores the outcome.
     * @param node The node is the response object.
     */
    private void onResponse(Child child, JsonNode node) {
        int id = node.get("id").asInt(-1);
        synchronized (child.io) {
            if (id == child.cancelId) {
                return;
            }
            if (id != child.expectId) {
                failLocked(child, "pool payload", child.step, "id");
                return;
            }
            if (node.has("error") && !node.get("error").isNull()) {
                onErrorLocked(child, node.get("error"));
                return;
            }
            JsonNode result = node.get("result");
            if ("session/new".equals(child.expectMethod)) {
                String session = result == null ? "" : text(result.get("sessionId"));
                if (session.length() == 0) {
                    failLocked(child, "pool payload", "session/new", "sessionId");
                    return;
                }
                child.sessionId = session;
            }
            child.outcome = Outcome.OK;
            child.outcomeId = id;
            child.io.notifyAll();
        }
    }

    /**
     * The {@code onErrorLocked} method records one JSON-RPC error.
     * The caller already holds the child lock. A message that contains login or auth words uses reason {@code pool auth} and sets the auth-failed flag. Any other error uses {@code pool payload}.
     * A non-auth error with a numeric code puts {@code code} and that number in the detail, then the redacted message when it is non-empty.
     *
     * @param child The child stores the failure.
     * @param error The error is the JSON-RPC error object.
     */
    private void onErrorLocked(Child child, JsonNode error) {
        String raw = text(error.get("message"));
        String redacted = redact(raw);
        boolean auth = authWords(redacted);
        String reason = auth ? "pool auth" : "pool payload";
        if (auth) {
            child.authFailed = true;
        }
        String detail = redacted;
        if (!auth && error.has("code") && error.get("code").isNumber()) {
            detail = "code " + error.get("code").asInt();
            if (redacted.length() > 0) {
                detail = detail + " " + redacted;
            }
        }
        failLocked(child, reason, child.step, detail);
    }

    /**
     * The {@code onMethod} method handles one child-initiated message.
     * A message with an id whose method name contains {@code permission} is refused by {@link #denyPermission}. Any other request fails with {@code pool payload} and then {@link #sendCancel} runs. The match ignores case.
     * A notice that is not {@code session/update} only increments the note count. {@code tool_call} and {@code tool_call_update} do the same. {@code agent_message_chunk} and {@code agent_thought_chunk} go to {@link #forward}. Any other update only increments the note count.
     *
     * @param child The child receives the message.
     * @param node The node is the JSON object.
     */
    private void onMethod(Child child, JsonNode node) {
        String method = text(node.get("method"));
        boolean request = node.has("id") && !node.get("id").isNull();
        if (request) {
            if (method.toLowerCase().indexOf("permission") >= 0) {
                denyPermission(child, node);
            } else {
                fail(child, "pool payload", child.step == null ? "stdout" : child.step, method);
                sendCancel(child);
            }
            return;
        }
        if (!"session/update".equals(method)) {
            note(child);
            return;
        }
        JsonNode params = node.get("params");
        JsonNode update = params == null ? null : params.get("update");
        if (update == null) {
            update = params;
        }
        String kind = update == null ? "" : text(update.get("sessionUpdate"));
        if ("tool_call".equals(kind) || "tool_call_update".equals(kind)) {
            note(child);
            return;
        }
        if ("agent_message_chunk".equals(kind) || "agent_thought_chunk".equals(kind)) {
            String chunk = chunkText(update);
            forward(child, "agent_thought_chunk".equals(kind), chunk);
            return;
        }
        note(child);
    }

    /**
     * The {@code forward} method sends one chunk to the sink.
     * A null or empty chunk returns. A thought chunk returns when think is off. A closed prompt or a null sink returns. Reply text is appended to the assembled answer. Thought text is not appended. The forwarded flag is set.
     * Thought text calls {@link Sink#thought}. Reply text calls {@link Sink#message}. {@link PoolChat.TooLarge} cancels the session, sets the skip flag, and does not call done. An I/O exception fails with {@code pool down}. Any other exception fails with {@code pool payload}.
     *
     * @param child The child owns the sink and the assembled answer.
     * @param thought The flag is true for {@code agent_thought_chunk}.
     * @param chunk The chunk is the text piece.
     */
    private void forward(Child child, boolean thought, String chunk) {
        if (chunk == null || chunk.length() == 0) {
            return;
        }
        if (thought && !child.think) {
            return;
        }
        Sink sink;
        synchronized (child.io) {
            if (!child.promptOpen || child.sink == null) {
                return;
            }
            if (!thought) {
                child.assembled.append(chunk);
            }
            child.forwarded = true;
            sink = child.sink;
        }
        try {
            if (thought) {
                sink.thought(chunk);
            } else {
                sink.message(chunk);
            }
        } catch (PoolChat.TooLarge ex) {
            sendCancel(child);
            synchronized (child.io) {
                child.skipDone = true;
                child.io.notifyAll();
            }
        } catch (Exception ex) {
            String reason = ex instanceof IOException ? "pool down" : "pool payload";
            fail(child, reason, "stdout", ex.getClass().getSimpleName());
        }
    }

    /**
     * The {@code note} method counts one ignored child message.
     *
     * @param child The child owns the note count.
     */
    private void note(Child child) {
        synchronized (child.io) {
            child.notes = child.notes + 1;
        }
    }

    /**
     * The {@code fail} method stores a failure while holding the child lock.
     *
     * @param child The child stores the failure.
     * @param reason The reason is the done reason.
     * @param place The place is the step name placed in the message.
     * @param detail The detail is the extra text placed in the message.
     */
    private void fail(Child child, String reason, String place, String detail) {
        synchronized (child.io) {
            failLocked(child, reason, place, detail);
        }
    }

    /**
     * The {@code failLocked} method stores a failure. The caller already holds the child lock.
     * If this expected id is already {@link Outcome#FAIL}, the method returns. Otherwise it stores the reason and the clipped message, closes the prompt, sets {@link Outcome#FAIL}, and wakes the waiter.
     *
     * @param child The child stores the failure.
     * @param reason The reason is the done reason.
     * @param place The place is the step name placed in the message.
     * @param detail The detail is the extra text placed in the message.
     */
    private void failLocked(Child child, String reason, String place, String detail) {
        if (child.outcome == Outcome.FAIL && child.outcomeId == child.expectId) {
            return;
        }
        child.failReason = reason;
        child.failMessage = clip(reason, place, detail, child);
        child.promptOpen = false;
        child.outcome = Outcome.FAIL;
        child.outcomeId = child.expectId;
        child.io.notifyAll();
    }

    /**
     * The {@code markDead} method marks one generation dead and wakes waiters.
     * The exit code is read when the process is already gone. A process that is still alive, or a missing process, keeps code {@code -1}. A generation mismatch returns without changing the child.
     * A matching generation clears alive, stores {@link Outcome#DEAD}, and wakes both the child wait and the cap wait.
     *
     * @param child The child is marked dead.
     * @param generation The generation is the process generation that ended.
     */
    private void markDead(Child child, int generation) {
        int code = -1;
        PoolLink link = child.link;
        if (link != null && !link.alive()) {
            code = link.exitCode();
        }
        synchronized (child.io) {
            if (child.generation != generation) {
                return;
            }
            child.alive = false;
            child.exitCode = code;
            child.outcome = Outcome.DEAD;
            child.outcomeId = child.expectId;
            child.io.notifyAll();
        }
        synchronized (capLock) {
            capLock.notifyAll();
        }
    }

    /**
     * The {@code chunkText} method reads the text from one session update.
     * A null update returns an empty string. A null content node returns an empty string. Textual content is returned as text. Any other content uses its {@code text} field.
     *
     * @param update The update is the session update object.
     * @return The return value is the chunk text, or an empty string.
     */
    private static String chunkText(JsonNode update) {
        if (update == null) {
            return "";
        }
        JsonNode content = update.get("content");
        if (content == null || content.isNull()) {
            return "";
        }
        if (content.isTextual()) {
            return content.asText("");
        }
        return text(content.get("text"));
    }

    /**
     * The {@code text} method reads a JSON node as text.
     * A null node or a JSON null returns an empty string.
     *
     * @param node The node is the JSON value.
     * @return The return value is the text, or an empty string.
     */
    private static String text(JsonNode node) {
        if (node == null || node.isNull()) {
            return "";
        }
        return node.asText("");
    }

    /**
     * The {@code authWords} method reports whether a message looks like an authentication failure.
     * A null message is treated as empty. The check is lowercase. The message matches when it contains {@code login} or {@code auth}.
     *
     * @param message The message is the error text.
     * @return The return value is true when the message contains those words.
     */
    private static boolean authWords(String message) {
        String lower = message == null ? "" : message.toLowerCase();
        return lower.indexOf("login") >= 0 || lower.indexOf("auth") >= 0;
    }

    /**
     * The {@code deathMessage} method builds the {@code pool down} detail for a dead process.
     * An exit code of zero or more is included. A forwarded turn adds {@code chunk 1}. A turn with no forwarded chunk adds {@code chunk 0}. A retry adds {@code retry used}. The pieces are clipped as place {@code exit}.
     *
     * @param child The child supplies the exit code, the forwarded flag, and the note counts.
     * @param retried The flag is true when this death follows a restart.
     * @return The return value is the clipped message.
     */
    private String deathMessage(Child child, boolean retried) {
        StringBuilder detail = new StringBuilder();
        if (child.exitCode >= 0) {
            detail.append(child.exitCode).append(' ');
        }
        detail.append(child.forwarded ? "chunk 1" : "chunk 0");
        if (retried) {
            detail.append(" retry used");
        }
        return clip("pool down", "exit", detail.toString().trim(), child);
    }

    /**
     * The {@code exceptionLine} method builds a {@code pool payload} line from an exception.
     * Only the first line of the exception message is kept. The detail starts with the exception class name. A non-empty message is redacted and appended. A null step uses place {@code stdout}.
     *
     * @param child The child supplies the step name.
     * @param ex The exception is the failure.
     * @return The return value is the clipped message.
     */
    private String exceptionLine(Child child, Exception ex) {
        String first = ex.getMessage() == null ? "" : ex.getMessage();
        int nl = first.indexOf('\n');
        if (nl >= 0) {
            first = first.substring(0, nl);
        }
        String detail = ex.getClass().getSimpleName();
        if (first.length() > 0) {
            detail = detail + " " + redact(first);
        }
        return clip("pool payload", child.step == null ? "stdout" : child.step, detail, child);
    }

    /**
     * The {@code quote} method redacts a stdout fragment and limits it to 80 characters.
     *
     * @param raw The raw text is the fragment.
     * @return The return value is the redacted fragment, cut at 80 characters.
     */
    private static String quote(String raw) {
        String redacted = redact(raw);
        if (redacted.length() > QUOTE_CAP) {
            return redacted.substring(0, QUOTE_CAP);
        }
        return redacted;
    }

    /**
     * The {@code clip} method builds one sink message and limits it to 240 characters.
     * The reason comes first. A non-empty place and a non-empty detail follow. A note count and a stderr byte count are appended when they are above zero. The text is redacted. Carriage return and line feed become spaces.
     * An empty result becomes the reason. A result that does not start with the reason is prefixed with the reason. Text longer than 240 characters is cut to 240.
     *
     * @param reason The reason is the done reason.
     * @param place The place is the step name.
     * @param detail The detail is the extra text.
     * @param child The child supplies note and stderr counts. A null child omits them.
     * @return The return value is the clipped message.
     */
    private static String clip(String reason, String place, String detail, Child child) {
        StringBuilder sb = new StringBuilder();
        sb.append(reason);
        if (place != null && place.length() > 0) {
            sb.append(' ').append(place);
        }
        if (detail != null && detail.length() > 0) {
            sb.append(' ').append(detail);
        }
        if (child != null && child.notes > 0) {
            sb.append(" notes: ").append(child.notes);
        }
        if (child != null && child.stderrBytes > 0) {
            sb.append(" stderr ").append(child.stderrBytes);
        }
        String text = redact(sb.toString()).replace('\r', ' ').replace('\n', ' ').trim();
        if (text.length() == 0) {
            text = reason;
        }
        if (!text.startsWith(reason)) {
            text = reason + " " + text;
        }
        if (text.length() > MESSAGE_CAP) {
            return text.substring(0, MESSAGE_CAP);
        }
        return text;
    }

    /**
     * The {@code redact} method hides secrets in one string.
     * A null or empty string returns empty. A bearer token becomes {@code redacted}. A token that starts with {@code xai-} or {@code sk-} and has at least eight more token characters becomes {@code redacted}. An {@code access_token}, {@code api_key}, or {@code refresh_token} assignment becomes {@code redacted}.
     *
     * @param raw The raw text may contain a secret.
     * @return The return value is the redacted text.
     */
    private static String redact(String raw) {
        if (raw == null || raw.length() == 0) {
            return "";
        }
        String text = raw.replaceAll("(?i)bearer\\s+\\S+", "redacted");
        text = text.replaceAll("(?i)(xai-|sk-)[A-Za-z0-9_\\-]{8,}", "redacted");
        text = text.replaceAll("(?i)\"?(access_token|api_key|refresh_token)\"?\\s*[:=]\\s*\"?[A-Za-z0-9._\\-]+\"?",
                "redacted");
        return text;
    }

    /**
     * The {@code readCapped} method reads one stdout line with a 1 MiB cap.
     * Carriage return is skipped. A zero byte marks the line, clears the buffer, and drops the rest of the line. A buffer that reaches 1 MiB marks the line over size, clears the buffer, and drops the rest. End of stream with an empty clean buffer returns an eof line. Any other end returns the bytes read so far.
     *
     * @param in The stream is the child stdout.
     * @return The return value is the line record.
     * @throws IOException The method throws this exception when the read fails.
     */
    private Line readCapped(InputStream in) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        boolean nul = false;
        boolean over = false;
        while (true) {
            int b = in.read();
            if (b < 0) {
                Line line = new Line();
                if (buf.size() == 0 && !nul && !over) {
                    line.eof = true;
                    return line;
                }
                line.nul = nul;
                line.over = over;
                line.text = new String(buf.toByteArray(), StandardCharsets.UTF_8);
                return line;
            }
            if (b == '\n') {
                break;
            }
            if (b == '\r') {
                continue;
            }
            if (b == 0) {
                nul = true;
                buf.reset();
                continue;
            }
            if (nul || over) {
                continue;
            }
            if (buf.size() >= LINE_CAP) {
                over = true;
                buf.reset();
                continue;
            }
            buf.write(b);
        }
        Line line = new Line();
        line.nul = nul;
        line.over = over;
        line.text = new String(buf.toByteArray(), StandardCharsets.UTF_8);
        return line;
    }

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The {@code Outcome} enum is the result of one JSON-RPC wait.
     * {@code NONE} is the value before a reply. {@code OK} is a result. {@code FAIL} is an error or a bad frame. {@code DEAD} means the process is gone. {@code STOP} means cancel, shutdown, or a skipped done.
     */
    private enum Outcome {
        NONE,
        OK,
        FAIL,
        DEAD,
        STOP
    }

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The {@code Line} class is one stdout read.
     * The text is the decoded line. The nul flag means a zero byte was seen. The over flag means the line passed 1 MiB. The eof flag means the stream ended before a line.
     */
    private static final class Line {
        private String text = "";
        private boolean nul;
        private boolean over;
        private boolean eof;
    }

    // @aa-sdd PK-0469 PK-0519 PK-0528 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0505 docs/specs/java/org/casserole/pool/pool.spec.md
    /**
     * The {@code Child} class is one ACP seat for one user.
     * It holds the link, the session id, the hold text, and the prompt sink.
     * A blank host keeps a {@code grok agent stdio} process. A set host keeps a WebSocket to {@code grok agent serve}.
     */
    private static final class Child {
        private final String user;
        private final Object promptGate = new Object();
        private final Object io = new Object();
        private final Object writeLock = new Object();
        private PoolLink link;
        private OutputStreamWriter writer;
        private volatile boolean alive;
        private volatile boolean initialized;
        private volatile boolean inPrompt;
        private volatile boolean promptOpen;
        private volatile boolean think;
        private volatile boolean forwarded;
        private volatile boolean retried;
        private volatile boolean cancelAsked;
        private volatile boolean cancelSent;
        private volatile boolean skipDone;
        private volatile boolean authFailed;
        private volatile int generation;
        private volatile int notes;
        private volatile long stderrBytes;
        private volatile int exitCode = -1;
        private volatile int idleToken;
        private volatile Sink sink;
        private String sessionId;
        /** The field is the boot id this hold belongs to. */
        private String heldBoot;
        /** The field is the working set already in the session, plus the stored reply block. */
        private String flatHeld;
        /** The field is the rules this hold opened with. Null means the hold does not remember them. */
        private String heldRules;
        /** The field is true when a later prompt may send only the tail. */
        private boolean holdValid;
        /** The field is the rules string for the next {@code session/new}. An empty string leaves {@code _meta} off. */
        private String porchRules = "";
        private String step = "spawn";
        private String expectMethod = "";
        private String failReason;
        private String failMessage;
        private StringBuilder assembled = new StringBuilder();
        private int nextId = 1;
        private int expectId = -1;
        private int cancelId = -1;
        private int outcomeId = -1;
        private Outcome outcome = Outcome.NONE;

        /**
         * The constructor stores the user name.
         *
         * @param user The user is the map key for this child.
         */
        private Child(String user) {
            this.user = user;
        }
    }

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The {@code StdoutWork} class reads one generation of child stdout.
     * End of stream, a read error, or a zero byte and an over-long line are reported back to the server.
     */
    private static final class StdoutWork implements Runnable {
        private final PoolChatServer server;
        private final Child child;
        private final int generation;

        /**
         * The constructor stores the server, the child, and the generation.
         *
         * @param server The server receives parsed lines and failures.
         * @param child The child owns the process.
         * @param generation The generation is the process generation this reader serves.
         */
        private StdoutWork(PoolChatServer server, Child child, int generation) {
            this.server = server;
            this.child = child;
            this.generation = generation;
        }

        /**
         * The {@code run} method reads stdout until the generation ends.
         * A null process marks the child dead and returns. End of stream and a read error do the same. A generation change returns. A zero byte fails with detail {@code NUL}. A line over 1 MiB fails with detail {@code line over 1 MiB}. Any other line is dispatched.
         */
        @Override
        public void run() {
            PoolLink link = child.link;
            if (link == null) {
                server.markDead(child, generation);
                return;
            }
            InputStream in = link.input();
            try {
                while (child.generation == generation && !server.shut) {
                    Line line = server.readCapped(in);
                    if (line.eof) {
                        server.markDead(child, generation);
                        return;
                    }
                    if (child.generation != generation) {
                        return;
                    }
                    if (line.nul) {
                        server.fail(child, "pool parse", "stdout", "NUL");
                        continue;
                    }
                    if (line.over) {
                        server.fail(child, "pool parse", "stdout", "line over 1 MiB");
                        continue;
                    }
                    server.dispatch(child, generation, line.text);
                }
            } catch (IOException ex) {
                server.markDead(child, generation);
            }
        }
    }

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The {@code StderrWork} class counts bytes on one generation of child stderr.
     * It does not parse the bytes.
     */
    private static final class StderrWork implements Runnable {
        private final Child child;
        private final int generation;

        /**
         * The constructor stores the child and the generation.
         *
         * @param child The child owns the stderr count.
         * @param generation The generation is the process generation this reader serves.
         */
        private StderrWork(Child child, int generation) {
            this.child = child;
            this.generation = generation;
        }

        /**
         * The {@code run} method adds each stderr read to the child's byte count.
         * A null process returns. End of stream returns. A read error returns. The loop stops when the generation changes. Each read uses a 4096 byte buffer.
         */
        @Override
        public void run() {
            PoolLink link = child.link;
            if (link == null) {
                return;
            }
            InputStream err = link.error();
            byte[] buf = new byte[4096];
            try {
                while (child.generation == generation) {
                    int n = err.read(buf);
                    if (n < 0) {
                        return;
                    }
                    child.stderrBytes = child.stderrBytes + n;
                }
            } catch (IOException ex) {
                return;
            }
        }
    }

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The {@code IdleReap} class destroys one idle child when its token still matches.
     */
    private static final class IdleReap extends java.util.TimerTask {
        private final PoolChatServer server;
        private final Child child;
        private final int token;

        /**
         * The constructor stores the server, the child, and the idle token.
         *
         * @param server The server destroys the process and removes the child.
         * @param child The child is the process that may be idle.
         * @param token The token is the idle token captured when the timer was armed.
         */
        private IdleReap(PoolChatServer server, Child child, int token) {
            this.server = server;
            this.child = child;
            this.token = token;
        }

        /**
         * The {@code run} method destroys the child when it is still idle.
         * A child inside a prompt, a token that no longer matches, or a child that is not alive returns. Otherwise the process is destroyed, the alive flag is cleared, the child is removed, and the cap wait is woken.
         */
        @Override
        public void run() {
            synchronized (server.capLock) {
                if (child.inPrompt || child.idleToken != token || !child.alive) {
                    return;
                }
                server.destroyProcess(child);
                child.alive = false;
                server.children.remove(child.user, child);
                server.capLock.notifyAll();
            }
        }
    }

    // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
    // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
    // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
    // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
    // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
    // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
    // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
    // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
    // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
    // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
    // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
    // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
    // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
    // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
    // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
    // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
    // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
    // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
    /**
     * The {@code ShutdownWork} class runs {@link PoolChatServer#shutdown} from the runtime hook.
     */
    private static final class ShutdownWork implements Runnable {
        private final PoolChatServer server;

        /**
         * The constructor stores the server.
         *
         * @param server The server is shut down when the hook runs.
         */
        private ShutdownWork(PoolChatServer server) {
            this.server = server;
        }

        /**
         * The {@code run} method calls {@link PoolChatServer#shutdown}.
         */
        @Override
        public void run() {
            server.shutdown();
        }
    }
}
/*****************************************************************************************
 * End of Class: PoolChatServer.java 102226
 ****************************************************************************************/
