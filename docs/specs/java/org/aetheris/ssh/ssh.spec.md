# org.aetheris.ssh

Component file. It carries how this package is built.

**Depends:** [java/org/aetheris/config/config.spec.md](../config/config.spec.md), [java/org/aetheris/swing/swing.spec.md](../swing/swing.spec.md), [java/org/aetheris/wire/wire.spec.md](../wire/wire.spec.md), [java/org/casserole/chat/chat.spec.md](../../casserole/chat/chat.spec.md), [java/org/casserole/config/config.spec.md](../../casserole/config/config.spec.md), [java/org/casserole/lamina/Lamina.spec.md](../../casserole/lamina/lamina.spec.md), [java/org/casserole/lamina/lamina.spec.md](../../casserole/lamina/lamina.spec.md), [java/org/casserole/llm/llm.spec.md](../../casserole/llm/llm.spec.md), [java/org/casserole/mood/mood.spec.md](../../casserole/mood/mood.spec.md), [java/org/casserole/pool/pool.spec.md](../../casserole/pool/pool.spec.md), [java/org/casserole/ssh/shell/shell.spec.md](../../casserole/ssh/shell/shell.spec.md), [java/org/roux/model/model.spec.md](../../roux/model/model.spec.md), [java/org/roux/roux.spec.md](../../roux/roux.spec.md), [java/org/roux/session/session.spec.md](../../roux/session/session.spec.md), [java/org/roux/spring/spring.spec.md](../../roux/spring/spring.spec.md), [java/org/roux/token/token.spec.md](../../roux/token/token.spec.md)

## The VT100 heatbar wears the Roux session count

The VT100 heat bar collects the Roux session count when the app starts and keeps that count as the session continues. The canvas heat bar stays on the selected cell.

The SSH heat integer is the sum of the estimate counter over the stored Roux messages for that username. Empty text counts as 1. A chat with no messages uses 1, with 0 characters and 0 blocks. A chat with messages uses the sum. The system prompt is stored and counted. Mood stays in the prompt builder and is not added again. The message list stays. The canvas ring stays. Token Calc opens on that same integer.

The door is channel A. The ask is `session_tokens:`. The reply is `session_tokens_result:` with `used`, `chars`, and `blocks`. The reply does not paint the VT100. The client asks once when the mux is answered. The server pushes the same reply at the end of mux start, after a stored turn, after command dispatch, and after a session replace. Until the reply is held, the glass estimate may show. Each pane keeps its own held sum. When the engine word contains ollama, the budget is num-ctx 131072. Otherwise num-ctx is omitted, because a context size of 0 does not send the field.

The public methods are `sessionTokenReply`, `sendConsoleText`, `requestSessionTokens`, `rouxSessionHeld`, `rouxSessionUsed`, `rouxSessionChars`, and `rouxSessionBlocks`. The private helper is `publishSessionTokens`. The Rust helper is `session_tokens_of`. Each pane holds its own sum. No new wire type is added beyond these two lines. No system line is added, removed, or reordered. The prompt stays. The mood line stays in the prompt builder.

Both clients change. The host answers on channel A.

**Truth.** The VT100 heat integer is the sum of the per-message estimate over the stored Roux messages for that user, including the system prompt and excluding a second add of the mood. Empty text is 1. An empty chat is 1 used, 0 characters, and 0 blocks. The ask `session_tokens:` and the reply `session_tokens_result:` with `used`, `chars`, and `blocks` travel on channel A and do not paint the terminal. The client asks once when the mux is up. The server pushes after mux start, after a stored turn, after command dispatch, and after a session replace. Each pane holds its own sum. Until that reply is held, the glass estimate may show. The canvas ring stays on the selected cell. Ollama uses num-ctx 131072. Any other engine omits num-ctx.

**Reason.** The VT100 bar was a glass estimate of the screen, not the Roux session, so it did not match the stored messages at start and it did not keep the count as the session continued. The canvas ring is a different count and stays on the selected cell.

**Avoidances.** Do not add the mood a second time. Do not drop the system prompt from the sum. Do not paint the reply on the VT100. Do not share one held sum across panes. Do not leave the canvas ring on the Roux tally. Do not send num-ctx when the budget is 0. Do not add, remove, or reorder a system line. Do not add a package, a public type, a shortcut, a stored column, a new channel, a new wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-0464 PK-0465 PK-0466

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

## A saved Aetheris tab waits for Connect, and only then takes focus.

A close on Aetheris still remembers that tab. On the next open the client was logging in the last user by itself. The client waits until the user is picked and Connect is clicked, and only then brings Aetheris into focus. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The saved word stays `aetheris`. The open does not call `connectRequested`. The connection form stays as it is. After that Connect reaches the session, the Aetheris tab is selected. A click on the SSH tab before Connect clears the wait and stores `ssh`. A close while the wait is still on keeps the stored word. The Aetheris tab stays dark until the mux is up.

`SshClientApp` sets the restore flag when the stored word is `aetheris`. It does not connect from that flag.

`sessionConnected` on the focused session still selects Aetheris when the flag is set and the tab is enabled. The select does not write the word.

`confirmAndExit` on Yes writes the visible tab when the wait is already clear. While the wait is still on, it leaves the stored word. Disconnect during exit still does not replace `aetheris` with `ssh`.

No new public method, package, public type, vault column, G-key, channel, or wire type. `getProductTab` and `setProductTab` stay the product-tab methods.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

The tab stays dark until the mux is up, and a waiting close does not store `ssh`. The connection form stays. The open connect is dropped, and Aetheris is selected after the user's Connect. The SSH-tab cancel stays. The row is the build.

**Truth.** A stored `aetheris` sets a restore flag and does not call `connectRequested`. After the user's Connect, `sessionConnected` selects Aetheris when the tab is enabled, and that select does not write the word. A click on SSH before Connect clears the wait and stores `ssh`. A close while the wait is on keeps `aetheris`.

**Reason.** The previous open logged in the last user by itself. The saved word still means Aetheris should return, but only after the user is picked and Connect is clicked. The connection form stays as it is. The tab stays dark until the mux is up. A waiting exit must not replace `aetheris` with `ssh`, including a disconnect during exit.

**Avoidances.** Do not connect from the restore flag. Do not write the product-tab word when the restore select runs. Do not store `ssh` from a close while the wait is still on. Do not replace `aetheris` with `ssh` on disconnect during exit. Do not add a public method, a package, a public type, a vault column, a G-key, a channel, or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.

**semantic-key:** PK-5998

## Console thought

A pool thought on the VT100 rides the thinking channel and opens the pull-down. The thought stays off the terminal.

A VT100 pool turn was collecting the reply and dropping the thought. The thought now rides kind `t` on the stream channel that already carries that kind. The cell id is the nil id the console already uses. When console pool thinking is on, that chunk opens the pull-down and the tag is pool. The thought is not written on the terminal. The assistant reply still paints on the VT100. The end of that turn closes the block, so the next thought starts a new block. A canvas cell keeps its own gate. Ollama and Grok on the console stay as they are.

No new channel is allocated. The session writer stays bound to the channel that already exists. The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this difference waits until it is named. Nothing beside this routing moves. The cells already on the canvas stay as they are.

**Truth.** With console pool thinking on, a pool thought for the console's nil cell arrives as kind `t` on the existing stream channel, opens the pull-down, and is tagged pool. It is not written on the terminal. The assistant reply still paints on the VT100. The end of the turn closes the block, so the next thought starts a new block. A canvas cell keeps its own gate. Ollama and Grok on the console are unchanged. No new channel is allocated.

**Reason.** Pool thinking on the VT100 was not opening the pull-down. The thought was being dropped while the reply was collected. The thought has to ride the thinking channel that already exists, open the pull-down, and stay off the terminal.

**Avoidances.** Do not write the thought on the terminal. Do not allocate a new channel. Do not unbind the session writer. Do not paint the assistant reply anywhere but the VT100. Do not leave the block open after the turn, and do not append the next thought to the closed block. Do not move a canvas cell off its own gate. Do not change Ollama or Grok on the console. Do not write a Rust crate on this row.


**semantic-key:** PK-3577 PK-3578 PK-3579 PK-3580 PK-3581 PK-3582

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

## The button word

OK buttons in modals, including the Token Calc modal, and the `v` fold toggle, show their specified text.

The Token Calc OK stays `OK`. The pin OK stays `OK`. The fold mark stays `v` while open and `^` while folded. The thinking fold stays `v` while open and `^` while folded. The tool fold and the group fold stay `v` and `^`. The connection-bar mark stays `v` and `^`. A label of one or two characters takes two ems after the border is on. A longer label keeps the short-fit width. Approve still grows by three ems after that fit. Yes and No stay on their own floor. The height stays the short fit when the word fits, and rises when the short fit is under the word. The minimum size is that floor. The short fit no longer replaces the label with an ellipsis. The label stays the specified text.

The floor is applied again after the mark border is on, and again when the thinking fold changes its letter. It runs for the notebook, for the thinking strip, and for the folding panel.

There is no new package, no new public type, and no new public method. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, no settings-file edit, and no dependency-manifest edit. Nothing beside this floor moves. The send set stays as it is. The Java client carries this. The Rust client stays. This row is not a separate feature. The conversation transfer stays closed.

**Truth.** Modal OK buttons, including Token Calc, show `OK`. The pin OK shows `OK`. Open fold marks show `v` and folded marks show `^`, on the thinking fold, the tool fold, the group fold, and the connection bar. A label of one or two characters takes two ems after the border is on. A longer label keeps the short-fit width. Approve still grows by three ems after that fit. Yes and No stay on their own floor. Height stays the short fit when the word fits, and rises when the short fit is under the word. The minimum size is that floor. No label is replaced by an ellipsis. The floor runs again after the mark border and when the thinking fold changes its letter.

**Reason.** OK buttons and the fold toggle were not showing the text specified for them. The short fit was replacing that text with an ellipsis.

**Avoidances.** Do not change `OK`, `v`, or `^` to any other label. Do not give a one-character or two-character label any floor but two ems after the border is on. Do not put a longer label on that two-em floor. Do not take the three extra ems off Approve. Do not move Yes and No off their own floor. Do not let the short fit replace a label with an ellipsis. Do not leave the height under the word. Do not skip the floor after the border or after the thinking fold changes letter. Do not change the Rust client. Do not reopen the conversation transfer. Do not add a package, a public type, a public method, a global key, a vault column, a channel, a wire message type, a color key, or a library. Do not edit a settings file or a dependency manifest.


**semantic-key:** PK-3656 PK-3657 PK-3658 PK-3659 PK-3660 PK-3661 PK-3662 PK-3663 PK-3664

## The button face

Buttons that have not already been given a special face take the Rust tab face, and their text color holds through focus, hover, press, and disable.

Idle fill is the tab gray with no stroke. Hover takes the hover fill and a one-pixel hover edge. Press fills #4a4a4a. The word stays the button foreground, including while the hover word is emerald. A disabled word is that same foreground at half alpha. The corner radius is 12. Plate tabs, folded plate words, the Rust marks, the mark letters, choice faces, arrows, and the hidden zero-size bar buttons stay on the faces they already have.

The private class is `PlateFaceUi`. The private helpers are `seatPlateFace` and `plateSkip`. The Swing client changes. The Rust client stays.

**Truth.** An unconverted button is tab gray with no idle stroke, a one-pixel edge on hover, #4a4a4a while pressed, and corner radius 12. The foreground, including emerald hover text, does not change with focus, hover, press, or disable, except that a disabled word is that foreground at half alpha.

**Reason.** Unconverted buttons were still on a light control face, and a state change was recoloring the word. The tab face keeps the word stable and matches the Rust chrome.

**Avoidances.** Do not paint this face onto plate tabs, folded plate words, Rust marks, mark letters, choice faces, arrows, or the hidden zero-size bar buttons. Do not swap the foreground for a different color on focus, hover, press, or disable. Do not drop the emerald hover word. Do not use a corner other than 12. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-3675 PK-3676 PK-3677 PK-3678 PK-3679

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

## The shell launch enables native access

The shell launch passes --enable-native-access=ALL-UNNAMED. That is the flag named for an unnamed module.

The shell application calls SymbolLookup.libraryLookup for user32 and for dwmapi from an unnamed module. JDK 26 warns and names --enable-native-access=ALL-UNNAMED. bin/aetheris-shell.ps1, bin/aetheris-shell.sh, and the stick launcher written by bin/install-aetheris-shell.ps1 pass that flag to the shell JVM. The command pages that quote that launch carry the same flag. The run configuration for the shell application sets VM_PARAMETERS to the same flag. The class stays org.casserole.app.ui.ssh.SshClientApp. The launch stays java -cp. paintCaption stays. The server launch stays.

A call to SymbolLookup.libraryLookup for user32 warns when the flag is absent and does not warn when --enable-native-access=ALL-UNNAMED is present. No new public type, no new public method, and no new test class are added. Java sources stay. The Rust client stays. The server launch is not given this flag.

The doors do not gain, lose, or reorder a line.

**Truth.** The shell JVM is started with --enable-native-access=ALL-UNNAMED from the PowerShell launcher, the shell script, the stick launcher, the command pages that quote the launch, and the run configuration VM_PARAMETERS. The lookup is user32 and dwmapi. The class stays org.casserole.app.ui.ssh.SshClientApp. The launch stays java -cp. Without the flag the lookup warns. With the flag it does not.

**Reason.** JDK 26 restricts SymbolLookup.libraryLookup on an unnamed module and names this flag. The window caption still needs user32 and dwmapi. Passing the flag on every shell launch removes the warning without moving the class onto the module path and without changing paintCaption.

**Avoidances.** Java sources are not edited. The Rust client is not edited. The server launch does not take the flag. The class name and java -cp stay. paintCaption stays. The wire does not change.

**semantic-key:** PK-5999

## Hover tips follow the switch

File, Settings gains the checkbox tooltips enabled. It starts on. A missing key stays on. Off, hover tips do not show.

ToolTipManager.setEnabled is the gate. seat_button_hover sets the tooltip delay. The private method is applyTooltipGate. The private functions are tip_delay and seat_button_hover. The declared check is the_settings_choice_stays. The files are AetherisNotebookPanel.java and chrome.rs. The known key is tooltips. Debug Mood stays. Painted borders stay. Other keys stay in extras. A blocked file keeps tips on for the session and is not overwritten.

No new public type, no new public method, no new test class, no new library, no new module file, and no new operating-system thread are added. The same user does not reload. Each user still keeps a separate mood. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the place. Every member frame still leaves together. One press still places one cell. The model header still starts each thinking stream. The user tip stays while tips are on. The footer spinner still follows the vault on screen. Both clients carry this. A known folded-bar mismatch stays outside this change: the shell glyph top is 108.2 and the status bottom is 64.1. The thinking-strip check still splits on the tag rule, and that split sits outside this change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** The settings checkbox reads tooltips enabled. It starts on. A missing tooltips key stays on. Off, hover tips do not show. The Java gate is ToolTipManager.setEnabled. The Rust path sets the tooltip delay through seat_button_hover, using tip_delay. Debug Mood, painted borders, and other keys in extras stay. A blocked settings file keeps tips on for the session and is not overwritten. The user tip still shows while tips are on.

**Reason.** Hover tips were always on. One checkbox has to gate every tip, and the default has to stay on so a missing key does not hide tips the reader already relies on. A blocked file cannot be saved, so the session keeps tips on rather than guessing an off state and writing it back. The delay is how this client suppresses the tip. The manager flag is how the other client does it. Both follow the same key.

**Avoidances.** The switch does not start off. A missing key does not turn tips off. Debug Mood and painted borders are not dropped. Other keys stay in extras. A blocked file is not overwritten. The user tip does not show while tips are off. The doors do not change. The folded-bar mismatch at 108.2 and 64.1, and the thinking-strip tag split, are not this change. The known paste-undo gap stays.


**semantic-key:** PK-3711 PK-3712 PK-3713 PK-3714 PK-3715 PK-3716

# Focused pane cursor ring

The full cycle is named, and the build is named. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.

**semantic-key:** PK-6000

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. This row is not a feature. A formal unit test is not declared. An outside-list row is not added.

Branch, action, stage, and the when field are not retargeted.

The edit below is the build. Our types take no lambdas. Lombok stays out. JUnit stays out. There is no new G-key, no new public type, no new public method, no new private method, and no new package. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited. The private field is the one-pixel inset.

The product tree compiles with tests skipped. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The product commit comes next. Its message states that the change is in the tree. Its message states that the change landed.

**semantic-key:** PK-7885

## Goal

The focused pane wears the full cursor ring.

When more than one SSH pane is open, that ring is the focused pane's. A single pane stays bare.

**semantic-key:** PK-6001

## What stays

Divider gray stays 0x3A3A3A, and the divider size stays 11.

One-touch arrows stay. The inspector split stays on the split ground. A single pane stays bare. An unfocused pane stays bare. The stroke bytes stay 0x00D7FF. No new color key is introduced. The heatbar geometry stays. The track stays 5 pixels. Slices already locked stay locked. The open batch already in the working tree stays open.

**semantic-key:** PK-6002

## Edit

Java reserves a one-pixel empty inset while the pane is the focused leaf, and the border paint fills that inset so the terminal cannot cover the ring.

seatPaneFocus sets the empty inset while the pane is the focused leaf, then revalidates. paintBorder fills that inset in the cursor ink, one pixel on each edge. The paintChildren overlay is removed. The terminal and the think strip sit inside the inset, so a later child paint cannot cover the ring. The pane does not grow. A single pane stays bare. An unfocused pane clears the inset and stays bare.

Rust stays. The Rust stroke is already the inside cursor stroke on the focused leaf.

**semantic-key:** PK-6003

## Proof

The product tree compiles with tests skipped, and the proof does not open the window or touch a live session.

The window stays closed. The proof does not make a live POST. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

**semantic-key:** PK-6004

## Scope

Java is the tree that moves, and Rust stays.

The neighbor lines under What stays stay in force. There is no side move on this slice.

**semantic-key:** PK-6005

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This land leaves the working set as it stands.

**Truth.** When more than one SSH pane is open, the focused pane wears a one-pixel cursor ring on every edge in the cursor ink, and the terminal and the think strip sit inside the inset so a later child paint cannot cover the ring. A single pane stays bare. An unfocused pane clears the inset and stays bare. The pane does not grow. Rust already draws the inside cursor stroke on the focused leaf and does not move.

**Reason.** A paintChildren overlay can be covered by a later child. An empty inset from seatPaneFocus, filled by paintBorder, keeps one pixel on each edge outside those children. The private field is that inset. The stroke bytes stay 0x00D7FF, so no new color key is required.

**Avoidances.** Do not open a second revision. Do not retarget branch, action, stage, or the when field. Do not add a lambda, Lombok, JUnit, a G-key, a public type, a public method, a private method, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not grow the pane, ring a single pane, or ring an unfocused pane. Do not change divider gray 0x3A3A3A, divider size 11, the one-touch arrows, the inspector split, the heatbar geometry, or the 5 pixel track. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.

**semantic-key:** PK-7904

# Bar buttons sit five pixels shorter

The full cycle is named, and the build is named. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.

**semantic-key:** PK-6006

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. This row is not a feature. A formal unit test is not declared. An outside-list row is not added.

Branch, action, stage, and the when field are not retargeted.

The edit below is the build. Our types take no lambdas. Lombok stays out. JUnit stays out. There is no new G-key, no new public type, no new public method, no new private method, and no new package. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited. The edit sits inside holdShortLabel.

The product tree compiles with tests skipped. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The product commit comes next. Its message states that the change is in the tree. Its message states that the change landed.

**semantic-key:** PK-7886

## Goal

The bar buttons sit five pixels shorter, and the height still follows the font.

The BB1 and BB2 buttons lose 5 pixels of height. The height still follows the font.

**semantic-key:** PK-6007

## What stays

The preferred-minus-4 cut stays, and the word floor stays.

The 70 percent path stays. The red search x stays off the bar path. The app point size on a bar button stays. The focused pane ring stays. The SSH divider stays. Slices already locked stay locked. The open batch already in the working tree stays open.

**semantic-key:** PK-6008

## Edit

After holdShortLabel raises a short button to the word, it subtracts 5 pixels when the button carries aetheris.bar.

fitAetherisButton still sets that bar button to the preferred height minus 4 before the floor. Buttons that are not bar buttons do not lose the 5 pixels. The font on a bar button stays the app point size, so the settled height still grows and shrinks with the font control.

Rust stays.

**semantic-key:** PK-6009

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This land leaves the working set as it stands.

**Truth.** BB1 and BB2 lose 5 pixels of height after the word floor, and the settled height still follows the app point size on the font control. Buttons that are not bar buttons do not lose those 5 pixels. Rust stays.

**Reason.** holdShortLabel already raises a short label to the word. The five-pixel cut is applied there, and only when the button carries aetheris.bar, so the floor and the font still own the height. fitAetherisButton still uses preferred height minus 4 before that floor. The 70 percent path and the red search x stay off this cut.

**Avoidances.** Do not open a second revision. Do not retarget branch, action, stage, or the when field. Do not add a lambda, Lombok, JUnit, a G-key, a public type, a public method, a private method, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not take the 5 pixels from a button that lacks aetheris.bar. Do not drop the preferred-minus-4 cut, the word floor, the 70 percent path, the app point size, the focused pane ring, or the SSH divider. Do not put the red search x on the bar path. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.

**semantic-key:** PK-7905

# Tab text follows the control-bar font

The full cycle is named, and the build is named. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.

**semantic-key:** PK-6010

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. This row is not a feature. A formal unit test is not declared. An outside-list row is not added.

Branch, action, stage, and the when field are not retargeted.

The edit below is the build. Our types take no lambdas. Lombok stays out. JUnit stays out. There is no new G-key, no new public type, no new public method, and no new package. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited. The private method is seatPlateFont. The private field is platePoints.

The product tree compiles with tests skipped. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. Rust stays.

The product commit comes next. Its message states that the change is in the tree. Its message states that the change landed. Rust stays.

**semantic-key:** PK-7887

## Goal

The SSH and Aetheris tab text follows the control-bar font.

The open strip and the folded plates share that size.

**semantic-key:** PK-6011

## What stays

The control bar stays on applyBarFont, and the menu stays on applyMenuFont.

The canvas tab titles stay on the notebook. The link chip stays. The plate colors stay. The plate shape stays. The range stays 8 through 36. The bar height stays. Slices already locked stay locked, including the five-pixel bar buttons and the Roux session count already seated on the VT100 heatbar. The open batch already in the working tree stays open. SshClientApp stays unedited on this slice.

**semantic-key:** PK-6012

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This land leaves the working set as it stands.

**Truth.** The SSH word, the Aetheris word, and the product tab pane use sans serif at the control-bar size, clamped from 8 through 36, on the open strip and on the folded plates. The size survives a fold that installs a tab UI. Rust stays.

**Reason.** The tab words were not on the control-bar font. seatPlateFont is called from applyMenuFont, which already runs from the font spinner and the first menu seat, and showChrome calls it again after a fold so a new tab UI cannot keep a stale size. Clearing a stored button size lets the word grow. The canvas tab titles stay on the notebook, and the plate colors and shape stay.

**Avoidances.** Do not open a second revision. Do not edit SshClientApp on this slice. Do not retarget branch, action, stage, or the when field. Do not add a lambda, Lombok, JUnit, a G-key, a public type, a public method, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not leave the range 8 through 36, the bar height, the link chip, the plate colors, the plate shape, applyBarFont, or applyMenuFont. Do not move Rust until that move is named. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.

**semantic-key:** PK-7906

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. The one-transcript plan stays open for smoke. This row is not a feature. A law is not added. An outside-list row is not added. A glossary row is not added.

Branch, action, stage, and the when field are not retargeted.

The edit below is the build. Our types take no lambdas. Lombok stays out. JUnit stays out. There is no new G-key, no new public type, and no new package. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited. The public methods on existing types are relayArmedMoods, sshMoodLineFor, moodHeld, and setMoodHeld.

The product tree compiles with tests skipped. The Rust client crate is checked offline. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

One stamp covers this checkbox, the per-user flag, and the live VT100 count. The Java product commit follows. Its message states that those three changes are in the tree. The Rust product commit is next, with the same message. The crate parent is the Java commit. chrome.rs and SshClientApp.java stage only the named hunks. Its message states that those three changes landed. The proofs are shared because the slices land in one tree.

**semantic-key:** PK-7888

## Goal

The Mood modal paints SSH Mood Active.

When that box is on for the canvas user, the saved mood line is seated on every connected SSH pane of that user.

**semantic-key:** PK-6013

## Edit

AetherisNotebookPanel.openMood adds a checkbox labeled SSH Mood Active, to the left of Clear mood.

The box is selected from the canvas user flag. The box is enabled when that user name is non-empty. A toggle writes that user flag and relays. Approve, Clear mood, and the mood switch relay after the mood file is saved.

SshClientApp.relayArmedMoods walks the connected AMWP panes. The user is the vault name on the last request. The wanted line is sshMoodLineFor for that user. A match sends nothing. An empty want on a pane that was already told sends shell_mood_clear. A non-empty want sends shell_mood and the line. The frame is scope A, channel A, type data. A failed write leaves the held line so the next beat retries.

aimSession calls relayArmedMoods after the listener is aimed. paintFrameStatus passes the mood gate from sshMoodLineFor of the focused pane user.

The per-user file is the next slice. The live typing beat is the slice after that. This row does not add a crate-only change. The three slices land in one Java tree and one crate tree. The proofs are shared.

**semantic-key:** PK-6014

## Scope

Java is named on this row, and Rust already paints SSH Mood Active.

There is no side move on this slice. The shared mood file aetheris-mood.yaml stays. The per-user flags sit in aetheris-ssh-mood.yaml. That file is the store this batch asked for.

**semantic-key:** PK-6015

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. This row is not a feature. A law is not added. An outside-list row is not added. A glossary row is not added.

Branch, action, stage, and the when field are not retargeted.

The edit below is the build. Our types take no lambdas. Lombok stays out. JUnit stays out. There is no new G-key, no new public type, and no new package. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited. The public method is thinkCluster. The private methods are holdThink, parkThink, seatThink, and placeThink.

The product tree compiles with tests skipped. The Rust client crate is checked offline. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The Java product commit follows. Its message states that the change is in the tree. The Rust product commit is next, with the same message. The crate parent is the Java commit. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-7889

## Goal

The thinking spinner and its clock leave the crowded BB1 row.

Open, they sit to the right of the global font control. Folded, they sit at the far east of the main menu row. The user combo stays on BB1. A quiet deck hides the cluster.

**semantic-key:** PK-6016

## What stays

The connected-user combo stays on the east of BB1. A quiet deck still hides the cluster. The clock still follows the running card.

Java startSpin and stopSpin stay on the vault run. Rust footer_clock stays the clock. The mood dialog spinner stays. The SSH shell spinner stays. The group clock already seated stays. The font control stays where the Close Pane slice put it. Canvas, group, and pipe sends stay. The canvas-switch seat stays locked. The open batch already in the working tree stays open.

**semantic-key:** PK-6017

## Proof

The product tree compiles with tests skipped on the working tree. JAVA_HOME is the Zulu 26.0.2 JDK.

The Rust client crate is checked offline from its manifest, on the recorded chrome plus these seat edits. The named test was not executed. The test module does not compile on the recorded head. The window stays closed. The proof does not make a live POST. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. A clean checkout was not recompiled.

**semantic-key:** PK-7920

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

**Truth.** One thinking cluster leaves BB1. Open, it sits after the font control. Folded, it sits at the far east of the menu row. The user combo stays on the east of BB1. A quiet deck hides the cluster. The cluster is not painted twice. The named test is not run.

**Reason.** BB1 was crowded. The spinner and its clock belong with the font control when the bar is open, and at the far east of the menu when the bar is folded. east_reserve was reserving spinner width beside the combo, so the open footer drops that width and paints the combo only.

**Avoidances.** Do not open a second revision. Do not retarget branch, action, stage, or the when field. Do not add a lambda, Lombok, JUnit, a G-key, a public type, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not move the user combo off BB1. Do not show the cluster on a quiet deck. Do not paint the cluster twice. Do not change startSpin, stopSpin, footer_clock, the mood dialog spinner, the SSH shell spinner, the seated group clock, or the font control's place. Do not run the named test. Do not recompile a clean checkout as this proof. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.

**semantic-key:** PK-7907

## Approval

This sentence names the build. A key on an SSH pane writes the line and posts the edit. The count runs off the key. The newest edit is the one that runs. A result paints when its stamp is still current. Each pane keeps its own integer. The timer paints that integer. A click still measures on that call.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The product tree is compiled with tests skipped. No new JUnit class is added. The Rust client crate is not checked. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The product commit is SshTerminalPanel and the posts in SshClientApp. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-6018

## Goal

While a pane is in front, a key updates that pane's unsent line and posts one edit. The count runs off the key. The heatbar paints the integer stored on that pane. Another pane keeps its own integer.

**semantic-key:** PK-6019

## What stays

A canvas edit still posts off the cell key. A canvas click still measures on that call. The SSH token window still calls sshLiveCount on open. The formula stays. A held Roux integer is the base. An empty draft adds nothing. An unheld session counts the session text, and the draft when the text does not already end with it. Undo stays. History, paste, and the chords stay. The heatbar geometry stays. chrome.rs stays. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The next free number stays the number already next. The outside-list number already next stays next. No new G-key, vault column, channel, AMWP type, color key, library, or Cargo.toml edit is added. No glossary row is added. The match already named in the spec stays. aa-sdd-source-prose.md stays. application.yaml stays.

**semantic-key:** PK-6020

## The cause

sshLiveCount ran on the key, on the 200 ms timer, and on every status paint. The count walked the session text on the EDT. The canvas key already moved that walk off the key.

**semantic-key:** PK-6021

## The seat

SshEditQueue holds one pending post. offerSshEdit bumps the stamp and drops an unstarted post. The worker copies that pane on the EDT, counts off the EDT, and seats the integer on that pane when the stamp is still current. The heatbar paints when that pane is focused. The timer posts the focused pane and then paints the stored integer. It does not call sshLiveCount. paintFrameStatus paints the stored integer. A focus change posts the pane now in front. The private class is SshEditQueue. The private types are SshEdit and SshSeat. The package methods on the pane are liveCount and seatLiveCount. No new public type is added. No new public method is added.

**semantic-key:** PK-6022

## Proof

The product tree was compiled with tests skipped on the working tree before the product commit. That compile passed. The Rust client crate was not checked. The window stayed closed.

**semantic-key:** PK-6023

## Scope

No new public type is added. No new public method is added. No new library is added. Cargo.toml is not edited. No system line is added, removed, or reordered. The history file stays. The next free number stays the number already next. The outside-list number already next stays next.

**Truth.** A key on an SSH pane writes the unsent line and posts one edit. The count runs off the key. SshEditQueue holds one pending post. offerSshEdit bumps the stamp and drops an unstarted post. The worker copies the pane on the EDT, counts off the EDT, and seats the integer when the stamp is still current. Each pane keeps its own integer. The 200 ms timer posts the focused pane and paints the stored integer. It does not call sshLiveCount. paintFrameStatus paints the stored integer. A focus change posts the pane now in front. The private class is SshEditQueue. The private types are SshEdit and SshSeat. The package methods are liveCount and seatLiveCount. Rust stays.

**Reason.** sshLiveCount walked the session text on the EDT from the key, from the 200 ms timer, and from every status paint. The canvas key already posts its walk off the key. The SSH pane has to do the same, and each pane has to keep its own integer.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not change the paging memorandum, application.yaml, aa-sdd-source-prose.md, chrome.rs, or grid.rs. Do not add a G-key, a vault column, a channel, an AMWP type, a color key, a library, a Cargo.toml edit, a glossary row, a public type, a public method, or a JUnit class. Do not retarget branch, action, stage, or the when field. Do not move Rust or check the crate. Do not let the key, the timer, or paintFrameStatus call sshLiveCount for the live paint. Do not drop sshLiveCount from the token window open. Do not change the formula: a held Roux integer is the base, an empty draft adds nothing, and an unheld session counts the session text plus the draft only when the text does not already end with it. Do not let one pane overwrite another pane's integer. Do not paint a result whose stamp is no longer current. Do not change undo, history, paste, the chords, or the heatbar geometry. Do not stop a canvas click from measuring on that call. Do not add, remove, or reorder a system line. Do not change the history file. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-7925

# The shell launch enables native access

The stamp names the build. The restricted-method warning leaves the Java console. The launch passes `--enable-native-access=ALL-UNNAMED`. The IDE is closed. The run configuration takes the same flag. Java sources stay. Rust stays. Where this page is thinner than the locked row, the locked row wins.

**semantic-key:** PK-6024

## Approval

This sentence names the build. The Java shell launch passes `--enable-native-access=ALL-UNNAMED`. The IDE run configuration SshClientApp gains that VM parameter.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker.

The proof calls libraryLookup with the flag and without it. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The product tree is compiled with tests skipped.

The product commit is the three launchers and the two command pages. The IDE workspace file stays out. Its message states that the change is in the tree. Its message states that the change landed. The next marker stays that same marker.

**semantic-key:** PK-6025

## Goal

The console that starts SshClientApp no longer warns that SymbolLookup.libraryLookup is restricted.

**semantic-key:** PK-6026

## What stays

The class stays org.casserole.app.ui.ssh.SshClientApp. The launch stays java -cp plus LocalM2Classpath. It does not become exec:java. paintCaption stays. The two libraries stay user32 and dwmapi. The server launch stays. Rust stays. grid.rs stays. application.yaml stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The next free outside number stays the number already next. No new G-key, vault column, channel, AMWP type, or library is added. Cargo.toml is not edited. No glossary row is added. No new public Java type is added. No new public Java method is added. No new JUnit class is added. A law is not added.

**semantic-key:** PK-6027

## The cause

JDK 26 treats SymbolLookup.libraryLookup as restricted native access. SshClientApp is an unnamed module, loaded from the classes directory. The JDK prints four warnings and names `--enable-native-access=ALL-UNNAMED`. A future JDK blocks the call unless that access is enabled.

**semantic-key:** PK-6028

## The seat

The PowerShell shell launcher passes `--enable-native-access=ALL-UNNAMED` on the shell java line. The shell script passes the same flag. The stick launcher written by the install script passes the same flag ahead of `-Duser.home`. The two command pages that quote that launch carry the same flag. The IDE configuration SshClientApp sets VM_PARAMETERS to `--enable-native-access=ALL-UNNAMED`.

**semantic-key:** PK-6029

## Proof

A temp class calls SymbolLookup.libraryLookup("user32", arena) and exits. Without the flag, stderr contains the restricted-method warning. With `--enable-native-access=ALL-UNNAMED`, stderr does not contain that warning. The temp class is deleted. The window stays closed. The product tree compiles with tests skipped, and that compile passes. No live POST is made. A pool turn is not sent. A live SSH connect is not made.

**semantic-key:** PK-6030

## Prose

No new type is added. No new method is added. No source-prose anchor is added. paintCaption stays as filed.

**semantic-key:** PK-6031

## Scope

No side move is named on this plate. The three launchers, the two command pages, and the IDE VM parameter are the seat. The sibling is none. No system line is added, removed, or reordered. The doors do not gain, lose, or reorder a line.

**Truth.** The PowerShell launcher, the shell script, and the stick launcher pass `--enable-native-access=ALL-UNNAMED` to the shell JVM. The two command pages quote that flag. The class stays org.casserole.app.ui.ssh.SshClientApp. The launch stays java -cp plus LocalM2Classpath. A temp class that calls SymbolLookup.libraryLookup("user32", arena) warns without the flag and does not warn with it.

**Reason.** JDK 26 treats SymbolLookup.libraryLookup as restricted native access on an unnamed module and prints four warnings that name the flag. A future JDK blocks the call unless that access is enabled. The process loads from the classes directory and looks up user32 and dwmapi when it paints the Windows caption.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not change application.yaml, grid.rs, Rust, or the server launch. Do not add a G-key, a vault column, a channel, an AMWP type, a library, a Cargo.toml edit, a glossary row, a public Java type, a public Java method, a JUnit class, a law, or a source-prose anchor. Do not advance the next marker. Do not retarget branch, action, stage, or the when field. Do not change the class name. Do not turn the launch into exec:java. Do not change paintCaption. Do not drop user32 or dwmapi. Do not drop the flag from any of the three launchers or the two command pages. Do not place the stick-launcher flag after `-Duser.home`. Do not open the window. Do not leave the temp class behind. Do not send a live POST, send a pool turn, or open a live SSH session. Do not call Session::start or chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-6032

# A plain reply is one paragraph run

The stamp names the build. The Java VT100 paragraph wraps as one run. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.

**semantic-key:** PK-6033

## Approval

This sentence names the build. A plain reply is one paragraph run. A single line feed inside a paragraph becomes one space. A blank line stays a paragraph break. The package-private type is ParagraphFlow. The method is flow. FormatRouter flows the identity path before toCrlf. WordWrap calls flow before the width flush. The test is ParagraphFlowTest. InputPage stays. paintFull stays. parseColumns stays. Shift-Enter stays a hard break. grid.rs stays. Java moves. Rust stays. The product files are ParagraphFlow.java, FormatRouter.java, WordWrap.java, and ParagraphFlowTest.java.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. A crate run is not required because the Rust grid stays.

The product commit is those four Java files: ParagraphFlow.java, FormatRouter.java, WordWrap.java, and ParagraphFlowTest.java. grid.rs stays out. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-6034

## Goal

A plain reply is one paragraph run. A blank line stays a paragraph break.

**semantic-key:** PK-6035

## What stays

Rust wrap behavior stays. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The next free outside number stays the number already next. No new G-key, vault column, channel, AMWP type, or library is added. Cargo.toml is not edited. No glossary row is added. No new public Java type is added. No new public method is added. The package-private type is ParagraphFlow. The method is flow. The new test is ParagraphFlowTest. A law is not added. The spine pages stay. No decision block is added on this plate. InputPage stays. paintFull stays. parseColumns stays. Shift-Enter stays a hard break. ListRenderer still splits items before wrap. Identity does not call WordWrap. Structured handlers stay on their own renderers.

**semantic-key:** PK-6036

## The cause

A plain reply does not match the markdown detector. The identity path keeps every line feed as a hard CRLF. JediTerm treats that line feed as a hard break, so each sentence wraps on its own. Rust soft-wraps one run and rejoins only rows marked wrapped. A hard CRLF stays a break. InputPage and paintFull still emit a hard break for the editor and for Shift-Enter. Those bytes match a sentence line feed, so the join belongs on the formatter.

**semantic-key:** PK-6037

## The seat

A plain reply is one paragraph run. A single line feed inside a paragraph becomes one space. A blank line stays a paragraph break. The package-private type is ParagraphFlow. The method is flow. FormatRouter flows the identity path before toCrlf. WordWrap calls flow before the width flush. The test is ParagraphFlowTest. InputPage stays. paintFull stays. parseColumns stays. Shift-Enter stays a hard break. grid.rs stays. One side move is named. The formatter is the shared console. A plain reply's bytes change for both clients. The Rust grid file is not edited. It soft-wraps the one run it is now given. A blank line stays a break.

**semantic-key:** PK-6038

## Proof

The test is ParagraphFlowTest. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. A crate run is not required because the Rust grid stays. The folded bar failure stays outside this diff. The thinking-strip check stays outside this diff.

**semantic-key:** PK-6039

## Prose

No new public type is added. No new public method is added. The package-private type is ParagraphFlow. The method is flow. The new test is ParagraphFlowTest. The files are ParagraphFlow.java, FormatRouter.java, WordWrap.java, and ParagraphFlowTest.java.

**semantic-key:** PK-6040

## Side move

One side move is named. The formatter is the shared console. A plain reply's bytes change for both clients. The Rust grid file is not edited. It soft-wraps the one run it is now given. A blank line stays a break.

**semantic-key:** PK-6041

## Wire

The wire stanza stays empty. The doors do not gain, lose, or reorder a line.

**Truth.** A plain reply is one paragraph run. A single line feed inside a paragraph becomes one space. A blank line stays a paragraph break. The package-private type is ParagraphFlow. The method is flow. FormatRouter flows the identity path before toCrlf. WordWrap calls flow before the width flush. The product files are ParagraphFlow.java, FormatRouter.java, WordWrap.java, and ParagraphFlowTest.java. Java moves. Rust stays. grid.rs stays out. A plain reply's bytes change for both clients. The Rust grid soft-wraps the one run it is now given.

**Reason.** A plain reply does not match the markdown detector. The identity path keeps every line feed as a hard CRLF. JediTerm treats that line feed as a hard break, so each sentence wraps on its own. Those bytes match a sentence line feed, so the join belongs on the formatter.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not edit grid.rs. Do not change Rust wrap behavior. Do not make ParagraphFlow public. Do not add a public method. Do not call WordWrap from the identity path. Do not stop ListRenderer from splitting items before wrap. Do not move structured handlers off their own renderers. Do not change InputPage, paintFull, or parseColumns. Do not turn Shift-Enter into a soft break. Do not turn a blank line into a space. Do not leave a single line feed inside a paragraph as a hard break. Do not add a G-key, a vault column, a channel, an AMWP type, a library, a Cargo.toml edit, a glossary row, a law, or a decision block. Do not retarget branch, action, stage, or the when field. Do not skip ParagraphFlowTest. Do not require a crate run. Do not treat the folded bar failure or the thinking-strip check as part of this diff. Do not open the window, send a live POST, send a pool turn, or open a live SSH session. Do not call Session::start or chrome::run. Do not add, remove, or reorder a door line. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-6042

# Focused pane cursor ring

The pre-plan is locked, and a second revision is not opened. The full cycle is named, and the build is named, in the slice sentence itself. Java is named. Rust stays. The seats for the work are already filed on the locked row, and no extra cut is opened. Where this page is thinner than the locked row, the locked row wins. The formal plan is stamped from this page. Dependencies were filed before the landing was filed on the plan. The page carries a new-item mark.

**semantic-key:** PK-6043

## Slice

When more than one SSH pane is open, the focused pane wears a one-pixel cursor ring on every edge, and the terminal cannot cover that ring.

A single pane stays bare.

**semantic-key:** PK-6044

## What stays

Divider gray stays 0x3A3A3A, and the divider size stays 11.

One-touch arrows stay. The inspector split stays. A single pane stays bare. An unfocused pane stays bare. The stroke bytes stay 0x00D7FF. Slices already locked stay locked. The open batch already in the working tree stays open.

**semantic-key:** PK-8191

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This land leaves the working set as it stands.

**Truth.** When more than one SSH pane is open, the focused pane wears a one-pixel cursor ring on every edge that the terminal cannot cover. A single pane stays bare. An unfocused pane stays bare. Java is the named tree. Rust stays.

**Reason.** The ring has to survive whatever the terminal paints next, so the ring is a full edge around the focused pane rather than a stroke the terminal can cover. One pane has nothing to distinguish, so it stays bare.

**Avoidances.** Do not open a second revision. Do not move Rust. Do not open an extra cut. Do not change divider gray 0x3A3A3A, divider size 11, the one-touch arrows, the inspector split, or the stroke bytes 0x00D7FF. Do not ring a single pane or an unfocused pane. Do not add, remove, or reorder a system line. Do not disturb the working set.

**semantic-key:** PK-7908

# Bar buttons sit five pixels shorter

The pre-plan is locked, and a second revision is not opened. The full cycle is named, and the build is named, in the slice sentence itself. Java is named. Rust stays. The seats for the work are already filed on the locked row, and no extra cut is opened. Where this page is thinner than the locked row, the locked row wins. The formal plan is stamped from this page. Dependencies were filed before the landing was filed on the plan. The page carries a new-item mark.

**semantic-key:** PK-6045

## Slice

The BB1 and BB2 buttons lose 5 pixels of height, and the height still follows the font control.

**semantic-key:** PK-6046

## What stays

The preferred-minus-4 cut stays, and the word floor stays.

The 70 percent path stays. The red search x stays off the bar path. Slices already locked stay locked. The open batch already in the working tree stays open.

**semantic-key:** PK-8192

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This land leaves the working set as it stands.

**Truth.** BB1 and BB2 sit 5 pixels shorter, and the font control still drives the height. Java is the named tree. Rust stays.

**Reason.** The buttons should sit tighter without leaving the font. The preferred-minus-4 cut and the word floor already size the label, so those stay, and the five pixels are the only new cut.

**Avoidances.** Do not open a second revision. Do not move Rust. Do not open an extra cut. Do not drop the preferred-minus-4 cut, the word floor, or the 70 percent path. Do not put the red search x on the bar path. Do not add, remove, or reorder a system line. Do not disturb the working set.

**semantic-key:** PK-7909

# Tab text follows the control-bar font

The pre-plan is locked, and a second revision is not opened. The full cycle is named, and the build is named, in the slice sentence itself. Java is named. Rust stays. The seats for the work are already filed on the locked row, and no extra cut is opened. Where this page is thinner than the locked row, the locked row wins. The formal plan is stamped from this page. Dependencies were filed before the landing was filed on the plan. The page carries a new-item mark.

**semantic-key:** PK-6047

## Slice

The SSH word and the Aetheris word take the font size on the control bar.

The open tab strip and the folded plate buttons share that size.

**semantic-key:** PK-6048

## What stays

The control-bar labels, fields, and buttons stay on applyBarFont, and the menu stays on applyMenuFont.

The canvas tab titles stay on the notebook. The link chip stays. The plate colors stay. The plate shape stays. The range stays 8 through 36. Slices already locked stay locked, including the five-pixel bar buttons and the Roux session count on the VT100 heatbar. The open batch already in the working tree stays open.

**semantic-key:** PK-6049

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This land leaves the working set as it stands.

**Truth.** The SSH word and the Aetheris word follow the control-bar font, and the open tab strip and the folded plate buttons share that size. Java is the named tree. Rust stays.

**Reason.** One size should govern the tab words the reader sees open and the same words on a folded plate. The range 8 through 36, the plate colors, and the plate shape already belong to that control, so they stay.

**Avoidances.** Do not open a second revision. Do not move Rust. Do not open an extra cut. Do not take the control-bar labels, fields, or buttons off applyBarFont, or the menu off applyMenuFont. Do not move the canvas tab titles off the notebook. Do not change the link chip, the plate colors, the plate shape, or the range 8 through 36. Do not add, remove, or reorder a system line. Do not disturb the working set.

**semantic-key:** PK-7910

## Dependencies

The Java Mood modal was built with Clear, Approve, and Cancel. SSH Mood Active is seated on this row.

The status strip passed the mood gate as false. This row passes the focused pane user line. The server already consumes shell_mood and shell_mood_clear. Rust already paints SSH Mood Active. The shared mood file stays. The per-user file is the next slice. The live line is the slice after that. No new library is added. Cargo.toml is not edited. The one-transcript plan stays open for smoke.

The proof compiles the product tree with tests skipped and checks the Rust client crate offline. The window stays closed.

**semantic-key:** PK-6050

## Seat

The checkbox label is SSH Mood Active, and it sits to the left of Clear mood.

The box follows the canvas user. An armed user with the mood switch on and a saved line sends shell_mood to every connected AMWP pane of that user. A pane that already matches sends nothing. A clear goes only to a pane that was already told. A raw session is left alone. The console door order stays.

No new public type is added. The public methods on existing types are relayArmedMoods, sshMoodLineFor, moodHeld, and setMoodHeld.

**Truth.** SSH Mood Active sits to the left of Clear mood and follows the canvas user. An armed user seats shell_mood on every connected AMWP pane of that user, skips a pane that already matches, and clears only a pane that was already told. Rust stays on this row.

**Reason.** The modal lacked the checkbox, and the status strip was passing the mood gate as false. The server already consumes the mood frames, and Rust already paints the label, so the Java seat is the missing piece.

Do not add a public type, a library, or a Cargo.toml edit. Do not close the one-transcript smoke. Do not change the shared mood file or the console door order. Do not touch a raw session. Do not clear a pane that was never told. Do not open the window for the proof.

**semantic-key:** PK-6051

## Dependencies

The cluster is one spinner. BB1 holds it today, west of the user combo.

The open control row already ends at the font control. The folded row already holds the menu. No new library is added.

The proof compiles the product tree with tests skipped and checks the Rust client crate offline. The window stays closed.

**semantic-key:** PK-6052

## Seat

thinkCluster returns the spinner. holdThink remembers it. parkThink seats it after the font control.

seatThink and placeThink move that same cluster to the east of the folded menu row. The user combo stays on BB1. A quiet deck hides the cluster. The public method is thinkCluster. The private methods are holdThink, parkThink, seatThink, and placeThink.

**Truth.** One spinner leaves BB1 and sits after the font control when the bar is open, and at the far east of the menu row when the bar is folded. The user combo stays on BB1. A quiet deck hides the cluster.

**Reason.** BB1 was holding the spinner west of the user combo. The open row already ends at the font control, and the folded row already holds the menu, so the same cluster can move to those seats without a second spinner.

Do not add a library. Do not paint two clusters. Do not move the user combo off BB1. Do not show the cluster on a quiet deck. Do not mint a glossary row for NUT. Do not open the window for the proof.

**semantic-key:** PK-6053

## Dependencies

pushDraft writes the unsent line and runs the beat. The beat called feedComposerHeat, and that called sshLiveCount on the key. The 200 ms timer and paintFrameStatus did the same. The canvas key posts an edit and walks off the key. A click still measures on that call. The formula stays: a held Roux integer plus the draft, or the session text when no tally is held. Undo stays. chrome.rs stays. grid.rs stays. No new library is added. Cargo.toml is not edited. No new JUnit class is added.

The proof compiles the product tree with tests skipped. The window stays closed.

**semantic-key:** PK-6054

## Seat

One queue is the seat. The newest post runs. The key posts. The timer paints the stored integer and posts the focused pane. It does not count on the tick. The result seats the integer on that pane when the stamp is still current.

**semantic-key:** PK-6055

## Out of scope

Rust stays out. The canvas queue stays out. Undo stays out. Clear turns stays out. The token window's click path stays a direct measure. No new public type is added. No new public method is added.

**Truth.** One queue posts the SSH count off the key. The newest post runs. The timer paints the stored integer and posts the focused pane. It does not count on the tick. The integer seats on that pane when the stamp is still current. Each pane keeps its own integer. Java moves. Rust stays.

**Reason.** The beat called sshLiveCount on the key, and the 200 ms timer and paintFrameStatus did the same. The canvas key already posts its walk off the key. The SSH tab has to post the same way.

Do not move Rust, the canvas queue, undo, or Clear turns. Do not move chrome.rs or grid.rs. Do not add a library, a Cargo.toml edit, a JUnit class, a public type, or a public method. Do not let the timer count on the tick. Do not change the formula. Do not let a click stop measuring on that call. Do not turn the token window's click path into a posted edit. Do not paint a result whose stamp is no longer current. Do not open the window for the proof.

**semantic-key:** PK-6056

# The shell launch enables native access

The Java console stops warning that SymbolLookup.libraryLookup is restricted. The launch passes `--enable-native-access=ALL-UNNAMED`. Java sources stay. Rust stays. The formal plan is the page stamped from this sketch.

**semantic-key:** PK-6057

## Dependencies

SshClientApp calls SymbolLookup.libraryLookup for user32 and for dwmapi when it paints the Windows caption. The process is an unnamed module, loaded from the classes directory. JDK 26 prints the restricted-method warning and names the flag. The shell launch stays java -cp plus LocalM2Classpath. It does not become exec:java. The class stays org.casserole.app.ui.ssh.SshClientApp. No new public type is added. No new public method is added. No new JUnit class is added. application.yaml stays. grid.rs stays.

The proof calls libraryLookup once with the flag and once without it, from a temp class, and does not open the window. The product tree is compiled with tests skipped. The window stays closed.

**semantic-key:** PK-6058

## Seat

The PowerShell shell launcher, the shell script, and the stick launcher written by the install script pass `--enable-native-access=ALL-UNNAMED` to the shell JVM. The command pages that quote that launch carry the same flag. The IDE configuration SshClientApp gains the same VM parameter.

**semantic-key:** PK-6059

## Out of scope

A named module stays out. A change to paintCaption stays out. The server launch stays out. The Rust client stays out. grid.rs stays out. A smoke row stays out. A push stays out.

**Truth.** The three launchers and the command pages that quote them pass `--enable-native-access=ALL-UNNAMED`. Java sources stay. Rust stays.

**Reason.** JDK 26 warns that SymbolLookup.libraryLookup is restricted on the unnamed module that looks up user32 and dwmapi. The flag is what the warning names.

Do not move Java sources, Rust, grid.rs, paintCaption, the server launch, or application.yaml. Do not add a public type, a public method, or a JUnit class. Do not turn the launch into exec:java. Do not change the class name. Do not drop the flag from a launcher or a quoting command page. Do not file a smoke row. Do not open the window for the proof.

**semantic-key:** PK-6060

# A plain reply is one paragraph run

The Java VT100 paragraph wraps as one run. This page does not move that point. The formal plan is the page that names the build.

**semantic-key:** PK-6061

## What stays

No new public type is added. No new public method is added. The package-private type is ParagraphFlow. The method is flow. The new test is ParagraphFlowTest. No new library is added. Cargo.toml is not edited. InputPage stays. paintFull stays. parseColumns stays. Shift-Enter stays a hard break. ListRenderer still splits items before wrap. Identity does not call WordWrap. The Rust grid file stays. The window stays closed. The next free number stays the number already next. The next free outside number stays the number already next. The open batch already in the working tree stays open. The paste gap already seated stays. The wire stanza stays empty. The doors do not gain, lose, or reorder a line.

**Truth.** A plain reply is one paragraph run. A single line feed inside a paragraph becomes one space. A blank line stays a paragraph break. The package-private type is ParagraphFlow. The method is flow. FormatRouter flows the identity path before toCrlf. WordWrap calls flow before the width flush. The new test is ParagraphFlowTest. A plain reply's bytes change for both clients. The Rust grid file is not edited.

**Reason.** A plain reply does not match the markdown detector. The identity path keeps every line feed as a hard CRLF. JediTerm treats that line feed as a hard break, so each sentence wraps on its own. The join belongs on the formatter.

**Avoidances.** Do not treat this page as stamped. Do not edit the Rust grid file. Do not make ParagraphFlow public. Do not add a public method, a library, or a Cargo.toml edit. Do not call WordWrap from the identity path. Do not stop ListRenderer from splitting items before wrap. Do not change InputPage, paintFull, or parseColumns. Do not turn Shift-Enter into a soft break. Do not turn a blank line into a space. Do not close the open batch. Do not add, remove, or reorder a door line. Do not open the window.

**semantic-key:** PK-6062

## Goal

The close stores the product tab. The next open shows SSH and waits, or connects and then shows Aetheris.

**semantic-key:** PK-6063

## What stays

The frame rectangle, the exit question, the help dialog, the control that remembers the credential, the mux gate on the Aetheris tab, the canvas tabs, and the single startup pane stay. The air-gap word stays. `num-ctx` stays.

**semantic-key:** PK-6064

## Store

`SshClientProperties` gains `UI_PRODUCT_TAB` (`ui.product.tab`), `PRODUCT_TAB_SSH` (`ssh`), and `PRODUCT_TAB_AETHERIS` (`aetheris`). `applyDefaultsForMissingKeys` puts `ssh` when the key is missing. `getProductTab` returns `aetheris` when the stored word is that word, and `ssh` otherwise. `setProductTab` stores `aetheris` when the argument is that word, and `ssh` otherwise, then calls `save`.

**semantic-key:** PK-6065

## Click

`SshClientApp` listens to the product tab pane. A change while writes are allowed stores `aetheris` when the index is 1 and `ssh` otherwise, and clears the restore flag. A change while writes are suppressed returns.

**semantic-key:** PK-6066

## Exit

On Yes, read the selected index, suppress writes, clear the restore flag, call `setProductTab`, then `saveFrameState`, then dispose the sessions. On Cancel, return as today.

**semantic-key:** PK-6067

## Bounce

`refreshAetherisTab` still disables the Aetheris tab when the focused connector is missing or not muxed, and still selects SSH in that case. That select runs while writes are suppressed.

**semantic-key:** PK-6068

## Open

When `getProductTab` is `aetheris`, set the restore flag and post a runnable. The runnable returns when the flag is clear or the startup pane is already connecting or connected. Otherwise it calls `connectRequested`. `sessionConnected` on the focused session, after `refreshAetherisTab`, selects Aetheris when the restore flag is set and the tab is enabled. That select runs while writes are suppressed, then the flag clears. When the tab is still disabled, the flag stays set.

**semantic-key:** PK-6069

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** The close stores `ssh` or `aetheris`, a stored `ssh` waits for Connect, and a stored `aetheris` connects and then selects that pane once the tab is enabled.

**Reason.** The Aetheris tab cannot be selected while it is disabled, and a disconnect during exit would otherwise store `ssh` on the way out.

**Avoidances.** No new public type, no vault column, no live connect from this filing, and no store of a program-driven tab change.

**semantic-key:** PK-7938

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. No new channel. No new wire type. The new method is `CommandContext.writeModelStatus`. `ChatShell` implements it. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed.

The status command reports the selected family and model. The lines under it are that family's options.

**semantic-key:** PK-7816

## Goal

`status` in cmd mode names the model the next console turn will use, and the options stored for that family.

**semantic-key:** PK-6070

## What stays

The session lines stay. `getModelName` stays the Ollama id. Load, stop, and the OSC keep that id. The cell status line stays. Air-gap stays. `num-ctx` stays. The console turn stays the shared door already landed.

**semantic-key:** PK-6071

## Print

`writeModelStatus` reads the console session and writes the block. `family` is the provider word. `model` is `consoleTargetLabel`. Family `ollama` then writes `think`. An empty think is `off`. Family `grok` then writes `think`, `effort`, `send-effort`, `stream`, `burner`, and the two numbers when they are stored. An empty effort is blank. Family `pool` then writes `think` as `true` or `off`.

**semantic-key:** PK-6072

## Command

`status` calls `writeModelStatus` where it printed `getModelName`. The help line names the selected model. The description names the family and the options.

**semantic-key:** PK-6073

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** `status` prints the family, the selected target, and only the options stored for that family.

**Reason.** The old model line printed the Ollama id even when the next console turn would use Grok or Pool.

**Avoidances.** No new public type, no vault column, no new frame, and no change to the cell status line.

**semantic-key:** PK-7939

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. No new channel. No new wire type. The new method is `CommandContext.selectConsoleFamily`. `ChatShell` implements it. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed.

A bare `select-model` opens the modal. A family word switches that family and keeps its stored options.

**semantic-key:** PK-7817

## Goal

`select-model` on a raw VT100 switches `grok`, `ollama`, or `pool` and keeps the options stored for that family. A bare command still opens the modal.

**semantic-key:** PK-6074

## What stays

The OSC stays on the bare command. The stored options stay. `status` stays. The cell door stays. Air-gap stays. `num-ctx` stays.

**semantic-key:** PK-6075

## Word

`selectConsoleFamily` accepts `ollama`, `grok`, or `pool`. It sets the provider and writes `model` plus the target label. Any other word returns without a write. The stored fields stay.

**semantic-key:** PK-6076

## Command

A bare `select-model` sends the OSC and `Model Config`. One of the three words calls `selectConsoleFamily` and does not send the OSC. Any other argument throws the usage line.

**semantic-key:** PK-6077

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** One family word switches the console provider and leaves that family's stored options in place, and a bare command still opens the modal.

**Reason.** The operator on a raw VT100 needs to change family without the Swing modal, and without dropping the options last stored for that family.

**Avoidances.** No new public type, no vault column, no new frame, and no OSC on a family-word call.

**semantic-key:** PK-7940

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. No new channel. No new wire type. No new public method. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed.

Ctrl+Left and Ctrl+Right move the console cursor by word. Left and Right still move by character.

**semantic-key:** PK-7818

## Goal

Ctrl+Left and Ctrl+Right move the unsent console cursor by words. Left and Right stay one character.

**semantic-key:** PK-6078

## What stays

History, Home, End, Delete, and the plain arrows stay. A sealed paste stays atomic. The Swing key map stays. Air-gap stays. `num-ctx` stays.

**semantic-key:** PK-6079

## Sequences

`handleCompleteEscape` matches `ESC [ 1 ; 5 D` and `ESC [ 5 D` for one word backward, and `ESC [ 1 ; 5 C` and `ESC [ 5 C` for one word forward. A sealed paste and an open paste return before those matches.

**semantic-key:** PK-6080

## Step

A word character is a letter or a digit. Forward skips a non-word run, then consumes the next word, and lands on the first character after it. Backward steps onto the previous word and lands on its first character. The line redraws when the cursor changes.

**semantic-key:** PK-6081

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** The two Ctrl-arrow sequences move the unsent cursor by words, and the plain arrows stay one character.

**Reason.** The client already sends the sequences, so the line buffer has to step by word without a new Swing key map.

**Avoidances.** No new public method, no new key map, and no word step inside a sealed paste or an open paste.

**semantic-key:** PK-7941

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. No new channel. No new wire type. No new public method. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed.

A saved Aetheris tab waits for Connect. The tab takes focus after that connect.

**semantic-key:** PK-7819

## Goal

A saved Aetheris tab waits for the user's Connect, then takes focus.

**semantic-key:** PK-6082

## What stays

The stored word stays. SSH still reopens on SSH. The tab stays dark until the mux is up. The SSH-tab cancel stays. Air-gap stays. `num-ctx` stays.

**semantic-key:** PK-6083

## Open

When the stored word is `aetheris`, set the restore flag. Do not call `connectRequested`.

**semantic-key:** PK-6084

## Connect

The focused `sessionConnected` path already selects Aetheris when the flag is set. Leave that path.

**semantic-key:** PK-6085

## Exit

On Yes, when the flag is still set, do not write `ssh` from the visible SSH tab. When the flag is clear, write the visible tab. Set the suppress flag before dispose in both cases.

**semantic-key:** PK-6086

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** A stored `aetheris` waits for the user's Connect, and an exit while that wait is still set does not store `ssh` from the visible tab.

**Reason.** Connecting on open logged in before the operator picked the account, and the visible SSH tab during that wait is not the tab the operator closed on.

**Avoidances.** No new public method, no change to the stored words, and no connect call from the open path.

**semantic-key:** PK-7942

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the top strip stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. No new channel. No new wire type. `0x0100` stays unallocated. The shell writes the family word beside the console history. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. Java holds the core. The seated crate still paints the character rule. This row writes no crate.

The VT100 console returns on the last selected model. Pool stays pool. The word survives an app restart.

**semantic-key:** PK-7820

## Goal

The VT100 console returns on the last selected model. Pool stays pool. The word survives an app restart. Java holds the core.

**semantic-key:** PK-6087

## What stays

Cell thoughts stay on the notebook strip. The VT100 strip stays on the top. The vault think log stays the notebook log. The VT100 chrome file stays the height and the closed flag. Ollama and Grok options stay as they are. The terminal bytes stay channel T. Kind `t` on the stream channel stays the signal. The nil cell stays the console address. Close still dismisses the pane. Scroll focus stays. The sapphire stroke stays. The top strip stays. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6088

## Word

The seat is `ChatShell`. A session starts by reading `~/.casserole/{user}/console-model`. Version 1 carries the family word. The word is `ollama`, `grok`, or `pool`. A missing file leaves the family at ollama. A version other than 1 is left unread and is not overwritten. The dialog set and the shell `model` word both write that file. The next session reads it before the first turn. The dialog still asks the shell, so the modal shows the stored word. No new channel. No new wire type. No VT100 control code.

**semantic-key:** PK-6089

## Rust row

The row is written in the ledger, after the product commit. The match states the Java requirement in full. Java holds the core. The seated crate still paints the character rule. This row writes no crate. The crate move for this word waits until that move is named. No side move on this plate. This land leaves the working set as it stands.

**Truth.** The VT100 console returns on the last selected model, pool stays pool, and the word survives an app restart.

**Reason.** A new session has to read the family word before the first turn, or the console would open on ollama after a pool choice.

**semantic-key:** PK-6090

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the family word stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. No new channel. No new wire type. `0x0100` stays unallocated. The shell writes the chosen model beside the console history. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. Java holds the core. The seated crate still paints the character rule. This row writes no crate.

The VT100 console returns on the model chosen in Model Config. The choice survives an app restart.

**semantic-key:** PK-7821

## Goal

The VT100 console returns on the model chosen in Model Config. The choice survives an app restart. Java holds the core.

**semantic-key:** PK-6091

## What stays

The family word stays in the same file. Cell thoughts stay on the notebook strip. The VT100 strip stays on the top. The vault think log stays the notebook log. The VT100 chrome file stays the height and the closed flag. The terminal bytes stay channel T. Kind `t` on the stream channel stays the signal. The nil cell stays the console address. Close still dismisses the pane. Scroll focus stays. The sapphire stroke stays. The family word stays. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6092

## Model

The seat is `ChatShell`. A session starts by reading `~/.casserole/{user}/console-model`. Version stays 1. The file carries the family word and the chosen model. The lines are the Ollama id, the Grok id, think, effort, send effort, stream, temperature when it was set, max tokens when it was set, burner, and pool think. A missing file leaves the family at ollama and the model at the server default. A file that carries only the family word still reads. A version other than 1 is left unread and is not overwritten. The dialog set writes that file. The shell `model` word writes that file. A Grok turn that turns send effort off writes that file. The next session reads it before the first turn. The dialog still asks the shell, so the modal shows the stored model. The server process that is already running keeps the shell it loaded. A new server process writes the file and reads it on the next session. No new channel. No new wire type. No VT100 control code.

**semantic-key:** PK-6093

## Rust row

The row is written in the ledger, after the product commit. The match states the Java requirement in full. Java holds the core. The seated crate still paints the character rule. This row writes no crate. The crate move for this model waits until that move is named. No side move on this plate. This land leaves the working set as it stands.

**Truth.** The VT100 console returns on the model chosen in Model Config, and the choice survives an app restart.

**Reason.** The family word is already in the console file, and the chosen model has to ride in that same file before the first turn.

**semantic-key:** PK-6094

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. Do not retarget the branch, the current action, the stage, or the recorded when-stamp. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the named Java files. Neither commit is pushed. The crate stays. This row writes no crate. This landing does not name a crate edit. The crate move for this delta waits until that move is named.

The `v` toggle folding panel is the name for that pattern. A folding panel separates the control bar at the top from the VT100 and Aetheris panes. The control bar behaves the way the Rust Aetheris pane behaves. Java is named.

**semantic-key:** PK-7822

## Goal

A folding panel carries the control bar above the VT100 and Aetheris panes. Open paints `v`. Folded paints `^`. The bar follows the Rust control bar.

**semantic-key:** PK-6095

## What stays

A New Term split stays a split. The south status line stays. File, Tools, and Help stay in that order. Show Thinking stays on the plate in front. A closed thinking pane rises. A folded thinking pane opens. A pane that is already showing stays at its height. The log stays. The closed mark stays. A parked pane stays. Token Calc stays off. The copy chord stays. Ctrl-Shift-C still copies the console highlight. Ctrl-C stays the interrupt. The Aetheris strip stays as it is. The east name stays the user. The choice is not saved. Earlier finessing rows stay locked. The crate stays.

**semantic-key:** PK-6096

## Panel

The seat is the package-private `FoldingPanel` in `org.casserole.app.ui.ssh`. `SshClientApp` seats it in the center. The vertical control split leaves. `setJMenuBar` leaves. The same File, Tools, and Help bar is seated on the panel. `mark` returns `v` while the bar is open and `^` while it is folded. `openAfter` and `linkAfter` are the beats. Connect folds the bar and seats the link. Disconnect opens the bar and clears the link. A resize while the link is up folds the bar. A resize while the link is down leaves the mark. The mark toggles either way. The first span is stored and does not fold. Connecting leaves the bar and the link.

**semantic-key:** PK-6097

## Rows

Open, the menu sits at the top. The `v` mark sits on the west of the control fields. SSH and Aetheris stay on the tab strip under that. Folded, one row holds the `^` mark, the link word, SSH, Aetheris, a vertical divider, then File, Tools, and Help. The tab strip hides while the bar is folded. Opening the bar restores the look-and-feel tab strip. Exact `Connected` paints the short chip `Connected`. Any other link word uses the west line with an empty host. The south strip keeps the long line. A connect on the focused session folds the bar. A pane you switch to that is already connected folds the bar. The pane you are already on keeps the mark until the next beat. A disconnected pane opens the bar. An Aetheris control stays enabled only for a focused AMWP session. A folded SSH press still honors the restore mark.

**semantic-key:** PK-6098

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made. No JUnit. `Session::start` is not called. `chrome::run` is not called.

**semantic-key:** PK-6099

## Rust row

The crate already paints this bar. This landing does not name a crate edit. The crate stays unwritten for this delta. The match states the Java requirement in full. The neighbor lines stay. A New Term split stays a split. The south status line stays. File, Tools, and Help stay. Show Thinking stays. Token Calc stays off. The copy chord stays. The Aetheris strip stays. The east name stays the user. Side move. No side move on this plate. Wire stanza. Empty. No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** A folding panel carries the control bar above the VT100 and Aetheris panes, open paints `v`, and folded paints `^`.

**Reason.** The `v` toggle is the folding-panel pattern, and the control bar has to follow the bar the Rust Aetheris pane already paints.

**semantic-key:** PK-6100

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependencies are weighed here. The landing is filed on the plan. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the SSH and Aetheris product tabs, on `SshClientProperties`, on the Connect button, and on the mux gate. Style limits hold: no Lombok, no lambdas in our types, no new package, and no new public type.

**semantic-key:** PK-7966

## What this sketch is

The plan is cut from the close. A close remembers SSH or Aetheris. Aetheris connects on the next open and then shows that pane. The word lives beside the frame rectangle. It is not a vault column.

The close remembers the product tab. SSH returns to SSH. Aetheris returns to Aetheris after the startup pane connects.

**semantic-key:** PK-6101

## The slice

The operator closes the app on one of the two product tabs. The next open shows that tab. SSH stays disconnected until Connect. Aetheris connects with the fields already on the connection panel, and the pane is selected when the tab is enabled.

**semantic-key:** PK-6102

## What stays

The frame rectangle stays. The exit question stays. The help dialog stays. The control that remembers the credential stays. The Aetheris tab stays disabled until the focused session is muxed. Canvas tabs stay. One pane still starts. The air-gap word stays. `num-ctx` stays the configured integer. Earlier finessing rows stay locked.

**semantic-key:** PK-6103

## The weighing

The weighing lives in this section. The Aetheris tab cannot be selected while it is disabled. A disconnect during exit selects SSH. A write that listens to every selection change would store `ssh` on the way out and the next open would miss Aetheris.

Index 0 is SSH. Index 1 is Aetheris. The stored words are `ssh` and `aetheris`. The client file key is `ui.product.tab` on the store that already holds `ui.frame.x`. A missing word reads as `ssh`. A user change stores the word and writes the file. Yes on exit reads the selected index first, stores that word, then disposes the sessions. Cancel does not store. The bounce to SSH when the mux drops, and the restore select after connect, do not store.

An open of `ssh` shows the SSH tab. Connect is not called. An open of `aetheris` has the startup pane call the Connect path once. Host, port, user, the stored credential, and mode are the fields already filled. When that session enables the Aetheris tab, the tab is selected. The mux gate stays. A raw session leaves the tab disabled and leaves SSH showing. The stored word stays `aetheris` until a click or a confirmed exit on SSH. An empty credential uses the failure dialog Connect already shows. This row adds no prompt. A click on SSH during restore clears the waiting restore, so a later connect in that same open stays on SSH. Canvas tabs are the notebook canvases. This row does not store which canvas was showing. The existing save writes the client file. This row does not open that file and does not print it.

**semantic-key:** PK-6104

## The floor

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made. The product files are `SshClientProperties` and `SshClientApp`.

**Truth.** The stored word beside the frame rectangle is `ssh` or `aetheris`, and only a user click or a confirmed exit writes it.

**Reason.** A listener on every selection change would record the exit bounce to SSH and forget that the operator closed on Aetheris.

**Avoidances.** No vault column, no extra prompt for an empty credential, no print of the client file, and no memory of which canvas was showing.

**semantic-key:** PK-6105

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the console modal, on the shared door, on `StatusCommand`, on `ChatShell`, and on `CommandContext`. Style limits hold: no Lombok, no lambdas in our types, no new package, and no new public type.

**semantic-key:** PK-7975

## What this sketch is

The plan is cut from one print. `status` on cmd names the family and model the console door will use, and the options stored for that family. It is a print of session fields the shared door already stores. It is not a vault column. It is not a new frame.

`status` names the model the next console turn will use.

**semantic-key:** PK-6106

## The slice

The operator types `status` in cmd mode. The model line is the selected target. The family line names `ollama`, `grok`, or `pool`. The lines under those two are the options stored for that family.

**semantic-key:** PK-6107

## What stays

Chat, mode, conversation, region, the SSH user, the message counts, and roux stay. `getModelName` stays the Ollama id. `ollama load`, `ollama stop`, and the `select-model` OSC keep that id. The cell status line stays. Air-gap stays true. `num-ctx` stays. Earlier finessing rows stay locked.

**semantic-key:** PK-6108

## The weighing

The weighing lives in this section.

`model` prints `consoleTargetLabel`. Family `grok` does not print the Ollama id. Family `pool` prints `pool`. `getModelName` stays. Load, stop, and the OSC keep calling it. An empty Ollama think prints `off`. A stored level prints as stored. The Grok think line prints the stored checkbox. The console turn still does not copy that word onto the execute body. An empty effort prints blank. `send-effort` prints the stored boolean. `temperature` and `max-tokens` print only when those values are stored. The burner line prints the stored box. The console turn stays ephemeral, so the box does not open a hop. Pool think prints `true` only when the stored word is `true`. Any other word prints `off`. Chat, mode, conversation, region, the SSH user, the counts, and roux stay where they are.

**semantic-key:** PK-8005

## The landing

The landing is the plan's edit. `writeModelStatus` prints the block. `status` calls that method in place of the old model line.

**semantic-key:** PK-6109

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** The model line is the selected target, and the lines under the family are only that family's stored options.

**Reason.** Printing `getModelName` on every family showed an Ollama id the next turn might not call.

**Avoidances.** No vault column, no new frame, no hop opened by the burner box, and no copy of the Grok think word onto the execute body.

**semantic-key:** PK-7943

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the console modal, on the shared door, on the status print, on `SelectModelCommand`, on `ChatShell`, and on `CommandContext`. Style limits hold: no Lombok, no lambdas in our types, no new package, and no new public type.

**semantic-key:** PK-7976

## What this sketch is

The plan is cut from one shortcut. `select-model` with one family word switches that family and keeps the options stored for it. A bare command still opens the modal. It is a shortcut on the console session. It is not a vault column. It is not a new frame.

A family word switches the console. The stored options stay.

**semantic-key:** PK-6110

## The slice

The operator types `select-model` in cmd mode on a raw VT100. One word, `grok`, `ollama`, or `pool`, becomes the family. The options last stored for that family stay. A bare command still opens the modal on the client.

**semantic-key:** PK-6111

## What stays

The OSC stays on the bare command. The modal stays. `getModelName` stays the Ollama id. `status` stays. Air-gap stays true. `num-ctx` stays. Earlier finessing rows stay locked.

**semantic-key:** PK-6112

## The weighing

The weighing lives in this section.

The OSC and the `Model Config` line stay on the bare command. The client opens the modal. `grok`, `ollama`, or `pool` sets the provider. The command writes the model line. It does not send the OSC. The Ollama id, the Grok id, think, effort, send-effort, stream, temperature, max tokens, burner, and pool think stay. A second word or any other word is refused. The family stays. The modal stays closed. The shortcut does not need the Swing modal.

**semantic-key:** PK-8006

## The landing

The landing is the plan's edit. `selectConsoleFamily` sets the provider. `select-model` calls it for one family word.

**semantic-key:** PK-6113

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** One of `grok`, `ollama`, or `pool` sets the provider and writes the model line, and every other argument is refused.

**Reason.** A raw VT100 can switch family without opening Swing, and the options last stored for that family have to survive the switch.

**Avoidances.** No vault column, no new frame, no OSC on the family word, and no modal opened by a refused argument.

**semantic-key:** PK-7944

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on `ChatShell` and on the single-line editor. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7977

## What this sketch is

The plan is cut from one cursor step. Ctrl+Left and Ctrl+Right move the unsent console cursor by words. It is a cursor step on the line buffer. It is not a new key map in the Swing client. The client already sends the sequences.

Ctrl with an arrow walks a word. The arrow alone walks a character.

**semantic-key:** PK-6114

## The slice

The operator is editing a console line that has not been entered. Ctrl+Left moves to the start of the word. Ctrl+Right moves to the end of the next word. Left and Right still move one character.

**semantic-key:** PK-6115

## What stays

Plain arrows, Home, End, Delete, and history stay. A sealed paste stays atomic. The JediTerm key map stays. Air-gap stays true. `num-ctx` stays. Earlier finessing rows stay locked.

**semantic-key:** PK-6116

## The weighing

The weighing lives in this section.

`ESC [ 1 ; 5 D` and `ESC [ 5 D` step backward. `ESC [ 1 ; 5 C` and `ESC [ 5 C` step forward. A word is a letter or a digit. Space, hyphen, and underscore are boundaries. Forward lands at the end of the next word. Backward lands at the start of this word, or the previous word when it is already on a start. A sealed paste and an open paste ignore the keys. Left and Right stay one character.

**semantic-key:** PK-8007

## The landing

The landing is the plan's edit. `handleCompleteEscape` calls the two word steps and redraws when the cursor moves.

**semantic-key:** PK-6117

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** Ctrl with an arrow walks a word on the unsent line, and the arrow alone walks a character.

**Reason.** The client already emits the word-step sequences, so the shell only has to honor them on the line buffer.

**Avoidances.** No new Swing key map, no new public method, and no word step through a sealed or open paste.

**semantic-key:** PK-7945

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the product-tab close, on `SshClientApp`, and on `SshClientProperties`. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7978

## What this sketch is

The plan is cut from the open path. A saved Aetheris tab waits for Connect, then takes focus. The stored word stays. The form stays.

Aetheris waits. Connect is the user's click.

**semantic-key:** PK-6118

## The slice

The last close was on Aetheris. The next open shows the connection form and does not log in. The user picks the account and clicks Connect. When that session is up, Aetheris takes focus.

**semantic-key:** PK-6119

## What stays

The key `ui.product.tab` stays. The words `ssh` and `aetheris` stay. A close on SSH still reopens on SSH. The Aetheris tab stays disabled until the focused mux is up. A click on the SSH tab before Connect still stores `ssh`. Air-gap stays true. `num-ctx` stays. Earlier finessing rows stay locked.

**semantic-key:** PK-6120

## The weighing

The weighing lives in this section.

The restore flag is set. `connectRequested` is not called. The focused `sessionConnected` selects Aetheris when the flag is set and the tab is enabled. The username field stays as the client already shows it. A click on the SSH tab clears the wait and the stored word becomes `ssh`. Yes while waiting leaves the stored word. It does not write `ssh` from the visible SSH tab. Yes after focus writes the visible tab. A program bounce during dispose stays suppressed.

**semantic-key:** PK-8008

## The landing

The landing is the plan's edit. The open drops the connect call. The exit keeps the word while the flag is set.

**semantic-key:** PK-6121

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** Open sets the restore flag and does not connect, and Connect is the click that later gives Aetheris focus.

**Reason.** The operator has to pick the account on the form before a session starts, and the visible SSH tab during that wait must not overwrite the stored word.

**Avoidances.** No change to `ui.product.tab`, no login from open, and no write of `ssh` while the wait flag is still set.

**semantic-key:** PK-7946

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the console family and on the shell session start. The landing commits when the turn ends. A control this row adds carries a tooltip that says what it is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new public type, no new package, no new channel, and no new wire type.

**semantic-key:** PK-7979

## What this sketch is

The plan is cut from one row. The VT100 console returns on the last selected model. Java holds the core.

**semantic-key:** PK-6122

## The slice

The VT100 console returns on the last selected model. Pool stays pool. The word survives an app restart. A new session reads the word before the first turn. Java holds the core. The seated crate still paints the character rule. This row writes no crate. The crate move for this word waits until that move is named.

**semantic-key:** PK-6123

## What stays

Earlier finessing rows stay locked. The VT100 strip stays on the top. The notebook strip stays on the bottom. Ollama and Grok on the console stay as they are. The terminal bytes stay channel T. Scroll focus stays. The sapphire stroke stays. The top strip stays. No side move on this plate. This land leaves the working set as it stands.

**Truth.** The VT100 console returns on the last selected model, pool stays pool, and the word survives an app restart.

**Reason.** The console family and the shell session start are the seats that have to keep the last selected word.

**Avoidances.** No new public type, no new channel, and no crate from this row.

**semantic-key:** PK-6124

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the console model file and on the shell session start. The landing commits when the turn ends. A control this row adds carries a tooltip that says what it is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new public type, no new package, no new channel, and no new wire type.

**semantic-key:** PK-7980

## What this sketch is

The plan is cut from one row. The VT100 console returns on the model chosen in Model Config. Java holds the core.

**semantic-key:** PK-6125

## The slice

The VT100 console returns on the model chosen in Model Config. The choice survives an app restart. A new session reads that model before the first turn. Java holds the core. The seated crate still paints the character rule. This row writes no crate. The crate move for this model waits until that move is named.

**semantic-key:** PK-6126

## What stays

Earlier finessing rows stay locked. The family word stays in the same file. The VT100 strip stays on the top. The notebook strip stays on the bottom. The terminal bytes stay channel T. Scroll focus stays. The sapphire stroke stays. The family word stays. No side move on this plate. This land leaves the working set as it stands.

**Truth.** The VT100 console returns on the model chosen in Model Config, and the choice survives an app restart.

**Reason.** The console model file and the shell session start are the seats that have to keep the chosen model.

**Avoidances.** No new public type, no new channel, and no crate from this row.

**semantic-key:** PK-6127

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the control bar in `SshClientApp` and on the Rust control bar already in the crate. The landing commits when the turn ends. Generated source that moves in this landing places a marker on the publish ledger in the same turn, and the public spine carries that knowledge in public voice. Style limits hold: no Lombok, no lambdas in our types, no new public type, no new package, no new channel, and no new wire type.

**semantic-key:** PK-7981

## What this sketch is

The plan is cut from one row. A folding panel carries the control bar. The vertical control split leaves. A New Term split stays a split.

**semantic-key:** PK-6128

## The slice

A folding panel is the `v` toggle. Open paints `v`. Folded paints `^`. It is a toggle. The control fields sit with that mark. The menu sits at the top while the bar is open. The same menu sits on the folded row. Java moves. The crate already paints this bar.

**semantic-key:** PK-6129

## What stays

Earlier finessing rows stay locked. A New Term split stays a split. The south status line stays. File, Tools, and Help stay in that order. Show Thinking stays. Token Calc stays off. The copy chord stays. The choice is not saved. The crate stays.

**semantic-key:** PK-6130

## Side move

No side move on this plate.

**Truth.** A folding panel is the `v` toggle, and it separates the control bar at the top from the VT100 and Aetheris panes.

**Reason.** The control bar in `SshClientApp` has to behave the way the Rust Aetheris pane already behaves.

**Avoidances.** No new public type, no save of the choice, and no side move.

**semantic-key:** PK-8014

# The title bar matches the Rust caption

The window title bar matches the Rust window title bar.

**semantic-key:** PK-6131

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke for the bar-press plate or for this plate. Do not file a feature smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new private method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. `paintCaption` and `applyCaption` write the system default. `colorRef` stays.

Compile quietly and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Rust does not move. Tests are not run on this plate.

One commit of the named Java file records that the change is in the tree.

**semantic-key:** PK-8050

## Goal

The window title bar matches the Rust window title bar.

**semantic-key:** PK-6132

## What stays

The caption words stay. Immersive dark mode stays on. The letters stay I, E, P, and O. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. The hover emerald stays `#00FF66`. File, Tools, Token Calc, and Help stay. No yaml edit. No `Cargo.toml` edit. Rust stays.

**semantic-key:** PK-6133

## Edits

The seat is `paintCaption` and `applyCaption` on `SshClientApp`. Immersive dark mode stays on. Caption, border, and title text take the system default. That is the dark caption the Rust window leaves. The caption words stay. `colorRef` stays. No new private method.

**semantic-key:** PK-6134

## Land

Java is named. Rust stays.

The caption words stay. Immersive dark mode stays on.

No side move on this plate.

The cap is the window title bar matching the Rust window title bar.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** Caption, border, and title text take the system default. Immersive dark mode stays on. That is the dark caption the Rust window leaves. `colorRef` stays.

**Reason.** The Java title bar was not the system default the Rust window already leaves, so the two captions did not match.

**Avoidances.** Do not turn immersive dark mode off. Do not change the caption words. Do not drop `colorRef`. Do not add a private method. Do not move Rust. Do not edit yaml or `Cargo.toml`. Do not stage the card-round hunks. Do not file a feature smoke. This row is not a feature. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8088

# The host row flows and the buttons wrap under it

The master control bar flows like the Rust control bar, and the button bar sits on its own row under the host elements.

**semantic-key:** PK-6135

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke for the mark plate or for this plate. Do not file a feature smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. The private class is `FlowRow`. The private method is `flowLabel`.

Compile quietly and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Rust does not move. Tests are not run on this plate.

One commit of the named Java file records that the change is in the tree.

**semantic-key:** PK-8051

## Goal

The master control bar flows like the Rust control bar, and the button bar sits on its own row under the host elements.

**semantic-key:** PK-6136

## What stays

The Mode combo stays. The fold mark stays west. The font stepper stays on the host row. Title-case labels stay. File, Tools, Token Calc, and Help stay. No yaml edit. No `Cargo.toml` edit. Rust stays.

**semantic-key:** PK-6137

## Edits

The seat is `SshConnectionPanel`. The host row holds Mode, Host, Port, Username, Password, Remember password, and Font size. The button row holds Connect, Disconnect, Reconnect, Clear Terminal, Send file..., New Term, and Close Pane. Both rows flow left and wrap inside the frame width. The private class is `FlowRow`. The private method is `flowLabel`.

**semantic-key:** PK-6138

## Land

Java is named. Rust stays.

The Mode combo stays. The fold mark stays west.

No side move on this plate.

The cap is the master control bar flowing like the Rust control bar, with the button bar on its own row under the host elements.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The host row holds Mode, Host, Port, Username, Password, Remember password, and Font size. The button row under it holds Connect, Disconnect, Reconnect, Clear Terminal, Send file..., New Term, and Close Pane. Both rows flow left and wrap inside the frame width.

**Reason.** The master control bar did not flow like the Rust control bar, and the buttons were not on their own row under the host elements.

**Avoidances.** Do not move the Mode combo. Do not move the fold mark off the west. Do not move the font stepper off the host row. Do not drop title-case labels. Do not move Rust. Do not edit yaml or `Cargo.toml`. Do not stage the card-round hunks. Do not file a feature smoke. This row is not a feature. `FlowRow` and `flowLabel` stay private. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8089

# The app default font is twenty

The default app font size is 20.

**semantic-key:** PK-6139

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke for the button-row plate or for this plate. Do not file a feature smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. A stored size stays. The range stays 8 through 36.

Compile quietly and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Rust does not move. Tests are not run on this plate.

One commit of the named Java file records that the change is in the tree.

**semantic-key:** PK-8052

## Goal

The default app font size is 20.

**semantic-key:** PK-6140

## What stays

A stored font key stays. The range stays 8 through 36. Rust `FONT_DEFAULT` stays 14. File, Tools, Token Calc, and Help stay. No yaml edit. No `Cargo.toml` edit. Rust stays.

**semantic-key:** PK-6141

## Edits

`DEFAULT_FONT_SIZE` is 20. A missing or unreadable key uses that default. A stored size stays as written. The spinner range stays 8 through 36.

**semantic-key:** PK-6142

## Land

Java is named. Rust stays.

A stored size stays.

No side move on this plate.

The cap is the default app font size at 20.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `DEFAULT_FONT_SIZE` is 20. A missing or unreadable key uses 20. A stored size stays as written. The spinner range stays 8 through 36. Rust `FONT_DEFAULT` stays 14.

**Reason.** The Java app default was not 20. The Rust missing-key default stays 14, and a stored size is not rewritten.

**Avoidances.** Do not rewrite a stored font key. Do not change the range away from 8 through 36. Do not change Rust `FONT_DEFAULT` away from 14. Do not move Rust. Do not edit yaml or `Cargo.toml`. Do not stage the card-round hunks. Do not file a feature smoke. This row is not a feature. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8090

# The first start centers the shell

The first start centers the shell at a reasonable size.

**semantic-key:** PK-6143

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. The private methods are `seatOpeningFrame` and `usableScreen`.

Compile quietly and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Tests are not run. Rust stays.

One commit of the named Java file records that the change is in the tree.

**semantic-key:** PK-8053

## Goal

The first start centers the shell at a reasonable size.

**semantic-key:** PK-6144

## What stays

A saved rectangle that meets a screen stays. The default frame constants stay. A later resize still saves. No yaml edit. No `Cargo.toml` edit. Rust stays.

**semantic-key:** PK-6145

## Edits

When the saved rectangle is missing or misses every screen, `seatOpeningFrame` centers 1200 by 760 on the usable screen. `usableScreen` subtracts the taskbar insets. The size is capped to that screen, with a 32 pixel margin when the floor still fits. The floor is 640 by 480 when the screen allows. The size never exceeds the usable screen. A saved on-screen rectangle still uses `setBounds`.

**semantic-key:** PK-6146

## Proof

Compile quietly and skip tests. The window stays closed. The live POST is not made. A pool turn is not sent. A live SSH connect is not made. JUnit is not used. Tests are not run. Rust stays.

**semantic-key:** PK-6147

## Land

Java is named. Rust stays.

A saved on-screen rectangle stays.

No side move on this plate.

The cap is the first start centered at a reasonable size.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** A missing or fully off-screen saved rectangle is centered at 1200 by 760 on the usable screen. `usableScreen` subtracts the taskbar insets. The size is capped to that screen, with a 32 pixel margin when the floor still fits. The floor is 640 by 480 when the screen allows. The size never exceeds the usable screen. A saved on-screen rectangle still uses `setBounds`.

**Reason.** A first start with no saved rectangle, or a rectangle that misses every screen, did not center the shell at a reasonable size.

**Avoidances.** Do not replace a saved rectangle that meets a screen. Do not change the default frame constants. Do not stop a later resize from saving. Do not let the size exceed the usable screen. Do not move Rust. Do not edit yaml or `Cargo.toml`. Do not stage the card-round hunks. Do not file a smoke. This row is not a feature. Do not add a public type or a public method. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8091

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. `applyMenuBar` is a new public method on the existing theme. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

The quiet compile that skips tests passed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. No Rust.

One commit of the menu hover in `SshClientTheme.java` and the bar call in `SshClientApp.java` records that the change is in the tree.

**semantic-key:** PK-8054

## Goal

When the pointer is over a main menu item, the background shifts to a lighter grey tab button shape. Mirror that main menu look and feel on hover in the Java Aetheris app, for both open and collapsed and shifted menu shapes.

**semantic-key:** PK-6148

## What stays

The menu words stay the menu green. Button hover ink stays. The check mark stays off this plate. Rust stays. No `Cargo.toml` edit.

**semantic-key:** PK-6149

## Edits

The main menu bar paints a lighter grey tab, gray 70, corner 6, while the pointer is on a menu title or on Token Calc. Idle stays clear. The same bar is seated open, collapsed, and shifted. Context rows take the same tab.

**semantic-key:** PK-6150

## Proof

The quiet compile that skips tests passed. The window stays closed. The live POST is not made. A pool turn is not sent. A live SSH connect is not made. No Rust.

**semantic-key:** PK-6151

## Land

Java is named. Java moves. Rust stays.

The menu words stay the menu green.

No side move on this plate. The hover tab is the named look.

The cap is the lighter grey tab on hover for the open menu, the collapsed menu, and the shifted menu.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The main menu bar paints a lighter grey tab, gray 70, corner 6, while the pointer is on a menu title or on Token Calc. Idle stays clear. The same bar is seated open, collapsed, and shifted. Context rows take the same tab. `applyMenuBar` is a new public method on the existing theme.

**Reason.** The main menu hover did not yet show the lighter grey tab on the open, collapsed, and shifted shapes.

**Avoidances.** Do not change the menu words away from the menu green. Do not change button hover ink. Do not put the check mark on this plate. Do not add a public type or a package. Do not move Rust. Do not edit `Cargo.toml`. Do not stage the card-round hunks. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-8182

# The button bottoms clear the joint

The button bar on the bottom is being clipped by the joint between the panes. Add padding to the bottom of the control bar pane, the folding panel containing it, so the bottoms of the buttons clear by at least 2 px.

**semantic-key:** PK-6152

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

The quiet compile that skips tests passed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. No Rust.

One partial commit of `SshConnectionPanel.java` records that the change is in the tree.

**semantic-key:** PK-8055

## Goal

The button bar on the bottom is being clipped by the joint between the panes. Add padding to the bottom of the control bar pane, the folding panel containing it, so the bottoms of the buttons clear by at least 2 px.

**semantic-key:** PK-6153

## What stays

The button faces stay. The menu hover stays. The connect path stays off this plate. Rust stays.

**semantic-key:** PK-6154

## Edits

The connection panel, inside the folding panel, takes an empty bottom border of 4 px. The buttons clear the joint by at least 2 px.

**semantic-key:** PK-6155

## Land

Java is named. Java moves. Rust stays.

No side move on this plate. The 4 px bottom pad is the named clearance.

The cap is at least 2 px under the buttons.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The connection panel, inside the folding panel, takes an empty bottom border of 4 px. The buttons clear the joint by at least 2 px.

**Reason.** The bottoms of the button bar were clipped by the joint between the panes.

**Avoidances.** Do not change the button faces. Do not change the menu hover. Do not put the connect path on this plate. Do not use a pad thinner than the 4 px that yields at least 2 px of clearance. Do not move Rust. Do not stage the card-round hunks. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-8183

# Every About line is on the glass

Every About line is on the glass, including the copyright line.

**semantic-key:** PK-6156

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. There is no crate commit. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No `Cargo.toml` edit.

Compile quietly from the Java tree and skip tests. Rust stays, so the crate is not checked for this plate. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

One commit of the `showAbout` hunk in `SshClientApp` records that the change is in the tree. The ledger note names the Java commit.

**semantic-key:** PK-8056

## Goal

Every About line is on the glass, including the copyright line.

**semantic-key:** PK-6157

## What stays

`ABOUT_TEXT` stays, including DarkJediTerm. Rust `ABOUT_BODY` stays. Menu font stays. Modal ink stays. The search clear stays. The bar height stays the preferred-height-minus-4 cut. Six open bookings stay open. The bar-height plate stays locked. The SSH mux stays. Channel A stays. Rust stays.

**semantic-key:** PK-6158

## Edits

`showAbout` turns line wrap off. It splits `ABOUT_TEXT` on the newline. The row count is that line count. `FontMetrics` measures each line. The column count is the widest line in `m` widths, rounded up, plus one column so the last glyph clears the inset. The fixed 64 by 8 seat is replaced. The four-line clip is not the seat. No new method.

**semantic-key:** PK-6159

## Land

Java is named. Java moves. Rust stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `showAbout` turns line wrap off. The row count is the line count of `ABOUT_TEXT`. The column count is the widest line in `m` widths, rounded up, plus one column. The fixed 64 by 8 seat is replaced. The four-line clip is not the seat. No new method.

**Reason.** The About box was clipping lines, including the copyright line, on a fixed 64 by 8 seat.

**Avoidances.** Do not change `ABOUT_TEXT` or DarkJediTerm. Do not change Rust `ABOUT_BODY`. Do not leave line wrap on. Do not keep the 64 by 8 seat or the four-line clip. Do not add a method. Do not change the bar height off the preferred-height-minus-4 cut. Do not stage the banner, the connect path, the page-arm, the pane scroll take, the `map_key` flip, or the card-round hunks. Do not move Rust. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8184

# The main menu width follows the font

The main menu width follows the font. Every bar item stays visible. Every popup row stays visible.

**semantic-key:** PK-6160

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. There is no crate commit. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No `Cargo.toml` edit.

Compile quietly from the Java tree and skip tests. Rust stays, so the crate is not checked for this plate. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

One commit of the named hunks in `FoldingPanel` and `SshClientApp` records that the change is in the tree. The ledger note names the Java commit.

**semantic-key:** PK-8057

## Goal

The main menu width follows the font. Every bar item stays visible. Every popup row stays visible.

**semantic-key:** PK-6161

## What stays

The font still follows the control in both seats. Menu green stays. Hover emerald stays. The 5 pixel heatbar stays. The 640 floor stays. A smaller font does not shrink that floor. Six open bookings stay open. The composer-beat plate stays locked. The SSH mux stays. Channel A stays. Rust stays.

**semantic-key:** PK-6162

## Edits

Token Calc stores its preferred size as its maximum once, at install, before the live font. That cap is removed. `applyMenuFont` calls the private method `fitMenuWidth` after the font is seated.

`fitMenuWidth` clears the minimum, preferred, and maximum on each bar item, measures the live preferred size, and stores that size as the minimum and the preferred size. The maximum width is that width. The maximum height stays open so the item does not stretch across the bar. Each menu popup clears its preferred size and takes the new preferred size as its size. `setPopupSize` is not called.

When the menu width plus the preferred width of the controls that share its row, plus the frame insets, is wider than the frame, the frame grows to that width. The minimum width rises only when that sum exceeds the current minimum. The 640 by 480 floor stays.

**semantic-key:** PK-6163

## Land

Java is named. Java moves. Rust stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `fitMenuWidth` is private and runs after the font is seated. The Token Calc maximum cap is removed. `setPopupSize` is not called. The frame grows when the menu row is wider than the frame. The 640 by 480 floor stays. A smaller font does not shrink that floor.

**Reason.** The menu width was truncated by a fixed width stored before the live font.

**Avoidances.** Do not call `setPopupSize`. Do not leave the Token Calc maximum cap. Do not stretch a bar item across the bar. Do not shrink the 640 by 480 floor. Do not change menu green or hover emerald. Do not change the 5 pixel heatbar. Do not stage the banner, the connect path, the card-round hunks, the page-arm, the pane scroll take, or the `map_key` flip. Do not move Rust. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8185

# A second SSH pane wears the lighter divider

When more than one SSH pane is open, the terminal divider is the lighter gray. The focused pane wears the cursor stroke. A single pane stays bare. The inspector split stays.

**semantic-key:** PK-6164

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. The branch, the action, the stage, and the phase clock are not retargeted. Do not spend the land marker on the stamp.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No `Cargo.toml` edit.

Compile quietly from the Java tree and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

The product commit follows and records that the change is in the tree. The ledger note names the Java commit.

**semantic-key:** PK-8058

## Goal

When more than one SSH pane is open, the terminal divider is the lighter gray. The focused pane wears the cursor stroke. A single pane stays bare. The inspector split stays.

**semantic-key:** PK-6165

## What stays

The inspector split stays on the split ground. One-touch arrows stay. The divider size stays 11. Unfocused panes stay bare. No new color key. The divider bytes are `0x3A3A3A`. The stroke bytes are `0x00D7FF`. The arrow ink stays. The Cancel width stays. The heatbar count stays. The 5 pixel track stays. The composer-beat plate through the emerald-arrow plate stay locked. Six open bookings stay open.

**semantic-key:** PK-6166

## Edits

Java. `splitFocused` sets the client property `ssh.pane.split` on the new terminal split before `applySplitPane`. `DarkDivider` paints `SSH_DIVIDER` when that property is set. The inspector split does not set the property and keeps the split ground. `selectSession` calls the private method `markPaneFocus`. That method calls the package method `seatPaneFocus`. The focused pane paints a 1 pixel inside stroke after its children. A single pane stays bare. Unfocused panes stay bare.

Rust stays.

**semantic-key:** PK-6167

## Proof

Compile quietly and skip tests. The window stays closed. The live POST is not made by the proof. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

**semantic-key:** PK-6168

## Land

Java is named. Rust stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** When more than one SSH pane is open, `DarkDivider` paints `SSH_DIVIDER` at `0x3A3A3A`. The focused pane paints a 1 pixel inside stroke at `0x00D7FF`. A single pane stays bare. The inspector split keeps the split ground. The divider size stays 11. Rust stays.

**Reason.** Multiple SSH panes still used the dark split ground, and the focused pane had no cursor stroke.

**Avoidances.** Do not paint the lighter gray on a single pane. Do not paint it on the inspector split. Do not stroke an unfocused pane. Do not change the divider size away from 11. Do not add a color key. Do not change the arrow ink. Do not move Rust. Do not spend the land marker on the stamp. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8092

# The title bar uses the frame charcoal

The shell window title bar uses the theme's very dark frame charcoal.

**semantic-key:** PK-6169

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. The branch, the action, the stage, and the phase clock are not retargeted.

The edits below are the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

From the Casserole tree, compile quietly and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Rust does not move. Tests are not run on this plate.

One commit of the named Java file records that the change is in the tree. The proof on the papers is the product commit. Rust stays.

**semantic-key:** PK-8059

## Goal

The shell window title bar uses the theme's very dark frame charcoal.

**semantic-key:** PK-6170

## What stays

Min, max, and close stay. The content pane stays on the frame background. The import path stays. Earlier rows stay locked. Rust stays. No yaml edit.

**semantic-key:** PK-6171

## Edits

The seat is `SshClientApp`. `paintCaption` runs when the window opens and when the window state changes. On Windows it reads the native handle and paints the caption. Any other system returns. A missing handle returns. A failure is caught and the window still opens.

`applyCaption` sets the dark caption mode, then the caption color and the border color to the frame background, then the title text to the panel foreground. The colors are the theme colors already loaded. No new color key. The call uses the platform linker. No new library. The methods stay private. No new public type.

**semantic-key:** PK-6172

# Every VT100 pane uses the Apple bar

Every pane in the VT100 tab uses the Apple bar. The terminal strip was the old bar. It is the Apple bar.

**semantic-key:** PK-6173

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. The branch, the action, the stage, and the phase clock are not retargeted.

The edits below are the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. `AppleScrollBar` stays. `install` stays the pane arm. The package method is `armStrip`. `BAR_PX` stays the measure. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

From the Casserole tree, compile quietly and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Rust does not move. Tests are not run on this plate.

One commit of the named Java files records that the change is in the tree. The proof on the papers is the product commit. Rust stays.

**semantic-key:** PK-8060

## Goal

Every pane in the VT100 tab uses the Apple bar. The terminal strip was the old bar. It is the Apple bar.

**semantic-key:** PK-6174

## What stays

The wheel routing stays. The scroll policies stay. The thin width stays 2. The popped-out width stays 15. The colors, the opacities, the fade, and the corner radius stay the floating-bar measures already locked. The pane bars stay on `install`. Their preferred thickness stays zero. The thinking strip stays on the bar it has. The mood glass stays on its own bar. It is not a VT100 pane. The help text and the paste editor stay. Earlier rows stay locked. Rust stays. No yaml edit. A press on the bar does not take the caret. The bar is not a tab stop.

**semantic-key:** PK-6175

## Land

Java is named. The crate does not move.

The wheel routing stays. The scroll policies stay. The pane bars stay. The thinking strip stays. The mood glass stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** Every VT100 pane builds its strip through `AppleScrollBar.armStrip`, called from `DarkJediTermWidget.createScrollBar`, and that strip is the Apple bar at `BAR_PX` 15.

**Reason.** The VT100 panes still had the old style scroll bars. The terminal strip was that old bar, and every pane in the VT100 tab had to take the Apple bar instead.

**Avoidances.** Do not leave the old strip bar in place. Do not paint find ticks on the Apple bar. Do not drop the find highlight in the terminal. Do not move the thinking strip, the mood glass, the help text, or the paste editor onto this arm. The mood glass is not a VT100 pane. Do not change the pane bars off `install`, or their preferred thickness off zero. Do not change the thin width off 2 or the popped-out width off 15. Do not let a press on the bar take the caret. Do not make the bar a tab stop. Do not add a public type, a public method, a package, a color key, a library, a vault column, a channel, or an AMWP type. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.


**semantic-key:** PK-7865

# Help sits on the main menu bar

Help sits on the main menu bar.

**semantic-key:** PK-6176

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke for this plate, for the divider plate, or for the open-thought plate. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

From the Casserole tree, compile quietly and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Rust does not move. Tests are not run on this plate.

One commit of the named Java file records that the change is in the tree.

**semantic-key:** PK-8061

## Goal

Help sits on the main menu bar.

**semantic-key:** PK-6177

## What stays

File stays. Tools stays. Token Calc stays before Help. Show Thinking stays under Tools. Help... and About Aetheris... stay under Help. The order stays File, Tools, Token Calc, and Help. The folded row stays. The open divider stays. The words stay SSH, Aetheris, and Canvas. The Aetheris tab stays disabled until the mux answers. The grip stays. The thinking text stays clear. The emerald word stays. The tooltip wait stays. The plate fills stay. Font colors stay. No yaml edit. Rust stays.

**semantic-key:** PK-6178

## Edits

Help is already seated after Token Calc. Token Calc is a menu item on the bar. A menu item grows across the remaining width, so Help sat past the edge. The item maximum size becomes its preferred size. Help stays on the bar. Token Calc still opens the token window. The seat is `installMenu` in `SshClientApp`.

**semantic-key:** PK-6179

## Land

Java is named. The crate does not move.

File, Tools, and Help stay. Token Calc stays before Help.

No side move on this plate.

The cap is how Help returns to the visible bar. Token Calc still opens the token window.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** A menu item's maximum size is its preferred size, so Help stays on the bar after Token Calc. Token Calc still opens the token window.

**Reason.** Token Calc is a menu item on the bar. A menu item was growing across the remaining width, so Help sat past the edge.

**Avoidances.** Do not move Help off the bar. Do not change the order File, Tools, Token Calc, Help. Do not move Show Thinking out from under Tools. Do not move Help... or About Aetheris... out from under Help. Do not stop Token Calc from opening the token window. Do not file a smoke for this plate, the divider plate, or the open-thought plate. Do not move Rust. Do not edit yaml. Do not stage the card-round hunks. Do not add a public type, a public method, a package, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-7796 PK-7866

# The title bar takes the system default

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6180

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The window title bar matches the Rust window title bar. `paintCaption` and `applyCaption` write the system default. `colorRef` stays.

No Lombok. No lambdas in our types. No new public type. No new public method. No new private method. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8119

## Slice

The window title bar matches the Rust window title bar. Java moves. Rust stays.

**semantic-key:** PK-6181

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The caption words stay. Immersive dark mode stays on. The letters stay I, E, P, and O. The pin letter stays P. No yaml edit. Rust stays.

**semantic-key:** PK-6182

## Depends

`SshClientApp.java`.

**Truth.** The window title bar matches the Rust window title bar by taking the system default. Java moves. Rust stays.

**Reason.** The Java caption was not the system default the Rust window already leaves.

**Avoidances.** Do not turn immersive dark mode off. Do not change the caption words. Do not drop `colorRef`. Do not add a private method. Do not treat this row as a feature. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6183

# The control bar flows under a button row

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6184

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The master control bar flows like the Rust control bar, and the button bar sits on its own row under the host elements.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library. The private class is `FlowRow`. The private method is `flowLabel`.

**semantic-key:** PK-8120

## Slice

The master control bar flows like the Rust control bar, and the button bar sits on its own row under the host elements. Java moves. Rust stays.

**semantic-key:** PK-6185

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The Mode combo stays. The fold mark stays west. The font stepper stays on the host row. Title-case labels stay. No yaml edit. Rust stays.

**semantic-key:** PK-6186

## Depends

`SshConnectionPanel.java`.

**Truth.** The master control bar flows like the Rust control bar, and the button bar sits on its own row under the host elements. Java moves. Rust stays.

**Reason.** The Java host controls and the buttons were not yet two flowing rows.

**Avoidances.** Do not move the Mode combo, the west fold mark, the font stepper, or the title-case labels. Do not treat this row as a feature. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. `FlowRow` and `flowLabel` stay private. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6187

# Twenty is the app default

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6188

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The default app font size is 20. A stored size stays. The range stays 8 through 36.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8121

## Slice

The default app font size is 20. Java moves. Rust stays.

**semantic-key:** PK-6189

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. A stored font key stays. The range stays 8 through 36. Rust `FONT_DEFAULT` stays 14. No yaml edit. Rust stays.

**semantic-key:** PK-6190

# The first start centers the shell

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6191

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The first start centers the shell at a reasonable size. A saved on-screen rectangle stays. A later resize still saves.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library. A saved on-screen rectangle stays. A later resize still saves.

**semantic-key:** PK-8122

## Slice

The first start centers the shell at a reasonable size. Java moves. Rust stays.

**semantic-key:** PK-6192

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. A saved rectangle that meets a screen stays. The default frame constants stay. No yaml edit.

**semantic-key:** PK-6193

## Depends

`SshClientApp.java`.

**Truth.** The first start centers the shell at a reasonable size. A saved rectangle that meets a screen stays. The default frame constants stay. Java moves. Rust stays.

**Reason.** A first start did not center the shell at a reasonable size when the saved rectangle was missing or missed every screen.

**Avoidances.** Do not replace a saved rectangle that meets a screen. Do not change the default frame constants. Do not treat this row as a feature. Do not edit yaml. Do not stage the card-round hunks. Do not add a public type, a public method, a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-8196

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

When the pointer is over a main menu item, the background shifts to a lighter grey tab button shape. Mirror that look on the open menu, the collapsed menu, and the shifted menu. The menu bar is the same bar in all three seats.

No Lombok. No lambdas in our types. No new public type. No new package. `applyMenuBar` is a new public method on the existing theme. The menu words stay the menu green.

**semantic-key:** PK-8123

## Slice

When the pointer is over a main menu item, the background shifts to a lighter grey tab button shape. Mirror that main menu look and feel on hover in the Java Aetheris app, for both open and collapsed and shifted menu shapes. Java moves. Rust stays.

**semantic-key:** PK-6194

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The menu words stay the menu green. Button hover ink stays. The check mark stays off this plate.

**semantic-key:** PK-6195

## Side move

No side move on this plate. The hover tab is the named look.

**semantic-key:** PK-6196

## Depends

`SshClientTheme.java`. `SshClientApp.java`. The menu bar is the same bar when the control line is open, collapsed, and shifted.

**Truth.** The hover background is the lighter grey tab on the open menu, the collapsed menu, and the shifted menu. `applyMenuBar` is a new public method on the existing theme. The menu words stay the menu green. Java moves. Rust stays.

**Reason.** The Java main menu did not yet mirror the lighter grey tab on every menu shape.

**Avoidances.** Do not change the menu words. Do not change button hover ink. Do not put the check mark on this plate. Do not treat this row as a feature. Do not stage the card-round hunks. Do not add a public type or a package. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6197

# The button bottoms clear the joint

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6198

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The button bar on the bottom is being clipped by the joint between the panes. Add padding to the bottom of the control bar pane, the folding panel containing it, so the bottoms of the buttons clear by at least 2 px.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package.

**semantic-key:** PK-8124

## Slice

The button bar on the bottom is being clipped by the joint between the panes. Add padding to the bottom of the control bar pane, the folding panel containing it, so the bottoms of the buttons clear by at least 2 px. Java moves. Rust stays.

**semantic-key:** PK-6199

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The button faces stay. The menu hover stays.

**semantic-key:** PK-6200

## Side move

No side move on this plate. The 4 px bottom pad is the named clearance.

**semantic-key:** PK-6201

## Depends

`SshConnectionPanel.java`. The folding panel contains that pane.

**Truth.** Padding on the bottom of the control bar pane clears the button bottoms by at least 2 px. The named clearance is a 4 px bottom pad. Java moves. Rust stays.

**Reason.** The joint between the panes was clipping the bottoms of the buttons.

**Avoidances.** Do not change the button faces. Do not change the menu hover. Do not treat this row as a feature. Do not stage the card-round hunks. Do not add a public type, a public method, or a package. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6202

# Every About line is on the glass

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6203

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The About box was truncating the text. The layout is rearranged so all the text in the About box is rendered.

**semantic-key:** PK-8125

## Slice

The About box shows every line of `ABOUT_TEXT`. The copyright line stays one line. The box is wide enough for that line and tall enough for the line count.

**semantic-key:** PK-6204

## What stays

`ABOUT_TEXT` stays, including DarkJediTerm. Rust `ABOUT_BODY` stays. Menu font stays. Modal ink stays. The search clear stays. The bar height stays the preferred-height-minus-4 cut. Six open bookings stay open in the working tree. The bar-height plate stays locked. Rust stays.

**semantic-key:** PK-6205

## Wire

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The About box shows every line of `ABOUT_TEXT`. The copyright line stays one line. The box is wide enough for that line and tall enough for the line count. Rust stays.

**Reason.** The About box was truncating the text.

**Avoidances.** Do not change `ABOUT_TEXT` or DarkJediTerm. Do not wrap the copyright line. Do not change Rust `ABOUT_BODY`. Do not change the bar height. Do not stage the banner or the connect path. Do not move Rust. Do not treat this row as a feature. Do not unlock the bar-height plate.

**semantic-key:** PK-8156

# The main menu width follows the font

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6206

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The font size on the menu bar already works. The width is still truncated by a fixed width. The main file menu width becomes dynamic so every item stays visible on a font change.

**semantic-key:** PK-8126

## Slice

The main menu width follows the font. File, Tools, Token Calc, and Help stay fully visible on the bar. Every popup row stays fully visible. When that row is wider than the frame, the frame grows. The 640 floor stays.

**semantic-key:** PK-6207

## What stays

The font still follows the control in the open seat and the folded seat. Menu green stays. Hover emerald stays. The 5 pixel heatbar stays. Six open bookings stay open in the working tree. The composer-beat plate stays locked. Rust stays.

**semantic-key:** PK-6208

## Wire

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The main menu width follows the font. File, Tools, Token Calc, and Help stay fully visible. Every popup row stays fully visible. The frame grows when that row is wider than the frame. The 640 floor stays. Rust stays.

**Reason.** A fixed width was truncating the menu after the font changed.

**Avoidances.** Do not leave the fixed width. Do not shrink the 640 floor. Do not change menu green or hover emerald. Do not change the 5 pixel heatbar. Do not move Rust. Do not treat this row as a feature. Do not stage the banner, the connect path, or the card-round hunks. Do not unlock the composer-beat plate.

**semantic-key:** PK-8157

# A second SSH pane wears the lighter divider

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-8195

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

When multiple SSH panes are active, the divider drag bar wears the lighter gray the Rust VT100 bar already wears. The focused pane wears the cursor stroke. A single pane stays bare. The inspector split stays on the split ground.

**semantic-key:** PK-8127

## Slice

When more than one SSH pane is open, the terminal divider is the lighter gray `0x3A3A3A`. The focused pane wears a 1 pixel inside stroke in `0x00D7FF`. A single pane stays bare. The inspector split keeps the split ground.

**semantic-key:** PK-6209

## What stays

The inspector split stays. One-touch arrows stay. The divider size stays 11. Unfocused panes stay bare. The arrow ink stays. The Cancel width stays. The heatbar count stays. The 5 pixel track stays. The composer-beat plate through the emerald-arrow plate stay locked. Six open bookings stay open.

**semantic-key:** PK-6210

## Wire

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** More than one SSH pane paints the divider `0x3A3A3A` and a 1 pixel focus stroke `0x00D7FF`. A single pane stays bare. The inspector split keeps the split ground. The divider size stays 11. Rust stays.

**Reason.** The Java terminal divider did not yet wear the lighter gray the Rust VT100 bar already wears.

**Avoidances.** Do not paint a single pane. Do not move the inspector split off the split ground. Do not change the divider size. Do not stage the banner, the connect path, or the checkbox faces. Do not move Rust. Do not treat this row as a feature.

**semantic-key:** PK-8158

# The title bar takes the frame charcoal

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6211

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made.

The window title bar is a very dark charcoal that matches the window theme.

No Lombok. No lambdas in our types. No new public type. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8173

## Slice

The very dark charcoal is the frame background. The title words are the panel foreground. The native title bar stays, and it takes those colors. Java moves. Rust stays.

**semantic-key:** PK-6212

## What stays

Earlier rows stay locked. Min, max, and close stay. The content pane stays on the frame background. The import path stays. No yaml edit.

**semantic-key:** PK-6213

## Depends

`SshClientTheme` frame background and panel foreground. The shell window in `SshClientApp`.

**Truth.** The native title bar stays and takes the frame background as its very dark charcoal and the panel foreground as its title words.

**Reason.** The title bar was not painted in the window theme, so the caption did not match the frame.

**Avoidances.** Do not replace the native title bar. Do not add a color key or a library. Do not drop min, max, or close. Do not move the content pane off the frame background. Do not change the import path. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not add a public type, a package, a channel, or an AMWP type. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6214

# The VT100 panes take the Apple bar

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6215

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made.

The VT100 panes still have the old style scroll bars. The new Apple style scroll bars go on all instances of panes in the VT100 tab context.

No Lombok. No lambdas in our types. No new public type. No new public method. `AppleScrollBar` stays. `install` stays the pane arm. The package method is `armStrip`. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8174

## Slice

Every pane in the VT100 tab uses the Apple bar. The terminal strip was the old bar. It is the Apple bar. The thin pill stays 2. The popped-out width stays 15. Rust stays.

**semantic-key:** PK-6216

## What stays

Earlier rows stay locked. The wheel routing stays. The scroll policies stay. The pane bars stay on `install`. Their preferred thickness stays zero. The thinking strip stays on the bar it has. The mood glass stays on its own bar. It is not a VT100 pane. The help text and the paste editor stay. No yaml edit.

**semantic-key:** PK-6217

## Depends

`AppleScrollBar`. `DarkJediTermWidget`.

**Truth.** Every pane in the VT100 tab uses the Apple bar. The terminal strip is that bar. The thin pill stays 2 and the popped-out width stays 15.

**Reason.** The VT100 panes still had the old style scroll bars, including the terminal strip.

**Avoidances.** Do not leave any VT100 pane on the old bar. Do not move the thinking strip, the mood glass, the help text, or the paste editor. The mood glass is not a VT100 pane. Do not take the pane bars off `install`. Do not change their preferred thickness away from zero. Do not add a public type or a public method. Do not rename `install`. The package method is `armStrip`. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not add a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6218

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made.

Help sits on the main menu bar.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8175

## Slice

Help sits on the main menu bar. Java moves. Rust stays.

**semantic-key:** PK-6219

## What stays

Earlier rows stay locked. File, Tools, and Help stay. Token Calc stays before Help. The folded row stays. The open divider stays. No yaml edit. Rust stays.

**semantic-key:** PK-6220

## Depends

`SshClientApp`.

**Truth.** Help sits on the main menu bar. Java moves. Rust stays.

**Reason.** Help was not visible on the main menu bar.

**Avoidances.** Do not move Token Calc from before Help. Do not change File, Tools, or Help. Do not move the folded row or the open divider. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not add a public type, a public method, a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6221

# Every split divider is 11 pixels from one constant, and collapse remembers an open height only when the drag is at least that tall.

Files: `org.casserole.app.ui.ssh.SshClientTheme` and `org.casserole.app.ui.ssh.SshClientApp`.

1. Add `public static final int DIVIDER_SIZE = 11` on `SshClientTheme`.
2. `applySplitPane` calls `setDividerSize(DIVIDER_SIZE)` after `setUI`, so the constant is the last write. It stays the only `setDividerSize` in the tree.
3. The shell split, the session split, the notebook split, and every open cell split already call `applySplitPane`. They pick up 11. A collapsed card has no inner split and pays nothing.
4. `DarkDivider.paint` already fills the live rectangle and then paints the one-touch buttons. Leave that paint alone.
5. In `collapseControlPanel`, the open height is `connectionPanel.getPreferredSize().height + splitPane.getDividerSize()`. Remember `getDividerLocation()` only when it is at least that tall. Then set the location to 0. A reading of 0, a sliver, or a half-open drag leaves the saved expanded location alone.
6. `expandControlPanel` already floors at preferred height plus `getDividerSize()`. Leave that formula. It follows the constant.
7. Delete the literal `8` in `collapseControlPanel`. While a session is connected, a resize still sets the divider location to 0. Do not fold that park-at-zero rule into the constant.

Each open card pays the extra 3 pixels because the bar moved from 8 to 11. The one-touch arrows sit in that thicker bar. The extra pixels are hit area.

A position shorter than the open height, on expand, opens at the open height.

**Truth.** One constant, 11, is the only divider size, written after the split UI, and the open-height guard uses the live size rather than the literal 8.

**Reason.** The arrows were hard to grab on an 8 pixel bar. A sliver must not replace a real open panel the operator had dragged tall.

**Avoidances.** Do not leave a second `setDividerSize`. Do not keep the literal 8. Do not remember a location of 0 or a sliver as the expanded height. Do not change the park-at-zero rule while connected.

**semantic-key:** PK-6222

# The bottom status bar keeps today's message on the west and names the focused connected user on the east, and a later message does not erase the name.

File: `SshClientApp`. The frame status bar built in the constructor. Methods `aetherisStatus`, `selectSession`, `sessionConnected`, `sessionDisconnected`, and `setStatusDisconnected`.

1. Add a second label on the east of that same bottom bar. `statusLabel` stays on the west and keeps today's messages.
2. The east label shows the focused session's username when that session is connected, the connector is mux-capable, and the last request has a username. Otherwise the east label is empty.
3. `aetherisStatus` still sets only `statusLabel`. The user name remains.
4. Refresh the east label from `selectSession` and from the connect and disconnect paths.
5. The notebook footer label stays `vault:` plus the mode, `vault: disk` or `vault: ram`. It does not become the user name.
6. The user name uses the status line's existing foreground.

The west line still says Connected, Connecting, Disconnected, or a notebook message. Each of those writes replaces the west string only. The name survives the next message.

When the focused session is not a connected mux user, the name is absent.

**Truth.** A second east label carries the focused connected username and survives later west-side messages. The footer keeps the vault mode.

**Reason.** One label was being replaced wholesale, so a Run message erased the only place the operator could see which user was in focus.

**Avoidances.** Do not put the user name in the vault-mode label. Do not let `aetherisStatus` clear the east label. Do not name a user who is not a connected mux session.

**semantic-key:** PK-6223

# The button painter replaces the flat line with a raised bevel and keeps the 4 by 10 empty margin.

Inside `applyButton`, replace the flat line with a raised bevel. The border is a `CompoundBorder` of `BevelBorder(BevelBorder.RAISED, highlight, shadow)` and the same `EmptyBorder(4, 10, 4, 10)`. Highlight is lighter than `buttonBackground`. Shadow is darker. Fill, foreground, opaque, content-area filled, and focus-not-painted stay.

Callers already covered, and they keep today's height: shell Connect, Disconnect, Reconnect, Clear, Send file, New Term, Close pane, and the paste dialog's OK and Cancel.

Notebook callers are the short set. They receive this bevel and then the height cut.

The bevel is how a control reads as a button. It is not a color change.

**Truth.** Raised bevel, same 4-by-10 margin, same fills, and the named shell buttons keep their height.

**Reason.** A flat line the color of the field disappeared into the panel. A lighter highlight and a darker shadow are enough.

**Avoidances.** Do not drop opaque, filled, or focus-not-painted. Do not recolor the fill. Do not shrink Connect, Disconnect, Reconnect, Clear, Send file, New Term, Close pane, or paste OK and Cancel.

**semantic-key:** PK-6224

# Notebook buttons lose 40 percent of their height and every button the painter touches gains a raised bevel while its fill stays.

Files: `org.casserole.app.ui.ssh.SshClientTheme` and `org.aetheris.swing.AetherisNotebookPanel`.

`applyButton` today sets a flat `CompoundBorder` of `LineBorder(fieldBackground)` and `EmptyBorder(4, 10, 4, 10)`. `applyFontTree` then derives every button, toggle, label, checkbox, and combo to the live font size. There is no separate notebook height.

The bevel reaches every button that painter touches, shell and notebook. The height cut is the notebook set only. The two rules are split so a shell button does not shrink.

**Truth.** One painter gains a raised bevel for every button, and only the notebook set is shortened.

**Reason.** Flat borders did not read as buttons. The notebook buttons were too tall. The shell buttons were not the complaint.

**Avoidances.** Do not shorten the shell buttons. Do not change the fill colors. Do not leave the flat line border in place of the bevel.

**semantic-key:** PK-6225

# The tabbed pane lives in the existing client class, the footer has three actions, and the icon does not block the notebook.

- Put the tabbed pane inside the existing `SshClientApp`. The split tree stays in the terminal tab. Do not reparent the terminal widget into the canvas.
- The footer is Run, New turn, and Preview. There is no fourth Graph button.
- Collapse the connection strip on connect. Re-assert divider position 0 on resize while connected.
- Theme keys live on the client properties and the client theme. The host color helper paints the prompts.
- Generate the icon at plan or build time. Do not block the notebook on pixels.

Cursor default is translucent electric blue when focused, and a darker paler form when inactive. Menu foreground is matrix-rain green for the font only. `chat>` is green. `cmd>` is blue. The terminal foreground already stored is kept.

The icon is an Eye of Horus crossed with a swoosh, green, blue, and gold on black. A black square with the motif is acceptable if the pixels lag.

**Truth.** Tabs stay in the current client class, the footer is three buttons, the strip re-collapses on resize, and theme and icon follow the named defaults.

**Reason.** Reparenting the terminal widget would throw away the splits. A fourth button would be the graph surface this stage refused. Pixel work must not gate the notebook.

**Avoidances.** Do not reparent the terminal widget. Do not add a Graph button. Do not let a connected resize open the strip. Do not block the notebook on the icon.

**semantic-key:** PK-6226

# Both choosers share one size and one folder in the shell preference, and cancel still saves the size.

- Both doors call one helper. The terminal door still pastes. The notebook door still fills the user text.
- The memory lives in the shell preference `~/.casserole/ssh-client.yaml` through `SshClientProperties`. It is not a vault column. It is not `application.yaml`.
- Size is written when the dialog closes, including cancel.
- The folder is written when a file is approved, including when the later read refuses for size or for a NUL in the first 8 KiB.
- A missing folder opens at the platform default. A rectangle that misses the current screen opens at the platform default size, and the folder is kept when it still exists.
- An `OUTPUT_ONLY` cell still refuses before the chooser opens.
- The export door does not use this helper.
- Two vault users on one window share the chooser memory.
- `showOpenDialog` disposes its dialog before it returns, so the bounds come from that dialog window.

**Truth.** One size and one folder, in the shell file, shared by the paste door and the import door.

**Reason.** A fresh chooser made the operator walk back to the same folder after every file.

**Avoidances.** Do not store this on the cell or in application.yaml. Do not open the chooser for an output-only cell. Do not point the export door at this helper. Do not write the folder on cancel.

**semantic-key:** PK-6227

# One constant of 11 is the only divider size, and a sliver does not replace a saved open panel.

- One static constant, value 11, on the theme. The split helper is the only `setDividerSize`, and it sets the constant after the split UI is installed.
- The shell bar, the session bar, the notebook bar, and every open cell bar take it. A collapsed card has no inner bar. A split added later uses that same door.
- The divider paint already paints the live size and then the one-touch buttons. The extra 3 pixels, from 8 to 11, are hit area for those arrows. Each open card pays those 3 pixels.
- The open height is the connection panel's preferred height plus `getDividerSize()`.
- Collapse remembers the current position only when it is at least that tall. A reading of 0, a sliver, or a half-open drag leaves the saved height alone.
- Expand keeps its floor. A position shorter than the open height opens at the open height.
- While a session is connected, a resize still sets the divider location to 0. Do not fold that park-at-zero rule into the constant.
- The literal 8 in the collapse path drops out. Collapse must not keep treating a location above 8 as the expanded test once the constant is 11.

**Truth.** Thickness is 11. Open height is preferred panel height plus the live divider size. A sliver is not remembered.

**Reason.** The arrows were hard to grab on an 8 pixel bar, and a sliver must not replace a panel the operator had dragged tall.

**Avoidances.** Do not leave a second setDividerSize. Do not keep the literal 8. Do not remember 0 or a sliver. Do not change the park-at-zero rule while connected.

**semantic-key:** PK-6228

# The bottom frame status line names the focused connected user and keeps that name after the next message.

- The bar at the very bottom of the window carries the focused connected user.
- Notebook status and the connect messages may replace their own text. The user name remains on that bar.
- When the focused session is not a connected session that speaks the notebook pipe, the name is absent.
- The name uses the status line's existing foreground.
- The footer label stays `vault:` plus the mode, `vault: disk` or `vault: ram`.

**Truth.** The frame status names the user. The footer still names the vault mode.

**Reason.** Connect text used to replace the whole status string, which would erase the name.

**Avoidances.** Do not put the user name in the vault-mode label. Do not clear the name when a later status message arrives.

**semantic-key:** PK-6229

# Every Swing scrollbar shows a light gray arrow button with a black arrow at each end.

Every Swing scrollbar shows an arrow button at each end, vertical and horizontal. The face is light gray. The arrow is black.

The seats are the canvas, each open cell pane, the inspector, the View modal, and the shell text scrolls.

The terminal bar stays the terminal widget's own bar. The theme today sets `ScrollBar.background` and leaves the arrow buttons to the platform look. This note replaces that platform arrow with the light gray face and the black arrow on the Swing panes only.

**Truth.** Swing bars gain a light gray arrow button at each end. The terminal bar is untouched.

**Reason.** The platform arrows were not reliably visible.

**Avoidances.** Do not restyle the terminal scrollbar. Do not drop the vertical bar. Do not use a face other than light gray or an arrow other than black.

**semantic-key:** PK-6230

# The divider solution is accepted: constant 11, and a sliver cannot replace a real open panel.

The divider solution is accepted. Review of the pad continues.

The accepted solution is the divider note. The earlier line that moved the literal 8 onto the constant is closed by that note. Thickness is the constant 11. The open height is the connection panel's preferred height plus the live divider size. Collapse remembers a position only when it is at least that tall. Expand floors at that same figure. While connected, the bar still parks at 0. A sliver cannot take the place of a real open panel.

The split helper writes the constant once, after the split UI is installed. The shell bar, the session bar, the notebook bar, and every open cell bar read it.

**Truth.** 11 is accepted. The open-height test uses the live divider size. Park-at-zero stays.

**Reason.** A sliver must not overwrite the panel the operator had opened.

**Avoidances.** Do not keep the literal 8. Do not remember a sliver. Do not drop the park-at-zero rule.

**semantic-key:** PK-6231

# Both file choosers can remember one size and one folder, and a missing folder opens at the platform default.

The operator's words: for the file-select dialog used to import, whether the import is on the terminal or in the notebook pane into the conversation turn, persist the resized dialog. Also persist the last directory a file was imported from, so the next import from that folder does not require navigating there again.

Feasible. Both choosers are a fresh `JFileChooser` with no memory. The terminal chooser pastes the file into the terminal. The notebook chooser fills the turn. The dialog bounds and the last folder can be restored. One size and one folder serve both. A missing folder opens at the platform default. This note waits.

**Truth.** One size and one folder for both choosers. A missing folder uses the platform default.

**Reason.** Every import currently opens a fresh dialog in a fresh folder.

**Avoidances.** Do not give the two doors different memories. Do not fail when the saved folder is gone.

**semantic-key:** PK-6232

# Every split divider grows by 3 pixels under one constant, and the accepted constant is 11.

The operator's words: increase the width of all split-pane divider settings, bound by one global static constant, by 3 pixels, so the expand and collapse buttons are easier to locate. The note is updated to the accepted solution.

Accepted. One static constant is 11. The split helper is the only call to `setDividerSize`, and it sets that constant after the split UI is installed. The shell bar, the session bar, the notebook bar, and every open cell bar read it. Each open card pays the extra 3 pixels. The one-touch arrows sit in that thicker bar.

The open height is the connection panel's preferred height plus `getDividerSize()`. On collapse, the current position is remembered only when it is at least that tall. A reading of 0, a sliver, or a half-open drag leaves the saved height alone. On expand, a position shorter than that figure opens at the figure. While a session is connected, the bar still parks at position 0. The literal 8 drops out. This note waits.

**Truth.** Constant 11. Extra 3 pixels. Open height guards the remembered panel. A sliver is not saved.

**Reason.** An 8 pixel bar made the one-touch arrows hard to grab.

**Avoidances.** Do not leave a second setDividerSize. Do not remember a sliver. Do not drop the park-at-zero rule while connected. Do not keep the literal 8.

**semantic-key:** PK-6233

# Every scrollbar shows an arrow at each end, light gray with a black arrow.

The operator's words: make all scrollbar arrows on each end visible, just a light gray with black arrows.

Every scrollbar shows an arrow button at each end. The button face is light gray. The arrow is black.

That is the canvas, each open cell pane, the inspector, the View modal, and the shell text scrolls. Vertical and horizontal. The theme today sets `ScrollBar.background` and leaves the arrow buttons to the platform look. The terminal paints its own bar. Feasible. This note waits. No code until note-taking is done.

**Truth.** Light gray face, black arrow, both ends, Swing panes. The terminal bar stays its own.

**Reason.** The platform arrows were not reliably visible.

**Avoidances.** Do not restyle the terminal bar. Do not use a color other than light gray and black.

**semantic-key:** PK-6234

# The client frame defaults to 1024 by 968, the port defaults to 22, and the font spinner runs from 8 to 36.

Standalone from the host. `SshClientProperties` is `java.util.Properties`, UTF-8. Created with defaults on first run. This capture's store is `~/.ssh-client.properties`. A later design migrates it. Do not rewrite that fact here.

| Key | Default | Role |
|---|---|---|
| `ui.frame.width` and `ui.frame.height` | 1024 by 968 | Restored window size. Saved about 400 ms after resize. |
| `ui.connection.host` | `localhost` | |
| `ui.connection.port` | 22, not 2222 | The operator must type 2222 for this host, or persist it. |
| `ui.connection.username` | the operating-system user name | |
| `ui.connection.password` | absent | Stored only if Remember password is checked. Plain text. The value is not published. |
| `ui.connection.rememberPassword` | false | |
| `ui.connection.mode` | `vt100` | `vt100` or `pty`. |
| `ui.terminal.font.size` | 14 | Spinner range 8 to 36. |

Color defaults:

| Surface | Hex |
|---|---|
| Frame and split | `#1e1e1e` |
| Panel | `#2d2d2d`, foreground `#dcdcdc` |
| Field | `#1a1a1a`, foreground `#e6e6e6` |
| Button | `#3c3c3c`, foreground `#f0f0f0` |
| Status | `#171717`, foreground `#b4b4b4` |
| Terminal background and foreground | `#0d0d0d` and `#dcdcdc` |
| Selection | `#345a8c` and `#ffffff` |
| Cursor | `#e6e6e6` |

**Truth.** 1024 by 968, save near 400 ms, host localhost, port 22, font 14 in 8 to 36, and the nine color rows.

**Reason.** The client is a generic terminal first. Port 22 is its default. This host listens on 2222.

**Avoidances.** Do not change this capture's client default port to 2222. Do not store the password when Remember is off. Do not drop a hex.

**semantic-key:** PK-6235

# The panel persists host and port on focus loss, applies a font immediately, and confirms a mode change.

Fields: host, port, username, password, Remember password, a mode combo of VT100 or Standard PTY, a font-size spinner from 8 to 36, and the buttons Connect, Disconnect, Reconnect, Clear Terminal, and Send file.

- Persist host, port, user, and the remember flag, plus the password when remembered, on focus loss and on Connect or Reconnect. The password is plain text. The value is not published.
- Font size is persisted immediately. A font change mutates the live terminal font through the dark settings' terminal font size and a reinit of the font and the size. That reinit is exposed because the upstream method is protected.
- A mode change confirms. If connected, it reconnects. Cancel restores the previous mode.
- Send file is enabled only when connected.

The default port in this capture is 22. The operator types 2222 for this host. The default font size is 14.

**Truth.** Spinner 8 to 36. Font applies live. Mode change confirms and reconnects. Send file only while connected. Port default 22.

**Reason.** A mode change on a live session has to reconnect or the terminal type lies.

**Avoidances.** Do not persist a password when Remember is off. Do not enable Send file while disconnected. Do not change the default port to 2222 in this capture.

**semantic-key:** PK-6236

# The canvas uses Consolas, Menlo, or Monospaced, keeps 10000 lines of scrollback, and treats Ctrl+C as interrupt.

The dark terminal settings:

- Font: Consolas on Windows, Menlo on macOS, Monospaced otherwise. Size from the properties or the spinner. Default size 14. Range 8 to 36.
- The xterm palette. Custom default foreground, background, and selection from the properties.
- Hyperlink color purple, `rgb(180, 80, 255)`.
- Scrollback of 10000 lines.
- Paste keystrokes advertised as Ctrl+Shift+V and Ctrl+V. Ctrl+C remains interrupt at the shell, not copy.

The application intercepts Ctrl+V and Ctrl+Shift+V when the terminal is focused and opens the paste dialog instead of the widget's built-in paste.

The color defaults are frame and split `#1e1e1e`, panel `#2d2d2d` with foreground `#dcdcdc`, field `#1a1a1a` with `#e6e6e6`, button `#3c3c3c` with `#f0f0f0`, status `#171717` with `#b4b4b4`, terminal `#0d0d0d` with `#dcdcdc`, selection `#345a8c` with `#ffffff`, and cursor `#e6e6e6`.

**Truth.** Consolas, Menlo, or Monospaced. Scrollback 10000. Purple links. Ctrl+C is interrupt. Ctrl+V opens the dialog.

**Reason.** The widget's own paste would bypass the seal the server expects from this client.

**Avoidances.** Do not use Ctrl+C as copy. Do not let the widget paste when the terminal is focused. Do not drop the 10000-line scrollback.

**semantic-key:** PK-6237

# History up and down live in the shell, so a plain client and the bundled client stay equivalent.

History up and down is implemented in `ChatShell`, not in the window. The client is a dumb terminal. That keeps a plain client and the bundled client equivalent.

The file in this capture is `~/.casserole/history` on the host machine, shared across connections.

**Truth.** The client does not implement history. The shell does. Both clients match.

**Reason.** A client-side history would diverge from a plain client.

**Avoidances.** Do not implement up and down in the window. Do not give the two clients different history.

**semantic-key:** PK-6238

# The client store moves to ~/.casserole/ssh-client.yaml, and the old properties file is imported once.

Approved. Migrate to `~/.casserole/ssh-client.yaml`. On first run, import `~/.ssh-client.properties` once if that file is present.

The document holds host, port, the last username, and an account list. The design sample host is `localhost`. The design sample port is `2222`. Each account has a username. The password is stored in plain text only when Remember is on. An account that is not remembered keeps an empty password field. No password value is published. No sample account name is published.

The username control is an editable combo. Picking a known user fills the password if one is stored. Typing a new name adds that account on a successful connect when Remember is on. Plain text, the same honesty as today.

Persist accounts, the last user, host, port, and frame and colors. Do not persist the split tree.

The parser must be able to run without the host context, because the client starts from its own main. The plan states that constraint.

**Truth.** Shell file, one-time import, editable combo, plain-text password only when Remember is on, sample port 2222, no split tree.

**Reason.** The old properties file was a single-user store, and the new file has to remember more than one account.

**Avoidances.** Do not import the old file on every launch. Do not persist the split tree. Do not store a password when Remember is off. Do not publish a password.

**semantic-key:** PK-6239

# One font size refreshes every live pane, and the client does not add a second exclusive check.

| Item | Lock |
|---|---|
| Font size | One size for the window. A spinner change updates all panes. Keep the shared dark terminal settings. On change, refresh every live widget. Do not invent per-pane fonts. |
| Shells | One shell per pane. Never share a channel. |
| Same username | Disk-backed: the server exclusive rule, the red line. Memory-only: allowed. The client does not add a second exclusive layer. |
| Layout persist | None. Restart is one empty pane. |

**Truth.** One font, one shell per pane, server-only exclusive rule, no saved layout.

**Reason.** A per-pane font and a second exclusive check would diverge from the server rule.

**Avoidances.** Do not invent per-pane fonts. Do not share a channel. Do not persist the layout. Do not add a client exclusive layer.

**semantic-key:** PK-6240

# Every splitter is dark gray with a 1 pixel light outline, and the divider is about 8 pixels.

Approved. Every splitter, the connection-versus-terminals split and the nested terminal splits:

- Fill dark gray, `#3a3a3a` and `#2a2a2a`.
- A 1 pixel light-gray outline or a top and left highlight, `#8a8a8a`.
- A custom split divider, or the UI manager plus a border.
- `dividerSize` about 8.

One theme helper for all split panes.

This paper's divider is about 8. A later notebook tweak sets a constant of 11. Do not merge those two numbers in this file.

**Truth.** Fill `#3a3a3a` and `#2a2a2a`, 1 pixel `#8a8a8a`, divider about 8, one helper.

**Reason.** The platform divider did not match the dark window.

**Avoidances.** Do not replace the about-8 size in this paper with 11. Do not leave a splitter on the platform paint.

**semantic-key:** PK-6241

# File holds New Term, Close Pane, and Exit, and Help holds Help and About.

Approved. Menu plus shortcuts. A menu bar:

| Menu | Items |
|---|---|
| File | New Term…, the same dialog as the button. Close Pane. Exit. |
| Help | Help…. About. |

Split direction lives on the New Term dialog, not as two File items, so the menu matches the button.

- About: the application name, the sentence that this is the terminal chat client for the host proof, and a one-line note of the Java runtime and the terminal widget.
- Help: a scrollable dialog of client gestures. Connect, remember password, New Term, splits, hotkeys, paste, font, and the account combo. A short server blurb: Escape enters `cmd>`, type `help`, and `message list`, `verbose`, and `find by`. The terminal `help` stays canonical for commands.

No tray. No first-run wizard. The product name on the about item is the host's name.

**Truth.** Two menus. One New Term dialog. About is a one-liner. Help lists gestures and points at terminal help. No tray. No wizard.

**Reason.** Shortcuts stay primary. The menu is the cheap second door, and it must not fork the split direction.

**Avoidances.** Do not add two File items for direction. Do not duplicate the command book in Help. Do not add a tray or a wizard.

**semantic-key:** PK-6242

# The client reads and writes ~/.casserole/ssh-client.yaml, and a missing file imports the old properties once.

New or replace:

- Evolve `SshClientProperties`, or a sibling store, to read and write `~/.casserole/ssh-client.yaml` with the YAML library. Keep the getters the panel uses, plus a list of accounts.
- On load, if the shell file is missing and `~/.ssh-client.properties` exists, import once and write the shell file.

Edit:

- `SshConnectionPanel`. The username field becomes an editable combo. Selecting a known account fills the password if one is stored. Remember plus a successful connect appends or updates that account.
- Colors, frame, host, port, last username, and font stay in the same document.

The parser must work without a running host context, because `SshClientApp.main` starts the window alone. That constraint is repeated in the sequencing section.

Done: compile. A client launch migrates the old properties. The combo lists saved users. No password value is published.

**Truth.** One shell file, one-time import, editable combo, password filled only when stored.

**Reason.** The panel already had getters. The store changes shape without a host context.

**Avoidances.** Do not import the old file twice. Do not require the host process to parse the client file. Do not drop the frame, color, host, port, or font keys.

**semantic-key:** PK-6243

## One user run grows a sibling list

One `UserRun` per user keeps the connector and the answers, and a map of `CellRun` lets Stop follow the focused cell while a sibling stays in flight.

The closed rule stays one `UserRun` per user. The object keeps the connector and that user's answers. A second `UserRun` class is not added.

The footer cell id, the footer clock, and Stop follow the focused in-flight cell. One `UserRun` holds a map of `CellRun`. The phase is `RUNNING` or `STOPPED`. `armRun` does not erase a sibling that is still in flight. A `STOPPED` cell drops its own late frame.

The answer map stays keyed by cell. A late reply still lands on the cell that started it, and a matching Stop still drops the paint.

**Truth.** One `UserRun` per user holds the connector, the answers, and a map of `CellRun` whose phase is `RUNNING` or `STOPPED`. The footer follows the focused cell. A stopped cell drops only its own late frame.

**Reason.** One `cellId` and one `stoppedId` could show only one run, so a second cell's late reply painted and the footer clock stopped while a sibling was still in flight.

**Avoidances.** Do not add a second `UserRun` class. Do not let `armRun` erase a sibling. Do not drop a sibling's frame because another cell is `STOPPED`. Do not key the answer map by anything other than the cell that started the run.

**semantic-key:** PK-6244

## Crosstalk

Crosstalk is an operator action in both directions, and when nothing is highlighted N is 1.

A terminal selection, or the last N lines, becomes a new `EXCLUDED` cell when the operator asks. There is no automatic tag. When nothing is highlighted, N is 1. That line is the last line the terminal returned. A highlight still wins. The new cell starts `EXCLUDED`. The operator still has to ask.

A cell fence goes to the terminal on scope T when the operator asks and confirms. An ephemeral cell is never fenced to the terminal.

Terminal chat is not auto-imported. An optional import command beside this lock stays parked.

Two conversations stay two conversations until the operator joins them.

**Truth.** With no highlight, N is 1 and the line is the last line the terminal returned. A highlight wins. The new cell is `EXCLUDED`. A fence to the terminal requires a confirm and is never ephemeral. Terminal chat does not import itself.

**Reason.** An automatic join would mix two conversations, and an unnamed N would copy an arbitrary tail into the notebook.

**Avoidances.** Do not import terminal chat on its own. Do not fence an ephemeral cell to the terminal. Do not skip the confirm on the way to the terminal. Do not use a highlight and the last-line rule at the same time. The highlight wins. Do not start the new cell as included.

**semantic-key:** PK-6245

## SQL design serial

A design serial comes first, and this page does not invent a statement, a keyword, or a grammar.

The grammar is absent. The host mechanics are shared with the canvas. There is one router. The terminal's conversational style stays.

The design serial does not exist. Implementation may trail that serial. The serial waits. It is not in the first plan. The idea stays.

**Truth.** The SQL language is not in this plan. No statement, keyword, or grammar is invented here. The terminal stays conversational. One router is shared with the notebook.

**Reason.** Inventing the grammar inside this serial would ship a language that has no design page and would fork a second router.

**Avoidances.** Do not invent a statement, a keyword, or a grammar. Do not put the design serial in the first plan. Do not drop the idea. Do not add a second router for the terminal. Do not change the terminal's conversational style to make room for a language that does not exist.

**semantic-key:** PK-6246

## CellRun remembers Stop while a sibling is in flight

One `UserRun` holds a map of `CellRun`, and a `STOPPED` cell drops only its own late frame.

The finding is that `consumeStop` drops a late frame only when the single `cellId` is null. `armRun` replaces that id. `stopRequest` clears it and stops the clock.

`armRun` puts a `RUNNING` `CellRun` and sets the focused id. It does not remove a sibling. It does not clear a `STOPPED` mark on another cell.

Footer Stop sets the focused `CellRun` to `STOPPED` and sends `TYPE_CANCEL` for that id. Sibling runs stay `RUNNING`. The footer clock reads the focused cell. If the focused cell is `STOPPED` and another cell is `RUNNING`, the clock follows the focused id the operator moves to, and the spinner stays while any `RUNNING` cell remains.

`consumeStop` drops data, error, and end-of-file when that cell's phase is `STOPPED`. A sibling's phase does not matter.

A matching cancel writes `CANCELLED` on that cell and does not replace the assistant body.

The current `stopRequest` is replaced by this path. The one-cell stop is the same path with a map of one.

The seam is one `UserRun`. The map is the sibling list. No second run class.

**Truth.** `armRun` adds a `RUNNING` `CellRun` without erasing a sibling. Footer Stop marks the focused cell `STOPPED` and cancels that id. Late data, error, and end for that cell are dropped. The spinner stays while any cell is `RUNNING`. The clock follows the focused cell.

**Reason.** One `cellId` and one `stoppedId` paint a stopped cell's late reply as soon as another run makes the id non-null, and they stop the footer clock while a sibling is still in flight.

**Avoidances.** Do not add a second run class. Do not let `armRun` remove a sibling or clear another cell's `STOPPED` mark. Do not drop a sibling's frame because this cell is `STOPPED`. Do not replace the assistant body on cancel. Do not stop the spinner while any `RUNNING` cell remains. Do not keep the old `stopRequest` beside this path.


**semantic-key:** PK-6247
