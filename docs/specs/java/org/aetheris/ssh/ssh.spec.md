# org.aetheris.ssh

Component file. It carries how this package is built.

**Depends:** [java/org/aetheris/config/config.spec.md](../config/config.spec.md), [java/org/aetheris/swing/swing.spec.md](../swing/swing.spec.md), [java/org/aetheris/wire/wire.spec.md](../wire/wire.spec.md), [java/org/casserole/chat/chat.spec.md](../../casserole/chat/chat.spec.md), [java/org/casserole/config/config.spec.md](../../casserole/config/config.spec.md), [java/org/casserole/lamina/Lamina.spec.md](../../casserole/lamina/lamina.spec.md), [java/org/casserole/lamina/lamina.spec.md](../../casserole/lamina/lamina.spec.md), [java/org/casserole/llm/llm.spec.md](../../casserole/llm/llm.spec.md), [java/org/casserole/mood/mood.spec.md](../../casserole/mood/mood.spec.md), [java/org/casserole/pool/pool.spec.md](../../casserole/pool/pool.spec.md), [java/org/casserole/ssh/shell/shell.spec.md](../../casserole/ssh/shell/shell.spec.md), [java/org/roux/model/model.spec.md](../../roux/model/model.spec.md), [java/org/roux/roux.spec.md](../../roux/roux.spec.md), [java/org/roux/session/session.spec.md](../../roux/session/session.spec.md), [java/org/roux/spring/spring.spec.md](../../roux/spring/spring.spec.md), [java/org/roux/token/token.spec.md](../../roux/token/token.spec.md)

**Depends:** [java/org/casserole/casserole.spec.md](../../casserole/casserole.spec.md)
**Depends:** [rust/aetheris/aetheris-client/src/chrome.spec.md](../../../../rust/aetheris/aetheris-client/src/chrome.spec.md)

# Aetheris Shell

This page builds the Java shell around the terminal. The map is [program-spine.md](../../../../README.md). The notebook is [aetheris-java-canvas.md](../swing/swing.spec.md).


**semantic-key:** PK-3491 PK-3492 PK-3493 PK-3494 PK-3495 PK-3496 PK-3497 PK-3498 PK-3499 PK-3500 PK-3501 PK-3502 PK-3503 PK-3504 PK-3505 PK-3506 PK-3507 PK-3508 PK-3509 PK-3510 PK-3511 PK-3512 PK-3513 PK-3514 PK-3515

## The main window returns to its saved desktop position and maximized state, and a missing or off-screen position is centered.

The shell already kept the window width and height. It also keeps where the window sits on the desktop, and whether it is maximized. The new keys are `ui.frame.x`, `ui.frame.y`, and `ui.frame.maximized`. They sit beside `ui.frame.width` and `ui.frame.height` in the shell client file.

A missing position still centers the window, at the saved size. A position that misses every screen is centered, and the saved size is kept. A maximized window comes back maximized. The stored width and height stay the restored size. A maximized frame writes the maximized flag and leaves that rectangle alone.

A move, a resize, or a change of the maximized state saves after the same 400 ms pause. Exit saves too. An iconified window does not replace the stored rectangle.

A probe pointed the store at a temp file. The live client file was not written. A rectangle at 48, 64 sized 860 by 660 came back there, and that frame was not shown. Maximized, the frame on screen was 1550 by 974, and the file still held 48, 64 and 860 by 660, with maximized true. An origin of -10000, -10000 at 900 by 700 was centered at 318, 130 and kept 900 by 700. A missing origin at 880 by 620 was centered at 328, 170. A disk round trip kept 120, 80 and 900 by 700, then kept that size when the maximized flag flipped to true. The window was not opened by hand. The maximized check showed a frame and disposed it.

**Truth.** The shell client file stores `ui.frame.x`, `ui.frame.y`, and `ui.frame.maximized` beside width and height. A saved on-screen rectangle returns there. A maximized window returns maximized and the stored rectangle stays the restored size. Saves wait 400 ms, and exit saves too.

**Reason.** Width and height alone drop the desktop position and the maximized state. An off-screen or missing origin is centered so the window is reachable, and the saved size is kept. A maximized frame does not overwrite that restored rectangle. An iconified window is not a new rectangle, so it does not replace the stored one. The probe used a temp file so the live client file was not written.

**Avoidances.** Do not drop a saved size when centering. Do not write the maximized on-screen size over the restored rectangle. Do not let an iconified window replace the stored rectangle. Do not write the live client file from the probe. Do not leave the maximized probe frame on screen.


**semantic-key:** PK-3516 PK-3517 PK-3518 PK-3519 PK-3520 PK-3521 PK-3522

## `select-model` at the console prompt, and Select model on the console menu, open Model Config held on Ollama for the shell.

The model is chosen from the VT100 console, inside the command set that already runs there. `select-model` at `cmd>` opens the Model Config modal. A right-click on the console offers Select model and opens that same modal. The modal was built once so a later context could use it. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The command name is `select-model`. It is registered with the other console commands. `help` lists it. The two-word forms `select model` and `list models` stay tabled. The menu label is Select model. It sits on the console menu that already holds Import.

The modal is the Model Config modal already on the cell. A console open uses that dialog. The family control stays on the dialog and is held on Ollama. Grok and Pool stay the cell lanes. A console open does not write a cell, a Grok settings row, or a pool flag. Thinking and effort on the Ollama pane write this console. A cell open stays the cell modal path.

`select-model` runs on the server. It writes one private OSC on the VT100 stream, then the line `Model Config`. The client removes that OSC before the terminal paints it and opens the modal with the shell model and the shell think string. A raw SSH session shows the line. It has no modal. The menu item, when the session is not muxed, leaves the modal closed and the status line says the raw console has no modal.

A menu click on a muxed session asks the server for the shell model on the existing Scope A data frame. The first line is `shell_model:`. The reply is `shell_model_result:`. A change in the modal sends `shell_model_set:` on that same frame. Both are recognized before a cell execute, so they do not take an execute slot and they do not mark a cell busy. There is no new channel and no new wire type. The cell id on these frames is nil.

The shell stores the model name it already stored, and a think string beside it. The next console chat turn sends `ollama/` plus that model name, and that think string. An empty model name keeps the configured model. A turn already inside the call keeps the name it started with. `swap` and `ollama load` stay the weight movers. This click does not POST to Ollama and does not unload a model. The status key `model` stays. After a set, the console prints `model` and the id.

One modal flag. A second open while the dialog is up does nothing. The menu tip is written in this same turn. It names the console, the Ollama id, Thinking, and that Grok and Pool stay on the cell. The new public type this build names is `SelectModelCommand`. The new methods this build names are `CommandContext.getConsoleThink`, `AmwpClientListener.onConsoleModel`, and `ChatShell.applyConsoleModel`. No new package, vault column, G-key, channel, or wire type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent.

The command stays on the server and the dialog stays on the client. Grok and Pool stay on the cell. Select model sits beside Import. The shell model rides the data frame that already carries `show:` and `tags:`, and that model is put on the next console turn. The raw session and the nil cell id stay. The row is the build.

**Truth.** `select-model` is a console command that `help` lists. On a muxed session it opens Model Config held on Ollama and writes the shell model and think string. A raw session prints `Model Config` and has no modal. The frames are `shell_model:`, `shell_model_result:`, and `shell_model_set:` on the existing Scope A data frame, with a nil cell id. The new public type is `SelectModelCommand`.

**Reason.** The modal was built once so the console can use it without a second dialog. Grok and Pool stay cell lanes so a console open does not write a cell, a Grok settings row, or a pool flag. The private OSC is removed before paint so the terminal does not show the control sequence, and the following line `Model Config` is what a raw session can show. The frames are recognized before a cell execute so they do not take a slot or mark a cell busy. A turn already inside the call keeps the name it started with. `swap` and `ollama load` stay the weight movers, so this click does not POST and does not unload a model.

**Avoidances.** Do not take `select model` or `list models` off the table. Do not open a second modal while the dialog is up. Do not write a cell, a Grok settings row, or a pool flag from a console open. Do not give these frames a cell id. Do not add a channel or a wire type. Do not POST to Ollama or unload a model from this click. Do not open the window, make the live POST, or send a pool turn from this filing.


**semantic-key:** PK-3523 PK-3524 PK-3525 PK-3526 PK-3527 PK-3528 PK-3529 PK-3530 PK-3531 PK-3532 PK-3533 PK-3534 PK-3535 PK-3536 PK-3537 PK-3538 PK-3539 PK-3540 PK-3541 PK-3542 PK-3543 PK-3544 PK-3545 PK-3546

## Close remembers the product tab, SSH returns on SSH without connecting, and Aetheris returns on Aetheris and connects.

The close remembers the product tab. SSH comes back on SSH. Aetheris comes back on Aetheris and connects. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The two product tabs are SSH and Aetheris. The word is stored with the client window, in the same file that already stores the frame. The key is `ui.product.tab`. The words are `ssh` and `aetheris`. A missing word and any other word read as `ssh`.

A click on SSH stores `ssh`. A click on Aetheris stores `aetheris`. Exit confirmed stores the tab that is selected, and it stores that word before the sessions are disposed. Cancel leaves the stored word. A selection the program makes, including the bounce to SSH when the mux drops, does not store.

The next open with `ssh` shows the SSH tab and does not connect. The next open with `aetheris` connects the startup pane with the host, port, user, password, and mode already on the connection panel, then selects Aetheris once that tab is enabled. The Aetheris tab stays disabled until the focused session is muxed.

A connect that does not enable the tab leaves SSH showing. The stored word stays `aetheris` until a click or a confirmed exit on SSH. A click on SSH while the restore is waiting cancels that restore. An empty password uses the failure dialog the Connect button already uses. One pane starts. Canvas tabs stay as they are.

The new methods this build names are `SshClientProperties.getProductTab` and `SshClientProperties.setProductTab`. No new package, public type, vault column, G-key, channel, or wire type. The help dialog stays. The exit question stays.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

The Aetheris tab stays dark until the mux is up, and a program bounce does not write `ssh`. The exit question stays. The word is stored beside the frame, and Aetheris is selected after the tab enables. The empty password and the raw mode stay. The row is the build.

**Truth.** `ui.product.tab` stores `ssh` or `aetheris`. A missing or other word reads as `ssh`. A confirmed exit stores the visible tab before sessions are disposed. The next `aetheris` open connects with the host, port, user, password, and mode already on the panel, then selects Aetheris once the tab is enabled. The next `ssh` open does not connect.

**Reason.** The close should return to the product that was in front. Cancel must not change the stored word. A program bounce to SSH, including when the mux drops, must not overwrite a stored `aetheris`. The Aetheris tab stays disabled until the focused session is muxed, so the select waits until that tab is enabled. An empty password uses the failure dialog Connect already uses. One pane starts, and canvas tabs stay as they are.

**Avoidances.** Do not store a program-made selection. Do not store the tab after sessions are disposed. Do not connect on a stored `ssh`. Do not enable Aetheris before the focused session is muxed. Do not start a second pane. Do not change the help dialog or the exit question. Do not add a package, a public type, a vault column, a G-key, a channel, or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3547

## The console model modal lists Ollama, Grok, and Pool, and the next console turn uses the same door a cell uses.

The model modal opened from the VT100 console offers only Ollama. Pool and the Grok models belong on that modal too. The old console path is the Ollama process, and that path can go. Where it fights the cell method, it leaves the VT100 console. The cell method is the common method for the console and for cells. The modal close button does not close, and it repeats the model line on the console. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The family control on a console open lists Ollama, Grok, and Pool, and the control is enabled. A cell open stays the cell modal path. A console open writes no cell and no Grok settings file. The nil cell id stays the frame id. The shell session holds the choice.

`shell_model:` and `shell_model_set:` stay the frames. A set and its result carry `provider:`, `model:`, `ollama_model:`, `think:`, `grok_model:`, `grok_think:`, `effort:`, `send_effort:`, `stream:`, `temperature:`, `max_tokens:`, `burner:`, and `pool_think:`. A missing provider reads as `ollama`. There is no new channel and no new wire type.

The next console turn calls `AmwpFacade.completeTurn`. That is the cell door: the air-gap checks, the probe, `llmRouter.complete` with the call options, and `poolChat.prompt`. The console does not take the execute semaphore. `execute` calls the same private refusal and the same private routed complete, and the cell still sends its own frames. An empty Ollama id keeps the configured model. An empty Grok id fails with `no model defined`. Pool sends no model id. The shell mints one pool boot for the SSH session. Air-gap stays true. The console turn is not a burner cell. The burner box is stored.

`swap <from> <to>` no longer spawns `ollama` and no longer POSTs `/api/generate`. When the family is `ollama`, `to` becomes the shell model name. When the family is `grok` or `pool`, the command writes the family and leaves the target. `ollama` and `ollama stop` stay the explicit binary commands. `select model` and `list models` stay tabled.

The modal close marks the dialog quiet before dispose, so dispose does not send `shell_model_set:`. A result after that close does not open the modal. `select-model` and the menu clear that mark and ask `shell_model:` before the dialog opens. The product-tab close stays its own path.

The new methods this build names are `AmwpFacade.completeTurn`, `AmwpFacade.cancelPool`, `ChatShell.applyConsoleTarget`, `ChatShell.consoleModelBody`, `ChatShell.consoleTargetLabel`, and `CommandContext.getConsoleProvider`. The `ChatShell` constructor gains `AmwpFacade`. No new package, public type, vault column, G-key, channel, or wire type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

The Grok file stays off the nil id, swap stays off the process, and a dispose does not send a set. The menu tip is rewritten for the three families. The console turn goes through `completeTurn`, and the modal opens from the result. The nil cell, the one shell pool boot, and the semaphore on the cell execute stay. The row is the build.

**Truth.** A console open enables a family control of Ollama, Grok, and Pool. The next console turn calls `AmwpFacade.completeTurn` and does not take the execute semaphore. Close marks the dialog quiet before dispose so dispose does not send `shell_model_set:`. A missing provider reads as `ollama`. An empty Grok id fails with `no model defined`. Air-gap stays true.

**Reason.** The old console path was an Ollama process, and where it fights the cell method it leaves. One door keeps the air-gap checks, the probe, the routed complete, and the pool prompt. The close button was repeating the model line because dispose sent a set. Marking the dialog quiet first stops that. `swap` no longer spawns `ollama` or posts `/api/generate`, because a family word writes the shell model or the family instead. The console turn is not a burner cell, and the burner box is only stored. One pool boot is minted for the SSH session.

**Avoidances.** Do not write a cell or a Grok settings file from a console open. Do not let the console take the execute semaphore. Do not send `shell_model_set:` from dispose. Do not open the modal from a result after close. Do not spawn `ollama` or POST `/api/generate` from `swap`. Do not treat the console turn as a burner cell. Do not add a package, a public type, a vault column, a G-key, a channel, or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3548

## Console `status` prints the selected family and that family's stored options, not the old local name alone.

The status command in cmd mode reports the selected model from the old Ollama name. That command reports the model the new control stores, and the metadata that belongs to that model. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

`status` keeps the session lines. The model line becomes the selected target. A family line names `ollama`, `grok`, or `pool`. The lines under those two are the options stored for that family. A Grok selection does not print the Ollama id. A Pool selection prints `pool`.

`status` prints `family` and `model` from the console session. `model` is the Ollama id, `grok/` plus the Grok id, or `pool`. `getModelName` stays the Ollama id. `ollama load`, `ollama stop`, and the `select-model` OSC keep using that id.

Family `ollama` prints `think`. An empty think prints `off`. Any other stored word prints as stored.

Family `grok` prints `think`, `effort`, `send-effort`, `stream`, and `burner`. An empty think prints `off`. An empty effort prints blank. `send-effort`, `stream`, and `burner` print `true` or `false`. `temperature` prints only when a temperature is stored. `max-tokens` prints only when a cap is stored. The console turn still does not copy `think` onto the execute body. `burner` stays stored and does not open a hop.

Family `pool` prints `think` as `true` when the stored word is `true`, and `off` otherwise.

The other status lines stay: chat, mode, conversation, region, the SSH user, the message counts, and roux. There is no new channel and no new wire type.

The new method this build names is `CommandContext.writeModelStatus`. `ChatShell` implements it. No new package, public type, vault column, G-key, channel, or wire type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

`getModelName` stays the Ollama id for load, stop, and the OSC. The other status lines stay. The family, the target label, and that family's options are printed. An empty effort is a blank, and pool think is on only when the word is `true`. The row is the build.

**Truth.** `status` prints `family` and `model` from the console session. `model` is the Ollama id, `grok/` plus the Grok id, or `pool`. Each family prints only its own option lines. `getModelName` remains the Ollama id. `CommandContext.writeModelStatus` is the new method, and `ChatShell` implements it.

**Reason.** The old status line named the Ollama model even after the console stored another family. A Grok selection must not print the Ollama id, and a Pool selection prints `pool`. Temperature and max tokens print only when stored so an absent cap is not invented. The console turn still does not copy `think` onto the execute body, and stored `burner` does not open a hop. The session lines stay so chat, mode, conversation, region, the SSH user, the message counts, and roux are not dropped.

**Avoidances.** Do not print the Ollama id on a Grok or Pool selection. Do not print `temperature` or `max-tokens` when they are not stored. Do not print a blank effort as a level. Do not treat pool think as on unless the stored word is `true`. Do not copy console `think` onto the execute body. Do not open a burner hop from the stored burner word. Do not add a channel or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3549 PK-3550 PK-3551 PK-3552

## `select-model` with `grok`, `ollama`, or `pool` switches only that family, and a bare command still opens the modal.

The model modal works from this client's VT100. A standard VT100 console has no modal. `select-model` in cmd mode takes one argument, `grok`, `ollama`, or `pool`, and switches that family only, using the options last stored for it. A bare `select-model` still opens the modal. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The shortcut is the family word. It does not take a model id, a think word, or any other option. The stored options for that family stay. The command writes the model line. Any other argument is refused and does not open the modal.

A bare `select-model` keeps the OSC and the `Model Config` line. This client still opens the modal.

`select-model grok`, `select-model ollama`, and `select-model pool` set the console family to that word. The Ollama id, the Grok id, think, effort, send-effort, stream, temperature, max tokens, burner, and pool think stay as stored. The command writes `model` and the target label. It does not send the OSC.

Any other argument, including a second word, is refused with a usage line. The family stays. The modal stays closed.

The new method this build names is `CommandContext.selectConsoleFamily`. `ChatShell` implements it. No new package, public type, vault column, G-key, channel, or wire type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

The stored options stay, and a bad argument stays off the family. The modal path stays. The family switches and the model line is written. The three words and the nil cell stay. The row is the build.

**Truth.** Bare `select-model` still opens the modal through the OSC and the line `Model Config`. `select-model grok`, `select-model ollama`, and `select-model pool` switch the family, keep the stored options, write `model` and the target label, and do not send the OSC. Any other argument, including a second word, is a usage refusal.

**Reason.** A standard VT100 has no modal, so the family word is the switch. The word does not take a model id or a think word because the options last stored for that family stay. A bad argument must not change the family and must not open the modal.

**Avoidances.** Do not send the OSC when a family word is present. Do not change stored options on a family switch. Do not accept a model id, a think word, or a second word. Do not open the modal on a refused argument. Do not add a package, a public type, a vault column, a G-key, a channel, or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3553 PK-3554 PK-3555 PK-3556 PK-3557 PK-3558 PK-3559 PK-3560 PK-3561 PK-3562 PK-3563 PK-3564 PK-3565 PK-3566 PK-3567 PK-3568 PK-3569 PK-3570 PK-3571 PK-3572 PK-3573 PK-3574 PK-3575

## Ctrl+Left and Ctrl+Right move by words on the unsent console line, and plain arrows stay one character.

While a console line is still being edited, Left and Right move one character. Ctrl+Left and Ctrl+Right move by words, the way a bash line does. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The keys are the xterm sequences the terminal already sends, `ESC [ 1 ; 5 D` and `ESC [ 1 ; 5 C`. The shorter `ESC [ 5 D` and `ESC [ 5 C` do the same step. A word is a run of letters and digits. Forward lands at the end of the next word. Backward lands at the start of this word, or the previous word when the cursor is already on a start. Plain Left and Right stay one character. Up and Down stay history. A sealed paste ignores the keys.

`ChatShell.handleCompleteEscape` treats `ESC [ 1 ; 5 D` and `ESC [ 5 D` as one word backward, and `ESC [ 1 ; 5 C` and `ESC [ 5 C` as one word forward. The cursor stays inside the unsent line. The line redraws when the cursor moves.

A word character is a letter or a digit. Space, hyphen, and underscore are boundaries. The step matches bash readline `backward-word` and `forward-word`.

Plain Left, Right, Home, End, Delete, Up, and Down stay as they are. A sealed paste and an open paste ignore the word keys.

No new public method, package, public type, vault column, G-key, channel, or wire type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

Plain arrows stay on one character, and a sealed paste stays still. The terminal key map already sends the two sequences. The cursor moves and the line redraws. The bash word rule stays. The row is the build.

**Truth.** `ESC [ 1 ; 5 D` and `ESC [ 5 D` move one word backward. `ESC [ 1 ; 5 C` and `ESC [ 5 C` move one word forward. A word is letters and digits. Space, hyphen, and underscore are boundaries. The cursor stays inside the unsent line.

**Reason.** Character steps are slow on a long unsent line. The step matches bash readline `backward-word` and `forward-word`: forward lands at the end of the next word, and backward lands at the start of this word, or the previous word when the cursor is already on a start. The terminal already sends those xterm sequences. A sealed paste and an open paste ignore the word keys so a paste is not edited by them.

**Avoidances.** Do not change plain Left, Right, Home, End, Delete, Up, or Down. Do not let the cursor leave the unsent line. Do not honor the word keys during a sealed paste or an open paste. Do not add a public method, a package, a public type, a vault column, a G-key, a channel, or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3576

## Console thought

A pool thought on the VT100 rides the thinking channel and opens the pull-down. The thought stays off the terminal.

A VT100 pool turn was collecting the reply and dropping the thought. The thought now rides kind `t` on the stream channel that already carries that kind. The cell id is the nil id the console already uses. When console pool thinking is on, that chunk opens the pull-down and the tag is pool. The thought is not written on the terminal. The assistant reply still paints on the VT100. The end of that turn closes the block, so the next thought starts a new block. A canvas cell keeps its own gate. Ollama and Grok on the console stay as they are.

No new channel is allocated. The session writer stays bound to the channel that already exists. The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this difference waits until it is named. Nothing beside this routing moves. The cells already on the canvas stay as they are.

**Truth.** With console pool thinking on, a pool thought for the console's nil cell arrives as kind `t` on the existing stream channel, opens the pull-down, and is tagged pool. It is not written on the terminal. The assistant reply still paints on the VT100. The end of the turn closes the block, so the next thought starts a new block. A canvas cell keeps its own gate. Ollama and Grok on the console are unchanged. No new channel is allocated.

**Reason.** Pool thinking on the VT100 was not opening the pull-down. The thought was being dropped while the reply was collected. The thought has to ride the thinking channel that already exists, open the pull-down, and stay off the terminal.

**Avoidances.** Do not write the thought on the terminal. Do not allocate a new channel. Do not unbind the session writer. Do not paint the assistant reply anywhere but the VT100. Do not leave the block open after the turn, and do not append the next thought to the closed block. Do not move a canvas cell off its own gate. Do not change Ollama or Grok on the console. Do not write a Rust crate on this row.


**semantic-key:** PK-3577 PK-3578 PK-3579 PK-3580 PK-3581 PK-3582

## VT100 strip

The thinking pane is one component. A second instance sits on the VT100 pane. The stream difference stays the signal. The thought stays off the terminal.

The pull-down opens a block with a tag, appends text, and finishes. It does not read a frame, a cell, or a channel. The notebook keeps one instance and feeds it cell thoughts. Each VT100 panel keeps another instance and feeds it a nil-cell kind `t` when console pool thinking is on. The tag on that instance is pool. The notebook strip does not paint the nil cell. The two logs stay apart. The VT100 strip does not write the vault log. The assistant reply still paints on the VT100. The terminal bytes stay on channel T. Ollama and Grok on the console stay as they are. A canvas cell keeps its own gate.

One strip is seated on each terminal panel. The reply stays on channel T. Kind `t` on the nil cell is what the VT100 strip receives. Cell thoughts stay on the notebook strip. The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this strip waits until it is named. Nothing beside this strip moves. The cells already on the canvas stay as they are.

**Truth.** One pull-down component opens a tagged block, appends text, and finishes, without reading a frame, a cell, or a channel. The notebook instance receives cell thoughts. Each VT100 panel has its own instance, fed a nil-cell kind `t` when console pool thinking is on, tagged pool. The notebook strip does not paint the nil cell. The logs stay apart. The VT100 strip does not write the vault log. Terminal bytes stay on channel T. The assistant reply still paints on the VT100.

**Reason.** The thinking pane has to be one reusable component, with a second instance on the VT100, so a pool thought opens that strip and never lands on the terminal.

**Avoidances.** Do not make the pull-down read a frame, a cell, or a channel. Do not paint the nil cell on the notebook strip. Do not mix the two logs. Do not write the VT100 thought into the vault log. Do not move terminal bytes off channel T. Do not move the assistant reply off the VT100. Do not change Ollama or Grok on the console. Do not move a canvas cell off its own gate. Do not write a Rust crate on this row.


**semantic-key:** PK-3583 PK-3584 PK-3585 PK-3586 PK-3587 PK-3588 PK-3589 PK-3590 PK-3591 PK-3592 PK-3593 PK-3594 PK-3595 PK-3596 PK-3597 PK-3598 PK-3599 PK-3600

## VT100 top

The VT100 thinking pane sits on the top and slides down. Its bottom edge meets the top of the terminal. Drag that edge to set the height. The height and the closed state are stored apart from the notebook strip. The text stays off the vault log.

The VT100 strip sits on the north of the terminal and grows downward. The bottom edge of the strip meets the top of the terminal. Drag that edge to set the height. Dragging down grows the pane. The notebook strip stays on the bottom. Its grip stays on the north edge, and dragging up still grows that pane. The VT100 height and the closed flag are stored in `~/.casserole/aetheris-vt100-think.yaml`. Every VT100 strip shares that file. The thought text is not written there. The notebook file stays `aetheris-think.yaml` and the notebook log stays `aetheris-thinking.txt`. Close still hides the pane. A closed pane stays closed when the application opens again. `v` still folds the text for the session. Clear still wipes the text and leaves the pane up. A pool thought still opens the strip when console pool thinking is on. The assistant reply still paints on the VT100. Ollama and Grok on the console stay as they are.

The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this strip waits until it is named. Nothing beside this placement moves. The cells already on the canvas stay as they are. Use confirmed the top strip.

**Truth.** The VT100 strip grows down from the north. Its bottom edge meets the terminal, and dragging that edge down sets a taller pane. The notebook strip stays on the bottom, and dragging its north grip up still grows it. Height and the closed flag for every VT100 strip live in `~/.casserole/aetheris-vt100-think.yaml`, with no thought text in that file. The notebook file stays `aetheris-think.yaml` and the notebook log stays `aetheris-thinking.txt`. Close hides the pane and the closed state survives the next open. `v` folds for the session only. Clear wipes the text and leaves the pane up.

**Reason.** The VT100 thinking pane has to sit on top, slide down, and take its height from a drag edge against the terminal. That height and the closed state have to be its own, and the text has to stay out of the vault log.

**Avoidances.** Do not seat the VT100 strip on the bottom. Do not move the notebook strip off the bottom or reverse its grip. Do not store VT100 chrome in the notebook file. Do not write the thought text into `aetheris-vt100-think.yaml` or into the vault log. Do not give each VT100 strip a separate file. Do not drop the closed-on-next-open rule, the session fold, or Clear leaving the pane up. Do not change Ollama or Grok on the console. Do not write a Rust crate on this row.


**semantic-key:** PK-3601 PK-3602 PK-3603 PK-3604 PK-3605

## The folding panel

The control bar folds with a `v` toggle, called a folding panel, instead of a vertical split between that bar and the VT100 and Aetheris panes. It behaves the way the Rust Aetheris pane already folds. The control bar is not a drag.

A folding panel is the `v` toggle. Open paints `v`. Folded paints `^`. The panel sits between the control bar and the VT100 and Aetheris panes. The vertical control split goes away. A New Term split stays a split.

The bar starts open. Connect folds it. Disconnect opens it. A resize while the link is up folds it. A resize while the link is down leaves the mark. The mark toggles either way. The first span is stored and does not fold. Connecting does not drop the bar or the link. The choice is not saved.

Open, the menu sits at the top. File, Tools, and Help stay in that order. Show Thinking stays. Token Calc stays off. The `v` mark sits on the control fields. The SSH and Aetheris tabs sit on the next row.

Folded, one row holds the `^` mark, the link word, SSH, Aetheris, a vertical divider, then File, Tools, and Help. The same menus move. They are not painted twice. The tab strip hides while the bar is folded and returns when the bar is open. Connected paints the short chip `Connected`. Another link word uses the west line with an empty host. The south strip stays the long line.

A connect on the focused session folds the bar. A pane switched to that is already connected folds the bar. The pane already showing keeps the mark until the next beat. A disconnected pane switched to opens the bar. An Aetheris tab stays enabled only for a focused AMWP session. A folded SSH press still honors the restore mark.

There is no new package, no new public type, no new global key, no new vault column, no new channel, and no new wire message type. The folding panel stays private to its package. Nothing beside this panel moves. The cells already on the canvas stay as they are. The Rust client already paints this bar. This row writes no Rust crate. The crate change for this difference waits until it is named. Terminal splits stay. SSH and Aetheris stay. File, Tools, and Help are seated once.

**Truth.** Open shows `v` and folded shows `^`. The vertical control split is gone. A New Term split remains a split. The bar starts open, is not saved, and toggles either way. Connect, a connect on the focused session, a switch onto an already connected pane, and a resize while the link is up all fold it. Disconnect, and a switch onto a disconnected pane, open it. A resize while the link is down leaves the mark. The pane already showing keeps its mark until the next beat. The first span is stored and does not fold. Folded, one row is `^`, the link word, SSH, Aetheris, a vertical divider, then File, Tools, and Help, painted once. `Connected` is the short chip. Any other link word uses the west line with an empty host. The south strip stays the long line. The tab strip hides while folded. Token Calc stays off. Show Thinking stays. An Aetheris tab is enabled only for a focused AMWP session. A folded SSH press still honors the restore mark.

**Reason.** The control menu at the top was collapsing with a split pane. A folding panel matches the Rust Aetheris pane and keeps the bar a toggle rather than a drag.

**Avoidances.** Do not keep the vertical control split. Do not turn a New Term split into a fold. Do not make the control bar a drag. Do not save the fold. Do not fold the first span. Do not paint File, Tools, or Help twice. Do not drop Show Thinking. Do not turn Token Calc on. Do not enable an Aetheris tab outside a focused AMWP session. Do not ignore the restore mark on a folded SSH press. Do not change the south strip from the long line. Do not write a Rust crate on this row. Do not add a package, a public type, a global key, a vault column, a channel, or a wire message type.


**semantic-key:** PK-3606 PK-3607 PK-3608 PK-3609 PK-3610 PK-3611 PK-3612 PK-3613 PK-3614 PK-3615 PK-3616 PK-3617

## The charcoal menu

The background of every context menu uses the dark charcoal of the theme. The green foreground pops on that charcoal.

The charcoal is the panel background. The green is the menu foreground. Both already live on the theme. There is no new color key.

The menus are the cell import popup, the terminal context popup, and the File, Tools, and Help dropdowns. The Ollama combo stays a combo and keeps the field colors.

A popup is painted opaque. The fill is the panel background. The words are the menu foreground. The border is the button background. Each plain item is opaque on those colors. A nested menu is painted the same way. The basic popup painter and the basic item painter carry the colors on Windows. Those colors are installed before the window is built.

The cell popup is painted before it is shown. The terminal popup is painted before it returns. The terminal widget keeps the theme. File, Tools, and Help are painted after their items are added.

The menu words stay. The import tip stays. No settings file is edited.

There is no new package, no new public type, no new global key, no new vault column, no new channel, and no new wire message type. Nothing beside these menus moves. The send set stays as it is. The Java client carries this. The Rust client stays.

**Truth.** The cell import popup, the terminal context popup, and the File, Tools, and Help dropdowns are opaque. Their fill is the theme panel background, their words are the theme menu foreground, and their border is the button background. Nested menus match. On Windows the basic popup painter and the basic item painter carry those colors, installed before the window is built. The cell popup is painted before it is shown, and the terminal popup before it returns. The Ollama combo stays a combo on the field colors. No new color key is added.

**Reason.** Context menus were not wearing the dark charcoal of the theme, so the green foreground could not pop against it.

**Avoidances.** Do not add a color key. Do not restyle the Ollama combo into a menu. Do not change the menu words or the import tip. Do not edit a settings file. Do not paint File, Tools, or Help before their items exist. Do not change the Rust client. Do not add a package, a public type, a global key, a vault column, a channel, or a wire message type.


**semantic-key:** PK-3618 PK-3619 PK-3620 PK-3621 PK-3622 PK-3623

## The charcoal title

The window title bar is painted a very dark charcoal so it matches the rest of the window.

The very dark charcoal is the frame background. The title words are the panel foreground. Both already live on the theme. There is no new color key. The native title bar stays. Minimize, maximize, and close stay.

The caption is painted when the window opens and when the window state changes. On Windows the caption and the border take the frame background. The title text takes the panel foreground. A dark caption mode is set so the bar accepts that paint. Any other system skips the paint. A missing handle skips the paint. A failure is caught and the window still opens.

The methods stay private on the shell window. There is no new public type and no new library. The platform linker paints the caption.

The content pane stays on the frame background. The import path stays. No settings file is edited.

There is no new package, no new global key, no new vault column, no new channel, and no new wire message type. Nothing beside this caption moves. The send set stays as it is. The Java client carries this. The Rust client stays.

**Truth.** On Windows, when the window opens and when its state changes, the native caption and border take the theme frame background and the title text takes the panel foreground, with dark caption mode set. Another system, a missing handle, or a failed paint leaves the window open and skips or survives that paint. Minimize, maximize, and close stay. There is no new color key, no new public type, and no new library. The content pane stays on the frame background.

**Reason.** The title bar has to wear the same very dark charcoal as the window theme.

**Avoidances.** Do not replace the native title bar. Do not remove minimize, maximize, or close. Do not add a color key or a library. Do not paint the caption on a system other than Windows. Do not block the window from opening when the handle is missing or the paint fails. Do not move the content pane off the frame background. Do not change the import path. Do not edit a settings file. Do not change the Rust client. Do not add a package, a public type, a global key, a vault column, a channel, or a wire message type.


**semantic-key:** PK-3624 PK-3625 PK-3626

## The Apple scroll

Every scroll bar in the Java client looks and behaves like the overlay scroll bars in the Rust client. One reusable scroll bar carries that behavior, built once and used again.

The Rust bars float on the content. They take no gutter. They stay clear while the pointer is off the pane. On the pane they are a thin pill. On the bar they widen. A click off the handle centers the handle. A drag follows the pointer. The wheel stays.

`AppleScrollBar.install` arms a scroll pane. The shared scroll-pane call uses it. The canvas, the member row, the cell prompt, the cell reply, the inspector, the thinking strip, the mood draft, the paste editor, and the help text all pass through that call. Another Java application calls `install` on its own pane.

The hit strip is 10 pixels. The resting width is 2. The handle is at least 12. The corner radius is 6. The fade is 0.2 seconds, cubic out. The track is the Rust plate `#0d0d0d`. The handle is gray 215, gray 245 while the pointer is on the handle, and white while the button is down. The track opacity is 0.4 on the pane and 0.7 on the bar. The handle opacity is 0.6 on the pane and 1 on the bar. A pane that opens under the pointer fades in.

The bar takes no layout gutter. The member row stays the height of its cards. The existing height function still adds the bar's preferred height, and that height is zero. The policies stay. A bar that is not needed stays hidden. A press does not take the caret. The bar is not a tab stop.

The terminal view paints its own cells. It has no toolkit bar. The light-gray arrow buttons leave the shared scroll-pane call. There is no new package, no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside these bars moves. The send set stays as it is. The Java client carries this. The Rust client stays.

**Truth.** `AppleScrollBar.install` arms a scroll pane, and the canvas, member row, cell prompt, cell reply, inspector, thinking strip, mood draft, paste editor, and help text all use that call. Another Java application can call `install` on its own pane. The hit strip is 10 pixels, the resting width is 2, the handle is at least 12, the corner radius is 6, and the fade is 0.2 seconds, cubic out. The track is `#0d0d0d`. The handle is gray 215, gray 245 under the pointer, and white while the button is down. Track opacity is 0.4 on the pane and 0.7 on the bar. Handle opacity is 0.6 on the pane and 1 on the bar. A click off the handle centers it. A drag follows the pointer. The bar takes no gutter, its preferred height is zero, and a pane that opens under the pointer fades in. The wheel and the policies stay. A press does not take the caret. The bar is not a tab stop. The terminal view has no toolkit bar.

**Reason.** Every Java scroll bar has to match the Rust overlay bars, and that bar has to be built once so another Java application can use it.

**Avoidances.** Do not take a layout gutter. Do not change the wheel or the policies. Do not show a bar that is not needed. Do not take the caret on a press. Do not make the bar a tab stop. Do not put a toolkit bar on the terminal view. Do not leave the light-gray arrow buttons on the shared scroll-pane path. Do not change the member-row height away from the height of its cards. Do not add a color key, a library, a package, a global key, a vault column, a channel, or a wire message type. Do not edit a settings file. Do not change the Rust client.


**semantic-key:** PK-3627 PK-3628 PK-3629 PK-3630 PK-3631 PK-3632 PK-3633 PK-3634 PK-3635 PK-3636

## The hot strip

The scroll bar pops out when the pointer is on the hot strip, the same strip the Rust bar senses.

The viewport is added first. It paints last and it takes the pointer. The bar sat under that view, so the hot strip never saw the hover and the pill never showed. Wheel scroll still moved the content.

The layout raises a visible bar above the view. The vertical bar sits above the horizontal bar where they meet. The 10 pixel strip receives the pointer. The bar pops out. The pill paints on the content. A bar that is not needed is cleared to an empty box so it does not keep the strip. Hover on the pane uses the mouse event. A pane that opens under the pointer still reads the pointer.

The widths, the colors, the opacities, and the fade stay the overlay measures: resting width 2, handle at least 12, corner radius 6, fade 0.2 seconds cubic out, track `#0d0d0d`, handle gray 215, gray 245 on the handle, white while the button is down, track opacity 0.4 on the pane and 0.7 on the bar, handle opacity 0.6 on the pane and 1 on the bar. The preferred thickness stays zero. The member row stays the height of its cards. The wheel stays. The policies stay. The terminal view stays. It has no toolkit bar. A press does not take the caret. The bar is not a tab stop.

There is no new package, no new public type, and no new public method. `AppleScrollBar` stays. The method stays `install`. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this hit order moves. The send set stays as it is. The Java client carries this. The Rust client stays. Use confirmed the hot strip.

**Truth.** A visible bar is raised above the view, and the vertical bar sits above the horizontal bar where they meet. The 10 pixel strip receives the pointer, the bar pops out, and the pill paints on the content. A bar that is not needed is an empty box and does not keep the strip. Hover uses the mouse event, and a pane that opens under the pointer still reads the pointer. The overlay measures, the zero preferred thickness, the wheel, and the policies stay.

**Reason.** The bar was under the viewport, so the hot strip never saw the pointer and the pill never showed. The bar has to pop out on that strip the way the Rust bar does. The wheel was already moving the content.

**Avoidances.** Do not leave the bar under the view. Do not let an unneeded bar keep the strip. Do not change the 10 pixel strip. Do not change the overlay widths, colors, opacities, or fade. Do not give the bar a preferred thickness other than zero. Do not change the wheel, the policies, or the terminal view. Do not take the caret on a press. Do not make the bar a tab stop. Do not add a public method. Do not change the Rust client.


**semantic-key:** PK-3637 PK-3638 PK-3639

## The wider bar

Once the scroll bar has popped out, it is 5 pixels wider.

The popped-out bar was 10 pixels. It is 15. The thin pill stays 2. The strip that holds the popped-out bar is that same width, so the strip is 15. The colors, the opacities, the fade, and the corner radius stay: track `#0d0d0d`, handle gray 215, gray 245 on the handle, white while the button is down, track opacity 0.4 on the pane and 0.7 on the bar, handle opacity 0.6 on the pane and 1 on the bar, fade 0.2 seconds cubic out, corner radius 6, handle at least 12. The preferred thickness stays zero. The member row stays the height of its cards. The wheel stays. The policies stay. The terminal view stays. It has no toolkit bar. A press does not take the caret. The bar is not a tab stop.

There is no new package, no new public type, and no new public method. `AppleScrollBar` stays. The method stays `install`. `BAR_PX` stays the measure. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this width moves. The send set stays as it is. The Java client carries this. The Rust client stays.

**Truth.** The popped-out bar and the strip that holds it are 15 pixels. The thin pill stays 2. `BAR_PX` is that measure. Colors, opacities, the 0.2 second cubic-out fade, the corner radius of 6, and the minimum handle of 12 stay. Preferred thickness stays zero. The wheel, the policies, and the terminal view stay.

**Reason.** The popped-out scroll bar was too narrow. It has to grow by 5 pixels, from 10 to 15.

**Avoidances.** Do not leave the popped-out bar at 10. Do not change the thin pill from 2. Do not let the strip and the popped-out bar disagree. Do not change the colors, opacities, fade, or corner radius. Do not give the bar a preferred thickness other than zero. Do not change the wheel, the policies, or the terminal view. Do not take the caret on a press. Do not make the bar a tab stop. Do not add a public method. Do not change the Rust client.


**semantic-key:** PK-3640

## The VT100 bar

Every pane in the VT100 tab uses the overlay scroll bar. The old terminal scrollbars are gone.

The VT100 terminal lays its own scroll strip. That strip was the old bar. Every new VT100 pane builds a widget, and that widget arms the strip with the overlay bar. A fresh session builds a fresh widget. The thinking strip on this tab already uses the overlay bar. It stays. The mood glass stays on its own bar. It is not a VT100 pane.

The strip is 15 pixels, the popped-out width. The terminal layout sizes the strip from the preferred width, so that preferred width is 15. The scroll panes keep a preferred thickness of zero. The thin pill stays 2. The colors, the opacities, the fade, and the corner radius stay: track `#0d0d0d`, handle gray 215, gray 245 on the handle, white while the button is down, track opacity 0.4 on the pane and 0.7 on the bar, handle opacity 0.6 on the pane and 1 on the bar, fade 0.2 seconds cubic out, corner radius 6. The strip rests on the plate. The pointer on the pane shows the thin pill. The pointer on the strip pops the bar out. The wheel stays. The policies stay. A press does not take the caret. The bar is not a tab stop. The old track painted find ticks. The overlay bar does not paint those ticks. The find highlight in the terminal stays.

There is no new package, no new public type, and no new public method. `AppleScrollBar` stays. `install` stays the pane arm. The package method is `armStrip`. `BAR_PX` stays the measure. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this strip moves. The send set stays as it is. The Java client carries this. The Rust client stays.

**Truth.** Every new VT100 pane arms its scroll strip with the overlay bar through `armStrip`, and a fresh session builds a fresh widget. The strip's preferred width is 15. Scroll panes keep a preferred thickness of zero. The thin pill stays 2. The pointer on the pane shows that pill, and the pointer on the strip pops the bar out. The overlay bar does not paint find ticks. The find highlight in the terminal stays. The thinking strip on this tab already uses the overlay bar and stays. The mood glass is not a VT100 pane and keeps its own bar.

**Reason.** The VT100 panes still had the old scrollbars. Every pane on that tab has to use the overlay bar.

**Avoidances.** Do not leave the old terminal bar in place. Do not reuse one widget across sessions. Do not move the thinking strip off the overlay bar. Do not treat the mood glass as a VT100 pane. Do not size the strip from anything but the 15 pixel preferred width. Do not paint find ticks on the overlay bar. Do not drop the find highlight in the terminal. Do not change the wheel or the policies. Do not take the caret on a press. Do not make the bar a tab stop. Do not add a public method. Do not change the Rust client.


**semantic-key:** PK-3641 PK-3642 PK-3643 PK-3644 PK-3645 PK-3646

## The dark bevel

The bright lip leaves. The bevels wear a dark shadow. The cell panes go black. The ink stays.

The bright lip sits on the button bevel, on Yes and No, on the one-pixel line around P and C and the thinking clear mark, on the grab-bar outline, and on the split bevel the look and feel puts back. The lip is `#242424`. The shadow is black. The painted surface stays. The word stays.

The frame, the split, and the status go to `#0c0c0c` when they are still the old default. The panel goes to `#121212` when it is still the old default. A ground already changed stays. The button face stays `#3c3c3c`. A form field stays `#1a1a1a`. The cell panes go black. The round field stays a round of 8. The green line and the gray line stay. The cell split stays clear. The grab bar stays. Its outline is the black shadow.

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The cell stays a round of 6. The overlay scroll bar stays.

The Rust client stays. The straight bevel, the field round, and the cell round stay the earlier reading.

There is no new package, no new public type, and no new public method. The grounds are seated in private. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside these grounds moves. The send set stays as it is. The Java client carries this.

**Truth.** The bevel highlight moves to `#242424` and the shadow is black, on the button bevel, on Yes and No, on the one-pixel line around P, C, and the thinking clear mark, on the grab-bar outline, and on the split bevel the look and feel puts back. Where they are still the old default, the frame, the split, and the status become `#0c0c0c`, and the panel becomes `#121212`. A ground already changed stays. The button face stays `#3c3c3c`. A form field stays `#1a1a1a`. The cell panes go black. The round field stays 8, the cell stays a round of 6, and the grab-bar outline is the black shadow. Ink stays: green prompt, yellow reply text, cyan think ink, white letters on P and C, menu green, and a white caret on an open pane. Selection, sapphire, and the two find painters stay.

**Reason.** The bright lip was too light for the window. A dark shadow and black cell panes keep the ink, the neon lines, and the selection.

**Avoidances.** Do not leave the bright lip in place. Do not repaint a ground the user already changed. Do not change the button face from `#3c3c3c` or a form field from `#1a1a1a`. Do not change font colors, selection highlights, the sapphire, the two find painters, the prompt green, the reply yellow, the think cyan, the white letters, the menu green, or the white caret. Do not drop the round field of 8, the cell round of 6, or the overlay bar. Do not fill the cell split. Do not change the Rust client. Do not add a color key, a library, a public method, a package, a global key, a vault column, a channel, or a wire message type. Do not edit a settings file.


**semantic-key:** PK-3647 PK-3648 PK-3649 PK-3650 PK-3651

## The quiet bevel

The bevel highlight drops under the panel and stays over the black. The ink stays.

The bevel highlight was `#242424`. That lip sits over the panel. The panel ground is `#121212`. The black ground is `#000000`. The lip moves to `#0a0a0a`. Ten is darker than the panel and lighter than the black. The shadow stays black. The button face stays. The cell panes stay black. The grab bar keeps its seat. Its fill is that grey. Its outline stays black.

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The green line and the gray line stay. The cell split stays clear. The round field stays a round of 8. The cell stays a round of 6. The overlay scroll bar stays. Yes and No stay. P and C stay. The thinking clear mark stays.

The Rust client stays. The dark grounds and the straight bevel stay the earlier reading.

There is no new package, no new public type, and no new public method. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this lip moves. The send set stays as it is. The Java client carries this.

**Truth.** The bevel highlight is `#0a0a0a`. That value, ten, is darker than the panel at `#121212` and lighter than black at `#000000`. The shadow stays black. The grab bar keeps its seat, fills with that grey, and keeps a black outline. Cell panes stay black. Button faces stay. Yes, No, P, C, and the thinking clear mark stay. Ink, selection, sapphire, both find painters, the green and gray lines, the clear cell split, the round field of 8, the cell round of 6, and the overlay bar stay.

**Reason.** The highlight at `#242424` sat too bright over the panel. The lip has to fall under the panel grey and stay above the black, without touching the ink.

**Avoidances.** Do not leave the lip at `#242424`. Do not make the lip as dark as `#000000` or as light as `#121212`. Do not change the shadow from black. Do not move the grab bar or turn its outline off black. Do not lighten the cell panes. Do not change the ink, the selection, the sapphire, the find painters, Yes and No, P and C, or the thinking clear mark. Do not change the Rust client. Do not add a color key or a library. Do not edit a settings file.


**semantic-key:** PK-3652

## The quiet edge

The light Ocean edges take the quiet grey. The ink stays.

The grey is `#0a0a0a`. Ten is darker than the panel at `#121212` and lighter than the black at `#000000`. It is the lip the bevel already wears. The selected tab face is the panel charcoal. An unselected tab, the tab strip, and the content frame are the quiet grey. The tab shadow stays black, so the frame still reads. A scroll edge, a combo frame, a spinner edge, the menu-bar line, an open menu frame, a check box, a find field, and a dialog frame use that grey. The file chooser frame uses it. The button face stays. A form field stays. The cell rounds stay.

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The green line and the gray line stay. The cell split stays clear. The round field stays a round of 8. The cell stays a round of 6. The overlay scroll bar stays. Yes and No stay. P and C stay. The thinking clear mark stays.

The Rust client stays. The quiet lip and the dark grounds stay the earlier reading.

There is no new package, no new public type, and no new public method. The Ocean edges are installed in private, including the quiet fill. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside these edges moves. The send set stays as it is. The Java client carries this.

**Truth.** Ocean edges that were light are `#0a0a0a`: an unselected tab, the tab strip, the content frame, a scroll edge, a combo frame, a spinner edge, the menu-bar line, an open menu frame, a check box, a find field, a dialog frame, and the file chooser frame. The selected tab face is the panel charcoal. The tab shadow stays black. The button face and a form field stay. Cell rounds stay a field of 8 and a cell of 6. Ink, selection, sapphire, both find painters, Yes and No, P and C, and the thinking clear mark stay.

**Reason.** The light Ocean edges were still brighter than the quiet bevel. They have to wear the same grey, `#0a0a0a`, so the frame still reads on a black tab shadow and the ink is untouched.

**Avoidances.** Do not leave a light Ocean edge in place. Do not paint the selected tab with the quiet grey. Do not turn the tab shadow off black. Do not change the button face or a form field. Do not change the cell rounds. Do not change the ink, the selection, the sapphire, or the find painters. Do not change the Rust client. Do not add a color key, a library, a public method, a package, a global key, a vault column, a channel, or a wire message type. Do not edit a settings file.


**semantic-key:** PK-3653 PK-3654

## The help seat

Help sits on the main menu bar.

The order stays File, Tools, Token Calc, and Help. Token Calc is a menu item. That item was taking the remaining width, so Help sat past the edge. The item maximum size is its preferred size. Help stays on the bar. `Help...` and `About Aetheris...` stay under Help. Show Thinking stays under Tools. Token Calc still opens the token window.

The folded row stays. The open divider stays. The words stay SSH and Aetheris.

There is no new package, no new public type, and no new public method. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this seat moves. The send set stays as it is. The Java client carries this. The Rust client stays.

**Truth.** The main menu order is File, Tools, Token Calc, and Help. Token Calc is a menu item whose maximum size is its preferred size, so Help stays on the bar. `Help...` and `About Aetheris...` stay under Help. Show Thinking stays under Tools. Token Calc still opens the token window. The folded row, the open divider, and the words SSH and Aetheris stay.

**Reason.** Help had slipped past the edge of the main menu bar because Token Calc was taking the remaining width. Help has to sit on that bar again.

**Avoidances.** Do not change the order from File, Tools, Token Calc, and Help. Do not let Token Calc grow past its preferred size. Do not move `Help...` or `About Aetheris...` out from under Help. Do not move Show Thinking out from under Tools. Do not stop Token Calc from opening the token window. Do not change the folded row, the open divider, or the words SSH and Aetheris. Do not change the Rust client. Do not add a package, a public type, a public method, a global key, a vault column, a channel, a wire message type, a color key, or a library. Do not edit a settings file.


**semantic-key:** PK-3655

## The button word

OK buttons in modals, including the Token Calc modal, and the `v` fold toggle, show their specified text.

The Token Calc OK stays `OK`. The pin OK stays `OK`. The fold mark stays `v` while open and `^` while folded. The thinking fold stays `v` while open and `^` while folded. The tool fold and the group fold stay `v` and `^`. The connection-bar mark stays `v` and `^`. A label of one or two characters takes two ems after the border is on. A longer label keeps the short-fit width. Approve still grows by three ems after that fit. Yes and No stay on their own floor. The height stays the short fit when the word fits, and rises when the short fit is under the word. The minimum size is that floor. The short fit no longer replaces the label with an ellipsis. The label stays the specified text.

The floor is applied again after the mark border is on, and again when the thinking fold changes its letter. It runs for the notebook, for the thinking strip, and for the folding panel.

There is no new package, no new public type, and no new public method. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, no settings-file edit, and no dependency-manifest edit. Nothing beside this floor moves. The send set stays as it is. The Java client carries this. The Rust client stays. This row is not a separate feature. The conversation transfer stays closed.

**Truth.** Modal OK buttons, including Token Calc, show `OK`. The pin OK shows `OK`. Open fold marks show `v` and folded marks show `^`, on the thinking fold, the tool fold, the group fold, and the connection bar. A label of one or two characters takes two ems after the border is on. A longer label keeps the short-fit width. Approve still grows by three ems after that fit. Yes and No stay on their own floor. Height stays the short fit when the word fits, and rises when the short fit is under the word. The minimum size is that floor. No label is replaced by an ellipsis. The floor runs again after the mark border and when the thinking fold changes its letter.

**Reason.** OK buttons and the fold toggle were not showing the text specified for them. The short fit was replacing that text with an ellipsis.

**Avoidances.** Do not change `OK`, `v`, or `^` to any other label. Do not give a one-character or two-character label any floor but two ems after the border is on. Do not put a longer label on that two-em floor. Do not take the three extra ems off Approve. Do not move Yes and No off their own floor. Do not let the short fit replace a label with an ellipsis. Do not leave the height under the word. Do not skip the floor after the border or after the thinking fold changes letter. Do not change the Rust client. Do not reopen the conversation transfer. Do not add a package, a public type, a public method, a global key, a vault column, a channel, a wire message type, a color key, or a library. Do not edit a settings file or a dependency manifest.


**semantic-key:** PK-3656 PK-3657 PK-3658 PK-3659 PK-3660 PK-3661 PK-3662 PK-3663 PK-3664

## The dark line

The line under the control bar, and the edges of the divider, take the dark split ground.

The open rule and the folded rule both take the split background. The divider border is empty. `DarkDivider` fills that ground and does not stroke a second edge. The arrows stay. The divider stays 11 pixels. The chip words stay.

No new private method. The Swing client changes. The Rust client stays.

**Truth.** The control-bar rule and the divider edges are the split background. The divider border is empty, so no second stroke is painted. The divider remains 11 pixels.

**Reason.** Those edges were still a pale line on a dark window, a highlight left over from a light bevel.

**Avoidances.** Do not stroke a second edge on the divider. Do not move the arrows, the 11 pixel divider, or the chip words. Do not change the Rust client. Do not add a package, a public type, a public method, a private method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-3665 PK-3666 PK-3667

## The title bar

The Swing window title bar matches the Rust window title bar.

Immersive dark mode stays on. Caption, border, and title text take the system default. That default is the dark caption the Rust window already leaves in place. The caption words stay. No hex is painted onto the caption.

`paintCaption` and `applyCaption` write the system default. `colorRef` stays. No new private method. The Swing client changes. The Rust client stays.

**Truth.** Caption, border, and title text are the system default under immersive dark mode. The caption words stay. The caption is not painted from a hex.

**Reason.** The Swing caption was a painted color, so the two windows did not share one dark title bar.

**Avoidances.** Do not paint a hex on the caption. Do not turn immersive dark mode off. Do not change the caption words. Do not change the Rust client. Do not add a package, a public type, a public method, a private method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-3668

## The rust marks

The P and C buttons match the Rust color and style on the canvas and on the VT100 tab.

P is sapphire while the pin is off and green while the pin is on. C is sapphire. Both wear a one-pixel white stroke and a corner radius of 2. The letter stays white. P is at least 22 by 22. C is at least 22 by 16. The hover emerald stays. T stays. The cell-state P stays.

The private painter is `paintRustPin`. `rustMarkEdge`, `seatRustBox`, and `ThinkStrip.paintRustClear` stay package-private. `paintMarkChoice` stays. The Swing client changes. The Rust client stays.

**Truth.** P and C use the Rust mark: sapphire, or green for P while the pin is on, a one-pixel white stroke, corner radius 2, and a white letter. P is at least 22 by 22. C is at least 22 by 16. The same face is on the canvas and on the VT100 tab.

**Reason.** The Swing P and C were a different face from the Rust marks, so the pin and the clear did not read as the same controls across the two clients.

**Avoidances.** Do not restyle T. Do not restyle the cell-state P. Do not drop the hover emerald. Do not shrink P under 22 by 22 or C under 22 by 16. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-3669 PK-3670 PK-3671

## The control flow

The master control bar flows the way the Rust control bar flows, and the buttons sit on their own row under the host fields.

The host row is Mode, Host, Port, Username, Password, Remember password, and Font size. The button row is Connect, Disconnect, Reconnect, Clear Terminal, Send file..., New Term, and Close Pane. The rows flow left and wrap. The fold mark stays on the west. The Mode combo stays.

The private class is `FlowRow`. The private helper is `flowLabel`. The Swing client changes. The Rust client stays.

**Truth.** Host fields occupy the first wrapping row. Connect, Disconnect, Reconnect, Clear Terminal, Send file..., New Term, and Close Pane occupy the next wrapping row. The fold mark stays west. The Mode combo stays.

**Reason.** One packed row clipped the host fields against the buttons. The Rust bar already kept those as two flowing rows.

**Avoidances.** Do not put the buttons back on the host row. Do not move the fold mark off the west. Do not remove the Mode combo. Do not rename Send file... on this rule. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-3672 PK-3673

## The default size

When the Swing client has no usable application font size, it uses 20.

A stored size stays. A missing key is 20. An unreadable key is 20. The range stays 8 through 36. The Rust `FONT_DEFAULT` stays 14.

The Swing client changes. The Rust client stays.

**Truth.** The Swing missing or unreadable application font size is 20. A stored size wins. The clamp remains 8 through 36. The Rust default remains 14.

**Reason.** The Swing start size was still the old small default, so a first open did not match the face the window is built around. A stored choice must not be replaced by that start size.

**Avoidances.** Do not overwrite a stored size with 20. Do not change the Rust default of 14. Do not widen or shrink the 8 through 36 range. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-3674

## The button face

Buttons that have not already been given a special face take the Rust tab face, and their text color holds through focus, hover, press, and disable.

Idle fill is the tab gray with no stroke. Hover takes the hover fill and a one-pixel hover edge. Press fills #4a4a4a. The word stays the button foreground, including while the hover word is emerald. A disabled word is that same foreground at half alpha. The corner radius is 12. Plate tabs, folded plate words, the Rust marks, the mark letters, choice faces, arrows, and the hidden zero-size bar buttons stay on the faces they already have.

The private class is `PlateFaceUi`. The private helpers are `seatPlateFace` and `plateSkip`. The Swing client changes. The Rust client stays.

**Truth.** An unconverted button is tab gray with no idle stroke, a one-pixel edge on hover, #4a4a4a while pressed, and corner radius 12. The foreground, including emerald hover text, does not change with focus, hover, press, or disable, except that a disabled word is that foreground at half alpha.

**Reason.** Unconverted buttons were still on a light control face, and a state change was recoloring the word. The tab face keeps the word stable and matches the Rust chrome.

**Avoidances.** Do not paint this face onto plate tabs, folded plate words, Rust marks, mark letters, choice faces, arrows, or the hidden zero-size bar buttons. Do not swap the foreground for a different color on focus, hover, press, or disable. Do not drop the emerald hover word. Do not use a corner other than 12. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-3675 PK-3676 PK-3677 PK-3678 PK-3679

## The opening frame

The first start centers the shell at a usable size.

A saved rectangle that meets a screen stays. A missing origin, and a saved rectangle that misses every screen, seats 1200 by 760, capped to the usable screen, and centered. The floor is 640 by 480 when the screen allows. The size never exceeds the usable screen. A later resize still saves.

The private helpers are `seatOpeningFrame` and `usableScreen`. The Swing client changes. The Rust client stays.

**Truth.** A saved on-screen rectangle is restored. Otherwise the frame is 1200 by 760, capped to the usable screen, centered, and never below 640 by 480 when the screen can hold that floor. A later resize is saved.

**Reason.** A missing origin, or a rectangle parked on a screen that is gone, was opening the shell off the glass or at a size the display could not hold.

**Avoidances.** Do not replace a saved rectangle that still meets a screen. Do not exceed the usable screen. Do not drop the floor below 640 by 480 when the screen allows it. Do not stop saving a later resize. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-3680

## The visible letters

P, C, and T show their letters at the Rust pin size.

The letter is painted after the fill. The pad is 8 by 2, applied on both sides, plus a further 8 by 4, so the box adds 24 by 8. The floor is 22 by 22. The application point size stays. P is green while the pin is on and sapphire while it is off. C and T stay sapphire. The stroke is one white pixel and the corner radius is 2. The cell-state letters I, E, P, and O stay.

The private class is `RustLetterFace`. The private painter is `paintRustToken`. `seatRustLetter` stays package-private. The Swing client changes. The Rust client stays.

**Truth.** P, C, and T paint the letter after the fill, in a box that adds 24 by 8 from that pad and is at least 22 by 22. P is green when the pin is on and sapphire when it is off. C and T stay sapphire. The stroke is one white pixel. The corner radius is 2. The application point size stays.

**Reason.** The letters were being clipped inside a box that was sized before the glyph, so P, C, and T could show a face with no readable letter.

**Avoidances.** Do not paint the letter under the fill. Do not restyle the cell-state I, E, P, and O. Do not shrink the floor under 22 by 22. Do not change the application point size. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-3681 PK-3682 PK-3683

## The menu tab

When the pointer is over a main menu item, the background becomes a lighter grey tab.

The tab is gray 70 with corner 6. Idle stays clear. The same bar is painted open, collapsed, and shifted. Token Calc takes the same tab. The menu words stay the menu green.

`applyMenuBar` is a new public method on the existing theme. The Swing client changes. The Rust client stays.

**Truth.** Hover on a main menu item paints gray 70 with corner radius 6, in the open bar, the collapsed bar, and the shifted bar, and on Token Calc. Idle is clear. Menu words stay menu green.

**Reason.** The other client already answered a menu hover with that tab. The Swing bar stayed flat, so the item under the pointer did not read as a tab.

**Avoidances.** Do not paint the tab while the pointer is idle. Do not cover only one of the three bar shapes. Do not leave Token Calc off the tab. Do not change the menu green. Do not add a package, a public type other than the method on the existing theme, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not change the Rust client. Do not edit the Rust manifest.


**semantic-key:** PK-3684 PK-3685 PK-3686 PK-3687

## The button pad

The bottoms of the control-bar buttons clear the joint between the panes by at least 2 pixels.

The connection panel inside the folding panel takes 4 pixels of empty border on the bottom. The button faces stay.

The 4 pixel bottom pad is the clearance this rule names. The Swing client changes. The Rust client stays.

**Truth.** The folding panel's connection panel has a 4 pixel empty bottom border, so the button bottoms clear the pane joint by at least 2 pixels. Button faces do not change.

**Reason.** The button bar was clipped by the joint under the folding panel. Two pixels of clearance needed a 4 pixel pad.

**Avoidances.** Do not use a pad that leaves less than 2 pixels of clearance. Do not restyle the button faces. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-3688 PK-3689

## The about box shows every line

The About box shows every line, including the copyright line. Line wrap is off. The row count is the line count. The column count follows the widest line.

`showAbout` turns line wrap off. `ABOUT_TEXT` splits on the newline. The row count is that line count. There are seven lines. Font metrics measure each line. The column count is the widest line in `m` widths, rounded up, plus one column. The copyright line stays one line. The words stay, including DarkJediTerm. The Rust `ABOUT_BODY` stays.

The edit is inside `showAbout`. The Swing client changes. The Rust client stays.

**Truth.** About does not wrap. It shows all seven lines of `ABOUT_TEXT`, including the copyright line as one line. Rows equal the line count. Columns are the widest line in `m` widths, rounded up, plus one. The words stay, including DarkJediTerm. Rust `ABOUT_BODY` stays.

**Reason.** The box was truncating the text, so the copyright line and the wider lines never reached the glass.

**Avoidances.** Do not turn line wrap on. Do not drop the copyright line. Do not split the copyright line. Do not change the words, including DarkJediTerm. Do not change Rust `ABOUT_BODY`. Do not add a package, a public type, a public method, a private method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-3690

## The menu width follows the font

The main menu width follows the font. Every bar item and every popup row stays visible when the font changes. The frame grows when the menu is wider than the window. The 640 floor stays.

Token Calc on the bar had taken its preferred size once, at install, before the live font, and that size was stored as the maximum. A later font change grew the label inside that cap, so the title clipped. File, Tools, and Help already follow the preferred width. A popup keeps the size it had when it first opened, because a menu uses that size unless the width is 0. Calling the popup-size lock would freeze the preferred size. This rule does not call it.

`fitMenuWidth` runs from `applyMenuFont` after the font is seated. Each bar item drops its minimum, preferred, and maximum, then takes the live preferred size. The maximum width is that width. The maximum height stays open so the item does not stretch across the bar and push Help off the edge. Each menu popup clears its preferred size and takes the new preferred size as its size, so every row stays on the glass, including Close Pane, Import, and About Aetheris. When the menu plus the folded controls that share its row are wider than the frame, the frame grows. The 640 floor stays. A smaller font does not shrink that floor.

The install path no longer stores the Token Calc cap. `applyMenuFont` owns the width. The font still follows the control in both the open seat and the folded seat. The private helper is `fitMenuWidth`. The package method `applyMenuFont` stays. The Swing client changes. The Rust client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** After the font is seated, every bar item's maximum width is its live preferred width, and its maximum height stays unset. Every popup, including Close Pane, Import, and About Aetheris, takes the new preferred size. The frame grows to fit the menu and the folded controls on that row, and never shrinks below 640. Token Calc is not capped at install.

**Reason.** A maximum taken before the live font clipped Token Calc, and a popup remembered its first width, so a larger font hid bar items and rows.

**Avoidances.** Do not store a Token Calc maximum at install. Do not lock the popup size. Do not set a maximum height that stretches an item across the bar and pushes Help off. Do not shrink the frame below 640 to fit a smaller font. Do not drop Close Pane, Import, or About Aetheris off the popup. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-3691 PK-3692

## The SSH divider wears the lighter gray

When more than one SSH pane is open, the divider drag bar wears the lighter gray the Rust VT100 bar already wears, and the focused pane wears the cursor stroke. A single pane stays bare. The inspector split stays on the split ground.

An SSH terminal split sets the client property `ssh.pane.split` before `applySplitPane`. `DarkDivider` paints `SSH_DIVIDER`, the bytes 0x3A3A3A, when that property is set. The inspector split keeps the split ground. One-touch arrows stay. The divider size stays 11.

When more than one pane is open, the focused pane paints a 1 pixel inside stroke in 0x00D7FF. The private helper is `markPaneFocus`. The package method is `seatPaneFocus`. Unfocused panes stay bare. A single pane stays bare.

The Swing client changes. The Rust client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** An SSH split divider is 0x3A3A3A and stays 11 pixels, with one-touch arrows. With two or more panes, the focused pane has a 1 pixel inside stroke of 0x00D7FF. One pane, and every unfocused pane, stays bare. The inspector split keeps the split ground.

**Reason.** Several SSH panes were separated by the dark split ground, and the focused pane had no full cursor stroke, so the active pane was easy to lose. The Rust bar was already the lighter gray.

**Avoidances.** Do not paint 0x3A3A3A on the inspector split. Do not stroke a single pane or an unfocused pane. Do not change the divider size off 11. Do not drop the one-touch arrows. Do not use a stroke other than 1 pixel of 0x00D7FF. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-3693 PK-3694 PK-3695

## The focused pane wears the full cursor ring

The focused pane wears the full cursor ring.

The stroke owns a one-pixel inset. The border paint fills that ring in the cursor ink. The terminal and the thinking strip sit inside the inset. A later child paint cannot cover the ring. A single pane stays bare. An unfocused pane stays bare.

The divider gray stays 0x3A3A3A. The divider size stays 11. One-touch arrows stay. The inspector split stays. The stroke bytes stay 0x00D7FF.

The private field is the one-pixel inset. The Swing client changes. The Rust client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** With two or more panes, the focused pane reserves a one-pixel inset and paints it in 0x00D7FF after children, so the ring stays whole. The terminal and the thinking strip sit inside that inset. A single pane and every unfocused pane stay bare. The divider stays 0x3A3A3A at 11 pixels, with one-touch arrows. The inspector split stays.

**Reason.** The cursor stroke was a partial edge that a later child paint could cover, so the focused pane did not wear a full ring.

**Avoidances.** Do not paint the ring on a single pane or an unfocused pane. Do not let a child paint cover the inset. Do not put the terminal or the thinking strip on top of the ring. Do not change 0x00D7FF, 0x3A3A3A, or the 11 pixel divider. Do not drop the one-touch arrows. Do not restyle the inspector split. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a private method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-3696

## The SSH and Aetheris tab text follows the control-bar font

The SSH word and the Aetheris word take the control-bar point size. The open strip and the folded plates share that size.

A fold that installs the tab face again keeps the size. The range stays 8 through 36. The face stays sans serif. The plate colors stay. The plate shape stays. The canvas tab titles stay. The link chip stays. The menu font stays. The control-bar font stays. The bar height stays.

The private helper is `seatPlateFont`. The private field is `platePoints`. The Swing client changes. The Rust client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** The SSH tab and the Aetheris tab, on the open strip and on the folded plates, use the control-bar point size in a sans serif face. Reinstalling the tab face on a fold keeps that size. The range is 8 through 36. Plate colors, plate shape, canvas tab titles, the link chip, the menu font, and the bar height stay.

**Reason.** The control bar could change size while the SSH and Aetheris tabs stayed on a fixed face, including after a fold installed the tab face again.

**Avoidances.** Do not leave the folded plates on a different size from the open strip. Do not drop the size when a fold installs the tab face again. Do not change the face off sans serif. Do not widen the range past 8 through 36. Do not restyle plate colors or plate shape. Do not resize canvas tab titles, the link chip, the menu, or the bar height from this rule. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-3697

## The font control sits after Close Pane

The global font sizing control sits to the right of the button bar, after a divider that follows Close Pane.

The host row keeps Mode, Host, Port, Username, Password, and Remember password. The button row keeps Connect, Disconnect, Reconnect, Clear, Send file, New Term, and Close Pane. The private method barRule draws the divider after Close Pane. The font label and the spinner follow that divider. The font tip stays.

The other client paints the same order on the button row. A separator follows Close Pane. Font size, the minus control, the size, and the plus control follow that separator. The host row keeps the fields and the link status. The line between Connect and the shell tab stays. On the folded bar, which still sits on one row, Font size sits on the button row after Close Pane. The folded row stays.

The divider uses the grab edge already in the tree. Both clients carry this. No new package, no new public type, no new public method other than the private divider, no new vault column, no new channel, no new shell message type, no new color key, and no new library are added.

No system line is added, removed, or reordered. The doors stay. The settings file stays.

**Truth.** After Close Pane, a divider, then the font label and spinner, sit on the button row. The folded one-row bar keeps Font size in that same place. The host row and the rest of the button row do not move.

**Reason.** The sizing control belongs with the button bar, to the right of the last action, not mixed into the host fields. The existing grab edge is enough for the divider, so no new edge is introduced. The tip on the font control stays with the control.

**Avoidances.** The host fields, the link status, and the line between Connect and the shell tab stay. The folded row is not split. No wire line moves.


**semantic-key:** PK-3698

## The control bar button reads Load File

The control bar button reads Load File, and the tip, the help, and the notice titles that name that control use the same words.

The shell panel paints the button as Load File. The send-file tip names Load File. The shell help and the notice titles name Load File. The chat welcome names Load File. The other client paints the button as Load File, names Load File in the same tip, names Load File in the help body, and names Load File in the shell notices.

Method names stay. The three-em width stays. The file still opens in the paste editor. OK still inserts. Enter still submits. The button still enables only while the focused pane is connected. A disabled button still does not show the tip. Import stays a different path. The font control stays to the right of Close Pane.

Both clients carry this. No new package, no new public type, no new public method, no new vault column, no new channel, no new shell message type, no new color key, and no new library are added.

No system line is added, removed, or reordered. The doors stay. The settings file stays.

**Truth.** The visible name of that control is Load File on the button, in the tip, in the help, in the notice titles, and in the chat welcome. The path is unchanged: the file opens in the paste editor, OK inserts, Enter submits, and the button works only while the focused pane is connected.

**Reason.** The control loads a file into the editor. The word on the button, the tip, the help, and the notices has to be the same word, or the reader cannot find the control the help describes. Import is a different path and keeps its own name.

**Avoidances.** Method names do not change. The three-em width does not change. A disabled button still hides its tip. The font control does not move. The wire does not change.


**semantic-key:** PK-3699 PK-3700 PK-3701 PK-3702

## The thinking spinner sits on the control bar

The thinking spinner and its clock sit on the control bar. Open, they sit to the right of the global font control. Folded, they sit at the far east of the main menu row.

The user combo stays on the east of the first button row. A quiet deck hides the cluster. The clock follows the running card. The public method thinkCluster returns the spinner. The private method holdThink remembers it. parkThink places it after the font control. seatThink and placeThink move that same cluster to the east of the folded menu row. The font control stays where it was put, to the right of Close Pane. The mood dialog spinner stays. The shell spinner stays. The group clock stays.

Both clients carry this. No new package, no new public type, no new vault column, no new channel, no new shell message type, no new color key, and no new library are added. One cluster is moved. It is not copied.

No system line is added, removed, or reordered.

**Truth.** While the bar is open, the thinking spinner and its clock sit to the right of the font control. While the bar is folded, that same cluster sits at the east of the menu row. A quiet deck hides it. The clock follows the running card. The user combo stays on the east of the first button row.

**Reason.** The first button row was crowded. The spinner is one cluster, so parking it after the font control and moving that same cluster east when the bar folds keeps a single clock. The user combo, the mood dialog spinner, the shell spinner, and the group clock are other indicators and stay where they are.

**Avoidances.** The font control does not move. The user combo does not leave the first button row. A second spinner is not created. The wire does not change.


**semantic-key:** PK-3703 PK-3704 PK-3705 PK-3706 PK-3707 PK-3708 PK-3709 PK-3710

## Hover tips follow the switch

File, Settings gains the checkbox tooltips enabled. It starts on. A missing key stays on. Off, hover tips do not show.

ToolTipManager.setEnabled is the gate. seat_button_hover sets the tooltip delay. The private method is applyTooltipGate. The private functions are tip_delay and seat_button_hover. The declared check is the_settings_choice_stays. The files are AetherisNotebookPanel.java and chrome.rs. The known key is tooltips. Debug Mood stays. Painted borders stay. Other keys stay in extras. A blocked file keeps tips on for the session and is not overwritten.

No new public type, no new public method, no new test class, no new library, no new module file, and no new operating-system thread are added. The same user does not reload. Each user still keeps a separate mood. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the place. Every member frame still leaves together. One press still places one cell. The model header still starts each thinking stream. The user tip stays while tips are on. The footer spinner still follows the vault on screen. Both clients carry this. A known folded-bar mismatch stays outside this change: the shell glyph top is 108.2 and the status bottom is 64.1. The thinking-strip check still splits on the tag rule, and that split sits outside this change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** The settings checkbox reads tooltips enabled. It starts on. A missing tooltips key stays on. Off, hover tips do not show. The Java gate is ToolTipManager.setEnabled. The Rust path sets the tooltip delay through seat_button_hover, using tip_delay. Debug Mood, painted borders, and other keys in extras stay. A blocked settings file keeps tips on for the session and is not overwritten. The user tip still shows while tips are on.

**Reason.** Hover tips were always on. One checkbox has to gate every tip, and the default has to stay on so a missing key does not hide tips the reader already relies on. A blocked file cannot be saved, so the session keeps tips on rather than guessing an off state and writing it back. The delay is how this client suppresses the tip. The manager flag is how the other client does it. Both follow the same key.

**Avoidances.** The switch does not start off. A missing key does not turn tips off. Debug Mood and painted borders are not dropped. Other keys stay in extras. A blocked file is not overwritten. The user tip does not show while tips are off. The doors do not change. The folded-bar mismatch at 108.2 and 64.1, and the thinking-strip tag split, are not this change. The known paste-undo gap stays.


**semantic-key:** PK-3711 PK-3712 PK-3713 PK-3714 PK-3715 PK-3716

## Edit

FoldingPanel.seatPlateFont sets the SSH button, the Aetheris button, and the product tab pane to sans serif at the control-bar size, clamped from 8 through 36.

It clears a stored button size so the word can grow. applyMenuFont calls it, and that method already runs from the font spinner and from the first menu seat. showChrome calls it again after a fold installs a tab UI, so the size survives that install. SshClientApp stays as it stands. Rust stays. A crate move for this tab size waits until it is named.


**semantic-key:** PK-3717

## What stays

Clear mood, Approve, and Cancel stay. The mood switch stays. The saved line stays in aetheris-mood.yaml.

Canvas, group, and pipe doors stay. The console door order stays. The pinned host stays first. The mood string follows. The activate line follows. The turns follow. placeHost still inserts the host at index 0 only when absent. A raw session stays untouched. A pane that already holds the same line sends nothing. The one-transcript plan stays open for smoke. Its type list stays unbuilt. The open batch already in the working tree stays open. The Rust draft  stays as filed.

The Java Mood modal was built with Clear, Approve, and Cancel. SSH Mood Active is seated on this row. The status strip passed the mood gate as false. This row passes the focused pane user line. The server already consumes shell_mood and shell_mood_clear. Rust already paints SSH Mood Active. The empty Rust note on this batch is not a row.


**semantic-key:** PK-3729

## Wire

The console door order is unchanged. The pinned host stays first. The mood string follows. The activate line follows. The turns follow.

No system line is added, removed, or reordered.

**Truth.** The Mood modal shows a checkbox labeled SSH Mood Active, to the left of Clear mood, enabled when the canvas user name is non-empty. An armed user with the switch on and a saved line seats that line on every connected AMWP pane of that user. A pane that already holds the line sends nothing. An empty want clears only a pane that was already told, by shell_mood_clear. A non-empty want sends shell_mood and the line on scope A, channel A, type data. A failed write keeps the held line for the next beat. Rust does not gain a crate-only change on this row.

**Reason.** The modal had Clear, Approve, and Cancel, and the status strip was passing the mood gate as false. The server already consumes shell_mood and shell_mood_clear, and Rust already paints the checkbox, so this row seats the Java box and the relay. The per-user store and the live typing beat are the other two slices in the same tree.

**Avoidances.** Do not open a second revision. Do not rewrite slices already locked, and do not close the one-transcript smoke or build its type list. Do not stage hunks in chrome.rs or SshClientApp.java beyond the named hunks. Do not retarget branch, action, stage, or the when field. Do not add a lambda, Lombok, JUnit, a G-key, a public type, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not drop Clear mood, Approve, Cancel, the mood switch, or aetheris-mood.yaml. Do not change door order or let placeHost insert a host that is already present. Do not touch a raw session. Do not send when the pane already holds the same line. Do not clear a pane that was never told. Do not drop the held line after a failed write. Do not treat the empty Rust note as a row. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.


**semantic-key:** PK-3730

## Edit

AetherisNotebookPanel no longer parents the cluster on the footer. The footer keeps the user combo. thinkCluster returns the same spinner.

SshConnectionPanel.holdThink remembers the cluster. parkThink seats it at the end of the button row, after the font control. applyBarFont still sizes its labels when the folded row is holding it.

FoldingPanel.seatThink takes the cluster. placeThink parks it on the button row while the bar is open, and seats it on the east of the folded menu row while the bar is folded. One cluster moves. It is not painted twice. SshClientApp seats the cluster after the menu is installed.

Rust paints the cluster after the font control on the open button row, and at the far east of the folded menu row. A quiet deck paints nothing. east_reserve keeps the combo width and drops the spinner width. paint_east_capsule paints the combo only.

The test the_user_combo_stays_on_the_footer locks the combo on the footer and an empty spin rect. That test module does not compile on the recorded head. The proof does not run it.


**semantic-key:** PK-3739

## Product

The product files are SshTerminalPanel.java and the posts in SshClientApp.java. One product commit. Java moves. Rust stays.


**semantic-key:** PK-3754

# A key on an SSH pane posts the count

The SSH VT100 tab posts a token edit the way a canvas cell does. The count runs off the key. Each pane keeps its own integer. Java moves. Rust stays. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-3774 PK-3775 PK-3776 PK-3777 PK-3778 PK-3779 PK-3780 PK-3781 PK-3782 PK-3783 PK-3784 PK-3785 PK-3786 PK-3787

## Edits

The seat stays `AppleScrollBar`. `install` stays the pane arm. `applyScrollPane` stays the call. No new public type. No new public method.

`armStrip` arms one bar a host lays out itself. `DarkJediTermWidget.createScrollBar` calls it. JediTerm calls that method while the widget is built. Every new VT100 pane builds a widget, and a fresh session builds a fresh widget.

The terminal layout sizes the strip from the preferred width. A pane bar keeps a preferred size of 0 by 0. A strip bar's preferred width is `BAR_PX`, 15. The thin pill stays 2. The paint still eases from the thin pill to the popped-out width. The strip rests on the plate. The pointer on the widget shows the thin pill. The pointer on the strip pops the bar out.

The old track painted find ticks. The Apple bar does not paint those ticks. The find highlight in the terminal stays.

No new package. No new color key. No new library.


**semantic-key:** PK-3790

## Depends

`SshClientProperties.java`.

**Truth.** The default app font size is 20. A stored font key stays. The range stays 8 through 36. Rust `FONT_DEFAULT` stays 14. Java moves. Rust stays.

**Reason.** The Java default was not 20, and the Rust missing-key default of 14 must stay.

**Avoidances.** Do not rewrite a stored font key. Do not change the range. Do not change Rust `FONT_DEFAULT`. Do not treat this row as a feature. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not add a public type, a public method, a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.


**semantic-key:** PK-3792

# The main menu hover is a lighter grey tab

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.


**semantic-key:** PK-3793

# Help returns to the main menu bar

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.


**semantic-key:** PK-3794

# Theme keys name the cursor, the menu font, and the two prompts, the icon is the eye-and-swoosh motif, and a connected resize keeps the connection strip collapsed.

Theme, icon, and the connection strip are shell chrome. The files are the client properties, the client theme, the client application, and the prompt color helper.

| Key | Default |
|---|---|
| `ui.terminal.color.cursor` | translucent electric blue while the terminal is focused |
| `ui.terminal.color.cursor.inactive` | a darker, paler form of the focused cursor |
| `ui.color.menu.foreground` | matrix-rain green, and that green is the menu font only |
| `ui.terminal.color.foreground` | keep the color already stored |
| `ui.prompt.chat.foreground` | green |
| `ui.prompt.cmd.foreground` | blue |

The host helper that paints `cmd> ` in medium blue keeps that name. Add a green `chat>`. Do not add a second yaml parser family to read these keys.

While the session is connected, a resize re-asserts the connection-strip divider at position 0. Disconnect restores the last expanded height.

The window icon is generated at build: an Eye of Horus crossed with a swoosh, green, blue, and gold on black. If the pixels are late, a black square carrying the motif is an acceptable placeholder. The icon does not block the notebook.

**Truth.** Cursor, inactive cursor, menu font, chat prompt, and command prompt are named theme keys; the connected strip stays collapsed across resize; the icon is the eye-and-swoosh on black.

**Reason.** The menu green is font only so the menu bar does not become a green slab. The strip must stay collapsed or a resize pops the connection form back over the terminal. The icon must not hold the notebook hostage.

**Avoidances.** Do not recolor the menu background with the matrix green. Do not add a new yaml stack for these keys. Do not block the notebook on icon pixels. Do not let a connected resize open the strip.


**semantic-key:** PK-3795 PK-3796

# Every Swing scrollbar shows a light-gray arrow button with a black triangle at each end, and the terminal bar is left alone.

Files: `SshClientTheme`, `AetherisNotebookPanel`, `PasteEditorDialog`, and `SshClientApp.showHelp`.

`installLookAndFeelDefaults` sets `ScrollBar.background` and leaves the arrow buttons to the platform look. The shell never calls `UIManager.setLookAndFeel`.

1. Add `applyScrollPane(JScrollPane)` on the theme. It installs one UI on the vertical bar and the same UI on the horizontal bar.
2. The UI is a private static subclass of `BasicScrollBarUI` inside `SshClientTheme`. It is not a new top-level type and gets no trace comment.
3. `createDecreaseButton` and `createIncreaseButton` each return a button with a non-zero preferred size, opaque, face `#C0C0C0`, and a black triangle for that orientation. Both ends stay present on vertical and horizontal bars. The arrow is `#000000`.
4. Call `applyScrollPane` on every Swing pane this shell builds: the notebook canvas (`new JScrollPane(column)`), the inspector inner pane and the inspector outer pane inside `wrap`, each open cell's user pane and assistant pane, the View modal pane, the paste-editor pane, and the Help dialog pane in `showHelp`.
5. Do not touch the terminal widget's bar.

While soft wrap is on, the inspector text's horizontal bar is not shown, so its arrows exist for the wrap-off state. Vertical arrows stay.

Light gray is `#C0C0C0`. Black is `#000000`. The platform look is not the arrow.

**Truth.** One scrollbar UI, both orientations, face `#C0C0C0`, arrow `#000000`, on the named Swing panes, and not on the terminal widget.

**Reason.** The platform arrows were missing or easy to miss. The operator asked for a light-gray button and a black arrow at each end.

**Avoidances.** Do not make this a new top-level type. Do not paint the terminal widget's bar. Do not hide the vertical arrows while wrap is on. Do not give the buttons a zero preferred size.


**semantic-key:** PK-3797

# The bottom status line names the connected user of the focused terminal session.

The operator's words: in the status line at the very bottom, include the currently connected user, so a focus change on the terminal pane makes it easy to know which user is in focus.

The bottom status line names the connected user of the focused terminal session. It changes when that focus changes. This note waits.

**Truth.** The bottom line names the focused connected user and follows focus.

**Reason.** Several sessions make the notebook's user ambiguous without that name.

**Avoidances.** Do not wait for a notebook message to discover which user is focused.


**semantic-key:** PK-3798

# The paste dialog matches the spinner size, and Ctrl+Enter is one chat turn.

1. Set the font spinner, for example to 18. Press Ctrl+V.
2. The dialog font size matches the spinner. Edit. Press Ctrl+Enter.
3. The result is one chat turn, not one turn per line.

The dialog is 18 by 72. Enter inside it is a newline.

**Truth.** Spinner size 18 is the example. One turn. The dialog font matches.

**Reason.** A per-line submit was the bug this flow guards.

**Avoidances.** Do not produce one turn per line. Do not ignore the spinner size.


**semantic-key:** PK-3799

# The window is titled Casserole SSH Client, collapses the connection panel on connect, and confirms before close.

- Title: `Casserole SSH Client`.
- Icon: `/icons/casserole.jpg`, the master plus 32 and 16.
- A vertical split. The connection panel is on top. The terminal is below.
- On first show, the divider sits at the control panel's preferred height.
- On connect, the control panel collapses. Reconnect and disconnect stay available after expand.
- Status bar: Connecting, Connected with host and port, Disconnected, and errors.
- Close confirms, waits for disconnect, disposes the widget, and persists the size.

The frame default is 1024 by 968, saved about 400 ms after a resize.

**Truth.** Title `Casserole SSH Client`. Collapse on connect. Confirm on close. Icon at three sizes.

**Reason.** The connection strip should get out of the way once the session is up, without losing reconnect.

**Avoidances.** Do not close without a confirm. Do not leave the strip open after connect. Do not drop the 32 and 16 icons.


**semantic-key:** PK-3800

# Both modes force a raw terminal, accept every host key, and allow 15 seconds for authentication.

The session controller runs connect, disconnect, and reconnect on daemon workers serialized on a worker lock. Interface callbacks hop to the event thread in the shell application.

Connect:

1. Validate host, user, and port.
2. A client that accepts every server key. This is version 1. There is no known-hosts file.
3. A password identity. Authentication verify waits 15 seconds.
4. A shell channel with a pseudo-terminal configuration and a `TERM` environment value.
5. The connector wraps the inverted streams. `write` is synchronized, because the terminal widget can write from multiple threads.
6. A remote close sets the status to `Connection closed by remote host.`

Raw modes, both VT100 and Standard PTY: echo off, canonical off, signals off, extensions off, the three echo variants off, output processing off, and eight-bit characters on. The flag list is `ECHO=0`, `ICANON=0`, `ISIG=0`, `IEXTEN=0`, `ECHOE=0`, `ECHOK=0`, `ECHOCTL=0`, `OPOST=0`, `CS8=1`.

The shell does its own echo and line editing. Canonical echo plus signals mangled bracketed paste and turned Ctrl+C into a signal, which stamped `^C` on each pasted line.

| | VT100 | Standard PTY |
|---|---|---|
| `TERM` and the terminal type | `vt100` | `xterm-256color` |
| Mode map | the same raw map | the same raw map |

The combo exists for a planned fuller map. Both paths currently force raw. Changing mode reconnects.

Resize: the terminal widget sends window-change. The server shell currently reads `COLUMNS` only at start.

**Truth.** Accept-all keys. 15 second auth. Raw map on both modes. `vt100` versus `xterm-256color`. Write is synchronized. Remote close has that status sentence.

**Reason.** Signals and canonical echo broke paste. The two modes are not two maps yet.

**Avoidances.** Do not enable echo, canonical mode, or signals. Do not claim the server updates columns on resize. Do not add a known-hosts check in this capture.


**semantic-key:** PK-3801

# The paste dialog is 18 by 72, uses the spinner's typeface, and accepts on Ctrl+Enter.

The paste dialog:

- Modal, dark themed, a text area of 18 by 72, wrap on.
- The font is the terminal typeface at the control-panel spinner size, so the editor matches the canvas.
- Enter is a newline. Ctrl+Enter or OK accepts. Cancel discards.
- The client then sends the paste and submits it: a bracketed wrap plus a carriage return.
- File send is UTF-8, rejects a NUL or too many control bytes, caps at 65536, then uses the same dialog.

A plain client does not get this dialog. It gets the server-side summary `[paste: N lines, M chars — Enter to send]`.

**Truth.** 18 by 72. Enter inserts. Ctrl+Enter sends. File cap 65536. NUL rejected. Plain clients see the server summary.

**Reason.** The editor has to match the canvas font or the operator cannot judge the blob.

**Avoidances.** Do not treat Enter as send. Do not skip the NUL check. Do not give a plain client this dialog.


**semantic-key:** PK-3802

# The client is reusable window pieces that do not start the host.

Reusable window pieces, so they can later move into a larger app: the connection panel, the terminal panel, the session controller, the theme, and the client properties.

The window, the connection panel, the session, the look, the paste editor, and the history split are the child sections. The client does not start the host. In this capture it stores `~/.ssh-client.properties` and defaults the port to 22.

**Truth.** Five reusable types. The client is not the host.

**Reason.** The pieces have to move later without dragging the host framework with them.

**Avoidances.** Do not start the host from these types. Do not rewrite the default port inside this capture.


**semantic-key:** PK-3803

# Panes are nested splits, New Term asks horizontal or vertical, and the last pane cannot close the window.

Locked with the New Term dialog. Nested `JSplitPane`s, not tabs. Each pane is its own terminal panel, its own session controller, and its own shell. Several independent terminals in one window.

| Gesture | Lock |
|---|---|
| Start or restart | One terminal pane, ready to Connect. A fresh layout every launch. |
| New Term, on the connection panel | A dialog: Horizontal or Vertical. The split is relative to the currently focused terminal pane. |
| Hotkeys | Ctrl+Shift+D horizontal, Ctrl+Shift+E vertical, Ctrl+Shift+W closes the pane. |
| Menu | The same three actions, plus Exit, Help, and About. Shortcuts remain primary. |
| Connection bar | One strip. It always reflects the focused pane. Connect, Disconnect, Reconnect, Send file, and New Term act on that focus. |
| New pane | Copies host and port from the focused pane. The user comes from the combo. The operator must change the user before Connect if that focused user is already a live disk-backed session. |
| Close pane | Collapse the split. The last pane cannot close the window except File, then Exit. |
| Mouse-relative split | Out. A focused-pane split is enough. Hit-testing the terminal widget for a mouse-aware split is too much override. |

**Truth.** One pane at launch. Dialog is Horizontal or Vertical only. Hotkeys D, E, and W with Ctrl+Shift. Last pane stays. No mouse-relative split.

**Reason.** Tabs would hide the other shell. A mouse hit-test would fight the terminal widget.

**Avoidances.** Do not use tabs. Do not put Horizontal and Vertical as two File items. Do not close the window from the last pane. Do not persist the layout.


**semantic-key:** PK-3804

# The client stores accounts in one shell file and splits the window into independent terminals.

The client gains multiple terminals, remembered accounts, and a shell-file store. The account file, the panes, and the font rule are the child sections.

The store is `~/.casserole/ssh-client.yaml`. The split tree is not persisted. Each pane is its own shell. One font size serves the window.

**Truth.** One shell file, many panes, one font, no saved layout.

**Reason.** One terminal could not show two users at once.

**Avoidances.** Do not persist the split tree. Do not share one channel across panes.


**semantic-key:** PK-3805 PK-3806

# Each pane owns its controller, the divider is #3a3a3a with an #8a8a8a outline, and restart is one pane.

This is the largest package. Keep one connection strip and many shells.

New:

- A terminal session, named as the tree prefers. One terminal panel, one session controller, the last connect request, and state. The listener attaches the connector to this panel only. Interface hops go to the event thread. Status and button enablement run only if this session is focused.
- A split helper replaces the focused component with a split pane, horizontal or vertical, containing the old session and the new one. Close gives the sibling the parent slot. The last pane ignores close. File, then Exit, is the only way out.
- A custom divider through the theme. Dark fill `#3a3a3a`. Light outline `#8a8a8a`. Applied to every splitter, including the connection strip versus the terminals. This package's colors are those two. The design also names `#2a2a2a` as the second dark fill and a divider of about 8. Do not replace this package's `#3a3a3a` and `#8a8a8a` with a later 11 pixel constant.

Edit:

- The connection panel listener gains a new-term request. The New Term button opens a dialog, Horizontal or Vertical, and splits the focused pane. The new pane copies host and port from the focus. The user comes from the combo.
- The shell application holds a tree of sessions and tracks focus with a widget focus listener. The connection bar applies connection state and fills fields from the focused session. Connect, Disconnect, Reconnect, Send file, and Clear act on the focus. A font-size change calls `setFontSize` on every terminal panel. The shared dark terminal settings stay, and each widget is refreshed.
- Hotkeys on the existing keyboard-focus dispatcher, the same place as the paste interceptor. Ctrl+Shift+D horizontal, Ctrl+Shift+E vertical, Ctrl+Shift+W close. Consume them so the terminal widget does not eat them.
- A menu bar. File: New Term…, Close Pane, Exit. Help: Help…, About. The same dialogs as the button.
- About and Help are small dialogs, plain text or a text area. Help is client gestures plus a short server blurb. It is not a second command book.
- Restart is one pane. Do not write the layout.

Do not miss:

- Do not share one session controller or one terminal channel across panes.
- The shared settings object is wanted. Still call `setFontSize` on each panel so the canvases repaint.
- New Term with no focus uses the only pane, or the last pane.
- The same user, disk-backed, shows the server red line in that pane. Other panes stay up.
- The paste interceptor and Send file target the focused connector.

Done: compile. Two panes, two users, a shared font, dark dividers, hotkeys, About, and Help.

**Truth.** Own controller per pane. Fill `#3a3a3a`, outline `#8a8a8a`. Hotkeys consumed. Restart is one pane.

**Reason.** A shared controller would cross two shells. A shared font object still needs a per-panel refresh.

**Avoidances.** Do not share a controller or a channel. Do not persist the layout. Do not let the terminal widget eat the hotkeys. Do not close the window from the last pane.


**semantic-key:** PK-3807

## Land

Java is named. The crate does not move.

Min, max, and close stay. The import path stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** On Windows, the native caption takes the theme's frame background for the bar and the border, and the panel foreground for the title words, in dark caption mode. Any other system, a missing handle, or a failed paint leaves the window able to open.

**Reason.** The title bar was not the very dark charcoal of the window theme, so the frame and the caption did not match.

**Avoidances.** Do not add a color key or a library. Do not make the caption methods public. Do not add a public type. Do not drop min, max, or close. Do not move the content pane off the frame background. Do not change the import path. Do not edit yaml. Do not let a caption failure stop the window from opening. Do not paint a caption on a system that is not Windows, or when the native handle is missing. Do not move Rust. Do not stage the card-round hunks. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-7795 PK-7864

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the section below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. No new channel. No new wire type. The new methods are `SshClientProperties.getProductTab` and `SshClientProperties.setProductTab`. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed.

A close on the SSH tab reopens on the SSH tab. A close on the Aetheris pane reopens on the Aetheris pane and connects.


**semantic-key:** PK-7815
