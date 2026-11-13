# org.casserole.chat.cmd

`select model <name>` should feel like pointing at a tag, not like surgery. `swap <from> <to>` is already registered and it unloads one tag and loads another. `ollama list` already runs through `OllamaExecCommand`. The new names are the short door. `list models` calls Ollama `GET /api/tags` and prints the names. `select model <name>` sets the session model, unloads the previous session model, and loads the named tag with `keep_alive -1`, the same end state as swap. Stage 1 still speaks Ollama only. A Grok name is refused. The command string can grow a provider prefix in Stage 2 without a rename.

## Model Config modal

21. The Ollama effort rows ask the server that already holds the Ollama base URL. The Swing modal does not open a socket to Ollama, and it does not read that URL. The client writes one `CHANNEL_A` `TYPE_DATA` frame. The body starts with `show:` and the model id. `handle` sees that first line before the execute slot, before `inFlight`, and before Roux. It does not enter `execute`. It does not mark the cell busy, failed, or running. A run already in flight on that cell stays in flight. The server POSTs `{base}/api/show` with Jackson, body `{"model": id}`, connect 10 seconds, read 10 seconds. The base is the URL already injected for Ollama, with the trailing slash trimmed the way `OllamaLoadCommand` trims it. This is not `ollama load`, not `/api/generate`, and not a terminal command. The reply is `CHANNEL_A` `TYPE_DATA` for that same cell id. The body starts with `show_result:` and the model id, then one `value:` line per kept string. A miss is `show_result:` and no values. It is not `TYPE_ERROR` and it is not `CHANNEL_STREAM`, so the cell answer path and the strip never see it. The client applies the reply only while the modal is open on that cell and the id still matches. A stale reply is dropped. No AMWP session means the client does not send, and the rows stay hidden. There is no run banner. Jackson reads the top-level `thinking.values`. Booleans are dropped. A string is kept only when it is `low`, `medium`, or `high`, in the order returned. `ThinkLevel` on spring-ai 1.1.8 throws on any other string. This row does not upgrade Spring AI and does not open a chat client to pass a foreign string. `thinking.default` is not applied. The cell's stored level stays selected when it is still a row. Otherwise the effort pull-down has no selection until the user picks one. The checkbox still works. A missing `thinking` object, an empty list, a boolean-only list, or an HTTP miss hides the rows. The ask runs when the id field loses focus, and when the checkbox is turned on, and only when the id is non-empty. It does not run on each character. It does not run on the EDT.
**semantic-key:** PK-308

### 6.1 Mockup anatomy

| Region | Meaning |
|---|---|
| Canvas tabs Tab 1/2/3 | Conversations inside Aetheris. Not SSH splits. |
| Aether Space | Scrollable turn-cell column. |
| Header dots | **AMWP / engine / vault** health. Red down, gray idle, cyan live. Not macOS traffic lights. Not 4-state. |
| Cell blue / green | Settled vs focused. Border = focus, not include/exclude. |
| Cell checkbox | Multi-select for batch run / export / chain. Not 4-state. |
| Cell header Button 1–4 | **Modal** four-state. See §6.3. |
| Cell pin | **Burner.** Independent of 4-state. |
| Cell body | User Input / splitter / AI Response. One turn. |
| Model Selector | Per-cell `execution_target`. |
| Arrow | Sequential Turn N → N+1. Parallel groups are extra rows `[STAGE 2]`. |
| Inspector | Tools, MCP groups, **working-set preview**, canvas settings. |
| Footer | Command strip on focused cell / canvas. Not SSH Connect. |
| Status bar | Canvas + AMWP + engine + `vault: ram\ | disk`. |

### 7.1 Frame header

**Server:** mux around existing `Command` stdin/stdout. `ChatShell` writes VT100 into a pipe labeled `0x0001`. Aetheris server façade writes `0x0002+`. Façade is not the vault.
**semantic-key:** PK-309

## 2. What stays

The thirteen labels stay. Copy stays dark without a highlight. Paste stays dark when the clipboard has no string. Open as URL stays dark unless the highlight is a URL. Clear on the alternate screen still returns false from clear_scrollback_keep_line. The SSH mux stays. Channel A stays. Command-line history stays. Ctrl+A stays 0x01. The paste chords stay on the editor path. The card-round hunks stay unstaged.

## 0. On approval

4. Build the edits below. No lambdas in our types. No Lombok. No JUnit. The new public type is `SelectModelCommand`. No new package. No new vault column. No new channel. No new AMWP type.

## 3. landing

**Command.** `SelectModelCommand` in `org.casserole.chat.cmd`. Name `select-model`. Aliases empty. Help: `Open the Model Config modal for this console.` Description: the shell client opens the modal, a raw terminal prints the line, and `swap` still moves the weights. `CommandProcessorFactory.create` registers it after `SwapCommand`.
**semantic-key:** PK-310
**semantic-key:** PK-310
**semantic-key:** PK-310
**semantic-key:** PK-310

## 3. Edits

**Command.** `status` calls `writeModelStatus` where it printed `getModelName`. The help line names the selected model. The description names the family and the options.
**semantic-key:** PK-311

## 3. Edits

**Command.** A bare `select-model` sends the OSC and `Model Config`. One of the three words calls `selectConsoleFamily` and does not send the OSC. Any other argument throws the usage line.
**semantic-key:** PK-312

## 2. What stays

The thirteen menu labels stay. Copy, Paste, and Open as URL keep their gates. Clear on the alternate screen still leaves that grid as it stands. Bare Page is not a host byte. The term crate stays. The SSH mux stays. Channel A stays. Command-line history stays. The card-round hunks stay unstaged.

### 5. Show frame

- The base is `spring.ai.ollama.base-url`, the property `CommandProcessorFactory` already injects. The facade constructor takes the same `@Value`. A trailing slash is trimmed the way `OllamaLoadCommand.trimSlash` trims it. An empty value uses `http://localhost:11434`, the same fallback those commands use.
**semantic-key:** PK-313
**semantic-key:** PK-313

# Aetheris pre-plan 001

No Lombok. No lambdas in our types. No new package. The one new public type is `SelectModelCommand`.
**semantic-key:** PK-314

## 3. Cause

Init peek ran **inside** `Command.start()`. The Shell sends Init only **after** `channel.open().verify()` returns. MINA does not finish that open until `start()` returns.
**semantic-key:** PK-315

## 5. Spec impact

| Head | Action |
|---|---|
| Constitution §7 Init-or-raw | **No rewrite.** |
| Core §4.9 | One as-built sentence: peek must not block `Command.start()`. |
| This BF + index | **This turn.** |
**semantic-key:** PK-316

### 4.4 Model load / unload (not `ollama run`)

- Command name `ollama` matches remaining tokens as argv.
**semantic-key:** PK-317

### 5.3 ChatShell — the operator OS

It **does not** know command names. Enter in command mode hands the trimmed line to `CommandProcessor`.
**semantic-key:** PK-318

#### Colors (VT100 SGR)

Typed chat is **default/white** (no SGR). Command **output** is default color; only errors are red.
**semantic-key:** PK-319

### 5.4 Command processor

`CommandContext` is implemented by `ChatShell`: session, mode, properties, model name, SSH username, processor, `write` / `writeError`, `setChatActive`, `requestExit`.
**semantic-key:** PK-320

## 3. Resolution

`CommandProcessorFactory` reads the same key and passes it to `OllamaLoadCommand` and `SwapCommand`. Their generate bodies add `"options":{"num_ctx":N}` only when the value is positive.
**semantic-key:** PK-321
**semantic-key:** PK-321
**semantic-key:** PK-321

## 0. What 1.0 actually is (honesty)

1. **Flags before success.** `rotateHot` marks the RAM header `SPILLED` *then* waits for pins *then* writes. Pin timeout or I/O error leaves a hot page that *says* spilled, file missing, add aborted.
**semantic-key:** PK-322

## 9. The new section

| Offset | Field |
|---|---|
| 0 | Magic `C L I P` |
| 4 | Version, short, `1` |
| 6 | Size, short, `64` |
| 8 | Flags, int. Bit 0 set means the record is live. Bit 1 set means rewind. Clear bit 1 means auto. |
| 12 | Head origin, long. `0` means no floor. |
| 20 | Capacity, int. `0` when the percent trigger had no capacity. |
| 24 | Estimate, int. |
| 28 | Remaining, int. |
| 32 | Roll epoch, long, milliseconds. |
| 40 | CRC32C over bytes `[0, 40)`. |
| 44 | Pad through 63. |

## 10. Casserole integration

`MessageListCommand`: `memory.get(id)`, filter by role, print unmarshalled text.
**semantic-key:** PK-323

`StatusCommand`: `conversation:` (username or ephemeral UUID), optional `region-uuid:`, `ssh-user:`, Roux `seq=`.
**semantic-key:** PK-324

## 13. Roux CLI (later slice — accounted only)

Bind to engine APIs, implemented as Casserole `Command` objects (`roux memory …`), longest-name match:
**semantic-key:** PK-325

### P4 — Casserole wiring + exclusive + banner

`RejectShell`: implements `Command`; on `start`, write `Ansi.red(reason)`, disable paste, `callback.onExit(1)`. No Roux region.
**semantic-key:** PK-326

`StatusCommand`: keep `ssh-user`. `conversation:` = Roux region id (username or ephemeral UUID). If disk-backed, add `region-uuid: {manifest uuid}` so display UUID survives reconnects.
**semantic-key:** PK-327

## 2. Command layer

His words: it works perfectly. The team says this is the point.

## 2. Out of this build

| Stays as it is | Why |
| --- | --- |
| | This is the Rust silo. It does not retarget the Java product ask. |
| Command layer, splits, paste editor, import, font spinner, select model, Aetheris tab, canvas, Turso | Slice section 11. Remember password is already on the slice and is in this build. |
| `iced` | Closed 2026-10-23 12:00. The window is `egui` on `winit`. |
| Library publish name | `aetheris-term` is the in-tree name. Publishing waits. |
| Version pins in the spec | `Cargo.lock` is the pin, taken on the build day. The plan does not copy crate versions into markdown. |
| Host-key pin | Interest. This cut accepts the server key, the same as `AcceptAllServerKeyVerifier`. |
| A live SSH connect, an opened window, a push | |
| `application.yaml`, air-gap, `num-ctx`, the Grok POST | The client does not read that file, does not call Ollama, does not POST, and does not spawn `grok`. |
**semantic-key:** PK-328

### P2 — Server SSH UX (chrome, editor, commands, colors)

- `StatusCommand` — each `key: value` via `Ansi.kv`. No yellow wrap.
**semantic-key:** PK-329

- `MessageListCommand` — parse `verbose` like `meta`; default truncate to `list-preview-chars` (`0` = full). `message list meta` terse too.
**semantic-key:** PK-330

- `MessageFindCommand` — if args start with `by`, remainder is needle (strip one quote pair); case-insensitive contains; print every hit terse (`[seq] ROLE: preview`). Else existing `<idHi> <idLo>`. Bad usage lists **both** spellings.
**semantic-key:** PK-331
