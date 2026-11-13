# Module chrome

[Rust tree](../index.md) · **aetheris-client**

**Source** `rust/aetheris/aetheris-client/src/chrome.rs`

The window is the client screen. `egui` is the immediate-mode library that lays out the controls. `winit` is the library that opens the operating-system window. `wgpu` is the library that paints that window. This client does not use `eframe`, the frame library that wraps an `egui` window.

The plate is the tab strip. The plate name is the literal `Aetheris`. The window caption is `Aetheris Rust`. The password field is masked. The password is never copied into the title. The password is never copied into the status line. The function `run` is the only path that opens a window. The function `run` is the only path that loads the login file [`Door`](door/struct.Door.md) keeps. [`Door`](door/struct.Door.md) is the type that reads and writes that login file. `cargo test` does not call `run`.

The End key returns the view to the tail when the view is off the tail. The tail is the end of the terminal text. That return does not send a byte. When the view is already on the tail, End sends the sequence `ESC [ F`.

The console takes the keyboard on the first paint. The console takes the keyboard when a session opens. A click on the console takes the keyboard. A drag on the console takes the keyboard. Closing the console Model Config returns the keyboard to the pane that opened the modal. That return runs once. A later click in a login field keeps that field.

Esc is the byte `0x1B` on its own key. The host times that bare Esc. The host owns the prompt `cmd>`. The client sends the typed line and a CR. CR is the carriage-return byte. The command `select-model` stays on the host. The family word is `grok`, `ollama`, or `pool`.

While the console holds the keyboard, Escape stays on the console. While the console holds the keyboard, the arrow keys stay on the console. Tab still leaves the console. That lock is what lets the family word follow Esc.

The Aetheris tab stays disabled until the multiplexed channel answers. The session calls that channel the mux. The plate holds one canvas tab. That tab is labeled `Canvas`. The init body places `air-gap` and `num-ctx` on one line above the inspector. The cards start at the top of the canvas. New appends the next card. Each card has one letter. The letter is `I`, `E`, `P`, or `O`. A later Run keeps an earlier card by that letter. Clear leaves one empty Included card. Clear leaves the `air-gap` word and the `num-ctx` word. Run on the focused card sends that prompt unless the letter is `E`.

The inspector sits beside the cards. The inspector shows that same set of cards. The newest block is first. The inspector shows an estimated token count. The inspector does not send. The focused Included reply paints on that pane when the reply is in hand. The send keeps the focused prompt. A letter click keeps that card. A prompt takes the pane when the prompt is clicked. A prompt takes the pane when the prompt gains the keyboard. A prompt that already holds the keyboard does not take the pane back.

A redraw paints the keys already in hand. A redraw then commits a paint budget. The budget paints canvas pieces first. The budget then paints one terminal chunk. The rest stays queued.

Members of one group sit on a sideways row. Each member is as wide as its top bar plus 10 pixels. The merge stays the column width under that row. The block takes the column width. Every card wears a one-pixel line. The armed card's line is sapphire blue. A focused merge lights its group. The footer holds Run and Stop. Run sends the focused card. Stop cancels the card that is running. A later member of that group still runs. The spinner sits on the right of that bar while a card is running. The user combo sits further to the right. The clock shows that running card. A quiet deck hides the spinner. A quiet deck is a canvas with no card running. The combo stays.

Kind `t` appends to the session log above the footer. Kind `t` is a thought. The body is the lighter sapphire blue. A new block opens with a cyan model tag. The text stays off the card. `v` folds that text. `^` restores that text. Close hides the pane and keeps the log. Clear wipes the pane. Clear wipes the saved log. The north edge sets the height. New scrolls the canvas to the newest card.

The bar between the canvas and the inspector moves for this session. The share is the position of that bar. The share comes back the next time the window opens. An arrow hides one pane. The other arrow brings that pane back. The marks are filled buttons. A triangle sits in each button. The buttons stay on the bar. `P` sits on that bar while the pin bar is folded. `P` moves with the bar. The inspector column is closed when the bar is collapsed to the right. The inspector folds a line after the shell's column count. The shell is the saved layout for this window.

A saved Aetheris tab waits for Connect. That tab then takes the plate. That tab lands on the newest card. A disconnect while Aetheris is showing stores the word `aetheris`. That disconnect arms the same wait. The next connect returns to Aetheris. A close on SSH stores `ssh`. A close on Aetheris stores `aetheris`. A close while that wait is still armed leaves the stored word alone.

A restored close stores the size. A restored close stores the position. The next open places that rectangle. A maximized close keeps the last restored rectangle. A maximized close opens maximized. A missing maximized key stays maximized. The window opens maximized.

The connection bar starts open. Connect folds the connection bar. Disconnect opens the connection bar. `v` folds the fields. `^` brings the fields back. A resize while connected folds the connection bar. That choice is not saved. The status line stays. The plate tabs stay.

The zoom starts at three Ctrl-+ steps. The stream reply paints on the card that is running. Stop cancels that cell. Keys typed in a prompt are not sent. Returning to SSH arms the console once.

A failure on the wire opens the error notice at the top of the Aetheris pane. The body stays until you press Close. The Close control stays until you press Close. A hard failure is red. Any other failure is yellow. A named banner on an ordinary card stays on the status line. This notice is not a popup.

View opens the stored reply in a read-only window. The mark stays off until the reply has a character past a space. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this SSH user in the Swing bounds file. Swing is the Java library that draws the other client. The window stays inside the desktop view. A shrink stays. The title bar carries a close mark. The login file stays as it was.

Connect opens `aetheris.db` for this SSH user after the session starts. The first canvas loads. This thread writes that canvas on each paint. Disconnect writes that canvas before the cards are cleared. Close writes that canvas before the cards are cleared. The Java H2 file stays closed. H2 is the Java embedded database. Cloud sync stays off.

Burner marks the turn ephemeral. The prompt stays. The reply stays. A cloud run is denied. The status text is `burner denied`. An ordinary card leaves the notice closed. A group opens the yellow notice. The standing word stays `ollama`. The login file stays as it was.

The group row starts hidden. `v` shows the group row. `^` hides the group row. The choice stays on the card for this window. Group sits on that row. Join sits on that row. Ungroup sits on that row. The execution word stays with the model plate. The model plate is the control that names the model. The focused cell carries the local clock in its top stroke. The focused cell carries the short model name in that same stroke. Each side of that line is a one-pixel sapphire stroke. The words carry no box border. A gap above the line keeps it off the cell above. The first cell keeps that gap under the view. A primary click inside the cell border arms that cell. The prompt editor keeps the caret. A press on the prompt holds the mouse wheel on that pane. A press on the reply holds the mouse wheel on that pane. A press outside that pane returns the wheel to the canvas. The armed cell stays armed. A hover on a cell shows the local time. A hover on a cell shows the short model name. A quiet cell paints no line.

A thought of kind `t` on the nil cell opens that session's VT100 strip. The nil cell is sixteen zero bytes. No canvas card owns the nil cell. The strip slides down from the top of that pane. Pool uses the pool switch. Ollama uses the think string. Grok opens when `grok_think` is true. A dismissed Model Config keeps the think string. A right-click on the VT100 opens Import and Select model. The modal is the Model Config window this client already has. That window is drawn on the VT100. That window is drawn on the notebook. The notebook is the thinking strip. The shell keeps the file. The next session comes back on that family. The next session comes back on that model. The grip is the south edge. The Clear button owns its face. The console starts three pixels under that button. Dragging down grows the pane. Each SSH pane keeps its own height. Each SSH pane keeps its own fold. The file keeps the last height. The file keeps the closed mark. The text stays off the vault log. The vault log is the stored session record. The notebook strip stays above the footer.

The prompt ink is green. A reply that has text is yellow. A blank reply is gray. An excluded card is gray.

The tool bar starts hidden. `v` shows the tool bar. `^` hides the tool bar. That choice is not saved. Search sits on that row. Search selects the first turn whose prompt or reply contains the field. While `aetheris.db` is open, Search reads that file. An empty field matches nothing. A percent sign is an ordinary character. An underscore is an ordinary character. A burner stores empty payloads. That turn is not a file hit. Search does not change the card letter. The master row sits at the bottom of the canvas. The tool row sits at the bottom of the canvas. The two rows wear one rounded square border. The cells stack from the top of the scroll. A short deck leaves the empty space above the bar. A short deck is a canvas that does not fill the scroll. A new card still scrolls down to the newest cell.

Export sits on that row beside Search. The button writes this canvas to a version 1 `.aetheris` file. The chooser asks whether burner bodies are kept. Cancel writes nothing. The turns on this canvas stay.

Import sits on that row beside Export. The button reads a version 1 `.aetheris` file. The button appends that file's turns as copies. Each turn gets a new id. Group links are rewritten to the copies. Pipe pointers are rewritten to the copies. A pipe pointer is the text `{{cell`plus an id plus`.output}}`. A file over 1 MiB is refused. A bad version is refused. Cancel writes nothing. The turns already on this canvas stay.

Pull sits on that row beside Import. A highlight is copied when one is selected. Spaces in that highlight stay. Otherwise the last non-empty screen line is copied. The new turn starts Excluded. The prior turn's model stays on the prior turn. The login file stays as it was. The Turso file stays as it was. The Turso file is the database `aetheris.db`.

To terminal sits on that row beside Pull. The reply is sent when the turn has one. Otherwise the prompt is sent. A burner turn is refused. You confirm before the bytes are written. Cancel writes nothing. The login file stays as it was. The Turso file stays as it was.

Mood sits on that row after the switch. The button opens the glass for the selected card. The glass is the mood chat for that card. Enter sends the line on a new wire id. The card's running flag stays clear. The shaping call uses the card's burner bit. Think stream stays off that call. A reply that sets the string raises a pane above the footer. Approve saves the field. Cancel keeps the saved string. Cancel keeps the switch. The kept size is used the next time the glass opens. The place on screen is not kept.

Hide Collapsed sits on that row after Mood. Show Collapsed sits on that row after Mood. A divider sits before those two controls. Hide takes every folded card off the deck. Show puts those cards back. Those cards come back still folded. The choice is saved in `aetheris-hide.yaml`. That choice is not a vault column.

Collapse All keeps the one-line card on the deck. Expand All keeps the one-line card on the deck. The per-cell fold keeps the one-line card on the deck. Those three controls do not add an id to the conceal set. The conceal set is the set of card ids that Hide Collapsed stores. Hide Collapsed is the only path that adds an id. A later version of this window must not pass the fold bit into `shown_slots`. The function `shown_slots` chooses which cards stay on the canvas.

| | |
| --- | --- |
| **Specification** | L37 rust-aetheris-client.draft.001.md |
| **Author** | clayl (2026-10-23) |

## Type Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| struct | [`ThinkBlock`](chrome/struct.ThinkBlock.md) | The struct `ThinkBlock` is one thought in the session log. |
| struct | [`ThinkLog`](chrome/struct.ThinkLog.md) | The struct `ThinkLog` is the session thought log. |
| struct | [`ThinkChrome`](chrome/struct.ThinkChrome.md) | The struct `ThinkChrome` is the fold, the close, and the dragged height of a thought pane. |
| struct | [`ThinkStrip`](chrome/struct.ThinkStrip.md) | The struct `ThinkStrip` is the result of painting one thought strip. |
| struct | [`ThinkYaml`](chrome/struct.ThinkYaml.md) | The struct `ThinkYaml` is version 1 of the shared think file `aetheris-think.yaml`. |
| struct | [`VtThinkYaml`](chrome/struct.VtThinkYaml.md) | The struct `VtThinkYaml` is version 1 of `aetheris-vt100-think.yaml`. |
| struct | [`ThinkCellBlock`](chrome/struct.ThinkCellBlock.md) | The struct `ThinkCellBlock` is one cell inside the `cells` block of the think yaml. |
| struct | [`MoodYaml`](chrome/struct.MoodYaml.md) | Version 1 of the shared mood yaml. Width and height stay for the glass. |
| struct | [`MoodSlots`](chrome/struct.MoodSlots.md) | The struct `MoodSlots` holds the pixel heights inside the mood glass. |
| struct | [`MoodTurn`](chrome/struct.MoodTurn.md) | The struct `MoodTurn` is one line of the mood-glass chat. |
| struct | [`MoodDesk`](chrome/struct.MoodDesk.md) | The struct `MoodDesk` is the mood glass for one selected card. |
| enum | [`ClearChoice`](chrome/enum.ClearChoice.md) | The enum `ClearChoice` is the result of the clear-mood question. |
| struct | [`ButtonHoverInk`](chrome/struct.ButtonHoverInk.md) | The struct `ButtonHoverInk` is the button theme that paints emerald words on hover. |
| enum | [`MoodBegin`](chrome/enum.MoodBegin.md) | The enum `MoodBegin` is the gate in front of the mood glass. |
| struct | [`MoodPaintRow`](chrome/struct.MoodPaintRow.md) | The struct `MoodPaintRow` is one painted line of the mood-glass chat. |
| enum | [`MoodIntake`](chrome/enum.MoodIntake.md) | The enum `MoodIntake` is what the mood glass does with one incoming frame. |
| struct | [`ConsoleFocus`](chrome/struct.ConsoleFocus.md) | Keyboard focus for the console. The host still owns the line. |
| enum | [`Plate`](chrome/enum.Plate.md) | The enum `Plate` names which frame fills the window under the tab strip. |
| struct | [`PlatePick`](chrome/struct.PlatePick.md) | The struct `PlatePick` is one choice of the frame under the tab strip. |
| struct | [`CanvasFacts`](chrome/struct.CanvasFacts.md) | The struct `CanvasFacts` holds the words from one init body. |
| enum | [`Mark`](chrome/enum.Mark.md) | One letter on a card. A new card starts Included. |
| struct | [`TurnCard`](chrome/struct.TurnCard.md) | One in-memory card. The cell id lasts for the connect. |
| enum | [`GatherSlot`](chrome/enum.GatherSlot.md) | The enum `GatherSlot` is one paint slot on the canvas. |
| enum | [`TypedWrap`](chrome/enum.TypedWrap.md) | The enum `TypedWrap` is the result of reading a typed column count. |
| enum | [`PaneFace`](chrome/enum.PaneFace.md) | The enum `PaneFace` names which canvas text pane wears the optional frame. |
| enum | [`RunAsk`](chrome/enum.RunAsk.md) | The enum `RunAsk` is the decision for one Run press. |
| struct | [`HeatBeat`](chrome/struct.HeatBeat.md) | One heatbar beat. The queue keeps the newest pending beat only. |
| enum | [`HeatKind`](chrome/enum.HeatKind.md) | The enum `HeatKind` names which pane a heatbar beat describes. |
| struct | [`CanvasHeat`](chrome/struct.CanvasHeat.md) | The struct `CanvasHeat` is one canvas beat for the heatbar worker. |
| struct | [`VtHeat`](chrome/struct.VtHeat.md) | The struct `VtHeat` is one terminal beat for the heatbar worker. |
| struct | [`HeatPaint`](chrome/struct.HeatPaint.md) | The struct `HeatPaint` is the result of one heatbar beat. |
| struct | [`HeatSlot`](chrome/struct.HeatSlot.md) | The struct `HeatSlot` is the shared state of the heatbar worker. |
| struct | [`HeatInner`](chrome/struct.HeatInner.md) | The struct `HeatInner` is the lock and the wait signal shared with the heatbar worker. |
| struct | [`HeatQueue`](chrome/struct.HeatQueue.md) | The struct `HeatQueue` listens to heatbar edits on a background thread. |
| struct | [`InspectPane`](chrome/struct.InspectPane.md) | The struct `InspectPane` is one measured inspector view. |
| struct | [`SinkPiece`](chrome/struct.SinkPiece.md) | The struct `SinkPiece` is one row while `sink_inspect` chooses what to keep. |
| struct | [`TokenModel`](chrome/struct.TokenModel.md) | The struct `TokenModel` is one model row in the token window. |
| struct | [`TokenBudgets`](chrome/struct.TokenBudgets.md) | The struct `TokenBudgets` holds the two context windows read from `application.yaml`. |
| struct | [`MoodFit`](chrome/struct.MoodFit.md) | The struct `MoodFit` is the result of fitting one send into a frame. |
| struct | [`ColumnNotice`](chrome/struct.ColumnNotice.md) | The struct `ColumnNotice` carries the heights the column paints in this frame. |
| struct | [`ColumnBands`](chrome/struct.ColumnBands.md) | The struct `ColumnBands` holds the rectangles for one column frame. |
| struct | [`AppleSeat`](chrome/struct.AppleSeat.md) | The struct `AppleSeat` is the last frame of one apple bar. |
| struct | [`FooterHit`](chrome/struct.FooterHit.md) | The struct `FooterHit` is what the canvas footer asked for this frame. |
| struct | [`PinView`](chrome/struct.PinView.md) | The struct `PinView` is one frame of the pin. |
| struct | [`PinHit`](chrome/struct.PinHit.md) | The struct `PinHit` is what the pin row asked for this frame. |
| enum | [`FootPiece`](chrome/enum.FootPiece.md) | The enum `FootPiece` is one item on a footer row. |
| struct | [`ConnectedSeat`](chrome/struct.ConnectedSeat.md) | The struct `ConnectedSeat` is one SSH connection the pull-down can list. |
| struct | [`UserMenu`](chrome/struct.UserMenu.md) | The struct `UserMenu` is the pull-down list. |
| enum | [`UserMove`](chrome/enum.UserMove.md) | The enum `UserMove` is what a chosen name does to the vault on screen. |
| struct | [`SearchSeat`](chrome/struct.SearchSeat.md) | First hit on this canvas. The prompt and the reply are the text. |
| enum | [`KeepBurners`](chrome/enum.KeepBurners.md) | The burner question. Cancel, and any other word, writes nothing. |
| enum | [`SessionFile`](chrome/enum.SessionFile.md) | The Roux table from one `session_export_result:` body. A failure stays off the file. |
| struct | [`FiledCell`](chrome/struct.FiledCell.md) | One cell from a version 1 file. The parent, the branch, and the status are parsed and dropped. |
| struct | [`Halt`](chrome/struct.Halt.md) | The cell Stop cancels, and whether the next queued member should start. |
| struct | [`ExpandedYaml`](chrome/struct.ExpandedYaml.md) | Version 1 of `~/.casserole/aetheris-expanded.yaml`. Checked cell ids only. |
| struct | [`HideYaml`](chrome/struct.HideYaml.md) | Version 1 of `~/.casserole/aetheris-hide.yaml`. One window flag. |
| struct | [`SettingsYaml`](chrome/struct.SettingsYaml.md) | Version 1 of `~/.casserole/aetheris-settings.yaml`. Window switches. |
| struct | [`DeleteCut`](chrome/struct.DeleteCut.md) | The struct `DeleteCut` is one planned deletion of cards from the canvas. |
| struct | [`ErrorNotice`](chrome/struct.ErrorNotice.md) | One open notice. Close drops it. A later miss replaces the body. |
| struct | [`SlideSkin`](chrome/struct.SlideSkin.md) | The struct `SlideSkin` is the three colors of one error notice. |
| struct | [`MissBody`](chrome/struct.MissBody.md) | The struct `MissBody` is a parsed fault body from `parse_miss`. |
| struct | [`FaultSeat`](chrome/struct.FaultSeat.md) | The struct `FaultSeat` is the status text and the optional notice for one wire fault. |
| enum | [`PipeBody`](chrome/enum.PipeBody.md) | The enum `PipeBody` is the result of resolving `{{cell[k].output}}` tokens in one text. |
| enum | [`PipeMiss`](chrome/enum.PipeMiss.md) | The enum `PipeMiss` is why one chain card could not be framed. |
| enum | [`ChainAsk`](chrome/enum.ChainAsk.md) | The enum `ChainAsk` is the next action for a pipe chain. |
| enum | [`PipeWake`](chrome/enum.PipeWake.md) | The enum `PipeWake` is what the window should do after a chain card finishes. |
| struct | [`CommitWake`](chrome/struct.CommitWake.md) | The struct `CommitWake` is the result of draining the live session once. |
| enum | [`TextPane`](chrome/enum.TextPane.md) | A text pane that can hold the wheel. The canvas is no seat. |
| enum | [`ScrollPress`](chrome/enum.ScrollPress.md) | Where the press landed. A folded brief is a card press. |
| struct | [`ViewRect`](chrome/struct.ViewRect.md) | One saved rectangle. Swing's four keys. |
| struct | [`ViewSlots`](chrome/struct.ViewSlots.md) | The button row stays. The find bar yields when the view is short. |
| struct | [`ViewFind`](chrome/struct.ViewFind.md) | Find inside one reply window. The canvas search stays on the canvas. |
| struct | [`ViewPane`](chrome/struct.ViewPane.md) | The struct `ViewPane` is one open reply window. |
| enum | [`ImportRead`](chrome/enum.ImportRead.md) | The enum `ImportRead` is why reading an import file failed. |
| struct | [`ChooserReport`](chrome/struct.ChooserReport.md) | The struct `ChooserReport` is the bounds and the path printed by a file chooser. |
| struct | [`ImportOutcome`](chrome/struct.ImportOutcome.md) | The struct `ImportOutcome` is the result of one prompt import. |
| enum | [`MenuFollow`](chrome/enum.MenuFollow.md) | What the menu asks for after the grid fields move. |
| struct | [`MenuSeat`](chrome/struct.MenuSeat.md) | The pane fields the menu writes. The caller sends the clipboard and opens a URL. |
| struct | [`FrameSeat`](chrome/struct.FrameSeat.md) | The rectangle and the maximized flag held until close. |
| struct | [`OpenFrame`](chrome/struct.OpenFrame.md) | What the next open uses. `physical` means the size is in physical pixels. |
| enum | [`PasteDecision`](chrome/enum.PasteDecision.md) | What OK does with the editor body. |
| enum | [`PaneLink`](chrome/enum.PaneLink.md) | One pane's link. It follows the session, not a canvas word. |
| struct | [`ShellEnables`](chrome/struct.ShellEnables.md) | Buttons on the open bar. New Term and Clear stay on. Close needs a second pane. |
| enum | [`SplitWay`](chrome/enum.SplitWay.md) | Which way a split runs. Horizontal is side by side. |
| enum | [`PaneSide`](chrome/enum.PaneSide.md) | The `PaneSide` enum names which child a split path step follows. |
| enum | [`PaneNode`](chrome/enum.PaneNode.md) | The `PaneNode` enum is one node in the pane tree. |
| enum | [`Detach`](chrome/enum.Detach.md) | The `Detach` enum is the result of removing one leaf from a [`PaneNode`] tree. |
| struct | [`SplitBar`](chrome/struct.SplitBar.md) | The `SplitBar` struct is one draggable divider in a pane layout. |
| struct | [`SplitLayout`](chrome/struct.SplitLayout.md) | The `SplitLayout` struct is the rectangles for one pane tree. |
| enum | [`PromptBytes`](chrome/enum.PromptBytes.md) | The `PromptBytes` enum is the classified contents of a prompt file. |
| enum | [`ConsoleReadFail`](chrome/enum.ConsoleReadFail.md) | The `ConsoleReadFail` enum is why a console import did not return text. |
| enum | [`TabWord`](chrome/enum.TabWord.md) | The word written under `ui.product.tab`. |
| struct | [`TabStep`](chrome/struct.TabStep.md) | The `TabStep` struct is one change to the product tab. |
| struct | [`ConnectSnap`](chrome/struct.ConnectSnap.md) | The `ConnectSnap` struct is the connect fields last used for one pane. |
| struct | [`ParkedPane`](chrome/struct.ParkedPane.md) | The `ParkedPane` struct is one terminal that is not the pane in front. |
| struct | [`LoadedCanvas`](chrome/struct.LoadedCanvas.md) | The `LoadedCanvas` struct is one user's canvas read from the vault file. |
| struct | [`ParkedDeck`](chrome/struct.ParkedDeck.md) | One user's canvas while another user is on the glass. |
| enum | [`ScopeHome`](chrome/enum.ScopeHome.md) | Where a parked pane's canvas piece lands. |
| struct | [`DeckRefs`](chrome/struct.DeckRefs.md) | The `DeckRefs` struct is a mutable view of one canvas deck while scope is applied. |
| struct | [`FeedOut`](chrome/struct.FeedOut.md) | The `FeedOut` struct is what `feed_parts` asks the caller to do after one burst of scope. |
| struct | [`ParkedHit`](chrome/struct.ParkedHit.md) | The `ParkedHit` struct is a click or menu choice on a pane that is not in front. |
| struct | [`PasteDraft`](chrome/struct.PasteDraft.md) | The `PasteDraft` struct is the text open in the paste editor. |
| struct | [`ShellNotice`](chrome/struct.ShellNotice.md) | The `ShellNotice` struct is one modal notice over the terminal. |
| struct | [`App`](chrome/struct.App.md) | The `App` struct is the state of the client window. |
| enum | [`ControlBeat`](chrome/enum.ControlBeat.md) | A beat on the connection bar. Connecting is not a beat. |
| enum | [`TermStroke`](chrome/enum.TermStroke.md) | The enum `TermStroke` is one console input after the event list has been read. `Text` is characters to encode. `Key` is one terminal key. `Paste` is clipboard text waiting for the paste dialog. `Copy` copies the highlight and writes no byte. `Motion` is a console menu label that stays on this pane and writes no byte. |
| enum | [`PullAsk`](chrome/enum.PullAsk.md) | Empty leaves the deck. Seated is the index of the new Excluded card. |
| enum | [`TerminalAsk`](chrome/enum.TerminalAsk.md) | Quiet leaves the status. Burner is refused. Ready is the text to write. |
| enum | [`TerminalSeat`](chrome/enum.TerminalSeat.md) | Quiet leaves the status. Word is a status. Write is the full turn text. |

## Constant Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| const | [`CANVAS_TAB`](#constant-canvas_tab) | The one canvas tab on the empty plate. The vault name waits. |
| const | [`TURN_LABEL`](#constant-turn_label) | The first card. Later cards use [`turn_label`]. |
| const | [`RUN_LABEL`](#constant-run_label) | `RUN_LABEL` is the label on the Run button. The canvas footer shows that button. The hover text on that button is [`RUN_TIP`]. |
| const | [`STOP_LABEL`](#constant-stop_label) | `STOP_LABEL` is the label on the Stop button. The canvas footer shows that button beside Run. The hover text on that button is [`STOP_TIP`]. |
| const | [`RUN_TIP`](#constant-run_tip) | `RUN_TIP` is the hover text on the Run button in the canvas footer. The button sends the focused turn to its engine. A member of a group sends every member of that group. |
| const | [`STOP_TIP`](#constant-stop_tip) | `STOP_TIP` is the hover text on the Stop button in the canvas footer. Stop cancels the turn that is running. Other turns in a group keep running. |
| const | [`NEW_LABEL`](#constant-new_label) | `NEW_LABEL` is the label on the New button. The canvas footer shows that button. The hover text on that button is [`NEW_TIP`]. |
| const | [`CLEAR_LABEL`](#constant-clear_label) | `CLEAR_LABEL` is the label on the Clear button. The canvas footer shows that button. The hover text on that button is [`CLEAR_TIP`]. |
| const | [`DELETE_LABEL`](#constant-delete_label) | `DELETE_LABEL` is the label on the Delete button. The canvas footer shows that button. The hover text on that button is [`DELETE_TIP`]. |
| const | [`DELETED_TURN`](#constant-deleted_turn) | `DELETED_TURN` is the status text after Delete removes one turn. The status line shows this text. A group delete uses [`DELETED_GROUP`] instead. |
| const | [`DELETED_GROUP`](#constant-deleted_group) | `DELETED_GROUP` is the status text after Delete removes a whole group. The status line shows this text. A single-turn delete uses [`DELETED_TURN`] instead. |
| const | [`DELETE_TIP`](#constant-delete_tip) | `DELETE_TIP` is the hover text on the Delete button in the canvas footer. Delete removes the armed card. A card that is not in a group removes that one row. |
| const | [`COLLAPSE_ALL`](#constant-collapse_all) | `COLLAPSE_ALL` is the label on the Collapse All button. The canvas footer shows that button. The hover text on that button is [`COLLAPSE_ALL_TIP`]. |
| const | [`EXPAND_ALL`](#constant-expand_all) | `EXPAND_ALL` is the label on the Expand All button. The canvas footer shows that button beside Collapse All. The hover text on that button is [`EXPAND_ALL_TIP`]. |
| const | [`FOLD_OPEN`](#constant-fold_open) | `FOLD_OPEN` is the face of the fold button while a card is folded. The card bar shows this mark. The hover text in that state is [`FOLD_OPEN_TIP`]. |
| const | [`FOLD_SHUT`](#constant-fold_shut) | `FOLD_SHUT` is the face of the fold button while a card is open. The card bar shows this mark. The hover text in that state is [`FOLD_SHUT_TIP`]. |
| const | [`FOLD_OPEN_TIP`](#constant-fold_open_tip) | `FOLD_OPEN_TIP` is the hover text on the fold button while a card is folded. Expand opens the prompt under the buttons. Expand also opens the reply under the buttons. |
| const | [`FOLD_SHUT_TIP`](#constant-fold_shut_tip) | `FOLD_SHUT_TIP` is the hover text on the fold button while a card is open. Collapse keeps a short line of the reply to the right of the buttons. A card with no reply keeps a short line of the prompt there instead. |
| const | [`COLLAPSE_ALL_TIP`](#constant-collapse_all_tip) | `COLLAPSE_ALL_TIP` is the hover text on the Collapse All button in the canvas footer. Collapse All folds every turn on this canvas. Each card keeps a short line of the reply to the right of its buttons. |
| const | [`EXPAND_ALL_TIP`](#constant-expand_all_tip) | `EXPAND_ALL_TIP` is the hover text on the Expand All button in the canvas footer. Expand All opens every turn. The prompt shows under the buttons. The reply shows under the buttons. |
| const | [`HIDE_COLLAPSED`](#constant-hide_collapsed) | `HIDE_COLLAPSED` is the label on the Hide Collapsed button. The tool row of the canvas footer shows that button. The hover text on that button is [`HIDE_COLLAPSED_TIP`]. |
| const | [`SHOW_COLLAPSED`](#constant-show_collapsed) | `SHOW_COLLAPSED` is the label on the Show Collapsed button. The tool row of the canvas footer shows that button after Hide Collapsed. The hover text on that button is [`SHOW_COLLAPSED_TIP`]. |
| const | [`HIDE_COLLAPSED_TIP`](#constant-hide_collapsed_tip) | `HIDE_COLLAPSED_TIP` is the hover text on the Hide Collapsed button. Hide Collapsed takes every folded card off this canvas. The cards stay stored. |
| const | [`SHOW_COLLAPSED_TIP`](#constant-show_collapsed_tip) | `SHOW_COLLAPSED_TIP` is the hover text on the Show Collapsed button. Show Collapsed puts the folded cards back on this canvas. Those cards sit where they sat. Those cards stay folded. |
| const | [`BRIEF_MIN`](#constant-brief_min) | Swing pins the brief. Preferred width and minimum width are 20. |
| const | [`COPY_LABEL`](#constant-copy_label) | `COPY_LABEL` is the label on the Copy button. The card bar shows that button. The reply window uses the same word on its own Copy button. |
| const | [`COPY_TIP`](#constant-copy_tip) | `COPY_TIP` is the hover text on the Copy button on a card. Copy places that turn's whole reply on the clipboard. The prompt stays on the card. |
| const | [`BURNER_LABEL`](#constant-burner_label) | `BURNER_LABEL` is the label on the burner checkbox. The card bar shows that checkbox. The hover text on that checkbox is [`BURNER_TIP`]. |
| const | [`BURNER_DENIED`](#constant-burner_denied) | `BURNER_DENIED` is the status text when a cloud run is denied on a burner turn. The status line shows this text. A turn in a group also opens a notice with this text. |
| const | [`BURNER_TIP`](#constant-burner_tip) | `BURNER_TIP` is the hover text on the burner checkbox on a card. Burner marks this turn as ephemeral. The prompt stays. The reply stays. |
| const | [`EXPANDED_LABEL`](#constant-expanded_label) | `EXPANDED_LABEL` is the label on the Expanded View checkbox. The card bar shows that checkbox. The hover text on that checkbox is [`EXPANDED_TIP`]. |
| const | [`EXPANDED_TIP`](#constant-expanded_tip) | `EXPANDED_TIP` is the hover text on the Expanded View checkbox on a card. A checked box shows the whole reply. An unchecked box keeps the reply in a window the same height as the prompt. |
| const | [`EXPANDED_YAML_FILE`](#constant-expanded_yaml_file) | Per-cell Expanded View. Java and Rust share this file. |
| const | [`HIDE_YAML_FILE`](#constant-hide_yaml_file) | Hide Collapsed for this window. Missing means Show. Not a vault column. |
| const | [`SETTINGS_YAML_FILE`](#constant-settings_yaml_file) | Global switches for this window. Missing means the quiet plate. Not a vault column. |
| const | [`CELL_VIEW_ROWS`](#constant-cell_view_rows) | Default open-cell rows. A missing door key is this count. The footer count replaces it. |
| const | [`ROW_MINUS_TIP`](#constant-row_minus_tip) | `ROW_MINUS_TIP` is the hover text on the minus button beside the row count. That button sits in the canvas footer. The button shows fewer rows on each open turn. |
| const | [`ROW_PLUS_TIP`](#constant-row_plus_tip) | `ROW_PLUS_TIP` is the hover text on the plus button beside the row count. That button sits in the canvas footer. The button shows more rows on each open turn. |
| const | [`ROW_FIELD_TIP`](#constant-row_field_tip) | `ROW_FIELD_TIP` is the hover text on the row-count field in the canvas footer. The field sets the row count for each open turn. You type a number. The number applies after a short pause. |
| const | [`VIEW_LABEL`](#constant-view_label) | `VIEW_LABEL` is the label on the View button. The card bar shows that button. The hover text on that button is [`VIEW_TIP`]. |
| const | [`VIEW_TIP`](#constant-view_tip) | `VIEW_TIP` is the hover text on the View button on a card. View opens the stored reply in a read-only window. The window size is remembered for this vault user. |
| const | [`VIEW_CLOSE`](#constant-view_close) | `VIEW_CLOSE` is the label on the Close button in the reply window. That button leaves the window. The hover text on that button is [`VIEW_CLOSE_TIP`]. |
| const | [`VIEW_CLOSE_TIP`](#constant-view_close_tip) | `VIEW_CLOSE_TIP` is the hover text on the Close button in the reply window. Close leaves the reply window. Close returns to the canvas. |
| const | [`VIEW_COPY_TIP`](#constant-view_copy_tip) | `VIEW_COPY_TIP` is the hover text on the Copy button in the reply window. Copy places this window's whole reply on the clipboard. The find field stays open. |
| const | [`VIEW_FIND_TIP`](#constant-view_find_tip) | `VIEW_FIND_TIP` is the hover text on the find field in the reply window. Typing highlights every match in this reply. Ctrl-F finds the next match. |
| const | [`VIEW_PREV`](#constant-view_prev) | `VIEW_PREV` is the face of the previous-match button in the reply window. That button sits on the find bar. The hover text on that button is [`VIEW_PREV_TIP`]. |
| const | [`VIEW_NEXT`](#constant-view_next) | `VIEW_NEXT` is the face of the next-match button in the reply window. That button sits on the find bar beside the previous-match button. The hover text on that button is [`VIEW_NEXT_TIP`]. |
| const | [`VIEW_PREV_TIP`](#constant-view_prev_tip) | `VIEW_PREV_TIP` is the hover text on the previous-match button in the reply window. The button moves to the previous match. The search wraps to the end after the first match. |
| const | [`VIEW_NEXT_TIP`](#constant-view_next_tip) | `VIEW_NEXT_TIP` is the hover text on the next-match button in the reply window. The button moves to the next match. The search wraps to the start after the last match. |
| const | [`VIEW_CAP`](#constant-view_cap) | Swing `ViewFind.CAP`. A further match paints a plus on the count. |
| const | [`VIEW_MIN_W`](#constant-view_min_w) | `VIEW_MIN_W` is the smallest width of the reply window, in pixels. A saved rectangle narrower than this width is dropped. The window also refuses to shrink below this width when the screen is at least this wide. |
| const | [`VIEW_MIN_H`](#constant-view_min_h) | `VIEW_MIN_H` is the smallest height of the reply window, in pixels. A saved rectangle shorter than this height is dropped. The window also refuses to shrink below this height when the screen is at least this tall. |
| const | [`VIEW_OPEN_W`](#constant-view_open_w) | `VIEW_OPEN_W` is the width used when the reply window first opens, in pixels. That width is the default before a saved rectangle is applied. The window still stays inside the screen. |
| const | [`VIEW_OPEN_H`](#constant-view_open_h) | `VIEW_OPEN_H` is the height used when the reply window first opens, in pixels. That height is the default before a saved rectangle is applied. The window still stays inside the screen. |
| const | [`VIEW_TITLE_CLOSE`](#constant-view_title_close) | The title bar paints the standard close mark. `Window::open` is that mark. |
| const | [`VIEW_INK`](#constant-view_ink) | `VIEW_INK` is the text color in the reply window. The color is yellow. Every span of the reply uses this color. |
| const | [`VIEW_FIELD`](#constant-view_field) | `VIEW_FIELD` is the background color of reply text that is not a match. The color is near black. A match uses [`VIEW_MATCH_BG`] instead. |
| const | [`VIEW_MATCH_BG`](#constant-view_match_bg) | `VIEW_MATCH_BG` is the background color of a find match that is not the current match. The color is dark gold. The current match uses a different background. |
| const | [`VIEW_BODY`](#constant-view_body) | `VIEW_BODY` is the span kind for reply text that is not a match. The value is 0. That span paints with [`VIEW_FIELD`] behind the text. |
| const | [`VIEW_MATCH`](#constant-view_match) | `VIEW_MATCH` is the span kind for a find match that is not the current match. The value is 1. That span paints with [`VIEW_MATCH_BG`] behind the text. |
| const | [`VIEW_CURRENT`](#constant-view_current) | `VIEW_CURRENT` is the span kind for the current find match. The value is 2. That span paints with a different background from [`VIEW_MATCH`]. |
| const | [`CLIPBOARD_BUSY`](#constant-clipboard_busy) | Swing's status word when the clipboard set fails. |
| const | [`SELECT_MODEL_LABEL`](#constant-select_model_label) | `SELECT_MODEL_LABEL` is the Select model line on the console menu. A right-click on the terminal shows that line. The hover text for that action is [`SELECT_MODEL_TIP`]. |
| const | [`SELECT_MODEL_TIP`](#constant-select_model_tip) | `SELECT_MODEL_TIP` is the hover text for Select model on the console menu. Select model opens the Model Config modal for this console. The next session comes back on that family. The next session comes back on that model. |
| const | [`IMPORT_LABEL`](#constant-import_label) | `IMPORT_LABEL` is the Import line on a card menu. A right-click on a prompt shows that line. The hover text on that line is [`IMPORT_TIP`]. |
| const | [`DUPLICATE_LABEL`](#constant-duplicate_label) | `DUPLICATE_LABEL` is the Duplicate group line on a card menu. A grouped card shows that line. The hover text on that line is [`DUPLICATE_TIP`]. |
| const | [`IMPORT_ON_REPLY`](#constant-import_on_reply) | `IMPORT_ON_REPLY` is the status text when a right-click lands on the reply. The status line shows this text. Import from that click is refused because the click is on the reply. |
| const | [`IMPORT_MERGE`](#constant-import_merge) | `IMPORT_MERGE` is the status text when import is refused on a merge cell. The chooser does not open. The refusal is reported before any file is read. |
| const | [`IMPORT_OUTPUT`](#constant-import_output) | `IMPORT_OUTPUT` is the status text when import is refused on an output-only card. The chooser does not open. The refusal is reported before any file is read. |
| const | [`IMPORT_BIG`](#constant-import_big) | `IMPORT_BIG` is the status text when an import file is over 1 MiB. The status line shows this text. The turns already on the canvas stay. |
| const | [`IMPORT_BINARY`](#constant-import_binary) | `IMPORT_BINARY` is the status text when an import file is treated as binary. A NUL byte in the scanned prefix causes that refusal. The prompt is left unchanged. |
| const | [`IMPORT_FAILED`](#constant-import_failed) | `IMPORT_FAILED` is the prefix of a status line when an import fails. A reason follows this prefix. The turns already on the canvas stay. |
| const | [`CHOOSER_MISS`](#constant-chooser_miss) | `CHOOSER_MISS` is the status text when the file chooser does not open. The text is used when that failure leaves no other message. The import does not read a file. |
| const | [`IMPORT_TIP`](#constant-import_tip) | `IMPORT_TIP` is the hover text on the Import line of a card menu. Import reads a text file into this prompt. The reply stays on the card. |
| const | [`DUPLICATE_TIP`](#constant-duplicate_tip) | `DUPLICATE_TIP` is the hover text on the Duplicate group line of a card menu. Duplicate group copies this group. A card that is not in a group reports that no group is selected. |
| const | [`IMPORT_CAP`](#constant-import_cap) | Swing `IMPORT_CAP`. The deck refuses a file over this size. This size is legal. |
| const | [`LARGE_FILE`](#constant-large_file) | Polite line when a file is past the old bolt. The import still runs. |
| const | [`NUL_SCAN`](#constant-nul_scan) | Swing scans this many leading bytes for a NUL. A NUL past this stays. |
| const | [`TRANSFER_LABEL`](#constant-transfer_label) | `TRANSFER_LABEL` is the Transfer Conversation line. A card menu shows that line. The console menu shows the same line. |
| const | [`MENU_PROMPT`](#constant-menu_prompt) | `MENU_PROMPT` is the menu for a prompt on a card that belongs to a group. The lines are [`IMPORT_LABEL`], [`DUPLICATE_LABEL`], and [`TRANSFER_LABEL`]. A reply menu leaves the import line out. |
| const | [`MENU_REPLY`](#constant-menu_reply) | `MENU_REPLY` is the menu for a reply on a card that belongs to a group. The lines are [`DUPLICATE_LABEL`] and [`TRANSFER_LABEL`]. The import line is not on this menu. |
| const | [`MENU_PROMPT_LONE`](#constant-menu_prompt_lone) | `MENU_PROMPT_LONE` is the menu for a prompt on a card that is not in a group. The lines are [`IMPORT_LABEL`] and [`TRANSFER_LABEL`]. The duplicate line is not on this menu. |
| const | [`MENU_REPLY_LONE`](#constant-menu_reply_lone) | `MENU_REPLY_LONE` is the menu for a reply on a card that is not in a group. The only line is [`TRANSFER_LABEL`]. Import and duplicate are not on this menu. |
| const | [`CHOOSER_MIN_W`](#constant-chooser_min_w) | Swing refuses a saved rectangle under this width. |
| const | [`CHOOSER_MIN_H`](#constant-chooser_min_h) | Swing refuses a saved rectangle under this height. |
| const | [`IMPORT_CHOOSER`](#constant-import_chooser) | Cell import chooser. The folder and the bounds ride in the environment. |
| const | [`MARK_I`](#constant-mark_i) | `MARK_I` is the face of the Included letter button. The card bar shows that button. A press sets the card to Included. |
| const | [`MARK_E`](#constant-mark_e) | `MARK_E` is the face of the Excluded letter button. The card bar shows that button. A press sets the card to Excluded. |
| const | [`MARK_P`](#constant-mark_p) | `MARK_P` is the face of the Input letter button. The card bar shows that button. A press sets the card to Input. |
| const | [`MARK_O`](#constant-mark_o) | `MARK_O` is the face of the Output letter button. The card bar shows that button. A press sets the card to Output. |
| const | [`IDLE_STOP`](#constant-idle_stop) | `IDLE_STOP` is the status text when Stop is used and nothing is running. The status line shows this text. No cancel is sent in that case. |
| const | [`HALT_STOP`](#constant-halt_stop) | `HALT_STOP` is the status text after Stop cancels a running turn. The status line shows this text. Other turns in a group can still run. |
| const | [`EXCLUDED_RUN`](#constant-excluded_run) | `EXCLUDED_RUN` is the status text when Run is refused because the card is Excluded. The status line shows this text. A group send with nothing to send uses the same text. |
| const | [`TOO_LARGE`](#constant-too_large) | `TOO_LARGE` is the status text when Run is refused because the prompt is too large. The status line shows this text. No send goes out in that case. |
| const | [`INSPECTOR_LABEL`](#constant-inspector_label) | `INSPECTOR_LABEL` is the heading of the inspector column. That column sits beside the cards. The heading is this word. |
| const | [`EST_TOKENS`](#constant-est_tokens) | `EST_TOKENS` is the prefix before an estimated token count. The count is written after this prefix. The estimate is not a send. |
| const | [`TOKEN_OPEN_TIP`](#constant-token_open_tip) | `TOKEN_OPEN_TIP` is the hover text on the T button in the inspector. T opens the token window. The ring compares this estimate to the selected model's context window. |
| const | [`TOKEN_CALC`](#constant-token_calc) | `TOKEN_CALC` is the label on the Token Calc button. The menu row shows that button. The hover text on that button is [`TOKEN_CALC_TIP`]. |
| const | [`TOKEN_CALC_TIP`](#constant-token_calc_tip) | `TOKEN_CALC_TIP` is the hover text on the Token Calc button. Token Calc opens the token window. The ring compares this estimate to the selected model's context window. |
| const | [`TOOLS_MENU`](#constant-tools_menu) | `TOOLS_MENU` is the label on the Tools menu. The menu bar shows that menu. The hover text on that menu is [`TOOLS_MENU_TIP`]. |
| const | [`SHOW_THINKING`](#constant-show_thinking) | `SHOW_THINKING` is the label on the Show Thinking item. The Tools menu shows that item. The hover text on that item is [`SHOW_THINKING_TIP`]. |
| const | [`TOKEN_COMBO_TIP`](#constant-token_combo_tip) | `TOKEN_COMBO_TIP` is the hover text on the model combo in the token window. The estimate stays. The ring uses this model's context window. |
| const | [`TOKEN_OK_TIP`](#constant-token_ok_tip) | `TOKEN_OK_TIP` is the hover text on the OK button in the token window. OK closes the token window. The estimate is left as it was. |
| const | [`RING_TRACK`](#constant-ring_track) | `RING_TRACK` is the color of the token ring track. The color is a dark blue-gray. An unknown fill and a fill that is not positive also use this color. |
| const | [`RING_AMBER`](#constant-ring_amber) | `RING_AMBER` is the amber color of the token ring. The ring uses this color when the fill is at least 0.7 and under 0.9. A fuller ring uses [`RING_RED`]. |
| const | [`RING_RED`](#constant-ring_red) | `RING_RED` is the red color of the token ring. The ring uses this color when the fill is at least 0.9. A smaller positive fill uses another color. |
| const | [`RING_HEAD`](#constant-ring_head) | `RING_HEAD` is the color of the upper line in the token ring. The color is light gray. That line sits above the center of the ring. |
| const | [`RING_FOOT`](#constant-ring_foot) | `RING_FOOT` is the color of the lower line in the token ring. The color is a muted gray. That line sits below the center of the ring. |
| const | [`RING_BG`](#constant-ring_bg) | `RING_BG` is the background color behind the token ring. The color is near black. The rectangle is filled with square corners. |
| const | [`BLOCK_RULE`](#constant-block_rule) | `BLOCK_RULE` is the rule drawn between inspector blocks. A line of dashes is that rule. The first block has no rule before it. |
| const | [`GROUP_LABEL`](#constant-group_label) | `GROUP_LABEL` is the label on the Group button. The group-tool row shows that button on a card that is not in a group. The hover text on that button is [`GROUP_TIP`]. |
| const | [`JOIN_LABEL`](#constant-join_label) | `JOIN_LABEL` is the label on the Join button. The group-tool row shows that button beside Group. The hover text on that button is [`JOIN_TIP`]. |
| const | [`UNGROUP_LABEL`](#constant-ungroup_label) | `UNGROUP_LABEL` is the label on the Ungroup button. The group-tool row shows that button on a card that is already in a group. The hover text on that button is [`UNGROUP_TIP`]. |
| const | [`ADD_CELL_LABEL`](#constant-add_cell_label) | `ADD_CELL_LABEL` is the label on the Add Cell button. A merge card shows that button. The hover text on that button is [`ADD_CELL_TIP`]. |
| const | [`ADD_CELL_TIP`](#constant-add_cell_tip) | `ADD_CELL_TIP` is the hover text on the Add Cell button. Add Cell adds a new turn to this merge group. The new turn is the next member. |
| const | [`BAR_SHUT`](#constant-bar_shut) | `BAR_SHUT` is the face of the group-tool mark while that row is hidden. The mark is a v. The hover text in that state is [`BAR_SHOW_TIP`]. |
| const | [`BAR_OPEN`](#constant-bar_open) | `BAR_OPEN` is the face of the group-tool mark while that row is showing. The mark is a caret. The hover text in that state is [`BAR_HIDE_TIP`]. |
| const | [`BAR_SHOW_TIP`](#constant-bar_show_tip) | `BAR_SHOW_TIP` is the hover text on the group-tool mark while that row is hidden. The mark shows the group tools. Group, Join, and Ungroup sit on the row under this bar. |
| const | [`BAR_HIDE_TIP`](#constant-bar_hide_tip) | `BAR_HIDE_TIP` is the hover text on the group-tool mark while that row is showing. The mark hides the group tools. Group, Join, and Ungroup fold away. |
| const | [`TOOL_SHUT`](#constant-tool_shut) | `TOOL_SHUT` is the face of the tool-bar mark while that bar is hidden. The mark is a v. The hover text in that state is [`TOOL_SHOW_TIP`]. |
| const | [`TOOL_OPEN`](#constant-tool_open) | `TOOL_OPEN` is the face of the tool-bar mark while that bar is showing. The mark is a caret. The hover text in that state is [`TOOL_HIDE_TIP`]. |
| const | [`TOOL_SHOW_TIP`](#constant-tool_show_tip) | `TOOL_SHOW_TIP` is the hover text on the tool-bar mark while that bar is hidden. The mark shows the tool bar. Search, Export, Import, Pull, To terminal, the mood switch, and Mood sit on the row under this bar. |
| const | [`TOOL_HIDE_TIP`](#constant-tool_hide_tip) | `TOOL_HIDE_TIP` is the hover text on the tool-bar mark while that bar is showing. The mark hides the tool bar. Search, Export, Import, Pull, To terminal, the mood switch, Mood, Hide Collapsed, and Show Collapsed fold away. |
| const | [`CONTROL_FOLD_TIP`](#constant-control_fold_tip) | `CONTROL_FOLD_TIP` is the hover text on the connection-bar mark while that bar is open. The mark folds the connection bar. The fields leave the window. The canvas uses that height. |
| const | [`CONTROL_SHOW_TIP`](#constant-control_show_tip) | `CONTROL_SHOW_TIP` is the hover text on the connection-bar mark while that bar is folded. The mark shows the connection bar. The host field, the port field, the user field, and the password field sit on the first row. |
| const | [`HOST_TIP`](#constant-host_tip) | `HOST_TIP` is the hover text on the host field of the connection bar. The field names the machine the SSH session dials. The label beside the field uses the same hover text. |
| const | [`PORT_TIP`](#constant-port_tip) | `PORT_TIP` is the hover text on the port field of the connection bar. The field is the port the SSH session dials. The label beside the field uses the same hover text. |
| const | [`USER_FIELD_TIP`](#constant-user_field_tip) | `USER_FIELD_TIP` is the hover text on the user field of the connection bar. The field is the account the SSH session logs in as. The list remembers accounts. |
| const | [`PASSWORD_TIP`](#constant-password_tip) | `PASSWORD_TIP` is the hover text on the password field of the connection bar. The field holds the password for that user. The label beside the field uses the same hover text. |
| const | [`REMEMBER_TIP`](#constant-remember_tip) | `REMEMBER_TIP` is the hover text on the Remember password checkbox. That checkbox sits on the connection bar. A checked box keeps the password for this user. |
| const | [`FONT_TIP`](#constant-font_tip) | `FONT_TIP` is the hover text on the font-size label and on the size number. Both sit on the connection bar. The size applies to the panes and to this bar. |
| const | [`FONT_MINUS_TIP`](#constant-font_minus_tip) | `FONT_MINUS_TIP` is the hover text on the smaller-font button. That button sits beside the font size on the connection bar. The panes take the new size. This bar takes the new size. |
| const | [`FONT_PLUS_TIP`](#constant-font_plus_tip) | `FONT_PLUS_TIP` is the hover text on the larger-font button. That button sits beside the font size on the connection bar. The panes take the new size. This bar takes the new size. |
| const | [`ACCOUNTS_TIP`](#constant-accounts_tip) | `ACCOUNTS_TIP` is the hover text on the accounts combo of the connection bar. You pick a remembered account. The user field fills in. The password field fills in. |
| const | [`CONNECT_TIP`](#constant-connect_tip) | `CONNECT_TIP` is the hover text on the Connect button. That button sits on the connection bar. Connect opens the SSH session with the host, the port, the user, and the password on this bar. |
| const | [`DISCONNECT_TIP`](#constant-disconnect_tip) | `DISCONNECT_TIP` is the hover text on the Disconnect button. That button sits on the connection bar. Disconnect closes the SSH session. The bar opens. |
| const | [`RECONNECT_TIP`](#constant-reconnect_tip) | `RECONNECT_TIP` is the hover text on the Reconnect button. That button sits on the connection bar. Reconnect closes the session. Reconnect opens the session again with the fields on this bar. |
| const | [`CLEAR_TERM_TIP`](#constant-clear_term_tip) | `CLEAR_TERM_TIP` is the hover text on the Clear Terminal button. That button sits on the connection bar. Clear Terminal wipes the focused pane. |
| const | [`SEND_FILE_TIP`](#constant-send_file_tip) | `SEND_FILE_TIP` is the hover text on the Load File button. That button sits on the connection bar. Load File reads a file and opens it in the paste editor. |
| const | [`NEW_TERM_TIP`](#constant-new_term_tip) | `NEW_TERM_TIP` is the hover text on the New Term button. That button sits on the connection bar. New Term splits the focused pane. The split is horizontal or vertical. |
| const | [`CLOSE_PANE_TIP`](#constant-close_pane_tip) | `CLOSE_PANE_TIP` is the hover text on the Close Pane button. That button sits on the connection bar. Close Pane closes the focused pane. The last pane stays. |
| const | [`NEW_TIP`](#constant-new_tip) | `NEW_TIP` is the hover text on the New button in the canvas footer. New adds an empty card at the end of this canvas. New puts the cursor in that card's prompt. |
| const | [`CLEAR_TIP`](#constant-clear_tip) | `CLEAR_TIP` is the hover text on the Clear button in the canvas footer. Clear deletes every turn on this canvas. Clear leaves one empty turn in their place. |
| const | [`GROUP_TIP`](#constant-group_tip) | `GROUP_TIP` is the hover text on the Group button. Group starts a join on this turn. This turn becomes member 1. A merge cell is added under it. |
| const | [`JOIN_TIP`](#constant-join_tip) | `JOIN_TIP` is the hover text on the Join button. Join adds this turn to the group of the turn you focused before this one. You click a member first. You then press Join on the turn you want to add. |
| const | [`UNGROUP_TIP`](#constant-ungroup_tip) | `UNGROUP_TIP` is the hover text on the Ungroup button. Ungroup pulls this turn out of its group. Ungroup returns that turn to the column of cards that are not in a group. |
| const | [`FILE_MENU_TIP`](#constant-file_menu_tip) | `FILE_MENU_TIP` is the hover text on the File menu. The menu holds New Term. The menu holds Close Pane, Import, Settings, and Exit. |
| const | [`FILE_NEW_TIP`](#constant-file_new_tip) | `FILE_NEW_TIP` is the hover text on New Term in the File menu. That item splits the focused pane. The split is horizontal or vertical. |
| const | [`FILE_CLOSE_TIP`](#constant-file_close_tip) | `FILE_CLOSE_TIP` is the hover text on Close Pane in the File menu. That item closes the focused pane. The last pane stays. |
| const | [`FILE_IMPORT_TIP`](#constant-file_import_tip) | `FILE_IMPORT_TIP` is the hover text on Import in the File menu. That item reads a file into the focused terminal. The canvas import is a different control. |
| const | [`FILE_SETTINGS_TIP`](#constant-file_settings_tip) | `FILE_SETTINGS_TIP` is the hover text on Settings in the File menu. Settings opens the global switches for this window. The Settings window holds those switches. |
| const | [`FILE_EXIT_TIP`](#constant-file_exit_tip) | `FILE_EXIT_TIP` is the hover text on Exit in the File menu. Exit closes the Aetheris window. A confirmation comes first. |
| const | [`SETTINGS_TITLE`](#constant-settings_title) | `SETTINGS_TITLE` is the title of the Settings window. The window shows that word in its title bar. The body under the title is [`SETTINGS_BODY`]. |
| const | [`SETTINGS_BODY`](#constant-settings_body) | `SETTINGS_BODY` is the line under the Settings title. The line names the controls as global switches. Those switches are saved for the next open. |
| const | [`SETTINGS_BORDERS`](#constant-settings_borders) | `SETTINGS_BORDERS` is the label on the painted-borders checkbox. The Settings window shows that checkbox. The hover text on that checkbox is [`SETTINGS_BORDERS_TIP`]. |
| const | [`SETTINGS_BORDERS_TIP`](#constant-settings_borders_tip) | `SETTINGS_BORDERS_TIP` is the hover text on the painted-borders checkbox. On paints the Java cell frame on the canvas text panes. Off leaves those panes without that frame. |
| const | [`SETTINGS_DEBUG`](#constant-settings_debug) | `SETTINGS_DEBUG` is the label on the Debug Mood checkbox. The Settings window shows that checkbox. The hover text on that checkbox is [`SETTINGS_DEBUG_TIP`]. |
| const | [`SETTINGS_DEBUG_TIP`](#constant-settings_debug_tip) | `SETTINGS_DEBUG_TIP` is the hover text on the Debug Mood checkbox. On paints the invitation immediately before the user line under the pointer. Those lines carry no card number. |
| const | [`SETTINGS_TIPS`](#constant-settings_tips) | `SETTINGS_TIPS` is the label on the tooltips checkbox. The Settings window shows that checkbox. The hover text on that checkbox is [`SETTINGS_TIPS_TIP`]. |
| const | [`SETTINGS_TIPS_TIP`](#constant-settings_tips_tip) | `SETTINGS_TIPS_TIP` is the hover text on the tooltips checkbox. The checkbox turns hover tips on or off. On is how the window works now. Off shows no hover tips. |
| const | [`SETTINGS_OK_TIP`](#constant-settings_ok_tip) | `SETTINGS_OK_TIP` is the hover text on the OK button in Settings. OK closes Settings. The switch stays as set. |
| const | [`TOOLS_MENU_TIP`](#constant-tools_menu_tip) | `TOOLS_MENU_TIP` is the hover text on the Tools menu. The menu holds Show Thinking. Show Thinking reveals the plate in front. |
| const | [`SHOW_THINKING_TIP`](#constant-show_thinking_tip) | `SHOW_THINKING_TIP` is the hover text on Show Thinking in the Tools menu. The item shows the thinking pane. That pane is on the plate in front. |
| const | [`HELP_MENU_TIP`](#constant-help_menu_tip) | `HELP_MENU_TIP` is the hover text on the Help menu. The menu holds Help. The menu holds About. |
| const | [`HELP_ITEM_TIP`](#constant-help_item_tip) | `HELP_ITEM_TIP` is the hover text on Help in the Help menu. That item opens the help text. The About item is separate. |
| const | [`ABOUT_ITEM_TIP`](#constant-about_item_tip) | `ABOUT_ITEM_TIP` is the hover text on About in the Help menu. That item opens About Aetheris. The help text is a separate item. |
| const | [`SSH_TAB_TIP`](#constant-ssh_tab_tip) | `SSH_TAB_TIP` is the hover text on the SSH tab. The tab shows the SSH terminal. The tab stays available. |
| const | [`AETHERIS_TAB_TIP`](#constant-aetheris_tab_tip) | `AETHERIS_TAB_TIP` is the hover text on the Aetheris tab. The tab shows the Aetheris canvas. The multiplexed channel has to be up before that tab can be chosen. |
| const | [`PASTE_OK_TIP`](#constant-paste_ok_tip) | `PASTE_OK_TIP` is the hover text on OK in the paste editor. OK inserts this text. Ctrl+Enter does the same insert. |
| const | [`PASTE_CANCEL_TIP`](#constant-paste_cancel_tip) | `PASTE_CANCEL_TIP` is the hover text on Cancel in the paste editor. Cancel drops this text. The editor inserts no characters. |
| const | [`PASTE_BODY_TIP`](#constant-paste_body_tip) | `PASTE_BODY_TIP` is the hover text on the text in the paste editor. You edit the text before it is inserted. Enter adds a line. |
| const | [`NOTICE_OK_TIP`](#constant-notice_ok_tip) | `NOTICE_OK_TIP` is the hover text on OK in a notice. OK closes this notice. The notice stays until that close. |
| const | [`SPLIT_H_TIP`](#constant-split_h_tip) | `SPLIT_H_TIP` is the hover text on the Horizontal button. |
| const | [`SPLIT_V_TIP`](#constant-split_v_tip) | `SPLIT_V_TIP` is the hover text on the Vertical button. |
| const | [`SPLIT_CANCEL_TIP`](#constant-split_cancel_tip) | `SPLIT_CANCEL_TIP` is the hover text on the Cancel button in the split window. |
| const | [`HELP_OK_TIP`](#constant-help_ok_tip) | `HELP_OK_TIP` is the hover text on the OK button in the help window. |
| const | [`ABOUT_OK_TIP`](#constant-about_ok_tip) | `ABOUT_OK_TIP` is the hover text on the OK button in the About window. |
| const | [`EXIT_YES_TIP`](#constant-exit_yes_tip) | `EXIT_YES_TIP` is the hover text on the Yes button in the exit window. |
| const | [`EXIT_NO_TIP`](#constant-exit_no_tip) | `EXIT_NO_TIP` is the hover text on the No button in the exit window. |
| const | [`SEARCH_LABEL`](#constant-search_label) | `SEARCH_LABEL` is the word on the Search button. |
| const | [`EXPORT_LABEL`](#constant-export_label) | `EXPORT_LABEL` is the word on the Export button. |
| const | [`EXPORT_TIP`](#constant-export_tip) | `EXPORT_TIP` is the hover text on the Export button in the canvas tool row. |
| const | [`EXPORT_ASK`](#constant-export_ask) | `EXPORT_ASK` is the question about keeping burner bodies. |
| const | [`EXPORT_WORD`](#constant-export_word) | `EXPORT_WORD` is the status text after an export file is written. |
| const | [`EXPORT_FAILED`](#constant-export_failed) | `EXPORT_FAILED` is the status prefix when an export does not finish. |
| const | [`EXPORT_ASK_CHOOSER`](#constant-export_ask_chooser) | Yes, No, Cancel. No path from the person is spliced into this script. |
| const | [`EXPORT_SAVE_CHOOSER`](#constant-export_save_chooser) | System save dialog. No path from the person is spliced into this script. |
| const | [`CANVAS_IMPORT_LABEL`](#constant-canvas_import_label) | `CANVAS_IMPORT_LABEL` is the word on the Import button. |
| const | [`CANVAS_IMPORT_TIP`](#constant-canvas_import_tip) | `CANVAS_IMPORT_TIP` is the hover text on the Import button in the canvas tool row. |
| const | [`PULL_LABEL`](#constant-pull_label) | `PULL_LABEL` is the word on the Pull button. |
| const | [`PULL_TIP`](#constant-pull_tip) | `PULL_TIP` is the hover text on the Pull button in the canvas tool row. |
| const | [`PIN_LABEL`](#constant-pin_label) | `PIN_LABEL` is the word on the pin checkbox. |
| const | [`PIN_OK_LABEL`](#constant-pin_ok_label) | `PIN_OK_LABEL` is the word on the pin OK button. |
| const | [`PIN_FOLD_LABEL`](#constant-pin_fold_label) | `PIN_FOLD_LABEL` is the mark on the pin fold button. |
| const | [`PIN_SHOW_LABEL`](#constant-pin_show_label) | `PIN_SHOW_LABEL` is the letter on the folded pin mark. |
| const | [`PIN_CLEAR_LABEL`](#constant-pin_clear_label) | `PIN_CLEAR_LABEL` is the mark on the pin clear control. |
| const | [`PIN_BOX_TIP`](#constant-pin_box_tip) | `PIN_BOX_TIP` is the hover text on the pin checkbox. |
| const | [`PIN_FIELD_TIP`](#constant-pin_field_tip) | `PIN_FIELD_TIP` is the hover text on the pin word field. |
| const | [`PIN_CLEAR_TIP`](#constant-pin_clear_tip) | `PIN_CLEAR_TIP` is the hover text on the pin clear control. |
| const | [`PIN_FOLD_TIP`](#constant-pin_fold_tip) | `PIN_FOLD_TIP` is the hover text on the pin fold button. |
| const | [`PIN_SHOW_TIP`](#constant-pin_show_tip) | `PIN_SHOW_TIP` is the hover text on the folded pin mark. |
| const | [`PIN_OK_TIP`](#constant-pin_ok_tip) | Java's OK tip, with the mood sentence removed. `commitPin` writes the states and does not open Mood. |
| const | [`PULL_WORD`](#constant-pull_word) | `PULL_WORD` is the status text after Pull adds a turn. |
| const | [`PULL_EMPTY`](#constant-pull_empty) | `PULL_EMPTY` is the status text when Pull adds nothing. |
| const | [`TERMINAL_LABEL`](#constant-terminal_label) | `TERMINAL_LABEL` is the word on the To terminal button. |
| const | [`TERMINAL_TIP`](#constant-terminal_tip) | `TERMINAL_TIP` is the hover text on the To terminal button. |
| const | [`TERMINAL_SENT`](#constant-terminal_sent) | `TERMINAL_SENT` is the status text after the turn is written to the terminal. |
| const | [`TERMINAL_EPHEMERAL`](#constant-terminal_ephemeral) | `TERMINAL_EPHEMERAL` is the status text when To terminal refuses a burner turn. |
| const | [`TERMINAL_FAILED`](#constant-terminal_failed) | `TERMINAL_FAILED` is the status prefix when a terminal write does not finish. |
| const | [`TERMINAL_OVER`](#constant-terminal_over) | `TERMINAL_OVER` is the status text when the terminal paste is too long. |
| const | [`TERMINAL_ASK`](#constant-terminal_ask) | `TERMINAL_ASK` is the question shown before a turn is written to the terminal. |
| const | [`TERMINAL_PASTE_CAP`](#constant-terminal_paste_cap) | Java `String.length`. A body over this is refused before confirm. |
| const | [`TERMINAL_PREVIEW_CAP`](#constant-terminal_preview_cap) | Dialog cap. Swing's preview keeps this many UTF-16 units, then an ellipsis. |
| const | [`TERMINAL_CONFIRM_CHOOSER`](#constant-terminal_confirm_chooser) | OK or Cancel. The preview rides in the environment. No cell text is spliced into this script. |
| const | [`IMPORT_VERSION`](#constant-import_version) | `IMPORT_VERSION` is the status text when a canvas import is refused for its version. |
| const | [`CANVAS_IMPORT_CHOOSER`](#constant-canvas_import_chooser) | System open dialog. No path from the person is spliced into this script. |
| const | [`SEARCH_FIELD_TIP`](#constant-search_field_tip) | `SEARCH_FIELD_TIP` is the hover text on the search field in the canvas tool row. |
| const | [`SEARCH_TIP`](#constant-search_tip) | `SEARCH_TIP` is the hover text on the Search button in the canvas tool row. |
| const | [`SEARCH_CLEAR_TIP`](#constant-search_clear_tip) | `SEARCH_CLEAR_TIP` is the hover text on the search clear control. |
| const | [`SEARCH_MISS`](#constant-search_miss) | `SEARCH_MISS` is the status text when a canvas search finds nothing. |
| const | [`MERGE_LABEL`](#constant-merge_label) | `MERGE_LABEL` is the word on a merge card's title. |
| const | [`JOIN_BANNER`](#constant-join_banner) | Empty target shows as ollama. The banner is that word. |
| const | [`MERGE_FAIL`](#constant-merge_fail) | `MERGE_FAIL` is the fault text written on a merge card when the join fails. |
| const | [`GROUP_MISS`](#constant-group_miss) | Armed card has no group. Swing's yellow slide says this. The slide stays off. |
| const | [`GROUP_COPIED`](#constant-group_copied) | Swing's status after the copy lands. |
| const | [`NOTICE_INK`](#constant-notice_ink) | Yellow slide edge. The notice uses this ink. |
| const | [`SLIDE_TITLE`](#constant-slide_title) | `SLIDE_TITLE` is the title word on the error notice. |
| const | [`SLIDE_CLOSE`](#constant-slide_close) | `SLIDE_CLOSE` is the word on the Close button of the error notice. |
| const | [`SLIDE_EMPTY`](#constant-slide_empty) | Empty garnered text. Swing uses this sentence. |
| const | [`SLIDE_FAILED`](#constant-slide_failed) | Unstructured miss. The status word is this. |
| const | [`CELL_BUSY`](#constant-cell_busy) | `CELL_BUSY` is the status word for a structured failure that names this reason. |
| const | [`SLIDE_ROWS`](#constant-slide_rows) | Body rows. Swing's text area is three rows. |
| const | [`SLIDE_EDGE_PX`](#constant-slide_edge_px) | Border. Swing's line border is 2. |
| const | [`SLIDE_RED_EDGE`](#constant-slide_red_edge) | `SLIDE_RED_EDGE` is the border color of a hard error notice. |
| const | [`SLIDE_RED_FILL`](#constant-slide_red_fill) | `SLIDE_RED_FILL` is the background color of a hard error notice. |
| const | [`SLIDE_RED_INK`](#constant-slide_red_ink) | `SLIDE_RED_INK` is the text color of a hard error notice. |
| const | [`SLIDE_YELLOW_FILL`](#constant-slide_yellow_fill) | `SLIDE_YELLOW_FILL` is the background color of an error notice that is not hard. |
| const | [`SLIDE_YELLOW_INK`](#constant-slide_yellow_ink) | `SLIDE_YELLOW_INK` is the text color of an error notice that is not hard. |
| const | [`SLIDE_TIP`](#constant-slide_tip) | `SLIDE_TIP` is the hover text on the error-notice title. |
| const | [`SLIDE_BODY_TIP`](#constant-slide_body_tip) | `SLIDE_BODY_TIP` is the hover text on the body of the error notice. |
| const | [`SLIDE_CLOSE_TIP`](#constant-slide_close_tip) | `SLIDE_CLOSE_TIP` is the hover text on the Close button of the error notice. |
| const | [`PIPE_BLOCK`](#constant-pipe_block) | Status when a pipe blocks the send. The slide stays off. |
| const | [`PIPE_OPEN`](#constant-pipe_open) | `PIPE_OPEN` is the opening marker of a cell-output token in turn text. |
| const | [`PIPE_CLOSE`](#constant-pipe_close) | `PIPE_CLOSE` is the closing marker of a cell-output token in turn text. |
| const | [`MEMBER_EAST`](#constant-member_east) | Pixels east of a member's top bar. The row scrolls sideways. |
| const | [`EDGE_QUIET`](#constant-edge_quiet) | Quiet card line. Swing uses `0x3a3a3a`. |
| const | [`EDGE_GHOST`](#constant-edge_ghost) | Excluded card line. Swing uses `Color.DARK_GRAY`. |
| const | [`EDGE_ARMED`](#constant-edge_armed) | Armed card line. Swing uses selection sapphire. |
| const | [`EDGE_MATCH`](#constant-edge_match) | Readable pin match. Swing uses `MEDIUM_GREEN`. |
| const | [`LINK_CHIP_HI`](#constant-link_chip_hi) | Raised edge of the folded Connected chip. |
| const | [`LINK_CHIP_LO`](#constant-link_chip_lo) | Shadow edge of the folded Connected chip. |
| const | [`EDGE_MISS`](#constant-edge_miss) | Readable pin miss. Swing uses `0x222222`. |
| const | [`PIN_CLEAR_FILL`](#constant-pin_clear_fill) | Clear mark. Swing paints the x on `#E53935`. |
| const | [`CARD_EDGE_PX`](#constant-card_edge_px) | `CARD_EDGE_PX` is the stroke width of a card edge, in pixels. |
| const | [`THINK_FRAME_MS`](#constant-think_frame_ms) | Spinner step. Swing's ThinkSpinner waits 120 ms. |
| const | [`THINK_WORD`](#constant-think_word) | `THINK_WORD` is the word shown beside a thinking spinner. |
| const | [`THINK_GLYPH`](#constant-think_glyph) | Glyph. Swing uses `#00FF66`. |
| const | [`BUTTON_HOVER_INK`](#constant-button_hover_ink) | Button word while the pointer is on the button. Swing uses `#00FF66`. |
| const | [`MENU_INK`](#constant-menu_ink) | Main menu word. Swing menu foreground is `#00ff41`. |
| const | [`BUTTON_TIP_DELAY`](#constant-button_tip_delay) | Tooltip wait. egui's default is half a second. This seat adds 50 ms. |
| const | [`THINK_INK`](#constant-think_ink) | Word. Swing uses `#00D7FF`. |
| const | [`THINK_BODY`](#constant-think_body) | Thought body. Locked lighter sapphire. Bytes 115, 174, 255. |
| const | [`THINK_FIELD`](#constant-think_field) | Thought pane. Swing `FIELD_BG`. |
| const | [`THINK_KIND`](#constant-think_kind) | Kind byte for a thought. Swing paints only this kind on the log. |
| const | [`THINK_TAG_CAP`](#constant-think_tag_cap) | Swing `thinkTagWord` stops at 80 UTF-16 units. |
| const | [`THINK_GRIP`](#constant-think_grip) | Grip on the north edge. The Clear mark sits on the east of this row. |
| const | [`FOLDED_LINE_LIFT`](#constant-folded_line_lift) | Folded canvas control line. The bottoms sit this far above the outer top of BB1. |
| const | [`BUTTON_CLEAR_PX`](#constant-button_clear_px) | Free points around a button border. The clip and the next shape stay outside it. |
| const | [`THINK_GRIP_INK`](#constant-think_grip_ink) | Grip line. Swing paints `0x8a8a8a`. |
| const | [`THINK_MAX_FALLBACK`](#constant-think_max_fallback) | Swing `thinkMaxHeight` when the panel has not been measured. |
| const | [`THINK_MAX_KEEP`](#constant-think_max_keep) | Swing keeps 160 pixels for the canvas and the footer. |
| const | [`THINK_CLEAR_LABEL`](#constant-think_clear_label) | `THINK_CLEAR_LABEL` is the letter on the Clear button of a thinking strip. |
| const | [`THINK_CLOSE_LABEL`](#constant-think_close_label) | `THINK_CLOSE_LABEL` is the word on the Close button of a thinking strip. |
| const | [`THINK_LOG_FILE`](#constant-think_log_file) | `THINK_LOG_FILE` is the file name of the saved thinking log. |
| const | [`THINK_YAML_FILE`](#constant-think_yaml_file) | `THINK_YAML_FILE` is the file name of the notebook thinking settings. |
| const | [`THINK_HEIGHT_MIN`](#constant-think_height_min) | Java keeps a dragged height in this range. A number outside it is ignored. |
| const | [`THINK_HEIGHT_MAX`](#constant-think_height_max) | `THINK_HEIGHT_MAX` is the greatest thinking-pane height that is kept, in pixels. |
| const | [`THINK_FOLD_TIP`](#constant-think_fold_tip) | `THINK_FOLD_TIP` is the hover text on the fold button of the notebook thinking strip. |
| const | [`THINK_CLOSE_TIP`](#constant-think_close_tip) | `THINK_CLOSE_TIP` is the hover text on the Close button of the notebook thinking strip. |
| const | [`THINK_CLEAR_TIP`](#constant-think_clear_tip) | `THINK_CLEAR_TIP` is the hover text on the Clear button of the notebook thinking strip. |
| const | [`THINK_GRIP_TIP`](#constant-think_grip_tip) | `THINK_GRIP_TIP` is the hover text on the drag row of the notebook thinking strip. |
| const | [`VT_THINK_YAML_FILE`](#constant-vt_think_yaml_file) | `VT_THINK_YAML_FILE` is the file name of the terminal thinking-pane settings. |
| const | [`VT_FOLD_TIP`](#constant-vt_fold_tip) | `VT_FOLD_TIP` is the hover text on the fold button of the terminal thinking strip. |
| const | [`VT_CLOSE_TIP`](#constant-vt_close_tip) | `VT_CLOSE_TIP` is the hover text on the Close button of the terminal thinking strip. |
| const | [`VT_CLEAR_TIP`](#constant-vt_clear_tip) | `VT_CLEAR_TIP` is the hover text on the Clear button of the terminal thinking strip. |
| const | [`VT_GRIP_TIP`](#constant-vt_grip_tip) | `VT_GRIP_TIP` is the hover text on the drag row of the terminal thinking strip. |
| const | [`VT_BELOW_CLEAR`](#constant-vt_below_clear) | The SSH console starts this far under the Clear button while the strip is open. |
| const | [`MOOD_YAML_FILE`](#constant-mood_yaml_file) | `MOOD_YAML_FILE` is the file name of the shared mood record. |
| const | [`HOST_SYSTEM`](#constant-host_system) | Vestige host sentence. Tests lock that a new working set leaves it off the wire. |
| const | [`MOOD_ACTIVATE`](#constant-mood_activate) | Legacy activate sentence. A new send leaves this row off. |
| const | [`MOOD_OPENER`](#constant-mood_opener) | The sentence before the constructed mood. Canvas and the glass use this line. |
| const | [`MOOD_CLOSER`](#constant-mood_closer) | The sentence after the constructed mood. Canvas and the glass use this line. |
| const | [`MOOD_TOO_LARGE`](#constant-mood_too_large) | `MOOD_TOO_LARGE` is the body of a yellow error notice when a mood send does not fit. |
| const | [`MOOD_WIDTH_MIN`](#constant-mood_width_min) | `MOOD_WIDTH_MIN` is the smallest mood-glass width that is kept, in pixels. |
| const | [`MOOD_WIDTH_MAX`](#constant-mood_width_max) | `MOOD_WIDTH_MAX` is the greatest mood-glass width that is kept, in pixels. |
| const | [`MOOD_HEIGHT_MIN`](#constant-mood_height_min) | `MOOD_HEIGHT_MIN` is the smallest mood-glass height that is kept, in pixels. |
| const | [`MOOD_HEIGHT_MAX`](#constant-mood_height_max) | `MOOD_HEIGHT_MAX` is the greatest mood-glass height that is kept, in pixels. |
| const | [`MOOD_GREY`](#constant-mood_grey) | `MOOD_GREY` is the color of the round mood button when no mood string is saved. |
| const | [`MOOD_OFF`](#constant-mood_off) | `MOOD_OFF` is the red of the round mood button while a saved string is switched off. |
| const | [`MOOD_ON_INK`](#constant-mood_on_ink) | `MOOD_ON_INK` is the green of the round mood button while the switch is on. |
| const | [`MOOD_YELLOW`](#constant-mood_yellow) | `MOOD_YELLOW` is the ink of the mood string in the inspector. |
| const | [`MOOD_SWITCH_GREY_TIP`](#constant-mood_switch_grey_tip) | `MOOD_SWITCH_GREY_TIP` is the hover text on the round mood switch when no mood string is saved. |
| const | [`MOOD_SWITCH_ON_TIP`](#constant-mood_switch_on_tip) | `MOOD_SWITCH_ON_TIP` is the hover text on the round mood switch while that switch is on. |
| const | [`MOOD_SWITCH_OFF_TIP`](#constant-mood_switch_off_tip) | `MOOD_SWITCH_OFF_TIP` is the hover text on the round mood switch while a saved string is off. |
| const | [`MOOD_LABEL`](#constant-mood_label) | `MOOD_LABEL` is the word on the Mood button. |
| const | [`MOOD_TITLE`](#constant-mood_title) | `MOOD_TITLE` is the title word of the mood glass. |
| const | [`MOOD_OPEN_W`](#constant-mood_open_w) | `MOOD_OPEN_W` is the mood-glass width used when a stored side is missing. |
| const | [`MOOD_OPEN_H`](#constant-mood_open_h) | `MOOD_OPEN_H` is the mood-glass height used when a stored side is missing. |
| const | [`MOOD_LINE_MISS`](#constant-mood_line_miss) | `MOOD_LINE_MISS` is the notice body after three mood replies that contain no mood line. |
| const | [`MOOD_KEYWORD`](#constant-mood_keyword) | `MOOD_KEYWORD` is the status text when a mood send does not leave the glass. |
| const | [`MOOD_NO_CELL`](#constant-mood_no_cell) | `MOOD_NO_CELL` is the status text when the mood glass has no card to use. |
| const | [`MOOD_APPROVE`](#constant-mood_approve) | `MOOD_APPROVE` is the word on the Approve button in the mood glass. |
| const | [`MOOD_CANCEL`](#constant-mood_cancel) | `MOOD_CANCEL` is the word on the Cancel button in the mood glass. |
| const | [`MOOD_CLEAR`](#constant-mood_clear) | `MOOD_CLEAR` is the word on the Clear mood button in the mood glass. |
| const | [`SSH_MOOD_BOX`](#constant-ssh_mood_box) | `SSH_MOOD_BOX` is the label on the SSH mood checkbox in the mood glass. |
| const | [`SSH_MOOD_STATUS`](#constant-ssh_mood_status) | `SSH_MOOD_STATUS` is the status-strip text while the SSH mood gate is on. |
| const | [`SHELL_MOOD_MARK`](#constant-shell_mood_mark) | `SHELL_MOOD_MARK` is the opening of the canvas-channel body that sets one SSH pane's mood. |
| const | [`SHELL_MOOD_CLEAR`](#constant-shell_mood_clear) | `SHELL_MOOD_CLEAR` is the canvas-channel body that clears one SSH pane's mood. |
| const | [`SSH_MOOD_TIP`](#constant-ssh_mood_tip) | `SSH_MOOD_TIP` is the hover text on the SSH mood checkbox in the mood glass. |
| const | [`MOOD_YOU`](#constant-mood_you) | `MOOD_YOU` is the ink of a user line in the mood glass. |
| const | [`MOOD_ASSIST`](#constant-mood_assist) | `MOOD_ASSIST` is the ink of an assistant line in the mood glass. |
| const | [`MOOD_GLASS_BG`](#constant-mood_glass_bg) | `MOOD_GLASS_BG` is the background fill of the mood glass. |
| const | [`MOOD_GLASS_INK`](#constant-mood_glass_ink) | `MOOD_GLASS_INK` is the ordinary text color inside the mood glass. |
| const | [`MOOD_GLASS_EDGE`](#constant-mood_glass_edge) | `MOOD_GLASS_EDGE` is the border color of the mood glass. |
| const | [`MOOD_SHAPE_SYSTEM`](#constant-mood_shape_system) | `MOOD_SHAPE_SYSTEM` is the system text that opens a mood-shaping send. |
| const | [`MOOD_FORMAT_ASK`](#constant-mood_format_ask) | `MOOD_FORMAT_ASK` is the reminder sent when a mood reply has no mood line. |
| const | [`MOOD_BUTTON_TIP`](#constant-mood_button_tip) | `MOOD_BUTTON_TIP` is the hover text on the Mood button in the canvas tool row. |
| const | [`MOOD_LINE_TIP`](#constant-mood_line_tip) | `MOOD_LINE_TIP` is the hover text on the line field in the mood glass. |
| const | [`MOOD_DRAFT_TIP`](#constant-mood_draft_tip) | `MOOD_DRAFT_TIP` is the hover text on the mood-string field in the mood glass. |
| const | [`MOOD_APPROVE_TIP`](#constant-mood_approve_tip) | `MOOD_APPROVE_TIP` is the hover text on the Approve button in the mood glass. |
| const | [`MOOD_CANCEL_TIP`](#constant-mood_cancel_tip) | `MOOD_CANCEL_TIP` is the hover text on the Cancel button in the mood glass. |
| const | [`MOOD_CLEAR_TIP`](#constant-mood_clear_tip) | `MOOD_CLEAR_TIP` is the hover text on the Clear mood button in the mood glass. |
| const | [`MOOD_ASK_TITLE`](#constant-mood_ask_title) | `MOOD_ASK_TITLE` is the title of the window that asks before a mood is cleared. |
| const | [`MOOD_ASK_WORDS`](#constant-mood_ask_words) | `MOOD_ASK_WORDS` is the question in the clear-mood window. |
| const | [`MOOD_YES`](#constant-mood_yes) | `MOOD_YES` is the word on the Yes button in the clear-mood window. |
| const | [`MOOD_NO`](#constant-mood_no) | `MOOD_NO` is the word on the No button in the clear-mood window. |
| const | [`MOOD_YES_TIP`](#constant-mood_yes_tip) | `MOOD_YES_TIP` is the hover text on the Yes button in the clear-mood window. |
| const | [`MOOD_NO_TIP`](#constant-mood_no_tip) | `MOOD_NO_TIP` is the hover text on the No button in the clear-mood window. |
| const | [`MOOD_SPIN_TIP`](#constant-mood_spin_tip) | `MOOD_SPIN_TIP` is the hover text on the spinner in the mood glass. |
| const | [`SSH_MOOD_YAML_FILE`](#constant-ssh_mood_yaml_file) | `SSH_MOOD_YAML_FILE` is the file name of the SSH mood checkbox record. |
| const | [`MARK_CLASS`](#constant-mark_class) | The constant `MARK_CLASS` is the egui class on the I, E, P, and O marks. |
| const | [`CANVAS_WEIGHT`](#constant-canvas_weight) | Open canvas share. Swing `setResizeWeight(0.72)`. A drag moves the live share. |
| const | [`SPLIT_BAR_PX`](#constant-split_bar_px) | Grab bar. Swing `DIVIDER_SIZE` is 11. |
| const | [`SPLIT_FILL`](#constant-split_fill) | Bar fill. Swing `DarkDivider` uses `0x3a3a3a`. |
| const | [`SPLIT_EDGE`](#constant-split_edge) | Bar line. Swing `DarkDivider` uses `0x8a8a8a`. |
| const | [`DIVIDER_SAVE_MS`](#constant-divider_save_ms) | Door write delay. Swing restarts a 400 ms timer on each move. |
| const | [`DIVIDER_SPAN_MIN`](#constant-divider_span_min) | Swing skips the ratio while the span is under 32. |
| const | [`ARROW_W`](#constant-arrow_w) | One-touch button. The width is the bar, so the control stays on the divider. |
| const | [`ARROW_H`](#constant-arrow_h) | `ARROW_H` is the height of one divider arrow button, in pixels. |
| const | [`ARROW_GAP`](#constant-arrow_gap) | Gap between the two buttons. The pair stays centered on the bar. |
| const | [`ARROW_ROUND`](#constant-arrow_round) | `ARROW_ROUND` is the corner radius of a divider arrow button, in pixels. |
| const | [`ARROW_INSET`](#constant-arrow_inset) | Triangle margin. Narrower than the old mark so the icon fits the bar. |
| const | [`ARROW_FILL`](#constant-arrow_fill) | Off-gray, lighter than the bar, so the button is noticeable. |
| const | [`ARROW_HOT`](#constant-arrow_hot) | `ARROW_HOT` is the fill of a divider arrow button while the pointer is on it. |
| const | [`ARROW_EDGE`](#constant-arrow_edge) | `ARROW_EDGE` is the border color of a divider arrow button. |
| const | [`ARROW_ICON`](#constant-arrow_icon) | `ARROW_ICON` is the color of the triangle on a divider arrow button. |
| const | [`WRAP_COLUMNS`](#constant-wrap_columns) | Missing shell column. Swing `getAetherisWrapColumns` uses 80. |
| const | [`WRAP_TYPE_MS`](#constant-wrap_type_ms) | Typed column count. Swing's Incrementor waits 800 ms. |
| const | [`SOFT_WRAP_LABEL`](#constant-soft_wrap_label) | `SOFT_WRAP_LABEL` is the word on the soft-wrap checkbox. |
| const | [`SOFT_WRAP_TIP`](#constant-soft_wrap_tip) | `SOFT_WRAP_TIP` is the hover text on the soft-wrap checkbox in the inspector column. |
| const | [`WRAP_MINUS_TIP`](#constant-wrap_minus_tip) | `WRAP_MINUS_TIP` is the hover text on the minus button beside the soft-wrap count. |
| const | [`WRAP_PLUS_TIP`](#constant-wrap_plus_tip) | `WRAP_PLUS_TIP` is the hover text on the plus button beside the soft-wrap count. |
| const | [`WRAP_FIELD_TIP`](#constant-wrap_field_tip) | `WRAP_FIELD_TIP` is the hover text on the soft-wrap count field. |
| const | [`LINE_CHARS`](#constant-line_chars) | Missing inspector line length. Swing `getAetherisInspectorLineChars` uses 80. |
| const | [`LINE_MINUS_TIP`](#constant-line_minus_tip) | `LINE_MINUS_TIP` is the hover text on the minus button for the inspector line length. |
| const | [`LINE_PLUS_TIP`](#constant-line_plus_tip) | `LINE_PLUS_TIP` is the hover text on the plus button for the inspector line length. |
| const | [`LINE_FIELD_TIP`](#constant-line_field_tip) | `LINE_FIELD_TIP` is the hover text on the inspector line-length field. |
| const | [`SCOPE_PER_TURN`](#constant-scope_per_turn) | Scope A pieces applied on one turn of the window loop. |
| const | [`PAINT_PER_TURN`](#constant-paint_per_turn) | Terminal bursts fed on one turn. The rest stays queued. |
| const | [`IDLE_POLL_MS`](#constant-idle_poll_ms) | Quiet session poll. A backlog uses the short poll. |
| const | [`COMMIT_POLL_MS`](#constant-commit_poll_ms) | `COMMIT_POLL_MS` is the short wait, in milliseconds, while a live session still has work queued. |
| const | [`WINDOW_TITLE`](#constant-window_title) | Plate name. Never built from the password. The OS caption is [`WINDOW_CAPTION`]. |
| const | [`WINDOW_CAPTION`](#constant-window_caption) | Taskbar hover and the window caption. Windows reads the title. |
| const | [`WINDOW_ICON_PX`](#constant-window_icon_px) | Title-bar and taskbar icon edge. The art is the Java Eye of Horus jpeg. |
| const | [`INPUT_GREEN`](#constant-input_green) | Swing `INPUT_GREEN`. The prompt on a working card. |
| const | [`OUTPUT_YELLOW`](#constant-output_yellow) | Swing `OUTPUT_YELLOW`. A reply that has text. |
| const | [`RESPONSE_GRAY`](#constant-response_gray) | Swing `RESPONSE_GRAY`. A reply whose text is blank. |
| const | [`PANE_EDGE_PX`](#constant-pane_edge_px) | Java `PANE_EDGE_PX`. The optional canvas text frame. |
| const | [`PANE_STROKE_ROUND`](#constant-pane_stroke_round) | Java `PANE_STROKE_ROUND`. The field corner plus the stroke. |
| const | [`SINK_SYSTEM`](#constant-sink_system) | `SINK_SYSTEM` is the role byte for a system row in the sink. |
| const | [`SINK_USER`](#constant-sink_user) | `SINK_USER` is the role byte for a user row in the sink. |
| const | [`SINK_ASSISTANT`](#constant-sink_assistant) | `SINK_ASSISTANT` is the role byte for an assistant row in the sink. |
| const | [`SINK_TOOL`](#constant-sink_tool) | `SINK_TOOL` is the role byte for a tool row in the sink. |
| const | [`SINK_TOOL_CALL`](#constant-sink_tool_call) | `SINK_TOOL_CALL` is the role byte for a tool-call row in the sink. |
| const | [`APPLE_THUMB_SHARE`](#constant-apple_thumb_share) | The handle is this share of the pane. It does not follow the scroll range. |
| const | [`INPUT_EDGE_PAD`](#constant-input_edge_pad) | Inset of the input-edge caption from each side of the pane. |
| const | [`CELL_CLEAR_PX`](#constant-cell_clear_px) | Air under the embedded line, and air between that line and the cell above. |
| const | [`FOOTER_EDGE_PX`](#constant-footer_edge_px) | Stroke on the button bar. The same grey as the divider line. |
| const | [`FOOTER_ROUND`](#constant-footer_round) | Corner of the bar. A square with a short radius, not a pill. |
| const | [`FOOTER_PAD`](#constant-footer_pad) | Space inside the stroke so the buttons do not touch it. |
| const | [`FOOTER_OUT`](#constant-footer_out) | Space outside the stroke so the bar does not kiss the column edge. |
| const | [`FOOTER_FILL`](#constant-footer_fill) | Plate behind the buttons. A step above the canvas so the bar reads. |
| const | [`HEAD_LOCK_OPEN`](#constant-head_lock_open) | Open shackle. The right leg stops at the crown. A hash is ink. A dot is clear. |
| const | [`HEAD_LOCK_LATCHED`](#constant-head_lock_latched) | Closed shackle. Both legs meet the body. A hash is ink. A dot is clear. |
| const | [`HEAD_LOCK_TIP`](#constant-head_lock_tip) | The tip on the padlock. The lock is separate from I, E, P, and O. |
| static | [`PIN_FIELD_RECT`](#constant-pin_field_rect) | `PIN_FIELD_RECT` is the test slot for the pin word field. |
| static | [`PIN_BOX_RECT`](#constant-pin_box_rect) | `PIN_BOX_RECT` is the test slot for the pin checkbox. |
| static | [`PIN_HIT_CARD`](#constant-pin_hit_card) | `PIN_HIT_CARD` is the test slot for a card that covers the pin field. |
| static | [`PIN_SCROLL_CLIP`](#constant-pin_scroll_clip) | `PIN_SCROLL_CLIP` is the test slot for the canvas card scroll clip. |
| static | [`PIN_FORCE_SCROLL`](#constant-pin_force_scroll) | `PIN_FORCE_SCROLL` is the test flag that jumps the canvas scroll to the bottom. |
| static | [`CANVAS_STRIP_RECT`](#constant-canvas_strip_rect) | `CANVAS_STRIP_RECT` is the test slot for the canvas thinking strip. |
| static | [`CANVAS_CARD_CLIP`](#constant-canvas_card_clip) | `CANVAS_CARD_CLIP` is the test slot for the first card's clip. |
| static | [`CANVAS_CARD_RECT`](#constant-canvas_card_rect) | `CANVAS_CARD_RECT` is the test slot for the first card's rectangle. |
| static | [`PIN_MARK_RECT`](#constant-pin_mark_rect) | `PIN_MARK_RECT` is the test slot for the folded pin mark. |
| static | [`PIN_ROW_RECT`](#constant-pin_row_rect) | `PIN_ROW_RECT` is the test slot for the pin row. |
| static | [`THINK_GRIP_RECT`](#constant-think_grip_rect) | `THINK_GRIP_RECT` is the test slot for the thinking grip row. |
| static | [`THINK_GRIP_DRAG_RECT`](#constant-think_grip_drag_rect) | `THINK_GRIP_DRAG_RECT` is the test slot for the thinking drag region. |
| static | [`THINK_CLEAR_RECT`](#constant-think_clear_rect) | `THINK_CLEAR_RECT` is the test slot for the thinking Clear button. |
| static | [`THINK_FOLD_RECT`](#constant-think_fold_rect) | `THINK_FOLD_RECT` is the test slot for the folded thinking fold button. |
| static | [`BB_FOLD_RECT`](#constant-bb_fold_rect) | `BB_FOLD_RECT` is the test slot for the button-bar fold mark. |
| static | [`BB_FOLD_LINE`](#constant-bb_fold_line) | `BB_FOLD_LINE` is the test slot for the line beside a folded button bar. |
| static | [`BB_RUN_RECT`](#constant-bb_run_rect) | `BB_RUN_RECT` is the test slot for the Run button. |
| static | [`THINK_CLOSE_RECT`](#constant-think_close_rect) | `THINK_CLOSE_RECT` is the test slot for the Close button on a folded thinking row. |
| static | [`CANVAS_TAB_RECT`](#constant-canvas_tab_rect) | `CANVAS_TAB_RECT` is the test slot for the canvas tab. |
| static | [`CANVAS_NORTH_LAYER`](#constant-canvas_north_layer) | `CANVAS_NORTH_LAYER` is the test slot for the canvas header layer. |
| static | [`CONTROL_MARK_RECT`](#constant-control_mark_rect) | `CONTROL_MARK_RECT` is the test slot for the connection-bar fold mark. |
| static | [`USER_COMBO_RECT`](#constant-user_combo_rect) | `USER_COMBO_RECT` is the test slot for the user combo. |
| static | [`USER_SPIN_RECT`](#constant-user_spin_rect) | `USER_SPIN_RECT` is the test slot for a user spinner rectangle. |
| const | [`USER_COMBO_W`](#constant-user_combo_w) | Preferred width of the connected-user pull-down. Java sets 160. |
| const | [`USER_COMBO_MAX`](#constant-user_combo_max) | Ceiling so a long name stays off the master buttons. Java sets 180. |
| const | [`USER_COMBO_ID`](#constant-user_combo_id) | Distinct from the connection-strip accounts pull-down. |
| const | [`BB_FOLD_TIP`](#constant-bb_fold_tip) | Open mark. Click folds BB1 and BB2. |
| const | [`BB_SHOW_TIP`](#constant-bb_show_tip) | Folded mark. Click restores BB1 and BB2. |
| const | [`SHELL_FONT_MIN`](#constant-shell_font_min) | Swing spinner floor. `DEFAULT_FONT_SIZE` is the same number. |
| const | [`SHELL_FONT_MAX`](#constant-shell_font_max) | Swing spinner ceiling. |
| const | [`FRAME_MIN_W`](#constant-frame_min_w) | A restored frame under this width is not placed. |
| const | [`FRAME_MIN_H`](#constant-frame_min_h) | A restored frame under this height is not placed. |
| const | [`CONSOLE_SAMPLE`](#constant-console_sample) | Console import and Send file sample this many leading bytes. |
| const | [`SCOPE_PARK_CAP`](#constant-scope_park_cap) | A background pane keeps this many scope pieces. The canvas applies them on focus. |
| const | [`PANE_GAP`](#constant-pane_gap) | The gap between two panes. The divider sits in it. |
| const | [`EXIT_TITLE`](#constant-exit_title) | The constant `EXIT_TITLE` is the title of the exit confirmation box. |
| const | [`EXIT_BODY`](#constant-exit_body) | The constant `EXIT_BODY` is the question in the exit confirmation box. |
| const | [`EXIT_POS_W`](#constant-exit_pos_w) | Title box used to decide whether a saved exit origin meets the view. |
| const | [`EXIT_POS_H`](#constant-exit_pos_h) | The constant `EXIT_POS_H` is the height of the box used to test a saved exit origin. |
| const | [`NEW_TERM_TITLE`](#constant-new_term_title) | The constant `NEW_TERM_TITLE` is the title of the new-terminal box. |
| const | [`NEW_TERM_BODY`](#constant-new_term_body) | The constant `NEW_TERM_BODY` is the prompt in the new-terminal box. |
| const | [`PASTE_TITLE`](#constant-paste_title) | The constant `PASTE_TITLE` is the title of the paste editor. |
| const | [`PASTE_HINT`](#constant-paste_hint) | The constant `PASTE_HINT` is the hint in the paste editor. |
| const | [`PASTE_OVER`](#constant-paste_over) | The constant `PASTE_OVER` is the notice when pasted text is longer than 65536 characters. |
| const | [`FILE_BINARY`](#constant-file_binary) | The constant `FILE_BINARY` is the notice when a file contains a NUL byte. |
| const | [`FILE_EMPTY`](#constant-file_empty) | The constant `FILE_EMPTY` is the notice for an empty file. |
| const | [`FILE_OVER`](#constant-file_over) | The constant `FILE_OVER` is the notice when a file is too long. |
| const | [`NOT_CONNECTED`](#constant-not_connected) | The constant `NOT_CONNECTED` is the notice when no session is connected. |
| const | [`ABOUT_TITLE`](#constant-about_title) | The constant `ABOUT_TITLE` is the title of the About box. |
| const | [`ABOUT_APP_PX`](#constant-about_app_px) | App mark on the About box. Half the prior edge. The ground is black. |
| const | [`ABOUT_BODY`](#constant-about_body) | The constant `ABOUT_BODY` is the body of the About box. |
| const | [`HELP_TITLE`](#constant-help_title) | The constant `HELP_TITLE` is the title of the Help box. |
| const | [`HELP_BODY`](#constant-help_body) | The constant `HELP_BODY` is the body of the Help box. |
| const | [`IMPORT_MENU`](#constant-import_menu) | The constant `IMPORT_MENU` is the Import line on a menu. |
| const | [`EXPORT_SESSION_LABEL`](#constant-export_session_label) | The constant `EXPORT_SESSION_LABEL` is the Export Session line on a menu. |
| const | [`EXPORT_SESSION_TIP`](#constant-export_session_tip) | The constant `EXPORT_SESSION_TIP` is the hover text for Export Session. |
| const | [`EXPORT_SESSION_RAW`](#constant-export_session_raw) | The constant `EXPORT_SESSION_RAW` is the notice for a console that has no session export. |
| const | [`SESSION_EXPORT_ASK`](#constant-session_export_ask) | The constant `SESSION_EXPORT_ASK` is the request line that asks for a session export. |
| const | [`SESSION_REPLACE_ASK`](#constant-session_replace_ask) | The constant `SESSION_REPLACE_ASK` is the request line that asks for a session replace. |
| const | [`TRANSFER_TITLE`](#constant-transfer_title) | The constant `TRANSFER_TITLE` is the title of the transfer box. |
| const | [`TRANSFER_TO_CANVAS_BODY`](#constant-transfer_to_canvas_body) | The constant `TRANSFER_TO_CANVAS_BODY` is the body of the confirm box that asks before a console session is copied onto the canvas. |
| const | [`TRANSFER_TO_ROUX_BODY`](#constant-transfer_to_roux_body) | The constant `TRANSFER_TO_ROUX_BODY` is the body of the confirm box that asks before the canvas is copied into the Roux chat. |
| const | [`TRANSFER_CANVAS_TIP`](#constant-transfer_canvas_tip) | The constant `TRANSFER_CANVAS_TIP` is the hover text on the canvas menu line `Transfer Conversation`. |
| const | [`TRANSFER_CONSOLE_TIP`](#constant-transfer_console_tip) | The constant `TRANSFER_CONSOLE_TIP` is the hover text on the console menu line `Transfer Conversation`. |
| const | [`TRANSFER_WORD`](#constant-transfer_word) | The constant `TRANSFER_WORD` is the status text `conversation transferred`. |
| const | [`TRANSFER_BUSY`](#constant-transfer_busy) | The constant `TRANSFER_BUSY` is the status text `a turn is still running`. |
| const | [`TRANSFER_TABLE`](#constant-transfer_table) | The constant `TRANSFER_TABLE` is the error `replace failed: the table is not a message table`. |
| const | [`TRANSFER_FIT`](#constant-transfer_fit) | The constant `TRANSFER_FIT` is the status text `replace failed: the session does not fit in one frame`. |
| const | [`SESSION_EXPORT_SAVE`](#constant-session_export_save) | The constant `SESSION_EXPORT_SAVE` is the PowerShell script that opens a Save dialog titled `Export Session`. |
| const | [`CLOSE_PANE_MENU`](#constant-close_pane_menu) | The constant `CLOSE_PANE_MENU` is the File menu label `Close Pane (Ctrl+Shift+W)`. |
| const | [`CONSOLE_MENU`](#constant-console_menu) | JediTerm's console menu, then Import, then Export Session, then Transfer Conversation, then Select model. |
| const | [`CONSOLE_MENU_GAP`](#constant-console_menu_gap) | A separator sits ahead of each of these lines. Java `separatorBefore`, plus the Import gap. |
| const | [`DEFAULT_WIDTH`](#constant-default_width) | The constant `DEFAULT_WIDTH` is the window width used when no usable saved rectangle exists. |
| const | [`DEFAULT_HEIGHT`](#constant-default_height) | The constant `DEFAULT_HEIGHT` is the window height used when no usable saved rectangle exists. |
| const | [`MIN_WIDTH`](#constant-min_width) | The constant `MIN_WIDTH` is the smallest inner width the window will keep. |
| const | [`MIN_HEIGHT`](#constant-min_height) | The constant `MIN_HEIGHT` is the smallest inner height the window will keep. |
| const | [`OPEN_MAXIMIZED`](#constant-open_maximized) | The window opens maximized. |
| const | [`OPEN_ZOOM_STEPS`](#constant-open_zoom_steps) | Ctrl-+ presses on a fresh start. egui adds one tenth and rounds. |
| const | [`ZOOM_STEP`](#constant-zoom_step) | The constant `ZOOM_STEP` is the zoom added for one Ctrl-+ step. |
| const | [`PLATE`](#constant-plate) | The constant `PLATE` is the near-black fill behind the panes and the canvas. |
| const | [`STATUS_BG`](#constant-status_bg) | Java `DEFAULT_STATUS_BACKGROUND`. The thin bar under the Aetheris pane. |
| const | [`STATUS_FG`](#constant-status_fg) | Java `DEFAULT_STATUS_FOREGROUND`. |
| const | [`STATUS_PAD_X`](#constant-status_pad_x) | Java status `EmptyBorder` left and right. |
| const | [`STATUS_PAD_Y`](#constant-status_pad_y) | Java status `EmptyBorder` top and bottom. |
| const | [`INK`](#constant-ink) | The constant `INK` is the default terminal foreground. |
| const | [`SELECT_BG`](#constant-select_bg) | The constant `SELECT_BG` is the background of a selected terminal cell. |
| const | [`SELECT_FG`](#constant-select_fg) | The constant `SELECT_FG` is the foreground of a selected terminal cell. |
| const | [`CURSOR`](#constant-cursor) | The constant `CURSOR` is the cyan used for a 1-pixel inside stroke. |

## Function Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`emerald_combo_arrow`](#function-emerald_combo_arrow) | The function `emerald_combo_arrow` paints the pull-down arrow on a combo box. |
| fn | [`think_tag_word`](#function-think_tag_word) | The function `think_tag_word` builds the short tag from a model name. |
| fn | [`model_after_slash`](#function-model_after_slash) | The function `model_after_slash` returns the text after the first slash. |
| fn | [`short_model_name`](#function-short_model_name) | The function `short_model_name` returns the short model label. |
| fn | [`think_model_word`](#function-think_model_word) | The function `think_model_word` builds the tag word for a model target. |
| fn | [`think_paints`](#function-think_paints) | The function `think_paints` decides whether a thought is painted. |
| fn | [`think_tag_line`](#function-think_tag_line) | The function `think_tag_line` builds the cyan model tag. |
| fn | [`think_pane_height`](#function-think_pane_height) | The function `think_pane_height` returns the floor height of the thought pane. |
| fn | [`think_mark`](#function-think_mark) | The function `think_mark` returns the fold button mark. |
| fn | [`fold_think`](#function-fold_think) | The function `fold_think` folds or restores the text on this strip. |
| fn | [`close_think`](#function-close_think) | The function `close_think` hides the thought pane. |
| fn | [`reveal_think`](#function-reveal_think) | The function `reveal_think` shows the pane for a new thought. |
| fn | [`open_vt_strip`](#function-open_vt_strip) | The function `open_vt_strip` opens the thought strip for this SSH session. |
| fn | [`show_thinking`](#function-show_thinking) | The function `show_thinking` is the Tools command Show Thinking. |
| fn | [`track_focused_think`](#function-track_focused_think) | The function `track_focused_think` chooses which think flag is in force. |
| fn | [`console_piece_opens`](#function-console_piece_opens) | The function `console_piece_opens` reports whether a scope piece turns thinking on. |
| fn | [`seat_vt_chrome`](#function-seat_vt_chrome) | The function `seat_vt_chrome` builds the thought strip for one SSH session. |
| fn | [`keep_vt_session`](#function-keep_vt_session) | The function `keep_vt_session` copies one session strip back out of [`ThinkChrome`]. |
| fn | [`clear_think`](#function-clear_think) | The function `clear_think` empties the thought pane. |
| fn | [`think_max_height`](#function-think_max_height) | The function `think_max_height` returns the tallest scroll height the pane may use. |
| fn | [`think_scroll_height`](#function-think_scroll_height) | The function `think_scroll_height` returns the height of the thought scroll. |
| fn | [`rise_think`](#function-rise_think) | The function `rise_think` advances one step of the rising notebook strip. |
| fn | [`drag_think_height`](#function-drag_think_height) | The function `drag_think_height` changes the notebook pane height from a drag. |
| fn | [`drag_vt_think_height`](#function-drag_vt_think_height) | The function `drag_vt_think_height` changes the VT100 thought-pane height from a drag. |
| fn | [`think_log_path`](#function-think_log_path) | The function `think_log_path` builds the path of the thought log. |
| fn | [`think_yaml_path`](#function-think_yaml_path) | The function `think_yaml_path` builds the path of the shared think yaml. |
| fn | [`think_log_text`](#function-think_log_text) | The function `think_log_text` renders the thought log as one string. |
| fn | [`parse_think_log`](#function-parse_think_log) | The function `parse_think_log` reads a thought log back into blocks. |
| fn | [`write_think_log`](#function-write_think_log) | The function `write_think_log` writes the thought log to disk. |
| fn | [`read_think_log`](#function-read_think_log) | The function `read_think_log` loads the thought log from disk. |
| fn | [`load_think_file`](#function-load_think_file) | The function `load_think_file` loads the thought log, the pane state, and the yaml. |
| fn | [`store_think_file`](#function-store_think_file) | The function `store_think_file` writes the thought log. |
| fn | [`yaml_scalar`](#function-yaml_scalar) | The function `yaml_scalar` reads one plain value from the text after a colon. |
| fn | [`split_yaml_key`](#function-split_yaml_key) | The function `split_yaml_key` splits one top-level yaml line into a key and the rest. |
| fn | [`think_version_one`](#function-think_version_one) | The function `think_version_one` reports whether a yaml value is version 1. |
| fn | [`think_flag`](#function-think_flag) | The function `think_flag` reads a yaml yes-or-no value. |
| fn | [`think_kept_height`](#function-think_kept_height) | The function `think_kept_height` reads a dragged height that the file may keep. |
| fn | [`parse_think_yaml`](#function-parse_think_yaml) | The function `parse_think_yaml` reads the text of `aetheris-think.yaml`. |
| fn | [`render_think_yaml`](#function-render_think_yaml) | The function `render_think_yaml` writes a [`ThinkYaml`] back to text. |
| fn | [`read_think_yaml`](#function-read_think_yaml) | The function `read_think_yaml` loads `aetheris-think.yaml` from disk. |
| fn | [`write_think_yaml`](#function-write_think_yaml) | The function `write_think_yaml` writes `aetheris-think.yaml`. |
| fn | [`note_think_closed`](#function-note_think_closed) | The function `note_think_closed` sets the closed mark on the think yaml. |
| fn | [`note_think_opened`](#function-note_think_opened) | The function `note_think_opened` clears the closed mark after a later thought. |
| fn | [`note_think_height`](#function-note_think_height) | The function `note_think_height` stores a dragged thought-pane height. |
| fn | [`nil_cell`](#function-nil_cell) | The function `nil_cell` reports the console cell. |
| fn | [`vt_think_yaml_path`](#function-vt_think_yaml_path) | The function `vt_think_yaml_path` builds the path of the VT100 think file. |
| fn | [`parse_vt_think_yaml`](#function-parse_vt_think_yaml) | The function `parse_vt_think_yaml` reads the text of `aetheris-vt100-think.yaml`. |
| fn | [`render_vt_think_yaml`](#function-render_vt_think_yaml) | The function `render_vt_think_yaml` writes a [`VtThinkYaml`] back to text. |
| fn | [`read_vt_think_yaml`](#function-read_vt_think_yaml) | The function `read_vt_think_yaml` loads `aetheris-vt100-think.yaml` from disk. |
| fn | [`write_vt_think_yaml`](#function-write_vt_think_yaml) | The function `write_vt_think_yaml` writes `aetheris-vt100-think.yaml`. |
| fn | [`note_vt_closed`](#function-note_vt_closed) | The function `note_vt_closed` sets the closed mark on the VT100 think file. |
| fn | [`note_vt_opened`](#function-note_vt_opened) | The function `note_vt_opened` clears the closed mark on the VT100 think file. |
| fn | [`note_vt_height`](#function-note_vt_height) | The function `note_vt_height` stores a dragged VT100 thought-pane height. |
| fn | [`seat_vt_home`](#function-seat_vt_home) | The function `seat_vt_home` applies the VT100 think file to a new pane. |
| fn | [`load_vt_think`](#function-load_vt_think) | The function `load_vt_think` loads the VT100 think file and a pane to match it. |
| fn | [`seat_think_home`](#function-seat_think_home) | The function `seat_think_home` applies the shared think file to the notebook pane. |
| fn | [`think_line_indent`](#function-think_line_indent) | The function `think_line_indent` counts the leading spaces on a yaml line. |
| fn | [`think_field_name`](#function-think_field_name) | The function `think_field_name` reads the field name on one yaml line. |
| fn | [`think_field_value`](#function-think_field_value) | The function `think_field_value` reads the value on one yaml field line. |
| fn | [`split_think_cells`](#function-split_think_cells) | The function `split_think_cells` splits the `cells` block into a header and one block per cell. |
| fn | [`join_think_cells`](#function-join_think_cells) | The function `join_think_cells` writes the `cells` block back to text. |
| fn | [`think_snap_of_block`](#function-think_snap_of_block) | The function `think_snap_of_block` reads the five think fields from one cell block. |
| fn | [`think_scalar_text`](#function-think_scalar_text) | The function `think_scalar_text` renders one think field value for yaml. |
| fn | [`put_think_field`](#function-put_think_field) | The function `put_think_field` writes one indented field into a cell block. |
| fn | [`patch_think_block`](#function-patch_think_block) | The function `patch_think_block` updates one cell block to match a think snapshot. |
| fn | [`seat_cell_think`](#function-seat_cell_think) | The function `seat_cell_think` copies saved think fields onto the matching cards. |
| fn | [`think_open_echo`](#function-think_open_echo) | The function `think_open_echo` reports an echo that is not a checkbox edit. |
| fn | [`note_cell_think`](#function-note_cell_think) | The function `note_cell_think` writes one cell's five think fields into the yaml. |
| fn | [`mood_yaml_path`](#function-mood_yaml_path) | `~/.casserole/aetheris-mood.yaml`. The shared record. A per-user file wins. |
| fn | [`mood_user_path`](#function-mood_user_path) | `~/.casserole/aetheris-mood-{user}.yaml`. The name is sanitized. |
| fn | [`any_user_mood_file`](#function-any_user_mood_file) | True when any per-user mood file is already on disk. |
| fn | [`claim_shared_mood`](#function-claim_shared_mood) | The first seated user claims the shared file only while no per-user file exists. |
| fn | [`seated_mood_line`](#function-seated_mood_line) | The line one user seats. A user who is not the owner does not read the showing record. |
| fn | [`mood_has_line`](#function-mood_has_line) | The function `mood_has_line` reports whether a [`MoodYaml`] holds a mood string. |
| fn | [`mood_sends`](#function-mood_sends) | The function `mood_sends` reports whether a canvas send carries the mood string and the shape line. |
| fn | [`ssh_mood_live`](#function-ssh_mood_live) | The function `ssh_mood_live` reports whether an SSH pane should take the mood. |
| fn | [`ssh_mood_line`](#function-ssh_mood_line) | The function `ssh_mood_line` returns the mood string an SSH pane should hold. |
| fn | [`ssh_mood_seat`](#function-ssh_mood_seat) | The function `ssh_mood_seat` returns the mood line an SSH pane should hold. |
| fn | [`shell_mood_body`](#function-shell_mood_body) | The function `shell_mood_body` builds the channel A body that sets one pane's mood. |
| fn | [`shell_status_mood`](#function-shell_status_mood) | The function `shell_status_mood` joins the left-hand status text with the mood gate. |
| fn | [`shell_status_with_facts`](#function-shell_status_with_facts) | The function `shell_status_with_facts` joins the SSH status, the mood gate, and the Aetheris facts. |
| fn | [`shell_status_with_tokens`](#function-shell_status_with_tokens) | The function `shell_status_with_tokens` appends the token estimate to the joined strip. |
| fn | [`session_memory_text`](#function-session_memory_text) | The function `session_memory_text` returns the console text one pane holds. |
| fn | [`session_memory_lines`](#function-session_memory_lines) | The function `session_memory_lines` counts the non-blank rows in pane text. |
| fn | [`ssh_mood_relay`](#function-ssh_mood_relay) | The function `ssh_mood_relay` builds the channel A body for one pane, or returns nothing. |
| fn | [`relay_one_ssh_mood`](#function-relay_one_ssh_mood) | The function `relay_one_ssh_mood` sends one pane's mood body when the pane must change. |
| fn | [`mood_quote`](#function-mood_quote) | The function `mood_quote` wraps one mood string as a double-quoted yaml scalar. |
| fn | [`mood_unquote`](#function-mood_unquote) | The function `mood_unquote` reads one quoted yaml scalar back into text. |
| fn | [`mood_key_line`](#function-mood_key_line) | The function `mood_key_line` splits one top-level mood line into a key and the text after the colon. |
| fn | [`mood_join_plain`](#function-mood_join_plain) | The function `mood_join_plain` joins a plain yaml value with the indented lines under it. |
| fn | [`mood_value`](#function-mood_value) | The function `mood_value` reads one mood-yaml value, including a block that continues on later lines. |
| fn | [`mood_kept_pair`](#function-mood_kept_pair) | The function `mood_kept_pair` reads a dialog width and height that the mood file may keep. |
| fn | [`parse_mood_yaml`](#function-parse_mood_yaml) | The function `parse_mood_yaml` reads the text of one mood yaml file into a [`MoodYaml`]. |
| fn | [`mood_pin_rows`](#function-mood_pin_rows) | The function `mood_pin_rows` reads the indented pin lines under the `pins` key. |
| fn | [`mood_pin_for`](#function-mood_pin_for) | The function `mood_pin_for` returns the pin stored for one canvas. |
| fn | [`set_mood_pin`](#function-set_mood_pin) | The function `set_mood_pin` stores one canvas pin on a [`MoodYaml`]. |
| fn | [`render_mood_yaml`](#function-render_mood_yaml) | The function `render_mood_yaml` writes version 1 of a [`MoodYaml`] as text. |
| fn | [`read_mood_yaml`](#function-read_mood_yaml) | The function `read_mood_yaml` loads one mood file from disk. |
| fn | [`write_mood_yaml`](#function-write_mood_yaml) | The function `write_mood_yaml` stores one [`MoodYaml`] when the record is not blocked. |
| fn | [`note_mood_flip`](#function-note_mood_flip) | The function `note_mood_flip` turns the mood switch the other way when a string is saved. |
| fn | [`load_mood_file`](#function-load_mood_file) | The function `load_mood_file` reads the shared mood file for this user. |
| fn | [`ssh_mood_yaml_path`](#function-ssh_mood_yaml_path) | The function `ssh_mood_yaml_path` builds the path of the SSH mood checkbox file. |
| fn | [`ssh_user_on`](#function-ssh_user_on) | The function `ssh_user_on` reports whether one canvas user turned the SSH mood box on. |
| fn | [`set_ssh_user`](#function-set_ssh_user) | The function `set_ssh_user` stores one user's SSH mood checkbox. |
| fn | [`render_ssh_mood`](#function-render_ssh_mood) | The function `render_ssh_mood` writes the SSH mood checkbox file as text. |
| fn | [`parse_ssh_mood`](#function-parse_ssh_mood) | The function `parse_ssh_mood` reads the text of the SSH mood checkbox file. |
| fn | [`load_ssh_mood_file`](#function-load_ssh_mood_file) | The function `load_ssh_mood_file` reads the SSH mood checkbox file for this user. |
| fn | [`write_ssh_mood`](#function-write_ssh_mood) | The function `write_ssh_mood` stores the SSH mood checkbox list. |
| fn | [`load_grok_home`](#function-load_grok_home) | The function `load_grok_home` loads the Grok settings document for this user. |
| fn | [`mint_pool_boot`](#function-mint_pool_boot) | The function `mint_pool_boot` builds one boot id for this window. |
| fn | [`mood_knob`](#function-mood_knob) | The function `mood_knob` returns the color and the side of the round mood button. |
| fn | [`preview_chars`](#function-preview_chars) | The function `preview_chars` shortens one mood string for a single preview line. |
| fn | [`mood_inspector_head`](#function-mood_inspector_head) | The function `mood_inspector_head` returns the inspector line for a mood that is not showing its yellow card. |
| fn | [`paint_inspector_mood`](#function-paint_inspector_mood) | The function `paint_inspector_mood` paints the yellow mood card at the head of the inspector scroll. |
| fn | [`mint_mood_wire`](#function-mint_mood_wire) | The function `mint_mood_wire` builds one mood wire id. |
| fn | [`mood_target`](#function-mood_target) | The function `mood_target` returns the model word one card would send. |
| fn | [`mood_focus_card`](#function-mood_focus_card) | The function `mood_focus_card` returns the canvas cell the mood glass uses. |
| fn | [`mood_glass_target`](#function-mood_glass_target) | The function `mood_glass_target` returns the model the mood glass sends. |
| fn | [`mood_glass_title`](#function-mood_glass_title) | The function `mood_glass_title` builds the title bar of the mood glass. |
| fn | [`mood_open_px`](#function-mood_open_px) | The function `mood_open_px` returns the width and height used to open the mood glass. |
| fn | [`note_mood_size`](#function-note_mood_size) | The function `note_mood_size` stores the mood dialog size for the next open. |
| fn | [`approve_mood_line`](#function-approve_mood_line) | The function `approve_mood_line` saves the mood-string field and turns the switch on. |
| fn | [`clear_mood_line`](#function-clear_mood_line) | The function `clear_mood_line` drops the saved mood string and turns the switch off. |
| fn | [`seed_mood_field`](#function-seed_mood_field) | The function `seed_mood_field` copies the saved mood into the rising field when the glass opens. |
| fn | [`mood_origin`](#function-mood_origin) | The function `mood_origin` returns the top-left point that centers the mood glass. |
| fn | [`mood_fit`](#function-mood_fit) | The function `mood_fit` clamps the mood glass to the app window and returns its origin and size. |
| fn | [`mood_slots`](#function-mood_slots) | The function `mood_slots` divides the mood glass into transcript, draft, and button heights. |
| fn | [`mood_floor`](#function-mood_floor) | The function `mood_floor` returns the smallest size the mood glass may be dragged to. |
| fn | [`mood_wrap_lines`](#function-mood_wrap_lines) | The function `mood_wrap_lines` counts the wrapped lines in the rising mood field. |
| fn | [`mood_pane_px`](#function-mood_pane_px) | The function `mood_pane_px` returns the pixel height of the rising mood pane. |
| fn | [`mood_slide_next`](#function-mood_slide_next) | The function `mood_slide_next` moves the rising mood pane one step toward its target height. |
| fn | [`advance_mood_slide`](#function-advance_mood_slide) | The function `advance_mood_slide` advances the rising pane and hides it after it shuts. |
| fn | [`mood_spin_line`](#function-mood_spin_line) | The function `mood_spin_line` returns the dialog clock while a mood reply is in flight. |
| fn | [`mood_spin_clock`](#function-mood_spin_clock) | The function `mood_spin_clock` returns the elapsed clock for a mood reply that is in flight. |
| fn | [`clear_ask_drops`](#function-clear_ask_drops) | The function `clear_ask_drops` reports whether a clear-mood choice drops the saved string. |
| fn | [`mood_choice_size`](#function-mood_choice_size) | The function `mood_choice_size` returns the size of one Yes or No button. |
| fn | [`button_ink`](#function-button_ink) | The function `button_ink` returns the ink for a button that paints its own words. |
| fn | [`mark_button`](#function-mark_button) | The function `mark_button` builds one I, E, P, or O button. |
| fn | [`seat_mark_button`](#function-seat_mark_button) | The function `seat_mark_button` paints one letter on one cell and returns the response. |
| fn | [`mark_pressed`](#function-mark_pressed) | The function `mark_pressed` reports a primary click on one I, E, P, or O letter. |
| fn | [`paint_menu_ink`](#function-paint_menu_ink) | The function `paint_menu_ink` keeps the main-menu words green. |
| fn | [`tip_delay`](#function-tip_delay) | The function `tip_delay` returns how long a pointer must rest before a hover tip opens. |
| fn | [`seat_button_hover`](#function-seat_button_hover) | The function `seat_button_hover` applies one hover delay and installs the emerald button theme once. |
| fn | [`add_mood_choice`](#function-add_mood_choice) | The function `add_mood_choice` paints one Yes or No button and returns its response. |
| fn | [`paint_clear_ask`](#function-paint_clear_ask) | The function `paint_clear_ask` paints the clear-mood question and returns the choice. |
| fn | [`mood_owns`](#function-mood_owns) | The function `mood_owns` reports whether a wire id belongs to the mood glass. |
| fn | [`take_mood_line`](#function-take_mood_line) | The function `take_mood_line` takes the typed mood line when Enter is accepted. |
| fn | [`begin_mood`](#function-begin_mood) | The function `begin_mood` decides whether the mood glass may open. |
| fn | [`begin_mood_doc`](#function-begin_mood_doc) | The function `begin_mood_doc` decides whether the mood glass may open for one document. |
| fn | [`mood_shape_frame`](#function-mood_shape_frame) | The function `mood_shape_frame` builds the channel A body for one mood-shaping send. |
| fn | [`mood_shape_tuned`](#function-mood_shape_tuned) | The function `mood_shape_tuned` builds the channel A body for one tuned mood-shaping send. |
| fn | [`mood_char`](#function-mood_char) | The function `mood_char` returns the character at a byte index and that character's UTF-8 length. |
| fn | [`mood_skip`](#function-mood_skip) | The function `mood_skip` advances a byte index across characters that should be ignored. |
| fn | [`mood_word`](#function-mood_word) | The function `mood_word` reports whether a word sits at a byte index. |
| fn | [`mood_mark_char`](#function-mood_mark_char) | The function `mood_mark_char` reports a character that may sit between a mood key and its colon. |
| fn | [`mood_clean`](#function-mood_clean) | The function `mood_clean` turns one raw mood value into the stored token string. |
| fn | [`mood_next_line`](#function-mood_next_line) | The function `mood_next_line` returns the next physical line after a mood key. |
| fn | [`mood_key_at`](#function-mood_key_at) | The function `mood_key_at` finds the first mood key at or after a byte index. |
| fn | [`mood_fence_at`](#function-mood_fence_at) | The function `mood_fence_at` finds the first fenced mood block at or after a byte index. |
| fn | [`mood_marks`](#function-mood_marks) | The function `mood_marks` reports whether one chat row carries a mood key. |
| fn | [`mood_pluck`](#function-mood_pluck) | The function `mood_pluck` returns the last mood value in a reply. |
| fn | [`mood_mark_word`](#function-mood_mark_word) | The function `mood_mark_word` returns the label painted beside one mood-glass turn. |
| fn | [`mood_paint_rows`](#function-mood_paint_rows) | The function `mood_paint_rows` splits the mood chat into one painted row per physical line. |
| fn | [`mood_piece_cell`](#function-mood_piece_cell) | The function `mood_piece_cell` returns the cell id carried by one scope piece. |
| fn | [`mood_take_reply`](#function-mood_take_reply) | The function `mood_take_reply` stores one mood reply and pulls a mood line out of it. |
| fn | [`mood_take_fault`](#function-mood_take_fault) | The function `mood_take_fault` turns one mood-wire fault into a slide notice. |
| fn | [`intake_mood`](#function-intake_mood) | The function `intake_mood` consumes a frame whose id belongs to the mood glass. |
| fn | [`accept_thought`](#function-accept_thought) | The function `accept_thought` appends kind `t` text onto the thinking log. |
| fn | [`close_thought`](#function-close_thought) | The function `close_thought` closes the open thinking block. |
| fn | [`paint_think_grip`](#function-paint_think_grip) | The function `paint_think_grip` paints the drag row of an open thinking strip and returns what the row did. |
| fn | [`paint_think_folded_row`](#function-paint_think_folded_row) | The function `paint_think_folded_row` paints one folded thinking row. |
| fn | [`seat_vt_console`](#function-seat_vt_console) | The function `seat_vt_console` places the SSH console three pixels under the Clear button. |
| fn | [`think_tail_pin`](#function-think_tail_pin) | The function `think_tail_pin` reports whether the thinking scroll should jump to the tail. |
| fn | [`think_buffer_len`](#function-think_buffer_len) | The function `think_buffer_len` counts the bytes in the thinking log. |
| fn | [`paint_think_row`](#function-paint_think_row) | The function `paint_think_row` paints the fold button, Close, and the thinking text. |
| fn | [`button_outer_height`](#function-button_outer_height) | The function `button_outer_height` returns the painted height of a stock button. |
| fn | [`button_lane_height`](#function-button_lane_height) | The function `button_lane_height` returns the lane that holds one stock button. |
| fn | [`button_lane_clip`](#function-button_lane_clip) | The function `button_lane_clip` returns the clip rectangle for one button lane. |
| fn | [`folded_control_height`](#function-folded_control_height) | The function `folded_control_height` returns the height of one folded thinking row. |
| fn | [`notebook_strip_shown`](#function-notebook_strip_shown) | The function `notebook_strip_shown` returns the shown height and the full height of the notebook strip. |
| fn | [`paint_think_strip`](#function-paint_think_strip) | The function `paint_think_strip` paints one thinking strip and returns what the paint did. |
| fn | [`reply_divider_band`](#function-reply_divider_band) | The function `reply_divider_band` returns the row already between the input pane and the reply pane. |
| fn | [`console_model_home`](#function-console_model_home) | The function `console_model_home` returns the pane that opened the console Model Config. |
| fn | [`console_event_filter`](#function-console_event_filter) | The function `console_event_filter` returns the keys the console keeps while it holds the keyboard. |
| fn | [`append_scope`](#function-append_scope) | The function `append_scope` appends one scope body onto the gathered text. |
| fn | [`unesc_fact`](#function-unesc_fact) | The function `unesc_fact` unescapes one fact word. |
| fn | [`seat_facts`](#function-seat_facts) | The function `seat_facts` reads `air-gap` and `num-ctx` from one init body. |
| fn | [`accept_scope`](#function-accept_scope) | The function `accept_scope` keeps an init body's facts, or keeps the body as text. |
| fn | [`turn_label`](#function-turn_label) | `Turn 1` for the first card. The number grows with the plate. |
| fn | [`mint_cell`](#function-mint_cell) | The function `mint_cell` builds a 16-byte cell id from a counter. |
| fn | [`is_merge`](#function-is_merge) | The function `is_merge` reports whether a card is the merge of a group. |
| fn | [`is_member`](#function-is_member) | The function `is_member` reports whether a card belongs to a group as a member. |
| fn | [`merge_input_models`](#function-merge_input_models) | The function `merge_input_models` joins the short model names of the input cards in one merge. |
| fn | [`member_width`](#function-member_width) | The function `member_width` returns the width of one member column. |
| fn | [`gather_slots`](#function-gather_slots) | The function `gather_slots` builds the visual order of the cards. |
| fn | [`card_armed`](#function-card_armed) | The function `card_armed` reports whether a card takes the armed edge. |
| fn | [`card_edge`](#function-card_edge) | The function `card_edge` returns the border color of one card. |
| fn | [`pinned_edge`](#function-pinned_edge) | The function `pinned_edge` returns the border color while a pin may recolor the card. |
| fn | [`think_frame`](#function-think_frame) | The function `think_frame` returns the spinner glyph for one moment. |
| fn | [`think_line`](#function-think_line) | The function `think_line` returns the spinner line for a run that is in flight. |
| fn | [`think_clock`](#function-think_clock) | The function `think_clock` formats elapsed time for the spinner. |
| fn | [`two`](#function-two) | The function `two` prints a count with a leading zero when the count is under ten. |
| fn | [`footer_clock`](#function-footer_clock) | The function `footer_clock` returns the elapsed milliseconds of the last running card. |
| fn | [`canvas_land_y`](#function-canvas_land_y) | The function `canvas_land_y` returns the viewport offset that puts the last pixel of the column in view. |
| fn | [`drag_weight`](#function-drag_weight) | The function `drag_weight` returns the canvas share after a horizontal drag. |
| fn | [`frame_body_height`](#function-frame_body_height) | The function `frame_body_height` returns the height left under the tab row. |
| fn | [`canvas_width`](#function-canvas_width) | The function `canvas_width` returns the canvas width in pixels. |
| fn | [`divider_per_mille`](#function-divider_per_mille) | The function `divider_per_mille` returns the canvas share as a per-mille of the span. |
| fn | [`weight_from_per_mille`](#function-weight_from_per_mille) | The function `weight_from_per_mille` returns a share from a stored per-mille. |
| fn | [`open_share`](#function-open_share) | The function `open_share` returns the canvas share used when the window opens. |
| fn | [`divider_span_ready`](#function-divider_span_ready) | The function `divider_span_ready` reports whether the span is wide enough to store a divider ratio. |
| fn | [`divider_due`](#function-divider_due) | The function `divider_due` reports whether a divider move is old enough to save. |
| fn | [`wrap_due`](#function-wrap_due) | The function `wrap_due` reports whether a typed column count is old enough to apply. |
| fn | [`step_columns`](#function-step_columns) | The function `step_columns` steps the column count by a signed amount. |
| fn | [`apply_typed_columns`](#function-apply_typed_columns) | The function `apply_typed_columns` reads a typed column count. |
| fn | [`wrap_columns_text`](#function-wrap_columns_text) | The function `wrap_columns_text` wraps text at a column count. |
| fn | [`push_wrapped`](#function-push_wrapped) | The function `push_wrapped` appends one line wrapped to a character width. |
| fn | [`inspector_shown`](#function-inspector_shown) | The function `inspector_shown` returns the text the inspector pane shows. |
| fn | [`arrow_share`](#function-arrow_share) | The function `arrow_share` returns the share after one divider arrow is pressed. |
| fn | [`restore_share`](#function-restore_share) | The function `restore_share` returns the share to put back when a hidden pane opens. |
| fn | [`inspector_width`](#function-inspector_width) | The function `inspector_width` returns the inspector width in pixels. |
| fn | [`inspector_closed`](#function-inspector_closed) | The function `inspector_closed` reports whether the inspector pane is closed. |
| fn | [`split_reserved`](#function-split_reserved) | The function `split_reserved` returns the width held back for the divider bar and its gaps. |
| fn | [`arrow_zones`](#function-arrow_zones) | The function `arrow_zones` returns the two arrow-button rectangles on the divider bar. |
| fn | [`arrow_icon`](#function-arrow_icon) | The function `arrow_icon` returns the three corners of the triangle inside one arrow button. |
| fn | [`paint_arrow_button`](#function-paint_arrow_button) | The function `paint_arrow_button` paints one filled arrow button. |
| fn | [`ghost_color`](#function-ghost_color) | The function `ghost_color` returns the gray used for an excluded card. |
| fn | [`prompt_ink`](#function-prompt_ink) | The function `prompt_ink` returns the prompt color for one card. |
| fn | [`reply_ink`](#function-reply_ink) | The function `reply_ink` returns the reply color for one card. |
| fn | [`pane_edge`](#function-pane_edge) | The function `pane_edge` returns the optional frame color of one canvas text pane. |
| fn | [`push_kept`](#function-push_kept) | The function `push_kept` appends the rows one card would contribute. |
| fn | [`join_word`](#function-join_word) | The function `join_word` returns the short name the cell caption already paints. |
| fn | [`join_banner_line`](#function-join_banner_line) | The function `join_banner_line` reports whether one line is a join banner. |
| fn | [`concat_join`](#function-concat_join) | The function `concat_join` builds the joined reply text. |
| fn | [`strip_join`](#function-strip_join) | The function `strip_join` removes join banners from a merged reply. |
| fn | [`merge_kept_block`](#function-merge_kept_block) | The function `merge_kept_block` builds the later-card view of one merge. |
| fn | [`merge_inspect_blocks`](#function-merge_inspect_blocks) | The function `merge_inspect_blocks` builds the inspector view of the focused merge. |
| fn | [`kept_sources`](#function-kept_sources) | The function `kept_sources` returns the kept turns before the focus. |
| fn | [`send_blocks`](#function-send_blocks) | The function `send_blocks` returns the send set of one card. |
| fn | [`inspect_blocks`](#function-inspect_blocks) | The function `inspect_blocks` returns the blocks the inspector shows for one focus. |
| fn | [`inspect_heads`](#function-inspect_heads) | The function `inspect_heads` returns one flag per inspector block. |
| fn | [`visible_heads`](#function-visible_heads) | The function `visible_heads` returns one flag per visible card that contributes a block. |
| fn | [`working_blocks`](#function-working_blocks) | The function `working_blocks` returns the blocks in send order. |
| fn | [`estimate_tokens`](#function-estimate_tokens) | The function `estimate_tokens` estimates a token count the way `HeuristicTokenCounter` does. |
| fn | [`ssh_live_count`](#function-ssh_live_count) | The function `ssh_live_count` returns the token estimate for the focused pane. |
| fn | [`keep_pending`](#function-keep_pending) | The function `keep_pending` stores the newest beat and drops a stale result. |
| fn | [`seat_ready`](#function-seat_ready) | The function `seat_ready` stores a finished paint when that paint is still the newest one. |
| fn | [`run_heat`](#function-run_heat) | The function `run_heat` counts one heatbar beat into a [`HeatPaint`]. |
| fn | [`heat_worker`](#function-heat_worker) | The function `heat_worker` is the loop on the heatbar thread. |
| fn | [`hash_mood`](#function-hash_mood) | The function `hash_mood` writes one [`MoodYaml`] into a `DefaultHasher`. |
| fn | [`mark_tag`](#function-mark_tag) | The function `mark_tag` returns a small number for one card mark. |
| fn | [`canvas_heat_sig`](#function-canvas_heat_sig) | The function `canvas_heat_sig` returns the value that decides whether the canvas heatbar posts a new beat. |
| fn | [`vt_heat_sig`](#function-vt_heat_sig) | The function `vt_heat_sig` returns the value that decides whether the terminal heatbar posts a new beat. |
| fn | [`counted_text`](#function-counted_text) | The function `counted_text` joins block text into the string the estimate counts. |
| fn | [`seat_focused_reply`](#function-seat_focused_reply) | The function `seat_focused_reply` appends the focused reply onto the last inspector block. |
| fn | [`shown_line_chars`](#function-shown_line_chars) | The function `shown_line_chars` returns the preview width in characters. |
| fn | [`counted_inspector`](#function-counted_inspector) | The function `counted_inspector` returns the text the token estimate counts. |
| fn | [`sink_inspect`](#function-sink_inspect) | The function `sink_inspect` chooses which rows stay inside a managed token budget. |
| fn | [`sink_role`](#function-sink_role) | The function `sink_role` returns the role byte used by the sink. |
| fn | [`retarget_debug`](#function-retarget_debug) | The function `retarget_debug` moves the debug invitation onto the user line that is still present. |
| fn | [`paint_sunk`](#function-paint_sunk) | The function `paint_sunk` returns the inspector pane for one block list. |
| fn | [`seat_debug_head`](#function-seat_debug_head) | The function `seat_debug_head` inserts debug invitation lines at the front of one block. |
| fn | [`paint_inspector`](#function-paint_inspector) | The function `paint_inspector` returns the inspector text, newest block first. |
| fn | [`finish_inspect`](#function-finish_inspect) | The function `finish_inspect` measures one block list and paints it. |
| fn | [`inspect_body`](#function-inspect_body) | The function `inspect_body` returns the inspector text for the focused card. |
| fn | [`inspect_body_pane`](#function-inspect_body_pane) | The function `inspect_body_pane` returns the measured inspector pane for the focused card. |
| fn | [`inspect_body_pane_gate`](#function-inspect_body_pane_gate) | The function `inspect_body_pane_gate` builds the focused inspector pane and then applies the sink. |
| fn | [`inspect_body_chars`](#function-inspect_body_chars) | The function `inspect_body_chars` returns only the inspector text for the focused card. |
| fn | [`inspect_body_estimate`](#function-inspect_body_estimate) | The function `inspect_body_estimate` returns only the token estimate for the focused card. |
| fn | [`inspect_chars_estimate`](#function-inspect_chars_estimate) | The function `inspect_chars_estimate` returns the token estimate for one preview width. |
| fn | [`inspect_visible`](#function-inspect_visible) | The function `inspect_visible` returns the inspector text for the cards the pin is showing. |
| fn | [`inspect_visible_pane`](#function-inspect_visible_pane) | The function `inspect_visible_pane` returns the measured pane for the cards the pin is showing. |
| fn | [`inspect_visible_pane_gate`](#function-inspect_visible_pane_gate) | The function `inspect_visible_pane_gate` builds the visible inspector pane and then applies the sink. |
| fn | [`inspect_visible_chars`](#function-inspect_visible_chars) | The function `inspect_visible_chars` returns only the visible inspector text. |
| fn | [`token_line`](#function-token_line) | The function `token_line` returns the south line of the inspector. |
| fn | [`count_i32`](#function-count_i32) | The function `count_i32` narrows a `usize` count to an `i32`. |
| fn | [`token_fill`](#function-token_fill) | The function `token_fill` returns the share of the context window that the estimate fills. |
| fn | [`ring_sweep`](#function-ring_sweep) | The function `ring_sweep` returns the used arc in degrees. |
| fn | [`tokens_left`](#function-tokens_left) | The function `tokens_left` returns how many tokens are still inside the window. |
| fn | [`tokens_over`](#function-tokens_over) | The function `tokens_over` returns how many tokens are past the window. |
| fn | [`percent_used`](#function-percent_used) | The function `percent_used` returns the rounded percent of the window that is used. |
| fn | [`percent_free`](#function-percent_free) | The function `percent_free` returns the percent of the window that is still free. |
| fn | [`ring_ink`](#function-ring_ink) | The function `ring_ink` returns the color of the used arc. |
| fn | [`ring_stroke`](#function-ring_stroke) | The function `ring_stroke` returns the stroke width of the token ring. |
| fn | [`ring_oval`](#function-ring_oval) | The function `ring_oval` returns the square the token ring is drawn on. |
| fn | [`ring_edge`](#function-ring_edge) | The function `ring_edge` returns one point on the token ring. |
| fn | [`ring_arc`](#function-ring_arc) | The function `ring_arc` returns the clockwise points of the used arc. |
| fn | [`ring_words`](#function-ring_words) | The function `ring_words` returns the two lines drawn in the token ring. |
| fn | [`grok_budget`](#function-grok_budget) | The function `grok_budget` returns the context window stored for one Grok id. |
| fn | [`yaml_key`](#function-yaml_key) | The function `yaml_key` returns one value from a YAML mapping. |
| fn | [`yaml_window`](#function-yaml_window) | The function `yaml_window` reads a context window from one YAML value. |
| fn | [`token_budgets_from_yaml`](#function-token_budgets_from_yaml) | The function `token_budgets_from_yaml` reads the pool window and the Grok map from one YAML document. |
| fn | [`application_yaml_path`](#function-application_yaml_path) | The function `application_yaml_path` returns the path of the bundled `application.yaml`. |
| fn | [`token_budgets_from_path`](#function-token_budgets_from_path) | The function `token_budgets_from_path` reads token budgets from one file. |
| fn | [`application_yaml_budgets`](#function-application_yaml_budgets) | The function `application_yaml_budgets` reads the token budgets from the bundled YAML file. |
| fn | [`token_seen`](#function-token_seen) | The function `token_seen` reports whether a token-window label is already in the list. |
| fn | [`add_grok_row`](#function-add_grok_row) | The function `add_grok_row` appends one Grok row when that label is new. |
| fn | [`add_token_target`](#function-add_token_target) | The function `add_token_target` appends the token-window row for one stored target. |
| fn | [`token_models`](#function-token_models) | The function `token_models` returns the rows the token window can calculate against. |
| fn | [`selected_heat_budget`](#function-selected_heat_budget) | The function `selected_heat_budget` returns the context window Token Calc opens on for the focused card. |
| fn | [`token_model_index`](#function-token_model_index) | The function `token_model_index` returns the row to select for the focused target. |
| fn | [`token_source`](#function-token_source) | The function `token_source` returns the words that name where the context window came from. |
| fn | [`token_stats`](#function-token_stats) | The function `token_stats` returns the lines beside the token ring. |
| fn | [`session_window`](#function-session_window) | The function `session_window` returns the session context window from canvas facts. |
| fn | [`canvas_token_targets`](#function-canvas_token_targets) | The function `canvas_token_targets` collects the model words stored on the cards. |
| fn | [`focused_completion_cap`](#function-focused_completion_cap) | The function `focused_completion_cap` returns the completion cap of the focused card. |
| fn | [`paint_token_ring`](#function-paint_token_ring) | The function `paint_token_ring` paints the token ring in one rectangle. |
| fn | [`frame_for`](#function-frame_for) | The function `frame_for` builds the execute frame for one cell. |
| fn | [`mood_user_card`](#function-mood_user_card) | The function `mood_user_card` reports whether a card counts as a user turn for the mood pin. |
| fn | [`member_text_before`](#function-member_text_before) | The function `member_text_before` reports whether a merge has member prompt text before the focus. |
| fn | [`kept_nonempty`](#function-kept_nonempty) | The function `kept_nonempty` reports whether one earlier card still contributes text. |
| fn | [`prior_mood_users`](#function-prior_mood_users) | The function `prior_mood_users` counts user turns before the focus for the mood pin. |
| fn | [`working_block_ids`](#function-working_block_ids) | The function `working_block_ids` returns the cell ids in the working set, as text. |
| fn | [`block_heads`](#function-block_heads) | The function `block_heads` returns one lock flag per working-set block. |
| fn | [`splice_target`](#function-splice_target) | The function `splice_target` returns the cell id where the mood line is spliced. |
| fn | [`mood_debug_seat`](#function-mood_debug_seat) | The function `mood_debug_seat` returns the debug lines and the block index of the splice target. |
| fn | [`apply_mood_splice`](#function-apply_mood_splice) | The function `apply_mood_splice` writes the mood line into one user row. |
| fn | [`commit_mood_pin`](#function-commit_mood_pin) | The function `commit_mood_pin` stores a pending pin and clears the pending flag. |
| fn | [`fit_blocks`](#function-fit_blocks) | The function `fit_blocks` builds one execute frame and reports whether rows were dropped. |
| fn | [`plan_group`](#function-plan_group) | The function `plan_group` builds one frame for each member that would be sent. |
| fn | [`provider_id`](#function-provider_id) | The function `provider_id` returns the provider word of a target. |
| fn | [`cloud_target`](#function-cloud_target) | The function `cloud_target` reports whether a target is a cloud provider. |
| fn | [`burner_denied`](#function-burner_denied) | The function `burner_denied` reports a burner card aimed at a cloud target. |
| fn | [`snap_of`](#function-snap_of) | The function `snap_of` copies the think flags of one card. |
| fn | [`apply_snap`](#function-apply_snap) | The function `apply_snap` writes a think snap back onto a card. |
| fn | [`card_tune`](#function-card_tune) | The function `card_tune` builds the execute tune for one card. |
| fn | [`burner_blocks`](#function-burner_blocks) | The function `burner_blocks` reports whether a cloud burner blocks the send. |
| fn | [`group_burner_blocks`](#function-group_burner_blocks) | The function `group_burner_blocks` reports whether any member of a group is blocked by a cloud burner. |
| fn | [`group_burner_denied`](#function-group_burner_denied) | The function `group_burner_denied` reports whether a member that would be sent is a denied cloud burner. |
| fn | [`burner_fault`](#function-burner_fault) | The function `burner_fault` builds the status for a denied cloud run. |
| fn | [`ask_run`](#function-ask_run) | The function `ask_run` chooses the run for one focused card. |
| fn | [`ask_run_mood`](#function-ask_run_mood) | The function `ask_run_mood` chooses the run and reports whether the fit dropped rows. |
| fn | [`ask_run_doc`](#function-ask_run_doc) | The function `ask_run_doc` chooses the run from the cards, the mood, and the model document. |
| fn | [`seat_mood_slide`](#function-seat_mood_slide) | The function `seat_mood_slide` opens the yellow slide when a mood fit drops rows. |
| fn | [`promote_group`](#function-promote_group) | The function `promote_group` turns an ordinary card into a group and inserts the merge. |
| fn | [`join_group`](#function-join_group) | The function `join_group` gives an ordinary card the group of another card. |
| fn | [`add_cell`](#function-add_cell) | The function `add_cell` inserts an empty member immediately before a merge. |
| fn | [`next_number`](#function-next_number) | The function `next_number` returns the next id from a counter. |
| fn | [`blank_like`](#function-blank_like) | The function `blank_like` builds an empty card from another card. |
| fn | [`card_stamp`](#function-card_stamp) | The function `card_stamp` returns the current time in milliseconds since the Unix epoch. |
| fn | [`view_window_height`](#function-view_window_height) | The function `view_window_height` returns the height of a pane that shows a fixed number of rows. |
| fn | [`pane_font`](#function-pane_font) | The function `pane_font` returns the font of one pane. |
| fn | [`pane_line`](#function-pane_line) | The function `pane_line` returns the height of one input line. |
| fn | [`pane_width`](#function-pane_width) | The function `pane_width` returns a finite width for one pane. |
| fn | [`paint_view_plate`](#function-paint_view_plate) | The function `paint_view_plate` paints the plate around an open pane. |
| fn | [`reply_bound`](#function-reply_bound) | The function `reply_bound` returns the reply height limit. |
| fn | [`group_pane_height`](#function-group_pane_height) | The function `group_pane_height` returns the reply pane height for one card. |
| fn | [`group_open_floor`](#function-group_open_floor) | The function `group_open_floor` returns the space under an open reply. |
| fn | [`merge_column_width`](#function-merge_column_width) | The function `merge_column_width` returns the column width for a merge. |
| fn | [`row_count`](#function-row_count) | The function `row_count` returns a pane row count of at least 1. |
| fn | [`prompt_window_height`](#function-prompt_window_height) | The function `prompt_window_height` returns the locked height of one open pane. |
| fn | [`paint_locked_pane`](#function-paint_locked_pane) | The function `paint_locked_pane` puts a fixed pane on the parent and paints inside it. |
| fn | [`pane_wheel`](#function-pane_wheel) | The function `pane_wheel` returns the scroll source for one locked pane. |
| fn | [`deck_hole`](#function-deck_hole) | The function `deck_hole` returns the canvas viewport. |
| fn | [`column_bands`](#function-column_bands) | The function `column_bands` turns a column notice into deck and think rectangles. |
| fn | [`paint_north_seal`](#function-paint_north_seal) | The function `paint_north_seal` fills the band from the column top to the deck. |
| fn | [`seat_north_layer`](#function-seat_north_layer) | The function `seat_north_layer` stores the header hit area. |
| fn | [`pane_scroll`](#function-pane_scroll) | The function `pane_scroll` scrolls inside a locked pane. |
| fn | [`apple_thumb_len`](#function-apple_thumb_len) | The function `apple_thumb_len` returns the handle length. |
| fn | [`apple_thumb_pos`](#function-apple_thumb_pos) | The function `apple_thumb_pos` returns the thumb origin along the track. |
| fn | [`apple_offset_from_thumb`](#function-apple_offset_from_thumb) | The function `apple_offset_from_thumb` returns the scroll offset for a thumb origin. |
| fn | [`apple_scroll_delta`](#function-apple_scroll_delta) | The function `apple_scroll_delta` returns the content delta that lands on `desired`. |
| fn | [`apple_lerp`](#function-apple_lerp) | The function `apple_lerp` blends two amounts. |
| fn | [`apple_max_offset`](#function-apple_max_offset) | The function `apple_max_offset` returns how far the content can scroll. |
| fn | [`apple_track_rect`](#function-apple_track_rect) | The function `apple_track_rect` returns the hit strip on the outer edge. |
| fn | [`apple_thumb_rect`](#function-apple_thumb_rect) | The function `apple_thumb_rect` returns the handle rectangle inside the hit strip. |
| fn | [`apple_press_grab`](#function-apple_press_grab) | The function `apple_press_grab` returns the grab offset for a press. |
| fn | [`apple_drag_delta`](#function-apple_drag_delta) | The function `apple_drag_delta` returns the content delta for a held handle. |
| fn | [`show_apple`](#function-show_apple) | The function `show_apple` hides the native bar and paints a forty-percent handle. |
| fn | [`paint_apple_bars`](#function-paint_apple_bars) | The function `paint_apple_bars` paints the floating apple bars. |
| fn | [`format_edge_stamp`](#function-format_edge_stamp) | The function `format_edge_stamp` formats a wall time for the input edge. |
| fn | [`local_edge_stamp`](#function-local_edge_stamp) | The function `local_edge_stamp` formats the current local time. |
| fn | [`input_edge_caption`](#function-input_edge_caption) | The function `input_edge_caption` joins the time text and the short model. |
| fn | [`hover_caption`](#function-hover_caption) | The function `hover_caption` returns the hover words for a cell that is not selected. |
| fn | [`quiet_cell_tip`](#function-quiet_cell_tip) | The function `quiet_cell_tip` reports a cell that is not the focus. |
| fn | [`caption_rule`](#function-caption_rule) | The function `caption_rule` returns the width of one flank. |
| fn | [`caption_gap`](#function-caption_gap) | The function `caption_gap` returns the air between a flank and the words. |
| fn | [`caption_span`](#function-caption_span) | The function `caption_span` returns the full caption width. |
| fn | [`flank_band`](#function-flank_band) | The function `flank_band` returns the visible part of one flank. |
| fn | [`frame_caption`](#function-frame_caption) | The function `frame_caption` returns the caption painted on a cell frame. |
| fn | [`input_edge_x`](#function-input_edge_x) | The function `input_edge_x` returns the right inset of the caption. |
| fn | [`embedded_caption_top`](#function-embedded_caption_top) | The function `embedded_caption_top` returns the top of the embedded caption. |
| fn | [`caption_clearance`](#function-caption_clearance) | The function `caption_clearance` returns the clear air around the embedded line. |
| fn | [`embedded_clip_top`](#function-embedded_clip_top) | The function `embedded_clip_top` returns the clip above an embedded caption. |
| fn | [`embedded_visible_h`](#function-embedded_visible_h) | The function `embedded_visible_h` returns the caption height that remains after the clip. |
| fn | [`cell_caption_line`](#function-cell_caption_line) | The function `cell_caption_line` returns the height of one monospace caption line. |
| fn | [`paint_input_edge`](#function-paint_input_edge) | The function `paint_input_edge` paints the time text and the model on the top stroke. |
| fn | [`paint_prompt_window`](#function-paint_prompt_window) | The function `paint_prompt_window` paints the prompt in a fixed window. |
| fn | [`seat_prompt_caret`](#function-seat_prompt_caret) | The function `seat_prompt_caret` puts the caret on the edit before the prompt widget paints. |
| fn | [`paint_bounded_reply`](#function-paint_bounded_reply) | The function `paint_bounded_reply` paints a reply in a fixed window. |
| fn | [`paint_open_reply`](#function-paint_open_reply) | The function `paint_open_reply` paints the whole reply in the prompt plate. |
| fn | [`bar_mark`](#function-bar_mark) | The function `bar_mark` returns the glyph for the group-tool row. |
| fn | [`bar_tip`](#function-bar_tip) | The function `bar_tip` returns the hover text for the group-tool mark. |
| fn | [`bar_tools`](#function-bar_tools) | The function `bar_tools` returns the group-tool labels for one card. |
| fn | [`tool_mark`](#function-tool_mark) | The function `tool_mark` returns the glyph for the tool bar. |
| fn | [`tool_tip`](#function-tool_tip) | The function `tool_tip` returns the hover text for the tool-bar mark. |
| fn | [`pin_tokens`](#function-pin_tokens) | The function `pin_tokens` splits a pin field into words. |
| fn | [`push_pin_token`](#function-push_pin_token) | The function `push_pin_token` stores one finished pin word. |
| fn | [`pin_match`](#function-pin_match) | The function `pin_match` reports whether a card matches every pin word. |
| fn | [`pin_slots`](#function-pin_slots) | The function `pin_slots` applies the pin to deck geometry. |
| fn | [`find_hit`](#function-find_hit) | The function `find_hit` reports a case-insensitive hit on the user text or the assistant text. |
| fn | [`find_slots`](#function-find_slots) | The function `find_slots` applies the find filter after the conceal pass. |
| fn | [`shown_slots`](#function-shown_slots) | The function `shown_slots` drops folded cards while hide is on. |
| fn | [`pin_box_off`](#function-pin_box_off) | The function `pin_box_off` leaves the pin field alone when the box changes. |
| fn | [`pin_enter_commits`](#function-pin_enter_commits) | The function `pin_enter_commits` reports whether Enter in the field commits. |
| fn | [`pin_enter_due`](#function-pin_enter_due) | The function `pin_enter_due` reports whether this frame should commit Enter. |
| fn | [`apply_pin_clear`](#function-apply_pin_clear) | The function `apply_pin_clear` empties the pin field and does not commit. |
| fn | [`pin_write_due`](#function-pin_write_due) | The function `pin_write_due` reports whether OK or Enter may write. |
| fn | [`pin_commit_after_mark`](#function-pin_commit_after_mark) | The function `pin_commit_after_mark` reports whether a pin commit still stands after a mark press. |
| fn | [`eligible_locks`](#function-eligible_locks) | The function `eligible_locks` returns the cards that may show a lock. |
| fn | [`repair_locks`](#function-repair_locks) | The function `repair_locks` packs the locks into a prefix of the eligible cards. |
| fn | [`press_lock`](#function-press_lock) | The function `press_lock` applies one lock press and keeps a packed prefix. |
| fn | [`shows_lock`](#function-shows_lock) | The function `shows_lock` reports whether one card may show a lock. |
| fn | [`head_lock_image`](#function-head_lock_image) | The function `head_lock_image` builds the 16 by 16 padlock. |
| fn | [`paint_head_lock`](#function-paint_head_lock) | The function `paint_head_lock` paints the padlock on one card and reports a primary click. |
| fn | [`paint_head_lock_divider`](#function-paint_head_lock_divider) | The function `paint_head_lock_divider` paints the line between the padlock and the marks. |
| fn | [`seat_mark`](#function-seat_mark) | The function `seat_mark` writes one mark onto one cell. |
| fn | [`commit_pin`](#function-commit_pin) | The function `commit_pin` writes marks from a pin commit. |
| fn | [`fold_pin_bar`](#function-fold_pin_bar) | The function `fold_pin_bar` hides the pin bar and keeps the box and the words. |
| fn | [`show_pin_bar`](#function-show_pin_bar) | The function `show_pin_bar` shows the pin bar and keeps the box and the words. |
| fn | [`paint_pin_face`](#function-paint_pin_face) | The function `paint_pin_face` paints one pin letter as a click target. |
| fn | [`pin_mark_size`](#function-pin_mark_size) | The function `pin_mark_size` returns the size of P. |
| fn | [`paint_fact_line`](#function-paint_fact_line) | The function `paint_fact_line` paints one line on the inspector share of the tab row. |
| fn | [`pin_show_fill`](#function-pin_show_fill) | The function `pin_show_fill` returns the fill for P. |
| fn | [`paint_pin_mark`](#function-paint_pin_mark) | The function `paint_pin_mark` draws P on the divider. |
| fn | [`paint_pin_row`](#function-paint_pin_row) | The function `paint_pin_row` paints the pin row and returns what it asked. |
| fn | [`canvas_footer_rows`](#function-canvas_footer_rows) | The function `canvas_footer_rows` returns BB1, and BB2 when it is open. |
| fn | [`paint_tool_gap`](#function-paint_tool_gap) | The function `paint_tool_gap` paints the vertical mark between groups. |
| fn | [`paint_mood_switch`](#function-paint_mood_switch) | The function `paint_mood_switch` paints the mood switch and reports a click. |
| fn | [`spin_box`](#function-spin_box) | The function `spin_box` returns the width of the spinner glyph. |
| fn | [`paint_footer_spin`](#function-paint_footer_spin) | The function `paint_footer_spin` paints the footer spinner, the word, and the clock. |
| fn | [`user_menu`](#function-user_menu) | The function `user_menu` builds the pull-down from connected AMWP records. |
| fn | [`user_move`](#function-user_move) | The function `user_move` chooses what a pick does to the open vault. |
| fn | [`take_user_move`](#function-take_user_move) | The function `take_user_move` writes a user only when the pick binds a different vault. |
| fn | [`user_tip`](#function-user_tip) | The function `user_tip` returns the hover text for the user pull-down. |
| fn | [`user_combo_width`](#function-user_combo_width) | The function `user_combo_width` returns the room left for the user pull-down. |
| fn | [`east_reserve`](#function-east_reserve) | The function `east_reserve` returns the east column held before the master buttons paint. |
| fn | [`paint_east_capsule`](#function-paint_east_capsule) | The function `paint_east_capsule` paints the user combo on the outer east. |
| fn | [`bb_fold_mark`](#function-bb_fold_mark) | The function `bb_fold_mark` returns the fold glyph for the button bar. |
| fn | [`bb_fold_line_ink`](#function-bb_fold_line_ink) | The function `bb_fold_line_ink` returns the ink of the folded button-bar line. |
| fn | [`footer_ceiling`](#function-footer_ceiling) | The function `footer_ceiling` returns the room kept for the button bar. |
| fn | [`paint_bb_folded_line`](#function-paint_bb_folded_line) | The function `paint_bb_folded_line` paints one folded button-bar row. |
| fn | [`paint_canvas_footer`](#function-paint_canvas_footer) | The function `paint_canvas_footer` paints the footer with the bars open. |
| fn | [`paint_bb_bars`](#function-paint_bb_bars) | Paint the footer from a bottom-up layout. |
| fn | [`search_turn`](#function-search_turn) | The function `search_turn` searches a card list for text in the prompt or the reply. |
| fn | [`search_word`](#function-search_word) | The function `search_word` turns a search result into status text. |
| fn | [`keep_word`](#function-keep_word) | The function `keep_word` maps an export answer onto [`KeepBurners`]. |
| fn | [`export_esc`](#function-export_esc) | Swing `esc`. A carriage return is dropped. A newline becomes `\n`. A backslash doubles. |
| fn | [`mark_token`](#function-mark_token) | The function `mark_token` prints a [`Mark`] as the stored state word. |
| fn | [`export_status_token`](#function-export_status_token) | A stored fault is FAILED. A quiet card with a reply is COMPLETED. |
| fn | [`group_token`](#function-group_token) | An ordinary card is empty. A grouped card is the number this client stores. |
| fn | [`vault_status_of`](#function-vault_status_of) | A running card stores RUNNING. A fault stores FAILED. |
| fn | [`mark_of`](#function-mark_of) | The function `mark_of` reads a stored state word back into a [`Mark`]. |
| fn | [`parse_cell_text`](#function-parse_cell_text) | `cell_text` back into 16 bytes. Any other shape stays off the deck. |
| fn | [`cell_from_card`](#function-cell_from_card) | The function `cell_from_card` builds one [`crate::vault::CellRow`] from a canvas card. |
| fn | [`card_from_cell`](#function-card_from_cell) | The function `card_from_cell` rebuilds a [`TurnCard`] from one stored row. |
| fn | [`cards_from_canvas`](#function-cards_from_canvas) | The function `cards_from_canvas` turns a stored canvas into cards and rejected ids. |
| fn | [`seat_from_hits`](#function-seat_from_hits) | First deck card whose id is in the file hits. The count is the file count. |
| fn | [`export_toon`](#function-export_toon) | Version 1 TOON. The parent field is empty. The branch is main. |
| fn | [`export_answer`](#function-export_answer) | Cancel writes nothing. Yes and No build the file text. The cards stay. |
| fn | [`write_export`](#function-write_export) | The function `write_export` writes an export body to a file. |
| fn | [`land_export`](#function-land_export) | The status word after the bytes land. A miss names the reason. |
| fn | [`chooser_line`](#function-chooser_line) | The function `chooser_line` runs a PowerShell script and returns its first output line. |
| fn | [`ask_keep_burners`](#function-ask_keep_burners) | The function `ask_keep_burners` asks whether burner bodies should be kept in an export. |
| fn | [`pick_export_path`](#function-pick_export_path) | The function `pick_export_path` asks for a path for a canvas export file. |
| fn | [`session_export_file`](#function-session_export_file) | `None` when the body is not a session export. The table text keeps its header line. |
| fn | [`session_export_file_of`](#function-session_export_file_of) | The function `session_export_file_of` reads a session-export reply from one scope piece. |
| fn | [`session_replace_file`](#function-session_replace_file) | `Ok` is the success line. `Err` is the text after `replace failed:`. |
| fn | [`session_replace_file_of`](#function-session_replace_file_of) | The function `session_replace_file_of` reads a session-replace reply from one scope piece. |
| fn | [`session_tokens_of`](#function-session_tokens_of) | `used`, `chars`, and `blocks` from `session_tokens_result:`. |
| fn | [`parse_message_table`](#function-parse_message_table) | Role and text. A bad header, a bad row, or a count that misses the rows is an error. |
| fn | [`pair_session`](#function-pair_session) | User then assistant is one cell. A lone user has an empty reply. A lone assistant has an empty prompt. |
| fn | [`split_message_row`](#function-split_message_row) | The function `split_message_row` splits one message-table row into a role and a text field. |
| fn | [`unquote_field`](#function-unquote_field) | The function `unquote_field` removes quotes and escape sequences from one table field. |
| fn | [`quote_field`](#function-quote_field) | The function `quote_field` quotes a table field when the text needs quotes. |
| fn | [`table_from_cards`](#function-table_from_cards) | One user row and one assistant row per cell that has text. An empty cell stays out. |
| fn | [`push_transfer_pair`](#function-push_transfer_pair) | The function `push_transfer_pair` appends one user row and one assistant row to a transfer table. |
| fn | [`transfer_inputs`](#function-transfer_inputs) | Input prompts of one merge, in deck order, joined by a newline. |
| fn | [`transfer_reply`](#function-transfer_reply) | The merge card's stored reply. JOIN lines stay. |
| fn | [`seat_transferred`](#function-seat_transferred) | The copied cells replace the deck. An empty list leaves one empty card. |
| fn | [`ask_ok`](#function-ask_ok) | OK continues. Cancel is false. A dialog that does not open is an error. |
| fn | [`pick_session_export_path`](#function-pick_session_export_path) | The function `pick_session_export_path` asks for a path for a session export file. |
| fn | [`land_session_export`](#function-land_session_export) | The status word after the Roux table lands. A miss names the reason. |
| fn | [`begin_export`](#function-begin_export) | Cancel on either chooser returns None and leaves the status. The cards stay. |
| fn | [`filed_new`](#function-filed_new) | The function `filed_new` builds a blank [`FiledCell`] for one id from an import file. |
| fn | [`import_unesc`](#function-import_unesc) | Swing `unesc`. `\\` becomes `\`. `\n` becomes a newline. Any other backslash stays. |
| fn | [`import_i32`](#function-import_i32) | The function `import_i32` parses one import field as a signed integer. |
| fn | [`import_column`](#function-import_column) | A negative column, or a number that does not fit, lands as 0. |
| fn | [`import_truth`](#function-import_truth) | Swing `truth`. `true`, `yes`, and `1`. |
| fn | [`filed_state`](#function-filed_state) | Blank is Included. GHOST is Excluded. An unknown token is Included and unreadable. |
| fn | [`filed_kept`](#function-filed_kept) | Readable, not Excluded, and not an ephemeral cell with both bodies blank. |
| fn | [`parse_filed`](#function-parse_filed) | Version 1 cells. `None` is a missing version or a version other than 1. |
| fn | [`rewrite_k`](#function-rewrite_k) | Swing `rewriteK`. A mapped k is rewritten. A bad number stays. A missing closer keeps the tail. |
| fn | [`kept_k_map`](#function-kept_k_map) | Old kept place to new kept place. Both lists follow the file order. |
| fn | [`fresh_group`](#function-fresh_group) | A non-empty group string mints one fresh number. An empty group stays 0. |
| fn | [`import_copies`](#function-import_copies) | Version 1 cells as copies. `None` is a bad version or zero cells. |
| fn | [`seat_canvas_import`](#function-seat_canvas_import) | Over 1 MiB is refused. A bad version is refused. The copies are appended. The earlier cards stay. |
| fn | [`pick_canvas_file`](#function-pick_canvas_file) | The function `pick_canvas_file` asks for a path to a canvas import file. |
| fn | [`land_canvas_import`](#function-land_canvas_import) | The status word after the copies land. A miss names the reason. |
| fn | [`begin_canvas_import`](#function-begin_canvas_import) | Cancel writes nothing. A miss names the reason. The armed card stays. |
| fn | [`duplicate_group`](#function-duplicate_group) | Copy the armed group to the next place. Members stay in deck order, then the merge. |
| fn | [`seat_duplicate`](#function-seat_duplicate) | The menu and Ctrl+Shift+G share this seat. The word is the status line. |
| fn | [`ungroup_card`](#function-ungroup_card) | Ungroup one card. A merge with no members left becomes an ordinary card. |
| fn | [`settle_merge`](#function-settle_merge) | The function `settle_merge` writes the joined member replies onto the merge card. |
| fn | [`fail_merge`](#function-fail_merge) | The function `fail_merge` marks the merge card of one group as failed. |
| fn | [`halt_run`](#function-halt_run) | Cancel one running card. The reply stays. |
| fn | [`group_after`](#function-group_after) | After a member ends or faults. The merge waits until every member is quiet. |
| fn | [`expanded_yaml_path`](#function-expanded_yaml_path) | The function `expanded_yaml_path` builds the path of the expanded-view file. |
| fn | [`nil_cell_id`](#function-nil_cell_id) | The function `nil_cell_id` reports whether a cell id text should be skipped. |
| fn | [`push_expanded_id`](#function-push_expanded_id) | The function `push_expanded_id` appends one cell id to the expanded-view list. |
| fn | [`seat_inline_expanded`](#function-seat_inline_expanded) | `{ id: true, id2: true }`. A pair that is not a key blocks the file. |
| fn | [`parse_expanded_yaml`](#function-parse_expanded_yaml) | A missing file is fresh. An empty file, or any version other than 1, is blocked. |
| fn | [`render_expanded_yaml`](#function-render_expanded_yaml) | The function `render_expanded_yaml` writes an [`ExpandedYaml`] as version-1 text. |
| fn | [`read_expanded_yaml`](#function-read_expanded_yaml) | The function `read_expanded_yaml` loads an [`ExpandedYaml`] from a file. |
| fn | [`write_expanded_yaml`](#function-write_expanded_yaml) | A blocked file is left alone. |
| fn | [`load_expanded_file`](#function-load_expanded_file) | The function `load_expanded_file` loads the expanded-view file from the home directory. |
| fn | [`hide_yaml_path`](#function-hide_yaml_path) | The function `hide_yaml_path` builds the path of the hide-collapsed file. |
| fn | [`hide_on`](#function-hide_on) | The function `hide_on` reads a yes-or-no yaml value as a flag. |
| fn | [`parse_hide_yaml`](#function-parse_hide_yaml) | A missing file is Show. An empty file, or any version other than 1, is blocked. |
| fn | [`render_hide_yaml`](#function-render_hide_yaml) | The function `render_hide_yaml` writes a [`HideYaml`] as version-1 text. |
| fn | [`read_hide_yaml`](#function-read_hide_yaml) | The function `read_hide_yaml` loads a [`HideYaml`] from a file. |
| fn | [`write_hide_yaml`](#function-write_hide_yaml) | A blocked file is left alone. |
| fn | [`load_hide_file`](#function-load_hide_file) | The function `load_hide_file` loads the hide-collapsed file from the home directory. |
| fn | [`settings_yaml_path`](#function-settings_yaml_path) | The function `settings_yaml_path` builds the path of the window-settings file. |
| fn | [`parse_settings_yaml`](#function-parse_settings_yaml) | A missing file is the quiet plate. An empty file, or any version other than 1, is blocked. |
| fn | [`render_settings_yaml`](#function-render_settings_yaml) | The function `render_settings_yaml` writes a [`SettingsYaml`] as version-1 text. |
| fn | [`read_settings_yaml`](#function-read_settings_yaml) | The function `read_settings_yaml` loads a [`SettingsYaml`] from a file. |
| fn | [`write_settings_yaml`](#function-write_settings_yaml) | A blocked file is left alone. |
| fn | [`load_settings_file`](#function-load_settings_file) | The function `load_settings_file` loads the window-settings file from the home directory. |
| fn | [`expanded_on`](#function-expanded_on) | The function `expanded_on` reports whether one cell id is checked in the expanded-view file. |
| fn | [`note_expanded`](#function-note_expanded) | Check adds the id. Uncheck drops it. A match writes nothing. A nil id stays out. |
| fn | [`forget_expanded`](#function-forget_expanded) | Deleted cards drop their ids. An absent id stays absent. |
| fn | [`seat_expanded`](#function-seat_expanded) | A saved id is checked. Any other id is unchecked. A blocked file leaves the cards. |
| fn | [`append_turn`](#function-append_turn) | Append the next empty card. The returned index is the new focus. |
| fn | [`begin_run`](#function-begin_run) | The function `begin_run` marks one card as running and clears its last result. |
| fn | [`accept_reply`](#function-accept_reply) | The function `accept_reply` appends one reply fragment to a running card. |
| fn | [`accept_end`](#function-accept_end) | The function `accept_end` stops a card when its run ends. |
| fn | [`accept_fault`](#function-accept_fault) | The function `accept_fault` records a fault on one card and stops that card. |
| fn | [`clear_turn`](#function-clear_turn) | The function `clear_turn` empties the text of one card and stops it. |
| fn | [`clear_deck`](#function-clear_deck) | Disconnect leaves one empty card. |
| fn | [`clear_plate`](#function-clear_plate) | Clear leaves one empty Included card and a new cell id. |
| fn | [`removal_indexes`](#function-removal_indexes) | The armed card, or every card of its group. Group 0 is one card. |
| fn | [`delete_armed`](#function-delete_armed) | Delete stays off when the removal would leave the canvas empty. |
| fn | [`plan_delete`](#function-plan_delete) | The card that remains where the first removed card sat. A tail removal lands on the last card. |
| fn | [`shift_index`](#function-shift_index) | The function `shift_index` maps an index across a deletion. |
| fn | [`pipe_hits_delete`](#function-pipe_hits_delete) | The running card still sits on the Swing queue. A named card clears the chain. |
| fn | [`group_hits_delete`](#function-group_hits_delete) | The function `group_hits_delete` reports whether a deletion touches the live group run. |
| fn | [`apply_delete`](#function-apply_delete) | The function `apply_delete` removes the cards named by a [`DeleteCut`]. |
| fn | [`fold_mark`](#function-fold_mark) | `+` when the card is folded. `-` when it is open. |
| fn | [`fold_brief`](#function-fold_brief) | The first line of the reply. An empty reply uses the first line of the prompt. |
| fn | [`brief_width`](#function-brief_width) | Paint width of the folded line. The text stays stored. |
| fn | [`fold_all`](#function-fold_all) | The function `fold_all` sets the collapsed flag on every card. |
| fn | [`fold_card`](#function-fold_card) | One card. A missing index leaves the deck alone. |
| fn | [`collapse_keeps_the_card`](#function-collapse_keeps_the_card) | LOCK F-141. Collapse All, Expand All, and the per-cell fold keep the card. |
| fn | [`release_fold`](#function-release_fold) | A fold releases the id. It never adds one. |
| fn | [`conceal_collapsed`](#function-conceal_collapsed) | Hide Collapsed snapshots the folded ids. A fold must not call this. |
| fn | [`run_chord`](#function-run_chord) | Ctrl+Enter runs. Plain Enter stays in the prompt. A busy card does not send. |
| fn | [`duplicate_chord`](#function-duplicate_chord) | Ctrl+Shift+G copies the armed group. Swing binds `VK_G` with Ctrl and Shift. |
| fn | [`notice_ink`](#function-notice_ink) | Yellow when the status is the group miss. Other lines keep the plate ink. |
| fn | [`slide_skin`](#function-slide_skin) | Red for a hard miss. Yellow for any other miss. |
| fn | [`unesc_miss`](#function-unesc_miss) | Swing `unesc` for a reason or a message. `\\n` is a newline. `\\\\` is a slash. |
| fn | [`parse_miss`](#function-parse_miss) | Swing `decodeError`. A body that starts with `reason:` is structured. |
| fn | [`hard_miss`](#function-hard_miss) | Swing `hardMiss`. Engine-down, too-large, a pool break, or a Grok transport line. |
| fn | [`banner_token`](#function-banner_token) | The function `banner_token` reports whether a fault word stays on the status line. |
| fn | [`graceful_notice`](#function-graceful_notice) | A named banner on an ordinary card stays on the status line. |
| fn | [`slide_text`](#function-slide_text) | Empty garnered text becomes `execute failed`. |
| fn | [`fault_card`](#function-fault_card) | The function `fault_card` finds the card a fault should land on. |
| fn | [`seat_fault`](#function-seat_fault) | A wire miss. `cell-busy` stays a status word. A named banner on an ordinary |
| fn | [`close_slide`](#function-close_slide) | Close drops the notice. The card text stays. |
| fn | [`pipe_chord`](#function-pipe_chord) | Ctrl+Shift+F5 runs the chain. Swing binds `VK_F5` with Ctrl and Shift. |
| fn | [`sources_before`](#function-sources_before) | Kept cards before `target`. An excluded card is skipped. The same group is skipped. |
| fn | [`resolve_pipes`](#function-resolve_pipes) | Replace `{{cell[k].output}}` with that earlier reply. A bad index, an empty reply, |
| fn | [`pipe_indexes`](#function-pipe_indexes) | Named earlier cards in token order, then the armed card. A bad token is not queued. |
| fn | [`pipe_frame`](#function-pipe_frame) | Outbound working set for one chain card. Pipe text is resolved. The stored prompt stays. |
| fn | [`take_next`](#function-take_next) | The next card that can be sent. A merge is not sent. A pipe block stops the chain. |
| fn | [`plan_chain`](#function-plan_chain) | Chain for the armed card. Earlier named cards, then that card. One run is in flight. |
| fn | [`plan_chain_mood`](#function-plan_chain_mood) | The function `plan_chain_mood` plans a pipe chain with one mood file. |
| fn | [`plan_chain_doc`](#function-plan_chain_doc) | The function `plan_chain_doc` plans the next send for the focused card. |
| fn | [`pipe_after`](#function-pipe_after) | After the running chain card ends. The next frame waits for that reply. |
| fn | [`pipe_after_mood`](#function-pipe_after_mood) | The function `pipe_after_mood` continues a pipe chain after the running card, using one mood file. |
| fn | [`pipe_after_doc`](#function-pipe_after_doc) | The function `pipe_after_doc` chooses the next chain frame after the running card ends or faults. |
| fn | [`fill_capped`](#function-fill_capped) | Pull until `cap`, then one extra when the source still has a piece. |
| fn | [`take_front`](#function-take_front) | Take at most `cap` from the front. The caller paints canvas pieces before terminal chunks. |
| fn | [`commit_wait`](#function-commit_wait) | A live session waits 16 ms when the queues are caught up, and 1 ms when a budget remains. |
| fn | [`editor_event`](#function-editor_event) | A prompt moves the pane when it is clicked or when it gains the keyboard. |
| fn | [`pane_focus`](#function-pane_focus) | The letter click wins. The next paint keeps that card until a prompt event. |
| fn | [`border_arm`](#function-border_arm) | A primary click inside the cell border arms that card. |
| fn | [`scroll_press`](#function-scroll_press) | Prompt and reply take the wheel. A card press and the padding return it. |
| fn | [`scroll_seat_after`](#function-scroll_seat_after) | The seat after that press. Card and padding return the wheel to the canvas. |
| fn | [`bar_press`](#function-bar_press) | A press in the scroll strip leaves the wheel on the canvas. |
| fn | [`seat_on_deck`](#function-seat_on_deck) | An index past the deck returns the wheel to the canvas. |
| fn | [`pane_owns_wheel`](#function-pane_owns_wheel) | The wheel stays on this pane when the seat matches it. |
| fn | [`pane_stops_wheel`](#function-pane_stops_wheel) | Stop the canvas wheel only while this pane owns it and the pointer is inside. |
| fn | [`caption_band_inside`](#function-caption_band_inside) | The lower half of the caption, inside the cell. The line tip stays there. |
| fn | [`pointer_inside`](#function-pointer_inside) | The pointer is inside the part of `rect` this `ui` paints. |
| fn | [`copy_reply`](#function-copy_reply) | Whole reply for the clipboard. A blank reply leaves the clipboard alone. |
| fn | [`copy_notice`](#function-copy_notice) | The status line when the clipboard set fails. A quiet set leaves the line. |
| fn | [`view_body`](#function-view_body) | The reply window opens when the text has a character past a space. |
| fn | [`view_title`](#function-view_title) | Swing's window name. The dash is the title dash. |
| fn | [`seat_view_glass`](#function-seat_view_glass) | Full-screen glass. It sits above the canvas and below the reply window. |
| fn | [`disarm_view_glass`](#function-disarm_view_glass) | The glass leaves with the window. The next click reaches the canvas. |
| fn | [`view_bounds_path`](#function-view_bounds_path) | `~/.aetheris/{username}/view-bounds.txt`. An empty name writes nothing. |
| fn | [`vault_runtime`](#function-vault_runtime) | The function `vault_runtime` builds a Tokio runtime for vault work. |
| fn | [`home_dir`](#function-home_dir) | The function `home_dir` reads the home directory from the process environment. |
| fn | [`format_view_bounds`](#function-format_view_bounds) | The function `format_view_bounds` writes a [`ViewRect`] as four text lines. |
| fn | [`parse_view_bounds`](#function-parse_view_bounds) | The function `parse_view_bounds` reads a saved reply-window rectangle. |
| fn | [`rects_meet`](#function-rects_meet) | The function `rects_meet` reports whether two rectangles overlap. |
| fn | [`seated_bounds`](#function-seated_bounds) | A short side or a rectangle off every screen loads as the default. |
| fn | [`bound_view`](#function-bound_view) | The reply window stays inside the desktop view. |
| fn | [`view_body_band`](#function-view_body_band) | Height left for the reply after the find bar and the button row. |
| fn | [`view_slots`](#function-view_slots) | The function `view_slots` splits a reply window into find, body, and control heights. |
| fn | [`store_view_bounds`](#function-store_view_bounds) | The function `store_view_bounds` writes a reply-window rectangle to a file. |
| fn | [`view_rect_from`](#function-view_rect_from) | The function `view_rect_from` converts a painted rectangle into a [`ViewRect`]. |
| fn | [`fold_upper`](#function-fold_upper) | The function `fold_upper` returns the uppercase form of one character. |
| fn | [`fold_lower`](#function-fold_lower) | The function `fold_lower` returns the lowercase form of one character. |
| fn | [`same_letter`](#function-same_letter) | Swing `regionMatches(true)` on one character. |
| fn | [`view_ranges`](#function-view_ranges) | Body, match, or the current match. Spans stay inside the text. |
| fn | [`view_job`](#function-view_job) | The function `view_job` builds the painted text of one reply window. |
| fn | [`card_menu`](#function-card_menu) | Menu lines. The reply omits Import. Duplicate group shows on a grouped card. |
| fn | [`import_refused`](#function-import_refused) | Merge first, then output only. Either word is before the chooser opens. |
| fn | [`has_nul`](#function-has_nul) | A NUL in the first [`NUL_SCAN`] bytes. A later NUL stays legal. |
| fn | [`import_text`](#function-import_text) | Quiet import text. A leading NUL is refused. |
| fn | [`import_landed`](#function-import_landed) | `imported into turn N`. The number is the plate number, starting at 1. |
| fn | [`import_is_large`](#function-import_is_large) | A prompt file past [`IMPORT_CAP`]. That size itself is not large. |
| fn | [`console_is_large`](#function-console_is_large) | A session file past [`TERMINAL_PASTE_CAP`] bytes. That size itself is not large. |
| fn | [`prompt_status`](#function-prompt_status) | Landed status. A large file keeps the polite line ahead of the turn words. |
| fn | [`read_import`](#function-read_import) | Metadata first, then the bytes. Either length over the cap is refused. |
| fn | [`chooser_bounds_usable`](#function-chooser_bounds_usable) | A saved rectangle meets a screen and the usable minimum. |
| fn | [`exit_pos_usable`](#function-exit_pos_usable) | A saved Confirm Exit origin meets the view. Absent keys stay on the default place. |
| fn | [`selection_opens_as_url`](#function-selection_opens_as_url) | The selection is a URL Java can open. A scheme is required. A space is not. |
| fn | [`console_scroll_target`](#function-console_scroll_target) | Next scroll offset. Positive `lines` moves toward the tail. The floor is 0. |
| fn | [`console_menu_lines`](#function-console_menu_lines) | Terminal lines for one menu motion. A page is the visible row count. |
| fn | [`select_all_span`](#function-select_all_span) | Both corners of the buffer. Index 0 is the oldest line. |
| fn | [`console_menu_gates`](#function-console_menu_gates) | Open as URL stays dark unless the highlight is a URL. Copy stays dark without a highlight. |
| fn | [`console_menu_tip`](#function-console_menu_tip) | The chord Java paints on the menu item. The label itself stays the menu string. |
| fn | [`console_menu_pick`](#function-console_menu_pick) | The nine JediTerm lines, then Import, then Select model. A dark line does not fire. |
| fn | [`apply_console_label`](#function-apply_console_label) | The function `apply_console_label` applies one console menu line to `seat`. |
| fn | [`open_console_url`](#function-open_console_url) | Java `Desktop.browse`. A string that is not a URL stays closed. |
| fn | [`shell_file_script`](#function-shell_file_script) | Shell file dialog. Java's remembered chooser sets no title and no filter. |
| fn | [`step_font`](#function-step_font) | The spinner range. A step that would leave it stays on the edge. |
| fn | [`frame_usable`](#function-frame_usable) | A restored rectangle meets a screen and the usable minimum. |
| fn | [`note_frame_seat`](#function-note_frame_seat) | A move or a resize. Maximized and minimized keep the last restored rectangle. |
| fn | [`open_frame_seat`](#function-open_frame_seat) | A usable saved rectangle returns that size and place. |
| fn | [`looks_binary`](#function-looks_binary) | NUL in the sample, or control bytes other than tab and the newline family |
| fn | [`console_text`](#function-console_text) | Send file read. The cap is Java `String.length`. Console import uses [`console_import_text`]. |
| fn | [`console_import_text`](#function-console_import_text) | Console import. The same refusals as [`console_text`], with no character cap. |
| fn | [`paste_decision`](#function-paste_decision) | The function `paste_decision` classifies editor text before a terminal paste. |
| fn | [`paste_wrapped`](#function-paste_wrapped) | Java `sendPaste`. The markers always wrap. LF stays. No trailing Enter. |
| fn | [`focused_link`](#function-focused_link) | The function `focused_link` chooses the [`PaneLink`] for the pane that is in front. |
| fn | [`link_from_status`](#function-link_from_status) | The function `link_from_status` maps one status line onto a [`PaneLink`]. |
| fn | [`shell_enables`](#function-shell_enables) | The function `shell_enables` fills the open-bar flags for one pane. |
| fn | [`count_leaves`](#function-count_leaves) | The function `count_leaves` counts pane ids in a [`PaneNode`] tree. |
| fn | [`first_leaf`](#function-first_leaf) | The function `first_leaf` returns the pane id of the first leaf. |
| fn | [`graft_split`](#function-graft_split) | The new leaf is the right child. The ratio is half. |
| fn | [`detach_leaf`](#function-detach_leaf) | Remove one leaf. The sibling takes that split's place. The last leaf stays. |
| fn | [`set_split_ratio`](#function-set_split_ratio) | The function `set_split_ratio` writes a split ratio at one path. |
| fn | [`layout_panes`](#function-layout_panes) | The function `layout_panes` builds a [`SplitLayout`] for one pane tree. |
| fn | [`walk_panes`](#function-walk_panes) | The function `walk_panes` lays one [`PaneNode`] into leaf rectangles and divider bars. |
| fn | [`half_span`](#function-half_span) | Half of the span after the gap. The two sides match. |
| fn | [`monitor_rects`](#function-monitor_rects) | Screens the restored frame can land on. Physical pixels. |
| fn | [`io_text`](#function-io_text) | The function `io_text` turns an I/O error into text a caller can show. |
| fn | [`read_growing`](#function-read_growing) | Chunked read. The buffer grows from the bytes that arrive. |
| fn | [`read_prompt_bytes`](#function-read_prompt_bytes) | Prompt bytes. A NUL in the first [`NUL_SCAN`] bytes is binary and the rest stays on disk. |
| fn | [`read_console_import`](#function-read_console_import) | Session import. A refusal is binary or empty. A failed read is the thrown message. |
| fn | [`read_console_file`](#function-read_console_file) | Read a console file the way Java `readConsoleText` does. |
| fn | [`rects_intersect`](#function-rects_intersect) | The function `rects_intersect` reports whether two rectangles overlap. |
| fn | [`chooser_directory_live`](#function-chooser_directory_live) | A missing or empty folder, or a path that is not a directory, stays off the dialog. |
| fn | [`chooser_parent_dir`](#function-chooser_parent_dir) | The parent of an approved file, when that parent is a directory. |
| fn | [`parse_chooser_report`](#function-parse_chooser_report) | `BOUNDS x y w h` and `FILE path`. A quiet line is a cancel. A non-positive size is dropped. |
| fn | [`keep_chooser`](#function-keep_chooser) | Bounds on every close. The folder only when a file is approved. |
| fn | [`pick_import_file`](#function-pick_import_file) | The chooser. Cancel leaves the file empty. A miss names the failure. |
| fn | [`pick_shell_file`](#function-pick_shell_file) | Send file and console import. Same runner as the cell door. |
| fn | [`begin_import`](#function-begin_import) | Merge and output refuse before the chooser. Cancel returns no status. |
| fn | [`load_import`](#function-load_import) | The function `load_import` reads a prompt file into one card. |
| fn | [`click_inside`](#function-click_inside) | The pointer released a primary click inside the painted part of this card. |
| fn | [`accept_fault_deck`](#function-accept_fault_deck) | The function `accept_fault_deck` appends a fault body onto one card. |
| fn | [`reply_opens_turn`](#function-reply_opens_turn) | A reply or an end on the running last card opens the next turn. |
| fn | [`land_open_turn`](#function-land_open_turn) | Seat the piece. When it opens the next turn, return that card's index. |
| fn | [`route_scope`](#function-route_scope) | The function `route_scope` applies one [`ScopePiece`] to the canvas deck. |
| fn | [`route_console_thought`](#function-route_console_thought) | A nil-cell kind t belongs on the VT100 strip. The notebook log stays. |
| fn | [`clear_canvas`](#function-clear_canvas) | The function `clear_canvas` drops the seated canvas facts and the gathered scope text. |
| fn | [`canvas_body`](#function-canvas_body) | Seated record of the two words plus later scope text. |
| fn | [`fact_line`](#function-fact_line) | One line above the inspector. Empty until both words are seated. |
| fn | [`shown_target`](#function-shown_target) | Empty stored target shows as `ollama`. Java `AetherisStage2.shownTarget`. |
| fn | [`ssh_heat_budget`](#function-ssh_heat_budget) | The SSH heat budget. A managed count above zero is that count. |
| fn | [`air_gap_word`](#function-air_gap_word) | The function `air_gap_word` returns the air-gap word shown on the status line. |
| fn | [`status_extra`](#function-status_extra) | Connection words stay on the link chip. A canvas word is the status-line tail. |
| fn | [`aetheris_status_line`](#function-aetheris_status_line) | Java `statusLine`. The vault word is disk. The Rust vault is the file. |
| fn | [`shell_mode_word`](#function-shell_mode_word) | VT100 or PTY. A missing or unknown token is VT100. AMWP stays off this strip. |
| fn | [`shell_term_type`](#function-shell_term_type) | TERM for that mode. Java `SshSessionMode.getTermType`. |
| fn | [`shell_status_line`](#function-shell_status_line) | The VT100 strip. The wire word is the console mode. The Aetheris strip keeps AMWP. |
| fn | [`status_facts_line`](#function-status_facts_line) | The function `status_facts_line` builds one status line for the notebook or the terminal. |
| fn | [`status_user_name`](#function-status_user_name) | East name. Empty unless the session is a connected AMWP user. |
| fn | [`shell_status_west`](#function-shell_status_west) | West word on the SSH status strip. The chip keeps the short seat word. |
| fn | [`status_bar_height`](#function-status_bar_height) | The function `status_bar_height` returns the height of the status strip. |
| fn | [`paint_status_bar`](#function-paint_status_bar) | West is the notebook line. East is the user name. The fill is the Java status color. |
| fn | [`icon_ground`](#function-icon_ground) | A light, dull corner is the rounded ground. The eye stays. |
| fn | [`fill_icon_ground`](#function-fill_icon_ground) | The rounded ground becomes black. The eye stays. The plate stays square. |
| fn | [`window_icon_rgba`](#function-window_icon_rgba) | Eye of Horus, then the casserole mark. RGBA at [`WINDOW_ICON_PX`]. |
| fn | [`about_app_rgba`](#function-about_app_rgba) | Eye of Horus at [`ABOUT_APP_PX`]. The ground is black. The eye stays. |
| fn | [`seat_about_mark`](#function-seat_about_mark) | One About mark as a texture. A failed decode leaves the seat empty. |
| fn | [`decode_icon_rgba`](#function-decode_icon_rgba) | The function `decode_icon_rgba` decodes an image and scales it to a square. |
| fn | [`window_icon`](#function-window_icon) | The function `window_icon` builds the window icon for the client window. |
| fn | [`divider_center_x`](#function-divider_center_x) | Center of the divider. `origin_x` is the row's left. `canvas_w` is the canvas column. |
| fn | [`pin_top_seat`](#function-pin_top_seat) | P on the top of the divider. Center x is the bar. Top is the bar's top. |
| fn | [`pin_show_seat`](#function-pin_show_seat) | P on the divider. The rect stays inside the band and still covers the bar. |
| fn | [`pin_show_covers_bar`](#function-pin_show_covers_bar) | The seat still overlaps the bar, including when the bar is against an edge. |
| fn | [`fact_line_rect`](#function-fact_line_rect) | The inspector's share of the tab row. `avoid` is P, so the words start clear of it. |
| fn | [`settle_plate`](#function-settle_plate) | The function `settle_plate` chooses the plate under the tab strip. |
| fn | [`open_product_tab`](#function-open_product_tab) | Open sits on SSH. A stored `aetheris` word arms the wait for Connect. |
| fn | [`step_product_tab`](#function-step_product_tab) | One product-tab step. A click wins over an armed restore. |
| fn | [`close_product_tab`](#function-close_product_tab) | Close stores the visible plate. An armed restore leaves the file alone. |
| fn | [`run`](#function-run) | Open the window. Tests do not call this. |
| fn | [`scope_home`](#function-scope_home) | A piece for the user on the glass stays on the glass. |
| fn | [`focused_canvas_home`](#function-focused_canvas_home) | Focused canvas pieces stay on the glass when that pane's user is the glass, |
| fn | [`glass_run_pane`](#function-glass_run_pane) | The pane that carries this glass user's execute. |
| fn | [`think_salt`](#function-think_salt) | Scroll id for one canvas user's thinking pane. |
| fn | [`feed_parts`](#function-feed_parts) | Seat scope on one deck. The caller wires `frames` on the session that delivered the pieces. |
| fn | [`seat_bar_font`](#function-seat_bar_font) | The master control bar uses the same point size as the panes. |
| fn | [`control_mark`](#function-control_mark) | `v` while the connection bar is showing. `^` while it is folded. |
| fn | [`control_tip`](#function-control_tip) | The function `control_tip` returns the hover text for the fold mark. The parameter `open` is true while the connection bar is showing. An open bar returns `CONTROL_FOLD_TIP`. A folded bar returns `CONTROL_SHOW_TIP`. |
| fn | [`send_file_button`](#function-send_file_button) | Send file keeps the full word. Three ems past the text clear the round edge. |
| fn | [`seat_plate_tabs`](#function-seat_plate_tabs) | SSH stays on. Aetheris waits for the mux. |
| fn | [`paint_link_status`](#function-paint_link_status) | Connected wears the green chip. Every other line keeps the plate ink. |
| fn | [`paint_link_chip`](#function-paint_link_chip) | Green face, white letters, light edge on the top and left, dark edge on the bottom and right. |
| fn | [`step_control_bar`](#function-step_control_bar) | The bar starts open. Connect folds it. Disconnect opens it. |
| fn | [`step_control_link`](#function-step_control_link) | Connected seats the link. Disconnected clears it. A resize and the mark leave it. |
| fn | [`control_after_status`](#function-control_after_status) | A shell status. Connecting, and every canvas line, leave the bar and the link. |
| fn | [`note_control_span`](#function-note_control_span) | The first frame size is the open frame. A later change is a resize. |
| fn | [`connect_allowed`](#function-connect_allowed) | Connect stays off while a field is empty, the port is bad, or a session is up. |
| fn | [`parse_port`](#function-parse_port) | The function `parse_port` reads a TCP port from text. The parameter `text` is the field contents. Surrounding whitespace is removed. A value that is not a whole number returns `None`. |
| fn | [`clamp_logical`](#function-clamp_logical) | A resize below the minimum becomes the minimum. The axes are logical pixels. |
| fn | [`open_zoom`](#function-open_zoom) | The zoom after `steps` of Ctrl-+. Each step adds one tenth, then rounds |
| fn | [`view_on_tail`](#function-view_on_tail) | The view is on the tail when the offset sits on the bottom of the content. |
| fn | [`shift_key`](#function-shift_key) | The function `shift_key` reports whether a key is a Shift key. The parameter `key` is the egui key. Left Shift returns true. Right Shift returns true. Every other key returns false. |
| fn | [`shift_released_just_before`](#function-shift_released_just_before) | Shift went up, and no other key went down between that release and this event. |
| fn | [`enter_stroke`](#function-enter_stroke) | Enter is CR. Shift-Enter is one LF. Command or Alt keeps the CR. |
| fn | [`stroke_of`](#function-stroke_of) | The function `stroke_of` turns one egui event into a [`TermStroke`]. The parameter `event` is that event. A copy event becomes [`TermStroke::Key`] of `Key::CtrlC`. A paste event becomes [`TermStroke::Paste`] with the cloned text. |
| fn | [`stroke_at`](#function-stroke_at) | The Enter event's shift bit can be clear while Shift is held, or just released. |
| fn | [`map_key`](#function-map_key) | The function `map_key` turns one pressed egui key into a [`TermStroke`]. The parameter `key` is that key. The parameter `modifiers` supplies command, shift, and alt. An unmapped key returns `None`. |
| fn | [`cell_at`](#function-cell_at) | The function `cell_at` returns the grid row and column under a pointer. The parameter `origin` is the grid's top-left point. The parameter `pos` is the pointer. The parameters `char_w` and `row_h` are the cell size. The parameters `cols` and `rows` are the grid size. The result is `(row, column)`. |
| fn | [`selection_lines`](#function-selection_lines) | One highlight. `keep_spaces` leaves the trailing spaces on each row. |
| fn | [`selected_text`](#function-selected_text) | The function `selected_text` returns the highlighted grid text with trailing spaces removed on each row. The parameter `grid` is the terminal grid. The parameters `r0` and `c0` are one corner. The parameters `r1` and `c1` are the other corner. The corners may be in either order. |
| fn | [`last_screen_line`](#function-last_screen_line) | Last non-empty row on the screen that is showing. Scrollback stays out. |
| fn | [`pull_source`](#function-pull_source) | Highlight when its trim is not empty. Spaces in that highlight stay. |
| fn | [`ask_pull`](#function-ask_pull) | A new card. The prompt is the terminal text. The mark is Excluded. |
| fn | [`terminal_card`](#function-terminal_card) | The focused card. A focus past the end uses the last card. An empty deck is none. |
| fn | [`ask_terminal`](#function-ask_terminal) | Reply when its trim is not empty. Otherwise the prompt. An empty body is quiet. |
| fn | [`terminal_preview`](#function-terminal_preview) | Newlines become spaces. Past `max` UTF-16 units, the rest becomes an ellipsis. |
| fn | [`terminal_confirm_word`](#function-terminal_confirm_word) | `OK` confirms. Every other word cancels. |
| fn | [`ask_terminal_confirm`](#function-ask_terminal_confirm) | The function `ask_terminal_confirm` asks the operator to confirm a terminal send. The parameter `preview` is the shortened text shown in the chooser. The function runs `powershell.exe` with `-NoProfile`, `-STA`, and `-Command`. The command text is `TERMINAL_CONFIRM_CHOOSER`. The preview is placed in the environment variable `AETHERIS_TERMINAL_PREVIEW`. |
| fn | [`terminal_normalize`](#function-terminal_normalize) | Collapse CR LF and a bare CR to LF. |
| fn | [`terminal_units`](#function-terminal_units) | UTF-16 units. That is Java `String.length`. |
| fn | [`prepare_terminal`](#function-prepare_terminal) | LF-only body. Over the cap is refused. The dialog has not opened. |
| fn | [`terminal_bytes`](#function-terminal_bytes) | Bracketed paste when a session is up. LF stays. No trailing Enter. |
| fn | [`begin_terminal`](#function-begin_terminal) | Confirm, then the full text. Cancel leaves the status. A chooser miss names the reason. |
| fn | [`in_selection`](#function-in_selection) | The function `in_selection` reports whether one cell lies inside the highlight. The parameter `anchor` is one corner. The parameter `current` is the other corner. The parameters `row` and `col` are the cell. A missing corner returns false. |
| fn | [`paint_colors`](#function-paint_colors) | The function `paint_colors` returns the foreground and background for one terminal cell. The parameter `cell` is that [`Cell`]. The parameter `selected` is true when the cell is inside the highlight. The result is `(foreground, background)`. |
| fn | [`color_of`](#function-color_of) | The function `color_of` turns a terminal [`Color`] into an egui color. The parameter `color` is that color. The parameter `fallback` is used for the default color. [`Color::Default`] returns `fallback`. |
| fn | [`index_rgb`](#function-index_rgb) | The function `index_rgb` returns the red, green, and blue bytes for one color index. The parameter `index` is that index. The result is `[red, green, blue]`. |

## Constant Detail

### CANVAS_TAB

<a id="constant-canvas_tab"></a>

```rust
const CANVAS_TAB: &str = "Canvas";
```

The one canvas tab on the empty plate. The vault name waits.

### TURN_LABEL

<a id="constant-turn_label"></a>

```rust
const TURN_LABEL: &str = "Turn 1";
```

The first card. Later cards use [`turn_label`](#function-turn_label).

### RUN_LABEL

<a id="constant-run_label"></a>

```rust
const RUN_LABEL: &str = "Run";
```

`RUN_LABEL` is the label on the Run button. The canvas footer shows that button. The hover text on that button is [`RUN_TIP`](#constant-run_tip).

### STOP_LABEL

<a id="constant-stop_label"></a>

```rust
const STOP_LABEL: &str = "Stop";
```

`STOP_LABEL` is the label on the Stop button. The canvas footer shows that button beside Run. The hover text on that button is [`STOP_TIP`](#constant-stop_tip).

### RUN_TIP

<a id="constant-run_tip"></a>

```rust
const RUN_TIP: &str = "Run sends the focused turn to its engine. A grouped member sends every member of that group, each as its own call. The merge cell is not sent. The call keeps the earlier turns this card is allowed to see, then writes the reply on that cell. Ctrl+Enter does the same send when the main view is open.";
```

`RUN_TIP` is the hover text on the Run button in the canvas footer. The button sends the focused turn to its engine. A member of a group sends every member of that group.
Each member goes out as its own call. The merge cell is not sent. The call keeps the earlier turns this card is allowed to see. The call then writes the reply on that cell.
Ctrl+Enter does the same send while the main view is open.

### STOP_TIP

<a id="constant-stop_tip"></a>

```rust
const STOP_TIP: &str = "Stop cancels the turn that is running. Other turns in a group keep running. The reply already stored on the cell stays. When nothing is running, the status line says nothing is running.";
```

`STOP_TIP` is the hover text on the Stop button in the canvas footer. Stop cancels the turn that is running. Other turns in a group keep running.
The reply already stored on the cell stays. The status line reports that nothing is running when no turn is running.

### NEW_LABEL

<a id="constant-new_label"></a>

```rust
const NEW_LABEL: &str = "New";
```

`NEW_LABEL` is the label on the New button. The canvas footer shows that button. The hover text on that button is [`NEW_TIP`](#constant-new_tip).

### CLEAR_LABEL

<a id="constant-clear_label"></a>

```rust
const CLEAR_LABEL: &str = "Clear";
```

`CLEAR_LABEL` is the label on the Clear button. The canvas footer shows that button. The hover text on that button is [`CLEAR_TIP`](#constant-clear_tip).

### DELETE_LABEL

<a id="constant-delete_label"></a>

```rust
const DELETE_LABEL: &str = "Delete";
```

`DELETE_LABEL` is the label on the Delete button. The canvas footer shows that button. The hover text on that button is [`DELETE_TIP`](#constant-delete_tip).

### DELETED_TURN

<a id="constant-deleted_turn"></a>

```rust
const DELETED_TURN: &str = "deleted turn on Canvas";
```

`DELETED_TURN` is the status text after Delete removes one turn. The status line shows this text. A group delete uses [`DELETED_GROUP`](#constant-deleted_group) instead.

### DELETED_GROUP

<a id="constant-deleted_group"></a>

```rust
const DELETED_GROUP: &str = "deleted group on Canvas";
```

`DELETED_GROUP` is the status text after Delete removes a whole group. The status line shows this text. A single-turn delete uses [`DELETED_TURN`](#constant-deleted_turn) instead.

### DELETE_TIP

<a id="constant-delete_tip"></a>

```rust
const DELETE_TIP: &str = "Delete removes the armed card. A normal card removes that one row. A card in a group removes that whole group and its merge. The button stays off when the delete would leave the canvas empty. Pipe tokens stay as written.";
```

`DELETE_TIP` is the hover text on the Delete button in the canvas footer. Delete removes the armed card. A card that is not in a group removes that one row.
A card in a group removes that whole group and its merge. The button stays off when the delete would leave the canvas empty. A pipe pointer already written in the text is left unchanged.

### COLLAPSE_ALL

<a id="constant-collapse_all"></a>

```rust
const COLLAPSE_ALL: &str = "Collapse All";
```

`COLLAPSE_ALL` is the label on the Collapse All button. The canvas footer shows that button. The hover text on that button is [`COLLAPSE_ALL_TIP`](#constant-collapse_all_tip).

### EXPAND_ALL

<a id="constant-expand_all"></a>

```rust
const EXPAND_ALL: &str = "Expand All";
```

`EXPAND_ALL` is the label on the Expand All button. The canvas footer shows that button beside Collapse All. The hover text on that button is [`EXPAND_ALL_TIP`](#constant-expand_all_tip).

### FOLD_OPEN

<a id="constant-fold_open"></a>

```rust
const FOLD_OPEN: &str = "+";
```

`FOLD_OPEN` is the face of the fold button while a card is folded. The card bar shows this mark. The hover text in that state is [`FOLD_OPEN_TIP`](#constant-fold_open_tip).

### FOLD_SHUT

<a id="constant-fold_shut"></a>

```rust
const FOLD_SHUT: &str = "-";
```

`FOLD_SHUT` is the face of the fold button while a card is open. The card bar shows this mark. The hover text in that state is [`FOLD_SHUT_TIP`](#constant-fold_shut_tip).

### FOLD_OPEN_TIP

<a id="constant-fold_open_tip"></a>

```rust
const FOLD_OPEN_TIP: &str = "Expand opens the prompt and the reply under the buttons.";
```

`FOLD_OPEN_TIP` is the hover text on the fold button while a card is folded. Expand opens the prompt under the buttons. Expand also opens the reply under the buttons.

### FOLD_SHUT_TIP

<a id="constant-fold_shut_tip"></a>

```rust
const FOLD_SHUT_TIP: &str = "Collapse keeps a short line of the reply, or of the prompt when there is no reply, to the right of the buttons. The text stays.";
```

`FOLD_SHUT_TIP` is the hover text on the fold button while a card is open. Collapse keeps a short line of the reply to the right of the buttons. A card with no reply keeps a short line of the prompt there instead.
The text stays stored.

### COLLAPSE_ALL_TIP

<a id="constant-collapse_all_tip"></a>

```rust
const COLLAPSE_ALL_TIP: &str = "Collapse All folds every turn on this canvas. Each card keeps a short line of the reply to the right of its buttons. The prompt and the reply stay stored.";
```

`COLLAPSE_ALL_TIP` is the hover text on the Collapse All button in the canvas footer. Collapse All folds every turn on this canvas. Each card keeps a short line of the reply to the right of its buttons.
The prompt stays stored. The reply stays stored.

### EXPAND_ALL_TIP

<a id="constant-expand_all_tip"></a>

```rust
const EXPAND_ALL_TIP: &str = "Expand All opens every turn. The prompt and the reply show under the buttons.";
```

`EXPAND_ALL_TIP` is the hover text on the Expand All button in the canvas footer. Expand All opens every turn. The prompt shows under the buttons. The reply shows under the buttons.

### HIDE_COLLAPSED

<a id="constant-hide_collapsed"></a>

```rust
const HIDE_COLLAPSED: &str = "Hide Collapsed";
```

`HIDE_COLLAPSED` is the label on the Hide Collapsed button. The tool row of the canvas footer shows that button. The hover text on that button is [`HIDE_COLLAPSED_TIP`](#constant-hide_collapsed_tip).

### SHOW_COLLAPSED

<a id="constant-show_collapsed"></a>

```rust
const SHOW_COLLAPSED: &str = "Show Collapsed";
```

`SHOW_COLLAPSED` is the label on the Show Collapsed button. The tool row of the canvas footer shows that button after Hide Collapsed. The hover text on that button is [`SHOW_COLLAPSED_TIP`](#constant-show_collapsed_tip).

### HIDE_COLLAPSED_TIP

<a id="constant-hide_collapsed_tip"></a>

```rust
const HIDE_COLLAPSED_TIP: &str = "Hide Collapsed takes every folded card off this canvas. The cards stay stored. Show Collapsed brings them back. The choice is saved.";
```

`HIDE_COLLAPSED_TIP` is the hover text on the Hide Collapsed button. Hide Collapsed takes every folded card off this canvas. The cards stay stored.
Show Collapsed brings those cards back. The window stores that choice.

### SHOW_COLLAPSED_TIP

<a id="constant-show_collapsed_tip"></a>

```rust
const SHOW_COLLAPSED_TIP: &str = "Show Collapsed puts the folded cards back on this canvas. They sit where they sat, still folded. The choice is saved.";
```

`SHOW_COLLAPSED_TIP` is the hover text on the Show Collapsed button. Show Collapsed puts the folded cards back on this canvas. Those cards sit where they sat. Those cards stay folded.
The window stores that choice.

### BRIEF_MIN

<a id="constant-brief_min"></a>

```rust
const BRIEF_MIN: f32 = 20.0;
```

Swing pins the brief. Preferred width and minimum width are 20.

### COPY_LABEL

<a id="constant-copy_label"></a>

```rust
const COPY_LABEL: &str = "Copy";
```

`COPY_LABEL` is the label on the Copy button. The card bar shows that button. The reply window uses the same word on its own Copy button.

### COPY_TIP

<a id="constant-copy_tip"></a>

```rust
const COPY_TIP: &str = "Copy copies this turn's whole reply to the clipboard. The prompt stays. The button stays off until this turn has a reply.";
```

`COPY_TIP` is the hover text on the Copy button on a card. Copy places that turn's whole reply on the clipboard. The prompt stays on the card.
The button stays off until this turn has a reply.

### BURNER_LABEL

<a id="constant-burner_label"></a>

```rust
const BURNER_LABEL: &str = "burner";
```

`BURNER_LABEL` is the label on the burner checkbox. The card bar shows that checkbox. The hover text on that checkbox is [`BURNER_TIP`](#constant-burner_tip).

### BURNER_DENIED

<a id="constant-burner_denied"></a>

```rust
const BURNER_DENIED: &str = "burner denied";
```

`BURNER_DENIED` is the status text when a cloud run is denied on a burner turn. The status line shows this text. A turn in a group also opens a notice with this text.

### BURNER_TIP

<a id="constant-burner_tip"></a>

```rust
const BURNER_TIP: &str = "Burner marks this turn as ephemeral. The prompt and the reply stay. A cloud Run is denied. An Ollama Run still sends.";
```

`BURNER_TIP` is the hover text on the burner checkbox on a card. Burner marks this turn as ephemeral. The prompt stays. The reply stays.
A cloud Run is denied. An Ollama Run still sends.

### EXPANDED_LABEL

<a id="constant-expanded_label"></a>

```rust
const EXPANDED_LABEL: &str = "Expanded View";
```

`EXPANDED_LABEL` is the label on the Expanded View checkbox. The card bar shows that checkbox. The hover text on that checkbox is [`EXPANDED_TIP`](#constant-expanded_tip).

### EXPANDED_TIP

<a id="constant-expanded_tip"></a>

```rust
const EXPANDED_TIP: &str = "Expanded View shows the whole reply. Uncheck it and the reply stays in a window the same height as the prompt. The footer count sets that height. The text stays. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell's choice. The check is saved for that cell and comes back when the app opens.";
```

`EXPANDED_TIP` is the hover text on the Expanded View checkbox on a card. A checked box shows the whole reply. An unchecked box keeps the reply in a window the same height as the prompt.
The row count in the footer sets that height. The text stays stored. A fresh canvas starts unchecked. Clear starts unchecked.
A new turn keeps the prior cell's choice. The check is saved for that cell. The check comes back when the app opens.

### EXPANDED_YAML_FILE

<a id="constant-expanded_yaml_file"></a>

```rust
const EXPANDED_YAML_FILE: &str = "aetheris-expanded.yaml";
```

Per-cell Expanded View. Java and Rust share this file.

### HIDE_YAML_FILE

<a id="constant-hide_yaml_file"></a>

```rust
const HIDE_YAML_FILE: &str = "aetheris-hide.yaml";
```

Hide Collapsed for this window. Missing means Show. Not a vault column.

### SETTINGS_YAML_FILE

<a id="constant-settings_yaml_file"></a>

```rust
const SETTINGS_YAML_FILE: &str = "aetheris-settings.yaml";
```

Global switches for this window. Missing means the quiet plate. Not a vault column.

| | |
| --- | --- |
| **Specification** | PK-1097 docs/specs/common/formal-plan.spec.md FINESSE:f-182 |

### CELL_VIEW_ROWS

<a id="constant-cell_view_rows"></a>

```rust
const CELL_VIEW_ROWS: usize = 4;
```

Default open-cell rows. A missing door key is this count. The footer count replaces it.

### ROW_MINUS_TIP

<a id="constant-row_minus_tip"></a>

```rust
const ROW_MINUS_TIP: &str = "Fewer rows shown on each open turn. The prompt keeps this many lines. An unchecked Expanded View reply keeps this many lines too. A checked Expanded View reply stays whole. The count is saved with the shell. The smallest count is 1.";
```

`ROW_MINUS_TIP` is the hover text on the minus button beside the row count. That button sits in the canvas footer. The button shows fewer rows on each open turn.
The prompt keeps this many lines. An unchecked Expanded View reply keeps this many lines too. A checked Expanded View reply stays whole.
The count is saved with the shell. The shell is the saved layout for this window. The smallest count is 1.

### ROW_PLUS_TIP

<a id="constant-row_plus_tip"></a>

```rust
const ROW_PLUS_TIP: &str = "More rows shown on each open turn. The prompt keeps this many lines. An unchecked Expanded View reply keeps this many lines too. A checked Expanded View reply stays whole. The count is saved with the shell.";
```

`ROW_PLUS_TIP` is the hover text on the plus button beside the row count. That button sits in the canvas footer. The button shows more rows on each open turn.
The prompt keeps this many lines. An unchecked Expanded View reply keeps this many lines too. A checked Expanded View reply stays whole.
The count is saved with the shell. The shell is the saved layout for this window.

### ROW_FIELD_TIP

<a id="constant-row_field_tip"></a>

```rust
const ROW_FIELD_TIP: &str = "Row count for each open turn. Type a number. It applies after a short pause. The prompt keeps this many lines. An unchecked Expanded View reply keeps this many lines too. A checked Expanded View reply stays whole. The count is saved with the shell. The smallest count is 1.";
```

`ROW_FIELD_TIP` is the hover text on the row-count field in the canvas footer. The field sets the row count for each open turn. You type a number. The number applies after a short pause.
The prompt keeps this many lines. An unchecked Expanded View reply keeps this many lines too. A checked Expanded View reply stays whole.
The count is saved with the shell. The shell is the saved layout for this window. The smallest count is 1.

### VIEW_LABEL

<a id="constant-view_label"></a>

```rust
const VIEW_LABEL: &str = "View";
```

`VIEW_LABEL` is the label on the View button. The card bar shows that button. The hover text on that button is [`VIEW_TIP`](#constant-view_tip).

### VIEW_TIP

<a id="constant-view_tip"></a>

```rust
const VIEW_TIP: &str = "View opens the stored reply in a read-only window. The window size is remembered for this vault user. The button stays off until this turn has a reply.";
```

`VIEW_TIP` is the hover text on the View button on a card. View opens the stored reply in a read-only window. The window size is remembered for this vault user.
The button stays off until this turn has a reply.

### VIEW_CLOSE

<a id="constant-view_close"></a>

```rust
const VIEW_CLOSE: &str = "Close";
```

`VIEW_CLOSE` is the label on the Close button in the reply window. That button leaves the window. The hover text on that button is [`VIEW_CLOSE_TIP`](#constant-view_close_tip).

### VIEW_CLOSE_TIP

<a id="constant-view_close_tip"></a>

```rust
const VIEW_CLOSE_TIP: &str = "Close the reply window and return to the canvas. The window size is saved for this vault user.";
```

`VIEW_CLOSE_TIP` is the hover text on the Close button in the reply window. Close leaves the reply window. Close returns to the canvas.
The window size is saved for this vault user.

### VIEW_COPY_TIP

<a id="constant-view_copy_tip"></a>

```rust
const VIEW_COPY_TIP: &str = "Copy copies this window's whole reply to the clipboard. The find field stays.";
```

`VIEW_COPY_TIP` is the hover text on the Copy button in the reply window. Copy places this window's whole reply on the clipboard. The find field stays open.

### VIEW_FIND_TIP

<a id="constant-view_find_tip"></a>

```rust
const VIEW_FIND_TIP: &str = "Type to highlight every match in this reply. Ctrl-F finds the next match. Esc closes this bar.";
```

`VIEW_FIND_TIP` is the hover text on the find field in the reply window. Typing highlights every match in this reply. Ctrl-F finds the next match.
Esc closes this bar.

### VIEW_PREV

<a id="constant-view_prev"></a>

```rust
const VIEW_PREV: &str = "^";
```

`VIEW_PREV` is the face of the previous-match button in the reply window. That button sits on the find bar. The hover text on that button is [`VIEW_PREV_TIP`](#constant-view_prev_tip).

### VIEW_NEXT

<a id="constant-view_next"></a>

```rust
const VIEW_NEXT: &str = "v";
```

`VIEW_NEXT` is the face of the next-match button in the reply window. That button sits on the find bar beside the previous-match button. The hover text on that button is [`VIEW_NEXT_TIP`](#constant-view_next_tip).

### VIEW_PREV_TIP

<a id="constant-view_prev_tip"></a>

```rust
const VIEW_PREV_TIP: &str = "Previous match. Wraps to the end.";
```

`VIEW_PREV_TIP` is the hover text on the previous-match button in the reply window. The button moves to the previous match. The search wraps to the end after the first match.

### VIEW_NEXT_TIP

<a id="constant-view_next_tip"></a>

```rust
const VIEW_NEXT_TIP: &str = "Next match. Wraps to the start.";
```

`VIEW_NEXT_TIP` is the hover text on the next-match button in the reply window. The button moves to the next match. The search wraps to the start after the last match.

### VIEW_CAP

<a id="constant-view_cap"></a>

```rust
const VIEW_CAP: usize = 2000;
```

Swing `ViewFind.CAP`. A further match paints a plus on the count.

### VIEW_MIN_W

<a id="constant-view_min_w"></a>

```rust
const VIEW_MIN_W: i32 = 200;
```

`VIEW_MIN_W` is the smallest width of the reply window, in pixels. A saved rectangle narrower than this width is dropped. The window also refuses to shrink below this width when the screen is at least this wide.

### VIEW_MIN_H

<a id="constant-view_min_h"></a>

```rust
const VIEW_MIN_H: i32 = 120;
```

`VIEW_MIN_H` is the smallest height of the reply window, in pixels. A saved rectangle shorter than this height is dropped. The window also refuses to shrink below this height when the screen is at least this tall.

### VIEW_OPEN_W

<a id="constant-view_open_w"></a>

```rust
const VIEW_OPEN_W: i32 = 720;
```

`VIEW_OPEN_W` is the width used when the reply window first opens, in pixels. That width is the default before a saved rectangle is applied. The window still stays inside the screen.

### VIEW_OPEN_H

<a id="constant-view_open_h"></a>

```rust
const VIEW_OPEN_H: i32 = 480;
```

`VIEW_OPEN_H` is the height used when the reply window first opens, in pixels. That height is the default before a saved rectangle is applied. The window still stays inside the screen.

### VIEW_TITLE_CLOSE

<a id="constant-view_title_close"></a>

```rust
const VIEW_TITLE_CLOSE: bool = true;
```

The title bar paints the standard close mark. `Window::open` is that mark.

### VIEW_INK

<a id="constant-view_ink"></a>

```rust
const VIEW_INK: egui::Color32 = egui::Color32::from_rgb(0xff, 0xff, 0x55);
```

`VIEW_INK` is the text color in the reply window. The color is yellow. Every span of the reply uses this color.

### VIEW_FIELD

<a id="constant-view_field"></a>

```rust
const VIEW_FIELD: egui::Color32 = egui::Color32::from_rgb(0x1a, 0x1a, 0x1a);
```

`VIEW_FIELD` is the background color of reply text that is not a match. The color is near black. A match uses [`VIEW_MATCH_BG`](#constant-view_match_bg) instead.

### VIEW_MATCH_BG

<a id="constant-view_match_bg"></a>

```rust
const VIEW_MATCH_BG: egui::Color32 = egui::Color32::from_rgb(0x6b, 0x5a, 0x1e);
```

`VIEW_MATCH_BG` is the background color of a find match that is not the current match. The color is dark gold. The current match uses a different background.

### VIEW_BODY

<a id="constant-view_body"></a>

```rust
const VIEW_BODY: u8 = 0;
```

`VIEW_BODY` is the span kind for reply text that is not a match. The value is 0. That span paints with [`VIEW_FIELD`](#constant-view_field) behind the text.

### VIEW_MATCH

<a id="constant-view_match"></a>

```rust
const VIEW_MATCH: u8 = 1;
```

`VIEW_MATCH` is the span kind for a find match that is not the current match. The value is 1. That span paints with [`VIEW_MATCH_BG`](#constant-view_match_bg) behind the text.

### VIEW_CURRENT

<a id="constant-view_current"></a>

```rust
const VIEW_CURRENT: u8 = 2;
```

`VIEW_CURRENT` is the span kind for the current find match. The value is 2. That span paints with a different background from [`VIEW_MATCH`](#constant-view_match).

### CLIPBOARD_BUSY

<a id="constant-clipboard_busy"></a>

```rust
const CLIPBOARD_BUSY: &str = "clipboard busy";
```

Swing's status word when the clipboard set fails.

### SELECT_MODEL_LABEL

<a id="constant-select_model_label"></a>

```rust
const SELECT_MODEL_LABEL: &str = "Select model";
```

`SELECT_MODEL_LABEL` is the Select model line on the console menu. A right-click on the terminal shows that line. The hover text for that action is [`SELECT_MODEL_TIP`](#constant-select_model_tip).

### SELECT_MODEL_TIP

<a id="constant-select_model_tip"></a>

```rust
const SELECT_MODEL_TIP: &str = "Select model opens the Model Config modal for this console. The next session comes back on that family and that model.";
```

`SELECT_MODEL_TIP` is the hover text for Select model on the console menu. Select model opens the Model Config modal for this console. The next session comes back on that family. The next session comes back on that model.

### IMPORT_LABEL

<a id="constant-import_label"></a>

```rust
const IMPORT_LABEL: &str = "Import...";
```

`IMPORT_LABEL` is the Import line on a card menu. A right-click on a prompt shows that line. The hover text on that line is [`IMPORT_TIP`](#constant-import_tip).

### DUPLICATE_LABEL

<a id="constant-duplicate_label"></a>

```rust
const DUPLICATE_LABEL: &str = "Duplicate group";
```

`DUPLICATE_LABEL` is the Duplicate group line on a card menu. A grouped card shows that line. The hover text on that line is [`DUPLICATE_TIP`](#constant-duplicate_tip).

### IMPORT_ON_REPLY

<a id="constant-import_on_reply"></a>

```rust
const IMPORT_ON_REPLY: &str = "import refused on output";
```

`IMPORT_ON_REPLY` is the status text when a right-click lands on the reply. The status line shows this text. Import from that click is refused because the click is on the reply.

### IMPORT_MERGE

<a id="constant-import_merge"></a>

```rust
const IMPORT_MERGE: &str = "import refused: merge cell";
```

`IMPORT_MERGE` is the status text when import is refused on a merge cell. The chooser does not open. The refusal is reported before any file is read.

### IMPORT_OUTPUT

<a id="constant-import_output"></a>

```rust
const IMPORT_OUTPUT: &str = "import refused: output only";
```

`IMPORT_OUTPUT` is the status text when import is refused on an output-only card. The chooser does not open. The refusal is reported before any file is read.

### IMPORT_BIG

<a id="constant-import_big"></a>

```rust
const IMPORT_BIG: &str = "import refused: over 1 MiB";
```

`IMPORT_BIG` is the status text when an import file is over 1 MiB. The status line shows this text. The turns already on the canvas stay.

### IMPORT_BINARY

<a id="constant-import_binary"></a>

```rust
const IMPORT_BINARY: &str = "import refused: binary";
```

`IMPORT_BINARY` is the status text when an import file is treated as binary. A NUL byte in the scanned prefix causes that refusal. The prompt is left unchanged.

### IMPORT_FAILED

<a id="constant-import_failed"></a>

```rust
const IMPORT_FAILED: &str = "import failed: ";
```

`IMPORT_FAILED` is the prefix of a status line when an import fails. A reason follows this prefix. The turns already on the canvas stay.

### CHOOSER_MISS

<a id="constant-chooser_miss"></a>

```rust
const CHOOSER_MISS: &str = "the chooser did not open";
```

`CHOOSER_MISS` is the status text when the file chooser does not open. The text is used when that failure leaves no other message. The import does not read a file.

### IMPORT_TIP

<a id="constant-import_tip"></a>

```rust
const IMPORT_TIP: &str = "Import reads a text file into this prompt. The reply stays.";
```

`IMPORT_TIP` is the hover text on the Import line of a card menu. Import reads a text file into this prompt. The reply stays on the card.

### DUPLICATE_TIP

<a id="constant-duplicate_tip"></a>

```rust
const DUPLICATE_TIP: &str =
"Duplicate group copies this group. A card outside a group says no group selected.";
```

`DUPLICATE_TIP` is the hover text on the Duplicate group line of a card menu. Duplicate group copies this group. A card that is not in a group reports that no group is selected.

### IMPORT_CAP

<a id="constant-import_cap"></a>

```rust
const IMPORT_CAP: u64 = 1024 * 1024;
```

Swing `IMPORT_CAP`. The deck refuses a file over this size. This size is legal.
A prompt file over this size says [`LARGE_FILE`](#constant-large_file) and the import continues.

### LARGE_FILE

<a id="constant-large_file"></a>

```rust
const LARGE_FILE: &str = "This file is large. The import continues.";
```

Polite line when a file is past the old bolt. The import still runs.

### NUL_SCAN

<a id="constant-nul_scan"></a>

```rust
const NUL_SCAN: usize = 8192;
```

Swing scans this many leading bytes for a NUL. A NUL past this stays.

### TRANSFER_LABEL

<a id="constant-transfer_label"></a>

```rust
const TRANSFER_LABEL: &str = "Transfer Conversation";
```

`TRANSFER_LABEL` is the Transfer Conversation line. A card menu shows that line. The console menu shows the same line.

### MENU_PROMPT

<a id="constant-menu_prompt"></a>

```rust
const MENU_PROMPT: [&str; 3] = [IMPORT_LABEL, DUPLICATE_LABEL, TRANSFER_LABEL];
```

`MENU_PROMPT` is the menu for a prompt on a card that belongs to a group. The lines are [`IMPORT_LABEL`](#constant-import_label), [`DUPLICATE_LABEL`](#constant-duplicate_label), and [`TRANSFER_LABEL`](#constant-transfer_label). A reply menu leaves the import line out.

### MENU_REPLY

<a id="constant-menu_reply"></a>

```rust
const MENU_REPLY: [&str; 2] = [DUPLICATE_LABEL, TRANSFER_LABEL];
```

`MENU_REPLY` is the menu for a reply on a card that belongs to a group. The lines are [`DUPLICATE_LABEL`](#constant-duplicate_label) and [`TRANSFER_LABEL`](#constant-transfer_label). The import line is not on this menu.

### MENU_PROMPT_LONE

<a id="constant-menu_prompt_lone"></a>

```rust
const MENU_PROMPT_LONE: [&str; 2] = [IMPORT_LABEL, TRANSFER_LABEL];
```

`MENU_PROMPT_LONE` is the menu for a prompt on a card that is not in a group. The lines are [`IMPORT_LABEL`](#constant-import_label) and [`TRANSFER_LABEL`](#constant-transfer_label). The duplicate line is not on this menu.

### MENU_REPLY_LONE

<a id="constant-menu_reply_lone"></a>

```rust
const MENU_REPLY_LONE: [&str; 1] = [TRANSFER_LABEL];
```

`MENU_REPLY_LONE` is the menu for a reply on a card that is not in a group. The only line is [`TRANSFER_LABEL`](#constant-transfer_label). Import and duplicate are not on this menu.

### CHOOSER_MIN_W

<a id="constant-chooser_min_w"></a>

```rust
const CHOOSER_MIN_W: i32 = 240;
```

Swing refuses a saved rectangle under this width.

### CHOOSER_MIN_H

<a id="constant-chooser_min_h"></a>

```rust
const CHOOSER_MIN_H: i32 = 180;
```

Swing refuses a saved rectangle under this height.

### IMPORT_CHOOSER

<a id="constant-import_chooser"></a>

```rust
const IMPORT_CHOOSER: &str = r#"$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing
Add-Type -ReferencedAssemblies System.Windows.Forms,System.Drawing -TypeDefinition @'
using System;
using System.Runtime.InteropServices;
using System.Windows.Forms;
public delegate IntPtr AetherisOfnHook(IntPtr hWnd, int msg, IntPtr wparam, IntPtr lparam);
[StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)]
public struct AetherisOfn {
    public int lStructSize;
    public IntPtr hwndOwner;
    public IntPtr hInstance;
    public string lpstrFilter;
    public string lpstrCustomFilter;
    public int nMaxCustFilter;
    public int nFilterIndex;
    public IntPtr lpstrFile;
    public int nMaxFile;
    public IntPtr lpstrFileTitle;
    public int nMaxFileTitle;
    public string lpstrInitialDir;
    public string lpstrTitle;
    public int Flags;
    public short nFileOffset;
    public short nFileExtension;
    public string lpstrDefExt;
    public IntPtr lCustData;
    public AetherisOfnHook lpfnHook;
    public string lpTemplateName;
    public IntPtr pvReserved;
    public int dwReserved;
    public int FlagsEx;
}
public class AetherisPlacedChooser {
    public bool AutoUpgradeEnabled;
    public string Title;
    public string Filter;
    public string InitialDirectory;
    public string FileName;
    public bool Place;
    public int PlaceX;
    public int PlaceY;
    public int PlaceW;
    public int PlaceH;
    public bool HaveOut;
    public int OutX;
    public int OutY;
    public int OutW;
    public int OutH;
    AetherisOfnHook hook;
    [StructLayout(LayoutKind.Sequential)]
    public struct RECT {
        public int Left;
        public int Top;
        public int Right;
        public int Bottom;
    }
    [DllImport("user32.dll")]
    static extern bool MoveWindow(IntPtr hWnd, int X, int Y, int nWidth, int nHeight, bool bRepaint);
    [DllImport("user32.dll")]
    static extern bool GetWindowRect(IntPtr hWnd, out RECT lpRect);
    [DllImport("user32.dll")]
    static extern IntPtr GetParent(IntPtr hWnd);
    [DllImport("comdlg32.dll", CharSet = CharSet.Unicode, EntryPoint = "GetOpenFileNameW")]
    static extern bool GetOpenFileName(ref AetherisOfn ofn);
    [DllImport("comdlg32.dll")]
    static extern int CommDlgExtendedError();
    static string NativeFilter(string filter) {
        if (string.IsNullOrEmpty(filter)) return null;
        string native = filter.Replace('|', '\0') + "\0";
        return native;
    }
    IntPtr OnHook(IntPtr hWnd, int msg, IntPtr wparam, IntPtr lparam) {
        const int WM_INITDIALOG = 0x0110;
        const int WM_NOTIFY = 0x004E;
        const int WM_DESTROY = 0x0002;
        const int CDN_INITDONE = -601;
        if (Place && (msg == WM_INITDIALOG || msg == WM_NOTIFY)) {
            bool placeNow = msg == WM_INITDIALOG;
            if (msg == WM_NOTIFY && lparam != IntPtr.Zero) {
                int code = Marshal.ReadInt32(lparam, IntPtr.Size == 8 ? 16 : 8);
                placeNow = code == CDN_INITDONE;
            }
            if (placeNow) {
                IntPtr parent = GetParent(hWnd);
                IntPtr target = parent != IntPtr.Zero ? parent : hWnd;
                MoveWindow(target, PlaceX, PlaceY, PlaceW, PlaceH, true);
            }
        }
        if (msg == WM_DESTROY) {
            IntPtr parent = GetParent(hWnd);
            IntPtr target = parent != IntPtr.Zero ? parent : hWnd;
            RECT rect;
            if (GetWindowRect(target, out rect)) {
                OutX = rect.Left;
                OutY = rect.Top;
                OutW = rect.Right - rect.Left;
                OutH = rect.Bottom - rect.Top;
                HaveOut = OutW > 0 && OutH > 0;
            }
        }
        return IntPtr.Zero;
    }
    public DialogResult ShowDialog() {
        hook = new AetherisOfnHook(OnHook);
        int chars = 32768;
        IntPtr buffer = Marshal.AllocHGlobal(chars * 2);
        try {
            byte[] zeros = new byte[chars * 2];
            Marshal.Copy(zeros, 0, buffer, zeros.Length);
            AetherisOfn ofn = new AetherisOfn();
            ofn.lStructSize = Marshal.SizeOf(typeof(AetherisOfn));
            ofn.lpstrFilter = NativeFilter(Filter);
            ofn.nFilterIndex = 1;
            ofn.lpstrFile = buffer;
            ofn.nMaxFile = chars;
            if (!string.IsNullOrEmpty(InitialDirectory)) ofn.lpstrInitialDir = InitialDirectory;
            if (!string.IsNullOrEmpty(Title)) ofn.lpstrTitle = Title;
            ofn.Flags = 0x00000004 | 0x00000008 | 0x00000800 | 0x00001000 | 0x00080000 | 0x00000020 | 0x00800000;
            ofn.lpfnHook = hook;
            bool ok = GetOpenFileName(ref ofn);
            if (!ok) {
                int err = CommDlgExtendedError();
                if (err != 0) throw new InvalidOperationException("chooser failed " + err);
                FileName = "";
                return DialogResult.Cancel;
            }
            FileName = Marshal.PtrToStringUni(buffer);
            return DialogResult.OK;
        } finally {
            Marshal.FreeHGlobal(buffer);
        }
    }
}
'@
[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false
$d = New-Object AetherisPlacedChooser
$d.AutoUpgradeEnabled = $false
$d.Title = 'Import'
$d.Filter = 'Text files (*.txt)|*.txt|All files (*.*)|*.*'
$dir = [Environment]::GetEnvironmentVariable('AETHERIS_CHOOSER_DIR')
if ($dir -and (Test-Path -LiteralPath $dir -PathType Container)) { $d.InitialDirectory = $dir }
$raw = [Environment]::GetEnvironmentVariable('AETHERIS_CHOOSER_BOUNDS')
if ($raw) {
    $parts = $raw.Split(',')
    if ($parts.Length -eq 4) {
        $x = 0; $y = 0; $w = 0; $h = 0
        $okX = [int]::TryParse($parts[0].Trim(), [ref]$x)
        $okY = [int]::TryParse($parts[1].Trim(), [ref]$y)
        $okW = [int]::TryParse($parts[2].Trim(), [ref]$w)
        $okH = [int]::TryParse($parts[3].Trim(), [ref]$h)
        if ($okX -and $okY -and $okW -and $okH -and $w -ge 240 -and $h -ge 180) {
            $box = New-Object System.Drawing.Rectangle $x, $y, $w, $h
            $hit = $false
            foreach ($screen in [System.Windows.Forms.Screen]::AllScreens) {
                if ($screen.Bounds.IntersectsWith($box)) { $hit = $true; break }
            }
            if ($hit) {
                $d.Place = $true
                $d.PlaceX = $x
                $d.PlaceY = $y
                $d.PlaceW = $w
                $d.PlaceH = $h
            }
        }
    }
}
$approved = $d.ShowDialog() -eq [System.Windows.Forms.DialogResult]::OK
if ($d.HaveOut) { [Console]::Out.WriteLine(('BOUNDS {0} {1} {2} {3}' -f $d.OutX, $d.OutY, $d.OutW, $d.OutH)) }
if ($approved -and $d.FileName) { [Console]::Out.WriteLine(('FILE {0}' -f $d.FileName)) }
"#;
```

Cell import chooser. The folder and the bounds ride in the environment.
No path from the person is spliced into this script. `OpenFileDialog` is
sealed, so this placer calls `GetOpenFileName` and keeps the classic hook.
The floor in this text is [`CHOOSER_MIN_W`](#constant-chooser_min_w) by [`CHOOSER_MIN_H`](#constant-chooser_min_h).

### MARK_I

<a id="constant-mark_i"></a>

```rust
const MARK_I: &str = "I";
```

`MARK_I` is the face of the Included letter button. The card bar shows that button. A press sets the card to Included.

### MARK_E

<a id="constant-mark_e"></a>

```rust
const MARK_E: &str = "E";
```

`MARK_E` is the face of the Excluded letter button. The card bar shows that button. A press sets the card to Excluded.

### MARK_P

<a id="constant-mark_p"></a>

```rust
const MARK_P: &str = "P";
```

`MARK_P` is the face of the Input letter button. The card bar shows that button. A press sets the card to Input.

### MARK_O

<a id="constant-mark_o"></a>

```rust
const MARK_O: &str = "O";
```

`MARK_O` is the face of the Output letter button. The card bar shows that button. A press sets the card to Output.

### IDLE_STOP

<a id="constant-idle_stop"></a>

```rust
const IDLE_STOP: &str = "Nothing is running.";
```

`IDLE_STOP` is the status text when Stop is used and nothing is running. The status line shows this text. No cancel is sent in that case.

### HALT_STOP

<a id="constant-halt_stop"></a>

```rust
const HALT_STOP: &str = "Stopped.";
```

`HALT_STOP` is the status text after Stop cancels a running turn. The status line shows this text. Other turns in a group can still run.

### EXCLUDED_RUN

<a id="constant-excluded_run"></a>

```rust
const EXCLUDED_RUN: &str = "Excluded.";
```

`EXCLUDED_RUN` is the status text when Run is refused because the card is Excluded. The status line shows this text. A group send with nothing to send uses the same text.

### TOO_LARGE

<a id="constant-too_large"></a>

```rust
const TOO_LARGE: &str = "too-large";
```

`TOO_LARGE` is the status text when Run is refused because the prompt is too large. The status line shows this text. No send goes out in that case.

### INSPECTOR_LABEL

<a id="constant-inspector_label"></a>

```rust
const INSPECTOR_LABEL: &str = "Inspector";
```

`INSPECTOR_LABEL` is the heading of the inspector column. That column sits beside the cards. The heading is this word.

### EST_TOKENS

<a id="constant-est_tokens"></a>

```rust
const EST_TOKENS: &str = "est. tokens: ";
```

`EST_TOKENS` is the prefix before an estimated token count. The count is written after this prefix. The estimate is not a send.

### TOKEN_OPEN_TIP

<a id="constant-token_open_tip"></a>

```rust
const TOKEN_OPEN_TIP: &str = "T opens the token window. The ring compares this estimate to the selected model's context window. The estimate stays. This open does not ask the host and does not send.";
```

`TOKEN_OPEN_TIP` is the hover text on the T button in the inspector. T opens the token window. The ring compares this estimate to the selected model's context window.
The estimate stays. This open does not ask the connected machine. This open does not send.

### TOKEN_CALC

<a id="constant-token_calc"></a>

```rust
const TOKEN_CALC: &str = "Token Calc";
```

`TOKEN_CALC` is the label on the Token Calc button. The menu row shows that button. The hover text on that button is [`TOKEN_CALC_TIP`](#constant-token_calc_tip).

### TOKEN_CALC_TIP

<a id="constant-token_calc_tip"></a>

```rust
const TOKEN_CALC_TIP: &str = "Token Calc opens the token window. The ring compares this estimate to the selected model's context window. The estimate stays. This open does not ask the host and does not send.";
```

`TOKEN_CALC_TIP` is the hover text on the Token Calc button. Token Calc opens the token window. The ring compares this estimate to the selected model's context window.
The estimate stays. This open does not ask the connected machine. This open does not send.

### TOOLS_MENU

<a id="constant-tools_menu"></a>

```rust
const TOOLS_MENU: &str = "Tools";
```

`TOOLS_MENU` is the label on the Tools menu. The menu bar shows that menu. The hover text on that menu is [`TOOLS_MENU_TIP`](#constant-tools_menu_tip).

### SHOW_THINKING

<a id="constant-show_thinking"></a>

```rust
const SHOW_THINKING: &str = "Show Thinking";
```

`SHOW_THINKING` is the label on the Show Thinking item. The Tools menu shows that item. The hover text on that item is [`SHOW_THINKING_TIP`](#constant-show_thinking_tip).

### TOKEN_COMBO_TIP

<a id="constant-token_combo_tip"></a>

```rust
const TOKEN_COMBO_TIP: &str = "The estimate stays. The ring uses this model's context window. Ollama rows share the session num-ctx. Grok and pool windows come from application.yaml. A grok row shows the completion cap. That cap is the reply limit. This open does not ask the host.";
```

`TOKEN_COMBO_TIP` is the hover text on the model combo in the token window. The estimate stays. The ring uses this model's context window.
Ollama rows share the session num-ctx. Num-ctx is the context-size setting for that session. Grok and pool windows come from application.yaml.
A grok row shows the completion cap. That cap is the reply limit. This open does not ask the connected machine.

### TOKEN_OK_TIP

<a id="constant-token_ok_tip"></a>

```rust
const TOKEN_OK_TIP: &str = "Close the token window.";
```

`TOKEN_OK_TIP` is the hover text on the OK button in the token window. OK closes the token window. The estimate is left as it was.

### RING_TRACK

<a id="constant-ring_track"></a>

```rust
const RING_TRACK: egui::Color32 = egui::Color32::from_rgb(0x2a, 0x34, 0x40);
```

`RING_TRACK` is the color of the token ring track. The color is a dark blue-gray. An unknown fill and a fill that is not positive also use this color.

### RING_AMBER

<a id="constant-ring_amber"></a>

```rust
const RING_AMBER: egui::Color32 = egui::Color32::from_rgb(0xf0, 0xa2, 0x02);
```

`RING_AMBER` is the amber color of the token ring. The ring uses this color when the fill is at least 0.7 and under 0.9. A fuller ring uses [`RING_RED`](#constant-ring_red).

### RING_RED

<a id="constant-ring_red"></a>

```rust
const RING_RED: egui::Color32 = egui::Color32::from_rgb(0xe5, 0x39, 0x35);
```

`RING_RED` is the red color of the token ring. The ring uses this color when the fill is at least 0.9. A smaller positive fill uses another color.

### RING_HEAD

<a id="constant-ring_head"></a>

```rust
const RING_HEAD: egui::Color32 = egui::Color32::from_rgb(0xdc, 0xdc, 0xdc);
```

`RING_HEAD` is the color of the upper line in the token ring. The color is light gray. That line sits above the center of the ring.

### RING_FOOT

<a id="constant-ring_foot"></a>

```rust
const RING_FOOT: egui::Color32 = egui::Color32::from_rgb(0x9a, 0xa4, 0xae);
```

`RING_FOOT` is the color of the lower line in the token ring. The color is a muted gray. That line sits below the center of the ring.

### RING_BG

<a id="constant-ring_bg"></a>

```rust
const RING_BG: egui::Color32 = egui::Color32::from_rgb(0x12, 0x12, 0x12);
```

`RING_BG` is the background color behind the token ring. The color is near black. The rectangle is filled with square corners.

### BLOCK_RULE

<a id="constant-block_rule"></a>

```rust
const BLOCK_RULE: &str = "------------";
```

`BLOCK_RULE` is the rule drawn between inspector blocks. A line of dashes is that rule. The first block has no rule before it.

### GROUP_LABEL

<a id="constant-group_label"></a>

```rust
const GROUP_LABEL: &str = "Group";
```

`GROUP_LABEL` is the label on the Group button. The group-tool row shows that button on a card that is not in a group. The hover text on that button is [`GROUP_TIP`](#constant-group_tip).

### JOIN_LABEL

<a id="constant-join_label"></a>

```rust
const JOIN_LABEL: &str = "Join";
```

`JOIN_LABEL` is the label on the Join button. The group-tool row shows that button beside Group. The hover text on that button is [`JOIN_TIP`](#constant-join_tip).

### UNGROUP_LABEL

<a id="constant-ungroup_label"></a>

```rust
const UNGROUP_LABEL: &str = "Ungroup";
```

`UNGROUP_LABEL` is the label on the Ungroup button. The group-tool row shows that button on a card that is already in a group. The hover text on that button is [`UNGROUP_TIP`](#constant-ungroup_tip).

### ADD_CELL_LABEL

<a id="constant-add_cell_label"></a>

```rust
const ADD_CELL_LABEL: &str = "Add Cell";
```

`ADD_CELL_LABEL` is the label on the Add Cell button. A merge card shows that button. The hover text on that button is [`ADD_CELL_TIP`](#constant-add_cell_tip).

### ADD_CELL_TIP

<a id="constant-add_cell_tip"></a>

```rust
const ADD_CELL_TIP: &str = "Add Cell adds a new turn to this merge group. The new turn is the next member. Group, Join, Ungroup, and New stay.";
```

`ADD_CELL_TIP` is the hover text on the Add Cell button. Add Cell adds a new turn to this merge group. The new turn is the next member.
Group stays. Join stays. Ungroup stays. New stays.

### BAR_SHUT

<a id="constant-bar_shut"></a>

```rust
const BAR_SHUT: &str = "v";
```

`BAR_SHUT` is the face of the group-tool mark while that row is hidden. The mark is a v. The hover text in that state is [`BAR_SHOW_TIP`](#constant-bar_show_tip).

### BAR_OPEN

<a id="constant-bar_open"></a>

```rust
const BAR_OPEN: &str = "^";
```

`BAR_OPEN` is the face of the group-tool mark while that row is showing. The mark is a caret. The hover text in that state is [`BAR_HIDE_TIP`](#constant-bar_hide_tip).

### BAR_SHOW_TIP

<a id="constant-bar_show_tip"></a>

```rust
const BAR_SHOW_TIP: &str = "Show group tools. Group, Join, and Ungroup sit on the row under this bar. The row starts hidden. The choice is not saved.";
```

`BAR_SHOW_TIP` is the hover text on the group-tool mark while that row is hidden. The mark shows the group tools. Group, Join, and Ungroup sit on the row under this bar.
The row starts hidden. The window does not store that choice.

### BAR_HIDE_TIP

<a id="constant-bar_hide_tip"></a>

```rust
const BAR_HIDE_TIP: &str = "Hide group tools. Group, Join, and Ungroup fold away. The choice is not saved.";
```

`BAR_HIDE_TIP` is the hover text on the group-tool mark while that row is showing. The mark hides the group tools. Group, Join, and Ungroup fold away.
The window does not store that choice.

### TOOL_SHUT

<a id="constant-tool_shut"></a>

```rust
const TOOL_SHUT: &str = "v";
```

`TOOL_SHUT` is the face of the tool-bar mark while that bar is hidden. The mark is a v. The hover text in that state is [`TOOL_SHOW_TIP`](#constant-tool_show_tip).

### TOOL_OPEN

<a id="constant-tool_open"></a>

```rust
const TOOL_OPEN: &str = "^";
```

`TOOL_OPEN` is the face of the tool-bar mark while that bar is showing. The mark is a caret. The hover text in that state is [`TOOL_HIDE_TIP`](#constant-tool_hide_tip).

### TOOL_SHOW_TIP

<a id="constant-tool_show_tip"></a>

```rust
const TOOL_SHOW_TIP: &str = "Show the tool bar. Search, Export, Import, Pull, To terminal, the mood switch, and Mood sit on the row under this bar. Hide Collapsed and Show Collapsed sit after Mood. The rows sit at the bottom of the canvas. The bar wears a rounded square border. The bar starts hidden. The choice is not saved.";
```

`TOOL_SHOW_TIP` is the hover text on the tool-bar mark while that bar is hidden. The mark shows the tool bar. Search, Export, Import, Pull, To terminal, the mood switch, and Mood sit on the row under this bar.
Hide Collapsed and Show Collapsed sit after Mood. The rows sit at the bottom of the canvas. The bar wears a rounded square border.
The bar starts hidden. The window does not store that choice.

### TOOL_HIDE_TIP

<a id="constant-tool_hide_tip"></a>

```rust
const TOOL_HIDE_TIP: &str = "Hide the tool bar. Search, Export, Import, Pull, To terminal, the mood switch, Mood, Hide Collapsed, and Show Collapsed fold away. The rows sit at the bottom of the canvas. The bar wears a rounded square border. The choice is not saved.";
```

`TOOL_HIDE_TIP` is the hover text on the tool-bar mark while that bar is showing. The mark hides the tool bar. Search, Export, Import, Pull, To terminal, the mood switch, Mood, Hide Collapsed, and Show Collapsed fold away.
The rows sit at the bottom of the canvas. The bar wears a rounded square border. The window does not store that choice.

### CONTROL_FOLD_TIP

<a id="constant-control_fold_tip"></a>

```rust
const CONTROL_FOLD_TIP: &str = "Fold the connection bar. The fields leave the window. The canvas uses that height. ^ brings the bar back. Connect folds the bar. A resize while connected folds it. Disconnect opens it. The choice is not saved.";
```

`CONTROL_FOLD_TIP` is the hover text on the connection-bar mark while that bar is open. The mark folds the connection bar. The fields leave the window. The canvas uses that height.
A caret brings the bar back. Connect folds the bar. A resize while connected folds the bar. Disconnect opens the bar.
The window does not store that choice.

### CONTROL_SHOW_TIP

<a id="constant-control_show_tip"></a>

```rust
const CONTROL_SHOW_TIP: &str = "Show the connection bar. Host, port, user, and password sit on the first row. Connect and Disconnect sit on the row below. Connect folds the bar. A resize while connected folds it. Disconnect opens it. The choice is not saved.";
```

`CONTROL_SHOW_TIP` is the hover text on the connection-bar mark while that bar is folded. The mark shows the connection bar. The host field, the port field, the user field, and the password field sit on the first row.
Connect and Disconnect sit on the row below. Connect folds the bar. A resize while connected folds the bar. Disconnect opens the bar.
The window does not store that choice.

### HOST_TIP

<a id="constant-host_tip"></a>

```rust
const HOST_TIP: &str = "Host the SSH session dials.";
```

`HOST_TIP` is the hover text on the host field of the connection bar. The field names the machine the SSH session dials. The label beside the field uses the same hover text.

| | |
| --- | --- |
| **Specification** | F-157 |

### PORT_TIP

<a id="constant-port_tip"></a>

```rust
const PORT_TIP: &str = "Port the SSH session dials.";
```

`PORT_TIP` is the hover text on the port field of the connection bar. The field is the port the SSH session dials. The label beside the field uses the same hover text.

### USER_FIELD_TIP

<a id="constant-user_field_tip"></a>

```rust
const USER_FIELD_TIP: &str = "User the SSH session logs in as. The list remembers accounts.";
```

`USER_FIELD_TIP` is the hover text on the user field of the connection bar. The field is the account the SSH session logs in as. The list remembers accounts.

### PASSWORD_TIP

<a id="constant-password_tip"></a>

```rust
const PASSWORD_TIP: &str = "Password for that user.";
```

`PASSWORD_TIP` is the hover text on the password field of the connection bar. The field holds the password for that user. The label beside the field uses the same hover text.

### REMEMBER_TIP

<a id="constant-remember_tip"></a>

```rust
const REMEMBER_TIP: &str = "Remember password keeps the password for this user.";
```

`REMEMBER_TIP` is the hover text on the Remember password checkbox. That checkbox sits on the connection bar. A checked box keeps the password for this user.

### FONT_TIP

<a id="constant-font_tip"></a>

```rust
const FONT_TIP: &str = "Font size for the panes and this bar. The range is 8 to 36.";
```

`FONT_TIP` is the hover text on the font-size label and on the size number. Both sit on the connection bar. The size applies to the panes and to this bar.
The range is 8 to 36.

### FONT_MINUS_TIP

<a id="constant-font_minus_tip"></a>

```rust
const FONT_MINUS_TIP: &str = "Smaller font. The panes and this bar take the new size.";
```

`FONT_MINUS_TIP` is the hover text on the smaller-font button. That button sits beside the font size on the connection bar. The panes take the new size. This bar takes the new size.

### FONT_PLUS_TIP

<a id="constant-font_plus_tip"></a>

```rust
const FONT_PLUS_TIP: &str = "Larger font. The panes and this bar take the new size.";
```

`FONT_PLUS_TIP` is the hover text on the larger-font button. That button sits beside the font size on the connection bar. The panes take the new size. This bar takes the new size.

### ACCOUNTS_TIP

<a id="constant-accounts_tip"></a>

```rust
const ACCOUNTS_TIP: &str = "Pick a remembered account. The user and the password fill in.";
```

`ACCOUNTS_TIP` is the hover text on the accounts combo of the connection bar. You pick a remembered account. The user field fills in. The password field fills in.

### CONNECT_TIP

<a id="constant-connect_tip"></a>

```rust
const CONNECT_TIP: &str = "Connect opens the SSH session with the host, port, user, and password on this bar.";
```

`CONNECT_TIP` is the hover text on the Connect button. That button sits on the connection bar. Connect opens the SSH session with the host, the port, the user, and the password on this bar.

### DISCONNECT_TIP

<a id="constant-disconnect_tip"></a>

```rust
const DISCONNECT_TIP: &str = "Disconnect closes the SSH session. The bar opens.";
```

`DISCONNECT_TIP` is the hover text on the Disconnect button. That button sits on the connection bar. Disconnect closes the SSH session. The bar opens.

### RECONNECT_TIP

<a id="constant-reconnect_tip"></a>

```rust
const RECONNECT_TIP: &str = "Reconnect closes the session and opens it again with the fields on this bar.";
```

`RECONNECT_TIP` is the hover text on the Reconnect button. That button sits on the connection bar. Reconnect closes the session. Reconnect opens the session again with the fields on this bar.

### CLEAR_TERM_TIP

<a id="constant-clear_term_tip"></a>

```rust
const CLEAR_TERM_TIP: &str = "Clear Terminal wipes the focused pane.";
```

`CLEAR_TERM_TIP` is the hover text on the Clear Terminal button. That button sits on the connection bar. Clear Terminal wipes the focused pane.

### SEND_FILE_TIP

<a id="constant-send_file_tip"></a>

```rust
const SEND_FILE_TIP: &str = "Load File reads a file and opens it in the paste editor. OK inserts the text.";
```

`SEND_FILE_TIP` is the hover text on the Load File button. That button sits on the connection bar. Load File reads a file and opens it in the paste editor.
OK inserts the text.

### NEW_TERM_TIP

<a id="constant-new_term_tip"></a>

```rust
const NEW_TERM_TIP: &str = "New Term splits the focused pane. Horizontal or vertical.";
```

`NEW_TERM_TIP` is the hover text on the New Term button. That button sits on the connection bar. New Term splits the focused pane. The split is horizontal or vertical.

### CLOSE_PANE_TIP

<a id="constant-close_pane_tip"></a>

```rust
const CLOSE_PANE_TIP: &str = "Close Pane closes the focused pane. The last pane stays.";
```

`CLOSE_PANE_TIP` is the hover text on the Close Pane button. That button sits on the connection bar. Close Pane closes the focused pane. The last pane stays.

### NEW_TIP

<a id="constant-new_tip"></a>

```rust
const NEW_TIP: &str = "New adds an empty card at the end of this canvas and puts the cursor in its prompt. The new turn keeps the prior turn's model. The new turn starts Included, so a later Run can send it.";
```

`NEW_TIP` is the hover text on the New button in the canvas footer. New adds an empty card at the end of this canvas. New puts the cursor in that card's prompt.
The new turn keeps the prior turn's model. The new turn starts Included. A later Run can send that turn.

### CLEAR_TIP

<a id="constant-clear_tip"></a>

```rust
const CLEAR_TIP: &str = "Clear deletes every turn on this canvas and leaves one empty turn in their place. The other canvases stay. This cannot be undone from the button.";
```

`CLEAR_TIP` is the hover text on the Clear button in the canvas footer. Clear deletes every turn on this canvas. Clear leaves one empty turn in their place.
The other canvases stay. The button cannot undo that delete.

### GROUP_TIP

<a id="constant-group_tip"></a>

```rust
const GROUP_TIP: &str = "Group starts a join on this turn. This turn becomes member 1 and a merge cell is added under it. Run sends each member on its own. The merge cell is not sent. When every member has completed, the merge cell holds the joined replies. A failed or cancelled member fails the merge and leaves that reply blank. Group does nothing if this turn is already in a group.";
```

`GROUP_TIP` is the hover text on the Group button. Group starts a join on this turn. This turn becomes member 1. A merge cell is added under it.
Run sends each member on its own. The merge cell is not sent. When every member has completed, the merge cell holds the joined replies.
A failed member fails the merge. A cancelled member fails the merge. That failure leaves the merge reply blank. Group does nothing if this turn is already in a group.

### JOIN_TIP

<a id="constant-join_tip"></a>

```rust
const JOIN_TIP: &str = "Join adds this turn to the group of the turn you focused before this one. Click a member first, then Join on the turn you want to add. The new member keeps its own execution target. Join does nothing until a member is armed.";
```

`JOIN_TIP` is the hover text on the Join button. Join adds this turn to the group of the turn you focused before this one. You click a member first. You then press Join on the turn you want to add.
The new member keeps its own execution target. The execution target is the place that turn runs. Join does nothing until a member is armed.

### UNGROUP_TIP

<a id="constant-ungroup_tip"></a>

```rust
const UNGROUP_TIP: &str = "Ungroup pulls this turn out of its group and returns it to the ordinary column. When the last member leaves, the merge cell returns to an ordinary turn too.";
```

`UNGROUP_TIP` is the hover text on the Ungroup button. Ungroup pulls this turn out of its group. Ungroup returns that turn to the column of cards that are not in a group.
When the last member leaves, the merge cell becomes a turn that is not a merge.

### FILE_MENU_TIP

<a id="constant-file_menu_tip"></a>

```rust
const FILE_MENU_TIP: &str = "File. New Term, Close Pane, Import, Settings, and Exit.";
```

`FILE_MENU_TIP` is the hover text on the File menu. The menu holds New Term. The menu holds Close Pane, Import, Settings, and Exit.

### FILE_NEW_TIP

<a id="constant-file_new_tip"></a>

```rust
const FILE_NEW_TIP: &str = "Split the focused pane. Horizontal or vertical.";
```

`FILE_NEW_TIP` is the hover text on New Term in the File menu. That item splits the focused pane. The split is horizontal or vertical.

### FILE_CLOSE_TIP

<a id="constant-file_close_tip"></a>

```rust
const FILE_CLOSE_TIP: &str = "Close the focused pane. The last pane stays.";
```

`FILE_CLOSE_TIP` is the hover text on Close Pane in the File menu. That item closes the focused pane. The last pane stays.

### FILE_IMPORT_TIP

<a id="constant-file_import_tip"></a>

```rust
const FILE_IMPORT_TIP: &str = "Read a file into the focused terminal.";
```

`FILE_IMPORT_TIP` is the hover text on Import in the File menu. That item reads a file into the focused terminal. The canvas import is a different control.

### FILE_SETTINGS_TIP

<a id="constant-file_settings_tip"></a>

```rust
const FILE_SETTINGS_TIP: &str = "Settings opens the global switches for this window.";
```

`FILE_SETTINGS_TIP` is the hover text on Settings in the File menu. Settings opens the global switches for this window. The Settings window holds those switches.

### FILE_EXIT_TIP

<a id="constant-file_exit_tip"></a>

```rust
const FILE_EXIT_TIP: &str = "Close Aetheris. A confirm comes first.";
```

`FILE_EXIT_TIP` is the hover text on Exit in the File menu. Exit closes the Aetheris window. A confirmation comes first.

### SETTINGS_TITLE

<a id="constant-settings_title"></a>

```rust
const SETTINGS_TITLE: &str = "Settings";
```

`SETTINGS_TITLE` is the title of the Settings window. The window shows that word in its title bar. The body under the title is [`SETTINGS_BODY`](#constant-settings_body).

### SETTINGS_BODY

<a id="constant-settings_body"></a>

```rust
const SETTINGS_BODY: &str = "Global switches. They are saved for the next open.";
```

`SETTINGS_BODY` is the line under the Settings title. The line names the controls as global switches. Those switches are saved for the next open.

### SETTINGS_BORDERS

<a id="constant-settings_borders"></a>

```rust
const SETTINGS_BORDERS: &str = "Painted text borders";
```

`SETTINGS_BORDERS` is the label on the painted-borders checkbox. The Settings window shows that checkbox. The hover text on that checkbox is [`SETTINGS_BORDERS_TIP`](#constant-settings_borders_tip).

### SETTINGS_BORDERS_TIP

<a id="constant-settings_borders_tip"></a>

```rust
const SETTINGS_BORDERS_TIP: &str =
"On paints the Java cell frame on the canvas text panes. Off keeps the quiet plate.";
```

`SETTINGS_BORDERS_TIP` is the hover text on the painted-borders checkbox. On paints the Java cell frame on the canvas text panes. Off leaves those panes without that frame.

### SETTINGS_DEBUG

<a id="constant-settings_debug"></a>

```rust
const SETTINGS_DEBUG: &str = "Debug Mood";
```

`SETTINGS_DEBUG` is the label on the Debug Mood checkbox. The Settings window shows that checkbox. The hover text on that checkbox is [`SETTINGS_DEBUG_TIP`](#constant-settings_debug_tip).

### SETTINGS_DEBUG_TIP

<a id="constant-settings_debug_tip"></a>

```rust
const SETTINGS_DEBUG_TIP: &str = "On paints the invitation immediately before the user line the pointer seats. Those lines carry no card number. Off leaves the inspector as it is. Painting does not move the pin.";
```

`SETTINGS_DEBUG_TIP` is the hover text on the Debug Mood checkbox. On paints the invitation immediately before the user line under the pointer. Those lines carry no card number.
Off leaves the inspector as it is. That painting does not move the pin.

### SETTINGS_TIPS

<a id="constant-settings_tips"></a>

```rust
const SETTINGS_TIPS: &str = "tooltips enabled";
```

`SETTINGS_TIPS` is the label on the tooltips checkbox. The Settings window shows that checkbox. The hover text on that checkbox is [`SETTINGS_TIPS_TIP`](#constant-settings_tips_tip).

### SETTINGS_TIPS_TIP

<a id="constant-settings_tips_tip"></a>

```rust
const SETTINGS_TIPS_TIP: &str = "Hover tips. On is how the window works now. Off shows none.";
```

`SETTINGS_TIPS_TIP` is the hover text on the tooltips checkbox. The checkbox turns hover tips on or off. On is how the window works now. Off shows no hover tips.

### SETTINGS_OK_TIP

<a id="constant-settings_ok_tip"></a>

```rust
const SETTINGS_OK_TIP: &str = "Close Settings. The switch stays.";
```

`SETTINGS_OK_TIP` is the hover text on the OK button in Settings. OK closes Settings. The switch stays as set.

### TOOLS_MENU_TIP

<a id="constant-tools_menu_tip"></a>

```rust
const TOOLS_MENU_TIP: &str = "Tools. Show Thinking reveals the plate in front.";
```

`TOOLS_MENU_TIP` is the hover text on the Tools menu. The menu holds Show Thinking. Show Thinking reveals the plate in front.

### SHOW_THINKING_TIP

<a id="constant-show_thinking_tip"></a>

```rust
const SHOW_THINKING_TIP: &str = "Show the thinking pane on the plate in front.";
```

`SHOW_THINKING_TIP` is the hover text on Show Thinking in the Tools menu. The item shows the thinking pane. That pane is on the plate in front.

### HELP_MENU_TIP

<a id="constant-help_menu_tip"></a>

```rust
const HELP_MENU_TIP: &str = "Help and About.";
```

`HELP_MENU_TIP` is the hover text on the Help menu. The menu holds Help. The menu holds About.

### HELP_ITEM_TIP

<a id="constant-help_item_tip"></a>

```rust
const HELP_ITEM_TIP: &str = "Open the help text.";
```

`HELP_ITEM_TIP` is the hover text on Help in the Help menu. That item opens the help text. The About item is separate.

### ABOUT_ITEM_TIP

<a id="constant-about_item_tip"></a>

```rust
const ABOUT_ITEM_TIP: &str = "Open About Aetheris.";
```

`ABOUT_ITEM_TIP` is the hover text on About in the Help menu. That item opens About Aetheris. The help text is a separate item.

### SSH_TAB_TIP

<a id="constant-ssh_tab_tip"></a>

```rust
const SSH_TAB_TIP: &str = "Show the SSH terminal.";
```

`SSH_TAB_TIP` is the hover text on the SSH tab. The tab shows the SSH terminal. The tab stays available.

### AETHERIS_TAB_TIP

<a id="constant-aetheris_tab_tip"></a>

```rust
const AETHERIS_TAB_TIP: &str = "Show the Aetheris canvas. The mux has to be up.";
```

`AETHERIS_TAB_TIP` is the hover text on the Aetheris tab. The tab shows the Aetheris canvas. The multiplexed channel has to be up before that tab can be chosen.

### PASTE_OK_TIP

<a id="constant-paste_ok_tip"></a>

```rust
const PASTE_OK_TIP: &str = "OK inserts this text. Ctrl+Enter does the same.";
```

`PASTE_OK_TIP` is the hover text on OK in the paste editor. OK inserts this text. Ctrl+Enter does the same insert.

### PASTE_CANCEL_TIP

<a id="constant-paste_cancel_tip"></a>

```rust
const PASTE_CANCEL_TIP: &str = "Cancel drops this text. Nothing is inserted.";
```

`PASTE_CANCEL_TIP` is the hover text on Cancel in the paste editor. Cancel drops this text. The editor inserts no characters.

### PASTE_BODY_TIP

<a id="constant-paste_body_tip"></a>

```rust
const PASTE_BODY_TIP: &str = "Edit the text before it is inserted. Enter adds a line.";
```

`PASTE_BODY_TIP` is the hover text on the text in the paste editor. You edit the text before it is inserted. Enter adds a line.

### NOTICE_OK_TIP

<a id="constant-notice_ok_tip"></a>

```rust
const NOTICE_OK_TIP: &str = "Close this notice.";
```

`NOTICE_OK_TIP` is the hover text on OK in a notice. OK closes this notice. The notice stays until that close.

### SPLIT_H_TIP

<a id="constant-split_h_tip"></a>

```rust
const SPLIT_H_TIP: &str = "Split the focused pane horizontally.";
```

`SPLIT_H_TIP` is the hover text on the Horizontal button.
The button sits in the window that asks how to split the focused pane.
The tip says a click splits that pane horizontally.
A click then splits the focused pane.

### SPLIT_V_TIP

<a id="constant-split_v_tip"></a>

```rust
const SPLIT_V_TIP: &str = "Split the focused pane vertically.";
```

`SPLIT_V_TIP` is the hover text on the Vertical button.
The button sits in the window that asks how to split the focused pane.
The tip says a click splits that pane vertically.
A click then splits the focused pane.

### SPLIT_CANCEL_TIP

<a id="constant-split_cancel_tip"></a>

```rust
const SPLIT_CANCEL_TIP: &str = "Cancel leaves the panes as they are.";
```

`SPLIT_CANCEL_TIP` is the hover text on the Cancel button in the split window.
The tip says Cancel leaves every pane as it is.
A click closes the window and does not split the focused pane.

### HELP_OK_TIP

<a id="constant-help_ok_tip"></a>

```rust
const HELP_OK_TIP: &str = "Close help.";
```

`HELP_OK_TIP` is the hover text on the OK button in the help window.
The tip says that button closes the help text.
A click clears the flag that keeps the help window open.

### ABOUT_OK_TIP

<a id="constant-about_ok_tip"></a>

```rust
const ABOUT_OK_TIP: &str = "Close About.";
```

`ABOUT_OK_TIP` is the hover text on the OK button in the About window.
The tip says that button closes About.
A click clears the flag that keeps the About window open.

### EXIT_YES_TIP

<a id="constant-exit_yes_tip"></a>

```rust
const EXIT_YES_TIP: &str = "Exit Aetheris.";
```

`EXIT_YES_TIP` is the hover text on the Yes button in the exit window.
The tip says Yes leaves Aetheris.
A click closes the ask and sets the flag that the exit was confirmed.

### EXIT_NO_TIP

<a id="constant-exit_no_tip"></a>

```rust
const EXIT_NO_TIP: &str = "Stay in Aetheris.";
```

`EXIT_NO_TIP` is the hover text on the No button in the exit window.
The tip says No keeps Aetheris open.
A click closes the ask and does not set the confirmed-exit flag.

### SEARCH_LABEL

<a id="constant-search_label"></a>

```rust
const SEARCH_LABEL: &str = "Search";
```

`SEARCH_LABEL` is the word on the Search button.
The button sits on the canvas tool row when that row is open.
A click, or Enter in the search field beside it, starts a search of this canvas.

### EXPORT_LABEL

<a id="constant-export_label"></a>

```rust
const EXPORT_LABEL: &str = "Export";
```

`EXPORT_LABEL` is the word on the Export button.
The button sits on the canvas tool row when that row is open.
A click starts an export of this canvas.

### EXPORT_TIP

<a id="constant-export_tip"></a>

```rust
const EXPORT_TIP: &str = "Export writes this canvas to a .aetheris file. You choose whether burner bodies are kept. Cancel writes nothing. A later import of that file makes new turns. It does not replace these.";
```

`EXPORT_TIP` is the hover text on the Export button in the canvas tool row.
The tip says Export writes this canvas to a file whose name ends in `.aetheris`.
The tip says you choose whether burner bodies are kept.
A burner turn is a turn marked ephemeral, and its body is the prompt and the reply.
The tip says Cancel writes nothing.
The tip says a later import of that file makes new turns.
The tip says that import does not replace the turns already on this canvas.

### EXPORT_ASK

<a id="constant-export_ask"></a>

```rust
const EXPORT_ASK: &str = "Keep burner bodies?";
```

`EXPORT_ASK` is the question about keeping burner bodies.
A burner turn is a turn marked ephemeral.
Keeping the bodies writes that turn's prompt and reply into the export file.
Dropping them writes those two fields empty and still writes the turn.
The export confirm script in this file shows this same question with Yes, No, and Cancel.

### EXPORT_WORD

<a id="constant-export_word"></a>

```rust
const EXPORT_WORD: &str = "exported";
```

`EXPORT_WORD` is the status text after an export file is written.
The write of the canvas text succeeded.
The status line is this word alone.

### EXPORT_FAILED

<a id="constant-export_failed"></a>

```rust
const EXPORT_FAILED: &str = "export failed: ";
```

`EXPORT_FAILED` is the status prefix when an export does not finish.
The failure text is appended after this prefix.
The status line is that combined text.

### EXPORT_ASK_CHOOSER

<a id="constant-export_ask_chooser"></a>

```rust
const EXPORT_ASK_CHOOSER: &str = "Add-Type -AssemblyName System.Windows.Forms; $r = [System.Windows.Forms.MessageBox]::Show('Keep burner bodies?', 'Export', 'YesNoCancel'); [Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false; [Console]::Out.WriteLine($r)";
```

Yes, No, Cancel. No path from the person is spliced into this script.

### EXPORT_SAVE_CHOOSER

<a id="constant-export_save_chooser"></a>

```rust
const EXPORT_SAVE_CHOOSER: &str = "Add-Type -AssemblyName System.Windows.Forms; $d = New-Object System.Windows.Forms.SaveFileDialog; $d.Title = 'Export'; $d.Filter = 'Aetheris files (*.aetheris)|*.aetheris|All files (*.*)|*.*'; $d.DefaultExt = 'aetheris'; if ($d.ShowDialog() -eq [System.Windows.Forms.DialogResult]::OK) { [Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false; [Console]::Out.WriteLine($d.FileName) }";
```

System save dialog. No path from the person is spliced into this script.

### CANVAS_IMPORT_LABEL

<a id="constant-canvas_import_label"></a>

```rust
const CANVAS_IMPORT_LABEL: &str = "Import";
```

`CANVAS_IMPORT_LABEL` is the word on the Import button.
The button sits on the canvas tool row when that row is open.
A click starts an import onto this canvas.

### CANVAS_IMPORT_TIP

<a id="constant-canvas_import_tip"></a>

```rust
const CANVAS_IMPORT_TIP: &str = "Import reads a .aetheris file and adds its turns to this canvas as copies. Each turn gets a new id. Group links and pipe pointers are rewritten to the copies. A file over 1 MiB is refused. The turns already here stay.";
```

`CANVAS_IMPORT_TIP` is the hover text on the Import button in the canvas tool row.
The tip says Import reads a file whose name ends in `.aetheris`.
The tip says the file's turns are added to this canvas as copies.
The tip says each copied turn gets a new id.
The tip says group links and pipe pointers are rewritten to those copies.
The tip says a file over 1 MiB is refused.
The tip says the turns already on this canvas stay.

### PULL_LABEL

<a id="constant-pull_label"></a>

```rust
const PULL_LABEL: &str = "Pull";
```

`PULL_LABEL` is the word on the Pull button.
The button sits on the canvas tool row when that row is open.
A click copies terminal text into a new turn.

### PULL_TIP

<a id="constant-pull_tip"></a>

```rust
const PULL_TIP: &str = "Pull copies the terminal into a new turn. A highlight is copied when one is selected. Otherwise the last non-empty screen line is copied. The new turn starts Excluded, so Run will not send it until you include it.";
```

`PULL_TIP` is the hover text on the Pull button in the canvas tool row.
The tip says Pull copies the terminal into a new turn.
The tip says a highlight is copied when one is selected.
The tip says the last non-empty screen line is copied when nothing is highlighted.
The tip says the new turn starts Excluded.
Excluded is the mark that keeps Run from sending the turn until you include it.

### PIN_LABEL

<a id="constant-pin_label"></a>

```rust
const PIN_LABEL: &str = "pin";
```

`PIN_LABEL` is the word on the pin checkbox.
The checkbox sits on the pin row.
Turning the box on lets you type the pin words.

### PIN_OK_LABEL

<a id="constant-pin_ok_label"></a>

```rust
const PIN_OK_LABEL: &str = "OK";
```

`PIN_OK_LABEL` is the word on the pin OK button.
The button sits on the pin row.
The button can be clicked only while the pin box is on.

### PIN_FOLD_LABEL

<a id="constant-pin_fold_label"></a>

```rust
const PIN_FOLD_LABEL: &str = "v";
```

`PIN_FOLD_LABEL` is the mark on the pin fold button.
The button sits at the start of the pin row.
A click asks the pin bar to fold.

### PIN_SHOW_LABEL

<a id="constant-pin_show_label"></a>

```rust
const PIN_SHOW_LABEL: &str = "P";
```

`PIN_SHOW_LABEL` is the letter on the folded pin mark.
The mark is painted on the divider while the pin bar is folded.
The same letter is measured when the mark's size is chosen.

### PIN_CLEAR_LABEL

<a id="constant-pin_clear_label"></a>

```rust
const PIN_CLEAR_LABEL: &str = "x";
```

`PIN_CLEAR_LABEL` is the mark on the pin clear control.
The control sits on the pin row after the word field.
A click clears the pin words when that control handles the click.

### PIN_BOX_TIP

<a id="constant-pin_box_tip"></a>

```rust
const PIN_BOX_TIP: &str = "Pin filters the canvas by the words in the field. Turn it on to type the words. The marks stay. Turn it off and the words stay. The cards come back.";
```

`PIN_BOX_TIP` is the hover text on the pin checkbox.
The tip says the pin filters the canvas by the words in the field.
The tip says you turn the box on to type those words.
The tip says the marks stay while you type.
The tip says turning the box off keeps the words.
The tip says the cards come back when the box is off.

### PIN_FIELD_TIP

<a id="constant-pin_field_tip"></a>

```rust
const PIN_FIELD_TIP: &str = "Pin words. A turn must contain every word. Enter does what OK does. The marks stay.";
```

`PIN_FIELD_TIP` is the hover text on the pin word field.
The tip says the field holds the pin words.
The tip says a turn must contain every word.
The tip says Enter does what the OK button does.
The tip says the marks stay.

### PIN_CLEAR_TIP

<a id="constant-pin_clear_tip"></a>

```rust
const PIN_CLEAR_TIP: &str = "Clear the pin words. The field goes empty. The box stays as it is. The cards come back.";
```

`PIN_CLEAR_TIP` is the hover text on the pin clear control.
The tip says the control clears the pin words.
The tip says the field goes empty.
The tip says the box stays as it is.
The tip says the cards come back.

### PIN_FOLD_TIP

<a id="constant-pin_fold_tip"></a>

```rust
const PIN_FOLD_TIP: &str = "Fold the pin bar. The bar leaves the canvas. The cards use the full height. The pin stays as the box left it. P brings the bar back. The choice is saved with the shell.";
```

`PIN_FOLD_TIP` is the hover text on the pin fold button.
The tip says the button folds the pin bar.
The tip says the bar leaves the canvas.
The tip says the cards then use the full height.
The tip says the pin stays as the box left it.
The tip says the P mark brings the bar back.
The tip says the choice is saved with the shell.

### PIN_SHOW_TIP

<a id="constant-pin_show_tip"></a>

```rust
const PIN_SHOW_TIP: &str = "Show the pin bar. P sits on the divider while the bar is folded. It moves with the divider. The pin stays as the box left it. The choice is saved with the shell. Green means the pin is on. Sapphire means the pin is ready and off.";
```

`PIN_SHOW_TIP` is the hover text on the folded pin mark.
The mark is the P on the divider, and the tip shows while the reply window is closed.
The tip says the mark shows the pin bar.
The tip says P sits on the divider while the bar is folded.
The tip says P moves with the divider.
The tip says the pin stays as the box left it.
The tip says the choice is saved with the shell.
The tip says green means the pin is on.
The tip says sapphire means the pin is ready and off.

### PIN_OK_TIP

<a id="constant-pin_ok_tip"></a>

```rust
const PIN_OK_TIP: &str = "OK applies the pin words. A turn that misses any word leaves the canvas. The marks stay. OK does not open Mood.";
```

Java's OK tip, with the mood sentence removed. `commitPin` writes the states and does not open Mood.

### PULL_WORD

<a id="constant-pull_word"></a>

```rust
const PULL_WORD: &str = "pulled excluded";
```

`PULL_WORD` is the status text after Pull adds a turn.
The new turn was copied from the terminal.
The status line is this text, and the new turn receives focus.

### PULL_EMPTY

<a id="constant-pull_empty"></a>

```rust
const PULL_EMPTY: &str = "no terminal line";
```

`PULL_EMPTY` is the status text when Pull adds nothing.
The source text was missing, or it was blank after trimming.
The status line is this text, and the canvas stays as it was.

### TERMINAL_LABEL

<a id="constant-terminal_label"></a>

```rust
const TERMINAL_LABEL: &str = "To terminal";
```

`TERMINAL_LABEL` is the word on the To terminal button.
The button sits on the canvas tool row when that row is open.
A click starts a write of the focused turn into the terminal session.

### TERMINAL_TIP

<a id="constant-terminal_tip"></a>

```rust
const TERMINAL_TIP: &str = "To terminal writes the focused turn into the terminal session. The reply is sent when the turn has one. Otherwise the prompt is sent. A burner turn is refused. You confirm before the bytes are written.";
```

`TERMINAL_TIP` is the hover text on the To terminal button.
The tip says the button writes the focused turn into the terminal session.
The tip says the reply is sent when the turn has one.
The tip says the prompt is sent when the turn has no reply.
The tip says a burner turn is refused.
A burner turn is a turn marked ephemeral.
The tip says you confirm before the bytes are written.

### TERMINAL_SENT

<a id="constant-terminal_sent"></a>

```rust
const TERMINAL_SENT: &str = "sent to terminal";
```

`TERMINAL_SENT` is the status text after the turn is written to the terminal.
The session accepted the bytes.
The status line is this text.

### TERMINAL_EPHEMERAL

<a id="constant-terminal_ephemeral"></a>

```rust
const TERMINAL_EPHEMERAL: &str = "ephemeral";
```

`TERMINAL_EPHEMERAL` is the status text when To terminal refuses a burner turn.
A burner turn is a turn marked ephemeral.
The status line is this word, and nothing is written to the terminal.

### TERMINAL_FAILED

<a id="constant-terminal_failed"></a>

```rust
const TERMINAL_FAILED: &str = "terminal write failed: ";
```

`TERMINAL_FAILED` is the status prefix when a terminal write does not finish.
The failure text is appended after this prefix.
The status line is that combined text, and the turn bytes are not written.

### TERMINAL_OVER

<a id="constant-terminal_over"></a>

```rust
const TERMINAL_OVER: &str = "Paste exceeds 65536 characters.";
```

`TERMINAL_OVER` is the status text when the terminal paste is too long.
The length is counted in UTF-16 units after carriage returns are normalized.
A body over 65536 units is refused before the confirm dialog opens.

### TERMINAL_ASK

<a id="constant-terminal_ask"></a>

```rust
const TERMINAL_ASK: &str = "Write this cell to the terminal?";
```

`TERMINAL_ASK` is the question shown before a turn is written to the terminal.
The confirm script in this file shows this question with OK and Cancel.
Cancel writes nothing.

### TERMINAL_PASTE_CAP

<a id="constant-terminal_paste_cap"></a>

```rust
const TERMINAL_PASTE_CAP: usize = 65536;
```

Java `String.length`. A body over this is refused before confirm.

### TERMINAL_PREVIEW_CAP

<a id="constant-terminal_preview_cap"></a>

```rust
const TERMINAL_PREVIEW_CAP: usize = 120;
```

Dialog cap. Swing's preview keeps this many UTF-16 units, then an ellipsis.

### TERMINAL_CONFIRM_CHOOSER

<a id="constant-terminal_confirm_chooser"></a>

```rust
const TERMINAL_CONFIRM_CHOOSER: &str = "Add-Type -AssemblyName System.Windows.Forms; $p = $env:AETHERIS_TERMINAL_PREVIEW; if ($null -eq $p) { $p = '' }; $m = \"Write this cell to the terminal?`n\" + $p; $r = [System.Windows.Forms.MessageBox]::Show($m, 'Terminal', 'OKCancel'); [Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false; [Console]::Out.WriteLine($r)";
```

OK or Cancel. The preview rides in the environment. No cell text is spliced into this script.

### IMPORT_VERSION

<a id="constant-import_version"></a>

```rust
const IMPORT_VERSION: &str = "import refused: version";
```

`IMPORT_VERSION` is the status text when a canvas import is refused for its version.
The file was read, and the copy step returned nothing.
The status line is this text, and the turns already on the canvas stay.

### CANVAS_IMPORT_CHOOSER

<a id="constant-canvas_import_chooser"></a>

```rust
const CANVAS_IMPORT_CHOOSER: &str = "Add-Type -AssemblyName System.Windows.Forms; $d = New-Object System.Windows.Forms.OpenFileDialog; $d.Title = 'Import'; $d.Filter = 'Aetheris files (*.aetheris)|*.aetheris|All files (*.*)|*.*'; if ($d.ShowDialog() -eq [System.Windows.Forms.DialogResult]::OK) { [Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false; [Console]::Out.WriteLine($d.FileName) }";
```

System open dialog. No path from the person is spliced into this script.

### SEARCH_FIELD_TIP

<a id="constant-search_field_tip"></a>

```rust
const SEARCH_FIELD_TIP: &str = "Text to find on this canvas. Search selects the first turn whose text contains this. A turn that misses stays off the canvas until the red x clears the filter. An empty field matches nothing. Percent and underscore are ordinary characters. Cell states stay.";
```

`SEARCH_FIELD_TIP` is the hover text on the search field in the canvas tool row.
The tip says the field is the text to find on this canvas.
The tip says Search selects the first turn whose text contains the field.
The tip says a turn that does not contain the field stays off the canvas until the red clear control drops the filter.
The tip says an empty field matches nothing.
The tip says a percent sign and an underscore are ordinary characters.
The tip says cell states stay.

### SEARCH_TIP

<a id="constant-search_tip"></a>

```rust
const SEARCH_TIP: &str = "Search this canvas for the text in the field. A hit selects the first matching turn and does not change its state. A turn that misses stays off the canvas until the red x clears the filter. An empty field matches nothing. Percent and underscore are ordinary characters.";
```

`SEARCH_TIP` is the hover text on the Search button in the canvas tool row.
The tip says the button searches this canvas for the text in the field.
The tip says a hit selects the first matching turn and does not change its state.
The tip says a turn that does not contain the field stays off the canvas until the red clear control drops the filter.
The tip says an empty field matches nothing.
The tip says a percent sign and an underscore are ordinary characters.

### SEARCH_CLEAR_TIP

<a id="constant-search_clear_tip"></a>

```rust
const SEARCH_CLEAR_TIP: &str = "Clear the search. The field goes empty. The find filter drops. Turns the filter had hidden come back. Cell states stay.";
```

`SEARCH_CLEAR_TIP` is the hover text on the search clear control.
The control is the red mark beside the search field.
The tip says the control clears the search.
The tip says the field goes empty.
The tip says the find filter drops.
The tip says turns the filter had hidden come back.
The tip says cell states stay.

### SEARCH_MISS

<a id="constant-search_miss"></a>

```rust
const SEARCH_MISS: &str = "no hits";
```

`SEARCH_MISS` is the status text when a canvas search finds nothing.
An empty field is one way to find nothing.
The status line is this text, and no turn is selected by the search.

### MERGE_LABEL

<a id="constant-merge_label"></a>

```rust
const MERGE_LABEL: &str = "Merge";
```

`MERGE_LABEL` is the word on a merge card's title.
A merge card is the column-zero card of a group.
A click on that title selects the card.

### JOIN_BANNER

<a id="constant-join_banner"></a>

```rust
const JOIN_BANNER: &str = "JOIN ollama";
```

Empty target shows as ollama. The banner is that word.

### MERGE_FAIL

<a id="constant-merge_fail"></a>

```rust
const MERGE_FAIL: &str = "FAILED";
```

`MERGE_FAIL` is the fault text written on a merge card when the join fails.
The merge card is the column-zero card of the group.
The reply on that card is cleared, and this word is stored as the fault.

### GROUP_MISS

<a id="constant-group_miss"></a>

```rust
const GROUP_MISS: &str = "no group selected";
```

Armed card has no group. Swing's yellow slide says this. The slide stays off.

### GROUP_COPIED

<a id="constant-group_copied"></a>

```rust
const GROUP_COPIED: &str = "duplicated group";
```

Swing's status after the copy lands.

### NOTICE_INK

<a id="constant-notice_ink"></a>

```rust
const NOTICE_INK: egui::Color32 = egui::Color32::from_rgb(0xf9, 0xa8, 0x25);
```

Yellow slide edge. The notice uses this ink.

### SLIDE_TITLE

<a id="constant-slide_title"></a>

```rust
const SLIDE_TITLE: &str = "Error";
```

`SLIDE_TITLE` is the title word on the error notice.
The notice is painted at the top of the Aetheris pane.
The word is colored with the notice edge, and its hover text is [`SLIDE_TIP`](#constant-slide_tip).

### SLIDE_CLOSE

<a id="constant-slide_close"></a>

```rust
const SLIDE_CLOSE: &str = "Close";
```

`SLIDE_CLOSE` is the word on the Close button of the error notice.
The button sits on the right of the notice title row.
A click closes the notice.

### SLIDE_EMPTY

<a id="constant-slide_empty"></a>

```rust
const SLIDE_EMPTY: &str = "execute failed";
```

Empty garnered text. Swing uses this sentence.

### SLIDE_FAILED

<a id="constant-slide_failed"></a>

```rust
const SLIDE_FAILED: &str = "failed";
```

Unstructured miss. The status word is this.

### CELL_BUSY

<a id="constant-cell_busy"></a>

```rust
const CELL_BUSY: &str = "cell-busy";
```

`CELL_BUSY` is the status word for a structured failure that names this reason.
The notice stays closed for that failure.
The status line is this word.

### SLIDE_ROWS

<a id="constant-slide_rows"></a>

```rust
const SLIDE_ROWS: usize = 3;
```

Body rows. Swing's text area is three rows.

### SLIDE_EDGE_PX

<a id="constant-slide_edge_px"></a>

```rust
const SLIDE_EDGE_PX: f32 = 2.0;
```

Border. Swing's line border is 2.

### SLIDE_RED_EDGE

<a id="constant-slide_red_edge"></a>

```rust
const SLIDE_RED_EDGE: egui::Color32 = egui::Color32::from_rgb(0xc6, 0x28, 0x28);
```

`SLIDE_RED_EDGE` is the border color of a hard error notice.
A hard notice is the notice `slide_skin` builds when the failure is marked hard.
The channels are red 198, green 40, and blue 40.

### SLIDE_RED_FILL

<a id="constant-slide_red_fill"></a>

```rust
const SLIDE_RED_FILL: egui::Color32 = egui::Color32::from_rgb(0x3a, 0x14, 0x14);
```

`SLIDE_RED_FILL` is the background color of a hard error notice.
A hard notice is the notice `slide_skin` builds when the failure is marked hard.
The channels are red 58, green 20, and blue 20.

### SLIDE_RED_INK

<a id="constant-slide_red_ink"></a>

```rust
const SLIDE_RED_INK: egui::Color32 = egui::Color32::from_rgb(0xf8, 0xe8, 0xe8);
```

`SLIDE_RED_INK` is the text color of a hard error notice.
A hard notice is the notice `slide_skin` builds when the failure is marked hard.
The same color paints the body of a hard shell notice window.
The channels are red 248, green 232, and blue 232.

### SLIDE_YELLOW_FILL

<a id="constant-slide_yellow_fill"></a>

```rust
const SLIDE_YELLOW_FILL: egui::Color32 = egui::Color32::from_rgb(0x3a, 0x30, 0x14);
```

`SLIDE_YELLOW_FILL` is the background color of an error notice that is not hard.
`slide_skin` uses this fill when the failure is not marked hard.
The channels are red 58, green 48, and blue 20.

### SLIDE_YELLOW_INK

<a id="constant-slide_yellow_ink"></a>

```rust
const SLIDE_YELLOW_INK: egui::Color32 = egui::Color32::from_rgb(0xf8, 0xf4, 0xe8);
```

`SLIDE_YELLOW_INK` is the text color of an error notice that is not hard.
`slide_skin` uses this ink when the failure is not marked hard.
The channels are red 248, green 244, and blue 232.

### SLIDE_TIP

<a id="constant-slide_tip"></a>

```rust
const SLIDE_TIP: &str =
"Error notice. It slides down from the top of the Aetheris pane. It stays until you close it.";
```

`SLIDE_TIP` is the hover text on the error-notice title.
The title is [`SLIDE_TITLE`](#constant-slide_title) at the top of the Aetheris pane.
The tip says the notice is an error notice.
The tip says it slides down from the top of the Aetheris pane.
The tip says it stays until you close it.

### SLIDE_BODY_TIP

<a id="constant-slide_body_tip"></a>

```rust
const SLIDE_BODY_TIP: &str =
"Error notice. It stays until you close it. The cell does not keep this text.";
```

`SLIDE_BODY_TIP` is the hover text on the body of the error notice.
The body is the read-only text under the title.
The tip says the body is an error notice.
The tip says the notice stays until you close it.
The tip says the cell does not keep this text.

### SLIDE_CLOSE_TIP

<a id="constant-slide_close_tip"></a>

```rust
const SLIDE_CLOSE_TIP: &str = "Close this error notice. It stays until you close it.";
```

`SLIDE_CLOSE_TIP` is the hover text on the Close button of the error notice.
The button word is [`SLIDE_CLOSE`](#constant-slide_close).
The tip says the button closes this error notice.
The tip says the notice stays until you close it.

### PIPE_BLOCK

<a id="constant-pipe_block"></a>

```rust
const PIPE_BLOCK: &str = "pipe";
```

Status when a pipe blocks the send. The slide stays off.

### PIPE_OPEN

<a id="constant-pipe_open"></a>

```rust
const PIPE_OPEN: &str = "{{cell[";
```

`PIPE_OPEN` is the opening marker of a cell-output token in turn text.
The token ends at [`PIPE_CLOSE`](#constant-pipe_close), and the text between the markers is a turn number.
Import rewriting maps that number onto the copied turn.
A send replaces the token with an earlier reply, or blocks when that reply cannot be used.
A token with no closer is left as written.

### PIPE_CLOSE

<a id="constant-pipe_close"></a>

```rust
const PIPE_CLOSE: &str = "].output}}";
```

`PIPE_CLOSE` is the closing marker of a cell-output token in turn text.
The token starts at [`PIPE_OPEN`](#constant-pipe_open), and the text between the markers is a turn number.
Import rewriting maps that number onto the copied turn.
A send replaces the token with an earlier reply, or blocks when that reply cannot be used.
A token with no closer is left as written.

### MEMBER_EAST

<a id="constant-member_east"></a>

```rust
const MEMBER_EAST: f32 = 10.0;
```

Pixels east of a member's top bar. The row scrolls sideways.

### EDGE_QUIET

<a id="constant-edge_quiet"></a>

```rust
const EDGE_QUIET: egui::Color32 = egui::Color32::from_rgb(0x3a, 0x3a, 0x3a);
```

Quiet card line. Swing uses `0x3a3a3a`.

### EDGE_GHOST

<a id="constant-edge_ghost"></a>

```rust
const EDGE_GHOST: egui::Color32 = egui::Color32::from_rgb(0x40, 0x40, 0x40);
```

Excluded card line. Swing uses `Color.DARK_GRAY`.

### EDGE_ARMED

<a id="constant-edge_armed"></a>

```rust
const EDGE_ARMED: egui::Color32 = egui::Color32::from_rgb(0x2e, 0x86, 0xff);
```

Armed card line. Swing uses selection sapphire.

### EDGE_MATCH

<a id="constant-edge_match"></a>

```rust
const EDGE_MATCH: egui::Color32 = egui::Color32::from_rgb(0x3c, 0xb3, 0x71);
```

Readable pin match. Swing uses `MEDIUM_GREEN`.

### LINK_CHIP_HI

<a id="constant-link_chip_hi"></a>

```rust
const LINK_CHIP_HI: egui::Color32 = egui::Color32::from_rgb(0x9a, 0xe6, 0xbc);
```

Raised edge of the folded Connected chip.

### LINK_CHIP_LO

<a id="constant-link_chip_lo"></a>

```rust
const LINK_CHIP_LO: egui::Color32 = egui::Color32::from_rgb(0x1b, 0x5c, 0x38);
```

Shadow edge of the folded Connected chip.

### EDGE_MISS

<a id="constant-edge_miss"></a>

```rust
const EDGE_MISS: egui::Color32 = egui::Color32::from_rgb(0x22, 0x22, 0x22);
```

Readable pin miss. Swing uses `0x222222`.

### PIN_CLEAR_FILL

<a id="constant-pin_clear_fill"></a>

```rust
const PIN_CLEAR_FILL: egui::Color32 = egui::Color32::from_rgb(0xE5, 0x39, 0x35);
```

Clear mark. Swing paints the x on `#E53935`.

### CARD_EDGE_PX

<a id="constant-card_edge_px"></a>

```rust
const CARD_EDGE_PX: f32 = 1.0;
```

`CARD_EDGE_PX` is the stroke width of a card edge, in pixels.
The canvas paints each card rectangle with a stroke of this width.
A caption flank that is still visible also reports this width as its stroke.

### THINK_FRAME_MS

<a id="constant-think_frame_ms"></a>

```rust
const THINK_FRAME_MS: u64 = 120;
```

Spinner step. Swing's ThinkSpinner waits 120 ms.

### THINK_WORD

<a id="constant-think_word"></a>

```rust
const THINK_WORD: &str = "thinking";
```

`THINK_WORD` is the word shown beside a thinking spinner.
A running card's spinner line is the glyph, a space, and this word.
The footer spinner paints this word next to the glyph and the clock.
A mood reply that is in flight uses the same word on its clock line and as its own label.

### THINK_GLYPH

<a id="constant-think_glyph"></a>

```rust
const THINK_GLYPH: egui::Color32 = egui::Color32::from_rgb(0x00, 0xFF, 0x66);
```

Glyph. Swing uses `#00FF66`.

### BUTTON_HOVER_INK

<a id="constant-button_hover_ink"></a>

```rust
const BUTTON_HOVER_INK: egui::Color32 = egui::Color32::from_rgb(0x00, 0xFF, 0x66);
```

Button word while the pointer is on the button. Swing uses `#00FF66`.

### MENU_INK

<a id="constant-menu_ink"></a>

```rust
const MENU_INK: egui::Color32 = egui::Color32::from_rgb(0x00, 0xff, 0x41);
```

Main menu word. Swing menu foreground is `#00ff41`.

### BUTTON_TIP_DELAY

<a id="constant-button_tip_delay"></a>

```rust
const BUTTON_TIP_DELAY: f32 = 0.55;
```

Tooltip wait. egui's default is half a second. This seat adds 50 ms.

### THINK_INK

<a id="constant-think_ink"></a>

```rust
const THINK_INK: egui::Color32 = egui::Color32::from_rgb(0x00, 0xD7, 0xFF);
```

Word. Swing uses `#00D7FF`.

### THINK_BODY

<a id="constant-think_body"></a>

```rust
const THINK_BODY: egui::Color32 = egui::Color32::from_rgb(115, 174, 255);
```

Thought body. Locked lighter sapphire. Bytes 115, 174, 255.

### THINK_FIELD

<a id="constant-think_field"></a>

```rust
const THINK_FIELD: egui::Color32 = egui::Color32::from_rgb(0x1a, 0x1a, 0x1a);
```

Thought pane. Swing `FIELD_BG`.

### THINK_KIND

<a id="constant-think_kind"></a>

```rust
const THINK_KIND: u8 = b't';
```

Kind byte for a thought. Swing paints only this kind on the log.

### THINK_TAG_CAP

<a id="constant-think_tag_cap"></a>

```rust
const THINK_TAG_CAP: usize = 80;
```

Swing `thinkTagWord` stops at 80 UTF-16 units.

### THINK_GRIP

<a id="constant-think_grip"></a>

```rust
const THINK_GRIP: f32 = 18.0;
```

Grip on the north edge. The Clear mark sits on the east of this row.

### FOLDED_LINE_LIFT

<a id="constant-folded_line_lift"></a>

```rust
const FOLDED_LINE_LIFT: f32 = 4.0;
```

Folded canvas control line. The bottoms sit this far above the outer top of BB1.

### BUTTON_CLEAR_PX

<a id="constant-button_clear_px"></a>

```rust
const BUTTON_CLEAR_PX: f32 = 2.0;
```

Free points around a button border. The clip and the next shape stay outside it.

| | |
| --- | --- |
| **Specification** | PK-1096 docs/specs/common/formal-plan.spec.md FINESSE:f-183 |

### THINK_GRIP_INK

<a id="constant-think_grip_ink"></a>

```rust
const THINK_GRIP_INK: egui::Color32 = egui::Color32::from_rgb(0x8a, 0x8a, 0x8a);
```

Grip line. Swing paints `0x8a8a8a`.

### THINK_MAX_FALLBACK

<a id="constant-think_max_fallback"></a>

```rust
const THINK_MAX_FALLBACK: f32 = 480.0;
```

Swing `thinkMaxHeight` when the panel has not been measured.

### THINK_MAX_KEEP

<a id="constant-think_max_keep"></a>

```rust
const THINK_MAX_KEEP: f32 = 160.0;
```

Swing keeps 160 pixels for the canvas and the footer.

### THINK_CLEAR_LABEL

<a id="constant-think_clear_label"></a>

```rust
const THINK_CLEAR_LABEL: &str = "C";
```

`THINK_CLEAR_LABEL` is the letter on the Clear button of a thinking strip.
The button sits on the grip row of the notebook strip and of the terminal strip.
A click clears that strip's thinking text.

### THINK_CLOSE_LABEL

<a id="constant-think_close_label"></a>

```rust
const THINK_CLOSE_LABEL: &str = "Close";
```

`THINK_CLOSE_LABEL` is the word on the Close button of a thinking strip.
The button sits on the notebook strip and on the terminal strip.
A click closes that strip.

### THINK_LOG_FILE

<a id="constant-think_log_file"></a>

```rust
const THINK_LOG_FILE: &str = "aetheris-thinking.txt";
```

`THINK_LOG_FILE` is the file name of the saved thinking log.
The file sits in the `.casserole` directory under the user home directory.
The notebook strip reads and writes the thinking text there.

### THINK_YAML_FILE

<a id="constant-think_yaml_file"></a>

```rust
const THINK_YAML_FILE: &str = "aetheris-think.yaml";
```

`THINK_YAML_FILE` is the file name of the notebook thinking settings.
The file sits in the `.casserole` directory under the user home directory.
The file keeps the closed mark, the pane height, and the other blocks that were already in the file.

### THINK_HEIGHT_MIN

<a id="constant-think_height_min"></a>

```rust
const THINK_HEIGHT_MIN: i32 = 48;
```

Java keeps a dragged height in this range. A number outside it is ignored.

### THINK_HEIGHT_MAX

<a id="constant-think_height_max"></a>

```rust
const THINK_HEIGHT_MAX: i32 = 2000;
```

`THINK_HEIGHT_MAX` is the greatest thinking-pane height that is kept, in pixels.
The least kept height is 48.
A drag that stops inside that range is stored for the next open.
A stored number below 48, or a stored number of 2001 or more, is ignored.

### THINK_FOLD_TIP

<a id="constant-think_fold_tip"></a>

```rust
const THINK_FOLD_TIP: &str = "Fold or restore the thinking text. Close hides the pane and keeps the log. The log stays between sessions. A closed pane stays closed when the app opens again. It is not stored on the turn.";
```

`THINK_FOLD_TIP` is the hover text on the fold button of the notebook thinking strip.
The tip says the button folds or restores the thinking text.
The tip says Close hides the pane and keeps the log.
The tip says the log stays between sessions.
The tip says a closed pane stays closed when the app opens again.
The tip says the thinking text is not stored on the turn.

### THINK_CLOSE_TIP

<a id="constant-think_close_tip"></a>

```rust
const THINK_CLOSE_TIP: &str = "Close the thinking pane. The log stays. A closed pane stays closed when the app opens again. Clear wipes the pane and the saved log.";
```

`THINK_CLOSE_TIP` is the hover text on the Close button of the notebook thinking strip.
The tip says the button closes the thinking pane.
The tip says the log stays.
The tip says a closed pane stays closed when the app opens again.
The tip says Clear wipes the pane and the saved log.

### THINK_CLEAR_TIP

<a id="constant-think_clear_tip"></a>

```rust
const THINK_CLEAR_TIP: &str = "Clear the thinking log. The saved log clears too. The next thought accumulates from here.";
```

`THINK_CLEAR_TIP` is the hover text on the Clear button of the notebook thinking strip.
The button letter is [`THINK_CLEAR_LABEL`](#constant-think_clear_label).
The tip says the button clears the thinking log.
The tip says the saved log clears too.
The tip says the next thought accumulates from here.

### THINK_GRIP_TIP

<a id="constant-think_grip_tip"></a>

```rust
const THINK_GRIP_TIP: &str = "Drag the north edge to set the height of the thinking pane. A height from 48 through 2000 is kept for the next open.";
```

`THINK_GRIP_TIP` is the hover text on the drag row of the notebook thinking strip.
The tip says you drag the north edge to set the height of the thinking pane.
The tip says a height from 48 through 2000 is kept for the next open.

### VT_THINK_YAML_FILE

<a id="constant-vt_think_yaml_file"></a>

```rust
const VT_THINK_YAML_FILE: &str = "aetheris-vt100-think.yaml";
```

`VT_THINK_YAML_FILE` is the file name of the terminal thinking-pane settings.
The file sits in the `.casserole` directory under the user home directory.
The file is separate from the notebook file `aetheris-think.yaml`.
The file keeps the closed mark and the pane height, and it does not keep the thinking text.

### VT_FOLD_TIP

<a id="constant-vt_fold_tip"></a>

```rust
const VT_FOLD_TIP: &str = "Fold or restore the thinking text. Close hides the pane and keeps the height. A closed pane stays closed when the app opens again. The text is not written to the vault log.";
```

`VT_FOLD_TIP` is the hover text on the fold button of the terminal thinking strip.
The tip says the button folds or restores the thinking text.
The tip says Close hides the pane and keeps the height.
The tip says a closed pane stays closed when the app opens again.
The tip says the text is not written to the vault log.

### VT_CLOSE_TIP

<a id="constant-vt_close_tip"></a>

```rust
const VT_CLOSE_TIP: &str = "Close the thinking pane. The height stays. A closed pane stays closed when the app opens again. The text is not written to the vault log.";
```

`VT_CLOSE_TIP` is the hover text on the Close button of the terminal thinking strip.
The tip says the button closes the thinking pane.
The tip says the height stays.
The tip says a closed pane stays closed when the app opens again.
The tip says the text is not written to the vault log.

### VT_CLEAR_TIP

<a id="constant-vt_clear_tip"></a>

```rust
const VT_CLEAR_TIP: &str = "Clear the thinking text. The next thought accumulates from here. It is not written to the vault log.";
```

`VT_CLEAR_TIP` is the hover text on the Clear button of the terminal thinking strip.
The button letter is [`THINK_CLEAR_LABEL`](#constant-think_clear_label).
The tip says the button clears the thinking text.
The tip says the next thought accumulates from here.
The tip says the text is not written to the vault log.

### VT_GRIP_TIP

<a id="constant-vt_grip_tip"></a>

```rust
const VT_GRIP_TIP: &str = "Drag the south edge to set the height of the thinking pane. A height from 48 through 2000 is kept for the next open.";
```

`VT_GRIP_TIP` is the hover text on the drag row of the terminal thinking strip.
The tip says you drag the south edge to set the height of the thinking pane.
The tip says a height from 48 through 2000 is kept for the next open.

### VT_BELOW_CLEAR

<a id="constant-vt_below_clear"></a>

```rust
const VT_BELOW_CLEAR: f32 = 3.0;
```

The SSH console starts this far under the Clear button while the strip is open.

### MOOD_YAML_FILE

<a id="constant-mood_yaml_file"></a>

```rust
const MOOD_YAML_FILE: &str = "aetheris-mood.yaml";
```

`MOOD_YAML_FILE` is the file name of the shared mood record.
The file sits in the `.casserole` directory under the user home directory.
A file named for one user is a different file.

### HOST_SYSTEM

<a id="constant-host_system"></a>

```rust
const HOST_SYSTEM: &str = "You are the Casserole assistant. Be concise and useful.";
```

Vestige host sentence. Tests lock that a new working set leaves it off the wire.

### MOOD_ACTIVATE

<a id="constant-mood_activate"></a>

```rust
const MOOD_ACTIVATE: &str = crate::mood::LEGACY;
```

Legacy activate sentence. A new send leaves this row off.

### MOOD_OPENER

<a id="constant-mood_opener"></a>

```rust
const MOOD_OPENER: &str = crate::mood::OPENER;
```

The sentence before the constructed mood. Canvas and the glass use this line.

### MOOD_CLOSER

<a id="constant-mood_closer"></a>

```rust
const MOOD_CLOSER: &str = crate::mood::CLOSER;
```

The sentence after the constructed mood. Canvas and the glass use this line.

### MOOD_TOO_LARGE

<a id="constant-mood_too_large"></a>

```rust
const MOOD_TOO_LARGE: &str = "keyword only. too-large";
```

`MOOD_TOO_LARGE` is the body of a yellow error notice when a mood send does not fit.
A mood fit that drops rows stores this body, and the notice is not hard.
A mood frame that cannot be built stores the same body and clears the in-flight send.

### MOOD_WIDTH_MIN

<a id="constant-mood_width_min"></a>

```rust
const MOOD_WIDTH_MIN: i32 = 480;
```

`MOOD_WIDTH_MIN` is the smallest mood-glass width that is kept, in pixels.
The value is 480.
A width below this value is refused, and the stored size stays as it was.
A file keeps a width only together with a height, and only inside the allowed pair.

### MOOD_WIDTH_MAX

<a id="constant-mood_width_max"></a>

```rust
const MOOD_WIDTH_MAX: i32 = 4000;
```

`MOOD_WIDTH_MAX` is the greatest mood-glass width that is kept, in pixels.
The value is 4000.
A width above this value is refused, and the stored size stays as it was.
A file keeps a width only together with a height, and only inside the allowed pair.

### MOOD_HEIGHT_MIN

<a id="constant-mood_height_min"></a>

```rust
const MOOD_HEIGHT_MIN: i32 = 320;
```

`MOOD_HEIGHT_MIN` is the smallest mood-glass height that is kept, in pixels.
The value is 320.
A height below this value is refused, and the stored size stays as it was.
A file keeps a height only together with a width, and only inside the allowed pair.

### MOOD_HEIGHT_MAX

<a id="constant-mood_height_max"></a>

```rust
const MOOD_HEIGHT_MAX: i32 = 4000;
```

`MOOD_HEIGHT_MAX` is the greatest mood-glass height that is kept, in pixels.
The value is 4000.
A height above this value is refused, and the stored size stays as it was.
A file keeps a height only together with a width, and only inside the allowed pair.

### MOOD_GREY

<a id="constant-mood_grey"></a>

```rust
const MOOD_GREY: egui::Color32 = egui::Color32::from_rgb(0x9E, 0x9E, 0x9E);
```

`MOOD_GREY` is the color of the round mood button when no mood string is saved.
The button then sits on the off side.
The channels are red 158, green 158, and blue 158.

### MOOD_OFF

<a id="constant-mood_off"></a>

```rust
const MOOD_OFF: egui::Color32 = egui::Color32::from_rgb(0xE5, 0x39, 0x35);
```

`MOOD_OFF` is the red of the round mood button while a saved string is switched off.
The button then sits on the off side.
The No button in the clear-mood ask uses the same red.
The channels are red 229, green 57, and blue 53.

### MOOD_ON_INK

<a id="constant-mood_on_ink"></a>

```rust
const MOOD_ON_INK: egui::Color32 = egui::Color32::from_rgb(0x00, 0xFF, 0x66);
```

`MOOD_ON_INK` is the green of the round mood button while the switch is on.
The button then sits on the on side.
The Yes button in the clear-mood ask uses the same green.
The channels are red 0, green 255, and blue 102.

### MOOD_YELLOW

<a id="constant-mood_yellow"></a>

```rust
const MOOD_YELLOW: egui::Color32 = egui::Color32::from_rgb(0xFF, 0xFF, 0x55);
```

`MOOD_YELLOW` is the ink of the mood string in the inspector.
The string is painted at the head of the inspector scroll while the switch is on.
The channels are red 255, green 255, and blue 85.

### MOOD_SWITCH_GREY_TIP

<a id="constant-mood_switch_grey_tip"></a>

```rust
const MOOD_SWITCH_GREY_TIP: &str = "The mood switch stays grey until a mood string is saved. A saved mood stays when the app closes. The round button is red while the switch is off and green while it is on.";
```

`MOOD_SWITCH_GREY_TIP` is the hover text on the round mood switch when no mood string is saved.
The switch sits on the canvas tool row.
The tip says the switch stays grey until a mood string is saved.
The tip says a saved mood stays when the app closes.
The tip says the round button is red while the switch is off and green while it is on.

### MOOD_SWITCH_ON_TIP

<a id="constant-mood_switch_on_tip"></a>

```rust
const MOOD_SWITCH_ON_TIP: &str = "A click turns the switch off and keeps the saved string. While the switch is on, a send carries a shape line after the mood string. The round button is green while the switch is on. The switch comes back on when the app opens.";
```

`MOOD_SWITCH_ON_TIP` is the hover text on the round mood switch while that switch is on.
The switch sits on the canvas tool row.
The tip says a click turns the switch off and keeps the saved string.
The tip says a send carries a shape line after the mood string while the switch is on.
The tip says the round button is green while the switch is on.
The tip says the switch comes back on when the app opens.

### MOOD_SWITCH_OFF_TIP

<a id="constant-mood_switch_off_tip"></a>

```rust
const MOOD_SWITCH_OFF_TIP: &str = "A click turns the switch on. A send then carries a shape line after the saved string. The round button is red while the switch is off. The switch comes back off when the app opens.";
```

`MOOD_SWITCH_OFF_TIP` is the hover text on the round mood switch while a saved string is off.
The switch sits on the canvas tool row.
The tip says a click turns the switch on.
The tip says a send then carries a shape line after the saved string.
The tip says the round button is red while the switch is off.
The tip says the switch comes back off when the app opens.

### MOOD_LABEL

<a id="constant-mood_label"></a>

```rust
const MOOD_LABEL: &str = "Mood";
```

`MOOD_LABEL` is the word on the Mood button.
The button sits on the canvas tool row when that row is open.
A click asks the mood glass to open for the selected card.

### MOOD_TITLE

<a id="constant-mood_title"></a>

```rust
const MOOD_TITLE: &str = "Mood";
```

`MOOD_TITLE` is the title word of the mood glass.
A blank short model name leaves the title as this word alone.
Any other short model name follows this word after a middle dot.

### MOOD_OPEN_W

<a id="constant-mood_open_w"></a>

```rust
const MOOD_OPEN_W: f32 = 760.0;
```

`MOOD_OPEN_W` is the mood-glass width used when a stored side is missing.
The value is 760 pixels.
A record that has both a stored width and a stored height does not use this width.

### MOOD_OPEN_H

<a id="constant-mood_open_h"></a>

```rust
const MOOD_OPEN_H: f32 = 520.0;
```

`MOOD_OPEN_H` is the mood-glass height used when a stored side is missing.
The value is 520 pixels.
A record that has both a stored width and a stored height does not use this height.

### MOOD_LINE_MISS

<a id="constant-mood_line_miss"></a>

```rust
const MOOD_LINE_MISS: &str = "keyword only. mood line";
```

`MOOD_LINE_MISS` is the notice body after three mood replies that contain no mood line.
Each earlier reply without a line asks again with the format reminder.
The third one opens this notice, the notice is not hard, and the glass stays open.

### MOOD_KEYWORD

<a id="constant-mood_keyword"></a>

```rust
const MOOD_KEYWORD: &str = "keyword only.";
```

`MOOD_KEYWORD` is the status text when a mood send does not leave the glass.
The glass closes, and the notice body is this text when no longer detail was given.
A mood-wire fault starts its notice with this text and then appends the reason.

### MOOD_NO_CELL

<a id="constant-mood_no_cell"></a>

```rust
const MOOD_NO_CELL: &str = "no cell";
```

`MOOD_NO_CELL` is the status text when the mood glass has no card to use.
The focused index is not a card.
The glass stays closed.

### MOOD_APPROVE

<a id="constant-mood_approve"></a>

```rust
const MOOD_APPROVE: &str = "Approve";
```

`MOOD_APPROVE` is the word on the Approve button in the mood glass.
The button sits on the glass footer.
A click saves the mood-string field.

### MOOD_CANCEL

<a id="constant-mood_cancel"></a>

```rust
const MOOD_CANCEL: &str = "Cancel";
```

`MOOD_CANCEL` is the word on the Cancel button in the mood glass.
The button sits on the glass footer.
A click drops this chat and closes the glass.

### MOOD_CLEAR

<a id="constant-mood_clear"></a>

```rust
const MOOD_CLEAR: &str = "Clear mood";
```

`MOOD_CLEAR` is the word on the Clear mood button in the mood glass.
The button sits on the glass footer.
A click opens the ask before the saved string is dropped.

### SSH_MOOD_BOX

<a id="constant-ssh_mood_box"></a>

```rust
const SSH_MOOD_BOX: &str = "SSH Mood Active";
```

`SSH_MOOD_BOX` is the label on the SSH mood checkbox in the mood glass.
The checkbox shows the saved choice of the user who has focus on the canvas.
A checked box is one of the two gates that give that user's SSH panes the mood.

### SSH_MOOD_STATUS

<a id="constant-ssh_mood_status"></a>

```rust
const SSH_MOOD_STATUS: &str = "mood active";
```

`SSH_MOOD_STATUS` is the status-strip text while the SSH mood gate is on.
An empty left-hand status becomes this text alone.
Any other left-hand status keeps its text, then two spaces, then this text.
The mood string itself stays off the strip.

### SHELL_MOOD_MARK

<a id="constant-shell_mood_mark"></a>

```rust
const SHELL_MOOD_MARK: &str = "shell_mood:\n";
```

`SHELL_MOOD_MARK` is the opening of the canvas-channel body that sets one SSH pane's mood.
The body is this opening, the trimmed mood line, and a final newline.
The terminal grid does not paint that channel.

### SHELL_MOOD_CLEAR

<a id="constant-shell_mood_clear"></a>

```rust
const SHELL_MOOD_CLEAR: &str = "shell_mood_clear:\n";
```

`SHELL_MOOD_CLEAR` is the canvas-channel body that clears one SSH pane's mood.
It is sent when the pane should hold no line and the pane already holds one.
A body that starts with this text stores no held line on that pane.

### SSH_MOOD_TIP

<a id="constant-ssh_mood_tip"></a>

```rust
const SSH_MOOD_TIP: &str = "When this box is checked and the mood switch is on, every SSH pane of the user who has focus on the canvas takes that mood. The box shows that user's saved choice. The console does not print the mood. The status strip says mood active. An unchecked box leaves that user's SSH sessions as they are. The choice is remembered for those panes.";
```

`SSH_MOOD_TIP` is the hover text on the SSH mood checkbox in the mood glass.
The tip says a checked box and an on mood switch give that mood to every SSH pane of the user who has focus on the canvas.
The tip says the box shows that user's saved choice.
The tip says the console does not print the mood.
The tip says the status strip says the mood is active.
The tip says an unchecked box leaves that user's SSH sessions as they are.
The tip says the choice is remembered for those panes.

### MOOD_YOU

<a id="constant-mood_you"></a>

```rust
const MOOD_YOU: egui::Color32 = egui::Color32::from_rgb(0x00, 0xC8, 0x53);
```

`MOOD_YOU` is the ink of a user line in the mood glass.
The prompt mark before the line field uses the same green.
The channels are red 0, green 200, and blue 83.

### MOOD_ASSIST

<a id="constant-mood_assist"></a>

```rust
const MOOD_ASSIST: egui::Color32 = egui::Color32::from_rgb(0x00, 0xD7, 0xFF);
```

`MOOD_ASSIST` is the ink of an assistant line in the mood glass.
A turn whose role is assistant is painted in this color.
The channels are red 0, green 215, and blue 255.

### MOOD_GLASS_BG

<a id="constant-mood_glass_bg"></a>

```rust
const MOOD_GLASS_BG: egui::Color32 = egui::Color32::from_rgb(0x0d, 0x0d, 0x0d);
```

`MOOD_GLASS_BG` is the background fill of the mood glass.
The glass rectangle is filled with this color.
The channels are red 13, green 13, and blue 13.

### MOOD_GLASS_INK

<a id="constant-mood_glass_ink"></a>

```rust
const MOOD_GLASS_INK: egui::Color32 = egui::Color32::from_rgb(0xdc, 0xdc, 0xdc);
```

`MOOD_GLASS_INK` is the ordinary text color inside the mood glass.
A turn that is neither the user nor the assistant uses this ink.
The line field uses this ink as well.
The channels are red 220, green 220, and blue 220.

### MOOD_GLASS_EDGE

<a id="constant-mood_glass_edge"></a>

```rust
const MOOD_GLASS_EDGE: egui::Color32 = egui::Color32::from_rgb(0x3a, 0x3a, 0x3a);
```

`MOOD_GLASS_EDGE` is the border color of the mood glass.
The border is a one-pixel stroke around the glass rectangle.
The channels are red 58, green 58, and blue 58.

### MOOD_SHAPE_SYSTEM

<a id="constant-mood_shape_system"></a>

```rust
const MOOD_SHAPE_SYSTEM: &str = "You fill a mood token string. The app stores that string. The app does not store the rest of the reply as the mood. End every reply with one final line. That line is the mood token string. The line starts with mood: and the tokens follow on that same line. Separate the tokens with commas. Each token is one short meaning: a style, a tone, a stance, or a symbol. No stars. No quotes. No code fence on that line. Before that line, say what you determined. When the user asks for more detail, a richer mood, more tokens, or an enriched mood, do both of these. Say the extra detail in the reply, in the words you determined. Put that same detail on the mood line as more tokens. The mood line must contain the new tokens. Do not leave the new detail only in the talk. If the user did not ask for more, a short line is enough. Short example: mood: spare, dry, lantern-light. Enriched example, when the user asked for more: mood: spare, dry, lantern-light, short sentences, no warmth, gravel voice, night watch. When the user asked for more, add tokens until the line holds the detail you named.";
```

`MOOD_SHAPE_SYSTEM` is the system text that opens a mood-shaping send.
The text asks for a mood token string, and the app stores that string.
The rest of the reply is not stored as the mood.
Every reply ends on one final line, and that line is the token string.
The line starts with the mood mark, and the tokens follow on that same line.
The tokens are separated by commas.
Each token is one short meaning, such as a style, a tone, a stance, or a symbol.
That line has no stars, no quotes, and no code fence.
The reply says what was determined before that line.
A request for more detail puts the extra words in the reply and the same detail on the mood line as more tokens.
A request that does not ask for more can keep the line short.

### MOOD_FORMAT_ASK

<a id="constant-mood_format_ask"></a>

```rust
const MOOD_FORMAT_ASK: &str = "Return the mood text on one final line. The line starts with mood: and the rest of that line is the mood token string. Separate the tokens with commas. No stars and no quotes on that line. If the user asked for more detail, that line carries more tokens for that detail. Do not leave the detail only in the talk. Short example: mood: spare, dry, lantern-light.";
```

`MOOD_FORMAT_ASK` is the reminder sent when a mood reply has no mood line.
The reminder is added as a user turn while the try count is still below three.
The reminder asks for the mood text on one final line.
The line starts with the mood mark, and the rest of that line is the token string.
The tokens are separated by commas, and that line has no stars and no quotes.
A request for more detail puts more tokens for that detail on the line.
The detail is not left only in the talk.

### MOOD_BUTTON_TIP

<a id="constant-mood_button_tip"></a>

```rust
const MOOD_BUTTON_TIP: &str = "Mood opens the glass for the selected card. The glass uses that card's model. Enter sends the line. Each send takes a new wire id. Approve saves the field and turns the switch on. Cancel keeps the saved string and the switch. A saved mood is already in the field. That send carries those tokens in the header. Clear mood asks first. Yes drops the string and starts empty. No leaves the mood.";
```

`MOOD_BUTTON_TIP` is the hover text on the Mood button in the canvas tool row.
The tip says Mood opens the glass for the selected card.
The tip says the glass uses that card's model.
The tip says Enter sends the line.
The tip says each send takes a new wire id, and a wire id is the identifier of that send.
The tip says Approve saves the field and turns the switch on.
The tip says Cancel keeps the saved string and the switch.
The tip says a saved mood is already in the field.
The tip says that send carries those tokens in the header.
The tip says Clear mood asks first.
The tip says Yes drops the string and starts empty.
The tip says No leaves the mood.

### MOOD_LINE_TIP

<a id="constant-mood_line_tip"></a>

```rust
const MOOD_LINE_TIP: &str = "Enter sends this line. An empty line is ignored. A send already in flight keeps the typed line.";
```

`MOOD_LINE_TIP` is the hover text on the line field in the mood glass.
The tip says Enter sends this line.
The tip says an empty line is ignored.
The tip says a send already in flight keeps the typed line.

### MOOD_DRAFT_TIP

<a id="constant-mood_draft_tip"></a>

```rust
const MOOD_DRAFT_TIP: &str = "The mood string rises above the footer when a reply sets it. A saved mood is already in the field when the dialog opens. The height follows the wrap. One line stays one line tall. You can edit it before Approve.";
```

`MOOD_DRAFT_TIP` is the hover text on the mood-string field in the mood glass.
The tip says the mood string rises above the footer when a reply sets it.
The tip says a saved mood is already in the field when the dialog opens.
The tip says the height follows the wrap.
The tip says one line stays one line tall.
The tip says you can edit the field before Approve.

### MOOD_APPROVE_TIP

<a id="constant-mood_approve_tip"></a>

```rust
const MOOD_APPROVE_TIP: &str = "Approve saves the mood-string field and places that string at the head. The chat is discarded. An empty field closes and writes nothing. The dialog keeps this size the next time it opens.";
```

`MOOD_APPROVE_TIP` is the hover text on the Approve button in the mood glass.
The tip says Approve saves the mood-string field and places that string at the head.
The tip says the chat is discarded.
The tip says an empty field closes and writes nothing.
The tip says the dialog keeps this size the next time it opens.

### MOOD_CANCEL_TIP

<a id="constant-mood_cancel_tip"></a>

```rust
const MOOD_CANCEL_TIP: &str = "Cancel drops this chat. The saved string and the switch stay. The dialog closes. The dialog keeps this size the next time it opens.";
```

`MOOD_CANCEL_TIP` is the hover text on the Cancel button in the mood glass.
The tip says Cancel drops this chat.
The tip says the saved string and the switch stay.
The tip says the dialog closes.
The tip says the dialog keeps this size the next time it opens.

### MOOD_CLEAR_TIP

<a id="constant-mood_clear_tip"></a>

```rust
const MOOD_CLEAR_TIP: &str = "Clear mood asks before it drops the string. Yes clears the saved string and turns the switch off. The field and this chat start empty. No leaves the mood untouched. The dialog stays open.";
```

`MOOD_CLEAR_TIP` is the hover text on the Clear mood button in the mood glass.
The tip says Clear mood asks before it drops the string.
The tip says Yes clears the saved string and turns the switch off.
The tip says the field and this chat start empty.
The tip says No leaves the mood untouched.
The tip says the dialog stays open.

### MOOD_ASK_TITLE

<a id="constant-mood_ask_title"></a>

```rust
const MOOD_ASK_TITLE: &str = "Clear mood";
```

`MOOD_ASK_TITLE` is the title of the window that asks before a mood is cleared.
The window opens from the Clear mood button.
Yes and No sit in that window.

### MOOD_ASK_WORDS

<a id="constant-mood_ask_words"></a>

```rust
const MOOD_ASK_WORDS: &str = "Clear the Mood Setting?";
```

`MOOD_ASK_WORDS` is the question in the clear-mood window.
The question is a label under the window title.
Yes and No answer that question.

### MOOD_YES

<a id="constant-mood_yes"></a>

```rust
const MOOD_YES: &str = "Yes";
```

`MOOD_YES` is the word on the Yes button in the clear-mood window.
The button is painted in the on-switch green.
A click clears the saved mood string.

### MOOD_NO

<a id="constant-mood_no"></a>

```rust
const MOOD_NO: &str = "No";
```

`MOOD_NO` is the word on the No button in the clear-mood window.
The button is painted in the off-switch red.
A click leaves the saved mood as it is.

### MOOD_YES_TIP

<a id="constant-mood_yes_tip"></a>

```rust
const MOOD_YES_TIP: &str = "Yes clears the saved string and turns the switch off. The field and this chat start empty. The dialog stays open.";
```

`MOOD_YES_TIP` is the hover text on the Yes button in the clear-mood window.
The tip says Yes clears the saved string and turns the switch off.
The tip says the field and this chat start empty.
The tip says the dialog stays open.

### MOOD_NO_TIP

<a id="constant-mood_no_tip"></a>

```rust
const MOOD_NO_TIP: &str = "No leaves the mood untouched. The ask closes. The mood dialog stays open.";
```

`MOOD_NO_TIP` is the hover text on the No button in the clear-mood window.
The tip says No leaves the mood untouched.
The tip says the ask closes.
The tip says the mood dialog stays open.

### MOOD_SPIN_TIP

<a id="constant-mood_spin_tip"></a>

```rust
const MOOD_SPIN_TIP: &str = "The spinner runs while a mood reply is in flight. The clock counts from that send.";
```

`MOOD_SPIN_TIP` is the hover text on the spinner in the mood glass.
The spinner sits on the glass footer while a reply is in flight.
The tip says the spinner runs while a mood reply is in flight.
The tip says the clock counts from that send.

### SSH_MOOD_YAML_FILE

<a id="constant-ssh_mood_yaml_file"></a>

```rust
const SSH_MOOD_YAML_FILE: &str = "aetheris-ssh-mood.yaml";
```

`SSH_MOOD_YAML_FILE` is the file name of the SSH mood checkbox record.
The file sits in the `.casserole` directory under the user home directory.
The shared mood file `aetheris-mood.yaml` is a different file.

### MARK_CLASS

<a id="constant-mark_class"></a>

```rust
const MARK_CLASS: &str = "aetheris.mark";
```

The constant `MARK_CLASS` is the egui class on the I, E, P, and O marks.
The class name is `aetheris.mark`. `ButtonHoverInk` uses this class to paint those letters emerald while the pointer is on them.

### CANVAS_WEIGHT

<a id="constant-canvas_weight"></a>

```rust
const CANVAS_WEIGHT: f32 = 0.72;
```

Open canvas share. Swing `setResizeWeight(0.72)`. A drag moves the live share.

### SPLIT_BAR_PX

<a id="constant-split_bar_px"></a>

```rust
const SPLIT_BAR_PX: f32 = 11.0;
```

Grab bar. Swing `DIVIDER_SIZE` is 11.

### SPLIT_FILL

<a id="constant-split_fill"></a>

```rust
const SPLIT_FILL: egui::Color32 = egui::Color32::from_rgb(0x3a, 0x3a, 0x3a);
```

Bar fill. Swing `DarkDivider` uses `0x3a3a3a`.

### SPLIT_EDGE

<a id="constant-split_edge"></a>

```rust
const SPLIT_EDGE: egui::Color32 = egui::Color32::from_rgb(0x8a, 0x8a, 0x8a);
```

Bar line. Swing `DarkDivider` uses `0x8a8a8a`.

### DIVIDER_SAVE_MS

<a id="constant-divider_save_ms"></a>

```rust
const DIVIDER_SAVE_MS: u64 = 400;
```

Door write delay. Swing restarts a 400 ms timer on each move.

### DIVIDER_SPAN_MIN

<a id="constant-divider_span_min"></a>

```rust
const DIVIDER_SPAN_MIN: f32 = 32.0;
```

Swing skips the ratio while the span is under 32.

### ARROW_W

<a id="constant-arrow_w"></a>

```rust
const ARROW_W: f32 = SPLIT_BAR_PX;
```

One-touch button. The width is the bar, so the control stays on the divider.

### ARROW_H

<a id="constant-arrow_h"></a>

```rust
const ARROW_H: f32 = 14.0;
```

`ARROW_H` is the height of one divider arrow button, in pixels.
The value is 14.
The canvas button is the upper one, and the inspector button is the lower one.
The step between their centers is half of this height plus the gap between the buttons.

### ARROW_GAP

<a id="constant-arrow_gap"></a>

```rust
const ARROW_GAP: f32 = 4.0;
```

Gap between the two buttons. The pair stays centered on the bar.

### ARROW_ROUND

<a id="constant-arrow_round"></a>

```rust
const ARROW_ROUND: f32 = 2.0;
```

`ARROW_ROUND` is the corner radius of a divider arrow button, in pixels.
The value is 2.
The fill and the inside stroke both use this radius.

### ARROW_INSET

<a id="constant-arrow_inset"></a>

```rust
const ARROW_INSET: f32 = 2.0;
```

Triangle margin. Narrower than the old mark so the icon fits the bar.

### ARROW_FILL

<a id="constant-arrow_fill"></a>

```rust
const ARROW_FILL: egui::Color32 = egui::Color32::from_rgb(0x5c, 0x5c, 0x5c);
```

Off-gray, lighter than the bar, so the button is noticeable.

### ARROW_HOT

<a id="constant-arrow_hot"></a>

```rust
const ARROW_HOT: egui::Color32 = egui::Color32::from_rgb(0x6e, 0x6e, 0x6e);
```

`ARROW_HOT` is the fill of a divider arrow button while the pointer is on it.
A button that is not under the pointer uses a different fill.
The channels are red 110, green 110, and blue 110.

### ARROW_EDGE

<a id="constant-arrow_edge"></a>

```rust
const ARROW_EDGE: egui::Color32 = egui::Color32::from_rgb(0x9a, 0x9a, 0x9a);
```

`ARROW_EDGE` is the border color of a divider arrow button.
The border is a one-pixel stroke drawn inside the button.
The channels are red 154, green 154, and blue 154.

### ARROW_ICON

<a id="constant-arrow_icon"></a>

```rust
const ARROW_ICON: egui::Color32 = egui::Color32::from_rgb(0xdc, 0xdc, 0xdc);
```

`ARROW_ICON` is the color of the triangle on a divider arrow button.
The triangle is painted after the button fill, and it has no stroke.
The channels are red 220, green 220, and blue 220.

### WRAP_COLUMNS

<a id="constant-wrap_columns"></a>

```rust
const WRAP_COLUMNS: u32 = 80;
```

Missing shell column. Swing `getAetherisWrapColumns` uses 80.

### WRAP_TYPE_MS

<a id="constant-wrap_type_ms"></a>

```rust
const WRAP_TYPE_MS: u64 = 800;
```

Typed column count. Swing's Incrementor waits 800 ms.

### SOFT_WRAP_LABEL

<a id="constant-soft_wrap_label"></a>

```rust
const SOFT_WRAP_LABEL: &str = "soft wrap";
```

`SOFT_WRAP_LABEL` is the word on the soft-wrap checkbox.
The checkbox sits in the inspector column.
A click turns soft wrap on or off for that column.

### SOFT_WRAP_TIP

<a id="constant-soft_wrap_tip"></a>

```rust
const SOFT_WRAP_TIP: &str = "Soft wrap folds long inspector lines at the column count. Turn it off to keep each line on one row and scroll sideways. The choice applies to the inspector only.";
```

`SOFT_WRAP_TIP` is the hover text on the soft-wrap checkbox in the inspector column.
The tip says soft wrap folds long inspector lines at the column count.
The tip says turning it off keeps each line on one row and scrolls sideways.
The tip says the choice applies to the inspector only.

### WRAP_MINUS_TIP

<a id="constant-wrap_minus_tip"></a>

```rust
const WRAP_MINUS_TIP: &str = "Fewer soft-wrap columns. When soft wrap is on, the inspector folds a line after this many characters. The count is saved with the shell. The smallest count is 1.";
```

`WRAP_MINUS_TIP` is the hover text on the minus button beside the soft-wrap count.
The button sits in the inspector column.
The tip says the button uses fewer soft-wrap columns.
The tip says the inspector folds a line after this many characters when soft wrap is on.
The tip says the count is saved with the shell.
The tip says the smallest count is 1.

### WRAP_PLUS_TIP

<a id="constant-wrap_plus_tip"></a>

```rust
const WRAP_PLUS_TIP: &str = "More soft-wrap columns. When soft wrap is on, the inspector folds a line after this many characters. The count is saved with the shell.";
```

`WRAP_PLUS_TIP` is the hover text on the plus button beside the soft-wrap count.
The button sits in the inspector column.
The tip says the button uses more soft-wrap columns.
The tip says the inspector folds a line after this many characters when soft wrap is on.
The tip says the count is saved with the shell.

### WRAP_FIELD_TIP

<a id="constant-wrap_field_tip"></a>

```rust
const WRAP_FIELD_TIP: &str = "Soft-wrap column count. Type a number. It applies after a short pause. When soft wrap is on, the inspector folds a line after this many characters. The count is saved with the shell.";
```

`WRAP_FIELD_TIP` is the hover text on the soft-wrap count field.
The field sits between the minus button and the plus button in the inspector column.
The tip says the field is the soft-wrap column count.
The tip says a typed number applies after a short pause.
The tip says the inspector folds a line after this many characters when soft wrap is on.
The tip says the count is saved with the shell.

### LINE_CHARS

<a id="constant-line_chars"></a>

```rust
const LINE_CHARS: u32 = 80;
```

Missing inspector line length. Swing `getAetherisInspectorLineChars` uses 80.

### LINE_MINUS_TIP

<a id="constant-line_minus_tip"></a>

```rust
const LINE_MINUS_TIP: &str = "Fewer characters on each inspector line. Preview cuts each kept line to this count. The count is saved with the shell. The smallest count is 1.";
```

`LINE_MINUS_TIP` is the hover text on the minus button for the inspector line length.
The button sits after the soft-wrap controls.
The tip says the button uses fewer characters on each inspector line.
The tip says preview cuts each kept line to this count.
The tip says the count is saved with the shell.
The tip says the smallest count is 1.

### LINE_PLUS_TIP

<a id="constant-line_plus_tip"></a>

```rust
const LINE_PLUS_TIP: &str = "More characters on each inspector line. Preview cuts each kept line to this count. The count is saved with the shell.";
```

`LINE_PLUS_TIP` is the hover text on the plus button for the inspector line length.
The button sits after the line-length field.
The tip says the button uses more characters on each inspector line.
The tip says preview cuts each kept line to this count.
The tip says the count is saved with the shell.

### LINE_FIELD_TIP

<a id="constant-line_field_tip"></a>

```rust
const LINE_FIELD_TIP: &str = "Inspector line length. Type a number. It applies after a short pause. Preview cuts each kept line to this count. The count is saved with the shell.";
```

`LINE_FIELD_TIP` is the hover text on the inspector line-length field.
The field sits between that row's minus button and plus button.
The tip says the field is the inspector line length.
The tip says a typed number applies after a short pause.
The tip says preview cuts each kept line to this count.
The tip says the count is saved with the shell.

### SCOPE_PER_TURN

<a id="constant-scope_per_turn"></a>

```rust
const SCOPE_PER_TURN: usize = 8;
```

Scope A pieces applied on one turn of the window loop.

### PAINT_PER_TURN

<a id="constant-paint_per_turn"></a>

```rust
const PAINT_PER_TURN: usize = 1;
```

Terminal bursts fed on one turn. The rest stays queued.

### IDLE_POLL_MS

<a id="constant-idle_poll_ms"></a>

```rust
const IDLE_POLL_MS: u64 = 16;
```

Quiet session poll. A backlog uses the short poll.

### COMMIT_POLL_MS

<a id="constant-commit_poll_ms"></a>

```rust
const COMMIT_POLL_MS: u64 = 1;
```

`COMMIT_POLL_MS` is the short wait, in milliseconds, while a live session still has work queued.
The value is 1.
A session that is caught up waits longer.
A session that is not up does not use this wait.

### WINDOW_TITLE

<a id="constant-window_title"></a>

```rust
pub const WINDOW_TITLE: &str = "Aetheris";
```

Plate name. Never built from the password. The OS caption is [`WINDOW_CAPTION`](#constant-window_caption).

### WINDOW_CAPTION

<a id="constant-window_caption"></a>

```rust
pub const WINDOW_CAPTION: &str = "Aetheris Rust";
```

Taskbar hover and the window caption. Windows reads the title.

### WINDOW_ICON_PX

<a id="constant-window_icon_px"></a>

```rust
const WINDOW_ICON_PX: u32 = 64;
```

Title-bar and taskbar icon edge. The art is the Java Eye of Horus jpeg.

### INPUT_GREEN

<a id="constant-input_green"></a>

```rust
const INPUT_GREEN: egui::Color32 = egui::Color32::from_rgb(0x00, 0xff, 0x66);
```

Swing `INPUT_GREEN`. The prompt on a working card.

### OUTPUT_YELLOW

<a id="constant-output_yellow"></a>

```rust
const OUTPUT_YELLOW: egui::Color32 = egui::Color32::from_rgb(0xff, 0xff, 0x55);
```

Swing `OUTPUT_YELLOW`. A reply that has text.

### RESPONSE_GRAY

<a id="constant-response_gray"></a>

```rust
const RESPONSE_GRAY: egui::Color32 = egui::Color32::from_rgb(0x66, 0x66, 0x66);
```

Swing `RESPONSE_GRAY`. A reply whose text is blank.

### PANE_EDGE_PX

<a id="constant-pane_edge_px"></a>

```rust
const PANE_EDGE_PX: f32 = 2.0;
```

Java `PANE_EDGE_PX`. The optional canvas text frame.

### PANE_STROKE_ROUND

<a id="constant-pane_stroke_round"></a>

```rust
const PANE_STROKE_ROUND: f32 = 10.0;
```

Java `PANE_STROKE_ROUND`. The field corner plus the stroke.

### SINK_SYSTEM

<a id="constant-sink_system"></a>

```rust
const SINK_SYSTEM: u8 = 1;
```

`SINK_SYSTEM` is the role byte for a system row in the sink.
The value is 1.
The role name `system` maps to this byte.
Leading system rows are kept at the front, and a pack with none at the front keeps the last system row instead.

### SINK_USER

<a id="constant-sink_user"></a>

```rust
const SINK_USER: u8 = 2;
```

`SINK_USER` is the role byte for a user row in the sink.
The value is 2.
The role name `user` maps to this byte, and so does any role that is not system, assistant, tool, or tool call.
A user row with no assistant row after it is the live row.

### SINK_ASSISTANT

<a id="constant-sink_assistant"></a>

```rust
const SINK_ASSISTANT: u8 = 3;
```

`SINK_ASSISTANT` is the role byte for an assistant row in the sink.
The value is 3.
The role name `assistant` maps to this byte.
An assistant row stays with the previous user, tool, or tool-call row when those sequence numbers are consecutive.

### SINK_TOOL

<a id="constant-sink_tool"></a>

```rust
const SINK_TOOL: u8 = 4;
```

`SINK_TOOL` is the role byte for a tool row in the sink.
The value is 4.
The role name `tool` maps to this byte.
A tool row can be the row kept together with the assistant row that follows it.

### SINK_TOOL_CALL

<a id="constant-sink_tool_call"></a>

```rust
const SINK_TOOL_CALL: u8 = 5;
```

`SINK_TOOL_CALL` is the role byte for a tool-call row in the sink.
The value is 5.
The role name `tool_call` maps to this byte.
A tool-call row can be the row kept together with the assistant row that follows it.

### APPLE_THUMB_SHARE

<a id="constant-apple_thumb_share"></a>

```rust
const APPLE_THUMB_SHARE: f32 = 0.40;
```

The handle is this share of the pane. It does not follow the scroll range.

### INPUT_EDGE_PAD

<a id="constant-input_edge_pad"></a>

```rust
const INPUT_EDGE_PAD: f32 = 8.0;
```

Inset of the input-edge caption from each side of the pane.

### CELL_CLEAR_PX

<a id="constant-cell_clear_px"></a>

```rust
const CELL_CLEAR_PX: f32 = 4.0;
```

Air under the embedded line, and air between that line and the cell above.

### FOOTER_EDGE_PX

<a id="constant-footer_edge_px"></a>

```rust
const FOOTER_EDGE_PX: f32 = 2.0;
```

Stroke on the button bar. The same grey as the divider line.

### FOOTER_ROUND

<a id="constant-footer_round"></a>

```rust
const FOOTER_ROUND: f32 = 6.0;
```

Corner of the bar. A square with a short radius, not a pill.

### FOOTER_PAD

<a id="constant-footer_pad"></a>

```rust
const FOOTER_PAD: f32 = 6.0;
```

Space inside the stroke so the buttons do not touch it.

### FOOTER_OUT

<a id="constant-footer_out"></a>

```rust
const FOOTER_OUT: f32 = 4.0;
```

Space outside the stroke so the bar does not kiss the column edge.

### FOOTER_FILL

<a id="constant-footer_fill"></a>

```rust
const FOOTER_FILL: egui::Color32 = egui::Color32::from_rgb(0x1a, 0x1a, 0x1a);
```

Plate behind the buttons. A step above the canvas so the bar reads.

### HEAD_LOCK_OPEN

<a id="constant-head_lock_open"></a>

```rust
const HEAD_LOCK_OPEN: [&str; 16] = [
    "......####......",
    ".....#....#.....",
    ".....#..........",
    ".....#..........",
    "................",
    "................",
    "....########....",
    "...#........#...",
    "...#........#...",
    "...#...##...#...",
    "...#...##...#...",
    "...#........#...",
    "....########....",
    "................",
    "................",
    "................",
];
```

Open shackle. The right leg stops at the crown. A hash is ink. A dot is clear.

### HEAD_LOCK_LATCHED

<a id="constant-head_lock_latched"></a>

```rust
const HEAD_LOCK_LATCHED: [&str; 16] = [
    "................",
    "................",
    "......####......",
    ".....#....#.....",
    ".....#....#.....",
    ".....#....#.....",
    "....########....",
    "...#........#...",
    "...#........#...",
    "...#...##...#...",
    "...#...##...#...",
    "...#........#...",
    "....########....",
    "................",
    "................",
    "................",
];
```

Closed shackle. Both legs meet the body. A hash is ink. A dot is clear.

### HEAD_LOCK_TIP

<a id="constant-head_lock_tip"></a>

```rust
const HEAD_LOCK_TIP: &str = "The lock is separate from I, E, P, and O. A press keeps a packed prefix from the top. Excluding a locked turn drops that lock.";
```

The tip on the padlock. The lock is separate from I, E, P, and O.

### PIN_FIELD_RECT

<a id="constant-pin_field_rect"></a>

```rust
static PIN_FIELD_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`PIN_FIELD_RECT` is the test slot for the pin word field.
The slot is a thread-local cell, and it starts empty.
A pin-row paint stores the field rectangle.

### PIN_BOX_RECT

<a id="constant-pin_box_rect"></a>

```rust
static PIN_BOX_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`PIN_BOX_RECT` is the test slot for the pin checkbox.
The slot is a thread-local cell, and it starts empty.
A pin-row paint stores the checkbox rectangle.

### PIN_HIT_CARD

<a id="constant-pin_hit_card"></a>

```rust
static PIN_HIT_CARD: std::cell::Cell<Option<egui::Rect>> = const
```

`PIN_HIT_CARD` is the test slot for a card that covers the pin field.
The slot is a thread-local cell, and it starts empty.
A card whose rectangle contains the center of the pin field stores that card rectangle.

### PIN_SCROLL_CLIP

<a id="constant-pin_scroll_clip"></a>

```rust
static PIN_SCROLL_CLIP: std::cell::Cell<Option<egui::Rect>> = const
```

`PIN_SCROLL_CLIP` is the test slot for the canvas card scroll clip.
The slot is a thread-local cell, and it starts empty.
A canvas paint stores the clip rectangle of that scroll.

### PIN_FORCE_SCROLL

<a id="constant-pin_force_scroll"></a>

```rust
static PIN_FORCE_SCROLL: std::cell::Cell<bool> = const
```

`PIN_FORCE_SCROLL` is the test flag that jumps the canvas scroll to the bottom.
The flag is a thread-local cell, and it starts false.
A true flag, during a canvas paint, scrolls to the cursor with no animation.

### CANVAS_STRIP_RECT

<a id="constant-canvas_strip_rect"></a>

```rust
static CANVAS_STRIP_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`CANVAS_STRIP_RECT` is the test slot for the canvas thinking strip.
The slot is a thread-local cell, and it starts empty.
An open strip stores its rectangle, and a closed strip stores nothing.

### CANVAS_CARD_CLIP

<a id="constant-canvas_card_clip"></a>

```rust
static CANVAS_CARD_CLIP: std::cell::Cell<Option<egui::Rect>> = const
```

`CANVAS_CARD_CLIP` is the test slot for the first card's clip.
The slot is a thread-local cell, and it starts empty.
A paint of card zero stores the clip rectangle around that card.

### CANVAS_CARD_RECT

<a id="constant-canvas_card_rect"></a>

```rust
static CANVAS_CARD_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`CANVAS_CARD_RECT` is the test slot for the first card's rectangle.
The slot is a thread-local cell, and it starts empty.
A paint of card zero stores that card's rectangle.

### PIN_MARK_RECT

<a id="constant-pin_mark_rect"></a>

```rust
static PIN_MARK_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`PIN_MARK_RECT` is the test slot for the folded pin mark.
The slot is a thread-local cell, and it starts empty.
Painting P on the divider stores that mark's rectangle.

### PIN_ROW_RECT

<a id="constant-pin_row_rect"></a>

```rust
static PIN_ROW_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`PIN_ROW_RECT` is the test slot for the pin row.
The slot is a thread-local cell, and it starts empty.
A pin-row paint stores the row rectangle.

### THINK_GRIP_RECT

<a id="constant-think_grip_rect"></a>

```rust
static THINK_GRIP_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`THINK_GRIP_RECT` is the test slot for the thinking grip row.
The slot is a thread-local cell, and it starts empty.
An open grip stores its row, and a folded strip stores the folded row.

### THINK_GRIP_DRAG_RECT

<a id="constant-think_grip_drag_rect"></a>

```rust
static THINK_GRIP_DRAG_RECT: std::cell::Cell<Option<egui::Rect>> =
const
```

`THINK_GRIP_DRAG_RECT` is the test slot for the thinking drag region.
The slot is a thread-local cell, and it starts empty.
The region stops one pixel short of the Clear button.

### THINK_CLEAR_RECT

<a id="constant-think_clear_rect"></a>

```rust
static THINK_CLEAR_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`THINK_CLEAR_RECT` is the test slot for the thinking Clear button.
The slot is a thread-local cell, and it starts empty.
A grip paint stores the Clear button rectangle.

### THINK_FOLD_RECT

<a id="constant-think_fold_rect"></a>

```rust
static THINK_FOLD_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`THINK_FOLD_RECT` is the test slot for the folded thinking fold button.
The slot is a thread-local cell, and it starts empty.
A folded thinking row stores that button's rectangle.

### BB_FOLD_RECT

<a id="constant-bb_fold_rect"></a>

```rust
static BB_FOLD_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`BB_FOLD_RECT` is the test slot for the button-bar fold mark.
The slot is a thread-local cell, and it starts empty.
An open bar and a folded bar both store that mark's rectangle.

### BB_FOLD_LINE

<a id="constant-bb_fold_line"></a>

```rust
static BB_FOLD_LINE: std::cell::Cell<Option<egui::Rect>> = const
```

`BB_FOLD_LINE` is the test slot for the line beside a folded button bar.
The slot is a thread-local cell, and it starts empty.
A folded bar stores that line when the span is open, and an open bar stores nothing.

### BB_RUN_RECT

<a id="constant-bb_run_rect"></a>

```rust
static BB_RUN_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`BB_RUN_RECT` is the test slot for the Run button.
The slot is a thread-local cell, and it starts empty.
An open button bar stores the Run rectangle, and a folded bar stores nothing.

### THINK_CLOSE_RECT

<a id="constant-think_close_rect"></a>

```rust
static THINK_CLOSE_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`THINK_CLOSE_RECT` is the test slot for the Close button on a folded thinking row.
The slot is a thread-local cell, and it starts empty.
A folded thinking row stores that button's rectangle.

### CANVAS_TAB_RECT

<a id="constant-canvas_tab_rect"></a>

```rust
static CANVAS_TAB_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`CANVAS_TAB_RECT` is the test slot for the canvas tab.
The slot is a thread-local cell, and it starts empty.
A canvas paint stores the tab rectangle.

### CANVAS_NORTH_LAYER

<a id="constant-canvas_north_layer"></a>

```rust
static CANVAS_NORTH_LAYER: std::cell::Cell<Option<egui::LayerId>> =
const
```

`CANVAS_NORTH_LAYER` is the test slot for the canvas header layer.
The slot is a thread-local cell, and it starts empty.
A canvas paint stores the foreground layer used by the tab and by P.

### CONTROL_MARK_RECT

<a id="constant-control_mark_rect"></a>

```rust
static CONTROL_MARK_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`CONTROL_MARK_RECT` is the test slot for the connection-bar fold mark.
The slot is a thread-local cell, and it starts empty.
An open bar and a folded bar both store that mark's rectangle.

### USER_COMBO_RECT

<a id="constant-user_combo_rect"></a>

```rust
static USER_COMBO_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`USER_COMBO_RECT` is the test slot for the user combo.
The slot is a thread-local cell, and it starts empty.
The east capsule clears it, then stores the combo rectangle when the combo is painted.

### USER_SPIN_RECT

<a id="constant-user_spin_rect"></a>

```rust
static USER_SPIN_RECT: std::cell::Cell<Option<egui::Rect>> = const
```

`USER_SPIN_RECT` is the test slot for a user spinner rectangle.
The slot is a thread-local cell, and it starts empty.
The east capsule clears it, because that capsule does not paint the spinner.

### USER_COMBO_W

<a id="constant-user_combo_w"></a>

```rust
const USER_COMBO_W: f32 = 160.0;
```

Preferred width of the connected-user pull-down. Java sets 160.

### USER_COMBO_MAX

<a id="constant-user_combo_max"></a>

```rust
const USER_COMBO_MAX: f32 = 180.0;
```

Ceiling so a long name stays off the master buttons. Java sets 180.

### USER_COMBO_ID

<a id="constant-user_combo_id"></a>

```rust
const USER_COMBO_ID: &str = "aetheris-connected-user";
```

Distinct from the connection-strip accounts pull-down.

### BB_FOLD_TIP

<a id="constant-bb_fold_tip"></a>

```rust
const BB_FOLD_TIP: &str = "Fold the button bar. BB1 and BB2 leave the window. The canvas uses that height. The folded line keeps the unfold mark. The choice is not saved.";
```

Open mark. Click folds BB1 and BB2.

### BB_SHOW_TIP

<a id="constant-bb_show_tip"></a>

```rust
const BB_SHOW_TIP: &str = "Show the button bar. The fold mark sits at the left of BB1, then a divider, then Run. BB2 stays on its own mark. The choice is not saved.";
```

Folded mark. Click restores BB1 and BB2.

### SHELL_FONT_MIN

<a id="constant-shell_font_min"></a>

```rust
const SHELL_FONT_MIN: i32 = 8;
```

Swing spinner floor. `DEFAULT_FONT_SIZE` is the same number.

### SHELL_FONT_MAX

<a id="constant-shell_font_max"></a>

```rust
const SHELL_FONT_MAX: i32 = 36;
```

Swing spinner ceiling.

### FRAME_MIN_W

<a id="constant-frame_min_w"></a>

```rust
const FRAME_MIN_W: i32 = 400;
```

A restored frame under this width is not placed.

### FRAME_MIN_H

<a id="constant-frame_min_h"></a>

```rust
const FRAME_MIN_H: i32 = 300;
```

A restored frame under this height is not placed.

### CONSOLE_SAMPLE

<a id="constant-console_sample"></a>

```rust
const CONSOLE_SAMPLE: usize = 4096;
```

Console import and Send file sample this many leading bytes.

### SCOPE_PARK_CAP

<a id="constant-scope_park_cap"></a>

```rust
const SCOPE_PARK_CAP: usize = 256;
```

A background pane keeps this many scope pieces. The canvas applies them on focus.

### PANE_GAP

<a id="constant-pane_gap"></a>

```rust
const PANE_GAP: f32 = 6.0;
```

The gap between two panes. The divider sits in it.

### EXIT_TITLE

<a id="constant-exit_title"></a>

```rust
const EXIT_TITLE: &str = "Confirm exit";
```

The constant `EXIT_TITLE` is the title of the exit confirmation box.
The title is `Confirm exit`.

### EXIT_BODY

<a id="constant-exit_body"></a>

```rust
const EXIT_BODY: &str = "Exit the SSH client?";
```

The constant `EXIT_BODY` is the question in the exit confirmation box.
The question is `Exit the SSH client?`.

### EXIT_POS_W

<a id="constant-exit_pos_w"></a>

```rust
const EXIT_POS_W: i32 = 160;
```

Title box used to decide whether a saved exit origin meets the view.

### EXIT_POS_H

<a id="constant-exit_pos_h"></a>

```rust
const EXIT_POS_H: i32 = 48;
```

The constant `EXIT_POS_H` is the height of the box used to test a saved exit origin.
The height is 48 pixels.
The test places that box on the saved origin and asks whether it meets the view.

### NEW_TERM_TITLE

<a id="constant-new_term_title"></a>

```rust
const NEW_TERM_TITLE: &str = "New Term";
```

The constant `NEW_TERM_TITLE` is the title of the new-terminal box.
The title is `New Term`.

### NEW_TERM_BODY

<a id="constant-new_term_body"></a>

```rust
const NEW_TERM_BODY: &str = "Split the focused terminal:";
```

The constant `NEW_TERM_BODY` is the prompt in the new-terminal box.
The prompt is `Split the focused terminal:`.

### PASTE_TITLE

<a id="constant-paste_title"></a>

```rust
const PASTE_TITLE: &str = "Edit paste";
```

The constant `PASTE_TITLE` is the title of the paste editor.
The title is `Edit paste`.

### PASTE_HINT

<a id="constant-paste_hint"></a>

```rust
const PASTE_HINT: &str = "Ctrl+Enter or OK sends to the terminal. Enter inserts a newline.";
```

The constant `PASTE_HINT` is the hint in the paste editor.
The text says Ctrl+Enter or OK sends the text to the terminal.
The text says Enter inserts a newline.

### PASTE_OVER

<a id="constant-paste_over"></a>

```rust
const PASTE_OVER: &str = "Paste exceeds 65536 characters.";
```

The constant `PASTE_OVER` is the notice when pasted text is longer than 65536 characters.
[`PasteDecision::Over`](chrome/enum.PasteDecision.md#member-over) opens a Load File notice with this text.

### FILE_BINARY

<a id="constant-file_binary"></a>

```rust
const FILE_BINARY: &str = "File looks binary. Send a UTF-8 text file.";
```

The constant `FILE_BINARY` is the notice when a file contains a NUL byte.
The console text reader returns this text. An import that finds a NUL returns it as well.

### FILE_EMPTY

<a id="constant-file_empty"></a>

```rust
const FILE_EMPTY: &str = "File is empty.";
```

The constant `FILE_EMPTY` is the notice for an empty file.
The text is `File is empty.`

### FILE_OVER

<a id="constant-file_over"></a>

```rust
const FILE_OVER: &str = "File exceeds 65536 characters.";
```

The constant `FILE_OVER` is the notice when a file is too long.
The text is `File exceeds 65536 characters.`

### NOT_CONNECTED

<a id="constant-not_connected"></a>

```rust
const NOT_CONNECTED: &str = "Not connected.";
```

The constant `NOT_CONNECTED` is the notice when no session is connected.
The text is `Not connected.`

### ABOUT_TITLE

<a id="constant-about_title"></a>

```rust
const ABOUT_TITLE: &str = "About Aetheris";
```

The constant `ABOUT_TITLE` is the title of the About box.
The title is `About Aetheris`.

### ABOUT_APP_PX

<a id="constant-about_app_px"></a>

```rust
const ABOUT_APP_PX: u32 = 64;
```

App mark on the About box. Half the prior edge. The ground is black.

### ABOUT_BODY

<a id="constant-about_body"></a>

```rust
const ABOUT_BODY: &str = "Aetheris Shell\nSSH terminal (DarkJediTerm) for the Casserole/Roux host.\nJava + Apache MINA SSHD + JediTerm.\n\nAuthor: Clay Lambert\nCopyright (C) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>\nLicensed under the MIT License.";
```

The constant `ABOUT_BODY` is the body of the About box.
The first line is `Aetheris Shell`.
The second line is `SSH terminal (DarkJediTerm) for the Casserole/Roux host.`
The third line is `Java + Apache MINA SSHD + JediTerm.`
A blank line follows those three lines.
The author line is `Author: Clay Lambert`.
The copyright line is `Copyright (C) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>`.
The last line is `Licensed under the MIT License.`

### HELP_TITLE

<a id="constant-help_title"></a>

```rust
const HELP_TITLE: &str = "Help";
```

The constant `HELP_TITLE` is the title of the Help box.
The title is `Help`.

### HELP_BODY

<a id="constant-help_body"></a>

```rust
const HELP_BODY: &str = "Aetheris Shell\n\nConnect: fill host/port/user/password and Connect.\nUsername combo remembers accounts (if Remember password).\nNew Term: split the focused pane Horizontal or Vertical.\n  Ctrl+Shift+D horizontal   Ctrl+Shift+E vertical   Ctrl+Shift+W close pane\nFont size applies to every pane.\nPaste: Ctrl+V / Ctrl+Shift+V opens the paste editor. OK inserts the text. Enter in the terminal submits. Shift-Enter adds a line. Load File is the same path.\nImport: File menu or the console right-click menu. The file dialog inserts the file at the terminal cursor.\nRestart always starts with one pane.\n\nOn the server: Esc toggles cmd> (medium blue). Type help for commands.\nmessage list is terse; message list verbose is full.\nmessage find by 'text' scans this session (case-insensitive).\nOne disk-backed login per username; change the combo before Connect on a new pane.";
```

The constant `HELP_BODY` is the body of the Help box.
The first line is `Aetheris Shell`.
A blank line follows that title.
The connect line is `Connect: fill host/port/user/password and Connect.`
The account line is `Username combo remembers accounts (if Remember password).`
The split line is `New Term: split the focused pane Horizontal or Vertical.`
The chord line begins with two spaces. It names `Ctrl+Shift+D` horizontal, `Ctrl+Shift+E` vertical, and `Ctrl+Shift+W` close pane.
The font line is `Font size applies to every pane.`

The paste line says `Ctrl+V` or `Ctrl+Shift+V` opens the paste editor.
The paste line says OK inserts the text.
The paste line says Enter in the terminal submits.
The paste line says Shift-Enter adds a line.
The paste line says Load File is the same path.
The import line says the File menu or the console right-click menu starts import.
The import line says the file dialog inserts the file at the terminal cursor.
The restart line is `Restart always starts with one pane.`

A blank line follows the restart line.
The server line says Esc toggles `cmd>` in medium blue.
The server line says to type help for commands.
The list line says `message list` is terse and `message list verbose` is full.
The find line says `message find by 'text'` scans this session and ignores case.
The last line says one disk-backed login is kept per username.
The last line says to change the combo before Connect on a new pane.

### IMPORT_MENU

<a id="constant-import_menu"></a>

```rust
const IMPORT_MENU: &str = "Import...";
```

The constant `IMPORT_MENU` is the Import line on a menu.
The text is `Import...`.

### EXPORT_SESSION_LABEL

<a id="constant-export_session_label"></a>

```rust
const EXPORT_SESSION_LABEL: &str = "Export Session...";
```

The constant `EXPORT_SESSION_LABEL` is the Export Session line on a menu.
The text is `Export Session...`.

### EXPORT_SESSION_TIP

<a id="constant-export_session_tip"></a>

```rust
const EXPORT_SESSION_TIP: &str = "Export Session writes this console's Roux memory to a .toon file. The file is the whole message table. Cancel writes nothing. The session stays.";
```

The constant `EXPORT_SESSION_TIP` is the hover text for Export Session.
The text says the command writes this console's Roux memory to a `.toon` file.
The text says the file is the whole message table.
The text says cancel writes nothing.
The text says the session stays.

### EXPORT_SESSION_RAW

<a id="constant-export_session_raw"></a>

```rust
const EXPORT_SESSION_RAW: &str = "raw console has no Roux export";
```

The constant `EXPORT_SESSION_RAW` is the notice for a console that has no session export.
The text is `raw console has no Roux export`.

### SESSION_EXPORT_ASK

<a id="constant-session_export_ask"></a>

```rust
const SESSION_EXPORT_ASK: &str = "session_export:\n";
```

The constant `SESSION_EXPORT_ASK` is the request line that asks for a session export.
The text is `session_export:` followed by a newline.

### SESSION_REPLACE_ASK

<a id="constant-session_replace_ask"></a>

```rust
const SESSION_REPLACE_ASK: &str = "session_replace:\n";
```

The constant `SESSION_REPLACE_ASK` is the request line that asks for a session replace.
The text is `session_replace:` followed by a newline.

### TRANSFER_TITLE

<a id="constant-transfer_title"></a>

```rust
const TRANSFER_TITLE: &str = "Transfer Conversation";
```

The constant `TRANSFER_TITLE` is the title of the transfer box.
The title is `Transfer Conversation`.

### TRANSFER_TO_CANVAS_BODY

<a id="constant-transfer_to_canvas_body"></a>

```rust
const TRANSFER_TO_CANVAS_BODY: &str =
"Clear the canvas and copy this Roux session onto the canvas cells?";
```

The constant `TRANSFER_TO_CANVAS_BODY` is the body of the confirm box that asks before a console session is copied onto the canvas.
The text asks whether to clear the canvas and copy the Roux session onto the canvas cells. `ask_transfer_from_console` passes this text to `ask_ok`. The title passed with it is `Transfer Conversation`.

### TRANSFER_TO_ROUX_BODY

<a id="constant-transfer_to_roux_body"></a>

```rust
const TRANSFER_TO_ROUX_BODY: &str =
"Clear the Roux session and copy this canvas into the Roux chat?";
```

The constant `TRANSFER_TO_ROUX_BODY` is the body of the confirm box that asks before the canvas is copied into the Roux chat.
The text asks whether to clear the Roux session and copy this canvas into that chat. `ask_transfer_from_canvas` passes this text to `ask_ok`. The title passed with it is `Transfer Conversation`.

### TRANSFER_CANVAS_TIP

<a id="constant-transfer_canvas_tip"></a>

```rust
const TRANSFER_CANVAS_TIP: &str = "Transfer Conversation copies this canvas into the Roux session after OK. The Roux turns become these prompts and replies. Cancel leaves both sides as they are.";
```

The constant `TRANSFER_CANVAS_TIP` is the hover text on the canvas menu line `Transfer Conversation`.
The text says OK copies this canvas into the Roux session. The Roux turns then become these prompts and replies. Cancel leaves both sides as they are.

### TRANSFER_CONSOLE_TIP

<a id="constant-transfer_console_tip"></a>

```rust
const TRANSFER_CONSOLE_TIP: &str = "Transfer Conversation copies this Roux session onto the canvas after OK. The canvas turns become the chat. Cancel leaves both sides as they are.";
```

The constant `TRANSFER_CONSOLE_TIP` is the hover text on the console menu line `Transfer Conversation`.
The text says OK copies this Roux session onto the canvas. The canvas turns then become the chat. Cancel leaves both sides as they are.

### TRANSFER_WORD

<a id="constant-transfer_word"></a>

```rust
const TRANSFER_WORD: &str = "conversation transferred";
```

The constant `TRANSFER_WORD` is the status text `conversation transferred`.
`land_transfer_replace` stores this text when the replace reply is success. `land_transfer_cards` stores this text after the canvas becomes the copied cells.

### TRANSFER_BUSY

<a id="constant-transfer_busy"></a>

```rust
const TRANSFER_BUSY: &str = "a turn is still running";
```

The constant `TRANSFER_BUSY` is the status text `a turn is still running`.
Both transfer confirms store this text when any card has `running` set. The copy does not start on that path.

### TRANSFER_TABLE

<a id="constant-transfer_table"></a>

```rust
const TRANSFER_TABLE: &str = "replace failed: the table is not a message table";
```

The constant `TRANSFER_TABLE` is the error `replace failed: the table is not a message table`.
`parse_message_table` returns this text when the table is empty. The same text is returned when the header is not `messages[N]{r,ts,tok,x}:`, when a row does not split, or when the row count does not match that header.

### TRANSFER_FIT

<a id="constant-transfer_fit"></a>

```rust
const TRANSFER_FIT: &str = "replace failed: the session does not fit in one frame";
```

The constant `TRANSFER_FIT` is the status text `replace failed: the session does not fit in one frame`.
`ask_transfer_from_canvas` stores this text when `text_frame` cannot wrap the replace body. The canvas is not sent on that path.

### SESSION_EXPORT_SAVE

<a id="constant-session_export_save"></a>

```rust
const SESSION_EXPORT_SAVE: &str = "Add-Type -AssemblyName System.Windows.Forms; $d = New-Object System.Windows.Forms.SaveFileDialog; $d.Title = 'Export Session'; $d.Filter = 'Toon files (*.toon)|*.toon|All files (*.*)|*.*'; $d.DefaultExt = 'toon'; if ($d.ShowDialog() -eq [System.Windows.Forms.DialogResult]::OK) { [Console]::OutputEncoding = New-Object System.Text.UTF8Encoding $false; [Console]::Out.WriteLine($d.FileName) }";
```

The constant `SESSION_EXPORT_SAVE` is the PowerShell script that opens a Save dialog titled `Export Session`.
The filter offers `*.toon` and every file. The default extension is `toon`. OK prints the chosen path as UTF-8 with no byte-order mark. Cancel prints no path.

`pick_session_export_path` runs this script through `chooser_line`. An empty line is a cancel. A printed path becomes the file that receives the table.

### CLOSE_PANE_MENU

<a id="constant-close_pane_menu"></a>

```rust
const CLOSE_PANE_MENU: &str = "Close Pane  (Ctrl+Shift+W)";
```

The constant `CLOSE_PANE_MENU` is the File menu label `Close Pane  (Ctrl+Shift+W)`.
Two spaces sit between `Pane` and the opening parenthesis. The button is enabled only when more than one pane is open. A click sets `close_pane_clicked`.

### CONSOLE_MENU

<a id="constant-console_menu"></a>

```rust
const CONSOLE_MENU: [&str; 13] = [
    "Open as URL",
    "Copy",
    "Paste",
    "Select All",
    "Clear Buffer",
    "Page Up",
    "Page Down",
    "Line Up",
    "Line Down",
    IMPORT_MENU,
    EXPORT_SESSION_LABEL,
    TRANSFER_LABEL,
    SELECT_MODEL_LABEL,
];
```

JediTerm's console menu, then Import, then Export Session, then Transfer Conversation, then Select model.

### CONSOLE_MENU_GAP

<a id="constant-console_menu_gap"></a>

```rust
const CONSOLE_MENU_GAP: [&str; 6] = [
    "Clear Buffer",
    "Page Up",
    "Line Up",
    IMPORT_MENU,
    EXPORT_SESSION_LABEL,
    TRANSFER_LABEL,
];
```

A separator sits ahead of each of these lines. Java `separatorBefore`, plus the Import gap.

### DEFAULT_WIDTH

<a id="constant-default_width"></a>

```rust
const DEFAULT_WIDTH: f32 = 1024.0;
```

The constant `DEFAULT_WIDTH` is the window width used when no usable saved rectangle exists.
The value is 1024 logical pixels. `open_frame_seat` returns this width with no place. A physical width that cannot convert from `i32` also falls back to this value.

### DEFAULT_HEIGHT

<a id="constant-default_height"></a>

```rust
const DEFAULT_HEIGHT: f32 = 968.0;
```

The constant `DEFAULT_HEIGHT` is the window height used when no usable saved rectangle exists.
The value is 968 logical pixels. `open_frame_seat` returns this height with no place. A physical height that cannot convert from `i32` also falls back to this value.

### MIN_WIDTH

<a id="constant-min_width"></a>

```rust
const MIN_WIDTH: f32 = 640.0;
```

The constant `MIN_WIDTH` is the smallest inner width the window will keep.
The value is 640 logical pixels. The window attributes use it as the minimum inner size. `clamp_logical` raises a narrower width to this floor.

### MIN_HEIGHT

<a id="constant-min_height"></a>

```rust
const MIN_HEIGHT: f32 = 480.0;
```

The constant `MIN_HEIGHT` is the smallest inner height the window will keep.
The value is 480 logical pixels. The window attributes use it as the minimum inner size. `clamp_logical` raises a shorter height to this floor.

### OPEN_MAXIMIZED

<a id="constant-open_maximized"></a>

```rust
const OPEN_MAXIMIZED: bool = true;
```

The window opens maximized.

### OPEN_ZOOM_STEPS

<a id="constant-open_zoom_steps"></a>

```rust
const OPEN_ZOOM_STEPS: u32 = 3;
```

Ctrl-+ presses on a fresh start. egui adds one tenth and rounds.

### ZOOM_STEP

<a id="constant-zoom_step"></a>

```rust
const ZOOM_STEP: f32 = 0.1;
```

The constant `ZOOM_STEP` is the zoom added for one Ctrl-+ step.
The value is 0.1. `open_zoom` adds it, clamps the result from 0.2 through 5.0, and rounds to one decimal.

### PLATE

<a id="constant-plate"></a>

```rust
const PLATE: egui::Color32 = egui::Color32::from_rgb(0x0d, 0x0d, 0x0d);
```

The constant `PLATE` is the near-black fill behind the panes and the canvas.
The red, green, and blue bytes are 0x0D, 0x0D, and 0x0D. Panel fill, window fill, and the extreme background use it. `paint_colors` uses it as the default cell background.

### STATUS_BG

<a id="constant-status_bg"></a>

```rust
const STATUS_BG: egui::Color32 = egui::Color32::from_rgb(0x17, 0x17, 0x17);
```

Java `DEFAULT_STATUS_BACKGROUND`. The thin bar under the Aetheris pane.

### STATUS_FG

<a id="constant-status_fg"></a>

```rust
const STATUS_FG: egui::Color32 = egui::Color32::from_rgb(0xb4, 0xb4, 0xb4);
```

Java `DEFAULT_STATUS_FOREGROUND`.

### STATUS_PAD_X

<a id="constant-status_pad_x"></a>

```rust
const STATUS_PAD_X: f32 = 8.0;
```

Java status `EmptyBorder` left and right.

### STATUS_PAD_Y

<a id="constant-status_pad_y"></a>

```rust
const STATUS_PAD_Y: f32 = 3.0;
```

Java status `EmptyBorder` top and bottom.

### INK

<a id="constant-ink"></a>

```rust
const INK: egui::Color32 = egui::Color32::from_rgb(0xdc, 0xdc, 0xdc);
```

The constant `INK` is the default terminal foreground.
The red, green, and blue bytes are 0xDC, 0xDC, and 0xDC. `paint_colors` uses it when a cell's foreground is the default color. A mood choice that is not the selected ink also uses this color.

### SELECT_BG

<a id="constant-select_bg"></a>

```rust
const SELECT_BG: egui::Color32 = egui::Color32::from_rgb(0x34, 0x5a, 0x8c);
```

The constant `SELECT_BG` is the background of a selected terminal cell.
The red, green, and blue bytes are 0x34, 0x5A, and 0x8C. `paint_colors` returns this color as the background when the cell is selected.

### SELECT_FG

<a id="constant-select_fg"></a>

```rust
const SELECT_FG: egui::Color32 = egui::Color32::from_rgb(0xff, 0xff, 0xff);
```

The constant `SELECT_FG` is the foreground of a selected terminal cell.
The red, green, and blue bytes are 0xFF, 0xFF, and 0xFF. `paint_colors` returns this color as the foreground when the cell is selected.

### CURSOR

<a id="constant-cursor"></a>

```rust
const CURSOR: egui::Color32 = egui::Color32::from_rgb(0x00, 0xd7, 0xff);
```

The constant `CURSOR` is the cyan used for a 1-pixel inside stroke.
The red, green, and blue bytes are 0x00, 0xD7, and 0xFF. The focused pane wears that stroke when more than one pane is showing. The terminal cell under the cursor wears the same stroke.

## Function Detail

### emerald_combo_arrow

<a id="function-emerald_combo_arrow"></a>

```rust
fn emerald_combo_arrow(
    ui: &egui::Ui,
    rect: egui::Rect,
    _visuals: &egui::style::WidgetVisuals,
    _open: bool,
)
```

The function `emerald_combo_arrow` paints the pull-down arrow on a combo box.
The arrow is a triangle. The triangle uses the same emerald green as a hovered button word. That green is `#00FF66`.
The triangle is centered in `rect`. The triangle width is seven tenths of `rect`. The triangle height is forty-five hundredths of `rect`.
`ui` is the `egui` surface that receives the triangle. `_visuals` is not read. `_open` is not read.

| | |
| --- | --- |
| **Specification** | F-154 |

### think_tag_word

<a id="function-think_tag_word"></a>

```rust
fn think_tag_word(model: &str) -> String
```

The function `think_tag_word` builds the short tag from a model name.
`model` is that name. A newline is dropped. A carriage return is dropped. The box-drawing character U+2500 is dropped.
The remaining text is trimmed. An empty result is the word `model`. The result stops at 80 UTF-16 code units.

### model_after_slash

<a id="function-model_after_slash"></a>

```rust
fn model_after_slash(target: &str) -> String
```

The function `model_after_slash` returns the text after the first slash.
`target` is the model word. The word is trimmed before the split. The text after the slash is trimmed.
A word with no slash returns an empty string. The Java client calls this `modelAfterSlash`.

### short_model_name

<a id="function-short_model_name"></a>

```rust
fn short_model_name(target: &str) -> String
```

The function `short_model_name` returns the short model label.
`target` is the model word. `ollama` and `grok` use the text after the slash. An empty text after the slash uses the provider word itself. `pool` returns `pool`.
Any other provider returns an empty string. The Java client calls this `shortModelName`.

### think_model_word

<a id="function-think_model_word"></a>

```rust
fn think_model_word(target: &str) -> String
```

The function `think_model_word` builds the tag word for a model target.
`target` is that model word. A blank short name uses the provider word. `think_tag_word` turns a blank word into `model`.

### think_paints

<a id="function-think_paints"></a>

```rust
fn think_paints(provider: &str, grok_on: bool, pool_on: bool) -> bool
```

The function `think_paints` decides whether a thought is painted.
`provider` is the engine word. `grok_on` is the Grok switch. `pool_on` is the Pool switch.
Grok is painted only when `grok_on` is true. Pool is painted only when `pool_on` is true. Ollama is painted. Any other provider is painted.
The Java client calls this choice `paintThinkDelta`.

### think_tag_line

<a id="function-think_tag_line"></a>

```rust
fn think_tag_line(word: &str) -> String
```

The function `think_tag_line` builds the cyan model tag.
`word` is the model word on that line. The line is two U+2500 characters, the word, and two more U+2500 characters.
The Java client writes the same four rule characters.

### think_pane_height

<a id="function-think_pane_height"></a>

```rust
fn think_pane_height(line: f32) -> f32
```

The function `think_pane_height` returns the floor height of the thought pane.
`line` is the height of one text line. The result is three lines plus 8 pixels. The north-edge drag uses this height as the floor.

### think_mark

<a id="function-think_mark"></a>

```rust
fn think_mark(folded: bool) -> &'static str
```

The function `think_mark` returns the fold button mark.
`folded` is true when the text is hidden. A folded strip returns `^`. A strip that is showing text returns `v`.

### fold_think

<a id="function-fold_think"></a>

```rust
fn fold_think(chrome: &mut ThinkChrome)
```

The function `fold_think` folds or restores the text on this strip.
`chrome` is the pane state. The open flag stays as it was. The log is not an argument, so the log stays.
The slide is set to `-1`, which stops the rise. The Java client calls this `ThinkStrip.fold`.

### close_think

<a id="function-close_think"></a>

```rust
fn close_think(chrome: &mut ThinkChrome)
```

The function `close_think` hides the thought pane.
`chrome` is the pane state. The open flag becomes false. The fold flag becomes false, so the mark returns to `v`. The slide becomes `-1`.
The log is not an argument, so the log stays.

### reveal_think

<a id="function-reveal_think"></a>

```rust
fn reveal_think(chrome: &mut ThinkChrome)
```

The function `reveal_think` shows the pane for a new thought.
`chrome` is the pane state. The open flag becomes true. A fold stays folded.
A pane that was closed sets the slide to `0`, so the pane rises from the buttons. A pane that was already open keeps its slide.

### open_vt_strip

<a id="function-open_vt_strip"></a>

```rust
fn open_vt_strip(open: &mut bool, slide: &mut f32)
```

The function `open_vt_strip` opens the thought strip for this SSH session.
`open` is that session's open flag. `slide` is that session's rise. A fold is not an argument, so the fold stays on this session.
A closed strip sets the slide to `0`, so the strip rises from the top of that pane. The Java client calls this `ThinkStrip.openStrip`.

### show_thinking

<a id="function-show_thinking"></a>

```rust
fn show_thinking(open: bool, slide: f32) -> (bool, f32, bool)
```

The function `show_thinking` is the Tools command Show Thinking.
`open` is true when the pane in front is already open. `slide` is the current rise.
The result is three values. The first value is open, and it is true, so the plate in front opens. The second value is the slide. A closed pane returns `0` so the pane rises. An open pane returns the slide it already had.
The third value is the fold flag, and it is false, so a fold opens and the text is visible. The log is not an argument. The yaml is not an argument. Both stay as they are.

| | |
| --- | --- |
| **Specification** | PK-1285 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md |

### track_focused_think

<a id="function-track_focused_think"></a>

```rust
fn track_focused_think(dialog: bool, gate: bool, own: bool) -> bool
```

The function `track_focused_think` chooses which think flag is in force.
`dialog` is true while the console dialog is open. `gate` is the flag the dialog owns. `own` is the flag the focused session stored.
An open dialog returns `gate`. A shut dialog returns `own`.

### console_piece_opens

<a id="function-console_piece_opens"></a>

```rust
fn console_piece_opens(piece: &crate::amwp::ScopePiece) -> bool
```

The function `console_piece_opens` reports whether a scope piece turns thinking on.
`piece` is one [`ScopePiece`](amwp/enum.ScopePiece.md). A piece that is not `Later` returns false. A cell that is not the nil cell returns false.
The body is read as text and passed to `console_result_open`. The function returns true only when that call returns `Some(true)`. That true result means the `shell_model_result:` body says thinking is on.

### seat_vt_chrome

<a id="function-seat_vt_chrome"></a>

```rust
fn seat_vt_chrome(open: bool, slide: f32, folded: bool, height: f32) -> ThinkChrome
```

The function `seat_vt_chrome` builds the thought strip for one SSH session.
`open` is the open flag. `slide` is the rise. `folded` is the fold. `height` is the dragged height.
The result is a [`ThinkChrome`](chrome/struct.ThinkChrome.md) that holds those four values. The function does not read a file.

### keep_vt_session

<a id="function-keep_vt_session"></a>

```rust
fn keep_vt_session(chrome: &ThinkChrome) -> (bool, f32, bool, f32)
```

The function `keep_vt_session` copies one session strip back out of [`ThinkChrome`](chrome/struct.ThinkChrome.md).
`chrome` is that strip. The result is four values, in this order: open, slide, folded, height.
The function does not write a file.

### clear_think

<a id="function-clear_think"></a>

```rust
fn clear_think(log: &mut ThinkLog, chrome: &mut ThinkChrome)
```

The function `clear_think` empties the thought pane.
`log` is the session log. `chrome` is the pane state. The block list is cleared. The live flag becomes false. The cell id becomes sixteen zero bytes.
The open flag becomes true, so the strip stays up. The fold flag is not changed, so a fold stays folded.

### think_max_height

<a id="function-think_max_height"></a>

```rust
fn think_max_height(panel_h: f32, natural: f32) -> f32
```

The function `think_max_height` returns the tallest scroll height the pane may use.
`panel_h` is the measured panel height. `natural` is the three-line height.
A panel shorter than 1 pixel has not been measured. That case returns `natural` or 480, whichever is larger. 480 is the fallback height.
A measured panel keeps 160 pixels for the canvas and the footer. The room above that keep is the limit. When that room is shorter than `natural`, the result is `natural`.
The Java client calls this `thinkMaxHeight`.

### think_scroll_height

<a id="function-think_scroll_height"></a>

```rust
fn think_scroll_height(stored: f32, natural: f32, panel_h: f32) -> f32
```

The function `think_scroll_height` returns the height of the thought scroll.
`stored` is the dragged height. `natural` is the three-line height. `panel_h` is the measured panel height.
A stored height of zero uses `natural`. A stored height above zero uses that stored height.
The result is clamped between `natural` and the max from `think_max_height`.

### rise_think

<a id="function-rise_think"></a>

```rust
fn rise_think(slide: f32, full: f32) -> f32
```

The function `rise_think` advances one step of the rising notebook strip.
`slide` is the current rise in pixels. `full` is the settled height. A result of `-1` means the rise has settled.
A negative slide is already settled. A `full` of zero or less is settled. A slide within half a pixel of `full` is settled.
Otherwise the next step comes from `mood_slide_next`. That step is a quarter of the remaining span, and it is at least 12 pixels. A step that lands within half a pixel of `full` returns `-1`.

### drag_think_height

<a id="function-drag_think_height"></a>

```rust
fn drag_think_height(stored: f32, natural: f32, panel_h: f32, delta_y: f32) -> f32
```

The function `drag_think_height` changes the notebook pane height from a drag.
`stored` is the dragged height. `natural` is the three-line floor. `panel_h` is the measured panel height. `delta_y` is the drag in pixels.
Dragging up passes a negative `delta_y` and grows the pane. The result stays between `natural` and the max from `think_max_height`.

### drag_vt_think_height

<a id="function-drag_vt_think_height"></a>

```rust
fn drag_vt_think_height(stored: f32, natural: f32, panel_h: f32, delta_y: f32) -> f32
```

The function `drag_vt_think_height` changes the VT100 thought-pane height from a drag.
`stored` is the dragged height. `natural` is the three-line floor. `panel_h` is the measured panel height. `delta_y` is the drag in pixels.
Dragging down passes a positive `delta_y` and grows the pane. The result stays between `natural` and the max from `think_max_height`.

### think_log_path

<a id="function-think_log_path"></a>

```rust
fn think_log_path(home: &std::path::Path) -> std::path::PathBuf
```

The function `think_log_path` builds the path of the thought log.
`home` is the user home directory. The file is `home` joined to `.casserole` and then to `aetheris-thinking.txt`. The yaml file sits in that same `.casserole` directory.

### think_yaml_path

<a id="function-think_yaml_path"></a>

```rust
fn think_yaml_path(home: &std::path::Path) -> std::path::PathBuf
```

The function `think_yaml_path` builds the path of the shared think yaml.
`home` is the user home directory. The file is `home` joined to `.casserole` and then to `aetheris-think.yaml`.
The `cells` map uses the same cell ids as the Java vault.

### think_log_text

<a id="function-think_log_text"></a>

```rust
fn think_log_text(blocks: &[ThinkBlock]) -> String
```

The function `think_log_text` renders the thought log as one string.
`blocks` is the list of [`ThinkBlock`](chrome/struct.ThinkBlock.md) values. Each block is the tag line, a newline, and the body.
A newline sits between blocks. The first block has no newline before it. The Java client writes this same document.

### parse_think_log

<a id="function-parse_think_log"></a>

```rust
fn parse_think_log(text: &str) -> Vec<ThinkBlock>
```

The function `parse_think_log` reads a thought log back into blocks.
`text` is the file text. An empty string returns an empty list.
A line is split on a newline, and a trailing carriage return is removed. A line that starts with U+2500 is a tag and starts a new [`ThinkBlock`](chrome/struct.ThinkBlock.md).
The lines under that tag are the body, joined with newlines. Lines before the first tag are dropped. The last open tag is kept even when its body is empty.

### write_think_log

<a id="function-write_think_log"></a>

```rust
fn write_think_log(path: &std::path::Path, text: &str) -> bool
```

The function `write_think_log` writes the thought log to disk.
`path` is the file. `text` is the document from `think_log_text`.
A parent directory is created when the parent path is not empty. A failed create returns false. A failed write returns false. A finished write returns true.

### read_think_log

<a id="function-read_think_log"></a>

```rust
fn read_think_log(path: &std::path::Path) -> Vec<ThinkBlock>
```

The function `read_think_log` loads the thought log from disk.
`path` is the file. A missing file returns an empty list. An empty file returns an empty list. A file that cannot be read returns an empty list.
Any other file is parsed as UTF-8 with lossy replacement, then passed to `parse_think_log`.

### load_think_file

<a id="function-load_think_file"></a>

```rust
fn load_think_file() -> (ThinkLog, ThinkChrome, ThinkYaml)
```

The function `load_think_file` loads the thought log, the pane state, and the yaml.
The result is those three values. A missing home directory returns an empty [`ThinkLog`](chrome/struct.ThinkLog.md), a new [`ThinkChrome`](chrome/struct.ThinkChrome.md), and a fresh [`ThinkYaml`](chrome/struct.ThinkYaml.md).
Otherwise the log file is read. A non-empty block list replaces the empty log. The yaml file is read. `seat_think_home` then applies that yaml to the pane.

### store_think_file

<a id="function-store_think_file"></a>

```rust
fn store_think_file(log: &ThinkLog)
```

The function `store_think_file` writes the thought log.
`log` is the session log. A missing home directory writes nothing. Otherwise the blocks are rendered and written to the thought-log path. A failed write is ignored.

### yaml_scalar

<a id="function-yaml_scalar"></a>

```rust
fn yaml_scalar(rest: &str) -> &str
```

The function `yaml_scalar` reads one plain value from the text after a colon.
`rest` is that text. Empty text returns an empty string.
A value that starts with a double quote returns the text before the next double quote. A value that starts with a double quote and has no second quote is not treated as quoted.
A value that starts with a single quote returns the text before the next single quote. A value that starts with a single quote and has no second quote is not treated as quoted.
Any other value returns the first word. A `#` in that word starts a comment, and the comment is dropped.

### split_yaml_key

<a id="function-split_yaml_key"></a>

```rust
fn split_yaml_key(line: &str) -> Option<(&str, &str)>
```

The function `split_yaml_key` splits one top-level yaml line into a key and the rest.
`line` is that line. A line with no colon returns nothing.
The key must be non-empty. The key may contain ASCII letters, ASCII digits, `_`, and `-`. Any other character returns nothing.
The rest is the text after the first colon, including its leading space.

### think_version_one

<a id="function-think_version_one"></a>

```rust
fn think_version_one(value: &str) -> bool
```

The function `think_version_one` reports whether a yaml value is version 1.
`value` is the text after the colon. The value is read with `yaml_scalar`.
The word `1` returns true. A finite number from 1.0 up to, but not including, 2.0 returns true. Any other text returns false.

### think_flag

<a id="function-think_flag"></a>

```rust
fn think_flag(value: &str) -> bool
```

The function `think_flag` reads a yaml yes-or-no value.
`value` is the text after the colon. The value is read with `yaml_scalar`.
`true`, `yes`, and `1` return true. The words `true` and `yes` ignore ASCII case. Any other text returns false.

### think_kept_height

<a id="function-think_kept_height"></a>

```rust
fn think_kept_height(value: &str) -> Option<i32>
```

The function `think_kept_height` reads a dragged height that the file may keep.
`value` is the text after the colon. The value is read with `yaml_scalar`, then parsed as a floating-point number.
A number that is not finite returns nothing. A number below 48 returns nothing. A number of 2001 or more returns nothing.
A number inside that range is truncated toward zero and returned as an `i32`.

### parse_think_yaml

<a id="function-parse_think_yaml"></a>

```rust
fn parse_think_yaml(text: &str) -> ThinkYaml
```

The function `parse_think_yaml` reads the text of `aetheris-think.yaml`.
`text` is that file body. A leading UTF-8 byte-order mark is ignored. Empty text returns a blocked [`ThinkYaml`](chrome/struct.ThinkYaml.md).
A leading `---` line, a leading `%` line, and blank lines are skipped until the first key. A non-empty line before any key returns a blocked record. A top-level line that is not a key returns a blocked record.
A top-level line does not start with a space or a tab. Later indented lines stay on the current key.
The key `version` must be version 1. A missing version returns a blocked record. Any other version returns a blocked record.
The key `closed` is read with `think_flag`. The key `height` is read with `think_kept_height`. The key `cells` is kept as the original block, including its key line. Every other key is kept in `extras`, in the order it was read.

### render_think_yaml

<a id="function-render_think_yaml"></a>

```rust
fn render_think_yaml(yaml: &ThinkYaml) -> String
```

The function `render_think_yaml` writes a [`ThinkYaml`](chrome/struct.ThinkYaml.md) back to text.
`yaml` is the record. The text starts with `version: 1`. The next line is `closed: true` or `closed: false`.
A stored height adds a `height:` line. An empty `cells` block is written as `cells: {}`. A stored `cells` block is written as it was read, with a newline added when it does not already end in one.
The extras text is written last.

### read_think_yaml

<a id="function-read_think_yaml"></a>

```rust
fn read_think_yaml(path: &std::path::Path) -> ThinkYaml
```

The function `read_think_yaml` loads `aetheris-think.yaml` from disk.
`path` is the file. A file that cannot be read returns a fresh [`ThinkYaml`](chrome/struct.ThinkYaml.md). An empty file returns a blocked [`ThinkYaml`](chrome/struct.ThinkYaml.md).
Any other file is parsed as UTF-8 with lossy replacement, then passed to `parse_think_yaml`.

### write_think_yaml

<a id="function-write_think_yaml"></a>

```rust
fn write_think_yaml(path: &std::path::Path, yaml: &ThinkYaml) -> bool
```

The function `write_think_yaml` writes `aetheris-think.yaml`.
`path` is the file. `yaml` is the record. A blocked record returns false and writes nothing.
A parent directory is created when the parent path is not empty. A failed create returns false. A failed write returns false. A finished write returns true.

### note_think_closed

<a id="function-note_think_closed"></a>

```rust
fn note_think_closed(yaml: &mut ThinkYaml) -> bool
```

The function `note_think_closed` sets the closed mark on the think yaml.
`yaml` is the record. A blocked record returns false and is left alone. Any other record sets `closed` to true and returns true.
This function does not write the file.

### note_think_opened

<a id="function-note_think_opened"></a>

```rust
fn note_think_opened(yaml: &mut ThinkYaml) -> bool
```

The function `note_think_opened` clears the closed mark after a later thought.
`yaml` is the record. A blocked record returns false. A record that is already open returns false and stays as it is.
A closed record sets `closed` to false and returns true. This function does not write the file.

### note_think_height

<a id="function-note_think_height"></a>

```rust
fn note_think_height(yaml: &mut ThinkYaml, px: i32) -> bool
```

The function `note_think_height` stores a dragged thought-pane height.
`yaml` is the record. `px` is the height in pixels. A blocked record returns false. A height outside 48 through 2000 returns false.
A height inside that range is stored and the function returns true. This function does not write the file. The Java client keeps the same range.

### nil_cell

<a id="function-nil_cell"></a>

```rust
fn nil_cell(cell: &[u8; 16]) -> bool
```

The function `nil_cell` reports the console cell.
`cell` is a 16-byte id. Sixteen zero bytes return true. Any other id returns false.
A canvas card does not own this id. The Java client calls it `CONSOLE_CELL`.

### vt_think_yaml_path

<a id="function-vt_think_yaml_path"></a>

```rust
fn vt_think_yaml_path(home: &std::path::Path) -> std::path::PathBuf
```

The function `vt_think_yaml_path` builds the path of the VT100 think file.
`home` is the user home directory. The file is `home` joined to `.casserole` and then to `aetheris-vt100-think.yaml`.
The notebook file `aetheris-think.yaml` stays a separate file.

### parse_vt_think_yaml

<a id="function-parse_vt_think_yaml"></a>

```rust
fn parse_vt_think_yaml(text: &str) -> VtThinkYaml
```

The function `parse_vt_think_yaml` reads the text of `aetheris-vt100-think.yaml`.
`text` is that file body. A leading UTF-8 byte-order mark is ignored. Empty text returns a blocked [`VtThinkYaml`](chrome/struct.VtThinkYaml.md).
A blank line and a `#` comment are skipped. A leading `---` line and a leading `%` line are skipped until the first key.
An indented line before any key returns a blocked record. An indented line after a key is skipped. A line that is not a key returns a blocked record.
The key `version` must be version 1. A missing version returns a blocked record. Any other version returns a blocked record.
The key `closed` is read with `think_flag`. The key `height` is read with `think_kept_height`. Any other key is ignored. The thought text is not stored.

### render_vt_think_yaml

<a id="function-render_vt_think_yaml"></a>

```rust
fn render_vt_think_yaml(yaml: &VtThinkYaml) -> String
```

The function `render_vt_think_yaml` writes a [`VtThinkYaml`](chrome/struct.VtThinkYaml.md) back to text.
`yaml` is the record. The text starts with `version: 1`. The next line is `closed: true` or `closed: false`.
A stored height adds a `height:` line. The thought text is not written.

### read_vt_think_yaml

<a id="function-read_vt_think_yaml"></a>

```rust
fn read_vt_think_yaml(path: &std::path::Path) -> VtThinkYaml
```

The function `read_vt_think_yaml` loads `aetheris-vt100-think.yaml` from disk.
`path` is the file. A file that cannot be read returns a fresh [`VtThinkYaml`](chrome/struct.VtThinkYaml.md). An empty file returns a blocked [`VtThinkYaml`](chrome/struct.VtThinkYaml.md).
Any other file is parsed as UTF-8 with lossy replacement, then passed to `parse_vt_think_yaml`.

### write_vt_think_yaml

<a id="function-write_vt_think_yaml"></a>

```rust
fn write_vt_think_yaml(path: &std::path::Path, yaml: &VtThinkYaml) -> bool
```

The function `write_vt_think_yaml` writes `aetheris-vt100-think.yaml`.
`path` is the file. `yaml` is the record. A blocked record returns false and writes nothing.
A parent directory is created when the parent path is not empty. A failed create returns false. A failed write returns false. A finished write returns true.

### note_vt_closed

<a id="function-note_vt_closed"></a>

```rust
fn note_vt_closed(yaml: &mut VtThinkYaml) -> bool
```

The function `note_vt_closed` sets the closed mark on the VT100 think file.
`yaml` is the record. A blocked record returns false and is left alone. Any other record sets `closed` to true and returns true.
This function does not write the file.

### note_vt_opened

<a id="function-note_vt_opened"></a>

```rust
fn note_vt_opened(yaml: &mut VtThinkYaml) -> bool
```

The function `note_vt_opened` clears the closed mark on the VT100 think file.
`yaml` is the record. A blocked record returns false. A record that is already open returns false and stays as it is.
A closed record sets `closed` to false and returns true. This function does not write the file.

### note_vt_height

<a id="function-note_vt_height"></a>

```rust
fn note_vt_height(yaml: &mut VtThinkYaml, px: i32) -> bool
```

The function `note_vt_height` stores a dragged VT100 thought-pane height.
`yaml` is the record. `px` is the height in pixels. A blocked record returns false. A height outside 48 through 2000 returns false.
A height inside that range is stored and the function returns true. This function does not write the file.

### seat_vt_home

<a id="function-seat_vt_home"></a>

```rust
fn seat_vt_home(chrome: &mut ThinkChrome, yaml: &VtThinkYaml)
```

The function `seat_vt_home` applies the VT100 think file to a new pane.
`chrome` is the pane state. `yaml` is the file record. The open flag is set to false, so the pane starts hidden.
A blocked record returns without copying a height. A stored height is copied onto the pane. This function does not open the strip.

### load_vt_think

<a id="function-load_vt_think"></a>

```rust
fn load_vt_think() -> (ThinkChrome, VtThinkYaml)
```

The function `load_vt_think` loads the VT100 think file and a pane to match it.
The result is a [`ThinkChrome`](chrome/struct.ThinkChrome.md) and a [`VtThinkYaml`](chrome/struct.VtThinkYaml.md). A missing home directory returns a new pane and a fresh record.
Otherwise the yaml is read from `vt_think_yaml_path` and applied with `seat_vt_home`.

### seat_think_home

<a id="function-seat_think_home"></a>

```rust
fn seat_think_home(log: &ThinkLog, chrome: &mut ThinkChrome, yaml: &ThinkYaml)
```

The function `seat_think_home` applies the shared think file to the notebook pane.
`log` is the session log. `chrome` is the pane state. `yaml` is the file record.
A blocked record with at least one block sets the pane open and returns. A blocked record does not copy a height.
A record that is not blocked copies a stored height. That record opens the pane only when the log has a block and `closed` is false. `closed: true` leaves the pane shut even when the log has text.

### think_line_indent

<a id="function-think_line_indent"></a>

```rust
fn think_line_indent(line: &str) -> usize
```

The function `think_line_indent` counts the leading spaces on a yaml line.
`line` is that line. Only the space character counts. A tab does not count.

### think_field_name

<a id="function-think_field_name"></a>

```rust
fn think_field_name(line: &str) -> Option<&str>
```

The function `think_field_name` reads the field name on one yaml line.
`line` is that line. The line is trimmed, then split on the first colon.
A line with no colon returns nothing. An empty name returns nothing. A name may contain ASCII letters, ASCII digits, `_`, and `-`. Any other character returns nothing.

### think_field_value

<a id="function-think_field_value"></a>

```rust
fn think_field_value(line: &str) -> String
```

The function `think_field_value` reads the value on one yaml field line.
`line` is that line. The line is trimmed. A line with no colon returns an empty string.
The text after the colon is read with `yaml_scalar`.

### split_think_cells

<a id="function-split_think_cells"></a>

```rust
fn split_think_cells(cells: &str) -> Option<(String, Vec<ThinkCellBlock>)>
```

The function `split_think_cells` splits the `cells` block into a header and one block per cell.
`cells` is that block, including its key line. A carriage return at the end of a line is removed. A final empty line is dropped when the text ends with a newline.
The first line must be a key whose name trims to `cells`. Any other block returns nothing.
A header whose value starts with `{` is an empty flow map. That form returns the header `cells:` and no cells, and only when the block is one line. A header with any other value returns nothing.
A later line with text and fewer than four leading spaces starts a cell. The cell key is that line trimmed, with one trailing colon removed and with matching quotes removed. Lines under the cell stay on that cell.
The result is the header line and the cell blocks.

### join_think_cells

<a id="function-join_think_cells"></a>

```rust
fn join_think_cells(header: &str, blocks: &[ThinkCellBlock]) -> String
```

The function `join_think_cells` writes the `cells` block back to text.
`header` is the `cells` key line. `blocks` is the cell list. The header is trimmed on the right, then a newline is added.
Each line of each cell is written, and each of those lines ends with a newline.

### think_snap_of_block

<a id="function-think_snap_of_block"></a>

```rust
fn think_snap_of_block(block: &ThinkCellBlock) -> crate::model::ThinkSnap
```

The function `think_snap_of_block` reads the five think fields from one cell block.
`block` is that cell. The result starts from `crate::model::ThinkSnap::off`, which is every switch off and every string empty.
`ollama_on`, `grok_on`, and `pool_on` are read with `think_flag`. `ollama_level` and `ollama_id` are read as text. A line that is not one of those names is skipped.

### think_scalar_text

<a id="function-think_scalar_text"></a>

```rust
fn think_scalar_text(value: &str) -> String
```

The function `think_scalar_text` renders one think field value for yaml.
`value` is that text. An empty value is written as `''`.
A value of only ASCII letters, ASCII digits, `-`, `_`, `.`, `:`, `+`, and `/` is written as itself, unless the word is `true`, `false`, `yes`, `no`, `null`, or `~`.
Any other value is wrapped in single quotes. A single quote inside the value is doubled.

### put_think_field

<a id="function-put_think_field"></a>

```rust
fn put_think_field(lines: &mut Vec<String>, name: &str, rendered: &str) -> bool
```

The function `put_think_field` writes one indented field into a cell block.
`lines` is the cell text. `name` is the field name. `rendered` is the value text already prepared for yaml.
The wanted line is four spaces, the name, a colon, a space, and the value. A line that already equals that text returns false.
A line with the same field name and different text is replaced, and the function returns true. A missing field is appended, and the function returns true.

### patch_think_block

<a id="function-patch_think_block"></a>

```rust
fn patch_think_block(block: &mut ThinkCellBlock, snap: &crate::model::ThinkSnap) -> bool
```

The function `patch_think_block` updates one cell block to match a think snapshot.
`block` is the cell. `snap` is the wanted `crate::model::ThinkSnap`. A block that already matches returns false.
Each field that differs is written with `put_think_field`. The fields are `ollama_on`, `ollama_level`, `ollama_id`, `grok_on`, and `pool_on`. A switch is written as `true` or `false`. A string is written with `think_scalar_text`.
The result is true when any field line changed.

### seat_cell_think

<a id="function-seat_cell_think"></a>

```rust
fn seat_cell_think(cards: &mut [TurnCard], yaml: &ThinkYaml)
```

The function `seat_cell_think` copies saved think fields onto the matching cards.
`cards` is the canvas. `yaml` is the shared think file. A blocked file returns without a change. A `cells` block that cannot be split returns without a change.
A card whose id is the nil cell is skipped. The console id is that nil cell, and a canvas card does not own it.
Each other card looks up its hex id in the cell blocks, ignoring ASCII case. A missing id leaves that card as it is. A matching block copies `ollama_on`, `ollama_level`, `ollama_id`, `grok_on`, and `pool_on` onto the card.

### think_open_echo

<a id="function-think_open_echo"></a>

```rust
fn think_open_echo(
    target: Option<&str>,
    prior: &crate::model::ThinkSnap,
    next: &crate::model::ThinkSnap,
) -> bool
```

The function `think_open_echo` reports an echo that is not a checkbox edit.
`target` is the new model target. `prior` is the snapshot before the dialog filled the id. `next` is the snapshot after that fill.
Opening the dialog fills `ollama_id` from the target and sends no new target, so `target` is absent. The function returns true when `target` is absent and `ollama_on`, `ollama_level`, `grok_on`, and `pool_on` are unchanged.
A real edit changes a flag, the level, or the target. That edit returns false. `ollama_id` is not compared.

### note_cell_think

<a id="function-note_cell_think"></a>

```rust
fn note_cell_think(yaml: &mut ThinkYaml, id: &str, snap: &crate::model::ThinkSnap) -> bool
```

The function `note_cell_think` writes one cell's five think fields into the yaml.
`yaml` is the record. `id` is the cell id. `snap` is the wanted `crate::model::ThinkSnap`. The five fields are `ollama_on`, `ollama_level`, `ollama_id`, `grok_on`, and `pool_on`.
A blocked record returns false. An empty id returns false. The nil id `00000000-0000-0000-0000-000000000000` returns false. The comparison ignores ASCII case.
An empty `cells` block starts as the header `cells:` and no cells. A `cells` block that cannot be split returns false.
A matching cell is updated with `patch_think_block`. Other cells stay. Unknown keys inside a cell stay, because the patch writes only the five fields. A cell that already matches writes nothing and returns false.
A missing cell whose snapshot is `crate::model::ThinkSnap::off` returns false. Any other missing cell is appended and patched. The function returns true when `cells` was replaced.

### mood_yaml_path

<a id="function-mood_yaml_path"></a>

```rust
fn mood_yaml_path(home: &std::path::Path) -> std::path::PathBuf
```

`~/.casserole/aetheris-mood.yaml`. The shared record. A per-user file wins.

### mood_user_path

<a id="function-mood_user_path"></a>

```rust
fn mood_user_path(home: &std::path::Path, user: &str) -> std::path::PathBuf
```

`~/.casserole/aetheris-mood-{user}.yaml`. The name is sanitized.

### any_user_mood_file

<a id="function-any_user_mood_file"></a>

```rust
fn any_user_mood_file(home: &std::path::Path) -> bool
```

True when any per-user mood file is already on disk.

### claim_shared_mood

<a id="function-claim_shared_mood"></a>

```rust
fn claim_shared_mood(owner_empty: bool, any_user_file: bool, own_file: bool) -> bool
```

The first seated user claims the shared file only while no per-user file exists.

### seated_mood_line

<a id="function-seated_mood_line"></a>

```rust
fn seated_mood_line(
    owner: &str,
    asking: &str,
    showing: &MoodYaml,
    bank: &[(String, MoodYaml)],
    ssh_users: &[(String, bool)],
) -> Option<String>
```

The line one user seats. A user who is not the owner does not read the showing record.

### mood_has_line

<a id="function-mood_has_line"></a>

```rust
fn mood_has_line(yaml: &MoodYaml) -> bool
```

The function `mood_has_line` reports whether a [`MoodYaml`](chrome/struct.MoodYaml.md) holds a mood string.
`yaml` is that record. The check trims `line`. A line that still has a character returns true. A line that is empty after the trim returns false.

### mood_sends

<a id="function-mood_sends"></a>

```rust
fn mood_sends(yaml: &MoodYaml) -> bool
```

The function `mood_sends` reports whether a canvas send carries the mood string and the shape line.
`yaml` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. A blocked record returns false. A switch that is off returns false. A line that trims to empty returns false.
Any other record returns true. The shape line is the invitation that follows the mood string on that send.

### ssh_mood_live

<a id="function-ssh_mood_live"></a>

```rust
fn ssh_mood_live(checked: bool, mood: &MoodYaml) -> bool
```

The function `ssh_mood_live` reports whether an SSH pane should take the mood.
`checked` is the footer box. `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. The function returns true only when that box is checked and `mood_sends` is true.
An unchecked box leaves the SSH panes as they are.

### ssh_mood_line

<a id="function-ssh_mood_line"></a>

```rust
fn ssh_mood_line(checked: bool, mood: &MoodYaml) -> Option<String>
```

The function `ssh_mood_line` returns the mood string an SSH pane should hold.
`checked` is the footer box. `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. A pair that `ssh_mood_live` rejects returns `None`.
A live pair returns the trimmed mood line. `None` leaves a pane that was never given a line alone. `None` is also the signal that clears a pane that already holds a line.

### ssh_mood_seat

<a id="function-ssh_mood_seat"></a>

```rust
fn ssh_mood_seat(checked: bool, mood: &MoodYaml) -> Option<String>
```

The function `ssh_mood_seat` returns the mood line an SSH pane should hold.
`checked` is the footer checkbox. `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. The checkbox makes the line available. The mood switch turns that line on through `mood_sends`.
Either one off returns `None`. That `None` clears a pane that already holds a line. The function calls `ssh_mood_line`.

### shell_mood_body

<a id="function-shell_mood_body"></a>

```rust
fn shell_mood_body(line: &str) -> String
```

The function `shell_mood_body` builds the channel A body that sets one pane's mood.
`line` is the mood string. The body is the mark `shell_mood:`, a newline, the trimmed line, and a final newline.
Channel A is the canvas channel. The terminal grid does not paint channel A.

### shell_status_mood

<a id="function-shell_status_mood"></a>

```rust
fn shell_status_mood(west: &str, live: bool) -> String
```

The function `shell_status_mood` joins the left-hand status text with the mood gate.
`west` is the text already on the left of the status strip. `live` is true when the mood gate is on. A gate that is off returns `west` alone.
An empty `west` with a live gate returns the words `mood active`. Any other live gate returns `west`, two spaces, and `mood active`. The mood string itself stays off this strip.

### shell_status_with_facts

<a id="function-shell_status_with_facts"></a>

```rust
fn shell_status_with_facts(west: &str, live: bool, facts: &str) -> String
```

The function `shell_status_with_facts` joins the SSH status, the mood gate, and the Aetheris facts.
`west` is the left-hand status text. `live` is the mood gate. `facts` is the extra text this strip does not already carry.
The head is `shell_status_mood` of `west` and `live`. Empty facts return that head. An empty head returns the facts alone. Any other pair returns the head, two spaces, and the facts.

### shell_status_with_tokens

<a id="function-shell_status_with_tokens"></a>

```rust
fn shell_status_with_tokens(west: &str, estimate: usize) -> String
```

The function `shell_status_with_tokens` appends the token estimate to the joined strip.
`west` is that strip. `estimate` is the token count. The count passes through `count_i32` and then `token_line`.
An empty strip returns the estimate line alone. Any other strip returns the strip, two spaces, and the estimate line. The name on the right of the status strip stays off this string.

### session_memory_text

<a id="function-session_memory_text"></a>

```rust
fn session_memory_text(grid: &Grid) -> String
```

The function `session_memory_text` returns the console text one pane holds.
`grid` is that pane's [`Grid`](../aetheris-term/grid/struct.Grid.md). The lines are the scrollback, then the visible rows, in that order. A trailing row that trims to empty is dropped.
The remaining lines are joined with a newline. An empty pane returns an empty string. `estimate_tokens` of that empty string is 1.

### session_memory_lines

<a id="function-session_memory_lines"></a>

```rust
fn session_memory_lines(text: &str) -> usize
```

The function `session_memory_lines` counts the non-blank rows in pane text.
`text` is the string from `session_memory_text`. A line that trims to empty is skipped. The token count stores this number as `blocks`.

### ssh_mood_relay

<a id="function-ssh_mood_relay"></a>

```rust
fn ssh_mood_relay(want: Option<&str>, held: Option<&str>) -> Option<String>
```

The function `ssh_mood_relay` builds the channel A body for one pane, or returns nothing.
`want` is the mood line the pane should hold. `held` is the line the pane already holds. Both values are trimmed. An empty value is treated as absent.
Equal values return `None`, so a pane that already matches sends nothing. A wanted line returns the `shell_mood:` body from `shell_mood_body`. No wanted line returns the text `shell_mood_clear:`.
An unchecked box with nothing held therefore sends nothing, because both sides are absent.

### relay_one_ssh_mood

<a id="function-relay_one_ssh_mood"></a>

```rust
fn relay_one_ssh_mood(session: &crate::session::Session, want: Option<&str>)
```

The function `relay_one_ssh_mood` sends one pane's mood body when the pane must change.
`session` is that pane. `want` is the mood line it should hold. A session whose mux has not answered returns without sending.
`ssh_mood_relay` compares `want` with the line the session already holds. No body means the pane already matches, and the function returns. A body that `text_frame` cannot build also returns.
The frame uses a cell id of sixteen zero bytes. A body that starts with `shell_mood_clear:` stores no held line. Any other body stores the trimmed wanted line. The session then writes the frame.

### mood_quote

<a id="function-mood_quote"></a>

```rust
fn mood_quote(text: &str) -> String
```

The function `mood_quote` wraps one mood string as a double-quoted yaml scalar.
`text` is that string. The result starts and ends with `"`. A backslash becomes two backslashes. A double quote becomes a backslash and a quote. A newline becomes the two characters `\` and `n`.
A carriage return is dropped. Every other character is copied.

### mood_unquote

<a id="function-mood_unquote"></a>

```rust
fn mood_unquote(raw: &str) -> String
```

The function `mood_unquote` reads one quoted yaml scalar back into text.
`raw` is trimmed first. An empty value returns an empty string. A value that does not start with `"` or `'` returns that trimmed text unchanged.
The opening quote must close. A double-quoted value treats `\` as an escape. `\n` becomes a newline. `\t` becomes a tab. Any other escaped character is that character. A trailing `\` with nothing after it stays a backslash.
A single-quoted value copies characters until the closing quote and does not treat `\` as an escape.

### mood_key_line

<a id="function-mood_key_line"></a>

```rust
fn mood_key_line(line: &str) -> Option<(String, String)>
```

The function `mood_key_line` splits one top-level mood line into a key and the text after the colon.
`line` has its trailing whitespace removed. A key wrapped in `'` or `"` ends at the matching quote. The text after that quote must contain `:`. An empty quoted key returns `None`.
The value is the text after that colon, with leading whitespace removed. An unquoted line uses `split_yaml_key`. A line that is not a key returns `None`.

### mood_join_plain

<a id="function-mood_join_plain"></a>

```rust
fn mood_join_plain(first: &str, more: &[String]) -> String
```

The function `mood_join_plain` joins a plain yaml value with the indented lines under it.
`first` is the text on the key line, after trim. `more` holds the following lines. An empty piece inserts a newline when the result does not already end with one.
A non-empty piece inserts a space when the result is not empty and does not end with a newline. The piece is then appended.

### mood_value

<a id="function-mood_value"></a>

```rust
fn mood_value(rest: &str, more: &[String]) -> String
```

The function `mood_value` reads one mood-yaml value, including a block that continues on later lines.
`rest` is the text after the colon. `more` holds the indented lines under that key. A value that starts with `"` or `'` returns `mood_unquote` of `rest`, and `more` is not used.
A value that starts with `|` keeps each following line on its own line. A value that starts with `>` folds those lines with spaces. A blank line in either block inserts a newline. The block result drops trailing newlines.
Any other value is joined by `mood_join_plain`.

### mood_kept_pair

<a id="function-mood_kept_pair"></a>

```rust
fn mood_kept_pair(width: &str, height: &str) -> Option<(i32, i32)>
```

The function `mood_kept_pair` reads a dialog width and height that the mood file may keep.
`width` and `height` are the texts after those colons. Each text is read with `yaml_scalar` and parsed as a floating-point number. A value that does not parse returns `None`. A value that is not finite returns `None`.
Each number is truncated toward zero. The width must sit from 480 through 4000. The height must sit from 320 through 4000. A number outside its range returns `None`.

### parse_mood_yaml

<a id="function-parse_mood_yaml"></a>

```rust
fn parse_mood_yaml(text: &str) -> MoodYaml
```

The function `parse_mood_yaml` reads the text of one mood yaml file into a [`MoodYaml`](chrome/struct.MoodYaml.md).
`text` is that file body. A leading UTF-8 byte-order mark is ignored. Empty text returns a blocked record. A trailing empty line after a final newline is dropped. A carriage return at the end of a line is dropped.
A leading `---` line, a leading `%` line, and blank lines are skipped until the first key. A line that is indented before any key returns a blocked record. A top-level line that is not a key returns a blocked record. A file with no keys returns a blocked record.
The key `version` must be version 1. Any other version returns a blocked record. A missing `version` key returns a blocked record. Unknown keys are ignored.
`line` is read with `mood_value`. `on` and `pending` are read with `think_flag`. `pins` is read with `mood_pin_rows`. `width` and `height` are kept only as a pair from `mood_kept_pair`. The canvas id stays empty. A line that trims to empty forces the switch off.

### mood_pin_rows

<a id="function-mood_pin_rows"></a>

```rust
fn mood_pin_rows(more: &[String]) -> Vec<(String, String)>
```

The function `mood_pin_rows` reads the indented pin lines under the `pins` key.
`more` holds those lines. A blank line is skipped. A line with no colon is skipped. The name is the text before the first colon, with `"` and `'` trimmed from both ends. An empty name is skipped.
The value is `mood_unquote` of the text after that colon. An empty value is skipped. Each kept row is the canvas id and then the pin, which is a cell id or the word `head`.

### mood_pin_for

<a id="function-mood_pin_for"></a>

```rust
fn mood_pin_for(yaml: &MoodYaml, canvas: &str) -> String
```

The function `mood_pin_for` returns the pin stored for one canvas.
`yaml` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. `canvas` is the canvas id. A matching pin is returned as stored. A missing id returns the word `head`.

### set_mood_pin

<a id="function-set_mood_pin"></a>

```rust
fn set_mood_pin(yaml: &mut MoodYaml, canvas: &str, pin: &str)
```

The function `set_mood_pin` stores one canvas pin on a [`MoodYaml`](chrome/struct.MoodYaml.md).
`yaml` is that record. `canvas` is the canvas id. `pin` is the cell id or the word `head`. An empty canvas id writes nothing.
A canvas that is already in the pin list replaces its pin. A new canvas is appended.

### render_mood_yaml

<a id="function-render_mood_yaml"></a>

```rust
fn render_mood_yaml(yaml: &MoodYaml) -> String
```

The function `render_mood_yaml` writes version 1 of a [`MoodYaml`](chrome/struct.MoodYaml.md) as text.
`yaml` is that record. The text starts with `version: 1`. The line is written with `mood_quote`. The switch is written as `'on': true` or `'on': false`. `pending` is written as `true` or `false`.
A non-empty pin list is written under `pins:`, one canvas id and pin per indented line. The pin text is not quoted. Width and height are written only when both are present. The canvas id is not written.

### read_mood_yaml

<a id="function-read_mood_yaml"></a>

```rust
fn read_mood_yaml(path: &std::path::Path) -> MoodYaml
```

The function `read_mood_yaml` loads one mood file from disk.
`path` is that file. A file that cannot be read returns `MoodYaml::fresh`. An empty file returns `MoodYaml::blocked`. Any other bytes are parsed with `parse_mood_yaml` as lossy UTF-8.

### write_mood_yaml

<a id="function-write_mood_yaml"></a>

```rust
fn write_mood_yaml(path: &std::path::Path, yaml: &MoodYaml) -> bool
```

The function `write_mood_yaml` stores one [`MoodYaml`](chrome/struct.MoodYaml.md) when the record is not blocked.
`path` is the file. `yaml` is the record. A blocked record returns false and writes nothing. The parent directory is created when the path has one and that create fails returns false.
The bytes are `render_mood_yaml`. The function returns true when the write succeeds.

### note_mood_flip

<a id="function-note_mood_flip"></a>

```rust
fn note_mood_flip(yaml: &mut MoodYaml) -> bool
```

The function `note_mood_flip` turns the mood switch the other way when a string is saved.
`yaml` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. A blocked record returns false and is left alone. A line that trims to empty returns false and is left alone.
Any other record toggles `on` and returns true.

### load_mood_file

<a id="function-load_mood_file"></a>

```rust
fn load_mood_file() -> MoodYaml
```

The function `load_mood_file` reads the shared mood file for this user.
The path is `mood_yaml_path` under the home directory. A missing home directory returns `MoodYaml::fresh`. The bytes are read with `read_mood_yaml`.

### ssh_mood_yaml_path

<a id="function-ssh_mood_yaml_path"></a>

```rust
fn ssh_mood_yaml_path(home: &std::path::Path) -> std::path::PathBuf
```

The function `ssh_mood_yaml_path` builds the path of the SSH mood checkbox file.
`home` is the home directory. The path is `home` joined with `.casserole` and then `aetheris-ssh-mood.yaml`. The shared mood file `aetheris-mood.yaml` stays as it is.

### ssh_user_on

<a id="function-ssh_user_on"></a>

```rust
fn ssh_user_on(users: &[(String, bool)], name: &str) -> bool
```

The function `ssh_user_on` reports whether one canvas user turned the SSH mood box on.
`users` is the list of names and flags. `name` is the user to find. An empty name returns false. A name that is not in the list returns false.
A matching name returns that stored flag. The match is exact.

### set_ssh_user

<a id="function-set_ssh_user"></a>

```rust
fn set_ssh_user(users: &mut Vec<(String, bool)>, name: &str, on: bool)
```

The function `set_ssh_user` stores one user's SSH mood checkbox.
`users` is the list of names and flags. `name` is the user. `on` is the new flag. An empty name writes nothing.
A name that is already in the list replaces the earlier flag. A new name is appended.

### render_ssh_mood

<a id="function-render_ssh_mood"></a>

```rust
fn render_ssh_mood(users: &[(String, bool)]) -> String
```

The function `render_ssh_mood` writes the SSH mood checkbox file as text.
`users` is the list of names and flags. The text starts with `version: 1` and then `users:`. Each user is one indented line of the name, a colon, and `true` or `false`.

### parse_ssh_mood

<a id="function-parse_ssh_mood"></a>

```rust
fn parse_ssh_mood(text: &str) -> (bool, Vec<(String, bool)>)
```

The function `parse_ssh_mood` reads the text of the SSH mood checkbox file.
`text` is that body. The result is a blocked flag and the user list. A blank line is skipped. A line that starts with `#` is skipped. A line that does not start with a space or a tab ends the `users` block.
`version:` is parsed as an integer. The word `users:` opens the user block. Inside that block, each line is a name, a colon, and a flag. Quotes around the name are removed. An empty name is skipped. The flag is on only when the text after the colon trims to `true`.
A version other than 1 returns blocked and an empty list, including text that has no version. The users read before that check are dropped.

### load_ssh_mood_file

<a id="function-load_ssh_mood_file"></a>

```rust
fn load_ssh_mood_file() -> (bool, Vec<(String, bool)>)
```

The function `load_ssh_mood_file` reads the SSH mood checkbox file for this user.
The result is a blocked flag and the user list. A missing home directory returns not blocked and an empty list. A file that cannot be read returns the same pair. That missing file is an empty map.
An empty file returns blocked and an empty list. Any other bytes are parsed with `parse_ssh_mood` as lossy UTF-8.

### write_ssh_mood

<a id="function-write_ssh_mood"></a>

```rust
fn write_ssh_mood(path: &std::path::Path, users: &[(String, bool)]) -> bool
```

The function `write_ssh_mood` stores the SSH mood checkbox list.
`path` is the file. `users` is the list of names and flags. The parent directory is created when the path has one. A failed create returns false.
The bytes are `render_ssh_mood`. The function returns true when the write succeeds.

### load_grok_home

<a id="function-load_grok_home"></a>

```rust
fn load_grok_home() -> crate::model::GrokDoc
```

The function `load_grok_home` loads the Grok settings document for this user.
A missing home directory returns `crate::model::GrokDoc::memory`, which is a document with no file. Any other home loads `grok_path` through `crate::model::GrokDoc::load`.
A missing file stays empty until a cell writes one.

### mint_pool_boot

<a id="function-mint_pool_boot"></a>

```rust
fn mint_pool_boot() -> String
```

The function `mint_pool_boot` builds one boot id for this window.
Pool execute and pool mood send this id. The first eight bytes are the nanoseconds since the Unix epoch, as a big-endian `u64`. A clock before that epoch uses 1.
The last eight bytes are that same count multiplied, with wrap, by `0x9E3779B97F4A7C15`, also big-endian. The text is `cell_text` of those sixteen bytes.

### mood_knob

<a id="function-mood_knob"></a>

```rust
fn mood_knob(mood: &MoodYaml) -> (egui::Color32, bool)
```

The function `mood_knob` returns the color and the side of the round mood button.
`mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. A record with no mood string returns grey and false. False places the button on the left, which is off.
A switch that is on returns the green ink and true. True places the button on the right. A saved string whose switch is off returns the red ink and false.

### preview_chars

<a id="function-preview_chars"></a>

```rust
fn preview_chars(text: &str, max: usize) -> String
```

The function `preview_chars` shortens one mood string for a single preview line.
`text` is that string. `max` is the most characters to keep. Each newline becomes a space. A result that is already within `max` is returned as that one line.
A longer result keeps the first `max` characters and then the ellipsis character U+2026.

### mood_inspector_head

<a id="function-mood_inspector_head"></a>

```rust
fn mood_inspector_head(mood: &MoodYaml) -> String
```

The function `mood_inspector_head` returns the inspector line for a mood that is not showing its yellow card.
`mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. A record with no mood string returns `mood: (none)` and a newline. A switch that is off returns `mood: off `, then the first 80 characters from `preview_chars`, and a newline.
A switch that is on returns an empty string. The yellow card is the mood string in that case.

### paint_inspector_mood

<a id="function-paint_inspector_mood"></a>

```rust
fn paint_inspector_mood(ui: &mut egui::Ui, line: &str)
```

The function `paint_inspector_mood` paints the yellow mood card at the head of the inspector scroll.
`ui` is the inspector column. `line` is the mood string. The card wraps to the width of the visible clip, and that width is at least 1. The ink is the yellow `MOOD_YELLOW`. The font is the body style.
The card takes a hover row of that wrapped height, which is at least 1. The text is painted at the left of the clip and at the top of that row. The card stays on that column while the turn text scrolls sideways. A scroll down carries the card out of view.

### mint_mood_wire

<a id="function-mint_mood_wire"></a>

```rust
fn mint_mood_wire(n: u64) -> [u8; 16]
```

The function `mint_mood_wire` builds one mood wire id.
`n` is the count for this send. The first eight bytes are zero. The last eight bytes are `n` as a big-endian `u64`. `mint_cell` leaves that tail zero, so a mood id does not collide with a fresh cell id.

### mood_target

<a id="function-mood_target"></a>

```rust
fn mood_target(card: &TurnCard) -> String
```

The function `mood_target` returns the model word one card would send.
`card` is that [`TurnCard`](chrome/struct.TurnCard.md). The target is trimmed. An empty word returns `ollama`. A target whose provider is `pool` returns the word `pool`.
Any other target returns the trimmed word, including its provider prefix.

### mood_focus_card

<a id="function-mood_focus_card"></a>

```rust
fn mood_focus_card(cards: &[TurnCard], focus: usize) -> Option<usize>
```

The function `mood_focus_card` returns the canvas cell the mood glass uses.
`cards` is the deck. `focus` is the focused index. The Java client calls this read `moodCard`. An empty deck returns `None`.
An index inside the deck returns that index. An index past the deck returns the last cell.
// @aa-sdd PK-1170 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-208

### mood_glass_target

<a id="function-mood_glass_target"></a>

```rust
fn mood_glass_target(
    card: &TurnCard,
    entry: Option<&crate::model::GrokEntry>,
    pool_boot: &str,
) -> String
```

The function `mood_glass_target` returns the model the mood glass sends.
`card` is the selected cell. `entry` is that cell's Grok settings, when the document has one. `pool_boot` is the boot id from `mint_pool_boot`. The Java client calls this `moodTarget`.
The result is the `target` from `crate::model::mood_tune`. A pool card sends the word `pool`. A Grok card with no model id takes the model stored on `entry`.
// @aa-sdd PK-1170 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-208

### mood_glass_title

<a id="function-mood_glass_title"></a>

```rust
fn mood_glass_title(target: &str) -> String
```

The function `mood_glass_title` builds the title bar of the mood glass.
`target` is the model word the glass will send. The short name comes from `short_model_name`. A blank short name keeps the word `Mood`.
Any other short name returns `Mood`, a middle dot U+00B7, and that name.
// @aa-sdd PK-1170 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-208

### mood_open_px

<a id="function-mood_open_px"></a>

```rust
fn mood_open_px(yaml: &MoodYaml) -> (f32, f32)
```

The function `mood_open_px` returns the width and height used to open the mood glass.
`yaml` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. A record that has both a width and a height returns that pair as `f32` values. Any missing side returns 760 by 520.
The place on screen is not in this pair.

### note_mood_size

<a id="function-note_mood_size"></a>

```rust
fn note_mood_size(yaml: &mut MoodYaml, width: i32, height: i32) -> bool
```

The function `note_mood_size` stores the mood dialog size for the next open.
`yaml` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. `width` and `height` are the pixel sizes. A blocked record returns false and leaves the stored pair alone.
The width must sit from 480 through 4000. The height must sit from 320 through 4000. A size outside either range returns false and leaves the pair alone. A size inside both ranges is stored and the function returns true.

### approve_mood_line

<a id="function-approve_mood_line"></a>

```rust
fn approve_mood_line(yaml: &mut MoodYaml, draft: &str) -> bool
```

The function `approve_mood_line` saves the mood-string field and turns the switch on.
`yaml` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. `draft` is the field text. The text is trimmed. An empty field returns false and writes no string.
A trimmed field that differs from the stored line sets `pending`. The stored line becomes that trimmed text. A switch that is off is turned on. A line that `mood_has_line` then rejects turns the switch off. The function returns true after that write.

### clear_mood_line

<a id="function-clear_mood_line"></a>

```rust
fn clear_mood_line(yaml: &mut MoodYaml) -> bool
```

The function `clear_mood_line` drops the saved mood string and turns the switch off.
`yaml` is the [`MoodYaml`](chrome/struct.MoodYaml.md) record. A blocked record returns false and is left alone. The line is cleared. The switch is turned off. `pending` is cleared. The pin list is cleared.
Width and height stay. The function returns true after that clear.

### seed_mood_field

<a id="function-seed_mood_field"></a>

```rust
fn seed_mood_field(desk: &mut MoodDesk, saved: &str)
```

The function `seed_mood_field` copies the saved mood into the rising field when the glass opens.
`desk` is the [`MoodDesk`](chrome/struct.MoodDesk.md). `saved` is the stored mood string. The string is trimmed. An empty save returns without writing, so the field stays empty.
Any other save replaces `draft` with that trimmed text. The chat and the typed line stay empty.

### mood_origin

<a id="function-mood_origin"></a>

```rust
fn mood_origin(screen: egui::Rect, width: f32, height: f32) -> egui::Pos2
```

The function `mood_origin` returns the top-left point that centers the mood glass.
`screen` is the app window. `width` and `height` are the glass size. The point starts at the center of `screen`, shifted back by half the width and half the height.
A right edge past the window is pulled to the window's right. A left edge past the window is pulled to the window's left. The bottom and the top are pulled the same way. The file does not store this place.

### mood_fit

<a id="function-mood_fit"></a>

```rust
fn mood_fit(screen: egui::Rect, width: f32, height: f32) -> (egui::Pos2, egui::Vec2)
```

The function `mood_fit` clamps the mood glass to the app window and returns its origin and size.
`screen` is the app window. `width` and `height` are the wanted size. A negative size is treated as 0. A size larger than the window is cut to the window.
The origin comes from `mood_origin` of that fitted size. A saved pair larger than the window therefore opens at the window edge. The button row stays inside that height.

### mood_slots

<a id="function-mood_slots"></a>

```rust
fn mood_slots(inner_h: i32, draft_px: i32, button_px: i32, gap_px: i32) -> MoodSlots
```

The function `mood_slots` divides the mood glass into transcript, draft, and button heights.
`inner_h` is the inner height of the glass. `draft_px` is the wanted draft height. `button_px` is the wanted button height. `gap_px` is one gap. A negative input is treated as 0.
The button row is kept, and it is cut to the inner height when it is taller. The draft is cut to half the inner height. When the draft is showing, two gaps are reserved first. If those two gaps fill the room above the buttons, the draft height becomes 0. Otherwise the draft is cut to the room that remains, and that room then shrinks by the draft.
When the draft height is 0, one gap is reserved, cut to the room above the buttons. `glass` is the room left for the transcript.

### mood_floor

<a id="function-mood_floor"></a>

```rust
fn mood_floor(screen: egui::Rect) -> egui::Vec2
```

The function `mood_floor` returns the smallest size the mood glass may be dragged to.
`screen` is the app window. The width is 200, or the window width when that is smaller. The height is 160, or the window height when that is smaller. A negative window side is treated as 0 before that cut.

### mood_wrap_lines

<a id="function-mood_wrap_lines"></a>

```rust
fn mood_wrap_lines(text: &str, inner_chars: usize) -> usize
```

The function `mood_wrap_lines` counts the wrapped lines in the rising mood field.
`text` is the field. `inner_chars` is the number of characters on one row, and a count of 0 is treated as 1. Empty text returns 0.
Each newline-separated piece is counted. An empty piece counts as 1. Any other piece counts as its character length divided by the row width, rounded up.

### mood_pane_px

<a id="function-mood_pane_px"></a>

```rust
fn mood_pane_px(text: &str, inner_chars: usize, line_px: i32, dialog_h: i32) -> i32
```

The function `mood_pane_px` returns the pixel height of the rising mood pane.
`text` is the field. `inner_chars` is the wrap width. `line_px` is the height of one line, and a value below 1 is treated as 1. `dialog_h` is the dialog height.
Text that trims to empty returns 0. The line count comes from `mood_wrap_lines`, and it is at least 1. The height is that count times the line height. The cap is half the dialog, or one line when the dialog height is not positive. A cap below one line becomes one line. Taller text is cut to that cap, and the rest scrolls.

### mood_slide_next

<a id="function-mood_slide_next"></a>

```rust
fn mood_slide_next(h: i32, target: i32) -> i32
```

The function `mood_slide_next` moves the rising mood pane one step toward its target height.
`h` is the current height. `target` is the height the pane is opening or closing toward. Equal values return `h`.
The step is a quarter of the remaining span, and it is at least 12. Opening adds that step. Closing subtracts it. A step that would pass the target returns the target.

### advance_mood_slide

<a id="function-advance_mood_slide"></a>

```rust
fn advance_mood_slide(slide: i32, target: i32) -> i32
```

The function `advance_mood_slide` advances the rising pane and hides it after it shuts.
`slide` is the current height. `target` is the wanted height. The value `-1` means the pane is hidden.
A target of 0 leaves a hidden pane hidden. Any other current height steps toward 0 through `mood_slide_next`, and a step that lands on 0 returns `-1`.
A target above 0 treats a hidden pane as height 0 and then steps toward that target.

### mood_spin_line

<a id="function-mood_spin_line"></a>

```rust
fn mood_spin_line(busy: bool, elapsed_ms: u64) -> Option<String>
```

The function `mood_spin_line` returns the dialog clock while a mood reply is in flight.
`busy` is that in-flight flag. `elapsed_ms` is the time since the send. A quiet send returns `None`.
A busy send returns one glyph from `think_frame`, a space, and the word `thinking`. The canvas card stays quiet while this line is showing.

### mood_spin_clock

<a id="function-mood_spin_clock"></a>

```rust
fn mood_spin_clock(elapsed_ms: u64) -> String
```

The function `mood_spin_clock` returns the elapsed clock for a mood reply that is in flight.
`elapsed_ms` is the time since that send. The text comes from `think_clock`. The form is `m:ss` until an hour, and then `h:mm:ss`.

### clear_ask_drops

<a id="function-clear_ask_drops"></a>

```rust
fn clear_ask_drops(choice: ClearChoice) -> bool
```

The function `clear_ask_drops` reports whether a clear-mood choice drops the saved string.
`choice` is the [`ClearChoice`](chrome/enum.ClearChoice.md). `Yes` returns true. `Stay`, `No`, `Escape`, and `Close` return false.

### mood_choice_size

<a id="function-mood_choice_size"></a>

```rust
fn mood_choice_size(text: egui::Vec2, pad: egui::Vec2, em: f32) -> egui::Vec2
```

The function `mood_choice_size` returns the size of one Yes or No button.
`text` is the measured word. `pad` is the button padding. `em` is the width of the letter M. An `em` of 0 or less is treated as 1.
The width is the word, the padding on both sides, and two of those ems. The height is the word plus the padding on both sides.

### button_ink

<a id="function-button_ink"></a>

```rust
fn button_ink(ui: &egui::Ui, rect: egui::Rect, rest: egui::Color32) -> egui::Color32
```

The function `button_ink` returns the ink for a button that paints its own words.
`ui` is the panel. `rect` is the button. `rest` is the color used when the pointer is elsewhere. A pointer inside `rect` returns the emerald hover ink. Any other pointer returns `rest`.

### mark_button

<a id="function-mark_button"></a>

```rust
fn mark_button(selected: bool, label: &str) -> egui::Button<'_>
```

The function `mark_button` builds one I, E, P, or O button.
`selected` is true when that letter is the card's current mark. `label` is the letter. The button is a selectable button, so the face shows the selected state.
The button carries the class `aetheris.mark`. Hover ink still paints the word, selected or not. The button is not a toggle group.

### seat_mark_button

<a id="function-seat_mark_button"></a>

```rust
fn seat_mark_button(
    ui: &mut egui::Ui,
    index: usize,
    label: &str,
    selected: bool,
) -> egui::Response
```

The function `seat_mark_button` paints one letter on one cell and returns the response.
`ui` is the cell. `index` is the cell's place in the deck. `label` is the letter. `selected` is true when that letter is the current mark.
The persistent id is `aetheris.mark`, the index, and the letter, so a new turn does not move the click. The scope uses no item spacing. The button takes a click and does not take focus.

### mark_pressed

<a id="function-mark_pressed"></a>

```rust
fn mark_pressed(response: &egui::Response) -> bool
```

The function `mark_pressed` reports a primary click on one I, E, P, or O letter.
`response` is the button response from `seat_mark_button`. A primary pointer click returns true. Focus, Space, and Enter do not count.

### paint_menu_ink

<a id="function-paint_menu_ink"></a>

```rust
fn paint_menu_ink(style: &mut egui::Style)
```

The function `paint_menu_ink` keeps the main-menu words green.
`style` is the egui style for that menu. The inactive, hovered, active, and open widget colors are all set to `MENU_INK`. The green stays at rest and while the pointer is on the menu. Swing is the Java library that draws the other client, and this green matches that menu.

### tip_delay

<a id="function-tip_delay"></a>

```rust
fn tip_delay(on: bool) -> f32
```

The function `tip_delay` returns how long a pointer must rest before a hover tip opens.
`on` is the tip switch. A switch that is on returns 0.55 seconds. A switch that is off returns the largest `f32`, so the wait does not arrive.

### seat_button_hover

<a id="function-seat_button_hover"></a>

```rust
fn seat_button_hover(ctx: &egui::Context, tips_on: bool)
```

The function `seat_button_hover` applies one hover delay and installs the emerald button theme once.
`ctx` is the egui context. `tips_on` is the tip switch. The wanted delay comes from `tip_delay`. When the dark theme's tooltip delay differs, every style is set to that delay.
The theme id is `aetheris-button-hover-ink`. The first call replaces the button theme with [`ButtonHoverInk`](chrome/struct.ButtonHoverInk.md) and stores that the theme is installed. A later call leaves the theme in place.

### add_mood_choice

<a id="function-add_mood_choice"></a>

```rust
fn add_mood_choice(
    ui: &mut egui::Ui,
    text: &str,
    fill: egui::Color32,
    ink: egui::Color32,
) -> egui::Response
```

The function `add_mood_choice` paints one Yes or No button and returns its response.
`ui` is the question row. `text` is the word. `fill` is the button face. `ink` is the word color at rest. The size comes from `mood_choice_size`, using the width of the letter M as one em. The height is at least the style's interact height.
A pointer inside the predicted rectangle, while the row is enabled, paints the word in the emerald hover ink. Any other pointer keeps `ink`. The button uses that word color, the given fill, and the measured minimum size.

### paint_clear_ask

<a id="function-paint_clear_ask"></a>

```rust
fn paint_clear_ask(ctx: &egui::Context, generation: u64, open: &mut bool) -> ClearChoice
```

The function `paint_clear_ask` paints the clear-mood question and returns the choice.
`ctx` is the egui context. `generation` keeps this question on the glass that opened it. `open` is that question's flag. A closed question returns [`ClearChoice::Stay`](chrome/enum.ClearChoice.md#member-stay) and paints nothing.
A foreground area covers the app window and takes click and drag, so a click on the glass behind the question does not dismiss it. Escape with no modifiers closes the question and returns [`ClearChoice::Escape`](chrome/enum.ClearChoice.md#member-escape). Enter is not a clear.
The window title is `Clear mood`. The window is centered, and it does not collapse, resize, or scroll. The label is `Clear the Mood Setting?`. Yes uses the green face. No uses the red face. A primary click on Yes returns [`ClearChoice::Yes`](chrome/enum.ClearChoice.md#member-yes). A primary click on No returns [`ClearChoice::No`](chrome/enum.ClearChoice.md#member-no). Each button shows its hover tip.
Closing the window sets `open` to false and returns [`ClearChoice::Close`](chrome/enum.ClearChoice.md#member-close). Yes and No also set `open` to false. A question that is still waiting returns [`ClearChoice::Stay`](chrome/enum.ClearChoice.md#member-stay).

### mood_owns

<a id="function-mood_owns"></a>

```rust
fn mood_owns(wires: &[[u8; 16]], cell: &[u8; 16]) -> bool
```

The function `mood_owns` reports whether a wire id belongs to the mood glass.
`wires` is the list kept on the [`MoodDesk`](chrome/struct.MoodDesk.md). `cell` is the id on an incoming frame. A list that contains that id returns true. Any other id returns false.

### take_mood_line

<a id="function-take_mood_line"></a>

```rust
fn take_mood_line(desk: &mut MoodDesk) -> bool
```

The function `take_mood_line` takes the typed mood line when Enter is accepted.
`desk` is the [`MoodDesk`](chrome/struct.MoodDesk.md). A closed glass returns false. A send already in flight returns false and keeps the typed line. A line that trims to empty returns false and stays in the field.
Any other line is removed from the field, the try count is set to 0, and the line is appended as a user turn. The function then returns true.

### begin_mood

<a id="function-begin_mood"></a>

```rust
fn begin_mood(cards: &[TurnCard], focus: usize, mux: bool) -> MoodBegin
```

The function `begin_mood` decides whether the mood glass may open.
`cards` is the deck. `focus` is the focused index. `mux` is true when the multiplexed channel has answered. The Grok document is an empty in-memory document, so no burner-cloud box is checked.
The decision is `begin_mood_doc`. A burner card on a cloud model stays closed. A channel that has not answered stays closed.

### begin_mood_doc

<a id="function-begin_mood_doc"></a>

```rust
fn begin_mood_doc(
    cards: &[TurnCard],
    focus: usize,
    mux: bool,
    doc: &crate::model::GrokDoc,
) -> MoodBegin
```

The function `begin_mood_doc` decides whether the mood glass may open for one document.
`cards` is the deck. `focus` is the focused index. `mux` is true when the multiplexed channel has answered. `doc` is the Grok settings document.
A missing card returns [`MoodBegin::NoCell`](chrome/enum.MoodBegin.md#member-nocell). A card that `burner_blocks` refuses returns [`MoodBegin::Burner`](chrome/enum.MoodBegin.md#member-burner). A checked burner-cloud box on a Grok card allows that hop, because `burner_blocks` then returns false. A quiet channel returns [`MoodBegin::NoMux`](chrome/enum.MoodBegin.md#member-nomux). Any other card returns [`MoodBegin::Open`](chrome/enum.MoodBegin.md#member-open).

### mood_shape_frame

<a id="function-mood_shape_frame"></a>

```rust
fn mood_shape_frame(
    id: &[u8; 16],
    card: &TurnCard,
    session: &str,
    turns: &[MoodTurn],
) -> Option<Vec<u8>>
```

The function `mood_shape_frame` builds the channel A body for one mood-shaping send.
`id` is the mood wire id. `card` is the selected cell. `session` is the saved mood string. `turns` is the glass chat. The Grok entry is absent. The pool boot id is empty.
The body comes from `mood_shape_tuned`. The control text is the first system line. A trimmed field adds the opener, that field, and the closer. An empty field leaves those three lines off the frame. The mood switch is not read.

### mood_shape_tuned

<a id="function-mood_shape_tuned"></a>

```rust
fn mood_shape_tuned(
    id: &[u8; 16],
    card: &TurnCard,
    session: &str,
    turns: &[MoodTurn],
    entry: Option<&crate::model::GrokEntry>,
    pool_boot: &str,
) -> Option<Vec<u8>>
```

The function `mood_shape_tuned` builds the channel A body for one tuned mood-shaping send.
`id` is the mood wire id. `card` is the selected cell. `session` is the saved mood string. `turns` is the glass chat. `entry` is the Grok settings for that cell. `pool_boot` is the boot id.
The first line is the system text `MOOD_SHAPE_SYSTEM`. A `session` that trims to non-empty adds three more system lines: the opener, that trimmed field, and the closer. Each chat turn is then added with its own role and text. Every line has `head` false.
The tune comes from `crate::model::mood_tune` of the card target, the card's ephemeral flag, `entry`, and `pool_boot`. The bytes come from `execute_tuned`. A frame that cannot be built returns `None`.

### mood_char

<a id="function-mood_char"></a>

```rust
fn mood_char(text: &str, index: usize) -> Option<(usize, char)>
```

The function `mood_char` returns the character at a byte index and that character's UTF-8 length.
`text` is the reply. `index` is the byte index. An index that is past the end, or that is not on a character boundary, returns `None`.

### mood_skip

<a id="function-mood_skip"></a>

```rust
fn mood_skip(text: &str, mut index: usize, keep: impl Fn(char) -> bool) -> usize
```

The function `mood_skip` advances a byte index across characters that should be ignored.
`text` is the reply. `index` is the start. `keep` reports whether one character is skipped. The index moves by each kept character's UTF-8 length. The first character that `keep` rejects stops the scan, and that index is returned.

### mood_word

<a id="function-mood_word"></a>

```rust
fn mood_word(text: &str, index: usize, word: &str) -> bool
```

The function `mood_word` reports whether a word sits at a byte index.
`text` is the reply. `index` is the start. `word` is the expected text. The slice must be long enough. The comparison ignores ASCII case. A short tail returns false.

### mood_mark_char

<a id="function-mood_mark_char"></a>

```rust
fn mood_mark_char(ch: char) -> bool
```

The function `mood_mark_char` reports a character that may sit between a mood key and its colon.
`ch` is that character. Whitespace returns true. `*` returns true. `_` returns true. A backtick returns true. Any other character returns false.

### mood_clean

<a id="function-mood_clean"></a>

```rust
fn mood_clean(raw: &str) -> String
```

The function `mood_clean` turns one raw mood value into the stored token string.
`raw` is that value. A carriage return becomes a space. A newline becomes a space. The result is trimmed.
A matching pair of wrappers is removed from both ends, and the result is trimmed again, until the pair is gone. The pairs are quotes, single quotes, backticks, stars, underscores, and angle brackets. Leading and trailing stars, underscores, and backticks are then removed one at a time.
The words `the string`, in any ASCII case, return an empty string. Any other result is returned as cleaned.

### mood_next_line

<a id="function-mood_next_line"></a>

```rust
fn mood_next_line(text: &str, mut index: usize) -> String
```

The function `mood_next_line` returns the next physical line after a mood key.
`text` is the reply. `index` is the byte index at the end of the key line. One leading carriage return is skipped. One leading newline is skipped. The result runs until the next newline or carriage return.
An empty span returns an empty string.

### mood_key_at

<a id="function-mood_key_at"></a>

```rust
fn mood_key_at(text: &str, start: usize) -> Option<(usize, String)>
```

The function `mood_key_at` finds the first mood key at or after a byte index.
`text` is the reply. `start` is that index. The key is the word `mood`, ignoring ASCII case. Whitespace and then the word `string` may follow. Marks from `mood_mark_char` and whitespace may follow that.
The next character must be `:` or the fullwidth colon U+FF1A. The value runs to the end of that line and is cleaned with `mood_clean`. An empty value uses `mood_clean` of the following line from `mood_next_line`.
The returned index is the end of the match. A reply with no such key returns `None`. A `mood` that is not followed by a colon is not a match, and the scan continues one character later.

### mood_fence_at

<a id="function-mood_fence_at"></a>

```rust
fn mood_fence_at(text: &str, start: usize) -> Option<(usize, String)>
```

The function `mood_fence_at` finds the first fenced mood block at or after a byte index.
`text` is the reply. `start` is that index. The opening mark is three backticks. Whitespace and then the word `mood` must follow, ignoring ASCII case. Spaces and tabs may follow that word. One carriage return may follow. The next character must be a newline.
The body runs until the next three backticks. The value is `mood_clean` of that body. The returned index is just past the closing backticks. A fence with no close is not a match, and the scan continues one character later. No fence returns `None`.

### mood_marks

<a id="function-mood_marks"></a>

```rust
fn mood_marks(row: &str) -> bool
```

The function `mood_marks` reports whether one chat row carries a mood key.
`row` is that row. A row that `mood_key_at` can read from the first byte returns true. An empty value still counts. A row with no key returns false.

### mood_pluck

<a id="function-mood_pluck"></a>

```rust
fn mood_pluck(text: &str) -> String
```

The function `mood_pluck` returns the last mood value in a reply.
`text` is that reply. Each non-empty value from `mood_key_at` replaces the one before it. A fenced value from `mood_fence_at` replaces that result when the fence ends at or after the key, or when no key value was kept.
An empty value is ignored. The words `the string` do not count, because `mood_clean` turns them into an empty string. A reply with no value returns an empty string.

### mood_mark_word

<a id="function-mood_mark_word"></a>

```rust
fn mood_mark_word(role: &str) -> &str
```

The function `mood_mark_word` returns the label painted beside one mood-glass turn.
`role` is the turn role. The role `user` returns `you`. The role `assistant` returns `assistant`. Any other role returns that role unchanged.

### mood_paint_rows

<a id="function-mood_paint_rows"></a>

```rust
fn mood_paint_rows(turns: &[MoodTurn]) -> Vec<MoodPaintRow>
```

The function `mood_paint_rows` splits the mood chat into one painted row per physical line.
`turns` is the chat. Each turn's label comes from `mood_mark_word`. The role `user` paints as `you`. The role `assistant` paints as `assistant`.
The turn text is split on newlines. A mood key on an assistant row sets `hot`. A user row is not hot. An assistant row with no mood key is not hot.

### mood_piece_cell

<a id="function-mood_piece_cell"></a>

```rust
fn mood_piece_cell(piece: &ScopePiece) -> Option<[u8; 16]>
```

The function `mood_piece_cell` returns the cell id carried by one scope piece.
`piece` is that [`ScopePiece`](amwp/enum.ScopePiece.md). A reply, an end, a fault, a delta, and a later piece return their cell id. An init piece returns `None`.

### mood_take_reply

<a id="function-mood_take_reply"></a>

```rust
fn mood_take_reply(desk: &mut MoodDesk, cell: [u8; 16], body: &[u8]) -> MoodIntake
```

The function `mood_take_reply` stores one mood reply and pulls a mood line out of it.
`desk` is the [`MoodDesk`](chrome/struct.MoodDesk.md). `cell` is the wire id. `body` is the reply bytes. A closed glass returns [`MoodIntake::Quiet`](chrome/enum.MoodIntake.md#member-quiet). A wire id that is not the current send returns [`MoodIntake::Quiet`](chrome/enum.MoodIntake.md#member-quiet).
The in-flight flag is cleared. The send instant is cleared. The bytes are read as lossy UTF-8 and appended as an assistant turn. A non-empty value from `mood_pluck` replaces the draft, resets the try count, and returns [`MoodIntake::Quiet`](chrome/enum.MoodIntake.md#member-quiet).
An empty value adds 1 to the try count, stopping at the maximum `u32`. A count below 3 appends the format reminder as a user turn and returns [`MoodIntake::Resend`](chrome/enum.MoodIntake.md#member-resend). The third miss returns a slide whose body is `keyword only. mood line`, whose failure is not hard, and whose close flag is false.

### mood_take_fault

<a id="function-mood_take_fault"></a>

```rust
fn mood_take_fault(desk: &mut MoodDesk, cell: [u8; 16], body: &[u8]) -> MoodIntake
```

The function `mood_take_fault` turns one mood-wire fault into a slide notice.
`desk` is the [`MoodDesk`](chrome/struct.MoodDesk.md). `cell` is the wire id. `body` is the fault bytes. A closed glass returns [`MoodIntake::Quiet`](chrome/enum.MoodIntake.md#member-quiet). A wire id that is not the current send returns [`MoodIntake::Quiet`](chrome/enum.MoodIntake.md#member-quiet).
The bytes are read as lossy UTF-8 and parsed with `parse_miss`. The notice starts with `keyword only.`. A non-empty reason is appended after a space. A non-empty message that differs from the reason is appended after a space. `hard_miss` sets the hard flag.
The slide closes the glass. The in-flight flag is left as it was.

### intake_mood

<a id="function-intake_mood"></a>

```rust
fn intake_mood(desk: &mut MoodDesk, piece: &ScopePiece) -> MoodIntake
```

The function `intake_mood` consumes a frame whose id belongs to the mood glass.
`desk` is the [`MoodDesk`](chrome/struct.MoodDesk.md). `piece` is the incoming [`ScopePiece`](amwp/enum.ScopePiece.md). A piece with no cell id returns [`MoodIntake::Pass`](chrome/enum.MoodIntake.md#member-pass). A cell id that `mood_owns` rejects also returns [`MoodIntake::Pass`](chrome/enum.MoodIntake.md#member-pass). The cell path does not see an id the glass owns.
A reply is handled by `mood_take_reply`. A fault is handled by `mood_take_fault`. A delta, an end, and a later piece return [`MoodIntake::Quiet`](chrome/enum.MoodIntake.md#member-quiet). An init piece returns [`MoodIntake::Pass`](chrome/enum.MoodIntake.md#member-pass).

### accept_thought

<a id="function-accept_thought"></a>

```rust
fn accept_thought(log: &mut ThinkLog, cell: [u8; 16], model: &str, text: &str)
```

The function `accept_thought` appends kind `t` text onto the thinking log.
`log` is the [`ThinkLog`](chrome/struct.ThinkLog.md). `cell` is the sixteen-byte id. `model` is the model word used for the header. `text` is the new thought text. Empty text returns without writing.
The header is `think_tag_line` of `think_tag_word` for that model. The same open cell and the same header keep the open block. A different cell or a different model pushes a new block, stores the cell, and marks the log open.
The text is appended to the last block's body.
// @aa-sdd PK-1166 docs/specs/java/org/aetheris/swing/swing.spec.md FINESSE:f-210

### close_thought

<a id="function-close_thought"></a>

```rust
fn close_thought(log: &mut ThinkLog)
```

The function `close_thought` closes the open thinking block.
`log` is the [`ThinkLog`](chrome/struct.ThinkLog.md). `live` is set to false. The stored blocks stay. A reply, an end, a fault, or Stop is what calls this.

### paint_think_grip

<a id="function-paint_think_grip"></a>

```rust
fn paint_think_grip(
    ui: &mut egui::Ui,
    chrome: &mut ThinkChrome,
    natural: f32,
    panel_h: f32,
    width: f32,
    along_top: bool,
    live: f32,
) -> (bool, Option<i32>, egui::Rect)
```

The function `paint_think_grip` paints the drag row of an open thinking strip and returns what the row did.
`ui` is the strip. `chrome` is the pane state. `natural` is the three-line floor. `panel_h` is the measured panel height. `width` is the row width. `along_top` is true for the VT100 strip. `live` is the height stored when a drag starts.
The row is `width` by 18 pixels and only hovers. The Clear button is the letter `C`, 22 pixels wide, and it sits 4 pixels in from the right. Its face is the sapphire color `pin_show_fill` returns for false, and the pin box does not change that face. The word is white, or emerald while the pointer is on the button. The stroke is white.
The hover tip is the VT100 clear tip when `along_top` is true, and the notebook clear tip otherwise. The drag region stops 1 pixel short of `C`. A region wider than 1 pixel accepts a drag. A narrower region does not.
A drag that starts stores `live` as the height and sets the slide to `-1`. A drag moves the height through `drag_vt_think_height` when the strip is on top, and through `drag_think_height` otherwise. A drag that stops keeps the rounded height when it sits from 48 through 2000. A hover sets the vertical resize cursor. The grip tip matches the strip.
A grey line is painted from 16 pixels in from the left to 6 pixels before `C`, when that span is open. The result is whether Clear was clicked, the kept height, and the Clear rectangle.

### paint_think_folded_row

<a id="function-paint_think_folded_row"></a>

```rust
fn paint_think_folded_row(
    ui: &mut egui::Ui,
    row: egui::Rect,
    chrome: &mut ThinkChrome,
    natural: f32,
    panel_h: f32,
    along_top: bool,
    live: f32,
) -> (bool, bool, Option<i32>, egui::Rect)
```

The function `paint_think_folded_row` paints one folded thinking row.
`ui` is the strip. `row` is the row rectangle. `chrome` is the pane state. `natural` is the three-line floor. `panel_h` is the measured panel height. `along_top` is true for the VT100 strip. `live` is the height stored when a drag starts.
Left to right, the row holds the unfold mark, the drag, `C`, and Close. The mark is `think_mark` of a folded strip, which is `^`. A click calls `fold_think`. Close calls `close_think`. `C` uses the same sapphire face and the same clear tip as the open grip. The three buttons keep their size. A wider or narrower window changes only the drag.
The drag starts 1 pixel to the right of the fold button and stops 1 pixel short of `C`. Its height change, its kept range of 48 through 2000, and its resize cursor match `paint_think_grip`. A grey line joins the fold button to `C` when there is room. An open strip does not call this.
The result is whether Clear was clicked, whether Close was clicked, the kept height, and the Clear rectangle.

### seat_vt_console

<a id="function-seat_vt_console"></a>

```rust
fn seat_vt_console(ui: &mut egui::Ui, origin: egui::Pos2, right: f32, clear_bottom: f32)
```

The function `seat_vt_console` places the SSH console three pixels under the Clear button.
`ui` is the pane. `origin` is the top-left of the strip. `right` is the strip's right edge. `clear_bottom` is the bottom of Clear. The console top is that bottom plus 3, and it is cut to the available floor.
The cursor advances across a reserved rectangle that ends one item-spacing above the console top, so the next control does not take a hit on `C`. The gap under Clear is filled with the plate color when the gap has height.

### think_tail_pin

<a id="function-think_tail_pin"></a>

```rust
fn think_tail_pin(seen: usize, now: usize) -> bool
```

The function `think_tail_pin` reports whether the thinking scroll should jump to the tail.
`seen` is the buffer length from the previous paint. `now` is the length this paint. A longer buffer returns true, and the scroll pins the tail. The same length returns false, and a manual scroll is left alone.

| | |
| --- | --- |
| **Specification** | PK-1073 docs/specs/common/formal-plan.spec.md FINESSE:f-219 |

### think_buffer_len

<a id="function-think_buffer_len"></a>

```rust
fn think_buffer_len(blocks: &[ThinkBlock]) -> usize
```

The function `think_buffer_len` counts the bytes in the thinking log.
`blocks` is the list of [`ThinkBlock`](chrome/struct.ThinkBlock.md) values. Each block adds the length of its tag and the length of its body. A stream that appends to the open block still grows this count.

| | |
| --- | --- |
| **Specification** | PK-1073 docs/specs/common/formal-plan.spec.md FINESSE:f-219 |

### paint_think_row

<a id="function-paint_think_row"></a>

```rust
fn paint_think_row(
    ui: &mut egui::Ui,
    blocks: &[ThinkBlock],
    chrome: &mut ThinkChrome,
    natural: f32,
    panel_h: f32,
    width: f32,
    along_top: bool,
    cap: f32,
) -> bool
```

The function `paint_think_row` paints the fold button, Close, and the thinking text.
`ui` is the strip. `blocks` is the log. `chrome` is the pane state. `natural` is the three-line floor. `panel_h` is the measured panel height. `width` is the row width. `along_top` is true for the VT100 strip. `cap` limits the text height.
The fold button shows `think_mark` for the current fold, and a click calls `fold_think`. Close sits on the right, and a click calls `close_think`. The tips match the VT100 strip or the notebook strip.
Folded chrome paints no text. Open chrome paints a scroll whose height is `think_scroll_height`, cut to `cap`, when that height is at least 1. The field is the dark `THINK_FIELD`. The scroll sticks to the bottom. A longer buffer, from `think_tail_pin`, also jumps to the tail with no animation.
Each block paints its tag in cyan and, when the body is not empty, the body in the lighter sapphire. A blank monospace row separates blocks. The notebook strip adds one more blank row so the latest line stays above the footer. The VT100 strip does not add that row. The function returns whether Close was clicked.

### button_outer_height

<a id="function-button_outer_height"></a>

```rust
fn button_outer_height(ui: &egui::Ui) -> f32
```

The function `button_outer_height` returns the painted height of a stock button.
`ui` is the column. The column font is the override. `egui::TextStyle::Button` ignores that override, so a lane sized from that style is short and the clip cuts the border.
The height is the measured letter M plus the button padding above and below. The stroke is taken from that pad, so it does not add height. The result is at least the style's interact height.

| | |
| --- | --- |
| **Specification** | PK-1096 docs/specs/common/formal-plan.spec.md FINESSE:f-183 |

### button_lane_height

<a id="function-button_lane_height"></a>

```rust
fn button_lane_height(ui: &egui::Ui) -> f32
```

The function `button_lane_height` returns the lane that holds one stock button.
`ui` is the column. The height is `button_outer_height` plus 2 pixels of clearance above the button and 2 pixels below it.

### button_lane_clip

<a id="function-button_lane_clip"></a>

```rust
fn button_lane_clip(lane: egui::Rect, parent: egui::Rect) -> egui::Rect
```

The function `button_lane_clip` returns the clip rectangle for one button lane.
`lane` is the button's rectangle. `parent` is the clip it must stay inside. The button keeps its own rectangle. The clip starts as the parent.
The left edge moves to 2 pixels left of the lane when the parent starts further left. The right edge moves to 2 pixels right of the lane when the parent ends further right. The rounded border is therefore not the clip edge. That right limit keeps an overflowing master button off the combo on the right.
A clip whose right edge would pass its left edge sets the right edge equal to the left.

| | |
| --- | --- |
| **Specification** | PK-1096 docs/specs/common/formal-plan.spec.md FINESSE:f-183 |

### folded_control_height

<a id="function-folded_control_height"></a>

```rust
fn folded_control_height(ui: &egui::Ui) -> f32
```

The function `folded_control_height` returns the height of one folded thinking row.
`ui` is the column. The height is the Close button plus its clearance, from `button_lane_height`. It is also at least the 18-pixel grip plus 2 pixels of clearance above and below.
A row sized from `egui::TextStyle::Button` ignores the column font and clips Close. This height does not use that style.

| | |
| --- | --- |
| **Specification** | F-159 |
| **Specification** | PK-1096 docs/specs/common/formal-plan.spec.md FINESSE:f-183 |
| **Specification** | PK-1286 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md |

### notebook_strip_shown

<a id="function-notebook_strip_shown"></a>

```rust
fn notebook_strip_shown(ui: &egui::Ui, chrome: &ThinkChrome, panel_h: f32) -> (f32, f32)
```

The function `notebook_strip_shown` returns the shown height and the full height of the notebook strip.
`ui` is the column. `chrome` is the pane state. `panel_h` is the measured panel height. A closed pane returns `(0, 0)`.
A folded pane's full height is `folded_control_height`. An open pane's full height is the 18-pixel grip, the item gap, and the taller of the scroll height and the button lane. The scroll height comes from `think_scroll_height`.
A slide below 0 shows the full height. Any other slide shows that slide, clamped from 0 to the full height.

### paint_think_strip

<a id="function-paint_think_strip"></a>

```rust
fn paint_think_strip(
    ui: &mut egui::Ui,
    blocks: &[ThinkBlock],
    chrome: &mut ThinkChrome,
    panel_h: f32,
    along_top: bool,
    salt: u64,
) -> Option<ThinkStrip>
```

The function `paint_think_strip` paints one thinking strip and returns what the paint did.
`ui` is the pane. `blocks` is the log. `chrome` is the pane state. `panel_h` is the measured panel height. `along_top` is true for the VT100 strip. `salt` keeps this strip's widget ids apart from another strip. A closed pane returns `None`.
The notebook strip sits on the buttons and rises. The VT100 strip sits on the top. `along_top` seats the grip on the south edge and grows downward. Both strips fill their rectangle with the plate color. A slide of 0 or more advances through `rise_think` and asks for another paint after 16 milliseconds.
On the VT100 strip, the shown height is cut to the available height. A folded strip paints `paint_think_folded_row` and then places the console with `seat_vt_console`. An open strip paints the text above the grip, paints `paint_think_grip` on the south edge, and places the console under Clear. The plate extends 24 pixels below the host, and it stops at the available bottom.
On the notebook strip, a folded row is raised by 4 pixels and its height is `folded_control_height`. An open row is not raised. The grip is painted first. The text row is painted under it. During a rise, the drag uses the visible scroll height. After the rise settles, the drag uses the full scroll height.

### reply_divider_band

<a id="function-reply_divider_band"></a>

```rust
fn reply_divider_band(input: egui::Rect, reply: egui::Rect) -> Option<egui::Rect>
```

The function `reply_divider_band` returns the row already between the input pane and the reply pane.
`input` is the input rectangle. `reply` is the reply rectangle. A reply whose top is not below the input bottom, allowing half a pixel, returns `None`.
Any other pair returns the rectangle from the input's bottom to the reply's top. The left edge is the input's left. The right edge is the wider of the two right edges.

### console_model_home

<a id="function-console_model_home"></a>

```rust
fn console_model_home(console: bool, noted: Option<u64>, focused: u64) -> Option<u64>
```

The function `console_model_home` returns the pane that opened the console Model Config.
`console` is true when the modal belongs to the console. `noted` is the pane id stored when the modal opened. `focused` is the pane that has focus now. Closing that modal returns the keyboard to the pane this function names.
A cell modal passes false for `console` and returns `None`. A missing note returns the focused pane. A stored note returns that note.

### console_event_filter

<a id="function-console_event_filter"></a>

```rust
fn console_event_filter() -> egui::EventFilter
```

The function `console_event_filter` returns the keys the console keeps while it holds the keyboard.
Escape stays on the console. The horizontal arrow keys stay on the console. The vertical arrow keys stay on the console. Tab is not kept, so Tab still moves focus.

### append_scope

<a id="function-append_scope"></a>

```rust
fn append_scope(buf: &mut String, chunk: &[u8])
```

The function `append_scope` appends one scope body onto the gathered text.
`buf` is that text. `chunk` is the body bytes, read as lossy UTF-8. An empty chunk writes nothing. A buffer that is not empty and does not end with a newline gains a newline first.
The chunk is then appended, so each chunk stays on its own lines.

### unesc_fact

<a id="function-unesc_fact"></a>

```rust
fn unesc_fact(raw: &str) -> String
```

The function `unesc_fact` unescapes one fact word.
`raw` is the text after the colon. The escapes match `AmwpToon.unesc`. A backslash followed by `n` becomes a newline. A backslash followed by a backslash becomes one backslash. Any other backslash is copied. Every other character is copied.

### seat_facts

<a id="function-seat_facts"></a>

```rust
fn seat_facts(body: &[u8]) -> Option<CanvasFacts>
```

The function `seat_facts` reads `air-gap` and `num-ctx` from one init body.
`body` is that body, read as lossy UTF-8. Each line is matched by its prefix. `air-gap:` is unescaped with `unesc_fact`, and an empty word is ignored. `num-ctx:` must parse as an `i32`.
`managed-tokens:` and `head-count:` also parse as an `i32`. A missing line of either kind stays zero. A line that does not parse is ignored. This function does not read a yaml key for either count.
Both `air-gap` and `num-ctx` must be present. A missing one returns `None`. Any other body returns the [`CanvasFacts`](chrome/struct.CanvasFacts.md).

### accept_scope

<a id="function-accept_scope"></a>

```rust
fn accept_scope(facts: &mut Option<CanvasFacts>, text: &mut String, piece: &ScopePiece)
```

The function `accept_scope` keeps an init body's facts, or keeps the body as text.
`facts` is the stored [`CanvasFacts`](chrome/struct.CanvasFacts.md). `text` is the gathered text. `piece` is the [`ScopePiece`](amwp/enum.ScopePiece.md). An init body that `seat_facts` accepts replaces `facts`. An init body that does not seat is appended with `append_scope`.
A later piece appends its body. A reply, an end, a fault, and a delta write nothing here.

### turn_label

<a id="function-turn_label"></a>

```rust
fn turn_label(index: usize) -> String
```

`Turn 1` for the first card. The number grows with the plate.

### mint_cell

<a id="function-mint_cell"></a>

```rust
fn mint_cell(n: u64) -> [u8; 16]
```

The function `mint_cell` builds a 16-byte cell id from a counter.
The counter `n` is a `u64`. The first eight bytes are `n` in big-endian order. The last eight bytes are zero.

### is_merge

<a id="function-is_merge"></a>

```rust
fn is_merge(group: u64, column: u32) -> bool
```

The function `is_merge` reports whether a card is the merge of a group.
The value `group` is the group id. The value `column` is the column in that group.

A merge has a group other than zero and a column of zero. The function returns false for every other pair.

### is_member

<a id="function-is_member"></a>

```rust
fn is_member(group: u64, column: u32) -> bool
```

The function `is_member` reports whether a card belongs to a group as a member.
The value `group` is the group id. The value `column` is the column in that group.

A member has a group other than zero and a column above zero. A merge returns false because its column is zero. An ordinary card returns false because its group is zero.

### merge_input_models

<a id="function-merge_input_models"></a>

```rust
fn merge_input_models(
    groups: &[u64],
    columns: &[u32],
    targets: &[String],
    merge_at: usize,
) -> String
```

The function `merge_input_models` joins the short model names of the input cards in one merge.
The slices `groups`, `columns`, and `targets` are parallel rows in index order. The index `merge_at` is the merge card. An index past `groups` returns an empty string. A group id of zero returns an empty string.

The scan uses the shortest of the three slices. A row is kept when its group matches the merge and its column is not zero. `short_model_name` supplies the name. An empty name is skipped.
The separator is ` | `. The names stay in index order. The merge card is omitted because its column is zero.
// @aa-sdd PK-1167 docs/specs/java/org/aetheris/swing/swing.spec.md FINESSE:f-211

### member_width

<a id="function-member_width"></a>

```rust
fn member_width(bar: f32) -> f32
```

The function `member_width` returns the width of one member column.
The value `bar` is the top-bar width in pixels. The result adds [`MEMBER_EAST`](#constant-member_east). That constant is ten pixels.

### gather_slots

<a id="function-gather_slots"></a>

```rust
fn gather_slots(groups: &[u64], columns: &[u32]) -> Vec<GatherSlot>
```

The function `gather_slots` builds the visual order of the cards.
The slices `groups` and `columns` are parallel. The scan stops at the shorter slice. A card whose group is zero becomes [`GatherSlot::Card`](chrome/enum.GatherSlot.md#member-card) and keeps its place.

The first card of a nonzero group anchors one [`GatherSlot::Block`](chrome/enum.GatherSlot.md#member-block). Members of that group are collected in index order. The first card with column zero is the merge. A later card with column zero in that same group is not stored as the merge.
Every later card of that group is marked painted. The group is not emitted again. The merge is stored apart from the member list, and the row paints the members before the merge.

### card_armed

<a id="function-card_armed"></a>

```rust
fn card_armed(index: usize, focus: usize, groups: &[u64], columns: &[u32]) -> bool
```

The function `card_armed` reports whether a card takes the armed edge.
The index `index` is the card under test. The index `focus` is the armed card. The slices `groups` and `columns` are parallel.

The focused index itself returns true. That check happens before the length tests. A focus past `groups`, an index past `groups`, or a focus past `columns` returns false.
A focus that is not a merge returns false for every other card. A focus that is a merge returns true for every card whose group is that same nonzero group.

### card_edge

<a id="function-card_edge"></a>

```rust
fn card_edge(mark: Mark, armed: bool) -> egui::Color32
```

The function `card_edge` returns the border color of one card.
The mark `mark` is the card letter. The flag `armed` is true when the card is armed.

An armed card returns [`EDGE_ARMED`](#constant-edge_armed). That color is `0x2e86ff`. An excluded card that is not armed returns [`EDGE_GHOST`](#constant-edge_ghost). That color is `0x404040`. Any other card returns [`EDGE_QUIET`](#constant-edge_quiet). That color is `0x3a3a3a`.

### pinned_edge

<a id="function-pinned_edge"></a>

```rust
fn pinned_edge(
    mark: Mark,
    armed: bool,
    pin_on: bool,
    ephemeral: bool,
    prompt: &str,
    reply: &str,
    needle: &str,
) -> egui::Color32
```

The function `pinned_edge` returns the border color while a pin may recolor the card.
The mark `mark` is the card letter. The flag `armed` is true when the card is armed. The flag `pin_on` is true when the pin box is on. The flag `ephemeral` is true on a burner card. The strings `prompt` and `reply` are the card text. The string `needle` is the pin field.

An armed card returns [`EDGE_ARMED`](#constant-edge_armed) before the pin is considered. A pin that is on, on a card that is not a burner, calls `pin_match`. A match returns [`EDGE_MATCH`](#constant-edge_match), the color `0x3cb371`. A miss returns [`EDGE_MISS`](#constant-edge_miss), the color `0x222222`. An empty pin field has no tokens, so it misses.
A pin that is off uses `card_edge`. A burner also uses `card_edge`. The call into `pin_match` passes false for the burner flag because a burner already took the other branch.

### think_frame

<a id="function-think_frame"></a>

```rust
fn think_frame(elapsed_ms: u64) -> &'static str
```

The function `think_frame` returns the spinner glyph for one moment.
The count `elapsed_ms` is the milliseconds since the run started. The four glyphs, in order, are a vertical bar, a slash, a hyphen, and a backslash.

The index is `elapsed_ms` divided by [`THINK_FRAME_MS`](#constant-think_frame_ms). That constant is 120. The index wraps over the four glyphs.

### think_line

<a id="function-think_line"></a>

```rust
fn think_line(running: bool, elapsed_ms: u64) -> Option<String>
```

The function `think_line` returns the spinner line for a run that is in flight.
The flag `running` is true while the card is running. The count `elapsed_ms` is passed to `think_frame`.

A card that is not running returns `None`. A running card returns the glyph, a space, and the word `thinking`. That word is [`THINK_WORD`](#constant-think_word).

### think_clock

<a id="function-think_clock"></a>

```rust
fn think_clock(elapsed_ms: u64) -> String
```

The function `think_clock` formats elapsed time for the spinner.
The count `elapsed_ms` is the milliseconds since the run started. Whole seconds are `elapsed_ms` divided by 1000. The remainder under one second is dropped.

A span under one hour is `m:ss`. The minutes are not padded. The seconds are two digits. A span of one hour or more is `h:mm:ss`. The hours are not padded. The minutes and the seconds are two digits.

### two

<a id="function-two"></a>

```rust
fn two(value: u64) -> String
```

The function `two` prints a count with a leading zero when the count is under ten.
The count `value` is that number. A count under 10 is `0` and then the digit. A count of 10 or more is printed in full, with no extra zeros and no cut.

### footer_clock

<a id="function-footer_clock"></a>

```rust
fn footer_clock(cards: &[TurnCard], now: Instant) -> Option<u64>
```

The function `footer_clock` returns the elapsed milliseconds of the last running card.
The slice `cards` is the card list. The instant `now` is the clock this call reads. The search starts at the end of the list. The first card whose `running` flag is true is the card this function uses.

A list with no running card returns `None`. A running card with no `started` instant returns zero. Any other running card returns the milliseconds from `started` to `now`. `saturating_duration_since` keeps that span at zero when `now` is earlier than `started`.

### canvas_land_y

<a id="function-canvas_land_y"></a>

```rust
fn canvas_land_y(content_h: f32, extent_h: f32) -> f32
```

The function `canvas_land_y` returns the viewport offset that puts the last pixel of the column in view.
The height `content_h` is the full column. The height `extent_h` is the visible window. The difference is `content_h` minus `extent_h`.

A negative difference returns zero. A difference of zero or more is returned as it is. Swing `scrollCanvasToEnd` uses this difference.

### drag_weight

<a id="function-drag_weight"></a>

```rust
fn drag_weight(weight: f32, delta_x: f32, span_x: f32, reserved: f32) -> f32
```

The function `drag_weight` returns the canvas share after a horizontal drag.
The share `weight` is the share before the drag. The distance `delta_x` is the drag in pixels. The width `span_x` is the full span. The width `reserved` is the width held back from the span.

The incoming share is clamped from 0 through 1. The room is `span_x` minus `reserved`. A room of zero or less returns that clamped share.
Any other room adds `delta_x` divided by the room. The sum is clamped from 0 through 1. Swing clamps the divider to the span.

### frame_body_height

<a id="function-frame_body_height"></a>

```rust
fn frame_body_height(span_y: f32, spent: f32) -> f32
```

The function `frame_body_height` returns the height left under the tab row.
The height `span_y` is the full frame. The height `spent` is the height the tab row already took, including the gap under it.

A negative `spent` is treated as zero. The result is `span_y` minus that height. The result is never negative. The footer, the split, and the inspector share this height.

### canvas_width

<a id="function-canvas_width"></a>

```rust
fn canvas_width(span_x: f32, weight: f32, reserved: f32) -> f32
```

The function `canvas_width` returns the canvas width in pixels.
The width `span_x` is the full span. The share `weight` is the canvas share. The width `reserved` is the bar and the gaps beside it.

The room is `span_x` minus `reserved`. A room of zero or less returns zero. Any other room is multiplied by `weight` after `weight` is clamped from 0 through 1.

### divider_per_mille

<a id="function-divider_per_mille"></a>

```rust
fn divider_per_mille(weight: f32) -> u16
```

The function `divider_per_mille` returns the canvas share as a per-mille of the span.
The share `weight` is clamped from 0 through 1. The result is that share times 1000, rounded to the nearest integer, and clamped from 0 through 1000.

Swing stores this number as `ui.aetheris.inspector.divider`.

### weight_from_per_mille

<a id="function-weight_from_per_mille"></a>

```rust
fn weight_from_per_mille(per_mille: u16) -> f32
```

The function `weight_from_per_mille` returns a share from a stored per-mille.
The number `per_mille` is that stored count. A count above 1000 is read as 1000. The result is the capped count divided by 1000.

A stored 1000 is the full span. A stored 0 is an empty share.

### open_share

<a id="function-open_share"></a>

```rust
fn open_share(stored: Option<u16>) -> f32
```

The function `open_share` returns the canvas share used when the window opens.
The option `stored` is the saved per-mille. A missing value keeps the open share [`CANVAS_WEIGHT`](#constant-canvas_weight). That share is 0.72.

A stored value is converted with `weight_from_per_mille`.

### divider_span_ready

<a id="function-divider_span_ready"></a>

```rust
fn divider_span_ready(span_x: f32) -> bool
```

The function `divider_span_ready` reports whether the span is wide enough to store a divider ratio.
The width `span_x` is that span. The result is true when `span_x` is at least [`DIVIDER_SPAN_MIN`](#constant-divider_span_min). That floor is 32. Swing skips the ratio while the span is under 32.

### divider_due

<a id="function-divider_due"></a>

```rust
fn divider_due(elapsed_ms: u64) -> bool
```

The function `divider_due` reports whether a divider move is old enough to save.
The count `elapsed_ms` is the milliseconds since the move. The result is true when that count has reached [`DIVIDER_SAVE_MS`](#constant-divider_save_ms). That wait is 400 milliseconds.

### wrap_due

<a id="function-wrap_due"></a>

```rust
fn wrap_due(elapsed_ms: u64) -> bool
```

The function `wrap_due` reports whether a typed column count is old enough to apply.
The count `elapsed_ms` is the milliseconds since the last key. The result is true when that count has reached [`WRAP_TYPE_MS`](#constant-wrap_type_ms). That wait is 800 milliseconds.

### step_columns

<a id="function-step_columns"></a>

```rust
fn step_columns(columns: u32, delta: i32) -> u32
```

The function `step_columns` steps the column count by a signed amount.
The count `columns` is the current count. The amount `delta` is the step. A count under 1 is treated as 1 before the step.

A negative `delta` subtracts the absolute value of `delta`. The subtraction saturates. The result then stops at 1. A `delta` of zero or more adds that amount. The addition saturates instead of wrapping.

### apply_typed_columns

<a id="function-apply_typed_columns"></a>

```rust
fn apply_typed_columns(text: &str) -> TypedWrap
```

The function `apply_typed_columns` reads a typed column count.
The text `text` is the field. The field is trimmed before it is read.

An empty field returns [`TypedWrap::Keep`](chrome/enum.TypedWrap.md#member-keep). Text that is not an `i32` returns [`TypedWrap::Keep`](chrome/enum.TypedWrap.md#member-keep). [`TypedWrap::Keep`](chrome/enum.TypedWrap.md#member-keep) restores the field.
A parsed value under 1 returns [`TypedWrap::Apply`](chrome/enum.TypedWrap.md#member-apply) of 1. That branch covers zero and a negative value. Any other parsed value returns [`TypedWrap::Apply`](chrome/enum.TypedWrap.md#member-apply) of that value as a `u32`.

### wrap_columns_text

<a id="function-wrap_columns_text"></a>

```rust
fn wrap_columns_text(text: &str, columns: u32) -> String
```

The function `wrap_columns_text` wraps text at a column count.
The text `text` is the body. The count `columns` is the width in characters. A count under 1 is treated as 1. A count that does not fit in `usize` is treated as 1.

Each line is split on a newline and wrapped by `push_wrapped`. A newline is written between those lines. The first line has no newline before it.
A hyphen is not a break by itself. A UUID is not a break by itself. `push_wrapped` breaks on whitespace. A run with no whitespace is cut at the width.

### push_wrapped

<a id="function-push_wrapped"></a>

```rust
fn push_wrapped(line: &str, width: usize, out: &mut String)
```

The function `push_wrapped` appends one line wrapped to a character width.
The text `line` is that line. The count `width` is the maximum characters in one row. The buffer `out` receives the wrapped text.

A line that already fits is appended whole. A later piece writes a newline before itself. The first piece does not.
A longer line looks for the last whitespace inside the width. The window is `width` characters. The character just past that window is not a break. A whitespace break writes the text before that whitespace. The whitespace itself is not written. The following whitespace is skipped. A tail that is only whitespace ends the wrap.
A window with no whitespace is cut at the width. The cut is on a character boundary. The remainder is wrapped again.

### inspector_shown

<a id="function-inspector_shown"></a>

```rust
fn inspector_shown(body: &str, columns: u32, soft: bool) -> String
```

The function `inspector_shown` returns the text the inspector pane shows.
The text `body` is the pane text. The count `columns` is the wrap width. The flag `soft` is soft wrap.

Soft wrap calls `wrap_columns_text`. Soft wrap off returns a copy of `body`. Each line then stays whole, and the pane scrolls sideways.

### arrow_share

<a id="function-arrow_share"></a>

```rust
fn arrow_share(share: f32, parked: Option<f32>, hide_canvas: bool) -> (f32, Option<f32>)
```

The function `arrow_share` returns the share after one divider arrow is pressed.
The share `share` is the canvas share, clamped from 0 through 1. The option `parked` is the share saved when a pane was hidden. The flag `hide_canvas` is true for the arrow that hides the canvas.

If the canvas arrow is pressed and the share is already 1, the parked share is restored and the parked value is cleared. If that share is already 0, the share stays 0 and the parked value stays. Any other canvas-arrow press sets the share to 0 and parks the current share.
If the other arrow is pressed and the share is already 0, the parked share is restored and the parked value is cleared. If that share is already 1, the share stays 1 and the parked value stays. Any other press of that arrow sets the share to 1 and parks the current share.

### restore_share

<a id="function-restore_share"></a>

```rust
fn restore_share(parked: Option<f32>) -> f32
```

The function `restore_share` returns the share to put back when a hidden pane opens.
The option `parked` is the saved share. A saved share strictly between 0 and 1 is returned. A missing share returns [`CANVAS_WEIGHT`](#constant-canvas_weight). A saved 0 returns [`CANVAS_WEIGHT`](#constant-canvas_weight). A saved 1 returns [`CANVAS_WEIGHT`](#constant-canvas_weight). That constant is 0.72.

### inspector_width

<a id="function-inspector_width"></a>

```rust
fn inspector_width(span_x: f32, weight: f32, reserved: f32) -> f32
```

The function `inspector_width` returns the inspector width in pixels.
The width `span_x` is the full span. The share `weight` is the canvas share. The width `reserved` is the bar and the gaps beside it.

A closed inspector returns zero, so no text sits past the bar. `inspector_closed` is that test. The room is `span_x` minus `reserved`. A room of zero or less returns zero. Any other room is multiplied by one minus `weight`, after `weight` is clamped from 0 through 1.

### inspector_closed

<a id="function-inspector_closed"></a>

```rust
fn inspector_closed(weight: f32) -> bool
```

The function `inspector_closed` reports whether the inspector pane is closed.
The share `weight` is the canvas share. The result is true when `weight` is 1 or more. The canvas then takes the whole span.

### split_reserved

<a id="function-split_reserved"></a>

```rust
fn split_reserved(gap: f32, closed: bool) -> f32
```

The function `split_reserved` returns the width held back for the divider bar and its gaps.
The width `gap` is one side gap. The flag `closed` is true when the inspector is closed.

A closed inspector returns [`SPLIT_BAR_PX`](#constant-split_bar_px) plus one gap. That bar is 11 pixels. An open inspector returns the bar plus two gaps. The closed pane drops the gap on its side.

### arrow_zones

<a id="function-arrow_zones"></a>

```rust
fn arrow_zones(bar: egui::Rect) -> (egui::Rect, egui::Rect)
```

The function `arrow_zones` returns the two arrow-button rectangles on the divider bar.
The rectangle `bar` is the bar. The result is the canvas button and then the inspector button.

Both buttons are centered on the bar. The canvas button is the upper one. The inspector button is the lower one. The step between centers is half of [`ARROW_H`](#constant-arrow_h) plus [`ARROW_GAP`](#constant-arrow_gap). Those values are 14 and 4. The button width is [`ARROW_W`](#constant-arrow_w) or the bar width, whichever is smaller. [`ARROW_W`](#constant-arrow_w) is the bar width constant.

### arrow_icon

<a id="function-arrow_icon"></a>

```rust
fn arrow_icon(rect: egui::Rect, points_left: bool) -> [egui::Pos2; 3]
```

The function `arrow_icon` returns the three corners of the triangle inside one arrow button.
The rectangle `rect` is the button. The flag `points_left` chooses the tip. The triangle is inset by [`ARROW_INSET`](#constant-arrow_inset). That inset is 2 pixels.

A left tip uses the left center, the right bottom, and the right top. A right tip uses the left top, the right center, and the left bottom. The tip points at the pane that hides.

### paint_arrow_button

<a id="function-paint_arrow_button"></a>

```rust
fn paint_arrow_button(painter: &egui::Painter, rect: egui::Rect, points_left: bool, hot: bool)
```

The function `paint_arrow_button` paints one filled arrow button.
The painter `painter` receives the shapes. The rectangle `rect` is the button. The flag `points_left` is passed to `arrow_icon`. The flag `hot` is true while the pointer is on the button.

A hot button is filled with [`ARROW_HOT`](#constant-arrow_hot), the color `0x6e6e6e`. Any other button is filled with [`ARROW_FILL`](#constant-arrow_fill), the color `0x5c5c5c`. The corner radius is [`ARROW_ROUND`](#constant-arrow_round), which is 2.
A stroke of 1 pixel in [`ARROW_EDGE`](#constant-arrow_edge) is then drawn inside the rectangle. That color is `0x9a9a9a`. The triangle from `arrow_icon` is painted after the fill. The triangle uses [`ARROW_ICON`](#constant-arrow_icon), the color `0xdcdcdc`, and it has no stroke. Swing draws the triangle after the bar fill.

### ghost_color

<a id="function-ghost_color"></a>

```rust
fn ghost_color(mark: Mark) -> Option<egui::Color32>
```

The function `ghost_color` returns the gray used for an excluded card.
The mark `mark` is the card letter. [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded) returns gray `0x808080`. That letter is E.

[`Mark::Included`](chrome/enum.Mark.md#member-included), [`Mark::Input`](chrome/enum.Mark.md#member-input), and [`Mark::Output`](chrome/enum.Mark.md#member-output) return `None`. The caller then keeps the working color.

### prompt_ink

<a id="function-prompt_ink"></a>

```rust
fn prompt_ink(mark: Mark) -> egui::Color32
```

The function `prompt_ink` returns the prompt color for one card.
The mark `mark` is the card letter. An excluded card uses the gray from `ghost_color`. Any other card uses [`INPUT_GREEN`](#constant-input_green). That green is `0x00ff66`.

### reply_ink

<a id="function-reply_ink"></a>

```rust
fn reply_ink(mark: Mark, reply: &str) -> egui::Color32
```

The function `reply_ink` returns the reply color for one card.
The mark `mark` is the card letter. The string `reply` is the reply text.

An excluded card returns the gray from `ghost_color`. That test happens before the blank test. A reply that trims to empty returns [`RESPONSE_GRAY`](#constant-response_gray). That gray is `0x666666`. Any other reply returns [`OUTPUT_YELLOW`](#constant-output_yellow). That yellow is `0xffff55`.

### pane_edge

<a id="function-pane_edge"></a>

```rust
fn pane_edge(on: bool, face: PaneFace) -> Option<egui::Color32>
```

The function `pane_edge` returns the optional frame color of one canvas text pane.
The flag `on` is the frame switch. The face `face` is the [`PaneFace`](chrome/enum.PaneFace.md). A reply face follows trimmed text, the same test Java uses for `hasResponse`. This function does not read the reply text.

A switch that is off returns `None`. The pane then keeps the quiet plate. A switch that is on returns a color. [`PaneFace::Input`](chrome/enum.PaneFace.md#member-input) uses [`EDGE_MATCH`](#constant-edge_match). [`PaneFace::Reply`](chrome/enum.PaneFace.md#member-reply) uses [`EDGE_ARMED`](#constant-edge_armed). [`PaneFace::EmptyReply`](chrome/enum.PaneFace.md#member-emptyreply) uses [`RESPONSE_GRAY`](#constant-response_gray).

| | |
| --- | --- |
| **Specification** | PK-1097 docs/specs/common/formal-plan.spec.md FINESSE:f-182 |
| **Specification** | PK-1287 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md |

### push_kept

<a id="function-push_kept"></a>

```rust
fn push_kept(block: &mut Vec<(&'static str, String)>, mark: Mark, prompt: &str, reply: &str)
```

The function `push_kept` appends the rows one card would contribute.
The vector `block` receives the rows. The mark `mark` is the card letter. The string `prompt` is the prompt. The string `reply` is the reply. A row is a role word and the text.

[`Mark::Excluded`](chrome/enum.Mark.md#member-excluded) appends nothing. [`Mark::Included`](chrome/enum.Mark.md#member-included) appends a non-empty prompt as `user` and a non-empty reply as `assistant`. [`Mark::Input`](chrome/enum.Mark.md#member-input) appends a non-empty prompt as `user`. [`Mark::Output`](chrome/enum.Mark.md#member-output) appends a non-empty reply as `assistant`. An empty string is not appended.

### join_word

<a id="function-join_word"></a>

```rust
fn join_word(target: &str) -> String
```

The function `join_word` returns the short name the cell caption already paints.
The string `target` is the stored model word. `short_model_name` is tried first. A non-empty short name is returned.

An empty short name falls back to `target` trimmed. An empty trimmed target returns `ollama`. Any other trimmed target is returned as it is.

### join_banner_line

<a id="function-join_banner_line"></a>

```rust
fn join_banner_line(line: &str) -> bool
```

The function `join_banner_line` reports whether one line is a join banner.
The string `line` is that line. A banner is `JOIN` plus one model token. The legacy literal and a legacy full target are that shape.

The literal [`JOIN_BANNER`](#constant-join_banner), which is `JOIN ollama`, returns true. A line that starts with `JOIN ` returns true when the rest is non-empty and contains no whitespace. A line without that prefix returns false. An empty token returns false. A token that contains whitespace returns false.

### concat_join

<a id="function-concat_join"></a>

```rust
fn concat_join(replies: &[String], targets: &[String]) -> String
```

The function `concat_join` builds the joined reply text.
The slice `replies` holds each member reply. The slice `targets` holds each member model word. A missing target is read as an empty string.

Each part is `JOIN`, a space, `join_word` of that target, a newline, and the reply. An empty target becomes `ollama` through `join_word`. Parts after the first are separated by a blank line.

### strip_join

<a id="function-strip_join"></a>

```rust
fn strip_join(merged: &str) -> String
```

The function `strip_join` removes join banners from a merged reply.
The string `merged` is that reply. An empty string returns an empty string.

Each line is split on a newline. A line that `join_banner_line` accepts is dropped. That is a `JOIN` line whose model token has no whitespace. Every other line is kept. A blank line is not a banner, so a blank line between replies stays. A newline is written before each kept line after the first.

### merge_kept_block

<a id="function-merge_kept_block"></a>

```rust
fn merge_kept_block(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    merge_index: usize,
    target: usize,
) -> Vec<(&'static str, String)>
```

The function `merge_kept_block` builds the later-card view of one merge.
The slices are parallel card rows. The index `merge_index` is the merge card. The index `target` is the exclusive end of the member scan. Members at or after `target` are not read.

A member is kept when it shares the merge group, its column is not zero, and its mark is [`Mark::Included`](chrome/enum.Mark.md#member-included) or [`Mark::Input`](chrome/enum.Mark.md#member-input). Those letters are I and P. An empty prompt is skipped. The kept prompts are joined with newlines into one `user` row.
The merge reply is added only when the merge mark is [`Mark::Included`](chrome/enum.Mark.md#member-included) or [`Mark::Output`](chrome/enum.Mark.md#member-output). `strip_join` removes the banners first. An empty stripped reply is not added. The reply row uses the role `assistant`.

### merge_inspect_blocks

<a id="function-merge_inspect_blocks"></a>

```rust
fn merge_inspect_blocks(
    prompts: &[String],
    replies: &[String],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
) -> Vec<Vec<(&'static str, String)>>
```

The function `merge_inspect_blocks` builds the inspector view of the focused merge.
The slices are parallel card rows. The index `focus` is the merge card. The result is one block, or an empty vector when both parts are empty.

Every member of that group is scanned, including cards after the focus. A member has a column other than zero. An empty prompt is skipped. Marks are not consulted. The prompts are joined with newlines into one `user` row.
The merge reply is then appended raw. `strip_join` is not called. An empty reply is not appended. The reply row uses the role `assistant`.

### kept_sources

<a id="function-kept_sources"></a>

```rust
fn kept_sources(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
) -> Vec<Vec<(&'static str, String)>>
```

The function `kept_sources` returns the kept turns before the focus.
The slices are parallel card rows. The index `focus` is not included. The scan is `0` up to but not including `focus`.

A card in the focused group is skipped when that group is not zero. Siblings of the focused group stay out. A member is also skipped when a merge of its own group appears before the focus. That merge is emitted through `merge_kept_block` instead.
A merge card becomes `merge_kept_block` with `target` equal to the focus. An empty block is dropped. Any other card uses `push_kept`. An empty block is dropped.

### send_blocks

<a id="function-send_blocks"></a>

```rust
fn send_blocks(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
) -> Vec<Vec<(&'static str, String)>>
```

The function `send_blocks` returns the send set of one card.
The slices are parallel card rows. The index `focus` is the card being sent. `kept_sources` supplies the earlier blocks.

The focused prompt is always last. It is one `user` row, and it is appended even when the prompt is empty. The focused reply is not added here.

### inspect_blocks

<a id="function-inspect_blocks"></a>

```rust
fn inspect_blocks(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
) -> Option<Vec<Vec<(&'static str, String)>>>
```

The function `inspect_blocks` returns the blocks the inspector shows for one focus.
The slices are parallel card rows. This is not the send set. A focused excluded card omits its own text. Earlier kept turns stay.

Unequal slice lengths return `None`. A focus past a slice returns `None`. A focused merge returns `merge_inspect_blocks`, which lists that group.
Any other focus starts from `kept_sources`. The focused prompt is appended as a `user` row unless the mark is [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded).

### inspect_heads

<a id="function-inspect_heads"></a>

```rust
fn inspect_heads(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
    locks: &[bool],
) -> Vec<bool>
```

The function `inspect_heads` returns one flag per inspector block.
The slices are parallel card rows. The slice `locks` holds the lock flag of each card. A missing lock is false. The flag is the card that owns that block.

Unequal slice lengths return an empty vector. A focus past `marks` returns an empty vector. A focused merge returns one flag for each block from `merge_inspect_blocks`. Every one of those flags is the focus card lock.
Any other focus follows the same skips as `kept_sources`. A non-empty merge block contributes the merge card lock. A non-empty ordinary block contributes that card lock. The focus card lock is appended unless the focus mark is [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded).

### visible_heads

<a id="function-visible_heads"></a>

```rust
fn visible_heads(prompts: &[String], replies: &[String], marks: &[Mark], view: &PinView, locks: &[bool]) -> Vec<bool>
```

The function `visible_heads` returns one flag per visible card that contributes a block.
The slices `prompts`, `replies`, and `marks` are parallel. The view `view` is the [`PinView`](chrome/struct.PinView.md). The slice `locks` holds the lock flag of each card. A missing lock is false.

Unequal prompt, reply, or mark lengths return an empty vector. A card that `shows` rejects is skipped. `push_kept` builds the block. An empty block is skipped. Any other card contributes its lock.

### working_blocks

<a id="function-working_blocks"></a>

```rust
fn working_blocks(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
) -> Option<Vec<Vec<(&'static str, String)>>>
```

The function `working_blocks` returns the blocks in send order.
The slices are parallel card rows. The index `focus` is the card that would be sent.

Unequal slice lengths return `None`. A focus past a slice returns `None`. A missing focus is that `None`. A focused merge returns `merge_inspect_blocks`. That list is the group, and earlier cards outside the group are skipped.
A focused [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded) card returns an empty vector. Any other focus returns `send_blocks`.

### estimate_tokens

<a id="function-estimate_tokens"></a>

```rust
fn estimate_tokens(text: &str) -> usize
```

The function `estimate_tokens` estimates a token count the way `HeuristicTokenCounter` does.
The string `text` is the counted text. An empty string returns 1.

Any other string returns its UTF-16 length plus 3, divided by 4. The division is integer division.

### ssh_live_count

<a id="function-ssh_live_count"></a>

```rust
fn ssh_live_count(held: Option<i32>, memory: &str, live: &str) -> i32
```

The function `ssh_live_count` returns the token estimate for the focused pane.
The option `held` is the stored Roux integer. The string `memory` is the pane text. The string `live` is the unsent line.

A present integer stays the base. An empty unsent line adds nothing. A non-empty unsent line adds `estimate_tokens` of that line, as an `i32`.
When nothing is held, an empty unsent line counts `memory` alone. An unsent line that `memory` already ends with is also counted from `memory` alone. The screen estimate then already includes the echoed line. Any other unsent line is concatenated after `memory`, and that joined text is counted.

| | |
| --- | --- |
| **Specification** | L40 aa-sdd-source-prose.md FINESSE:f-177 |

### keep_pending

<a id="function-keep_pending"></a>

```rust
fn keep_pending(slot: &mut HeatSlot, beat: HeatBeat)
```

The function `keep_pending` stores the newest beat and drops a stale result.
The slot `slot` is the shared [`HeatSlot`](chrome/struct.HeatSlot.md). The beat `beat` is the edit just posted. The newest pending edit is the one that runs.

`current` becomes `beat.seq`. `pending` becomes this beat. A ready result whose sequence number is not `current` is cleared. A ready result that still matches `current` is left in place.
// @aa-sdd PK-1171 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-209

### seat_ready

<a id="function-seat_ready"></a>

```rust
fn seat_ready(slot: &mut HeatSlot, paint: HeatPaint)
```

The function `seat_ready` stores a finished paint when that paint is still the newest one.
The slot `slot` is the shared [`HeatSlot`](chrome/struct.HeatSlot.md). The paint `paint` is the result just counted.

The paint is stored when its sequence number equals `current` and `pending` is empty. Any other paint is not stored. An older result does not replace the slot.
// @aa-sdd PK-1171 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-209

### run_heat

<a id="function-run_heat"></a>

```rust
fn run_heat(beat: &HeatBeat) -> HeatPaint
```

The function `run_heat` counts one heatbar beat into a [`HeatPaint`](chrome/struct.HeatPaint.md).
The beat `beat` is that edit. The frame that posted the beat does not run this function. The worker thread runs it.

A canvas beat with `pin_active` builds a [`PinView`](chrome/struct.PinView.md) whose `active` flag is true and whose `shown` vector is copied from the beat. That path calls `inspect_visible_pane_gate`. A canvas beat with the pin off calls `inspect_body_pane_gate`. Both calls receive the card rows, the focus, the mood, the line width, the debug lines, the locks, and the budget.
The canvas result uses the beat sequence number. `canvas` is true. `estimate`, `chars`, and `blocks` pass through `count_i32`. `body` is the pane text.
A terminal beat sets `estimate` from `ssh_live_count`. A present `held` integer forces `chars` and `blocks` to zero. A missing `held` sets `chars` from the UTF-16 length of `memory` and `blocks` from `session_memory_lines`. Both of those counts pass through `count_i32`. `canvas` is false. `body` is empty.
// @aa-sdd PK-1171 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-209

### heat_worker

<a id="function-heat_worker"></a>

```rust
fn heat_worker(inner: std::sync::Arc<HeatInner>)
```

The function `heat_worker` is the loop on the heatbar thread.
The value `inner` is the shared [`HeatInner`](chrome/struct.HeatInner.md). A poisoned mutex still yields the inner slot.

The loop waits until a beat is pending or until `stop` is set with nothing pending. `stop` with an empty `pending` returns, and the thread ends. A pending beat is taken and `busy` becomes true.
The beat is skipped when a newer beat is already pending, or when its sequence number is not `current`, or when `stop` is set. A skip clears `busy`, signals the condition variable, and continues. A beat that is not skipped calls `run_heat`, then `seat_ready`, then clears `busy` and signals.

### hash_mood

<a id="function-hash_mood"></a>

```rust
fn hash_mood(mood: &MoodYaml, hasher: &mut std::collections::hash_map::DefaultHasher)
```

The function `hash_mood` writes one [`MoodYaml`](chrome/struct.MoodYaml.md) into a `DefaultHasher`.
The record `mood` is that mood. The hasher `hasher` receives the fields. The fields are written in this order: `blocked`, `line`, `on`, `pending`, `canvas`, `pins`, `width`, `height`.

No other field is written.

### mark_tag

<a id="function-mark_tag"></a>

```rust
fn mark_tag(mark: Mark) -> u8
```

The function `mark_tag` returns a small number for one card mark.
The mark `mark` is the card letter. [`Mark::Included`](chrome/enum.Mark.md#member-included) is 0. [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded) is 1. [`Mark::Input`](chrome/enum.Mark.md#member-input) is 2. [`Mark::Output`](chrome/enum.Mark.md#member-output) is 3.

### canvas_heat_sig

<a id="function-canvas_heat_sig"></a>

```rust
fn canvas_heat_sig(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
    mood: &MoodYaml,
    line_chars: u32,
    debug: &Option<(usize, Vec<String>)>,
    pin_active: bool,
    shown: &[bool],
    locks: &[bool],
    managed: i32,
) -> u64
```

The function `canvas_heat_sig` returns the value that decides whether the canvas heatbar posts a new beat.
The slices are the card rows. The index `focus` is the focused card. The record `mood` is written with `hash_mood`. The count `line_chars` is the preview width. The option `debug` is the invitation lines. The flag `pin_active` is the pin. The slice `shown` is pin visibility. The slice `locks` is the lock flags. The integer `managed` is the token budget.

The value covers `focus`, `line_chars`, `pin_active`, the mood, every prompt, every reply, every mark through `mark_tag`, every group, every column, `shown`, `locks`, and `managed`. It also covers whether `debug` is present. A present `debug` adds its index and its lines. `DefaultHasher` finishes the value as a `u64`.
The caller `post_canvas_heat` posts one beat when this value changes. An unchanged value does not post a beat. The integer already shown stays until a newer paint is applied.

### vt_heat_sig

<a id="function-vt_heat_sig"></a>

```rust
fn vt_heat_sig(generation: u64, held: Option<i32>, live: &str) -> u64
```

The function `vt_heat_sig` returns the value that decides whether the terminal heatbar posts a new beat.
The number `generation` is the grid generation. The option `held` is the stored integer. The string `live` is the unsent line. The pane memory text is not part of this value.

`DefaultHasher` finishes those three values as a `u64`. The caller `post_vt_heat` posts one beat when this value changes. An unchanged value does not post a beat.

### counted_text

<a id="function-counted_text"></a>

```rust
fn counted_text(blocks: &[Vec<(&'static str, String)>]) -> String
```

The function `counted_text` joins block text into the string the estimate counts.
The slice `blocks` is the blocks. Each row text is appended, and a newline is appended after it. The blocks stay in the order given. The rows inside a block stay in the order given.

An empty slice returns an empty string.

### seat_focused_reply

<a id="function-seat_focused_reply"></a>

```rust
fn seat_focused_reply(
    blocks: &mut [Vec<(&'static str, String)>],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
)
```

The function `seat_focused_reply` appends the focused reply onto the last inspector block.
The slice `blocks` is that block list. The other slices are the card rows. The index `focus` is the focused card. The send set stays the prompt. This function does not call `send_blocks`.

A focused merge returns immediately. `merge_inspect_blocks` already included that reply. A mark other than [`Mark::Included`](chrome/enum.Mark.md#member-included) returns immediately. An empty reply returns immediately.
Any other focus appends an `assistant` row to the last block. A list with no last block appends nothing.

### shown_line_chars

<a id="function-shown_line_chars"></a>

```rust
fn shown_line_chars(count: u32) -> usize
```

The function `shown_line_chars` returns the preview width in characters.
The count `count` is the stored width. A count under 1 uses [`LINE_CHARS`](#constant-line_chars). That missing-key length is 80. Swing uses 80.

Any other count is converted to `usize`. A count that does not fit becomes `usize::MAX`.

### counted_inspector

<a id="function-counted_inspector"></a>

```rust
fn counted_inspector(blocks: &[Vec<(&'static str, String)>], mood: &MoodYaml) -> String
```

The function `counted_inspector` returns the text the token estimate counts.
The slice `blocks` is passed to `counted_text`. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). A preview cut does not change this text.

`mood_sends` must be true, and the block list must not be empty, before a mood head is added. The head is [`MOOD_OPENER`](#constant-mood_opener), a newline, the trimmed mood line, a newline, and [`MOOD_CLOSER`](#constant-mood_closer). That head is placed before the counted text. [`MOOD_OPENER`](#constant-mood_opener) is the sentence before the constructed mood. [`MOOD_CLOSER`](#constant-mood_closer) is the sentence after it.
The vestige host sentence stays off. This function does not add `HOST_SYSTEM`. A mood that `mood_sends` rejects adds no head. An empty block list adds no head.

### sink_inspect

<a id="function-sink_inspect"></a>

```rust
fn sink_inspect(
    blocks: &[Vec<(&'static str, String)>],
    heads: &[bool],
    managed: i32,
) -> Option<(Vec<Vec<(&'static str, String)>>, usize)>
```

The function `sink_inspect` chooses which rows stay inside a managed token budget.
The slice `blocks` is the inspector blocks. The slice `heads` is the lock flag of each block. The integer `managed` is the budget. This reading feeds the inspector and the numerator. The idea is borrowed from the Java class `ReichenbachSink`.

The trim applied by the host stays on the host. This function does not perform that trim. A budget of zero or less is a closed gate and returns `None`. The caller then keeps the block list it already has.
A role `mood` is skipped. Every other row becomes a piece. `estimate_tokens` supplies the token count. `sink_role` supplies the role byte. The flag `stay` is true when the role word is `user` and `line_at_turn_head` finds a mood line at the start of the text. The flag `head` copies the lock of that block. A missing lock is false.
An empty piece list returns `None`. A list in which no piece is locked returns `None`.
Sequence numbers start at 1 in row order. The live piece is the last user-role piece that has no assistant after it. Its sequence number becomes 0. It is held out of the stored list and appended after the other kept rows.
The stored pieces are ordered by sequence number. Leading system pieces are pinned in that order. If no system piece leads, the last system piece is pinned instead. A `stay` piece that is not already pinned is pinned too. The pin list is then ordered by sequence number.
Locked pieces that are not pinned form the head list, in stored order. The pin tokens plus those locked tokens are compared with the budget. If that sum is over the budget, the tail is not taken. The live piece is still appended.
Otherwise the walk starts at the newest stored piece. A piece already pinned or locked is skipped. An assistant is paired with the previous piece when that previous piece is the prior sequence number, is not already taken, and its role is user, tool, or tool call. The pair is kept or dropped together. A single row is kept the same way.
The charged total starts as the pin tokens, the locked tokens, and the live tokens. A row or a pair that would push the total over the budget stops the walk. Older rows are left out. The tail is reversed so it runs from older to newer.
The kept order is the pin rows, then the locked rows, then the tail, then the live piece. Each piece comes back as its own one-row block. Those blocks are the chronological order `paint_inspector` reverses. The second value is the token sum of the kept pieces. That sum is the numerator.

### sink_role

<a id="function-sink_role"></a>

```rust
fn sink_role(role: &str) -> u8
```

The function `sink_role` returns the role byte used by the sink.
The string `role` is the row role. `system` returns [`SINK_SYSTEM`](#constant-sink_system), which is 1. `assistant` returns [`SINK_ASSISTANT`](#constant-sink_assistant), which is 3. `tool` returns [`SINK_TOOL`](#constant-sink_tool), which is 4. `tool_call` returns [`SINK_TOOL_CALL`](#constant-sink_tool_call), which is 5.

Any other role, including `user`, returns [`SINK_USER`](#constant-sink_user), which is 2.

### retarget_debug

<a id="function-retarget_debug"></a>

```rust
fn retarget_debug(
    before: &[Vec<(&'static str, String)>],
    after: &[Vec<(&'static str, String)>],
    debug: Option<(usize, Vec<String>)>,
) -> Option<(usize, Vec<String>)>
```

The function `retarget_debug` moves the debug invitation onto the user line that is still present.
The slice `before` is the block list before the sink. The slice `after` is the kept list. The option `debug` is a block index and the invitation lines.

A missing `debug` returns `None`. An index past `before` returns `None`. A before-block with no `user` row returns `None`. The search then looks for an after-block whose `user` text equals that row.
A match returns that new index with the same lines. The invitation stays on the user line that still rides. A dropped line returns `None`, so the invitation is left off.

### paint_sunk

<a id="function-paint_sunk"></a>

```rust
fn paint_sunk(
    blocks: Vec<Vec<(&'static str, String)>>,
    heads: &[bool],
    mood: &MoodYaml,
    line_chars: u32,
    debug: Option<(usize, Vec<String>)>,
    managed: i32,
) -> InspectPane
```

The function `paint_sunk` returns the inspector pane for one block list.
The vector `blocks` is that list. The slice `heads` is the lock flags. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The count `line_chars` is the preview width. The option `debug` is the invitation. The integer `managed` is the budget.

An open gate is a `Some` from `sink_inspect`. The debug lines are retargeted with `retarget_debug`. `finish_inspect` paints the sunk blocks. The pane estimate is then replaced by the sink sum. The character count, the block count, and the body stay on that sunk pane.
A closed gate is a `None`. The block list passed in is kept. `finish_inspect` paints that list with the original debug lines.

### seat_debug_head

<a id="function-seat_debug_head"></a>

```rust
fn seat_debug_head(blocks: &mut [Vec<(&'static str, String)>], at: usize, lines: &[String])
```

The function `seat_debug_head` inserts debug invitation lines at the front of one block.
The slice `blocks` is the block list. The index `at` is the block. The slice `lines` is the invitation lines.

An index past the list returns without a change. A block whose first row is not `user` returns without a change. Each line is inserted as a `mood` row. The insertions keep the line order at the front of that block.

### paint_inspector

<a id="function-paint_inspector"></a>

```rust
fn paint_inspector(
    blocks: Vec<Vec<(&'static str, String)>>,
    mood: &MoodYaml,
    line_chars: u32,
    debug: Option<(usize, Vec<String>)>,
) -> String
```

The function `paint_inspector` returns the inspector text, newest block first.
The vector `blocks` is the blocks. The record `mood` supplies the head from `mood_inspector_head`. The count `line_chars` is passed to `shown_line_chars`. The option `debug` is applied with `seat_debug_head` before the blocks are reversed.

The blocks are painted from last to first. [`BLOCK_RULE`](#constant-block_rule) and a newline sit between blocks. The first block has no rule before it. A `mood` row is written as its text plus a newline, and it is not numbered.
Every other row is numbered from 1. The line is the number, `. `, the role, two spaces, and `preview_chars` of the text. A newline inside a row is drawn as a space. Each kept role line is cut to the line count. A cut line gains the ellipsis character U+2026.
An on mood switch adds no mood head. The estimate leaves this string. It sits on the south of the inspector.

### finish_inspect

<a id="function-finish_inspect"></a>

```rust
fn finish_inspect(
    blocks: Vec<Vec<(&'static str, String)>>,
    mood: &MoodYaml,
    line_chars: u32,
    debug: Option<(usize, Vec<String>)>,
) -> InspectPane
```

The function `finish_inspect` measures one block list and paints it.
The vector `blocks` is that list. The record `mood`, the count `line_chars`, and the option `debug` are passed to the helpers named below.

`counted_inspector` builds the counted text before the debug lines are inserted. `estimate` is `estimate_tokens` of that text. `chars` is the UTF-16 length of that text. `blocks` is the length of the list passed in. `body` is `paint_inspector` of the same list.

### inspect_body

<a id="function-inspect_body"></a>

```rust
fn inspect_body(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
    mood: &MoodYaml,
) -> String
```

The function `inspect_body` returns the inspector text for the focused card.
The slices are the card rows. The index `focus` is the focused card. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The marks are the stored marks. A focused excluded card omits its own text.

The call uses [`LINE_CHARS`](#constant-line_chars) as the preview width. The returned string is the pane body. Newest block is first because `paint_inspector` reverses the blocks.

### inspect_body_pane

<a id="function-inspect_body_pane"></a>

```rust
fn inspect_body_pane(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
    mood: &MoodYaml,
    line_chars: u32,
    debug: Option<(usize, Vec<String>)>,
) -> InspectPane
```

The function `inspect_body_pane` returns the measured inspector pane for the focused card.
The slices are the card rows. The index `focus` is the focused card. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The count `line_chars` is the preview width. The option `debug` is the invitation.

The call goes to `inspect_body_pane_gate` with an empty lock slice and a budget of 0. That budget closes the sink.

### inspect_body_pane_gate

<a id="function-inspect_body_pane_gate"></a>

```rust
fn inspect_body_pane_gate(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
    mood: &MoodYaml,
    line_chars: u32,
    debug: Option<(usize, Vec<String>)>,
    locks: &[bool],
    managed: i32,
) -> InspectPane
```

The function `inspect_body_pane_gate` builds the focused inspector pane and then applies the sink.
The slices are the card rows. The index `focus` is the focused card. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The count `line_chars` is the preview width. The option `debug` is the invitation. The slice `locks` is the lock flags. The integer `managed` is the budget.

`inspect_blocks` supplies the blocks. A `None` from that call returns `finish_inspect` of an empty list, and the debug lines are dropped. Any other list takes locks from `inspect_heads`, appends the focused reply with `seat_focused_reply`, and returns `paint_sunk`.

### inspect_body_chars

<a id="function-inspect_body_chars"></a>

```rust
fn inspect_body_chars(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
    mood: &MoodYaml,
    line_chars: u32,
) -> String
```

The function `inspect_body_chars` returns only the inspector text for the focused card.
The arguments match `inspect_body_pane`, except there is no debug value. The debug value passed on is `None`. The returned string is the pane `body`.

### inspect_body_estimate

<a id="function-inspect_body_estimate"></a>

```rust
fn inspect_body_estimate(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
    mood: &MoodYaml,
) -> usize
```

The function `inspect_body_estimate` returns only the token estimate for the focused card.
The preview width is [`LINE_CHARS`](#constant-line_chars). The debug value is `None`. The returned number is the pane `estimate`.

### inspect_chars_estimate

<a id="function-inspect_chars_estimate"></a>

```rust
fn inspect_chars_estimate(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    groups: &[u64],
    columns: &[u32],
    focus: usize,
    mood: &MoodYaml,
    line_chars: u32,
) -> usize
```

The function `inspect_chars_estimate` returns the token estimate for one preview width.
The count `line_chars` is that width. The debug value is `None`. The returned number is the pane `estimate`. The preview cut does not change the counted text, so the width does not change this number.

### inspect_visible

<a id="function-inspect_visible"></a>

```rust
fn inspect_visible(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    view: &PinView,
    mood: &MoodYaml,
) -> String
```

The function `inspect_visible` returns the inspector text for the cards the pin is showing.
The slices are the card rows. The view `view` is the [`PinView`](chrome/struct.PinView.md). The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). This is not the send set.

An excluded card omits its own text. A pin miss is absent. Cards after the focus that the view still shows are included. The preview width is [`LINE_CHARS`](#constant-line_chars). The returned string is the pane body.

### inspect_visible_pane

<a id="function-inspect_visible_pane"></a>

```rust
fn inspect_visible_pane(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    view: &PinView,
    mood: &MoodYaml,
    line_chars: u32,
    debug: Option<(usize, Vec<String>)>,
) -> InspectPane
```

The function `inspect_visible_pane` returns the measured pane for the cards the pin is showing.
The slices `prompts`, `replies`, and `marks` must be the same length. A mismatch returns `finish_inspect` of an empty list, and the debug lines are dropped.

A matching length calls `inspect_visible_pane_gate` with an empty lock slice and a budget of 0. That budget closes the sink.

### inspect_visible_pane_gate

<a id="function-inspect_visible_pane_gate"></a>

```rust
fn inspect_visible_pane_gate(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    view: &PinView,
    mood: &MoodYaml,
    line_chars: u32,
    debug: Option<(usize, Vec<String>)>,
    locks: &[bool],
    managed: i32,
) -> InspectPane
```

The function `inspect_visible_pane_gate` builds the visible inspector pane and then applies the sink.
The view `view` decides which cards are shown. The slice `locks` is the lock flags. The integer `managed` is the budget. This is not the send set.

Unequal prompt, reply, or mark lengths return an empty pane, and the debug lines are dropped. `visible_heads` supplies the locks. Each shown card is passed through `push_kept`. An empty block is skipped. Cards after the focus stay eligible. The result is `paint_sunk`.

### inspect_visible_chars

<a id="function-inspect_visible_chars"></a>

```rust
fn inspect_visible_chars(
    prompts: &[String],
    replies: &[String],
    marks: &[Mark],
    view: &PinView,
    mood: &MoodYaml,
    line_chars: u32,
) -> String
```

The function `inspect_visible_chars` returns only the visible inspector text.
The debug value is `None`. The returned string is the pane `body` from `inspect_visible_pane`.

### token_line

<a id="function-token_line"></a>

```rust
fn token_line(estimate: i32) -> String
```

The function `token_line` returns the south line of the inspector.
The integer `estimate` is the token count. A negative count is painted as 0. The line is [`EST_TOKENS`](#constant-est_tokens) plus that number. [`EST_TOKENS`](#constant-est_tokens) is `est. tokens: `.

### count_i32

<a id="function-count_i32"></a>

```rust
fn count_i32(n: usize) -> i32
```

The function `count_i32` narrows a `usize` count to an `i32`.
The count `n` is that number. A value that fits is returned as an `i32`. A value that does not fit returns `i32::MAX`.

### token_fill

<a id="function-token_fill"></a>

```rust
fn token_fill(used: i32, budget: i32) -> f64
```

The function `token_fill` returns the share of the context window that the estimate fills.
The integer `used` is the estimate. The integer `budget` is the window. The result is a `f64` from 0 through 1.

A budget under 1 returns 0. That window is unknown. A `used` of 0 or less returns 0. An empty window stays 0. A `used` at or over the budget returns 1. Any other pair returns `used` divided by `budget`.

### ring_sweep

<a id="function-ring_sweep"></a>

```rust
fn ring_sweep(used: i32, budget: i32) -> i32
```

The function `ring_sweep` returns the used arc in degrees.
The integer `used` is the estimate. The integer `budget` is the window. The result is `360` times `token_fill`, rounded to the nearest `i32`.

A full window is 360. A count past the window is 360, because `token_fill` is then 1.

### tokens_left

<a id="function-tokens_left"></a>

```rust
fn tokens_left(used: i32, budget: i32) -> i32
```

The function `tokens_left` returns how many tokens are still inside the window.
The integer `used` is the estimate. The integer `budget` is the window. A budget under 1 returns -1. That window is unknown.

A negative `used` is treated as 0. The difference is `budget` minus `used`, computed in `i64`. A negative difference returns 0. A difference above `i32::MAX` returns `i32::MAX`. Any other difference is returned as an `i32`.

### tokens_over

<a id="function-tokens_over"></a>

```rust
fn tokens_over(used: i32, budget: i32) -> i32
```

The function `tokens_over` returns how many tokens are past the window.
The integer `used` is the estimate. The integer `budget` is the window. A budget under 1 returns 0. A `used` that is less than or equal to the budget returns 0.

Any other pair subtracts the budget from `used` in `i64`. A difference above `i32::MAX` returns `i32::MAX`. Any other difference is returned as an `i32`.

### percent_used

<a id="function-percent_used"></a>

```rust
fn percent_used(used: i32, budget: i32) -> i32
```

The function `percent_used` returns the rounded percent of the window that is used.
The integer `used` is the estimate. The integer `budget` is the window. A budget under 1 returns -1. A `used` of 0 or less returns 0.

Any other pair computes `(used * 100 + budget / 2) / budget` in `i64`. The division is integer division. A result above 9999 returns 9999. Any other result is returned as an `i32`.

### percent_free

<a id="function-percent_free"></a>

```rust
fn percent_free(used: i32, budget: i32) -> i32
```

The function `percent_free` returns the percent of the window that is still free.
The integer `used` is the estimate. The integer `budget` is the window. The used percent comes from `percent_used`.

A used percent below 0 returns -1. That is the unknown window. Any other result is 100 minus the used percent. A negative remainder returns 0.

### ring_ink

<a id="function-ring_ink"></a>

```rust
fn ring_ink(fill: f64, known: bool) -> egui::Color32
```

The function `ring_ink` returns the color of the used arc.
The number `fill` is the share from `token_fill`. The flag `known` is true when the window is known.

An unknown window returns [`RING_TRACK`](#constant-ring_track). A `fill` of 0 or less also returns [`RING_TRACK`](#constant-ring_track). A `fill` of 0.9 or more returns [`RING_RED`](#constant-ring_red). A `fill` of 0.7 or more returns [`RING_AMBER`](#constant-ring_amber). Any smaller positive fill returns [`EDGE_MATCH`](#constant-edge_match). That color is the green used while the window is still open.

### ring_stroke

<a id="function-ring_stroke"></a>

```rust
fn ring_stroke(width: i32, height: i32) -> f32
```

The function `ring_stroke` returns the stroke width of the token ring.
The integers `width` and `height` are the pane size in pixels. The span is the smaller side. A span under 1 is treated as 1.

The stroke is 0.09 times that span. A stroke under 12 returns 12. Any larger stroke is returned as it is.

### ring_oval

<a id="function-ring_oval"></a>

```rust
fn ring_oval(width: i32, height: i32) -> (i32, i32, i32)
```

The function `ring_oval` returns the square the token ring is drawn on.
The integers `width` and `height` are the pane size. The result is the left inset, the top inset, and the diameter. An empty result is `(0, 0, 0)` when the pane is too small.

The pad is half of `ring_stroke`, rounded up, plus 6. The diameter is the smaller side minus twice that pad. A diameter under 8 returns `(0, 0, 0)`. Any other diameter returns `(width - diameter) / 2` and `(height - diameter) / 2`, using integer division.

### ring_edge

<a id="function-ring_edge"></a>

```rust
fn ring_edge(width: i32, height: i32, turns: f64) -> Option<(i32, i32)>
```

The function `ring_edge` returns one point on the token ring.
The integers `width` and `height` are the pane size. The number `turns` is the fraction of a full turn. Turn 0 is 12 o'clock. A quarter turn is 3 o'clock.

The oval comes from `ring_oval`. A diameter under 8 returns `None`. The center is the oval origin plus half the diameter. The angle is `turns` times two pi, minus half pi. The point is rounded to integers.

### ring_arc

<a id="function-ring_arc"></a>

```rust
fn ring_arc(width: i32, height: i32, sweep: i32) -> Vec<(i32, i32)>
```

The function `ring_arc` returns the clockwise points of the used arc.
The integers `width` and `height` are the pane size. The integer `sweep` is the arc in degrees from `ring_sweep`. The points start at 12 o'clock.

A sweep of 0 or less returns an empty vector. The step count is `sweep / 6`, clamped from 8 through 120. The points run from step 0 through that count, inclusive. Each step maps to `ring_edge` along the sweep. A missing point is skipped.

### ring_words

<a id="function-ring_words"></a>

```rust
fn ring_words(used: i32, budget: i32, known: bool) -> (String, String)
```

The function `ring_words` returns the two lines drawn in the token ring.
The integer `used` is the estimate. The integer `budget` is the window. The flag `known` is true when the window is known. The first string is the upper line. The second string is the lower line.

An unknown window returns `no window` and `token_line` of the estimate. A count past the window returns the used percent, a percent sign, and `past by` plus `tokens_over`. A count still inside the window returns the free percent, `% free`, and `tokens_left` plus ` left`.

### grok_budget

<a id="function-grok_budget"></a>

```rust
fn grok_budget(windows: &[(String, i32)], id: &str) -> i32
```

The function `grok_budget` returns the context window stored for one Grok id.
The slice `windows` is the id and window pairs. The string `id` is the model id. The first pair whose id matches and whose window is above 0 is returned.

A missing id returns 0. A matching id whose window is 0 or less is skipped.

### yaml_key

<a id="function-yaml_key"></a>

```rust
fn yaml_key<'a>(map: &'a serde_yaml::Mapping, key: &str) -> Option<&'a serde_yaml::Value>
```

The function `yaml_key` returns one value from a YAML mapping.
The mapping `map` is that mapping. The string `key` is looked up as a YAML string. A missing key returns `None`.

### yaml_window

<a id="function-yaml_window"></a>

```rust
fn yaml_window(value: &serde_yaml::Value) -> i32
```

The function `yaml_window` reads a context window from one YAML value.
The value `value` is that node. A positive integer that fits in `i32` is returned. Anything else is 0.

An `i64` is accepted. A `u64` is accepted when it fits in `i64`. Any other YAML type returns 0. A number under 1 returns 0. A number above `i32::MAX` returns 0.

### token_budgets_from_yaml

<a id="function-token_budgets_from_yaml"></a>

```rust
fn token_budgets_from_yaml(yaml: &str) -> TokenBudgets
```

The function `token_budgets_from_yaml` reads the pool window and the Grok map from one YAML document.
The string `yaml` is that document. A bad document returns [`TokenBudgets::empty`](chrome/struct.TokenBudgets.md#method-empty). The text is not logged.

Empty text, after trim, returns empty budgets. A parse error returns empty budgets. A root that is not a mapping returns empty budgets. A missing `casserole` mapping returns empty budgets.
`casserole.grok.context-windows` supplies the Grok pairs. A key that is not a string is skipped. A window of 0 is skipped. `casserole.pool.context-window` supplies `pool` through `yaml_window`. Every other key is dropped.

### application_yaml_path

<a id="function-application_yaml_path"></a>

```rust
fn application_yaml_path() -> std::path::PathBuf
```

The function `application_yaml_path` returns the path of the bundled `application.yaml`.
The path is `CARGO_MANIFEST_DIR` joined with `../../../src/main/resources/application.yaml`. `CARGO_MANIFEST_DIR` is the crate directory captured when the crate is compiled.

### token_budgets_from_path

<a id="function-token_budgets_from_path"></a>

```rust
fn token_budgets_from_path(path: &std::path::Path) -> TokenBudgets
```

The function `token_budgets_from_path` reads token budgets from one file.
The path `path` is that file. A successful read is parsed with `token_budgets_from_yaml`. A missing file returns [`TokenBudgets::empty`](chrome/struct.TokenBudgets.md#method-empty). Any other read error returns empty budgets. The read is not logged.

### application_yaml_budgets

<a id="function-application_yaml_budgets"></a>

```rust
fn application_yaml_budgets() -> TokenBudgets
```

The function `application_yaml_budgets` reads the token budgets from the bundled YAML file.
The path comes from `application_yaml_path`. The read goes through `token_budgets_from_path`.

### token_seen

<a id="function-token_seen"></a>

```rust
fn token_seen(seen: &[String], label: &str) -> bool
```

The function `token_seen` reports whether a token-window label is already in the list.
The slice `seen` holds the labels already added. The string `label` is the candidate. The result is true when one stored label equals `label`.

### add_grok_row

<a id="function-add_grok_row"></a>

```rust
fn add_grok_row(
    rows: &mut Vec<TokenModel>,
    seen: &mut Vec<String>,
    id: &str,
    focused_target: &str,
    focused_cap: i32,
    factory: i32,
    grok_windows: &[(String, i32)],
)
```

The function `add_grok_row` appends one Grok row when that label is new.
The vector `rows` receives the [`TokenModel`](chrome/struct.TokenModel.md). The vector `seen` receives the label. The string `id` is the model id. An empty id returns without a change.

The label is `grok/` plus the id. A label already in `seen` returns without a change. The completion cap starts as `factory`. The cap becomes `focused_cap` when that cap is above 0, the focused target is Grok, and `id` equals the text after the slash in `focused_target`.
The context window comes from `grok_budget`. The row family is `grok`. The window is known only when the budget is above 0. The completion cap is marked known. The label is then recorded in `seen`.

### add_token_target

<a id="function-add_token_target"></a>

```rust
fn add_token_target(
    rows: &mut Vec<TokenModel>,
    seen: &mut Vec<String>,
    target: &str,
    window: i32,
    known: bool,
    focused_target: &str,
    focused_cap: i32,
    factory: i32,
    pool_window: i32,
    grok_windows: &[(String, i32)],
)
```

The function `add_token_target` appends the token-window row for one stored target.
The string `target` is that model word. `provider_id` reads the family. `model_after_slash` reads the id. A label already in `seen` is not added again.

An Ollama target with an empty id returns without a change. Any other Ollama target uses the label `ollama/` plus the id, the session `window`, and the session `known` flag. Its completion cap is 0 and is not known.
A Grok target with an empty id returns without a change. Any other Grok target is passed to `add_grok_row`. A pool target adds one row labeled `pool` when that label is new. The pool window is `pool_window` when that value is above 0, and otherwise 0. Its completion cap is 0 and is not known. Any other provider adds nothing.

### token_models

<a id="function-token_models"></a>

```rust
fn token_models(
    window_known: bool,
    session_window: i32,
    targets: &[String],
    focused_target: &str,
    focused_cap: i32,
    pool_window: i32,
    grok_windows: &[(String, i32)],
) -> Vec<TokenModel>
```

The function `token_models` returns the rows the token window can calculate against.
The flag `window_known` says whether the session window was reported. The integer `session_window` is that window. The slice `targets` holds the stored model words. The string `focused_target` is the focused card. The integer `focused_cap` is its completion cap. The integer `pool_window` is the pool budget. The slice `grok_windows` is the Grok map. This function does not ask the host.

The session window is kept only when `window_known` is true and the window is above 0. Otherwise the stored window is 0 and the known flag is false. The factory cap is `GrokEntry::factory` and its `max_completion_tokens`.
The first row is always `Ollama session` in the `ollama` family, using that session window. Its completion cap is 0 and is not known. Each target is then offered to `add_token_target`. The focused target is offered after that list. A Grok row is a canvas target or the focused turn.
A pool row labeled `pool` is appended when none was added yet. Its window is `pool_window` when that value is above 0, and otherwise 0.

### selected_heat_budget

<a id="function-selected_heat_budget"></a>

```rust
fn selected_heat_budget(
    window_known: bool,
    session_window: i32,
    targets: &[String],
    focused_target: &str,
    focused_cap: i32,
    pool_window: i32,
    grok_windows: &[(String, i32)],
) -> i32
```

The function `selected_heat_budget` returns the context window Token Calc opens on for the focused card.
The arguments are the same inputs `token_models` uses. The row comes from `token_model_index`. The first known row does not win.

A selected row whose window is known and above 0 returns that window. An unknown window returns 0. A known window of 0 or less returns 0. A missing row returns 0.
// @aa-sdd PK-1165 docs/specs/java/org/aetheris/swing/swing.spec.md FINESSE:f-207

### token_model_index

<a id="function-token_model_index"></a>

```rust
fn token_model_index(rows: &[TokenModel], focused_target: &str) -> usize
```

The function `token_model_index` returns the row to select for the focused target.
The slice `rows` is the token-window rows. The string `focused_target` is the model word. An empty slice returns 0.

A pool target looks for the label `pool`. A Grok target with an id looks for `grok/` plus that id. An Ollama target with an id looks for `ollama/` plus that id. A turn that names no model looks for `Ollama session`. The returned index is the first matching label. A missing label returns 0.

### token_source

<a id="function-token_source"></a>

```rust
fn token_source(model: &TokenModel) -> &'static str
```

The function `token_source` returns the words that name where the context window came from.
The model `model` is the selected row. A family of `grok` or `pool` returns `application.yaml`. Any other family returns `session num-ctx`.

### token_stats

<a id="function-token_stats"></a>

```rust
fn token_stats(used: i32, chars: i32, blocks: i32, model: Option<&TokenModel>) -> String
```

The function `token_stats` returns the lines beside the token ring.
The integer `used` is the estimate. The integer `chars` is the character count. The integer `blocks` is the block count. The option `model` is the selected [`TokenModel`](chrome/struct.TokenModel.md). A negative count is printed as 0.

The first lines are `est. tokens: `, `characters: `, `blocks: `, and `model: `. A missing model leaves the model name empty. A known window then adds `context window: `, `remaining: `, `used: `, `free: `, and `source: `. `remaining` is `tokens_left`. `used` and `free` are the percents. `source` is `token_source`. An over-count above 0 also adds `over: ` before the used percent.
An unknown window adds `context window: not reported`, `remaining: not reported`, `used: not reported`, and `free: not reported`. A Grok row then says `source: no context window on the model config`. A pool row says `source: pool has no context window here`. Any other row says `source: session num-ctx was not reported`.
A known completion cap adds `completion cap: `. The last line is `This open does not ask the host.`

### session_window

<a id="function-session_window"></a>

```rust
fn session_window(facts: Option<&CanvasFacts>) -> (bool, i32)
```

The function `session_window` returns the session context window from canvas facts.
The option `facts` is the [`CanvasFacts`](chrome/struct.CanvasFacts.md) from the init body. A present record whose `num_ctx` is above 0 returns true and that count.

A missing record returns false and 0. A `num_ctx` of 0 or less returns false and 0.

### canvas_token_targets

<a id="function-canvas_token_targets"></a>

```rust
fn canvas_token_targets(cards: &[TurnCard]) -> Vec<String>
```

The function `canvas_token_targets` collects the model words stored on the cards.
The slice `cards` is the card list. Each `target` is trimmed. An empty target is skipped. The other targets are returned in card order.

### focused_completion_cap

<a id="function-focused_completion_cap"></a>

```rust
fn focused_completion_cap(card: &TurnCard, doc: &crate::model::GrokDoc) -> i32
```

The function `focused_completion_cap` returns the completion cap of the focused card.
The card `card` is that card. The document `doc` is the Grok document. A target whose provider is not `grok` returns 0.

The factory cap is `GrokEntry::factory` and its `max_completion_tokens`. The cell id is looked up with `doc.entry`. An entry whose `max_completion_tokens` is 1 or more returns that cap. A missing entry returns the factory cap. An entry whose cap is under 1 returns the factory cap.

### paint_token_ring

<a id="function-paint_token_ring"></a>

```rust
fn paint_token_ring(ui: &egui::Ui, rect: egui::Rect, used: i32, model: Option<&TokenModel>)
```

The function `paint_token_ring` paints the token ring in one rectangle.
The surface `ui` supplies the painter. The rectangle `rect` is the ring area. The integer `used` is the estimate. The option `model` is the selected [`TokenModel`](chrome/struct.TokenModel.md).

The rectangle is filled with [`RING_BG`](#constant-ring_bg) and square corners. The width and the height are the rectangle sides rounded to integers. `ring_oval` supplies the oval. A diameter under 8 returns after the fill.
The track is a circle at the oval center. The radius is half the diameter. The stroke width comes from `ring_stroke`. The track color is [`RING_TRACK`](#constant-ring_track).
An inner circle is drawn when the diameter minus twice the inset is above 12. The inset is the stroke rounded up, plus 4. That circle uses a green of `0x3cb371` at alpha 70. Its stroke is 0.12 times the ring stroke, and at least 1.5. Its radius is the track radius minus the inset.
The window is known only when the model says so. A missing model is unknown and uses a budget of 0. A negative estimate is drawn as 0. `token_fill` receives 0 for both inputs when the window is unknown.
A known window with a fill above 0 draws the used arc. The sweep is `360` times the fill, rounded. A rounded sweep of 0 is raised to 1. `ring_arc` supplies the points, shifted by the rectangle origin. Two or more points are stroked with `ring_ink`.
`ring_words` supplies the two center lines. The upper line uses a font of 0.09 times the shorter side, and at least 13. It sits 2 pixels above the center in [`RING_HEAD`](#constant-ring_head). The lower line uses 0.05 times the shorter side, and at least 11. It sits 2 pixels below the center in [`RING_FOOT`](#constant-ring_foot). A shorter side under 1 is treated as 1.

### frame_for

<a id="function-frame_for"></a>

```rust
fn frame_for(
    cell: &[u8; 16],
    tune: &crate::amwp::ExecuteTune,
    blocks: &[Vec<(&'static str, String)>],
    heads: &[bool],
) -> Option<Vec<u8>>
```

The function `frame_for` builds the execute frame for one cell.
The bytes `cell` are the cell id. The tune `tune` is the `crate::amwp::ExecuteTune`. The slice `blocks` is the rows. The slice `heads` is the lock flag of each block. A missing lock is false.

Each row becomes a `SetLine` with that role, that text, and the block lock. The result is `execute_tuned` of the cell, the tune, and those lines.

### mood_user_card

<a id="function-mood_user_card"></a>

```rust
fn mood_user_card(card: &TurnCard) -> bool
```

The function `mood_user_card` reports whether a card counts as a user turn for the mood pin.
The card `card` is that card. A merge card returns true.

Any other card returns true only when its mark is [`Mark::Included`](chrome/enum.Mark.md#member-included) or [`Mark::Input`](chrome/enum.Mark.md#member-input) and its prompt is not empty.

### member_text_before

<a id="function-member_text_before"></a>

```rust
fn member_text_before(cards: &[TurnCard], merge: usize, focus: usize) -> bool
```

The function `member_text_before` reports whether a merge has member prompt text before the focus.
The slice `cards` is the card list. The index `merge` is the merge card. The index `focus` is the exclusive end of the scan.

A card counts when it is before `focus`, it shares the merge group, its column is above 0, its mark is [`Mark::Included`](chrome/enum.Mark.md#member-included) or [`Mark::Input`](chrome/enum.Mark.md#member-input), and its prompt is not empty.

### kept_nonempty

<a id="function-kept_nonempty"></a>

```rust
fn kept_nonempty(cards: &[TurnCard], index: usize, focus: usize) -> bool
```

The function `kept_nonempty` reports whether one earlier card still contributes text.
The slice `cards` is the card list. The index `index` is the card. The index `focus` is passed to `member_text_before` for a merge.

A merge is nonempty when a member prompt sits before the focus, or when its mark is [`Mark::Included`](chrome/enum.Mark.md#member-included) or [`Mark::Output`](chrome/enum.Mark.md#member-output) and `strip_join` of its reply is not empty. Any other card is nonempty when `push_kept` appends a row.

### prior_mood_users

<a id="function-prior_mood_users"></a>

```rust
fn prior_mood_users(cards: &[TurnCard], focus: usize) -> usize
```

The function `prior_mood_users` counts user turns before the focus for the mood pin.
The slice `cards` is the card list. The index `focus` is the focused card. A focus past the list returns 0.

A card in the focused group is skipped when that group is not zero. A member is skipped when a merge of its group appears before the focus. A merge counts when `member_text_before` is true. Any other card counts when `mood_user_card` is true.

### working_block_ids

<a id="function-working_block_ids"></a>

```rust
fn working_block_ids(cards: &[TurnCard], focus: usize) -> Vec<String>
```

The function `working_block_ids` returns the cell ids in the working set, as text.
The slice `cards` is the card list. The index `focus` is the focused card. A missing focus returns an empty vector. Each id comes from `cell_text`.

A focused merge returns only that merge cell. A focused excluded card returns an empty vector. Any other focus lists earlier cards that `kept_nonempty` accepts, then appends the focused cell.
A card in the focused group is skipped when that group is not zero. A member is skipped when a merge of its group appears before the focus.

### block_heads

<a id="function-block_heads"></a>

```rust
fn block_heads(cards: &[TurnCard], focus: usize) -> Vec<bool>
```

The function `block_heads` returns one lock flag per working-set block.
The slice `cards` is the card list. The index `focus` is passed to `working_block_ids`. The outbound list stays the list the client built. The host trims.

Each id is true when some card has that cell text and its `locked` flag is true. A locked card marks both halves, because the flag is per block rather than per row.

### splice_target

<a id="function-splice_target"></a>

```rust
fn splice_target(cards: &[TurnCard], focus: usize, mood: &MoodYaml) -> Option<String>
```

The function `splice_target` returns the cell id where the mood line is spliced.
The slice `cards` is the card list. The index `focus` is the focused card. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The result is an owned string. `None` means there is no splice target.

`mood_sends` false returns `None`. A focus past the list returns `None`. An excluded card that is not a merge returns `None`.
The pin starts as `mood_pin_for` of the mood canvas. A pending mood replaces that pin with `consume_pin` of `prior_mood_users` and the focused cell text. `pin_index` then finds that pin among every cell text, in card order.
A focused merge sends one `crate::mood::SendTurn`. Its id is the focused cell text. Its `user` flag is true. Its `at` is the focus index.
Any other focus walks `working_block_ids`. A working id missing from the card list is skipped. The focused card is a user turn when `mood_user_card` is true or its prompt is not empty. An earlier merge is a user turn when `member_text_before` is true. Any other earlier card uses `mood_user_card`.
The return value is `place` of those turns and the pin index, converted with `to_string`. `place` returning `None` returns `None`.

### mood_debug_seat

<a id="function-mood_debug_seat"></a>

```rust
fn mood_debug_seat(
    debug: bool,
    cards: &[TurnCard],
    focus: usize,
    mood: &MoodYaml,
    view: Option<&PinView>,
) -> Option<(usize, Vec<String>)>
```

The function `mood_debug_seat` returns the debug lines and the block index of the splice target.
The flag `debug` turns the lines on. The slice `cards` is the card list. The index `focus` is the focused card. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The option `view` is a [`PinView`](chrome/struct.PinView.md) when a pin is showing.

A false `debug` flag returns `None`. `mood_sends` false returns `None`. Empty `debug_lines` of the mood line return `None`. `splice_target` returning `None` returns `None`. The function does not change `mood`.
An active pin view counts cards that `shows` and that `push_kept` keeps. The count is the index of the card whose cell text equals the splice target. A target that is not in that count returns `None`.
Any other call uses `working_block_ids`. The index is the position of the splice target in that list. A missing position returns `None`.

### apply_mood_splice

<a id="function-apply_mood_splice"></a>

```rust
fn apply_mood_splice(
    blocks: &mut [Vec<(&'static str, String)>],
    cards: &[TurnCard],
    focus: usize,
    mood: &MoodYaml,
)
```

The function `apply_mood_splice` writes the mood line into one user row.
The slice `blocks` is the send rows, and the function changes it. The slice `cards` is the card list. The index `focus` is the focused card. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md).

`splice_target` returning `None` returns without a write. A target missing from `working_block_ids` returns without a write. A missing block at that index returns without a write.
The first row whose role is `user` is replaced by `splice_turn` of its text and the trimmed mood line. The function returns on that row. A block with no user row is left unchanged.

### commit_mood_pin

<a id="function-commit_mood_pin"></a>

```rust
fn commit_mood_pin(mood: &mut MoodYaml, cards: &[TurnCard], focus: usize)
```

The function `commit_mood_pin` stores a pending pin and clears the pending flag.
The record `mood` is changed. The slice `cards` is the card list. The index `focus` is the focused card.

The function returns immediately when the mood is not pending, when `mood_sends` is false, when the canvas name is empty, or when the focus is past the list.
Otherwise the pin is `consume_pin` of `prior_mood_users` and the focused cell text. `set_mood_pin` stores it on a clone of the canvas name. `pending` is then set to false.

### fit_blocks

<a id="function-fit_blocks"></a>

```rust
fn fit_blocks(
    cell: &[u8; 16],
    tune: &crate::amwp::ExecuteTune,
    blocks: &[Vec<(&'static str, String)>],
    mood: &MoodYaml,
    heads: &[bool],
) -> MoodFit
```

The function `fit_blocks` builds one execute frame and reports whether rows were dropped.
The bytes `cell` are the cell id. The tune `tune` is the `crate::amwp::ExecuteTune`. The slice `blocks` is the rows. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The slice `heads` is the lock flag of each block.

`mood_sends` false returns `frame_for` of the original blocks. `dropped` is false. No system row is added.
`mood_sends` true places one empty system row first, with a lock flag of false, and then clones the given blocks. The function does not change the text of those given rows. The system text is empty.
A frame from `frame_for` returns that frame and `dropped` false. A missing frame returns no frame and `dropped` false. A body that does not fit does not report a drop.

| | |
| --- | --- |
| **Specification** | PK-1288 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md |

### plan_group

<a id="function-plan_group"></a>

```rust
fn plan_group(
    cards: &[TurnCard],
    group: u64,
    mood: &MoodYaml,
    doc: &crate::model::GrokDoc,
    pool_boot: &str,
) -> (RunAsk, bool)
```

The function `plan_group` builds one frame for each member that would be sent.
The slice `cards` is the card list. The value `group` is the group id. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The document `doc` is the `crate::model::GrokDoc`. The string `pool_boot` is the pool boot id. The pair is a [`RunAsk`](chrome/enum.RunAsk.md) and a dropped flag.

A card is skipped when its group differs, when its column is 0, or when its mark is [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded). `no_model` on a member returns [`RunAsk::NoModel`](chrome/enum.RunAsk.md#member-nomodel) and false, and that return discards frames already built.
Each remaining member builds blocks with `send_blocks`, splices the mood with `apply_mood_splice`, and fits them with `fit_blocks`. The mood copy starts as `mood.clone()`. A frame that fits calls `commit_mood_pin` on that copy before the next member.
A fit with a frame stores the cell and the frame. Its dropped flag is or-ed into the group flag. A fit with no frame returns [`RunAsk::TooLarge`](chrome/enum.RunAsk.md#member-toolarge) and true when this fit or an earlier fit dropped rows. No stored frames returns [`RunAsk::Excluded`](chrome/enum.RunAsk.md#member-excluded) and false. Stored frames return [`RunAsk::Group`](chrome/enum.RunAsk.md#member-group) and the dropped flag.

### provider_id

<a id="function-provider_id"></a>

```rust
fn provider_id(target: &str) -> String
```

The function `provider_id` returns the provider word of a target.
The string `target` is the stored target. The word matches `AetherisStage2.providerId`.

A trim that is empty returns `ollama`. A slash keeps the text before the first slash. A target with no slash keeps the trimmed text. The result is lowercased with `to_ascii_lowercase`.

### cloud_target

<a id="function-cloud_target"></a>

```rust
fn cloud_target(target: &str) -> bool
```

The function `cloud_target` reports whether a target is a cloud provider.
The string `target` is the stored target. The word matches `cloudTarget`.

The function returns false when `provider_id` is `ollama`. Every other provider returns true. An empty target is `ollama` through `provider_id`, so it returns false.

### burner_denied

<a id="function-burner_denied"></a>

```rust
fn burner_denied(ephemeral: bool, target: &str) -> bool
```

The function `burner_denied` reports a burner card aimed at a cloud target.
The flag `ephemeral` is the burner flag. The string `target` is the stored target.

The function returns true only when `ephemeral` is true and `cloud_target` is true. An ollama target returns false.

### snap_of

<a id="function-snap_of"></a>

```rust
fn snap_of(card: &TurnCard) -> crate::model::ThinkSnap
```

The function `snap_of` copies the think flags of one card.
The card `card` is that card. The result is a `crate::model::ThinkSnap`. It is the think holder for this window. It does not read a vault column.

The snap copies `ollama_on`, `ollama_level`, `ollama_id`, `grok_on`, and `pool_on`.

### apply_snap

<a id="function-apply_snap"></a>

```rust
fn apply_snap(card: &mut TurnCard, think: &crate::model::ThinkSnap)
```

The function `apply_snap` writes a think snap back onto a card.
The card `card` is changed. The snap `think` is the `crate::model::ThinkSnap`.

The card receives `ollama_on`, a clone of `ollama_level`, a clone of `ollama_id`, `grok_on`, and `pool_on`.

### card_tune

<a id="function-card_tune"></a>

```rust
fn card_tune(
    card: &TurnCard,
    doc: &crate::model::GrokDoc,
    pool_boot: &str,
) -> crate::amwp::ExecuteTune
```

The function `card_tune` builds the execute tune for one card.
The card `card` supplies the target, the burner flag, the cell, and the think flags. The document `doc` is the `crate::model::GrokDoc`. The string `pool_boot` is the pool boot id. The result is a `crate::amwp::ExecuteTune`.

The document entry is looked up by `cell_text`. A missing entry is passed as `None`. The tune is `canvas_tune` of the target, the burner flag, `snap_of`, that entry, and `pool_boot`.
A Grok target with no document entry still receives a tune. `fill_grok` then fills the factory temperature and the factory completion count.

### burner_blocks

<a id="function-burner_blocks"></a>

```rust
fn burner_blocks(card: &TurnCard, doc: &crate::model::GrokDoc) -> bool
```

The function `burner_blocks` reports whether a cloud burner blocks the send.
The card `card` is that card. The document `doc` is the `crate::model::GrokDoc`.

`burner_denied` false returns false. Otherwise the function returns the opposite of `grok_burner_allows`. That allow is true only for a `grok` target whose burner box is checked in `burner_cloud` of this cell.

### group_burner_blocks

<a id="function-group_burner_blocks"></a>

```rust
fn group_burner_blocks(cards: &[TurnCard], group: u64, doc: &crate::model::GrokDoc) -> bool
```

The function `group_burner_blocks` reports whether any member of a group is blocked by a cloud burner.
The slice `cards` is the card list. The value `group` is the group id. The document `doc` is the `crate::model::GrokDoc`.

A group of 0 returns false. A card counts when its group matches, its column is above 0, its mark is not [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded), and `burner_blocks` is true.

### group_burner_denied

<a id="function-group_burner_denied"></a>

```rust
fn group_burner_denied(cards: &[TurnCard], group: u64) -> bool
```

The function `group_burner_denied` reports whether a member that would be sent is a denied cloud burner.
The slice `cards` is the card list. The value `group` is the group id. An excluded member stays out.

A group of 0 returns false. A card counts when its group matches, its column is above 0, its mark is not [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded), and `burner_denied` is true for its burner flag and its target.

### burner_fault

<a id="function-burner_fault"></a>

```rust
fn burner_fault(group: u64) -> FaultSeat
```

The function `burner_fault` builds the status for a denied cloud run.
The value `group` is the group id. The result is a [`FaultSeat`](chrome/struct.FaultSeat.md). The function does not read the prompt or the reply.

The status word is [`BURNER_DENIED`](#constant-burner_denied). A group of 0 leaves the slide empty. Any other group opens an [`ErrorNotice`](chrome/struct.ErrorNotice.md) whose body is [`BURNER_DENIED`](#constant-burner_denied) and whose `hard` flag is false. A notice that is not hard is the yellow slide.

### ask_run

<a id="function-ask_run"></a>

```rust
fn ask_run(cards: &[TurnCard], focus: usize) -> RunAsk
```

The function `ask_run` chooses the run for one focused card.
The slice `cards` is the card list. The index `focus` is the focused card. The result is a [`RunAsk`](chrome/enum.RunAsk.md).

The function calls `ask_run_mood` with [`MoodYaml::fresh`](chrome/struct.MoodYaml.md#method-fresh) and returns only the [`RunAsk`](chrome/enum.RunAsk.md). The dropped flag from that call is discarded.

### ask_run_mood

<a id="function-ask_run_mood"></a>

```rust
fn ask_run_mood(cards: &[TurnCard], focus: usize, mood: &MoodYaml) -> (RunAsk, bool)
```

The function `ask_run_mood` chooses the run and reports whether the fit dropped rows.
The slice `cards` is the card list. The index `focus` is the focused card. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The pair is a [`RunAsk`](chrome/enum.RunAsk.md) and a dropped flag.

The function calls `ask_run_doc` with `crate::model::GrokDoc::memory` and an empty pool boot string.

### ask_run_doc

<a id="function-ask_run_doc"></a>

```rust
fn ask_run_doc(
    cards: &[TurnCard],
    focus: usize,
    mood: &MoodYaml,
    doc: &crate::model::GrokDoc,
    pool_boot: &str,
) -> (RunAsk, bool)
```

The function `ask_run_doc` chooses the run from the cards, the mood, and the model document.
The slice `cards` is the card list. The index `focus` is the focused card. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The document `doc` is the `crate::model::GrokDoc`. The string `pool_boot` is the pool boot id. The pair is a [`RunAsk`](chrome/enum.RunAsk.md) and a dropped flag.

Any card with `running` true returns [`RunAsk::Busy`](chrome/enum.RunAsk.md#member-busy) and false. A missing focus returns [`RunAsk::Busy`](chrome/enum.RunAsk.md#member-busy) and false. A focused card whose group is not 0 returns [`RunAsk::BurnerDenied`](chrome/enum.RunAsk.md#member-burnerdenied) and false when `group_burner_blocks` is true, and otherwise returns `plan_group`.
An ordinary card returns [`RunAsk::BurnerDenied`](chrome/enum.RunAsk.md#member-burnerdenied) and false when `burner_blocks` is true. `no_model` returns [`RunAsk::NoModel`](chrome/enum.RunAsk.md#member-nomodel) and false. `working_blocks` returning `None` returns [`RunAsk::Busy`](chrome/enum.RunAsk.md#member-busy) and false. An empty block list returns [`RunAsk::Excluded`](chrome/enum.RunAsk.md#member-excluded) and false.
Otherwise the function splices the mood and calls `fit_blocks` with `block_heads`. A frame returns [`RunAsk::Send`](chrome/enum.RunAsk.md#member-send) and the fit dropped flag. A missing frame returns [`RunAsk::TooLarge`](chrome/enum.RunAsk.md#member-toolarge) and the fit dropped flag.

### seat_mood_slide

<a id="function-seat_mood_slide"></a>

```rust
fn seat_mood_slide(slide: &mut Option<ErrorNotice>, dropped: bool)
```

The function `seat_mood_slide` opens the yellow slide when a mood fit drops rows.
The option `slide` is the notice, and the function may replace it. The flag `dropped` comes from the fit. The function does not change the mood switch.

A true `dropped` flag stores an [`ErrorNotice`](chrome/struct.ErrorNotice.md) whose body is [`MOOD_TOO_LARGE`](#constant-mood_too_large) and whose `hard` flag is false. A notice that is not hard is the yellow slide. A false flag leaves the notice as it was.

### promote_group

<a id="function-promote_group"></a>

```rust
fn promote_group(cards: &mut Vec<TurnCard>, focus: usize, seq: &mut u64) -> bool
```

The function `promote_group` turns an ordinary card into a group and inserts the merge.
The vector `cards` is changed. The index `focus` is the card. The counter `seq` supplies the next id. The function returns false when it changes nothing.

A missing focus returns false and leaves `seq` alone. A card whose group is already not 0 returns false and leaves `seq` alone.
Otherwise `seq` advances by one with wrap, and that number is the group id. The focused card takes column 1. A card from [`TurnCard::fresh`](chrome/struct.TurnCard.md#method-fresh) is inserted at the next index, with that group and column 0. Its cell is `mint_cell` of the same number. The function returns true.

### join_group

<a id="function-join_group"></a>

```rust
fn join_group(cards: &mut [TurnCard], join_from: Option<usize>, target: usize) -> bool
```

The function `join_group` gives an ordinary card the group of another card.
The slice `cards` is changed. The option `join_from` is the source index. The index `target` is the card that joins. The target stays at its index.

`None` returns false. Equal indexes return false. An index past the list returns false. A source whose group is 0, or whose column is 0, returns false. A target whose group is already not 0 returns false.
Otherwise the target group becomes the source group. The target column becomes one more than the greatest column already in that group. The function returns true.

### add_cell

<a id="function-add_cell"></a>

```rust
fn add_cell(cards: &mut Vec<TurnCard>, merge_at: usize, seq: &mut u64) -> bool
```

The function `add_cell` inserts an empty member immediately before a merge.
The vector `cards` is changed. The index `merge_at` is the merge. The counter `seq` supplies the next cell number. The other cards stay in place. The function returns false when it inserts nothing.

A missing index returns false. A card that is not a merge returns false. The donor is the member of that group with the greatest column. Two cards that share that column keep the earlier one, because the test is strict.
The new card comes from [`TurnCard::fresh`](chrome/struct.TurnCard.md#method-fresh) of `next_number`. Its column is one more than that greatest column. It copies `expanded`, `target`, and the think flags from the donor. A group with no member uses an empty target, false think flags, and `expanded` false. The card is inserted at `merge_at`. The function returns true.

### next_number

<a id="function-next_number"></a>

```rust
fn next_number(seq: &mut u64, deny: impl Fn(u64) -> bool) -> u64
```

The function `next_number` returns the next id from a counter.
The counter `seq` is advanced and returned. The closure `deny` rejects a number. Zero stays free. A caller can use `deny` to skip a number already in use.

Each pass adds one with wrap. The function returns the first result that is not 0 and that `deny` accepts.

### blank_like

<a id="function-blank_like"></a>

```rust
fn blank_like(source: &TurnCard, n: u64, group: u64) -> TurnCard
```

The function `blank_like` builds an empty card from another card.
The card `source` supplies the copied fields. The number `n` is the new cell id. The value `group` is the new group. The mark and the column stay. The cell is new.

The cell is `mint_cell` of `n`. The prompt, the reply, the fault, `parent_cell_id`, and `vault_status` are empty. `running` is false. `started` is `None`. `collapsed`, `locked`, and `bar_open` are false. `expanded` is true. `branch_id` is `main`. `created_ms` comes from `card_stamp`.
The copy keeps `mark`, `column`, `ephemeral`, `target`, and the think flags. The group is the argument, not the source group.

### card_stamp

<a id="function-card_stamp"></a>

```rust
fn card_stamp() -> i64
```

The function `card_stamp` returns the current time in milliseconds since the Unix epoch.
A clock before the epoch returns 0. The millisecond count is cast to `i64`.

### view_window_height

<a id="function-view_window_height"></a>

```rust
fn view_window_height(rows: usize, line_height: f32, margin_y: f32, floor: f32) -> f32
```

The function `view_window_height` returns the height of a pane that shows a fixed number of rows.
The count `rows` is the row count. The value `line_height` is the height of one line. The value `margin_y` is added under the lines. The value `floor` is the least height returned.

A count below 1 is treated as 1. The inner height is the count times `line_height`. The result is that inner height plus `margin_y`, raised to `floor` when it is shorter.

### pane_font

<a id="function-pane_font"></a>

```rust
fn pane_font(ui: &egui::Ui) -> egui::FontId
```

The function `pane_font` returns the font of one pane.
The surface `ui` supplies the style. The result is an `egui::FontId`.

`override_font_id` is cloned when the style has one. That override is the control-bar size when the column set it. Otherwise the font is the monospace text style.

| | |
| --- | --- |
| **Specification** | PK-1289 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md |

### pane_line

<a id="function-pane_line"></a>

```rust
fn pane_line(ui: &egui::Ui) -> f32
```

The function `pane_line` returns the height of one input line.
The surface `ui` supplies the font and the spacing. The result is at least 16.

The font comes from `pane_font`, so a column override is the control-bar size. The height is the font row height plus `extra_text_line_spacing`. A shorter sum is raised to 16.

### pane_width

<a id="function-pane_width"></a>

```rust
fn pane_width(ui: &egui::Ui, desired: f32) -> f32
```

The function `pane_width` returns a finite width for one pane.
The surface `ui` supplies the available width. The value `desired` is the width a caller wants. A member passes its column. Any other caller can pass a non-finite value and take the row.

A finite `desired` of at least 1 is kept. Otherwise the available width is used. A result that is not finite, or that is below 1, becomes 200.

### paint_view_plate

<a id="function-paint_view_plate"></a>

```rust
fn paint_view_plate(ui: &egui::Ui, rect: egui::Rect, edge: Option<egui::Color32>)
```

The function `paint_view_plate` paints the plate around an open pane.
The surface `ui` supplies the painter. The rectangle `rect` is the plate. The option `edge` is the optional frame color. The text does not bring its own border.

A color paints [`PANE_STROKE_ROUND`](#constant-pane_stroke_round), the text-edit background, and a stroke of [`PANE_EDGE_PX`](#constant-pane_edge_px) drawn inside the rectangle. `None` keeps the quiet plate: the inactive widget stroke, the inactive corner radius, and the same background, also drawn inside.

### reply_bound

<a id="function-reply_bound"></a>

```rust
fn reply_bound(expanded: bool, window: f32) -> Option<f32>
```

The function `reply_bound` returns the reply height limit.
The flag `expanded` is the checked state. The value `window` is the saved row window. `None` means the reply stays whole.

A checked flag returns `None`. An unchecked flag returns `window` raised to at least 1.

### group_pane_height

<a id="function-group_pane_height"></a>

```rust
fn group_pane_height(merge: bool, expanded: bool, reply_empty: bool, window: f32) -> Option<f32>
```

The function `group_pane_height` returns the reply pane height for one card.
The flag `merge` marks the merge card. The flag `expanded` marks a checked reply. The flag `reply_empty` marks an empty reply. The value `window` is the saved row window. `None` means the reply stays whole, or that no reply pane is painted.

An empty reply on a card that is not a merge returns `None`. A member with an empty reply paints no reply pane. An ordinary card with an empty reply paints no reply pane. A merge with an empty reply keeps the row window.
An expanded card whose reply is not empty returns `None`. Every other call returns `window` raised to at least 1.

### group_open_floor

<a id="function-group_open_floor"></a>

```rust
fn group_open_floor(in_group: bool, shown: f32, window: f32) -> f32
```

The function `group_open_floor` returns the space under an open reply.
The flag `in_group` marks a card in a group. The value `shown` is the height already shown. The value `window` is the row window. The space keeps a group cell at least that window.

A grouped card shorter than `window` returns `window` minus `shown`. Every other call returns 0.

### merge_column_width

<a id="function-merge_column_width"></a>

```rust
fn merge_column_width(available: f32) -> Option<f32>
```

The function `merge_column_width` returns the column width for a merge.
The value `available` is the width on offer. A finite value of at least 1 returns that width. Every other value returns `None`. A member keeps the bar width and does not use this result.

### row_count

<a id="function-row_count"></a>

```rust
fn row_count(rows: u32) -> usize
```

The function `row_count` returns a pane row count of at least 1.
The count `rows` is the stored count. A count that does not fit in `usize` uses [`CELL_VIEW_ROWS`](#constant-cell_view_rows). The result is then raised to at least 1.

### prompt_window_height

<a id="function-prompt_window_height"></a>

```rust
fn prompt_window_height(ui: &mut egui::Ui, rows: usize) -> f32
```

The function `prompt_window_height` returns the locked height of one open pane.
The surface `ui` supplies the line height. The count `rows` is the row count.

The height is `view_window_height` of those rows, `pane_line`, a margin of 8, and a floor of 0. That is the rows times the line, plus 8.

### paint_locked_pane

<a id="function-paint_locked_pane"></a>

```rust
fn paint_locked_pane<R>(
    ui: &mut egui::Ui,
    width: f32,
    height: f32,
    salt: impl egui::AsIdSalt,
    edge: Option<egui::Color32>,
    add: impl FnOnce(&mut egui::Ui, egui::Rect) -> R,
) -> (egui::Rect, Option<R>)
```

The function `paint_locked_pane` puts a fixed pane on the parent and paints inside it.
The surface `ui` is the parent. The value `width` is the requested width. The value `height` is the requested height. The value `salt` is the child id salt. The option `edge` is the frame color passed to `paint_view_plate`. The closure `add` paints inside the pane. The pair is the allocated rectangle and the closure result.

The width comes from `pane_width`. The height is at least 1. The parent allocates that exact size with a hover sense, so the parent cursor advances by the pane and the closure cannot move it. That fixed size is the card lock.
The plate is painted on the allocated rectangle. The inset is [`PANE_EDGE_PX`](#constant-pane_edge_px) when `edge` is set, and 1 otherwise. The closure runs on a child clipped to the shrunk rectangle. An inner side of 1 or less skips the closure and returns `None` for the result.

### pane_wheel

<a id="function-pane_wheel"></a>

```rust
fn pane_wheel(wheel: bool) -> egui::scroll_area::ScrollSource
```

The function `pane_wheel` returns the scroll source for one locked pane.
The flag `wheel` is the mouse-wheel flag. The bar stays. Only the wheel flag is changed.

The source starts as the default. `mouse_wheel` is then set to `wheel`.

### deck_hole

<a id="function-deck_hole"></a>

```rust
fn deck_hole(bounds: egui::Rect, north: f32, south: f32) -> egui::Rect
```

The function `deck_hole` returns the canvas viewport.
The rectangle `bounds` is the canvas column. The value `north` is the first free row under the pin strip, or under P when the bar is folded. The value `south` is the top of the thinking pane, or the top of the buttons when that pane is closed.

`north` is clamped into the column. `south` is clamped from that top down to the column bottom, so a closed neighbor gives its space back. The rectangle keeps the column left and right.

### column_bands

<a id="function-column_bands"></a>

```rust
fn column_bands(column: egui::Rect, notice: ColumnNotice) -> ColumnBands
```

The function `column_bands` turns a column notice into deck and think rectangles.
The rectangle `column` is the column. The notice `notice` is the [`ColumnNotice`](chrome/struct.ColumnNotice.md). The result is a [`ColumnBands`](chrome/struct.ColumnBands.md).

`north` is clamped into the column. `buttons` is clamped from that north down to the column bottom. A think height of at least 1 places the think top at `buttons` minus that height, and not above `north`. A shorter height uses `buttons` as the think top.
The think rectangle exists when `buttons` is at least 1 below that top. It spans the column width from that top to `buttons`. Otherwise `think` is `None`. The deck is `deck_hole` from `north` to the think top.

### paint_north_seal

<a id="function-paint_north_seal"></a>

```rust
fn paint_north_seal(ui: &egui::Ui, column: egui::Rect, deck_top: f32)
```

The function `paint_north_seal` fills the band from the column top to the deck.
The surface `ui` supplies the painter. The rectangle `column` is the column. The value `deck_top` is the top of the deck. The fill is opaque.

`deck_top` is clamped into the column. The band keeps the column left, top, and right. A band under 1 in width or height returns without a fill. Otherwise the band is filled with [`PLATE`](#constant-plate) and square corners.
The cells paint first. This fill covers whatever reached the header. The Canvas tab and the pin strip paint on the foreground, above this plate.

### seat_north_layer

<a id="function-seat_north_layer"></a>

```rust
fn seat_north_layer(ui: &egui::Ui, layer: egui::LayerId, rect: egui::Rect)
```

The function `seat_north_layer` stores the header hit area.
The surface `ui` supplies the context. The id `layer` is the area. The rectangle `rect` is the band. A cell under the plate does not take a click in this band.

A rectangle under 1 in width or height returns without a write. Otherwise the area state uses the top-left pivot, the rectangle size, and `interactable` true. `last_became_visible_at` is `None`.

### pane_scroll

<a id="function-pane_scroll"></a>

```rust
fn pane_scroll<R>(
    ui: &mut egui::Ui,
    salt: impl egui::AsIdSalt,
    height: f32,
    wheel: bool,
    add: impl FnOnce(&mut egui::Ui) -> R,
) -> R
```

The function `pane_scroll` scrolls inside a locked pane.
The surface `ui` is the parent. The value `salt` is the scroll id. The value `height` is the maximum height. The flag `wheel` is passed to `pane_wheel`. The closure `add` paints the contents. The result is the closure value.

The area is vertical. It shrinks on neither axis. Its maximum height is `height`. Its minimum scrolled height is 1, so a short count is not dropped by a 64-point floor. Only the vertical axis is enabled. The return is the `inner` value from `show_apple`.

### apple_thumb_len

<a id="function-apple_thumb_len"></a>

```rust
fn apple_thumb_len(track: f32) -> f32
```

The function `apple_thumb_len` returns the handle length.
The value `track` is the pane span on that axis. The handle is forty percent of that span, from [`APPLE_THUMB_SHARE`](#constant-apple_thumb_share).

A span of 1 or less returns the span, and a negative span returns 0. Any longer span is clamped between 1 and the span, so a track that cannot hold the full share still keeps a handle that fits.

### apple_thumb_pos

<a id="function-apple_thumb_pos"></a>

```rust
fn apple_thumb_pos(start: f32, track: f32, thumb: f32, offset: f32, max_offset: f32) -> f32
```

The function `apple_thumb_pos` returns the thumb origin along the track.
The value `start` is the track start. The value `track` is the track length. The value `thumb` is the handle length. The value `offset` is the scroll offset. The value `max_offset` is the greatest offset. The offset maps across the travel, not across the content.

Travel is `track` minus `thumb`, and not below 0. Travel of 0, or a `max_offset` of 0 or less, returns `start`. Otherwise the offset ratio is clamped from 0 to 1 and applied across that travel.

### apple_offset_from_thumb

<a id="function-apple_offset_from_thumb"></a>

```rust
fn apple_offset_from_thumb(
    thumb_top: f32,
    start: f32,
    track: f32,
    thumb: f32,
    max_offset: f32,
) -> f32
```

The function `apple_offset_from_thumb` returns the scroll offset for a thumb origin.
The value `thumb_top` is the thumb origin. The value `start` is the track start. The value `track` is the track length. The value `thumb` is the handle length. The value `max_offset` is the greatest offset. This is the inverse of [`apple_thumb_pos`](#function-apple_thumb_pos).

Travel is `track` minus `thumb`, and not below 0. Travel of 0, or a `max_offset` of 0 or less, returns 0. Otherwise the position ratio is clamped from 0 to 1 and multiplied by `max_offset`.

### apple_scroll_delta

<a id="function-apple_scroll_delta"></a>

```rust
fn apple_scroll_delta(current: f32, desired: f32) -> f32
```

The function `apple_scroll_delta` returns the content delta that lands on `desired`.
The value `current` is the present offset. The value `desired` is the offset the thumb asks for. The result is `current` minus `desired`.

A positive value moves the content down and decreases the offset.

### apple_lerp

<a id="function-apple_lerp"></a>

```rust
fn apple_lerp(from: f32, to: f32, t: f32) -> f32
```

The function `apple_lerp` blends two amounts.
The value `from` is the start. The value `to` is the end. The value `t` is the blend. `t` is clamped from 0 to 1. The result is `from` plus that fraction of `to` minus `from`.

### apple_max_offset

<a id="function-apple_max_offset"></a>

```rust
fn apple_max_offset(content: f32, view: f32) -> f32
```

The function `apple_max_offset` returns how far the content can scroll.
The value `content` is the content length. The value `view` is the visible length. The result is `content` minus `view`, and not below 0.

### apple_track_rect

<a id="function-apple_track_rect"></a>

```rust
fn apple_track_rect(inner: egui::Rect, axis: usize, thick: f32) -> egui::Rect
```

The function `apple_track_rect` returns the hit strip on the outer edge.
The rectangle `inner` is the pane. The index `axis` is 0 for the horizontal bar and any other value for the vertical bar. The value `thick` is the strip thickness. The long axis is the pane span.

Axis 0 is the bottom strip, from `thick` above the bottom to the bottom-right corner. Any other axis is the right strip, from `thick` left of the right edge to the bottom-right corner.

### apple_thumb_rect

<a id="function-apple_thumb_rect"></a>

```rust
fn apple_thumb_rect(track: egui::Rect, axis: usize, origin: f32, thumb: f32) -> egui::Rect
```

The function `apple_thumb_rect` returns the handle rectangle inside the hit strip.
The rectangle `track` is the strip. The index `axis` is 0 for a horizontal handle. The value `origin` is the handle start. The value `thumb` is the handle length. The length is the forty-percent handle from `apple_thumb_len`.

Axis 0 runs along x and keeps the strip top and bottom. Any other axis runs along y and keeps the strip left and right.

### apple_press_grab

<a id="function-apple_press_grab"></a>

```rust
fn apple_press_grab(seat: &AppleSeat, pos: egui::Pos2, bar_w: f32, enabled: egui::Vec2b) -> [Option<f32>; 2]
```

The function `apple_press_grab` returns the grab offset for a press.
The record `seat` is the [`AppleSeat`](chrome/struct.AppleSeat.md). The point `pos` is the pointer. The value `bar_w` is the hit thickness. The pair `enabled` says which axes accept a press. A press on the handle keeps that grab. A press on the track centers the handle.

An inner rectangle under 1 in either side returns no grab. The vertical axis is tested before the horizontal axis. The first pass hits the handle. The second pass hits the track. A disabled axis is skipped. An axis whose max offset is 0.5 or less, or whose view is 1 or less, is skipped.
A handle hit stores the pointer minus the current thumb origin. A track hit moves the thumb so the pointer is at its center, clamped so the thumb stays on the track, and stores the pointer minus that origin. The first hit returns. No hit returns both slots as `None`.

### apple_drag_delta

<a id="function-apple_drag_delta"></a>

```rust
fn apple_drag_delta(seat: &AppleSeat, pos: egui::Pos2, enabled: egui::Vec2b) -> Option<egui::Vec2>
```

The function `apple_drag_delta` returns the content delta for a held handle.
The record `seat` is the [`AppleSeat`](chrome/struct.AppleSeat.md). The point `pos` is the pointer. The pair `enabled` says which axes accept a drag. `None` means the handle is already there.

An axis is skipped when its grab is `None` or when it is disabled. The thumb top is the pointer minus the grab, clamped onto the track. The desired offset comes from `apple_offset_from_thumb`. The step comes from `apple_scroll_delta`. A step whose absolute value is 0.01 or less is ignored. No accepted step returns `None`.

### show_apple

<a id="function-show_apple"></a>

```rust
fn show_apple<R>(
    ui: &mut egui::Ui,
    mut area: egui::ScrollArea,
    enabled: egui::Vec2b,
    add_contents: impl FnOnce(&mut egui::Ui) -> R,
) -> egui::scroll_area::ScrollAreaOutput<R>
```

The function `show_apple` hides the native bar and paints a forty-percent handle.
The surface `ui` is the parent. The area `area` is the scroll area. The pair `enabled` says which axes get a handle. The closure `add_contents` paints the contents. The result is the scroll output. The wheel and the drag scroll stay on the area, because the function does not replace the scroll source.

The saved frame is an [`AppleSeat`](chrome/struct.AppleSeat.md) stored under the rounded cursor origin. A released primary button clears both grabs. A fresh press with no grab calls `apple_press_grab`. A held primary button asks `apple_drag_delta` for a delta.
A horizontal grab sets the dragged id to the key with 0. Otherwise a vertical grab sets it to the key with 1. The native bar is `AlwaysHidden`. A delta turns animation off and is applied with `scroll_with_delta_animation` and no animation.
After the area shows, `paint_apple_bars` paints the handles. The saved frame then stores the clamped offset, the inner rectangle, and the content size. A held grab requests another frame. The frame is written back to temporary data.

### paint_apple_bars

<a id="function-paint_apple_bars"></a>

```rust
fn paint_apple_bars(
    ui: &egui::Ui,
    output: &egui::scroll_area::ScrollAreaOutput<impl Sized>,
    seat: &AppleSeat,
    enabled: egui::Vec2b,
    bar_w: f32,
)
```

The function `paint_apple_bars` paints the floating apple bars.
The surface `ui` supplies the painter and the scroll style. The output `output` is the scroll result. The record `seat` is the [`AppleSeat`](chrome/struct.AppleSeat.md). The pair `enabled` says which axes paint. The value `bar_w` is the wide thickness. Thickness and opacity stay on the egui fade. The length stays forty percent.

An inner rectangle under 1 in either side returns. A rectangle that is not visible returns. A disabled axis is skipped. An axis whose max offset is 0.5 or less, or whose view is 1 or less, is skipped.
The hit strip uses `bar_w`. The bar is hot when that axis is grabbed or the pointer is on the strip. The area is hot when the pointer is in the inner rectangle. Width animates from `floating_width` to `bar_w`. Handle opacity uses the interact value while hot, and otherwise blends the dormant and active values. Track opacity uses the interact value while hot, the active value while the pointer is in the area, and the dormant value otherwise. Both opacities at 0 or below skip the axis.
The handle length is `apple_thumb_len` of the view. Its origin comes from the clamped offset. A grab uses the active widget. A hot bar uses the hovered widget. Any other bar uses the inactive widget. A foreground scroll color uses the text stroke. Otherwise the handle uses the widget fill. The track fill is the extreme background. Each fill is multiplied by its opacity and uses the widget corner radius.

### format_edge_stamp

<a id="function-format_edge_stamp"></a>

```rust
fn format_edge_stamp(when: chrono::DateTime<chrono::FixedOffset>) -> String
```

The function `format_edge_stamp` formats a wall time for the input edge.
The time `when` is a fixed-offset date. The pattern is `YYYY-MM-DD HH:MM:SS`.

### local_edge_stamp

<a id="function-local_edge_stamp"></a>

```rust
fn local_edge_stamp() -> String
```

The function `local_edge_stamp` formats the current local time.
The time is `Local::now` converted with `fixed_offset`, then passed to `format_edge_stamp`. Tests pass a fixed offset into `format_edge_stamp` instead.

### input_edge_caption

<a id="function-input_edge_caption"></a>

```rust
fn input_edge_caption(stamp: &str, model: &str) -> String
```

The function `input_edge_caption` joins the time text and the short model.
The string `stamp` is the time text. The string `model` is the short model. An empty model returns the time text alone. Any other model returns the time text, a middle dot, and the model.

### hover_caption

<a id="function-hover_caption"></a>

```rust
fn hover_caption(stamp: &str, model: &str) -> String
```

The function `hover_caption` returns the hover words for a cell that is not selected.
The string `stamp` is the time text. The string `model` is the short model. The result is `input_edge_caption`, the same caption a selected cell paints.

### quiet_cell_tip

<a id="function-quiet_cell_tip"></a>

```rust
fn quiet_cell_tip(index: usize, focus: usize) -> bool
```

The function `quiet_cell_tip` reports a cell that is not the focus.
The index `index` is the cell. The index `focus` is the focused cell. The function returns true when the two differ. The focused cell already paints the line.

### caption_rule

<a id="function-caption_rule"></a>

```rust
fn caption_rule(em: f32) -> f32
```

The function `caption_rule` returns the width of one flank.
The value `em` is one em. A value below 1 counts as 8. The result is twice that width, the span the box rule used to take.

### caption_gap

<a id="function-caption_gap"></a>

```rust
fn caption_gap(space: f32) -> f32
```

The function `caption_gap` returns the air between a flank and the words.
The value `space` is the offered gap. A value below 1 returns 4. Every other value returns `space`.

### caption_span

<a id="function-caption_span"></a>

```rust
fn caption_span(rule: f32, gap: f32, text_w: f32) -> f32
```

The function `caption_span` returns the full caption width.
The value `rule` is one flank. The value `gap` is the air on one side of the words. The value `text_w` is the word width. The result is flank, air, words, air, and flank.

### flank_band

<a id="function-flank_band"></a>

```rust
fn flank_band(
    x: f32,
    w: f32,
    y: f32,
    view_left: f32,
    view_right: f32,
) -> Option<(f32, f32, f32, f32)>
```

The function `flank_band` returns the visible part of one flank.
The value `x` is the flank left. The value `w` is the flank width. The value `y` is the top. The values `view_left` and `view_right` are the pane edges. A flank past the pane is gone.

The left and right are clamped to the pane. A remaining width below 0.5 returns `None`. Otherwise the four numbers are the visible left, `y`, the visible width, and [`CARD_EDGE_PX`](#constant-card_edge_px). That stroke is one pixel.

### frame_caption

<a id="function-frame_caption"></a>

```rust
fn frame_caption(index: usize, focus: usize, stamp: &str, model: &str) -> String
```

The function `frame_caption` returns the caption painted on a cell frame.
The index `index` is the cell. The index `focus` is the focused cell. The string `stamp` is the time text. The string `model` is the short model. The caption rides the focused cell. A quiet cell keeps a plain frame.

An index other than the focus returns an empty string. The focus returns `input_edge_caption`.

### input_edge_x

<a id="function-input_edge_x"></a>

```rust
fn input_edge_x(pane_left: f32, pane_right: f32, text_width: f32, pad: f32) -> (f32, f32)
```

The function `input_edge_x` returns the right inset of the caption.
The values `pane_left` and `pane_right` are the pane edges. The value `text_width` is the caption width. The value `pad` is the inset. The pair is the visible left and the visible width. A wide caption keeps the right edge and loses its left.

The right edge is `pane_right` minus `pad`. The unclamped left is that edge minus `text_width`. The visible left is at least `pane_left` plus `pad`. The visible width is the right edge minus that left, and not below 0.

### embedded_caption_top

<a id="function-embedded_caption_top"></a>

```rust
fn embedded_caption_top(cell_top: f32, text_h: f32) -> f32
```

The function `embedded_caption_top` returns the top of the embedded caption.
The value `cell_top` is the top of the cell. The value `text_h` is the line height. The line is centered on the top stroke. The result is `cell_top` minus half of `text_h`.

### caption_clearance

<a id="function-caption_clearance"></a>

```rust
fn caption_clearance(line_h: f32, clear: f32) -> f32
```

The function `caption_clearance` returns the clear air around the embedded line.
The value `line_h` is the line height. The value `clear` is the extra air. The result is half the line plus that air.

The same result is the north gap. The same result is the pad between the stroke and the buttons.

### embedded_clip_top

<a id="function-embedded_clip_top"></a>

```rust
fn embedded_clip_top(cell_top: f32, text_h: f32, clear: f32) -> f32
```

The function `embedded_clip_top` returns the clip above an embedded caption.
The value `cell_top` is the top of the cell. The value `text_h` is the line height. The value `clear` is the extra air. The clip sits on the air above the line, under the view and under the cell above.

The result is `cell_top` minus `caption_clearance` of the line height and the extra air.

### embedded_visible_h

<a id="function-embedded_visible_h"></a>

```rust
fn embedded_visible_h(cell_top: f32, text_h: f32, clip_top: f32) -> f32
```

The function `embedded_visible_h` returns the caption height that remains after the clip.
The value `cell_top` is the top of the cell. The value `text_h` is the line height. The value `clip_top` is the clip.

The caption top comes from `embedded_caption_top`. The caption bottom is that top plus `text_h`. The visible height is the bottom minus the greater of the caption top and `clip_top`, and not below 0.

### cell_caption_line

<a id="function-cell_caption_line"></a>

```rust
fn cell_caption_line(ui: &egui::Ui) -> f32
```

The function `cell_caption_line` returns the height of one monospace caption line.
The surface `ui` supplies the painter. The north gap and the button pad use this height.

The line is the unwrapped layout of the character `0` in the monospace face and white. The result is that height, and at least 1.

### paint_input_edge

<a id="function-paint_input_edge"></a>

```rust
fn paint_input_edge(ui: &mut egui::Ui, rect: egui::Rect, caption: &str, salt: usize)
```

The function `paint_input_edge` paints the time text and the model on the top stroke.
The surface `ui` supplies the painter. The rectangle `rect` is the cell. The string `caption` is the words. The value `salt` keeps the hover id apart from other cells. The words sit on the right.

An empty caption returns. The face is monospace. The ink is the text color. The em is the width of `0`. The gap is the width of one space. A shown width under 1, or a line under 1 tall, returns. A visible height under 1 returns.
The right edge comes from `input_edge_x` with [`INPUT_EDGE_PAD`](#constant-input_edge_pad). The line top comes from `embedded_caption_top`, so the line is centered on the cell top and half of it sits above the stroke. The clip comes from `embedded_clip_top` with [`CELL_CLEAR_PX`](#constant-cell_clear_px). The band behind the words is the panel fill, with square corners.
Each flank is `flank_band` filled with [`EDGE_ARMED`](#constant-edge_armed). That stroke is one pixel. The words are one galley, clipped to the band, and they carry no frame of their own. The line stays whole. The band hovers and shows `crate::model::MODEL_LINE_TIP`.

### paint_prompt_window

<a id="function-paint_prompt_window"></a>

```rust
fn paint_prompt_window(
    ui: &mut egui::Ui,
    text: &mut String,
    rows: usize,
    width: f32,
    ink: egui::Color32,
    salt: usize,
    wheel: bool,
    borders: bool,
    seat: Option<usize>,
) -> (egui::Response, egui::Rect, Option<usize>)
```

The function `paint_prompt_window` paints the prompt in a fixed window.
The surface `ui` is the parent. The string `text` is the prompt, and the edit changes it. The count `rows` is the window. The value `width` is the pane width. The color `ink` is the text color. The value `salt` keeps the ids apart. The flag `wheel` is passed to `pane_scroll`. The flag `borders` is passed to `pane_edge`. The option `seat` is a cursor index when the caller sets one. The triple is the response, the rectangle, and the cursor index.

A row count below 1 becomes 1. The font is `pane_font`. The height is `prompt_window_height`. The edge is [`PaneFace::Input`](chrome/enum.PaneFace.md#member-input). The pane is `paint_locked_pane`. The scroll is `pane_scroll` of the inner height.
A cursor in `seat` calls `seat_prompt_caret` before the edit paints. The edit is multiline, with no frame, and Enter is its return key. The cursor index is the primary index from the edit. The same `seat` then clamps that cursor to the character count and stores it. `clear_undoer` turns the widget undo off before the state is stored.
A pane that does not paint returns a click response on the rectangle and no cursor. Longer text scrolls inside the window.

### seat_prompt_caret

<a id="function-seat_prompt_caret"></a>

```rust
fn seat_prompt_caret(ctx: &egui::Context, id: egui::Id, text: &str, cursor: usize)
```

The function `seat_prompt_caret` puts the caret on the edit before the prompt widget paints.
The context `ctx` holds the edit state. The id `id` is the edit. The string `text` supplies the character count. The index `cursor` is the requested caret.

A missing state starts from the default. The caret is `cursor` clamped to the character count. The undo buffer is cleared. The state is stored on `id`.

### paint_bounded_reply

<a id="function-paint_bounded_reply"></a>

```rust
fn paint_bounded_reply(
    ui: &mut egui::Ui,
    reply: &str,
    ink: egui::Color32,
    member: bool,
    height: f32,
    salt: usize,
    wheel: bool,
    borders: bool,
) -> egui::Rect
```

The function `paint_bounded_reply` paints a reply in a fixed window.
The surface `ui` is the parent. The string `reply` is the text. The color `ink` is the text color. The flag `member` wraps the label. The value `height` is the window. The value `salt` keeps the ids apart. The flag `wheel` is passed to `pane_scroll`. The flag `borders` is passed to `pane_edge`. The result is the pane rectangle. The text scrolls inside the window.

A reply that trims to empty uses [`PaneFace::EmptyReply`](chrome/enum.PaneFace.md#member-emptyreply). Any other reply uses [`PaneFace::Reply`](chrome/enum.PaneFace.md#member-reply). The width passed to `paint_locked_pane` is infinite, so `pane_width` takes the row. A member adds a wrapping label. Any other card uses `ui.label`.

### paint_open_reply

<a id="function-paint_open_reply"></a>

```rust
fn paint_open_reply(
    ui: &mut egui::Ui,
    reply: &str,
    ink: egui::Color32,
    member: bool,
    fill: bool,
    borders: bool,
) -> egui::Rect
```

The function `paint_open_reply` paints the whole reply in the prompt plate.
The surface `ui` is the parent. The string `reply` is the text. The color `ink` is the text color. The flag `member` wraps the label. The flag `fill` also wraps the label and may widen the row. The flag `borders` chooses the stroke. The result is the frame rectangle.

A reply that trims to empty uses [`PaneFace::EmptyReply`](chrome/enum.PaneFace.md#member-emptyreply). Any other reply uses [`PaneFace::Reply`](chrome/enum.PaneFace.md#member-reply). A frame color uses a margin of 4 by 2, the text-edit background, a stroke of [`PANE_EDGE_PX`](#constant-pane_edge_px), and [`PANE_STROKE_ROUND`](#constant-pane_stroke_round). No color uses the inactive stroke and the inactive corner radius, with the same margin and background.
`fill` true sets the minimum width from `merge_column_width` when that width exists. A member or a fill paints a wrapping label. Any other card uses `ui.label`.

### bar_mark

<a id="function-bar_mark"></a>

```rust
fn bar_mark(open: bool) -> &'static str
```

The function `bar_mark` returns the glyph for the group-tool row.
The flag `open` is the row state. A true flag returns [`BAR_OPEN`](#constant-bar_open), which is `^`, and that glyph shows the row. A false flag returns [`BAR_SHUT`](#constant-bar_shut), which is `v`, and that glyph is the closed row. A new card starts closed.

### bar_tip

<a id="function-bar_tip"></a>

```rust
fn bar_tip(open: bool) -> &'static str
```

The function `bar_tip` returns the hover text for the group-tool mark.
The flag `open` is the row state. A true flag returns [`BAR_HIDE_TIP`](#constant-bar_hide_tip). A false flag returns [`BAR_SHOW_TIP`](#constant-bar_show_tip).

### bar_tools

<a id="function-bar_tools"></a>

```rust
fn bar_tools(group: u64) -> &'static [&'static str]
```

The function `bar_tools` returns the group-tool labels for one card.
The value `group` is the group id. A group of 0 returns [`GROUP_LABEL`](#constant-group_label) and [`JOIN_LABEL`](#constant-join_label). Any other group returns [`UNGROUP_LABEL`](#constant-ungroup_label). An ordinary card is the zero group. A member or a merge is the other branch.

### tool_mark

<a id="function-tool_mark"></a>

```rust
fn tool_mark(open: bool) -> &'static str
```

The function `tool_mark` returns the glyph for the tool bar.
The flag `open` is the bar state. A true flag returns [`TOOL_OPEN`](#constant-tool_open), which is `^`, and that glyph shows the bar. A false flag returns [`TOOL_SHUT`](#constant-tool_shut), which is `v`, and that glyph is the closed bar. The bar starts closed.

### tool_tip

<a id="function-tool_tip"></a>

```rust
fn tool_tip(open: bool) -> &'static str
```

The function `tool_tip` returns the hover text for the tool-bar mark.
The flag `open` is the bar state. A true flag returns [`TOOL_HIDE_TIP`](#constant-tool_hide_tip). A false flag returns [`TOOL_SHOW_TIP`](#constant-tool_show_tip).

### pin_tokens

<a id="function-pin_tokens"></a>

```rust
fn pin_tokens(raw: &str) -> Vec<String>
```

The function `pin_tokens` splits a pin field into words.
The string `raw` is the field. The result matches Java `pinTokens`. The split is on any character that is not a letter or a digit. The words `a`, `the`, `and`, `of`, and `to` are dropped.

Each alphanumeric run is handed to `push_pin_token`. A trailing run is handed over after the loop.

### push_pin_token

<a id="function-push_pin_token"></a>

```rust
fn push_pin_token(out: &mut Vec<String>, cur: &mut String)
```

The function `push_pin_token` stores one finished pin word.
The vector `out` receives the word. The string `cur` is the run just finished, and the function clears it.

An empty run returns. The word is lowercased. `a`, `the`, `and`, `of`, and `to` are dropped. Every other word is pushed.

### pin_match

<a id="function-pin_match"></a>

```rust
fn pin_match(ephemeral: bool, prompt: &str, reply: &str, tokens: &[String]) -> bool
```

The function `pin_match` reports whether a card matches every pin word.
The flag `ephemeral` is the burner flag. The string `prompt` is the user text. The string `reply` is the assistant text. The slice `tokens` is the pin words. The test matches Java `pinMatch`.

A burner returns false. An empty token list returns false. Otherwise the haystack is the prompt, a newline, and the reply, all lowercased. Every token must be a substring of that haystack.

### pin_slots

<a id="function-pin_slots"></a>

```rust
fn pin_slots(slots: &[GatherSlot], view: &PinView) -> Vec<GatherSlot>
```

The function `pin_slots` applies the pin to deck geometry.
The slice `slots` is the deck. The view `view` is the [`PinView`](chrome/struct.PinView.md). Indexes stay. An empty group block is omitted.

An inactive view returns a clone of the slots. An active view keeps a card when `shows` is true. A block keeps the members and the merge that `shows` accepts. A block with no members and no merge is left out. The group id is copied.

### find_hit

<a id="function-find_hit"></a>

```rust
fn find_hit(prompt: &str, reply: &str, needle: &str) -> bool
```

The function `find_hit` reports a case-insensitive hit on the user text or the assistant text.
The string `prompt` is the user text. The string `reply` is the assistant text. The string `needle` is the find text. An empty needle returns true, so the cell stays shown. Percent and underscore stay ordinary characters.

The needle, the prompt, and the reply are lowercased. The hit is `contains` on either text.

### find_slots

<a id="function-find_slots"></a>

```rust
fn find_slots(
    slots: &[GatherSlot],
    prompts: &[String],
    replies: &[String],
    needle: &str,
) -> Vec<GatherSlot>
```

The function `find_slots` applies the find filter after the conceal pass.
The slice `slots` is the deck. The slices `prompts` and `replies` are the card text. The string `needle` is the find text. Indexes stay. An empty needle returns a clone of the slots.

A card stays when `find_hit` is true. A missing prompt or reply is empty. A block keeps the members and the merge that hit. A block whose members and merge all miss is omitted.

### shown_slots

<a id="function-shown_slots"></a>

```rust
fn shown_slots(slots: &[GatherSlot], folds: &[bool], hide: bool) -> Vec<GatherSlot>
```

The function `shown_slots` drops folded cards while hide is on.
The slice `slots` is the deck. The slice `folds` is the folded flag of each card. The flag `hide` turns the filter on. Open cards stay in place. Indexes of the cards that remain stay the same. The choice is for this window. It is not saved.

A false `hide` flag returns a clone of the slots. A card stays when its fold flag is not true. A missing fold flag counts as open. A block keeps the open members and the open merge. A block with neither is omitted.

### pin_box_off

<a id="function-pin_box_off"></a>

```rust
fn pin_box_off(was_on: bool, on: bool, needle: &mut String)
```

The function `pin_box_off` leaves the pin field alone when the box changes.
The flag `was_on` is the previous box. The flag `on` is the box now. The string `needle` is the field. Turning the box off keeps the words. The function does not change a mark, so the states stay where the last OK left them.

The three arguments are unused. The field is not cleared.

### pin_enter_commits

<a id="function-pin_enter_commits"></a>

```rust
fn pin_enter_commits(on: bool, enter_in_field: bool) -> bool
```

The function `pin_enter_commits` reports whether Enter in the field commits.
The flag `on` is the pin box. The flag `enter_in_field` is the Enter key. The function returns true only when both are true.

### pin_enter_due

<a id="function-pin_enter_due"></a>

```rust
fn pin_enter_due(on: bool, lost: bool, enter: bool, seen: bool) -> (bool, bool)
```

The function `pin_enter_due` reports whether this frame should commit Enter.
The flag `on` is the pin box. The flag `lost` is the focus loss. The flag `enter` is the Enter key. The flag `seen` is true after an earlier frame of the same loss. The pair is the commit flag and the next `seen` flag. Enter commits on the frame the field loses focus.

The box off, or no loss, returns false and false. A loss that was already seen returns false and true, so that later frame does not commit. `seen` stays true until the loss ends. The first frame of a loss returns `pin_enter_commits` and true.

### apply_pin_clear

<a id="function-apply_pin_clear"></a>

```rust
fn apply_pin_clear(on: bool, needle: &mut String) -> bool
```

The function `apply_pin_clear` empties the pin field and does not commit.
The flag `on` is unused, so the box stays as it is. The string `needle` is cleared. The function returns false.

### pin_write_due

<a id="function-pin_write_due"></a>

```rust
fn pin_write_due(commit: bool, on: bool, view_open: bool) -> bool
```

The function `pin_write_due` reports whether OK or Enter may write.
The flag `commit` is the commit ask. The flag `on` is the pin box. The flag `view_open` is the view window. The function returns true only when `commit` is true, the box is on, and the view window is closed.

### pin_commit_after_mark

<a id="function-pin_commit_after_mark"></a>

```rust
fn pin_commit_after_mark(due: bool, mark_pressed: bool) -> bool
```

The function `pin_commit_after_mark` reports whether a pin commit still stands after a mark press.
The flag `due` is the pin commit. The flag `mark_pressed` is the mark press. A mark press is not a pin commit. The function returns true only when `due` is true and the mark was not pressed.

### eligible_locks

<a id="function-eligible_locks"></a>

```rust
fn eligible_locks(marks: &[Mark], head_count: i32) -> Vec<usize>
```

The function `eligible_locks` returns the cards that may show a lock.
The slice `marks` is the mark of each card, from the top. The count `head_count` is the cap. Eligible cards come top first, skipping [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded), and the list is capped by `head-count`.

A count of 0 or less returns an empty vector. The walk keeps each other index in order. It stops when the list length reaches `head_count`.

| | |
| --- | --- |
| **Specification** | PK-1076 docs/specs/common/formal-plan.spec.md FINESSE:f-216 |

### repair_locks

<a id="function-repair_locks"></a>

```rust
fn repair_locks(marks: &[Mark], locked: &[bool], head_count: i32) -> Vec<bool>
```

The function `repair_locks` packs the locks into a prefix of the eligible cards.
The slice `marks` supplies `eligible_locks`. The slice `locked` is the current lock of each card. The count `head_count` is the cap. The result has one flag per mark.

The prefix is the leading eligible cards that are already locked. The first unlocked eligible card ends it. A missing lock flag counts as unlocked. Every flag starts false. The prefix indexes are then set true. A lock past that prefix is cleared.

### press_lock

<a id="function-press_lock"></a>

```rust
fn press_lock(marks: &[Mark], locked: &[bool], head_count: i32, index: usize) -> Vec<bool>
```

The function `press_lock` applies one lock press and keeps a packed prefix.
The slice `marks` supplies the eligible cards. The slice `locked` is the current locks. The count `head_count` is the cap. The index `index` is the pressed card. The result starts from `repair_locks`.

An index that is not eligible returns that repaired vector. The first unlocked eligible card is locked when it is the pressed card. Otherwise the last locked eligible card is unlocked when it is the pressed card. Any other press returns the repaired vector.

### shows_lock

<a id="function-shows_lock"></a>

```rust
fn shows_lock(marks: &[Mark], head_count: i32, index: usize) -> bool
```

The function `shows_lock` reports whether one card may show a lock.
The slice `marks` supplies `eligible_locks`. The count `head_count` is the cap. The index `index` is the card. The function returns true when that index is in the eligible list.

### head_lock_image

<a id="function-head_lock_image"></a>

```rust
fn head_lock_image(locked: bool) -> egui::ColorImage
```

The function `head_lock_image` builds the 16 by 16 padlock.
The flag `locked` chooses the closed green shackle or the open grey shackle. A hash becomes that ink. A dot stays clear. The result is the image [`paint_head_lock`](#function-paint_head_lock) draws.

| | |
| --- | --- |
| **Specification** | PK-1066 docs/specs/common/formal-plan.spec.md FINESSE:f-226 |

### paint_head_lock

<a id="function-paint_head_lock"></a>

```rust
fn paint_head_lock(ui: &mut egui::Ui, index: usize, locked: bool) -> bool
```

The function `paint_head_lock` paints the padlock on one card and reports a primary click.
`ui` is the cell bar. `index` is the card. `locked` is true when the shackle is closed. The control is 16 by 16. The image comes from [`head_lock_image`](#function-head_lock_image).
The id is `aetheris.headlock` and the card index. The button is not a mark. It does not use [`seat_mark_button`](#function-seat_mark_button). A primary click returns true. The tip is [`HEAD_LOCK_TIP`](#constant-head_lock_tip).

| | |
| --- | --- |
| **Specification** | PK-1066 docs/specs/common/formal-plan.spec.md FINESSE:f-226 |

### paint_head_lock_divider

<a id="function-paint_head_lock_divider"></a>

```rust
fn paint_head_lock_divider(ui: &mut egui::Ui)
```

The function `paint_head_lock_divider` paints the line between the padlock and the marks.
`ui` is the cell bar. The line is not a button. The line is not a mark. The bytes are 110, 110, and 110.

| | |
| --- | --- |
| **Specification** | PK-1066 docs/specs/common/formal-plan.spec.md FINESSE:f-226 |

### seat_mark

<a id="function-seat_mark"></a>

```rust
fn seat_mark(marks: &mut [Mark], index: usize, mark: Mark) -> bool
```

The function `seat_mark` writes one mark onto one cell.
The slice `marks` is changed. The index `index` is the cell. The mark `mark` is the new mark. The other marks stay. A missing index writes nothing and returns false. The press writes the mark. Focus and release do not call this function.

A present index stores `mark` and returns true.

### commit_pin

<a id="function-commit_pin"></a>

```rust
fn commit_pin(
    marks: &mut [Mark],
    ephemeral: &[bool],
    prompts: &[String],
    replies: &[String],
    needle: &str,
)
```

The function `commit_pin` writes marks from a pin commit.
The slice `marks` is changed. The slice `ephemeral` is the burner flag of each card. The slices `prompts` and `replies` are the card text. The string `needle` is the field. The write matches Java `commitPin`. The states are the marks. They are not a vault write. The function does not open Mood.

The tokens come from `pin_tokens`. A card that `pin_match` accepts stays as it is, except [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded) becomes [`Mark::Included`](chrome/enum.Mark.md#member-included). A miss becomes [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded). A burner misses. An empty field misses every card. A missing burner flag is false. A missing prompt or reply is empty.

### fold_pin_bar

<a id="function-fold_pin_bar"></a>

```rust
fn fold_pin_bar(open: bool, pin_on: bool, needle: &str) -> (bool, bool, String)
```

The function `fold_pin_bar` hides the pin bar and keeps the box and the words.
The flag `open` is unused. The flag `pin_on` is the box. The string `needle` is the field. The triple is false, the same box, and a clone of the words.

### show_pin_bar

<a id="function-show_pin_bar"></a>

```rust
fn show_pin_bar(open: bool, pin_on: bool, needle: &str) -> (bool, bool, String)
```

The function `show_pin_bar` shows the pin bar and keeps the box and the words.
The flag `open` is unused. The flag `pin_on` is the box. The string `needle` is the field. The triple is true, the same box, and a clone of the words. P is the control that asks for this result.

### paint_pin_face

<a id="function-paint_pin_face"></a>

```rust
fn paint_pin_face(
    ui: &mut egui::Ui,
    letter: &str,
    fill: egui::Color32,
    ink: egui::Color32,
    bevel: egui::Color32,
) -> egui::Response
```

The function `paint_pin_face` paints one pin letter as a click target.
The surface `ui` is the parent. The string `letter` is the glyph. The color `fill` is the plate. The color `ink` is the glyph. The color `bevel` is the stroke. The result is the click response.

The face is the button font. The size is the glyph plus twice the button padding plus 8 by 4, and at least 22 by 22. The allocation senses a click. A visible rectangle is filled with a corner radius of 2 and an inside stroke of 1. The glyph is centered.

### pin_mark_size

<a id="function-pin_mark_size"></a>

```rust
fn pin_mark_size(ui: &egui::Ui) -> egui::Vec2
```

The function `pin_mark_size` returns the size of P.
The surface `ui` supplies the button font. The size is the letter [`PIN_SHOW_LABEL`](#constant-pin_show_label) plus 20 by 8, and at least a 22 pixel box.

### paint_fact_line

<a id="function-paint_fact_line"></a>

```rust
fn paint_fact_line(ui: &mut egui::Ui, rect: egui::Rect, line: &str)
```

The function `paint_fact_line` paints one line on the inspector share of the tab row.
The surface `ui` is the parent. The rectangle `rect` is that share. The string `line` is the text. The two words are the line the caller passes.

An empty line returns. A rectangle under 8 wide, or under 1 tall, returns. The child is left to right and clipped to the rectangle. The label is monospace and truncated.

### pin_show_fill

<a id="function-pin_show_fill"></a>

```rust
fn pin_show_fill(on: bool) -> egui::Color32
```

The function `pin_show_fill` returns the fill for P.
The flag `on` is the pin box. A true flag returns [`EDGE_MATCH`](#constant-edge_match), the green. A false flag returns [`EDGE_ARMED`](#constant-edge_armed), the sapphire.

### paint_pin_mark

<a id="function-paint_pin_mark"></a>

```rust
fn paint_pin_mark(ui: &egui::Ui, rect: egui::Rect, on: bool, layer: egui::LayerId)
```

The function `paint_pin_mark` draws P on the divider.
The surface `ui` supplies the painter. The rectangle `rect` is P. The flag `on` chooses the fill. The id `layer` is the header strip, so P stays above the plate that covers the cells. The bar owns the pointer, so this function adds no second hit target.

A pointer inside the rectangle uses [`BUTTON_HOVER_INK`](#constant-button_hover_ink). Any other pointer uses white. The glyph is [`PIN_SHOW_LABEL`](#constant-pin_show_label). A visible rectangle is painted on `layer` with a corner radius of 2, the fill from `pin_show_fill`, and a white inside stroke of 1. The glyph is centered.

### paint_pin_row

<a id="function-paint_pin_row"></a>

```rust
fn paint_pin_row(
    ui: &mut egui::Ui,
    on: &mut bool,
    needle: &mut String,
    enter_seen: &mut bool,
) -> PinHit
```

The function `paint_pin_row` paints the pin row and returns what it asked.
The surface `ui` is the parent. The flag `on` is the pin box, and the checkbox changes it. The string `needle` is the field, and the edit changes it. The flag `enter_seen` remembers a focus loss. The result is a [`PinHit`](chrome/struct.PinHit.md).

The fold button sets `fold` when [`PIN_FOLD_LABEL`](#constant-pin_fold_label) is clicked. The checkbox is [`PIN_LABEL`](#constant-pin_label). `pin_box_off` then runs and changes nothing. The field is a single line, 160 wide, with the hint `words`, and it is enabled only while the box is on.
`pin_enter_due` may set `commit` from Enter and a focus loss, and it stores the next `enter_seen`. The clear face calls `apply_pin_clear`, which empties the field and sets `commit` false. The OK button sets `commit` true when it is clicked while the box is on. `bottom` is the bottom of the row.

### canvas_footer_rows

<a id="function-canvas_footer_rows"></a>

```rust
fn canvas_footer_rows(open: bool) -> (Vec<FootPiece>, Option<Vec<FootPiece>>)
```

The function `canvas_footer_rows` returns BB1, and BB2 when it is open.
The flag `open` shows BB2. BB2 is the row under BB1. The tool mark follows the last divider on BB1. The spinner sits on the control bar and is not a piece in this list. The user combo sits on the east of BB1 and is not a piece in this list.

BB1 is [`RUN_LABEL`](#constant-run_label), [`STOP_LABEL`](#constant-stop_label), [`NEW_LABEL`](#constant-new_label), [`CLEAR_LABEL`](#constant-clear_label), [`FootPiece::Rows`](chrome/enum.FootPiece.md#member-rows), [`DELETE_LABEL`](#constant-delete_label), a gap, [`COLLAPSE_ALL`](#constant-collapse_all), [`EXPAND_ALL`](#constant-expand_all), and a gap. A closed bar returns that row and `None`.
An open bar adds [`SEARCH_LABEL`](#constant-search_label), [`EXPORT_LABEL`](#constant-export_label), [`CANVAS_IMPORT_LABEL`](#constant-canvas_import_label), [`PULL_LABEL`](#constant-pull_label), [`TERMINAL_LABEL`](#constant-terminal_label), a gap, [`FootPiece::Switch`](chrome/enum.FootPiece.md#member-switch), [`MOOD_LABEL`](#constant-mood_label), a gap, [`HIDE_COLLAPSED`](#constant-hide_collapsed), and [`SHOW_COLLAPSED`](#constant-show_collapsed).

### paint_tool_gap

<a id="function-paint_tool_gap"></a>

```rust
fn paint_tool_gap(ui: &mut egui::Ui)
```

The function `paint_tool_gap` paints the vertical mark between groups.
The surface `ui` is the parent. The mark is nine pixels wide. The height follows the buttons through `button_outer_height`. The allocation senses hover.

The line is centered in the allocation. It starts 2 pixels below the top and ends 3 pixels above the bottom, and not above the start. The stroke is 1 pixel in the text color.

### paint_mood_switch

<a id="function-paint_mood_switch"></a>

```rust
fn paint_mood_switch(ui: &mut egui::Ui, mood: &MoodYaml) -> bool
```

The function `paint_mood_switch` paints the mood switch and reports a click.
The surface `ui` is the parent. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The switch is 36 by 22. The function returns true when the switch is enabled and the click landed. The caller flips the mood.

The switch is enabled when `mood_has_line` is true and the mood is not blocked. An enabled switch senses a click. A disabled switch senses hover. The knob color and the side come from `mood_knob`. No mood line is grey and sits on the left. On sits on the right. Off sits on the left.
No mood line uses [`MOOD_SWITCH_GREY_TIP`](#constant-mood_switch_grey_tip). An on switch uses [`MOOD_SWITCH_ON_TIP`](#constant-mood_switch_on_tip). An off switch that has a line uses [`MOOD_SWITCH_OFF_TIP`](#constant-mood_switch_off_tip). The track is inset by 2 on the vertical axis. Its corner radius is half the track height. The fill is white at alpha 70. The knob diameter is the allocation height minus 6. It sits 2 pixels in from the chosen side and 3 pixels below the top.

### spin_box

<a id="function-spin_box"></a>

```rust
fn spin_box(ui: &egui::Ui) -> f32
```

The function `spin_box` returns the width of the spinner glyph.
The surface `ui` supplies the monospace face. The four frames share one box so the word stays put. The frames are `|`, `/`, `-`, and `\`.

Each frame is measured in [`THINK_GLYPH`](#constant-think_glyph). The result is the widest frame plus 2.

### paint_footer_spin

<a id="function-paint_footer_spin"></a>

```rust
fn paint_footer_spin(ui: &mut egui::Ui, elapsed_ms: u64)
```

The function `paint_footer_spin` paints the footer spinner, the word, and the clock.
The surface `ui` is the parent. The count `elapsed_ms` is the run time passed to `think_clock`. The glyph is green, from [`THINK_GLYPH`](#constant-think_glyph). The word and the clock are cyan, from [`THINK_INK`](#constant-think_ink). The box is the widest frame from `spin_box`.

The glyph comes from `think_frame` of the plate time in milliseconds, not from `elapsed_ms`. The box height is the glyph height or the interact size, whichever is taller. The allocation senses hover. The glyph is centered. The word is [`THINK_WORD`](#constant-think_word). The clock is `think_clock`. The function asks for another frame after [`THINK_FRAME_MS`](#constant-think_frame_ms).

### user_menu

<a id="function-user_menu"></a>

```rust
fn user_menu(seats: &[ConnectedSeat]) -> UserMenu
```

The function `user_menu` builds the pull-down from connected AMWP records.
The slice `seats` is those records. An empty username is omitted. Illegal names share `anon` through `sanitize_user`. A focused record that is skipped leaves the selection empty when no earlier listed record was focused.

A record is skipped when it is disconnected, when `amwp` is false, or when the name is empty. A skipped record does not change `selected`. A kept name is sanitized and stored once. A kept focused record sets `selected` to that sanitized name.

### user_move

<a id="function-user_move"></a>

```rust
fn user_move(open_user: &str, picked: &str) -> UserMove
```

The function `user_move` chooses what a pick does to the open vault.
The string `open_user` is the user already open. The string `picked` is the chosen name. The same open user keeps the vault. A different name binds. An empty pick is quiet.

An empty `picked` returns [`UserMove::Quiet`](chrome/enum.UserMove.md#member-quiet). Otherwise the name is `sanitize_user` of the pick. A non-empty `open_user` equal to that name returns [`UserMove::Keep`](chrome/enum.UserMove.md#member-keep). Every other pick returns [`UserMove::Switch`](chrome/enum.UserMove.md#member-switch) of the sanitized name.

### take_user_move

<a id="function-take_user_move"></a>

```rust
fn take_user_move(open_user: &str, picked: &str, user: &mut String) -> bool
```

The function `take_user_move` writes a user only when the pick binds a different vault.
The string `open_user` is the user already open. The string `picked` is the chosen name. The string `user` receives the bound name. The same user leaves `user` unchanged.

[`UserMove::Quiet`](chrome/enum.UserMove.md#member-quiet) and [`UserMove::Keep`](chrome/enum.UserMove.md#member-keep) return false. [`UserMove::Switch`](chrome/enum.UserMove.md#member-switch) stores the sanitized name in `user` and returns true.

### user_tip

<a id="function-user_tip"></a>

```rust
fn user_tip(name: &str) -> String
```

The function `user_tip` returns the hover text for the user pull-down.
The string `name` is the shown name. The sentences are plain. The full name stays in the tip when the button is capped.

An empty name starts `No user is selected.` Any other name starts `Selected user:` and the name. The rest says that choosing a name switches the vault on screen to that connected user, and that the thinking spinner follows the vault that is running.

### user_combo_width

<a id="function-user_combo_width"></a>

```rust
fn user_combo_width(remain: f32, reserved: f32) -> f32
```

The function `user_combo_width` returns the room left for the user pull-down.
The value `remain` is the width on offer. The value `reserved` is the width held back. The result is capped at the preferred width [`USER_COMBO_W`](#constant-user_combo_w) and the ceiling [`USER_COMBO_MAX`](#constant-user_combo_max).

The room is `remain` minus `reserved`, and not below 0. That room is then limited by both caps.

### east_reserve

<a id="function-east_reserve"></a>

```rust
fn east_reserve() -> f32
```

The function `east_reserve` returns the east column held before the master buttons paint.
The combo keeps 160, which is [`USER_COMBO_W`](#constant-user_combo_w). The spinner sits on the control bar and is not part of this reserve.

The result is [`USER_COMBO_W`](#constant-user_combo_w).

| | |
| --- | --- |
| **Specification** | F-156 |

### paint_east_capsule

<a id="function-paint_east_capsule"></a>

```rust
fn paint_east_capsule(ui: &mut egui::Ui, users: &UserMenu) -> Option<String>
```

The function `paint_east_capsule` paints the user combo on the outer east.
The surface `ui` is the parent. The menu `users` is the [`UserMenu`](chrome/struct.UserMenu.md). The result is the clicked name. The spinner sits on the control bar. This function does not paint it. The combo stays, including an empty list. A click reports a name only when it differs from the name already shown.

A remaining width of 1 or less returns `None`. The lane width is the remaining width minus [`BUTTON_CLEAR_PX`](#constant-button_clear_px), and at least 1. The lane height is `button_outer_height`. The layout runs right to left. The clip is `button_lane_clip`.
The shown text is `selected`, or empty when nothing is selected. The combo width is `user_combo_width` of the remaining width and a reserve of 0, and at least 1. The id is [`USER_COMBO_ID`](#constant-user_combo_id). The icon is `emerald_combo_arrow`. A selectable name that is clicked, and that is not already selected, is returned. The hover text is `user_tip` of the shown name.

### bb_fold_mark

<a id="function-bb_fold_mark"></a>

```rust
fn bb_fold_mark(open: bool) -> &'static str
```

The function `bb_fold_mark` returns the fold glyph for the button bar.
The flag `open` means the bars are showing. A true flag returns `v`. A false flag returns `^`, the glyph used while the bars are folded.

| | |
| --- | --- |
| **Specification** | PK-992 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md FINESSE:f-223 |

### bb_fold_line_ink

<a id="function-bb_fold_line_ink"></a>

```rust
fn bb_fold_line_ink() -> egui::Color32
```

The function `bb_fold_line_ink` returns the ink of the folded button-bar line.
The color is [`THINK_GRIP_INK`](#constant-think_grip_ink), the thinking grip ink. The folded button bar uses this line.

### footer_ceiling

<a id="function-footer_ceiling"></a>

```rust
fn footer_ceiling(ui: &egui::Ui, tools_open: bool, bars_open: bool) -> f32
```

The function `footer_ceiling` returns the room kept for the button bar.
The surface `ui` supplies the row height and the gap. The flag `tools_open` adds the tool row. The flag `bars_open` means the bar is showing. The scroll stops on the painted bar, so spare pixels stay with the cells. The row is the painted button plus the clearance. The stock interact size is short of that.

A folded parent returns `folded_control_height` and gives the rest back to the canvas. An open bar uses one row, or two when the tools are open. The gap between rows is the item spacing when the tools are open, and 0 otherwise. The total adds twice [`FOOTER_PAD`](#constant-footer_pad), twice [`FOOTER_OUT`](#constant-footer_out), twice [`FOOTER_EDGE_PX`](#constant-footer_edge_px), and 24.

### paint_bb_folded_line

<a id="function-paint_bb_folded_line"></a>

```rust
fn paint_bb_folded_line(ui: &mut egui::Ui, bars_open: &mut bool) -> FooterHit
```

The function `paint_bb_folded_line` paints one folded button-bar row.
The surface `ui` is the parent. The flag `bars_open` is set true when the unfold mark is clicked. The result is a [`FooterHit`](chrome/struct.FooterHit.md) whose click flags stay false. The row is the unfold mark, then the thinking line. It paints no drag handle, no C, and no Close.

The height is `folded_control_height`. The width is the available width, and at least 1. The allocation senses hover. The inner rectangle shrinks by [`BUTTON_CLEAR_PX`](#constant-button_clear_px), and a side under 1 keeps the outer rectangle. The child runs left to right and is clipped to the outer rectangle.
The mark is `bb_fold_mark` of false, so the glyph is `^`, and the hover text is [`BB_SHOW_TIP`](#constant-bb_show_tip). A click sets `bars_open` true. The line starts 6 pixels after the mark and ends 16 pixels before the right edge, at the vertical center. It is drawn only when that span is positive, with a 1 pixel stroke from `bb_fold_line_ink`. `master_top` is the top of the row. `bar` is the outer rectangle.

| | |
| --- | --- |
| **Specification** | PK-1069 docs/specs/common/formal-plan.spec.md FINESSE:f-223 |

### paint_canvas_footer

<a id="function-paint_canvas_footer"></a>

```rust
fn paint_canvas_footer(
    ui: &mut egui::Ui,
    allow_delete: bool,
    tools_open: &mut bool,
    tool_needle: &mut String,
    mood: &MoodYaml,
    row_field: &mut String,
    users: &UserMenu,
) -> FooterHit
```

The function `paint_canvas_footer` paints the footer with the bars open.
The surface `ui` is the parent. The flag `allow_delete` is passed through. The flag `tools_open` is the tool row. The string `tool_needle` is the search field. The record `mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md). The string `row_field` is the row count. The menu `users` is the [`UserMenu`](chrome/struct.UserMenu.md). The result is the [`FooterHit`](chrome/struct.FooterHit.md) from `paint_bb_bars`.

This call starts `bars_open` at true. `paint_bb_bars` paints from a bottom-up layout. One rounded square wraps both rows. The tool row is painted first, so it sits under the master row. The spinner sits on the control bar. The combo stays.

### paint_bb_bars

<a id="function-paint_bb_bars"></a>

```rust
fn paint_bb_bars(
    ui: &mut egui::Ui,
    allow_delete: bool,
    tools_open: &mut bool,
    tool_needle: &mut String,
    mood: &MoodYaml,
    row_field: &mut String,
    users: &UserMenu,
    bars_open: &mut bool,
) -> FooterHit
```

Paint the footer from a bottom-up layout.
One rounded square wraps both rows while the parent is open.
Folded, the thinking line replaces that square.

| | |
| --- | --- |
| **Specification** | PK-1069 docs/specs/common/formal-plan.spec.md FINESSE:f-223 |

### search_turn

<a id="function-search_turn"></a>

```rust
fn search_turn(cards: &[TurnCard], needle: &str) -> Option<SearchSeat>
```

The function `search_turn` searches a card list for text in the prompt or the reply.
`cards` is that list of [`TurnCard`](chrome/struct.TurnCard.md) values.
`needle` is the text to find.

If `needle` is empty, the function returns `None`.
The function tests each card with `find_hit` on the prompt and the reply.
That test lowercases both sides, so the match ignores case.
The fault text is not read.

The first hit keeps its index. Each hit adds one to the count.
The return is a [`SearchSeat`](chrome/struct.SearchSeat.md) with that index and that count.
If no card hits, the function returns `None`.

### search_word

<a id="function-search_word"></a>

```rust
fn search_word(seat: Option<&SearchSeat>) -> String
```

The function `search_word` turns a search result into status text.
`seat` is the [`SearchSeat`](chrome/struct.SearchSeat.md) from a search, or `None` when nothing matched.

If `seat` is `None`, the text is `no hits`.
If `seat` is present, the text is `search` and then the hit count.

### keep_word

<a id="function-keep_word"></a>

```rust
fn keep_word(raw: &str) -> KeepBurners
```

The function `keep_word` maps an export answer onto [`KeepBurners`](chrome/enum.KeepBurners.md).
`raw` is the word the chooser printed.

The function trims `raw` before it matches.
The word `Yes` returns [`KeepBurners::Yes`](chrome/enum.KeepBurners.md#member-yes).
The word `No` returns [`KeepBurners::No`](chrome/enum.KeepBurners.md#member-no).
Any other word, including a blank word, returns [`KeepBurners::Cancel`](chrome/enum.KeepBurners.md#member-cancel).

### export_esc

<a id="function-export_esc"></a>

```rust
fn export_esc(raw: &str) -> String
```

Swing `esc`. A carriage return is dropped. A newline becomes `\n`. A backslash doubles.

### mark_token

<a id="function-mark_token"></a>

```rust
fn mark_token(mark: Mark) -> &'static str
```

The function `mark_token` prints a [`Mark`](chrome/enum.Mark.md) as the stored state word.
`mark` is the card state.

[`Mark::Included`](chrome/enum.Mark.md#member-included) returns `INCLUDED`.
[`Mark::Excluded`](chrome/enum.Mark.md#member-excluded) returns `EXCLUDED`.
[`Mark::Input`](chrome/enum.Mark.md#member-input) returns `INPUT_ONLY`.
[`Mark::Output`](chrome/enum.Mark.md#member-output) returns `OUTPUT_ONLY`.

### export_status_token

<a id="function-export_status_token"></a>

```rust
fn export_status_token(card: &TurnCard) -> &'static str
```

A stored fault is FAILED. A quiet card with a reply is COMPLETED.
CANCELLED stays off this client. A stop that left no reply and no fault stays empty.

### group_token

<a id="function-group_token"></a>

```rust
fn group_token(group: u64) -> String
```

An ordinary card is empty. A grouped card is the number this client stores.

### vault_status_of

<a id="function-vault_status_of"></a>

```rust
fn vault_status_of(card: &TurnCard) -> String
```

A running card stores RUNNING. A fault stores FAILED.
A quiet card keeps INTERRUPTED, FAILED, CANCELLED, and COMPLETED.
A quiet reply with no stored token stores COMPLETED. A quiet empty card stores empty.

### mark_of

<a id="function-mark_of"></a>

```rust
fn mark_of(state: &str) -> Mark
```

The function `mark_of` reads a stored state word back into a [`Mark`](chrome/enum.Mark.md).
`state` is that word. The match is exact, including case.

The word `EXCLUDED` returns [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded).
The word `INPUT_ONLY` returns [`Mark::Input`](chrome/enum.Mark.md#member-input).
The word `OUTPUT_ONLY` returns [`Mark::Output`](chrome/enum.Mark.md#member-output).
Any other word, including `INCLUDED` and a blank word, returns [`Mark::Included`](chrome/enum.Mark.md#member-included).

### parse_cell_text

<a id="function-parse_cell_text"></a>

```rust
fn parse_cell_text(raw: &str) -> Option<[u8; 16]>
```

`cell_text` back into 16 bytes. Any other shape stays off the deck.

### cell_from_card

<a id="function-cell_from_card"></a>

```rust
fn cell_from_card(canvas_id: &str, index: usize, card: &TurnCard) -> crate::vault::CellRow
```

The function `cell_from_card` builds one `crate::vault::CellRow` from a canvas card.
`canvas_id` is the canvas id stored on the row.
`index` is the zero-based place of the card.
`card` is the [`TurnCard`](chrome/struct.TurnCard.md) being stored.

The row id is the text of the card's 16-byte cell.
The turn index is `index` plus one, stored as a signed integer.
If the branch id is empty, the row stores `main`.
If the branch id is not empty, the row stores that id.
The column is stored as a signed integer.
The state word comes from `mark_token`.
The group id comes from `group_token`.
The execution status comes from `vault_status_of`.

The parent id, the target, the prompt, and the reply are copied onto the row.
The collapsed flag, the ephemeral flag, and the created time are copied.
The locked flag is stored as `head_lock`.
The readable flag is set to true.

### card_from_cell

<a id="function-card_from_cell"></a>

```rust
fn card_from_cell(cell: &crate::vault::CellRow) -> Option<TurnCard>
```

The function `card_from_cell` rebuilds a [`TurnCard`](chrome/struct.TurnCard.md) from one stored row.
`cell` is the `crate::vault::CellRow`.
If `parse_cell_text` cannot read the row id, the function returns `None`.

An empty group id becomes group 0.
A group id that is not a whole number also becomes 0.
A negative column becomes 0.
Any other column is kept as an unsigned value.
If the branch id is empty, the card stores `main`.
If the branch id is not empty, the card stores that id.

The prompt is the user text. The reply is the assistant text.
The mark comes from `mark_of` on the stored state.
The fault starts empty. The card is not running. The start time is absent.
The Ollama, Grok, and pool switches start off, and the Ollama level and id start empty.
The bar starts closed. Expanded view starts off.
The vault status is the stored execution status.
The parent id, the collapsed flag, the lock, the ephemeral flag, the target, and the created time are copied.

### cards_from_canvas

<a id="function-cards_from_canvas"></a>

```rust
fn cards_from_canvas(canvas: &crate::vault::CanvasRow) -> (Vec<TurnCard>, Vec<String>)
```

The function `cards_from_canvas` turns a stored canvas into cards and rejected ids.
`canvas` is the `crate::vault::CanvasRow`.

Each cell is passed to `card_from_cell`.
A cell that becomes a [`TurnCard`](chrome/struct.TurnCard.md) is appended to the card list.
A cell that does not is appended to the second list as its id text.
The pair keeps both lists in file order.

### seat_from_hits

<a id="function-seat_from_hits"></a>

```rust
fn seat_from_hits(cards: &[TurnCard], hits: &[String]) -> Option<SearchSeat>
```

First deck card whose id is in the file hits. The count is the file count.

### export_toon

<a id="function-export_toon"></a>

```rust
fn export_toon(name: &str, cards: &[TurnCard], keep: bool) -> String
```

Version 1 TOON. The parent field is empty. The branch is main.
A burner body stays only when `keep` is true. The cards stay as they are.

| | |
| --- | --- |
| **Specification** | PK-1290 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md |

### export_answer

<a id="function-export_answer"></a>

```rust
fn export_answer(raw: &str, cards: &[TurnCard]) -> Option<String>
```

Cancel writes nothing. Yes and No build the file text. The cards stay.

### write_export

<a id="function-write_export"></a>

```rust
fn write_export(path: &std::path::Path, body: &str) -> Result<(), String>
```

The function `write_export` writes an export body to a file.
`path` is the file path.
`body` is the text to write.

The bytes written are the bytes of `body`.
A successful write returns `Ok`.
A failed write returns `Err` with the error text.

### land_export

<a id="function-land_export"></a>

```rust
fn land_export(cards: &[TurnCard], keep: bool, path: &std::path::Path) -> String
```

The status word after the bytes land. A miss names the reason.

### chooser_line

<a id="function-chooser_line"></a>

```rust
fn chooser_line(script: &str) -> Result<String, String>
```

The function `chooser_line` runs a PowerShell script and returns its first output line.
`script` is the PowerShell command text.

The process is `powershell.exe` with `-NoProfile`, `-STA`, and `-Command`.
If the process cannot start, the error text is returned as `Err`.
If the process exits with a failure, the trimmed standard error is returned as `Err`.
If that error text is empty, the text is `the chooser did not open`.

A successful exit returns the first line of standard output, trimmed.
If that output has no line, the return is an empty string.

### ask_keep_burners

<a id="function-ask_keep_burners"></a>

```rust
fn ask_keep_burners() -> Result<KeepBurners, String>
```

The function `ask_keep_burners` asks whether burner bodies should be kept in an export.
The chooser is a Yes, No, and Cancel box.
The box text is `Keep burner bodies?`.

The printed word is mapped with `keep_word`.
The return is that [`KeepBurners`](chrome/enum.KeepBurners.md) value.
If the chooser fails, the error text is returned as `Err`.

### pick_export_path

<a id="function-pick_export_path"></a>

```rust
fn pick_export_path() -> Result<Option<std::path::PathBuf>, String>
```

The function `pick_export_path` asks for a path for a canvas export file.
The dialog title is `Export`.
The filter offers `*.aetheris` files and all files.
The default extension is `aetheris`.

An empty printed line means cancel, and the return is `Ok` with `None`.
Any other line is returned as `Ok` with that path.
If the chooser fails, the error text is returned as `Err`.

### session_export_file

<a id="function-session_export_file"></a>

```rust
fn session_export_file(body: &[u8]) -> Option<SessionFile>
```

`None` when the body is not a session export. The table text keeps its header line.

### session_export_file_of

<a id="function-session_export_file_of"></a>

```rust
fn session_export_file_of(piece: &ScopePiece) -> Option<SessionFile>
```

The function `session_export_file_of` reads a session-export reply from one scope piece.
`piece` is a [`ScopePiece`](amwp/enum.ScopePiece.md).

If `piece` is not [`ScopePiece::Later`](amwp/enum.ScopePiece.md#member-later), the function returns `None`.
A later piece is passed to `session_export_file` by its body.
The helper returns `None` when the bytes are not UTF-8 or the `session_export_result:` prefix is missing.
It also returns `None` when the rest is neither a failure nor a message table.
Text after `export failed:` becomes [`SessionFile::Failed`](chrome/enum.SessionFile.md#member-failed) with the trimmed reason.
Text that begins with `messages[` becomes [`SessionFile::Table`](chrome/enum.SessionFile.md#member-table) and keeps its header line.

### session_replace_file

<a id="function-session_replace_file"></a>

```rust
fn session_replace_file(body: &[u8]) -> Option<Result<(), String>>
```

`Ok` is the success line. `Err` is the text after `replace failed:`.
`None` when the body is not a replace reply.

### session_replace_file_of

<a id="function-session_replace_file_of"></a>

```rust
fn session_replace_file_of(piece: &ScopePiece) -> Option<Result<(), String>>
```

The function `session_replace_file_of` reads a session-replace reply from one scope piece.
`piece` is a [`ScopePiece`](amwp/enum.ScopePiece.md).

If `piece` is not [`ScopePiece::Later`](amwp/enum.ScopePiece.md#member-later), the function returns `None`.
A later piece is passed to `session_replace_file` by its body.
The helper returns `None` when the bytes are not UTF-8 or the `session_replace_result:` prefix is missing.
It also returns `None` when the first line of the rest is not `ok` and does not start with `replace failed:`.
Text after `replace failed:` is returned as `Err` with the trimmed reason.
A first line of `ok` is returned as `Ok`.

### session_tokens_of

<a id="function-session_tokens_of"></a>

```rust
fn session_tokens_of(piece: &ScopePiece) -> Option<(i32, i32, i32)>
```

`used`, `chars`, and `blocks` from `session_tokens_result:`.
None when the body is not that reply.

| | |
| --- | --- |
| **Specification** | L40 aa-sdd-source-prose.md FINESSE:f-163 |

### parse_message_table

<a id="function-parse_message_table"></a>

```rust
fn parse_message_table(table: &str) -> Result<Vec<(String, String)>, &'static str>
```

Role and text. A bad header, a bad row, or a count that misses the rows is an error.

### pair_session

<a id="function-pair_session"></a>

```rust
fn pair_session(table: &str) -> Result<Vec<(String, String)>, &'static str>
```

User then assistant is one cell. A lone user has an empty reply. A lone assistant has an empty prompt.
System and tool rows stay out of the cells.

### split_message_row

<a id="function-split_message_row"></a>

```rust
fn split_message_row(line: &str) -> Option<(String, String)>
```

The function `split_message_row` splits one message-table row into a role and a text field.
`line` is that row.
The return is `None` when the row does not have the expected commas or the role is not a name.

Commas inside a quoted field do not count. A backslash inside quotes skips the next character.
The role is the text before the first comma outside quotes.
The text field is everything after the third comma outside quotes.
If either comma is missing, the function returns `None`.
If the first comma is the first character, the function returns `None`.

The role must be non-empty. Each role character must be an ASCII lowercase letter or `_`.
The text field is passed through `unquote_field`.

### unquote_field

<a id="function-unquote_field"></a>

```rust
fn unquote_field(field: &str) -> String
```

The function `unquote_field` removes quotes and escape sequences from one table field.
`field` is that field text.

If `field` is shorter than two characters, it is returned unchanged.
If `field` does not start with `"`, it is returned unchanged.
A closing `"` at the end is not copied. A field with no closing quote copies through the last character.

Inside the quotes, `\n` becomes a newline. `\r` becomes a carriage return. `\t` becomes a tab.
`\"` becomes `"`. `\\` becomes `\`.
Any other backslash is copied, and the character after it is copied on the next step.

### quote_field

<a id="function-quote_field"></a>

```rust
fn quote_field(text: &str) -> String
```

The function `quote_field` quotes a table field when the text needs quotes.
`text` is the field text.

An empty string is quoted.
A string that contains `,`, `"`, a newline, a carriage return, `:`, ``, or`` is quoted.
Any other string is returned unchanged.

A quoted string is wrapped in `"`.
`\` and `"` are each prefixed with `\`.
A newline becomes `\n`. A carriage return becomes `\r`. A tab becomes `\t`.

### table_from_cards

<a id="function-table_from_cards"></a>

```rust
fn table_from_cards(cards: &[TurnCard]) -> String
```

One user row and one assistant row per cell that has text. An empty cell stays out.
A merge group is one pair. The user row joins the input prompts.
The assistant row is the merge reply. JOIN lines stay.
// @aa-sdd PK-1168 docs/specs/java/org/aetheris/swing/swing.spec.md FINESSE:f-213

### push_transfer_pair

<a id="function-push_transfer_pair"></a>

```rust
fn push_transfer_pair(body: &mut String, count: &mut usize, prompt: &str, reply: &str)
```

The function `push_transfer_pair` appends one user row and one assistant row to a transfer table.
`body` is the table text being built.
`count` is the running row count.
`prompt` is the user text. `reply` is the assistant text.

The user row is `user,0,0,` and then the quoted prompt.
The assistant row is `assistant,0,0,` and then the quoted reply.
Each row ends with a newline.
`count` increases by two.

### transfer_inputs

<a id="function-transfer_inputs"></a>

```rust
fn transfer_inputs(cards: &[TurnCard], group: u64) -> String
```

Input prompts of one merge, in deck order, joined by a newline.
An empty prompt stays out.
// @aa-sdd PK-1168 docs/specs/java/org/aetheris/swing/swing.spec.md FINESSE:f-213

### transfer_reply

<a id="function-transfer_reply"></a>

```rust
fn transfer_reply(cards: &[TurnCard], group: u64) -> String
```

The merge card's stored reply. JOIN lines stay.
// @aa-sdd PK-1168 docs/specs/java/org/aetheris/swing/swing.spec.md FINESSE:f-213

### seat_transferred

<a id="function-seat_transferred"></a>

```rust
fn seat_transferred(
    cards: &mut Vec<TurnCard>,
    focus: &mut usize,
    seq: &mut u64,
    pairs: &[(String, String)],
)
```

The copied cells replace the deck. An empty list leaves one empty card.

### ask_ok

<a id="function-ask_ok"></a>

```rust
fn ask_ok(title: &str, body: &str) -> Result<bool, String>
```

OK continues. Cancel is false. A dialog that does not open is an error.

### pick_session_export_path

<a id="function-pick_session_export_path"></a>

```rust
fn pick_session_export_path() -> Result<Option<std::path::PathBuf>, String>
```

The function `pick_session_export_path` asks for a path for a session export file.
The dialog title is `Export Session`.
The filter offers `*.toon` files and all files.
The default extension is `toon`.

An empty printed line means cancel, and the return is `Ok` with `None`.
Any other line is returned as `Ok` with that path.
If the chooser fails, the error text is returned as `Err`.

| | |
| --- | --- |
| **Specification** | PK-1291 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md |

### land_session_export

<a id="function-land_session_export"></a>

```rust
fn land_session_export(path: &std::path::Path, body: &str) -> String
```

The status word after the Roux table lands. A miss names the reason.

### begin_export

<a id="function-begin_export"></a>

```rust
fn begin_export(cards: &[TurnCard]) -> Option<String>
```

Cancel on either chooser returns None and leaves the status. The cards stay.

### filed_new

<a id="function-filed_new"></a>

```rust
fn filed_new(file_id: String) -> FiledCell
```

The function `filed_new` builds a blank [`FiledCell`](chrome/struct.FiledCell.md) for one id from an import file.
`file_id` is that id.

The cell is readable. The mark is [`Mark::Included`](chrome/enum.Mark.md#member-included).
The prompt, the reply, the group key, and the target start empty.
The column is 0.
The ephemeral, collapsed, and head-lock flags start false.

### import_unesc

<a id="function-import_unesc"></a>

```rust
fn import_unesc(raw: &str) -> String
```

Swing `unesc`. `\\` becomes `\`. `\n` becomes a newline. Any other backslash stays.

### import_i32

<a id="function-import_i32"></a>

```rust
fn import_i32(raw: &str) -> Option<i32>
```

The function `import_i32` parses one import field as a signed integer.
`raw` is that field text.

A whole number in the `i32` range returns `Some` of that number.
Any other text returns `None`.

### import_column

<a id="function-import_column"></a>

```rust
fn import_column(raw: &str) -> u32
```

A negative column, or a number that does not fit, lands as 0.

### import_truth

<a id="function-import_truth"></a>

```rust
fn import_truth(raw: &str) -> bool
```

Swing `truth`. `true`, `yes`, and `1`.

### filed_state

<a id="function-filed_state"></a>

```rust
fn filed_state(token: &str) -> (bool, Mark)
```

Blank is Included. GHOST is Excluded. An unknown token is Included and unreadable.

### filed_kept

<a id="function-filed_kept"></a>

```rust
fn filed_kept(cell: &FiledCell) -> bool
```

Readable, not Excluded, and not an ephemeral cell with both bodies blank.

### parse_filed

<a id="function-parse_filed"></a>

```rust
fn parse_filed(raw: &str) -> Option<Vec<FiledCell>>
```

Version 1 cells. `None` is a missing version or a version other than 1.
A version 1 file with zero cells is an empty list.

### rewrite_k

<a id="function-rewrite_k"></a>

```rust
fn rewrite_k(text: &str, k_map: &[(i32, i32)]) -> String
```

Swing `rewriteK`. A mapped k is rewritten. A bad number stays. A missing closer keeps the tail.
The map counts kept cells in the file.

### kept_k_map

<a id="function-kept_k_map"></a>

```rust
fn kept_k_map(filed: &[FiledCell], copies: &[TurnCard]) -> Vec<(i32, i32)>
```

Old kept place to new kept place. Both lists follow the file order.

### fresh_group

<a id="function-fresh_group"></a>

```rust
fn fresh_group(
    key: &str,
    cards: &[TurnCard],
    assigned: &mut Vec<(String, u64)>,
    seq: &mut u64,
) -> u64
```

A non-empty group string mints one fresh number. An empty group stays 0.
The number does not join a group already on the canvas.

### import_copies

<a id="function-import_copies"></a>

```rust
fn import_copies(raw: &str, cards: &[TurnCard], seq: &mut u64) -> Option<Vec<TurnCard>>
```

Version 1 cells as copies. `None` is a bad version or zero cells.
The pipe number counts kept cells in the file. Cards already here do not shift it.
The id in the file is not reused.

### seat_canvas_import

<a id="function-seat_canvas_import"></a>

```rust
fn seat_canvas_import(cards: &mut Vec<TurnCard>, seq: &mut u64, bytes: &[u8]) -> String
```

Over 1 MiB is refused. A bad version is refused. The copies are appended. The earlier cards stay.

### pick_canvas_file

<a id="function-pick_canvas_file"></a>

```rust
fn pick_canvas_file() -> Result<Option<std::path::PathBuf>, String>
```

The function `pick_canvas_file` asks for a path to a canvas import file.
The dialog title is `Import`.
The filter offers `*.aetheris` files and all files.

An empty printed line means cancel, and the return is `Ok` with `None`.
Any other line is returned as `Ok` with that path.
If the chooser fails, the error text is returned as `Err`.

### land_canvas_import

<a id="function-land_canvas_import"></a>

```rust
fn land_canvas_import(cards: &mut Vec<TurnCard>, seq: &mut u64, path: &std::path::Path) -> String
```

The status word after the copies land. A miss names the reason.

### begin_canvas_import

<a id="function-begin_canvas_import"></a>

```rust
fn begin_canvas_import(cards: &mut Vec<TurnCard>, seq: &mut u64) -> Option<String>
```

Cancel writes nothing. A miss names the reason. The armed card stays.

### duplicate_group

<a id="function-duplicate_group"></a>

```rust
fn duplicate_group(cards: &mut Vec<TurnCard>, focus: usize, seq: &mut u64) -> Option<usize>
```

Copy the armed group to the next place. Members stay in deck order, then the merge.
The block is inserted after the last card of that group. Prompts and replies are empty.
The source group stays. No group returns None and leaves the deck alone.

### seat_duplicate

<a id="function-seat_duplicate"></a>

```rust
fn seat_duplicate(
    cards: &mut Vec<TurnCard>,
    focus: usize,
    seq: &mut u64,
    join_from: &mut Option<usize>,
) -> &'static str
```

The menu and Ctrl+Shift+G share this seat. The word is the status line.

### ungroup_card

<a id="function-ungroup_card"></a>

```rust
fn ungroup_card(cards: &mut [TurnCard], index: usize)
```

Ungroup one card. A merge with no members left becomes an ordinary card.

### settle_merge

<a id="function-settle_merge"></a>

```rust
fn settle_merge(cards: &mut [TurnCard], group: u64)
```

The function `settle_merge` writes the joined member replies onto the merge card.
`cards` is the card list.
`group` is the group number.

If `group` is 0, the function returns without a change.
A member is a card in that group whose column is greater than 0 and whose mark is not [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded).
The function collects those replies and targets in list order.
`concat_join` turns them into the merge text, including the `JOIN` lines.

The merge card is the card in that group whose column is 0.
If that card exists, its reply becomes the joined text and its fault is cleared.
If no merge card exists, the list is left unchanged.

### fail_merge

<a id="function-fail_merge"></a>

```rust
fn fail_merge(cards: &mut [TurnCard], group: u64)
```

The function `fail_merge` marks the merge card of one group as failed.
`cards` is the card list.
`group` is the group number.

If `group` is 0, the function returns without a change.
The merge card is the card in that group whose column is 0.
If that card exists, its reply is cleared and its fault becomes `FAILED`.
If no merge card exists, the list is left unchanged.

### halt_run

<a id="function-halt_run"></a>

```rust
fn halt_run(
    cards: &mut [TurnCard],
    focus: usize,
    group_live: &mut u64,
    group_rest: &mut Vec<([u8; 16], Vec<u8>)>,
) -> Option<Halt>
```

Cancel one running card. The reply stays.
A focused running card is the one that stops. Other members keep running.
A later member of the live group stays queued.
An empty queue leaves the merge as it stands when no member is still running.
None when every card is quiet.

### group_after

<a id="function-group_after"></a>

```rust
fn group_after(
    cards: &mut [TurnCard],
    live: &mut u64,
    rest: &mut Vec<([u8; 16], Vec<u8>)>,
    running_before: Option<[u8; 16]>,
    pieces: &[ScopePiece],
) -> Option<([u8; 16], Vec<u8>)>
```

After a member ends or faults. The merge waits until every member is quiet.
A queued frame still leaves when no member is running.

### expanded_yaml_path

<a id="function-expanded_yaml_path"></a>

```rust
fn expanded_yaml_path(home: &std::path::Path) -> std::path::PathBuf
```

The function `expanded_yaml_path` builds the path of the expanded-view file.
`home` is the home directory.

The path is `home`, then `.casserole`, then `aetheris-expanded.yaml`.

### nil_cell_id

<a id="function-nil_cell_id"></a>

```rust
fn nil_cell_id(id: &str) -> bool
```

The function `nil_cell_id` reports whether a cell id text should be skipped.
`id` is that text.

An empty string returns true.
The text `00000000-0000-0000-0000-000000000000` returns true, ignoring ASCII case.
Any other text returns false.

### push_expanded_id

<a id="function-push_expanded_id"></a>

```rust
fn push_expanded_id(cells: &mut Vec<String>, key: &str)
```

The function `push_expanded_id` appends one cell id to the expanded-view list.
`cells` is that list.
`key` is the id text.

The function trims `key` before it tests the id.
A nil id is not appended. `nil_cell_id` defines that test.
An id already in the list, ignoring ASCII case, is not appended.
Any other id is appended as owned text.

### seat_inline_expanded

<a id="function-seat_inline_expanded"></a>

```rust
fn seat_inline_expanded(raw: &str, cells: &mut Vec<String>) -> bool
```

`{ id: true, id2: true }`. A pair that is not a key blocks the file.

### parse_expanded_yaml

<a id="function-parse_expanded_yaml"></a>

```rust
fn parse_expanded_yaml(text: &str) -> ExpandedYaml
```

A missing file is fresh. An empty file, or any version other than 1, is blocked.

### render_expanded_yaml

<a id="function-render_expanded_yaml"></a>

```rust
fn render_expanded_yaml(yaml: &ExpandedYaml) -> String
```

The function `render_expanded_yaml` writes an [`ExpandedYaml`](chrome/struct.ExpandedYaml.md) as version-1 text.
`yaml` is that value.

The text starts with `version: 1`.
If the cell list is empty, the next line is `cells: {}`.
If the cell list is not empty, the next line is `cells:` and each id is a line `  id: true`.
The ids are sorted before they are written.
The extra text is appended after the cells.

### read_expanded_yaml

<a id="function-read_expanded_yaml"></a>

```rust
fn read_expanded_yaml(path: &std::path::Path) -> ExpandedYaml
```

The function `read_expanded_yaml` loads an [`ExpandedYaml`](chrome/struct.ExpandedYaml.md) from a file.
`path` is that file.

If the file cannot be read, the return is [`ExpandedYaml::fresh`](chrome/struct.ExpandedYaml.md#method-fresh).
If the file is empty, the return is [`ExpandedYaml::blocked`](chrome/struct.ExpandedYaml.md#method-blocked).
Any other file is parsed with `parse_expanded_yaml`.
Bytes that are not UTF-8 are read with the replacement character.

### write_expanded_yaml

<a id="function-write_expanded_yaml"></a>

```rust
fn write_expanded_yaml(path: &std::path::Path, yaml: &ExpandedYaml) -> bool
```

A blocked file is left alone.

### load_expanded_file

<a id="function-load_expanded_file"></a>

```rust
fn load_expanded_file() -> ExpandedYaml
```

The function `load_expanded_file` loads the expanded-view file from the home directory.
If `home_dir` returns nothing, the return is [`ExpandedYaml::fresh`](chrome/struct.ExpandedYaml.md#method-fresh).
Otherwise the file is `expanded_yaml_path` of that directory, read by `read_expanded_yaml`.

### hide_yaml_path

<a id="function-hide_yaml_path"></a>

```rust
fn hide_yaml_path(home: &std::path::Path) -> std::path::PathBuf
```

The function `hide_yaml_path` builds the path of the hide-collapsed file.
`home` is the home directory.

The path is `home`, then `.casserole`, then `aetheris-hide.yaml`.

### hide_on

<a id="function-hide_on"></a>

```rust
fn hide_on(value: &str) -> Option<bool>
```

The function `hide_on` reads a yes-or-no yaml value as a flag.
`value` is the text after the colon.

The text is reduced with `yaml_scalar` before the test.
`think_flag` accepts `true`, `yes`, and `1`, and those return `Some(true)`.
The words `false` and `no` return `Some(false)`, ignoring ASCII case.
The word `0` returns `Some(false)`.
Any other text returns `None`.

### parse_hide_yaml

<a id="function-parse_hide_yaml"></a>

```rust
fn parse_hide_yaml(text: &str) -> HideYaml
```

A missing file is Show. An empty file, or any version other than 1, is blocked.

### render_hide_yaml

<a id="function-render_hide_yaml"></a>

```rust
fn render_hide_yaml(yaml: &HideYaml) -> String
```

The function `render_hide_yaml` writes a [`HideYaml`](chrome/struct.HideYaml.md) as version-1 text.
`yaml` is that value.

The text starts with `version: 1`.
The next line is `hide_collapsed: true` when the hide flag is set.
The next line is `hide_collapsed: false` when the hide flag is clear.
The extra text is appended after that line.

### read_hide_yaml

<a id="function-read_hide_yaml"></a>

```rust
fn read_hide_yaml(path: &std::path::Path) -> HideYaml
```

The function `read_hide_yaml` loads a [`HideYaml`](chrome/struct.HideYaml.md) from a file.
`path` is that file.

If the file cannot be read, the return is [`HideYaml::fresh`](chrome/struct.HideYaml.md#method-fresh).
If the file is empty, the return is [`HideYaml::blocked`](chrome/struct.HideYaml.md#method-blocked).
Any other file is parsed with `parse_hide_yaml`.
Bytes that are not UTF-8 are read with the replacement character.

### write_hide_yaml

<a id="function-write_hide_yaml"></a>

```rust
fn write_hide_yaml(path: &std::path::Path, yaml: &HideYaml) -> bool
```

A blocked file is left alone.

### load_hide_file

<a id="function-load_hide_file"></a>

```rust
fn load_hide_file() -> HideYaml
```

The function `load_hide_file` loads the hide-collapsed file from the home directory.
If `home_dir` returns nothing, the return is [`HideYaml::fresh`](chrome/struct.HideYaml.md#method-fresh).
Otherwise the file is `hide_yaml_path` of that directory, read by `read_hide_yaml`.

### settings_yaml_path

<a id="function-settings_yaml_path"></a>

```rust
fn settings_yaml_path(home: &std::path::Path) -> std::path::PathBuf
```

The function `settings_yaml_path` builds the path of the window-settings file.
`home` is the home directory.

The path is `home`, then `.casserole`, then `aetheris-settings.yaml`.

### parse_settings_yaml

<a id="function-parse_settings_yaml"></a>

```rust
fn parse_settings_yaml(text: &str) -> SettingsYaml
```

A missing file is the quiet plate. An empty file, or any version other than 1, is blocked.

### render_settings_yaml

<a id="function-render_settings_yaml"></a>

```rust
fn render_settings_yaml(yaml: &SettingsYaml) -> String
```

The function `render_settings_yaml` writes a [`SettingsYaml`](chrome/struct.SettingsYaml.md) as version-1 text.
`yaml` is that value.

The text starts with `version: 1`.
The next lines are `painted_borders`, `debug_mood`, and `tooltips`.
Each line is `true` when that flag is set and `false` when it is clear.
The extra text is appended after those lines.

### read_settings_yaml

<a id="function-read_settings_yaml"></a>

```rust
fn read_settings_yaml(path: &std::path::Path) -> SettingsYaml
```

The function `read_settings_yaml` loads a [`SettingsYaml`](chrome/struct.SettingsYaml.md) from a file.
`path` is that file.

If the file cannot be read, the return is [`SettingsYaml::fresh`](chrome/struct.SettingsYaml.md#method-fresh).
If the file is empty, the return is [`SettingsYaml::blocked`](chrome/struct.SettingsYaml.md#method-blocked).
Any other file is parsed with `parse_settings_yaml`.
Bytes that are not UTF-8 are read with the replacement character.

### write_settings_yaml

<a id="function-write_settings_yaml"></a>

```rust
fn write_settings_yaml(path: &std::path::Path, yaml: &SettingsYaml) -> bool
```

A blocked file is left alone.

### load_settings_file

<a id="function-load_settings_file"></a>

```rust
fn load_settings_file() -> SettingsYaml
```

The function `load_settings_file` loads the window-settings file from the home directory.
If `home_dir` returns nothing, the return is [`SettingsYaml::fresh`](chrome/struct.SettingsYaml.md#method-fresh).
Otherwise the file is `settings_yaml_path` of that directory, read by `read_settings_yaml`.

### expanded_on

<a id="function-expanded_on"></a>

```rust
fn expanded_on(yaml: &ExpandedYaml, id: &str) -> bool
```

The function `expanded_on` reports whether one cell id is checked in the expanded-view file.
`yaml` is the [`ExpandedYaml`](chrome/struct.ExpandedYaml.md).
`id` is the cell id text.

A blocked file returns false.
A file whose cell list contains `id`, ignoring ASCII case, returns true.
Any other file returns false.

### note_expanded

<a id="function-note_expanded"></a>

```rust
fn note_expanded(yaml: &mut ExpandedYaml, id: &str, on: bool) -> bool
```

Check adds the id. Uncheck drops it. A match writes nothing. A nil id stays out.

### forget_expanded

<a id="function-forget_expanded"></a>

```rust
fn forget_expanded(yaml: &mut ExpandedYaml, ids: &[String]) -> bool
```

Deleted cards drop their ids. An absent id stays absent.

### seat_expanded

<a id="function-seat_expanded"></a>

```rust
fn seat_expanded(cards: &mut [TurnCard], yaml: &ExpandedYaml)
```

A saved id is checked. Any other id is unchecked. A blocked file leaves the cards.

### append_turn

<a id="function-append_turn"></a>

```rust
fn append_turn(cards: &mut Vec<TurnCard>, seq: &mut u64) -> usize
```

Append the next empty card. The returned index is the new focus.
The box follows the last card. An empty deck starts unchecked.

### begin_run

<a id="function-begin_run"></a>

```rust
fn begin_run(card: &mut TurnCard)
```

The function `begin_run` marks one card as running and clears its last result.
`card` is that [`TurnCard`](chrome/struct.TurnCard.md).

The running flag becomes true.
The start time becomes the current instant.
The reply is cleared.
The fault is cleared.

### accept_reply

<a id="function-accept_reply"></a>

```rust
fn accept_reply(card: &mut TurnCard, cell: &[u8; 16], body: &[u8])
```

The function `accept_reply` appends one reply fragment to a running card.
`card` is the [`TurnCard`](chrome/struct.TurnCard.md).
`cell` is the 16-byte id the fragment belongs to.
`body` is the fragment bytes.

The fragment is appended only when the card is running, its cell matches `cell`, and `body` is not empty.
The bytes are read as UTF-8, and a bad byte becomes the replacement character.
If any of those three tests fails, the card stays as it was.

### accept_end

<a id="function-accept_end"></a>

```rust
fn accept_end(card: &mut TurnCard, cell: &[u8; 16])
```

The function `accept_end` stops a card when its run ends.
`card` is the [`TurnCard`](chrome/struct.TurnCard.md).
`cell` is the 16-byte id that ended.

If the card's cell matches `cell`, the running flag becomes false and the start time is cleared.
If the cells differ, the card stays as it was.
The reply and the fault are not changed.

### accept_fault

<a id="function-accept_fault"></a>

```rust
fn accept_fault(card: &mut TurnCard, cell: &[u8; 16], body: &[u8])
```

The function `accept_fault` records a fault on one card and stops that card.
`card` is the [`TurnCard`](chrome/struct.TurnCard.md).
`cell` is the 16-byte id the fault names.
`body` is the fault bytes.

A `cell` of sixteen zero bytes matches this card.
Any other `cell` matches only when it equals the card's cell.
A cell that matches neither test leaves the card unchanged.
An empty `body` leaves the card unchanged.

If the card already has a fault that does not end with a newline, a newline is inserted.
The bytes are then appended as UTF-8, and a bad byte becomes the replacement character.
The running flag becomes false.
The start time is cleared.

### clear_turn

<a id="function-clear_turn"></a>

```rust
fn clear_turn(card: &mut TurnCard)
```

The function `clear_turn` empties the text of one card and stops it.
`card` is that [`TurnCard`](chrome/struct.TurnCard.md).

The prompt, the reply, and the fault are cleared.
The running flag becomes false.
The start time is cleared.
The cell id, the mark, the group, and the other flags stay as they were.

### clear_deck

<a id="function-clear_deck"></a>

```rust
fn clear_deck(cards: &mut Vec<TurnCard>, focus: &mut usize)
```

Disconnect leaves one empty card.

### clear_plate

<a id="function-clear_plate"></a>

```rust
fn clear_plate(cards: &mut Vec<TurnCard>, focus: &mut usize, seq: &mut u64) -> Option<[u8; 16]>
```

Clear leaves one empty Included card and a new cell id.
The returned cell was running.

### removal_indexes

<a id="function-removal_indexes"></a>

```rust
fn removal_indexes(cards: &[TurnCard], focus: usize) -> Vec<usize>
```

The armed card, or every card of its group. Group 0 is one card.

### delete_armed

<a id="function-delete_armed"></a>

```rust
fn delete_armed(cards: &[TurnCard], focus: usize) -> bool
```

Delete stays off when the removal would leave the canvas empty.

### plan_delete

<a id="function-plan_delete"></a>

```rust
fn plan_delete(cards: &[TurnCard], focus: usize) -> Option<DeleteCut>
```

The card that remains where the first removed card sat. A tail removal lands on the last card.

### shift_index

<a id="function-shift_index"></a>

```rust
fn shift_index(index: usize, gone: &[usize]) -> Option<usize>
```

The function `shift_index` maps an index across a deletion.
`index` is the place before the deletion.
`gone` is the indexes that will be removed.

If `index` is one of the removed indexes, the function returns `None`.
Otherwise the return is `index` minus how many removed indexes sit before it.

### pipe_hits_delete

<a id="function-pipe_hits_delete"></a>

```rust
fn pipe_hits_delete(cards: &[TurnCard], pipe_live: bool, rest: &[usize], gone: &[usize]) -> bool
```

The running card still sits on the Swing queue. A named card clears the chain.

### group_hits_delete

<a id="function-group_hits_delete"></a>

```rust
fn group_hits_delete(
    cards: &[TurnCard],
    group_live: u64,
    rest: &[([u8; 16], Vec<u8>)],
    gone: &[usize],
) -> bool
```

The function `group_hits_delete` reports whether a deletion touches the live group run.
`cards` is the card list.
`group_live` is the group number that is running, or 0 when no group is running.
`rest` is the queued member frames, each a cell id and a body.
`gone` is the indexes that will be removed.

If `group_live` is 0, the function returns false.
It returns true when a removed card belongs to `group_live`.
It also returns true when a queued cell id equals a removed card's cell.
Otherwise it returns false.

### apply_delete

<a id="function-apply_delete"></a>

```rust
fn apply_delete(cards: &mut Vec<TurnCard>, focus: &mut usize, cut: &DeleteCut)
```

The function `apply_delete` removes the cards named by a [`DeleteCut`](chrome/struct.DeleteCut.md).
`cards` is the card list.
`focus` is the focused index, and it is replaced by the cut.
`cut` is the [`DeleteCut`](chrome/struct.DeleteCut.md).

A card stays when its index is not in `cut.gone`.
`focus` becomes `cut.focus`.
If that index is past the end of the shortened list, `focus` becomes the last remaining index.
An empty list leaves `focus` at 0.

### fold_mark

<a id="function-fold_mark"></a>

```rust
fn fold_mark(collapsed: bool) -> &'static str
```

`+` when the card is folded. `-` when it is open.

### fold_brief

<a id="function-fold_brief"></a>

```rust
fn fold_brief(prompt: &str, reply: &str) -> String
```

The first line of the reply. An empty reply uses the first line of the prompt.

### brief_width

<a id="function-brief_width"></a>

```rust
fn brief_width(leftover: f32, column: f32, member: bool) -> f32
```

Paint width of the folded line. The text stays stored.
An ordinary card uses the leftover inside the column.
A member stays at [`BRIEF_MIN`](#constant-brief_min), so the row does not set the column width.

### fold_all

<a id="function-fold_all"></a>

```rust
fn fold_all(cards: &mut [TurnCard], collapsed: bool)
```

The function `fold_all` sets the collapsed flag on every card.
`cards` is the card list.
`collapsed` is the flag to store.

Each card's collapsed flag becomes `collapsed`.
The prompt and the reply stay stored.

### fold_card

<a id="function-fold_card"></a>

```rust
fn fold_card(cards: &mut [TurnCard], index: usize, collapsed: bool) -> bool
```

One card. A missing index leaves the deck alone.

### collapse_keeps_the_card

<a id="function-collapse_keeps_the_card"></a>

```rust
fn collapse_keeps_the_card() -> bool
```

LOCK F-141. Collapse All, Expand All, and the per-cell fold keep the card.
A later plate must not make this return false. Hide Collapsed is the only
path that adds an id.

### release_fold

<a id="function-release_fold"></a>

```rust
fn release_fold(concealed: &mut Vec<[u8; 16]>, id: [u8; 16])
```

A fold releases the id. It never adds one.

### conceal_collapsed

<a id="function-conceal_collapsed"></a>

```rust
fn conceal_collapsed(cards: &[TurnCard]) -> Vec<[u8; 16]>
```

Hide Collapsed snapshots the folded ids. A fold must not call this.

### run_chord

<a id="function-run_chord"></a>

```rust
fn run_chord(enter: bool, ctrl: bool, running: bool, view_open: bool) -> bool
```

Ctrl+Enter runs. Plain Enter stays in the prompt. A busy card does not send.
An open reply window keeps the chord.

### duplicate_chord

<a id="function-duplicate_chord"></a>

```rust
fn duplicate_chord(g: bool, ctrl: bool, shift: bool) -> bool
```

Ctrl+Shift+G copies the armed group. Swing binds `VK_G` with Ctrl and Shift.

### notice_ink

<a id="function-notice_ink"></a>

```rust
fn notice_ink(status: &str) -> Option<egui::Color32>
```

Yellow when the status is the group miss. Other lines keep the plate ink.

### slide_skin

<a id="function-slide_skin"></a>

```rust
fn slide_skin(hard: bool) -> SlideSkin
```

Red for a hard miss. Yellow for any other miss.

### unesc_miss

<a id="function-unesc_miss"></a>

```rust
fn unesc_miss(raw: &str) -> String
```

Swing `unesc` for a reason or a message. `\\n` is a newline. `\\\\` is a slash.

### parse_miss

<a id="function-parse_miss"></a>

```rust
fn parse_miss(raw: &str) -> MissBody
```

Swing `decodeError`. A body that starts with `reason:` is structured.

### hard_miss

<a id="function-hard_miss"></a>

```rust
fn hard_miss(reason: &str, message: &str) -> bool
```

Swing `hardMiss`. Engine-down, too-large, a pool break, or a Grok transport line.

### banner_token

<a id="function-banner_token"></a>

```rust
fn banner_token(token: &str) -> bool
```

The function `banner_token` reports whether a fault word stays on the status line.
`token` is that word. The match is exact.

The words are `air-gap`, `engine-down`, `too-large`, `burner denied`, `pipe`, and `no-model`.
Any of those words returns true.
Any other word returns false.

### graceful_notice

<a id="function-graceful_notice"></a>

```rust
fn graceful_notice(miss: &MissBody) -> bool
```

A named banner on an ordinary card stays on the status line.

### slide_text

<a id="function-slide_text"></a>

```rust
fn slide_text(raw: &str) -> String
```

Empty garnered text becomes `execute failed`.

### fault_card

<a id="function-fault_card"></a>

```rust
fn fault_card<'a>(cards: &'a [TurnCard], cell: &[u8; 16], focus: usize) -> Option<&'a TurnCard>
```

The function `fault_card` finds the card a fault should land on.
`cards` is the card list.
`cell` is the 16-byte id the fault names.
`focus` is the focused index.

If `cell` is not sixteen zero bytes, the return is the card with that cell.
If no card has that cell, the return is `None`.
If `cell` is sixteen zero bytes, the return is the running card.
If no card is running, the return is the card at `focus`.
If that index is past the list, the return is `None`.

### seat_fault

<a id="function-seat_fault"></a>

```rust
fn seat_fault(cards: &[TurnCard], focus: usize, cell: &[u8; 16], body: &[u8]) -> Option<FaultSeat>
```

A wire miss. `cell-busy` stays a status word. A named banner on an ordinary
card stays a status word. A grouped member opens the notice. Anything else
opens the notice. A hard miss is red. Any other miss is yellow.

### close_slide

<a id="function-close_slide"></a>

```rust
fn close_slide(slide: &mut Option<ErrorNotice>)
```

Close drops the notice. The card text stays.

### pipe_chord

<a id="function-pipe_chord"></a>

```rust
fn pipe_chord(f5: bool, ctrl: bool, shift: bool) -> bool
```

Ctrl+Shift+F5 runs the chain. Swing binds `VK_F5` with Ctrl and Shift.

### sources_before

<a id="function-sources_before"></a>

```rust
fn sources_before(cards: &[TurnCard], target: usize) -> Vec<usize>
```

Kept cards before `target`. An excluded card is skipped. The same group is skipped.
`k` counts this list from 1. Cloud is off, so there is no burner skip.

### resolve_pipes

<a id="function-resolve_pipes"></a>

```rust
fn resolve_pipes(
    text: &str,
    cards: &[TurnCard],
    sources: &[usize],
    stack: &mut Vec<usize>,
) -> PipeBody
```

Replace `{{cell[k].output}}` with that earlier reply. A bad index, an empty reply,
a failed card, or a cycle blocks. A token with no closer stays as written.
The stored prompt is not this string.

### pipe_indexes

<a id="function-pipe_indexes"></a>

```rust
fn pipe_indexes(cards: &[TurnCard], target: usize) -> Vec<usize>
```

Named earlier cards in token order, then the armed card. A bad token is not queued.

### pipe_frame

<a id="function-pipe_frame"></a>

```rust
fn pipe_frame(
    cards: &[TurnCard],
    index: usize,
    mood: &MoodYaml,
    doc: &crate::model::GrokDoc,
    pool_boot: &str,
) -> Result<MoodFit, PipeMiss>
```

Outbound working set for one chain card. Pipe text is resolved. The stored prompt stays.

### take_next

<a id="function-take_next"></a>

```rust
fn take_next(
    cards: &[TurnCard],
    queue: &mut Vec<usize>,
    mood: &MoodYaml,
    doc: &crate::model::GrokDoc,
    pool_boot: &str,
    dropped: &mut bool,
) -> ChainAsk
```

The next card that can be sent. A merge is not sent. A pipe block stops the chain.

### plan_chain

<a id="function-plan_chain"></a>

```rust
fn plan_chain(cards: &[TurnCard], focus: usize) -> ChainAsk
```

Chain for the armed card. Earlier named cards, then that card. One run is in flight.

### plan_chain_mood

<a id="function-plan_chain_mood"></a>

```rust
fn plan_chain_mood(cards: &[TurnCard], focus: usize, mood: &MoodYaml) -> (ChainAsk, bool)
```

The function `plan_chain_mood` plans a pipe chain with one mood file.
`cards` is the card list.
`focus` is the focused index.
`mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md).

The call uses `crate::model::GrokDoc::memory` as the model document.
The pool-boot string is empty.
The pair is the [`ChainAsk`](chrome/enum.ChainAsk.md) and the dropped flag from `plan_chain_doc`.
The dropped flag is true when the fit dropped rows.

### plan_chain_doc

<a id="function-plan_chain_doc"></a>

```rust
fn plan_chain_doc(
    cards: &[TurnCard],
    focus: usize,
    mood: &MoodYaml,
    doc: &crate::model::GrokDoc,
    pool_boot: &str,
) -> (ChainAsk, bool)
```

The function `plan_chain_doc` plans the next send for the focused card.
`cards` is the card list.
`focus` is the focused index.
`mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md) applied while the frame is fitted.
`doc` is the `crate::model::GrokDoc` used to tune the card.
`pool_boot` is the pool boot text passed into that tune.

If any card is running, the return is [`ChainAsk::Busy`](chrome/enum.ChainAsk.md#member-busy) and false.
If `focus` is past the list, the return is [`ChainAsk::Busy`](chrome/enum.ChainAsk.md#member-busy) and false.
If the focused card's mark is [`Mark::Excluded`](chrome/enum.Mark.md#member-excluded), the return is [`ChainAsk::Excluded`](chrome/enum.ChainAsk.md#member-excluded) and false.
If the focused card is a merge, group not 0 and column 0, the return is [`ChainAsk::Quiet`](chrome/enum.ChainAsk.md#member-quiet) and false.

Any other card builds a queue with `pipe_indexes` and takes the next frame with `take_next`.
The pair is that [`ChainAsk`](chrome/enum.ChainAsk.md) and whether the fit dropped rows.

### pipe_after

<a id="function-pipe_after"></a>

```rust
fn pipe_after(
    cards: &[TurnCard],
    live: &mut bool,
    rest: &mut Vec<usize>,
    running_before: Option<[u8; 16]>,
    pieces: &[ScopePiece],
) -> PipeWake
```

After the running chain card ends. The next frame waits for that reply.
A fault clears the rest. A pipe block sets the status word.

### pipe_after_mood

<a id="function-pipe_after_mood"></a>

```rust
fn pipe_after_mood(
    cards: &[TurnCard],
    live: &mut bool,
    rest: &mut Vec<usize>,
    running_before: Option<[u8; 16]>,
    pieces: &[ScopePiece],
    mood: &MoodYaml,
) -> (PipeWake, bool)
```

The function `pipe_after_mood` continues a pipe chain after the running card, using one mood file.
`cards` is the card list.
`live` is whether a chain is in flight, and the function may clear it.
`rest` is the queued card indexes, and the function may replace or clear it.
`running_before` is the cell that was running before these pieces, or `None`.
`pieces` is the new [`ScopePiece`](amwp/enum.ScopePiece.md) values.
`mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md).

The call uses `crate::model::GrokDoc::memory` as the model document.
The pool-boot string is empty.
The pair is the [`PipeWake`](chrome/enum.PipeWake.md) and the dropped flag from `pipe_after_doc`.

### pipe_after_doc

<a id="function-pipe_after_doc"></a>

```rust
fn pipe_after_doc(
    cards: &[TurnCard],
    live: &mut bool,
    rest: &mut Vec<usize>,
    running_before: Option<[u8; 16]>,
    pieces: &[ScopePiece],
    mood: &MoodYaml,
    doc: &crate::model::GrokDoc,
    pool_boot: &str,
) -> (PipeWake, bool)
```

The function `pipe_after_doc` chooses the next chain frame after the running card ends or faults.
`cards` is the card list.
`live` is whether a chain is in flight, and a stop sets it to false.
`rest` is the queued card indexes, and a stop clears it.
`running_before` is the cell that was running, or `None`.
`pieces` is the new [`ScopePiece`](amwp/enum.ScopePiece.md) values.
`mood` is the [`MoodYaml`](chrome/struct.MoodYaml.md) used to fit the next frame.
`doc` is the `crate::model::GrokDoc` used to tune that frame.
`pool_boot` is the pool boot text passed into that tune.

If `live` is false, the return is [`PipeWake::Quiet`](chrome/enum.PipeWake.md#member-quiet) and false.
If `running_before` is `None`, the return is the same.
If no card still has that cell, the return is the same.
A fault piece with a non-empty body for that cell, or for sixteen zero bytes, clears the queue and `live`, then returns quiet.
If no end piece names that cell, the return is quiet and the queue stays.

An end with no such fault takes the next frame with `take_next`.
[`ChainAsk::Send`](chrome/enum.ChainAsk.md#member-send) stores the remaining indexes and returns [`PipeWake::Send`](chrome/enum.PipeWake.md#member-send) with that cell and frame.
The bool on that return is whether the fit dropped rows.
[`ChainAsk::Pipe`](chrome/enum.ChainAsk.md#member-pipe) clears the chain and returns the status `pipe`.
[`ChainAsk::TooLarge`](chrome/enum.ChainAsk.md#member-toolarge) clears the chain and returns the status `too-large`, and the bool is the dropped flag.
[`ChainAsk::Excluded`](chrome/enum.ChainAsk.md#member-excluded) clears the chain and returns the status `Excluded.`.
[`ChainAsk::NoModel`](chrome/enum.ChainAsk.md#member-nomodel) clears the chain and returns the status `no-model`.
[`ChainAsk::Busy`](chrome/enum.ChainAsk.md#member-busy) and [`ChainAsk::Quiet`](chrome/enum.ChainAsk.md#member-quiet) clear the chain and return [`PipeWake::Quiet`](chrome/enum.PipeWake.md#member-quiet).

### fill_capped

<a id="function-fill_capped"></a>

```rust
fn fill_capped<T>(stash: &mut VecDeque<T>, cap: usize, mut next: impl FnMut() -> Option<T>)
```

Pull until `cap`, then one extra when the source still has a piece.
The extra stays in the stash so the next turn keeps order.

### take_front

<a id="function-take_front"></a>

```rust
fn take_front<T>(stash: &mut VecDeque<T>, cap: usize) -> Vec<T>
```

Take at most `cap` from the front. The caller paints canvas pieces before terminal chunks.

### commit_wait

<a id="function-commit_wait"></a>

```rust
fn commit_wait(session_up: bool, more: bool) -> Option<u64>
```

A live session waits 16 ms when the queues are caught up, and 1 ms when a budget remains.

### editor_event

<a id="function-editor_event"></a>

```rust
fn editor_event(clicked: bool, gained: bool, index: usize) -> Option<usize>
```

A prompt moves the pane when it is clicked or when it gains the keyboard.
A prompt that already holds the keyboard is not a new event.

### pane_focus

<a id="function-pane_focus"></a>

```rust
fn pane_focus(clicked: Option<usize>, editor: Option<usize>, stored: usize) -> usize
```

The letter click wins. The next paint keeps that card until a prompt event.

### border_arm

<a id="function-border_arm"></a>

```rust
fn border_arm(inside: bool, editor: bool) -> bool
```

A primary click inside the cell border arms that card.
The prompt editor is the other path. It keeps the caret.

### scroll_press

<a id="function-scroll_press"></a>

```rust
fn scroll_press(on_prompt: bool, on_reply: bool, on_card: bool) -> ScrollPress
```

Prompt and reply take the wheel. A card press and the padding return it.

### scroll_seat_after

<a id="function-scroll_seat_after"></a>

```rust
fn scroll_seat_after(press: ScrollPress) -> Option<TextPane>
```

The seat after that press. Card and padding return the wheel to the canvas.

### bar_press

<a id="function-bar_press"></a>

```rust
fn bar_press(rect: egui::Rect, pos: egui::Pos2, bar: f32, horizontal: bool) -> bool
```

A press in the scroll strip leaves the wheel on the canvas.
Vertical bars use the right `bar` pixels.
A horizontal bar uses the bottom `bar` pixels when `horizontal` is set.

### seat_on_deck

<a id="function-seat_on_deck"></a>

```rust
fn seat_on_deck(
    seat: Option<(usize, TextPane)>,
    deck_len: usize,
) -> Option<(usize, TextPane)>
```

An index past the deck returns the wheel to the canvas.

### pane_owns_wheel

<a id="function-pane_owns_wheel"></a>

```rust
fn pane_owns_wheel(seat: Option<(usize, TextPane)>, card: usize, pane: TextPane) -> bool
```

The wheel stays on this pane when the seat matches it.

### pane_stops_wheel

<a id="function-pane_stops_wheel"></a>

```rust
fn pane_stops_wheel(owns: bool, pointer_inside: bool) -> bool
```

Stop the canvas wheel only while this pane owns it and the pointer is inside.

### caption_band_inside

<a id="function-caption_band_inside"></a>

```rust
fn caption_band_inside(cell: egui::Rect, line_h: f32) -> egui::Rect
```

The lower half of the caption, inside the cell. The line tip stays there.

### pointer_inside

<a id="function-pointer_inside"></a>

```rust
fn pointer_inside(ui: &egui::Ui, rect: egui::Rect) -> bool
```

The pointer is inside the part of `rect` this `ui` paints.
A scrolled card keeps a raw rect above the viewport. That part is clipped,
so a click there stays with the pin field.

### copy_reply

<a id="function-copy_reply"></a>

```rust
fn copy_reply(reply: &str) -> Option<String>
```

Whole reply for the clipboard. A blank reply leaves the clipboard alone.
A lone newline gains a carriage return. A pair that already has one stays.

### copy_notice

<a id="function-copy_notice"></a>

```rust
fn copy_notice(failed: bool) -> Option<&'static str>
```

The status line when the clipboard set fails. A quiet set leaves the line.

### view_body

<a id="function-view_body"></a>

```rust
fn view_body(reply: &str) -> Option<&str>
```

The reply window opens when the text has a character past a space.
Swing trims. This gate matches Copy, so the two marks agree.

### view_title

<a id="function-view_title"></a>

```rust
fn view_title(turn: &str) -> String
```

Swing's window name. The dash is the title dash.

### seat_view_glass

<a id="function-seat_view_glass"></a>

```rust
fn seat_view_glass(ctx: &egui::Context, generation: u64)
```

Full-screen glass. It sits above the canvas and below the reply window.
A click on the Canvas tab or P stops on this glass.

### disarm_view_glass

<a id="function-disarm_view_glass"></a>

```rust
fn disarm_view_glass(ctx: &egui::Context, generation: u64)
```

The glass leaves with the window. The next click reaches the canvas.

### view_bounds_path

<a id="function-view_bounds_path"></a>

```rust
fn view_bounds_path(home: &std::path::Path, username: &str) -> Option<std::path::PathBuf>
```

`~/.aetheris/{username}/view-bounds.txt`. An empty name writes nothing.
This is the Swing file. The door file stays. The window vault is `aetheris.db` beside the Java vault directory.

### vault_runtime

<a id="function-vault_runtime"></a>

```rust
fn vault_runtime() -> Option<tokio::runtime::Runtime>
```

The function `vault_runtime` builds a Tokio runtime for vault work.
The runtime is a current-thread runtime with every driver enabled.

A built runtime is returned as `Some`.
If the runtime cannot be built, the return is `None`.

### home_dir

<a id="function-home_dir"></a>

```rust
fn home_dir() -> Option<std::path::PathBuf>
```

The function `home_dir` reads the home directory from the process environment.
The value of `USERPROFILE` is used when it is set.
If `USERPROFILE` is absent, the value of `HOME` is used.
If both are absent, the return is `None`.

### format_view_bounds

<a id="function-format_view_bounds"></a>

```rust
fn format_view_bounds(rect: ViewRect) -> String
```

The function `format_view_bounds` writes a [`ViewRect`](chrome/struct.ViewRect.md) as four text lines.
`rect` is that rectangle.

The lines are `x=`, `y=`, `width=`, and `height=`, in that order.
Each line ends with a newline, including the last line.

### parse_view_bounds

<a id="function-parse_view_bounds"></a>

```rust
fn parse_view_bounds(text: &str) -> Option<ViewRect>
```

The function `parse_view_bounds` reads a saved reply-window rectangle.
`text` is the file text.
The return is `None` when the text does not name a usable rectangle.

Blank lines are skipped. A line with no `=` is skipped.
A value that is not a signed integer is skipped.
A key other than `x`, `y`, `width`, or `height` is skipped.
Each accepted key stores its value. A later line for the same key replaces that value.
A missing key stays 0.

The function counts each accepted line. Fewer than four accepted lines returns `None`.
A width below 200 returns `None`. A height below 120 returns `None`.
Any other text returns a [`ViewRect`](chrome/struct.ViewRect.md) with the stored values.

### rects_meet

<a id="function-rects_meet"></a>

```rust
fn rects_meet(a: ViewRect, b: ViewRect) -> bool
```

The function `rects_meet` reports whether two rectangles overlap.
`a` is the first [`ViewRect`](chrome/struct.ViewRect.md).
`b` is the second [`ViewRect`](chrome/struct.ViewRect.md).

The rectangles meet when each one starts before the other one ends, on both axes.
Edges that only touch do not meet.
A sum that would pass the signed range stops at the top of that range.

### seated_bounds

<a id="function-seated_bounds"></a>

```rust
fn seated_bounds(text: &str, screens: &[ViewRect]) -> Option<ViewRect>
```

A short side or a rectangle off every screen loads as the default.

### bound_view

<a id="function-bound_view"></a>

```rust
fn bound_view(screen: ViewRect, requested: ViewRect, reply_height: i32) -> ViewRect
```

The reply window stays inside the desktop view.
`reply_height` is the reply. It scrolls, so it does not stretch the window.
A short side grows to the view minimum when the view can hold that.
A request past the view stops on the view edge. A shrink stays.

### view_body_band

<a id="function-view_body_band"></a>

```rust
fn view_body_band(window_height: i32, find_row: i32, control_row: i32) -> i32
```

Height left for the reply after the find bar and the button row.

### view_slots

<a id="function-view_slots"></a>

```rust
fn view_slots(window_height: i32, find_row: i32, control_row: i32) -> ViewSlots
```

The function `view_slots` splits a reply window into find, body, and control heights.
`window_height` is the content height.
`find_row` is the height the find bar wants.
`control_row` is the height the button row wants.
The return is a [`ViewSlots`](chrome/struct.ViewSlots.md).

A negative height is treated as 0.
If the find bar and the button row fit, both keep the heights they asked for.
If they do not fit, the button row keeps what remains of the window, up to the height it asked for.
The find bar then gets whatever is left.
The body height is the window minus those two heights.

### store_view_bounds

<a id="function-store_view_bounds"></a>

```rust
fn store_view_bounds(path: &std::path::Path, rect: ViewRect)
```

The function `store_view_bounds` writes a reply-window rectangle to a file.
`path` is that file.
`rect` is the [`ViewRect`](chrome/struct.ViewRect.md).

If `path` has no parent directory, the function returns without writing.
If that directory cannot be created, the function returns without writing.
The file text is `format_view_bounds` of `rect`.
A failed write is ignored.

### view_rect_from

<a id="function-view_rect_from"></a>

```rust
fn view_rect_from(rect: egui::Rect) -> ViewRect
```

The function `view_rect_from` converts a painted rectangle into a [`ViewRect`](chrome/struct.ViewRect.md).
`rect` is the `egui` rectangle.

The x and y values are the rectangle's minimum corner, rounded to the nearest integer.
The width and the height are rounded to the nearest integer.
A width or height below 0 is stored as 0.

### fold_upper

<a id="function-fold_upper"></a>

```rust
fn fold_upper(ch: char) -> char
```

The function `fold_upper` returns the uppercase form of one character.
`ch` is that character.

An ASCII character uses the ASCII uppercase letter.
Any other character uses Unicode uppercase.
If that uppercase form is not exactly one character, `ch` is returned unchanged.

### fold_lower

<a id="function-fold_lower"></a>

```rust
fn fold_lower(ch: char) -> char
```

The function `fold_lower` returns the lowercase form of one character.
`ch` is that character.

An ASCII character uses the ASCII lowercase letter.
Any other character uses Unicode lowercase.
If that lowercase form is not exactly one character, `ch` is returned unchanged.

### same_letter

<a id="function-same_letter"></a>

```rust
fn same_letter(a: char, b: char) -> bool
```

Swing `regionMatches(true)` on one character.

### view_ranges

<a id="function-view_ranges"></a>

```rust
fn view_ranges(len: usize, spans: &[(usize, usize)], current: i32) -> Vec<(usize, usize, u8)>
```

Body, match, or the current match. Spans stay inside the text.

### view_job

<a id="function-view_job"></a>

```rust
fn view_job(
    body: &str,
    spans: &[(usize, usize)],
    current: i32,
    width: f32,
    font_px: f32,
) -> egui::text::LayoutJob
```

The function `view_job` builds the painted text of one reply window.
`body` is the reply text.
`spans` is the match ranges, as byte starts and ends.
`current` is the index of the match to paint as the current match, or a negative number when there is none.
`width` is the wrap width in pixels.
`font_px` is the monospace size in pixels.

The wrap width is at least 1.
The ranges come from `view_ranges`. A range with no bytes is skipped.
A match range uses the match background. The current match uses the notice color.
Every other range uses the field background.
The glyphs use the reply-window ink.
Each range appends that slice of `body`.

### card_menu

<a id="function-card_menu"></a>

```rust
fn card_menu(on_reply: bool, in_group: bool) -> &'static [&'static str]
```

Menu lines. The reply omits Import. Duplicate group shows on a grouped card.
A card outside a group omits that line. A merge cell and any member keep it.
// @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-140

### import_refused

<a id="function-import_refused"></a>

```rust
fn import_refused(mark: Mark, group: u64, column: u32) -> Option<&'static str>
```

Merge first, then output only. Either word is before the chooser opens.

### has_nul

<a id="function-has_nul"></a>

```rust
fn has_nul(bytes: &[u8]) -> bool
```

A NUL in the first [`NUL_SCAN`](#constant-nul_scan) bytes. A later NUL stays legal.

### import_text

<a id="function-import_text"></a>

```rust
fn import_text(mark: Mark, group: u64, column: u32, bytes: &[u8]) -> Result<String, &'static str>
```

Quiet import text. A leading NUL is refused.
Bad UTF-8 takes the replacement character. The reply is not an argument.

### import_landed

<a id="function-import_landed"></a>

```rust
fn import_landed(index: usize) -> String
```

`imported into turn N`. The number is the plate number, starting at 1.

### import_is_large

<a id="function-import_is_large"></a>

```rust
fn import_is_large(len: u64) -> bool
```

A prompt file past [`IMPORT_CAP`](#constant-import_cap). That size itself is not large.

### console_is_large

<a id="function-console_is_large"></a>

```rust
fn console_is_large(len: u64) -> bool
```

A session file past [`TERMINAL_PASTE_CAP`](#constant-terminal_paste_cap) bytes. That size itself is not large.

### prompt_status

<a id="function-prompt_status"></a>

```rust
fn prompt_status(large: bool, index: usize) -> String
```

Landed status. A large file keeps the polite line ahead of the turn words.

### read_import

<a id="function-read_import"></a>

```rust
fn read_import(path: &std::path::Path) -> Result<Vec<u8>, ImportRead>
```

Metadata first, then the bytes. Either length over the cap is refused.

### chooser_bounds_usable

<a id="function-chooser_bounds_usable"></a>

```rust
fn chooser_bounds_usable(rect: (i32, i32, i32, i32), screens: &[(i32, i32, i32, i32)]) -> bool
```

A saved rectangle meets a screen and the usable minimum.

### exit_pos_usable

<a id="function-exit_pos_usable"></a>

```rust
fn exit_pos_usable(x: i32, y: i32, view: (i32, i32, i32, i32)) -> bool
```

A saved Confirm Exit origin meets the view. Absent keys stay on the default place.

### selection_opens_as_url

<a id="function-selection_opens_as_url"></a>

```rust
fn selection_opens_as_url(text: &str) -> bool
```

The selection is a URL Java can open. A scheme is required. A space is not.

### console_scroll_target

<a id="function-console_scroll_target"></a>

```rust
fn console_scroll_target(current: f32, lines: i32, row_h: f32) -> f32
```

Next scroll offset. Positive `lines` moves toward the tail. The floor is 0.

### console_menu_lines

<a id="function-console_menu_lines"></a>

```rust
fn console_menu_lines(label: &str, rows: usize) -> i32
```

Terminal lines for one menu motion. A page is the visible row count.

### select_all_span

<a id="function-select_all_span"></a>

```rust
fn select_all_span(grid: &Grid) -> ((usize, usize), (usize, usize))
```

Both corners of the buffer. Index 0 is the oldest line.

### console_menu_gates

<a id="function-console_menu_gates"></a>

```rust
fn console_menu_gates(
    grid: &Grid,
    anchor: Option<(usize, usize)>,
    current: Option<(usize, usize)>,
    clipboard: Option<&str>,
) -> (bool, bool, bool, bool)
```

Open as URL stays dark unless the highlight is a URL. Copy stays dark without a highlight.
Paste stays dark when the clipboard has no string. Page, line, and clear stay lit.
Clear on the alternate screen still leaves that grid as it stands.
// @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-143

### console_menu_tip

<a id="function-console_menu_tip"></a>

```rust
fn console_menu_tip(label: &str) -> Option<&'static str>
```

The chord Java paints on the menu item. The label itself stays the menu string.

### console_menu_pick

<a id="function-console_menu_pick"></a>

```rust
fn console_menu_pick(
    ui: &mut egui::Ui,
    url_on: bool,
    copy_on: bool,
    paste_on: bool,
    buffer_on: bool,
) -> Option<&'static str>
```

The nine JediTerm lines, then Import, then Select model. A dark line does not fire.

### apply_console_label

<a id="function-apply_console_label"></a>

```rust
fn apply_console_label(seat: MenuSeat<'_>, label: &str) -> MenuFollow
```

The function `apply_console_label` applies one console menu line to `seat`.
`seat` is the [`MenuSeat`](chrome/struct.MenuSeat.md) of grid, selection, scroll, and jump fields the menu may write. `label` is the menu line that was clicked. The return value is a [`MenuFollow`](chrome/enum.MenuFollow.md) for work the menu itself does not finish.

The `Open as URL` line reads the grid only when `anchor` and `current` are both present. `selection_opens_as_url` decides whether that text is a URL. A URL returns [`MenuFollow::Url`](chrome/enum.MenuFollow.md#member-url) with the text. A missing end returns [`MenuFollow::Quiet`](chrome/enum.MenuFollow.md#member-quiet). Text that is not a URL also returns [`MenuFollow::Quiet`](chrome/enum.MenuFollow.md#member-quiet).

The `Copy` line reads the same two ends. A non-empty selection returns [`MenuFollow::Copy`](chrome/enum.MenuFollow.md#member-copy) with that text. A missing end returns [`MenuFollow::Quiet`](chrome/enum.MenuFollow.md#member-quiet). An empty selection also returns [`MenuFollow::Quiet`](chrome/enum.MenuFollow.md#member-quiet).

The `Paste` line returns [`MenuFollow::Paste`](chrome/enum.MenuFollow.md#member-paste). That line does not write `seat`.

The `Select All` line stores both ends from `select_all_span`. Those ends land in `anchor` and `current`. The result is [`MenuFollow::Quiet`](chrome/enum.MenuFollow.md#member-quiet).

The `Clear Buffer` line calls `clear_scrollback_keep_line` on the grid. A true result clears `anchor`. A true result clears `current`. A true result sets `at_tail`. A true result sets `jump`. The result is [`MenuFollow::Quiet`](chrome/enum.MenuFollow.md#member-quiet) whether or not the grid cleared.

`Page Up`, `Page Down`, `Line Up`, and `Line Down` skip the scroll write when the grid is on the alternate screen. Otherwise `scroll_lines` becomes the value from `console_menu_lines` for that label. `scroll_base` becomes `None` on that same path. The result is [`MenuFollow::Quiet`](chrome/enum.MenuFollow.md#member-quiet).

[`IMPORT_MENU`](#constant-import_menu) returns [`MenuFollow::Import`](chrome/enum.MenuFollow.md#member-import). [`EXPORT_SESSION_LABEL`](#constant-export_session_label) returns [`MenuFollow::Export`](chrome/enum.MenuFollow.md#member-export). [`TRANSFER_LABEL`](#constant-transfer_label) returns [`MenuFollow::Transfer`](chrome/enum.MenuFollow.md#member-transfer). [`SELECT_MODEL_LABEL`](#constant-select_model_label) returns [`MenuFollow::Model`](chrome/enum.MenuFollow.md#member-model). Any other line returns [`MenuFollow::Quiet`](chrome/enum.MenuFollow.md#member-quiet).

### open_console_url

<a id="function-open_console_url"></a>

```rust
fn open_console_url(text: &str)
```

Java `Desktop.browse`. A string that is not a URL stays closed.

### shell_file_script

<a id="function-shell_file_script"></a>

```rust
fn shell_file_script() -> String
```

Shell file dialog. Java's remembered chooser sets no title and no filter.
The cell-import script stays. This copy asks for Open and every file.

### step_font

<a id="function-step_font"></a>

```rust
fn step_font(size: i32, delta: i32) -> i32
```

The spinner range. A step that would leave it stays on the edge.

### frame_usable

<a id="function-frame_usable"></a>

```rust
fn frame_usable(rect: (i32, i32, i32, i32), screens: &[(i32, i32, i32, i32)]) -> bool
```

A restored rectangle meets a screen and the usable minimum.

### note_frame_seat

<a id="function-note_frame_seat"></a>

```rust
fn note_frame_seat(
    seat: FrameSeat,
    maximized: bool,
    minimized: bool,
    origin: Option<(i32, i32)>,
    size: (i32, i32),
) -> FrameSeat
```

A move or a resize. Maximized and minimized keep the last restored rectangle.

### open_frame_seat

<a id="function-open_frame_seat"></a>

```rust
fn open_frame_seat(
    rect: Option<(i32, i32, i32, i32)>,
    maximized: bool,
    screens: &[(i32, i32, i32, i32)],
) -> OpenFrame
```

A usable saved rectangle returns that size and place.
An unusable rectangle returns the default size and no place.
The maximized flag stays either way.

### looks_binary

<a id="function-looks_binary"></a>

```rust
fn looks_binary(raw: &[u8]) -> bool
```

NUL in the sample, or control bytes other than tab and the newline family
on more than a tenth of that sample. An empty file is not binary.

### console_text

<a id="function-console_text"></a>

```rust
fn console_text(raw: &[u8]) -> Result<String, &'static str>
```

Send file read. The cap is Java `String.length`. Console import uses [`console_import_text`](#function-console_import_text).

### console_import_text

<a id="function-console_import_text"></a>

```rust
fn console_import_text(raw: &[u8]) -> Result<String, &'static str>
```

Console import. The same refusals as [`console_text`](#function-console_text), with no character cap.

### paste_decision

<a id="function-paste_decision"></a>

```rust
fn paste_decision(text: &str) -> PasteDecision
```

The function `paste_decision` classifies editor text before a terminal paste.
`text` is the editor body. The result is a [`PasteDecision`](chrome/enum.PasteDecision.md).

`terminal_normalize` turns CR LF and a bare CR into LF before the tests. An empty body returns [`PasteDecision::Quiet`](chrome/enum.PasteDecision.md#member-quiet). A body whose `terminal_units` count is above [`TERMINAL_PASTE_CAP`](#constant-terminal_paste_cap) returns [`PasteDecision::Over`](chrome/enum.PasteDecision.md#member-over). [`TERMINAL_PASTE_CAP`](#constant-terminal_paste_cap) is 65536 UTF-16 units. Any other body returns [`PasteDecision::Ready`](chrome/enum.PasteDecision.md#member-ready) with the normalized text.

### paste_wrapped

<a id="function-paste_wrapped"></a>

```rust
fn paste_wrapped(text: &str) -> Vec<u8>
```

Java `sendPaste`. The markers always wrap. LF stays. No trailing Enter.

### focused_link

<a id="function-focused_link"></a>

```rust
fn focused_link(session_up: bool, status: &str, link_up: bool) -> PaneLink
```

The function `focused_link` chooses the [`PaneLink`](chrome/enum.PaneLink.md) for the pane that is in front.
`session_up` is true when that pane has a session. `status` is the connection text. `link_up` is true after a connected session until that session drops.

A false `session_up` returns [`PaneLink::Down`](chrome/enum.PaneLink.md#member-down). A `status` that starts with `Disconnected` also returns [`PaneLink::Down`](chrome/enum.PaneLink.md#member-down). The text `Connecting` returns [`PaneLink::Opening`](chrome/enum.PaneLink.md#member-opening). [`PaneLink::Up`](chrome/enum.PaneLink.md#member-up) is returned when `link_up` is true, when `status` is `Connected`, or when `status` starts with `Connected`.

A later arm also returns [`PaneLink::Up`](chrome/enum.PaneLink.md#member-up) when `link_up` is true. That arm is reached only after the earlier `link_up` test has already returned. Every remaining status returns [`PaneLink::Opening`](chrome/enum.PaneLink.md#member-opening).

### link_from_status

<a id="function-link_from_status"></a>

```rust
fn link_from_status(text: &str, prior: PaneLink) -> PaneLink
```

The function `link_from_status` maps one status line onto a [`PaneLink`](chrome/enum.PaneLink.md).
`text` is that line. `prior` is the link kept when the line is not a connection word.

The line `Connecting` returns [`PaneLink::Opening`](chrome/enum.PaneLink.md#member-opening). The line `Connected` returns [`PaneLink::Up`](chrome/enum.PaneLink.md#member-up). A line that starts with `Connected` also returns [`PaneLink::Up`](chrome/enum.PaneLink.md#member-up). A line that starts with `Disconnected` returns [`PaneLink::Down`](chrome/enum.PaneLink.md#member-down). Every other line returns `prior`.

### shell_enables

<a id="function-shell_enables"></a>

```rust
fn shell_enables(phase: PaneLink, panes: usize) -> ShellEnables
```

The function `shell_enables` fills the open-bar flags for one pane.
`phase` is that pane's [`PaneLink`](chrome/enum.PaneLink.md). `panes` is the pane count. The result is a [`ShellEnables`](chrome/struct.ShellEnables.md).

`reconnect` is true only when `phase` is [`PaneLink::Up`](chrome/enum.PaneLink.md#member-up). `send_file` uses that same test. `clear` is always true. `new_term` is always true. `close_pane` is true only when `panes` is greater than 1.

### count_leaves

<a id="function-count_leaves"></a>

```rust
fn count_leaves(node: &PaneNode) -> usize
```

The function `count_leaves` counts pane ids in a [`PaneNode`](chrome/enum.PaneNode.md) tree.
`node` is the root to count. A [`PaneNode::Leaf`](chrome/enum.PaneNode.md#member-leaf) counts as one. A [`PaneNode::Split`](chrome/enum.PaneNode.md#member-split) returns the sum of its two children.

### first_leaf

<a id="function-first_leaf"></a>

```rust
fn first_leaf(node: &PaneNode) -> u64
```

The function `first_leaf` returns the pane id of the first leaf.
`node` is the tree to walk. A [`PaneNode::Leaf`](chrome/enum.PaneNode.md#member-leaf) returns its id. A [`PaneNode::Split`](chrome/enum.PaneNode.md#member-split) walks `left` and does not read `right`.

### graft_split

<a id="function-graft_split"></a>

```rust
fn graft_split(node: PaneNode, old_id: u64, new_id: u64, fresh: SplitWay) -> PaneNode
```

The new leaf is the right child. The ratio is half.

### detach_leaf

<a id="function-detach_leaf"></a>

```rust
fn detach_leaf(node: PaneNode, id: u64) -> Detach
```

Remove one leaf. The sibling takes that split's place. The last leaf stays.

### set_split_ratio

<a id="function-set_split_ratio"></a>

```rust
fn set_split_ratio(node: &mut PaneNode, path: &[PaneSide], ratio: f32)
```

The function `set_split_ratio` writes a split ratio at one path.
`node` is the tree to change. `path` is the [`PaneSide`](chrome/enum.PaneSide.md) steps from this node to the split. `ratio` is the share to store.

The share is clamped to the range 0.15 through 0.85 before the walk. An empty path writes that share when `node` is a [`PaneNode::Split`](chrome/enum.PaneNode.md#member-split). An empty path on a leaf writes nothing. [`PaneSide::Left`](chrome/enum.PaneSide.md#member-left) continues into the first child when this node is a split. [`PaneSide::Right`](chrome/enum.PaneSide.md#member-right) continues into the second child when this node is a split. A step whose node is a leaf writes nothing.

### layout_panes

<a id="function-layout_panes"></a>

```rust
fn layout_panes(node: &PaneNode, rect: egui::Rect, gap: f32) -> SplitLayout
```

The function `layout_panes` builds a [`SplitLayout`](chrome/struct.SplitLayout.md) for one pane tree.
`node` is the tree. `rect` is the area the tree fills. `gap` is the space between two children. The walk starts with an empty path.

### walk_panes

<a id="function-walk_panes"></a>

```rust
fn walk_panes(
    node: &PaneNode,
    rect: egui::Rect,
    gap: f32,
    path: &[PaneSide],
    leaves: &mut Vec<(u64, egui::Rect)>,
    bars: &mut Vec<SplitBar>,
)
```

The function `walk_panes` lays one [`PaneNode`](chrome/enum.PaneNode.md) into leaf rectangles and divider bars.
`node` is the tree. `rect` is the area that node fills. `gap` is the space kept between the two children. `path` is the [`PaneSide`](chrome/enum.PaneSide.md) steps from the root to this node. `leaves` receives each pane id with its rectangle. `bars` receives one [`SplitBar`](chrome/struct.SplitBar.md) for each split.

A [`PaneNode::Leaf`](chrome/enum.PaneNode.md#member-leaf) pushes that id and `rect` onto `leaves`. A [`PaneNode::Split`](chrome/enum.PaneNode.md#member-split) clamps `ratio` to the range 0.15 through 0.85. The gap is then limited to the shorter side of `rect`. A negative shorter side is treated as zero.

A [`SplitWay::Horizontal`](chrome/enum.SplitWay.md#member-horizontal) split gives the first child the left share of the width after the gap. The bar is the gap rectangle between the children. The second child takes the remaining width. The walk continues on the left child with [`PaneSide::Left`](chrome/enum.PaneSide.md#member-left) appended. It then continues on the right child with [`PaneSide::Right`](chrome/enum.PaneSide.md#member-right) appended.

A [`SplitWay::Vertical`](chrome/enum.SplitWay.md#member-vertical) split gives the first child the top share of the height after the gap. The bar is the gap rectangle under that child. The second child takes the remaining height. The walk continues on the top child with [`PaneSide::Left`](chrome/enum.PaneSide.md#member-left) appended. It then continues on the bottom child with [`PaneSide::Right`](chrome/enum.PaneSide.md#member-right) appended.

### half_span

<a id="function-half_span"></a>

```rust
fn half_span(span: f32, gap: f32) -> f32
```

Half of the span after the gap. The two sides match.

### monitor_rects

<a id="function-monitor_rects"></a>

```rust
fn monitor_rects(event_loop: &ActiveEventLoop) -> Vec<(i32, i32, i32, i32)>
```

Screens the restored frame can land on. Physical pixels.

### io_text

<a id="function-io_text"></a>

```rust
fn io_text(err: &std::io::Error) -> String
```

The function `io_text` turns an I/O error into text a caller can show.
`err` is that error. An empty display string becomes `read failed`. Any other message is returned unchanged.

### read_growing

<a id="function-read_growing"></a>

```rust
fn read_growing(path: &std::path::Path) -> Result<Vec<u8>, String>
```

Chunked read. The buffer grows from the bytes that arrive.
It does not reserve the file length.

### read_prompt_bytes

<a id="function-read_prompt_bytes"></a>

```rust
fn read_prompt_bytes(path: &std::path::Path) -> Result<PromptBytes, String>
```

Prompt bytes. A NUL in the first [`NUL_SCAN`](#constant-nul_scan) bytes is binary and the rest stays on disk.

### read_console_import

<a id="function-read_console_import"></a>

```rust
fn read_console_import(path: &std::path::Path) -> Result<String, ConsoleReadFail>
```

Session import. A refusal is binary or empty. A failed read is the thrown message.

### read_console_file

<a id="function-read_console_file"></a>

```rust
fn read_console_file(path: &std::path::Path) -> Result<String, String>
```

Read a console file the way Java `readConsoleText` does.

### rects_intersect

<a id="function-rects_intersect"></a>

```rust
fn rects_intersect(a: (i32, i32, i32, i32), b: (i32, i32, i32, i32)) -> bool
```

The function `rects_intersect` reports whether two rectangles overlap.
`a` and `b` are each `(x, y, width, height)` in the same pixel space. A width that is not positive returns false. A height that is not positive returns false.

The far edges are added in `i64` so the sum does not wrap. The rectangles overlap when each origin is strictly before the other rectangle's far edge on both axes.

### chooser_directory_live

<a id="function-chooser_directory_live"></a>

```rust
fn chooser_directory_live(raw: &str) -> Option<&str>
```

A missing or empty folder, or a path that is not a directory, stays off the dialog.

### chooser_parent_dir

<a id="function-chooser_parent_dir"></a>

```rust
fn chooser_parent_dir(file: &std::path::Path) -> Option<std::path::PathBuf>
```

The parent of an approved file, when that parent is a directory.

### parse_chooser_report

<a id="function-parse_chooser_report"></a>

```rust
fn parse_chooser_report(text: &str) -> ChooserReport
```

`BOUNDS x y w h` and `FILE path`. A quiet line is a cancel. A non-positive size is dropped.

### keep_chooser

<a id="function-keep_chooser"></a>

```rust
fn keep_chooser(door: &mut Door, report: &ChooserReport) -> bool
```

Bounds on every close. The folder only when a file is approved.
A later refused read still finds the folder already written.

### pick_import_file

<a id="function-pick_import_file"></a>

```rust
fn pick_import_file(
    directory: String,
    bounds: Option<(i32, i32, i32, i32)>,
) -> Result<ChooserReport, String>
```

The chooser. Cancel leaves the file empty. A miss names the failure.

### pick_shell_file

<a id="function-pick_shell_file"></a>

```rust
fn pick_shell_file(
    directory: String,
    bounds: Option<(i32, i32, i32, i32)>,
) -> Result<ChooserReport, String>
```

Send file and console import. Same runner as the cell door.
The script asks for Open and every file. The cell script stays.

### begin_import

<a id="function-begin_import"></a>

```rust
fn begin_import(cards: &mut [TurnCard], index: usize, door: &mut Door) -> ImportOutcome
```

Merge and output refuse before the chooser. Cancel returns no status.
A landed file replaces the prompt. The reply stays. The folder is kept
before that read, including a later refusal.

### load_import

<a id="function-load_import"></a>

```rust
fn load_import(cards: &mut [TurnCard], index: usize, path: &std::path::Path) -> (String, bool)
```

The function `load_import` reads a prompt file into one card.
`cards` is the deck. `index` is the card to fill. `path` is the file. The first return is status text. The second return is true only when the byte read fails.

Metadata that cannot be read leaves the large-file flag false. A length above [`IMPORT_CAP`](#constant-import_cap) sets that flag. [`PromptBytes::Binary`](chrome/enum.PromptBytes.md#member-binary) returns [`IMPORT_BINARY`](#constant-import_binary) and false. A read error returns [`IMPORT_FAILED`](#constant-import_failed) plus the message, and true.

A missing card at `index` returns an empty string and false. A successful `import_text` writes the prompt and returns `prompt_status`. A refusal from `import_text` returns that word and false.

### click_inside

<a id="function-click_inside"></a>

```rust
fn click_inside(ui: &egui::Ui, rect: egui::Rect) -> bool
```

The pointer released a primary click inside the painted part of this card.

### accept_fault_deck

<a id="function-accept_fault_deck"></a>

```rust
fn accept_fault_deck(cards: &mut [TurnCard], focus: usize, cell: &[u8; 16], body: &[u8])
```

The function `accept_fault_deck` appends a fault body onto one card.
`cards` is the deck. `focus` is the index used when no card is running and the cell is sixteen zero bytes. `cell` is the 16-byte id. `body` is the fault bytes.

A `cell` that is not all zeros finds the card with that id and calls `accept_fault`. A missing card on that path returns without a write. A zero cell uses the first card whose `running` flag is set. When no card is running, the zero cell uses `focus`. `accept_fault` then runs on that card when the index exists.

### reply_opens_turn

<a id="function-reply_opens_turn"></a>

```rust
fn reply_opens_turn(cards: &[TurnCard], piece: &ScopePiece) -> bool
```

A reply or an end on the running last card opens the next turn.
A fault does not. A card that is not last does not. A quiet card does not.

### land_open_turn

<a id="function-land_open_turn"></a>

```rust
fn land_open_turn(
    cards: &mut Vec<TurnCard>,
    seq: &mut u64,
    focus: usize,
    facts: &mut Option<CanvasFacts>,
    text: &mut String,
    log: &mut ThinkLog,
    piece: &ScopePiece,
) -> Option<usize>
```

Seat the piece. When it opens the next turn, return that card's index.

### route_scope

<a id="function-route_scope"></a>

```rust
fn route_scope(
    facts: &mut Option<CanvasFacts>,
    text: &mut String,
    cards: &mut [TurnCard],
    focus: usize,
    log: &mut ThinkLog,
    piece: &ScopePiece,
)
```

The function `route_scope` applies one [`ScopePiece`](amwp/enum.ScopePiece.md) to the canvas deck.
`facts` receives an init body. `text` receives later scope text. `cards` is the deck. `focus` is the card `accept_fault_deck` uses for a zero cell. `log` is the [`ThinkLog`](chrome/struct.ThinkLog.md). `piece` is the body to apply.

[`ScopePiece::Init`](amwp/enum.ScopePiece.md#member-init) always calls `accept_scope`. [`ScopePiece::Later`](amwp/enum.ScopePiece.md#member-later) returns without that call when the body is a model note, a session export, or a session replace. Any other later body calls `accept_scope`.

[`ScopePiece::Reply`](amwp/enum.ScopePiece.md#member-reply) finds the card with the same cell and calls `accept_reply`. A cell that is not sixteen zero bytes then calls `close_thought`. A missing card still closes the thought when the cell is not nil.

[`ScopePiece::End`](amwp/enum.ScopePiece.md#member-end) finds the card with the same cell and calls `accept_end`. A cell that is not sixteen zero bytes then calls `close_thought`.

[`ScopePiece::Fault`](amwp/enum.ScopePiece.md#member-fault) calls `accept_fault_deck`. A cell that is not sixteen zero bytes then calls `close_thought`.

[`ScopePiece::Delta`](amwp/enum.ScopePiece.md#member-delta) returns when the cell is sixteen zero bytes. It returns when `kind` is not [`THINK_KIND`](#constant-think_kind). [`THINK_KIND`](#constant-think_kind) is the byte `t`. It returns when `body` is empty. It returns when no card has that cell. It returns when `think_paints` rejects the card's provider, `grok_on`, and `pool_on`. Otherwise `accept_thought` appends the lossy UTF-8 body under `think_model_word` of the card target.

### route_console_thought

<a id="function-route_console_thought"></a>

```rust
fn route_console_thought(log: &mut ThinkLog, open: bool, tag: &str, piece: &ScopePiece) -> bool
```

A nil-cell kind t belongs on the VT100 strip. The notebook log stays.
Pool opens on the pool switch and the tag is pool. Ollama opens when the
think string is set and the tag is the short model. Grok opens when
grok_think is true and the tag is the Grok model. An empty tag reads
ollama. The end of that turn closes the block even when the gate has shut.
A different tag on this nil cell starts a header.
// @aa-sdd PK-1166 docs/specs/java/org/aetheris/swing/swing.spec.md FINESSE:f-210

### clear_canvas

<a id="function-clear_canvas"></a>

```rust
fn clear_canvas(facts: &mut Option<CanvasFacts>, text: &mut String)
```

The function `clear_canvas` drops the seated canvas facts and the gathered scope text.
`facts` becomes `None`. `text` is cleared.

### canvas_body

<a id="function-canvas_body"></a>

```rust
fn canvas_body(facts: Option<&CanvasFacts>, text: &str) -> String
```

Seated record of the two words plus later scope text.
The paint splits them. The two words sit above the inspector.
Later scope text stays on the canvas.

### fact_line

<a id="function-fact_line"></a>

```rust
fn fact_line(facts: Option<&CanvasFacts>) -> String
```

One line above the inspector. Empty until both words are seated.

### shown_target

<a id="function-shown_target"></a>

```rust
fn shown_target(target: &str) -> String
```

Empty stored target shows as `ollama`. Java `AetherisStage2.shownTarget`.

### ssh_heat_budget

<a id="function-ssh_heat_budget"></a>

```rust
fn ssh_heat_budget(managed: i32, engine: &str) -> i32
```

The SSH heat budget. A managed count above zero is that count.
A blank engine counts as ollama. An engine that contains ollama uses 131072.
Any other engine uses zero. A canvas window is not this number.
// @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230

### air_gap_word

<a id="function-air_gap_word"></a>

```rust
fn air_gap_word(air: &str) -> &str
```

The function `air_gap_word` returns the air-gap word shown on the status line.
`air` is the stored word. Trimmed empty text returns `unset`. Any other text is returned with surrounding whitespace removed.

### status_extra

<a id="function-status_extra"></a>

```rust
fn status_extra(status: &str) -> &str
```

Connection words stay on the link chip. A canvas word is the status-line tail.

### aetheris_status_line

<a id="function-aetheris_status_line"></a>

```rust
fn aetheris_status_line(amwp: bool, air: &str, mismatch: bool, engine: &str, extra: &str) -> String
```

Java `statusLine`. The vault word is disk. The Rust vault is the file.

### shell_mode_word

<a id="function-shell_mode_word"></a>

```rust
fn shell_mode_word(token: &str) -> &'static str
```

VT100 or PTY. A missing or unknown token is VT100. AMWP stays off this strip.

### shell_term_type

<a id="function-shell_term_type"></a>

```rust
fn shell_term_type(token: &str) -> &'static str
```

TERM for that mode. Java `SshSessionMode.getTermType`.

### shell_status_line

<a id="function-shell_status_line"></a>

```rust
fn shell_status_line(mode: &str, air: &str, mismatch: bool, engine: &str, extra: &str) -> String
```

The VT100 strip. The wire word is the console mode. The Aetheris strip keeps AMWP.

### status_facts_line

<a id="function-status_facts_line"></a>

```rust
fn status_facts_line(wire: &str, air: &str, mismatch: bool, engine: &str, extra: &str) -> String
```

The function `status_facts_line` builds one status line for the notebook or the terminal.
`wire` is the mode word placed after `vault: disk`. `air` is the air-gap word. `mismatch` is true when the line should say the air gap does not match. `engine` is the target passed through `shown_target`. `extra` is an optional tail.

The line is `vault: disk`, the wire word, `air-gap:`, the word from `air_gap_word`, and `engine:`. A true `mismatch` inserts ` mismatch` after the air-gap word. A non-empty `extra` is appended after two spaces. An empty `extra` adds nothing.

### status_user_name

<a id="function-status_user_name"></a>

```rust
fn status_user_name(amwp: bool, user: &str) -> String
```

East name. Empty unless the session is a connected AMWP user.

### shell_status_west

<a id="function-shell_status_west"></a>

```rust
fn shell_status_west(status: &str, host: &str, port: &str) -> String
```

West word on the SSH status strip. The chip keeps the short seat word.
Connected shows host and port. A disconnect reason uses the Java em dash.

### status_bar_height

<a id="function-status_bar_height"></a>

```rust
fn status_bar_height(ui: &egui::Ui) -> f32
```

The function `status_bar_height` returns the height of the status strip.
`ui` supplies the body font. The height is that font's row height plus [`STATUS_PAD_Y`](#constant-status_pad_y) above the text and the same pad below it. [`STATUS_PAD_Y`](#constant-status_pad_y) is 3.

### paint_status_bar

<a id="function-paint_status_bar"></a>

```rust
fn paint_status_bar(ui: &mut egui::Ui, rect: egui::Rect, west: &str, east: &str)
```

West is the notebook line. East is the user name. The fill is the Java status color.

### icon_ground

<a id="function-icon_ground"></a>

```rust
fn icon_ground(r: u8, g: u8, b: u8) -> bool
```

A light, dull corner is the rounded ground. The eye stays.

### fill_icon_ground

<a id="function-fill_icon_ground"></a>

```rust
fn fill_icon_ground(rgba: &mut [u8], width: u32, height: u32)
```

The rounded ground becomes black. The eye stays. The plate stays square.

### window_icon_rgba

<a id="function-window_icon_rgba"></a>

```rust
fn window_icon_rgba() -> Option<(u32, u32, Vec<u8>)>
```

Eye of Horus, then the casserole mark. RGBA at [`WINDOW_ICON_PX`](#constant-window_icon_px).

### about_app_rgba

<a id="function-about_app_rgba"></a>

```rust
fn about_app_rgba() -> Option<(u32, u32, Vec<u8>)>
```

Eye of Horus at [`ABOUT_APP_PX`](#constant-about_app_px). The ground is black. The eye stays.

### seat_about_mark

<a id="function-seat_about_mark"></a>

```rust
fn seat_about_mark(
    ctx: &egui::Context,
    name: &str,
    plate: Option<(u32, u32, Vec<u8>)>,
) -> Option<egui::TextureHandle>
```

One About mark as a texture. A failed decode leaves the seat empty.

### decode_icon_rgba

<a id="function-decode_icon_rgba"></a>

```rust
fn decode_icon_rgba(bytes: &[u8], edge: u32) -> Option<(u32, u32, Vec<u8>)>
```

The function `decode_icon_rgba` decodes an image and scales it to a square.
`bytes` are the encoded image. `edge` is the width and the height in pixels. The result is width, height, and packed RGBA bytes. A decode that fails returns `None`.

The scale uses the triangle filter. A decoded width of zero returns `None`. A decoded height of zero returns `None`.

### window_icon

<a id="function-window_icon"></a>

```rust
fn window_icon() -> Option<winit::window::Icon>
```

The function `window_icon` builds the window icon for the client window.
The pixels come from `window_icon_rgba`. A missing image returns `None`. A failure inside `Icon::from_rgba` also returns `None`.

### divider_center_x

<a id="function-divider_center_x"></a>

```rust
fn divider_center_x(origin_x: f32, canvas_w: f32, gap: f32) -> f32
```

Center of the divider. `origin_x` is the row's left. `canvas_w` is the canvas column.

### pin_top_seat

<a id="function-pin_top_seat"></a>

```rust
fn pin_top_seat(host: egui::Rect, bar: egui::Rect, size: egui::Vec2) -> egui::Rect
```

P on the top of the divider. Center x is the bar. Top is the bar's top.

### pin_show_seat

<a id="function-pin_show_seat"></a>

```rust
fn pin_show_seat(band: egui::Rect, bar_center_x: f32, size: egui::Vec2) -> egui::Rect
```

P on the divider. The rect stays inside the band and still covers the bar.

### pin_show_covers_bar

<a id="function-pin_show_covers_bar"></a>

```rust
fn pin_show_covers_bar(seat: egui::Rect, bar_center_x: f32) -> bool
```

The seat still overlaps the bar, including when the bar is against an edge.

### fact_line_rect

<a id="function-fact_line_rect"></a>

```rust
fn fact_line_rect(
    band: egui::Rect,
    canvas_w: f32,
    gap: f32,
    inspector_w: f32,
    avoid: Option<egui::Rect>,
) -> Option<egui::Rect>
```

The inspector's share of the tab row. `avoid` is P, so the words start clear of it.

### settle_plate

<a id="function-settle_plate"></a>

```rust
fn settle_plate(plate: Plate, mux: bool, pick: Option<Plate>) -> PlatePick
```

The function `settle_plate` chooses the plate under the tab strip.
`plate` is the plate now showing. `mux` is true when the multiplexed session has answered. `pick` is a clicked [`Plate`](chrome/enum.Plate.md), or `None` when nothing was clicked. The result is a [`PlatePick`](chrome/struct.PlatePick.md).

A false `mux` forces [`Plate::Ssh`](chrome/enum.Plate.md#member-ssh). On that path `arm_console` is true only when `plate` was [`Plate::Aetheris`](chrome/enum.Plate.md#member-aetheris).

A true `mux` keeps `pick` when it is present. A missing `pick` keeps `plate`. `arm_console` is then true only when the current plate is [`Plate::Aetheris`](chrome/enum.Plate.md#member-aetheris) and the next plate is [`Plate::Ssh`](chrome/enum.Plate.md#member-ssh).

### open_product_tab

<a id="function-open_product_tab"></a>

```rust
fn open_product_tab(stored_aetheris: bool) -> TabStep
```

Open sits on SSH. A stored `aetheris` word arms the wait for Connect.

### step_product_tab

<a id="function-step_product_tab"></a>

```rust
fn step_product_tab(plate: Plate, mux: bool, restore: bool, pick: Option<Plate>) -> TabStep
```

One product-tab step. A click wins over an armed restore.
A restore with no click takes Aetheris, lands, and stores nothing.
A mux drop while Aetheris is showing stores aetheris and arms the wait.

### close_product_tab

<a id="function-close_product_tab"></a>

```rust
fn close_product_tab(plate: Plate, restore: bool) -> Option<TabWord>
```

Close stores the visible plate. An armed restore leaves the file alone.

### run

<a id="function-run"></a>

```rust
pub fn run()
```

Open the window. Tests do not call this.

### scope_home

<a id="function-scope_home"></a>

```rust
fn scope_home(showing_user: &str, pane_user: &str, parked: bool) -> ScopeHome
```

A piece for the user on the glass stays on the glass.
A piece for someone else stays on that seat when the seat exists.
An empty pane user waits on the pane.

### focused_canvas_home

<a id="function-focused_canvas_home"></a>

```rust
fn focused_canvas_home(glass: &str, pane_user: &str, parked: bool) -> ScopeHome
```

Focused canvas pieces stay on the glass when that pane's user is the glass,
or when either name is empty. A different user with a seat stays on that seat.
A different user with no seat waits on the pane.
// @aa-sdd PK-1074 docs/specs/common/formal-plan.spec.md FINESSE:f-218

### glass_run_pane

<a id="function-glass_run_pane"></a>

```rust
fn glass_run_pane(
    glass: &str,
    focused_user: &str,
    focused_id: u64,
    parked: &[(u64, &str)],
) -> Option<u64>
```

The pane that carries this glass user's execute.
An empty glass, or a focused user who is that glass, uses the focused pane.
Another user's parked pane is that pane. A missing pane sends nothing.
// @aa-sdd PK-1074 docs/specs/common/formal-plan.spec.md FINESSE:f-218

### think_salt

<a id="function-think_salt"></a>

```rust
fn think_salt(user: &str) -> u64
```

Scroll id for one canvas user's thinking pane.
// @aa-sdd PK-1074 docs/specs/common/formal-plan.spec.md FINESSE:f-218

### feed_parts

<a id="function-feed_parts"></a>

```rust
fn feed_parts(
    deck: DeckRefs<'_>,
    seq: &mut u64,
    pieces: &[ScopePiece],
    mood: &MoodYaml,
    grok: &mut crate::model::GrokDoc,
    pool_boot: &str,
    mux: bool,
) -> FeedOut
```

Seat scope on one deck. The caller wires `frames` on the session that delivered the pieces.
A quiet mux fails the merge or the pipe and does not start the next card.

### seat_bar_font

<a id="function-seat_bar_font"></a>

```rust
fn seat_bar_font(ui: &mut egui::Ui, points: i32)
```

The master control bar uses the same point size as the panes.

### control_mark

<a id="function-control_mark"></a>

```rust
fn control_mark(open: bool) -> &'static str
```

`v` while the connection bar is showing. `^` while it is folded.

### control_tip

<a id="function-control_tip"></a>

```rust
fn control_tip(open: bool) -> &'static str
```

The function `control_tip` returns the hover text for the fold mark. The parameter `open` is true while the connection bar is showing. An open bar returns `CONTROL_FOLD_TIP`. A folded bar returns `CONTROL_SHOW_TIP`.

### send_file_button

<a id="function-send_file_button"></a>

```rust
fn send_file_button(ui: &mut egui::Ui, enabled: bool) -> egui::Response
```

Send file keeps the full word. Three ems past the text clear the round edge.

| | |
| --- | --- |
| **Specification** | F-157 |

### seat_plate_tabs

<a id="function-seat_plate_tabs"></a>

```rust
fn seat_plate_tabs(ui: &mut egui::Ui, plate: Plate, mux: bool) -> Option<Plate>
```

SSH stays on. Aetheris waits for the mux.

### paint_link_status

<a id="function-paint_link_status"></a>

```rust
fn paint_link_status(ui: &mut egui::Ui, status: &str)
```

Connected wears the green chip. Every other line keeps the plate ink.

### paint_link_chip

<a id="function-paint_link_chip"></a>

```rust
fn paint_link_chip(ui: &mut egui::Ui, text: &str)
```

Green face, white letters, light edge on the top and left, dark edge on the bottom and right.

### step_control_bar

<a id="function-step_control_bar"></a>

```rust
fn step_control_bar(open: bool, link_up: bool, beat: ControlBeat) -> bool
```

The bar starts open. Connect folds it. Disconnect opens it.
A resize while the link is up folds it. The mark toggles either way.
A resize while the link is down leaves the mark.

### step_control_link

<a id="function-step_control_link"></a>

```rust
fn step_control_link(link_up: bool, beat: ControlBeat) -> bool
```

Connected seats the link. Disconnected clears it. A resize and the mark leave it.

### control_after_status

<a id="function-control_after_status"></a>

```rust
fn control_after_status(open: bool, link_up: bool, text: &str) -> (bool, bool)
```

A shell status. Connecting, and every canvas line, leave the bar and the link.

### note_control_span

<a id="function-note_control_span"></a>

```rust
fn note_control_span(
    prior: Option<egui::Vec2>,
    next: egui::Vec2,
    open: bool,
    link_up: bool,
) -> (Option<egui::Vec2>, bool)
```

The first frame size is the open frame. A later change is a resize.

### connect_allowed

<a id="function-connect_allowed"></a>

```rust
pub(crate) fn connect_allowed(
    host: &str,
    port: &str,
    user: &str,
    password: &str,
    session_up: bool,
) -> bool
```

Connect stays off while a field is empty, the port is bad, or a session is up.

### parse_port

<a id="function-parse_port"></a>

```rust
pub(crate) fn parse_port(text: &str) -> Option<u16>
```

The function `parse_port` reads a TCP port from text. The parameter `text` is the field contents. Surrounding whitespace is removed. A value that is not a whole number returns `None`.

The number must sit from 1 through 65535. A value in that range is returned as `u16`. Zero returns `None`. A value above 65535 returns `None`.

### clamp_logical

<a id="function-clamp_logical"></a>

```rust
pub(crate) fn clamp_logical(width: f32, height: f32) -> (f32, f32)
```

A resize below the minimum becomes the minimum. The axes are logical pixels.

### open_zoom

<a id="function-open_zoom"></a>

```rust
fn open_zoom(steps: u32) -> f32
```

The zoom after `steps` of Ctrl-+. Each step adds one tenth, then rounds
to one decimal, the same count egui uses.

### view_on_tail

<a id="function-view_on_tail"></a>

```rust
pub(crate) fn view_on_tail(content: f32, view: f32, offset: f32) -> bool
```

The view is on the tail when the offset sits on the bottom of the content.

### shift_key

<a id="function-shift_key"></a>

```rust
fn shift_key(key: egui::Key) -> bool
```

The function `shift_key` reports whether a key is a Shift key. The parameter `key` is the egui key. Left Shift returns true. Right Shift returns true. Every other key returns false.

### shift_released_just_before

<a id="function-shift_released_just_before"></a>

```rust
fn shift_released_just_before(events: &[egui::Event], index: usize) -> bool
```

Shift went up, and no other key went down between that release and this event.

### enter_stroke

<a id="function-enter_stroke"></a>

```rust
fn enter_stroke(shift: bool, command: bool, alt: bool) -> Key
```

Enter is CR. Shift-Enter is one LF. Command or Alt keeps the CR.

### stroke_of

<a id="function-stroke_of"></a>

```rust
fn stroke_of(event: &egui::Event) -> Option<TermStroke>
```

The function `stroke_of` turns one egui event into a [`TermStroke`](chrome/enum.TermStroke.md). The parameter `event` is that event. A copy event becomes [`TermStroke::Key`](chrome/enum.TermStroke.md#member-key) of `Key::CtrlC`. A paste event becomes [`TermStroke::Paste`](chrome/enum.TermStroke.md#member-paste) with the cloned text.

A text event with a non-empty string becomes [`TermStroke::Text`](chrome/enum.TermStroke.md#member-text). An empty text event is ignored. A key event that is pressed is passed to `map_key`. A key release, and every other event, returns `None`.

| | |
| --- | --- |
| **Specification** | PK-1292 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md |

### stroke_at

<a id="function-stroke_at"></a>

```rust
fn stroke_at(
    events: &[egui::Event],
    index: usize,
    live_shift: bool,
    live_command: bool,
    live_alt: bool,
) -> Option<TermStroke>
```

The Enter event's shift bit can be clear while Shift is held, or just released.

| | |
| --- | --- |
| **Specification** | PK-1293 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md |

### map_key

<a id="function-map_key"></a>

```rust
fn map_key(key: egui::Key, modifiers: egui::Modifiers) -> Option<TermStroke>
```

The function `map_key` turns one pressed egui key into a [`TermStroke`](chrome/enum.TermStroke.md). The parameter `key` is that key. The parameter `modifiers` supplies command, shift, and alt. An unmapped key returns `None`.

Enter uses `enter_stroke`. Backspace, Escape, Home, End, and Delete become those terminal keys. Command with Arrow Up is the motion `Line Down`. Command with Arrow Down is the motion `Line Up`. Shift with Page Up is `Page Up`. Shift with Page Down is `Page Down`. Command with L, and without shift or alt, is `Clear Buffer`.

Command with Arrow Left is `Key::CtrlLeft`. Command with Arrow Right is `Key::CtrlRight`. Plain arrows are the matching direction keys. Command with A, C, and E become `Key::CtrlA`, `Key::CtrlC`, and `Key::CtrlE`. Command and Shift with C returns [`TermStroke::Copy`](chrome/enum.TermStroke.md#member-copy) and writes no key. Command and Shift with Z, and without alt, is `Key::Redo`. Command with Z, and without shift or alt, is `Key::CtrlZ`.

### cell_at

<a id="function-cell_at"></a>

```rust
fn cell_at(
    origin: egui::Pos2,
    pos: egui::Pos2,
    char_w: f32,
    row_h: f32,
    cols: usize,
    rows: usize,
) -> (usize, usize)
```

The function `cell_at` returns the grid row and column under a pointer. The parameter `origin` is the grid's top-left point. The parameter `pos` is the pointer. The parameters `char_w` and `row_h` are the cell size. The parameters `cols` and `rows` are the grid size. The result is `(row, column)`.

A non-positive cell size returns `(0, 0)`. A zero column count or a zero row count returns `(0, 0)`. Otherwise the column is the floored horizontal distance divided by `char_w`, and the row is the floored vertical distance divided by `row_h`. A negative distance becomes 0. The row is clamped to `rows - 1`. The column is clamped to `cols - 1`.

### selection_lines

<a id="function-selection_lines"></a>

```rust
fn selection_lines(
    grid: &Grid,
    r0: usize,
    c0: usize,
    r1: usize,
    c1: usize,
    keep_spaces: bool,
) -> String
```

One highlight. `keep_spaces` leaves the trailing spaces on each row.
The console copy trims those spaces. Pull keeps them.

### selected_text

<a id="function-selected_text"></a>

```rust
pub(crate) fn selected_text(
    grid: &Grid,
    r0: usize,
    c0: usize,
    r1: usize,
    c1: usize,
) -> String
```

The function `selected_text` returns the highlighted grid text with trailing spaces removed on each row. The parameter `grid` is the terminal grid. The parameters `r0` and `c0` are one corner. The parameters `r1` and `c1` are the other corner. The corners may be in either order.

The text comes from `selection_lines` with spaces not kept. Rows are joined by a line feed. A missing history row is skipped by that helper.

### last_screen_line

<a id="function-last_screen_line"></a>

```rust
fn last_screen_line(grid: &Grid) -> String
```

Last non-empty row on the screen that is showing. Scrollback stays out.
A blank row is skipped. The line is trimmed.

### pull_source

<a id="function-pull_source"></a>

```rust
fn pull_source(
    grid: &Grid,
    anchor: Option<(usize, usize)>,
    current: Option<(usize, usize)>,
) -> Option<String>
```

Highlight when its trim is not empty. Spaces in that highlight stay.
Otherwise the last non-empty screen line. Empty when neither has text.

### ask_pull

<a id="function-ask_pull"></a>

```rust
fn ask_pull(
    cards: &mut Vec<TurnCard>,
    seq: &mut u64,
    grid: &Grid,
    anchor: Option<(usize, usize)>,
    current: Option<(usize, usize)>,
) -> PullAsk
```

A new card. The prompt is the terminal text. The mark is Excluded.
The prior card's model stays on that card. An empty source adds nothing.

### terminal_card

<a id="function-terminal_card"></a>

```rust
fn terminal_card(cards: &[TurnCard], focus: usize) -> Option<&TurnCard>
```

The focused card. A focus past the end uses the last card. An empty deck is none.

### ask_terminal

<a id="function-ask_terminal"></a>

```rust
fn ask_terminal(card: Option<&TurnCard>) -> TerminalAsk
```

Reply when its trim is not empty. Otherwise the prompt. An empty body is quiet.
A burner is refused before either body is read. The card stays.

### terminal_preview

<a id="function-terminal_preview"></a>

```rust
fn terminal_preview(text: &str, max: usize) -> String
```

Newlines become spaces. Past `max` UTF-16 units, the rest becomes an ellipsis.

### terminal_confirm_word

<a id="function-terminal_confirm_word"></a>

```rust
fn terminal_confirm_word(raw: &str) -> bool
```

`OK` confirms. Every other word cancels.

### ask_terminal_confirm

<a id="function-ask_terminal_confirm"></a>

```rust
fn ask_terminal_confirm(preview: &str) -> Result<bool, String>
```

The function `ask_terminal_confirm` asks the operator to confirm a terminal send. The parameter `preview` is the shortened text shown in the chooser. The function runs `powershell.exe` with `-NoProfile`, `-STA`, and `-Command`. The command text is `TERMINAL_CONFIRM_CHOOSER`. The preview is placed in the environment variable `AETHERIS_TERMINAL_PREVIEW`.

A failure to start the process returns that error text. A process that exits unsuccessfully returns the trimmed standard error. Empty error text returns `CHOOSER_MISS`. A successful exit reads the first line of standard output. `terminal_confirm_word` accepts only the trimmed word `OK`. Any other word returns false. A missing first line is treated as empty and returns false.

### terminal_normalize

<a id="function-terminal_normalize"></a>

```rust
fn terminal_normalize(text: &str) -> String
```

Collapse CR LF and a bare CR to LF.

### terminal_units

<a id="function-terminal_units"></a>

```rust
fn terminal_units(text: &str) -> usize
```

UTF-16 units. That is Java `String.length`.

### prepare_terminal

<a id="function-prepare_terminal"></a>

```rust
fn prepare_terminal(text: &str) -> Result<String, &'static str>
```

LF-only body. Over the cap is refused. The dialog has not opened.

### terminal_bytes

<a id="function-terminal_bytes"></a>

```rust
fn terminal_bytes(live: bool, text: &str) -> Result<Vec<u8>, &'static str>
```

Bracketed paste when a session is up. LF stays. No trailing Enter.
`no terminal` when the session is down.

### begin_terminal

<a id="function-begin_terminal"></a>

```rust
fn begin_terminal(cards: &[TurnCard], focus: usize) -> TerminalSeat
```

Confirm, then the full text. Cancel leaves the status. A chooser miss names the reason.

### in_selection

<a id="function-in_selection"></a>

```rust
fn in_selection(
    anchor: Option<(usize, usize)>,
    current: Option<(usize, usize)>,
    row: usize,
    col: usize,
) -> bool
```

The function `in_selection` reports whether one cell lies inside the highlight. The parameter `anchor` is one corner. The parameter `current` is the other corner. The parameters `row` and `col` are the cell. A missing corner returns false.

The row range and the column range include both ends, in either order. A cell inside both ranges returns true. A cell outside either range returns false.

### paint_colors

<a id="function-paint_colors"></a>

```rust
pub(crate) fn paint_colors(cell: &Cell, selected: bool) -> (egui::Color32, egui::Color32)
```

The function `paint_colors` returns the foreground and background for one terminal cell. The parameter `cell` is that [`Cell`](../aetheris-term/grid/struct.Cell.md). The parameter `selected` is true when the cell is inside the highlight. The result is `(foreground, background)`.

A selected cell returns `SELECT_FG` and `SELECT_BG` and ignores the cell colors. Otherwise the foreground comes from `color_of` with fallback `INK`. The background comes from `color_of` with fallback [`PLATE`](#constant-plate). A bold cell whose foreground is an index below 8 uses that index plus 8. A reverse cell swaps the two colors.

### color_of

<a id="function-color_of"></a>

```rust
fn color_of(color: Color, fallback: egui::Color32) -> egui::Color32
```

The function `color_of` turns a terminal [`Color`](../aetheris-term/grid/enum.Color.md) into an egui color. The parameter `color` is that color. The parameter `fallback` is used for the default color. [`Color::Default`](../aetheris-term/grid/enum.Color.md#member-default) returns `fallback`.

[`Color::Index`](../aetheris-term/grid/enum.Color.md#member-index) returns the red, green, and blue bytes from `index_rgb`. [`Color::Rgb`](../aetheris-term/grid/enum.Color.md#member-rgb) returns those three bytes directly.

### index_rgb

<a id="function-index_rgb"></a>

```rust
fn index_rgb(index: u8) -> [u8; 3]
```

The function `index_rgb` returns the red, green, and blue bytes for one color index. The parameter `index` is that index. The result is `[red, green, blue]`.

An index below 16 returns the matching row of `ANSI`. An index of 232 or above is a gray ramp. The level is `8 + 10 * (index - 232)`, and all three bytes use that level. Any other index is the 6 by 6 by 6 cube that starts at 16. Red is the index divided by 36. Green is the remainder divided by 6. Blue is the remainder modulo 6. A part of 0 is the byte 0. Any other part is `55 + 40 * part`.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
