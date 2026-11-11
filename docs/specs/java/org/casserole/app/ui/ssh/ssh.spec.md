# org.casserole.app.ui.ssh

Component file. It carries how this package is built.

**Depends:** [java/org/aetheris/config/config.spec.md](../../../../aetheris/config/config.spec.md), [java/org/aetheris/swing/swing.spec.md](../../../../aetheris/swing/swing.spec.md), [java/org/aetheris/wire/wire.spec.md](../../../../aetheris/wire/wire.spec.md), [java/org/casserole/mood/mood.spec.md](../../../mood/mood.spec.md), [java/org/roux/token/token.spec.md](../../../../roux/token/token.spec.md)

**Depends:** [java/org/aetheris/ssh/ssh.spec.md](../../../../aetheris/ssh/ssh.spec.md)
**Depends:** [java/org/casserole/llm/llm.spec.md](../../../llm/llm.spec.md)
**Depends:** [rust/aetheris/aetheris-client/src/chrome.spec.md](../../../../../../rust/aetheris/aetheris-client/src/chrome.spec.md)

## The tab rule

A divider sits between Connect and the SSH tab. The two tab buttons stand apart.

The open bar carries the line. The folded row stays as it is. The folded rule stays on the menu, after Aetheris and before File, Tools, and Help.

The Java client seats a one-pixel panel on the south of the open head. The ink is the panel foreground. The Java default of that ink is `#dcdcdc`. The live theme may already hold another panel foreground. This plate reads no home file and adds no color key. The line leaves with the open bar.

The Rust client calls `separator()` after the door note and before the SSH tab. The parent column is vertical, so the line is horizontal. That is the same call the folded menu divider uses.

The menu stays. The words stay SSH and Aetheris. The Aetheris tab stays disabled until the mux answers. Connect stays the first button of the open row. The grip stays. The thinking text stays clear. The emerald word stays. The tooltip wait stays.

There is no new package, no new public type, and no new public method. The Java line is a private field. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, no settings-file edit, and no dependency-manifest edit. Nothing beside this line moves. The send set stays as it is. Both clients carry this.

**Truth.** On the open bar, a divider sits between Connect and the SSH tab, and the two tab buttons stand apart. The folded row is unchanged, and its rule stays after Aetheris and before File, Tools, and Help. Java draws a one-pixel panel on the south of the open head in the panel foreground, whose default is `#dcdcdc`, and reads no home file. The line leaves with the open bar. Rust draws a horizontal `separator()` after the door note and before the SSH tab, the same call the folded menu divider uses. Connect stays the first button of the open row. The Aetheris tab stays disabled until the mux answers. The words stay SSH and Aetheris.

**Reason.** Connect and the SSH tab were sitting against each other. A divider has to separate them, and the two tab buttons have to stand apart, on both clients.

**Avoidances.** Do not put the new line on the folded row. Do not move the folded rule from its place after Aetheris and before File, Tools, and Help. Do not paint the Java line thicker than one pixel, or in any ink but the panel foreground. Do not add a color key or read a home file for the ink. Do not leave the line up after the open bar folds away. Do not make the Rust line vertical. Do not use a different separator than the folded menu divider. Do not move Connect off the first place on the open row. Do not enable the Aetheris tab before the mux answers. Do not change the grip, the clear thinking text, the emerald word, or the tooltip wait. Do not add a package, a public type, a public method, a global key, a vault column, a channel, a wire message type, or a library. Do not edit a settings file or a dependency manifest.


**semantic-key:** PK-0038 PK-0039

## The about box

The About box names Clay Lambert, the copyright, the email, and the MIT license, and it shows the application icon large beside the branded icon.

The author is Clay Lambert. The copyright line is `Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>`. The license line is `Licensed under the MIT License`. The lines the shell already shows stay above those three lines. The application icon is the Eye of Horus from `/icons/aetheris.jpg` at 128 pixels. The branded icon is the casserole from `/icons/casserole.jpg` at 96 pixels. The application icon is the larger mark. The frame icon stays the Eye of Horus. `Help...` stays. `About Aetheris...` stays under Help. The title stays `About Aetheris`. The order stays File, Tools, Token Calc, and Help.

The Java client seats the two marks on the north of the dialog and the words under them. A missing file leaves that seat empty. The dialog is plain.

The Rust client loads the same two files on the first open. The edge is what the icon decoder takes. The application mark is drawn first. The brand mark is drawn beside it. The frame path still asks for 64.

There is no new package, no new public type, and no new public method. The marks are seated in private on each client. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, no settings-file edit, and no dependency-manifest edit. Nothing beside this box moves. The send set stays as it is. Both clients carry this. This row is not a separate feature. The transfer recorded beside the menu word stays closed.

**Truth.** The About box title is `About Aetheris`. Under the lines the shell already shows, it names Clay Lambert, then `Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>`, then `Licensed under the MIT License`. The Eye of Horus from `/icons/aetheris.jpg` is drawn at 128 pixels, and the casserole from `/icons/casserole.jpg` is drawn beside it at 96 pixels. The application icon is the larger mark. The frame icon stays the Eye of Horus. Java puts both marks on the north and the words under them, leaves a missing file's seat empty, and keeps the dialog plain. Rust loads both files on the first open, draws the application mark first, draws the brand beside it, and still asks the frame path for 64. `Help...` and `About Aetheris...` stay under Help. Both clients carry this.

**Reason.** The About box has to name the author, the copyright, the email, and the MIT license, with the application icon large and the branded icon beside it.

**Avoidances.** Do not change the three lines or their order. Do not put those lines above the lines the shell already shows. Do not swap the sizes: the Eye of Horus is 128 and the casserole is 96. Do not change the frame icon, and do not stop the Rust frame path from asking for 64. Do not fill a seat whose file is missing. Do not make the Java dialog anything but plain. Do not move `Help...` or `About Aetheris...`. Do not change the menu order. Do not reopen the conversation transfer. Do not add a package, a public type, a public method, a global key, a vault column, a channel, a wire message type, a color key, or a library. Do not edit a settings file or a dependency manifest.


**semantic-key:** PK-0040 PK-0041

## The mark hover

The four state marks turn emerald while the pointer is on them, and each letter returns to the color it already had when the pointer leaves.

The letters stay I, E, P, and O. The tips stay Included, Excluded, Input only, and Output only. The rest color is left alone, including when the mark is already selected: a selected mark still takes the emerald for as long as the pointer is on it. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. Tabs stay. Menu titles stay. A check box never takes this hover.

The Swing client hangs the same listener on those four toggles and does not hang it on a check box. The Rust client paints the same four marks with the same emerald. The Rust helper is `mark_button`. Both clients change.

**Truth.** I, E, P, and O turn emerald on hover and restore their previous color on exit. Selection does not block that hover. Tips, letters, the pin, the 11 pixel divider, tabs, menus, and check boxes stay as they are.

**Reason.** The pointer could rest on a state letter and the letter gave no answer. The hover the rest of the chrome already uses is emerald, so these four marks use that same answer.

**Avoidances.** Do not retint the rest color. Do not give the hover to a check box. Do not change the letters, the four tips, the pin letter P, the 11 pixel divider, the arrows, the tooltip wait, the tabs, or the menu titles. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-0042 PK-0043 PK-0044 PK-0045

## The black ground

The About box drops the casserole picture. The eye is half the previous size, the art stays, and the ground is black with square corners.

The About edge is 64. Light, dull corner pixels flood to black. Saturated eye pixels stay. The corners stay square. The jpg stays. The window icon stays.

The Swing helpers are `iconGround` and `fillIconGround`. The Rust helpers are `icon_ground`, `fill_icon_ground`, and `about_app_rgba`. Both clients change.

**Truth.** About shows no casserole picture. The eye is drawn at half size on a black ground with square corners. The edge is 64. Dull light corners become black. Saturated eye pixels, the jpg, and the window icon stay.

**Reason.** The About art was sitting on a light rounded ground with a second picture beside the eye, so the mark did not read as one black square.

**Avoidances.** Do not round the corners. Do not replace or redraw the eye art. Do not darken saturated eye pixels. Do not drop the jpg or the window icon. Do not use an edge other than 64. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-0049 PK-0050

## The about layer

While the About box is active it paints above every other component.

The Swing client paints the About dialog always on top and modal, and brings it forward when the dialog opens. The Rust client paints the About window on the foreground layer and moves that layer to the top while the box is open. The Help window stays. The eye stays.

Both clients change.

**Truth.** An open About box is the topmost window: modal and always on top in Swing, and the foreground layer moved to the top in Rust. Help and the eye stay.

**Reason.** About could open underneath another component, so the box was active and not visible.

**Avoidances.** Do not leave About under another window while it is open. Do not change the Help window. Do not change the eye. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-0053 PK-0054 PK-0055 PK-0056

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

# Ctrl-Shift-C copies the highlight and writes nothing

Ctrl-Shift-C copies the console highlight to the clipboard and the console writes nothing on that chord. Ctrl-C stays the interrupt, byte 0x03.


**semantic-key:** PK-4437

# Product tab

The door keeps one word, `ui.product.tab`. The words are `ssh` and `aetheris`. A missing word opens on SSH. A saved Aetheris word waits for Connect, then takes the plate and lands on the newest card. A close stores the plate that is showing.


**semantic-key:** PK-4442 PK-4443 PK-4444 PK-4445 PK-4446
