# org.aetheris.wire

Contract file. Two sides match this component.

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

## A mood change sends the whole pool session once

A mood change sends the whole pool working set once. The same porch rules may send the tail again. The switch off is that change.

Changing the mood in the mood modal clears the saved line and sets a new one. Until this rule, the prior mood stayed in the connected pool session, and turning the canvas switch off left that mood in the session. A held pool session sends the tail when the new working set starts with the held text. The porch rules are set when the session opens. A later tail keeps those rules. The opener and the closer are that header. A collapsed card stays in the working set. An excluded card stays out. Neither card stores the header. Ollama and Grok already send the working set whole. Roux memory already replaces the triple through the desk when the shell frame arrives. The pool child is the delta.

rulesHold compares the porch rules this hold opened with to the rules on this prompt. A null memory does not extend. A different line does not extend. An empty next matches an empty hold. The switch off is an empty rule string. canExtend requires that match, then the tail rules it already had. align stores the rules with the held text. dropHold forgets them. The next success with those same rules may send the tail. MoodDesk.seat stays the Roux rewrite. canvasSystems stays the canvas triple. sshLine stays the shell line. The package-visible static is rulesHold.

No new public type and no new public method are added. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added. The Java client carries this. The Rust client does not change. chrome.rs, grid.rs, and the shell application class stay.

A pool prompt whose porch rules differ from the open session sends the whole working set and opens a session. The next prompt with those same rules may send the tail. Canvas, group, and pipe keep the opener, the constructed mood, and the closer while the footer switch is on and the line is non-blank. The switch off leaves that triple off the working set. The shell still places one triple in the Roux transcript.

**Truth.** When the porch rules on the prompt differ from the rules the open pool session was opened with, including a switch that turns mood off, the client sends the whole working set once and opens a session. The next prompt with those same rules may send the tail. A null memory does not extend. A different line does not extend. An empty next matches an empty hold. A collapsed card stays in the working set. An excluded card stays out. Neither stores the header.

**Reason.** The pool child was the only door still extending a session whose header had changed. Ollama and Grok already sent the working set whole, and the shell already replaced its triple when the frame arrived. A tail is valid only when it still starts with the porch rules the hold opened with. Switch off is an empty rule string, so it is a different line and must send the whole set once. The same rules after that success may tail again.

**Avoidances.** MoodDesk.seat, canvasSystems, and sshLine are not retuned. A collapsed card is not dropped. An excluded card is not pulled in. The Rust client does not grow a pool session here. No door line is reordered beyond this pool send.

**semantic-key:** PK-0467 PK-0468 PK-0469 PK-0470

## AMWP codec

A frame is a sixteen-octet header plus a payload of at most one mebibyte. Java and Rust build that header the same way. Java turns a bad frame into a named failure on a stream. Rust turns a buffer into need-more, bad, or one frame, and its demux then sits in Wait, Mux, Raw, or Broken. The execute text is a separate line codec. It is not the frame header.

**semantic-key:** PK-0471 PK-0472 PK-0473 PK-0474 PK-0475 PK-0476 PK-0477 PK-0478 PK-0479

## 6. Java frame and listener

An `AmwpFrame` is the channel, the type, the flags, and the payload octets from one successful decode, or the same four values from a direct constructor.

The constructor stores a null payload as a new empty array. It stores a non-null payload as that same array, not a copy. The getters return those stored values. `getPayload` therefore aliases a caller-supplied array. A frame from `parse` holds the array `parse` allocated.

`isFragment` is true when `(flags & 0x04) != 0`. No other flag bit is named. Encode and parse do not branch on the bit.

`AmwpClientListener` is three callbacks. The interface does not switch threads. Its contract is that the caller invokes them off the UI thread. The wire package does not call them.

- `onAmwpFrame(frame)` receives one scope or stream frame.
- `onAmwpClosed(reason)` receives the caller's reason string when the mux has gone raw or has closed. This interface does not fix the reason text.
- `onConsoleModel(model, think)` is the console asking for the model-config surface. `model` is the shell's model id, empty when the shell has none. `think` empty means off, the word `true` means on, and any other value is a level. The interface does not check those words.

**Truth.** A frame value is four fields and a fragment-bit test. The listener is a callback contract, not a decoder and not a thread hop.

**Reason.** The payload has to stay raw octets so a terminal frame and a TOON frame can share one decode. The listener has to stay outside the codec so a CRC failure cannot be mistaken for a model-config call.

**Avoidances.** Do not UTF-8-decode inside `AmwpFrame`. Do not copy the payload in the constructor. Do not treat `isFragment` as a reassembly signal. Do not add a fourth listener method. Do not invent a fixed close-reason catalog.

**semantic-key:** PK-0480

## 8. Java peek stream

The peek stream is the only reader of its source. A blocking wait takes from an unbounded queue of boxed integers. It does not call `available` on the source.

Construction starts a daemon thread named `amwp-byte-pump`. The thread runs until the stream is closed or the source fails.

The pump:

1. While `closed` is false, read one octet from the source.
2. A negative read queues `-1` and the pump returns.
3. Any other read queues `b & 0xFF`, which is an integer from 0 through 255, then loops.
4. If the loop sees `closed` before a read, it returns and queues no sentinel.
5. A byte that was read before the loop notices `closed` is still queued, and the following loop check can then exit with no `-1`.
6. Any exception, including a read failure and an interrupted queue put, attempts to queue one `-1`. If that put is interrupted, the interrupt flag is set and no further sentinel is attempted. A data octet whose put threw is not queued.

`pollByte(timeoutMs)`:

1. If an unread integer is stored, clear it and return it. The timeout is not used.
2. Otherwise poll the queue for `timeoutMs` milliseconds.
3. A null poll returns `TIMEOUT`, which is `-2`.
4. An interrupt sets the interrupt flag and returns `-2`, not `-1`.

`unread(b)` stores that integer in the one slot, replacing any previous unread integer. The stored value is returned unchanged by the next `pollByte` or single-byte `read`. The slot accepts an octet or `-1`, and the code does not mask it.

`read()`:

1. If an unread integer is stored, clear it and return it.
2. Otherwise take from the queue, blocking with no timeout.
3. An interrupt sets the interrupt flag and returns `-1`. That is the same numeric result as a queued end, with the interrupt flag set.

`read(buf, off, len)`:

1. A null buffer or a non-positive length returns 0 and does not take an unread value or a queued value.
2. Take the first octet through `read()`.
3. If it is negative, return `-1` and do not write the buffer.
4. Store it at `buf[off]`.
5. While the buffer is not full, poll the queue without waiting. Do not consult the unread slot in this loop.
6. A null poll ends the fill. The method returns the count already stored, which is at least 1.
7. A queued value less than zero is not stored in the buffer. The unread slot becomes `-1`, whatever the negative value was, and the fill ends. The next `read` or `pollByte` therefore observes end.
8. Any other queued value is stored and the count grows.

`available` is the queue size, plus one when the unread slot is set. Octets still inside the source are not included. `close` sets `closed`, interrupts the pump, and closes the source.

**Truth.** The pump is the sole source reader. `pollByte` returns `0` through `255`, `-1` for a queued end, or `-2` for a timeout or an interrupted wait. `read` uses `-1` both for end and for its own interrupt.

**Reason.** A source can report no bytes available while a blocking read would still return data, so the wait has to sit on the queue the pump fills. One unread slot is enough to put back the single end marker a bulk read must not swallow.

**Avoidances.** Do not call `available` on the source to decide a blocking peek. Do not use `-2` as an octet. Do not let a bulk read treat a queued negative value as data. Do not drop that negative on the floor. Do not wait for a full bulk buffer. Do not start a second reader on the same source.

**semantic-key:** PK-0481 PK-0482
