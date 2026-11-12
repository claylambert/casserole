# org.casserole

Concept file. It states what this program is.

**Depends:** [java/org/casserole/config/config](config/config.spec.md), [java/org/roux/roux](../roux/roux.spec.md)

The specifications in the directories under this package are these.

- [org.casserole.app](app/app.spec.md)
- [org.casserole.app.ui.ssh](app/ui/ssh/ssh.spec.md)
- [org.casserole.chat](chat/chat.spec.md)
- [org.casserole.chat.cmd](chat/cmd/cmd.spec.md)
- [org.casserole.config](config/config.spec.md)
- [org.casserole.edit](edit/edit.spec.md)
- [org.casserole.format.fence](format/fence/fence.spec.md)
- [org.casserole.format](format/format.spec.md)
- [org.casserole.format.json](format/json/json.spec.md)
- [org.casserole.format.list](format/list/list.spec.md)
- [org.casserole.format.markdown](format/markdown/markdown.spec.md)
- [org.casserole.format.table](format/table/table.spec.md)
- [org.casserole.lamina](lamina/lamina.spec.md)
- [org.casserole.lamina.spring](lamina/spring/spring.spec.md)
- [org.casserole.llm](llm/llm.spec.md)
- [org.casserole.log](log/log.spec.md)
- [org.casserole.mood](mood/mood.spec.md)
- [org.casserole.ollama](ollama/ollama.spec.md)
- [org.casserole.pool](pool/pool.spec.md)
- [org.casserole.ssh.shell](ssh/shell/shell.spec.md)
- [org.casserole.ssh](ssh/ssh.spec.md)

## The pool child starts as `grok agent stdio` with no updater flag, no approve flag, and no model flag.

Pool was run and the banner read `pool down exit chunk 0 retry used stderr 124`. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The installed `grok agent stdio` rejects `--no-auto-update` and exits 2. The 124 bytes on stderr are `error: unexpected argument '--no-auto-update' found` plus the usage line. The help on that command lists `--debug`, `--debug-file`, `--leader-socket`, and `--help`. `grok update` is its own command. The child never reaches `initialize`. The one retry dies the same way. `chunk 0` means no chat text was forwarded. The word `exit` on the banner is the death place. The number 124 is the stderr byte count.

The spawn is `grok agent stdio`. Windows stays `cmd.exe /c grok agent stdio`, so the `grok.cmd` shim still starts through the command host. No `--no-auto-update`. No `--always-approve`. No `--model`. No MCP list. `XAI_API_KEY` still stays off the child. `USERPROFILE` and `HOME` stay. The working directory stays `user.home` plus `.casserole/pool-cwd`.

The pool plan, the pool pre-plan, and draft.002 keep the old flag sentence. A sentence on each names this rule as the operative spawn. Draft.001 stays untracked and unedited. The earlier rows stay.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent.

The rejected flag comes off both spawn lines. The Windows command host stays. The key strip, the directory, and the one retry stay. The old flag sentences stay, with the sentence that names this spawn. The row is the build.

**Truth.** The pool child is `grok agent stdio`, and on Windows `cmd.exe /c grok agent stdio`. It does not receive `--no-auto-update`, `--always-approve`, `--model`, or an MCP list. `XAI_API_KEY` stays off the child. The banner `pool down exit chunk 0 retry used stderr 124` was exit 2 and 124 stderr bytes naming `--no-auto-update`.

**Reason.** That installed command rejects `--no-auto-update`, prints `error: unexpected argument '--no-auto-update' found` plus the usage line, and exits 2 before `initialize`. The one retry died the same way, so no chat text was forwarded. `grok update` is its own command. The help lists `--debug`, `--debug-file`, `--leader-socket`, and `--help`. The command-host shim stays so Windows still starts `grok.cmd`. The key stays off the child. The working directory stays `user.home` plus `.casserole/pool-cwd`.

**Avoidances.** Do not pass `--no-auto-update`, `--always-approve`, `--model`, or an MCP list. Do not put `XAI_API_KEY` on the child. Do not drop `USERPROFILE` or `HOME`. Do not edit draft.001. Do not send a pool turn, make a live POST, or open the window from this filing.

**semantic-key:** PK-0488

## A pool tool notice is noise, a permission request is denied once, and the cell fails only when that deny cannot be written.

A second Pool turn showed the banner `pool tool refused session/update tool_call notes: 25`. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The first turn landed in message text. The second turn sent a `session/update` whose kind is `tool_call`. That notice has no id. The handler failed the cell before a later message chunk could land. `notes: 25` is the other updates on that same prompt.

`tool_call` and `tool_call_update` are noise. They are counted, not painted, not stored, and not a failure. Message chunks still paint on the card. Thought chunks still paint on the footer strip when Pool Thinking is on. Text inside a tool notice stays off both surfaces.

A request whose method contains `permission` is answered on that same id. The reply selects the option whose kind is `reject_once`. If that kind is absent, it selects `reject_always`. If neither reject kind is present, the outcome is `cancelled`. The tool title, the command, and the input stay off the log. `session/cancel` is not sent. The cell does not fail. The child stays up.

Any other request from the child stays `pool payload`, then `session/cancel`. `--always-approve`, `yoloMode`, and `autoMode` stay off. The spawn stays `grok agent stdio`. The prompt still ends only on the `session/prompt` response. Text that arrived is the answer. A turn that ends with no message chunk stores an empty reply and does not open the slide. `pool tool refused` remains the reason when the deny cannot be written.

The pool plan, its plan copy, and draft.002 keep the old tool-failure sentences. A sentence on each names this rule. Draft.001 stays untracked and unedited. The earlier rows stay.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent.

Tool notices are noise, and one deny is written on the permission request. Message text and thinking text are the only paint. The prompt latch and the child stay. `--always-approve` stays off, and every other child request stays on `pool payload`. The row is the build.

**Truth.** `tool_call` and `tool_call_update` are counted and do not fail the cell. A permission request is answered `reject_once`, else `reject_always`, else `cancelled`, on the same id. Any other child request is `pool payload`, then `session/cancel`. `pool tool refused` remains only when the deny cannot be written. The measured banner was `pool tool refused session/update tool_call notes: 25`.

**Reason.** The tool notice has no id, and failing the cell on it dropped the chat before a later message chunk could land. `notes: 25` counts the other updates on that same prompt. Text inside a tool notice stays off the card and off the strip so only message chunks and, when Pool Thinking is on, thought chunks paint. The tool title, the command, and the input stay off the log. The prompt still ends only on the `session/prompt` response, and text that arrived is the answer.

**Avoidances.** Do not paint or store a tool notice. Do not fail the cell on `tool_call` or `tool_call_update`. Do not send `session/cancel` for a permission deny. Do not enable `--always-approve`, `yoloMode`, or `autoMode`. Do not change the spawn off `grok agent stdio`. Do not open the slide when a turn ends with no message chunk. Do not edit draft.001. Do not send a pool turn from this filing.

**semantic-key:** PK-0489

## A pool member and a local member overlap because the region lock is released before the provider call, and the reply window gains a find bar.

A join group sent a pool child and an Ollama cell. The child answer arrived after the Ollama request. Those two go on the wire together. The reply window also gains a find bar for a large reply and for JOIN lines in a merge. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The client already writes each member frame and returns. The server holds that user's Roux region across the provider call. The pool prompt blocks inside that hold, so an Ollama complete that took the lock first keeps the child waiting. `LLM_EXEC` runs the execute. `POOL_IO` reads the child. The hold is what lines them up.

Clear and add stay inside the region lock, as one pair, so the region is the last writer's whole list. The lock is released before `runPool`, before the probe, and before `llmRouter.complete`. The wire body stays the Prompt already built. `stillMine` still drops a late reply. The semaphore of 3 stays. One pool prompt per user stays on that child's prompt gate, so two pool members of one group stay in order on that gate. A pool member and an Ollama member overlap. Two Ollama members overlap on this release. Ollama's own queue stays the operator's setting. `num-ctx` stays. A pipe chain stays sequential. No new mux channel. No new pool reason. `PoolChatServer` stays.

The model plan package 7 and its plan copy keep the hold-through-complete sentences. A sentence on each names this yield. The probe gate does not assume the region lock is held. Hello still releases the probe lock before the POST. The region lock is not held across that wait.

The reply window gains a find bar. Ctrl-F opens it. When the bar is open and the field has text, Ctrl-F moves to the next match and wraps to the start. When the field is empty, Ctrl-F focuses the field. Esc hides the bar, clears the highlights, and returns focus to the reply. Esc leaves the window open. Each keystroke highlights every match in that reply. The match is case-insensitive. An empty field clears the highlights. No match shows the word none on the count. The error slide stays out of this search. Every match is marked. The current match is the selection and scrolls into view. The `^` button moves to the previous match and wraps to the end. The `v` button moves to the next match and wraps to the start. Each of those controls carries a tip. The search reads the reply already in the window, including the JOIN lines in a merge. The canvas search stays the canvas search. The field is not filled from a selection. At most 2000 matches are stored and painted. Past that the count says there are more, and the buttons walk the stored set. The work stays on the Swing thread. No new package, public type, vault column, or G-key.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent.

The region lock is released before the provider call, and clear and add stay one pair. The find bar is on the reply window, with Ctrl-F, Esc, and the `v` and `^` buttons. The semaphore of 3, the child gate, and the pipe chain stay. The old hold-through-complete sentences stay, with the sentence that names this yield. The row is the build.

**Truth.** Clear and add hold the region lock as one pair, and the lock is released before `runPool`, the probe, and `llmRouter.complete`. A pool member and an Ollama member overlap. Two pool members of one user stay in order on that child's prompt gate. The semaphore stays 3. The reply window find bar stores at most 2000 case-insensitive matches.

**Reason.** The pool prompt blocked inside the Roux hold, so an Ollama complete that took the lock first kept the child waiting. Releasing the lock before the provider call lets those lanes overlap without splitting the region's list, because clear and add stay one pair. `stillMine` still drops a late reply. A pipe chain stays sequential. The find bar reads the reply already in the window, including JOIN lines, so a large merge can be searched without using the canvas search or the error slide.

**Avoidances.** Do not hold the region lock across `runPool`, the probe, or `llmRouter.complete`. Do not hold the region lock across the Hello wait. Do not assume the probe gate holds the region lock. Do not add a mux channel or a pool reason. Do not replace `PoolChatServer`. Do not let two pool members of one user pass the child gate together. Do not fill the find field from a selection. Do not store more than 2000 matches. Do not run the search off the Swing thread. Do not let Esc close the reply window. Do not open the window, make the live POST, or send a pool turn from this filing.

**semantic-key:** PK-0490 PK-0491 PK-0492 PK-0493 PK-0494 PK-0495 PK-0496 PK-0497 PK-0498

## The first pool prompt of a held session sends the working set, and a later prompt sends only the tail.

A pool Run pastes the whole working set into a headless session that already keeps its own chat, so the column is read twice. The first request of a session carries that working set, and a later request carries only the tail, until the next app start. A full paste on the runs that need it is expected and accepted. Persisted sessions stay a later use case. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The desktop mints one boot id with the notebook and sends it on each pool execute. The wire still carries the full working set. Roux still takes the full list. The server decides what the child receives.

One session id stays on that SSH user's child while the hold is valid. The hold is valid when the boot id matches, the process is alive, and the next working set extends the text that session already holds. The first prompt of the hold calls `session/new` and sends the working set. A later prompt sends the tail, which is the working set with that held prefix removed. After a clean success the held prefix becomes the working set just sent, plus the stored reply in the same assistant role-line shape, when the reply is non-empty. Thought text stays out of the prefix.

A new session and the whole working set are used when the boot id changes, the boot field is empty, the child is new, the process was reaped or respawned, the previous prompt failed or was cancelled, the working set does not extend the hold, or the tail would be empty. That covers a re-run of the same card, an edit above the tail, a card higher in the column, another canvas, a same-group sibling, and a later card whose context includes a merge this session never saw. A downward jump across several unchanged cards stays a tail. `session/load` stays out. Abandoned session folders stay on disk. Tool refusals may remain inside a held session for its life.

A death before any forwarded chunk still retries once, on a new session, with the whole working set. A death after a chunk, a parse, a payload, a tool refusal, and an auth failure still do not retry, and they drop the hold. The five pool reasons stay. The child gate stays. The semaphore of 3 stays. The fifteen-minute idle reap stays. `num-ctx`, air-gap, and the Grok URL stay. The frame fit stays on the full working set.

The pool plan package 3 and its plan copy keep the once-per-prompt sentence. A sentence on each names this tail.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent.

One session id stays on the child and is dropped when the hold breaks. The boot id is sent from the notebook. The tail is sliced on the server, and the full working set stays on the wire. The once-per-prompt sentence stays, with the sentence that names this tail. The row is the build.

**Truth.** A valid hold sends the working set on `session/new`, then the tail. The wire and Roux still carry the full list. The hold breaks on a boot change, an empty boot, a new or reaped child, a failed or cancelled prompt, a working set that does not extend the hold, or an empty tail. A death before any forwarded chunk retries once with the whole working set. The idle reap stays fifteen minutes. The semaphore stays 3.

**Reason.** The headless session already keeps its own chat, so pasting the whole column on every Run makes the column be read twice. The tail is the working set minus the held prefix. After a clean success the prefix becomes that working set plus the stored reply, in the assistant role-line shape, only when the reply is non-empty. Thought text stays out of the prefix so it is not replayed as chat. A full paste remains expected when the hold cannot be proved. Persisted sessions stay a later use case, so `session/load` stays out and abandoned folders stay on disk.

**Avoidances.** Do not strip the full working set off the wire or off Roux. Do not put thought text in the held prefix. Do not use `session/load`. Do not treat a downward jump across unchanged cards as a broken hold. Do not retry a death after a chunk, a parse, a payload, a tool refusal, or an auth failure. Do not change the five pool reasons, the child gate, the semaphore of 3, or the fifteen-minute idle reap. Do not send a pool turn from this filing.

**semantic-key:** PK-0499 PK-0500 PK-0501 PK-0502 PK-0503 PK-0504 PK-0505

## SSH I/O logs the connect and the disconnect

Every SSH connect and disconnect on the host writes one INFO line. The password never appears.

`SshIoTrace` is package-private in `org.casserole.ssh.shell`. `SshServer.listenLoop` calls `SshIoTrace.attach` after the shell factory is set and before `sshd.start`. The password authenticator calls `SshIoTrace.authReject` when the account check returns false. The password is not a parameter of that call. Each line is INFO. Each line starts with `ssh-io`. The root logger is already INFO.

The events are `session-accept`, `session-open`, `authenticated`, `auth-reject`, `session-exception`, `session-disconnect`, `session-closed`, `channel-open`, `channel-open-failed`, and `channel-closed`. A trace fault in the listener logs `event=trace-fault` and does not throw. Key exchange stays quiet. Channel state changes stay quiet. Identification lines stay quiet.

Every line carries `event`, `session`, `user`, `remote-ip`, `remote-port`, `local-ip`, `local-port`, `client-version`, `server-version`, `iface-mac`, and `peer-mac`. A missing value is `absent`. The user is the session name, and on `auth-reject` it is the offered name. `iface-mac` is the hardware address of the server interface that owns the local socket. A wildcard bind, and a loopback with no hardware address, stay `absent`. `peer-mac` stays `absent`. The SSH session does not carry the peer hardware address. The clock starts at `session-open` and at channel init. `session-closed` and `channel-closed` carry `duration-ms`. `session-disconnect` carries the reason code, the reason text, the language, and initiator `local` or `peer`. `session-closed` repeats that disconnect tail. An exception line carries the exception class and a short text. A channel line carries `channel-id`, `channel-recipient`, `channel-class`, and `channel-type` when the TCP/IP channel exposes it.

The package methods are `attach` and `authReject`. The Swing client stays. The Rust client stays. The mux stays. Channel A stays. The command-line history stays. The model configuration stays. The log level stays.

**Truth.** Connect and disconnect events log at INFO as `ssh-io` lines with the fields above. A missing field is the word `absent`. `peer-mac` is always `absent`. The password is never a parameter and never a field. A trace fault logs `event=trace-fault` and does not throw. Key exchange, channel state, and identification stay quiet.

**Reason.** Connect and disconnect had no single INFO line that named the user, the sockets, the accepting interface, and how long the channel lived, so a failed login and a drop could not be traced without the password or the peer hardware address, neither of which the session has.

**Avoidances.** Do not log the password. Do not invent a peer hardware address. Do not throw from a trace fault. Do not log key exchange, channel state changes, or identification lines. Do not change the log level, the mux, channel A, or either client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the model configuration or the Rust manifest.

**semantic-key:** PK-0506 PK-0507 PK-0508 PK-0509 PK-0510

## The server stop keeps the shutdown cycle

Stopping the server holds the shell group open, settles the Windows accept, then drains five seconds before the group shuts down.

A stop had been leaving at once. The work was dropped on the floor. The failure was an IllegalStateException, Executor has been shut down, thrown on InnocuousThread. The stack was NoCloseExecutor, then the Windows accept failure, then the IOCP handler. Before that drop, a shutdown took about 5 to 10 seconds.

The listener signals the listen loop, calls sshd.stop, then cancels the listen task. QuietNioService waits one second for the Windows accept failure, then holds the group five seconds, then the stock close runs. The executor stays open through that wait. The private classes are QuietNioFactory and QuietNioService. The private methods are settleAccept, drainGroup, and pause. ACCEPT_SETTLE_MS is 1000. GROUP_GRACE_MS is 5000.

Lamina, Roux, and PoolChat stay. sshd stays 2.19.0. A stop on localhost with no client, using that same settle and drain, took 6010 ms and did not print the executor exception.

The server carries this. The clients do not. No new package, no new public type, no new public method, no new vault column, no new channel, no new shell message type, no new color key, and no new library are added. No system line is added, removed, or reordered.

**Truth.** A server stop signals the listen loop, stops sshd, and cancels the listen task, while the executor stays open. The accept is given 1000 ms to fail, the group is held 5000 ms, and then the stock close runs. A localhost stop with no client took 6010 ms and did not throw Executor has been shut down. sshd stays 2.19.0.

**Reason.** Closing the executor before the Windows accept and the IOCP handler finished made the stop throw and skip the drain. The one-second settle covers the accept failure. The five-second hold is the group drain that the old stop used to spend, which is why a quiet localhost stop lands near 6010 ms instead of returning at once. One sshd.stop is enough.

**Avoidances.** The executor is not shut before the wait. Lamina, Roux, and PoolChat are not removed. sshd is not upgraded. No wire line is added. The clients are not edited.

**semantic-key:** PK-0511 PK-0512 PK-0513

## Dependencies

PoolChatServer.canExtend sends the tail when the new working set starts with the held text. align stores that text. dropHold forgets it. session/new applies the porch rules. MoodDesk.porchRules is those rules. MoodDesk.seat still rewrites the Roux transcript. canvasSystems still builds the canvas triple. sshLine still builds the shell line. kept still drops an excluded card. A collapsed card stays in the working set. PoolMoodRulesTest gains the rule-hold cases. chrome.rs stays. grid.rs stays. SshClientApp stays. aa-sdd-source-prose.md stays. No new library is added. Cargo.toml is not edited. application.yaml stays.

The proof runs PoolMoodRulesTest, MoodDeskTest, and SshMoodMemoryTest, then compiles the product tree with tests skipped. No new JUnit class is added. The window stays closed.

**semantic-key:** PK-0530

## A turn echoes white, spins, writes a yellow reply, and Ctrl+C adds no messages.

1. Type, with white echo, then Enter.
2. The spinner is on the current line.
3. The reply is yellow, or a table, with carriage-return line feed. The prompt returns.
4. `status` shows the user and assistant counts increased.
5. Ctrl+C during the wait writes a red `^C` and adds no new messages.

**Truth.** White type, spinner, yellow or table, counts move, Ctrl+C adds nothing.

**Reason.** The turn is the path the formatting and the cancel rules exist to protect.

**Avoidances.** Do not append on Ctrl+C. Do not color the typed echo yellow.

**semantic-key:** PK-0533

## A stopped engine prints a red error with the message and the first stack frame, and the listener stays up.

The engine is stopped. Chat Enter prints a red error containing the message and the first stack frame. The listener stays up.

The error color is `ESC[31m`. The error writer adds a carriage-return line feed.

**Truth.** Red error, first frame, listener still up.

**Reason.** The listener is independent of the model process.

**Avoidances.** Do not tear down the listener because the engine is down. Do not hide the first frame.

**semantic-key:** PK-0534

## Providers stay behind the service, there is no model picker, and an empty reply is an error rather than a retry.

- Swap or add providers behind `LlmService`, other models from the same library, without changing the shell.
- A model-picker command versus configuration only. This capture is configuration only.
- Guardrails on the engine command line. Today any arguments that pass the metacharacter filter are allowed.
- Empty-reply handling is an error, not a retry.

The default model remains `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key.

**Truth.** Four model gaps. No picker. Empty reply is an error. The filter is the only guardrail.

**Reason.** The shell should not change when a second provider arrives.

**Avoidances.** Do not retry an empty reply. Do not add a picker in this capture. Do not bypass the metacharacter filter.

**semantic-key:** PK-0535

## The server file binds the shell and chat keys, and the demo context length 8192 is sent as num_ctx.

Bound to `CasseroleProperties` with prefix `casserole`, plus the model-library engine properties.

Shell block: port `2222`, a configured username, a configured password in plain text, and host key `hostkey.ser`. The username and password values are not published.

Chat block: `system-prompt` empty, `max-messages` 40, `escape-timeout-ms` 100, `max-paste-chars` 65536, `spinner` `ascii` with `braille` and `none` as the other names, `spinner-ms` 100, `smart-format` true, `table-style` `ascii` with `box` as the other name, `history-size` 500.

The unused datasource block: url `jdbc:h2:mem:casserole`, user name `sa`, password field empty, driver `org.h2.Driver`. JPA `ddl-auto` is `update`. This block is unused.

Engine block: `base-url` `http://localhost:11434`. `init.pull-model-strategy` `never`. Chat model `qwen2.5-coder:7b`. The same model is repeated under options. `options.num-ctx` is `8192`, the demo value sent as `num_ctx`. A context length of 0 omits the field and does not send `num_ctx`. The non-zero demo still sends 8192.

The child sections are the three key tables. No API key is present.

**Truth.** Port 2222, max messages 40, escape 100, paste cap 65536, spinner ascii at 100 ms, history 500, model `qwen2.5-coder:7b`, `num-ctx` 8192.

**Reason.** The file is the baseline the constraints promised to keep.

**Avoidances.** Do not publish the password. Do not send num_ctx when the value is 0. Do not treat the datasource as used.

**semantic-key:** PK-0536

## The shell builds a prompt, completes through the service, formats, and writes yellow or a self-colored table.

The path is:

1. `ChatShell.runLlmTurn`.
2. `ChatSession.buildPrompt`, with the max message count and the new user text.
3. `LlmService.complete`.
4. `SpringAiOllamaLlmService`.
5. `ChatModel.call`, blocking, one shot.
6. Extract `AssistantMessage.getText()`.
7. `FormatRouter.format`.
8. Yellow, or a table that colored itself, written to the terminal.

`LlmService` is the only model-library type the rest of the app is supposed to depend on. Commands do not call `ChatModel`.

Implementation today is non-streaming. No token callback. No thinking channel.

**Truth.** Blocking one-shot call. Format, then yellow or a self-colored table. No stream.

**Reason.** A single boundary lets a later path reuse prompt assembly.

**Avoidances.** Do not call the model from a command. Do not add a token callback in this capture.

**semantic-key:** PK-0537

## The prompt keeps the system message, takes the tail of non-system turns past the cap, and appends only after success.

On connect, a new `ChatSession` is created with:

1. The system message equal to `casserole.chat.system-prompt`.
2. `active` true, so the shell starts in `CHAT` and shows `chat>`.

Each turn:

1. Collect the system message.
2. Collect all non-system messages. If there are more than `max-messages`, take the tail.
3. Append the new user text, which is not yet stored.
4. Call the model.
5. On success only, append the user text and then the assistant text. A cancelled or failed turn does not grow history.

`message list` shows the full in-memory list, including the system message. The model only sees the capped prompt.

There is no token counting. The cap is message count, not tokens. That is a known gap for the vision that the product owns context-window management. The default cap is 40.

**Truth.** System plus the tail of non-system turns, then the new line. Persist only on success. List shows everything. Cap is 40 messages.

**Reason.** A failure must not become part of the next prompt.

**Avoidances.** Do not append on cancel or failure. Do not hide system from message list. Do not pretend the cap is tokens.

**semantic-key:** PK-0538

## Load posts keep_alive -1 with a 10 second connect timeout, and stop is a 30 second process.

Load, the commands `ollama load` and `ollama start`:

- HTTP `POST` to `{base-url}/api/generate`.
- Body: model set to the configured id, prompt a single space, `stream` false, `keep_alive` `-1`.
- Connect timeout 10 seconds. Request timeout 120 seconds.
- Pins the model in memory until stop, or until the engine's own policy.

Stop, the command `ollama stop`:

- `ProcessBuilder` arguments `ollama stop` and the model. No shell. Timeout 30 seconds.

Arbitrary command line, such as `ollama list` and `ollama ps`:

- The command name `ollama` matches the remaining tokens as arguments.
- Longest-name match means `ollama load` and `ollama stop` win over a bare `ollama`.
- `OllamaProcess.validate` rejects an empty token, `..`, and the characters `;`, `|`, `&`, `<`, `>`, newline, and carriage return.
- Combined standard output and standard error, capped at 65536 bytes.
- Quotes are not supported in the split.

The `ollama` binary must be on the server path. Do not use `ollama run`. It opens an interactive prompt and hangs. No API key. The base URL is `http://localhost:11434`.

**Truth.** POST generate, keep_alive -1, 10s connect, 120s request. Stop is 30s with no shell. Validate rejects metacharacters. Output cap 65536. No quotes.

**Reason.** An interactive runner hangs the listener. A shell string would accept metacharacters.

**Avoidances.** Do not call ollama run. Do not pass the line through a shell. Do not accept quotes. Do not raise the output cap past 65536 in this capture.

**semantic-key:** PK-0539

## The default is qwen2.5-coder:7b, switching is configuration only, and there is no download path inside the host.

The proof default is `qwen2.5-coder:7b` because it is small enough for local memory and behaves reasonably for a coding and operations assistant. Larger instruct models, for example the commented `cwc-mistral-nemo-12b:latest`, were discussed for cleaner prose and tables. Switching is configuration only.

No download-from-a-hub path exists in the host. Models are whatever the local engine already has. The engine is `http://localhost:11434`. There is no API key and no runtime select-model command in this capture.

**Truth.** Default `qwen2.5-coder:7b`. Yaml switches models. No hub download. No select-model command.

**Reason.** The default fits local memory. The host does not fetch weights.

**Avoidances.** Do not add a hub download. Do not treat the larger model as the default. Do not add a picker in this capture.

**semantic-key:** PK-0540

## The listener thread is casserole-sshd, it is not a daemon, and it starts from the runner rather than the constructor.

`org.casserole.ssh.shell.SshServer` extends `Thread`. The thread name is `casserole-sshd`. It is not a daemon.

- It does not start in the constructor. `InitRunner`, an application runner, calls `start()` after the context is up so the model and the properties exist.
- Shutdown on destroy sets `running` false, notifies, and stops the listener.
- The host key provider reads `Paths.get` of the host-key path, `hostkey.ser`.
- Default server setup, plus a password authenticator, plus `ChatShellFactory`.

Leftover classes `MinimalSshServer` and `SshServerTest` are not host beans. They are earlier echo and pseudo-terminal experiments with the same proof credentials. Ignore them for product behavior. Do not publish those credentials.

**Truth.** Non-daemon thread `casserole-sshd`. Start after the context is up. Host key `hostkey.ser`. Leftovers are not beans.

**Reason.** Starting in the constructor raced the model and the properties.

**Avoidances.** Do not start in the constructor. Do not register the leftovers as beans. Do not publish the proof password.

**semantic-key:** PK-0541

## Each connection gets a new session, a fresh command set, a format router, and a shell whose model name is injected once.

`ChatShellFactory.createShell`:

1. `new ChatSession` with the system prompt.
2. The command factory creates a fresh command set. Command objects are stateless. The session is on the context.
3. The format-router factory creates a router from the smart-format flag.
4. `new ChatShell`.

`modelName` is injected once from `spring.ai.ollama.chat.options.model`. The default fallback is `qwen2.5-coder:7b`.

**Truth.** Four steps. Model name injected once from the options property.

**Reason.** A shared command object would leak one connection's session into the next.

**Avoidances.** Do not reuse a command set across connections. Do not read the model name from the other property.

**semantic-key:** PK-0542

## The shell owns the reader, the three prompts, and the timers, and Enter in command mode hands the line to the processor.

`ChatShell` implements the listener command, `Runnable`, and the command context.

It owns:

- A UTF-8 reader on the channel.
- The line buffer, the Escape state machine, paste state, and the history cursor.
- Echo. Green in command mode. Default otherwise.
- Prompts `chat> `, `cmd> `, and `idle> `, each with a trailing space.
- The spinner timer, the escape timer, and the model executor.

It does not know command names. Enter in command mode hands the trimmed line to `CommandProcessor`.

The character pipeline, Escape, paste, history, the spinner, Ctrl+C, colors, width, and threads are the child sections.

**Truth.** Three prompts with trailing spaces. Green command echo. No command names inside the shell.

**Reason.** The shell is the line editor. The processor is the catalog.

**Avoidances.** Do not teach the shell the command catalog. Do not drop the trailing space on the prompts.

**semantic-key:** PK-0543 PK-0544

## The listener, the per-connection factory, the line editor, and the command processor are the server design.

The production listener is a thread started after the host is up. Each connection builds a session, a command set, a format router, and a shell. The shell owns the line editor and does not know command names. The processor matches the longest command. The child sections hold each of those rules.

Port 2222. Prompts `chat> `, `cmd> `, and `idle> `.

**Truth.** Listener, factory, editor, and commands. The shell does not know command names.

**Reason.** Command names live in the factory so the editor stays a line editor.

**Avoidances.** Do not start the listener in the constructor. Do not put command names inside the editor.

**semantic-key:** PK-0545 PK-0546

## Characters are handled under the shell lock, a line feed after a carriage return is dropped, and a sealed paste stays atomic.

Decoded characters go through `handleChar`, synchronized on the shell.

1. If an escape sequence is being collected, append until it is complete: a letter, `~`, or a two-character sequence that is not a control-sequence introducer or a single-shift.
2. Escape starts a sequence and schedules the timeout.
3. Ctrl+C, byte `0x03`, cancels a paste, cancels the model, or clears the line.
4. Carriage return is a paste newline or Enter. A line feed after a carriage return is dropped. A lone line feed is Enter.
5. Backspace or delete erases one glyph, or aborts a sealed paste.
6. A printable character is echoed and buffered, unless the paste is sealed. The blob is atomic until Enter, Ctrl+C, or Backspace.

Other control characters are ignored.

**Truth.** Lock on the shell. `0x03` cancels. CRLF drops the line feed. A sealed paste ignores further typing.

**Reason.** A line feed after carriage return would submit twice, and a sealed blob must not accept more typing.

**Avoidances.** Do not insert ignored controls. Do not echo into a sealed paste. Do not treat line feed after carriage return as a second Enter.

**semantic-key:** PK-0547

## Bare Escape waits 100 milliseconds, and bracketed paste plus the arrows are the sequences that are handled.

Bare Escape is the mode switch. Arrow keys and bracketed paste also start with Escape. The solution:

- On Escape, wait `escape-timeout-ms`, default 100.
- If more bytes arrive in time and form a complete control sequence, handle that sequence.
- If the buffer is still only Escape when the timer fires, treat it as bare Escape. Discard the unfinished line. Toggle `COMMAND` with `CHAT` or `IDLE`.

Handled sequences:

| Sequence | Meaning |
|---|---|
| `ESC[200~` | Bracketed paste start |
| `ESC[201~` | Bracketed paste end, then seal the blob |
| `ESC[A` or `ESC[OA` | History up |
| `ESC[B` or `ESC[OB` | History down |

Other control sequences are dropped. They are not inserted into the line. That is intentional. The shell is not a full emulator.

On start the shell writes `ESC[?2004h` to enable bracketed paste. On destroy or exit it writes `ESC[?2004l`.

**Truth.** 100 ms wait. Paste `200~` and `201~`. History `A`/`OA` and `B`/`OB`. Enable `ESC[?2004h`. Disable `ESC[?2004l`. Other sequences dropped.

**Reason.** Without the wait, a bare Escape and an arrow are the same first byte.

**Avoidances.** Do not insert unknown sequences. Do not treat an arrow as a mode switch. Do not leave bracketed paste enabled after exit.

**semantic-key:** PK-0548

## The spinner paints an emerald frame and a cyan thinking word on the current line, and stop erases it.

While the turn is in flight:

- The timer paints a carriage return, an emerald `#00FF66` frame, a space, the word `thinking` in `#00D7FF`, and erase to end of line.
- ASCII frames are `|`, `/`, `-`, and `\`.
- Braille starts at U+280B and cycles. It needs a font with those glyphs. Consolas does not have them.
- Stop is a carriage return plus erase, so the spinner does not become a committed line.

An earlier bug wrote `thinking...` as a normal line and then could not overwrite it. The carriage return plus erase design is required.

`none` skips animation. The frame interval is `spinner-ms`, default 100, clamped from 40 to 500.

This capture's word color is `#00D7FF`. A later notebook spinner uses a different blue. Do not merge them here.

**Truth.** Glyph `#00FF66`. Word `thinking` in `#00D7FF`. Frames `| / - \`. Stop erases. `none` skips.

**Reason.** A committed thinking line could not be overwritten and became part of the scrollback.

**Avoidances.** Do not write a committed thinking line. Do not use braille as the default. Do not replace `#00D7FF` with a later blue inside this capture.

**semantic-key:** PK-0549 PK-0550

## The engine base is localhost port 11434, pull is never, and options.model is what the shell injects.

| Key | Default | Role |
|---|---|---|
| `spring.ai.ollama.base-url` | `http://localhost:11434` | Injected into the load command. |
| `spring.ai.ollama.init.pull-model-strategy` | `never` | Do not pull models at startup. The operator pulls them. |
| `spring.ai.ollama.chat.model` | `qwen2.5-coder:7b` | The model-library property. Also duplicated under `options.model`. |
| `spring.ai.ollama.chat.options.model` | `qwen2.5-coder:7b` | This is what `ChatShellFactory` injects as `modelName` for `status`, `ollama load`, and `ollama stop`. |
| `spring.ai.ollama.chat.options.num-ctx` | `8192` | Demo value sent as `options.num_ctx`. A value of 0 omits the field. |

A commented alternative in the file is `cwc-mistral-nemo-12b:latest`. It is not the default. Switching models is a file or environment change. There is no runtime `select model` command yet.

`ChatShellFactory` falls back to `qwen2.5-coder:7b` if the property is missing. No API key.

**Truth.** Base `http://localhost:11434`. Pull never. Both model keys are `qwen2.5-coder:7b`. The options key is the one the shell uses. Demo context 8192.

**Reason.** Startup must not download a model. The shell has to show the same id that load and stop use.

**Avoidances.** Do not pull at startup. Do not treat the commented model as the default. Do not invent a select-model command in this capture. Do not send num_ctx when the value is 0.

**semantic-key:** PK-0551

## Message list truncates at 120 characters unless the count is 0 or the command says verbose.

Approved.

| Item | Lock |
|---|---|
| Configuration | `casserole.chat.list-preview-chars: 120`. A value of `0` means never truncate. |
| Default | `message list`, `message list` with a filter, and `message list meta` are terse. Truncate and append `…`. |
| Full text | `message list verbose`, and `verbose user`, and `meta verbose`. |
| Parse | A flag on the existing command, the same style as `meta`. |
| Truncate | A count of Java `char` values. The text is already unmarshalled. |

**Truth.** Default 120. Zero means never truncate. Meta is terse too. Verbose is the full text.

**Reason.** A full card on every list row buried the sequence the operator was scanning.

**Avoidances.** Do not leave meta verbose by accident. Do not truncate verbose. Do not use a byte count.

**semantic-key:** PK-0552

## The system prompt, the live model, and spill settings stay process-wide.

Still process-wide: the system prompt for new regions, the live local-engine model, and durability, spill, and page size.

There is no per-user model and no per-user system prompt in this slice. The engine is `http://localhost:11434`. The code model default is `qwen2.5-coder:7b`.

**Truth.** Prompt, model, and spill stay one process. Accounts do not each carry a model.

**Reason.** The out-list already refused a model or prompt per user.

**Avoidances.** Do not store a model or a system prompt on the account row.

**semantic-key:** PK-0553

## The header follows the spinner, the prompt is medium blue, and find-by is an argument of the existing find.

Edit:

- `Ansi` gains dim gray, a brighter gray, medium blue at about 32 or 33, `kv` with a green key and a hot-pink value, and box-drawing helpers.
- `ChatShell.runLlmTurn`, after `stopSpinner`, writes a two-row header at `terminalColumns`, default 80, then the existing reply color. Cancel, empty, or error: no header. Commands: no header. Truncate the model if the label is longer than `columns - 4`. Pad `─` to the width. The timestamp is `yyyy-MM-dd HH:mm:ss`, local to the JVM, at write time. The model is the live `modelName`. The example model string used by the design is `qwen2.5-coder:7b`.
- `ChatShell.promptText`. Command mode is a medium-blue `cmd> `. Typed characters stay green.
- The line editor on `ChatShell`. `editCursor`. Insert at the cursor. Backspace deletes left. CSI `3~` deletes at the cursor. CSI `C`, `D`, `H`, `F`, `1~`, and `4~`. Ctrl+A and Ctrl+E. After a change, `redrawInputLine()` plus N backspaces. A sealed paste is unchanged. Up and Down still land at the end of the line unless the operator moves.
- `StatusCommand`. Each `key: value` goes through `Ansi.kv`. No yellow wrap.
- `MessageListCommand`. Parse `verbose` the way `meta` is parsed. Default truncate to `list-preview-chars`. `0` means full. `message list meta` is terse too.
- `MessageFindCommand`. If the arguments start with `by`, the remainder is the needle and one quote pair is stripped. Case-insensitive contains. Print every hit terse, `[seq] ROLE: preview`. Otherwise the existing high id and low id. Bad usage lists both spellings.
- `HelpCommand` one-liners pick up the new help strings automatically.

Done: compile. A plain terminal hand check covers the reply box, Escape to a blue `cmd>`, `status` colors, a truncated `message list`, a full `verbose`, `message find by`, and left and right editing.

**Truth.** Header after the spinner, blue `cmd>`, green typed text, editor sequences, status colors, terse list, find-by.

**Reason.** These are the server half of the locked design, and they do not need the window.

**Avoidances.** Do not banner a failure. Do not yellow-wrap status. Do not drop the id form of find. Do not append when the cursor is mid-line.

**semantic-key:** PK-0554

## The header is written after the spinner stops, and a failed turn has no header.

- The spinner sits on the prompt line, carriage return plus the frame plus erase-to-end. The header comes after `stopSpinner`, or it overwrites the thinking line.
- A resize mid-session uses `terminalColumns` at write time. That width is already parsed from the terminal environment. If the column count never arrived, use 80.
- A very long model id truncates the model. Never wrap the box onto a third line into the reply.
- A failed, empty, or cancelled turn has no header, because nothing was received.
- Command output, including `status` and `message list`, has no header.
- The timestamp is server-local. Two hosts will disagree. The proof is one host.

**Truth.** Six seals: after the spinner, width at write time, no third line, no header on failure, no header on commands, one host's clock.

**Reason.** Writing the box too early erases the thinking line, and wrapping it corrupts the reply.

**Avoidances.** Do not write the header before the spinner stops. Do not banner a failure. Do not assume two hosts share a clock.

**semantic-key:** PK-0555

## After a successful complete, and after the spinner stops, a dim-gray box is written at the live column width.

After a successful model complete, after `stopSpinner`, and before the body, write a dim-gray box-drawing header, then the existing body, then `chat>`.

Width is the current `terminalColumns`. The default is 80. Dim gray is `ESC[90m`, or 256-color about 245, for the drawing. Inner text is a slightly brighter gray. Then reset.

The box the operator sees is two rows. The first row opens with `┌`, a space-dash pair, the stamp, a middle dot, the model id, dashes, and `┐`. The second row is `└`, dashes, and `┘`. Both rows are exactly `terminalColumns` wide. The sample stamp is `2026-08-26 14:32:05`. The sample model is `qwen2.5-coder:7b`. Under the box is the formatted reply, the same yellow and smart-format as today, then `chat>`.

| Item | Lock |
|---|---|
| When | The message was received. The wall clock is the moment the assistant reply is about to be written. That clock is the host JVM's local time, not a frame timestamp, and not the client clock. |
| Stamp format | `yyyy-MM-dd HH:mm:ss`. No zone. This is a proof format. |
| Model | The live model id already on the shell. A load updates it. The same string `status` uses. Do not invent `qwen_coder`. |
| Glyphs | Box drawing `┌ ┐ └ ┘ ─ ·`. If the inner label is longer than `columns - 4`, truncate the model and keep the timestamp. Pad `─` so both rows are exactly `terminalColumns`. |
| Fallback | If ASCII is required: `+-- 2026-08-26 14:32:05 · model --+` and a second row of `+` and dashes. The terminal font has the box glyphs. The tofu risk is the same as the braille spinner. |
| Who | Model replies only. Commands are unbannered. |
| Body | Unchanged. The existing reply color and format router still run. The chrome is not wrapped in yellow. |

**Truth.** Dim gray `ESC[90m`, width `terminalColumns` default 80, stamp `yyyy-MM-dd HH:mm:ss`, model truncated first, both rows exact.

**Reason.** The header is receipt chrome. It is not part of the yellow body.

**Avoidances.** Do not use a frame timestamp or the client clock. Do not wrap the box onto a third line. Do not truncate the timestamp. Do not yellow-wrap the chrome.

**semantic-key:** PK-0556

## Today the shell stops the spinner, writes the yellow assistant blob, and then writes chat>.

`ChatShell.runLlmTurn` stops the spinner, writes the yellow-formatted assistant blob, then `chat>`.

**Truth.** The current path is spinner, yellow body, then the chat prompt.

**Reason.** The header has to be inserted into that path without becoming part of the yellow body.

**Avoidances.** Do not leave the header out of the description of today. Today has no header.

**semantic-key:** PK-0557

## A blank host keeps the local child

A blank host keeps the local child. A set host dials grok agent serve.

The settings are `casserole.pool.host`, `casserole.pool.port`, and `casserole.pool.secret`. A blank host starts `grok agent stdio`. On Windows that start stays `cmd.exe /c grok agent stdio`. A set host opens one WebSocket at `ws://host:port/ws`. A port below 1 or above 65535 uses 2419. A blank host ignores the port. An IPv6 host is wrapped in brackets. A host that already starts with `[` stays. The serve token rides on the Authorization header as Bearer plus the token. A blank token sends no header. A token that contains a carriage return or a line feed is empty. The token is not placed in the URL. The token is not written to the log. The JSON-RPC lines stay the lines the local child already uses. The seat is one seat, a process or a WebSocket. The facade builds that seat from the properties. The address check does not open a socket. The Rust client does not dial, so it does not read the host, the port, or the token.

The address is PoolAddress. The link is PoolLink. The contract is PoolChat. The server is PoolChatServer.

**Truth.** A blank host keeps the local `grok agent stdio` child. A set host dials `grok agent serve` at `ws://host:port/ws`. A port below 1 or above 65535 uses 2419. The token is an Authorization header, or no header when the token is blank. The token is not in the URL.

**Reason.** The pool seat was only a local child. A host names a serve process on another machine. The same JSON lines cross that socket. The Rust client does not open the seat.

**Avoidances.** A blank host does not open a socket. The token is not placed in the URL. The token is not written to the log. A token that contains a carriage return or a line feed is not sent. The spawn does not gain `--always-approve`. The Rust client does not read the host, the port, or the token. The address check does not open a socket.

**semantic-key:** PK-8252
