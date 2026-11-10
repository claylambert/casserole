# org.casserole.llm

Contract file. Two sides match this component.

**Depends:** [java/org/casserole/config/config.spec.md](../config/config.spec.md)

# A blank API session tries once more

The stamp names the build. An API session that returns no text tries once more. The empty session is the seat. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0521 PK-0522

# A blank API session tries once more

Enter on the SSH console failed as grok empty content from GrokLlmService.completeStream. The same send, recalled and sent again, worked. A retry covers an API session that comes back empty like that. Java and Rust both use that server session. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-0531 PK-0532

# Providers stay behind the service, there is no model picker, and an empty reply is an error rather than a retry.

- Swap or add providers behind `LlmService`, other models from the same library, without changing the shell.
- A model-picker command versus configuration only. This capture is configuration only.
- Guardrails on the engine command line. Today any arguments that pass the metacharacter filter are allowed.
- Empty-reply handling is an error, not a retry.

The default model remains `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key.

**Truth.** Four model gaps. No picker. Empty reply is an error. The filter is the only guardrail.

**Reason.** The shell should not change when a second provider arrives.

**Avoidances.** Do not retry an empty reply. Do not add a picker in this capture. Do not bypass the metacharacter filter.


**semantic-key:** PK-0535

# The shell builds a prompt, completes through the service, formats, and writes yellow or a self-colored table.

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

# Model door

This page builds the provider door. The map is [program-spine.md](../../aetheris/config/config.spec.md).


**semantic-key:** PK-0561 PK-0562

## A cloud model carries `burner_cloud`, factory false, and an unchecked box refuses a burner hop with `burner denied`.

The checkbox lives in the model settings. That modal is the model settings dialog on the model serial. The burner pin on the card stays the mark that the turn is ephemeral.

The checkbox is specific to that model, and the split is local against cloud. A local model still receives a burner cell. The checkbox is not on that path. A cloud model carries the checkbox. The document key is `burner_cloud`. The factory value is false.

Unchecked refuses a burner hop to that cloud model. The cell stores `FAILED`. The status line shows `burner denied`. The frame is not sent. Checked is the user's allow. The shell reads the box and sends the frame.

The checkbox is not a body field. Air-gap still refuses a cloud target on its own. `notebook.burner-key` stays the server door. This checkbox does not write that key. The tree still refuses every burner cloud hop. This row does not write the client.

A later note on the model serial names an exception: a normal `grok/` cell may POST while air-gap stays `true`. This checkbox still does not write the air-gap word. The tree still stops a `grok/` target on `air-gap`.

**Truth.** `burner_cloud` is a per-cloud-model checkbox in the model settings, factory false. Unchecked stores `FAILED`, shows `burner denied`, and does not send the frame. Checked is the allow, and the shell sends the frame. The card burner pin stays the ephemeral mark. The tree still refuses every burner cloud hop, and it still stops a `grok/` target on `air-gap`.

**Reason.** A local model can take a burner cell without this box. A cloud hop needs an explicit allow because the factory is false. The box is not a body field and does not write `notebook.burner-key` or the air-gap word, so those doors stay independent. The serial's exception, that a normal `grok/` cell may POST while air-gap stays `true`, is not applied by this checkbox.

**Avoidances.** Do not put the checkbox on the local-model path. Do not send the frame when the box is unchecked. Do not write `notebook.burner-key`. Do not write the air-gap word. Do not treat the card burner pin as this checkbox. Do not lift the tree's refusal of a burner cloud hop in this row.

**semantic-key:** PK-5661

## Every readable card opens one Model Config modal, writes that cell at once, and shows the chosen model on one label line.

The card used to keep model configuration on the group bar, and that bar starts hidden. A twelve-column field stores one execution target. An empty field means the local service and uses the configured local service. The word `ollama` is not written back. `grok/` plus a model id is Grok. The `Grok` button was shown only when the provider is grok. It opened the `Grok settings` dialog. OK wrote that dialog. Cancel wrote nothing. A first change into that provider with no document copied the nearest earlier cell, else the standing default, else opened the dialog, and a close without OK put the prior target back. The think checkbox sat on that bar and was shown only when the provider was local. The map is session memory keyed by cell id. On sends `think` true for that cell. Off leaves `think` unset. The tip that said the on value is for this session is the sentence this modal corrects. It is not a vault column. Group, Join, and Ungroup share that bar. The main bar did not name the model. The model serial uses one thinking control in its earlier sentence. The tree already splits the call. A local run reads this map. A cloud run reads that cell's stream flag into the same execute field. This rule keeps the split and gives each model its own Thinking checkbox for this cell. The serial stays the operative reading of the chat call. This rule is the operative reading of the modal. The configuration moves into one modal on the cell. The cell shows the choice as it is made.

One button sits on the main bar of every readable card. The title is `Model`. It stays `Model`.

The click opens a modal for this cell. The title is `Model Config`. The model is selected in the modal. The options for that model are defined in the modal. The card does not hold those controls.

The first control in the modal is a pull-down of model families. The rows start with Ollama and Grok. A later model is a new row. Ollama is the default. A new cell, a cleared canvas, and an empty target open on Ollama.

Under the pull-down, the modal shows only the options that belong to the selected model. Ollama options do not show when Grok is selected. Grok options do not show when Ollama is selected. The Thinking checkbox on screen is the selected model's checkbox for this cell.

A change in the modal writes this cell at once. A combo, a checkbox, and the family pull-down are that change. A number field becomes that change when it loses focus and the text parses. A target change updates the stored target, the cell label, and the group-bar field. A Thinking change updates this cell's thinking value and leaves the target string as it is. The cell does not wait for the modal to close. The cell does not wait for Run. Closing the modal leaves the cell as last written. The dialog's wait for OK, and the restore of the prior target when that first open closes without OK, leave with the `Grok` button.

The cell shows the selected model on one label line under the main bar and above the body. The height is one line plus the header's small vertical pad. The ink is the card's ordinary label ink. It is a label. It is not a field, not a vault column, and not the status line. A local cell with no model id reads `ollama`. A local cell with a model id reads that id. A cloud cell reads the model id after `grok/`. A cloud cell with no model id reads `grok`. A model that is not selected does not appear on the cell. The status line still prints `engine:`.

Local options are the model id, the Thinking checkbox, and the effort. The model id is a field. An empty id stores an empty execution target. The configured local service stays the call. A chosen id stores `ollama/` plus that id. The tree does not read an `ollama/` suffix until the build is named. The Thinking checkbox is on or off for this cell. The factory is off. On sends `think` true for this cell. Off leaves `think` unset. The map stays session memory keyed by cell id. A second cell keeps its own value. Effort does not replace the checkbox. Effort is the level. It shows when the checkbox is on and that model's `thinking.values` from `POST /api/show` lists strings. A chosen string is what `think` sends. The published `low`, `medium`, and `high` example is the docs example. It is not this host. When the model lists only booleans, the checkbox is the control and the effort rows stay hidden. Turning the box off leaves `think` unset and keeps the chosen level in this cell's session memory, so the next on sends that level again. The checkbox off does not send `false`. This set does not show the cloud chat API, `reasoning_effort`, stream, temperature, max tokens, or `burner_cloud`. `num-ctx` stays the server window. The thinking strip still paints thinking. This filing does not call the host. The value is not a new vault column.

Cloud options are the options the settings dialog already writes, plus the Thinking checkbox for this cell. API is `chat` only. Model is the text-model table and writes `grok/` plus the id. For this app, effort is always explicitly defined in the config. An undefined effort can waste tokens. The control lists the legal levels for the selected model and always holds one of them. The value is stored on this cell. Every cloud call for this cell sends `reasoning_effort` with that defined level. The dialog in the tree can leave the effort unset, and the body then leaves the field off. That path is the code this rule adjusts. The factory level is `high` where that is the model's published start. Where the model has no published start, the factory level is `high` as well, so the config is defined. Streaming is `false` or `true`. Temperature stays. Max tokens stays. The factory values stay 1 and 128000. The probe still uses 16, and the probe sends this cell's `reasoning_effort`. The `burner_cloud` checkbox stays. The factory value is false. The Thinking checkbox is this cell's thinking on or off. The factory is off. The checkbox does not remove `reasoning_effort`. The effort control stays on the pane when the checkbox is off. The Streaming pull-down stays the stream flag. The Thinking checkbox does not set `stream`. When the pull-down selects Grok and the cell has no document, the modal fills from the nearest earlier cell on that canvas, else the standing default, and writes that cell at once. A copied effort that is not a defined level is written as the factory level before that write. The copied document copies that cell's thinking value with it. With neither, the cloud options show, Thinking is off, Effort is the factory level, and the cell shows Grok with no model id until one is chosen.

The `Grok` button leaves the card. The group-bar think checkbox leaves the card. The modal holds the Thinking checkbox. The group bar keeps the free-text field, Group, Join, and Ungroup. The group-bar toggle and the free-text execution target stay. The field writes the same string. A keystroke updates the cell label at once.

The build turn puts a tip on `Model`. The tip says the button opens the Model Config modal for this cell, the pull-down chooses the model, the modal shows only that model's options, the Thinking checkbox sets thinking on or off for this cell and this model, Ollama is the default, and a change updates this cell at once. The checkbox carries a tip in that same turn. The local tip says on asks this cell to think and off leaves `think` unset. The cloud tip says the checkbox is on or off for this cell, and that the checkbox leaves `reasoning_effort` on the call. The effort control carries a tip in that same turn. The tip says the config always defines the level, and that level is sent as `reasoning_effort` on every cloud call for this cell. An undefined effort can waste tokens. The label carries a tip in that same turn. The tip says the line is the model this turn will call. The label stays the model line.

An unreadable card keeps the load-error header. It has no `Model` button and no label. A collapsed card keeps both. The label adds one line. A grouped member grows by the button. The label stays inside the card width. The member row scrolls. The column width stays the canvas width. The canvas sideways bar stays off.

The merge shows the same button and the same label. Run on a group still sends each member. Each member sends that member's own thinking value. A cloud member sends that member's `reasoning_effort`. The merge is still not sent. The merge checkbox stays out of the run.

No new vault column. The locked list of host models plus `grok/<model>` stays off the card. The pull-down is model families. It is not that list. `select model` and `list models` stay tabled. Choosing Grok does not write the air-gap word. Both air-gap doors stay.

The feature uses the doors that are already there. The named build follows this rule. The model serial, the plan, and the pre-plan stay the earlier reading. This filing does not edit them.

One modal. `openGrokDialog` becomes the Model Config modal. The title is `Model Config`. There is no second dialog. OK and Cancel leave with the `Grok` button. Close leaves the cell as last written. There is no restore of the prior target. The single open flag stays. The family pull-down is the first control. One pane shows. The cloud pane is the form that dialog already builds, plus the Thinking checkbox. The local pane is the model id, the Thinking checkbox, and the effort. The model-table fill stays. The `Grok` button is not added. The group-bar think checkbox is not added.

One stored string. A modal change writes the cell execution target, the group-bar field, and the label. That write persists the way the dialog's OK persists today. A keystroke in the field updates the cell in memory and the label at once. It does not persist on each character. The next run already reads the cell object. Typing a cloud target adopts the document, nearest earlier cell, else the standing default, else the factory, and does not open a second dialog. A stored provider that is neither local nor that cloud chat stays as written. Opening the modal does not rewrite it. A pull-down selection then writes the chosen family.

The label sits in the header, under the main bar, and above the body. It is not a child of the top header row. It is not a child of the group bar. The group bar still starts hidden, and the label stays visible while that bar is hidden. A collapsed card keeps the brief on the button row. The label is the line under that row. Member width still measures the top header row, so the member grows by the `Model` button and does not grow by the label text. The member row grows with the card. The column width stays the canvas width. The member scroller still owns the overflow. The canvas sideways bar stays off. The unreadable card returns before the button and the label. The engine word and the JOIN banner stay the shown target. An empty target still prints `ollama` there. The label uses the short rule above. Do not point JOIN at the short rule.

One effort table. The effort rows and the effort start move next to the model table on the settings document. The dialog, the load, and the copy call that table. The service does not grow a model table. The unset token leaves the rows. `grok-4.7` and `grok-4.6` list `low`, `medium`, `high`, and `xhigh`, and start at `high`. `grok-4.5` lists `low`, `medium`, and `high`, and starts at `high`. `grok-4.20-multi-agent-0309` keeps those four levels and starts at `high`. Every other id lists `high` and starts at `high`. Filling effort keeps the current level when it is still a row, and otherwise selects the start. Empty and the unset token normalize to that start. The entry factory effort is `high`. Load and copy run the normalizer, and the next save stores `high`. Version stays `1`. A blocked file stays unread and is not overwritten. A cloud pane with no document does not select `grok-4.3` and does not write that id onto the target. The effort row is `high` until a model is chosen. The entry model stays `grok-4.3` only as the factory id inside a document that already has a model. The first time a cell gains a model id, and the standing default is empty, that entry becomes the standing default, matching today's first confirm. The new entry's model is empty until a row is chosen. A stored `grok-4.3` already on a document stays. A later edit writes the cell only. A pane with no model id does not become the standing default.

The cloud body always sends the level. Assembly copies the normalized effort for a cloud cell. The execute encoding writes `reasoning_effort` when the value is non-empty. It does not drop the unset token. The request body always puts the field. An empty value is `high` before the JSON is built. That last door does not invent a catalog. The probe body takes this cell's level and puts it on the Hello body. The probe start and the admit gate take that level. The gate stays one Hello for the server process. A later cloud cell does not probe again. Its own call sends its own level. The probe still leaves temperature off and still uses `max_completion_tokens` 16. A local frame does not carry `reasoning_effort`.

The local id uses the call that already exists. The router reads the id after `ollama/` and passes it into the local chat service on the same options object the think path already builds. The call sets the model on that instance. The library stays 1.1.8. This rule does not follow a later branch that removes those setters. An empty target and a bare `ollama` do not set the model. The boolean on complete becomes the think string. Empty means off. `true` means the checkbox. Any other value is the level. There is no new parameter beside it. The configured model stays. The shell model-name swap is not called. The options object is built when a model id is present, including when thinking is off and `num-ctx` is unset. An unset `num-ctx` omits that field. A configured `num-ctx` still rides. There is no second local chat client. The call stays inside the memory hold that already wraps complete.

One session holder. The holder widens in place. The value holds this cell's local on or off, the local level, and the cloud on or off. A removed cell still drops the holder. Two families on one cell keep both values. Assembly does not put local thinking on `think_stream`. `think_stream` stays the cloud stream flag. The execute frame gains the optional key `think`. The key is absent when local thinking is off. The key is `true` when it is on and no level is chosen. The key is the level string when it is on and a level is chosen. Off does not send `false`. The server reads `think` only when the provider is local. Boolean on stays the enabled think boolean. A level of `low`, `medium`, or `high` is a think level of that string. Off leaves the option unset. The disabled think boolean is not sent. The holder keeps the level when the box is turned off, and the next on sends it again. This key is not a vault column. Client and server ship in the same build. A saved turn has no think flag to migrate.

The local effort rows ask the server that already holds the local base URL. The modal does not open a socket to the local service, and it does not read that URL. The client writes one channel A data frame. The body starts with `show:` and the model id. The handler sees that first line before the execute slot, before the in-flight mark, and before the memory hold. It does not enter execute. It does not mark the cell busy, failed, or running. A run already in flight on that cell stays in flight. The server posts `{base}/api/show` with the JSON library, body `{"model": id}`, connect 10 seconds, read 10 seconds. The base is the URL already injected for the local service, with the trailing slash trimmed the way the load command trims it. This is not the load command, not `/api/generate`, and not a terminal command. The reply is channel A data for that same cell id. The body starts with `show_result:` and the model id, then one `value:` line per kept string. A miss is `show_result:` and no values. It is not the error type and it is not the stream channel, so the cell answer path and the strip never see it. The client applies the reply only while the modal is open on that cell and the id still matches. A stale reply is dropped. No session means the client does not send, and the rows stay hidden. There is no run banner. The reader reads the top-level `thinking.values`. Booleans are dropped. A string is kept only when it is `low`, `medium`, or `high`, in the order returned. The think-level type on library 1.1.8 throws on any other string. This rule does not upgrade that library and does not open a chat client to pass a foreign string. `thinking.default` is not applied. The cell's stored level stays selected when it is still a row. Otherwise the effort pull-down has no selection until the user picks one. The checkbox still works. A missing `thinking` object, an empty list, a boolean-only list, or an HTTP miss hides the rows. The ask runs when the id field loses focus, and when the checkbox is turned on, and only when the id is non-empty. It does not run on each character. It does not run on the interface thread.

The cloud Thinking checkbox is the holder flag for this cell. It does not set `stream`. It does not remove `reasoning_effort`. Kind `t` paints the thinking strip only when that flag is on. When the flag is off, kind `t` is dropped and the reply still paints. Stream false still returns one reply, so the strip stays hidden. The checkbox does not bound token use. The effort level does. A copied cloud document copies the source cell's cloud flag from the holder. The thinking strip stays the strip. The strip is not given a second widget.

The landing check, when the build is named, accepts a compile and does not run the test suite as the proof. The window is not opened unless asked. The live POST is not made. The `Model` button is on the main bar. The label is one line under it. The group bar still starts hidden and still holds the field, Group, Join, and Ungroup. A grouped member grows by the button. The label stays inside the card. The column stays the canvas width. The canvas sideways bar stays off. An unreadable card has neither control. The tips above are on the controls that are built in that turn.

The stored strings are fixed. Selecting Ollama with an empty id writes an empty target. It does not write the word `ollama` and it does not write `ollama/`. Selecting Ollama with an id writes `ollama/` plus the trimmed id. Selecting Grok when the document has a model writes `grok/` plus that id. Selecting Grok with no model writes `grok/` and the label reads `grok`. Run on that target is `no-model` before air-gap, before the key, and before `Hello`. That order stays. A typed `ollama/` or a typed `ollama` normalizes to an empty target when the field loses focus. During the keystrokes the label follows the short rule. A provider that is neither local nor that cloud chat leaves the family pull-down with no row selected and both panes hidden until the user picks a row. Opening the modal does not rewrite that target.

Each family keeps its store. Moving to Ollama does not forget the cell's cloud document. Moving to Grok does not forget the local id or the holder. Coming back writes the target from the store that cell already has. Adopting a cloud document still returns when the cell already has a document. It does not copy the nearest cell over that document. A modal write updates the field and the label in place. It does not rebuild the tabs. The group bar's open state stays. Collapse stays. Temperature writes on focus loss when the text is a finite double. Max tokens writes on focus loss when the text is an int of 1 or more. A bad number leaves the last good value and the status line says `settings`. The local id updates the cell in memory and the label on each change, and it persists on focus loss. The card's burner checkbox stays the ephemeral flag. The pane's burner checkbox stays `burner_cloud`. They are not the same box. A change on the merge writes the merge cell only. Members are not copied. Run still skips the merge. The mood shaper stays its own execute. It does not open this modal and it does not gain a `think` key.

The holder fields are the local on flag, the local level, and the cloud on flag, keyed by cell id. A missing key means off and no level. The cloud flag is session memory. A shell restart clears it. Effort, stream, temperature, max tokens, burner, and the model stay in the cloud document. The flag does not gain a document key. Kind `t` is dropped while painting a thought delta when the cloud flag is off. The server still sends the delta when stream is true. The effort level is the token bound.

The ask is closed. No call is open. The rules above are the row. The model serial, the model plan, and the model pre-plan stay the earlier reading. A pre-plan was named, then approved. The plan stamp names the build. Until that stamp, code waits. The stamp is the name of the build.

Thinking is on or off for the selected model on this cell. It is per cell. It is not a global option. The sentence that effort replaces the think checkbox is replaced. The tip that says the on value is for this session is the sentence the modal corrects. The map is already keyed by cell id. Local thinking stays in the session map. Cloud reasoning stays on that cell's settings document. No new vault column. The execute frame gains the optional key `think` for the local level. The cloud call does not use that key. Local on sends `think` for this cell. Local off leaves `think` unset. Every cloud call sends this cell's `reasoning_effort`. The Thinking checkbox does not remove that field. Neither checkbox sets `stream`. Neither checkbox enters the handler. The execute field stays the provider branch the tree already has. One checkbox, titled `Thinking`, sits in the selected model's options, and the cloud pane keeps Effort beside it. The tip says on or off for this cell and this model. Two cells are two values, and two families on one cell are two values. On changes the wire. Local off does not send `false`. The config includes an explicit level. The call sends it. The token use is that level. The config always defines effort. An undefined effort can waste tokens.

The fit uses one modal, one effort table, one session holder, and the local options object that already exists. It does not add a dialog, a chat client, or a shell model swap. The cut lands in this order: the table, the modal, the holder, the local id, the probe level, then the landing check. One stored string crosses the field, the label, and the modal. `think` is the local side of the frame the server already accepts. `think_stream` stays the cloud stream flag. An empty cloud effort is `high` before the body is built, and the call is not skipped. The show post stays on the server that already holds the base URL. The interface process does not open that socket. There is no new channel and no terminal command. The `show:` branch sits before the execute slot, so a run in flight stays in flight and a stale reply dies when the id does not match. A show result stays off the cell answer and off the strip. A family switch keeps both stores. The think level accepts `low`, `medium`, and `high`. A foreign string is not a row. `grok/` with no id is `no-model` before air-gap. A provider that is neither family does not move the pull-down. No extra cut is taken. The thinking strip stays. The earlier card and group rules stay. The checkbox is this cell's door. The lock shut the ask. The later stamp names the build.

The handshake handler stays where it is. It runs when the stored target is `grok/` and the user presses Run. Opening the modal does not probe. A change in the modal is not a cloud POST and does not enter the handler. The local effort rows ask the server with `POST /api/show` through the `show:` frame. That ask sends no cell prompt. The reply is not a cell answer. Flipping Thinking does not probe. A cell on the local family does not enter the handler. Provider `grok` with an empty model is `no-model` before the key is read and before `Hello`. The probe model is the id on that cell. The body is one user message `Hello`, stream false, `max_completion_tokens` 16, and this cell's `reasoning_effort`. An empty effort is normalized to the factory level before the body is built. The call sends that level. The call is not skipped for effort. The greeting is discarded. One `Hello` for the server process. Valid true matches that key. A later cloud Run in that process skips `Hello` and sends the cell prompt with this cell's `reasoning_effort`. A miss stores `FAILED` and does not send the prompt. A `401` clears valid. A server restart clears it. A shell restart does not. Air-gap stays two doors. `burner_cloud` still rides the cloud frame. It does not ride a local frame. A group Run handshakes each member on that member's own target and that member's own thinking value. A member left on the local family stays out. The merge label does not enter the handler.

The button, the group-bar field, and the label are one stored string. The handler follows that string. The Thinking checkbox is a second value on this cell. The local family keeps it in the session map keyed by cell id. The cloud family keeps the effort on that cell's settings document, and the cloud body sends that level. The target string does not carry the checkbox or the effort.

The model serial stays the operative reading of the chat call, the headers, and the air-gap. This rule is the operative reading of the modal, the effort, and the think split. This rule does not edit the serial. The row is closed. No call is open. The plan stamp names the build.

The locked names stay. `openGrokDialog` becomes the Model Config modal. `grokDialogOpen` stays the one flag. The `Grok` button leaves the card, and the `Grok settings` title leaves with it. `fillModels` stays the model table. `effortRows` and `effortStart` move next to `GrokSettingsDocument.MODELS`. `fillEffort` keeps the current level when it is still a row. `cell.executionTarget` is the stored string. `groupedMemberWidth` still measures `headerTop`. `shownTarget` stays the engine word and the JOIN banner. `assemble` copies the normalized effort for a grok cell. `AmwpToon.encodeExecute` writes `reasoning_effort` when the value is non-empty. `GrokLlmService.requestBody` always puts the field. `probeBody`, `startGrokProbe`, and `ProbeGate.admit` take this cell's level. `LlmRouter.complete` reads the id after `ollama/` and passes it into `SpringAiOllamaLlmService` on the same `OllamaChatOptions`. The call is `setModel`. spring-ai stays 1.1.8. `ChatShell.setModelName` is not called. `ollamaThink` widens in place. The holder fields are `ollamaOn`, `ollamaLevel`, and `grokOn`. Boolean on is `ThinkOption.ThinkBoolean.ENABLED`. A level of `low`, `medium`, or `high` is `new ThinkOption.ThinkLevel(level)`. `ThinkBoolean.DISABLED` is not sent. The show frame is `CHANNEL_A` `TYPE_DATA`. A miss is not `TYPE_ERROR` and not `CHANNEL_STREAM`. The branch sits before the execute slot, before `inFlight`, and before Roux. The base is trimmed the way `OllamaLoadCommand` trims it. Jackson reads `thinking.values`. The show ask does not run on the EDT. Kind `t` is dropped in `paintThinkDelta` when `grokOn` is off. `adoptGrok` returns when the cell already has a document. A modal write does not call `rebuildTabs`.

**Truth.** `Model` on every readable card opens one `Model Config` modal. Ollama is the default family. A change writes the cell at once. The label under the main bar is the model this turn will call. Local thinking is the optional `think` key. Every cloud call sends a defined `reasoning_effort`, factory `high`, with temperature factory 1, max tokens factory 128000, and a probe of 16. `grok-4.3` is the factory id inside a document that already has a model, and an empty document does not write it. The plan stamp names the build. The model serial stays the reading of the chat call, the headers, and the air-gap.

**Reason.** The group bar hid the model, and one think control could not serve both families. Each cell keeps its own Thinking value so two cells are two values and two families on one cell are two values. Effort is always defined because an undefined effort can waste tokens, and the level, not the checkbox, bounds token use. The show ask stays off the cell path so a run in flight is not marked and a stale reply cannot land on the wrong id. Empty `grok/` is `no-model` before air-gap, before the key, and before `Hello`, so a missing id fails in that order. One `Hello` per server process keeps the probe from repeating, while each later call still sends its own level. Client and server ship together because a saved turn has no think flag to migrate.

**Avoidances.** Do not put the model controls back on the card. Do not wait for OK, Cancel, or Run before a modal change is written. Do not restore the prior target on close. Do not add a second dialog, a second chat client, a shell model swap, a vault column, or a second thinking widget. Do not send `false` for local thinking off, and do not send the disabled think boolean. Do not let the Thinking checkbox set `stream` or remove `reasoning_effort`. Do not call the host from the filing, and do not open a socket from the modal. Do not run the show ask on each character or on the interface thread. Do not select `grok-4.3` on an empty document. Do not write the air-gap word by choosing Grok. Do not put `think` on the mood shaper. Do not edit the model serial. Do not open the window unless asked, and do not make the live POST from the landing check.


**semantic-key:** PK-0563 PK-0564 PK-0565 PK-0566 PK-0567 PK-0568 PK-0569 PK-0570 PK-0571 PK-0572 PK-0573 PK-0574 PK-0575 PK-0576 PK-0577 PK-0578 PK-0579 PK-0580 PK-0581 PK-0582 PK-0583 PK-0584 PK-0585 PK-0586 PK-0587 PK-0588 PK-0589 PK-0590 PK-0591 PK-0592 PK-0593 PK-0594 PK-0595 PK-0596 PK-0597 PK-0598 PK-0599 PK-0600 PK-0601 PK-0602 PK-0603 PK-0604

## The Ollama model control is an editable pull-down of the names on the configured host, and a typed id that is not in the list stays.

The models on the configured host are in the Model pull-down. This rule is closed. The build is the code that was checked. This sentence files it closed.

The Ollama Model control is an editable pull-down. Opening the Ollama pane, and switching the family to Ollama, sends `tags:` on `CHANNEL_A` `TYPE_DATA`. `handle` sees that first line before the execute slot, before `inFlight`, and before Roux. The server GETs `{base}/api/tags`. The base is `spring.ai.ollama.base-url`, trailing slash trimmed. Connect is 10 seconds. Read is 10 seconds. The reply is `tags_result:` and one `model:` line per tag `name`. A miss is `tags_result:` and no model lines. It is not `TYPE_ERROR` and it is not `CHANNEL_STREAM`. The pull-down lists those names. A typed id that is not in the list stays. No session means the client does not send, and the list stays empty. The interface process does not open a socket and does not read the URL.

An empty Model field still sends `show:`. The server uses `spring.ai.ollama.chat.options.model` when the id is empty. A blank property does not call the host. The reply id fills the pull-down when the field is still empty. That fill does not by itself persist a target. Choosing a row, or leaving a non-empty field, writes `ollama/` plus the trimmed id. Clearing the field and leaving it writes an empty target and does not ask again.

`thinking.values` keeps every textual level except `true` and `false`. Booleans stay off the effort rows. A chosen level is `new ThinkOption.ThinkLevel(level)` for that exact string. `true` stays the checkbox, `ThinkOption.ThinkBoolean.ENABLED`. Empty and `false` leave think unset. `ThinkBoolean.DISABLED` is not sent. Effort still shows only when Thinking is on and the reply listed a level. A boolean-only model keeps the checkbox and leaves Effort hidden.

The earlier modal rule stays the earlier reading of the filter that keeps only `low`, `medium`, and `high`. This rule is the operative reading of the Ollama model list and of the host's own level strings. The model serial stays unread. No new channel. No new type. No new vault column. The build that was checked compiled, and the test suite was not the proof. The window was not opened. The live cell POST was not made.

The host list stays on the server that already holds the base URL. `tags:` sits beside `show:`, before `inFlight`. One editable pull-down stays. The host's own word is the level. The row is closed.

**Truth.** Opening the Ollama pane, or switching the family to Ollama, fills an editable Model pull-down from `GET {base}/api/tags` through `tags:` and `tags_result:`. An empty field still sends `show:` and uses `spring.ai.ollama.chat.options.model` when that property is set. A chosen textual level is sent as that exact string. The row is closed.

**Reason.** The operator checked the Ollama pane, so the host's tag names belong in the pull-down and a typed id that is absent from the list still stays. A blank model property does not call the host. The fill of an empty field does not by itself persist a target, so the stored target changes only when a row is chosen or a non-empty field is left. The earlier low, medium, and high filter remains the earlier reading. This rule is operative for the list and for the host's own level strings because the host's word is the level. `ThinkLevel` on the current library accepts that exact string, while `true` and `false` stay off the effort rows.

**Avoidances.** Do not open a socket from the interface process. Do not read the base URL in the client. Do not send `tags:` when there is no session. Do not treat a miss as `TYPE_ERROR` or `CHANNEL_STREAM`. Do not persist a target merely because an empty field was filled from `show:`. Do not ask again when a cleared field is left. Do not send `ThinkBoolean.DISABLED`. Do not show Effort for a boolean-only model. Do not add a channel, a type, or a vault column. Do not open the window or make the live cell POST.


**semantic-key:** PK-0605 PK-0606 PK-0607 PK-0608 PK-0609 PK-0610 PK-0611 PK-0612 PK-0613 PK-0614 PK-0615 PK-0616 PK-0617 PK-0618 PK-0619 PK-0620 PK-0621

## The Grok model control lists every text-model id, wide enough for `grok-4.20-0309-non-reasoning`, and an empty cell stays unselected.

`grok-4.3` stays the factory id. The other text models are rows in that same pull-down. This sentence builds it. This rule is closed.

The text table is already `GrokSettingsDocument.MODELS`, in the model serial's Models order: `grok-4.7`, `grok-4.6`, `grok-4.5`, `grok-4.3`, `grok-4.20-0309-reasoning`, `grok-4.20-0309-non-reasoning`, `grok-build-0.1`, `grok-4.20-multi-agent-0309`. `fillModels` already adds those rows. An empty document does not select `grok-4.3` and does not write it. The closed control has no prototype and no field ink, so an empty selection paints as a short blank box. The eye reads that box as no selector.

A later console command is named `select-model`. The two-word forms in the sentence above stay tabled.

The Grok Model control lists every id in `MODELS`, in that order. `grok-4.3` is a row. It is not forced onto an empty cell. A stored id outside the table stays as an extra first row. An empty model stays unselected until the user picks a row.

The closed control is wide enough for `grok-4.20-0309-non-reasoning`. The popup shows every row. The control takes the field ink. The tip says the list is the text-model table, `grok-4.3` is the factory id, and a chosen row writes `grok/` plus that id.

A pick writes the cell at once, normalizes effort for that id, and updates the label. Image, video, and voice ids stay off the list. There is no call that lists models from the network. `select model` and `list models` stay tabled. The model serial stays the earlier reading. The effort-table rule stays. Opening the pane still does not write `grok-4.3`.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made.

The table that is already in the tree stays. `MODELS` is not given a second catalog. The blank box becomes a pull-down of that table, wide enough to read as a model control, in the field ink. There is no models GET. A pick still writes through the listener that already normalizes effort. No row is selected until the user picks, so opening the pane does not store `grok-4.3`. The row is the build.

**Truth.** The Grok Model pull-down lists `grok-4.7`, `grok-4.6`, `grok-4.5`, `grok-4.3`, `grok-4.20-0309-reasoning`, `grok-4.20-0309-non-reasoning`, `grok-build-0.1`, and `grok-4.20-multi-agent-0309`, in that order. The closed control is wide enough for `grok-4.20-0309-non-reasoning` and uses the field ink. An empty cell stays unselected. `grok-4.3` stays the factory id and is not written by opening the pane.

**Reason.** An empty closed control with no prototype and no field ink painted as a short blank box, so the eye read it as no selector. The pull-down uses the table already in the tree so there is no second catalog and no network list. A stored id outside the table stays as an extra first row so an unknown stored id is not dropped. Image, video, and voice ids stay off because this control is the text-model table.

**Avoidances.** Do not force `grok-4.3` onto an empty cell. Do not add a models GET. Do not list image, video, or voice ids. Do not take `select model` or `list models` off the table. Do not open the window or make the live POST. Do not edit the model serial.


**semantic-key:** PK-0622 PK-0623 PK-0624 PK-0625 PK-0626 PK-0627 PK-0628 PK-0629 PK-0630 PK-0631 PK-0632

## Models that refuse `reasoning_effort` start with the field off, and a host 400 that names that parameter turns Send effort off and retries once.

The model was set to `grok-4.3`, then to `grok-4.20-0309-reasoning` and `grok-4.20-0309-non-reasoning`. The host answered `Grok HTTP 400 {"code":"invalid-argument","error":"Model grok-4.20-0309-reasoning does not support parameter reasoningEffort."}`. The cell needs a detection mechanism and a config switch that can turn this field off, so the cell can chat. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The earlier modal rule remains the operative reading that a defined level is `high` when the field is sent, and that `omit` is not a level. This rule is the operative reading of when `reasoning_effort` stays off the wire. The model serial stays unread.

The catalog is on `GrokSettingsDocument`. `grok-4.20-0309-reasoning`, `grok-4.20-0309-non-reasoning`, and `grok-build-0.1` start with the field off. `grok-4.7`, `grok-4.6`, `grok-4.5`, `grok-4.3`, and `grok-4.20-multi-agent-0309` start with the field on. Any other id starts on. A stored `send_reasoning_effort` boolean wins over that start. A file written before this rule has no key, so the start applies on load. Version stays 1.

The Grok pane gains one checkbox, `Send effort`, beside the effort pull-down. On sends this cell's level as `reasoning_effort` on the cell call and on `Hello`. Off leaves the field off both bodies. The level stays stored. The pull-down stays on screen and is disabled while the box is off. Picking a model sets the box from the catalog for that id. The tip says on sends the level, off leaves the field off the wire, and a host 400 that names this parameter turns the box off and retries the call once. The tip is written in the same turn the control is added.

The execute frame carries `send_reasoning_effort: false` when the box is off. Absent means on, so an older frame still sends the level. The service puts `reasoning_effort` only when the flag is on. Empty with the flag on is still `high`.

A Grok HTTP 400 whose text contains `does not support parameter` and `reasoningEffort` or `reasoning_effort` is this refusal. `Hello` retries once with the field off. The cell call retries once with the field off. The second miss is the error the cell already shows. A successful retry sends `effort_off:` on `CHANNEL_A` `TYPE_DATA` for that cell. The client stores `send_reasoning_effort` false and, when the modal is open on that cell, clears the box. One retry. No loop.

`grok-4.3` stays the factory id and still sends `high` until a refusal or the box says otherwise. Image, video, and voice stay off the model list. No new channel. No new type. No new vault column. No new package. No live POST from this filing. The project compiles, and the test suite is not the proof. The window is not opened.

The refusal is a property of the model, and a switch on the cell. The level string stays, and one boolean is added. The word `omit` stays illegal. `Send effort` sits on the Grok pane. A pick follows the catalog. One refused Hello and one refused cell call are retried, then the client is told with `effort_off:`. `grok-4.3` stays on, and the two `grok-4.20-0309` ids stay off, until the host or the box says otherwise. The row is the build.

**Truth.** `Send effort` defaults on for `grok-4.7`, `grok-4.6`, `grok-4.5`, `grok-4.3`, `grok-4.20-multi-agent-0309`, and any other id, and off for `grok-4.20-0309-reasoning`, `grok-4.20-0309-non-reasoning`, and `grok-build-0.1`. A stored boolean wins. A matching HTTP 400 retries once with the field off and then stores the boolean false. `omit` is not a level. Version stays 1.

**Reason.** The host rejected `reasoningEffort` on `grok-4.20-0309-reasoning`, so those models start with the field off and a detected refusal can turn the switch off without a loop. An absent flag still means on so an older frame keeps sending the level. The level stays stored while the pull-down is disabled, so turning the box back on does not lose it. Empty with the flag on is still `high` because a defined level remains `high` when the field is sent.

**Avoidances.** Do not treat `omit` as a level. Do not retry more than once. Do not drop the stored level when the box is off. Do not add a channel, a type, a vault column, or a package. Do not put image, video, or voice ids on the list. Do not make a live POST from this filing. Do not open the window.


**semantic-key:** PK-0633 PK-0634 PK-0635 PK-0636 PK-0637 PK-0638 PK-0639 PK-0640 PK-0641 PK-0642 PK-0643 PK-0644 PK-0645 PK-0646 PK-0647

## The dummy key

When the model key is scrubbed for a public configuration, the key field stays, and its value is the placeholder your-grok-api-key.

The field name stays `casserole.grok.api-key`. The field is not removed. A person finds that line and replaces the placeholder. The password field stays empty. The private configuration stays unpublished. Neither client changes.

**Truth.** A published configuration keeps `casserole.grok.api-key` set to your-grok-api-key. The field is not deleted. The password field is empty. The private configuration is not published.

**Reason.** Removing the field left no place to put a key. A real key must not ship in the public file, and a password must not ship beside it.

**Avoidances.** Do not delete `casserole.grok.api-key`. Do not write a real key. Do not write a password. Do not publish the private configuration. Do not change either client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.

**semantic-key:** PK-5662

## The Ollama count is a completion field

The divide-by-4 estimate stays. A status repaint does not call the local model host. Stock Ollama has no tokenize route.

The loopback engine observed here is 0.40.12. The configured host is 0.40.13. Both speak on port 11434. `POST /api/tokenize` returns 404. `POST /api/detokenize` returns 404. `POST /v1/tokenize` returns 404. The published usage notes name `prompt_eval_count` as the input-token count on the done frame of `/api/generate` and `/api/chat`. That call runs the model. The Spring adapter has no tokenize method. The local tag list is `cwc-mistral-nemo-12b:latest`, `qwen2.5-coder:14b`, and `qwen2.5-coder:7b`. `deepseek-r1:7b` is named in the configuration and is not in that list. The public model default remains `qwen2.5-coder:7b`.

A probe `POST /api/generate` with model `qwen2.5-coder:7b`, prompt `abcd`, `stream` false, `keep_alive` 0, and `options.num_predict` 1 returned `prompt_eval_count` 30 and `eval_count` 1. The done reason was `length`. The same call with `num_predict` 0 returned `prompt_eval_count` 30 and `eval_count` 25. Zero does not mean generate nothing on this server. Each call took about 19 seconds from an empty process list, and `keep_alive` 0 left that list empty. The 30 is the rendered prompt, template included. The divide-by-4 guess for `abcd` is 1.

Neither client changes. The configuration stays. No library is added.

**Truth.** There is no tokenize route on this Ollama. The real input count is `prompt_eval_count` on a generate or chat done frame, and that call runs the model. The status line keeps the divide-by-4 estimate and does not call `/api/generate`. `num_predict` 0 is not an empty generation.

**Reason.** A live count looked cheaper than the guess, and it is not. The only count the server returns is on a completion, the template is inside that count, and a status repaint cannot afford a generation.

**Avoidances.** Do not call `/api/tokenize`, `/api/detokenize`, or `/v1/tokenize` and expect a count. Do not call `/api/generate` from a status repaint. Do not treat `num_predict` 0 as generate nothing. Do not replace the estimate with `prompt_eval_count` in this rule. Do not drop the template out of the 30. Do not change either client, the configuration, or the tag list. Do not add a library, a package, a public type, or a public method.


**semantic-key:** PK-0676

## A blank API session tries once more

An API session that returns no text, and has shown nothing, tries the same request once more. A stop is not tried again. Text already shown is not tried again. The second miss stays grok empty content.

The Rust client sends the execute frame. The server owns the session. The private type is EmptyContent. No new public type, no new public method, and no new test class are added. The Java server carries the retry. The Rust client does not grow a second try. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** A session that returns no text and has shown nothing is sent once more, the same request. If the user stopped it, it is not sent again. If any text was already shown, it is not sent again. A second empty result stays the notice grok empty content. The private type is EmptyContent.

**Reason.** A blank completion can be a miss rather than an answer. One more try of the same request covers that miss. A stop is the user ending the call, and text already on screen is a result, so neither is a blank session. The client only sends the execute frame. The session, including the second try, belongs to the server.

**Avoidances.** A stop is not retried. Shown text is not retried. The notice is not renamed. The doors do not change. The Rust client does not own the retry. The known paste-undo gap stays.


**semantic-key:** PK-0677 PK-0678

## Aetheris Grok API

The remote door is one chat-completions POST, the key is the bearer token, and a normal cell may send while both air-gap words stay explicit true.

This page is the locked reading for that call. A later sketch is cut from it. A formal plan is a later page. This page does not by itself license a new type, and it is not a place to store a key. It is not a live call. It does not open a later product stage, and it does not restamp the stage plan that already shipped the keyless invite.

Rank is fixed. A higher guard rail wins. The stage map and the constitution win where a sentence here drifts. Where this page is thinner than a lock already smoked on the stage serial, that lock wins, and this page is repaired. The layout page stays the layout page. This slice does not append layout.

The cell target is `provider/model`. The remote provider token is `grok`. The factory model id is `grok-4.3`. The call stays `POST https://api.x.ai/v1/chat/completions`. Image, video, and voice routes are not this call. The Responses API is not this call.

The key property is `casserole.grok.api-key`. The public example value is `your-grok-api-key`. A real value stays off this page. The committed project file leaves the property blank. An operator writes the secret and restarts the server. The value is not printed on a page.

Air-gap defaults to explicit `true` on the shell file and on the server file. Unset still refuses. Explicit `false` opens the cloud door and weakens the refusal, so that word is not the default. A normal cell, burner pin off, aimed at `grok/` plus a model id, may read the key and POST while both words stay explicit `true` and equal. The status line does not show `air-gap` for that target. A burner cell still stops. Any other cloud target still stops on `air-gap`. A mismatch between the shell word and the server word still blocks the send. The constitution file stays as written. Until the exception is in the tree, the tree still stops a `grok/` target on `air-gap`.

The first smoke is a normal cell. The burner pin stays off. A burner cell aimed at `grok/` still stops on the shell with `burner denied`.

An empty model stops before the key is read. The reason is `no-model`. The message is `no model defined`. The cell stores `FAILED`. That reason is not a fifth vault token. Vault tokens stay null, `COMPLETED`, `FAILED`, and `CANCELLED`. An empty key with a model present stays reason `failed` and message `grok key missing`.

The request and the reply are JSON. The format's parser forms the body and reads the reply. Internal records stay on their own readers. Assistant text is `choices[0].message.content` when that value is text.

The named request cap is 1800 seconds, including a thinking stream. Connect stays 10 seconds. Until that cap is built, the client in the tree still uses 120 seconds. The owed-answer clock uses the same named cap. An answer still owed after that cap is a soft failure. The reply field stays clear.

The execution target stays a free text field. The status line repaints the engine word on each key in that field. Until that repaint is built, the tree reads the stored cell and does not repaint on each key.

The first contact is a reusable handshake. It runs when the target is `grok/` and the operator presses Run. Local checks run before any request. While the server process has no valid key yet, one probe body is sent. The user message on that body is `Hello`. A success marks the key valid in memory for that server process. The greeting is discarded. The same Run then sends the cell prompt with `Authorization: Bearer` and the key. A `401` clears the flag. A server restart clears it. A shell restart does not.

Thinking text is off by default. On, that remote cell sends `stream` true. Readable thinking is `delta.reasoning_content`. The reply is `delta.content`. The stream ends with `data: [DONE]`. The text drops with the session. It is not a vault column. The pane is a three-line strip above the BB1 bar and under the main canvas. A `v` minimizes it and restores it. A call that produces no thinking hides the strip. It comes back when thinking occurs again. The widget is a read-only text area because the text is plain.

The same control, factory off, covers the local engine. On, and the target is the local engine, the one-shot chat sets `think` true. `message.thinking` paints the same strip. `message.content` stays the reply. Off leaves `think` unset. The local call is not streamed by that control. The local context field stays off the remote body.

Search, tools, and deferred completion stay off this release. Do not implement search from a parked note. Other vendors stay unbuilt.

A hard or soft failure on any member fails the group. A valid reply stays stored. The merge stores `FAILED` and its reply stays blank. A hard miss is red. A soft miss is yellow. The unidentified wire miss uses the error slide. The redacted `Grok HTTP` line goes on the slide, not on the cell.

Per-cell settings are a document that rehydrates from yaml. The modal is four pulldowns: chat only, model, reasoning effort, and streaming. The burner-cloud checkbox sits on that same modal. It is specific to that model. A local model still receives a burner cell. A cloud model refuses the hop while the box is unchecked.

Temperature and `max_completion_tokens` ride on the cell. The adopted cell sends `temperature` `1` and omits `top_p`. `max_completion_tokens` on the cell is `128000`. The probe cap is `16`. An earlier sentence named `top_p` at the schema default `0.95`. The adopted reading leaves `top_p` off the cell. The schema default is not a send.

The analysis rows of this slice are adopted and are the reading a plan cuts from. They are not, by this page alone, a license to write the types. The live POST was not made from this page.

**Truth.** One remote URL, one bearer key, factory model `grok-4.3`, air-gap left explicit `true`, and a normal non-burner `grok/` cell is the door that may POST. The key text is never on the page. The tree timeouts and the air-gap stop remain until the named numbers and the exception are built.

**Reason.** The remote call needs a key, and a key is not a payment credential, so the air-gap lock yields only for this provider on a normal cell. Every other cloud target stays refused. The page stays the operative reading so a later stage does not invent a second door.

**Avoidances.** Do not store the key on the page. Do not set air-gap `false` to make the call. Do not switch the cell to the Responses API. Do not send `num_ctx` on the remote body. Do not adopt `grok-4.7` as the factory id. Do not add a fifth vault token. Do not rewrite the constitution file or the stage map from this page. Do not treat a parked search note as this release.

**semantic-key:** PK-5663

## Depends

The remote-call page depends on the stage map, the constitution, and the locked stage serial, and it does not rewrite them.

The stage map stays the working map. Its slate names this remote call. This page does not rewrite that map.

The constitution stays as written. A cell execution target is `provider/model`. The constitution file is not edited to open the door. This page is the operative reading for provider `grok` only.

The locked stage serial stays locked. The remote row there is the gap that invited the call names. This page does not reopen that serial.

The stage sketch places the remote call after the execute seams. The stamped stage plan's remote package is not restamped by this page.

The keyless call and the notebook root are already in the tree. This page reads that shape and does not rebuild it.

The code this page reads is the remote chat service, the provider interface, the property binder, the server resource file, the router, the facade, the notebook panel, the stage surface, the air-gap reader, the wire codec, the wire constants, the wire text codec, and the client properties. The key reference is the server yaml reference. The operator resource file stays off this page. The public example uses `your-grok-api-key`.

The local context length is not copied onto the remote call and is not copied into the air-gap yaml. The remote call does not receive `num_ctx`. A configured integer greater than 0 is the local engine's `options.num_ctx`. The value 0 omits the field. The published example is 8192. The code default model is `qwen2.5-coder:7b`. The published local base is `http://localhost:11434`. An operator file may override the model or the context length, and that file stays off this page.

Other vendors named on a parked list stay unbuilt.

**Truth.** The stage map, the constitution, and the locked stage serial remain the higher pages. This page adds the remote door and does not restamp those pages. `num_ctx` stays a local field.

**Reason.** The call was invited by the stage gap. A thinner sentence here loses to a lock already smoked. Copying the local window onto the remote body would invent a field the remote schema does not have.

**Avoidances.** Do not rewrite the stage map. Do not edit the constitution file to force the exception. Do not restamp the stage plan. Do not copy a private context length onto this page or into the air-gap yaml. Do not build the parked vendors from this dependency list.

**semantic-key:** PK-5664

## Where the key goes

The server takes the first non-empty remote key and sends it only as a bearer token.

The lookup is the read on the remote chat service. The property binder supplies the first place. The first hit wins. A blank or absent property is a miss. The value is not logged. The public example keeps the config key `casserole.grok.api-key` and the dummy value `your-grok-api-key`. The operator file that holds a real value stays off this page. A secret in a project file is a known risk. The property exists so an operator can set it. The writer of the page does not write the secret and does not commit a filled value.

The order is fixed.

1. `casserole.grok.api-key` in the server resource file. Blank or absent is a miss. The value is not logged. The filed commit leaves it blank.
2. The environment variable `XAI_API_KEY`. Empty or absent is a miss. A value is trimmed.
3. The first non-empty, non-comment line of `grok.key` under `.aetheris` in the process home. That path is `~/.aetheris/grok.key`.
4. The same line of `grok.key` under `.casserole`. That path is `~/.casserole/grok.key`.

A comment line is a trimmed line that starts with `#`. A blank line is skipped. A missing file, or a read error, is a miss and the lookup continues. The program does not create `grok.key`.

The server process is the process that calls the API. Restart the server after the value changes. The property is not live-reloaded.

Do not paste the key into chat, a cell, or a page. An operator may report that the secret is written on `casserole.grok.api-key`. The page still does not print it. The kernel and any board stay clear of the value.

A missing key still fails the cell. The status line shows the reason `failed`. The wire message is `grok key missing`. The key text is not in that message.

The local context length, the local base, and the local model stay as they are. The remote call does not send `num_ctx`. The published local base is `http://localhost:11434`. The code default model is `qwen2.5-coder:7b`. A context length of 0 omits the field. The published example context length is 8192. An operator file may override the model or the context length, and that file stays off this page.

The stage serial keeps its earlier keyless sentence. This page is the operative reading for where the key is placed.

**Truth.** First hit wins, in the order property, environment, process-home key file, then the second key file. Blank, empty, comment, missing file, and read error are misses. The bearer token is the only wire form. The text `grok key missing` carries no key.

**Reason.** The property is first so a configured value wins, and a blank property still falls through so the environment and the two files remain doors. The server must be restarted because the property is not live-reloaded. The value stays off the page because the page is not a secret store.

**Avoidances.** Do not log the value. Do not paste it into chat, a cell, or a page. Do not create `grok.key` when the file is missing. Do not treat a comment or a blank line as the key. Do not stop the lookup on the first miss. Do not send `num_ctx` on the remote call. Do not put the local base or the local model on the remote body.

**semantic-key:** PK-5665

## Already in the tree

The router, the one-shot JSON body, and the air-gap refusal are already built, and this page does not rebuild them.

`LlmRouter.complete(target, prompt)` is the map. There is no registry class. The token before `/` selects the provider. `grok` selects `GrokLlmService.completeFor`. The model id is the text after `/`, trimmed. An empty stored target goes out as `ollama` and is not written back as that word.

The request URL is `https://api.x.ai/v1/chat/completions`. Connect timeout is 10 seconds. The tree request timeout is 120 seconds. The named cap is 1800 seconds. The tree still uses 120 seconds until that number is built. The header is `Authorization: Bearer` plus the key. The body is JSON, formed with the JSON parser: `model`, `messages` of `role` and `content`, and `stream` false. Roles are `system`, `user`, and `assistant`. `num_ctx` is not set. Options on the prompt are ignored.

A 2xx body is read with the same parser. Assistant text is `choices[0].message.content` when that node is a string. Null content returns empty, and empty content fails the cell as `grok empty content`. A non-string `content`, or a body that is not JSON, fails as `grok content shape`. A missing `choices` array or a missing `message` fails as `grok response has no content`. The reader does not join content parts and does not read `reasoning_content`.

A non-2xx body is turned into `Grok HTTP` plus the status plus at most 180 characters, with the key text replaced by `[redacted]`. The facade sends that string as the error message. The error slide shows it. The cell stores `FAILED` and the reply is left as it was. The status line shows `failed`.

Air-gap is enforced on the server from `~/.aetheris.yaml`, key `aetheris.network.air-gap`. Explicit `true` refuses cloud. A missing key loads as `unset` and refuses. Explicit `false` allows. The stored target is not rewritten.

The shell reads `--aetheris-yaml`, then `./aetheris.yaml`, then `~/.aetheris.yaml`. Those are not one shared file. The shell status word is its own file. Session facts echo the server. A reported mismatch blocks the shell's cloud send. The server still enforces its own value.

The default word is explicit `true`. `false` opens the cloud door and weakens security. A choice that weakens security is questioned and verified before it is filed. The default that was agreed is `true`. Unset still refuses. Both `aetheris.yaml` and `~/.aetheris.yaml` are explicit `true`. A remote smoke stays refused until an explicit `false` is named for that smoke, except the normal-cell exception below.

The normal-cell exception is the operative reading for provider `grok`. An API key is the requirement for remote connectivity. It is not a payment credential. The value stays off this page. The folder stays private. On the wire the value is the bearer token. Both air-gap words stay explicit `true`. This rule does not edit those files. The constitution lock stays on the constitution: `air-gap: true` forbids cloud targets. The lock yields for provider `grok`. A normal cell, burner pin off, aimed at `grok/` plus a model id, may read `casserole.grok.api-key` and POST. The status line does not show `air-gap` for that target. A burner cell still stops with `burner denied`. Any other cloud target still stops on `air-gap`. Unset still refuses. A mismatch between the shell word and the server word still blocks the send. The tree still stops a `grok/` target on `air-gap` until this exception is built. The live POST was not made.

A burner cell plus a cloud target is failed on the shell before the frame is sent. The status word is `burner denied`. The server would also refuse while `notebook.burner-key` is missing. The shell does not read that flag.

The group bar starts hidden. A `v` on the card opens a text field. The tip says empty sends to the local engine, and `grok/model` sends to the remote door when a key is present. Each key writes the cell in memory. Run saves the canvas. The field does not save on its own.

The status line prints `engine:` as the focused cell's stored target. An empty stored field prints `ollama`.

Run on a group still sends each member on that member's own target. The merge cell is not sent.

The keyless commit compiled. The window was not exercised by hand. The live POST was not made. The property commit left the key blank. A later report may say the secret is on `casserole.grok.api-key`. This page still does not print it. The client body is unchanged: `model`, `messages`, and `stream` false.

**Truth.** The tree already maps `grok` to one service, posts JSON with `stream` false, reads a string `choices[0].message.content`, redacts the key in a non-2xx line, and refuses cloud unless the later normal-cell exception is built. Connect is 10 seconds. The tree timeout is 120 seconds.

**Reason.** The keyless invite proved the shape. The page records that shape so a later build adds the probe, the cap, and the exception without a second client. Explicit `true` stays the default because `false` weakens the refusal. The key is allowed on a normal remote cell because connectivity requires it and it is not a payment credential.

**Avoidances.** Do not rebuild the router. Do not add a registry class. Do not write an empty target back as the word `ollama`. Do not set `num_ctx`. Do not join content parts. Do not read `reasoning_content` on this one-shot path. Do not put the key in the error text. Do not treat the shell file and the server file as one file. Do not let the shell skip its own word because the server also checks. Do not send the merge cell.

**semantic-key:** PK-5666

## The call as it stands

A remote run types `grok/` plus a model id, passes the local doors, and stores one reply, and the tree still stops that target on air-gap until the exception is built.

1. The operator opens the group bar and types `grok/` plus the model id into the execution target. Empty still means the local engine.

2. Run harvests the card. A shell air-gap refusal or a shell mismatch stops the send. The banner is `air-gap`. A burner cell stops the send. The banner is `burner denied`. The normal-cell exception allows a normal `grok/` cell on the page. The tree still stops that cell here until the exception is built.

3. The walker builds the working set. On a cloud target, burner text from earlier cells is left out. The frame carries `execution_target` and `ephemeral`.

4. The server reads `~/.aetheris.yaml`. Cloud plus refuse returns reason `air-gap` and does not call complete. A burner frame plus a missing `notebook.burner-key` returns `burner denied` and does not call complete. The notebook shell already stopped a burner, so this server check is not what a notebook Run hits today. The normal-cell exception allows a normal `grok/` cell on the page. The tree still returns `air-gap` for that cell until the exception is built.

5. The router takes the target. `grok` with an empty model is its own miss, named under the empty-model rule. Any other provider token fails as `unknown provider`. The stored target stays as written. Until the empty-model build is in the tree, an empty model still throws the same exception as a missing key, and the message is `grok key missing`.

6. A present key and a non-empty model POST the body. The reply is stored on that cell as `COMPLETED`. Over 1 MiB, prefix included, the cell is `too-large` and the stored reply is not replaced. The handshake runs before that POST on the page. The tree still reaches this POST when the shell has not stopped the cell.

**Truth.** Empty means the local engine. `grok/` plus an id is the remote target. Burner text is stripped from a cloud working set. The frame carries the target and the ephemeral bit. A reply over 1 MiB, prefix included, is `too-large` and does not replace the stored reply. Success stores `COMPLETED`.

**Reason.** The call already has a path. The page separates what the tree does today from what the exception and the empty-model reason will do, so a builder does not pretend the exception is already enforcing.

**Avoidances.** Do not send the merge cell. Do not include burner text from earlier cells on a cloud target. Do not replace the stored reply when the payload is over 1 MiB. Do not rewrite the stored target when the provider token is unknown. Do not report an empty model as `grok key missing` once the empty-model build is in the tree.

**semantic-key:** PK-5667

## Handshake handler

The first remote contact is one process-wide probe whose user message is `Hello`, and the same Run then sends the cell prompt.

The handler is reusable. This provider is the first cloud implementation. A later cloud provider uses the same handler shape. The handler does not run because a cell exists. It runs when the target is `grok/` and the operator presses Run. The product does not assume the operator will pick the remote door.

On every `grok/` Run the local checks run before any request. The air-gap exception, the burner pin, a missing model, and a missing key are decided here. A failure uses the banner already named for that miss. The cell stores `FAILED`. The prompt stays on the machine. The key is not put on a request. A burner cell still stops with `burner denied`. Any other cloud target still stops on `air-gap`. A mismatch between the shell word and the server word still blocks the send. Unset still refuses.

The initial check asserts the key. While this server process has no successful probe, the handler sends one small `POST https://api.x.ai/v1/chat/completions`. The body is JSON. `model` is the target id. `messages` is one user message whose content is `Hello`. That is the whole user message on the first POST. `stream` is false. The cell prompt is not in that body. `temperature` and `top_p` stay off that body. `max_completion_tokens` on the probe is `16`. The header is `Authorization: Bearer` plus the key. That POST is allowed for a normal cell under the air-gap exception.

A wire miss from the probe opens the error slide already specified. The cell stores `FAILED`. The cell prompt is not sent. The rule does not add a second window.

A handshake that succeeds marks the key valid for this server process. The flag is one valid-true flag in the process that reads the key. The flag matches that one key. It lives in memory. It is not written to a file. The key is not copied into a new store. A server restart clears it. A shell restart does not. The shell does not keep a second copy.

From then on in that process, a `grok/` Run that finds the flag valid skips the probe. The cell prompt POST carries the same bearer header. The probe does not open a session the vendor remembers, so the key stays on every POST.

After a 2xx `Hello` with readable content, that same Run sends the cell prompt. The greeting is discarded and is not stored on the cell. A miss stores `FAILED` and does not send the prompt.

The probe gate is one flight for the process. States are none, in flight, and valid. The check and the claim share one lock. The lock is not held during the POST. A Run that finds none becomes the leader and sends the one `Hello`. A Run that finds in flight waits and does not send its own `Hello`. A miss leaves the gate at none. Each of those cells stores `FAILED`. The next `grok/` Run may probe again. Cancelling the leader cell does not abort `Hello`. That cell stores `CANCELLED` and does not send its prompt. The three execute slots stay cell calls. `cell-busy` is not this gate.

A `401` on a later call clears the presumption. The error slide shows the failure. The cell stores `FAILED`. The next `grok/` Run probes again.

The handler is specified. It is not built by this page. The tree still stops a `grok/` target on `air-gap` until the exception is built. The live POST was not made. Code for this spec waits until a sketch and a formal plan name the build.

**Truth.** One `Hello` per server process, `stream` false, probe cap `16`, no sampling fields on the probe, greeting discarded, then the cell prompt on that same Run. Valid-true is server memory only. A `401` or a server restart clears it. The leader's cancel does not abort `Hello`.

**Reason.** Two notebooks, or a shell restart, must not disagree about a key the server already proved. Two simultaneous runs must not each send `Hello`. The greeting is not the operator's answer, so it is not stored. The key stays on every POST because the probe is not a vendor session.

**Avoidances.** Do not put the cell prompt on the probe body. Do not store the greeting. Do not write valid-true to a file. Do not keep a second flag in the shell. Do not hold the probe lock during the POST. Do not let a waiter send its own `Hello`. Do not abort `Hello` when the leader cell is cancelled. Do not treat `cell-busy` as this gate. Do not open a second error window.

**semantic-key:** PK-5668

## Empty model

An empty remote model stops before the key is read, with reason `no-model` and message `no model defined`.

An empty model is a target whose provider is `grok` and whose text after the slash is empty. `grok` and `grok/` are that miss. A blank target stays the local engine.

The call stops before the POST. The key is not consulted. An empty model and an empty key together report `no-model`.

The error frame carries reason `no-model` and message `no model defined`. The status line shows `no model defined`. The cell stores `FAILED`. Vault tokens stay null, `COMPLETED`, `FAILED`, and `CANCELLED`. `no-model` is the error reason. It is not a fifth vault token. An unknown vault token marks that card unreadable.

An empty key with a model id present stays reason `failed` and message `grok key missing`. The status line for that miss still shows `failed`.

Until that build is in the tree, the complete path still throws the missing-key exception for an empty model, and the facade still sends reason `failed` and message `grok key missing`. This note writes no type by itself. The check belongs in the shell and in the complete path, before the key read.

**Truth.** `grok` and `grok/` are `no-model`. A blank target is the local engine. The banner is `no model defined`. The vault token is `FAILED`. An empty key with a model present remains `grok key missing`.

**Reason.** A missing model was wearing the key banner because both misses shared one exception. The operator needs a distinct reason, and the key must not be consulted when there is nothing to call.

**Avoidances.** Do not consult the key for an empty model. Do not add `no-model` as a vault token. Do not report `grok` or `grok/` as the local engine. Do not keep the shared missing-key exception once the distinct path is built.

**semantic-key:** PK-5669

## Error slide

An unidentified miss opens one slide at the top of the desktop pane, and the redacted remote HTTP line goes there, not on the cell.

The notice sits at the top of the pane and slides down. The border is red. The title is Error. The body is three wrapped rows. It stays until Close. A later miss replaces the text. The canvas stays usable. It is not an application-modal dialog.

A non-2xx body is `Grok HTTP` plus the status plus at most 180 characters, with the key text replaced by `[redacted]`. Newlines in that line are spaces. A transport miss is `Grok` plus the exception name plus the redacted detail. That string is the slide text. The `garnered` line still caps at 240 characters. The cell stores `FAILED`. The reply box is not replaced. The status line shows `failed`.

Named banners stay on the status line and do not open the slide: `air-gap`, `engine-down`, `too-large`, `burner denied`, `pipe`, `grok key missing`, `unknown provider`, `unknown role`, and `no tool is invocable`. `cell-busy` stays a status-line notice. A group miss later colors this same slide red or yellow. That group rule does not add a second window. A single named banner on its own still stays on the status line.

The control is built. Compile passed. The window was not opened by hand. The live POST was not made.

**Truth.** One slide, red border, title Error, three wrapped rows, stays until Close, canvas stays usable. The remote wire text is `Grok HTTP` plus status plus at most 180 characters, key replaced by `[redacted]`. A transport miss uses the same slide. The reply box is not replaced.

**Reason.** A miss with no graceful handling was dropping a long redacted line onto the cell. The slide keeps the canvas usable and keeps the key out of the cell text. Named banners already have a status line, so they do not open a second notice.

**Avoidances.** Do not put the redacted HTTP line on the cell. Do not make the slide application-modal. Do not open the slide for the named banners or for `cell-busy`. Do not leave the key text in the 180 characters. Do not add a second window. Do not replace the reply box on this miss.

**semantic-key:** PK-5670

## Group fail

Any hard or soft failure on a member fails the merge, leaves valid replies stored, and colors the one slide red or yellow.

One empty member used to leave the concatenation waiting, so the merge never wrote. Any hard or soft failure on a member fails the group. The rule is filed here. The tree still waits until the rule is built.

A valid reply is `COMPLETED` with assistant text. That text stays, and the save that stored it stays.

A hard failure returns no reply to store. The reasons are `engine-down`, a remote transport miss, and `too-large`. The slide border and the title ink are red.

A soft or logical failure is a rule that stopped the cell, or a send that left the answer blank. The reasons include an empty reply, `grok key missing`, `no-model`, a `Grok HTTP` line, `air-gap`, `burner denied`, `unknown provider`, `unknown role`, `pipe`, and `no tool is invocable`. The slide border and the title ink are yellow. The failed cell stays blank and stores `FAILED`.

`cell-busy` is not a failure. It does not fail the group and does not open the slide.

The merge cell stores `FAILED` and its reply stays blank. Concatenation does not run. Members that already stored a valid reply are left as they are. When any miss in the group is hard, the notice is red. When every miss is soft, the notice is yellow. Vault tokens stay null, `COMPLETED`, `FAILED`, and `CANCELLED`.

A later build also treats `CANCELLED` as a member miss that fails the merge. A group whose only miss is `CANCELLED` stores `FAILED` on the merge and does not open the slide. This heading's first filing is the hard and soft rule. The cancel-only silence is the later package reading and stays in force with it.

The execution target stays the free text field. A locked list of host models plus `grok/<model>` is not the control. Both would write `execution_target`. The free text field is the control.

An answer that is still owed is a soft failure. A member has stored a response. Another member still has no answer. The reasonable time is the request cap on this page. The clock starts when the stored response lands. When the cap passes and the answer is still missing, that member is a soft failure. The yellow slide says `the cell is still waiting and no answer arrived`. The reply field stays clear. The cell stores `FAILED`. A valid reply already stored stays stored. The group fails under the rule above. The two clocks keep two jobs: the HTTP cap is a hard transport miss, and this clock is the wait after some other member has already stored a response.

The named cap is 1800 seconds. Until that number is built, the client in the tree still uses 120 seconds, and the owed-answer clock uses 120 seconds with it. `cell-busy` is not this wait.

The yellow skin uses the same geometry as the red slide: border and title `0xF9A825`, fill `0x3A3014`, ink `0xF8F4E8`. One slide. The named banners still paint the status line.

**Truth.** Any member `FAILED` or `CANCELLED` fails the merge. Concatenation does not run. A stored valid reply stays. The merge reply stays blank. Hard is red. Soft is yellow. Cancel-only does not open the slide. The owed-answer words are `the cell is still waiting and no answer arrived`. The named cap is 1800 seconds. The tree cap is 120 seconds until that build.

**Reason.** One empty member left the merge waiting forever. Failing the group on any miss writes a blank merge and keeps the replies that already succeeded. The HTTP death and the owed-answer wait are different clocks, so a long call is not painted as a soft wait and a sibling that never answers is not painted as a transport death.

**Avoidances.** Do not run concatenation after a failed or cancelled member. Do not clear a valid reply already stored. Do not treat `cell-busy` as a failure. Do not open the slide for a cancel-only group. Do not choose a locked model list as the execution target. Do not start the owed-answer clock before one member has stored a response. Do not add a second slide for the yellow skin.

**semantic-key:** PK-5671

## The request menu

The remote cell stays on `POST https://api.x.ai/v1/chat/completions`, and the menu lists every header and body field without promoting one the schema does not require.

This page does not switch the cell to the Responses API. Image, video, and voice routes are not this call.

The authority is the vendor OpenAPI 3.1.0 document, info version 1.0.0, schema `ChatRequest`, at `https://docs.x.ai/openapi.json`. The operation is `handle_generic_completion_request`. The `required` array on `ChatRequest` is empty. The JSON body itself is required. The only body media type is `application/json`. The operation lists no header parameters. Security is `bearerAuth`.

The legacy guide is `https://docs.x.ai/developers/model-capabilities/legacy/chat-completions`. The sample sends `Content-Type: application/json` and `Authorization: Bearer` plus the key. The sample model id is `grok-4.7`. The sample client timeout is 3600 seconds. This page does not adopt that timeout or that model as the factory id.

The models page is `https://docs.x.ai/developers/models`. The model page is `https://docs.x.ai/developers/models/grok-4.7`. The reasoning page is `https://docs.x.ai/developers/model-capabilities/text/reasoning`. The live `GET /v1/language-models` was not called. The key was not used to list models.

Required in these tables means the schema lists the field in `required`, or the operation requires the header. A field the examples always send is still optional when the schema does not list it. This page does not promote a field.

The client today sends `model`, `messages`, and `stream` false. The JSON parser forms that body. It does not send any other body field. Connect timeout stays 10 seconds. The named request cap is 1800 seconds. The client in the tree still uses 120 seconds until that number is built. `num_ctx` is not a field on `ChatRequest`. It stays off this call.

**Truth.** The body media type is `application/json`. The schema `required` array is empty. The JSON body itself is required. Security is bearer auth. The client sends three fields until a later build names more. `num_ctx` is not on this schema.

**Reason.** The menu is an assessment of the vendor schema, not a second product. Promoting an example field would make the client send a parameter the schema does not require. The Responses API, image, video, and voice are different calls.

**Avoidances.** Do not switch the cell to the Responses API. Do not call the model list with the key from this page. Do not adopt the sample timeout of 3600 seconds. Do not adopt `grok-4.7` as the factory id. Do not send `num_ctx`. Do not mark an example field required when the schema leaves it out.

**semantic-key:** PK-5672

## Headers

The remote call sends two headers, and neither header is a cell setting.

`Authorization` is required. The value is `Bearer` plus the key. It is not a cell field. The key lookup order is the source: `casserole.grok.api-key`, then `XAI_API_KEY`, then `~/.aetheris/grok.key`, then `~/.casserole/grok.key`. The public example value is `your-grok-api-key`. A real key stays off this page.

`Content-Type` is required. The value is `application/json`. It is not a cell field. The body is JSON.

The operation lists no header parameters. Security is `bearerAuth`. The sample also sends those two headers.

`prompt_cache_key` is plumbed on the server to `x-grok-conv-id`. That name is not a request header in the OpenAPI. The client does not send it.

**Truth.** Required headers are `Authorization: Bearer` plus the key, and `Content-Type: application/json`. `x-grok-conv-id` is not sent.

**Reason.** The vendor operation names bearer security and a JSON body, and it lists no other header parameters. A conversation id plumbed inside the server is not a header the schema publishes.

**Avoidances.** Do not make either header a modal field. Do not send `x-grok-conv-id`. Do not put the key anywhere except the bearer value. Do not omit `Content-Type` on the JSON body.

**semantic-key:** PK-5673

## Body fields

Every `ChatRequest` body field is optional on the schema, and this client sends only the fields a later rule names.

The schema default is not a send. The client today sends `model`, `messages`, and `stream` false, and no other body field.

`deferred` is optional. Default false. True returns a `request_id`. The result is a later `GET /v1/chat/deferred-completion/{request_id}`. The cell client is still one shot. Deferred stays off the wire until that build is named.

`frequency_penalty` is optional. Default 0. Number from -2.0 to 2.0. Not supported by reasoning models. The reasoning page says a request that includes it returns an error.

`guidelines` is optional. Per-category output policy. Omitted leaves it unset. A request that cannot apply them, including a multi-agent model or a request with `stop`, fails with 400.

`logit_bias` is optional. Unsupported. Maps token ids to a bias from -100 to 100, added to the logits before sampling.

`logprobs` is optional. Default false. Return log probabilities of output tokens. Silently ignored on `grok-4.20` and newer.

`max_completion_tokens` is optional. Upper bound on visible output tokens. It does not count reasoning tokens or function-call tokens. When unset, the schema default is 128000. The cell sends `128000`. The probe sends `16`. A 400 that names the cap is the slide text.

`max_tokens` is optional. Deprecated. Use `max_completion_tokens`.

`messages` is optional on the schema. The schema does not list it as required. The published examples always send it. The walker builds it. It is not a modal field. Roles this client sends are `system`, `user`, and `assistant`. Any other role is `unknown role`.

`model` is optional on the schema. The schema does not list it as required. The published examples always send it. This client still refuses an empty id before the POST. The banner for that miss is `no model defined`, reason `no-model`. An earlier sentence said the empty id used the same banner as a missing key. That shared banner is what the tree still sends until the empty-model path is built. This menu does not name a default id. The factory id elsewhere on the page is `grok-4.3`.

`n` is optional. Default 1. How many choices to generate. Billing follows the tokens across all choices.

`parallel_tool_calls` is optional. Default true. False caps the model at one tool call. Tool use stays off until tool integration is named.

`presence_penalty` is optional. Default 0. Number from -2.0 to 2.0. Not supported by `grok-3` or by reasoning models. The reasoning page says a request that includes it returns an error.

`prompt_cache_key` is optional. A stable key for sticky routing and prompt-cache hits across requests that share a prefix.

`reasoning_effort` is optional. How hard a reasoning model thinks. Supported values and the default depend on the model. On the Responses API the same idea is `reasoning.effort`. This cell stays on chat completions, so the field name here is `reasoning_effort`. It rides only when the stored value is not `omit`. `grok-4.3` stays omit only.

`response_format` is optional. Structured output. Null or one of `text`, `json_object`, `json_schema`.

`safety_identifier` is optional. A stable end-user id from the client. Hash an internal id. Do not send an email or a name. Stored with request metadata so a usage-policy violation can be attributed to that user.

`search_parameters` is optional. Realtime search. The schema default for `mode` is `auto`. The description also calls `on` the default. This page does not pick a winner. Search stays off this release.

`seed` is optional. Best effort at a repeatable sample when the seed and the other parameters match. Determinism is not guaranteed.

`service_tier` is optional. `default`, `priority`, or `fast`. The schema says Fast and Priority are interchangeable: the model's fast deployment and its rates where configured, otherwise higher scheduling priority at a higher price.

`stop` is optional. Up to 4 stop sequences. Not supported by reasoning models. The reasoning page says a request that includes it returns an error.

`stream` is optional. Default false. True sends server-sent deltas and ends with `data: [DONE]`. This client keeps false until the thinking control is on. The one-shot complete stays the off path.

`stream_options` is optional. Used with streaming. `include_usage` is required on that object. This modal does not send `stream_options`.

`temperature` is optional. Default 1. Range 0 to 2. Values above 1 are sampled at 1. The cell sends `1`. The probe leaves it unset.

`tool_choice` is optional. `none`, `auto`, `required`, or an object whose `type` is `function` plus an optional function name. Tool use stays off.

`tools` is optional. Up to 1000 tools. Function tools, or the deprecated `live_search` form. Tool use stays off until tool integration is named.

`top_logprobs` is optional. Integer from 0 to 8. Requires `logprobs` true. Silently ignored on `grok-4.20` and newer.

`top_p` is optional. Default 0.95. Nucleus sampling. The schema recommends altering this or `temperature`. The adopted cell omits `top_p`. The probe omits it. An earlier sentence named factory `0.95` as a send. The adopted reading does not send it.

`user` is optional. A unique end-user id for abuse monitoring. `safety_identifier` is the later field for that job. `user` is still accepted.

`web_search_options` is optional. Compatibility object. `search_context_size` defaults to `medium` and is mapped to `max_search`. `filters` and `user_location` are included for compatibility.

Tools, `deferred`, and search stay off the wire. The remote call does not send `num_ctx`.

**Truth.** The schema marks every row optional. The JSON body is still required. The cell that is built sends `model`, `messages`, `stream`, `temperature` `1`, and `max_completion_tokens` `128000`, plus `reasoning_effort` only when it is not `omit`. The probe sends `model`, one user message `Hello`, `stream` false, and `max_completion_tokens` `16`. `top_p` is omitted on both.

**Reason.** The vendor required array is empty, so the page refuses to promote an example into a requirement. The client still refuses an empty model because an empty id has no call to make. Sampling follows the adopted pair: temperature on the cell, top-p left off, so a model that rejects the pair is not failed by a field the schema only recommends.

**Avoidances.** Do not send a menu field the build has not named. Do not send `max_tokens`. Do not send `frequency_penalty`, `presence_penalty`, or `stop` to a reasoning model. Do not send an email or a name in `safety_identifier`. Do not pick a winner between search mode `auto` and `on`. Do not send `num_ctx`. Do not send tools, deferred, or search on this release.

**semantic-key:** PK-5674

## Nested fields

A nested object is required only when that object is sent, and this client does not send the nested objects the menu only records.

Message roles are `system`, `developer`, `user`, `assistant`, `tool`, and `function`. There is no role-order limit on chat models. The client today sends only `system`, `user`, and `assistant`, each as a string `content`. It does not send image parts. Any other role is `unknown role`.

`messages.role` is required on the message. It is one of the six roles.

`messages.content` is required on system, developer, user, tool, and function. It is optional on assistant. The value is a string today. The parts form uses type `text`, `image_url`, `text_file`, or `file`. `input_text` and `input_image` are accepted. This client does not send image parts.

`messages.name` is optional. It is an end-user id on that message. It is not on the tool role.

`messages.reasoning_content` is optional. Assistant reasoning text. Assistant role only.

`messages.tool_calls` is optional. Assistant tool calls. Tool use stays off.

`messages.tool_call_id` is optional. Tool role. The id from the assistant tool call.

`guidelines.type` is required on the object. It must be `policy`.

`guidelines.category` is required on the object. The values are `sexual`, `violence`, `self_harm`, `illicit`, or `profanity`. Each category at most once.

`guidelines.behavior` is required on the object. `decline` for sexual, violence, illicit, and profanity. `decline` or `offer_support` for self_harm.

`response_format.type` is required on the object. The values are `text`, `json_object`, or `json_schema`.

`response_format.json_schema` is required when type is `json_schema`. It is the desired schema, including a `schema` field.

`search_parameters.mode` is optional. Schema default `auto`. Description: `off` skips search, `on` searches every source, `auto` lets the model decide. The description also calls `on` the default. This page does not pick a winner.

`search_parameters.max_search_results` is optional. Default 15.

`search_parameters.return_citations` is optional. Default true.

`search_parameters.from_date` is optional. ISO-8601 `YYYY-MM-DD`.

`search_parameters.to_date` is optional. ISO-8601 `YYYY-MM-DD`.

`search_parameters.sources` is optional. If omitted, the model looks over the web and X. Each source has a required `type` of `web`, `x`, `news`, or `rss`.

On a web or news source, `country` is an ISO alpha-2 code. `excluded_websites` holds at most 5 sites, with no protocol and no subdomain. `safe_search` defaults to true. Web also has `allowed_websites`, at most 5.

On an X source, the fields are `included_x_handles`, `excluded_x_handles`, `post_favorite_count`, and `post_view_count`. `x_handles` is the deprecated name of `included_x_handles`.

On an RSS source, `links` holds the feed URLs.

A function tool requires `type` `function` and a `function` object that is a function definition. `function.name` is required. It is the name returned if the model calls it. `function.parameters` is required. It is a JSON schema the model should follow. It is not enforced. `function.description` is optional. It says when to call it. `function.strict` is optional. It is not supported and is kept for compatibility. `function.defer_loading` is optional. It is Responses API only. It is hidden from the prompt until a tool search loads it. It is not this cell call.

A live-search tool requires `type` `live_search` and `sources`. The schema marks this form deprecated.

`web_search_options.search_context_size` is optional. Default `medium`. It is mapped to `max_search`.

`web_search_options.filters.allowed_domains` is optional. At most 5 domains, no protocol and no subdomain.

`web_search_options.filters.excluded_domains` is optional. Domains to drop. No protocol and no subdomain.

`web_search_options.user_location.type` is required on that object. It is always `approximate`.

`web_search_options.user_location.city` is optional.

`web_search_options.user_location.region` is optional.

`web_search_options.user_location.country` is optional. ISO alpha-2.

`web_search_options.user_location.timezone` is optional. An IANA name, such as `America/Chicago`.

Search, tools, guidelines, and response format stay off the wire until a build names them. The walker sends string content for `system`, `user`, and `assistant` only.

**Truth.** Six roles exist on the schema. This client sends three, as strings. Nested search, tool, guideline, and format objects are recorded and not sent. Image parts are not sent.

**Reason.** The nested rules matter only when the parent object is sent. Recording them keeps the menu complete without putting search, tools, or image parts on this release.

**Avoidances.** Do not invent a request property the schema extract omitted. Do not send image parts. Do not send a role outside `system`, `user`, and `assistant`. Do not send `defer_loading` on this cell. Do not send a deprecated live-search tool. Do not put a protocol or a subdomain on an excluded or allowed site. Do not send more than five sites or five domains. Do not repeat a guidelines category.

**semantic-key:** PK-5675

## Reasoning effort by model

Reasoning effort is depth on the chat field `reasoning_effort`, and the legal values depend on the model id.

The Responses name is `reasoning.effort`. The values are the same. This cell stays on chat completions, so the field name here is `reasoning_effort`.

`grok-4.7` accepts `low`, `medium`, `high`, and `xhigh`. The default is `high`. It controls reasoning depth. It cannot be turned off.

`grok-4.6` accepts `low`, `medium`, `high`, and `xhigh`. The default is `high`. It controls reasoning depth. It cannot be turned off.

`grok-4.5` accepts `low`, `medium`, and `high`. The default is `high`. It controls reasoning depth. It cannot be turned off. `xhigh` is treated as `high`.

`grok-4.20-multi-agent` accepts `low`, `medium`, `high`, and `xhigh`. The default is not stated. The value is an agent count, 4 or 16, not reasoning depth. The pricing table spells the id `grok-4.20-multi-agent-0309`. This page does not merge the two spellings.

`low` is the faster setting. It uses some reasoning tokens. The page names latency-sensitive agent use and simple tool calling.

`medium` is for long context and harder analysis. It thinks more. The page names complex data analysis and long-context reasoning.

`high` is the deeper default and uses more reasoning tokens. The page names hard problems, complex math, multi-step logic, and competition-level tasks.

`xhigh` is the deepest, and it is slower. It is the maximum depth. The page names the hardest problems, where answer quality matters more than response time. `xhigh` is available on `grok-4.6` and later.

On `grok-4.7`, `grok-4.6`, and `grok-4.5`, `frequency_penalty`, `presence_penalty`, and `stop` are the fields the reasoning page rejects. A request that includes them returns an error.

The effort control adds `omit`. `omit` leaves `reasoning_effort` off the body. A selected value is sent, including `high` when `high` is the published default. `grok-4.3`, `grok-4.20-0309-reasoning`, `grok-4.20-0309-non-reasoning`, and `grok-build-0.1` are `omit` only. This table does not list them. The page does not invent values for them.

A best-practices sentence says `grok-4.3` defaults to `low` and also accepts `none`. The reasoning-page summary table does not list `grok-4.3`. Both sentences stay. This page does not pick a winner, and it does not add `grok-4.3` to this table from that sentence alone. The pulldown for `grok-4.3` stays `omit` only.

The client does not send `reasoning_effort` until the field is in the build. Until then the field stays off the wire.

**Truth.** Chat field `reasoning_effort`. Responses field `reasoning.effort`. Defaults are `high` where the table states `high`. `xhigh` starts at `grok-4.6`. The multi-agent spelling is not merged with the pricing id. `omit` is a client row, not a vendor value, and it sends nothing.

**Reason.** Depth and the streaming switch are different controls. Sending the published default still records the depth so a silent vendor default does not decide the result. Leaving `grok-4.3` at `omit` refuses to invent a row the reasoning table does not list.

**Avoidances.** Do not turn reasoning off on a model that cannot disable it. Do not send `xhigh` as a distinct depth on `grok-4.5`. Do not merge `grok-4.20-multi-agent` with `grok-4.20-multi-agent-0309`. Do not send `frequency_penalty`, `presence_penalty`, or `stop` with those reasoning models. Do not add `none` or `low` to the `grok-4.3` pulldown from the best-practices sentence alone. Do not treat effort as the streaming switch.

**semantic-key:** PK-5676

## Models

The text-priced model ids are the set a cell may put in `model` on this POST, and the factory id is `grok-4.3`.

The vendor models page says to use Grok 4.7 for code and for chat. This page does not adopt `grok-4.7` as the product default. The effort pulldown for `grok-4.3` stays `omit` only. The modal is not built by this page. The client still sends the model on the target.

Two prices on one id are the short-prompt rate, then the long-prompt rate. A prompt that reaches the threshold is billed at the higher rate for every token in that request. The threshold on these rows is 200k prompt tokens. Prices are per 1M tokens.

`grok-4.7` has context 500k. Input is $2.00 then $4.00. Cached input is $0.50 then $1.00. Output is $6.00 then $12.00. The model page says text and image in, text out, function calling, structured outputs, and reasoning. Efforts are low, medium, high, and xhigh. Default high. Knowledge cut-off is May 2026. The batch API is in us-east-1 and us-west-2, at a 20 percent discount. Regions are us-east-1, us-west-2, and us-central-1. The limit is 150 requests per second and 50000000 tokens per minute.

`grok-4.6` has context 500k. Input is $2.00 then $4.00. Cached input is $0.50 then $1.00. Output is $6.00 then $12.00. It is a pricing row. The reasoning page lists efforts low, medium, high, and xhigh, default high. No separate model page is in the sitemap.

`grok-4.5` has context 500k. Input is $2.00 then $4.00. Cached input is $0.30 then $0.60. Output is $6.00 then $12.00. It is a pricing row. Efforts are low, medium, and high, default high. `xhigh` is treated as `high`.

`grok-4.3` has context 1M. Input is $1.25 then $2.50. Cached input is $0.20 then $0.40. Output is $2.50 then $5.00. It is a pricing row only. It is the factory id.

`grok-4.20-0309-reasoning` has context 1M. Input is $1.25 then $2.50. Cached input is $0.20 then $0.40. Output is $2.50 then $5.00. It is a pricing row only. `logprobs` and `top_logprobs` are ignored on `grok-4.20` and newer.

`grok-4.20-0309-non-reasoning` has context 1M. Input is $1.25 then $2.50. Cached input is $0.20 then $0.40. Output is $2.50 then $5.00. It is a pricing row only.

`grok-build-0.1` has context 256k. Input is $1.00 then $2.00. Cached input is $0.20 then $0.40. Output is $2.00 then $4.00. It is a pricing row only.

`grok-4.20-multi-agent-0309` has context 1M. Input is $1.25 then $2.50. Cached input is $0.20 then $0.40. Output is $2.50 then $5.00. It is the pricing id. The reasoning page names `grok-4.20-multi-agent` and uses effort as an agent count. Guidelines cannot be applied on a multi-agent model. The two spellings are not merged.

The alias section on the models page is a pattern, not a list of ids. It says `<modelname>` tracks the latest stable version, `<modelname>-latest` tracks the latest version, and `<modelname>-<date>` is one release. This page does not invent `grok-4.7-latest` or any other alias. Sample ids inside `GET /v1/language-models` examples are samples. They are not this catalog. The live list was not called.

These ids are on the same models page. They are not the body of this POST. A cell target does not select them.

`grok-imagine-image-quality` is image, $0.05 per image.

`grok-imagine-image` is image, $0.02 per image.

`grok-imagine-image-2.0` is image, $0.04 per image.

`grok-imagine-video-1.5-lite` is video, $0.020 per second.

`grok-imagine-video` is video, $0.050 per second.

`grok-imagine-video-1.5` is video, $0.080 per second.

`grok-voice-2.0` is speech to speech. It is deprecated. The price is $0.08 per minute, and $0.004 per text input.

`grok-voice-3.0` is speech to speech. The price is $0.08 per minute, and $0.004 per text input.

Speech to text on that page is $0.10 per hour on REST and $0.20 per hour streaming. Text to speech is $15.00 per 1M characters. Those two rows name the mode, not a model id.

Image input, when a text model accepts it, is capped at 20MiB per image. The counted types are jpg, jpeg, and png. The models page states no limit on how many images. This client does not send image parts.

The code default local model is `qwen2.5-coder:7b`. That id is not a row on this remote catalog. An operator file may override the local model, and that file stays off this page. A private model name is not published here.

**Truth.** Factory id `grok-4.3`, context 1M, not `grok-4.7`. The long-prompt threshold is 200k tokens and rebills the whole request. Image, video, and voice ids are not this POST. No alias is invented.

**Reason.** The vendor recommends a newer id for code and chat. The product default stays the id this page names so a modal and a target do not drift to a recommendation. Non-text families stay off the chat body.

**Avoidances.** Do not adopt `grok-4.7` as the factory id. Do not invent a `-latest` alias. Do not select an image, video, or voice id as the cell model. Do not send image parts. Do not merge the two multi-agent spellings. Do not publish a private local model name in this catalog. Do not call the live model list with the key.

**semantic-key:** PK-5677

## How a cell keeps the settings

The first remote selection with no standing default opens one modal, and every later remote selection copies the nearest earlier cell on that canvas.

The choice persists. A new remote cell receives settings. The operator is asked once, in a modal. After that, a button edits the settings for that cell. An edit sticks only to that cell. When a new turn is created and the operator selects the remote door, the nearest earlier cell's settings are copied and presumed correct. The operator may edit them or leave them. That is the fine-grained control for the call on that turn.

This rule files the behavior. It does not build the modal. It does not build the button. It does not add a vault column. It does not mint a new wire key. It does not bump the schema. Column names wait until the build is named.

The modal for now holds `api`, `model`, `reasoning_effort`, and `stream`. The burner-cloud checkbox sits on that same modal. `messages` are not a setting. The walker builds them. `Authorization` and `Content-Type` are not settings. The menu stays the assessment list. A field this modal does not show stays off the wire. Tool fields stay off the wire until tool integration is named. The client still sends only `model`, `messages`, and `stream` false until the build sends more.

The model id is the text after `grok/` on the execution target. The modal writes that suffix when a model is picked. This page does not pre-fill an id.

1. The first time the operator selects the remote door on a cell, and no standing default exists, one modal opens. The choices confirmed become the standing default, and they are stored on that cell. The operator is not asked again.

2. After the standing default exists, selecting the remote door does not open the modal.

3. A button on the cell edits the settings for that cell. It is shown when the target provider is `grok`, beside the execution target. The exact chrome stays light. The layout page is still the layout page. When the button is built, the same turn gives it a tooltip. The tip says what the control is and what the click does.

4. An edit writes that cell only. It does not change the standing default. It does not change any other cell.

5. Creating a new turn does not select the remote door and does not copy settings. The copy runs when the operator selects the remote door on that turn.

6. The source is the nearest earlier cell on the same canvas that already has stored settings. Canvas order, walking backward. A later cell is not a source. Another canvas is not a source. Those settings are presumed correct. The operator may edit or leave them.

7. If no earlier cell on that canvas has stored settings, the standing default is copied onto the new cell.

8. If there is no standing default and no earlier cell, the modal in rule 1 runs.

9. A copied model id is written as the target suffix `grok/` plus that id.

10. Run on a group still sends each member on that member's own target. The stored settings ride with that member. The merge cell is not sent.

`stream` true is the thinking switch. The pulldown is that switch. The client keeps `stream` false until that build is in the tree. A cell whose `stream` is `false` stays on the one-shot path. A stored tool field is not sent until tool integration is named. A stored `deferred` true is not sent until that build is named. The legacy guide's 3600 second timeout is not adopted. Connect stays 10 seconds. The named cap is 1800 seconds. The client in the tree still uses 120 seconds until that number is built. The readable thinking text on this cell arrives only when `stream` is true.

**Truth.** One modal, once, when no standing default exists. Later cells copy backward on the same canvas only. An edit sticks to that cell. The target suffix is `grok/` plus the id. The merge cell is not sent.

**Reason.** The operator should not re-answer the same door on every new turn, and an edit on one cell must not rewrite the standing default or a neighbor. Walking backward keeps a later cell from becoming the source.

**Avoidances.** Do not open the modal again after a standing default exists. Do not copy on turn creation before the remote door is selected. Do not copy from a later cell or from another canvas. Do not pre-fill a model id. Do not add a vault column for these settings. Do not send a field the modal does not show. Do not adopt the 3600 second timeout. Do not send the merge cell.

**semantic-key:** PK-5678

## The settings modal

The settings modal is four pulldowns plus the burner checkbox and the number fields, and the choices rehydrate from one yaml document.

For now the operator gets the model, the effort, chat only, streaming on or off, and any other required parameter. The choices are pulldowns. The parameters are a document that rehydrates from yaml. This rule writes no type and does not create the file.

The dialog is a `JDialog` owned by the notebook frame. It is application modal. The title is `Grok settings`. OK writes the document. Cancel writes nothing. When this open was the transition into `grok` and the cell had no document, Cancel puts the target back to the text it had before that transition.

The file is `~/.casserole/aetheris-grok.yaml`. It is not the client connection file, not the air-gap file, not the server resource file, and not a vault column. `version` is `1`. The public key shape stays `your-grok-api-key` on the server example. This document has no key field.

**Truth.** Title `Grok settings`. Application modal. Chat only. Document path `~/.casserole/aetheris-grok.yaml`. Version `1`. The file is created on the first confirm, not by this page.

**Reason.** Call settings need a document the operator can reopen. They do not belong in the connection file, the air-gap file, the server resource file, or the vault.

**Avoidances.** Do not create the file before the first confirm. Do not put a key in the document. Do not write on Cancel. Do not offer Responses, image, video, or voice as rows. Do not append the layout page from this modal.

**semantic-key:** PK-5679

## What the room already does

The first remote selection with no standing default opens one modal, and the copy runs only when the operator selects the remote door.

That confirm is the standing default and the cell. A later edit sticks to that cell. A new turn does not copy. The copy runs when the operator selects the remote door, from the nearest earlier cell on the same canvas, walking backward. The button beside the execution target opens the modal for that cell. It is shown when the provider is `grok`. The row still starts collapsed. The same build turn tips that button, OK, and Cancel. The tip says what the control is and what the click does. The layout page is not appended.

The cell record stores `executionTarget`. It has no effort field and no stream field. This rule does not add a vault column and does not bump the schema.

The client properties load `~/.casserole/ssh-client.yaml` with the yaml library already on the client, then store every key except `accounts` as a string. A nested map becomes one string on that load, and the next save writes the string back. That file also holds the connection password. A remote-call document does not go in it. The password stays off this page.

The air-gap reader is a space-indent hand parser. Unknown keys are ignored. The shell file and the server file are not the same file. The yaml library stays off that parser. Call settings do not go in `aetheris.yaml`.

The server resource file holds the key. Call settings do not go there. This page does not read that file for call settings. The public example value is `your-grok-api-key`. The operator file stays off this page.

**Truth.** The cell record stores the target and does not grow an effort column or a stream column. The connection yaml flattens maps and holds a password, so it is the wrong file. The air-gap parser ignores unknown keys and is two files. The server resource file holds the key and not the call settings.

**Reason.** A flattened connection file would destroy the nested document on the next save, and it already holds a password. The air-gap parser is a hand reader that must stay a hand reader. The key file is not the settings file.

**Avoidances.** Do not store call settings in the connection yaml. Do not store them in the air-gap yaml. Do not store them in the server resource file. Do not add a vault column. Do not bump the schema. Do not run the yaml library through the air-gap hand parser. Do not append the layout page.

**semantic-key:** PK-5680

## The four pulldowns

Four combo boxes, not editable, choose the API, the model, the effort, and streaming, and OK stays disabled until a model is selected.

The listener that refreshes Effort when Model changes is an anonymous class, the same shape as the other card listeners.

The API pulldown's document key is `api`. The only row is `chat`. It is not a body field. It selects this POST.

The Model pulldown's document key is `model`. The rows are the text-model table, in that order. The value is sent as `model` and as the target suffix `grok/` plus the id.

The Effort pulldown's document key is `reasoning_effort`. The rows are the legal values for the selected model, plus `omit`. The value is sent when it is not `omit`.

The Streaming pulldown's document key is `stream`. The rows are `false` and `true`. `false` is the one-shot path. `true` is the thinking-stream path.

`chat` is the only API row. Responses is not a row. Image, video, and voice ids are not model rows. The page does not invent an alias. The page does not pre-select `grok-4.7`. OK stays disabled until a model is selected. An edit selects the stored id. A stored id that is not in the table is an extra row at the top, so a rehydrate does not drop it.

Effort follows the reasoning table.

`grok-4.7` and `grok-4.6` offer `low`, `medium`, `high`, `xhigh`, and `omit`. The selection starts on `high`, the published default.

`grok-4.5` offers `low`, `medium`, `high`, and `omit`. The selection starts on `high`. `xhigh` is not a row.

`grok-4.20-multi-agent-0309` offers `low`, `medium`, `high`, `xhigh`, and `omit`. The reasoning page lists those values on `grok-4.20-multi-agent` and does not state a default. This page does not merge the two spellings. The model row stays the pricing id. The selection starts on `omit`.

`grok-4.3`, `grok-4.20-0309-reasoning`, `grok-4.20-0309-non-reasoning`, and `grok-build-0.1` offer `omit` only. The reasoning table does not list them. This page does not invent values.

Changing the model keeps the current effort when that value is still a row. When it is not, the selection moves to the published default, or to `omit` when no default is stated. `omit` leaves `reasoning_effort` off the body. A selected value is sent, including `high` when `high` is the published default. `reasoning_effort` is depth. It is not the streaming switch.

Streaming starts on `false`. `true` shows the thinking pane and, in that build, sends `stream` true. There is no second checkbox on the card. On a remote cell, the document `stream` value is the thinking control. A later package carries it as `think_stream`.

**Truth.** Four non-editable combo boxes. API is `chat` only. Model rows are the text catalog. Effort adds `omit`. Streaming is `false` or `true`, factory `false`. An unknown stored id stays as an extra top row. Factory model `grok-4.3` is omit only.

**Reason.** Pulldowns stop a free-typed effort that the model rejects. `omit` is a row so the client can leave the field off without pretending the vendor default was chosen. Sending `high` when `high` is the published default records the depth. A stored id outside the table must survive a reload.

**Avoidances.** Do not make the boxes editable. Do not add a Responses row. Do not pre-select `grok-4.7`. Do not drop an unknown stored id. Do not invent effort values for ids the reasoning table omits. Do not merge the multi-agent spellings. Do not use effort as the streaming switch. Do not put a second checkbox on the card.

**semantic-key:** PK-5681

## Burner cloud checkbox

A checkbox in the model settings allows or refuses a burner hop to that cloud model, and a local burner still runs.

The burner pin on the card stays. It marks the turn ephemeral.

A local model is the local-engine path. A burner cell still goes there. This checkbox is not on that path. The shell stop does not fire for the local engine.

A cloud model is the model in these settings. The checkbox is that model's allow. The document key is `burner_cloud`. The factory value is false. False is unchecked. True is checked.

Unchecked: the shell refuses the hop. The cell stores `FAILED`. The status line shows `burner denied`. The frame is not sent. Unchecked on a frame that still arrives stores the same denial.

Checked: the shell reads the box and sends the frame. For provider `grok` the server reads that bit.

The checkbox is not a body field. Air-gap still refuses a cloud target on its own, except the normal-cell exception for provider `grok`. A checked box does not open an air-gap.

The adopted frame rule: for provider `grok` the execute frame carries `burner_cloud` beside `ephemeral`. The shell copies the checkbox. The server reads that bit. `notebook.burner-key` stays the door for every other cloud provider. A local burner still runs. An earlier sentence left the bit off the execute text. The adopted sentence puts it on the frame. This page does not build the frame by itself.

`notebook.burner-key` stays the server door for every other cloud provider. A missing or false server flag still returns `burner denied` for those providers. This checkbox does not write that key and does not write the air-gap word. The tree still refuses every burner cloud hop until this control is built.

The value is stored with the standing default and with the cell entry. A missing `burner_cloud` means false. The first confirm writes it with the other settings. A later edit writes that cell only. The copy rules copy it with the document. A copied true allows the hop on that new cloud cell. The factory value before any confirm is false.

The build turn tips the checkbox. The tip says a check allows a burner hop to this cloud model, and an unchecked box refuses it.

Until the checkbox exists, the frame value is false.

**Truth.** Document key `burner_cloud`, factory false. Unchecked stores `FAILED` with status `burner denied` and does not send. Checked sends, and provider `grok` reads the frame bit. The local engine ignores the checkbox. `notebook.burner-key` remains the door for every other cloud provider.

**Reason.** The operator needs a per-model allow for a burner hop that is not the air-gap word and not the shared server flag. A local burner is a different path and must keep running. A checked box must not weaken air-gap for other providers.

**Avoidances.** Do not put the checkbox on the local-engine path. Do not treat the checkbox as a body field. Do not let a checked box set air-gap false. Do not let the checkbox write `notebook.burner-key`. Do not send an unchecked burner hop. Do not change the standing default when one cell is edited. Do not treat a missing key as true.

**semantic-key:** PK-5682

## Required, and what is not a pulldown

The vendor required array is empty, the JSON body is required, and the only choice the modal treats as required is a model id.

`Authorization` is required. It is the key lookup. It is not a pulldown. The public example value is `your-grok-api-key`.

`Content-Type` is required. It is `application/json`. It is not a pulldown.

`model` is optional on the schema. This client refuses an empty id before the POST. The modal will not confirm without one. That is the required choice. The banner is `no model defined`. The reason is `no-model`.

`messages` are built by the walker. They are not a pulldown.

`stream` defaults to false on the schema. The pulldown makes the boolean explicit. The body sends the selected value, including `false`.

`reasoning_effort` is optional. `omit` leaves it off.

`stream_options.include_usage` is required only when that object is sent. This modal does not send `stream_options`.

No other body field is required from the operator. The adopted cell sends `temperature` `1` and `max_completion_tokens` `128000` and omits `top_p`. Those number fields sit on the same dialog once the build names them. An earlier sentence on this heading left temperature, a max-token count, top-p, tools, `deferred`, and search off the wire. Tools, `deferred`, and search stay off. Temperature and the completion cap are the later adopted sends. Top-p stays omitted.

**Truth.** Required on the wire from the operator's point of view: bearer key, JSON content type, and a non-empty model. Messages come from the walker. Stream is explicit even when false. Effort is optional. Usage options are not sent.

**Reason.** An empty schema required list would otherwise let a confirm with no model reach a POST the client must refuse. Making `stream` explicit avoids a silent schema default. Leaving top-p off follows the adopted sampling pair.

**Avoidances.** Do not confirm without a model. Do not make the key or the content type a pulldown. Do not make messages a pulldown. Do not send `stream_options`. Do not require temperature or the completion cap from a pulldown. Do not send tools, deferred, or search. Do not send `top_p` on the adopted cell.

**semantic-key:** PK-5683

## The document

One yaml object at `~/.casserole/aetheris-grok.yaml` holds the standing default and each cell, and a version other than `1` is left unread.

The early object named `api`, `model`, `reasoning_effort`, `stream`, and `burner_cloud`. The same object is the standing default and a cell entry. The later build also stores `temperature` and `max_completion_tokens` on that object. `top_p` stays off the document.

The shape, not the factory selection, is version `1`, a `default` map, and a `cells` map keyed by cell id. A sample id in that shape may read `grok-4.6` with `reasoning_effort` `high` on the default and `omit` on a cell. That sample id is a shape. It is not a factory selection. The factory model is `grok-4.3`. Factory `reasoning_effort` is `omit` for that id. Factory `stream` is false. Factory `burner_cloud` is false. Factory `temperature` is `1`. Factory `max_completion_tokens` is `128000`. Factory `api` is `chat`.

`version` is `1`. A file whose version is not `1` is left unread and is not overwritten. A missing file is no standing default. A missing `default` key is no standing default. A missing key on an entry means `api` `chat`, `stream` `false`, `reasoning_effort` `omit`, `burner_cloud` `false`, and an empty `model`. An empty `model` is not a usable default. `burner_cloud` is a known key. The writer keeps it.

The file is UTF-8. No byte-order mark. The client already has the yaml library, and this file is the one that uses it. The writer emits the object in block style and emits only the known keys. Unknown keys are dropped on the next write. The file has no key field. A key found in it is ignored and is not written back.

The first confirm writes `default` and that cell. A later edit writes that cell only. The copy rules write the new cell's entry when they copy. Creating a turn does not write an entry. Removing turns removes the ids they remove, in the same save. Import mints new ids and does not carry this file. An imported cell has no entry until the remote door is selected.

On open, the notebook loads the file into the object. When a cell entry exists, its `model` is written onto the target suffix so the card and the document show one id. When no entry exists, the stored target stays. A hand edit of the free field to `grok/` plus an id updates that cell's `model` when the field loses focus. A hand edit away from `grok` hides the pane and does not stream. The entry may remain. The free text field stays. It is the control.

The execute text carries `think_stream: true` only when `stream` is `true`, and carries `reasoning_effort` only when the value is not `omit`. Absent means off, the same rule as `ephemeral`. The server ignores both for a provider that is not `grok`. `api` is not on the execute text. The remote path stays `POST /v1/chat/completions`.

Until that build is in the tree, the client still sends `model`, `messages`, and `stream` false. It does not send `reasoning_effort`. This rule does not create the yaml file.

**Truth.** Path `~/.casserole/aetheris-grok.yaml`, UTF-8, no byte-order mark, version `1`. Unknown keys drop on the next write. A foreign version is left unread and is not overwritten. A key found in the file is ignored. Factory values are `chat`, `grok-4.3`, `omit`, `stream` false, `burner_cloud` false, `temperature` `1`, `max_completion_tokens` `128000`. The sample id `grok-4.6` is a shape only.

**Reason.** One document rehydrates the standing default and the cell without a vault column. Leaving a foreign version unread prevents a newer file from being flattened. Dropping unknown keys keeps the writer honest. Ignoring a key in the file keeps the secret out of the settings document.

**Avoidances.** Do not overwrite a version other than `1`. Do not write a key into the file. Do not write a found key back. Do not treat `grok-4.6` as the factory id. Do not put `top_p` on the document. Do not put `api` on the execute text. Do not create the file before the first confirm. Do not carry the file across an import. Do not treat an empty model as a usable default.

**semantic-key:** PK-5684

## Fit he can strike

The modal fit is four pulldowns, chat only, stream default false, a model required before OK, effort from the published table, and one yaml document that rehydrates.

These are the fit choices inside that rule. A strike names the replacement. Until a strike, the filed fit holds.

The file is its own document, beside the client config, because the client yaml flattens maps and the server yaml ignores unknown keys.

`omit` is a row. A value equal to the published default is still sent.

Cancel on the first transition puts the target back.

On open, a cell entry wins over a different suffix already stored on the target.

**Truth.** Own file. `omit` is a real row. A published default is still sent when selected. First-transition Cancel restores the prior target. A cell entry beats a mismatched suffix on open.

**Reason.** Each fit is a choice that could have gone the other way. Filing it keeps a later strike honest: the replacement has to name what changes, and the rest stays.

**Avoidances.** Do not flatten the document into the client yaml. Do not hide `omit`. Do not suppress a selected value just because it equals the vendor default. Do not leave the target on `grok` when the first open is cancelled and the cell had no document. Do not let a stale suffix beat a stored cell entry on open.

**semantic-key:** PK-5685

## Thinking text on the Grok stream

Readable thinking text is available on the chat-completions stream this cell already uses, and encrypted reasoning is a different call that this cell does not make.

It is not on the wire today. The client still sends `stream` false until the thinking build is in the tree.

Two different things travel under the word thinking. They stay distinct.

1. Readable summarized text. The cell can receive it on the chat-completions stream it already uses.
2. Encrypted reasoning. That is a Responses API item. This cell does not call Responses. Chat completions has no field for that ciphertext.

This rule files both. It does not switch the cell to Responses. It does not set `stream` true by itself. It does not read the thinking text until the stream build. It does not adopt a longer timeout than the named 1800 seconds.

The endpoint stays `POST https://api.x.ai/v1/chat/completions`. Connect stays 10 seconds. The named request cap is 1800 seconds, including a thinking stream. The client in the tree still uses 120 seconds until that number is built. 900 and 3600 are not adopted.

**Truth.** Readable text is a chat-completions stream. Encrypted reasoning is Responses only. This cell stays on chat completions. `stream` false is what the tree sends until the build.

**Reason.** The word thinking names two transports. Mixing them would move the cell onto an API this product does not call, or would paint ciphertext the chat schema does not carry.

**Avoidances.** Do not switch the cell to Responses to obtain the readable summary. Do not set `stream` true before the build. Do not adopt 900 or 3600 as the cap. Do not treat encrypted reasoning as the readable text.

**semantic-key:** PK-5686

## What was read

The streaming, reasoning, and best-practices pages are the authority for readable thinking text, and no live POST was made.

Streaming is `https://docs.x.ai/developers/model-capabilities/text/streaming`.

Reasoning is `https://docs.x.ai/developers/model-capabilities/text/reasoning`.

Best practices is `https://docs.x.ai/developers/model-capabilities/text/best-practices`.

The OpenAPI file was not downloaded again for this note. A non-stream response field named `message.reasoning_content` is not newly asserted. The menu already lists `messages.reasoning_content` as an optional assistant field on the request. The streaming page is the evidence for the response chunk.

No live POST was made. The key was not used.

**Truth.** Three vendor pages are the authority. The chunk evidence is the streaming page. A non-stream `message.reasoning_content` is not newly asserted. No live POST was made.

**Reason.** The menu already records the request field. Asserting a non-stream response field without a fresh schema read would invent a path. The stream sample is enough to name the deltas.

**Avoidances.** Do not treat the request field `messages.reasoning_content` as proof of a non-stream response field. Do not make a live POST to confirm the pages. Do not use the key to fetch them.

**semantic-key:** PK-5687

## Readable text on this cell

Readable thinking arrives only when `stream` is true, as `delta.reasoning_content`, then `delta.content`, then `data: [DONE]`.

The cell endpoint stays `POST https://api.x.ai/v1/chat/completions`.

Streaming is supported by every model that has text output. That includes chat and image understanding. It is not supported by models whose output is an image. Image generation, video, and voice stay off this POST.

Enable it with request `"stream": true`. The published chat-completions sample uses model `grok-4.7` and sets no other flag for thinking text. There is no second switch on that sample. `reasoning_effort` is the depth control. It is not the switch that turns the text on.

The transport is server-sent events. The docs recommend a client SDK to parse the stream. This product reads the lines with the JSON parser already in the remote service. Server-sent events are a line read beside that JSON reader. A second parser is not added.

The published sample events use model `grok-4.7` and object `chat.completion.chunk`.

The first `data` line has `choices[0].delta.reasoning_content` set to `The`, and `delta.role` set to `assistant`.

A later line has `delta.content` set to `Ah`, and `delta.role` set to `assistant`.

A later line has `delta.content` set to `,`.

The stream ends with `data: [DONE]`.

Each published chunk also carries `id`, `created`, `model`, `choices[0].index` 0, `system_fingerprint`, and `usage`. The usage object on those chunks has `prompt_tokens`, `completion_tokens`, `total_tokens`, and `prompt_tokens_details` with `text_tokens`, `audio_tokens`, `image_tokens`, and `cached_tokens`. The sample chunks do not show a `reasoning_tokens` field. The reasoning page says usage metrics expose `reasoning_tokens`. The best-practices page names `usage.output_tokens_details.reasoning_tokens` on the Responses shape. This page does not invent a chat-completions path for that counter.

The first sample chunk is a reasoning delta and reports `completion_tokens` 1. The next content chunk still reports `completion_tokens` 1. The comma chunk reports `completion_tokens` 2.

The published OpenAI and xAI loops on the streaming page print `delta.content` or `chunk.content` only. A reader that copies those loops never prints `reasoning_content`. The reasoning page's xAI loop is the one that prints `chunk.reasoning_content`.

The service writes `stream` false. The parser reads `choices[0].message.content` when that node is a string. It does not read `reasoning_content`. Connect stays 10 seconds. The named cap is 1800 seconds. The client in the tree still uses 120 seconds until that number is built. The readable thinking text arrives only once `stream` is true. Those deltas are not sent, and they are not painted, until that build.

`stream` true may still be stored, under the rule already on this page. The client keeps `stream` false until the streaming build is named.

**Truth.** Switch is `stream` true. First sample delta is reasoning text `The` with role `assistant`. Later deltas are content `Ah` and `,`. End marker is `data: [DONE]`. Object is `chat.completion.chunk`. Usage on the samples has no `reasoning_tokens` field. A content-only loop misses the thinking text.

**Reason.** The vendor sample that matches this product is the chat-completions stream, not the Responses loops. The tree reader is a one-shot JSON object reader, so an SSE body would fail as the wrong shape until a line reader is added.

**Avoidances.** Do not add a second switch for thinking text. Do not treat `reasoning_effort` as that switch. Do not copy a content-only loop. Do not invent `usage.output_tokens_details.reasoning_tokens` on the chat-completions chunk. Do not read `reasoning_content` while `stream` is false. Do not paint usage or the role.

**semantic-key:** PK-5688

## What the readable text is

The readable text is a summarization of internal reasoning, and reasoning tokens are billed as part of total consumption.

The reasoning page heading is Summarized Reasoning Content. For `grok-4.7`, the docs expose summarizations of the model's internal reasoning. The published sample output is a prose summary of a projectile problem. It uses conservation of energy and says the final speed does not depend on the launch angle. The page presents that text as a summary. It does not present it as the raw trace.

The examples under that heading are not the cell's chat-completions body.

The Vercel AI SDK sample uses `streamText` on `xai.responses('grok-4.7')`. The loop reads `fullStream` where `part.type` is `reasoning-delta`, and prints `part.text`.

The OpenAI SDK sample uses `client.responses.create` with `stream` true, against `https://api.x.ai/v1`. The loop prints `event.delta` when `event.type` is `response.reasoning_text.delta` or `response.reasoning_summary_text.delta`.

The curl sample is `POST https://api.x.ai/v1/responses` with `stream` true, model `grok-4.7`, an `input` array, and a 3600 second limit. That limit is not adopted.

The xAI SDK sample uses `chat.stream()` and prints `chunk.reasoning_content`. That sample sets `timeout` to `3600` and comments that the timeout is overridden longer for reasoning models. That timeout is not adopted.

Those four are how the docs demonstrate the summary. The cell sample that matches this product is the chat-completions stream: `delta.reasoning_content`, then `delta.content`, then `data: [DONE]`.

The last sentence of the summarized section: when you use a reasoning model, the reasoning tokens are billed as part of total consumption.

The best-practices page says `max_output_tokens` caps only the visible answer. Reasoning tokens and function-call tokens do not count toward it. When unset, that cap defaults to 128000. A tight cap cuts the answer off. Responses uses `max_output_tokens`. The chat field already on the menu is `max_completion_tokens`, and that row already says it counts visible output only. This page does not rename it.

**Truth.** The painted text is a summary, not a raw trace. The matching cell shape is `delta.reasoning_content`, then `delta.content`, then `data: [DONE]`. Reasoning tokens are billed inside total consumption. `max_completion_tokens` caps visible output only and defaults to 128000 when unset. The cell sends 128000. The field is not renamed to `max_output_tokens`.

**Reason.** The Responses samples show the same summary through a different API. Keeping them on the page prevents a builder from copying those loops onto this cell. The cap name stays the chat name so the body matches `ChatRequest`.

**Avoidances.** Do not present the summary as the raw trace. Do not move the cell to `POST /v1/responses` to match the samples. Do not adopt the 3600 second sample timeout. Do not rename `max_completion_tokens` to `max_output_tokens`. Do not count reasoning tokens or function-call tokens against that cap.

**semantic-key:** PK-5689

## Depth, and what a reasoning model rejects

`reasoning_effort` stays the depth control, and a reasoning model rejects presence penalty, frequency penalty, and stop.

The chat completions field name is `reasoning_effort`. The Responses field name is `reasoning.effort`.

`grok-4.7` and `grok-4.6` accept `low`, `medium`, `high` (default), and `xhigh`. Reasoning cannot be disabled.

`grok-4.5` accepts `low`, `medium`, and `high` (default). There is no `xhigh`. A request that sends `xhigh` is treated as `high`.

`xhigh` is available on `grok-4.6` and later.

`low` uses some reasoning tokens and stays fast. The page names latency-sensitive agent use and simple tool calling.

`medium` thinks more. The page names complex data analysis and long-context reasoning.

`high` is the default and uses more reasoning tokens. The page names hard problems, complex math, multi-step logic, and competition-level tasks.

`xhigh` is the maximum depth, and it is slower. The page names the hardest problems, where answer quality matters more than response time.

`grok-4.20-multi-agent` is the spelling on the reasoning page. `reasoning.effort` there is an agent count, 4 or 16, not depth. The pricing id is `grok-4.20-multi-agent-0309`. This page does not merge the two spellings.

The best-practices page says `grok-4.3` defaults to `low` and also accepts `none`. The reasoning-page summary table does not list `grok-4.3`. Both sentences stay. This page does not pick a winner, and it does not add `grok-4.3` to the effort table from the best-practices sentence alone. The pulldown stays `omit` only.

The same best-practices page says to set the effort explicitly on every request, and to record it with every result, so a default never decides the result. This client does not send `reasoning_effort` until that field is named. When it is sent, a selected value goes out, including a value equal to the published default. `omit` sends nothing.

The reasoning page says `presencePenalty`, `frequencyPenalty`, and `stop` cannot be used with reasoning models. A request that includes them returns an error. The menu rows already say `frequency_penalty`, `presence_penalty`, and `stop` are not supported on reasoning models.

**Truth.** Depth values and defaults match the reasoning table. `grok-4.3` stays `omit` only even though a best-practices sentence names `low` and `none`. Penalties and stop error on a reasoning model. The client sends effort only after the field is in the build.

**Reason.** This section stands on its own so a stream build does not have to hunt the menu for the reject list. Both `grok-4.3` sentences stay because picking a winner would invent a pulldown row.

**Avoidances.** Do not disable reasoning on a model that cannot disable it. Do not send `xhigh` as its own depth on `grok-4.5`. Do not merge the multi-agent spellings. Do not add `none` or `low` to `grok-4.3` from the best-practices sentence alone. Do not send `presence_penalty`, `frequency_penalty`, or `stop` to a reasoning model. Do not let a silent default decide a result once the field is sent.

**semantic-key:** PK-5690

## Encrypted reasoning is not the readable text

Encrypted reasoning belongs to the Responses API, and chat completions has no field for that ciphertext.

Pass `include` with `reasoning.encrypted_content` when that item is requested. On Responses, `grok-4.7` returns `reasoning.encrypted_content` on every response whether or not `include` lists it, together with the encrypted outputs of any server-side tools. Reasoning items in the output carry `encrypted_content`. Pass those items back unchanged in the next request's `input` so the model keeps its reasoning across turns when the client manages the history.

That default return does not stop the vendor from storing the thinking trace server-side for rehydration. Clients that ignore the field keep working as before. Whether the response itself is stored for `previous_response_id` is governed by `store`, not by `include`.

The Vercel AI SDK includes encrypted reasoning automatically unless `store` is false.

Chat completions is unaffected. It has no field for the ciphertext.

The best-practices page says to use the Responses API for reasoning models. It says Chat Completions is deprecated, and that it is offered only as a legacy endpoint. It has no field for the model's encrypted reasoning, so every multi-turn or tool-calling conversation sent through it discards the model's earlier thinking. New capabilities arrive on the Responses API first. The page also says results from Chat Completions understate what the reasoning models can do, especially across multiple turns and tool calls. A comparison guide is named on that page. This note did not fetch it, and it does not map fields from it.

Two ways the best-practices page keeps reasoning across turns, both on Responses:

Chain requests with `previous_response_id` while `store` is on, which is the default. The vendor stores the reasoning with the response and restores it.

Manage the history with `store` false. Request the reasoning with `include` set to `reasoning.encrypted_content`, and append every output item to the next `input`.

Prefer streaming on that page so the connection survives a long generation. The final `response.completed` event carries the encrypted items to send back.

Leaving encrypted reasoning out is the most common cause of a cache miss on a reasoning model. `prompt_cache_key` is already a body field on the menu. The best-practices page says to give each conversation its own key and only append. Cache hits are not guaranteed.

This warning stays so the choice is recorded. It does not move the cell. The cell stays `POST https://api.x.ai/v1/chat/completions`. A multi-turn remote cell today sends `messages` of `system`, `user`, and `assistant` string content. When the thinking control is off, `stream` is false. That shape does not carry encrypted reasoning, and the docs say the earlier thinking is discarded.

The readable summary is not sent back as a later turn's messages. Sending the summary back would bill it as input.

**Truth.** Ciphertext is `reasoning.encrypted_content` on Responses. `store` governs `previous_response_id`, not `include`. Chat completions has no ciphertext field and discards earlier thinking. This cell does not move.

**Reason.** The vendor's own page says the legacy endpoint drops earlier thinking. The product keeps that endpoint because the cell contract is chat completions. The warning is filed so a later turn does not pretend the summary or the ciphertext is in the message list.

**Avoidances.** Do not move the cell to Responses because the best-practices page prefers it. Do not invent a ciphertext field on chat completions. Do not map the unfetched comparison guide. Do not send the readable summary back as input. Do not promise a cache hit from `prompt_cache_key`. Do not treat `include` as the switch for `store`.

**semantic-key:** PK-5691

## Timeouts

The named remote request cap is 1800 seconds, connect stays 10 seconds, and the published longer literals are not adopted.

The streaming page cautions that, when streaming a reasoning model, the client should manually override the request timeout so the connection is not closed early.

Published literals, not adopted:

Python OpenAI samples set `httpx.Timeout(3600.0)`.

Curl samples use a 3600 second maximum.

The xAI Python SDK samples set `timeout` to `3600`.

The JavaScript OpenAI sample on the streaming page sets `timeout` to `360000`.

The best-practices page says allow at least 900 seconds per request, and 1800 seconds or more for long-running evals.

Connect stays 10 seconds. The named cap is 1800 seconds for the request, including a thinking stream. The client in the tree still uses 120 seconds until that number is built. 900 and 3600 are not adopted. The JavaScript literal 360000 is not adopted.

A death at the HTTP cap is a hard transport miss. The public line stays the redacted `Grok HTTP` line, at most 180 characters, with the key replaced by `[redacted]`, newlines as spaces. The `garnered` line still caps at 240 characters.

The owed-answer clock is a different job. It starts when one member has stored a response and another member still has no answer. It uses the same named cap. It is not the HTTP timeout.

**Truth.** Connect 10 seconds. Named request cap 1800 seconds. Tree cap 120 seconds until the build. Not adopted: 900, 3600, and 360000. HTTP death is a hard transport miss. The soft clock is separate.

**Reason.** A reasoning stream can outlive a short client timeout. The named cap is the one this product uses, including the stream, so the vendor samples do not become a second number. Keeping 120 until the build prevents a page from pretending the tree already waits 1800 seconds.

**Avoidances.** Do not adopt 3600, 900, or 360000. Do not change the connect timeout from 10 seconds. Do not treat the owed-answer clock as the HTTP cap. Do not leave the key in the timeout error line.

**semantic-key:** PK-5692

## What he still names

The stream, the pane, and the 1800 second cap are named, and the local think level stays unset.

The thinking-stream section names the stream and the pane. The request cap is 1800 seconds. Local `think` is supported on the same control. It is not a level string. It is not a host probe. It does not stream the local engine.

`stream` true is sent for a remote cell whose thinking control is on, and only after that build is in the tree. Until then the client keeps `stream` false. `deferred` true and tool fields stay off the wire until those builds are named.

**Truth.** Cap 1800 seconds. Remote stream only when the control is on and the build exists. Local think is boolean true or unset. Deferred and tools stay off.

**Reason.** The open names are the ones a build still has to land. Leaving them listed stops a reader from treating a filed behavior as code already in the tree.

**Avoidances.** Do not send `stream` true before the build. Do not send a local think level. Do not probe the host model. Do not stream the local engine from this control. Do not send `deferred` or tool fields early.

**semantic-key:** PK-5693

## Thinking stream on the card

Thinking text is off by default, and when it is on the remote cell sends `stream` true and the summary paints as the deltas arrive.

The factory value is `stream` false. Only an enabled control flips the switch on the API. The text streams into a pane. The later reading of that pane is a three-line strip above the BB1 bar and under the main canvas, not a drag-height card split. The earlier card-split fit stays recorded. The strip is the pane. The widget is a read-only text area because the text is plain. A `v` minimizes the strip and restores it. No type is written by this note.

The height of the earlier card fit was set by drag. The strip replaces that placement. Three lines are viewable.

**Truth.** Factory off. On sends `stream` true for that remote cell only. The pane is the three-line strip. The widget is a read-only text area. Thinking text drops with the session.

**Reason.** The operator asked for streaming thought that stays quiet until enabled. A bare finished-reply frame cannot carry the deltas, so the stream is a separate kind. The strip replaced the card split so one shared surface shows the run that is thinking.

**Avoidances.** Do not enable the stream by default. Do not show the pane on a local cell from the remote switch alone. Do not treat the earlier card split as the live placement. Do not use a rich-text pane when the published stream is plain summary text. Do not write a vault column for the thinking string.

**semantic-key:** PK-5694

## The switch

The thinking switch is `stream` true on the chat-completions POST, and there is no second remote field for it.

`reasoning_effort` is depth. It is not the switch, and this build does not send it until the document holds a value other than `omit`.

The factory value is `stream` `false`. The modal is the editor. The pulldown rows are `false` and `true`. There is no second checkbox on the card. The first confirm writes the standing default, including this value. A later edit writes that cell only.

The pane is shown when that cell's `stream` is `true` and the provider is `grok`. A local cell does not show the remote pane and does not use this stream. Changing the target away from `grok` hides the pane, and the next run does not stream. The stored document may remain, so a return to `grok` shows the pane again when `stream` is still `true`.

The document is cell state, copied with the other remote settings. The copy runs when the operator selects the remote door: the nearest earlier cell on the same canvas, walking backward. A copied `true` enables the new cell. A canvas with no earlier remote cell uses the standing default, and the factory value before any confirm is `false`. Creating a turn does not copy. This section does not add a vault column.

The execute text carries `think_stream: true` only when the control is on. Absent means false, the same rule as `ephemeral`.

**Truth.** One switch, `stream` true, factory false, edited in the modal, no second checkbox. The pane follows provider `grok` and the stored boolean. A copied true enables the new cell.

**Reason.** The vendor sample has no second flag for the summary. Depth stays a different field so the operator can think without streaming, or stream without changing depth.

**Avoidances.** Do not add a second remote think field. Do not send `reasoning_effort` as the switch. Do not show the remote pane on a local cell. Do not stream after the target leaves `grok`. Do not copy settings when a turn is created. Do not add a vault column.

**semantic-key:** PK-5695

## Off

Off keeps the one-shot path: one complete call, one data frame of the full reply, then end of stream.

`LlmService.complete` stays one shot and returns one chat response. The remote request body still ends with `stream` false. The facade still calls the router complete once, then one stream-channel data frame of the full UTF-8 reply, scope payload fragment index 0 and fragment count 1, then end of stream. The accept path still treats that data frame as the whole answer: remember the answer, clear the run, then apply pending work.

The stream channel stays `0x0003`. A flag-off data frame is the whole reply and is never scanned for a kind byte.

**Truth.** Off means `stream` false, one complete, one data frame, fragment index 0, fragment count 1, then end of stream. That data frame is the whole answer.

**Reason.** The finished-reply frame already ends the run. Leaving the off path on that frame keeps every existing cell working when the control is false.

**Avoidances.** Do not scan a flag-off data frame for a kind byte. Do not open the stream reader when the control is off. Do not send `stream` true on the off path.

**semantic-key:** PK-5696

## On

On is that remote cell only, for that run, and the same server thread reads the server-sent events.

The execute text gains `think_stream: true`. Absent means false, the same rule as `ephemeral` and the tool-calling flag. The notebook sets the line only when the provider is `grok` and the control is on. The server ignores the flag for any other provider.

The facade takes the stream path only when the flag is on. That path does not call the router complete. The remote service grows a stream method used only there. The server thread that already calls complete is the thread that reads the events. The desktop package does not grow a reactive stream type. The local engine stays on complete. The cell stays `POST https://api.x.ai/v1/chat/completions`. It does not move to Responses.

The body sends `model`, `messages`, and `stream` true. It also sends `reasoning_effort` when the document holds a value other than `omit`. No other menu field rides along. The reader follows arrival order on `choices[0]`. A `delta.reasoning_content` string is thinking text. A `delta.content` string is reply text. `data: [DONE]` ends the run. A loop that prints only `delta.content` misses the thinking text. Usage on a chunk is not painted. The role on a delta is not painted. An empty delta is skipped.

Connect stays 10 seconds. The named request cap is 1800 seconds. A stall past that cap fails the run the way a timeout fails today. The client in the tree still uses 120 seconds until that number is built. 900 and 3600 are not adopted.

The run starts by clearing that cell's thinking text and reply on screen, so the deltas append from empty. The stored reply is not cleared until the stream ends and a full answer is stored. A non-2xx before any token stays the failed path already on this page. A drop mid-stream fails the run, does not replace the stored reply, and leaves the text already painted on the cell for the session.

**Truth.** Flag `think_stream: true` selects the stream method on the existing server thread. Body is `model`, `messages`, `stream` true, and effort only when not `omit`. Reasoning deltas and content deltas stay apart. A mid-stream drop does not replace the stored reply. Screen text already painted stays for the session.

**Reason.** The one-shot reader cannot parse an event stream. Reading on the thread that already calls complete avoids a second client and a reactive type in the desktop package. Clearing the screen without clearing the stored reply keeps a failed stream from wiping a previous answer.

**Avoidances.** Do not call router complete on the stream path. Do not add a reactive stream type in the desktop package. Do not stream the local engine on this path. Do not move the cell to Responses. Do not paint usage or role. Do not send other menu fields. Do not replace the stored reply on a mid-stream drop. Do not adopt 900 or 3600.

**semantic-key:** PK-5697

## The wire

A thinking delta is its own one-chunk frame with a kind byte, and a bare data frame remains the finished reply.

A thinking delta cannot be a bare data frame. The accept path treats every stream-channel data frame as the finished reply and then clears the run. The first token would become the answer and the run would end. Mood replies use that same channel and append on a bare data frame. `0x0003` stays the cell model stream. The constitution file stays as written. `0x0004` stays tools. `0x0100` stays unallocated. Fragment index 0 on a one-chunk frame stays a codec offset. The fragment flag is not the kind.

Each delta is its own one-chunk frame: fragment index 0, fragment count 1, the cell id, then the body. The body is one ASCII kind byte and then UTF-8 text.

The later frame reading is type delta `0x06` on channel `0x0003`. The payload after the 24-byte scope prefix is one ASCII byte `t` or `c`, then the UTF-8 text.

`t` appends to the thinking text and paints the pane. It does not remember the answer. It does not clear the run. It does not replace the reply.

`c` appends to the reply and paints the reply area. It does not clear the run. It does not replace the thinking text.

The kind byte is read only for a cell whose control was on for this run. A flag-off data frame is the whole reply and is never scanned for a kind. A reply that begins with the letter t stays the reply. The server sends a kind byte only when the execute carried `think_stream: true`. A frame for the mood cell stays the mood path. A kind byte is not appended into the mood transcript.

`data: [DONE]` is end of stream. End of stream stores the accumulated reply through remember-answer and then clears the run. The thinking text is not that store. The finished reply is one data frame at the end, and the client stores that text. A delta does not store the cell and does not end the run. A thinking chunk is not a data frame.

Stop still wins. Consuming stop drops a late `t` or `c`, marks the run cancelled, and does not store the partial reply.

**Truth.** Channel `0x0003`. Tools stay `0x0004`. `0x0100` stays unallocated. Kind `t` is thinking. Kind `c` is reply. Delta type is `0x06`. Scope prefix is 24 bytes. End of stream stores only the accumulated reply. Stop drops a late delta and does not store the partial reply.

**Reason.** The existing data frame ends the run, so the first thinking token would be stored as the answer if it rode that frame. A kind byte read only when the flag was on keeps a reply that begins with the letter t intact. Mood traffic stays on the bare data path.

**Avoidances.** Do not send a thinking delta as a bare data frame. Do not allocate `0x0100` for the kind. Do not use the fragment flag as the kind. Do not scan a flag-off frame for `t` or `c`. Do not append a kind byte into the mood transcript. Do not store thinking text as the answer. Do not store a partial reply after stop.

**semantic-key:** PK-5698

## The pane

The live pane is a three-line read-only strip above the BB1 bar, and the earlier card split remains the fit that was replaced.

The operator asked for a pop-out text pane whose height is set by drag. The card's prompt and reply are text areas, both from the shared area helper: line wrap, word wrap, the field background, the mono font, and the view-row count. They sit in a vertical split with resize weight 0.5. The split tip already says to drag the bar. A merge sink has no prompt. Its reply scroll is the center. A collapsed card has no areas. A second text class on one card is the extra surface. The earlier thinking pane was another area, read-only, same background and mono font. The foreground stays off the reply's output yellow so the pane does not read as a second answer. An empty pane uses the gray already used for an empty reply. A ghost card paints the pane gray. The published stream is plain summary text, so a wrapping area holds it. A rich-text pane is the strike, not the filed widget.

The pane is on the card in that earlier fit. It is not a dialog and it is not the inspector. It is present only while the control is on.

A normal open card in that fit nests two vertical splits. The outer split keeps the prompt on top. The inner split puts the thinking pane over the reply. Either bar drags. Resize weight stays 0.5. The open-card height helper still sizes the open card. The drag is the height control. That fit does not add a second row-count control for the pane.

A merge sink with the control on is one vertical split, thinking pane over the reply, and the prompt stays absent. A group run still does not send the merge, so a group run does not stream into that pane. A member with the control on streams into that member's own pane.

A collapsed card stays the one-line reply. The pane is not built while the card is collapsed. Opening the card shows the pane when the control is on. The brief line stays the reply.

Turning the control off hides the pane and keeps the session text. The next run sends `stream` false.

The same turn the settings button is built, the button, OK, Cancel, and the split bar that sizes the pane each get a tooltip. The tip says what the control is and what the click or the drag does. No control is built in this note. The layout page is not appended. The exact chrome stays light.

The later reading replaces that card split. The pane is a strip above the BB1 bar and under the main canvas. Three lines are viewable. A `v` minimizes it and restores it. A call that produces no thinking hides the strip. It comes back when thinking occurs again. The widget is still the read-only text area. While a run is producing thinking, the strip shows that run. A group shows the member that is running. The last member that produced thinking stays up until a later call hides the strip or replaces the text. The card split above is the earlier fit. This strip is the pane.

**Truth.** Live placement is the three-line strip above the BB1 bar, minimized by `v`, hidden when a call produces no thinking. The widget is a read-only text area. The earlier fit is a nested vertical split, resize weight 0.5, drag for height, gray when empty, not the reply yellow. A group does not stream into the merge.

**Reason.** Plain summary text does not need a rich-text pane. One strip above the canvas shows the run that is thinking without a second answer color. The card split stays on the page so a strike can name it again.

**Avoidances.** Do not use the reply yellow for the thinking text. Do not build the pane on a collapsed card. Do not stream a group into the merge pane. Do not make the pane a dialog or an inspector line. Do not add a second row-count control in the card fit. Do not append the layout page. Do not treat the card split as the live placement after the strip is named.

**semantic-key:** PK-5699

## What the text is

Thinking text lives on the in-memory cell for the session, and it is not the stored reply and not a later turn's messages.

A card rebuild in the session paints it again. It is not a vault column. A restart drops it. The inspector does not list it. View stays the stored reply. The stored reply stays the reply, written once when the stream ends.

The thinking text is not a later turn's messages. Chat completions drops earlier thinking, and sending the summary back would bill it as input. It is not part of the merged user message, and it is not part of the merge reply. Each member remains its own call.

**Truth.** Session memory only. Not a vault column. Not an inspector line. Not a later message. Not part of the merge. The stored reply is written once at the end of the stream.

**Reason.** The summary is a display of this run. Putting it in the vault or in the next prompt would bill it as input and would pretend chat completions kept the earlier thought.

**Avoidances.** Do not add a vault column. Do not list the text in the inspector. Do not send it as a later message. Do not fold it into the merged user message or the merge reply. Do not write it into the stored reply before the stream ends.

**semantic-key:** PK-5700

## Left as they are

The local think control, the request cap, tools, deferred completion, and the local context field stay on their own rules.

Local `think` is the consideration in the next section, and the later reading supports it on the same control. This remote switch does not by itself set the local field. The named request cap is 1800 seconds. The client in the tree still uses 120 seconds until that number is built. Tool fields and `deferred` stay off the wire. No `num_ctx` on the remote body. No live POST in this note.

A context length of 0 omits the local field. The published example is 8192. The code default model is `qwen2.5-coder:7b`. The published local base is `http://localhost:11434`. An operator file may override the model or the context length, and that file stays off this page.

**Truth.** Remote body has no `num_ctx`. Tools and deferred stay off. Cap name is 1800 seconds. Tree timeout is 120 seconds. No live POST.

**Reason.** The stream build must not drag the local context field, the tool list, or a deferred call onto the remote body. Those stay out until a separate rule names them.

**Avoidances.** Do not put `num_ctx` on the remote body. Do not send tools or `deferred` from this section. Do not make a live POST. Do not change the tree timeout until the 1800 second cap is built. Do not publish a private model name or a private context length.

**semantic-key:** PK-5701

## Fit he can strike

The stream fit is off by default, on sends `stream` true for that remote cell, and the summary paints as the deltas arrive.

These are the fit choices inside that rule. A strike names the replacement.

The pane is the shared text area. The operator asked for a rich-text pane. The filed widget stays the text area because the text is plain. Name the rich-text pane if the area is the wrong fit.

A copied document can turn a new cell on. The factory stream is `false`. The first confirm writes the standing default.

The thinking text lasts for the session. A vault column waits until one is named.

The kind bytes are `t` and `c`, read only for a run that set the flag. The channel stays `0x0003`.

The earlier sentence put the pane on the card. The later sentence puts the strip above the BB1 bar. The strip is the pane. A floating window is not the cut.

**Truth.** Factory stream false. Kinds `t` and `c` on channel `0x0003` only when the flag was set. Session lifetime. Text area, not a floating window. The strip is the live pane.

**Reason.** Each fit is a choice a strike can replace without reopening the behavior: off by default, on streams that cell, summary paints as deltas arrive, height follows the strip.

**Avoidances.** Do not switch the widget to a rich-text pane unless a strike names it. Do not add a vault column. Do not read kind bytes on a run that did not set the flag. Do not move the channel off `0x0003`. Do not make the pane a floating window. Do not treat the card placement as the live pane after the strip is named.

**semantic-key:** PK-5702

## Ollama thinking, for consideration

Local thinking is a separate rule from the remote field, and the adopted reading turns it on with the same control.

The early note does not set `think`, does not stream the local engine, and does not probe the host model. The later reading supports local thinking on the control already named for the remote door, factory off.

The authority pages are `https://docs.ollama.com/capabilities/thinking`, `https://docs.ollama.com/capabilities/streaming`, and `https://docs.ollama.com/api/chat`.

The thinking page does not print a closed catalog. It says to see the full list, and it links `https://ollama.com/search?c=thinking`. This filing did not open that search. It did not call a private host. It does not say whether a configured host model supports thinking. A private model name stays off this page. The code default model is `qwen2.5-coder:7b`. An operator file may override the model, and that file stays off this page.

The published local base is `http://localhost:11434`.

**Truth.** Local `think` is not a remote body field. The adopted control is the shared one, factory off. No host probe. No private model name on this page.

**Reason.** The local engine and the remote door must not share a request field. The same operator control can still mean "show thought" on whichever door the cell is using.

**Avoidances.** Do not put `think` on the remote body. Do not probe the host from this page. Do not publish a private model name. Do not open the vendor search as if it were this product's catalog. Do not call a private address.


**semantic-key:** PK-0679

## What a thinking model returns

A thinking-capable local model emits a `thinking` field that separates the reasoning trace from the final answer.

The page names three uses: audit the steps, animate the trace in a UI, or hide the trace when only the answer is needed.

Discover the controls per model with `POST /api/show`. The response may include a top-level `thinking` object with `values` and `default`. The published example is model `gpt-oss`: `values` is `low`, `medium`, and `high`, and `default` is `medium`. That example is the page's example. It is not this host's model. This product does not probe `/api/show` and does not send a level string.

`values` may be the booleans `true` and `false`, or model-defined strings.

`default` is used when `think` is unset.

A `values` list of only `false` means the model does not support thinking.

If `thinking` is omitted, the model has no thinking metadata. It might still think based on its existing behavior.

**Truth.** The trace is `thinking`, apart from the answer. `/api/show` may return `values` and `default`. The published example model is `gpt-oss` with default `medium`. This product does not call that probe and does not send a level.

**Reason.** The vendor page leaves the catalog open and puts the controls on the model itself. Copying the example model or its default would publish a host this product did not probe.

**Avoidances.** Do not treat `gpt-oss` as this host's model. Do not call `POST /api/show` from this product. Do not send a level string. Do not assume omitted metadata means the model cannot think. Do not treat a values list of only `false` as a thinking model.

**semantic-key:** PK-5703

## The think field

`think` is a request field on `/api/chat` and on `/api/generate`, and this product sends `true` or leaves it unset.

The chat schema names the type `ThinkValue`: boolean, string, or null.

`true` requests thinking output.

`false` requests no thinking output, if the model permits it.

`null` uses the model default.

A string selects a supported level from `thinking.values`. The string must match `/api/show` exactly. Numbers are not supported. An unsupported name uses the model default.

Chat returns `message.thinking` and `message.content`. Generate returns `thinking` and `response`.

The chat schema describes `ChatResponse.message.thinking` as the optional deliberate thinking trace when `think` is enabled. `ChatStreamEvent.message.thinking` is partial thinking text when `think` is enabled.

The `ChatMessage` object in that schema requires `role` and `content`. The properties shown are `role`, `content`, `images`, and `tool_calls`. `thinking` is not a property on that request-message object. The streaming page separately says the next request must carry the accumulated thinking. Both stay. This page does not invent a request `ChatMessage.thinking` property that the schema extract omitted.

The page's own examples name `qwen3` in the curl and Python samples, and `deepseek-r1` in the JavaScript sample. Those names are the samples. They are not a catalog, and they are not this host's model id. The code default model is `qwen2.5-coder:7b`.

`options.num_ctx` is a different field. It is the context length. It is not `think`. A positive configured integer is sent as `options.num_ctx`. The value 0 omits the field. A negative value omits the field. An omitted key omits the field. The published example is 8192. Do not copy `num_ctx` onto the remote body. Do not publish a private context length. An operator file may override the model or the context length, and that file stays off this page.

The adopted product rule: when the shared control is on and the target is the local engine, the chat request sets `think` true. The call stays the one-shot complete. `message.thinking` paints the same strip. `message.content` stays the reply. When the control is off, `think` stays unset. No level string is sent. `false` is not sent. The local engine is not streamed by this rule.

**Truth.** ThinkValue is boolean, string, or null. This product sends `true` or omits the field. Chat exposes `message.thinking` and `message.content`. Generate exposes `thinking` and `response`. `num_ctx` is not `think`. Zero omits `num_ctx`. The published example context length is 8192.

**Reason.** The schema and the streaming page disagree about whether the next request carries thinking on the message. Both stay, and this product does not invent the missing request property. Leaving `think` unset, rather than sending `false`, keeps the model default when the operator did not ask for the trace.

**Avoidances.** Do not send a level string. Do not send `false` from this control. Do not send a number as `think`. Do not invent `ChatMessage.thinking`. Do not treat the sample names `qwen3` and `deepseek-r1` as this host. Do not copy `num_ctx` onto the remote body. Do not publish a private context length. Do not stream the local engine from this rule.

**semantic-key:** PK-5704

## Streaming

On the local REST API, `stream` defaults to true, and this product's shared control does not stream the local engine.

The chat schema says the same default. The SDKs default `stream` to false. Set `stream` true in an SDK to turn it on.

A thinking-capable model emits `thinking` alongside `content` in each chunk. Detect the first thinking chunk, then switch when `message.content` arrives. Partial `thinking` and partial `content` must be accumulated if the history is kept. That matters for tools: the next request passes the accumulated thinking, the tool call, and the tool result.

The streaming page's Python sample sets `stream` true and does not set `think` in that snippet. The thinking page's stream sample sets `think` true and `stream` true. Both are on the docs.

This product's adopted local path stays the one-shot complete. It sets `think` true when the control is on. It does not set `stream` true on that call.

**Truth.** REST default `stream` is true. SDK default `stream` is false. Vendor samples disagree about whether the snippet sets `think`. This product does not stream the local call.

**Reason.** The one-shot reader already returns `message.thinking` when the field is present. Streaming the local engine would be a second path this control does not open. The vendor default is recorded so a raw REST client is not mistaken for this product's call.

**Avoidances.** Do not assume the SDK default matches the REST default. Do not stream the local engine from the shared control. Do not drop partial thinking if a later history build keeps the trace. Do not treat either vendor sample as this product's request.

**semantic-key:** PK-5705

## What this product does

The local service is one complete call, sets the context length only when it is positive, and sets `think` true only when the shared control is on.

`SpringAiOllamaLlmService.getId()` returns `ollama`. `complete()` calls the chat model once. When the configured context integer is greater than 0 it builds the local chat options and sets the context length only. It does not set `think` until the adopted control is on. It does not stream. A null response throws `Ollama returned no response.`

The host model id is the code default `qwen2.5-coder:7b` unless an operator file overrides it. That operator file stays off this page. This page does not restate a private id. The published local base is `http://localhost:11434`. The published example context length is 8192. The value 0 omits the field.

The early section does not change that service. The adopted reading does: when the control is on and the target is the local engine, the chat request sets `think` true. The call stays `complete()`. `message.thinking` paints the same strip. `message.content` stays the reply. When the control is off, `think` stays unset. This rule does not stream the local engine and does not probe the host model. No level string is sent. The JSON parser reads `message.thinking` from that response JSON. One reader for that field. If the chat response does not expose the raw JSON or the options object has no think setter, stop and ask. Do not add a second HTTP client. Do not probe `/api/show`.

**Truth.** Provider id `ollama`. One complete call. Null response text is `Ollama returned no response.` Context length is set only when the integer is greater than 0. `think` true only when the shared control is on. No second HTTP client. No level string. No `/api/show` probe.

**Reason.** The service already owns the local call. Adding a second client would split the reader. Stopping when the options object cannot carry `think` keeps the product from inventing a private HTTP path.

**Avoidances.** Do not add a second HTTP client. Do not stream this call. Do not probe the host model. Do not send a level string. Do not set the context field when the integer is 0 or negative. Do not publish a private model name or a private context length. Do not leave a null response as an empty success.

**semantic-key:** PK-5706

## Smoke sheet

The smoke sheet collects the open calls and the settled rows, and it does not by itself make the live POST.

The sheet files the calls in one place. It does not answer a row by being a sheet. The build stays off the tree until a plan names it. The client still sends `model`, `messages`, and `stream` false until that build.

Calls still open is one table. A blank line must not end the table. No row is answered by repairing the table.

The reply reader uses the JSON parser to form the request and to read `choices[0].message.content`. An external body that is not the English interchange, not the internal wire text, and not plain text, and that requires parsing, is read and formed with that format's parser. Internal processing stays on the custom mechanics. Server-sent events are a line read beside the JSON object reader. The factory id is `grok-4.3`. The request cap is 1800 seconds. The tree still uses 120 seconds. The status line repaints the engine word on each key in the execution-target field. The tree does not, until that repaint is built. Compile passed. The live POST was not made.

The first smoke is a normal cell. It is named no-burner. The burner pin stays off on that cell. A burner cell aimed at `grok/` still stops on the shell with `burner denied`. Air-gap stays `true`.

The normal-cell exception stands. An API key is the requirement for remote connectivity. It is not a payment credential. The value stays off this page. Both air-gap words stay explicit `true`. A normal cell aimed at `grok/` may read the key and POST. The status line does not show `air-gap` for that target. A burner cell still stops. Any other cloud target still stops on `air-gap`. The constitution lock stays on the constitution. This page is the operative reading for a remote target. The tree still stops a `grok/` target on `air-gap` until the exception is built.

The first contact is the handshake. It runs when the target is `grok/` and the operator presses Run. Local checks run before any request. The probe asserts the key. Valid true lasts for the server process, in memory only. The key is not copied into a new store. Later runs in that process skip the probe and send the cell prompt with the bearer token. A `401` or a server restart clears the presumption. A shell restart does not. A wire miss opens the error slide. The cell prompt is not sent on a failed probe. The handler is specified. It is not built by this sheet. The live POST was not made.

Code waits for the sketch and the formal plan. The sheet does not write the sketch and does not stamp the plan.

Thinking text drops with the session. The pane is the three-line strip above the BB1 bar. Temperature and the completion cap ride. Search stays off this release. Local thinking is supported on the same control. The probe content is `Hello`.

The named build list is approved as the slice. The JSON parser already forms the request and reads the reply. The lock names the sketch. A formal plan waits until it is named. This locked page stays.

**Truth.** One sheet. Factory model `grok-4.3`. Cap 1800 seconds, tree 120 seconds. First smoke is a normal cell with the burner pin off. Probe content is `Hello`. Live POST was not made. Air-gap stays explicit `true`.

**Reason.** The open calls had to sit in one table so a blank line could not hide rows. The sheet records what is settled and what the tree still does not do, without pretending a compile is a live call.

**Avoidances.** Do not split the open-call table with a blank line. Do not make the live POST from the sheet. Do not print the key. Do not set air-gap false. Do not treat the sheet as a stamped plan. Do not turn the burner pin on for the first smoke.

**semantic-key:** PK-5707

## Calls still open

The open-call section is closed by the lock, and a strike on the sketch opens the next sketch serial without editing this page.

The lock names the sketch. A formal plan waits until it is named. This section is closed. The two fits under it stand with the lock. A strike on the sketch smoke opens the next sketch serial. This locked page stays.

The remaining call at the lock was the build itself, and the pause holds until the plan names the build. No type is written by closing the section.

**Truth.** The open-call section is closed. The locked page is not reopened for a gap. The next sketch serial is a new file.

**Reason.** A closed serial stays the source. A gap found later needs a new sketch so the locked reading is not clobbered.

**Avoidances.** Do not edit this locked page to answer a later gap. Do not treat the closed section as a license to write types. Do not reopen the open-call table.

**semantic-key:** PK-5708

## Fits on this chew

Four fits are already filed, and a strike names the replacement without answering them by silence.

The strip widget is a read-only text area. The published stream is plain summary text. Name a rich-text pane if the area is the wrong fit.

Max tokens uses the wire name `max_completion_tokens`. Factory `128000`. Temperature factory `1`. The schema default for `top_p` is `0.95`. The adopted cell omits `top_p`. Name another wire field or another factory number to strike the cap or the temperature. The earlier sentence on this fit listed `top_p` factory `0.95` as if it were sent. The adopted reading leaves it unset.

Local `think` uses the shared control, factory off. On, and the target is the local engine, the request sets `think` true. Off leaves `think` unset. The call stays the one-shot complete. Name a separate local control, a level string, or a stream to strike that fit.

While a run is producing thinking, the strip shows that run. A group shows the member that is running. The last member that produced thinking stays up until a later call hides the strip or replaces the text. Name another member, or say the strip clears when the run ends, to strike that fit.

The adopted analysis sends `temperature` `1` on the cell and leaves `top_p` unset. The probe cap is `max_completion_tokens` `16`. The shared control is carried as `think_stream` on the execute text for both providers.

**Truth.** Text area, completion cap `128000`, temperature `1`, `top_p` omitted on the adopted cell, shared local think, strip follows the running member. Probe cap `16`.

**Reason.** The fits are the choices still open to a named replacement. Leaving them filed means silence does not change the widget, the cap, the local flag, or which run the strip shows.

**Avoidances.** Do not change a fit without naming the replacement. Do not send `top_p` on the adopted cell. Do not clear the strip at the end of a run unless a strike says so. Do not add a local level string unless a strike names one. Do not switch the widget unless a strike names the rich-text pane.

**semantic-key:** PK-5709

## Filed fits he can strike

The filed fits stay unless a strike says the fit is wrong.

The settings file is `~/.casserole/aetheris-grok.yaml`, version 1. It holds a standing default plus a `cells` map. The client yaml, the server yaml, and the server resource file stay as they are. The operator resource file stays off this page.

Effort on the wire: `omit` is a row and is left off. A value equal to the published default is still sent.

On the transition into `grok`, Cancel puts the prior target back when the cell had no document.

On open, a cell entry wins over a different suffix already stored on the target.

The `grok-4.3` effort pulldown is `omit` only. The best-practices line, `none` and default `low`, stays recorded beside it. Those rows were not added.

Factory stream is `false`. The first confirm writes the standing default, including stream. A later cell edit leaves other cells as they are. A copied true stream enables the new cell.

Kind bytes are `t` and `c`, read only for a run that set the flag. The channel stays `0x0003`.

The earlier sentence put the pane on the card. The later sentence puts the strip above the BB1 bar. The strip is the pane.

**Truth.** Version-1 settings file, `omit` left off the wire, first Cancel restores the target, cell entry wins on open, `grok-4.3` is omit only, factory stream false, kinds `t` and `c` on `0x0003`, strip is the pane.

**Reason.** These fits are already on the page. A strike is allowed only when the fit is wrong, so the document path and the kind bytes are not reopened by a later sentence that does not name them.

**Avoidances.** Do not add `none` or `low` to the `grok-4.3` pulldown. Do not overwrite a foreign version. Do not read kind bytes when the flag was off. Do not move the channel. Do not let a cell edit change the standing default or another cell. Do not put the settings in the client yaml, the air-gap yaml, or the server resource file.

**semantic-key:** PK-5710

## Already settled

The settled list is the operative door, and a later adopted analysis on this serial leaves `top_p` off the cell.

The key is `casserole.grok.api-key`. Do not paste it. The public example value is `your-grok-api-key`.

The cell is `POST https://api.x.ai/v1/chat/completions`. Responses stays off this cell.

The modal for now is api `chat`, model, reasoning effort, and streaming.

Thinking text is `delta.reasoning_content` when stream is true, then `delta.content`, then `data: [DONE]`.

The settings store is `~/.casserole/aetheris-grok.yaml`, version 1. The four settings rehydrate. The thinking string lasts for the session.

The empty-model banner is `no model defined`. The state code is `no-model`. The status line shows the banner. The vault token stays `FAILED`. An empty key stays `grok key missing` with reason `failed`.

An HTTP failure puts the redacted `Grok HTTP` line on the error slide, not the cell. The cell stores `FAILED`. The status line shows `failed`.

Any hard or soft failure on a member fails the group. A valid reply stays stored. A failed cell stays blank. The merge stores `FAILED` and concatenation does not run. Hard is a red slide. Soft is a yellow slide. The tree still waits until that rule is built. A cancel-only group stores `FAILED` on the merge and does not open the slide.

The execution target is the free text field. The locked list of host models plus `grok/<model>` is not the control.

After a stored response, a member that still has no answer when the request cap passes is a soft failure. The named cap is 1800 seconds. The yellow slide says `the cell is still waiting and no answer arrived`. The reply field stays clear. The client in the tree still uses 120 seconds until that number is built.

The burner checkbox lives in the model settings. It is specific to that model. The key is `burner_cloud`. Factory false. A local model still receives a burner cell. A cloud model refuses the hop while the box is unchecked. The shell reads the box. The tree still refuses until the control is built.

The air-gap default word is explicit `true` on `aetheris.yaml` and on `~/.aetheris.yaml`. Unset still refuses. `false` opens the cloud door.

The remote exception: an API key is the requirement for remote connectivity. It is not a payment credential. The value stays off this page. A normal cell aimed at `grok/` may read the key and POST while air-gap stays `true`. The status line does not show `air-gap` for that target. A burner cell still stops. Any other cloud target still stops on `air-gap`. The constitution lock stays on the constitution. This page is the operative reading. The tree still stops a `grok/` target on `air-gap` until the exception is built.

The handshake runs on `grok/` plus Run. Local checks run first. The probe asserts the key. Valid true lasts for the server process, in memory only. Later runs skip the probe and send the cell prompt with the bearer token. A `401` or a server restart clears it. Wire misses use the error slide. The handler is specified and is not built by this list.

The one user message on the first POST has content `Hello`. The cell prompt stays off that body.

Thinking text drops with the session. It is not a vault column. The strip above the BB1 bar shows three lines. A `v` minimizes and restores it. No thinking hides it. Thinking brings it back. The widget is a text area.

The adopted fields on the wire are temperature `1` and `max_completion_tokens` `128000` on the cell. `top_p` is omitted. An earlier sentence in this list named factory `top_p` `0.95` as a send. The schema default remains `0.95`. The adopted analysis does not send it. Tools, `deferred`, and search stay off.

Search is not this release. Do not implement it from a parked note.

Local thinking is supported. The shared control, factory off, sets `think` true on a local target. The call stays the one-shot complete. The same strip paints `message.thinking`.

The factory id is `grok-4.3`. The effort pulldown for that id stays `omit` only. The modal is not built by this list. The client still sends the model on the target.

The request timeout is 1800 seconds. Connect stays 10 seconds. The owed-answer clock uses that same cap. The client in the tree still uses 120 seconds until that number is built.

The status line repaints the engine word on each key in the execution-target field. The tree still reads the cell and does not repaint until that build.

The JSON parser reads `choices[0].message.content` and forms the request body. A non-string `content` fails as `grok content shape`. Empty content fails the cell as `grok empty content`. Compile passed. The live POST was not made.

The first smoke is a normal cell. It is named no-burner. The burner pin stays off. A burner cell aimed at `grok/` still stops on the shell.

**Truth.** Settled door: chat completions, key property, factory `grok-4.3`, `Hello`, temperature `1`, completion cap `128000`, `top_p` omitted, air-gap explicit `true` with the normal-cell exception, three-line strip, shared local think, no live POST yet.

**Reason.** The list is the record a builder checks before the plan. Where an earlier sentence and the adopted analysis disagree, the adopted analysis is the send, and the earlier number stays as the schema default that is not sent.

**Avoidances.** Do not paste the key. Do not send `top_p`. Do not implement parked search. Do not adopt `grok-4.7` as the factory id. Do not put thinking text in the vault. Do not treat the tree's 120 second timeout as the named cap. Do not make the live POST from this list.

**semantic-key:** PK-5711

## Outstanding

The outstanding list is what the tree still does not do, and the adopted readings stay the rule.

The menu is not on the wire. The tables are filed. The client still sends `model`, `messages`, and `stream` false. No other body field is sent until the build.

The settings modal is named. The pulldowns are api `chat`, model, reasoning effort, and streaming. The checkbox `burner_cloud` sits on that same modal. The document is `~/.casserole/aetheris-grok.yaml`. The file is not created by this list. Tool rows stay off the wire until tool integration is named.

The live POST path is compiled. It has never been smoked against the remote host. An operator may report the secret is on `casserole.grok.api-key`. This page does not make the call and does not print the value. The public example is `your-grok-api-key`.

The code invents no default remote model beyond the factory id `grok-4.3` on the document. `grok` with nothing after the slash fails as `no-model`. The models table is the menu. The vendor page recommends a newer id for code and for chat. This page does not adopt `grok-4.7`.

The legacy guide sets a 3600 second client timeout for reasoning models. The streaming pages add a caution, samples at 3600 seconds, one script literal of 360000, and a floor of 900 seconds, with 1800 or more for long evals. The named cap is 1800 seconds. Connect stays 10 seconds. The client in the tree still uses 120 seconds until that number is built. 900 and 3600 are not adopted.

Readable text is `delta.reasoning_content` on a chat-completions stream when `stream` is true, then `delta.content`, then `data: [DONE]`. Until that build is in the tree, the client writes `stream` false and does not read `reasoning_content`. Encrypted reasoning stays on Responses. The cell stays on chat completions.

Off by default. On, that remote cell sends `stream` true and the summary paints on the strip. A bare data frame still ends the run, so the build marks a thinking delta apart from the reply.

Local think is supported on the same control. The service does not set it until that control is on, and it does not stream. The host model was not probed.

The empty-model banner is `no model defined`. The state code is `no-model`. The vault token stays `FAILED`. An empty key stays `grok key missing` with reason `failed`. The tree still sends the shared banner until the build.

The service builds a redacted `Grok HTTP` line. The slide shows it. The cell stores `FAILED` and the reply stays. The status line shows `failed`. A transport miss uses the same slide. Named banners stay on the status line.

The air-gap word is explicit `true` on both files. Unset still refuses. `false` opens the cloud door. A choice that weakens security is questioned and verified before it is filed. The agreed default is `true`.

A normal cell aimed at `grok/` may read the key and POST while air-gap stays `true`. The status line does not show `air-gap` for that target. A burner cell still stops. Any other cloud target still stops on `air-gap`. The constitution lock stays on the constitution. The tree still stops a `grok/` target on `air-gap` until the exception is built.

The handshake runs on `grok/` plus Run. Local checks run first. Valid true is server memory only. Later runs send the cell prompt with the bearer token. A `401` clears it. A server restart clears it. A wire miss opens the error slide. The cell prompt is not sent on a failed probe.

The probe user message is `Hello`.

No code for this spec until the sketch and the formal plan name the build. The list does not write the sketch.

The named build list is approved for this slice. The pause holds until the plan.

The burner checkbox is specific to that model. A local model still receives a burner cell. A cloud model refuses the hop while `burner_cloud` is false, and the shell sends while it is true. Factory false. `notebook.burner-key` stays the server door for every other cloud provider.

The free text field stays. The locked list of the host's models plus `grok/<model>` is not the control. The field writes memory on each key and waits for another save before the vault row matches.

The factory model is `grok-4.3`. The effort pulldown for that id stays `omit` only.

The engine word repaints on each key. The tree does not repaint yet.

External JSON is formed and read with that format's parser. Internal English interchange and internal wire text stay on their own readers.

Any hard or soft failure on a member fails the group. A valid reply stays stored. The tree still waits until the group rule is built.

**Truth.** Outstanding means specified and not yet the tree, except the slide and the JSON reader that are already built. Factory `grok-4.3`. Cap named 1800, tree 120. Probe `Hello`. No live POST. No key on the page.

**Reason.** The list keeps the gap visible so a compile of the keyless body is not mistaken for the probe, the modal, the stream, or the group rule.

**Avoidances.** Do not treat the menu as the wire. Do not smoke the live host from this list. Do not adopt `grok-4.7` or a 3600 second timeout. Do not invent a default model for a bare `grok` target. Do not write code before the plan names the build. Do not publish the key.

**semantic-key:** PK-5712

## What the team needs

The operator supplies the secret, and the page already names the banners, the cap, the probe, and the factory model.

The secret is written on `casserole.grok.api-key`. Do not paste it here. The filed commit left the value blank. Restart the server if it was up before the write. The public example is `your-grok-api-key`.

The factory model id is `grok-4.3`. The effort pulldown for that id stays `omit` only. The modal is not built by this list. The client still sends the model on the target.

The air-gap default is explicit `true` on both yaml files. Unset still refuses. `false` opens the cloud door and weakens security. That choice is questioned and verified before it is filed. The agreed word is `true`. A normal cell aimed at `grok/` may read the key and POST while the word stays `true`. The tree still stops that cell on `air-gap` until the exception is built.

The first smoke is a normal cell, named no-burner. The burner pin stays off. A burner cell is still refused on the shell, even when `notebook.burner-key` is true.

The burner-cloud checkbox lives in the model settings. It is specific to that model. A local model still receives a burner cell. A cloud model refuses the hop while the box is unchecked, and the shell sends while the box is checked. The tree still refuses until that build.

The empty-model banner is `no model defined`. The state code is `no-model`. The status line shows the banner. The vault token stays `FAILED`. An empty key stays reason `failed` and message `grok key missing`.

A non-2xx line is the redacted `Grok HTTP` text. It goes to the error slide, not the cell.

The free text field stays. The locked list is not the control. An answer still owed after the request cap is a soft failure. The yellow words are `the cell is still waiting and no answer arrived`.

The smoke is the handshake. It runs on `grok/` plus Run. The probe asserts the key. Valid true lasts for the server process. Later runs send the cell prompt with the bearer token. The probe content is `Hello`. The handler is not built by this list. The live POST was not made.

The modal set is `api` `chat`, `model`, `reasoning_effort`, and `stream`. The cell also sends `temperature` `1` and `max_completion_tokens` `128000`. The probe sends `max_completion_tokens` `16` and leaves `temperature` and `top_p` unset. The cell omits `top_p`. An earlier pair on this list named `top_p` `0.95` as a send. That pair is the earlier reading. A 400 that names the cap is the slide text. Tools, `deferred`, and search stay off.

The named request cap is 1800 seconds, including a thinking stream. Connect stays 10 seconds. The owed-answer clock uses that same cap. The client in the tree still uses 120 seconds until that number is built.

`deferred` true and tool fields stay off the wire until those builds are named. While the control is off, `stream` stays false. The readable thinking text arrives only when `stream` is true.

The thinking text drops with the session. It is not a vault column. The pane is the three-line strip above the BB1 bar. A `v` minimizes and restores it. The widget is a text area.

Local thinking is supported on that same control. Control on and a local target set `think` true. The call stays the one-shot complete. The strip paints `message.thinking`. Control off leaves `think` unset. The host model is not probed.

A change to a banner, the burner door, the target control, or the modal is a later named build. This page does not hold the secret.

**Truth.** Secret off the page. Factory `grok-4.3`. Air-gap `true`. Probe `Hello`. Cell temperature `1`, completion cap `128000`, `top_p` omitted. Probe cap `16`. Request cap 1800 seconds. Connect 10 seconds. Strip is three lines. Local `think` is true or unset.

**Reason.** The names that used to be open are now the product rule. Restarting the server is required because the key property is not live-reloaded. The page still does not hold the secret.

**Avoidances.** Do not paste the secret. Do not set air-gap false for the smoke. Do not turn the burner pin on for the first smoke. Do not send `top_p` on the adopted cell. Do not send tools, deferred, or search. Do not probe the host model. Do not store thinking text in the vault.

**semantic-key:** PK-5713

## Out of this slice

This slice does not make a live POST, does not print the key, and does not put `num_ctx` on the remote call.

No type is required to file the menu, the modal shape, the stream behavior, the empty-model banner, the group rule, the burner checkbox, or the air-gap default. The error slide is the control that is built. The JSON parser on the request and the reply is in the remote service. The factory id is `grok-4.3`. The request cap is 1800 seconds. The tree still uses 120 seconds. The engine-word repaint is named and is not in the tree. Compile passed. The live POST was not made.

No key value on this page. The public example is `your-grok-api-key`. The operator file may hold the secret. The value is not copied here.

No second secret store.

No `num_ctx` on the remote call. It is not a `ChatRequest` field. On the local engine, 0 omits the field. The published example is 8192. The code default model is `qwen2.5-coder:7b`. The published local base is `http://localhost:11434`. An operator file may override the model or the context length, and that file stays off this page.

Until the named build, the client does not send temperature, a completion cap, or top-p. The menu lists them. The adopted build sends temperature `1` and `max_completion_tokens` `128000` on the cell and omits `top_p`.

No streaming code in this slice. When the control is off, complete stays one shot and the client sends `stream` false. When the control is on, the later build reads the events on the server thread that already calls complete. The desktop package does not grow a reactive stream type. Until that build is in the tree, the client does not read `reasoning_content`.

The early local section does not turn `think` on. The adopted reading does, on the shared control. The local service does not stream. `num_ctx` stays the existing local option. `0` still omits it.

No modal and no settings button are created by this slice. The yaml file is not created. The vault column is not.

Tool use stays off. The allowlist stays empty.

Other named vendors stay unbuilt.

A later product stage, the later visual shell, contract agents, and the named memory products stay closed.

The layout page is not appended.

The stage plan is not restamped.

The stage marker stays where the stage plan left it. This slice does not retarget the working map or the action.

**Truth.** No live POST. No key on the page. No `num_ctx` on the remote body. No second secret store. No streaming code yet. No modal file yet. Tools off. Other vendors unbuilt. Stage plan not restamped.

**Reason.** The slice files the door. It does not pretend the tree already has the probe, the stream, the modal, or a live call. Keeping the stage marker still prevents this page from opening a later stage.

**Avoidances.** Do not make the live POST. Do not copy the key. Do not add a second secret store. Do not send `num_ctx` on the remote call. Do not add a reactive stream type. Do not create the yaml file in this slice. Do not turn tools on. Do not build the other vendors. Do not append the layout page. Do not restamp the stage plan. Do not publish a private context length.

**semantic-key:** PK-5714

## Team brief

The brief records the door as it was asked, and the settled reading is the later rule on this page.

The call is already compiled as a keyless shape: `grok/<model>` to `https://api.x.ai/v1/chat/completions`. The operative key order is `casserole.grok.api-key`, then `XAI_API_KEY`, then `~/.aetheris/grok.key`, then `~/.casserole/grok.key`. An earlier sentence named only the environment and the two files. The property is first. A blank property is a miss, and the environment and the two files still apply. A missing key fails the cell with `grok key missing`. `num_ctx` stays off the remote body. The live POST was never made from this brief. The public example value is `your-grok-api-key`.

What was outstanding is not a second client. It is the smoke, the model id, the air-gap word on both yaml files, and four drifts: one banner for a missing key and a missing model, the HTTP line on the slide instead of a bare execute failure, the shell's burner stop beside `notebook.burner-key`, and a free text field where a locked serial named a list. Those drifts are settled as `no-model`, the error slide, the burner checkbox plus the frame bit for this provider, and the free text field.

The vendor required list on `ChatRequest` is empty. `Authorization` and `Content-Type` are the required headers. The client still sends `model`, `messages`, and `stream` false until the build adds the named fields.

Readable thinking is available. On chat completions, `stream` true sends server-sent events. The published sample puts the summary in `delta.reasoning_content`, then the answer in `delta.content`, and ends with `data: [DONE]`. The text is a summarization. Reasoning tokens are billed as part of total consumption. Encrypted reasoning is a different Responses feature. The cell stays on chat completions.

Local `think` is `true`, `false`, `null`, or a string taken exactly from `POST /api/show`. This product sends `true` or leaves the field unset. Chat returns `message.thinking` apart from `message.content`. The local service does not stream. The host model was not probed. The code default model is `qwen2.5-coder:7b`.

Off keeps one data frame of the whole reply, then end of stream. On, the execute carries `think_stream: true`, and the same server thread reads the events. A bare data frame cannot carry the deltas. The kind byte is `t` or `c`, read only for a run that set the flag. The channel stays `0x0003`.

The document is `~/.casserole/aetheris-grok.yaml`. The connection yaml flattens a nested map and holds the connection password, so the document does not go there. The password stays off this page.

The empty-model reason is `no-model`. The message is `no model defined`. The cell stores `FAILED`. Vault tokens stay null, `COMPLETED`, `FAILED`, and `CANCELLED`. An empty key with a model present stays `grok key missing`.

The slide border is red for an unidentified miss. The body is three rows. It stays until Close. Named banners stay on the status line.

Any hard or soft failure on a member fails the group. The free text field stays. The owed-answer cap is the named 1800 seconds. Until that build, the tree uses 120 seconds.

The air-gap default is explicit `true` because `false` opens the cloud door. The normal-cell exception lets a non-burner `grok/` cell POST while the word stays `true`.

The probe content is `Hello`. Valid-true is server memory. The same Run continues after a readable 2xx. The greeting is discarded.

The strip is three lines above the BB1 bar. Temperature is `1`. The cell completion cap is `128000`. `top_p` is omitted. Search stays off. Local thinking uses the same control.

The pause holds until the plan names the build. The layout page stays the layout page. This serial is the remote-call page.

**Truth.** One client. Property-first key lookup. Chat completions. `Hello` then the cell prompt. Kinds `t` and `c`. Factory model `grok-4.3`. No live POST in the brief. No key text.

**Reason.** The brief is the running account of the door. Where an early sentence named a shorter key order or a 120 second request, the later rule on this page replaces it, and the early sentence stays as the shape the tree had.

**Avoidances.** Do not build a second client. Do not drop the property from the key order. Do not put the settings document in the connection file. Do not print the password or the key. Do not move the cell to Responses. Do not treat the brief as a live POST. Do not retarget the stage from this brief.

**semantic-key:** PK-5715

## Slice analysis

The adopted analysis is the reading a plan cuts from, and the rows are adopted as rules even where the tree does not have them yet.

A remote run today stops on air-gap in the shell before any POST. The JSON parser can already form one JSON chat body and read `choices[0].message.content`. The rest of the approved slice is specified and is not in the tree. The pause holds until the plan names the build. This analysis does not by itself approve a sketch.

After a 2xx `Hello` with readable content, that same Run sends the cell prompt. The greeting is discarded and is not stored on the cell. A miss stores `FAILED` and does not send the prompt. A builder must not store the greeting as the cell answer and must not wait for a second click. The prompt the operator typed never rides the first POST.

Keep one valid-true flag in the server process that reads the key. The flag matches that one key. A server restart clears it. A shell restart does not. The shell does not keep a second copy. Two notebooks on one server must not disagree.

One probe gate sits in the server process, beside the one valid-true flag. The states are none, in flight, and valid. The check and the claim share one lock, and the lock is not held during the POST. A Run that finds valid sends that cell's prompt. A Run that finds none becomes the leader and sends the one `Hello`. A Run that finds in flight waits and does not send `Hello`. A 2xx with readable content sets valid and discards the greeting. The leader and each waiter then send their own cell prompts. A miss leaves the gate at none. Each of those cells stores `FAILED` and does not send. The probe belongs to the process. Cancelling the leader cell does not abort `Hello`. Cancel aborts a cell's own prompt call. The three execute slots stay three cell calls. `cell-busy` is not this gate.

One predicate for the normal-cell exception is called by both doors. A normal cell, burner pin off, provider `grok`, model present, both air-gap words explicit `true` and equal, may pass. Unset, a mismatch, a burner, and any other cloud provider still stop. The constitution file stays as written. Today both doors stop the call, so the JSON parser is never reached until the predicate is built.

The execute frame carries `burner_cloud` beside `ephemeral`. The shell copies the checkbox. For provider `grok` the server reads that bit. `notebook.burner-key` stays the door for every other cloud provider. A local burner still runs. An earlier sentence left the bit off the execute text. The adopted sentence puts it on.

Any member `FAILED` or `CANCELLED` fails the merge. Concatenation does not run. A member that already stored a valid reply stays. The hang is in the tree for every group until this rule is built. The analysis first left that build outside the pause. The sketch and the plan take it into this build. Both sentences stay. The plan is the build reading.

The earlier region rule holds the facade mutex through clear, add, and complete. The wire body stays the prompt already built. A later yield on the plan releases the region lock before the provider call, so a pool child and a local complete on the same user may overlap. Clear and add stay inside the region lock. The pool process itself is detailed on the server page. Here the door rule is the overlap and the lock order: the probe lock is not taken while the region lock is held, and the region lock is not held during `Hello`. The semaphore of 3 stays. One pool prompt per user stays on the child gate.

When the cap is built, the remote HTTP timeout is 1800 seconds and connect stays 10. A death at that cap is a hard transport miss. The soft clock stays the wait after some other member has already stored a response. The two clocks keep two jobs. 120 stays until that build.

Read a `stream` true body as event lines. `reasoning_content` goes out as kind `t`. `content` goes out as kind `c`. Both ride the stream channel `0x0003`. The data frame is the finished reply, once, at the end. The cell stores the joined `c` text. The spinner stays the running clock. The strip paints `t`. A thinking chunk sent as a data frame would be stored as the cell answer. The current one-shot reader fails an event body as `grok content shape`.

One per-cell control, factory off, is carried as `think_stream` on the execute text for both providers. Remote on sends `stream` true. Local on sets `think` true on complete, and the JSON parser reads `message.thinking` from that JSON. One reader for that field. The same strip paints it. No new vault column.

The probe adds `max_completion_tokens` `16` and leaves `temperature` and `top_p` unset. The cell sends `temperature` `1` and omits `top_p`. `max_completion_tokens` `128000` stays on the cell only. A 400 that names the cap is the slide text. A strike may keep both sampling fields on the cell. Until that strike, `top_p` is omitted. The vendor schema says alter temperature or top-p. Sending both was the risk this row removes. The schema default of `top_p` is `0.95`. It is not sent.

An empty model is checked in the shell and in the complete path, before the key read. An empty model is `no-model` and `no model defined`. An empty key with a model present stays `grok key missing`. The tree still shares one missing-key exception until that check is built.

Cancel drops the run token and also aborts that cell's HTTP call. A late reply is still dropped. The shared `Hello` still finishes.

This slice does not edit the stage map or the constitution. When a later stage opens, a remote target follows this serial: free text, and a normal cell may POST while air-gap stays `true`. An agent execute uses this same server handler. Thinking text stays out of the vault until a later serial names a column. This row files no later-stage work. A contract that follows only the stage map would build a host-model dropdown and would refuse the remote door. That is why this serial is the operative reading.

The goal still holds. One chat-completions POST, the key as the bearer token, `Hello` only on the first probe body, the cell prompt on the later body, thinking text on the strip and out of the vault, local `think` on the same strip, and the group fail when any member misses.

Rows that protect the goal are the same Run after `Hello`, valid-true in the server process that holds the key, and one probe flight per server process. The exception and the checkbox belong in that same plan so both doors read them.

No code is written by the analysis itself.

**Truth.** Fourteen adopted rows. Same Run continues after `Hello`. One server flag. One probe flight. Both doors share one predicate. `burner_cloud` rides for this provider. Group fail includes cancel. HTTP cap 1800, connect 10, soft clock separate. Kinds `t` and `c`. Shared `think_stream`. Cell temperature `1`, no `top_p`, cell cap `128000`, probe cap `16`. Empty model before the key read. Cancel aborts the cell's HTTP call and not `Hello`. Constitution and stage map stay unedited.

**Reason.** Each row is a place a builder could store the greeting, probe twice, or stop a normal cell on air-gap. Adopting the column makes the plan's job the doors, the probe, and the body, not a second invention.

**Avoidances.** Do not store the greeting. Do not keep a second valid flag in the shell. Do not hold the probe lock during the POST. Do not abort `Hello` on leader cancel. Do not edit the constitution or the stage map from this slice. Do not send `top_p`. Do not send the probe without `max_completion_tokens` `16`. Do not treat the spinner as the strip. Do not add a vault column for thinking text. Do not write the types from the analysis alone.

**semantic-key:** PK-5716

## Ollama think stream

Local thinking streams while the model writes, and a set think string on a text-only prompt pulls each thinking piece on the shared local API.

This page is that slice. It is not a second client, not a live POST, not a pool turn, not a secure-shell turn, and not remote-console thinking. It is not a hand exercise of the window. It does not open a new requirement by itself.

Kind `t` is the piece. The reply is the joined content. The request carries the resolved model, the think option, and `num_ctx` when that integer is greater than 0. The value 0 omits the field. The published example context length is 8192. The code default model is `qwen2.5-coder:7b`. The published local base is `http://localhost:11434`. An operator file may override the model or the context length, and that file stays off this page. A private model name is not published.

The console pane opens when the family is the local engine and the think string is set. The tag is the short model. The pool still opens only when the pool switch is on. The pool process is detailed on the server page. The remote door on the console stays shut. The database pin stays turso 0.8.3. No side move on this plate. This land leaves the working set as it stands.

The Java server leads. The terminal client opens the terminal strip.

**Truth.** Kind `t` while the model writes. Joined content is the reply. `num_ctx` only when greater than 0. Console pane for a set local think string. Pool only when the pool switch is on. Remote console stays shut. Pin turso 0.8.3.

**Reason.** The local trace used to wait until the reply returned. Streaming the piece as kind `t` shows the thought while the model writes, without a second client and without opening the remote console.

**Avoidances.** Do not build a second client. Do not make a live POST from this page. Do not open a pool turn from this plate. Do not stream remote thinking on the console. Do not publish a private model name or a private context length. Do not move the working set.


**semantic-key:** PK-0680

## Team brief

Local thinking for the console used to stay on the host until the reply returned, and the trace now leaves as kind `t` while the model writes.

The page is the local think stream. The Java server leads. The terminal client opens the terminal strip. The shared local API is the reader. There is no second client. The database pin stays turso 0.8.3. The match leaves the working set as it stands. The window stayed closed. The terminal strip opens while the local engine writes the thought. The tag is the short model. The proof stanza is empty of a side move. The Java test run passed, 38 tests. No further seat is required for this plate.

**Truth.** Kind `t` leaves while the model writes. One shared reader. Pin turso 0.8.3. Tag is the short model. 38 Java tests passed. The window stayed closed.

**Reason.** Waiting for the whole reply hid the trace. The brief records that the stream is the trace, not a second product.

**Avoidances.** Do not add a second client. Do not change the database pin. Do not open the window for this brief. Do not treat the brief as a pool turn or a remote console.

**semantic-key:** PK-5717

## Seats

Each duty on this plate is one sentence, and none of them opens a second client.

The rule is that local thinking streams while the model writes.

The page is the local think stream. The Java server leads. The terminal client opens the terminal strip.

The shared local API is the reader. There is no second client. The database pin stays turso 0.8.3.

The match leaves the working set as it stands. The window stayed closed.

The terminal strip opens while the local engine writes the thought. The tag is the short model.

The proof is one empty stanza. The Java test run passed, 38 tests.

No cut is required beyond that proof.

**Truth.** One reader, pin turso 0.8.3, short-model tag, 38 tests, window closed, working set unchanged.

**Reason.** The duties separate the reader, the strip, and the proof so the stream does not become a client, a pin change, or a window exercise.

**Avoidances.** Do not add a second client. Do not change the pin. Do not open the window. Do not move the working set. Do not assign the strip a tag other than the short model for a local turn.

**semantic-key:** PK-5718

## The seat

A set think string, a sink, and a text-only prompt stream the local chat API with stream true, and kind `t` is each thinking piece.

The reply is the joined `message.content`. The generation metadata has no thinking key, so the cell blob stays quiet on a stream.

The reader is the shared local API bean. This service does not build a second client. The chat model's stream helper on the pinned client leaves `message.thinking` unread, so the pull uses the streaming chat path.

`think` true sends the boolean enabled value. Any other non-empty level sends a level value. Empty and false leave think unset. The disabled value is not sent.

The options copy starts from the chat-model defaults. The caller model wins, then the bean model, then `spring.ai.ollama.chat.options.model`. The code default model is `qwen2.5-coder:7b`. An operator file may override the model, and that file stays off this page. `num_ctx` is set when the configured integer is greater than 0. The value 0 omits the field. The published example is 8192. A private context length is not published. A missing model says `no model defined`.

The model worker takes one frame, runs the sink, then takes the next. The pull is limit-rate 1 and a blocking stream. Closing the stream cancels the HTTP call. An empty pull returns empty content. The chat shell still reports an empty reply. A stopped cell throws `CallStopped`. A runtime failure stays a runtime and the facade reports `engine-down`. The stream is not retried.

Empty, a null sink, an image, a tool call, and a tool result stay on the one-shot call. The one-shot still reads thinking from the metadata key when the usage counts are present.

The console block closes in the outer finally only when a chunk was written. An empty chunk and a chunk that does not fit leave the block closed. Pool thought still marks the block open after the send returns. The pool process is detailed on the server page. The door rule is that mark.

The Java pane follows the family of the turn. The pool uses the pool switch. The local engine uses the stored think string. That string is set from the console set and from the shell result, because the dialog checkbox closes with the dialog. The remote door stays shut. The notebook strip still ignores the nil cell.

The seated terminal client opens the terminal strip. The pool stays the pool switch and the tag is `pool`. The local engine uses the stored think string and the tag is the short model. The remote door stays shut. The end of the turn closes the block even when the gate has shut.

The published local base is `http://localhost:11434`.

**Truth.** Stream true on a text-only prompt with a set think string. Kind `t` per piece. Joined content is the reply. Caller model, then bean model, then the options model. `num_ctx` only when greater than 0. Missing model text is `no model defined`. Stop throws `CallStopped`. Runtime failure is `engine-down`. No retry. Disabled is not sent. Empty and false leave think unset. Pool tag is `pool`. Local tag is the short model. Remote stays shut.

**Reason.** The stock stream helper drops `message.thinking`, so the pull uses the streaming chat path on the shared bean. Limit-rate 1 keeps one frame in flight. Closing the stream cancels the HTTP call so a stopped cell does not keep spending the engine. The checkbox dies with the dialog, so the stored string has to be set from the console and from the shell result.

**Avoidances.** Do not build a second client. Do not send the disabled value. Do not retry the stream. Do not put an image, a tool call, a tool result, an empty prompt, or a null sink on this stream. Do not open the remote console. Do not tag a local turn as `pool`. Do not leave the block open when no chunk was written, except the pool mark after send. Do not ignore a missing model. Do not publish a private model name or a private context length.

**semantic-key:** PK-5719

## The stanza

No side move is on this plate, and the land leaves the working set as it stands.

The stream, the kind byte, the shared reader, and the strip are the change. The working set is not.

**Truth.** The working set is unchanged. There is no side move.

**Reason.** A proof that moves the working set would mix this plate with unrelated edits. The stanza stays empty so the stream stands alone.

**Avoidances.** Do not smuggle a working-set edit into this plate. Do not treat an empty stanza as a missing requirement.

**semantic-key:** PK-5720

## Proof

The Java test run passed 38 tests, the terminal crate test passed 153 tests, and the window stayed closed.

The Java proof is a test run from the server tree. The window stayed closed. No live POST. No pool turn. No secure-shell turn.

The window stayed closed. No session start. No chrome run.

A hand exercise of this slice was not clicked from the page. A later operator report says the slice worked. That report files the smoke. It does not open a new row.

A hand exercise of the crate move was not clicked from the page. A later operator report says the crate move worked. That report files the smoke. It does not open a new row.

Commit identifiers stay off this page.

**Truth.** 38 Java tests passed. 153 crate tests passed. No live POST. No pool turn. No session start. No chrome run. The window stayed closed. A later smoke files success and opens no row.

**Reason.** The proof is the closed window and the test counts. An operator smoke confirms the slice without becoming a new requirement.

**Avoidances.** Do not open the window for the proof. Do not make a live POST. Do not start a session or a chrome run as the proof. Do not open a new row from the smoke sentence. Do not publish a commit identifier.

**semantic-key:** PK-5721

## Aetheris Grok API formal plan

This plan is the stamped run-book for the locked remote serial, and where it is thinner the locked serial wins.

The stamp files the plan and names the build. It does not move the stage marker. It does not retarget the working map or the action.

The canonical file is the plan on the serial shelf. The dated review path stays off this page.

The source is the locked serial and the locked sketch. The region lock and the group fail are in this cake. This page does not rewrite the stage map, the constitution, the stage plan, or the stage sketch.

Eight packages. A normal cell, burner pin off, target `grok/` plus a model id, both air-gap words explicit `true` and equal, runs the local checks, sends one `Hello` when this server process has no valid key yet, then sends the cell prompt. The greeting is discarded. The cell stores the prompt reply. Thinking paints the strip. The vault stores the reply.

The call stays `POST https://api.x.ai/v1/chat/completions`. The public key shape is `your-grok-api-key`.

**Truth.** Stamped run-book. Eight packages. Locked serial wins where this page is thin. Stage marker stays. Region lock and group fail are in the cake. No key on the page.

**Reason.** The sketch was the smoke. The stamp is the license to build, in order, without retargeting the stage and without rewriting the higher pages.

**Avoidances.** Do not retarget the stage, the working map, or the action. Do not rewrite the constitution, the stage map, the stage plan, or the stage sketch. Do not treat a thinner sentence here as the winner over the locked serial. Do not print the key. Do not publish the dated review path.

**semantic-key:** PK-5722

## Stamp record

The stamp updates the sketch in place, writes this plan, and only then builds the eight packages.

Update the sketch in place. The state becomes locked. The region package and the group-fail package replace the neighbor section. The out table drops those two neighbor rows. The as-built rows for the group merge and the region point at those packages. A gap found after this lock opens the next sketch serial. The locked serial stays locked and is not edited.

Append one sentence on the layout page under the group-fail rule, after the sentence already there. Do not rewrite the locked paragraphs, the sentence that leaves the target-control row closed, or the sentence that the tree cap stays 120 seconds until the named cap is built. The new sentence says this build includes the group fail, any member `FAILED` or `CANCELLED` fails the merge, and a group whose only miss is `CANCELLED` does not open the error slide.

Write this body as the plan, stamped and approved. The same body. The dated folder name stays off this page.

Record the ledger, then one local commit of those spec paths only, before any type edit. The subject records that the plan is stamped. Do not retarget the stage-plan heads, the remote-serial head, or the layout head.

Then build packages 1 through 8 in order. Anonymous classes only. No lambdas in product types. No boilerplate generator. No formal test class is added by this plan. No new wire key. No new vault column. A source tag on a new type uses a key already on that layer, the router side or the wire side. Prefer methods on the types already named.

After the last package, compile quiet and skip tests from the server tree. The window stays closed. No live POST. Air-gap files stay explicit `true`. Do not read or edit the server resource file, the connection yaml, the key notes, or the home air-gap file. Do not print the key. Do not set air-gap `false`. Do not edit the context-length field.

The subject of the build ledger records that the cake is in the tree. The stage marker, the action, and the working map stay. Do not commit the IDE workspace, the model file, the editor caches, the requirements notes, or unrelated deletions.

Do not commit memory files.

**Truth.** Sketch updated in place. Plan stamped. Spec commit before types. Packages 1 through 8 in order. Quiet compile, skip tests, window closed, no live POST, air-gap left `true`, key not printed, context length not edited.

**Reason.** The stamp is the gate. Writing types before the spec commit would build from a page that is not yet the record. Leaving the layout sentences intact keeps the tree cap and the free-text field from being rewritten by a filing sentence.

Do not edit the locked serial. Do not rewrite the layout paragraphs named above. Do not retarget the stage heads. Do not print the key. Do not set air-gap false. Do not edit the context length. Do not read the operator resource file as part of the build. Do not add a formal test class. Do not use lambdas in product types. Do not commit memory, the IDE workspace, or the model file.

**semantic-key:** PK-5723

## Goal

One stamped run-book, then the types for the locked remote serial, in eight packages.

A normal cell, burner pin off, target `grok/` plus a model id, both air-gap words explicit `true` and equal, runs the local checks, sends one `Hello` when this server process has no valid key yet, then sends the cell prompt. The greeting is discarded. The cell stores the prompt reply. Thinking paints the strip. The vault stores the reply.

The call is `POST https://api.x.ai/v1/chat/completions`. The key is the bearer token. The public example is `your-grok-api-key`.

**Truth.** Eight packages. `Hello` only when the process has no valid key. Greeting discarded. Reply stored. Strip paints thinking. Vault stores the reply. Air-gap words stay explicit `true`.

**Reason.** The goal is the operator's cell, not the probe text. The probe exists only to prove the key, then get out of the way.

**Avoidances.** Do not store the greeting. Do not send the cell prompt before the local checks. Do not turn the burner pin on for this goal. Do not set either air-gap word to `false`. Do not skip a package.

**semantic-key:** PK-5724

## Out of this build

This build does not open a later stage, does not change the higher pages, and does not send the fields the slice left off.

Out of this build:

A later stage, the later visual shell, contract agents, and the named memory products.

A constitution edit. A stage-map edit. A rewrite of the stage plan or the stage sketch. The region yield is a sentence on this plan. The stage sentence stays on that page.

Tools, `deferred`, search, `search_parameters`, and `web_search_options`. Search stays parked. Do not implement it from that note.

The Responses API. Image, video, and voice. `grok-4.7` as the factory id. `num_ctx` on a remote call.

Unifying the two JSON mappers. A new HTTP library. A second local client.

A live POST. A hand window. A push. The key printed on a page or in a commit. The public example remains `your-grok-api-key`.

A fifth vault token. Tokens stay null, `COMPLETED`, `FAILED`, and `CANCELLED`.

**Truth.** Vault tokens stay four. Factory id stays `grok-4.3`. URL stays chat completions. No live POST, no push, no printed key, no remote `num_ctx`, no second local client, no mapper merge.

**Reason.** The build is the eight packages. The out list is what a vendor page or a later stage would pull in if it were not named.

**Avoidances.** Do not add a fifth vault token. Do not adopt `grok-4.7`. Do not send `num_ctx` on the remote call. Do not add a second local client or a new HTTP library. Do not unify the mappers. Do not print the key. Do not make a live POST. Do not edit the constitution or the stage map. Do not implement parked search.

**semantic-key:** PK-5725

## Floor

The floor is the launch, the URL, the key order, the timeouts, the semaphore, and the parsers, and this build does not move them except where a package names a number.

Class `org.casserole.app.ui.ssh.SshClientApp`. Launch stays `java -cp`. Never `exec:java`.

`LlmRouter.complete(String, Prompt)` does not gain a cell-record parameter. Call options are a plain class in `org.casserole.llm`, built by `AmwpFacade` from the execute-text fields.

`POST https://api.x.ai/v1/chat/completions` stays the only remote URL.

Key lookup order stays `casserole.grok.api-key`, then `XAI_API_KEY`, then `~/.aetheris/grok.key`, then `~/.casserole/grok.key`. The value stays off the page. On the wire it is `Authorization: Bearer`. The public example is `your-grok-api-key`.

Connect timeout stays 10 seconds. The remote HTTP timeout becomes 1800 seconds in the body package. Until that package the tree stays at 120.

The semaphore of 3 stays three cell calls. `LLM_EXEC` stays 4. The chat shell does not take the semaphore.

The error slide stays the one notice. The group package adds a yellow skin on that same slide. No second window.

Tooltips land in the turn a control is built. The tip says what the control is and what the click does.

Remote JSON and event JSON objects stay on the JSON parser already in `GrokLlmService`. Internal wire text and the English interchange stay on their readers. Server-sent events are a line read beside that JSON reader.

One reader for a format. Local `message.thinking` uses one JSON read. Do not add a second parser for the same field.

The code default local model is `qwen2.5-coder:7b`. The published local base is `http://localhost:11434`. A context length of 0 omits the local field. The published example is 8192. An operator file may override the model or the context length, and that file stays off this page.

**Truth.** Launch `java -cp` of `org.casserole.app.ui.ssh.SshClientApp`. Router signature unchanged. One remote URL. Key order property, environment, then the two key files. Connect 10. HTTP 1800 in the body package, 120 until then. Semaphore 3. `LLM_EXEC` stays 4. Chat shell does not take the semaphore. One slide. One JSON parser for the remote body.

**Reason.** The floor is what the keyless invite already proved. Changing the launch, the router signature, or the key order would be a different build. The timeout is the one number this cake is allowed to move, and only in the body package.

**Avoidances.** Do not use `exec:java`. Do not add a cell-record parameter to the router complete method. Do not change the key order. Do not change connect from 10 seconds. Do not move the HTTP timeout before the body package. Do not give the chat shell the semaphore. Do not add a second window or a second parser. Do not print the key. Do not publish a private model name or a private context length.

**semantic-key:** PK-5726

## Package order

Build the eight packages in order, and do not pull a later package forward.

Package 1, doors. One air-gap predicate, `no-model`, and `burner_cloud` on the execute frame.

Package 2, probe gate. One `Hello` for the server process, then the cell prompt on that same Run.

Package 3, cell body and clocks. Sampling, caps, HTTP 1800, and the soft clock.

Package 4, document. Modal and `~/.casserole/aetheris-grok.yaml`.

Package 5, thinking. `think_stream`, event kinds, the strip, and local `think`.

Package 6, cancel. Cancel aborts that cell's HTTP call.

Package 7, region lock. The facade mutex, with the later yield that releases it before the provider call.

Package 8, group fail. A failed or cancelled member fails the merge.

A later package may use a field an earlier package added.

**Truth.** Order is doors, probe, body, document, thinking, cancel, region, group fail. No forward pull.

**Reason.** The body can leave the machine. It waits until the doors and the probe exist. The document feeds the stream bit that thinking reads. Cancel closes a call the body knows how to make.

**Avoidances.** Do not pull a later package forward. Do not ship the body before the doors. Do not ship thinking before the document names `stream`. Do not ship the group rule before members store states.

**semantic-key:** PK-5727

## Package 1. Doors

A normal remote cell with a model id passes both air-gap doors while both words stay explicit true, and an empty model stops as `no-model` before the key is read.

Done means that pass, the empty-model stop, and a burner cloud hop for `grok` that follows `burner_cloud`. Any other cloud provider still stops on `air-gap`. The constitution file is untouched.

Files: `org.aetheris.config.AetherisYaml`, `org.aetheris.ssh.AmwpFacade`, `org.aetheris.swing.AetherisNotebookPanel`, `org.aetheris.wire.AmwpToon`.

One static method `AetherisYaml.grokDoor(provider, modelPresent, burner, word)`. It returns true only when the provider is `grok`, a model id is present, the burner flag is false, and `word` is exactly `true`. Unset returns false. Explicit `false` returns false from this method. The old refuse path stays for every other case.

The shell block calls it. A cloud target that is not this door still blocks when the refuse path is true, and still blocks when the shell word and the server word differ. Session facts that are not reported block a `grok` target, because both words are not confirmed. A door that returns true does not block.

Server execute calls the same method with the process-home word. A true result skips the `air-gap` reply. A cloud target that fails the door and is still refused still replies `air-gap`.

Empty model is checked in the cell dispatch and in `GrokLlmService.completeFor`, before the key read. Target `grok` or `grok/` with nothing after the slash. Reason `no-model`. Message `no model defined`. The cell stores `FAILED`. The status line shows the banner. The key is not consulted. An empty key with a model present stays `grok key missing`. Add a typed exception for the empty model. The facade maps it to reason `no-model`. Do not reuse the missing-key exception for that case.

The execute body gains `burnerCloud`, default false. Encode writes `burner_cloud: true` only when set. Decode reads that line. Unknown lines stay ignored.

The shell copies the checkbox into that field. Until the document package builds the checkbox, the value is false. Unchecked, or false: an ephemeral `grok` cell stores `FAILED`, status `burner denied`, and the frame is not sent. Checked: the shell sends the frame. The server, for provider `grok`, reads the frame bit. Unchecked on a frame that still arrives stores the same denial. `notebook.burner-key` stays the server door for every other cloud provider. The checkbox does not write that flag and does not write the air-gap word. A local burner still runs.

The yaml files stay explicit `true`. Do not edit them.

**Truth.** `grokDoor` is true only for `grok`, model present, burner false, word exactly `true`. Empty model is a new exception, reason `no-model`, message `no model defined`, before the key read. `burner_cloud: true` is written only when set. Default false. Other cloud providers still use `notebook.burner-key` and still stop on `air-gap`.

**Reason.** One method keeps the shell and the server from drifting. A missing model must not wear `grok key missing`. The checkbox is not the air-gap word, so the files stay explicit `true`.

**Avoidances.** Do not reuse the missing-key exception for an empty model. Do not consult the key on an empty model. Do not edit the air-gap yaml files. Do not let the checkbox write `notebook.burner-key`. Do not let unreported session facts pass a `grok` target. Do not treat explicit `false` as this door. Do not encode `burner_cloud` when it is false.

**semantic-key:** PK-5728

## Package 2. Probe gate

The first remote run in a server process sends one `Hello`, and a readable success discards the greeting and sends the cell prompt on that same run.

Done means that first run, a miss that stores `FAILED` and does not send the prompt, and a second run that waits during the probe and does not send its own `Hello`.

Files: `AmwpFacade`, `GrokLlmService`. The gate is a private class inside the facade. No new top-level type.

The gate lives in the server process, beside one valid-true flag. The flag matches the key the key read returned. It is not written to a file. The shell does not keep a copy.

States are none, in flight, and valid. The check and the claim share one lock. That lock is not held during the POST.

A run that finds valid sends that cell's prompt with the bearer token.

A run that finds none becomes the leader. The probe body is `model` equal to the target id, one user message whose content is `Hello`, `stream` false, and `max_completion_tokens` `16`. `temperature` and `top_p` stay off that body. The cell prompt stays off that body.

A run that finds in flight waits. It does not send `Hello`.

A 2xx with readable content sets valid and discards the greeting. The leader and each waiter then send their own cell prompts. A miss leaves the gate at none. Each of those cells stores `FAILED` and does not send its prompt. The next remote run may probe again.

A `401` on a later call clears valid. A server restart clears the flag. A shell restart does not.

Cancelling the leader does not abort `Hello`. That cell stores `CANCELLED` and does not send its prompt. The cancel package wires the HTTP abort for a cell's own prompt call. The probe future stays on the gate.

`cell-busy` is not this gate. The three execute slots stay three cell calls. The probe runs inside the slot the leader already holds.

A wire miss from the probe opens the existing error slide. A local miss uses the banner already named for it.

Lock order with the region package, once that package lands: the region mutex is acquired first. The probe lock is acquired only to check and claim, then released before the POST. Never acquire the region mutex while holding the probe lock. The region lock is not held during `Hello`.

The URL stays `POST https://api.x.ai/v1/chat/completions`.

**Truth.** Private gate inside the facade. Probe body model, `Hello`, `stream` false, cap `16`, no sampling fields. Greeting discarded. `401` clears valid. Leader cancel does not abort `Hello`. Probe lock released before the POST.

**Reason.** A top-level type is not required for one process flag. Keeping the probe inside the leader's existing slot stops the gate from becoming a fourth execute slot. Releasing the lock before the POST avoids a deadlock with the region mutex.

**Avoidances.** Do not add a top-level type. Do not write the flag to a file. Do not hold the probe lock during the POST. Do not abort `Hello` when the leader is cancelled. Do not send temperature or top-p on the probe. Do not store the greeting. Do not treat `cell-busy` as this gate. Do not acquire the region mutex while holding the probe lock.

**semantic-key:** PK-5729

## Package 3. Cell body and clocks

A cell prompt sends temperature 1, omits top-p, sends a visible-output cap of 128000, and the HTTP timeout is 1800 seconds.

Connect stays 10. The JSON object reader stays the `stream` false path. The soft clock uses 1800 seconds.

Files: `GrokLlmService`, `AmwpFacade`, `AetherisNotebookPanel`.

The cell body sends `model`, `messages`, and `stream` false when thinking is off. Roles stay `system`, `user`, and `assistant`. Any other role is `unknown role`.

The cell sends `temperature` `1` and omits `top_p`. `max_completion_tokens` is `128000` on the cell only. A 400 that names the cap uses the existing redacted `Grok HTTP` slide text. No second parser.

`reasoning_effort` rides only when the stored value is not `omit`. `grok-4.3` stays omit only. Until the document package, the field stays off the wire.

Tools, `deferred`, and search stay off the wire. The remote call does not send `num_ctx`.

Assistant text stays `choices[0].message.content` when that value is text. Empty content fails as `grok empty content`. A non-text value fails as `grok content shape`. A missing choice fails as `grok response has no content`. The non-stream reader does not read `reasoning_content` and does not join content parts.

`completeFor` uses connect 10 and timeout 1800. A death at that cap is a hard transport miss. The public line stays `Grok HTTP` plus status plus at most 180 characters, key replaced by `[redacted]`, newlines as spaces. The `garnered` line still caps at 240.

The soft clock starts when one group member has stored a response and another member still has no answer. The wait is 1800 seconds from that store. On fire, the waiting member stores `FAILED`, the reply field stays clear, and the yellow words are `the cell is still waiting and no answer arrived`. A miss that already failed stands. `cell-busy` is not this wait. The HTTP cap and this clock keep two jobs. The group package paints that miss yellow and fails the merge.

**Truth.** Temperature `1`. No `top_p`. Cell cap `128000`. Connect 10. Timeout 1800. Soft-clock words `the cell is still waiting and no answer arrived`. Errors `grok empty content`, `grok content shape`, `grok response has no content`, and `unknown role`. No `num_ctx`.

**Reason.** The probe cap and the cell cap are different jobs. A 400 that names the cell cap reuses the redacted line so a second parser is not born. The soft clock waits on a sibling, not on the HTTP socket.

**Avoidances.** Do not send `top_p`. Do not put `128000` on the probe. Do not send `reasoning_effort` before the document package stores a non-omit value. Do not send tools, deferred, search, or `num_ctx`. Do not read `reasoning_content` on the non-stream path. Do not start the soft clock before a sibling has stored a response. Do not treat `cell-busy` as the soft wait. Do not leave the key in the 180 characters.

**semantic-key:** PK-5730

## Package 4. Document

The first remote selection with no standing default opens one modal, and confirm creates `~/.casserole/aetheris-grok.yaml`.

Done means that file holds the standing default and that cell. A button on a remote cell edits that cell only. A later remote selection copies the nearest earlier cell on that canvas.

Files: `AetherisNotebookPanel`. The yaml library is already on the client. The hand reader for the air-gap yaml stays the hand reader. The file is created on the first confirm, not during the plan filing.

Path `~/.casserole/aetheris-grok.yaml`. It is not the connection yaml, not the air-gap yaml, not the server resource file, and not a vault column.

`version` is `1`. A file whose version is not `1` is left unread and is not overwritten. Unknown keys are dropped on the next write. A key found in the file is ignored and is not written back. The public key shape `your-grok-api-key` is not a field of this file.

The standing default and each cell entry hold `api`, `model`, `reasoning_effort`, `stream`, `burner_cloud`, `temperature`, and `max_completion_tokens`. `top_p` stays off the document.

Factory values: `api` `chat`, `model` `grok-4.3`, `reasoning_effort` `omit`, `stream` false, `burner_cloud` false, `temperature` `1`, `max_completion_tokens` `128000`. The sample id `grok-4.6` on the locked serial stays a shape. This build does not adopt `grok-4.7`.

The dialog is a `JDialog` owned by the notebook frame. Title `Grok settings`. Application modal. Four combo boxes, not editable: api `chat` only, the text-model table already on the locked serial, `reasoning_effort` from that table plus `omit` (`grok-4.3` is omit only), and `stream` `false` or `true`. The burner checkbox and the two number fields sit on that same dialog. OK stays disabled until a model is selected. A stored id that is not in the table is an extra row at the top. Cancel writes nothing. When this open was the transition into `grok` and the cell had no document, Cancel puts the target back.

The button sits beside the execution target and shows when the provider is `grok`. Tooltips on that button, OK, Cancel, the checkbox, and the two number fields, in this turn. Listeners are anonymous classes.

The model id is the text after `grok/` on the execution target. The modal writes that suffix. The shell's `burner_cloud` copy in the doors package reads this document.

`stream` on the document is the thinking control for a remote cell. The thinking package carries it as `think_stream`.

**Truth.** File `~/.casserole/aetheris-grok.yaml`, version `1`, created on first confirm. Title `Grok settings`. Factory `grok-4.3`, omit, stream false, burner false, temperature `1`, cap `128000`. No key field. No `top_p`. Unknown stored model stays as an extra top row. First-transition Cancel restores the target.

**Reason.** Confirm is the moment the operator has chosen, so the file is not created while the plan is only filed. A foreign version is left unread so a newer document is not flattened. The connection file is the wrong home because it flattens maps and holds a password.

**Avoidances.** Do not create the file during plan filing. Do not overwrite a version other than `1`. Do not write a key into the file. Do not adopt `grok-4.7`. Do not treat `grok-4.6` as the factory. Do not put `top_p` on the document. Do not enable OK with no model. Do not drop an unknown stored id. Do not use lambdas for the listeners. Do not put call settings in the air-gap hand parser.

**semantic-key:** PK-5731

## Package 5. Thinking

One per-cell control, factory off, sends `stream` true on the remote door and `think` true on the local one-shot, and both paint the same strip.

The cell stores the joined content. Local thinking paints `message.thinking`.

Files: `AmwpToon`, `AmwpConstants`, `AmwpFacade`, `GrokLlmService`, `SpringAiOllamaLlmService`, `LlmRouter`, `AetherisNotebookPanel`.

The execute body gains `thinkStream`, default false. Encode writes `think_stream: true` only when set. Decode reads it. No vault column.

The remote control is the modal `stream` value. A local cell uses one checkbox beside the execution target, factory off, memory for the session, not a vault column and not the remote yaml. A tooltip in this turn. There is no second checkbox on a remote card.

Control off: the remote call sends `stream` false. The local call leaves `think` unset.

Remote on reads the body as event lines. A line is `data:` plus a JSON object, then `data: [DONE]`. `delta.reasoning_content` goes out as kind `t`. `delta.content` goes out as kind `c`. Both ride the stream channel. The JSON parser reads each JSON object with the mapper already in `GrokLlmService`.

The kind frame is delta type `0x06` on channel `0x0003`. Payload after the 24-byte scope prefix is one ASCII byte `t` or `c`, then the UTF-8 text. `0x0100` stays unallocated. The data frame is the finished reply, once, at the end, and the client stores that text. A delta does not store the cell and does not end the run. A thinking chunk is not a data frame.

The strip is a read-only text area, three rows, above the BB1 bar and under the main canvas. A `v` minimizes it and restores it. A tooltip on `v` in this turn. A call that produces no thinking hides the strip. It comes back when thinking occurs again. While a run is producing thinking, the strip shows that run. A group shows the member that is running. The last member that produced thinking stays up until a later call hides the strip or replaces the text. Thinking text drops with the session. It is not a vault column, not an inspector line, and not a later turn's messages. The spinner stays the running clock.

Local on calls the existing chat client with `think` true on the local chat options and does not stream. The JSON parser reads `message.thinking` from that response JSON. `message.content` stays the reply. The same strip paints the thinking text. If the response does not expose the raw JSON or the options object has no think setter, stop and ask. Do not add a second HTTP client. Do not probe `/api/show`. No level string.

`LlmProvider.complete(Prompt)` stays. The router already special-cases the remote complete path. It special-cases the local think flag the same way.

The code default model is `qwen2.5-coder:7b`. The published local base is `http://localhost:11434`. Context length 0 omits the field. The published example is 8192. An operator override stays off this page.

**Truth.** `think_stream: true` only when set. Delta `0x06`, channel `0x0003`, kinds `t` and `c`, 24-byte scope prefix. End marker `data: [DONE]`. Three-row strip. Local `think` true, no stream, no level, no second client. Factory off.

**Reason.** The stock local stream helper drops thinking text, and this package still refuses a second client. Stopping to ask when the options object cannot carry `think` is safer than a private HTTP path. The kind byte exists because a bare data frame would end the run on the first token.

**Avoidances.** Do not add a vault column. Do not put a second checkbox on a remote card. Do not allocate `0x0100`. Do not store a delta as the cell. Do not add a second HTTP client. Do not probe `/api/show`. Do not send a level string. Do not stream the local call. Do not hide the spinner. Do not send the thinking text as a later message.

**semantic-key:** PK-5732

## Package 6. Cancel

Cancel drops the run token and aborts that cell's prompt HTTP call, and the shared `Hello` still finishes.

A late reply is still dropped when the token is gone. The cell stores `CANCELLED`.

Files: `AmwpFacade`, `GrokLlmService`.

The cell prompt call is an asynchronous send. The future is held beside that cell's live token. Cancel cancels that future and removes the token, and still cancels the local handle.

The still-mine check still drops a reply that arrives after the token is gone. The cell stores `CANCELLED`. The in-flight reply is not stored.

The probe future stays on the gate from the probe package. Cancelling the leader does not cancel it.

**Truth.** Asynchronous cell send. Cancel aborts that future and the local handle. `Hello` stays. Late reply dropped. Token `CANCELLED`. Partial reply not stored.

**Reason.** The token drop alone left the HTTP call running. The probe is shared, so the leader's cancel must not spend the waiters' proof.

**Avoidances.** Do not cancel the probe future from a cell cancel. Do not store the in-flight reply. Do not accept a late reply after the token is gone. Do not leave the cell HTTP call running.

**semantic-key:** PK-5733

## Package 7. Roux region lock

Clear and add stay inside the region lock, and the provider call runs after that lock is released.

The wire body stays the prompt already built. A pool child and a local complete on the same user may overlap. The pool process is detailed on the server page.

Files: `AmwpFacade`.

The earlier reading grew the synchronized block that already calls clear and add so that it included the router complete and the reply send that follows a still-mine check. That earlier reading stays on the page. The later yield is the operative door: clear and add stay inside the region lock, and the provider call runs after the lock is released.

Complete still uses the local prompt. It does not read the region back.

This is the facade mutex. It is not the memory engine's lock and not the try-lock. Lock order stays facade lock first, then the engine's per-call lock inside clear and add. Never the reverse.

The stage sketch sentence that releases the mutex before complete stays on that page. This package is the yield. Do not edit that file.

The semaphore of 3 stays. A second call for that user waits on the mutex while the lock is held. The probe lock rule stays: region lock first, probe lock only for the claim, probe lock released before the POST.

The later yield: one pool prompt per user stays on the child gate. The probe lock is not taken while the region lock is held, and the region lock is not held during `Hello`.

**Truth.** Operative yield: region lock around clear and add only. Provider call after release. Prompt already built. Semaphore 3. Facade lock before the engine lock. No region lock during `Hello`. No probe lock while the region lock is held. Pool overlap is allowed. One pool prompt per user on the child gate.

**Reason.** Holding the facade mutex through the provider call blocked every other call for that user, including a pool child, for the whole remote wait. The prompt was already built, so the call does not need the region. The earlier hold stays recorded so the yield is visible as a change, not as a silent delete.

**Avoidances.** Do not edit the stage sketch. Do not reverse the lock order. Do not read the region back during complete. Do not hold the region lock during `Hello`. Do not take the probe lock while the region lock is held. Do not change the semaphore. Do not detail the pool scheduler here. Do not drop the earlier hold from the record.

**semantic-key:** PK-5734

## Package 8. Group fail

Any member that failed or was cancelled fails the merge, concatenation does not run, and a cancel-only group does not open the slide.

A member that already stored a valid reply stays. The merge stores `FAILED` and its reply stays blank. A hard miss is red. A soft miss is yellow.

Files: `AetherisNotebookPanel`. The layout page already received its sentence in the filing step.

The merge stops waiting when any member is `FAILED` or `CANCELLED`. It does not call the concatenation join. The merge assistant text stays blank. The merge status is `FAILED`. Members already `COMPLETED` stay.

A hard miss is `engine-down`, a remote transport miss, or `too-large`. A soft miss is an empty reply, `grok key missing`, `no-model`, a `Grok HTTP` line, `air-gap`, `burner denied`, `unknown provider`, `unknown role`, `pipe`, `no tool is invocable`, or the owed-answer wait. When any miss in the group is hard, the notice is red. When every miss is soft, the notice is yellow.

The red skin stays the existing slide colors. The yellow skin uses the same geometry: border and title `0xF9A825`, fill `0x3A3014`, ink `0xF8F4E8`. One slide. The named banners still paint the status line.

`cell-busy` does not fail the group and does not open the slide.

A group whose only miss is `CANCELLED` stores `FAILED` on the merge and does not open the slide.

Today the tree waits on a failed member. This package is that change.

**Truth.** Merge status `FAILED`, assistant text blank, concatenation not called. Hard reasons: `engine-down`, remote transport miss, `too-large`. Soft reasons include `grok key missing`, `no-model`, `Grok HTTP`, `air-gap`, `burner denied`, `unknown provider`, `unknown role`, `pipe`, `no tool is invocable`, an empty reply, and the owed-answer wait. Yellow is `0xF9A825`, `0x3A3014`, `0xF8F4E8`. Cancel-only opens no slide.

**Reason.** The tree waits because the merge demands every member completed. One failure must end that wait without erasing replies that already succeeded, and a pure cancel is not a slide.

**Avoidances.** Do not call the concatenation join after a failed or cancelled member. Do not clear a completed member. Do not open the slide for `cell-busy` or for a cancel-only group. Do not add a second window. Do not paint yellow when any miss is hard. Do not paint red when every miss is soft.

**semantic-key:** PK-5735

## Gate

The gate is a quiet compile with tests skipped, a closed window, no live POST, and air-gap left explicit true.

Compile quiet and skip tests from the server tree. The window was not opened. The live POST was not made. Air-gap stays explicit `true`. The key was not printed. The public example remains `your-grok-api-key`.

The stage marker stays. The action stays. The working map stays.

The locked serial is the source. This page is the cake. Where this page is thinner, the locked serial wins.

Locked serial wins where the plan is thin.

**Reason.** The gate proves the types compile without spending the key or opening the cloud door. A push is a later named act.

**Avoidances.** Do not open the window. Do not make the live POST. Do not set air-gap false. Do not print the key. Do not move the stage marker. Do not treat a thinner plan sentence as the winner over the locked serial.

**semantic-key:** PK-5736

## Aetheris Grok API pre-plan

This page is the sketch a later plan is cut from, and where it is thinner the locked serial wins.

The packages below are the batch. The page is not a license to write types by itself. It is not a copy of the process rules in a plans folder. It is not a rewrite of the locked serial, the stage map, or the constitution. It does not open a later stage.

The locked serial binds this page. The stage map and the constitution still win if a sentence drifts. Repair this page.

One chat POST. The key is the bearer token. `Hello` proves the key. The cell prompt is the later body. Thinking paints the strip. The vault stores the reply.

This page is the smoke. Throwing the sketch away is a success. A gap marked here opens the next sketch serial. Do not clobber this file. Do not reopen the locked serial for that gap. A requirement this page does not already inherit from the locked serial is written on a spec serial first.

No boilerplate generator. No lambdas in product types. Anonymous classes only in product UI. A source tag on a new type when a later build creates it. A new control gets a tooltip in the turn it is built. The tip says what the control is and what the click does. External JSON stays on the JSON parser. One reader for a format. Server-sent events are a line read beside the JSON object reader. Internal wire text and the English interchange stay on their own readers.

The formal plan is the later stamped page. The stamp names the build. Packages for the region lock and the group fail are in this cake.

**Truth.** Sketch, not the build. Locked serial wins where this page is thinner. One POST, bearer key, `Hello`, then the cell prompt. Next sketch is a new file. Region lock and group fail are inside the cake.

**Reason.** The sketch exists so the plan can be thrown away if the batch is wrong, without reopening the locked serial. The two neighbors moved inside so the plan does not leave the region lock and the group fail outside the build.

**Avoidances.** Do not clobber this file. Do not reopen the locked serial for a gap. Do not write a requirement here that the locked serial does not already hold. Do not use lambdas in product types. Do not add a second parser for one format. Do not treat this page as a license to write types before the plan names the build.

**semantic-key:** PK-5737

## What this slice is

The slice is the rest of the remote chat call after the keyless invite and the one-shot JSON reader.

Done means a normal cell, burner pin off, target `grok/` plus a model id, both air-gap words explicit `true` and equal. Run passes the local checks, sends one `Hello` when this server process has no valid key yet, then sends the cell prompt with the bearer token. The greeting is discarded. The cell stores the prompt's reply. With the thinking control on, remote thinking paints the strip and the reply is still the stored text. The same control on a local cell sets `think` true and paints `message.thinking` on that strip. An empty model stops as `no-model` with `no model defined`. A burner cloud hop follows the checkbox.

Not done: a stamped plan by this page alone, the types, a plans-folder copy, a later stage, a constitution edit, a stage-map edit, tools, `deferred`, search, the Responses API, `grok-4.7` as the factory id, `num_ctx` on a remote call, or the key written on a page. The public example value is `your-grok-api-key`.

The call stays `POST https://api.x.ai/v1/chat/completions`.

**Truth.** Done is one normal non-burner remote cell, one `Hello` when the process has no valid key, then the cell prompt, greeting discarded, strip for thinking, `no-model` before the key, burner hop from the checkbox.

**Reason.** The keyless invite already shipped the shape. This slice is the probe, the body, the document, the strip, the cancel, the region yield, and the group fail.

**Avoidances.** Do not call the slice done when tools, search, deferred, Responses, or `grok-4.7` are still out. Do not put `num_ctx` on the remote call. Do not write the key on a page. Do not edit the constitution or the stage map.

**semantic-key:** PK-5738

## In / Out

The slice is eight packages in and a closed list out, and the out list is not built.

In is doors, the probe gate, the cell body and clocks, the document, thinking, cancel, the region lock, and the group fail.

Out is tools, deferred, search, the Responses API, image, video, voice, `grok-4.7` as the factory id, `num_ctx` on the remote call, a constitution edit, a stage-map edit, unifying two JSON mappers, and a later stage.

The remote URL stays `POST https://api.x.ai/v1/chat/completions`.

**Truth.** Eight packages are in. Search, tools, deferred, Responses, image, video, voice, a newer factory id, and remote `num_ctx` are out.

**Reason.** The batch has to be named before the plan so a later package is not pulled forward and an out item is not implemented from a parked note.

**Avoidances.** Do not implement an out item inside an in package. Do not unify the two JSON mappers in this slice. Do not edit the constitution or the stage map. Do not send `num_ctx` on the remote call.

**semantic-key:** PK-5739

## In

The in list is the eight packages, in the order doors, probe, body, document, thinking, cancel, region lock, and group fail.

Doors, before any POST. One air-gap predicate on both doors. `no-model` before the key read. `burner_cloud` on the execute frame for provider `grok`.

Probe gate. One `Hello` for the server process. The same Run then sends the cell prompt.

Cell body and clocks. `temperature` `1`. `top_p` omitted. Cell cap `128000`. Probe cap `16`. HTTP timeout `1800`. Connect `10`.

Document. Modal and `~/.casserole/aetheris-grok.yaml`. Factory model `grok-4.3`.

Thinking. `think_stream` on the execute text for both providers. Event kinds `t` and `c`. The strip. Local `think` through the JSON parser.

Cancel. Cancel aborts that cell's HTTP call. The shared `Hello` still finishes.

Region lock. The facade hold is the package. The later plan yield releases the provider call. This sketch says the lock is held through complete. The plan's later sentence is the yield. Both stay. The pool process is on the server page.

Group fail. Any member `FAILED` or `CANCELLED` fails the merge.

**Truth.** Eight in rows. Temperature `1`. No `top_p`. Caps `128000` and `16`. Timeouts `1800` and `10`. Factory `grok-4.3`. Kinds `t` and `c`. `Hello` is not aborted by the leader's cancel.

**Reason.** The doors and the probe come before a body that can leave the machine. The document feeds the body. Thinking rides it. Cancel closes the call. The region package wraps it. The merge reads the states those calls store.

**Avoidances.** Do not reorder the packages so a body leaves the machine before the doors. Do not send `top_p`. Do not abort `Hello` from a cell cancel. Do not drop `no-model` to after the key read.

**semantic-key:** PK-5740

## Out

The out list stays off this release, and the call stays the chat-completions POST.

Tools, `deferred`, and search are off this release. Search stays parked. Do not implement it from that note.

The Responses API, image, video, and voice are out. The call stays `POST https://api.x.ai/v1/chat/completions`.

`grok-4.7` as the factory id is out. The vendor docs recommend it. The factory id stays `grok-4.3`.

`num_ctx` on the remote call is out. Only the local engine sets `num_ctx`. The value 0 omits that local field. The published example is 8192. The code default model is `qwen2.5-coder:7b`. The published local base is `http://localhost:11434`. An operator file may override the model or the context length, and that file stays off this page.

The constitution and the stage map are out. This slice does not edit them.

Unifying the two JSON mappers is out. One reader for a format is still the consideration. This slice does not unify them.

A later stage, the later visual shell, and the named memory products are closed. When a later stage opens, a remote target follows the locked serial.

**Truth.** Out: tools, deferred, search, Responses, image, video, voice, factory `grok-4.7`, remote `num_ctx`, constitution edits, stage-map edits, a second mapper merge, and the later stage.

**Reason.** The vendor recommendation and the schema's extra routes are real, and they are still not this cell. Naming them out keeps the factory id and the URL stable.

**Avoidances.** Do not adopt `grok-4.7` as the factory id. Do not send `num_ctx` on the remote call. Do not implement search from the parked note. Do not switch the URL. Do not edit the constitution or the stage map. Do not unify the mappers in this slice.

**semantic-key:** PK-5741

## As-built

The as-built table is what the tree does today and what this slice adds, and the plan must not forget it.

The remote complete path writes `model`, `messages`, and `stream` false with the JSON parser. Connect is `10`. The tree timeout is `120`. It reads `choices[0].message.content` when it is text. This slice adds the probe body, the cell fields, the `1800` cap, and the event line read. The JSON object reader stays for `stream` false.

An empty model shares the missing-key exception with an empty key. The facade says `grok key missing`. This slice uses `no-model` and `no model defined` before the key read. An empty key with a model present stays `grok key missing`.

Key lookup is `casserole.grok.api-key`, then `XAI_API_KEY`, then `~/.aetheris/grok.key`, then `~/.casserole/grok.key`. The same order stays. The value stays off this page. On the wire it is the bearer token. The public example is `your-grok-api-key`.

Air-gap: the shell blocks before the frame. The server refuses a frame that arrives. Both words are explicit `true`. Unset refuses. A mismatch blocks the shell. This slice uses one predicate, both doors. A normal remote cell may pass. The constitution file stays.

The shell refuses every burner cloud hop. The server reads `notebook.burner-key` only if a frame arrives. This slice puts `burner_cloud` beside `ephemeral` for provider `grok`. `notebook.burner-key` stays the door for every other cloud provider.

A semaphore of `3` in the facade stays `3`. The probe gate is a different lock.

The merge writes when every member is `COMPLETED`. A failure mark sets `FAILED` on that one cell. This slice fails the merge when any member is `FAILED` or `CANCELLED`.

The execute path clears the region, then calls the model outside that lock. This sketch holds the lock through complete. The plan's later yield releases the provider call after clear and add. The pool overlap is noted here and detailed on the server page.

Cancel drops the run token. The HTTP send is not aborted. This slice aborts that cell's HTTP call.

The error slide is built. One red skin. The group rule adds yellow on that same slide. A remote wire miss uses the slide that is already there.

The local service sets the context length when it is greater than `0`. It does not set `think` until this slice. This slice sets `think` true when `think_stream` is on. The JSON parser reads `message.thinking`.

The settings file does not exist yet. The modal is not built. This slice creates the file on confirm and builds the modal.

The spinner is the running clock. The strip is not built. This slice builds the strip. The spinner stays the clock.

**Truth.** Tree today: `stream` false, connect 10, timeout 120, shared key banner, air-gap stops the remote target, semaphore 3, merge waits, HTTP send not aborted, context length only when positive, no settings file, no strip. This slice adds the rest without a second slide or a second semaphore.

**Reason.** The plan has to see the tree, or it will rebuild the JSON reader and miss the hang in the merge.

**Avoidances.** Do not replace the one-shot JSON reader. Do not change the key order. Do not change the semaphore of 3. Do not build a second error window. Do not set `think` when the control is off. Do not create the settings file before confirm. Do not turn the spinner into the strip.

**semantic-key:** PK-5742

## Work packages

The package order is doors, probe, cell body and clocks, document, thinking, cancel, region lock, then group fail.

A later package may use a field an earlier package added. Do not pull a later package forward.

The doors and the probe come before a cell body that can leave the machine. The document feeds the body. Thinking rides that body. Cancel closes the call. The region lock wraps that call. The merge rule reads the states those calls store.

**Truth.** Order is 1 through 8: doors, probe, body, document, thinking, cancel, region, group fail.

**Reason.** A body that can leave the machine before the doors exist will POST a cell the air-gap predicate would have stopped. The document has to exist before thinking reads its stream bit.

**Avoidances.** Do not pull thinking or cancel forward of the body. Do not send a cell body before the doors and the probe exist. Do not run the merge rule before the calls store their states.

**semantic-key:** PK-5743

## Doors

One air-gap predicate serves both doors, an empty model stops before the key read, and a burner hop for this provider follows the frame bit.

`blockedCloud` and `refusesCloud` both call the predicate. It returns true only when the provider is `grok`, a model id is present, the burner flag is false, and the word is exactly `true`.

A normal cell, burner pin off, provider `grok`, model present, both air-gap words explicit `true` and equal, may pass.

Unset still refuses. A mismatch still blocks the shell. A burner still stops. Any other cloud provider still stops on `air-gap`. Explicit `false` is not this door.

The constitution file stays as written. The yaml files stay explicit `true`.

Empty model is checked in the shell and in the complete path, before the key read. Reason `no-model`. Message `no model defined`. The vault token stays `FAILED`.

An empty key with a model present stays `grok key missing`.

The execute frame carries `burner_cloud` beside `ephemeral`. The shell copies the checkbox. For provider `grok` the server reads that bit.

Unchecked: the shell stores `FAILED`, status `burner denied`, and does not send the frame. Unchecked on a frame that still arrives stores the same denial.

Checked: the shell sends. A local burner still runs.

`notebook.burner-key` stays the server door for every other cloud provider. The checkbox does not write that flag and does not write the air-gap word.

**Truth.** One predicate. Pass only for `grok`, model present, burner off, word exactly `true`, both words equal. `no-model` before the key read. `burner_cloud` beside `ephemeral` for this provider. Local burner still runs.

**Reason.** Two doors that do not share the predicate leave one side stopping a call the other side allows. The checkbox must not become the air-gap word or the other providers' server flag.

**Avoidances.** Do not let unset, a mismatch, a burner, or another cloud provider pass. Do not read the key before the empty-model check. Do not reuse the missing-key exception for an empty model. Do not let the checkbox write `notebook.burner-key` or the air-gap word. Do not edit the constitution file. Do not set the yaml words to `false`.

**semantic-key:** PK-5744

## Probe gate

One `Hello` proves the key for the server process, and the same Run then sends the cell prompt.

The gate lives in the server process that reads the key, beside one valid-true flag. The flag matches that one key. It is not written to a file. The shell does not keep a copy.

States are none, in flight, and valid. The check and the claim share one lock. The lock is not held during the POST.

A Run that finds valid sends that cell's prompt with `Authorization: Bearer` and the key.

A Run that finds none becomes the leader and sends one `Hello`. The body is `model` equal to the target id, one user message whose content is `Hello`, `stream` false, and `max_completion_tokens` `16`. `temperature` and `top_p` stay off that body. The cell prompt stays off that body.

A Run that finds in flight waits. It does not send its own `Hello`.

A 2xx with readable content sets valid and discards the greeting. The leader and each waiter then send their own cell prompts.

A miss leaves the gate at none. Each of those cells stores `FAILED` and does not send its prompt. The next `grok/` Run may probe again.

A `401` on a later call clears valid. A server restart clears the flag. A shell restart does not.

The probe belongs to the process. Cancelling the leader cell does not abort `Hello`. That cell stores `CANCELLED` and does not send its prompt.

`cell-busy` is not this gate. The three execute slots stay three cell calls.

A wire miss opens the existing error slide. A local miss uses the banner already named for it. No second window.

The call stays `POST https://api.x.ai/v1/chat/completions`.

Lock order with the region package, once that package lands: the region mutex is acquired first. The probe lock is acquired only to check and claim, then released before the POST. Never acquire the region mutex while holding the probe lock. The region lock is not held during `Hello`.

**Truth.** States none, in flight, valid. Probe body is model, `Hello`, `stream` false, cap `16`, no temperature, no top-p. Greeting discarded. `401` or server restart clears valid. Leader cancel does not abort `Hello`. Semaphore stays 3.

**Reason.** Two runs that both see no valid key would each send `Hello` and might store the greeting. One flight, one flag, and the same Run continuing keeps the operator's prompt as the stored answer.

**Avoidances.** Do not put the cell prompt on the probe. Do not send temperature or top-p on the probe. Do not hold the lock during the POST. Do not let a waiter send `Hello`. Do not abort `Hello` when the leader is cancelled. Do not keep a shell copy of the flag. Do not treat `cell-busy` as this gate. Do not open a second window. Do not acquire the region mutex while holding the probe lock.

**semantic-key:** PK-5745

## Cell body and clocks

The cell sends temperature 1, omits top-p, caps visible output at 128000, and the HTTP timeout becomes 1800 seconds while connect stays 10.

The JSON object reader already in the remote service stays the `stream` false path.

The cell body sends `model`, `messages`, and `stream` false when the thinking control is off.

Roles on `messages` are `system`, `user`, and `assistant`. Any other role is `unknown role`.

The cell sends `temperature` `1` and omits `top_p`. `max_completion_tokens` is `128000` on the cell. A 400 that names the cap is the slide text.

`reasoning_effort` rides only when the pulldown is not `omit`. `grok-4.3` stays omit only.

Tools, `deferred`, and search stay off the wire.

Assistant text is `choices[0].message.content` when that value is text. Empty content fails as `grok empty content`. A non-text value fails as `grok content shape`. A missing choice fails as `grok response has no content`.

The non-stream reader does not read `reasoning_content` and does not join content parts.

When this package is built, the remote HTTP timeout is `1800` seconds and connect stays `10`. Until that build the tree stays at `120`.

A death at the HTTP cap is a hard transport miss. The public line stays the redacted `Grok HTTP` line, at most `180` characters, with the key replaced by `[redacted]`. Newlines are spaces. The `garnered` line still caps at 240 characters.

The soft clock starts when one member has stored a response and another member still has no answer. The wait is 1800 seconds from that store. On fire, the waiting member stores `FAILED`, the reply field stays clear, and the yellow words are `the cell is still waiting and no answer arrived`. The two clocks keep two jobs.

The remote call does not send `num_ctx`.

**Truth.** Cell: temperature `1`, no `top_p`, cap `128000`, `stream` false when thinking is off. Probe cap is not this package's cell cap. HTTP `1800`, connect `10`, tree `120` until the build. Soft clock words are `the cell is still waiting and no answer arrived`. No `num_ctx`.

**Reason.** The vendor schema says to alter temperature or top-p, not both. Omitting top-p avoids a model that rejects the pair. The HTTP death and the sibling wait are different failures, so they do not share one clock.

**Avoidances.** Do not send `top_p`. Do not send tools, deferred, or search. Do not read `reasoning_content` on the non-stream path. Do not join content parts. Do not send `num_ctx`. Do not adopt 120 as the named cap. Do not start the soft clock before one member has stored a response. Do not leave the key in the HTTP line.

**semantic-key:** PK-5746

## Document

The settings file is `~/.casserole/aetheris-grok.yaml`, version 1, and the factory model is `grok-4.3`.

It is not the connection yaml, not the air-gap yaml, not the server resource file, and not a vault column.

`version` is `1`. A file whose version is not `1` is left unread and is not overwritten. Unknown keys are dropped on the next write. A key found in the file is ignored and is not written back. The public key shape `your-grok-api-key` belongs on the server example, not in this file.

The standing default and each cell entry hold `api`, `model`, `reasoning_effort`, `stream`, `burner_cloud`, `temperature`, and `max_completion_tokens`. `top_p` stays off the document.

Factory values: `api` `chat`, `model` `grok-4.3`, `reasoning_effort` `omit` for `grok-4.3`, `stream` false, `burner_cloud` false, `temperature` `1`, `max_completion_tokens` `128000`.

The sample id `grok-4.6` in the locked serial stays a shape. It is not the factory selection. This page does not adopt `grok-4.7`.

The modal is the editor. Four pulldowns stay the menu: api chat, model, reasoning effort, stream. The burner checkbox and the two number fields sit on that same modal.

A new remote turn copies the nearest earlier cell's document. The operator may edit or leave it. An edit sticks to that cell.

The yaml library is already on the client. The hand reader for the air-gap yaml stays the hand reader.

Tooltips land on the new controls in the turn they are built.

The file is created on the first confirm. This sketch does not create it.

**Truth.** Path `~/.casserole/aetheris-grok.yaml`. Version `1`. Factory `grok-4.3`, effort `omit`, stream false, burner false, temperature `1`, cap `128000`. No `top_p`. No key field. Sample `grok-4.6` is a shape only.

**Reason.** The connection file flattens maps and holds a password. The air-gap parser ignores unknown keys and must stay a hand reader. The server resource file holds the key. The settings need their own document.

**Avoidances.** Do not create the file before confirm. Do not overwrite a foreign version. Do not write a key back. Do not adopt `grok-4.7` or treat `grok-4.6` as the factory. Do not put `top_p` on the document. Do not run the yaml library through the air-gap hand parser. Do not change the standing default when one cell is edited.

**semantic-key:** PK-5747

## Thinking

One per-cell control, factory off, is carried as `think_stream` for both providers, and the strip paints kind `t` while the stored reply stays the joined content.

No new vault column.

Remote on sends `stream` true. The body is read as event lines. `reasoning_content` goes out as kind `t`. `content` goes out as kind `c`. Both ride the stream channel. The data frame is the finished reply, once, at the end. The cell stores the joined `c` text. A thinking chunk is not a data frame.

The spinner stays the running clock. The strip paints `t`.

The strip sits above the BB1 bar and under the main canvas. Three lines are viewable. A `v` minimizes it and restores it. A call that produces no thinking hides the strip. It comes back when thinking occurs again.

The widget stands as a read-only text area. While a run is producing thinking, the strip shows that run. A group shows the member that is running. The last member that produced thinking stays up until a later call hides the strip or replaces the text.

Thinking text drops with the session. It is not a vault column, not an inspector line, and not a later turn's messages.

Local on sets `think` true on the one-shot complete and does not stream. The JSON parser reads `message.thinking` from that JSON. `message.content` stays the reply. The same strip paints the thinking text.

The control off leaves the remote call at `stream` false and leaves local `think` unset. No level string. No probe of `/api/show`.

The minimize control gets a tooltip in the turn the strip is built. The tip says what `v` does.

The frame reading: type delta `0x06` on channel `0x0003`. Payload after the 24-byte scope prefix is one ASCII byte `t` or `c`, then the UTF-8 text. `0x0100` stays unallocated.

**Truth.** Factory off. `think_stream` for both providers. Kinds `t` and `c` on channel `0x0003`, delta `0x06`, 24-byte scope prefix. Three-line strip, `v` toggles, hidden when there is no thinking. Local `think` true, no stream, no level, no `/api/show`. Session text only.

**Reason.** One control keeps the operator from learning two switches. The data frame stays the finished reply so a thinking chunk is not stored as the answer. The spinner remains the clock so the strip is not mistaken for progress.

**Avoidances.** Do not add a vault column. Do not send a level string. Do not probe `/api/show`. Do not stream the local engine. Do not store a thinking chunk as the reply. Do not allocate `0x0100`. Do not leave the strip up when a call produces no thinking. Do not send the summary as a later message.

**semantic-key:** PK-5748

## Cancel

Cancel drops the run token and aborts that cell's HTTP call, and the shared `Hello` still finishes.

A late reply is still dropped when the token is gone. The cell stores `CANCELLED`. The in-flight reply is not stored.

Cancelling the leader of a probe does not abort the shared `Hello`. The probe package owns that rule.

The cell prompt call is an asynchronous send. The future is held beside that cell's live token. Cancel cancels that future and removes the token, and still cancels the local handle.

**Truth.** Cell HTTP call aborts. `Hello` does not. Late reply dropped. Token `CANCELLED`. Partial reply not stored.

**Reason.** Dropping the token alone left the HTTP call running and spent the key. Aborting the cell call stops the spend. Aborting `Hello` would punish every waiter for one leader's cancel.

**Avoidances.** Do not abort `Hello` from a cell cancel. Do not store the in-flight reply. Do not accept a late reply after the token is gone. Do not leave the HTTP call running after cancel.

**semantic-key:** PK-5749

## Roux region lock

The sketch holds the facade mutex through complete, and the later yield runs the provider call after that lock is released.

The facade mutex stays around clear and every add. This sketch holds it through complete. The wire body stays the prompt already built.

The hold is that user's region, so a local complete on the same user waits inside it too.

The semaphore of 3 stays. A second call for that user waits on the mutex.

The locked stage sketch still says the mutex is released before complete. That sentence stays on that page. This package is the yield onto this cake. Do not edit that other page.

The later plan sentence yields the hold through complete. Clear and add stay inside the region lock. The provider call runs after that lock is released, so a pool child and a local complete on the same user overlap. The wire body stays the prompt already built. The semaphore of 3 stays. One pool prompt per user stays on the child gate. The probe lock is not taken while the region lock is held, and the region lock is not held during `Hello`.

The pool process itself is detailed on the server page. The door rule is the overlap, the prompt already built, and the lock order.

This is the facade mutex. It is not the memory engine's lock. Lock order stays facade lock first, then the engine's per-call lock inside clear and add. Never the reverse.

**Truth.** Clear and add stay inside the region lock. The later yield releases the lock before the provider call. Prompt already built. Semaphore 3. One pool prompt per user on the child gate. Probe lock is not held with the region lock. `Hello` does not hold the region lock.

**Reason.** Holding the lock through the provider call would block a pool child on the same user for the whole remote wait. Releasing it after clear and add keeps the working set stable for the prompt that was already built, and lets the two calls overlap. The earlier stage sentence stays on its own page so this cake does not rewrite it.

**Avoidances.** Do not edit the stage sketch. Do not reverse the lock order. Do not hold the region lock during `Hello`. Do not take the probe lock while the region lock is held. Do not rebuild the prompt from the region during complete. Do not change the semaphore of 3. Do not detail the pool scheduler on this page.

**semantic-key:** PK-5750

## Group fail

Any member that failed or was cancelled fails the merge, concatenation does not run, and a cancel-only group does not open the slide.

A member that already stored a valid reply stays.

The merge stores `FAILED` and its reply stays blank.

A hard miss is red. A soft miss is yellow. `cell-busy` is not a failure and does not open the slide.

A group whose only miss is `CANCELLED` stores `FAILED` on the merge and does not open the error slide.

A hard miss is `engine-down`, a remote transport miss, or `too-large`. A soft miss is an empty reply, `grok key missing`, `no-model`, a `Grok HTTP` line, `air-gap`, `burner denied`, `unknown provider`, `unknown role`, `pipe`, `no tool is invocable`, or the owed-answer wait. When any miss in the group is hard, the notice is red. When every miss is soft, the notice is yellow.

The red skin stays the existing slide colors. The yellow skin uses the same geometry: border and title `0xF9A825`, fill `0x3A3014`, ink `0xF8F4E8`. One slide. The named banners still paint the status line.

The layout page stays the source of the layout. This package is the behavior.

**Truth.** `FAILED` or `CANCELLED` fails the merge. Concatenation does not run. Valid replies stay. Merge reply blank. Cancel-only stores `FAILED` and opens no slide. Yellow colors are `0xF9A825`, `0x3A3014`, and `0xF8F4E8`.

**Reason.** One empty member left the merge waiting. Failing the group writes a result. A cancel that is the only miss is not an error the operator needs a slide for, because the operator asked to stop.

**Avoidances.** Do not run concatenation after a failed or cancelled member. Do not clear a stored valid reply. Do not open the slide for `cell-busy`. Do not open the slide for a cancel-only group. Do not add a second window for yellow. Do not treat a soft miss as red when every miss is soft.

**semantic-key:** PK-5751

## Neighbors

The region lock and the group fail moved into the cake, and this page does not keep them outside it.

They are packages on this sketch. They are not a neighbor list left for another build.

**Truth.** Both former neighbors are packages on this page.

**Reason.** Leaving them outside the cake would ship a remote call whose merge still hangs and whose region lock still drops before the call. The sketch pulls them in so the plan cannot forget them.

**Avoidances.** Do not put the region lock or the group fail back outside the cake. Do not drop them from the package order.

**semantic-key:** PK-5752

## Gate

The formal plan is the stamped run-book, and this sketch stays the batch it was cut from.

The locked serial is the source. This page is the batch. Where this page is thinner, the locked serial wins.

The plan names the build. This gate does not retarget the stage marker. It does not by itself write the types.

A gap found after the lock opens the next sketch serial. This file stays.

**Truth.** The plan is stamped from this page. The locked serial still wins where the sketch is thin. A later gap is a new sketch file.

**Reason.** The gate closes the smoke. The build waits for the plan so the sketch is not mistaken for a license to edit types.

**Avoidances.** Do not clobber this file. Do not reopen the locked serial for a gap. Do not retarget the stage from this gate. Do not treat the sketch as thinner than the serial when they disagree. Repair this page instead.

**semantic-key:** PK-5753

## Application yaml reference

Boot loads the server resource file, and the code defaults below apply when a key is omitted.

Binders:

`casserole.*` binds to the casserole properties.

`roux.*` binds to the memory-engine properties.

`spring.*` binds to the framework and to the local-engine client, not to the casserole property types.

Client UI settings live in `~/.ssh-client.properties`, not this file. The later connection document is `~/.casserole/ssh-client.yaml`.

Values below are code defaults when the key is omitted. A checked-in file may override a key. The code default for `max-messages` is 40. A file on disk may set another positive integer. The operator resource file stays off this page. The public example uses `your-grok-api-key`, local base `http://localhost:11434`, model `qwen2.5-coder:7b`, and context length 8192.

Restart the server after changes. None of these keys are live-reloaded.

The remote call does not read a context length from this file. The local engine does.

**Truth.** Three binders. Code defaults apply when a key is omitted. `max-messages` code default is 40. Nothing here is live-reloaded. The operator file stays off the page.

**Reason.** The resource file is the boot contract. A public page can name every key and every code default without copying the operator file, the real key, or a private context length.

**Avoidances.** Do not treat a UI properties file as this yaml. Do not assume a change is live. Do not copy the operator file onto the page. Do not publish a real key, a password, a private model name, or a private context length.

**semantic-key:** PK-5754

## casserole.lamina

The lamina executor is bound on the casserole properties, and metrics stay off unless the key is set.

`metrics` is a boolean. The code default is `false`. When true, the executor logs submit, complete, and fail counts at debug. The default is off.

This key is not a model-door field. It is the executor beside the process that later calls a model.

**Truth.** `casserole.lamina.metrics` defaults to false. True logs submit, complete, and fail counts at debug.

**Reason.** Metrics are diagnostic. They stay off so a normal start does not emit counts the operator did not ask for.

**Avoidances.** Do not turn metrics on by default. Do not treat this key as a model, a context length, or a key.

**semantic-key:** PK-5755

## casserole.ssh

The shell listener is a proof listener, not production authentication, and the public example does not carry a password.

`port` is an int. The code default is `2222`. The range is 1 through 65535. It is the listen port. The client must match it.

`username` is a string. It is the fallback password-auth user if no roster file is found. The sanitize class is `[A-Za-z0-9._-]+`. The local fallback name stays off this page.

`password` is a string. It is the fallback secret. It is ignored when a roster file is present. The public example leaves the password empty. The operator secret stays off this page. Do not copy a password onto this page.

`host-key` is a path. The code default is `hostkey.ser`. It may be relative or absolute. The host-key provider creates the file if it is missing. The operator host-key file stays home. The server creates a host key on first start.

**Truth.** Default port `2222`, range 1 through 65535. Fallback user is sanitized to letters, digits, dot, underscore, and hyphen. Password is ignored when a roster file is present and is empty on the public example. Host key path default `hostkey.ser`, created if missing.

**Reason.** The listener needs a port, a fallback identity, and a host key so a shell can connect before a roster file exists. The password is a secret, so the public example leaves it empty and the operator value stays off the page.

**Avoidances.** Do not publish a password. Do not publish the local fallback username. Do not treat this listener as production authentication. Do not use a port outside 1 through 65535. Do not copy the operator host-key file onto the page.

**semantic-key:** PK-5756

## casserole.chat

The chat keys size the shell and the prompt window, and they are not the local context window.

`system-prompt` is a string. The code default is empty. A blank value leaves a new region without a system frame. A configured value is the first system frame on a new region. Restore does not rewrite an existing history. Chat start and clear keep a configured value and leave a blank value off.

`max-messages` is an int. The code default is `40`. A positive value is recommended. It caps non-system turns in the local prompt. The memory engine still stores everything. `0` in the window API means all. The yaml value should stay positive. A file on disk may set another positive integer. That override is not the code default.

`max-tokens` is an int. The code default is `0`. `0` means off. Any other value is a stored heuristic token cap. It is a dual cap with `max-messages`. The first limit wins, except a user and assistant pair, or a tool and assistant pair, is never split and may slightly exceed the token cap.

`escape-timeout-ms` is an int. The code default is `100`. The code clamps a value below `20` to `100`. It separates a bare escape from a control sequence used by paste and arrows. A typical range is 50 through 150.

`max-paste-chars` is an int. The code default is `65536`. The value must be greater than 0. A value of 0 or below is treated as 65536. It is the server paste abort. The client has its own cap.

`spinner` is a string. The code default is `ascii`. The allowed values are `ascii`, `braille`, and `none`. Anything else becomes `ascii`. It is the in-prompt thinking animation. `braille` needs font glyphs. A font that lacks them shows missing-glyph boxes.

`spinner-ms` is an int. The code default is `100`. It is clamped to 40 through 500. It is the frame interval.

`smart-format` is a boolean. The code default is `true`. `false` keeps identity, carriage-return line feeds, and links only. `true` tries JSON, then a fence, then a table, then a list, then markdown, and takes the first match.

`table-style` is a string. The code default is `ascii`. `ascii` uses plus, minus, and bar. `box` uses the box-drawing characters. Any other value is treated as ascii in the drawers. It is the table chrome when the table handler wins.

`history-size` is an int. The code default is `500`. A value below `10` becomes `500`. It is the command-line history file `~/.casserole/{user}/history`, used by up and down. It is not the chat memory.

`list-preview-chars` is an int. The code default is `120`. `0` means never truncate. It is the default terse body length for message list and message find. A verbose list ignores it.

The roster file is not this yaml. Load one file, in order: `./users.yaml`, then `~/.casserole/users.yaml`, then the classpath `users.yaml`, else the shell fallback pair. A file that is present is the source of truth. There is no merge. Duplicate names refuse boot. Invalid region-owner names are skipped. A public roster uses demo names and demo passwords. Those passwords are for a local start. This page does not copy an operator password.

The prompt is the system frame plus the tail of non-system turns until `max-messages` and, when set, `max-tokens`. The store is the full region until clear or chat start. The list is the full region, unmarshalled.

`max-tokens` trims history before the prompt is built. It is not the local context window. That window is `num-ctx`. A value of 0 omits `num-ctx`. The published example is 8192.

**Truth.** Code default `max-messages` is 40. `max-tokens` default 0 means off. Escape timeout default 100, clamp below 20 back to 100. Paste cap default 65536. Spinner default `ascii`, interval default 100 clamped 40 through 500. Smart format default true. Table style default `ascii`. History size default 500, below 10 becomes 500. List preview default 120, 0 means no truncate. System prompt default empty. Roster load order is working directory, then home, then classpath, else the fallback pair.

**Reason.** The prompt window is a tail the model sees. The store is the whole region. Mixing them would drop history the operator can still list. The context window is a different key, so a message cap is not a context length.

**Avoidances.** Do not treat `max-messages` or `max-tokens` as `num-ctx`. Do not split a user and assistant pair, or a tool and assistant pair, to meet the token cap. Do not merge roster files. Do not allow duplicate roster names to boot. Do not put command-line history in the chat memory. Do not publish an operator password. Do not use 0 as the yaml message cap when the intent is the code default of 40.

**semantic-key:** PK-5757

## casserole.grok

The remote key is `casserole.grok.api-key`, the public example is `your-grok-api-key`, and a blank value is a miss.

The key is bound on the casserole properties. Restart the server after a change. The value is not logged. The property is not live-reloaded.

`api-key` is a string. The code default is empty. It is the remote bearer token. Blank is a miss. The remote service reads this first, then `XAI_API_KEY`, then `~/.aetheris/grok.key`, then `~/.casserole/grok.key`. The first hit wins. Empty or absent at each step is a miss. An environment value is trimmed. A key file contributes its first non-empty, non-comment line. A comment is a trimmed line that starts with `#`. A missing file or a read error continues the lookup. The program does not create `grok.key`.

On the wire the value is `Authorization: Bearer` plus the key. The remote call does not send `num_ctx`.

The operator file that holds a real key stays off this page. Do not paste the key into chat, a cell, or a page.

A missing key fails the cell. The status line shows `failed`. The wire message is `grok key missing`. The key text is not in that message.

**Truth.** Property `casserole.grok.api-key`, default empty, public example `your-grok-api-key`. Lookup order is property, `XAI_API_KEY`, `~/.aetheris/grok.key`, `~/.casserole/grok.key`. Not logged. Not live-reloaded. Not sent as `num_ctx`. Miss text is `grok key missing`.

**Reason.** The property is first so a configured value wins, and a blank value falls through so the environment and the two files remain doors. The page shows the shape a person replaces, not a live key.

**Avoidances.** Do not log the value. Do not paste a real key. Do not treat blank as a key. Do not send `num_ctx` on the remote call. Do not create `grok.key` when it is missing. Do not stop the lookup on the first miss. Do not put the key text in `grok key missing`.

**semantic-key:** PK-5758

## spring.ai.ollama.chat.options

A positive `num-ctx` is sent as `options.num_ctx`, and 0, a negative value, or an omitted key leaves the field off.

The key is bound by the local-engine client onto the chat options. A change is read at server start. Restart the server after an edit. No command and no shell control sets these keys.

`num-ctx` is an int. The code default is omitted. A positive integer is sent as `options.num_ctx` on each chat request, on the local load, and on the load half of a swap. Omitted, `0`, or a negative value leaves the parameter off. The local engine then uses the model's own window.

The published example is `8192`. That is the example on the public file. An operator file may override the context length, and that file stays off this page. Do not publish a private context length.

The remote call does not receive this field.

The code default model is `qwen2.5-coder:7b`. An operator file may override the model, and that file stays off this page.

**Truth.** `num-ctx` omitted, 0, or negative omits `options.num_ctx`. A positive integer is sent on chat, on load, and on the load half of swap. The published example is 8192. The remote door does not get the field.

**Reason.** The model's own window is the right fallback when the operator did not set a length. Sending 0 would be a different instruction from omitting the field. The published example is 8192 so a public file has a concrete window without copying an operator override.

**Avoidances.** Do not send `num_ctx` when the integer is 0 or negative. Do not send it on the remote body. Do not publish a private context length. Do not expect a shell command to change this key without a restart. Do not treat the published 8192 as a second code default when the key is omitted.


**semantic-key:** PK-0681 PK-0682

## roux engine

The memory engine page size, frame cap, resident set, and durability mode are code defaults, and the engine process is detailed on the server page.

`page-bytes` is an int. The code default is `1048576`, one mebibyte. It must be greater than 64 plus the frame header, and the frame header is 56 bytes plus its own margin, so the floor is above 64 plus that header. Prefer a multiple of `spill.align-bytes`. It is the size of one page and of one slot in `pages.bin`. The offset is the page id times `page-bytes`. Changing this invalidates an existing `pages.bin`. On restore, refuse to reopen if the manifest page size does not equal the yaml value.

`max-frame-bytes` is an int. The code default is `0`. `0` means `page-bytes` minus 64. Otherwise the range is 1 through `page-bytes` minus 64. A single message larger than this is rejected before rotate. That covers paste and tool dumps.

`resident-pages` is an int. The code default is `2`. A value below 1 is treated as 1. It is the working set of the hot page plus page-ins. `1` means hot only, and cold reads use the file channel. If spill is disabled and rotate would exceed this count, rotate fails.

`durability` is a string. The code default is `commit`. The modes are `hot-only`, `commit`, and `append`. It says when the hot slot is written. `commit` and `append` still publish a full page on rotate when spill is on. `hot-only` on the restore slice does no pack I/O at all. Exclusive is off, and the same username may open more than one session.

These keys are the memory engine beside the model door. They do not set the model, the context length, or the remote key. The engine process stays on the server page. This page keeps every field and every default.

**Truth.** Page size default 1048576. Max frame default 0 means page size minus 64. Resident pages default 2, below 1 becomes 1. Durability default `commit`. A page-size change invalidates `pages.bin`. A manifest mismatch refuses reopen.

**Reason.** The page is both the memory unit and the file slot. A frame bigger than the page cannot be stored. Durability chooses whether a kill loses the hot slot. None of that is the model door, and the defaults still belong on this reference because they live in the same boot file.

**Avoidances.** Do not change `page-bytes` on an existing pack and expect the old file to open. Do not let a single message larger than the frame cap rotate. Do not treat resident page count below 1 as zero. Do not treat these keys as `num-ctx` or as the remote key. Do not move the engine process onto this page beyond the fields and the defaults.

**semantic-key:** PK-5759

## roux.durability

Durability chooses when hot bytes hit the pack, and the code default is `commit`.

Aliases: `h0` means `hot-only`. `h2` means `append`. Anything else, including `h1`, means `commit`.

`hot-only` never writes the hot slot. It is memory only: no rotate spill and no close write. An existing user pack is frozen. A process kill loses the whole session. Close on the restore slice unmaps only and does not checkpoint. Exclusive is off. The same username may open multiple independent memory sessions.

`commit` writes the hot slot after a successful user and assistant pair. A process kill loses the in-flight turn, which is not stored anyway. Close on the restore slice checkpoints and writes the manifest. Exclusive is on. One live shell per username.

`append` writes the hot slot on every append, including a system frame on create or clear. A process kill loses almost nothing that was already on the hot slot. Close does extra I/O. Pastes are chatty. Exclusive is on. One live shell per username.

The engine process is detailed on the server page. These three modes and the aliases stay here because they are the boot field.

**Truth.** Default `commit`. `h0` is `hot-only`. `h2` is `append`. `h1` and any other token are `commit`. `hot-only` never writes and turns exclusive off. `commit` writes after a successful pair. `append` writes on every append.

**Reason.** The alias table exists so a short token cannot silently pick a mode the operator did not name. `h1` falls through to `commit` because it is not an alias. `hot-only` is the bench that must not touch the pack.

**Avoidances.** Do not treat `h1` as its own mode. Do not checkpoint on `hot-only` close. Do not leave exclusive on for `hot-only`. Do not expect `append` to be quiet on paste. Do not confuse this field with the model context length.

**semantic-key:** PK-5760

## roux.spill

Spill writes sealed pages to one `pages.bin` per region, and the code default is on.

`enabled` is a boolean. The code default is `true`. `true` sends sealed pages to `pages.bin`. `false` keeps old pages in memory, and hitting `resident-pages` makes rotate fail.

`dir` is a path. The code default is the user home plus `/.casserole/roux`. Any writable directory is allowed. The live layout is `{dir}/{regionUuid}/pages.bin`. The restore slice is `{dir}/{sshUsername}/pages.bin` plus `manifest.bin`.

`align-bytes` is an int. The code default is `4096`. `0` or `1` means no pad. Any other value pads the published used-byte count up, capped at `page-bytes`. The point is fewer ragged extents. A typical value is 4096, a disk block, or a value from 4096 through 65536.

The pack file is one `pages.bin` per region. The slot is the page id times `page-bytes`. It is not a set of per-page files.

This alignment default is a disk pad. It is not the model context length. A context length of 0 omits `num_ctx`. The published context example is 8192.

The engine process is detailed on the server page.

**Truth.** Spill default on. Directory default is the home `roux` folder. Align default 4096. Zero or one means no pad. One `pages.bin` per region. Slot math is page id times page size.

**Reason.** Sealed pages have to leave memory or the resident cap fails the rotate. Alignment is a pad, not a context window, so the number 4096 here must not be read as `num-ctx`.

**Avoidances.** Do not split the pack into per-page files. Do not treat align 4096 as the model context length. Do not enable spill and then expect a full pack write when durability is `hot-only`. Do not pad past `page-bytes`.

**semantic-key:** PK-5761

## roux.crypto

Pack encryption is off, and turning it on makes rotate unsupported.

`enabled` is a boolean. The code default is `false`. `true` makes rotate unsupported. It is not implemented. Leave it `false`.

The engine process is detailed on the server page. The field and the default stay here.

**Truth.** `roux.crypto.enabled` defaults to false. True is unsupported and rotate fails closed as unsupported.

**Reason.** The flag exists so a later implementation has a key. Shipping true before the implementation would break rotate.

**Avoidances.** Do not set `enabled` true. Do not treat the flag as implemented encryption. Do not confuse it with the remote API key.

**semantic-key:** PK-5762

## roux.session

A disk-backed session checkpoints on hangup and reopens on connect, and memory-only mode never writes.

`persist` is a boolean. The code default is `true`. On a disk-backed hangup or shutdown it checkpoints and writes the manifest, and it does not delete. `false` is the older delete behavior. Memory-only never writes.

`restore` is a boolean. The code default is `true`. A disk-backed connect reopens `{dir}/{username}/` if it is present. `false` with leftover files refuses the open and does not clobber them.

`exclusive` is a boolean. The code default is `true`. It applies to disk-backed sessions only. A second shell as the same user is refused while a live region or an operating-system file lock exists. A different username may connect at the same time. A crash leftover releases the lock and the next start may take over. The flag is ignored when the session is memory-only, which is `hot-only` or spill disabled.

`clear` and chat start still wipe the pack in place. The history becomes empty. The folder stays the same.

The engine process is detailed on the server page.

**Truth.** Persist, restore, and exclusive all default to true. Persist false deletes on the older path. Restore false refuses rather than clobber. Exclusive is ignored for memory-only. Clear and chat start wipe in place and keep the folder.

**Reason.** The defaults keep one disk session per user and bring it back on the next connect. Refusing a leftover when restore is false prevents a silent overwrite. Memory-only has nothing to lock.

**Avoidances.** Do not delete the folder on a default hangup. Do not clobber leftover files when restore is false. Do not apply exclusive to a memory-only session. Do not give a second shell the same disk user while a lock is held. Do not change the folder on clear or chat start.

**semantic-key:** PK-5763

## spring.datasource / spring.jpa

The database keys are present and unused by the chat path, and boot still starts the in-memory database.

`spring.datasource.url` on the checked-in example is `jdbc:h2:mem:casserole`. It is in memory and gone on restart.

`spring.datasource.username` on the checked-in example is `sa`.

`spring.datasource.password` is empty.

`spring.datasource.driver-class-name` is `org.h2.Driver`.

`spring.jpa.hibernate.ddl-auto` is `update`. The allowed values are `none`, `validate`, `update`, `create`, and `create-drop`.

`spring.jpa.properties.hibernate.dialect` is `H2Dialect`.

There are no entities. The keys are irrelevant to the shell chat until something uses the persistence API. They are not the model door. The password is already empty. Do not put a real password here.

**Truth.** In-memory URL `jdbc:h2:mem:casserole`, user `sa`, empty password, driver `org.h2.Driver`, schema update, dialect `H2Dialect`. Unused by the chat path. Gone on restart.

**Reason.** The framework starts because the keys are present. Leaving them documented stops a later reader from deleting them as unused and breaking boot, and it keeps the password empty.

**Avoidances.** Do not point this URL at a file database from this page. Do not put a password in the public example. Do not treat these keys as the chat store. Do not add entities from this reference.


**semantic-key:** PK-0683

## spring.ai.ollama

The local engine base, the pull policy, and the model id are read at boot, and the published values are the public example.

The keys are used by the local chat model and by the server commands.

`base-url` is `http://localhost:11434`. That is the local HTTP base. It is also injected into the local load command. An operator host stays off this page.

`init.pull-model-strategy` is `never`. The client must not pull on boot. The other named values are `always` and `when_missing`. The missing-model name may vary by the client line. Keep `never`. Pull the model yourself.

`chat.model` is `qwen2.5-coder:7b`. It is the client model id.

`chat.options.model` is `qwen2.5-coder:7b`. This is the id the shell factory, the status line, the local load, and the local stop use. Keep both model keys the same.

`chat.options.num-ctx` on the published example is `8192`. A positive integer is sent. The value 0 omits the field. An omitted or negative value omits the field. Do not publish a private context length. An operator file may override the model or the context length, and that file stays off this page.

The local engine must already have the model. Boot starts if the local engine is down. Chat turns go red.

The public example keeps every key in this table. The two public copies of the file stay the same bytes. The operator file stays home.

A missing model on a call says `no model defined` when the resolved id is empty. A null local response throws `Ollama returned no response.`

**Truth.** Base `http://localhost:11434`. Pull strategy `never`. Both model keys `qwen2.5-coder:7b`. Published context example `8192`. Boot does not pull. Boot does not require the engine to be up. A down engine turns chat red.

**Reason.** Pulling on boot would change the operator's machine. `never` leaves that choice to the operator. Two model keys exist because different readers use different paths, and they must match or status and the call disagree.

**Avoidances.** Do not publish an operator host. Do not set the pull strategy to `always` on the public example. Do not let the two model keys drift. Do not publish a private model name or a private context length. Do not send context length 0. Do not expect boot to fail when the local engine is down. Do not copy the operator file onto the page.


**semantic-key:** PK-0684

## Tuning cheat sheet

The cheat sheet is a set of key moves, and it does not change the code defaults until a file sets them.

To force spill in tests, set `roux.page-bytes` to `65536` or smaller, and set `spill.enabled` to true.

For a memory-only bench, set `spill.enabled` to false and raise `resident-pages`.

To enlarge the prompt tail, raise `max-messages` above the code default of 40, or set `max-tokens`. That tail is not `num-ctx`.

For a safer tail on kill, set durability to `append`. That does more writes.

For less disk during chat, set durability to `hot-only`. There is no pack I/O. Exclusive is off. Hangup loses that session.

For a huge single paste, raise `max-frame-bytes` and `page-bytes`, and also `max-paste-chars`.

For a braille spinner, set `spinner` to `braille` and use a font that has those glyphs.

The published local context example stays 8192. The value 0 omits the field. Do not use a page-size or an alignment number as the context length.

**Truth.** Test spill can use page size 65536 with spill on. Memory-only is spill off and a higher resident count. `append` writes more. `hot-only` writes nothing and loses the session on hangup. Prompt tail keys are `max-messages` and `max-tokens`. Code default messages is 40.

**Reason.** The sheet is the short map from a goal to the keys already defined. It does not invent a new default.

**Avoidances.** Do not treat the test page size as the code default. Do not treat `hot-only` as durable. Do not raise `max-messages` and call it the context window. Do not select braille without a font that can draw it. Do not publish a private context length.

**semantic-key:** PK-5764

## Related files

These files are not the server resource yaml, and each one keeps the job the boot file does not.

`~/.casserole/ssh-client.yaml` holds the terminal host, port, font, colors, and accounts. It imports an old `~/.ssh-client.properties` once. It is not the remote settings document. A nested map in it flattens to one string. It also holds the connection password. The password stays off this page. Call settings do not go in it.

The memory pack is `{roux.spill.dir}/…/pages.bin`.

`users.yaml` holds the shell password accounts. The load order is the working directory, then `~/.casserole/`, then the classpath. A present file is the source of truth. There is no merge. Duplicate names refuse boot. The public roster passwords are demo values for a local start. An operator password stays off this page.

`~/.casserole/{user}/history` is the per-user up and down line history. It is not the chat memory.

`hostkey.ser` is the shell host key named by `casserole.ssh.host-key`. The operator file stays home. The server creates a host key on first start.

`~/.casserole/aetheris-grok.yaml` is the remote call document, version 1. It is not this resource file. It has no key field.

`~/.aetheris.yaml` is the air-gap file the server reads. The shell may also read a launch path, then `./aetheris.yaml`, then the home file. They are not one shared file. The air-gap word defaults to explicit `true`.

`~/.aetheris/grok.key` and `~/.casserole/grok.key` are the third and fourth places in the key lookup, after `casserole.grok.api-key` and `XAI_API_KEY`. The public example value is `your-grok-api-key`.

The local base on the public example is `http://localhost:11434`. The code default model is `qwen2.5-coder:7b`. The published context example is 8192. The value 0 omits the field. An operator file may override the model or the context length, and that file stays off this page.

Update this list when a key is added. The session keys for the restore slice are already in the binder.

**Truth.** Connection yaml, pack file, roster, line history, and host key are outside the server resource file. The remote settings document and the air-gap file are outside it too. The key lookup still starts at `casserole.grok.api-key` inside the resource file.

**Reason.** One boot file cannot hold the password, the call settings, and the air-gap word without flattening or leaking. Naming the neighbors keeps each secret in its own file and off this page.

**Avoidances.** Do not put call settings in the connection yaml. Do not put the API key in the remote settings document. Do not merge roster files. Do not treat line history as chat memory. Do not copy a password, a real key, or the operator host key onto this page. Do not publish a private model name or a private context length.

**semantic-key:** PK-5765

# The heuristic stays; the real count is named

Say what this Ollama will return for a real token count, and leave the heuristic in the product.

**semantic-key:** PK-5766

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. There is no product commit. The branch, the action, the stage, and the phase clock are not retargeted. Do not spend the next marker.

No product edit. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new library. No `Cargo.toml` edit.

The probe already ran against local Ollama. Java is not compiled. The crate is not checked. The window stays closed. No pool turn. No live SSH connect. `Session::start` is not called. `chrome::run` is not called.

A land commit records the finding.

**semantic-key:** PK-8049

## Goal

Say what this Ollama will return for a real token count, and leave the heuristic in the product.

**semantic-key:** PK-5767

## What stays

`HeuristicTokenCounter` stays. The Rust `estimate_tokens` copy stays. The canvas estimate stays. The SSH strip stays. The yaml stays. Roux stored counts stay. The SSH INFO plate stays locked. Six open bookings stay open. Rust stays. Java stays.

**semantic-key:** PK-5768

## Finding

`POST /api/tokenize` is absent on Ollama 0.40.12. The docs put the input count in `prompt_eval_count` on the done frame of `POST /api/generate` or `POST /api/chat`. The body needs `model` and the text (`prompt`, or `messages` for chat). `stream: false` returns one object. That request loads the model when the process list is empty and it generates output tokens. `options.num_predict` 1 capped the probe at one output token. `options.num_predict` 0 did not. `keep_alive` 0 unloads when the call ends. The count includes the rendered template. A later plate that wants this number on the glass has to name that live completion. This plate does not.

**semantic-key:** PK-5769

## Proof

Local `GET /api/version` returned 0.40.12. `POST /api/tokenize` returned 404. The `abcd` probe with `num_predict` 1 returned `prompt_eval_count` 30 and `eval_count` 1. The process list was empty after `keep_alive` 0. The window stays closed. No pool turn. No live SSH connect. `Session::start` is not called. `chrome::run` is not called. Java stays. Rust stays.

**semantic-key:** PK-5770

## Land

The investigation is named. Java stays. Rust stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** Ollama 0.40.12 has no `POST /api/tokenize`. The input count is `prompt_eval_count` on the done frame of generate or chat. The `abcd` probe with `num_predict` 1 returned `prompt_eval_count` 30 and `eval_count` 1. The heuristic stays in the product. There is no product commit.

**Reason.** A real token count needs a live completion, and this plate only names that requirement.

**Avoidances.** Do not replace `HeuristicTokenCounter` or `estimate_tokens` on this plate. Do not call tokenize as if it existed on 0.40.12. Do not treat `num_predict` 0 as a one-token cap. Do not put the count on the glass. Do not spend the next marker. Do not move Java or Rust. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not open the window, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-5771

# The heuristic stays; the real count is named

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-8190

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page. No product moves. The investigation is the plate. The heuristic stays. Java stays. Rust stays. No extra cut is made.

A real token count would replace the divide-by-4 guess. This plate names what the running Ollama requires. The divide-by-4 estimate stays in the product.

**semantic-key:** PK-5772

## Slice

The requirement for an actual Ollama token count on the running server. The divide-by-4 estimate stays in the product.

**semantic-key:** PK-5773

## Wire

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The investigation names what the running Ollama requires for a real token count. `HeuristicTokenCounter` stays. The Rust `estimate_tokens` copy stays. No product moves. Java stays. Rust stays.

**Reason.** The divide-by-4 guess is still the product count until a later plate names the live completion.

**Avoidances.** Do not replace the heuristic on this plate. Do not move Java or Rust. Do not move the yaml or Roux stored counts. Do not treat this row as a feature. Do not unlock the SSH INFO plate.

**semantic-key:** PK-8155

## Grok gets a provider, and the stored target stays stored

The stored `execution_target` selects the provider on the outbound call, and a failure does not rewrite that stored field.

The service interface is `LlmProvider`. There is no registry class in the tree. The map is `LlmRouter.complete(target, prompt)`. The caller supplies the `Prompt`. The terminal `swap` stays. The router does not replace `swap`. That map is the approved final shape.

`AmwpFacade.execute` reads `execution_target`. The token before `/` selects the provider. An empty stored field selects `ollama` on the outbound body only. That empty field is not written back. `ollama` selects the local provider. `grok` selects the remote provider. Any other token fails the cell. The stored target is left as written.

The local provider is the only provider that sets `num_ctx`. The remote provider has its own `complete`. Local options do not ride on a remote call. Integer `0` omits the `num_ctx` field. The named default is 4096. The published example is 8192. The local base is `http://localhost:11434`.

The remote key is read from the environment variable `XAI_API_KEY`, then from `grok.key` under `~/.aetheris/`, then from `grok.key` under `~/.casserole/`. The post URL is `https://api.x.ai/v1/chat/completions`. A missing key, an empty model, or an empty URL fails the cell. The published placeholder for a key value is `your-grok-api-key`. The key is not written into cell text. The key is not logged. A burner cell is still not a key store. No key value is stored in the tree.

The per-cell model control lists the host's local models and `grok/<model>`. It writes `CellRecord.executionTarget`. The existing update already persists `execution_target`. The status line prints the focused cell's target. An empty stored field prints `ollama`.

Other cloud vendors stay unbuilt. Streaming stays one-shot `complete()`. `org.aetheris` does not grow a reactive stream type.

**Truth.** `LlmRouter.complete(target, prompt)` maps the token before `/` to one `LlmProvider`. Empty stored text goes out as `ollama` and is not written back. Only the local provider sets `num_ctx`. A missing remote key fails the cell. The stored target stays as written.

**Reason.** A hand-edited `grok/model` cell still ran on the local engine, and a shared `complete` would have attached local options to the remote call.

**Avoidances.** Do not add a registry class beside the router. Do not let the router replace terminal `swap` or build the prompt. Do not write an empty field back as `ollama`. Do not let `num_ctx` ride on the remote call. Do not log the key or store it in cell text. Do not treat a burner cell as a key store. Do not build the other cloud vendors in this slice. Do not add a reactive stream type to `org.aetheris`.


**semantic-key:** PK-0685 PK-0686 PK-0687 PK-0688 PK-0689 PK-0690 PK-0691 PK-0692 PK-0693 PK-0694 PK-0695 PK-0696 PK-0697 PK-0698 PK-0699 PK-0700 PK-0701 PK-0702 PK-0703 PK-0704 PK-0705 PK-0706 PK-0707 PK-0708 PK-0709 PK-0710 PK-0711 PK-0712 PK-0713 PK-0714 PK-0715 PK-0716 PK-0717 PK-0718 PK-0719 PK-0720 PK-0721 PK-0722 PK-0723 PK-0724 PK-0725 PK-0726 PK-0727

## One provider call

The terminal and the notebook both call `LlmRouter.complete(target, prompt)`, and the caller supplies the prompt.

The terminal shell and `AmwpFacade` each build a message list and call `complete`. The shared method is `LlmRouter.complete(target, prompt)`. The caller supplies the `Prompt`. The router does not build it, does not walk the vault, and does not prepend a mood line.

The terminal builds its own prompt and calls the router with target `ollama` and region `{user}`.

The notebook walker builds the working set. The facade calls the router with the cell target and region `{user}.aetheris`.

The local options stay inside the local provider. Only that provider sets `num_ctx`. Integer `0` omits the field. The named default is 4096. The published example is 8192. The local base is `http://localhost:11434`. The remote URL is `https://api.x.ai/v1/chat/completions`. The remote provider does not set `num_ctx`.

Terminal cancel and notebook cancel stay two paths. `ThinkingClock` stays the one clock.

**Truth.** `LlmRouter.complete(target, prompt)` is the one call. The caller supplies the `Prompt`. The terminal uses `ollama` and `{user}`. The notebook uses the cell target and `{user}.aetheris`. Only the local provider sets `num_ctx`.

**Reason.** Two methods that each prepend a system line and call `complete` will drift, and a shared method that walks the vault or adds a mood line would change the terminal.

**Avoidances.** Do not let the router build the prompt, walk the vault, or prepend a mood line. Do not put notebook turns on `{user}`. Do not put terminal turns on `{user}.aetheris`. Do not let local options ride on the remote call. Do not merge the two cancel paths. Do not add a second clock.


**semantic-key:** PK-0728

## Grok and the provider router

`execution_target` is `provider/model`, and the router refuses a cloud target before it calls the provider when air-gap, a down local engine, or burner policy says no.

The router runs in this order:

1. Air-gap true and a cloud target: fail the cell, show the air-gap banner, and leave the stored target alone.
2. Local engine down and a local target: fail the cell. The banner says engine-down.
3. Ephemeral, cloud, and policy deny: fail the cell.
4. Otherwise `LlmRouter`.

Keys are read from the environment variable `XAI_API_KEY`, then `grok.key` under `~/.aetheris/`, then `grok.key` under `~/.casserole/`. The URL is `https://api.x.ai/v1/chat/completions`. The published placeholder for a key value is `your-grok-api-key`. The key is not cell text, unless the cell is a burner, and a burner is still not a key store. The key is not logged. A missing key fails the cell.

The local engine remains the wired local engine. Its base is `http://localhost:11434`. The remote provider is the first cloud implementation. Other cloud vendors stay unbuilt.

`complete()` stays one-shot. No reactive stream type in `org.aetheris` on day one.

One provider method takes the working set, the target, and the region id. The terminal passes `{user}`. The notebook passes `{user}.aetheris`. The caller supplies the turns. Local options stay inside the local provider. The method is `LlmRouter.complete`. The router does not build the prompt. Only the local provider sets `num_ctx`. Integer `0` omits the field. The named default is 4096. The published example is 8192.

**Truth.** The router is four steps ending in `LlmRouter.complete(target, prompt)`. The caller supplies the turns. Local options stay local. The remote key is never logged and never required to live in ordinary cell text.

**Reason.** One shared `complete` attached local options to every call, and a cloud target had no refuse before the body left the machine.

**Avoidances.** Do not rewrite the stored target on a refuse. Do not use one banner for air-gap and engine-down. Do not let the router build the prompt. Do not put both regions on one id. Do not add a reactive stream type. Do not build the other cloud vendors here. Do not set `num_ctx` on the remote call.


**semantic-key:** PK-0729 PK-0730

## The router sees a prompt and a target

`LlmRouter.complete` receives a prompt the caller already built and a target, and it does not walk the vault or prepend a mood line.

The finding is that a shared complete path can walk the vault or prepend the mood line and change the terminal.

The terminal builds its prompt, including the host system line, and calls `LlmRouter.complete` with target `ollama` and region `{user}`. The default host line is `You are the Casserole assistant. Be concise and useful.`

The notebook walker builds the working set, including the host line and the mood line when one is approved, and the facade calls `LlmRouter.complete` with the cell target and region `{user}.aetheris`.

The router selects the provider from the token before `/`. An empty target selects `ollama`. The local provider alone sets `num_ctx`. A remote provider does not. Integer `0` omits the field. The named default is 4096. The published example is 8192. The local base is `http://localhost:11434`. The remote URL is `https://api.x.ai/v1/chat/completions`. The remote key is `XAI_API_KEY` or `grok.key`. The published placeholder is `your-grok-api-key`.

Terminal `swap` still loads the local model. The router does not replace `swap`.

The seam is that `LlmProvider` already has `getId` and `complete(Prompt)`. The router is the map those providers were missing. The comment forbids a `CellRecord` parameter.

**Truth.** The caller supplies the `Prompt`. The router maps the token before `/` and does not accept a `CellRecord`. Empty target selects `ollama`. Only the local provider sets `num_ctx`. `swap` stays.

**Reason.** A shared method that walks the vault or adds the mood line would change the terminal prompt and hide who built the turns.

**Avoidances.** Do not give the router a `CellRecord` parameter. Do not let it open the vault, read the region, or prepend a mood line. Do not let it replace `swap`. Do not set `num_ctx` on the remote provider. Do not put the terminal on `{user}.aetheris` or the notebook on `{user}`.


**semantic-key:** PK-0731 PK-0732
