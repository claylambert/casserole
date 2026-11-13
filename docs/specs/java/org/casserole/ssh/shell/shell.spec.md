# org.casserole.ssh.shell

## 3. Floor

- The semaphore of 3 stays three cell calls. `LLM_EXEC` stays 4. `ChatShell` does not take the semaphore.
**semantic-key:** PK-412

## 4. P1 — Logging

- Replace `System.out.println` in `SshServer` start banner with INFO **and** keep one console line so OpenSSH operators still see port/auth
**semantic-key:** PK-413

### 6.3 `ChatShell`

`ChatShellFactory` constructor grows a `Lamina` parameter (Spring injects the singleton engine).
**semantic-key:** PK-414

## 11. House rules for implementers

- `PRESERVE-ALL` — do not “clean up” ChatShell/Roux/splits to make Lamina prettier
**semantic-key:** PK-415

## 7. P4 — Bantha Roux

- `ChatShellFactory` still `open(user)` for vt100
**semantic-key:** PK-416

## 8. P5 — AMWP

**Server:** mux **around** ChatShell pipes. Client Init with no valid reply → **raw PTY**, never wrap JediTerm.
**semantic-key:** PK-417

## 14. Files / components (checklist)

| Path | Slice |
|---|---|
| `SshSessionController` / pane resize → NAWS | P0 |
| `ChatShell` window-change, wrap, sticky cmd | P0 |
| `SshClientProperties` / `SshClientTheme` | P1 |
| `pom.xml` Hikari+H2 compile | P2 |
| `org.aetheris.config.AetherisYaml` | P2 |
| `org.aetheris.swing.AetherisWorker` | P2 |
| `org.aetheris.vault.*` | P3 |
| `RouxChatMemory.openRam` | P4 |
| `CasseroleApplication` scan | P4 |
| `org.aetheris.wire.*` | P5 |
| Client byte demux in front of TtyConnector | P5 |
| Server mux around ChatShell | P5 |
| `org.casserole.llm.LlmProvider` | P6 |
| `swap` command | P6 |
| `SshClientApp` JTabbedPane | P7 |
| `org.aetheris.swing` canvas / inspector | P7 |
| `bin/docs/*` | P8 |

## 11. Package 8. Tool hooks only

**Done means:** The channel, the lane, the flag, the depth, and the empty allowlist exist. No tool runs. No tool is painted into `ChatShell`. The flag stays false.
**semantic-key:** PK-418

### WP-B — Logging (S0-10)

Replace `System.out.println` in `SshServer.start` banner with INFO logs **and** keep a one-line console banner so operators still see the port. Do not go silent.
**semantic-key:** PK-419

### WP-F — Lamina (S0-11, S0-12)

- Shutdown: `ChatShell.destroy` and Boot `@PreDestroy` must drain/cancel via Lamina, not `executor.shutdown` on a private pool
**semantic-key:** PK-420

## 5. House rules the plan must carry

| Token | Stage 0 meaning |
|---|---|
| `PRESERVE-ALL` | Do not “clean up” ChatShell, Roux, splits, formatters to make Lamina prettier |
| `NO-LAMBDA` | Lamina submit sites use named types / anonymous classes |
| `PURE-JAVA` | Scripts may call `java` and vendor installers; app code stays Java |
| | PlantUML on Lamina state/lane diagram in JavaDoc if the executor is non-obvious |
| | When **stamps** the Stage 0 plan, copy to |
| Q7 | Do not spend this stage deleting JPA |
| Q17 | Do not add a Casserole Spring datasource / Hikari on the server |
**semantic-key:** PK-421

## 8. Acceptance (Stage 0 done)

- [ ] Lamina lanes live; ChatShell LLM turns and SSH listen/read no longer use private `new Thread` / `newSingleThreadExecutor`
**semantic-key:** PK-422

### WP-A — Tab A defects (S1-16…S1-18) first among equals

- NAWS/window-change on connect **and** resize; ChatShell listens; wrap input at `terminalColumns`.
**semantic-key:** PK-423

## Console word step

1. `ChatShell.handleCompleteEscape` treats `ESC [ 1; 5 D` and `ESC [ 5 D` as one word backward, and `ESC [ 1; 5 C` and `ESC [ 5 C` as one word forward. The cursor stays inside the unsent line. The line redraws when the cursor moves.

## SSH I/O logs the connect and the disconnect

`SshIoTrace` is package-private in `org.casserole.ssh.shell`. `SshServer.listenLoop` calls `SshIoTrace.attach` after the shell factory is set and before `sshd.start`. The password authenticator calls `SshIoTrace.authReject` when the account check returns false. The password is not a parameter of that call. Each line is INFO. Each line starts with `ssh-io`. The root logger is already INFO.
**semantic-key:** PK-424
**semantic-key:** PK-424

## Roux code carries the tags and the Knuth source prose

Java is named. A full scan of org.roux applies the relevant tags. The same scan upgrades the Roux documentation to the Knuth source prose. ChatShell stays out.

## Server stop keeps the shutdown cycle

No new package. No new public type. No new public method. The private classes are QuietNioFactory and QuietNioService. The private methods are settleAccept, drainGroup, and pause. ACCEPT_SETTLE_MS is 1000. GROUP_GRACE_MS is 5000. No new. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands.

### Consolidation

- **One provider call, two regions.** `ChatShell` and `AmwpFacade` each build a message list, prepend the casserole system prompt, and call `LlmService.complete`. The host call can be one method that accepts the working set, the target, and the region id. Tab A stays on `{user}`. Aetheris stays on `{user}.aetheris`. `NO-DUAL-WRITE` stays.
**semantic-key:** PK-425

### 3. MCP gets a channel, a lane, and a closed default - Make it so. Approved

- Depth is a counter on the tool loop. The allowlist is checked before the invoke. Over the cap, or off the list, the cell gets an error and the loop stops. Day-one depth is 1. One hop, then the loop stops. The hooks stay: channel `0x0004`, the `TOOL_EXEC` lane, the `tool_calling_enabled` flag, and depth 1. Tool use is off until he approaches tool integration. The allowlist is empty. No tool is invocable. The plan does not turn the flag on. What the tools will do is not defined. Cells stay prompt-only. Tool output is not written to the PTY unless the operator uses the crosstalk confirm. Tools are not painted into `ChatShell`. Stage 1 execute stays `LLM_EXEC`.

### MCP host

- Do not paint tools into `ChatShell`.

### 10. The router sees a prompt and a target - Smoked. Approved

 - `ChatShell` builds the Tab A prompt, including the casserole system line, and calls `LlmRouter.complete` with target `ollama` and region `{user}`.
**semantic-key:** PK-426

#### 4.8.4 Theme palette — ****

Prompt colors: server `Ansi` for `chat>` / `cmd>` **and** matching named Swing config so Tab A chrome and the PTY prompt do not drift. Server yaml may hold the SGR names; client yaml holds the Swing colors. Do not add a new yaml parser family.
**semantic-key:** PK-427

### 2.1 Placement in this tree

2. **Tab B Aetheris** — all new UI in `org.aetheris` / `org.aetheris.swing`. Not inside `ChatShell`.
**semantic-key:** PK-428

### 2.2 Dual source of truth

7. Aetheris-only (never type in JediTerm): vt100 Roux may still exist because ChatShellFactory opens on SSH connect (`PRESERVE-ALL`). Do not rebuild login for Stage 1.
**semantic-key:** PK-429

### 7.1 Frame header

**Backpressure:** one in-flight execute per `cell_id`. Do not invent a second ack protocol in Stage 1. Keepalive 2s idle; does not touch Roux exclusive or ChatShell mode.
**semantic-key:** PK-430

## 14. Tools / MCP `[STAGE 2]`

Until MCP exists, cells are prompt-only. Do not paint tools into `ChatShell`.
**semantic-key:** PK-431

## 0. On approval

4. Build the edit below. No lambdas in our types. No Lombok. No JUnit. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. The package-private type is SshIoTrace. The package methods are attach and authReject.

6. One commit of SshIoTrace.java and the SshServer hunks.

## 3. Edits

consoleModelFrame swallows an ask whose first line equals session_tokens: and replies with writeConsoleReply of sessionTokenReply when the inner shell is a ChatShell. The ask does not paint. The end of startMux, after bindConsole, pushes that same reply when the inner shell is a ChatShell.
**semantic-key:** PK-432

## 1. Goal

A reader of org.roux can follow each type from its prose, and each tag names the spec that type serves. The key stays in the family section 10.3 already locks. The spec file is one trailing word. A further trailing word is a hint. ChatShell stays out.

## 3. Edits

Java. ChatShell. The welcome line names Load File.
**semantic-key:** PK-433

## 0. On approval

4. Build the edit below. No lambdas in our types. No Lombok. No JUnit. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. The private classes inside SshServer are QuietNioFactory and QuietNioService. The private methods are settleAccept, drainGroup, and pause. ACCEPT_SETTLE_MS is 1000. GROUP_GRACE_MS is 5000.

## 2. What stays

On this JDK a stock stop of an idle port-0 server returns in about 1 ms. The stock awaitTermination of 5 seconds does not elapse. The five-second drain stays in QuietNioService, before that stock close, while the executor still accepts the accept callback.
**semantic-key:** PK-434

## 3. Edits

Java. SshServer.shutdown sets running false, notifies listenLock, calls sshd.stop, then cancels the listen task. A stop that throws is logged. The method still finishes.
**semantic-key:** PK-435

## 3. Edits

Java. listenLoop installs QuietNioFactory before start.
**semantic-key:** PK-436

Java. QuietNioService.doCloseImmediately logs the settle, waits ACCEPT_SETTLE_MS, logs the drain, waits GROUP_GRACE_MS, then calls the stock close. The group executor stays open through both waits. The stock close still runs shutdownNow and awaitTermination.
**semantic-key:** PK-437

## 4. Proof

A localhost stock server, port 0, no client, with the same 1000 ms settle and 5000 ms drain, stopped in 6010 ms. The captured stderr did not contain Executor has been shut down. The live POST is not made by the proof. A pool turn is not sent. A live SSH connect is not made. No Session::start. No chrome::run. A clean checkout was not recompiled.

## 3. The cause

The canvas prompt is a text area that is rebuilt with the card. An undo edit bound to that document dies with the card. The SSH line is not a Swing document. It lives in ChatShell. A byte 0x1A was ignored. Shift-Ctrl-Z had no sequence. The clients had to send a chord the shell could recognize without changing the keys that already do work.

## 4. The seat

ChatShell keeps a draft seat and a visit seat. A history hop discards the visit stack and syncs the canonical line. The draft stack stays parked with the draft. The first text change that leaves history mode adopts the visit seat as the draft seat. Enter and bare Escape drop both stacks. Ctrl-Z is byte 0x1A. Redo is ESC [ 1; 6 Z. A paste is one step, recorded when the paste ends. The history file does not store the stacks.

## 3. The cause

A plain reply does not match the markdown detector. The identity path keeps every line feed as a hard CRLF. JediTerm treats that line feed as a hard break, so each sentence wraps on its own. Rust soft-wraps one run and rejoins only rows marked wrapped. A hard CRLF stays a break. InputPage and paintFull still emit a hard break for the editor and for Shift-Enter. Those bytes match a sentence line feed, so the join belongs on the formatter.
**semantic-key:** PK-438
**semantic-key:** PK-438

## 2. Out of this build

- A Spring AI upgrade. spring-ai stays 1.1.8. No second Ollama chat client. No call to `ChatShell.setModelName`.
**semantic-key:** PK-439

## 8. Package 4. Ollama id and ThinkLevel

- `setModel` on the `OllamaChatOptions` the think path already builds, when the id is non-empty. An empty target and a bare `ollama` do not set the model. The configured Spring model stays. Do not call `ChatShell.setModelName`.
**semantic-key:** PK-440

## 3. The edit

The semaphore of 3 stays. One pool prompt per user stays on the child gate, so two pool members of one group stay in order on that gate. A pool member and an Ollama member overlap. Two Ollama members overlap on this release. Ollama's own queue stays the operator's setting. `runChain` stays sequential. ChatShell still shares `LLM_EXEC` and still takes no permit from the semaphore. No new pool reason.
**semantic-key:** PK-441

## 3. Edits

The seat is `ChatShell`.

## 3. Edits

# Aetheris pre-plan 001

Scan applies the relevant tags. The same scan upgrades the Roux documentation to the Knuth source prose. ChatShell stays out.

## Seat

shutdown signals the listen loop, calls sshd.stop, then cancels the listen task. QuietNioService settles the accept for 1000 ms, drains the group for 5000 ms, then the stock close runs. The private classes are QuietNioFactory and QuietNioService. The private methods are settleAccept, drainGroup, and pause. No new public type. No new public method. This stamp has no product.
**semantic-key:** PK-442
**semantic-key:** PK-442

## Dependencies pitied

ChatShell owns the SSH line buffer. A canvas prompt is a local editor on each client. javax.swing.undo.UndoManager is the Java stack. EditMemory stores the text and the cursor, and the caller writes them into the live widget. A card rebuild must not capture a dead text area. History up and history down reload the canonical line and are not undo steps. The history file stays. The first typed character on a recalled line still leaves history mode. Ctrl-A, Ctrl-E, Ctrl-C, Enter, arrows, and paste stay. No new library. No Cargo.toml edit. No new JUnit class.

## Dependencies pitied

`EditMemory` is the RAM seat. `note` stores a full copy of the prompt on the key. The cell listener reads `getText` and `getCaretPosition`. The caret Swing reports has already stepped past the characters just inserted. `applyCellText` writes that seat back into the live prompt. `ChatShell` keeps `note` for the SSH line. The cell path is the seat.
**semantic-key:** PK-443

## The cause

### 2.2 Out

| Item | Why |
| --- | --- |
| Grok serial, Grok plan, Grok pre-plan | They stay the earlier reading. This slice does not edit them. |
| | One `JTextArea`. This slice does not add a widget and does not reopen the drag-height pane. |
| | They stay. |
| `select model` and `list models` | Tabled. The Ollama id stays a free-text field. The pull-down is families. |
| Air-gap, `no-model` order, `burner_cloud` | The doors stay. Choosing Grok does not write the air-gap word. |
| Probe gate | One `Hello` for the server process. This slice does not make it per cell. |
| Roux hold | The call stays inside the hold that already wraps `complete`. |
| `num-ctx` 4096 | |
| Mood shaper | Its own execute. It does not open this modal and does not gain a `think` key. |
| `ChatShell.setModelName` | That call swaps the terminal model. This slice does not call it. |
| `application.yaml` | The show URL is the property already injected. This slice does not edit the file. |
| Spring AI upgrade | 1.1.8 stays. `ThinkLevel` accepts `low`, `medium`, and `high`. |
| N6, seed-kit prod 1.0, Episode 2 | Held, frozen, done 2026-10-18 08:30. |
| New test harness | Shape matches the earlier. |

### 4. Ollama model id and ThinkLevel

- An empty target and a bare `ollama` do not set the model. The configured Spring model stays. `ChatShell.setModelName` is not called.
**semantic-key:** PK-444

# — AMWP Init echoed as `chat> N���`

**Kind:** ad-hoc request spec (bug-fix description). **Not a stage plan.**
**Module:** aetheris (AMWP) — ChatShell is the victim; mux lives beside it.
**When:** 2026-09-02. **Stage:** 1. **Reporter:** **Status:** fixed.
**Keys:** `G-18G-07OPAQUE-TONE-PIPE`
**Does not mint**. Java stays `// `.

## 2. Fingerprint

CRC32C of header `[0,12)` + payload is `07 4e 91 d2`. **`0x4E` is ASCII `N`.** `0xAE` / `0x91` / `0xD2` are invalid UTF-8 → U+FFFD. ChatShell echoes only non-ISO-control chars onto the line after `chat>`. That is exactly `N���`.

## 4. Resolution

`start()` starts the byte pump and **returns immediately**. Handshake (peek → mux or raw ChatShell) runs on Lamina `SSH_IO` **after** the channel is open so the client Init can arrive. OpenSSH still times out to raw. Init eater remains for a late Init on the raw path.
**semantic-key:** PK-445

### Holes

5. **The VT100 spinner is not this map.** If the hello think is the console `ChatShell` spinner, that shell has its own `inFlight` and stops its own spinner when that shell's `complete` returns. The run map does not start it and does not clear it. The shared model is still why that spinner outlasts 30 seconds.
**semantic-key:** PK-446

## 3. Cause

The paste door was already built. `sendPaste` wraps `ESC[200~` and `ESC[201~` and writes no trailing CR. ChatShell records a newline inside that paste and paints once at the end. To terminal was still on the raw write.
**semantic-key:** PK-447

# — Wrap first copies the unwrapped line, then wraps

**Module:** casserole (ChatShell input redraw).

# — VT100 paste must not send a return

**Module:** casserole (VT100 client and ChatShell).
**When:** 2026-10-19 21:13. **Stage:** 1. **Reporter:** **Status:** closed.
**Keys:** none new.
**Trigger:** named the defect and the build.
**Does not mint**.

## 2. Fingerprint

On `ESC[201~`, ChatShell set `pasteSealed` and wrote the paste summary plus `\r\n`. The following `\r` took `handleEnter` and submitted the line. A sealed paste also drops further typing until Enter, Ctrl+C, or Backspace.
**semantic-key:** PK-448

# — The server page paints a paste

**Module:** casserole (ChatShell).
**When:** 2026-10-25 14:38. **Stage:** 1. **Reporter:** **Status:** closed.
**Trigger:** named the first Java plate and the build.

## 4. Resolution

`InputPage` is the one walk. Paint and the cursor both read it.
**semantic-key:** PK-449

## 7. Verify

`mvn -q -Dtest=InputPageTest test` from ` 12 tests, 0 failures. That run compiles the main tree.

# — A page edit paints the damage

**When:** 2026-10-25 15:23. **Stage:** 1. **Reporter:** **Status:** closed.
**Trigger:** held Backspace on a pasted page and named the jitter. Rust stays held.

## 2. Fingerprint

Every Backspace and every typed glyph called `redrawInputLine`. That walk returns to the page origin, sends erase-in-display (`ESC[J`), and writes every remaining character. `ChatShell.write` flushes on every call, so the erase reaches the client before the text. A held key repeats that blank and rebuild. A paste stays calm because the glyphs stay silent until one paint at `ESC[201~`.
**semantic-key:** PK-450

## 4. Resolution

`InputPage` stays the only measure. A glyph that fills the width still parks with a return and a line feed. The cursor leaves that last column.
**semantic-key:** PK-451

## 7. Verify

`mvn -q "-Dtest=PagePaintTest,InputPageTest" test` from ` PagePaintTest 9 tests, InputPageTest 12 tests, 0 failures.

#### Spring AI / Ollama

| Key | Default | Role |
|---|---|---|
| `spring.ai.ollama.base-url` | `http://localhost:11434` | Injected into `OllamaLoadCommand`. |
| `spring.ai.ollama.init.pull-model-strategy` | `never` | Do not pull models at Boot. Operator must `ollama pull` themselves. |
| `spring.ai.ollama.chat.model` | `qwen2.5-coder:7b` | Spring AI 1.1 property. Also duplicated under `options.model`. |
| `spring.ai.ollama.chat.options.model` | `qwen2.5-coder:7b` | **This** is what `ChatShellFactory` injects as `modelName` for `status` / `ollama load` / `ollama stop`. |
| `spring.ai.ollama.chat.options.num-ctx` | `8192` | Demo value sent as `options.num_ctx`. |
**semantic-key:** PK-452

### 5.1 Lifecycle

`org.casserole.ssh.shell.SshServer` extends `Thread` (`casserole-sshd`, non-daemon).
**semantic-key:** PK-453

#### Ctrl+C

The **client** must request a raw PTY (`ISIG=0`) or the kernel will turn Ctrl+C into SIGINT before ChatShell sees `0x03`. That was a real bug during paste (`^C` per line of a POM).
**semantic-key:** PK-454

### 6.4 Tables

- Header and body cells use yellow SGR from ChatShell’s `replyContext()`.
**semantic-key:** PK-351

### 7.3 Session / PTY

ChatShell does its own echo and line editing. Canonical echo + ISIG mangled bracketed paste and turned Ctrl+C into a signal (`^C` per pasted line).
**semantic-key:** PK-455

### 11.5 LLM

- Swap/add providers behind `LlmService` (other Spring AI models) without changing ChatShell.
**semantic-key:** PK-456

## `spring.ai.ollama`

| Key | Checked-in | Options / notes |
|---|---|---|
| `base-url` | `http://localhost:11434` | Ollama HTTP. Also injected into `ollama load`. |
| `init.pull-model-strategy` | `never` | Spring AI: **`never`** (don’t pull on Boot), **`always`**, **`when_missing`** (name may vary by 1.1.x). Keep `never`; pull yourself. |
| `chat.model` | `qwen2.5-coder:7b` | Spring 1.1 model id. |
| `chat.options.model` | `qwen2.5-coder:7b` | **This** is what `ChatShellFactory` / `status` / `ollama load` / `ollama stop` use (`@Value`). Keep both in sync. |
**semantic-key:** PK-457

## 10. Server and the SSH session

The server runs the estimate, the roll, and the rewind. The place is `ChatShell.runLlmTurn` for the connection that owns the region, and the command objects for `message rewind` and the `status` line. The client is the terminal on that connection. It does not keep a second copy of the head. After a roll, the next prompt, the message list, and `message show` on that connection read the same region.
**semantic-key:** PK-458

## 17. Acceptance (v1, end-to-end, no JUnit mandate)

5. `ChatMemory` adapter usable without ChatShell (add/get/clear Messages).
**semantic-key:** PK-459

### P4 — Casserole wiring + exclusive + banner

`ChatShell.start`: after welcome lines, if `session.wasRestored()` write `Restored N messages for {user}`; else `New session`. Then existing prompt.
**semantic-key:** PK-460

## 7. Sequencing gotchas (must not miss)

1. **Username exists in `createShell`.** Do not wait for `ChatShell.start`.
**semantic-key:** PK-461

## 2. As seated

**Checkpoint.** `checkpointHot` calls `publishPage` on the hot page and does not unmap. `durability` `hot-only` returns before that call. `append` calls it at the end of `appendMessage`. `ChatShell` calls `session.checkpoint()` on the commit path. A thrown write inside `checkpointHot` is swallowed. Requirement 9 is that behavior.
**semantic-key:** PK-462

# Rust Aetheris client

- **What it is not:** the Java page. A new F row. A change to `ChatShell`. A second line editor.

# Rust Aetheris client, working serial

- A page edit paints the damage. A held Backspace does not redraw the page. Rust work on this plate is held. Plate 3 is the Rust client. The think strip waits. PagePaintTest 9 tests, InputPageTest 12 tests. No F-number.

### Catch-up order

It worked. SoftReturnTest stayed 1 test.

## 2. The Swing lesson

The Java shell paints a glyph edit as the damage, in one flush. When the row count drops, `PagePaint` sends delete-line (`ESC[nM`) with the cursor on the first row past the new page. JediTerm removes those lines and leaves the cursor where it is. Erase-in-display stays off that path.
**semantic-key:** PK-463

## 2. The turn

Side move: ChatShell inserts a newline on a lone LF and does not submit. A CR still submits. A LF after a CR is still swallowed.
**semantic-key:** PK-464

## 3. Proof

SoftReturnTest passed, 1 test. No Session::start. No chrome::run.

## 10. Smoke

SoftReturnTest stayed 1 test.

## 2. The turn

Side move: `ChatShell.seatConsoleGrokThink` marks the SSH Grok call and turns its stream on when `grok_think` is true. The requirement is that `reasoning_content` is readable. The saved stream checkbox stays. The sibling is the Rust pane.
**semantic-key:** PK-465

## 3. Proof

the_vt_menu_offers_select_model holds Export Session. the_export_writes_the_canvas peels the table and writes the file. ShellMoodFrameTest passed, 1 test. No Session::start. No chrome::run.

## 4. The model

| Fact | Where it lives |
|---|---|
| Property | `spring.ai.ollama.chat.options.model` |
| Checked-in id | `qwen2.5-coder:7b` |
| Who reads it | `ChatShellFactory`, by `@Value` |
| Who calls Ollama | The host. Boot still starts when Ollama is down. A failed turn is red text from the host. |
**semantic-key:** PK-466

## 6. The stream

`ChatShell.redrawInputLine` rebuilds the line on every key. It writes CR, cursor-up when the line already wrapped, erase-in-display (`ESC [ J`), the prompt, then one flush per character, then the cursor. `write` flushes every call. A longer line is more flushes. The erase runs before the characters come back.
**semantic-key:** PK-467

## 8. Keys and paste

The same stand-up also wires the host line editor. The encoder already knows these bytes. DECCKM stays the library rule: CSI while that mode is off, SS3 while it is on. ChatShell accepts both forms for the arrows.
**semantic-key:** PK-468

## 5. Rust account

Face. The first keys are the ones `ChatShell` already understands, so a typed line round-trips.
**semantic-key:** PK-469

## 8. Grid

The first grid is enough VT100 to paint a chat transcript and to accept the redraw `ChatShell` already sends.
**semantic-key:** PK-470

## 9. Keys

`ChatShell.handleChar` and `ChatShell.handleCompleteEscape` are the host's table. The encoder matches it.

## 12. Tests and safe Rust

- A cursor address plus erase redraws a line the way `ChatShell` redraws the input line.
**semantic-key:** PK-471

### P1 — Yaml + `users.yaml` + per-user history + auth

- `SshServer` — `PasswordAuthenticator` matches any loaded entry; if file present do **not** merge `casserole.ssh` pair
**semantic-key:** PK-472

- `ChatShell.historyFile()` — `~/.casserole/{sanitizedUser}/history` (username is already set in `start()` before `loadHistory()`)
**semantic-key:** PK-473

## Sequencing gotchas

3. History path needs sanitized username; invalid SSH name already `RejectShell`.
**semantic-key:** PK-474

### Today

`ChatShell.runLlmTurn` stops the spinner, writes the yellow-formatted assistant blob, then `chat>`.
**semantic-key:** PK-475

### Today

`SshServer` authenticates one pair from `casserole.ssh.username` / `password`. Restore already keys Roux by authenticated username and `{spill.dir}/{user}/`.
**semantic-key:** PK-476
