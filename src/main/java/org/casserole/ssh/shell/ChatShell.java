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

package org.casserole.ssh.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.aetheris.ssh.AmwpFacade;
import org.aetheris.swing.GrokSettingsDocument;
import org.aetheris.wire.AmwpCodec;
import org.aetheris.wire.AmwpToon;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.Signal;
import org.apache.sshd.server.SignalListener;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.session.ServerSession;
import org.casserole.chat.ChatSession;
import org.casserole.edit.EditMemory;
import org.casserole.mood.MoodDesk;
import org.roux.toon.ToonCodec;
import org.casserole.chat.ShellMode;
import org.casserole.chat.SpinnerStyle;
import org.casserole.chat.ThinkingClock;
import org.casserole.chat.cmd.CommandContext;
import org.casserole.chat.cmd.CommandProcessor;
import org.casserole.config.CasseroleProperties;
import org.casserole.format.FormatContext;
import org.casserole.format.FormatResult;
import org.casserole.format.FormatRouter;
import org.casserole.lamina.Lamina;
import org.casserole.lamina.LaminaHandle;
import org.casserole.lamina.LaminaTask;
import org.casserole.lamina.Lane;
import org.casserole.llm.LlmRouter;
import org.casserole.llm.LlmService;
import org.roux.session.RegionOwner;
import org.roux.token.HeuristicTokenCounter;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;

// @aa-sdd GK-1013 aetheris-plan-f-173.001.md FINESSE:f-173
// @aa-sdd GK-1014 aetheris-plan-f-185.001.md FINESSE:f-185
// @aa-sdd GK-1015 aetheris-stage-2-finessing.001.md FINESSE:f-222
// @aa-sdd GK-1016 aetheris-plan-f-36.001.md FINESSE:f-36
// @aa-sdd GK-1017 aetheris-plan-f-38.001.md FINESSE:f-38
// @aa-sdd GK-1018 aetheris-plan-f-39.001.md FINESSE:f-39
// @aa-sdd GK-1019 aetheris-plan-f-40.001.md FINESSE:f-40
// @aa-sdd GK-1020 aetheris-plan-f-41.001.md FINESSE:f-41
// @aa-sdd GK-1021 aetheris-plan-f-75.001.md FINESSE:f-75
// @aa-sdd GK-1022 aetheris-plan-f-76.001.md FINESSE:f-76
// @aa-sdd GK-1023 FEAT-005.spinner-colors.md
// @aa-sdd GK-1024 FEAT-006.stop-spinner-timer.md
// @aa-sdd GK-1025 FEAT-007.transfer-conversation.md
/**
 * The {@code ChatShell} class is the VT100 shell for one SSH connection.
 * It implements {@link Command}, {@link Runnable}, and {@link CommandContext}. It holds the line buffer, the escape mode, and the chat session.
 * Command echo is green. Enter goes to the command processor or the model worker. The shell does not name the commands. Terminal bytes stay on this connection.
 * // @aa-sdd L40 aa-sdd-source-prose.md#ChatShell
 *
 * @author clayl (2026-08-24)
 */
// @aa-sdd PK-684 docs/specs/common/grok.spec.md
// @aa-sdd PK-685 docs/specs/common/grok.spec.md
// @aa-sdd PK-412 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-415 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-734 docs/specs/common/stages.spec.md
// @aa-sdd PK-417 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-735 docs/specs/common/stages.spec.md
// @aa-sdd GK-1316 aetheris-plan-stage-1.001.md
// @aa-sdd PK-738 docs/specs/common/stages.spec.md
// @aa-sdd PK-418 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-742 docs/specs/common/stages.spec.md
// @aa-sdd PK-420 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-421 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-422 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-747 docs/specs/common/stages.spec.md
// @aa-sdd GK-1402 aetheris-pre-plan-stage-1.001.md
// @aa-sdd PK-423 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-748 docs/specs/common/stages.spec.md
// @aa-sdd PK-425 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-1638 aetheris-stage-2.draft.001.md
// @aa-sdd GK-1676 aetheris-stage-2.draft.001.md
// @aa-sdd PK-780 docs/specs/common/stages.spec.md
// @aa-sdd PK-426 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-428 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-430 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-431 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-432 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-1770 aetheris-plan-f-170.001.md
// @aa-sdd PK-641 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-439 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-440 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-441 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-1933 aetheris-pre-plan-f-170.001.md
// @aa-sdd PK-690 docs/specs/common/pre-plan.spec.md
// @aa-sdd GK-1983 aetheris-pre-plan-f-191.001.md
// @aa-sdd PK-443 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-2027 aetheris-pre-plan-f-22.001.md
// @aa-sdd PK-444 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-2086 aetheris-pre-plan-f-36.001.md
// @aa-sdd GK-2106 BF-001.amwp-init-echo.md
// @aa-sdd GK-2108 BF-001.amwp-init-echo.md
// @aa-sdd PK-714 docs/specs/common/server.spec.md
// @aa-sdd PK-715 docs/specs/common/server.spec.md
// @aa-sdd PK-445 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-446 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-2140 BF-008.user-switch-orphans-run.md
// @aa-sdd PK-717 docs/specs/common/server.spec.md
// @aa-sdd GK-2147 BF-008.user-switch-orphans-run.md
// @aa-sdd PK-447 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-2156 bugs-fixed.md
// @aa-sdd GK-2158 BF-002.wrap-duplicate-line.md
// @aa-sdd GK-2159 BF-007.paste-no-return.md
// @aa-sdd PK-448 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-2162 BF-009.server-page.md
// @aa-sdd GK-2165 BF-011.still-page.md
// @aa-sdd PK-450 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-454 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-351 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-455 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-456 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-724 docs/specs/common/server.spec.md
// @aa-sdd PK-704 docs/specs/common/roux.spec.md
// @aa-sdd GK-2319 roux-clip-range.md
// @aa-sdd PK-458 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-459 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-2368 roux-session-restore-plan.md
// @aa-sdd GK-2372 roux-session-restore-plan.md
// @aa-sdd PK-460 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-461 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-2390 roux-session-restore.md
// @aa-sdd GK-2396 roux-session-restore.md
// @aa-sdd PK-462 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-2426 rust-aetheris-client-finesse.draft.001.md
// @aa-sdd GK-2444 rust-aetheris-client.draft.001.md
// @aa-sdd PK-464 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-465 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-710 docs/specs/common/rust.spec.md
// @aa-sdd PK-711 docs/specs/common/rust.spec.md
// @aa-sdd PK-467 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-468 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-469 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-470 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-633 docs/specs/rust/aetheris/aetheris-term/src/keys.spec.md
// @aa-sdd PK-471 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-473 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-475 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd PK-759 docs/specs/common/stages.spec.md
// @aa-sdd PK-760 docs/specs/common/stages.spec.md
// @aa-sdd PK-761 docs/specs/common/stages.spec.md
// @aa-sdd PK-762 docs/specs/common/stages.spec.md
// @aa-sdd PK-763 docs/specs/common/stages.spec.md
// @aa-sdd GK-2591 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-2599 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-768 docs/specs/common/stages.spec.md
// @aa-sdd PK-400 docs/specs/java/org/casserole/log/log.spec.md
// @aa-sdd GK-2622 aetheris-stages-1-3.finalize.md
// @aa-sdd PK-782 docs/specs/common/stages.spec.md
// @aa-sdd PK-433 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-2758 aetheris-plan-f-191.001.md
// @aa-sdd PK-341 docs/specs/java/org/casserole/edit/edit.spec.md
// @aa-sdd PK-342 docs/specs/java/org/casserole/edit/edit.spec.md
// @aa-sdd PK-670 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-672 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-674 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-675 docs/specs/common/formal-plan.spec.md
// @aa-sdd GK-2973 aetheris-plan-f-75.001.md
// @aa-sdd GK-2974 aetheris-plan-f-76.001.md
// @aa-sdd PK-725 docs/specs/common/server.spec.md
public class ChatShell implements Command, Runnable, CommandContext {

    private static final char ESC = 0x1B;
    private static final int CTRL_A = 1;
    private static final int CTRL_C = 3;
    private static final int CTRL_E = 5;
    /** The field is the Ctrl+Z byte. It undoes the unsent line. That undo is not written to the history file. */
    private static final int CTRL_Z = 0x1A;
    /** The field is the redo sequence. It is Shift-Ctrl-Z, which xterm sends as modifier 6. */
    private static final String REDO_SEQ = "\u001B[1;6Z";
    private static final DateTimeFormatter REPLY_STAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String BRACKETED_PASTE_ENABLE = "\u001B[?2004h";
    private static final String BRACKETED_PASTE_DISABLE = "\u001B[?2004l";
    private static final String PASTE_START = "\u001B[200~";
    private static final String PASTE_END = "\u001B[201~";

    private final ChatSession session;
    private final CommandProcessor processor;
    private final LlmService llmService;
    private final LlmRouter llmRouter;
    private final CasseroleProperties properties;
    private String modelName;
    /** The field is the think string for this console. An empty string means off. */
    private String consoleThink;
    /** The field is the family word for this console. The values are {@code ollama}, {@code grok}, and {@code pool}. */
    private String consoleProvider;
    /** The field is true when the console-model file version is not 1. That file is left unread and is not overwritten. */
    private boolean consoleModelBlocked;
    /** The field is the Grok model id for this console. The Ollama id stays in {@link #modelName}. */
    private String consoleGrokModel;
    /** The field is the Grok think flag. It is applied by {@link #seatConsoleGrokThink} only when the text is {@code true}. */
    private String consoleGrokThink;
    /** The field is the Grok effort word. */
    private String consoleEffort;
    /** The field is false when {@code reasoning_effort} stays off the Grok body. */
    private boolean consoleSendEffort;
    /** The field is the Grok {@code stream} flag. */
    private boolean consoleStream;
    /** The field is false when the turn does not send a temperature override. */
    private boolean consoleTemperaturePresent;
    private double consoleTemperature;
    /** The field is false when the turn does not send a max-token override. */
    private boolean consoleMaxPresent;
    private int consoleMaxTokens;
    /** The field is stored with the console model. A console turn keeps ephemeral false and copies this flag to burner cloud. */
    private boolean consoleBurner;
    /** The field is the pool think string. {@code true} means on. */
    private String consolePoolThink;
    /** The field is one pool boot id for this shell. */
    private final String consolePoolBoot;
    /** The field is the family of the turn now in flight. Ctrl+C on {@code pool} cancels that child. */
    private String turnProvider;
    private final AmwpFacade amwpFacade;
    private final FormatRouter formatRouter;
    private final Lamina lamina;

    private InputStream in;
    /** The field is true while {@code message edit} is reading a body. A line that is {@code <.>} ends that body. */
    private boolean collectingEdit;
    private long editSeq;
    private StringBuilder editBody;
    private OutputStream out;
    private OutputStream err;
    private ExitCallback callback;
    private LaminaHandle readHandle;
    private String sshUsername;
    private int terminalColumns;
    private Environment environment;
    private int lastDrawnRows;
    /** The field is the row of the cursor inside the drawn page. Zero is the prompt row. */
    private int lastCursorRow;
    /** The field is the column of the cursor inside the drawn page. */
    private int lastCursorCol;
    /** The field is the text the screen is showing. A character edit diffs against this text. */
    private String paintedText = "";
    /** The field is the cursor index that {@link #paintedText} was painted with. */
    private int paintedCursor;
    /** The field is true when the prompt is on the current line, so a character edit can patch it. */
    private boolean promptLive;
    /** The field is true when keys in this read share one paint. */
    private boolean paintPending;

    private final StringBuilder lineBuffer;
    private int editCursor;
    private final List<String> history;
    private int historyCursor;
    private String historyDraft;
    /**
     * The field is the undo memory for the unsent draft. A history visit keeps its own undo memory.
     * Neither memory is written to the history file.
     * // @aa-sdd GK-1026 aetheris-plan-f-191.001.md FINESSE:f-191
     */
    private EditMemory draftSeat;
    /** The field is the undo memory for the history line now on the screen. A history move drops this stack. */
    private EditMemory visitSeat;
    private final StringBuilder escapeBuffer;
    private boolean inEscapeSequence;
    private boolean lastWasCR;
    private boolean pasting;
    private boolean pasteSealed;
    private int pasteStartIndex;
    private ShellMode mode;
    private boolean exiting;

    private final Timer escapeTimer;
    private TimerTask pendingEscapeTask;

    private final Timer spinnerTimer;
    private TimerTask spinnerTask;
    private boolean spinnerRunning;
    private int spinnerFrame;
    private long spinnerStartedAtMs;

    private final AtomicReference<LaminaHandle> inFlight;
    private final AtomicBoolean turnCancelled;

    /**
     * The constructor stores the session, the command processor, and the model services.
     * Think starts empty. The family starts as {@code ollama}. Effort sending starts on. Stream starts off. Temperature starts at {@code 1.0} and is not sent. The max-token value starts at {@code 128000} and is not sent. The pool boot is a new random id. The mode starts as chat. The terminal width starts at 80.
     * The escape timer is a daemon named {@code casserole-esc}. The spinner timer is a daemon named {@code casserole-spinner}.
     *
     * @param session The session is the chat state for this connection.
     * @param processor The processor is the command registry for this connection.
     * @param llmService The service is the Ollama boundary stored for this shell.
     * @param properties The properties are the server settings.
     * @param modelName The model name is the configured Ollama id used for status.
     * @param formatRouter The router formats a reply before it is written.
     * @param lamina The lamina submits the read loop and the model turn.
     * @param llmRouter The router is the provider map. The console turn calls {@link AmwpFacade#completeTurn} and does not take the execute permit.
     * @param amwpFacade The facade runs a console turn and a framed turn on the same path.
     */
    public ChatShell(
            ChatSession session,
            CommandProcessor processor,
            LlmService llmService,
            CasseroleProperties properties,
            String modelName,
            FormatRouter formatRouter,
            Lamina lamina,
            LlmRouter llmRouter,
            AmwpFacade amwpFacade) {
        this.session = session;
        this.processor = processor;
        this.llmService = llmService;
        this.properties = properties;
        this.modelName = modelName;
        this.consoleThink = "";
        this.consoleProvider = "ollama";
        this.consoleGrokModel = "";
        this.consoleGrokThink = "";
        this.consoleEffort = "";
        this.consoleSendEffort = true;
        this.consoleStream = false;
        this.consoleTemperaturePresent = false;
        this.consoleTemperature = 1.0d;
        this.consoleMaxPresent = false;
        this.consoleMaxTokens = 128000;
        this.consoleBurner = false;
        this.consolePoolThink = "";
        this.consolePoolBoot = UUID.randomUUID().toString();
        this.turnProvider = "ollama";
        this.amwpFacade = amwpFacade;
        this.formatRouter = formatRouter;
        this.lamina = lamina;
        this.llmRouter = llmRouter;
        this.lineBuffer = new StringBuilder();
        this.editCursor = 0;
        this.history = new ArrayList<String>();
        this.historyCursor = -1;
        this.historyDraft = "";
        this.draftSeat = new EditMemory();
        this.visitSeat = new EditMemory();
        this.escapeBuffer = new StringBuilder();
        this.mode = ShellMode.CHAT;
        this.escapeTimer = new Timer("casserole-esc", true);
        this.spinnerTimer = new Timer("casserole-spinner", true);
        this.inFlight = new AtomicReference<LaminaHandle>();
        this.turnCancelled = new AtomicBoolean(false);
        this.sshUsername = "";
        this.terminalColumns = 80;
        this.lastDrawnRows = 1;
        this.lastCursorRow = 0;
    }

    /**
     * The {@code setInputStream} method stores the SSH input stream.
     *
     * @param in The stream is the SSH input.
     */
    @Override
    public void setInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * The {@code setOutputStream} method stores the SSH output stream.
     *
     * @param out The stream is the SSH output.
     */
    @Override
    public void setOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * The {@code setErrorStream} method stores the SSH error stream.
     *
     * @param err The stream is the SSH error output.
     */
    @Override
    public void setErrorStream(OutputStream err) {
        this.err = err;
    }

    /**
     * The {@code setExitCallback} method stores the exit callback.
     *
     * @param callback The callback is invoked when the shell exits.
     */
    @Override
    public void setExitCallback(ExitCallback callback) {
        this.callback = callback;
    }

    /**
     * The {@code start} method prepares the connection and submits the read loop.
     * The SSH user name is read from the server session. A failure stores an empty name. The terminal width comes from {@link #parseColumns}. Window-change listening starts. History and the console model are loaded.
     * Bracketed paste is enabled. The welcome text is {@code Welcome to Casserole chat}, then the type, escape, Ctrl+C, and exit lines, then the paste line. A restored session writes the restored count and the user name. Any other session writes {@code New session}. The prompt is written. The read task is named {@code casserole-chat-shell} on {@link Lane#SSH_IO}.
     *
     * @param channel The channel is the SSH channel for this shell.
     * @param env The environment carries the terminal columns.
     * @throws IOException The method declares this exception when a write fails.
     */
    @Override
    public void start(ChannelSession channel, Environment env) throws IOException {
        try {
            ServerSession serverSession = channel.getServerSession();
            if (serverSession != null && serverSession.getUsername() != null) {
                sshUsername = serverSession.getUsername();
            }
        } catch (Exception ignored) {
            sshUsername = "";
        }
        this.environment = env;
        terminalColumns = parseColumns(env);
        listenForWindowChange(channel, env);
        loadHistory();
        loadConsoleModel();
        write(BRACKETED_PASTE_ENABLE);
        write("Welcome to Casserole chat\r\n");
        write("Type to chat. Escape toggles command mode. Ctrl+C cancels. exit hangs up.\r\n");
        write("Paste inserts into the buffer (Ctrl+Shift+V or Load File); Enter submits once. Shift-Enter adds a line.\r\n\r\n");
        if (session.wasRestored()) {
            write("Restored " + session.getRestoredCount() + " messages for " + sshUsername + "\r\n\r\n");
        } else {
            write("New session\r\n\r\n");
        }
        writePrompt();
        LaminaTask readTask = new LaminaTask(Lane.SSH_IO, "casserole-chat-shell", this);
        readHandle = lamina.submit(readTask);
    }

    /**
     * The {@code destroy} method closes this shell.
     * The exit flag is set. A paste is cleared. The in-flight turn is cancelled. Bracketed paste is disabled. The session is closed. The spinner and the escape timer are stopped and cancelled. The read task is cancelled.
     *
     * @param channel The channel is the SSH channel being closed.
     */
    @Override
    public void destroy(ChannelSession channel) {
        exiting = true;
        pasting = false;
        turnCancelled.set(true);
        LaminaHandle future = inFlight.getAndSet(null);
        if (future != null) {
            future.cancel(true);
        }
        write(BRACKETED_PASTE_DISABLE);
        if (session != null) {
            session.close();
        }
        stopSpinner();
        cancelEscapeTimer();
        escapeTimer.cancel();
        spinnerTimer.cancel();
        if (readHandle != null) {
            readHandle.cancel(true);
        }
    }

    /**
     * The {@code run} method reads SSH input until the stream ends or the shell exits.
     * Each read uses a 1024 character buffer. Characters are handled under the shell lock, and one paint is flushed after the buffer. A clean end calls the exit callback with status {@code 0}. Any other failure calls it with status {@code 1} and the exception message. An exit that is already underway does not call the callback.
     */
    @Override
    public void run() {
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        char[] buffer = new char[1024];
        try {
            int n;
            while (!exiting && (n = reader.read(buffer)) != -1) {
                synchronized (this) {
                    for (int i = 0; i < n; i++) {
                        handleChar(buffer[i]);
                    }
                    flushPaint();
                }
            }
            if (!exiting) {
                callback.onExit(0);
            }
        } catch (Exception ex) {
            if (!exiting) {
                callback.onExit(1, ex.getMessage());
            }
        }
    }

    /**
     * The {@code handleChar} method dispatches one decoded character.
     * An exiting shell returns. {@link #takeChar} handles the byte. {@link #afterEdit} then records a text change as one undo step. Cursor motion is not an undo step.
     *
     * @param c The character is one decoded input character.
     * @throws IOException The method throws this exception when a write fails.
     */
    private synchronized void handleChar(char c) throws IOException {
        if (exiting) {
            return;
        }
        int historyBefore = historyCursor;
        try {
            takeChar(c);
        } finally {
            afterEdit(historyBefore);
        }
    }

    /**
     * The {@code takeChar} method applies one byte of the line editor.
     * An escape in progress is appended until {@link #isCompleteEscapeSequence} accepts it. A bare escape starts that sequence and the timer. Ctrl+C calls {@link #handleCtrlC}. Ctrl+A moves to the start. Ctrl+E moves to the end. Those two moves are skipped during a paste.
     * Carriage return submits, or inserts a newline during a paste. A line feed inserts a soft newline when {@link #softReturn} is true. Backspace is byte 127 or byte 8. Ctrl+Z calls {@link #undoLine} outside a paste. A printable character is inserted, or appended during a paste. A sealed paste ignores printable characters until Enter, Ctrl+C, or backspace.
     *
     * @param c The character is one input byte.
     * @throws IOException The method throws this exception when a write fails.
     */
    private void takeChar(char c) throws IOException {
        if (exiting) {
            return;
        }
        int b = c & 0xFFFF;

        if (inEscapeSequence) {
            cancelEscapeTimer();
            escapeBuffer.append(c);
            if (isCompleteEscapeSequence(escapeBuffer)) {
                String sequence = escapeBuffer.toString();
                inEscapeSequence = false;
                escapeBuffer.setLength(0);
                handleCompleteEscape(sequence);
            }
            return;
        }

        if (b == ESC) {
            inEscapeSequence = true;
            escapeBuffer.setLength(0);
            escapeBuffer.append(c);
            scheduleEscapeTimer();
            return;
        }

        if (b == CTRL_C) {
            handleCtrlC();
            return;
        }
        if (b == CTRL_A && !pasting && !pasteSealed) {
            editCursor = 0;
            deferPaint();
            return;
        }
        if (b == CTRL_E && !pasting && !pasteSealed) {
            editCursor = lineBuffer.length();
            deferPaint();
            return;
        }

        if (c == '\r') {
            lastWasCR = true;
            if (pasting) {
                appendPasteNewline();
            } else {
                handleEnter();
            }
            return;
        }
        if (c == '\n') {
            if (!softReturn(c, lastWasCR)) {
                lastWasCR = false;
                return;
            }
            lastWasCR = false;
            if (pasting) {
                appendPasteNewline();
            } else {
                insertSoftReturn();
            }
            return;
        }
        lastWasCR = false;

        if (c == 127 || c == 8) {
            handleBackspace();
            return;
        }
        if (b == CTRL_Z && !pasting && !pasteSealed) {
            undoLine();
            return;
        }

        if (!Character.isISOControl(c)) {
            if (pasting) {
                appendPasteChar(c);
            } else if (pasteSealed) {
                // Blob is atomic until Enter or Ctrl+C / Backspace.
                return;
            } else {
                historyCursor = -1;
                clampEditCursor();
                lineBuffer.insert(editCursor, c);
                editCursor++;
                deferPaint();
            }
        }
    }

    /**
     * The {@code onEscapeTimeout} method treats a lone escape as a mode toggle.
     * If the escape sequence is no longer open, the method returns. The buffer is cleared. A buffer that is still only the escape byte, and not a paste, calls {@link #handleBareEscape}. A write failure is ignored.
     */
    private void onEscapeTimeout() {
        synchronized (this) {
            if (!inEscapeSequence) {
                return;
            }
            boolean bare = escapeBuffer.length() == 1 && escapeBuffer.charAt(0) == ESC;
            inEscapeSequence = false;
            escapeBuffer.setLength(0);
            if (bare && !pasting) {
                try {
                    handleBareEscape();
                } catch (IOException ignored) {
                    // write failed; session is likely dying
                }
            }
        }
    }

    /**
     * The {@code handleBareEscape} method discards the unfinished line and toggles command mode.
     * A paste returns. The line, the cursor, and both undo memories are cleared. Command mode returns to chat when the session is active, and to idle when it is not. Any other mode becomes command mode. The prompt is written.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void handleBareEscape() throws IOException {
        if (pasting) {
            return;
        }
        flushPaint();
        pasteSealed = false;
        pasteStartIndex = 0;
        lineBuffer.setLength(0);
        editCursor = 0;
        dropLineSeats();
        breakBelowPage();
        if (mode == ShellMode.COMMAND) {
            mode = session.isActive() ? ShellMode.CHAT : ShellMode.IDLE;
        } else {
            mode = ShellMode.COMMAND;
        }
        writePrompt();
    }

    /**
     * The {@code handleEnter} method submits the line buffer for the current mode.
     * A paste does not persist history. A page with a newline is trimmed only at the edges. Any other line is trimmed. An edit collection goes to {@link #takeEditLine}. A non-empty ordinary line is remembered.
     * Command mode calls {@link #handleCommandLine}. Idle mode writes {@code Chat is idle. Type 'chat start' or press Escape for commands.} An empty chat line writes the prompt. Any other chat line calls {@link #handleChatLine}.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void handleEnter() throws IOException {
        boolean persistHistory = !pasteSealed && !pasting;
        flushPaint();
        String raw = lineBuffer.toString();
        lineBuffer.setLength(0);
        editCursor = 0;
        pasteSealed = false;
        pasteStartIndex = 0;
        historyCursor = -1;
        historyDraft = "";
        dropLineSeats();
        String line = InputPage.pageOpen(raw) ? stripEdgeNewlines(raw) : raw.trim();
        breakBelowPage();
        if (collectingEdit) {
            takeEditLine(line);
            return;
        }
        if (persistHistory && line.length() > 0) {
            remember(line);
        }
        if (mode == ShellMode.COMMAND) {
            handleCommandLine(line);
            return;
        }
        if (mode == ShellMode.IDLE) {
            write("Chat is idle. Type 'chat start' or press Escape for commands.\r\n");
            writePrompt();
            return;
        }
        if (line.length() == 0) {
            writePrompt();
            return;
        }
        handleChatLine(line);
    }

    /**
     * The {@code handleCommandLine} method runs one command line.
     * An empty line writes the prompt and stays in command mode. An unknown command writes {@code Unknown command:} in red. A thrown exception is written by {@link #formatError}. If the shell is exiting, the exit callback receives status {@code 0}.
     * The prompt stays {@code cmd>} except for {@code chat start} and {@code exit}. {@code exit} leaves through the exit path. {@code chat start} returns to chat or idle.
     * // @aa-sdd G-24 STICKY-CMD aetheris-stages-1-3.finalize.md
     *
     * @param line The line is the trimmed command text.
     * @throws IOException The method throws this exception when a write fails.
     */
    private void handleCommandLine(String line) throws IOException {
        if (line.length() == 0) {
            lastDrawnRows = 1;
            writePrompt();
            return;
        }
        try {
            boolean matched = processor.execute(this, line);
            if (!matched) {
                writeError("Unknown command: " + line);
            }
        } catch (Exception ex) {
            writeError(formatError(ex));
        }
        if (exiting) {
            if (callback != null) {
                callback.onExit(0);
            }
            return;
        }
        // Clear, chat start, and the other commands leave the Roux count current.
        // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-163
        publishSessionTokens();
        if (leavesCommandMode(line)) {
            returnToChatOrIdle();
        } else {
            mode = ShellMode.COMMAND;
            lastDrawnRows = 1;
            writePrompt();
        }
    }

    /**
     * The {@code leavesCommandMode} method reports whether the command returns to chat.
     * The normalized line {@code chat start}, or a line that starts with {@code chat start }, returns true. Every other line returns false and stays in command mode.
     * // @aa-sdd STICKY-CMD aetheris-stages-1-3.finalize.md
     *
     * @param line The line is the trimmed command.
     * @return The return value is true when command mode should be left.
     */
    private boolean leavesCommandMode(String line) {
        String normalized = CommandProcessor.normalize(line);
        if (normalized.equals("chat start") || normalized.startsWith("chat start ")) {
            return true;
        }
        return false;
    }

    /**
     * The {@code returnToChatOrIdle} method leaves command mode.
     * An active session becomes chat. Any other session becomes idle. The prompt is written.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void returnToChatOrIdle() throws IOException {
        mode = session.isActive() ? ShellMode.CHAT : ShellMode.IDLE;
        writePrompt();
    }

    /**
     * The {@code handleChatLine} method queues one model turn and leaves the SSH loop free.
     * A turn that is still running writes {@code busy — wait or Ctrl+C} and the prompt. Otherwise the spinner starts, the cancel flag is cleared, and a task named {@code casserole-llm} is submitted on {@link Lane#LLM_EXEC}.
     *
     * @param line The line is the user text.
     * @throws IOException The method throws this exception when a write fails.
     */
    private void handleChatLine(final String line) throws IOException {
        LaminaHandle existing = inFlight.get();
        if (existing != null && !existing.isDone()) {
            write("busy — wait or Ctrl+C\r\n");
            writePrompt();
            return;
        }
        startSpinner();
        turnCancelled.set(false);
        final int maxMessages = properties.getChat().getMaxMessages();
        final int maxTokens = properties.getChat().getMaxTokens();
        final int managedTokens = properties.getChat().getManagedTokens();
        final int headCount = properties.getChat().getHeadCount();
        /**
         * The task runs one model turn off the SSH read loop.
         */
        LaminaTask llmTask = new LaminaTask(Lane.LLM_EXEC, "casserole-llm", new Runnable() {
            // @aa-sdd GK-1027 aetheris-stage-2-finessing.001.md FINESSE:f-222
            /**
             * The {@code run} method calls {@link #runLlmTurn} with the captured limits.
             */
            @Override
            public void run() {
                runLlmTurn(line, maxMessages, maxTokens, managedTokens, headCount);
            }
        });
        inFlight.set(lamina.submit(llmTask));
    }

    /**
     * The {@code runLlmTurn} method builds the prompt, calls {@link AmwpFacade#completeTurn}, and writes the reply.
     * An exit or a cancel returns before the call and again before the write. A head over the allowance writes {@code The head is over the allowance.} Ephemeral is always false. The pool boot is this shell's boot.
     * Grok fills the target as {@code grok/} plus the id, plus stream, effort, think, burner cloud, temperature, and max tokens. Pool uses target {@code pool} and sets think to {@code true} only when the pool think string is {@code true}. Any other family uses {@code ollama} or {@code ollama/} plus the model id, and copies the console think string.
     * A user name that fails sanitizing throws an exception with the message {@code invalid username}. A null or blank reply throws {@code The engine returned an empty reply.} Success appends the user line and the assistant text, checkpoints, writes the reply header, and writes the prompt. A failure writes the formatted error and the prompt. The in-flight handle is cleared.
     *
     * @param line The line is the user text.
     * @param maxMessages The limit is the history cap for the prompt.
     * @param maxTokens The limit is the token cap for the prompt.
     * @param managedTokens The limit is the managed token cap for the prompt.
     * @param headCount The count is the head size for the prompt.
     */
    private void runLlmTurn(String line, int maxMessages, int maxTokens, int managedTokens, int headCount) {
        AmwpToon.ExecuteBody req = new AmwpToon.ExecuteBody();
        String provider = "ollama";
        try {
            if (exiting || turnCancelled.get()) {
                return;
            }
            Prompt prompt = session.buildPrompt(maxMessages, maxTokens, managedTokens, headCount, line);
            if (session.headOverAllowance()) {
                write("The head is over the allowance.\r\n");
            }
            List<Message> messages = new ArrayList<Message>();
            if (prompt.getInstructions() != null) {
                messages.addAll(prompt.getInstructions());
            }
            String user;
            synchronized (this) {
                provider = consoleProvider == null || consoleProvider.length() == 0 ? "ollama" : consoleProvider;
                turnProvider = provider;
                req.ephemeral = false;
                req.poolBoot = consolePoolBoot;
                if ("grok".equals(provider)) {
                    ensureGrokModel();
                    String id = consoleGrokModel == null ? "" : consoleGrokModel.trim();
                    req.executionTarget = "grok/" + id;
                    req.thinkStream = consoleStream;
                    req.reasoningEffort = consoleEffort == null ? "" : consoleEffort;
                    req.sendReasoningEffort = consoleSendEffort;
                    seatConsoleGrokThink(req, consoleGrokThink);
                    req.burnerCloud = consoleBurner;
                    req.temperaturePresent = consoleTemperaturePresent;
                    req.temperature = consoleTemperature;
                    req.maxCompletionPresent = consoleMaxPresent;
                    req.maxCompletionTokens = consoleMaxTokens;
                } else if ("pool".equals(provider)) {
                    req.executionTarget = "pool";
                    if ("true".equals(consolePoolThink)) {
                        req.think = "true";
                    }
                } else {
                    String id = modelName == null ? "" : modelName.trim();
                    req.executionTarget = id.length() == 0 ? "ollama" : "ollama/" + id;
                    req.think = consoleThink == null ? "" : consoleThink;
                }
                user = sshUsername == null ? "" : sshUsername;
            }
            String safeUser;
            try {
                safeUser = RegionOwner.sanitize(user);
            } catch (IllegalArgumentException ex) {
                throw new Exception("invalid username");
            }
            String text = amwpFacade.completeTurn(safeUser, req, messages);
            rememberEffort(provider, req);
            if (turnCancelled.get() || exiting) {
                return;
            }
            if (text == null || text.trim().length() == 0) {
                throw new Exception("The engine returned an empty reply.");
            }
            synchronized (this) {
                if (turnCancelled.get() || exiting) {
                    return;
                }
                stopSpinner();
                session.appendUser(line);
                session.appendAssistant(text);
                session.checkpoint();
                // The stored turn is in Roux. The heatbar hears that count.
                // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-163
                publishSessionTokens();
                write(Ansi.replyHeader(LocalDateTime.now().format(REPLY_STAMP), consoleTargetLabel(), terminalColumns));
                write(colorReply(formatRouter.format(text, replyContext())));
                writePrompt();
            }
        } catch (Exception ex) {
            rememberEffort(provider, req);
            if (turnCancelled.get() || exiting) {
                return;
            }
            synchronized (this) {
                if (turnCancelled.get() || exiting) {
                    return;
                }
                try {
                    stopSpinner();
                    writeError(formatError(ex));
                    writePrompt();
                } catch (IOException ignored) {
                    // ignore
                }
            }
        } finally {
            inFlight.set(null);
        }
    }

    /**
     * The {@code rememberEffort} method stores a Grok effort switch that the turn turned off.
     * A provider other than {@code grok}, a null body, or a body that still sends effort returns. Otherwise effort sending is stored false and the console model file is written.
     *
     * @param provider The provider is the family of this turn.
     * @param req The request is the body the facade may have changed.
     */
    private void rememberEffort(String provider, AmwpToon.ExecuteBody req) {
        if (!"grok".equals(provider) || req == null || req.sendReasoningEffort) {
            return;
        }
        synchronized (this) {
            consoleSendEffort = false;
            saveConsoleModel();
        }
    }

    /**
     * The {@code handleCtrlC} method cancels an in-flight turn or clears the current line.
     * A paste is discarded, {@code ^C} is written in red, and the prompt returns. A running turn sets the cancel flag. A pool turn calls {@link AmwpFacade#cancelPool}. A refused user name is ignored. The turn is cancelled, the line is cleared, the spinner stops, and {@code ^C} is written. An idle line writes {@code ^C} and the prompt.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void handleCtrlC() throws IOException {
        flushPaint();
        if (pasting || pasteSealed) {
            pasting = false;
            pasteSealed = false;
            pasteStartIndex = 0;
            lineBuffer.setLength(0);
            editCursor = 0;
            breakBelowPage();
            writeError("^C");
            writePrompt();
            return;
        }
        LaminaHandle future = inFlight.get();
        if (future != null && !future.isDone()) {
            turnCancelled.set(true);
            String family;
            String user;
            synchronized (this) {
                family = turnProvider;
                user = sshUsername == null ? "" : sshUsername;
            }
            if ("pool".equals(family) && amwpFacade != null) {
                try {
                    amwpFacade.cancelPool(RegionOwner.sanitize(user));
                } catch (IllegalArgumentException ignored) {
                    // a refused name has no pool child
                }
            }
            future.cancel(true);
            inFlight.set(null);
            lineBuffer.setLength(0);
            stopSpinner();
            writeError("^C");
            writePrompt();
            return;
        }
        lineBuffer.setLength(0);
        editCursor = 0;
        breakBelowPage();
        write("^C\r\n");
        writePrompt();
    }

    /**
     * The {@code handleBackspace} method erases one character or a sealed paste.
     * A sealed paste is cleared and the prompt is written. An empty line or a cursor at the start returns. Otherwise the character before the cursor is deleted.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void handleBackspace() throws IOException {
        if (pasteSealed) {
            pasteSealed = false;
            pasteStartIndex = 0;
            lineBuffer.setLength(0);
            editCursor = 0;
            breakBelowPage();
            writePrompt();
            return;
        }
        if (lineBuffer.length() == 0 || editCursor <= 0) {
            return;
        }
        clampEditCursor();
        lineBuffer.deleteCharAt(editCursor - 1);
        editCursor--;
        deferPaint();
    }

    /**
     * The {@code handleCompleteEscape} method applies one finished escape sequence.
     * A null sequence returns. Paste start opens a paste. Paste end calls {@link #finishPaste}. The redo sequence calls {@link #redoLine}. Up and down recall history, or move a row when the line is a page. A sealed or open paste ignores the motion keys.
     * Right and left move one character. Ctrl-right and Ctrl-left move one word. Home moves to the start. End moves to the end. Delete removes the character at the cursor.
     *
     * @param sequence The sequence is the complete escape sequence.
     * @throws IOException The method throws this exception when a write fails.
     */
    private void handleCompleteEscape(String sequence) throws IOException {
        if (sequence == null) {
            return;
        }
        if (sequence.equals(PASTE_START) || sequence.endsWith("[200~")) {
            if (!pasting) {
                pasting = true;
                pasteStartIndex = lineBuffer.length();
            }
            return;
        }
        if (sequence.equals(PASTE_END) || sequence.endsWith("[201~")) {
            if (pasting) {
                finishPaste();
            }
            return;
        }
        if (REDO_SEQ.equals(sequence) && !pasting && !pasteSealed) {
            redoLine();
            return;
        }
        if (sequence.equals("\u001B[A") || sequence.equals("\u001B[OA")) {
            historyUp();
            return;
        }
        if (sequence.equals("\u001B[B") || sequence.equals("\u001B[OB")) {
            historyDown();
            return;
        }
        if (pasteSealed || pasting) {
            return;
        }
        if (sequence.equals("\u001B[C") || sequence.equals("\u001B[OC")) {
            if (editCursor < lineBuffer.length()) {
                editCursor++;
                deferPaint();
            }
            return;
        }
        if (sequence.equals("\u001B[D") || sequence.equals("\u001B[OD")) {
            if (editCursor > 0) {
                editCursor--;
                deferPaint();
            }
            return;
        }
        if (sequence.equals("\u001B[1;5C") || sequence.equals("\u001B[5C")) {
            clampEditCursor();
            int before = editCursor;
            moveWordForward();
            if (editCursor != before) {
                deferPaint();
            }
            return;
        }
        if (sequence.equals("\u001B[1;5D") || sequence.equals("\u001B[5D")) {
            clampEditCursor();
            int before = editCursor;
            moveWordBackward();
            if (editCursor != before) {
                deferPaint();
            }
            return;
        }
        if (sequence.equals("\u001B[H") || sequence.equals("\u001B[1~") || sequence.equals("\u001B[7~")) {
            editCursor = 0;
            deferPaint();
            return;
        }
        if (sequence.equals("\u001B[F") || sequence.equals("\u001B[4~") || sequence.equals("\u001B[8~")) {
            editCursor = lineBuffer.length();
            deferPaint();
            return;
        }
        if (sequence.equals("\u001B[3~")) {
            clampEditCursor();
            if (editCursor < lineBuffer.length()) {
                lineBuffer.deleteCharAt(editCursor);
                deferPaint();
            }
        }
    }

    /**
     * The {@code moveWordForward} method moves to the first character after the next word.
     * A word is a letter or a digit. A cursor already at the end stays there. Non-word characters are skipped before the word.
     */
    private void moveWordForward() {
        int end = lineBuffer.length();
        int i = editCursor;
        if (i >= end) {
            return;
        }
        if (!wordChar(lineBuffer.charAt(i))) {
            i++;
            while (i < end && !wordChar(lineBuffer.charAt(i))) {
                i++;
            }
        }
        if (i >= end) {
            editCursor = end;
            return;
        }
        i++;
        while (i < end && wordChar(lineBuffer.charAt(i))) {
            i++;
        }
        editCursor = i;
    }

    /**
     * The {@code moveWordBackward} method moves to the first character of the current word, or the previous word.
     * A word is a letter or a digit. A cursor already at the start stays there.
     */
    private void moveWordBackward() {
        int i = editCursor;
        if (i <= 0) {
            return;
        }
        i--;
        while (i > 0 && !wordChar(lineBuffer.charAt(i))) {
            i--;
        }
        while (i > 0 && wordChar(lineBuffer.charAt(i - 1))) {
            i--;
        }
        editCursor = i;
    }

    /**
     * The {@code wordChar} method reports whether a character belongs to a word.
     * A word character is a letter or a digit.
     *
     * @param c The character is one character of the unsent line.
     * @return The return value is true when the character is a letter or a digit.
     */
    private static boolean wordChar(char c) {
        return Character.isLetterOrDigit(c);
    }

    /**
     * The {@code finishPaste} method ends a bracketed paste.
     * The page paints once and stays editable. {@link #commandPasteIsOneLine} drops trailing newlines when the paste is one line. Nothing here sends Enter. The cursor moves to the end of the buffer.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void finishPaste() throws IOException {
        pasting = false;
        commandPasteIsOneLine();
        pasteSealed = false;
        editCursor = lineBuffer.length();
        deferPaint();
    }

    /**
     * The {@code commandPasteIsOneLine} method drops trailing newlines when this paste has no newline inside it.
     * A page keeps its interior newlines, including a trailing newline. A bad paste start is treated as the beginning of the buffer.
     *
     * @return The return value is true when the paste is one line.
     */
    private boolean commandPasteIsOneLine() {
        int start = pasteStartIndex;
        if (start < 0 || start > lineBuffer.length()) {
            start = 0;
        }
        int end = lineBuffer.length();
        while (end > start) {
            char c = lineBuffer.charAt(end - 1);
            if (c != '\n' && c != '\r') {
                break;
            }
            end--;
        }
        for (int i = start; i < end; i++) {
            char c = lineBuffer.charAt(i);
            if (c == '\n' || c == '\r') {
                return false;
            }
        }
        if (end < lineBuffer.length()) {
            lineBuffer.setLength(end);
        }
        return true;
    }

    /**
     * The {@code stripEdgeNewlines} method keeps interior newlines and drops leading and trailing carriage returns and line feeds.
     *
     * @param raw The raw text is the buffer contents.
     * @return The return value is the text for one chat or command turn.
     */
    private String stripEdgeNewlines(String raw) {
        int begin = 0;
        int end = raw.length();
        while (begin < end) {
            char c = raw.charAt(begin);
            if (c != '\n' && c != '\r') {
                break;
            }
            begin++;
        }
        while (end > begin) {
            char c = raw.charAt(end - 1);
            if (c != '\n' && c != '\r') {
                break;
            }
            end--;
        }
        return raw.substring(begin, end);
    }

    /**
     * The {@code appendPasteChar} method appends one pasted character.
     * If {@link #ensurePasteCapacity} refuses the character, the method returns.
     *
     * @param c The character is one printable character.
     * @throws IOException The method throws this exception when a write fails.
     */
    private void appendPasteChar(char c) throws IOException {
        if (!ensurePasteCapacity(1)) {
            return;
        }
        lineBuffer.append(c);
    }

    /**
     * The {@code softReturn} method reports whether this byte inserts a newline and does not submit.
     * A lone line feed keeps the line open. Carriage return still submits. A line feed after carriage return stays swallowed.
     *
     * @param c The character is the byte.
     * @param afterCr The flag is true when the previous byte was carriage return.
     * @return The return value is true when this byte inserts a newline and does not submit.
     */
    static boolean softReturn(char c, boolean afterCr) {
        return c == '\n' && !afterCr;
    }

    /**
     * The {@code insertSoftReturn} method inserts a newline for Shift-Enter.
     * A sealed paste returns. The newline stays in the line. Enter still submits.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void insertSoftReturn() throws IOException {
        if (pasteSealed) {
            return;
        }
        historyCursor = -1;
        clampEditCursor();
        lineBuffer.insert(editCursor, '\n');
        editCursor++;
        deferPaint();
    }

    /**
     * The {@code appendPasteNewline} method records a newline inside a paste as {@code \n}.
     * The turn is not submitted. If the paste cap is exceeded, the method returns.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void appendPasteNewline() throws IOException {
        if (!ensurePasteCapacity(1)) {
            return;
        }
        lineBuffer.append('\n');
    }

    /**
     * The {@code ensurePasteCapacity} method checks the paste cap before more characters are added.
     * A configured cap of zero or less becomes {@code 65536}. Room under the cap returns true. An overflow clears the paste and the line, writes {@code Paste exceeds max-paste-chars}, and returns false.
     *
     * @param extra The count is the number of characters about to be added.
     * @return The return value is false when the paste was aborted.
     * @throws IOException The method throws this exception when a write fails.
     */
    private boolean ensurePasteCapacity(int extra) throws IOException {
        int max = properties.getChat().getMaxPasteChars();
        if (max <= 0) {
            max = 65536;
        }
        if (lineBuffer.length() + extra <= max) {
            return true;
        }
        pasting = false;
        pasteSealed = false;
        pasteStartIndex = 0;
        lineBuffer.setLength(0);
        editCursor = 0;
        breakBelowPage();
        writeError("Paste exceeds max-paste-chars (" + max + ").");
        writePrompt();
        return false;
    }

    /**
     * The {@code isCompleteEscapeSequence} method reports whether an escape buffer is finished.
     * A buffer shorter than two characters returns false. A final letter or {@code ~} returns true. A two-character sequence whose second character is not {@code [} and not {@code O} returns true.
     *
     * @param buf The buffer is the escape buffer.
     * @return The return value is true when the sequence should be handled.
     */
    private boolean isCompleteEscapeSequence(StringBuilder buf) {
        if (buf.length() < 2) {
            return false;
        }
        char last = buf.charAt(buf.length() - 1);
        if (last >= 'A' && last <= 'Z') {
            return true;
        }
        if (last >= 'a' && last <= 'z') {
            return true;
        }
        if (last == '~') {
            return true;
        }
        if (buf.length() == 2 && buf.charAt(1) != '[' && buf.charAt(1) != 'O') {
            return true;
        }
        return false;
    }

    /**
     * The {@code scheduleEscapeTimer} method starts or restarts the bare-escape timer.
     * A configured timeout below 20 milliseconds becomes 100 milliseconds. The timer calls {@link #onEscapeTimeout}.
     */
    private void scheduleEscapeTimer() {
        cancelEscapeTimer();
        int timeout = properties.getChat().getEscapeTimeoutMs();
        if (timeout < 20) {
            timeout = 100;
        }
        /**
         * The task treats a lone escape as a mode toggle when the timer fires.
         */
        pendingEscapeTask = new TimerTask() {
            // @aa-sdd L40 aa-sdd-source-prose.md#ChatShell
            /**
             * The {@code run} method calls {@link #onEscapeTimeout}.
             */
            @Override
            public void run() {
                onEscapeTimeout();
            }
        };
        escapeTimer.schedule(pendingEscapeTask, timeout);
    }

    /**
     * The {@code cancelEscapeTimer} method cancels a pending bare-escape timeout.
     * A missing task returns. The stored task is cleared.
     */
    private void cancelEscapeTimer() {
        if (pendingEscapeTask != null) {
            pendingEscapeTask.cancel();
            pendingEscapeTask = null;
        }
    }

    /**
     * The {@code startSpinner} method starts the thinking animation on the current line.
     * Style {@code none}, or a style with no frames, returns. An interval below 40 milliseconds becomes 40. An interval above 500 milliseconds becomes 500. The first frame is due immediately.
     */
    private void startSpinner() {
        SpinnerStyle style = SpinnerStyle.fromProperty(properties.getChat().getSpinner());
        if (style == SpinnerStyle.NONE || style.getFrames().length == 0) {
            return;
        }
        stopSpinner();
        spinnerRunning = true;
        spinnerFrame = 0;
        spinnerStartedAtMs = System.currentTimeMillis();
        int interval = properties.getChat().getSpinnerMs();
        if (interval < 40) {
            interval = 40;
        }
        if (interval > 500) {
            interval = 500;
        }
        /**
         * The task paints the next thinking frame on each timer tick.
         */
        spinnerTask = new TimerTask() {
            // @aa-sdd L40 aa-sdd-source-prose.md#ChatShell
            /**
             * The {@code run} method calls {@link #paintSpinnerFrame}.
             */
            @Override
            public void run() {
                paintSpinnerFrame();
            }
        };
        spinnerTimer.schedule(spinnerTask, 0L, interval);
    }

    /**
     * The {@code stopSpinner} method stops the timer and erases the spinner line.
     * A call while the spinner is idle does not erase the line. An exiting shell does not erase the line.
     */
    private void stopSpinner() {
        boolean wasRunning = spinnerRunning;
        spinnerRunning = false;
        if (spinnerTask != null) {
            spinnerTask.cancel();
            spinnerTask = null;
        }
        if (wasRunning && !exiting) {
            write(Ansi.CR + Ansi.EL);
        }
    }

    /**
     * The {@code paintSpinnerFrame} method overwrites the current line with the next frame.
     * A stopped spinner or an exiting shell returns. An empty frame list returns. The frame is emerald. The word {@code thinking} and the elapsed clock are electric blue. The rest of the line is erased.
     */
    private void paintSpinnerFrame() {
        synchronized (this) {
            if (!spinnerRunning || exiting) {
                return;
            }
            SpinnerStyle style = SpinnerStyle.fromProperty(properties.getChat().getSpinner());
            String[] frames = style.getFrames();
            if (frames == null || frames.length == 0) {
                return;
            }
            String frame = frames[spinnerFrame % frames.length];
            spinnerFrame++;
            write(Ansi.CR + Ansi.EMERALD + frame + " " + Ansi.ELECTRIC_BLUE + "thinking "
                    + ThinkingClock.format(spinnerStartedAtMs) + Ansi.RESET + Ansi.EL);
        }
    }

    /**
     * The {@code writePrompt} method writes the prompt for the current mode and resets the painted page.
     * The painted text is cleared. The page is measured as empty. The prompt is marked live.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void writePrompt() throws IOException {
        write(promptText());
        paintPending = false;
        promptLive = true;
        paintedText = "";
        paintedCursor = 0;
        InputPage page = InputPage.measure("", visiblePromptLength(), wrapColumns());
        lastDrawnRows = page.rows;
        lastCursorRow = page.cells[0].row;
        lastCursorCol = page.cells[0].col;
    }

    /**
     * The {@code promptText} method returns the prompt for the current mode.
     * Command mode returns blue {@code cmd> }. Idle mode returns {@code idle> }. Chat mode returns green {@code chat> }.
     *
     * @return The return value is the prompt text.
     */
    private String promptText() {
        if (mode == ShellMode.COMMAND) {
            return Ansi.mediumBlue("cmd> ");
        }
        if (mode == ShellMode.IDLE) {
            return "idle> ";
        }
        return Ansi.green("chat> ");
    }

    /**
     * The {@code visiblePromptLength} method returns the visible columns of the prompt.
     * Color codes do not count. Command mode is 5 columns. Idle mode and chat mode are 6 columns.
     *
     * @return The return value is the column count.
     */
    private int visiblePromptLength() {
        if (mode == ShellMode.COMMAND) {
            return 5;
        }
        if (mode == ShellMode.IDLE) {
            return 6;
        }
        return 6;
    }

    /**
     * The {@code afterEdit} method records a text change on the draft undo memory or the visit undo memory.
     * A paste still in progress waits for the closing bracket. A history move that returns to the draft copies the visit memory onto the draft and notes the line. A history line notes the visit memory. An ordinary line notes the draft memory.
     *
     * @param historyBefore The cursor is the history index before this byte.
     */
    private void afterEdit(int historyBefore) {
        if (pasting) {
            return;
        }
        String now = lineBuffer.toString();
        if (historyBefore >= 0 && historyCursor < 0) {
            draftSeat = visitSeat;
            visitSeat = new EditMemory();
            draftSeat.note(now, editCursor);
        } else if (historyCursor >= 0) {
            visitSeat.note(now, editCursor);
        } else {
            draftSeat.note(now, editCursor);
        }
        draftSeat.endApply();
        visitSeat.endApply();
    }

    /**
     * The {@code dropLineSeats} method clears both undo memories.
     * Enter and a bare escape drop the unsent line. The undo stack goes with it. Both memories are synced to an empty line at cursor zero.
     */
    private void dropLineSeats() {
        draftSeat.discard();
        draftSeat.sync("", 0);
        visitSeat.discard();
        visitSeat.sync("", 0);
    }

    /**
     * The {@code undoLine} method restores the previous unsent text.
     * A history line uses the visit memory. Any other line uses the draft memory. If undo has no earlier text, the method returns. The screen paints the restored line.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void undoLine() throws IOException {
        EditMemory seat = historyCursor >= 0 ? visitSeat : draftSeat;
        if (!seat.undo()) {
            return;
        }
        lineBuffer.setLength(0);
        lineBuffer.append(seat.text());
        editCursor = seat.cursor();
        clampEditCursor();
        deferPaint();
    }

    /**
     * The {@code redoLine} method restores the text that undo removed.
     * A history line uses the visit memory. Any other line uses the draft memory. If redo has no later text, the method returns.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void redoLine() throws IOException {
        EditMemory seat = historyCursor >= 0 ? visitSeat : draftSeat;
        if (!seat.redo()) {
            return;
        }
        lineBuffer.setLength(0);
        lineBuffer.append(seat.text());
        editCursor = seat.cursor();
        clampEditCursor();
        deferPaint();
    }

    /**
     * The {@code historyUp} method recalls an older history line.
     * An open page moves one row up instead. An empty history returns. The first press saves the draft and selects the newest history line. A later press moves one line older. The visit undo memory is reset to that line.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void historyUp() throws IOException {
        if (InputPage.pageOpen(lineBuffer)) {
            moveRow(-1);
            return;
        }
        if (history.isEmpty()) {
            return;
        }
        if (historyCursor < 0) {
            historyDraft = lineBuffer.toString();
            historyCursor = history.size() - 1;
        } else if (historyCursor > 0) {
            historyCursor--;
        }
        lineBuffer.setLength(0);
        lineBuffer.append(history.get(historyCursor));
        editCursor = lineBuffer.length();
        visitSeat.discard();
        visitSeat.sync(lineBuffer.toString(), editCursor);
        deferPaint();
    }

    /**
     * The {@code historyDown} method recalls a newer history line, or restores the draft.
     * An open page moves one row down instead. A cursor that is not in history returns. The last step restores the saved draft and clears the visit memory.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void historyDown() throws IOException {
        if (InputPage.pageOpen(lineBuffer)) {
            moveRow(1);
            return;
        }
        if (historyCursor < 0) {
            return;
        }
        if (historyCursor < history.size() - 1) {
            historyCursor++;
            lineBuffer.setLength(0);
            lineBuffer.append(history.get(historyCursor));
            editCursor = lineBuffer.length();
            visitSeat.discard();
            visitSeat.sync(lineBuffer.toString(), editCursor);
        } else {
            historyCursor = -1;
            lineBuffer.setLength(0);
            lineBuffer.append(historyDraft);
            editCursor = lineBuffer.length();
            draftSeat.sync(lineBuffer.toString(), editCursor);
            visitSeat.discard();
            visitSeat.sync("", 0);
        }
        deferPaint();
    }

    /**
     * The {@code deferPaint} method marks this read so it paints once.
     */
    private void deferPaint() {
        paintPending = true;
    }

    /**
     * The {@code flushPaint} method paints the edit that this read accumulated.
     * A read with no pending paint returns. A prompt that is not live uses {@link #paintFull}. A live prompt uses {@link #paintDiff}.
     */
    private void flushPaint() {
        if (!paintPending) {
            return;
        }
        paintPending = false;
        if (!promptLive) {
            paintFull();
            return;
        }
        paintDiff();
    }

    /**
     * The {@code paintDiff} method patches the cells that changed.
     * Erase-to-end of the screen stays off this path. A change in the prompt cell's row or column uses {@link #paintFull}. Otherwise {@link PagePaint#update} writes the difference. Command mode paints characters in green.
     * // @aa-sdd G-22 aetheris-stages-1-3.finalize.md
     */
    private void paintDiff() {
        clampEditCursor();
        int cols = wrapColumns();
        int promptVis = visiblePromptLength();
        InputPage before = InputPage.measure(paintedText, promptVis, cols);
        InputPage after = InputPage.measure(lineBuffer, promptVis, cols);
        if (before.cells[0].row != after.cells[0].row || before.cells[0].col != after.cells[0].col) {
            paintFull();
            return;
        }
        PagePaint.Patch patch = PagePaint.update(
                paintedText,
                paintedCursor,
                lastCursorRow,
                lastCursorCol,
                lineBuffer.toString(),
                editCursor,
                promptVis,
                cols,
                mode == ShellMode.COMMAND);
        if (patch.ansi.length() > 0) {
            write(patch.ansi);
        }
        rememberPaint(patch.rows, patch.cursorRow, patch.cursorCol);
    }

    /**
     * The {@code paintFull} method replaces the whole page in one flush.
     * A resize uses this path. A character edit does not. The old page is erased to the end of the screen, the prompt is written, and the line is drawn with row breaks. Command mode wraps each character in green. The cursor is then moved to its cell.
     * // @aa-sdd G-22 aetheris-stages-1-3.finalize.md
     */
    private void paintFull() {
        clampEditCursor();
        int cols = wrapColumns();
        int promptVis = visiblePromptLength();
        InputPage page = InputPage.measure(lineBuffer, promptVis, cols);
        StringBuilder sb = new StringBuilder();
        int origin = lastDrawnRows < 1 ? 1 : lastDrawnRows;
        sb.append(Ansi.CR);
        if (origin > 1) {
            sb.append(Ansi.cuu(origin - 1));
        }
        sb.append(Ansi.ED);
        sb.append(promptText());
        for (int i = 0; i < page.cells[0].row; i++) {
            sb.append("\r\n");
        }
        boolean commandGreen = mode == ShellMode.COMMAND;
        for (int i = 0; i < lineBuffer.length(); i++) {
            char c = lineBuffer.charAt(i);
            if (c != '\n' && c != '\r') {
                if (commandGreen) {
                    sb.append(Ansi.GREEN);
                }
                sb.append(c);
                if (commandGreen) {
                    sb.append(Ansi.RESET);
                }
            }
            if (page.breakAfter[i]) {
                sb.append("\r\n");
            }
        }
        InputPage.Cell cursor = page.cells[editCursor];
        sb.append(Ansi.CR);
        if (page.rows > 1) {
            sb.append(Ansi.cuu(page.rows - 1));
        }
        if (cursor.row > 0) {
            sb.append(Ansi.cud(cursor.row));
        }
        if (cursor.col > 0) {
            sb.append(Ansi.cuf(cursor.col));
        }
        write(sb.toString());
        promptLive = true;
        rememberPaint(page.rows, cursor.row, cursor.col);
    }

    /**
     * The {@code rememberPaint} method stores the page that was just written.
     *
     * @param rows The count is the page rows now on screen.
     * @param cursorRow The row is the cursor row inside the page.
     * @param cursorCol The column is the cursor column inside the page.
     */
    private void rememberPaint(int rows, int cursorRow, int cursorCol) {
        paintedText = lineBuffer.toString();
        paintedCursor = editCursor;
        lastDrawnRows = rows;
        lastCursorRow = cursorRow;
        lastCursorCol = cursorCol;
    }

    /**
     * The {@code breakBelowPage} method leaves the drawn page on screen and continues on the next row.
     * The cursor moves down to the last drawn row when it is above that row. The prompt is no longer live.
     *
     * @throws IOException The method throws this exception when a write fails.
     */
    private void breakBelowPage() throws IOException {
        int rows = lastDrawnRows < 1 ? 1 : lastDrawnRows;
        int row = lastCursorRow;
        if (row < 0) {
            row = 0;
        }
        if (row >= rows) {
            row = rows - 1;
        }
        int down = rows - 1 - row;
        if (down > 0) {
            write(Ansi.cud(down));
        }
        write("\r\n");
        promptLive = false;
        paintPending = false;
        paintedText = "";
        paintedCursor = 0;
        lastDrawnRows = 1;
        lastCursorRow = 0;
        lastCursorCol = 0;
    }

    /**
     * The {@code moveRow} method moves the cursor one visual row.
     * The first row and the last row hold. A cursor that does not move does not paint.
     *
     * @param delta The delta is {@code -1} or {@code 1}.
     * @throws IOException The method throws this exception when a write fails.
     */
    private void moveRow(int delta) throws IOException {
        clampEditCursor();
        InputPage page = InputPage.measure(lineBuffer, visiblePromptLength(), wrapColumns());
        int next = InputPage.moveRow(page, editCursor, delta);
        if (next == editCursor) {
            return;
        }
        editCursor = next;
        deferPaint();
    }

    /**
     * The {@code wrapColumns} method returns the wrap width.
     * A stored width below 20 returns 80. Any other stored width is returned as it is.
     *
     * @return The return value is the wrap width.
     */
    private int wrapColumns() {
        int cols = terminalColumns;
        if (cols < 20) {
            return 80;
        }
        return cols;
    }

    /**
     * The {@code clampEditCursor} method keeps the edit cursor inside the buffer.
     * A negative cursor becomes zero. A cursor past the end becomes the buffer length.
     */
    private void clampEditCursor() {
        if (editCursor < 0) {
            editCursor = 0;
        }
        if (editCursor > lineBuffer.length()) {
            editCursor = lineBuffer.length();
        }
    }

    /**
     * The {@code remember} method appends a history entry and writes the file.
     * A line equal to the last entry is not added again. A configured size below 10 becomes 500. Older entries are dropped until the list fits.
     *
     * @param line The line is the submitted text.
     */
    private void remember(String line) {
        if (history.size() > 0 && line.equals(history.get(history.size() - 1))) {
            return;
        }
        history.add(line);
        int max = properties.getChat().getHistorySize();
        if (max < 10) {
            max = 500;
        }
        while (history.size() > max) {
            history.remove(0);
        }
        saveHistory();
    }

    /**
     * The {@code historyFile} method returns the history file for this user.
     * An empty user name uses the directory {@code anon}. The path is {@code ~/.casserole/} plus that name plus {@code history}.
     *
     * @return The return value is the history file.
     */
    private File historyFile() {
        String user = sshUsername == null || sshUsername.length() == 0 ? "anon" : sshUsername;
        File dir = new File(new File(System.getProperty("user.home"), ".casserole"), user);
        return new File(dir, "history");
    }

    /**
     * The {@code loadHistory} method loads the history file into memory.
     * A missing file returns. A blank line is skipped. Each stored line is unescaped. A read error is ignored.
     */
    private void loadHistory() {
        File file = historyFile();
        if (!file.isFile()) {
            return;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    history.add(unescapeHistory(line));
                }
            }
        } catch (Exception ignored) {
            // missing or unreadable history is not fatal
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    // ignore
                }
            }
        }
    }

    /**
     * The {@code saveHistory} method writes the in-memory history file.
     * The parent directory is created when it is missing. Each line is escaped and followed by a newline. A write error is ignored.
     */
    private void saveHistory() {
        File file = historyFile();
        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            for (int i = 0; i < history.size(); i++) {
                writer.write(escapeHistory(history.get(i)));
                writer.write("\n");
            }
        } catch (Exception ignored) {
            // ignore persist errors
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignored) {
                    // ignore
                }
            }
        }
    }

    /**
     * The {@code consoleModelFile} method returns the console-model file for this user.
     * An empty user name uses the directory {@code anon}. The path is {@code ~/.casserole/} plus that name plus {@code console-model}.
     *
     * @return The return value is the console-model file.
     */
    private File consoleModelFile() {
        String user = sshUsername == null || sshUsername.length() == 0 ? "anon" : sshUsername;
        File dir = new File(new File(System.getProperty("user.home"), ".casserole"), user);
        return new File(dir, "console-model");
    }

    /**
     * The {@code loadConsoleModel} method reads version 1 of the console target.
     * A missing file leaves the family at {@code ollama} and the model at the server default. A version other than {@code 1}, or a read error, blocks later writes and leaves the values unread. A file that carries only the family word still reads when that word is {@code ollama}, {@code grok}, or {@code pool}.
     * Temperature that is blank, infinite, or not a number stays unset. A max-token value below 1 or not an integer stays unset. Effort sending changes only for the text {@code true} or {@code false}.
     */
    private void loadConsoleModel() {
        File file = consoleModelFile();
        if (!file.isFile()) {
            return;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String version = "";
            boolean sawProvider = false;
            String provider = "";
            String ollama = null;
            String think = null;
            String grokModel = null;
            String grokThink = null;
            String effort = null;
            String sendEffort = null;
            String stream = null;
            String burner = null;
            String poolThink = null;
            boolean sawTemp = false;
            String temp = "";
            boolean sawMax = false;
            String max = "";
            String line;
            while ((line = reader.readLine()) != null) {
                String row = line.trim();
                if (row.startsWith("version:")) {
                    version = row.substring("version:".length()).trim();
                } else if (row.startsWith("provider:")) {
                    sawProvider = true;
                    provider = row.substring("provider:".length()).trim();
                } else if (row.startsWith("ollama_model:")) {
                    ollama = row.substring("ollama_model:".length()).trim();
                } else if (row.startsWith("grok_model:")) {
                    grokModel = row.substring("grok_model:".length()).trim();
                } else if (row.startsWith("grok_think:")) {
                    grokThink = row.substring("grok_think:".length()).trim();
                } else if (row.startsWith("pool_think:")) {
                    poolThink = row.substring("pool_think:".length()).trim();
                } else if (row.startsWith("send_effort:")) {
                    sendEffort = row.substring("send_effort:".length()).trim();
                } else if (row.startsWith("think:")) {
                    think = row.substring("think:".length()).trim();
                } else if (row.startsWith("effort:")) {
                    effort = row.substring("effort:".length()).trim();
                } else if (row.startsWith("stream:")) {
                    stream = row.substring("stream:".length()).trim();
                } else if (row.startsWith("temperature:")) {
                    sawTemp = true;
                    temp = row.substring("temperature:".length()).trim();
                } else if (row.startsWith("max_tokens:")) {
                    sawMax = true;
                    max = row.substring("max_tokens:".length()).trim();
                } else if (row.startsWith("burner:")) {
                    burner = row.substring("burner:".length()).trim();
                }
            }
            if (!"1".equals(version)) {
                consoleModelBlocked = true;
                return;
            }
            if (sawProvider && ("ollama".equals(provider) || "grok".equals(provider) || "pool".equals(provider))) {
                consoleProvider = provider;
            }
            if (ollama != null && ollama.length() > 0) {
                setModelName(ollama);
            }
            if (think != null) {
                consoleThink = think;
            }
            if (grokModel != null) {
                consoleGrokModel = grokModel;
            }
            if (grokThink != null) {
                consoleGrokThink = grokThink;
            }
            if (effort != null) {
                consoleEffort = effort;
            }
            if ("false".equals(sendEffort)) {
                consoleSendEffort = false;
            } else if ("true".equals(sendEffort)) {
                consoleSendEffort = true;
            }
            if (stream != null) {
                consoleStream = "true".equals(stream);
            }
            if (burner != null) {
                consoleBurner = "true".equals(burner);
            }
            if (poolThink != null) {
                consolePoolThink = poolThink;
            }
            if (sawTemp) {
                if (temp.length() == 0) {
                    consoleTemperaturePresent = false;
                } else {
                    try {
                        double parsed = Double.parseDouble(temp);
                        if (Double.isInfinite(parsed) || Double.isNaN(parsed)) {
                            consoleTemperaturePresent = false;
                        } else {
                            consoleTemperature = parsed;
                            consoleTemperaturePresent = true;
                        }
                    } catch (NumberFormatException ex) {
                        consoleTemperaturePresent = false;
                    }
                }
            }
            if (sawMax) {
                if (max.length() == 0) {
                    consoleMaxPresent = false;
                } else {
                    try {
                        int parsed = Integer.parseInt(max);
                        if (parsed < 1) {
                            consoleMaxPresent = false;
                        } else {
                            consoleMaxTokens = parsed;
                            consoleMaxPresent = true;
                        }
                    } catch (NumberFormatException ex) {
                        consoleMaxPresent = false;
                    }
                }
            }
        } catch (Exception ignored) {
            consoleModelBlocked = true;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    // the model keeps what it already read
                }
            }
        }
    }

    /**
     * The {@code saveConsoleModel} method writes the family word and the chosen model.
     * A blocked file is not overwritten. A family other than {@code ollama}, {@code grok}, or {@code pool} returns. Temperature and max tokens are written only when they are present. A write error leaves the previous file. Callers hold the shell lock.
     */
    private void saveConsoleModel() {
        if (consoleModelBlocked) {
            return;
        }
        String provider = consoleProvider == null ? "" : consoleProvider.trim();
        if (!"ollama".equals(provider) && !"grok".equals(provider) && !"pool".equals(provider)) {
            return;
        }
        File file = consoleModelFile();
        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write("version: 1\n");
            writer.write("provider: " + provider + "\n");
            writer.write("ollama_model: " + oneLine(modelName) + "\n");
            writer.write("think: " + oneLine(consoleThink) + "\n");
            writer.write("grok_model: " + oneLine(consoleGrokModel) + "\n");
            writer.write("grok_think: " + oneLine(consoleGrokThink) + "\n");
            writer.write("effort: " + oneLine(consoleEffort) + "\n");
            writer.write("send_effort: " + (consoleSendEffort ? "true" : "false") + "\n");
            writer.write("stream: " + (consoleStream ? "true" : "false") + "\n");
            if (consoleTemperaturePresent) {
                writer.write("temperature: " + Double.toString(consoleTemperature) + "\n");
            }
            if (consoleMaxPresent) {
                writer.write("max_tokens: " + consoleMaxTokens + "\n");
            }
            writer.write("burner: " + (consoleBurner ? "true" : "false") + "\n");
            writer.write("pool_think: " + oneLine(consolePoolThink) + "\n");
        } catch (Exception ignored) {
            // a missed write leaves the previous model
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignored) {
                    // a missed close still left the bytes already written
                }
            }
        }
    }

    /**
     * The {@code escapeHistory} method makes one history line safe for the file.
     * A backslash becomes two backslashes. A newline becomes {@code \n}. A carriage return is removed.
     *
     * @param line The line is the raw text.
     * @return The return value is the file-safe line.
     */
    private String escapeHistory(String line) {
        return line.replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "");
    }

    /**
     * The {@code unescapeHistory} method restores one history line from the file.
     * {@code \n} becomes a newline. A doubled backslash becomes one backslash. Any other character is copied.
     *
     * @param line The line is the file text.
     * @return The return value is the raw line.
     */
    private String unescapeHistory(String line) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\\' && i + 1 < line.length()) {
                char n = line.charAt(i + 1);
                if (n == 'n') {
                    out.append('\n');
                    i++;
                    continue;
                }
                if (n == '\\') {
                    out.append('\\');
                    i++;
                    continue;
                }
            }
            out.append(c);
        }
        return out.toString();
    }

    /**
     * The {@code listenForWindowChange} method listens for a terminal window change and refreshes the wrap width.
     * A null environment returns. Only {@code WINCH} is handled.
     * // @aa-sdd G-22 naws-on-resize aetheris-stages-1-3.finalize.md
     *
     * @param channel The channel is the SSH channel.
     * @param env The environment is the session environment.
     */
    private void listenForWindowChange(ChannelSession channel, Environment env) {
        if (env == null) {
            return;
        }
        /**
         * The listener refreshes the wrap width when the terminal window changes.
         */
        env.addSignalListener(new SignalListener() {
            // @aa-sdd L40 aa-sdd-source-prose.md#ChatShell
            /**
             * The {@code signal} method applies a new column count on {@code WINCH}.
             * Any other signal returns.
             *
             * @param ch The channel is the SSH channel that raised the signal.
             * @param signal The signal is the terminal signal.
             */
            @Override
            public void signal(org.apache.sshd.common.channel.Channel ch, Signal signal) {
                if (signal != Signal.WINCH) {
                    return;
                }
                applyWindowColumns();
            }
        }, Signal.WINCH);
    }

    /**
     * The {@code applyWindowColumns} method reads the column count again and redraws the input line.
     * An unchanged width returns. The redraw uses {@link #paintFull}.
     * // @aa-sdd G-22 aetheris-stages-1-3.finalize.md
     */
    private void applyWindowColumns() {
        int next = parseColumns(environment);
        if (next == terminalColumns) {
            return;
        }
        terminalColumns = next;
        synchronized (this) {
            paintPending = false;
            paintFull();
        }
    }

    /**
     * The {@code parseColumns} method reads {@code COLUMNS} from the SSH environment.
     * A missing environment, a missing value, or a value that is not an integer returns 80. A value below 20 returns 80. A value of 20 or more returns that value minus 2.
     *
     * @param env The environment is the SSH session environment.
     * @return The return value is the wrap width.
     */
    private int parseColumns(Environment env) {
        if (env == null || env.getEnv() == null) {
            return 80;
        }
        String raw = env.getEnv().get(Environment.ENV_COLUMNS);
        if (raw == null) {
            return 80;
        }
        try {
            int cols = Integer.parseInt(raw.trim());
            if (cols < 20) {
                return 80;
            }
            if (cols > 4) {
                return cols - 2;
            }
            return cols;
        } catch (NumberFormatException ex) {
            return 80;
        }
    }

    /**
     * The {@code replyContext} method builds the format context from settings and the current terminal width.
     * The border color is electric blue. The header color and the cell color are yellow.
     *
     * @return The return value is the format context.
     */
    private FormatContext replyContext() {
        FormatContext ctx = new FormatContext();
        ctx.setColumns(terminalColumns);
        ctx.setTableStyle(properties.getChat().getTableStyle());
        ctx.setSgrBorder(Ansi.ELECTRIC_BLUE);
        ctx.setSgrHeader(Ansi.YELLOW);
        ctx.setSgrCell(Ansi.YELLOW);
        ctx.setSgrReset(Ansi.RESET);
        return ctx;
    }

    /**
     * The {@code colorReply} method wraps a reply in yellow when the renderer did not paint its own colors.
     * A result that already applies body color is returned as yellow text. Any other result is returned unchanged.
     *
     * @param result The result is the formatted output.
     * @return The return value is the text to write.
     */
    private String colorReply(FormatResult result) {
        if (result.isApplyBodyColor()) {
            return Ansi.yellow(result.getText());
        }
        return result.getText();
    }

    /**
     * The {@code formatError} method flattens an exception into a short error line.
     * A null or blank message uses the exception class name. The first stack frame is appended in parentheses when a frame exists.
     *
     * @param ex The exception is the failure.
     * @return The return value is the message plus the first stack frame.
     */
    private String formatError(Exception ex) {
        String message = ex.getMessage();
        if (message == null || message.trim().length() == 0) {
            message = ex.getClass().getSimpleName();
        }
        StackTraceElement[] stack = ex.getStackTrace();
        if (stack != null && stack.length > 0) {
            return message + " (" + stack[0].toString() + ")";
        }
        return message;
    }

    /**
     * The {@code getSession} method returns the chat session for this connection.
     *
     * @return The return value is the chat session.
     */
    @Override
    public ChatSession getSession() {
        return session;
    }

    /**
     * The {@code getShellMode} method returns the current shell mode.
     *
     * @return The return value is chat, idle, or command mode.
     */
    @Override
    public ShellMode getShellMode() {
        return mode;
    }

    /**
     * The {@code getProperties} method returns the server settings.
     *
     * @return The return value is the server settings.
     */
    @Override
    public CasseroleProperties getProperties() {
        return properties;
    }

    /**
     * The {@code getModelName} method returns the Ollama model id.
     *
     * @return The return value is the Ollama model id.
     */
    @Override
    public String getModelName() {
        return modelName;
    }

    /**
     * The {@code getConsoleThink} method returns the console think string.
     * A null string is returned as empty.
     *
     * @return The return value is the think string.
     */
    @Override
    public synchronized String getConsoleThink() {
        return consoleThink == null ? "" : consoleThink;
    }

    /**
     * The {@code getConsoleProvider} method returns the console family.
     * A null or empty family is returned as {@code ollama}.
     *
     * @return The return value is {@code ollama}, {@code grok}, or {@code pool}.
     */
    @Override
    public synchronized String getConsoleProvider() {
        if (consoleProvider == null || consoleProvider.length() == 0) {
            return "ollama";
        }
        return consoleProvider;
    }

    /**
     * The {@code applyConsoleModel} method stores the console model and the think string.
     * The family becomes {@code ollama}. An empty model leaves the current name. An empty think string clears think. A turn already inside the completion keeps the values it already read.
     *
     * @param model The model is the Ollama id.
     * @param think The think text is empty for off, {@code true} for on, or another word for a level.
     */
    public synchronized void applyConsoleModel(String model, String think) {
        this.consoleProvider = "ollama";
        String id = model == null ? "" : model.trim();
        if (id.length() > 0) {
            setModelName(id);
        }
        String level = think == null ? "" : think.trim();
        this.consoleThink = level;
    }

    /**
     * The {@code applyConsoleTarget} method stores one {@code shell_model_set:} body.
     * A missing provider is handled as the Ollama frame. An empty Ollama id does not clear {@link #modelName}. A Grok id is stored separately and never becomes that Ollama name.
     * Effort sending changes only for {@code true} or {@code false}. Stream is true only for the text {@code true}. A blank, infinite, or unparsable temperature stays unset. A max-token value below 1 or not an integer stays unset. The console model file is written.
     *
     * @param body The body is the stripped frame, including the command line.
     */
    public synchronized void applyConsoleTarget(String body) {
        String provider = lineValue(body, "provider:");
        if (provider.length() == 0) {
            applyConsoleModel(lineValue(body, "model:"), lineValue(body, "think:"));
            saveConsoleModel();
            return;
        }
        this.consoleProvider = provider;
        String ollama = lineValue(body, "ollama_model:");
        if (ollama.length() == 0 && "ollama".equals(provider)) {
            ollama = lineValue(body, "model:");
        }
        if (ollama.length() > 0) {
            setModelName(ollama);
        }
        this.consoleThink = lineValue(body, "think:");
        this.consoleGrokModel = lineValue(body, "grok_model:");
        this.consoleGrokThink = lineValue(body, "grok_think:");
        this.consoleEffort = lineValue(body, "effort:");
        String send = lineValue(body, "send_effort:");
        if ("false".equals(send)) {
            this.consoleSendEffort = false;
        } else if ("true".equals(send)) {
            this.consoleSendEffort = true;
        }
        this.consoleStream = "true".equals(lineValue(body, "stream:"));
        String temp = lineValue(body, "temperature:");
        if (temp.length() == 0) {
            this.consoleTemperaturePresent = false;
        } else {
            try {
                double parsed = Double.parseDouble(temp);
                if (Double.isInfinite(parsed) || Double.isNaN(parsed)) {
                    this.consoleTemperaturePresent = false;
                } else {
                    this.consoleTemperature = parsed;
                    this.consoleTemperaturePresent = true;
                }
            } catch (NumberFormatException ex) {
                this.consoleTemperaturePresent = false;
            }
        }
        String max = lineValue(body, "max_tokens:");
        if (max.length() == 0) {
            this.consoleMaxPresent = false;
        } else {
            try {
                int parsed = Integer.parseInt(max);
                if (parsed < 1) {
                    this.consoleMaxPresent = false;
                } else {
                    this.consoleMaxTokens = parsed;
                    this.consoleMaxPresent = true;
                }
            } catch (NumberFormatException ex) {
                this.consoleMaxPresent = false;
            }
        }
        this.consoleBurner = "true".equals(lineValue(body, "burner:"));
        this.consolePoolThink = lineValue(body, "pool_think:");
        saveConsoleModel();
    }

    /**
     * The {@code seatConsoleGrokThink} method copies a Grok think value onto this turn.
     * The text {@code true} sets the request think string to {@code true} and turns the request stream on. The saved stream flag is not changed. A null request, a null string, or any other text leaves the request as it was built.
     *
     * @param req The request is the execute body for this turn.
     * @param grokThink The text is {@code true} when the console Grok think flag is on.
     */
    static void seatConsoleGrokThink(AmwpToon.ExecuteBody req, String grokThink) {
        if (req == null || grokThink == null || !"true".equals(grokThink.trim())) {
            return;
        }
        req.think = "true";
        req.thinkStream = true;
    }

    /**
     * The {@code applyConsoleMood} method stores or clears the console mood line.
     * {@link #consoleMoodLine} reads the text. A result of null is ignored. The screen is not written.
     *
     * @param text The text is the stripped message body.
     */
    public synchronized void applyConsoleMood(String text) {
        String line = consoleMoodLine(text);
        if (line == null) {
            return;
        }
        session.setMoodLine(line);
    }

    /**
     * The {@code sessionTokenReply} method builds the stored-session count reply.
     * A chat with no messages uses 1 token, 0 characters, and 0 blocks. A chat with messages sums the token count, the character count, and the block count. Empty message text counts as empty. The screen is not written.
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-163
     *
     * @return The return value is the {@code session_tokens_result:} body.
     */
    public synchronized String sessionTokenReply() {
        int used = 1;
        int chars = 0;
        int blocks = 0;
        List<Message> messages = session.getMessages();
        if (messages != null && messages.size() > 0) {
            used = 0;
            HeuristicTokenCounter counter = new HeuristicTokenCounter();
            for (int i = 0; i < messages.size(); i++) {
                Message message = messages.get(i);
                String text = "";
                if (message != null && message.getText() != null) {
                    text = message.getText();
                }
                used = used + counter.count(text);
                chars = chars + text.length();
                blocks = blocks + 1;
            }
        }
        return "session_tokens_result:\nused: " + used + "\nchars: " + chars + "\nblocks: " + blocks + "\n";
    }

    /**
     * The {@code publishSessionTokens} method sends the stored-session count on the console path.
     * A null facade or an empty user name returns. A user name that fails sanitizing returns. A failed send does not break the shell. The screen is not written.
     * // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-163
     */
    private void publishSessionTokens() {
        if (amwpFacade == null || sshUsername == null || sshUsername.length() == 0) {
            return;
        }
        String safe;
        try {
            safe = RegionOwner.sanitize(sshUsername);
        } catch (IllegalArgumentException ex) {
            return;
        }
        try {
            amwpFacade.sendConsoleText(safe, sessionTokenReply());
        } catch (RuntimeException ignored) {
            // mux closing
        }
    }

    /**
     * The {@code exportSessionToon} method returns this connection's message table.
     * The screen is not written.
     *
     * @return The return value is the TOON message table.
     */
    public String exportSessionToon() {
        return session.exportToon();
    }

    /**
     * The {@code sessionExportReply} method wraps one message table in a reply.
     * A null or empty table becomes {@code messages[0]{r,ts,tok,x}:} and a newline. A body that does not fit one frame returns {@code export failed: the session does not fit in one frame}.
     *
     * @param toon The table is the message table, or empty.
     * @return The return value is the reply text.
     */
    public static String sessionExportReply(String toon) {
        String body = toon == null || toon.length() == 0 ? "messages[0]{r,ts,tok,x}:\n" : toon;
        String reply = "session_export_result:\n" + body;
        byte[] utf = reply.getBytes(StandardCharsets.UTF_8);
        if (!AmwpCodec.fits(utf)) {
            return "session_export_result:\nexport failed: the session does not fit in one frame\n";
        }
        return reply;
    }

    /**
     * The {@code applySessionReplace} method replaces this connection's turns with the user and assistant rows in the table.
     * A missing command, a table that does not parse, or a role other than {@code user} or {@code assistant} leaves the stored turns as they stand. A failed replace does the same. The screen is not written. The configured prompt stays. The console mood leaves with the turns.
     *
     * @param text The text is the stripped body, the command line plus the table.
     * @return The return value is the reply text.
     */
    public synchronized String applySessionReplace(String text) {
        String table = tableAfterCommand(text, "session_replace:");
        if (table == null) {
            return replaceResult(false, "the table is not a message table");
        }
        String[] flat = ToonCodec.parseMessageTable(table);
        if (flat == null) {
            return replaceResult(false, "the table is not a message table");
        }
        List<String> roles = new ArrayList<String>();
        List<String> texts = new ArrayList<String>();
        for (int i = 0; i < flat.length; i += 2) {
            String role = flat[i];
            if (!"user".equals(role) && !"assistant".equals(role)) {
                return replaceResult(false, "the table is not input and reply rows");
            }
            roles.add(role);
            texts.add(flat[i + 1] == null ? "" : flat[i + 1]);
        }
        if (!session.replaceTurns(roles, texts)) {
            return replaceResult(false, "the table is not input and reply rows");
        }
        // The replaced region is the count the heatbar wears.
        // @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-163
        publishSessionTokens();
        return replaceResult(true, "");
    }

    /**
     * The {@code replaceResult} method builds the reply for one session replace.
     * Success returns {@code session_replace_result:} and {@code ok}. Failure returns {@code replace failed:} plus the reason. A null reason is written as empty.
     *
     * @param ok The flag is true when the stored turns were replaced.
     * @param reason The reason is the short failure. It is unused when {@code ok} is true.
     * @return The return value is the reply text.
     */
    public static String replaceResult(boolean ok, String reason) {
        if (ok) {
            return "session_replace_result:\nok\n";
        }
        String why = reason == null ? "" : reason;
        return "session_replace_result:\nreplace failed: " + why + "\n";
    }

    /**
     * The {@code tableAfterCommand} method returns the text after a command line.
     * A null text, a null command, or an empty text returns null. A trailing carriage return on the first line is removed. A first line that does not start with the command returns null. A body with no newline returns an empty string.
     *
     * @param text The text is the stripped body.
     * @param command The command is the first line, including the colon.
     * @return The return value is the table after that line, or null when the command is absent.
     */
    static String tableAfterCommand(String text, String command) {
        if (text == null || command == null || text.length() == 0) {
            return null;
        }
        int nl = text.indexOf('\n');
        String first = nl < 0 ? text : text.substring(0, nl);
        if (first.endsWith("\r")) {
            first = first.substring(0, first.length() - 1);
        }
        if (!first.startsWith(command)) {
            return null;
        }
        if (nl < 0) {
            return "";
        }
        return text.substring(nl + 1);
    }

    /**
     * The {@code consoleMoodLine} method reads the mood line from a message body.
     * The parse is {@link MoodDesk#parseShell}.
     *
     * @param text The text is the stripped body.
     * @return The return value is the mood line, an empty string when the frame clears it, or null when this is not a mood frame.
     */
    static String consoleMoodLine(String text) {
        return MoodDesk.parseShell(text);
    }

    /**
     * The {@code consoleModelBody} method echoes the console target, one line per field.
     * Temperature and max tokens are omitted when they were absent. A null family is written as {@code ollama}.
     *
     * @return The return value is the result body after {@code shell_model_result:}.
     */
    public synchronized String consoleModelBody() {
        String provider = consoleProvider == null || consoleProvider.length() == 0 ? "ollama" : consoleProvider;
        String ollama = modelName == null ? "" : modelName;
        StringBuilder sb = new StringBuilder();
        sb.append("provider: ").append(oneLine(provider)).append('\n');
        sb.append("model: ").append(oneLine(ollama)).append('\n');
        sb.append("ollama_model: ").append(oneLine(ollama)).append('\n');
        sb.append("think: ").append(oneLine(consoleThink)).append('\n');
        sb.append("grok_model: ").append(oneLine(consoleGrokModel)).append('\n');
        sb.append("grok_think: ").append(oneLine(consoleGrokThink)).append('\n');
        sb.append("effort: ").append(oneLine(consoleEffort)).append('\n');
        sb.append("send_effort: ").append(consoleSendEffort ? "true" : "false").append('\n');
        sb.append("stream: ").append(consoleStream ? "true" : "false").append('\n');
        if (consoleTemperaturePresent) {
            sb.append("temperature: ").append(Double.toString(consoleTemperature)).append('\n');
        }
        if (consoleMaxPresent) {
            sb.append("max_tokens: ").append(consoleMaxTokens).append('\n');
        }
        sb.append("burner: ").append(consoleBurner ? "true" : "false").append('\n');
        sb.append("pool_think: ").append(oneLine(consolePoolThink)).append('\n');
        return sb.toString();
    }

    /**
     * The {@code consoleTargetLabel} method returns the model line for the screen.
     * Pool returns {@code pool}. Grok returns {@code grok/} plus the Grok id. Any other family returns the stored Ollama id. A null Ollama id returns an empty string.
     *
     * @return The return value is the model line.
     */
    public synchronized String consoleTargetLabel() {
        String provider = consoleProvider == null || consoleProvider.length() == 0 ? "ollama" : consoleProvider;
        if ("pool".equals(provider)) {
            return "pool";
        }
        if ("grok".equals(provider)) {
            String id = consoleGrokModel == null ? "" : consoleGrokModel;
            return "grok/" + id;
        }
        return modelName == null ? "" : modelName;
    }

    /**
     * The {@code ensureGrokModel} method fills an empty Grok id from {@link org.aetheris.swing.GrokSettingsDocument#consoleSeed}.
     * A stored id stays. A null seed, or a seed whose model is empty, leaves the id empty. A missing settings row in that seed is factory {@code grok-4.3}. A null effort in the seed is stored as {@code high}. The settings file is not written. A key is not read. Callers hold the shell lock.
     */
    private void ensureGrokModel() {
        String id = consoleGrokModel == null ? "" : consoleGrokModel.trim();
        if (id.length() > 0) {
            return;
        }
        String[] seed = GrokSettingsDocument.consoleSeed();
        if (seed == null || seed.length < 5 || seed[0] == null || seed[0].trim().length() == 0) {
            return;
        }
        consoleGrokModel = seed[0].trim();
        consoleEffort = seed[1] == null ? "high" : seed[1];
        consoleSendEffort = "true".equals(seed[2]);
        consoleStream = "true".equals(seed[3]);
        consoleBurner = "true".equals(seed[4]);
    }

    /**
     * The {@code selectConsoleFamily} method switches the console family to {@code ollama}, {@code grok}, or {@code pool}.
     * The stored options for each family stay. An empty Grok id is filled by {@link #ensureGrokModel}. An unknown word writes nothing and returns false. A stored family writes {@code model} plus the target label and returns true.
     *
     * @param family The family is the family word.
     * @return The return value is true when the family was stored.
     */
    @Override
    public boolean selectConsoleFamily(String family) {
        String word = family == null ? "" : family.trim();
        String label;
        synchronized (this) {
            if (!"ollama".equals(word) && !"grok".equals(word) && !"pool".equals(word)) {
                return false;
            }
            this.consoleProvider = word;
            if ("grok".equals(word)) {
                ensureGrokModel();
            }
            label = consoleTargetLabel();
            saveConsoleModel();
        }
        write("model " + label + "\r\n");
        return true;
    }

    /**
     * The {@code writeModelStatus} method prints the selected family, the target, and that family's stored options.
     * {@link #getModelName()} stays the Ollama id.
     */
    @Override
    public void writeModelStatus() {
        String block;
        synchronized (this) {
            block = modelStatusText();
        }
        write(block);
    }

    /**
     * The {@code modelStatusText} method builds the status block.
     * Grok fills an empty id first. Grok lines are think, effort, send-effort, stream, temperature when present, max tokens when present, and burner. Pool writes think as {@code true} or {@code off}. Any other family writes the console think word. Each line ends with carriage return and line feed.
     *
     * @return The return value is the status block, including carriage return and line feed.
     */
    private String modelStatusText() {
        String provider = getConsoleProvider();
        if ("grok".equals(provider)) {
            ensureGrokModel();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Ansi.kv("family", provider)).append("\r\n");
        sb.append(Ansi.kv("model", consoleTargetLabel())).append("\r\n");
        if ("grok".equals(provider)) {
            sb.append(Ansi.kv("think", wordOrOff(consoleGrokThink))).append("\r\n");
            String effort = consoleEffort == null ? "" : oneLine(consoleEffort);
            sb.append(Ansi.kv("effort", effort)).append("\r\n");
            sb.append(Ansi.kv("send-effort", consoleSendEffort ? "true" : "false")).append("\r\n");
            sb.append(Ansi.kv("stream", consoleStream ? "true" : "false")).append("\r\n");
            if (consoleTemperaturePresent) {
                sb.append(Ansi.kv("temperature", Double.toString(consoleTemperature))).append("\r\n");
            }
            if (consoleMaxPresent) {
                sb.append(Ansi.kv("max-tokens", Integer.toString(consoleMaxTokens))).append("\r\n");
            }
            sb.append(Ansi.kv("burner", consoleBurner ? "true" : "false")).append("\r\n");
        } else if ("pool".equals(provider)) {
            String think = "true".equals(consolePoolThink) ? "true" : "off";
            sb.append(Ansi.kv("think", think)).append("\r\n");
        } else {
            sb.append(Ansi.kv("think", wordOrOff(consoleThink))).append("\r\n");
        }
        return sb.toString();
    }

    /**
     * The {@code wordOrOff} method returns a stored word, or {@code off} when it is empty.
     *
     * @param raw The raw text is the stored word.
     * @return The return value is the word, or {@code off} when it is empty.
     */
    private static String wordOrOff(String raw) {
        String word = oneLine(raw);
        if (word.length() == 0) {
            return "off";
        }
        return word;
    }

    /**
     * The {@code lineValue} method reads one prefixed line from a body.
     * A null body or a null key returns empty. A trailing carriage return on a line is removed. The value is the text after the key, with carriage returns and line feeds removed. A missing line returns empty.
     *
     * @param text The text is the body.
     * @param key The key is the line prefix, including the colon.
     * @return The return value is the value, or an empty string when the line is missing.
     */
    private static String lineValue(String text, String key) {
        if (text == null || key == null) {
            return "";
        }
        int start = 0;
        while (start <= text.length()) {
            int nl = text.indexOf('\n', start);
            String line = nl < 0 ? text.substring(start) : text.substring(start, nl);
            if (line.endsWith("\r")) {
                line = line.substring(0, line.length() - 1);
            }
            if (line.startsWith(key)) {
                return oneLine(line.substring(key.length()).trim());
            }
            if (nl < 0) {
                break;
            }
            start = nl + 1;
        }
        return "";
    }

    /**
     * The {@code oneLine} method returns the same text with carriage return and line feed removed.
     * A null or empty string returns empty.
     *
     * @param raw The raw text is the field.
     * @return The return value is the text with those characters removed.
     */
    private static String oneLine(String raw) {
        if (raw == null || raw.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == '\r' || c == '\n') {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * The {@code getSshUsername} method returns the SSH user name.
     * A null name is returned as empty.
     *
     * @return The return value is the SSH user name.
     */
    @Override
    public String getSshUsername() {
        return sshUsername == null ? "" : sshUsername;
    }

    /**
     * The {@code getProcessor} method returns the command processor.
     *
     * @return The return value is the command processor.
     */
    @Override
    public CommandProcessor getProcessor() {
        return processor;
    }

    /**
     * The {@code write} method writes UTF-8 text to the SSH output and flushes it.
     * A null text or a null output returns. A write failure is ignored.
     *
     * @param text The text is the bytes to write.
     */
    @Override
    public void write(String text) {
        if (text == null || out == null) {
            return;
        }
        try {
            out.write(text.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException ignored) {
            // session likely closed
        }
    }

    /**
     * The {@code writeError} method writes the text in red and adds a carriage return and a line feed.
     *
     * @param text The text is the error line.
     */
    @Override
    public void writeError(String text) {
        write(Ansi.red(text) + "\r\n");
    }

    /**
     * The {@code setChatActive} method stores whether chat is active and sets the mode.
     * An active session becomes chat mode. An inactive session becomes idle mode.
     *
     * @param active The flag is true when chat is active.
     */
    @Override
    public void setChatActive(boolean active) {
        session.setActive(active);
        if (active) {
            mode = ShellMode.CHAT;
        } else {
            mode = ShellMode.IDLE;
        }
    }

    /**
     * The {@code requestExit} method disables bracketed paste and marks the shell as exiting.
     */
    @Override
    public void requestExit() {
        write(BRACKETED_PASTE_DISABLE);
        exiting = true;
    }

    /**
     * The {@code setModelName} method stores the Ollama model id.
     * A null or blank name returns without a change. Any other name is trimmed.
     *
     * @param modelName The model name is the Ollama id.
     */
    @Override
    public void setModelName(String modelName) {
        if (modelName == null || modelName.trim().length() == 0) {
            return;
        }
        this.modelName = modelName.trim();
    }

    /**
     * The {@code collectMessageEdit} method starts collecting a message body.
     * The next lines are stored until a line that is {@code <.>}.
     *
     * @param seq The sequence is the message number to edit.
     */
    @Override
    public void collectMessageEdit(long seq) {
        collectingEdit = true;
        editSeq = seq;
        editBody = new StringBuilder();
    }

    /**
     * The {@code takeEditLine} method accepts one line of a bare {@code message edit}.
     * The line {@code <.>} ends the body, edits that sequence, and writes {@code edited} plus the sequence. A failure writes the formatted error. Any other line is appended with a newline before it when the body is already non-empty. A null line is stored as empty. The prompt is written.
     *
     * @param line The line is the trimmed input line.
     * @throws IOException The method throws this exception when a write fails.
     */
    private void takeEditLine(String line) throws IOException {
        if ("<.>".equals(line)) {
            collectingEdit = false;
            String body = editBody == null ? "" : editBody.toString();
            editBody = null;
            try {
                session.editMessage(editSeq, body);
                write("edited " + editSeq + "\r\n");
            } catch (Exception ex) {
                writeError(formatError(ex));
            }
            writePrompt();
            return;
        }
        if (editBody == null) {
            editBody = new StringBuilder();
        }
        if (editBody.length() > 0) {
            editBody.append('\n');
        }
        editBody.append(line == null ? "" : line);
        writePrompt();
    }
}
/*****************************************************************************************
 * End of Class: ChatShell.java 082426
 ****************************************************************************************/
