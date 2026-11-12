# aetheris-client::chrome

Component file. It carries how this package is built.

**Depends:** [rust/aetheris/aetheris-client/aetheris-client](../aetheris-client.spec.md), [rust/aetheris/aetheris-client/src/amwp](amwp.spec.md), [rust/aetheris/aetheris-client/src/door](door.spec.md), [rust/aetheris/aetheris-client/src/session](session.spec.md), [rust/aetheris/aetheris-term/aetheris-term](../../aetheris-term/aetheris-term.spec.md), [rust/aetheris/aetheris-term/src/grid](../../aetheris-term/src/grid.spec.md), [java/org/aetheris/swing/swing](../../../../java/org/aetheris/swing/swing.spec.md), [java/org/casserole/llm/llm](../../../../java/org/casserole/llm/llm.spec.md), [java/org/roux/roux](../../../../java/org/roux/roux.spec.md), [java/org/aetheris/wire/wire](../../../../java/org/aetheris/wire/wire.spec.md), [java/org/aetheris/ssh/ssh](../../../../java/org/aetheris/ssh/ssh.spec.md)

## Add Cell

When a group exists, the merge cell's main button bar carries `Add Cell`, and that button adds a new empty member to the merge group. The older ways of adding a cell stay.

The button label is `Add Cell`. It sits on the merge cell's main button bar only. A member card does not show it. An ordinary card does not show it. The canvas footer does not show it. The second bar still holds the execution target, Group, Join, and Ungroup, and it still starts hidden. New still appends one ordinary turn at the end of the canvas. Group still turns an ordinary card into member 1 and inserts a merge under it. Join still attaches an existing ordinary card to the armed member's group and leaves that card where it sits. Ungroup still pulls one card out.

Add Cell adds a new empty member to that merge's group. The new member takes the next column, one past the highest member column. It is placed with the members, before the merge. The prompt and the reply stay empty. The new member copies the last member's execution target, Ollama think choice, Grok settings, and expanded choice. When the group has no member, the new cell stays on an empty target and starts unchecked. Focus moves to the new prompt.

Both clients paint the button. There is no new package, no new public type, no new global key, no new vault column, no new channel, and no new wire message type. Nothing moves beyond the copy and the focus, and both clients make that same copy. No system line is added, removed, or reordered. The send set stays as it is.

**Truth.** `Add Cell` appears only on the merge cell's main button bar. It inserts an empty member in the next column, one past the highest member column, with the members and before the merge. The new prompt and reply are empty. The new member copies the last member's execution target, Ollama think choice, Grok settings, and expanded choice. With no member to copy, the target is empty and the expanded choice starts unchecked. Focus moves to the new prompt. New, Group, Join, and Ungroup keep their existing behavior. Both clients do this.

**Reason.** A merge group needs an obvious way to add a cell for someone who does not know the older path. That older path stays, because it will still be useful.

**Avoidances.** Do not show `Add Cell` on a member card, an ordinary card, or the canvas footer. Do not change New, Group, Join, or Ungroup. Do not show the second bar, which still starts hidden and still holds the execution target, Group, Join, and Ungroup. Do not place the new member after the merge or in any column but the next one. Do not fill the prompt or the reply. Do not skip the copy of the last member's target, think choice, Grok settings, and expanded choice. Do not give a memberless group a target or a checked expanded choice. Do not leave focus off the new prompt. Do not add, remove, or reorder a system line. Do not add a package, a public type, a global key, a vault column, a channel, or a wire message type.

**semantic-key:** PK-0005 PK-0006 PK-0007 PK-0008 PK-0009 PK-0010 PK-0011 PK-0012

## The file import

A file import streams text of any size from disk into the VT100 session or into a cell input pane. A large file gets a polite line, and the import continues. The user owns a size overflow. A thrown error is shown in red.

The read streams from disk in chunks of 8192 bytes. It does not allocate one array of the file length. The session import places the text in the VT100 session. The prompt import places the text in the cell input pane. Neither path stops at 65536 characters or at 1 MiB.

The polite line is `This file is large. The import continues.` A session file longer than 65536 bytes says that line, then the import continues. A prompt file longer than 1 MiB says that line, then the import continues. A file at either bolt does not say the line. The line is not an error. On the prompt, the landed status keeps the line ahead of `imported into turn N`.

A thrown error is the red error. The session shows the error dialog. The prompt shows the red error slide. The Rust session paints that dialog in the red ink. This row does not add a later graceful handler.

An empty session file stays refused. A binary file stays refused. A merge cell stays refused. An output-only cell stays refused. Send file stays at 65536 characters. The paste editor stays at 65536 characters. A cell written to the terminal stays at 65536 characters. The `.aetheris` deck import stays at 1 MiB.

There is no new package, no new public type, no new global key, no new vault column, no new channel, and no new wire message type. Nothing beside these two imports moves. The send set stays as it is. Both clients stream the same two imports.

**Truth.** Session import and prompt import stream from disk in 8192-byte chunks and do not stop at 65536 characters or at 1 MiB. A session file longer than 65536 bytes, and a prompt file longer than 1 MiB, show `This file is large. The import continues.` and then continue. A file exactly at either bolt does not show the line. On the prompt, that line stays ahead of `imported into turn N`. A thrown error is a red error dialog on the session and a red error slide on the prompt. An empty session file, a binary file, a merge cell, and an output-only cell stay refused. Send file, the paste editor, and a cell written to the terminal stay at 65536 characters. A `.aetheris` deck import stays at 1 MiB.

**Reason.** File import was stopping at a 65536 boundary, likely the length of one buffer. The text has to stream from disk at an arbitrary size, into the VT100 session or into a cell input pane. A large file deserves a polite warning, and the user remains responsible if a size overflow still occurs. A thrown error is simply shown in red.

**Avoidances.** Do not allocate one array of the file length. Do not stop either import at 65536 characters or at 1 MiB. Do not treat the polite line as an error. Do not say the line for a file that is exactly 65536 bytes on the session path or exactly 1 MiB on the prompt path. Do not put the polite line after `imported into turn N`. Do not add a later graceful overflow handler. Do not accept an empty session file, a binary file, a merge cell, or an output-only cell. Do not raise the 65536 limit on Send file, the paste editor, or a cell written to the terminal. Do not raise the 1 MiB limit on a `.aetheris` deck import. Do not add a package, a public type, a global key, a vault column, a channel, or a wire message type.

**semantic-key:** PK-0013 PK-0014 PK-0015 PK-0016 PK-0017 PK-0018 PK-0019 PK-0020 PK-0021 PK-0022 PK-0023 PK-0024 PK-0025 PK-0026

## The row height on a group

An added cell, and every cell in a group, follows the row-height control on the first button bar. When that count changes, the merge cell resizes to the same number of visible input rows.

That control is the saved visible-row count. The height is that count times the mono line. The line is floored at 16. Eight pixels sit under the lines. A missing count is 4. A count under 1 is 1. That is the font-height sizing function.

An added cell uses that height for its prompt. Its reply uses that height when Expanded View is unchecked. When Expanded View is checked and the reply has text, the reply stays whole, and a group cell's reply is never shorter than that height. An empty added cell therefore shows the same row window as the initial member. Add Cell still copies the last member's execution target, Ollama think choice, Grok settings, and expanded choice.

Every open cell in a group uses that function. A sequential card outside a group keeps the rule it already has. A folded card stays a brief line.

The merge cell has no prompt. Its reply pane uses that same height, including when the reply is empty. When Expanded View is checked and the reply has text, the reply stays whole, and the pane is never shorter than that height. Changing the row control changes that height, so the merge resizes with the new count.

There is no new package, no new public type, no new global key, no new vault column, no new channel, and no new wire message type. Nothing beside this height moves. The send set stays as it is. Both clients use the same function. `Add Cell` stays on the merge bar.

**Truth.** The visible-row count, missing as 4 and never under 1, times a mono line floored at 16, plus 8 pixels under the lines, is the height. An added cell's prompt uses it. An unchecked reply uses it. A checked reply that has text stays whole and is never shorter than it. Every open group cell uses it. The merge has no prompt, and its reply uses that height even when empty, with the same floor when Expanded View is checked and the reply has text. A sequential card outside a group is unchanged. A folded card stays a brief line. Both clients do this.

**Reason.** An added cell was not taking the same height as the initial cell. Every cell in a group, including the merge when the count changes, has to follow the visible-row control.

**Avoidances.** Do not size an added cell differently from the initial member. Do not let a group reply fall shorter than the row window when Expanded View is checked and the reply has text. Do not clip a checked reply that has text. Do not give the merge a prompt. Do not leave an empty merge reply off that window. Do not apply this function to a sequential card outside a group. Do not grow a folded card past a brief line. Do not drop the Add Cell copy of the last member's execution target, Ollama think choice, Grok settings, and expanded choice. Do not use a line under 16 pixels or a pad other than 8. Do not treat a missing count as anything but 4, or a count under 1 as anything but 1. Do not add a package, a public type, a global key, a vault column, a channel, or a wire message type.

**semantic-key:** PK-0027 PK-0028

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

## The bar press

A press on a pulled-out scroll bar leaves the wheel on the master canvas.

A touch does this, and a drag does this. The Swing client returns focus to the canvas scroller. The Rust client leaves the wheel on the canvas. A press on the text still focuses that text. The bar still drags. The hit strip is the scroll bar width, on the right. These panes scroll vertically.

The Swing helper is `barHit`. The Rust helper is `bar_press`. Both clients change.

**Truth.** Pressing or dragging the overlay scroll bar does not take the wheel off the master canvas. Pressing the text still focuses the text. The bar still drags. The strip is the bar width, on the right, and the panes scroll vertically.

**Reason.** A press meant to grab the bar was moving focus onto the bar, so the next wheel notch scrolled the wrong pane.

**Avoidances.** Do not steal focus from a text press. Do not disable the drag. Do not move the strip off the right edge or off the scroll-bar width. Do not make these panes scroll horizontally. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.

**semantic-key:** PK-0046 PK-0047 PK-0048

## The black ground

The About box drops the casserole picture. The eye is half the previous size, the art stays, and the ground is black with square corners.

The About edge is 64. Light, dull corner pixels flood to black. Saturated eye pixels stay. The corners stay square. The jpg stays. The window icon stays.

The Swing helpers are `iconGround` and `fillIconGround`. The Rust helpers are `icon_ground`, `fill_icon_ground`, and `about_app_rgba`. Both clients change.

**Truth.** About shows no casserole picture. The eye is drawn at half size on a black ground with square corners. The edge is 64. Dull light corners become black. Saturated eye pixels, the jpg, and the window icon stay.

**Reason.** The About art was sitting on a light rounded ground with a second picture beside the eye, so the mark did not read as one black square.

**Avoidances.** Do not round the corners. Do not replace or redraw the eye art. Do not darken saturated eye pixels. Do not drop the jpg or the window icon. Do not use an edge other than 64. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.

**semantic-key:** PK-0049 PK-0050

## The narrower calc

The Token Calc window is 20 percent narrower.

The Swing client sets the width to four fifths of the packed width and keeps the packed height. The Rust default size is 512 by 420. The ring stays 220. The title stays Token window.

Both clients change.

**Truth.** Token Calc is four fifths of its packed width in Swing, at the packed height. Rust opens it at 512 by 420. The ring diameter stays 220. The title stays Token window.

**Reason.** The packed window was wider than the ring it shows. Twenty percent off the width keeps the height and the 220 ring.

**Avoidances.** Do not change the packed height. Do not resize the ring off 220. Do not rename Token window. Do not use a Rust default other than 512 by 420. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.

**semantic-key:** PK-0051 PK-0052

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

## Duplicate group on the menu

On a right click, Duplicate group shows only when the cell is a group or a merge cell. Any cell in that group can open it.

A lone cell omits the line. Import stays. Transfer Conversation stays. Shift-Ctrl-G stays.

Both clients change.

**Truth.** The context line Duplicate group is present for a merge cell and for every member of that group, and absent for a lone cell. Import, Transfer Conversation, and Shift-Ctrl-G stay.

**Reason.** The line was offered on a cell that is not in a group, where duplicating a group has nothing to copy.

**Avoidances.** Do not show Duplicate group on a lone cell. Do not hide it on a member that is not the merge cell itself. Do not remove Import or Transfer Conversation. Do not remove Shift-Ctrl-G. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.

**semantic-key:** PK-0061 PK-0062 PK-0063 PK-0064 PK-0065 PK-0066 PK-0067 PK-0068 PK-0069 PK-0070 PK-0071 PK-0072 PK-0073

## Collapse all keeps the one-line card

Collapse All and Expand All only fold each cell to the one-line card. They do not take a cell off the canvas.

Collapse All sets the fold bit and does not remove the card. Expand All clears the fold bit, so the cards return. The one-line brief is still the card. A fold releases an id. It never adds one. Hide Collapsed is the only path that fills the conceal set. The deck omits a concealed id only while Hide is on. Collapse All writes Show into `aetheris-hide.yaml`, so a saved Hide cannot omit those folded cards on the next open. Hide Collapsed stays the only click that writes Hide. Show Collapsed stays the click that writes Show from that button. Other canvases keep their fold bits. The window omission lifts with the Collapse All write, the same flag Show Collapsed already clears.

The lock is `collapseKeepsTheCard` in Swing and `collapse_keeps_the_card` in Rust. A later change must not make that return false. The package method is `collapseKeepsTheCard`. The private helpers are `armConceal`, `refillConceal`, `releaseFold`, and `cellsForDeck`. The Rust helpers are `collapse_keeps_the_card`, `release_fold`, `conceal_collapsed`, `arm_conceal`, and `release_deck`.

The one side effect is that write of Show. The requirement is the lock. The sibling is Hide Collapsed. Both clients change.

**Truth.** Collapse All and Expand All toggle the one-line fold and leave every card on the canvas. Only Hide Collapsed adds a concealed id, and the deck hides those ids only while Hide is on. Collapse All persists Show in `aetheris-hide.yaml`. The lock `collapseKeepsTheCard` and `collapse_keeps_the_card` stays true.

**Reason.** Hide had been applied to every folded card, so Collapse All removed cards from the canvas instead of shortening them to one line. A saved `hide_collapsed: true` then kept them off on the next open.

**Avoidances.** Do not make Collapse All or Expand All fill the conceal set. Do not make a fold add an id. Do not omit a card while Hide is off. Do not let any click but Hide Collapsed write Hide. Do not let any button but Show Collapsed write Show, except the Collapse All write of Show that keeps the next open from hiding the folded cards. Do not clear fold bits on other canvases. Do not make the lock return false. Do not add a package, a public type, a public method beyond `collapseKeepsTheCard`, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.

**semantic-key:** PK-0074 PK-0075 PK-0076 PK-0077 PK-0078 PK-0079

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

## The heatbar estimate is the token ring count

The `est. tokens` word on the heat bar shows the chat-window memory, the same integer the token ring shows. It does not show a count from the beginning of the session.

On the Swing notebook the ring uses `tokenEstimate`. That integer is the estimate of the counted working set. Included cells are in. Excluded, input only, and output only stay out. The dip may still ask the host. The settled `prompt_eval_count` does not replace the word.

On the Swing SSH strip the ring uses the Roux tally for the focused user. The heat-bar word uses that tally. The last screen line is not the word. The client does not put rows into that book. An empty book sums to 0. The word shows that 0.

On the Rust canvas the ring uses the inspector estimate. The heat-bar word uses that estimate. The focused card prompt is not the word.

On the Rust SSH plate the ring and the heat bar already share `session_memory_text`. That shared count stays.

The track stays 5 pixels. The needle stays 2 by 9. The posts stay at 70 and 90. The lunge stays 6 pixels. One flash stays. The estimate stays the fallback. Empty text stays 1. A tokenizer library stays off this gauge. Rust gains no HTTP client.

The private field is `ring_used`. Both clients change. The wire does not gain, lose, or reorder a system line.

**Truth.** The heat-bar integer is the ring integer. Swing notebook: `tokenEstimate` of Included cells only, and a settled host count does not replace it. Swing SSH: the Roux tally, or 0 when the book is empty. Rust canvas: the inspector estimate, not the focused prompt. Rust SSH: `session_memory_text`. Geometry stays a 5 pixel track, a 2 by 9 needle, posts at 70 and 90, and a 6 pixel lunge.

**Reason.** The word was a running total from the start of the session, so it disagreed with the ring, which shows the working set.

**Avoidances.** Do not show a from-the-start total. Do not let `prompt_eval_count` replace the notebook word. Do not use the last screen line or the focused card prompt as the word. Do not count Excluded, input-only, or output-only cells. Do not change the 5 pixel track, the 2 by 9 needle, the posts, the lunge, or the single flash. Do not add an HTTP client to Rust. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.

**semantic-key:** PK-0105 PK-0106 PK-0107 PK-0108

## The pull-down arrow wears the emerald

The pull-down arrow paints in the existing hover emerald, #00FF66. The rest of the control stays.

Swing seats `seatComboArrow` on the client theme. The icon draws the downward triangle on the combo button. Rust seats `emerald_combo_arrow` and passes it to each combo icon. The field stays. The word stays. The pane arrows stay. The spinner stays.

The private Swing helper is `seatComboArrow`. The private Rust helper is `emerald_combo_arrow`. Both clients change. The wire does not gain, lose, or reorder a system line.

**Truth.** Every pull-down arrow is #00FF66, drawn as a downward triangle. The field, the word, the pane arrows, and the spinner do not take that ink.

**Reason.** The arrow was still the stock control color, so it did not match the emerald hover the rest of the chrome uses.

**Avoidances.** Do not recolor the field, the word, the pane arrows, or the spinner. Do not use an emerald other than #00FF66. Do not add a color key. Do not change the arrow into a different glyph. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, or a library. Do not edit the Rust manifest.

**semantic-key:** PK-0109 PK-0110 PK-0111 PK-0112 PK-0113

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

## A merge join names the model the reply came from

When a merge joins two or more cells, the join line names the model the reply came from. The word is the short name the cell caption already paints.

`concatJoin` and `concat_join` write `JOIN` plus the short model name. An empty local-model target still reads `ollama`. `ollama/qwen2.5-coder:7b` reads `qwen2.5-coder:7b`. `grok/grok-4` reads `grok-4`. Pool reads `pool`. A strip drops that line, a legacy full target line, and the legacy literal `JOIN ollama`. The caption field `shownTarget` stays.

The private Swing helpers are `joinWord` and `joinBannerLine`. The private Rust helpers are `join_word` and `join_banner_line`. Both clients change. Unrelated edits in the Rust chrome stay out of this change. The wire does not gain, lose, or reorder a system line.

**Truth.** A merge of two or more cells banners `JOIN` and the short model name: `ollama` when the local target is empty, `qwen2.5-coder:7b` from `ollama/qwen2.5-coder:7b`, `grok-4` from `grok/grok-4`, and `pool` for the pool. The strip does not keep the old full target line or the literal `JOIN ollama`. `shownTarget` stays the caption source.

**Reason.** The join line said a generic local target, including the frozen literal `JOIN ollama`, so the operator could not see which model the reply came from.

**Avoidances.** Do not write the full target on the join line. Do not keep the literal `JOIN ollama`. Do not rename an empty local target to anything but `ollama`. Do not shorten `qwen2.5-coder:7b`, `grok-4`, or `pool` any further. Do not change `shownTarget`. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.

**semantic-key:** PK-0127

## A canvas cell input updates the heatbar while the user types

The canvas cell input moves the heatbar estimated token count while the user types, the way the VT100 pane already does. Java moves. Rust moves. The formal plan is the page stamped from this sketch.

**semantic-key:** PK-0258 PK-0259 PK-0260

## The SSH mood flag is saved by canvas user

The Mood modal shows the SSH activation for the user that has focus on the Aetheris canvas. The flag is saved by user. An armed flag is remembered on that user's SSH panes at startup. Java moves. Rust moves. The formal plan is the page stamped from this sketch.

**semantic-key:** PK-0266 PK-0267 PK-0268 PK-0269 PK-0270 PK-0271 PK-0272 PK-0273 PK-0274

## The VT100 heatbar moves while the user types

The heatbar beat and the estimated token count move while the user types in an SSH VT100 pane. The number follows the focused pane when several panes are open. The canvas path already moves. Java moves. Rust moves. The formal plan is the page stamped from this sketch.

**semantic-key:** PK-0275 PK-0276 PK-0277 PK-0278 PK-0279 PK-0280 PK-0281 PK-0282 PK-0283

## The canvas switch leaves the SSH mood seated

Turning the canvas mood switch off dropped the SSH session mood while the Mood modal still said SSH Mood Active. Turning the switch back on left the session inactive. Java moves. Rust moves. The working word is NUT, and that word stays the owner's. This page does not mint a glossary row. The formal plan is the page stamped from this sketch.

**semantic-key:** PK-0285 PK-0286 PK-0287

## The heatbar uses the selected cell

The heatbar uses the model of the selected canvas cell. Token Calc already opens on that row. The heatbar was using the first known window. The formal plan is the page stamped from this sketch.

**semantic-key:** PK-0339 PK-0340 PK-0341 PK-0342 PK-0343

## Rust chrome

This page builds the Rust window. The map is [program-spine.md](../../../../README.md).

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

## Transfer conversation

Transfer Conversation copies the session onto the canvas, and copies the canvas back into the session.

The menu title is `Transfer Conversation`. On the canvas it sits after Duplicate group, on the prompt side and on the reply side. On the VT100 menu it sits after Import and before Select model. OK on the console clears the canvas and copies the session's user and assistant turns onto the cells. OK on the canvas clears the session turns and copies the canvas as input and reply rows. Cancel writes nothing and clears nothing. The configured prompt stays. A running canvas cell refuses the transfer and clears nothing. The confirm stays. The session door stays. Turns are copied as pairs.

There is no new package and no new public type. The public methods are `parseMessageTable`, `pairConversation`, `conversationTable`, `replaceTurns`, `applySessionReplace`, `replaceResult`, `replaceCanvas`, and `transferFromConsole`. There is no new global key, no new vault column, no new channel, no new wire message type, and no new library. No settings file is edited, and no dependency manifest is edited. Both clients carry the transfer.

**Truth.** Rust paints File, Tools, Token Calc, and Help, and the lines under File, Tools, and Help, in `#00ff41`. The word stays that green under the pointer. The order stays File, Tools, Token Calc, and Help. Show Thinking stays under Tools. `Help...` and `About Aetheris...` stay under Help. Token Calc still opens the token window. Context menus stay. Button hover stays `#00FF66`. Java does not change for the menu ink.

`Transfer Conversation` sits after Duplicate group on both the prompt side and the reply side of the canvas, and after Import and before Select model on the VT100 menu. OK on the console clears the canvas and copies the session's user and assistant turns onto the cells. OK on the canvas clears the session turns and copies the canvas as input rows and reply rows. Cancel writes nothing and clears nothing. The configured prompt stays. A running canvas cell refuses the transfer and clears nothing. Both clients carry it.

**Reason.** The Rust main menu words have to match the Java menu green. Separately, a conversation has to be able to move from the session onto the canvas and from the canvas back into the session, from a menu on each side, without opening a further row.

**Avoidances.** Do not paint the menu words any green but `#00ff41`. Do not drop the green while the pointer is on the word. Do not recolor context menus. Do not change the button hover from `#00FF66`. Do not change the tooltip wait, the folded row, the open divider, or the words SSH and Aetheris. Do not change the Java menu for this ink. Do not put `Transfer Conversation` anywhere but after Duplicate group on the canvas menus and between Import and Select model on the VT100 menu. Do not let Cancel write or clear. Do not drop the configured prompt. Do not transfer, and do not clear, while a canvas cell is running. Do not add a package, a public type, a global key, a vault column, a channel, a wire message type, or a library. Do not edit a settings file or a dependency manifest.

**semantic-key:** PK-4451 PK-4452 PK-4453 PK-4454 PK-4455 PK-4456 PK-4457 PK-4458 PK-4459 PK-4460 PK-4461 PK-4462 PK-4463 PK-4464 PK-4465 PK-4466 PK-4467 PK-4468 PK-4469 PK-4470 PK-4471 PK-4472 PK-4473 PK-4474

## The input pane

The canvas input pane takes the control-bar font size, and the input window grows with that size.

Plus and minus on the control bar still step the size. The typed text uses that size. When a caller has no control-bar size, the pane keeps the mono face. The line floor stays 16. Reply text stays on the size it already follows. The desk line stays. The desk draft stays. Dialogs stay. The letters stay I, E, P, and O. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. The hover emerald stays #00FF66.

The Rust helper is `pane_font`. The Rust client changes. The Swing client stays.

**Truth.** Input text and the input window follow the control-bar point size. A missing control-bar size keeps the mono face. The line floor is 16. The hover emerald remains #00FF66.

**Reason.** The control bar could be stepped to a new size while the input pane stayed on a smaller face, so the line being typed no longer matched the chrome.

**Avoidances.** Do not resize replies, the desk line, the desk draft, or dialogs from this rule. Do not drop the line floor below 16. Do not change the mark letters, the pin, the 11 pixel divider, the arrows, the tooltip wait, or #00FF66. Do not change the Swing client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.

**semantic-key:** PK-7142

## The default font

When the Rust client has no usable font size, it uses 14.

A missing key reads 14. An unreadable key reads 14. A memory door reads 14. A size that was stored is left as written. The allowed range stays 8 through 36. Plus and minus stay. This rule does not change the Swing default, which stays 8. The letters stay I, E, P, and O. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. The hover emerald stays #00FF66.

The Rust constant is `FONT_DEFAULT`. The Rust client changes. The Swing client stays.

**Truth.** The Rust missing, unreadable, or memory-door font size is 14. A stored size wins. The clamp remains 8 through 36. The Swing default remains 8 under this rule.

**Reason.** An absent key was filling a size the Rust window no longer uses as its start, so a first open came up smaller than the face the client is built around.

**Avoidances.** Do not overwrite a stored size with 14. Do not move the Swing default in this rule. Do not widen or shrink the 8 through 36 range. Do not change the mark letters, the pin, the 11 pixel divider, the arrows, the tooltip wait, or #00FF66. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the application yaml or the Rust manifest.

**semantic-key:** PK-4475

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

## The heatbar beat pulses on both tabs

The heat-bar beat pulses when the count changes, on the VT100 tab and on the canvas tab, the way the Swing composer beat already pulses.

The arm frame draws the 6 pixel lunge and does not ease it. A later frame eases one step, the lunge times 0.72, when 16 ms have passed, and asks for another frame in 16 ms. A faster frame does not spend that step. A long gap spends one step. The flash eases on that same step. The beat is stored on one fixed id. The 5 pixel track stays. The 2 by 9 needle stays. The posts stay. The inks stay. A held Roux count still replaces the glass.

The beat stays in paint and in the step. The Rust client changes. The Swing client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** On both tabs, a count change arms a 6 pixel lunge with no ease on that frame. Each later frame, after 16 ms, eases one step by multiplying the remaining lunge by 0.72 and schedules the next frame 16 ms out. A frame sooner than 16 ms does not step. A longer gap still steps once. The flash eases on that step. One fixed id holds the beat. A held Roux count still replaces the glass estimate. The track stays 5 pixels, the needle 2 by 9, and the posts and inks stay.

**Reason.** The Rust needle jumped to the new edge. The Swing beat already lunges 6 pixels and eases back, and the Rust tabs did not.

**Avoidances.** Do not ease on the arm frame. Do not step more than once per 16 ms. Do not spend the step on a faster frame. Do not spend more than one step across a long gap. Do not ease the flash on a different clock. Do not use a second id for the beat. Do not let the pulse replace a held Roux count. Do not change the 5 pixel track, the 2 by 9 needle, the posts, or the inks. Do not change the Swing client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a private method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.

**semantic-key:** PK-4484 PK-4485

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

## The mood modal uses the selected cell

The mood modal uses the model of the selected canvas cell. The other client already does.

mood_focus_card, mood_glass_target, and mood_glass_title follow that cell. The open waits until the place lands. The title and the send read that cell. The other client's moodCard and moodTarget already do this and do not change. No new public type, no new public method, and no new test class are added. The Rust client carries this. A known gap in paste undo stays. grid.rs stays.

The doors do not gain, lose, or reorder a line.

**Truth.** The mood window waits until the selected canvas cell is the place, then the title and the send read that cell. The private functions are mood_focus_card, mood_glass_target, and mood_glass_title. The Java path already uses moodCard and moodTarget and is not edited.

**Reason.** The modal was able to open on a cell other than the one selected, so the title and the send could name a different model than the card in front. Waiting until the place lands keeps the title and the target on that cell.

**Avoidances.** The Java client is not edited. The open does not read a cell before the place lands. The wire does not change. The known paste-undo gap stays.

**semantic-key:** PK-4493 PK-4494 PK-4495 PK-4496 PK-4497 PK-4498

## The heatbar beat pulses when the count changes

The full cycle is named, and the build is named. Rust moves. Java stays. Where this page is thinner than the locked row, the locked row wins.

**semantic-key:** PK-4499 PK-4500

## The heatbar beat pulses when the count changes

The pre-plan is locked, and a second revision is not opened. The full cycle is named, and the build is named, in the slice sentence itself. Rust is named. Java stays. The seats for the work are already filed on the locked row, and no extra cut is opened. Where this page is thinner than the locked row, the locked row wins. The formal plan is stamped from this page. Dependencies were filed before the landing was filed on the plan. The page carries a new-item mark.

**semantic-key:** PK-4571 PK-4572

## Canvas text panes can wear the Java cell frame

The Java cell frame is asked for on the Rust canvas text panes, and the quiet plate stays. The switch starts off. Rust moves. Java stays. The preserve token keeps the off path. The working word is NUT, and that word stays the owner's. This page does not mint a glossary row. The formal plan is the page stamped from this sketch.

**semantic-key:** PK-4573 PK-4574 PK-4575 PK-4576 PK-4577

## The Rust mood seat follows the Java desk

The Rust mood seat follows the Java desk. Java stays. Rust moves. The formal plan is the page stamped from this sketch.

**semantic-key:** PK-4585 PK-4586 PK-4587 PK-4588

## The Rust mood layer matches the smoked desk

The Rust client carries the smoked mood desk. The invitation, the pin, the debug head, the glass, and the wire follow the Java seat. Java stays. Rust moves. The formal plan is the page stamped from this sketch.

**semantic-key:** PK-4589 PK-4590 PK-4591 PK-4592 PK-4593 PK-4594

## The heatbar walk runs off the key

Rust cell input and VT100 typing stop waiting on the heatbar walk. Java already posts the newest beat and counts off the key. This page does not move that point. The formal plan is the page that names the build.

**semantic-key:** PK-4601 PK-4602 PK-4603 PK-4604 PK-4605 PK-4606 PK-4607 PK-4608 PK-4609 PK-4610 PK-4611 PK-4612 PK-4613 PK-4614 PK-4615 PK-4616 PK-4617 PK-4618

## One press seats one cell

One press on I, E, P, or O seats that cell only. This page does not move that point. The formal plan is the page that names the build.

**semantic-key:** PK-4633 PK-4634

## The folded thinking line clears the button bar

The folded thinking line clears BB1.

**semantic-key:** PK-4643

## The folded thinking line clears the button bar

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.

**semantic-key:** PK-4644

## Delete removes the armed card and stays off when the canvas would be empty

Delete sits beside Clear. It removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The standing model stays the local engine.

**semantic-key:** PK-4659 PK-4660 PK-4661 PK-4662 PK-4663 PK-4664 PK-4665 PK-4666 PK-4667 PK-4668

## Inspector line length

The inspector cuts each kept line to the count on the header, and the mood line stays at 80 characters.

**semantic-key:** PK-4669 PK-4670 PK-4671 PK-4672 PK-4673

## What the click does not do

There is no file write, no log clear, and no send. The other client keeps its menu. The body stays yellow. The closed-window check counted 188 passes. It held Tools between File and Token Calc, and Show Thinking on the plate in front. It did not start a session and it did not open the window.

**Truth.** Tools is between File and Token Calc, Help stays last, and the same menu is on the open control bar and on the folded tab row. Show Thinking closes the menu and opens the thinking pane of the plate in front, from the top of a VT100 pane or from the buttons on the canvas. An already visible pane stays at its height. The log and the closed mark stay. A parked pane stays. The body stays yellow.

**Reason.** The main menu had File, Token Calc, and Help, and no way to reveal the thinking pane of the plate that was in front.

**Avoidances.** Do not place Tools anywhere but between File and Token Calc. Do not move Help off the last place. Do not use a different menu when the control bar is folded. Do not leave the menu open after the click. Do not reveal a pane that is not the plate in front. Do not change the height of a pane that is already showing. Do not clear the log or write the closed mark. Do not move a parked pane. Do not let the canvas click change the VT100 strip, or the VT100 click change the canvas pane. Do not paint the body anything but yellow. Do not send.

**semantic-key:** PK-7733

## The path

The session returns this connection's table. The shell wraps that table as `session_export_result`. A table that does not fit one frame comes back as a short failure. The shell answers `session_export:` on channel A and does not paint the VT100 grid. The ask is channel A text on the nil cell. The reply is the same path. No system line is added, removed, or reordered.

This is not a menu item on the other client, not a new channel, not a new frame type, and not a canvas schema change. Canvas Export still writes a `.aetheris` file.

The client check counted 188 passes. The frame check counted 1 pass and held the empty table, one row, and the frame that does not fit. The window stayed closed. The check did not start a session and it did not open the window.

**Truth.** Export Session, after Import and before Select model, writes the answered connection's whole table to a `.toon` file and leaves the session in place. Cancel writes nothing. No session says `Not connected`. A raw console says `raw console has no Roux export`. A table that does not fit one frame is a short failure and does not open the dialog. The ask and the reply are channel A text on the nil cell, and the grid is not painted. Canvas Export stays `.aetheris`.

**Reason.** The console menu could not save the connection's message table, and a canvas export file is a different schema from that table.

**Avoidances.** Do not write on Cancel. Do not drop the session after a successful save. Do not export a raw console. Do not open the dialog when the table does not fit one frame. Do not paint the reply on the VT100 grid. Do not add, remove, or reorder a system line. Do not add a channel or a frame type. Do not change the canvas schema. Do not turn Canvas Export into a `.toon` file. Do not hide the item. Do not offer a parked pane a different item. Do not put the item anywhere but after Import and before Select model.

**semantic-key:** PK-7137

## Rust export writes an empty parent, branch main, and the card lock.

`export_toon` writes `version: 1`, the canvas name, and `cells:`. `parent_cell_id` is empty. `branch_id` is `main`. `head_lock` is `true` when the card is locked and `false` when it is not. A burner body is empty unless the operator keeps burners. The vault column `head_lock` is an integer, default 0, added on an older file that lacks the column. Load rewrites a stored `RUNNING` to `INTERRUPTED`.

**Truth.** The Rust version-1 export writes an empty parent, branch `main`, and `head_lock` from the card lock. Load rewrites stored `RUNNING` to `INTERRUPTED`.

**Reason.** This client does not copy the stored parent and branch into the file. It does record whether the card was locked. A card left `RUNNING` would look live after the process had already stopped, so the next open marks it interrupted.

**Avoidances.** Do not copy the stored parent and branch into this export. Do not omit `head_lock`. Do not leave `RUNNING` in place across a load. Do not write a burner body when the operator strips burners.

**semantic-key:** PK-7141

## The interrupt

The focused console reads the copy event as Ctrl-C and writes 0x03. The host writes ^C, cancels the in-flight console turn, and writes the prompt.

A canvas field and Model Config keep the copy chord. Cut and paste stay clipboard events. A mouse drag still copies.

The encoder in the terminal library already maps that key to 0x03. This plate does not change that encoder. This plate is not a canvas Run cancel. It does not change the other client.

No side move on this plate. The working set stays as it stands.

The check does not open a window and does not start a session.

**Truth.** On the focused console, the window copy event is byte 0x03. The host cancels the in-flight console turn on that byte. A field outside the console keeps the copy.

**Reason.** The window was handing Ctrl-C to the clipboard, so a running console request never saw the interrupt the host already honors.

**Avoidances.** Do not copy when the console grid holds the keys. Do not change the terminal key encoder. Do not treat this byte as a canvas Run cancel. Do not take cut, paste, or a mouse-drag copy off the clipboard.

**semantic-key:** PK-6866

## The two chords

Ctrl-Shift-C is a key chord. The clipboard receives the highlight. The console writes nothing, so the interrupt mark stays off the screen. An empty highlight leaves the clipboard as it is and still writes nothing.

Ctrl-C sends byte 0x03. The host still owns that interrupt.

The window reports bare Ctrl-C as a copy event. That event stays the interrupt. A Shift key that is still down keeps the chord a copy when the C event says shift is clear. Shift is what keeps the two chords apart.

Menu Copy is unchanged. A drag copy is unchanged. Paste is unchanged. The other client keeps Ctrl-C as the interrupt. The copy chord on this plate is the key on this client.

No side move on this plate.

The check does not open a window and does not start a session.

**Truth.** Ctrl-Shift-C copies a non-empty highlight and writes no bytes. Ctrl-C, and the window's bare copy event, write 0x03. A Shift key that is still down keeps a command-C as a copy even when that event's shift bit is clear.

**Reason.** The window was reporting Ctrl-C as a copy, so the interrupt never reached the host, and a Shift key that was still down was being read as the interrupt when the event itself said shift was clear.

**Avoidances.** Do not write bytes on Ctrl-Shift-C. Do not copy on bare Ctrl-C. Do not treat the window copy event as a clipboard copy. Do not clear the clipboard when the highlight is empty. Do not change menu Copy, drag copy, or paste.

**semantic-key:** PK-6867
