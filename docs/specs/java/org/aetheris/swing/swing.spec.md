# org.aetheris.swing

Component file. It carries how this package is built.

**Depends:** [java/org/aetheris/config/config.spec.md](../config/config.spec.md), [java/org/aetheris/vault/vault.spec.md](../vault/vault.spec.md), [java/org/aetheris/wire/wire.spec.md](../wire/wire.spec.md), [java/org/casserole/app/ui/ssh/ssh.spec.md](../../casserole/app/ui/ssh/ssh.spec.md), [java/org/casserole/chat/chat.spec.md](../../casserole/chat/chat.spec.md), [java/org/casserole/edit/edit.spec.md](../../casserole/edit/edit.spec.md), [java/org/casserole/mood/mood.spec.md](../../casserole/mood/mood.spec.md), [java/org/roux/model/model.spec.md](../../roux/model/model.spec.md), [java/org/roux/roux.spec.md](../../roux/roux.spec.md), [java/org/roux/session/session.spec.md](../../roux/session/session.spec.md), [java/org/roux/token/token.spec.md](../../roux/token/token.spec.md), [java/org/roux/toon/toon.spec.md](../../roux/toon/toon.spec.md)

## Pin face

P is green while the pin is on and sapphire while the pin is off. C stays sapphire.

Green is `#3cb371`. Sapphire is `#2e86ff`. The letter stays white. The one-pixel white bevel stays. C stays sapphire. The pin box stays the only switch. Java keeps P on the east of the canvas tab. The thinking-pane C stays sapphire. The Rust client paints the same surface in this same course. Nothing beside this surface moves. The cells already on the canvas stay as they are.

The pin seat is already recorded for the Rust client. This course carries the painted surface onto that client. Use confirmed the surface.

**Truth.** P is `#3cb371` while the pin is on and `#2e86ff` while the pin is off. The letter stays white and the one-pixel white bevel stays. C stays `#2e86ff`. Java keeps P on the east of the canvas tab. Rust paints the same green and the same sapphire. The pin box remains the only switch.

**Reason.** P has to show green while the pin is on and sapphire while the pin is off, so the folded mark reports the pin state. C stays sapphire.

**Avoidances.** Do not paint C green. Do not add a second switch beside the pin box. Do not move Java's P off the east of the canvas tab. Do not drop the white letter or the one-pixel white bevel. Do not leave the Rust surface behind this course.


**semantic-key:** PK-0003 PK-0004

# The heatbar uses the selected cell

The stamp names the build. The heatbar uses the model of the selected canvas cell. The selected cell is the seat. Java moves. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0220 PK-0221 PK-0222 PK-0223 PK-0224

# The thinking pane stays on the tail

The stamp names the build. The thinking pane sits on the bottom of its buffer before a new block is painted. Java moves. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0234 PK-0235 PK-0236 PK-0237 PK-0238 PK-0239 PK-0240 PK-0241 PK-0242

# The heatbar uses the selected cell

The heatbar uses the model of the selected canvas cell. Token Calc already opens on that row. The heatbar was using the first known window. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-0339 PK-0340 PK-0341 PK-0342 PK-0343

# The thinking pane stays on the tail

The thinking pane sits on the bottom of its buffer before a new block is painted. This page does not move that point. The formal plan is the page that names the build.


**semantic-key:** PK-0353 PK-0354 PK-0355 PK-0356 PK-0357 PK-0358 PK-0359 PK-0360 PK-0361

# Duplicate group shows only on a grouped cell

On a right click on a cell, the option to duplicate a group shows only when the cell is a group or merge cell group. Any cell in the group can activate it.


**semantic-key:** PK-0375 PK-0376 PK-0377 PK-0378 PK-0379 PK-0380 PK-0381 PK-0382 PK-0383 PK-0384

# Duplicate group shows only on a grouped cell

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.


**semantic-key:** PK-0417 PK-0418 PK-0419 PK-0420 PK-0421 PK-0422 PK-0423 PK-0424 PK-0425 PK-0426

# Eight code packages are built from this plan, the seed-kit note is not in the build, and the next stage stays closed.

This plan is the build contract for eight packages on the notebook and the shell chrome around it. Approval names the build of those eight. The held note about the starter kit is not in the build. Prior tweak plans stay frozen and are not edited. The later-stage account already carries these notes. Do not open the next stage and do not rewrite that account's item table.

The sentence that binds the plan is this. The notebook already stands. Eight code packages. One pipe. Four context states. The next stage stays closed. The frozen starter kit stays frozen.

No Lombok. No lambdas in project types. Anonymous classes only. `// @aa-sdd` lands on the one new type, `org.aetheris.swing.Incrementor`. The class stays `org.casserole.app.ui.ssh.SshClientApp`. Shell launch stays `java -cp` plus the local Maven classpath helper. Never `exec:java`. Do not edit `src/main/resources/application.yaml`. Do not cut a starter kit.

The filed earlier builds stay. This plan does not reopen them. The Run land from the previous plan, which scrolled the user area, is the path this plan's end-of-pane package replaces.

The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key and no password are added.

**Truth.** Eight packages ship from this plan. The starter-kit note is a record only. One new type is the incrementor.

**Reason.** The operator approved the notes and named the build in the same acceptance, so the plan both stamps and orders the Java.

**Avoidances.** Do not cut a starter kit. Do not edit the host `application.yaml`. Do not edit the frozen plans. Do not open the next stage. Do not add a second trace-comment type.


**semantic-key:** PK-0458

## Mood wire

Each mood send takes a new wire id, so a typed line on the mood glass is not answered with the yellow slide `keyword only. cell-busy`.

The mood glass used one wire id for the whole chat. The server holds that id until the reply has been written. The next mood frame, including the ask for a missing mood line, was reusing that same id. The server answered `cell-busy`. The glass treated that answer as a miss, closed, and the yellow slide said `keyword only. cell-busy`.

Each mood send now takes a new wire id. The id is registered before the frame is written. A late frame for an older id stays off the cell. The reply for the send just made still fills the glass. A canvas cell that is running still answers `cell-busy`. The mood glass does not reuse the id of the turn that is releasing.

The Java client carries this behavior. The Rust client is not changed on this row. Its matching behavior waits until that catch-up is named.

The mood layer that this send belongs to holds together: the glass, the switch, the saved string, the shape line, the rising pane, the kept size, and a mood turn's own wire id. Those Java rows stay as they landed. The Rust client's matching shell is the named catch-up. It does not reopen these rows.

**Truth.** Every mood send registers a fresh wire id before its frame is written. A late frame for an older id does not land on the cell. The reply for the send just made still fills the glass. A running canvas cell still answers `cell-busy`. The mood glass does not reuse the id of the turn that is releasing, and it does not close itself on that answer for its own next frame.

**Reason.** A typed request on the mood glass was closing and showing the yellow slide `keyword only. cell-busy`, because the whole mood chat shared one wire id that the server still held. Each mood send needs its own id. A canvas cell that is actually running still has to answer `cell-busy`.

**Avoidances.** Do not keep one wire id for the whole mood chat. Do not apply a late frame for an older id to the cell. Do not drop the reply that belongs to the send just made. Do not clear `cell-busy` for a canvas cell that is running. Do not reuse the id of the turn that is releasing. Do not change the Rust client on this row. Do not reopen the Java mood rows when the Rust shell catch-up is named.


**semantic-key:** PK-0463

## One transfer pair for a merge group

A transfer from the canvas to the Roux session makes a merge group one input message and one reply message. The input joins the member prompts. The reply is the stored merge text. JOIN lines stay on that reply.

transferRows emits one pair at the first card of the group. transfer_inputs joins the member prompts. No new public type, no new public method, and no new test class are added. Both clients carry this. Nothing else on this plate moves. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** Transferring a merge group into the Roux session writes one input and one reply. The input is the member prompts joined. The reply is the stored merge text, and the JOIN lines stay on that reply. The pair is emitted at the first card of the group.

**Reason.** A merge is one turn. Sending each member as its own Roux message split a single join into several sessions. One pair keeps the member prompts as the input and the stored merge text, including its JOIN lines, as the reply.

**Avoidances.** JOIN lines are not stripped from the reply. The pair is not emitted once per member. The doors do not change. The known paste-undo gap stays.


**semantic-key:** PK-0736 PK-0737 PK-0738 PK-0739 PK-0740 PK-0741 PK-0742

## VT100 top

The VT100 thinking pane sits on the top and slides down. Its bottom edge meets the top of the terminal. Drag that edge to set the height. The height and the closed state are stored apart from the notebook strip. The text stays off the vault log.

The VT100 strip sits on the north of the terminal and grows downward. The bottom edge of the strip meets the top of the terminal. Drag that edge to set the height. Dragging down grows the pane. The notebook strip stays on the bottom. Its grip stays on the north edge, and dragging up still grows that pane. The VT100 height and the closed flag are stored in `~/.casserole/aetheris-vt100-think.yaml`. Every VT100 strip shares that file. The thought text is not written there. The notebook file stays `aetheris-think.yaml` and the notebook log stays `aetheris-thinking.txt`. Close still hides the pane. A closed pane stays closed when the application opens again. `v` still folds the text for the session. Clear still wipes the text and leaves the pane up. A pool thought still opens the strip when console pool thinking is on. The assistant reply still paints on the VT100. Ollama and Grok on the console stay as they are.

The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this strip waits until it is named. Nothing beside this placement moves. The cells already on the canvas stay as they are. Use confirmed the top strip.

**Truth.** The VT100 strip grows down from the north. Its bottom edge meets the terminal, and dragging that edge down sets a taller pane. The notebook strip stays on the bottom, and dragging its north grip up still grows it. Height and the closed flag for every VT100 strip live in `~/.casserole/aetheris-vt100-think.yaml`, with no thought text in that file. The notebook file stays `aetheris-think.yaml` and the notebook log stays `aetheris-thinking.txt`. Close hides the pane and the closed state survives the next open. `v` folds for the session only. Clear wipes the text and leaves the pane up.

**Reason.** The VT100 thinking pane has to sit on top, slide down, and take its height from a drag edge against the terminal. That height and the closed state have to be its own, and the text has to stay out of the vault log.

**Avoidances.** Do not seat the VT100 strip on the bottom. Do not move the notebook strip off the bottom or reverse its grip. Do not store VT100 chrome in the notebook file. Do not write the thought text into `aetheris-vt100-think.yaml` or into the vault log. Do not give each VT100 strip a separate file. Do not drop the closed-on-next-open rule, the session fold, or Clear leaving the pane up. Do not change Ollama or Grok on the console. Do not write a Rust crate on this row.


**semantic-key:** PK-3601 PK-3602 PK-3603 PK-3604 PK-3605

## The rust marks

The P and C buttons match the Rust color and style on the canvas and on the VT100 tab.

P is sapphire while the pin is off and green while the pin is on. C is sapphire. Both wear a one-pixel white stroke and a corner radius of 2. The letter stays white. P is at least 22 by 22. C is at least 22 by 16. The hover emerald stays. T stays. The cell-state P stays.

The private painter is `paintRustPin`. `rustMarkEdge`, `seatRustBox`, and `ThinkStrip.paintRustClear` stay package-private. `paintMarkChoice` stays. The Swing client changes. The Rust client stays.

**Truth.** P and C use the Rust mark: sapphire, or green for P while the pin is on, a one-pixel white stroke, corner radius 2, and a white letter. P is at least 22 by 22. C is at least 22 by 16. The same face is on the canvas and on the VT100 tab.

**Reason.** The Swing P and C were a different face from the Rust marks, so the pin and the clear did not read as the same controls across the two clients.

**Avoidances.** Do not restyle T. Do not restyle the cell-state P. Do not drop the hover emerald. Do not shrink P under 22 by 22 or C under 22 by 16. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-3669 PK-3670 PK-3671

## The visible letters

P, C, and T show their letters at the Rust pin size.

The letter is painted after the fill. The pad is 8 by 2, applied on both sides, plus a further 8 by 4, so the box adds 24 by 8. The floor is 22 by 22. The application point size stays. P is green while the pin is on and sapphire while it is off. C and T stay sapphire. The stroke is one white pixel and the corner radius is 2. The cell-state letters I, E, P, and O stay.

The private class is `RustLetterFace`. The private painter is `paintRustToken`. `seatRustLetter` stays package-private. The Swing client changes. The Rust client stays.

**Truth.** P, C, and T paint the letter after the fill, in a box that adds 24 by 8 from that pad and is at least 22 by 22. P is green when the pin is on and sapphire when it is off. C and T stay sapphire. The stroke is one white pixel. The corner radius is 2. The application point size stays.

**Reason.** The letters were being clipped inside a box that was sized before the glyph, so P, C, and T could show a face with no readable letter.

**Avoidances.** Do not paint the letter under the fill. Do not restyle the cell-state I, E, P, and O. Do not shrink the floor under 22 by 22. Do not change the application point size. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-3681 PK-3682 PK-3683

# Aetheris canvas

This page builds the Java notebook. The map is [program-spine.md](../config/config.spec.md).


**semantic-key:** PK-3808 PK-3809 PK-3810 PK-3811 PK-3812 PK-3813 PK-3814 PK-3815 PK-3816 PK-3817 PK-3818

## A readable card splits its header so the target and the group controls stay in view.

A grouped member card is 480 pixels wide. The header was one flow-layout line. That line wraps, and the card keeps a one-row height, so the target field, Group, Join, and Ungroup were clipped.

The header is a vertical split pane.

The top pane holds the turn label, the state buttons `I` `E` `P` `O`, collapse, burner, and View. The bottom pane holds the execution target, Group, Join, and Ungroup. The bottom pane is collapsed by default. The card height is the top row plus the divider.

The divider is one-touch. Opening it grows the card by the bottom row so those controls stay in view. Collapsing it returns the card to the top row plus the divider. Dragging the divider so the bottom row has room keeps the pane open. Dragging it shut, or the one-touch that hides the bottom, collapses it. The one-touch that would hide the top row is treated as open, and the divider snaps so both rows stay visible.

The open or collapsed choice is not stored on the cell. A card rebuild starts collapsed. A readable card uses this split, grouped or not. Group is on the lower pane, so a sequential cell can still start a group. An unreadable card stays the short load-error header. It has no split.

The check did not click through the window by hand.

**Truth.** A readable card, grouped or not, uses a vertical header split. The lower pane starts collapsed, the divider is one-touch, and a rebuild starts collapsed. An unreadable card keeps the short load-error header and has no split.

**Reason.** The single header line wrapped inside a one-row card, so the target field, Group, Join, and Ungroup were clipped on a 480-pixel grouped member.

**Avoidances.** Do not store the open or collapsed choice on the cell. Do not let the one-touch hide the top row. Do not put the split on an unreadable card. Do not treat a hand click as the proof.


**semantic-key:** PK-3819

## The group controls live on a second bar that a `v` opens, and the header split is gone.

This rule replaces the header split. The canvas height was holding that split, so the lower buttons stayed clipped.

The main bar keeps the turn, the state buttons, collapse, burner, and View, and it gains a `v` toggle. The `v` is the down indicator. The second bar is hidden.

A click opens the second bar under the main bar and the mark becomes `^`. The card grows by that bar. The group member row's horizontal scroller keeps a vertical minimum equal to the row, so the canvas column grows and its own scroll can bring the bar into view. A second click hides the bar, the mark returns to `v`, and the card shrinks.

The second bar holds the execution target, Group, Join, and Ungroup. The choice is not stored on the cell. A card rebuild starts closed. An unreadable card has no toggle.

The check did not click through the window by hand.

**Truth.** The header split is gone. A `v` on the main bar opens the execution target, Group, Join, and Ungroup on a second bar and the mark becomes `^`. A second click closes it. A rebuild starts closed.

**Reason.** The split stayed clipped because the canvas height held the lower pane. The second bar grows the card, and the member row's vertical minimum lets the canvas scroll bring that bar into view.

**Avoidances.** Do not keep the header split. Do not store the open choice on the cell. Do not put a toggle on an unreadable card. Do not treat a hand click as the proof.


**semantic-key:** PK-3820 PK-3821 PK-3822 PK-3823 PK-3824

## A grouped member is as wide as its main bar, plus the card chrome, plus 10 pixels.

A grouped member was preferring 400 pixels and capping at 480. The main bar is wider than the width the card was given, so the `v` sat past the east edge.

A grouped member is the width of its main bar, plus the card chrome, plus 10 pixels. That leaves at least 10 pixels between the `v` and the card's east edge. The card does not shrink below that width. A font change measures the bar again. A sequential card and the merge sink stay on the column width. An unreadable card stays the short load-error header.

A layout probe laid the real cards out in a 1280 by 800 window. The east gap was 23 pixels at font 8, font 19, and font 32. Font 19 is the control-panel size. Those member widths were 452 and 476. Font 32 grew to 580 and 621, past the old 480 cap, and the gap stayed 23. The window was not opened by hand.

**Truth.** A grouped member's width is the main bar plus the card chrome plus 10 pixels, and it does not shrink below that. The measured east gap was 23 pixels at font 8, font 19, and font 32. Font 19 widths were 452 and 476. Font 32 widths were 580 and 621.

**Reason.** The old 400-pixel preference and 480-pixel cap left the `v` past the east edge. The extra 10 pixels keep that control inside the card, and a font change measures the bar again so the gap survives a larger face.

**Avoidances.** Do not cap a grouped member at 480. Do not shrink it below the main bar plus chrome plus 10 pixels. Do not put a sequential card or the merge sink on that member width. Do not change the unreadable card off the short load-error header.


**semantic-key:** PK-3825 PK-3826

## The footer tool bar starts hidden behind a `v`, and the vault line stays only on the status line.

The original footer bar holds Run, Stop, New turn, Clear turns, Preview, and the row count. The tool bar holds search, Search, Export, Import, Pull, and To terminal.

The original footer gains a `v` toggle. The tool bar starts hidden. A click opens the tool bar under the original footer and the mark becomes `^`. A second click hides the tool bar and the mark returns to `v`. The choice is not stored.

The vault line was painted on the original footer and on the thin status line. It stays on the status line. The original footer no longer carries it. A card click writes that line to the status line.

A layout probe at font 19 found the tool bar closed, then open. The footer grew from 107 to 149. The vault line reached the status host. No label on the panel carried it. The window was not opened by hand.

**Truth.** The tool bar starts hidden. Its `v` opens search, Search, Export, Import, Pull, and To terminal, and the mark becomes `^`. The vault line is only on the status line. At font 19 the footer grew from 107 to 149 when the tool bar opened.

**Reason.** The tool actions are a second row so the original footer can stay the run controls. The vault line was painted twice, so the footer copy leaves and a card click writes the line only to the status host.

**Avoidances.** Do not store the open choice. Do not paint the vault line on the original footer. Do not leave a panel label carrying that line.


**semantic-key:** PK-3827 PK-3828 PK-3829 PK-3830

## Every button on the notebook carries a hover tip that says what the control is and what the click does.

The footer buttons are Run, Stop, New turn, Clear turns, Preview, the row-count minus and plus, and the tool-bar `v`. The tool bar is Search, Export, Import, Pull, and To terminal. Each turn has `I`, `E`, `P`, `O`, collapse, burner, View, the group-bar `v`, Group, Join, and Ungroup. A turn that did not load keeps the `?` mark and the tip says why. The inspector has soft wrap, its minus and plus, the line-length minus and plus, pin, and OK. The canvas split and the prompt and reply split each have two arrows. The reply window has Close. The mood dialog has Send, Approve, and Cancel. The card menu Import carries a tip. The connected-user pull-down names the selected user and says that a choice switches the vault on screen. The fields that already had a short tip now use the same kind of sentence.

A `v` tip changes with the click. Closed says show. Open says hide. The choice is still not stored.

A layout probe at font 19 walked the built notebook. 37 buttons carried a tip. The tool-bar `v` and the group-bar `v` flipped from show to hide and back. The window was not opened by hand.

**Truth.** Every listed notebook button carries a tip of what it is and what the click does. A closed `v` says show and an open `v` says hide. At font 19, 37 buttons carried a tip, and both `v` controls flipped both ways.

**Reason.** A control without a sentence leaves the click unnamed. The `v` tip changes with the mark so show and hide stay the same control. The connected-user pull-down names the selected user because a choice switches the vault on screen. A turn that did not load keeps `?` so the tip can say why.

**Avoidances.** Do not leave a listed button without that sentence. Do not store the `v` choice. Do not keep a short tip on a field that now uses this sentence. Do not treat a hand-opened window as the proof.


**semantic-key:** PK-3831 PK-3832 PK-3833 PK-3834 PK-3835

## A collapsed card keeps one reply line on the button row, to the right of the buttons.

A collapsed card kept a one-line preview under the button bar. That line now sits on the same row, to the right of the buttons. The card height is that row. Opening the card puts the prompt and the reply back under the buttons, at the saved row count. The line leaves the button row.

The line is the start of the reply. It is one line. Ten pixels of padding stay on its east side. A grouped member that is collapsed grows by that line, so the text has room east of the `v`. An open member stays the width of its main bar plus 10 pixels. The group bar still opens under the row. The choice to collapse stays on the cell. The group-bar choice stays off the cell.

A layout probe at font 19 in a 1280 by 800 window measured a collapsed sequential card at 48 pixels tall. The open card beside it was 284. The line shared the button row and sat east of the buttons. Opening the group bar grew that card from 48 to 89, and the line stayed on the button row. Opening the collapsed card put the body under the buttons. A collapsed member was 354 pixels wider than an open member. The open member's east gap was 27. The window was not opened by hand.

**Truth.** A collapsed card is one row: the buttons, then the start of the reply, with 10 pixels of east padding. At font 19 in a 1280 by 800 window a collapsed sequential card was 48 pixels tall, the open card beside it was 284, the group bar grew the collapsed card from 48 to 89, a collapsed member was 354 pixels wider than an open member, and the open member's east gap was 27.

**Reason.** The preview used to sit under the button bar and added a second row to a card that should be one row. A collapsed member grows by that line so the text has room east of the `v`. An open member stays the main-bar width plus 10 pixels.

**Avoidances.** Do not leave the preview under the buttons. Do not drop the 10-pixel east pad. Do not store the group-bar choice on the cell. Do not take the collapse choice off the cell. Do not change the saved row count when the card opens.


**semantic-key:** PK-3836 PK-3837 PK-3838 PK-3839 PK-3840 PK-3841 PK-3842

## Collapse All folds every turn on the showing canvas, and Expand All opens them again at the saved row count.

The original footer gains Collapse All and Expand All. They sit as their own group, after a visible divider, to the right of the buttons that were already there. Collapse All folds every turn on the canvas that is showing. Each card keeps the short line of the reply to the right of its buttons. Expand All opens every turn on that canvas. The prompt and the reply show under the buttons, at the saved row count. The choice is saved on each turn. Other canvases stay as they are.

Each new button carries a tip that says what it is and what the click does.

When a button is added, the same turn gives it a tooltip. The tip says what the control is and what the click does. A look-and-feel button is not this duty. A button already on screen before this duty is not reopened by it. The check against the existing rules found no second reading and added no mark.

A layout probe at font 19 in a 1280 by 800 window found Collapse All and Expand All on the footer row. The divider sat between the tool-bar `v` and Collapse All. The divider was 9 by 22. Collapse All folded the three turns on the open canvas. The other canvas stayed open. Expand All opened those three turns again. The other canvas stayed open. The window was not opened by hand. The probe showed a frame and disposed it.

**Truth.** Collapse All and Expand All sit in their own footer group after a divider that measured 9 by 22. They change only the canvas that is showing, and each turn stores its own collapsed or open choice. A new button receives its tip in the same turn.

**Reason.** The fold belongs on the footer so one click can fold or open every turn in view without touching another canvas. The short reply line stays to the right of the buttons so a folded card remains one row. The tip duty rides the same turn so a new button is not added unnamed. A look-and-feel button is not a button this surface adds, and a button already on screen is not reopened.

**Avoidances.** Do not fold or open a canvas that is not showing. Do not drop the saved row count on expand. Do not treat a look-and-feel button as this tip duty. Do not reopen a button that was already on screen. Do not leave the probe frame up.


**semantic-key:** PK-3843 PK-3844 PK-3845 PK-3846 PK-3847 PK-3848

## The tool-bar `v` sits past a second divider, after Delete, Collapse All, and Expand All.

The original footer keeps Delete on the near side of the first divider, then Collapse All and Expand All. A second divider sits to the right of those two buttons. The tool-bar `v` sits on the far side of that divider. The bar still starts hidden. A click still opens it and the mark becomes `^`. A second click hides it. The choice is still not saved.

A layout probe at font 19 in a 1280 by 800 window found the `v` at the end of the row. Delete sat just before the first divider. Collapse All and Expand All sat between the two dividers. Each divider was 9 by 22. The `v` opened the tool bar and closed it again. The window was not opened by hand. The probe showed a frame and disposed it.

**Truth.** Footer order is Delete, a 9 by 22 divider, Collapse All, Expand All, a second 9 by 22 divider, then the tool-bar `v`. The tool bar still starts hidden, and the choice is not saved.

**Reason.** The fold buttons are their own group. The tool toggle moves to the far side of a second divider so it is not read as part of that group.

**Avoidances.** Do not put the tool-bar `v` before the fold buttons. Do not save the open choice. Do not start the tool bar open. Do not leave the probe frame up.


**semantic-key:** PK-3867 PK-3868 PK-3869 PK-3870 PK-3871 PK-3872 PK-3873

## A group is only as tall as its member cards, the merge sits under that row, and arming the merge paints the whole run sapphire.

When a group is established, the member row was taking the spare height of the canvas. The members sat at the north of that space and the merge cell sat at the south. The member row is now as tall as its cards. The merge cell sits under that row. A sideways bar keeps its own height only when the cards are wider than the row. Join moves the added turn into that row, in canvas order, and the merge stays at the end of the run. Ungroup keeps the turns that remain together the same way.

Selecting the merge cell paints every card in that group sapphire, RGB 46, 134, 255. That is the run point. Run sends each member and does not send the merge. Selecting one member, or a turn outside the group, lights only that card.

A layout probe at font 19 in a 1280 by 800 window found a gap of 0 between the member row and the merge. Joining a later turn put both members in the row and left the other turn outside the block. The gap stayed 0. Arming the merge lit 3 cards. Arming the turn outside the group lit 1 card. Run asked for an AMWP session, which is the send path with no session open. Opening the group bar grew the row from 292 to 330 and the gap stayed 0. The window was not opened by hand. The probe showed a frame and disposed it.

**Truth.** The member row is as tall as its cards, the merge sits under it with a gap of 0, and arming the merge paints every card in the group RGB 46, 134, 255. Run sends each member and does not send the merge. Opening the group bar grew the row from 292 to 330 and the gap stayed 0.

**Reason.** The spare canvas height left the members at the north and the merge at the south. Snapping the row to the cards puts the merge at the end of the run. The sapphire run point marks the whole group when the merge is armed, while a single member or an outside turn lights only that card.

**Avoidances.** Do not give the member row the spare canvas height. Do not send the merge. Do not light the whole group when one member, or a turn outside the group, is selected. Do not cover the cards with the sideways bar. Do not leave the probe frame up.


**semantic-key:** PK-3874 PK-3875 PK-3876 PK-3877 PK-3878 PK-3879 PK-3880 PK-3881 PK-3882 PK-3883

## The merge cell shows only the reply, and the inspector lists what Run would send for the selected turn.

A merge cell shows only the reply box. The prompt box is hidden. View still opens the stored reply. Collapse still keeps the one-line reply. Import into the merge cell is refused while the prompt box is hidden. Ungroup brings the prompt box back on that cell.

Selecting a turn rebuilds the inspector for that turn. The list is what Run would send for that turn: the host line, the mood line, and the earlier turns that call keeps. A turn below the selection stays out of the list.

When the selected turn is the merge cell, the inspector lists the member prompts joined as one user line, and the merge reply as the assistant line. Turns outside that group stay out of that list. Run still sends each member. The merge is still not sent.

A layout probe at font 19 in a 1280 by 800 window selected the top turn of three. The inspector held that turn's prompt and left out the two turns below it. The merge card had no prompt box and no split. Its member still had a prompt box. View was on the merge card. Selecting the merge listed the member prompt and the merge reply, and left out the turns outside the group. Selecting the turn under the merge left out the turn below that one. Ungroup put the prompt box back on the merge cell. The window was not opened by hand. The probe showed a frame and disposed it. View was not opened.

**Truth.** A merge cell hides the prompt box and the split, keeps View and the one-line collapse, and refuses Import until Ungroup restores the prompt box. The inspector is the selected turn's send list, and a merge selection shows the joined member prompts plus the merge reply.

**Reason.** The merge is a reply sink, so the prompt box is hidden and Import has nowhere to land. The inspector is rebuilt on selection so it shows the host line, the mood line, and the earlier turns that call keeps, and it leaves out every turn below the selection. The merge list is the joined view, not the member-by-member Run.

**Avoidances.** Do not show a prompt box on the merge while it is a merge. Do not accept Import into that hidden prompt. Do not send the merge. Do not put turns outside the group into the merge inspector list. Do not put a turn below the selection into the list. Do not open View as part of this proof.


**semantic-key:** PK-3884 PK-3885 PK-3886

## A group above the selected turn goes out as one turn: member prompts joined by a newline, and the merge reply with the JOIN lines removed.

When Run builds the list for a turn, a group that sits above that turn goes out as one turn. The member prompts are one user message, joined by a newline, in canvas order. The assistant message is the merge reply with the JOIN lines removed. A line that is JOIN plus that member's target is the tag. `ollama` is the tag when the target is empty. The reply text stays. The card still stores the JOIN lines. The merge cell's state still decides whether that reply goes. Included and output-only send it.

Run on the group still sends each member. The merge is still not sent. A member's own call still leaves its siblings and the merge out of that call.

A probe grouped two turns under an earlier turn and added a later turn. The later turn's working set held the earlier turn, then one user message of both prompts, then the merge reply without the JOIN lines, then the later prompt. The replies stored on the member cards were left out. One member's own working set held the earlier turn and that member's prompt. The inspector for the later turn showed both prompts and the stripped reply, and it did not show the JOIN lines. The window was not opened by hand. The probe showed a frame and disposed it.

**Truth.** Above a later turn, a group is one user message of the member prompts in canvas order and one assistant message of the merge reply with JOIN lines removed. Included and output-only still decide whether that reply goes. Run on the group still sends each member and does not send the merge.

**Reason.** A later call should see the group as one turn, not as every member reply. The JOIN line is a tag, JOIN plus that member's target, or `ollama` when the target is empty, and it is storage on the card rather than text in the assistant message. A member's own call leaves siblings and the merge out so that call stays that member.

**Avoidances.** Do not send the stored member replies in place of the stripped merge reply. Do not strip the JOIN lines out of the card's stored reply. Do not send the merge on a group Run. Do not include siblings or the merge in a member's own call. Do not show the JOIN lines in the later turn's inspector.


**semantic-key:** PK-3887 PK-3888

## The group block uses the same center alignment as a sequential card, so it keeps the column width.

A fresh canvas with a two-cell group looked full width. Adding a turn under that group parked the group on the right at half the column. A sequential card is centered in the vertical box. The group block was left-aligned. Those two alignments split the column, and the left-aligned block received the right-hand half. The group block now uses the same center alignment as a sequential card. The member row and the merge still fill that block. A member card keeps its own width inside the row.

A layout probe at font 19 in a 1280 by 800 window grouped two turns and added a later turn. The group block and the later card were both 892 wide and both started at the same x. The merge filled the block. A box measurement of the old alignment put the group at x 631 with width 632 beside a full-width card. The same measurement with one shared alignment gave both the full width. The window was not opened by hand. The probe showed a frame and disposed it.

**Truth.** The group block is center-aligned like a sequential card. In a 1280 by 800 window at font 19, the group block and the later card were both 892 wide and started at the same x. The old left alignment measured the group at x 631 with width 632.

**Reason.** A left-aligned group beside a centered card split the column, so the group received the right-hand half. One shared center alignment gives both the full width. Members still keep their own width inside the row, and the merge still fills the block.

**Avoidances.** Do not left-align the group block. Do not let a later turn shrink the group to half the column. Do not change a member card off its own width inside the row.


**semantic-key:** PK-3889

## The member row scrolls sideways inside the canvas width, and the canvas sideways bar stays off.

A group snapped to the merge had no sideways bar when a third cell joined the row. The member scroller was publishing the sum of the member cards as its preferred width. That sum became the column width, so the group's own bar stayed off and a canvas bar would slide the merge with the members.

The member scroller no longer publishes that sum. The column takes the canvas width. The merge and a later turn stay that width. A member card keeps its own width. When the cards are wider than the row, the group's sideways bar turns on and keeps its own height, so it does not cover the cards. Two members that fit show no bar. The canvas sideways bar stays off.

A layout probe at font 19 in a 1280 by 800 window used the saved canvas divider. Two members sat in a row 919 wide and the group's bar was off. Joining a third member and adding a later turn brought the canvas vertical bar in, and the column followed that width. The group block and the later card were both 902 wide and both started at the same x. The merge filled the block. Each member stayed 446 wide. The third member started at x 892 in a row 902 wide, so it ran past the row. The group's bar was 17 pixels tall and the member card fit above it. The canvas sideways bar stayed off. The window was not opened by hand. The probe showed a frame and disposed it.

**Truth.** The member scroller does not publish the sum of the cards. The column, the merge, and a later turn take the canvas width. When the cards are wider than the row, the group's own bar is 17 pixels tall in the measured case and the card fits above it. Two members in a 919-wide row showed no bar. Three members left each card 446 wide, with the third starting at x 892 in a row 902 wide. The canvas sideways bar stayed off.

**Reason.** Publishing the sum made that sum the column width, so the group's bar never turned on and a canvas bar would have moved the merge with the members. The group's own bar keeps its own height so it does not cover the cards.

**Avoidances.** Do not publish the sum of the member cards as the preferred width. Do not slide the merge with a canvas sideways bar. Do not cover the cards with the group's bar. Do not change a member off its own width.


**semantic-key:** PK-3890 PK-3891 PK-3892 PK-3893 PK-3894 PK-3895 PK-3896

## A named error stays on the status line, and any other error slides a notice down from the top of the pane.

An error that already has its own words stays on the status line. Those words are `air-gap`, `engine-down`, `too-large`, `burner denied`, `pipe`, `grok key missing`, `unknown provider`, `unknown role`, and `no tool is invocable`. `cell-busy` stays a status-line notice and does not fail the cell.

Any other error opens a notice at the top of the pane. It slides down. The border is red. The title is Error. The body is a text area of three rows, wrapping, and not editable. The text is the gathered message. A model-service HTTP miss sends `Grok HTTP` plus the status plus at most 180 characters of the body, with the key text replaced by `[redacted]`. A transport miss sends `Grok` plus the exception name plus the redacted detail. The cell stores `FAILED`. The reply box is not replaced. The status line shows `failed`.

The notice stays until Close. A later miss replaces the text and opens it again. The canvas stays usable. It is not an application-modal dialog. Close carries a tip. The notice carries a tip.

The window was not opened by hand.

A wire miss from the model handshake handler uses this slide. The model serial names the handler. A local miss keeps the banner already named for it. This note does not add a second window and does not write the client.

**Truth.** Named banners stay on the status line. Any other error opens a red-bordered notice titled Error, with three wrapping read-only rows, until Close. A model HTTP miss is `Grok HTTP`, the status, and at most 180 characters with the key replaced by `[redacted]`. The cell stores `FAILED`, the reply box stays, and the status line shows `failed`. A handshake wire miss uses this same slide.

**Reason.** Errors that already have their own words do not need a second surface. `cell-busy` is a notice, not a failed cell. The slide is not application-modal so the canvas stays usable. The key text is replaced so a miss does not show the secret. A later miss replaces the text instead of stacking another window. A local miss keeps the banner already named for it.

**Avoidances.** Do not fail the cell on `cell-busy`. Do not replace the reply box. Do not make the notice application-modal. Do not show key text. Do not send more than 180 characters of the HTTP body. Do not add a second window for the handshake miss. Do not open the window by hand as the proof.

**semantic-key:** PK-6248

## Any member that fails hard or soft fails the group, the merge stores `FAILED`, and `CONCAT` does not run.

One empty member leaves `CONCAT` waiting, so the merge never writes. That wait is the hang. A hard failure or a soft failure on any member fails the group.

A valid reply is `COMPLETED` with assistant text. That text stays, and the save that stored it stays.

A hard failure returns no reply to store. The reasons are `engine-down`, a model transport miss, and `too-large`. The slide border and the title ink are red.

A soft or logical failure is a rule that stopped the cell, or a send that left the answer blank. The reasons include an empty reply, `grok key missing`, `no-model`, a `Grok HTTP` line, `air-gap`, `burner denied`, `unknown provider`, `unknown role`, `pipe`, and `no tool is invocable`. The slide border and the title ink are yellow. The failed cell stays blank and stores `FAILED`.

`cell-busy` is not a failure. It does not fail the group and does not open the slide.

The named banners still paint the status line. They also open the slide in the color named here. The body stays three wrapped rows. The notice stays until Close.

The merge cell stores `FAILED` and its reply stays blank. `CONCAT` does not run. Members that already stored a valid reply are left as they are. When any miss in the group is hard, the notice is red. When every miss is soft, the notice is yellow.

The vault tokens stay null, `COMPLETED`, `FAILED`, and `CANCELLED`. The tree still waits on a failed member. This row does not write the client.

This row does not choose the execution-target widget. Row 8 on the model smoke sheet stays open.

A later agreement makes this row the address for the hang. The build is its own named build and sits outside the model-code pause. Any member `FAILED` or `CANCELLED` fails the merge. That sentence does not write the client.

A later approval includes this rule in that build. Any member `FAILED` or `CANCELLED` fails the merge. A group whose only miss is `CANCELLED` does not open the error slide. That sentence does not rewrite the rows above.

**Truth.** A hard or soft member failure fails the group. The merge stores `FAILED`, its reply stays blank, and `CONCAT` does not run. Hard misses use a red slide. Soft misses use a yellow slide. Any `FAILED` or `CANCELLED` member fails the merge. A group whose only miss is `CANCELLED` does not open the error slide. Vault tokens stay null, `COMPLETED`, `FAILED`, and `CANCELLED`.

**Reason.** One empty member left `CONCAT` waiting, so the merge never wrote. That wait is the hang. A valid `COMPLETED` reply is left as stored so a member that already answered is not wiped. `cell-busy` is not a failure. Named banners stay on the status line and also open the slide so the color is visible. The execution-target widget stays unchosen here. The cancel-only case fails the merge without the slide so a cancel is not painted as the red or yellow error.

**Avoidances.** Do not run `CONCAT` after a member failure. Do not clear a valid reply already stored. Do not treat `cell-busy` as a group failure. Do not open the slide when the only miss is `CANCELLED`. Do not rewrite the earlier color rows with the later cancel sentence. Do not choose the execution-target widget in this rule. Do not write the client in the filing sentences that say they do not.

**semantic-key:** PK-6249

## An answer still missing when the request cap passes is a soft failure, and the cap is 1800 seconds.

The execution target stays the free text field. The locked list of the host's Ollama models plus `grok/<model>` is not the control. Row 8 is closed.

An answer that is still owed is a soft failure. A member has stored a response. Another member still has no answer. The reasonable time is the request cap on the model serial. That cap was 120 seconds until another number was named. The clock starts when the stored response lands. When the cap passes and the answer is still missing, that member is a soft failure.

The named cap is 1800 seconds. That is the request cap. The owed-answer clock uses that cap. The client in the tree still uses 120 seconds until that number is built.

The yellow slide says the cell is still waiting and no answer arrived. The reply field stays clear. The cell stores `FAILED`. A valid reply already stored stays stored. The group fails under the group-fail rule. The merge stores `FAILED` and `CONCAT` does not run.

A miss that already failed, hard or soft, stands. This wait does not replace it. `cell-busy` is not this wait.

The tree still waits. This row does not write the client.

**Truth.** The execution target stays free text. An owed answer becomes a soft failure when the cap passes after a sibling response lands. The named cap is 1800 seconds. Until that number is built, the client uses 120 seconds. The yellow slide says the cell is still waiting and no answer arrived. The cell stores `FAILED`, the reply field stays clear, the merge stores `FAILED`, and `CONCAT` does not run.

**Reason.** A member that already answered must not leave the group waiting forever on a sibling that never answers. The clock starts when the stored response lands, not when the run starts. 1800 seconds is the named request cap. The tree still has 120 seconds until that cap is built. A failure that already stands is not replaced by this wait, and `cell-busy` is not this wait.

**Avoidances.** Do not replace the free-text target with a locked model list. Do not use a cap other than 1800 seconds once that number is built. Do not leave the client on 120 seconds after the build. Do not clear a valid reply already stored. Do not replace a hard or soft miss that already stands. Do not treat `cell-busy` as an owed answer. Do not run `CONCAT`.

**semantic-key:** PK-6250

## Thinking text lives in one three-line strip above the footer, and it drops with the session.

The thinking text drops with the session. It is not a vault column. The surface is one strip on the pane, above the original footer and under the main canvas. Three lines are viewable. The widget is a read-only text area. A `v` minimizes the strip and restores it. The `v` carries a tip in the turn it is built.

A call that produces no thinking hides the strip. The strip comes back when thinking occurs again. While a run is producing thinking, the strip shows that run. A group shows the member that is running. The last member that produced thinking stays up until a later call hides the strip or replaces the text.

The model-card split is the earlier fit. This strip is the pane. A local model's `message.thinking` uses this same strip. This row does not write the client. The model-code pause still holds.

**Truth.** One read-only strip above the footer shows three lines of thinking for the run in progress, or the last member that produced it. A call with no thinking hides it. The text is not a vault column and drops with the session. The `v` minimizes and restores the strip and carries a tip in the turn it is built.

**Reason.** Thinking is session surface, not a stored column. One strip serves the pane, including a local model's `message.thinking`, so the card split is not a second surface. A group shows the member that is running so the strip follows the live call. The pause still holds because this row files the strip and does not build it.

**Avoidances.** Do not store thinking in a vault column. Do not add a second thinking widget beside this strip. Do not leave the strip up after a call that produces no thinking. Do not write the client in this row. Do not lift the model-code pause.


**semantic-key:** PK-3897 PK-3898 PK-3899 PK-3900 PK-3901

## A new turn copies the prior turn's model config, and the prompt stays empty.

When a new turn is created, after a response from the API or by the New turn button, that turn takes the model config of the prior turn. A continued Grok chat stays on Grok until the family is switched to Ollama or a merge is set up. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The model serial's sentence that a new turn does not copy settings stays the earlier reading. This rule is the operative reading of a new turn's model config.

The prior turn is the last cell on this canvas before the new card. The new-turn path is the path for the New turn button and for the card born after a response on the last cell. Import, Pull, Clear turns, and the merge sink do not use this copy.

The new card stores the prior card's execution target. An empty target stays empty. The prompt stays empty. The new card stays Included. It does not join the prior card's group.

When the prior card has a Grok document, that document is copied onto the new cell id. The copy includes the model, the level, `send_reasoning_effort`, stream, burner, temperature, and max tokens. `alsoDefault` stays false. An empty model still does not become standing. When the prior card has a session think holder, the new cell gets the same `ollamaOn`, `ollamaLevel`, `ollamaId`, and `grokOn`. No new vault column.

A canvas with no cell yet stays the factory empty turn. The New turn tip says the new turn keeps the prior turn's model. The tip is written in the same turn the control is changed.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made.

The copy is on the new-turn path only. The target string and the Grok document that already exist are copied. The prompt stays empty and the card stays Included. No channel is added. Import, Pull, Clear, and the merge sink stay off this copy. The row is the build.

**Truth.** New turn, and the card born after a response on the last cell, copy the prior cell's target, Grok document, and think holder. The prompt stays empty, the card stays Included, and it does not join the prior group. `alsoDefault` stays false. A canvas with no cell yet stays the factory empty turn.

**Reason.** A continued chat should stay on the same model until the family is switched or a merge is set up. The earlier serial sentence that a new turn does not copy settings remains the earlier reading. This rule is operative for the new card. Import, Pull, Clear turns, and the merge sink stay off the copy so those paths do not invent a prior model. An empty model does not become the standing default.

**Avoidances.** Do not copy on Import, Pull, Clear turns, or the merge sink. Do not fill the prompt. Do not join the prior group. Do not set `alsoDefault`. Do not add a vault column or a channel. Do not open the window or make the live POST.


**semantic-key:** PK-3902 PK-3903 PK-3904

## When a response on the last cell appends a card, that new card becomes the selected focus.

When a turn completes and a new turn card is appended to the canvas, the current selected focus moves to that new cell card. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

Selected focus is `focusedCellId`. It paints the sapphire edge. The inspector, Run, and Delete follow it. A click writes it in `armFocus`. The completion path already scrolls to the new card and places the caret in its prompt. It leaves `focusedCellId` on the card that just answered.

The path is a response that completes on the last cell of a canvas and appends a card through `newTurn(true)`. The New turn button calls `newTurn(false)` and leaves the selected card where it was set. A completion on an earlier cell still focuses that cell's reply. Import, Pull, Clear turns, and the merge sink stay on their own paths.

Before the canvas rebuilds, the new cell id is `focusedCellId` and `pendingFocusId`. The sapphire edge is on that card. The inspector lists that card. The status line names that card's engine. The caret lands in that card's prompt. The canvas scrolls to the end. The Join source stays the last card that was clicked.

The new-turn model copy stays. The new card still keeps the prior turn's model. The prompt stays empty. The card stays Included and ungrouped.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made.

`focusedCellId` is written before the rebuild so the sapphire edge, the inspector, and Run follow that card. The caret stays in the new prompt and the scroll stays at the end. The button, import, Pull, Clear, and the merge sink stay on their own paths. The model copy stays. The row is the build.

**Truth.** A completion on the last cell that appends through `newTurn(true)` sets `focusedCellId` and `pendingFocusId` to the new cell before the rebuild. The sapphire edge, the inspector, Run, Delete, the status engine line, and the caret follow that card. `newTurn(false)` does not move the selected card.

**Reason.** The completion path already scrolled to the new card and placed the caret, but it left focus on the card that just answered. The selected card should be the card that just appeared. The Join source stays the last card clicked so this focus move does not change Join.

**Avoidances.** Do not move focus on the New turn button, on a completion of an earlier cell, on Import, on Pull, on Clear turns, or on the merge sink. Do not drop the model copy, the empty prompt, or the Included ungrouped state. Do not change the Join source. Do not open the window or make the live POST.


**semantic-key:** PK-3905 PK-3906 PK-3907

## Both new-turn paths land the canvas viewport on the last pixel of the column after the prompt is focused.

When a new turn is created, and the canvas is scrolled down to the bottom, sometimes it is not. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The canvas is one column in one scroll pane. A new card is born in `newTurn`. The button passes false. A finished last cell passes true. Three moves leave the viewport short of the end.

The button writes `pendingFocusId` only when that id is already null. A scroll does not clear the id. The next button still aims at the older card, and `scrollRectToVisible` on a card taller than the viewport prefers the top of that card.

The land path sets the viewport to the end and then focuses the prompt. The caret scroll pulls a card that is taller than the viewport back off the end. When the card fits, the prompt is already visible and the miss stays hidden.

A group member row calls `revalidate` from `pinMemberRow` after the first measure. That layout runs after the end position is stored. The column grows. A grouped canvas can finish short of the end. A member row scrolls sideways only. The canvas pane is the one that moves.

Both new-turn entries set `pendingFocusId` to the new cell and `pendingLandOnInput` true before `rebuildTabs`. `focusedCellId` moves only on `newTurn(true)`. Import, Pull, Clear, a completion on an earlier cell, and the merge sink stay on their own scroll paths.

The land pass focuses the new card's prompt, then sets the canvas viewport to the end. Laid-out height under 1 uses the preferred height. The canvas scroller skips a `VERTICAL_SCROLLBAR_NEVER` ancestor. One following EDT pass validates and pins the end again, and does not focus. A group row that grows after the first measure still ends at the bottom. The Join source stays the last card that was clicked.

The model copy and the selection rule stay. The new card still keeps the prior turn's model. The prompt stays empty. The card stays Included and ungrouped. The sapphire edge still moves only on the append that follows a finished last cell.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made.

One landing serves both `newTurn` calls. `pendingFocusId` aims at the new card and the land flag is set. The sapphire write stays on `newTurn(true)`. The prompt is focused, then the canvas is pinned to the end, then pinned once more after layout. The member-row scroller is skipped, and `pinMemberRow`'s later layout finishes before the second pin. The model copy, the selection rule, import, Pull, Clear, and the merge sink stay. The row is the build.

**Truth.** Both new-turn entries set `pendingFocusId` and `pendingLandOnInput` before `rebuildTabs`. The land focuses the prompt, then pins the viewport to the end, then pins it again on a later EDT pass without focusing. `focusedCellId` still moves only on `newTurn(true)`.

**Reason.** The button kept an older pending id because a scroll does not clear it, and `scrollRectToVisible` prefers the top of a card taller than the viewport. Focusing the prompt after the end pin pulled a tall card back off the end. `pinMemberRow` revalidates after the end is stored, so a grouped column grows past that pin. The second pin runs after that layout. Height under 1 uses the preferred height so an unlaid column still has an end. A `VERTICAL_SCROLLBAR_NEVER` ancestor is not the canvas scroller. The member row scrolls sideways only.

**Avoidances.** Do not pin the member-row scroller. Do not focus on the second pin. Do not move the sapphire edge on `newTurn(false)`. Do not change Join's source. Do not take Import, Pull, Clear, an earlier-cell completion, or the merge sink onto this land. Do not open the window or make the live POST.


**semantic-key:** PK-3908 PK-3909 PK-3910

## Duplicate group adds an empty replica after the selected group, and Shift-Ctrl-G does the same.

Groups with a merge are working. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

A selected group is the group of the armed card. The whole group lights when the merge is armed, the same way it does today. A member card names that same group. Right-click opens the card menu. Duplicate group adds a replica after that group. The new prompts and replies are empty. Shift-Ctrl-G does the same. The letter is the G key, with or without caps lock. A card with no group, or no armed card, opens the error slide in yellow with the words `no group selected`.

The card menu gains Duplicate group. The tip says the replica is empty, the shape stays, and Shift-Ctrl-G does the same. Import stays on the prompt side. A right-click on the reply still opens the menu so Duplicate group is there. Import on the reply stays refused.

The replica follows the selected group. It has the same members in the same order, the same merge when the source has one, the same execution targets, the same `I` `E` `P` `O` states, and the same burner marks. Prompts and replies are empty. Run state is empty. The cards start open. A new group id is minted. The parent pointer stays empty. Turn numbers follow the new order. The armed card stays the one that was selected. A Grok entry and a thinking holder are copied onto the matching new cell.

Shift-Ctrl-G sits on the same map as Ctrl-Enter, so it fires while a prompt has the caret.

No armed card, or an armed card with no group, calls the error slide with `no group selected` and the yellow skin. A yellow notice clears the hard latch so this miss paints yellow. A hard notice still sets the latch.

Group, Join, Ungroup, Delete, and the merge sink stay. No new package, public type, vault column, or G-key.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made.

The member row and the merge are copied, and the text is left empty. Duplicate group is on the card menu, and Shift-Ctrl-G is on the same map as Ctrl-Enter. The replica is parked after the source group and the turns are renumbered. The yellow slide is for a card that is not in a group. The row is the build.

**Truth.** Duplicate group and Shift-Ctrl-G copy the selected group's shape after that group, with empty prompts, empty replies, and an empty run state. The new cards start open, a new group id is minted, and the parent pointer stays empty. No group, or no armed card, shows yellow `no group selected`.

**Reason.** The replica keeps members, order, merge, targets, `I` `E` `P` `O`, and burner marks so the shape stays while the text starts clear. The armed card stays the one already selected. A Grok entry and a thinking holder are copied onto the matching new cell so the replica can run as the source would. The shortcut shares the Ctrl-Enter map so it fires while a prompt has the caret. A yellow notice clears the hard latch so this miss is not painted as a hard error. Caps lock does not change the G key.

**Avoidances.** Do not copy prompt or reply text. Do not keep the source group id or a parent pointer. Do not leave the new cards collapsed. Do not accept Import on the reply. Do not add a package, a public type, a vault column, or another G-key. Do not drop Group, Join, Ungroup, Delete, or the merge sink. Do not open the window or make the live POST.


**semantic-key:** PK-3911 PK-3912 PK-3913 PK-3914 PK-3915 PK-3916 PK-3917 PK-3918 PK-3919 PK-3920

## An icon button after View copies that cell's whole reply to the system clipboard, and the reply window has the same icon.

A button on every cell button bar that has an output window copies that cell's whole reply to the operating-system clipboard. The button is a contemporary clipboard copy icon. The reply window gets the same icon. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The button sits on the main bar of every readable card, immediately after View. A collapsed readable card keeps it. The stored reply is the output when the window is folded. The group bar stays the target, Group, Join, and Ungroup. An unreadable load-error card stays bare.

The control is an icon-only button. The glyph is two overlapping rounded rectangles, painted in the button foreground and dimmed when the button is disabled. The accessible name is Copy. The button is enabled when the copy source has non-whitespace text, and disabled when that source is empty, so a click leaves the clipboard as it is. Construction uses the same gate as View. The first pool chunk that leaves non-whitespace text in the mounted assistant area enables the button.

An open card copies the assistant area, including pool chunks painted before the reply is stored. A collapsed card copies the stored reply. The prompt stays on the card. The thought strip stays on the footer. JOIN lines stay in the reply. A selection stays a selection. The copy is the whole document. A folded card during a pool stream copies the last stored reply. The brief keeps the stream for the eye. The copy leaves the brief alone until the reply is stored.

The reply window gains the same icon on the south bar, before Close. The click copies the snapshot that window opened. The find field and its highlights stay. The dialog title stays as it is. The window is built only when the reply has text, so the reply-window copy button is enabled while the window is up.

The clipboard write uses the system clipboard on the Swing thread. A blank body leaves the clipboard as it is. A busy clipboard sets the status line to clipboard busy. The error slide stays closed. The reply text stays off the status line. A newline that is not already a Windows pair is written as a Windows pair, so a paste keeps the breaks. The paste-in normalizer stays as it is. Both buttons carry a tip written in the same turn. No new package, public type, vault column, G-key, channel, or type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent.

The copy icon sits on every readable card, after View. The two-rectangle mark is painted, and the reply window icon sits before Close. The clipboard is written, and a busy clipboard stays on the status line. An empty click leaves the clipboard as it is. The row is the build.

**Truth.** Copy is an icon-only button of two overlapping rounded rectangles, accessible name Copy, immediately after View on every readable card, and on the reply window's south bar before Close. A non-whitespace source copies the whole document as Windows newline pairs. An empty source leaves the clipboard alone. A busy clipboard sets the status line to clipboard busy.

**Reason.** The whole reply, not a selection, is what goes to the clipboard. An open card uses the assistant area so pool chunks painted before storage are included. A collapsed card, and a folded card during a pool stream, use the last stored reply so the brief can keep the stream for the eye. JOIN lines stay in the reply. The prompt and the thought strip stay where they are. Windows pairs keep the breaks on paste. The paste-in normalizer is a different path and stays as it is.

**Avoidances.** Do not copy the prompt, the thought strip, or only the selection. Do not enable Copy when the source is empty or whitespace. Do not put reply text on the status line. Do not open the error slide for a clipboard miss. Do not put the button on an unreadable load-error card. Do not add a package, a public type, a vault column, a G-key, a channel, or a type. Do not open the window, make the live POST, or send a pool turn from this filing.

**semantic-key:** PK-6251

## Footer Delete removes one normal card, or the whole group and its merge, and it stays off when that set is the entire canvas.

Duplicate group is in the tree. Footer Delete reads the armed card. A normal card deletes that cell. A group cell set deletes the whole group and the merge. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The control stays the Delete button on the original footer. A normal card is an armed cell whose group id is empty, and the click removes that one row. A group cell set is the group of the armed card. A member names that group. The merge names that group. The click removes every cell on this canvas with that group id, the members and the merge, in one vault transaction.

The button is enabled when the removal set is non-empty and smaller than the canvas. One cell on the canvas leaves it off. A group that is the entire canvas leaves it off. Clear turns stays the wipe that leaves one empty cell. There is no confirm dialog.

A one-cell delete keeps the earlier vault reading. The row leaves, the order drops that id, the survivors store turn indexes from 1, and trace rows for that id leave. Pipe tokens stay as written. A parent pointer on a survivor stays stored. Another canvas stays.

A group delete uses that same reading for every id in the set, in one transaction. `deleteTurn` delegates to that write. A late save skips a buried id, because an update that matches zero rows inserts the cell again. A failed delete lifts the burial. A successful delete keeps it for the session.

A `RUNNING` member of the set is marked `STOPPED` and sent the cancel frame Stop already sends. A chain that names a removed id is cleared. The owed-answer timer for that group is stopped before the worker. The Grok holder and the thinking holder for each removed id are dropped. Focus moves to the cell that followed the first removed index, or to the last remaining cell when the set sat at the end.

The Delete tip is rewritten in this same turn. It names the normal card, the group, the renumber, the empty-canvas gate, and the pipe tokens. The sapphire paint stays the rule that lights the whole run when the merge is armed. Group, Join, Ungroup, Duplicate group, and Shift-Ctrl-G stay. The copy icon stays. The pool hold stays on the server. No new package, public type, vault column, G-key, channel, or type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent.

A member stays with its set. The ids are buried so a late save stays quiet, and the group timer stops before the worker. The Delete tip is rewritten in the same turn. One vault transaction writes the set, and focus parks on the survivor. The empty-canvas gate and the pipe text stay. The row is the build.

**Truth.** Delete removes one ungrouped card, or every cell on this canvas that shares the armed card's group id, including the merge, in one transaction. The button is off when the set is empty or is the whole canvas. There is no confirm dialog. Survivors renumber from 1. A late save skips a buried id for the session.

**Reason.** A group cell set is the group, whether the armed card is a member or the merge, so one click takes the members and the merge together. Clear turns remains the wipe that leaves one empty cell, so Delete stays off when the set is the entire canvas. Burial exists because an update that matches zero rows would insert the cell again. A failed delete lifts the burial so the cell can be saved. A `RUNNING` member is `STOPPED` with the same cancel frame Stop already sends, and the owed-answer timer stops before the worker so that wait cannot land on a removed id.

**Avoidances.** Do not confirm the delete. Do not delete the last remaining set on the canvas. Do not rewrite pipe tokens or a survivor's parent pointer. Do not touch another canvas. Do not leave a buried id after a failed delete. Do not leave the Grok holder or the thinking holder for a removed id. Do not add a package, a public type, a vault column, a G-key, a channel, or a type. Do not open the window, make the live POST, or send a pool turn from this filing.


**semantic-key:** PK-3921 PK-3922 PK-3923 PK-3924 PK-3925 PK-3926 PK-3927 PK-3928 PK-3929 PK-3930

## After Connect brings Aetheris into focus, the showing canvas scrolls to the newest turn.

After Connect, when the saved tab brings Aetheris into focus, the canvas was opening at the top. That first open scrolls to the bottom so the newest turn is in view, the same way a new turn lands. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The scroll is the land that already follows a new turn. The caret goes in the newest prompt. The viewport sits on the last pixel of the column, then once more after layout. A canvas that is still loading waits for that load. A canvas already on screen scrolls at once. A manual visit to the tab does not take this path. SSH still opens on SSH.

`SshClientApp.maybeRestoreAetherisTab` calls `AetherisNotebookPanel.landOnNewest` after it selects Aetheris.

`landOnNewest` uses the new-turn land. The newest cell on the showing canvas is the target. The viewport goes to the end of the column. The prompt of that cell takes the caret.

When the vault bind is still in flight, the land waits for that bind's canvas paint. When the vault is already showing, the land runs then.

No new package, public type, vault column, G-key, channel, or wire type. The new public method is `AetherisNotebookPanel.landOnNewest`.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

The new-turn scroll stays, and a manual tab visit stays off this path. The land is called after the tab select. The bind that arrives after the select is held. The row is the build.

**Truth.** The saved-tab select calls `AetherisNotebookPanel.landOnNewest`. The newest cell on the showing canvas takes the caret, and the viewport sits on the last pixel of the column, then once more after layout. A vault bind still in flight delays the land until that canvas paints. The new public method is `landOnNewest`.

**Reason.** The first open after Connect was leaving the canvas at the top, so the newest turn was out of view. The land is the same one a new turn already uses. A canvas still loading waits so the land is not applied to an empty paint. A canvas already showing scrolls at once. A manual visit does not take this path, and SSH still opens on SSH.

**Avoidances.** Do not scroll on a manual visit to the tab. Do not land before a vault bind still in flight has painted. Do not change the SSH open. Do not add a package, a public type, a vault column, a G-key, a channel, or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.

**semantic-key:** PK-6252

## The mood layer is online, and a mood miss reports on the error slide, takes the layer offline, and still lets the chat turn run.

The mood layer comes online. A mood miss reports on the error slide and the mood layer goes offline. A chat turn still runs. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

Pin OK still opens Mood while the layer is online. An empty `mood-shape` is the Ollama model in use. Send talks to that shaper. One send is in flight. Approve keeps the transcript as the second system line. Cancel keeps the filter commit and leaves the mood line empty. The host line stays the host line.

A miss does not open a blocking dialog. The error slide carries the words. Those words start with `keyword only.` A reason from the shaper follows when one is present. A hard miss keeps the red slide. Any other miss uses the yellow slide. The mood line is cleared. The layer stays offline until the app starts again. A later pin OK still writes the filter and does not open Mood. A frame for a mood id never marks a cell, never clears a chain, and never fails a turn. When the mood line would make a turn too large, the line is dropped, the slide shows the miss, and the turn is sent without it. The inspector shows `mood: offline` after that miss.

The notebook keeps the Mood dialog for a live shaper. One send is in flight. A second Send leaves the typed line in the field.

A mood miss closes the dialog, paints the error slide, clears the mood line, and leaves the layer offline for this session. The canvas stays usable. The cell is not stored `FAILED` from that miss.

Frames that belong to a mood id stay on the mood path after the dialog closes. They do not enter the cell error path.

A turn whose body does not fit because of the mood line drops that line, reports the miss, and sends the turn.

No new package, public type, public method, vault column, G-key, channel, or wire type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

The pin commit, the cell door, and the error slide stay, and a mood id stays off the cell path. The host line and the approved mood line stay. The offline switch lands, and a turn drops a line that does not fit. The wire ids after the dialog closes stay on the mood path. The row is the build.

**Truth.** While the mood layer is online, pin OK opens Mood, an empty `mood-shape` is the Ollama model in use, and one Send is in flight. A miss starts with `keyword only.`, uses red for a hard miss and yellow otherwise, clears the mood line, and leaves the layer offline until the next app start. The inspector then shows `mood: offline`. A too-large mood line is dropped and the turn is sent. The cell is not stored `FAILED` from a mood miss.

**Reason.** A mood miss must not stop the chat turn. The slide is not a blocking dialog, so the canvas stays usable. Approve keeps the transcript as the second system line, and Cancel keeps the filter commit while leaving the mood line empty. The host line stays the host line. A later pin OK still writes the filter and does not open Mood once the layer is offline. A mood-id frame never marks a cell, never clears a chain, and never fails a turn, including after the dialog closes.

**Avoidances.** Do not open a blocking dialog for a mood miss. Do not store the cell `FAILED` from that miss. Do not let a mood-id frame enter the cell error path. Do not send a second Send while one is in flight. Do not send a turn that does not fit while still carrying the mood line. Do not add a package, a public type, a public method, a vault column, a G-key, a channel, or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3931 PK-3932 PK-3933 PK-3934 PK-3935 PK-3936 PK-3937 PK-3938 PK-3939

## While thinking is on, the thinking pane stays up until Close, and it rises from the top of the footer.

In the pane, when thinking is on, the thinking slide up works. It was disappearing when thinking pauses. It stays in view until the user closes that pane. Close is on that pane. The pane sits above the footer bar. It slides up from the top of the footer bar. The footer buttons stay reachable. It persists until the user closes it. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The pane is the thinking strip. Three lines stay viewable. The text stays yellow. `v` still folds the text and `^` still restores it. Close is the dismiss. A pause leaves the pane and the text. A later thought on another cell replaces the text. A later thought on the same cell appends. The text still drops when the session ends. It is still not a vault column. The strip rule stays locked.

The notebook keeps the thinking pane above the footer. The footer sits on the bottom of that stack at its own height. The pane's bottom edge stays on the top edge of the footer. The height grows upward from that edge. The footer buttons stay at full size.

While thinking is arriving, the pane opens with that slide. A pause leaves the pane up. The text stays. Close dismisses it. The dismiss slides the height back to the top of the footer, then drops the text.

`v` still folds the text. Close stays on the pane while the text is folded. A later thought still fills the area. A thought after Close opens the slide again.

No new package, public type, public method, vault column, G-key, channel, or wire type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

The footer stays at full height under the pane, and the text stays off the vault. The yellow thought text and the `v` fold stay. The slide lands on the top edge of the footer, and Close dismisses the pane. A second finish leaves the text on a pause. The row is the build.

**Truth.** The thinking pane's bottom edge is the top edge of the footer, and its height grows upward. Three yellow lines stay viewable. A pause leaves the pane and the text. Close slides the height back to the footer, then drops the text. A later thought on the same cell appends. A later thought on another cell replaces the text. The text still drops when the session ends.

**Reason.** The slide was disappearing when thinking paused, so the user could not read it. Close is the dismiss, not the pause. The footer stays at its own height so its buttons stay full size and reachable. `v` still folds the text and `^` still restores it, and Close stays on the pane while the text is folded. A thought after Close opens the slide again. The text is still not a vault column.

**Avoidances.** Do not hide the pane on a pause. Do not shrink the footer buttons. Do not drop the text before the dismiss slide finishes. Do not store the text in a vault column. Do not add a package, a public type, a public method, a vault column, a G-key, a channel, or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3940 PK-3941 PK-3942 PK-3943 PK-3944 PK-3945 PK-3946 PK-3947 PK-3948 PK-3949 PK-3950 PK-3951 PK-3952 PK-3953 PK-3954 PK-3955 PK-3956 PK-3957 PK-3958

## Thinking state is copied onto the next turn and stored beside the notebook, and the thinking log scrolls, drags, and survives the session.

The thinking checkbox in Model Config does not persist. When it is turned on and the model config is copied for the next turn, the thinking state is carried. The thinking pane is sized by a drag on its north border. Thinking is a continuous scroll. The text is tagged from the model source, per block, with a separator between each model's text, so a merge stays readable. The log persists between sessions. A Clear control on the pane clears the pane and the saved log, and the log then accumulates again. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

`newTurn` already copies a session holder onto the new cell: `ollamaOn`, `ollamaLevel`, `ollamaId`, `grokOn`, and `poolOn`. The holder is the map. It is not a vault column and it is not in `aetheris-grok.yaml`. A restart builds an empty map. The checkbox then reads off. The next new turn copies no thinking state. The execution target and the Grok document still copy. That is the miss. The new-turn model copy stays locked. This rule stores the holder and names `poolOn` on that same copy.

The stay-open rule stays locked. Close still hides the pane. This rule keeps the log when Close hides the pane. Clear is the drop. The text stays off the turn.

The holder is written to `~/.casserole/aetheris-think.yaml`, version 1, keyed by cell id. The keys are `ollama_on`, `ollama_level`, `ollama_id`, `grok_on`, and `pool_on`. The dragged height is the key `height` in that same file. A version other than 1 is left unread and is not overwritten. The panel loads the file when it opens. A checkbox change writes the file. An effort change writes the file. `newTurn` still copies the holder, and the copy is written under the new cell id. A duplicated group writes the copied holders. A deleted cell is dropped from the file. No new vault column. `aetheris-grok.yaml` stays the Grok document.

The north edge of the thinking pane is a drag. Dragging it changes the pane height. The height grows and shrinks from the top of the footer. The footer buttons stay at full size. A drag keeps that height for this session and writes it into the yaml. The next session opens at that height. The minimum is the three-line pane. The maximum leaves the canvas and the footer in reach.

Thinking text is a continuous scroll. A later thought appends. It does not replace the log. Each block is tagged with the model name of the cell that produced it. A new block writes a separator and that tag, then the text. The same block's later chunks append. A pause closes the block. The next thought opens a new block. A group shows one block per member run, so each model stays marked. The body stays yellow. The tag is cyan. The area is a read-only styled pane. `v` still folds the text and `^` still restores it.

The log is `~/.casserole/aetheris-thinking.txt`. It is not a vault column. It is not stored on the turn. The next session loads it into the pane. When the log is non-empty, the pane is up. Clear is a small `C` at the east of the north edge, out of the text. Clear empties the pane and the file. The pane stays up. The next thought accumulates from that empty log.

No new package, public type, public method, vault column, G-key, channel, or wire type.

The project compiles, and the test suite is not the proof. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

The other client's match is a row on section 12 of , written when this client lands. That slice waits until the catch-up is named. This rule writes no crate.

The holder stays off the vault and is written beside the Grok document. The body stays yellow, the model tag is cyan, and the small `C` sits on the north edge. The drag, the continuous log, and the file that Clear wipes stay. The copy on `newTurn` includes `poolOn`, and a second run is a new block. The row is the build.

**Truth.** The think holder is version 1 at `~/.casserole/aetheris-think.yaml`, keyed by cell id, with `ollama_on`, `ollama_level`, `ollama_id`, `grok_on`, `pool_on`, and `height`. The log is `~/.casserole/aetheris-thinking.txt`. A non-empty log opens the pane. Clear is `C` and empties both the pane and the file. Blocks are yellow, tags are cyan, and a drag keeps the height between the three-line minimum and a maximum that leaves the canvas and the footer in reach.

**Reason.** A restart was building an empty map, so the checkbox read off and the next new turn copied no thinking state even though the target and the Grok document still copied. The file keeps the holder, including `poolOn`, and `newTurn` writes the copy under the new cell id. A duplicated group writes the copied holders. A deleted cell is dropped. Close still hides the pane, and the log stays. Clear is the drop. A version other than 1 is left unread and is not overwritten so a foreign file is not destroyed. The text stays off the turn.

**Avoidances.** Do not store the holder or the log in a vault column or on the turn. Do not write the holder into `aetheris-grok.yaml`. Do not overwrite a file whose version is not 1. Do not replace the log on a later thought. Do not drop the log on Close. Do not hide the pane on Clear. Do not let the drag cover the canvas or the footer. Do not write the other client's crate in this rule. Do not add a package, a public type, a public method, a vault column, a G-key, a channel, or a wire type. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3959 PK-3960 PK-3961 PK-3962 PK-3963 PK-3964 PK-3965 PK-3966 PK-3967 PK-3968 PK-3969 PK-3970 PK-3971 PK-3972

## Mood is optional, a footer switch enforces one saved string for the session, and Mood drafts that string on the selected card's model.

Mood is optional. Only a defined mood activates the workflow, and it stays enforced for the session. The user may turn the mood off, and that removes every mood record. A mood switch sits on the footer. Turning it back on, without a change to the mood, inserts the last saved mood string at the head of the transcript. Changing the mood is a macro override of the transcript, including turns spoken under an earlier mood. A Mood button sits on the footer. The button uses the model config of the selected cell to draft the mood string. The first filing does not name the build. The row is closed. The client waits until a later sentence names the build.

The tool bar gains a separator after To terminal. The mood switch and the Mood button sit after that separator. The client still waits while those requirements are settled.

The dialog carries a private JediTerm. The connector is local. The transcript is an in-memory list. Each Send pushes that whole list on channel A. No new lane. The client waits until the build is named.

The first build is the mood string. It keeps the transcript surface and the Send field. The private JediTerm waits. The plan stamp names that first build.

The earlier mood-layer rule stays the earlier reading. This rule is the mood door.

With no saved string, a Run carries the host line and the turns. The switch stays off. A click on the switch does not turn it on.

The saved string is session memory. It is not a vault column. It is not written into a cell. A restart starts with no saved string and the switch off. Export does not carry the string. Clear turns leaves the string and the switch.

The tool bar carries the mood controls. The row is search, Search, Export, Import, Pull, To terminal, then a separator, then the mood switch, then the button labeled `Mood`. The separator is the same narrow vertical mark the original footer uses between groups. The original footer does not carry these two controls. The tool bar still starts hidden. The `v` on the original footer opens the row, and that tip names the mood switch and the Mood button with the other tools. The switch has two positions. Left is off. Right is on. It is not a range slider. The switch tip while on says a click removes every mood record and keeps the saved string. The switch tip while off, with a string saved, says a click inserts that string at the head. The switch tip with no string says the switch stays off until a mood string is saved. The button tip says Mood opens a chat with the selected card's model, and Approve saves one mood string and places it at the head.

The Mood button opens the dialog for the selected card. The selected card wears the sapphire edge. A missing readable card leaves the dialog closed. The status line shows `no cell`.

The speaker is that card's model config. The call uses the target and the model id that card would use on Run. An empty target is `ollama`. A Grok card uses that card's Grok model id. A pool card uses `pool`. The call is ephemeral. Think stays off. The working set is the shaping chat. It is not the canvas transcript.

A cloud target that card is not allowed to call leaves the dialog closed. The error slide says `keyword only.` The saved string and the switch stay. A missing connector does the same.

The dialog title is `Mood`. It is modal. The center is a private JediTerm. Approve, Cancel, and an editable mood-string field sit under the glass. Enter on the glass sends the current line. An empty line is ignored. One send is in flight. A second Enter leaves the typed line on the glass.

The glass is a second JediTerm. It is not the live terminal. It is not a terminal session, and the vault does not follow it. Its settings are its own copy. The live terminal font stays put. The widget is created with the dialog and disposed when the dialog closes, on Approve, on Cancel, and on a miss.

The connector is local. It is the terminal byte seam for this glass. Keystrokes stop there. Terminal reports stop there. The glass writes nothing on channel T. It does not use `SshTtyConnector`. The page editor, bracketed paste, and the shell commands stay on the real terminal.

The transcript is an in-memory list. The glass paints that list. The list is not scraped off the screen. Open starts the list empty. Each Send pushes the whole list on channel A, the same full working set a canvas Run pushes. The call stays ephemeral. The mood id is consumed before the cell path. The reply is printed in one write when it arrives. The last assistant line fills the mood-string field. The operator can edit that field. Approve commits the field. The list and the widget are discarded when the dialog closes. The saved mood string is what remains.

The shaping system line asks for one mood string. The string is style, mood, and symbolic modifiers. The host line on a canvas Run stays the host line. When the shaping body does not fit, the slide shows `keyword only. too-large`. The saved string and the switch stay. The list stays until the dialog closes.

Cancel closes the dialog. The saved string and the switch stay as they were.

A miss closes the dialog and paints the error slide. The words start with `keyword only.` A hard miss keeps the red slide. Any other miss uses the yellow slide. The saved string and the switch stay. The canvas stays usable. A frame for a mood id never marks a cell, never clears a chain, and never fails a turn.

Approve of the first string saves it, moves the switch on, and inserts that string once at the head of the transcript. While the switch stays on, the workflow stays enforced for the session.

The switch off removes every mood record from the transcript. The saved string stays. A Run carries no mood line.

The switch on, with the saved string unchanged, inserts that string once at the head. The switch does not call the model.

Approve of a different string replaces the saved string, removes every prior mood record, and inserts the new string once at the head. The new string governs the whole transcript, including turns spoken under an earlier mood. The switch is on.

Approve of the same string leaves a single copy at the head.

The walker puts the host line first. While the switch is on, the next system message is the saved string. While the switch is off, the walker skips it. The role is `system`.

When the encoded body does not fit and the mood line is present, the slide shows `keyword only. too-large`, the body is assembled again without that line, and the turn is sent. The saved string and the switch stay on.

The inspector shows `mood: (none)` when no string is saved. It shows `mood: off` and a preview of the saved string when the switch is off. It shows `mood:` and a preview of the live string when the switch is on.

Pin OK writes the filter and leaves the dialog closed. `notebook.mood-shape` stays in the yaml. The button does not read it.

No new package, public type, vault column, G-key, channel, or wire type. The local connector is not a new public type.

The other client's match is a row on section 12 of , written when this client lands. That slice waits until the catch-up is named. This rule writes no crate.

The first build keeps the transcript surface and the Send field. The tool bar carries the separator, the mood switch, and the Mood button. The switch and Approve follow the layout above. The shaping system line is `Define one mood string. The string is style, mood, and symbolic modifiers.` A miss keeps the saved string and the switch. The layer does not latch offline. The private JediTerm, the local connector, and Enter on the glass wait until the second build is named.

The first build compiled. The window stayed closed. The live POST was not made. A pool turn was not sent. A live SSH connect was not made. The private JediTerm waits.

The second build is the mood glass. The transcript surface gives way to the private JediTerm. The Mood button tip names the glass and Enter.

The mood glass is in the tree. Enter sends on the glass. The window stayed closed. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

A check on a pool card showed the slide `keyword only. air-gap`. The shaping call was sending the burner bit. A normal pool card keeps that bit off, the same way Run does. The mood id still keeps the chat off the cell.

The mood chat stays on the glass. A you mark and an assistant mark separate the turns. The shaping line tells the model to end with one line, `mood: <the string>`. That string is the intent it understood: style, mood, and symbolic modifiers, on one line. The app keeps the last non-empty line that starts with `mood:`. That line fills the mood-string field. The rest of the reply stays on the glass. An empty key leaves the field as it was. Approve still saves the field. You is green. Assistant is blue. The mood line is yellow when the key is marked.

A later check left the mood-string field empty. The pluck wanted a line that started with the letters mood and a colon. A reply may wrap that key, change the case, put a space before the colon, or write mood string. A fenced mood block counts. The app keeps the last such value and puts it in the field. Stars, quotes, and a wrapped pair of angle brackets come off. The template words the string do not fill the field. The rest of the reply stays on the glass. An empty key leaves the field as it was. Approve still saves the field. That wider pluck is the operative reading. The last value is the one kept.

The local connector stays off `SshTtyConnector` and off channel T. Enter is Send. The widget is disposed with the dialog. One send stays in flight. The pin stays on the filter. The string stays off the vault. A mood id stays off the cell path. The host line stays, and the saved string sits at the head while the switch is on. The saved string is held while the switch is off.

**Truth.** Mood starts off and stays off until a string is saved. The tool bar, after To terminal and a separator, holds a two-position switch and `Mood`. Approve saves one `system` string and inserts it once at the head while the switch is on. The glass is a private JediTerm on a local connector, channel A carries the whole shaping list, and the wider pluck fills the field from the last mood value. A normal pool card does not send the burner bit. The layer does not latch offline. `notebook.mood-shape` stays in the yaml and the button does not read it.

**Reason.** Mood is optional so a Run with no saved string carries only the host line and the turns. The switch removes every mood record without forgetting the string, and turning it back on inserts that same string without calling the model. A different Approve replaces every prior mood record, including turns spoken under an earlier mood, because the new string governs the whole transcript. The glass is not the live terminal, so keystrokes and terminal reports stop at the local connector and the vault does not follow it. The list is pushed whole, the same way a canvas Run pushes a working set, and the mood id is consumed before the cell path. The field stayed empty when the pluck required a line that started with the letters mood and a colon, so a wrapped key, a changed case, a space before the colon, the words mood string, and a fenced block also count. Stars, quotes, and a wrapped pair of angle brackets come off, and the template words the string do not fill the field. The pool slide `keyword only. air-gap` happened because the shaping call sent the burner bit. Run leaves that bit off on a normal pool card, and the shaper does the same.

**Avoidances.** Do not turn the switch on when no string is saved. Do not store the string in a vault column, a cell, or an export. Do not clear the string on Clear turns or on restart by writing an old string back. Do not call the model from the switch. Do not open Mood when no readable card is selected. Do not write on channel T or use `SshTtyConnector` for the glass. Do not scrape the transcript off the screen. Do not send the burner bit on a normal pool shaping call. Do not latch the layer offline on a miss. Do not let a mood-id frame mark a cell, clear a chain, or fail a turn. Do not let pin OK open this dialog. Do not let the button read `notebook.mood-shape`. Do not fill the field with the template words the string. Do not write the other client's crate in this rule. Do not add a package, a public type, a vault column, a G-key, a channel, or a wire type.


**semantic-key:** PK-3973 PK-3974 PK-3975 PK-3976 PK-3977 PK-3978 PK-3979 PK-3980 PK-3981 PK-3982 PK-3983 PK-3984 PK-3985 PK-3986 PK-3987

## A reply with no mood line is asked again, three tries, then a yellow card, and the dialog footer shows the thinking spinner.

The mood line was checked. The pool model returned the mood string after the encoded format was asked for, and the line lit. The dialog retries when the reply has no mood line. Three tries, then a yellow card. The standard thinking spinner, with the burner clock, sits on the footer of the dialog while a reply is in flight. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

A reply with no mood line is one try. The dialog posts the format line and sends again. The third miss paints the yellow slide and leaves the dialog open. The footer spinner counts from the send. The window stayed closed. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The mood-string rule stays the earlier reading. This rule is the retry and the spinner.

A reply that carries a mood line fills the field. The try count returns to zero. The spinner stops.

A reply with no mood line is one try. The assistant text stays on the glass. The field stays as it was. While the count is under three, the dialog posts one line asking for the mood text in the expected form and sends that list again. The line is a user turn, so the glass marks it you. The line says to return one final line that starts with mood: and gives the example `mood: spare, dry, lantern-light`.

The third reply with no mood line stops the retries. The yellow slide says `keyword only. mood line`. That slide is the yellow card. The dialog stays open. The list stays. The field stays as it was. The saved string and the switch stay. A later line typed in the dialog starts the count over.

The dialog footer carries the same thinking spinner the notebook uses. The glyph is emerald. The word thinking is electric blue. The clock is `m:ss` from `ThinkingClock`, and it counts from that send. A retry starts the clock again. The spinner stops when the reply lands, when the yellow slide paints, when the dialog closes, and when a miss closes the dialog. Approve and Cancel stay available while the spinner runs. A second Enter while a reply is in flight still leaves the typed line.

Think stays off. The shaping call still copies the card's burner bit. Air-gap stays. Channel A stays the lane. No new channel.

The other client's match is a row on section 12, written when this client lands. That slice waits until the catch-up is named. This rule writes no crate.

The ask stays on channel A and off a new type. The spinner sits on the dialog footer, and the yellow slide is painted. Three tries and the clock stay. The field and the saved string stay. The row is the build.

**Truth.** A mood line fills the field and resets the try count. Each reply with no mood line counts as one try. Under three, the dialog sends a you line whose example is `mood: spare, dry, lantern-light`. The third miss shows `keyword only. mood line`, leaves the dialog open, and stops the retries. The footer spinner is emerald, the word thinking and the `m:ss` clock are electric blue, and the clock is `ThinkingClock` from that send.

**Reason.** The encoded format had to be asked for before the pool model returned a mood line. Three tries bound that ask. The posted line is a user turn so the glass marks it you. The assistant text and the field stay as they were until a mood line arrives. A later typed line starts the count over so a new attempt is not stuck on the yellow card. Approve and Cancel stay available while the spinner runs. Think stays off. The shaping call still copies the card's burner bit, and air-gap stays.

**Avoidances.** Do not close the dialog on the yellow card. Do not clear the saved string or move the switch on a missing mood line. Do not retry past three for the same typed line. Do not hide Approve or Cancel while the spinner runs. Do not send a second Enter's line while a reply is in flight. Do not turn Think on. Do not add a channel. Do not write the other client's crate in this rule. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3988 PK-3989 PK-3990

## The thinking word and clock stay put while the glyph turns, and the mood knob is green when on and red when off.

The thinking spinner bounces because the layout bounds the text to the left. The thinking text stays put while the glyph turns. The mood switch round button is green when the switch is on and red when it is off. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

The glyph keeps a fixed box. The word thinking and the clock stay put. The mood knob is green when the switch is on and red when it is off. The window stayed closed. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The retry-and-spinner rule stays the earlier reading. This rule is the still text and the colored knob.

The spinner glyph keeps a fixed box. The word thinking and the clock sit to the right of that box and stay put while the glyph turns. The same spinner is the notebook spinner and the dialog footer spinner. The colors stay emerald for the glyph and electric blue for the word and the clock.

The round button on the mood switch is green while the switch is on and red while it is off. Left stays off. Right stays on. The tip names the colors.

The other client's match is a row on section 12, written when this client lands. That slice waits until the catch-up is named. This rule writes no crate.

The spinner stays on the class that already turns. The fixed box and the two knob colors are painted. Left stays off and right stays on. The row is the build.

**Truth.** The spinner glyph has a fixed box. The word thinking and the clock sit to the right of that box and do not move while the glyph turns. The glyph is emerald. The word and the clock are electric blue. The same spinner is the notebook spinner and the dialog footer spinner. The mood knob is green when the switch is on and red when it is off.

**Reason.** The spinner bounced because the layout bounds the text to the left of a changing glyph. A fixed box keeps the word and the clock still. The knob colors show the two positions: left is off and red, right is on and green. The tip names the colors so the positions stay readable.

**Avoidances.** Do not let the word thinking or the clock move with the glyph. Do not change emerald or electric blue. Do not make the knob green while the switch is off, or red while it is on. Do not make the switch a range slider. Do not swap left and right. Do not write the other client's crate in this rule. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3991 PK-3992 PK-3993 PK-3994

## While the mood switch is on, the send adds a system line that tells the model to use that mood as the shape of its responses.

The mood was set on a fresh canvas, then the talk began, and the model used that mood only after it was coaxed. A header is added after the mood setting when the transcript is sent. It is not stored in a cell. It is prepended and managed the way the mood header is. The header tells the model to activate and use that mood as the shape of its responses. The check used qwen2.5-coder:7b. This sentence names the full cycle and the build. This rule is closed. The plan stamp names the build.

A shape line follows the mood string on a canvas send. The line tells the model to activate that mood and use it as the shape of its responses. The window stayed closed. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The spinner-and-switch rule stays the earlier reading. This rule is the shape line.

The walker puts the host line first. While the switch is on and a mood string is saved, the next system message is that string. The system message after that string tells the model to activate the mood above and use it as the shape of its responses. The words are `Activate the mood above. Use that mood as the shape of your responses.` The role is `system`. The line is not written into a cell. The mood glass does not carry this line. The shaping chat stays the shaping chat.

While the switch is off, or no string is saved, the walker skips the mood string and skips this line.

When the encoded body does not fit and the mood line is present, the slide still shows `keyword only. too-large`. The body is assembled again without the mood string and without this line. The saved string and the switch stay on.

The inspector, while the switch is on, shows the mood preview and then `shape:` with this line. The token estimate counts this line. While the switch is off, or no string is saved, the inspector does not show the shape line and the estimate does not count it.

The switch tip, while a string is saved, names the shape line.

The other client's match is a row on section 12, written when this client lands. That slice waits until the catch-up is named. This rule writes no crate.

The line stays off the cell and off the mood glass. The inspector shows the line while the switch is on. The line lands after the mood string. Both lines drop when the body does not fit. The row is the build.

**Truth.** On a canvas send, with the switch on and a mood string saved, the walker sends the host line, then the mood string as `system`, then `Activate the mood above. Use that mood as the shape of your responses.` as `system`. The inspector shows `shape:` and that line, and the token estimate counts it. The line is not stored in a cell and is not on the mood glass.

**Reason.** Setting the mood was not enough. On a fresh canvas the model used the mood only after it was coaxed, against qwen2.5-coder:7b. The shape line is prepended on the send, the same way the mood header is, so the model is told to activate the mood above and use it as the shape of its responses. The shaping chat stays the shaping chat. When the body does not fit, both the mood string and this line are dropped together, the slide shows `keyword only. too-large`, and the saved string and the switch stay on. The switch tip names the shape line while a string is saved so the extra system line is visible before the send.

**Avoidances.** Do not write the shape line into a cell. Do not put the shape line on the mood glass. Do not send the shape line when the switch is off or no string is saved. Do not count the line in the token estimate in those cases. Do not drop only one of the two lines when the body does not fit. Do not turn the switch off or clear the saved string on that drop. Do not write the other client's crate in this rule. Do not open the window, make the live POST, send a pool turn, or make a live SSH connect from this filing.


**semantic-key:** PK-3995 PK-3996 PK-3997 PK-3998 PK-3999 PK-4000 PK-4001 PK-4002 PK-4003 PK-4004

## Closed thinking pane

A thinking pane that the user closed stays closed the next time the application opens.

Close hides the thinking pane and keeps the log. That choice is the root key `closed` in `~/.casserole/aetheris-think.yaml`. Close writes `closed` true. The file version stays 1. A version other than 1 is left unread and is not overwritten.

The next open loads the log. When `closed` is true, the pane stays hidden. When `closed` is absent or false, and the log has text, the pane is shown. An empty log still leaves the pane hidden.

A later thought opens the pane and writes `closed` false. The `v` fold lasts only for the current session and is not written. Clear still wipes the pane and the saved log.

The Close tip says that a closed pane stays closed when the application opens again.

The Java client carries this behavior. The Rust client is not changed on this row. Its matching behavior waits until that catch-up is named.

**Truth.** Close stores `closed` as true at version 1, hides the pane, and keeps the log. The next open honors that flag. A later thought stores `closed` as false and shows the pane. Clear still wipes the pane and the saved log. The Close tip states the reopen rule.

**Reason.** Closing the pane and then closing the application was showing the pane again on the next open. The closed choice has to survive that open, and the log has to remain.

**Avoidances.** Do not store the mark in the vault. Do not drop the log when the pane hides. Do not show the pane when the log is empty. Do not write the session fold. Do not read or overwrite a file whose version is not 1. Do not change the Rust client on this row.


**semantic-key:** PK-4005

## Approve width

The Approve button in the mood dialog is wide enough to show the whole word Approve, with no ellipsis.

The label stays `Approve`. The short fit still sets the height. After that fit, the preferred width and the minimum width each grow by three ems of the button font. The ellipsis no longer replaces the end of the word. Cancel stays on the short fit. The footer buttons stay on the short fit. The tip stays. The click still saves the field.

The Java client carries this behavior. The Rust client is not changed on this row. Its matching behavior waits until that catch-up is named.

**Truth.** Approve keeps its label and its short height, then gains three ems of preferred width and three ems of minimum width from the button font. The full word shows. Cancel and the footer buttons stay on the short fit. The tip and the save on click stay.

**Reason.** The Approve label was being cut off and replaced with an ellipsis. The button has to be wide enough to show the whole word.

**Avoidances.** Do not change the label. Do not change the short height. Do not widen Cancel. Do not widen the footer buttons. Do not drop the tip. Do not stop the click from saving the field. Do not change the Rust client on this row.


**semantic-key:** PK-4006

## Mood stays

A mood survives from one run of the application to the next, the switch follows that saved state, and a yellow line above the inspector shows the mood string only while the switch is on.

The saved string and the switch position live in `~/.casserole/aetheris-mood.yaml`, version 1. The keys are `line` and `on`. The file is not a vault column and not a cell. Export does not carry it. Clear turns leaves it. A missing file means no string and the switch off. A version other than 1 is left unread and is not overwritten.

The Mood button stays enabled. It opens the mood glass. Approve of a non-empty field saves that string, turns the switch on when it was off, and writes the file. An empty field writes nothing. The switch does not call the model.

When a string is saved, a click flips the switch and writes the file. On at exit is on at the next start. Off at exit is off at the next start. When no string is saved, the round button is grey, the switch stays off, and a click does nothing.

A yellow line sits above the inspector. While the switch is on, the line shows the saved string. While the switch is off, the line is not shown.

The switch tip names the grey state, the flip, and that the mood stays. The Mood button tip names that Approve turns the switch on when it was off, and that the string stays.

The Java client carries this behavior. The Rust client is not changed on this row. Its matching behavior waits until that catch-up is named.

**Truth.** Version 1 of `~/.casserole/aetheris-mood.yaml` stores `line` and `on`. Approve of a non-empty field saves the string, turns the switch on if it was off, and writes the file. The switch then flips across restarts. With no saved string the round button is grey, the switch stays off, and a click does nothing. The yellow line above the inspector shows the string only while the switch is on.

**Reason.** The mood lasted only for the application session, so it had to be set again every time. The switch has to come back on if it was on at exit, and off if it was off. Setting a mood in the dialog has to turn the switch on. Until a mood exists, the switch has to stay grey. The string has to be visible above the inspector while the switch is on, and hidden while it is off.

**Avoidances.** Do not store the file as a vault column or a cell. Do not put it in an export. Do not clear it on Clear turns. Do not disable the Mood button. Do not let the switch call the model. Do not write a file for an empty Approve. Do not show the yellow line while the switch is off. Do not read or overwrite a version other than 1. Do not change the Rust client on this row.


**semantic-key:** PK-4007 PK-4008 PK-4009 PK-4010

## Mood rise

When a mood string is set, a pane rises from the top of the mood-dialog footer, shows that text, and takes its height from the wrap.

The resting footer of the mood dialog is the spinner, Approve, and Cancel. The single-line mood field and the Mood string label leave that row.

When a reply sets the mood string, a pane rises from the top of that footer. The rise starts at height 0 and slides to the wrapped height. A later string, or an edit, sets the new height without sliding from zero again. An empty string hides the pane. The pane belongs to this dialog only. It is not the canvas thinking pane. It is not saved.

The height is the wrapped line count at the pane width. One visual line is one line tall. Two visual lines are two lines tall. The text wraps on words. The height stops at half the dialog. Taller text scrolls. A dialog resize measures the wrap again.

The pane stays editable. Approve still reads it. An empty field writes nothing. The field colors stay the field colors.

The pane tip names the rise, the wrap, and that the text can be edited before Approve. The Mood button tip names that the string rises above the dialog footer.

The Java client carries this behavior. The Rust client is not changed on this row. Its matching behavior waits until that catch-up is named.

**Truth.** The resting footer is only the spinner, Approve, and Cancel. A reply that sets a mood string slides a pane up from height 0 to the wrapped height. One visual line is one line tall, and two are two lines tall. Later text changes the height without another slide from zero. An empty string hides the pane. The height never passes half the dialog, and taller text scrolls. The pane stays editable, and Approve still reads it.

**Reason.** The mood string in the footer was hard to inspect and approve. It needs to rise and grow with the wrap, the way a thinking pane shows its text, while the footer itself stays clean.

**Avoidances.** Do not leave the single-line field or the Mood string label on the resting footer. Do not treat this pane as the canvas thinking pane. Do not save the pane. Do not slide from zero again on a later string or an edit. Do not let the pane cover more than half the dialog. Do not write a file for an empty field. Do not change the field colors. Do not change the Rust client on this row.


**semantic-key:** PK-4011 PK-4012 PK-4013 PK-4014 PK-4015 PK-4016 PK-4017 PK-4018 PK-4019

## Dialog size

The mood dialog opens at the size it had when it last closed, whether that close was Approve, Cancel, or the title-bar close.

The size is `width` and `height` on `~/.casserole/aetheris-mood.yaml`, version 1. The file is not a vault column. A missing pair opens the dialog at 760 by 520. A kept width from 480 through 4000, and a height from 320 through 4000, is used. A number outside that range is ignored. The place on screen is not kept. The dialog still centers.

Approve and Cancel write the size, then the dialog closes. The title-bar close uses that same close, so it writes the size too. An empty Approve still writes no mood string. The size is written with the close. A later switch flip keeps the pair. A version other than 1 is left unread and is not overwritten.

Approve and Cancel name that the dialog keeps this size. The Mood button names that the dialog opens at the size it had when it last closed.

The Java client carries this behavior. The Rust client is not changed on this row. Its matching behavior waits until that catch-up is named.

**Truth.** `width` and `height` are stored at version 1 on `~/.casserole/aetheris-mood.yaml`. A missing pair opens at 760 by 520. A width from 480 through 4000 and a height from 320 through 4000 are used. Anything outside that range is ignored. Approve, Cancel, and the title-bar close write the size and then close. The dialog centers. A later switch flip keeps the pair. An empty Approve still writes no mood string.

**Reason.** The dialog was forgetting the size the user left it at. The next open has to match that size, from Approve or from Cancel.

**Avoidances.** Do not store the size as a vault column. Do not keep the place on the screen. Do not use a width outside 480 through 4000 or a height outside 320 through 4000. Do not write a mood string for an empty Approve. Do not drop the size when the switch flips later. Do not read or overwrite a version other than 1. Do not change the Rust client on this row.


**semantic-key:** PK-4020

## Mood tokens

When the user invites a richer mood, the mood control header tells the model to put that detail on the mood line as more tokens, and to say in the reply what it determined.

The mood glass system line tells the model how to fill the mood string. The pluck stays. The last mood line still fills the field. On a canvas send, the shape line stays the line that follows a saved mood string: the system words `Activate the mood above. Use that mood as the shape of your responses.` The mood file stays the mood file.

The system line says that the application stores the mood line. The line is many tokens, separated by commas. Each token is one short meaning. Before the line, the model says what it determined. When the user asks for more detail, a richer mood, more tokens, or an enriched mood, the model says that detail in the reply and puts the same detail on the mood line as more tokens. A short line stays enough when the user did not ask for more.

A missing mood line still asks again. That ask repeats the same rule.

The Mood button tip names that ask.

The Java client carries this behavior. The Rust client is not changed on this row. Its matching behavior waits until that catch-up is named.

**Truth.** The mood system line requires a comma-separated line of short tokens, a statement of what was determined before that line, and the same extra detail on the line when the user asks for more detail, a richer mood, more tokens, or an enriched mood. A short line remains enough when the user did not ask. The last mood line is still plucked into the field. A missing line is asked for again with the same rule. The canvas shape line stays `Activate the mood above. Use that mood as the shape of your responses.`

**Reason.** Asking the mood model for enriched detail was leaving the spoken reply rich and the stored mood line thin. An invitation for more detail has to land on the mood line as more tokens, and the reply still has to say what was determined.

**Avoidances.** Do not drop the pluck. Do not change the canvas shape line. Do not change the mood file. Do not force a long line when the user did not ask for more. Do not use a different rule on the repeat ask. Do not change the Rust client on this row.


**semantic-key:** PK-4021

## Mood session

A saved mood is already in the dialog field, a mood send carries that mood in the header the way a conversation send does, and Clear mood starts from scratch.

The dialog used to open on an empty field and an empty chat. The saved string stayed on the switch and on the next canvas send.

When a saved mood is present, the field already holds that string. The pane rises. The user can edit it, approve it, or ask the model to enhance it. An empty save still opens an empty field.

A mood send keeps the shape line. When the field holds a mood, that string and the activate line follow the shape line, before the chat. The pair is the same pair a canvas send places after the host line: the mood string, then the system line `Activate the mood above. Use that mood as the shape of your responses.` The switch does not gate this chat. An empty field sends no mood tokens. A canvas send still carries the pair only while the switch is on.

Clear mood drops the saved string, turns the switch off, and empties the field and the chat. A reply already in flight stays off the glass. The dialog stays open. The kept size stays.

The Java client carries this behavior. This row does not write the Rust crate. After the behavior was confirmed in use, the Rust catch-up was named, and that crate stays held for the catch-up rather than being written here.

**Truth.** A saved mood opens already in the field, with the pane risen, ready to edit, approve, or enhance. An empty save opens an empty field. A mood send with text in the field places the shape line, then the mood string and the activate line, then the chat. An empty field sends no mood tokens. Clear mood drops the saved string, turns the switch off, and empties the field and the chat, while a reply already in flight stays off the glass, the dialog stays open, and the kept size stays. A canvas send still carries the pair only while the switch is on.

**Reason.** A saved mood has to be in the field so the user can ask the model to enhance it, with the tokens traveling in the header the way a conversation send carries them. Clear mood has to be able to start from scratch.

**Avoidances.** Do not open a saved mood on an empty field. Do not let the switch gate the mood-dialog chat. Do not send mood tokens from an empty field. Do not put the canvas pair on a canvas send while the switch is off. Do not let an in-flight reply land on the glass after Clear mood. Do not close the dialog or drop the kept size on Clear mood. Do not write the Rust crate on this row.


**semantic-key:** PK-4022 PK-4023 PK-4024

## Clear ask

Clear mood asks before it drops the string. The ask says `Clear the Mood Setting?` Yes clears. No leaves the mood. The mood dialog stays open.

Clear mood opens a small ask over the mood dialog. The words are `Clear the Mood Setting?` Yes is green. No is red. Yes runs the clear. No closes the ask. The mood string, the switch, the field, and the chat stay. The mood dialog stays open. Closing the ask, and escape, leave the mood untouched in the same way No does.

The Java client carries this behavior. This row does not write the Rust crate. After the ask was confirmed in use, the Rust catch-up was named, and that crate stays held for the catch-up rather than being written here.

**Truth.** Clear mood opens an ask over the mood dialog with the words `Clear the Mood Setting?` Yes is green and runs the clear. No is red and closes the ask. Close and escape also leave the mood string, the switch, the field, and the chat as they are. The mood dialog stays open.

**Reason.** Clear mood was dropping the string with no chance to refuse. The user needs a yes or a no first, and the dialog has to stay open either way.

**Avoidances.** Do not clear on No, on close, or on escape. Do not close the mood dialog when the ask closes. Do not change the clear that Yes runs. Do not write the Rust crate on this row.


**semantic-key:** PK-4025 PK-4026 PK-4027 PK-4028 PK-4029 PK-4030 PK-4031 PK-4032

## Choice words

Yes and No on the clear ask show their words in full. Yes stays green. No stays red.

The ask stays. The words stay `Clear the Mood Setting?` Yes stays green. No stays red. Each button is wide enough for its word. The bevel and the pad sit outside the word. An ellipsis in place of either word is a miss. Yes still runs the clear.

The Java client carries this behavior. This row does not write the Rust crate. After the words were confirmed in use, the Rust catch-up was named, and that crate stays held for the catch-up rather than being written here. No, close, and escape still leave the mood untouched.

**Truth.** The ask still reads `Clear the Mood Setting?` Yes is green, No is red, and each button is wide enough that the bevel and the pad sit outside the full word. Neither label is replaced by an ellipsis. Yes still clears.

**Reason.** The colored buttons were not showing the words Yes and No. The colors stay, and the words have to be fully visible.

**Avoidances.** Do not drop the green or the red. Do not move the clear off Yes. Do not let an ellipsis replace either word. Do not put the bevel or the pad on top of the word. Do not write the Rust crate on this row.


**semantic-key:** PK-4033

## Pin bar

The pin box, the word field, and OK sit on a thin bar at the top of the canvas pane.

The inspector header keeps the Inspector label, soft wrap, and the two counters. The tips stay on the three pin controls. OK writes the states. The click does not open Mood. The OK tip stays the sentence already on the button. The edge paint stays in the chrome paint. Nothing beside this bar moves. The cells already on the canvas stay as they are.

The pin behavior is already recorded for the Rust client. This row writes no Rust crate.

**Truth.** A thin bar at the top of the canvas pane holds the pin box, the word field, and OK. OK writes the pin states and does not open Mood. The inspector header keeps its label, soft wrap, and two counters. The three tips stay, and the OK tip keeps the sentence already on the button.

**Reason.** The pin controls had to sit where they can be seen, on the canvas, before any matching Rust work.

**Avoidances.** Do not move the write off OK. Do not thicken the bar. Do not move soft wrap or the two counters off the inspector header. Do not open Mood from the OK click. Do not change the OK tip. Do not move the edge paint out of the chrome paint. Do not write a Rust crate on this row. Do not move any other control.


**semantic-key:** PK-4034 PK-4035 PK-4036 PK-4037

## Pin tuck

A folded pin bar leaves the canvas. P brings it back. Folding does not change the pin box.

`v` on the open bar folds the whole bar off the canvas. The cards use the height they had before the pin bar. P sits on the east of the canvas tab header while the bar is folded, the way C sits on the thinking grip. P adds no row. P shows the bar. `v` stays `v`. Folding does not uncheck the pin. Only the pin box turns the filter off. The words stay until the user changes them. The choice is saved with the shell. A missing key stays folded. The OK tip stays the sentence already on the button. The edge paint stays in the chrome paint. Nothing beside this tuck moves. The cells already on the canvas stay as they are.

The pin behavior is already recorded for the Rust client. The crate change for the tuck waits until that catch-up is named. This row writes no Rust crate.

**Truth.** `v` removes the whole pin bar from the canvas, and the cards take back the height the bar had used. While the bar is folded, P sits on the east of the canvas tab header, overlaid, with no added row, and P shows the bar again. Folding does not uncheck the pin. Only the pin box turns the filter off. The words, the saved fold, and a missing key stay as they were: saved with the shell, missing means folded.

**Reason.** A folded bar was still taking space. It needs to leave the canvas, with P tucked where it can be seen, and folding must not turn the pin off.

**Avoidances.** Do not leave a folded bar on the canvas. Do not add a row for P. Do not change `v` into another mark. Do not uncheck the pin by folding. Do not give any control but the pin box the power to turn the filter off. Do not drop the words on a fold. Do not treat a missing key as open. Do not write the Rust crate on this row.


**semantic-key:** PK-4046 PK-4047 PK-4048 PK-4049

## Mark face

P and the thinking-pane C wear the sapphire of an armed card, a one-pixel white bevel, and a white letter.

P and the thinking-pane C wear that armed-card sapphire. A one-pixel white bevel surrounds each painted surface. The letter is white. P stays on the east of the canvas tab header, overlaid, with no added row. That seat is the pattern to keep. P shows while the pin bar is folded. C stays on the east of the thinking grip and still clears the log. The other short buttons keep the theme bevel. The cell-state P stays on the theme surface. Folding leaves the pin box as it is. The words stay. The edge paint stays in the chrome paint. Nothing beside these two marks moves. The cells already on the canvas stay as they are.

The pin behavior and the thinking behavior are already recorded for the Rust client. The crate change for this painted surface waits until that catch-up is named. This row writes no Rust crate.

**Truth.** The pin P and the thinking-pane C are armed-card sapphire, with a one-pixel white bevel and a white letter. P stays overlaid on the east of the canvas tab header and shows only while the pin bar is folded. C stays on the east of the thinking grip and still clears the log. Other short buttons, including the cell-state P, keep the theme bevel and the theme surface.

**Reason.** P and C need to read as sapphire marks with a thin white bevel and a white letter, without moving the seat of P.

**Avoidances.** Do not move P off the east of the canvas tab header. Do not add a row for P. Do not move C off the thinking grip or stop it from clearing the log. Do not put the sapphire and white bevel on the other short buttons. Do not put them on the cell-state P. Do not uncheck the pin by folding. Do not drop the words. Do not write the Rust crate on this row.


**semantic-key:** PK-4050

## Pin edge

While the pin box is on, a readable match wears green and a readable miss wears dim, and an armed card stays sapphire. The paint is the paint the cards already use.

While the pin box is on, a readable match is `#3cb371` and a readable miss is `#222222`. The armed card, and every card in a group whose merge is armed, is `#2e86ff`. That sapphire wins over the pin color. The paint stays in the chrome paint. This row writes no new Java. The inner prompt border and the inner reply border stay their own green line. The pin box stays the only off switch. The words stay. The seat of P stays. The painted surface of P and of C stays. Nothing beside this edge moves. The cells already on the canvas stay as they are.

The pin behavior is already recorded for the Rust client. The crate change for the edge waits until that catch-up is named. This row writes no Rust crate.

**Truth.** With the pin box on, a readable match is `#3cb371` and a readable miss is `#222222`. An armed card, and every card in a group whose merge is armed, is `#2e86ff`, and that sapphire replaces the pin color. The chrome paint already does this. No new Java is written for the edge. Prompt and reply borders keep their own green line.

**Reason.** The pin edge is the card paint that already exists. A readable match has to be green, a readable miss has to be dim, and the armed card, including an armed merge, has to stay sapphire.

**Avoidances.** Do not invent a second edge color. Do not let the pin color cover `#2e86ff` on an armed card or on a card in a group whose merge is armed. Do not move the paint out of the chrome paint. Do not recolor the inner prompt or reply borders. Do not add another off switch beside the pin box. Do not move P or repaint P and C. Do not write new Java, and do not write a Rust crate, on this row.

**semantic-key:** PK-6253

## Expanded view

Each readable cell carries an Expanded View checkbox. Checked, the reply shows all of its text inside the text-pane border. Unchecked, the reply stays at the footer row count, the same height as the prompt.

The checkbox reads `Expanded View`. It sits on the readable cell header after the collapse control and before the burner checkbox. An unreadable card has no box. Checked, the reply text pane grows to the wrapped buffer and shows all of the text inside its border. A text change and a window resize refit a checked reply. Unchecked, the reply stays at the saved view-row count, the same height as the prompt, and the text scrolls inside. The footer count sets that height. The prompt keeps that count. A folded card stays a brief line. The checkbox still shows and keeps the choice. Opening the card honors the choice.

A fresh canvas, a vault open, and a disconnect reload start unchecked. Footer Clear leaves one empty card unchecked. A new turn copies the prior cell. An empty deck stays unchecked. A duplicated group starts checked. Import, Pull, and a merge cell start unchecked. The choice stays for this window. It is not saved. It is not a vault column and not an export field. The collapse control stays the saved fold.

The Java client carries this behavior. The Rust client already paints Expanded View. This row writes no Rust crate. Nothing beside this checkbox moves. The cells already on the canvas stay as they are. Use confirmed the checkbox.

**Truth.** `Expanded View` sits on a readable cell header after the collapse control and before the burner checkbox. Checked, the reply grows to the wrapped buffer and shows every line inside its border, and a text change or a resize refits it. Unchecked, the reply keeps the saved view-row count, matching the prompt, and scrolls. A folded card stays one brief line while the checkbox keeps the choice. A fresh canvas, a vault open, a disconnect reload, footer Clear, an empty deck, Import, Pull, and a merge cell start unchecked. A new turn copies the prior cell. A duplicated group starts checked. The choice lasts for this window only.

**Reason.** A readable cell needs a way to show the whole reply inside the border, and a way to sit back at the same height as the prompt. A new canvas and Clear start unchecked. A new turn keeps the prior cell's choice.

**Avoidances.** Do not put the checkbox on an unreadable card. Do not save the choice, and do not make it a vault column or an export field. Do not change the saved fold. Do not grow a folded card past a brief line. Do not start a fresh canvas, a vault open, a disconnect reload, footer Clear, an empty deck, Import, Pull, or a merge cell checked. Do not start a duplicated group unchecked. Do not write a Rust crate on this row.


**semantic-key:** PK-4051 PK-4052 PK-4053 PK-4054 PK-4055 PK-4056 PK-4057 PK-4058 PK-4059 PK-4060 PK-4061

## Cell stamp

The focused cell wears the local time and the short model name on the top stroke of its outer frame. The line is painted across the border. There is no north pad.

The line is centered on that stroke and seated on the right, eight pixels in. The words are `── stamp · model ──`. A blank model keeps the stamp. The stamp is local wall time: year, month, day, hour, minute, second. The clock ticks once a second. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. An armed merge keeps the sapphire line. The line stays on the focused cell. Folded or open, the seat is that outer frame. The hover says the line is the model this turn will call. The model line leaves the header.

The desktop client draws the line on top of the border. The cell keeps its width. The buttons, the prompt, and the reply keep their size. There is no north pad. There is no first-cell lead. There is no inner button pad. The input pane border stays a plain stroke.

The Java client carries this behavior. The Rust client already paints the embedded line and the first-cell lead. This row writes no Rust crate. The Rust lead stays on the Rust client. The cells already on the canvas stay as they are. The line is not stored in the vault.

**Truth.** Only the focused cell paints `── stamp · model ──` on the top stroke of its outer frame, centered on that stroke and eight pixels in from the right. The stamp is local wall time to the second and ticks once a second. A blank model keeps the stamp. A wide line keeps the right end. A quiet cell has a plain frame. An armed merge keeps the sapphire line. Folded or open, the line sits on that outer frame, drawn on top of the border, with no north pad, no first-cell lead, and no inner button pad. The hover says the line is the model this turn will call. The model line is gone from the header. Widths and the input-pane stroke stay.

**Reason.** The model name and the timestamp have to ride on the Java cell the way the Rust cell wears them, adapted to this toolkit. The paint crosses the border. It does not take a north pad.

**Avoidances.** Do not put the line in the header or in the vault. Do not pad the north, the first cell, or the inner buttons. Do not change the cell width or the size of the buttons, the prompt, or the reply. Do not paint the line on a quiet cell. Do not drop the sapphire line of an armed merge. Do not tick slower or faster than once a second. Do not drop the stamp when the model is blank. Do not clip the right end of a wide line. Do not write a Rust crate on this row. Do not move the Rust first-cell lead.


**semantic-key:** PK-4062 PK-4063 PK-4064 PK-4065

## Clean rule

The rule beside the stamp is a stroke, not a character. The text carries no box rule. The line still reads as the stamp and the model.

The focused cell still wears the local stamp and the short model on the top stroke of its outer frame. The line is centered on that stroke and seated on the right, eight pixels in. The words are the stamp and the model. A middle dot sits between them. A blank model keeps the stamp. Each flank is a one-pixel stroke, two ems long, the span the box rule used to take. One space of air sits between the stroke and the words. The stroke uses the caption ink and sits on the border. The text carries no box rule. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. An armed merge keeps the sapphire line. The line stays on the focused cell. The hover stays the line is the model this turn will call. The cell keeps its width. The buttons, the prompt, and the reply keep their size. There is no north pad. There is no first-cell lead. There is no inner button pad. The input pane border stays a plain stroke.

The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this difference waits until it is named. Nothing beside this stroke moves. The cells already on the canvas stay as they are. The line is not stored in the vault. The clock still ticks once a second.

**Truth.** The focused cell's caption is the stamp, a middle dot, and the model, with no box-drawing characters. Each side is a one-pixel stroke two ems long, in the caption ink, on the border, with one space between the stroke and the words. The line stays centered on the top stroke, eight pixels in from the right. A blank model keeps the stamp. A wide line keeps the right end. Quiet cells stay plain. An armed merge keeps the sapphire line. The hover still says the line is the model this turn will call.

**Reason.** The rule beside the stamp was a character, and it has to be a stroke painted in the graphics so the line stays clean and still reads the way the other client's cell reads.

**Avoidances.** Do not put a box rule back into the text. Do not move the line off the border or out of the caption ink. Do not change the eight-pixel inset, the two-em flanks, or the one-space gap. Do not store the line in the vault. Do not add a north pad, a first-cell lead, or an inner button pad. Do not change the clock from one second. Do not write a Rust crate on this row. Do not replace the Rust character rule until that change is named.


**semantic-key:** PK-4066

## Sapphire stroke

The stroke beside the stamp wears the selection sapphire. The words stay the caption ink.

The focused cell still wears the local stamp and the short model on the top stroke of its outer frame. The line is centered on that stroke and seated on the right, eight pixels in. The words are the stamp and the model. A middle dot sits between them. A blank model keeps the stamp. Each flank is a one-pixel stroke, two ems long. One space of air sits between the stroke and the words. The stroke wears the selection sapphire, `#2e86ff`, the same color the armed card already paints. The words stay the caption ink. The text carries no box rule. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. An armed merge keeps the sapphire line. The line stays on the focused cell. The hover stays the line is the model this turn will call. The cell keeps its width. The buttons, the prompt, and the reply keep their size. There is no north pad. There is no first-cell lead. There is no inner button pad. The input pane border stays a plain stroke.

The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this difference waits until it is named. Nothing beside this color moves. The cells already on the canvas stay as they are. The line is not stored in the vault. The clock still ticks once a second. Use confirmed the sapphire stroke.

**Truth.** Each flank of the stamp line is a one-pixel stroke, two ems long, in the selection sapphire `#2e86ff`. The stamp, the middle dot, and the model stay in the caption ink. The rest of the stamp seat is unchanged: eight pixels in from the right, no box rule in the text, the right end kept when the line is wide, a plain frame on a quiet cell, and the sapphire line kept on an armed merge.

**Reason.** The stroke beside the stamp has to wear the same sapphire as the selection. The words stay in the caption ink.

**Avoidances.** Do not paint the words in sapphire. Do not put a box rule back into the text. Do not store the line in the vault. Do not add a north pad, a first-cell lead, or an inner button pad. Do not change the clock from one second. Do not write a Rust crate on this row. Do not replace the Rust character rule until that change is named.


**semantic-key:** PK-4067

## Scroll focus

A click on a text pane puts scroll focus on that pane. A click outside that pane returns scroll focus to the canvas. Hover on a cell shows the stamp the way a selected cell paints it.

A click on the prompt or the reply, including its scrollbar, puts scroll focus on that text. The wheel then scrolls that pane and stops at its end. A click anywhere else on the card returns scroll focus to the canvas. The armed cell stays armed. A click on the canvas padding arms nothing and returns scroll focus to the canvas. The wheel over a text pane scrolls that pane only while that pane owns scroll focus. Otherwise the wheel scrolls the canvas. A folded brief is not a text pane. A click on it returns scroll focus to the canvas. The execution target keeps the caret.

Hover anywhere on a cell shows the stamp and the short model, the same words a selected cell paints. A blank model keeps the stamp. The clock is the moment of the hover. A quiet cell still paints no line. The focused line keeps its hover: the line is the model this turn will call. Buttons keep their own hover.

The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this difference waits until it is named. Nothing beside this focus moves. The cells already on the canvas stay as they are. The line is not stored in the vault. Use confirmed the scroll focus.

**Truth.** A click on the prompt, the reply, or either scrollbar gives that pane the wheel until the wheel reaches the pane's end. Any other click on the card, a click on a folded brief, or a click on the canvas padding returns the wheel to the canvas. Padding arms nothing. The armed cell stays armed, and the execution target keeps the caret. Hover anywhere on a cell shows the stamp and the short model at the moment of the hover. A blank model keeps the stamp. A quiet cell paints no line. The focused cell's own hover still says the line is the model this turn will call. Buttons keep their own hover.

**Reason.** Scroll focus was sticking inside a text pane. A click outside that pane has to give the wheel back to the canvas. Hover has to show the same stamp a selected cell paints.

**Avoidances.** Do not let the wheel scroll the canvas while a text pane owns scroll focus. Do not let the wheel pass the end of the focused pane. Do not treat a folded brief as a text pane. Do not arm a cell from a click on the canvas padding. Do not move the caret off the execution target. Do not paint a line on a quiet cell. Do not store the line in the vault. Do not replace a button's own hover. Do not write a Rust crate on this row.


**semantic-key:** PK-4068 PK-4069 PK-4070 PK-4071 PK-4072 PK-4073 PK-4074 PK-4075 PK-4076 PK-4077 PK-4078 PK-4079

## The rounded cell

The canvas cells wear the rounded corners of the Rust cells: the cell as a whole, the input pane, and the reply pane. The selection and the paint stay.

The cell corner is a round of 6. The stroke is 1 pixel and sits inside the cell. The color stays the color the cell already paints. An armed cell, and a card lit with its merge, is sapphire. A pin match is green. A pin miss is dim. A ghost is dark gray. A quiet cell is the quiet gray. The pad inside the stroke stays 6. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons, the prompt, and the reply keep their size.

The prompt and the reply use that same round of 6. The prompt line stays green and 2 pixels. A reply that has text stays green and 2 pixels. A reply with no text stays gray and 2 pixels. The line sits inside the pane. The pane clips its children to the round so the plate follows the stroke. The field stays the field. The overlay scroll bar stays.

The Rust client paints the cell with this round of 6 and this 1 pixel inside stroke. The toolkit at egui 0.37 paints the panes with the inactive corner, and that corner is 6. The inactive stroke width is 0. Java keeps the green and the gray on the panes. Those are the paint mechanics the panes already use. The stamp on the top stroke stays the earlier reading. This row rounds the corner.

A collapsed card rounds the cell. It has no panes. A merge sink has no prompt pane. Its reply rounds. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays.

There is no new package, no new public type, and no new public method. The corner is painted by a private edge. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this corner moves. The send set stays as it is. The Java client carries this. The Rust client stays.

**Truth.** The cell corner is a round of 6 with a 1 pixel stroke inside the cell. Armed and merge-lit cards stay sapphire, a pin match stays green, a pin miss stays dim, a ghost stays dark gray, and a quiet cell stays the quiet gray. The inner pad stays 6 and the caption inset stays 8, with no north pad. The prompt and the reply use the same round of 6. The prompt line, and a reply that has text, stay green and 2 pixels. A reply with no text stays gray and 2 pixels. The line sits inside the pane, and the pane clips its children to the round. A collapsed card rounds the cell and has no panes. A merge sink has no prompt, and its reply rounds. The error slide stays square. Rust already uses this round of 6 and this 1 pixel inside stroke. At egui 0.37 the panes use the inactive corner of 6, and the inactive stroke width is 0.

**Reason.** The Java cells have to take the rounded corners the Rust cells already wear, on the cell, the input pane, and the reply pane, without changing the selection or the paint.

**Avoidances.** Do not change the selection colors. Do not move the caption off the top stroke or change the inset from 8. Do not add a north pad. Do not change the cell width or the size of the buttons, the prompt, or the reply. Do not change the green and gray line weights from 2 pixels. Do not round the error slide, the footer, the mood glass, or the thinking strip. Do not give a collapsed card panes, or a merge sink a prompt pane. Do not add a color key, a library, a public method, a package, a global key, a vault column, a channel, or a wire message type. Do not edit a settings file. Do not change the Rust client.


**semantic-key:** PK-4080 PK-4081 PK-4082 PK-4083

## The round field

The dark field on the prompt and on the reply matches the Rust corner. The text panes stop looking squarer than the Rust render.

The dark field on the prompt and on the reply is a round of 8. That is the Rust corner of 6 points at the open zoom of 1.3, taken to the nearest pixel. The line stays 2 pixels. It follows that same curve. The outer stroke radius is 10, so the inside edge meets the field. The prompt line stays green. A reply that has text stays green. A reply with no text stays gray. The field stays the field. The pane keeps its size. The overlay scroll bar stays a sibling of the field.

The cell stays a round of 6. The cell stroke stays 1 pixel. The pad inside that stroke stays 6. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons keep their size.

The Rust client fills the plate at the inactive corner of 6 and scales it by the open zoom. The inactive stroke width is 0. Java keeps the green and the gray on the panes. The cell round and the stamp stay the earlier reading.

A collapsed card has no panes. A merge sink has no prompt pane. Its reply takes this field round. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays.

There is no new package, no new public type, and no new public method. The private edge painter stays. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this field moves. The send set stays as it is. The Java client carries this. The Rust client stays.

**Truth.** The prompt field and the reply field are a round of 8, the Rust corner of 6 points at an open zoom of 1.3, rounded to the nearest pixel. The line stays 2 pixels and follows that curve. The outer stroke radius is 10, so the inside edge meets the field. Green stays on the prompt and on a reply that has text. Gray stays on an empty reply. The cell itself stays a round of 6 with a 1 pixel stroke, an inner pad of 6, and a caption inset of 8. A merge reply takes this field round. The error slide stays square.

**Reason.** Rounding the pane stroke to 6 still left the text plates looking squarer than the Rust render. The field has to match that corner.

**Avoidances.** Do not leave the field at a round of 6. Do not change the line from 2 pixels. Do not use an outer stroke radius other than 10. Do not change the cell from a round of 6 or its stroke from 1 pixel. Do not move the caption or add a north pad. Do not change the green or the gray. Do not change the field color or the pane size. Do not pull the overlay bar off its place as a sibling of the field. Do not round the error slide, the footer, the mood glass, or the thinking strip. Do not give a collapsed card panes, or a merge sink a prompt pane. Do not change the Rust client.


**semantic-key:** PK-4084 PK-4085 PK-4086 PK-4087

## The straight bevel

The straight bevel leaves the text panes. The round field stays.

The prompt and the reply sit in one split. The split puts a straight bevel back on after the border is cleared. This plate clears that border once the interface is on. The straight line leaves. The round field stays a round of 8. The line stays 2 pixels and follows that curve. The prompt line stays green. A reply that has text stays green. A reply with no text stays gray. The field stays the field. The pane keeps its size. The overlay scroll bar stays. The grab bar stays.

The cell stays a round of 6. The cell stroke stays 1 pixel. The pad inside that stroke stays 6. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons keep their size. The canvas split stays. A terminal split stays.

The Rust client stays. The field round, the cell round, and the stamp stay the earlier reading.

A collapsed card has no panes. A merge sink has no prompt pane. Its reply keeps the round field. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays.

There is no new package, no new public type, and no new public method. The private edge painter stays. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this clear moves. The send set stays as it is. The Java client carries this. Use confirmed that the straight bevel is gone. The border is cleared once.

**Truth.** After the interface is on, the straight bevel the split puts back on the prompt and the reply is cleared once. The field stays a round of 8, the line stays 2 pixels on that curve, and the green and gray stay. The grab bar stays. The cell stays a round of 6 with a 1 pixel stroke, an inner pad of 6, and a caption inset of 8. The canvas split stays. A terminal split stays. The error slide stays square.

**Reason.** A straight bevel was still sitting on the text panes after the round field was painted. That line has to go, and the round field has to remain.

**Avoidances.** Do not clear the border before the interface is on. Do not clear it more than once. Do not drop the round field, the 2 pixel curve, the grab bar, the canvas split, or a terminal split. Do not round the error slide, the footer, the mood glass, or the thinking strip. Do not change the Rust client. Do not add a package, a public type, a public method, a global key, a vault column, a channel, a wire message type, a color key, or a library. Do not edit a settings file.


**semantic-key:** PK-4088

## The short bar

The cell bar takes the quiet grey and is ten pixels shorter. The panes keep their width. The arrows stay.

The light line on each edge of the cell bar takes the quiet grey. The grey is `#0a0a0a`. Ten is darker than the panel at `#121212` and lighter than the black at `#000000`. The bar is ten pixels shorter than the panes, five on each side. The panes keep their width. The split stays a split. The divider thickness stays 11. The one-touch arrows stay. Drag stays. The other split bars stay. The cell rounds stay. The cell split stays clear.

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The green line and the gray line stay. The round field stays a round of 8. The cell stays a round of 6. The overlay scroll bar stays. Yes and No stay. P and C stay. The thinking clear mark stays.

The Rust client stays. The quiet edges and the quiet lip stay the earlier reading.

There is no new package, no new public type, and no new public method. The short bar is seated in private. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this bar moves. The send set stays as it is. The Java client carries this. Use confirmed the short bar.

**Truth.** Each edge of the cell bar is `#0a0a0a`. The bar is ten pixels shorter than the panes, five on each side. The panes keep their width. The split stays a split. The divider stays 11 pixels thick. The one-touch arrows stay, and drag stays. Other split bars stay. The cell split stays clear. The field stays a round of 8 and the cell a round of 6. Ink, selection, sapphire, and both find painters stay.

**Reason.** The cell bar was still wearing a light edge, and it was as wide as the panes. It has to take the quiet grey and sit ten pixels shorter, without narrowing the panes or losing the arrows.

**Avoidances.** Do not leave the light edge. Do not shorten the bar by any count but ten, or by an uneven split other than five on each side. Do not change the pane width. Do not change the divider from 11. Do not drop the one-touch arrows or drag. Do not restyle the other split bars. Do not fill the cell split. Do not change the Rust client. Do not add a color key, a library, a public method, a package, a global key, a vault column, a channel, or a wire message type. Do not edit a settings file.


**semantic-key:** PK-4089 PK-4090 PK-4091 PK-4092

## The folded row

A folded thinking row holds the fold, C, and Close. The inspector folds on its own bar, with P on that bar. The SSH, Aetheris, and Canvas tabs wear the plate.

A folded thinking strip keeps one row. Left to right, the fold mark, the drag line, C, and Close. The line is the grip ink from the mark to C. A drag while the strip is folded stores the height. The row stays one row. An open strip keeps the mark and Close on the text row, and C on the east of the grip. The notebook strip and the VT100 strip both do this.

The canvas and the inspector sit in a vertical fold. The bar is 11 pixels. The fill is `#3a3a3a`. The edge is `#8a8a8a`. The upper arrow hides the canvas. The lower arrow hides the inspector. A click on the hidden side restores the parked share. P sits on the top of that bar and moves with a drag. The pin tip wears the sentence the Rust pin already shows. The facts line stays where it sits. The per-mille save stays.

The SSH tab, the Aetheris tab, and the Canvas tab wear the plate. Selected is `#005c80` with word `#c0deff`. Idle is `#323232` with word `#d7d7d7`. Hover is `#404040` with stroke `#969696` and word `#f5f5f5`. The corner is 6. The strip is `#0d0d0d`. The content frame stays off. A disabled tab wears half ink. The folded SSH and Aetheris buttons wear the same plate. The words stay SSH, Aetheris, and Canvas.

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The green line and the gray line stay. The cell split stays clear. The round field stays a round of 8. The cell stays a round of 6. The overlay scroll bar stays. The cell bar stays the short quiet bar. The other split bars stay. Yes and No stay. P and C stay. The thinking clear mark stays. The pin box, the field, Clear, and OK stay. The facts line stays. The Ocean edges stay. The bevel stays.

The Rust client stays. The short bar and the quiet edges stay the earlier reading.

There is no new package and no new public type. The public methods are `installPlateTabs` and `installPlateButton`. The private work seats the folded row, detaches, seats the pin, drags by a delta, hides the canvas, hides the inspector, places the pin, and notes that the divider moved. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this row, this bar, and these tabs moves. The send set stays as it is. The Java client carries this.

**Truth.** A folded thinking strip, on both the notebook and the VT100, is one row: fold mark, grip-ink drag line, C, and Close. A drag while folded stores the height and the row stays one row. Open, the mark and Close sit on the text row and C sits on the east of the grip. The canvas and inspector divider is 11 pixels, fill `#3a3a3a`, edge `#8a8a8a`. The upper arrow hides the canvas. The lower arrow hides the inspector. A click on the hidden side restores the parked share. P sits on top of that bar and moves with a drag. The pin tip uses the Rust pin sentence. The facts line and the per-mille save stay. SSH, Aetheris, and Canvas use the plate: selected `#005c80` / `#c0deff`, idle `#323232` / `#d7d7d7`, hover `#404040` / stroke `#969696` / word `#f5f5f5`, corner 6, strip `#0d0d0d`. The content frame stays off. A disabled tab wears half ink. The folded SSH and Aetheris buttons wear the same plate. The words stay SSH, Aetheris, and Canvas.

**Reason.** The folded thinking strip needed one row that still holds the fold, C, and Close. The inspector needed its own fold bar with P on it. The SSH, Aetheris, and Canvas tabs needed the plate.

**Avoidances.** Do not grow a folded thinking strip past one row. Do not drop the stored height when that row is dragged. Do not move C off the east of the grip while the strip is open. Do not swap the arrows: upper hides the canvas, lower hides the inspector. Do not leave a hidden side unrestored on a click. Do not leave P behind when the bar is dragged. Do not move the facts line or drop the per-mille save. Do not turn the content frame on. Do not change the tab words. Do not change the ink, the short quiet cell bar, the pin box, the field, Clear, OK, the Ocean edges, or the bevel. Do not change the Rust client. Do not add a color key, a library, a package, a public type, a global key, a vault column, a channel, or a wire message type. Do not edit a settings file.


**semantic-key:** PK-4093 PK-4094 PK-4095 PK-4096 PK-4097 PK-4098 PK-4099 PK-4100 PK-4101 PK-4102 PK-4103 PK-4104 PK-4105 PK-4106

## The open thought

The thinking text has no border. The white line around that pane is gone.

The scroll frame drops after the overlay scroll bar is seated. The viewport border drops. The viewport ground is the field, `#1a1a1a`. The text margin stays. The notebook strip and the VT100 strip both do this. The Rust client already paints that text with no frame.

The grip stays. The fold stays. The folded row stays. Close stays. C stays. The black line on C stays. The cell panes stay. The overlay scroll bar stays. The round field stays. Font colors stay. Selection highlights stay. The emerald word stays. The tooltip wait stays.

There is no new package, no new public type, and no new public method. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, and no settings-file edit. Nothing beside this border moves. The send set stays as it is. The Java client carries this. The Rust client stays.

**Truth.** The thinking text has no border and no white line. After the overlay bar is seated, the scroll frame drops and the viewport border drops. The viewport ground is `#1a1a1a`. The text margin stays. Both the notebook strip and the VT100 strip do this. The grip, the fold, the folded row, Close, C, and the black line on C stay. Rust already paints the text with no frame and does not change.

**Reason.** A white line was ringing the thinking text. That border has to go, on both strips, without moving the grip or the buttons.

**Avoidances.** Do not leave the white line or the scroll frame. Do not drop the frame before the overlay bar is seated. Do not change the viewport ground from `#1a1a1a`. Do not drop the text margin. Do not do this on only one of the two strips. Do not move the grip, the fold, the folded row, Close, C, or the black line on C. Do not change the cell panes, the round field, the emerald word, or the tooltip wait. Do not change the Rust client. Do not add a color key, a library, a public method, a package, a global key, a vault column, a channel, or a wire message type. Do not edit a settings file.


**semantic-key:** PK-4107

## The pin above the divider

The P button stays painted on top of the inspector divider.

The letter stays P. The divider stays 11 pixels. The arrows stay. A repaint of those arrows paints the bar, and the mark paints after the bar. The mark stays at z-order 0. The inspector fold returns false from optimized drawing, so a child repaint that meets the mark includes the mark. Placing the pin keeps that z-order.

There is no new package, no new public type, and no new public method. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, no settings-file edit, and no dependency-manifest edit. Nothing beside this order moves. The send set stays as it is. The Java client carries this. The Rust client stays. This row is not a separate feature. The conversation transfer stays closed.

**Truth.** P stays the letter, painted after the inspector bar, at z-order 0. The divider stays 11 pixels and the arrows stay. The inspector fold does not use optimized drawing, so a child repaint that meets the mark includes the mark. Placing the pin keeps z-order 0.

**Reason.** The P button was disappearing under the inspector divider. It has to stay painted on top of that bar.

**Avoidances.** Do not change the letter from P. Do not change the divider from 11 pixels. Do not drop the arrows. Do not paint the mark before the bar. Do not move the mark off z-order 0. Do not turn optimized drawing on for the inspector fold. Do not lose that z-order when the pin is placed. Do not change the Rust client. Do not reopen the conversation transfer. Do not add a package, a public type, a public method, a global key, a vault column, a channel, a wire message type, a color key, or a library. Do not edit a settings file or a dependency manifest.


**semantic-key:** PK-4108

## The thinking face

The thinking pane output matches the application font. The first and second canvas button bars grow with that font. The thinking body wears the lighter sapphire.

The output takes the application point size. A change of that size writes the face onto text already in the pane and onto the next run. The three-line height follows the size. The body is selection sapphire lightened 33 percent toward white. The bytes are 115, 174, and 255. The notebook strip and the VT100 strip both wear it. The model tag stays cyan. Card replies stay yellow.

The first bar holds Run, Stop, New turn, Clear turns, Preview, the row count, Delete, Collapse All, Expand All, and the tool fold. The second bar holds Search, Export, Import, Pull, To terminal, Mood, Hide Collapsed, and Show Collapsed. Those buttons keep the application point size. Their height is the preferred height at that size. Other short buttons stay on the short fit.

The face is seated when the body font is set. There is no new package, no new public type, and no new public method. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, no settings-file edit, and no dependency-manifest edit. Nothing beside this face moves. The send set stays as it is. The Java client carries this. The Rust client stays. This row is not a separate feature. The conversation transfer stays closed.

**Truth.** Thinking output uses the application point size, including text already in the pane, and the three-line height follows that size. The body is selection sapphire lightened 33 percent toward white, bytes 115, 174, and 255, on both the notebook strip and the VT100 strip. The model tag stays cyan. Card replies stay yellow. Run, Stop, New turn, Clear turns, Preview, the row count, Delete, Collapse All, Expand All, the tool fold, Search, Export, Import, Pull, To terminal, Mood, Hide Collapsed, and Show Collapsed keep the application point size, and their height is the preferred height at that size. Other short buttons stay on the short fit.

**Reason.** The thinking output was not following the application font. The two canvas button bars have to grow tall enough for that size. The thinking body has to wear the lighter sapphire again.

**Avoidances.** Do not leave existing thinking text on the old size. Do not size the three-line height from any font but the application font. Do not use any body color but bytes 115, 174, and 255, the selection sapphire lightened 33 percent toward white. Do not wear it on only one strip. Do not change the model tag from cyan or card replies from yellow. Do not leave either button bar on a height other than the preferred height at the application point size. Do not move the other short buttons off the short fit. Do not change the Rust client. Do not reopen the conversation transfer. Do not add a package, a public type, a public method, a global key, a vault column, a channel, a wire message type, a color key, or a library. Do not edit a settings file or a dependency manifest.


**semantic-key:** PK-4109 PK-4110 PK-4111

## The shorter bar

The two canvas button bars are 2 pixels under their preferred height.

Those bar buttons keep the application point size. The height is the preferred height, then minus 2, and the floor is 1. Other short buttons stay on the 70 percent path. The font does not shrink.

The Swing client changes. The Rust client stays.

**Truth.** Canvas bar buttons use the application point size and a height of preferred minus 2, never below 1. Buttons that are not on those bars stay on the 70 percent path. The font is not reduced to make the bar shorter.

**Reason.** The preferred height was one step too tall for the bars, and shrinking the font would have clipped the words instead of the padding.

**Avoidances.** Do not shrink the font. Do not put the other short buttons on this minus-2 path. Do not let the height fall below 1. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4112

## The canvas lip

The white line under the Canvas tab is replaced by the dark quiet bevel.

That line is the pane between the Canvas tab and the scroll pane of the cells. It was a white highlight, a bevel or an inset. After the overlay scroll bar is seated, the scroll under that tab takes the quiet bevel. The lip is 0x0a0a0a. The shadow is black. The viewport border drops.

`applyCanvasScroll` is a new public method on the existing theme. The Swing client changes. The Rust client stays.

**Truth.** The canvas scroll lip is 0x0a0a0a over a black shadow, the quiet bevel, with no viewport border. It is applied after the overlay scroll bar is seated.

**Reason.** The stock highlight was a white inset between the Canvas tab and the cells, which breaks the dark ground.

**Avoidances.** Do not leave the white highlight, the light bevel, or the light inset. Do not keep the viewport border. Do not apply the bevel before the overlay scroll bar is seated. Do not change the Rust client. Do not add a package, a public type other than the method on the existing theme, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4113 PK-4114

## The token ring counts Included cells

The token ring counts only the active cells in a canvas session. Active means Included.

An Excluded cell stays out. An input-only cell stays out. An output-only cell stays out. A merge counts an Included member prompt, and it counts the merge reply only when that merge is Included. The inspector list stays. The mood line stays, and it is not a cell. The Rust token ring stays as it is. This rule does not change the Rust client.

The private helpers are `tokenActive`, `activeMemberPrompts`, `appendActiveMergeTokens`, and `appendActiveGroupTokens`. The Swing client changes.

**Truth.** The ring sums Included cells only. Excluded, input-only, and output-only cells are out. A merge adds an Included member's prompt, and adds the merge reply only when the merge itself is Included. The mood line is not a cell. The inspector list stays.

**Reason.** The ring was counting every cell on the canvas, including cells the operator had excluded or limited to input only or output only, so the total was the session rather than the active set.

**Avoidances.** Do not count Excluded, input-only, or output-only cells. Do not count a merge reply whose merge is not Included. Do not count the mood line. Do not change the inspector list. Do not change the Rust ring in this rule. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4116

## The bar buttons sit two pixels shorter

The two canvas button bars lose another 2 pixels of height. The bar height is the preferred height minus 4.

Bar buttons keep the font size. `fitAetherisButton` sets a bar button to the preferred height minus 4. The earlier cut was minus 2. This rule replaces that cut. Buttons that are not bar buttons keep the 70 percent path. The red search x is not a bar button.

The Swing client changes. The Rust client stays.

**Truth.** A canvas bar button is the preferred height minus 4 and keeps its font size. The minus-2 cut is gone. Non-bar buttons stay on the 70 percent path. The red search x is not on this path.

**Reason.** Minus 2 was still one step too tall. The font has to stay, so the cut moves to minus 4 instead of shrinking the words.

**Avoidances.** Do not leave the height at preferred minus 2. Do not shrink the font. Do not put non-bar buttons, or the red search x, on the minus-4 path. Do not change the Rust client. Do not add a package, a public type, a public method, a private method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4117

## The mood dialog opens on a readable cell

The Mood dialog opens on a readable cell. An armed card still wins. A missing arm uses the cell the canvas already targets. A blocked cloud stays closed. A missing connector stays closed.

The old path called `findCell` on the focused id and returned before the dialog was shown when that id was null, when the cell was gone, or when the cell was not readable. The focused id is set on a card press and is never cleared. The only signal was the status word `no cell`. The canvas target already falls through to the last cell on the showing canvas. Mood ignored that fall-through. The dialog method itself is the same path that last wrote the mood store. The button still calls `openMood`. The cloud gate and the connector gate were not the silent return.

`moodCard` keeps an armed readable cell. When the arm misses, it uses the canvas target when that cell is readable, otherwise the last readable cell on the showing canvas. When no readable cell remains, the status still says `no cell` and the dialog stays closed. A blocked cloud still closes the dialog with the keyword-only slide. A missing connector still closes the dialog with that same slide.

The private helper is `moodCard`. The Swing client changes. The Rust client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** Mood opens on the armed readable cell, else the readable canvas target, else the last readable cell on the showing canvas. With none, the status is `no cell` and the dialog stays closed. A blocked cloud or a missing connector still closes on the keyword-only slide. The button still calls `openMood`.

**Reason.** The dialog returned before it was shown whenever the focused id was empty, gone, or unreadable, even though the canvas already had a readable cell to target. The only feedback was the status word `no cell`.

**Avoidances.** Do not return before the dialog is shown when a readable cell still exists. Do not clear the focused id. Do not open on an unreadable cell. Do not bypass the cloud gate or the connector gate. Do not change the keyword-only slide. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4118

## The mood Cancel button shows Cancel

The Mood Cancel button is wide enough that the word Cancel is fully drawn.

After `fitAetherisButton`, Cancel's preferred width and minimum width gain the same three ems that Clear and Approve already take. The height stays the fitted height. The word stays Cancel. Clear stays. Approve stays. The mood card stays. The cloud gate stays. The connector gate stays.

The Swing client changes. The Rust client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** Cancel's preferred width and minimum width include three extra ems, the same pad Clear and Approve already have. The height stays the fitted height. The label stays Cancel.

**Reason.** The word Cancel was clipped. Clear and Approve were already wide enough by three ems, and Cancel had not been given that pad.

**Avoidances.** Do not change the label off Cancel. Do not change the fitted height. Do not resize Clear or Approve in this rule. Do not bypass the mood card, the cloud gate, or the connector gate. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4119

## The heatbar sits on the thin status bar

The notebook heat bar sits full width under the footer, on the thin status bar.

`buildHeatStrip` seats that beat. The word rides the east of that strip. T stays in the inspector. On the product tab the frame beat hides, parent and all.

The 5 pixel track stays. The 2 by 9 needle stays. The posts at 70 and 90 stay. The 6 pixel lunge stays. One flash stays. The count stays the ring estimate.

The Swing client changes. The Rust client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** The notebook beat is full width on the thin status bar under the footer, with the word on the east. T stays in the inspector. The product tab hides the frame beat and its parent. Track, needle, posts, lunge, flash, and ring count stay.

**Reason.** The beat was not on the thin status bar, so the canvas footer and the gauge did not share one strip.

**Avoidances.** Do not move T out of the inspector. Do not leave the frame beat visible on the product tab. Do not change the 5 pixel track, the 2 by 9 needle, the posts at 70 and 90, the 6 pixel lunge, the single flash, or the ring count. Do not put the word anywhere but the east of the strip. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4120 PK-4121

## The bar buttons sit five pixels shorter

The two canvas button bars lose 5 pixels of height after the word floor. The height still follows the font.

Bar buttons keep the application point size. `fitAetherisButton` still sets a bar button to the preferred height minus 4. The short-label hold still raises the button when that cut sits under the word. After that floor, a bar button loses 5 pixels. Buttons that are not bar buttons keep the 70 percent path. The red search x is not a bar button.

The edit is inside the short-label hold. The Swing client changes. The Rust client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** A canvas bar button is preferred height minus 4, then raised if that cut sits under the word, then 5 pixels shorter. The application point size stays, so the height still follows the font. Non-bar buttons stay on the 70 percent path. The red search x is not on this path.

**Reason.** Minus 4 was still too tall once the word floor had done its job. Taking 5 pixels after that floor shortens the bar without shrinking the font or clipping the word.

**Avoidances.** Do not take the 5 pixels before the word floor. Do not shrink the font. Do not drop the preferred-minus-4 step. Do not put non-bar buttons, or the red search x, on this path. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a private method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4122

## A merge cell takes scroll focus only when a cell in the group is clicked

A merge group leaves the wheel on the canvas scroll pane. A cell in that group takes scroll focus only when that cell is clicked anywhere.

The member row does not take the wheel and does not take focus. A wheel that is not on a focused text pane moves the canvas. A click anywhere on a grouped cell gives the caret to the text under the pointer, or to the prompt, then the reply, when that text is showing. The execution target keeps its own caret. A press on a scroll bar still returns the wheel to the canvas. A standard cell keeps its existing press. The member row still drags sideways.

The private helpers are `seatGroupWheel` and `focusGroupCell`. The Swing client changes. The Rust client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** The member row neither takes the wheel nor takes focus. The canvas wheel moves unless the pointer is on a focused text pane. A click on a grouped cell focuses the text under the pointer, or the prompt and then the reply when that text is showing. The execution target keeps its caret. A scroll-bar press returns the wheel to the canvas. A standard cell keeps its old press. The member row still drags sideways.

**Reason.** The member row was taking the wheel as soon as the group existed, so the canvas no longer scrolled until the operator hunted for a text pane.

**Avoidances.** Do not give the member row the wheel or the focus. Do not move the canvas wheel while the pointer is on a focused text pane. Do not take the caret away from the execution target. Do not change the scroll-bar press that returns the wheel to the canvas. Do not change the press on a standard cell. Do not stop the sideways drag. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4123 PK-4124

## A canvas cell input updates the heat bar while the user types

A canvas cell input updates the heat bar's estimated token count as characters are added and removed.

The cell card writes the live prompt and refreshes the inspector. The private method is noteLiveDraft. The private field that holds the running count is liveTokenHold. The composer beat of 200 ms already paints tokenRingUsed, and paste, cut, and delete take that same path. The count function stays the one a click already uses. A later rule owns a window that was choosing the wrong model. This rule owns only the live draft.

The other client counts the live prompt vector on every frame, including when the inspector column width is 0. The heat bar wears ring_used.

Both clients carry this. No new package, no new public type, and no new public method are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added.

No system line is added, removed, or reordered. The doors stay. The settings file stays.

**Truth.** Typing, pasting, cutting, or deleting in a canvas cell moves the estimated token count on the heat bar through the count a click already uses, painted on the existing 200 ms beat. The other client keeps counting that live vector even when the inspector column width is 0.

**Reason.** The draft is already on the card, and the beat already paints the used ring. The key should not invent a second count. A wrong model window is a separate rule and is left to that rule.

**Avoidances.** The wire does not change. The count function is not replaced. The wrong-window rule is not taken up here. Nothing new is added to the public surface beyond the private draft note and the private hold.

**semantic-key:** PK-6254

## The shell mood flag is saved by canvas user

Opening the mood modal shows the shell activation for the user that has focus on the canvas, the flag is saved by user, and an armed flag is remembered on that user's shell panes at startup.

The store is ~/.casserole/aetheris-ssh-mood.yaml. The version is 1. The body is a users map of sanitized name to boolean. The shared mood file aetheris-mood.yaml stays version, line, on, width, and height. The checkbox shows the canvas user's flag. An empty user leaves the box unchecked and disabled. A toggle writes that user and relays. On connect, the relay places the panes of armed users. In the other client, ssh_mood is the glass copy of the canvas user flag, and the map is the store. A blocked file is left unread and is not overwritten. A missing file starts empty. A name that sanitizes to anon is not stored. The other client's draft stays as filed.

No new package, no new public type, and no new public method are added on this rule. The readers and writers are the private store methods. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The shared mood file stays. The new file is this store. Both clients carry this.

The console door order is unchanged. The pinned host stays first. The mood string follows. The activate line follows. The turns follow. No system line is added, removed, or reordered.

**Truth.** The shell mood checkbox is the boolean stored for the focused canvas user in ~/.casserole/aetheris-ssh-mood.yaml at version 1. An empty user sees the box unchecked and disabled. A name that sanitizes to anon is not stored. A missing file starts empty. A blocked file is not read and is not overwritten. Startup relays the panes of users whose flag is armed.

**Reason.** The modal was showing one activation for whoever was focused, and a restart was forgetting which user's panes should come back armed. The flag belongs to the user, not to the pane and not to the shared mood line. The shared file keeps version, line, on, width, and height. anon is not a real user name, so it is not a key.

**Avoidances.** The shared mood file is not rewritten into the shell map. The door order does not change. A blocked file is not clobbered. An empty user cannot toggle the box.

**semantic-key:** PK-6255

## The VT100 heat bar moves while the user types

The heat bar beat and the estimated token count move while the user types in a shell pane, and the number is the focused pane when several panes are open.

Each pane keeps its own unsent line. The heat bar and the token window read the focused pane. A held shell integer stays the base. A non-empty draft adds the heuristic of that line. An empty draft adds nothing. An unheld tally uses the session text, and adds the draft when the echo has not already caught that suffix. Enter clears the line. Backspace pops one character. Shift-Enter appends a newline and keeps the line. The canvas path stays. No server call runs on a key.

The public methods on the existing panel are setDraftBeat and liveLine. The private method is sshLiveCount. The private function is ssh_live_count. Both clients carry this. No new package, no new public type, no new vault column, no new channel, no new shell message type, no new color key, and no new library are added.

No system line is added, removed, or reordered. The doors stay. The settings file stays.

**Truth.** Typing in the focused shell pane moves that pane's estimated count on the heat bar. A held integer is the base, and only a non-empty draft adds the heuristic of the unsent line. An unheld tally uses the session text and adds the draft only when the echo has not already caught that suffix. Enter clears, Backspace drops one character, and Shift-Enter keeps a newline on the line.

**Reason.** The canvas path already moved with the draft. A shell pane was waiting for a click, and several open panes would have shared one number. The focused pane owns the number the bar shows. The count is local, so a key does not call the server. The echo must not be counted twice.

**Avoidances.** The canvas path is not retuned. No server call is added on a key. An empty draft adds nothing. The wire does not change.

**semantic-key:** PK-6256

## The canvas switch leaves the shell mood in place

The canvas mood switch leaves a shell mood that the checkbox still holds. A checked box keeps the console mood while the canvas switch is off. An unchecked box still clears a pane that was told.

The canvas switch stays on canvas, group, and pipe sends. The shell gate is the checkbox and a saved line. sshMoodLineFor and ssh_mood_seat do not read the switch. A null want still leaves a pane that was never told alone. An unchecked box, or a missing line, still clears a pane that was told. The status strip follows that gate. paintFrameStatus runs at the end of relayArmedMoods. A null panel returns before that call. The server pair stays. Earlier sentences that fold the canvas switch into the shell gate are the contract this rule replaces. Assemble stays the Run.

The private function is ssh_mood_seat. No new package, no new public type, and no new public method are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. Both clients carry this.

When the box is checked, the console door order stays pinned host, mood string, activate line, then turns, whether the canvas switch is on or off. When the box is unchecked, there is no shell_mood frame, and a pane already told gets shell_mood_clear. Canvas, group, and pipe doors stay host, then mood and activate only while the canvas switch is on. This change sends fewer clears.

**Truth.** Turning the canvas mood switch off does not clear a shell pane whose checkbox is still SSH Mood Active and whose line is saved. Turning the switch back on is not required to revive that session. An unchecked box, or a missing line, still sends shell_mood_clear to a pane that was told, and a pane that was never told is left alone.

**Reason.** The modal still said the box was on while the session had gone inactive, because the canvas switch was being read as the shell gate. The switch belongs on canvas, group, and pipe sends. The shell gate is the checkbox and the saved line. Reading the switch inside sshMoodLineFor and ssh_mood_seat was the miss. Fewer clears are sent because a checked box no longer clears when the canvas switch drops.

**Avoidances.** The server pair is not rewritten. Assemble stays the Run. A null panel does not paint status. The checkbox label stays SSH Mood Active. Canvas, group, and pipe still withhold mood and activate while the canvas switch is off.

**semantic-key:** PK-6257

## The assistant reply frame is sapphire

The assistant reply frame on a canvas cell is sapphire. The input frame stays green.

The assistant scroll takes SELECTION_SAPPHIRE when the cell has a reply. The input scroll stays MEDIUM_GREEN. The input text stays INPUT_GREEN. An empty reply stays gray. The card edge stays. Cell frames in the other client stay as they are.

The Java client carries this. No new package, no new public type, no new public method, no new private method, no new vault column, no new channel, no new shell message type, and no new color key are added. No new library is added. The stroke uses the sapphire already used for an armed card.

No system line is added, removed, or reordered.

**Truth.** A canvas cell that has a reply paints the assistant scroll in SELECTION_SAPPHIRE. The input scroll stays MEDIUM_GREEN and the input text stays INPUT_GREEN. An empty reply stays gray. The card edge does not change.

**Reason.** The reply frame needed a color the eye already reads as the armed-card sapphire, so the reply is distinct from the green input without a new color key. An empty reply is not a reply, so it stays gray. One stroke is enough.

**Avoidances.** No new color key is introduced. The input frame is not recolored. The other client's cell frames are not edited. The card edge stays. The wire does not change.


**semantic-key:** PK-4155

## The I, E, P, and O marks stay live

The I, E, P, and O marks on a canvas cell stay functional in every focus state and every event state. One method, seatMark, is the write.

A press on a mark calls seatMark. Ctrl-Shift I, Ctrl-Shift E, Ctrl-Shift P, and Ctrl-Shift O call it. The write does not ride ButtonModel armed, pressed, or focus. The four marks are plain buttons. They are not focusable. The card listener does not steal focus from a mark. The gesture does not call rebuildTabs and does not rebuild the deck. The live card paints the seated letter with PLATE_SELECT, and paints the ghost when the state is EXCLUDED. The seated letter wears PLATE_SELECT and PLATE_SELECT_INK. paintCellMark paints the four faces and the ghost on the live card. A JButton put in place of the toggle is the same miss.

The other client's mark_button already writes the mark in the same show pass. This rule does not change that client. chrome.rs and grid.rs stay. The private method is seatMark. No new public type, no new public method, and no new color key are added. No new library is added. The Java client carries this.

No system line is added, removed, or reordered. The doors stay. The settings file stays.

**Truth.** Pressing I, E, P, or O, or using Ctrl-Shift with that letter, seats the mark through seatMark. The four marks are plain buttons and are not focusable. The write ignores the toggle model's armed, pressed, and focus. The gesture does not rebuild the deck. On the live card the seated letter wears PLATE_SELECT and PLATE_SELECT_INK, and EXCLUDED paints the ghost.

**Reason.** The marks were dying when the button model was armed, pressed, or focused, and a card listener was taking focus away from the mark. A plain button that is not focusable, with the write outside that model, keeps the letter live in every focus state and every event state. Rebuilding the deck on the gesture was wiping the press. The ghost on EXCLUDED shows the mark is off the send without hiding the letter.

**Avoidances.** The marks do not become focusable. The gesture does not call rebuildTabs. No new color key is added. The other client is not edited. A toggle, or a JButton standing in for the toggle, is not the fix. The wire does not change.


**semantic-key:** PK-4167 PK-4168 PK-4169 PK-4170 PK-4171 PK-4172 PK-4173

## Mood follows the switch on an existing session

Mood follows the footer switch on a session the user already has. The checkbox makes mood available for the shell. Shell mood is on when the footer switch is on and the checkbox is checked. The switch off turns mood off, including the shell. An unchecked box leaves the shell without the mood while the switch is on.

The earlier rule that the switch does not clear a checked shell gate is overridden for activation. That earlier rule stays otherwise. An existing canvas, an existing shell transcript, a mood switched off and back on, and a Transfer Conversation from the shell onto the canvas take the mood the switch now says. The engagement is the opener, then the constructed mood, then the closer. The constructed mood is the saved line. The example paragraph stays an example.

The public constants are MoodDesk.OPENER, MoodDesk.CLOSER, and MoodDesk.LEGACY. OPENER is the sentence before the constructed mood. CLOSER is the sentence after it. LEGACY is the old activate sentence. A later place still drops that old row. The package method sshLine takes the checkbox, the footer switch, and the saved line. Both gates on, and a line saved, returns the constructed mood. Either gate off returns null. canvasSystems returns the opener, the constructed mood, and the closer while the switch is on and the line is non-blank. A canvas execute keeps that triple. A frame that does not fit fails as too large and does not run. The send does not drop the triple to fit.

seat writes the opener, the constructed mood, and the closer, and takes every prior triple out, including a pair that still uses LEGACY. A row immediately before the opener is the host and stays. A row immediately before the closer, or before LEGACY, is the mood and leaves. adopt reads the constructed mood. porchRules returns the opener, a blank line, the constructed mood, a blank line, and the closer. A legacy pair still reads. The glass writes the opener, the field text, and the closer. The footer switch does not gate the glass.

Transfer from the canvas onto Roux relays the gate after ok. The server may still hold the previous line until that frame arrives. Transfer from Roux onto the canvas stores user and assistant cells and does not store the triple in those cells. The next canvas execute carries the triple from the switch. The constructed mood is normalize of the saved line.

No new public type and no new public method are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The Java client carries this. The Rust client does not change on this rule. chrome.rs and grid.rs stay.

Canvas, group, and pipe send an empty host place, the opener, the constructed mood, the closer, and the turns, while the footer switch is on and the line is non-blank. The server replaces that empty place. The execute does not drop the triple to fit. The shell sends the pinned host, the opener, the constructed mood, the closer, and the turns, while the footer switch is on, the checkbox is checked, and a line is saved. The switch off sends shell_mood_clear even when the box stays checked. An unchecked box sends clear even when the switch is on. An existing transcript gets the triple on that announce and loses the prior pair, including the legacy activate sentence. The glass sends the shaping line, then the opener, the constructed field text, and the closer, when the field holds a line. The footer switch does not gate the glass. Pool session/new porch rules are the opener, a blank line, the constructed mood, a blank line, and the closer. The host stays out.

**Truth.** An existing session takes the opener, the constructed mood, and the closer when the footer switch is on. Shell mood also requires the checkbox. Switch off sends shell_mood_clear even if the box stays checked. An unchecked box clears even if the switch is on. A frame that does not fit fails too large and is not trimmed. The host row immediately before the opener stays. The mood row immediately before the closer, or before the old activate sentence, leaves when the gate is rewritten. The example paragraph stays an example.

**Reason.** Mood was reaching a fresh session and a cleared session, and missing the session already on screen: an existing canvas, an existing shell transcript, a switch turned off and back on, and a Transfer Conversation. The footer switch is the activation for every door that pushes mood, and the checkbox only makes the shell eligible. Dropping the triple to fit would send a different mood than the one the user saved, so a frame that does not fit does not run. The glass is still the field, not the switch. The server may keep the previous line until the relay after a successful transfer arrives.

**Avoidances.** The triple is not dropped to fit. The example paragraph is not promoted to the constructed mood. The glass is not gated by the footer switch. The host stays out of the pool porch. The Rust client is not edited on this rule. Cells stored by a transfer onto the canvas do not contain the triple.


**semantic-key:** PK-4174 PK-4175 PK-4176 PK-4177 PK-4178 PK-4179 PK-4180 PK-4181 PK-4182 PK-4183 PK-4184 PK-4185 PK-4186 PK-4187

## The mood call uses the armed cell on the showing canvas

The mood call uses the armed cell on the showing canvas. The arm is the readable cell on that canvas. A readable cell on a hidden canvas is a stale arm and falls through.

moodCard reads focusedTurn. A readable cell returns. A missing arm, or an unreadable arm, falls through to targetCell. A readable targetCell returns. Else the last readable cell on the showing canvas returns. None returns null. openMood then sets the status to no cell and the dialog stays closed. sendMood still loads the id captured at open and still calls moodTarget. moodTarget still stands. sendMood still sets the execute target from that card.

ollamaPaneOwnsCell returns true when the family combo is missing, and when the selected item is Ollama. It returns false for Grok, for Pool, and for an empty selection. commitOllamaModel calls it on the cell path. The console path still calls commitConsoleModel. The Ollama editor keyReleased calls ollamaPaneOwnsCell after its existing guard. The family listener stays. applyGrokFamily still writes the grok target. A missing family combo keeps the old write. The Ollama pane can still write the cell after the family item is already Grok or Pool, and that write stops unless the family item is Ollama.

The private methods are moodCard and ollamaPaneOwnsCell. No new public type and no new public method are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The Java client carries this. The Rust client already reads the focused card on the showing canvas and does not change. chrome.rs and grid.rs stay. The console path stays.

The mood triple stays. Canvas, group, and pipe send an empty host place, the opener, the constructed mood, the closer, and the turns, while the footer switch is on and the line is non-blank. The server replaces that empty place. The shell sends the pinned host, the opener, the constructed mood, the closer, and the turns, while the footer switch is on, the checkbox is checked, and a line is saved. The glass sends the shaping line, then the opener, the constructed field text, and the closer, when the field holds a line. The footer switch does not gate the glass. Pool session/new porch rules are the opener, a blank line, the constructed mood, a blank line, and the closer. The host stays out.

The mood execute execution_target follows the armed cell on the showing canvas. An empty target is ollama. A Grok card sends grok and that card's model id. A pool card sends pool. The constructed mood is normalize of the saved line. The example paragraph stays an example.

A probe of the Family combo click wrote ollama on focus loss while the combo still showed Ollama, then wrote grok, and the grok write stayed. This rule does not treat that click as the cause.

**Truth.** The mood dialog opens on the readable cell of the showing canvas, then the last readable cell there, and stays closed with status no cell when none exists. The execute target is that card: an empty target is ollama, a Grok card sends grok and that card's model id, and a pool card sends pool. An Ollama model write does not land on a cell whose family item is Grok, Pool, or an empty selection. A missing family combo still allows the old write. Run and the engine word stay on the showing canvas.

**Reason.** The call had been using Ollama after Grok was selected on the cell in front, because a readable Ollama cell on a hidden canvas beat that card. The arm is the showing canvas. The server completes grok on a grok target and ollama on an empty target or an ollama target, so an Ollama reply means the card that was read stored an empty target or an ollama target. Stopping the Ollama pane from writing after the family item has left Ollama keeps the card the family is showing. The focus-loss write that still showed Ollama, then wrote grok and kept grok, is not the cause this rule fixes.

**Avoidances.** A hidden canvas is not a live arm. The console path still uses commitConsoleModel and is not asked. The family listener stays. The mood triple on the wire does not change. The Rust client is not edited. The example paragraph stays an example.


**semantic-key:** PK-4188 PK-4189 PK-4190

## A cell key posts the token walk

A key in a canvas cell input writes the draft and posts an edit. The transcript walk runs off the key. The newest edit is the one that runs. A late result paints only when its stamp is still current. A click still measures on that call. The heat bar still reads the integer on its beat.

noteLiveDraft still writes the cell text on the key. The first key on an unarmed card arms that card, then posts the edit and schedules the inspector. A key on a card that is already armed posts the edit and schedules the inspector. The arm chrome, the status line, the delete button, and the caption stay on the key. A click still measures before it returns. refreshInspector paints the inspector, then measures. The scheduled inspector paint is the text, the facts line, and the mood line. It does not walk.

measureTokensNow claims the next stamp, drops an edit that has not started, walks the armed transcript, and paints when that stamp is still current. An empty canvas claims a stamp and paints 0. TokenEditQueue is a private class. The thread is a daemon named aetheris-token-edit. offer keeps the newest edit that has not started. The worker copies the place on the event thread, walks the copy off the event thread, and paints on the event thread when the stamp is still current and the card that posted it is still armed. transcriptFrom is the shared walk. membersOn reads the ordered list the walk already holds, so the join-banner strip stays the strip the click already used. The worker does not read the showing canvas. HeuristicEstimator stays. The marks stay. The heat bar stays on tokenRingUsed. The token window stays. notebookDip.watch stays on the paint. The shell heat path stays.

The private classes are TokenEdit, TokenSeat, and TokenEditQueue. No new public type and no new public method are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The Java client carries this. The Rust client already counts the live prompt off the key and does not change. chrome.rs, grid.rs, and the shell application class stay.

No send line moves. The doors stay.

**Truth.** Typing does not walk the armed transcript before the key returns. The key writes the draft, posts one edit, and returns. The daemon aetheris-token-edit keeps only the newest edit that has not started, walks a copy off the event thread, and paints on the event thread only when that stamp is still current and that card is still armed. An empty canvas paints 0. A click still measures before it returns. The heat bar still reads tokenRingUsed on its beat.

**Reason.** Each insert, delete, and change was running the armed transcript and the heuristic on the event thread before the key returned, so typing waited on the walk. The count stays the count. Posting the edit keeps the newest draft and drops a stale result. The worker must not read the showing canvas, because the copy taken on the event thread is the seat. The join-banner strip stays the one the click already used, through the ordered list the walk already holds.

**Avoidances.** The count is not changed. A click is not posted. The scheduled inspector paint does not walk. The shell heat path is not moved onto this queue. The worker does not touch the showing canvas. No send line moves. The Rust client is not edited.


**semantic-key:** PK-4191 PK-4192 PK-4193 PK-4194 PK-4195 PK-4196 PK-4197 PK-4198 PK-4199 PK-4200 PK-4201 PK-4202 PK-4203 PK-4204 PK-4205 PK-4206

## Undo and redo stay in RAM for this session

Session undo and redo for shell panes and for canvas cells live in RAM only. They do not survive a restart. Ctrl-Z undoes. Shift-Ctrl-Z redoes. Each canvas cell keeps its own stack. Each shell input line keeps its own stack, including a line pulled back from history while that line is the one being edited. Undo runs in reverse order of the text changes.

EditMemory wraps UndoManager. The limit is 100. An edit stores the text and the cursor. The caller writes them into the live widget. sync remembers a rebuilt card without pushing. A history hop reloads the canonical line and is not an undo step. The draft stack stays parked until that draft returns. The first text change that leaves history mode adopts the visit place. Enter and bare Escape drop the stacks. A paste is one step. Ctrl-Z is byte 0x1A. Redo is ESC [ 1 ; 6 Z. The Java terminal sends those bytes only when the terminal is focused. The Rust client keeps a string place per canvas cell and sends the same chords for the shell.

The public type is EditMemory. The package is org.casserole.edit. No new public method beyond that type, no new vault column, no new channel, no new shell message type, no new color key, and no new library are added. Both clients carry this. grid.rs stays. The history file stays.

Ctrl-Z is byte 0x1A. Shift-Ctrl-Z is ESC [ 1 ; 6 Z. Both are editor operations on the unsent line and on a canvas prompt. They are not a channel and not a shell message type.

**Truth.** Ctrl-Z undoes and Shift-Ctrl-Z redoes, up to 100 edits, in memory, per canvas cell and per shell input line. Each edit stores the text and the cursor. A history hop is not an undo step, and the draft stack waits until that draft returns. Enter and bare Escape drop the stacks. A paste is one step. The bytes are 0x1A and ESC [ 1 ; 6 Z, sent by the Java terminal only while the terminal is focused. Nothing is written to disk. The history file stays.

**Reason.** Undo belongs to the line being edited, not to the session file and not to the wire. A history hop reloads a canonical line, so pushing it would make undo fight the history walk. Parking the draft stack until that draft returns keeps the edits of the line the user was typing. The chords are editor operations, so they stay off the channel and off the shell message types. The limit of 100 bounds the RAM hold for this session.

**Avoidances.** The stacks are not written to disk and do not survive a restart. A history hop is not an undo step. The bytes are not a new channel and not a new shell message type. grid.rs is not edited. The history file stays. Clear from its own button is not this undo.


**semantic-key:** PK-4207 PK-4208 PK-4209 PK-4210 PK-4211 PK-4212 PK-4213 PK-4214 PK-4215 PK-4216 PK-4217 PK-4218 PK-4219 PK-4220 PK-4221

## A key on a shell pane posts the count

Typing on the shell pane posts the token edit the way a canvas cell does. The count runs off the key. Each pane keeps its own integer. The timer paints that integer. A click still measures on that call.

SshEditQueue keeps the newest post. The key writes the unsent line and posts. The walk copies that pane and counts off the key. A result places the integer on that pane when its stamp is still current, and the heat bar paints it when that pane is in front. The timer posts the focused pane and paints the stored integer. It does not call sshLiveCount. A click still calls sshLiveCount.

The private class is SshEditQueue. The private types are SshEdit and SshSeat. The package methods are liveCount and seatLiveCount. No new public type and no new public method are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The Java client carries this. The Rust client does not change. chrome.rs and grid.rs stay.

No send line moves. The doors stay.

**Truth.** A key on a shell pane writes the unsent line and posts an edit. The newest edit runs. The integer lands on that pane only when its stamp is still current. Each pane keeps its own integer. The timer paints the stored integer of the focused pane and does not call sshLiveCount. A click still calls sshLiveCount before it returns.

**Reason.** The canvas key already left the walk. The shell key was still counting before it returned. The timer's job is to paint the integer the queue already seated, not to count again. A click remains a synchronous measure so the number the click shows is the number of that call.

**Avoidances.** The timer does not call sshLiveCount. A stale stamp does not paint. The Rust client is not edited. No send line moves.

**semantic-key:** PK-6258

## Clear turns keeps the armed model

Clear turns keeps the armed cell's model on the new first turn. The word is captured before the wipe. Thinking flags stay off. Expanded stays unchecked. The button still cannot be undone.

clearTurns reads the armed cell, then the empty turn is built. The new cell's executionTarget is written from that captured word. newTurn stays on the last cell. In the other client, try_clear reads the focused card, clear_plate builds the empty card, and the fresh card receives target and ollama_id. keep_card_think writes that id. Thinking flags stay off. Expanded stays unchecked. Marks stay Included on the fresh card. The other canvases stay. The shell session stays. Clear still cannot be undone from the button.

No new public type and no new public method are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. Both clients carry this. grid.rs stays.

No send line moves. The doors stay.

**Truth.** After Clear turns, the new first turn wears the model the armed cell had before the wipe. The Java cell stores that execution target. The Rust card stores the target and the Ollama id. Thinking flags are off. Expanded is unchecked. Marks on the fresh card are Included. Other canvases and the shell session stay. The Clear button still cannot be undone.

**Reason.** The wipe was dropping the model the user had armed, so the new first turn opened on a default the cell had not chosen. Capturing the word before the empty turn is built keeps the model without keeping the text. Thinking and the expanded check are session chrome, not part of the model, so they start off. Included is the fresh card's mark.

**Avoidances.** Clear is not added to undo. Thinking flags are not copied. Expanded is not copied. Other canvases are not cleared. The shell session is not cleared. newTurn still means the last cell. The wire does not change.


**semantic-key:** PK-4222 PK-4223 PK-4224

## The mood title names the model

The mood title names the model the glass sends. A blank short name keeps the word Mood.

openMood calls moodTarget on the card moodCard already chose, then shortModelName. A blank short name keeps the title Mood. Any other short name sets the title to Mood, then U+00B7, then that name. paint_mood reads the card bound to the glass. mood_tune fills the target the send uses. short_model_name reads that target. The same blank rule keeps the title Mood. Window::new takes that string. The window id stays on the generation. The mood string stays. The footer switch stays. Approve, Cancel, and Clear mood stay. The send stays on the same card and the same target. View, the token window, Model Config, and the Clear mood ask keep their titles. No row is added at the top of the chat.

No new public type and no new public method are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. Both clients carry this. grid.rs stays.

No send line moves. The doors stay.

**Truth.** The mood window title is Mood when the short name is blank. Otherwise it is Mood, the middle dot U+00B7, and that short name. Java reads moodTarget and shortModelName from the card the dialog already chose. The other client reads the mood tune for the bound card and short_model_name. The window id stays on the generation. The send stays on that card and that target.

**Reason.** The glass sends one model, and the title is the place the reader sees which model that is. A blank short name must not become a stray separator, so the word stays Mood. The title is not a new row in the chat, and the other windows keep the titles they already have.

**Avoidances.** View, the token window, Model Config, and the Clear mood ask do not change titles. No row is added at the top of the chat. Approve, Cancel, Clear mood, the mood string, and the footer switch stay. The send target does not move. The wire does not change.


**semantic-key:** PK-4225 PK-4226

## The mood triplet splices onto the turn

The mood triplet splices onto the head of the turn being sent. One record. Zero or one turn already in the transcript keeps that head on the first turn. More than one turn, and a newly defined or updated mood, puts that head on the turn being sent. The pin stays until the next define or update. A deleted pin walks back one cell. Clear and a new session put the pin at the head. Marks do not move the pin. The switch off removes the splice and leaves the pin. The glass stays the shaping line, then the opener, the field, and the closer. The switch does not gate that chat.

One mood record. The opener, the line, and the closer splice onto the head of one turn's user text. The format is the opener, a newline, the line, a newline, the closer, a newline, then the raw turn. spliceTurn, unspliceTurn, and lineAtTurnHead are the public methods on MoodDesk. rulesFromTurn stays private. seat stays the system-row helper. Canvas, group, and pipe no longer prepend the system triple. The empty host place stays. The server still replaces it. The vault cell keeps the raw user text. Assemble splices the outbound copy.

The pin is a cell id, or the word head, per canvas, in aetheris-mood.yaml. Version stays 1. The keys pending and pins are written in place. A missing pin means head. It is not a vault column. A user turn is a kept cell that contributes user text, including a merge sink. Zero or one such turn already ahead of the target keeps the pin at the head, and the splice rides the first included user cell in the send. More than one, and a newly defined or updated mood, sets pending. The Run that sends the next turn consumes pending and pins that target cell. The same trimmed line is not an update. Two approves before a send use the latest line and one move. The pin stays until the next define or update. Further turns of the same mood append after the pin. Marks do not move the pin. An excluded pin cell contributes no lines. The send places the splice on the first included user cell at or after the pin that is in this send. A run whose target sits before the pin sends no splice. Reorder keeps the cell id. The record travels with the cell. deleteFocusedTurn walks the pin back one remaining cell when the removed ids contain it. None behind that hole returns the pin to head. clearTurns sets that canvas pin to head and clears pending. A new session clears the line, the switch, the pins, and pending. The switch off leaves the pin and sends no splice. The switch on with the same held line puts the splice back at that pin.

The shell has no cells. The spliced user message in the Roux username region is the pointer. moodHeld, moodPinOrdinal, and moodMovePending sit on ChatSession. appendUser stores the raw text unless the stored user ordinal is the pin and the move is not pending, and then it stores the splice. A define or update with one stored user turn or none writes the splice onto that first user turn and drops a stored system mood row. A define or update with more than one stored user turn sets pending and leaves the old splice until buildPrompt of the next turn. That build drops the system mood rows, strips the stored splices, sets the pin to the user count about to be added, and splices the new user message. A stored system triple with no user splice still rides through seat until the line changes. Switch off strips the stored splice, keeps moodHeld and the pin, and clears pending. Switch on of that held line writes the splice back onto the pin and does not jump. reset and clearTurns drop the turns, set the pin ordinal to 0, clear pending, and leave the mood line. They do not re-seat a system triple. ChatSession has no single-turn delete. clearTurns and reset are the return to the first turn. replaceTurns clears the console mood with the turns. adopt reads a leading system triple, and otherwise the first spliced user head, and that user ordinal becomes the pin. porchRules returns the system triple when one is present, and otherwise the spliced user head, as the opener, a blank line, the line, a blank line, and the closer. A narrow Roux window does not promise a mid-list splice. The full prompt does. getWindow stays.

The glass appendMoodHeader stays. The switch does not gate that chat. The shell frame stays shell_mood and the line, or shell_mood_clear. Replies above the pin stay. The pin does not delete them. The public methods are spliceTurn, unspliceTurn, and lineAtTurnHead. No new public type, no new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The Java client carries this. The Rust client does not change on this rule. The declared mood batteries are maintained. The new case is that a spliced turn wears the triple at its head. That is not a new test class.

Canvas, group, and pipe send an empty host place, then the turns. The pinned turn's user text starts with the opener, the line, and the closer. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host place. The mood triple is not a system row. On the shell, the prompt turns are the Roux turns. The pinned user turn's text starts with the opener, the line, and the closer. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin. The glass stays the shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Pool porch rules are the opener, a blank line, the line, a blank line, and the closer, read from a leading system triple or from the spliced user-turn head.

**Truth.** The mood is one record spliced onto one user turn: opener, newline, line, newline, closer, newline, then the raw turn. The vault keeps the raw text. Assemble splices the outbound copy. With zero or one user turn already ahead, the pin stays head and the first included user cell in the send wears the splice. With more than one, a new or updated line sets pending, and the next Run pins the target cell. The same trimmed line is not an update. Marks do not move the pin. An excluded pin slides to the first included user cell at or after the pin in this send. A target before the pin sends no splice. Deleting the pin walks back one remaining cell, or back to head. Switch off removes the splice and leaves the pin. Replies above the pin stay.

**Reason.** A system triple on every send was the wrong place once the mood belonged to a turn. Putting it on the head of the turn being sent, and leaving it there until the next define or update, lets later turns of the same mood append after the pin. The cell id travels through a reorder. The shell has no cells, so the pointer is the spliced user message in the username region. A narrow window cannot promise a splice in the middle of a list. The full prompt can. Two approves before a send are one move to the latest line.

**Avoidances.** Canvas, group, and pipe no longer prepend the system triple. The pin is not a vault column. Marks do not move the pin. The pin does not delete replies above it. The glass is not gated by the switch. The shell frame stays shell_mood and the line, not the opener and the closer. ChatSession has no single-turn delete. The Rust client is not edited on this rule. A narrow Roux window is not promised a mid-list splice.


**semantic-key:** PK-4227 PK-4228 PK-4229 PK-4230 PK-4231 PK-4232 PK-4233 PK-4234 PK-4235 PK-4236 PK-4237 PK-4238 PK-4239 PK-4240

## Debug Mood paints the triplet

File, Settings gains Debug Mood. When it is on, the inspector shows the mood triplet as a card above the turn the pointer places, so the transcript over the wire can be examined. Painting the card does not move the pin. The send stays the splice.

File, Settings opens the window. The checkbox is Debug Mood. It starts off. On, the inspector paints one card above the turn the pointer places. The inspector lists the newest card first, and the mood card is painted immediately before that turn. The card lines are the opener, the line, and the closer, the same three lines the splice puts at the head of that turn. The role word on each line is mood. Off leaves the inspector as it is. The existing mood header stays. A pending move is shown where the next Run would place it, and the paint does not write the pin. A Run still consumes pending. Zero or one turn already ahead of the target keeps the card on the first turn. More than one, and a pending define or update, puts the card on the turn being sent. An excluded pin shows the card on the first included cell at or after the pin that this working set would send. A working set whose target sits before the pin shows no card. The switch off shows no card.

The choice is debug_mood in aetheris-settings.yaml, version 1. A missing file stays off and is not written until the switch moves. A bad file stays off and is not overwritten. Other keys in that file, including painted_borders, are written back. The other client already keeps unknown keys. This rule does not edit that client. The public method is openSettings. The nested place is SettingsSeat. The shell application gains the menu item. No new public type is added. Not a new test class.

The wire is unchanged. The working set stays the splice. The card is inspector paint. Writing debug_mood keeps the other keys, so painted_borders survives.

**Truth.** Debug Mood starts off. On, the inspector paints one mood card immediately before the turn the pointer places. The lines are the opener, the line, and the closer, each with the role mood. A pending move is shown where the next Run would place it and is not written by the paint. Switch off, a target before the pin, and the off state show no card. debug_mood is version 1 in aetheris-settings.yaml, and painted_borders and the other keys are written back.

**Reason.** The splice is on the outbound copy, not in the vault cell, so the inspector is the place to see the three lines the wire will carry. Painting must not consume pending or move the pin, or the debug view would change the send. A missing or bad file stays off so a broken settings file is not overwritten. Unknown keys the other client already keeps, including painted_borders, have to survive the write.

**Avoidances.** The card is not a send line. The paint does not write the pin. The Rust client is not edited. Off leaves the inspector as it is. The existing mood header stays. A bad file is not overwritten.


**semantic-key:** PK-4241 PK-4242 PK-4243 PK-4244 PK-4245 PK-4246 PK-4247 PK-4248

## Debug Mood shows the head on its prompt

Debug Mood paints the invitation where the wire places it, immediately before the prompt the pointer places. The lines carry no card number. The rest of the inspector stays.

When Debug Mood is on, the card that holds the user line the pointer places opens with the splice head, then that user line. The head is mood:, the saved line, shape:, the opener, and the closer. The opener stays: I'm opening a window with this atmosphere… would you like to step into it with me? The closer stays: Shape your replies around this mood — let it breathe through the words. Those lines carry no card number and do not advance the number. The role lines keep their numbers. The inspector still builds oldest first and reverses once. The head is not a separate card and it is not reversed on its own. A block that does not open with the user role is left as it is. Off leaves the inspector as it is. Painting the head does not move the pin. The header stays. The separators stay. The facts line stays. The send stays the invitation splice. The glass stays. moodHead stays. The shell frame stays. debug_mood stays. The settings place stays. The settings version stays 1.

No new public type, no new public method, and no new test class are added. This rule overrides the separate debug card. The Java client carries this. The Rust client does not change on this rule. A known gap in paste undo stays.

The doors do not gain, lose, or reorder a line. The send stays the invitation splice.

**Truth.** With Debug Mood on, the invitation is painted immediately before the user line the pointer places, on that card, not as its own card. The lines are mood:, the saved line, shape:, the opener, and the closer. They carry no card number and do not advance the number. Role lines keep their numbers. The inspector still builds oldest first and reverses once, and the head is not reversed by itself. A block that does not open with the user role is left as it is. Painting does not move the pin.

**Reason.** A separate card sat above the turn and could be reversed on its own, so the debug view did not match the wire, which places the head on the prompt. Putting the head on the user line, with no card number, keeps the numbers on the role lines and leaves the rest of the inspector alone. Off still leaves the inspector as it is.

**Avoidances.** The head is not a separate card. The lines do not take a card number. The header, the separators, and the facts line stay. The send is not changed. The doors do not gain, lose, or reorder a line. The Rust client is not edited. The known paste-undo gap stays.


**semantic-key:** PK-4254 PK-4255 PK-4256

## The inspector drops the mood and shape directives

The inspector drops the mood directive and the shape directive from the top of the card list while the switch is on. Debug Mood stays. The yellow mood string stays.

While the switch is on, the inspector text does not open with mood:, the line, shape:, the opener, and the closer. The yellow card stays. It shows the saved line while the switch is on. A blank line still paints mood: (none). A saved line with the switch off still paints mood: off and the line. Debug Mood still paints the invitation immediately before the user line the pointer places. Those lines carry no card number. Painting does not move the pin. The send stays the invitation splice. The token count still counts the opener, the line, and the closer. The working set stays. mood.rs stays. The glass stays. The shell frame stays.

No new public type, no new public method, and no new test class are added. Both clients carry this. A known folded-bar mismatch stays outside this change: the shell glyph top is 108.2 and the status bottom is 64.1. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** With the switch on, the card list does not open with the mood and shape directives. The yellow card still shows the saved line. A blank line paints mood: (none). A saved line with the switch off paints mood: off and the line. Debug Mood still paints the invitation immediately before the user line, with no card number, and does not move the pin. The token count still counts the opener, the line, and the closer.

**Reason.** The inspector is the seat of the cards. The directives at the top repeated a head the send and Debug Mood already show. The yellow string is the short mood the reader watches, including the off and none forms, so it stays. Dropping the directives does not change what is counted or what is sent.

**Avoidances.** Debug Mood is not removed. The yellow card is not removed. mood: (none) and mood: off stay. The send, the token count, the glass, the shell frame, and mood.rs stay. The doors do not change. The folded-bar mismatch at 108.2 and 64.1 is not this change. The known paste-undo gap stays.


**semantic-key:** PK-4257 PK-4258

## The undo caret stays on the edit

Undo in a cell input pane places the caret where the edit began. Each undo keeps that place. The key queues a span and returns. The stack takes the span when undo or redo runs.

The caret had been moving its index to the wrong location, about 1 to 2 characters ahead of the edit. The public method is noteSpan. The shell line stays on note. The Rust client does not change on this rule. No new public type and no new test class are added. The Java client carries this. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** Each undo in a cell input pane puts the caret where that edit began, not 1 or 2 characters ahead. The key only queues a span. Undo and redo drain that span onto the stack. The public method is noteSpan. The shell line still uses note.

**Reason.** Recording the span on the key was moving the caret before the edit had a stable index, which landed the caret 1 to 2 characters ahead and made typing wait. Queuing the span and applying it when undo or redo runs keeps the caret on the edit and keeps the key short.

**Avoidances.** The shell line is not moved onto noteSpan. The Rust client is not edited. The known paste-undo gap stays. The wire does not change.


**semantic-key:** PK-4259 PK-4260 PK-4261 PK-4262 PK-4263 PK-4264 PK-4265

## Hide and Show return focus to the selected cell

Hide Collapsed and Show Collapsed put focus back on the cell that was selected. An open cell takes the prompt. A folded cell takes the header. A cell that left the deck waits until Show.

Hide Collapsed and Show Collapsed rebuild the deck, then put focus on the cell that was selected. An open cell takes the prompt. A folded cell takes the header. A cell that left the deck is not on the canvas. Show brings it back and places focus when it is still the selected cell. Collapse-all still keeps the one-line card. Hide Collapsed is the only path that adds an id.

No new public type, no new public method, and no new test class are added. Both clients carry this. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** After Hide Collapsed or Show Collapsed, focus returns to the cell that was selected. An open cell focuses the prompt. A folded cell focuses the header. A cell removed from the deck is not focused until Show brings it back, and only if it is still the selected cell. Hide Collapsed remains the only path that adds an id.

**Reason.** Rebuilding the deck was dropping focus off the cell the reader had selected. The prompt is the live editor of an open cell. The header is what a folded cell still shows. A cell that Hide Collapsed removed is not on the canvas, so focus waits for Show. Adding an id stays on that one path so Show does not invent cells.

**Avoidances.** Show does not add an id. A cell that is no longer the selected cell is not focused when it returns. Collapse-all still keeps the one-line card. The wire does not change. The known paste-undo gap stays.


**semantic-key:** PK-4266 PK-4267 PK-4268 PK-4269

## Expanded View stays per cell

Expanded View is saved per cell. A restart puts those checks back. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell's choice. A bad file stays unread.

The file is aetheris-expanded.yaml. It is not a vault column. A checked card is remembered when the vault stores. Add Cell writes the new card. The Java client joins that file. The Rust client writes a check while the view glass is open. The private type is ExpandedSeat. No new public type, no new public method, and no new test class are added. Both clients write aetheris-expanded.yaml. A bad file stays unread and is not overwritten. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** Each cell's Expanded View check is stored in aetheris-expanded.yaml and restored on restart. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell's choice. Add Cell writes the new card. A check is written while the view glass is open, and a checked card is remembered when the vault stores. A bad file is not read and is not overwritten.

**Reason.** The check was dying with the process, so a restart opened every cell the same way. The choice belongs to the cell, not to the vault row, which is why the file is separate and not a vault column. Clear and a fresh canvas are a new plate, so they start unchecked. A new turn is a continuation, so it keeps the prior cell's choice. Overwriting a bad file would destroy a file the reader can still repair.

**Avoidances.** The check is not a vault column. A bad file is not overwritten. A fresh canvas and Clear do not restore old checks. The wire does not change. The known paste-undo gap stays.


**semantic-key:** PK-4270 PK-4271 PK-4272 PK-4273 PK-4274 PK-4275

## The heat bar uses the selected cell

The heat bar uses the model of the selected canvas cell. That is the row Token Calc opens on. The first known window does not win. An unknown window stays 0. The shell tab stays on its own window.

The package-private method is selectedHeatBudget. No new public type, no new public method, and no new test class are added. The existing token-ring checks keep the new asserts. Both clients carry this. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** The canvas heat bar takes its window from the model of the selected cell, the same row Token Calc opens on. The first known window does not override that row. An unknown window shows 0. The shell tab keeps the window of that shell, not the canvas cell.

**Reason.** The bar was keeping the first window it knew, so a selected cell with a different model still painted the earlier budget. Token Calc already opens on the selected cell's row. The heat bar has to open on that same row. An unknown model has no budget, so the window stays 0 rather than borrowing another model's window.

**Avoidances.** The first known window does not win. The shell tab is not moved onto the canvas cell's window. An unknown window is not guessed. The wire does not change. The known paste-undo gap stays.

**semantic-key:** PK-6259

## The heat bar walk runs off the key

Cell input and shell typing post a heat bar beat. The queue keeps the newest beat. The walk runs off the key. The heat bar paints the last placed integer.

The other client already does this with the cell edit queue and the shell edit queue. The private type is HeatQueue. The private functions are keep_pending, seat_ready, and run_heat. No new public type, no new public method, and no new test class are added. The Rust client carries this. The Java client does not change. The folded bar stays. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** A key in a Rust cell input or a Rust shell pane posts a heat bar beat and returns. HeatQueue keeps the newest beat that has not run. The walk runs off the key. The bar paints the last integer that was placed. keep_pending, seat_ready, and run_heat are the private functions.

**Reason.** The other client already took the token walk off the key. This client was still walking before the key returned. Keeping only the newest beat drops a stale draft without dropping the integer the bar has already placed.

**Avoidances.** The Java queues are not rewritten. The folded bar is not moved. A stale beat does not replace the newest one. The wire does not change. The known paste-undo gap stays.

**semantic-key:** PK-6260

## Thinking headers follow the stream

When thinking is enabled in a merge group, for all input cells, the thinking pane places the model header before each thinking block. A different cell or a different model starts that header again. The same cell and the same model keep the open block.

paintThinkDelta stores the word on thinkStripWord. accept_thought compares the cell and the model line. route_console_thought uses that rule on the nil cell. The private field is thinkStripWord. The private function is accept_thought. No new public type, no new public method, and no new test class are added. Both clients carry this.

The shell strip uses the same header rule. A different tag on the nil cell starts a header. The same tag still appends. A known folded-bar mismatch stays outside this change: the shell glyph top is 108.2 and the status bottom is 64.1. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** In a merge whose input cells all have thinking on, each thinking block is preceded by the model header when the cell or the model changes. The same cell and the same model keep appending to the open block. The stored word is thinkStripWord. On the nil cell, a different tag starts a header and the same tag still appends.

**Reason.** A merge streams more than one model into one thinking pane. Without a header at each change, the blocks run together. Repeating the header while the cell and the model are unchanged would split one stream. The shell strip has no cell, so the tag is the stand-in, and the nil-cell path uses the same rule.

**Avoidances.** The header is not repeated for the same cell and the same model. The same tag on the nil cell does not start a new header. No send line moves. The folded-bar mismatch at 108.2 and 64.1 is not this change. The known paste-undo gap stays.


**semantic-key:** PK-4276 PK-4277 PK-4278 PK-4279

## The merge tip lists the input models

A tooltip hover on the merge output cell of a merge group shows every model defined on the input panes. A pipe separates those names. The model button leaves that cell. Models stay on the input cells.

mergeInputModels joins the short names in deck order. merge_input_models joins the short names in deck order. The focused caption uses that same list. The private method is mergeInputModels. The private function is merge_input_models. No new public type, no new public method, and no new test class are added. Both clients carry this.

The focused merge cell paints that same list. A focused cell paints the line and does not raise the hover tip. The stamp stays in front of the names. The execution target field stays on the group bar. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** Hovering the merge output cell lists every input model, short names in deck order, separated by a pipe. The model button is not on that cell. The focused caption paints the same list, with the stamp in front of the names, and does not raise the hover tip. Models remain on the input cells. The execution target field stays on the group bar.

**Reason.** The output cell is the join, not a model of its own. The reader needs every input model in one tip, in the order the deck already uses, without a model button that would imply the output cell has a model. Focus paints the line in place. Hover is for the cell that is not already showing that line.

**Avoidances.** The model button does not stay on the output cell. Models are not removed from the input cells. A focused cell does not also raise the hover tip. The stamp is not placed after the names. The execution target field does not leave the group bar. The wire does not change. The known paste-undo gap stays.


**semantic-key:** PK-4280 PK-4281 PK-4282 PK-4283 PK-4284

## The canvas prompt keeps an undo place

The canvas prompt keeps its own undo place. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo puts the caret where the edit began. Redo restores the caret after the edit. A paste is one span. Import is one whole-text step. The limit is 100.

The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The widget timer undoer is cleared. The module is edit. main.rs gains mod edit. No new public type, no new public method, no new test class, and no new library are added. The Rust client carries this. The Java client does not change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** The canvas prompt's undo stack holds at most 100 spans. The key queues a span and returns. Undo and redo drain the queue before they step. Undo places the caret where the edit began. Redo places the caret after the edit. A paste is one span. Import is one whole-text step. The widget timer undoer is cleared. The private type EditMemory lives in the edit module.

**Reason.** The prompt was not keeping the same caret place the cell undo already uses. Queuing the span on the key, then draining before the step, stops the caret from landing ahead of the edit. A paste is one gesture, so it is one span. Import replaces the whole text, so it is one step. Clearing the widget timer undoer keeps a second undoer from fighting this stack. The limit of 100 matches the session undo limit.

**Avoidances.** The Java client is not edited. The widget timer undoer is not left running. Import is not recorded as a character span. The library manifest is not edited. The wire does not change. The known paste-undo gap stays.


**semantic-key:** PK-4289 PK-4290 PK-4291 PK-4292 PK-4293 PK-4294 PK-4295

## One press places one cell

A press on I, E, P, or O places that cell only. The other cells stay, including a new turn at the bottom. The letter does not take focus. Space and Enter do not place it.

A pin commit does not ride that press. Enter in the pin field still commits on the frame the field loses focus. The next frame of that loss does not commit. Pull still starts a new turn Excluded. A new card still starts Included.

The private functions are seat_mark, seat_mark_button, mark_pressed, pin_enter_due, and pin_commit_after_mark. The field is pin_enter_seen. They live in chrome.rs. No new module file is added. No new public type, no new public method, no new test class, and no new library are added. The Rust client carries this. The Java client does not change.

A known folded-bar mismatch stays outside this change: the shell glyph top is 108.2 and the status bottom is 64.1. The thinking-strip check still splits on the tag rule, and that split sits outside this change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** One press on I, E, P, or O changes that cell and no other cell, including a new turn sitting at the bottom. The letter does not take focus. Space and Enter do not place the mark. A pin commit does not follow the press. Enter in the pin field commits on the frame the field loses focus, and the next frame of that loss does not commit again. Pull still starts Excluded. A new card still starts Included. The field that remembers the pin Enter is pin_enter_seen.

**Reason.** The press was placing more than the cell under the letter, and it was committing the pin and taking focus. The letter is a mark, not a focus target, so Space and Enter must not activate it. The pin field still needs Enter, but only on the frame where focus actually leaves, or the following frame commits a second time.

**Avoidances.** Other cells are not rewritten. The letter does not take focus. Space and Enter do not place the mark. The pin does not commit on the mark press, and it does not commit again on the next frame. Pull and a new card keep Excluded and Included. The Java client is not edited. The wire does not change. The folded-bar mismatch at 108.2 and 64.1, and the thinking-strip tag split, are not this change.

**semantic-key:** PK-6261

## Each user's run stays with that user

Each user's run and thinking stay with that user. A second user can run while the first is still running. The focused session does not paint another user's thinking on the glass. The glass user's execute leaves on that user's session.

Shell sessions already run on their own sockets and stay that way. The thinking scroll follows the user on the glass. The private functions are focused_canvas_home, glass_run_pane, think_salt, glass_pane, live_glass_pane, and land_focused_canvas. The test-only names are canvas_user, answer_mux, and take_wires. The files are chrome.rs and session.rs.

No new public type, no new public method, no new test class, no new library, no new module file, and no new operating-system thread are added. The parked deck stays. The same user does not reload. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the place. Every member frame still leaves together. One press still places one cell. The model header still starts each thinking stream. The Rust client carries this. The Java client does not change. The window stays closed as a build constraint of this rule. A known folded-bar mismatch stays outside this change: the shell glyph top is 108.2 and the status bottom is 64.1. The thinking-strip check still splits on the tag rule, and that split sits outside this change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** Two canvas users can run at the same time. Each run and each thinking stream stay with that user. The glass paints thinking only for the user on screen, and that user's execute leaves on that user's session. The thinking scroll follows the user on the glass. Shell sessions stay on their own sockets. think_salt keeps the streams apart. Replacing a socket still stops only that pane's run.

**Reason.** A parked deck kept the turn, and the glass was still painting the other user's thinking and sending the execute on the focused session. The run pane, the thinking salt, and the glass pane have to be the user who is on screen. The scroll follows that user so a stream does not sit at another user's offset. No new thread is added. The parked deck, the single thinking file, and the per-pane cancel stay.

**Avoidances.** Shell sockets are not merged. Another user's thinking is not painted on the focused glass. The thinking file is not split. No new operating-system thread is added. Member frames still leave together. One press still places one cell. The model header still starts each stream. The Java client is not edited. The doors do not change. The folded-bar mismatch at 108.2 and 64.1, and the thinking-strip tag split, are not this change.


**semantic-key:** PK-4306 PK-4307 PK-4308 PK-4309 PK-4310 PK-4311 PK-4312 PK-4313

## The thinking pane stays on the tail

When new thinking text arrives, the thinking pane scrolls to the bottom of its buffer before that text is painted. A reader who had scrolled up sees the new text as it streams. The same length leaves that scroll where the reader put it.

pinTail runs before the insert in begin and append, then again after the insert. loadStoredLog pins after the stored text is in the pane. think_tail_pin is true when the buffer grew. paint_think_row sets the scroll to the prior bottom before the new block is laid out, and scrolls the new tail into view. The notebook strip and the shell strip both do this. The private method is pinTail. The private functions are think_tail_pin and think_buffer_len. The declared check is a_new_think_block_pins_the_tail. The files are ThinkStrip.java and chrome.rs.

No new public type, no new public method, no new test class, no new library, no new module file, and no new operating-system thread are added. The per-user thinking salt stays. think_chrome stays global. The parked deck stays. The same user does not reload. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the place. Every member frame still leaves together. One press still places one cell. The model header still starts each thinking stream. The user tip stays. The footer spinner still follows the vault on screen. Both clients carry this. A known folded-bar mismatch stays outside this change: the shell glyph top is 108.2 and the status bottom is 64.1. The thinking-strip check still splits on the tag rule, and that split sits outside this change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** New thinking text is pinned to the tail before it is painted, and pinned again after the insert. A reader who scrolled up is brought to the new text as it streams. If the buffer length does not change, the scroll stays where the reader put it. Stored text is pinned after it is loaded. Both the notebook strip and the shell strip do this. think_tail_pin is true only when the buffer grew.

**Reason.** Painting first and scrolling after left the new block below the window the reader was holding, so a stream arrived unseen. Moving to the prior bottom before the new block is laid out, then bringing the new tail into view, shows the text as it arrives. A buffer that did not grow is not a new tail, so that scroll is left alone. The salt that keeps users apart, and the global thinking chrome, stay.

**Avoidances.** A same-length update does not move the scroll. The per-user salt stays. think_chrome stays global. The user tip stays. The footer spinner still follows the vault on screen. No new thread is added. The doors do not change. The folded-bar mismatch at 108.2 and 64.1, and the thinking-strip tag split, are not this change. The known paste-undo gap stays.


**semantic-key:** PK-4314 PK-4315 PK-4316 PK-4317 PK-4318 PK-4319 PK-4320 PK-4321 PK-4322

## Each user keeps a mood

Each user keeps their own mood. The line, the on switch, the pending flag, the pins, and the dialog size are that user's record. A pane's shell mood uses that pane's user. The checkbox stays in the shell map. The line and the switch come from that user's record.

The first user placed in a process claims the shared mood file only when no per-user mood file exists yet. A later user starts empty. A save writes that user's file. The shared file is not rewritten. A version other than 1 stays unread and is not overwritten. A blocked file is not overwritten.

The private method is adoptMoodUser. The private functions are mood_user_path, adopt_mood_user, and mood_for_user. The declared check is a_second_user_keeps_its_mood. The files are AetherisNotebookPanel.java and chrome.rs. The shell checkbox file stays aetheris-ssh-mood.yaml. MoodDesk.sshLine stays the gate rule. The version 1 body stays. The glass still shows the placed user's switch. Canvas sends still use the showing mood.

No new public type, no new public method, no new test class, no new library, no new module file, and no new operating-system thread are added. The same user does not reload. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the place. Every member frame still leaves together. One press still places one cell. The model header still starts each thinking stream. The user tip stays. The footer spinner still follows the vault on screen. Both clients carry this. A known folded-bar mismatch stays outside this change: the shell glyph top is 108.2 and the status bottom is 64.1. The thinking-strip check still splits on the tag rule, and that split sits outside this change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** The line, the on switch, the pending flag, the pins, and the dialog size belong to one user. A second user starts empty and does not inherit the first user's mood. The first user claims the shared mood file only when that user does not yet have a per-user file. Later saves write the user's file and do not rewrite the shared file. A version other than 1 is left unread and is not overwritten. The shell checkbox stays in aetheris-ssh-mood.yaml. The line and the switch for a pane come from that pane's user. The glass shows the placed user's switch. Canvas sends use the showing mood.

**Reason.** One shared mood file made every user wear the same line, switch, pins, and dialog size. The record is the user's. Claiming the shared file once, and only when no per-user file exists, keeps a single-user install from starting blank, and keeps a second user from copying the first. A version other than 1, and a blocked file, are not safe to rewrite. The checkbox map stays the shell map it already is. The gate rule on the desk does not change.

**Avoidances.** A later user does not start from the first user's mood. The shared file is not rewritten on save. A version other than 1 is not overwritten. A blocked file is not overwritten. The shell checkbox file stays aetheris-ssh-mood.yaml. MoodDesk.sshLine stays the gate rule. The doors do not change. The folded-bar mismatch at 108.2 and 64.1, and the thinking-strip tag split, are not this change. The known paste-undo gap stays.


**semantic-key:** PK-4323 PK-4324 PK-4325 PK-4326 PK-4327 PK-4328 PK-4329

# A merge cell takes scroll focus only when a cell in the group is clicked

The full cycle is named, and the build is named. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4330 PK-4331

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. This row is not a feature. A formal unit test is not declared. An outside-list row is not added.

Branch, action, stage, and the when field are not retargeted.

The edit below is the build. Our types take no lambdas. Lombok stays out. JUnit stays out. There is no new G-key, no new public type, no new public method, and no new package. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited. The private methods are seatGroupWheel and focusGroupCell.

The product tree compiles with tests skipped. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. Rust stays.

The product commit comes next. Its message states that the change is in the tree. Its message states that the change landed. Rust stays.

**semantic-key:** PK-7890

## Goal

A merge cell group leaves the wheel on the canvas scroll pane.

A cell in that group takes scroll focus only when that cell is clicked anywhere. The wheel on a focused text pane then stays with that cell, the same way a standard cell keeps the wheel after its text is clicked.

**semantic-key:** PK-6262

## What stays

A standard cell keeps the old press.

A click on its text focuses that text. A click on its chrome returns the wheel to the canvas. A press on a pulled-out scroll bar still returns the wheel to the canvas. The member row still drags sideways on its bar. The canvas column still tracks the viewport width. Slices already locked stay locked. The open batch already in the working tree stays open. Rust stays.

**semantic-key:** PK-6263

## Edit

On AetherisNotebookPanel, the member row is a sideways scroller, and seatGroupWheel gives the vertical wheel back to the canvas.

While that row is wider than the canvas, its own wheel listener takes a vertical wheel and scrolls the members sideways. seatGroupWheel turns that listener off, refuses focus on the row, and seats the canvas wheel on the row. A wheel that is not on a focused text pane moves the canvas bar. A click anywhere on a grouped cell, other than its scroll bar and other than the execution target, calls focusGroupCell. The text under the pointer takes the caret. A click on the chrome gives the caret to the prompt, then the reply, when that text is showing. The execution target keeps its own caret. Rust stays. A crate move for this wheel waits until it is named.

**semantic-key:** PK-6264

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This land leaves the working set as it stands.

**Truth.** A merge group does not take the canvas wheel until a cell in the group is clicked. seatGroupWheel turns off the member row's vertical-wheel listener, refuses focus on that row, and seats the canvas wheel on the row. focusGroupCell puts the caret on the text under the pointer, or on the prompt and then the reply when the click is chrome. The execution target and the scroll bar keep their own behavior. Rust stays.

**Reason.** While the member row is wider than the canvas, its listener was taking a vertical wheel and scrolling members sideways, so the canvas scroll pane lost the wheel. The group should leave that wheel alone until a cell is clicked, and then keep it the way a standard cell keeps the wheel after its text is clicked.

**Avoidances.** Do not open a second revision. Do not retarget branch, action, stage, or the when field. Do not add a lambda, Lombok, JUnit, a G-key, a public type, a public method, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not change the standard cell press, the sideways drag, or the canvas column's tracking of the viewport width. Do not let a scroll-bar press or a click on chrome of a standard cell keep the wheel off the canvas. Do not give focusGroupCell the scroll bar or the execution target. Do not move Rust until that move is named. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.

**semantic-key:** PK-7911

# The assistant reply frame is sapphire

The full cycle is named, and the build is named. Java moves. Rust stays. The working word is NUT, and that word stays the owner's. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4332

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. This row is not a feature. A law is not added. An outside-list row is not added. A glossary row is not added.

Branch, action, stage, and the when field are not retargeted.

The edit below is the build. Our types take no lambdas. Lombok stays out. JUnit stays out. There is no new G-key, no new public type, no new public method, and no new package. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited.

The product tree compiles with tests skipped. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The Java product commit follows. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend. Rust stays.

**semantic-key:** PK-7891

## Goal

The assistant reply pane in every Java Aetheris canvas cell wears a sapphire frame.

The input pane stays green.

**semantic-key:** PK-6265

## What stays

The input scroll frame stays medium green. The input text stays input green. An empty reply stays gray.

The card edge stays the pin match and the armed sapphire. Rust cell frames stay. The thinking-cluster seat stays locked. The open batch already in the working tree stays open.

**semantic-key:** PK-6266

## Edit

On AetherisNotebookPanel, the assistant scroll, when the cell has a reply, takes SELECTION_SAPPHIRE.

That color is already the armed-card sapphire, 0x2e86ff. The input scroll keeps MEDIUM_GREEN.

**semantic-key:** PK-6267

## Proof

The product tree compiles with tests skipped on the working tree. JAVA_HOME is the Zulu 26.0.2 JDK.

The window stays closed. The proof does not make a live POST. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. A clean checkout was not recompiled. The crate is not checked. Rust stays.

**semantic-key:** PK-7921

## Scope

Java is named, and the crate stays until the catch-up is named.

There is no side move on this slice. The working set stays. The next marker is the one after that spend.

**semantic-key:** PK-6268

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

**Truth.** A Java canvas cell that has a reply paints the assistant scroll in SELECTION_SAPPHIRE, 0x2e86ff. The input scroll stays MEDIUM_GREEN. An empty reply stays gray. Rust cell frames do not move.

**Reason.** The reply frame was the same medium green as the input. The armed-card sapphire already exists, so the reply scroll reuses that color instead of a new color key. The input stays green so the two panes stay distinct.

**Avoidances.** Do not open a second revision. Do not retarget branch, action, stage, or the when field. Do not move Rust until the catch-up is named. Do not add a lambda, Lombok, JUnit, a G-key, a public type, a public method, a package, a vault column, a channel, an AMWP type, or a library, and do not edit Cargo.toml. Do not change the input green, the input text green, the empty-reply gray, or the card edge. Do not check the crate. Do not recompile a clean checkout as this proof. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.

**semantic-key:** PK-7912

# One seat keeps the mark letters live

The filing records the words and the seat. A later sentence names the bake. seatMark is that build. Java stays on the filing. Java moves on the product commit. Rust is unaffected. The working word is NUT, and that word stays the owner's. This filing adds no glossary row. Where this page is thinner than the locked row, the locked row wins.

**semantic-key:** PK-6269

## Standing

The working field stays the stage map. The when field stays the date already written on the resume card. This filing does not move that point.

**semantic-key:** PK-6270

## Filing

The plan stays open until review. The locked row records the words and this seat. Slices already locked are not rewritten. No smoke is filed. A smoked mark waits until a smoke is filed. This row is not a feature. A law is not added. An outside-list row is not added. A glossary row is not added for NUT.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker.

This filing writes no Java and no Rust. There is no lambda in these types. There is no Lombok. There is no new G-key, no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited. No new package is added. seatMark is named for a later bake, and this filing does not add it.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The Rust client crate stays unrun.

No product is in that commit. A later sentence names the bake. That sentence spends the build. This filing does not.

**semantic-key:** PK-6271

## Bake

The bake sentence names the build. seatMark is the build. The press on a mark calls it. Ctrl-Shift I, E, P, and O call it. The four marks are plain buttons. They are not focusable. The card listener does not steal focus from a mark. The gesture does not call rebuildTabs. The seated letter wears PLATE_SELECT and PLATE_SELECT_INK. Ghost stays EXCLUDED. Rust stays.

Branch, action, stage, and the when field are not retargeted.

The product tree is compiled with tests skipped. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The Rust client crate stays unrun.

The product commit is the notebook and the mark face. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-6272

## Goal

One method seats a mark. I, E, P, and O stay live on the first press, on a press that leaves the button, and on Ctrl-Shift I, E, P, and O. Focus, arm, and release do not decide whether the cell is written. The live card shows the letter and the ghost. The deck stays.

**semantic-key:** PK-6273

## What stays

CellState stays INCLUDED, EXCLUDED, INPUT_ONLY, and OUTPUT_ONLY. Ghost stays EXCLUDED. The four letters stay I, E, P, and O. The tips stay. fitAetherisButton stays. The import popup stays. armFocus may still record the card. persistQuiet stays the save. refreshInspector may still rewrite the inspector. It does not rebuild the deck.

PlateFaceUi keeps the idle fill, the hover fill, the hover edge, and the press fill. The seated letter adds the existing PLATE_SELECT fill and PLATE_SELECT_INK. Those colors are already on the theme. No new color key is added.

The pin path stays. application.yaml stays. chrome.rs stays. grid.rs stays. The open batch already in the working tree stays open. Slices already locked stay locked. The closed feature stays closed. The next free number stays the number already next. The outside-list number already next stays next. Source-prose tags wait for the bake sentence. This filing adds none.

**semantic-key:** PK-6274

## The cause

The four marks are JToggleButtons in one ButtonGroup. stateButton writes cell.state inside the action, then calls armHold, persistQuiet, rebuildTabs, and refreshInspector. On Zulu 26, ToggleButtonModel.setPressed(false) fires that action only while the model is still armed. BasicButtonListener.focusLost calls setPressed(false) and then setArmed(false). mouseExited calls setArmed(false) and writes nothing.

installImportPopup runs on the button. Its mousePressed calls armFocus, then focusCanvas on an ordinary card or focusGroupCell on a grouped card. That runs while the button is armed and pressed, so focusLost runs the action inside the press. rebuildTabs calls canvasTabs.removeAll and builds every card again. applyUiFont refits the notebook. restoreHold runs later, moves the viewport, and focusHeader lands on the collapse button. The matching release is not delivered. The button is already gone.

A press that is still armed writes the letter. The rebuilt card ghosts when the letter is E. A press that has already lost armed writes nothing. armFocus still refreshes the inspector, so the screen still moves and the cell stays included. A later press, once the header is still, stays armed and the write lands. PlateFaceUi does not paint isSelected, so the four letters look the same when the mouse is up. E shows up as the ghost, on the card built at the end of the rebuild.

Ctrl-Shift I, E, P, and O write the state and then call the same rebuild. They are the same seat. A JButton is the same miss. DefaultButtonModel fires the action from setPressed(false) while armed, and focusLost uses the same order. mouseExited still clears armed first. The seat is the press, off the button model.

**semantic-key:** PK-6275

## The seat

seatMark(cell, state) is the one method. It writes cell.state. It paints the four faces from that state. It repaints the card, so the ghost follows EXCLUDED and clears when the state is any other mark. It calls persistQuiet. It may call refreshInspector. It does not call rebuildTabs. It does not call armHold. It does not call focusHeader. It does not move the viewport.

The four marks are plain buttons. They are not a ButtonGroup. They are not JToggleButtons. setFocusable(false). setRequestFocusEnabled(false). There is no ActionListener. The button's own mousePressed calls seatMark for that letter, then consumes the gesture. The release does not call seatMark. A second press of the letter already seated writes that same state again and leaves the deck in place.

installImportPopup still walks the button. When the source is a mark, that listener may call armFocus and may still open the import popup. It does not call focusCanvas. It does not call focusGroupCell. It does not request focus.

The mark face reads cell.state. The seated letter paints with PLATE_SELECT and PLATE_SELECT_INK. The other three paint the idle plate. Hover and press stay the plate hover and the plate press. ButtonModel.isSelected is not the paint. A focus change cannot clear the letter.

Ctrl-Shift I, E, P, and O call seatMark and return. That listener does not call rebuildTabs.

The gesture updates the live card. A rebuild that some other gesture still needs stays on that gesture. A mark does not post one. If a later reading still wants a rebuild after a mark, that rebuild is invokeLater after the current mouse event returns, and it is not the path this seat takes.

**semantic-key:** PK-6276

## Rust

Rust is unaffected. This issue is Java. chrome.rs mark_button is an egui selectable with the class aetheris.mark. The four clicks assign Included, Excluded, Input, or Output on marks[i] in that same show pass and set clicked_card. There is no ButtonGroup. There is no focusLost. There is no rebuild that drops the release. Immediate mode builds the widgets again each frame. egui keeps the click by id. No Rust proposal is filed. chrome.rs stays. grid.rs stays. The match already named in the spec stays as it stands.

**semantic-key:** PK-6277

## Wire

No system line is added, removed, or reordered. The doors stay. The yaml stays.

**semantic-key:** PK-6278

## Proof

This filing runs no product compile and no crate check. The window stays closed.

The bake proof compiles the product tree with tests skipped. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The Rust client crate stays unrun. The crate stays unwritten.

The bake shows one press of E writes EXCLUDED and ghosts the live card, with the viewport left where it was. A press that leaves the button before release still writes. I, P, and O use seatMark. Ctrl-Shift I, E, P, and O use seatMark. The seated letter wears PLATE_SELECT. The other three stay idle. Rust stays unwritten on that bake.

The bake names seatMark(cell, state). A press calls it. Ctrl-Shift I, E, P, and O call it. The four marks are plain buttons, not focusable, not a ButtonGroup, and not JToggleButtons. setFocusable(false). setRequestFocusEnabled(false). There is no ActionListener. mousePressed calls seatMark and consumes the gesture. Release does not. The gesture does not call rebuildTabs, armHold, or focusHeader, and it does not move the viewport. The seated letter wears PLATE_SELECT and PLATE_SELECT_INK. Ghost stays EXCLUDED. The live card updates. The deck stays. Rust stays.

**Reason.** On Zulu 26, ToggleButtonModel.setPressed(false) fires the action only while the model is still armed. focusLost presses off and then disarms, and mouseExited disarms and writes nothing. installImportPopup steals focus during the press, rebuildTabs removes every card, and the release is never delivered. A press that is still armed writes and then ghosts E on the rebuilt card. A press that has already lost armed writes nothing. PlateFaceUi does not paint isSelected. The seat is the press, off the button model. A JButton misses the same way because DefaultButtonModel fires from setPressed(false) while armed.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not file a smoke on the filing, and do not mark the row smoked before a smoke is filed. Do not spend the marker on the bake stamp. Do not retarget branch, action, stage, or the when field. Do not add seatMark on the filing. Do not add a lambda, Lombok, a G-key, a vault column, a channel, an AMWP type, a color key, a library, or a package, and do not edit Cargo.toml. Do not add a source-prose tag on the filing. Do not leave the marks as JToggleButtons or in a ButtonGroup. Do not keep an ActionListener on the mark. Do not let release call seatMark. Do not let focus, arm, or release decide the write. Do not call rebuildTabs, armHold, or focusHeader from seatMark, and do not move the viewport from it. Do not let the card listener call focusCanvas or focusGroupCell from a mark, and do not let it request focus. Do not paint from ButtonModel.isSelected. Do not add a color key. Do not drop idle, hover, hover edge, or press fill from PlateFaceUi. Do not change CellState away from INCLUDED, EXCLUDED, INPUT_ONLY, and OUTPUT_ONLY. Do not change the letters I, E, P, and O, the tips, fitAetherisButton, the import popup, persistQuiet, or the pin path. Do not let refreshInspector rebuild the deck. Do not let a second press of a seated letter rebuild the deck. Do not drop armFocus when it only records the card. Do not change application.yaml, chrome.rs, or grid.rs. Do not file a Rust proposal. Do not change mark_button, the class aetheris.mark, or the same-pass write of Included, Excluded, Input, or Output on marks[i]. Do not drop clicked_card. Do not add, remove, or reorder a system line. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not run a crate check. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-6279

# The mood call uses the armed cell on the showing canvas

The stamp names the build. The mood call uses the model of the armed cell. The user selected Grok on that cell. The call used Ollama. The constructed mood stays the saved line. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4333 PK-4334 PK-4335

## Approval

This sentence names the build. The mood call uses the armed readable cell on the showing canvas. A readable cell on a hidden canvas is a stale arm. The Ollama pane writes the cell while the family item is Ollama. A Grok or Pool item leaves that write idle. A missing family combo keeps the old write. The console path stays. The mood triple stays. The execute target follows that armed cell.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The product tree is compiled with tests skipped. No new JUnit class is added. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The Rust client crate stays unrun.

The product commit is AetherisNotebookPanel. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-6280

## Goal

The mood dialog shapes with the model of the armed cell on the canvas that is showing. A Grok card sends grok and that card's model id. An empty target sends ollama. A pool card sends pool. The same cell is the cell Run uses when that cell is the armed cell on the showing canvas.

**semantic-key:** PK-6281

## What stays

moodTarget stays. sendMood still sets body.executionTarget from moodTarget on the card whose id was stored at open. outboundTarget stays. An empty stored target still leaves as ollama. A grok provider with an empty model id still fills from the Grok document for that card when the model is non-blank. The mood triple stays. MoodDesk stays. OPENER, CLOSER, and LEGACY stay. The constructed mood stays the saved line. The footer switch stays the canvas gate. The SSH checkbox stays the shell gate. The mark seat stays. The earlier speaker row stays locked. The one-desk row stays locked. The triple row stays locked. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The next free number stays the number already next. The outside-list number already next stays next. No new public type is added. No new public method is added. No new G-key, vault column, channel, AMWP type, color key, library, or Cargo.toml edit is added. No glossary row is added. The match already named in the spec stays. aa-sdd-source-prose.md stays. chrome.rs stays. grid.rs stays. application.yaml stays. The yaml mood files stay.

**semantic-key:** PK-6282

## The cause

The shaping send still sets the execute target from the card. moodTarget has stood since the seat that introduced it. sendMood last took the mood header on the later seat that stored it. The one-desk row and the triple row left that read in place. The server completes grok on a grok target and ollama on an empty target or an ollama target. A grok target with no model id returns no model defined. An Ollama reply means the card that was read stored an empty target or an ollama target.

moodCard asked findCell for focusedCellId. That search accepts a readable cell on any canvas. The method comment said a stale arm falls through to the showing canvas. The code fell through when the cell was missing or unreadable. A readable cell on a hidden canvas stayed the speaker. Run and the engine word use the showing canvas. A readable Ollama cell on a hidden canvas beat the Grok cell in front. On one canvas, with a readable armed cell, the two reads name the same cell. The earlier speaker row stays locked. This row states the speaker.

A Swing probe clicked the Ollama editor, then the family combo, then chose Grok. Focus loss wrote ollama while the combo still showed Ollama. The Grok item then wrote grok. The grok write stayed after the deck showed the Grok pane. The row does not treat that click as the cause. The Ollama editor keyReleased and commitOllamaModel still write an ollama target with no look at the family item. A later key or a later focus loss can paint ollama after the family item is already Grok or Pool.

**semantic-key:** PK-6283

## The seat

moodCard reads focusedTurn. A readable cell returns. A missing arm, or an unreadable arm, falls through to targetCell. A readable targetCell returns. Else the last readable cell on the showing canvas returns. None returns null. openMood then sets the status to no cell and the dialog stays closed. The comment states the showing-canvas arm. The tag already on the method stays. This plate's tag sits on the method.

sendMood still loads the id captured at open and still calls moodTarget. The dialog stays modal, so the arm does not change while it is open.

ollamaPaneOwnsCell returns true when the family combo is missing. It returns true when the selected item is Ollama. It returns false for Grok, for Pool, and for an empty selection. commitOllamaModel calls it on the cell path, after the console branch and after the adjusting guard. The console path still calls commitConsoleModel and does not call ollamaPaneOwnsCell. The Ollama editor keyReleased calls ollamaPaneOwnsCell after its existing guard. The family listener stays. applyGrokFamily still writes the grok target.

The private methods are moodCard and ollamaPaneOwnsCell. moodCard is the method already seated for the speaker. This plate changes its first read. ollamaPaneOwnsCell is new and private.

**semantic-key:** PK-6284

## Rust

Rust stays. begin_mood reads cards.get(focus) on the slice the showing canvas already holds. mood_target reads that card. An empty word is ollama. A pool card sends pool. chrome.rs stays. grid.rs stays. No crate delta is taken. No Rust proposal is filed on this plate.

**semantic-key:** PK-6285

## Wire

The mood triple stays. Canvas, group, and pipe keep the empty host seat, the opener, the constructed mood, and the closer while the footer switch is on and the line is non-blank. SSH keeps the pinned host, the opener, the constructed mood, and the closer while the footer switch is on and the checkbox is checked and a line is saved. Glass keeps the shaping line, then the opener, the field text, and the closer. Pool session/new keeps the porch rules. The host stays out of that porch.

The mood execute execution_target follows the armed cell on the showing canvas. An empty target is ollama. A Grok card sends grok and that card's model id. A pool card sends pool.

No new channel is added. No new AMWP type is added. The doors stay. The yaml stays.

**semantic-key:** PK-6286

## Proof

The product tree is compiled with tests skipped. No new JUnit class is added. A formal test stays off this plate. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The Rust client crate stays unrun. The crate stays unwritten.

**Truth.** moodCard reads the armed readable cell on the showing canvas. A readable cell on a hidden canvas is a stale arm. A missing or unreadable arm falls through to targetCell, then to the last readable cell on the showing canvas, and otherwise returns null. openMood then sets the status to no cell and the dialog stays closed. ollamaPaneOwnsCell writes only while the family item is Ollama, or while the family combo is missing. Grok, Pool, and an empty selection leave that write idle. The console path stays on commitConsoleModel. applyGrokFamily still writes the grok target. A Grok card sends grok and that card's model id. An empty target sends ollama. A pool card sends pool. Rust stays.

**Reason.** findCell accepted a readable cell on any canvas, so a readable Ollama cell on a hidden canvas beat the Grok cell in front. The comment promised a fall-through to the showing canvas, and the code fell through only when the cell was missing or unreadable. The Ollama editor keyReleased and commitOllamaModel still wrote an ollama target with no look at the family item, so a later key could paint ollama after the family item was already Grok or Pool. The server completes grok on a grok target and ollama on an empty target or an ollama target.

**Avoidances.** Do not rewrite slices already locked, the earlier speaker row, the one-desk row, the triple row, or the mark seat. Do not close the open batch or the closed feature. Do not change application.yaml, the yaml mood files, aa-sdd-source-prose.md, chrome.rs, or grid.rs. Do not add a public type, a public method, a G-key, a vault column, a channel, an AMWP type, a color key, a library, a Cargo.toml edit, a glossary row, or a JUnit class. Do not retarget branch, action, stage, or the when field. Do not move Rust or take a crate delta. Do not change moodTarget, outboundTarget, OPENER, CLOSER, LEGACY, MoodDesk, the constructed mood, the footer switch, or the SSH checkbox. Do not let sendMood stop setting body.executionTarget from moodTarget on the card stored at open. Do not let an empty stored target leave as anything other than ollama. Do not stop a grok provider with an empty model id from filling from the Grok document when the model is non-blank. Do not let the arm change while the modal dialog is open. Do not let a Grok, Pool, or empty family selection write an Ollama id. Do not let a missing family combo drop the old write. Do not call ollamaPaneOwnsCell from the console path. Do not drop the family listener or applyGrokFamily. Do not drop the tag already on moodCard. Do not treat the Swing probe click as the cause. Do not put the host into the pool porch. Do not add, drop, or reorder the mood triple. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not run a formal test or a crate check. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-6287

# A cell key posts the token walk

The stamp names the build. Typing in a Java cell input stops waiting on the token walk. The count stays accurate as the edits catch up. This is an enhancement. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4336 PK-4337 PK-4338 PK-4339 PK-4340 PK-4341 PK-4342 PK-4343 PK-4344 PK-4345 PK-4346 PK-4347 PK-4348 PK-4349 PK-4350 PK-4351

## Approval

This sentence names the build. A cell key writes the draft and posts the edit. The transcript walk runs off the key. The newest edit is the one that runs. A result paints when its stamp is still current. A click still measures on that call. The heuristic, the marks, the heatbar, and the token window stay.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The product tree is compiled with tests skipped. No new JUnit class is added. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The Rust client crate stays unrun.

The product commit is AetherisNotebookPanel. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-6288

## Goal

Typing in a canvas cell input returns without waiting on the armed transcript. The same heuristic still paints. The heatbar still reads that integer on its beat. A click still has the integer before the call returns.

**semantic-key:** PK-6289

## What stays

The draft text is still written on the key. The live-draft seat stays. The armed walk stays transcriptThroughArm. HeuristicEstimator stays. The four marks stay. Included, Excluded, Input only, and Output only stay. GHOST stays excluded. The mood head stays. Pipe resolve stays. An empty transcript with no mood head stays 0. paintTokenEstimate still stores the integer, the character count, and the block count. notebookDip.watch still runs when that integer is painted, on the EDT. tokenRingUsed still returns that integer. The token window still opens on those three fields. The heatbar still reads tokenRingUsed on the status beat. A settled host count still does not replace the word. The settled-host seat stays. The SSH heat path still uses sshLiveCount. The live SSH count seat stays. SshClientApp stays. A click, a mood change, and a structural change still measure before the call returns. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The next free number stays the number already next. The outside-list number already next stays next. No new public type is added. No new public method is added. No new G-key, vault column, channel, AMWP type, color key, library, or Cargo.toml edit is added. No glossary row is added. The match already named in the spec stays. aa-sdd-source-prose.md stays. chrome.rs stays. grid.rs stays. application.yaml stays.

**semantic-key:** PK-6290

## The cause

The cell document listener calls noteLiveDraft on the EDT for insert, remove, and change. noteLiveDraft copies the pane text onto the cell, then calls refreshInspector. refreshInspector builds the inspector text and walks transcriptThroughArm. That walk renders the armed transcript and HeuristicEstimator counts the whole rendered string. The key does not return until that count is painted. liveTokenHold only stops a document event from reentering the same call. It is not a queue. The heatbar does not calculate. It reads the integer already stored. The 200 ms beat is that read. The measurement is what blocks the key.

**semantic-key:** PK-6291

## The seat

noteLiveDraft still writes cell.userText before it returns. The first key on an unarmed card calls armFocus with the draft flag. The arm chrome, the status line, the delete button, and the caption still update on that call. The call then posts the edit and schedules the inspector. It does not walk. A key on a card that is already armed posts the edit and schedules the inspector. A click still calls armFocus with the measure flag, and that path still calls refreshInspector.

refreshInspector paints the inspector, then measures. paintInspectorView is the inspector text, the facts line, and the mood line. It does not walk. measureTokensNow claims the next stamp, drops any edit that has not started, walks transcriptThroughArm, and paints when that stamp is still current. An empty canvas claims a stamp and paints 0, so a late edit cannot overwrite a newer empty.

The cell key schedules paintInspectorView once. Later keys share that paint. The paint runs after the key returns. The walk is not inside that paint.

TokenEditQueue is a private class. Its thread is a daemon named aetheris-token-edit. The panel starts it in the constructor. offer runs on the EDT. It assigns the next stamp and keeps only the newest edit that has not started. The worker does not touch Swing. It takes one edit. When a newer edit is already waiting, it drops the one it took. Otherwise it asks the EDT for a copied seat and waits only for that copy. The copy holds the fields the walk reads: id, column position, group id, state, readable, ephemeral, user text, assistant text, and execution target. The mood head and the cloud bit are taken on the EDT before the copy leaves. The worker then runs transcriptFrom on the copy. A torn seat drops that pass. The next edit catches up. The result is painted on the EDT when its stamp is still the newest claim and still the newest paint, and when the card that posted it is still the armed card.

transcriptThroughArm still reads the showing canvas and calls transcriptFrom. transcriptFrom does not read the showing canvas. addGroupTurn strips join banners with membersOn. membersOn reads the ordered list the walk already holds. That list is the canvas order, so the strip matches the members the click already used. The worker does not call groupMembers.

The private classes are TokenEdit, TokenSeat, and TokenEditQueue. The private methods that this plate adds are paintInspectorView, measureTokensNow, claimTokenSequence, acceptTokenPaint, scheduleInspectorView, offerTokenEdit, transcriptFrom, membersOn, captureTokenSeat, and copySeatCell. armFocus gains the draft flag. No new public type is added. No new public method is added.

**semantic-key:** PK-6292

## Rust

Rust stays. The Rust canvas already counts the live prompt on the frame, off the key. chrome.rs stays. grid.rs stays. No crate delta is taken. No Rust proposal is filed on this plate.

**semantic-key:** PK-6293

## Wire

No send line moves. No system line is added, removed, or reordered. The doors stay. The yaml stays.

**semantic-key:** PK-6294

## Proof

The product tree is compiled with tests skipped. No new JUnit class is added. A formal test stays off this plate. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The Rust client crate stays unrun. The crate stays unwritten.

**Truth.** A cell key writes cell.userText and returns without walking the armed transcript. The first key on an unarmed card calls armFocus with the draft flag. A key on an armed card posts the edit and schedules the inspector. A click still calls armFocus with the measure flag and still calls refreshInspector. paintInspectorView paints the inspector and does not walk. measureTokensNow claims the next stamp, drops an edit that has not started, walks transcriptThroughArm, and paints only when that stamp is still current. An empty canvas paints 0. TokenEditQueue is private. Its daemon thread is named aetheris-token-edit. The worker does not touch Swing. The copy carries id, column position, group id, state, readable, ephemeral, user text, assistant text, and execution target. The mood head and the cloud bit are taken on the EDT. A torn seat drops that pass. The result paints on the EDT only when the stamp is still the newest claim and the newest paint and the card is still armed. The private classes are TokenEdit, TokenSeat, and TokenEditQueue. Rust stays.

**Reason.** noteLiveDraft called refreshInspector on the EDT, and that walk counted the whole rendered transcript before the key could return. liveTokenHold only stops reentry. It is not a queue. The heatbar reads the integer already stored. The 200 ms beat is that read. The measurement is what blocks the key. The newest edit is the one that runs, and a result paints only while its stamp is still current.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not change application.yaml, aa-sdd-source-prose.md, chrome.rs, grid.rs, or SshClientApp. Do not add a public type, a public method, a G-key, a vault column, a channel, an AMWP type, a color key, a library, a Cargo.toml edit, a glossary row, or a JUnit class. Do not retarget branch, action, stage, or the when field. Do not move Rust or take a crate delta. Do not let the key wait on the walk. Do not stop writing the draft on the key. Do not change HeuristicEstimator, the four marks, Included, Excluded, Input only, Output only, or GHOST. Do not change the mood head or pipe resolve. Do not let an empty transcript with no mood head leave 0. Do not change paintTokenEstimate, notebookDip.watch, tokenRingUsed, or the three fields of the token window. Do not let the heatbar calculate. Do not drop the 200 ms beat. Do not let a settled host count replace the word. Do not move the SSH heat path off sshLiveCount. Do not let a click, a mood change, or a structural change return before it measures. Do not let the worker touch Swing or call groupMembers. Do not let transcriptFrom read the showing canvas. Do not let a late edit overwrite a newer empty. Do not paint a result whose stamp is no longer current or whose card is no longer armed. Do not drop a torn seat into the paint. Do not treat liveTokenHold as a queue. Do not add, remove, or reorder a system line. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not run a formal test or a crate check. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-8198

# Debug Mood paints the triplet

The stamp names the build. File, Settings carries Debug Mood. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4352 PK-4353 PK-4354 PK-4355 PK-4356 PK-4357 PK-4358 PK-4359

## Approval

This sentence names the build. File, Settings opens the window. The checkbox is Debug Mood. On, the inspector paints the mood triplet as one card above the turn the pointer seats. The card is the opener, the line, and the closer. Off leaves the inspector as it is. Painting the card does not move the pin. The send stays the splice already seated.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

No crate test is run. The Rust client crate is not checked. Rust stays. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The product commit is AetherisNotebookPanel.java and the Settings menu item in SshClientApp.java. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-6295

## Goal

The user can read the mood triplet in the inspector, on the card above the turn the pointer seats, and see the same opener, line, and closer the wire puts at the head of that turn.

**semantic-key:** PK-6296

## What stays

The send stays the splice already seated. The empty host seat stays. The server still replaces it. Vault cell text stays unspliced. The pin file stays. A paint does not consume pending. A Run still does. The mood header on the inspector stays. Glass appendMoodHeader stays. The switch does not gate that chat. The shell frame stays. SSH has no inspector card on this plate. The Rust Settings window stays Painted text borders. chrome.rs stays. grid.rs stays. application.yaml stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The next free number stays the number already next. The outside-list number already next stays next. No new G-key, vault column, channel, AMWP type, color key, library, or Cargo.toml edit is added. No glossary row is added. aa-sdd-source-prose.md stays. No new public type is added. No new JUnit class is added.

**semantic-key:** PK-6297

## The cause

The splice sits on the head of one user turn. The inspector shows the raw cell text. The triplet is not visible where the pointer seats it. The ask is a Debug Mood checkbox on the File settings window, and that triplet as a card above the associated turn.

**semantic-key:** PK-6298

## The seat

File, Settings opens the window on the Java door. The checkbox is Debug Mood. It starts off. On, the inspector paints one card above the turn the pointer seats. The inspector lists the newest card first, and the mood card is painted immediately before that turn. The card lines are the opener, the line, and the closer, the same three lines spliceTurn puts at the head of that turn. The role word on each line is mood. Off leaves the inspector as it is. The existing mood header stays. A pending move is shown where the next Run would seat it, and the paint does not write the pin. A Run still consumes pending. Zero or one turn already ahead of the target keeps the card on the first turn. More than one, and a pending define or update, puts the card on the turn being sent. An excluded pin shows the card on the first included cell at or after the pin that this working set would send. A working set whose target sits before the pin shows no card. The switch off shows no card. The choice is debug_mood in aetheris-settings.yaml, version 1. A missing file stays off and is not written until the switch moves. A bad file stays off and is not overwritten. Other keys in that file, including painted_borders, are written back. Rust already keeps unknown keys. This plate does not edit the crate. The public method is openSettings. The nested seat is SettingsSeat. No new public type is added. No new JUnit class is added. SshClientApp gains the menu item. chrome.rs stays. grid.rs stays.

**semantic-key:** PK-6299

## Proof

The Rust client crate was not checked. A crate test was not run. The window stayed closed. No live POST was made. A pool turn was not sent. A live SSH connect was not made. Session::start was not called. chrome::run was not called.

**semantic-key:** PK-6300

## The tree

AetherisNotebookPanel.java holds the window, the yaml seat, and the inspector card. SshClientApp.java gains Settings on the File menu. No other Java file moves. No Rust file moves.

**semantic-key:** PK-6301

## Side move

One side move is named. The settings file is shared with the Rust door. Java writes debug_mood and keeps every other key so painted_borders survives a Java save. The requirement is a choice that is still there on the next open. The sibling is chrome.rs. That file is not edited.

**semantic-key:** PK-6302

## Wire

No system line is added, removed, or reordered. The working set stays the splice already seated. The card is inspector paint. The doors do not gain, lose, or reorder a system line.

**Truth.** File, Settings opens a Java window whose checkbox is Debug Mood, and it starts off. On, the inspector paints one card immediately before the turn the pointer seats, listing the newest card first. The card lines are the opener, the line, and the closer, and the role word on each line is mood. Off leaves the inspector as it is. A paint does not write the pin and does not consume pending. The choice is debug_mood in aetheris-settings.yaml at version 1. A missing file stays off until the switch moves. A bad file stays off and is not overwritten. Other keys, including painted_borders, are written back. The public method is openSettings. The nested seat is SettingsSeat. Rust stays.

**Reason.** The splice sits on the head of one user turn, and the inspector shows the raw cell text, so the triplet is not visible where the pointer seats it. The card is paint. It is not a second write of the pin.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not change chrome.rs, grid.rs, aa-sdd-source-prose.md, the paging memorandum, or application.yaml. Do not move Rust or check the crate. Do not add a G-key, a vault column, a channel, an AMWP type, a color key, a library, a Cargo.toml edit, a glossary row, a public type, or a JUnit class. Do not add a public method other than openSettings. Do not retarget branch, action, stage, or the when field. Do not start Debug Mood on. Do not let a paint move the pin or consume pending. Do not drop the existing mood header. Do not show a card when the switch is off, when the target sits before the pin, or on SSH. Do not change the send away from the splice already seated. Do not drop the empty host seat. Do not splice vault cell text. Do not drop painted_borders or any other key on a Java save. Do not write a missing file before the switch moves. Do not overwrite a bad file. Do not change the Rust Settings window away from Painted text borders. Do not let the switch gate the glass. Do not add, remove, or reorder a system line. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-6303

# Debug Mood shows the head on its prompt

The stamp names the build. Debug Mood shows the insert where the wire places it, immediately before the prompt the pointer seats, with no card number. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4360 PK-4361 PK-4362

## Approval

This sentence names the build. Debug Mood paints the invitation immediately before the user line the pointer seats. The lines carry no card number. The rest of the inspector stays. Painting does not move the pin.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

No crate test is run. The Rust client crate is not checked. Rust stays. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The product commit is AetherisNotebookPanel.java. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-6304

## Goal

Debug Mood is a visual of the wire head on the prompt the pointer seats. The lines read mood:, the line, shape:, the opener, and the closer, then that user line. They do not take a card number.

**semantic-key:** PK-6305

## What stays

The inspector still builds oldest first and reverses once. The header stays. The separators stay. The role lines keep their numbers. The facts line stays. The pin rules stay. debugSpliceId still previews and does not write the pin. The send stays the invitation splice. Glass stays. moodHead stays. The shell frame stays. debug_mood stays. The settings seat stays. The yaml version stays 1. The Debug Mood window row stays locked. The invitation row stays locked. This row overrides the separate debug card. The paste gap already seated stays. chrome.rs stays. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The next free number stays the number already next. The outside-list number already next stays next. No new G-key, vault column, channel, AMWP type, color key, library, or Cargo.toml edit is added. No glossary row is added. aa-sdd-source-prose.md stays. No new public type is added. No new public method is added. No new JUnit class is added. A law is not added.

**semantic-key:** PK-6306

## The cause

The debug paint was its own card, added after the turn and flipped above it by the reverse. The wire head is not its own card. It is the start of the user message the pointer seats. The ask is that head in the inspector, on that prompt, in the reverse the inspector already does. The card number stays blank. The rest of the inspector stays.

**semantic-key:** PK-6307

## The seat

When Debug Mood is on and the pointer seats a user line, that card opens with the splice head and then the user line. The head is mood:, the saved line, shape:, the opener, and the closer. The opener stays: I'm opening a window with this atmosphere… would you like to step into it with me? The closer stays: Shape your replies around this mood — let it breathe through the words. Those four lines carry no card number and do not advance the number. The user line and the assistant line keep the numbers they had. A block that does not open with the user role is left as it is. Off leaves the inspector as it is. Painting does not move the pin.

**semantic-key:** PK-6308

## The tree

AetherisNotebookPanel.java paints the head on the user line and skips the number. No other Java file moves. No Rust file moves. SshClientApp stays parked.

**semantic-key:** PK-6309

## Scope

No side move is named on this plate. The blank number is the asked paint. The header, the reverse, and the role lines are named stays.

**semantic-key:** PK-6310

## Wire

No system line is added, removed, or reordered. The doors do not gain, lose, or reorder a line. The send stays the invitation splice.

**Truth.** When Debug Mood is on, the user card opens with four unnumbered lines and then the user line. Those lines are mood: with the saved line, shape:, the opener, and the closer. They do not advance the card number. The user line and the assistant line keep their numbers. A block that does not open with the user role is left as it is. Off leaves the inspector as it is. Painting does not move the pin. Rust stays.

**Reason.** The debug paint was its own card, added after the turn and flipped above it by the reverse. The wire head is the start of the user message, not a separate card, and it carries no card number.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not change chrome.rs, grid.rs, or aa-sdd-source-prose.md. Do not move Rust or check the crate. Do not add a G-key, a vault column, a channel, an AMWP type, a color key, a library, a Cargo.toml edit, a glossary row, a public type, a public method, a JUnit class, or a law. Do not retarget branch, action, stage, or the when field. Do not keep Debug Mood as its own card. Do not number those four head lines or advance the number for them. Do not renumber the user line or the assistant line. Do not change a block that does not open with the user role. Do not change the oldest-first build, the one reverse, the header, the separators, the facts line, or debugSpliceId. Do not let the paint write the pin. Do not change the invitation splice, glass, moodHead, the shell frame, debug_mood, the settings seat, or yaml version 1. Do not add, remove, or reorder a system line. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-6311

# The undo caret stays on the edit

The stamp names the build. Undo puts the caret back where the edit began. The undo list stays off the key so typing does not hitch. The cell prompt is the seat. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4363 PK-4364 PK-4365 PK-4366 PK-4367 PK-4368 PK-4369

## Approval

This sentence names the build. Undo in a cell input pane seats the caret where the edit began. Each undo keeps that seat. The key queues a span and returns.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The product tree is compiled with tests skipped.

The product commit is EditMemory.java and AetherisNotebookPanel.java. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-6312

## Goal

A cell prompt undo puts the caret back on the edit. Typing does not wait on a full copy of the prompt.

**semantic-key:** PK-6313

## What stays

note stays the SSH line path. History hops stay off the stack. Ctrl-Z and Shift-Ctrl-Z stay the chords. The limit stays 100. A rebuilt card still syncs. Import through setText still records. The token walk stays off the key. Rust stays. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The next free outside number stays the number already next. No new G-key, vault column, channel, AMWP type, or library is added. Cargo.toml is not edited. No glossary row is added. No new public Java type is added. The public method is noteSpan. No new JUnit class is added. A law is not added. The spine pages stay. No decision block is added on this plate.

**semantic-key:** PK-6314

## The cause

The listener stored the caret Swing had already stepped past the new characters. The next undo wrote that later index back. The same path copied the whole prompt on the key.

**semantic-key:** PK-6315

## The seat

noteSpan queues the offset, the removed text, the inserted text, and the two caret seats. An insert records the offset as the caret before the edit. The key updates the shadow text and returns. Undo and redo drain the queue, then step. applyCellText seats that caret, then seats it once more after Swing's own caret step.

**semantic-key:** PK-6316

## Prose

The public method is noteSpan. No new type is added.

**semantic-key:** PK-6317

## Scope

No side move is named on this plate. The cell prompt undo is the seat. The sibling is this same plate. No system line is added, removed, or reordered. The doors do not gain, lose, or reorder a line.

**Truth.** noteSpan queues the offset, the removed text, the inserted text, and the two caret seats. An insert records the offset as the caret before the edit. The key updates the shadow text and returns. Undo and redo drain the queue, then step. applyCellText seats that caret, then seats it once more after Swing's own caret step. The limit stays 100. Rust stays.

**Reason.** The listener stored the caret Swing had already stepped past the new characters, so the next undo wrote that later index back. The same path copied the whole prompt on the key.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not move Rust or grid.rs. Do not add a G-key, a vault column, a channel, an AMWP type, a library, a Cargo.toml edit, a glossary row, a public Java type, a JUnit class, a law, or a decision block. Do not add a public method other than noteSpan. Do not retarget branch, action, stage, or the when field. Do not move note off the SSH line path. Do not put a history hop on the stack. Do not change Ctrl-Z, Shift-Ctrl-Z, or the limit 100. Do not stop a rebuilt card from syncing. Do not stop import through setText from recording. Do not put the token walk back on the key. Do not copy the whole prompt on the key. Do not store the caret after Swing has stepped past the insert. Do not seat the caret only once if Swing then steps it. Do not add, remove, or reorder a system line. Do not open the window, send a live POST, send a pool turn, or open a live SSH session. Do not call Session::start or chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-6318

# A merge cell takes scroll focus only when a cell in the group is clicked

The pre-plan is locked, and a second revision is not opened. The full cycle is named, and the build is named, in the slice sentence itself. Java is named. Rust stays. The seats for the work are already filed on the locked row, and no extra cut is opened. Where this page is thinner than the locked row, the locked row wins. The formal plan is stamped from this page. Dependencies were filed before the landing was filed on the plan. The page carries a new-item mark.


**semantic-key:** PK-4370 PK-4371

## Slice

The member row stops taking the wheel, and the canvas scroll pane keeps it.

A click anywhere on a cell in the group gives that cell the caret. The wheel on that focused text then stays with the cell.

**semantic-key:** PK-6319

## What stays

A standard cell keeps the old press.

A scroll bar press still returns the wheel to the canvas. The member row still drags sideways. Slices already locked stay locked. The open batch already in the working tree stays open.

**semantic-key:** PK-8197

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This land leaves the working set as it stands.

**Truth.** A merge group leaves the canvas wheel alone until a cell in the group is clicked. That click gives the cell the caret, and the wheel on the focused text stays with the cell. Java is the named tree. Rust stays.

**Reason.** The member row was taking the wheel. The canvas scroll pane is the owner until a grouped cell is clicked, and a standard cell already shows how a click keeps the wheel with the text.

**Avoidances.** Do not open a second revision. Do not move Rust. Do not open an extra cut. Do not change the standard cell press. Do not stop a scroll-bar press from returning the wheel to the canvas. Do not stop the member row from dragging sideways. Do not add, remove, or reorder a system line. Do not disturb the working set.

**semantic-key:** PK-7913

# The assistant reply frame is sapphire

The assistant reply frame on a Java canvas cell turns sapphire. The input frame stays green. Java moves. Rust stays. The working word is NUT, and that word stays the owner's. This page does not mint a glossary row. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-4372

## Dependencies

The reply frame is medium green when the cell has a reply. The input frame is the same green.

SELECTION_SAPPHIRE is already the armed-card color. No new library is added.

The proof compiles the product tree with tests skipped. The window stays closed.

**semantic-key:** PK-6320

## Seat

The assistant scroll takes SELECTION_SAPPHIRE when the cell has a reply.

The input scroll stays MEDIUM_GREEN. An empty reply stays gray. No new public type is added. No new public method is added. No new color key is added.

**Truth.** A reply that exists wears SELECTION_SAPPHIRE. The input stays MEDIUM_GREEN. An empty reply stays gray. Rust stays.

**Reason.** Both frames were the same green. The armed-card sapphire is already in the tree, so the reply can take it without a new color key.

Do not move Rust. Do not add a public type, a public method, a color key, or a library. Do not turn an empty reply sapphire. Do not mint a glossary row for NUT. Do not open the window for the proof.

**semantic-key:** PK-6321

# One seat keeps the mark letters live

One seat keeps the I, E, P, and O marks live in every focus state and every event state. The working word is NUT, and that word stays the owner's. This filing adds no glossary row. Review comes before the bake. Java stays on this filing. Rust is unaffected. The formal plan is the page stamped from this sketch.

**semantic-key:** PK-6322

## Dependencies

stateButton builds a JToggleButton, adds it to one ButtonGroup, and writes cell.state inside the action, then armHold, persistQuiet, rebuildTabs, and refreshInspector. On Zulu 26 the toggle fires that action from setPressed(false) only while the model is armed. focusLost presses off, then disarms. mouseExited disarms and writes nothing.

installImportPopup on mousePressed calls armFocus, then focusCanvas or focusGroupCell. That steals focus during the press. rebuildTabs removes every card. restoreHold moves the viewport and focusHeader lands on the collapse button. The release is not delivered. PlateFaceUi paints press and hover. It does not paint isSelected. Ghost is cell.state equal to EXCLUDED, painted on the card the rebuild builds.

Ctrl-Shift I, E, P, and O write the same state and call the same rebuild. A JButton keeps the same armed release. The seat is the press.

PLATE_SELECT and PLATE_SELECT_INK are already on the theme. The mark face uses them for the seated letter. No new color key is added.

chrome.rs mark_button is an egui selectable. The click writes the mark in the same show pass. The crate stays unwritten. No new library is added. Cargo.toml is not edited. application.yaml stays.

This filing runs no product compile and no crate check. The window stays closed. When a later sentence names the bake, the proof compiles the product tree with tests skipped. The window stays closed on that proof.

**semantic-key:** PK-6323

## Seat

One method is the seat. seatMark writes the cell, paints the four faces from that state, repaints the ghost on the live card, and persists. The mark's mousePressed calls it. Ctrl-Shift I, E, P, and O call it. The buttons are plain, not focusable, and not a ButtonGroup. The popup listener does not steal focus from a mark. The gesture does not rebuild the deck. Rust is unaffected. This filing has no product. The bake waits.

**semantic-key:** PK-6324

## Bake

The bake is named. seatMark is the build. Java moves on the product commit. Rust stays.

**Truth.** seatMark writes the cell, paints the four faces from that state, repaints the ghost on the live card, and persists. mousePressed calls it. Ctrl-Shift I, E, P, and O call it. The buttons are plain, not focusable, and not a ButtonGroup. The popup listener does not steal focus from a mark. The gesture does not rebuild the deck. Ghost is EXCLUDED. PLATE_SELECT and PLATE_SELECT_INK mark the seated letter. Rust is unaffected. This filing has no product.

**Reason.** On Zulu 26 the toggle fires from setPressed(false) only while armed. focusLost presses off and then disarms. mouseExited disarms and writes nothing. The import popup steals focus during the press, the rebuild removes every card, and the release is not delivered. The seat is the press.

Do not treat the filing as a product commit. Do not move Rust or write the crate. Do not add a color key, a library, or a Cargo.toml edit. Do not change application.yaml. Do not leave the marks as a ButtonGroup of toggle buttons. Do not let the popup listener steal focus from a mark. Do not let the gesture rebuild the deck. Do not paint the seated letter from isSelected. Do not drop PLATE_SELECT or PLATE_SELECT_INK. Do not change ghost away from EXCLUDED. Do not add a glossary row for NUT. Do not run a product compile or a crate check on the filing. Do not open the window.

**semantic-key:** PK-6325

# The mood call uses the armed cell on the showing canvas

The Java mood call uses the model of the armed cell on the showing canvas. The user selected Grok on that cell. The call used Ollama. Java moves. Rust stays. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-4373 PK-4374 PK-4375

## Dependencies

AetherisNotebookPanel.moodCard chooses the speaker. focusedTurn is the armed cell on the showing canvas. targetCell is the fall-through Run already uses. sendMood still sets the execute target from moodTarget on the card captured at open. commitOllamaModel writes an Ollama id onto the cell. The Ollama editor keyReleased writes that id on each key. The console path stays on commitConsoleModel. MoodDesk stays the desk. The triple stays. chrome.rs stays. grid.rs stays. aa-sdd-source-prose.md stays. No new library is added. Cargo.toml is not edited. application.yaml stays. The two mood files stay.

The proof compiles the product tree with tests skipped. No new JUnit class is added. The window stays closed.

**semantic-key:** PK-6326

## Seat

The mood call uses the armed readable cell on the showing canvas. A readable cell on a hidden canvas is a stale arm. The fall-through is the cell Run already targets, then the last readable cell on the showing canvas. The Ollama pane writes the cell while the family item is Ollama. A Grok or Pool family item leaves that write idle. A missing family combo keeps the old write. The console path stays. The mood triple stays. The execute target follows that armed cell. Rust already reads the focused card on the showing canvas. No crate delta is taken.

**Truth.** The mood call uses the armed readable cell on the showing canvas. A readable cell on a hidden canvas is a stale arm. The fall-through is the cell Run already targets, then the last readable cell on the showing canvas. The Ollama pane writes while the family item is Ollama. Grok or Pool leaves that write idle. A missing family combo keeps the old write. The console path stays. The mood triple stays. The execute target follows that armed cell. Rust already reads the focused card.

**Reason.** The user selected Grok on the armed cell of the showing canvas, and the call used Ollama. The speaker has to be that showing-canvas arm.

Do not move Rust or take a crate delta. Do not move chrome.rs, grid.rs, or aa-sdd-source-prose.md. Do not add a library, a Cargo.toml edit, or a JUnit class. Do not change application.yaml or the two mood files. Do not replace MoodDesk or the triple. Do not let sendMood stop using moodTarget on the card captured at open. Do not let a hidden readable cell stay the speaker. Do not let Grok or Pool keep the Ollama write. Do not let a missing family combo drop the old write. Do not move the console path off commitConsoleModel. Do not open the window for the proof.

**semantic-key:** PK-6327

# A cell key posts the token walk

The Java cell input stops waiting on the token walk. The count stays the count. The key posts the edit. The walk catches up off the key. Java moves. Rust stays. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-4376 PK-4377 PK-4378 PK-4379 PK-4380 PK-4381 PK-4382 PK-4383 PK-4384 PK-4385 PK-4386 PK-4387 PK-4388 PK-4389 PK-4390 PK-4391

## Dependencies

AetherisNotebookPanel.noteLiveDraft writes the draft on the key. refreshInspector paints the inspector and measures the armed transcript. transcriptThroughArm is the armed walk already seated. HeuristicEstimator counts the rendered string. paintTokenEstimate stores the integer. tokenRingUsed is the integer the heatbar already reads. notebookDip.watch runs when that integer is painted. A click still calls refreshInspector. The SSH heat path stays on sshLiveCount. chrome.rs stays. grid.rs stays. SshClientApp stays. aa-sdd-source-prose.md stays. No new library is added. Cargo.toml is not edited. application.yaml stays.

The proof compiles the product tree with tests skipped. No new JUnit class is added. The window stays closed.

**semantic-key:** PK-6328

## Seat

A cell key writes the draft and posts one edit. The transcript walk runs off the key on a copied seat. The newest edit is the one that runs. A result paints when its stamp is still current. A click still measures on that call. The heuristic, the marks, the heatbar, and the token window stay. Rust already counts the live prompt off the key. No crate delta is taken.

**Truth.** A cell key writes the draft and posts one edit. The transcript walk runs off the key on a copied seat. The newest edit is the one that runs. A result paints when its stamp is still current. A click still measures on that call. The heuristic, the marks, the heatbar, and the token window stay. Rust already counts the live prompt off the key.

**Reason.** The key was waiting on the token walk. The count has to stay the count, and the walk has to catch up off the key.

Do not move Rust or take a crate delta. Do not move chrome.rs, grid.rs, SshClientApp, or aa-sdd-source-prose.md. Do not add a library, a Cargo.toml edit, or a JUnit class. Do not change application.yaml. Do not let the key wait on the walk. Do not change the count. Do not drop the click measurement. Do not change HeuristicEstimator, the marks, the heatbar, or the token window. Do not move the SSH heat path off sshLiveCount. Do not paint a result whose stamp is no longer current. Do not open the window for the proof.

**semantic-key:** PK-6329

# Debug Mood paints the triplet

File, Settings carries Debug Mood. On, the inspector shows the mood triplet above the turn the pointer seats. Java moves. Rust stays. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-4392 PK-4393 PK-4394 PK-4395 PK-4396 PK-4397 PK-4398 PK-4399

## Dependencies

The Java File menu does not yet open Settings. The Rust File menu already does, and that window saves aetheris-settings.yaml. This plate adds the Java window and one checkbox. The send stays the splice already seated. spliceTurn stays the wire head. The inspector paint does not write the pin. chrome.rs stays. grid.rs stays. No new library is added. Cargo.toml is not edited. No new JUnit class is added. No new public type is added.

The proof compiles the product tree with tests skipped. That compile passed on the working tree. The Rust client crate was not checked. A crate test was not run. The window stays closed.

**semantic-key:** PK-6330

## Seat

File, Settings opens the window. The checkbox is Debug Mood. On, the inspector paints the opener, the line, and the closer as one card above the turn the pointer seats. Off leaves the inspector as it is.

**semantic-key:** PK-6331

## Out of scope

The Rust crate stays out. The send stays out. The pin write stays out. Glass appendMoodHeader stays out. The shell frame stays out. The mood header already on the inspector stays out. SSH as an inspector stays out. A new public type stays out. A new JUnit class stays out. chrome.rs stays out. grid.rs stays out.

**Truth.** File, Settings opens the window. The checkbox is Debug Mood. On, the inspector paints the opener, the line, and the closer as one card above the turn the pointer seats. Off leaves the inspector as it is. The paint does not write the pin. Java moves. Rust stays.

**Reason.** The Java File menu does not yet open Settings. The Rust window already saves aetheris-settings.yaml. This plate adds the Java window and one checkbox so the triplet is visible where the pointer seats it.

Do not move the Rust crate, chrome.rs, or grid.rs. Do not add a library, a Cargo.toml edit, a public type, or a JUnit class. Do not change the send or spliceTurn as the wire head. Do not let the paint write the pin. Do not change appendMoodHeader, the shell frame, or the mood header already on the inspector. Do not give SSH an inspector card. Do not open the window for the proof.

**semantic-key:** PK-6332

# Debug Mood shows the head on its prompt

Debug Mood shows the invitation where the wire places it. The lines sit immediately before the prompt the pointer seats. They carry no card number. The rest of the inspector stays. Java moves. Rust stays. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-4400 PK-4401 PK-4402

## Dependencies

The inspector already builds oldest first and reverses once. Debug Mood already paints a head, and that head is its own card. This plate moves the head onto the user line the pointer seats and leaves the number off those lines. The send stays the invitation splice. The header stays. The pin write stays off the paint. chrome.rs stays. grid.rs stays. No new library is added. Cargo.toml is not edited. No new JUnit class is added. No new public type is added. No new public method is added.

The proof compiles the product tree with tests skipped. That compile passed on the working tree. The Rust client crate was not checked. A crate test was not run. The window stays closed.

**semantic-key:** PK-6333

## Seat

Debug Mood on paints mood:, the line, shape:, the opener, and the closer immediately before the user line the pointer seats. Those lines carry no card number. Off leaves the inspector as it is.

**semantic-key:** PK-6334

## Out of scope

The Rust crate stays out. The send stays out. The pin write stays out. The inspector header stays out. The one reverse stays out. The role-line numbers stay out. Glass stays out. moodHead stays out. The shell frame stays out. chrome.rs stays out. grid.rs stays out.

**Truth.** Debug Mood on paints mood:, the line, shape:, the opener, and the closer immediately before the user line the pointer seats. Those lines carry no card number. Off leaves the inspector as it is. Java moves. Rust stays.

**Reason.** The debug head was its own card. The wire places that head on the prompt, and the lines carry no card number.

Do not add a library, a Cargo.toml edit, a public type, a public method, or a JUnit class. Do not keep the head as its own card. Do not number those lines. Do not change the send, the header, the one reverse, the role-line numbers, glass, moodHead, or the shell frame. Do not let the paint write the pin. Do not open the window for the proof.

**semantic-key:** PK-6335

# The undo caret stays on the edit

Undo in a cell input pane leaves the caret where the edit began. The index has been landing one or two characters ahead. The undo list stays off the key path so typing stays smooth. This is Java. Rust stays. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-4403 PK-4404 PK-4405 PK-4406 PK-4407 PK-4408 PK-4409

## Dependencies

EditMemory is the RAM seat. note stores a full copy of the prompt on the key. The cell listener reads getText and getCaretPosition. The caret Swing reports has already stepped past the characters just inserted. applyCellText writes that seat back into the live prompt. ChatShell keeps note for the SSH line. The cell path is the seat.

The public method is noteSpan. It queues the span. The key updates the shadow text and returns. Undo and redo drain the queue, then step. The caret stored for an insert is the offset where the text arrived. No new public type is added. No new JUnit class is added. grid.rs stays.

The proof compiles the product tree with tests skipped. The window stays closed.

**semantic-key:** PK-6336

## Seat

Each undo in a cell input pane seats the caret where that edit began. The key does not copy the whole prompt onto the undo stack. The span waits until undo or redo.

**semantic-key:** PK-6337

## Out of scope

The SSH line stays out. Rust undo stays out. The heatbar walk stays out. A smoke row stays out. A push stays out. A spine rewrite stays out.

**Truth.** Each undo in a cell input pane seats the caret where that edit began. The key does not copy the whole prompt onto the undo stack. The span waits until undo or redo. noteSpan is the public method. Java moves. Rust stays.

**Reason.** The caret Swing reports has already stepped past the characters just inserted, and the index has been landing one or two characters ahead. The key was also copying the whole prompt onto the undo stack.

Do not move the SSH line, Rust undo, or the heatbar walk. Do not move grid.rs. Do not add a public type or a JUnit class. Do not add a public method other than noteSpan. Do not copy the whole prompt on the key. Do not store the caret after the insert has stepped it. Do not rewrite the spine. Do not file a smoke row. Do not open the window for the proof.

**semantic-key:** PK-6338

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

A finesse ask is the row, the cycle, the filing, and the product change. The sentence on how an ask lands is the operative reading of a finesse ask. The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the sent-level heads or the send-effort heads.

The build is the one package below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. One commit records the product type in the tree. Neither commit is pushed.

The Grok serial is not edited. Its earlier reading, that a new turn does not copy, stays the earlier reading.

**semantic-key:** PK-7823

## Goal

A new turn from the New turn button, or from a response on the last cell, starts with the prior turn's model config.

**semantic-key:** PK-6339

## Out of this build

Import, Pull, Clear turns, and the merge sink stay as they are. The prompt, the reply, the group, and the ephemeral flag are not copied. The Grok serial, the stage map, and the constitution stay untouched.

**semantic-key:** PK-6340

## Package

The only edit is `AetherisNotebookPanel.newTurn`. Before the worker runs, snapshot the last cell: the execution target, the Grok entry copy, and the think-holder copy. After `addTurn`, store that target and call `saveCell`. On the event-dispatch thread, `copyOnto` applies the Grok entry with `alsoDefault` false, and the think holder is put on the new id. Then `rebuildTabs` runs. A canvas with no cell skips the copy. The New turn tip gains one sentence: the new turn keeps the prior turn's model.

**semantic-key:** PK-6341

## Proof

A quiet compile that does not run tests exits 0. The window is not opened. The live call is not made.

**Truth.** `newTurn` carries the last cell's execution target, Grok entry, and think holder onto the new cell, and it does not carry the prompt.

**Reason.** A turn started from the button or from a response on the last cell is a continuation, so it must open on the model config the previous turn already held.

**Avoidances.** No copy of prompt, reply, group, or ephemeral flag, no change to import, Pull, Clear turns, or the merge sink, and no edit of the Grok serial.

**semantic-key:** PK-6342

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the sent-level heads, the send-effort heads, or the model-copy heads.

The build is the one package below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. One commit records the product type in the tree. Neither commit is pushed.

When a turn completes and a new turn card is appended, the selected focus moves to that card.

**semantic-key:** PK-7824

## Goal

When a response completes on the last cell and a new card is appended, that card is the selected focus.

**semantic-key:** PK-6343

## Out of this build

The New turn button's selection stays as it is. Import, Pull, Clear turns, and the merge sink stay as they are. The model copy inside a new turn stays. The Grok serial, the stage map, and the constitution stay untouched.

**semantic-key:** PK-6344

## Package

The only edit is `AetherisNotebookPanel.newTurn(boolean)`. The completion path passes true. The button passes false. When the argument is true, the event-dispatch runnable sets `focusedCellId` and `pendingFocusId` to the new cell, clears the assistant flag, and sets the land-on-input flag, all before `rebuildTabs`. The status line then names that card's engine. When the argument is false, the existing pending-focus assignment stays, and `focusedCellId` stays where the click left it. The Join source stays the last clicked card. The model copy inside `newTurn` stays.

**semantic-key:** PK-6345

## Proof

A quiet compile that does not run tests exits 0. The window is not opened. The live call is not made.

**Truth.** A completed last cell appends a card that becomes the selected focus, and the New turn button does not take that same selection path.

**Reason.** The card born from a finished last cell is the card the operator is now on, so the focus has to move with the append rather than stay on the cell that just finished.

**Avoidances.** No change to the button's selection, no change to import, Pull, Clear turns, or the merge sink, and no change to the model copy.

**semantic-key:** PK-8199

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the heads for the rows that landed before this one.

The build is the one package below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. One commit records the product type in the tree. Neither commit is pushed.

When a new turn is created, the canvas ends at the bottom.

**semantic-key:** PK-7825

## Goal

When a new turn is created, the canvas viewport sits on the last pixel of the column.

**semantic-key:** PK-6346

## Out of this build

The sapphire edge on the New turn button stays as it is. `focusedCellId` still moves only on `newTurn(true)`. Import, Pull, Clear turns, a completion on an earlier cell, and the merge sink stay out. The model copy stays out. A component listener stays out. The second pin is one event-dispatch pass. The Grok serial, the stage map, and the constitution stay untouched.

**semantic-key:** PK-6347

## Package

The edit is `AetherisNotebookPanel.newTurn(boolean)` and the land pass in `scrollPendingFocus`.

Both calls, before `rebuildTabs`, set `pendingFocusId` to the new cell, clear the assistant flag, and set `pendingLandOnInput`. `focusedCellId` is written only when `armAppended` is true. The status line stays on that path. `refreshInspector` stays where it is. The model copy inside `newTurn` stays. The Join source stays the last clicked card.

The land pass focuses the new prompt first, then pins the canvas to the end, then queues one event-dispatch pass that validates, finds the card, and pins the end again. That second pass does not focus. The non-land path stays `scrollRectToVisible`.

Two private helpers live on the panel. `canvasScroll` walks ancestors and skips a pane whose vertical bar policy is `VERTICAL_SCROLLBAR_NEVER`. `scrollCanvasToEnd` sets the viewport Y to view height minus extent. A laid-out height under 1 uses the preferred height. A negative Y stays 0. `scrollOf` stays for the hold path.

**semantic-key:** PK-6348

## Proof

A quiet compile that does not run tests exits 0. The window is not opened. The live call is not made.

**Truth.** Both entries into `newTurn` aim the viewport at the last pixel of the column, and the second pin is one later event-dispatch pass that does not move focus.

**Reason.** A new card that is built and then left above the fold is not yet the end of the canvas, so the land has to pin after layout as well as before it.

**Avoidances.** No sapphire move on the button path, no component listener, no change to the model copy, and no scroll of a sideways-only bar that never scrolls vertically.

**semantic-key:** PK-8200

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. One commit records the product type in the tree. Neither commit is pushed.

Duplicate group copies the selected group with clear cells.

**semantic-key:** PK-7826

## Goal

An armed group can be copied, empty, to the next place in the column.

**semantic-key:** PK-6349

## Out of this build

Prompt text and reply text are not copied. The armed card is not moved onto the replica. A new package, public type, vault column, or G-key stays out. Group, Join, Ungroup, Delete, and the merge sink stay as they are. The window is not opened. A live call is not sent.

**semantic-key:** PK-6350

## The edit

The edit is `AetherisNotebookPanel`.

The card menu gains Duplicate group. A right-click on the reply still opens that item. Import stays refused there. The tip names the empty replica and Shift-Ctrl-G. The new menu item carries a tooltip.

Shift-Ctrl-G is `VK_G` with Ctrl and Shift, on the same input map as Ctrl-Enter. The key code does not follow caps lock.

`duplicateGroup` reads the armed card. No card, or a card with an empty group id, calls `showErrorSlide` with the words `no group selected` and yellow set true.

The replica is one new group id. Members are copied in canvas order, then the merge when the source has one. Each new cell copies the execution target, the I E P O state, the burner mark, and the column position. Prompt, reply, run state, and parent pointer are empty. The card starts open. The block is inserted after the last cell of the source group. Turn indexes are rewritten from 1 in the new order. A Grok entry and a thinking holder are copied onto the matching new cell. The armed card stays.

`applySlideSkin` sets the hard latch from the yellow flag. A yellow notice clears the latch. A hard notice sets it.

**semantic-key:** PK-6351

## Proof

A quiet compile that does not run tests passes. The window is not opened. The live call is not made.

**Truth.** Duplicate group, from the card menu or from Shift-Ctrl-G, inserts one empty replica after the armed group and leaves the armed card where it is.

**Reason.** The operator needs a clear group of the same shape in the next place in the column, not a second copy of the prompts and replies already written.

**Avoidances.** No copied prompt or reply, no move of the armed card, no new public type, and no change to Group, Join, Ungroup, Delete, or the merge sink.

**semantic-key:** PK-6352

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. One commit records the product type in the tree. Neither commit is pushed.

A copy icon on a readable cell copies that turn's whole reply to the clipboard. The View window has the same icon.

**semantic-key:** PK-7827

## Goal

One click puts a turn's whole reply on the operating-system clipboard. The control sits on every readable cell bar that has an output, and on the View window. The mark is a contemporary copy icon.

**semantic-key:** PK-6353

## Out of this build

The group bar, an unreadable load-error card, footer BB1 and BB2, the inspector, the mood dialog, and the model dialog stay out. The prompt, the thought strip, the find field and its highlights, and a selected range as the copy source stay out. `SshClientApp.normalizeNewlines` is not changed. The error slide is not used for a busy clipboard. A new package, public type, vault column, G-key, channel, or wire type stays out. The window is not opened. A live call is not sent. A pool turn is not sent. The pool-session hold stays as landed.

**semantic-key:** PK-6354

## The edit

The edit is `AetherisNotebookPanel` only.

The button sits on `headerTop` of every readable `CellCard`, immediately after View. A collapsed readable card keeps the button on that same bar. The output window is folded. The stored reply is the output. Included, excluded, input-only, output-only, and a readable ghost card all keep the output pane, so they all keep the button. A merge sink copies the merge reply, and the JOIN lines stay in that text.

The button is enabled when the copy source has non-whitespace text. It is disabled when that source is empty or whitespace, so a click leaves the clipboard as it is. Construction uses the same gate as View: `assistantText` trimmed. The first pool chunk that leaves non-whitespace text in the mounted assistant area enables the button. `CellCard` holds the `JButton` so `appendPoolChunk` can enable it.

The copy source on an open card is `assistantArea.getText()`. That area includes in-flight pool kind `c` chunks that `appendPoolChunk` paints before `TYPE_DATA` stores the reply. The copy source on a collapsed card is `cell.assistantText`. The prompt stays on the card. Thought-strip text stays on the footer. A selection stays a selection. The copy is the whole document.

A folded card during a pool stream copies the last stored reply. `appendPoolChunk` appends those chunks onto the truncated brief and leaves `assistantText` until `TYPE_DATA`. That catch is accepted. The brief is the eye's line. The copy does not read it.

The View window, `showViewModal`, gains the same icon on the south bar, before Close. The click copies `area.getText()`, the snapshot the dialog opened from `cell.assistantText`. Find highlights and the find field stay. The dialog returns before it is built when the reply is blank, so the View copy button is enabled while the window is up. The dialog title stays as it is.

The control is an icon-only `JButton`. The glyph is two overlapping rounded rectangles, painted in the button foreground and dimmed when the button is disabled. A private method returns an anonymous `Icon`. `fitAetherisButton` keeps the height with the sibling buttons, and `aetheris.short` still survives a font refit. The glyph stays about ten pixels so the seventy-percent height crush leaves it whole. The accessible name is Copy.

The card tip says Copy copies this turn's whole reply to the clipboard, the prompt stays, and the button stays off until the turn has a reply. The View tip says Copy copies this window's whole reply to the clipboard and the find field stays. Tips go through `buttonTip`.

The write is `Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(body), null)` on the Swing thread. A null, empty, or whitespace body returns and leaves the clipboard as it is. `IllegalStateException` sets the status line through `host.aetherisStatus(statusLine("clipboard busy"))`. The error slide stays closed. The reply text stays off the status line and off the logs. Vault text and `JTextArea` use `\n`. The clipboard write turns a `\n` that is not already part of `\r\n` into `\r\n`, so a paste into Notepad keeps the breaks. `normalizeNewlines` stays the paste-in path.

`groupedMemberWidth` remeasures `headerTop`. The extra button widens the member card and the ten-pixel east gap stays. The earlier member-width rule stays as filed.

**semantic-key:** PK-6355

## Accepted catch

A collapsed card that is still receiving pool chunks copies the previous stored reply while the brief shows a truncated prefix plus the new chunks. The next `TYPE_DATA` stores the reply and a rebuild enables Copy on that stored text. An open card copies the painting area the whole time. A whitespace-only reply leaves the button off, the same gate as View.

**semantic-key:** PK-6356

## Gotchas the code keeps

A busy clipboard is a status line. The slide stays for the failures it already shows.

The copy does not trim a reply that has real text. Leading and trailing blank lines ride along. The enable gate is the trim.

JOIN banners in a merge reply are part of the stored text and part of the View snapshot, so they ride along.

Two buttons do not share one clipboard owner. Each click replaces the clipboard contents. A second click of the same reply writes that reply again.

**semantic-key:** PK-6357

## Proof

A quiet compile that does not run tests passes. The window is not opened. The live call is not made. A pool turn is not sent.

**Truth.** Copy, on a readable cell and in the View window, writes that turn's whole reply to the system clipboard and leaves the prompt, the thought strip, and the find field alone.

**Reason.** The operator needs the whole reply, including a merge's JOIN lines, in one click, while the chunks are still painting on an open card and from the stored text on a folded card.

**Avoidances.** No copy of the prompt or the thought strip, no selected-range source, no error slide for a busy clipboard, no change to `normalizeNewlines`, and no shared clipboard owner between the two buttons.

**semantic-key:** PK-7933

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed.

The mood layer rides the pin. A mood miss leaves the turn.

**semantic-key:** PK-7828

## Goal

The mood layer runs after pin OK. A mood miss paints the error slide, takes the layer offline for the session, and leaves the chat turn runnable.

**semantic-key:** PK-6358

## What stays

The Mood dialog stays. Approve still stores one system line. Cancel still keeps the filter. Empty `mood-shape` stays the Ollama model in use. Air-gap stays. `num-ctx` stays. Offline lasts until the next app start.

**semantic-key:** PK-6359

## Edits

The edits live on `AetherisNotebookPanel`.

Every mood id stays known after the dialog closes. A frame for that id stays on the mood path.

A miss closes the dialog, clears the mood line, paints the error slide, and sets the layer offline. Later pin OK writes the filter and does not open Mood.

A second Send while one is in flight leaves the typed line in the field.

When the mood line makes the body too large, the line is dropped, the slide shows the miss, and the turn is sent without it.

**semantic-key:** PK-6360

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** Pin OK opens Mood, a miss takes the layer offline until the next app start, and the chat turn still runs.

**Reason.** A failed shaper must not block the turn the operator already pinned, and it must not keep opening Mood on later pins in the same session.

**Avoidances.** No new public method, no rollback of the pin, no blocking dialog, and no second Send that replaces the line already in flight.

**semantic-key:** PK-7947

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed.

The thinking pane stays until Close. It slides up from the top of the footer. The footer buttons stay reachable.

**semantic-key:** PK-7829

## Goal

The thinking pane stays in view until Close. It rises from the top of the footer. The footer buttons stay reachable.

**semantic-key:** PK-6361

## What stays

The earlier strip stays: three lines, yellow ink, `v` folds the text, the text drops with the session, and the text is not a vault column. The error slide stays the notice at the top. Air-gap stays. `num-ctx` stays.

**semantic-key:** PK-6362

## Stack

The edits are on `AetherisNotebookPanel`. The footer is the south child of the south stack, at its own height. The thinking pane is the north child. Its bottom edge stays on the top edge of the footer. The animated height grows upward from that edge. The footer buttons stay at full size.

**semantic-key:** PK-6363

## Open

A thought opens the pane with that slide. A pane that is already open keeps its height. A folded pane stays folded. Close stays on the folded pane.

**semantic-key:** PK-6364

## Pause

A finish leaves the pane and the text. A second finish leaves the pane and the text.

**semantic-key:** PK-6365

## Close

Close slides the height back to the top of the footer, then drops the text and hides the pane. The next thought opens the slide again.

**semantic-key:** PK-6366

## Text

Another cell replaces the text. The same cell appends. The ink stays yellow. The area stays three lines and read-only.

**semantic-key:** PK-6367

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** The thinking pane rises from the top of the footer and stays until Close, while the footer buttons keep their own height.

**Reason.** A pane that covers the footer takes Run, Stop, and New turn out of reach, and a pause is not a dismiss.

**Avoidances.** No vault column for the text, no retarget of the error slide, and no hide on a finish.

**semantic-key:** PK-7948

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

The thinking checkbox is carried onto the next turn. The thinking log stays, tagged by model.

**semantic-key:** PK-7830

## Goal

The thinking checkbox survives a restart and is copied with the model onto the next turn. The thinking pane keeps a tagged log, a dragged height, and a Clear that wipes the saved text.

**semantic-key:** PK-6368

## What stays

The earlier strip stays. The model copy onto a new turn stays. The pane above the footer stays. Close still hides the pane. The footer buttons stay reachable. The body stays yellow. `v` still folds the text. The text stays off the vault. The error slide stays the notice at the top. Air-gap stays. `num-ctx` stays.

**semantic-key:** PK-6369

## Holder

The edits are on `AetherisNotebookPanel`. Load `~/.casserole/aetheris-think.yaml` when the panel opens. Version 1. A version other than 1 is left unread and is not overwritten. Write the holder on a Thinking change, on an effort change, on `newTurn`, on a duplicated group, and after a delete drops a cell. The new cell receives the prior holder, including `poolOn`.

**semantic-key:** PK-6370

## Height

The north edge drags. The dragged height is `height` in that yaml. The minimum is the three-line pane. The maximum leaves the canvas and the footer in reach. The next session opens at the saved height.

**semantic-key:** PK-6371

## Log

`~/.casserole/aetheris-thinking.txt` is the text. A later chunk appends. A new block writes a separator and the model tag in cyan, then the yellow text. A pause closes the block. Close hides the pane and keeps the text. When the saved log is non-empty, the pane opens with it. Clear is a small `C` at the east of the north edge. Clear empties the pane and the file. The pane stays up.

**semantic-key:** PK-6372

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** The thinking holder, including `poolOn`, is copied onto the next turn and reloaded from version 1 of the home yaml, and the log file keeps tagged blocks until Clear.

**Reason.** A new turn that copies the model without the thinking checkbox drops the setting the operator just chose, and a log that dies with the pane cannot be reread after Close.

**Avoidances.** No vault column, no overwrite of a yaml version other than 1, no Rust slice in this build, and no Clear that hides the pane.

**semantic-key:** PK-7949

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The glass paragraphs on this row wait. They do not retarget the earlier heads.

The build is the edits below. Dish 1 is the mood string. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten. The private JediTerm stays unwritten.

The private JediTerm waits.

**semantic-key:** PK-7831

## Goal

Mood is optional. A saved string sits once at the head of the working set while the switch is on. The Mood button drafts that string in the Swing dialog against the selected card.

**semantic-key:** PK-6373

## What stays

The earlier mood reading stays. Pin OK still writes the filter and leaves the Mood dialog closed. The host line stays the host line. The error slide stays the notice. Air-gap stays. `num-ctx` stays. `notebook.mood-shape` stays in the yaml and the button does not read it. Channel A stays the lane. A mood id stays off the cell path. The private JediTerm waits. Clear turns leaves the string and the switch. Export does not carry the string.

**semantic-key:** PK-6374

## Footer

The edits are on `AetherisNotebookPanel`. BB2, after To terminal, gains the same narrow divider BB1 uses, then a two-position switch, then a button labeled `Mood`. Left is off. Right is on. It is not a range slider. BB1 does not carry them. BB2 still starts hidden. The `v` tip names the mood switch and the Mood button with the other tools. Each control carries the tip named on the row.

**semantic-key:** PK-6375

## Switch

A click with no saved string leaves the switch off. Off keeps the saved string and the walker skips the mood line. On inserts that saved string once at the head. The switch does not call the model.

**semantic-key:** PK-6376

## Door

The Mood button opens the dialog for the card with the sapphire edge. A missing readable card leaves the dialog closed and the status line shows `no cell`. A cloud target that card is not allowed to call leaves the dialog closed and the slide says `keyword only.` A missing wire connector does the same. The saved string and the switch stay.

**semantic-key:** PK-6377

## Dialog

Dish 1 keeps the Swing transcript. The title is `Mood`. The transcript is read-only. Send, Approve, and Cancel sit with the line field. An editable mood-string field shows the draft. Send ignores an empty line. One send is in flight. A second Send leaves the typed line in the field. The shaping system line is `Define one mood string. The string is style, mood, and symbolic modifiers.` The call is ephemeral. Think stays off. The working set is the shaping chat. The speaker is that card's target and model id. An empty target is `ollama`. A Grok card uses that card's Grok model id. A pool card uses `pool`. The last assistant reply fills the mood-string field. Approve commits that field, turns the switch on, and closes. An empty field closes and writes nothing. Cancel closes. The saved string and the switch stay as they were. The chat list is discarded when the dialog closes.

**semantic-key:** PK-6378

## Miss

A miss closes the dialog and paints the error slide. The words start with `keyword only.` A hard miss keeps the red slide. Any other miss uses the yellow slide. The saved string and the switch stay. The layer does not latch offline. A later Mood click can open the dialog. A frame for a mood id never marks a cell, never clears a chain, and never fails a turn. A shaping body that does not fit paints `keyword only. too-large`, leaves the dialog open, and leaves the list in place.

**semantic-key:** PK-6379

## Walker

The host line stays first. While the switch is on and a string is saved, the next system message is that string. While the switch is off, the walker skips it. The role is `system`. When the encoded body does not fit and the mood line is present, the slide shows `keyword only. too-large`, the body is assembled again without that line, and the turn is sent. The saved string and the switch stay on.

**semantic-key:** PK-6380

## Inspector

`mood: (none)` when no string is saved. `mood: off` and a preview when the switch is off. `mood:` and a preview when the switch is on.

**semantic-key:** PK-6381

## Memory

The saved string is session memory. A restart starts with no string and the switch off. It is not a vault column and it is not written into a cell.

**semantic-key:** PK-6382

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** Dish 1 stores one optional mood string in session memory and places it once, as a system line, at the head of the working set only while the switch is on.

**Reason.** The operator needs a style string enforced for the session without a model call on every toggle, and without taking the layer offline when a draft misses.

**Avoidances.** No vault column, no read of `notebook.mood-shape` by the button, no export of the string, no Rust slice, no private JediTerm, and no offline latch.

**semantic-key:** PK-7950

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

A missing mood line retries. Three tries paint the yellow slide. The dialog footer carries the thinking spinner.

**semantic-key:** PK-7832

## Goal

When a mood reply has no mood line, the dialog asks for that line and sends again. Three replies with no line paint the yellow slide and leave the dialog open. The dialog footer shows the thinking spinner and the elapsed clock while a reply is in flight.

**semantic-key:** PK-6383

## What stays

The pluck, the glass, the field, and Approve stay. A transport miss still closes the dialog. Think stays off. The shaping call still copies the card's burner bit. Air-gap stays. Channel A stays the lane. The canvas spinner still follows the canvas run. The saved string and the switch stay through a format miss.

**semantic-key:** PK-6384

## Tries

The edits are on `AetherisNotebookPanel`. A reply that `moodPluck` can read fills the field and sets the try count to zero. A reply it cannot read counts as one try. The assistant text stays on the glass. The field stays as it was. While the count is under three, the dialog appends one user line and sends the list again. That line asks for one final line that starts with `mood:` and gives the example `mood: spare, dry, lantern-light`. The glass marks that line you. The third reply with no mood line stops. The yellow slide says `keyword only. mood line`. The dialog stays open. The list stays. A line typed after that starts the count at zero.

**semantic-key:** PK-6385

## Spinner

The dialog footer gains the same `ThinkSpinner` the notebook uses. Emerald glyph. Electric-blue word `thinking`. The clock is `ThinkingClock`, counted from that send, not from the canvas run. A retry starts the clock again. The spinner stops when the reply lands, when the yellow slide paints, when the dialog closes, and when a miss closes the dialog. The tip names the wait. Approve and Cancel stay available. A second Enter while a reply is in flight still leaves the typed line.

**semantic-key:** PK-6386

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** Three replies with no mood line stop on the yellow slide `keyword only. mood line`, and any reply `moodPluck` can read clears the count.

**Reason.** The pool model can return a reply that is not the expected mood line, so the dialog has to ask again, and it has to stop at three without closing.

**Avoidances.** No Rust slice, no clock tied to the canvas run, and no close of the dialog on a format miss.

**semantic-key:** PK-7951

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

Two finesses sit in one row. The thinking text stays put. The mood knob is green when on and red when off.

**semantic-key:** PK-7833

## Goal

The thinking word and the clock stay put while the glyph turns. The mood switch round button is green when on and red when off.

**semantic-key:** PK-6387

## What stays

The retry and the dialog spinner stay. The clock still counts from that send on the dialog and from the canvas run on the notebook. Left stays off. Right stays on. The switch still does not call the model.

**semantic-key:** PK-6388

## Spinner

The edits are on `AetherisNotebookPanel`. `ThinkSpinner` gives the glyph a fixed box measured from the widest frame. The word thinking and the clock are a second label to the right of that box. The row is left aligned. A frame change does not move the word. The notebook spinner and the dialog spinner are that same class.

**semantic-key:** PK-6389

## Knob

The round button is green `#00FF66` while the switch is on and red `#E53935` while it is off. The track stays. The tip names the colors. That tip is the tooltip for the control.

**semantic-key:** PK-6390

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** The glyph turns inside a fixed box, and the mood knob is `#00FF66` when on and `#E53935` when off.

**Reason.** A glyph that changes width shoves the word and the clock, and the on and off positions need colors the tip can name.

**Avoidances.** No Rust slice, no range slider, and no model call from the switch.

**semantic-key:** PK-7952

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

A shape line follows the mood. The line tells the model to activate that mood and use it as the shape of its responses.

**semantic-key:** PK-7834

## Goal

A canvas send with the mood switch on carries the saved mood string, then a system line that tells the model to activate that mood and use it as the shape of its responses. The line is not a cell.

**semantic-key:** PK-6391

## What stays

The still text and the knob colors stay. The mood string stays the system message after the host line. The switch still does not call the model. The mood glass still uses the shaping line. A miss still keeps the saved string and the switch. Left stays off. Right stays on.

**semantic-key:** PK-6392

## Shape line

The edits are on `AetherisNotebookPanel`. The words are `Activate the mood above. Use that mood as the shape of your responses.` The role is `system`. While the switch is on and a mood string is saved, the walker adds that string, then this line, then the transcript. The line is not written into a cell. The mood glass does not carry this line.

**semantic-key:** PK-6393

## Drop

When the encoded body does not fit, the body is assembled again without the mood string and without this line. The slide stays `keyword only. too-large`. The saved string and the switch stay on.

**semantic-key:** PK-6394

## Inspector

While the switch is on, the inspector shows the mood preview and then `shape:` with this line. The token estimate counts this line. While the switch is off, or no string is saved, the inspector does not show the shape line and the estimate does not count it.

**semantic-key:** PK-6395

## Tip

While a string is saved, the switch tip names the shape line. That tip is the tooltip for the control.

**semantic-key:** PK-6396

## Rust row

The row is written in the ledger, after the product commit. The match is this: the shape line follows the mood string on a canvas send. The words tell the model to activate the mood above and use it as the shape of its responses. The line is not a cell. The mood glass does not carry it. A body that does not fit drops the mood string and this line together. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** While the switch is on, the saved mood string is followed by one system line, `Activate the mood above. Use that mood as the shape of your responses.`, and that line is not stored on a cell.

**Reason.** A mood string alone was used only after the model was coaxed, so the send has to say that the mood above is the shape of the responses.

**Avoidances.** No cell write of the shape line, no shape line on the mood glass, no crate in this shape, and no Rust slice until the catch-up is named.

**semantic-key:** PK-6397

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

A closed thinking pane stays closed when the app opens again.

**semantic-key:** PK-7835

## Goal

Close writes the thinking pane shut. The next open of the app leaves that pane shut. The log stays loaded.

**semantic-key:** PK-6398

## What stays

The holder file and the log file stay. The log still survives Close. Clear still wipes both. A later thought still opens the pane. The `v` fold stays for this session and is not written. An open pane with a saved log still shows on the next open. Version 1 stays. A version other than 1 stays unread and is not overwritten.

**semantic-key:** PK-6399

## Mark

The edits are on `AetherisNotebookPanel`. `~/.casserole/aetheris-think.yaml` gains the root key `closed`. Close sets it true and writes the file. A thought that opens the pane sets it false and writes the file. A missing key is false.

**semantic-key:** PK-6400

## Open

The log still loads. When `closed` is true, the pane stays hidden. When `closed` is false and the log has text, the pane shows, as it does now.

**semantic-key:** PK-6401

## Tip

The Close tip says a closed pane stays closed when the app opens again. That tip is the tooltip for the control.

**semantic-key:** PK-6402

## Rust row

The row is written in the ledger, after the product commit. The match is this: Close writes `closed` true on the think yaml. The next open loads the log and leaves the pane shut. A later thought opens the pane and clears the mark. The fold is not written. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** Close writes `closed` true beside the dragged height, and the next open loads the log while leaving the pane shut.

**Reason.** Closing the pane and then the app was bringing the pane back open, because the closed choice was not stored.

**Avoidances.** No write of the `v` fold, no wipe of the log on Close, no crate, and no Rust slice until the catch-up is named.

**semantic-key:** PK-6403

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

The Approve label is truncated. The button grows so the word is fully shown.

**semantic-key:** PK-7836

## Goal

The mood dialog shows the whole word Approve. The short height stays.

**semantic-key:** PK-6404

## What stays

The label stays `Approve`. Cancel stays on the short fit. The other short buttons stay. The tip stays. Approve still saves the field and closes.

**semantic-key:** PK-6405

## Width

The edit is on `AetherisNotebookPanel`, in `openMood`. After the short fit, Approve's preferred width and minimum width grow by three ems of its font. The height stays the short height. The ellipsis has no room left to replace the label.

**semantic-key:** PK-6406

## Rust row

The row is written in the ledger, after the product commit. The match is this: the mood dialog Approve button is wider by three ems after the short fit. The label stays Approve. The height stays short. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** After the short fit, Approve grows by three ems and keeps the short height, so the label is not replaced by an ellipsis.

**Reason.** The short fit was truncating the word Approve.

**Avoidances.** No change to the Cancel width, no change to the label text, no crate, and no Rust slice until the catch-up is named.

**semantic-key:** PK-6407

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

The mood stays with the app. The switch flips. The line above the inspector shows the string while the switch is on.

**semantic-key:** PK-7837

## Goal

A saved mood and the switch position survive closing the app. The switch flips once a mood exists. Until then it is grey. While the switch is on, the string is a line above the inspector.

**semantic-key:** PK-6408

## What stays

The string is not a vault column and not a cell. Export does not carry it. Clear turns leaves it. The Mood button stays enabled. Approve of an empty field writes nothing. The shape line still follows the string on a send. Left stays off. Right stays on. The knob colors stay for an enabled switch.

**semantic-key:** PK-6409

## File

The edits are on `AetherisNotebookPanel`. The file is `~/.casserole/aetheris-mood.yaml`, version 1. The keys are `line` and `on`. A missing file is no string and the switch off. A version other than 1 is left unread and is not overwritten. A flip writes the file. Approve of a non-empty field writes the file.

**semantic-key:** PK-6410

## Switch

With a saved string, a click flips on or off and nothing else. With no string, the switch is grey, stays off, and a click does nothing. The Mood button is not grey. Approve of a non-empty field saves the string and turns the switch on when it was off.

**semantic-key:** PK-6411

## Line

A yellow line sits above the inspector. While the switch is on, the line shows the saved string. While the switch is off, the line is not shown.

**semantic-key:** PK-6412

## Tip

The switch tip names the grey state, the flip, and that the mood stays. The Mood button tip names that Approve turns the switch on when it was off and that the string stays. Those tips are the tooltips for the controls.

**semantic-key:** PK-6413

## Rust row

The row is written in the ledger, after the product commit. The match is this: the mood string and the switch position are a home file, not a vault column. The next open restores both. The switch is grey until a string exists, then a click flips it. Approve turns it on when it was off. A yellow line above the inspector shows the string while the switch is on. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** `line` and `on` in the home mood file survive the app, the switch stays grey until a string exists, and a yellow line above the inspector shows that string only while the switch is on.

**Reason.** The mood was dying with the session, and the operator needs the same string and the same switch position on the next open.

**Avoidances.** No vault column, no export of the string, no grey Mood button, no crate, and no Rust slice until the catch-up is named.

**semantic-key:** PK-6414

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

The mood string rises in the dialog. The height follows the wrap.

**semantic-key:** PK-7838

## Goal

When a reply sets the mood string, a pane rises from the top of the mood-dialog footer and shows that string. The height follows the wrap. The resting footer stays clear.

**semantic-key:** PK-6415

## What stays

The string is not a vault column and not a cell. Approve of an empty field writes nothing. The pane is editable, and Approve reads it. The canvas thinking pane is not this pane. The shape line still follows the string on a send. The saved mood file stays the file already landed.

**semantic-key:** PK-6416

## Footer

The edits are on `AetherisNotebookPanel`. The resting footer of the mood dialog is the spinner, Approve, and Cancel. The single-line mood field and the Mood string label leave that row.

**semantic-key:** PK-6417

## Pane

When a reply sets a non-empty mood string, a pane rises from the top of that footer and shows the string. The rise slides from height 0 to the wrapped height. A later change sets the new height without sliding from zero. An empty string hides the pane. The pane is not saved.

**semantic-key:** PK-6418

## Height

Word wrap at the pane width. One visual line is one line tall. Two visual lines are two lines tall. The height stops at half the dialog. Taller text scrolls. When the pane scrolls, the wrap uses the width inside the scrollbar. A dialog resize remeasures the wrap.

**semantic-key:** PK-6419

## Tip

The pane tip names the rise, the wrap, and the edit before Approve. The Mood button tip names that the string rises above the dialog footer. Those tips are the tooltips for the controls.

**semantic-key:** PK-6420

## Rust row

The row is written in the ledger, after the product commit. The match is this: the mood string rises from the top of the dialog footer when a reply sets it. The height follows the wrap. One line is one line tall. The resting footer is the spinner, Approve, and Cancel. Past half the dialog the pane scrolls. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** A non-empty mood reply raises an editable pane from the top of the dialog footer, and the pane's height is the wrapped line count up to half the dialog.

**Reason.** The string has to be easy to inspect and approve, and the resting footer has to stay the spinner, Approve, and Cancel.

**Avoidances.** No saved pane, no use of the canvas thinking pane, no crate, and no Rust slice until the catch-up is named.

**semantic-key:** PK-6421

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

The mood dialog keeps its size.

**semantic-key:** PK-7839

## Goal

The mood dialog opens at the size it had when it last closed on Approve or Cancel.

**semantic-key:** PK-6422

## What stays

The string is not a vault column and not a cell. The place on screen is not kept. The dialog still centers. An empty Approve still writes no mood string. A version other than 1 is left unread and is not overwritten. The rising pane still follows the dialog height.

**semantic-key:** PK-6423

## File

The edits are on `AetherisNotebookPanel`. The existing `~/.casserole/aetheris-mood.yaml`, version 1, gains `width` and `height`. A missing pair opens the dialog at 760 by 520. A kept width from 480 through 4000 and a height from 320 through 4000 is used. A number outside that range is ignored.

**semantic-key:** PK-6424

## Close

Approve and Cancel write the size, then the dialog closes. The title-bar close uses that same close, so it writes the size too. A later switch flip keeps the pair in the file.

**semantic-key:** PK-6425

## Tip

Approve and Cancel name that the dialog keeps this size. The Mood button names that the dialog opens at the size it had when it last closed. Those tips are the tooltips for the controls.

**semantic-key:** PK-6426

## Rust row

The row is written in the ledger, after the product commit. The match is this: the mood dialog keeps the width and height from the last Approve or Cancel. The next open uses that pair. Until then it opens at 760 by 520. The place on screen is not kept. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** Approve, Cancel, and the title-bar close write `width` and `height`, and the next open uses a pair inside 480 through 4000 by 320 through 4000, or else 760 by 520.

**Reason.** The dialog should come back at the size it was left, without remembering where on the screen it sat.

**Avoidances.** No stored screen position, no use of a size outside that range, no crate, and no Rust slice until the catch-up is named.

**semantic-key:** PK-6427

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

An invited enrichment fills the mood line.

**semantic-key:** PK-7840

## Goal

When the user asks the mood model for more detail, the control header tells that model to say the detail in the reply and to put more semantic tokens on the mood line.

**semantic-key:** PK-6428

## What stays

The pluck stays. The last mood line still fills the field. The canvas shape line stays `Activate the mood above. Use that mood as the shape of your responses.` The mood file stays the mood file. A missing mood line still asks again, up to three tries. A short line stays enough when the user did not ask for more.

**semantic-key:** PK-6429

## System line

The edits are on `AetherisNotebookPanel`. `MOOD_SHAPE_SYSTEM` becomes these words. You fill a mood token string. The app stores that string. The app does not store the rest of the reply as the mood. End every reply with one final line. That line is the mood token string. The line starts with mood: and the tokens follow on that same line. Separate the tokens with commas. Each token is one short meaning: a style, a tone, a stance, or a symbol. No stars. No quotes. No code fence on that line. Before that line, say what you determined. When the user asks for more detail, a richer mood, more tokens, or an enriched mood, do both of these. Say the extra detail in the reply, in the words you determined. Put that same detail on the mood line as more tokens. The mood line must contain the new tokens. Do not leave the new detail only in the talk. If the user did not ask for more, a short line is enough. Short example: `mood: spare, dry, lantern-light`. Enriched example, when the user asked for more: `mood: spare, dry, lantern-light, short sentences, no warmth, gravel voice, night watch`. When the user asked for more, add tokens until the line holds the detail you named.

**semantic-key:** PK-6430

## Format ask

`MOOD_FORMAT_ASK` becomes these words. Return the mood text on one final line. The line starts with mood: and the rest of that line is the mood token string. Separate the tokens with commas. No stars and no quotes on that line. If the user asked for more detail, that line carries more tokens for that detail. Do not leave the detail only in the talk. Short example: `mood: spare, dry, lantern-light`.

**semantic-key:** PK-6431

## Tip

The Mood button tip adds that when you ask for more detail, the mood line is told to carry those extra tokens. That tip is the tooltip for the control.

**semantic-key:** PK-6432

## Rust row

The row is written in the ledger, after the product commit. The match is this: when the user asks the mood model for more detail, the control line tells it to say that detail and to put more tokens on the mood line. The app still keeps that line. A short line stays enough when the user did not ask for more. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** An invited enrichment must appear both in the reply and as more comma-separated tokens on the final `mood:` line, and a request that did not ask for more may stay a short line.

**Reason.** The model was saying the extra detail and leaving the stored mood string thin, because the header did not require the new tokens on the mood line.

**Avoidances.** No change to the canvas shape line, no store of the rest of the reply as the mood, no crate, and no Rust slice until the catch-up is named.

**semantic-key:** PK-6433

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. They do not retarget the earlier heads.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

A mood turn keeps its own wire id.

**semantic-key:** PK-7841

## Goal

A line typed on the mood glass is sent. The yellow slide does not say `keyword only. cell-busy`.

**semantic-key:** PK-6434

## What stays

The server still answers `cell-busy` when a second frame arrives for a canvas cell that is running. The pluck stays. A missing mood line still asks again. A frame for a mood id still stays off the cell. The saved string and the switch stay.

**semantic-key:** PK-6435

## Wire

The edits are on `AetherisNotebookPanel`. `openMood` no longer mints the wire id. `sendMood` mints a new id, stores it as the current mood id, and registers it before the frame is written. The format ask uses that same path, so it gets its own id. A late frame for an older id stays on the mood list and stays off the cell. The reply fills the glass only when the id is the one just sent.

**semantic-key:** PK-6436

## Rust row

The row is written in the ledger, after the product commit. The match is this: each mood send uses a new wire id. The server still refuses a second frame for a canvas cell that is running. The mood glass stays open. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** Each mood send, including a format ask, mints and registers its own wire id before the frame is written, so the glass does not collide with a running canvas cell.

**Reason.** A reused mood id was answered `keyword only. cell-busy` when the canvas cell that shared it was still running.

**Avoidances.** No weakening of `cell-busy` for a canvas cell, no mood frame that marks a cell, no crate, and no Rust slice until the catch-up is named.

**semantic-key:** PK-6437

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the mood string through the wire-id change stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

A saved mood opens in the field. The mood send carries those tokens in the header. Clear mood starts empty.

**semantic-key:** PK-7842

## Goal

A saved mood is already in the field when the dialog opens. The operator can ask the model to enhance it. The model sees those tokens in the header. Clear mood starts from scratch.

**semantic-key:** PK-6438

## What stays

The pluck stays. The shape line stays. A canvas send still carries the mood string and the activate line only while the switch is on. Each mood send still mints its own wire id. Approve and Cancel still keep the dialog size. An empty Approve still writes nothing.

**semantic-key:** PK-6439

## Field

The edits are on `AetherisNotebookPanel`. When the dialog opens and a saved mood is present, the field is set to that string. The pane rises. An empty save still opens an empty field.

**semantic-key:** PK-6440

## Header

After the shape line, a non-empty field adds that string and then the activate line, before the chat. An empty field adds nothing. The switch does not gate this chat.

**semantic-key:** PK-6441

## Clear

Clear mood clears the saved string, turns the switch off, empties the field and the chat, and drops a reply already in flight. The dialog stays open. The kept size stays. The tip names that click. That tip is the tooltip for the control.

**semantic-key:** PK-6442

## Rust row

The row is written in the ledger, after the product commit. The match is this: a saved mood is already in the rising field when the dialog opens. A mood send keeps the shape line, then that field, then the activate line, before the chat. An empty field sends no mood tokens. Clear mood drops the saved string, turns the switch off, and empties the field and the chat. The dialog stays open. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** A saved mood opens in the rising field, a mood send places that field and the activate line after the shape line, and Clear mood empties the string and the chat without closing the dialog.

**Reason.** Enhancement has to see the tokens already saved, the same way a conversation send carries them, and a fresh start has to drop that string without losing the dialog.

**semantic-key:** PK-6443

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the mood string through the field seed stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

Clear mood asks before it drops the string. Yes clears. No leaves the mood.

**semantic-key:** PK-7843

## Goal

Clear mood asks before it drops the string. Yes clears. No leaves the mood. The mood dialog stays open.

**semantic-key:** PK-6444

## What stays

The saved field seed stays. The mood header stays. Approve and Cancel stay. The clear itself stays the clear that drops the saved string, turns the switch off, and empties the field and the chat. The kept size stays. Enter does not clear. The switch colors stay the same green and the same red.

**semantic-key:** PK-6445

## Ask

Clear mood opens a small modal over the mood dialog. The words are Clear the Mood Setting? Yes is green. No is red. The ask is not resizable. It sits over the mood dialog.

**semantic-key:** PK-6446

## Yes

Yes is the only click that runs the clear. The ask closes. The mood dialog stays open.

**semantic-key:** PK-6447

## No

No closes the ask. Escape closes the ask. The window close closes the ask. The mood string, the switch, the field, and the chat stay. The mood dialog stays open.

**semantic-key:** PK-6448

## Tips

The Clear mood tip and the Mood button tip name the ask. Each tip is the tooltip for that control, and it says what the control is and what the click does.

**semantic-key:** PK-6449

## Rust row

The row is written in the ledger, after the product commit. The match is this: Clear mood asks Clear the Mood Setting? Yes clears the saved string and the chat. No leaves the mood and closes the ask. The mood dialog stays open. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** Clear mood asks Clear the Mood Setting? before it drops the string, Yes runs the clear, and No leaves the mood with the dialog still open.

**Reason.** A clear that drops the string on the first click gives no chance to leave the mood in place.

**semantic-key:** PK-6450

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the mood string through the clear ask stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit records the product types in the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust slice stays unwritten.

Yes and No show their words.

**semantic-key:** PK-7844

## Goal

Yes and No show their words on the clear ask. Yes stays green. No stays red.

**semantic-key:** PK-6451

## What stays

The ask stays. The words stay Clear the Mood Setting? Yes still clears. No, Escape, and the window close still leave the mood. The mood dialog stays open. The colors stay the same green and the same red. Enter still does not clear.

**semantic-key:** PK-6452

## Measure

The edit is `paintClearChoice` on `AetherisNotebookPanel`. The short fit locks a width from before the colored border. That width is dropped. The button is measured from the word, the bevel, the pad, and two spare ems. The minimum is that size. The short height stays when the word still fits. A tighter height grows to the word.

**semantic-key:** PK-6453

## Rust row

The row is written in the ledger, after the product commit. The match is this: Yes and No are wide enough to show the words. Yes stays green. No stays red. The shape says no crate. The Rust slice waits until the catch-up is named.

**Truth.** Yes and No on the clear ask are wide enough to show their words, Yes stays green, and No stays red.

**Reason.** The short fit was locking a width from before the colored border, and that width was cutting the words.

**semantic-key:** PK-6454

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the clear-ask word widths stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The seated slice is named. This row writes no crate.

The pin bar sits on the canvas.

**semantic-key:** PK-7845

## Goal

The pin box, the word field, and OK sit on a thin bar at the top of the canvas.

**semantic-key:** PK-6455

## What stays

The pin words stay. A miss goes Excluded. An excluded match goes Included. Any other match stays. OK writes those states. The click does not open Mood. The OK tip stays the sentence already on the button. Turning the box off clears the words and leaves the states. The edge paint stays in `paintChrome`. The inspector header keeps soft wrap and the two counters. The Rust pin row stays seated. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6456

## Bar

The edit is `AetherisNotebookPanel`. `buildPinBar` is private. It builds the pin box, the word field, and OK, with the tips and the clicks those controls already had. The constructor seats that bar on the north of the canvas side. The canvas tabs stay under the bar. `wrap` keeps the Inspector label, soft wrap, and the two counters. It no longer adds the pin controls.

**semantic-key:** PK-6457

## Rust row

The row is written in the ledger, after the product commit. The match is this: the pin box, the field, and OK sit on a thin bar at the top of the canvas. No side move on this plate. This land leaves the working set as it stands. The slice is `rust-aetheris-pin-ok-slice`. This row writes no crate.

**Truth.** The pin box, the word field, and OK sit on a thin bar at the top of the canvas, and the canvas tabs stay under that bar.

**Reason.** Those controls were on the inspector header, and the bar puts them on the north of the canvas side without a side move.

**semantic-key:** PK-6458

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the pin-bar fold stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The seated slice is named. This row writes no crate.

The folded pin bar leaves the canvas.

**semantic-key:** PK-7846

## Goal

The folded pin bar leaves the canvas. P brings it back. Folding leaves the pin box as it is.

**semantic-key:** PK-6459

## What stays

The pin words stay until the user changes them. Only the pin box turns the filter off. A miss goes Excluded. An excluded match goes Included. Any other match stays. OK writes those states. The click does not open Mood. The OK tip stays the sentence already on the button. The edge paint stays in `paintChrome`. The inspector header keeps soft wrap and the two counters. The shell key stays `ui.aetheris.pin.bar.open`. A missing key stays folded. The Rust pin row stays seated. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6460

## Tuck

The edit is `AetherisNotebookPanel`. `buildPinBar` stays private. v on the open bar hides the whole bar. The cards use the height they had before the bar. P is tucked on the east of the canvas tab header, the way C sits on the east of the thinking grip. P shows only while the bar is folded. P adds no row. v stays v. P does not uncheck the pin. The saved fold stays `shellPinBarOpen`.

**semantic-key:** PK-6461

## Rust row

The row is written in the ledger, after the product commit. The match is this: the folded pin bar leaves the canvas. P brings it back from the tab header. Folding leaves the pin box as it is. No side move on this plate. This land leaves the working set as it stands. The slice is `rust-aetheris-pin-ok-slice`. The crate move for the tuck waits until that move is named. This row writes no crate.

**Truth.** The folded pin bar leaves the canvas, P on the east of the canvas tab header brings it back, and folding leaves the pin box as it is.

**Reason.** A folded bar that still occupies the canvas keeps the cards short of the height they had before the bar.

**semantic-key:** PK-6462

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the folded pin bar stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The seated slices are named. This row writes no crate.

P and C wear sapphire.

**semantic-key:** PK-7847

## Goal

P and C wear sapphire. A one-pixel white bevel surrounds each face. The letter is white.

**semantic-key:** PK-6463

## What stays

The seat of P stays on the east of the canvas tab header. P adds no row. P shows while the pin bar is folded. C stays on the east of the thinking grip. C still clears the log. The other short buttons keep the theme bevel. Folding leaves the pin box as it is. The words stay. The shell key stays `ui.aetheris.pin.bar.open`. The edge paint stays in `paintChrome`. The OK tip stays the sentence already on the button. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6464

## Face

The edit is `AetherisNotebookPanel`. `paintMarkChoice` stays private. It paints P and the thinking-pane C. The fill is the armed-card sapphire. A one-pixel white line surrounds the face. The letter is white. A shell font change fits the short button, then the mark paint returns. The cell-state P stays on the theme face. v stays v. The seat of P stays.

**semantic-key:** PK-6465

## Rust row

The row is written in the ledger, after the product commit. The match is this: P and C wear sapphire. A one-pixel white bevel surrounds each face. The letter is white. No side move on this plate. This land leaves the working set as it stands. The pin slice is `rust-aetheris-pin-ok-slice`. The think slice is `rust-aetheris-think-slice`. The crate move for the face waits until that move is named. This row writes no crate.

**Truth.** P and C wear the armed-card sapphire, a one-pixel white bevel surrounds each face, and the letter is white.

**Reason.** Those two marks are the tuck and the clear, and the armed-card sapphire is the fill that sets them apart from the theme bevel.

**semantic-key:** PK-6466

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the sapphire faces stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit that already holds the paint.

The edit below is the paint already in `paintChrome`. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. No product commit. The paint is already in the tree. Section 12 of the Rust client serial gains this row in that ledger. The seated slice is named. This row writes no crate.

The pin edge is the paint already on the cards.

**semantic-key:** PK-7848

## Goal

The pin edge is the paint already on the cards. While the pin box is on, a readable match wears green and a readable miss wears dim. The armed card stays sapphire.

**semantic-key:** PK-6467

## What stays

The inner prompt and reply borders stay their own green line. The pin box stays the only off switch. The words stay until the user changes them. The shell key stays `ui.aetheris.pin.bar.open`. The OK tip stays the sentence already on the button. The seat of P stays. The face of P and C stays. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6468

## Edge

The paint is `paintChrome` on `AetherisNotebookPanel`. While the pin box is on, a readable match is `#3cb371` and a readable miss is `#222222`. The armed card, and every card in a group whose merge is armed, is `#2e86ff`. That sapphire wins over the pin color. This row writes no new Java. The proof is the product commit that already holds the paint.

**semantic-key:** PK-6469

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made. The product diff stays empty.

**semantic-key:** PK-6470

## Rust row

The row is written in the ledger, after the stamp. The match is this: the pin edge is the paint already on the cards. A readable match is green. A readable miss is dim. The armed card stays sapphire. A merge arm lights the group. No side move on this plate. This land leaves the working set as it stands. The slice is `rust-aetheris-pin-ok-slice`. The crate move for the edge waits until that move is named. This row writes no crate.

**Truth.** While the pin box is on, a readable match is `#3cb371`, a readable miss is `#222222`, and the armed card and a merge-armed group stay `#2e86ff`.

**Reason.** That edge paint is already on the cards, so this landing records it and writes no new product code.

**semantic-key:** PK-6471

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the pin edge stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. The Rust face follows in this same course.

P is green while the pin is on and sapphire while the pin is off. Java is first.

**semantic-key:** PK-7849

## Goal

P is green while the pin is on and sapphire while the pin is off. Green is `#3cb371`. Sapphire is `#2e86ff`. The letter stays white. The one-pixel white bevel stays. C stays sapphire.

**semantic-key:** PK-6472

## What stays

Java keeps P on the east of the canvas tab header. P adds no row. P shows while the pin bar is folded. C stays on the east of the thinking grip. C still clears the log. The other short buttons keep the theme bevel. The pin box stays the only switch. The words stay. The shell key stays `ui.aetheris.pin.bar.open`. The edge paint stays in `paintChrome`. v stays v. The cell-state P stays on the theme face. The six-pixel inset stays off. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6473

## Face

The edit is `AetherisNotebookPanel`. `paintMarkChoice` stays private. P is green while the pin is on and sapphire while the pin is off. C stays sapphire because the fill follows the pin box only when the button is P. A one-pixel white line surrounds the face. The letter is white. A shell font change fits the short button, then the mark paint returns the same face. Turning the pin box on or off paints P again, including while P is hidden. The tip says green means the pin is on, and sapphire means the pin is ready and off. That tip says what the control is and what the click does.

**semantic-key:** PK-6474

## Rust row

The row is written in the ledger, after the product commit. The match is this: P is green while the pin is on and sapphire while the pin is off. Green is `#3cb371`. Sapphire is `#2e86ff`. The letter stays white. The one-pixel white bevel stays. C stays sapphire. Java keeps P on the tab. No side move on this plate. This land leaves the working set as it stands. The Rust face follows in this course.

**Truth.** P is `#3cb371` while the pin is on and `#2e86ff` while the pin is off, the letter and the one-pixel white bevel stay, and C stays sapphire.

**Reason.** The pin mark has to show whether the pin is on, and the clear mark on the thinking grip stays the armed-card sapphire.

**semantic-key:** PK-6475

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the pin-face colors stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. Java holds the core. The seated crate already paints Expanded View. This row writes no crate.

Each readable cell carries Expanded View.

**semantic-key:** PK-7850

## Goal

Each readable cell carries Expanded View. Checked, the reply text pane grows to the wrapped buffer and shows all of the text inside its border. Unchecked, the reply stays at the saved view-row count, the same height as the prompt. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell's choice. Java holds the core.

**semantic-key:** PK-6476

## What stays

The collapse control stays the saved fold. A folded card stays a brief line. The prompt keeps the footer count. The footer count stays on the shell. Labels stay New and Clear. An unreadable card has no box. The choice stays for this window. It is not saved. It is not a vault column and not an export field. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6477

## Box

The edit is `AetherisNotebookPanel`. The checkbox reads Expanded View. It sits on the readable cell header after the collapse control and before the burner checkbox. The tip says the checked reply shows the whole reply, an unchecked reply stays the same height as the prompt, the footer count sets that height, the text stays, a fresh canvas and Clear start unchecked, a new turn keeps the prior cell's choice, a folded card stays a brief line, and the choice stays for this window and is not saved. The three row-count tips and the expand tip say the prompt keeps the count, an unchecked reply keeps the count, and a checked reply stays whole. Each of those tips says what the control is and what the click does.

**semantic-key:** PK-6478

## Memory

The flag is a window set of cell ids on the panel. A missing id is unchecked. A vault bind clears the set before the cards are built again. Footer Clear drops the removed ids and leaves the new card unchecked. A new turn copies the prior cell. An empty deck stays unchecked. Each cell made by duplicate group starts checked. Import, Pull, and a merge cell are new ids and start unchecked.

**semantic-key:** PK-6479

## Fit

Unchecked, the reply pane keeps the saved row count and scrolls inside. Checked, the reply rows sit at one and both reply scroll bars stay off, and the pane height is the wrapped buffer plus the insets plus one line. A width under 40 keeps the row-count height until the pane has a width. A document listener on the reply and a resize listener on the card queue that fit. The same height does not revalidate again. A group member row grows with the card. The prompt pane keeps the row-count height.

**semantic-key:** PK-6480

## Rust row

The row is written in the ledger, after the product commit. The match states the Java requirement in full. Java holds the core. The seated crate already paints Expanded View. This row writes no crate. No side move on this plate. This land leaves the working set as it stands.

**Truth.** Each readable cell carries Expanded View, a checked reply shows the whole text inside its border, and an unchecked reply stays the footer row count.

**Reason.** The reply has to open to the wrapped buffer without losing the saved view-row height the prompt already uses.

**semantic-key:** PK-6481

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through Expanded View stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. Java holds the core. The seated crate already paints the embedded line and the first lead. This row writes no crate.

The model name and the timestamp ride on the Java cells. The paint crosses the border. The paint does not take the north pad.

**semantic-key:** PK-7851

## Goal

The focused Java cell wears the local stamp and the short model on its outer frame. The paint of that line crosses the border. The cell does not grow. Java holds the core.

**semantic-key:** PK-6482

## What stays

The cell border stays the line and the six-pixel inset it already paints. The column inset stays eight pixels. The input pane border stays a plain stroke. Expanded View stays. The fold stays. The sapphire line stays. The pin stays. The buttons, the prompt, and the reply keep their size. The cell keeps its width. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6483

## Stamp

The edit is `AetherisNotebookPanel`. The model label leaves the header. The focused cell wears `── stamp · model ──` centered on the top stroke, seated on the right, eight pixels in. A blank model keeps the stamp. The stamp is local wall time, year, month, day, hour, minute, and second. One timer on the panel repaints that line once a second. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. An armed merge keeps the sapphire line. The line stays on the focused cell. The hover says the line is the model this turn will call. That hover is the tooltip for the line.

**semantic-key:** PK-6484

## Paint

The column paints the line after its children, so the line lies across the border. The card does not gain a north pad, a first-cell lead, or an inner button pad.

**semantic-key:** PK-6485

## Rust row

The row is written in the ledger, after the product commit. The match states the Java requirement in full. Java holds the core. The seated crate already paints the embedded line and the first lead. This row writes no crate. The Rust lead stays on the crate. No side move on this plate. This land leaves the working set as it stands.

**Truth.** The focused cell wears `── stamp · model ──` on the top stroke, the line is painted across the border, and the cell does not grow.

**Reason.** The model name and the timestamp have to ride the outer frame without a north pad, a first-cell lead, or an inner button pad.

**semantic-key:** PK-6486

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the stamp line stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. Java holds the core. The seated crate still paints the character rule. This row writes no crate.

The rule beside the stamp is a stroke. The paint draws that rule. The text carries no box rule.

**semantic-key:** PK-7852

## Goal

The focused Java cell still wears the stamp and the short model on its outer frame. The rule on each side is a stroke. The cell does not grow. Java holds the core.

**semantic-key:** PK-6487

## What stays

The cell border stays the line and the six-pixel inset it already paints. The column inset stays eight pixels. The input pane border stays a plain stroke. The clock stays. The hover stays. Expanded View stays. The fold stays. The sapphire line stays. The pin stays. The buttons, the prompt, and the reply keep their size. The cell keeps its width. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6488

## Rule

The edit is `AetherisNotebookPanel`. The words are the stamp and the short model. A middle dot sits between them. A blank model keeps the stamp. Each flank is a one-pixel stroke, two ems long, in the caption ink, on the top border. One space of air sits between the stroke and the words. The text carries no box rule. The line stays centered on that stroke and seated on the right, eight pixels in. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. An armed merge keeps the sapphire line. The line stays on the focused cell. The hover stays. The card does not gain a north pad, a first-cell lead, or an inner button pad.

**semantic-key:** PK-6489

## Rust row

The row is written in the ledger, after the product commit. The match states the Java requirement in full. Java holds the core. The seated crate still paints the character rule. This row writes no crate. The crate move for this delta waits until that move is named. No side move on this plate. This land leaves the working set as it stands.

**Truth.** The rule beside the stamp is a one-pixel stroke, two ems long, and the text carries no box rule.

**Reason.** A character rule in the caption text is the piece this paint replaces, while the seated crate still paints that character rule.

**semantic-key:** PK-7956

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the stroke rule stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. Java holds the core. The seated crate still paints the character rule. This row writes no crate.

The stroke beside the stamp wears the selection sapphire. The words stay the caption ink.

**semantic-key:** PK-7853

## Goal

The focused Java cell still wears the stamp and the short model on its outer frame. The rule on each side is a stroke. That stroke wears the selection sapphire. The words stay the caption ink. The cell does not grow. Java holds the core.

**semantic-key:** PK-6490

## What stays

The cell border stays the line and the six-pixel inset it already paints. The column inset stays eight pixels. The input pane border stays a plain stroke. The clock stays. The hover stays. Expanded View stays. The fold stays. The pin stays. The buttons, the prompt, and the reply keep their size. The cell keeps its width. The words stay the caption ink. The text carries no box rule. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6491

## Stroke

The edit is `AetherisNotebookPanel`. The words are the stamp and the short model. A middle dot sits between them. A blank model keeps the stamp. Each flank is a one-pixel stroke, two ems long, on the top border. The stroke uses `SELECTION_SAPPHIRE`, the same color the armed card already paints. One space of air sits between the stroke and the words. The words stay the caption ink. The text carries no box rule. The line stays centered on that stroke and seated on the right, eight pixels in. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. An armed merge keeps the sapphire line. The line stays on the focused cell. The hover stays. The card does not gain a north pad, a first-cell lead, or an inner button pad.

**semantic-key:** PK-6492

## Rust row

The row is written in the ledger, after the product commit. The match states the Java requirement in full. Java holds the core. The seated crate still paints the character rule. This row writes no crate. The crate move for this delta waits until that move is named. No side move on this plate. This land leaves the working set as it stands.

**Truth.** The stroke beside the stamp uses `SELECTION_SAPPHIRE`, and the words stay the caption ink.

**Reason.** The armed card already paints that sapphire, and the flank strokes take the same color without putting a box rule in the text.

**semantic-key:** PK-7957

## Approval

Approval locks the sketch in place, files this plan, and names the build. The stamp does not move the stage, and it does not retarget the working page or the current action. The stage stays 1 and built. The working page stays the stage map. The current action stays the boot sit. The recorded when-stamp stays. Where this page is thinner than the locked finessing row, the locked row wins.

The dependency weighing sits on the sketch. The edits land in the sections below.

The sketch stays at its first serial. A second serial is not opened. The finessing row is the lock. Earlier finessing rows are not rewritten. The rows from the thinking pane through the sapphire stroke stay as each landing wrote them. They do not retarget the earlier heads. The proof on the papers is the product commit.

The build is the edits below. No lambdas in our types. No Lombok. No JUnit. No new G-key. No new public type. No new package. No new public method. No new vault column. No new channel. No new wire type. From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. No live call is sent. A pool turn is not sent. A live SSH connect is not made. One commit of the product type records the tree. Neither commit is pushed. Section 12 of the Rust client serial gains this row in that ledger. Java holds the core. The seated crate still paints the character rule. This row writes no crate.

A click on a text pane puts scroll focus on that pane. A click outside that pane returns scroll focus to the canvas. Hover on a cell shows the model as it is stamped when a cell is selected.

**semantic-key:** PK-7854

## Goal

A click on a text pane puts scroll focus on that pane. A click outside that pane returns scroll focus to the canvas. The armed cell stays armed. Hover on a cell shows the stamp and the short model. The cell does not grow. Java holds the core.

**semantic-key:** PK-6493

## What stays

The sapphire stroke stays. The words stay the caption ink. A quiet cell still paints no line. The clock stays. The focused hover stays the line is the model this turn will call. The execution target keeps the caret. The buttons, the prompt, and the reply keep their size. The cell keeps its width. Expanded View stays. The fold stays. The pin stays. There is no north pad. There is no first-cell lead. There is no inner button pad. No side move on this plate. This land leaves the working set as it stands.

**semantic-key:** PK-6494

## Focus

The edit is `AetherisNotebookPanel`. A click on the prompt or the reply, including its scrollbar, calls `requestFocusInWindow` on that text. A click on the card outside those panes calls `requestFocusInWindow` on the canvas `JScrollPane`, except the execution target, which keeps the caret. A click on the canvas padding, the viewport, or a group block focuses the canvas and does not change the armed cell. The canvas scroller is focusable.

**semantic-key:** PK-6495

## Wheel

Each text scroller has wheel scrolling turned off. A wheel listener consumes the event. While that text is the focus owner, the wheel moves that pane's vertical bar and stops at its end. Otherwise the wheel moves the canvas vertical bar. The walk to the canvas starts above the text pane. A block wheel uses the block increment. A unit wheel uses `getUnitsToScroll` times the bar's unit increment.

**semantic-key:** PK-6496

## Hover

Hover on the card, the header, the prompt, the reply, their scrollers, and a folded brief shows the stamp and the short model. The words are the same words a selected cell paints. A blank model keeps the stamp. The clock is read when the tip is asked for. A quiet cell still paints no line. The focused line keeps the tip the line is the model this turn will call. Buttons and the execution target keep their own tips. Each tip says what the control is and what the click does.

**semantic-key:** PK-6497

## Rust row

The row is written in the ledger, after the product commit. The match states the Java requirement in full. Java holds the core. The seated crate still paints the character rule. This row writes no crate. The crate move for this delta waits until that move is named. No side move on this plate. This land leaves the working set as it stands.

**Truth.** A click on a text pane puts scroll focus on that pane, a click outside it returns scroll focus to the canvas, and hover shows the stamp and the short model.

**Reason.** Wheel movement has to follow the pane that holds focus, and the hover has to read the same stamp a selected cell paints.

**semantic-key:** PK-7958

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the sent-level work and on the send-effort work. It also depends on the standing style limits: no Lombok, no lambdas in our types, anonymous classes only, no new package, and no new public type. The New turn tip carries the tooltip duty for the sentence this slice adds.

**semantic-key:** PK-7961

## What this sketch is

The plan is cut from one copy on `newTurn`. A new turn, from the button or from a finished response, starts with the prior turn's model config.

**semantic-key:** PK-6498

## What this sketch is not

This is not an edit of the Grok serial. It is not a copy on import, Pull, Clear turns, or the merge sink. It is not a new vault column. It is not a copied prompt.

**semantic-key:** PK-6499

## The slice

Continued chat stays on the model just used. The operator switches when choosing Ollama, or when setting up a merge.

Done means `newTurn` copies the last cell's execution target, Grok document, and session think holder onto the new cell. The prompt stays empty. The card stays Included and ungrouped. Import, Pull, Clear, and the merge sink stay as they are. A quiet compile that does not run tests passes. The window stays closed. The live call is not made.

**Truth.** The new cell inherits the last cell's execution target, Grok document, and session think holder, and it inherits nothing of the prompt.

**Reason.** Continued chat should stay on the model just used, and a switch to Ollama or a merge setup remains a deliberate change rather than a side effect of starting a turn.

**Avoidances.** No Grok-serial edit, no vault column, no copied prompt, and no copy planted on import, Pull, Clear, or the merge sink.

**semantic-key:** PK-6500

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the model-copy work and on the standing style limits: no Lombok, no lambdas in our types, anonymous classes only, no new package, and no new public type.

**semantic-key:** PK-7962

## What this sketch is

The plan is cut from one selection move on the card born after a finished last cell. When a turn completes and a new card is appended, that card is the selected focus.

**semantic-key:** PK-6501

## What this sketch is not

This is not a change to the New turn button's selection. It is not a change to import, Pull, Clear turns, or the merge sink. It is not a new vault column. It is not a change to the model copy.

**semantic-key:** PK-6502

## The slice

The card that just appeared is the card the operator is on. The sapphire edge, the inspector, and the caret agree.

Done means `newTurn(true)` writes `focusedCellId` and `pendingFocusId` to the new cell before `rebuildTabs`. The caret lands in that prompt. The canvas scrolls to the end. The model copy stays. The New turn button still passes false. A quiet compile that does not run tests passes. The window stays closed. The live call is not made.

**Truth.** The completion path writes both focus ids onto the new cell before the tabs rebuild, and the button path still passes false.

**Reason.** The sapphire edge, the inspector, and the caret have to name the same new card, or the operator is looking at one cell while the selection still points at the finished one.

**Avoidances.** No button-selection change, no vault column, no model-copy change, and no edit of import, Pull, Clear turns, or the merge sink.

**semantic-key:** PK-6503

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the model-copy work and on the selection move that follows a finished last cell. It also depends on the standing style limits: no Lombok, no lambdas in our types, anonymous classes only, no new package, and no new public type.

**semantic-key:** PK-7963

## What this sketch is

The plan is cut from one landing so a new turn finishes at the bottom of the canvas. When a new turn is created, the canvas ends at the bottom.

**semantic-key:** PK-6504

## What this sketch is not

This is not a move of the sapphire edge on the New turn button. It is not a change to import, Pull, Clear turns, or the merge sink. It is not a new listener. It is not a new public type. It is not a change to the model copy.

**semantic-key:** PK-6505

## The slice

Both entries into `newTurn` aim the scroll at the new card. The caret goes in that prompt. The viewport is then pinned to the end, and pinned once more after the next layout. The sapphire edge still moves only when the new card follows a finished last cell.

Done means `newTurn` writes `pendingFocusId` to the new cell and sets `pendingLandOnInput` before `rebuildTabs`. The land pass focuses the prompt, then pins the canvas end, then pins it again on the next event-dispatch pass. A sideways-only member row is not the pane that moves. `focusedCellId` still moves only on `newTurn(true)`. A quiet compile that does not run tests passes. The window stays closed. The live call is not made.

**Truth.** Every new turn requests a land on the new prompt and a pin to the canvas end, repeated once after the next layout.

**Reason.** The caret has to be in the new prompt and the viewport has to show the bottom, or the new card exists while the operator is still looking at the cards above it.

**Avoidances.** No new listener, no new public type, no sapphire move on the button path, and no change to the model copy or to import, Pull, Clear turns, or the merge sink.

**semantic-key:** PK-6506

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the group row on the finessing page, on the sapphire edge of a merge arm, on the error slide, and on the model copy. The new control carries a tooltip. Style limits hold: no Lombok, no lambdas in our types, no new package, and no new public type.

**semantic-key:** PK-7964

## What this sketch is

The plan is cut from one command: Duplicate group copies the armed group after itself with empty cells. One clear group lands under the armed group. Shift-Ctrl-G does the same.

**semantic-key:** PK-6507

## What this sketch is not

This is not a copy of the prompt text or the reply text. It is not a new package, public type, or vault column. It is not a live call.

**semantic-key:** PK-6508

## The slice

The armed card names the group. Duplicate group, on the card menu or on Shift-Ctrl-G, inserts a replica after that group. Members keep their order, their targets, their states, and their burner marks. Prompts and replies are empty. The cards start open. A new group id is minted.

**semantic-key:** PK-6509

## What stays

Group, Join, Ungroup, Delete, and the merge sink stay. The armed card stays put. Import stays on the prompt side of the menu. A card with no group opens the yellow slide with the words `no group selected`.

**Truth.** The replica keeps order, targets, states, and burner marks, and it keeps none of the prompt or reply text.

**Reason.** A clear group under the armed group lets the same shape run again without dragging the words already on the source cards.

**Avoidances.** No copied prompt or reply, no new package or public type, no vault column, no live call, and no move of the armed card.

**semantic-key:** PK-6510

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The same approval's follow-on names the View window. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the readable cell main bar, on the View window, and on the existing tip path. The new control carries a tooltip. Style limits hold: no Lombok, no lambdas in our types, no new package, and no new public type.

**semantic-key:** PK-6511

## What this sketch is

The plan is cut from one icon in two places. A copy icon on a readable cell copies that turn's whole reply to the clipboard. The View window has the same icon. A copy icon puts the whole reply on the clipboard. The View window carries the same icon.

**semantic-key:** PK-6512

## What this sketch is not

This is not a new package, public type, vault column, or G-key. It is not a change to the prompt, the thought strip, or the find field. It is not a live call. It is not a pool turn.

**semantic-key:** PK-6513

## The slice

Every readable cell that has an output window gains a clipboard copy control on its main button bar. The control is an icon of two overlapping rounded rectangles, the contemporary copy mark. A click copies that turn's whole reply onto the operating-system clipboard. The View window gains the same icon on its south bar and copies the reply that window is showing.

**semantic-key:** PK-6514

## What stays

The prompt stays on the card. The thought strip stays on the footer. The find field and its highlights stay. The group bar stays the target, Group, Join, and Ungroup. An unreadable load-error card stays the question-mark row. Footer BB1 and BB2, the inspector, the mood dialog, and the model dialog stay as they are. `normalizeNewlines` stays the paste-in path. Earlier finessing rows stay locked. The pool hold stays as landed.

**semantic-key:** PK-6515

## Where the copy reads

An open card copies the assistant area, so a pool stream can be copied while the chunks are painting and before the reply is stored. A collapsed card copies the stored reply. The one-line brief is the eye's line during a folded stream. The copy leaves that brief alone until the reply is stored. JOIN lines stay in the text, because they are in the stored reply and in the View snapshot. A selection is left where it is. The copy is the whole document.

**Truth.** The same copy mark writes the whole reply from a readable cell bar and from the View window's south bar.

**Reason.** The reply the operator is reading, including a stream that is still painting on an open card, has to reach the clipboard without taking the prompt, the thought strip, or the find field with it.

**Avoidances.** No new public type, no change to the prompt, the thought strip, or the find highlights, and no read of the one-line brief while a folded stream is still arriving.

**semantic-key:** PK-6516

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the wild-card mood dialog, on the error slide, and on `AetherisNotebookPanel`. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7982

## What this sketch is

The plan is cut from the mood layer that stays online until it misses. The mood layer stays online, and a mood miss leaves the turn. It is the same Mood dialog, the same pin commit, and the same second system line. A miss uses the error slide and takes the layer offline for the session.

A mood miss leaves the turn.

**semantic-key:** PK-6517

## The slice

Pin OK opens Mood. Send reaches the shaper. Approve stores one system line for the next Run. A miss paints the error slide, clears that line, and stops the layer until the next app start. The turn still runs.

**semantic-key:** PK-6518

## What stays

`Q-S2-1`, `Q-S2-2`, and `Q-S2-3` stay. Empty `mood-shape` stays the Ollama model in use. The host line stays the host line. Cancel still keeps the filter commit. Air-gap stays true. `num-ctx` stays. Earlier finessing rows stay locked. No new button brings the layer back. A restart does.

**semantic-key:** PK-6519

## The weighing

The weighing lives in this section.

States are written, then Mood opens. A miss does not roll the states back. Send, Approve, and Cancel stay. One send is in flight. A miss paints the error slide. The canvas stays usable. There is no blocking dialog. A mood id never marks a cell and never clears a chain. A mood line that does not fit is dropped. The turn is sent without it. A hung shaper still holds one slot until the provider returns. The other slots stay for turns. Approve during Send keeps the transcript so far. A late reply is dropped.

**semantic-key:** PK-8009

## The landing

The landing is the plan's edit. The edits stay on `AetherisNotebookPanel`.

**semantic-key:** PK-6520

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** A mood miss clears the line, paints the error slide, and leaves the pinned turn runnable until the next app start brings the layer back.

**Reason.** The pin has already written the states, so a shaper failure must not roll them back and must not hold the canvas behind a blocking dialog.

**Avoidances.** No new button to restore the layer, no cell mark from a mood id, no chain clear, and no second in-flight Send.

**semantic-key:** PK-7953

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the thinking strip and on the footer of `AetherisNotebookPanel`. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7983

## What this sketch is

The plan is cut from the pane that stays. The thinking pane stays until Close, and it rises from the top of the footer. It is the same three-line yellow strip. Close is the dismiss. The footer buttons stay at full size under the pane.

**semantic-key:** PK-6521

## The slice

Thinking still opens the pane. A pause leaves the pane and the text. Close slides it away. The footer stays under the pane at its own height, so Run, Stop, New turn, and the rest stay in reach.

**semantic-key:** PK-6522

## What stays

Earlier finessing rows stay locked. The earlier strip still names the three lines, the yellow text, and the `v` fold. The text still drops when the session ends. It is still not a vault column. The error slide stays the notice at the top of the pane. Air-gap stays. `num-ctx` stays.

**semantic-key:** PK-6523

## The weighing

The weighing lives in this section.

The same area, the same yellow ink, and the same `v` fold stay. Close is added. The button row stays at its own height on the bottom of the stack. A finish leaves the text. A second finish leaves the text. Another cell replaces the text. The same cell appends. The text drops with the session. The vault stays empty of it. The error slide stays the top notice. This row does not retarget it.

**semantic-key:** PK-8010

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** Close is the only dismiss, and the footer button row keeps its own height under the pane.

**Reason.** A pause still has text the operator may want to read, and the buttons under that pane have to stay reachable while the pane is up.

**Avoidances.** No vault write of the text, no new public method, and no retarget of the error slide.

**semantic-key:** PK-7954

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the thinking strip, on the new-turn model copy, and on the pane above the footer. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7984

## What this sketch is

The plan is cut from the checkbox and the log. The thinking checkbox is carried onto the next turn, and the thinking log stays. It is the same pane above the footer. A drag on the north edge sets the height. Each model is a tagged block. Clear wipes the saved log.

**semantic-key:** PK-6524

## The slice

Model Config thinking stays on when the next turn copies the prior model. The pane remembers its height and its text. A block names the model that thought it. Clear starts the log over.

**semantic-key:** PK-6525

## What stays

Earlier finessing rows stay locked. The earlier strip still names the strip. The new-turn path still copies the model onto the new turn. Close still hides the pane and the pane still rises from the top of the footer. Close keeps the log. The text is still not a vault column. The error slide stays the notice at the top of the pane. Air-gap stays. `num-ctx` stays.

**semantic-key:** PK-6526

## The weighing

The weighing lives in this section.

The session holder is written to `~/.casserole/aetheris-think.yaml`. It is loaded when the panel opens. `newTurn` still copies the holder, including `poolOn`, and writes the new cell. Close hides the pane and keeps the log. The footer stays at full height. Thinking text appends. A new block writes a separator and the model tag. The log file is `~/.casserole/aetheris-thinking.txt`. Clear empties the pane and the file. A drag on the north edge sets the height. The height is stored in the yaml.

**semantic-key:** PK-8011

## Proof

From the product root, a quiet compile that does not run tests must exit clean. The window stays closed. The live call is not made. A pool turn is not sent. A live SSH connect is not made.

**Truth.** The next turn copies the thinking holder, and the pane keeps a tagged log whose height was dragged.

**Reason.** The model copy onto a new turn is incomplete if the thinking checkbox stays behind, and the log has to survive Close.

**Avoidances.** No vault column, no new public method, and no Clear that leaves the old file in place.

**semantic-key:** PK-7955

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. Dish 1 is this build. The private JediTerm waits. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan. The private JediTerm waits.

The sketch depends on the footer bars, on the button tips, on the sapphire selection, on the error slide, and on the earlier mood dialog. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-6527

## What this sketch is

The plan is cut from dish 1. Dish 1 is the optional mood string, the BB2 switch, and the Swing dialog. A saved string sits at the head of the working set while the switch is on. Off keeps the string and sends no mood line. A changed string replaces the head.

Dish 1 is the mood string. The private JediTerm waits.

**semantic-key:** PK-6528

## The slice

Mood is optional. A defined string stays enforced for the session while the switch is on. The Mood button opens a Swing dialog against the selected card's model. Approve stores one mood string. The walker places that string once at the head, after the host line, while the switch is on.

**semantic-key:** PK-6529

## What stays

Earlier finessing rows stay locked. The earlier mood reading stays. Pin OK still writes the filter. The host line stays the host line. The error slide stays the notice. Air-gap stays. `num-ctx` stays. `notebook.mood-shape` stays in the yaml. Channel A stays the lane. The private JediTerm, the local connector, and Enter on the glass wait until dish 2 is named.

**semantic-key:** PK-6530

## Sketch

BB2 gains a separator after To terminal, then the mood switch, then the button labeled Mood. The switch is two positions. A click with no saved string leaves it off. Off removes the mood line from the working set and keeps the string. On inserts that string once at the head and does not call the model.

The dialog keeps a read-only transcript, a line field, Send, an editable mood-string field, Approve, and Cancel. Send ignores an empty line. One send is in flight. The shaping system line asks for one mood string. The last assistant reply fills the field. Approve commits the field and turns the switch on. Cancel keeps the saved string and the switch. A miss closes the dialog, paints the slide, and keeps the saved string and the switch. The layer does not latch offline.

The speaker is the selected card. An empty target is ollama. A Grok card uses that card's model id. A pool card uses pool. Think stays off. The call is ephemeral. The working set is the shaping chat. A cloud target that card is not allowed to call leaves the dialog closed. The slide says `keyword only.`

The walker puts the host line first. While the switch is on, the next system message is the saved string. A body that does not fit drops that line for the send, paints `keyword only. too-large`, and leaves the string and the switch on. The inspector shows `(none)`, `off` with a preview, or the live preview. The string is session memory. It is not a vault column. A restart starts with no string and the switch off.

**Truth.** While the switch is on, one saved string is the system line after the host line, and off keeps the string without sending it.

**Reason.** Mood has to be optional and session-scoped, drafted against the selected card, without a model call on the switch itself and without waiting on the private terminal.

**Avoidances.** No vault column, no offline latch, no dish 2, and no call when the card's cloud target is not allowed.

**semantic-key:** PK-6531

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The mood line was exercised. The pool model returned the encoded format after it was asked, and the line lit. The sketch depends on the error slide, on the thinking spinner, on the mood glass, and on `ThinkingClock`. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7985

## What this sketch is

The plan is cut from the retry. A missing mood line is asked for again. The third miss paints the yellow slide. The dialog footer shows the spinner. Three replies with no mood line, then stop. A mood line fills the field and clears the count. The clock counts from the send.

A missing mood line retries. Three tries paint the yellow slide. The dialog footer carries the thinking spinner.

**semantic-key:** PK-6532

## The slice

The mood dialog already plucks a mood line into the field. When a reply has no mood line, the dialog asks for that line in the expected form and sends again. Three such replies paint the yellow slide and leave the dialog open. While a reply is in flight, the dialog footer shows the notebook thinking spinner and the elapsed clock.

**semantic-key:** PK-6533

## What stays

Earlier finessing rows stay locked. The pluck stays the last mood value. Marks, a space before the colon, the words mood string, and a fenced mood block still count. Approve still saves the field. Cancel still keeps the saved string and the switch. A transport miss still closes the dialog. Think stays off. The shaping call still copies the card's burner bit. Air-gap stays. Channel A stays the lane. The canvas spinner still follows the canvas run.

**Truth.** A reply with no mood line is sent again, and the third such reply paints the yellow slide without closing the dialog.

**Reason.** The pluck only fills the field when the reply already carries a mood line, so a miss has to ask for that line and then stop.

**Avoidances.** No new public method, no change to which marks count as a mood line, and no clock taken from the canvas run.

**semantic-key:** PK-6534

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. Two finesses sit in one row. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the thinking spinner, on the mood switch, and on the dialog footer spinner. The new color tip carries a tooltip. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-6535

## What this sketch is

The plan is cut from one row. The glyph box stays fixed. The knob carries the on and off colors.

The thinking text stays put. The mood knob is green when on and red when off.

**semantic-key:** PK-6536

## The slice

The thinking spinner keeps the word and the clock in one place while the glyph turns. The mood switch round button is green when the switch is on and red when it is off.

**semantic-key:** PK-6537

## What stays

Earlier finessing rows stay locked. The spinner still counts from the send on the dialog and from the canvas run on the notebook. Left stays off. Right stays on. The switch still does not call the model.

**Truth.** The word and the clock stay put while the glyph turns, and the round button is green on and red off.

**Reason.** The two finesses share one row because both are paint: a stable spinner label, and a knob whose color says the position.

**Avoidances.** No new public method, no model call from the switch, and no change to which clock a spinner uses.

**semantic-key:** PK-6538

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. A mood was set on a fresh canvas. The model used it after it was coaxed. A shape line follows the mood string on the send. The line is not a cell. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the mood header and on the still text and knob colors. The switch tip carries a tooltip. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-6539

## What this sketch is

The plan is cut from one row. A shape line follows the saved mood string when the transcript is sent.

A shape line follows the mood. The line tells the model to use that mood as the shape of its responses.

**semantic-key:** PK-6540

## The slice

While the mood switch is on, a canvas send carries the saved mood string and then one more system line. That line tells the model to activate the mood above and use it as the shape of its responses. The line is not written into a cell.

**semantic-key:** PK-6541

## What stays

Earlier finessing rows stay locked. The mood string stays the system message after the host line. The switch still does not call the model. The mood glass stays the shaping chat. Left stays off. Right stays on. The knob colors stay.

**Truth.** A canvas send with the switch on carries the saved mood and then one shape line, and that line is not a cell.

**Reason.** The model used a set mood only after it was coaxed, so the send itself has to name the mood as the shape of the responses.

**Avoidances.** No cell for the shape line, no model call from the switch, and no change to the knob colors.

**semantic-key:** PK-6542

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The thinking pane was closed, the app was closed, and the pane was open again on the next start. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on Close and on the think yaml and the saved log. The Close tip carries a tooltip. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-6543

## What this sketch is

The plan is cut from one row. Close writes a closed mark. The next open honors it.

A closed thinking pane stays closed when the app opens again.

**semantic-key:** PK-6544

## The slice

Close hides the thinking pane and keeps the log. That closed choice is written beside the dragged height. The next open loads the log and leaves the pane shut.

**semantic-key:** PK-6545

## What stays

Earlier finessing rows stay locked. The log file stays. Clear still wipes the pane and the saved log. A later thought still opens the pane. The fold stays a session choice. The yaml version stays 1. A version other than 1 stays unread and is not overwritten.

**Truth.** The closed choice is stored beside the dragged height, and the next open loads the log without showing the pane.

**Reason.** A close that only hides the pane is forgotten when the app starts again.

**Avoidances.** No new public method, no wipe of the log on Close, and no overwrite of a yaml version other than 1.

**semantic-key:** PK-6546

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The mood dialog Approve label is truncated, with an ellipsis. The sketch depends on the mood dialog and on the short button fit. The button tip carries a tooltip. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7986

## What this sketch is

The plan is cut from one row. Approve is wide enough for its label.

Approve shows its full label.

**semantic-key:** PK-6547

## The slice

The mood dialog Approve button keeps the short height and gains width so the word Approve is fully shown.

**semantic-key:** PK-6548

## What stays

Earlier finessing rows stay locked. The label stays `Approve`. Cancel stays the short fit. The footer buttons stay the short fit. The click still saves the field.

**Truth.** Approve keeps the short height and grows wide enough that the word is fully shown.

**Reason.** The short fit was replacing the end of Approve with an ellipsis.

**Avoidances.** No new public method, no change to the label, and no widening of Cancel or the footer buttons.

**semantic-key:** PK-6549

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The mood was ephemeral to the app session and is now to be kept. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the mood switch and on the shape line. The tips carry tooltips. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-6550

## What this sketch is

The plan is cut from one row. The mood and the switch survive the app.

The mood stays with the app. The switch flips. The line above the inspector shows the string while the switch is on.

**semantic-key:** PK-6551

## The slice

The saved mood string and the switch position are written under the home directory. The next open restores both. The switch is grey until a string exists. While the switch is on, the string sits above the inspector. The Mood button sets the mood. On at exit is on at the next start. Off at exit is off. Approve turns the switch on if it was off. The switch stays grey until a mood is defined.

**semantic-key:** PK-6552

## What stays

Earlier finessing rows stay locked. The string is not a vault column and not a cell. Export does not carry it. Clear turns leaves it. The Mood button stays enabled. The shape line still follows the string on a send. Left stays off. Right stays on.

**Truth.** The string and the switch position are restored on the next open, and the line above the inspector shows the string only while the switch is on.

**Reason.** A mood that dies with the session cannot be the mood the operator already approved.

**Avoidances.** No vault column, no export of the string, and no enabled flip before a string exists.

**semantic-key:** PK-6553

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

When the mood string is set in the footer of the mood dialog, that text operates more like the thinking pane. The sketch depends on the mood string and on the mood file. The tips carry tooltips. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7987

## What this sketch is

The plan is cut from one row. The mood string rises in the dialog.

The mood string rises from the top of the dialog footer. The height follows the wrap.

**semantic-key:** PK-6554

## The slice

The resting footer of the mood dialog stays the spinner, Approve, and Cancel. When a reply sets the mood string, a pane rises from the top of that footer and shows the string. The height is the wrapped line count. One line is one line tall. Two lines are two lines tall. The string stays easy to inspect and approve, and the footer stays clean.

**semantic-key:** PK-6555

## What stays

Earlier finessing rows stay locked. The string is not a vault column and not a cell. Approve of an empty field writes nothing. The pane is editable. The canvas thinking pane stays the thinking pane. The shape line still follows the string on a send.

**Truth.** A set mood string pops up from the top of the dialog footer at the height of its wrap, and the resting footer stays the spinner, Approve, and Cancel.

**Reason.** The string has to be inspected and approved without leaving a cluttered footer.

**Avoidances.** No vault column, no empty Approve write, and no reuse of the canvas thinking pane for this rise.

**semantic-key:** PK-6556

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the mood file and on the mood dialog. The tips carry tooltips. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7988

## What this sketch is

The plan is cut from one row. The mood dialog keeps its size.

The mood dialog keeps the size it had when it closed.

**semantic-key:** PK-6557

## The slice

Approve and Cancel write the mood dialog width and height. The next open uses that pair. Until a size is kept, the dialog opens at 760 by 520.

**semantic-key:** PK-6558

## What stays

Earlier finessing rows stay locked. The string is not a vault column and not a cell. The place on screen is not kept. A version other than 1 is left unread and is not overwritten.

**Truth.** The next open uses the width and height written by Approve or Cancel, and until then the dialog is 760 by 520.

**Reason.** The size the dialog was left at is the size the next open should use.

**Avoidances.** No stored screen position, no vault column, and no overwrite of a file version other than 1.

**semantic-key:** PK-6559

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The mood model was asked for enriched detail. It said the detail and left the mood string thin. The control header has to put more semantic tokens on the mood line when enrichment is invited, and to say in the reply what it determined. The sketch depends on the mood glass and on the shape line. The tip carries a tooltip. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7989

## What this sketch is

The plan is cut from one row. An invited enrichment fills the mood line.

An invited enrichment fills the mood line with more tokens, and the reply says what was determined.

**semantic-key:** PK-6560

## The slice

The mood glass system line tells the model to put more tokens on the mood line when the user asks for enriched detail, and to say that detail in the reply. The app still keeps the mood line.

**semantic-key:** PK-6561

## What stays

Earlier finessing rows stay locked. The pluck stays. The canvas shape line stays. The mood file stays the mood file.

**Truth.** An invited enrichment fills the mood line with more tokens, and the reply says what was determined.

**Reason.** Detail that lives only in the talk never becomes the string the app stores.

**Avoidances.** No new public method, no change to the pluck, and no change to the canvas shape line.

**semantic-key:** PK-6562

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

A request typed on the mood glass drew the yellow slide `keyword only. cell-busy`. The sketch depends on the mood glass and on the format ask. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7990

## What this sketch is

The plan is cut from one row. A mood turn keeps its own wire id.

**semantic-key:** PK-6563

## The slice

Each mood send uses a new wire id. The server still refuses a second frame for a canvas cell that is running. The mood glass stays open for the next turn.

**semantic-key:** PK-6564

## What stays

Earlier finessing rows stay locked. The pluck stays. The format ask stays. The cell-busy rule stays for a canvas cell.

**Truth.** A mood turn keeps its own wire id, and a running canvas cell still refuses a second frame.

**Reason.** The yellow slide `keyword only. cell-busy` was answering a mood line that shared a canvas cell's wire id.

**Avoidances.** No new public method, and no change to the cell-busy rule for a canvas cell.

**semantic-key:** PK-6565

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the mood file, on the rising field, on the enrichment header, and on the activate line. The Clear tip carries a tooltip. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7991

## What this sketch is

The plan is cut from one row. A saved mood opens in the field. The mood send carries those tokens in the header. Clear mood starts empty.

**semantic-key:** PK-6566

## The slice

When a mood is already saved, the dialog opens with that string in the field. The operator can ask the model to enhance it. A mood send places that string and the activate line in the header, the way a conversation send carries them. Clear mood drops the string and the chat.

**semantic-key:** PK-6567

## What stays

Earlier finessing rows stay locked. The pluck stays. The shape line stays. A canvas send still carries the pair only while the switch is on. The wire id stays. The kept size stays.

**Truth.** A saved mood opens in the field, the mood send carries those tokens in the header, and Clear mood starts empty.

**Reason.** Enhancement cannot see a string that the dialog left blank, and a scratch start cannot leave the old chat in place.

**Avoidances.** No new public method, and no canvas send of the pair while the switch is off.

**semantic-key:** PK-6568

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the mood session: the clear that empties the string and the chat, the field seed, the header, and the kept size. The landing commits when the turn ends. The Clear mood tip and the Mood button tip name the ask, and each tip says what the control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7992

## What this sketch is

The plan is cut from one row. Clear mood asks before it drops the string. Yes clears. No leaves the mood.

**semantic-key:** PK-6569

## The slice

Clear mood opens an ask over the mood dialog. The words are Clear the Mood Setting? Yes is green. No is red. Yes runs the clear. No leaves the mood. The mood dialog stays open.

**semantic-key:** PK-6570

## What stays

Earlier finessing rows stay locked. The clear itself stays. The field seed stays. The header stays. The kept size stays. The switch colors stay the same green and the same red.

**Truth.** Clear mood asks before it drops the string, Yes clears, and No leaves the mood.

**Reason.** The clear already drops the string and the chat, and that drop needs an ask before it runs.

**Avoidances.** No new public method, and no close of the mood dialog when the ask closes.

**semantic-key:** PK-6571

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the clear ask. The landing commits when the turn ends. The tips stay, and each tip says what the control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7993

## What this sketch is

The plan is cut from one row. Yes and No show their words.

**semantic-key:** PK-6572

## The slice

The clear ask keeps its words. Yes and No are wide enough to show those words. Yes stays green. No stays red.

**semantic-key:** PK-6573

## What stays

Earlier finessing rows stay locked. The ask stays. The clear stays on Yes. The tips stay.

**Truth.** Yes and No show their words, and the buttons stay green and red.

**Reason.** The clear ask already has the words, and those words have to fit on the buttons.

**Avoidances.** No new public method, and no move of the clear off Yes.

**semantic-key:** PK-6574

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the pin controls already on the inspector header. The landing commits when the turn ends. The tips stay on those controls, and each tip says what the control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, and no new public method.

**semantic-key:** PK-7994

## What this sketch is

The plan is cut from one row. The pin bar sits on the canvas.

**semantic-key:** PK-6575

## The slice

The pin box, the word field, and OK leave the inspector header. They sit on a thin bar at the top of the canvas. The tips stay on those controls. OK still writes the states. The click still does not open Mood. The edge paint stays in `paintChrome`.

**semantic-key:** PK-6576

## What stays

Earlier finessing rows stay locked. The pin words stay. The states stay. The OK tip stays the sentence already on the button. The inspector keeps soft wrap and the two counters. The Rust pin row stays where the slice seated it. This row writes no crate.

**Truth.** The pin bar sits on the canvas, and the pin box, the word field, and OK leave the inspector header.

**Reason.** The pin controls were already on the inspector header, and the bar is their seat at the top of the canvas.

**Avoidances.** No new public method, no open of Mood from the click, and no crate from this row.

**semantic-key:** PK-6577

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the pin bar on the canvas, on the shell fold, and on the C tuck on the thinking grip. The landing commits when the turn ends. The tips say what each control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, no new public method, no new G-key, no new vault column, no new channel, and no new wire type. The shell pair from the fold stays.

**semantic-key:** PK-7995

## What this sketch is

The plan is cut from one row. The folded pin bar leaves the canvas.

**semantic-key:** PK-6578

## The slice

v folds the whole pin bar off the canvas. The cards use the height they had before the bar. P sits on the east of the canvas tab header while the bar is folded. P shows the bar. Folding leaves the pin box as it is.

**semantic-key:** PK-6579

## What stays

Earlier finessing rows stay locked. The pin words stay until the user changes them. Only the pin box turns the filter off. OK still writes the states. The click still does not open Mood. The OK tip stays the sentence already on the button. The edge paint stays in `paintChrome`. The inspector keeps soft wrap and the two counters. The shell key stays `ui.aetheris.pin.bar.open`. A missing key stays folded. The Rust pin row stays where the slice seated it. This row writes no crate.

**Truth.** The folded pin bar leaves the canvas, and P on the tab header shows it again.

**Reason.** The pin bar is already on the canvas, the shell fold already saves that choice, and C on the thinking grip is the tuck this P follows.

**Avoidances.** No new public method, no filter change from the fold, and no crate from this row.

**semantic-key:** PK-6580

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the pin mark on the canvas tab header, on the thinking clear mark on the grip, and on the sapphire already used for the armed card. The landing commits when the turn ends. The tips say what each control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, no new public method, no new G-key, no new vault column, no new channel, and no new wire type. The seat of P stays. The seat of C stays.

**semantic-key:** PK-7996

## What this sketch is

The plan is cut from one row. P and C wear sapphire.

**semantic-key:** PK-6581

## The slice

P and C wear the armed-card sapphire. A one-pixel white bevel surrounds each face. The letter is white. The seat of P stays on the east of the canvas tab header. C stays on the east of the thinking grip.

**semantic-key:** PK-6582

## What stays

Earlier finessing rows stay locked. The seat of P stays. That seat is the pattern to keep. P shows while the pin bar is folded and adds no row. C still clears the thinking log. The other short buttons keep the theme bevel. The pin box stays the only off switch. The words stay. The shell key stays. The edge paint stays in `paintChrome`. The OK tip stays. This row writes no crate.

**Truth.** P and C wear sapphire, and both seats stay where they are.

**Reason.** The pin mark and the thinking clear mark are already seated, and the armed-card sapphire is the fill those faces take.

**Avoidances.** No new public method, no move of either seat, and no crate from this row.

**semantic-key:** PK-6583

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on `paintChrome` on the card, on the pin box, and on the armed card and the merge arm. The landing commits when the turn ends. The tips say what each control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, no new public method, no new G-key, no new vault column, no new channel, and no new wire type. This row writes no new Java. The build is the paint already in the tree.

**semantic-key:** PK-7997

## What this sketch is

The plan is cut from one row. The pin edge is the paint already on the cards.

**semantic-key:** PK-6584

## The slice

While the pin box is on, a readable match wears green and a readable miss wears dim. The armed card stays sapphire. A merge arm lights every card in that group.

**semantic-key:** PK-6585

## What stays

Earlier finessing rows stay locked. The paint stays in `paintChrome`. A match is `#3cb371`. A miss is `#222222`. The armed card and a merge-lit group are `#2e86ff`. The inner prompt and reply borders stay their own green line. The pin box stays the only off switch. The words stay. The shell key stays. The OK tip stays. The seat of P stays. The face of P and C stays. This row writes no crate.

**Truth.** The pin edge is the paint already on the cards, a readable match is green, a readable miss is dim, and the armed card stays sapphire.

**Reason.** `paintChrome` already paints that edge from the pin box, the armed card, and the merge arm.

**Avoidances.** No new Java, no new public method, and no crate from this row.

**semantic-key:** PK-6586

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the pin mark on the canvas tab header, on the thinking clear mark on the grip, on the green already used for a readable match, and on the sapphire already used for the armed card. The landing commits when the turn ends. The tips say what each control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, no new public method, no new G-key, no new vault column, no new channel, and no new wire type. The seat of P stays. The seat of C stays. C stays sapphire.

**semantic-key:** PK-7998

## What this sketch is

The plan is cut from one row. P is green while the pin is on and sapphire while the pin is off.

**semantic-key:** PK-6587

## The slice

P is green while the pin is on and sapphire while the pin is off. Green is `#3cb371`. Sapphire is `#2e86ff`. The letter stays white. The one-pixel white bevel stays. C stays sapphire. Java keeps P on the east of the canvas tab header. The Rust face follows in this same course.

**semantic-key:** PK-6588

## What stays

Earlier finessing rows stay locked. The seat of P stays. P shows while the pin bar is folded and adds no row. C still clears the thinking log. The other short buttons keep the theme bevel. The pin box stays the only switch. The words stay. The shell key stays. The edge paint stays in `paintChrome`. The six-pixel inset stays off. No side move on this plate. This land leaves the working set as it stands.

**Truth.** P is green while the pin is on and sapphire while the pin is off, and C stays sapphire.

**Reason.** The readable-match green and the armed-card sapphire are already on the canvas, and the pin mark is the face that has to switch between them.

**Avoidances.** No new public method, no move of either seat, and no change of C off sapphire.

**semantic-key:** PK-6589

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the footer view-row count, on the collapse control on the cell, and on the reply text pane and its border. The landing commits when the turn ends. The tips say what each control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, no new public method, no new G-key, no new vault column, no new channel, and no new wire type. The choice stays for this window. It is not saved.

**semantic-key:** PK-7999

## What this sketch is

The plan is cut from one row. Expanded View sits on each readable cell. Java holds the core.

**semantic-key:** PK-6590

## The slice

Each readable cell header carries a checkbox labeled Expanded View. It sits after the collapse control and before the burner checkbox. An unreadable card has no box. Checked, the reply text pane grows to the wrapped buffer and shows all of the text inside its border. A text change and a window resize refit a checked reply. Unchecked, the reply stays at the saved view-row count, the same height as the prompt, and the text scrolls inside. The footer count sets that height. The prompt keeps that count. A folded card stays a brief line. The checkbox still shows and keeps the choice. A fresh canvas, a vault open, and a disconnect reload start unchecked. Footer Clear leaves one empty card unchecked. A new turn copies the prior cell. An empty deck stays unchecked. A duplicated group starts checked. Import, Pull, and a merge cell start unchecked. The choice stays for this window. It is not saved. It is not a vault column and not an export field. Java holds the core. The seated crate already paints Expanded View. This row writes no crate.

**semantic-key:** PK-6591

## What stays

Earlier finessing rows stay locked. The collapse control stays the saved fold. The footer count stays the shell key. The prompt keeps that count. Labels stay New and Clear. No side move on this plate. This land leaves the working set as it stands.

**Truth.** Each readable cell carries Expanded View, a checked reply shows all of the text inside the border, a fresh canvas and Clear start unchecked, and a new turn keeps the prior cell's choice.

**Reason.** The footer view-row count, the collapse control, and the reply text pane already give the height and the fold this checkbox has to sit beside.

**Avoidances.** No save of the choice, no new public method, and no crate from this row.

**semantic-key:** PK-6592

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the cell outer frame, on the short model name, and on the focused cell. The landing commits when the turn ends. The tips say what each control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, no new public method, no new G-key, no new vault column, no new channel, and no new wire type. The line is not saved.

**semantic-key:** PK-8000

## What this sketch is

The plan is cut from one row. The stamp and the model sit on the Java cell border. Java holds the core.

**semantic-key:** PK-6593

## The slice

The focused cell wears the local stamp and the short model on the top stroke of its outer frame. The line is centered on that stroke and seated on the right. A blank model keeps the stamp. The clock ticks once a second. A quiet cell keeps a plain frame. The line stays on the focused cell. The model line leaves the header. The paint draws the line on top of the border. The cell keeps its width. The buttons, the prompt, and the reply keep their size. There is no north pad. Java holds the core. The seated crate already paints the embedded line and the first lead. This row writes no crate. The Rust lead stays on the crate.

**semantic-key:** PK-6594

## What stays

Earlier finessing rows stay locked. The cell border stays the line it already paints. The column inset stays. The input pane border stays a plain stroke. Expanded View stays. The fold stays. The sapphire line stays. The pin stays. No side move on this plate. This land leaves the working set as it stands.

**Truth.** The model name and the timestamp ride the Java cell border, the paint crosses that border, and the paint does not take the north pad.

**Reason.** The focused cell already has an outer frame, and the short model name belongs on that frame the way the seated crate already paints the embedded line.

**Avoidances.** No save of the line, no north pad, no new public method, and no crate from this row.

**semantic-key:** PK-6595

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the cell outer frame and on the caption already on that frame. The landing commits when the turn ends. The tips say what each control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, no new public method, no new G-key, no new vault column, no new channel, and no new wire type. The line is not saved.

**semantic-key:** PK-8001

## What this sketch is

The plan is cut from one row. The rule is a stroke. Java holds the core.

**semantic-key:** PK-6596

## The slice

The focused cell still wears the local stamp and the short model on the top stroke of its outer frame. Each flank is a one-pixel stroke. The words carry no box rule. The line stays clean and still reads the way the Rust cell reads. Java holds the core. The seated crate still paints the character rule. This row writes no crate. The crate move for this delta waits until that move is named.

**semantic-key:** PK-6597

## What stays

Earlier finessing rows stay locked. The cell border stays the line it already paints. The column inset stays. The input pane border stays a plain stroke. The clock stays. The hover stays. Expanded View stays. The fold stays. The sapphire line stays. The pin stays. No side move on this plate. This land leaves the working set as it stands.

**Truth.** The rule beside the stamp is a stroke, the paint draws that rule, and the text carries no box rule.

**Reason.** The caption is already on the outer frame, and a character rule in that text is what the stroke replaces.

**Avoidances.** No save of the line, no new public method, and no crate from this row.

**semantic-key:** PK-6598

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the cell outer frame, on the caption already on that frame, and on the selection sapphire already on that frame. The landing commits when the turn ends. The tips say what each control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, no new public method, no new G-key, no new vault column, no new channel, and no new wire type. The line is not saved.

**semantic-key:** PK-8002

## What this sketch is

The plan is cut from one row. The stroke wears the selection sapphire. Java holds the core.

**semantic-key:** PK-6599

## The slice

The focused cell still wears the local stamp and the short model on the top stroke of its outer frame. Each flank is a one-pixel stroke. The stroke wears the selection sapphire. The words stay the caption ink. The text carries no box rule. Java holds the core. The seated crate still paints the character rule. This row writes no crate. The crate move for this delta waits until that move is named.

**semantic-key:** PK-6600

## What stays

Earlier finessing rows stay locked. The cell border stays the line it already paints. The column inset stays. The input pane border stays a plain stroke. The clock stays. The hover stays. Expanded View stays. The fold stays. The pin stays. The words stay the caption ink. No side move on this plate. This land leaves the working set as it stands.

**Truth.** The stroke beside the stamp wears the selection sapphire, and the words stay the caption ink.

**Reason.** The selection sapphire is already on the outer frame, and the flank strokes are the piece that takes it.

**Avoidances.** No save of the line, no box rule in the text, and no crate from this row.

**semantic-key:** PK-6601

## Standing

This sketch is serial 001, and the locked finessing row binds it. Where this page is thinner than that row, the locked row wins. Approval of the full cycle names the build and stamps the formal plan from this page. The dependency seat and the landing are filed on the row. No extra cut is opened. The sketch stays locked. The next page is the stamped plan.

The sketch depends on the canvas scroller, on the prompt and the reply, and on the stamp already on the focused cell. The landing commits when the turn ends. The tips say what each control is and what the click does. Style limits hold: no Lombok, no lambdas in our types, no new package, no new public type, no new public method, no new G-key, no new vault column, no new channel, and no new wire type. The line is not saved.

**semantic-key:** PK-8003

## What this sketch is

The plan is cut from one row. Scroll focus returns to the canvas. Hover shows the stamp. Java holds the core.

**semantic-key:** PK-6602

## The slice

A click on the prompt or the reply puts scroll focus on that text. A click outside those panes returns scroll focus to the canvas. The armed cell stays armed. Hover on a cell shows the stamp and the short model. Java holds the core. The seated crate still paints the character rule. This row writes no crate. The crate move for this delta waits until that move is named.

**semantic-key:** PK-6603

## What stays

Earlier finessing rows stay locked. The sapphire stroke stays. The words stay the caption ink. A quiet cell still paints no line. The clock stays. The focused hover stays the line is the model this turn will call. The execution target keeps the caret. Expanded View stays. The fold stays. The pin stays. No side move on this plate. This land leaves the working set as it stands.

**Truth.** A click on a text pane puts scroll focus on that pane, a click outside that pane returns scroll focus to the canvas, and hover shows the model as it is stamped when a cell is selected.

**Reason.** The canvas scroller, the prompt, the reply, and the stamp already on the focused cell are the seats this click and this hover use.

**Avoidances.** No save of the line, no change of the armed cell, and no crate from this row.

**semantic-key:** PK-6604

# The P mark stays above the divider

The P button stays painted on top of the inspector divider.

**semantic-key:** PK-6605

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke for the short-label plate or for this plate. Do not file a feature smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted. There is no crate commit on this plate.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

From the Casserole tree, compile quietly and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Rust does not move. Tests are not run on this plate.

One commit of the named Java file records that the change is in the tree.

**semantic-key:** PK-8062

## Goal

The P button stays painted on top of the inspector divider.

**semantic-key:** PK-6606

## What stays

The letter stays P. The divider stays 11 pixels. The arrows stay. The fold of the pin bar stays. The token window stays. File, Tools, Token Calc, and Help stay. No yaml edit. Rust stays.

**semantic-key:** PK-6607

## Edits

The P mark overlaps the divider. The bar is opaque. A repaint of the arrows paints that bar across the mark, and the mark does not paint again. `InspectorFold` returns false from `isOptimizedDrawingEnabled`. A child repaint that meets the mark starts from the fold, and the mark paints after the bar. `placePin` keeps the mark at z-order 0.

The seat is `InspectorFold` in `AetherisNotebookPanel`.

**semantic-key:** PK-6608

## Land

Java is named. The crate does not move.

The letter stays P. The arrows stay.

No side move on this plate.

The cap is the mark above the divider. The arrows still hide a side.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `InspectorFold` returns false from `isOptimizedDrawingEnabled`, a child repaint that meets the mark starts from the fold, the mark paints after the bar, and `placePin` keeps the mark at z-order 0.

**Reason.** The bar is opaque. A repaint of the arrows painted that bar across the P mark, and the mark did not paint again, so P disappeared under the divider.

**Avoidances.** Do not leave optimized drawing on for the fold. Do not move the mark off z-order 0. Do not change the letter away from P. Do not change the divider away from 11 pixels. Do not drop the arrows or the fold of the pin bar. The arrows still hide a side. Do not move Rust. Do not edit yaml. Do not stage the card-round hunks. Do not file a feature smoke. This row is not a feature. There is no crate commit on this plate. Do not add a public type or a public method. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-7797 PK-7867

# The bar buttons sit two pixels under preferred height

BB1 and BB2 button height is 2 pixels under the preferred height.

**semantic-key:** PK-6609

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

The quiet compile that skips tests passed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Tests are not run. Rust stays.

One commit of the named Java file records that the change is in the tree.

**semantic-key:** PK-8063

## Goal

BB1 and BB2 button height is 2 pixels under the preferred height.

**semantic-key:** PK-6610

## What stays

The app point size stays. Other short buttons stay on the 70 percent path. The font does not shrink on BB1 or BB2. No `Cargo.toml` edit. Rust stays.

**semantic-key:** PK-6611

## Edits

`fitAetherisButton` on a BB1 or BB2 button sets the height to the preferred height at the app point size, minus 2 pixels, then the floor at 1. The point size stays. Other short buttons stay on the 70 percent path. The comment on `seatBarButton` says the same.

**semantic-key:** PK-6612

## Proof

The quiet compile that skips tests passed. The window stays closed. The live POST is not made. A pool turn is not sent. A live SSH connect is not made. JUnit is not used. Tests are not run. Rust stays.

**semantic-key:** PK-6613

## Land

Java is named. Rust stays.

The point size stays.

No side move on this plate.

The cap is the bar height, 2 pixels under the preferred height.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `fitAetherisButton` on a BB1 or BB2 button sets the height to the preferred height at the app point size, minus 2 pixels, then the floor at 1. The point size stays. Other short buttons stay on the 70 percent path. The comment on `seatBarButton` says the same.

**Reason.** BB1 and BB2 were still at the full preferred height.

**Avoidances.** Do not shrink the font on BB1 or BB2. Do not move other short buttons off the 70 percent path. Do not drop the floor at 1. Do not move Rust. Do not edit `Cargo.toml`. Do not stage the card-round hunks. Do not file a smoke. This row is not a feature. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8093

# P, C, and T wear the pin size

P, C, and T show their letters at the Rust pin size.

**semantic-key:** PK-6614

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. The private class is `RustLetterFace`. The private method is `paintRustToken`. `seatRustLetter` is package-private.

The quiet compile that skips tests passed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Tests are not run. Rust stays.

One commit of the two named Java files records that the change is in the tree.

**semantic-key:** PK-8064

## Goal

P, C, and T show their letters at the Rust pin size.

**semantic-key:** PK-6615

## What stays

Cell-state I, E, P, and O stay. The app point size stays. `rustMarkEdge` and `seatRustBox` stay. No `Cargo.toml` edit. Rust stays.

**semantic-key:** PK-6616

## Edits

P, C, and T take `RustLetterFace`. The fill is painted, then a one-pixel white stroke with corner radius 2, then the letter. The size is the letter plus 24 by 8, from the egui button pad of 8 by 2 twice plus 8 by 4, and at least 22 by 22. The font is the app point size. P is green while the pin is on and sapphire while it is off. C and T stay sapphire. T carries `aetheris.token` so the cell-state marks stay on their own face.

**semantic-key:** PK-6617

## Land

Java is named. Rust stays.

The cell-state marks stay.

No side move on this plate.

The cap is the letter, visible, at the Rust pin size.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** P, C, and T take `RustLetterFace`. The fill is painted, then a one-pixel white stroke with corner radius 2, then the letter. The size is the letter plus 24 by 8, and at least 22 by 22. The font is the app point size. P is green while the pin is on and sapphire while it is off. C and T stay sapphire. T carries `aetheris.token`. `paintRustToken` is private. `seatRustLetter` is package-private.

**Reason.** P, C, and T did not show their letters at the Rust pin size, and the cell-state marks have to stay on their own face.

**Avoidances.** Do not restyle cell-state I, E, P, or O. Do not change the app point size. Do not change `rustMarkEdge` or `seatRustBox`. Do not drop the floor of 22 by 22. Do not move Rust. Do not edit `Cargo.toml`. Do not stage the card-round hunks. Do not file a smoke. This row is not a feature. Do not add a public type or a public method. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8094

# The Canvas tab loses the white bevel

That white line under the Canvas tab, the pane separating the Canvas tab and the scroll pane for the canvas cells, is a white highlight, a bevel or inset, that must be changed to the dark mode theme we defined.

**semantic-key:** PK-6618

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. `applyCanvasScroll` is a new public method on the existing theme. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

The quiet compile that skips tests passed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. No Rust.

One partial commit of `SshClientTheme.java` and `AetherisNotebookPanel.java` records that the change is in the tree.

**semantic-key:** PK-8065

## Goal

That white line under the Canvas tab, the pane separating the Canvas tab and the scroll pane for the canvas cells, is a white highlight, a bevel or inset, that must be changed to the dark mode theme we defined.

**semantic-key:** PK-6619

## What stays

The cells stay. The plate tabs stay. The Apple bar stays. The menu hover stays. The button pad stays. Six open bookings stay open in the working tree. Rust stays.

**semantic-key:** PK-6620

## Edits

The canvas cell scroll takes the quiet bevel after the Apple bar is seated. The lip is `0x0a0a0a`. The shadow is black. The system inset drops. The viewport border drops. `applyCanvasScroll` seats that bevel on the existing theme.

**semantic-key:** PK-6621

## Land

Java is named. Java moves. Rust stays.

No side move on this plate. The quiet bevel on that scroll is the named change.

The lip is `0x0a0a0a`. The shadow is black.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The canvas cell scroll takes the quiet bevel after the Apple bar is seated. The lip is `0x0a0a0a`. The shadow is black. The system inset drops. The viewport border drops. `applyCanvasScroll` is a new public method on the existing theme.

**Reason.** The pane under the Canvas tab still painted a white highlight, a bevel or inset, instead of the dark lip.

**Avoidances.** Do not change the cells, the plate tabs, or the Apple bar. Do not change the menu hover or the button pad. Do not close the six open bookings. Do not move Rust. Do not stage the card-round hunks. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-8186

# The canvas scroll wears the panel ground

Flesh that out so it matches our dark theme. The scroll pane under the Canvas tab still paints the Metal control face. That fill takes the panel ground.

**semantic-key:** PK-6622

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

The quiet compile that skips tests passed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. No Rust.

One partial commit of `SshClientTheme.java` records that the change is in the tree.

**semantic-key:** PK-8066

## Goal

Flesh that out so it matches our dark theme. The scroll pane under the Canvas tab still paints the Metal control face. That fill takes the panel ground.

**semantic-key:** PK-6623

## What stays

The quiet bevel stays. The viewport border stays clear. The cells stay. The plate tabs stay. The Apple bar stays. Six open bookings stay open in the working tree. Rust stays. The quiet-bevel plate stays locked.

**semantic-key:** PK-6624

## Edits

The canvas cell scroll paints the panel ground after the Apple bar is seated. `applyCanvasScroll` sets that background on the existing pane. The color is the panel background already on the theme. The quiet bevel stays. The viewport border stays clear.

**semantic-key:** PK-6625

## Land

Java is named. Java moves. Rust stays.

No side move on this plate. The panel ground on that scroll is the named change.

The pane fill is the panel ground.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The canvas cell scroll paints the panel ground after the Apple bar is seated. `applyCanvasScroll` sets that background on the existing pane. The color is the panel background already on the theme. The quiet bevel stays. The viewport border stays clear.

**Reason.** The scroll pane under the Canvas tab still painted the Metal control face.

**Avoidances.** Do not drop the quiet bevel. Do not put a viewport border back. Do not change the cells, the plate tabs, or the Apple bar. Do not close the six open bookings. Do not unlock the quiet-bevel plate. Do not add a public method. Do not move Rust. Do not stage the card-round hunks. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-8187

# The token ring counts Included cells

The token estimate, and the ring that opens on T, count Included cells on the current canvas. Excluded, input only, and output only stay out.


**semantic-key:** PK-4410

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Rust stays. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

The quiet compile that skips tests passed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

One commit of `AetherisNotebookPanel.java` records that the change is in the tree. The ledger note names the Java commit.

**semantic-key:** PK-8067

## Goal

The token estimate, and the ring that opens on T, count Included cells on the current canvas. Excluded, input only, and output only stay out.

**semantic-key:** PK-6626

## What stays

The inspector list stays. The mood line stays. It is not a cell. The heuristic stays. The ring window stays. The Rust token ring stays where it already sits in the tree. This delta waits until the crate is named. Six open bookings stay open in the working tree. The quiet-bevel plate stays locked. The panel-ground plate stays locked. The duplicate-group plate stays locked. The one-line-fold plate stays locked.

**semantic-key:** PK-6627

## Edits

`tokenActive` is true for a readable Included cell. `refreshInspector` still paints the working set. The string passed to `HeuristicTokenCounter` appends a source block only when that cell is Included. A merge source appends Included member prompts, and the merge reply only when the merge is Included. A focused merge appends Included member prompts in full, and the merge reply only when the merge is Included. The focused cell's prompt is counted only when that cell is Included.

**semantic-key:** PK-6628

## Proof

The quiet compile that skips tests passed. The window stays closed. The live POST is not made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

**semantic-key:** PK-6629

## Land

Java and Aetheris are named. Java moves. Rust stays. The token ring slice is already in the tree. The crate move for this delta waits until the crate is named.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `tokenActive` is true for a readable Included cell. `HeuristicTokenCounter` appends a source block only when that cell is Included. A merge appends Included member prompts, and the merge reply only when the merge is Included. The focused cell's prompt is counted only when that cell is Included. Rust stays until the crate is named.

**Reason.** Excluded, input only, and output only cells were still factored into the token ring.

**Avoidances.** Do not count Excluded, input only, or output only. Do not drop the inspector list. Do not treat the mood line as a cell. Do not change the heuristic. Do not move Rust on this plate. Do not close the six open bookings. Do not unlock the quiet-bevel, panel-ground, duplicate-group, or one-line-fold plates. Do not stage the card-round hunks. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-6630

# The bar buttons lose two more pixels

BB1 and BB2 buttons lose two pixels of height.

**semantic-key:** PK-6631

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. There is no crate commit. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No `Cargo.toml` edit.

Compile quietly from the Java tree and skip tests. Rust stays, so the crate is not checked for this plate. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

One commit of `AetherisNotebookPanel` records that the change is in the tree. The ledger note names the Java commit.

**semantic-key:** PK-8068

## Goal

BB1 and BB2 buttons lose two pixels of height.

**semantic-key:** PK-6632

## What stays

The 70 percent path stays. The red search x stays off the bar path. Menu font stays. Modal ink stays. The find filter stays. The About layout stays for the every-line plate. Six open bookings stay open. The menu-font plate stays locked. The SSH mux stays. Channel A stays. Rust stays.

**semantic-key:** PK-6633

## Edits

`fitAetherisButton`, when `aetheris.bar` is set, sets the height to the preferred height minus 4. The earlier cut was minus 2. This plate replaces that cut. It does not subtract 2 from a height that was already reduced. Buttons that are not bar buttons keep the 70 percent path. The search clear does not enter this method.

**semantic-key:** PK-6634

## Land

Java is named. Java moves. Rust stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** When `aetheris.bar` is set, `fitAetherisButton` sets the height to the preferred height minus 4. That replaces the minus-2 cut. It does not subtract 2 from a height that was already reduced. The search clear does not enter this method. There is no crate commit.

**Reason.** BB1 and BB2 were still two pixels taller than the preferred height minus 4.

**Avoidances.** Do not subtract 2 from a height that was already reduced by 2. Do not move buttons that are not bar buttons off the 70 percent path. Do not send the search clear through this method. Do not change menu font, modal ink, or the find filter. Do not move the About layout. Do not stage the page-arm, the pane scroll take, the `map_key` flip, or the card-round hunks. Do not move Rust. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8188

# The Mood button opens on a readable cell

The Mood button opens the mood dialog for a readable cell on the showing canvas.

**semantic-key:** PK-6635

## Goal

The Mood button opens the mood dialog for a readable cell on the showing canvas.

**semantic-key:** PK-6636

## What stays

A blocked cloud stays closed. A missing AMWP connector stays closed. Both keep the keyword only slide. The mood string, the switch, and the saved size stay. Menu width stays the live-width cut. The 5 pixel heatbar stays. Six open bookings stay open. The menu-width plate stays locked. Rust stays.

**semantic-key:** PK-6637

## Edits

The root cause is the arm. `openMood` called `findCell(focusedCellId)` and returned before `setVisible` when that id was null, when the cell was gone, or when the cell was not readable. `focusedCellId` is set on a card press and is never cleared. The only signal was the status word no cell. `targetCell` already falls through to the last cell on the showing canvas. Mood ignored that fall-through.

The private method `moodCard` keeps an armed readable cell. When the arm misses, it uses `targetCell` when that cell is readable, otherwise the last readable cell on the showing canvas. When no readable cell remains, the status still says no cell and the dialog stays closed. The cloud gate and the connector gate stay where they are.

**semantic-key:** PK-6638

## Land

Java is named. Java moves. Rust stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `moodCard` is private. It keeps an armed readable cell, then `targetCell` when that cell is readable, otherwise the last readable cell on the showing canvas. When none remains, the status says no cell and the dialog stays closed. The cloud gate and the connector gate stay.

**Reason.** `openMood` returned before the dialog was shown whenever the armed id was missing, stale, or not readable, and it ignored the canvas fall-through.

**Avoidances.** Do not open a blocked cloud or a missing AMWP connector. Do not drop the keyword only slide. Do not change the mood string, the switch, or the saved size. Do not change the menu width. Do not clear `focusedCellId` as the fix. Do not move Rust. Do not stage the banner, the connect path, or the card-round hunks. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8189

# Cancel shows the full word

The Cancel button shows the word Cancel in full.

**semantic-key:** PK-6639

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Rust stays. The branch, the action, the stage, and the phase clock are not retargeted. Do not spend the land marker on the stamp.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No `Cargo.toml` edit.

Compile quietly from the Java tree and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called. Rust stays.

The product commit follows and records that the change is in the tree. The ledger note names the Java commit.

**semantic-key:** PK-8069

## Goal

The Cancel button shows the word Cancel in full.

**semantic-key:** PK-6640

## What stays

Clear stays at the width it has. Approve stays at the width it has. The mood card stays. The cloud gate stays. The connector gate stays. The tooltips stay. The mood-arm plate stays locked. The heatbar-integer plate stays locked.

**semantic-key:** PK-6641

## Edits

After `fitAetherisButton`, Cancel preferred width and minimum width gain the same three ems Clear and Approve already take. The height stays the fitted height. The word stays Cancel.

**semantic-key:** PK-6642

## Land

Java is named. Rust stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** After `fitAetherisButton`, Cancel preferred width and minimum width gain three ems. The height stays the fitted height. The word stays Cancel. Clear and Approve keep the widths they have.

**Reason.** The Cancel text on the Mood button was truncated.

**Avoidances.** Do not change the width of Clear or Approve. Do not change the word away from Cancel. Do not change the fitted height. Do not open the cloud gate or the connector gate. Do not drop the tooltips. Do not move Rust. Do not spend the land marker on the stamp. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8095

# Canvas cells wear a round of six

The canvas cells wear the rounded corner. The cell, the prompt, and the reply use a round of 6.

**semantic-key:** PK-6643

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. The branch, the action, the stage, and the phase clock are not retargeted.

The edits below are the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. The private method is `roundEdge`. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

From the Casserole tree, compile quietly and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Rust does not move. Tests are not run on this plate.

One commit of the named Java file records that the change is in the tree. The proof on the papers is the product commit. Rust stays.

**semantic-key:** PK-8070

## Goal

The canvas cells wear the rounded corner. The cell, the prompt, and the reply use a round of 6.

**semantic-key:** PK-6644

## What stays

The selection colors stay. An armed cell, and a card lit with its merge, is sapphire. A pin match is green. A pin miss is dim. A ghost is dark gray. A quiet cell is the quiet gray. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons, the prompt, and the reply keep their size. The cell stroke stays 1 pixel. The pad inside that stroke stays 6. The pane line stays 2 pixels. The prompt line stays green. A reply that has text stays green. A reply with no text stays gray. The field stays the field. The Apple bar stays. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays. Earlier rows stay locked. The earlier selection reading stays the earlier reading. Rust stays. No yaml edit.

**semantic-key:** PK-6645

## Edits

The seat stays `AetherisNotebookPanel`. `roundEdge` paints an inside stroke on a round corner. The corner wedges take the parent plate. The pad sits inside the stroke.

The cell uses a round of 6, a stroke of 1, and a pad of 6. The color is the color `paintChrome` already chooses. The prompt and the reply use a round of 6 and a stroke of 2. `textScroll` clips its children to that round so the plate follows the stroke. The field on that plate stays the field color already on the text.

Rust paints the cell with this round of 6 and this 1 pixel inside stroke. egui 0.37 paints the panes with the inactive corner, and that corner is 6. The inactive stroke width is 0. Java keeps the green and the gray on the panes.

A collapsed card rounds the cell. It has no panes. A merge sink has no prompt pane. Its reply rounds.

No new package. No new color key. No new library.

**semantic-key:** PK-6646

## Land

Java is named. The crate does not move.

The selection colors stay. The caption stays. The Apple bar stays. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The cell, the prompt, and the reply use a round of 6. The cell stroke is 1 and the pad inside it is 6. The pane stroke is 2. `roundEdge` paints that inside stroke, and `textScroll` clips children to the round.

**Reason.** The Java canvas cells were square. They take the rounded corners the Rust cells already paint, on the cell as a whole, the input pane, and the reply pane, while selection and paint stay.

**Avoidances.** Do not change selection colors. Do not move the caption off the top stroke or change the caption inset from 8. Do not add a north pad. Do not change cell width or the size of the buttons, the prompt, or the reply. Do not change the field color. Do not round the error slide. Do not move the Apple bar, the footer, the mood glass, or the thinking strip. Do not follow the Rust inactive stroke width of 0 on the Java panes. Java keeps the green and the gray. Do not round a prompt onto a merge sink. A collapsed card has no panes. Do not add a public type, a public method, a package, a color key, a library, a vault column, a channel, or an AMWP type. `roundEdge` stays private. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-7798 PK-7868

# The text plates wear the round field

The text plates wear the round field.

**semantic-key:** PK-6647

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. The branch, the action, the stage, and the phase clock are not retargeted.

The edits below are the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. The private method stays `roundEdge`. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

From the Casserole tree, compile quietly and skip tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Rust does not move. Tests are not run on this plate.

One commit of the named Java file records that the change is in the tree. The proof on the papers is the product commit. Rust stays.

**semantic-key:** PK-8071

## Goal

The text plates wear the round field.

**semantic-key:** PK-6648

## What stays

The selection colors stay. An armed cell, and a card lit with its merge, is sapphire. A pin match is green. A pin miss is dim. A ghost is dark gray. A quiet cell is the quiet gray. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons, the prompt, and the reply keep their size. The cell stays a round of 6. The cell stroke stays 1 pixel. The pad inside that stroke stays 6. The pane line stays 2 pixels. The prompt line stays green. A reply that has text stays green. A reply with no text stays gray. The field stays the field. The Apple bar stays. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays. Earlier rows stay locked. The cell-round reading stays the earlier reading. The selection reading stays the earlier reading. Rust stays. No yaml edit.

**semantic-key:** PK-6649

## Edits

The seat stays `AetherisNotebookPanel`. The dark field on the prompt and on the reply is a round of 8. That is the Rust corner of 6 points at the open zoom of 1.3, taken to the nearest pixel. The outer stroke radius is 10, the field radius plus the 2 pixel line, so the inside edge of the stroke meets the field. `textScroll` clips its children to that outer radius. The viewport clips its own paint to the field radius of 8. The viewport is not opaque, so a later paint of the field includes the stroke under the corner. `update` paints without filling a square first.

The Apple bar stays a sibling of the field. The field clip does not cover the bar.

Rust fills the plate at the inactive corner of 6 and scales it by the open zoom. The inactive stroke width is 0. Java keeps the green and the gray on the panes.

A collapsed card has no panes. A merge sink has no prompt pane. Its reply takes this field round.

No new package. No new color key. No new library.

**semantic-key:** PK-6650

## Land

Java is named. The crate does not move.

The selection colors stay. The caption stays. The Apple bar stays. The cell stays a round of 6. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The dark field on the prompt and on the reply is a round of 8, and the outer stroke radius is 10 so the 2 pixel line meets the field. The cell stays a round of 6.

**Reason.** The text pane cells still looked more square than the Rust render. A Rust corner of 6 points at the open zoom of 1.3 is 8 pixels, and the Java field has to wear that round.

**Avoidances.** Do not leave the field square. Do not fill a square before the round paint. Do not make the viewport opaque. Do not let the field clip cover the Apple bar. Do not change the cell round away from 6, the cell stroke away from 1, or the pad away from 6. Do not change selection colors, the caption inset of 8, or the absence of a north pad. Do not round the error slide. Do not follow the Rust inactive stroke width of 0. Java keeps the green and the gray. A collapsed card has no panes. A merge sink has no prompt pane. Do not add a public type, a public method, a package, a color key, a library, a vault column, a channel, or an AMWP type. `roundEdge` stays private. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-7799 PK-7869

# The text panes drop the straight bevel

The text panes drop the straight bevel.

**semantic-key:** PK-6651

## Goal

The text panes drop the straight bevel.

**semantic-key:** PK-6652

## What stays

The round field stays a round of 8. The line stays 2 pixels and follows that curve. The prompt line stays green. A reply that has text stays green. A reply with no text stays gray. The field stays the field. The Apple bar stays. The grab bar stays. The cell stays a round of 6. The cell stroke stays 1 pixel. The pad inside that stroke stays 6. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons, the prompt, and the reply keep their size. The canvas split stays. A terminal split stays. The selection colors stay. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays. Earlier rows stay locked. The round-field reading, the cell-round reading, and the selection reading stay the earlier readings. Rust stays. No yaml edit.

**semantic-key:** PK-6653

## Edits

The seat stays `AetherisNotebookPanel`. The prompt and the reply sit in one split. `applySplitPane` clears the border, then the split UI puts the straight bevel back. This plate clears that border after the UI is on. The straight line leaves. The round field stays. The green line and the gray line stay.

The canvas split is a different call. A terminal split is a different call. Those borders stay. The grab bar stays. Its outline stays.

No new package. No new color key. No new library.

**semantic-key:** PK-6654

## Land

Java is named. The crate does not move.

The round field stays. The green line and the gray line stay. The cell stays a round of 6. The Apple bar stays. The grab bar stays. The canvas split stays. A terminal split stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** After the split UI is on, the prompt-and-reply split clears the straight bevel. The round field stays. The canvas split and a terminal split keep their borders.

**Reason.** `applySplitPane` clears the border, then the split UI puts the straight bevel back, so the straight line was still sitting on the text panes.

**Avoidances.** Do not clear the canvas split or a terminal split. Do not drop the grab bar or its outline. Do not change the round field of 8, the 2 pixel line, the cell round of 6, the cell stroke of 1, or the pad of 6. Do not change the green prompt line, the green reply that has text, or the gray reply that has no text. Do not move the caption or change the inset of 8. Do not add a north pad. Do not add a public type or a public method. `roundEdge` stays private. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-7800 PK-7870

# The cell bar is the short quiet grey

The cell bar wears the quiet grey and is ten pixels shorter.

**semantic-key:** PK-6655

## Goal

The cell bar wears the quiet grey and is ten pixels shorter.

**semantic-key:** PK-6656

## What stays

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The round field stays a round of 8. The green line and the gray line stay. The cell stays a round of 6. The cell split stays clear. The Apple bar stays. The button face stays `#3c3c3c`. A form field stays `#1a1a1a`. The cell panes stay black. The panel ground stays `0x121212`. The frame, the split, and the status stay `0x0c0c0c`. The bevel shadow stays black. The bevel lip stays `0x0a0a0a`. The other split bars stay. The divider thickness stays 11. The one-touch arrows stay. Drag stays. Yes and No stay. P and C stay. The thinking clear mark stays. The Ocean-edge reading and the quiet-lip reading stay the earlier readings. No yaml edit. Rust stays.

**semantic-key:** PK-6657

## Edits

The light line on each edge of the cell bar takes the quiet grey. The grey is `0x0a0a0a`. The bar is ten pixels shorter than the panes, five on each side. The panes keep their width. The split stays a split.

`seatQuietBar` seats the cell split. `QuietSplitUi` is the private split UI. `QuietDivider` paints the short bar. The lip is the quiet grey. The edge is black. The ocean border does not paint. The arrows stay on the bar. The seat is `AetherisNotebookPanel`.

**semantic-key:** PK-6658

## Land

Java is named. The crate does not move.

Font colors stay. Selection highlights stay. The green line and the gray line stay. The cell split stays clear. The cell panes stay black. The panes keep their width.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The cell bar lip is `0x0a0a0a`, the edge is black, and the bar is ten pixels shorter than the panes, five on each side. The panes keep their width.

**Reason.** The light line on each edge of the cell bar was not the quiet grey, and the bar ran the full pane width.

**Avoidances.** Do not shorten the panes. Do not change the other split bars. Do not change divider thickness away from 11. Do not drop the one-touch arrows or drag. Do not let the ocean border paint on this bar. Do not change font colors or selection highlights. Do not add a public type or a public method. `QuietSplitUi` and `QuietDivider` stay private. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-7801 PK-7871

# P stays painted on the divider

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6659

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The P button stays painted on top of the inspector divider.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8128

## Slice

The P button stays painted on top of the inspector divider. Java moves. Rust stays.

**semantic-key:** PK-6660

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The letter stays P. The divider stays 11 pixels. The arrows stay. The token window stays. No yaml edit. Rust stays.

**semantic-key:** PK-6661

## Depends

`AetherisNotebookPanel`.

**Truth.** The P button stays painted on top of the inspector divider. Java moves. Rust stays.

**Reason.** The P mark was being covered by the opaque divider when the arrows repainted.

**Avoidances.** Do not change the letter away from P. Do not change the divider away from 11 pixels. Do not drop the arrows. Do not treat this row as a feature. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not add a public type, a public method, a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6662

# The bar buttons sit two pixels under preferred height

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-8201

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

BB1 and BB2 button height is 2 pixels under the preferred height. The point size stays. Other short buttons stay on the 70 percent path.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. The point size stays. Other short buttons stay on the 70 percent path.

**semantic-key:** PK-8129

## Slice

BB1 and BB2 button height is 2 pixels under the preferred height. Java moves. Rust stays.

**semantic-key:** PK-6663

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The app point size stays. Other short buttons stay.

**semantic-key:** PK-6664

## Depends

`AetherisNotebookPanel.java`.

**Truth.** BB1 and BB2 button height is 2 pixels under the preferred height. The app point size stays. Other short buttons stay on the 70 percent path. Java moves. Rust stays.

**Reason.** BB1 and BB2 were still at the full preferred height.

**Avoidances.** Do not change the app point size. Do not move other short buttons off the 70 percent path. Do not treat this row as a feature. Do not stage the card-round hunks. Do not add a public type, a public method, or a package. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6665

# P, C, and T wear the pin size

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-8202

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature. PRESERVE-ALL stays on this row.

P, C, and T show their letters at the Rust pin size. The cell-state marks stay.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. `RustLetterFace` is private. `seatRustLetter` is package-private. The cell-state marks stay.

**semantic-key:** PK-6666

## Slice

P, C, and T show their letters at the Rust pin size. Java moves. Rust stays.

**semantic-key:** PK-6667

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. Cell-state I, E, P, and O stay. The app point size stays.

**semantic-key:** PK-6668

## Depends

`AetherisNotebookPanel.java`. `ThinkStrip.java`.

**Truth.** P, C, and T show their letters at the Rust pin size. `RustLetterFace` is private. `seatRustLetter` is package-private. Cell-state I, E, P, and O stay. Java moves. Rust stays.

**Reason.** Those three letters were not yet at the Rust pin size, and the cell-state marks have to stay.

**Avoidances.** Do not restyle cell-state I, E, P, or O. Do not change the app point size. Do not treat this row as a feature. Do not stage the card-round hunks. Do not add a public type, a public method, or a package. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-8145

# The Canvas tab loses the white bevel

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6669

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

That white line under the Canvas tab, the pane separating the Canvas tab and the scroll pane for the canvas cells, is a white highlight, a bevel or inset, that must be changed to the dark mode theme we defined. The highlight wears the dark grey that sits under the panel and over the black.

No Lombok. No lambdas in our types. No new public type. `applyCanvasScroll` is a new public method on the existing theme. No new package.

**semantic-key:** PK-8130

## Slice

That white line under the Canvas tab, the pane separating the Canvas tab and the scroll pane for the canvas cells, is a white highlight, a bevel or inset, that must be changed to the dark mode theme we defined. Java moves. Rust stays.

**semantic-key:** PK-6670

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The cells stay. The plate tabs stay. The Apple bar stays. Six open bookings stay open in the working tree.

**semantic-key:** PK-6671

## Side move

No side move on this plate. The quiet bevel on that scroll is the named change.

**semantic-key:** PK-6672

## Depends

`SshClientTheme.java`. `AetherisNotebookPanel.java`. The canvas cell scroll sits under the Canvas tab.

**Truth.** The white highlight under the Canvas tab changes to the dark mode theme. `applyCanvasScroll` is a new public method on the existing theme. The cells, the plate tabs, and the Apple bar stay. Java moves. Rust stays.

**Reason.** That separator still painted a white bevel or inset.

**Avoidances.** Do not change the cells, the plate tabs, or the Apple bar. Do not close the six open bookings. Do not treat this row as a feature. Do not stage the card-round hunks. Do not add a public type or a package. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6673

# The canvas scroll wears the panel ground

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6674

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

Flesh that out so it matches our dark theme. The scroll pane under the Canvas tab still paints the Metal control face. That fill takes the panel ground. The fill wears the same dark the panel already wears.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package.

**semantic-key:** PK-8131

## Slice

Flesh that out so it matches our dark theme. The scroll pane under the Canvas tab still paints the Metal control face. That fill takes the panel ground. Java moves. Rust stays.

**semantic-key:** PK-6675

## What stays

Earlier rows through the button-pad plate stay locked. The quiet-bevel plate stays locked. The closed feature stays closed. This row is not a feature. The quiet bevel stays. The viewport border stays clear. The plate tabs stay. The cells stay. Six open bookings stay open in the working tree.

**semantic-key:** PK-6676

## Side move

No side move on this plate. The panel ground on that scroll is the named change.

**semantic-key:** PK-6677

## Depends

`SshClientTheme.java`. `applyCanvasScroll` already seats the quiet bevel. The pane fill is the Metal control face until this plate.

**Truth.** The scroll pane under the Canvas tab takes the panel ground. The quiet bevel stays. The viewport border stays clear. Java moves. Rust stays.

**Reason.** That pane still painted the Metal control face after the quiet bevel was seated.

**Avoidances.** Do not drop the quiet bevel. Do not add a viewport border. Do not change the plate tabs or the cells. Do not unlock the quiet-bevel plate. Do not close the six open bookings. Do not treat this row as a feature. Do not stage the card-round hunks. Do not add a public type, a public method, or a package. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6678

# The token ring counts Included cells

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.


**semantic-key:** PK-4411

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Aetheris is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The ring counts Included cells. Restricted cells stay out. Included is the active state. The inspector list stays.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package.

**semantic-key:** PK-6679

## Slice

The Token Ring Calc counts Included cells on the current canvas. Excluded, input only, and output only stay out. Java moves. Rust stays.

**semantic-key:** PK-6680

## What stays

Earlier rows through the button-pad plate stay locked. The quiet-bevel plate stays locked. The panel-ground plate stays locked. The duplicate-group plate stays locked. The one-line-fold plate stays locked. The closed feature stays closed. This row is not a feature. The inspector list stays. The mood line stays. The Rust token ring stays where it already sits in the tree. Six open bookings stay open in the working tree.

**semantic-key:** PK-6681

## Depends

`AetherisNotebookPanel.java`. The token estimate south of the inspector. The ring opens on T.

**Truth.** The Token Ring Calc counts Included cells on the current canvas. Excluded, input only, and output only stay out. The inspector list stays. The mood line stays. Java moves. Rust stays.

**Reason.** Restricted cells were still factored into the token ring.

**Avoidances.** Do not count a restricted cell. Do not drop the inspector list. Do not treat the mood line as a cell. Do not move the Rust token ring on this plate. Do not treat this row as a feature. Do not stage the card-round hunks. Do not add a public type, a public method, or a package. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-6682

# The bar buttons lose two more pixels

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6683

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

BB1 and BB2 buttons lose two pixels of height. The bar height is the preferred height minus 4.

**semantic-key:** PK-8132

## Slice

BB1 and BB2 buttons lose two pixels of height. The bar height is the preferred height minus 4.

**semantic-key:** PK-6684

## What stays

The 70 percent path stays on the buttons that are not bar buttons. The red search x stays off the bar path. Menu font stays. Modal ink stays. The find filter stays. The About layout stays for the every-line plate. Six open bookings stay open in the working tree. The menu-font plate stays locked. Rust stays.

**semantic-key:** PK-6685

## Wire

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** BB1 and BB2 lose two pixels of height. The bar height is the preferred height minus 4. The 70 percent path stays on the other buttons. Rust stays.

**Reason.** The earlier cut was preferred height minus 2, and this plate replaces that cut with minus 4.

**Avoidances.** Do not move the other buttons off the 70 percent path. Do not put the red search x on the bar path. Do not change menu font, modal ink, or the find filter. Do not move the About layout. Do not move Rust. Do not treat this row as a feature. Do not stage the card-round hunks. Do not unlock the menu-font plate.

**semantic-key:** PK-8159

# The Mood button opens on a readable cell

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-8203

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The Mood modal is not displaying when the mood button is clicked. The root cause is isolated and the fix is named.

**semantic-key:** PK-8133

## Slice

The Mood button opens the mood dialog. The root cause is the arm. `openMood` asked for the armed cell and returned before the dialog was shown when that id was missing, stale, or not readable. The status line carried the word no cell. The canvas already has a fall-through cell. Mood ignored it.

**semantic-key:** PK-6686

## What stays

A blocked cloud stays closed, with the keyword only slide. A missing AMWP connector stays closed, with that same slide. The mood string, the switch, and the saved size stay. The menu-width plate stays locked. Six open bookings stay open in the working tree. Rust stays.

**semantic-key:** PK-6687

## Wire

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The Mood button opens the mood dialog for a readable cell. The root cause is the arm. A blocked cloud stays closed. A missing AMWP connector stays closed. Rust stays.

**Reason.** Mood returned before the dialog whenever the armed id missed, and it ignored the canvas fall-through.

**Avoidances.** Do not open a blocked cloud. Do not open a missing connector. Do not drop the keyword only slide. Do not change the mood string, the switch, or the saved size. Do not move Rust. Do not treat this row as a feature. Do not stage the banner or the connect path. Do not unlock the menu-width plate.

**semantic-key:** PK-8160

# Cancel shows the full word

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6688

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

In the Mood modal, the Cancel text on the Cancel button is truncated. The Cancel button grows wide enough that Cancel is fully rendered.

**semantic-key:** PK-8134

## Slice

The Cancel button on the Mood dialog is wide enough that the word Cancel is fully rendered.

**semantic-key:** PK-6689

## What stays

Clear stays. Approve stays. The mood card stays. The cloud gate stays. The connector gate stays. The mood-arm plate stays locked. The heatbar-integer plate stays locked. Six open bookings stay open.

**semantic-key:** PK-6690

## Wire

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The Cancel button grows wide enough that the word Cancel is fully rendered. Clear stays. Approve stays. Rust stays.

**Reason.** The Cancel label was truncated on the Mood dialog.

**Avoidances.** Do not change Clear or Approve. Do not change the mood card. Do not open the cloud gate or the connector gate. Do not move Rust. Do not treat this row as a feature. Do not stage the banner or the connect path. Do not unlock the mood-arm plate or the heatbar-integer plate.

**semantic-key:** PK-8161

# Canvas cells take the Rust corner

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6691

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made.

The cells in the Java canvas take the rounded corners of the Rust cells. The cell as a whole, the input pane, and the reply pane. The selection and the paint stay.

No Lombok. No lambdas in our types. No new public type. No new public method. The private method is `roundEdge`. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8176

## Slice

The canvas cells wear the rounded corner. The cell, the prompt, and the reply use a round of 6. The cell stroke stays 1 pixel. The pane line stays 2 pixels. The colors stay. Rust stays.

**semantic-key:** PK-6692

## What stays

Earlier rows stay locked. The selection colors stay. An armed cell is sapphire. A pin match is green. A pin miss is dim. A ghost is dark gray. A quiet cell is the quiet gray. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons, the prompt, and the reply keep their size. The Apple bar stays. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays. No yaml edit.

**semantic-key:** PK-6693

## Depends

`AetherisNotebookPanel`.

**Truth.** The cell, the prompt, and the reply use a round of 6. The cell stroke stays 1 pixel and the pane line stays 2 pixels. The colors stay.

**Reason.** The Java canvas cells did not wear the rounded corners the Rust cells already use, on the cell as a whole, the input pane, and the reply pane.

**Avoidances.** Do not change the selection colors or the paint that chooses them. Do not move the caption, the Apple bar, the error slide, the footer, the mood glass, or the thinking strip. Do not add a north pad. Do not change cell width or the size of the buttons, the prompt, or the reply. The error slide stays square. Do not add a public type or a public method. `roundEdge` stays private. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not add a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-8204

# The text plates match the Rust round

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6694

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made.

The text pane cells still look more square than what Rust renders. The roundness of that edge matches the Rust render.

No Lombok. No lambdas in our types. No new public type. No new public method. The private method stays `roundEdge`. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8177

## Slice

The text plates wear the round field. The dark field on the prompt and on the reply is a round of 8. The line stays 2 pixels and follows that curve. The cell stays a round of 6. Rust stays.

**semantic-key:** PK-6695

## What stays

Earlier rows stay locked. The selection colors stay. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons, the prompt, and the reply keep their size. The prompt line stays green. A reply that has text stays green. A reply with no text stays gray. The field stays the field. The Apple bar stays. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays. No yaml edit.

**semantic-key:** PK-6696

## Depends

`AetherisNotebookPanel`.

**Truth.** The dark field on the prompt and on the reply is a round of 8. The 2 pixel line follows that curve. The cell stays a round of 6.

**Reason.** The text panes still looked more square than the Rust render, so the edge roundness has to match that render.

**Avoidances.** Do not leave the field square. Do not change the cell round away from 6. Do not change the line away from 2 pixels. Do not change selection colors, the caption inset of 8, or the absence of a north pad. Do not change the green prompt line, the green reply that has text, or the gray reply that has no text. Do not change the field color. Do not move the Apple bar, the error slide, the footer, the mood glass, or the thinking strip. The error slide stays square. Do not add a public type or a public method. `roundEdge` stays private. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not add a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-8205

# The straight bevel leaves the text panes

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6697

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made.

The straight bevel still sits on the text panes. This plate clears that line.

No Lombok. No lambdas in our types. No new public type. No new public method. The private method stays `roundEdge`. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8178

## Slice

The text panes drop the straight bevel. The prompt and the reply sit in one split. The straight bevel leaves after the split UI is on. The round field stays a round of 8. The line stays 2 pixels. The cell stays a round of 6. Rust stays.

**semantic-key:** PK-6698

## What stays

Earlier rows stay locked. The round field stays. The prompt line stays green. A reply that has text stays green. A reply with no text stays gray. The field stays the field. The Apple bar stays. The grab bar stays. The cell stays a round of 6. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons, the prompt, and the reply keep their size. The canvas split stays. A terminal split stays. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays. No yaml edit.

**semantic-key:** PK-6699

## Depends

`AetherisNotebookPanel`.

**Truth.** The straight bevel leaves the prompt-and-reply split after the split UI is on. The round field stays a round of 8, the line stays 2 pixels, and the cell stays a round of 6.

**Reason.** The straight bevel was still sitting on the text panes after the round field was painted.

**Avoidances.** Do not clear the canvas split or a terminal split. Do not drop the grab bar. Do not change the round field, the line, or the cell round. Do not add a public type or a public method. `roundEdge` stays private. Do not edit yaml. Do not move Rust. Do not stage the card-round hunks. Do not add a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-8206

# The cell bar is shorter and quiet

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-6700

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java is named. Rust stays. No extra cut is made.

The cell bar wears `0x0a0a0a`. It is ten pixels shorter than the panes. The panes keep their width. The arrows stay.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8179

## Slice

The cell bar wears the quiet grey and is ten pixels shorter. The grey is `0x0a0a0a`. The panes keep their width. Rust stays.

**semantic-key:** PK-6701

## What stays

Earlier rows stay locked. Font colors stay. Selection highlights stay. The green line and the gray line stay. The cell split stays clear. The button face stays. A form field stays. The white letters stay. The cell panes stay black. The other split bars stay. No yaml edit.

**semantic-key:** PK-6702

## Depends

`AetherisNotebookPanel`.

**Truth.** The cell bar wears `0x0a0a0a` and is ten pixels shorter than the panes. The panes keep their width. The arrows stay.

**Reason.** The cell bar was the light edge and ran as wide as the panes. The same quiet grey belongs on a shorter bar.

**Avoidances.** Do not change the pane width. Do not drop the arrows. Do not change the other split bars. Do not change font colors, selection highlights, the green line, the gray line, the clear cell split, the button face, a form field, the white letters, or the black cell panes. Do not edit yaml. Do not add a public type, a public method, a package, a channel, an AMWP type, a color key, or a library. Do not move Rust. Do not stage the card-round hunks. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-8207

# The notebook tab is a canvas, an inspector, and a footer, it runs only on a mux-capable session, and cells never enter the terminal memory.

The files are the client application and new types under `org.aetheris.swing`.

The shell frame holds:

- a connection strip that is collapsed while connected
- a tabbed pane
- a terminal tab that is today's split tree and the terminal widget
- a notebook tab with canvas tabs, an inspector titled for the working-set preview, and a footer of Run, New turn, and Preview
- a status line naming vault `ram` or `disk`, the mux, and the engine

The split tree stays inside the terminal tab. Do not reparent the terminal widget.

There is no Graph button.

A cell has four states, and the state is modal. The pin marks a burner. Completing turn N materializes an empty turn N+1.

Hotkeys bind on the notebook root only: Ctrl+Shift+I, Ctrl+Shift+E, Ctrl+Shift+P, and Ctrl+Shift+O.

The notebook tab is disabled unless the focused session is mux-capable.

Assembly, on the client, walks `cell_order` before the target. Skip an excluded cell. Skip an empty burner. Input-only contributes the user text. Output-only contributes the assistant text. Included contributes both. Then append the target prompt. The inspector shows that list and a token estimate.

Execute: the shell worker builds the working set, sends it on the mux, and the host clears the RAM region and pushes the full set, then completes on the existing model lane. A stream may return as one data frame plus end. Cells never enter the terminal memory pack. A terminal `message find` returns no canvas turns.

The four states are included, excluded, input only, and output only. There is no fifth state.

**Truth.** One notebook tab beside the existing terminal tab, four-state cells, a working-set walk with a token estimate, and execute that pushes into RAM memory without writing the terminal pack.

**Reason.** Reparenting the terminal widget would break the splits the operator already uses. Disabling the notebook on a raw session stops a run that has no mux to ride. Keeping cells out of terminal memory stops `message find` from listing them.

**Avoidances.** Do not add a Graph button. Do not reparent the terminal widget. Do not enable the notebook tab on a raw session. Do not write canvas turns into the terminal pack. Do not add a fifth cell state.

**semantic-key:** PK-6703

# HikariCP and H2 are compile dependencies of the shell only, notebook yaml is read by the existing hand parser, and one named worker owns database and mux client input-output.

The build file promotes HikariCP and H2 to compile scope. H2 may already be present at runtime; promote it. Do not register a Spring `DataSource` bean on the host. Do not add Flyway, a persistence provider in use, or a second yaml library.

The notebook yaml reader parses `vault.mode`, `vault.path`, `network.air-gap`, and `amwp.enabled` with the existing space-indent hand parser. Unknown keys are ignored. A tab character fails loudly. Spaces are the only indent.

`org.aetheris.swing.AetherisWorker` is a single named executor in the shell. It runs JDBC and the client's mux input-output. It is not the host's existing lane scheduler. It is not the Swing event thread. It shuts down when the window closes.

The host's `application.yaml` is not the file these four keys are invented in as a Spring datasource. The notebook file is the notebook file.

**Truth.** Compile-scoped pool and database, four keys on the hand parser, tabs fail, and one shell worker for JDBC and client mux input-output.

**Reason.** A Spring datasource on the host would open a database the host is forbidden to open. A second yaml library would fork the parser the shell already trusts. The event thread must not block on JDBC or on the pipe.

**Avoidances.** Do not add a Spring `DataSource` on the host. Do not add Flyway or a second yaml stack. Do not run JDBC or client mux input-output on the event thread. Do not accept tab indent.


**semantic-key:** PK-4412 PK-4413

# The shell vault is pure JDBC, RAM by default, disk only when asked, and it stores workspace, canvas, cell, and trace.

The package is `org.aetheris.vault`. Access is pure JDBC. SQL lives in a constants class. The schema is applied in-tree when the vault opens.

| Table | Role |
|---|---|
| `workspace` | the user workspace |
| `canvas` | canvas tabs |
| `cell` | turns, state, and the large text columns |
| `trace` | execute traces |

There are no tag tables and no scope tables. A burner row may exist with its large text columns null.

Modes:

- `ram` uses `jdbc:h2:mem:aetheris`. This is the default. It is not crash-proof.
- `disk` uses `~/.aetheris/{sanitize(user)}/vault`. Crash-proof behavior exists only in disk mode.

All JDBC runs on `AetherisWorker`.

Search is H2 `LIKE`. There is no Lucene index.

The host process does not open this database. The unused in-memory database already on the host classpath is not this vault and is not started for the notebook.

**Truth.** Four tables, pure JDBC, RAM at `jdbc:h2:mem:aetheris` by default, disk under the sanitized user only when opted in, and every statement on the shell worker.

**Reason.** The notebook must survive a shell restart only when the operator chose disk. RAM is the default so a first launch does not create a file the operator did not ask for. Search stays inside the database the shell already opens.

**Avoidances.** Do not add tag or scope tables. Do not open this database from the host. Do not run JDBC on the event thread. Do not add Lucene. Do not treat RAM as crash-proof.

**semantic-key:** PK-6704

# Collapse is a boolean, the brief is assistant text, the four letters keep tooltips, and import and clear do not touch the terminal session.

These locks are closed.

- `collapsed` is a boolean on `CellRecord`. Default false. It is not a `CellState`.
- The brief is assistant text only, from the first character. One line, or two if the header row has the height. Empty output leaves the brief empty. Never the user text.
- `I`, `E`, `P`, and `O` stay one character. Tooltips are Included, Excluded, Input only, and Output only. There is no second set of buttons.
- The collapse glyph is `+` when collapsed and `-` when open. The tooltip is `Expand` or `Collapse`.
- The working set does not read `collapsed`. `E` is still the only way out of context.
- Input is `#00FF66` on `#1A1A1A`. The border is `#3CB371`. Output and the View modal are `#FFFF55`. An excluded cell stays gray, and gray wins.
- View bounds live in `~/.aetheris/{user}/view-bounds.txt`. Not a cell column. Not `aetheris.yaml`. Off-screen falls back to 720 by 480, centered on the notebook pane.
- A header rebuild restores `cardY - viewY`. Focus stays on that card.
- Import is a right-click item `Import...`. It replaces `userText`. The cap is 1 MiB. There is no pipe push.
- Clear is a footer button. The active canvas only. One new empty included turn. Terminal `clear` is not called. There is no confirm dialog.
- `select model` and `list models` stay unwired. `swap` and `ollama list` stay as they are.
- Shell launch is `java -cp` plus the local classpath helper. Never `exec:java`.
- The frozen Stage 1 plan is not edited.

A burner still stores null large-text columns. The collapse flag is stored either way.

**Truth.** Fourteen locks fix the flag, the brief, the letters, the colors, the bounds file, the scroll math, import, clear, and the commands that stay unwired.

**Reason.** Each lock retires an earlier alternative: a fifth state, full-word labels, a second button row, user text as the brief, a header import button, and a clear that also wiped the terminal.

**Avoidances.** Do not add a fifth state. Do not read `collapsed` in the working set. Do not call terminal `clear` from the footer. Do not wire `select model` or `list models`. Do not store view bounds on the cell.

**semantic-key:** PK-6705

# Clear turns deletes the active canvas, including burners and their trace rows, and leaves one empty included turn without touching the terminal.

Files: `VaultStore`, `VaultSql`, `AetherisNotebookPanel.buildFooter`.

1. The footer button is `Clear turns`, beside New turn.
2. The active canvas only. On `AetherisWorker`: delete `trace` rows whose `cell_id` is in that canvas, then delete those `cell` rows, then add one empty included cell, then save order.
3. Replace the in-memory `canvas.cells` and `cellOrder` with that one cell. Rebuild the tabs. Scroll to it.
4. Do not call `ClearCommand`. Do not touch `ChatSession`.
5. There is no confirm dialog. The status line names the canvas and says the turns are cleared.
6. Burner cells are deleted with the rest.

The new turn has the same shape as `newCell`. The terminal history stays. One gesture that cleared both memories would surprise the console.

Trace rows are deleted even though nothing inserts traces yet, so a later trace cannot dangle from a removed cell.

**Truth.** One footer button clears one canvas back to a single empty included turn, deletes trace rows first, and does not call the terminal clear.

**Reason.** The canvas is the vault. The terminal clear drops session turns and keeps the system prompt. They are cousins, not the same box.

**Avoidances.** Do not call `ClearCommand`. Do not touch `ChatSession`. Do not spare burner cells. Do not add a confirm dialog. Do not clear every canvas.

**semantic-key:** PK-6706

# Fourteen hand checks cover collapse, color, View, focus, import, and clear, and a unit-test task is not the proof.

No new test framework. Do not claim a check passed unless it was clicked.

1. Collapse a cell that has output. The brief is the start of that output. Expand. Both texts are unchanged.
2. Collapse a cell with empty output. The brief is blank.
3. Restart the shell on a disk vault. Collapsed cells are still collapsed. An old vault that predates the column opens with every cell expanded.
4. A collapsed included cell still contributes its output to the working set of a later Run.
5. `E` on a collapsed cell ghosts it, grays the brief, and drops it from the working set.
6. Hover `I`, `E`, `P`, and `O` and read the four tooltips.
7. Input is emerald. Output and View are yellow. An excluded cell is gray.
8. Move and resize View, close, and open another cell. Same place and size.
9. Close View while it is off-screen. The next open is 720 by 480, centered.
10. Click `I` on a cell that is not at the top of the pane. That cell stays at the same place in the scroll.
11. Right-click the input and import a short text file. The input is replaced. The output is not.
12. Right-click an output-only cell. Import refuses.
13. A file over 1 MiB refuses.
14. Clear turns leaves one empty turn. The terminal history is still there.

Disk restart is the persistence proof. RAM mode still uses the View bounds file, which is not inside the database.

**Truth.** Fourteen clicks prove the brief, the old vault, the working set, the tooltips, the colors, the bounds, the hold, import, and clear.

**Reason.** A compile does not show that gray wins, that an off-screen View returns to 720 by 480, or that the terminal history survived.

**Avoidances.** Do not add a unit-test task. Do not claim the clicks without clicking them. Do not treat an old vault's expanded cells as a failed migration.

**semantic-key:** PK-6707

# A cell collapses to its header and an output brief, colors and focus hold, import fills that cell's input, and clear leaves the terminal session alone.

On the shell that already runs, the goal is all of this.

- A cell collapses to its header and an output brief, and comes back with the same text.
- The four letters have tooltips.
- Input is emerald and output is the console yellow.
- View reopens where it was.
- A state click does not jump the scroll.
- A right-click imports a text file into that cell's input.
- Clear turns empties the active canvas and leaves the terminal session alone.
- A disk vault from before this plan still opens.

Not this pass: `select model`, `list models`, a fifth context state, a working-set change, pinned context, a cloud adapter, mux channel `0x0004`, a graph button, an import button, full-word labels, a second button row, and any edit to the frozen Stage 1 plan.

An old disk vault that predates the collapse column opens with every cell expanded. That is part of the goal, not a failure.

**Truth.** The goal is collapse, tooltips, emerald and yellow, remembered View, a held scroll, right-click import, canvas-only clear, and an old vault that still opens.

**Reason.** The operator had forgotten the letters, lost the View window, and was thrown to the top of the pane by a state click. Those are the defects this pass closes.

**Avoidances.** Do not change the working-set walk. Do not add full-word labels or a second button row. Do not edit the frozen Stage 1 plan. Do not wire the tabled model commands.

**semantic-key:** PK-6708

# The collapse column lands before the card, the focus hold lands with the card, and the manual check is last.

Dependencies point downward. Do not pull the next stage into a slice.

1. The `collapsed` column and `CellRecord`. Vault first.
2. The collapse card and the output brief. Needs the column.
3. The focus hold. With the card, because the toggle rebuilds.
4. Tooltips. Header, any time after the card.
5. Colors. Paint, any time after the card.
6. The View bounds file. Independent.
7. Right-click import. Independent.
8. Footer Clear turns. Independent.
9. Manual check. After the eight behaviors.

No new test framework. No unit-test task.

A collapse that does not survive a rebuild is not the flag. That is why the column is first and the hold travels with the card.

**Truth.** Vault column first, card and hold together, paint and tooltips after the card, and three independent slices before the hand check.

**Reason.** The toggle calls a rebuild. Without a stored flag and a held scroll, the rebuild forgets the collapse and jumps to the top.

**Avoidances.** Do not paint the card before the column exists. Do not add a unit-test task. Do not pull next-stage work into a slice.

**semantic-key:** PK-6709

# The collapse flag is a non-null boolean on the cell, default false, added to an existing disk vault without failing on a duplicate add.

Files: `org.aetheris.vault.CellRecord`, `VaultSql`, `VaultStore`.

1. Add `public boolean collapsed` on `CellRecord`. `newCell` leaves it false.
2. Add `collapsed BOOLEAN NOT NULL DEFAULT FALSE` to the cell table definition.
3. On vault open, add the column when it is missing. `CREATE TABLE IF NOT EXISTS` does not alter a disk vault that already has `cell`. Probe `INFORMATION_SCHEMA.COLUMNS` for table `cell`, column `collapsed`. Add the column only when the probe is empty. A duplicate add is not a failure.
4. Carry the flag through `INSERT_CELL`, `UPDATE_CELL`, `SELECT_CELLS`, `insertCell`, `bindUpdate`, and `loadCells`.
5. A burner still writes null large-text columns. The flag is stored either way.
6. Do not add an enum value to `CellState`.

An older disk vault opens. Every cell on that vault starts expanded because the new column's default is false. A failed duplicate add is success, so a second open does not throw.

**Truth.** The flag is a boolean column defaulting to false, probed and added when missing, and carried on insert, update, and load.

**Reason.** A create-if-absent statement does not alter a table that already exists, so an old disk vault would otherwise drop the flag on the next rebuild.

**Avoidances.** Do not add a fifth `CellState`. Do not skip the probe. Do not treat a duplicate add as a failure. Do not leave the flag off the insert or the load.

**semantic-key:** PK-6710

# One card keeps its header while collapsed, the body hides, and the brief is the assistant text from the first character.

File: `org.aetheris.swing.AetherisNotebookPanel`, inner class `CellCard`.

1. Add one header toggle beside the state buttons. The label is `+` when `cell.collapsed` is true and `-` when false. The tooltip is `Expand` or `Collapse`. The operator may rename the glyph later. The glyphs in this plan are the ones that ship.
2. The toggle writes `cell.collapsed`, calls `persistQuiet`, then `rebuildTabs`, using the focus hold.
3. An open card keeps the vertical split. A closed card does not add the split. The brief is added under the header.
4. The brief source is `assistantText` only. Plain truncation from the first character. One line. A second line only when the header row is tall enough. No summarizer. Empty or whitespace output leaves the brief blank.
5. Brief color follows the color rules. Gray wins when `cell.state` is excluded.
6. The collapsed maximum height is the header plus the brief. It is not the open card's 280.
7. `I`, `E`, `P`, `O`, the burner, and View stay on that same header while collapsed. View stays disabled when there is no output.

User text is never the source of the brief. A collapsed included cell still enters the working set.

**Truth.** One toggle, one card, assistant text as a one-line or two-line brief, and the open-card cap of 280 does not apply once collapsed.

**Reason.** A second panel swapping focus was the scroll jump this same pass is trying to kill. One card keeps one identity.

**Avoidances.** Do not copy the state buttons onto a second bar. Do not summarize. Do not use user text as the brief. Do not keep the 280 cap on a collapsed card. Do not enable View when there is no output.

**semantic-key:** PK-6711

# The state buttons stay one character wide and each gains a tooltip that says the full word.

File: `AetherisNotebookPanel.stateButton`.

Set the tooltip from the state.

- `I` means Included.
- `E` means Excluded.
- `P` means Input only.
- `O` means Output only.

The visible text stays the one character. Full words are not the labels. There is no second set of buttons.

The letters are the mnemonics the hotkeys already use. The tooltip is what the operator reads a month later.

**Truth.** Labels stay `I`, `E`, `P`, and `O`. Tooltips are Included, Excluded, Input only, and Output only.

**Reason.** Full words wrapped the crowded header. Tooltips carry the words without a second row of buttons.

**Avoidances.** Do not replace the letters with full words. Do not add a second copy of the buttons. Do not omit a tooltip.

**semantic-key:** PK-6712

# Input text is `#00FF66` on `#1A1A1A`, output and View are `#FFFF55`, and an excluded cell is gray in every area.

File: `AetherisNotebookPanel`, the text area helper and `showViewModal`.

1. User foreground `#00FF66`. Background stays `#1A1A1A`. User border stays `#3CB371`.
2. Assistant foreground `#FFFF55` when the cell has output. The empty-response border may stay the gray chrome it has now.
3. View modal foreground `#FFFF55` on `#1A1A1A`.
4. An excluded cell sets the user area, the assistant area, and the brief to gray after the other colors.

`#FFFF55` is the Swing stand-in for terminal bright yellow, SGR 93. The two greens stay two greens so the edge and the words are not the same color. This is paint. It does not touch the wire.

The previous field text `#E6E6E6` is replaced for input and for View. Ghost gray still wins over both.

**Truth.** Emerald input, medium-green border, bright-yellow output and View, and gray over all three areas when the cell is excluded.

**Reason.** The operator could not tell input from a light gray field, and output did not match the console yellow already used for replies.

**Avoidances.** Do not recolor the border to `#00FF66`. Do not let emerald or yellow beat gray on an excluded cell. Do not change the wire for a paint change.

**semantic-key:** PK-6713

# View remembers one global rectangle beside the user directory, and a rectangle that misses the screen opens at 720 by 480 centered.

Files: `AetherisNotebookPanel.showViewModal`. A new type `org.aetheris.swing.ViewBounds` is allowed, with `// @aa-sdd` and no lambdas.

1. The path is the user directory beside the vault folder: `~/.aetheris/{user}/view-bounds.txt`. Four lines or keys: `x`, `y`, `width`, and `height`.
2. RAM vault mode uses the same file. The bounds are not inside H2 and not in `aetheris.yaml`.
3. On dialog close, write the dialog bounds.
4. On open, use the saved rectangle when it intersects a current screen device. Otherwise `setSize(720, 480)` and `setLocationRelativeTo` the notebook pane.
5. A missing file, a short file, or a non-integer is the same fallback. Do not throw to the operator.

The dialog is born at 720 by 480, centered, when nothing valid is stored. The preference is global, not per turn, and not on `CellRecord`. One rectangle serves every cell's View, including after a restart.

**Truth.** One file of x, y, width, and height beside the user, restored when it hits a screen, otherwise 720 by 480 centered, with no throw on a bad file.

**Reason.** The operator resizes View for comfort and expects the next cell, and the next launch, to open there. A per-cell column would fork that preference.

**Avoidances.** Do not store the rectangle on the cell. Do not put it in the database or in `aetheris.yaml`. Do not throw a short file at the operator. Do not use an off-screen rectangle.

**semantic-key:** PK-6714

# A header rebuild restores the card's offset in the viewport and keeps focus on that card.

File: `AetherisNotebookPanel.rebuildTabs` and the header listeners that call it. That includes the state buttons and the new collapse toggle. The burner keeps its current path if it does not rebuild.

1. Before `rebuildTabs` from a header control, remember the card id, `card.getY()`, and the canvas scroll pane's view position Y.
2. After the new card is validated, set the view Y to `newCardY - (oldCardY - oldViewY)`.
3. Focus stays on that card. A collapsed card focuses the header toggle, not a hidden text area.
4. Do not use `scrollRectToVisible` of the whole card for this hold. That call pulls a tall card to the top.
5. New turn and the end of a run may still scroll to the new cell. Clear turns may scroll to the new empty turn.

The scroll jump today is the state button calling `persistQuiet` and then `rebuildTabs`. The rebuild throws away the scroll pane, the new viewport starts at the top, and a whole-card reveal pulls a tall card upward when a pending id exists.

View does not rebuild and does not need the hold.

**Truth.** The hold is `newCardY - (oldCardY - oldViewY)`, focus stays on the card, and a whole-card reveal is not the hold.

**Reason.** The operator clicked a state button and the pane jumped to the top. The click should leave the card where the eye already was.

**Avoidances.** Do not scroll the whole card into view for a header click. Do not focus a hidden text area on a collapsed card. Do not arm this hold on New turn, on the end of a run, or on Clear turns.

**semantic-key:** PK-6715

# A right-click `Import...` replaces that cell's user text from one UTF-8 file, refuses output-only and a NUL, and caps the file at 1 MiB.

File: `AetherisNotebookPanel.CellCard`.

1. The popup opens on mouse press when `isPopupTrigger`. One item, `Import...`. No header button.
2. The cell under the pointer is the target.
3. An output-only cell refuses with a status line. No chooser.
4. A press whose source is the assistant area refuses. The file never writes `assistantText`.
5. `JFileChooser` stays on the event thread. The read runs on `AetherisWorker`.
6. Refuse when `file.length()` is greater than 1 MiB, before reading. The bytes are UTF-8. A NUL in the first 8 KiB refuses and does not replace text.
7. Success replaces `userText`, updates the area if the card is open, and calls `persistQuiet`. The state is unchanged. There is no `runTarget`. There is no mux frame.

No focused input cell refuses, with a status line. The cell state does not change. The import does not push the pipe.

1 MiB is 1,048,576 bytes. The check is the file length before the read, not a partial load.

**Truth.** Right-click import replaces user text only, on the worker, with a 1 MiB cap, a NUL check in the first 8 KiB, and no execute.

**Reason.** A header button would crowd the card. The assistant area is not a destination for a file. A NUL or an oversized file must fail before it becomes the prompt.

**Avoidances.** Do not put Import on the header. Do not write `assistantText`. Do not open the chooser for an output-only cell. Do not read a file over 1 MiB. Do not run the cell. Do not change the state.

**semantic-key:** PK-6716

# These usability tweaks are built from this plan, on the notebook that already stands, and the operator terminal still drives.

This plan is the build contract for the first usability pass on the notebook that Stage 1 already shipped. The blueprint is frozen. The Stage 1 plan is frozen and is not edited. This turn of the plan does not itself move Java. Java waits until the build is named.

The sentence that binds the pass is this. The notebook already stands. These are usability tweaks. The operator terminal still drives. There is one pipe. There are four context states. The next stage stays closed.

The eight behavior rows are locked from the blueprint. Project types take no Lombok and no lambdas. A trace comment `// @aa-sdd` lands on a new type only. The client class stays `org.casserole.app.ui.ssh.SshClientApp`.

Shell launch stays `java -cp` plus the local Maven classpath helper. Never `exec:java`. The local engine, when a cell runs, remains `http://localhost:11434`. The code model default remains `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key is added.

**Truth.** The notebook already runs. This plan only changes collapse, tooltips, color, view bounds, focus, import, and clear, and it does not open the next stage.

**Reason.** The operator asked for usability on the canvas that exists, without a fifth state and without a second product.

**Avoidances.** Do not edit the frozen Stage 1 plan. Do not add Lombok or lambdas. Do not rename the client class. Do not launch with `exec:java`. Do not open the next stage.

**semantic-key:** PK-6717

# After Run the caret is in the new turn with the answer still on screen, an exclude updates the inspector immediately, and the tab follows the focused user.

The goal has three parts.

- After Run, the caret is in the new turn and the answer is still on screen.
- The inspector matches the chain the next Run would send, and an `E` click updates it immediately.
- The notebook tab shows the notebook of whichever connected user owns the focused terminal session, and the other user's turns are still there when that session is focused again.

Not this plan: a second notebook product tab, a blueprint, a fifth context state, `select model`, `list models`, a cloud adapter, the next stage, a RAM session cache, an edit to the frozen plans, and a rewrite of the filed code contract.

The tree's notebook yaml is disk, so the other user's vault remains at `~/.aetheris/{user}/vault`. This plan does not change that mode.

**Truth.** Caret in the new input, inspector equal to the next send, and one tab that follows the focused connected user.

**Reason.** The operator was scrolling to type, staring at an inspector that still listed an excluded cell, and losing the other user's canvas when a second terminal connected.

**Avoidances.** Do not build a second product tab. Do not add a RAM cache. Do not rewrite the filed contract. Do not open the next stage.

**semantic-key:** PK-6718

# The Run land and the inspector are the notebook panel, the focused console is the shell, and the hand check is after all three.

1. Run lands on the next input.
2. The inspector shows the chain.
3. The focused console owns the notebook.
4. A manual check.

The first two are the notebook panel. The third is the shell session focus. The check is after the three. No new test framework. No unit-test task.

Nothing in this order reopens collapse, color, import, or clear.

**Truth.** Two notebook edits, one shell focus edit, then a hand check, with no new test framework.

**Reason.** The focus swap is a different class from the Run land, so it is its own slice, and the check waits until both exist.

**Avoidances.** Do not add a unit-test task. Do not interleave a rewrite of the filed build.

**semantic-key:** PK-6719

# A finished Run on the last cell puts the caret in the new turn's input and scrolls only enough to show that user area.

File: `org.aetheris.swing.AetherisNotebookPanel`. Methods `applyAssistant`, `newTurn`, and `scrollPendingFocus`.

Today a finished response on the last cell sets a pending focus on that cell and marks the pending focus as the assistant, then calls `newTurn`. `newTurn` leaves an existing pending focus alone, so the caret stays in the answer.

1. When the answered cell is the last cell, do not keep focus on that assistant. The new turn becomes the pending focus, and the pending focus is the input.
2. After layout, scroll the least that shows the new cell's user area. Call `scrollRectToVisible` on that user area. Do not call it on the whole card. The whole-card call pulls a tall card to the top.
3. The answered card is the card above the new turn. The minimal scroll leaves it in the viewport when the pane has the room. When the pane is shorter than both cards, the new input stays visible and the answer sits against it from above.
4. A response on a cell that is not last still focuses that cell's assistant. Run uses the last cell, so Run takes the new-turn path.
5. The header focus-hold stays as it is. A state click still does not jump the scroll. Clear turns and New turn may still scroll to the empty turn they create.
6. Do not arm the hold on this path.

The answer stays above the new input. The operator does not have to scroll before typing.

**Truth.** Last-cell Run focuses the new input and reveals that user area, not the whole card, leaving the answer above it when the pane has room.

**Reason.** The caret was stuck in the answer because the new turn refused to replace a pending focus that was already set.

**Avoidances.** Do not keep focus on the assistant of the last cell. Do not reveal the whole card. Do not arm the header hold on this path. Do not change a mid-list response that is not a Run on the last cell.

**semantic-key:** PK-6720

# The inspector lists only cells that would be sent, an excluded last cell is absent, and a state click refreshes the list in that click.

File: `AetherisNotebookPanel`. Methods `assemble`, `stateButton`, and the Ctrl+Shift+I, Ctrl+Shift+E, Ctrl+Shift+P, and Ctrl+Shift+O path in `installHotkeys`.

`appendCell` already skips excluded cells and does not read `collapsed`. Leave it.

1. `assemble` does not add the target user turn when the target state is excluded.
2. Included and input-only still contribute the target's user text. That text is the prompt Run sends. Prior output-only cells still contribute assistant text. Prior input-only cells still contribute user text. A collapsed included cell stays on the chain.
3. `stateButton` and the hotkey path call `refreshInspector` on the event thread in the same click, after the state is written. The list changes before the next Preview.
4. The excluded card stays on the canvas. Gray stays gray.
5. An empty chain still names the target id and shows an estimate of 0. Do not invent a second inspector screen.

Today the exclude click does not refresh the inspector, so the old list stays until the next Preview, Run, or new turn. The last cell's user text is inserted even when the last cell is excluded. Both of those stop.

`I`, `P`, and `O` stay the active half of a cell. The card on the canvas stays visible.

**Truth.** Assemble omits an excluded target, state and hotkey refresh the inspector in the same click, and an empty chain still shows the id and an estimate of 0.

**Reason.** The inspector is the chain about to be sent. A stale list after `E` shows memory the next Run will not include.

**Avoidances.** Do not remove the card from the canvas. Do not drop a collapsed included cell. Do not wait for Preview to refresh. Do not add a second inspector. Do not read `collapsed` inside `appendCell`.

**semantic-key:** PK-6721

# Three small moves follow this plan: Run lands on the next input, the inspector shows the chain, and the focused console owns the notebook.

This plan is the build contract for three small moves on the notebook that already stands. A blueprint was waived because the items are small. The previous tweak plan and the Stage 1 plan stay frozen and are not edited. The code contract of the filed build is not a slice of this plan and is not reopened.

The sentence that binds the plan is this. The notebook already stands. Three small moves. One tab. One pipe. Four context states. The next stage stays closed.

No Lombok. No lambdas in project types. No new type, so no new trace comment. The class stays `org.casserole.app.ui.ssh.SshClientApp`. Shell launch stays `java -cp` plus the local Maven classpath helper. Never `exec:java`.

This document does not itself move Java. Java waits until the build is named. The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`.

**Truth.** Three moves and a hand check, one tab, one pipe, four states, no new type, and no next stage.

**Reason.** The operator wanted the caret in the next input, the inspector to match the chain, and the notebook to follow the focused terminal user.

**Avoidances.** Do not add a second notebook tab. Do not add a type. Do not reopen the filed usability build. Do not edit the frozen plans. Do not use `exec:java`.

**semantic-key:** PK-6722

# A twelve-hyphen rule sits between contributing cards, an included card keeps both role lines together, and the inspector stays one text area.

File: `org.aetheris.swing.AetherisNotebookPanel`, method `refreshInspector`.

`assemble` flattens roles and drops the cell boundary. Do not insert a rule after every two lines of that flat list.

1. Walk the same cells `assemble` walks, in that order. For each cell that contributes text, write its role lines, then a rule before the next contributing cell.
2. Included writes the user line and the assistant line, with no rule between them. Input-only writes the user line. Output-only writes the assistant line. Excluded writes nothing and gets no rule.
3. The rule is twelve hyphens on their own line: `------------`. Soft wrap leaves that on one row.
4. The header `working set → id` stays first. `est. tokens` stays last. Neither is wrapped in a rule.
5. One contributing cell produces no rule. A later state click still calls `refreshInspector`, and the rules close up around the cells that remain.
6. The inspector stays one `JTextArea`.

A line between every two roles would cut an included pair in half. The rule follows the card, not the role.

**Truth.** Twelve hyphens between cards, both roles of an included card stay together, header and token line stay outside, and the area stays one text area.

**Reason.** The operator asked for a divider between turns. A rule between roles would split the pair the card is.

**Avoidances.** Do not rule a flat role list. Do not paint a second inspector. Do not put a rule around the header or `est. tokens`. Do not draw a rule for a single card.

**semantic-key:** PK-6723

# While the spinner turns, the base and the word `thinking` are `#4169E1` and the moving glyph is `#00FF66` at 120 milliseconds.

File: `AetherisNotebookPanel.ThinkSpinner`.

1. While spinning, the label base is `#4169E1`. Each 120 ms frame paints the moving glyph in `#00FF66` and the word `thinking` in `#4169E1`. The frames stay `|`, `/`, `-`, and `\`.
2. One label can carry both colors. Set the text after `applyLabel`, on each tick, so the theme foreground does not win.
3. `stopSpin` clears the text and hides the label, as it does now.
4. The cell border stays `#3CB371`. The input emerald stays `#00FF66`.

`#4169E1` is the bright royal blue this plan names. The spec had no hex until this lock. The word does not swap to emerald. The glyph does.

The label is hidden while the run is idle. The spin sets its own color on each frame.

**Truth.** Royal blue `#4169E1` for the base and the word, emerald `#00FF66` for the glyph, 120 ms frames, hidden when idle.

**Reason.** The spinner was easy to miss. Interleaving the input emerald makes the motion readable without recoloring the word or the cell border.

**Avoidances.** Do not recolor the word to emerald. Do not change the cell border. Do not let the theme foreground overwrite the spin colors. Do not change the four frames.

**semantic-key:** PK-6724

# A reusable incrementor on the west button bar sets both open panes' row count, minimum 1, no ceiling, restored from `ui.aetheris.view.rows`.

New type: `org.aetheris.swing.Incrementor`. The first line of the type is `// @aa-sdd`. No lambdas. A listener interface with one method, implemented by an anonymous class.

Files: `Incrementor`, `AetherisNotebookPanel` (`buildFooter`, `CellCard`, `applyUiFont`), and `SshClientProperties`.

1. The widget is `+`, `-`, and an editable integer. It sits in the west `FlowLayout`, with Run, New turn, Clear turns, and Preview.
2. `+` and `-` change the count by one and apply immediately. They stop a pending type timer. The minimum is 1. Minus at 1 stays 1.
3. A typed value uses a non-repeating Swing timer of 800 ms, restarted on each edit. When it fires, apply. Empty and non-numeric leave the last good value in the field. A number below 1 applies as 1.
4. There is no ceiling.
5. Store the integer on `SshClientProperties` in `~/.casserole/ssh-client.yaml`, key `ui.aetheris.view.rows`. A missing key means 4. Not a vault column. Not `application.yaml`. Switching vault users does not change it. Chat-memory sync does not carry it.
6. Both open panes use the count. Set each open area's row count, and set the preferred and maximum height of each open pane from the row count times the current line height. The open card's maximum height becomes the header plus those two panes plus the divider and the borders. Remove the open-card cap of 280. Text past the visible rows scrolls inside the pane.
7. The collapsed brief stays one line, or two when the header is at least two line-heights tall. The inspector and the View modal do not take the count.
8. `applyUiFont` recomputes the pixel height from the saved row count.
9. Load the count before the first canvas build, and restore it into the widget.

800 ms is the 0.8 second settle. The saved number is rows, so a font-size change recomputes pixels. The footer incrementor is the row control. It is not the later column control.

**Truth.** Plus, minus, and an 800 ms typed value set a row count of at least 1 with no ceiling, key `ui.aetheris.view.rows`, default 4, and the 280 cap is removed.

**Reason.** The open card was clipped at 280, so extra rows never appeared. Saving rows rather than pixels keeps the height honest when the font changes.

**Avoidances.** Do not cap the rows. Do not store the count on the cell. Do not grow the brief, the inspector, or View from this number. Do not put the key in `application.yaml`. Do not change the count when the vault user changes.

**semantic-key:** PK-6725

# After Run the canvas viewport goes to its maximum Y and the new turn's input takes focus, while New turn and Clear turns still show the top of their card.

File: `AetherisNotebookPanel`, method `scrollPendingFocus`. The canvas scroll pane is the one `scrollOf` already finds.

1. On the land path, when `pendingLandOnInput` is set, after `validate()`, set that viewport's view position Y to the maximum, `viewHeight - extentHeight`, floored at 0. Then `focusPrompt()` on the new card.
2. Do not call `scrollRectToVisible` on the card or on the user area for this path. That was the previous land behavior this package replaces.
3. When the new card fits in the viewport, the end position shows the whole card. When the card is taller, the bottom stays in view.
4. A collapsed card with no user area still moves the viewport to the end.
5. New turn and Clear turns keep the whole-card scroll they have now.
6. `rebuildTabs` still clears `pendingLandOnInput` when a hold is armed, then calls `restoreHold`. Header, collapse, and `I`, `E`, `P`, and `O` keep that path.

The row count is applied before that scroll, so the end of the pane includes the height the incrementor just set. If the two actions meet and the operator must scroll again, that is accepted. Do not special-case the intersection.

The end scroll is the Run land only.

**Truth.** Run sets view Y to the maximum floored at 0 and focuses the new input. New turn, Clear turns, and header clicks do not take that path.

**Reason.** Scrolling the user area left the new card partly below the fold. The operator asked for the end of the pane so the new card is fully exposed when it fits.

**Avoidances.** Do not reveal the user area or the whole card on the Run land. Do not send New turn or Clear turns to the end. Do not let a header click set the land flag. Do not engineer a joint between row height and the land.

**semantic-key:** PK-6726

# The inspector paints newest first, the pull-down stays on the outer east, notebook buttons become 60 percent of today's height, and soft wrap tracks the inner viewport.

These locks are closed.

- Inspector cards are newest first. The header `working set → id` stays first. `est. tokens` stays last. Twelve hyphens sit between cards. Inside an included card the user line still precedes the assistant line. Numbers follow the painted list, so the top line is 1.
- `assemble` stays oldest first. A collapsed included cell still enters the working set. `E` is the way out of context.
- The Run land stays the end-scroll from the previous plan. New turn and Clear turns stay whole-card. Header, collapse, and `I`, `E`, `P`, and `O` stay on the delta hold.
- The east capsule puts `ThinkSpinner` on `BorderLayout.WEST` and the user combo on `BorderLayout.EAST`. The capsule stays `BorderLayout.EAST` on the footer. The west button flow stays.
- Choosing a name still selects that connected terminal session. The mux stays up. The same username does not reload. This package only swaps the two children.
- Notebook buttons become 60 percent of today's height. Their font shrinks to fit, floored at 8 pt, the floor the font setter already uses. `applyButton` adds a raised bevel on every button it paints. Fill colors stay. Shell buttons keep their height.
- Every Swing scrollbar shows an arrow button at each end. Face `#C0C0C0`. Arrow `#000000`. The terminal widget's bar stays out.
- One `SoftWrapArea`. It tracks the inner inspector viewport. Soft wrap starts on. The existing `soft wrap` checkbox is the switch and is not saved. Wrap is on words. The hyphen rule and the UUID stay one row at a normal width. Cell panes and the View modal stay later seats.
- While wrap is on, the inspector's horizontal bar is absent. The arrow buttons return on that bar when wrap is off. The vertical bar stays.
- The starter-kit note is held. No kit. The frozen starter stays frozen.
- `select model` and `list models` stay unwired.

The previous plan's hand check stays unclicked. This plan does not pretend those clicks happened.

**Truth.** Newest-first paint, oldest-first send, swapped capsule children, 60 percent height with an 8 pt floor, light-gray arrows, and a viewport-tracking wrap that is not saved.

**Reason.** Showing the spinner was shoving the pull-down left. The wrap flag was already on and the area still sized to the longest line. Both are locked here as paint, not as a new pipe.

**Avoidances.** Do not change `assemble`. Do not save the checkbox. Do not cut the shell button height. Do not touch the terminal's own scrollbar. Do not move cell panes or View onto the handler in this plan.

**semantic-key:** PK-6727

# After a named build the newest card is first, the pull-down stays east, notebook buttons are shorter and beveled, and the inspector wraps to the pane.

After a later named build, the goal is all of this.

- The inspector lists the newest card first.
- The pull-down stays on the outer east corner while the spinner sits on the west of that capsule.
- Notebook buttons are 40 percent shorter, which is 60 percent of today's height, and every `applyButton` target has a raised bevel.
- Swing scrollbar arrows show light gray with a black arrow at each end.
- The inspector wraps to the pane width while soft wrap is on.

Not this plan: Java on the approval turn, a change to `assemble`, character wrap, moving the open cell panes or the View modal onto the handler, a saved off-state for the wrap checkbox, the terminal's own scrollbar, a shell-button height cut, the starter kit, the next stage, a fifth cell state, a cloud adapter, an edit to the prior plans or to the locked pads, and a hand check claimed without the clicks.

Forty percent shorter and sixty percent of today's height are the same cut.

**Truth.** Five visible results after a named build, and no Java on the stamp itself.

**Reason.** The operator wanted the newest turn at the top, a stable pull-down, shorter buttons that still read as buttons, visible arrows, and a wrap that follows the divider.

**Avoidances.** Do not implement on the approval turn. Do not enable character wrap. Do not save an off state. Do not claim unclicked checks.

**semantic-key:** PK-6728

# The capsule swap is first, the soft-wrap handler is last, and the five packages wait until the build is named.

1. Spinner on the west, pull-down on the east.
2. Short notebook buttons, and a bevel on every button.
3. Scrollbar arrows.
4. Inspector newest first.
5. Soft-wrap area on the inner inspector.

The soft-wrap package is last so the handler wraps the reversed text and leaves the horizontal arrows for the wrap-off state. The starter-kit note is not in this order.

The approval turn stamps paper only. The five packages wait until the build is named. When it is, compile with `mvn -q -DskipTests compile` from the project root. No unit-test task. No hand check unless the clicks are asked for.

**Truth.** Five packages, wrap last, paper on approval, compile only after the build is named.

**Reason.** Wrapping before the reverse would test the handler against the old order. Arrows must exist before the wrap-off state is asked to show them.

**Avoidances.** Do not build on the stamp. Do not put the kit in the order. Do not add a unit-test task. Do not stage the host yaml or the IDE workspace.

**semantic-key:** PK-6729

# The spinner sits on the west of the east capsule and the user combo stays pinned to the east edge, preferred width 160 and maximum 180.

File: `org.aetheris.swing.AetherisNotebookPanel`, `buildFooter`.

Today the east capsule is a `BorderLayout` with an 8 px horizontal gap. The combo is west. The spinner is east. Showing the spinner widens that east child, the capsule grows, and the pull-down shifts left.

1. Add the spinner with `east.add(thinkSpinner, BorderLayout.WEST)`.
2. Add the combo with `east.add(userCombo, BorderLayout.EAST)`.
3. The capsule stays `footer.add(east, BorderLayout.EAST)`.
4. The west flow stays Run, New turn, Clear turns, Preview, the incrementor, and the footer status.
5. Keep the combo preferred width 160 and maximum width 180, and keep the tooltip as the full name. The adjusting guard stays. Choosing a name still calls the host focus for that connected user.
6. A hidden spinner is skipped by `BorderLayout`, so the combo alone sits on the outer east corner. A visible spinner takes its preferred width on the west of the capsule. The capsule grows leftward. The combo stays pinned to the east edge.

The mux stays up. The same username does not reload. Only the two children swap.

**Truth.** Spinner west, combo east, capsule still footer-east, combo 160 preferred and 180 maximum, hidden spinner leaves the combo on the outer corner.

**Reason.** The old east child grew when the spinner appeared and pushed the names left. Pinning the combo to the east edge stops that shove.

**Avoidances.** Do not move the west button flow. Do not drop the width caps or the full-name tooltip. Do not reload the vault for a same-user choice. Do not change the 8 px gap unless the layout already carries it as the capsule gap.

**semantic-key:** PK-6730

# The short set is fitted to 60 percent of today's height and the font steps down to no smaller than 8 pt.

The short set is Run, New turn, Clear turns, Preview, the incrementor `+` and `-`, the card buttons (`I`, `E`, `P`, `O`, collapse, and View), and the View modal Close. The incrementor field, the `soft wrap` checkbox, and the shell buttons are not in this set.

`fitAetherisButton(AbstractButton)` lives on the notebook panel.

1. Mark the button with client property `aetheris.short`.
2. Set its font to the live font size and apply the pre-slice flat border, a line plus `EmptyBorder(4, 10, 4, 10)`. Read that preferred height. That reading is today's height, H.
3. Target height is `max(1, round(H * 0.6))`.
4. Call `applyButton`, which installs the bevel.
5. Step the font down from the live size until the preferred height is at most the target, and stop at 8 pt.
6. Set preferred size to the fitted label width and the target height. Set maximum height to the target. Do not freeze the width from the tall font.

`applyUiFont` calls `applyFontTree` and then refits every marked button still in the tree, so a shell font change does not restore the tall notebook buttons. Card buttons are recreated by `rebuildTabs`, so construction calls the fitter too. The View modal is modal, so Close is fitted when the dialog opens. A font change while that dialog is up is out of this slice.

Sixty percent of today's height is the 40 percent reduction. The 8 pt floor is the floor the font setter already uses.

**Truth.** Target height is 60 percent of the flat-border height, floored at 1, font floored at 8 pt, marked `aetheris.short`, and refit after a font change.

**Reason.** Measuring H before the bevel keeps the percentage honest against the buttons the operator called too big. Freezing the tall width would leave a short label in a wide button.

**Avoidances.** Do not shorten the incrementor field, the soft-wrap checkbox, or the shell buttons. Do not go below 8 pt. Do not freeze the width from the tall font. Do not refit a View dialog that is already open when the font changes.

**semantic-key:** PK-6731

# The inspector reverses card blocks so the newest contributing card is first, then numbers from 1, and the send order stays oldest first.

File: `AetherisNotebookPanel.refreshInspector`.

Today the walk is oldest first. Prior cells skip excluded and empty ephemeral cells. An included prior writes the user line, then the assistant line, when each text is present. Input-only writes the user line. Output-only writes the assistant line. The target skips excluded and always writes one user line. Numbering happens during that walk, so the oldest line is 1. The header is written, then the blocks with `------------` between them, then `est. tokens`.

1. Keep that collection, including who is skipped and which role lines a card contains.
2. Build each block without a display number. Token totals still accumulate during the walk. Order inside a block stays user then assistant.
3. Reverse the block list before paint. The target card, when it contributes, becomes the first block under the header. Older cards follow downward.
4. Then number. The first painted role line is `1`. The next line is `2`. A two-line included card keeps both numbers together, user then assistant.
5. Write `------------` only when the block index is greater than 0, after the reverse, so the rule still sits between cards and the first card has none above it. One contributing card still produces no rule.
6. Header first. `est. tokens` last. An early empty-canvas return stays.
7. Do not edit `assemble`, `appendCell`, or the canvas land path.

The list on screen reverses. The working set still sends oldest first. The canvas land stays as built. One text area.

**Truth.** Collect as today, reverse the blocks, number after the reverse so the top line is 1, and leave send order oldest first.

**Reason.** The operator wanted the newest turn at the top so the list does not have to chase the bottom. The model still needs oldest first or the conversation runs backward.

**Avoidances.** Do not number before the reverse. Do not put a rule above the first card. Do not reverse `assemble`. Do not split an included card's user and assistant lines. Do not drop the empty-canvas return.

**semantic-key:** PK-6732

# While wrap is on the area tracks the viewport width, and a viewport resize reflows the text.

1. `setSoftWrap(boolean)` stores the flag, calls `setLineWrap` and `setWrapStyleWord` with that flag, and revalidates.
2. `getScrollableTracksViewportWidth` returns the flag.
3. While the flag is on, `getPreferredSize` uses the parent viewport's width when that width is positive, and the superclass height. That is what lets a divider move shrink the column. While the flag is off, `getPreferredSize` is the superclass longest-line size.
4. A component listener on the inner viewport calls `revalidate` on resize. That covers a divider move, and `setSoftWrap` covers a toggle while the divider stays still.
5. A scrollbar policy alone is not the fix. The track, the preferred width, and the listener travel together.

The listener covers a toggle that happens while the divider stays still, and a divider move that happens while wrap stays on.

**Truth.** Track flag, preferred width from the viewport, and a resize listener, all three, or the column does not follow the divider.

**Reason.** The override that tracks the viewport is the part that makes the width stick. A policy alone can miss a toggle or a drag.

**Avoidances.** Do not return the longest-line width while wrap is on. Do not omit the listener. Do not omit the track flag.

**semantic-key:** PK-6733

# The outer inspector never shows a horizontal bar, and the inner bar appears only when wrap is off.

1. The outer inspector pane's horizontal policy is `HORIZONTAL_SCROLLBAR_NEVER` for the life of the pane, so the long line cannot open a second horizontal bar. Its vertical policy stays the default.
2. While wrap is on, the inner pane's horizontal policy is `HORIZONTAL_SCROLLBAR_NEVER`.
3. While wrap is off, the inner pane's horizontal policy is `HORIZONTAL_SCROLLBAR_AS_NEEDED`. The arrow buttons are already on that bar.
4. The inner vertical bar stays.

The vertical bar is not part of the wrap switch.

**Truth.** Outer horizontal never, inner horizontal never while wrapped and as-needed while not, vertical unchanged.

**Reason.** Two horizontal bars would appear if the outer pane still honored the long line. The arrows belong on the inner bar when wrap is off.

**Avoidances.** Do not hide the vertical bar. Do not let the outer pane open a horizontal bar. Do not show the inner horizontal bar while wrap is on.

**semantic-key:** PK-6734

# Soft wrap starts on, the checkbox is the only switch, and word wrap leaves the hyphen rule and the UUID as one token.

1. Launch state stays on. The checkbox writes the in-memory flag and calls `setSoftWrap`. Nothing writes that flag to `ssh-client.yaml` or the vault.
2. Wrap is on words. Do not enable character wrap. The twelve-hyphen rule has no spaces and stays one row at a normal inspector width. The UUID in the header has no spaces and stays one token. A very narrow pane may leave those tokens wider than the pane. Previews have spaces and fold.
3. `assemble` still sends oldest first. The painted list is still newest first. Wrap is paint on this one area.

The checkbox label stays `soft wrap`. The handler does not need a second name on the glass.

A normal width fits the twelve hyphens and the UUID. The previews reflow because they contain spaces.

**Truth.** Default on, not saved, word wrap, hyphen rule and UUID stay one token at a normal width, send order unchanged.

**Reason.** The operator did not ask to remember an off state. Character wrap would cut the hyphen rule and the UUID.

**Avoidances.** Do not save the checkbox. Do not enable character wrap. Do not change `assemble`. Do not rename the checkbox.

**semantic-key:** PK-6735

# A soft-wrap text area tracks the inner inspector viewport, starts on, wraps on words, and is not a saved preference.

New type: `org.aetheris.swing.SoftWrapArea` extends `JTextArea`. `// @aa-sdd` on that type. Anonymous listeners only.

The inspector is constructed as this area. The flag already starts true. The checkbox already starts checked and already says `soft wrap`. `setLineWrap(true)` and `setWrapStyleWord(true)` already run, and the area still reports the longest line, so the pane scrolls sideways when the divider moves.

`wrap` builds a header, the title plus `soft wrap`, and an inner scroll pane around the area, then an outer scroll pane around that panel. The handler attaches to the inner pane. The open cell panes and the View modal keep ordinary text areas with line wrap on. They are later seats.

A scrollbar policy alone is not the fix. The track, the preferred width, and the listener travel together.

**Truth.** One new text area on the inner inspector pane, default on, word wrap, checkbox not saved, cell panes and View left for later.

**Reason.** The flag and the line-wrap calls were already true, and the area still preferred the longest line, so the scroll pane honored that width.

**Avoidances.** Do not attach the handler to the outer pane. Do not move the cell panes or View in this plan. Do not save the checkbox. Do not treat a scrollbar policy as the whole fix.

**semantic-key:** PK-6736

# Five hand checks run only after a named build, and an unclicked check is not a pass.

No unit-test task. The approval turn does not click these. Do not claim them passed unless they were clicked. The previous plan's hand check stays unclicked.

1. With the spinner hidden, the user pull-down sits on the outer east corner. Start a Run. The spinner appears on its west and the pull-down stays on that corner.
2. Run, New turn, Clear turns, Preview, `+`, `-`, and the card buttons are visibly shorter. Shell Connect and the paste buttons keep their height. Every one of those buttons shows a raised bevel. The fills are unchanged.
3. The canvas, an open cell, the inspector, View, paste, and Help show a light-gray arrow button with a black arrow at each end of each Swing bar.
4. Several included cards: the newest block is under the header, older blocks follow, a hyphen rule sits between blocks, and an included block still shows user then assistant. The top line is numbered 1. `E` removes that card's lines on that click. The next Run still sends oldest first.
5. Soft wrap starts checked. Drag the inspector divider. Preview lines fold to the new width. The hyphen rule and the UUID stay one row at a normal width. Uncheck `soft wrap`. The long line returns and the horizontal bar, with the arrows, returns. Check it again. The bar leaves and the text folds, with the divider left where it is.

The arrow face is `#C0C0C0` and the arrow is `#000000`. The short buttons are 60 percent of the measured height, font no smaller than 8 pt.

**Truth.** Five clicks cover the capsule, the short beveled buttons, the arrows, newest-first numbering, and wrap on and off.

**Reason.** The stamp does not include the clicks. Claiming them would record a check that did not happen.

**Avoidances.** Do not click these on the approval turn. Do not claim them passed unseen. Do not mark the previous plan's check as clicked.

**semantic-key:** PK-6737

# Five paint and layout packages wait on this plan until the build is named, and the starter kit stays frozen.

This plan is the build contract for five paint and layout notes. Approval stamps the document. Java waits until the build is named in a later sentence. The starter-kit note is not in the build. Prior tweak plans stay frozen and are not edited. Do not open the next stage and do not rewrite that account's item table.

The sentence that binds the plan is this. The notebook already stands. Five paint and layout notes. One pipe. Four context states. The next stage stays closed. The frozen starter kit stays frozen.

No Lombok. No lambdas in project types. Anonymous classes only. `// @aa-sdd` lands on the one new type, `org.aetheris.swing.SoftWrapArea`, and only when a later build creates it. The class stays `org.casserole.app.ui.ssh.SshClientApp`. Shell launch stays `java -cp` plus the local Maven classpath helper. Never `exec:java`. Do not edit `src/main/resources/application.yaml`. Do not cut a starter kit.

The eight packages of the previous plan stay compiled. The hand check in that plan was not clicked. The earlier unrun session check stays closed. This plan does not reopen them.

The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`.

**Truth.** Five packages are specified and not built by the stamp. One new type is allowed later: the soft-wrap area.

**Reason.** The operator accepted the notes, including the soft-wrap solution, and did not name Java in the same sentence.

**Avoidances.** Do not write Java on the stamp. Do not cut a kit. Do not edit the prior plans. Do not open the next stage.

**semantic-key:** PK-6738

# Soft-wrap width is a saved character count, default 80, minimum 1, and the inner bar is as-needed in both states.

These locks are closed.

- The inspector soft-wrap width is a character count. Default 80. It is saved. While soft wrap is on, the inspector wraps at that count.
- A second incrementor sits beside the `soft wrap` checkbox. Minimum 1. Plus and minus apply immediately. A typed value waits 800 ms. The tooltip says `soft wrap columns`.
- The footer incrementor still sets card rows.
- The key is `ui.aetheris.inspector.wrap.columns` in `~/.casserole/ssh-client.yaml` through `SshClientProperties`. A missing key means 80. It is not written by the missing-key default fill. It is not a vault column. It is not `application.yaml`. Chat-memory sync does not carry it.
- The checkbox still starts on. It is not saved.
- While wrap is on, preferred width is the column count times the live mono character width. The viewport does not stretch it. While wrap is off, the longest line returns.
- The inner inspector bar is as-needed in both states. A column wider than the pane scrolls, and the arrow buttons sit on that bar. The outer bar stays hidden.
- Word wrap stays. The hyphen rule and the UUID stay one token. A full preview is 80 characters of body plus the `N. role` prefix, so at a width of 80 a long preview folds once. Raising the count keeps it on one row.
- Open cell panes and the View modal stay off this handler.
- `assemble` stays oldest first. The painted list stays newest first.
- The starter-kit note is held. No kit. The frozen starter stays frozen.
- `select model` and `list models` stay unwired.

The five packages of the previous plan stay. This plan does not reopen the earlier plans.

**Truth.** Default 80, key `ui.aetheris.inspector.wrap.columns`, 800 ms typed edit, inner bar as-needed, outer bar never, word wrap kept.

**Reason.** A pixel width goes stale when the font changes. The row control is already a character-shaped integer, and this width belongs with it, as a second control so the two are not mixed.

**Avoidances.** Do not save the checkbox. Do not track the viewport. Do not hide the inner bar while a wide column needs it. Do not character-wrap the hyphen rule or the UUID. Do not move the cell panes onto this handler.

**semantic-key:** PK-6739

# The inspector wraps at a saved character width from the control beside the checkbox, and approval confirms that build.

The goal is that the inspector wraps at a saved character width, default 80, from the control beside the checkbox. Approval stamps this plan and confirms that build.

Not this plan: a second implementation of a match, a saved checkbox, a pixel width, viewport tracking, character wrap, moving the cell panes or the View modal onto the handler, a change to `assemble`, the starter kit, the next stage, and a hand check claimed without the clicks.

A clean match does not get an empty second record of the same Java.

**Truth.** One saved character width, default 80, confirmed rather than rewritten if the tree already matches.

**Reason.** The operator asked for a plus-minus beside the checkbox because the pane width was not a stable column.

**Avoidances.** Do not store pixels. Do not track the viewport. Do not save the checkbox. Do not claim unclicked checks. Do not file an empty second change for a clean match.

**semantic-key:** PK-6740

# The only package is the inspector wrap columns, compiled from the project root, with no unit-test task.

One package: the inspector wrap columns.

The code that matches this plan is the candidate. A match stays. A gap would have been fixed on the approval turn. Compile with `mvn -q -DskipTests compile` from the project root. No unit-test task. No hand check unless the clicks are asked for.

There is no second package hiding in the checkbox or the footer row control.

**Reason.** The build was named on approval and the tree was already the candidate, so the slice is a confirmation plus a compile.

**Avoidances.** Do not open a second package. Do not add a unit-test task. Do not stage the host yaml or the IDE workspace.

**semantic-key:** PK-6741

# The soft-wrap area stops tracking the viewport, prefers the column count times the live character width, and saves `ui.aetheris.inspector.wrap.columns`.

Files: `org.aetheris.swing.SoftWrapArea`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.app.ui.ssh.SshClientProperties`, and `org.casserole.app.ui.ssh.SshClientApp`.

1. `SoftWrapArea.getScrollableTracksViewportWidth` returns false.
2. `setWrapColumns` stores at least 1. While wrap is on, `getPreferredSize` width is that count times `getColumnWidth`, plus insets. Height is the wrapped span at that width. While wrap is off, preferred size is the superclass longest line.
3. The wrap builder reads the host's shell wrap columns, floors a bad value at 80, and calls `setWrapColumns` before the scroll pane is shown.
4. The second incrementor is the next child after the `soft wrap` checkbox. Its listener calls `setWrapColumns` and the host change method. The `+` and `-` buttons go through the short-button fitter. The field is a text field. Tooltips say `soft wrap columns`.
5. The inner pane's horizontal policy is `HORIZONTAL_SCROLLBAR_AS_NEEDED`. The outer pane's horizontal policy is `HORIZONTAL_SCROLLBAR_NEVER`. The scrollbar painter still paints the arrow buttons.
6. `SshClientProperties` key `ui.aetheris.inspector.wrap.columns`. The getter returns 80 when the key is missing, and at least 1 otherwise. The setter writes the integer and saves. The missing-key default fill does not grow this key.
7. The host pair is the read of the column count and the notice that it changed. The footer row pair stays the card-row control.
8. The checkbox listener only calls `setSoftWrap`. It does not write the shell file.

Minimum 1. Plus and minus apply immediately. A typed value waits 800 ms. A full preview is 80 characters of body plus the numbered role prefix, so at 80 a long preview folds once. Raising the count is how that preview stays one row. The hyphen rule and the UUID stay one token because word wrap does not cut them.

**Truth.** Track returns false, width is columns times the live mono width, missing key is 80, inner bar as-needed, outer bar never, checkbox does not save.

**Reason.** Viewport tracking made the wrap edge whatever the pane was, so a divider move changed it and there was nothing to persist. A character count survives a font change.

**Avoidances.** Do not track the viewport. Do not write the checkbox into the shell file. Do not let the missing-key fill invent the column key. Do not hide the inner bar in both states. Do not point the footer incrementor at this key.


**semantic-key:** PK-4414

# Six hand checks cover the default of 80, a wider saved count, a cleared field, wrap off and on, and the separate row control.

No unit-test task. The approval turn does not click these. Do not claim them passed unless they were clicked. The two earlier hand-check lists stay unclicked.

1. Soft wrap starts checked. The control beside it reads 80. Long previews fold on the prefix. The hyphen rule and the UUID stay one row.
2. Press `+`. The column grows at once and the next launch still shows that count.
3. Type a number and wait. The column follows. Clear the field. The last good count returns.
4. Uncheck `soft wrap`. The long line returns and the horizontal bar can return. Check it again. The saved column returns.
5. Set the column wider than the inspector. The inner bar shows the light-gray arrows. Narrow the count under the pane. That bar leaves.
6. The footer incrementor still changes card rows. A font change keeps the same character count and retargets the pixel width.

The wait on a typed value is 800 ms. The arrow face on that inner bar is `#C0C0C0` with a `#000000` arrow. The tooltip is `soft wrap columns`.

**Truth.** Six clicks prove 80, immediate plus, the 800 ms type, restore after wrap off, the inner arrows, and the separate row control.

**Reason.** The approval confirmed the tree and did not click. The checks stay open until someone clicks them.

**Avoidances.** Do not claim the clicks on the approval turn. Do not mark the earlier lists clicked. Do not let the footer control change the column count.

**semantic-key:** PK-6742

# The inspector wraps at a saved character width, default 80, and approval of this plan confirms that build.

This plan is the build contract for the inspector column width. Approval names the build. A blueprint was waived. Prior tweak plans stay frozen and are not edited. Do not open the next stage and do not rewrite that account's item table.

The sentence that binds the plan is this. The notebook already stands. One column width on the inspector. One pipe. Four context states. The next stage stays closed. The frozen starter kit stays frozen.

No Lombok. No lambdas in project types. Anonymous classes only. No new type. `SoftWrapArea` already carries `// @aa-sdd`. The class stays `org.casserole.app.ui.ssh.SshClientApp`. Shell launch stays `java -cp` plus the local Maven classpath helper. Never `exec:java`. Do not edit `src/main/resources/application.yaml`. Do not cut a starter kit.

The hand check was not clicked on the approval turn. The public page does not name a commit. A match of the tree to this plan stays. A gap would have been fixed on the approval turn.

The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`.

**Truth.** One package, a saved column count defaulting to 80, confirmed by approval, with no new type.

**Reason.** Viewport tracking wrapped to the pane, so there was no width to align. A character count beside the checkbox is the fix the operator asked to build on approval.

**Avoidances.** Do not add a type. Do not cut a kit. Do not edit the prior plans. Do not open the next stage. Do not claim the hand check.

**semantic-key:** PK-6743

# The vault is compile-scoped, pure JDBC, RAM by default, and the hand parser grows four keys without a second yaml library.

- HikariCP and H2 at compile scope. Pure JDBC. Schema applied in-tree. Statements run on the shell worker.
- Default RAM url `jdbc:h2:mem:aetheris`. Disk path `~/.aetheris/{user}/vault`.
- Extend the hand parser. Spaces only. Ignore unknown keys.

Tables are `workspace`, `canvas`, `cell`, and `trace`. No scope table. A burner may store null large-text columns.

Keys are `vault.mode`, `vault.path`, `network.air-gap`, and `amwp.enabled`. A tab fails loudly.

The host does not get a Spring datasource. The host does not open this database.

**Truth.** Compile-scoped pool and database, four tables, RAM default, disk opt-in, hand-parsed keys, spaces only.

**Reason.** The shell is the only process that should hold the notebook. A host datasource would put notebook rows in the server process this stage kept free of that database.

**Avoidances.** Do not add a migration tool, a persistence framework, or a Spring datasource on the host. Do not accept tab indent. Do not add Lucene.

**semantic-key:** PK-6744

# Cells stay four-state, the inspector shows the working set and a token estimate, and assembly does not resolve a template token.

- Four-state modal cells, a ghost for excluded, and a burner pin. The inspector shows the working set and a token estimate.
- The assembly walk is the locked walk. There is no `{{cell}}` resolution.

The walk goes in cell order before the target. Skip excluded. Skip an empty burner. Input-only contributes user text. Output-only contributes assistant text. Included contributes both. Then append the target prompt.

Completing turn N materializes an empty turn N+1. Collapse is not a fifth state. The pin does not create a fifth state.

The ghost is paint. It is the excluded cell still on the canvas, not a second record.

**Truth.** Four states, ghost and pin, a token estimate beside the working set, and no template-token resolution.

**Reason.** A template token would invent a second assembly language beside the walk. The walk is the only assembly.

**Avoidances.** Do not add a fifth state. Do not resolve `{{cell}}`. Do not hide an excluded cell by deleting it. Do not skip the token estimate.

**semantic-key:** PK-6745

# A cell can collapse to a short output brief, the four letters have tooltips, and clear leaves the terminal session alone.

Small usability on the notebook pane that the first stage already shipped.

Done means all of this.

- A cell can collapse to its header plus a short output brief, and it comes back open with the same text.
- `I`, `E`, `P`, and `O` still mean the four states, and each has a tooltip.
- Input text is bright emerald.
- Output text is the console yellow.
- View reopens at the last size and place.
- A state click does not throw the scroll to the top.
- A right-click on a cell can import a text file into that cell's input.
- A footer button clears the canvas turns and leaves the terminal session alone.

Not done: `select model`, `list models`, a fifth cell state, pinned context, a cloud adapter, a mux change, a change to the working-set walk, a second row of state buttons, full-word labels, and an import button on the header.

Emerald is `#00FF66`. Console yellow is `#FFFF55`. The four tooltips are Included, Excluded, Input only, and Output only.

**Truth.** Done is collapse, tooltips, two colors, remembered View, a held scroll, right-click import, and a canvas-only clear.

**Reason.** The pass is usability on a notebook that already runs, not a new stage.

**Avoidances.** Do not change the working-set walk. Do not add a fifth state, full-word labels, a second button row, or a header import button. Do not wire the tabled model commands.

**semantic-key:** PK-6746

# The in-list stores a collapse boolean, keeps one card, and imports into user text with a 1 MiB cap.

- `collapsed` is a boolean on `CellRecord`, persisted. Default false. It is not a `CellState`.
- One card. Header buttons stay. The body hides. The brief is the assistant text, one line or two. Empty output leaves the brief empty.
- Tooltips on `I`, `E`, `P`, and `O`: Included, Excluded, Input only, Output only. Labels stay one character.
- Input foreground `#00FF66` on `#1A1A1A`. Border stays `#3CB371`. Output foreground `#FFFF55`. A ghost stays gray.
- One View bounds preference beside the user, not on the cell. Off-screen falls back to 720 by 480, centered.
- A header rebuild restores `cardY - viewY` and keeps focus on that cell.
- Right-click import into the cell's user text. Cap 1 MiB. No pipe push.
- Footer clear of the active canvas vault to one empty included turn. The terminal is untouched.

The collapse glyph in the plan is `+` when collapsed and `-` when open. This blueprint allows that glyph and allows the operator to rename it. The brief never falls back to user text.

**Truth.** Eight in-rows: flag, card, tooltips, colors, bounds, hold, import, and clear, with the hex values and the 720 by 480 fallback.

**Reason.** Each row retires a worse design: a fifth state, a second button set, full-word labels, and an import button.

**Avoidances.** Do not make collapse a state. Do not use user text as the brief. Do not call the terminal clear.

**semantic-key:** PK-6747

# Model listing stays unwired, collapse does not skip the working set, and the frozen Stage 1 papers stay unedited.

| Item | Why it is out |
|---|---|
| `select model` and `list models` | Unwired. `swap` and `ollama list` stay |
| A fifth state, or collapse skipping the working set | The later stage already locks four states. `E` remains the way out of context |
| A second copy of the state buttons | Retired. One set stays |
| Full-word button labels | Retired. The letters stay |
| An import button on the header | Clutter. The menu is the door |
| User text as the collapsed brief | Retired. The brief is the output |
| Terminal `clear` from the footer button | Different memory |
| Next-stage canvas, a cloud adapter, mux channel `0x0004`, a graph | Not this slice |
| Edits to the frozen Stage 1 blueprint or the frozen Stage 1 plan | Those stamps stay |

`lost models` was a typo for `list models` and waits with the rest of that ask.

**Truth.** Nine out-rows keep the tabled commands, the fifth state, the retired labels, and the frozen papers out of the slice.

**Reason.** Each out-row is an alternative the operator retired or a later stage that must not start here.

**Avoidances.** Do not wire `select model` or `list models`. Do not skip the working set because a cell is collapsed. Do not edit the frozen Stage 1 papers. Do not clear the terminal from the footer.

**semantic-key:** PK-6748

# Eight behaviors are in and the retired alternatives stay out.

The boundary is locked. The eight in-rows are the flag, the card, the tooltips, the colors, the view bounds, the focus hold, the import, and the clear. The out-rows are the commands that stay unwired, the fifth state, the second button copy, the full-word labels, the header import, user text as the brief, a terminal clear from the footer, next-stage canvas work, and edits to the frozen Stage 1 papers.

The plan must cover every in-row and none of the out-rows. Child rules state each row in full.

**Truth.** In and out are closed. Eight behaviors ship. The retired alternatives do not return.

**Reason.** The boundary is what stops a full-word label or a terminal clear from riding back in as a small extra.

**Avoidances.** Do not move an out-row onto the plan. Do not drop an in-row.

**semantic-key:** PK-6749

# The plan must remember that an existing disk table will not gain a column from create-if-absent, and that the open card is capped at 280.

| Surface | Today | This slice |
|---|---|---|
| `CellRecord` | No collapse flag | `collapsed`, default false |
| Cell table | No collapse column. Create-if-absent will not alter a disk vault that already exists | The definition includes the column. An idempotent alter adds it for a vault already on disk |
| Working set | Skips excluded. Sends input for input-only and included. Sends output for output-only and included | Unchanged. Do not read `collapsed` here |
| Card header | `I`, `E`, `P`, `O`, burner, View. No tooltips. A state click calls `persistQuiet` then `rebuildTabs` | Same buttons, plus tooltips, plus one collapse toggle. No second set |
| Card body | Vertical split, user over assistant. Max height 280. Text `#E6E6E6` on `#1A1A1A`. A ghost is gray | Open card keeps the split. A collapsed card hides the split and shows the brief. Colors move. Gray still wins |
| Rebuild | A new scroll pane. Revealing the whole card pulls a tall card to the top | Header actions hold the viewport delta |
| View | Always 720 by 480, centered on the panel. Foreground `#E6E6E6` | Restores saved bounds. Foreground `#FFFF55` |
| Footer | Run, New turn, Preview | Adds Clear turns |
| Import | None | A popup on the card |
| Terminal clear | Drops session turns | Not called by the footer |

The field background `#1A1A1A` stays. The user border `#3CB371` stays. Input moves from `#E6E6E6` to `#00FF66`.

**Truth.** Ten surfaces move from a known today, including the 280 cap, the `#E6E6E6` text, and a View that is always 720 by 480.

**Reason.** Create-if-absent is the trap. An old disk vault would silently keep the table without the flag.

**Avoidances.** Do not read `collapsed` in the working set. Do not call the terminal clear. Do not leave View at a hard 720 by 480 once a rectangle is saved. Do not rely on create-if-absent to alter a disk table.

**semantic-key:** PK-6750

# The column lands before the card, and the focus hold lands with the card because the toggle rebuilds.

The order is a suggestion, not a schedule. The column lands before the card, because a collapse that does not survive a rebuild is not the flag. The focus hold lands with the card, because the collapse toggle rebuilds the card.

The packages are the flag, the card, the tooltips, the colors, the view bounds, the focus hold, the import, the clear, and the hand check.

Tooltips and colors may follow the card. The bounds file, the import, and the clear are independent of that chain. The hand check is last.

**Truth.** Column before card, hold with the card, and the rest free to follow, with the hand check last.

**Reason.** The toggle persists and rebuilds. Without the column and the hold, the rebuild forgets the collapse and jumps the scroll.

**Avoidances.** Do not treat the order as a calendar that blocks an independent slice. Do not build the card before the flag can survive a rebuild.

**semantic-key:** PK-6751

# This blueprint is frozen, the notebook already stands, and the build is taken from the tweak plan rather than from this file.

This file is the frozen blueprint of the first usability pass. It is not the file a builder implements. The tweak plan is the implementation file. The stamped Stage 1 blueprint and the stamped Stage 1 plan stay frozen. This file does not edit them.

The sentence that binds the pass is this. The notebook already stands. These are usability tweaks on that notebook. The operator terminal still drives. There is one pipe. There are four context states. The next stage stays closed.

No Lombok. No lambdas in project types. `// @aa-sdd` on a new type only. The class stays `SshClientApp`.

Shell launch stays `java -cp`. The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key is added.

Do not implement from this file.

**Truth.** The blueprint is frozen. The plan implements it. Four context states stay four, and the next stage stays closed.

**Reason.** A blueprint that is edited during the build stops being the record of what was accepted.

**Avoidances.** Do not implement from this file. Do not edit the frozen Stage 1 blueprint or plan. Do not add a fifth state. Do not open the next stage.

**semantic-key:** PK-6752

# Clear turns deletes the active canvas and its trace rows and leaves one empty included turn with no confirm dialog.

- Footer button `Clear turns`, beside New turn.
- The active canvas only. Delete that canvas's cells. Delete trace rows for those cell ids even though nothing inserts traces yet.
- Insert one empty included turn, the same shape as a new cell. Save order.
- Do not call the terminal clear command. Do not touch the chat session.
- No confirm dialog unless one is explicitly added later. The status line names the canvas and says the turns are cleared.
- Burner cells are not kept.

The in-memory cell list and order become that one cell. The tabs rebuild and scroll to it.

**Truth.** One button, one canvas, traces deleted first, one empty included turn, no confirm, burners included.

**Reason.** The terminal clear and the canvas clear are different memories. A confirm was not asked for.

**Avoidances.** Do not call the terminal clear. Do not spare burners. Do not add a confirm. Do not clear every canvas. Do not leave trace rows behind.

**semantic-key:** PK-6753

# One header toggle hides the split, shows an assistant brief of one line or two, and leaves the open cap of 280 behind.

- One header toggle. The suggested label is `+` when collapsed and `-` when open. The tooltip is `Expand` or `Collapse`. The glyph may be renamed later. These are the glyphs that ship unless renamed.
- The toggle sets `cell.collapsed`, persists quietly, then rebuilds, and it uses the focus hold.
- Open: the split stays. Closed: the split is not added. A brief is added under the header.
- The brief is `assistantText`, plain, from the first character. One line. A second line only when the header row is tall enough for it. No summarizer. Empty output leaves the brief blank. User text is never the source.
- Brief color follows the color package, and gray wins when the cell is excluded.
- The collapsed card's maximum height is the header plus the brief, not 280.
- `I`, `E`, `P`, `O`, the burner, and View stay on that same header, visible while collapsed. View stays disabled when there is no output.

A collapsed included cell still enters the working set.

**Truth.** One toggle, assistant brief, one or two lines, collapsed height is header plus brief, and the state buttons stay visible.

**Reason.** Two panels swapping focus would add a second path to the scroll jump. One card keeps one identity.

**Avoidances.** Do not add a second set of state buttons. Do not summarize. Do not use user text. Do not apply the 280 cap to a collapsed card. Do not enable View with no output.

**semantic-key:** PK-6754

# Input is `#00FF66`, the border stays `#3CB371`, output and View are `#FFFF55`, and excluded stays gray.

- Input text `#00FF66`. Field background stays `#1A1A1A`. User border stays `#3CB371`.
- Assistant text `#FFFF55` when the cell has output. The empty-response border may stay the gray chrome it has now.
- View modal text `#FFFF55`.
- Excluded keeps both areas gray, including the brief.

`#FFFF55` stands in for terminal SGR 93. The previous body color `#E6E6E6` is what this slice replaces on input and on View.

**Truth.** Two greens and one yellow, with gray winning on an excluded cell including the brief.

**Reason.** The edge and the words must not be the same green, and the output must match the console.

**Avoidances.** Do not recolor the border to emerald. Do not let yellow beat gray. Do not change the empty-response border unless it already is the gray chrome.

**semantic-key:** PK-6755

# Before a header rebuild the pane remembers card Y and view Y, and after the rebuild the same delta is restored.

- Before a rebuild from a header control, remember the card id, the card's Y, and the viewport view Y.
- After the new card is laid out, set the view Y so `cardY - viewY` matches the remembered delta.
- Focus stays on that card. A collapsed card focuses the header, not a hidden text area.
- Revealing the whole card is not the hold. New turn and the end of a run may still scroll to the new cell. Clear turns may scroll to the new empty turn.

The plan's formula is `newCardY - (oldCardY - oldViewY)`. That is the same delta.

View does not rebuild and does not need the hold.

**Truth.** The delta `cardY - viewY` is restored, focus stays, and a whole-card reveal is refused for header clicks.

**Reason.** The rebuild throws away the scroll pane. Without the delta, the new viewport starts at the top.

**Avoidances.** Do not reveal the whole card for a header click. Do not focus a hidden area. Do not apply the hold to View.

**semantic-key:** PK-6756

# The card popup offers `Import...`, refuses output-only and the assistant area, and replaces user text under a 1 MiB cap.

- Popup trigger on the card. One item, `Import...`. No header button.
- The cell under the pointer is the target.
- Output-only refuses, status line, no dialog.
- A press on the assistant area refuses. The file never writes assistant text.
- The chooser stays on the event thread. The read runs on the shell worker. UTF-8. Length above 1 MiB refuses before the read. A NUL in the first 8 KiB refuses.
- Success replaces user text, updates the area, and persists quietly. The state is unchanged. No execute. No mux frame.

No focused input cell refuses, with a status line. 1 MiB is the file length, checked before the bytes are read.

**Truth.** One menu item, user text only, 1 MiB before the read, NUL in the first 8 KiB, no state change and no execute.

**Reason.** Too many header buttons was the clutter. The assistant area is not a file destination.

**Avoidances.** Do not add a header button. Do not write assistant text. Do not read past 1 MiB. Do not execute. Do not change the state.

**semantic-key:** PK-6757

# The later plan's check is a manual pass with no new test framework.

No new test framework. The later plan's check is a manual pass.

- Collapse a cell with output. The brief is the start of that output. Expand. The full text is still there.
- Collapse a cell with empty output. The brief is blank.
- Restart the shell on a disk vault. The collapsed cells are still collapsed.
- Run a collapsed included cell's neighbors and confirm the collapsed cell's output is still in the working set.
- Toggle `E` on a collapsed cell. It ghosts. The brief goes gray. It drops out of the working set because of `E`, not because of collapse.
- Hover the four letters and read the tooltips.
- Input reads emerald. Output and View read yellow.
- Move and resize View. Close. Open another cell's View. Same place and size.
- Drag that window off-screen, close, reopen. It comes back centered at 720 by 480.
- Click `I` on a cell that is not at the top. The cell stays at the same place in the pane.
- Right-click the input of a cell and import a short text file. The input is replaced. The output is not.
- Right-click an output-only cell. Import refuses.
- Import a file larger than 1 MiB. It refuses.
- Clear turns. One empty turn remains. The terminal history is still there.

The plan adds the old-vault case: a vault that predates the column opens with every cell expanded. That case is part of the pass.

**Truth.** Fourteen manual observations, including the old vault, the working set, the off-screen View, and the terminal history.

**Reason.** The blueprint refuses a plan that ships the paint and does not say how a person checks it.

**Avoidances.** Do not add a test framework. Do not drop the off-screen fallback or the 1 MiB refusal. Do not treat a gray brief on an excluded cell as optional.

**semantic-key:** PK-6758

# Each state letter keeps a one-character label and gains the matching tooltip.

- `I` is Included.
- `E` is Excluded.
- `P` is Input only.
- `O` is Output only.

The visible label stays the one character. Full words are not painted on the button.

**Truth.** Four tooltips, four one-character labels.

**Reason.** The letters are easy to forget. The tooltip is the reminder that does not widen the header.

**Avoidances.** Do not replace the letters with words. Do not omit a tooltip.

**semantic-key:** PK-6759

# View bounds are four keys in a text file beside the vault, and RAM mode still uses that file.

- The file sits beside the user directory, sibling of the vault folder: `~/.aetheris/{user}/view-bounds.txt`. Not a cell column. Not `aetheris.yaml`.
- Keys `x`, `y`, `width`, and `height`.
- Write them when the dialog closes, from the dialog's bounds.
- On open, use them when the rectangle intersects a current screen. Otherwise 720 by 480 centered on the notebook pane.
- RAM vault mode still uses this file. The bounds are not inside H2.

A missing file, a short file, or a non-integer is the same fallback. Do not throw to the operator. The plan centers with `setLocationRelativeTo` the notebook pane and `setSize(720, 480)`.

**Truth.** One text file of four keys, screen intersection required, otherwise 720 by 480, and the file is used even when the vault is RAM.

**Reason.** The bounds are chrome, not cell content, so they must survive a RAM vault that forgets every turn.

**Avoidances.** Do not store the rectangle in H2 or on the cell. Do not throw on a bad file. Do not open an off-screen rectangle.


**semantic-key:** PK-4417

# After a run the canvas sits at its end, the status names the user, and the divider is 11 pixels.

Usability on the notebook and the shell chrome around it. The requirements were checked by hand on the note pad and approved, including the final hand-check table of twelve rows.

Done means all of this.

- After Run, the canvas sits at its end and the new turn's input has focus.
- The frame status line names the focused connected user and keeps that name.
- Both file choosers remember one size and one folder.
- An incrementor on the west of the notebook button bar sets the viewable rows of open input and output panes, and that count comes back when the app opens.
- The inspector draws a short rule between cards.
- A pull-down of connected users sits in an east capsule with the thinking spinner and selects that terminal session.
- The spinner is royal blue `#4169E1` with an emerald glyph while it spins.
- Every split divider is 11 pixels, from one constant, and a sliver cannot replace a saved open connection panel.

Not done: a formal plan written from this file, Java written from this file, a product-kit cut, `select model`, `list models`, a fifth cell state, pinned context, a cloud adapter, mux channel `0x0004`, a change to the working-set walk, a painted inspector, a row ceiling, per-vault chooser memory, and crosstalk.

**Truth.** Done is end-of-pane after Run, a kept user name, one chooser memory, a row incrementor, a short inspector rule, an east user pull-down, a royal-blue spinner, and an 11 pixel divider.

**Reason.** The notebook already stands. These notes change how it is used. They do not open a new stage.

**Avoidances.** Do not treat a missing plan or missing Java in this file as permission to skip the stamped plan. Do not add a row ceiling, a painted inspector, or per-vault chooser memory.

**semantic-key:** PK-6760

# The nine in-rows cover the Run land, the status name, both choosers, the row count, the inspector rule, the held kit, the pull-down, the spinner, and the 11 pixel divider.

| Work | What was approved |
|---|---|
| End of the pane | After Run, scroll the active canvas to the end of the pane and focus the new turn's input. The hand checks that lock this are the Run-only land and the hold on header and state clicks. |
| User on the status line | The frame status line includes the focused connected user and keeps the name across later messages. |
| Chooser size and folder | One remembered size and one remembered folder for the terminal send-file chooser and the notebook import chooser. |
| Incrementor | A reusable incrementor on the west of the notebook button bar. Rows for both open panes. Restored at app open. No ceiling. One shell preference, not private per vault. |
| Inspector rule | A short text rule between cards in the one inspector area. |
| Product kit, held | The next kit records the interchange notes on the open serial, or where the operator directs. The frozen product stays frozen. |
| Connected-user pull-down | A pull-down of connected users. Choosing a name selects that terminal session. East capsule with the spinner. Same username is one entry and does not reload. An in-flight reply drops. No selection when focus is not a connected session that speaks the notebook pipe. |
| Thinking spinner | The spinner is `#4169E1`. The moving glyph steps `#00FF66`. The word `thinking` stays royal blue. |
| Divider width | Divider thickness is the constant 11. Open height guards the connection panel. |

The hand-check rows that approved these are the twelve readings: the blue hex, which part is emerald, Run-only end-scroll, hold instead of end-scroll, no row ceiling, west seat for the incrementor, one shell preference, chooser cancel and refuse rules, which status line carries the name, the pull-down selecting the session, an in-flight run, and an unfocused session.

**Truth.** Each in-row names the approved behavior, including `#4169E1`, `#00FF66`, the constant 11, and the west seat.

**Reason.** The notes were approved together, including the twelve hand-check readings.

**Avoidances.** Do not drop a row. Do not split the chooser memory per vault. Do not give the row count a ceiling.

**semantic-key:** PK-6761

# New turn and Clear turns still bring the top of the card into view, and a combo must not leave the terminal session behind.

| Item | Why it is out |
|---|---|
| A formal plan and Java taken from this file | Wait until the operator names each. The stamped plan already exists as the successor. |
| Cutting a new product kit | Held. The operator cuts it when ready. |
| Rewriting the frozen product | Frozen. |
| End-scroll on New turn and Clear turns | Those two still bring the top of that card into view. |
| End-scroll on a header click or `I` `E` `P` `O` | The hold keeps the operator's place. |
| A row ceiling | The minimum is 1. There is no maximum. |
| Per-vault row count or chooser memory | One shell preference. |
| A painted inspector | The inspector stays one text area. |
| A combo that swaps cards and leaves the terminal session alone | The pull-down selects the session. The mux stays up. |
| Crosstalk, or a copy of one user's cells into another vault | Messages stay put unless the operator says otherwise. |
| `select model`, `list models` | Unwired. |
| A fifth cell state | The later stage already locks four. |
| Next-stage rows, a cloud adapter, mux channel `0x0004` | Not this slice. The notes slide into those rows later. The stages paper records the fit. |
| An export door that uses the chooser memory | That door stays its own door. |

**Truth.** Out means the frozen kit, the other scrolls, a ceiling, a painted list, a card-only combo, and the tabled commands.

**Reason.** Each out-row is a reading the operator already settled, or a stage that stays closed.

**Avoidances.** Do not end-scroll New turn, Clear turns, or a state click. Do not paint the inspector. Do not copy cells across vaults. Do not cut a kit from this slice.

**semantic-key:** PK-6762

# Nine notes are in, and end-scroll stays on Run alone.

The boundary is the nine notes against the retired alternatives. Child sections state each row. The later plan covers every in-row and none of the out-rows.

In: end of the pane after Run, the user on the frame status line, one chooser size and folder, the row incrementor, a short inspector rule, a held product-kit note, the connected-user pull-down, the royal-blue spinner, and divider thickness 11.

Out includes a build from this file, a product-kit cut, rewriting the frozen product, end-scroll on New turn or Clear turns, end-scroll on a header or state click, a row ceiling, per-vault row or chooser memory, a painted inspector, a combo that swaps cards without selecting the terminal session, crosstalk, the tabled model commands, a fifth cell state, next-stage rows, a cloud adapter, and mux channel `0x0004`.

**Truth.** Nine notes are in. The out-list is closed with them.

**Reason.** The boundary is what stops New turn, Clear turns, and a state click from inheriting the Run land.

**Avoidances.** Do not move an out-row onto the plan. Do not drop an in-row.

**semantic-key:** PK-6763

# Swing stays on the event thread, vault writes stay on the notebook worker, and a user switch drops the in-flight frame.

Swing stays on the event dispatch thread. Vault writes and import reads stay on `AetherisWorker`. The file chooser stays on the event dispatch thread. No new execution lane is added. No notebook frame is sent on import.

A user switch during a run clears the listener, drops the in-flight frame, and stops the spinner. The session stays connected.

**Truth.** The event thread paints. The worker touches the vault and the import bytes. A switch drops the frame and keeps the session.

**Reason.** Import and chooser work must not move onto a host lane or onto the event thread's opposite.

**Avoidances.** Do not open a new lane. Do not send a notebook frame on import. Do not disconnect the session when the user changes mid-run.


**semantic-key:** PK-4418 PK-4419

# The Run land still scrolls the user area, the divider is still 8, and the open card is still capped at 280.

| Surface | Today | This slice |
|---|---|---|
| Run land | The pending land scrolls the user area of the new card into view. | The active canvas viewport goes to its maximum Y. The new input takes focus. |
| New turn, Clear turns | Whole-card scroll, top of that card. | Unchanged. |
| Header hold | The delta is `cardY - viewY`, restored after rebuild. The hold clears the pending land. | Unchanged. Collapse and `I` `E` `P` `O` keep using it. |
| Frame status | One label. Connected, Connecting, Disconnected, and notebook messages replace the whole string. | The same bar also names the focused connected user. A later message keeps the name. |
| Notebook footer label | `vault: disk` or `vault: ram`, in the west flow. | Stays the vault mode. It does not become the user name. |
| Choosers | A fresh `JFileChooser` in the send-file path and in the import path. No memory. | One size and one folder, restored. `showOpenDialog` has already disposed the dialog when it returns, so the bounds come from the dialog window. |
| Open card | 4 rows. Maximum height 280. `setRows` does not cap a filled area. | The row count sets the card height. Text past the rows scrolls inside the pane. |
| Collapsed brief | One line, or two when the header is tall enough. | Unchanged. The incrementor does not grow the brief. |
| Inspector | One text area. Header, one line per role, `est. tokens`. | A short rule between cards. An included card keeps two role lines together. Input-only and output-only keep one line. Excluded cards stay out, and the rules close up. |
| Footer east | The thinking spinner alone. | A border panel. Pull-down on its west. Spinner on its east. That panel is the footer's east. |
| Spinner | Glyph plus ` thinking`, theme label color, 120 ms, hidden until a run. | Royal blue `#4169E1` while spinning. Glyph steps `#00FF66`. The word stays royal blue. Hidden when idle. |
| Divider | The split helper sets size 8. Collapse treats a location above 8 as expanded. | Size 11 from one constant, set after the split UI is installed. The literal 8 drops out. |
| Vault switch | The focused session that speaks the notebook pipe. The same username does not reload. The listener drops on the connector that lost focus. | The pull-down uses that same door. |

**Truth.** Today is a user-area land, one status string, no chooser memory, 4 rows with a 280 cap, a spinner alone on the east, and a divider of 8.

**Reason.** The later plan has to replace those surfaces without forgetting which ones stay.

**Avoidances.** Do not leave the land on the user area. Do not let the footer vault label become the user name. Do not grow the collapsed brief with the row count. Do not keep the literal 8.

**semantic-key:** PK-6764

# Divider chrome can land first, the row count lands before the end-of-pane check, and the pull-down and the status name the same user.

Order is a suggestion. The divider change is chrome with no vault effect. The row incrementor lands before a check of the end-of-pane land, because the end of the pane includes the row height. The pull-down and the status line name the same user.

The nine packages are the end of the pane, the user on the status line, chooser size and folder, the incrementor, the inspector rule, the held product-kit note, the connected-user pull-down, the thinking spinner, and the divider width. Each package is its own rule.

**Truth.** Suggested order puts the divider anywhere, the row count before the land check, and the two user-name surfaces together.

**Reason.** The end of the pane is meaningless to check before the row height exists.

**Avoidances.** Do not check the Run land against the old 280 cap. Do not give the divider package a vault write.

**semantic-key:** PK-6765

# The operator checks this blueprint by hand, the next stage stays closed, and the frozen product stays frozen.

The operator checks this blueprint by hand. A formal plan waits until the operator names it. Java waits until the operator names the build. The successor plan is the file that was later stamped. The next stage stays closed. The frozen product stays frozen.

Until that naming: do not write a second plan from this page, and do not edit code from this page.

**Truth.** The gate is a hand check, then a named plan, then a named build.

**Reason.** Nine approved notes are not a license to open the next stage or to thaw the product kit.

**Avoidances.** Do not open the next stage. Do not cut the kit. Do not edit code from this blueprint.

**semantic-key:** PK-6766

# This blueprint is superseded by the stamped tweak plan, and a builder implements from that plan.

This file is a blueprint. It is superseded by the Stage 1 tweaks plan for this serial, which is stamped and approved. Build from that plan. Approval named the build.

The source of the asks is the locked note pad that recorded the nine notes and the twelve hand-check rows. Those rows are approved. Pre-plan 001 and plans 001 and 002 stay frozen. Do not implement from this file.

The sentence that binds the pass: the notebook already stands. These are the next usability tweaks on that notebook. One pipe. Four context states. The next stage stays closed. The frozen product kit stays frozen.

No Lombok. No lambdas in project types. Anonymous classes only. `// @aa-sdd` on a new type only. The class stays `SshClientApp`. Shell launch stays `java -cp`. Do not edit `src/main/resources/application.yaml`.

The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key is added.

**Truth.** The blueprint is not the build file. The stamped plan is. Approval named the build.

**Reason.** A second implementation file would split the nine notes from the plan that already carries them.

**Avoidances.** Do not implement from this file. Do not edit the frozen earlier plans or the frozen product kit. Do not open the next stage. Do not edit application.yaml.

**semantic-key:** PK-6767

# Nine packages are approved, one of them is a held kit note, and the other eight are the build.

The note pad is locked. This file is the blueprint. The nine notes and the twelve hand-check rows are approved.

The count is nine packages. The product-kit note is a held cut, not code. The other eight are the build the later plan has to cover. The stages paper now carries these nine notes. The next stage stays closed.

The operator sits for the hand check of this blueprint.

**Truth.** Eight packages are code. The kit note is a record. The stages paper carries all nine.

**Reason.** The briefing closes note-taking on this pad and points the build at the later plan.

**Avoidances.** Do not treat the kit note as a class. Do not reopen the locked note pad. Do not open the next stage.

**semantic-key:** PK-6768

# Choosing a connected user selects that terminal session, and the same name does not reload the vault.

- The footer east is one border panel. The pull-down is its west. The spinner is its east. That panel is the footer's east.
- The pull-down sits to the right of Run, New turn, Clear turns, and Preview. The spinner stays the lower-right corner.
- The list is connected sessions that speak the notebook pipe, by the sanitized username the vault opens. Connecting and disconnected panes are omitted.
- Two panes with the same username are one entry and one vault. Choosing the focused user's name does not reload the vault. Otherwise the first connected pane with that name is selected.
- Choosing a name selects that session. Session selection remains the door. Run stays on the focused connector. The mux stays up.
- An illegal name still becomes `anon`. Two illegal names share that vault.
- A switch during a run clears the listener on the old connector, drops the in-flight frame, and stops the spinner as the switch starts. The session stays connected. A frame already queued can still arrive. Applying the assistant looks up the cell id in the open canvases. After the swap that id is absent, so the frame does not write. The epoch already discards a stale bind.
- When focus is not a connected session that speaks the notebook pipe, the pull-down has no selection, the status names no user, and the cards stay on the last vault.
- This is not crosstalk. The session is still running. The card slides to the user in focus according to the terminal. Messages stay put unless the operator says otherwise. Protect the mux.
- A combo that only swaps the cards would leave Run on the old connector. The listener on the connector that lost focus drops frames.
- The cloud adapter, mux channel `0x0004`, and crosstalk stay on that same pipe. The pull-down is a view switch. It does not copy cells from one user into another.

**Truth.** One east capsule lists connected users and selects the session. Same name, no reload. Mid-run switch drops the frame and keeps the session.

**Reason.** A card-only swap would leave the run pointed at the connector that just lost focus.

**Avoidances.** Do not reload on the same username. Do not list a connecting or disconnected pane. Do not copy cells across vaults. Do not disconnect on a switch.

**semantic-key:** PK-6769

# The Run land scrolls the active canvas to its maximum Y and focuses the new turn's input.

- The Run land, the pending land on input, scrolls the active canvas viewport to its maximum Y and focuses the new turn's input.
- When the new card fits, that shows the whole card. When the row count has made it taller than the viewport, the bottom stays in view.
- New turn and Clear turns keep the whole-card scroll.
- A header click, collapse, and `I`, `E`, `P`, and `O` keep the hold. They do not set the land flag.

The two scrolls do not special-case each other. The first one that hits the scroll wins. If they meet and the operator has to scroll again, that is accepted. Do not over-build that meeting.

**Truth.** Run goes to the bottom of the canvas and focuses the new input. New turn and Clear turns still show the top of their card.

**Reason.** A tall card cannot be fully on screen. The bottom is the part the next sentence needs.

**Avoidances.** Do not end-scroll New turn, Clear turns, or a header click. Do not keep the user-area scroll on the Run land.

**semantic-key:** PK-6770

# A reusable incrementor sets open-pane rows from 1 with no ceiling, and a typed value applies after 0.8 seconds.

- New type `org.aetheris.swing.Incrementor`. `// @aa-sdd` on that type. The controls are `+`, `-`, and an editable value.
- It sits in the west button flow, with Run, New turn, Clear turns, and Preview.
- `+` and `-` change the row count by one, immediately, and cancel a pending typed value.
- A typed value waits 0.8 seconds after the last key, then applies. Empty and non-numeric leave the last good value.
- The minimum is 1. There is no ceiling.
- The saved value is rows, in the same shell preference file as the chooser memory. A font-size change recomputes pixels from the rows.
- Both open panes use it. The card's maximum height comes from that row count, the line height, the header, the divider, and the borders. The 280 cap is replaced by that height.
- Text past the visible rows scrolls inside the pane.
- The collapsed brief, the inspector, and the View modal do not take the number. The brief stays one line, or two when the header is at least two line-heights tall.
- Switching vault users does not change it. Roux sync does not carry it.
- It is not a column on the cell.
- An open cell is 4 rows today, and `setRows` does not cap a filled area, so the row count has to set the card height or the extra rows stay clipped.

**Truth.** Plus, minus, and a typed integer set both open panes. The save is a row count. The floor is 1. There is no ceiling.

**Reason.** The 280 cap would hide the extra rows the operator just asked for.

**Avoidances.** Do not cap the rows. Do not store them on the cell or in the vault. Do not apply them to the brief, the inspector, or the View modal. Do not change them when the vault user changes.

**semantic-key:** PK-6771

# A short text rule sits between inspector cards, and an included card keeps its two role lines together.

- The inspector refresh inserts one short rule between cards.
- The rule is characters in the same text area, short enough that soft wrap leaves it on one row.
- The header and `est. tokens` stay outside the rules.
- An included card is two role lines and keeps them together. An input-only card is one role line. An output-only card is one role line.
- An excluded card stays out of the list, and the rules close up between the cards that remain.
- A later pin that drops a card drops that card's lines, and the rules sit between the cards that remain.
- A line between every two role lines would cut an included pair in half. That is not the rule.
- The inspector stays one text area. It does not become a painted list.

**Truth.** One short rule between cards, in the same text, with the header and the token line outside it.

**Reason.** A rule between every role line would split a turn. A long rule would wrap.

**Avoidances.** Do not paint a second inspector. Do not put a rule inside an included card. Do not wrap the header or the token line in a rule.

**semantic-key:** PK-6772

# While the spinner runs, the base is #4169E1, the glyph steps #00FF66, and the word thinking stays royal blue.

- While it spins, the base is `#4169E1`. The moving glyph steps `#00FF66` on each 120 ms frame. The word `thinking` stays `#4169E1`.
- The cell border stays `#3CB371`. The input emerald stays `#00FF66`.
- The label is hidden while idle. The spin sets its colors after the theme label color is applied, on each frame.
- The spinner stays at the lower right, inside the east capsule.
- The word `thinking` is kept. The whole label does not swap colors.
- No earlier paper named a hex for royal blue. The approved reading is `#4169E1`.

**Truth.** Royal blue base, emerald glyph, royal-blue word, 120 ms, hidden when idle.

**Reason.** The theme color made the spinner easy to miss. The emerald is the same green as the input text.

**Avoidances.** Do not recolor the cell border. Do not recolor the word `thinking`. Do not show the label while idle.

**semantic-key:** PK-6773

# The inspector lists the newest card first, notebook buttons lose 40 percent of their height, and soft wrap starts on.

Paint and layout on the notebook the previous plan already built. The note pad was checked and the five notes were approved. The soft-wrap solution was approved, including the divider catch.

Done means all of this.

- The inspector lists the newest card first.
- The pull-down stays on the east of the footer capsule and the spinner sits on its west.
- Notebook buttons are 40 percent shorter, with a font that fits, and every button has a raised bevel.
- Scrollbar arrows show at each end, light gray with a black arrow.
- The inspector wraps to the pane width while soft wrap is on, and soft wrap starts on.

Not done: a formal plan or code taken from this file, a product-kit cut, `select model`, `list models`, a fifth cell state, a change to assembly order, character wrap, moving the cell panes or the View modal onto the handler, a saved off-state for the wrap checkbox, and the terminal's own scrollbar.

**Truth.** Done is newest-first paint, spinner west and pull-down east, buttons at 60 percent height with a bevel on every button, gray bars with black arrows, and soft wrap on by default.

**Reason.** These five notes are paint and layout on a notebook that already runs.

**Avoidances.** Do not reverse the send order. Do not shorten the shell buttons. Do not restyle the terminal scrollbar. Do not save an off state for the checkbox.

**semantic-key:** PK-6774

# The inspector reverses on screen, the capsule swaps its children, buttons lose 40 percent, arrows are light gray, and soft wrap tracks the viewport.

| Work | Rule |
|---|---|
| Newest first | Inspector cards newest first. The header stays first. The token line stays last. The hyphen rule stays between cards. Send order stays oldest first. |
| Capsule | The spinner is on the west of the east capsule. The pull-down is on the east. The capsule stays on the footer east. |
| Buttons | Notebook buttons lose 40 percent of their height. Their font shrinks to fit. Every button the button painter paints gets a raised bevel. |
| Arrows | An arrow button at each end of every Swing scrollbar. Light gray face. Black arrow. |
| Soft wrap | A reusable soft-wrap area. It tracks the inspector viewport. Default on. The existing checkbox is the switch. |

**Truth.** Five rows: display order, capsule children, height and bevel, scrollbar arrows, and a viewport-tracking wrap area.

**Reason.** Each row is an approved note. The wrap row includes the divider catch.

**Avoidances.** Do not reverse send order inside the newest-first row. Do not apply the 40 percent cut to shell buttons.

**semantic-key:** PK-6775

# Assembly stays oldest first, wrap is on words, and the 40 percent cut does not reach the shell buttons.

| Item | Why it is out |
|---|---|
| Assembly order | The list reverses on screen. The working set still sends oldest first. |
| Character wrap | Word wrap was approved. The hyphen rule and the identifier stay one row at a normal width. |
| Cell panes and the View modal | They can take the same handler later. This slice's seat is the inspector. |
| A saved wrap checkbox | It starts on at each launch. An off state is not kept. |
| The terminal scrollbar | The terminal paints its own bar. The arrow rule is the Swing scroll panes. |
| Shell button height | The bevel reaches the shell buttons. The 40 percent cut is the notebook pane. |
| The held kit note, the next stage, the frozen product | Held, closed, frozen. |

**Truth.** Out is send order, character wrap, other seats, a saved checkbox, the terminal bar, and shell height.

**Reason.** Word wrap and a notebook-only height cut were the approved limits.

**Avoidances.** Do not character-wrap. Do not shorten Connect or the other shell buttons. Do not restyle the terminal bar. Do not save the checkbox.

**semantic-key:** PK-6776

# Five notes are in, and assembly order, character wrap, and shell-button height stay out.

The boundary is five notes. Child sections state each one.

In: newest card first in the inspector, spinner west and pull-down east, shorter notebook buttons with a bevel on every button, arrow buttons on Swing scrollbars, and a soft-wrap area that tracks the inspector viewport.

Out: reversing what gets sent, character wrap, moving cell panes and the View modal onto the handler in this slice, saving the wrap checkbox, the terminal scrollbar, shell-button height, the held kit note, the next stage, and the frozen product.

**Truth.** Five in. Send order, character wrap, shell height, and the terminal bar stay out.

**Reason.** The boundary keeps paint from changing the working set.

**Avoidances.** Do not move an out-row into the build. Do not drop an in-row.

**semantic-key:** PK-6777

# The inspector still writes oldest first, the combo still sits west of the spinner, and the text area still sizes to the longest line.

- The inspector refresh writes oldest first. Prior cards, then the target card, under `working set → id`, with `------------` between cards and `est. tokens` last. An included card keeps the user line before the assistant line. An excluded card stays out.
- The footer builder puts the combo on the west of the east capsule and the thinking spinner on the east. Showing the spinner widens that east child and the pull-down shifts left.
- The button painter paints a flat line border and an empty margin. The font walk sets the notebook button font from the shell point size.
- The theme sets `ScrollBar.background` and leaves the arrow buttons to the platform look.
- The inspector soft-wrap flag starts true. The checkbox starts checked. `setLineWrap(true)` and `setWrapStyleWord(true)` already run. The area still sizes to the longest line, so the pane scrolls sideways when the divider moves.
- The inspector text sits in an inner scroll pane. The wrap builds a second scroll pane around the header and that inner pane.
- The previous plan's eight packages stay in the tree. This slice rides on that tree.

The shell launch stays `java -cp`. Do not edit `src/main/resources/application.yaml`.

**Truth.** Oldest-first text, combo west of the spinner, a flat border, platform arrows, and a longest-line width are what the tree does now.

**Reason.** The five notes are corrections of those surfaces. The eight earlier packages stay.

**Avoidances.** Do not reopen the eight packages. Do not treat setLineWrap as enough. Do not leave the combo on the west of the capsule.

**semantic-key:** PK-6778

# The suggested order is the capsule, then the buttons, then the arrows, then the reversed list, and the soft-wrap handler last.

Suggested order for a later plan: capsule swap, button height and bevel, scrollbar arrows, newest-first inspector, soft wrap last.

Soft wrap is last so the handler wraps the reversed text and leaves the horizontal arrows for the wrap-off state.

The five packages are newest-first cards, the capsule swap, shorter buttons, scrollbar arrows, and the soft-wrap area. Each package is its own rule.

**Truth.** Soft wrap is last because it wraps the reversed text and must leave the horizontal arrows for the off state.

**Reason.** A handler written before the reverse would be checked against the old order.

**Avoidances.** Do not put soft wrap ahead of the reverse. Do not hide the horizontal arrows in the wrap-off state.

**semantic-key:** PK-6779

# The formal plan is stamped, code waits for a named build, and the previous note pad stays locked.

The operator approved this blueprint. The formal plan is the Stage 1 tweaks plan for this serial, stamped and approved. Code waits until the operator names the build.

The previous note pad stays locked. Do not edit it. Do not edit plans 001, 002, or 003. Do not edit the earlier note pad. The next stage stays closed. The frozen product stays frozen. The product-kit note stays held.

**Truth.** The plan exists and is stamped. Code is not named yet.

**Reason.** A stamped plan is not a named build.

**Avoidances.** Do not edit the locked pads or the earlier plans. Do not start code before the build is named. Do not open the next stage.

**semantic-key:** PK-6780

# This blueprint is superseded by the stamped layout plan, and code waits until the operator names the build.

This file is a blueprint. It is superseded by the Stage 1 tweaks plan for this serial, which is stamped and approved. Code waits until the operator names the build.

The source is the locked note pad. The five notes are approved. The soft-wrap solution is approved whole, including the divider catch.

Plans 001, 002, and 003 stay frozen. The earlier blueprints stay frozen. The previous note pad stays locked. Do not implement from this file.

The sentence that binds the pass: the notebook already stands. Five more notes on that notebook. One pipe. Four context states. The next stage stays closed. The frozen product stays frozen.

No Lombok. No lambdas in project types. Anonymous classes only. `// @aa-sdd` on the one new type, the soft-wrap area, when a later build creates it. The class stays `SshClientApp`. Shell launch stays `java -cp`. Do not edit `src/main/resources/application.yaml`.

The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key is added.

**Truth.** The blueprint records five approved notes. The stamped plan is the implementation file. Code waits for a named build.

**Reason.** The previous eight packages are already in the tree. This file must not reopen them.

**Avoidances.** Do not implement from this file. Do not edit plans 001, 002, or 003. Do not edit the locked note pad. Do not open the next stage. Do not thaw the product kit.

**semantic-key:** PK-6781

# The soft-wrap hull tracks the inner viewport so a divider move reflows the text.

The five notes and the soft-wrap solution are approved, and the operator named this blueprint. This file is the draft. Note-taking is closed. A formal plan waited at the time of the note. Code waited. The next stage stays closed.

Five notes. The soft-wrap hull is the package above. The divider catch is in that hull: the area tracks the inner viewport, so a divider move reflows the text.

The machines stay as the as-built section. The width track is the fix. A scrollbar policy alone can miss it.

The wire stays quiet. Assembly stays oldest first.

The pull-down stays on the outer east corner. The buttons get shorter and beveled. The lines fold inside the inspector.

The checkbox still says `soft wrap`.

The operator checks this draft by hand.

**Truth.** Five notes are closed as a blueprint. The wrap fix is the inner-viewport track.

**Reason.** A scrollbar policy alone misses a divider move and a toggle while the divider is still.

**Avoidances.** Do not reopen note-taking. Do not change assembly. Do not rename the checkbox.

**semantic-key:** PK-6782

# The newest contributing card is the first block under the header, and the top role line is numbered 1.

Reverse the card blocks in the inspector refresh. The newest contributing card is the first block under the header. Older cards follow downward.

- The header stays first.
- `est. tokens` stays last.
- The hyphen rule stays between cards. The rule is `------------`.
- Inside an included card the user line still precedes the assistant line.
- The numbers follow the list, so the top line is 1.
- Assembly stays oldest first.
- The canvas land stays as built.
- An excluded card stays out, and the rules close up.

**Truth.** Screen order is newest first. Send order is oldest first. The first painted role line is 1.

**Reason.** The operator asked for the newest entry at the top so the new lines do not have to be chased to the bottom.

**Avoidances.** Do not edit assembly. Do not reverse the user and assistant lines inside a card. Do not number before the reverse.

**semantic-key:** PK-6783

# Notebook buttons become 60 percent of today's height, and every painted button gains a raised bevel.

Notebook button height becomes 60 percent of today's height. That is a loss of 40 percent. The font on those buttons shrinks so the label fits.

That set is Run, New turn, Clear turns, Preview, the incrementor, the card buttons, and the View modal Close.

Shell buttons keep their height. The button painter adds a raised bevel on every button it paints, shell and notebook. The fill colors stay.

Today the painter uses a flat line border and an empty margin. The font walk sets the notebook button font from the shell point size. The shrink has to follow the shorter height so the label still fits.

**Truth.** Notebook buttons are 60 percent as tall. Shell buttons keep height. Every painted button is beveled. Fills stay.

**Reason.** The notebook buttons read as too tall, and a flat border does not read as a button.

**Avoidances.** Do not shorten the shell buttons. Do not change the fill colors. Do not leave the notebook labels overflowing the shorter face.

**semantic-key:** PK-6784

# While wrap is on, the inspector area tracks the inner viewport and the horizontal bar is absent.

The solution was approved whole.

1. One reusable text area, proposed name `org.aetheris.swing.SoftWrapArea`. `// @aa-sdd` on that type when the build creates it.
2. While wrap is on, the area tracks the viewport width, and a resize listener on that viewport reflows the text. The checkbox turns wrap off and restores the long-line width and the horizontal bar. A scrollbar policy alone is not the fix.
3. Soft wrap stays on at launch. The existing checkbox is the switch. It is not saved. The checkbox says `soft wrap`.
4. Wrap is on words. The twelve-hyphen rule and the identifier in the header stay one row at a normal width. The previews fold. Character wrap stays off. A very narrow pane can leave the rule and the identifier longer than the pane.
5. The handler attaches to the inner inspector pane. The open cell panes and the View modal stay later seats. They already ask for line wrap and can show the same hole. This note does not move them.
6. While wrap is on, the horizontal bar is absent. The arrow buttons return on that bar when wrap is off. The vertical bar stays.
7. Assembly still sends oldest first. The newest-first paint still paints newest first.

The flag is already on. The area still reports a preferred width of the longest line, so narrowing the inspector leaves the text on one row with a sideways scroll. The text sits in the inner scroll pane. A second scroll pane wraps the header and that inner pane. The handler attaches to the inner pane. Once that area tracks the inner viewport, the outer pane stops growing sideways from the unwrapped line.

The override that tracks the viewport is what makes the width stick. The listener covers a toggle that happens while the divider stays still, and a divider move that happens while wrap stays on.

**Truth.** Soft wrap tracks the inner inspector viewport, starts on, is not saved, and hides the horizontal bar only while it is on.

**Reason.** Line wrap was already set, and the longest-line width still forced a sideways scroll when the divider moved.

**Avoidances.** Do not use character wrap. Do not save the checkbox. Do not move the cell panes or the View modal onto the handler in this slice. Do not treat a scrollbar policy as the whole fix. Do not change send order.

**semantic-key:** PK-6785

# The spinner is west in the east capsule and the pull-down stays on the outer east corner.

In the east capsule, the thinking spinner is the west child and the user combo is the east child. The capsule stays the east child of the footer. The west button flow stays.

A spin shows and hides on the west of the capsule. The pull-down stays on the outer east corner.

Today the combo is west and the spinner is east. Showing the spinner widens that east child and the pull-down shifts left. The swap removes that shift.

**Truth.** Spinner west, combo east, capsule still footer-east.

**Reason.** A spinner on the east pushed the pull-down left every time a run started.

**Avoidances.** Do not move the capsule off the footer east. Do not change the west button flow. Do not leave the combo on the west of the capsule.

**semantic-key:** PK-6786

# Cells can collapse, the four letters need words, and a footer clear resets the canvas.

This sheet is only for minor usability on the notebook before the next stage is prepared. Design the collapse before building it.

- Cells in the notebook pane can collapse. Decide the design first. The sketch is a custom panel: while the cell is open, its container holds focus; while collapsed, a bar holds focus and the cell stays in the model, hidden. A small state says which cells are collapsed. Later buttons may sit on that bar. Ask for a cleaner approach if one exists, and record it on the briefing of the next serial.
- Context buttons `{I, E, P, O}` show the full text of each option. The letters alone are easy to forget after a month away.
- A new button on the bottom button panel clears all turns, the way the terminal clear resets chat memory to an initial state and clears all turns.
- Link the stage papers this sheet depends on. Every change stays compatible with what already runs and stays forward compatible with the next two stages. Look for inconsistencies before the tweaks land.
- On the terminal pane, select a model other than by typing an engine command. Add `select model` supplied by a model name. Add a command to list models on the local engine. The asked spelling on this sheet was `lost models`.
- Default cell text is bright emerald green for input, and the same yellow used for console output for the output cells. The current text reads as a light grey.
- The View window remembers size and position. The next View opens at that size and that place.
- A state-control click must not pop focus to the top of the parent sliding pane. Focus stays on the cell whose button was clicked. Lock that cell in its relative view position in the enclosing scroll pane after the click.
- Import a file into the current input cell only.

The asks are for evaluation before the details are closed.

**Truth.** Nine asks: collapse, full words, clear, compatibility, model commands, emerald and yellow, remembered View, a held scroll, and import into the current input.

**Reason.** The letters and the light grey text were hard to use, and the View and the scroll reset cost an extra step.

**Avoidances.** Do not build the collapse before the design is chosen. Do not import into a cell that is not the current input. Do not drop the compatibility check against the next stages.

**semantic-key:** PK-6787

# This file is a locked fallback, and the live draft is the next tweak serial.

This file is locked. It is the fallback. The live draft is the next tweak serial. Do not edit this file.

It is the original ask sheet for minor notebook changes before preparation of the next stage. The child heading carries the asks.

**Truth.** The sheet is locked. Later serials absorbed it.

**Reason.** A fallback that is still edited would fork the asks the next serial already packed.

**Avoidances.** Do not edit this file. Do not treat it as the live draft.

**semantic-key:** PK-6788

# Nine asks move forward: collapse, full words, a canvas clear, compatibility, model commands, two colors, View bounds, a held scroll, and import.

1. Cells in the notebook pane can collapse. Design first. The sketch: a custom panel, the cell container holds focus while open, a bar holds focus while collapsed, the cell stays in the model and is only hidden. A small state says which cells are collapsed. Future buttons may sit on the bar.
2. Context buttons `{I, E, P, O}` show the full words. The letters alone are easy to forget.
3. A footer button clears all turns, the way the terminal `clear` resets chat memory to the initial state.
4. Every change stays compatible with what already runs, and stays forward compatible with the next two stages.
5. The terminal gains `select model` plus a model name, and a command to list local-engine models. The original line `lost models` is read here as `list models`.
6. Default cell text: bright emerald green for input, and the console-output yellow for output.
7. The View window remembers size and position for the next open.
8. A state-button click keeps the scroll position on that cell. Focus does not jump to the top of the parent pane.
9. Import a file into the current input cell only.

**Truth.** The nine asks are unchanged in intent and are now a design list.

**Reason.** The next serial needed one list, including the reading of the misspelled list command.

**Avoidances.** Do not drop the compatibility ask. Do not treat `lost models` as a command name. The command name is `list models`.


**semantic-key:** PK-4420

# This serial is design only, the next stage stays closed, and no code moves until the open points are accepted.

This file is locked. It is the fallback. The live draft is the next tweak serial. Do not edit this file.

Mode: design. Not a build. The next stage stays closed.

It absorbs the original ask sheet. It depends on the stages paper, the technical specification, the Stage 1 plan, and the Stage 1 build record.

Code the tweaks have to stay compatible with: `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.chat.cmd.CommandProcessorFactory`, `org.casserole.chat.cmd.ClearCommand`, `org.casserole.chat.cmd.SwapCommand`, `org.casserole.chat.cmd.OllamaExecCommand`, `org.casserole.ssh.shell.Ansi`.

This serial is minor usability on the Stage 1 notebook that is already built. It does not open the next stage. It does not add a fifth cell state. It does not wire the cloud adapter. No code moves until the open points in the briefing are accepted.

Markup kinds used on the pad: locked, accepted, open, and under review.

The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key is added.

**Truth.** Design serial. No code. Four states stay four. The next stage stays closed.

**Reason.** The asks needed a design reading before any class changed.

**Avoidances.** Do not edit this locked file. Do not open the next stage. Do not add a fifth state. Do not wire the cloud adapter.

**semantic-key:** PK-6789

# Collapse is a boolean on the cell, the letters stay one character with a tooltip, and the model commands are tabled.

Collapse is a view flag, not a context state. `CellState` stays the four values `INCLUDED`, `EXCLUDED`, `INPUT_ONLY`, and `OUTPUT_ONLY`. The next stage already locks no fifth state for the pin and the ghost. A collapsed cell that is `INCLUDED` still walks into the working set. `E` remains the way out of context. Putting collapse on the enum would make a later promote-to-included rule and the ghost paint lie.

Accepted addition: when a cell is collapsed, replicate the state controls was the first note, then retired. Render a one-line brief of the contents from the first character, or two lines if the button height allows. Accepted correction of that first brief: the buttons render once and stay, with no second copies. When collapsed, bring the brief into focus.

The cleaner panel is the card that already exists. The card keeps one identity. The header is the bar. The split that holds the input and the output is hidden, not removed, and not replaced by a second focus owner. A boolean `collapsed` on `CellRecord`, default false, is the state model. It persists, so a tab rebuild does not forget it. Future bar buttons are more header controls on that same card. Two panels swapping focus would add a second path to the scroll jump this same sheet is trying to kill. Accepted.

The header is crowded. The first reading wanted full-word labels `Included`, `Excluded`, `Input only`, and `Output only`, with the first letter as the mnemonic, wrapping on the flow layout. Tooltips alone were first called not enough. Accepted correction: leave the one-character mnemonics, and add a tooltip on each so it hints what the letter is for.

`select model <name>` should feel like pointing at a tag. `swap <from> <to>` is already registered and it unloads one tag and loads another. `ollama list` already runs through `OllamaExecCommand`. The new names would be the short door. `list models` would call the local engine `GET /api/tags` and print the names. `select model <name>` would set the session model, unload the previous session model, and load the named tag with `keep_alive -1`, the same end state as swap. This stage still speaks the local engine only. A cloud name is refused. The command string can grow a provider prefix later without a rename. Accepted: table the ask. `swap` already changes the model. The local engine is `http://localhost:11434`.

The footer clear and the terminal `clear` are cousins, not the same box. Terminal `clear` drops session turns and keeps the system prompt. The canvas is the vault. The new button clears the active canvas back to one empty `INCLUDED` turn and writes the vault. It does not also wipe the terminal session. One gesture that clears both memories would surprise the console. Ghost cells go away with the rest of the turns. The burner pin does not spare a cell. `clear` on the console stays what it is. Accepted: the terminal chat is untouched. This only affects the notebook memory.

Input text is bright emerald `#00FF66` on the existing `#1A1A1A` field. The border stays medium green `#3CB371`, so the edge and the words are not the same green. Output text is bright yellow `#FFFF55`, the Swing stand-in for terminal SGR 93 (`Ansi.YELLOW`). The View modal uses that same yellow. An `EXCLUDED` cell stays gray in both areas, so the ghost still reads. Empty-response chrome can stay as it is. This is paint. It does not touch the wire. Accepted.

The View dialog is born at 720 by 480, centered, every time. One client preference stores x, y, width, and height. It is global, not per turn. Save it when the dialog closes. The next View, including after a restart, opens there. If those coordinates are off the current screen, center a 720 by 480 again. Store it beside the vault user, not on `CellRecord`, so a later inspector can reuse the same key. Accepted.

The scroll jump is the state button calling a quiet persist and then rebuilding the tabs. Rebuild throws away the scroll pane. The new viewport starts at the top. Scrolling the whole card into view then pulls a tall card to the top when a pending id exists. Hold: before the rebuild, remember the viewport's view Y and the card's Y. After the rebuild, set the view so `cardY - viewY` is unchanged, then focus that same cell. Apply the hold to every header control that rebuilds, including the burner. View does not rebuild and does not need it. Clear-all may scroll to the new empty turn on purpose. Accepted.

Import reads one text file, UTF-8, into the focused cell's user text, and replaces that text. The assistant area never receives it. An `OUTPUT_ONLY` cell refuses. No focused input cell refuses, with a status line. A NUL in the first 8 KiB refuses. The cap is 1 MiB. The cell state does not change. The import does not push the pipe. Accepted, with the door as a context menu: right-click a cell and choose import. An import button on the cell button panel is clutter.

**Truth.** One card, a persisted boolean, letter tooltips, tabled model commands, canvas-only clear, two greens plus `#FFFF55`, 720 by 480, a held scroll, and a right-click import capped at 1 MiB.

**Reason.** A fifth state, a second button set, and a second focus owner would break the next stage and the scroll hold.

**Avoidances.** Do not add a fifth state. Do not put a second copy of the state buttons on the bar. Do not clear the terminal from the footer. Do not wire the tabled commands. Do not put an import button on the header.

**semantic-key:** PK-6790

# The open points on this serial are closed by the acceptances in the briefing.

These points were still open when the briefing was drafted. Each one is closed by an acceptance already recorded.

- Collapse is view-only. A collapsed `INCLUDED` cell still enters the working set. Accepted as that reading. Collapse does not skip the walk.
- The footer clear affects the canvas vault only. Accepted. It does not also run the terminal `clear`.
- `select model <name>` would unload the previous session model and load the new tag. The whole command is tabled, so this point does not ship.
- `lost models` is a typo. The command name is `list models`. That command waits with the tabled ask.
- Import replaces the input text. The cap is 1 MiB. Accepted as replace, not insert-at-caret, and the cap stays 1 MiB.
- Button labels stay the letters. The words live in the tooltips. The earlier full-word labels do not ship.
- No code on this serial. The next stage stays closed. The cloud adapter stays unwired. The four context states stay four.
- All of the open items are addressed by those acceptances. Treat each accordingly.

**Truth.** Collapse still walks, clear stays on the canvas, the model commands stay tabled, import replaces text, and the letters keep the face.

**Reason.** The open list existed so a wrong reading could be refused before code.

**Avoidances.** Do not skip a collapsed included cell. Do not insert the import at the caret. Do not put the full words on the button face. Do not move code on this serial.

**semantic-key:** PK-6791

# One card, letter tooltips, a canvas-only clear, tabled model commands, two colors, global view bounds, a held scroll, and a right-click import are locked.

| Ask | Disposition |
|---|---|
| Collapse | Locked. One card. The header buttons stay. No second copy of the state buttons. `collapsed` is a boolean on the cell record, default false. It is not a fifth `CellState`. While collapsed, the body hides and a brief of the contents is in focus: the text from the first character, one line, or two lines if the bar height allows. The first reading said user text first, and if the user text is empty, the assistant text, with plain truncation and no summary engine. That brief reading is corrected in the same table: the brief is the output of the cell. The input is not relevant when the cell is included. A collapsed `INCLUDED` cell still enters the working set. `E` stays the way out of context. |
| Context letters | Locked. The buttons stay `I`, `E`, `P`, `O`. Each one gains a tooltip: Included, Excluded, Input only, Output only. Full words are not the labels. |
| Clear turns | Locked. A footer button clears the notebook canvas vault back to one empty `INCLUDED` turn and writes the vault. Terminal `clear` is not called. The session stays. Burner cells are not spared. |
| `select model` / `list models` | Tabled. `swap` was remembered. The ask waits. `lost models` was a typo for `list models`, and that command waits with the rest of the ask. `ollama list` and `swap` stay as they are. |
| Text color | Locked. Input foreground `#00FF66` on `#1A1A1A`. Border stays `#3CB371`. Output foreground `#FFFF55`, the Swing stand-in for terminal SGR 93. The View modal uses that yellow. An `EXCLUDED` cell stays gray in both areas. |
| View bounds | Locked. One client preference, global, not per turn, not on the cell record. Save x, y, width, and height when the dialog closes. The next open, including after a restart, uses it. Off-screen coordinates fall back to 720 by 480, centered. |
| Focus hold | Locked. Before a header control rebuilds the pane, remember the viewport view Y and the card Y. After the rebuild, restore `cardY - viewY` and keep focus on that cell. View does not rebuild. Clear-all may scroll to the new empty turn. |
| Import | Locked. Right-click on a cell opens a context menu. The item imports one UTF-8 text file and replaces that cell's user text. No import button on the header. The assistant area never receives the file. An `OUTPUT_ONLY` cell refuses. No focused input cell refuses, with a status line. A NUL in the first 8 KiB refuses. The cap is 1 MiB. The cell state does not change. The import does not push the pipe. |

The four context states stay four. The cloud adapter stays unwired. The next stage stays closed.

The output-brief correction in this table is the operator's correction of the user-text-first sentence. The next serial makes that correction the standing brief.

**Truth.** Eight dispositions. Collapse is a boolean. The brief correction says the output, not the input. Letters stay letters. Clear spares the session and not the burners.

**Reason.** The table is the lock the plan is allowed to trust.

**Avoidances.** Do not add a fifth state. Do not spare a burner on clear. Do not call terminal clear. Do not use full-word labels.


**semantic-key:** PK-4421

# Planning waits until the operator approves these dispositions for a plan.

Planning waits. The operator approves these dispositions for a plan in a later word. Until that word: do not write a plan file, do not edit code, do not open the next stage.

**Truth.** The gate is a later word. This serial is not that word.

**Reason.** Dispositions are not a plan and not a build.

**Avoidances.** Do not write a plan file from this serial. Do not edit code. Do not open the next stage.

**semantic-key:** PK-6792

# One set of buttons stays rendered, the tooltip carries the word, and this serial changes no class.

The first collapse note asked for a second set of state buttons on the bar. The next note retired that. One set of buttons, always rendered. The brief is the only thing that appears when the body hides.

The letters stay one character wide. The tooltip carries the word.

`swap` already does the model change. The new commands sit until the operator picks them up.

The footer clear and the console clear stay in different rooms.

The greens stay two greens. The yellow matches the console. The ghost stays gray.

Nothing in this serial is a plan slice and nothing is a class change. The next paper is a plan, and only after the operator says to plan. Filing the dispositions did not file code.

**Truth.** Buttons stay one set. Tooltips carry the words. Clears stay apart. No class changes.

**Reason.** A second button set and a shared clear were the two mistakes the briefing retires.

**Avoidances.** Do not render a second set of state buttons. Do not clear both memories from one gesture. Do not treat this serial as a plan.

**semantic-key:** PK-6793

# The collapsed brief is the assistant text, and an empty output leaves the brief empty.

The collapsed brief is the assistant text, from the first character, one line, or two lines if the bar height allows. Plain truncation. No summary engine. The user text is not the brief. An empty output leaves the brief empty. It does not fall back to the input.

That reading is the correction on the previous serial: the brief is the output of a cell, and the input is not relevant when the cell is included.

**Truth.** The brief is assistant text only. Empty output stays an empty brief.

**Reason.** Inclusion already puts the input on the walk. The bar does not repeat it.

**Avoidances.** Do not fall back to user text. Do not summarize. Do not use more than two lines.


**semantic-key:** PK-4424 PK-4425 PK-4426 PK-4427 PK-4428 PK-4429 PK-4430 PK-4431

# The eight locks stand, with the brief now the assistant text.

| Ask | Disposition |
|---|---|
| Collapse | Locked. One card. The header buttons stay. No second copy of the state buttons. `collapsed` is a boolean on the cell record, default false. It is not a fifth `CellState`. While collapsed, the body hides and the brief in the change section is in focus. A collapsed `INCLUDED` cell still enters the working set. `E` stays the way out of context. |
| Context letters | Locked. The buttons stay `I`, `E`, `P`, `O`. Each one gains a tooltip: Included, Excluded, Input only, Output only. Full words are not the labels. |
| Clear turns | Locked. A footer button clears the notebook canvas vault back to one empty `INCLUDED` turn and writes the vault. Terminal `clear` is not called. The session stays. Burner cells are not spared. |
| `select model` / `list models` | Tabled. `swap` and `ollama list` stay as they are. |
| Text color | Locked. Input foreground `#00FF66` on `#1A1A1A`. Border stays `#3CB371`. Output foreground `#FFFF55`, the Swing stand-in for terminal SGR 93. The View modal uses that yellow. An `EXCLUDED` cell stays gray in both areas. |
| View bounds | Locked. One client preference, global, not per turn, not on the cell record. Save x, y, width, and height when the dialog closes. The next open, including after a restart, uses it. Off-screen coordinates fall back to 720 by 480, centered. |
| Focus hold | Locked. Before a header control rebuilds the pane, remember the viewport view Y and the card Y. After the rebuild, restore `cardY - viewY` and keep focus on that cell. View does not rebuild. Clear-all may scroll to the new empty turn. |
| Import | Locked. Right-click on a cell opens a context menu. The item imports one UTF-8 text file and replaces that cell's user text. No import button on the header. The assistant area never receives the file. An `OUTPUT_ONLY` cell refuses. No focused input cell refuses, with a status line. A NUL in the first 8 KiB refuses. The cap is 1 MiB. The cell state does not change. The import does not push the pipe. |

The four context states stay four. The cloud adapter stays unwired. The next stage stays closed.

**Truth.** Eight locks. The brief slot now points at the assistant text. Everything else matches the previous table.

**Reason.** Carrying the table stops a later plan from reviving user text as the brief.

**Avoidances.** Do not spare burners. Do not add a fifth state. Do not store view bounds on the cell.

**semantic-key:** PK-6794

# Planning waits until the operator names the plan.

Planning waits. Until the operator names the plan: do not write a plan file, do not edit code, do not open the next stage.

**Truth.** The gate is the operator's word. This serial is not that word.

**Reason.** A corrected brief is not a plan.

**Avoidances.** Do not write a plan file. Do not edit code. Do not open the next stage.

**semantic-key:** PK-6795

# Run should focus the next turn, the inspector should hide an excluded cell, and the notebook should follow the focused terminal.

Four more asks, recorded for analysis. They are not yet a plan.

- Encode, in the interchange form, what relates to the build just completed.
- When Run is clicked in the notebook pane, shift focus straight to the next new turn cell. That brings the last result into the same view and removes an extra scroll before the next input.
- When a cell is excluded from the chat history, hide it in the inspector. The inspector shows only what is active on the memory-list chain.
- When another terminal session is added under a different user, the notebook on screen follows that user. The notebook stays focused relative to the active terminal window. The older notebook is presumed still alive even if the screen shows the new one. The operator needs to move between the two notebooks according to which terminal window is active.

The team reads these asks and answers on the next serial. This memorandum does not lock them and does not move code.

**Truth.** Four new asks: encode the filed build, land Run on the next input, hide excluded cells in the inspector, and follow the focused terminal user.

**Reason.** The operator was scrolling after Run, still seeing excluded cells in the inspector, and losing the other user's notebook when a second session connected.

**Avoidances.** Do not treat these four asks as a build. Do not drop the older notebook. Do not show an excluded cell as if it were on the chain.

**semantic-key:** PK-6796

# This serial is dispositions only, the brief is the output, and the earlier plan stamps stay filed.

This file is locked. It is the fallback. The live draft is the next tweak serial. The blueprint and the plan stay stamped for the build already filed. Do not edit this file.

Mode: dispositions packed. Not a plan. Not a build. The next stage stays closed.

It absorbs the previous disposition serial, which is locked. It depends on the stages paper, the technical specification, the Stage 1 plan, and the Stage 1 build record.

Code a later plan has to stay compatible with: `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.chat.cmd.CommandProcessorFactory`, `org.casserole.chat.cmd.ClearCommand`, `org.casserole.chat.cmd.SwapCommand`, `org.casserole.ssh.shell.Ansi`.

One acceptance moved. The collapsed brief is the output of the cell. Everything else from the previous serial stands. This serial is not a plan and it does not move code.

Markup kinds: locked, accepted, tabled, and under review.

**Truth.** Locked fallback. The only moved acceptance is the output brief. No code.

**Reason.** The previous serial still said user text first in one sentence. This serial retires that sentence.

**Avoidances.** Do not edit this file. Do not reopen the filed build. Do not open the next stage.

**semantic-key:** PK-6797

# The brief reads the output, and planning still waits.

The brief reads the output. Inclusion already decided that the input is in the walk. The bar does not repeat it.

The rest of the previous serial is unchanged. Planning still waits for the operator's word. No code.

**Truth.** Output brief. Prior locks unchanged. No code.

**Reason.** The input is already on the working-set walk. Repeating it on the bar was the mistake.

**Avoidances.** Do not put user text on the brief. Do not write a plan from this briefing. Do not edit code.

**semantic-key:** PK-6798

# The filed build is encoded, Run keeps the answer in view, the inspector lists the chain, and the notebook follows the focused console.

The build already in the tree is encoded as a code spec. Run, after it finishes, puts the caret in the next new turn and keeps that answer in the same view. The inspector lists the memory chain and leaves an excluded cell out of that list. The notebook on screen is the notebook of the terminal console that has focus, and the other user's notebook is still there when that console is focused again.

**Truth.** Four changes of intent: an encode, a Run land, an inspector chain, and a notebook that follows focus.

**Reason.** The operator should not scroll for the next input, should not see an excluded cell as sendable, and should not lose the other user.

**Avoidances.** Do not treat this paragraph as code already moved. The child sections hold the reading. The encode is not a second build.

**semantic-key:** PK-6799

# The eight locks stand, the brief is assistant text, and this serial does not reopen them.

| Ask | Disposition |
|---|---|
| Collapse | Locked. One card. The header buttons stay. No second copy of the state buttons. `collapsed` is a boolean on the cell record, default false. It is not a fifth `CellState`. While collapsed, the body hides and the brief is the assistant text, from the first character, one line, or two if the bar height allows. A collapsed `INCLUDED` cell still enters the working set. `E` stays the way out of context. |
| Context letters | Locked. The buttons stay `I`, `E`, `P`, `O`. Each one gains a tooltip: Included, Excluded, Input only, Output only. Full words are not the labels. |
| Clear turns | Locked. A footer button clears the notebook canvas vault back to one empty `INCLUDED` turn and writes the vault. Terminal `clear` is not called. The session stays. Burner cells are not spared. |
| `select model` / `list models` | Tabled. `swap` and `ollama list` stay as they are. |
| Text color | Locked. Input foreground `#00FF66` on `#1A1A1A`. Border stays `#3CB371`. Output foreground `#FFFF55`, the Swing stand-in for terminal SGR 93. The View modal uses that yellow. An `EXCLUDED` cell stays gray in both areas. |
| View bounds | Locked. One client preference, global, not per turn, not on the cell record. Save x, y, width, and height when the dialog closes. The next open, including after a restart, uses it. Off-screen coordinates fall back to 720 by 480, centered. |
| Focus hold | Locked. Before a header control rebuilds the pane, remember the viewport view Y and the card Y. After the rebuild, restore `cardY - viewY` and keep focus on that cell. View does not rebuild. Clear-all may scroll to the new empty turn. |
| Import | Locked. Right-click on a cell opens a context menu. The item imports one UTF-8 text file and replaces that cell's user text. No import button on the header. The assistant area never receives the file. An `OUTPUT_ONLY` cell refuses. No focused input cell refuses, with a status line. A NUL in the first 8 KiB refuses. The cap is 1 MiB. The cell state does not change. The import does not push the pipe. |

Those eight are in the tree. The code contract is the filed-build code spec. This serial does not reopen them. The four context states stay four. The cloud adapter stays unwired. The next stage stays closed.

**Truth.** The eight locks match the previous serial, with the assistant brief, and they are already in the tree.

**Reason.** Restating them stops the snack from reopening a filed build.

**Avoidances.** Do not reopen the eight. Do not add a fifth state. Do not untable the model commands.

**semantic-key:** PK-6800

# The four asks are encode, Run onto the next input, an inspector of the chain, and a notebook that follows the focused terminal.

The memorandum on the previous serial, kept:

- Encode, in the interchange form, what relates to the build just completed.
- When Run is clicked, shift focus straight to the next new turn cell so the last result stays in view and the next input does not need an extra scroll.
- When a cell is excluded from the chat history, hide it in the inspector. The inspector shows only what is active on the memory-list chain.
- When another terminal session is added under a different user, the notebook follows that user and stays tied to the active terminal window. The older notebook is still alive. The operator moves between the two according to the active terminal window.

The child sections are the team's reading. They did not lock the snack. They did not move code.

**Truth.** Four asks, quoted as the snack. The reading did not lock them and did not move code.

**Reason.** The operator asked for analysis before a plan.

**Avoidances.** Do not treat the snack as approved code. Do not drop one of the four asks.

**semantic-key:** PK-6801

# Planning waits, and the encode stays a draft until the operator says yes.

Planning waits. Until the operator names the plan: do not write a plan file, do not edit code, do not open the next stage. The encode stays a draft until the operator says yes.

**Truth.** No plan, no code, no next stage, and the encode is still a draft.

**Reason.** A reading of four asks is not a named plan.

**Avoidances.** Do not write a plan file. Do not edit code. Do not open the next stage. Do not treat the encode as accepted beyond a draft.

**semantic-key:** PK-6802

# This serial is dispositions plus four unreadied asks, and it does not move code.

This file is locked. It is the fallback. The live draft is the next tweak serial. Do not edit this file.

Mode: dispositions packed. Not a plan. Not a build. The next stage stays closed.

It absorbs the previous serial, which is locked. It depends on the first tweak plan, the first tweak blueprint, the filed-build code spec, the stages paper, the technical specification, the Stage 1 plan, and the Stage 1 build records.

Code a later plan has to stay compatible with: `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.app.ui.ssh.SshClientApp`, `org.casserole.chat.cmd.CommandProcessorFactory`, `org.casserole.chat.cmd.ClearCommand`, `org.casserole.chat.cmd.SwapCommand`, `org.casserole.ssh.shell.Ansi`.

One snack moved. The filed build stays filed. Four new asks are on the table with the team's reading. This serial is not a plan and it does not move code.

The class stays `SshClientApp`. Shell launch stays `java -cp`. The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key is added.

**Truth.** Locked fallback. The filed build stays filed. Four asks are read and not built.

**Reason.** The encode and the three usability asks needed a reading before a plan.

**Avoidances.** Do not edit this file. Do not reopen the filed build. Do not move code. Do not open the next stage.

**semantic-key:** PK-6803

# Three gaps are counted, planning still waits, and the encode stays a draft until the operator says yes.

Three gaps, counted.

- Run focuses the answered cell, and the new turn will not take a focus that is already set.
- `E` changes the cell and leaves the inspector text where it was.
- One bind on the focused connect replaces the pane. Selecting a session does not bind. The disk file of the other user is the session that is still alive.

The caret belongs where the next sentence starts, with the answer still in the room. The inspector is the chain about to be sent. Two consoles are two notebooks, and the live one is the one under the operator's hands.

One listener and one pane. The focused terminal picks the vault and the pipe. A second product tab is a different machine. This ask does not build that machine.

The disk vault per user is the shelf. The swap is the fix. A memory-only vault would not be there on return. Write the disk vault before the close.

The three asks are small at the door. The session one looks like a stomp. The other notebook is on disk. The pane shows the user who connected last.

The previous serial is the fallback. This serial is the draft. Planning still waits. No code. The next stage stays closed.

**Truth.** Three gaps: Run focus, a stale inspector after exclusion, and a bind that only happens on connect.

**Reason.** Each gap is a place the screen lies about the chain or about which notebook is live.

**Avoidances.** Do not build a second product tab. Do not move code from this briefing. Do not open the next stage.

**semantic-key:** PK-6804

# An excluded cell is absent from the inspector, including when it is the last cell, and a state click refreshes that list.

A refresh of the inspector already omits an excluded cell from the working set. The `E` click does not refresh the inspector, so the old list stays until the next Preview, Run, or new turn. The last cell's user text is inserted into that list even when the last cell is excluded.

The ask is the list that will actually be sent. An excluded cell is absent from it, including when that cell is the last one. `I`, `P`, and `O` stay the active half of a cell. A collapsed included cell stays on the chain. The card on the canvas stays visible.

`E` changes the cell and leaves the inspector text where it was. That is the gap.

**Truth.** The inspector should be the chain about to be sent. Excluded is absent, including the last cell. A collapsed included cell stays.

**Reason.** A stale list after `E`, and a last-cell insert that ignores exclusion, show cells that will not be sent.

**Avoidances.** Do not hide the card on the canvas. Do not drop a collapsed included cell from the chain. Do not leave the last excluded cell in the list.

**semantic-key:** PK-6805

# A finished Run leaves the caret in the answered cell, and the ask puts it in the new turn's input.

A finished Run on the last cell writes the answer, creates the next turn, and leaves the caret in the answer. The new input sits below that card. The ask puts the caret in the new turn's input and keeps the answer in that same view.

The header focus-hold stays as it is. A state click still does not jump the scroll.

The new turn will not take a focus that is already set. That is the gap: Run focuses the answered cell.

**Truth.** Today the caret stays in the answer. The ask moves it to the new input and keeps the answer in view.

**Reason.** The operator should not scroll to type the next sentence.

**Avoidances.** Do not change the header focus-hold. Do not make a state click jump the scroll.

**semantic-key:** PK-6806

# One notebook follows the focused terminal, and a different user writes the open vault before the load.

The shell has one notebook tab and one notebook. Connecting the focused session as a new user closes the open vault and loads that user. The tree's `aetheris.yaml` is disk, so the previous user's vault remains at `~/.aetheris/{user}/vault`. Clicking the other terminal enables the tab when that pipe speaks the notebook protocol and does not load that user's vault back. The older session is on disk. It is not the notebook on screen.

The ask is one notebook that follows the focused terminal window, with the other user's canvases waiting when that window is focused again. A swap writes the open vault before it closes.

One bind runs on the focused connect and replaces the pane. Selecting a session does not bind. The disk file of the other user is the session that is still alive.

One listener and one pane. The focused terminal picks the vault and the pipe. A second product tab is a different machine. This ask does not build that machine.

The disk vault per user is already the shelf. The swap is the fix. A memory-only vault would not be sitting there when the operator comes back. This configuration is disk. Write it before the close.

The three asks are small at the door. The session one is the one that looks like a stomp. The other notebook is on disk. The pane is showing the user who connected last.

The team read the hull. They did not lock the snack. They did not move code.

The previous serial is the fallback. This serial is the draft. The encode is the contract of the filed build. Planning still waits. No code. The next stage stays closed.

**Truth.** One tab follows the focused terminal. A different user is written, then loaded. The same click on the other terminal does not load that vault back today.

**Reason.** The operator saw the new user replace the pane and thought the older notebook had been destroyed. It is on disk.

**Avoidances.** Do not add a second product tab. Do not skip the write before close. Do not use a memory-only vault for this swap. Do not lock the snack from this reading.

**semantic-key:** PK-6807

# The second tweak build is closed, and the pad is open for the next handful of tweaks.

The second tweak build is closed. The pad is open for the next handful of tweaks. Those tweaks are the nine notes in the child sections.

**Truth.** The previous build is closed. The pad starts empty of new code.

**Reason.** Closing the previous build keeps its hand checks from being treated as an open slice.

**Avoidances.** Do not reopen the second tweak build. Do not start code from this sentence.

**semantic-key:** PK-6808

# A tap is filed in the operator's words, and a brainstorm on this page does not become a plan on its own.

The operator taps a requirement. It is filed here, in those words, and a filed note is adjusted only when the operator asks. A brainstorm stays on this page. It does not become a plan on its own.

The nine notes are the end of the pane, the user on the status line, import dialog size and folder, the incrementor, the inspector divider, the product-kit note, the connected-user pull-down, the thinking spinner, and the split divider width. Each note is its own rule. Each note waits. No code until note-taking is done.

**Truth.** Notes stay notes until note-taking is closed.

**Reason.** A tap is not a plan and not a class change.

**Avoidances.** Do not turn a brainstorm into a plan. Do not edit code from this page.

**semantic-key:** PK-6809

# Note-taking stays open until the operator says it is done.

Note-taking stays open until the operator says it is done. Until that sentence: do not write a plan, do not edit code, do not open the next stage.

The later lock of this file is recorded on the title. While the gate was the live rule, the sentence above was the stop.

**Truth.** No plan, no code, and no next stage until note-taking is declared done.

**Reason.** An approved note is not yet a named plan.

**Avoidances.** Do not write a plan from an open pad. Do not edit code. Do not open the next stage.

**semantic-key:** PK-6810

# This serial is a locked note pad, not a plan and not a build, and the next stage stays closed.

This file is locked. Note-taking is closed. The nine notes and the twelve hand-check rows are approved. The blueprint is the tweak blueprint for this serial. Do not edit this file.

Mode: note-taking. Not a plan. Not a build. The next stage stays closed.

It absorbs the previous serial, which is locked. It depends on the stages paper, the second tweak plan, the second tweak code spec, the filed-build code spec, and the first tweak plan.

Code a later plan has to stay compatible with: `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.app.ui.ssh.SshClientApp`.

The previous serial is closed. The second tweak plan is encoded and closed. This serial is a pad. A note is written when the operator taps one, and a note changes only when the operator asks. No plan and no code until the operator says note-taking is done.

Markup kinds: locked, accepted, open, and tabled.

The class stays `SshClientApp`. Shell launch stays `java -cp`. The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key is added.

**Truth.** Locked pad. Nine notes and twelve hand-check rows are approved. No code from this file.

**Reason.** The pad collects taps. It does not become a plan by itself.

**Avoidances.** Do not edit this file. Do not write a plan from it. Do not open the next stage.

**semantic-key:** PK-6811

# Nine notes are read against the filed code, and the open points stay open until the operator answers them.

The pad is in spec mode. This is the reading of the nine notes. The notes stay as tapped. Note-taking stays open. A plan waits until the operator names one. Code waits until the operator names the build. The next stage stays closed. The frozen product stays frozen. A new kit waits until the operator is ready. The previous plan's hand checks were not clicked. The reading is against the code that is filed.

The count is nine notes. The stages paper already carries the first four. The rest join that row when note-taking closes. This turn does not write them there. None of these notes is a slice of the next stage.

Hull:

- One vault follows the focused terminal session. The pull-down selects that session. A combo that only swaps the cards leaves Run on the old connector. The listener on the connector that lost focus drops frames. The cloud adapter, mux channel `0x0004`, and crosstalk stay on that same pipe. The pull-down is a view switch. It is not crosstalk, and it does not copy cells from one user into another. Accepted: it is not crosstalk. The session is still running. The card slides to the user in focus according to the terminal. If messages should be affected, the operator will say so. Otherwise protect the mux.
- The footer east slot is the thinking spinner today. The pull-down also pins to the east. One east panel holds the pull-down and then the spinner. The pull-down sits to the right of Run, New turn, Clear turns, and Preview. The spinner stays the lower-right corner. Accepted layout: a new border panel, spinner on the east, pull-down on the west, and that panel on the east of the footer. They sit next to each other on the east side.
- The end-of-pane land and the row count meet when the new card is taller than the canvas. Scroll to the end of the pane shows the bottom of the canvas. A card taller than the viewport cannot be fully on screen at the same time. The end-of-pane note is the Run land, the path that sets the pending land on input. A header hold still uses the delta and keeps the operator's place. A state click and a later pin commit use that hold. Accepted: do not special-case the meeting. One action can occur independent of the other. The first scroll wins. If the operator has to scroll again, that stands.
- The divider constant is 11. The split helper is the only `setDividerSize`, and the literal there is 8 today. Collapse treats a divider location above 8 as expanded. That 8 moves with the constant. The divider paint paints the live size and then the one-touch buttons, so the extra 3 pixels are hit area. While connected, divider location is still driven separately. The shell split, the session split, the notebook split, and every open cell split take the constant. Each open card pays the extra 3 pixels. The solution on the divider note is the accepted one.
- The inspector line sits between turns. An included turn is two role lines. An input-only or output-only turn is one role line. A line between every two roles would cut a pair in half. An excluded turn stays out of the list, and the lines close up. The header `working set → id` and `est. tokens` stay outside the rules. A later filter that removes a turn removes it from this same list. The inspector stays one text area. Soft wrap can break a long rule, so the rule stays short. Accepted.
- The kit note is the next product kit. It has no row in the next stage. Seek, the quoted prose, and the note of what to do and what occurred, on the open serial or where the operator directs, go in that kit when the operator is ready. Accepted.

Machines, as they sit:

- End of the pane. After Run, the canvas viewport goes to its maximum Y and the new turn's input takes focus. That is the land path. The user-area scroll on that path is the behavior this note replaces. New turn and Clear turns still scroll the whole card, which brings the top of that card into view. A collapsed card has no user area. The land path then scrolls the card itself. The row count is applied before that scroll, so the end of the pane includes the height the incrementor just set.
- Incrementor. Plus and minus apply on the click and cancel a pending typed value. A typed value waits 0.8 seconds after the last key, then applies. The value is a whole number of rows. The minimum is 1. An empty box or a non-numeric box leaves the last good value in place. The saved number is rows, so a font-size change recomputes the pixel height. Both open panes take it. The collapsed brief stays one line, or two when the header is at least two line-heights tall. The inspector and the View modal do not take the number. An open card is capped at 280 today, and `setRows` does not cap a filled area, so the row count has to set the card height or the extra rows stay clipped. Text past the visible rows scrolls inside the pane. The value is one shell preference, restored when the app opens. It is not a column on the cell. Switching users does not change it. Roux sync does not carry it.
- Divider. One static constant, value 11. The split helper reads it. The collapse check reads it. A split added later uses that door.
- Choosers. The terminal send-file chooser and the notebook cell chooser are each a fresh `JFileChooser`. `showOpenDialog` disposes its dialog before it returns, so the bounds come from that dialog window. One size and one folder serve both doors. They are one shell preference. A missing folder opens at the platform default. A saved rectangle that misses the current screen opens at the platform default size, and the folder is kept when it still exists. The size is kept when the dialog closes, including a cancel. The folder is kept when a file is approved, including when the later read refuses for size or for a NUL in the first 8 KiB. An `OUTPUT_ONLY` cell still refuses before the chooser opens. The export door does not use this pair. Two vault users on one window share the chooser memory.

Wire:

- The pull-down lists connected sessions that speak the notebook pipe, by the sanitized username the vault opens. A connecting pane and a disconnected pane are omitted. Two panes with the same username are one entry and one vault. Choosing the name selects that session. When the focused pane is already that user, it stays, and the vault does not reload. Otherwise the first connected pane with that name is selected. An illegal name still becomes `anon`. Two illegal names share that vault.
- A switch during a run clears the listener on the old connector. The in-flight frame is dropped. The spinner stops as the switch starts. A frame already queued on the event thread can still arrive. Applying the assistant looks up the cell id in the open canvases. After the swap that id is absent, so the frame does not write. The epoch already discards a stale bind.
- When focus is not a connected session that speaks the notebook pipe, the vault stays open. The combo has no selection. The status names no user. The cards on screen are the last vault.
- The line at the very bottom of the window is the frame status label. It says Connected, Connecting, Disconnected, or a notebook message, and each of those writes replaces the whole string. The connected user is included on that line and is still there after the next message. When the focused session is not a connected session that speaks the notebook pipe, the name is absent. The footer label stays the vault mode, `vault: disk` or `vault: ram`, in the button row.

Paint:

- While the spinner spins, the base is bright royal blue and the moving glyph interleaves the input emerald `#00FF66`. The word `thinking` stays royal blue. The timer step is 120 milliseconds. The label is hidden while the run is idle. The theme label color is applied once. The spin sets its own color after that, on each frame. No hex for royal blue is in the spec. The reading on the table is `#4169E1`. The cell border stays `#3CB371`. The emerald stays `#00FF66`.
- The user name on the status line uses that line's existing foreground.
- The inspector rule is a short line of light strokes between turns, in the same text, so the list still reads as a list. The spinner keeps the word `thinking`.

Slide-in, recorded until note-taking closes:

| Note | Slides into |
|---|---|
| End of the pane | Chrome on the cards that groups and pipes use. The Run land only. A pin and a state click keep the hold. |
| Status user | The frame status line names the focused connected user. That is the user the one vault is showing. |
| Choosers | The local chooser for the terminal paste and the cell import. The export door stays its own door. |
| Incrementor | Chrome on those same cards. Pipes still read the text. The brief stays the brief. |
| Inspector rule | The inspector list the previous plan already refreshes, including after a pin. The rules follow the turns that remain. |
| Product kit | The next kit. No row in the next stage. |
| Pull-down | The same one-tab, one-vault pipe. It selects the session. The cloud adapter, the tool channel, and crosstalk stay on it. |
| Spinner | Paint on the spinner at the lower right. The pipe is unchanged. |
| Divider | Shell chrome. Every split that comes through the split helper, including a split the next stage adds. |

Open points, still open:

- Bright royal blue has no hex in the spec. The reading is `#4169E1`. Accept if the hex moves.
- The glyph interleaves `#00FF66`. The word `thinking` stays royal blue. Accept if the whole label should swap colors.
- The end-scroll is the Run land. Accept if New turn and Clear turns should also go to the end of the pane.
- The row count has no ceiling. A very large value makes the new card taller than the window, and the Run land then shows the bottom. Accept if a ceiling is wanted.
- The inspector rule is a short text rule in the one inspector area. Accept if that area must become a painted list.
- The row count and the chooser memory are one shell preference. Accept if each vault user keeps a private copy.

**Truth.** The reading covers the hull, the machines, the wire, the paint, the slide-in, and six open points. `#4169E1` is a proposal, not yet a lock.

**Reason.** A combo that ignores the terminal, a rule that splits a pair, and an 8 pixel bar were the failures the reading names.

**Avoidances.** Do not copy cells across users. Do not special-case the tall-card meeting. Do not paint the inspector yet. Do not write the stages paper on this turn.

**semantic-key:** PK-6812

# A divider between cards is accepted, and the inspector stays one text area.

The inspector reading is accepted. A divider between cards is the ask. Review of the pad continues.

The divider follows the card. An included card keeps its two role lines together. An input-only card and an output-only card each keep their one line. The open point that asked whether the inspector must become a painted list is closed. The inspector stays one text area. The rule stays short.

**Truth.** Between cards, not between every role line. One text area. A short rule.

**Reason.** A painted list was the open question. The acceptance closes it.

**Avoidances.** Do not turn the inspector into a painted list. Do not split an included pair.

**semantic-key:** PK-6813

# The nine notes stand approved, and twelve proposed answers wait for a last hand check.

The pad has been checked well enough to approve it all. For questions the team still had that were not answered directly, the table below proposes a solution so the features can be checked once more before a blueprint.

The nine notes stand approved. A blueprint stays closed until that table is checked. These rows are the questions not answered directly. Each proposed solution is the reading already on the pad.

Already approved, so they are not in the table:

- The pull-down is not crosstalk, the mux stays protected, and the east capsule is a border panel with the pull-down on the west and the spinner on the east.
- The Run land and the row count do not special-case each other. The first scroll wins, and the operator scrolls again if the two meet.
- The divider is the constant 11 and the open-height figure.
- The inspector rule is a short rule between cards in the one inspector area.
- The kit note is the next product kit, cut when the operator is ready.

The twelve proposed rows are the next heading.

**Truth.** Nine notes are approved. Twelve proposals remain for a last hand check. Five settlements are already out of that table.

**Reason.** The last check has to see only the questions that were still open.

**Avoidances.** Do not put the five settled items back into the open table. Do not start a blueprint before the table is checked.

**semantic-key:** PK-6814

# A pull-down of connected users sits on the east of the notebook button bar and switches the card layout.

The operator's words: a pull-down for connected users in the notebook pane, which can switch the view from one connected user's chat in the card layout to another's. Add it to the bottom global button bar, to the right of the existing buttons, pinned to the east side of the button bar.

A pull-down of connected users sits on the notebook bottom button bar, to the right of the existing buttons, pinned to the east side. Choosing a name switches the card layout to that connected user's chat. This note waits.

**Truth.** East-pinned pull-down. Choosing a name switches the visible chat.

**Reason.** Focus already follows the terminal. Other operators need the same switch from the notebook bar.

**Avoidances.** Do not pin the control anywhere but the east of that bar. Do not build it while the note waits.

**semantic-key:** PK-6815

# After Run, the canvas scrolls to the end of the pane so the new turn card is fully in view.

The operator's words: the move-focus-to-next-turn behavior, in use, should shift focus to the end of the pane itself. That scrolls everything down to the end and exposes the new turn card completely.

After Run, the canvas scrolls to the end of the pane. The new turn card is fully in view. The closed encode of the previous build stays closed. This note waits.

**Truth.** Run scrolls to the end of the pane and shows the new card.

**Reason.** Landing on the next cell still left the operator short of a full view of that card.

**Avoidances.** Do not reopen the closed encode. Do not move code while the note waits.

**semantic-key:** PK-6816

# Twelve proposed answers cover the spinner hex, the Run-only land, no row ceiling, and a pull-down that selects the session.

| Note | Still open | Proposed solution |
|---|---|---|
| Spinner color | No hex was named for bright royal blue. | Use `#4169E1` while the spinner spins. The cell border stays `#3CB371`. The emerald stays `#00FF66`. |
| Spinner parts | Which part of the spinner carries the emerald. | The moving glyph steps `#00FF66`. The word `thinking` stays royal blue. |
| End of the pane | Whether New turn and Clear turns also jump to the end of the pane. | The end-scroll is the Run land only. New turn and Clear turns still bring the top of that card into view. |
| Hold | Whether a header click or a state click also jumps to the end. | A header click, including collapse and `I`, `E`, `P`, and `O`, keeps the operator's place. It does not jump to the end. |
| Row ceiling | Whether the row count has a ceiling. | No ceiling. The minimum is 1. A card taller than the window shows its bottom on the Run land, and the operator scrolls. |
| Incrementor seat | Where the incrementor sits, now that the east capsule is the pull-down and the spinner. | The incrementor sits in the west button flow, with Run, New turn, Clear turns, and Preview. |
| One preference | Whether the row count and the chooser memory are one shell preference or private per vault user. | One shell preference for the row count. One shell preference for the chooser size and folder. Switching users does not change them. |
| Chooser keep | What is kept on cancel, on a refused read, and when the saved place is gone. | The dialog size is kept on cancel and on approve. The folder is kept when a file is approved, including when the later read refuses. A missing folder, or a rectangle that misses the screen, opens at the platform default. An `OUTPUT_ONLY` cell still refuses before the chooser opens. The export door stays its own door. |
| Status name | Which bottom line carries the user, and whether a later message erases the name. | The frame status line at the very bottom of the window includes the focused connected user. The next status message keeps that name. The notebook footer keeps the vault mode label. |
| Pull-down selects | Whether choosing a name selects the terminal session. | Choosing a name selects that connected session. The card follows that session. The mux stays up. Two panes with the same username are one entry, and the vault does not reload. |
| In-flight run | What happens to a run that is in flight when the user changes. | The in-flight reply is dropped. The spinner stops. The session stays connected. |
| No connected session | What the pull-down and the status show when focus is not a connected session that speaks the notebook pipe. | The pull-down has no selection. The status names no user. The cards stay on the last vault. |

**Truth.** Twelve proposals. `#4169E1` for the base, `#00FF66` for the glyph, minimum rows 1, and no reload on the same username.

**Reason.** These were the questions still open after the notes themselves were approved.

**Avoidances.** Do not end-scroll New turn or a state click. Do not add a row ceiling. Do not reload the vault for the same username. Do not erase the user name on the next status message.

**semantic-key:** PK-6817

# Plus and minus change viewable rows on both panes, and a typed value applies after 0.8 seconds.

The operator's words: a reusable control with `+` and `-` next to each other and an editable text value beside them. On the button bar at the bottom of the notebook panel, `+` increases the number of viewable rows in a cell for both the input and the output panes, and `-` decreases that height by one row in both cases. The selected number of viewable lines persists, and the app restores that row count when it opens. Typing the value has the same effect after about a 0.8 second timeout, then the pane re-renders. Call it an incrementor.

Feasible. The control is an incrementor: `+`, `-`, and an editable value, on the notebook footer. `+` and `-` change the viewable rows of the input pane and the output pane by one, then the pane re-renders. A typed value waits 0.8 seconds after it settles, then does the same. The row count is saved and restored when the app opens. An open cell is 4 rows today, and the open card is capped at 280, so the row count has to drive the card height or the extra rows stay clipped. This note waits.

**Truth.** An incrementor on the footer sets both panes, persists rows, and applies a typed value after 0.8 seconds.

**Reason.** The 280 cap would clip the rows the operator just added.

**Avoidances.** Do not leave the 280 cap in charge of the new row count. Do not apply the typed value before 0.8 seconds of quiet.


**semantic-key:** PK-4432

# A divider sits between inspector cards, and an included card keeps its two role lines together.

The operator's words: in the inspector, add a thin divider line between each turn pair, so one turn is easier to tell from the next. The follow-up: a divider between cards is the ask.

Accepted. The divider sits between cards. An included card is two role lines. An input-only card is one role line. An output-only card is one role line. An excluded card stays out of the list, and the dividers close up between the cards that remain. The header and the token estimate stay outside the rules. The inspector stays one text area. The rule stays short so soft wrap keeps it on one row. This note waits.

**Truth.** One short rule between cards. Pairs stay intact. Excluded cards stay out.

**Reason.** A line between every role would cut an included turn in half.

**Avoidances.** Do not paint a second inspector. Do not put the header or the token line inside a rule.

**semantic-key:** PK-6818

# While it spins, the thinking spinner is bright royal blue interleaved with emerald #00FF66.

The operator's words: the thinking spinner at the lower right of the notebook should be a bright royal blue while spinning, animated with the bright emerald used for the input font, so it is easier to keep in sight while thinking.

While it spins, the thinking spinner is bright royal blue, interleaved with the input emerald `#00FF66`. It stays at the lower right. This note waits. A hex for royal blue is not in this note. The later reading proposes one.

**Truth.** Royal blue plus `#00FF66`, at the lower right, only as a note so far.

**Reason.** The spinner was hard to see while a run was in flight.

**Avoidances.** Do not move the spinner off the lower right. Do not invent a hex inside this note.

**semantic-key:** PK-6819

# The operator is reviewing the new build, and the pad is open for a couple of minor items.

The operator is reviewing the new build. The pad is open for a couple of minor items. Those items are the five notes in the child sections.

**Truth.** The pad opens on a build already in the tree.

**Reason.** Minor paint notes need a place that is not a plan.

**Avoidances.** Do not treat this sentence as a build order.

**semantic-key:** PK-6820

# The previous pad, its blueprint, and its plan stay frozen, and the hand check stays unclicked.

The previous pad, the blueprint, and the third tweak plan stay frozen. The nine notes and the twelve hand-check rows stay approved. The compile stays. The hand check stays unclicked. The product-kit note stays held. `select model` and `list models` stay tabled. The next stage stays closed.

**Truth.** Closed is the previous nine, the unclicked hand check, the held kit, and the tabled commands.

**Reason.** A new pad must not pretend the hand check was clicked.

**Avoidances.** Do not claim the hand check passed. Do not cut the kit. Do not untable the model commands. Do not open the next stage.

**semantic-key:** PK-6821

# A tap is filed in the operator's words, and it does not become a plan on its own.

The operator taps a requirement. It is filed here, in those words, and a filed note is adjusted only when the operator asks. A brainstorm stays on this page. It does not become a plan on its own.

The five notes are inspector reverse order, spinner west and pull-down east, shorter buttons with a bevel on all, scrollbar arrows, and inspector soft wrap. Each note waits. No code until note-taking is done.

**Truth.** Five notes, filed as notes.

**Reason.** A tap is not a class change.

**Avoidances.** Do not turn the page into a plan. Do not edit code from it.

**semantic-key:** PK-6822

# This serial is a locked note pad, the five notes are approved, and the soft-wrap solution is approved whole.

This file is locked. Note-taking is closed. The five notes are approved. The soft-wrap solution is approved. The blueprint is the tweak blueprint for this serial. The successor pad is the next tweak serial. Do not edit this file.

Mode: note-taking. Not a plan. Not a build. The next stage stays closed.

It absorbs the previous note pad, which is locked. It depends on the third tweak plan, the third tweak blueprint, and the stages paper.

Code a later plan has to stay compatible with: `org.aetheris.swing.AetherisNotebookPanel`, `org.aetheris.swing.Incrementor`, `org.casserole.app.ui.ssh.SshClientApp`.

The previous pad stays locked. The third tweak plan is stamped and the eight packages are in the tree. This serial is a pad. A note is written when the operator taps one, and a note changes only when the operator asks. No plan and no code until the operator says note-taking is done.

Markup kinds: locked, accepted, open, and tabled.

The class stays `SshClientApp`. Shell launch stays `java -cp`. Do not edit `src/main/resources/application.yaml`. The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key is added.

**Truth.** Locked pad. Five notes and the soft-wrap solution are approved. The eight earlier packages stay in the tree.

**Reason.** The pad exists so the next paint notes do not reopen the stamped plan.

**Avoidances.** Do not edit this file. Do not reopen the eight packages. Do not open the next stage.

**semantic-key:** PK-6823

# The soft-wrap area tracks the inner viewport, word wrap stays, and the horizontal bar returns when wrap is off.

The operator opened spec mode on the soft-wrap note. The note stays as tapped. Note-taking stays open. A plan waits until one is named. Code waits until the build is named. The next stage stays closed.

One new note. The hull is sound.

The flag is already on. The soft-wrap flag starts true, the checkbox starts checked, and `setLineWrap(true)` plus `setWrapStyleWord(true)` already run. The area still reports a preferred width of the longest line. The scroll pane honors that width. Narrowing the inspector moves the divider and the text stays one row, with a sideways scroll. That is the hole.

The text sits in the inner scroll pane. The wrap builds a second scroll pane around the header and that inner pane. The handler attaches to the inner pane, the one that owns the text. Once that area tracks the inner viewport, the outer pane stops growing sideways from the unwrapped line.

The approved solution:

1. One reusable handler, a text area that can turn soft wrap on and off. `// @aa-sdd` lands on that type when a later build creates it. No code on this turn.
2. While wrap is on, the area tracks the viewport width, and a resize of that viewport reflows the text. The sense is a component listener on the viewport. Turning the existing checkbox off restores the long-line width and the horizontal bar.
3. Soft wrap stays on at launch. The checkbox is the switch. It is not a saved preference. An off state was not requested.
4. Wrap is on word boundaries. The twelve-hyphen rule has no spaces, and an identifier in the header has no spaces. At a normal inspector width both fit on one row. A very narrow pane can leave those two lines longer than the pane. The previews have spaces, so they reflow. Do not switch the area to character wrap.
5. The first seat is the inspector. The open cell panes and the View modal already ask for line wrap and can show the same hole. They are later seats for the same handler. This note does not move them.
6. While wrap is on, the horizontal bar is absent. The arrow buttons return on that bar when wrap is off. The vertical bar stays.
7. Assembly still sends oldest first. The newest-first note still paints newest first. The wrap is paint on the one text area.

The override that tracks the viewport is the part that makes the width stick. A scrollbar policy alone can miss it. The listener covers a toggle that happens while the divider stays still, and a divider move that happens while wrap stays on.

The wire is quiet. Nothing in this note touches the connector, the vault, or the working set that gets sent.

The lines break inside the pane. The header and the rule stay one row at a normal width. The previews fold.

The checkbox already says `soft wrap`. The handler does not need a second name on the glass.

The solution above is the team's proposal for the operator to accept.

**Truth.** Seven solution steps. The viewport track is the fix. Word wrap stays. The checkbox is not saved.

**Reason.** A scrollbar policy alone misses a still divider and a toggle.

**Avoidances.** Do not use character wrap. Do not save the checkbox. Do not move the cell panes in this note. Do not touch the wire. Do not rename the checkbox.

**semantic-key:** PK-6824

# The inspector lists newest first, the header stays first, and assembly still sends oldest first.

The operator's words: make the inspector entries operate in reverse order, so the newest one is always inserted at the top of the list. That removes the need to keep the new entries at the bottom in focus.

The inspector list runs newest first. The newest contributing card is the first entry under the header. Older cards follow it downward.

The inspector refresh writes oldest first today. Prior cards, then the target card, under `working set → id`, with `------------` between cards and `est. tokens` last. The card blocks reverse. The header stays first. The token line stays last. The hyphen rule stays between cards. Inside an included card the user line still precedes the assistant line. An excluded card still stays out, and the rules close up. The numbers follow the list, so the top line is 1.

The list on screen reverses. Assembly still sends oldest first. The canvas land stays as built. One text area. Feasible. This note waits. No code until note-taking is done.

**Truth.** Newest block under the header. Token line last. Top role line is 1. Send order unchanged.

**Reason.** Chasing the newest entry to the bottom was the complaint.

**Avoidances.** Do not reverse assembly. Do not reverse user before assistant inside a card. Do not paint a second inspector.

**semantic-key:** PK-6825

# The inspector wraps to its own width when soft wrap is on, and soft wrap starts on.

The operator's words: soft wrap does not appear to be working. Add a reusable soft-wrap handler that senses changes in inspector width and, when enabled, adjusts the text to wrap to the width of the inspector window. Enable soft wrap by default.

The inspector wraps to its own width when soft wrap is on. The wrap follows the pane as the divider moves. Soft wrap starts on.

The checkbox and the flag already start on. `setLineWrap(true)` is already called. The area still sizes to the longest line, so the pane scrolls sideways. The handler is the piece that makes the flag wrap. The team reading is the briefing. This note waits. No code until note-taking is done.

**Truth.** Wrap follows the inspector width. Default on. The longest-line size is the hole.

**Reason.** Line wrap was already set, and the pane still scrolled sideways.

**Avoidances.** Do not treat setLineWrap as the fix. Do not start with soft wrap off.

**semantic-key:** PK-6826

# Notebook buttons lose 40 percent of their height, and every button gains a beveled shadow.

The operator's words: the buttons are too big on the notebook pane. Reduce them in height by 40 percent, and adjust the fonts so the labels still fit. Also add a beveled shadow to all buttons so they are more noticeable as buttons.

Notebook buttons lose 40 percent of their height. The font on those buttons shrinks so the label still fits. Every button gains a beveled shadow.

The height cut is the notebook pane: Run, New turn, Clear turns, Preview, the incrementor, the card buttons, and the View modal Close. The shell buttons keep their height. The button painter paints a flat line border today. The bevel is a raised shadow on every button that painter touches, shell and notebook, so a button reads as a button. The fill colors stay. Feasible. This note waits. No code until note-taking is done.

**Truth.** Forty percent off the notebook height. Bevel on every painted button. Shell height unchanged. Fills stay.

**Reason.** The notebook buttons read as too tall, and a flat border does not read as a button.

**Avoidances.** Do not shorten the shell buttons. Do not change the fill colors.

**semantic-key:** PK-6827

# The pull-down sits on the east of the capsule and the spinner sits on the west.

The operator's words: swap the spinner and the user pull-down. When the spinner activates it pushes the pull-down to the left. The pull-down should be on the east and the spinner on the west of the encapsulating border layout.

The pull-down sits on the east of the capsule. The spinner sits on the west of that same capsule. The capsule stays on the east of the footer.

The footer builder puts the combo on the west and the thinking spinner on the east today. Showing the spinner widens that east child, and the pull-down shifts left. After the swap the pull-down stays on the outer east corner while the spinner shows and hides. The west button flow stays. Feasible. This note waits. No code until note-taking is done.

**Truth.** Combo east, spinner west, capsule still footer-east.

**Reason.** The spinner on the east shoved the pull-down every time a run started.

**Avoidances.** Do not move the west button flow. Do not leave the combo on the west of the capsule.

**semantic-key:** PK-6828

# This pad is the wrap-column note, the plan that superseded it is stamped, and the next stage stays closed.

Mode: note plus build. Superseded by the fifth tweak plan, which is stamped and approved. The wrap-column section stays. The next stage stays closed.

The source is the previous note pad, which is locked. The fourth tweak plan's packages stay compiled. It depends on that plan and on the stages paper.

The previous pad stays locked. This pad is the wrap-column note. The operator asked to take it on this turn.

The class stays `SshClientApp`. Shell launch stays `java -cp`. Do not edit `src/main/resources/application.yaml`. The local engine stays `http://localhost:11434`. The code model default stays `qwen2.5-coder:7b`. A context length of 0 omits `num_ctx`. No API key is added.

**Truth.** The pad is one note. The successor plan is stamped. The section itself stays.

**Reason.** The operator asked for the wrap width on this turn, and the plan later carried it.

**Avoidances.** Do not edit the locked previous pad. Do not drop the wrap-column section. Do not open the next stage.

**semantic-key:** PK-6829

# The operator approved the column width and named the build.

The operator approved the wrap-column note and named the build. The solution above is the code.

The column is the width. The pane is only the window it sits in.

Pixels were the wrong save. The integer is the save. The font supplies the pixels.

The wire stays quiet.

The new control sits on the checkbox. The row control stays on the footer.

The checkbox still says `soft wrap`.

**Truth.** Approved and named. The integer is the save. The checkbox keeps its words.

**Reason.** A pixel save would go stale on the next font change.

**Avoidances.** Do not save pixels. Do not move the row control off the footer. Do not rename the checkbox. Do not touch the wire.

**semantic-key:** PK-6830

# Viewport tracking has no aligned width, a hidden horizontal bar clips a wider column, and a pixel width goes stale when the font changes.

1. The previous soft-wrap note tracks the viewport. The wrap edge is whatever the pane is, so there is no width to align. A divider move changes it.
2. A fixed column wider than the pane is clipped if the horizontal bar stays hidden. The previous note hid that bar while wrap was on.
3. A pixel width goes stale when the font changes. The shell preference for card rows is a character-shaped integer, and this one belongs with it.
4. The footer incrementor sets card rows. A second one has to be obvious, or the two controls get mixed up.
5. The hyphen rule and the identifier have no spaces. Word wrap will not cut them. A full preview is 80 characters of body plus the `N. role` prefix, so at a width of 80 a long preview folds once.
6. The open cell panes and the View modal are still later seats.

**Truth.** Six problems: no aligned width, a hidden bar, stale pixels, two incrementors, unsplittable tokens, and later seats.

**Reason.** A viewport edge and a pixel save both fail the operator's ask for a stable character width.

**Avoidances.** Do not hide the horizontal bar while a column can be wider than the pane. Do not save pixels. Do not cut the hyphen rule or the identifier on word wrap.

**semantic-key:** PK-6831

# The wrap width is a saved character count, default 80, set by a second incrementor beside the checkbox.

The operator's words: soft wrap works, but there is no defined width, so it does not align with the inspector width. The fix is a new plus and minus control for the width of the soft wrap, next to the soft-wrap checkbox. When wrap is enabled, it enforces the wrap on the configured width. Make the count persist. Default the control to 80 characters.

The wrap width is a character count. Default 80. The count is saved. While soft wrap is on, the inspector wraps at that count.

The problems and the solution are the child sections.

**Truth.** A saved column count, default 80, beside the checkbox.

**Reason.** Tracking the pane left no width to align.

**Avoidances.** Do not leave the wrap edge as whatever the pane happens to be. Do not default to a count other than 80.

**semantic-key:** PK-6832

# The area stops tracking the viewport, the inner bar is as-needed in both states, and the key is ui.aetheris.inspector.wrap.columns.

1. The soft-wrap area stops tracking the viewport. While wrap is on, the preferred width is the column count times the live monospaced character width. While wrap is off, the longest line returns.
2. The inner inspector bar is as-needed in both states. A column wider than the pane scrolls, and the arrow buttons sit on that bar. The outer bar stays hidden.
3. The count is `ui.aetheris.inspector.wrap.columns` in `~/.casserole/ssh-client.yaml`, through `SshClientProperties`. A missing key means 80. It is not written by the missing-key default writer. It is not `application.yaml` and not a vault column. The checkbox still starts on and is not saved.
4. A second incrementor sits beside the `soft wrap` checkbox. Minimum 1. Plus and minus apply immediately. A typed value waits 800 ms. The tooltip says `soft wrap columns`. The footer incrementor still sets card rows. Both buttons take the short notebook height.
5. Word wrap stays. The hyphen rule and the identifier stay one token. Raising the count is how a long preview stays one row.
6. Cell panes and the View modal stay off this handler. Assembly stays oldest first. Newest-first paint stays newest first.

The `N. role` prefix plus 80 characters of body is why a width of 80 folds a long preview once.

**Truth.** Column times character width. As-needed inner bar. Key defaults to 80. Tooltip `soft wrap columns`. Minimum 1. Typed wait 800 ms.

**Reason.** The operator asked for a persistable width next to the checkbox, defaulting to 80.

**Avoidances.** Do not track the viewport. Do not hide the inner horizontal bar while wrap is on. Do not write the key from the missing-key defaults. Do not save the checkbox. Do not let the footer incrementor set the column count.

**semantic-key:** PK-6833

# One notebook tab shows the vault of the user on the focused terminal, and a different user is written before the load.

One notebook tab shows the vault of the user on the focused terminal session. A different user writes the open vault, then loads that user's vault, and the listener moves to that connector. The same user does not reload. A session that is not a connected session speaking the notebook pipe does not close the open vault.

The ask was to establish which notebook is active according to the active user in the terminal.

**Truth.** One tab. Write then load on a user change. Same user does not reload. A non-pipe focus leaves the vault open.

**Reason.** The pane was sticking on the user who connected last.

**Avoidances.** Do not reload the same user. Do not close the vault when focus is not a connected notebook session. Do not skip the write.

**semantic-key:** PK-6834

# The seven hand checks in the second tweak plan were not clicked, and they are closed with this file.

The hand-check section of the second tweak plan was not clicked. The seven checks in that plan stay unrun. They are closed with this file. They are not an open slice.

The operator's word was to build what had just been approved. Closing the file includes those unrun checks. It does not claim they were clicked.

**Truth.** Seven checks, unrun, closed with the contract, not an open slice.

**Reason.** An unclicked list was blocking the next pad.

**Avoidances.** Do not claim the seven checks were clicked. Do not leave them as an open slice.

**semantic-key:** PK-6835

# An excluded cell is absent from the inspector, including the last cell, and a state click refreshes the list.

The inspector lists the working set. An excluded cell is absent, including when it is the last cell. An `I`, `E`, `P`, or `O` click refreshes the list in that click. The card stays on the canvas. A collapsed included cell stays on the chain.

The ask was that the inspector show only what is active on the memory-list chain.

**Truth.** Chain only. Last cell included. Refresh on the same click. Canvas card stays.

**Reason.** A stale list after exclusion showed cells that would not be sent.

**Avoidances.** Do not leave an excluded cell in the list. Do not hide the canvas card. Do not drop a collapsed included cell.

**semantic-key:** PK-6836

# A finished Run on the last cell creates the next turn, puts the caret in that input, and scrolls that user area into view.

A finished Run on the last cell creates the next turn, puts the caret in that turn's input, and scrolls that user area into view. The answer stays above it. The header focus-hold is unchanged.

The ask was to shift focus straight to the next new turn cell so the last result stays in view without an extra scroll before the next input.

**Truth.** Caret in the new input. User area scrolled into view. Answer above. Header hold unchanged.

**Reason.** The operator was landing in the answer and scrolling to type.

**Avoidances.** Do not change the header focus-hold. Do not leave the caret in the answer on this path.

**semantic-key:** PK-6837

# The brief is the assistant text from the first character, and empty output leaves the brief empty.

The brief is the assistant text, from the first character, one line, or two when the header row is at least two lines tall. Empty output leaves the brief empty. User text is not the brief.

Plain truncation. No summary engine.

**Truth.** Assistant text only, one line or two, empty when the output is empty.

**Reason.** The input is already on the working-set walk when the cell is included.

**Avoidances.** Do not use user text as the brief. Do not fall back to the input. Do not summarize.

**semantic-key:** PK-6838

# The footer button clears the active canvas, including burners and their trace rows, and leaves one empty included turn.

The footer button clears the active canvas, including burner cells and their trace rows, and leaves one empty included turn. Terminal `clear` is not called.

The session stays. The vault is written. Burners are not spared. Trace rows go before cell rows. One empty `INCLUDED` turn is added and the order is saved.

**Truth.** Canvas only. Burners and traces go. One empty included turn remains. The session stays.

**Reason.** The terminal clear and the canvas clear are different memories.

**Avoidances.** Do not call terminal clear. Do not spare a burner. Do not leave the trace rows behind.

**semantic-key:** PK-6839

# Collapsed is a boolean on the cell, default false, and an older disk vault gains the column when it is missing.

`collapsed` is a boolean on the cell, default false, stored on insert, update, and load. An older disk vault gains the column when it is missing.

It is not a fifth `CellState`.

**Truth.** The flag is persisted. A vault that predates the column gains it. The four states stay four.

**Reason.** An existing disk vault does not alter itself just because the create statement is unchanged.

**Avoidances.** Do not make collapse a fifth state. Do not skip the column on an older vault. Do not default the flag to true.

**semantic-key:** PK-6840

# User text is #00FF66 on #1A1A1A, assistant text and the View modal are #FFFF55, and an excluded cell is gray.

User text is `#00FF66` on `#1A1A1A`. The user border stays `#3CB371`. Assistant text and the View modal are `#FFFF55` when there is output. An excluded cell is gray in the user area, the assistant area, and the brief.

`#FFFF55` is the Swing stand-in for terminal SGR 93.

**Truth.** Emerald on the dark field, medium-green border, yellow output, gray ghost including the brief.

**Reason.** The edge and the words must not be the same green, and the ghost must still read.

**Avoidances.** Do not recolor an excluded cell with the emerald or the yellow. Do not change the user border from `#3CB371`.

**semantic-key:** PK-6841

# A header rebuild restores card Y minus view Y, and a collapsed card focuses the header control.

A header control that rebuilds the pane restores `cardY - viewY` and keeps focus on that card. A collapsed card focuses the header control. A new turn, the end of a run, and Clear turns may still scroll to the cell they create.

View does not rebuild. Do not scroll the whole card into view just to perform the hold.

**Truth.** The delta is card Y minus view Y. Collapse focuses the toggle. New turn, Run, and Clear turns may still move.

**Reason.** Rebuilding the scroll pane was throwing the operator to the top.

**Avoidances.** Do not apply the hold to View. Do not block a deliberate scroll to a cell the action just created.

**semantic-key:** PK-6842

# Right-click offers Import..., and a file over 1 MiB or a NUL in the first 8 KiB refuses.

Right-click on a cell offers `Import...`. The file replaces that cell's user text. The assistant area refuses. `OUTPUT_ONLY` refuses. Over 1 MiB refuses before the read. A NUL in the first 8 KiB refuses. The state does not change. The import does not run the cell.

The file is UTF-8. 1 MiB is 1048576 bytes. There is no import button on the header. No focused input cell refuses, with a status line. The import does not push the pipe.

**Truth.** Menu item `Import...`. Replace user text. Refuse output-only, assistant press, over 1048576 bytes, and a NUL in the first 8 KiB.

**Reason.** A header button was clutter, and a bad file must not change the cell.

**Avoidances.** Do not read a file larger than 1 MiB. Do not import into the assistant area. Do not change state. Do not run the cell.

**semantic-key:** PK-6843

# The state controls stay I, E, P, and O, and the collapse control reads plus when collapsed and minus when open.

The state controls stay `I`, `E`, `P`, and `O`, with the tooltips Included, Excluded, Input only, and Output only. The collapse control reads `+` when collapsed and `-` when open.

Full words are not the labels.

**Truth.** Letters on the face. Four tooltips. Plus means collapsed. Minus means open.

**Reason.** The letters were easy to forget, and full words crowded the header.

**Avoidances.** Do not replace the letters with full words. Do not swap the plus and minus meanings.

**semantic-key:** PK-6844

# One card carries the header open or collapsed, and the collapsed card is the header plus the brief.

One card carries the header while it is open and while it is collapsed. The body hides when `collapsed` is true. The open card keeps its height cap. The collapsed card is the header plus the brief.

The header buttons stay. There is no second copy of the state buttons.

**Truth.** One identity. Body hides. Collapsed height is header plus brief.

**Reason.** A second card would bring back the second focus owner the scroll hold was written to kill.

**Avoidances.** Do not render a second set of state buttons. Do not remove the header when the body hides.

**semantic-key:** PK-6845

# One rectangle is stored at ~/.aetheris/{user}/view-bounds.txt, and a miss opens at 720 by 480, centered.

One rectangle is stored at `~/.aetheris/{user}/view-bounds.txt` as `x`, `y`, `width`, and `height`. It is not a cell column and not an `aetheris.yaml` key. A saved rectangle that misses every screen opens at 720 by 480, centered. Close writes the dialog bounds.

The preference is global for that user, not per turn, and not on the cell record. The next open, including after a restart, uses it. Do not throw on a bad rectangle.

**Truth.** Four keys in a text file beside the user. Off-screen falls back to 720 by 480, centered.

**Reason.** The dialog was born at 720 by 480 every time, and a resize was forgotten.

**Avoidances.** Do not store the rectangle on the cell or in aetheris.yaml. Do not throw when the rectangle misses the screen.


**semantic-key:** PK-4433

## Considerations

These four notes are relevant, they are not rows, and silence leaves them here until one of them is named.

Show the estimate, the server system line, and the configured cap beside the working set. The pin, the pipes, and the mood line all grow that body. The operator currently cannot see a drop.

Put the parsed air-gap word on the status line. The line already says vault mode, whether the frame is structured or raw, and `engine: ollama`. The parsed air-gap flag is the missing word. The word does not, by itself, enforce the flag.

Replace the next column probe with one schema version row before `group_id`, status, and the tool knob land. The collapsed probe can stay as history.

Define `k` before any pipe is built. The least surprising index is the send-order position, oldest first, skipping cells the walker skipped. The inspector's painted numbers are a different sequence.

None of these pull a later merge, a search-engine index, the SQL grammar, incremental paged memory, the graph paint, contract agents, or the later memory product into the slice. Those stay-out rows still stand.

**Truth.** Four considerations sit beside the holes: an inspector window line, an air-gap word on the status line, one schema version, and a send-order definition of `k`. Naming one adds it. Silence does not.

**Reason.** Each one is the smallest honest fix for a hole that has no smaller fix, and writing them as rows too early would drag the stay-out list into the slice.

**Avoidances.** Do not treat an unnamed consideration as a slice row. Do not use the inspector's painted numbers as `k`. Do not let the air-gap word replace the server check. Do not add another one-off column probe for the next column. Do not pull the stay-out rows in beside these notes.

**semantic-key:** PK-6846

## Groups and pipes keep the columns the insert already promised

`group_id` and `execution_status` are stored on the cell, and the update writes the same card the insert already promised.

`group_id` is a nullable column. Null means the cell is sequential.

`execution_status` is a nullable column. Null means never run. `COMPLETED` means the reply was stored. `FAILED` means `reason: failed`, `reason: air-gap`, `reason: engine-down`, or `reason: too-large`. `reason: cell-busy` does not write `FAILED`. `CANCELLED` means Stop landed on that cell and the body stays. An empty assistant body with a null status is never run. An empty body with `FAILED` is a failure.

The assistant body is replaced only on `COMPLETED`. A pipe reads that body when it is non-empty, including after a later `CANCELLED`. An empty body is not a source. A latest status of `FAILED` stops the pipe. `CANCELLED` does not by itself fail a sibling member.

`bindUpdate` writes the same mutable columns the insert already writes, plus `group_id` and `execution_status` once version 2 exists. The argument is the canvas card. No second data holder.

Day-one merge stays `CONCAT` with a model banner on each part. The banner is a label. The word is `JOIN`, then that member's execution target. An empty stored target shows as `ollama`, because that is the target that ran. The algorithm token stays `CONCAT`. A later model-merge stays parked.

Siblings stay out of one another's working sets. An ephemeral cell cannot push.

**Truth.** Null `group_id` is sequential. Null `execution_status` is never run. The other tokens are `COMPLETED`, `FAILED`, and `CANCELLED`. `cell-busy` does not write `FAILED`. The body changes only on `COMPLETED`. A non-empty body stays a pipe source after `CANCELLED`. The banner word is `JOIN` and the algorithm token is `CONCAT`.

**Reason.** An empty assistant body could not tell never-run from failure, and an update that skipped the lineage columns dropped a group edit on the next load.

**Avoidances.** Do not write `FAILED` for `cell-busy`. Do not replace the assistant body on `FAILED` or `CANCELLED`. Do not let an empty body be a pipe source. Do not let `FAILED` through the pipe. Do not let `CANCELLED` fail a sibling. Do not add a second data holder for the update. Do not change the algorithm token to `JOIN`. `JOIN` is the label. Do not let siblings into one another's working sets. Do not let an ephemeral cell push.

**semantic-key:** PK-6847

## Search gains a door, a canvas, and a literal needle

Search is the vault `LIKE` query the notebook calls, scoped to the open canvas, with a literal needle and no rows for an empty needle.

Stage 1 `LIKE` is `VaultStore.search`. It wraps the needle in `%` on both sides, it has no canvas predicate, and the shell never calls it. `%` and `_` in the needle are wildcards. An empty needle matches every non-null body. Burner NULL bodies do not match. That is the whole remainder this door closes.

The query takes the open canvas id. Hits from another canvas stay out.

`%`, `_`, and the escape character in the needle are escaped. The operator's characters are literals.

An empty needle returns no rows. It does not list the vault.

A search field on the notebook calls `VaultStore.search`. A hit selects that cell. The hit does not change cell state.

Wildcard `LIKE` is sufficient for this project. The door above stays the search. A search-engine index is not scheduled. A heavier layer is a later lift only when a real need is named. That layer may be this project's own search layer. It is not a commitment to any particular index.

**Truth.** `VaultStore.search` takes the open canvas id, escapes `%`, `_`, and the escape character, and returns no rows for an empty needle. A hit selects the cell and does not change state. Burner NULL bodies still do not match.

**Reason.** The query existed and the notebook never called it, so an empty needle would have listed every non-null body and a percent sign in the needle would have been a wildcard.

**Avoidances.** Do not search across canvases. Do not leave `%` or `_` as wildcards in the operator's needle. Do not list the vault for an empty needle. Do not change cell state on a hit. Do not schedule a search-engine index. Do not let burner NULL bodies match.

**semantic-key:** PK-6848

## Stop against a group

Footer Stop cancels the focused cell, and each member cancels only itself.

This closes the open item.

Footer Stop stays the existing one-cell control. It sends `TYPE_CANCEL` for the focused cell. The clock on the footer follows that cell.

Each group member carries its own cancel. That control sends `TYPE_CANCEL` for that member's cell id.

Stop does not cancel every member. `CANCELLED` is written on the cell whose cancel matched. A late reply for that cell is not painted. The assistant body is left as it was.

**Truth.** Stop cancels the focused cell. A member control cancels that member. Stop does not cancel the group. The matching cell becomes `CANCELLED` and its late reply is not painted.

**Reason.** One `stoppedId` could not say whether a group stop meant the focused member or every member, and the group row cannot be built until that answer is named.

**Avoidances.** Do not cancel every member from footer Stop. Do not paint a late reply for the cell whose cancel matched. Do not clear a sibling because one member was stopped. Do not replace the assistant body on `CANCELLED`.

**semantic-key:** PK-6849

## Groups and CONCAT

Operator editing creates the group, and day-one merge is `CONCAT` with a `JOIN` label on each member.

Promote the focused cell into a group. That cell becomes the first member. Add a cell to the group. The new sibling has a different `execution_target`. Remove a cell from the group. It returns to the sequential column, or it is deleted. Members scroll horizontally. The merge cell is below the members.

Fields already named on the cell, and unused until this slice:

- `branch_id`, default `main`
- `column_position`
- `group_id`, and null means sequential

Fork is a new column, a new `branch_id`, and a parent equal to the fork cell.

Merge points at an explicit node or at variable references. Parallel-group siblings stay out of each other's working sets.

Lineage walks `target`, then `parent`, then `root`. Ignore siblings unless the walk is a merge or a `{{cell[k].output}}` reference.

Execute uses the host `llm-exec` lane. One in-flight execute per cell. Interface work stays on the interface thread. Day-one member replies use `0x0003` and the cell id. `0x0100` stays the later range and is not allocated in this slice. Backpressure stays one in-flight execute per `cell_id`. Do not invent a second acknowledgement protocol.

The merge algorithm is `CONCAT` with model banners. The banner is a label. The word is `JOIN`, then that member's execution target. An empty stored target shows as `ollama`. The algorithm token stays `CONCAT`. A later spec can highlight a different merge once the infrastructure shows the gap. Until then, model-merge and pick-winner stay parked. The picture is two calls joined into one merge cell. The label is on every member.

`execution_status` is null, `COMPLETED`, `FAILED`, or `CANCELLED`. The client writes it when the frame arrives. A data frame stores `COMPLETED`. `reason: failed` stores `FAILED`. `reason: cell-busy` does not. A matching cancel stores `CANCELLED` and leaves the body. The body changes only on `COMPLETED`. A non-empty body stays a source after `CANCELLED`.

`bindUpdate` persists `parent_cell_id`, `branch_id`, `column_position`, `group_id`, and `execution_status` from the canvas card.

One `UserRun` holds a map of `CellRun`. The phase is `RUNNING` or `STOPPED`. The footer Stop and the footer clock follow the focused cell.

**Truth.** Day-one merge is `CONCAT`. The label is `JOIN` plus the member target, or `ollama` when the stored target is empty. Null `group_id` is sequential. Status is null, `COMPLETED`, `FAILED`, or `CANCELLED`. Stop cancels the focused cell. A member cancels itself. Replies stay on `0x0003`.

**Reason.** An unnamed merge would be rebuilt as a model choice or as a cancel-all, and the columns the insert already named would die on the next load if the update skipped them.

**Avoidances.** Do not let siblings into each other's working sets. Do not treat every column as a merge. Do not allocate `0x0100` for day-one replies. Do not invent a second acknowledgement protocol. Do not change the algorithm token to `JOIN`. Do not write `FAILED` for `cell-busy`. Do not replace the body on cancel. Do not ship model-merge or pick-winner on day one.

**semantic-key:** PK-6850

## Pipes, chain, and fork columns

A pipe is a static copy, a variable that resolves on execute, or a chain, and one walker builds both the send and the inspector.

Text push copies the source response into the target prompt. The copy is static.

Variable push is `{{cell[k].output}}`. It resolves at each target execute.

Chain is a topological execute of the dependencies. The gesture is Ctrl+Shift+F5.

The assistant body is replaced only on `COMPLETED`. A pipe reads that body when it is non-empty, including after a later `CANCELLED`. An empty body is not a source. A latest status of `FAILED` stops the pipe. An ephemeral cell cannot push. A cycle fails. A chain stops on `FAILED`.

Assembly order the client already walks, with variable resolve added:

1. Skip `EXCLUDED`.
2. Skip empty ephemeral content.
3. `INPUT_ONLY` contributes the user turn only.
4. `OUTPUT_ONLY` contributes the assistant turn only.
5. `INCLUDED` contributes both.
6. Resolve variables on the target. This is the Stage 2 step.
7. Append the target prompt.

The inspector shows that list before Run.

That list and the send are one walker. `k` is 1-based among kept cells. The estimate is `HeuristicTokenCounter` on the walker text, including both system lines. An empty string counts as 1. A payload over 1 MiB, prefix included, fails the cell in a banner. Notebook scope stays one chunk. `AmwpCodec.fits` is the measure. The prefix is 24 bytes.

**Truth.** Three pipe modes are static copy, `{{cell[k].output}}`, and a chain on Ctrl+Shift+F5. One walker resolves `k` as the 1-based index of kept cells and estimates with `HeuristicTokenCounter`. Over 1 MiB, prefix included, the cell fails in a banner.

**Reason.** Stage 1 already walked the seven-step order except the resolve. A second walk, or a `k` taken from paint order, would send a different body than the inspector showed.

**Avoidances.** Do not resolve variables on the host. Do not use paint order as `k`. Do not let an ephemeral cell push. Do not let an empty body be a source. Do not let `FAILED` through. Do not drop a non-empty body only because the status is `CANCELLED`. Do not reassemble fragments to get under 1 MiB. Do not keep a second estimator beside `HeuristicTokenCounter`.

**semantic-key:** PK-6851

## Search

Search is required, the engine for this slice is wildcard `LIKE` on the client database, and a search-engine index is not scheduled.

The door is the open canvas, a literal treatment of `%` and `_`, an empty needle that returns no rows, and a hit that selects the cell. The notebook field calls `VaultStore.search`. A hit does not change cell state. Burner NULL bodies do not match.

A heavier layer waits until a real need is named. That layer may be this project's own. It is not a commitment to a particular index. Older files that still name an index stay as they are. This lock is the operative reading.

**Truth.** This slice completes canvas-scoped wildcard `LIKE` with escaped `%` and `_`, no rows for an empty needle, and selection without a state change.

**Reason.** Search was required and the only query had no door, so the needle was a wildcard across the whole vault.

**Avoidances.** Do not schedule an index. Do not repair this page back into an index schedule. Do not list the vault for an empty needle. Do not change cell state on a hit. Do not match burner NULL bodies.

**semantic-key:** PK-6852

## Export and import

The package is `.aetheris` wire text, version 1, and import mints a new id for every cell.

The body is the wire text. The file is versioned. The first package version is 1. Counted serials stay 1-based. `k` is already 1-based. The vault schema starts at 1.

Identifiers are remapped. On import each cell id becomes a new UUID minted for that import. Parent, branch, group members, and `{{cell[k].output}}` are rewritten to the new ids. The import is a copy. The same file can be imported again.

A one-chunk frame keeps `frag_index` 0 and `frag_count` 1.

Burner bodies are stripped by default.

The door is not the local file chooser and not the starter kit. The constitution file stays as written.

Encryption at rest stays on the Stage 3 list. It does not enter this slice. The algorithm named for that later list is AES-256. It is not built here.

**Truth.** Export version is 1. Import mints a new UUID per cell and rewrites parent, branch, group members, and pipe references. Burner bodies are stripped. The frame stays one chunk. The door is its own door.

**Reason.** A package that kept the old ids would collide on a second import, and a burner body in the file would leave the machine in the export.

**Avoidances.** Do not reuse the local chooser or the starter kit as this door. Do not keep old ids on import. Do not include burner bodies by default. Do not change `frag_index` or `frag_count` on a one-chunk frame. Do not add at-rest encryption in this slice. Do not use JSON as the interchange.

**semantic-key:** PK-6853

## Context pin and keyword filter

While the pin is on, every keystroke filters by keywords and never calls a model, and OK commits non-matches to `EXCLUDED`.

The filter token is `LEX-FILTER`. The pin token is `CONTEXT-PIN`. The mood layer runs after OK, and only if a shaper is configured. That later step is a dialog only. Its token is `MOOD-LAYER`.

The filter steps are these.

- Split on whitespace and punctuation.
- Drop the stopwords `a`, `the`, `and`, `of`, and `to`. The ellipsis in the lock is not a second list.
- Remaining tokens are AND substring matches, case-insensitive, against cell input and cell output.
- Burner NULL bodies are not searched.
- Matches highlight. Non-matches dim. Nothing is written until OK.

On OK, non-matches commit to `EXCLUDED`. There is no fifth state. Ignore means `EXCLUDED`. An `EXCLUDED` match promotes to `INCLUDED`. `INPUT_ONLY` and `OUTPUT_ONLY` keep their state. Matches that were already `INCLUDED` stay `INCLUDED`. Burners still skip payloads. After the commit, the four-state buttons still override.

Turning the pin off clears the highlights and leaves the prune in place. The operator un-ghosts with the buttons. The pin does not restore them. The pin stays on until the operator turns it off. A new cell is filtered live the same way. The mood layer does not run again until the next OK.

The pin sits on the inspector rail so the canvas stays the cards.

Already on the floor, and still required: cell cards, `cell_order`, the four-state buttons, ghost paint, the inspector preview, and vault persistence of state.

New in this slice: the pin control, the tokenizer, the highlight and dim preview, the OK commit, the stay-on rule, and the off rule.

No new wire. No new channel. Stemming and synonyms belong to the mood layer. There is no third matcher.

**Truth.** `LEX-FILTER` is a live keyword filter with no model call. Stopwords are `a`, `the`, `and`, `of`, and `to`. OK writes non-matches as `EXCLUDED` and promotes an excluded match to `INCLUDED`. Off clears highlights and leaves the prune.

**Reason.** The four buttons already isolate cards, and a keystroke that called a model would hide a prune behind a generation the operator did not ask for.

**Avoidances.** Do not add a fifth state. Do not call a model on a keystroke. Do not write before OK. Do not search burner NULL bodies. Do not treat the ellipsis as extra stopwords. Do not let the pin restore `EXCLUDED` when it turns off. Do not run the mood layer again before the next OK. Do not add a third matcher. Do not add a channel for the pin.

**semantic-key:** PK-6854

## Mood layer

The mood layer runs after the keyword filter has committed, and only when a shaper is configured, and it adds one system line for the whole conversation.

That extra working-set row is ephemeral. It is one system-role message. It is not a canvas cell. It is not a terminal memory row.

The skip message in the dialog is `keyword only.`

The shaper call is a `JDialog` plus `AetherisWorker`. It does not run on the interface thread. It does not run on the host lane.

The default shaper is the local model already in use. The published default for that code model is `qwen2.5-coder:7b`. The local base is `http://localhost:11434`. The file key is `mood-shape`, under the `notebook` root. It holds the shaper model id. A bare key does not load. An empty id is still the local model in use.

If the id is missing, the air-gap forbids the call, or the model cannot be reached, this phase is skipped. The keyword filter still stands.

A remote adapter is allowed as a configured shaper. It is not required. The remote vendor is not required to run the notebook. The remote URL, when that adapter is used, is `https://api.x.ai/v1/chat/completions`. This phase is not a spec compiler.

Cancel keeps the keyword-filter commit and adds no mood row. Approve appends one system-role shape message for the entire conversation: style, mood, and symbolic modifiers. The walker puts the host line first and this line second. They are not fused. The line is appended to the working set. It is not written into cell bodies. The inspector shows it in the header. An unknown wire role fails the cell.

The mini-chat is scoped to the dialog. It is not a canvas tab. It is not terminal chat. It is ephemeral and burner.

Not in this phase: a model call on the keystroke, a second paged-memory writer, a fifth state, a search index, an embedding database, or a third matcher.

`LEX-FILTER` is the first Stage 2 canvas slice. `MOOD-LAYER` follows it. Both need the Stage 1 notebook. `MOOD-LAYER` also needs the Stage 1 provider interface and a configured shaper. That order is the map's staging note. A stamped plan may sequence other remainder rows around it. This page does not convert the note into a plan.

Transport is an ordinary Stage 1 execute on notebook scope. No extra channel.

**Truth.** `MOOD-LAYER` follows a committed `LEX-FILTER`. It adds one ephemeral system line, not a cell, only after approve and only when `mood-shape` names a reachable shaper the air-gap allows. Otherwise the dialog says `keyword only.` and the filter stands.

**Reason.** A shape line written into a cell, or fused with the host prompt, cannot be dropped without editing the cards, and a keystroke model call would no longer be a keyword filter.

**Avoidances.** Do not make the mood row a canvas cell or a terminal turn. Do not write it into a cell body. Do not fuse it with the host line. Do not run it on the interface thread or the host lane. Do not load a bare `mood-shape`. Do not require the remote vendor. Do not call a model on the filter keystroke. Do not add a channel, a second writer, a fifth state, an index, an embedding store, or a third matcher. Do not convert the staging note into a plan by itself.

**semantic-key:** PK-6855

## A cancelled rerun keeps the last good output

A pipe reads the assistant body, and a non-empty body stays a source after `CANCELLED`.

The finding is that `CANCELLED` replaced `COMPLETED`, so a pipe that required `COMPLETED` hid the body.

The pipe reads the assistant body. Non-empty is a source, including when the latest status is `CANCELLED`.

Empty is not a source. That covers a first run that was cancelled before any success.

Latest status `FAILED` stops the pipe. The body stays on the card so the inspector can still show it. The pipe does not push it.

A chain stops on `FAILED`. `CANCELLED` does not stop a sibling. An ephemeral cell still cannot push.

The seam is no new column. The body is the output. The status is the latest attempt. Those were already two fields.

**Truth.** Non-empty assistant text is a pipe source after `CANCELLED`. Empty is not a source. `FAILED` stops the pipe and leaves the text visible. `CANCELLED` does not stop a sibling. An ephemeral cell cannot push.

**Reason.** Requiring `COMPLETED` made a stopped rerun hide the last good reply from the chain, even though the text was still on the card.

**Avoidances.** Do not add a column for the last good output. Do not require `COMPLETED` before a non-empty body can be read. Do not let `CANCELLED` stop a sibling. Do not let an ephemeral cell push. Do not hide the failed text from the inspector.

**semantic-key:** PK-6856

## Java export writes the stored parent and the stored branch, and it leaves the lock column out of the file.

`exportToon` writes `version: 1`, the canvas name, and `cells:`. Each cell is one list item. The parent line is the stored parent. The branch line is the stored branch. The file does not contain `head_lock`. A burner body is empty unless the operator keeps burners. The other lines are the id, the turn index, the column, the group, the target, the context state, the ephemeral flag, the collapsed flag, the execution status, and the two bodies.

**Truth.** The Java version-1 export writes the stored parent and the stored branch and omits `head_lock`.

**Reason.** The lock is a vault column. This writer does not copy that column into the file a person carries.

**Avoidances.** Do not add a `head_lock` line to this export. Do not replace the stored parent or the stored branch with an empty parent and `main`. Do not write a burner body when the operator strips burners.


**semantic-key:** PK-6857

# Mood session

When a saved mood is present, the rising field already holds that trim. A mood send carries those tokens in the header. Clear mood drops the string and the chat.


**semantic-key:** PK-4674 PK-4675 PK-4676

# Pin bar

The pin bar matches the other client's pin bar: it folds off the canvas, P brings it back, and only the pin box turns the filter off.


**semantic-key:** PK-4677 PK-4678 PK-4679 PK-4680

# Scroll focus

A press on the prompt or the reply, including its scrollbar, holds the wheel on that pane. A press anywhere else on the card returns the wheel to the canvas.


**semantic-key:** PK-4681 PK-4682 PK-4683 PK-4684 PK-4685 PK-4686 PK-4687 PK-4688 PK-4689 PK-4690 PK-4691 PK-4692

## The button bars fold to one thinking line

BB1 and the encapsulated BB2 sit in a parent fold.

The fold mark sits at the far left of BB1. A divider sits between that mark and Run. The bar starts open. A fold drops the bars to the bottom of the canvas. The folded view is the thinking line. One grey line and the unfold mark sit at the left. BB1 leaves that view. BB2 leaves that view. The user combo leaves that view. Unfold restores the open bar.

The private method is seatFooterBars. The private functions are bb_fold_mark and paint_bb_folded_line. The test that locks the fold is the_bb_parent_folds_to_the_think_line. The Java client carries this fold. The Rust client carries this fold.

**Truth.** BB1 and the encapsulated BB2 sit in a parent fold. The fold mark sits at the far left of BB1. A divider sits between that mark and Run. The bar starts open. A fold drops both bars to the bottom of the canvas. The folded view is one grey line and the unfold mark at the left. Unfold restores the open bar.

**Reason.** The two bars stayed open. The canvas had no single control that dropped them to the thinking line. The parent fold puts both bars away. Unfold brings the open bar back.

**Avoidances.** The bar does not start folded. The fold mark does not sit to the right of Run. The folded view does not keep BB1. The folded view does not keep BB2. The folded view does not keep the user combo. Unfold does not leave the bars at the bottom.

**semantic-key:** PK-8248

## The fold mark is the width of the glyph

The BB1 fold mark is the width of the glyph.

The layout was widening that mark. Folded, the line is the height of the unfold glyph. The private method is lockFoldMark. The Java client carries this width. The Rust client stays as it stands.

**Truth.** The BB1 fold mark is the width of the glyph. Folded, the line is the height of the unfold glyph. The private method is lockFoldMark.

**Reason.** The layout was giving the mark a width of its own. The glyph no longer set the size. The folded line was taller than the unfold glyph.

**Avoidances.** The mark is not wider than the glyph. The folded line is not taller than the unfold glyph. The Rust client is not changed for this width.

**semantic-key:** PK-8249

## Each user keeps an isolated thinking pane

Each user keeps an isolated thinking pane.

A merge cell group is the only share. Every other thinking pane belongs to one user and one scope, and that pane persists on its own. The banner is the model defined for the armed cell. Clearing one pane leaves the other panes. The private seat is ThinkSeats. The private methods are seatThinkDelta, showThinkGlass, and armFocusedCell. The Java client carries this seat. The Rust client stays as it stands.

**Truth.** Each user keeps an isolated thinking pane. A merge cell group is the only share. Clearing one pane leaves the other panes. The banner is the model defined for the armed cell.

**Reason.** One glass was showing one shared log. A second user could see the first user's thought. The seat keeps each pane with its user, and a merge group is the only share.

**Avoidances.** A single-prompt cell does not share a pane with another cell. Clearing one pane does not clear another pane. The Rust client is not changed for this seat.

**semantic-key:** PK-8250

## The head lock is a padlock

The head lock is a padlock.

Unlocked, the padlock is grey and the shackle is open. Locked, the padlock is green and the shackle is closed. The icon sits at the far left of the canvas button bar, after the turn label. A divider sits between the icon and the marks I, E, P, and O. The icon is not one of those marks. The private seat is HeadLockMark. The private functions are paint_head_lock and paint_head_lock_divider. The Java client carries this lock. The Rust client carries this lock.

**Truth.** The head lock is a padlock. Unlocked, the padlock is grey and the shackle is open. Locked, the padlock is green and the shackle is closed. A divider sits between the icon and the marks I, E, P, and O.

**Reason.** The lock needed a picture of its own. The marks I, E, P, and O stay marks. The padlock sits to their left, with a divider between.

**Avoidances.** The padlock is not one of the marks I, E, P, and O. An open lock is not green. A closed lock is not grey. The icon does not sit to the right of those marks.

**semantic-key:** PK-8251

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
