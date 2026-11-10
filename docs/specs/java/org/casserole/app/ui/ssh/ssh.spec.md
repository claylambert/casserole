# org.casserole.app.ui.ssh

Component file. It carries how this package is built.

**Depends:** [java/org/aetheris/config/config.spec.md](../../../../aetheris/config/config.spec.md), [java/org/aetheris/swing/swing.spec.md](../../../../aetheris/swing/swing.spec.md), [java/org/aetheris/wire/wire.spec.md](../../../../aetheris/wire/wire.spec.md), [java/org/casserole/mood/mood.spec.md](../../../mood/mood.spec.md), [java/org/roux/token/token.spec.md](../../../../roux/token/token.spec.md)

## The master bar

The font control sizes every label, field, and button on the master control bar.

The panes already take that size. The first layout uses the stored size. The range stays 8 through 36. The Swing default stays 20. The Rust default stays 14. The menus stay. The folded bar drops the font before the tabs and the menu are painted, and the menu puts its own size back.

`applyBarFont` is package-private. `seatBarFont` is private. `seat_bar_font` is private. Both clients change.

**Truth.** Labels, fields, and buttons on the master control bar follow the font control, including the first layout, which uses the stored size. The range is 8 through 36. Swing's default stays 20 and Rust's stays 14. Menus keep their own sizing. The folded bar clears the font before the tabs and the menu.

**Reason.** The panes followed the font control while the master bar stayed on a fixed face, so the fields and buttons no longer matched the size the operator had set.

**Avoidances.** Do not resize the menus from the bar font. Do not leave the folded bar's font in place across the tabs and the menu. Do not change the Swing default off 20 or the Rust default off 14. Do not widen the range past 8 through 36. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-0057 PK-0058 PK-0059

## The capital mark

The About box writes a capital C in the copyright mark.

The Swing About box and the Rust About body both write that capital C. The source-file headers stay. The license line stays.

Both clients change.

**Truth.** The copyright mark in About uses a capital C in both clients. Source headers and the license line are not rewritten by this rule.

**Reason.** The mark was a lowercase c, so the copyright line in About did not match the capital C the notice uses.

**Avoidances.** Do not write a lowercase c in the About copyright mark. Do not rewrite source-file headers. Do not rewrite the license line. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-0060

## The menu, the modal ink, and the search clear

The font control resizes the main menu folded and unfolded. A modal control that paints a dark word takes the theme's light foreground. The search field on the lower canvas bar has a red x on its right, and a click clears the search text and the find filter so the hidden cells come back.

One menu bar moves between the open seat and the folded seat. `applyMenuFont` sets that bar, and every row under it, to the font-control point size. The control calls it. The first seat calls it. Rust `paint_menu` seats the same point size on the bar and inside each menu. The folded path clears the bar font before the menu. The menu puts it back.

The Clear mood Yes word is black until it takes the panel foreground. The Rust Yes word takes `INK`. Option-pane buttons take that same foreground. The pin x stays black. Menu green stays. Hover emerald stays. The thinking body stays.

The search field commits a find filter. A cell whose user text and assistant text both miss a case-insensitive contains stays off the canvas. An empty filter shows every cell the conceal set still allows. Search still selects the first hit and does not change a cell state. The red x is not a bar button. Its fill is the pin red. A click empties the field and the filter and rebuilds the deck. Hide stays the only path that adds a conceal id.

The package method is `applyMenuFont`. The private helpers are `paintMenuFont` and `findHits`. The Rust helpers are `find_hit` and `find_slots`. Both clients change. The canvas bar height is not changed here. The About layout is not changed here.

**Truth.** The main menu, open and folded, follows the font-control point size. Modal words that were black, including Clear mood Yes and option-pane buttons, use the light foreground. Rust Yes uses `INK`. The pin x stays black. A red x on the search field, filled with the pin red, clears the field and the case-insensitive find filter and rebuilds the deck. A cell drops out only when both its user text and its assistant text miss. An empty filter does not add conceal ids.

**Reason.** The menu stayed a fixed size when the font grew, modal Yes was black on a dark ground, and a search had no way to clear the filter short of editing the field by hand.

**Avoidances.** Do not keep a second menu bar for the folded seat. Do not leave the Clear mood Yes word black. Do not recolor the pin x, the menu green, the hover emerald, or the thinking body. Do not make the red x a bar button. Do not change a cell state from Search. Do not let the filter add a conceal id. Do not hide a cell when either the user text or the assistant text contains the query. Do not add a package, a public type, a public method beyond `applyMenuFont`, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-0080 PK-0081 PK-0082 PK-0083 PK-0084 PK-0085

## Send file keeps the full word

Send file keeps the full word. Every control that lacked a name now has one.

Swing `seatSendFileWidth` adds three ems past the word. Swing `seatBarTips` names the connection bar. Rust `send_file_button` does the same for the word. New and Clear name what they do. Group, Join, and Ungroup name what they do. Run and Stop already had tips. Swing New turn and Clear turns already had tips.

Both clients change. The wire does not gain, lose, or reorder a system line.

**Truth.** Send file is wide enough for the whole word plus three ems, in both clients. New, Clear, Group, Join, and Ungroup each carry a tip that says what that control does. Run, Stop, New turn, and Clear turns keep the tips they already had.

**Reason.** Send file was clipping its own label, and several controls had no tip, so the operator could not read the word or learn the click.

**Avoidances.** Do not shorten Send file back under the word plus three ems. Do not replace the tips on Run, Stop, New turn, or Clear turns. Do not leave New, Clear, Group, Join, or Ungroup without a tip that states the action. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-0114 PK-0115 PK-0116 PK-0117 PK-0118 PK-0119 PK-0120 PK-0121

## The heatbar sits on the status bar and wears the ring count

One heat bar sits north of the thin status bar on both tabs, and it wears the ring count.

The notebook strip is not seated. The inspector keeps T. The canvas word is `tokenRingUsed`. The SSH word is the session estimate. Token Calc opens on that same integer. The Rust inspector does not paint the estimate word. The status heat bar keeps the ring integer.

The 5 pixel track stays. The 2 by 9 needle stays. The posts at 70 and 90 stay. The 6 pixel lunge stays. One flash stays. Empty text stays 1.

The public methods are `tokenRingUsed`, `tokenRingWindow`, and `sessionMemoryText` on the widget and the terminal panel. The private helpers are `sessionMemoryText` and `sessionMemoryBlocks` on the frame, and `dropTrailingBlankLines` on the widget. Both clients change. The wire does not gain, lose, or reorder a system line.

**Truth.** One beat sits north of the thin status bar on the SSH tab and on the canvas tab. The notebook strip is absent. T stays in the inspector. The canvas integer is `tokenRingUsed`. The SSH integer is the session estimate. Token Calc opens on that integer. Rust does not paint a second estimate word in the inspector. Empty text is 1. Geometry stays a 5 pixel track, a 2 by 9 needle, posts at 70 and 90, a 6 pixel lunge, and one flash.

**Reason.** A second beat on the notebook, and an inspector word that was not the ring integer, made the status bar and the ring disagree about which count was live.

**Avoidances.** Do not seat the notebook strip beside this beat. Do not move T out of the inspector. Do not paint the estimate word in the Rust inspector. Do not open Token Calc on a different integer. Do not change empty text off 1. Do not change the track, needle, posts, lunge, or flash. Do not add a system line on the wire. Do not add a package, a public type, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-0122 PK-0123 PK-0124 PK-0125 PK-0126

# The control bar button reads Load File

The full cycle is named, and the build is named. Java moves. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0138 PK-0139 PK-0140 PK-0141

# The SSH mood flag is saved by canvas user

The full cycle is named, and the build is named. Java moves. Rust moves. The three slices land in one tree. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0142 PK-0143 PK-0144 PK-0145 PK-0146 PK-0147 PK-0148 PK-0149 PK-0150

# The VT100 heatbar moves while the user types

The full cycle is named, and the build is named. Java moves. Rust moves. The three slices land in one tree. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0152 PK-0153 PK-0154 PK-0155 PK-0156 PK-0157 PK-0158 PK-0159 PK-0160

## Build

MoodDoor.seat writes one pair and takes every prior pair out. A clear leaves the turns. MoodDoor.adopt reads the pair already stored. MoodDoor.porchRules returns the pair and leaves the host out. MoodDesk.DESK is the desk. canvasSystems and moodHead serve the canvas push. sshLine serves the checkbox and the saved line. announceBody serves the shell frame, including a clear on a pane that has not been told. parseShell reads that frame. ChatSession seats, adopts, and keeps the porch through the desk. AetherisNotebookPanel asks the desk for the canvas lines, the head, the glass sentence, and the SSH line. SshClientApp asks the desk for the frame. TerminalSession.moodTold is set only after a successful write, and a disconnect or an error clears it.


**semantic-key:** PK-0165

## What stays

MoodDesk stays the desk. MoodDoor stays the door. The package stays org.casserole.mood. normalize, moodHead, announceBody, and parseShell stay. seat, adopt, and porchRules stay the door. TerminalSession.moodTold still remembers the announce. A pane already told the same line still sends nothing. Pin commit and pull crosstalk stay on their own path. The mark seat stays. The one-desk row stays locked. The mark row stays locked, with the smoke sentence already filed. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The next free number stays the number already next. The outside-list number already next stays next. No new public type is added. No new public method is added. No new G-key, vault column, channel, AMWP type, color key, library, or Cargo.toml edit is added. No glossary row is added. The match already named in the spec stays. chrome.rs stays. grid.rs stays. application.yaml stays. The yaml mood files stay.


**semantic-key:** PK-0167

## Proof

The product tree is compiled with tests skipped. Then the battery runs: MoodDeskTest, ConsoleMoodTest, SshMoodMemoryTest, ShellMoodFrameTest, PoolMoodRulesTest, and BlankHostSeatTest. No new JUnit class is added. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The Rust client crate stays unrun. The crate stays unwritten.

**Truth.** The footer switch gates the canvas push and the shell. The checkbox makes SSH available. The switch off clears a checked SSH seat, even when the box stays checked. canvasSystems returns OPENER, normalize of the saved line, and CLOSER while the switch is on and the line is non-blank, and an empty list otherwise. moodHead joins those three with newlines. OPENER is: I'm opening a window with this atmosphere… would you like to step into it with me? CLOSER is: Shape your replies around this mood — let it breathe through the words. LEGACY stays the old sentence and a later seat drops that row. sshLine reads the checkbox, the footer switch, and the saved line, and either gate off returns null. The canvas execute keeps the triple and fails too-large rather than dropping it. seat writes host, OPENER, constructed mood, CLOSER, then the turns. shell_mood_clear goes to a told pane when the want line is empty. setMoodLine writes the triple when the want line changes. The same line sends nothing. applyTransferReplace calls noteMoodRelay before the status line when the result starts with ok. Glass writes the triple when the field holds a line, and the switch does not gate the glass. Pool porch rules are opener, a blank line, constructed mood, a blank line, and closer. Rust stays.

**Reason.** A send that did not fit the AMWP frame rebuilt the body without the mood and still sent, so a long canvas, a switch turned back on, and a Transfer Conversation went out without the mood. sshLine ignored the footer switch, so a checked SSH seat stayed announced when the switch was off. The mood is not a cell, and the too-large retry was dropping it. Transfer onto Roux reseated the server's previous line because the client did not send the gate after ok. The engagement asked for is opener, constructed mood, and closer, not one activate sentence after the mood.

**Avoidances.** Do not rewrite slices already locked, the one-desk row, or the mark row, and do not drop the mark seat or its filed smoke sentence. Do not close the open batch or the closed feature. Do not change application.yaml, the yaml mood files, chrome.rs, or grid.rs. Do not retarget branch, action, stage, or the when field. Do not move Rust or file a Rust proposal. Do not change MoodDesk, MoodDoor, org.casserole.mood, normalize, moodHead, announceBody, parseShell, seat, adopt, porchRules, or TerminalSession.moodTold. Do not let a pane already told the same line send again. Do not move pin commit or pull crosstalk onto this path. Do not drop the triple to fit a frame. Do not run a frame that still does not fit. Do not clear history. Do not let a clear that already fit stop fitting. Do not keep a stale legacy line or add a second triple. Do not stop adopt or porchRules from reading a legacy pair. Do not let the switch leave a checked SSH seat announced. Do not let an unchecked box keep the mood while the switch is on. Do not store the triple in canvas cells on a transfer from Roux. Do not skip noteMoodRelay after ok on applyTransferReplace, and do not place it after the status line. Do not let the footer switch gate the glass. Do not make the example paragraph a constant. Do not change the OPENER or CLOSER sentences. Do not drop #MoodDoor or #MoodDesk. Do not change the checkbox tip or the switch tip. Do not add a new JUnit class. Do not skip MoodDeskTest, ConsoleMoodTest, SshMoodMemoryTest, ShellMoodFrameTest, PoolMoodRulesTest, or BlankHostSeatTest. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not run a crate check. Do not advance the next free number or the outside-list number.


**semantic-key:** PK-0168

# The control bar button reads Load File

The control bar button reads Load File. The button that reads Send file takes that word. Java moves. Rust moves. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-0262 PK-0263 PK-0264 PK-0265

# The SSH mood flag is saved by canvas user

The Mood modal shows the SSH activation for the user that has focus on the Aetheris canvas. The flag is saved by user. An armed flag is remembered on that user's SSH panes at startup. Java moves. Rust moves. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-0266 PK-0267 PK-0268 PK-0269 PK-0270 PK-0271 PK-0272 PK-0273 PK-0274

# The VT100 heatbar moves while the user types

The heatbar beat and the estimated token count move while the user types in an SSH VT100 pane. The number follows the focused pane when several panes are open. The canvas path already moves. Java moves. Rust moves. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-0275 PK-0276 PK-0277 PK-0278 PK-0279 PK-0280 PK-0281 PK-0282 PK-0283

## Dependencies

The canvas draft stays with the live-draft slice. The held Roux integer stays the base. The one-transcript type list stays unbuilt.

The unsent line of the focused pane is the only addition. An empty draft adds nothing. The 200 ms beat stays. ComposerHeatbar.showHeuristic already lunges when the integer changes. No server call is made on a key. No new library is added. Cargo.toml is not edited.

The proof compiles the product tree with tests skipped and checks the Rust client crate offline. The window stays closed.


**semantic-key:** PK-0284

## Land

Java is named. The crate does not move.

The OK label stays OK. The fold letters stay v and ^.

No side move on this plate.

The cap is the specified text on the button. The short fit no longer replaces that text with an ellipsis.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `holdShortLabel` gives a one-character or two-character label, and any label already under its word, two ems after the border is on. The button text is not changed, so OK and the v fold show the specified text.

**Reason.** The short fit locked a width and a height that could replace a short label with an ellipsis, so OK and the v fold did not show the specified text.

**Avoidances.** Do not change the button text. Do not let Approve stop growing by three ems after the short fit. Do not move Yes and No off their own floor. Do not change the Token window title or the Token Calc menu item. Do not change the fold letters away from v and ^. Do not give longer labels a width other than the short-fit width. Do not file a feature smoke. This row is not a feature. There is no crate commit on this plate. Do not move Rust. Do not edit yaml. Do not stage the card-round hunks. `holdShortLabel` stays private. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-0415 PK-3791

## Edits

`FoldingPanel.applyMenuFont` walks the one bar and every row and sets the point size. `fontSizeChanged` calls it. The first seat calls it with the current size. `paint_menu` calls `seat_bar_font` on the bar and inside each menu. Clear mood Yes takes `getPanelForeground`. The Rust Yes takes `INK`. `OptionPane.buttonForeground` takes the panel foreground. The search clear is a red x on the pin fill, snapped to the field with no gap, and it is not a bar button. Search copies the field into the filter and selects the first hit. `findHits` and `find_slots` omit a cell whose user text and assistant text both miss a case-insensitive contains. An empty filter shows the conceal-allowed deck. The x empties the field and the filter and rebuilds. `search_turn` uses that same contains.


**semantic-key:** PK-0385

# Send file keeps the full word

Send file keeps the full word. Every control that lacked a name now has one.


**semantic-key:** PK-0405 PK-0406 PK-0407 PK-0408 PK-0409 PK-0410 PK-0411 PK-0412

## Depends

`FoldingPanel.java`. `SshClientTheme.java`.

**Truth.** The line under the control bar and the divider edges take the dark split ground. Java moves. Rust stays.

**Reason.** That line and those edges were still the pale ground instead of the dark split.

**Avoidances.** Do not change the divider away from 11 pixels. Do not drop the arrows or the chip words. Do not treat this row as a feature. Do not edit yaml. Do not move Rust. Do not add a public type, a public method, a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.


**semantic-key:** PK-0416

# Send file keeps the full word

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.


**semantic-key:** PK-0446 PK-0447 PK-0448 PK-0449 PK-0450 PK-0451 PK-0452 PK-0453

## Holes

A hole is a slated row that assumes a piece the tree does not have, and this list does not close the hole.

A plan has to close the hole or mark it open.

1. The remote model has no router. `AetherisNotebookPanel.assemble` sends `execution_target`. An empty cell field becomes the string `ollama`. No panel code assigns `CellRecord.executionTarget`. The operator cannot pick a model. `statusLine` always appends `engine: ollama`. `AmwpFacade.execute` never reads `executionTarget`. It always calls `LlmService.complete`. `SpringAiOllamaLlmService` is the only `LlmProvider`, and its id is `ollama`. `complete` sets the local `num_ctx` whenever the integer is positive. A hand-edited vault cell aimed at `grok/model` still runs on the local engine.

2. Air-gap is parsed and never consulted. `AetherisYaml.isAirGap` has no caller outside that type. The constructor default is false. The sample file is false. No code reads a cloud key from the environment, from `~/.aetheris/`, or from `~/.casserole/`. The flag lives in the shell file. The provider lives in the host. Neither side refuses a cloud target. The plan has to name the side that fails the cell and leaves the stored target alone.

3. The tool host has no wire, no lane, and no knob. `AmwpConstants` stops at `CHANNEL_STREAM` `0x0003`. There is no `0x0004`. `SshTtyConnector.writeAmwp` always writes channel `0x0002`. `Lane` is `SSH_IO`, `LLM_EXEC`, and `GENERAL`. A comment names `TOOL_EXEC`. The enum does not. A task on a missing lane throws `no pool`. The `cell` table has no `tool_calling_enabled` and no temperature, max-tokens, or top-p columns. A search of the host sources finds no tool-host type. `VaultSql.DDL_TRACE` creates `trace`. The only trace statement in use is `DELETE_TRACE_FOR_CANVAS`. Nothing inserts a row.

4. Pipes and groups have columns the save path does not keep, and statuses the table does not have. `group_id` is not a column. `execution_status` is not a column. An error frame sets the status line and clears the run. The cell does not record `FAILED`. An empty assistant body is the same shape as never run, excluded output, or a failure. `INSERT_CELL` writes `parent_cell_id`, `branch_id`, and `column_position`. `bindUpdate` does not. A later edit of those fields, saved through `saveCell`, stays in memory and dies on the next load. `{{cell[k].output}}` is not resolved. The serial, at this study, does not define `k`. The inspector's numbers are paint order, newest first, and they are not cell indexes. Using those numbers as `k` would read the wrong cell.

5. The mood line has no system role on the wire. `AmwpToon` writes `role` and `text`. `AmwpFacade` maps `assistant` to an assistant message. Every other role becomes a user message. A system-role shape line would be handed to the model as something the operator said.

6. Search is a query with no door. `VaultStore.search` is the only caller of `SEARCH_CELLS`, and the shell never calls `search`. The predicate is `user_clob LIKE ? OR assistant_clob LIKE ?` across the whole vault. There is no canvas predicate. The needle is wrapped in `%` on both sides. `%` and `_` inside the needle are wildcards. An empty needle matches every non-null body. Burner NULL bodies do not match `LIKE`. That part holds.

7. The server system line is invisible to the working set. `AmwpFacade` prepends the host `chat.system-prompt` to the notebook region and to the `Prompt` on every execute. The default text is `You are the Casserole assistant. Be concise and useful.` `assemble` and the inspector do not include that line. A mood system line would be a second system text. The first one is already applied, and the operator cannot see it in the inspector.

8. One client run slot. `UserRun` keeps one `cellId` and one `stoppedId`. The answer map can hold more than one cell. The spinner and Stop cannot. The parallel rule is one in-flight execute per cell. The client can show one.

9. A working set larger than one frame has no splitter. `AmwpCodec.encode` throws `AMWP payload exceeds 1 MiB`. The client always sends fragment index 0 and count 1, with the fragment flag clear. The server strips one prefix and decodes one body. `frag_count` is not a reassembly loop. A pipe that inlines a large output fails the run on the client before the server sees it.

10. New columns have no schema version. There is no version table. The collapsed flag arrived through `PROBE_COLLAPSED` and `ALTER_COLLAPSED`. `group_id`, a status, a tool knob, and a mood row need a migration. The tree has the one-off probe pattern and no version.

**Truth.** Ten holes are open in the tree this study read: no router, an unconsulted air-gap flag, no tool channel or lane, group and pipe columns the update does not keep, no system role, search with no door, an invisible host system line, one client run slot, no multi-frame split, and no schema version.

**Reason.** A slated row that assumes a router, a consulted flag, a tool lane, a pipe index, or a version table will build on air. Naming the hole is what lets a later answer close it or leave it open on purpose.

**Avoidances.** Do not close a hole inside the study. Do not treat paint order as `k`. Do not treat an empty assistant body as a recorded failure. Do not let a positive `num_ctx` on the local provider stand in for a remote call. Do not add a reassembly loop by assuming `frag_count` already loops.


**semantic-key:** PK-0462

## Console model

The VT100 console returns on the last selected model family. Pool stays pool. The word survives an application restart.

The shell used to start each session on ollama and forget the word last set. The session now reads `~/.casserole/{user}/console-model` before the first turn. Version 1 carries the family word. The word is ollama, grok, or pool. A missing file leaves the family at ollama. A version other than 1 is left unread and is not overwritten. The dialog set writes the file. The shell word `model` writes the file. The dialog still asks the shell, so the modal shows the stored word. The next turn uses that word. Pool stays pool. The notebook strip stays on the bottom. The VT100 strip stays on the top. Ollama and Grok options stay as they are.

The word sits beside the console history and off the vault. The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this word waits until it is named. Nothing beside this word moves. The cells already on the canvas stay as they are.

**Truth.** Before the first turn, the session reads version 1 of `~/.casserole/{user}/console-model`. The family word is ollama, grok, or pool. A missing file leaves the family at ollama. A version other than 1 is left unread and is not overwritten. Setting the model in the dialog, or with the shell word `model`, writes the file. The modal shows the stored word because it still asks the shell. The next turn uses that word. Pool stays pool.

**Reason.** The console was returning on ollama even when the last choice was pool. The last family has to survive an application restart, and pool has to stay pool.

**Avoidances.** Do not start a session that has a stored word back on ollama. Do not store the word in the vault. Do not read or overwrite a version other than 1. Do not accept a family word other than ollama, grok, or pool. Do not move the notebook strip or the VT100 strip. Do not change the Ollama or Grok options. Do not write a Rust crate on this row.


**semantic-key:** PK-0648 PK-0649 PK-0650 PK-0651 PK-0652 PK-0653 PK-0654 PK-0655 PK-0656 PK-0657 PK-0658 PK-0659 PK-0660 PK-0661 PK-0662 PK-0663 PK-0664 PK-0665 PK-0666 PK-0667 PK-0668 PK-0669 PK-0670 PK-0671 PK-0672 PK-0673 PK-0674

# Aetheris Shell

This page builds the Java shell around the terminal. The map is [program-spine.md](../../../../aetheris/config/config.spec.md). The notebook is [aetheris-java-canvas.md](../../../../aetheris/swing/swing.spec.md).


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

## VT100 strip

The thinking pane is one component. A second instance sits on the VT100 pane. The stream difference stays the signal. The thought stays off the terminal.

The pull-down opens a block with a tag, appends text, and finishes. It does not read a frame, a cell, or a channel. The notebook keeps one instance and feeds it cell thoughts. Each VT100 panel keeps another instance and feeds it a nil-cell kind `t` when console pool thinking is on. The tag on that instance is pool. The notebook strip does not paint the nil cell. The two logs stay apart. The VT100 strip does not write the vault log. The assistant reply still paints on the VT100. The terminal bytes stay on channel T. Ollama and Grok on the console stay as they are. A canvas cell keeps its own gate.

One strip is seated on each terminal panel. The reply stays on channel T. Kind `t` on the nil cell is what the VT100 strip receives. Cell thoughts stay on the notebook strip. The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this strip waits until it is named. Nothing beside this strip moves. The cells already on the canvas stay as they are.

**Truth.** One pull-down component opens a tagged block, appends text, and finishes, without reading a frame, a cell, or a channel. The notebook instance receives cell thoughts. Each VT100 panel has its own instance, fed a nil-cell kind `t` when console pool thinking is on, tagged pool. The notebook strip does not paint the nil cell. The logs stay apart. The VT100 strip does not write the vault log. Terminal bytes stay on channel T. The assistant reply still paints on the VT100.

**Reason.** The thinking pane has to be one reusable component, with a second instance on the VT100, so a pool thought opens that strip and never lands on the terminal.

**Avoidances.** Do not make the pull-down read a frame, a cell, or a channel. Do not paint the nil cell on the notebook strip. Do not mix the two logs. Do not write the VT100 thought into the vault log. Do not move terminal bytes off channel T. Do not move the assistant reply off the VT100. Do not change Ollama or Grok on the console. Do not move a canvas cell off its own gate. Do not write a Rust crate on this row.


**semantic-key:** PK-3583 PK-3584 PK-3585 PK-3586 PK-3587 PK-3588 PK-3589 PK-3590 PK-3591 PK-3592 PK-3593 PK-3594 PK-3595 PK-3596 PK-3597 PK-3598 PK-3599 PK-3600

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

## The opening frame

The first start centers the shell at a usable size.

A saved rectangle that meets a screen stays. A missing origin, and a saved rectangle that misses every screen, seats 1200 by 760, capped to the usable screen, and centered. The floor is 640 by 480 when the screen allows. The size never exceeds the usable screen. A later resize still saves.

The private helpers are `seatOpeningFrame` and `usableScreen`. The Swing client changes. The Rust client stays.

**Truth.** A saved on-screen rectangle is restored. Otherwise the frame is 1200 by 760, capped to the usable screen, centered, and never below 640 by 480 when the screen can hold that floor. A later resize is saved.

**Reason.** A missing origin, or a rectangle parked on a screen that is gone, was opening the shell off the glass or at a size the display could not hold.

**Avoidances.** Do not replace a saved rectangle that still meets a screen. Do not exceed the usable screen. Do not drop the floor below 640 by 480 when the screen allows it. Do not stop saving a later resize. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-3680

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

## Edit

FoldingPanel.seatPlateFont sets the SSH button, the Aetheris button, and the product tab pane to sans serif at the control-bar size, clamped from 8 through 36.

It clears a stored button size so the word can grow. applyMenuFont calls it, and that method already runs from the font spinner and from the first menu seat. showChrome calls it again after a fold installs a tab UI, so the size survives that install. SshClientApp stays as it stands. Rust stays. A crate move for this tab size waits until it is named.


**semantic-key:** PK-3717

# The Mood modal paints SSH Mood Active

The full cycle is named, and the build is named. Java moves. Rust stays on this row. The crate moves with the per-user flag and the live typing slices. The three slices land in one tree. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-3718 PK-3719 PK-3720 PK-3721 PK-3722 PK-3723 PK-3724 PK-3725 PK-3726 PK-3727 PK-3728

# The thinking spinner sits on the control bar

The full cycle is named, and the build is named. Java moves. Rust moves. The working word is NUT, and that word stays the owner's. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-3731 PK-3732 PK-3733 PK-3734 PK-3735 PK-3736 PK-3737 PK-3738

## Edit

AetherisNotebookPanel no longer parents the cluster on the footer. The footer keeps the user combo. thinkCluster returns the same spinner.

SshConnectionPanel.holdThink remembers the cluster. parkThink seats it at the end of the button row, after the font control. applyBarFont still sizes its labels when the folded row is holding it.

FoldingPanel.seatThink takes the cluster. placeThink parks it on the button row while the bar is open, and seats it on the east of the folded menu row while the bar is folded. One cluster moves. It is not painted twice. SshClientApp seats the cluster after the menu is installed.

Rust paints the cluster after the font control on the open button row, and at the far east of the folded menu row. A quiet deck paints nothing. east_reserve keeps the combo width and drops the spinner width. paint_east_capsule paints the combo only.

The test the_user_combo_stays_on_the_footer locks the combo on the footer and an empty spin rect. That test module does not compile on the recorded head. The proof does not run it.


**semantic-key:** PK-3739

# A key on an SSH pane posts the count

The stamp names the build. User input on the SSH VT100 tab posts the token edit the way a canvas cell does. The count runs off the key. Each pane keeps its own integer. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-3740 PK-3741 PK-3742 PK-3743 PK-3744 PK-3745 PK-3746 PK-3747 PK-3748 PK-3749 PK-3750 PK-3751 PK-3752 PK-3753

## Product

The product files are SshTerminalPanel.java and the posts in SshClientApp.java. One product commit. Java moves. Rust stays.


**semantic-key:** PK-3754

# The Mood modal paints SSH Mood Active

SSH Mood Active is asked for on the Mood modal. When that box is on, the mood line is seated on every connected SSH pane of the canvas user. Java moves. Rust stays on this row. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-3755 PK-3756 PK-3757 PK-3758 PK-3759 PK-3760 PK-3761 PK-3762 PK-3763 PK-3764 PK-3765

# The thinking spinner sits on the control bar

The thinking spinner and its clock leave the crowded BB1 row. Open, they sit to the right of the global font control. Folded, they sit at the far east of the main menu row. Java moves. Rust moves. The working word is NUT, and that word stays the owner's. This page does not mint a glossary row. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-3766 PK-3767 PK-3768 PK-3769 PK-3770 PK-3771 PK-3772 PK-3773

# A key on an SSH pane posts the count

The SSH VT100 tab posts a token edit the way a canvas cell does. The count runs off the key. Each pane keeps its own integer. Java moves. Rust stays. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-3774 PK-3775 PK-3776 PK-3777 PK-3778 PK-3779 PK-3780 PK-3781 PK-3782 PK-3783 PK-3784 PK-3785 PK-3786 PK-3787

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the section below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. No new channel. No new wire type. The new methods are `SshClientProperties.getProductTab` and `SshClientProperties.setProductTab`. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed.

A close on the SSH tab reopens on the SSH tab. A close on the Aetheris pane reopens on the Aetheris pane and connects.


**semantic-key:** PK-7815

# The main menu hover is a lighter grey tab

When the pointer is over a main menu item, the background shifts to a lighter grey tab button shape. Mirror that main menu look and feel on hover in the Java Aetheris app, for both open and collapsed and shifted menu shapes.


**semantic-key:** PK-3789

## Land

Java is named. The crate does not move.

Min, max, and close stay. The import path stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** On Windows, the native caption takes the theme's frame background for the bar and the border, and the panel foreground for the title words, in dark caption mode. Any other system, a missing handle, or a failed paint leaves the window able to open.

**Reason.** The title bar was not the very dark charcoal of the window theme, so the frame and the caption did not match.

**Avoidances.** Do not add a color key or a library. Do not make the caption methods public. Do not add a public type. Do not drop min, max, or close. Do not move the content pane off the frame background. Do not change the import path. Do not edit yaml. Do not let a caption failure stop the window from opening. Do not paint a caption on a system that is not Windows, or when the native handle is missing. Do not move Rust. Do not stage the card-round hunks. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-7795 PK-7864

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

## Pin fold

The pin bar folds. Enter in the word field does what OK does. Turning the box off keeps the words. Clear empties the field.

`v` folds the pin controls. `^` opens them. The bar starts folded. The choice is saved with the shell. A missing key stays folded. The footer tool bar is not saved. Enter in the word field does what OK does. Turning the box off keeps the words. Only a change in the field, or Clear, changes those words. Clear is a red button with a black x, to the right of the field. The states stay until OK. The OK tip stays the sentence already on the button. The edge paint stays in the chrome paint. The inspector header keeps soft wrap and the two counters. Nothing beside this fold moves. The cells already on the canvas stay as they are.

The pin behavior is already recorded for the Rust client. The crate change for the fold, for Enter, for the kept words, and for Clear waits until that catch-up is named. This row writes no Rust crate.

**Truth.** The pin bar starts folded. `v` folds it and `^` opens it. The choice is saved with the shell, and a missing key stays folded. Enter writes the states the way OK does. Turning the box off keeps the words until the field changes or Clear is used. Clear is a red button with a black x to the right of the field. The states wait for OK. The footer tool bar is not saved.

**Reason.** The pin bar needs to fold out of the way. Enter needs to write the states. The words need to stay until the user changes them, and Clear needs to empty the field.

**Avoidances.** Do not start the bar open. Do not treat a missing key as open. Do not save the footer tool bar. Do not clear the words merely by turning the box off. Do not change the words except by an edit or by Clear. Do not paint Clear as anything other than red with a black x. Do not apply the new states before OK. Do not change the OK tip or move the edge paint out of the chrome paint. Do not drop soft wrap or the two counters. Do not write the Rust crate on this row.


**semantic-key:** PK-4038 PK-4039 PK-4040 PK-4041 PK-4042 PK-4043 PK-4044 PK-4045

## The canvas fill

The scroll pane under the Canvas tab paints the panel ground, not the stock control face.

The cell scroll takes the panel ground after the overlay scroll bar is seated. The quiet bevel stays. The viewport border stays clear.

The Swing client changes. The Rust client stays.

**Truth.** The canvas cell scroll fills with the panel ground after the overlay bar is seated. The quiet bevel remains. The viewport border remains clear.

**Reason.** The lip had gone dark while the scroll pane still painted the stock light control face, so the canvas ground was still wrong.

**Avoidances.** Do not paint the stock control face on that scroll pane. Do not drop the quiet bevel. Do not bring the viewport border back. Do not fill the ground before the overlay scroll bar is seated. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4115

## The mood modal paints SSH Mood Active

The mood modal paints the checkbox SSH Mood Active, and when that box is on, the mood line is placed on every connected shell pane of the canvas user.

The label is SSH Mood Active. It sits to the left of Clear mood. The box follows the canvas user. An armed user, with the mood switch on and a saved line, sends shell_mood to every connected shell pane of that user. A pane that already matches sends nothing. A clear goes only to a pane that was already told. A raw session is left alone. The status strip follows the focused pane's user. The mood modal keeps Clear, Approve, and Cancel. The other client already paints the box and does not change on this rule.

The public methods on existing types are relayArmedMoods, sshMoodLineFor, moodHeld, and setMoodHeld. No new package and no new public type are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The shared mood file stays. The per-user file is a later rule.

The console door order is unchanged. The pinned host stays first. The mood string follows. The activate line follows. The turns follow. No system line is added, removed, or reordered. The Java client carries this.

**Truth.** The mood modal shows SSH Mood Active to the left of Clear mood. An armed user with the switch on and a saved line sends shell_mood to each connected shell pane of that user that does not already match. A clear is sent only to a pane that was already told. A raw session is never touched. The status strip follows the user of the focused pane.

**Reason.** The shell panes of one canvas user should wear that user's mood without a separate trip to each pane. A pane that already matches must not be told again, and a pane that was never told must not be cleared. The checkbox is the shell gate. The modal's Clear, Approve, and Cancel stay the modal's buttons.

**Avoidances.** The console door order does not change. No new shell message type is added. The other client is not edited. The per-user store is not this rule. A raw session is not given a mood.


**semantic-key:** PK-4144 PK-4145 PK-4146 PK-4147 PK-4148 PK-4149 PK-4150 PK-4151 PK-4152 PK-4153 PK-4154

# Ctrl-Shift-C copies the highlight and writes nothing

Ctrl-Shift-C copies the console highlight to the clipboard and the console writes nothing on that chord. Ctrl-C stays the interrupt, byte 0x03.


**semantic-key:** PK-4437

# Product tab

The door keeps one word, `ui.product.tab`. The words are `ssh` and `aetheris`. A missing word opens on SSH. A saved Aetheris word waits for Connect, then takes the plate and lands on the newest card. A close stores the plate that is showing.


**semantic-key:** PK-4442 PK-4443 PK-4444 PK-4445 PK-4446

## Transfer conversation

Transfer Conversation copies the session onto the canvas, and copies the canvas back into the session.

The menu title is `Transfer Conversation`. On the canvas it sits after Duplicate group, on the prompt side and on the reply side. On the VT100 menu it sits after Import and before Select model. OK on the console clears the canvas and copies the session's user and assistant turns onto the cells. OK on the canvas clears the session turns and copies the canvas as input and reply rows. Cancel writes nothing and clears nothing. The configured prompt stays. A running canvas cell refuses the transfer and clears nothing. The confirm stays. The session door stays. Turns are copied as pairs.

There is no new package and no new public type. The public methods are `parseMessageTable`, `pairConversation`, `conversationTable`, `replaceTurns`, `applySessionReplace`, `replaceResult`, `replaceCanvas`, and `transferFromConsole`. There is no new global key, no new vault column, no new channel, no new wire message type, and no new library. No settings file is edited, and no dependency manifest is edited. Both clients carry the transfer.

**Truth.** Rust paints File, Tools, Token Calc, and Help, and the lines under File, Tools, and Help, in `#00ff41`. The word stays that green under the pointer. The order stays File, Tools, Token Calc, and Help. Show Thinking stays under Tools. `Help...` and `About Aetheris...` stay under Help. Token Calc still opens the token window. Context menus stay. Button hover stays `#00FF66`. Java does not change for the menu ink.

`Transfer Conversation` sits after Duplicate group on both the prompt side and the reply side of the canvas, and after Import and before Select model on the VT100 menu. OK on the console clears the canvas and copies the session's user and assistant turns onto the cells. OK on the canvas clears the session turns and copies the canvas as input rows and reply rows. Cancel writes nothing and clears nothing. The configured prompt stays. A running canvas cell refuses the transfer and clears nothing. Both clients carry it.

**Reason.** The Rust main menu words have to match the Java menu green. Separately, a conversation has to be able to move from the session onto the canvas and from the canvas back into the session, from a menu on each side, without opening a further row.

**Avoidances.** Do not paint the menu words any green but `#00ff41`. Do not drop the green while the pointer is on the word. Do not recolor context menus. Do not change the button hover from `#00FF66`. Do not change the tooltip wait, the folded row, the open divider, or the words SSH and Aetheris. Do not change the Java menu for this ink. Do not put `Transfer Conversation` anywhere but after Duplicate group on the canvas menus and between Import and Select model on the VT100 menu. Do not let Cancel write or clear. Do not drop the configured prompt. Do not transfer, and do not clear, while a canvas cell is running. Do not add a package, a public type, a global key, a vault column, a channel, a wire message type, or a library. Do not edit a settings file or a dependency manifest.


**semantic-key:** PK-4451 PK-4452 PK-4453 PK-4454 PK-4455 PK-4456 PK-4457 PK-4458 PK-4459 PK-4460 PK-4461 PK-4462 PK-4463 PK-4464 PK-4465 PK-4466 PK-4467 PK-4468 PK-4469 PK-4470 PK-4471 PK-4472 PK-4473 PK-4474

# Inspector line length

The inspector cuts each kept line to the count on the header, and the mood line stays at 80 characters.


**semantic-key:** PK-4669 PK-4670 PK-4671 PK-4672 PK-4673
