# org.aetheris.swing

Component file. It carries how this package is built.

**Depends:** [java/org/aetheris/config/config](../config/config.spec.md), [java/org/aetheris/vault/vault](../vault/vault.spec.md), [java/org/aetheris/wire/wire](../wire/wire.spec.md), [java/org/casserole/app/ui/ssh/ssh](../../casserole/app/ui/ssh/ssh.spec.md), [java/org/casserole/chat/chat](../../casserole/chat/chat.spec.md), [java/org/casserole/edit/edit](../../casserole/edit/edit.spec.md), [java/org/casserole/mood/mood](../../casserole/mood/mood.spec.md), [java/org/roux/model/model](../../roux/model/model.spec.md), [java/org/roux/roux](../../roux/roux.spec.md), [java/org/roux/session/session](../../roux/session/session.spec.md), [java/org/roux/token/token](../../roux/token/token.spec.md), [java/org/roux/toon/toon](../../roux/toon/toon.spec.md)

## The composer beat is the FLASH

The composer beat is the FLASH. The track is 5 pixels. The needle is 2 by 9 in `RING_HEAD` on the true fill edge, standing 2 pixels above and below the track.

An amber post at 70 percent and a red post at 90 percent stay drawn, each 1 by 9. A count change lunges the needle 6 pixels toward the gates and eases back to the true edge. Crossing a gate flashes `RING_HEAD` once, and the fill takes that ink. The same flash is the beat when the settled model count replaces the estimate. One flash if both happen together. The words `est. tokens` wear the fill ink. No new color.

The bar is the fraction of the context window. The scale is linear. `est. tokens` carries a small count. Red still means the real window is nearly full. The wrapper is per model. One stored tax does not travel. The keystroke path stays the estimate. The model count is the correction after the text settles. `prompt_eval_cached_count` stays off the bar. `eval_count` stays off the bar. `prompt_eval_count` is the field.

The estimate is `(length + 3) / 4`. Empty text is 1. A tokenizer library does not own this gauge. The server stays. Roux chat memory keeps writing the estimate at write time. The ring stays the opened instrument. The heat bar is the always-on composer gauge. The inks are the ring inks. `EDGE_MATCH` is 0x3CB371. `RING_AMBER` is 0xF0A202. `RING_RED` is 0xE53935. `RING_TRACK` is 0x2A3440. `RING_HEAD` is 0xDCDCDC. `RING_BG` is 0x121212. An unknown window paints the empty track, the needle at the left cap, and the posts still drawn. An engine word that contains ollama uses num-ctx 131072. Grok and pool windows come from the rows the ring already reads. The application configuration stays.

The dip is named. After the text settles, Swing posts to the configured local model host on port 11434, path `/api/generate`, for model `deepseek-r1:7b`, and reads `prompt_eval_count`. `num_predict` is 1. `stream` is false. `keep_alive` is 300. The body is discarded. Debounce is 700 ms. A background daemon thread applies the result on the Swing thread. The paint path does not call. Text longer than 8000 characters skips the dip and keeps the estimate. Empty text does not call. A non-200 or a missing field keeps the estimate. A stale reply is dropped. The SSH plate polls the last screen line every 200 ms while the SSH tab is selected. The product tab hides the frame heat bar and the word. A repeat of the same estimate while a model count is held leaves the settled count. The weights for `deepseek-r1:7b` stay on the host that serves that engine. Rust has no HTTP client for this dip. Rust paints the beat on the estimate. Rust flashes when the estimate crosses a gate. Swing also flashes when the settled count replaces the estimate and the band stays.

The Swing SSH strip drops the `est. tokens` suffix. The colored word on the heat row is the `est. tokens` on that strip. `Vt100TokenWindow.withEstimate` stays. The Rust status sentence keeps `shell_status_with_tokens`, and the strip word is painted beside it.

The package-private types are `ComposerHeatbar` and `OllamaTokenDip` in `org.casserole.app.ui.ssh`, and `NotebookHeatbar` and `NotebookDip` in `org.aetheris.swing`. The private helpers are `feedComposerHeat` and `notebookWindow`. The bar carries a tooltip. It is not a new button. No new color key. The wire does not gain, lose, or reorder a system line.

The host changes first, the Swing client second, and the Rust client third.

**Truth.** The gauge is a 5 pixel track, a 2 by 9 needle in 0xDCDCDC standing 2 pixels past the track, posts at 70 and 90 that are 1 by 9, and a 6 pixel lunge that eases back. One `RING_HEAD` flash marks a gate crossing or a settled count replacing the estimate, not both. The fraction is linear. The keystroke count is `(length + 3) / 4`, and empty text is 1. `prompt_eval_count` may replace that count after a debounced generate of `deepseek-r1:7b` with `num_predict` 1, never from paint, never over 8000 characters, never on empty text. Ollama's unset window is num-ctx 131072. Rust stays on the estimate.

**Reason.** The operator needed a live fraction of the real window, with the same inks as the ring, without a second color and without a generation on every key. Red at 90 percent is the window nearly full. The settled count is a correction, not a second gauge.

**Avoidances.** Do not add a color. Do not put `prompt_eval_cached_count` or `eval_count` on the bar. Do not let one model's stored tax travel to another model. Do not call the host from the paint path. Do not dip on empty text or on text longer than 8000 characters. Do not apply a stale reply. Do not let a repeated estimate replace a held model count. Do not give Rust an HTTP client for this dip. Do not show the frame heat bar on the product tab. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a library, a lambda, or a test framework. Do not edit the application configuration or the Rust manifest.

**semantic-key:** PK-0086 PK-0087 PK-0088 PK-0089 PK-0090 PK-0091 PK-0092 PK-0093 PK-0094 PK-0095 PK-0096 PK-0097 PK-0098 PK-0099 PK-0100 PK-0101 PK-0102 PK-0103 PK-0104

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

## Delete removes the focused turn, renumbers the survivors from 1, and stays off for one turn or a grouped member.

Delete one turn from the canvas that is showing. The turns that remain take new labels from 1, in canvas order, with no gap. The label on the card is the stored turn index. New turn numbers the next card from the count of cells, so a gap would reuse a label that is still on screen.

Delete sits on the original footer, in the main button set, on the near side of the divider, just before that divider. It deletes the focused turn. With one turn on the canvas the button is disabled. It is enabled when two or more turns are on the canvas and the focused turn is not a grouped member. A grouped member is a turn with a group and a column position above 0. That turn cannot be deleted. There is no confirm dialog. Pipe tokens stay as the user wrote them.

The turn is one row in the cell table. That row holds the prompt, the reply, the state, the burner flag, the collapse flag, the target, the group, the status, the parent pointer, and the turn index. A click removes that cell row, drops the id from the canvas order, stores turn indexes from 1 on the rows that remain, and removes trace rows for that id. No tool writes a trace today. The tables have no foreign key, so the delete names each of those writes. Another canvas is another set of rows and stays. The walker does not read a parent pointer. A survivor that stored the deleted id keeps that string. Import rewrites a parent pointer. The walker still does not read it.

The merge sink is a different row: column position 0 with the same group. The rule does not turn the button off when the sink is the focused turn. A click on the sink does not remove it. That case stays open on this rule.

A later rule closes that open case. Arming any card of that group, member or merge, removes the whole group and the merge together. A single member stays with its siblings until that set leaves. The sentences above stay the record of the earlier build.

A layout probe at font 19 in a 1280 by 800 window found Delete between the tool-bar `v` and the divider. The divider was 9 by 22. One turn left the button off. Three turns left it on. Deleting the middle turn left indexes 1 and 2. The pipe token stayed on the later turn. The parent pointer on the first turn still named the deleted id. Trace rows for that id were gone. The other canvas stayed. A grouped member left the button off. A sequential turn beside that group left it on. A click on the merge sink left the sink in place. The labels stayed Turn 1, Turn 3, and Turn 2. The window was not opened by hand. The probe showed a frame and disposed it.

**Truth.** Delete sits just before the footer divider, removes the focused cell row, renumbers survivors from 1, and drops that id's trace rows. One turn, or a grouped member, leaves the button off. There is no confirm dialog. A click on the merge sink left the sink in place and the labels stayed Turn 1, Turn 3, and Turn 2. A later rule removes the whole group, members and merge, when any card of that group is armed.

**Reason.** Labels are the stored turn index, and New turn numbers from the cell count, so a gap would reuse a label still on screen. The delete names each write because the tables have no foreign key. Pipe tokens stay as written so a later prompt is not rewritten. A parent pointer stays stored because the walker does not read it, while import still rewrites one. The merge sink stayed open on this rule because the member test does not match column position 0. The later rule closes that case so a group leaves as one set.

**Avoidances.** Do not confirm the delete. Do not delete a grouped member from this button. Do not delete the last remaining turn. Do not rewrite pipe tokens. Do not clear a survivor's stored parent pointer. Do not touch another canvas. Do not treat the earlier sink click as a removal. Do not drop the earlier sentences when the later group removal is read.

**semantic-key:** PK-3849 PK-3850 PK-3851 PK-3852 PK-3853 PK-3854 PK-3855 PK-3856 PK-3857

## The armed card's rectangle is bright sapphire, RGB 46, 134, 255.

The armed card's rectangle was amber, RGB 200, 162, 42. It is now a bright sapphire, RGB 46, 134, 255. Pin green, a dim miss, and the ghost edge stay as they were.

The same probe counted one sapphire rectangle on the armed card and no amber rectangle. The window was not opened by hand.

**Truth.** The armed card paints one rectangle at RGB 46, 134, 255. The amber rectangle at RGB 200, 162, 42 is gone. Pin green, a dim miss, and the ghost edge are unchanged.

**Reason.** The armed edge changed from amber to sapphire so the selection is the bright blue and not the old gold.

**Avoidances.** Do not paint the armed rectangle amber. Do not change pin green, a dim miss, or the ghost edge. Do not leave an amber rectangle on the armed card.

**semantic-key:** PK-3858 PK-3859 PK-3860 PK-3861 PK-3862 PK-3863 PK-3864 PK-3865 PK-3866

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

## Pin fold

The pin bar folds. Enter in the word field does what OK does. Turning the box off keeps the words. Clear empties the field.

`v` folds the pin controls. `^` opens them. The bar starts folded. The choice is saved with the shell. A missing key stays folded. The footer tool bar is not saved. Enter in the word field does what OK does. Turning the box off keeps the words. Only a change in the field, or Clear, changes those words. Clear is a red button with a black x, to the right of the field. The states stay until OK. The OK tip stays the sentence already on the button. The edge paint stays in the chrome paint. The inspector header keeps soft wrap and the two counters. Nothing beside this fold moves. The cells already on the canvas stay as they are.

The pin behavior is already recorded for the Rust client. The crate change for the fold, for Enter, for the kept words, and for Clear waits until that catch-up is named. This row writes no Rust crate.

**Truth.** The pin bar starts folded. `v` folds it and `^` opens it. The choice is saved with the shell, and a missing key stays folded. Enter writes the states the way OK does. Turning the box off keeps the words until the field changes or Clear is used. Clear is a red button with a black x to the right of the field. The states wait for OK. The footer tool bar is not saved.

**Reason.** The pin bar needs to fold out of the way. Enter needs to write the states. The words need to stay until the user changes them, and Clear needs to empty the field.

**Avoidances.** Do not start the bar open. Do not treat a missing key as open. Do not save the footer tool bar. Do not clear the words merely by turning the box off. Do not change the words except by an edit or by Clear. Do not paint Clear as anything other than red with a black x. Do not apply the new states before OK. Do not change the OK tip or move the edge paint out of the chrome paint. Do not drop soft wrap or the two counters. Do not write the Rust crate on this row.

**semantic-key:** PK-4038 PK-4039 PK-4040 PK-4041 PK-4042 PK-4043 PK-4044 PK-4045

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

## Expanded view

Each readable cell carries an Expanded View checkbox. Checked, the reply shows all of its text inside the text-pane border. Unchecked, the reply stays at the footer row count, the same height as the prompt.

The checkbox reads `Expanded View`. It sits on the readable cell header after the collapse control and before the burner checkbox. An unreadable card has no box. Checked, the reply text pane grows to the wrapped buffer and shows all of the text inside its border. A text change and a window resize refit a checked reply. Unchecked, the reply stays at the saved view-row count, the same height as the prompt, and the text scrolls inside. The footer count sets that height. The prompt keeps that count. A folded card stays a brief line. The checkbox still shows and keeps the choice. Opening the card honors the choice.

A fresh canvas, a vault open, and a disconnect reload start unchecked. Footer Clear leaves one empty card unchecked. A new turn copies the prior cell. An empty deck stays unchecked. A duplicated group starts checked. Import, Pull, and a merge cell start unchecked. The choice is stored per cell in aetheris-expanded.yaml and restored on restart. It is not a vault column and not an export field. The collapse control stays the saved fold.

The Java client carries this behavior. The Rust client already paints Expanded View. This row writes no Rust crate. Nothing beside this checkbox moves. The cells already on the canvas stay as they are. Use confirmed the checkbox.

**Truth.** `Expanded View` sits on a readable cell header after the collapse control and before the burner checkbox. Checked, the reply grows to the wrapped buffer and shows every line inside its border, and a text change or a resize refits it. Unchecked, the reply keeps the saved view-row count, matching the prompt, and scrolls. A folded card stays one brief line while the checkbox keeps the choice. A fresh canvas, a vault open, a disconnect reload, footer Clear, an empty deck, Import, Pull, and a merge cell start unchecked. A new turn copies the prior cell. A duplicated group starts checked. The choice is stored per cell in aetheris-expanded.yaml and restored on restart.

**Reason.** A readable cell needs a way to show the whole reply inside the border, and a way to sit back at the same height as the prompt. A new canvas and Clear start unchecked. A new turn keeps the prior cell's choice.

**Avoidances.** Do not put the checkbox on an unreadable card. Do not make the choice a vault column or an export field. Do not change the saved fold. Do not grow a folded card past a brief line. Do not start a fresh canvas, a vault open, a disconnect reload, footer Clear, an empty deck, Import, Pull, or a merge cell checked. Do not start a duplicated group unchecked. Do not write a Rust crate on this row.

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

## The canvas fill

The scroll pane under the Canvas tab paints the panel ground, not the stock control face.

The cell scroll takes the panel ground after the overlay scroll bar is seated. The quiet bevel stays. The viewport border stays clear.

The Swing client changes. The Rust client stays.

**Truth.** The canvas cell scroll fills with the panel ground after the overlay bar is seated. The quiet bevel remains. The viewport border remains clear.

**Reason.** The lip had gone dark while the scroll pane still painted the stock light control face, so the canvas ground was still wrong.

**Avoidances.** Do not paint the stock control face on that scroll pane. Do not drop the quiet bevel. Do not bring the viewport border back. Do not fill the ground before the overlay scroll bar is seated. Do not change the Rust client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.

**semantic-key:** PK-4115

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

## One transcript estimates every construct

One transcript, oldest first, through the armed construct, is the only string the estimate counts, and one estimator runs on that rendered string.

One walk covers a merge arm, a member arm, and an ordinary arm. A group is one turn. The counted string is the full text. JOIN banners are stripped before the count. The paint stays capped. Assemble stays the Run. The canvas uses the heuristic estimator. A held shell session uses the stored tally over the same transcript shape.

The four marks change that one string. Included keeps both halves. Excluded keeps neither. Input only keeps the user half. Output only keeps the assistant half. Any state modifier and any structural modifier run on the one transcript. The marks are I, E, P, and O, in that order: Included, Excluded, input only, and output only.

A click on a merge group arms the group as one turn for the estimate, whether the card is an input member or the merge output cell. The focused cell id stays the clicked card. Both clicks count the same string.

The walk is one pass over the ordered cells. Live builders stay out of the counted string. The pipe index is part of that seal. An ephemeral cloud session is dropped from the count. The held shell tally is the integer the heat bar shows for a held session, and the canvas count is the integer it shows for the canvas.

The public types are Transcript, TokenEstimator, HeuristicEstimator, StoredCountEstimator, StateModifier, IncludedModifier, ExcludedModifier, InputOnlyModifier, OutputOnlyModifier, StructuralModifier, OrdinaryStructure, GroupStructure, and MessageStructure. The package named for them is org.roux.token. StateChain is package-private. MessageStructure has no caller. The private method is transcriptThroughArm.

The heat bar word is the integer the entry returns. The Java client carries this. The other client does not change on this rule. No system line is added, removed, or reordered. The doors stay. The settings file stays. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added.

**Truth.** Every construct the user can arm, including an ordinary cell, a group action, and a shell message turn, is estimated from one oldest-first transcript through that arm. The rendered string is what the estimator sees. Included keeps both halves, Excluded keeps neither, input only keeps the user half, and output only keeps the assistant half. A merge click from either card arms the group as one turn and counts one string.

**Reason.** Separate counts for a merge, a member, and a shell turn were drifting apart. One walk, one string, and one estimator keep the heat bar on the same integer a click would count. The modifiers belong on that walk so a mark cannot be applied after a second pass. The stored shell tally and the canvas heuristic meet on the same shape, with a seam left for a later estimator. Live builders are not text, so they stay out of the string. JOIN banners are paint, not counted text. An ephemeral cloud session is not part of the held transcript.

**Avoidances.** The paint cap stays. Assemble stays the Run. MessageStructure is public and has no caller. The wire does not gain a line. The other client is not edited on this rule. The focused cell id is not replaced by the group id when a member card is the card that was clicked.

**semantic-key:** PK-4125 PK-4126 PK-4127 PK-4128 PK-4129 PK-4130 PK-4131 PK-4132 PK-4133 PK-4134 PK-4135 PK-4136 PK-4137 PK-4138 PK-4139 PK-4140 PK-4141 PK-4142 PK-4143

## The mood modal paints SSH Mood Active

The mood modal paints the checkbox SSH Mood Active, and when that box is on, the mood line is placed on every connected shell pane of the canvas user.

The label is SSH Mood Active. It sits to the left of Clear mood. The box follows the canvas user. An armed user, with the mood switch on and a saved line, sends shell_mood to every connected shell pane of that user. A pane that already matches sends nothing. A clear goes only to a pane that was already told. A raw session is left alone. The status strip follows the focused pane's user. The mood modal keeps Clear, Approve, and Cancel. The other client already paints the box and does not change on this rule.

The public methods on existing types are relayArmedMoods, sshMoodLineFor, moodHeld, and setMoodHeld. No new package and no new public type are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The shared mood file stays. The per-user file is a later rule.

The console door order is unchanged. The pinned host stays first. The mood string follows. The activate line follows. The turns follow. No system line is added, removed, or reordered. The Java client carries this.

**Truth.** The mood modal shows SSH Mood Active to the left of Clear mood. An armed user with the switch on and a saved line sends shell_mood to each connected shell pane of that user that does not already match. A clear is sent only to a pane that was already told. A raw session is never touched. The status strip follows the user of the focused pane.

**Reason.** The shell panes of one canvas user should wear that user's mood without a separate trip to each pane. A pane that already matches must not be told again, and a pane that was never told must not be cleared. The checkbox is the shell gate. The modal's Clear, Approve, and Cancel stay the modal's buttons.

**Avoidances.** The console door order does not change. No new shell message type is added. The other client is not edited. The per-user store is not this rule. A raw session is not given a mood.

**semantic-key:** PK-4144 PK-4145 PK-4146 PK-4147 PK-4148 PK-4149 PK-4150 PK-4151 PK-4152 PK-4153 PK-4154

## The assistant reply frame is sapphire

The assistant reply frame on a canvas cell is sapphire. The input frame stays green.

The assistant scroll takes SELECTION_SAPPHIRE when the cell has a reply. The input scroll stays MEDIUM_GREEN. The input text stays INPUT_GREEN. An empty reply stays gray. The card edge stays. Cell frames in the other client stay as they are.

The Java client carries this. No new package, no new public type, no new public method, no new private method, no new vault column, no new channel, no new shell message type, and no new color key are added. No new library is added. The stroke uses the sapphire already used for an armed card.

No system line is added, removed, or reordered.

**Truth.** A canvas cell that has a reply paints the assistant scroll in SELECTION_SAPPHIRE. The input scroll stays MEDIUM_GREEN and the input text stays INPUT_GREEN. An empty reply stays gray. The card edge does not change.

**Reason.** The reply frame needed a color the eye already reads as the armed-card sapphire, so the reply is distinct from the green input without a new color key. An empty reply is not a reply, so it stays gray. One stroke is enough.

**Avoidances.** No new color key is introduced. The input frame is not recolored. The other client's cell frames are not edited. The card edge stays. The wire does not change.

**semantic-key:** PK-4155

## One mood desk places the canvas and the Roux session

One component, MoodDesk, behind MoodDoor, in org.casserole.mood, owns mood transitions, insertion, deletion, and override for the canvas full-push and for the Roux shell session.

MoodDesk.DESK is the desk. MoodDesk.ACTIVATE is the one activate sentence. The package methods are normalize, canvasSystems, moodHead, sshLine, announceBody, and parseShell. The door methods are seat, adopt, and porchRules. TerminalSession.moodTold and setMoodTold remember whether this connection announced the gate. Canvas, group, and pipe still full-push the pair after the host place while the switch is on. The shell still uses the checkbox and a saved line. A session that already has turns gets the current pair and loses any prior pair. Startup adopts a stored pair, and the client announces the gate on connect, including a clear when the shell gate is off. A declared test battery covers the desk, the canvas prefix, and the shell session.

The Java client carries this. The Rust client does not change. chrome.rs and grid.rs stay. The two mood files stay. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added.

Canvas, group, and pipe share one head. While the footer switch is on and a line is saved, the system lines are an empty host place, then the mood string, then the activate line, then the turns. The server drops that empty place when the prompt is blank, and fills it when a prompt is configured. The mood pair stays behind that place.

On the shell door, the console completeTurn is the path. While the checkbox is on and a line is saved, the system lines are the pinned host, then the mood string, then the activate line, then the turns. placeHost inserts the configured host at index 0 only when that text is absent. A blank prompt puts the pair first. A session that already has turns loses any prior pair and keeps the turns.

On the glass door, the shaping system line comes first, then the pair when the field holds a line. The footer switch does not gate the glass.

On the pool door, session/new, the pair is the porch rules. The host stays out. An extend keeps the rules of the open session.

**Truth.** MoodDesk in org.casserole.mood is the one place that inserts, replaces, and clears the mood pair. Canvas, group, and pipe send an empty host place, the mood string, the activate line, and the turns while the footer switch is on and a line is saved. The shell sends the pinned host, the mood string, the activate line, and the turns while the checkbox is on and a line is saved. A session that already has turns drops any prior pair and keeps the turns. The glass adds the pair after the shaping line when the field holds a line, and the footer switch does not gate that chat. Pool session/new takes the pair as porch rules and leaves the host out.

**Reason.** Canvas full-push and the Roux session were each inventing the pair. One desk owns the transitions so a connect, a startup adopt, and a session that already has turns all announce the same gate. The server still owns the empty host place: it drops that place when the prompt is blank and fills it when a prompt is configured. The glass is a field, not the footer switch. An extend must not replace the porch rules of the session that is already open.

**Avoidances.** No new vault column, channel, shell message type, color key, or library. The Rust client is not edited. The two mood files stay. The host stays out of the pool porch. The footer switch does not gate the glass. placeHost does not insert a second copy of a host that is already present.

**semantic-key:** PK-4156 PK-4157 PK-4158 PK-4159 PK-4160 PK-4161 PK-4162 PK-4163 PK-4164 PK-4165 PK-4166

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

## The mood insert is the invitation

The mood insert is the invitation. The head is mood:, the saved line, shape:, the opener, and the closer. The mood line is not written again between the two sentences.

The user-turn splice is the invitation. The opener stays: I'm opening a window with this atmosphere… would you like to step into it with me? The closer stays: Shape your replies around this mood — let it breathe through the words. The line is not written again between those two sentences. A newline after the closer joins the raw turn. There is no blank line between the head and the turn. spliceTurn writes that head. unspliceTurn strips it. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. lineAtTurnHead reads either head. inviteHead, invitedLine, and olderSpliceLine stay private. The public methods stay spliceTurn, unspliceTurn, and lineAtTurnHead.

The pin rules stay. Zero or one turn already in the transcript keeps that head on the first turn. More than one turn, and a newly defined or updated mood, puts that head on the turn being sent. The glass appendMoodHeader stays the shaping line, then the opener, the field, and the closer. The switch does not gate that chat. moodHead still counts the opener, the line, and the closer. The shell frame stays shell_mood and the line. The inspector header, while the line is present and the switch is on, is mood:, the line, shape:, the opener, and the closer. The mood line is still previewed at 80. The two sentences are the full text. Debug Mood paints that same head as the card above the pinned turn. Each physical line of the head is a mood role line. Painting the card does not move the pin. This rule overrides the card text and the send head. The settings version stays 1. debug_mood stays. The settings place stays.

No new public type, no new public method, and no new test class are added. The Java client carries this. The Rust client does not change on this rule. A known gap in paste undo stays.

Canvas, group, and pipe send an empty host place, then the turns. The pinned turn's user text starts with mood:, the line, shape:, the opener, and the closer. The mood line is not repeated between the two sentences. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host place. The mood block is not a system row. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. On the shell, the pinned user turn's text starts the same way. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin. The glass stays the shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Pool porch rules read from a spliced user head are mood:, the line, shape:, the opener, and the closer. A leading system triple still reads as the opener, a blank line, the line, a blank line, and the closer. In the inspector, while the line is present, mood: stays the line, and shape: is the opener and the closer on the following lines. Debug Mood paints that same head as the card above the pinned turn. Painting the card does not move the pin.

**Truth.** The spliced user head is the label mood:, the saved line, the label shape:, then the sentence I'm opening a window with this atmosphere… would you like to step into it with me?, then the sentence Shape your replies around this mood — let it breathe through the words, then a newline and the raw turn, with no blank line between the head and the turn. The saved line is not repeated between the sentences. An older head that starts with the opener, the line, and the closer is stripped and rewritten. The glass does not use this head. moodHead still counts the opener, the line, and the closer. The mood line preview is 80 characters. The two sentences are shown in full.

**Reason.** The invitation is the mood line and the two sentences, in that order, so the model meets the atmosphere before it is asked to shape the reply. Repeating the mood tokens between the sentences doubled the line. The older order is still recognized so a stored head is rewritten rather than stacked. The glass keeps the shaping line and the two sentences because that chat is not the user-turn splice. The count stays on the opener, the line, and the closer, not on the labels.

**Avoidances.** The line is not repeated between the sentences. There is no blank line between the head and the turn. The glass is not rewritten into the invitation and is not gated by the switch. The shell frame stays shell_mood and the line. The pin rules do not change. Painting does not move the pin. The Rust client is not edited. The known paste-undo gap stays. The example is not substituted for these two sentences.

**semantic-key:** PK-4249 PK-4250 PK-4251 PK-4252 PK-4253

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

## Merge members leave together

Merge input prompts leave together. Each member is its own call. One member does not wait for another. The merge joins when every member is quiet.

halt_run takes the focus. group_after waits until every member is quiet. Stop cancels the focused running member. Other members keep running. Clear cancels every member that is still out. Delete cancels every cut member that is still out. The Run tip says each as its own call.

No new public type, no new public method, and no new test class are added. The Rust client carries this. The Java client does not change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** A merge sends every input member at once. Each member is its own call, and none waits for another member to finish. The merge reply is joined when every member is quiet. Stop cancels only the focused running member. Clear cancels every member still out. Delete cancels every cut member still out. The Run tip says each as its own call.

**Reason.** A merge that waited on members in series held the join behind the slowest earlier call and made Stop look like it owned the whole group. Leaving together keeps the calls independent. The join still needs every member quiet. Stop follows focus. Clear and Delete follow the members they actually remove.

**Avoidances.** One member does not wait for another. Stop does not cancel the members that are not focused. The Java client is not edited. The doors do not change. The known paste-undo gap stays.

**semantic-key:** PK-4285 PK-4286 PK-4287 PK-4288

## The canvas prompt keeps an undo place

The canvas prompt keeps its own undo place. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo puts the caret where the edit began. Redo restores the caret after the edit. A paste is one span. Import is one whole-text step. The limit is 100.

The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The widget timer undoer is cleared. The module is edit. main.rs gains mod edit. No new public type, no new public method, no new test class, and no new library are added. The Rust client carries this. The Java client does not change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** The canvas prompt's undo stack holds at most 100 spans. The key queues a span and returns. Undo and redo drain the queue before they step. Undo places the caret where the edit began. Redo places the caret after the edit. A paste is one span. Import is one whole-text step. The widget timer undoer is cleared. The private type EditMemory lives in the edit module.

**Reason.** The prompt was not keeping the same caret place the cell undo already uses. Queuing the span on the key, then draining before the step, stops the caret from landing ahead of the edit. A paste is one gesture, so it is one span. Import replaces the whole text, so it is one step. Clearing the widget timer undoer keeps a second undoer from fighting this stack. The limit of 100 matches the session undo limit.

**Avoidances.** The Java client is not edited. The widget timer undoer is not left running. Import is not recorded as a character span. The library manifest is not edited. The wire does not change. The known paste-undo gap stays.

**semantic-key:** PK-4289 PK-4290 PK-4291 PK-4292 PK-4293 PK-4294 PK-4295

## Each canvas user keeps a deck

Each canvas user keeps a deck. A running turn stays on that deck when another user takes the glass. A parked shell session keeps feeding the user who owns it. The glass hears that user only when that user is on screen.

The same user reconnect does not reload the deck. Replacing this pane's socket stops a run that lived on it. A run on another pane stays. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the place. No new operating-system thread is added.

The private types are ParkedDeck, ScopeHome, DeckRefs, FeedOut, and LoadedCanvas. The private functions are scope_home, feed_parts, deck_key, note_replaced_run, wire_owned, wire_pane, lift_showing, plant_showing, remember_deck, plant_user, load_user_canvas, adopt_loaded, seat_canvas_user, card_snap, and deliver_scope. They live in chrome.rs. No new module file is added. No new public type, no new public method, no new test class, and no new library are added. The Rust client carries this. The Java client does not change.

A known folded-bar mismatch stays outside this change: the shell glyph top is 108.2 and the status bottom is 64.1. The thinking-strip check still splits on the tag rule, and that split sits outside this change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** Switching the glass to another canvas user parks the deck, including a turn that is still running. A parked shell session keeps delivering to the user who owns it, and the glass paints that delivery only while that user is on screen. Reconnecting the same user does not reload the deck. Replacing a pane's socket stops only the run that lived on that socket. Stop, Clear, and Delete cancel on the owning pane. The thinking file stays one file. The in-memory log moves with the place. No new operating-system thread is added.

**Reason.** One glass was one deck, so another user's session stole the running turn and the thinking log. The deck belongs to the user. The socket belongs to the pane, so replacing that socket stops that pane's run and leaves every other pane alone. One thinking file remains the store. The memory log is what moves when the place moves. A new thread is not required to keep a parked feed alive.

**Avoidances.** The same user does not reload. A run on another pane is not stopped when this pane's socket is replaced. Stop, Clear, and Delete do not cancel a run they do not own. The thinking file is not split. No new operating-system thread is added. The Java client is not edited. The doors do not change. The folded-bar mismatch at 108.2 and 64.1, and the thinking-strip tag split, are not this change.

**semantic-key:** PK-4296 PK-4297 PK-4298 PK-4299 PK-4300 PK-4301 PK-4302 PK-4303 PK-4304 PK-4305

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

## HikariCP and H2 are compile dependencies of the shell only, notebook yaml is read by the existing hand parser, and one named worker owns database and mux client input-output.

The build file promotes HikariCP and H2 to compile scope. H2 may already be present at runtime; promote it. Do not register a Spring `DataSource` bean on the host. Do not add Flyway, a persistence provider in use, or a second yaml library.

The notebook yaml reader parses `vault.mode`, `vault.path`, `network.air-gap`, and `amwp.enabled` with the existing space-indent hand parser. Unknown keys are ignored. A tab character fails loudly. Spaces are the only indent.

`org.aetheris.swing.AetherisWorker` is a single named executor in the shell. It runs JDBC and the client's mux input-output. It is not the host's existing lane scheduler. It is not the Swing event thread. It shuts down when the window closes.

The host's `application.yaml` is not the file these four keys are invented in as a Spring datasource. The notebook file is the notebook file.

**Truth.** Compile-scoped pool and database, four keys on the hand parser, tabs fail, and one shell worker for JDBC and client mux input-output.

**Reason.** A Spring datasource on the host would open a database the host is forbidden to open. A second yaml library would fork the parser the shell already trusts. The event thread must not block on JDBC or on the pipe.

**Avoidances.** Do not add a Spring `DataSource` on the host. Do not add Flyway or a second yaml stack. Do not run JDBC or client mux input-output on the event thread. Do not accept tab indent.

**semantic-key:** PK-4412 PK-4413

## The soft-wrap area stops tracking the viewport, prefers the column count times the live character width, and saves `ui.aetheris.inspector.wrap.columns`.

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

## The cell record stores `collapsed` default false, the table adds the column idempotently, and the flag rides insert, update, and load.

- `CellRecord.collapsed`, default false. `newCell` leaves it false.
- The cell definition lists `collapsed BOOLEAN NOT NULL DEFAULT FALSE`.
- On open, run an idempotent add for a disk vault whose table predates the column. A failed duplicate add is success.
- `INSERT_CELL`, `UPDATE_CELL`, `SELECT_CELLS`, `insertCell`, `bindUpdate`, and `loadCells` carry the flag.
- A burner still stores null large-text columns. The flag itself is stored.
- Do not add a value to `CellState`.

The probe the plan uses is `INFORMATION_SCHEMA.COLUMNS` for table `cell` and column `collapsed`. Add only when the probe is empty.

**Truth.** Boolean default false, idempotent column add, flag on every read and write path, no new state value.

**Reason.** A burner with null text still has a collapse bit, or a rebuild would forget that the burner was closed.

**Avoidances.** Do not add an enum value. Do not skip the burner flag. Do not treat a duplicate add as a failure.

**semantic-key:** PK-4415 PK-4416

## View bounds are four keys in a text file beside the vault, and RAM mode still uses that file.

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

## Swing stays on the event thread, vault writes stay on the notebook worker, and a user switch drops the in-flight frame.

Swing stays on the event dispatch thread. Vault writes and import reads stay on `AetherisWorker`. The file chooser stays on the event dispatch thread. No new execution lane is added. No notebook frame is sent on import.

A user switch during a run clears the listener, drops the in-flight frame, and stops the spinner. The session stays connected.

**Truth.** The event thread paints. The worker touches the vault and the import bytes. A switch drops the frame and keeps the session.

**Reason.** Import and chooser work must not move onto a host lane or onto the event thread's opposite.

**Avoidances.** Do not open a new lane. Do not send a notebook frame on import. Do not disconnect the session when the user changes mid-run.

**semantic-key:** PK-4418 PK-4419

## Nine asks move forward: collapse, full words, a canvas clear, compatibility, model commands, two colors, View bounds, a held scroll, and import.

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

## One card, letter tooltips, a canvas-only clear, tabled model commands, two colors, global view bounds, a held scroll, and a right-click import are locked.

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

## This serial records dispositions and does not open a plan or move code.

This file is locked. It is the fallback. The live draft is the next tweak serial. Do not edit this file.

Mode: dispositions packed. Not a plan. Not a build. The next stage stays closed.

It absorbs the previous design serial, which is locked. It depends on the stages paper, the technical specification, the Stage 1 plan, and the Stage 1 build record.

Code a later plan has to stay compatible with: `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.chat.cmd.CommandProcessorFactory`, `org.casserole.chat.cmd.ClearCommand`, `org.casserole.chat.cmd.SwapCommand`, `org.casserole.ssh.shell.Ansi`.

The previous briefing was accepted. This serial records those dispositions. It does not open a plan. It does not move code. The plan starts when the operator names it.

Markup kinds: locked, accepted, tabled, and under review.

**Truth.** Dispositions only. No plan. No code. The next stage stays closed.

**Reason.** The acceptances needed a locked table before anyone wrote a plan.

**Avoidances.** Do not edit this file. Do not open a plan from it. Do not move code. Do not open the next stage.

**semantic-key:** PK-4422 PK-4423

## The collapsed brief is the assistant text, and an empty output leaves the brief empty.

The collapsed brief is the assistant text, from the first character, one line, or two lines if the bar height allows. Plain truncation. No summary engine. The user text is not the brief. An empty output leaves the brief empty. It does not fall back to the input.

That reading is the correction on the previous serial: the brief is the output of a cell, and the input is not relevant when the cell is included.

**Truth.** The brief is assistant text only. Empty output stays an empty brief.

**Reason.** Inclusion already puts the input on the walk. The bar does not repeat it.

**Avoidances.** Do not fall back to user text. Do not summarize. Do not use more than two lines.

**semantic-key:** PK-4424 PK-4425 PK-4426 PK-4427 PK-4428 PK-4429 PK-4430 PK-4431

## Plus and minus change viewable rows on both panes, and a typed value applies after 0.8 seconds.

The operator's words: a reusable control with `+` and `-` next to each other and an editable text value beside them. On the button bar at the bottom of the notebook panel, `+` increases the number of viewable rows in a cell for both the input and the output panes, and `-` decreases that height by one row in both cases. The selected number of viewable lines persists, and the app restores that row count when it opens. Typing the value has the same effect after about a 0.8 second timeout, then the pane re-renders. Call it an incrementor.

Feasible. The control is an incrementor: `+`, `-`, and an editable value, on the notebook footer. `+` and `-` change the viewable rows of the input pane and the output pane by one, then the pane re-renders. A typed value waits 0.8 seconds after it settles, then does the same. The row count is saved and restored when the app opens. An open cell is 4 rows today, and the open card is capped at 280, so the row count has to drive the card height or the extra rows stay clipped. This note waits.

**Truth.** An incrementor on the footer sets both panes, persists rows, and applies a typed value after 0.8 seconds.

**Reason.** The 280 cap would clip the rows the operator just added.

**Avoidances.** Do not leave the 280 cap in charge of the new row count. Do not apply the typed value before 0.8 seconds of quiet.

**semantic-key:** PK-4432

## One rectangle is stored at ~/.aetheris/{user}/view-bounds.txt, and a miss opens at 720 by 480, centered.

One rectangle is stored at `~/.aetheris/{user}/view-bounds.txt` as `x`, `y`, `width`, and `height`. It is not a cell column and not an `aetheris.yaml` key. A saved rectangle that misses every screen opens at 720 by 480, centered. Close writes the dialog bounds.

The preference is global for that user, not per turn, and not on the cell record. The next open, including after a restart, uses it. Do not throw on a bad rectangle.

**Truth.** Four keys in a text file beside the user. Off-screen falls back to 720 by 480, centered.

**Reason.** The dialog was born at 720 by 480 every time, and a resize was forgotten.

**Avoidances.** Do not store the rectangle on the cell or in aetheris.yaml. Do not throw when the rectangle misses the screen.

**semantic-key:** PK-4433

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

## A model list is the remote list, or it is a red error

A model list is the remote list, or it is a red error.

The combo stays empty until the catalog reply carries model lines. Those lines fill the combo. The stored id is selected when it is one of those rows. An unknown stored id stays first. An empty stored id, an empty combo, or a stored id that is not a row shows a blank selection. The stored id is not cleared. An empty reply, or a session that has not answered, clears the combo and shows the red words Grok model list is empty. The same words sit in red on Model Config. An Ollama tags reply with no model lines shows the red words Ollama model list is empty. The typed id stays in the editor and is not added as a row. Air-gap does not gate the catalog ask. The token window does not invent a Grok row. A Grok row there is a canvas target or the focused turn. Effort, temperature, burner, think, and the Ollama show ask stay. The factory id stays grok-4.3.

**Truth.** A model list is the remote list, or it is a red error. The combo stays empty until the reply carries model lines. An empty reply clears the combo and shows the red error. The stored id is not cleared. The factory id stays grok-4.3.

**Reason.** A fixed table made a missed reply look loaded. The list is the reply, and a reply with no model lines is the red error.

**Avoidances.** Do not seed the combo from a fixed table. Do not clear the stored id when the reply is empty. Do not add a typed id as a row when the tag list is empty. Do not gate the catalog ask on air-gap. Do not change the factory id.

**semantic-key:** PK-8444
