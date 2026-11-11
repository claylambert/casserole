# org.casserole.llm

Contract file. Two sides match this component.

**Depends:** [java/org/casserole/config/config.spec.md](../config/config.spec.md)

# Model door

This page builds the provider door. The map is [program-spine.md](../../../../README.md).


**semantic-key:** PK-0561 PK-0562

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

## Console model

The VT100 console returns on the last selected model family. Pool stays pool. The word survives an application restart.

The shell used to start each session on ollama and forget the word last set. The session now reads `~/.casserole/{user}/console-model` before the first turn. Version 1 carries the family word. The word is ollama, grok, or pool. A missing file leaves the family at ollama. A version other than 1 is left unread and is not overwritten. The dialog set writes the file. The shell word `model` writes the file. The dialog still asks the shell, so the modal shows the stored word. The next turn uses that word. Pool stays pool. The notebook strip stays on the bottom. The VT100 strip stays on the top. Ollama and Grok options stay as they are.

The word sits beside the console history and off the vault. The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this word waits until it is named. Nothing beside this word moves. The cells already on the canvas stay as they are.

**Truth.** Before the first turn, the session reads version 1 of `~/.casserole/{user}/console-model`. The family word is ollama, grok, or pool. A missing file leaves the family at ollama. A version other than 1 is left unread and is not overwritten. Setting the model in the dialog, or with the shell word `model`, writes the file. The modal shows the stored word because it still asks the shell. The next turn uses that word. Pool stays pool.

**Reason.** The console was returning on ollama even when the last choice was pool. The last family has to survive an application restart, and pool has to stay pool.

**Avoidances.** Do not start a session that has a stored word back on ollama. Do not store the word in the vault. Do not read or overwrite a version other than 1. Do not accept a family word other than ollama, grok, or pool. Do not move the notebook strip or the VT100 strip. Do not change the Ollama or Grok options. Do not write a Rust crate on this row.


**semantic-key:** PK-0648 PK-0649 PK-0650 PK-0651 PK-0652 PK-0653 PK-0654 PK-0655 PK-0656 PK-0657 PK-0658 PK-0659 PK-0660 PK-0661 PK-0662 PK-0663 PK-0664 PK-0665 PK-0666 PK-0667 PK-0668 PK-0669 PK-0670 PK-0671 PK-0672 PK-0673 PK-0674

## Chosen model

The VT100 console returns on the model chosen in Model Config. The choice survives an application restart.

The modal writes the chosen model into `~/.casserole/{user}/console-model` with the family word. The version stays 1. The lines are the Ollama id, the Grok id, think, effort, send effort, stream, temperature when it was set, max tokens when it was set, burner, and pool think. A missing file leaves the family at ollama and the model at `qwen2.5-coder:7b`. A file that carries only the family word still reads. A version other than 1 is left unread and is not overwritten. The dialog set writes the file. The shell word `model` writes the file. A Grok turn that turns send effort off writes the file. The next session reads the file before the first turn. The dialog still asks the shell, so the modal shows the stored model. Pool stays pool. An Ollama id stays that id. A Grok id stays that id. The server process that is already running keeps the shell it loaded. A new server process writes the file and reads it on the next session. The notebook strip stays on the bottom. The VT100 strip stays on the top.

The chosen model sits beside the console history, in the same file as the family word. The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this model waits until it is named. Nothing beside this model moves. The cells already on the canvas stay as they are. Use confirmed the chosen model.

**Truth.** Version 1 of `~/.casserole/{user}/console-model` stores the family word together with the Ollama id, the Grok id, think, effort, send effort, stream, temperature when set, max tokens when set, burner, and pool think. A missing file leaves the family at ollama and the model at `qwen2.5-coder:7b`. A family-only file still reads. A version other than 1 is left unread and is not overwritten. Dialog set, the shell word `model`, and a Grok turn that turns send effort off all write the file. The next session reads it before the first turn. An already-running server keeps the shell it loaded. A new server process writes the file and reads it on the next session. Pool stays pool. An Ollama id stays that id. A Grok id stays that id.

**Reason.** The family word was surviving, and the model chosen in Model Config was not. That chosen model has to come back after an application restart. Pool must stay pool, an Ollama id must stay that id, and a Grok id must stay that id.

**Avoidances.** Do not drop temperature or max tokens when they were set, and do not write them when they were not set. Do not refuse a file that carries only the family word. Do not read or overwrite a version other than 1. Do not reload an already-running server process from the file. Do not move the notebook strip or the VT100 strip. Do not put the chosen model in the vault. Do not write a Rust crate on this row.


**semantic-key:** PK-0675

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

## Selecting Grok asks for the language-model catalog

Selecting Grok asks for the language-model catalog.

The dialog sends `grok_models:` and a newline on channel A. Opening on Grok queues that ask. Choosing Grok queues that ask. The server answers off the execute path. The reply is `grok_models_result` on channel A. A miss is that header and no model lines.

The server GETs `https://api.x.ai/v1/language-models`. The call uses HTTP/1.1 and follows no redirect. The key rides on the Authorization header. A missing key returns no model lines. A status outside 200 through 299 returns no model lines. A parse miss returns no model lines. The key is not logged. The key is not written on the reply.

A top-level array is the list. An object uses `models` when that field is an array, and otherwise `data`. A textual element is one id. An object uses a textual `id`, then each textual alias. An object with no textual `id` uses a textual `name`. Each id is trimmed. An empty id is skipped. An id that contains a newline or a carriage return is skipped. A repeated id is skipped. The order is the order in the body.

A reply with model lines replaces the combo. An unknown current id stays first. A reply with no model lines leaves the static rows. A shut dialog leaves the combo. The Rust client asks the same way. The Rust client does not GET the catalog.

The server parse is languageModelIds. The server answer is answerGrokModels. The dialog ask is askGrokModels. The dialog fill is applyGrokModelsResult. The row build is grokHostRows. The Rust wire is GrokModels. The Rust body is grok_models_body. The Rust apply is apply_grok_models.

**Truth.** Selecting Grok asks the server for the language-model catalog. The server GETs `https://api.x.ai/v1/language-models` on HTTP/1.1 and follows no redirect. A reply with model lines replaces the combo. An unknown current id stays first. A reply with no model lines leaves the static rows. The Rust client asks the same way and does not GET the catalog.

**Reason.** The Grok model combo was a fixed table. The combo never asked for the list. The ask is a channel A catalog frame, the same shape as the tag ask.

**Avoidances.** The Rust client does not GET the catalog. The call does not follow a redirect. The key is not logged. The key is not written on the reply. An unknown current id is not dropped. A reply with no model lines does not replace the static rows. The ask is not answered on the execute path.

**semantic-key:** PK-8253
