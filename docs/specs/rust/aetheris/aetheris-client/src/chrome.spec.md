# aetheris-client::chrome

Component file. It carries how this package is built.

**Depends:** [rust/aetheris/aetheris-client/aetheris-client.spec.md](../aetheris-client.spec.md), [rust/aetheris/aetheris-client/src/amwp.spec.md](amwp.spec.md), [rust/aetheris/aetheris-client/src/door.spec.md](door.spec.md), [rust/aetheris/aetheris-client/src/session.spec.md](session.spec.md), [rust/aetheris/aetheris-term/aetheris-term.spec.md](../../aetheris-term/aetheris-term.spec.md), [rust/aetheris/aetheris-term/src/Grid.spec.md](../../aetheris-term/src/grid.spec.md)

# VT100 heatbar wears the Roux session count

The full cycle is named, and the build is named. Java moves. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0132

# VT100 heatbar wears the Roux session count

The pre-plan is locked, and a second revision is not opened. The full cycle is named, and the build is named, in the slice sentence itself. Java is named. Rust is named. The seats for the work are already filed on the locked row, and no extra cut is opened. Where this page is thinner than the locked row, the locked row wins. The formal plan is stamped from this page. Dependencies were filed before the landing was filed on the plan. The page carries a new-item mark.


**semantic-key:** PK-0256

## Merge members leave together

Merge input prompts leave together. Each member is its own call. One member does not wait for another. The merge joins when every member is quiet.

halt_run takes the focus. group_after waits until every member is quiet. Stop cancels the focused running member. Other members keep running. Clear cancels every member that is still out. Delete cancels every cut member that is still out. The Run tip says each as its own call.

No new public type, no new public method, and no new test class are added. The Rust client carries this. The Java client does not change. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** A merge sends every input member at once. Each member is its own call, and none waits for another member to finish. The merge reply is joined when every member is quiet. Stop cancels only the focused running member. Clear cancels every member still out. Delete cancels every cut member still out. The Run tip says each as its own call.

**Reason.** A merge that waited on members in series held the join behind the slowest earlier call and made Stop look like it owned the whole group. Leaving together keeps the calls independent. The join still needs every member quiet. Stop follows focus. Clear and Delete follow the members they actually remove.

**Avoidances.** One member does not wait for another. Stop does not cancel the members that are not focused. The Java client is not edited. The doors do not change. The known paste-undo gap stays.


**semantic-key:** PK-4285 PK-4286 PK-4287 PK-4288

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

# Rust chrome

This page builds the Rust window. The map is [program-spine.md](../../../../java/org/aetheris/config/config.spec.md).


**semantic-key:** PK-4447 PK-4448

## The merge width

The merge cell expands horizontally with the canvas, the way a normal cell does. It does not snap to the width of what is currently visible.

A normal cell fills the canvas column. The prompt asks for the width of that column. The stroke follows that width.

The merge has no prompt. Its frame was the button bar, so the stroke stopped at the buttons. The merge now takes the column width. A finite width of at least one pixel is that column. The stroke follows it. When the canvas grows, the merge grows with it.

A member stays the button bar plus ten pixels. The members stay on the sideways row. The merge stays under that row. The gather order stays.

An expanded merge reply fills that same width and wraps. An unchecked merge reply already asks for the row width. A folded card stays a brief line. The group row height stays: the saved visible-row count times the mono line floored at 16, plus 8 pixels.

There is no new package, no new public type, no new global key, no new vault column, no new channel, and no new wire message type. Nothing beside this width moves. The send set stays as it is. The Rust client carries this. The Java client stays.

**Truth.** The Rust merge takes the canvas column width. A finite width of at least one pixel is that column, and the stroke follows it. When the canvas grows, the merge grows. A member stays the button bar plus ten pixels, on the sideways row, with the merge under that row and the gather order unchanged. An expanded merge reply fills that width and wraps. An unchecked merge reply asks for the row width. A folded card stays a brief line. Java does not change.

**Reason.** The merge was rendering short and snapping to what was visible. It has to match the horizontal layout of a normal cell.

**Avoidances.** Do not size the merge frame from the button bar. Do not accept a column width under one pixel. Do not change a member's width from the button bar plus ten pixels. Do not move the members off the sideways row or the merge from under that row. Do not change the gather order. Do not drop the wrap on an expanded merge reply. Do not grow a folded card past a brief line. Do not change the Java client. Do not add a package, a public type, a global key, a vault column, a channel, or a wire message type.


**semantic-key:** PK-4449

## The menu word

The main menu words wear the same green the Java menu bar paints.

The Java bar paints that green as `#00ff41`. File, Tools, Token Calc, and Help wear that green. The lines under File, Tools, and Help wear it too. The word stays that green while the pointer is on it. Token Calc still opens the token window. Show Thinking stays under Tools. `Help...` and `About Aetheris...` stay under Help. The order stays File, Tools, Token Calc, and Help.

The folded row stays. The open divider stays. The words stay SSH and Aetheris. Context menus stay. The button hover emerald stays `#00FF66`. The tooltip wait stays.

There is no new package, no new public type, and no new public method. The menu ink is painted in private. There is no new global key, no new vault column, no new channel, no new wire message type, no new color key, no new library, no settings-file edit, and no dependency-manifest edit. Nothing beside this ink moves. The send set stays as it is. The Rust client carries this. The Java client stays.

The same section also records a shared transfer, and it does not open a separate row.


**semantic-key:** PK-4450

## The input pane

The canvas input pane takes the control-bar font size, and the input window grows with that size.

Plus and minus on the control bar still step the size. The typed text uses that size. When a caller has no control-bar size, the pane keeps the mono face. The line floor stays 16. Reply text stays on the size it already follows. The desk line stays. The desk draft stays. Dialogs stay. The letters stay I, E, P, and O. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. The hover emerald stays #00FF66.

The Rust helper is `pane_font`. The Rust client changes. The Swing client stays.

**Truth.** Input text and the input window follow the control-bar point size. A missing control-bar size keeps the mono face. The line floor is 16. The hover emerald remains #00FF66.

**Reason.** The control bar could be stepped to a new size while the input pane stayed on a smaller face, so the line being typed no longer matched the chrome.

**Avoidances.** Do not resize replies, the desk line, the desk draft, or dialogs from this rule. Do not drop the line floor below 16. Do not change the mark letters, the pin, the 11 pixel divider, the arrows, the tooltip wait, or #00FF66. Do not change the Swing client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.

**semantic-key:** PK-7142

## The button row

The Rust button bar sits on its own row under the host controls.

The host row keeps the mark, the fields, Remember, Font size, and the link status. The second row holds Connect, Disconnect, Reconnect, Clear Terminal, Send file, New Term, and Close Pane. The show tip names that row.

The Rust client changes. The Swing client stays.

**Truth.** Rust paints seven buttons on a second row: Connect, Disconnect, Reconnect, Clear Terminal, Send file, New Term, and Close Pane. The host row keeps the mark, the fields, Remember, Font size, and the link status. The tip that reveals the bar names that second row.

**Reason.** The Rust buttons were still sharing the host row, so they did not follow the two-row bar the Swing client already uses.

**Avoidances.** Do not move Remember, Font size, or the link status onto the button row. Do not drop the show tip. Do not change the Swing client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-4476

## The bold turn

The turn text on a canvas cell is bold.

The label is strong, including on the ghost color path. The merge label stays. The view window title stays. The mark letters stay.

The Rust client changes. The Swing client stays.

**Truth.** The canvas cell's turn label is bold on every color path, including the ghost path. The merge label, the view window title, and the mark letters are not made bold by this rule.

**Reason.** The turn label was the same weight as the surrounding chrome, so the cell's own title did not separate from the marks and the merge label.

**Avoidances.** Do not bold the merge label, the view window title, or the mark letters. Do not skip the ghost color path. Do not change the Swing client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.


**semantic-key:** PK-4477

## The folded line

When the thinking pane is closed on the canvas, the folded control line lifts so C is not clipped by the upper canvas button bar.

C and Close sit on the same line as the drag bar. That line lifts 4 pixels. The band grows upward by that lift. The clearance is at least 2 pixels. The open strip stays on the buttons. The VT100 path stays. C keeps the color it already has.

The 4 pixel lift is the raise this rule names. The Rust client changes. The Swing client stays.

**Truth.** The folded canvas thinking line moves up 4 pixels, and the band grows upward by those 4 pixels, so C clears the upper button bar by at least 2 pixels. The open strip, the VT100 path, and the color of C stay.

**Reason.** With the pane closed, the upper button bar was cutting off C even though C and Close share the drag-bar line.

**Avoidances.** Do not lift by less than the 4 pixels that buy at least 2 pixels of clearance. Do not move the open strip off the buttons. Do not change the VT100 path. Do not recolor C. Do not change the Swing client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4478

## The VT100 menu moves the buffer

In the VT100 pane, the paging and cursor commands on the right-click menu move the buffer, and every buffer line stays lit.

The view had been pinned to the tail on the frame after a page, so a page did not hold. A parked pane stored the motion and never applied it. Ctrl+Up, Ctrl+Down, Shift+Page Up, Shift+Page Down, and Ctrl+L were not strokes. The five buffer lines went dark on the alternate screen.

The stick now follows the tail flag. A page leaves the tail until the view is on the tail again. A parked pane applies the lines it was holding. The five buffer lines stay lit. Clear on the alternate screen still leaves that grid as it stands. Copy, Paste, and Open as URL keep their gates. The menu labels stay the thirteen strings. The chord is shown on the hover. A bare Page key is not a host byte. The terminal crate stays.

The private helper is `console_menu_tip`. The private stroke is `Motion`. The Rust client changes. The Swing client stays. This Rust menu mirrors the menu the Swing client already runs.

**Truth.** Page and cursor commands from the VT100 context menu move the buffer and hold away from the tail until the view is on the tail again. Ctrl+Up, Ctrl+Down, Shift+Page Up, Shift+Page Down, and Ctrl+L are strokes. The five buffer lines stay lit. Clear on the alternate screen does not wipe that grid. Copy, Paste, and Open as URL keep their gates. Thirteen labels stay. Bare Page is not sent to the host.

**Reason.** The menu advertised paging and cursor motion, then pinned the next frame back to the tail, dropped a parked pane's motion, and left the five buffer lines dark on the alternate screen.

**Avoidances.** Do not pin the view to the tail on the frame after a page. Do not drop motion stored for a parked pane. Do not send bare Page to the host. Do not darken the five buffer lines. Do not clear the alternate-screen grid on Clear. Do not retarget Copy, Paste, or Open as URL. Do not change the thirteen labels. Do not change the Swing client or the terminal crate. Do not add a package, a public type, a public method, a shortcut beyond those strokes, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4479 PK-4480 PK-4481

## The user pull-down sits on the east

The user pull-down sits on the east of the canvas footer, the way the Swing canvas holds that column. Choosing a name switches the user.

`east_reserve` holds the east width before the master buttons paint. That width is 160 plus the spinner when a card runs. The buttons paint in the width that remains. The pull-down stays on the outer east. The spinner stays just west of it. The combo width stays 160. The ceiling stays 180. The arrow stays emerald.

The Rust client changes. The Swing client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** The Rust user combo is the outer east column, 160 wide, ceiling 180, with the spinner just west of it when a card runs. The reserved width is 160 plus that spinner. The buttons use the width that remains. The arrow stays emerald. A chosen name switches the user.

**Reason.** The Rust user control was not in the east column the Swing footer already uses, so the two canvases did not share one place to change user.

**Avoidances.** Do not paint the master buttons over the reserved east width. Do not move the pull-down off the outer east. Do not change the combo width off 160 or the ceiling off 180. Do not recolor the arrow. Do not change the Swing client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4482

## The folded thinking line clears the upper bar

The folded thinking line clears the upper canvas button bar.

`folded_control_height` sizes the folded canvas row to Close. The band adds that height plus the lift. The fold mark, C, and Close clear the outer top of the upper button bar by at least 2 pixels. The open strip stays. The VT100 strip stays.

The Rust client changes. The Swing client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** The folded canvas row is as tall as Close, and the band adds that height plus the lift, so the fold mark, C, and Close clear the upper button bar by at least 2 pixels. The open strip and the VT100 strip stay.

**Reason.** C could still meet the upper button bar when the folded row was shorter than Close. Sizing the row to Close, then adding the lift, is what buys the 2 pixels.

**Avoidances.** Do not size the folded row to anything shorter than Close. Do not drop the lift from the band. Do not leave less than 2 pixels between the fold mark, C, or Close and the outer top of the upper bar. Do not move the open strip or the VT100 strip. Do not change the Swing client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4483

## Canvas text panes can wear the cell frame

Canvas text panes can wear the cell frame. The switch starts off. Off keeps the quiet plate.

File, Settings opens the window. The checkbox is Painted text borders. Off keeps the quiet plate. On paints the cell frame on a canvas text pane that already paints. The input frame is medium green. A reply with trimmed text is sapphire. A reply whose trimmed text is empty is gray. A pane that was not painted stays unpainted. The card edge stays. Shell panes stay. Dialog text stays.

The choice is saved in aetheris-settings.yaml. A missing file stays off and is not written until the switch moves. A bad file stays off and is not overwritten. A memory door writes nothing.

The private enum is PaneFace. The private struct is SettingsYaml. The private functions are pane_edge, parse_settings_yaml, render_settings_yaml, read_settings_yaml, write_settings_yaml, load_settings_file, settings_yaml_path, paint_settings, and store_settings. PANE_EDGE_PX is 2. PANE_STROKE_ROUND is 10. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The Rust client carries this. The Java client does not change.

No system line is added, removed, or reordered.

**Truth.** Painted text borders starts off. Off, a canvas text pane keeps the quiet plate. On, a pane that already paints wears a 2-pixel frame with a corner round of 10: medium green on the input, sapphire when the reply's trimmed text is non-empty, and gray when that trimmed text is empty. An unpainted pane, a shell pane, dialog text, and the card edge stay as they are. The choice lives in aetheris-settings.yaml and is not written until the switch moves.

**Reason.** The quiet plate is the default the reader already has. The frame is optional paint on panes that already paint, matching the cell frame: green input, sapphire reply, gray empty reply. A missing or bad file must not be overwritten with a guess, and a memory door has nothing to persist.

**Avoidances.** The switch does not start on. Shell panes and dialog text are not framed. The card edge is not replaced. No new color key is added. The Java client is not edited. The wire does not change.

**semantic-key:** PK-7143

## Button borders keep two points of clearance

Button borders keep 2 points of clearance on every side. The lane follows the pane font.

The lane had been sized to the stock button height. The buttons paint at the pane font. That lane was also the clip, so the border was cut. The column font is the override. TextStyle Button ignores that override, so the folded Close row stayed short and the strip clip cut Close. The clearance is 2 points on every side of a button border. The first button row, the east combo, the tool row, and Close take that clearance. The folded line lift stays. The footer pad, the corner, and the edge stay. The control labeled C keeps its appearance.

The private functions are button_outer_height, button_lane_height, and button_lane_clip. BUTTON_CLEAR_PX is 2. No new public type, no new public method, no new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The Rust client carries this. The Java client does not change.

No system line is added, removed, or reordered.

**Truth.** A button border has 2 points of clearance on every side. The first button row, the east combo, the tool row, and Close use that clearance. The lane is tall enough for the pane font, not the stock button height, so the border is no longer the clip. The folded line lift, the footer pad, the corner, and the edge stay.

**Reason.** The buttons paint at the pane font while the lane was the stock height and also the clip, which cut the border. TextStyle Button ignores the column-font override, which is why the folded Close row stayed short and the strip cut Close. Two points around the border clear the stroke without moving the pad, the corner, or the edge.

**Avoidances.** The stock interact size is not the lane height. The folded line lift is not removed. The footer pad, the corner, and the edge stay. The control labeled C does not change appearance. The Java client is not edited. The wire does not change.


**semantic-key:** PK-4486 PK-4487 PK-4488 PK-4489 PK-4490 PK-4491 PK-4492

## The Rust mood place follows the desk

The Rust mood place follows the desk. Canvas, group, and pipe send the empty host place, the opener, the constructed mood, and the closer while the switch is on and the line is non-blank. A long canvas does not drop that triple. The legacy activate sentence stays off a new send. The checkbox makes the shell available. The switch off clears a checked shell gate. The glass places the opener, the field, and the closer. The switch does not gate that chat. The inspector shape line is the closer. An Ollama write stays off a Grok or Pool family.

Private functions carry the desk. No new public type and no new public method are added. The glass session struct stays the glass session. ssh_mood_seat delegates to ssh_mood_line. The checkbox makes the gate available. The footer switch turns it on. Either one off returns none and clears a pane that was told. The shell frame stays shell_mood and the trimmed line, or shell_mood_clear. The opener and the closer stay off that frame. The status strip says mood active only when both are on.

fit_blocks, while the switch is on and the line is non-blank, pushes an empty host place, the opener, the trimmed line, and the closer, then the turns. A frame that does not fit returns no frame and dropped false. The execute does not drop the triple. Mood off does not add a host place. The vestige host sentence stays off the wire.

mood_shape_tuned keeps the shaping line first. A trimmed field then places the opener, the field, and the closer. An empty field leaves those three off. The switch does not gate that chat. mood_inspector_head previews the closer at 80 characters. counted_inspector puts the opener, the trimmed line, and the closer at the head when a send carries the mood and the block list is non-empty.

ollama_pane_owns_cell returns true for a missing family or the Ollama family, and false for Grok or Pool. type_ollama, commit_ollama, and set_ollama_think return quiet before a write when the pane is not the console and does not own the cell. The console path does not ask. A missing family still writes. The opener and the closer match OPENER and CLOSER. The legacy activate sentence stays a named constant and stays off a new send.

One showing canvas stays the first vault canvas. Mood open uses focus on that deck. The client still sends the whole working set. The crate does not grow a pool session. The mood title bar stays. Approve still saves and turns the switch on. Clear still asks. Glass geometry, tips, colors, and button labels stay. The shell checkbox label stays. The yellow inspector card stays. Thinking flags stay. try_clear stays. The mood switch tooltips stay.

No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The Rust client carries this. The Java client does not change. grid.rs stays.

The status strip says mood active only when both gates are on. The inspector shape line is the closer, cut at 80. The counted head is the opener, the trimmed line, and the closer when a send carries the mood and the block list is non-empty. The client still sends the whole working set. The crate does not grow a pool session. A missing family still writes. The console path does not ask.

**Truth.** While the footer switch is on and the line is non-blank, canvas, group, and pipe send an empty host place, the opener, the trimmed line, and the closer, and a frame that does not fit returns no frame with dropped false rather than dropping the triple. Mood off adds no host place. The shell frame is shell_mood plus the constructed line only when both the checkbox and the switch are on; either off clears a pane that was told, and the opener and closer stay off that frame. The glass is the shaping line, then the opener, the field, and the closer, and the switch does not gate it. The closer preview is 80 characters. An Ollama write is quiet on a Grok or Pool family unless the pane is the console.

**Reason.** The desk already defined the triple, the two shell gates, the glass, and the family guard. This client was still sending the older shape. Matching the desk keeps one wire. A long canvas must fail the frame instead of trimming the mood. The vestige host sentence is not part of that wire. The whole working set is still what this client sends, so a pool session is not added here.

**Avoidances.** The legacy activate sentence is not put on a new send. The triple is not dropped to fit. The switch does not gate the glass. The crate does not grow a pool session. The Java client is not edited. Glass geometry, tips, colors, button labels, the checkbox label, the yellow inspector card, thinking flags, try_clear, and the mood switch tooltips stay.

**semantic-key:** PK-7144

## The Rust mood layer matches the desk

The Rust client carries the mood desk. The invitation, the pin, the debug head, the glass, and the wire follow that desk.

mood.rs is the desk. It does not paint. It owns the opener, the closer, the legacy activate sentence, the invitation, the older-head strip, the pin placement, and the debug lines. main.rs names the module. chrome.rs aliases MOOD_OPENER, MOOD_CLOSER, and MOOD_ACTIVATE to that desk. The glass session keeps the name MoodDesk. The opener stays: I'm opening a window with this atmosphere… would you like to step into it with me? The closer stays: Shape your replies around this mood — let it breathe through the words. The invitation head is mood:, the saved line, shape:, the opener, and the closer, then the raw turn. There is no blank line between the head and the turn. The line is not written again between the two sentences. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written.

One record lives in aetheris-mood.yaml, version 1. The keys are pending and pins. A pin value is a cell id or head. Unknown keys are dropped on rewrite. pending is always written. pins is written when the map holds a row. The showing canvas id is copied from the vault canvas and is not written. Zero or one user turn already ahead of the target keeps the pin at head, and the splice rides the first included user cell in the send. More than one, and a newly defined or updated mood, sets pending. The same trimmed line is not an update. The Run that sends the next turn consumes pending and pins that target cell. The pin stays until the next define or update. Marks do not move the pin. An excluded pin slides to the first included user cell at or after the pin. A run whose target sits before the pin sends no splice. Delete of the pin walks back one remaining cell, or head when none sits behind. Clear turns sets that canvas pin to head and clears pending. The mood line stays. Clear mood and a new session clear the line, the switch, the pins, and pending. Switch off removes the splice and leaves the pin. Switch on with the same held line restores the splice at the pin.

A group send clones the place. Each successful member consumes the pin on that clone. The caller file is written when the first sent cell is handed to a live mux. A refused frame leaves pending. The pin file is written when a frame is handed to a live mux.

The inspector header, while the line is present and the switch is on, is mood:, the line previewed at 80, then shape:, the opener, and the closer. The token estimate counts the opener, the line, and the closer. Debug lines are inserted after that count. Debug Mood is the File, Settings checkbox. It starts off. It is stored as debug_mood in aetheris-settings.yaml, version 1. On paints the invitation immediately before the user line the pointer places. Those lines use the role mood and carry no card number. Painting does not move the pin.

The shell frame stays shell_mood and the line. The glass stays the shaping line, the opener, the field, and the closer. The switch does not gate that chat. This client still sends the whole working set and does not grow a pool session. The legacy activate sentence stays off a new send. A mood that is off adds no host place. A body that does not fit returns no frame and leaves the switch on. No library manifest edit. No new public type on the Java side and no new public method there. The anchor is RustMoodDesk.

The test module names TRANSFER_LABEL, TRANSFER_WORD, SESSION_REPLACE_ASK, MENU_PROMPT_LONE, MENU_REPLY_LONE, and bar_press, which the existing tests already call. That naming lets the crate tests compile. It does not change product behavior. A known folded-bar mismatch stays outside this change: the shell glyph top is 108.2 and the status bottom is 64.1. A known gap in paste undo stays.

Canvas, group, and pipe send an empty host place, then the turns. The pinned turn's user text starts with mood:, the line, shape:, the opener, and the closer. The mood line is not repeated between the two sentences. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host place. The mood block is not a system row. A stored head in the older order is stripped and rewritten. On the shell, the pinned user turn starts the same way. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin. The glass stays the shaping line, then the opener, the field, and the closer when the field holds a line. Pool stays the porch rules of the other client. This client still sends the whole working set and does not grow a pool session. In the inspector, while the line is present, mood: stays the line, and shape: is the opener and the closer on the following lines. Debug Mood paints that same head immediately before the user line. Those lines carry no card number. Painting does not move the pin.

**Truth.** mood.rs owns the invitation, the older-head strip, the pin, and the debug lines, and it does not paint. The spliced head is mood:, the line, shape:, the opener, and the closer, with no blank line before the raw turn and no second copy of the line. The pin file is version 1, pending is always written, pins is written only when the map holds a row, and the showing canvas id is not written. A group send clones the place, and each successful member consumes the pin on that clone. A refused frame leaves pending. The file is written when a frame is handed to a live mux. Debug lines are inserted after the token count. A body that does not fit returns no frame and leaves the switch on.

**Reason.** The desk was already smoked on the other client. This client had an earlier mood place and needed the same invitation, the same pin, and the same debug head, including a group send that must not share one pin across members. Writing the pin file only when a frame reaches a live mux keeps a refused frame pending. Unknown keys are dropped on rewrite so the file stays the two keys this record owns. The test-module names are a compile repair for calls the tests already make, not a behavior change.

**Avoidances.** mood.rs does not paint. The showing canvas id is not written into the mood file. This client does not grow a pool session and still sends the whole working set. The legacy activate sentence stays off a new send. Mood off adds no host place. The switch does not gate the glass. The folded-bar mismatch at 108.2 and 64.1 is not this change. The known paste-undo gap stays. The Java client is not edited.

**semantic-key:** PK-7145

## The mood modal uses the selected cell

The mood modal uses the model of the selected canvas cell. The other client already does.

mood_focus_card, mood_glass_target, and mood_glass_title follow that cell. The open waits until the place lands. The title and the send read that cell. The other client's moodCard and moodTarget already do this and do not change. No new public type, no new public method, and no new test class are added. The Rust client carries this. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** The mood window waits until the selected canvas cell is the place, then the title and the send read that cell. The private functions are mood_focus_card, mood_glass_target, and mood_glass_title. The Java path already uses moodCard and moodTarget and is not edited.

**Reason.** The modal was able to open on a cell other than the one selected, so the title and the send could name a different model than the card in front. Waiting until the place lands keeps the title and the target on that cell.

**Avoidances.** The Java client is not edited. The open does not read a cell before the place lands. The wire does not change. The known paste-undo gap stays.


**semantic-key:** PK-4493 PK-4494 PK-4495 PK-4496 PK-4497 PK-4498

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. This row is not a feature. A formal unit test is not declared. An outside-list row is not added.

Branch, action, stage, and the when field are not retargeted.

The edit below is the build. Our types take no lambdas. Lombok stays out. JUnit stays out. There is no new G-key, no new public type, no new public method, no new private method, and no new package. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited. The beat stays in paint and step.

The Rust client crate is checked offline. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. Java stays.

The product commit comes next. Its message states that the change is in the tree. Its message states that the change landed. Java stays.

**semantic-key:** PK-7892

## Goal

The heatbar beat pulses when the count changes, on the VT100 tab and on the Aetheris canvas tab, the way the Java composer beat already pulses.

The arm frame shows the full lunge. Later frames ease it. The pulse happens when the user types on the VT100 tab and on the Aetheris canvas tab.

**semantic-key:** PK-7146

## What stays

The track is 5 pixels, the needle is 2 by 9, and the lunge is 6 pixels.

The posts stay at 70 and 90. The inks stay. A held Roux count still replaces the glass. The integer still moves on the heuristic the ring already uses. Java stays. The heatbar geometry already locked stays. The five-pixel bar-button cut stays. The open batch already in the working tree stays open.

**semantic-key:** PK-7147

## Edit

In heatbar.rs, the arm frame draws the 6 pixel lunge and does not ease it.

A later frame eases one step, lunge times 0.72, when 16 ms have passed, and asks for another frame in 16 ms. A faster frame does not spend that step. A long gap spends one step. The flash eases on that same step. The beat is stored on one fixed id. The 5 pixel track stays. The 2 by 9 needle stays. The posts stay. The inks stay. Java stays. A Java move for this beat waits until it is named.

**semantic-key:** PK-7148

## Proof

The Rust client crate is checked offline, and the proof does not open the window or touch a live session.

The window stays closed. The proof does not make a live POST. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. Java stays.

**semantic-key:** PK-7149

## Scope

Rust is the tree that moves, and Java stays.

The neighbor lines under What stays stay in force. There is no side move on this slice.

**semantic-key:** PK-7150

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This land leaves the working set as it stands.

**Truth.** On the VT100 tab and on the Aetheris canvas tab, a count change shows the full 6 pixel lunge on the arm frame, then eases one step of lunge times 0.72 each time 16 ms have passed. The beat lives on one fixed id inside paint and step. Java stays.

**Reason.** The Java composer beat already pulses this way. The Rust arm was not showing the full lunge before the ease. One step per 16 ms, including after a long gap and not on a faster frame, keeps the ease on the same clock as the flash. The 5 pixel track, the 2 by 9 needle, and the posts at 70 and 90 stay, so the pulse does not redraw the heatbar.

**Avoidances.** Do not open a second revision. Do not stage chrome.rs. Do not retarget branch, action, stage, or the when field. Do not add a lambda, Lombok, JUnit, a G-key, a public type, a public method, a private method, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not ease the arm frame. Do not spend an ease step before 16 ms, and do not spend more than one step across a long gap. Do not change the 5 pixel track, the 2 by 9 needle, the 6 pixel lunge, the posts at 70 and 90, or the inks. Do not stop a held Roux count from replacing the glass. Do not move Java until that move is named. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.

**semantic-key:** PK-7914

# Canvas text panes can wear the Java cell frame

The full cycle is named, and the build is named. Rust moves. Java stays. The switch starts off. The working word is NUT, and that word stays the owner's. The preserve token stays the glossary row it already is. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4501 PK-4502 PK-4503 PK-4504 PK-4505

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. This row is not a feature. A law is not added. An outside-list row is not added. A glossary row is not added.

Branch, action, stage, and the when field are not retargeted.

The edit below is the build. There is no new public type, no new public method, and no new G-key. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited.

The Rust client crate is checked offline. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The named tests are not executed. The test module does not compile on the recorded head.

The Rust product commit follows. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend. Java stays.

**semantic-key:** PK-7893

## Goal

File, Settings opens a window for global switches.

The first switch is Painted text borders. Off keeps the quiet plate. On paints the Java cell frame on the Rust canvas text panes.

**semantic-key:** PK-7151

## What stays

The off path keeps the quiet plate, the allocated pane size, and which panes paint.

An ordinary empty reply still paints no reply pane. The card edge stays. SSH panes stay. Dialog text stays. Text ink stays. Java stays. The open batch already in the working tree stays open.

**semantic-key:** PK-7152

## Edit

chrome.rs adds Settings to the File menu. The window has one checkbox.

The choice is saved in aetheris-settings.yaml under the home directory. A missing file stays off and is not written until the switch moves. A bad file stays off and is not overwritten. A memory door writes nothing.

On, the input frame is medium green. A reply with trimmed text is sapphire. A reply whose trimmed text is empty is gray. The stroke is 2. The corner is 10. The inner text clears that stroke. The pane that was not painted stays unpainted.

**semantic-key:** PK-7153

## Proof

The Rust client crate is checked offline from its manifest on the working tree before the product commit.

A clean checkout was not recompiled. The named tests were not executed. The test module does not compile on the recorded head. The window stays closed. The proof does not make a live POST. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. Java stays.

**semantic-key:** PK-7154

## Scope

Rust is named, and Java stays.

There is no side move on this slice. The working set stays. The next marker is the one after that spend.

**semantic-key:** PK-7155

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

**Truth.** Painted text borders starts off. Off keeps the quiet plate. On paints a medium-green input frame and a sapphire reply frame, stroke 2 and corner 10, only on a canvas text pane that already paints. An empty trimmed reply is gray. The choice lives in aetheris-settings.yaml under the home directory. A missing or bad file stays off. A memory door writes nothing. Java stays.

**Reason.** The Rust panes were quiet, and the Java cell already shows green and sapphire. A global switch lets the Rust canvas wear that frame without changing which panes paint, and without writing a settings file until the switch actually moves.

**Avoidances.** Do not open a second revision. Do not rewrite slices already locked, and do not close the open batch. Do not retarget branch, action, stage, or the when field. Do not move Java. Do not add a public type, a public method, a G-key, a vault column, a channel, an AMWP type, or a library, and do not edit Cargo.toml. Do not start the switch on. Do not write a missing or bad settings file before the switch moves. Do not let a memory door write. Do not paint a pane that was not painted. Do not change the card edge, SSH panes, dialog text, or text ink. Do not run the named tests. Do not recompile a clean checkout as this proof. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.

**semantic-key:** PK-7915

# Button borders keep two points of clearance

The full cycle is named, and the build is named. Rust moves. Java stays. The lane follows the pane font. The working word is NUT, and that word stays the owner's. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4506 PK-4507 PK-4508 PK-4509 PK-4510 PK-4511 PK-4512

## Goal

BB1 and the Close control on the thinking pane keep a 2 point clearance on every side of the button border.

The clip stays outside that ring.

**semantic-key:** PK-7156

## What stays

FOOTER_PAD stays 6. FOOTER_ROUND stays 6. FOOTER_EDGE_PX stays 2. FOOTER_OUT stays 4. FOLDED_LINE_LIFT stays 4.

C keeps its size and its drawn mark. The east combo keeps its reserved width. The folded line still clears the outer top of BB1. Java stays. The open batch already in the working tree stays open. Slices already locked stay locked.

**semantic-key:** PK-7157

## Edit

In chrome.rs the root is the lane. The canvas column sets the font override to the pane font. The button paints with that override.

The master lane was allocated at the stock interact height, and the clip was that lane. TextStyle Button ignores the override, so the folded Close row used that same short height and the strip clip cut Close. The frame pad sits outside the content clip.

The painted height comes from FontSelection, which honors the override. The lane is that height. The clip stays 2 points outside the lane and inside the parent, and it stops short of the east combo. Each footer row is the painted height plus 2 points above and below. Close sits inside a 2 point inset. The drag still spans the full folded row.

BUTTON_CLEAR_PX is 2. The private functions are button_outer_height, button_lane_height, and button_lane_clip.

**semantic-key:** PK-7158

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

**Truth.** The button lane follows the pane font through FontSelection, not the stock interact height. The clip stays 2 points outside that lane, inside the parent, and short of the east combo. Each footer row adds 2 points above and below. Close sits in a 2 point inset. BUTTON_CLEAR_PX is 2. The drag still spans the full folded row. Java stays.

**Reason.** TextStyle Button ignores the font override, so a lane allocated at the stock interact height clipped the bottom of BB1 and cut Close on the folded thinking pane. The painted height has to come from FontSelection, and the clip has to sit outside the border rather than on it.

**Avoidances.** Do not open a second revision. Do not rewrite slices already locked, and do not close the open batch. Do not retarget branch, action, stage, or the when field. Do not move Java. Do not add a public type, a public method, a G-key, a vault column, a channel, an AMWP type, or a library, and do not edit Cargo.toml. Do not change FOOTER_PAD 6, FOOTER_ROUND 6, FOOTER_EDGE_PX 2, FOOTER_OUT 4, or FOLDED_LINE_LIFT 4. Do not change the size or the drawn mark of C. Do not take the east combo's reserved width. Do not let the folded line hit the outer top of BB1. Do not let the clip reach the east combo. Do not shrink the drag to less than the full folded row. Do not keep the stock interact height as the lane. Do not run the named tests. Do not recompile a clean checkout as this proof. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.

**semantic-key:** PK-7916

# The Rust mood seat follows the Java desk

The stamp names the build. The Rust mood seat follows the Java desk. Java stays. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4513 PK-4514 PK-4515 PK-4516

## Approval

This sentence names the build. The Rust mood seat follows the Java desk. Canvas, group, and pipe send the empty host seat, the opener, the constructed mood, and the closer while the switch is on and the line is non-blank. A long canvas does not drop that triple. The legacy activate sentence stays off a new send. The checkbox makes SSH available. The switch off clears a checked SSH seat. The glass seats the opener, the field, and the closer. The switch does not gate that chat. The inspector shape line is the closer. An Ollama write stays off a Grok or Pool family.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The Rust client crate was already checked offline on the working tree, and that check passed. A product compile is not required because Java stays. No crate test is run. No new JUnit class is added. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The product commit is the lines in chrome.rs and the Ollama family guard in model.rs. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-7159

## Goal

The Rust client seats mood the way the Java desk already seats it, on the canvas and on the shell.

**semantic-key:** PK-7160

## What stays

Java stays. MoodDesk.java stays. The glass session struct stays the glass session. The Mood title bar stays. Approve still saves and turns the switch on. Clear still asks. Glass geometry, tips, colors, and button labels stay. The SSH checkbox label stays. The yellow inspector card stays. Thinking flags stay. try_clear stays. TokenEditQueue, SshEditQueue, and EditMemory stay. The mood switch tooltips stay. grid.rs stays. One showing canvas stays the first vault canvas. Mood open uses focus on that deck. The client still sends the whole working set. The crate does not grow a pool session. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The next free number stays the number already next. The outside-list number already next stays next. No new G-key, vault column, channel, AMWP type, color key, library, or Cargo.toml edit is added. No glossary row is added. The match already named in the spec stays. aa-sdd-source-prose.md stays. application.yaml stays.

**semantic-key:** PK-7161

## The cause

The Java desk already seated the opener, the constructed mood, and the closer, and left the crate until this catch-up was named. This ask names that catch-up. The Rust send still used the legacy activate sentence. The shell seat still ignored the footer switch. The glass still omitted the opener and the closer. The inspector shape line still previewed the legacy sentence.

**semantic-key:** PK-7162

## The seat

Private functions carry the desk. No new public type is added. No new public method is added. ssh_mood_seat delegates to ssh_mood_line. The checkbox makes the seat available. The footer switch turns it on. Either one off returns none and clears a pane that was told. The shell frame stays shell_mood and the trimmed line, or shell_mood_clear. The opener and the closer stay off that frame. The status strip says mood active only when both are on. fit_blocks, while the switch is on and the line is non-blank, pushes an empty host seat, the opener, the trimmed line, and the closer, then the turns. A frame that does not fit returns no frame and dropped false. The execute does not drop the triple. Mood off does not add a host seat. The vestige host sentence stays off the wire. mood_shape_tuned keeps the shaping line first. A trimmed field then seats the opener, the field, and the closer. An empty field leaves those three off. The switch does not gate that chat. mood_inspector_head previews the closer at 80 characters. counted_inspector puts the opener, the trimmed line, and the closer at the head when a send carries the mood and the block list is non-empty. ollama_pane_owns_cell returns true for a missing family or the Ollama family, and false for Grok or Pool. type_ollama, commit_ollama, and set_ollama_think return quiet before a write when the pane is not the console and does not own the cell. The console path does not ask. The opener and the closer match the Java opener and the Java closer. The legacy activate sentence stays a named constant and stays off a new send.

**semantic-key:** PK-7163

## Proof

A product compile is not required because Java stays. A crate test is not run. The window stays closed.

**semantic-key:** PK-7164

## Product

The product files are the lines in chrome.rs and the Ollama family guard in model.rs. grid.rs stays. Java stays. One product commit. Rust moves.

**semantic-key:** PK-7165

## Scope

No new public type is added. No new public method is added. No new library is added. Cargo.toml is not edited. The Ollama family guard is the carry already named for the showing-canvas family. The next free number stays the number already next. The outside-list number already next stays next.

**semantic-key:** PK-7166

## Wire

Canvas, group, and pipe send an empty host seat, the opener, the constructed mood, the closer, and the turns, while the footer switch is on and the line is non-blank. The server replaces that empty seat. The execute does not drop the triple to fit. The legacy activate sentence stays off a new send. On SSH, the checkbox makes the shell available and the footer switch turns it on. Either off clears a pane that was told. The shell frame stays shell_mood and the constructed line. Glass sends the shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat.

**Truth.** The Rust mood seat follows the Java desk through private functions. Canvas, group, and pipe send the empty host seat, the opener, the trimmed line, and the closer, then the turns, while the switch is on and the line is non-blank. A frame that does not fit returns no frame and dropped false. The execute does not drop the triple. The switch off clears a checked SSH seat. The shell frame stays shell_mood and the trimmed line, or shell_mood_clear, and the opener and the closer stay off that frame. Glass seats the opener, the field, and the closer after the shaping line. The switch does not gate that chat. mood_inspector_head previews the closer at 80 characters. ollama_pane_owns_cell is false for Grok and Pool. The legacy activate sentence stays a named constant and stays off a new send. Java stays.

**Reason.** The Java desk already seated the opener, the constructed mood, and the closer. The Rust send still used the legacy activate sentence. The shell seat ignored the footer switch. The glass omitted the opener and the closer. The inspector shape line still previewed the legacy sentence. This plate is the named catch-up.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not change grid.rs, MoodDesk.java, aa-sdd-source-prose.md, the paging memorandum, or application.yaml. Do not move Java or require a product compile. Do not add a G-key, a vault column, a channel, an AMWP type, a color key, a library, a Cargo.toml edit, a glossary row, a public type, a public method, or a JUnit class. Do not retarget branch, action, stage, or the when field. Do not drop the triple to fit a long canvas. Do not put the legacy activate sentence on a new send, and do not drop it as a named constant. Do not let the switch leave a checked SSH seat announced. Do not put the opener or the closer on the shell frame. Do not let the status strip say mood active unless both gates are on. Do not add a host seat when mood is off. Do not put the vestige host sentence on the wire. Do not let the switch gate the glass. Do not preview more or less than 80 characters of the closer. Do not let an Ollama write land on a Grok or Pool family. Do not let the console path ask. Do not change the Mood title bar, Approve, Clear, glass geometry, tips, colors, button labels, the SSH checkbox label, the yellow inspector card, thinking flags, try_clear, TokenEditQueue, SshEditQueue, EditMemory, or the mood switch tooltips. Do not grow a pool session in the crate. Do not change the first vault canvas or the focus mood open uses. Do not stop the client from sending the whole working set. Do not run a crate test. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-7167

# The Rust mood layer matches the smoked desk

The stamp names the build. The Rust client carries the smoked mood desk. The invitation, the pin, the debug head, the glass, and the wire follow the Java seat. Java stays. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4517 PK-4518 PK-4519 PK-4520 PK-4521 PK-4522

## Approval

This sentence names the build. The Rust mood layer matches the smoked desk. mood.rs seats the invitation, the pin, and the debug lines. Canvas, group, and pipe splice that invitation onto the pinned user turn. The glass session in chrome stays.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The failure is the_folded_bar_sits_on_one_row. The SSH glyph top is 108.2 and the status bottom is 64.1. The same rectangles fail on the recorded head. The Rust client crate check, offline, passed. A product compile is not required because Java stayed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The product commit is mood.rs, main.rs, and chrome.rs. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend. A sentence in the same approval item named the marker one step further, and that sentence was one ahead.

**semantic-key:** PK-7168

## Goal

The Rust client carries the smoked mood desk. The invitation, the pin, the debug head, the glass, and the wire match the Java seat.

**semantic-key:** PK-7169

## What stays

The opener stays: I'm opening a window with this atmosphere… would you like to step into it with me? The closer stays: Shape your replies around this mood — let it breathe through the words. The legacy activate sentence stays off a new send. The shell frame stays shell_mood and the line. Glass stays the shaping line, the opener, the field, and the closer. The switch does not gate that chat. The footer switch and the SSH checkbox stay where the earlier Rust mood seat put them. The mood title stays. An Ollama write stays off a Grok or Pool family. Rust still sends the whole working set and does not grow a pool session. A mood that is off adds no host seat. A body that does not fit returns no frame, leaves dropped false, and leaves the switch on. A group that does not fit still returns too large for the whole group. The chrome struct named MoodDesk stays the glass session. Java stays. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The outside-list number already next stays next. No new G-key, vault column, channel, AMWP type, color key, library, or Cargo.toml edit is added. No glossary row is added. No new public Java type is added. No new public Java method is added. No new JUnit class is added. A law is not added.

**semantic-key:** PK-7170

## The cause

The earlier Rust seat put the prior mood in the crate. The Java desk then took the invitation, the pin, and the debug head. That desk was marked smoked and named the version 1 cut. The crate still sends the older triple. This plate seats the smoked desk in Rust. The desk module does not paint. The window thread keeps the glass and the inspector.

**semantic-key:** PK-7171

## The seat

mood.rs is the desk. It does not use egui. It owns the opener, the closer, the legacy sentence, the invitation, the older-head strip, the pin placement, and the debug lines. main.rs names the module. chrome.rs aliases MOOD_OPENER, MOOD_CLOSER, and MOOD_ACTIVATE to that desk. The glass session keeps the name MoodDesk.

The invitation head is mood:, the saved line, shape:, the opener, and the closer, then the raw turn. There is no blank line between the head and the turn. The line is not written again between the two sentences. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. line_at_turn_head reads either head.

One record lives in aetheris-mood.yaml, version 1. The keys are pending and pins. A pin value is a cell id or head. Unknown keys are dropped on rewrite. pending is always written. pins is written when the map holds a row. The showing canvas id is copied from the vault canvas before a plan, a pipe, the inspector, a clear, and a delete. That id is not written.

Zero or one user turn already ahead of the target keeps the pin at head, and the splice rides the first included user cell in the send. More than one, and a newly defined or updated mood, sets pending. The same trimmed line is not an update. The Run that sends the next turn consumes pending and pins that target cell. The pin stays until the next define or update. Marks do not move the pin. An excluded pin slides to the first included user cell at or after the pin. A run whose target sits before the pin sends no splice. Delete of the pin walks back one remaining cell, or head when none sits behind. Clear turns sets that canvas pin to head and clears pending. The mood line stays. Clear mood and a new session clear the line, the switch, the pins, and pending. Switch off removes the splice and leaves the pin. Switch on with the same held line restores the splice at the pin.

A group send clones the seat. Each successful member consumes the pin on that clone, so a later member splices against the consumed pin. The caller file is written when the first sent cell is handed to a live mux. A refused frame, a too-large frame, or a frame that is not live leaves pending. The next successful send lands the same cell. Java commits the pin before the fits check. This seat writes the pin when the frame is wired.

The inspector header, while the line is present and the switch is on, is mood:, the line previewed at 80, then shape:, the opener, and the closer. Off reads mood: off and the preview. None reads mood: (none). The token estimate counts the opener, the line, and the closer. Debug lines are inserted after that count. Debug Mood is the File, Settings checkbox. The label is Debug Mood. It starts off. It is stored as debug_mood in aetheris-settings.yaml, version 1. painted_borders stays. Other keys stay. A second debug_mood key blocks the file. An absent key is false. On paints the invitation immediately before the user line the pointer seats. Those lines use the role mood and carry no card number. Off leaves the inspector as it is. Painting does not move the pin.

The status strip says mood active when the SSH checkbox and the footer switch are both on. The shell frame stays shell_mood and shell_mood_clear.

**semantic-key:** PK-7172

## Proof

The failure is the_folded_bar_sits_on_one_row. The SSH glyph rect top is 108.2. The status rect bottom is 64.1. The same rectangles fail on the recorded head. This plate does not restyle that bar. The Rust client crate check, offline, passed. A product compile is not required because Java stayed. The window stayed closed. No live POST was made. A pool turn was not sent. A live SSH connect was not made. Session::start was not called. chrome::run was not called.

**semantic-key:** PK-7173

## The tree

mood.rs is the desk. main.rs names the module. chrome.rs splices the invitation, keeps the pin, paints the debug head, and stores debug_mood. The anchor is RustMoodDesk on aa-sdd-source-prose.md. No other Rust file moves. No Java file moves. Cargo.toml is not edited. grid.rs stays.

**semantic-key:** PK-7174

## Side move

One side move is named. The chrome test module lists TRANSFER_LABEL, TRANSFER_WORD, SESSION_REPLACE_ASK, MENU_PROMPT_LONE, MENU_REPLY_LONE, and bar_press. The committed tests already call those names. The explicit import list omitted them, so the crate tests did not compile on the recorded head. The repair lets the suite run. It does not change product behavior. The sibling is none. Java tests are not this module. The requirement is the named Rust proof.

The pin file is written when a frame is handed to a live mux. That timing is the seat already named. The folded bar is not a side move. The failure is the same on the recorded head.

**semantic-key:** PK-7175

## Wire

Canvas, group, and pipe send an empty host seat, then the turns. The pinned turn's user text starts with mood:, the line, shape:, the opener, and the closer. The mood line is not repeated between the two sentences. Zero or one turn already in the transcript puts that head on the first turn. More than one turn and a new or updated mood puts that head on the turn being sent. The server replaces the empty host seat. The mood block is not a system row. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written.

SSH prompt turns are the Roux turns. The pinned user turn's text starts the same way. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin.

Glass sends the shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat.

Pool stays the Java porch rules. Rust still sends the whole working set and does not grow a pool session.

The inspector, while the line is present, keeps mood: as the line, and shape: is the opener and the closer on the following lines. Debug Mood paints that same head immediately before the user line. Those lines carry no card number. Painting does not move the pin.

**Truth.** mood.rs is the desk and does not use egui. It owns the invitation, the older-head strip, the pin, and the debug lines. main.rs names the module. chrome.rs aliases MOOD_OPENER, MOOD_CLOSER, and MOOD_ACTIVATE, and the glass session keeps the name MoodDesk. The pin file is written when a frame is handed to a live mux. Debug Mood starts off, stores debug_mood, and paints unnumbered mood lines immediately before the user line. The crate tests reported 194 passed and 1 failed. The failure is the_folded_bar_sits_on_one_row, with the SSH glyph top at 108.2 and the status bottom at 64.1, the same rectangles as the recorded head. A sentence that named the marker one step further was one ahead. Java stays.

**Reason.** The crate still sent the older triple after the Java desk took the invitation, the pin, and the debug head and was marked smoked as the version 1 cut. The desk module does not paint. The window thread keeps the glass and the inspector. Java commits the pin before the fits check. This seat writes the pin when the frame is wired.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not change grid.rs or Java sources. Do not edit Cargo.toml. Do not add a G-key, a vault column, a channel, an AMWP type, a color key, a library, a glossary row, a public Java type, a public Java method, a JUnit class, or a law. Do not advance the next marker on the stamp, and do not treat the marker one step further as next. Do not retarget branch, action, stage, or the when field. Do not let mood.rs use egui or paint. Do not rename the glass session away from MoodDesk. Do not repeat the mood line between the sentences or insert a blank line before the raw turn. Do not write the showing canvas id. Do not keep unknown keys on rewrite. Do not skip pending. Do not write pins when the map holds no row. Do not treat the same trimmed line as an update. Do not let marks move the pin. Do not write the pin on a refused, too-large, or not-live frame. Do not commit the Rust pin before the frame is wired. Do not share one pin across group members without a clone. Do not drop painted_borders. Do not accept a second debug_mood key. Do not number the debug lines. Do not let a paint move the pin. Do not restyle the folded bar. Do not treat that failure as new. Do not grow a pool session. Do not let the switch gate the glass. Do not drop TRANSFER_LABEL, TRANSFER_WORD, SESSION_REPLACE_ASK, MENU_PROMPT_LONE, MENU_REPLY_LONE, or bar_press from the chrome test module. Do not change product behavior with that import repair. Do not require a product compile. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-7926

# The mood modal uses the selected cell

The stamp names the build. The Rust mood modal uses the model of the selected canvas cell. Java already does. The selected cell is the seat. Java stays. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4523 PK-4524 PK-4525 PK-4526 PK-4527 PK-4528

## Approval

This sentence names the build. The Rust mood modal uses the model of the selected canvas cell. Java moodCard and moodTarget already do. Rust mood_focus_card, mood_glass_target, and mood_glass_title follow that cell. The open waits until the seat lands. The title and the send read that cell.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The exact Rust test the_mood_glass_uses_the_selected_cell runs offline inside the Rust client tree. A product compile is not required because Java stays.

The product commit is chrome.rs. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-7176

## Goal

The mood glass sends the model of the selected canvas cell, the same cell Java moodCard reads.

**semantic-key:** PK-7177

## What stays

The glass layout stays. The mood string, the switch, and the shaping chat stay. Java moodCard and moodTarget stay. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The next free outside number stays the number already next. No new G-key, vault column, channel, AMWP type, or library is added. Cargo.toml is not edited. No glossary row is added. No new public Java type is added. No new public method is added. No new JUnit class is added. A law is not added. The spine pages stay. No decision block is added on this plate.

**semantic-key:** PK-7178

## The cause

Java openMood reads moodCard, then moodTarget of that cell. Rust try_open_mood ran before the frame wrote the new focus, and the title stayed on the card stored at open. A later selected cell did not become the model the glass sent.

**semantic-key:** PK-7179

## The seat

mood_focus_card returns the selected canvas cell. An index past the deck falls through to the last cell. mood_glass_target is the tune Java moodTarget builds, including a grok id taken from the document when the stored word has none. mood_glass_title puts that short name on the bar. try_open_mood runs after the seat lands. The title and fire_mood_send read the selected cell, not the card captured at open. The private functions are mood_focus_card, mood_glass_target, and mood_glass_title.

**semantic-key:** PK-7180

## Proof

The exact Rust test the_mood_glass_uses_the_selected_cell passes, offline, inside the Rust client tree. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. A product compile is not required because Java stays.

**semantic-key:** PK-7181

## Prose

No new public method is added. The private Rust functions are mood_focus_card, mood_glass_target, and mood_glass_title.

**semantic-key:** PK-7182

## Scope

No side move is named on this plate.

**Truth.** The Rust mood glass sends the model of the selected canvas cell. mood_focus_card returns that cell, and an index past the deck falls through to the last cell. mood_glass_target matches Java moodTarget, including a grok id taken from the document when the stored word has none. mood_glass_title puts that short name on the bar. try_open_mood runs after the seat lands. The title and fire_mood_send read the selected cell. Java stays.

**Reason.** try_open_mood ran before the frame wrote the new focus, and the title stayed on the card stored at open, so a later selected cell was not the model the glass sent.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not move Java, grid.rs, moodCard, or moodTarget. Do not change the glass layout, the mood string, the switch, or the shaping chat. Do not add a G-key, a vault column, a channel, an AMWP type, a library, a Cargo.toml edit, a glossary row, a public Java type, a public method, a JUnit class, a law, or a decision block. Do not retarget branch, action, stage, or the when field. Do not open the glass before the seat lands. Do not read the card captured at open for the title or the send. Do not drop the fall-through to the last cell. Do not drop a grok id taken from the document when the stored word has none. Do not require a product compile. Do not skip the_mood_glass_uses_the_selected_cell. Do not open the window, send a live POST, send a pool turn, or open a live SSH session. Do not call Session::start or chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-7183

# The heatbar walk runs off the key

The stamp names the build. Rust cell input and VT100 typing stop waiting on the heatbar walk. Java already posts the newest beat and counts off the key. The newest beat is the seat. Java stays. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4529 PK-4530 PK-4531 PK-4532 PK-4533 PK-4534 PK-4535 PK-4536 PK-4537 PK-4538 PK-4539 PK-4540 PK-4541 PK-4542 PK-4543 PK-4544 PK-4545 PK-4546

## Approval

This sentence names the build. Rust cell input and VT100 typing post a heatbar beat. The queue keeps the newest beat. The walk runs off the key. The heatbar paints the last seated integer. Java offerTokenEdit and SshEditQueue already do this.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The exact Rust test the_heat_queue_keeps_the_newest_beat runs offline inside the Rust client tree. A product compile is not required because Java stays.

The product commit is chrome.rs. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-7184

## Goal

A key on a canvas cell or on the VT100 line returns without walking the transcript or the console grid. The newest beat is the one that runs.

**semantic-key:** PK-7185

## What stays

The folded bar stays. Java offerTokenEdit and SshEditQueue stay. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The next free outside number stays the number already next. No new G-key, vault column, channel, AMWP type, or library is added. Cargo.toml is not edited. No glossary row is added. No new public Java type is added. No new public method is added. No new JUnit class is added. A law is not added. The spine pages stay. No decision block is added on this plate.

**semantic-key:** PK-7186

## The cause

Java keeps one pending edit and walks it off the key. Rust inspect_body_pane and session_memory_text ran on the frame that paints the key. The heatbar waited on that walk.

**semantic-key:** PK-7187

## The seat

HeatQueue keeps one pending beat. keep_pending replaces that beat. seat_ready stores the integer only when the stamp is still current and nothing newer is waiting. run_heat is the walk. post_canvas_heat and post_vt_heat post from the frame. The frame paints the last seated integer. The private type is HeatQueue. The private functions are keep_pending, seat_ready, and run_heat.

**semantic-key:** PK-7188

## Proof

The exact Rust test the_heat_queue_keeps_the_newest_beat passes, offline, inside the Rust client tree. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. A product compile is not required because Java stays.

**semantic-key:** PK-7189

## Prose

No new public method is added. The private type is HeatQueue. The private functions are keep_pending, seat_ready, and run_heat.

**semantic-key:** PK-7190

## Scope

No side move is named on this plate.

**Truth.** A key on a canvas cell or on the VT100 line returns without walking the transcript or the console grid. HeatQueue keeps one pending beat. keep_pending replaces that beat. seat_ready stores the integer only when the stamp is still current and nothing newer is waiting. run_heat is the walk. post_canvas_heat and post_vt_heat post from the frame. The frame paints the last seated integer. Java stays.

**Reason.** inspect_body_pane and session_memory_text ran on the frame that paints the key, so the heatbar waited on that walk. Java offerTokenEdit and SshEditQueue already keep the newest beat off the key.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not move Java, offerTokenEdit, SshEditQueue, or grid.rs. Do not change the folded bar. Do not add a G-key, a vault column, a channel, an AMWP type, a library, a Cargo.toml edit, a glossary row, a public Java type, a public method, a JUnit class, a law, or a decision block. Do not retarget branch, action, stage, or the when field. Do not keep more than one pending beat. Do not paint a stamp that is no longer current. Do not walk on the key frame. Do not require a product compile. Do not skip the_heat_queue_keeps_the_newest_beat. Do not open the window, send a live POST, send a pool turn, or open a live SSH session. Do not call Session::start or chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-8224

# Merge members leave together

The stamp names the build. Merge input prompts leave together. Each member is its own call. Java stays. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4547 PK-4548 PK-4549 PK-4550

## Approval

This sentence names the build. Merge input prompts leave together. Each member is its own call. One member does not wait for another. The merge joins when every member is quiet.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. The Rust client crate tests, offline, passed these five and filtered 195: the_merge_members_leave_together, the_footer_spinner_clocks_the_running_card, the_group_sends_every_member_and_the_merge_holds_the_reply, clear_leaves_one_included_card, and the_delete_removes_the_armed_card. 5 passed. Java stays. Rust moves.

The product commit is chrome.rs. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-7191

## Goal

Merge members leave together. Each member is its own call.

**semantic-key:** PK-7192

## What stays

Java stays. The pipe stays one call after another. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The next free outside number stays the number already next. No new G-key, vault column, channel, AMWP type, or library is added. Cargo.toml is not edited. No glossary row is added. No new public Java type is added. No new public method is added. No new JUnit class is added. A law is not added. The spine pages stay. No decision block is added on this plate.

**semantic-key:** PK-7193

## The cause

RunAsk::Group wired the first member and held the rest. The next member waited until that call ended. Java already sends every member as its own call.

**semantic-key:** PK-7194

## The seat

Merge input prompts leave together. Each member is its own call. One member does not wait for another. The merge joins when every member is quiet. halt_run takes the focus. group_after waits until every member is quiet. The test is the_merge_members_leave_together.

**semantic-key:** PK-7195

## Proof

The five named tests passed, offline, inside the Rust client tree: the_merge_members_leave_together, the_footer_spinner_clocks_the_running_card, the_group_sends_every_member_and_the_merge_holds_the_reply, clear_leaves_one_included_card, and the_delete_removes_the_armed_card. 5 passed. 195 filtered. A product compile was not required because Java stayed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made.

**semantic-key:** PK-7196

## Prose

No new public method is added. halt_run takes the focus. group_after waits until every member is quiet.

**semantic-key:** PK-7197

## Side move

One side move is named. Stop cancels the focused running member. Other members keep running. Clear cancels every member that is still out. Delete cancels every cut member that is still out. The Run tip says each as its own call.

**Truth.** Merge input prompts leave together. Each member is its own call. One member does not wait for another. The merge joins when every member is quiet. halt_run takes the focus. group_after waits until every member is quiet. Stop cancels only the focused running member. Clear cancels every member still out. Delete cancels every cut member still out. The Run tip says each as its own call. 5 tests passed and 195 were filtered. Java stays.

**Reason.** RunAsk::Group wired the first member and held the rest, so the next member waited until that call ended. Java already sends every member as its own call.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not move Java or turn the pipe into parallel calls. Do not add a G-key, a vault column, a channel, an AMWP type, a library, a Cargo.toml edit, a glossary row, a public Java type, a public method, a JUnit class, a law, or a decision block. Do not retarget branch, action, stage, or the when field. Do not hold later members until the first call ends. Do not join the merge before every member is quiet. Do not let Stop cancel a member that is not the focused running member. Do not let Clear or Delete leave a still-out member running. Do not drop the Run tip. Do not skip any of the five named tests or the count 195 filtered. Do not require a product compile. Do not open the window, send a live POST, send a pool turn, or open a live SSH session. Do not call Session::start or chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-7198

# A canvas prompt keeps a span queue and a caret seat

The stamp names the build. The Rust canvas prompt keeps the undo seat Java already has. A text change is one span. Undo puts the caret where the edit began. Java stays. Rust moves. Where this page is thinner than the locked row, the locked row wins.

**semantic-key:** PK-7199

## Approval

This sentence names the build. The Rust canvas prompt keeps its own undo seat. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo puts the caret where the edit began. Redo restores the caret after the edit. A paste is one span. Import is one whole-text step. The limit is 100. The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The widget timer undoer is cleared. Java EditMemory stays the reference and is not edited.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. 13 passed. 200 filtered. Java stays, so a product compile is not required.

The product commit is edit.rs, main.rs, and chrome.rs. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-7200

## Goal

A canvas prompt keeps a span queue and a caret seat. Undo puts the caret where the edit began. Redo restores the caret after the edit.

**semantic-key:** PK-7201

## What stays

Java EditMemory stays. The SSH chords stay on the wire. Ctrl-Z stays byte 0x1A. Shift-Ctrl-Z stays ESC [ 1 ; 6 Z. Ctrl-Y is not the canvas redo chord. live_line stays a count. A history hop stays a Java SSH concern. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The next free outside number stays the number already next. No new G-key, vault column, channel, AMWP type, or library is added. Cargo.toml is not edited. No glossary row is added. No new public Java type is added. No new public method is added. No new JUnit class is added. A law is not added. The spine pages stay. No decision block is added on this plate. Clear and disconnect still drop the desk. A deleted cell drops its seat.

**semantic-key:** PK-7202

## The cause

Java EditMemory wraps the Swing undo stack. The canvas queues a span off the key and seats the caret where the edit began. Rust kept a full before-string and did not seat that caret. egui 0.37 keeps a timer Undoer on the text widget. That undoer snapshots the whole string. This plate does not use it. The canvas clears it so the chords have one owner.

**semantic-key:** PK-7203

## The seat

The Rust canvas prompt keeps its own undo seat. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo puts the caret where the edit began. Redo restores the caret after the edit. A paste is one span. Import is one whole-text step. The limit is 100. The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The widget timer undoer is cleared. Java EditMemory stays the reference and is not edited. edit.rs holds EditMemory and EditDesk. LIMIT is 100. note_span queues a span. Undo and redo drain the queue, then step. A span undo seats cursor_before. A span redo seats cursor_after. note drains first, so an import sits above any queued spans. The same text only moves the caret. note_prompt diffs one contiguous span. A paste or a selection replace is one span. take_caret returns the armed caret for that cell. The prompt seats that caret before the widget shows and again after it shows, then clears the widget undoer. Applying writes nothing. Each cell keeps its own stack.

**semantic-key:** PK-7204

## Proof

13 passed. 200 filtered. The tests are the_undo_seats_the_caret_where_the_insert_began, the_backspace_undo_restores_the_character, the_paste_is_one_span, the_new_edit_drops_redo, the_limit_drops_the_oldest, the_apply_records_nothing, the_same_text_moves_the_cursor, the_import_note_restores_the_old_line, the_cells_keep_their_own_stacks, the_discard_drops_the_stack, the_import_sits_above_the_queued_spans, the_queue_holds_the_span_until_undo, and the_prompt_window_stays_at_the_row_count. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. A product compile is not required because Java stays.

**semantic-key:** PK-7205

## Prose

No new public method is added. The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The module is edit. main.rs gains mod edit.

**semantic-key:** PK-7206

## Side move

No side move is named on this plate. Clearing the widget timer undoer is the seat. The chords have one owner.

**semantic-key:** PK-7207

## Wire

The wire stanza stays empty. The doors do not gain, lose, or reorder a line. Ctrl-Z stays byte 0x1A. Shift-Ctrl-Z stays ESC [ 1 ; 6 Z. Those bytes were already on the wire.

**Truth.** The Rust canvas prompt keeps its own undo seat. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo seats cursor_before. Redo seats cursor_after. The limit is 100. LIMIT is 100. edit.rs holds EditMemory and EditDesk. note_span queues a span. note drains first, so an import sits above any queued spans. take_caret returns the armed caret. The prompt seats that caret before the widget shows and again after it shows, then clears the widget undoer. Applying writes nothing. Each cell keeps its own stack. The private functions are note_prompt, note, undo, and redo. The module is edit. main.rs gains mod edit. Java EditMemory stays.

**Reason.** Java EditMemory wraps the Swing undo stack and seats the caret where the edit began. Rust kept a full before-string and did not seat that caret. egui 0.37 keeps a timer Undoer that snapshots the whole string, and this plate clears that undoer so the chords have one owner.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not edit Java EditMemory or grid.rs. Do not use the widget timer undoer. Do not keep a full before-string as the undo seat. Do not let Ctrl-Y become the canvas redo chord. Do not change Ctrl-Z from byte 0x1A or Shift-Ctrl-Z from ESC [ 1 ; 6 Z. Do not turn live_line into anything other than a count. Do not move a history hop off Java SSH. Do not let Clear or disconnect keep the desk. Do not let a deleted cell keep its seat. Do not add a G-key, a vault column, a channel, an AMWP type, a library, a Cargo.toml edit, a glossary row, a public Java type, a public method, a JUnit class, a law, or a decision block. Do not retarget branch, action, stage, or the when field. Do not raise the limit above 100. Do not keep an entry once the limit of 100 drops the oldest. Do not record an apply. Do not let the same text become a new span. Do not split a paste or a selection replace into more than one span. Do not skip any of the thirteen named tests or the count 200 filtered. Do not require a product compile. Do not open the window, send a live POST, send a pool turn, or open a live SSH session. Do not call Session::start or chrome::run. Do not add, remove, or reorder a door line. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-7208

# Each canvas user keeps a deck

The stamp names the build. Each canvas user keeps a running turn while another SSH session works. Java stays. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4551 PK-4552 PK-4553 PK-4554 PK-4555 PK-4556 PK-4557 PK-4558 PK-4559 PK-4560

## Approval

This sentence names the build. Each canvas user keeps a deck. A running turn stays on that deck when another user takes the glass. A parked SSH session keeps feeding the user who owns it. The glass hears that user only when that user is on screen. The same user reconnect does not reload the deck. Replacing this pane's socket stops a run that lived on it. A run on another pane stays. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. No new OS thread is added. The private types are ParkedDeck, ScopeHome, DeckRefs, FeedOut, and LoadedCanvas. The private functions are scope_home, feed_parts, deck_key, note_replaced_run, wire_owned, wire_pane, lift_showing, plant_showing, remember_deck, plant_user, load_user_canvas, adopt_loaded, seat_canvas_user, card_snap, and deliver_scope. Java stays. Rust moves. chrome.rs is the only product file.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. 1 passed. 213 filtered. Java stays, so a product compile is not required. The folded bar failure stays the rectangles already seated. SSH top is 108.2 and status bottom is 64.1. The thinking-strip check still splits on the tag rule already locked. The delta landed in a new block. Those two sit outside this diff.

The product commit is chrome.rs. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-7209

## Goal

Each canvas user keeps a deck. A running turn stays on that deck when another user takes the glass. A parked SSH session keeps feeding the user who owns it.

**semantic-key:** PK-7210

## What stays

Java stays. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The next free outside number stays the number already next. No new G-key, vault column, channel, AMWP type, or library is added. Cargo.toml is not edited. No glossary row is added. No new public Java type is added. No new public method is added. No new JUnit class is added. A law is not added. The spine pages stay. No decision block is added on this plate. The thinking file stays one file. The same user reconnect does not reload the deck. Disconnect of the focused session still clears the showing deck only. Pull's new turn still starts Excluded. New cards still start Included. Undo still moves with the deck. No new OS thread is added.

**semantic-key:** PK-7211

## The cause

One canvas deck is on the glass. Switching the user reloaded that deck and marked a stored running cell interrupted. A parked SSH session held its scope until the pane took focus. Each SSH session already has its own read thread. The missing piece is a per-user deck drained on the window thread.

**semantic-key:** PK-7212

## The seat

Each canvas user keeps a deck. A running turn stays on that deck when another user takes the glass. A parked SSH session keeps feeding the user who owns it. The glass hears that user only when that user is on screen. The same user reconnect does not reload the deck. Replacing this pane's socket stops a run that lived on it. A run on another pane stays. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. No new OS thread is added. The private types are ParkedDeck, ScopeHome, DeckRefs, FeedOut, and LoadedCanvas. The private functions are scope_home, feed_parts, deck_key, note_replaced_run, wire_owned, wire_pane, lift_showing, plant_showing, remember_deck, plant_user, load_user_canvas, adopt_loaded, seat_canvas_user, card_snap, and deliver_scope.

**semantic-key:** PK-7213

## Proof

1 passed. 213 filtered. The test is a_parked_user_keeps_the_run. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. A product compile is not required because Java stays. The folded bar failure stays the rectangles already seated. SSH top is 108.2 and status bottom is 64.1. The thinking-strip check still splits on the tag rule already locked. The delta landed in a new block. Those two sit outside this diff.

**semantic-key:** PK-7214

## Prose

No new public method is added. The private types are ParkedDeck, ScopeHome, DeckRefs, FeedOut, and LoadedCanvas. ScopeHome is the private enum. The private functions are scope_home, feed_parts, deck_key, note_replaced_run, wire_owned, wire_pane, lift_showing, plant_showing, remember_deck, plant_user, load_user_canvas, adopt_loaded, seat_canvas_user, card_snap, and deliver_scope. The file is chrome.rs. No new module file is added.

**semantic-key:** PK-7215

## Side move

No side move is named on this plate.

**semantic-key:** PK-7929

## Wire

The wire stanza stays empty. The doors do not gain, lose, or reorder a line.

**Truth.** Each canvas user keeps a deck. A running turn stays on that deck when another user takes the glass. A parked SSH session keeps feeding the user who owns it. The glass hears that user only when that user is on screen. The same user reconnect does not reload the deck. Replacing this pane's socket stops a run that lived on it. A run on another pane stays. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. No new OS thread is added. The private types are ParkedDeck, ScopeHome, DeckRefs, FeedOut, and LoadedCanvas. ScopeHome is the private enum. chrome.rs is the only product file. 1 passed. 213 filtered. SSH top is 108.2 and status bottom is 64.1. Java stays.

**Reason.** One canvas deck sat on the glass. Switching the user reloaded that deck and marked a stored running cell interrupted. A parked SSH session held its scope until the pane took focus. Each SSH session already has its own read thread. The missing piece is a per-user deck drained on the window thread.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not move Java or grid.rs. Do not add an OS thread or a module file. Do not reload the deck on the same user reconnect. Do not stop a run on another pane when this pane's socket is replaced. Do not let Stop, Clear, or Delete cancel a run on a pane that does not own it. Do not split the thinking file. Do not leave the in-memory log behind the seat. Do not let disconnect of the focused session clear a deck that is not showing. Do not start Pull's new turn as anything other than Excluded. Do not start a new card as anything other than Included. Do not separate undo from the deck. Do not let the glass hear a user who is not on screen. Do not add a G-key, a vault column, a channel, an AMWP type, a library, a Cargo.toml edit, a glossary row, a public Java type, a public method, a JUnit class, a law, or a decision block. Do not retarget branch, action, stage, or the when field. Do not skip a_parked_user_keeps_the_run or the counts 1 passed and 213 filtered. Do not require a product compile. Do not treat the folded bar failure or the thinking-strip check as part of this diff. Do not move SSH top off 108.2 or status bottom off 64.1. Do not open the window, send a live POST, send a pool turn, or open a live SSH session. Do not call Session::start or chrome::run. Do not add, remove, or reorder a door line. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-8193

# One press seats one cell

The stamp names the build. The I, E, P, and O marks seat one cell and leave the others. Java stays. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4561 PK-4562

## Approval

This sentence names the build. A press on I, E, P, or O seats that cell only. The other cells stay, including a new turn at the bottom. The letter does not take focus. Space and Enter do not seat it. A pin commit does not ride that press. Enter in the pin field still commits on the frame the field loses focus. The next frame of that loss does not commit. Pull still starts a new turn Excluded. A new card still starts Included. The private functions are seat_mark, seat_mark_button, mark_pressed, pin_enter_due, and pin_commit_after_mark. The field is pin_enter_seen. Java stays. Rust moves. chrome.rs is the only product file.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. 1 passed. 214 filtered. Java stays, so a product compile is not required. The folded bar failure stays the rectangles already seated. SSH top is 108.2 and status bottom is 64.1. The thinking-strip check still splits on the tag rule already locked. The delta landed in a new block. Those two sit outside this diff.

The product commit is chrome.rs. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-7216

## Goal

One press seats one cell. The other cells stay, including a new turn at the bottom.

**semantic-key:** PK-7217

## What stays

Java stays. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The next free outside number stays the number already next. No new G-key, vault column, channel, AMWP type, or library is added. Cargo.toml is not edited. No glossary row is added. No new public Java type is added. No new public method is added. No new JUnit class is added. A law is not added. The spine pages stay. No decision block is added on this plate. Pull still starts a new turn Excluded. A new card still starts Included. OK and Enter still commit the pin. The four Run marks stay. Included and Input still go out as prompt. Output stays context. Excluded stays skipped.

**semantic-key:** PK-7218

## The cause

The four letters were focusable selectable buttons on an unstable id. A click could land on another cell when the deck grew. Space or Enter on a focused letter could seat it. Enter in the pin field reports lost focus for two frames. A later Enter committed the pin. A miss goes Excluded. An empty field misses every card, including a new turn at the bottom. The I press then restored only the cell under the pointer.

**semantic-key:** PK-7219

## The seat

A press on I, E, P, or O seats that cell only. The other cells stay, including a new turn at the bottom. The letter does not take focus. Space and Enter do not seat it. A pin commit does not ride that press. Enter in the pin field still commits on the frame the field loses focus. The next frame of that loss does not commit. Pull still starts a new turn Excluded. A new card still starts Included. The private functions are seat_mark, seat_mark_button, mark_pressed, pin_enter_due, and pin_commit_after_mark. The field is pin_enter_seen.

**semantic-key:** PK-7220

## Proof

1 passed. 214 filtered. The test is a_mark_press_seats_one_cell. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. A product compile is not required because Java stays. The folded bar failure stays the rectangles already seated. SSH top is 108.2 and status bottom is 64.1. The thinking-strip check still splits on the tag rule already locked. The delta landed in a new block. Those two sit outside this diff.

**semantic-key:** PK-7221

## Prose

No new public method is added. The private functions are seat_mark, seat_mark_button, mark_pressed, pin_enter_due, and pin_commit_after_mark. The field is pin_enter_seen. The file is chrome.rs. No new module file is added.

**semantic-key:** PK-7222

## Wire

The wire stanza stays empty. The doors do not gain, lose, or reorder a line.

**Truth.** A press on I, E, P, or O seats that cell only. The other cells stay, including a new turn at the bottom. The letter does not take focus. Space and Enter do not seat it. A pin commit does not ride that press. Enter in the pin field commits on the frame the field loses focus. The next frame of that loss does not commit. Pull still starts a new turn Excluded. A new card still starts Included. The field is pin_enter_seen. The private functions are seat_mark, seat_mark_button, mark_pressed, pin_enter_due, and pin_commit_after_mark. chrome.rs is the only product file. 1 passed. 214 filtered. SSH top is 108.2 and status bottom is 64.1. The four Run marks stay. Included and Input still go out as prompt. Output stays context. Excluded stays skipped. Java stays.

**Reason.** The four letters were focusable selectable buttons on an unstable id, so a click could land on another cell when the deck grew. Enter in the pin field reports lost focus for two frames. A later Enter committed the pin, a miss went Excluded, and an empty field missed every card, including a new turn at the bottom. The I press then restored only the cell under the pointer.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not move Java or grid.rs. Do not add a module file. Do not let the letter take focus. Do not let Space or Enter seat the letter. Do not let a pin commit ride that press. Do not commit on the next frame of a pin-field focus loss. Do not drop the two-frame lost-focus report. Do not start Pull's new turn as anything other than Excluded. Do not start a new card as anything other than Included. Do not send Included or Input as anything other than prompt. Do not send Output as anything other than context. Do not stop skipping Excluded. Do not drop the four Run marks. Do not let OK or Enter stop committing the pin. Do not add a G-key, a vault column, a channel, an AMWP type, a library, a Cargo.toml edit, a glossary row, a public Java type, a public method, a JUnit class, a law, or a decision block. Do not retarget branch, action, stage, or the when field. Do not skip a_mark_press_seats_one_cell or the counts 1 passed and 214 filtered. Do not require a product compile. Do not treat the folded bar failure or the thinking-strip check as part of this diff. Do not move SSH top off 108.2 or status bottom off 64.1. Do not open the window, send a live POST, send a pool turn, or open a live SSH session. Do not call Session::start or chrome::run. Do not add, remove, or reorder a door line. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-8194

# Each user's run stays with that user

The stamp names the build. Each user's run and thinking stay with that user while another user runs. Java stays. Rust moves. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4563 PK-4564 PK-4565 PK-4566 PK-4567 PK-4568 PK-4569 PK-4570

## Approval

This sentence names the build. Each user's run and thinking stay with that user. A second user can run while the first is still running. The focused session does not paint another user's thinking on the glass. The glass user's execute leaves on that user's session. SSH sessions already run on their own sockets and stay that way. The thinking scroll follows the user on the glass. The private functions are focused_canvas_home, glass_run_pane, think_salt, glass_pane, live_glass_pane, and land_focused_canvas. The test-only names are canvas_user, answer_mux, and take_wires. The files are chrome.rs and session.rs. Java stays. Rust moves.

Branch, action, stage, and the when field are not retargeted. The next marker stays that same marker until the ledger.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. 1 passed. 215 filtered. 1 passed. 215 filtered. Java stays, so a product compile is not required. The folded bar failure stays the rectangles already seated. SSH top is 108.2 and status bottom is 64.1. The thinking-strip check still splits on the tag rule already locked. The delta landed in a new block. Those two sit outside this diff.

The product commit is chrome.rs and session.rs. grid.rs stays out. Its message states that the change is in the tree. Its message states that the change landed. The next marker is the one after that spend.

**semantic-key:** PK-7223

## Goal

Each user's run and thinking stay with that user. A second user can run while the first is still running.

**semantic-key:** PK-7224

## What stays

The parked deck stays. The same user does not reload. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. Every member frame still leaves together. One press still seats one cell. The model header still starts each thinking stream. Java stays. grid.rs stays. Slices already locked stay locked. The closed feature stays closed. The open batch already in the working tree stays open. The paste gap already seated stays. The next free number stays the number already next. The next free outside number stays the number already next. No new G-key, vault column, channel, AMWP type, or library is added. Cargo.toml is not edited. No glossary row is added. No new public Java type is added. No new public method is added. No new JUnit class is added. A law is not added. The user tip stays. The footer spinner still follows the vault on screen.

**semantic-key:** PK-7225

## The cause

The focused session wrote every canvas stream onto the glass. Run sent only on that socket. A second user's execute never left, and the first user's thinking stayed on the glass.

**semantic-key:** PK-7226

## The seat

Each user's run and thinking stay with that user. A second user can run while the first is still running. The focused session does not paint another user's thinking on the glass. The glass user's execute leaves on that user's session. SSH sessions already run on their own sockets and stay that way. The thinking scroll follows the user on the glass. The private functions are focused_canvas_home, glass_run_pane, think_salt, glass_pane, live_glass_pane, and land_focused_canvas. The test-only names are canvas_user, answer_mux, and take_wires. The files are chrome.rs and session.rs. Java stays. Rust moves.

**semantic-key:** PK-7227

## Proof

1 passed. 215 filtered. 1 passed. 215 filtered. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. A product compile is not required because Java stays. The folded bar failure stays the rectangles already seated. SSH top is 108.2 and status bottom is 64.1. The thinking-strip check still splits on the tag rule already locked. The delta landed in a new block. Those two sit outside this diff.

**Truth.** Each user's run and thinking stay with that user. A second user can run while the first is still running. The focused session does not paint another user's thinking on the glass. The glass user's execute leaves on that user's session. SSH sessions already run on their own sockets. The thinking scroll follows the user on the glass. The private functions are focused_canvas_home, glass_run_pane, think_salt, glass_pane, live_glass_pane, and land_focused_canvas. The test-only names are canvas_user, answer_mux, and take_wires. The files are chrome.rs and session.rs. a_second_user_keeps_its_think_and_its_run passed. 1 passed. 215 filtered. a_parked_user_keeps_the_run passed. 1 passed. 215 filtered. SSH top is 108.2 and status bottom is 64.1. Java stays. grid.rs stays out.

**Reason.** The focused session wrote every canvas stream onto the glass. Run sent only on that socket. A second user's execute never left, and the first user's thinking stayed on the glass.

**Avoidances.** Do not rewrite slices already locked, and do not close the open batch or the closed feature. Do not move Java or grid.rs. Do not reload the same user. Do not stop another pane's run when a socket is replaced. Do not let Stop, Clear, or Delete cancel a run on a pane that does not own it. Do not split the thinking file. Do not leave the in-memory log behind the seat. Do not hold member frames apart. Do not seat more than the pressed cell. Do not drop the model header from a thinking stream. Do not drop the user tip. Do not move the footer spinner off the vault on screen. Do not paint another user's thinking on the focused glass. Do not send the glass user's execute on another user's session. Do not move SSH sessions off their own sockets. Do not add a G-key, a vault column, a channel, an AMWP type, a library, a Cargo.toml edit, a glossary row, a public Java type, a public method, a JUnit class, a law, or a decision block. Do not retarget branch, action, stage, or the when field. Do not skip either named test or either count of 1 passed and 215 filtered. Do not require a product compile. Do not treat the folded bar failure or the thinking-strip check as part of this diff. Do not move SSH top off 108.2 or status bottom off 64.1. Do not open the window, send a live POST, send a pool turn, or open a live SSH session. Do not call Session::start or chrome::run. Do not advance the next free number or the outside-list number.

**semantic-key:** PK-7228

## Slice

The arm frame draws the full lunge, and later frames ease it the way the Java timer eases it.

The beat stays on one id. The pulse runs when the user types on the VT100 tab and on the Aetheris canvas tab.

**semantic-key:** PK-7229

## What stays

The track is 5 pixels, the needle is 2 by 9, and the lunge is 6 pixels.

The posts stay. The inks stay. A held Roux count still replaces the glass. Java stays. The open batch already in the working tree stays open.

**semantic-key:** PK-8223

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This land leaves the working set as it stands.

**Truth.** Typing on the VT100 tab or the Aetheris canvas tab pulses the heatbar beat. The arm frame shows the full lunge, later frames ease it, and the beat stays on one id. Rust is the named tree. Java stays.

**Reason.** The Java timer already eases this beat. The Rust heatbar should use that same shape, full lunge first, without a second id and without moving the track, the needle, the posts, or the inks.

**Avoidances.** Do not open a second revision. Do not move Java. Do not open an extra cut. Do not change the 5 pixel track, the 2 by 9 needle, the 6 pixel lunge, the posts, or the inks. Do not stop a held Roux count from replacing the glass. Do not put the beat on a second id. Do not add, remove, or reorder a system line. Do not disturb the working set.

**semantic-key:** PK-7917

# Canvas text panes can wear the Java cell frame

The Java cell frame is asked for on the Rust canvas text panes, and the quiet plate stays. The switch starts off. Rust moves. Java stays. The preserve token keeps the off path. The working word is NUT, and that word stays the owner's. This page does not mint a glossary row. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-4573 PK-4574 PK-4575 PK-4576 PK-4577

## Dependencies

The Rust text panes wear a quiet plate. Java paints a medium-green input frame and a sapphire reply frame.

An empty reply stays gray. No new color key is added. No new library is added.

The proof checks the Rust client crate offline. The window stays closed.

**semantic-key:** PK-7230

## Seat

File, Settings opens the window. The checkbox is Painted text borders.

Off keeps the quiet plate. On paints the Java cell frame on a canvas text pane that already paints. A missing settings file stays off.

**Truth.** Painted text borders starts off and keeps the quiet plate. On paints the Java cell frame only where a canvas text pane already paints. A missing settings file stays off. Rust moves. Java stays.

**Reason.** The quiet plate is the current Rust look. The switch has to start off so that look survives, and a missing file must not invent an on.

Do not move Java. Do not add a color key or a library. Do not start the switch on. Do not write a missing settings file. Do not paint a pane that was not painted. Do not turn an empty reply into a framed reply on the off path. Do not mint a glossary row for NUT. Do not open the window for the proof.

**semantic-key:** PK-7231

# Button borders keep two points of clearance

The button borders stay clear of the clip. BB1 was cutting the bottom of the buttons. The Close control on the thinking pane was cut the same way. The clearance is 2 points on every side. Rust moves. Java stays. The working word is NUT, and that word stays the owner's. This page does not mint a glossary row. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-4578 PK-4579 PK-4580 PK-4581 PK-4582 PK-4583 PK-4584

## Dependencies

The column font is the override. The button paints with that override.

The lane was the stock interact height, and the clip was the lane. TextStyle Button ignores the override. The frame pad sits outside the content clip. No new color key is added. No new library is added.

The proof checks the Rust client crate offline. The window stays closed.

**semantic-key:** PK-7232

## Seat

The lane follows the pane font. The clip stays 2 points outside the button border.

BB1, the east combo, the tool row, and Close take that clearance. The folded line lift stays. The footer pad, the corner, and the edge stay. C keeps its drawn mark.

**Truth.** The lane follows the pane font, and the clip stays 2 points outside the button border on BB1, the east combo, the tool row, and Close. The folded line lift, the footer pad, the corner, and the edge stay. Rust moves. Java stays.

**Reason.** The clip was the stock interact lane, and TextStyle Button ignores the font override, so the buttons and Close were cut. Two points of clearance outside the border is the fix.

Do not move Java. Do not add a color key or a library. Do not keep the stock interact height as the lane. Do not drop the folded line lift, the footer pad, the corner, or the edge. Do not change the drawn mark of C. Do not mint a glossary row for NUT. Do not open the window for the proof.

**semantic-key:** PK-7233

# The Rust mood seat follows the Java desk

The Rust mood seat follows the Java desk. Java stays. Rust moves. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-4585 PK-4586 PK-4587 PK-4588

## Dependencies

The Java desk already seats the opener, the constructed mood, and the closer. fit_blocks, mood_shape_tuned, ssh_mood_line, counted_inspector, and mood_inspector_head are the Rust seats. The glass session struct stays the glass session. The protocol stays private functions. ollama_pane_owns_cell is the carry from the showing-canvas family guard. Family is not copied out of the session. A missing family still writes. The console path does not ask. grid.rs stays. model.rs was clean before this plate. No new library is added. Cargo.toml is not edited. No new JUnit class is added. No crate test is run.

The proof checks the Rust client crate offline. That check passed on the working tree. A product compile is not required because Java stays. The window stays closed.

**semantic-key:** PK-7234

## Seat

The Rust mood seat follows the Java desk. Canvas, group, and pipe send the empty host seat, the opener, the constructed mood, and the closer while the switch is on and the line is non-blank. A long canvas does not drop that triple. The checkbox makes SSH available. The switch off clears a checked SSH seat. The glass seats the opener, the field, and the closer. The switch does not gate that chat.

**semantic-key:** PK-7235

## Out of scope

Java stays out. The mood string text stays out. The Mood title bar stays out. Approve, Cancel, and Clear mood stay out. Glass geometry stays out. The SSH checkbox label stays out. The yellow inspector card stays out. Thinking flags stay out. try_clear stays out. TokenEditQueue stays out. SshEditQueue stays out. EditMemory stays out. A pool session in the crate stays out. grid.rs stays out. No new public type is added. No new public method is added.

**Truth.** The Rust mood seat follows the Java desk. Canvas, group, and pipe send the empty host seat, the opener, the constructed mood, and the closer while the switch is on and the line is non-blank. A long canvas does not drop that triple. The switch off clears a checked SSH seat. The glass seats the opener, the field, and the closer, and the switch does not gate that chat. The protocol stays private functions. Java stays.

**Reason.** The Java desk already seats the opener, the constructed mood, and the closer. The Rust client has to seat mood the same way, and this page names that catch-up.

Do not move Java or require a product compile. Do not move grid.rs. Do not add a library, a Cargo.toml edit, a JUnit class, a public type, a public method, or a crate test. Do not copy family out of the session. Do not stop a missing family from writing. Do not let the console path ask. Do not drop the triple on a long canvas. Do not let the switch leave a checked SSH seat in place. Do not let the switch gate the glass. Do not change the mood string text, the Mood title bar, Approve, Cancel, Clear mood, glass geometry, the SSH checkbox label, the yellow inspector card, thinking flags, try_clear, TokenEditQueue, SshEditQueue, or EditMemory. Do not grow a pool session in the crate. Do not treat model.rs as already dirty before this plate. Do not open the window for the proof.

**semantic-key:** PK-7236

# The Rust mood layer matches the smoked desk

The Rust client carries the smoked mood desk. The invitation, the pin, the debug head, the glass, and the wire follow the Java seat. Java stays. Rust moves. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-4589 PK-4590 PK-4591 PK-4592 PK-4593 PK-4594

## Dependencies

The earlier Rust seat put the prior mood in the crate. The later Java seats moved the desk after that seat. The smoked pass is the desk, the switch, the splice, the invitation, and the debug head. This plate seats that pass in the crate. mood.rs is the desk and does not paint. The glass session in chrome stays under the name MoodDesk. main.rs names the module. Cargo.toml is not edited. grid.rs stays. Java stays.

The proof runs the Rust client crate tests offline and checks the crate offline, inside the Rust client tree. The tests reported 194 passed and 1 failed. The failure is the_folded_bar_sits_on_one_row. The same rectangles fail on the recorded head. The crate check passed. A product compile was not required because Java stayed. The window stays closed.

**semantic-key:** PK-7237

## Seat

One desk seats the invitation, the pin, and the debug lines. Canvas, group, and pipe splice that invitation onto the pinned user turn. The empty host seat stays the line the server replaces. The pin file is written when a frame is handed to a live mux.

**semantic-key:** PK-7238

## Out of scope

The Java sources stay out. A new pool session stays out. grid.rs stays out. The connection-bar row stays out. A smoke row stays out. A push stays out.

**Truth.** One desk seats the invitation, the pin, and the debug lines. Canvas, group, and pipe splice that invitation onto the pinned user turn. The empty host seat stays the line the server replaces. The pin file is written when a frame is handed to a live mux. mood.rs does not paint. The glass session stays named MoodDesk. Java stays. Rust moves.

**Reason.** The Java desk moved after the crate was seated, and the smoked pass has to land in the crate. The pin file is written when a frame is handed to a live mux.

Do not move Java or grid.rs. Do not edit Cargo.toml. Do not let mood.rs paint. Do not rename the glass session. Do not grow a pool session. Do not restyle the connection-bar row. Do not file a smoke row. Do not drop the failure the_folded_bar_sits_on_one_row or the counts 194 passed and 1 failed. Do not treat that failure as new. Do not open the window for the proof.

**semantic-key:** PK-7239

# The mood modal uses the selected cell

The Rust mood modal uses the model of the selected canvas cell. Java already does. This page does not move that point. The formal plan is the page that names the build.


**semantic-key:** PK-4595 PK-4596 PK-4597 PK-4598 PK-4599 PK-4600

## The cause

Java moodCard reads the focused turn on the showing canvas. moodTarget resolves that cell. The Rust glass opened on the focus from the previous frame, and the title stayed on the card captured at open.

**semantic-key:** PK-7240

## The seat

Rust mood_focus_card is the selected canvas cell. mood_glass_target is Java moodTarget for that cell. mood_glass_title is the bar. The open waits until the seat lands. The title and the send read that cell. Java stays. Rust moves. chrome.rs is the seat.

**semantic-key:** PK-7241

## What stays

No new public type is added. No new public method is added. No new JUnit class is added. Cargo.toml is not edited. No new library is added. grid.rs stays. The window stays closed. No system line is added, removed, or reordered. The doors do not gain, lose, or reorder a line.

**Truth.** Rust mood_focus_card is the selected canvas cell. mood_glass_target is Java moodTarget for that cell. mood_glass_title is the bar. The open waits until the seat lands. The title and the send read that cell. Java stays. Rust moves. chrome.rs is the seat.

**Reason.** The Rust glass opened on the focus from the previous frame, and the title stayed on the card captured at open. Java moodCard already reads the focused turn on the showing canvas.

**Avoidances.** Do not treat this page as stamped. Do not move Java or grid.rs. Do not add a public type, a public method, a JUnit class, a library, or a Cargo.toml edit. Do not open the glass before the seat lands. Do not keep the title on the card captured at open. Do not add, remove, or reorder a system line. Do not open the window.

**semantic-key:** PK-7930

# The heatbar walk runs off the key

Rust cell input and VT100 typing stop waiting on the heatbar walk. Java already posts the newest beat and counts off the key. This page does not move that point. The formal plan is the page that names the build.


**semantic-key:** PK-4601 PK-4602 PK-4603 PK-4604 PK-4605 PK-4606 PK-4607 PK-4608 PK-4609 PK-4610 PK-4611 PK-4612 PK-4613 PK-4614 PK-4615 PK-4616 PK-4617 PK-4618

## The cause

Java offerTokenEdit and SshEditQueue keep the newest beat and walk off the key. Rust was building the inspector and the console text on the frame that paints the key. That walk is the lag.

**semantic-key:** PK-7242

## The seat

HeatQueue keeps the newest beat. keep_pending drops the older pending beat. seat_ready paints only when that stamp is still current. run_heat walks off the frame. The folded bar stays. Java stays. Rust moves. chrome.rs is the seat.

**semantic-key:** PK-7243

## What stays

No new public type is added. No new public method is added. No new JUnit class is added. Cargo.toml is not edited. No new library is added. grid.rs stays. The window stays closed. No system line is added, removed, or reordered. The doors do not gain, lose, or reorder a line.

**Truth.** HeatQueue keeps the newest beat. keep_pending drops the older pending beat. seat_ready paints only when that stamp is still current. run_heat walks off the frame. The folded bar stays. Java stays. Rust moves. chrome.rs is the seat.

**Reason.** Rust was building the inspector and the console text on the frame that paints the key. That walk is the lag. Java already posts the newest beat off the key.

**Avoidances.** Do not treat this page as stamped. Do not move Java, offerTokenEdit, SshEditQueue, or grid.rs. Do not change the folded bar. Do not add a public type, a public method, a JUnit class, a library, or a Cargo.toml edit. Do not keep an older pending beat. Do not paint a stamp that is no longer current. Do not walk on the frame that paints the key. Do not add, remove, or reorder a system line. Do not open the window.

**semantic-key:** PK-7931

# Merge members leave together

Merge input prompts leave together. Each member is its own call. This page does not move that point. The formal plan is the page that names the build.


**semantic-key:** PK-4619 PK-4620 PK-4621 PK-4622

## The seat

Merge input prompts leave together. Each member is its own call. One member does not wait for another. The merge joins when every member is quiet. Java already sends each member as its own call. Java stays. Rust moves.

**semantic-key:** PK-7244

## What stays

No new public type is added. No new public method is added. No new JUnit class is added. Cargo.toml is not edited. No new library is added. Java stays. grid.rs stays. The window stays closed. The next free number stays the number already next. The next free outside number stays the number already next. The open batch already in the working tree stays open. The paste gap already seated stays. No system line is added, removed, or reordered. The doors do not gain, lose, or reorder a line.

**Truth.** Merge input prompts leave together. Each member is its own call. One member does not wait for another. The merge joins when every member is quiet. Java stays. Rust moves.

**Reason.** RunAsk::Group wired the first member and held the rest. Java already sends every member as its own call.

**Avoidances.** Do not treat this page as stamped. Do not move Java or grid.rs. Do not add a public type, a public method, a JUnit class, a library, or a Cargo.toml edit. Do not hold one member until another call ends. Do not join the merge before every member is quiet. Do not close the open batch. Do not add, remove, or reorder a system line. Do not open the window.

**semantic-key:** PK-7245

# A canvas prompt keeps a span queue and a caret seat

The Rust canvas prompt keeps the undo seat Java already has. A text change is one span. Undo puts the caret where the edit began. This page does not move that point. The formal plan is the page that names the build.

**semantic-key:** PK-8225

## The seat

The Rust canvas prompt keeps its own undo seat. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo puts the caret where the edit began. Redo restores the caret after the edit. A paste is one span. Import is one whole-text step. The limit is 100. The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The widget timer undoer is cleared. Java EditMemory stays the reference and is not edited. Java stays. Rust moves.

**semantic-key:** PK-7246

## What stays

No new public type is added. No new public method is added. No new JUnit class is added. Cargo.toml is not edited. No new library is added. Java stays. grid.rs stays. The window stays closed. The SSH chords stay on the wire. Ctrl-Y is not the canvas redo chord. The next free number stays the number already next. The next free outside number stays the number already next. The open batch already in the working tree stays open. The paste gap already seated stays. The wire stanza stays empty. The doors do not gain, lose, or reorder a line.

**Truth.** The Rust canvas prompt keeps its own undo seat. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo puts the caret where the edit began. Redo restores the caret after the edit. A paste is one span. Import is one whole-text step. The limit is 100. The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The widget timer undoer is cleared. Java stays. Rust moves.

**Reason.** Java EditMemory wraps the Swing undo stack and seats the caret where the edit began. Rust kept a full before-string and did not seat that caret. egui 0.37 keeps a timer Undoer that snapshots the whole string, and this plate clears it so the chords have one owner.

**Avoidances.** Do not treat this page as stamped. Do not edit Java EditMemory. Do not move Java or grid.rs. Do not use the widget timer undoer. Do not let Ctrl-Y become the canvas redo chord. Do not add a public type, a public method, a JUnit class, a library, or a Cargo.toml edit. Do not raise the limit above 100. Do not close the open batch. Do not add, remove, or reorder a door line. Do not open the window.

**semantic-key:** PK-7247

# Each canvas user keeps a deck

Each canvas user keeps a running turn while another SSH session works. This page does not move that point. The formal plan is the page that names the build.


**semantic-key:** PK-4623 PK-4624 PK-4625 PK-4626 PK-4627 PK-4628 PK-4629 PK-4630 PK-4631 PK-4632

## The cause

One canvas deck is on the glass. Switching the user reloaded that deck and marked a stored running cell interrupted. A parked SSH session held its scope until the pane took focus. The seat keeps one deck per user on the window thread. No new OS thread is added.

**semantic-key:** PK-7248

## The seat

Each canvas user keeps a deck. A running turn stays on that deck when another user takes the glass. A parked SSH session keeps feeding the user who owns it. The glass hears that user only when that user is on screen. The same user reconnect does not reload the deck. Replacing this pane's socket stops a run that lived on it. A run on another pane stays. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. No new OS thread is added. The private types are ParkedDeck, ScopeHome, DeckRefs, FeedOut, and LoadedCanvas. The private functions are scope_home, feed_parts, deck_key, note_replaced_run, wire_owned, wire_pane, lift_showing, plant_showing, remember_deck, plant_user, load_user_canvas, adopt_loaded, seat_canvas_user, card_snap, and deliver_scope. Java stays. Rust moves.

**semantic-key:** PK-7249

## What stays

No new public type is added. No new public method is added. No new JUnit class is added. Cargo.toml is not edited. No new library is added. Java stays. grid.rs stays. The window stays closed. The thinking file stays one file. The same user reconnect does not reload the deck. Pull's new turn still starts Excluded. New cards still start Included. The next free number stays the number already next. The next free outside number stays the number already next. The open batch already in the working tree stays open. The paste gap already seated stays. The wire stanza stays empty. The doors do not gain, lose, or reorder a line.

**Truth.** Each canvas user keeps a deck. A running turn stays on that deck when another user takes the glass. A parked SSH session keeps feeding the user who owns it. The glass hears that user only when that user is on screen. Replacing this pane's socket stops only the run that lived on it. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. No new OS thread is added. The private types are ParkedDeck, ScopeHome, DeckRefs, FeedOut, and LoadedCanvas. Java stays. Rust moves.

**Reason.** One canvas deck sat on the glass. Switching the user reloaded that deck and marked a stored running cell interrupted. A parked SSH session held its scope until the pane took focus. The seat keeps one deck per user on the window thread.

**Avoidances.** Do not treat this page as stamped. Do not move Java or grid.rs. Do not add an OS thread, a public type, a public method, a JUnit class, a library, or a Cargo.toml edit. Do not reload the deck on the same user reconnect. Do not stop a run on another pane when this pane's socket is replaced. Do not split the thinking file. Do not start Pull's new turn as anything other than Excluded. Do not start a new card as anything other than Included. Do not close the open batch. Do not add, remove, or reorder a door line. Do not open the window.

**semantic-key:** PK-7250

# One press seats one cell

One press on I, E, P, or O seats that cell only. This page does not move that point. The formal plan is the page that names the build.


**semantic-key:** PK-4633 PK-4634

## The cause

The four letters were focusable selectable buttons on an unstable id. A click could land on another cell when the deck grew. Enter in the pin field reports lost focus for two frames, so a later Enter committed the pin and sent every miss to Excluded, including a new turn at the bottom. The I press then restored only the cell under the pointer.

**semantic-key:** PK-7251

## The seat

A press on I, E, P, or O seats that cell only. The other cells stay, including a new turn at the bottom. The letter does not take focus. Space and Enter do not seat it. A pin commit does not ride that press. Enter in the pin field still commits on the frame the field loses focus. The next frame of that loss does not commit. Pull still starts a new turn Excluded. A new card still starts Included. The private functions are seat_mark, seat_mark_button, mark_pressed, pin_enter_due, and pin_commit_after_mark. The field is pin_enter_seen. Java stays. Rust moves.

**semantic-key:** PK-7252

## What stays

No new public type is added. No new public method is added. No new JUnit class is added. Cargo.toml is not edited. No new library is added. No new module file is added. Java stays. grid.rs stays. The window stays closed. Pull still starts a new turn Excluded. A new card still starts Included. OK and Enter still commit the pin. The next frame of a pin-field loss does not commit. The next free number stays the number already next. The next free outside number stays the number already next. The open batch already in the working tree stays open. The paste gap already seated stays. The wire stanza stays empty. The doors do not gain, lose, or reorder a line.

**Truth.** A press on I, E, P, or O seats that cell only. The other cells stay, including a new turn at the bottom. The letter does not take focus. Space and Enter do not seat it. A pin commit does not ride that press. Enter in the pin field commits on the frame the field loses focus. The next frame of that loss does not commit. The field is pin_enter_seen. The private functions are seat_mark, seat_mark_button, mark_pressed, pin_enter_due, and pin_commit_after_mark. Java stays. Rust moves.

**Reason.** The four letters were focusable selectable buttons on an unstable id. A click could land on another cell when the deck grew. Enter in the pin field reports lost focus for two frames, so a later Enter committed the pin and sent every miss to Excluded, including a new turn at the bottom.

**Avoidances.** Do not treat this page as stamped. Do not move Java or grid.rs. Do not add a module file, a public type, a public method, a JUnit class, a library, or a Cargo.toml edit. Do not let the letter take focus. Do not let Space or Enter seat the letter. Do not let a pin commit ride that press. Do not commit on the next frame of a pin-field focus loss. Do not start Pull's new turn as anything other than Excluded. Do not start a new card as anything other than Included. Do not close the open batch. Do not add, remove, or reorder a door line. Do not open the window.

**semantic-key:** PK-7253

# Each user's run stays with that user

Each user's run and thinking stay with that user while another user runs. This page does not move that point. The formal plan is the page that names the build.


**semantic-key:** PK-4635 PK-4636 PK-4637 PK-4638 PK-4639 PK-4640 PK-4641 PK-4642

## What stays

The parked deck stays. The same user does not reload. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. Every member frame still leaves together. One press still seats one cell. The model header still starts each thinking stream. No new public type is added. No new public method is added. No new JUnit class is added. Cargo.toml is not edited. No new library is added. No new module file is added. No new OS thread is added. Java stays. grid.rs stays. The window stays closed. The next free number stays the number already next. The next free outside number stays the number already next. The open batch already in the working tree stays open. The paste gap already seated stays. The wire stanza stays empty. The doors do not gain, lose, or reorder a line.

**Truth.** Each user's run and thinking stay with that user. A second user can run while the first is still running. The focused session does not paint another user's thinking on the glass. The glass user's execute leaves on that user's session. The thinking scroll follows the user on the glass. The files are chrome.rs and session.rs. Java stays. Rust moves.

**Reason.** The focused session wrote every canvas stream onto the glass. Run sent only on that socket. A second user's execute never left, and the first user's thinking stayed on the glass.

**Avoidances.** Do not treat this page as stamped. Do not move Java or grid.rs. Do not add a public type, a public method, a JUnit class, a library, a Cargo.toml edit, a module file, or an OS thread. Do not reload the same user. Do not paint another user's thinking on the focused glass. Do not send the glass user's execute on another user's session. Do not move SSH sessions off their own sockets. Do not close the open batch. Do not add, remove, or reorder a door line. Do not open the window.

**semantic-key:** PK-7254

# The main menu words wear the menu green

The main menu words wear the Java menu green.

**semantic-key:** PK-7255

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke for the Help plate or for this plate. The branch, the action, the stage, and the phase clock are not retargeted. The crate commit is the proof. It is not the head.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. The private function is `paint_menu_ink`.

Check the Rust workspace offline. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called. Java does not move. Tests are not run on this plate.

One commit of the named Rust file records that the change is in the crate.

**semantic-key:** PK-8072

## Goal

The main menu words wear the Java menu green.

**semantic-key:** PK-7256

## What stays

File stays. Tools stays. Token Calc stays before Help. Show Thinking stays under Tools. Help... and About Aetheris... stay under Help. The order stays File, Tools, Token Calc, and Help. The folded row stays. The open divider stays. The words stay SSH, Aetheris, and Canvas. The Aetheris tab stays disabled until the mux answers. The grip stays. The thinking text stays clear. The button hover emerald stays `#00FF66`. The tooltip wait stays. Context menus stay. Token Calc still opens the token window. No yaml edit. No `Cargo.toml` edit. Java stays.

**semantic-key:** PK-7257

## Edits

The seat is `paint_menu` in `chrome.rs`. The menu bar calls `menu_style`, then `paint_menu_ink`. That function sets the inactive, hovered, active, and open word color to `#00ff41`. That is the Swing menu foreground. File, Tools, and Help call `paint_menu_ink` on the open menu, so the lines under them wear the same green. Token Calc sits on the bar, so it takes the bar color. The word stays that green while the pointer is on it. Context menus stay.

**semantic-key:** PK-7258

## Proof

Check the Rust workspace offline. The window stays closed. The live POST is not made. A pool turn is not sent. A live SSH connect is not made. JUnit is not used. Tests are not run on this plate. Java stays. `Session::start` is not called. `chrome::run` is not called.

**semantic-key:** PK-7259

## Land

Rust is named. Java stays.

File, Tools, Token Calc, and Help stay. The lines under those menus wear the same green. Context menus stay.

No side move on this plate.

The green is how the main menu words match the Java menu bar. Token Calc still opens the token window.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `paint_menu_ink` sets the inactive, hovered, active, and open main-menu word to `#00ff41`. The lines under File, Tools, and Help wear that same green. Token Calc on the bar takes the bar color.

**Reason.** The Rust main menu words did not wear the Java menu green, which is the Swing menu foreground `#00ff41`.

**Avoidances.** Do not recolor context menus. Do not change the hover emerald `#00FF66`. Do not edit yaml or `Cargo.toml`. Do not move Java. Do not stage the card-round hunks. Do not add a public type or a public method. `paint_menu_ink` stays private. Do not file a smoke for the Help plate or for this plate. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-7260

# The input pane follows the control-bar size

The input pane font follows the control-bar font size.

**semantic-key:** PK-7261

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke for the four-mark plate or for this plate. Do not file a feature smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted. The crate commit is the proof. It is not the head.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. The private function is `pane_font`.

Check the Rust workspace offline. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called. Java does not move. Tests are not run on this plate.

One commit of the named Rust file records that the change is in the crate. The crate commit is the proof.

**semantic-key:** PK-8073

## Goal

The input pane font follows the control-bar font size.

**semantic-key:** PK-7262

## What stays

The plus and minus on the control bar stay. The replies stay on the size they already follow. The desk line stays. The desk draft stays. Dialogs stay. The letters stay I, E, P, and O. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. The hover emerald stays `#00FF66`. Tabs stay. Menu titles stay. The line floor stays 16. File, Tools, Token Calc, and Help stay. No yaml edit. No `Cargo.toml` edit. Java stays.

**semantic-key:** PK-7263

## Edits

The seat is `paint_prompt_window` in `chrome.rs`. The column already sets the control-bar size on the canvas. The input editor was pinned to the global mono face, so that size never reached the glyphs, and the locked window measured the same global face. `pane_font` returns the control-bar size when the column set it, and the mono face when a caller has no control-bar size. The input editor takes that face. `pane_line` measures the same face, so the row window grows with the control-bar size. The floor stays 16. The private function is `pane_font`.

**semantic-key:** PK-7264

## Land

Rust is named. Java stays.

The replies stay. The desk line stays. The desk draft stays. Dialogs stay.

No side move on this plate.

The cap is the input pane taking the control-bar font size, and the row window growing with that size.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `pane_font` returns the control-bar size when the column set it, and the mono face when a caller has no control-bar size. The input editor and `pane_line` use that face. The line floor stays 16.

**Reason.** The input editor was pinned to the global mono face, so the control-bar size never reached the glyphs, and the locked window measured that same global face.

**Avoidances.** Do not move the replies, the desk line, the desk draft, or the dialogs onto this face. Do not change the line floor away from 16. Do not move the plus and minus. Do not change the hover emerald `#00FF66`. Do not move Java. Do not edit yaml or `Cargo.toml`. Do not stage the card-round hunks. Do not file a feature smoke. This row is not a feature. `pane_font` stays private. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-8226

# A missing font key reads fourteen

The default font size is 14.

**semantic-key:** PK-7265

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke for the input-pane plate or for this plate. Do not file a feature smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted. The crate commit is the proof. It is not the head.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. The private constant is `FONT_DEFAULT`.

Check the Rust workspace offline. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called. Java does not move. Tests are not run on this plate.

One commit of the named Rust files records that the change is in the crate. The crate commit is the proof.

**semantic-key:** PK-8074

## Goal

The default font size is 14.

**semantic-key:** PK-7266

## What stays

A stored size stays as written. The range stays 8 through 36. The Java default stays 8. The plus and minus stay. The letters stay I, E, P, and O. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. The hover emerald stays `#00FF66`. File, Tools, Token Calc, and Help stay. No yaml edit. No `Cargo.toml` edit. Java stays.

**semantic-key:** PK-7267

## Edits

The seat is `FONT_DEFAULT` in `door.rs`. A missing key was 8. An unreadable key was 8. A memory door was 8. Those three now read 14. A stored 8 stays 8. The clamp stays 8 through 36. The comment no longer claims the Swing default. The existing shell-font test and the memory-door assert in `chrome.rs` name 14 for a missing key. The private constant is `FONT_DEFAULT`.

**semantic-key:** PK-7268

## Land

Rust is named. Java stays.

A stored size stays. The range stays.

No side move on this plate.

The cap is the missing font key reading 14.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** A missing key, an unreadable key, and a memory door read `FONT_DEFAULT` 14. A stored 8 stays 8. The clamp stays 8 through 36. The Java default stays 8.

**Reason.** Those three empty cases were 8, and the comment claimed the Swing default. The Rust default is 14, and a stored size is not rewritten.

**Avoidances.** Do not rewrite a stored size. Do not change the clamp away from 8 through 36. Do not change the Java default away from 8. Do not claim the Swing default in the comment. Do not move Java. Do not edit yaml or `Cargo.toml`. Do not stage the card-round hunks. Do not file a feature smoke. This row is not a feature. `FONT_DEFAULT` stays private. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-8227

# The seven buttons wrap under the host row

The button bar sits on its own row under the host control elements.

**semantic-key:** PK-7269

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke for the flow plate or for this plate. Do not file a feature smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

Check the crate offline. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called. Tests are not run. Java does not move.

One commit of the named Rust hunks records that the change is in the crate.

**semantic-key:** PK-8075

## Goal

The button bar sits on its own row under the host control elements.

**semantic-key:** PK-7270

## What stays

The host row keeps the mark, the fields, Remember, Font size, and the link status. The seven buttons stay Connect, Disconnect, Reconnect, Clear Terminal, Send file, New Term, and Close Pane. The fold tip stays. File, Tools, Token Calc, and Help stay. No yaml edit. No `Cargo.toml` edit. Java stays.

**semantic-key:** PK-7271

## Edits

The first wrapped row ends at the link status. The second wrapped row holds the seven buttons. The show tip names the button row under the host row. No new function.

**semantic-key:** PK-7272

## Proof

Check the crate offline. The window stays closed. The live POST is not made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called. Tests are not run. Java stays.

**semantic-key:** PK-7273

## Land

Rust is named. Java stays.

No side move on this plate.

The cap is the button bar sitting on its own row under the host control elements.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The first wrapped row ends at the link status. The second wrapped row holds Connect, Disconnect, Reconnect, Clear Terminal, Send file, New Term, and Close Pane. No new function.

**Reason.** The seven buttons were not yet on their own row under the host control elements.

**Avoidances.** Do not move the mark, the fields, Remember, Font size, or the link status off the host row. Do not drop the fold tip. Do not add a function. Do not move Java. Do not edit yaml or `Cargo.toml`. Do not stage the card-round hunks. Do not file a feature smoke. This row is not a feature. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-8228

# Canvas turn text is bold

The turn text on a canvas cell is bold.

**semantic-key:** PK-7274

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke for the About-image plate or for this plate. Do not file a feature smoke. This row is not a feature. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

Check the crate offline. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called. Tests are not run. Java does not move.

One commit of the named Rust hunk records that the change is in the crate. The crate commit is the proof and is not the head.

**semantic-key:** PK-8076

## Goal

The turn text on a canvas cell is bold.

**semantic-key:** PK-7275

## What stays

The merge label stays. The view window title stays. The mark letters stay. File, Tools, Token Calc, and Help stay. No yaml edit. No `Cargo.toml` edit. Java stays.

**semantic-key:** PK-7276

## Edits

The canvas cell turn label is strong, including the ghost color path. No new function.

**semantic-key:** PK-7277

## Land

Rust is named. Java stays.

No side move on this plate.

The cap is the turn text on a canvas cell in bold.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The canvas cell turn label is strong, including the ghost color path. No new function.

**Reason.** The turn text on a canvas cell was not bold, including on the ghost color path.

**Avoidances.** Do not change the merge label, the view window title, or the mark letters. Do not add a function. Do not move Java. Do not edit yaml or `Cargo.toml`. Do not stage the card-round hunks. Do not file a feature smoke. This row is not a feature. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-8229

# The thinking body stays the lighter sapphire

The thinking body stays the lighter sapphire in Java and in Rust. A later plate does not retint a locked color or resize a locked control unless the sentence names that element.

**semantic-key:** PK-7278

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. The branch, the action, the stage, and the phase clock are not retargeted. The crate commit is not the head.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. `THINK_BODY` is private.

The offline crate check passed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called. Tests are not run. No Java on this plate.

No Java commit. One partial commit of `chrome.rs` records that the change is in the crate. The crate commit is not the head.

**semantic-key:** PK-8077

## Goal

The thinking body stays the lighter sapphire in Java and in Rust. A later plate does not retint a locked color or resize a locked control unless the sentence names that element.

**semantic-key:** PK-7279

## What stays

Java `VT_THINK_BODY` already returns bytes 115, 174, 255 for both strips. Card replies stay yellow. Button sizes stay. The menus stay. No `Cargo.toml` edit.

**semantic-key:** PK-7280

## Edits

Rust paints the thought body with `THINK_BODY` at bytes 115, 174, 255. The yellow body name is gone. The module line and the block comment name the lighter sapphire. The existing test assert matches those bytes. The card yellow stays on `OUTPUT_YELLOW`.

**semantic-key:** PK-7281

## Proof

The offline crate check passed. The window stays closed. The live POST is not made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called. JUnit is not used. Tests are not run.

**semantic-key:** PK-7282

## Land

Java already wears the color, so the Java file stays. Rust is named.

Card replies stay yellow.

No side move on this plate.

The cap is the thinking body at bytes 115, 174, 255, locked on this row.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** Rust paints the thought body with private `THINK_BODY` at bytes 115, 174, 255. Java `VT_THINK_BODY` already returns those bytes for both strips. Card yellow stays on `OUTPUT_YELLOW`. A later plate does not retint a locked color or resize a locked control unless the sentence names that element.

**Reason.** The Rust thought body was not yet the lighter sapphire Java already paints, and a later plate must not retint that lock or resize a locked control unless the sentence names the element.

**Avoidances.** Do not retint card replies away from yellow. Do not change button sizes. Do not change the menus. Do not edit the Java file. Do not make `THINK_BODY` public. Do not add a color key. Do not edit `Cargo.toml`. Do not stage the card-round hunks. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-7283

# The folded thinking bar clears the button bar

When the thinking pane is closed in the Aetheris canvas, and the control line for the thinking pane is painted, with the C and Close on the same line as the drag bar, the C button is being clipped by the BB1 button bar. Raise the collapsed thinking bar control line up on the y axis of the canvas by enough pixels for the C button to not be clipped by at least 2 px.

**semantic-key:** PK-7284

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked. The shop clock fell back to Mountain Standard Time. This booking is after the resume-card booking.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. The branch, the action, the stage, and the phase clock are not retargeted. The crate commit is not the head.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

The offline crate check passed. The window stays closed. `Session::start` is not called. `chrome::run` is not called. Tests are not run. No Java.

One partial commit of `chrome.rs` records that the change is in the crate. The crate commit is not the head.

**semantic-key:** PK-8078

## Goal

When the thinking pane is closed in the Aetheris canvas, and the control line for the thinking pane is painted, with the C and Close on the same line as the drag bar, the C button is being clipped by the BB1 button bar. Raise the collapsed thinking bar control line up on the y axis of the canvas by enough pixels for the C button to not be clipped by at least 2 px.

**semantic-key:** PK-7285

## What stays

The open strip stays on the buttons. The VT path stays. C stays the color it already has. Button sizes stay. No `Cargo.toml` edit.

**semantic-key:** PK-7286

## Edits

The folded canvas control line lifts 4 px. The thinking band grows upward by that lift. The row height stays the shown line. C clears the BB1 button bar by at least 2 px. The assert sits on the folded canvas row. The open strip stays. The VT path stays.

**semantic-key:** PK-7287

## Proof

The offline crate check passed. The window stays closed. `Session::start` is not called. `chrome::run` is not called. Tests are not run. The folded-line test module does not compile. Names it already needed are missing from that module. The new assert was not executed. The proof for this land is that offline crate check. No Java.

**semantic-key:** PK-7288

## Land

Rust is named. Java stays. Rust moves.

The open strip stays. The VT path stays.

No side move on this plate. The 4 px lift is the named raise.

The cap is at least 2 px of clearance between C and the BB1 button bar.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The folded canvas control line lifts 4 px. The thinking band grows upward by that lift. The row height stays the shown line. C clears the BB1 button bar by at least 2 px. The open strip stays. The VT path stays.

**Reason.** The folded C sat on the same line as the drag bar and was clipped by the BB1 button bar.

**Avoidances.** Do not move the open strip off the buttons. Do not change the VT path. Do not recolor C. Do not change button sizes. Do not treat the unexecuted assert as the proof. Do not move Java. Do not edit `Cargo.toml`. Do not stage the card-round hunks. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window. Do not put lambdas in our types. Do not bring in Lombok or JUnit.

**semantic-key:** PK-7289

# The VT100 page keys fire

Page Up, Page Down, Line Up, Line Down, and Clear Buffer are lit on the VT100 context menu, a page holds, a parked pane applies the motion, and the Java chords fire those lines.

**semantic-key:** PK-7290

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Java stays. Rust moves. The branch, the action, the stage, and the phase clock are not retargeted.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No `Cargo.toml` edit.

The offline check of the `aetheris-client` crate passed. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

One commit of the `chrome.rs` plate hunks records that the change is in the tree. The ledger note names the crate commit. The crate commit is not the head.

**semantic-key:** PK-8079

## Goal

Page Up, Page Down, Line Up, Line Down, and Clear Buffer are lit on the VT100 context menu, a page holds, a parked pane applies the motion, and the Java chords fire those lines.

**semantic-key:** PK-7291

## What stays

The thirteen labels stay. Copy stays dark without a highlight. Paste stays dark when the clipboard has no string. Open as URL stays dark unless the highlight is a URL. Clear on the alternate screen still returns false from `clear_scrollback_keep_line`. Bare Page writes no host byte. `keys.rs` stays. Java stays. The SSH mux stays. Channel A stays. Command-line history stays. Ctrl+A stays `0x01`. The paste chords stay on the editor path. Six open bookings stay open. The Included-ring plate stays locked.

**semantic-key:** PK-7292

## Edits

`console_menu_gates` keeps the five buffer lines lit. `apply_console_label` writes the page and the line on the alternate screen and clears the tail flag. `paint_grid` sticks when the view is on the tail. A parked pane takes its waiting lines and sticks the same way. `TermStroke::Motion` carries Clear Buffer, Page Up, Page Down, Line Up, and Line Down. `map_key` binds Ctrl+L, Shift+Page Up, Shift+Page Down, Ctrl+Up, and Ctrl+Down. `apply_strokes` runs that label through `apply_console_label` and writes no byte. `console_menu_tip` puts the chord on the hover. The label strings stay.

**semantic-key:** PK-7293

## Proof

The offline check of the `aetheris-client` crate passed. The window stays closed. The live POST is not made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

**semantic-key:** PK-7294

## Land

Rust is named. Rust moves. Java stays. The Java menu is the mirror already in the tree. This plate does not retarget it.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The five buffer lines stay lit. `TermStroke::Motion` carries Clear Buffer, Page Up, Page Down, Line Up, and Line Down. `map_key` binds Ctrl+L, Shift+Page Up, Shift+Page Down, Ctrl+Up, and Ctrl+Down. `apply_strokes` writes no byte. Java stays.

**Reason.** The VT100 context menu advertised paging and cursor lines that did not yet fire, and Rust had to mirror the Java menu.

**Avoidances.** Do not drop a label from the thirteen. Do not light Copy without a highlight, Paste on an empty clipboard, or Open as URL unless the highlight is a URL. Do not make `clear_scrollback_keep_line` return true on the alternate screen. Do not let bare Page write a host byte. Do not edit `keys.rs`. Do not change Ctrl+A away from `0x01`. Do not move the paste chords off the editor path. Do not move Java. Do not edit `Cargo.toml`. Do not retarget the SSH mux or Channel A. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-7295

# The user pull-down stays in the lower right

The connected-user pull-down stays in the lower right of the Aetheris footer. The master buttons take the width that remains.

**semantic-key:** PK-7296

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. The branch, the action, the stage, and the phase clock are not retargeted. Do not spend the land marker on the stamp.

The edit below is the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No `Cargo.toml` edit.

Check the `aetheris-client` crate offline from the Rust workspace. The check does not include tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

The product commit follows and records that the change is in the tree. The ledger note names the crate commit.

**semantic-key:** PK-8080

## Goal

The connected-user pull-down stays in the lower right of the Aetheris footer. The master buttons take the width that remains.

**semantic-key:** PK-7297

## What stays

The combo width stays 160. The ceiling stays 180. The arrow stays emerald. The spinner still sits just west of the pull-down while a card runs. The SSH divider stays. The focused pane stroke stays. The composer-beat plate through the SSH-divider plate stay locked. Six open bookings stay open.

**semantic-key:** PK-7298

## Edits

Rust. The private function `east_reserve` holds the east width before the master buttons paint. That width is the combo's 160 plus the spinner when a card runs. The buttons paint in the width that remains. `paint_east_capsule` still paints the pull-down on the outer east and the spinner just west of it.

Java stays.

**semantic-key:** PK-7299

## Proof

Check the `aetheris-client` crate offline. The window stays closed. The live POST is not made by the proof. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called.

**semantic-key:** PK-7300

## Land

Rust is named. Java stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `east_reserve` holds the combo's 160, plus the spinner while a card runs, before the master buttons paint. `paint_east_capsule` keeps the pull-down on the outer east. The ceiling stays 180. The arrow stays emerald. Java stays.

**Reason.** The connected-user pull-down has to stay in the lower right the way the Java canvas holds that column, and choosing a name still switches the user.

**Avoidances.** Do not change the combo width away from 160 or the ceiling away from 180. Do not move the spinner off the west side of the pull-down while a card runs. Do not change the emerald arrow. Do not change the SSH divider or the focused pane stroke. Do not move Java. Do not spend the land marker on the stamp. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-8230

# The folded thinking line clears the button bar

The folded thinking line clears BB1.


**semantic-key:** PK-4643

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. The branch, the action, the stage, and the phase clock are not retargeted. Do not spend the land marker on the stamp.

The edit below is the build. Types we write do not use lambdas. No new G-key. No new public type. No new public method. No new package. No new color key. No new library. No `Cargo.toml` edit.

Check the `aetheris-client` crate offline from the Rust workspace. The check does not include tests. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. `Session::start` is not called. `chrome::run` is not called. Java stays.

The product commit follows and records that the change is in the tree. The ledger note names the crate commit.

**semantic-key:** PK-8081

## Goal

The folded thinking line clears BB1.

**semantic-key:** PK-7301

## What stays

The open strip stays. The VT100 strip stays. The composer-beat plate through the status-bar plate stay locked. Six open bookings stay open.

**semantic-key:** PK-7302

## Edits

The private function `folded_control_height` sizes the folded canvas row to Close. The band adds that height plus the lift. The bottoms of the fold mark, C, and Close stay at least 2 pixels above the outer top of BB1.

**semantic-key:** PK-7303

## Land

Rust is named. Java stays.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** `folded_control_height` sizes the folded canvas row to Close. The bottoms of the fold mark, C, and Close stay at least 2 pixels above the outer top of BB1. Java stays.

**Reason.** The folded thinking line was still meeting BB1.

**Avoidances.** Do not move the open strip or the VT100 strip. Do not leave less than 2 pixels above BB1. Do not move Java. Do not spend the land marker on the stamp. Do not file a smoke. This row is not a feature. Do not spend a new garage law. Do not add an outside-law row. Do not call `Session::start` or `chrome::run`. Do not open the window, send a live POST, send a pool turn, or open a live SSH connect. Do not put lambdas in our types.

**semantic-key:** PK-8231

# The merge fills the canvas column

The merge cell matches the canvas width a normal cell already uses.

**semantic-key:** PK-7304

## Approval

The approval files the work and names the build. It does not move the stage. It does not retarget the working set or the current action.

The stage stays `1` / `BUILT`. The working pointer stays the stage map. The action stays the product boot. The phase clock is not moved. Where this page is thinner, the locked requirement wins.

The full cycle is named, and the build is named. The work is booked.

The sketch stays locked in place. A second serial is not opened. Earlier rows stay locked and are not rewritten. The branch, the action, the stage, and the phase clock are not retargeted.

The edits below are the build. Types we write do not use lambdas. Lombok stays out. JUnit stays out. No new G-key. No new public type. No new package. No new vault column. No new channel. No new AMWP type.

The window stays closed. `Session::start` is not called. `chrome::run` is not called. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Java does not move. The Java build is not run on this plate. Tests stay off the window.

One commit of the named Rust file records that the change is in the tree. The proof on the papers is the product commit. The crate was named, so the crate moves in this filing. Java stays.

**semantic-key:** PK-8082

## Goal

The merge cell matches the canvas width a normal cell already uses.

**semantic-key:** PK-7305

## What stays

Add Cell stays on the merge bar. The row height already locked stays. A member stays the button bar plus ten pixels. The gather order stays. A folded card stays a brief line. Earlier rows stay locked. Java stays.

**semantic-key:** PK-7306

## Edits

The Rust seat is the merge card in `chrome.rs`. `merge_column_width` names the column. A finite width of at least 1 is the merge width. Any other width leaves the card as it is. At the start of a merge card that is not a member, the card takes that width, so the stroke matches the column a sequential cell already fills. A member does not take it.

An expanded merge reply fills that same width and wraps. An unchecked merge reply already asks for the row width. The open-reply call that is not a merge keeps the width it has.

**semantic-key:** PK-7307

## Proof

The window stays closed. `Session::start` is not called. `chrome::run` is not called. The live POST is not made. A pool turn is not sent. A live SSH connect is not made. JUnit is not used. The Java build is not run on this plate.

**semantic-key:** PK-7308

## Land

The named crate widens the merge to the canvas column. A member stays the bar plus ten pixels. Java stays.

Add Cell stays. The row height already locked stays. The gather order stays. A folded card stays a brief line.

No side move on this plate.

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** A merge card that is not a member takes a finite canvas-column width of at least 1, and its expanded reply fills that width and wraps. A member stays the button bar plus ten pixels.

**Reason.** The merge has no prompt, so its frame was the button bar. It rendered shorter than a normal cell and snapped to what was visible instead of the canvas column.

**Avoidances.** Do not give a member the column width. Do not change Add Cell, the locked row height, the gather order, or the brief line of a folded card. Do not let a non-merge open-reply call take the merge width. Do not treat a non-finite width, or a width under 1, as the merge width. Do not open a second serial. Do not move Java. Do not stage the card-round hunks. Do not add a public type, a package, a vault column, a channel, or an AMWP type. Do not put lambdas in our types. Do not bring in Lombok or JUnit. Do not open the window, call `Session::start` or `chrome::run`, send a live POST, send a pool turn, or open a live SSH connect.

**semantic-key:** PK-7309

# The menu words take the Java green

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-7310

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Rust is named. Java stays. No extra cut is made.

The main menu words wear the Java menu green.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library. The private function is `paint_menu_ink`.

**semantic-key:** PK-7311

## Slice

The main menu words wear the Java menu green. Rust moves. Java stays.

**semantic-key:** PK-7312

## What stays

Earlier rows stay locked. File, Tools, Token Calc, and Help stay. The lines under those menus wear the same green. Context menus stay. The button hover emerald stays `#00FF66`. No yaml edit. Java stays.

**semantic-key:** PK-7313

## Depends

`chrome.rs`.

**Truth.** The main menu words wear the Java menu green. The lines under File, Tools, Token Calc, and Help wear that same green. Rust moves. Java stays.

**Reason.** The Rust main menu words did not match the Java menu green.

**Avoidances.** Do not recolor context menus. Do not change the hover emerald `#00FF66`. Do not move Java. Do not edit yaml. Do not stage the card-round hunks. Do not add a public type or a public method. `paint_menu_ink` stays private. Do not add a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-7314

# The input pane takes the control-bar size

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-7315

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Rust is named. Java stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The input pane font follows the control-bar font size.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library. The private function is `pane_font`.

**semantic-key:** PK-7316

## Slice

The input pane font follows the control-bar font size. Rust moves. Java stays.

**semantic-key:** PK-7317

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The replies stay on the size they already follow. The desk line stays. The desk draft stays. Dialogs stay. The letters stay I, E, P, and O. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. The hover emerald stays `#00FF66`. The line floor stays 16. No yaml edit. Java stays.

**semantic-key:** PK-7318

## Depends

`chrome.rs`.

**Truth.** The input pane font follows the control-bar font size. Rust moves. Java stays.

**Reason.** The input pane was not following the control-bar font size.

**Avoidances.** Do not move the replies, the desk line, the desk draft, or the dialogs. Do not change the line floor away from 16. Do not change the hover emerald `#00FF66`. Do not move Java. Do not treat this row as a feature. Do not edit yaml. Do not stage the card-round hunks. `pane_font` stays private. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-8235

# The default size is fourteen

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-7319

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Rust is named. Java stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The default font size is 14.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library. The private constant is `FONT_DEFAULT`.

**semantic-key:** PK-8238

## Slice

The default font size is 14. Rust moves. Java stays.

**semantic-key:** PK-7320

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. A stored size stays as written. The range stays 8 through 36. The Java default stays 8. The plus and minus stay. No yaml edit. Java stays.

**semantic-key:** PK-7321

## Depends

`door.rs`.

**Truth.** The default font size is 14. A stored size stays as written. The range stays 8 through 36. The Java default stays 8. Rust moves. Java stays.

**Reason.** The empty font key was not yet 14.

**Avoidances.** Do not rewrite a stored size. Do not change the range or the Java default of 8. Do not move the plus and minus. Do not treat this row as a feature. Do not edit yaml. Do not move Java. Do not stage the card-round hunks. `FONT_DEFAULT` stays private. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-7322

# The button row sits under the host row

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-7323

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Rust is named. Java stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The button bar sits on its own row under the host control elements.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8239

## Slice

The button bar sits on its own row under the host control elements. Rust moves. Java stays.

**semantic-key:** PK-7324

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The host row keeps the mark, the fields, Remember, Font size, and the link status. The fold tip stays. No yaml edit. Java stays.

**semantic-key:** PK-7325

## Depends

`chrome.rs`.

**Truth.** The button bar sits on its own row under the host control elements. Rust moves. Java stays.

**Reason.** The buttons were still on the host row instead of a second row under it.

**Avoidances.** Do not move the mark, the fields, Remember, Font size, or the link status. Do not drop the fold tip. Do not move Java. Do not treat this row as a feature. Do not edit yaml. Do not stage the card-round hunks. Do not add a public type, a public method, a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-8236

# Canvas turn text is bold

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-7326

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Rust is named. Java stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The turn text on a canvas cell is bold.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library.

**semantic-key:** PK-8240

## Slice

The turn text on a canvas cell is bold. Rust moves. Java stays.

**semantic-key:** PK-7327

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The merge label stays. The view window title stays. The mark letters stay. No yaml edit. Java stays.

**semantic-key:** PK-7328

## Depends

`chrome.rs`.

**Truth.** The turn text on a canvas cell is bold. Rust moves. Java stays.

**Reason.** The canvas cell turn text was not bold.

**Avoidances.** Do not change the merge label, the view window title, or the mark letters. Do not treat this row as a feature. Do not edit yaml. Do not move Java. Do not stage the card-round hunks. Do not add a public type, a public method, a package, a channel, an AMWP type, a color key, or a library. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-8237

# The thinking body stays the lighter sapphire

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-7329

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java stays. Rust is named. No extra cut is made. The closed feature stays closed. This row is not a feature.

The thinking body stays the lighter sapphire in Java and in Rust. A later plate does not retint a locked color or resize a locked control unless the sentence names that element. Java bytes stay.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. `THINK_BODY` is private. Java bytes stay.

**semantic-key:** PK-7330

## Slice

The thinking body stays the lighter sapphire in Java and in Rust. A later plate does not retint a locked color or resize a locked control unless the sentence names that element. Java stays. Rust moves.

**semantic-key:** PK-7331

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. Card replies stay yellow. Button sizes stay. The menus stay.

**semantic-key:** PK-7332

## Depends

`ThinkStrip.java`. `chrome.rs`. The Java body already wears the lighter sapphire. This plate does not edit the Java file.

**Truth.** The thinking body stays the lighter sapphire in Java and in Rust. `THINK_BODY` is private. Java stays. Rust moves. A later plate does not retint a locked color or resize a locked control unless the sentence names that element.

**Reason.** Java already wore the lighter sapphire, and Rust had to match it without opening a later retint of a locked color.

**Avoidances.** Do not edit the Java file. Do not retint card replies. Do not change button sizes. Do not change the menus. Do not treat this row as a feature. Do not stage the card-round hunks. Do not add a public type, a public method, or a package. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-7333

# The folded thinking bar clears the button bar

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-8232

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Java stays. Rust is named. No extra cut is made. The closed feature stays closed. This row is not a feature.

When the thinking pane is closed in the Aetheris canvas, and the control line for the thinking pane is painted, with the C and Close on the same line as the drag bar, the C button is being clipped by the BB1 button bar. Raise the collapsed thinking bar control line up on the y axis of the canvas by enough pixels for the C button to not be clipped by at least 2 px. The open strip stays. The VT path stays.

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. The open strip stays. The VT path stays.

**semantic-key:** PK-8244

## Slice

When the thinking pane is closed in the Aetheris canvas, and the control line for the thinking pane is painted, with the C and Close on the same line as the drag bar, the C button is being clipped by the BB1 button bar. Raise the collapsed thinking bar control line up on the y axis of the canvas by enough pixels for the C button to not be clipped by at least 2 px. Java stays. Rust moves.

**semantic-key:** PK-7334

## What stays

Earlier rows stay locked. The closed feature stays closed. This row is not a feature. The open strip stays on the buttons. The VT path stays. C stays the color it already has.

**semantic-key:** PK-7335

## Side move

No side move on this plate. The 4 px lift is the named raise.

**semantic-key:** PK-7336

## Depends

`chrome.rs`. The open strip stays. The VT path stays.

**Truth.** The collapsed thinking bar rises enough that C is not clipped by at least 2 px. The named raise is a 4 px lift. The open strip stays. The VT path stays. Java stays. Rust moves.

**Reason.** C on the folded control line was clipped by the BB1 button bar.

**Avoidances.** Do not move the open strip. Do not change the VT path. Do not recolor C. Do not treat this row as a feature. Do not stage the card-round hunks. Do not add a public type, a public method, or a package. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-7337

# The VT100 page keys fire

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-7338

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Rust is named. Java stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The VT100 context menu's paging and cursor lines work, and the Java chords fire those same lines.

**semantic-key:** PK-8241

## Slice

The VT100 context menu's paging and cursor lines work, and the Java chords fire those same lines.

**semantic-key:** PK-7339

## What stays

Java stays. The thirteen menu labels stay. Copy, Paste, and Open as URL keep their gates. Clear on the alternate screen still leaves that grid as it stands. Bare Page is not a host byte. The term crate stays. The SSH mux stays. Channel A stays. Command-line history stays. Six open bookings stay open in the working tree. The Included-ring plate stays locked.

**semantic-key:** PK-7340

## Wire

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The VT100 paging and cursor lines work, and the Java chords fire those same lines. The thirteen labels stay. Java stays. Bare Page is not a host byte.

**Reason.** The right-click menu advertised paging and cursor control that did not yet fire.

**Avoidances.** Do not drop a label. Do not change the Copy, Paste, or Open as URL gates. Do not let bare Page write a host byte. Do not move Java. Do not move the term crate, the SSH mux, or Channel A. Do not treat this row as a feature. Do not stage the card-round hunks. Do not unlock the Included-ring plate.

**semantic-key:** PK-8162

# The user pull-down stays in the lower right

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-8233

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Rust is named. Java stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The user pull-down sits on the east of the Aetheris footer, the way the Java canvas holds that column. Choosing a name switches the user.

**semantic-key:** PK-8242

## Slice

The connected-user pull-down stays in the lower right of the Aetheris footer. The master buttons take the width that remains. A running card still shows the spinner just west of the pull-down.

**semantic-key:** PK-7341

## What stays

The combo width stays 160. The ceiling stays 180. The arrow stays emerald. The SSH divider stays. The focused pane stroke stays. The heatbar count stays. The 5 pixel track stays. The composer-beat plate through the SSH-divider plate stay locked. Six open bookings stay open.

**semantic-key:** PK-7342

## Wire

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The connected-user pull-down stays in the lower right. The master buttons take the width that remains. The combo width stays 160. The ceiling stays 180. The arrow stays emerald. Java stays.

**Reason.** The Rust footer did not yet hold the user pull-down on the east the way the Java canvas does.

**Avoidances.** Do not change the combo width or the ceiling. Do not move the spinner off the west side of a running card. Do not change the SSH divider or the focused pane stroke. Do not move Java. Do not treat this row as a feature.

**semantic-key:** PK-8163

# The folded thinking line clears the button bar

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.


**semantic-key:** PK-4644

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Rust is named. Java stays. No extra cut is made. The closed feature stays closed. This row is not a feature.

The folded thinking line clears BB1.

**semantic-key:** PK-8243

## Slice

The folded canvas thinking line grows to the full Close button. The bottoms of the fold mark, C, and Close sit at least 2 pixels above the outer top of BB1.

**semantic-key:** PK-7343

## What stays

The open strip stays. The VT100 strip stays. The 5 pixel track stays. The heatbar strip stays. The composer-beat plate through the status-bar plate stay locked. Six open bookings stay open.

**semantic-key:** PK-7344

## Wire

No system line is added, removed, or reordered. This land leaves the working set as it stands.

**Truth.** The folded canvas thinking line grows to the full Close button. The fold mark, C, and Close sit at least 2 pixels above the outer top of BB1. The open strip stays. The VT100 strip stays. Java stays.

**Reason.** The folded thinking line was still meeting BB1.

**Avoidances.** Do not move the open strip or the VT100 strip. Do not leave less than 2 pixels of clearance. Do not change the 5 pixel track or the heatbar strip. Do not move Java. Do not treat this row as a feature.

**semantic-key:** PK-8164

# The merge follows the canvas width

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-7345

## Sketch

This is the first sketch serial. It is locked. The formal plan is stamped from this page, and the build is named. The full cycle is named in the same sentence that names the build. Rust is named. Java stays. No extra cut is made.

The merge cell expands horizontally with the width of the canvas the way a normal cell does. It matches the layout of cells on the horizontal aspect.

No Lombok. No lambdas in our types. No new public type. No new package. No new channel. No new AMWP type.

**semantic-key:** PK-8234

## Slice

A normal cell fills the canvas column. The merge has no prompt, so its frame was the button bar. The merge now takes the column width. The stroke follows that width. An expanded merge reply fills that same width and wraps. A member stays the bar plus ten pixels. Rust moves. Java stays.

**semantic-key:** PK-7346

## What stays

Earlier rows stay locked. Add Cell stays on the merge bar. The row height already locked stays. The gather order stays. The members stay on the sideways row. The merge stays under that row. A folded card stays a brief line.

**semantic-key:** PK-7347

## Depends

The merge card in `aetheris-client` `chrome.rs`. The canvas column width. The member width stays the bar plus ten pixels.

**Truth.** The merge takes the canvas column the way a normal cell does, the stroke follows that width, and an expanded reply fills the same width and wraps. A member stays the button bar plus ten pixels.

**Reason.** The merge was rendering shorter than the column and snapping to what was visible, because it had no prompt and its frame was only the button bar.

**Avoidances.** Do not leave the merge on the button-bar width. Do not widen a member past the bar plus ten pixels. Do not move the members off the sideways row or lift the merge off the row beneath them. Do not change Add Cell, the locked row height, the gather order, or the brief line of a folded card. Do not move Java. Do not stage the card-round hunks. Do not add a public type, a package, a channel, or an AMWP type. Do not put lambdas in our types. Do not bring in Lombok.

**semantic-key:** PK-7348

## Look and feel

This client mirrors the Swing app as far as Rust allows, and close enough for jazz means the person recognizes the surface.

Where the layout, the controls, and the interaction can be mirrored, the mirror is filed when a sentence names the surface. Where one cannot be mirrored, the alternative is filed here and waits for review. This client is not a port of the Swing code. The wire stays the mux contract. The surface stayed open until a sentence named it.

Mirrors that have been named:

- The canvas footer matches the Swing place. The master row and the tool row sit at the bottom of the canvas. The cards scroll above them. Run and Stop stay on the card.
- The button bar wears a rounded square border. The cells stack from the top of the scroll. A short deck leaves the empty space above the bar.
- Pull copies the terminal into a new Excluded turn. A highlight wins. Spaces in that highlight stay. Otherwise the last non-empty screen line is copied.
- To terminal writes the focused turn into the terminal session. The reply is sent when the turn has one. Otherwise the prompt is sent. A burner is refused. Confirm comes before the bytes. The tool bar is complete.
- The air-gap and num-ctx words sit on one line above the inspector. P sits on the divider and moves with that bar. The cards start at the top of the canvas.
- P is green while the pin is on and sapphire while the pin is off. The button face moves with the divider.
- The footer sits on the frame under the tab row. The pin row takes height from the cards. While the pin box is on, the inspector lists the working set the edge already paints. The letters stay until OK or Enter.
- The inspector reads the stored marks. The edge stays live. A focused miss keeps the earlier turns.
- The pin view hides a miss. A miss leaves the canvas. The marks stay.
- The expanded view starts checked. Unchecked, the reply stays in a window the same height as the prompt.
- A new turn keeps the box from the prior cell.
- The last reply opens the next turn.
- The spinner sits on the bar.
- The row count sets the open height.
- The cell height locks every open pane.
- The inspector cuts each kept line. The mood line stays at 80.
- A fresh canvas starts unchecked. Clear leaves that box shut.
- Model Config follows the cell.
- Ctrl-C stops a running request in the VT100 console.
- One canvas goes in and comes out.
- The window writes the open canvas.

**Truth.** The person must recognize the Swing surface. Exact pixels are not required except where a named mirror states a count, a color, or a place. The wire does not move with the look.

**Reason.** A pixel-perfect port of the Swing code would freeze Java widgets into Rust. A free redesign would make the two clients different programs.

**Avoidances.** Do not port the Swing source. Do not change the mux to match a paint difference. Do not drop a named mirror's place, color, or count. Do not treat an unnamed surface as already mirrored.

**semantic-key:** PK-7349

## Java behavior match

A Java behavior this client does not yet carry is matched here exactly, and as closely as Rust allows, and the Java body stays on the Java page.

When a land adds, removes, or reorders a system line, the match carries a wire stanza. The stanza names the door, the system lines in send order, and the line in the seat the server replaces. A land that leaves the working set as it stands says so. The filing lists every side move, the requirement it serves, and the sibling. Earlier mood and think rows stay as each land wrote them. A later row names the seated slice when one is in the tree. The crate move for that delta waits until a sentence names it. The slice quotes the stanza. That rule writes no crate.

The window thread paints. The simple thread still holds, unless a row says otherwise.

Mood desk. The Rust mood layer matches the smoked desk. The mood module seats the invitation, the pin, and the debug lines. The glass session in the window stays. Canvas, group, and pipe: empty host seat, then the turns. The pinned turn's user text starts with `mood:`, the line, `shape:`, the opener, and the closer. The mood line is not repeated between the two sentences. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host seat. The mood block is not a system row. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. SSH: the prompt turns are the conversation turns. The pinned user turn's text starts the same way. The shell frame stays `shell_mood` and the line. Switch off sends no splice and leaves the pin. Glass: shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Pool stays the Java porch rules. This client still sends the whole working set and does not grow a pool session. Inspector: while the line is present, `mood:` stays the line, and `shape:` is the opener and the closer on the following lines. Debug Mood paints that same head immediately before the user line. Those lines carry no card number. Painting does not move the pin. One side move. The pin file is written when a frame is handed to a live mux. This row is the crate move for the smoked desk. The window thread paints the glass and the inspector. The mood module holds the invitation, the pin, and the debug lines and does not paint. The glass struct stays the session. This land writes the mood module, the program entry, and the window module.

Debug Mood on the prompt. Debug Mood paints the invitation immediately before the user line the pointer seats. The lines are `mood:`, the line, `shape:`, the opener, and the closer. They carry no card number. The role lines keep their numbers. The inspector still builds oldest first and reverses once. The head is not a separate card. Off leaves the inspector as it is. Painting does not move the pin. The header stays. The send stays the splice already seated. Wire stanza empty. The doors do not gain, lose, or reorder a line. The window thread paints the head on the card. This row writes no crate until a sentence names the catch-up.

Mood insert. The mood insert is the invitation. The head is `mood:`, the line, `shape:`, the opener, and the closer. The line is not repeated between the two sentences. A stored head in the older order is stripped, and the new head is written. Glass stays the shaping line, then the opener, the field, and the closer. The switch does not gate that chat. The mood-head count still counts the opener, the line, and the closer. The inspector header is `mood:`, the line, then `shape:`, the opener, and the closer. Debug Mood paints that head as the card above the pinned turn. Painting the card does not move the pin. Canvas, group, and pipe: empty host seat, then the turns. The pinned turn's user text starts with `mood:`, the line, `shape:`, the opener, and the closer. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host seat. The mood block is not a system row. SSH: the prompt turns are the conversation turns. The pinned user turn's text starts the same way. The shell frame stays `shell_mood` and the line. Switch off sends no splice and leaves the pin. Pool porch rules read from a spliced user head are `mood:`, the line, `shape:`, the opener, and the closer. A leading system triple still reads as the opener, a blank line, the line, a blank line, and the closer. The window thread writes the invitation on the turn. This row writes no crate until a sentence names the catch-up. An earlier wire shape is overridden in part by this head.

Debug Mood triplet. File, Settings opens the window. The checkbox is Debug Mood. On, the inspector paints the opener, the line, and the closer as one card above the turn the pointer seats. Off leaves the inspector as it is. The paint does not move the pin. The send stays the splice. Wire stanza empty. The window thread paints the card. This row writes no crate until a sentence names the catch-up.

Mood triplet on the turn. The mood triplet splices onto the head of the turn being sent. One record. Zero or one turn already in the transcript keeps that head on the first turn. More than one turn, and a newly defined or updated mood, puts that head on the turn being sent. The pin stays until the next define or update. A deleted pin walks back one cell. Clear and a new session put the pin at the head. Marks do not move the pin. The switch off removes the splice and leaves the pin. Glass stays the shaping line, then the opener, the field, and the closer. The switch does not gate that chat. Canvas, group, and pipe: empty host seat, then the turns. The pinned turn's user text starts with the opener, the line, and the closer. The server replaces the empty host seat. The mood triple is not a system row. SSH: the pinned user turn's text starts with the opener, the line, and the closer. The shell frame stays `shell_mood` and the line. Pool porch rules read a leading system triple or the spliced user-turn head. The window thread splices the turn. This row writes no crate until a sentence names the catch-up.

Thinking pane stays. The pane stays until Close. It rises from the top of the footer. The footer buttons stay reachable. Close is the dismiss. A pause leaves the text. `v` still folds the text and `^` still restores it. The text stays yellow. The Rust form is settled from that Swing lesson before the crate moves. The jazz mirror stays. The window thread paints the card. The simple thread still holds. The central contract stays quiet while Rust can carry the behavior. The seated think strip is the plate where the running card wears the line. This row is the match waiting. The Rust slice waits until a sentence names the catch-up.

Thinking log. The checkbox survives a restart and is copied with the model, including the pool-on flag. The north edge sets the height. The log is a continuous scroll. Each block is tagged with the model. A separator sits between blocks. The log persists. Clear wipes the pane and the file. Close hides the pane and keeps the log. The log is a local file, not a vault row and not an embedded-engine row. Styled runs are window text. The central contract records that the file is not a vault write. The Rust slice waits until a sentence names the catch-up.

Mood is optional. With no saved string, a Run carries the host line and the turns. The second button row, after To terminal, carries a separator, a two-position switch, and a Mood button. The button drafts against the selected card on a private terminal. Enter sends the line. The shaping call uses the card's burner bit. A normal pool card passes the same door as Run. The list is the transcript. The screen is not scraped. The shaping line asks for one mood string. The glass marks you and assistant. A reply line that starts with `mood:` fills the field. The last non-empty line wins. Marks, a space before the colon, the words `mood string`, and a fenced mood block still count. Approve stores that field and turns the switch on. An empty field writes nothing. Cancel keeps the string and the switch. A miss keeps them and does not latch the layer offline. The walker puts the saved string at the head while the switch is on. A canvas body that does not fit drops that line for the send. The string is session memory on this row. The window thread paints the private terminal. The local connector stays off the terminal channel. The string is not a vault row and not a cell text column. This row writes no crate until a sentence names the catch-up.

Mood format. A reply with no mood line is one try. The dialog posts a you line that asks for one final `mood:` line and sends again. The third miss paints the yellow slide and leaves the dialog open. The field and the saved string stay. The dialog footer shows the thinking spinner. The clock counts from that send. A mood line fills the field and clears the count. The clock is not the canvas run. This row writes no crate until a sentence names the catch-up.

Spinner and switch. The glyph keeps a fixed box. The word thinking and the clock stay put while the glyph turns. The mood switch round button is green while the switch is on and red while it is off. Left stays off. Right stays on. The glyph box is fixed. This row writes no crate until a sentence names the catch-up.

Mood shape. The shape line follows the mood string on a canvas send. The words tell the model to activate the mood above and use it as the shape of its responses. The line is not a cell. The mood glass does not carry it. A body that does not fit drops the mood string and this line together. This row writes no crate until a sentence names the catch-up.

Closed thinking pane. Close writes closed true on the think file. The next open loads the log and leaves the pane shut. A later thought opens the pane and clears the mark. The fold is not written. The mark is a file key, not a vault column. This row writes no crate until a sentence names the catch-up.

Approve width. The mood dialog Approve button grows by three ems after the short fit. The label stays Approve. The height stays short. This row writes no crate until a sentence names the catch-up.

Mood stays with the app. The mood string and the switch position are a home file, not a vault column. The next open restores both. The switch is grey until a string exists, then a click flips it. Approve turns it on when it was off. A yellow line above the inspector shows the string while the switch is on. This row writes no crate until a sentence names the catch-up. This row is the later move past the earlier claim that the string is only session memory.

Mood rise. The mood string rises from the top of the dialog footer when a reply sets it. The height follows the wrap. One line is one line tall. The resting footer is the spinner, Approve, and Cancel. Past half the dialog the pane scrolls. This row writes no crate until a sentence names the catch-up.

Dialog size. The mood dialog keeps the width and height from the last Approve or Cancel. The next open uses that pair. Until then it opens at 760 by 520. The place on screen is not kept. This row writes no crate until a sentence names the catch-up.

Mood tokens. When the user asks the mood model for more detail, the control line tells it to say that detail and to put more tokens on the mood line. The app still keeps that line. A short line stays enough when the user did not ask for more. This row writes no crate until a sentence names the catch-up.

Mood wire. Each mood send uses a new wire id. The server still refuses a second frame for a canvas cell that is running. The mood glass stays open. This row writes no crate until a sentence names the catch-up.

Mood session. A saved mood is already in the rising field when the dialog opens. A mood send keeps the shape line, then that field, then the activate line, before the chat. An empty field sends no mood tokens. Clear mood drops the saved string, turns the switch off, and empties the field and the chat. The dialog stays open. This row writes no crate until a sentence names the catch-up.

Clear ask. Clear mood asks `Clear the Mood Setting?` Yes clears the saved string and the chat. No leaves the mood and closes the ask. The mood dialog stays open. This row writes no crate until a sentence names the catch-up.

Choice words. Yes and No are wide enough to show the words. Yes stays green. No stays red. This row writes no crate until a sentence names the catch-up.

Pin bar on the canvas. The pin box, the field, and OK sit on a thin bar at the top of the canvas. The inspector header keeps soft wrap and the two counters. OK writes the states. The click does not open Mood. No system line is added, removed, or reordered. This land leaves the working set as it stands. The window thread paints the bar. This row writes no crate.

Pin fold. `v` folds the pin bar. P opens it. The bar starts folded. The choice is saved with the shell. A missing key stays folded. Enter in the word field does what OK does. OK writes the marks. A miss goes Excluded. An excluded match goes Included. Any other match stays. A burner misses. Clear empties the field and does not write. Turning the box off keeps the words. The states stay until OK. The OK tip stays the sentence already on the button. The edge paint stays in the paint that already draws the card. No system line moves. The field takes the words while the box is on. A scrolled card does not take that click. The crate move is this land. The states are the marks.

Pin tuck. `v` on the open bar folds the whole bar off the canvas. The cards use the height they had before the pin bar. P sits on the east of the canvas tab header while the bar is folded. P adds no row. P shows the bar. `v` stays `v`. Folding does not uncheck the pin. Only the pin box turns the filter off. The words stay. The choice is saved with the shell. A missing key stays folded. The OK tip stays the sentence already on the button. The edge paint stays in the card paint. No system line moves. The crate move for the tuck waits until a sentence names it. This row writes no crate until then.

Mark faces. P and the thinking-pane C wear the armed-card sapphire. A one-pixel white bevel surrounds each button face. The letter is white. The seat of P stays on the east of the canvas tab header. P adds no row. P shows while the pin bar is folded. C stays on the east of the thinking grip and still clears the log. The other short buttons keep the theme bevel. The cell-state P stays on the theme face. Folding leaves the pin box as it is. No system line moves. The crate move for the face waits until a sentence names it. This row writes no crate until then.

Pin edge colors. While the pin box is on, a readable match is `#3cb371` and a readable miss is `#222222`. The armed card, and every card in a group whose merge is armed, is `#2e86ff`. That sapphire wins over the pin color. The paint stays in the card paint. The inner prompt and reply borders stay their own green line. The pin box stays the only off switch. No system line moves. The crate move for the edge waits until a sentence names it. This row writes no crate until then.

Pin bar on this client. The pin bar matches the Java pin bar. While the pin box is on, a readable match is `#3cb371` and a readable miss is `#222222`. The armed card, and every card in a group whose merge is armed, is `#2e86ff`. That sapphire wins. The bar folds off the canvas. P sits on the east of the Canvas tab row and adds no row. P wears `#2e86ff`, a one-pixel white bevel, and a white letter. `v` stays `v`. Folding does not uncheck the pin. A missing key stays folded. Enter does what OK does while the box is on. Turning the box off keeps the words. Clear is a red face with a black x and does not commit. A burner skips the pin color. The six-pixel inset stays off. The OK tip does not open Mood. The thinking-pane C stays on its own plate. No system line moves. This slice is the crate move. The window thread paints the bar and the edge.

Pin seat. The air-gap and num-ctx words sit on one line above the inspector, on the inspector's share of the tab row. They leave the card scroll. Later scope text stays on the canvas. P sits on the divider and moves with that bar. A collapsed pane keeps P on the bar and inside the window. P adds no row. The sapphire face stays. The pin bar behavior stays. Java keeps P on the tab. No system line moves. This row is the crate move. The window thread paints the line and P.

Pin face colors. P is green while the pin is on and sapphire while the pin is off. Green is `#3cb371`. Sapphire is `#2e86ff`. The letter stays white. The one-pixel white bevel stays. C stays sapphire. Java keeps P on the tab. No system line moves. The Rust face follows in this course. The window thread paints the face.

Pin face on the divider. P is green while the pin is on and sapphire while the pin is off. The button face moves with the divider. The letter stays white. The white bevel stays. The pin bar behavior stays. Java keeps P on the tab. No system line moves. This row is the crate move.

Pin frame. The footer sits on the frame under the tab row. The pin row takes height from the cards. While the pin box is on, the inspector lists the working set the edge already paints. The letters stay until OK or Enter. OK and Enter write that set before the cards and the inspector paint. Clear does not write. Turning the box off keeps the words and the letters. The inspector then returns to those letters. A burner misses on the write and keeps the quiet edge until then. OK does not open Mood. No system line moves. This row is the crate move.

Pin filter. The inspector reads the stored marks. The edge stays live. A match is `#3cb371`. A miss is `#222222`. The armed card stays sapphire. A focused Excluded card omits its own text. The kept turns before it stay. Typing does not write. Clear does not write. Turning the box off does not write. OK and Enter write the marks. OK does not open Mood. Cards stay on the canvas. The six-pixel inset stays off. No system line moves. This row is the crate move.

Fresh box. A fresh canvas starts unchecked. Clear leaves that box shut. No system line moves. This row is the crate move.

Line length. The inspector cuts each kept line. The mood line stays at 80. No system line moves. This row is the crate move.

Cell height. The cell height locks every open pane. A checked reply stays whole. An unchecked reply keeps that pane. No system line moves. This row is the crate move.

Row count. The row count sets the open height. A checked reply stays whole. An unchecked reply keeps that height. No system line moves. This row is the crate move.

Bar run. The spinner sits on the bar. Run and Stop sit with it. A later group member keeps running. The stored reply stays. No system line moves. This row is the crate move.

Opened turn. The last reply opens the next turn. A card that is not last stays put. A fault stays put. The new card keeps that card's box. The focus moves to the new prompt. No system line moves. This row is the crate move.

Carried box. A new turn keeps the box from the prior cell. The first card starts checked. A blank copy still starts checked. The box stays for this window. It is not an export field. The row control that changes both windows waits. No system line moves. This row is the crate move.

Expanded view on this client. The expanded view starts checked. The reply shows in full. Unchecked, the reply stays in a window the same height as the prompt. A new card starts checked. The box stays for this window. It is not an export field. The row control that changes both windows waits. No system line moves. This row is the crate move.

Pin view. The pin view hides a miss. A miss leaves the canvas while the box is on and the field has a pin word. The card stays in the deck. The mark stays. The inspector lists the visible cards. An empty field shows the deck. Turning the box off shows the deck. OK and Enter leave the marks alone. OK does not open Mood. The send path stays on the stored marks. The six-pixel inset stays off. No system line moves. This row is the crate move.

Expanded view on the Java cell. Each readable cell carries an Expanded View checkbox after the collapse control and before the burner checkbox. An unreadable card has no box. Checked, the reply text pane grows to the wrapped buffer and shows all of the text inside its border. A text change and a window resize refit a checked reply. Unchecked, the reply stays at the saved view-row count, the same height as the prompt, and the text scrolls inside. The footer count sets that height. The prompt keeps that count. A folded card stays a brief line. The checkbox still shows and keeps the choice. A fresh canvas, a vault open, and a disconnect reload start unchecked. Footer Clear leaves one empty card unchecked. A new turn copies the prior cell. An empty deck stays unchecked. A duplicated group starts checked. Import, Pull, and a merge cell start unchecked. The choice stays for this window. It is not saved. It is not a vault column and not an export field. Java holds the core. The seated crate already paints Expanded View. This row writes no crate. No system line moves.

Cell stamp. The focused cell wears the local stamp and the short model on the top stroke of its outer frame. The line is centered on that stroke and seated on the right. Swing paints across the border. There is no north pad. The selection frame, the embedded line, and the first lead stay seated on the crate. Java holds the core. The seated crate already paints the caption and the first lead. The Rust lead stays on the crate. The crate move for this delta waits until a sentence names it. No system line moves. The window thread embeds the stamp on the Java cell. This row writes no crate.

Clean rule. The words are the stamp and the short model. Each flank is a one-pixel stroke on the top border. The text carries no box rule. The line stays clean and still reads the way the Rust cell reads. Java holds the core. The seated crate paints the sapphire flank. No system line moves. The window thread strokes the flank on the Java cell. This row writes no crate.

Sapphire stroke. The words are the stamp and the short model. Each flank is a one-pixel stroke on the top border. The stroke wears the selection sapphire. The words stay the caption ink. The text carries no box rule. Java holds the core. The seated crate paints the sapphire flank. No system line moves. This row writes no crate.

Scroll focus on the Java cell. A click on a text pane puts scroll focus on that pane. A click outside that pane returns scroll focus to the canvas. The armed cell stays armed. Hover on a cell shows the stamp and the short model. A quiet cell paints no line. Java holds the core. The seated crate parks scroll on the canvas and shows the stamp on hover. No system line moves. This row writes no crate.

Console pool thought. A pool thought on the VT100 rides kind `t` on the stream channel. The cell id is the nil id. When console pool thinking is on, the pull-down opens and the tag is pool. The thought stays off the terminal. The assistant reply still paints on the VT100. The end of the turn closes the block. A canvas cell keeps its own gate. Ollama and Grok on the console stay as they are. Java holds the core. The seated crate seats the VT100 thought strip on the top. No system line moves. This row writes no crate.

Reusable strip. The strip is the reusable component. A second instance sits on each VT100 panel. Console pool thoughts open that strip tagged pool when console pool thinking is on. Cell thoughts stay on the notebook strip. The thought stays off the terminal. The assistant reply still paints on the VT100. The end of the turn closes the block. Ollama and Grok on the console stay as they are. The notebook strip keeps the cell. This row writes no crate.

VT100 strip on top. The VT100 strip sits on the top and slides down. The bottom edge meets the top of the terminal. Drag that edge to set the height. The height and the closed state are stored apart from the notebook strip. The text stays off the vault log. The notebook strip stays on the bottom and still slides up. Cell thoughts stay on the notebook strip. Console pool thoughts still open the VT100 strip when console pool thinking is on. The assistant reply still paints on the VT100. Ollama and Grok on the console stay as they are. This row writes no crate.

Console family word. The VT100 console stores the last family word. A new session reads that word before the first turn. Pool stays pool. Ollama and Grok stay the word last set. The file sits beside the console history. A version other than 1 is left unread. The notebook strip stays on the bottom. The VT100 strip stays on the top. The seated crate opens Select model on the VT100. No system line moves. This row writes no crate.

Chosen model. The VT100 console stores the model chosen in Model Config. A new session reads that model before the first turn. Pool stays pool. An Ollama id stays that id. A Grok id stays that id. The file sits beside the console history with the family word. Version stays 1. A version other than 1 is left unread and is not overwritten. The notebook strip stays on the bottom. The VT100 strip stays on the top. No system line moves. This row writes no crate.

Add Cell. Add Cell sits on the merge cell button bar only. It adds a new member to that merge group. The new member takes the next column and sits with the members, before the merge. The prompt and the reply stay empty. Group, Join, Ungroup, and New stay. The new member copies the last member's execution target, Ollama think choice, Grok settings, and expanded choice. Focus moves to the new prompt. The requirement is a cell a new operator can type in at once. Both clients make that copy. No system line is added, removed, or reordered. The doors stay a canvas Run, a glass send, a group send, and a pipe send. The server still replaces the host sentence. This land leaves the send set as it stands. The window thread paints Add Cell on the merge bar. This row is the crate move.

Model Config. Model Config follows the cell. One modal. A change writes the cell at once. An empty Grok model says `no-model` and does not send. A group Run that includes one empty Grok model sends no member. Java marks that member failed, says `no-model`, and still sends the other members. The requirement is one click and no partial frames. The sibling is the Java dispatch loop. The doors are a canvas Run, a glass send, a group send, and a pipe send. They share this head. The system lines stay the host sentence, then the saved mood string, then the activate line, when the switch sends. The turns follow. The server replaces the host sentence. This land leaves the working set as it stands. This row is the crate move. The Ollama Model pull-down lists the models registered on the host. Opening the pane, switching the family to Ollama, and opening the arrow send `tags:`. The rows are the `model:` names. A typed id that is not in the list stays. The Swing process does not open a socket. Java holds the core. The seated crate already lists those names. This delta writes no crate. No system line moves. The window thread lists the host models on the Ollama pull-down.

Console stop. Ctrl-C stops a running request in the VT100 console. The focused grid sends `0x03`. The copy chord stays with a canvas field and with Model Config. Cut and paste stay clipboard events. A mouse drag still copies. No system line moves. This row is the crate move. The window thread sends the interrupt.

Vault proof. One canvas goes in and comes out on a local file. The canvas name is Canvas 1. A burner stores metadata and empty payloads. Search keeps `%` and `_` literal. A crash of RUNNING becomes INTERRUPTED. The file is `aetheris.db` beside the Java vault directory. The Java file stays closed. Cloud sync stays off. The pin is `turso` 0.8.3. The lock moves `generic-array` from 0.14.9 to 0.14.7 with that pin. The thinking-log checkbox restart stays held. This land leaves the working set as it stands. This row is the crate move. The simple thread still holds.

Open canvas. Connect opens `aetheris.db` after the session starts. The first canvas loads. This thread writes that canvas on each paint. Disconnect, a dropped link, and close write it before the deck is cleared. A turn that left the deck is removed. Traces go with it. Another canvas stays. A burner stores metadata and empty payloads. Search on the open file keeps percent and underscore literal. A running card stores RUNNING. The next open writes that status as INTERRUPTED. A quiet card keeps INTERRUPTED, FAILED, CANCELLED, and COMPLETED. Expanded View starts unchecked on load. The thinking-log checkbox restart stays held. The Java file stays closed. Cloud sync stays off. The pin stays `turso` 0.8.3. This row is the crate move. The window thread writes the open canvas.

Input edge. The input pane carries the local clock and the short model on the right of its top border. The stamp is local wall time, year month day, hour minute second. A middle dot sits between the stamp and the model. A blank model keeps the stamp. A caption wider than the pane keeps the right edge and loses its left. Folded cards and merge sinks have no input pane, so the line stays off those cards. The client names `chrono` 0.4.45 already in the lock for the local stamp. The pin stays `turso` 0.8.3. This row is the crate move.

Cell ink. The prompt on a working card is green, `#00ff66`. A reply that has text is yellow, `#ffff55`. A reply whose text is blank is gray, `#666666`. An excluded card paints both panes gray. The borders stay. The folded line stays. This row is the crate move.

Selection frame. The focused cell carries the local clock and the short model on the right of its outer frame. The stamp is local wall time, year month day, hour minute second. A middle dot sits between the stamp and the model. A blank model keeps the stamp. A caption wider than the frame keeps the right edge and loses its left. A quiet cell keeps a plain frame. An armed merge lights its members. The caption stays on the focused cell. Folded or open, the seat is that frame. The input pane border is a plain stroke. The half-line above the input pane is gone. The client names `chrono` 0.4.45 already in the lock. The pin stays `turso` 0.8.3. This row is the crate move.

North band. The caption sits in the band north of the focused cell. That band is the gap between this cell and the cell above. Four pixels stay between the caption and the stroke. Four pixels stay between the caption and the cell above. The cell keeps its width and its interior. There is no south pad. There is no east pad. There is no west pad. A long name stays in that band. A wide line keeps the right end. The pin stays `turso` 0.8.3. This row is the crate move.

Embedded line. The caption is embedded in the top stroke of the focused cell. Half the line stands above the stroke. Four pixels stay between the bottom of the line and the buttons. The north gap is half the line plus those four pixels. The first cell clips the line at the stroke. The cell keeps its width. The pin stays `turso` 0.8.3. This row is the crate move.

First lead. The first cell keeps a lead under the view. That lead is half the line plus four pixels. The caption stays embedded on the stroke and the whole line shows. Four pixels stay between the top of the line and the view. Later cells keep the north gap. The cell keeps its width. The pin stays `turso` 0.8.3. This row is the crate move.

Border arm. A primary click inside the cell border arms that cell. The focus highlight moves to that cell. The prompt editor keeps the caret. A click on reply text of any size arms the cell. A click in the empty interior arms the cell. A folded card arms on that click. The caption above the stroke stays outside the border. The cell keeps its width. The pin stays `turso` 0.8.3. This row is the crate move.

Sapphire flank. The words are the stamp and the short model. Each flank is a one-pixel stroke on the top border. The stroke wears the selection sapphire, `#2e86ff`. The words stay the caption ink. The text carries no box rule. A blank model keeps the stamp. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. The lead stays on the crate. The pin stays `turso` 0.8.3. This row is the crate move.

Scroll focus. A press on the prompt or the reply, including its scrollbar, holds the wheel on that pane. The wheel stops at the end of that pane. A press anywhere else on the card returns the wheel to the canvas. The armed cell stays armed. A press on the canvas padding arms nothing and returns the wheel to the canvas. A folded brief is not a text pane. The execution target keeps the caret. Hover anywhere on a cell shows the stamp and the short model. A blank model keeps the stamp. The clock is the moment of the hover. A quiet cell paints no line. The focused line keeps its hover. Buttons keep their own hover. The sapphire flanks stay. The lead stays. The pin stays `turso` 0.8.3. This row is the crate move.

VT100 thought strip. The strip is born on the north of the terminal and grows downward. The grip is the bottom edge. Dragging down grows the pane. Height and closed live in `~/.casserole/aetheris-vt100-think.yaml`. A version other than 1 is left unread and is not overwritten. The thought text is not written there and is not written to `aetheris-thinking.txt`. The notebook strip stays on `aetheris-think.yaml` and `aetheris-thinking.txt`. A nil-cell kind `t` opens this strip when console pool thinking is on. The tag is pool. The end of that turn closes the block. The pane starts hidden. A closed pane stays closed until a thought opens it. `v` folds for the session and does not write the file. Clear wipes the text and leaves the pane up. A canvas cell keeps its own gate. The assistant reply still paints on the VT100. The pin stays `turso` 0.8.3. No system line moves. This row is the crate move.

VT100 Select model. A right-click on the VT100 offers Select model. The modal is the one already seated. The shell answers with the family and the model id. A change sends `shell_model_set:` with both. Java writes the file. The next session reads that family and that model id. A family-only file still reads. A version other than 1 is left unread and is not overwritten. The ask on connect seats the console and leaves the modal shut. A right-click opens the modal. The cell modal stays on the cell. The pin stays `turso` 0.8.3. No system line moves. This row is the crate move.

VT100 modal paint. The Model Config window is drawn once for the plate on screen. The SSH plate and the notebook share that draw. Select model still asks the shell. The answer opens the modal. A raw console stays shut. A shut modal stays shut. The cell modal stays on the cell. The pin stays `turso` 0.8.3. No system line moves. This row is the crate move.

Canvas layout. The notebook thinking pane rises from the top of the buttons. Its bottom stays on that frame. The grip is the north edge. The VT100 strip still grows downward. The divider starts at the top of the frame. The Canvas tab is the top of the left column. The fact line is the inspector top row. P is anchored to the top of the divider and moves with it. A drag on the bar still moves the divider. A click on P opens the pin bar. The pin stays `turso` 0.8.3. No system line moves. The cells stop at the bottom of the open pin bar and at the top of the thinking pane. The cells take the gap the pin bar and the thinking pane leave. The frame assigns the gap under P and the grip. The deck top stays on the pin strip. Opening the thinking pane shortens the deck from the south. Folding it gives that height back. The top stays on the bottom of the pin strip. The Canvas tab and P share one strip. The deck starts at the bottom of that strip. Opening the thinking pane keeps that top and shortens the deck from the south. The window thread raises the divider and the thinking pane rises off the buttons.

Model tip. The stamp tip shows on a cell that is not selected. The selected cell is the focused cell. That cell paints the line and keeps the line tip. A cell that is not selected shows the stamp and the short model on hover. A blank model keeps the stamp. The clock is the moment of the hover. Buttons keep their own hover. The sapphire flanks stay. The pin stays `turso` 0.8.3. No system line moves. The window thread keeps the model tip off the selected cell.

Ollama think stream. Ollama think streams while the model writes. A set think string on a text-only prompt pulls each thinking piece on the shared Ollama path while the model writes. Kind `t` is the piece. The reply is the joined content. The request carries the resolved model, the think option, and `num_ctx` when that integer is greater than 0. Empty, false, a null sink, an image, a tool call, and a tool result stay on the one-shot call. The stream reply has no thinking metadata, so the cell blob stays quiet. The console pane opens when the family is ollama and the think string is set. The tag is the short model. Pool still opens only when the pool switch is on. Grok on the console stays shut. The seated crate opens the VT100 strip. Pool stays the pool switch and the tag is pool. Ollama uses the stored think string and the tag is the short model. Grok stays shut. The end of the turn closes the block even when the gate has shut. No system line moves. This row is the crate move.

Inspector header. The facts line wraps above the inspector. A reported init is air-gap and num-ctx on one line. An unreported init stays `init: not reported`. The Inspector label is the next row. Soft wrap, the column count, and the line length are the row after that. The full mood line wraps in yellow above the cards while the switch is on. The card body keeps the short mood and shape lines and does not repeat the facts. The card text does not take a selection or the keyboard. The scroll pane keeps the wheel. The user list on the lower right uses the fitted size of the first button bar, and the font control keeps that size. No system line moves. The crate already seats the facts line, the Inspector label, the control row, and the wrapped mood. This filing writes no crate.

Facts line. The facts line sits on its own panel. The font shrinks so air-gap and num-ctx stay on one row inside the inspector column. An unreported init stays `init: not reported`. The Inspector label, soft wrap, the column count, and the line length sit on the panel snapped under that line. Both size controls stay on that panel. That pair is the north of the inspector. The scroll pane is the center. The full mood line is the first card and scrolls with the turn cards while the switch is on. The card body keeps the short mood and shape lines and does not repeat the facts. The card text does not take a selection or the keyboard. The scroll pane keeps the wheel. The user list on the lower right uses the fitted size of the first button bar, and the font control keeps that size. No system line moves. Java seats the mood as the first card inside the scroll, and the facts font fits the column. This filing writes no crate.

View checks. Each Expanded View check is saved with that cell id. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell choice. Delete drops that cell. A version other than 1 is left unread and is not overwritten. The window caption is `Aetheris Rust`. The icon is the Eye of Horus art. The thin status bar sits at the bottom of the Aetheris pane. West names the vault, the wire, the air-gap, and the engine. East names the user while the mux is up. The link chip stays on the connection bar. The seat is `aetheris-expanded.yaml`. It is not a vault column. No wire move. The pin stays `turso` 0.8.3. Java keeps Expanded View for this window. This client saves the check per cell in that file. The Java frame title stays `Aetheris Shell`. The status bar uses the Java colors and the Java line. The Rust vault word is `disk`. The mismatch tail stays off because the client has one air-gap word. This filing writes the crate.

Hide collapsed. Hide Collapsed and Show Collapsed sit on the tool row after Mood, past a divider. Hide takes every folded card off the deck. Show puts those cards back, still folded. An open card stays. A folded member leaves and the open merge stays. A group that is entirely folded leaves. The choice is for this window. It is not saved. The seat is the deck. It is not a vault column. No wire move. The pin stays `turso` 0.8.3. This client omits a folded card in the shown slots while Hide Collapsed is on. Show passes the slots through. This filing writes the crate.

Hide collapsed on Java. Hide Collapsed and Show Collapsed sit on the second button row after Mood, past a divider. Hide takes every folded card off the canvas. Show puts those cards back, still folded. The column uses the cells on the canvas. A folded member leaves and the open merge stays. The choice is not saved. It is not a vault column. No wire move. This client already omits the folded card in the shown slots. Java omits it in the cells on the canvas and rebuilds the tabs. The button names match. This filing writes the Java sources.

Hide seat. Hide Collapsed and Show Collapsed keep their choice. Hide writes `hide_collapsed` true. Show writes it false. A missing file starts on Show. A version other than 1 is left unread and is not overwritten. The seat is `aetheris-hide.yaml`. It is not a vault column. No wire move. The pin stays `turso` 0.8.3. This client reads that file when the door has a file. A memory door writes nothing. The choice is saved. This filing writes the crate.

Hide seat on Java. The same buttons keep their choice on the second button row. Hide writes `hide_collapsed` true. Show writes it false. A missing file starts on Show. A version other than 1 is left unread and is not overwritten. The seat is `aetheris-hide.yaml`. It is not a vault column. No wire move. This client already reads that file. Java reads the same file and rebuilds the tabs. The button names match. The choice is saved. This filing writes the Java sources.

Token ring on Java. The estimate sits on the south of the inspector, before T. T wears the P button face. The ring compares that count to the selected model's context window. Ollama rows share the session num-ctx. A Grok row shows the completion cap and says the context window is not reported. Pool says it has no context window here. OK and the close icon dismiss the window. No wire move. It is not a vault column. No compaction. No change to the conversation window. This client keeps the estimate at the end of the inspector body. Java seats the estimate under the scroll. T opens the ring. The crate stays unwritten. This filing writes the Java sources.

Token ring budgets. The ring reads `casserole.grok.context-windows` and `casserole.pool.context-window` from `application.yaml`. Ollama rows still share the session num-ctx. A Grok id in that map uses that window and still shows the completion cap. Pool uses the pool window, 256000. A missing id stays unreported. This open does not ask the host. No wire move. It is not a vault column. No compaction. No change to the conversation window. The crate stays unwritten. This client still says the context window is not on the model config. Java reads the yaml budgets. This filing writes the Java sources.

Token ring on this client. The estimate sits on the south of the inspector, before T. T wears the P button face. The ring compares that count to the selected model's context window. Ollama rows share the session num-ctx. Grok and pool windows come from `application.yaml`. A Grok row shows the completion cap. A missing id stays unreported. OK and the close icon dismiss the window. No wire move. It is not a vault column. No compaction. No change to the conversation window. Java already seats the estimate under the scroll and reads the yaml budgets. This client seats the same line and the same ring. An empty count stays 1. This filing writes the crate. The pin stays `turso` 0.8.3.

User combo. The connected-user pull-down sits on the east of the footer. The spinner sits immediately west of it while a card is running. A quiet deck hides the spinner. The combo stays. The width is 160 and the ceiling is 180. The list is the sanitized names of connected mux sessions. An empty username is omitted. Illegal names share one `anon` entry. A focused raw session, or a disconnected session, leaves the selection empty and leaves the open vault open. The same user does not reload the vault. A different name binds that vault. The window has one live session. No system line moves. It is not a vault column. No second SSH session. Java already seats the pull-down. This client seats the same list and the same keep-or-switch rule. The status line stays. The accounts pull-down stays. This filing writes the crate. The pin stays `turso` 0.8.3.

Remembered chooser. One shell preference holds the dialog bounds and the last folder. The keys are `ui.chooser.x`, `ui.chooser.y`, `ui.chooser.width`, `ui.chooser.height`, and `ui.chooser.directory`. The cell import uses them. A cancel writes the bounds and leaves the folder. An approved file writes the parent folder before the read, so a refused read keeps that folder. A width under 240, a height under 180, or a rectangle that misses every screen opens at the platform default. A missing folder opens at the platform default. Merge and output only still refuse before the dialog opens. No system line moves. It is not a vault column. Send file stays on the VT100 tray. Java already seats the five keys. This client seats the same keys on the shell file. The cell import reads them. The export dialog and the canvas import dialog stay their own scripts. The classic dialog reports the bounds. This filing writes the crate. The pin stays `turso` 0.8.3.

VT100 shell catch-up. The later shell sits on the open connection bar. Connected enables Disconnect, Reconnect, and Send file, and disables Connect. Connecting disables Connect, Disconnect, Reconnect, and Send file. Disconnected enables Connect and disables the other three. New Term stays enabled. Clear Terminal stays enabled. Close Pane is enabled when more than one pane is open. Ctrl+Shift+D splits side by side. Ctrl+Shift+E splits top and bottom. Ctrl+Shift+W closes the focused pane. New Term asks Horizontal, Vertical, or Cancel. The divider starts at half. A drag stays between 0.15 and 0.85 and is not saved. A new pane starts disconnected. The last pane stays. Restart starts with one pane. The focused leaf wears a one-pixel ring when more than one pane is open. Ctrl+V and Ctrl+Shift+V open the edit paste. OK and Ctrl+Enter send. Enter inserts a newline. The cap is 65536. Over the cap refuses before the send. The send is always bracketed and keeps LF. An empty paste writes nothing. A disconnected send says `Not connected`. Send file opens that editor. Import inserts at the cursor and does not open the editor. A disconnected import says `Not connected` and does not open the dialog. The shell chooser title is Open and the filter is all files. The cell import chooser keeps the Import title and the text-file filter. The font control runs from 8 through 36, step 1, with minus and plus. A missing font key is 14. The first paint uses 14. The value 8 is the bottom of that range, not the missing-key size. The other client's missing size of 20 is not this client's default. File and Help stay above the fold. Exit asks, then saves the restored frame and closes every pane. A missing maximized key stays maximized. A stored false opens restored. A stored true opens maximized. Clear Terminal clears the active screen and keeps paste mode, the other screen, and the keypad mode. A background pane keeps its own scope, capped at 256, and does not open Model Config. The connected-user list is the focused pane and each parked connected pane. A pick focuses that pane, then the keep-or-switch rule runs. A second pane of the same user skips the canvas wipe. No system line moves. It is not a vault column. The Java emulator lines other than Import stay on the next slice. Java already seats the later shell. This client seats the same buttons, the same splits, the same paste editor, and the same import. The open-maximized default stays true. This filing writes the crate. The pin stays `turso` 0.8.3.

Shell finesse. Each SSH pane keeps its own thinking log. The strip sits at the top of that pane. One pane paints the strip above the grid. A horizontal split and a vertical split paint the strip inside the focused pane and inside each parked pane. The grid under a parked strip uses the height that remains. Height and closed stay on the shared window state. Clear wipes that pane's log. A thought on a background pane opens the shared strip and leaves Model Config shut. A scope piece is offered once. The SSH plate wears one status strip at the bottom. The fill is `0x171717`. The ink is `0xb4b4b4`. The pad is 8 by 3. Connecting stays Connecting. Connected shows Connected with host and port. A Connected string that already carries a suffix stays. Disconnected stays Disconnected. A disconnect reason uses an em dash. The east name is the sanitized user when the link is up and the mux has answered. The chip words stay. When a reply exists, the row already between the input pane and the reply pane fills with `0x3a3a3a`. A folded brief has no input pane, so the band stays off. Confirm exit stores `ui.exit.x` and `ui.exit.y`. The same pair leaves the file alone. Either key missing keeps the platform place. A 160 by 48 title box that misses the view keeps the platform place. Other keys stay. Side move: the import chooser calls the platform open-file dialog. The requirement is that file import opens, because the sealed dialog cannot be the path. The Java chooser stays. This land leaves the working set as it stands. It is not a vault column. Java seats one think strip on the north of each SSH panel. This client seats the same strip on each pane. The height and the closed state stay shared. Java seats the status strip on the south of the shell. This client seats the same strip on the SSH plate. The canvas vault line stays. Java leaves the row between the input and the reply. This client fills that row with the dark divider when a reply exists. Java confirm-and-exit leaves the exit origin on the platform. This client stores that origin on the shell file. This filing writes the crate. The pin stays `turso` 0.8.3.

Session think. Each SSH session opens its own thinking strip. A nil-cell kind `t`, while the console thought gate is open, appends to that session's log and starts that strip at height 0. The strip grows downward from the top of that pane. The step is twelve pixels, or a quarter of the remaining span, and the next paint is 16 ms later. A quiet session stays hidden. Height and fold stay on the shared window state. Close hides that session's strip. A dismissed Model Config does not seat a later shell-model result, so an empty think line cannot clear the string the checkbox wrote. A connect that has not opened the dialog still seats the server answer. A scope piece is still offered once. No system line moves. No wire move. It is not a vault column. Java seats one think strip on the north of each SSH panel and slides that strip down from height 0. Java returns when applying a shell-model result if the dialog was dismissed. This client seats the same gate and the same slide on each pane. This filing writes the crate. The pin stays `turso` 0.8.3.

Clear button. The Clear button owns its face on the thinking grip. The drag handle stops one pixel short of that button, so the grip tip and the resize cursor stay off the button. On an open SSH strip the console starts three pixels under the button. The notebook strip keeps its flow. No system line moves. No wire move. It is not a vault column. Java seats Clear on the east of the grip. This client seats the same button and keeps the drag off its face. This filing writes the crate. The pin stays `turso` 0.8.3.

SSH mood. The Mood footer carries a checkbox named SSH Mood Active. The box starts unchecked, and this window does not write it to the shared mood file. While it is unchecked, a mood that is on does not reach an SSH session. SSH stays as it is. While it is checked and the mood switch is on, each connected SSH pane takes that mood in its chat session. The thin SSH status strip says `mood active`. The mood string stays off the console grid. Door: the console turn completion. While the mood is seated, the system lines in send order are the pinned system already in the session, then the mood string, then the activate line `Activate the mood above. Use that mood as the shape of your responses.`, then the turns. The host insert places the configured host prompt at index 0 only when that text is absent. It does not replace the mood line. The line is not stored in the conversation window. Canvas, group, and pipe sends stay as they are. The server still replaces the first system line on that canvas door. While the box is unchecked, the console prompt is the current list and no `shell_mood` frame is sent. Side move: the chat session seats the mood and the activate line on the console prompt, and the shell consumes `shell_mood:` and `shell_mood_clear:` before the cell door. The requirement is that the VT100 chat prompt is built on the server. The frame is not written to the grid. It is not a vault column. Java seats the mood after the pinned system. The shell consumes the frame before the cell door and does not paint it. This client sends that frame only while the box is checked and the mood switch is on. This filing writes the crate and the server seat. The pin stays `turso` 0.8.3.

Thinking fold per pane. Each SSH thinking pane folds on its own. Fold toggles that strip, stops its rise, and hides its scroll. The neighbor stays as it is. Close returns the mark to `v` on the strip that closed. A thought opens that strip and leaves its fold. Clear keeps the fold. Height and the closed mark stay on the shared window state. The file does not store the fold. An open unfolded strip seats the console three pixels under that pane's Clear button. A folded strip keeps the grip and one row, and the console sits three pixels under that pane's Clear button. A closed strip leaves the console at the pane top. A rise uses that pane's slide, and a fold settles that slide. A grip drag still writes the one shared height. Focus carries the fold with the pane. No system line moves. No wire move. It is not a vault column. The pin stays `turso` 0.8.3. Java seats the minimized mark on each strip. This client seats the fold on the focused session and on each parked pane. The shared window state keeps the height. This filing writes the crate.

Folded menu. While the SSH plate is showing and the connection bar is folded, File and Help sit on that tab row after a divider that follows the Aetheris tab. An open bar keeps File and Help at the top of the window for SSH and for Aetheris. The Aetheris plate keeps the menu at the top even when the bar is folded. Java keeps File and Help on the window menu bar. No system line moves. No wire move. It is not a vault column. The pin stays `turso` 0.8.3. This client seats them at the top while the connection bar is open and while Aetheris is showing. While SSH is showing and the bar is folded, this client seats them on the tab row after a divider. This filing writes the crate.

VT100 status facts. The VT100 thin status line keeps the link word, then `mood active` when that gate is on, then the vault, the wire, the air-gap, and the engine. Connecting stays Connecting. Connected shows Connected with host and port. Disconnected stays Disconnected. A disconnect reason keeps the em dash. The mood string stays off the strip. The east name stays the user while the mux is up. The Aetheris strip keeps its canvas extra. The link chip stays on the connection bar. No system line moves. No wire move. It is not a vault column. The pin stays `turso` 0.8.3. This client seats those four facts on the VT100 strip. Java keeps the south label as it stands.

Blank host. A new chat starts blank. Door: a new chat session, and a canvas working set. While the prompt is blank and the mood is off, the new stream has no system line. While the mood is on, the system lines in send order are the mood string, then the activate line `Activate the mood above. Use that mood as the shape of your responses.` The canvas send opens with an empty system seat. The server drops that seat when the prompt is blank. The server drops the vestige sentence when a working set still opens with it and the prompt is blank. The mood line stays. A configured prompt fills the empty seat, and the mood pair follows when the mood is on. The inspector shows the mood head, then the working set. The vestige sentence stays off that pane. A mood-glass send keeps its shape line while the prompt is blank. Chat start and clear drop the turns. A configured prompt stays. A blank prompt leaves no system frame. A restored region keeps the system line it already holds until that new state. The porch reads the pair after a host, and at the head when the prompt is blank. The host stays out of the rules. No system line is added beyond that stanza. It is not a vault column. The pin stays `turso` 0.8.3. Java leaves the vestige sentence off a new session and off the canvas working set. This client sends an empty seat ahead of the mood pair and leaves the vestige sentence off the frame.

SSH pool mood. While the mood pair is seated, a pool session opens with that pair as porch rules. Door: pool session start. While the mood is on, the system lines in send order are the mood string, then the activate line `Activate the mood above. Use that mood as the shape of your responses.` They travel as `_meta.rules`. The porch appends those rules to its system prompt. The host stays the first system line in the role-line paste. The server does not replace the mood line. While the mood is off, `_meta` is absent. `yoloMode`, `autoMode`, and `systemPromptOverride` stay off. The role-line paste stays in its order, so a mood change opens a new porch session and the new rules go with it. A held session keeps the rules from the session that opened it. Canvas assembly stays. A canvas pool turn whose working set already holds the pair uses this same door. No extra system line. It is not a vault column. The pin stays `turso` 0.8.3. Java seats the rules on session start. The crate did not move.

SSH Grok think. While the SSH session's model is Grok and the Grok think switch is on, that pane's thinking strip opens and the tag is the Grok model. An off switch leaves the pane shut. Door: the console turn completion. No system line is added, removed, or reordered. While the switch is on, the call streams so reasoning content can be read, and kind `t` on the nil cell carries those chunks. The saved stream checkbox stays. The terminal reply stays the joined content. While the switch is off, the sink stays off that call. Canvas, group, and pipe sends stay as they are. Side moves: the console marks the call and turns its stream on when Grok think is true, so reasoning content is readable; the nil-cell sink attaches for that Grok turn, so the kind `t` frames exist; the notebook opens the strip on that same word, so there is one gate. Ollama keeps its think string. Pool keeps its own sink. It is not a vault column. The pin stays `turso` 0.8.3. Java seats the sink and the strip gate. This client copies the gate and opens that pane.

SSH mood prompt. An SSH prompt is the canvas shape on the conversation window. The host stays first. While the mood is on, the mood string and the activate line follow it, once. The turns follow. The new user line is last. A repeat of the same line leaves the region as it is. A changed line replaces the pair. Off removes the pair. The turns stay. The next prompt is the host and the turns. The conversation window pins the leading run, so an established session still sends the pair. Door: the console turn completion. While the mood is on, the system lines are the pinned host, the mood string, then the activate line. The host insert places the configured host prompt at index 0 only when that text is absent. It does not replace the mood line. While the mood is off, the system line is the host. Canvas, group, and pipe sends stay as they are. No wire move beyond this stanza. The canvas working set stays as it stands. It is not a vault column. The pin stays `turso` 0.8.3. Java seats the pair in the SSH conversation region after the host while the line is on, and removes the pair when the line is off. The prompt is that window plus the new user line. This client already sends `shell_mood:` and `shell_mood_clear:`. The crate did not move.

SSH think size. Each SSH pane in the VT100 tab keeps its own thinking height and its own fold. A drag on one pane writes that pane. The other panes stay at their heights and their folds. A new pane starts at the file height. A kept drag writes that height into the file for the next open. The file does not store the fold. The notebook strip stays on its own height. No system line moves. No wire move. It is not a vault column. The pin stays `turso` 0.8.3. Java keeps the user height and the minimized mark on each strip. The file keeps one height for the next strip. This client keeps the height and the fold on the focused session and on each parked pane. A paint no longer copies one height onto every pane. This filing writes the crate.

SSH think panes. Each SSH pane in the VT100 tab keeps its own thinking strip. A shell result that says thinking is on opens that pane's strip. One pane's empty think line does not shut another pane's strip. A thought on a pane that is already thinking still opens that strip. Turning Thinking off in the open dialog stops the focused pane and leaves a parked pane thinking. The notebook strip stays as it is. No system line moves. No wire move. It is not a vault column. The pin stays `turso` 0.8.3. Java seats one strip on the north of each SSH panel. This client seats the same strip and a think flag on the focused session and on each parked pane. A background shell result does not shut a gate that is already open. This filing writes the crate.

Window frame. A restored close stores the outer origin and the inner size in physical pixels. The next open places that origin and that size. A maximized close stores the flag and keeps the last restored rectangle. A minimized close does the same. A rectangle that misses every screen, or a size under 400 by 300, opens at 1024 by 968 with no place. The maximized flag still applies. A missing maximized key stays maximized. A disconnect while Aetheris is showing stores `aetheris` and arms the wait. The next connect takes Aetheris. A close on SSH stores `ssh`. The next open stays on SSH. A click on SSH while the wait is armed stores `ssh`. No system line moves. No wire move. It is not a vault column. The pin stays `turso` 0.8.3. Java stores the outer bounds and the maximized flag, and a missing maximized key opens restored. This client stores the outer origin and the inner size in physical pixels. A missing maximized key stays maximized. Java leaves the stored word on a disconnect. This client stores `aetheris` on that disconnect and returns to Aetheris on the next connect. This filing writes the crate.

Aetheris bar menu. While the Aetheris plate is showing and the control bar is folded, a divider follows the Aetheris tab and File and Help sit after that divider. The row is the unfold mark, the status, the SSH tab, the Aetheris tab, the divider, then File and Help. An open bar keeps File and Help at the top. The SSH folded row stays as the checked plate seated it. Java keeps File and Help on the window menu bar. No system line moves. No wire move. It is not a vault column. The pin stays `turso` 0.8.3. This client seats them on the folded row after the Aetheris tab while Aetheris is showing and while SSH is showing. An open bar keeps them at the top. This filing writes the crate.

Inspector mood. While the mood switch is on, the yellow mood string is the first card in the inspector scroll. It sits atop the turn cards. A scroll down carries it out of view. The card wraps on the visible column and stays on that column while the turn text scrolls sideways. The facts line, the Inspector label, and the control row stay above the scroll. The card body keeps the short mood and shape lines. An off switch leaves the yellow card out. The scroll pane keeps the wheel. No system line moves. No wire move. It is not a vault column. The pin stays `turso` 0.8.3. Java already seats the yellow mood as the first card inside the inspector scroll. This client seats the same card. This filing writes the crate.

SSH mood memory. When the SSH mood is on, an already established chat that can think keeps that mood in its session conversation window. The host system message stays first. The mood string follows. The activate line follows the mood string. The turns stay. The non-system cap does not count those lines. A session with one system message still pins that one message. A log that does not open on a system message still pins the last system message. A clear removes the pair and keeps the turns. Chat start and clear drop the turns and keep the seated mood. A new session object leaves a stored pair off the console prompt until `shell_mood` seats the line again. Door: the console turn completion. The send order stays the pinned host, then the mood string, then the activate line, then the turns. The host insert places the configured host prompt at index 0 only when that text is absent. It does not replace the mood line. Canvas, group, and pipe sends stay as they are. No extra wire move. The canvas working set stays as it stands. It is not a vault column. The pin stays `turso` 0.8.3. Java stores the mood in the SSH session conversation window. This client already sends `shell_mood:` when the held line differs. This filing writes the host. The crate did not move.

Connect string. While the connection bar is open, the connect word sits to the right of Font size on that bar. Connected wears a green face and white letters, the same chip the folded bar wears. Connecting, Disconnected, and a disconnect reason keep the plate ink. The tab row follows that bar. The south status strip still says Connected with the host and the port. Java keeps the connect words on the south status label. No system line moves. No wire move. It is not a vault column. The pin stays `turso` 0.8.3. This client seats the short word on the open bar, to the right of Font size, and keeps the south strip. Connected wears the green chip. This filing writes the crate.

VT100 think ink. The VT100 thinking strip paints the thought body in selection sapphire lightened 33 percent toward white. The bytes are 115, 174, and 255. The notebook body stays yellow. The model tag stays cyan. The clear mark stays the selection sapphire. The terminal reply stays bright yellow. No system line moves. The crate still paints the yellow body. The Rust slice waits until a sentence names the catch-up. The window thread paints the strip. This row writes no crate until then. The pin stays `turso` 0.8.3.

Tools menu. Tools sits on the main menu between File and Token Calc. Show Thinking is the item under it. A click reveals the thinking pane of the plate in front. On the VT100 plate the focused session strip opens. A closed strip rises. A folded strip opens so the text is visible. The log stays. The closed mark on the file stays. A parked pane stays. On the Aetheris plate the canvas thinking pane opens. A closed pane rises. A folded pane opens so the text is visible. The log stays. The closed mark on the file stays. The VT100 strip stays as it is. No system line moves. Java keeps its menu as it stands. The Rust body stays yellow. This client seats Tools and Show Thinking. This filing writes the crate. The pin stays `turso` 0.8.3.

Folded thinking row. A folded thinking strip is one row on the VT100 plate and on the Aetheris plate. Left to right the row is `^`, the drag, C, then Close. The drag line starts after `^` and ends before C. A wider or narrower window changes the drag. `^`, C, and Close keep their size. An open strip keeps the grip and the text row. The console stays three pixels under C. The canvas clip stays at or above the north edge of the row. Fold, close, and clear stay as they are. The log stays. The file stays. No system line moves. Java keeps its folded strip as it stands. The Rust body stays yellow. This client seats a folded strip as one row. An open strip keeps the grip and the text row. This filing writes the crate. The pin stays `turso` 0.8.3.

VT100 mode word. When the VT100 console is in focus, the thin status line names the console mode. The wire word is VT100 or PTY. The word comes from `ui.connection.mode`. The token `pty` paints PTY, and a connect asks for `xterm-256color`. A missing token or any other token paints VT100, and a connect asks for `vt100`. The Aetheris strip keeps AMWP when the mux has answered and raw when it has not. The link word, `mood active`, the vault, the air-gap, the engine, the east name, and the token estimate stay. The canvas extra stays on the Aetheris strip. The mode key stays as it stands. The PTY mode list stays empty. No system line moves. Java keeps its south label as it stands. This client names the VT100 wire from the console mode. This filing writes the crate. The pin stays `turso` 0.8.3.

Think fold line. When the thinking pane folds or unfolds, the typed line stays on the VT100 console. The cursor row is that line. A taller console keeps that row at the bottom. A shorter console keeps that row and moves the lines above it into the scrollback. A cursor that is not on the last row keeps the older pad and clip. The host still hears the new size. No system line moves. Java keeps its fold as it stands. This client keeps the typed line on the screen when the console height changes. This filing writes the crate. The pin stays `turso` 0.8.3.

Soft return keys. Enter sends the typed line. Shift-Enter adds a newline and does not send. Enter writes CR. Shift-Enter writes one LF. The two are not written together. A Shift key that is still down is a soft return when the Enter event says shift is clear. A Shift release in that same frame, with no other key between it and Enter, is a soft return. Ctrl+Enter still sends. Alt+Enter still sends. No system line moves. Java keeps its soft return. The PTY mode list stays empty. This client reads the shift key. This filing writes the crate. The pin stays `turso` 0.8.3.

Canvas think line. When thinking text is streaming on the canvas, the pane stays on the latest line. That line is whole and sits above the footer button bar. One line of the thinking field stays under the text. The VT100 strip stays as it stands. The folded row stays one row. The grip stays on the north edge. No system line moves. Java keeps its thinking pane. The Rust body stays yellow. This client keeps the latest canvas line above the footer. This filing writes the crate. The pin stays `turso` 0.8.3.

Session export. Export Session sits on the VT100 console menu after Import and before Select model. A click on the focused console asks that connection. A click on a parked pane asks that pane. The save dialog writes this connection's whole conversation message table to a `.toon` file. Cancel writes nothing. The session stays. A console with no session says `Not connected`. A raw console says `raw console has no Roux export`. Canvas Export stays a `.aetheris` file. The Swing menu stays. No system line is added, removed, or reordered. Side move: the session returns the table. The shell wraps it, and a table that does not fit one frame is a short failure. The shell answers `session_export:` on channel A and does not paint the grid. The ask and the reply are nil-cell channel A text. It is not a vault column. The pin stays `turso` 0.8.3. Java returns this connection's conversation table. This client asks and writes the file. This filing writes the crate and the server.

VT100 reflow. When the window or the font changes the column count, the primary screen reflows the session. A soft wrap joins and wraps again to the new width. A hard newline stays a break. A wide glyph stays whole. Color and bold stay on the cells. Lines that leave the window stay in the scrollback. The alternate screen still pads or clips. A zero size keeps the last positive size. A cursor on the last row stays on the last row. The host still hears the new size. No system line moves. This land leaves the working set as it stands. Java keeps the input line as it stands. This client reflows the primary screen when the column count changes. This filing writes the crate. The pin stays `turso` 0.8.3.

Copy chord. Ctrl-Shift-C copies the console highlight. An empty highlight leaves the clipboard as it is. The console writes nothing on that chord. Ctrl-C sends the interrupt, byte `0x03`. Bare Ctrl-C arrives as the window copy event and stays the interrupt. A Shift key that is still down keeps the chord a copy when the C event says shift is clear. No system line moves. Java keeps Ctrl-C as the interrupt. This client copies on Ctrl-Shift-C and writes nothing. This filing writes the crate. The pin stays `turso` 0.8.3.

**Truth.** Every land above is the match. Hex colors, pixel gaps, file keys, version 1, and the named defaults stand. A missing font key on this client is 14. The spinner still runs 8 through 36 by steps of 1. A home file whose version is not 1 is left unread and is not overwritten. `turso` stays 0.8.3 where a land pins it. `chrono` stays 0.4.45 where the stamp names it.

**Reason.** The Java window was already the behavior a person recognizes. Matching it in place, and recording the Rust alternative only where the thread or the file differs, keeps one product. A missing font of 8 rebuilt a size this client no longer uses. A missing font of 20 belongs to the other client.

**Avoidances.** Do not drop a color, a pixel gap, a key, or a default from a land above. Do not use 8 as the missing font. Do not use 20 as this client's missing font. Do not overwrite a version other than 1. Do not open the Java vault file from this client. Do not turn Ctrl-C on the focused console into a copy. Do not send a system-line change without the stanza that names the door and the order. Do not treat a row that writes no crate as already moved.

**semantic-key:** PK-7350

## Binding pass

The smoked mood layer is checked against the Java layer since the minimize-toggle cut, and the rows already on the match table stay as each land wrote them.

A later row is the move when it changes an earlier match. The mood file is the later home for the string and the switch. The wire id is the later rule that each mood send is its own id. The earlier row that called the string session memory stays as that land wrote it, and the home file is the later move. The shell map had still described the older mood door, and the think row had still described the short strip. Those rows now state the smoked shell. Pin OK writes the states. The Java tip on that button still says the mood dialog opens after the pins. The code does not open it. This shell follows the code. The glass uses the selected card's model. The notebook mood-shape key is not this door. The central contract records the think file and the mood file as home files. The crate stays unwritten. The Rust slice waits until a sentence names the catch-up. This pass writes no crate.

**Truth.** The match rows stay as written. A later row wins only by being the later move. Pin OK writes states and does not open Mood, even if a tip still says the dialog opens. The glass uses the selected card's model. The think file and the mood file are home files. This pass writes no crate.

**Reason.** The tip and the code disagreed. Following the tip would open Mood after OK. Following the code keeps OK on the pin states.

**Avoidances.** Do not rewrite an earlier match row in place when a later row is the move. Do not open Mood from OK. Do not treat the notebook mood-shape key as this door. Do not write a crate from this pass.

**semantic-key:** PK-7351

## Catch-up order

The smoked shell slides into the seated crate in three slices, and the minimize-toggle cut stays.

That cut is the seated crate. This pass writes no crate and adds no slice-index row. The outstanding menu is a check of that toggle, the think file, the mood catch-up that begins at the mood-string land, and the Pin tip when a sentence names it. The smoked shell in the shell map is the target. The mood rows stay as each land wrote them. The glass slice opens on the final contract.

Think home. Loading the think file opens the pane when `aetheris-thinking.txt` holds text. A close on the Java side writes `closed: true`, and the next open leaves the pane shut. The two apps share `~/.casserole/aetheris-think.yaml`. The slice reads version 1. It writes `closed` and `height`. It keeps the `cells` map. A version other than 1 is left unread and is not overwritten. A kept height falls between 48 and 2000 and maps onto the seated drag height. The drag floor stays. The fold stays in this window and is not written. Close writes `closed` and hides the pane. Clear still wipes the text log and leaves the pane up. Footer Clear, Connect, and Disconnect still leave the log. A later thought opens the pane and clears the mark. The checkbox restart stays held until the vault gives the card a stable id. The session copy of that checkbox is already seated. This slice writes no minted cell id into `cells`. The window log is a text galley in a scroll. The canvas bounce of the glyph stays on the Swing page. This slice leaves the seated think strip as it paints.

Mood canvas. The canvas footer rows are a row of five tool labels. The switch joins that row in this slice, and a separator lands with it, after To terminal. The same slice updates the footer hit test and the membership test with the row. The Mood button stays off this slice. The file is `~/.casserole/aetheris-mood.yaml`, version 1, with `line`, `on`, `width`, and `height`. A switch flip keeps the size pair. A version other than 1 is left unread and is not overwritten. A missing file means no string and a switch that is off and grey `#9E9E9E`. With a string, left is off `#E53935` and right is on `#00FF66`. The inspector shows `mood: (none)`, or `mood: off` and the string, or `mood:` and `shape:` while the switch is on. The yellow `#ffff55` line sits above the inspector while the switch is on. Its width stays locked so a long string leaves the saved ratio where it is. A canvas send, while the switch is on and the line is non-empty, places the saved string and then the shape line after the host line and before the turns. A body that does not fit drops both lines and the send continues. The layer stays on. Export and Clear turns leave the file. The mood record is not a card field, not a wire column, and not a vault row.

Mood glass. The button joins the tool bar in this slice. The same slice grows the tool row and updates the footer hit test with that button. The transcript is drawn on the window thread. The simple thread still holds. The Java glass stays the private terminal. Each send mints a 16-byte id outside the cell mint. The session keeps every mood id. Close clears the current id and keeps the set. A late frame for an older id stays off the cell path. Those ids are consumed before scope routing and before a fault is seated. A mood frame does not accept a reply, an end, a fault onto the deck, a thought, or a thought close. Scope routing closes the thought on Reply, End, and Fault even when the cell is not a card, so a mood end would close the open canvas thought if it were routed that way. A non-busy error for an id that is not a card still builds a fault seat, and that seat can open the canvas slide. `cell-busy` stays a status word and does not fail a canvas cell. The mood send does not set the card running and does not plan the chain. Planning the chain returns busy when any card is running. The server holds a frame id until that execute finishes. A second frame on the same id answers `cell-busy`. A different id may run beside it. The burner bit is the card bit. A normal pool card passes the same door as Run. Think stays off on the glass. The spinner uses its own clock. A reply with no mood line asks again. The third miss paints `keyword only. mood line` and leaves the dialog open. A wire miss closes the dialog and paints the slide. The saved string and the switch stay. The layer stays on. The pane rises above the footer when a reply sets the string. Past half the dialog the pane scrolls. The resting footer is the spinner, Approve, and Cancel. Approve is wide enough to show the label. The three-em growth stays the Swing fit. The Rust button sizes to the word Approve. Kept width runs from 480 through 4000. Kept height runs from 320 through 4000. Otherwise the dialog opens at 760 by 520. The place on screen is not kept. One send is in flight. The control line is the line that tells the model to put more tokens on the mood line when the user asks for more detail. The pluck is that same pluck. The thin header from the mood-string land stays on its row.

The order is the hand check, then think home, then the mood canvas, then the mood glass. The hand check of the seated minimize toggle is not a crate slice. It comes before think home changes the loader. Think home is the file, `closed`, and `height`. The mood canvas is the file, the switch, the inspector, the yellow line, and the two system lines. The mood glass is the button and the final glass, in one slice. The Pin OK tip stays a Java line. The pin commit writes the states. The tip still says the dialog opens. This order leaves that tip until a sentence names it.

The pane that stays until Close, the yellow thought text, and the `v` and `^` fold are already seated. This order leaves them seated. The next slice waits until a sentence names it.

Think home is seated. Close writes closed. A height from 48 through 2000 maps onto the drag height. The cells map stays. The mood canvas waits until a sentence names it.

The cell box seats from the map. A matching cell id brings the five fields onto the card when the vault opens. A checkbox, a level, a model id, a Grok flag, a pool flag, the next turn, and a duplicated group write those fields back. A different id stays off. The console think string stays on the console. Opening the dialog does not write the map. A card whose id is absent from the map stays off until Thinking is checked on that cell.

The clear mark wears sapphire. C on the thinking grip uses the sapphire face P wears while the pin is off. The letter is white. The one-pixel white bevel stays. The pin box does not turn C green. The seat stays on the east of the grip. Clear still wipes the log.

The connection bar folds on connect. The fields start open. `v` folds them. `^` brings them back. Connect folds the bar. Disconnect opens it. A resize while connected folds it. Connecting leaves the bar. A canvas line leaves the bar. The status line and the plate tabs stay. The choice is not saved.

The reply window floats over the canvas. The window sits above the Canvas tab and the P strip. It is the modal. A click on the tab, on P, or on Run stops on the window. The glass sits above the canvas and below the window. P's tip stays with the canvas. Find, Close, and the saved size stay.

The folded bar sits on one row. When the connection bar is folded, the status sits to the right of the unfold mark. The SSH and Aetheris tabs sit to the right of that status. Connected wears a green face, white letters, and a beveled edge. Unfolded, the fields, the status row, and the tabs stay as they stand.

The inspector header sits above the cards. The facts line wraps above the pane. The Inspector label is its own row. Soft wrap, the column count, and the line length are the next row. The mood line wraps above the cards. The card text does not take a selection or the keyboard. The user list uses the fitted size of the first button bar. That filing writes no crate.

The facts line sits on its own panel. The font fits the column. The width controls snap under that panel. Both size controls stay. The scroll pane is the center. The mood line is the first card. That filing writes no crate.

Each Expanded View check stays with that cell. The window caption is `Aetheris Rust`. The thin status bar sits at the bottom of the Aetheris pane. The seat is `aetheris-expanded.yaml`. It is not a vault column.

Hide Collapsed and Show Collapsed sit on the tool row after Mood. Hide takes every folded card off the canvas. Show puts those cards back, still folded. The choice is not saved. The Java buttons follow on the next land, on the second button row, with the same hide and show and the same unsaved choice.

Hide Collapsed and Show Collapsed keep their choice. Hide writes the flag. Show clears it. A missing file starts on Show. The seat is `aetheris-hide.yaml`. It is not a vault column. Java follows on the next land and reads the same file.

Java shows the token ring. The estimate sits on the south of the inspector. T opens the ring. The ring compares that count to the selected model's context window. Ollama rows share the session num-ctx. The crate stays unwritten. Java then reads the file budgets. `application.yaml` holds the Grok windows and the pool window. Pool is 256000. Ollama rows still share the session num-ctx. The crate stays unwritten.

This client shows the token ring. The estimate sits on the south of the inspector. T opens the ring. Ollama rows share the session num-ctx. Grok and pool windows come from `application.yaml`.

This client shows the user combo. The pull-down sits on the east of the footer. The spinner sits west of it while a card is running. A quiet deck hides the spinner. The combo stays. The same user keeps the open vault. A different name binds that vault.

This client shows the remembered chooser. The cell import keeps the dialog bounds and the last folder on the shell file. A cancel writes the bounds and leaves the folder. An approved file writes the parent folder before the read.

This client shows the VT100 shell. The later buttons, the splits, the paste editor, the font spinner, File and Help, and the exit confirm sit on the SSH plate. The first paint uses font 14. A missing font key is 14, not 8 and not 20. The spinner still runs from 8 through 36. A missing maximized key stays maximized.

This client shows the shell finesse. Each VT100 pane keeps its own thinking strip at the top of that pane. The SSH plate wears the status strip. A reply fills the row between the input and the reply with the dark divider. Confirm exit remembers its origin.

This client shows the session think. A dismissed Model Config keeps the think string. Each SSH session slides its own strip down from the top of that pane.

This client shows the clear button. The Clear button owns the pointer on the thinking grip. An open SSH strip leaves the console three pixels under that button.

This client shows the SSH mood. The Mood footer carries SSH Mood Active. The box starts unchecked, so an SSH session stays as it is. When the box is checked and the mood switch is on, each connected SSH pane takes that mood in its chat session. The status strip says `mood active`. The mood string stays off the console.

This client shows the thinking fold. Each SSH thinking pane folds and unfolds on its own. An open strip leaves the console three pixels under that pane's Clear button. A folded strip keeps the grip and one row, and the console follows that button. A closed strip leaves the console at the pane top.

This client shows the folded menu. While SSH is showing and the connection bar is folded, File and Help sit on the tab row after a divider that follows the Aetheris tab. An open bar returns them to the top of the window for SSH and for Aetheris. The Aetheris plate keeps the menu at the top.

This client shows the connect string. While the connection bar is open, the connect word sits to the right of Font size. Connected wears the green chip and white letters. The tab row follows that bar.

An established SSH session keeps the mood. The host line stays first. The mood string and the activate line follow it in that session's conversation window. The turns stay. A clear removes the pair. Chat start keeps the seated mood. The crate did not move. No system line is added beyond that seating.

The inspector mood scrolls with the cards. The yellow mood string is the first card in the inspector scroll while the switch is on. A scroll down carries it out of view. It stays on the visible column while the turn text scrolls sideways. No system line moves.

The folded Aetheris bar carries the menu. While the Aetheris plate is showing and the control bar is folded, File and Help sit after a divider that follows the Aetheris tab. An open bar keeps them at the top. The SSH folded row stays. No system line moves.

The Swing Model Config host list is checked. Gemma is on the list. The earlier miss was the host pointer. No source moved. The earlier model-list behavior stays. No new plate.

The window reflows the session. A column change joins a soft wrap and wraps the text again. A hard newline stays a break. A wide glyph stays whole. Color and bold stay. The alternate screen still pads or clips. No system line moves.

The source prose is the program a person reads. Each spec tag on a type names its spec file. A picture stays a picture. A bug id on the tag is a hint, not a key. No system line moves.

The console exports the conversation session. Export Session on the VT100 menu writes this connection's whole message table to a `.toon` file. Cancel writes nothing. The session stays. Canvas Export stays a `.aetheris` file. The Swing menu stays. The session returns the table. The shell wraps it. The shell answers `session_export:` on channel A and does not paint the grid.

The latest canvas thinking line stays above the footer. The pane stays on the latest line. That line is whole. One line of the thinking field stays under the text. The VT100 strip stays as it stands. No system line moves.

Shift-Enter adds a line in the VT100 console. Enter sends the typed line. Shift-Enter adds a newline and does not send. Enter writes CR. Shift-Enter writes one LF. A Shift key that is still down keeps the line open when the Enter event says shift is clear. That behavior is checked and stands. In the chat, the newline stays in the line. Enter still sends. A blank line in the middle stays in the send. A line feed after a carriage return stays swallowed. The shell inserts a newline on a lone LF and does not submit. No system line moves.

A fold of the thinking pane keeps the typed line. Folding the thinking pane makes the console taller and the typed line stays on the last row. Unfolding makes the console shorter and that row stays. The lines that no longer fit move into the scrollback. A cursor off the last row keeps the older pad and clip. No system line moves.

The VT100 strip names the console mode. When the VT100 console is in focus, the wire word is VT100 or PTY. The word comes from `ui.connection.mode`. The token `pty` paints PTY, and a connect asks for `xterm-256color`. A missing token or any other token paints VT100, and a connect asks for `vt100`. The Aetheris strip keeps AMWP or raw. The link word, `mood active`, the vault, the air-gap, the engine, the east name, and the token estimate stay. The status line that carries those facts is checked and stands. No system line moves.

A folded thinking strip is one row. A folded pane is `^`, the drag, C, then Close. A wider window lengthens the drag. The three buttons keep their size. An open pane keeps the grip and the text row. The console stays three pixels under C. The canvas rises with the shorter row. No system line moves.

Tools shows the thinking pane. Tools sits on the main menu between File and Token Calc. Show Thinking reveals the thinking pane of the plate in front. On the VT100 plate the focused session strip opens. On the Aetheris plate the canvas thinking pane opens. A closed pane rises. A folded pane opens so the text is visible. The log stays. The closed mark on the file stays. The other plate stays. A parked pane stays. Java keeps its menu as it stands. The Rust body stays yellow. No system line moves.

The VT100 think text wears the lighter sapphire. The body is selection sapphire lightened 33 percent toward white. The bytes are 115, 174, and 255. The notebook body stays yellow. The model tag stays cyan. The clear mark stays the selection sapphire. The terminal reply stays bright yellow. The crate still paints the yellow body. The Rust slice waits until a sentence names the catch-up. No system line moves.

The VT100 strip shows the focused session tokens. The focused pane's console text is the count. Another pane shows its own count. Token Calc opens the token window. No system line moves.

The console keeps the keyboard after Model Config. Closing that modal returns the keyboard to the chat pane that opened it. A parked pane that opened it becomes the focused pane again. A cell modal leaves the console keyboard where it is. No system line moves.

The pinned taskbar keeps the Eye of Horus. The title bar already wears that mark. A pin reads the icon stored in the executable. The executable carries the same mark at 16, 32, 48, and 256. No system line moves.

The console menu carries the nine lines. Open as URL, Copy, Paste, Select All, Clear Buffer, Page Up, Page Down, Line Up, and Line Down sit ahead of Import and Select model. The focused grid and a parked pane share that menu. The card menu stays. The paste keys still open the editor. No system line moves.

A new chat starts blank. No pool turn is required for that rule. No SSH session is required for that rule. No system line moves beyond the blank-host stanza.

A pool session carries the mood on the porch. The crate did not move. No pool turn is sent by the recording. No system line moves beyond that porch pair.

An SSH Grok think opens that pane. The Grok call streams while the switch is on. The nil-cell sink carries the reasoning content. The strip gate opens on the same word. That behavior is checked and stands.

An SSH prompt carries the mood while it is on. Off removes that pair. The crate did not move. That behavior is checked and stands. No system line moves beyond that pair.

Each SSH pane keeps its own thinking size. A fold stays on that pane. A new pane starts at the file height. That behavior is checked and stands. No system line moves.

Each SSH pane slides its own thinking strip. One pane does not shut the other. The notebook strip stays. No system line moves.

A restored close keeps the size and the position. A maximized close stays maximized. A disconnect while Aetheris is showing returns to Aetheris on the next connect. A close on SSH returns to the SSH tab. No system line moves.

The mood canvas is seated. The switch keeps the line. A send carries the shape line. The Mood button stays off this slice. The mood glass waits until a sentence names it.

The mood glass is seated. The Mood button sits after the switch. A send mints its own wire id. The dialog keeps a saved string. The Pin OK tip waits until a sentence names it.

The mood glass stays inside the window. The center is the text window. The button row stays on the screen. The Pin OK tip waits until a sentence names it.

A saved mood is already in the field. The mood send carries those tokens in the header. Clear mood drops the string and the chat. The Pin OK tip waits until a sentence names it.

A canvas send seats the host sentence, then the mood string, then the activate line. The server replaces the host sentence. The mood stays. The Pin OK tip waits until a sentence names it.

Clear mood asks before it drops the string. Yes clears. No leaves the mood. Yes and No show their words. The Pin OK tip waits until a sentence names it.

OK writes the pin states. OK does not open Mood. The tip says that. No system line moves. The working set stays as it stands. The pin edge waits until a sentence names it.

The mood desk, behind the mood door, seats the canvas full push and the SSH conversation session. A stored pair is adopted when the session opens. A connect announces the seat, including a clear when the SSH seat is off. Canvas, group, and pipe: empty host seat, then the mood string, then the activate line, then the turns, while the switch is on. The server replaces that empty seat. SSH: pinned host, then the mood string, then the activate line, then the turns, while the checkbox and a saved line are set. Glass: the shaping line, then the pair when the field holds a line. Pool session start: the pair is the porch rules and the host stays out. The crate stays unwritten. This client waits.

The conversational VT100 stand-up is the slice spec the stand-up section points at. The binary name is `aetheris-client`. This section stays the citation spine. The slice spec does not copy these rows.

**Truth.** The order is the hand check, then think home, then the mood canvas, then the mood glass. The minimize cut stays. Heights 48 through 2000 map onto the drag. Mood colors are grey `#9E9E9E`, off `#E53935`, on `#00FF66`, and the line is `#ffff55`. The glass opens at 760 by 520 unless a kept size is inside 480 through 4000 wide and 320 through 4000 tall. A missing font on the VT100 shell is 14. The first paint uses 14. Pool's context window in the budget file is 256000. The taskbar icon is the Eye of Horus at 16, 32, 48, and 256. The binary name is `aetheris-client`.

**Reason.** Landing the glass before the file and the switch would open a dialog with no saved string and no switch to turn on. Using 8 as the first paint rebuilt a size this client no longer uses. Using 20 would copy the other client's missing font.

**Avoidances.** Do not add a slice-index row for the order pass itself. Do not change the loader before the hand check of the minimize toggle. Do not route a mood frame through the cell path. Do not open Mood from OK while the tip still lags the code. Do not use 8 or 20 as this client's missing font. Do not copy these rows into the VT100 slice spec. Do not overwrite a think or mood file whose version is not 1.

**semantic-key:** PK-7352

## Shell map

A pure-Rust window carries every requirement of the SSH client and the Aetheris canvas so the metaphor matches that Java app and keeps the behavior the app already has.

Pure Rust means Rust libraries only. A binding to C sits outside this lane. The functional spine stays the rule for what the host already does. This map is the shell a person touches. It names no build and it writes no crate.

**Truth.** The shell map is the touched surface, written in Rust libraries only, and it does not by itself name a build.

**Reason.** Reading the Java window as the requirement list keeps the metaphor honest without turning a C binding or a crate pin into part of the map.

**Avoidances.** Do not treat a C binding as inside this lane. Do not read this map as a build order. Do not let this map replace the functional spine where the host rule is the cited page.

**semantic-key:** PK-7353

## Shell

The shell is one frame a person recognizes, and the font size used when none is stored is 14.

The window is one frame titled Aetheris. The icon comes from the casserole mark, scaled. The minimum is 640 by 480. The default is 1024 by 968. That geometry is jazz. The stack carries: a connection strip on top, then a vertical split that is one-touch and continuous, with resize weight 0, above the product tabs.

The product tabs are SSH and Aetheris. The Aetheris tab stays disabled until AMWP answers. Close on a tab restores that tab. A saved Aetheris tab waits for Connect, then takes focus, then lands on the newest turn. Scope A frames from one SSH user go to that user's canvas.

The File menu is New Term, Close Pane (Ctrl+Shift+W), Import, and Exit. The Help menu is Help, and About Aetheris. About names the shell, the SSH terminal, and the host. The Java stack line in About is jazz.

Help text carries these facts. Connect is host, port, user, and password. The username combo remembers accounts when Remember password is on. New Term splits the focused pane. Font size applies to every pane and to the canvas type. The spinner runs from 8 through 36, step 1. When the size is missing, this client uses 14. Paste and Import follow the paths below. Restart always starts with one pane. On the server, Esc toggles `cmd>` in medium blue. `help` lists commands. `message list` is terse. `message list verbose` is full. `message find by 'text'` scans this session and is case-insensitive. There is one disk-backed login per username. Change the combo before Connect on a new pane.

Splits nest. The divider starts at half. Ctrl+Shift+D splits horizontal. Ctrl+Shift+E splits vertical. Ctrl+Shift+W closes the focused pane. Close Pane stays off when one pane remains.

Paste: Ctrl+V and Ctrl+Shift+V open the paste editor. OK and Ctrl+Enter insert. Enter in the terminal submits. Send file is the same path. Over 65536 characters is refused.

Import on the console comes from the File menu or the console right-click. The file dialog inserts at the terminal cursor.

The console menu is Copy, Paste, Import, and Select model. Select model opens Model Config for this console. The family is Ollama, Grok, or Pool. The next console turn uses that family. A raw console with no AMWP has no modal. The status says `raw console has no modal`.

Close asks before the window exits. Geometry saves the restored rectangle and the maximized flag. A maximized or iconified frame does not replace the restored rectangle. The product-tab word is saved.

Word motion carries. Left, Right, Ctrl+Left, and Ctrl+Right pass through the PTY. The host line editor moves by character and by word. This client does not rebuild that editor. Esc still toggles `cmd>` on the host.

**Truth.** The shell is one Aetheris frame, minimum 640 by 480, default 1024 by 968, with the strip, the split, and the two product tabs. Font size runs from 8 through 36 in steps of 1, and a missing size is 14. Paste over 65536 characters is refused. A raw console reports `raw console has no modal`.

**Reason.** The Java shell already teaches the menus, the chords, the one-pane restart, and the host-owned line editor, and this client keeps that behavior while the missing font is this client's 14 rather than a size borrowed from another client.

**Avoidances.** Do not open a missing font at 8. Do not use 20 as this client's missing font. Do not enable the Aetheris tab before AMWP answers. Do not close the last pane. Do not let a maximized or iconified frame overwrite the restored rectangle. Do not rebuild the host line editor. Do not give a raw console a model modal.

**semantic-key:** PK-7354

## Canvas

The notebook pane is one vault canvas, the vault seeds the name Canvas 1, and this map does not invent an add-canvas control.

An empty state shows a tab labeled Canvas. No add-canvas control was found. A cell branch defaults to `main`.

Run sends the focused turn. A grouped member sends every member, each its own call. The merge cell is not sent. The call keeps the earlier turns the card is allowed to see, then writes the reply. Ctrl+Enter does the same when the main view is open: not the complex filter, and not while the view window is open. One execute is in flight for a cell. The wire rules stay on the functional spine.

Stop cancels the focused turn when it is running. Other group members keep running. The stored reply stays. If nothing is running, the status says `Nothing is running.` Then `Stopped.`

New turn adds an empty card at the end. The cursor sits in the prompt. The prior turn's model is kept. The card starts Included. The canvas lands on that card.

Clear turns deletes every turn on this canvas and leaves one empty turn. Other canvases stay. The button is not an undo. The mood file stays. The thinking log stays. Status reads `cleared turns on {name}`.

Preview rebuilds the inspector for the focused turn. The inspector shows host:, then mood: (none), mood: off and the string, or mood: and shape: while the switch is on. Earlier turns this call would keep come newest first, and an estimated token count. Preview does not send.

The row incrementor keeps that many lines for the prompt and that many lines for the reply. It is saved with the shell. The minimum is 1.

Search finds the first turn whose text contains the field. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change state.

Collapse All and Expand All are this canvas only. A collapsed card keeps a short reply line, or the prompt when no reply exists, to the right of the buttons. The choice is saved per turn. The card button is `+` and `-`. An unreadable turn shows `?`, disabled, labeled `load error`. It cannot expand, edit, or send.

The tool bar starts hidden behind `v` and `^`. The choice is not saved. The bar holds Search, Export, Import, Pull, To terminal, a separator, the mood switch, and Mood.

The user combo is pinned. The tip says it switches users. The selection focuses that connected user. There is one vault per SSH username. The status user line switches with it.

Think is a strip above the footer. It rises from the top of the footer and stays until Close. `v` folds the text. `^` restores it. The fold is this window only. Close writes closed true on `~/.casserole/aetheris-think.yaml` and hides the pane. The next open loads the log and leaves the pane shut. A later thought opens the pane and clears that mark. A version other than 1 is left unread and is not overwritten. The log is `~/.casserole/aetheris-thinking.txt`. It is a continuous scroll. Each block is tagged with the model. A separator sits between blocks. Clear on the pane wipes the pane and that file. The north edge sets the height, and the height is kept on the yaml when it falls between 48 and 2000. The thinking checkbox is kept on that yaml and is copied with the model, including poolOn. The glyph keeps a fixed box. The word thinking and the clock stay put while the glyph turns. The glyph is `#00FF66`. The word is `#00D7FF`. The spinner sits east of the footer, beside the user combo.

The inspector is a horizontal split: canvas, then inspector, resize weight 0.72. The divider ratio is persisted, and restored after the split has a real width. Soft wrap applies to the inspector only. The column count comes from the shell. The default is 80. One-touch tips hide the canvas or hide the inspector. Empty text is `(empty canvas)`.

The error slide is body plus Close. It stays until closed. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog.

**Truth.** Canvas 1 is the seeded name, branch `main` is the default, one execute is in flight per cell, the inspector share is 0.72 with a default column count of 80, think height is kept only from 48 through 2000, and a think file whose version is not 1 is left unread and is not overwritten.

**Reason.** The notebook already behaves this way on the Java window, including the files under `~/.casserole`, and inventing an add-canvas control would add a control the window does not have.

**Avoidances.** Do not invent an add-canvas control. Do not send the merge cell. Do not let Ctrl+Enter run from the complex filter or while the view window is open. Do not treat Clear turns as an undo or let it delete the mood file or the thinking log. Do not let search change state or treat percent and underscore as wildcards. Do not overwrite a think file whose version is not 1. Do not pop a dialog for a mood miss.

**semantic-key:** PK-7355

## Card

A card is one turn, and membership of later context follows the four states rather than the ghost dim.

The header reads `Turn N`. The state buttons are I, E, P, and O. Ctrl+Shift+I, Ctrl+Shift+E, Ctrl+Shift+P, and Ctrl+Shift+O set the armed turn. Included keeps the prompt and the stored reply as later context. Excluded is skipped. Input only sends the prompt and leaves the stored reply out of later context. Output only sends the stored reply and does not send the prompt. File import is refused on Output only. Ghost dim is look. Membership stays on the functional spine.

Burner means ephemeral. Export leaves the text out unless burner bodies are kept. To terminal refuses a burner. A cloud Run on a burner turn is denied. The text stays. Status reads `burner denied`.

View is a read-only reply window. It stays off until a reply exists. The size is remembered for this vault user. The Java title places an em dash before the turn number. Find is Ctrl+F, previous is `^`, next is `v`, then Close, and a count that reads `none` or a count. Esc closes find.

Copy: a copy mark copies the whole reply. It stays off until a reply exists. The prompt stays. The same copy sits on the View window. A busy clipboard says so.

Model opens Model Config. One modal is open at a time. The pull-down chooses the model. The pane shows only that model's options. Thinking is a checkbox for this cell and this model. Ollama is the default. A change updates the cell at once. Pool chats on the Heavy pool and does not use `your-grok-api-key`. An empty target sends to Ollama and is not written back as a name. `grok/` plus a model sends to Grok when `your-grok-api-key` is present. The word `pool` stores pool. Join does not change the field. Typing `grok/` adopts the nearest Grok document, else the standing document, else an empty model at high, and does not open the modal. A bare select-model opens the modal. A family word switches that family and keeps its stored options.

The card menu imports a text file into the prompt. Over 1 MiB is refused. A binary file is refused. Output only is refused. The reply is unchanged. A right-click on the assistant area says `import refused on output`. Duplicate group is on this menu.

The group bar starts hidden behind `v` and `^`. The choice is not saved. The bar holds the execution target, Group, Join, and Ungroup.

**Truth.** State chords are Ctrl+Shift with I, E, P, and O. Ollama is the default model. Pool does not use `your-grok-api-key`. Import over 1 MiB, a binary file, and Output only are refused. View and Copy stay off until a reply exists.

**Reason.** The card already exposes state, burner, view, copy, model, import, and the group bar on the Java window, and the ghost dim is only paint.

**Avoidances.** Do not treat ghost dim as membership. Do not send a stored reply from Input only, or a prompt from Output only. Do not open a second Model Config. Do not write an empty target back as a name. Do not open the modal when the person types `grok/`. Do not change the model field on Join. Do not use a real key value in place of `your-grok-api-key`.

**semantic-key:** PK-7356

## Already on the Java window

Controls already on the app, whose frames the host already answers, are carried, and this reading does not invent a later stage the host does not have.

The functional spine still holds a Rust build off inventing stage behavior the host does not have. A row here can be scratched if the first Rust client is limited to the earlier chrome. This reading does not rewrite the functional spine.

Export writes this canvas to a `.aetheris` file, in TOON. A chooser asks whether burner bodies are kept. Cancel writes nothing. A later import makes new turns and does not replace these.

Import of a canvas adds turns as copies. They receive new ids. Group links and pipe pointers are rewritten. A file over 1 MiB is refused. Existing turns stay.

Pull takes the terminal into a new turn. A highlight wins. Otherwise the last non-empty screen line is used. The new turn starts Excluded.

To terminal writes the focused turn into the terminal session. The reply is used if one exists, otherwise the prompt. A burner is refused. The person confirms before the bytes are written.

Delete removes the armed card. A normal card removes that row. A card in a group removes the whole group and its merge. The rows leave the vault. Remaining turns renumber from 1 with no gap. The button stays off when delete would empty the canvas. Pipe tokens stay as written.

Groups: a member row scrolls sideways only. Width is the main bar plus about 10 pixels. A third member scrolls sideways. The canvas horizontal bar stays off for the column. The group block is center-aligned like a sequential card. The merge card shows the reply only. The inspector shows the member prompts as one user line and the merge reply as the assistant line. Run on a member sends every member. The merge is not sent. Join's label is JOIN plus the model. The algorithm is CONCAT. A hard or soft failure on a member fails the group. Valid replies stay. Failed cells stay blank. The merge stores FAILED.

Duplicate group is on the card menu and on Ctrl+Shift+G. It copies the group to the next place. New prompts and replies are empty. Member shape and models stay. A turn that is not in a group opens a yellow notice.

Pipes: `{{cell[k].output}}` runs as a chain on Ctrl+Shift+F5. A bad index fails when that cell is sent. Pipe tokens stay as written on delete. Status reads `pipe` when a pipe blocks the run.

Pin uses tokens from the pin field. A readable card that misses goes Excluded. An excluded card that matches goes Included. OK writes those states. OK does not open Mood.

The mood switch sits on the tool bar, after the separator and before Mood. It is grey and disabled until a saved string exists. With a string, a click flips on and off and writes the file. Left is off. Right is on. Off is red `#E53935`. On is green `#00FF66`. Grey is `#9E9E9E`. The string and the switch live in `~/.casserole/aetheris-mood.yaml`, version 1, keys `line` and `on`. Width and height live on that same file. A version other than 1 is left unread and is not overwritten. A missing file starts with no string and the switch off. An empty line forces the switch off. Export does not carry the file. Clear turns leaves it. While the switch is on, a canvas send places the string and then the shape line as system lines. The shape line tells the model to activate the mood above and to use that mood as the shape of its responses. The lines are not a cell. The mood glass does not carry the shape line. A body that does not fit drops both lines and the send continues. The layer stays on. A yellow line above the inspector shows the string while the switch is on. The line is `#ffff55`. Its width stays locked so a long string does not push the split.

Mood is titled Mood. The Mood button opens it for the selected card. The glass uses that card's model. Enter sends the line. Each send takes a new wire id. A late frame for an older id stays off the cell. One send is in flight. The dialog footer shows the thinking spinner while a reply is in flight. A reply with no mood line asks again. The third miss paints the yellow slide, says `keyword only. mood line`, and leaves the dialog open. The glass marks you and assistant. The last mood line fills the field. Marks, a space before the colon, the words mood string, and a fenced mood block count. The words "the string" do not. When a reply sets the string, a pane rises above the footer. The height follows the wrap. Past half the dialog the pane scrolls. The resting footer is the spinner, Approve, and Cancel. Approve grows by three ems after the short fit. The label stays Approve. Approve of a non-empty field saves the string and turns the switch on when it was off. An empty Approve writes no string. Cancel keeps the string and the switch. Approve, Cancel, and the title-bar close write the width and height. A kept width from 480 through 4000 and a height from 320 through 4000 is used. Otherwise the dialog opens at 760 by 520. The place on screen is not kept. The control line tells the model to put the mood on one final line of comma-separated tokens, and to add tokens on that line when the user asks for more detail. The app stores that line.

A miss on the glass closes the dialog and paints the error slide. The words start with `keyword only.` A reason follows when one is present. A hard miss stays red. Any other miss is yellow. The saved string and the switch stay. The layer stays on. A format miss on the third try leaves the dialog open. A canvas cell that is running still answers `cell-busy` and does not fail the cell. Mood frame ids are consumed before the cell path. The shaping call uses the card's burner bit. A normal pool card passes the same door as Run.

Grok and pool on the card use the cell target as the door. The host owns the POST and the pool child. This client paints the reply, the thought strip, and the slide. Air-gap forbids a cloud target and does not rewrite the stored target.

**Truth.** These controls are already answered by the host. OK writes pin states and does not open Mood. Mood colors are off `#E53935`, on `#00FF66`, grey `#9E9E9E`, and the inspector line `#ffff55`. A kept mood dialog is 480 through 4000 wide and 320 through 4000 tall, otherwise 760 by 520. The third format miss says `keyword only. mood line` and leaves the dialog open.

**Reason.** The host already answers these frames, so a metaphor that honors the existing app carries them without inventing a stage the host does not have and without rewriting the functional spine.

**Avoidances.** Do not rewrite the functional spine from this list. Do not have OK open Mood. Do not carry the mood file in Export or clear it with Clear turns. Do not overwrite a mood file whose version is not 1. Do not let the yellow mood line push the split. Do not keep the dialog's place on screen. Do not fail a running canvas cell with `cell-busy`. Do not let a mood id fall through to the cell path. Do not rewrite a stored target when air-gap forbids the cloud.

**semantic-key:** PK-7357

## Palette

Close enough for jazz means a person recognizes this dark shell, and exact Swing metal is not the requirement.

Button height near 0.7 and a bevel are the live shell. Defaults, as built:

The frame and the split use `#1e1e1e`. A panel is `#2d2d2d` on `#dcdcdc`. A field is `#1a1a1a` on `#e6e6e6`. A button is `#3c3c3c` on `#f0f0f0`. Status is `#171717` on `#b4b4b4`. The terminal is `#0d0d0d` on `#dcdcdc`. Selection is `#345a8c` on `#ffffff`. The cursor is `#9900d7ff`, and the inactive cursor is `#664080a0`. The menu is `#00ff41`. The chat prompt is `#00c853`. The cmd prompt is `#2196f3`. The scrollbar face is `#C0C0C0` and the arrow is black. Canvas input is `#00ff66` and canvas output is `#ffff55`. The sapphire edge is `#2e86ff`. Medium green is `#3cb371` and reply gray is `#666666`. The inspector is `#121212` on `#dcdcdc`.

The mark on this whole plate is JAZZ.

**Truth.** The plate is the dark shell above, jazz rather than exact Swing metal, with button height near 0.7 and a bevel.

**Reason.** A person recognizes the shell from these inks, and the glyph engine and the exact Swing metal are not what the mark requires.

**Avoidances.** Do not require exact Swing metal on this plate. Do not swap the chat prompt `#00c853` with the cmd prompt `#2196f3`. Do not drop the inactive cursor `#664080a0` or the scrollbar face `#C0C0C0`.

**semantic-key:** PK-7358

## Status words

The status line already says these words, among the connect words, and this client carries the same words for the same events.

The words are: clipboard busy, vault error, raw console has no modal, keyword only, cell-busy, failed, session closed (with a reason when one exists), nothing is running, stopped, stop failed, cleared turns, clear turns failed, deleted group, deleted turn, delete failed, new turn failed, the Aetheris tab needs an AMWP session, the vault is still opening, no-model, air-gap, burner denied, pipe, too-large, running with the id, run failed, grok key missing, grok empty content, unknown provider, unknown role, no tool is invocable, pool auth, pool tool refused, and a Grok HTTP miss on the slide.

**Truth.** Those status words are the shell's vocabulary for those events, including grok key missing and a Grok HTTP miss on the slide.

**Reason.** The line already speaks this list, so a Rust client that honors the shell uses the same words for the same events.

**Avoidances.** Do not invent a substitute phrase for an event this list already names. Do not drop grok key missing, pool auth, or pool tool refused. Do not print a key value beside grok key missing.

**semantic-key:** PK-7359

## 17. Chat first

Inside the VT100 client, the first focus is the host chat conversation. The command layer slides in only after that conversation works.

| Focus | What the person can do |
|---|---|
| Door and stream | One SSH session. VT100. Init-or-raw. Scope T on the pane. |
| Chat | Type a line. Enter sends it. The reply paints. |
| Line | Backspace, Left, and Right edit that line. The host owns the line. |
| Plate | The chat prompt is readable. The terminal is dark, near enough to use, and not a finished theme. |

The command layer belongs to the host. Esc enters it. The prompt is `cmd>` in medium blue, and that mode stays until `chat start`. `exit` leaves. `help`, `message list`, and `message find` are the verbs the Java help text already names. This client does not rebuild those verbs. When the layer is in, the client passes Esc and paints what the host sends.

The client sends Esc as `0x1B` and sends the typed line plus CR. The console keeps Escape and the arrow keys. Tab still leaves the console. A bare Esc on the frame that still holds the console writes `0x1B`. Escape must not leave the console before the family word can follow.

The host already owns model selection. A family word, `grok`, `ollama`, or `pool`, switches that family and keeps the stored options. The client passes Esc and the line, and paints what the host writes. A model id stays closed. There is no model-id argument and no Rust modal. A bare select-model stays the Swing modal. This client keeps the grid. The window label still says Ollama.

The console keeps a stored grok id. An empty id takes the standing row in `aetheris-grok.yaml`. A missing row takes the factory id `grok-4.3`. Status and the turn read that id. Resolving it does not write the file. An API key is not read into the seed. The failure this closes is a status of `model grok/` with an empty id, and a following turn that reports that no model is defined. The client still only sends the typed line.

The same window then carries the Aetheris plate. The rules below are the plate that stands after that chat focus. The primary-goal account still holds: one login, Scope T on the emulator, Scope A reserved for the canvas, and the Turso file unopened. This focus does not add a crate, a `Cargo.toml`, or a version pin. The window work stays open.

The Aetheris tab waits for the mux. The plate starts as a dark empty frame, and Scope A is not painted on the terminal grid. Once the body is in, the Scope A body shows on the Aetheris plate. The notebook is not part of this client. The empty canvas sits on that plate. The init facts can sit with it. Cards then sit on the plate. One turn is Turn 1. Adding the next card leaves Turn 1 in place.

New appends the next prompt and scrolls the canvas to the newest card. The Turso file stays shut. The vault proof stays the gate.

The four state letters sit on the plate. I keeps the prompt and the reply. E keeps nothing. P keeps the prompt. O keeps the reply. Clear leaves one empty Included card.

The inspector shows the working set for the focused card, newest block first. It does not send. A letter click keeps that card. Exclude marks that card and leaves the others. A prompt that already holds the keyboard does not take the pane back. The focused Included reply paints on the inspector when it is in hand. The send keeps the focused prompt.

Three Included single turns paint newest first. The focused card is its prompt. Each earlier Included card is the user row with its assistant row under it. Twelve hyphens sit between those blocks.

Member cards of one group sit together. Run on a member sends every member, one after another. The merge card is not sent. The inspector shows the member prompts as one user line and the merge reply as the assistant line. On the canvas, members of one group sit on a sideways row. Each member is its top bar plus 10 pixels. The merge stays the column width under that row. The block takes the column. The canvas sideways bar stays off. A member stays at width 20.

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows `no group selected` in yellow on the status line. The group row starts hidden. `v` shows it and `^` hides it. That choice is not saved. Group, Join, and Ungroup sit on that row.

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says `pipe`. The token stays in the prompt.

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. The folded line stays inside the column. The divider bar stays on the share. A click on a folded card arms that card. The sapphire moves. The fold stays.

Every card wears a one-pixel line. The armed card is sapphire. A focused merge lights its group. The running card wears the line while that run is in flight. The line leaves when the run ends. The running card counts the seconds beside thinking. The count starts when the run starts. The shape is `m:ss`, and `h:mm:ss` once an hour has passed. The clock line leaves when the run ends. The yellow thought text is not part of this client.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu.

The reply opens in a read-only window. That mark also stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. An Ollama run still sends.

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone.

The window opens maximized. The zoom starts at three Ctrl-+ steps, which is 1.3. The window paints the keys already in hand, then commits a budget. Canvas pieces go first. One terminal chunk follows. The rest stays queued.

The bar between the canvas and the inspector moves for this session. The open share stays 0.72. A drag keeps the share until the window closes. The share comes back the next time the window opens. One arrow hides the canvas. The other hides the inspector. The arrow of a hidden pane brings that pane back. The marks are filled off-gray buttons. A triangle sits in each one. The buttons stay on the divider. The width is the bar. A triangle sits inside each button. Collapsed to the right, the inspector column is closed.

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window.

On a Rust UI change the Swing lesson is looked up first: thread management, canvas painting beside a slower chain, and a queue of events with listeners. The Rust shape is reasoned and settled before the crate moves. Reusable components carry that behavior as far as Rust allows.

**Truth.** Chat is a typed line, Enter, and a painted reply, with Backspace, Left, and Right on a host-owned line. The command layer is host-owned: Esc is `0x1B`, `cmd>` is medium blue until `chat start`, and this client does not rebuild `help`, `message list`, or `message find`. Family words switch `grok`, `ollama`, or `pool` without a Rust modal, without a model-id argument, and without writing `aetheris-grok.yaml` or reading an API key. An empty grok id is the standing row, or factory `grok-4.3` when the row is missing. The plate that stands on that same window is the tab, the canvas, the inspector, the groups, and the view, with the sizes, keys, colors, and sentences above. The Turso file stays shut. The notebook and the yellow thought text are not on this client. The window label still says Ollama.

**Reason.** The command layer was sliding in before the console would keep Escape, so the family word never followed. An empty grok id then reported that no model was defined. The plate was built on the same window only after the chat line could be sent and painted. Each later control, the divider, the arrows, the fold, the clock, the pipe, and the view, was added because the card was otherwise the wrong width, the wrong color, or the wrong focus.

**Avoidances.** Do not let Esc leave the console while that frame still holds it. Do not time a bare Esc here. Do not open a Rust model modal or send a model-id argument. Do not write `aetheris-grok.yaml` when an empty id is resolved. Do not read an API key into the seed. Do not rebuild the host verbs. Do not paint Scope A on the VT100 grid. Do not send the merge card. Do not show the canvas sideways bar. Do not delete the last card. Do not drop a pipe token, and do not send a bad `{{cell[k].output}}` index. Do not use a popup for a mood miss. Do not paint the yellow thought text from this rule. Do not open the Turso file. Do not save the group-row choice. Do not add a package manifest or a version pin from this focus.

**semantic-key:** PK-7360

## 23. UI design rules

The Java UI is the mirror, with the exceptions that rule names. The child constitution holds these design rules.

The Aetheris look is the inspiration. The closer feel, on the SSH client and on the Aetheris surface, is the interactive Grok text interface: a graphic shell that still behaves like an operating-system console. The canvas may still be fancy. The first slice stays the SSH VT100 console pane.

This rule does not add a crate. It does not add a new shared rule outside this silo.

**Truth.** Match the Java UI where this rule does not name an exception. Take the feel from the interactive Grok text interface on both the SSH pane and the Aetheris surface. The Aetheris look inspires. The canvas may be fancy. The first pane is the SSH VT100 console.

**Reason.** A Rust window that ignores the Java surface will not be the same product, and a window that copies only the Java toolkit will miss the console feel the SSH client and the Aetheris surface are both supposed to have. The first slice stays the console so the fancy canvas does not lead.

**Avoidances.** Do not drop a named exception to force a pixel match. Do not let the canvas replace the SSH VT100 console as the first slice. Do not treat the Aetheris look as a ban on the console feel. Do not add a crate, or a new shared rule, from this statement.

**semantic-key:** PK-7361

## 26. UI build modifier

On every Rust UI change, look for the Swing lesson, reason the Rust shape, and settle that shape before the crate moves.

The child constitution holds this modifier. The borrow from Swing is thread management, canvas painting beside a slower chain, and a queue of events with listeners. The pieces are reusable components, as far as Rust allows. The one ordinary read thread still holds.

This modifier does not itself add a crate. It does not add a shared rule number. It does not change the Java client.

**Truth.** No Rust UI change moves the crate until the Swing lesson has been looked up, the Rust shape has been reasoned, and the solution has been settled. Threading, painting the canvas beside a slower chain, and a listener queue are the borrowed lessons. Components stay reusable within what Rust allows. The simple read thread remains.

**Reason.** Swing already paid for those three lessons. Moving the crate first, and discovering the lesson after, rebuilds the same stalls: a slow chain blocking paint, events with nowhere to wait, and a thread model invented per screen.

**Avoidances.** Do not edit the UI crate before the lesson, the reason, and the settled shape are written. Do not drop thread management, side-by-side canvas paint, or the listener queue when the Swing screen had them. Do not make a component that only one screen can use, where Rust can share it. Do not replace the ordinary read thread from this modifier. Do not treat this modifier as a crate change or as a Java change.

**semantic-key:** PK-7362

# Folded menu on the Aetheris plate

While the Aetheris plate is showing and the control bar is folded, File and Help sit on that same row, after the Aetheris tab, with a divider between the tab and the menus.

**semantic-key:** PK-7363

## The folded row

The folded Aetheris row is the unfold mark, the status, the SSH tab, the Aetheris tab, a vertical divider, then File and Help.

An open bar keeps File and Help at the top of the window, for the Aetheris plate and for the SSH plate. The folded SSH row stays the seat it already has: the same divider, then File and Help, while SSH is showing.

File and Help stay one menu. The menu is not painted twice. The other client keeps File and Help on the window menu bar. This plate does not move that bar.


**semantic-key:** PK-4645 PK-4646 PK-4647 PK-4648 PK-4649 PK-4650 PK-4651 PK-4652 PK-4653 PK-4654 PK-4655 PK-4656 PK-4657 PK-4658

## What this plate does not move

This plate does not add a side move and does not add a wire move. It does not open a vault column. It does not retouch Open as URL, Copy, the Paste menu line, Select All, the Clear Buffer menu line, Page Up, Page Down, Line Up, or Line Down. It does not retouch the user combo, the remembered chooser, the shell, the clear button, the console mood, the thinking fold, the connect word, the console mood memory, or the inspector mood.

The check does not open a window and does not start a session.

**Truth.** On a folded Aetheris bar the row is unfold mark, status, SSH tab, Aetheris tab, vertical divider, File, Help. An open bar returns File and Help to the top. The folded SSH row is unchanged. The menu is painted once.

**Reason.** File and Help lived at the top, and on the folded SSH row, while a folded Aetheris row stopped at the tab. The operator could not reach the menu from that row without opening the bar, and painting the menu in both places would have made two menus.

**Avoidances.** Do not paint File and Help twice. Do not move File and Help off the top while the bar is open. Do not change the folded SSH row. Do not move the other client's window menu bar. Do not add a wire move or a vault column on this plate.

**semantic-key:** PK-7364

# The arrow buttons stay on the divider and the width is the bar

The buttons stay on the divider. The width is the bar. A triangle sits inside each button. The pair stays centered. The fill stays off-gray.

**semantic-key:** PK-7365

## The bound

The button width is the bar, 11. The height stays 14. The pair stays centered. The fill stays off-gray. The edge stays the lighter line. The triangle margin is 2, so the icon fits the narrower button.

A click still hides that pane. The other button still brings the parked share back. A drag on the rest of the bar still moves the share.

The other client keeps the one-touch control inside the divider. The bar is filled first, and the buttons are drawn on that bar. The button does not cross into the panes. The triangle fits the button.

Collapsed to the right, the inspector width stays zero. The label stays unplaced. The open share stays 0.72 when the key is absent. The door write stays 400 ms.

One window thread still holds.

**semantic-key:** PK-7366

## What stays off this plate

Burner, the yellow thought text, the minimize toggle, and the vault file stay off this plate. A saved tab and close-restore stay off this plate. The shell column stays off this plate. Duplicate group and pipes stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. This plate is not a host line and not a second runtime. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7367

## The shell column recorded beside this plate

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window.

**semantic-key:** PK-7368

## The saved tab recorded beside this plate

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone.

**semantic-key:** PK-7369

## The group copy recorded beside this plate

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows no group selected in yellow on the status line.

**semantic-key:** PK-7370

## The chain recorded beside this plate

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says pipe. The token stays in the prompt.

**semantic-key:** PK-7371

## Delete recorded beside this plate

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says deleted turn on Canvas, or deleted group on Canvas.

**semantic-key:** PK-7372

## Collapse recorded beside this plate

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window.

**semantic-key:** PK-7373

## The brief recorded beside this plate

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays.

**semantic-key:** PK-7374

## The folded click recorded beside this plate

A click on a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays.

**semantic-key:** PK-7375

## The copy mark recorded beside this plate

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. The reply window stays unopened on that plate. The door file stays.

**semantic-key:** PK-7376

## The card menu recorded beside this plate

A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu.

**semantic-key:** PK-7377

## The miss recorded beside this plate

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog.

**semantic-key:** PK-7378

## The reply window recorded beside this plate

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user.

**semantic-key:** PK-7379

## The reply window bound recorded beside this plate

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark.

**semantic-key:** PK-7380

## Burner recorded beside this plate

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is burner denied. An ordinary card leaves the notice closed. A local run still sends.

**semantic-key:** PK-7381

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** Each arrow button is 11 wide and 14 tall, the width of the bar, centered, off-gray, with a triangle inset by 2. A closed right inspector stays width zero. A missing open share is 0.72. The door write stays 400 ms.

**Reason.** A button wider than the bar crossed into the panes, and the triangle no longer fit once the control was kept on the divider.

**Avoidances.** Do not let a button cross into a pane. Do not change the height from 14. Do not inset the triangle by less than 2 on this narrower button. Do not place the closed inspector's label. Do not replace a missing open share with anything but 0.72. Do not change the 400 ms door write.

**semantic-key:** PK-8208

# The arrow marks are filled off-gray buttons and a closed inspector has width zero

The marks are filled off-gray buttons. A triangle sits in each one. The pair stays centered on the bar. Collapsed to the right, the inspector column is closed. No text sits past the bar.

**semantic-key:** PK-7382

## The buttons

The buttons are wider than the bar so they read as controls. The fill is off-gray, lighter than the bar. The edge is a lighter line. The triangle is light and sits inside the button. The upper button points at the canvas. The lower button points at the inspector.

A click on the button hides that pane. The other button brings the parked share back. A drag on the rest of the bar still moves the share.

The other client turns one-touch on. The divider is 11 pixels. The bar is filled, the edge is stroked, and then the one-touch buttons are drawn: a filled control with a triangle fitted inside. The pair stays centered on the bar.

**semantic-key:** PK-7383

## The closed pane

Collapsed to the right, the inspector width is zero. The label and the body are not placed. The gap on that side drops, and the bar meets the edge. An open share still gives the inspector its room. The open share stays 0.72 when the key is absent. The door write stays 400 ms.

One window thread still holds. The read thread stays the socket worker.

**semantic-key:** PK-7384

## The bound recorded beside this plate

The buttons stay on the divider. The width is the bar. A triangle sits inside each button.

**semantic-key:** PK-7385

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** Each arrow is a filled off-gray control, lighter than the bar, with a lighter edge and a light triangle inside. The pair stays centered. A click hides that pane. The other click restores the parked share. Collapsed to the right, the inspector width is zero and its label is not placed. A missing open share is 0.72. The door write stays 400 ms.

**Reason.** An unfilled mark did not read as a button, and a closed inspector still placed its label past the bar, so the pane looked open.

**Avoidances.** Do not leave text in a closed inspector. Do not drop the triangle out of the button. Do not stop a drag on the rest of the bar from moving the share. Do not replace a missing open share with anything but 0.72. Do not change the 400 ms door write. Do not open a second runtime.

**semantic-key:** PK-8209

# Run and Stop sit on the master bar

Run and Stop sit on the master footer row with one spinner, and the spinner shows only while a card is running.

**semantic-key:** PK-7386

## The master row

The master row, in order, is Run, Stop, New, Clear, Delete, a divider, Collapse All, Expand All, a divider, and v.

Run sends the focused card. Stop cancels the running card and leaves the stored reply on that card. When a later member of the live group is still queued and the mux is up, that member starts. The merge is not failed. When the queue is empty, the merge stays as it stands. When nothing is running, Stop says Nothing is running.

**semantic-key:** PK-7387

## The tool row

The tool row, in order, is the search field, Search, Export, Import, Pull, To terminal, a divider, the mood switch, and Mood.

**semantic-key:** PK-7388

## The spinner and the clock

The spinner and the clock sit on the east of the master row while any card is running. The clock is that running card. A quiet deck hides both. The glyph is green. The word and the clock are cyan. The four glyphs share one box.

This plate does not put a spinner on every card. It does not leave Run on the card. It does not start a second execute while one card is running. It does not add Preview, the row increment, or the user combo. It does not add a vault column or an export field. It does not change the other client.

**semantic-key:** PK-7389

## The send

No side move on this plate. The working set stays as it stands. The send order stays.

The check does not open a window and does not start a session.

**Truth.** The master row is Run, Stop, New, Clear, Delete, a divider, Collapse All, Expand All, a divider, and v. Stop cancels the running card, keeps its reply, and starts the next queued member of that group when the mux is up. One green spinner and a cyan clock sit east of that row only while a card is running.

**Reason.** A spinner on every card, and Run left on the card, split one run across the deck. The footer is the one place the operator starts and stops the focused card.

**Avoidances.** Do not paint a spinner on a quiet deck. Do not fail the merge when a later member is still queued. Do not start a second execute while one card is running. Do not change the working set on this plate. Do not put Preview, the row increment, or the user combo on this row.

**semantic-key:** PK-7390

# A primary click inside the cell border arms that cell

A primary click inside the cell border arms that cell, moves the sapphire focus to it, and leaves the prompt caret where the editor already has it.

**semantic-key:** PK-7391

## The border

The cell border is the outer frame. Reply text of any size is inside the frame, so a click on that text arms the card. A click in the empty interior of the frame arms the card. A folded card arms on that click. A button inside the frame arms the card.

The prompt editor is the other path. That click keeps the caret and still moves the highlight.

The caption stands on the stroke. The part of the caption above the stroke is outside the border, so a click there does not arm the cell. The cell keeps its width. An empty reply pane stays unpainted. The lead stays. The embedded line stays.

The vault pin stays Turso 0.8.3. This plate does not add a south pad, an east pad, a west pad, a smaller cell, or a new clock. It does not change the other client. It does not change the vault. It does not change the lead.

**semantic-key:** PK-7392

## The send

The doors are a canvas Run, a glass send, a group send, and a pipe send. They share this head. The system lines stay the host sentence, then the saved mood string, then the activate line, when the switch sends. The turns follow. The server replaces the host sentence. No side move on this plate. The working set stays as it stands.

The check does not open a window and does not start a session.

**Truth.** A primary click inside the border arms the cell and moves the sapphire line. The prompt click keeps the caret. The caption above the stroke stays outside the border. The cell width stays.

**Reason.** Clicks on reply text, on the empty interior, on a folded card, and on a button inside the frame were missing the arm, so the sapphire stayed on a card the pointer had already left.

**Avoidances.** Do not arm a click on the caption above the stroke. Do not drop the caret when the prompt editor is the path that was clicked. Do not paint an empty reply pane. Do not change the cell width, the lead, or the embedded line. Do not change the send head.

**semantic-key:** PK-7393

# The folded line stays inside the column and the bar stays on the share

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays. The standing model stays the local engine.

**semantic-key:** PK-7394

## The width

The brief is one line. Preferred width and minimum width are 20. It sits in the center of the bar. Line wrap is off. A long line cannot ask the split for the window.

A member row has preferred width 20, so the sum of the cards does not become the column. The row scrolls sideways. A member returns 20, so the row does not set the column width.

An ordinary card uses a finite leftover. A leftover under 20 returns that leftover. A leftover under 0 returns 0, so the pin does not add width. A leftover at or above 20 is capped at the column. An infinite leftover returns 20.

The paint uses a sized label that truncates. The stored first line stays the whole line. Run still sends the stored text. The share stays the number already on the bar. A drag still keeps the share. Collapse does not write a new fraction. The canvas width stays. An open reply still wraps inside the column. A member row still scrolls sideways. The choice stays for this window. The vault file stays.

The other client's canvas column tracks the viewport width. This plate keeps the brief from asking that column for the window.

One window thread still holds.

**semantic-key:** PK-7395

## What stays off this plate

This plate does not write a new divider fraction and does not write the door. Burner, the yellow thought text, the minimize toggle, and the vault file stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. The card menu, the error slide, and Copy stay off this plate. This plate does not rewrite a pipe and does not add an unreadable turn. It is not a second runtime. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7396

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** The folded brief is one unwrapped line with preferred and minimum width 20. A member row is width 20 and scrolls sideways. A leftover under 20 is that leftover, a leftover under 0 is 0, and an infinite leftover is 20. The stored line stays whole. Collapse does not write a new share.

**Reason.** A long folded line was asking the split for the window, so the bar left the share and a member row set the column to the sum of the cards.

**Avoidances.** Do not let a long brief change the share. Do not let a member row set the column width. Do not let the pin add width. Do not truncate the stored text. Do not stop Run from sending the stored text. Do not write the door or the vault on this plate.

**semantic-key:** PK-8210

# The thinking pane rises off the buttons and P stays on the divider

The notebook thinking pane rises from the top of the buttons. Its bottom stays on that frame. P is anchored to the top of the divider and moves with it.

**semantic-key:** PK-7397

## The pane and the divider

The grip is the north edge. The step is twelve pixels, or a quarter of the remaining span, every 16 ms. A settled pane stays on the buttons. A closed pane stays hidden.

The console strip still grows downward from the top. The divider starts at the top of the frame. The Canvas tab is the top of the left column. The fact line is the inspector top row. An empty fact line still takes that row.

P is anchored to the top of the divider and moves with it. A drag on the bar, including over P, still moves the divider. A click on P opens the pin bar. P adds no row and is not a second hit target. The vault pin stays Turso 0.8.3.

**semantic-key:** PK-7398

## The gap the cells use

The cells stop at the bottom of the open pin bar and at the top of the thinking pane. The grip on that top still sets the height. A closed bar or a closed pane gives that space back.

The cells take the gap the pin bar and the thinking pane leave. A folded pane keeps the grip and one row. The cells still stop at that row and at the pin field. The grip on that top still sets the height. A closed bar or a closed pane gives that space back.

The frame assigns the gap. P sets the north edge when the bar is folded. The pin row sets it when the bar is open. The grip sets the south edge, folded or open.

The deck top stays on the pin strip. The pin row sets the north edge when the bar is open. P sets it when the bar is folded. Opening the thinking pane shortens the deck from the south. Folding it gives that height back. The top stays on the bottom of the pin strip.

The Canvas tab and P share one strip. The deck starts at the bottom of that strip. Opening the thinking pane keeps that top and shortens the deck from the south. Closing it gives that height back. A scrolled cell does not paint through the strip.

**semantic-key:** PK-7399

## The send

The doors are a canvas Run, a glass send, a group send, and a pipe send. They share this head. The system lines stay the host sentence, then the saved mood string, then the activate line, when the switch sends. The turns follow. The server replaces the host sentence. No side move on this plate. The working set stays as it stands.

This plate does not cover the buttons, does not move P back onto the tab, and does not change the console strip growing downward. It does not change a caption. It does not edit the other client. It does not add a crate dependency.

The check does not open a window and does not start a session.

**Truth.** The notebook pane grows upward from the buttons by 12 pixels or a quarter of the remaining span every 16 ms, and a closed pane stays hidden. P rides the top of the divider, adds no row, and a drag over P still moves the divider. The deck's north edge is the pin row when the bar is open and P when the bar is folded. The grip sets the south edge. A scrolled cell does not paint through the shared Canvas-tab and P strip.

**Reason.** The thinking pane was falling over the buttons, and the divider sat a row low, so P slipped off the bar while the bar moved.

**Avoidances.** Do not cover the buttons with the pane. Do not grow the console strip upward. Do not give P a second hit target or an extra row. Do not let a drag over P fail to move the divider. Do not let the deck top leave the bottom of the pin strip. Do not let a scrolled cell paint through that strip. Do not change the send head.

**semantic-key:** PK-8245

# One canvas tab shows the init facts and later channel-A text

One canvas tab is labeled Canvas. The init body seats `air-gap` and `num-ctx` on that plate. Later channel-A text stays under that tab.

**semantic-key:** PK-7400

## What stays off the grid

Channel-T bytes still paint on the console grid. A channel-A body stays off that paint.

A payload of 24 bytes or more yields the bytes after the 24-byte prefix. A shorter payload is kept whole. An empty body is not queued.

Init is kept apart from data and error. Keepalive stays off the plate. A frame on any other channel stays off the grid and off the plate.

**semantic-key:** PK-7401

## The two words

The init body is the server facts. The lines are `air-gap:` and `num-ctx:`. The word is `true`, `false`, or `unset`. `num-ctx` is the server integer. Both must be present before the plate seats them. A later init that seats replaces the two words. An init body that lacks either word stays as text under the canvas. The raw init body does not stay beside the seated words.

This client does not read `~/.aetheris.yaml`. It does not parse a turn, a cell, or a vault row.

Data and error bodies append, one chunk at a time, in arrival order. This plate does not reassemble fragments by cell id.

The bytes skip the terminal paint hold. They cross on their own queue as soon as the frame is whole. The read thread still owns the socket. A second runtime is not added.

**semantic-key:** PK-7402

## The plate

The Aetheris tab still waits for the mux. Selecting it shows one canvas tab labeled Canvas. The label is the literal Canvas. The vault name Canvas 1 waits for the file.

When the init body seats, the plate shows the two words, for example:

```
air-gap: true
num-ctx: 4096
```

The word and the integer are the ones in that body. Later text follows those lines, in a monospace frame on the plate color `0x0d0d0d`. An empty canvas, with no seated words and no later text, leaves the dark frame under the tab.

Keys on that plate are not sent. Returning to SSH arms the console once. The words and the later text stay while the session stays, including while SSH is selected. Disconnect clears them and returns the plate to SSH. A new connect starts from an empty canvas.

A raw console, before the mux, leaves the tab disabled and the plate on SSH. No channel-A body is queued.

The chrome label stays the literal Ollama.

**semantic-key:** PK-7403

## What stays held

Turn cards, Run, the inspector, groups, and the vault file stay off this plate. A saved tab, a land on the newest turn, and close-restore stay off this plate. This plate does not write a product-tab key. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. The terminal library, the console plan, and the console pre-plan stay as they are.

The check does not open a window and does not start a session.

**Truth.** The plate is one tab labeled Canvas. An init body seats `air-gap` and `num-ctx` only when both words are present, and a later seating init replaces them. A payload of 24 bytes or more keeps the bytes after the prefix. Data and error append in arrival order. The frame is monospace on `0x0d0d0d`. Keys on the plate are not sent. Disconnect clears the plate and returns to SSH.

**Reason.** Channel-A text was either missing from the plate or leaking onto the console grid, and an init that lacked one of the two words was being treated as seated facts.

**Avoidances.** Do not paint channel A on the console grid. Do not seat an init that lacks `air-gap` or `num-ctx`. Do not leave the raw init body beside the seated words. Do not show keepalive or another channel on the plate. Do not read `~/.aetheris.yaml`. Do not send keys typed on the plate. Do not keep the words after disconnect. Do not enable the tab before the mux has answered.

**semantic-key:** PK-7404

# The latest canvas thinking line stays whole above the footer

When thinking text is streaming on the Aetheris canvas, the pane stays on the latest line, and that line sits whole above the footer.

**semantic-key:** PK-7405

## The line

The latest line is fully inside the clip. It sits above the footer button bar. One line of the thinking field stays under the text. The scroll still follows the stream.

The console thinking strip stays as it stands. The folded row stays one row. The grip stays on the north edge. The other client keeps its own thinking pane. The body on this client stays yellow.

This plate does not move the console strip, does not move the folded row, and does not change the yellow body. It does not edit the other client.

The check does not open a window and does not start a session.

**Truth.** While canvas thinking text is streaming, the latest line is whole, inside the clip, above the footer, with one field line under it, and the scroll follows the stream.

**Reason.** The latest line was falling under the footer, so the operator could not read the line that was still arriving.

**Avoidances.** Do not clip the latest line. Do not let that line sit on or under the footer. Do not drop the field line under the text. Do not move the console strip or the folded row. Do not change the yellow body.

**semantic-key:** PK-7406

# A right-click on the card opens the menu and Import fills the prompt

A right-click on the card opens the menu and arms that card. On the prompt the lines are Import and Duplicate group. On the reply, and on the folded brief, Import stays off and the status says import refused on output. Import reads a text file into that prompt. The reply stays. Duplicate group is the same copy as Ctrl+Shift+G.

**semantic-key:** PK-7407

## The menu

The fold stays. On the prompt the lines are Import and Duplicate group. On the reply, Import stays off, and the status says import refused on output when the menu opens. The folded brief counts as that reply. The other client still offers Import on the folded brief, because that brief is not the assistant pane. This plate treats the brief as the reply.

An empty reply paints no reply area, so a right-click there is the prompt menu. The other client keeps an empty assistant pane. A fault line is not the reply.

**semantic-key:** PK-7408

## The read

Import reads a text file into that prompt. The reply stays. A merge cell refuses with `import refused: merge cell` before the chooser opens. Output only refuses with `import refused: output only` before the chooser opens. Over 1 MiB refuses with `import refused: over 1 MiB`. Exactly 1 MiB is legal. A NUL in the first 8192 bytes refuses with `import refused: binary`. A NUL past that stays legal. A quiet import replaces the prompt and says imported into turn N. An empty file is a legal prompt. Bad UTF-8 takes the replacement character. Cancel writes nothing and leaves the status. A read or chooser miss says import failed and the message.

The chooser is the system open dialog. The crate forbids unsafe code and adds no package. The remembered chooser directory stays on the other client. The prompt stays in memory for this window. This click does not write the door. The vault file stays. The standing model stays the local engine.

Duplicate group on the menu is the same copy as Ctrl+Shift+G. A card outside a group says no group selected in yellow. Import words take the plate ink.

One window thread still holds.

**semantic-key:** PK-7409

## What stays off this plate

The error slide, canvas import, burner, the yellow thought text, and the minimize toggle stay off this plate. The vault file stays. Splits, the paste editor, the font spinner, and a modal stay off this plate. The reply window stays unopened. This plate does not rewrite a pipe. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7410

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** A right-click arms the card and opens Import plus Duplicate group on the prompt. The reply and the folded brief refuse Import with import refused on output. A merge and output-only refuse before the chooser. Over 1 MiB refuses. Exactly 1 MiB is legal. A NUL in the first 8192 bytes is binary. A NUL past that is legal. A quiet import replaces the prompt, says imported into turn N, and leaves the reply. Cancel writes nothing. Duplicate group matches Ctrl+Shift+G.

**Reason.** The card had no menu, so a text file could not land in the prompt, and Import on the reply was replacing the wrong pane.

**Avoidances.** Do not offer Import on the reply or on the folded brief. Do not change the reply on a prompt import. Do not open the chooser for a merge or for output-only. Do not refuse exactly 1 MiB. Do not refuse a NUL past the first 8192 bytes. Do not write on cancel. Do not write the door or the vault. Do not remember a chooser directory on this client. Do not use unsafe code or add a package for the dialog.

**semantic-key:** PK-8211

# A working prompt is green and a reply with text is yellow

The prompt on a working card is green, `#00ff66`. A reply that has text is yellow, `#ffff55`. A reply whose text is blank is gray, `#666666`. An excluded card paints both panes gray, `128,128,128`.

**semantic-key:** PK-7411

## Which ink wins

The open prompt takes green on every working letter. The open reply takes yellow when the stored text is not blank after trim. A blank reply takes the dim gray. Excluded wins, and both panes take the ghost gray.

The other client names those inks input green, output yellow, response gray, and gray. This plate uses the same colors.

The caret stays the cursor color. The borders stay as they are. The folded line stays as it is. This plate does not change the border or the folded brief. It does not change the vault. It does not edit the other client.

**semantic-key:** PK-7412

## The send

The doors are a canvas Run, a glass send, a group send, and a pipe send. They share this head. The system lines stay the host sentence, then the saved mood string, then the activate line, when the switch sends. The turns follow. The server replaces the host sentence. The working set stays as it stands.

The vault pin stays Turso 0.8.3.

The check does not open a window and does not start a session.

**Truth.** A working prompt is `#00ff66`. A reply that is not blank after trim is `#ffff55`. A blank reply is `#666666`. An excluded card paints both panes `128,128,128`. The caret, the borders, and the folded line stay.

**Reason.** The panes were not wearing the colors the operator already reads on the other client, so a prompt, a reply, a blank reply, and an excluded card could not be told apart by ink.

**Avoidances.** Do not paint a blank reply yellow. Do not let a working letter override excluded gray. Do not recolor the caret, the borders, or the folded line. Do not change the send head.

**semantic-key:** PK-7413

# The Clear button owns the pointer on the grip

The Clear button owns its face. The drag handle stops one pixel short of that button, so the grip tip and the resize cursor stay off the button and a press lands on Clear.

**semantic-key:** PK-7414

## The row

The grip allocates the row, then the Clear button, then a drag on the remainder. The drag stops one pixel short of the button, so the two rectangles do not meet. A shared edge would still count as a hit. The button keeps its sapphire face. The notebook strip and the console strip share that grip.

On an open console thinking strip the grip sits on the south of the shown height. The clip stays the pane, so the button is not cut to the grip row. The console cursor advances to three pixels under the button. The advance does not take a hit on the button. A closed strip leaves the console where it stands. The notebook strip does not take that seat.

**semantic-key:** PK-7415

## What this plate does not move

No side move on this plate. No wire move on this plate. The working set stays as it stands. The grip stays the height drag. This plate does not open a vault column and does not edit the other client. It does not retouch Open as URL, Copy, the Paste menu line, Select All, the Clear Buffer menu line, Page Up, Page Down, Line Up, or Line Down. It does not retouch the user combo, the remembered chooser, or the shell.

The check does not open a window and does not start a session.

**Truth.** The drag stops one pixel short of Clear, so a press on Clear is Clear. On an open console strip the cursor starts three pixels under that button and does not hit it. A closed strip leaves the console. The notebook strip does not take the three-pixel seat.

**Reason.** The grip and the button shared an edge, so the resize cursor and the grip tip stole the press that belonged to Clear, and an open console was starting under the button.

**Avoidances.** Do not let the drag rectangle meet the button. Do not treat a shared edge as a miss. Do not cut the button to the grip row. Do not move the notebook strip by those three pixels. Do not move a closed console.

**semantic-key:** PK-7416

# C on the thinking grip wears sapphire

C on the thinking grip wears the sapphire face P wears while the pin is off. Sapphire is `#2e86ff`. The letter stays white. The one-pixel white bevel stays.

**semantic-key:** PK-7417

## The face

The clear button uses the fill P uses while the pin is off. That fill is the armed sapphire. The stroke is a one-pixel white line. The letter is white. The same face paints the notebook grip and the console grip.

The pin box does not turn C green. P stays green while the pin is on and sapphire while the pin is off. C stays sapphire while the pin is on. C stays on the east of the grip and still clears the log.

The other short buttons keep the theme face. The red clear mark on the pin row stays red.

The other client paints C sapphire, the letter white, and a one-pixel white line around the face. C stays sapphire while the pin is on. P turns green while the pin is on. This plate mirrors that face on the grip. The seat stays.

This plate does not move P, does not paint a green C, does not add a row, and does not write the vault. It does not edit the other client.

No side move on this plate. The working set stays as it stands.

The check does not open a window and does not start a session.

**Truth.** C is `#2e86ff` with a white letter and a one-pixel white bevel, on the notebook grip and on the console grip, whether the pin is on or off. P is green only while the pin is on. The pin-row clear mark stays red.

**Reason.** C was taking the theme face, and the pin-on state was being read as a reason to paint C green, so the clear mark no longer matched the sapphire the operator already reads on P when the pin is off.

**Avoidances.** Do not paint C green. Do not let the pin box recolor C. Do not move C off the east of the grip. Do not stop C from clearing the log. Do not recolor the other short buttons or the red pin-row mark.

**semantic-key:** PK-7418

# The running card counts the seconds beside thinking

The running card counts the seconds beside thinking. The count starts when the run starts. The shape is m:ss. Once an hour has passed the shape is h:mm:ss. The digits are `#00D7FF`. The line leaves when the run ends. A quiet card stays clear.

**semantic-key:** PK-7419

## The line

The other client paints three pieces on one line while a run is up. The glyph is `| / - \` in `#00FF66`. The word thinking is `#00D7FF`. The digits are the same cyan. The count starts when the request starts. A missing start reads 0:00. Seconds are two digits. Minutes are two digits in the hour shape. The timer is 120 ms on the event thread. The line hides when the run ends. The work stays on the window thread.

The digits sit on the line the running card already wears. The same cyan. The same 120 ms paint. The start is kept when the run begins. The start clears when the run ends, when it faults, when Stop lands, and when Clear lands. A reply token keeps the count. The digits are a pure function of the milliseconds since that start. The card does not store the digits. One execute is in flight, so the card that is running counts its own start.

Kind `t` stays off the card. The yellow thought text stays off this plate. The door file is not written.

One window thread still holds.

**semantic-key:** PK-7420

## What stays off this plate

Burner, the yellow thought text, the minimize toggle, and the vault file stay off this plate. A saved tab and close-restore stay off this plate. The saved ratio, the shell column, and the hide arrows stay off this plate. Duplicate group and pipes stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. This plate is not a stored thought, not a second thread, not a host line, and not a second runtime. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7421

## The saved share recorded beside this plate

The share comes back the next time the window opens. One arrow hides the canvas. The other hides the inspector. The other arrow brings the hidden pane back.

**semantic-key:** PK-7422

## The arrow buttons recorded beside this plate

The marks are filled off-gray buttons. A triangle sits in each one. Collapsed to the right, the inspector column is closed.

**semantic-key:** PK-7423

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** While a run is up, the line is a green glyph in `#00FF66`, the word thinking in `#00D7FF`, and digits in that same cyan, painted every 120 ms. The shape is m:ss until an hour has passed, then h:mm:ss, with seconds in two digits and minutes in two digits in the hour shape. A missing start reads 0:00. The start clears on end, fault, Stop, and Clear. The card does not store the digits. Kind `t` stays off the card.

**Reason.** The running card had the word and no count, so the operator could not see how long the request had been in flight, and a stored digit would have survived the run.

**Avoidances.** Do not leave the line up after the run ends. Do not store the digits on the card. Do not paint kind `t` or the yellow thought text on this line. Do not start a second thread for the timer. Do not write the door. Do not count a quiet card.

**semantic-key:** PK-8212

# Collapse All folds every card and the stored text stays

Collapse All and Expand All sit beside Delete. Each card carries + when it is folded and - when it is open. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. The standing model stays the local engine.

**semantic-key:** PK-7424

## The fold

Collapse All folds every card on this canvas. Expand All opens every card. One card button folds or opens that card and arms that card. Collapse All and Expand All do not move the focus.

A folded card hides the prompt editor, the reply body, and the fault body. A short line of the reply sits to the right of the buttons. When the reply is empty, that line is the first line of the prompt. The stored prompt and the stored reply stay. Run still sends the stored text. The Run row stays. The think strip stays while a run is in flight.

A copied card starts open. The door file stays. The vault file stays. This plate has no vault write. The other client saves the choice on the turn. This plate keeps the choice for this window.

An unreadable turn, on the other client, shows a disabled question mark labeled load error and cannot expand, edit, or send. That turn stays off this plate.

One window thread still holds.

**semantic-key:** PK-7425

## What stays off this plate

Burner, the yellow thought text, the minimize toggle, and the vault file stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. The card menu, the error slide, and Copy stay off this plate. This plate does not rewrite a pipe. It is not a host line and not a second runtime. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7426

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** Collapse All folds every card on the canvas that is showing. Expand All opens every card. A folded card shows + and hides the prompt, the reply, and the fault, and keeps one short line of the reply, or the first line of the prompt when the reply is empty. The stored text stays and Run still sends it. The choice is for this window. A copied card starts open.

**Reason.** Folding was either dropping the text or leaving the whole editor on screen, so the operator could not see which card held which reply and could not send the stored turn.

**Avoidances.** Do not drop the stored prompt or reply. Do not stop Run from sending a folded card. Do not move the focus on Collapse All or Expand All. Do not write the vault. Do not put an unreadable load-error turn on this plate. Do not start a copied card folded.

**semantic-key:** PK-8213

# The inspector folds a line after the shell column count

The inspector folds a line after the shell column count. A missing count is 80. Soft wrap starts on for this window.

**semantic-key:** PK-7427

## The count

The open count is 80 when the key is absent. A count under 1 is 1. The key is the inspector wrap columns.

Plus and minus write the door at once. The same text does not rewrite the file. Other keys stay. A typed number waits 800 ms. Empty or unreadable text keeps the count.

Soft wrap starts on. The checkbox is not saved. Turning wrap off keeps each line on one row for this window, and the pane scrolls sideways. The choice is not saved.

A hyphen and an identifier that is one token stay one token. The wrap is on words at that character count.

The inspector does not send. The Run text stays whole.

One window thread still holds.

**semantic-key:** PK-7428

## What stays off this plate

Burner, the yellow thought text, the minimize toggle, and the vault file stay off this plate. A saved tab and close-restore stay off this plate. Duplicate group and pipes stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. This plate is not a host line and not a second runtime. It does not save the wrap checkbox. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7429

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** The inspector wraps on words at the shell column count. A missing key is 80. A count under 1 is 1. Plus and minus write the door at once. A typed number waits 800 ms. Soft wrap starts on and is not saved. Wrap off keeps each line whole and scrolls sideways.

**Reason.** The inspector was not folding at the column the shell already uses, and a missing count was not 80, so the pane and the shell disagreed on where a line ends.

**Avoidances.** Do not use a missing count other than 80. Do not accept a count under 1. Do not write the door before 800 ms on a typed number. Do not rewrite the file when the text did not change. Do not save the wrap checkbox. Do not send from the inspector. Do not break a hyphenated token or an identifier that is one token.

**semantic-key:** PK-8214

# A redraw paints first, then one budget of queued work

A redraw paints the plate before it commits. The keys already in hand are on that paint. The commit that follows takes at most eight channel-A pieces, then one terminal chunk. The rest stays queued.

**semantic-key:** PK-7430

## The budget

Channel-A pieces are taken first, at most eight. One terminal chunk is taken after them. Anything still queued waits for the next turn. The order of each queue is kept. Disconnect clears both stashes.

The read thread still holds a terminal burst for 8 ms of quiet, 16 ms of age, or 8 KiB. This plate does not change that hold. Channel A still skips it.

A live session with a backlog waits 1 ms. A live session that is caught up waits 16 ms. No session waits on the window.

**semantic-key:** PK-7431

## The socket does not take the keystroke

A write, a wire frame, and a resize park on a local queue. The window thread sends with a non-blocking send. A full channel keeps the command at the front. A later pump sends that key before the next one. A closed channel drops the queue. The window thread does not block on that send.

Disconnect still uses a blocking send, after the parked commands have been tried. The window thread still never enters the read runtime.

The chrome label stays the literal Ollama. The vault waits. The queues stay in memory. The read thread stays the socket worker. There is no second paint thread and no surface off the window thread.

**semantic-key:** PK-7432

## What stays off this plate

Burner and the think strip stay off this plate. The vault file stays off this plate. A saved tab, a land on the newest turn, and close-restore stay off this plate. The outlined card stays off this plate. The dragged divider, the saved ratio, the shell column, and the hide arrows stay off this plate. Sideways-only member scroll, the narrow column, the centered block, duplicate group, and pipes stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. This plate is not a host line and not a second runtime. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7433

## The group row recorded beside this plate

Members of one group sit on a sideways row. Each member is its top bar plus 10 pixels. The merge stays the column width under that row. The block takes the column. The canvas sideways bar stays off.

**semantic-key:** PK-7434

## The outline recorded beside this plate

Every card wears a one-pixel line. The armed card is sapphire. A focused merge lights its group.

**semantic-key:** PK-7435

## The think line recorded beside this plate

The running card wears the line while that run is in flight. The line leaves when the run ends.

**semantic-key:** PK-7436

## The land recorded beside this plate

New scrolls the canvas to the newest card.

**semantic-key:** PK-7437

## The divider recorded beside this plate

The bar between the canvas and the inspector moves for this session. The open share stays 0.72. A drag keeps the share until the window closes. The saved ratio stays off that plate.

**semantic-key:** PK-7438

## The clock recorded beside this plate

The running card counts the seconds beside thinking. The count starts when the run starts. The shape is m:ss, and h:mm:ss once an hour has passed. The line leaves when the run ends. The yellow thought text stays off that clock.

**semantic-key:** PK-7439

## The group bar recorded beside this plate

The group row starts hidden. v shows it and ^ hides it. The choice is not saved. Group, Join, and Ungroup sit on that row.

**semantic-key:** PK-7440

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** The window paints, then takes at most eight channel-A pieces and one terminal chunk. A full command channel keeps the first key at the front. A closed channel drops the queue. A quiet live session waits 16 ms. A backlog waits 1 ms. No session does not poll. Disconnect clears both stashes and uses a blocking send only after the parked commands have been tried.

**Reason.** The socket burst was painting before the keys already in hand, so a keystroke waited behind a queue the window did not need to drain in one turn.

**Avoidances.** Do not commit before the paint. Do not take more than eight channel-A pieces or more than one terminal chunk in one turn. Do not reorder a queue. Do not block the window thread on the send. Do not enter the read runtime from the window thread. Do not change the 8 ms, 16 ms, or 8 KiB paint hold. Do not poll when no session is up. Do not open a second paint thread.

**semantic-key:** PK-8215

# The connect word sits to the right of Font size on the open bar

While the connection bar is open, the connect word sits to the right of Font size, on that bar. Connected wears a green face and white letters, the same chip the folded bar wears.

**semantic-key:** PK-7441

## The open bar

The open bar paints the fold mark, the fields, the buttons, Font size, then the connect word. The word is not a row of its own under that bar. The tab row follows that bar. A door note, when one is present, stays under the bar.

Connected paints the green chip: white letters, a light edge on the top and the left, and a dark edge on the bottom and the right. Connecting, Disconnected, and a disconnect reason keep the plate ink. A group miss uses the notice ink. A folded bar still carries the same word on the folded row.

The south status strip still says Connected with the saved host and the saved port. The other client keeps the connect words on the south status label. This plate does not move that label.

**semantic-key:** PK-7442

## What this plate does not move

This plate does not add a side move and does not add a wire move. It does not open a vault column. It does not retouch Open as URL, Copy, the Paste menu line, Select All, the Clear Buffer menu line, Page Up, Page Down, Line Up, or Line Down. It does not retouch the user combo, the remembered chooser, the shell, the clear button, the console mood, the thinking fold, or the folded menu. It does not edit the other client.

The check does not open a window and does not start a session.

**Truth.** On an open bar the connect word follows Font size on that same bar. Connected is the green chip with white letters and the folded bevels. Every other connect line keeps the plate ink, or the notice ink when the line is the group miss. The tabs follow the bar. The south strip still names the host and the port.

**Reason.** The connect word was a row of its own under the open bar, so the tabs sat a row too low, and Connected on that open bar was not the chip the folded bar already wore.

**Avoidances.** Do not give the open connect word a row of its own. Do not paint Connecting, Disconnected, or a disconnect reason on the green chip. Do not drop the word from the folded row. Do not move the south status strip or the other client's south label.

**semantic-key:** PK-7443

# The connection bar folds when the shell says Connected

The connection bar starts open. Connect folds it when the shell says Connected. Disconnect opens it. A resize while connected folds it. The choice is not saved.

**semantic-key:** PK-7444

## The mark

v folds the fields. ^ brings them back. The mark toggles either way.

Connecting leaves the bar as it stands. A canvas status line leaves the bar and the link. The status line and the plate tabs stay.

A later frame size, while the link is up, folds the bar.

**semantic-key:** PK-7445

## The other client

The other client seats the connection panel on a vertical split. The panel starts at its preferred height. A focused connect sets the divider to 0. A focused disconnect opens the panel. A resize while connected sets the divider to 0. The one-touch arrow opens and closes the panel. This plate uses the v fold for that arrow. This client has one session.

This plate is not a split-pane drag and not a saved open height. It does not open a second session. It does not write the vault. It does not edit the other client.

No side move on this plate. The working set stays as it stands.

The check does not open a window and does not start a session.

**Truth.** The bar starts open. The word Connected folds it. A Disconnected word opens it. Connecting and a canvas line leave it. A resize while the link is up folds it. v and ^ toggle the fields. The choice is not saved.

**Reason.** An open connection bar was covering the console after the shell had already connected, and a resize while connected was leaving the fields in the way.

**Avoidances.** Do not fold on Connecting. Do not fold on a canvas status line. Do not save the open or folded choice. Do not hide the status line or the plate tabs. Do not open a second session.

**semantic-key:** PK-7446

# A mark named Copy copies the whole reply and leaves the prompt

A mark reads Copy and copies the whole reply. The mark stays off until the reply holds a character above a space. The prompt stays.

**semantic-key:** PK-7447

## The click

A click copies the whole reply. The prompt stays in the card. A lone newline gains a carriage return. A pair that already has one stays. The stored prompt and the stored reply stay. Run still sends the stored text. The click also arms that card.

The door file stays. The vault file stays. The standing model stays the local engine.

This clipboard command does not report a failure. The click leaves the status line. The words clipboard busy stay locked for a later set that can see the failure. The other client says clipboard busy on the status line when the set fails, and leaves the line when the set is quiet. The yellow error slide stays off. The word takes the plate ink.

The reply window has its own copy button and stays unopened on this plate. The two-sheet icon stays off this plate.

One window thread still holds.

**semantic-key:** PK-7448

## What stays off this plate

Burner, the yellow thought text, the minimize toggle, and the vault file stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. The card menu and the error slide stay off this plate. This plate does not write the door and does not rewrite a pipe. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7449

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** Copy stays off until the reply holds a character above a space. A click copies the whole reply, adds a carriage return to a lone newline, leaves a pair that already has one, leaves the prompt, and arms the card. The status line is left as it stands. The words clipboard busy stay available for a later set that can see a failure.

**Reason.** The mark was live before a reply existed, and a lone newline was leaving the clipboard without the carriage return the other client already adds.

**Avoidances.** Do not enable Copy on a blank reply or on spaces alone. Do not copy the prompt. Do not drop the stored text. Do not open the reply window on this plate. Do not open the yellow slide for this click. Do not claim this clipboard command can see its own failure. Do not write the door or the vault.

**semantic-key:** PK-8216

# Delete removes the armed card and stays off when the canvas would be empty

Delete sits beside Clear. It removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The standing model stays the local engine.


**semantic-key:** PK-4659 PK-4660 PK-4661 PK-4662 PK-4663 PK-4664 PK-4665 PK-4666 PK-4667 PK-4668

## The removal

The button is on when the removal set is smaller than the deck. One card stays. A group that is the whole deck stays. An empty group id removes that one card. A group id removes every card of that group, including the merge.

The cell ids that remain stay. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The inspector follows that card. The labels close up from Turn 1.

A pipe token stays as written. A run on a removed card sends the cancel and stops. A chain that names a removed card clears. A chain that does not name a removed card keeps its later cards. A group run that names a removed card clears.

The status line says deleted turn on Canvas, or deleted group on Canvas. The ink stays the plate ink. The door file stays. The vault file stays. This plate has no vault write. The other client removes the rows from its vault. This plate does not.

One window thread still holds.

**semantic-key:** PK-7450

## What stays off this plate

Burner, the yellow thought text, the minimize toggle, and the vault file stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. The card menu, the error slide, Collapse, and Copy stay off this plate. This plate does not rewrite a pipe. It is not a host line and not a second runtime. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7451

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** Delete removes one ungrouped card, or a whole group and its merge. It stays off when one card would remain or when the group is the whole deck. Focus lands where the first removed card sat, or on the last card after a tail removal. Labels restart at Turn 1. A pipe token stays as written. A run on a removed card is cancelled. The status line says deleted turn on Canvas, or deleted group on Canvas.

**Reason.** Delete was able to empty the canvas, and a grouped card was leaving its merge and its siblings behind.

**Avoidances.** Do not empty the canvas. Do not remove only one member of a group. Do not rewrite a pipe token. Do not keep a chain that names a removed card. Do not drop a chain that does not name a removed card. Do not write the vault. Do not change the plate ink of the status line.

**semantic-key:** PK-8217

# The bar between the canvas and the inspector moves for this session

The bar between the canvas and the inspector moves for this session. The bar is 11 pixels. The fill is `0x3a3a3a`. The line is `0x8a8a8a`. The open share is 0.72. The door file is not written.

**semantic-key:** PK-7452

## The drag

A drag adds the pointer movement over the room beside the bar. The share stays from 0 through 1. A zero room keeps the share. The next paint gives the canvas that share. The inspector takes the rest. Release keeps the share until the window closes.

Connect, disconnect, and Clear leave the share. This slice keeps the dragged share when the window changes size. The other client gives extra window pixels to the canvas at 0.72. The saved per-mille stays off this plate. The hide arrows stay off this plate.

The work stays on the window thread. One window thread still holds.

**semantic-key:** PK-7453

## What stays off this plate

Burner, the yellow thought text, the elapsed clock, and the vault file stay off this plate. A saved tab and close-restore stay off this plate. The saved ratio, the shell column, and the hide arrows stay off this plate. Duplicate group and pipes stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. This plate is not a host line and not a second runtime. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7454

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** The grab bar is 11 pixels, fill `0x3a3a3a`, line `0x8a8a8a`. The open share is 0.72. A drag keeps a share from 0 through 1 until the window closes, including across a size change. A zero room keeps the share. Connect, disconnect, and Clear leave it. The door file is not written.

**Reason.** The bar was fixed, so the operator could not give the canvas or the inspector the room of this session, and a size change was throwing away a share the pointer had already set.

**Avoidances.** Do not write the door on this plate. Do not save the per-mille. Do not let the share leave 0 through 1. Do not reset the share on connect, disconnect, or Clear. Do not drop the share when the window changes size. Do not change the share when the room beside the bar is zero.

**semantic-key:** PK-8218

# Ctrl+Shift+G copies the armed group and the new prompts and replies are empty

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. The standing model stays the local engine.

**semantic-key:** PK-7455

## The copy

The new cards sit after the last card of that group. Members stay in deck order, then the merge. Prompts and replies are empty. Faults and run state start clear. Marks and columns stay. Each copy keeps the execution target, the mark, the burner mark, and the column. The cells are new. The card starts open. The source group stays. The armed card stays.

A card with group 0, or no card, sets the status to no group selected and paints that line yellow, the yellow of the slide edge. The deck stays. The slide stays off. A copy sets the status to duplicated group.

The other client opens the yellow slide for no group selected. This plate uses the status line and leaves the slide off. The door file stays.

One window thread still holds.

**semantic-key:** PK-7456

## What stays off this plate

Burner, the yellow thought text, the minimize toggle, and the vault file stay off this plate. Pipes stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. The card menu and the error slide stay off this plate. This plate is not a per-card model, not a host line, and not a second runtime. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7457

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** Ctrl+Shift+G inserts a new group after the source group, members in deck order then the merge, with empty prompts and replies, clear faults and run state, and the same marks, columns, execution target, and burner mark. Group 0, or no card, sets the status to no group selected in yellow and does not change the deck. A copy sets the status to duplicated group. The slide stays off.

**Reason.** A group copy that kept the prompts and replies would have doubled a turn the operator had not asked to repeat, and a card outside a group had no status when the chord did nothing.

**Avoidances.** Do not copy the prompt, the reply, the run state, or the parent pointer. Do not start the copy folded. Do not move the armed card. Do not open the yellow slide. Do not change the deck when no group is selected. Do not write the door. Do not give the copy a per-card model.

**semantic-key:** PK-8219

# The air-gap and num-ctx line sits on its own panel

When the inspector is open, the air-gap and num-ctx line sits on its own panel. The font shrinks so that line stays one row inside the current column width.

**semantic-key:** PK-7458

## The stack

An unreported init stays `init: not reported`. The Inspector label, soft wrap, and both size controls sit on the panel snapped under that line. That pair is the north of the inspector. The scroll pane is the center.

The yellow mood line is the first card and scrolls with the turn cards while the switch is on. The card body keeps the short mood and shape lines. The card text does not take a selection or the keyboard. The scroll pane keeps the wheel. The card body does not repeat the facts.

The user list on the lower right uses the fitted master-row size, and the font control keeps that size.

**semantic-key:** PK-7459

## The fit

The facts panel preferred width stays 0 so the line does not push the split. The font shrinks from the shell size down to 8 until the string fits the column. The Inspector label, soft wrap, the column count, and the line length stay on the control panel under the facts line. A narrow column wraps those controls and keeps every one of them. The yellow mood wraps at the visible column and scrolls with the turn cards.

This plate does not change dialog combo fonts or canvas editors. It does not write the vault. The crate on this client already seats the facts line. This filing does not edit that crate.

No side move on this plate. The working set stays as it stands.

The check does not open a window, does not post, does not open a pooled turn, and does not open a console session.

**Truth.** The facts line is its own panel, fitted from the shell size down to 8 so air-gap and num-ctx stay one row, and that panel's preferred width is 0. The label, soft wrap, and both size controls snap under it. The yellow mood card is the first scrolling card and does not repeat the facts. An unreported init reads `init: not reported`.

**Reason.** The facts line was sharing a panel with the width controls, so a narrow column wrapped the facts, and the mood card was fixed instead of scrolling with the turns.

**Avoidances.** Do not let the facts line push the split. Do not shrink the facts font below 8. Do not drop a size control when the column wraps. Do not put the facts text on the mood card. Do not give the card text the selection or the keyboard. Do not take the wheel off the scroll pane.

**semantic-key:** PK-7460

# The first cell keeps a lead so the caption line shows whole

The first cell keeps a lead under the view. That lead is half the line plus four pixels. The whole line shows.

**semantic-key:** PK-7461

## The caption

The caption stays embedded in the top stroke, centered on that stroke, seated on the right. Half the line stands above the stroke. Four pixels stay between the top of the line and the view.

Later cells keep the north gap, the same measure, so the line does not meet the border of the cell above. Four pixels stay between the bottom of the line and the buttons. The part inside the cell stops four pixels above the buttons.

The cell keeps its width. The buttons, the prompt, and the reply keep their size. A wide line keeps the right end and loses its left. A long name stays on the stroke. The hover tip stays on that line.

The stamp is local wall time, year month day, hour minute second. A middle dot sits between the stamp and the model. A blank model keeps the stamp. The clock ticks once a second while the caption paints.

A quiet cell keeps a plain frame. An armed merge keeps the sapphire line. The focused cell wears the caption.

The vault pin stays Turso 0.8.3. This plate does not clip the first line, does not grow a taller gap between later cells, and does not add a south pad, an east pad, a west pad, a smaller cell, or a new clock. It does not change the vault. It does not edit the other client.

**semantic-key:** PK-7462

## The send

The doors are a canvas Run, a glass send, a group send, and a pipe send. They share this head. The system lines stay the host sentence, then the saved mood string, then the activate line, when the switch sends. The turns follow. The server replaces the host sentence. The working set stays as it stands.

The check does not open a window and does not start a session.

**Truth.** The first cell's lead is half the caption line plus 4 pixels, with 4 pixels between the top of the line and the view and 4 pixels between the bottom of the line and the buttons. Later cells use that same north measure. A wide caption keeps its right end. The stamp is local wall time with a middle dot before the model, and a blank model keeps the stamp alone.

**Reason.** The caption was centered on the top stroke with half the line above it, and the first cell had no lead, so the top of that line was clipped by the view.

**Avoidances.** Do not clip the first line. Do not use a taller gap between later cells than the first lead. Do not add a south, east, or west pad. Do not shrink the cell, the buttons, the prompt, or the reply. Do not drop the right end of a wide caption. Do not drop the stamp when the model is blank.

**semantic-key:** PK-8246

# A click on a folded card arms that card and the fold stays

A primary click inside a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays. The standing model stays the local engine.

**semantic-key:** PK-7463

## The click

The sapphire moves on the next paint. An open card stays on the editor path. A click outside leaves the armed card. The fold stays. The stored prompt and the stored reply stay. Run still sends the stored text.

A press anywhere on the card arms that card. The brief, the header, and the bar all take the press. The prompt editor is hidden while the card is folded, so the press is on the card itself.

Collapse All and Expand All stay as they are. The vault file stays. This plate does not expand the card and does not write a new divider fraction. It does not write the door.

One window thread still holds.

**semantic-key:** PK-7464

## What stays off this plate

Burner, the yellow thought text, the minimize toggle, and the vault file stay off this plate. Splits, the paste editor, import, the font spinner, and a modal stay off this plate. The card menu, the error slide, and Copy stay off this plate. This plate does not rewrite a pipe and does not add an unreadable turn. It is not a second runtime. The terminal library, the console plan, and the console pre-plan stay as they are. This plate does not edit the other client.

The check does not open a window and does not start a session.

**semantic-key:** PK-7465

## The tool bar recorded beside this plate

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter.

**Truth.** A primary click on a folded card, including the short line, arms that card and moves the sapphire. The fold stays. The stored text stays. Run still sends it. An open card stays on the editor path. A click outside does not move the arm.

**Reason.** A folded card was not taking the press, so the sapphire stayed on the card that was already armed and the operator could not aim Run at the card they had clicked.

**Avoidances.** Do not expand the card on that click. Do not drop the fold. Do not drop the stored text. Do not write the door or the vault. Do not move the arm on a click outside the card. Do not take an open card off the editor path.

**semantic-key:** PK-8220

# A folded SSH bar carries File and Help on the tab row

When the SSH tab is in focus and the connection bar is folded, a divider follows the Aetheris tab and File and Help sit after that divider, on the same row.

**semantic-key:** PK-7466

## The row

While the bar is folded and the plate in focus is SSH, the top row is skipped. The folded row paints the fold mark, the link chip, the SSH tab, the Aetheris tab, a vertical divider, then File and Help. The console gains that row.

While the bar is open, File and Help paint at the top and the tab row stays the tabs alone. While the Aetheris plate is showing, File and Help paint at the top whether the bar is folded or open. Opening the bar again puts File and Help back above the fold mark.

The menu items stay File and Help. New Term, Close Pane, Import, Exit, Help, and About keep the actions they already have. Close Pane stays enabled only when more than one pane is open. The menu is not painted twice. The seat of the menu is not saved.

The other client keeps File and Help on the window menu bar. This plate does not move that bar.

**semantic-key:** PK-7467

## What this plate does not move

This plate does not add a side move and does not add a wire move. It does not open a vault column. It does not retouch Open as URL, Copy, the Paste menu line, Select All, the Clear Buffer menu line, Page Up, Page Down, Line Up, or Line Down. It does not retouch the user combo, the remembered chooser, the shell, the clear button, the console mood, or the thinking fold. It does not edit the other client.

The check does not open a window and does not start a session.

**Truth.** A folded SSH row is fold mark, link chip, SSH tab, Aetheris tab, vertical divider, File, Help. An open bar, and any Aetheris plate, keep File and Help at the top. Close Pane is enabled only when more than one pane is open. The menu is painted once.

**Reason.** File and Help lived only at the top, so a folded SSH console hid the menu the operator still needed, and painting them on both rows would have made two menus.

**Avoidances.** Do not put File and Help on the folded Aetheris row on this plate. Do not paint the menu twice. Do not enable Close Pane when only one pane is open. Do not save a menu seat. Do not move the other client's window menu bar.

**semantic-key:** PK-7468

# A folded connection bar sits on one row

When the connection bar is folded, the status sits to the right of the unfold mark, and the SSH and Aetheris tabs sit to the right of that status, on one row.

**semantic-key:** PK-7469

## The chip

Connected wears a green face, white letters, and a beveled edge. The light edge is on the top and the left. The dark edge is on the bottom and the right.

A folded line that is not Connected stays plate ink on that same row.

Unfolded, the fields, the status row, and the tabs stay as they stand. This plate does not change the open bar.

**semantic-key:** PK-7470

## The other client

The other client keeps the status and the tabs on their own rows when the connection panel is closed. This plate is the row on this client. The open bar stays the open look.

The fold is not saved. This plate does not write the vault. It does not edit the other client.

No side move on this plate. The working set stays as it stands.

The check does not open a window and does not start a session.

**Truth.** A folded bar is one row: unfold mark, status, SSH tab, Aetheris tab. Connected is a green chip with white letters and that bevel. Every other folded line is plate ink on the same row. The open bar is unchanged.

**Reason.** A folded bar was still spending a row on the status and another row on the tabs, so the console lost height the operator had already asked to give back.

**Avoidances.** Do not split the folded status and the folded tabs onto two rows. Do not paint a non-Connected folded line on the green chip. Do not change the open bar. Do not save the fold.

**semantic-key:** PK-7471

# The button bar wears a rounded square and the cells stack from the top

One border wraps the master row and the tool row. The cells stack from the top of the card scroll. A short deck leaves the empty space above the bar. A new card still scrolls down to the newest cell.

**semantic-key:** PK-7472

## The frame

The stroke is the divider grey `0x8a8a8a`, two pixels. The corner radius is 6. The pad inside the stroke is 6. The margin outside the stroke is 4. The plate behind the buttons is `0x1a1a1a`. The corner is a short radius, a square and not a pill. The buttons sit inside the stroke.

v still shows the tool row under the master row, inside the same border. ^ still hides it. The choice is still not saved.

**semantic-key:** PK-7473

## The stack

The card scroll does not inherit the bottom-up seat of the footer. The cells stack from the top of that scroll. The first cell is above the second. Both sit above the bar. A short deck leaves the empty space above the bar. New still sets the land. The next paint still scrolls to the newest cell. The footer does not scroll away.

The other client's canvas column is a vertical box inside a scroll pane. That column does not track the viewport height. The first cell sits at the top. Later cells stack downward. Empty space stays under the last cell, above the footer. A new turn scrolls the canvas to the end. That client's footer has no rounded border. The border on this bar is the frame for this plate. The stack is the mirror.

**semantic-key:** PK-7474

## What stays put

The scope words stay at the top of the card scroll. The error slide stays at the top of the column, outside the scroll. Close still dismisses it. Run and Stop stay on the card. The group row stays on the card. Search still selects the first hit and does not scroll. The letter stays.

Export and Import stay on the tool row. The choosers stay on the window thread. New, Clear, Delete, Collapse All, and Expand All stay on the master row. Delete stays off when the canvas would be empty.

The inspector and the divider stay on the share. The column height stays the pane height. The tool choice is still a field for this window and is not written. The door file stays. The vault file stays. This plate does not write the vault. No new thread. No new channel.

This plate does not add a border on the cards. It does not move Run and Stop onto the footer. It does not add Pull, To terminal, Preview, or the row count. The yellow thought text, the minimize toggle, and the mood layer stay off this plate. Splits, the paste editor, the font spinner, and a modal stay off this plate. The card-menu text import stays the prompt path it already is. The burner vault check waits until the vault is online. The terminal library, the console plan, and the console pre-plan stay as they are.

The check does not open a window and does not start a session.

**Truth.** One frame wraps both footer rows: stroke `0x8a8a8a` at 2 pixels, corner radius 6, inside pad 6, outside margin 4, plate `0x1a1a1a`. Cells stack from the top. A short deck leaves empty space above the bar. New still scrolls to the newest cell.

**Reason.** The cells were growing off the bar instead of stacking from the top, and the bar had no frame, so a short deck did not read as a bar with empty space above it.

**Avoidances.** Do not make the corner a pill. Do not stack the cells from the bottom. Do not drop the scroll-to-newest on New. Do not put a new border on the cards. Do not let the footer scroll. Do not move Run and Stop onto the bar.

**semantic-key:** PK-7475

# The master row and the tool row sit at the bottom of the canvas

The master row and the tool row sit at the bottom of the canvas column. The cards scroll above them. The footer does not scroll away.

**semantic-key:** PK-7476

## The rows

The master row is New, Clear, Delete, Collapse All, Expand All, and v. The tool row is the search field, Search, Export, and Import.

The tool row starts hidden. v shows the tool row under the master row and the mark becomes ^. ^ hides the tool row and the mark returns to v. The choice is not saved. Close the window and open it again and the tool row starts hidden.

Pull and To terminal stay off this row until those plates are named. Run and Stop stay on the card. Preview and the row count stay where they already are. Moving those onto the footer is a later ask.

The other client's master row is Run, Stop, New turn, Clear turns, Preview, the row count, and v. Its tool row is the search field, Search, Export, Import, Pull, and To terminal. This plate keeps the membership already seated here.

**semantic-key:** PK-7477

## What stays put

The error slide stays at the top of the canvas column, outside the card scroll. Close still dismisses it. The scope words stay at the top of the card scroll. A new card still lands at the bottom of that scroll.

Search still selects the first turn whose prompt or reply contains the field. It does not scroll. The letter stays. An empty field matches nothing.

Export and Import stay on the tool row. Their choosers stay on the window thread. Delete stays off when the canvas would be empty. The group row stays on the card. Its v is the group bar.

The inspector, the divider, and the saved share stay beside the column. The column keeps the height of the pane. Opening the tool row shortens the card scroll. Closing it gives that height back.

The card scroll does not own the buttons. The read thread stays the one already filed. No new thread. No new channel. The door file stays. The vault file stays. This plate does not write the vault.

The yellow thought text, the minimize toggle, and the mood layer stay off this plate. Splits, the paste editor, the font spinner, and a modal stay off this plate. The card-menu text import stays the prompt path it already is. The burner vault check waits until the vault is online. The terminal library, the console plan, and the console pre-plan stay as they are.

The check does not open a window and does not start a session.

**Truth.** The master row is New, Clear, Delete, Collapse All, Expand All, and v, fixed at the bottom. The tool row under it starts hidden, and v and ^ toggle it without saving the choice. Run and Stop stay on the card. Opening the tool row shortens the card scroll and closing it gives that height back.

**Reason.** The rows were scrolling away with the cards, so the operator lost New, Clear, and the tool row as soon as the deck was taller than the pane.

**Avoidances.** Do not let the footer scroll. Do not move Run and Stop onto this footer. Do not put Pull, To terminal, Preview, or the row count on these rows. Do not save the tool-row choice. Do not make Search scroll. Do not dismiss the error slide by scrolling. Do not write the door or the vault.

**semantic-key:** PK-7478

# Members on one sideways row

Members of one group sit on a sideways row. Each member is as wide as its top bar plus 10 pixels. The merge stays the column width under that row. The block takes the column. The canvas sideways bar stays off.

**semantic-key:** PK-7479

## One row

An ordinary card stays full width. A group becomes one block at the first card of that group. Members sit in deck order on one horizontal row. That row scrolls sideways. The merge paints under the row at the column width. A later card of the same group is not painted again. The row takes the column width. One member still leaves the row across the column. The canvas scroll stays vertical.

**semantic-key:** PK-7480

## Ten pixels

A member is the width of its top bar plus 10 pixels. The top bar is measured, then the card is set to that width. The prompt and the reply wrap inside it. The merge keeps the column width. The canvas does not grow a sideways bar.

**semantic-key:** PK-7481

## The deck stays

The card stays where it sits. Join does not move indices. Run on a member still sends every member, one after another. The merge is still not sent. The inspector still shows the member prompts as one user line and the raw merge reply as the assistant line. A letter click still keeps that card. Exclude still marks that card and leaves the others. An ordinary card that sat between a member and its merge paints after the block. The indices stay. The group id stays in memory. The vault waits. Deck order stays. The terminal library stays untouched. No new wire frame type is added. The same window and the same read thread stay. The window word stays the literal Ollama.

**semantic-key:** PK-7482

## What the group page still owns

Run, the merge reply, and the inspector stay as the groups page filed them. The commit page keeps the window-thread budget. This page is the layout of the group.

**semantic-key:** PK-7483

## First held aside

Burner and the think strip stay held on this filing. The Turso file stays held. The saved-tab restore, the land on the newest turn, and close-restore stay held until the notes below release them. The outlined card, the dragged divider, the saved ratio, the shell column, and the hide arrows start held. Duplicate group and pipes start held. Splits, the paste editor, import, the font spinner, and a Rust modal stay held. This page is not a change to deck order. It is not a host line, not a second runtime, and not a second paint thread. A centered group block stays refused. Java is not edited. The terminal-library page, the console plan, and the console pre-plan stay as they are.

**semantic-key:** PK-7484

## The check

The first card of a group anchors one block. Members stay in deck order on the row. The merge follows them. An ordinary card that sat between them paints after the block. A member is its top bar plus 10 pixels. The row scrolls sideways. The block takes the column. The merge stays that width. The canvas sideways bar stays off. Run still sends every member. The merge is still not sent. A letter click still keeps that card. The window stayed closed. The session was not started, and the window loop was not entered. The client battery that seated the row counted 58.

**semantic-key:** PK-7485

## What later left that hold

Every card wears a one-pixel line. The armed card is sapphire. A focused merge lights its group. The window stayed closed. Java was not edited. The battery counted 61.

The running card wears the line while that run is in flight. The line leaves when the run ends. The window stayed closed. Java was not edited. The battery counted 62.

New scrolls the canvas to the newest card. The window stayed closed. Java was not edited. The battery counted 63.

The bar between the canvas and the inspector moves for this session. The open share stays 0.72. A drag keeps the share until the window closes. The saved ratio was still held at that note. The window stayed closed. Java was not edited. The battery counted 64.

The running card counts the seconds beside thinking. The count starts when the run starts. The shape is `m:ss`, and `h:mm:ss` once an hour has passed. The line leaves when the run ends. The yellow thought text stays held. The window stayed closed. Java was not edited. The battery counted 65.

The share comes back the next time the window opens. One arrow hides the canvas. The other hides the inspector. The other arrow brings the hidden pane back. The window stayed closed. Java was not edited. The battery counted 67.

The marks are filled off-gray buttons. A triangle sits in each one. Collapsed to the right, the inspector column is closed. The window stayed closed. Java was not edited. The battery counted 68.

The buttons stay on the divider. The width is the bar. A triangle sits inside each button. The window stayed closed. Java was not edited. The battery counted 68.

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window. The window stayed closed. Java was not edited. The battery counted 70.

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone. The window stayed closed. Java was not edited. The battery counted 80.

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows `no group selected` in yellow on the status line. The window stayed closed. Java was not edited. The battery counted 81.

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says `pipe`. The token stays in the prompt. The window stayed closed. Java was not edited. The battery counted 82.

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`. The window stayed closed. Java was not edited. The battery counted 83.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. The window stayed closed. Java was not edited. The battery counted 84.

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays. The window stayed closed. Java was not edited. The battery counted 85.

A click on a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays. The window stayed closed. Java was not edited. The battery counted 86.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. The View window stays held. The door file stays. The window stayed closed. Java was not edited. The battery counted 87.

A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu. The window stayed closed. Java was not edited. The battery counted 88.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog. The window stayed closed. Java was not edited. The battery counted 89.

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. The window stayed closed. Java was not edited. The battery counted 90.

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark. The window stayed closed. Java was not edited. The battery counted 91.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. An Ollama run still sends. The window stayed closed. Java was not edited. The battery counted 92.

The group row starts hidden. `v` shows it and `^` hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. The window stayed closed. Java was not edited. The battery counted 93.

The tool bar starts hidden. `v` shows it and `^` hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. The window stayed closed. Java was not edited. The battery counted 94.

**Truth.** A group is one column-wide block. Members share a sideways row at top-bar width plus 10 pixels, in deck order, and the merge sits under them at the column width. The canvas does not grow a sideways bar.

**Reason.** Members stacked in the column made a group look like separate cards. One measured row keeps them together without moving deck indices and without a canvas-wide horizontal bar.

**Avoidances.** Do not change deck order. Do not move Join indices. Do not send the merge. Do not paint a later card of the same group a second time. Do not grow a canvas sideways bar. Do not center the block. Do not add a second paint thread or a second runtime. Do not paint a host line on this page. Do not let the member width ignore the measured top bar.

**semantic-key:** PK-7486

# Group row

The group row starts hidden. `v` shows it and `^` hides it. The choice is not saved.

**semantic-key:** PK-7487

## The mark

The mark sits on the card, after Copy. `v` shows the row. `^` hides it. A new card starts closed. A copy starts closed. The source card keeps its own mark. The choice stays for this window and is not written.

**semantic-key:** PK-7488

## Who is on the row

An ordinary card shows Group and Join on that row. A member or a merge shows Ungroup. Run and Stop stay on their own row. Java also puts the execution target on that row. This plate leaves that word with the model plate. The standing model stays Ollama.

**semantic-key:** PK-7489

## Java's mark

Swing puts `v` on the card header. The click shows the row under that header and the mark becomes `^`. A later click hides the row and the mark returns to `v`. The row starts hidden. The choice is a field on the card and is not written. That row holds the execution target, Group, Join, and Ungroup. This plate does not move the execution word onto the row.

**semantic-key:** PK-7490

## First held aside

The model plate stays held. The yellow thought text stays held. The minimize toggle stays held. The mood layer stays held. The Turso file stays held. The door file stays. Splits, the paste editor, canvas import, the font spinner, and a Rust modal stay held. Export stays held. To terminal stays held. This page is not the execution word, not Burner, and not a new cloud target. Java is not edited. The terminal-library page, the console plan, and the console pre-plan stay as they are.

**semantic-key:** PK-7491

## The check

A fresh window starts with the row hidden. `v` shows Group and Join on an ordinary card. `^` hides them. A card in a group shows Ungroup on that row. Close the window and open it again, and the row starts hidden. The door file stays. The window stayed closed. The session was not started, and the window loop was not entered. The client battery that seated the row counted 93.

**semantic-key:** PK-7492

## The tool row that left the hold

A later note seated the tool bar, which this page had held aside. The tool bar starts hidden. `v` shows it and `^` hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. The client battery that seated that bar counted 94. The window stayed closed. Java was not edited.

**Truth.** The group row is hidden until `v`, returns to hidden on `^`, and is hidden again on the next open. Group and Join show on an ordinary card. Ungroup shows on a member or a merge. The choice is not written.

**Reason.** The group actions were crowding the card. They belong on a row the reader opens, and that open state must not survive the window, because it was never a saved preference.

**Avoidances.** Do not save the open or closed choice. Do not put the execution word on this row. Do not move Run or Stop onto this row. Do not show Group and Join on a member or a merge. Do not show Ungroup on an ordinary card. Do not let a copied card inherit the source card's mark. Do not treat Search as part of the group row. Do not change the door file.

**semantic-key:** PK-7493

# Groups

Group, Join, and Ungroup sit on the card. A merge card is labeled Merge. Run sends every member, one after another. The merge holds the joined reply.

**semantic-key:** PK-7494

## Sit together

Group on an ordinary card mints the next cell id. That id is the merge cell and the group id. The card becomes member column 1. The merge is inserted at the next index, column 0, same group, empty prompt, Included. Members keep the turn label of their index. The card after a merge at index 1 is Turn 3. The merge paints the word Merge. It has no prompt editor. The reply still shows. The letters I, E, P, and O stay on the merge. The group id is the next cell id, kept in memory. The vault waits.

Join remembers the focused member. Join on an ordinary card gives that card the group and the next column. The card stays where it sits. Join with no armed member does nothing. A card that already belongs to a group stays there.

Ungroup clears that card. When no members remain, the merge becomes an ordinary card and the reply stays. Ungroup on the merge leaves the members grouped and leaves the sink.

**semantic-key:** PK-7495

## Run

Run on a member, or on the merge, plans every member of that group whose letter is not E, in deck order. The merge cell is never in the frames. When every member is E, the status is `Excluded.` and nothing is sent. An Excluded merge still starts the members. When any member frame is too large, the status is `too-large` and nothing is sent.

Each member frame is that member's own working set. Earlier ordinary cards stay. An earlier merge collapses to one user line and one assistant line. Siblings of this group, and this group's merge, stay out of the frame. The member's own prompt is in the frame. One execute stays in flight. The next member starts when the current member's stream ends. Java `runTarget` dispatches every member in the same call. This plate queues.

When the last member ends, the merge reply is the members' replies in deck order. Each part is `JOIN ollama`, a newline, and the reply. A blank line sits between parts. An empty reply still gets the banner. The merge fault is cleared. E members are left out of that join. Replies that already arrived stay on the members.

A fault on a member of the live group clears the rest of the queue, clears the merge reply, and sets the merge fault to `FAILED`. Member replies that already arrived stay. Stop during a group run cancels the running cell, says `Stopped.`, and fails the merge the same way. When nothing is running, Stop says `Nothing is running.`

Clear and disconnect clear the queue and the live group id. Clear still leaves one empty Included card. The air-gap word and the num-ctx word stay.

The window word stays the literal Ollama. The execution target stays `ollama`. No model name other than ollama is introduced here. The terminal library stays untouched. No new wire frame type is added. The same window and the same read thread stay. Nothing is written to Turso. No model modal is opened by this page.

**semantic-key:** PK-7496

## The inspector

When the focus is the merge, the pane lists that group. One user row is every non-empty member prompt, any letter, joined by a newline and drawn with newlines as spaces. One assistant row is the raw merge reply, so the `JOIN ollama` lines stay in that row. The estimate counts each of those lines plus a newline. The length is UTF-16 code units. Empty is 1. Any other count is `(length + 3) / 4`.

A member focus lists that member's send set. Siblings stay out. The member's own prompt stays in.

A later card sees an earlier group as one block. The user line is the non-empty prompts of I and P members, joined by a newline. The assistant line is the merge reply with the `JOIN ollama` lines removed, when the merge letter is I or O and the stripped text is non-empty. Blank lines between the replies stay. A member whose merge sits before the focus is skipped as its own card.

The letters stay I, E, P, and O. E on a member skips that member. The inspector page lists the working set. This page teaches that list what a group is.

**semantic-key:** PK-7497

## First held aside

Burner and the think strip start held. The Turso file starts held. The saved-tab restore, the land on the newest turn, and close-restore start held. The outlined card starts held. The dragged divider, the saved ratio, the shell column, and the hide arrows start held. A sideways-only member scroll, a ten-pixel column, and a centered group block start off this page's own law. Duplicate group and pipes start held. Splits, the paste editor, import, the font spinner, and a Rust modal stay held. This page is not a host line and not a second runtime. A centered block stays refused. Java is not edited. The terminal-library page, the console plan, and the console pre-plan stay as they are.

**semantic-key:** PK-7498

## The check

Group inserts Merge under the member. The merge cell id is new. Join sets the next column and leaves the card in place. Run on a member returns one frame per member. The merge cell is absent. Each frame has that member's prompt. The sibling prompt stays out. An earlier solo prompt is in both frames. The merge reply is `JOIN ollama` plus each member reply. A later card's frame has the joined prompts as one user line and the replies with the `JOIN ollama` lines removed. The inspector on the merge starts with the member prompts as one user row and the raw merge reply as the assistant row. The estimate matches the counted text. E on a member skips that member. A fault on a member clears the merge reply, sets `FAILED`, and leaves the other member's reply. The window stayed closed. The session was not started, and the window loop was not entered. The client battery that seated the groups counted 54.

**semantic-key:** PK-7499

## What later left that hold

A letter click keeps that card. Exclude marks that card and leaves the others. A prompt that already holds the keyboard does not take the pane back. The window stayed closed. Java was not edited. The battery counted 55.

The window paints the keys already in hand, then commits a budget. Canvas pieces go first. One terminal chunk follows. The rest stays queued. The window stayed closed. Java was not edited. The battery counted 57.

Members of one group sit on a sideways row. Each member is its top bar plus 10 pixels. The merge stays the column width under that row. The block takes the column. The canvas sideways bar stays off. The window stayed closed. Java was not edited. The battery counted 58.

Every card wears a one-pixel line. The armed card is sapphire. A focused merge lights its group. The window stayed closed. Java was not edited. The battery counted 61.

The running card wears the line while that run is in flight. The line leaves when the run ends. The window stayed closed. Java was not edited. The battery counted 62.

New scrolls the canvas to the newest card. The window stayed closed. Java was not edited. The battery counted 63.

The bar between the canvas and the inspector moves for this session. The open share stays 0.72. A drag keeps the share until the window closes. The saved ratio was still held at that note. The window stayed closed. Java was not edited. The battery counted 64.

The running card counts the seconds beside thinking. The count starts when the run starts. The shape is `m:ss`, and `h:mm:ss` once an hour has passed. The line leaves when the run ends. The yellow thought text stays held. The window stayed closed. Java was not edited. The battery counted 65.

The share comes back the next time the window opens. One arrow hides the canvas. The other hides the inspector. The other arrow brings the hidden pane back. The window stayed closed. Java was not edited. The battery counted 67.

The marks are filled off-gray buttons. A triangle sits in each one. Collapsed to the right, the inspector column is closed. The window stayed closed. Java was not edited. The battery counted 68.

The buttons stay on the divider. The width is the bar. A triangle sits inside each button. The window stayed closed. Java was not edited. The battery counted 68.

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window. The window stayed closed. Java was not edited. The battery counted 70.

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone. The window stayed closed. Java was not edited. The battery counted 80.

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows `no group selected` in yellow on the status line. The window stayed closed. Java was not edited. The battery counted 81.

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says `pipe`. The token stays in the prompt. The window stayed closed. Java was not edited. The battery counted 82.

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`. The window stayed closed. Java was not edited. The battery counted 83.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. The window stayed closed. Java was not edited. The battery counted 84.

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays. The window stayed closed. Java was not edited. The battery counted 85.

A click on a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays. The window stayed closed. Java was not edited. The battery counted 86.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. The View window stays held. The door file stays. The window stayed closed. Java was not edited. The battery counted 87.

A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu. The window stayed closed. Java was not edited. The battery counted 88.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog. The window stayed closed. Java was not edited. The battery counted 89.

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. The window stayed closed. Java was not edited. The battery counted 90.

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark. The window stayed closed. Java was not edited. The battery counted 91.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. An Ollama run still sends. The window stayed closed. Java was not edited. The battery counted 92.

The group row starts hidden. `v` shows it and `^` hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. The window stayed closed. Java was not edited. The battery counted 93.

The tool bar starts hidden. `v` shows it and `^` hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. The window stayed closed. Java was not edited. The battery counted 94.

**Truth.** A group is a merge cell under its members. Run queues every member that is not E and never sends the merge. The joined reply is `JOIN ollama` plus each member reply, and the inspector shows that raw reply on the merge.

**Reason.** Members had to run as one chain without the sink going out as its own prompt, and a later card had to see the group as one block rather than as every sibling.

**Avoidances.** Do not send the merge cell. Do not send an E member. Do not send anything when a member frame is too large. Do not dispatch every member in one call on this client. Do not drop a member reply that already arrived when a later member faults. Do not leave the queue running after Clear or disconnect. Do not center the group block. Do not name a model other than ollama in the join banner. Do not let Ungroup on the merge dissolve the members.

**semantic-key:** PK-7500

# Hide collapsed on Java

Hide Collapsed takes every folded card off the canvas, and Show Collapsed puts those cards back still folded.

**semantic-key:** PK-7501

## The row

A divider follows Mood on the tool row. Hide Collapsed and Show Collapsed follow that divider. The names match the Rust buttons. Hide sets the window flag and rebuilds the tabs. Show clears the flag and rebuilds the tabs.

**semantic-key:** PK-7502

## The canvas list

The canvas cell list drops a folded card and keeps an open card. A folded member leaves, and the open merge stays. Show returns the full order, still folded where the turn is folded. The flag is not written. The proof holds the filter and the names. The proof does not open a window.

**semantic-key:** PK-7503

## The bounds

The choice is for this window. It is not saved. The column uses the cells on the canvas. It is not a vault column. No wire move rides this plate. The working set is left as it stands. The build battery that seated the filter counted 52. The window stayed closed. A later live confirmation held. That confirmation does not open a new row.

**Truth.** Java hides folded cards with the same two names, rebuilds the tabs, and does not write the flag. An open card stays. Show restores the full order still folded where it was folded.

**Reason.** The window that already hid folded cards needed the same two buttons, in the same place after Mood, without a vault column and without a wire change.

**Avoidances.** Do not add a vault column. Do not save the choice. Do not send a wire field. Do not treat this page as a new product ask. Do not let a live confirmation add a row.

**semantic-key:** PK-7504

# Hide collapsed

Hide Collapsed takes every folded card off the canvas, and Show Collapsed puts those same cards back still folded.

**semantic-key:** PK-7505

## The row

The tool row keeps its place. A divider follows Mood. Hide Collapsed and Show Collapsed follow that divider. The tool-mark tips name those two buttons. The footer still sits under the cards. The proof does not open a window.

**semantic-key:** PK-7506

## The deck

Hide sets a window flag. The deck that is shown drops every folded card. An open card stays. A folded member leaves, and the open merge stays. A group that is entirely folded leaves. Show passes the slots through. The cards stay stored. The flag is not written. The filter is that a folded card leaves the shown deck and an open card does not.

**semantic-key:** PK-7507

## Java

Java folds every turn with Collapse All. A folded card there keeps a short line of the reply beside its buttons, and that fold is saved on the turn. This page does not write that row. Java follows on its own page.

**semantic-key:** PK-7508

## The bounds

The choice is for this window. It is not saved. The seat is the deck. It is not a vault column. No wire move rides this plate. The working set is left as it stands. The client battery that seated the filter counted 169. The window stayed closed. The session was not started, and the window loop was not entered. A later live confirmation held. That confirmation does not open a new row.

**Truth.** Hide removes folded cards from the shown deck for this window, and Show restores them still folded. Open cards stay. Stored cards stay. The flag is not written.

**Reason.** A folded card was still taking a place on the canvas. The two buttons clear that place without unfolding the card and without a saved column.

**Avoidances.** Do not store the choice in the vault. Do not add a wire field. Do not treat these buttons as Java's Collapse All. Do not write the fold that Java saves on the turn. Do not let a live confirmation add a row.

**semantic-key:** PK-7509

# Hide choice kept on Java

Hide Collapsed writes `hide_collapsed` true, and Show Collapsed writes it false, on the same file the Rust window uses.

**semantic-key:** PK-7510

## The file

The file is the same `aetheris-hide.yaml`. Hide writes `hide_collapsed` true and rebuilds the tabs. Show writes false and rebuilds the tabs. A missing file starts on Show. A version other than 1 stays unread and is not overwritten. The proof holds the file and the names. The proof does not open a window.

**semantic-key:** PK-7511

## The row

Hide Collapsed and Show Collapsed keep their choice on the tool row. The names match the Rust buttons. The seat is not a vault column. No wire move rides this plate. The working set is left as it stands.

**semantic-key:** PK-7512

## The bounds

The build battery that seated the file counted 53. The window stayed closed. A later live confirmation held. That confirmation does not open a new row.

**Truth.** Java writes the same version-1 flag, rebuilds the tabs, starts a missing file on Show, and leaves any other version unread and unwritten.

**Reason.** A choice saved by one window has to come back in the Rust window from the same file, without a second format and without a vault column.

**Avoidances.** Do not invent a second file. Do not overwrite a version other than 1. Do not add a vault column. Do not send a wire field. Do not let a live confirmation add a row.

**semantic-key:** PK-7513

# Hide choice kept

Hide Collapsed writes `hide_collapsed` true, and Show Collapsed writes it false, so the choice is there on the next open.

**semantic-key:** PK-7514

## The file

The file is `aetheris-hide.yaml` under the home casserole directory. Version 1 holds `hide_collapsed`. A missing file starts on Show. An empty file, or any version other than 1, stays unread and is not overwritten. An in-memory door writes nothing. The proof holds that file. The proof does not open a window.

**semantic-key:** PK-7515

## Java

Java already takes folded cards off the canvas with Hide Collapsed and Show Collapsed. This page does not write that row. Java follows on its own page.

**semantic-key:** PK-7516

## The bounds

The seat is not a vault column. No wire move rides this plate. No side move rides this plate. The working set is left as it stands. The client battery that seated the file counted 170. The window stayed closed. The session was not started, and the window loop was not entered. A later live confirmation held. That confirmation does not open a new row.

**Truth.** Hide writes true and Show writes false in version 1 of `aetheris-hide.yaml`. A missing file starts on Show. Any other version is left unread and is not overwritten.

**Reason.** The hide choice was only for the open window. The next open has to come back on the same side without a vault column.

**Avoidances.** Do not store the flag in the vault. Do not overwrite a file whose version is not 1. Do not let an in-memory door write the file. Do not treat this page as Java's writer. Do not let a live confirmation add a row.

**semantic-key:** PK-7517

# Input edge

The input pane carries the local clock and the short model on the right of its top border.

**semantic-key:** PK-7518

## The line

The line that sat above the prompt now sits on the top stroke, on the right. The pane corner stays the corner. Two rule marks flank the stamp, a middle dot, and the short model. The gap in the stroke is the panel fill. The stamp is local wall time: year, month, day, hour, minute, second. A middle dot sits between the stamp and the model. A blank model keeps the stamp. A caption wider than the pane keeps the right edge and loses its left. The tip stays the model line. The clock asks for a paint once a second while that pane is on screen.

**semantic-key:** PK-7519

## Who wears it

Folded cards and merge sinks have no input pane, so the line stays off those cards. The local stamp uses chrono 0.4.45, already named in the lock. The pin stays turso 0.8.3.

**semantic-key:** PK-7520

## The standing head

The doors are a canvas Run, a glass send, a group send, and a pipe send. They share this head. The system lines stay the host sentence, then the saved mood string, then the activate line, when the switch sends. The turns follow. The server replaces the host sentence. This page leaves the working set as it stands.

**semantic-key:** PK-7521

## The bounds

This is not a vault change and not an edit of Java. It is not a cloud account. The client battery that seated the edge counted 140. The window stayed closed. The session was not started, and the window loop was not entered. The live window was not clicked for this slice.

**Truth.** A card with an input pane paints local time and the short model on the right of the top border, keeps the right edge when the caption is wider than the pane, and repaints the clock once a second while that pane is showing.

**Reason.** The clock and the model were sitting above the prompt. They belong on the border so the corner stays the corner and a wide caption loses its left rather than its right.

**Avoidances.** Do not paint the line on a folded card or a merge sink. Do not drop the stamp when the model is blank. Do not change the pin off turso 0.8.3. Do not add a second clock library beside chrono 0.4.45. Do not move the working-set head on this page.

**semantic-key:** PK-7522

# Inspector header

When the inspector is open, the facts line, the Inspector label, the controls, and the full mood line sit above the cards.

**semantic-key:** PK-7523

## The rows

The facts line wraps above the pane. A reported init is `air-gap` and `num-ctx` on that one line. An unreported init stays `init: not reported`. The Inspector label is the next row. Soft wrap, the column count, and the line length are the row after that. The full mood line wraps in yellow above the cards while the switch is on. The card body keeps the short mood line and the short shape line, and it does not repeat the facts. The card text does not take a selection or the keyboard. The scroll pane keeps the wheel.

**semantic-key:** PK-7524

## The user list

The user list on the lower right uses the fitted size of the first button bar. The font control keeps that size.

**semantic-key:** PK-7525

## Who already had it

The Rust inspector already seats the facts line, the Inspector label, the control row, and the wrapped mood above the cards. This page carries that header into Java. The crate is not rewritten. No side move rides this plate. The working set is left as it stands.

**semantic-key:** PK-7526

## The bounds

Dialog combo fonts stay as they are. Canvas editors stay as they are. Nothing is written to the vault. The build battery that seated the header counted 47. The window stayed closed. No live POST was made. No pool turn was sent. No SSH session was opened. The live window was not clicked for this slice. A later live confirmation held. That confirmation does not open a new row.

**Truth.** An open inspector wraps the facts above the pane, then the Inspector label, then soft wrap with the column count and the line length, then the full yellow mood line while the switch is on. The card body keeps only the short mood and shape lines. The card text does not take a selection or the keyboard.

**Reason.** Those rows were already the header on the Rust window. This client needed the same stack above the cards, with the user list held at the fitted primary button-bar size.

**Avoidances.** Do not put the facts inside the card body. Do not let the card text take a selection or the keyboard. Do not take the wheel away from the scroll pane. Do not resize the user list off the fitted primary button-bar size. Do not write the vault. Do not edit the Rust window's source on this page. Do not let a live confirmation add a row.

**semantic-key:** PK-7527

# Inspector mood scrolls

While the mood switch is on, the yellow mood string is the first card in the inspector scroll, and a scroll down carries it out of view.

**semantic-key:** PK-7528

## The card

The facts line, the Inspector label, and the control row stay above the scroll. The yellow mood string is the first card inside that scroll while the switch is on. The turn cards follow it. The card body keeps the short mood line and the short shape line. An off switch leaves the yellow card out. The short lines stay in the body.

**semantic-key:** PK-7529

## The scroll

A scroll down carries the yellow card out of view with the turn cards. The header rows stay. The card wraps on the visible column. A sideways scroll of the turn text leaves the card on that column.

**semantic-key:** PK-7530

## Java

Java already seats this card inside the inspector scroll. This plate does not move that client.

**semantic-key:** PK-7531

## The proof

An off switch has no yellow card. An on switch puts the yellow card under the control row and above the newest turn. A sideways scroll keeps the card on the column while the turn text moves. A scroll down carries the card out of view while the control row stays. The proof does not open a window. The client battery that seated the scroll counted 184. The window stayed closed. The session was not started, and the window loop was not entered. No side move rides this plate. No wire move rides this plate.

**semantic-key:** PK-7532

## Left alone

This page does not change Open as URL, Copy, the Paste menu line, Select All, the Clear Buffer menu line, Page Up, Page Down, Line Up, or Line Down. It does not reopen the user combo, the remembered chooser, the shell, the clear button, the SSH mood, the SSH mood memory, the thinking fold, the folded menu, or the connect string. Tool hooks stay off. It is not a vault column. A live click of the window is not this filing.

**Truth.** The yellow mood card leads the turn cards inside the scroll only while the switch is on, wraps on the visible column, and leaves with a scroll down. The header stays put. Sideways turn text does not drag the card off that column.

**Reason.** The yellow string had to sit with the cards it describes, so a reader who scrolls the turns also scrolls the mood, while the facts and the controls stay above the scroll.

**Avoidances.** Do not pin the yellow card so a scroll down cannot carry it away. Do not let a sideways scroll of the turn text move the card off the visible column. Do not paint the yellow card when the switch is off. Do not drop the short mood line or the short shape line from the card body. Do not edit Java. Do not add a vault column.

**semantic-key:** PK-7533

# Inspector

The inspector sits beside the cards. It shows the working set for the focused card, newest block first, and an estimated token count. It does not send.

**semantic-key:** PK-7534

## The pane

The Canvas tab stays full width. Under it, the cards take 0.72 of the width and the inspector takes the rest. The bar does not move. The ratio is not saved. The pane paints with the cards. There is no Preview button. The pane does not send. The rows are monospace. The label is Inspector. The host line stays off the pane. The mood line stays off the pane. The air-gap word and the num-ctx word stay on the canvas. Nothing is written to a file. The list is the set already in memory. The vault waits. The client does not read `~/.aetheris.yaml`. The terminal library stays untouched. No new wire frame type is added. The same window and the same read thread stay. No model modal is opened. The window word stays the literal Ollama. Nothing is written to Turso. This page does not write a product-tab key.

**semantic-key:** PK-7535

## The list

The rows are the lines Run would send for the focused card. An earlier I keeps a non-empty prompt, then a non-empty reply. An earlier E keeps nothing. An earlier P keeps a non-empty prompt. An earlier O keeps a non-empty reply. An empty prompt or an empty reply on an earlier card is left out. Prior fault text stays out.

The focused card adds its own prompt when the letter is I, P, or O. An empty focused prompt still has a row. The focused reply is not in the list. An Excluded focus has no rows.

A letter click keeps that card. Exclude marks that card and leaves the others. A prompt takes the pane when it is clicked or when it gains the keyboard. A prompt that already holds the keyboard does not take the pane back.

Each earlier card is one block. The focused prompt is its own block. Blocks paint newest first. Inside a block, the user row stays above the assistant row.

A row is `N. user  ` or `N. assistant  `, with two spaces after the role, then the text. Numbers start at 1. A newline in the text is drawn as a space. A line of twelve hyphens sits between blocks.

**semantic-key:** PK-7536

## The estimate

The last line is `est. tokens:` and the count. The counted text is each sent line plus a newline, in send order. An empty count is 1. Any other count is `(length + 3) / 4`. The length is UTF-16 code units, the same count `HeuristicTokenCounter` uses. The drawn space that stands in for a newline is not the counted character. The newline is. An Excluded focus counts empty text. The line is `est. tokens: 1`.

**semantic-key:** PK-7537

## The check

The pane lists the lines the focused card would send. Newest block first. A focused O lists its prompt. An earlier O lists its reply. E lists nothing. An Excluded focus lists no rows. The estimate is 1. The pane does not send. The estimate is 1 for empty text, and `(length + 3) / 4` otherwise. A newline in a prompt is drawn as a space and counted as a newline. The host line and the mood line stay off the pane. The air-gap word and the num-ctx word stay on the canvas. The cards take 0.72 of the width. The ratio is not saved. The window stayed closed. No live SSH was opened in the proof. The session was not started, and the window loop was not entered. The client battery that seated the list counted 53.

**semantic-key:** PK-7538

## First held aside

Groups, burner, and the think strip start held. The Turso file starts held. The saved-tab restore, the land on the newest turn, and close-restore start held. The outlined card starts held. The dragged divider, the saved ratio, the shell column, and the hide arrows start held. Splits, the paste editor, import, the font spinner, and a Rust modal stay held. This page is not a host line on the pane, not a mood line on the pane, and not a second runtime. Java is not edited. The terminal-library page, the console plan, and the console pre-plan stay as they are.

**semantic-key:** PK-7539

## What later left that hold

A letter click keeps that card. Exclude marks that card and leaves the others. A prompt that already holds the keyboard does not take the pane back. The window stayed closed. Java was not edited. The battery counted 55.

The window paints the keys already in hand, then commits a budget. Canvas pieces go first. One terminal chunk follows. The rest stays queued. The window stayed closed. Java was not edited. The battery counted 57.

Members of one group sit on a sideways row. Each member is its top bar plus 10 pixels. The merge stays the column width under that row. The block takes the column. The canvas sideways bar stays off. The window stayed closed. Java was not edited. The battery counted 58.

Three Included single turns paint newest first. The focused card is its prompt. Each earlier Included card is the user row with its assistant row under it. Twelve hyphens sit between those blocks. The window stayed closed. Java was not edited. The battery counted 59.

Every card wears a one-pixel line. The armed card is sapphire. A focused merge lights its group. The window stayed closed. Java was not edited. The battery counted 61.

The running card wears the line while that run is in flight. The line leaves when the run ends. The window stayed closed. Java was not edited. The battery counted 62.

New scrolls the canvas to the newest card. The window stayed closed. Java was not edited. The battery counted 63.

The bar between the canvas and the inspector moves for this session. The open share stays 0.72. A drag keeps the share until the window closes. The saved ratio was still held at that note. The window stayed closed. Java was not edited. The battery counted 64.

The running card counts the seconds beside thinking. The count starts when the run starts. The shape is `m:ss`, and `h:mm:ss` once an hour has passed. The line leaves when the run ends. The yellow thought text stays held. The window stayed closed. Java was not edited. The battery counted 65.

The share comes back the next time the window opens. One arrow hides the canvas. The other hides the inspector. The other arrow brings the hidden pane back. The window stayed closed. Java was not edited. The battery counted 67.

The marks are filled off-gray buttons. A triangle sits in each one. Collapsed to the right, the inspector column is closed. The window stayed closed. Java was not edited. The battery counted 68.

The buttons stay on the divider. The width is the bar. A triangle sits inside each button. The window stayed closed. Java was not edited. The battery counted 68.

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window. The window stayed closed. Java was not edited. The battery counted 70.

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone. The window stayed closed. Java was not edited. The battery counted 80.

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows `no group selected` in yellow on the status line. The window stayed closed. Java was not edited. The battery counted 81.

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says `pipe`. The token stays in the prompt. The window stayed closed. Java was not edited. The battery counted 82.

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`. The window stayed closed. Java was not edited. The battery counted 83.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. The window stayed closed. Java was not edited. The battery counted 84.

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays. The window stayed closed. Java was not edited. The battery counted 85.

A click on a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays. The window stayed closed. Java was not edited. The battery counted 86.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. The View window stays held. The door file stays. The window stayed closed. Java was not edited. The battery counted 87.

A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu. The window stayed closed. Java was not edited. The battery counted 88.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog. The window stayed closed. Java was not edited. The battery counted 89.

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. The window stayed closed. Java was not edited. The battery counted 90.

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark. The window stayed closed. Java was not edited. The battery counted 91.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. An Ollama run still sends. The window stayed closed. Java was not edited. The battery counted 92.

The group row starts hidden. `v` shows it and `^` hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. The window stayed closed. Java was not edited. The battery counted 93.

The tool bar starts hidden. `v` shows it and `^` hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. The window stayed closed. Java was not edited. The battery counted 94.

**Truth.** The inspector lists the focused card's send set, newest block first, and ends with `est. tokens:` using 1 for empty text and `(length + 3) / 4` otherwise. It does not send. The cards keep 0.72 of the width until a later note lets the bar move for the session.

**Reason.** The reader needed to see the set Run would send before Run, in the same order the send uses, without a Preview button and without the focused reply pretending it had already been sent.

**Avoidances.** Do not send from the pane. Do not list the focused reply. Do not list an earlier empty prompt or an earlier empty reply. Do not list prior fault text. Do not draw a newline as itself. Do not count the drawn space as the newline. Do not put the host line or the mood line on the pane. Do not read `~/.aetheris.yaml`. Do not save the 0.72 ratio on this page. Do not take the keyboard back when the prompt already holds it. Do not let an Excluded focus show rows or any estimate but 1.

**semantic-key:** PK-8247

# Land on the newest card

New scrolls the canvas to the newest card. The land is one shot.

**semantic-key:** PK-7540

## The offset

New appends the card, then the next canvas paint scrolls. The offset is the column height minus the view. A shorter column stays at zero. The scroll is instant. The animation duration is zero. The vertical scroll puts its cursor at the bottom. The caret stays in the new prompt. Clear, connect, and disconnect leave the land down. A finished run still waits for New. The member row scrolls sideways and stays out of that land. The work stays on the event thread. The ordinary thread still holds. The land is one paint.

**semantic-key:** PK-7541

## Java

Java's new turn arms a land on the new cell. Its scroll puts the viewport on the last pixel of the column. The member row scrolls sideways and stays out of that land. The work stays on the event thread. The land is one shot.

**semantic-key:** PK-7542

## First held aside

Burner stays held. The yellow thought text stays held. The elapsed clock stays held. The Turso file stays held. The saved-tab restore and close-restore stay held. The dragged divider, the saved ratio, the shell column, and the hide arrows stay held. Duplicate group and pipes stay held. Splits, the paste editor, import, the font spinner, and a Rust modal stay held. This page is not a stick that follows every reply. It is not a turn born from a finished run. It is not a host line. It is not a second runtime. Java is not edited. The terminal-library page, the console plan, and the console pre-plan stay as they are.

**semantic-key:** PK-7543

## The check

New scrolls the canvas to the newest card. The offset is the column height minus the view. A shorter column stays at zero. The caret stays in the new prompt. The land is one shot. The window stayed closed. The session was not started, and the window loop was not entered. The client battery that seated the land counted 63.

**semantic-key:** PK-7544

## What later left that hold

The bar between the canvas and the inspector moves for this session. The open share stays 0.72. A drag keeps the share until the window closes. The saved ratio was still held at that note. The window stayed closed. Java was not edited. The battery counted 64.

The running card counts the seconds beside thinking. The count starts when the run starts. The shape is `m:ss`, and `h:mm:ss` once an hour has passed. The line leaves when the run ends. The yellow thought text stays held. The window stayed closed. Java was not edited. The battery counted 65.

The share comes back the next time the window opens. One arrow hides the canvas. The other hides the inspector. The other arrow brings the hidden pane back. The window stayed closed. Java was not edited. The battery counted 67.

The marks are filled off-gray buttons. A triangle sits in each one. Collapsed to the right, the inspector column is closed. The window stayed closed. Java was not edited. The battery counted 68.

The buttons stay on the divider. The width is the bar. A triangle sits inside each button. The window stayed closed. Java was not edited. The battery counted 68.

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window. The window stayed closed. Java was not edited. The battery counted 70.

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone. The window stayed closed. Java was not edited. The battery counted 80.

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows `no group selected` in yellow on the status line. The window stayed closed. Java was not edited. The battery counted 81.

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says `pipe`. The token stays in the prompt. The window stayed closed. Java was not edited. The battery counted 82.

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`. The window stayed closed. Java was not edited. The battery counted 83.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. The window stayed closed. Java was not edited. The battery counted 84.

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays. The window stayed closed. Java was not edited. The battery counted 85.

A click on a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays. The window stayed closed. Java was not edited. The battery counted 86.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. The View window stays held. The door file stays. The window stayed closed. Java was not edited. The battery counted 87.

A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu. The window stayed closed. Java was not edited. The battery counted 88.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog. The window stayed closed. Java was not edited. The battery counted 89.

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. The window stayed closed. Java was not edited. The battery counted 90.

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark. The window stayed closed. Java was not edited. The battery counted 91.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. An Ollama run still sends. The window stayed closed. Java was not edited. The battery counted 92.

The group row starts hidden. `v` shows it and `^` hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. The window stayed closed. Java was not edited. The battery counted 93.

The tool bar starts hidden. `v` shows it and `^` hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. The window stayed closed. Java was not edited. The battery counted 94.

**Truth.** New is the only land. It scrolls once, with no animation, to an offset of column height minus the view, or zero when the column is shorter, and the caret stays in the new prompt.

**Reason.** A new card born below the fold was easy to miss. One instant scroll brings it into view without sticking to every later reply and without landing a card that a finished run did not ask to create.

**Avoidances.** Do not stick the land to every reply. Do not land on a turn born from a finished run. Do not let Clear, connect, or disconnect arm the land. Do not include the sideways member row in that vertical land. Do not animate the scroll. Do not drop the caret out of the new prompt. Do not treat the later seats above as a second land.

**semantic-key:** PK-7545

## The control

The count sits on the inspector header, after the soft-wrap count. Minus, the field, and plus set how many characters of each kept line are shown. A typed number applies after a short pause. The smallest count is 1. A missing shell key is 80. A value under 1 is 1. The same text does not rewrite the file. Other keys stay. The shell key is `ui.aetheris.inspector.line.chars`.

**semantic-key:** PK-7546

## The cut

The paint cuts each kept user line and each kept assistant line to that count. A newline in a kept line becomes a space before the cut. A longer line gains an ellipsis. The mood head stays at 80. The off line stays at 80. The shape line stays at 80. The token estimate counts the full role text. Changing the count paints the inspector on that same pass.

**semantic-key:** PK-7547

## The bounds

This count is not the soft-wrap column count, not the canvas row count, and not the cell height. It is not a preview, not the user combo, not a vault column, and not an export field. Java is not edited. No side move rides this plate. The working set is left as it stands. The client battery that seated the cut counted 119. The window stayed closed. The session was not started, and the window loop was not entered. The live window was not clicked for this slice. A later live confirmation held. That confirmation does not open a new row.

**Truth.** Kept inspector lines show the header count, with a floor of 1 and a missing value of 80, under `ui.aetheris.inspector.line.chars`. The mood, off, and shape lines stay at 80. The estimate still counts the full text.

**Reason.** The header already set how wide a soft wrap is. A second count was needed so each kept line could be shortened without moving the mood line off 80 and without changing what the estimate counts.

**Avoidances.** Do not use this count as the soft-wrap width, the canvas row count, or the cell height. Do not cut the mood line, the off line, or the shape line. Do not let the estimate count the shortened line. Do not rewrite the file when the text did not change. Do not drop the other shell keys. Do not let a live confirmation add a row.

**semantic-key:** PK-7548

# Thinking fold

`v` folds the thinking text, `^` restores it, Close keeps the log, and Clear wipes the pane and the saved file.

**semantic-key:** PK-7549

## The marks

The `v` button folds the text and the mark becomes `^`. `^` restores the text. Close hides the pane and keeps the log, and the mark returns to `v`. Clear wipes the pane and `~/.casserole/aetheris-thinking.txt`. The strip stays up. A fold stays folded. The north edge sets the scroll height for this window. The floor is three lines plus 8. A new thought opens the pane. A fold that is already folded stays folded. The yellow body stays. The cyan tag stays.

**semantic-key:** PK-7550

## The strip

The strip sits above the footer, between the card and the footer. The scroll field stays `#1A1A1A`. Three lines are the natural view. Drag up grows the scroll. The floor is three lines plus 8. The cap is the panel height minus 160. An unmeasured panel caps at 480. A short panel keeps the natural view. The dragged height lasts for this window.

**semantic-key:** PK-7551

## The file

A thought writes the file. Clear writes it empty. Close leaves the file. The next open reads a non-empty file and shows the pane. The footer Clear still leaves the log. Connect and disconnect already leave the log, and this page does not change that.

**semantic-key:** PK-7552

## The bounds

The checkbox that survives a restart is not this page. `aetheris-think.yaml` is not this page. A height that survives a restart is not this page. This Clear is not the footer Clear. The mood layer stays. Nothing is written to Turso. Nothing is written to the door file. Java is not edited. No new channel is added. The proof checks the mark, the fold, Close, Clear, the height floor and cap, the saved-file round trip, and a strip between the card and the footer. The client battery that seated the fold counted 103. The window stayed closed. The session was not started, and the window loop was not entered. The live window was not clicked for this toggle.

**Truth.** `v` folds and `^` restores. Close hides the pane and keeps both the pane text and the file. Clear empties both and leaves the strip up. The north edge drags a height that lasts for this window, from three lines plus 8 up to the panel height minus 160, or 480 when the panel is unmeasured.

**Reason.** The thinking text needed a fold, a hide that keeps the log, a wipe of the saved file, and a drag for height, without making that height or a checkbox survive the next open.

**Avoidances.** Do not save the dragged height. Do not write `aetheris-think.yaml` here. Do not let this Clear act as the footer Clear. Do not let Close wipe the file. Do not let a new thought unfold a fold that is already folded. Do not drop the yellow body or the cyan tag. Do not paint the scroll field off `#1A1A1A`.

**semantic-key:** PK-7553

# Keyboard after Model Config

Closing the console Model Config returns the keyboard to the chat pane that opened it.

**semantic-key:** PK-7554

## The return

The console Model Config modal takes the keyboard while it is open. When that modal exits, the keyboard returns to the chat pane that opened it. A parked pane that opened it becomes the focused pane again. The next paint of that pane takes the keys. A cell modal leaves the console keyboard where it is. The model choice stays as it was written.

**semantic-key:** PK-7555

## The bounds

This page does not change when the modal exits. It does not edit Java. It does not move the wire. No side move rides this plate. The proof of the close is the console plate painting Model Config and then giving the keys back. The client battery that seated the return counted 188. The window stayed closed. The session was not started, and the window loop was not entered.

**Truth.** The console Model Config modal owns the keyboard only while it is open. Exit returns the keys to the opener, and a parked opener becomes the focused pane on the next paint. A cell modal does not move the console keyboard.

**Reason.** The console was losing the keyboard after Model Config closed. The pane that opened the modal is the pane that should type next.

**Avoidances.** Do not change the rule for when the modal exits. Do not move the console keyboard when a cell modal closes. Do not rewrite the model choice on the way out. Do not treat bare Ctrl-C as this page.

**semantic-key:** PK-7556

# Model tip

The stamp tip shows on a cell that is not selected, and the selected cell keeps the painted line.

**semantic-key:** PK-7557

## The hover

The selected cell is the focused cell. That cell paints the line and keeps the line tip. A cell that is not selected shows the stamp and the short model on hover. A blank model keeps the stamp. The clock is the moment of the hover. Buttons keep their own hover. The sapphire flanks stay. The lead stays. The pin stays turso 0.8.3.

**semantic-key:** PK-7558

## The standing head

The doors are a canvas Run, a glass send, a group send, and a pipe send. They share this head. The system lines stay the host sentence, then the saved mood string, then the activate line, when the switch sends. The turns follow. The server replaces the host sentence. No side move rides this plate. The working set is left as it stands.

**semantic-key:** PK-7559

## The bounds

This is not a tip on the selected cell. It does not change the line tip or the button tips. It does not edit Java. It does not add a crate dependency. The client battery that seated the tip counted 151. The window stayed closed. The session was not started, and the window loop was not entered. The live window was not clicked for this slice.

**Truth.** Hover on an unselected cell shows the stamp and the short model, timed at the hover. The focused cell paints the line and keeps the line tip. A blank model still shows the stamp.

**Reason.** The selected cell already paints the line, so a stamp tip on that cell repeated it. The tip belongs on the cell that is not showing the line.

**Avoidances.** Do not show the stamp tip on the selected cell. Do not replace the line tip or a button tip. Do not drop the sapphire flanks or the lead. Do not move the pin off turso 0.8.3. Do not add a dependency for this tip.

**semantic-key:** PK-7560

# Mood switch on the canvas

The mood switch sits on the tool row after To terminal, a saved string stays, and a send carries the shape line.

**semantic-key:** PK-7561

## The file

Both apps share `~/.casserole/aetheris-mood.yaml`. The file sits beside the think file. Version 1 is the map this page reads. The keys are `line`, `on`, `width`, and `height`. A write stores `line`, `on`, and a kept `width` and `height`. The key is written `'on'` so the other reader still finds it. A switch flip keeps a size pair that is in range. Width runs from 480 through 4000. Height runs from 320 through 4000. A missing file is not blocked. A write then creates version 1. Empty bytes, a version other than 1, or a top line that is not a key blocks the file. A blocked file is not overwritten. A version other than 1, or a non-map, is left unread and is not overwritten. An empty line forces the switch off.

**semantic-key:** PK-7562

## The switch

The switch sits on the tool row after To terminal, with a separator. It is grey `#9E9E9E` until a saved string exists. Left is off `#E53935`. Right is on `#00FF66`. The round button is red while off and green while on. A click flips `on` and writes the file.

**semantic-key:** PK-7563

## The inspector and the yellow line

The inspector shows `mood: (none)`, or `mood: off` and a preview, or `mood:` and `shape:` while the switch is on. The yellow `#ffff55` line sits above the inspector while the switch is on. Its width stays locked.

**semantic-key:** PK-7564

## The send

A canvas send, while the switch is on and the line is non-empty, places two system lines at the head of the working set, before the turns. This client has no host line on this page. The first line is the saved string. The second line is `Activate the mood above. Use that mood as the shape of your responses.` A body that does not fit drops both lines, paints `keyword only. too-large`, and the send continues. The layer stays on. A group frame and a pipe frame carry the lines. Export and Clear turns leave the file.

**semantic-key:** PK-7565

## The bounds

The Mood button stays off this page. The mood glass stays off this page. A dialog stays off this page. No new wire id is added. Java is not edited. This page does not add a host system line. The mood is not a card field, not a column in the session table, and not a Turso row. The proof checks the folded line, the quoted `'on'` key, a kept size pair, a blocked file that is not overwritten, the three inspector forms, the two system lines before the turns, a drop that still sends, and a group frame and a pipe frame that carry the lines. The client battery that seated the switch counted 105. The window stayed closed. The session was not started, and the window loop was not entered. The live window was not clicked for this slice.

**Truth.** The shared version-1 file holds the line, the switch, and a size pair from 480 through 4000 wide and 320 through 4000 tall. The switch after To terminal writes that file. A qualifying canvas send puts the saved string and then the activate line ahead of the turns, and a body that does not fit drops both lines and still sends.

**Reason.** The canvas needed the saved mood without opening the glass. The switch and the two system lines carry that mood, and a blocked file must not be overwritten by a flip.

**Avoidances.** Do not paint the Mood button on this page. Do not add a host line on this page. Do not overwrite a blocked file. Do not leave the switch on when the line is empty. Do not drop the size pair when it is in range. Do not write the key as a bare word the other reader misses. Do not let Export or Clear turns touch the file. Do not turn the yellow line off its locked width or off `#ffff55`.

**semantic-key:** PK-7566

# Clear mood asks first

Clear mood opens an ask over the glass. Yes clears. No leaves the mood.

**semantic-key:** PK-7567

## The words

The title is Clear mood. The words are `Clear the Mood Setting?` Yes is green with black ink. No is red with white ink. Yes uses the on green and black ink. No uses the off red and white ink. The order is Yes, then No. Each word is at least the measured width, the button pad, and two ems. The button floor is the measured word, the button pad on both sides, and two ems.

**semantic-key:** PK-7568

## What dismisses it

Yes is the only path that drops the saved string, turns the switch off, and empties the field and the chat. No, Escape, and the window close leave the mood and close the ask. A click outside the ask does not dismiss it. Enter does not clear. Space does not clear. Yes and No take a primary pointer click only.

**semantic-key:** PK-7569

## Why the words stay whole

Java's ask is a modal child of the mood dialog. The first paint locked the button size, then the colored border ate the word, and the label collapsed to an ellipsis. The fix measures the word after the border, adds the insets, and adds two ems. The minimum size is that measure. The height stays short when the word fits. Enter is not a default button there, so Enter does not clear.

**semantic-key:** PK-7570

## This window

A stock modal closes on a backdrop click. This ask does not. A foreground area eats the pointer over the rest of the window, and a foreground window titled Clear mood sits on that area. The window is not resizable. It is anchored at the center. The title-bar close is the window close. Escape is consumed while the ask is up and leaves the mood. While the ask is up, the glass does not send, approve, cancel, or clear. A close of the mood window still seals and does not clear. Yes runs the seated clear: the line drops, then the yaml write when the line drops, then the chat clears. The dialog stays open. The wires and the kept size stay. The clear flag is true only for Yes. The proof does not paint the glass. The proof checks the ask, the word floor, and the seated clear.

**semantic-key:** PK-7571

## The bounds

This page does not edit Java. It does not change the canvas header. It does not change the Pin OK tip. The client battery that seated the ask counted 106. The window stayed closed. The session was not started, and the window loop was not entered. The live window was not clicked for this slice.

**Truth.** Clear mood asks `Clear the Mood Setting?` before it drops anything. Only Yes clears. No, Escape, the title-bar close, a click outside, Enter, and Space leave the mood. Each word is at least its measure, the button pad, and two ems.

**Reason.** A clear that dropped the string on the first click could not be taken back, and a colored border had been eating the word down to an ellipsis.

**Avoidances.** Do not clear on Enter, Space, Escape, the title-bar close, or a click outside. Do not let a backdrop click dismiss the ask. Do not size the word before the border. Do not drop the two extra ems. Do not let the glass send, approve, cancel, or clear while the ask is up. Do not let a close of the mood window clear the mood. Do not close the mood dialog when Yes clears.

**semantic-key:** PK-7572

# Mood glass

The Mood button sits on the tool row after the switch. A send mints its own wire id. The dialog keeps a saved string.

**semantic-key:** PK-7573

## Opening

The glass title is Mood. It opens for the focused card. One glass at a time. No card paints the status `no cell` and leaves the glass closed. A burner cloud card paints the burner fault and leaves the glass closed. The door is the same door as Run. No mux paints the yellow slide `keyword only.` and leaves the glass closed. A normal pool card opens. An Ollama burner opens. The tool tips name the mood switch and Mood.

**semantic-key:** PK-7574

## The wire id

Each send mints a 16-byte id outside the cell mint. The count sits in the last 8 bytes. The desk keeps every id across close, so a late frame stays off the cell. Those ids are consumed before scope routing and before a fault is seated. A consumed piece stays off the group path and the pipe path. A wire id is not a cell id. Delta, End, and Later for a mood id stay off the cell. A late reply after close stays quiet.

**semantic-key:** PK-7575

## The frame

The card running flag stays clear. The send stays off the chain plan. The burner bit is the card bit. The frame is the mood execute. The canvas path stays the set execute. An empty target is `ollama`. A pool card sends the word `pool`. Any other card sends its trimmed target. `ephemeral: true` is written only when the card is ephemeral. Think stream stays off. The glass does not carry the activate line. This client has no host line on this glass.

**semantic-key:** PK-7576

## The control line and the pluck

The first line is the mood-shape control text. That control tells the model to fill a mood token string the app stores, and not to store the rest of the reply as the mood. Every reply ends with one final line that starts with `mood:` and continues with comma-separated short tokens on that same line. Each token is one short meaning: a style, a tone, a stance, or a symbol. That line has no stars, no quotes, and no code fence. Before that line, the reply says what was determined. When the user asks for more detail, a richer mood, more tokens, or an enriched mood, the extra detail is said in the reply and the same detail is put on the mood line as more tokens. The new detail is not left only in the talk. When the user did not ask for more, a short line is enough. The short example is `mood: spare, dry, lantern-light`. The enriched example, when the user asked for more, is `mood: spare, dry, lantern-light, short sentences, no warmth, gravel voice, night watch`. When the user asked for more, tokens are added until the line holds the detail that was named. Tries 1 and 2 send the format ask: return the mood text on one final line that starts with `mood:`, keep the tokens comma-separated, use no stars and no quotes on that line, and put extra tokens on that line when more detail was asked rather than leaving the detail only in the talk. The short example on that ask is the same `mood: spare, dry, lantern-light`. The pluck takes the last non-empty mood key. Marks, a space before the colon, the words mood string, a fullwidth colon, and a fenced mood block count.

**semantic-key:** PK-7577

## Tries and misses

Enter sends. An empty trim is ignored. A send already in flight keeps the typed line. A new user line resets the tries. A reply for the open id stops the spinner, appends the assistant row, and plucks the line. A filled pluck writes the draft and resets the tries. An empty pluck counts a miss. Tries 1 and 2 send the format ask on a new id. The third miss paints `keyword only. mood line` and leaves the dialog open. A wire miss closes the dialog and paints the slide. `cell-busy` closes the dialog on the yellow slide `keyword only. cell-busy`. The card running flag stays clear. The card fault is left as it stands. A frame over 1 MiB leaves the dialog open, clears busy, and paints `keyword only. too-large`. The layer stays on.

**semantic-key:** PK-7578

## Paint and size

You is `#00C853`. Assistant is `#00D7FF`. An assistant row that carries the mood mark is `#FFFF55`. The pane rises with the draft and stops at half the dialog. The spinner uses its own clock. The card think line stays off. A kept size pair opens the glass. Otherwise the glass opens at 760 by 520. Position stays off the file. Width stays from 480 through 4000. Height stays from 320 through 4000. A size outside that range leaves the stored pair. The yaml still holds `line`, `on`, `width`, and `height`. It holds no `x` and no `y`. A blocked file is not overwritten.

**semantic-key:** PK-7579

## Approve and close

Approve, Cancel, and the title-bar close write the size when the inner rect is in range. Approve of a non-empty trim sets the line and turns the switch on. An empty Approve writes no string. Cancel leaves the line and the switch. Approve sizes to the word. Export and Clear leave the file. A vanished card closes the glass. Disconnect closes the glass and keeps the wire set.

**semantic-key:** PK-7580

## The bounds

This page does not edit Java. It does not add a host system line. It does not put the activate line on this glass. It does not change the set execute. It does not set a running flag on the card. It does not call the chain plan. It does not store an x or a y. The proof checks the button after the switch, the wire id outside the cell mint, the doors, the frame, the pluck, the rising pane, the spinner, the third format miss, the wire miss, the kept size, and a file that stores no place. The client battery that seated the glass counted 106. The window stayed closed. The session was not started, and the window loop was not entered. The live window was not clicked for this slice.

**Truth.** Mood opens one glass on the focused card, each send carries a 16-byte id that never enters the cell path, and the dialog keeps a saved string at a size from 480 by 320 through 4000 by 4000 with no stored place.

**Reason.** The mood talk had to use the same door as Run without looking like a canvas cell, so a late frame could not land on a card and a saved string could come back without an x or a y.

**Avoidances.** Do not mint the mood id as a cell id. Do not set the card running flag. Do not call the chain plan. Do not carry the activate line or a host line on this glass. Do not route a consumed mood piece onto the group path or the pipe path. Do not store x or y. Do not overwrite a blocked file. Do not close the dialog on the third format miss. Do not leave the dialog open on a wire miss. Do not drop the wire set when disconnect closes the glass.

**semantic-key:** PK-7581

# Host line before the mood

A canvas send with the switch on opens the working set with the host sentence, then the saved mood string, then the activate line, then the turns.

**semantic-key:** PK-7582

## The three lines

The host sentence is `You are the Casserole assistant. Be concise and useful.` When the switch sends, three system lines are seated: that host sentence, the saved mood string, then the activate line. The turns follow. The estimate counts those three lines when the send carries them. The inspector counts the host sentence when the send carries it and does not paint the sentence. A body that does not fit drops the three lines and still tries the turns. The server inserts the prompt when no system line is present. The server replaces the first system line with the configured prompt when that prompt is non-empty. The mood string and the activate line stay. The checked-in prompt is that same host sentence. Group sends and pipe sends use the same head.

**semantic-key:** PK-7583

## What stays as it was

A send with the switch off leaves the host sentence off, the same as before this change. The glass still opens with the mood-shape control. This page is not a glass change. It is not a handshake after the modal closes. The proof does not paint the glass. The order is held by the canvas mood check. The client battery that seated the head counted 106. The window stayed closed. The session was not started, and the window loop was not entered. The live window was not clicked for this slice. A later live confirmation held and was filed. That confirmation does not open a new row.

**semantic-key:** PK-7584

## Java

Java `assemble` opens the working set with the host sentence `You are the Casserole assistant. Be concise and useful.` When the switch is on, the saved mood string and the activate line follow. `AmwpFacade.assembleMessages` replaces the first system line with `chat.system-prompt` when that prompt is non-empty. The mood string and the activate line stay. The checked-in prompt is that same host sentence.

**semantic-key:** PK-7585

## The bounds

This page does not edit Java. It does not change the canvas header's other rows. It does not change the Pin OK tip.

**Truth.** A switched-on canvas send, and a group send and a pipe send, lead with the host sentence, then the saved mood string, then the activate line. The server may replace only the first of those lines. A frame that does not fit drops all three and still sends the turns.

**Reason.** The mood string sat in the seat the server overwrites. The host sentence takes that seat so the mood string stays as the second line.

**Avoidances.** Do not put the mood string in the first system seat. Do not drop the activate line when the server replaces the host sentence. Do not paint the host sentence in the inspector. Do not add the host sentence when the switch is off. Do not change the glass opener. Do not treat a frame that drops the three lines as a failed send.

**semantic-key:** PK-7586

## The seed

The saved trim is written into the rising field after the glass opens. An empty save leaves the field empty. The pane rises. The chat starts empty. The typed line starts empty.

**semantic-key:** PK-7587

## The header

A mood send keeps the shape line. The control text is the first system line. When the field holds a mood, that string is the next system line, then the activate line, before the chat. An empty field sends no mood tokens and leaves those two lines off the frame. The shape text already contains the words spare, dry inside the lantern-light example. The standalone line that must be present is `  text: spare, dry`.

**semantic-key:** PK-7588

## Clear mood

Clear mood drops the saved string, turns the switch off, and empties the field and the chat. Width and height stay. A blocked file is left alone. The clear drops the turns, the typed line, the field, the tries, the busy flag, the current id, and the slide. The dialog stays open. The generation, the wires, and the kept size stay. A reply already in flight stays off the glass and off the cell. A late reply for the old id is quiet and leaves the field empty. The button row is laid right to left, so the add order is Cancel, Approve, Clear mood. The row reads Clear mood, Approve, Cancel.

**semantic-key:** PK-7589

## Java's dialog

The other dialog seeds the field from the saved trim after the size is set and before the dialog is shown. While the dialog is open, the session line is the field trim. An empty field stays empty. The send keeps the shape line. A field that holds a mood adds that string, then the activate line, then the chat. The switch stays off this chat. Clear mood drops the saved string and the switch, empties the field and the chat, and forgets the in-flight id. The dialog stays open. The kept size stays. The wire ids stay, so a late frame stays off the cell.

**semantic-key:** PK-7590

## The bounds

This page does not edit Java. It does not change the canvas header. The canvas pair still rides the switch. The proof does not write the home yaml. The proof checks the seed, the header order, and the clear with the glass contract. The client battery that seated the session counted 106. The window stayed closed. The session was not started, and the window loop was not entered. The live window was not clicked for this slice. A later live confirmation held. That confirmation does not open a new row.

**Truth.** A saved trim is already in the field when the glass rises, the chat and the typed line start empty, and a non-empty field sends the shape line, then that string, then the activate line, before the chat. Clear mood drops the string and the switch, empties the chat, and leaves the dialog open at the kept size.

**Reason.** The glass was opening on an empty field even when a mood was already saved, and Clear mood had to drop that string without closing the dialog or moving the kept size.

**Avoidances.** Do not send mood tokens from an empty field. Do not close the dialog on Clear mood. Do not drop the kept width and height. Do not overwrite a blocked file. Do not paint a late reply for the old id. Do not put Clear mood anywhere but the left of Approve and Cancel. Do not change the canvas header on this page. Do not let the proof write the home yaml.

**semantic-key:** PK-7591

# Mood glass inside the window

The mood glass stays inside the app window. The center is the text window. The button row stays on the screen.

**semantic-key:** PK-7592

## The fit

A pair that fits stays. A saved pair larger than the window opens on the window edge. On a 1000 by 800 window, 760 by 520 still opens at 120, 140. On a rect at 10, 20 sized 400 by 300, a kept 925 by 546 opens at 10, 20 sized 400 by 300. The button row is reserved first. A hidden draft leaves the glass 492, the draft 0, and the buttons 24 inside 520. A draft of 40 leaves the glass 448. A draft of 400 stops at half, 260, and the glass is 228. A window of 20 gives the buttons the 20 and leaves the glass at 0. The inner rect is still the size that is kept. A rect under 480 by 320 is refused. The stored pair stays. A size under the kept range is not written. Position stays off the file.

**semantic-key:** PK-7593

## The center

The transcript scrolls inside the center. The center is the text window. The ground is `#0d0d0d`. The ink is `#dcdcdc`. A turn paints the role, the body, and a blank line. You is `#00C853`. Assistant is `#00D7FF`. An assistant row that carries the mood mark is `#FFFF55`. The prompt is the last line of that window. It is a green `> ` and the uncommitted line. The line grows with the text. Enter sends. A paste keeps its newlines. An empty trim is ignored. A send already in flight keeps the typed line.

**semantic-key:** PK-7594

## The draft and the buttons

The draft rises above the footer and stops at half the window. The spinner sits on the left. Approve sits before Cancel. Approve sizes to the word.

**semantic-key:** PK-7595

## Java's glass

The Java glass is a modal named Mood. The center is the terminal. The ground is `#0d0d0d`. The ink is `#dcdcdc`. Each turn paints the role, the body, and a blank line. The prompt is a green `> ` and the uncommitted line, inside that window. Enter sends. A paste keeps its newlines. The draft is a text area above the footer. It is hidden until a reply sets the string. The height follows the wrap and stops at half the dialog. The footer is the spinner on the left and Approve then Cancel on the right. The dialog stays inside the notebook. This client keeps that order. The center is a scroll on the window thread. The prompt is the last line of that scroll. The button row is placed inside the offered rect, so the transcript cannot push it off the window.

**semantic-key:** PK-7596

## The bounds

This page does not edit Java. It does not add a second palette. It does not store an x or a y. It does not change the wire, the pluck, or the yaml. The proof checks the fit and the slots with the glass contract. The client battery that seated the window counted 106. The window stayed closed. The session was not started, and the window loop was not entered. The live window was not clicked for this slice.

**Truth.** The glass is clipped to the app window, the button row is reserved before the transcript, and the center is a dark text window whose last line is the green prompt. A pair under 480 by 320 is not written. Position is not stored.

**Reason.** A glass larger than the window was leaving the button row off the screen. Reserving the buttons first, and stopping a large pair on the window edge, keeps Approve and Cancel in view.

**Avoidances.** Do not let the transcript push the button row off the window. Do not store x or y. Do not write a size under 480 by 320. Do not open a second palette. Do not hide the prompt anywhere but the last line of the text window. Do not drop the blank line after a turn. Do not change the wire, the pluck, or the yaml on this page.

**semantic-key:** PK-7597

# North band

The caption of the focused cell sits in the gap north of that cell, between this cell and the cell above.

**semantic-key:** PK-7598

## The gap

Four pixels stay open under the caption and above the cell's stroke, and four pixels stay open over the caption and under the cell above. The cell keeps its width and its interior. The buttons, the prompt, and the reply keep their size. There is no pad on the south, the east, or the west.

A long name stays inside that band, so it does not meet a button, the border, or the text. A wide caption keeps its right end and loses its left. The hover tip stays on that same line.

**semantic-key:** PK-7599

## The stamp

The stamp is local wall time, written as year, month, day, hour, minute, and second. A middle dot sits between the stamp and the model name. A blank model keeps the stamp and does not invent a name. The clock ticks once a second while the caption paints. This is the caption's existing clock. It is not a second clock.

**semantic-key:** PK-7600

## Who wears it

The focused cell wears the caption. A quiet cell has the same band and no caption, and it keeps a plain frame. An armed merge keeps the sapphire line.

**semantic-key:** PK-7601

## What stays put

The cell does not shrink to make the band. The vault does not change. The other client does not change. The embedded store stays pinned at turso 0.8.3. The closed-window check counted 143 passes. It did not start a session and it did not open the window.

**Truth.** The caption leaves the top stroke and lives in the north gap: four pixels above the stroke, four pixels under the cell above, right end kept, stamp then a middle dot then the model, and the stamp alone when the model is blank. Only the focused cell wears it. The send head and the working set stay.

**Reason.** A caption painted on the top stroke meets the buttons, the border, and the text, and a wide line that keeps its left end loses the stamp and the model, which are the part a reader needs.

**Avoidances.** Do not put the caption back on the top stroke. Do not shrink the cell. Do not pad the south, the east, or the west. Do not clip the right end of a wide caption. Do not drop the stamp when the model is blank. Do not paint the caption on a quiet cell. Do not drop the sapphire line on an armed merge. Do not change the send order or the working set. Do not move the store pin off turso 0.8.3. Do not add a second clock.

**semantic-key:** PK-7602

# Opened turn

A reply or an end on the running last card appends one empty turn and moves the focus there. A card that is not last stays put.

**semantic-key:** PK-7603

## Which card is last

The last card is the card at the end of the deck. The reply is the stream answer. The end is the closer. Either one, while that card is still running, appends one empty card and moves the focus to its prompt. The prompt and the reply on the new card stay empty. The new card keeps the last card's Expanded View choice.

**semantic-key:** PK-7604

## What does not open a turn

A reply on an earlier card leaves the deck as it is. A fault leaves the deck. A card that is not running leaves the deck. A second signal for the same card does not open another turn. Stop clears the run first, so a late reply does not open a card.

**semantic-key:** PK-7605

## What this plate leaves alone

The send path does not change. There is no new vault column and no new export field. The row control does not change. The other client does not change. The working set stays as it stands. The closed-window check counted 114 passes. It did not start a session and it did not open the window.

**Truth.** Only a reply or an end on the card that is still running at the end of the deck appends one empty turn, focuses its empty prompt, and copies that card's Expanded View choice. Anything else leaves the deck. Stop clears the run before a late reply can open a card.

**Reason.** A run that stays on the selected cell still needs a fresh turn when that cell is the last one and the reply lands. Opening a turn for every reply, or for a fault, or after Stop, would add cards the operator did not ask for.

**Avoidances.** Do not open a turn for an earlier card. Do not open a turn on a fault. Do not open a turn when the card is not running. Do not open a second turn for the same signal. Do not let a late reply open a card after Stop. Do not put text on the new prompt or the new reply. Do not drop the Expanded View choice. Do not change the send path, the vault, the export, or the row control.

**semantic-key:** PK-7606

# Outline

Every card wears a one-pixel line, drawn inside the card rect. A quiet card is `0x3a3a3a`. An excluded card is `0x404040`. The armed card is sapphire `0x2e86ff`. A focused merge lights every card in that group.

**semantic-key:** PK-7607

## The line

The line does not move the card. The member stays its top bar plus 10 pixels. The deck stays where it sits. A quiet card keeps `0x3a3a3a`. An excluded card keeps `0x404040`. The letter E is the dark line.

**semantic-key:** PK-7608

## The armed card

The card that holds the focus is sapphire `0x2e86ff`. When the focus is the merge, every card in that group is sapphire. A focused member lights that member. The other cards keep the quiet line, or the dark line when the letter is E. A pin match stays off this page. The six-pixel inset stays off this page.

The same window stays. The same read thread stays. There is no new crate and no new channel. The gather page seats the row. This page seats the line.

The closed-window check counted 61 passes. The window stayed closed. It did not start a session and it did not open the window. The proof locks the focused card on the sapphire edge.

**semantic-key:** PK-7609

## Still off this plate

Burner and the think strip stay held. The store stays held. The saved-tab restore, the land on the newest turn, and close-restore stay held. The dragged divider, the saved ratio, the shell column, and the hide arrows stay held. Duplicate group and pipes stay held. Splits, the paste editor, import, the font spinner, and a modal stay held. The library page and the two VT100 plan pages stay as they are.

**semantic-key:** PK-7610

## Plates recorded after the line

The running card wears the line while that run is in flight. The line leaves when the run ends. That plate's closed-window check counted 62 passes.

New scrolls the canvas to the newest card. That plate's closed-window check counted 63 passes.

The bar between the canvas and the inspector moves for this session. The open share stays 0.72. A drag keeps the share until the window closes. The saved ratio stays held on that plate. That plate's closed-window check counted 64 passes.

The running card counts the seconds beside thinking. The count starts when the run starts. The shape is `m:ss`, and `h:mm:ss` once an hour has passed. The line leaves when the run ends. The yellow thought text stays held on that plate. That plate's closed-window check counted 65 passes.

The share comes back the next time the window opens. One arrow hides the canvas. The other hides the inspector. The other arrow brings the hidden pane back. That plate's closed-window check counted 67 passes.

The marks are filled off-gray buttons. A triangle sits in each one. Collapsed to the right, the inspector column is closed. That plate's closed-window check counted 68 passes.

The buttons stay on the divider. The width is the bar. A triangle sits inside each button. That plate's closed-window check counted 68 passes.

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window. That plate's closed-window check counted 70 passes.

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone. That plate's closed-window check counted 80 passes.

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows `no group selected` in yellow on the status line. That plate's closed-window check counted 81 passes.

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says `pipe`. The token stays in the prompt. That plate's closed-window check counted 82 passes.

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`. That plate's closed-window check counted 83 passes.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. That plate's closed-window check counted 84 passes.

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays. That plate's closed-window check counted 85 passes.

A click on a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays. That plate's closed-window check counted 86 passes.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. The reply window stays held. The door file stays. That plate's closed-window check counted 87 passes.

A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu. That plate's closed-window check counted 88 passes.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog. That plate's closed-window check counted 89 passes.

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. That plate's closed-window check counted 90 passes.

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark. That plate's closed-window check counted 91 passes.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. A local-engine run still sends. That plate's closed-window check counted 92 passes.

The group row starts hidden. v shows it and ^ hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. That plate's closed-window check counted 93 passes.

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. That plate's closed-window check counted 94 passes.

**Truth.** Every card wears a one-pixel line inside its rect. Quiet is `0x3a3a3a`. Excluded is `0x404040`. The armed card, a focused member, and every card of a focused merge are `0x2e86ff`. The other cards keep the quiet line, or the dark line when the letter is E. The line does not move the card. The member width stays the top bar plus 10 pixels. The deck order stays.

**Reason.** The card in hand has to read as sapphire, and a focused merge has to light the whole group, without shifting the deck or widening the member.

**Avoidances.** Do not draw the line outside the card rect. Do not use a width other than one pixel. Do not move the card to make room for the line. Do not change the member width from the top bar plus 10 pixels. Do not change the deck order. Do not apply a pin match on this page. Do not apply the six-pixel inset on this page. Do not light a quiet card sapphire. Do not light an unfocused member of a merge. Do not start a session or open the window for the closed-window check.

**semantic-key:** PK-7611

## Fold and the mark P

v folds the whole bar off the canvas, so the cards use the height they had before the bar. A folded bar takes no height. P sits on the east of the Canvas tab row while the bar is folded, 6 pixels in, and only while the bar is folded. P adds no row. P shows the bar. v stays v on the theme face.

P wears sapphire #2e86ff, a one-pixel white bevel, and a white letter. Folding does not uncheck the pin. Only the pin box turns the filter off.

**semantic-key:** PK-7612

## The door key

The door key is `ui.aetheris.pin.bar.open`. A missing key stays folded. Fold writes false. P writes true. Other keys stay. The same text does not rewrite the file. The words and the pin box stay in this window.

**semantic-key:** PK-7613

## Enter, Clear, and the edge

Enter in the field does what OK does, and only while the box is on. Turning the box off keeps the words. The states stay. Clear is a red face #E53935 with a black x. Clear empties the field and does not commit. The box stays as it is.

`commit_pin` runs after the paint copies the marks back, and only while the view window is closed and the box is on. The OK tip stays the sentence that does not open the mood dialog.

While the box is on, a readable match wears #3cb371 and a readable miss wears #222222. A burner is not readable, so it skips that color. The armed card stays #2e86ff. A merge arm lights the group, and that sapphire wins over the match and miss colors. Pin off leaves a quiet card #3a3a3a and an excluded card #404040.

**semantic-key:** PK-7614

## What stays off

The six-pixel inset stays off, so a member's width stays the top bar plus 10. The thinking-pane C stays on its own plate. The mood dialog stays closed. The inner prompt and reply borders stay as they are. There is no vault write. The other client is not edited. The working set stays as it stands. The closed-window check counted 108 passes. It did not start a session and it did not open the window.

**semantic-key:** PK-7615

## The other client's bar, and the tip this client keeps

The other client places v, the pin box, the field, a red clear mark, and OK on the canvas bar. The bar starts folded when the shell key is missing. Enter in the field calls the same write as OK, and only while the box is on. Its edge paints a readable match #3cb371 and a readable miss #222222 while the box is on. The armed card, and every card in a group whose merge is armed, stays #2e86ff and wins. A burner is not readable. That client's OK tip still says the mood dialog opens. This client keeps the tip that does not open the mood dialog.

**Truth.** v hides the whole bar and writes the door key false. P, sapphire #2e86ff with a one-pixel white bevel and a white letter, shows it again from 6 pixels in on the east of the Canvas tab and writes the key true. A missing key stays folded. Enter writes only while the box is on. Clear is #E53935 with a black x, empties the field, and does not commit. Off keeps the words. A readable match is #3cb371, a readable miss is #222222, an armed card or an armed merge's group is #2e86ff and wins, a burner skips those colors, and pin off is #3a3a3a quiet and #404040 excluded.

**Reason.** The cards were losing height to a bar that should fold away, and the other client's OK tip still claimed the mood dialog opens after the write.

**Avoidances.** Do not leave the folded bar taking height. Do not let P add a row. Do not uncheck the pin by folding. Do not let Enter write while the box is off. Do not let Clear commit or change the box. Do not clear the words when the box turns off. Do not let a match or miss color beat an armed sapphire, including a group whose merge is armed. Do not color a burner as a readable match or miss. Do not turn the six-pixel inset on. Do not change member width away from the top bar plus 10. Do not open the mood dialog from the tip. Do not move the thinking-pane C onto this bar. Do not rewrite the file when the text is unchanged.

**semantic-key:** PK-7616

# Pin mark

P is green while the pin is on and sapphire while the pin is off. The letter stays white.

**semantic-key:** PK-7617

## The colors

Green is #3cb371. Sapphire is #2e86ff. The letter stays white. The one-pixel white bevel stays. `pin_show_fill` returns the match green when the pin box is on and the armed sapphire when the box is off. `tuck_pin_show` paints that fill.

**semantic-key:** PK-7618

## Where P sits

P stays on the divider and moves with it. The pin box stays the only switch. Turning the box paints the mark, including while the bar is open and P is hidden. The tip says green means the pin is on, and sapphire means the pin is ready and off.

**semantic-key:** PK-7619

## What this paint does not move

The other client keeps P on the tab, and its C stays sapphire. This plate mirrors the green and sapphire mark on the divider and does not edit that client. It does not move the pin box, Enter, Clear, the edge, the door key, or the placement of P. It does not touch the thinking-pane C. The six-pixel inset stays off. There is no new row and no vault write. The working set stays as it stands. The closed-window check counted 110 passes and held both colors. It did not start a session and it did not open the window.

**Truth.** P is #3cb371 while the pin box is on and #2e86ff while the box is off, with a white letter and a one-pixel white bevel, on the divider. The tip says green is on and sapphire is ready and off. The box is the only switch, and the mark follows the box even while P is hidden.

**Reason.** A single sapphire mark could not show whether the pin was on or only ready. The other client already paints green for on and sapphire for off, and this divider mark follows that.

**Avoidances.** Do not leave P sapphire while the pin is on. Do not paint the letter in the fill color. Do not drop the one-pixel white bevel. Do not make a second switch. Do not skip the paint while the bar is open and P is hidden. Do not move P off the divider. Do not change C. Do not apply the six-pixel inset. Do not write the vault. Do not change the working set.

**semantic-key:** PK-7620

# Pin filter

While the pin box is on, the edge is live and the inspector keeps reading the stored marks until OK or Enter.

**semantic-key:** PK-7621

## The live edge

`pinned_edge` paints the live line. A readable match is #3cb371. A readable miss is #222222. The armed card, and every card in a group whose merge is armed, stays #2e86ff. A burner keeps the quiet edge. Cards stay on the canvas. The six-pixel inset stays off.

**semantic-key:** PK-7622

## The inspector

The letters and the inspector stay on the stored marks until OK or Enter. A focused Excluded card omits its own text. The kept turns before it stay. Typing does not change the pane. Clear does not write. Turning the box off does not write. The words stay. The inspector reads the stored marks.

OK and Enter write those marks before the cards and the inspector paint. A miss becomes Excluded. An excluded match comes back as Included. Input and Output stay. A burner misses on that write. OK does not open the mood dialog.

**semantic-key:** PK-7623

## What a live inspector got wrong

An earlier frame pointed the inspector at a live preview. A focused miss then blanked the pane. Turning the box off left that blank when the stored focus was already Excluded. The preview also stayed while the box stayed on. The other client does not do that. It paints the edge from the field while the box is on, and it lets the letters, the gray text, and the inspector read the stored states. Typing, the checkbox, and Clear do not write. OK and Enter write, then the cards and the inspector paint those states. The send path still refuses an Excluded focus.

**semantic-key:** PK-7624

## What stays

P does not move. C does not change. There is no hidden card, no send-path change, and no vault write. The working set stays as it stands. The closed-window check counted 112 passes. It did not start a session and it did not open the window.

**Truth.** The edge follows the field while the box is on: #3cb371 match, #222222 miss, #2e86ff armed, quiet edge on a burner. The inspector and the letters stay on the stored marks until OK or Enter write them. A focused Excluded card omits only its own text. Cards stay on the canvas. OK does not open the mood dialog.

**Reason.** A live preview on the inspector blanked the pane for a focused miss and left that blank after the box turned off when the stored focus was already Excluded.

**Avoidances.** Do not point the inspector at the field before OK or Enter. Do not blank the pane for a miss. Do not hide a card. Do not let typing, Clear, or the off switch write. Do not drop the kept turns before a focused Excluded card. Do not change Input or Output on a match. Do not color a burner as a match or a miss. Do not let a match color beat an armed #2e86ff. Do not turn the six-pixel inset on. Do not open the mood dialog. Do not change the send path or the working set.

**semantic-key:** PK-7625

# Pin frame

The footer sits on the frame under the tab row. While the pin box is on, the inspector lists the working set the edge already paints.

**semantic-key:** PK-7626

## The height

`frame_body_height` is the height left under the tab row. The canvas column, the split, and the inspector share it. The pin row stays inside the column and takes its height from the cards. The footer is painted at the bottom of that column. P stays on the divider.

**semantic-key:** PK-7627

## The pin while the box is on

The letters stay until OK or Enter. OK and Enter write that set before the cards and the inspector paint. Clear does not write. Clear empties the field. With the box still on, an empty field previews every stored card as Excluded.

A miss drops out of the working set. An excluded match comes back as Included. Input and Output stay. A burner stays on its stored mark until OK or Enter. A burner misses on the write and keeps the quiet edge until then.

Turning the box off keeps the words and the letters, and the inspector returns to those letters. OK does not open the mood dialog.

**semantic-key:** PK-7628

## The other client's frame

The other client paints the canvas column inside the frame under the tab. The pin row takes height from the cards. The footer stays on the frame. While the pin box is on, the edge is live. The letters stay until OK or Enter. OK writes the states and does not open the mood dialog. Clear does not write. Turning the box off keeps the words. This plate mirrors that on this client's frame.

**semantic-key:** PK-7629

## What stays

P does not move. C does not change. The six-pixel inset stays off. There is no new filter, no hidden card, no send-path change, and no vault write. The working set this plate leaves is the set the write itself produces. The closed-window check counted 112 passes. It did not start a session and it did not open the window.

**Truth.** The footer is on the frame under the tab row, and the pin row borrows height from the cards. While the box is on, the inspector lists the same working set the edge paints, the letters wait for OK or Enter, an empty field previews every stored card as Excluded, and a burner keeps its stored mark and its quiet edge until that write. Off keeps the words and returns the inspector to the letters.

**Reason.** The footer was not on the frame, and the inspector was not reading the working set the edge was already painting, so the two panes disagreed while the box was on.

**Avoidances.** Do not paint the footer inside the card scroll. Do not give the pin row a height of its own apart from the cards. Do not write on Clear. Do not write before OK or Enter. Do not open the mood dialog from OK. Do not drop a burner's stored mark before the write. Do not drop the quiet edge on a burner before the write. Do not clear the words when the box turns off. Do not move P. Do not change C. Do not apply the six-pixel inset. Do not hide a card on this plate.

**semantic-key:** PK-7630

# Pin OK

OK writes the pin marks onto the cards and does not open the mood dialog. The tip says that write, and it does not say the dialog opens.

**semantic-key:** PK-7631

## The row

The canvas carries a box labeled pin, a word field, and OK, at the top of the canvas column. The field and OK stay quiet while the box is off. The words belong to this window. They are not a vault write.

**semantic-key:** PK-7632

## How a word matches

The words split on anything that is not a letter or a digit. The words a, the, and, of, and to drop out. A match is an AND of those remaining words as substrings of the prompt and the reply. A readable card that misses goes Excluded. An excluded card that matches goes Included. A matching card in another state stays. A burner misses and goes Excluded. A card that is not readable is skipped. No remaining word means every readable card goes Excluded. An empty field is that case.

**semantic-key:** PK-7633

## When the write runs

`commit_pin` runs after the paint copies the marks back, and only while the view window is closed. A pin click while that window is open waits. OK and Enter both write. Clear does not write. The marks are on the cards. The inspector shows them on the next paint. The green match edge, the dim miss edge, and the sapphire armed edge stay off this plate.

**semantic-key:** PK-7634

## The box, the fold, and the field

P opens the bar. Turning the box off leaves the states. The later rule on this plate is that the words stay. The first cut of this plate said turning the box off clears the words. That clear is not the rule that stands. The fold, the kept words, and Clear were already in place when the fold was named again.

The box stays on and the field takes the words. A scrolled card keeps a raw rectangle over the field. Only the painted part of that card can take a click. A click on the field stays with the field.

**semantic-key:** PK-7635

## The other client's tips, and what this client does not copy

The other client's box tip says turn the box on to type the words, OK writes the states, and turn it off to clear the words and leave the states. Its field tip says a turn must contain every word, a miss goes Excluded, and an excluded match goes back to Included. Its OK tip says that same write and then says the mood dialog opens. This client's click calls the write only. The write does not open the mood dialog.

**semantic-key:** PK-7636

## The head

The canvas head stays the host sentence, then the mood string, then the activate line. This plate leaves the working set as it stands. It does not change the glass path or the host line.

**semantic-key:** PK-7637

## The checks

The closed-window check counted 107 passes on the tip and the write, then 153 passes when the fold was named again, then 154 passes when the field kept the click. None of those checks started a session or opened the window. The write check does not open the mood dialog.

**Truth.** OK and Enter write the marks while the view window is closed, and a write that arrives while that window is open waits. A miss goes Excluded, an excluded match goes Included, any other match stays, a burner misses, and an empty field excludes every readable card. Clear does not write. The off switch keeps the words and the states. OK does not open the mood dialog. A click on the field stays with the field.

**Reason.** The other client's OK tip still said the mood dialog opens after the pins are written, but the click only writes the marks. A raw rectangle from a scrolled card was also taking the click that belonged to the field.

**Avoidances.** Do not open the mood dialog from OK. Do not put that sentence on the tip. Do not write while the view window is open. Do not let Clear write. Do not clear the words when the box turns off. Do not write the words into the vault. Do not skip the burner exclusion. Do not change a matching card that is already in another state. Do not let a scrolled card's raw rectangle take the field's click. Do not paint the match, miss, or armed edge on this plate. Do not change the canvas head or the working set.

**semantic-key:** PK-7638

# Facts and the pin mark

The air-gap word and the num-ctx word sit on one line above the inspector, and P sits on the divider and moves with it.

**semantic-key:** PK-7639

## The line

`fact_line` is air-gap, two spaces, then num-ctx. It is empty until both words are present. That line is the inspector's share of the tab row. `fact_line_rect` is that share, and it starts clear of P. The words leave the card scroll, so the cards start at the top of the canvas. Later scope text stays on the canvas. The Canvas tab stays the selected label on the left. The arrows stay on the bar.

**semantic-key:** PK-7640

## Where P sits

The divider bar is 11 pixels wide. P is wider than the bar, centered on it, and kept inside the window. `pin_show_seat` centers P on `divider_center_x` and clamps it into the tab row. `pin_show_covers_bar` stays true when the bar is against either edge. P moves when the bar moves. P adds no row.

A collapsed inspector hides the line with that pane and keeps P on the bar. A collapsed canvas keeps P on the bar at the left.

**semantic-key:** PK-7641

## The mark that stays

The mark stays sapphire #2e86ff, with a one-pixel white bevel and a white letter. v stays v. The pin box, Enter, Clear, the edge, and the door key stay as the pin bar left them.

**semantic-key:** PK-7642

## What this plate does not copy

The other client keeps P on the east of the canvas tab header and paints air-gap on the notebook status line. This plate does not edit that client. The thinking-pane C stays put. The six-pixel inset stays off. There is no new row above the cards, no change to the inner prompt and reply borders, and no vault write. The working set stays as it stands. The closed-window check counted 109 passes. It did not start a session and it did not open the window. A later live confirmation added no new requirement.

**Truth.** Air-gap and num-ctx share one line on the inspector's part of the tab row, empty until both are present, clear of P. P is centered on the 11-pixel divider, clamped inside the window, and stays on the bar when the inspector or the canvas collapses. The cards start at the top. The mark stays #2e86ff with a white letter and a one-pixel white bevel.

**Reason.** The inspector's share of the tab row was empty, and a fact line inside the card scroll stole height from the cards. P already had a divider that moves, and that divider is the place that keeps the cards' height.

**Avoidances.** Do not put the two words back in the card scroll. Do not show the line before both words are present. Do not let the line cover P. Do not add a row for P. Do not let P leave the window. Do not hide P when the inspector collapses or when the canvas collapses. Do not leave P on the east of the tab once the bar is folded. Do not move the pin box, Enter, Clear, the edge, or the door key. Do not change v or C. Do not apply the six-pixel inset. Do not edit the other client.

**semantic-key:** PK-7643

# Pin view

The pin view hides a miss from the canvas while the box is on and the field has a pin word. The marks stay the marks the user set.

**semantic-key:** PK-7644

## One frame

PinView is one frame. The box and the needle build it. The canvas and the inspector read it. `pin_match` is asked once a frame. The answer is not stored. `pin_slots` walks the deck geometry and keeps the indexes the view shows. The reply window already owns the name `view_slots`. A group block keeps the members and the merge that pass. A block with nothing left is omitted. Indexes stay, so focus stays.

**semantic-key:** PK-7645

## What leaves the canvas

A miss leaves the canvas while the box is on and the field has a pin word. The card stays in the deck. An empty field shows the deck. Turning the box off shows the deck. The words stay in the field. A burner is out of the view while a pin word is on. Its mark stays. The edge function stays for a card the view paints.

**semantic-key:** PK-7646

## The inspector

While the view is on, the inspector lists the visible cards. A user's Excluded card omits its own text. A pin miss is absent. Cards after the focus that pass are in the set. While the view is off, the inspector reads the stored marks. A focused Excluded card omits its own text. The kept turns before it stay.

**semantic-key:** PK-7647

## The marks

OK and Enter do not write a mark. Clear empties the field. OK does not open the mood dialog. The send path stays on the stored marks. There is no third mark, no saved prior mark, and no vault column.

**semantic-key:** PK-7648

## What stays

The six-pixel inset stays off. P does not move. C does not change. The other client does not change. The working set stays as it stands, because the send path still uses the stored marks. The closed-window check counted 112 passes. It did not start a session and it did not open the window. A later live confirmation added no new requirement.

**Truth.** While the box is on and the field has a pin word, a miss leaves the canvas and the inspector, and the card and its mark stay in the deck. An empty field or the box off shows the deck. The view is rebuilt every frame and is not stored. OK and Enter do not write marks. The send path uses the stored marks.

**Reason.** Writing a miss onto the card, or hiding the card from the deck, changed a mark the user had set. The view can omit a miss for one frame without a third mark and without a vault column.

**Avoidances.** Do not store the view. Do not drop a card from the deck because it misses. Do not change the mark a miss already has. Do not show a miss while the box is on and a pin word is present. Do not hide the deck when the field is empty or the box is off. Do not let OK or Enter write a mark on this plate. Do not open the mood dialog. Do not send from the view instead of the stored marks. Do not omit a passing member or a passing merge from a group block. Do not keep a group block that has nothing left. Do not move focus by renumbering indexes. Do not apply the six-pixel inset.

**semantic-key:** PK-7649

# Pipe

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. The standing model stays the local engine.

**semantic-key:** PK-7650

## The chain

The list is the kept cards before the armed card. An excluded card is skipped. A card in the same group is skipped. The scan walks `{{cell[k].output}}` in the armed prompt. A k in range is enqueued once, in token order. A bad k is left for send time. The armed card is last. One run is in flight. The next card is encoded when the earlier run ends.

When a card is sent, the outbound text replaces `{{cell[k].output}}` with that earlier reply. A pipe in an earlier kept prompt or reply is replaced on that same send. The stored prompt stays. The token stays in the prompt. A token with no closer stays as written.

A bad index, an empty reply, a failed card, or a cycle sets the status to `pipe` and clears the rest of the chain. The failure starts with `pipe:`. A fault on the running card clears the rest. A merge is not sent. An excluded armed card does not run the chain. The status word is `pipe`. The ink stays the plate ink. The slide stays off. This plate has no burner field, so the ephemeral miss stays off. The door file stays. The simple thread still holds. The other client binds the same chord on its dispatch thread. No armed card returns.

The closed-window check counted 82 passes. The window stayed closed. It did not start a session and it did not open the window.

**semantic-key:** PK-7651

## Plates recorded after the chain

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`. That plate's closed-window check counted 83 passes.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. That plate's closed-window check counted 84 passes.

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays. That plate's closed-window check counted 85 passes.

A click on a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays. That plate's closed-window check counted 86 passes.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. The reply window stays held. The door file stays. That plate's closed-window check counted 87 passes.

A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu. That plate's closed-window check counted 88 passes.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog. That plate's closed-window check counted 89 passes.

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. That plate's closed-window check counted 90 passes.

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark. That plate's closed-window check counted 91 passes.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. A local-engine run still sends. That plate's closed-window check counted 92 passes.

The group row starts hidden. v shows it and ^ hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. That plate's closed-window check counted 93 passes.

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. That plate's closed-window check counted 94 passes.

The card menu, the error slide, burner, the yellow thought, the minimize toggle, the store, splits, the paste editor, import, the font spinner, and a modal stay off the chain itself. The library page and the two VT100 plan pages stay as they are.

**Truth.** Ctrl+Shift+F5 runs kept earlier cards named by `{{cell[k].output}}`, counted from 1, skipping excluded cards and cards in the same group, once each in token order, then the armed card. The stored prompt keeps the token. The sent text carries the earlier reply. A bad index, an empty reply, a failed card, a cycle, or a fault sets `pipe` and clears the rest. A merge is not sent. An excluded armed card does not run. The slide stays off. The standing model stays the local engine.

**Reason.** A prompt that names an earlier card's output has to run those cards first, and a bad index has to stop the rest of the chain instead of sending the token as if it were text.

**Avoidances.** Do not run the chain with no armed card. Do not count an excluded card or a same-group card. Do not enqueue the same k twice. Do not send the armed card before the named cards. Do not rewrite the stored prompt. Do not replace a token that has no closer. Do not continue the chain after `pipe`. Do not send a merge. Do not run an excluded armed card. Do not open the slide for this status. Do not change the plate ink. Do not add a burner field on this plate. Do not start a second run while one is in flight. Do not change the door file.

**semantic-key:** PK-7652

# Pull

Pull copies the terminal into a new turn. A highlight is copied when one is selected. Spaces in that highlight stay. Otherwise the last non-empty screen line is copied. The new turn starts Excluded. The prior turn's model stays on that turn.

**semantic-key:** PK-7653

## Which text

A highlight wins when its trim is not empty. The stored prompt is the raw selection. Spaces stay. The console copy on this client still trims the trailing spaces on each row. Pull does not use that trim.

Otherwise the last non-empty screen row is copied. The walk starts at the bottom of the screen. A blank row is skipped. The line is trimmed. Scrollback is not that walk. A highlight that sits in scrollback still wins when it is the selection. The active screen is the one read, so an alternate screen is the screen when that screen is showing.

An empty source says `no terminal line` and adds no card. The deck stays. The sequence stays. The focus stays. The other client returns quietly when the canvas or the vault is missing. This plate has no vault, and the canvas is the in-memory deck. An empty screen takes the `no terminal line` path.

**semantic-key:** PK-7654

## The new card

A landed pull mints the next id, appends one card, and sets the mark to Excluded. The prompt is the terminal text. The reply is empty. The target is empty. The prior turn's execution target is not copied. New does copy that target. Pull does not. The burner mark stays off. The group stays 0. Focus moves to that card. The caret sits in the prompt. The next paint scrolls to that card. The status is `pulled excluded`. The click applies while the reply view is closed, the same gate Search, Export, and Import already use.

Run on that card says Excluded. The card stays in memory. New still copies nothing from the terminal and still keeps the prior model. The error slide stays at the top of the column.

**semantic-key:** PK-7655

## The row and the status

The button sits on the open tool row, after Import, inside the rounded bar. The tip says Pull copies the terminal into a new turn, a highlight is copied when one is selected, otherwise the last non-empty screen line is copied, and the new turn starts Excluded so Run will not send it until it is included. v still shows the row. ^ still hides it. The choice is still not saved. Search, Export, and Import stay. To terminal stays off this row and off these tips.

The other client's status line prefixes the vault word, the air-gap word, and the engine word, then the extra. This plate uses the bare extra. That prefix waits. A vault error on the other client says `pull failed` and the reason. This plate does not write the vault, so that word is not produced. It waits until the store file is online. `pull failed` waits with that file.

No wire frame leaves. The read thread stays the one already in use. The door file stays. The store file stays. The simple thread still holds.

To terminal, the yellow thought text, the minimize toggle, the mood layer, splits, the paste editor, the font spinner, and a modal stay held. The card-menu text import stays the prompt path it already is. The library page and the two VT100 plan pages stay as they are. The other client's Pull row stays. The differences on this plate are the ones named here.

The closed-window check counted 99 passes. The window stayed closed. It did not start a session and it did not open the window.

**Truth.** Pull, after Import, copies a highlight with its spaces, or else the last non-empty row of the screen that is showing, into a new Excluded card with an empty reply, an empty target, group 0, and the burner mark off. Scrollback is not the last-line walk. A scrollback highlight still wins when it is selected. An empty source says `no terminal line` and adds no card. The status is the bare `pulled excluded`. The prior model stays on the prior turn.

**Reason.** The tool row could import and search, and it could not take the terminal line onto a card without sending it. Trimming the highlight the way the console copy does would drop spaces the selection still held.

**Avoidances.** Do not trim spaces out of a highlight. Do not use the console copy's trailing-space trim. Do not walk scrollback for the last line. Do not skip a selected highlight that sits in scrollback. Do not read a screen that is not showing. Do not add a card for an empty source. Do not copy the prior execution target. Do not start the new card as anything but Excluded. Do not turn the burner mark on. Do not set a group other than 0. Do not leave focus on the prior card. Do not prefix the status. Do not say `pull failed` on this plate. Do not write the door, the store, or a wire frame. Do not put To terminal on this row.

**semantic-key:** PK-7656

# Saved ratio

The share comes back the next time the window opens. The door keeps one per-mille, 0 through 1000, on `ui.aetheris.inspector.divider`. The number is the share times 1000, rounded. The write waits 400 ms after the last move, and only when the span is at least 32. A span under 32 waits. An absent key keeps the open share at 0.72.

**semantic-key:** PK-7657

## The memory

A move restarts a 400 ms timer, and the timer writes once. Putting the saved place back does not write the door again. The same number does not rewrite the file. Other keys stay. A stored 0 hides the canvas. A stored 1000 hides the inspector. A drag or an arrow moves the session share at once. The work stays on the window thread.

**semantic-key:** PK-7658

## The arrows

The bar carries two marks. `<` points at the canvas. `>` points at the inspector. The upper mark hides the canvas and parks the share, and the inspector takes the width. The lower mark hides the inspector and parks the share, and the canvas takes the width. The other mark brings the parked share back. A parked edge comes back as 0.72. A drag still moves the bar.

The same window stays. The same read thread stays. There is no new crate and no new channel. One number sits on the door. Two marks sit on the bar. The divider page seats the session bar. This page seats the memory and the arrows. The simple thread still holds.

The closed-window check counted 67 passes. The window stayed closed. It did not start a session and it did not open the window.

**semantic-key:** PK-7659

## Still off this plate

Burner stays held. The yellow thought text stays held. The minimize toggle stays held. The store stays held. The saved-tab restore and close-restore stay held. The shell column stays held. Duplicate group and pipes stay held. Splits, the paste editor, import, the font spinner, and a modal stay held. The library page and the two VT100 plan pages stay as they are.

**semantic-key:** PK-7660

## Plates recorded after the share

The marks are filled off-gray buttons. A triangle sits in each one. Collapsed to the right, the inspector column is closed. That plate's closed-window check counted 68 passes.

The buttons stay on the divider. The width is the bar. A triangle sits inside each button. That plate's closed-window check counted 68 passes.

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window. That plate's closed-window check counted 70 passes.

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone. That plate's closed-window check counted 80 passes.

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows `no group selected` in yellow on the status line. That plate's closed-window check counted 81 passes.

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says `pipe`. The token stays in the prompt. That plate's closed-window check counted 82 passes.

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`. That plate's closed-window check counted 83 passes.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. That plate's closed-window check counted 84 passes.

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays. That plate's closed-window check counted 85 passes.

A click on a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays. That plate's closed-window check counted 86 passes.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. The reply window stays held. The door file stays. That plate's closed-window check counted 87 passes.

A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu. That plate's closed-window check counted 88 passes.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog. That plate's closed-window check counted 89 passes.

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. That plate's closed-window check counted 90 passes.

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark. That plate's closed-window check counted 91 passes.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. A local-engine run still sends. That plate's closed-window check counted 92 passes.

The group row starts hidden. v shows it and ^ hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. That plate's closed-window check counted 93 passes.

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. That plate's closed-window check counted 94 passes.

**Truth.** `ui.aetheris.inspector.divider` stores the share times 1000, rounded, from 0 through 1000. Absent, the open share is 0.72. The write waits 400 ms after the last move and only when the span is at least 32. The same number does not rewrite the file. Stored 0 hides the canvas. Stored 1000 hides the inspector. The upper mark parks the canvas. The lower mark parks the inspector. The other mark restores the parked share, and a parked edge comes back as 0.72. A drag still moves the bar.

**Reason.** The next open has to find the bar where it was left, and each arrow has to hide one pane without losing the share that brings it back.

**Avoidances.** Do not write while the span is under 32. Do not write before 400 ms have passed since the last move. Do not rewrite the file when the per-mille is unchanged. Do not write again when the saved place is put back. Do not drop the other door keys. Do not leave a parked edge at the hidden share. Do not stop a drag from moving the bar. Do not move the work off the window thread. Do not start a session or open the window for the closed-window check.

**semantic-key:** PK-7661

# Row count

The row count sets the open height. Minus, the field, and plus sit on the first button bar after Clear and before Delete.

**semantic-key:** PK-7662

## How the count changes

Minus and plus apply at once and stop a pending typed pause. A typed number applies after a short pause. An empty field or a word restores the shown count and does not change it. A count under 1 becomes 1. The smallest count is 1.

**semantic-key:** PK-7663

## The door key

The key is `ui.aetheris.view.rows`. A missing or unreadable key is 4. The same count, and the same stored text, does not rewrite the shell file. Other keys stay.

**semantic-key:** PK-7664

## What the count sizes

The count is one number for the shell. An open prompt keeps that many lines. An unchecked Expanded View reply keeps that many lines and wears the same plate as the prompt. A checked Expanded View reply stays the whole reply and wears that same plate. Unchecking it puts the reply in the saved window. A folded card stays a brief line.

**semantic-key:** PK-7665

## What stays

Preview does not change. The user combo does not change. There is no vault column and no export field. The other client does not change. The working set stays as it stands. The closed-window check counted 117 passes. It did not start a session and it did not open the window. A later live confirmation added no new requirement.

**Truth.** `ui.aetheris.view.rows` is the open height, default 4 when missing or unreadable, and never under 1. Minus and plus apply at once. A typed number waits a short pause. Empty or a word restores the shown count. An open prompt and an unchecked reply use that many lines. A checked reply stays whole. A folded card stays a brief line. An unchanged value does not rewrite the file.

**Reason.** The open prompt and the unchecked reply had no shared height on the footer, so each card could not follow one saved count.

**Avoidances.** Do not place the control anywhere but after Clear and before Delete. Do not wait to apply minus or plus. Do not apply a typed number before the short pause. Do not let an empty field or a word change the count. Do not store a count under 1. Do not use anything but 4 for a missing or unreadable key. Do not rewrite the file when the stored text is the same. Do not shrink a checked Expanded View reply. Do not change a folded card. Do not touch preview, the user combo, the vault, or the export.

**semantic-key:** PK-7666

# Row height

When the count changes, every open canvas cell takes that pane height on the same paint.

**semantic-key:** PK-7667

## The pane

The pane is the mono line, floored at 16, times the count, plus 8. The text scrolls inside the pane. A long prompt stays in that pane. The cell keeps that height when the text is longer.

**semantic-key:** PK-7668

## Which cells take it

The paint gives every open prompt that pane, and every unchecked reply the same pane. An unchecked Expanded View reply wears the prompt plate. A checked Expanded View reply stays the whole reply and keeps its plate. Unchecking the box puts the reply in the pane. A folded card stays a brief line. An empty reply stays unpainted.

**semantic-key:** PK-7669

## What stays

The footer control does not change. The saved key stays `ui.aetheris.view.rows`. Preview does not change. The user combo does not change. There is no vault column and no export field. The other client does not change. The working set stays as it stands. The closed-window check counted 117 passes. It did not start a session and it did not open the window. A later live confirmation added no new requirement.

**Truth.** Every open prompt and every unchecked reply takes one pane on the paint that changes the count: mono line floored at 16, times the count, plus 8. Text scrolls inside that pane. A checked reply stays whole. A folded card stays a brief line. An empty reply stays unpainted.

**Reason.** Changing the count was not reaching every open cell on that same paint, so a long prompt could grow the cell instead of scrolling inside the pane.

**Avoidances.** Do not size only the focused cell. Do not let a long prompt grow the pane. Do not floor the mono line below 16. Do not drop the plus 8. Do not shrink a checked Expanded View reply. Do not paint an empty reply. Do not change a folded card into the pane. Do not change the footer control or the saved key. Do not touch preview, the user combo, the vault, or the export.

**semantic-key:** PK-7670

# Sapphire flank

Each flank of the caption is a one-pixel stroke on the top border, in the selection sapphire #2e86ff. The words carry no box rule.

**semantic-key:** PK-7671

## The words

The words are the stamp and the short model. A middle dot sits between them. A blank model keeps the stamp. The words stay the caption ink. The caption stays centered on the top stroke and kept to the right, eight pixels in. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. The hover tip stays on that line. The clock ticks once a second while the caption paints.

**semantic-key:** PK-7672

## The flanks

Each flank is two ems, the span the box rule used to take, and one pixel tall. One space of air sits between a flank and the words. The stroke is #2e86ff. The panel fill sits behind the words, so the border does not run through the text.

**semantic-key:** PK-7673

## What stays

The lead stays. The border arm stays. There is no north, south, east, or west pad. The cell does not shrink. This is not a new clock and not a grey arm on the border. The store pin stays turso 0.8.3. The vault does not change. The other client does not change.

A canvas Run, a glass send, a group send, and a pipe send share one head. When the switch sends, the system lines stay the host sentence, then the saved mood string, then the activate line. The turns follow. The server replaces the host sentence. The working set stays as it stands.

The closed-window check counted 144 passes. It did not start a session and it did not open the window. A later live confirmation added no new requirement.

**Truth.** The caption words stay ink, with no box rule, eight pixels in on the right of the top stroke. Each flank is a one-pixel #2e86ff stroke, two ems long, with one space of air before the words. The panel fill covers the words so the border does not cut them. A blank model keeps the stamp. A wide line keeps the right end.

**Reason.** The box-drawing rule in the caption was a second border, and the part of the arm that meets the top border needed the selection sapphire instead of a grey arm. The border was also running through the text.

**Avoidances.** Do not put a box-drawing rule back in the caption. Do not paint the flanks grey. Do not make a flank taller than one pixel or longer than two ems. Do not drop the one space of air. Do not let the border run through the words. Do not pad any side. Do not shrink the cell. Do not move the lead or the border arm. Do not add a clock. Do not move the store pin off turso 0.8.3. Do not change the send head or the working set.

**semantic-key:** PK-7674

## The wheel

The wheel scrolls the pane that owns it and stops at the end of that pane. The wheel over a text pane scrolls that pane only while that pane owns the scroll. Otherwise the wheel scrolls the canvas. Scroll focus stays apart from the keyboard focus.

A press anywhere else on the card returns the wheel to the canvas. The armed cell stays armed. A press on the canvas padding arms nothing and returns the wheel to the canvas. A folded brief is not a text pane. A press on it returns the wheel to the canvas. The execution target keeps the caret.

**semantic-key:** PK-7675

## Hover

Hover anywhere on a cell shows the stamp and the short model, the same words a selected cell paints. A blank model keeps the stamp. The clock is the moment of the hover. A quiet cell paints no line. The focused line keeps its hover. That line is the model this turn will call. Buttons keep their own hover.

**semantic-key:** PK-7676

## What stays

The sapphire flanks stay. The lead stays. The store pin stays turso 0.8.3. The vault does not change. The other client does not change. This is not a new clock, not a VT100 thought strip, not a right-click on the console, and not a change to the chosen model file.

A canvas Run, a glass send, a group send, and a pipe send share one head. When the switch sends, the system lines stay the host sentence, then the saved mood string, then the activate line. The turns follow. The server replaces the host sentence. The working set stays as it stands.

The closed-window check counted 145 passes. It did not start a session and it did not open the window. A later live confirmation added no new requirement.

**Truth.** A press on a prompt or a reply, scrollbar included, parks the wheel on that pane until it hits the end. Any other press on the card, a press on canvas padding, and a press on a folded brief return the wheel to the canvas and do not arm a new cell from the padding. Hover shows the stamp and the short model, or the stamp alone when the model is blank, at the moment of the hover. A quiet cell paints no line.

**Reason.** The wheel was leaving the text pane, and the stamp was visible only on the focused cell, so a hover on another cell could not show which model that cell would call.

**Avoidances.** Do not let the wheel scroll past the end of the pane that owns it. Do not scroll a text pane whose press did not take the wheel. Do not treat a folded brief as a text pane. Do not arm a cell from a press on the canvas padding. Do not clear the armed cell when the wheel returns to the canvas. Do not tie scroll focus to keyboard focus. Do not take the caret off the execution target. Do not paint a line on a quiet cell. Do not give buttons the cell hover. Do not change the sapphire flanks, the lead, or the chosen model file. Do not add a clock. Do not move the store pin off turso 0.8.3.

**semantic-key:** PK-7677

# Selection frame

The focused cell carries the local clock and the short model on the right of its outer frame.

**semantic-key:** PK-7678

## The caption

The caption leaves the input pane. It embeds in the top stroke of the focused cell, the rectangle the selection line already draws. The form is two rules, the stamp, a middle dot, the short model, and two rules. The stamp is local wall time: year, month, day, hour, minute, and second. A blank model drops the dot and keeps the stamp. A caption wider than the frame keeps the right end and loses its left. The hover tip stays on that line. The clock ticks once a second while the caption paints.

**semantic-key:** PK-7679

## Who wears the frame

A quiet cell keeps a plain frame and has no caption. An armed merge lights its members, and those members keep the sapphire line. The caption stays on the focused cell. Folded or open, the place is that same frame. A folded cell wears the caption on that frame.

**semantic-key:** PK-7680

## The input edge

The input pane border is a plain stroke. The half-line above the input pane is gone. This is not a border-color change.

**semantic-key:** PK-7681

## What stays

The local stamp uses chrono 0.4.45, already named in the lock. The store pin stays turso 0.8.3. The vault does not change. The other client does not change. This is not a new clock.

A canvas Run, a glass send, a group send, and a pipe send share one head. When the switch sends, the system lines stay the host sentence, then the saved mood string, then the activate line. The turns follow. The server replaces the host sentence. The working set stays as it stands.

The closed-window check counted 142 passes. It did not start a session and it did not open the window.

**Truth.** The focused cell, folded or open, embeds the stamp and the short model in the top stroke of its outer frame, kept to the right: two rules, stamp, middle dot, model, two rules. A blank model drops the dot and keeps the stamp. A wide caption keeps the right end. A quiet cell has a plain frame. The input border is a plain stroke, and the half-line above the input pane is gone.

**Reason.** The clock and the model were not on the selection rectangle the operator was already looking at, and a half-line above the input pane was a second rule the plain stroke does not need.

**Avoidances.** Do not put the caption back on the input pane. Do not paint it on a quiet cell. Do not drop the stamp when the model is blank. Do not keep the middle dot when the model is blank. Do not clip the right end of a wide caption. Do not drop the sapphire line on members lit by an armed merge. Do not restore the half-line above the input pane. Do not change the border color. Do not add a second clock. Do not move chrono off 0.4.45 or the store pin off turso 0.8.3. Do not change the send head or the working set.

**semantic-key:** PK-7682

# Shell finesse

Each VT100 pane keeps its own thinking log, and the SSH plate wears one status strip at the bottom.

**semantic-key:** PK-7683

## The log

Each SSH pane keeps a `ThinkLog`. A split parks the focused log on that pane and starts the new focused log empty. Focus brings the parked log back. Clear wipes that pane's log. The strip id is the pane id.

Height and closed stay on the shared chrome and on `aetheris-vt100-think.yaml`. The last writer in a frame wins. A thought on a background pane opens the shared strip. Model Config stays shut for that pane. A scope piece is offered once. A later drain of the same piece leaves the log as it stands.

**semantic-key:** PK-7684

## The paint

One pane paints the strip, then the grid. A split paints the strip at the top of the focused child and at the top of each parked child. The parked grid uses the height that remains.

**semantic-key:** PK-7685

## The status strip

The strip is one bar at the bottom of the SSH plate. The fill is `0x171717`. The ink is `0xb4b4b4`. The pad is 8 by 3. Connecting stays Connecting. Connected shows `Connected host:port`. A Connected string that already carries a suffix stays. Disconnected stays Disconnected. A reason becomes `Disconnected — reason`, and the separator is an em dash. The east name is the sanitized user when the link is up and the mux has answered. The chip words stay Connected, Connecting, and Disconnected. The vault line stays on the canvas plate.

**semantic-key:** PK-7686

## The reply band

When a reply exists, the row already between the input pane and the reply pane fills with `SPLIT_FILL` `0x3a3a3a`. A folded brief has no input pane, so the band stays off. A reply that meets the input stays unfilled.

**semantic-key:** PK-7687

## Confirm exit

Confirm exit stores `ui.exit.x` and `ui.exit.y`. The size stays off the file. The same pair leaves the file alone. Either key missing keeps the platform place. A 160 by 48 title box that misses the view keeps the platform place. After the window shows, a box that meets the view writes the rounded top-left. A failed write sets the door note `door file could not be written` when that note is empty. Other keys stay. The window id is the title `Confirm exit`.

**semantic-key:** PK-7688

## The import side

`IMPORT_CHOOSER` calls `GetOpenFileNameW`. File import has to open, because `OpenFileDialog` is sealed. The sibling is none. The other client's chooser stays. Canvas import still constructs `OpenFileDialog`. The cell import and the shell Open dialog share `IMPORT_CHOOSER`. The working set stays as it stands.

**semantic-key:** PK-7689

## What this plate leaves for later

Open as URL, Copy, the Paste menu line, Select All, the Clear Buffer menu line, Page Up, Page Down, Line Up, and Line Down stay off this plate. This plate is not a check of the user combo, not a check of the remembered chooser, and not a tool hook. There is no vault column and no edit of the other client.

The closed-window check counted 178 passes. It held the exit origin beside the other keys, the per-pane log, the status words, and the divider band. It did not start a session and it did not open the window.

**Truth.** Each VT100 pane has its own thinking log and paints that strip at the top of the pane, including each parked child of a split. The SSH status bar is `0x171717` with ink `0xb4b4b4` and an 8 by 3 pad. Connected shows host and port. A disconnect reason uses an em dash. A reply that does not meet the input fills the band between them with `0x3a3a3a`. Confirm exit stores only the rounded origin, and file import opens through `GetOpenFileNameW`.

**Reason.** One shared thinking pane hid the log of the pane that was not focused, the status line was not on the shell, the reply row had no dark divider, and confirm-exit forgot where it had been.

**Avoidances.** Do not share one thinking log across panes. Do not start a split's new focused log with the parked text. Do not let a later drain of the same scope piece append again. Do not open Model Config for a background pane. Do not drop a Connected suffix that is already present. Do not put the vault line on the SSH status strip. Do not fill the band when the brief is folded or when the reply meets the input. Do not store the confirm-exit size. Do not move a 160 by 48 box that misses the view. Do not replace a door note that is already set. Do not construct the sealed dialog for this import. Do not pull Open as URL, Copy, Paste, Select All, Clear Buffer, or the page and line motions onto this plate.

**semantic-key:** PK-7690

# SSH think size

Each SSH pane in the VT100 tab keeps its own thinking height and its own fold. A drag on one pane changes that pane. The neighbor stays.

**semantic-key:** PK-7691

## Why every pane moved

Each pane already kept its log, its open mark, its slide, and its fold. The height lived on one shared chrome. Every paint copied that height onto every pane. A drag wrote the new height back onto that same chrome. The next paint resized the other panes. The file held that one height.

**semantic-key:** PK-7692

## The height on the pane

Each SSH session keeps its own height. Focus carries the height with the pane. A drag writes that pane. A kept drag, from 48 through 2000, writes the file and becomes the seed for a new pane. A pane that is already open does not take that seed. Zero is the three-line view. The fold stays on the pane. The file does not store the fold. Close still returns the mark to v on the pane that closed.

**semantic-key:** PK-7693

## What the next open does

A new pane starts at the height in the file. The next open is one pane, at the last height the file kept. The focused pane and each parked pane size and fold on their own. The strip sits at the top of that pane. The notebook thinking pane on the other tab stays on its own height.

There is no new door key, no vault column, and no wire move. The other client is not edited. The closed-window check counted 187 passes. A drag left the other strip at its height and its fold. A paint and a focus swap kept each height and each fold. The check did not start a session and it did not open the window. A later live confirmation added no new requirement.

**Truth.** Each SSH pane stores its own thinking height and its own fold. A drag from 48 through 2000 writes that pane and, when the drag is kept, writes the file as the seed for a new pane only. Zero is the three-line view. The file does not store the fold. Close returns the mark to v on the pane that closed. The notebook strip is a different height.

**Reason.** The height lived on one shared chrome, so a drag on one pane resized every pane on the next paint.

**Avoidances.** Do not copy one chrome height onto every pane. Do not let a drag resize the neighbor. Do not let a neighbor take another pane's fold. Do not apply the file seed to a pane that is already open. Do not accept a kept drag outside 48 through 2000 as the file seed. Do not store the fold in the file. Do not treat zero as anything but the three-line view. Do not leave Close's mark off v on the pane that closed. Do not resize the notebook thinking pane from an SSH drag. Do not add a door key or a vault column.

**semantic-key:** PK-7694

# Aetheris tab

One selectable Aetheris control sits on the existing window beside SSH. It stays disabled until the mux answers. The plate under it is a dark empty frame. Scope A stays read and unpainted.

**semantic-key:** PK-7695

## The gate

The status word Connected is the channel opening. The read thread sends that word before the init reply. The tab does not use that word.

The gate is the demux. The mux note stores true on the shared flag only when the demux reports a mux, and only after the push. A wait leaves the flag clear. A raw PTY leaves the flag clear. An init timeout marks the demux raw, and the flag stays clear. The console still works on that raw PTY.

The flag is never cleared. Disconnect and a broken mux drop the session. The UI reads the flag only while a session is present. No session means the tab is disabled and the plate is SSH.

A raw first byte, a bad frame while waiting, and a type other than init or data while waiting stay raw. Those paths stay in the frame reader. This slice adds no paint path for channel A.

An init frame sets the flag. A raw push and a timeout leave it clear. A later broken frame does not clear it. The session drop is what returns the plate. A channel A payload from that init stays out of the paint buffer.

**semantic-key:** PK-7696

## The plate

SSH is always a selectable label. Aetheris is a selectable label that is enabled only while the flag is set. A disabled control does not select. The disabled control matches the other client's grey: the tab is not selectable until init answers. The carry line is that the Aetheris tab needs a mux session.

The plate rule is:

1. While the mux is down, the plate is SSH. If the plate was Aetheris, the console arms once.
2. While the mux is up, a pick selects that plate. No pick keeps the current plate.
3. A pick of Aetheris while the mux is down stays SSH.

Selecting Aetheris fills the remaining rect with the plate color `0x0d0d0d`. The frame is empty. Scope A bytes are not painted there. The grid keeps receiving Scope T on the read side, so the console is current when SSH is selected again.

The chrome label stays the literal Ollama. This slice paints no model id. The strip stays short. The empty plate stays dark.

**semantic-key:** PK-7697

## Keys

Keys on the empty plate are not sent to the host. Grid focus is clear on that plate, and the stroke pass requires the SSH plate.

Returning to SSH, by a click or by the mux dropping, arms the console once. That is the same one-shot console-focus arm a session open already uses. The paint requests focus on that shot. It does not request focus on every frame. The Escape lock stays. Tab still leaves. The arrow keys stay on the console.

**semantic-key:** PK-7698

## The thread

The simple thread still holds. The tab is chrome on the UI thread. The read thread still owns the socket and still feeds Scope T to the grid. Scope A stays dropped. One flag crosses to the UI.

The signs for a shared runtime are a second pane, a shared shutdown, and a painted canvas. A disabled control and an empty frame do not meet those signs. This slice does not change the thread model.

The same window stays. There is no new crate, no new frame type, no new package, and no new public type. The library stays untouched. The console account stays on the stand-up page. This page takes the tab off that held list. The client serial stays the citation spine. The product-tab row there stays a carry. This page narrows the cut to the tab and the gate.

The closed-window check counted 42 passes. The window stayed closed. It did not start a session and it did not open the window.

**semantic-key:** PK-7699

## Still off this plate

The canvas, the store, the saved-tab restore, and the land on the newest turn stay held with the notebook. This slice does not write the saved tab word and does not add a product-tab key to `ssh-client.yaml`. SSH still opens as it does now. Close-restore of a tab stays held. Splits, the paste editor, import, the font spinner, and a modal stay held. The library page and the two VT100 plan pages stay as they are. The plan line stays: Scope A frames are read and not painted.

**semantic-key:** PK-7700

## Plates recorded after the tab

The Scope A body shows on the plate. The notebook stays held. That plate's closed-window check counted 44 passes.

The empty canvas sits on the plate. Cards stay held. That plate's closed-window check counted 45 passes.

One turn sits under the tab. The card is Turn 1. The stream reply paints on the card. That plate's closed-window check counted 48 passes.

New appends the next card under the tab. Turn 1 stays. That plate's closed-window check counted 50 passes.

The four letters sit under the tab. Clear leaves one empty Included card. That plate's closed-window check counted 52 passes.

The inspector sits beside the cards under the tab. That plate's closed-window check counted 53 passes.

Member cards sit together. Run on a member sends every member, one after another. The merge card is not sent. The inspector shows the member prompts as one user line and the merge reply as the assistant line. That plate's closed-window check counted 54 passes.

A letter click keeps that card. Exclude marks that card and leaves the others. A prompt that already holds the keyboard does not take the pane back. That plate's closed-window check counted 55 passes.

The window paints the keys already in hand, then commits a budget. Canvas pieces go first. One terminal chunk follows. The rest stays queued. That plate's closed-window check counted 57 passes.

Members of one group sit on a sideways row. Each member is its top bar plus 10 pixels. The merge stays the column width under that row. The block takes the column. The canvas sideways bar stays off. That plate's closed-window check counted 58 passes.

Every card wears a one-pixel line. The armed card is sapphire. A focused merge lights its group. That plate's closed-window check counted 61 passes.

The running card wears the line while that run is in flight. The line leaves when the run ends. That plate's closed-window check counted 62 passes.

New scrolls the canvas to the newest card. That plate's closed-window check counted 63 passes.

The bar between the canvas and the inspector moves for this session. The open share stays 0.72. A drag keeps the share until the window closes. The saved ratio stays held on that plate. That plate's closed-window check counted 64 passes.

The running card counts the seconds beside thinking. The count starts when the run starts. The shape is `m:ss`, and `h:mm:ss` once an hour has passed. The line leaves when the run ends. The yellow thought text stays held on that plate. That plate's closed-window check counted 65 passes.

The share comes back the next time the window opens. One arrow hides the canvas. The other hides the inspector. The other arrow brings the hidden pane back. That plate's closed-window check counted 67 passes.

The marks are filled off-gray buttons. A triangle sits in each one. Collapsed to the right, the inspector column is closed. That plate's closed-window check counted 68 passes.

The buttons stay on the divider. The width is the bar. A triangle sits inside each button. That plate's closed-window check counted 68 passes.

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window. That plate's closed-window check counted 70 passes.

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone. That plate's closed-window check counted 80 passes.

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows `no group selected` in yellow on the status line. That plate's closed-window check counted 81 passes.

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says `pipe`. The token stays in the prompt. That plate's closed-window check counted 82 passes.

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`. That plate's closed-window check counted 83 passes.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. That plate's closed-window check counted 84 passes.

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays. That plate's closed-window check counted 85 passes.

A click on a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays. That plate's closed-window check counted 86 passes.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. The reply window stays held. The door file stays. That plate's closed-window check counted 87 passes.

A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu. That plate's closed-window check counted 88 passes.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog. That plate's closed-window check counted 89 passes.

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. That plate's closed-window check counted 90 passes.

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark. That plate's closed-window check counted 91 passes.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. A local-engine run still sends. That plate's closed-window check counted 92 passes.

The group row starts hidden. v shows it and ^ hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. That plate's closed-window check counted 93 passes.

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. That plate's closed-window check counted 94 passes.

**Truth.** The Aetheris control enables only after the mux answers. Before connect, on a raw PTY, on timeout, and with no session, the control is disabled and the plate is SSH. Selecting it shows an empty frame at `0x0d0d0d` and does not paint Scope A. Selecting SSH shows the console and arms focus once. Disconnect or a broken mux returns the plate to SSH and disables Aetheris. The flag is set by an init answer and is not cleared by a later broken frame.

**Reason.** The tab has to wait for a real mux, and an empty frame has to stay dark until a later plate has something to paint.

**Avoidances.** Do not enable the tab on the Connected word alone. Do not enable it on a raw PTY, a wait, a timeout, a raw first byte, a bad waiting frame, or a waiting type other than init or data. Do not clear the flag except by dropping the session. Do not select a disabled control. Do not keep Aetheris showing while the mux is down. Do not paint Scope A on this frame. Do not send keys from the empty plate. Do not request console focus on every frame. Do not drop the Escape lock. Do not let Tab stay in the console. Do not take the arrow keys off the console. Do not paint a model id. Do not change the chrome label from Ollama. Do not write a product-tab key. Do not add a frame type, a package, a public type, or a second thread. Do not meet the shared-runtime signs with a disabled control and an empty frame.

**semantic-key:** PK-7701

# Taskbar mark

A pin of the running window keeps the Eye of Horus, because the operating system reads that mark from the executable.

**semantic-key:** PK-7702

## The art

The title bar wears the Eye of Horus. The caption stays Aetheris Rust. The pin uses that same art. This plate does not paint a new title-bar picture.

**semantic-key:** PK-7703

## The sizes in the executable

The executable carries the mark at 16, 32, 48, and 256. A pin of the running window asks the operating system for the icon stored there. The pinned button keeps the mark.

**semantic-key:** PK-7704

## What this plate does not move

The wire does not move. The other client does not change. The closed-window check counted 188 passes. The caption check holds the icon file. The linked executable carries the icon resource. The check did not start a session and it did not open the window.

**Truth.** The caption is Aetheris Rust, the title bar and the pinned button share the Eye of Horus, and the executable stores that art at 16, 32, 48, and 256.

**Reason.** A pin does not copy the pixels the title bar is already painting. It asks the executable. A missing size in that set drops the mark on the pinned button.

**Avoidances.** Do not paint a second picture for the pin. Do not change the caption away from Aetheris Rust. Do not omit 16, 32, 48, or 256. Do not move the wire. Do not edit the other client for this mark.

**semantic-key:** PK-7705

# Folded thinking row

A folded thinking pane is one row. Left to right the row is ^, the drag, C, then Close.

**semantic-key:** PK-7706

## The drag

The drag starts to the right of ^ and ends before C. The line is shorter than the open grip so Close fits on that row. C stays on the east of the drag. The sapphire face stays. A wider window lengthens the drag. A narrower window shortens the drag. ^, C, and Close keep their size.

**semantic-key:** PK-7707

## The open pane

An open pane keeps the grip and the text row. The grip stays full width. C stays on the east of that grip.

**semantic-key:** PK-7708

## The gap under the row

The console stays three pixels under C. A folded row is shorter, so the console and the canvas gain that height. The canvas clip stays at or above the north edge of the row.

**semantic-key:** PK-7709

## What the row does not do

Fold, close, and clear stay as they are. The log stays. The closed mark on the file stays. The height drag still writes the kept height. There is no file write from the fold itself, no log clear, and no send. The same row sits on the VT100 strip and on the canvas strip. The other client keeps its folded strip. The body stays yellow.

The closed-window check counted 188 passes. It held the folded VT100 row and the folded canvas row. It did not start a session and it did not open the window.

**Truth.** A folded thinking pane is one row, ^ then drag then C then Close, on both strips. The drag flexes with the window and stops before C. An open pane keeps a full-width grip and the text row, with C on the east of that grip. The console stays three pixels under C. The canvas clip stays at or above the north edge of the folded row. The body stays yellow.

**Reason.** A folded strip that kept the open grip and the text row was taller than one control row, so Close did not fit and the console and the canvas lost the height the fold was supposed to return.

**Avoidances.** Do not stack the folded controls on more than one row. Do not let the drag run under C or Close. Do not resize ^, C, or Close when the window changes width. Do not drop the sapphire face. Do not shorten the open grip below full width. Do not put the console closer than three pixels under C. Do not let the canvas clip cross the north edge of the row. Do not clear the log, write the closed mark, or send because the row folded. Do not stop the height drag from writing the kept height. Do not paint the body anything but yellow.

**semantic-key:** PK-7710

# Thinking fold

A fold or an unfold belongs to the thinking pane that was clicked. The other panes keep their own fold, their own text, and their own console top.

**semantic-key:** PK-7711

## The flag

The fold flag sits beside the open flag and the rise on the focused session, and on each parked pane. The paint borrows the shared height and puts this session's fold on the strip it draws. The height returns to the shared chrome. The fold returns to the session. The button lives in that pane's child, so the click belongs to that pane.

The other client's strip keeps minimized as a field on that strip. Fold toggles that field, stops that strip's rise, hides that strip's scroll, and lays out that host. This client does the same for the session that owns the strip.

A new thought opens that session's strip and leaves its fold. A folded strip that is already open stays folded. Close hides that strip and returns its mark to v. The other session stays as it is. Focus carries the fold with the pane. The file stores the height and the closed mark. It does not store the fold. There is no folded key in that text.

**semantic-key:** PK-7712

## The geometry

While the strip is open and unfolded, the scroll uses the shared height and the SSH console starts three pixels under that pane's Clear button. While the strip is folded, the host is the grip and one row, and the console starts three pixels under that pane's Clear button. While the strip is closed, the console starts at the pane top. While the strip is rising, the console follows that pane's slide. A fold sets that slide to settled and the host becomes the folded row.

A grip drag still writes one shared height. An unfolded pane uses that height. A folded pane keeps the short row until it is unfolded. The notebook strip keeps its own chrome. Clear keeps the fold and the height and wipes that pane's text. v shows the text. ^ folds it.

**semantic-key:** PK-7713

## What stays

There is no wire move and no vault column. The fold is not saved. Open as URL, Copy, the Paste menu line, Select All, the Clear Buffer menu line, Page Up, Page Down, Line Up, and Line Down stay off this plate. This plate is not a check of the user combo, the remembered chooser, the shell finesse, the clear button, the SSH mood, or a tool hook.

The closed-window check counted 182 passes. Two sessions can disagree: one pane shows its text and the other keeps its text hidden. The shared chrome does not take the fold. Close returns the mark on the pane that closed and leaves the neighbor folded. An open console, a folded console, a rising console, and a console under a settled fold each sit three pixels under that strip's Clear button. A closed strip leaves the console at the pane top. A fold during a rise settles that pane. The check did not start a session and it did not open the window.

**Truth.** Fold and unfold belong to the clicked pane. The neighbor keeps its fold, its text, and its console top. An open or folded strip places the SSH console three pixels under that pane's Clear. A closed strip places the console at the pane top. A fold stops that pane's rise and settles the folded row. Close returns v on the pane that closed. The file stores height and closed, not the fold. A grip drag still writes one shared height.

**Reason.** One click was folding every SSH thinking pane, so a neighbor lost its text and its console top.

**Avoidances.** Do not fold a pane that was not clicked. Do not store the fold. Do not put the fold on the shared chrome. Do not move the console off the three-pixel gap while the strip is open or folded. Do not leave the console below the pane top when the strip is closed. Do not let a fold keep a rise running. Do not let Close return v on a neighbor. Do not let a new thought clear the fold. Do not let Clear drop the fold or the height. Do not give a folded pane the shared height until it is unfolded. Do not resize the notebook strip from this fold. Do not add a vault column or a wire move.

**semantic-key:** PK-7714

# Think strip

One line sits on the card that is running. The glyph cycles `|` `/` `-` `\` every 120 ms. The glyph is `#00FF66`. The word thinking is `#00D7FF`. The line leaves when that run ends. A quiet card stays clear. The card does not store the line.

**semantic-key:** PK-7715

## The line

The Rust form is one line on the card whose running flag is set. One execute is in flight, so one card wears the line. A reply token keeps the line. The end of the run clears it. A fault clears it. A quiet card stays clear.

The frame is a pure function of elapsed milliseconds divided by 120. The paint asks for another frame after 120 ms. The window thread paints it. The card does not store the line. The line is paint. The simple thread still holds.

The same window stays. The same read thread stays. There is no new crate and no new channel. The outline page seats the card line. This page seats the think strip.

The closed-window check counted 62 passes. The window stayed closed. It did not start a session and it did not open the window.

**semantic-key:** PK-7716

## The lesson beside it

The earlier plate seats one spinner for the vault on screen. It shows while any cell of that vault is running and hides when none are. The timer stays on the event thread. The glyph is `|` `/` `-` `\` on a 120 ms step. The glyph color is `#00FF66`. The word thinking is `#00D7FF`.

The yellow strip is a second piece. It waits for kind `t`. This client keeps kind `t` off the card. The local engine is the runtime on this plate. The yellow text, the minimize toggle, and the elapsed clock stay held on this plate.

**semantic-key:** PK-7717

## Still off this plate

This line is not a second thread, a stored thought, a minimize toggle, a thinking checkbox, a vault column, or a pin match. Burner stays held. The yellow thought text stays held. The elapsed clock stays held. The store stays held. The saved-tab restore, the land on the newest turn, and close-restore stay held. The dragged divider, the saved ratio, the shell column, and the hide arrows stay held. Duplicate group and pipes stay held. Splits, the paste editor, import, the font spinner, and a modal stay held. The library page and the two VT100 plan pages stay as they are.

**semantic-key:** PK-7718

## Plates recorded after the strip

New scrolls the canvas to the newest card. That plate's closed-window check counted 63 passes.

The bar between the canvas and the inspector moves for this session. The open share stays 0.72. A drag keeps the share until the window closes. The saved ratio stays held on that plate. That plate's closed-window check counted 64 passes.

The running card counts the seconds beside thinking. The count starts when the run starts. The shape is `m:ss`, and `h:mm:ss` once an hour has passed. The line leaves when the run ends. The yellow thought text stays held on that plate. That plate's closed-window check counted 65 passes.

The share comes back the next time the window opens. One arrow hides the canvas. The other hides the inspector. The other arrow brings the hidden pane back. That plate's closed-window check counted 67 passes.

The marks are filled off-gray buttons. A triangle sits in each one. Collapsed to the right, the inspector column is closed. That plate's closed-window check counted 68 passes.

The buttons stay on the divider. The width is the bar. A triangle sits inside each button. That plate's closed-window check counted 68 passes.

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window. That plate's closed-window check counted 70 passes.

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone. That plate's closed-window check counted 80 passes.

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows `no group selected` in yellow on the status line. That plate's closed-window check counted 81 passes.

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says `pipe`. The token stays in the prompt. That plate's closed-window check counted 82 passes.

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`. That plate's closed-window check counted 83 passes.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. That plate's closed-window check counted 84 passes.

The folded line stays inside the column. The bar stays on the share. A member stays at width 20. The stored text stays. Run still sends it. The door file stays. That plate's closed-window check counted 85 passes.

A click on a folded card arms that card. The sapphire moves. The fold stays. The stored text stays. Run still sends it. The door file stays. That plate's closed-window check counted 86 passes.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. The reply window stays held. The door file stays. That plate's closed-window check counted 87 passes.

A right-click on the card opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu. That plate's closed-window check counted 88 passes.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog. That plate's closed-window check counted 89 passes.

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. That plate's closed-window check counted 90 passes.

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark. That plate's closed-window check counted 91 passes.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. A local-engine run still sends. That plate's closed-window check counted 92 passes.

The group row starts hidden. v shows it and ^ hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. That plate's closed-window check counted 93 passes.

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. That plate's closed-window check counted 94 passes.

**Truth.** The running card wears one line. The glyph cycles `|` `/` `-` `\` from elapsed milliseconds divided by 120, in `#00FF66`, beside the word thinking in `#00D7FF`. The paint asks again after 120 ms on the window thread. A reply token keeps the line. The end of the run clears it. A fault clears it. A quiet card stays clear. The card does not store the line. Kind `t` stays off the card. The local engine is the runtime on this plate.

**Reason.** The card in flight has to show that the run is still moving, and the line has to leave with the run, without a second thread or a stored thought.

**Avoidances.** Do not put the line on a quiet card. Do not store the line on the card. Do not clear the line while a reply token is still arriving. Do not keep the line after the run ends or after a fault. Do not paint kind `t` on the card. Do not add a second thread, a channel, or a crate. Do not start a session or open the window for the closed-window check.

**semantic-key:** PK-7719

# Token ring on the other client

The estimate sits on the south of the inspector, under the scroll. T sits before that line and wears the same face as P. T opens a ring. This client's crate stays unwritten on this plate.

**semantic-key:** PK-7720

## The comparison

The ring compares the estimate to the selected model's context window. The count is the same estimate the body used. This open does not ask the host. Local-engine rows share the session num-ctx. A hosted-model row keeps the completion cap. A missing id stays unreported. OK and the close icon dismiss the window.

There is no wire move, no vault column, no compaction, and no change to the packed prefix. The working set stays as it stands.

The first closed-window check counted 58 passes. It held the math, the model rows, and the painted arc. It did not open the window. A later live confirmation of the ring added no new requirement.

**semantic-key:** PK-7721

## The windows in the file

The application file holds `casserole.grok.context-windows` and `casserole.pool.context-window`. Pool is 256000. The rows grok-4.7, grok-4.6, and grok-4.5 are 500000. grok-4.3 and the grok-4.20 rows are 1000000. grok-build-0.1 is 256000. Local-engine rows still share the session num-ctx. A hosted-model row keeps the completion cap. A missing id stays unreported. This open does not ask the host.

The budget check counted 59 passes. The window stayed closed. The crate stays unwritten. A later live confirmation of the budgets added no new requirement.

**Truth.** On the other client, T wears P's face and opens a ring under the inspector. The count is the body estimate. Local-engine rows use the session num-ctx. Pool is 256000. grok-4.7, grok-4.6, and grok-4.5 are 500000. grok-4.3 and the grok-4.20 rows are 1000000. grok-build-0.1 is 256000. A missing id stays unreported. The completion cap stays on a hosted-model row. The ring does not ask the host. This crate stays unwritten.

**Reason.** The estimate lived on the card body, and the context windows for those model ids were not in the application file the ring reads.

**Avoidances.** Do not leave the estimate on the card body. Do not give T a face other than P's. Do not use a count other than the body estimate. Do not give local-engine rows a file window. Do not hide the completion cap. Do not invent a window for a missing id. Do not change 256000, 500000, or 1000000 for the rows named above. Do not ask the host on open. Do not compact, move the wire, or add a vault column. Do not write this crate on this plate.

**semantic-key:** PK-7722

# Token ring

The estimate sits on the south of the inspector, under the scroll. T sits before that line and wears the same face as P. T opens a ring.

**semantic-key:** PK-7723

## What the ring compares

The ring compares the estimate to the selected model's context window. The count is the same estimate the body used. An empty count stays 1. This open does not ask the host.

Local-engine rows share the session num-ctx. A hosted-model id uses the window named for that id and still shows the completion cap. A missing id stays unreported. Pool uses `casserole.pool.context-window`. The crate reads `casserole.grok.context-windows` and `casserole.pool.context-window` because the ring needs them. It drops every other key in that file.

**semantic-key:** PK-7724

## Dismiss

OK and the close icon dismiss the window. There is no wire move, no vault column, no compaction, and no change to the packed prefix. The working set stays as it stands. The other client is not edited.

The closed-window check counted 171 passes. It held the math, the model rows, and the windows from that file. It did not open the window. A later live confirmation added no new requirement.

**Truth.** T, wearing P's face, sits before the estimate on the south of the inspector and opens a ring. The count is the body estimate, and an empty count is 1. Local-engine rows use the session num-ctx. A hosted-model row uses `casserole.grok.context-windows` for that id, keeps the completion cap, and stays unreported when the id is missing. Pool uses `casserole.pool.context-window`. Every other key in that file is dropped. The ring does not ask the host. OK and the close icon dismiss it.

**Reason.** The estimate lived on the card body, and there was no ring on this client that compared that count to the selected model's context window.

**Avoidances.** Do not leave the estimate on the card body. Do not give T a face other than P's. Do not use a count other than the body estimate. Do not treat an empty count as anything but 1. Do not give local-engine rows different windows. Do not hide the completion cap on a hosted-model row. Do not invent a window for a missing id. Do not read any file key except the two context-window keys. Do not ask the host on open. Do not compact, move the wire, or add a vault column. Do not change the working set.

**semantic-key:** PK-7725

# Tool bar

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing.

**semantic-key:** PK-7726

## The mark

The mark sits on the canvas, after Expand All. v shows the row under the footer and the mark becomes ^. ^ hides the row and the mark returns to v. The bar starts closed. The choice stays for this window and is not written. Close the window and open it again, and the row starts hidden.

**semantic-key:** PK-7727

## Search

Search is the control on this row. Enter in the field runs the same search. The field keeps its text for this window. The field is not written. Search reads the prompt and the reply. An empty field matches nothing. Percent and underscore are ordinary characters. The first matching turn in canvas order is selected. The letter stays. A miss says `no hits`. A hit says `search` and the count.

Export, Import, Pull, and To terminal stay off this row. The standing model stays the local engine. The door file stays. The store file stays.

**semantic-key:** PK-7728

## What stays held

Export, canvas import, Pull, To terminal, the yellow thought text, the minimize toggle, and the mood layer stay held. Splits, the paste editor, the font spinner, and a modal stay held. The library page and the two VT100 plan pages stay as they are. The other client is not edited.

The closed-window check counted 94 passes. The window stayed closed. It did not start a session and it did not open the window.

**Truth.** The tool row starts hidden, v shows it, ^ hides it, and that choice is not saved. Search, after Expand All's mark, selects the first turn whose prompt or reply contains the field. An empty field matches nothing. Percent and underscore stay ordinary. The letter stays. A miss says `no hits`. A hit says `search` and the count.

**Reason.** The row had no show and hide on this client, and a search that treated percent or underscore as wildcards, or that changed the card letter, would not match the other client's field.

**Avoidances.** Do not start the row open. Do not save the show and hide choice. Do not put the mark anywhere but after Expand All. Do not match an empty field. Do not treat percent or underscore as wildcards. Do not change the letter. Do not select a later match ahead of the first in canvas order. Do not write the field. Do not put Export, Import, Pull, or To terminal on this row. Do not move the door or the store. Do not start the held plates on this row.

**semantic-key:** PK-7729

# Tools menu

Tools sits on the main menu. Show Thinking is the item under it. A click reveals the thinking pane of the plate in front.

**semantic-key:** PK-7730

## The menu

Tools sits between File and Token Calc. Help stays last. The same menu sits at the top while the control bar is open, and on the tab row while that bar is folded.

**semantic-key:** PK-7731

## The click

A click closes the menu and reveals the thinking pane of the plate in front.

On the VT100 plate the focused session strip opens. A closed strip rises from the top of that pane. A folded strip opens so the text is visible. A strip that is already showing stays at its height. The log stays. The closed mark on the file stays. A parked pane stays as it is.

On the canvas plate the thinking pane opens. A closed pane rises from the buttons. A folded pane opens so the text is visible. A pane that is already showing stays at its height. The log stays. The closed mark on the file stays. The VT100 strip stays as it is.

**semantic-key:** PK-7732

## What the click does not do

There is no file write, no log clear, and no send. The other client keeps its menu. The body stays yellow. The closed-window check counted 188 passes. It held Tools between File and Token Calc, and Show Thinking on the plate in front. It did not start a session and it did not open the window.

**Truth.** Tools is between File and Token Calc, Help stays last, and the same menu is on the open control bar and on the folded tab row. Show Thinking closes the menu and opens the thinking pane of the plate in front, from the top of a VT100 pane or from the buttons on the canvas. An already visible pane stays at its height. The log and the closed mark stay. A parked pane stays. The body stays yellow.

**Reason.** The main menu had File, Token Calc, and Help, and no way to reveal the thinking pane of the plate that was in front.

**Avoidances.** Do not place Tools anywhere but between File and Token Calc. Do not move Help off the last place. Do not use a different menu when the control bar is folded. Do not leave the menu open after the click. Do not reveal a pane that is not the plate in front. Do not change the height of a pane that is already showing. Do not clear the log or write the closed mark. Do not move a parked pane. Do not let the canvas click change the VT100 strip, or the VT100 click change the canvas pane. Do not paint the body anything but yellow. Do not send.

**semantic-key:** PK-7733

# View checks

Each Expanded View check is saved with that cell. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell's choice.

**semantic-key:** PK-7734

## The file

A checked cell id is saved in `~/.casserole/aetheris-expanded.yaml`, version 1. An absent id is unchecked. Uncheck drops the id. Delete drops that cell. A version other than 1 is left unread and is not overwritten. A nil cell is not saved. A new turn keeps the prior cell's choice, and that check is saved with the new id. A memory door has no path and writes nothing. The file is not a vault column.

The other client keeps Expanded View in a window set, with the same fresh, Clear, and new-turn rules.

**semantic-key:** PK-7735

## The icon

The plate name stays Aetheris. The window caption is Aetheris Rust. The operating system reads that caption on the taskbar hover. The icon is the Eye of Horus, scaled to 64, with the casserole mark behind it. The other client's frame wears the Eye of Horus from `/icons/aetheris.jpg`.

**semantic-key:** PK-7736

## The thin bar

The thin bar sits at the bottom of the Aetheris pane. It shows the model and the user name. West reads `vault: disk`, then AMWP or raw, then air-gap, then engine. An empty target shows as `ollama`. The vault word on this client is `disk`. East is the sanitized user name while the mux is up. The fill is #171717. The ink is #b4b4b4. The pad is 3 and 8. The other client's south bar is thin, west is the vault line, and east is the user name while the focused session is a connected AMWP user, in those same colors and that same pad.

The link chip stays on the connection bar. The mismatch tail stays off, because this client has one air-gap word. There is no wire move. The working set stays as it stands. The other client is not edited.

The closed-window check counted 168 passes. It held the round trip of the check, the caption and the icon bytes, and the status line. It did not open the window.

**Truth.** Expanded View is version 1 of `~/.casserole/aetheris-expanded.yaml`, one checked id per cell, unchecked on a fresh canvas and on Clear, copied onto a new turn's id, and dropped on Delete or uncheck. The caption is Aetheris Rust. The icon is the Eye of Horus at 64 with the casserole mark behind it. The thin bar is #171717 on #b4b4b4 with pad 3 and 8, west `vault: disk`, AMWP or raw, air-gap, and engine (`ollama` when empty), east the sanitized user while the mux is up.

**Reason.** The check lived only in the window, so a new turn lost it, and the taskbar and the thin bar were not yet showing the caption, the icon, the model, and the user.

**Avoidances.** Do not store the check in the vault. Do not read or overwrite a version other than 1. Do not save a nil cell. Do not write from a memory door. Do not start a fresh canvas or Clear as checked. Do not drop the prior choice on a new turn. Do not leave a deleted cell's id in the file. Do not change the plate name away from Aetheris or the caption away from Aetheris Rust. Do not scale the icon to anything but 64 on this plate. Do not drop the casserole mark behind it. Do not put the link chip on the thin bar. Do not show a mismatch tail. Do not invent an engine word other than `ollama` when the target is empty. Do not move the wire or the working set.

**semantic-key:** PK-7737

# Reply window bound

The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark.

**semantic-key:** PK-7738

## The bound

The window stays inside the current viewport. A request larger than that view stops at the view edge. A request that hangs past the edge moves in until the whole window is inside. A short side grows to 200 by 120 when the view can hold that. A view smaller than that minimum keeps the window inside the view. This finesse checks the current viewport. The other client checks every screen in screen pixels.

The reply scrolls. Its height does not stretch the window. A shrink stays at the size the person asked for.

**semantic-key:** PK-7739

## The controls

Find, when the bar is open, sits above the reply. Copy and Close sit under the reply. Those rows stay inside the window. The reply uses the height that is left. The title bar carries the close mark in the upper right. The south Close stays. Both close marks return to the canvas. Esc still closes the find bar. The size is still remembered for this vault user.

The other client keeps the reply in a dialog that can sit on any screen. Its title bar has the standard close mark. Close and Copy sit on the south edge. The reply scrolls between the find bar and that south edge.

**semantic-key:** PK-7740

## What stays off this bound

This is not a second operating-system window. The door file stays. The closed-window check counted 91 passes. It did not start a session and it did not open the window.

**semantic-key:** PK-7741

## Plates recorded after this bound

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. A local-engine run still sends. That plate's closed-window check counted 92 passes.

The group row starts hidden. v shows it and ^ hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. That plate's closed-window check counted 93 passes.

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. That plate's closed-window check counted 94 passes.

Burner, the group bar, the yellow thought text, the minimize toggle, the tool bar, the mood layer, the store, splits, the paste editor, canvas import, the font spinner, and a modal stay off this bound. The library page and the two VT100 plan pages stay as they are.

**Truth.** The reply window is clamped to the current viewport. A larger request stops at the edge. A request past the edge moves fully inside. A short side grows to 200 by 120 when the view can hold it, and a smaller view keeps the window inside anyway. A shrink stays. Find sits above the reply. Copy and the south Close sit under it. The upper-right close mark and the south Close both return to the canvas. The reply scrolls and does not stretch the window.

**Reason.** The window could be dragged off the desktop view, and a larger size hid Find, Copy, and Close.

**Avoidances.** Do not let the window leave the current viewport. Do not grow a side past the view edge. Do not refuse a shrink. Do not stretch the window to the reply. Do not let Find, Copy, or Close leave the window. Do not drop the upper-right close mark or the south Close. Do not let Esc close the window. Do not check every screen in screen pixels on this plate. Do not open a second operating-system window. Do not move the door file.

**semantic-key:** PK-7742

# Reply window layer

The reply window floats above the Canvas tab and the P strip. It is the modal. It takes the pointer over the canvas.

**semantic-key:** PK-7743

## The order

The reply window is foreground. It moves to the top of that order on every open frame. The canvas north strip stays registered, so it paints before the window. The glass sits above the canvas and below the window. The glass covers the screen above the canvas. The modal keeps the pointer on the window.

A click on the tab, on P, or on Run stops on the window. P's tip stays with the canvas. Find, Close, and the saved size stay.

**semantic-key:** PK-7744

## The other client's window

The other client opens View as a modal dialog. The dialog takes the parent. Close returns to the canvas. The size is remembered. This plate keeps that window and raises it over the canvas. It is not a new window and not a lower strip.

**semantic-key:** PK-7745

## What stays

There is no side move on Mood or Model Config. There is no vault write. The other client is not edited. The working set stays as it stands. The closed-window check counted 164 passes. It held the order, the paint, and the click. It did not start a session and it did not open the window.

**Truth.** The reply window is the foreground modal, above the Canvas tab and P, and above the glass. The glass is above the canvas. A click on the tab, on P, or on Run stops on the window. The north strip still paints first because it stays registered. Find, Close, and the saved size stay. P's tip stays with the canvas.

**Reason.** The connection bar folds, and the reply window was still under the Canvas tab and the P strip, so a click there reached the canvas instead of the modal.

**Avoidances.** Do not leave the reply window under the tab or under P. Do not put the glass above the window or below the canvas. Do not let a click on the tab, on P, or on Run pass through the modal. Do not move P's tip onto the window. Do not drop Find, Close, or the saved size. Do not open a second window. Do not change Mood or Model Config. Do not write the vault. Do not unregister the north strip.

**semantic-key:** PK-7746

# Reply window

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user.

**semantic-key:** PK-7747

## The mark and the body

The View mark sits on the card, before Copy. It stays off until the reply has a character past a space. That is the Copy gate. The other client trims. The two marks agree.

The window shows the stored reply. The ink is #ffff55. The field is #1a1a1a. Close sits east. Copy sits beside it and copies the whole reply. The find field stays. A quiet copy leaves the status line.

**semantic-key:** PK-7748

## Find

Find is Ctrl+F. The bar opens. A later Ctrl+F steps to the next match. An empty field keeps the bar. Esc closes the bar and leaves the window open. Previous wraps to the end. Next wraps to the start. The match ignores letter case. The scan moves past each match. Two thousand matches is the cap. The count is a space, the word none, a pair of numbers, or that pair with a plus. Other matches are #6b5a1e. The current match uses the notice ink.

**semantic-key:** PK-7749

## While it is open

The window name matches the other client's window. The title dash sits before the turn number. Ctrl+Enter does not run while the window is open. A second View waits until Close. This plate paints the window on the Aetheris surface. A second operating-system window stays off. The other client's dialog blocks the notebook. This plate blocks card edits, Run, and the chords. The divider and the inspector stay live.

**semantic-key:** PK-7750

## The remembered size

The size file is `view-bounds.txt` under `.aetheris` and the vault user. The four keys are x, y, width, and height. A short side is under 200 by 120 and loads as the default. The default is 720 by 480. A rectangle off the current viewport loads as the default. An empty user writes nothing. A closed session writes nothing. This plate checks the current viewport and stores its own rectangle. The other client checks every screen in screen pixels, centers a missing file on the notebook, and uses the vault username. This plate opens a missing file at 720 by 480 in the default place, and uses the door user while a session is open.

A letter fold that becomes two characters stays as written. The other client folds one UTF-16 unit.

The door file stays. The store stays. The standing model stays the local engine. The closed-window check counted 90 passes. It did not start a session and it did not open the window.

**semantic-key:** PK-7751

## Plates recorded after this window

A later bound keeps the reply window inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark. That bound's closed-window check counted 91 passes.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. A local-engine run still sends. That plate's closed-window check counted 92 passes.

The group row starts hidden. v shows it and ^ hides it. The choice is not saved. Group, Join, and Ungroup sit on that row. That plate's closed-window check counted 93 passes.

The tool bar starts hidden. v shows it and ^ hides it. The choice is not saved. Search sits on that row. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change the letter. That plate's closed-window check counted 94 passes.

Those four plates are not this window. Burner, the yellow thought text, the minimize toggle, the tool bar, the group bar, the mood layer, the store, the door, splits, the paste editor, canvas import, the font spinner, a modal, and a pipe rewrite stay off the window itself. The library page and the two VT100 plan pages stay as they are.

**Truth.** View, before Copy, opens a read-only reply only after the reply has a character past a space. The ink is #ffff55 on #1a1a1a. Find is Ctrl+F, a later Ctrl+F steps, Esc closes the bar only, and the match ignores case up to two thousand hits. The size is `view-bounds.txt` for the door user, default 720 by 480, and a side under 200 by 120 or a rectangle off the viewport loads as that default. Ctrl+Enter does not run while the window is open.

**Reason.** The reply had no read-only window, Find had no key, and the size was not remembered for the user who had the session open.

**Avoidances.** Do not enable View on a blank or space-only reply. Do not make the window editable. Do not close the window when Esc closes the find bar. Do not run Ctrl+Enter or open a second View while the window is open. Do not copy less than the whole reply. Do not honor letter case in the match. Do not scan more than two thousand matches. Do not drop the notice ink on the current match or #6b5a1e on the others. Do not write a size for an empty user or a closed session. Do not use the vault username on this plate. Do not open a second operating-system window. Do not block the divider or the inspector. Do not fold a two-character letter the way one UTF-16 unit folds. Do not move the door or the store.

**semantic-key:** PK-7752

# VT100 shell

The SSH plate grows the later shell. Reconnect, Clear Terminal, Send file, New Term, and Close Pane sit on the open bar. A split nests panes. The divider starts at half.

**semantic-key:** PK-7753

## The buttons

The buttons follow the connection. Connected enables Disconnect, Reconnect, and Send file, and disables Connect. Connecting disables Connect, Disconnect, Reconnect, and Send file. Disconnected enables Connect and disables the other three. New Term stays enabled. Clear Terminal stays enabled. Close Pane is enabled when more than one pane is open. The folded bar keeps the mark, the status chip, and the plate tabs. The buttons show when the bar is open. File and Help stay above the fold.

**semantic-key:** PK-7754

## Splits

New Term asks Horizontal, Vertical, or Cancel. Horizontal is side by side. Vertical is top and bottom. The divider starts at half. A drag stays between 0.15 and 0.85 and is not saved. Ctrl+Shift+D, Ctrl+Shift+E, and Ctrl+Shift+W split and close. A new pane copies the font, starts disconnected, and takes the focus. The last pane cannot close. Close shuts down the focused session and the sibling takes the place. Restart starts with one pane. The focused leaf wears a one-pixel ring when more than one pane is open. A background pane keeps its own scope, capped at 256, and does not open Model Config.

**semantic-key:** PK-7755

## Paste, Send file, and Import

Ctrl+V and Ctrl+Shift+V open Edit paste. The hint says Ctrl+Enter or OK sends to the terminal. Enter inserts a newline. The cap is 65536. Over the cap says `Paste exceeds 65536 characters.` The send is always bracketed and keeps LF. An empty paste writes nothing. A disconnected send says `Not connected`.

Send file reads the file and opens that editor. Import reads a text file and inserts it at the cursor, and does not open the editor. A disconnected import says `Not connected` and does not open the dialog. An empty file says `File is empty.` Over the cap says `File exceeds 65536 characters.` A binary sample says `File looks binary. Send a UTF-8 text file.` The shell chooser title is Open and the filter is all files. The cell import chooser keeps the Import title and the txt filter.

**semantic-key:** PK-7756

## Font, clear, and exit

The font control is minus and plus. The range is 8 through 36, step 1. The key is `ui.terminal.font.size`. A missing key is 8. A stored value outside that range clamps. The first paint uses 8, so the first paint is smaller than the seated 14. Clear Terminal clears the active screen. Paste mode, the keypad mode, and the other screen stay.

Exit asks `Exit the SSH client?` Yes saves the restored frame, the product tab, and the connection fields, and closes every pane. The keys are `ui.frame.x`, `ui.frame.y`, `ui.frame.width`, `ui.frame.height`, and `ui.frame.maximized`. A width under 400 becomes 1024. A height under 300 becomes 968. A missing maximized key stays maximized. A stored false opens restored. A stored true opens maximized. The open-maximized flag stays true.

The connected-user list is the focused pane and each parked connected pane. A pick focuses that pane, then the keep-or-switch rule runs. A second pane of the same user skips the canvas wipe.

The shell file keeps the font and the frame. There is no vault column and no new channel. The working set stays as it stands. Open as URL, Copy, the Paste menu line, Select All, the Clear Buffer menu line, Page Up, Page Down, Line Up, and Line Down stay off this plate. Select model stays as it is. Tool hooks stay off. The other client is not edited.

The closed-window check counted 177 passes. It held the buttons, the splits, the paste, the import, the menus, the active-screen clear, the font, and the frame. It did not start a session and it did not open the window. A later live confirmation added no new requirement.

**Truth.** The open SSH bar adds Reconnect, Clear Terminal, Send file, New Term, and Close Pane, enabled by the connection rules above. Splits start at half, drag from 0.15 through 0.85, and the last pane stays. The focused leaf wears a one-pixel ring when a second pane is open. Paste and Send file use the editor, cap 65536, always bracketed, LF kept. Import inserts at the cursor. The font is 8 through 36, missing key 8, first paint 8. Clear Terminal clears only the active screen. Exit saves the frame and closes every pane. A width under 400 becomes 1024. A height under 300 becomes 968. A missing maximized key stays maximized. A second pane of the same user skips the canvas wipe. A background scope caps at 256.

**Reason.** The SSH plate did not yet have the later shell: the split keys, the paste editor, the font, the frame restore, and the exit that closes every pane.

**Avoidances.** Do not enable Connect while connected or connecting. Do not enable Disconnect, Reconnect, or Send file while connecting or disconnected. Do not disable New Term or Clear Terminal. Do not close the last pane. Do not save the divider. Do not let a drag leave 0.15 through 0.85. Do not start a new pane connected. Do not let a background pane open Model Config or grow its scope past 256. Do not send an empty paste. Do not confirm a paste or a file over 65536. Do not open the editor for Import. Do not open Import while disconnected. Do not accept an empty or binary file. Do not give the shell chooser the cell import's title or txt filter. Do not let the font leave 8 through 36. Do not let the first paint use 14. Do not let Clear Terminal drop paste mode, the keypad mode, or the other screen. Do not exit without the question. Do not open restored when the maximized key is missing. Do not wipe the canvas for a second pane of the same user. Do not add a vault column.

**semantic-key:** PK-7757

# Console menu

The focused console and a parked pane share one menu. The order is Open as URL, Copy, Paste, Select All, Clear Buffer, Page Up, Page Down, Line Up, Line Down, Import..., then Select model.

**semantic-key:** PK-7758

## The gaps

A right-click on the focused console, and a right-click on a parked pane, open that same menu. A gap sits before Clear Buffer, before Page Up, before Line Up, and before Import. From the top the order is Open as URL, Copy, Paste, Select All, a gap, Clear Buffer, a gap, Page Up, Page Down, a gap, Line Up, Line Down, a gap, Import..., Select model.

**semantic-key:** PK-7759

## Each line

Open as URL stays dark unless the highlight is a URL. A click opens that URL and leaves the highlight.

Copy stays dark unless a highlight is present. A click copies the highlight and leaves the highlight.

Paste stays dark when the clipboard has no string. A click inserts through the bracketed paste. The paste keys still open the editor. A dead session says `Not connected`.

Select All highlights the buffer.

Clear Buffer stays dark on the alternate screen. A click clears the history and the lines above the cursor. The cursor line stays at the top. The view returns to the tail.

Page Up and Page Down stay dark on the alternate screen. A click moves the console view by one page. The page is the row count. The motion stays on this console view.

Line Up and Line Down stay dark on the alternate screen. A click moves the console view by one line. The motion stays on this console view.

Import... stays. Select model stays, with its tip.

**semantic-key:** PK-7760

## What the menu does not take

The card menu stays. A drag still copies the selection. Ctrl+V and Ctrl+Shift+V still open the paste editor. Find stays off this menu. There is no new key binding and no wire move. The console view moves on its own scroll. The thinking strip keeps its scroll. The other client is not edited.

The closed-window check counted 188 passes. It held the menu order and the kept cursor line. It did not start a session and it did not open the window.

**Truth.** One right-click menu serves the focused console and a parked pane, in the order and with the four gaps above. Open as URL, Copy, and Paste stay dark until they have a URL, a highlight, or clipboard text. Clear Buffer, Page Up, Page Down, Line Up, and Line Down stay dark on the alternate screen. Clear Buffer keeps the cursor line at the top and returns the view to the tail. Page motion uses the row count. Line motion moves one line. Both stay on this console view.

**Reason.** The other client's console menu already carried these lines, and this console did not.

**Avoidances.** Do not give the focused console and a parked pane different menus. Do not reorder the lines or drop a gap. Do not enable Open as URL, Copy, or Paste when they have nothing to do. Do not clear the highlight on Open as URL or Copy. Do not let Paste skip the bracketed paste. Do not enable the buffer, page, or line lines on the alternate screen. Do not clear the cursor line. Do not let page or line motion leave this console view or move the thinking strip. Do not add Find. Do not change the card menu, the drag copy, or the paste-editor keys. Do not add a key binding or a wire move.

**semantic-key:** PK-7761

# Model Config on the VT100 plate

The Model Config window is drawn once for the plate on screen. The SSH plate and the notebook share that draw.

**semantic-key:** PK-7762

## When it opens

Select model still asks the shell. The answer opens the modal. A raw console stays shut, and the status line says `raw console has no modal`. A shut modal stays shut. The cell modal stays on the cell. The window does not open before the shell answers. This client does not write the console-model file. There is no second modal and no new crate dependency. The store pin stays turso 0.8.3.

**semantic-key:** PK-7763

## The send head

A canvas Run, a glass send, a group send, and a pipe send share one head. When the switch sends, the system lines stay the host sentence, then the saved mood string, then the activate line. The turns follow. The server replaces the host sentence. The working set stays as it stands. The other client is not edited.

The closed-window check counted 149 passes. It did not start a session and it did not open the window. A later live confirmation covered both plates and added no new requirement.

**Truth.** One Model Config draw serves the SSH plate and the notebook. Select model asks the shell, and the answer opens that modal. A raw console stays shut with `raw console has no modal`. A shut modal stays shut. The cell modal stays on the cell. This client does not write the console-model file. The store pin stays turso 0.8.3.

**Reason.** Select model on the VT100 left the window down, because the draw belonged to the notebook plate and was not drawn for the plate on screen.

**Avoidances.** Do not draw a second modal. Do not open the modal before the shell answers. Do not open it for a raw console. Do not open a modal that was shut. Do not move the cell modal off the cell. Do not write the console-model file from this client. Do not add a crate dependency. Do not move the store pin off turso 0.8.3. Do not change the send head or the working set.

**semantic-key:** PK-7764

# Console mode on the status line

When the VT100 console is in focus, the thin status line names the console mode. The wire word is VT100 or PTY. The word comes from `ui.connection.mode`.

**semantic-key:** PK-7765

## The word

The word sits between `vault: disk` and `air-gap:`. The token `pty`, in any letter case, paints PTY. A connect then asks for the term `xterm-256color`. A missing token paints VT100. Any other token paints VT100. A connect then asks for the term `vt100`. A missing file stays missing until a save, and the default term is `vt100`.

A stored port, including 22, wins. A bad port does not.

**semantic-key:** PK-7766

## What stays on the lines

The canvas strip keeps AMWP when the mux has answered. It keeps raw when the mux has not. The canvas extra stays on that strip. The link word stays. `mood active` stays when that gate is on. The vault stays. The air-gap stays. The engine stays. The east name stays the user. The token estimate stays.

This plate reads `ui.connection.mode` and does not write it. There is no Mode combo. The connection bar stays as it stands. The PTY mode list stays empty. The other client keeps its south label. The closed-window check counted 188 passes. It held VT100 and PTY on the shell line, AMWP and raw on the canvas line, a stored `pty` token, a stored `PTY` token, an unknown token, and the default `vt100`. It did not start a session and it did not open the window.

**Truth.** Focus on the VT100 console paints PTY only for the token `pty` in any letter case, and VT100 for a missing or any other token, in the wire slot between `vault: disk` and `air-gap:`. PTY connects as `xterm-256color`. VT100 connects as `vt100`. The key is not written. The canvas strip stays AMWP or raw. A stored port, including 22, wins, and a bad port does not.

**Reason.** The thin line already carried a wire word, and that word was not the console mode the door would use on the next connect.

**Avoidances.** Do not paint PTY for any token other than `pty`, ignoring case. Do not paint VT100 for a `pty` token. Do not ask for `xterm-256color` unless the token is `pty`. Do not write the mode key from this line. Do not add a Mode combo. Do not fill the PTY mode list. Do not move AMWP or raw onto the VT100 line. Do not drop the link, the mood word, the vault, the air-gap, the engine, the user, or the token estimate. Do not let a bad port replace a stored port, including 22. Do not create a missing file before a save.

**semantic-key:** PK-7767

# VT100 status facts

The VT100 thin status line keeps the link word, then `mood active` when that gate is on, then the vault, the wire, the air-gap, and the engine. The east name stays the user. The canvas strip stays as it is.

**semantic-key:** PK-7768

## The link

The VT100 plate wears one thin status line at the bottom. The west word stays the link. Connecting stays Connecting. Connected shows `Connected host:port`. Disconnected stays Disconnected. A disconnect reason keeps the em dash. The link chip stays on the connection bar. This plate does not rewrite that chip.

**semantic-key:** PK-7769

## Mood and the facts

When the SSH mood gate is on, `mood active` follows that link word. The mood string stays off the strip. The facts follow, in this order: vault, wire, air-gap, then engine. The words are the same words the canvas strip paints. A blank air-gap reads `unset`. An empty engine reads `ollama`. The vault word is `disk`.

The east name stays the user while the mux is up. The canvas strip keeps the canvas extra at its own tail. That extra stays off this join.

There is no wire move and no vault column. The other client's status label is not edited. The closed-window check counted 188 passes. It held the joined line. It did not start a session and it did not open the window. A later live confirmation added no new requirement and did not move the source. The count stayed 188.

**Truth.** The VT100 status line is link, then `mood active` only while that gate is on, then vault, wire, air-gap, and engine, with the user on the east while the mux is up. Connected is `Connected host:port`. A disconnect reason keeps the em dash. Blank air-gap is `unset`. Empty engine is `ollama`. Vault is `disk`. The mood string and the canvas extra stay off this line.

**Reason.** The canvas strip already named the vault, the wire, the air-gap, and the engine, and the VT100 line had only the link word.

**Avoidances.** Do not drop the link word. Do not put `mood active` on the line while the gate is off. Do not print the mood string on the strip. Do not reorder vault, wire, air-gap, and engine. Do not invent an air-gap word other than `unset` when it is blank. Do not invent an engine other than `ollama` when it is empty. Do not change the vault word away from `disk`. Do not move the user off the east. Do not put the canvas extra on this join. Do not move the link chip off the connection bar. Do not rewrite the canvas strip. Do not move the wire or add a vault column.

**semantic-key:** PK-7770

# VT100 think ink

The thinking output on the VT100 strip is selection sapphire lightened 33 percent toward white. The bytes are 115, 174, and 255.

**semantic-key:** PK-7771

## The colors that stay

Selection sapphire is #2e86ff. Lightened 33 percent toward white, the body is 115, 174, 255. The chat text on the terminal stays yellow. The terminal reply stays bright yellow. The model tag stays cyan, #00d7ff. The clear mark stays the selection sapphire, #2e86ff. The notebook thinking strip stays yellow, #ffff55.

**semantic-key:** PK-7772

## Which strip

The match is the VT100 strip body on the other client's window thread. The simple thread still holds. This crate still paints the thought body yellow. That crate paint waits until the catch-up is named. This plate is not a chat-text recolor, not a notebook recolor, not a model-tag recolor, and not a paint of this crate. The working set stays as it stands.

The gate check counted 1 pass and held the bytes. The window stayed closed.

**Truth.** On the other client's VT100 thinking strip, the thought body is #2e86ff lightened 33 percent toward white, bytes 115, 174, and 255. The terminal chat and the terminal reply stay yellow. The tag stays #00d7ff. The clear mark stays #2e86ff. The notebook body stays #ffff55. This crate's thought body stays yellow until the catch-up is named.

**Reason.** The VT100 strip was painting the thought body in the same yellow as the chat, and the body needed the lighter sapphire so the thought could be told from the reply.

**Avoidances.** Do not leave the VT100 thought body at yellow on that strip. Do not use a lightening other than 33 percent toward white. Do not change the bytes away from 115, 174, and 255. Do not recolor the chat text, the terminal reply, the model tag, the clear mark, or the notebook body. Do not paint this crate's thought body with those bytes on this plate. Do not change the working set.

**semantic-key:** PK-7773

# VT100 thought strip

The strip is born on the north of the terminal and grows downward. The grip is the bottom edge. Dragging down grows the pane.

**semantic-key:** PK-7774

## The file

Height and closed live in `~/.casserole/aetheris-vt100-think.yaml`. Version is 1. A version other than 1 is left unread and is not overwritten. A missing file leaves the pane shut. The file keeps version, closed, and height. It keeps no cells. The stored height stays between 48 and 2000. The thought text is not written in that file and is not written to `aetheris-thinking.txt`. The notebook strip stays above the footer on `aetheris-think.yaml` and `aetheris-thinking.txt`.

**semantic-key:** PK-7775

## What opens it

A nil-cell kind `t` with a body opens this strip when console pool thinking is on. The tag is `pool`. The end of that turn closes the block. Kind `c` on the nil cell stays off this strip. A canvas cell keeps its own gate and its own log. The assistant reply still paints on the VT100.

The pane starts hidden. A closed pane stays closed until a thought opens it. v folds for the session and does not write the file. Clear wipes the text and leaves the pane up. The content sits above the grip, so the grip is the bottom edge against the terminal. This strip is not built on the bottom and then moved.

There is no right-click Select model on this plate, and no change to the chosen model file. There is no new crate dependency. The store pin stays turso 0.8.3. The other client is not edited.

**semantic-key:** PK-7776

## The send head

A canvas Run, a glass send, a group send, and a pipe send share one head. When the switch sends, the system lines stay the host sentence, then the saved mood string, then the activate line. The turns follow. The server replaces the host sentence. The working set stays as it stands.

The closed-window check counted 146 passes. It did not start a session and it did not open the window.

**Truth.** The VT100 thought strip is born on the north and grows down from a south grip. Height from 48 through 2000 and the closed mark live in `~/.casserole/aetheris-vt100-think.yaml` version 1, with no cells and no thought text. A nil-cell kind `t` opens it when console pool thinking is on, tagged `pool`, and the end of that turn closes the block. v folds without writing. Clear wipes the text and leaves the pane up. The notebook files stay the notebook's.

**Reason.** A strip built on the bottom and moved would cover the terminal from the wrong edge, and writing the thought text into the notebook log would mix the console pool thoughts with the canvas log.

**Avoidances.** Do not build the strip on the bottom and move it. Do not put the grip anywhere but the south edge. Do not store a height outside 48 through 2000. Do not read or overwrite a version other than 1. Do not write cells or thought text into the VT100 file or into `aetheris-thinking.txt`. Do not change the notebook files. Do not open the strip for kind `c` or when console pool thinking is off. Do not use a tag other than `pool` on this plate. Do not leave the block open after the turn ends. Do not show a pane that starts hidden, or a closed pane, until a thought opens it. Do not let v write the file. Do not drop the pane on Clear. Do not give a canvas cell this strip's gate. Do not stop the assistant reply from painting on the VT100. Do not add Select model or change the chosen model file on this plate. Do not move the store pin off turso 0.8.3.

**semantic-key:** PK-8222

# VT100 token estimate

The VT100 strip shows `est. tokens` for the SSH pane in focus. Another pane shows that pane's count. Token Calc is its own item on the main menu. It opens the token window.

**semantic-key:** PK-7777

## The line

The VT100 plate wears one thin status line at the bottom. The west word stays the link, then `mood active` when that gate is on, then the vault, the wire, the air-gap, and the engine. The estimate follows those words. The words are `est. tokens` and the count. The east name stays the user.

**semantic-key:** PK-7778

## The count

The count is the heuristic on the focused pane's console text. Scrollback and the screen are that text. Trailing blank rows stay off. An empty pane estimates as 1. Focus puts that pane's grid on the strip. Another pane shows its own count. The count does not ask the host.

**semantic-key:** PK-7779

## Token Calc

Token Calc sits on the main menu by itself, between File and Help. A click opens the token window. The open does not ask the host and does not send. On the VT100 plate the ring uses that pane's estimate. On the canvas plate the ring keeps the inspector estimate.

There is no wire move. The other client's status label is not edited. The closed-window check counted 188 passes. It held the joined line and Token Calc on the menu. It did not start a session and it did not open the window.

**Truth.** The VT100 status line ends the facts with `est. tokens` and the focused pane's heuristic count, or 1 when that pane is empty. Trailing blank rows are not text. Another pane shows its own count. Token Calc sits between File and Help, opens the token window without asking the host or sending, and the VT100 ring uses that pane's estimate while the canvas ring keeps the inspector estimate.

**Reason.** The thin line already carried the link and the canvas facts, and it did not show the focused session's estimate or a menu item that opens the token window.

**Avoidances.** Do not put the estimate ahead of the facts. Do not share one count across panes. Do not count trailing blank rows. Do not estimate an empty pane as anything but 1. Do not ask the host for the count or on Token Calc. Do not send from Token Calc. Do not use the inspector estimate on the VT100 ring. Do not replace the inspector estimate on the canvas ring. Do not place Token Calc anywhere but between File and Help on this plate. Do not move the wire.

**semantic-key:** PK-7780

# Yellow thought

Wire kind `t` appends to a session log above the footer. The body is #FFFF55. A new block opens with a cyan bold tag, `── word ──`, in #00D7FF. The text stays off the card.

**semantic-key:** PK-7781

## The block

The same cell keeps appending. A reply, an end, a fault, or Stop closes the block and leaves the text. The next thought starts a new block. The word is the model id. A blank id reads the provider. A blank provider reads `model`. The word stops at 80 UTF-16 units. A newline, a return, and the rule character drop out of the tag word.

The local engine paints. The hosted model paints when that card's switch is on. Pool paints when that card's switch is on. Both switches start off. They ride the in-memory card. The checkbox and the file stay held.

**semantic-key:** PK-7782

## The pane

The pane sits above the footer. The field is #1A1A1A. Three lines are the view. The running card keeps the green spinner, the word thinking, and the elapsed clock. The log stays for this window. Connect, Clear, and Disconnect leave it. The log is not stored on the turn. Kind `c` stays off the log and off the card.

**semantic-key:** PK-7783

## What this plate leaves held

The minimize toggle, v and ^, Close, a Clear of the log, the north-edge height, the saved log file, and the checkbox that survives a restart stay held. There is no vault column, no door write, no store write, no new channel, and no edit of the other client. The mood layer stays off.

The other client's paint returns unless the kind is `t` and the text is not empty. A fresh cell opens a block. The same cell appends the body. A finished run sets the block closed and leaves the text.

The closed-window check counted 102 passes. It held the yellow body, the cyan tag, the same-cell append, both gates, a closed block, and a log that Clear leaves in place. It did not start a session and it did not open the window.

**Truth.** Kind `t` with text appends to a three-line log above the footer, body #FFFF55 on #1A1A1A, and a new block opens with a cyan bold `── word ──` in #00D7FF. The word is the model id, else the provider, else `model`, cut at 80 UTF-16 units, with newline, return, and the rule character removed. The same cell appends. A reply, an end, a fault, or Stop closes the block. The local engine always paints. The hosted model and pool paint only while that card's switch is on, and both switches start off. Connect, Clear, and Disconnect leave the log. Kind `c` stays off.

**Reason.** The running card already showed a spinner, the word thinking, and the elapsed clock, and the thought text had nowhere to go that was not the card.

**Avoidances.** Do not paint a body other than #FFFF55 or a tag other than #00D7FF. Do not put the text on the card. Do not append across a cell change. Do not leave the block open after a reply, an end, a fault, or Stop. Do not drop the text when the block closes. Do not let the word pass 80 UTF-16 units or keep a newline, a return, or the rule character. Do not paint the hosted model or pool while that switch is off. Do not start either switch on. Do not wipe the log on Connect, Clear, or Disconnect. Do not store the log on the turn. Do not let kind `c` into the log or onto the card. Do not build the minimize toggle, the saved log, or the restart checkbox on this plate. Do not change the green spinner, the word thinking, or the elapsed clock.


**semantic-key:** PK-7784
