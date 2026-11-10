# aetheris-term

Concept file. It states what this program is.

**Depends:** This container names no other spec.

# Rust terminal

This page builds the Rust terminal emulator. The map is [program-spine.md](../../../java/org/aetheris/config/config.spec.md).


**semantic-key:** PK-4434 PK-4435 PK-4436

## First slice

The first slice is the SSH VT100 console pane, and the work is layered.

The overarching goal is a Rust Aetheris client for a person who wants Rust on the desk, and a proof of the method. The Java client stays the shop client. The Swing source stays put.

The shell map is the map of the whole shell. It is not the first build. One shot stays a story.

The door is one SSH session: host, port, user, password, Connect, and Disconnect. The mode is VT100 with TERM `vt100`. The stream is init-or-raw, the way the Java connector already does it. An answer of `0xAE` means the pane paints Scope T only. No answer means the raw PTY. Left, Right, Ctrl+Left, Ctrl+Right, and Esc pass through. The host owns the line editor and `cmd>`. The plate is the dark terminal, close enough for jazz.

Held for a later sentence at the start of this slice: splits, the paste editor, import, the font control, select model, the Aetheris tab, the canvas, and the Turso file. Turso stays the named vault engine. This slice does not open it. The vault-engine proof is the gate for the vault layer.

The cuts below were recorded on this section. The slice index is the discovery list. Each page name is a link.

The host family word slides in on its own cut. The focus record holds it. A Rust modal stays held.

A later cut takes the Aetheris tab off that held list. The tab lives on `rust-aetheris-tab-slice.draft.001.md`. The canvas stays held.

A later cut shows the Scope A body on the plate. The page is `rust-aetheris-scope-a-slice.draft.001.md`. The canvas foundation stays held.

A later cut seats the empty canvas. The page is `rust-aetheris-canvas-slice.draft.001.md`. Cards and the Turso file stay held.

A later cut seats one turn on the canvas.

**Truth.** The first slice is one SSH session in VT100 with TERM `vt100`, init-or-raw, `0xAE` for Scope T only, and the named keys passed through. Within the recorded cuts, the Aetheris tab leaves the held list, the Scope A body is shown, the empty canvas is seated, and one turn is seated on the canvas.

**Reason.** Layering keeps the console pane as the first plate, and each recorded cut takes one held piece onto the plate without making the whole shell the first build.

**Avoidances.** Do not treat the shell map as the first build. Do not open the Turso file in the first slice. Do not rebuild the host line editor. Do not paint more than Scope T when the answer is `0xAE`. Do not treat silence on init as anything but the raw PTY. Do not pull a later turn, a second card, or a vault open into this cut.

**semantic-key:** PK-6858

## 16. Primary first goal

The first goal is a working custom VT100 client that holds the same host conversation the Java SSH VT100 client already holds.

Custom means the character grid belongs to this client. JediTerm stays on the Java client. It is not the Rust grid. The byte parser was still an open choice when this goal was named. A later rule in this serial closes that choice on `vte`. This goal does not reopen it.

Working means a person can open one session and carry the VT100 conversation the Java client already carries. That conversation is the heart of the client. It is one SSH session. The mode is VT100, and `TERM` is `vt100`. The stream is init-or-raw, the same way the Java connector already does it. An answer of `0xAE` means the pane paints Scope T only. No answer means the raw PTY. The emulator paints Scope T. Scope A stays reserved for the canvas. The host line editor owns the line. The keys that editor owns pass through. On that heart they are Left, Right, Ctrl+Left, Ctrl+Right, and Esc. Later rules add the rest of the editor's keys. They do not take the line away from the host.

The canvas requirements, and the dependencies they sit on, are written down as the work proceeds. The shell map is that account. A choice made for this VT100 client that the canvas will later sit on is written in this serial in the same pass. The account already holds these.

| Dependency | Where it sits |
|---|---|
| One SSH session | The canvas rides the same login. Canvas tabs are conversations on that login. |
| Init-or-raw and Scope T | The emulator paints Scope T. Scope A stays reserved for the canvas. |
| Turso | Named with the vault. This goal does not open the Turso file. The vault proof stays the gate for the vault layer. |
| The server rules | They stay the rule when a later sentence names the canvas layer. The functional spine is the citation for those rules. |

This goal does not build the canvas. Accounting is the duty here. A later rule builds the plate. This goal also does not add a crate, a `Cargo.toml`, or a version pin. The window work stays open. The sentence that would name a build stays open.

**Truth.** The client this goal requires is one SSH session, a VT100 grid this program owns, `TERM` `vt100`, init-or-raw, Scope T on the emulator, and the host's line editor. Scope A is reserved for the canvas. The Turso file stays shut. The canvas is accounted and is not this goal's build.

**Reason.** The Java client already holds that conversation. A borrowed grid would leave the canvas with nowhere of its own to sit, and it would leave the Rust program unable to own the paint. Writing the shared choices in the same pass keeps a VT100 decision from stranding the plate that must ride the same login.

**Avoidances.** Do not put JediTerm on this client. Do not paint Scope A on the VT100 grid. Do not open the Turso file while proving this goal. Do not treat the canvas as built by this goal. Do not drop a VT100 choice the canvas will sit on. Do not add a package or a version pin from this goal alone.

**semantic-key:** PK-6859

## 18. Terminal component

The SSH terminal is an in-house component, modeled on JediTerm, its own library, reusable by any project, and a candidate to publish later.

The protocol stack stays `russh`. The VT100 byte parser is `vte`. `alacritty_terminal` was the other candidate and is not the parser. The in-house work is the component those two sit under: the grid, the widget, and the connector trait.

The split follows the JediTerm seam this client already uses. The widget paints. `TtyConnector` carries the bytes. `SshTtyConnector` is the connector, and Apache MINA sits behind it. This library copies that split, not those Java types. A connector trait. A widget. `russh` behind one connector.

The first behavior of the widget is the chat rule. Chat. The dark plate. The command layer slides in on the same widget.

A library any project can take stays free of Aetheris types. Init-or-raw and the Scope A listener stay with this client, in the connector the client supplies. The widget sees terminal bytes. The primary-goal account keeps that listener on the client. Publishing waits for a later sentence. The crate name waits with it.

| Piece | Owner |
|---|---|
| SSH handshake, login, channel, PTY request | `russh` |
| Byte parser | `vte` |
| Widget, grid paint, keys, settings | In-house library, shaped like that widget-and-connector split |
| Connector trait | In-house library. Read, write, resize |
| Init-or-raw, Scope T, Scope A listener | This client, the connector it supplies |
| Canvas, Turso, command verbs | Not this library. The primary goal and the chat rule still hold them |

This rule names the component and the split. It does not add a crate, a `Cargo.toml`, or a version pin. The window work stays open. The sentence that would name a build stays open. The Rust account of the library is the next rule. The grid stays in the library. The host paints.

**Truth.** The reusable library owns the widget, the grid, the keys, the settings, and a connector trait with read, write, and resize. `russh` owns the SSH session. `vte` owns the bytes. This client owns init-or-raw, Scope T, and the Scope A listener, and it supplies the connector. The library has no Aetheris types. The host paints. The crate name and publication wait.

**Reason.** A terminal that any project can take cannot know about the canvas, Turso, or this client's frames. The JediTerm split, widget against `TtyConnector`, is already how the bytes meet the paint. Keeping `vte` and `russh` under that component avoids a second stack beside them.

**Avoidances.** Do not build a private terminal that only this client can use. Do not put Aetheris types in the library. Do not move init-or-raw or the Scope A listener into the widget. Do not leave `alacritty_terminal` as a live second parser. Do not give the library the canvas, Turso, or the command verbs. Do not publish, and do not pin a crate name or a version, from this rule.

**semantic-key:** PK-6860

## 19. Terminal library serial

Terminal-library prose lives in its own specification. This client serial keeps only the way Aetheris uses that library.

From the sentence that splits them, the library specification is the home of the grid, the parser, the keys, and the connector. This serial keeps init-or-raw, the Scope A listener, the canvas account, and the order of slices. The first-slice rule, the chat rule, and the component rule stay the record of those decisions. The Java SSH client specifications stay the Java shell. The link is not a second copy of that tree.

The child constitution still governs this directory. The library specification does not carry a constitution of its own. The crate name waits. This rule does not add a crate.

**Truth.** One library specification holds the terminal component. This serial holds how the client uses it: init-or-raw, the Scope A listener, the canvas account, and slice order. One constitution governs the directory. The library specification has none of its own. No crate is created here, and the crate name is still unnamed.

**Reason.** The component is reusable, so its prose cannot live only inside this client's decisions. Copying the Java shell, or giving the library a second constitution, would make two laws for one grid.

**Avoidances.** Do not write further library prose into this serial. Do not copy the Java SSH specifications here. Do not give the library specification its own constitution. Do not name the crate from this split.

**semantic-key:** PK-6861

## 20. Rust account of the terminal library

The terminal library has a Rust account so the custom VT100 shell can become a real program. The account is sections 5 through 12 of the library specification. A specialist is not called on this pass.

The core is synchronous. `vte` is the parser. The grid stays in that library. The host paints. The host drives the connector. The core does not start a runtime, and it does not take a window crate.

How Aetheris uses the library is unchanged. Init-or-raw, the Scope A listener, the canvas account, and the slice order stay on this serial. The first-slice rule, the chat rule, and the component rule stay the record of those decisions. This account does not add a crate.

**Truth.** The library core is synchronous, parsed by `vte`, and it owns the grid. The host paints and the host drives the connector. The core starts no runtime and depends on no window crate. Client use stays on this serial.

**Reason.** A core that starts a runtime or opens a window cannot be the piece every project shares, and it cannot stay synchronous. The account exists so the shell is buildable without folding the client's canvas back into the library.

**Avoidances.** Do not start a runtime inside the core. Do not give the core a window crate. Do not move the grid out of the library. Do not move the paint into the library. Do not change init-or-raw, the Scope A listener, the canvas account, or the slice order from this account. Do not add a crate from the account itself.

**semantic-key:** PK-6862

## 21. Thread duty

The first implementations use one ordinary thread. A later shape is chosen only when that simple form has run out, and the owner is told before it is built.

The duty is section 13 of the library specification. The first Aetheris slice uses one ordinary thread on the read queue. The signs that call for one runtime live on that page. A judgment that the simple form still holds is recorded there and brought to the owner. A judgment that it has run out is written there and brought to the owner before that shape is built.

The stand-up keeps this thread. The read burst is coalesced on it. This duty does not add a crate.

**Truth.** One ordinary thread reads the queue. A runtime is not introduced while that form still holds. The library specification records the signs, and a spent form is written down and shown before any other shape is built.

**Reason.** The first shell does not need a runtime to read, and adding one early hides whether the simple thread has actually failed. The owner has to see the judgment before the shape changes.

**Avoidances.** Do not start with a runtime. Do not replace the thread in silence. Do not build the next shape before the owner has been shown that the simple form has run out. Do not treat a coalesced read burst as a reason to leave the one thread.

**semantic-key:** PK-6863

## 25. VT100 stand-up

The client binary is named `aetheris-client`. The functional spine stays the citation for the server rules. The first conversation is the host Ollama model, the setting `spring.ai.ollama.chat.options.model`, checked-in id `qwen2.5-coder:7b`.

The same stand-up wires the host line editor, including command-line history. The host owns the editor and the file `~/.casserole/{user}/history`. The client sends Up, Down, Home, End, Delete, Ctrl+A, and Ctrl+E, and paints the host redraw. That list is the list. The mux is unchanged.

The same stand-up remembers the login. Host, port, and user come back from `~/.casserole/ssh-client.yaml`. The password comes back when Remember password is on. The mux is unchanged. No address and no password is stored in this rule.

The window for this client is `egui` on `winit`.

A growing typed line must not jitter. The host redraw flushes once per character. The client must not paint between those flushes. The simple thread still holds. The read side coalesces one burst, then feeds once and paints once. The quiet gap is 8 ms, the time cap is 16 ms, and the size cap is 8 KiB. The read thread holds one burst and the UI paints that buffer once. The jitter is gone.

The console takes the keys on open and when a session opens.

Naming the binary does not close the window work, and it does not by itself add a new package. The coalesce and the key focus are in the client. The Java client is not changed by this stand-up.

The command layer is on this console. The host switches `grok`, `ollama`, or `pool` and keeps the stored options. The client sends Esc as `0x1B` and the typed line plus CR. The console keeps Escape and the arrow keys. Tab still leaves. A bare Esc on the frame that still holds the console writes `0x1B`. A model id and a Rust modal stay closed. The window label still says Ollama. The console keeps a stored grok id. An empty id takes the standing row in `aetheris-grok.yaml`. A missing row takes factory `grok-4.3`. Status and the turn read that id. The file is not written. An API key is not read into the seed. The client still only sends the typed line. There is no model-id argument. The closed failure is status `model grok/` with an empty id, then a turn that reports that no model is defined.

The Aetheris tab waits for the mux. The plate is a dark empty frame until the body arrives. The Scope A body shows on the plate. The notebook stays off this client. The empty canvas reads the init body and seats the two words, `air-gap` and `num-ctx`. Cards come after that empty plate.

One turn sits on the plate. The card is Turn 1. Run sends one Ollama execute. The stream reply paints on the card. New appends the next prompt. The working set keeps the earlier turn, then the new prompt. Turn 1 stays.

The four letters sit on the plate. I keeps the prompt and the reply. E keeps nothing. P keeps the prompt. O keeps the reply. Clear leaves one empty Included card.

The inspector shows the working set, newest block first, and an estimated token count. It does not send. A letter click keeps that card. Exclude marks that card and leaves the others. A prompt that already holds the keyboard does not take the pane back. The focused Included reply paints on the inspector when it is in hand. The send keeps the focused prompt.

Three Included single turns paint newest first. The focused card is its prompt. Each earlier Included card is the user row with its assistant row under it. Twelve hyphens sit between those blocks.

Member cards sit together. Run on a member sends every member, one after another. The merge card is not sent. The inspector shows the member prompts as one user line and the merge reply as the assistant line. Members of one group sit on a sideways row. Each member is its top bar plus 10 pixels. The merge stays the column width under that row. The block takes the column. The canvas sideways bar stays off. A member stays at width 20.

Ctrl+Shift+G copies the armed group to the next place. The new prompts and replies are empty. The member shape stays. A card outside a group shows `no group selected` in yellow on the status line. The group row starts hidden. `v` shows it and `^` hides it. The choice is not saved. Group, Join, and Ungroup sit on that row.

Ctrl+Shift+F5 runs the chain on the armed card. A prompt may hold `{{cell[k].output}}`. The number counts earlier cards from 1. Those cards run first, then the armed card. A bad index fails when that card is sent. The status line says `pipe`. The token stays in the prompt.

Delete removes the armed card. A card with no group removes that one row. A card in a group removes that whole group and its merge. The button stays off when that removal would leave the canvas empty. The focus lands on the card that now sits where the first removed card sat. A tail removal lands on the last card. The labels close up from Turn 1. A pipe token stays as written. A run on a removed card stops. The status line says `deleted turn on Canvas`, or `deleted group on Canvas`.

Collapse All folds every card on this canvas. Expand All opens every card. A folded card keeps a short line of the reply, or the prompt when the reply is empty. The stored text stays. Run still sends it. The choice stays for this window. The folded line stays inside the column. The bar stays on the share. A click on a folded card arms that card. The sapphire moves. The fold stays.

Every card wears a one-pixel line. The armed card is sapphire. A focused merge lights its group. The running card wears the line while that run is in flight. The line leaves when the run ends. The running card counts the seconds beside thinking. The count starts when the run starts. The shape is `m:ss`, and `h:mm:ss` once an hour has passed. The clock leaves when the run ends. The yellow thought text stays off this client.

A mark on the card copies the whole reply. The mark stays off until a reply exists. The prompt stays. A right-click opens the menu. Import reads a text file into the prompt. The reply stays. Duplicate group stays on the menu.

The reply opens in a read-only window. The mark stays off until a reply exists. Find is Ctrl+F. Esc closes the find bar. Close returns to the canvas. The size is remembered for this vault user. The reply window stays inside the desktop view. A larger size stops at the view edge. A shrink stays. Find, Copy, and Close stay in view. The title bar carries a close mark.

Body plus Close stays until Close. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog.

Burner marks the turn ephemeral. The prompt and the reply stay. A cloud run is denied. The status is `burner denied`. An ordinary card leaves the notice closed. An Ollama run still sends.

A saved Aetheris tab waits for Connect, then takes the plate and lands on the newest card. A close stores the visible plate. A close while that wait is still armed leaves the stored word alone.

The window opens maximized. The zoom starts at three Ctrl-+ steps, which is 1.3. The window paints the keys already in hand, then commits a budget. Canvas pieces go first. One terminal chunk follows. The rest stays queued.

The bar between the canvas and the inspector moves for this session. The open share is 0.72. A drag keeps the share until the window closes. The saved share comes back the next time the window opens. One arrow hides the canvas. The other hides the inspector. The arrow of the hidden pane brings that pane back. The marks are filled off-gray buttons. A triangle sits in each one. The buttons stay on the divider. The width is the bar. A triangle sits inside each button. Collapsed to the right, the inspector column is closed.

The inspector folds at the shell column. A missing count is 80. Soft wrap starts on for this window.

A Rust UI change looks up the Swing lesson before the crate moves: thread management, canvas painting beside a slower chain, and a queue of events with listeners. Reusable components carry that behavior as far as Rust allows. The simple thread still holds.

The Turso file stays shut. The vault proof stays the gate. The primary-goal account still holds.

**Truth.** The binary is `aetheris-client`. The first model is the host Ollama id `qwen2.5-coder:7b` from `spring.ai.ollama.chat.options.model`. History is the host file `~/.casserole/{user}/history`. Login host, port, and user return from `~/.casserole/ssh-client.yaml`, and the password returns only when Remember password is on. The window is `egui` on `winit`. The read thread coalesces one burst at 8 ms quiet, 16 ms cap, and 8 KiB, then feeds once and paints once. The console owns the keys on open and when a session opens. Esc is `0x1B`. The plate, the inspector, the groups, and the view follow the sizes, colors, keys, and sentences above. `air-gap` and `num-ctx` are the two words the init body seats. The Turso file stays shut.

**Reason.** The typed line jittered because the host flushed a redraw on every character and the client painted between flushes. Coalescing one burst on the existing read thread removes the jitter without a second thread. The console was also opening without the keys, so the line editor never saw them until focus was taken on open and again when a session opened. Remembered login and history stay on the host's files so this client does not grow a second mux.

**Avoidances.** Do not paint between per-character host flushes. Do not raise the quiet gap above 8 ms, the time cap above 16 ms, or the burst above 8 KiB for this coalesce. Do not leave the one read thread for this fix. Do not add editor keys beyond Up, Down, Home, End, Delete, Ctrl+A, and Ctrl+E on that history path. Do not restore a password when Remember password is off. Do not change the mux for history or for the remembered login. Do not put a password or a network address into the rule. Do not let Esc leave a frame that still holds the console. Do not send a model id or open a Rust modal. Do not read an API key into the seed. Do not write `aetheris-grok.yaml` to fill an empty grok id. Do not open the Turso file. Do not paint the notebook or the yellow thought text from this stand-up.

**semantic-key:** PK-6864

# Ctrl-C on the focused console writes the interrupt

While the console grid holds the keys, Ctrl-C sends byte 0x03. The window reports that chord as a copy event. The focused console sends the interrupt and does not copy.

**semantic-key:** PK-6865

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

# CSI M deletes lines at the cursor and the cursor stays

`CSI M` deletes the lines at the cursor. The lines below shift up. Blank lines fill the bottom of the screen. The cursor stays. Deleted lines stay out of the scrollback.

**semantic-key:** PK-6868

## The count

A missing count, or a count of zero, deletes one line. The count stops at the bottom of the screen. The lines at the cursor leave. The lines below shift up. Blank lines fill the bottom. The cursor cell stays. The scrollback length stays.

`ESC M` stays reverse index. It moves the line down and the cursor stays. It is not delete-line.

The shell never sets a scroll region, so the screen is the region.

**semantic-key:** PK-6869

## Why the grid honors it

A glyph edit paints as the damage, in one flush. When the row count drops, the paint sends delete-line (`ESC[nM`) with the cursor on the first row past the new page. Those lines leave and the cursor stays where it is. Erase-in-display stays off that path.

The grid already follows cursor motion, return, line feed, and erase-in-line. Dropping `CSI M` left the extra row on the console after a closed wrap and after a deleted newline.

A closed wrap feeds a full-width line and a marker on the row under the parked row, then the bytes that close the wrap. The marker moves up. The parked row is gone. The cursor sits on the shortened line.

A deleted newline feeds `AB`, a newline, `C`, and a marker under that page, then the bytes that join the newline. The marker moves up. The cursor sits after `ABC`.

`CSI M` and `CSI 2 M` leave the cursor where it is and leave the scrollback length where it is.

**semantic-key:** PK-6870

## What this plate does not change

This plate is not a second line editor and not a scroll region. It does not change the notebook chrome, the paint hold, the To terminal button, the yellow thought text, the minimize toggle, or the mood layer. It does not write the vault or the door. It does not open a new channel. It does not edit the other client.

The check does not open a window and does not start a session.

**Truth.** `CSI M` deletes one line when the count is missing or zero, and otherwise that many lines, stopped at the bottom of the screen. Lines below shift up, blanks fill the bottom, the cursor stays, and scrollback does not grow. `ESC M` remains reverse index. The screen is the region.

**Reason.** A closed wrap and a deleted newline were leaving the extra row on the console because the grid ignored delete-line while the paint was already sending it.

**Avoidances.** Do not put deleted lines in the scrollback. Do not move the cursor. Do not treat `ESC M` as delete-line. Do not use erase-in-display for this path. Do not invent a scroll region the shell never sets.

**semantic-key:** PK-6871

# Soft return in the VT100 chat

Shift-Enter adds a newline to the line being typed in the VT100 chat. The line stays open. Enter still sends that line.

**semantic-key:** PK-6872

## The caret

The cursor sits on the new row after Shift-Enter. The help line says that Shift-Enter adds a line.

**semantic-key:** PK-6873

## What the send keeps

A blank line in the middle of the typed line stays in the send. A line feed that follows a carriage return stays swallowed, so that pair does not become a second break.

**semantic-key:** PK-6874

## The host shell beside this key

On the chat shell, a lone line feed inserts a newline and does not submit. A carriage return still submits. A line feed after a carriage return is still swallowed. That shell rule is the side of this plate. It does not replace the console byte rule, and it does not change Enter into a newline.

**semantic-key:** PK-6875

## What the check held

The closed-window check counted 188 passes, and the soft-return check counted 1 pass. The check that the host-select model line still passes as typed is the check that holds this key. The check did not start a session and it did not open the window. A later live confirmation of Shift-Enter added no new requirement, and it did not move the source. Those two counts stayed 188 and 1.

**Truth.** In the VT100 chat, Shift-Enter inserts a newline, leaves the line open, and parks the cursor on the new row. Enter still sends. A blank line in the middle stays in the send. A line feed after a carriage return stays swallowed, on this client and on the chat shell. A lone line feed on the chat shell inserts and does not submit.

**Reason.** A chat line that submitted on Shift-Enter could not hold a blank row in the middle of the text the operator was still writing. Swallowing a line feed that follows a carriage return keeps a single submit from becoming two breaks.

**Avoidances.** Do not send on Shift-Enter in the VT100 chat. Do not change Enter into a newline. Do not drop a blank line from the middle of the send. Do not stop swallowing a line feed that follows a carriage return. Do not make a lone line feed submit on the chat shell. Do not move the mux wire for this key. Do not treat the live confirmation as a new requirement.

**semantic-key:** PK-6876

# Soft return on the console

Enter sends the typed line. Shift-Enter adds a newline and leaves the line open. The console writes one byte for that key, and the two bytes are never written together.

**semantic-key:** PK-6877

## The bytes

Enter is a carriage return, byte 0x0D. Shift-Enter is a line feed, byte 0x0A. Ctrl+Enter sends, and that byte is 0x0D. Alt+Enter sends, and that byte is 0x0D.

**semantic-key:** PK-6878

## Shift that the event does not report

The Enter event can arrive with the shift bit clear while the Shift key is still down. The console still adds the newline and writes 0x0A.

A Shift release in that same frame, with no other key between that release and Enter, still adds the newline and writes 0x0A. A letter between that release and Enter sends, and the byte is 0x0D. Enter alone writes 0x0D.

**semantic-key:** PK-6879

## What this key does not move

Paste stays as it is. The canvas prompt stays as it is. The other client keeps its own soft return. The PTY mode list stays empty. This change is the console on this client only.

The closed-window check counted 188 passes. The check that the host-select model line still passes as typed is the check that holds these bytes. The check did not start a session and it did not open the window.

**Truth.** Enter, Ctrl+Enter, and Alt+Enter write 0x0D and send. Shift-Enter writes 0x0A and does not send, including when the event's shift bit is clear while Shift is still down, and including a Shift release in the same frame with no key between the release and Enter. A letter between that release and Enter writes 0x0D.

**Reason.** The key event can report shift as clear while Shift is still down, and a Shift release in the same frame can arrive before Enter. Treating either arrival as a bare Enter sends a line the operator was still breaking.

**Avoidances.** Do not write 0x0D and 0x0A together for one key. Do not send on Shift-Enter. Do not treat Ctrl+Enter or Alt+Enter as a newline. Do not send when Shift is still down, even if the event says the shift bit is clear. Do not send on a same-frame Shift release when no key sits between that release and Enter. Do not keep the newline when a letter sits between the release and Enter. Do not change paste. Do not change the canvas prompt. Do not fill the PTY mode list. Do not rewrite the other client's soft return.

**semantic-key:** PK-6880

# To terminal, bracketed

To terminal writes `ESC[200~`, the body, and `ESC[201~`. Carriage return plus line feed, and a bare carriage return, collapse to a line feed. There is no trailing Enter.

**semantic-key:** PK-6881

## The cap

The count is UTF-16 units, the same count as a string length. Over 65536 is refused before confirm. The status says `Paste exceeds 65536 characters.` Nothing is written. A carriage return counts as one character.

**semantic-key:** PK-6882

## Which body

A burner returns `ephemeral` before the body is used, including before a huge body. The reply is sent when its trim is not empty. Otherwise the prompt is sent, including a prompt that is only spaces. An empty string returns. A newline-only prompt follows that rule.

Confirm stays. Cancel leaves the status. No session says `terminal write failed: no terminal`. A landed write says `sent to terminal`. An Excluded turn still writes. The view modal still keeps the button quiet. Line feeds stay inside the markers. Enter is not added.

**semantic-key:** PK-6883

## The console paste is a different path

A paste typed in the console stays on `bracket_paste`. Mode 2004 still gates those markers. Newlines on that path still become carriage returns. The shell records either byte as one row break.

**semantic-key:** PK-6884

## What stays off

Delete-line, the yellow thought text, the minimize toggle, and the mood layer stay off this plate. There is no store write, no door write, no new channel, and no edit of the other client. The closed-window check counted 101 passes. It held the markers, the line-feed body, the exact cap, the burner before a huge body, and a newline-only prompt. It did not start a session and it did not open the window.

**Truth.** To terminal wraps the body in `ESC[200~` and `ESC[201~`, collapses CR LF and a bare CR to LF, and writes no trailing Enter. Over 65536 UTF-16 units is refused before confirm with `Paste exceeds 65536 characters.` A burner is `ephemeral` before the body is used. The reply wins when its trim is not empty. Otherwise the prompt goes.

**Reason.** The button already wrote the focused turn, and the other client's button already wraps a bracketed paste, keeps newlines as line feeds, and refuses the cap before confirm. A trailing Enter would submit the paste.

**Avoidances.** Do not omit the bracket markers. Do not leave CR LF or a bare CR uncollapsed on this button. Do not add a trailing Enter. Do not confirm a body over 65536 UTF-16 units. Do not count the cap in bytes or in code points. Do not read a burner body. Do not send an empty string. Do not drop a prompt that is only spaces when the reply trim is empty. Do not change the console paste path, mode 2004, or its conversion of newlines to carriage returns. Do not write the store or the door.

**semantic-key:** PK-6885

# To terminal

To terminal writes the focused turn into the terminal session. The reply is sent when the turn has one. Otherwise the prompt is sent. A burner turn is refused. Confirm comes before the bytes. Cancel writes nothing.

**semantic-key:** PK-6886

## Which text

The focused turn is the source. A missing focus falls through to the last turn. An empty canvas returns quietly. A burner returns before either body is read. The status extra is `ephemeral`.

The reply is the text when its trim is not empty. Spaces in that reply stay. A reply that is only space falls through to the prompt. The prompt is sent as stored, including a prompt that is only space. Both bodies empty returns quietly, and the status stays.

**semantic-key:** PK-6887

## Confirm

The confirm shows `Write this cell to the terminal?` and a preview. Newlines in the preview become spaces. The preview keeps 120 UTF-16 units, then an ellipsis. The title is Terminal. OK writes. Cancel writes nothing and the status stays. Any word other than OK cancels. The preview rides beside the dialog. The cell text is not spliced into a script. A chooser miss says `terminal write failed` and the reason, and writes nothing.

**semantic-key:** PK-6888

## The bytes

The bytes are the full turn text, in UTF-8. Enter is not added. A missing session says `terminal write failed: no terminal`. A landed write says `sent to terminal`. The card stays. The letter stays. An Excluded turn still writes. Run on that card still says Excluded. A cloud Run stays the path it already is. The write is the same write the console already uses. The click applies while the reply view is closed, the same gate the other footer buttons already use.

The other client's status line prefixes the vault word, the air-gap word, and the engine word, then the extra. This plate uses the bare extra. That prefix waits.

**semantic-key:** PK-6889

## The row

The button sits on the open tool row, after Pull, inside the rounded bar. v still shows the row. ^ still hides it. The choice is still not saved. Search, Export, Import, and Pull stay on the row. The show and hide tips name Search, Export, Import, Pull, and To terminal. The button tip says the reply is sent when the turn has one, otherwise the prompt, that a burner is refused, and that confirm comes before the bytes.

No wire frame leaves. The read thread stays the one already in use. The door file stays. The store file stays. There is no new channel.

**semantic-key:** PK-6890

## What this plate completes, and what it leaves

This plate completes the tool bar. The yellow thought text stays held, waiting on wire kind `t`. The minimize toggle stays held with it. The mood layer stays held. Splits, the paste editor, the font spinner, and a modal stay held. The burner store check waits until the vault is online. The library page and the two VT100 plan pages stay as they are. The other client's To terminal row stays. The differences on this plate are the bare status extra and the rules above.

The closed-window check counted 100 passes. The window stayed closed. It did not start a session and it did not open the window.

**Truth.** To terminal, after Pull on the tool row, confirms with `Write this cell to the terminal?`, a 120-unit preview, and the title Terminal, then writes the reply when its trim is not empty, otherwise the stored prompt, as UTF-8 with no added Enter. A burner is `ephemeral` before either body is read. Cancel writes nothing. No session says `terminal write failed: no terminal`. A landed write says `sent to terminal` and leaves the card and the letter. An Excluded turn still writes. The status extra is bare.

**Reason.** Pull already copied the terminal into a new Excluded turn. The way back is this button, and a write without confirm, or a write that preferred the prompt over a real reply, would put the wrong text on the session.

**Avoidances.** Do not write before confirm. Do not let Cancel change the status. Do not add Enter. Do not send a reply that is only space in preference to the prompt. Do not drop spaces inside a reply that has a non-space trim. Do not read a burner body. Do not refuse an Excluded turn. Do not splice the cell text into a script. Do not prefix the status with the vault word, the air-gap word, or the engine word on this plate. Do not write when the reply view is open. Do not save the show and hide choice. Do not move the door or the store. Do not start the yellow thought, the minimize toggle, or the mood layer on this plate.

**semantic-key:** PK-6891

# Fold keeps the typed line

Folding the thinking pane, then unfolding it, keeps the line being typed on the VT100 console. The cursor row stays on the screen.

**semantic-key:** PK-6892

## Taller

While the local engine is thinking, the typed line sits on the last row of the console. Folding the thinking pane makes the console taller. The typed line stays on the last row. The lines above it stay. Blank rows fill above them when the scrollback has no line to give back.

**semantic-key:** PK-6893

## Shorter

Unfolding the thinking pane makes the console shorter. The typed line stays on the last row. The lines that no longer fit move into the scrollback. The typed line is not cut off the bottom.

**semantic-key:** PK-6894

## The older rule, still there

A cursor that is not on the last row keeps the older pad and clip. A zero size keeps the last positive size. Columns still clip. The host still hears the new size. The other client keeps its fold as it stands. This plate does not change the folded row and does not change Enter.

**semantic-key:** PK-6895

## The checks

The resize check holds a typed line on the last row. A taller screen keeps that line on the new last row. A shorter screen keeps that line and moves the row above it into the scrollback. The terminal-library check counted 27 passes. The client check counted 188 passes. The window stayed closed. The check did not start a session and it did not open the window.

**Truth.** A cursor on the last row stays on the last row when a thinking-pane fold makes the console taller or shorter. A taller screen pulls scrollback, or blank rows, in above that line. A shorter screen pushes the lines above that row into scrollback and does not cut the typed line off the bottom. A cursor off the last row keeps the older pad and clip. A zero size keeps the last positive size. Columns still clip, and the host still hears the new size.

**Reason.** Folding the thinking pane changes the console height while a line is being typed on the last row. A resize that padded or clipped that row like any other cursor cut the line off the bottom or moved it up into the middle.

**Avoidances.** Do not move a last-row cursor off the last row when the fold changes height. Do not cut the typed line off the bottom of a shorter console. Do not drop the lines above it when the console grows and scrollback can give them back. Do not invent lines from scrollback when scrollback is empty. Fill with blank rows. Do not change the pad and clip for a cursor that is not on the last row. Do not apply a zero size. Do not stop clipping columns. Do not hide the new size from the host. Do not change Enter. Do not change the folded row's own layout on this plate.

**semantic-key:** PK-6896

# VT100 client plan

`aetheris-client` opens one window, connects, and paints a VT100 chat. The host runs the local engine. The client sends keys and paints the host redraw. The door comes back from `~/.casserole/ssh-client.yaml`. Where this page is thinner, the behavior slice wins. The pre-plan stays locked. This page does not open a second pre-plan and does not rewrite pre-plan sections 1 through 7.

**semantic-key:** PK-6897

## Done

Packages 1 through 4 are in the tree. The closed-window check passes. The build passes. The build does not open the window. No live session is made. No live post is made. A pool turn is not sent. The ten proof lines on the behavior slice are the smoke run at the binary. They are not a step of this build.

The paper is recorded before any Rust file. The Rust tree is recorded after the crate exists. Stage, the working map, and the action stay. Do not add a lock-line prefix. One memory note may be written. Memory is not recorded with the tree.

Home is `C:\Projects\MCP\Casserole\rust\aetheris\`. Workspace members are `aetheris-term` and `aetheris-client`. The binary name is `aetheris-client`. Specs stay in the spec folder. A path that repeats the workspace folder name doubles. Staged names are confirmed before each record. Messages are UTF-8 with no BOM.

Closures stay Rust. The Java bans on lambdas, Lombok, and JUnit do not cross. Checks live next to the library and the door. The docs state the contract and cite the slice or the library serial. A new type that is the bridge cites the bridge mark. The first cut stays safe Rust.

One bounded look, once the crate exists, covers the session shape, grid ownership, the paste-mode flag, and any unsafe block. That look does not pick a window, does not file a rule, does not touch the other client, and does not widen the slice. A real defect is fixed before the product record. No unsafe block lands on this cut.

**semantic-key:** PK-6898

## Out of this build

The other client's product ask, the kernel action, the stage, and the resume mark stay. This is the Rust silo.

The command layer, splits, the paste editor, import, the font spinner, select model, the Aetheris tab, the canvas, and the store stay as they are. Remembering the connection secret is already on the behavior slice and is in this build.

The closed window family stays closed. The window is `egui` on `winit`.

`aetheris-term` is the in-tree name. Publishing waits.

The lockfile is the pin, taken on the build day. This plan does not copy crate versions into the page.

A host-key pin is interest only. This cut accepts the server key, the same as an accept-all verifier.

A live session, an opened window, and a push stay out. Proof is the closed-window check and the build.

The client does not read the application settings, does not call the local engine, does not post, and does not spawn the remote helper. It does not set the air-gap false. It does not edit `num-ctx`. It does not read the door file, the key notes, or the home settings file as part of the proof. It does not print a connection secret or a key.

**semantic-key:** PK-6899

## Floor

`aetheris-term` does not depend on `egui`, `winit`, `wgpu`, or `russh`. It does not name an Aetheris type.

`feed(&[u8])` borrows the grid for the call. A cell owns its character, its column width, and its color. No reference into the read buffer survives `feed`. The host serializes paint and `feed`. A mutex, if one is needed, stays outside the core.

The UI thread is the `winit` loop. It paints. It does not block on the socket and it does not enter the `russh` runtime. Do not use `eframe`. Own the loop so that split stays visible.

The read side is one ordinary thread. That thread owns one runtime because `russh` is async. The queue between the threads is bounded. The library's simple thread still holds. One pane. The unify signs are not met.

The decoder for the host bytes is `vte`. The grid is ours. `alacritty_terminal` stays the contingency and is not a dependency of this cut.

Keys are encoded in the library. The window maps events into the library key values. Application cursor mode sends CSI while off and SS3 while on. Enter is CR `0x0D`. Backspace is DEL `0x7F`.

Init-or-raw lives in the client connector. Magic `0xAE`, header 16 bytes, little-endian. The wait is 1500 ms. The keepalive is 2000 ms. Channel `0x0001` is Scope T. Channel `0x0002` is Scope A. The behavior matches the connector's init attempt and the demux that paints Scope T. Scope A frames are read and not painted. The Aetheris tab stays held. Do not invent a second frame layout. Read the frame constants and the codec on the build day and match them.

The door file is `~/.casserole/ssh-client.yaml`. The keys are host, port, username, the connection secret, and the remember flag, plus the `accounts` list. Unknown keys stay. A missing flag starts off. A connection secret is never written to a log, the status line, or the window title.

Paste: private mode 2004 is a flag on the grid. Other private modes stay effect-off. The cap is 65536. When the flag is on, the client sends `CSI 200 ~` through `CSI 201 ~`. When it is off, the client sends raw UTF-8 with newlines as CR.

The plate minimum is 640 by 480. The default is 1024 by 968. The plate is dark and monospace. Ligatures stay off the grid. The scrollbar follows the tail only when the view is already on the tail. The wheel does not send mouse tracking. A drag copies cell text locally. Bell is a flag. There is no sound.

The port field starts at 2222 when the key is missing. A stored port wins, including a stored 22. A port outside 1 through 65535 is treated as missing. An empty host, an empty user, an empty connection secret, or a bad port blocks Connect.

**semantic-key:** PK-6900

## Package order

The library comes before the door. The door comes before the chrome. The chrome comes before the socket. The socket is last so a compile of the first three packages needs no host.

1. Terminal library, `aetheris-term`. Grid, `vte`, keys, paste flag, connector trait. Checks read the grid. No window. No socket.
2. Door. A YAML map in `aetheris-client`. Temp-file checks. Unknown keys remain.
3. Chrome. `winit` plus `egui`. Strip, status, scrollbar, paint callback over the grid. Compiled, not launched.
4. Session. `russh` on the read thread. Init-or-raw in front of `feed`. The PTY is `vt100`. Resize sends a window change.

**semantic-key:** PK-6901

## Terminal library

The crate is `aetheris-term`. Dependencies are `vte` and `unicode-width`. No window crate. No `russh`.

The grid has one owner. `feed(&[u8])` advances a parser whose performer writes cells. Checks feed bytes and read cells. The grid honors print, CR, LF, and BS. It honors CUU, CUD, CUF, CUB, and CUP. It honors erase in line and erase in display. It honors SGR reset, bold, reverse, and the 16 colors. Color is stored as default, an index 0 through 255, or RGB. The first paint uses the 16 and the dark plate. Autowrap is on. Scroll enters a ring. The default cap is 10_000 lines, a setting on the grid. Save and restore cursor are honored. The alternate screen is honored. The primary grid keeps the chat scrollback. Device attributes answer as VT100. The cursor-position report returns the cursor cell the grid holds. Combining marks attach to the previous cell with a small cap. Bell sets a flag. The core plays no sound. Resize pads or clips. There is no reflow. Origin mode, insert mode, OSC, DCS, sixel, and charset switches are consumed with effect off.

Private mode 2004 is one flag the host can read. It sets on `CSI ? 2004 h` and clears on `CSI ? 2004 l`. Other private modes stay effect-off.

Keys are a library enum. Encode writes the bytes in the library key table, including Up, Down, Home, End, Delete, Ctrl+A, Ctrl+C, and Ctrl+E. Application cursor mode selects CSI or SS3 for the arrows. Checks assert the bytes. The library does not time Esc. It does not implement `help`, `message list`, or `message find`.

The connector trait is `read`, `write`, and `resize`. The core does not call it and does not start a runtime.

The module note cites the library serial and the slice.

**semantic-key:** PK-6902

## Door

The reader loads a YAML value so unknown keys round-trip. The pre-plan left the reader for crate day. The duty is a map, not a struct of five fields.

A missing file loads the stand-up defaults: host `localhost`, port `2222`, user the OS user name, remember flag off, connection secret empty. A stored port outside 1 through 65535 is treated as missing.

Save writes host, port, user, the remember flag, and the connection secret only when the flag is on. Off removes the secret key. The matching `accounts` row is updated the same way. Every other key and every other account row stay.

When the YAML is missing and `~/.ssh-client.properties` exists, those keys are imported once and the YAML is written. The legacy file stays in place.

A parse failure returns an error and does not overwrite the file.

Checks use a temp file. One check keeps an unrelated key. One check shows the secret is absent when the flag is off. No check prints the secret.

The secret field in the chrome is masked. The status line and the window title never carry it.

**semantic-key:** PK-6903

## Chrome

Dependencies are `egui`, `winit`, `egui-winit`, `egui-wgpu`, and `wgpu`. No `eframe`. No closed window family.

One window is titled Aetheris. The default is 1024 by 968. The minimum is 640 by 480, enforced by clamping a resize below that.

The plate is dark and monospace. The connect strip is host, port, user, the connection secret, Remember, Connect, Disconnect, and a status line. The user field lists the account names from the door file. Picking a name that has a stored secret fills the field and turns the checkbox on.

The grid is a paint callback in a rectangle the UI reserves. Cells become glyphs. Column width comes from the library. Ligatures stay off. The scrollbar and the wheel move the view of the ring. The view follows the tail only while it is already on the tail. End returns to the tail. The wheel does not write mouse-tracking bytes. A drag copies the selected cell text to the clipboard.

Keys, when the grid has focus, go through the library encoder and, once the session package exists, onto the connector. Until that package, the encoder is wired and the write waits on a connected session.

Connect stays disabled while a field is empty or the port is bad, and while a session is up. Disconnect stays disabled while the session is down.

Close disconnects, joins the read thread with a bound, and exits. A stuck join is surfaced on the status line. There is no confirm dialog on this cut.

This package is proved by a build. The build does not create a window.

**semantic-key:** PK-6904

## Session

`russh` runs on the read thread's runtime. The session authenticates with the connection secret. The server key is accepted. The check is the other client's accept-all, recorded as Version 1 with no known-hosts file.

The terminal type is `vt100`. A PTY is requested. A window-change follows a positive resize after the in-flight bytes. A burst keeps the latest size. A zero row or column keeps the last positive size.

On connect, the client writes the same init the connector writes on channel `0x0002`, type init. It waits 1500 ms. A valid init or data frame whose magic is `0xAE` enters the mux. Later paints are channel `0x0001` payloads only. Other channels are read and dropped. Keepalive matches the connector keepalive on a 2000 ms timer. No `0xAE` answer, or a failed parse, returns the peeked bytes unread and paints the raw PTY.

Read-thread bytes cross the bounded queue. The UI thread calls `feed` between paints. The UI thread does not enter the runtime.

Disconnect is the end of read. The grid stays. The status carries the reason. The process does not panic.

A refused connection, a timeout, a dead host, or an auth failure sets the status to Disconnected and leaves the secret field filled. The secret is still not logged.

No check in this package opens a socket. The live connect to port 2222 is the smoke of the ten slice lines.

**semantic-key:** PK-6905

## Seats of the build

The painter stays `egui` on `winit`, with `wgpu` under the paint, and `russh` behind the read thread. The four packages stay in that order. The ten lines stay the smoke at the binary. The strip stays short. The grid stays dark. The behavior slice stays the behavior. The look happens once the crate exists, and it does not pick the window.

**Truth.** The client opens one dark monospace window, restores the door, sends library-encoded keys, and paints a VT100 grid from `vte` through a bounded queue. Init-or-raw uses magic `0xAE`, a 16-byte little-endian header, a 1500 ms wait, and a 2000 ms keepalive. Channel `0x0001` paints. Channel `0x0002` is read and not painted. A miss falls back to the raw PTY. The server key is accepted. The connection secret is masked and never logged. Packages land library, door, chrome, then session. The build does not open the window and does not open a live session.

**Reason.** The socket is last so the grid, the door, and the paint can be proved with no host, and the behavior slice still wins wherever this page is thinner.

**Avoidances.** Do not depend on `egui`, `winit`, `wgpu`, or `russh` from the terminal crate. Do not name an Aetheris type there. Do not use `eframe`. Do not let the UI thread block on the socket or enter the session runtime. Do not start a second runtime. Do not meet the unify signs on this cut. Do not depend on `alacritty_terminal`. Do not reflow on resize. Do not play a bell. Do not send mouse tracking from the wheel. Do not invent a second frame layout. Do not paint Scope A. Do not log, title, or status-print the connection secret. Do not save the secret when the remember flag is off. Do not overwrite the door on a parse failure. Do not delete the legacy properties file after import. Do not drop unknown keys or other account rows. Do not treat a stored port, including 22, as missing when it is inside 1 through 65535. Do not connect on an empty host, an empty user, an empty secret, or a bad port. Do not confirm close on this cut. Do not open a socket from a check. Do not read the application settings, call the local engine, post, or spawn the remote helper. Do not set the air-gap false. Do not edit `num-ctx`. Do not print a key. Do not copy crate versions into this page. Do not publish the library name. Do not land an unsafe block. Do not widen the slice. Do not rewrite pre-plan sections 1 through 7. Do not stage the editor workspace, the diagram cache, the requirements notes, the key notes, the application settings, video-bay deletions, or memory.

**semantic-key:** PK-6906

# VT100 sketch

One program, `aetheris-client`, opens one window with `egui` on `winit`, one SSH session, and the host local-engine chat. This page is the sketch. Where it is thinner than the VT100 behavior page, that page wins. The grid, the parser, the keys, and the thread stay on the terminal library page.

**semantic-key:** PK-6907

## What the person does

The person connects, types, and the host reply paints. The host owns the line editor and the command-line history at `~/.casserole/{user}/history`. The client sends the keys and paints the redraw. The door comes back from `~/.casserole/ssh-client.yaml`.

This client does not read the application file, does not call the local engine, does not post to the hosted model interface, and does not spawn the hosted program. The host keeps the air gap. The command layer stays the later slide. The library owns the grid.

**semantic-key:** PK-6908

## The window pair

`egui` is locked for the connect strip, the scrollbar, and the status line. `winit` is locked for the operating-system window. The minimum is 640 by 480. The default is 1024 by 968. The window takes keyboard, mouse, resize, scale, and close. `egui-winit` is the glue that lock implies. `egui-wgpu` and `wgpu` paint. The grid is a paint callback in a rectangle `egui` reserves. The same surface can carry the canvas later.

`russh` does the handshake, the login, the channel, and the PTY. It has no pin on this sketch. It stays behind the read thread. The client accepts the server key the same way an accept-all verifier does. `vte` is the parser. The grid stays this client's. `unicode-width` is the column width. Ligatures stay off the grid.

The door file uses the same keys as the existing connection properties. A map reader writes back every key it did not change. The reader crate is chosen when the crate file is written. This page pins nothing else. A version pin waits for that day and lands in the lock file.

Init-or-raw sits in the client connector, not in the library. The wait is 1500 ms. Byte `0xAE` means Scope T. No answer means the raw PTY. The core of the terminal library does not depend on `egui`, `winit`, or `russh`.

**semantic-key:** PK-6909

## The four steps, when a sentence writes the crate

The terminal library feeds bytes and the checks read the grid. The encoder checks the wired keys. No window. No socket.

The door store loads and saves a temporary file. Unknown keys remain. A connection secret never appears in a log line.

The chrome draws the strip and a paint callback over a grid. That step is the first window. This paper does not open it.

`russh` runs on the read thread. Init-or-raw sits in front of `feed`. A live connect to port 2222 is the confirmation of the ten proof lines on the behavior page. This paper does not connect.

**semantic-key:** PK-6910

## The shape

The home is `rust/aetheris/` under the product tree. Specs stay in `docs/spec/ver.1.0/rust/aetheris/`. `aetheris-term` is the grid, the parser seat, the key encoder, the paste-mode flag, and the connector trait. It has no product types, no window crate, and no `russh`. `aetheris-client` is the locked binary name: the door, the file, the session, init-or-raw, and the paint. `aetheris-term` is the in-tree name on this sketch. The library publish name still waits.

The interface thread is the `winit` loop. It paints. It does not block on the socket. The read side is one ordinary thread. `russh` is asynchronous, so that thread owns one runtime and blocks on the read queue. The interface thread does not enter that runtime. The library core still does not start a runtime and does not call the connector. The queue is bounded. One pane. One runtime, on the read side, owned by the host. The signs that would unify those runtimes are not met.

The first cut stays in safe Rust. An unsafe pixel blit comes back before it lands. The check of lifetimes covers the session, the grid, the paste flag, and any unsafe. That check does not pick the window.

This page is paper. It does not open a window and it does not open SSH. The plan that follows this sketch is the canonical page. The build is named from that stamp. The crate follows that stamp.

**Truth.** The sketch is one `aetheris-client` window, `egui` on `winit`, minimum 640 by 480, default 1024 by 968, one SSH session, keys sent and redraw painted, door file `~/.casserole/ssh-client.yaml`, init-or-raw waiting 1500 ms for `0xAE` or else a raw PTY, and the library free of `egui`, `winit`, and `russh`. The behavior page wins where this sketch is thinner.

**Reason.** The window pair had to be locked before the crate, and the grid, the keys, and the thread already belonged to the library page rather than to this sketch.

**Avoidances.** Do not treat this sketch as the crate or as the plan. Do not copy the behavior page onto this page. Do not pin a crate this page leaves unpinned. Do not put init-or-raw in the library. Do not let the library depend on `egui`, `winit`, or `russh`. Do not let the interface thread block on the socket or enter the read runtime. Do not start a runtime in the library core. Do not unbound the queue. Do not add a second pane on this sketch. Do not call the local engine, post to the hosted interface, read the application file, or spawn the hosted program from this client. Do not log a connection secret. Do not drop unknown door keys on a write. Do not land an unsafe pixel blit in the first cut. Do not let ligatures onto the grid. Do not open the window or the socket from this paper.

**semantic-key:** PK-6911

# Session reflow

A window resize or a font scale changes the column count. The primary screen joins each soft wrap and wraps the session again to the new width. Every glyph stays. A hard newline stays a break.

**semantic-key:** PK-6912

## What changes the columns

Ctrl plus and Ctrl minus, a window resize, and the font spinner change the column count. The paint already asks the grid for the new size. A column change joins the soft wrap. A resize that changes only the row count still keeps a typed line on the last row.

**semantic-key:** PK-6913

## How the primary screen wraps

The primary screen joins a soft wrap with the next row. The joined text wraps again to the new width. A hard newline stays a break. A wide glyph stays whole and keeps its spacer. Color and bold stay on the cells. Red stays on the cells that were red.

Lines that no longer fit the window move into the scrollback. The paint draws that scrollback, so those glyphs stay on the screen. The scrollback is joined with the visible rows. A cursor on the last row stays on the last row. A taller screen fills above that row. A shorter screen keeps that row.

**semantic-key:** PK-6914

## What does not reflow

The alternate screen still pads or clips. A zero size keeps the last positive size. The host still hears the new size. The other client keeps the input line as it stands. The history lives in the grid. This plate does not change the folded row.

The terminal-library check counted 28 passes. The client check counted 188 passes. The window stayed closed. The check did not start a session and it did not open the window.

**Truth.** A column change reflows the primary screen: soft wraps join, the text wraps to the new width, a hard newline stays a break, a wide glyph stays whole with its spacer, and color, bold, and red stay. Overflow enters scrollback and is still painted. A last-row cursor stays on the last row. The alternate screen still pads or clips. A zero size keeps the last positive size. The host still hears the new size.

**Reason.** The grid padded or clipped, so a narrower column cut the glyphs off the line instead of wrapping them.

**Avoidances.** Do not pad or clip the primary screen on a column change. Do not drop a glyph. Do not turn a hard newline into a soft wrap. Do not split a wide glyph or drop its spacer. Do not drop color, bold, or red. Do not hide the lines that moved into scrollback. Do not move a last-row cursor off the last row. Do not reflow the alternate screen. Do not apply a zero size. Do not hide the new size from the host. Do not change the folded row. Do not move the history out of the grid.

**semantic-key:** PK-6915

# VT100 stand-up

The binary is `aetheris-client`. One window, one session, and a VT100 chat. The first conversation is the host's Ollama model. The client paints what the host sends. This page books the stand-up. It writes no crate. Where a sentence here drifts from a cited page, the repair is made here. The grid, the parser, the keys, and the thread stay on the library page. The pre-plan is the sketch. The plan waits for this smoke.

**semantic-key:** PK-6916

## The binary

The executable is `aetheris-client`. The window title is Aetheris. The other client's window title stays that client's title. There is no crate file on this page. The library crate name still waits.

The window pair is `egui` on `winit`. The chrome is `egui`. The window is `winit`. The grid is a paint callback. The core does not depend on either. The other window family was open through the first cut and is closed for this client. A terminal-only crate stays off, because the window stays a graphic surface.

The specialist stays off this page. When the crate is written, the specialist checks the session shape, the lifetimes around the grid, the paste-mode flag, and any unsafe block. The specialist does not pick the window crate.

**semantic-key:** PK-6917

## The conversation

The proof is one chat.

1. The person fills host, port, user, and the connection secret, and clicks Connect.
2. The pane shows the host greeting and a green `chat>` prompt. The host paints that prompt. This client does not draw it.
3. The person types a line. Enter sends it. The reply paints. Backspace, Left, and Right edit the line. The host owns the line.
4. Disconnect ends the session. The grid keeps what it already painted.

The same cut passes Up, Down, Home, End, Delete, Ctrl+A, and Ctrl+E. The host owns those edits and the command-line history. The client sends the bytes and paints the host redraw.

The command layer stays the host's later slide on this first cut. Esc still passes through. The proof of this stand-up is the chat line. When Esc is pressed, the client paints what the host sends.

Ctrl+C passes through. The host welcome already says Ctrl+C cancels. The client does not rebuild that cancel.

**semantic-key:** PK-6918

## The model

The initial model is Ollama, as the host is configured.

The host property is `spring.ai.ollama.chat.options.model`. The checked-in id is `qwen2.5-coder:7b`. The host shell factory reads it. The host calls Ollama. Boot still starts when Ollama is down. A failed turn is red text from the host.

The client does not read `application.yaml`. The client does not open a socket to Ollama. The client does not post to the remote chat service. The client does not spawn `grok`. The chrome says Ollama. It does not paint a model id, because a local edit of that file would make a painted id a lie.

Select-model stays a later slice on this first cut. Air-gap stays the host's rule. This client sends no `execution_target`.

**semantic-key:** PK-6919

## The door

Host is typed. Empty blocks Connect. Port starts at `2222`. That is `casserole.ssh.port` in the checked-in host file. The full-shell row still says 22. That 22 is the generic session default, and it misses this host. This stand-up matches the host. The person can change the field. A value outside 1 through 65535 blocks Connect. User is typed. Empty blocks Connect. The connection secret is masked. Empty blocks Connect. It is not written to a log. The remember box decides whether the file keeps it.

Connect is enabled when the four fields are usable and the session is down. Disconnect is enabled while a session is up. Status is Connecting, Connected with host and port, or Disconnected with the reason.

The door file is `~/.casserole/ssh-client.yaml`, the same store as the other client's connection properties. The keys are host, port, username, the connection secret, and the remember flag. The `accounts` list keeps one row per user name, with the user name and the secret.

Host, port, and user always write back. A stored value fills the field. A missing host starts at `localhost`. A missing port starts at `2222`. A stored port still wins, including a stored `22`. A port outside 1 through 65535 is treated as missing. A missing user starts at the OS user name. Empty host, empty user, empty secret, or a bad port still block Connect.

The remember control is a checkbox. A missing flag starts off, the same default as that class. When the box is on, the secret is written in the file and the masked field fills on the next open. When the box is off, the secret key is removed and the field starts empty. The secret is plain text in that file, the same way the other client already stores it. It is not written to a log, the status line, or the window title. The first cut left the secret unstored and left the remember box for a later slice. This page is that slice.

The user field offers the account names already in the file. Picking a name that has a stored secret fills the masked field and turns the checkbox on. Other account rows stay. Every other key stays. Colors, frame, and font stay with the other client. A parse failure leaves the file untouched. Status says the door was not saved. Connect still uses the typed fields. A missing file is not fatal. When the YAML is missing and `~/.ssh-client.properties` exists, those keys import once, the same first-read duty as the other client's properties. The legacy file stays in place.

The client writes the door when a field is left, when Connect is clicked, and when the window closes. A failed write does not block the session. This client does not read `application.yaml`. The mux is unchanged. No new scope, channel, or frame type. This page writes no crate.

One session. A second Connect while connected has no door. Reconnect, Clear Terminal, Send file, New Term, and Close Pane stay the later shell.

The host key check matches the other client: accept the server key. That is the accept-all verifier, recorded as Version 1 with no known-hosts file. A pin is an interest below. This stand-up connects the way the other client connects.

**semantic-key:** PK-6920

## The stream

`russh` does the handshake, the login, the channel, and the PTY request. No version pin on this page. The terminal type is `vt100`.

Init-or-raw is the client connector, the same duty as the other client's connector. The wait is 1500 ms. A `0xAE` answer means the pane paints Scope T only. No answer means the raw PTY.

The parser is `vte`. The grid stays in the library. One ordinary thread blocks on the read queue and calls `feed`. The UI thread paints. The queue is bounded.

The grid takes the new positive size, then the connector sends the window change. Bytes already in flight belong to the old size. A burst of resizes sends the latest size. A zero column or a zero row keeps the last positive size.

Disconnect is the end of read. The grid stays. The status carries the reason. The process does not panic. Close disconnects, joins the read thread, then exits. The full shell's confirm dialog stays later.

**semantic-key:** PK-6921

## Paint coalesce

As the unsent line grows, the pane looks like it is repainting on a cycle. The other client stays steady on the same host.

The host rebuilds the input line on every key. It writes CR, cursor-up when the line already wrapped, erase-in-display (`ESC [ J`), the prompt, then one flush per character, then the cursor. Each write flushes. A longer line is more flushes. The erase runs before the characters come back.

This client feeds each channel message and paints that grid. A 16 ms wake can land after the erase and before the last character. The person sees the line clear and grow again. The other client's reader applies the burst in the terminal model, and the repaint runs once per frame, so the erase and the rebuilt line are one picture.

The simple thread still holds. The host read loop keeps the bytes and releases one buffer when the channel has been quiet for 8 ms, or when 16 ms have passed since the first byte of the burst, or when the buffer reaches 8 KiB. The UI feeds that buffer once and paints once. The grid still applies every byte in order. A chat token that arrives after the quiet gap paints on its own. Disconnect and a broken mux still report at once. This is a coalesce of PTY bytes. The host still owns the line. The client does not keep a second editor.

A later sentence can also make the host flush that redraw once. That is server hygiene. This page does not name that other-client change. Dirty rectangles stay an interest. The first paint stays the full view.

The coalesce is in the tree. The read thread holds the burst and sends one buffer. The closed-window check counted 36 passes. The build passed. The window stayed closed. A later sit found the jitter gone. The coalesce holds.

**semantic-key:** PK-6922

## The plate

The child constitution's UI section is the feel. Graphic, and still an OS-oriented shell. The grid sits in a frame. The frame has a scrollbar in that same family. The connect strip is a short row of widgets above the frame. The shell follows the conversational text UI.

The minimum size is 640 by 480. The default is 1024 by 968, from the other client's window. The plate is dark and monospace, close to that client's plate.

Glyphs are the library's cells. Column width comes from `unicode-width`. Combining marks stay on the library rule. Ligatures stay off the grid, because a ligature breaks the columns.

The scrollbar and the wheel move the view of the ring. The view follows the tail while it is already on the tail. A view that has moved up stays there. End returns to the tail. The wheel does not send mouse tracking to the PTY.

A drag across cells copies that text to the clipboard. The selection is local.

Font size is a readable size at scale 1. The other client's spinner floor of 8 waits with the spinner. Per-monitor scale comes from the window.

Bell is a flag. There is no sound. The alternate screen follows the library rule. Chat stays on the primary grid.

**semantic-key:** PK-6923

## Keys and paste

The encoder stays the library's. This stand-up wires the chat set.

| Key | Bytes |
|---|---|
| Enter | CR `0x0D`. The host swallows a LF after CR. |
| Backspace | DEL `0x7F`. |
| Left / Right | CSI D / CSI C. |
| Ctrl+Left / Ctrl+Right | CSI `1 ; 5` D / C. |
| Esc | `0x1B`, sent at once. The host times a bare Esc. |
| Ctrl+C | `0x03`. |
| Text | UTF-8 of the committed characters. |

The same stand-up also wires the host line editor. The encoder already knows these bytes. Application cursor mode stays the library rule: CSI while that mode is off, SS3 while it is on. The host shell accepts both forms for the arrows.

| Key | Bytes |
|---|---|
| Up | CSI A. The host also accepts SS3 A. |
| Down | CSI B. The host also accepts SS3 B. |
| Home | CSI H. The host also accepts CSI `1 ~` and CSI `7 ~`. |
| End | CSI F. The host also accepts CSI `4 ~` and CSI `8 ~`. |
| Delete | CSI `3 ~`. The host deletes the character at the cursor. |
| Ctrl+A | `0x01`. The host moves the cursor to the start. It skips this while a paste is open. |
| Ctrl+E | `0x05`. The host moves the cursor to the end. It skips this while a paste is open. |

The host owns the command-line history. The file is `~/.casserole/{user}/history` on the host. An empty user is `anon`. The cap is `casserole.chat.history-size`. The checked-in value is 500. A value under 10 becomes 500 on the host. A missing or unreadable file is not fatal. Enter stores the trimmed line when the line is not empty and the shell is not pasting. A line that repeats the newest entry is not stored. The first Up keeps the unsent draft and jumps to the newest entry. Further Up walks older. Down walks newer, then restores the draft. Down with no recalled line returns. A recalled line lands with the cursor at the end. Typing a character clears the history cursor. The client does not read that file and does not keep a second list. This history is command-line history. It is not Roux chat memory. The host redraws with CR, cursor-up, and erase-in-display. The library grid already honors that redraw. The client paints it.

Keys ride the path the stream already names. A `0xAE` answer means Scope T. No answer means the raw PTY. This page adds no scope, no channel, and no frame type.

The paste editor stays a later slice. A conversation still needs a paste.

The host writes `CSI ? 2004 h` at shell start. The library consumes private modes with effect off until this flag. Mode 2004 sets the flag. Mode 2004 clear drops it. Other private modes stay effect-off.

When the flag is on, a paste is `CSI 200 ~`, then the text, then `CSI 201 ~`. When the flag is off, the paste is raw UTF-8 and each newline is CR. The cap is 65536 characters, the same cap as the other client's paste. Over the cap, the paste is refused and the status says so. Nothing is written to the channel. The paste is valid UTF-8. A pasted Esc is inside the bracket when the flag is on, so it does not toggle command mode by itself.

**semantic-key:** PK-6924

## Edges

A refused connection, a timeout, or a dead host sets status Disconnected, with the reason. The grid stays empty of a fake session. An auth failure uses the same status. The secret field stays filled for a retry. It is still not logged. A host drop mid-reply ends the read. Painted cells stay. Status carries the reason.

Ollama down: the host writes the red error. This client adds no second probe. Ollama slow: an open channel has no client timeout. The host owns the turn. A model edited on the host: the host calls that model. The chrome still says Ollama and still paints no id.

An empty line: Enter sends CR. The host decides the empty line. Wide characters and split UTF-8 stay in the library. `vte` reassembles a code point split across reads. Resize during a reply follows the stream rule. Visible cells follow the new size. In-flight bytes keep the old size.

Paste over the cap is refused. Status says so. Nothing is written to the channel. Paste before mode 2004 is raw UTF-8, newlines as CR, with the same cap.

Close while the read thread is blocked: disconnect unblocks read. The join has a bound. A stuck join is a defect to bring back, with a status, and is not a hang of the window with no status.

Bell, title OSC, and sixel follow the library account. Effect off, except the bell flag.

Up on an empty history: the key is sent. The host returns and paints nothing new. Down with no recalled line: the key is sent. The host returns and paints nothing new.

A missing door file fills the stand-up defaults. Connect still works. The next save creates the YAML. A door file that will not parse stays as it was. Status says the door was not saved. Connect uses the typed fields. Remember off removes the secret key. The next open leaves the secret field empty.

**semantic-key:** PK-6925

## Interests

These are available on a window and are not this stand-up.

A pinned host key can wait. The session crate can require a known key. The other client accepts any key. Matching that is what makes the first connect succeed against today's host. A pin is the next security sentence.

A growing composer can wait. The window crates can grow an input frame and scroll inside it. That is the conversational text frame. This stand-up keeps the line on the host, inside the grid. A local composer would be a second line editor.

Ligatures and color emoji as text shaping can wait. They fit a later canvas. They break a column grid.

A screen-reader path on the same chrome can wait. The chat proof does not depend on it.

IME composition in the grid can wait. The window crate can feed composition events. The first proof is a committed Unicode line.

A GPU canvas can wait. `wgpu` under the same window can carry the later canvas. The shell around it stays this frame.

Dirty rectangles can wait. The library already waits on a measurement. The first paint is the full view.

The confirm-on-exit dialog can wait. The full shell has it. This stand-up disconnects on close.

**semantic-key:** PK-6926

## Still off this first cut

Splits, the paste editor, import, the font spinner, select model, the Aetheris tab, the canvas, and the store stay held at the open of this page. The store stays named. This stand-up does not open it. The remember box is taken off that list. The door section stores the login.

**semantic-key:** PK-6927

## Plates recorded after the stand-up

The console takes the keys on open and when a session opens.

The host family word leaves the held list. The host already switches grok, ollama, or pool and keeps the stored options. The client passes Esc and the line. A model id and a modal stay closed. That plate's closed-window check counted 39 passes.

The console model select comes first. Esc was leaving the console, so the family word never followed. The console keeps Escape and the arrow keys. Tab still leaves. A bare Esc on the frame that still holds the console writes `0x1B`. A model id and a modal stay closed. The chrome still says Ollama. That plate's closed-window check counted 40 passes.

A grok choice with an empty id showed status `model grok/` and the next turn reported no model defined. The console keeps a stored grok id. An empty id takes the standing row in `aetheris-grok.yaml`. A missing row takes factory `grok-4.3`. Status and the turn read that id. The file is not written. A key is not read into the seed. This client still only sends the typed line. There is no model-id argument and no modal. The chrome still says Ollama. The other client's compile passed. The window stayed closed.

The Aetheris tab leaves the held list. The tab waits for the mux. The plate is a dark empty frame. Scope A stays unpainted. The canvas, the store, splits, the paste editor, import, the font spinner, and a modal stay held. That plate's closed-window check counted 42 passes.

The Scope A body shows on the Aetheris plate. The notebook stays held. The store, splits, the paste editor, import, the font spinner, and a modal stay held. That plate's closed-window check counted 44 passes.

The empty canvas sits on the Aetheris plate. Cards stay held. Cards, Run, the store, splits, the paste editor, import, the font spinner, and a modal stay held. That plate's closed-window check counted 45 passes.

One turn sits on the Aetheris plate. The card is Turn 1. A second turn, New, Clear, the store, splits, the paste editor, import, the font spinner, and a modal stay held. That plate's closed-window check counted 48 passes.

New appends Turn 2. Clear, the store, splits, the paste editor, import, the font spinner, and a modal stay held. That plate's closed-window check counted 50 passes.

The four letters sit on the Aetheris plate. Clear leaves one empty Included card. The inspector, groups, burner, the think strip, the store, splits, the paste editor, import, the font spinner, and a modal stay held. That plate's closed-window check counted 52 passes.

The inspector sits on the Aetheris plate. It shows the working set. It does not send. Groups, burner, the think strip, the store, the outlined card, splits, the paste editor, import, the font spinner, and a modal stay held. That plate's closed-window check counted 53 passes.

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

**semantic-key:** PK-6928

## The ten lines

The crate, when a later sentence writes it, is proved by this list. No live session is required to file this page.

1. `aetheris-client` opens one window.
2. Connect to the host on port 2222 with a real user reaches the green `chat>` prompt.
3. A typed line and Enter paint a reply from the host's Ollama model.
4. Backspace and the arrows edit the unsent line.
5. A paste under the cap lands in the host's line as one paste.
6. Disconnect leaves the painted reply on the grid and the process still exits cleanly on close.
7. Up recalls the newest stored command line. Further Up walks older. Down walks newer, then restores the unsent draft.
8. Home, End, Delete, Ctrl+A, and Ctrl+E edit the unsent line. The host paints the redraw.
9. Up on an empty history leaves the line as the host left it.
10. Close the client and open it again. Host, port, and user are filled. With the remember box on, the secret is filled and Connect needs no retype.

**Truth.** `aetheris-client` opens one dark monospace window, restores the door, and paints a VT100 chat the host owns. Enter is CR `0x0D`. Backspace is DEL `0x7F`. Esc is `0x1B` at once. Paste follows mode 2004 up to 65536 characters. Init waits 1500 ms. Magic `0xAE` paints Scope T. A miss paints the raw PTY. The read loop coalesces for 8 ms of quiet, 16 ms from the first byte, or 8 KiB, then feeds once and paints once. The server key is accepted. The connection secret is masked, stored only when the remember box is on, and never logged.

**Reason.** The first conversation has to be a host line the client does not rebuild, and the door has to come back the way the other client already stores it.

**Avoidances.** Do not draw the `chat>` prompt in the client. Do not rebuild the host line editor or the host cancel. Do not read `application.yaml`. Do not open a socket to Ollama. Do not post to the remote chat service. Do not spawn `grok`. Do not paint a model id on this cut. Do not send `execution_target` on this cut. Do not start the port at 22. Do not treat a stored 22 as missing. Do not log the connection secret or put it on the status line or the window title. Do not leave the secret unstored when the remember box is on. Do not keep the secret key when the box is off. Do not overwrite a door file that will not parse. Do not delete the legacy properties file. Do not block the session on a failed door write. Do not offer a second Connect while connected. Do not confirm close on this cut. Do not paint inside one host redraw. Do not add a second editor. Do not drop a chat token that arrives after the quiet gap. Do not delay a disconnect or a broken mux for the coalesce. Do not send mouse tracking from the wheel. Do not turn ligatures on. Do not play a bell. Do not read the history file. Do not store an empty line, a line during paste, or a repeat of the newest entry. Do not write a paste over 65536 characters. Do not let a bracketed Esc toggle command mode by itself. Do not add a client timeout on a slow turn. Do not add a second probe when Ollama is down. Do not pin the host key on this cut. Do not hang a close with no status.

**semantic-key:** PK-6929

# Terminal library

The working serial is an in-house terminal library. A sync core the host drives holds the grid, the parser seat, the keys, the settings, and the connector trait. The host paints. This page files the serial. It does not name a build. Until a sentence names the build there is no crate file. The crate name waits. Publishing waits.

**semantic-key:** PK-6930

## The seam

Terminal-library prose is written here. The client serial keeps the client's use of this library: init-or-raw, the Scope A listener, the canvas account, and the order of slices.

The session handshake, login, channel, and PTY request belong to `russh`. The byte parser is `vte`. The grid stays in this library. `alacritty_terminal` stays the contingency. The widget, the grid paint, the keys, and the settings belong to this library, in the shape of the Java terminal seat. The connector trait belongs to this library: read, write, and resize. Init-or-raw, Scope T, and the Scope A listener belong to the client, in the connector it supplies. The canvas, the store, and the host command verbs belong to the client serial.

The other client's shell specs stay that shell. This page does not fork them. This library stays free of Aetheris types. One constitution covers the directory. This page does not carry a second one. It sits beside the client serial. The language rules and the UI rules live on that constitution. This page points at them and does not restate them.

**semantic-key:** PK-6931

## Widget

The widget is the terminal seat. The Java dark widget is the example of the extension. This library owns the paint.

The first behavior the client needs is the chat focus in the client serial. This library makes that possible and does not know the word chat.

Paint the byte stream the connector supplies. Send keystrokes back through the connector. Enter, Backspace, Left, and Right are in the first focus. Settings carry the plate. The client supplies the dark terminal colors. The host on the far end owns the line editor and the shell. This library does not rebuild them.

The command layer slides in later, on this same widget. When it does, Esc passes through and the widget paints what the host sends, including `cmd>` in medium blue. This library does not implement `help`, `message list`, or `message find`. Paint sits with the host. The core exposes the grid. A window widget is an adapter a later sentence can add.

**semantic-key:** PK-6932

## Connector

The Java seam is the connector type. The client connector sits behind it, with the Java session stack on the far side of that client.

This library's connector trait is read, write, and resize. One implementation uses `russh` and requests a VT100 PTY. Another project can supply its own connector.

The client supplies the connector that peels init-or-raw. The widget sees terminal bytes. Scope A is delivered to the listener the client owns. That listener is the canvas seat. It is not a type in this library.

**semantic-key:** PK-6933

## Still open

The version pin on `vte` stays open. The approach is the parser section. `alacritty_terminal` stays the contingency. The window family the host paints with stays open. The core stays free of it. The crate name stays open. Publishing stays open. The sentence that names the build stays open.

**semantic-key:** PK-6934

## Account

The floor asked for a Rust account of this library, so a custom VT100 shell can become a real program. The specialist stays off this pass. A specialist is called when a sentence names the build, to check the code against this account. The Java seats this account mirrors are the ones the client already uses.

The emulator seat is `Perform` on `vte`. The terminal buffer is the grid this core owns. The connector type is the trait: read, write, resize. The reader thread inside the widget is the host loop. Widget paint is the host paint. The client connector is the connector the client supplies, outside this library.

The session crate is async. A core that starts a runtime cannot drop into a project that already picked one. The core stays synchronous and runtime-free. The core owns the grid. The connector stays a byte pipe. The host drives the pipe. The first keys are the ones the host shell already understands, so a typed line round-trips. One owner holds the grid. `feed` borrows it for the call. A cell owns its character. A loan of the read buffer ends when `feed` returns. The core stays in safe Rust.

**semantic-key:** PK-6935

## Ownership

The terminal state owns the grid, the cursor, the pen, the scrollback ring, and the parser. `feed` takes a byte slice, borrows the state for that call, and returns. The host paints from a shared borrow when `feed` is not running.

A cell stores an owned character, a column width, and a color. It does not store a pointer into the read buffer. The buffer dies at the end of the call.

The host calls `feed` on one task. Paint runs when that task is between calls. Bytes from another task move to the owner, and the owner calls `feed`. A lock, when a host cannot serialize those calls, stays outside the core. The core does not take it.

**semantic-key:** PK-6936

## Parser

The approach is `vte`. The parser advances and calls `Perform`. `Perform` is the emulator seat. The grid stays in this library. `vte` reassembles UTF-8 that arrives split across reads. It consumes an unknown sequence. The performer ignores a sequence it does not honor, and the bytes are already eaten, so the next print lands in the right cell.

`vte` is a 7-bit parser. C0 controls and `ESC` sequences are the wire it understands. The client and a VT100 PTY send those. An 8-bit C1 control is outside that parser. This core documents the stream as 7-bit controls plus UTF-8 text.

`alacritty_terminal` stays the contingency. It owns a grid, scrollback, and reflow. A later sentence can take that grid if writing ours stops paying for itself. Until that sentence, the grid is ours.

A hand-rolled parser stays off this page. The state machine is the part that desyncs a shell. `vte` is that machine. This library supplies the meaning.

No version pin. No crate file.

**semantic-key:** PK-6937

## Grid

The first grid is enough VT100 to paint a chat transcript and to accept the redraw the host shell already sends.

Honored:

- Print a character and advance the cursor.
- CR, LF, and BS.
- Cursor motion: CUU, CUD, CUF, CUB, and CUP. The host shell redraws the input line with these.
- Erase in line, and erase in display.
- Delete line (`CSI M`). Lines at the cursor shift up. Blank lines fill the bottom of the screen. The cursor stays. Deleted lines stay out of the scrollback. The screen is the region, because the shell never sets one. `ESC M` stays reverse index.
- SGR reset, bold, reverse, and the 16 colors. Default foreground and background come from settings. The client supplies the dark plate.
- Autowrap. It is the VT100 default. The first grid wraps.
- Scroll when the cursor passes the last row. The scrolled line enters a ring. The ring has a cap in settings. The cap is the memory bound.
- Save and restore cursor.
- Alternate screen. The core keeps a primary grid and an alternate grid. Chat scrollback stays on the primary. A full-screen program on the raw PTY switches grids and leaves the transcript in place.
- Device attributes, and cursor-position report. A raw PTY that waits on those gets an answer. The answer is a VT100 identity, and the cursor cell the grid actually holds.

Stored from the start, so a later paint can use them without a grid rewrite:

- Color is a default, an index from 0 through 255, or an RGB triple. The first paint uses the 16 and the plate.
- Column width comes from the Unicode width of the character. The measure is `unicode-width`. A combining mark attaches to the previous cell. The attachment has a small cap, so a hostile stream cannot grow one cell without limit.

Consumed, with the parser staying in sync, and the first grid leaving the effect off:

- Origin mode, and insert mode.
- Other private modes.
- OSC, including a title. The performer records nothing.
- DCS, including sixel. The hook, put, and unhook return.
- Charset switches.

A bell sets a flag the host can show. The core does not play a sound.

Resize sets columns and rows, pads or clips each row, and keeps the scrollback. Reflow of wrapped words waits for a later sentence. Bytes already in flight belong to the old size. The next prompt from the host redraws the line.

**semantic-key:** PK-6938

## Keys

The library defines its own key values. The window crate maps its events into those values. The library encodes bytes. The host writes the bytes on the connector.

The host shell's character table and its completed-escape table are the host's table. The encoder matches it.

| Key | Bytes | Host behavior |
|---|---|---|
| Enter | CR (`0x0D`) | Enter. A LF after a CR is swallowed by the host, so the widget sends CR only |
| Backspace | DEL (`0x7F`) | Deletes the character before the cursor. The host also accepts BS (`0x08`) |
| Left | `ESC [ D` | Moves one character left |
| Right | `ESC [ C` | Moves one character right |
| Ctrl+Left | `ESC [ 1 ; 5 D` | Moves one word left |
| Ctrl+Right | `ESC [ 1 ; 5 C` | Moves one word right |
| Esc | `ESC` (`0x1B`) | The host times a bare Esc. The library sends the byte and does not time it |

Text input is the UTF-8 of the committed characters.

The same encoder already knows the rest of the host table, so a later slice does not grow a second path. The first slice wires the keys the client serial already named. The rest wait for the command-layer slide, or for a sentence.

| Key | Bytes |
|---|---|
| Up | `ESC [ A` |
| Down | `ESC [ B` |
| Home | `ESC [ H` |
| End | `ESC [ F` |
| Delete | `ESC [ 3 ~` |
| Ctrl+A | `0x01` |
| Ctrl+C | `0x03` |
| Ctrl+E | `0x05` |

Application cursor keys are a mode the host can set. The host shell accepts both `ESC [ A` and `ESC O A`. The encoder sends CSI while that mode is off, and SS3 while it is on.

The library does not rebuild the host line editor. It does not implement `help`, `message list`, or `message find`.

The initial cut of the client wires Up, Down, Home, End, Delete, Ctrl+A, and Ctrl+E from the table above. The host owns the editor and the history file. The client sends the bytes and paints the host redraw. The command layer still waits. This account writes no crate.

**semantic-key:** PK-6939

## Host loop

The trait stays read, write, and resize. The core does not call the connector. The host loops.

`read` yields bytes. The host passes them to `feed`. `write` takes the bytes encode returned. `resize` takes columns and rows. The host resizes the grid first, then tells the connector. The client connector maps that to the session window-change. `russh` stays inside that connector. The trait does not name `russh`.

The host loop is the reader thread the Java widget hides inside itself. In this library that loop is the host's. One shape is a thread blocked on `read`. Another is a task on the runtime the host already runs, pulling from `russh` and calling `feed`. The core does not start a runtime and does not spawn a thread.

Disconnect is the end of `read`. The core does not panic. A resize that fails is reported. The grid keeps the new size. The next read surfaces a dead session.

The queue between the session task and `feed` is bounded. The scrollback cap bounds the grid. The session window adjustment stays in the `russh` connector. This library does not speak channel windows.

Init-or-raw and the Scope A listener stay in the connector the client supplies. `feed` sees terminal bytes.

**semantic-key:** PK-6940

## Paint

The host paints. The core exposes the grid, the cursor, the bell flag, and a dirty flag. The first dirty flag means the grid changed. A full repaint of a chat pane is the first approach. A dirty rectangle waits until a measurement says the full repaint costs.

Cell size in pixels is the host's. The host computes columns and rows and calls `resize`.

The core does not depend on `iced`, `egui`, or `winit`. Those stay the open window family on the client serial. An adapter that wraps one of them can sit beside this core when a sentence names it.

**semantic-key:** PK-6941

## Checks and safe Rust

The core is checked without a window and without a socket. Feed a byte string. Read the grid.

The first set:

- A line plus CR LF lands on the next row.
- Backspace from the host moves the cursor.
- An SGR color wraps a word, and reset clears it.
- A cursor address plus erase redraws a line the way the host shell redraws the input line.
- A UTF-8 character split across two feeds prints one character.
- An unknown CSI is consumed, and the next print lands in the right cell.
- Resize pads or clips, and does not panic.
- The scrollback drops the oldest line at the cap.
- A cursor-position report returns the cursor the grid holds.
- Alternate-screen enter and leave restore the primary transcript.

The core stays in safe Rust. The grid, the parser seat, the key encoder, and the connector trait allocate and borrow in safe code. A later renderer that needs an unsafe block for a pixel blit lives in the adapter. That block is a build-day look, and that is when the specialist is called.

This account writes no crate, no crate file, and no version pin.

**semantic-key:** PK-6942

## Thread model

The first implementations use a simple thread. The later shape waits until the simple form has run out. The team then writes the unified shape on this page before it is built.

The simple form is one ordinary thread that blocks on `read` and calls `feed`. The UI thread paints between those calls. A bounded queue sits between them. When the host already runs a runtime, the read side is a task on that runtime. That task is the same simple form. The core stays synchronous. The core still starts no runtime and still spawns no thread of its own.

For the first client slice the host has no UI runtime yet. The connector keeps `russh` behind the read end of the queue. One thread blocks on that queue and calls `feed`. The UI thread paints.

The robust form, when it is warranted, is one runtime the host owns. The read loop, the paint notices, and a later vault task sit on that runtime. The core stays the sync state machine. The unification happens in the host, around the core. The library grows no runtime of its own.

The signs:

- A second pane wants its own thread, and the threads need a shared queue, a shared grid, or a shared shutdown. Judge the simple form. If it has run out, write the unified shape on this page before it is built.
- The read thread and the paint thread contend often enough that the handoff drops frames or stalls the session window. The same duty.
- The session crate and a later store task would each start a runtime. The same duty. The unified shape is one runtime the host owns.
- Shutdown, reconnect, or resize must be coordinated across more than one thread, and the simple join is no longer obvious. The same duty.

When the simple form still holds, that judgment is recorded on this page. The simple form stays. Either way the judgment is brought back.

The typed-line jitter was a paint inside one host redraw. The read side was painting inside that redraw. That is the contention sign, in a mild form. The judgment is that the simple thread still holds. The host read loop coalesces one burst, then calls `feed` once, then the UI paints once. The quiet gap is 8 ms. The cap is 16 ms from the first byte of the burst, and 8 KiB. The core stays synchronous. No second runtime. The crate change waited for a sentence. The slice records the host writes that cause the burst.

The coalesce is in the tree. The host read loop releases one buffer on the quiet gap, the time cap, or the size cap, then the UI feeds once and paints once. The closed-window check passed. The window stayed closed. A later sit found the jitter gone. The simple thread still holds. No second runtime.

This duty covers the host loop of this library and any later thread the client adds beside it. This account names no runtime crate and no build.

**semantic-key:** PK-6943

## Language and UI

Source in this library follows the language section of the child constitution when a sentence names the build. Rust stays pure to Rust. The docs are rustdoc and they cite the specs.

The chrome around this grid follows the UI section of that constitution when a sentence names the build. Input frames expand. Scroll bars and widgets sit in the same family as the conversational text UI. The grid stays the VT100 pane.

**semantic-key:** PK-6944

## Paste mode

The binary is `aetheris-client`. The conversational stand-up is the VT100 behavior slice. The grid stays the VT100 pane. Private mode 2004 grows a flag the host can read, so a paste can be bracketed. Other private modes stay consumed with effect off. This account writes no crate.

**Truth.** The core is synchronous and runtime-free. `vte` parses. The grid, the cursor, the pen, the ring, and the parser have one owner. `feed` borrows that state and returns. A cell owns its character, its width, and its color. The host paints. The host encodes keys and writes them. Private mode 2004 is a flag. The first thread is one reader and a bounded queue. A burst coalesces for 8 ms of quiet, or 16 ms from the first byte, or 8 KiB, then one `feed` and one paint.

**Reason.** A core that starts a runtime cannot sit inside a host that already picked one, and a hand-rolled parser is the part that desyncs a shell.

**Avoidances.** Do not name an Aetheris type. Do not fork the other client's shell specs. Do not implement `help`, `message list`, or `message find`. Do not rebuild the host line editor. Do not time Esc in the library. Do not start a runtime or spawn a thread in the core. Do not take a lock inside the core. Do not let a cell point into the read buffer. Do not hand-roll the parser. Do not treat an 8-bit C1 control as part of this parser. Do not reflow on resize. Do not play a bell. Do not record an OSC title. Do not put deleted lines into the scrollback. Do not let a combining mark grow a cell without the small cap. Do not speak channel windows. Do not depend on `iced`, `egui`, or `winit`. Do not write a crate file before a sentence names the build. Do not publish. Do not pin a version on this page. Do not add a second runtime for the jitter. Do not paint inside the read of one host redraw.


**semantic-key:** PK-6945
