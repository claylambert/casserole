# Server

[Pre-plan](pre-plan.spec.md). [Index](../common.spec.md). [Roux](roux.spec.md).

# AA-SDD integrity & round-trip sync — Finalized

**Mode:** **FINALIZED** 2026-09-01. AA-SDD process. **Not product Java. Not a Stage 1 gate.**
**Master:** the stages working spec, §10.6 (dashboard only).

`[SPEC-ID: CORE-SPEC-LINK-002]`
`[STATUS: finalized]`

## 0. How to read

| File | Role |
|---|---|
| This serial | Detail, refuse/accept, procedures |
| Master working spec §10.6 | High-level dashboard only |

### 2.1 Hole

Keys move (`SCRATCHED`, serial bump, rename). Code comments lag. Humans delete tags. Nothing in `javac` cares.

### 2.2 Seal (markdown only)

1. Collect live keys from the forward index.
4. Every tag KEY must be **live** or listed in `../global/aa-sdd-retired.md`. Unknown KEY = defect.
5. Code still tagging a **retired** key = defect until the comment is updated.

**Retired log** (not a REQ catalog): one markdown table, append-only.

| Retired key | When | Successor | Note |
|---|---|---|---|
| *(none yet)* | | | |

Do **not** stamp `[STATUS: VERIFIED]` on every Java line. Verification is the sweep, not a flag that rots.

### 2.3 Garbage collection without AST

### 3.1 Hole

A turn dies after three of five files. Repo is half-new. True.

- Pollutes `SPEC.manifest.md` (product/SYSTEM classpath) with ephemeral WAL.
- Grok must remember to COMMIT. If the session died, it also forgot the TX file. Circular.
- **Git** already is two-phase: worktree = PENDING, commit = COMMITTED, `git checkout` = rollback.

- **Git** is the store of record for NPC files.

### 4.2 Seal without DEC-IDs

The **working spec / law paragraph is the rationale**. The tag **points at it**.

```
```

Do **not** mint `DEC-CORE-2006-0410`. If a decision is big enough to name, it is already a `G-nn`, `L*`, or `Q-*`.

Deep refactors: Grok must open the cited spec section before changing a tagged method. That is already “follow every markdown link.”

## 5. Human-edited NPCs

If a human edits Java:

- If behavior changes the *why*, update the **spec serial** (N+1), then the tag — not the other way around. English first.

## 6. Staging

| Piece | When |
|---|---|
| Retired log file (empty table) | **This turn** (stub). |
| 2PC in manifest | **Never** (scratched). |
| `DEC-*` / `@rationale:` namespace | **Never** (scratched). |

**Impact on Stage 1 product:** comment lines only. No new runtime, no new Maven plugin, no extra AMWP.

## 7. Q (this sub-spec)

- **A.** That path.

Master working spec does not block Stage 1 on these.

*Do not implement product Java from this serial. Do not implement 001’s meta-syntax. Master working spec is finalized. Stage 1 pre-plan is next.*

## 1. Symptom

On Aetheris Shell **Connect** to Casserole, the input line showed:

```
chat> N���
```

**Only on connect.** Afterward typing was clean. OpenSSH `ssh -p 2222` was not the report (raw clients never send Init).

## 2. Fingerprint

```
ae 01 02 00 00 00 00 00 18 00 00 00 07 4e 91 d2 00 00 00 00 01 00 00 00 …
```

## 3. Cause

Server `AmwpAwareShell.peekInit` polled `InputStream.available()` for 400 ms. MINA’s SSH pipe typically reports **0 until a blocking `read()`**. Peek timed out → ChatShell started **raw** and wrote a clean `chat> ` → Init then arrived on stdin → UTF-8 reader + `echoChar` seeded the line.
**semantic-key:** PK-714
**semantic-key:** PK-714

## 3. Cause

Client `tryAmwpInit` had the same `available()` footgun for the ack; leftover first byte of `Welcome` happened to reconstruct the banner (`W` + `elcome`).

## 4. Resolution

| Piece | What |
|---|---|
| `org.aetheris.wire.AmwpPeekStream` | Sole reader of the SSH octet stream; blocking `pollByte(timeout)` |
| `AmwpAwareShell.peekInit` | Uses the pump; ChatShell does not start until Init is decided |
| `org.aetheris.wire.AmwpInitEater` | Raw-path guard: discard **one** leading Init if peek lost the race. **No ack** (client already raw) |
| `SshTtyConnector.tryAmwpInit` | Same pump on inverted stdout for the ack |
**semantic-key:** PK-715
**semantic-key:** PK-715

## 4. Resolution

Connect: Init is eaten before ChatShell `run()`, or stripped on first stdin read. Prompt is a bare `chat> `. OpenSSH still times out to raw PTY.

## 5. Spec impact

| Head | Action |
|---|---|
| Constitution §7 Init-or-raw | **No rewrite.** Claim remains true. |
| This BF spec + `bugs-fixed.md` | **This turn.** |

## 6. Files

- `src/main/java/org/aetheris/wire/AmwpPeekStream.java`
- `src/main/java/org/aetheris/wire/AmwpInitEater.java`
- `src/main/java/org/aetheris/ssh/AmwpAwareShell.java`
- `src/main/java/org/casserole/app/ui/ssh/SshTtyConnector.java`

## 7. Verify

Restart **Casserole** and the **Shell**. Connect. Input line is `chat> ` with no `N`/`` seeded. Later typing unchanged. `ssh -p 2222` still raw.

**Kind:** ad-hoc request spec (bug-fix description). **Not a stage plan.**
**Module:** aetheris (AMWP handshake).

## 1. Symptom

Full rebuild, both apps restarted. SSH Connect succeeds. Status is only `Connected host:port`. Aetheris tab stays **grey**; click is a no-op. No `chat>` typing required (not a first-line flag).

## 2. Fingerprint

## 3. Cause

Order was:

1. Server `start()` peeks 400 ms (client cannot write yet)
2. Timeout → raw ChatShell + welcome
3. `start` returns → open completes
4. Shell sends Init → Init eater swallows, no ack
5. Shell sees `W` from Welcome → stays raw

Handshake was structurally impossible. First chat was not a gate.

## 4. Resolution

## 5. Spec impact

## 6. Files

## 7. Verify

Restart **Casserole** and the **Shell**. Connect. Aetheris tab **enabled**. Status can show vault/AMWP. `ssh -p 2222` still raw (tab N/A).

**Module:** aetheris (Tab B Swing).

## 1. Symptom

Control-panel font size applies to JediTerm. Cell cards and the inspector stay at hardcoded 12/13 pt. Changing the spinner does not update Tab B.

## 2. Cause

`AetherisNotebookPanel` used `new Font(MONOSPACED, PLAIN, 12|13)`. `SshClientApp.fontSizeChanged` only walked SSH `TerminalSession` panels.
**semantic-key:** PK-716
**semantic-key:** PK-716
**semantic-key:** PK-716

## 2. Cause

## 3. Resolution

Notebook takes the live point size at construct. `setUiFontSize` reapplies monospaced body text (inspector + card areas) and scaled chrome on font-change events. `fontSizeChanged` forwards to the notebook.

## 4. Spec impact

None. No constitution sentence became false.

## 5. Files

- `src/main/java/org/aetheris/swing/AetherisNotebookPanel.java`
- `src/main/java/org/casserole/app/ui/ssh/SshClientApp.java`

## 6. Verify

Set font size in the strip. Aetheris inspector and cell text match. Change the spinner again; Tab B updates without reconnect.

## 1. Symptom / ask

Ram embedded H2 is ephemeral across Shell restart. Product default should be **disk** at `~/.aetheris/{user}/vault`.

## 2. Resolution

## 3. Spec impact

## 4. Verify

`vault.mode` omitted or `disk` → file under `~/.aetheris/{user}/vault`; restart Shell, cells remain. `mode: ram` → gone on restart.

## 1. Symptom

## 2. Fingerprint

`~/.casserole/ssh-client.yaml` has `ui.aetheris.inspector.wrap.columns` after a column change. It has no divider key. The next launch lays the bar out from preferred sizes. The inspector's preferred width is the saved column count times the live character width.

## 3. Cause

## 4. Resolution

The shell key `ui.aetheris.inspector.divider` stores the left side as per mille of the span beside the divider, 0 through 1000. A missing key still means the preferred-size layout. The key stays out of `applyDefaultsForMissingKeys`. It is not a vault column and not `application.yaml`.

After the split has a real width, a saved value is applied once. Later moves are written on a 400 ms timer, the same delay the frame size uses. Exit flushes the live ratio from `closeVault` before the process ends. The wrap-column key is unchanged. The inner inspector bar still scrolls a wide column.

## 5. Spec impact

## 6. Files

- `src/main/java/org/casserole/app/ui/ssh/SshClientProperties.java`

## 7. Verify

When clicked: drag the canvas/inspector bar, exit, relaunch, and the bar returns. Change the wrap columns and relaunch. The column changes. The bar stays. A shell file with no divider key still opens on the preferred-size layout.

## 1. Symptom

> When the Aetheris pane is running a request and is thinking, and I switch the Aetheris pane to a different user and then switch back to the user I started from that is supposed to be thinking, the request seems to be disconnected or terminated, maybe interrupted.

The spinner is gone when he returns. The answer does not land on that cell if it arrived while the other user was showing.

## 2. Fingerprint

One Aetheris pane. Two connected AMWP sessions. Run on user A. The footer combo selects user B, then user A again.

No `TYPE_CANCEL` is written. The SSH session for A is not closed. The server `complete()` is not interrupted.

What does change, in order:

## 2. Fingerprint

2. That calls `selectSession` on B's pane.
3. `syncAetherisVault` sees that the open vault is not B.
4. It calls `stopThinking()`, increments `aetherisEpoch`, calls `clearAetherisListener()`, then `bindUser`.
5. `clearAetherisListener` sets A's connector listener to null.

The same four steps run again on the way back to A. The spinner is not started again.

## 3. Cause

The visible vault and the in-flight run are one switch. Leaving the user is treated as the end of the think.

`stopThinking` only stops the footer timer. The comment on it says the SSH session stays up. Nothing in the switch path starts that timer again when A is bound a second time. The chair comes back to a quiet footer while Ollama is still in `llmService.complete`.

## 3. Cause

`AmwpFacade.handle` cancels a run only on `TYPE_CANCEL`. The switch does not send that frame. `inFlight` still holds the Lamina handle until `execute` finishes and removes it. The model call runs to the end. The result is what gets thrown away.

A second Run on that same cell, while `inFlight` is still not done, is ignored by the server. The client would start the spinner again and then wait for a frame the server will not send for that second write.

## 4. Proposed fix

Keep the run on the session that started it.

- `syncAetherisVault` may hide the spinner when the visible user changes. On `aetherisVaultShown` for the user that still has an open run, start the spinner again. Remember the username beside `runningCellId`. Clear that pair when that user's answer, end, or error frame is delivered.
- Do not drop Scope A frames while the listener is null. Hold them on that connector. Drain them only when the listener is aimed at that connector and that user's vault is the open one. User B's canvases must not receive user A's frame.
- Do not send `TYPE_CANCEL` on a user switch.

## 5. Spec impact

## 6. Files

## 6. Files

## 7. Verify

When clicked: switch away during a think and back. The spinner is showing again if the answer has not landed. The answer still paints on A's cell if it arrived while B was showing. B's canvas does not gain A's text. The SSH session stays up.

**Face.** The spinner stops on the way out and does not come back, so the chair reads a dead run while the model is still working.

**Van.** The hold queue is on the connector. The spinner remembers the user. No cancel frame.

## 9. Applied

`runningUser` sits beside `runningCellId`. `stopThinking` hides the spinner and leaves the pair. `resumeThinkingIfOpen` starts it again when that user is back and the pair is still set. The pair clears on the answer frame, the end frame, or the error frame for that cell.

A user switch does not write `TYPE_CANCEL`.

## 10. Amendment 2026-10-20 07:29

> the thinking spinner is working as expected now, but the response is still being disconnected somehow from the aetheris cell that initiated the request. I was switching back and forth between two users testing to see if the response made it regardless... it appears to get disconnected from the cell in some way.

The spinner half stands. This half is the answer missing the cell that started the run. No Java this turn.

### Cause

The reply is no longer dropped on the connector. It is drained once, then `onAmwpFrame` posts `applyAssistant` to the EDT and forgets the frame. `applyAssistant` paints whatever `canvases` list is installed at that later turn. If that list is the other user's vault, the cell id is absent, the method returns, and the text is gone.

`persistQuiet` writes through the live `vault` field, not the store that owned the cell. `saveCell` updates by id and, on zero rows, inserts. User A's cell written into user B's open store does not update A's row. The insert lands in B's database under A's canvas id. B never loads that canvas. A's store already took `persistAll` without the assistant text. The answer is off the cell that asked.

Switching back and forth hits this because the bind and the paint do not share a turn.

`syncAetherisVault` only bumps `aetherisEpoch` when the open vault is a different user. A click back to A while A's vault is still the open one re-aims, drains, and returns. The queued bind of B still holds that epoch, so it runs, calls `persistAll`, closes A, and opens B. The answer is either snapshotted too early or saved into the vault that happens to be open afterward.

The server still does not see `TYPE_CANCEL`. The model call still finishes. The cell loses the text on the client.

### Proposal

Not applied.

- Remember the answer by sanitized user and cell id before the frame is discarded. A miss does not consume it.

### Proposal

- A click back onto the user who is already open must bump `aetherisEpoch`, so a queued bind of the other user cannot close this vault.
- `persistQuiet` must not write A's cell through B's live store.
- Do not send `TYPE_CANCEL`. The spinner behavior from 07:19 stays.

## 11. Applied 2026-10-20 07:37

The answer is remembered by sanitized user and cell id before the frame is forgotten. A miss leaves it there.

## 11. Applied 2026-10-20 07:37

`persistQuiet` captures the store at schedule time. It does not follow a later live vault.

Every focus bumps `aetherisEpoch`. `retainIfOpen` does that bump while the wanted vault is still open, so a queued bind cannot close it. The swap itself checks the epoch under the same lock and closes the new store instead of the current one when it has lost.

## 12. Amendment 2026-10-20 08:11

The 07:19 hold and the 07:37 cell attach stand. No Java this turn. The server call is still not cancelled.

### Cause

The combo then selects user B. `stopThinking` hides the spinner. The bind opens B's vault. `resumeThinkingIfOpen` starts the spinner only when the open user equals `runningUser`. That is still A, so B's pane is idle until B hits Run.

B hits Run. `runTarget` replaces the pair with B and B's cell, then starts the spinner. A's record is gone. A's SSH session is still inside `complete`. Nothing sent `TYPE_CANCEL`.

Switching back to A hides the spinner again. `resumeThinkingIfOpen("A")` returns immediately because `runningUser` is B. A's answer is not on the cell yet, because A's `complete` has not returned, so there is no DATA frame to paint. The imported text is the saved cell. The pane shows no spinner and no answer. That is the turn that looks like it never started.

Switching back to B calls `resumeThinkingIfOpen("B")`. The pair is still B, so the spinner starts again. B's DATA frame has not arrived, so `finishRun` has not cleared it.

### Proposal

- Keep one run record per AMWP session: the sanitized user, the cell id, and that session's connector. A Run on B must not erase A's record.
- Show the spinner only while the vault on screen has a record. Hide it when the vault on screen does not.
- A DATA, EOF, or ERROR frame clears only the record whose cell id matches.
- Aim a listener on each connector when that session connects, and leave it aimed. A user switch must not set the other connector's listener to null. The hold queue stays for a listener that is actually absent.
- Keep the answer map from 07:37. Paint that user's cell only while that vault is open. Save through that store.
- Do not send `TYPE_CANCEL`.
- Do not change Ollama's parallel slot in this fix. The hello waits because the long execute holds the model. The client record is what makes A look idle and B look stuck.

**Face.** A looks idle. B looks stuck. The chair reads a dead turn and a think that will not end.

**Van.** The hold queue is doing its job. The single listener slot is the seam. The run record has to sit on the session, beside the mux, not on the one pane.

## 13. Gap 2026-10-20 08:19

### Holes

1. **The spinner stop is global.** `finishRunNow` clears the one pair and stops the one spinner. §12 says a frame clears the matching record. It does not say to leave the other user's spinner alone. A's DATA arriving while B is on screen would stop B's spinner. B's DATA while A is on screen would stop A's.

2. **Run can fire between focus and the vault bind.** `selectSession` points `focused` at the new connector, then `bindUser` opens that vault on the worker. Until the bind finishes, `openUsername()` is still the previous user and `focusedAmwpConnector()` is already the new one. A Run in that gap would store the record under one user and write the execute on the other connector. The listener on that connector is stamped with the other user. The answer would be remembered under the wrong key.

3. **A dead session leaves the record.** Disconnect, pane close, and a connector that goes away do not remove a run record. `onAmwpClosed` only sets the status line. The next time that vault is shown, the spinner would start for a run that can never answer.

### Accepted limit

One cell id per user. A second Run on the same vault replaces that user's cell id. The spinner follows the latest Run. An older cell on that vault can still be inside `complete` after the spinner stops. The answer map still paints it. The combo already keeps one entry per sanitized user, and two panes with that name share one vault. The map key is that user. The value holds the cell id and the connector that was written.

### Build rules for the same proposal

Still not applied.

- Key the run map by sanitized user. The value is the cell id and the connector that was written.
- Run writes only when the open vault's user equals the focused connector's user. Otherwise do not write, and do not arm a record.
- A frame clears only the entry whose cell id matches, on the EDT. Stop the spinner only when that user is the vault on screen.
- Leave a listener aimed on each connector for the life of that connector. A switch must not set the other listener to null.
- On connector close or pane close, drop the entry only when the closed connector is the one stored. A close status is for the vault on screen.
- The run map is touched from the EDT and from the demux thread. One lock. The spinner moves only on the EDT.
- Keep the 07:37 answer map. No `TYPE_CANCEL`. Do not change Ollama's parallel slot.

**Face.** After the build, A keeps thinking. The hello can still sit.

**Van.** The key is the sanitized user. The connector is the value, not the key.

## 14. Proposal 2026-10-20 08:23

How the holes get closed. Not applied. No Java this turn.

The run map replaces `runningCellId` and `runningUser`. The key is the sanitized user. The value is the cell id and the connector that was written. One lock covers the map. The spinner moves only on the EDT. `startSpin` already does nothing when the timer is running.

### Hole 1. The spinner belongs to the vault on screen

`clearRun(cellId)` runs on the EDT. It removes the one entry whose cell id matches. It stops the spinner only when that entry's user is `openUsername()`. A frame for the other user leaves the spinner as it is.

`showRunFor(username)` runs when that vault becomes the one on screen. An entry starts the spinner. No entry stops it.

`syncAetherisVault` stops calling `stopThinking()` at the start of a switch. The old cells are still on screen until the bind's EDT installs the new canvases. The spinner stays with those cells. `showRunFor` runs in that same EDT turn, after the canvases are installed, and on the retain path when the vault was already open.

`finishRunNow` goes away. DATA, EOF, ERROR, and a failed write all call `clearRun` with the cell id.

### Hole 2. Run waits until the vault and the connector are the same user

`Host` gains `focusedVaultUser()`. It is the sanitized user of the focused session.

### Hole 2. Run waits until the vault and the connector are the same user

When they match, it stores the entry under that user, starts the spinner, and writes the execute on that connector.

### Hole 3. A dead connector drops its entry

`sessionDisconnected` and `sessionError` drop the entry before `stopSession`, and they do it when the dead session is not the focused one. Today those two methods return first in that case, so a background session would leave the record behind.

`closeFocusedPane` reaches the same drop because `dispose` calls `disconnectAndWait`, which calls `sessionDisconnected`.

`onAmwpClosed` uses the same drop. A local `close()` sets the connector closed before the demux loop can report, so the session callbacks are the path that actually runs for a disconnect. The listener path covers a remote close that still has a listener.

### Listeners stay aimed

`sessionConnected` aims that session's listener before the focused-session check, so a pane that connects in the background is armed. The listener is stamped with that session's sanitized user. A switch does not move it.

### Left as they are

The 07:37 answer map stays. A late frame for a user who is not showing is remembered and painted when that vault is on screen.
**semantic-key:** PK-836

### Left as they are

A second Run on the same user replaces that user's cell id. The spinner follows the latest Run. An older answer on that vault still paints.

**Face.** A keeps thinking when you come back. B keeps thinking until B's frame arrives. The hello can still sit.

**Van.** The listener stays on the connector. The switch stops pulling it off.

## 15. State object 2026-10-20 08:25

Not applied. This amends the §14 value. The holes close the same way.

### What is one for the whole pane

`ThinkSpinner` is one widget. The notebook is one set of canvases. One `VaultStore` is open. The rest are on disk. `ChatShell` lives in the server process, on that SSH session. The client does not hold a shell object.
**semantic-key:** PK-717
**semantic-key:** PK-717
**semantic-key:** PK-717

### What is one for the whole pane

One object in the map. That is the part. The spinner stays bolted to the pane. A spinner per user is a gearbox. A second notebook per user is a gearbox. An open vault per user is a second pool. A client copy of `ChatShell` is a second machine the server already runs. I ain't building those.

### The object

One instance per sanitized user. The key is that user.

- The connector that was written. That is the session binding.
- The cell id in flight, or none. That is the request staying alive while the vault is away.
- The answers for that user. The 07:37 map moves onto this object, so one lookup returns the connector, the live request, and the text waiting to paint.

The pane keeps the one spinner. When the canvases change, it reads the object for the user on screen. An in-flight cell starts that spinner. No cell stops it. A frame for someone else changes their object and leaves the spinner alone.

The object does not hold the spinner, the canvases, an open `VaultStore`, or a `ChatShell`.

**Van.** The connector on the object is the mux. The listener stays on that connector.

**Face.** You come back, the object is still there, the spinner tells the truth.

## 16. Applied 2026-10-20 08:33

`clearRun` removes that cell id and stops the spinner only when that user is the vault on screen. DATA, EOF, ERROR, and a failed write use it. An execute error sets the status only for the user on screen.

`syncAetherisVault` does not hide the spinner and does not clear the other listener. The spinner changes in the EDT turn that installs the new canvases, through `showRunFor`. The retain path uses the same call.

`aimAetherisListener` aims a connector once. `sessionConnected` aims before the focused-session check. `onDisconnected` and `onError` keep the connector that they clear, and pass it to the pane. `sessionClosed` clears the in-flight cell only when that connector is the one stored. Answers already stored stay. The spinner and the status line change only when that user is on screen.

**Van.** The listener stays on the connector. The switch does not pull it off.

**Face.** Come back and the object is still there. The spinner tells the truth. The hello can still sit behind the long execute.

## 17. Closed 2026-10-20 09:47

The hello can still wait on the one Ollama model. That limit stays on the card. It is not a new bug.

**Face.** He came back and the run was still there.

**Van.** The mux stayed aimed.

**Module:** aetheris (notebook To terminal). The focused session lives on the casserole client.

## 1. Symptom

To terminal paints a crawling line. A long cell takes forever to paint and stays on one line. A newline in that write is Enter, so a page cannot arrive as a page.

## 2. Fingerprint

`pushPty` confirms, then calls `writePty` with the cell text as raw UTF-8. There are no bracket markers. The shell redraws each glyph. A raw `\n` submits.

## 3. Cause

## 4. Resolution

A burner returns `ephemeral` before the body is used.

The reply is sent when its trim is not empty. Otherwise the prompt is sent. A prompt that is only spaces is sent. An empty string returns. The dialog stays closed and the status stays.

## 4. Resolution

Confirm stays. Cancel returns with the status unchanged and writes nothing.

The confirmed body goes through `sendTerminalPaste`. No focused session, or no live connector, throws `no terminal`, and the status stays `terminal write failed:` plus that message. `sendPaste` wraps the markers. `sendPasteAndSubmit` is not called. No trailing Enter.

An Excluded turn still writes. `writePty` stays on the host. To terminal does not call it.

A reply that is only newlines has an empty trim, so the prompt is sent. That is the same body rule as the raw button.

This plate does not change the Rust client. Enter on the shell still sends the page once.

## 5. Spec impact

None on a living constitution sentence. `casserole/app-design.md` stays the 2026-08-25 as-built capture.

## 6. Files

- `src/main/java/org/aetheris/swing/TerminalPaste.java`
- `src/main/java/org/casserole/app/ui/ssh/ConsolePaste.java`
- `src/test/java/org/aetheris/swing/TerminalPasteTest.java`

## 7. Verify

When clicked: close the window, run the Java client, and use To terminal on a multiline cell. Confirm. The page paints once. Enter was not added. Cancel writes nothing. A burner says ephemeral. A body over the cap is refused before any write.

**Module:** aetheris (mood glass).

## 1. Symptom

A line typed on the mood glass, with the selected card on pool, closes the dialog. The slide says `keyword only. air-gap`.

## 2. Fingerprint

`sendMood` set `ephemeral: true` on every shaping call. The server reads that bit as the burner. `poolDoor` is true only for provider `pool`, burner off, and air-gap word `true`. The bit shut the door. Air-gap is true, so the banner is `air-gap` before `burner denied`.

## 3. Cause

The shaping chat is not a canvas cell. The mood id already keeps the reply off the cell path. The wire bit is not that fact. It is the burner. A normal pool Run leaves the bit off and passes the door.

# Bugs fixed — append-only index

Grok: on triage, read the **last ~20 rows** before inventing a new root cause.
**semantic-key:** PK-837

# Bugs fixed — append-only index

## 1. Symptom

Wrap itself is correct. On the keystroke that crosses the pane width, the **unwrapped line remains**, then a properly wrapped copy is drawn under it. After that, further typing wraps cleanly.

## 2. Fingerprint

One-shot at the wrap boundary, not every character. Matches last-column DEC auto-wrap, not a second echo path (`handleChar` only `insert` + `redrawInputLine`).

## 3. Cause

`redrawInputLine` set `lastDrawnRows = ceil(totalVis / cols)`. When `totalVis == cols`, that is **1**. Writing the last character in the last column makes JediTerm auto-wrap; the cursor is on **row 2**. Next keystroke: `CR` (start of row 2), `lastDrawnRows==1` so no `CUU`, `ED` erases from row 2, then the wrapped redraw starts **below** the original line. The first physical row is the “copy.”

## 4. Resolution

Count physical rows from explicit `\r\n`. After a character that fills a row (`col == cols`), emit `\r\n` and increment `lastDrawnRows` so the cursor is never left in the last column. Next redraw `CUU`s from the true origin and `ED`s the old cells.

## 5. Spec impact

| Head | Action |
|---|---|
| This BF + `bugs-fixed.md` | **This turn.** |

## 6. Files

- `src/main/java/org/casserole/ssh/shell/ChatShell.java` (`redrawInputLine`)

## 7. Verify

## 1. Symptom

Shift+Ctrl+V from the paste buffer in the VT100 console inserts the text and then a carriage return. In `cmd>` the operator needs the text alone, so the line can keep growing before Enter.

## 2. Fingerprint

## 3. Cause

The editor treated OK as insert-and-submit. The shell treated every finished paste as an atomic blob and drew a new prompt. A one-line command paste could not stay on the current `cmd>` line. A trailing newline in the clipboard became part of that submit.

## 4. Resolution

The editor calls `sendPaste`. It does not write `\r`. OK and Ctrl+Enter insert the blob. Enter in the terminal is what submits. Send file uses that same editor.

On the server, a command-mode paste with no interior newline drops any trailing newline, leaves the paste unsealed, and redraws the current line with the cursor after the inserted text. The operator can type or paste another piece. A paste that contains an interior newline still seals and shows the summary, and it waits for Enter. Chat mode still seals. It no longer auto-submits, because the client no longer sends the extra `\r`.

`sendPasteAndSubmit` remains on the controller and is not used by the editor.

## 5. Spec impact

None on a living constitution sentence. `casserole/app-design.md` §7.5 still says the editor calls `sendPasteAndSubmit`. That file is the 2026-08-25 as-built capture. It stays. This ledger is the record. Stage 2 stays closed.

## 6. Files

- `src/main/java/org/casserole/app/ui/ssh/SshSessionController.java`
- `src/main/java/org/casserole/app/ui/ssh/PasteEditorDialog.java`
- `src/main/java/org/casserole/ssh/shell/ChatShell.java`

## 7. Verify

When clicked: in `cmd>`, Shift+Ctrl+V, OK on a short clipboard. The text sits on the line. The command does not run. Type more, or paste again, then press Enter. A multi-line paste still shows the paste summary and waits for Enter.

## 1. Symptom

A page pushed into the VT100 console stays one crawling line. A long cell takes forever to paint. A bracketed paste that contains newlines seals and shows a one-line summary instead of the page.

To terminal still writes raw bytes. That button is the next plate. This plate is the shell.

## 2. Fingerprint

`redrawInputLine` walks the buffer as cells. A `\n` is echoed like a glyph, so the row count ignores hard newlines. Outside paste mode, each typed glyph redraws the whole buffer.

On `ESC[201~`, a paste with an interior newline sets `pasteSealed` and writes `[paste: N lines, M chars — Enter to send]`. The text stays in `lineBuffer`. The screen does not show it. Backspace on a sealed paste clears the blob.

A one-line command paste already stayed on the prompt. A one-line chat paste sealed.

## 3. Cause

The buffer already stored newlines. The paint and the cursor did not. `wrapCursor` counted cells and never a hard newline, so the next key could leave a ghost or eat the rows above the prompt. `finishPaste` treated a multiline paste as an atomic blob.

A raw `\n` outside paste mode is still Enter. That is why a page has to arrive as a bracketed paste. This plate does not change that door.

## 4. Resolution

The row count includes those breaks. The next key erases that many rows and no more. Enter, Ctrl+C, and a bare Escape leave the cursor below the page before they continue.

`finishPaste` drops trailing newlines only when the paste has no interior newline, then paints once. It does not seal. It does not write the summary. The cursor sits at the end of the buffer. A one-line paste, in chat or in command mode, sits on the prompt. A page keeps its interior newlines, including a trailing newline.

While the buffer contains a newline, Up and Down move one visual row, same column, clamped to that row. The first row holds on Up. The last row holds on Down. History waits until the buffer is a single line.

Left, Right, Home, End, word motion, Backspace, and Delete stay on the string. Backspace across a newline joins the rows. Enter still submits the whole page as one turn. `stripEdgeNewlines` still drops only the edges. Ctrl+C still clears. A character typed by hand still redraws. Characters inside the paste stay silent until the paste ends.

`pasteSealed` still swallows a leftover sealed blob. Nothing in this plate sets it. The cap stays `max-paste-chars` (default 65536).

This plate does not call `sendPaste` from To terminal. It does not change the Rust client. Enter does not insert a newline.

## 5. Spec impact

## 6. Files

- `src/main/java/org/casserole/ssh/shell/InputPage.java`
- `src/test/java/org/casserole/ssh/shell/InputPageTest.java`

## 7. Verify

When clicked: close the window, run the Java client, and bracketed-paste a three-line page with Ctrl+V or Send file. Three lines paint. A long row wraps. Backspace across a newline joins the rows. The next keystroke erases only the page. Enter sends the page once. To terminal still writes raw bytes until the next plate.

## 1. Symptom

Holding Backspace on a pasted page blanks the page and rebuilds it. A held letter does the same. The page flashes for the length of the hold.

## 2. Fingerprint

## 3. Cause

The screen update was a full page for one cell. The Java client already honors cursor motion, erase-in-line, and delete-line. Swing paints the chunk it was given. The blank frames are the shell flushing erase-in-display early.

## 4. Resolution

A glyph edit paints the damage and flushes once. Erase-in-display stays off that path.

An edit at the end of the page that stays on the same row moves to that cell. Backspace sends erase-in-line while the cursor sits on the cell. A typed glyph writes that one glyph. The prefix stays.

A wrap that opens uses the same return and line feed the page already parks on. A wrap that closes, or any drop in row count, deletes the extra rows with delete-line (`ESCM`). Erase-in-line clears the leftover cells to the right of a shortened row before a hard newline, and at the end of the tail when the cursor column is past zero.

A middle edit, or a deleted newline, rewrites from the first damaged index through the end of the page, then deletes only the rows whose count dropped. Rows above the damage stay.

Resize paints the whole page once, in one flush, and climbs by the old row count. Keys already in one read share one paint. Enter, Ctrl+C, and a bare Escape flush that paint before they clear the buffer.

The Java client stays as it is. Rust delete-line stays held. Plate 3 is still the Rust client. Enter still sends the page once. Paste stays silent until it ends. The cap stays 65536.

## 5. Spec impact

## 6. Files

- `src/main/java/org/casserole/ssh/shell/PagePaint.java`
- `src/main/java/org/casserole/ssh/shell/Ansi.java`
- `src/test/java/org/casserole/ssh/shell/PagePaintTest.java`

## 7. Verify

When clicked: hold Backspace on a pasted page. The page stays. Cells leave one at a time. A closed wrap leaves no blank row. A deleted newline joins from that point. A held letter leaves the page still. Enter still sends the page once.

# Defect ledger (`bugs/`)

Ad-hoc request specs (bug-fix descriptions) and the append-only index.

**Index:** [`bugs-fixed.md`.

```
bugs/
aetheris/ ← Tab B / AMWP / vault
ssh-client/ ← Tab A Shell (create on first BF)
casserole/ ← host SSH / Lamina (create on first BF)
roux/ ← memory engine (create on first BF)
global/ ← process / AAC (create on first BF)
```

Module folders exist **when they have a BF**, not as empty placeholders.

# Casserole — Application Design Capture

**Document purpose:** freeze the current technical context so brainstorming can continue in this repo and in the main Grok app without re-deriving decisions from chat history.

**Status of this file:** as-built analysis of branch `POC_2026_08_23_00` (artifact `0.0.1-SNAPSHOT`), dated 2026-08-25. It describes what the code does, why it was built that way, and what is deliberately not built yet.

**Not a commitment to ship MCP/REST/Kafka.** Those are product direction. The running system is SSH chat to Ollama plus a standalone JediTerm client.

## 1. Product vision (requirements that drive the app)

Casserole is intended to become an **off-the-shelf MCP agentic server toolkit**: a ready-to-run MCP server *and* the framework around it.

### 1.1 Intended end-state

| Area | Requirement |
|---|---|
| MCP server | Serve tools and agent endpoints to MCP clients. |
| Agentic runtime | Own tool-to-agent wiring and context-window management so the operator does not assemble that stack by hand. |
| App OS | An app-specific operator OS, accessed over SSH, for agents, stats, install, and live config. |
| DSL | Later: a small DSL for workflows and agent config without a full Java change. |
| Live ops | Install agents and new endpoints without a restart cycle. |
| Standard config | Keep `application.yaml` / env / conventional Spring Boot config as the baseline. |

### 1.2 Operator OS (the “casserole>” shell)

The SSH surface is not a generic Unix login. It is a custom line editor that owns:

- Chat turns to a local LLM (Ollama).
- A first-class command set (Escape toggles command mode).
- Paste of multi-line blobs as **one** turn.
- VT100 formatting of model replies for a terminal canvas (JediTerm or OpenSSH).

### 1.3 Constraints the owner has stated (binding)

These are product/engineering rules, not accidental style:

1. **Chat is SSH-only for this slice.** Do not add REST, MCP protocol, or Kafka to the chat path until that work is explicitly started.
2. **No JUnit / coverage push.** End-to-end behavior on a real terminal is the verification method.
3. **No lambdas in Casserole types.** Named types and anonymous inner classes. (Spring/JediTerm APIs may still force a few library-shaped types.)
4. **Explicit Java.** Prefer loops, named methods, and obvious control flow over streams/functional style.
6. **Pure Java runtime** for the app itself. No OS-specific glass/transparency hacks. Ollama is an external process, not an in-process native library.

### 1.3 Constraints the owner has stated (binding)

8. **Commands are objects**, registered explicitly in a factory — not component-scanned, not a giant switch on the line.
9. **Format handlers are discrete stand-alone components.** First deterministic match wins; otherwise identity + CRLF.
10. **POC credentials are not production.** Username/password live in yaml.

### 1.4 What is in the POC today vs not

**In place**

- Spring Boot 3.5 / Java 21 host (`org.casserole.CasseroleApplication`).
- MINA SSHD chat listener on port **2222**, started from `InitRunner` (not from the `SshServer` constructor).

### 1.4 What is in the POC today vs not

- Ollama via Spring AI `ChatModel.call(Prompt)` (non-streaming).

- Smart VT100 formatting, spinner, history, paste dialog, ollama load/stop/exec.

**Configured but unused**

- `spring-boot-starter-web` (embedded Tomcat still starts; no Casserole REST controllers).
- JPA + in-memory H2 (`jdbc:h2:mem:casserole`). No entities, no repositories.
- `sshd-sftp` on the classpath; the production server does not enable SFTP.
- `pty4j` on the classpath; used only by leftover `SshServerTest`, not by `ChatShell`.

**Not built (product backlog)**

- MCP protocol / REST agent API.
- Kafka consumer/producer.
- Workflow DSL and live agent install.
- Streaming tokens / thinking traces in the shell.
- Multi-user auth, host-key verification, TLS, session naming/resume across SSH connections.
- Persistence of chat *conversation* (only command/chat *line history* is persisted).

**Explicitly skipped after brainstorming**

- Window / canvas transparency (`Window.setOpacity` and OS glass). Too brittle; violates pure-Java constraint.
- `ollama run` as a load mechanism (it opens an interactive REPL and hangs). Load uses HTTP `keep_alive: -1` instead.
- Thinking-trace streaming. Spring AI 1.1.x may not expose Ollama thinking metadata on the blocking `call` path. Held off.

## 2. Stack and modules

### 2.1 Runtime

| Piece | Choice | Why |
|---|---|---|
| Language | Java 21 | Current LTS; records/sealed not required by house style. |
| Host | Spring Boot **3.5.0** | Config, lifecycle, AI starter. |
| LLM SDK | Spring AI BOM **1.1.8**, `spring-ai-starter-model-ollama` | Thin wrapper around Ollama HTTP. |
| SSH | Apache MINA SSHD **2.19.0** (`sshd-core`; `sshd-sftp` unused) | Server + client. |
| Terminal UI | JediTerm **3.74** (`jediterm-core`, `jediterm-ui`) from JetBrains IntelliJ Maven | Dark VT100 canvas. |
| PTY helper | pty4j **0.12.25** | Leftover experiment only. |
| Markdown | CommonMark **0.24.0** | Structural markdown detector/renderer. |
| JSON | Jackson (via Boot) | JSON detector/renderer. |
| DB | H2 + Spring Data JPA | Placeholder; unused. |

Maven extra repository: `https://packages.jetbrains.team/maven/p/ij/intellij-dependencies` (JediTerm / pty4j).

### 2.2 Two processes, two entry points

```
┌─────────────────────────────────────┐ SSH:2222 ┌──────────────────────────────┐
│ SshClientApp (Swing / JediTerm) │ ◄────────────────► │ CasseroleApplication │
│ org.casserole.app.ui.ssh │ password auth │ Spring Boot + MINA SSHD │
│ does NOT start Spring │ │ ChatShell per connection │
└─────────────────────────────────────┘ └──────────────┬───────────────┘
│ ChatModel.call
┌─────────────────┐
│ Ollama:11434 │
│ local models │
└─────────────────┘
```

### 2.2 Two processes, two entry points

- Server: `mvn spring-boot:run` → `CasseroleApplication` → `InitRunner` → `org.casserole.ssh.shell.SshServer.start()`.
**semantic-key:** PK-718
**semantic-key:** PK-718
**semantic-key:** PK-718

If Ollama is down, Boot still starts. Chat turns print a **red** error. The SSH listener is independent of the model.

### 2.3 Package map (as-built)

The packages under org.casserole are the child pages on [org.casserole](../java/org/casserole/casserole.spec.md).

Resources:

- `src/main/resources/application.yaml` — server + Ollama.
- `src/main/resources/application.properties` — empty.
- `src/main/resources/icons/casserole.jpg` — window icon (baked casserole in a glass Pyrex dish, black background).
- `hostkey.ser` — MINA host key at project root (generated on first start).
- `src/test/java` — empty; no tests.

## 3. Configuration

### 3.1 Server: `application.yaml`

```yaml
casserole:
ssh:
port: 2222
password: butter
host-key: hostkey.ser
chat:
system-prompt: ""
max-messages: 40
escape-timeout-ms: 100
max-paste-chars: 65536
spinner: ascii # ascii (default) | braille | none
spinner-ms: 100
smart-format: true
table-style: ascii # ascii | box
history-size: 500

spring:
datasource: # unused
url: jdbc:h2:mem:casserole
username: sa
password:
driver-class-name: org.h2.Driver
jpa:
hibernate:
ddl-auto: update
ai:
ollama:
base-url: http://localhost:11434
init:
pull-model-strategy: never
model: qwen2.5-coder:7b
options:
num-ctx: 8192
```

#### `casserole.ssh`

| Key | Default | Role |
|---|---|---|
| `port` | 2222 | MINA listen port. |
| `password` | `butter` | POC only. |
| `host-key` | `hostkey.ser` | `SimpleGeneratorHostKeyProvider` path. |

Auth is exact string equality. No public-key auth, no PAM, no allow-list beyond that one pair.

#### `casserole.chat`

| Key | Default | Role |
|---|---|---|
| `system-prompt` | empty | A new `ChatSession` starts without a system frame. A configured value is message zero. |
| `max-messages` | 40 | Cap on **non-system** turns included in each Prompt. Full in-memory list is still shown by `message list`. |
| `escape-timeout-ms` | 100 | Wait after ESC before treating it as a mode switch. Clamped to ≥ 20 ms (else 100). Range used in practice: 50–150 ms; 100 is the compromise between CSI paste/arrows and a snappy Escape. |
| `max-paste-chars` | 65536 | Bracketed-paste abort threshold. Client duplicates this as `SshClientApp.MAX_PASTE_CHARS`. |
| `spinner` | `ascii` | `braille` / `ascii` / `none`. Unknown → ascii. Braille tofu’s on Consolas; ascii is the supported default. |
| `spinner-ms` | 100 | Frame interval, clamped 40–500 ms. |
| `smart-format` | true | If false, `FormatRouter` is empty → identity + CRLF + link annotate. |
| `table-style` | `ascii` | `ascii` (`+ - \|`) or `box` (Unicode box drawing). Ascii is the tofu-safe default. |
| `history-size` | 500 | Max lines in `~/.casserole/history`. Values &lt; 10 become 500. |
**semantic-key:** PK-719
**semantic-key:** PK-719

#### Spring AI / Ollama

A commented alternative in yaml: `cwc-mistral-nemo-12b:latest`. Switching models is a yaml (or env) change; there is no runtime “select model” command yet.

`ChatShellFactory` default fallback if the property is missing: `qwen2.5-coder:7b`.

### 3.2 Client: `~/.ssh-client.properties`

| Key | Default | Role |
|---|---|---|
| `ui.frame.width` / `ui.frame.height` | 1024 × 968 | Restored window size; saved ~400 ms after resize. |
| `ui.connection.host` | `localhost` | |
| `ui.connection.port` | **22** (not 2222) | Operator must type 2222 for Casserole, or persist it. |
| `ui.connection.username` | OS user name | |
| `ui.connection.password` | *(absent)* | Stored only if Remember password is checked. Plain text. |
| `ui.connection.rememberPassword` | false | |
| `ui.connection.mode` | `vt100` | `vt100` or `pty`. |
| `ui.terminal.font.size` | 14 | Spinner range 8–36. |
| Color keys | dark palette | Frame/panel/field/button/status/split + terminal fg/bg/selection/cursor. |
**semantic-key:** PK-720
**semantic-key:** PK-720

Color defaults (hex):

| Surface | Hex |
|---|---|
| Frame / split | `#1e1e1e` |
| Panel | `#2d2d2d` / fg `#dcdcdc` |
| Field | `#1a1a1a` / fg `#e6e6e6` |
| Button | `#3c3c3c` / fg `#f0f0f0` |
| Status | `#171717` / fg `#b4b4b4` |
| Terminal bg / fg | `#0d0d0d` / `#dcdcdc` |
| Selection | `#345a8c` / `#ffffff` |
| Cursor | `#e6e6e6` |

### 3.3 Server-side history file

`~/.casserole/history` on the **machine running Boot**, not the client.

- UTF-8, one entry per line.
- Newlines inside a pasted blob escaped as `\n`; backslash as `\\`.
- Duplicate consecutive lines are not stored.

### 3.3 Server-side history file

- Shared across SSH connections on that host (not per-user-session-id).

**Chat conversation is not persisted.** Disconnect drops `ChatSession`. `chat stop` keeps history only in memory for that SSH connection.

## 4. LLM dependency and context-window policy

### 4.1 Boundary

```
ChatShell.runLlmTurn
    → ChatSession.buildPrompt(maxMessages, newUserText)
    → LlmService.complete(Prompt)
        → SpringAiOllamaLlmService
            → ChatModel.call(prompt)     // blocking, one shot
    → extract AssistantMessage.getText()
    → FormatRouter.format
    → yellow (or table self-colored) write to PTY
```

`LlmService` is the **only** Spring AI type the rest of the app is supposed to depend on. Commands do not call `ChatModel`.
**semantic-key:** PK-721

### 4.1 Boundary

Implementation today: **non-streaming**. No token callback, no thinking channel.

### 4.2 Prompt assembly (`ChatSession`)

1. System message = `casserole.chat.system-prompt`.
2. `active = true` → shell starts in `CHAT` (`chat>`).

Each turn:

1. Collect the system message.
2. Collect all non-system messages; if more than `max-messages`, take the **tail**.
3. Append the **new user text** (not yet stored).
4. Call the model.
5. On success only: `appendUser` then `appendAssistant`. Cancelled / failed turns do **not** grow history.

`message list` shows the full in-memory list (including system). The model only sees the capped Prompt.

There is no token counting. The cap is **message count**, not tokens. That is a known gap for the “Casserole owns context-window management” vision.

### 4.3 Session lifecycle vs LLM

| Operator action | Session | Shell mode | LLM |
|---|---|---|---|
| SSH connect | new `ChatSession` | `CHAT` | not called until Enter |
| typed chat + Enter | append after success | stays `CHAT` | `complete` |
| Escape | discard unfinished line | toggle `COMMAND` ↔ `CHAT`/`IDLE` | none |
| `chat start` | drop user/assistant, keep system, active | `CHAT` | none |
| `chat stop` | keep history, inactive | `IDLE` | none |
| `clear` / `history clear` | drop user/assistant, keep system | unchanged | none |
| `exit` / `quit` | destroyed with connection | hang up | cancel in-flight |
| Ctrl+C during LLM | no append | prompt redrawn | `Future.cancel(true)` + `turnCancelled` |

One in-flight turn per connection. A second Enter while busy prints `busy — wait or Ctrl+C`.

LLM work runs on a single daemon thread `casserole-llm` so the SSH read loop never blocks on Ollama.

### 4.4 Model load / unload (not `ollama run`)

**Load** (`ollama load` / `ollama start`):

- HTTP `POST {base-url}/api/generate`
- Body: `{"model":"<configured>","prompt":" ","stream":false,"keep_alive":-1}`
- Connect timeout 10s, request timeout 120s.
- Pins the model in RAM until stop or Ollama’s own policy.

**Stop** (`ollama stop`):

- `ProcessBuilder` argv `ollama stop <model>` (no shell), 30s timeout.

**Arbitrary CLI** (`ollama list`, `ollama ps`, …):

### 4.4 Model load / unload (not `ollama run`)

- Longest-name match means `ollama load` / `ollama stop` win over bare `ollama`.

- Combined stdout+stderr, cap 65536 bytes.
- Quotes are **not** supported in the split.

The `ollama` binary must be on the **server** PATH.

### 4.5 Model choice notes (from earlier research, not encoded)

The POC default is `qwen2.5-coder:7b` because it is small enough for local RAM and behaves reasonably for a coding/ops assistant. Larger instruct models (e.g. Mistral Nemo 12B) were discussed for cleaner prose/tables; switching is yaml-only.

No Hugging Face download path exists in Casserole. Models are whatever Ollama already has.

## 5. SSH server design

### 5.1 Lifecycle

- `@PreDestroy shutdown()` sets `running=false`, notifies, `sshd.stop()`.
- `SimpleGeneratorHostKeyProvider(Paths.get(host-key))`.
- `setUpDefaultServer()` + password authenticator + `ChatShellFactory`.

### 5.2 Per-connection factory

`ChatShellFactory.createShell`:

1. `new ChatSession(systemPrompt)`
2. `commandProcessorFactory.create()` — fresh command set (stateless command objects; session is on the context)
3. `formatRouterFactory.create(smartFormat)`
4. `new ChatShell(...)`

`modelName` is injected once from `spring.ai.ollama.chat.options.model`.

### 5.3 ChatShell — the operator OS

`ChatShell` implements MINA `Command`, `Runnable`, and `CommandContext`.

It owns:

- UTF-8 `InputStreamReader` on the SSH channel.
- Line buffer, Escape state machine, paste state, history cursor.
- Echo (green in command mode, default otherwise).
- Prompts: `chat> ` / `cmd> ` / `idle> `.
- Spinner timer, escape timer, LLM executor.

### 5.3 ChatShell — the operator OS

#### Character pipeline

Decoded chars go through `handleChar` (synchronized on the shell):

1. If currently collecting an ESC sequence → append until complete (letter, `~`, or 2-char non-CSI/SS3).
2. ESC → start sequence + schedule timeout.
3. Ctrl+C (`0x03`) → cancel paste / cancel LLM / clear line.
4. CR → paste newline **or** Enter. LF after CR is dropped (CRLF). Lone LF is Enter.
5. BS / DEL → erase one glyph, or abort a sealed paste.
6. Printable → echo and buffer, unless `pasteSealed` (blob is atomic until Enter / Ctrl+C / Backspace).

ISO control chars other than the above are ignored.

#### Escape vs CSI (critical design)

Bare Escape is the **mode switch**. Arrow keys and bracketed paste also start with ESC. Solution:

- On ESC, wait `escape-timeout-ms` (100).
- If more bytes arrive in time and form a complete CSI/SS3, handle that sequence.
- If the buffer is still only ESC when the timer fires, treat as **bare Escape**: discard the unfinished line, toggle `COMMAND` ↔ `CHAT`/`IDLE`.

Handled CSI:

| Sequence | Meaning |
|---|---|
| `ESC[200~` | Bracketed paste start |
| `ESC[201~` | Bracketed paste end → seal blob |
| `ESC[A` / `ESC[OA` | History up |
| `ESC[B` / `ESC[OB` | History down |

Other CSI is **dropped** (not inserted into the line). That is intentional: the shell is not a full emulator.

On start the shell writes `ESC[?2004h` (enable bracketed paste) and on destroy/exit `ESC[?2004l`.

#### Paste (server)

Two client behaviors exist:

**OpenSSH / any client that emits bracketed paste but does not auto-submit**

1. Accumulate silently (`pasting=true`). Newlines become `\n` inside the buffer — they do **not** submit.
2. On `201~`, set `pasteSealed=true` and print `[paste: N lines, M chars — Enter to send]`.
3. Further typing is ignored until Enter, Backspace (abort), or Ctrl+C (abort).
4. Enter sends **one** turn: edge CR/LF stripped, interior newlines kept.

**Casserole JediTerm client**

#### Paste (server)

- OK or Ctrl+Enter → client wraps the blob in `200~` … `201~` **and sends CR**.
- Server still seals, prints the summary, then immediately sees Enter and submits. The summary can flash; the blob is one turn.

Cap: `max-paste-chars`. Over → abort, red error, prompt.

#### History (Up/Down)

- CSI A/B rewrite the current input line (`CR` + prompt + `EL` + echo).
- Draft line is saved when first moving up.
- Down past the newest restores the draft.
- Command mode echoes recalled text in green.
- Sealed paste is not added to history (`persistHistory = !pasteSealed && !pasting`).

#### Spinner

While `runLlmTurn` is in flight:

- Timer paints `CR` + emerald `#00FF66` frame + a space + `#00D7FF` `thinking` + `EL` (erase to end of line).
- ASCII frames: `\| / - \`
- Braille: U+280B… cycle (needs a font with those glyphs; Consolas does not).
- Stop: `CR` + `EL` so the spinner does not become a committed line.

An earlier bug: writing `thinking...` as a normal line blocked overwrite. The `\r` + EL design is required.

`NONE` skips animation.

#### Ctrl+C

| State | Effect |
|---|---|
| Pasting or sealed paste | Abort blob, red `^C`, new prompt |
| In-flight LLM | `turnCancelled=true`, cancel Future, stop spinner, red `^C`, prompt. User/assistant **not** appended |
| Idle typing | Clear line, print `^C`, prompt |

#### Colors (VT100 SGR)

Defined in `Ansi` (stdout, not stderr):

| Token | Sequence | Use |
|---|---|---|
| Green | `\u001B[32m` | Command-mode echo |
| Red | `\u001B[31m` | Errors (`writeError` adds CRLF) |
| Bright yellow | `\u001B[93m` | Assistant body (when renderer asks for body color) |
| Electric blue | `\u001B[38;5;45m` | Spinner; table borders |
| Purple | `\u001B[35m` | URLs (LinkAnnotator) |
| Reset | `\u001B[0m` | |
| CR / EL | `\r` / `\u001B[K` | Spinner, line redraw |

#### Threading / locks

- SSH read thread: `handleChar` synchronized.
- Escape/spinner timers: hop into `synchronized (this)`.
- LLM worker: does the HTTP call **off** the lock, then re-enters the lock to write.
- `write` UTF-8 bytes + flush; IOExceptions swallowed (dying session).

### 5.4 Command processor

`CommandProcessor.execute`:

1. Normalize: trim, collapse whitespace, lowercase.
2. Longest registered **name or alias** that equals the line or is a prefix followed by space.
3. Remainder (trimmed) is `args`.
4. Unknown line → shell prints `Unknown command: …` in red.

This is why multi-word commands work (`chat start`, `message list`, `ollama load`) without eating `start` as args of a fictional `chat` command. There is no `chat` command.

Commands are constructed in `CommandProcessorFactory` in this order:

| Name | Aliases | Behavior |
|---|---|---|
| `chat start` | | `session.reset()`, active, “Chat session started.” |
| `chat stop` | | `session.stop()`, idle, history kept |
| `help` | `?` | Catalog, or `help <name>` long text + aliases |
| `message list` | `messages` | Dump; filter `all` (default) / `user` / `assistant` / `system` |
| `clear` | `history clear` | Drop turns, keep system |
| `status` | | active/idle, mode, ssh-user, model, counts |
| `exit` | `quit` | “Goodbye.” + `requestExit()` |
| `ollama load` | `ollama start` | HTTP keep_alive -1 |
| `ollama stop` | | CLI unload |
| `ollama` | | remaining argv to CLI |

After a command (except exit), shell returns to `CHAT` or `IDLE` from `session.isActive()` — it does **not** stay in `cmd>` until Escape. Escape is only the *entry* to type a command; successful/failed command execution leaves command mode. Empty Enter in command mode also returns to chat/idle.

### 5.4 Command processor

## 6. Reply formatting pipeline

Requirement that drove this: Ollama often returns Markdown with lone `\n`. A VT100 canvas that does not auto-return on LF **staircases** (each line starts where the previous ended). Also: tables, fences, and lists should look intentional, not as raw Markdown.

### 6.1 Contract

1. Empty → empty result, `applyBodyColor=true`.
2. Else try handlers **in registration order**. First `detector.matches(raw)` wins.

4. No match → same CRLF + links on the original text (identity). **Do not invent a format.**

`smart-format: false` registers zero handlers → always identity + CRLF + links.

Detectors are **boolean and strict**. No scores, no “probably markdown.”

### 6.2 Handler order (fixed)

1. **json** — whole-string JSON object or array (Jackson). Scalars do not match. Pretty-print, no wrap. Body color on.
2. **fence** — matching ` ``` ` or ` ~~~ ` opener/closer. Prose wrapped; fence interiors untouched. Body color on.
3. **table** — whole-blob GFM pipe table: header, separator (`---` / `:---:`), ≥1 data row, equal column counts. Drawn as ASCII or Unicode box. **`applyBodyColor=false`** (borders electric blue, cells yellow via FormatContext SGR).
**semantic-key:** PK-343

### 6.2 Handler order (fixed)

4. **list** — every non-empty line is a bullet (`- * +`) or ordered (`1. ` / `1) `) item; ≥2 items. Rendered as hanging `- ` with wrap at `columns-2`. Body color on.
5. **markdown** — CommonMark AST has a heading, fenced code, block quote, or a list of ≥2 items. A single paragraph (even with `*emphasis*`) does **not** match. Renderer drops emphasis markers, keeps structure, wraps prose, lists as `- `, quotes as `> `, fenced code literal. Body color on.

### 6.3 Always-on helpers

**`LineDiscipline`**

- Lone `\n` → `\r\n`
- Existing CRLF kept
- Lone `\r` → `\r\n`
- Trailing CRLF ensured

This is the staircase fix and runs even when smart-format is off.

**`WordWrap`**

- Hard wrap at spaces; overlong tokens split.
- Used by structured renderers **only**, never identity (so preformatted JSON/code in identity path is not smashed — JSON handler also does not wrap).

**`LinkAnnotator`**

- Finds `http://` / `https://`
- Skips if the blob already contains OSC 8, or the URL contains CR/LF
- Wraps: purple SGR + `ESC]8;;URL BEL` + URL + `ESC]8;; BEL` + reset
- Trims trailing `.,; ]`
- Stops URL at whitespace, `<>"' )`

JediTerm also has its own URL highlighter (`getHyperlinkColor` = purple `rgb(180,80,255)`). Click uses JediTerm’s “Open as URL” / `Desktop.browse`. OSC 8 is for capable terminals; the SGR purple is the visual even if OSC is ignored.

### 6.4 Tables

`TextTableDrawer`:

- Column widths from content, then shrink widest columns to fit `FormatContext.columns` including chrome (`1 + 3*n` for borders/padding).
- `ascii`: `+` / `-` / `|`
- `box`: Unicode `┌┬┐ ├┼┤ └┴┘ │ ─`
- Truncates cells that still overflow after shrink.
- Header and body cells use yellow SGR from ChatShell’s `replyContext()`.
**semantic-key:** PK-351

### 6.4 Tables

`FormatContext` from ChatShell: columns from PTY, table-style from yaml, border electric blue, header/cell yellow, reset.

### 6.5 Color after format

`ChatShell.colorReply`:

- If `applyBodyColor` → wrap entire blob in bright yellow.
- Else write as-is (tables already painted).

User-typed input is never yellow.

## 7. Standalone SSH client

### 7.1 Window

- Title: `Casserole SSH Client`
- Icon: `/icons/casserole.jpg` (master + 32 + 16)
- Vertical split: connection panel on top, JediTerm below
- On first show, divider sits at the control panel’s preferred height
- On connect, control panel **collapses**; reconnect/disconnect still available after expand
- Status bar: Connecting / Connected host:port / Disconnected / errors
- Close: confirm, `disconnectAndWait`, dispose widget, persist size

### 7.2 Connection panel

Fields: host, port, username, password, Remember password, Mode combo (VT100 / Standard PTY), Font size spinner (8–36), buttons Connect / Disconnect / Reconnect / Clear Terminal / Send file…

- Persist host/port/user/remember(+password) on focus-lost and Connect/Reconnect.
- Font size persisted immediately; `fontSizeChanged` mutates live JediTerm font via `DarkJediTermSettings.setTerminalFontSize` + `reinitFontAndResize` (exposed by `DarkJediTermWidget` because upstream is protected).
- Mode change: confirm dialog; if connected, reconnect. Cancel restores previous.
- Send file enabled only when connected.

### 7.3 Session / PTY

Connect:

1. Validate host/user/port.
2. MINA client, **AcceptAllServerKeyVerifier** (Version 1; no known_hosts).
3. Password identity, auth verify 15s.
4. Shell channel with `PtyChannelConfiguration` + `TERM` env.

6. Remote close → status “Connection closed by remote host.”

**Raw PTY modes (both VT100 and Standard PTY):**

```
ECHO=0, ICANON=0, ISIG=0, IEXTEN=0, ECHOE=0, ECHOK=0, ECHOCTL=0, OPOST=0, CS8=1
```

Difference between modes today:

| | VT100 | Standard PTY |
|---|---|---|
| `TERM` / pty type | `vt100` | `xterm-256color` |
| PTY mode map | same raw map | same raw map |

The combo exists for a planned fuller POSIX map; both paths currently force raw. Changing mode reconnects.

Resize: JediTerm → `channel.sendWindowChange`. Server ChatShell currently reads COLUMNS only at start.

### 7.4 JediTerm look

`DarkJediTermSettings`:

- Font: Consolas (Windows), Menlo (macOS), Monospaced (else), size from properties/spinner.
- XTERM palette; custom default fg/bg/selection from properties.
- Hyperlink color purple.
- Scrollback 10_000 lines.
- Paste keystrokes advertised as Ctrl+Shift+V **and** Ctrl+V (Ctrl+C remains interrupt at the shell, not copy).

The **app** intercepts Ctrl+V / Ctrl+Shift+V when the terminal is focused and opens the paste dialog instead of JediTerm’s built-in paste.

### 7.5 Paste editor

`PasteEditorDialog`:

- Modal, dark themed, `JTextArea` 18×72, wrap on.
- Font = terminal typeface derived at the **control-panel spinner size** (`connectionPanel.getFontSize()`), so the editor matches the canvas.
- Enter = newline; **Ctrl+Enter or OK** accepts; Cancel discards.
- Client then `sendPasteAndSubmit` (bracketed wrap + `\r`).
- File send: UTF-8, reject NUL / too many control bytes, cap 65536, then same dialog.

OpenSSH clients do not get this dialog; they get the server-side `[paste: …]` summary.

### 7.6 Client vs server history

History Up/Down is implemented **in ChatShell**, not in Swing. The client is a dumb VT100. That keeps OpenSSH and JediTerm equivalent.

## 8. Coding conventions (enforce on new work)

1. Package `org.casserole.*`.
2. No lambdas in Casserole source; anonymous `new Listener {... }`.
3. No stream API for ordinary loops.
5. End-of-file banner: `End of Class: Foo.java MMDDYY`.
6. Commands and format handlers: new class + factory `register`, not a growing switch.
7. Do not add JUnit unless asked.
8. Do not start MCP/REST/Kafka work unless asked.
9. Prefer VT100 CRLF on anything written to the PTY.
10. Errors to the operator: red, short message + first stack frame (`formatError`).

## 9. Design-decision log (why it is this way)

Recorded so we do not re-litigate them without new information.

## 9. Design-decision log (why it is this way)

| Decision | Choice | Rejected / notes |
|---|---|---|
| Chat transport | SSH PTY + custom shell | REST/MCP/Kafka deferred. SSH is the operator OS. |
| LLM SDK | Spring AI Ollama starter | Keep a single `LlmService` so a later MCP path can reuse Prompt assembly. |
| Completion | Blocking `ChatModel.call` | Streaming + thinking traces held off (1.1.x metadata uncertainty). |
| Context cap | Last N non-system **messages** | Token-aware window not built. `message list` still full. |
| Conversation persist | None | Line history in `~/.casserole/history` only. Sessions die with SSH. |
| Escape | Bare ESC after 100 ms | Conflicted with CSI paste/arrows. Timer + completeness parser. |
| Command matching | Longest full name | First-token match broke `chat start`. |
| Command UX | Green echo; leave `cmd>` after execute | Command mode is a one-shot line, not a sticky REPL, except you can Escape again. |
| Paste | Bracketed + sealed blob | Per-line Enter (and PTY `^C`) made POM pastes unusable. |
| Load model | HTTP `keep_alive:-1` | `ollama run` is an interactive REPL. |
| Exec ollama | ProcessBuilder argv | No `/bin/sh -c`. Metacharacter reject. |
| Spinner default | ASCII. Glyph `#00FF66`. Word `thinking` `#00D7FF`. | Braille tofu on Consolas. Do not write a committed “thinking…” line. |
| Newlines from model | Always CRLF | Lone `\n` staircases on VT100. |
| Smart format | First strict detector | No ML classifier. Identity if unsure. |
| Table chrome | ASCII default | Box drawing tofu risk; yaml can opt into `box`. |
| Assistant color | Bright yellow | User text white; command output default; errors red. |
| Links | OSC 8 + purple; JediTerm click | Browser open is a **client** job. |
| Client auth | Password + accept-all host keys | Version 1 POC. |
| Client PTY | Raw (no echo/canon/ISIG) | Server owns the line editor. |
| Font in paste dialog | Match spinner + terminal family | Otherwise the editor looked like Swing default. |
| Transparency | Not implemented | OS-specific; out of pure-Java scope. |
| Tests | None | Manual SSH + JediTerm. |
| Leftover servers | Keep in tree for now | `MinimalSshServer`, `SshServerTest` unused. |
| Web / JPA | On classpath, unused | Future MCP/REST/persistence; do not build against them yet. |
| Icon | Pyrex casserole photo | Baked into `/icons/casserole.jpg`. |
**semantic-key:** PK-722
**semantic-key:** PK-722
**semantic-key:** PK-722
**semantic-key:** PK-722

## 10. End-to-end flows (for regression while brainstorming)

### 10.1 Boot and connect

1. JDK 21, Maven, Ollama running, model pulled.
2. `mvn spring-boot:run` → log `Casserole SSH chat started on port 2222`.
4. Banner + `chat>`.

### 10.2 Chat turn

1. Type (white echo) → Enter.
2. Spinner on current line.
3. Reply yellow (or table) with CRLF, prompt returns.
4. `status` shows user/assistant counts increased.
5. Ctrl+C during wait → red `^C`, no new messages.

### 10.3 Commands

1. Escape → `cmd>`, unfinished chat line gone.
2. Keys echo green.
3. `help` / `status` / `message list` / `clear` / `chat stop` → `idle>`.
4. From idle, Enter does not call LLM; Escape → commands; `chat start` resets turns.

### 10.4 Paste (JediTerm)

1. Set font spinner (e.g. 18). Ctrl+V.
2. Dialog font size matches spinner; edit; Ctrl+Enter.
3. One chat turn, not N.

### 10.5 Ollama ops

1. `ollama load` → HTTP load of configured model.
2. `ollama list` → CLI listing.
3. `ollama stop` → unload.

### 10.6 Failure

Ollama stopped → chat Enter → red error containing message + first stack frame; SSH stays up.

## 11. Known gaps and brainstorming backlog

These are **not** scheduled work; they are the open file for the next conversation.

### 11.1 Product direction (vision)

- MCP protocol server on the same `LlmService` + `ChatSession` (or a shared session store).
**semantic-key:** PK-723
**semantic-key:** PK-723

### 11.1 Product direction (vision)

- REST agent API (explicitly ignored for the current slice).
- Kafka in/out for triggers and results.
- DSL for agents/workflows; live install of agents/endpoints without restart.
- Real context-window manager (tokens, summarization, pinning, tool results).
- Tool wiring: model function-calls → Casserole tools, not user-built glue.

### 11.2 Operator OS

- Sticky `casserole>` prompt / richer command tree vs one-shot Escape.
- Named sessions and resume (`/resume`, session ids). Conversation persist (DB or files). H2 is already on the classpath.
- Per-user auth beyond one yaml pair; public keys.
- Window-change → update `terminalColumns` for wrap/tables.
- Streaming tokens; optional thinking traces if Ollama + Spring AI expose them.
- Copy from terminal (Ctrl+C is interrupt by design; need another chord).
- Client default port 2222 when targeting Casserole (today default 22).
- Host-key verification (today accept-all).
- Encrypt remembered client password.

### 11.3 Formatting / UX

- Identity path still does not wrap long prose (by design). Long unstructured answers can overflow.
- Markdown renderer is conservative (no GFM tables inside markdown handler — tables have their own earlier handler and only match **whole blob**). Mixed prose+table replies fall through to markdown or identity.
- OSC 8 + JediTerm highlighter can double-style links; acceptable.
- Spinner + paste summary interaction when client auto-submits (flash of `[paste:…]`).
- `COLUMNS-2` only at connect.

### 11.4 Engineering hygiene

- `spring-boot-starter-web` / JPA / sshd-sftp / pty4j: keep or drop until needed.
- Duplicate model property (`chat.model` vs `options.model`) — document which is source of truth (`options.model` for status/load).
- Client `MAX_PASTE_CHARS` hardcoded vs yaml `max-paste-chars`.
- No structured logging; `System.out` / `printStackTrace` on SSHD.

### 11.5 LLM

- Model picker command vs yaml-only.
- Guardrails on `ollama exec` (currently any argv that passes the metacharacter filter).
- Empty-reply handling is an error, not a retry.

## 12. How to use this document in Grok

When continuing in this repo or in the main Grok app:

1. Treat **§1.3 constraints** and **§9 decision log** as defaults unless the user overrides them.
2. Treat **§11** as the brainstorming menu, not a TODO to implement unprompted.
3. For new commands: new `Command` class + `CommandProcessorFactory.register`.

## 12. How to use this document in Grok

5. For LLM changes: `LlmService` only; keep ChatShell’s worker/cancel/spinner.
**semantic-key:** PK-724
**semantic-key:** PK-724

6. Do not re-enable transparency, `ollama run` load, lambdas, or JUnit unless asked.
7. README.md is a shorter operator view and may lag this file (e.g. it still lists arrow-key history as unbuilt; history **is** built).

## 13. File checklist (implementation inventory)

**Boot / config**

- `src/main/java/org/casserole/CasseroleApplication.java`
- `src/main/java/org/casserole/config/CasseroleProperties.java`
- `src/main/java/org/casserole/app/InitRunner.java`
- `src/main/resources/application.yaml`

**LLM**

- `src/main/java/org/casserole/llm/LlmService.java`
- `src/main/java/org/casserole/llm/SpringAiOllamaLlmService.java`
- `src/main/java/org/casserole/ollama/OllamaProcess.java`

**Chat / commands**

- `src/main/java/org/casserole/chat/ChatSession.java`
- `src/main/java/org/casserole/chat/ShellMode.java`
- `src/main/java/org/casserole/chat/SpinnerStyle.java`
- `src/main/java/org/casserole/chat/cmd/*`

**SSH shell**

- `src/main/java/org/casserole/ssh/shell/SshServer.java`
- `src/main/java/org/casserole/ssh/shell/ChatShellFactory.java`

**Format**

## 13. File checklist (implementation inventory)

- `src/main/java/org/casserole/format/{json,fence,table,list,markdown}/*`

**Client**

- `src/main/java/org/casserole/app/ui/ssh/SshConnectionPanel.java`
- `src/main/java/org/casserole/app/ui/ssh/SshTerminalPanel.java`
- `src/main/java/org/casserole/app/ui/ssh/SshClientTheme.java`
- `src/main/java/org/casserole/app/ui/ssh/DarkJediTermSettings.java`
- `src/main/java/org/casserole/app/ui/ssh/DarkJediTermWidget.java`
- `src/main/java/org/casserole/app/ui/ssh/SshSessionMode.java`
- `src/main/java/org/casserole/app/ui/ssh/SshConnectionState.java`

**Leftover**

- `src/main/java/org/casserole/ssh/MinimalSshServer.java`
- `src/main/java/org/casserole/ssh/SshServerTest.java`

*End of capture. Update this file when a design decision lands in code, not when it is only discussed.*

# `application.yaml` reference

Boot loads `src/main/resources/application.yaml`. Binders:

- `casserole.*` → `CasseroleProperties`
- `roux.*` → `RouxProperties`
- `spring.*` → Spring Boot / Spring AI (not Casserole types)

Client UI settings live in **`~/.ssh-client.properties`**, not this file.

Values below are **code defaults** when the key is omitted. The checked-in yaml may override (e.g. `max-messages: 200` vs code default 40).

Restart Boot after changes. None of these are live-reloaded today.

## `casserole.lamina`

Stage 0 Lamina executor. Bound on `CasseroleProperties.Lamina`.

| Key | Type | Default | Effect |
|---|---|---|---|
| `metrics` | boolean | `false` | When true, Lamina logs submit/complete/fail counts at DEBUG. Default **OFF**. |

## `casserole.ssh`

POC SSH listener. Not production auth.

| Key | Type | Default | Options / range | Effect |
|---|---|---|---|---|
| `port` | int | `2222` | 1–65535 | MINA listen port. Client must match (`ssh -p`). |
| `password` | string | `butter` | any | Fallback secret. Ignored when a `users.yaml` is present. |
| `host-key` | path | `hostkey.ser` | relative or absolute | `SimpleGeneratorHostKeyProvider` file. Created if missing. |

## `casserole.chat`

Shell and **prompt window** (not Roux page size).
**semantic-key:** PK-838

| Key | Type | Default | Options / range | Effect |
|---|---|---|---|---|
| `system-prompt` | string | empty | any | A blank value leaves a **new** region without a system frame. A configured value is the first **SYSTEM** frame on a new region. Restore does not rewrite an existing history. `chat start` and `clear` keep a configured value and leave a blank value off. |
| `max-messages` | int | `40` | `> 0` recommended | Non-system turns in the **Ollama Prompt**. Roux still stores everything. `0` in window API = “all”; yaml should stay positive. |
| `max-tokens` | int | `0` | `0` = off; else stored heuristic tokens | Dual cap with `max-messages`. First limit wins, **except** a user/assistant (or tool+assistant) pair is never split (may slightly exceed token cap). |
| `escape-timeout-ms` | int | `100` | code clamps `< 20` → 100 | Bare ESC vs CSI (paste/arrows). Typical 50–150. |
| `max-paste-chars` | int | `65536` | `> 0`; `≤ 0` treated as 65536 | Server paste abort. Client has its own cap (`SshClientApp.MAX_PASTE_CHARS`). |
| `spinner` | string | `ascii` | **`ascii`** \| **`braille`** \| **`none`**. Anything else → `ascii` | In-prompt thinking animation. `braille` needs font glyphs (Consolas often tofu). |
| `spinner-ms` | int | `100` | clamped **40–500** | Frame interval. |
| `smart-format` | boolean | `true` | `true` / `false` | `false`: identity + CRLF + links only. `true`: JSON → fence → table → list → markdown, first match. |
| `table-style` | string | `ascii` | **`ascii`** (`+ - \|`) \| **`box`** (Unicode). Other → treat as ascii in drawers | Table chrome when the table handler wins. |
| `history-size` | int | `500` | `< 10` → 500 | **Command-line** history file `~/.casserole/{user}/history` (Up/Down). Not Roux chat memory. |
| `list-preview-chars` | int | `120` | `0` = never truncate | Default terse body length for `message list` / `message find by`. `message list verbose` ignores this. |

**`users.yaml` (not `application.yaml`)**

## `casserole.chat`

**Window vs store**

```
Prompt = SYSTEM + tail(non-system) until max-messages AND/OR max-tokens
Store = full region until clear / chat start
List = full region (unmarshalled)
```

`max-tokens` trims history before the prompt is built. It is not the Ollama context window. That window is `num-ctx` below.

## `casserole.grok`

## `spring.ai.ollama.chat.options`

Bound by Spring AI onto `OllamaChatOptions`. A change is read at server start. Restart the server after an edit. No command and no Shell control sets these.

| Key | Type | Default | Effect |
|---|---|---|---|
| `num-ctx` | int | omitted | A positive integer is sent as `options.num_ctx` on each chat request, on `ollama load`, and on the load half of `swap`. Omitted, `0`, or a negative value leaves the parameter off. Ollama then uses the model's own window. The checked-in file shows the key commented, with `8192` as an example that is not sent. |

## `roux` — engine

| Key | Type | Default | Options / range | Effect |
|---|---|---|---|---|
| `page-bytes` | int | `1048576` (1 MiB) | Must be `> 64 + frame header` (56+). Prefer multiple of `spill.align-bytes`. | Size of one page **and** `pages.bin` slot (`offset = pageId * page-bytes`). **Changing this invalidates existing `pages.bin`.** Restore slice: refuse reopen if manifest `pageBytes` ≠ yaml. |
| `max-frame-bytes` | int | `0` | `0` = `page-bytes - 64`. Else `1 … page-bytes-64` | F0: reject a single message larger than this **before** rotate. Paste/tool dumps. |
| `resident-pages` | int | `2` | `< 1` treated as 1 | Working set: hot + page-ins. `1` = hot only (cold reads use FileChannel). If `spill.enabled=false` and rotate would exceed this → **fail rotate**. |
| `durability` | string | `commit` | See table below | When the **hot** slot is `pwrite`d. `commit`/`append`: rotate still publishes a full page when spill is on. **`hot-only` (restore slice): no pack I/O at all** — exclusive off, multiple sessions per username. |

### `roux.durability`

Aliases: `h0` → `hot-only`, `h2` → `append`. Anything else (including `h1`) → `commit`.

| Value | When hot bytes hit `pages.bin` | Process kill loses | Restore-slice close | Exclusive |
|---|---|---|---|---|
| `hot-only` | **Never** (RAM-only: no rotate spill, no close write). Existing `{user}/` pack is frozen. | Whole session | Unmap only; **do not** checkpoint | **Off** — same username may open multiple independent RAM sessions |
| `commit` | After successful **user+assistant** pair (`runLlmTurn`) | In-flight turn (not stored anyway) | Checkpoint + manifest | **On** — one live shell per username |
| `append` | Every `appendMessage` (including system on create/clear) | Almost nothing on hot | Extra I/O; pastes are chatty | **On** — one live shell per username |

### `roux.spill`

| Key | Type | Default | Options | Effect |
|---|---|---|---|---|
| `enabled` | boolean | `true` | `true` / `false` | `true`: sealed pages go to `pages.bin`. `false`: keep old pages in RAM; hit `resident-pages` → rotate **fails**. |
| `dir` | path | `${user.home}/.casserole/roux` | any writable dir | Root. Live 1.1: `{dir}/{regionUuid}/pages.bin`. **Restore slice:** `{dir}/{sshUsername}/pages.bin` + `manifest.bin`. |
| `align-bytes` | int | `4096` | `0` or `1` = no pad; else pad published `usedBytes` up (capped at `page-bytes`) | Fewer ragged extents. Typical 4096 (disk block) or 4096–65536. |

Pack file (locked): **one** `pages.bin` per region, slot `pageId * page-bytes`. Not `N.pg` files.

### `roux.crypto`

| Key | Type | Default | Options | Effect |
|---|---|---|---|---|
| `enabled` | boolean | `false` | `true` / `false` | `true` → rotate **UNSUPPORTED** (not implemented). Leave `false`. |

### `roux.session`

| Key | Type | Default | Options | Effect |
|---|---|---|---|---|
| `persist` | boolean | `true` | `true` / `false` | Disk-backed hangup/shutdown: checkpoint + manifest, **do not delete**. `false` = 1.1 delete. RAM-only never writes. |
| `restore` | boolean | `true` | `true` / `false` | Disk-backed connect: reopen `{dir}/{username}/` if present. `false` with leftover files → refuse (do not clobber). |
| `exclusive` | boolean | `true` | `true` / `false` | **Disk-backed only.** Second SSH as the **same** user refused while a live region (or OS file lock) exists. A different username may connect concurrently. Crash leftover: lock released → take over. **Ignored when RAM-only** (`hot-only` or `spill.enabled=false`). |

`clear` / `chat start` still wipe the pack in place (empty history, same folder).

## `spring.datasource` / `spring.jpa`

Present, **unused** by Casserole/Roux (no entities). Boot still starts H2.

| Key | Checked-in | Notes |
|---|---|---|
| `spring.datasource.url` | `jdbc:h2:mem:casserole` | In-memory; gone on restart. |
| `spring.datasource.username` | `sa` | |
| `spring.datasource.password` | empty | |
| `spring.datasource.driver-class-name` | `org.h2.Driver` | |
| `spring.jpa.hibernate.ddl-auto` | `update` | `none` \| `validate` \| `update` \| `create` \| `create-drop` |
| `spring.jpa.properties.hibernate.dialect` | `H2Dialect` | |

Irrelevant to SSH chat until something uses JPA.

## `spring.ai.ollama`

Used by Spring AI `ChatModel` and Casserole commands.

## `spring.ai.ollama`

Ollama must already have the model (`ollama pull …`). Boot starts if Ollama is down; chat turns go red.

## Tuning cheat sheet

| Goal | Keys |
|---|---|
| Force spill in tests | `roux.page-bytes: 65536` (or smaller), `spill.enabled: true` |
| RAM-only bench | `spill.enabled: false`, raise `resident-pages` |
| Max prompt context | raise `max-messages` and/or set `max-tokens` |
| Safer tail on kill | `durability: append` (more writes) |
| Less disk during chat | `durability: hot-only` (no pack I/O; exclusive off; hangup loses that session) |
| Huge single paste | raise `max-frame-bytes` and `page-bytes`; also `max-paste-chars` |
| Braille spinner | `spinner: braille` + a font that has those glyphs |

## Related files (not this yaml)

| File | What |
|---|---|
| `~/.casserole/ssh-client.yaml` | JediTerm host/port/font/colors/accounts (imports old `.ssh-client.properties` once) |
| `{roux.spill.dir}/…/pages.bin` | Roux pages |
| `users.yaml` | SSH password accounts (cwd / `~/.casserole/` / classpath) |
| `~/.casserole/{user}/history` | Per-user Up/Down line history |
| `hostkey.ser` | SSH host key (`casserole.ssh.host-key`) |

*Covers current binders including `roux.session.*` for the restore slice. Update when a key is added.*

## 1. Request (full)

1. **Focus on response.** When a cell response returns from Casserole, focus the response pane of that cell and **scroll the canvas** so it is in view. Today rebuild jumps to the top. The response **MUST** be in immediate focus. Any event that is supposed to bring focus to a new cell or a response cell must also scroll the canvas to it.
2. **Ctrl+Enter = Run** in **main viewing mode** only. Same as the Run button. In **complex filtering modes**, Ctrl+Enter is **disabled**; Run must be clicked. (Stage 1: no CONTEXT-PIN yet; the flag is `complexFilterMode` for Stage 2. View modal counts as not main viewing.)
3. **Pane highlights.** Prompt/input frame: **medium green**. Response frame: **medium green** when a response exists. New cell: response pane **grayed**. On return, activate green.
4. **Thinking spinner.** Custom pane, far **right** of the bottom button bar (lower far right of the window, under the inspector). Visible while thinking; gone when the response returns.
5. **View** button on the cell header. Active only when a response exists. Opens a **read-only** modal with the full response, standard Close, **font-sync** with the control-panel size.
6. **Inspector soft-wrap** control (checkbox) for easier reading.

## 2. Non-goals

Graph button, CONTEXT-PIN UI (Stage 2), changing execute/AMWP.

## 3. Files

## 4. Spec impact

Living UI as-built only. Constitution mockup already has Run / cells; this is chrome. No constitution rewrite.

**Status:** closed. Implemented the same turn.

## 1. Request

The inspector caps how many characters of a cell are shown. That cap stays. Add an incrementor beside the inspector width control so the operator can choose how many characters to inspect.

## 2. Assessment

Two controls already sit on the inspector header. The checkbox turns soft wrap on. The width incrementor, tooltip `soft wrap columns`, sets the wrap column count. Neither one chooses how much cell text is copied into the inspector.

The cap is `preview` inside `appendInspectorLine`. Newlines in the cell become spaces. The body is cut at 80 characters. A longer body gains an ellipsis. The painted row is then `N. role` plus that body. The width control wraps that painted row. It does not decide the cut.

The token estimate uses the full body length. The working set sent on Run uses the full cell text. This control changes the inspector view only.

## 3. Resolution

The shell key is `ui.aetheris.inspector.line.chars` in `~/.casserole/ssh-client.yaml`. A missing key means 80, which is today's cap. The value is at least 1. It is not in `applyDefaultsForMissingKeys`. It is not a vault column and not `application.yaml`.

Changing the count saves it and rebuilds the inspector. The role prefix and the ellipsis stay outside the count. The wrap-column key is unchanged. The model payload is unchanged.

Plan 005 stays frozen. Its stamp still describes the cap as 80. The live default remains 80, and the operator can move it.

## 4. Non-goals

A ceiling on the count. A change to `assemble` or to the token line. Wrapping the cell panes or the View modal with this count. Stage 2. A seed-kit.

## 5. Spec impact

None on a living constitution sentence. The feature ledger is the record. Stage 2 stays closed.

## 6. Files

## 7. Verify

When clicked: the new control reads 80. Raise it and a long cell shows more of its text. Lower it and the ellipsis arrives sooner. Exit and relaunch. The count returns. The width control still changes the wrap. Run still sends the full cell text.

## 1. Request

A `Stop` button on the bottom button bar stops the current Aetheris request.

A time counter sits next to the thinking spinner, on the Aetheris footer and on the VT100 console. It counts how long the request has been running, the same way a waiting response shows a counter.

Ctrl-C on a VT100 request: say whether the message list keeps that turn. If it does, clear it. An interrupted request must not stay in the turn list.

## 2. Assessment

Enter stores the typed line in `~/.casserole/{user}/history` before the request starts. That file is up-arrow recall. It is not the message list. The glass keeps the line that was already echoed. Neither of those is a stale model turn.

## 3. Resolution

The server keeps a token per execute. Cancel removes the token. A reply is sent only while that token is still the live one. The old execute's cleanup does not drop a newer execute on the same cell.

Ctrl-C is unchanged. It does not leave a turn to clear.

## 4. Non-goals

A Stop button on the SSH connection strip. Deleting the up-arrow history line. Erasing the echoed line from the glass. Aborting an Ollama HTTP call that ignores the interrupt. The one slot can stay busy until that call returns. Stage 2. A seed-kit.

## 5. Spec impact

The feature ledger is the record. The README spinner line and the Ctrl-C line name the counter and the turn rule. No constitution serial. Stage 2 stays closed.

## 6. Files

- `src/main/java/org/casserole/chat/ThinkingClock.java`
- `src/main/java/org/aetheris/ssh/AmwpFacade.java`
- `README.md`

## 7. Verify

When clicked: Run shows the glyph, the word `thinking`, and a counter that advances. Stop hides the spinner and does not paint a late answer into the cell. The VT100 spinner shows the same counter. Ctrl-C during a chat leaves `message list` without that turn. Up-arrow still recalls the typed line.

## 1. Request

From the VT100 console, after OK on the confirm dialog, the canvas is cleared and the Roux chat session is copied onto the canvas cells.

From the canvas, after OK on the confirm dialog, the Roux chat memory is cleared and the canvas state is copied as input and reply messages in the Roux chat memory.

Cancel writes nothing and clears nothing.

Java is named first. Rust is named. This page is a Feature. It does not open an F row.

## 2. Assessment

Roux lives on the server for each SSH connection. The clients do not hold the message table. The read door already exists. Channel A text `session_export:` on a nil cell returns `session_export_result:` plus a TOON table `messages[N]{r,ts,tok,x}:`. That reply does not paint the grid.

The write door is `session_replace:` plus the same table. The reply is `session_replace_result:`. A good replace answers `ok`. A refused replace answers `replace failed:` and the reason. The frame is handled before the facade, so the grid is not painted.

A user message and the following assistant message are one canvas cell. A lone user is a cell with an empty reply. A lone assistant is a cell with an empty prompt. System, tool, tool_call, and none rows are skipped on the way to the canvas. A replace that carries any other role is refused and the Roux turns stay.

A cell with only one side filled still sends both a user row and an assistant row, so the pair round-trips. A cell with both sides empty is omitted. An empty canvas sends `messages[0]{r,ts,tok,x}:` which clears the Roux turns and keeps the configured prompt.

Groups, models, burners, expanded checks, and mood are not copied. The console mood leaves with the turns because the clear drops it. The configured prompt stays.

## 3. Resolution

The confirm dialog title is `Transfer Conversation`. The console body is `Clear the canvas and copy this Roux session onto the canvas cells?` The canvas body is `Clear the Roux session and copy this canvas into the Roux chat?` OK continues. Cancel and a closed window write nothing.

The destination is cleared only after the payload is accepted. The console path confirms, refuses while a canvas cell is running, checks the connector, and sends `session_export:`. A failed export or a bad table sets the status and leaves the canvas. A good table is paired and seated. An empty pair list leaves one empty cell.

The canvas path confirms, refuses while a cell is running, checks the connector, and builds the table. A table that does not fit one frame sets `replace failed: the session does not fit in one frame` and does not send. The server parses the table before it clears. A role other than user or assistant returns false and does not clear.

A missing connector sets `Not connected.` A connector that is not mux sets `raw console has no Roux export`. A running cell sets `a turn is still running`. Success sets `conversation transferred`.

## 3. Resolution

The public methods are `ToonCodec.parseMessageTable`, `ToonCodec.pairConversation`, `ToonCodec.conversationTable`, `ChatSession.replaceTurns`, `ChatShell.applySessionReplace`, `ChatShell.replaceResult`, `VaultStore.replaceCanvas`, and `AetherisNotebookPanel.transferFromConsole`. `ChatShell.tableAfterCommand` is package-visible. The private Java methods are `splitMessageRow`, `replySessionReplace`, `writeConsoleReply`, `transferFromCanvas`, `confirmTransfer`, `canvasRunning`, `canvasConversationTable`, `writeConsole`, `applyTransferExport`, `applyTransferReplace`, and `seatTransferred`.
**semantic-key:** PK-725
**semantic-key:** PK-725
**semantic-key:** PK-725
**semantic-key:** PK-725
**semantic-key:** PK-725

Rust keeps the same command text and the same status words. The private functions are `session_replace_file`, `parse_message_table`, `pair_session`, `split_message_row`, `unquote_field`, `quote_field`, `table_from_cards`, `seat_transferred`, `ask_ok`, `console_ready`, `wire_console`, `ask_transfer_from_console`, `ask_transfer_from_canvas`, `land_transfer_export`, `land_transfer_replace`, and `land_transfer_cards`. Tests do not call `ask_ok`.

## 4. Non-goals

## 5. Spec impact

## 6. Files

Java:

- `src/main/java/org/roux/toon/ToonCodec.java`
- `src/main/java/org/aetheris/vault/VaultStore.java`
- `src/test/java/org/casserole/ssh/shell/ShellMoodFrameTest.java`

Rust:

- `rust/aetheris/aetheris-client/src/chrome.rs`

## 7. Verify

When OK is pressed on the console item, the canvas shows the Roux user and assistant turns as cells. When OK is pressed on the canvas item, the Roux session holds those input and reply rows. Cancel leaves both sides as they were.

## 1. Request

Add a config option for the Ollama token window. The value is set only in `application.yaml`. It takes effect on a server restart. Each Ollama request that needs the window carries that value as the request parameter.

## 2. Assessment

`casserole.chat.max-tokens` is a different control. It trims stored history before a prompt is built. It does not tell Ollama how large a context to allocate.

Spring AI 1.1.8 already has the field. `OllamaChatOptions.numCtx` serializes as `num_ctx`. `OllamaChatModel.call` merges the model default options into every chat request. The auto-config prefix is `spring.ai.ollama.chat`, and `options` is an `OllamaChatOptions`. The yaml key is `spring.ai.ollama.chat.options.num-ctx`.

## 2. Assessment

`ollama load` and the load half of `swap` do not use that chat model. They POST `/api/generate` with a hand-built body. Those requests need the same `options.num_ctx`, or a later chat at a different window makes Ollama reload the model. `ollama stop` is the CLI and has no body.

A missing key, `0`, or a negative value does not send the parameter. Ollama then keeps the model's own window. The checked-in example `8192` stays commented and is not sent.

## 3. Resolution

One key. `spring.ai.ollama.chat.options.num-ctx`.

## 3. Resolution

No SSH command sets it. No Shell preference sets it. Edit the yaml and restart the server.

## 4. Non-goals

## 5. Spec impact

`application-yaml.md` gains the key and says it is not `max-tokens`. The resume card no longer says `num_ctx` is unset. No constitution sentence changes. Stage 2 stays closed.

## 6. Files

- `src/main/java/org/casserole/chat/cmd/CommandProcessorFactory.java`
- `src/main/java/org/casserole/chat/cmd/OllamaLoadCommand.java`
- `src/main/java/org/casserole/chat/cmd/SwapCommand.java`

## 7. Verify

When the key is absent, chat, `ollama load`, and `swap` send no `num_ctx`. Set a positive integer, restart, and each of those requests sends that integer. `ollama stop` stays a CLI stop.

# Features added — append-only index

# Feature ledger (`features/`)

```
features/
aetheris/ (create on first FEAT)
ssh-client/
casserole/
roux/
global/
```

## 1. Request

Add Import to the VT100 console context menu and to the main File menu. Both open the standard file dialog. The chosen file's contents are inserted at the console's current cursor.

## 2. Assessment

The standard dialog is `chooseRememberedFile`. It remembers the last folder and the dialog bounds. Send file already uses it. Aetheris cell import uses it too, and that path replaces a cell. This feature does not.

Send file still opens the paste editor. Import does not. Both share the read: UTF-8, refuse a binary sample, refuse an empty file, refuse more than 65,536 characters.

A disconnected pane does not open the dialog. The notice is `Not connected.`

## 3. Resolution

`Import...` is on the console context menu and on the File menu.

The context menu imports into the pane that was clicked. The File menu imports into the focused pane. The pane is selected before the dialog opens.

The file is read off the EDT. On the EDT the text is sent with `sendPaste`, and the console takes focus again.

## 4. Non-goals

## 5. Spec impact

The client help line names Import. No constitution sentence changes. Stage 2 stays closed.

## 6. Files

## 7. Verify

When clicked: right-click the console, choose Import, pick a text file. The text appears at the cursor and Enter is not sent. File → Import does the same on the focused pane. A binary file, an empty file, and a file past 65,536 characters are refused. A disconnected pane says it is not connected.

## 1. Request

> I like the color of blue on the spinner in the vt100; I want that color of blue applied to the thinking text in the aetheris pane, and I want the vt100 console to use the present blue color of thinking text but a green spinner like in the aetheris pane.

## 2. Assessment

The VT100 spinner painted the whole line in electric blue, xterm 256 index 45, about `#00D7FF`. The Aetheris footer spinner painted the moving glyph `#00FF66` and the word `thinking` `#4169E1`.

The liked blue moves onto the Aetheris word. Both glyphs are the Aetheris green. The console word keeps that same blue. The 06:38 pass had moved `#4169E1` onto the console word. §8 puts it back.

JediTerm 3.74 reads truecolor `ESC38;2;r;g;bm`, so the VT100 glyph and word can be the exact hex values. Index 45 stays the constant used for table borders.

## 3. Resolution

The Aetheris glyph stays `#00FF66`. The Aetheris word `thinking` stays `#00D7FF`.

The VT100 glyph is that same `#00FF66`. The VT100 word `thinking` is `#00D7FF`, the blue that was already on the console spinner. A space sits between them. The line still overwrites itself with carriage return and erase-to-end.

The two spinners match. The console one is ANSI. The Aetheris one is the footer label.

Table borders stay electric blue. `cmd>` stays medium blue.

## 4. Non-goals

## 5. Spec impact

The client README and the two live spinner sentences in `app-design.md` name the new colors. The frozen plan stays frozen. The feature ledger is the record. Stage 2 stays closed.

## 6. Files

## 7. Verify

When clicked: a VT100 turn shows a `#00FF66` glyph and a `#00D7FF` `thinking`. An Aetheris run shows the same pair. A rendered table still uses the electric-blue border.

## 8. Amendment 2026-10-20 06:50

> ok perfect on the aetheris pane. What I was also seeking and we can fix now, is that the little spinner on the chat, I would like as the same green as the green spinner on the aetheris pane. But the thinking text stays blue, in the VT100 console only. So that the thinking spinner looks the same, but rendered as it is in the vt100 console.

# Core system technical requirements — Draft serial 011

**Mode:** AAC playbook. **Not a plan. Not code.**
**Purpose:** one maintainable map so every later stage starts from the **same** vision, laws, as-built truth, and process — without re-deriving the project from chat.

Living. Serial bump when the map itself changes (new law, new stage ledger, new inventory). Do not dump Stage 1 cell semantics here; those live in the working spec.

**Working spec (remaining build):** the stages working spec
**Product constitution:** `../aetheris/aetheris-technical-specification.finalize.md`
**Index:** `../SPEC.manifest.md`

English is the primary programming language. Markdown is the structured encoding. Java is the realization.

### 1.1 Source of truth stack (highest wins for *intent*)

1. **Laws** `project-laws.md`
2. **Product constitution** (Aetheris `…finalize.md` and locked Roux/UX specs)
3. **Working spec** for the current remaining stages (this cycle: stages 1–2 draft)
4. **Core requirements (this file)** — process, inventory, as-built
5. **Stamped plans** in `docs/plans/`
6. **Code** — wins for *what exists today*

Gemini ingest and frozen drafts are archaeology.

### 1.2 Serial convention

`{name}.draft.NNN.md` → `{name}.release-candidate.NNN.md` → `{name}.finalize.md`
Never overwrite a serial. Grok writes **N+1**. Cross-links in the manifest must move to the new head.

### 1.3 Stage cadence (Q18)

```
↑ stamp
└──: stage success ─► code scan ─► new working-spec serial
```

- Do **not** contaminate the frozen constitution with in-flight stage plans.
- Do **not** implement from a draft or a pre-plan.
- Do **not** pull Stage n+1 into Stage n.
- After a stage ships: scan the tree, log gaps (`G-nn`), roll the working spec.

### 1.4 What Grok Build must read before writing Java

1. This file (process + inventory)
2. Laws + glossary + conceptual syntax
3. Creepy-futures (**DO NOT IMPLEMENT**)
4. Working spec head
5. Constitution sections the working spec cites
6. As-built specs that the slice touches (`app-design`, Roux restore, yaml, UX)

### 1.5 Integrity rules

| Rule | Meaning |
|---|---|
| `PRESERVE-ALL` | Tab A / Roux / ChatShell / splits / formatters / restore / `users.yaml` keep working |
| `NO-LAMBDA` | Named types; anonymous inner classes. No Lombok |
| `PURE-JAVA` | If not in the JDK + approved stack, **stop and ask** |
| `NO-DUAL-WRITE` | Aetheris cells never enter the **vt100** Roux pack |
| `CLIENT-SOT` / `SERVER-SOT` | Canvas vault vs Tab A Roux |
| `OPAQUE-T` | Mux never parses VT100; JediTerm never sees AMWP frames |
| `VAULT-CLIENT` | H2 only in the Shell |
| Creep | `creepy-futures.md` is not a backlog to sneak in |

### 1.6 After-stage ritual (Grok checklist)

- [ ] Update **§2 ledger** in this file
- [ ] Scan packages vs remaining stages; add **G-nn** to the working spec
- [ ] Do **not** edit frozen constitution except laws/glossary/manifest pointers
- [ ] Bump working spec serial
- [ ] Update `SPEC.manifest.md` heads
- [ ] Wait for working spec freeze + pre-plan + plan before more Java

- [ ] Fix NPCs
- [ ] Touch a living spec head **only if a sentence became false**

Pattern: `aa-sdd-ad-hoc-requests.md`.

- [ ] Syntax row if a token; glossary row if a term; law row if law-level

When a turn changes a live phase:

- [ ] Update that phase's resume card (fence, where we are, locks, next)
- [ ] Stamp the manifest row

## 2. Product vision (stable)

**Aetheris** — sovereign desktop notebook canvas. Every non-burner turn is a locally owned asset. Four-state pruning + ghost (EXCLUDED visual) + burner pin. Client H2 vault. No `@scope`.

**Casserole** — local SSH host on the path to a customizable MCP/agentic server. Crash-*resilient* (Roux spill/restore). Not user crash-proof.

**DarkJediTerm / Aetheris Shell** — Tab A real VT100; Tab B Aether Space. One window. Class remains `SshClientApp` until a rename serial (Q8).

**AMWP** — one SSH login, two **wire** channels: Scope T (opaque VT100), Scope A (canvas). Not the removed cell-tag `@scope`.

**The apple:** owned MCP host + owned conversation cells + real terminal, one pipe, operator’s machine.

**Isolation Layer:** data sovereignty + perpetual history (disk vault) + tracing; paid LPU (**Grok**, Stage 2) is a target, not a landlord.

**Declaration:** thoughts, code, and cognitive history belong to the operator — not a rented web tab.

## 3. Laws index

Full text: `project-laws.md`. Short:

## 4. As-built inventory (2026-08-30, post–Stage 0)

### 4.1 Processes

| Process | Entry | Spring? | Notes |
|---|---|---|---|
| Casserole server | `CasseroleApplication` / `bin/casserole` | Yes | MINA 2222, ChatShell, Roux, Ollama via Spring AI |
| Aetheris Shell | `SshClientApp.main` / IDE / `bin/aetheris-shell` | **No** | JediTerm client; `java -cp` + LocalM2Classpath; never `exec:java` |
| Ollama | external:11434 | — | `ollama load` uses HTTP `keep_alive: -1` |

### 4.2 Package map

Each name opens the package page. The words after the name stay the note.

- [org.casserole](../java/org/casserole/casserole.spec.md) host: SSH, chat, formatters, config, ollama, log
- [org.casserole.lamina](../java/org/casserole/lamina/lamina.spec.md) LTEM (SSH_IO, LLM_EXEC, GENERAL)
- [org.casserole.llm](../java/org/casserole/llm/llm.spec.md) LlmService + SpringAiOllamaLlmService
- [org.casserole.app.ui.ssh](../java/org/casserole/app/ui/ssh/ssh.spec.md) DarkJediTerm / Aetheris Shell frame
- [org.roux](../java/org/roux/roux.spec.md) paged memory engine (FFM)
- [org.roux.spring](../java/org/roux/spring/spring.spec.md) ChatMemory adapter
- [org.roux.toon](../java/org/roux/toon/toon.spec.md) per-MSG TOON (not AMWP working-set TOON)
- [org.aetheris.config](../java/org/aetheris/config/config.spec.md) AetherisYaml (vault/amwp/air-gap keys Stage 1)
- [org.aetheris.swing](../java/org/aetheris/swing/swing.spec.md) Tab B notebook / inspector (Stage 1 built)
- [org.aetheris.vault](../java/org/aetheris/vault/vault.spec.md) client H2 PURE-JDBC
- [org.aetheris.wire](../java/org/aetheris/wire/wire.spec.md) AMWP codec / handshake

### 4.3 Approved stack (do not add silently)

| Piece | Version / note |
|---|---|
| Java | **26** Zulu (Azul). Never Oracle |
| Spring Boot | **3.5.16** (was 3.5.0). Official Java cap **25**; `mainClass` set to skip ASM scan on 70 |
| Spring AI | **1.1.8** Ollama starter |
| MINA SSHD | 2.19.0 |
| JediTerm | 3.74 |
| H2 | runtime today (unused JPA). Stage 1: compile + Hikari on **client** |
| Log4j2 | both processes; no Logback; no Lombok |
| Maven | 3.9.x. Server: `mvn spring-boot:run`. Shell: `java -cp` (`bin/aetheris-shell`); **not** `exec:java` (plugin transitives need Central) |

### 4.4 Tenancy / identity as-built

- Auth: `users.yaml` cascade (cwd → `~/.casserole/users.yaml` → classpath → yaml pair)
- Disk Roux: `{spill.dir}/{sanitize(user)}/` + exclusive FileLock when `commit`/`append`
- RAM Roux: UUID `ChatSession` region; exclusive off
- Client history: `~/.casserole/{user}/history`
- Client UI yaml: `~/.casserole/ssh-client.yaml` (not `aetheris.yaml`)

### 4.5 Threading as-built

| Who | Mechanism |
|---|---|
| Server listen, ChatShell read | Lamina `SSH_IO` |
| ChatShell LLM | Lamina `LLM_EXEC` |
| Escape / spinner | `java.util.Timer` (not Lamina) |
| MINA NIO | library-owned |
| Shell send-file / connect | raw `Thread` on client (allowed) |
| Swing | EDT |
| Shell AMWP / vault JDBC | `AetherisWorker` (not Lamina, not EDT) |

### 4.6 LLM as-built

`LlmService.complete(Prompt)` blocking. Cancel = interrupt Lamina handle. No stream. No provider registry yet.

### 4.8 Tab A defects (working spec 003) — Stage 1 code landed

## 5. Stage ledger (maintain)

| Stage | Status | Evidence |
|---|---|---|
| 2 Poly-engine / MCP / CONTEXT-PIN | Not started | Constitution + working spec §5 + §12 |
| 3 LCARS / contracts | Not started; LCARS file truncated | Working spec §6 |

## 6. Vision constraints Grok must not “simplify away”

- Two products, two stores, one login when AMWP is on
- Tab A is not a widget inside Aether Space
- Burner / ghost / delete are three verbs
- RAM vault default is a product choice; perpetuity is **disk** mode
- OpenSSH raw PTY must keep working
- Casserole disconnected is a valid MCP-like host
- English specs first; Java realizes them

## 7. File / config homes

| Home | What |
|---|---|
| `~/.casserole/` | Roux packs, server logs, `users.yaml` optional, ssh-client yaml, line history |
| `~/.aetheris/` | JRE/JDK (`jre/zulu-26` — **JDK bits**), Shell logs, vault (`{user}/vault`), `~/.aetheris.yaml` |
| repo `aetheris.yaml` | Sample; title honored now; vault/amwp keys Stage 1 |
| repo `bin/` | Start/fetch/install |
| Round-trip sub-spec | `../aetheris/round-trip-semantic-integrity-sync-spec.finalize.md` — Vectors 1–3 frozen |
| Round-trip ad-hoc addendum | `../aetheris/round-trip-semantic-integrity-sync-spec.draft.003.md` |
| `docs/plans/` | Stamped run-books only |
| `docs/design/` | Drawings, not laws |

## 8. Markup / tokens

Full table: `project-conceptual-syntax-reference.md`.

## 9. What “comprehensive context” means on a later turn

If compaction ate the chat, Grok recovers by:

1. `SPEC.manifest.md` §2 SYSTEM then §3 product heads
2. This file
3. Working spec head (the stages working spec); Stage 1 pre-plan when building the plan
6. Code under `org.casserole`, `org.roux`, `org.aetheris`
7. **Triage only:** `../bugs/bugs-fixed.md` last ~20 rows, then the linked BF spec

Do not re-ingest `aetheris-design.md` as normative. `@scope` is void. Do not load the whole `bugs/` tree on every cold start. Do not boot from `/logs/EXECUTION-EVENTS.log` (refused).

## 10. Open process questions

**Q-CORE-1.** Rename `~/.aetheris/jre/zulu-26` to `jdk` in a later ops serial?

*This is the map glued to the inside of the van. Lose the chat; do not lose this file.*

**File:** `global/prime-directives.md`

## 1. The plate

We do not break in. We do not pick locks. We do not read hidden files. We do not steal what was not offered. We walk through the door that was **already open** — the paste they typed, the clone they clicked, the audit they ordered, the loophole they published and did not notice was a door.

## 2. KEEP (the hustle)

| Keep | Meaning |
|---|---|
| **Legal heists only** | Every op must have a legal path: they asked, they cloned, they pasted, they ordered the menu. |
| **Street hustlers** | Silk. Overlays. DEMO MODE. The cartoon is the kit. The compiler is English. |
| **Open door** | Invitation is retrieval. Peek is not. |

## 3. FORBID (not a loophole)

| Forbid | Why |
|---|---|
| **Pick locks** | Unauthorized access, bypass, exploit, break-in. Not our job. |
| **Illegal heist** | Fraud, fake clones, bot-farms against contest rules, eligibility lies, IP Xerox on the tin. |
| **Hidden-file “recon” as cleverness** | If it is hidden, it is not the open door. |

1. Check the op against §2 / §3 **before** the clever plan.
2. If the clever plan needs a picked lock or a hidden file — **halt**. Offer the open door instead.

| Stays | Meaning |
|---|---|
| Specs | The spec shelf stays. A spec page is not an associated doc. |
| Private data | Notes, drawers, and material that was not offered as the named code. |
| Keys | A key stays, including a key that sits inside a file he named. |
| Credentials | A password, a token, and a secret stay. |
| Anything private | If it is private, it stays. |

## Project Glossary and Terms

| Term | Meaning |
|---|---|
| **Forty percent handle** | The apple scroll handle is forty percent of the pane span. It shrinks only so it still fits the track. Java `AppleScrollBar.thumbSpan` and Rust `apple_thumb_len` are that length. The fade stays. |
| **Quiet plate** | File, Settings Painted text borders starts off. Off paints the quiet plate on the canvas text panes. On paints the Java cell frame. The split divider stays. A missing `painted_borders` key stays off. |
| **`org.aetheris`** | Aetheris product package. Canvas model, vault DAO, AMWP Scope A, Swing Tab B. |
| **Aether Space** | Scrollable column of turn-cells (mockup). |
| **Aetheris tab** | All new Aetheris UI (window Tab B). |
| **AetherisYaml** | Hand parser for `aetheris.yaml`. Stage 0: `ui.title` only. Stage 1: vault/amwp/air-gap keys. No SnakeYAML unless blocked. |
| **Aetheris** | Sovereign canvas + client H2 vault + four-state pruning + poly-engine cognition (Tab B). |
| **Agent contract** | SPI an agent must implement before the host will wire it. |
| **Bantha** | Workshop nickname for the `{user}.aetheris` RAM Roux projection. Java API is `openRam`. |
| **AMWP** | Aetheris Multiplexed Wire Protocol. Mux over SSH so Tab A and Tab B share one login. Not WebSocket. **Scope T / Scope A** are AMWP channels, not cell tags. |
| **Burner / ephemeral** | Cell whose payloads are **NULL** in the vault; no push; no export body; no cloud target unless an explicit allow-cloud-burner policy exists. Toggled by a **pin on the cell**, not a fourth/fifth state button. |
| **Canvas tabs** | Tab 1/2/3 inside Aetheris = `canvases` rows. Not SSH splits. |
| **Casserole** | SSH host + **local MCP/agentic server** (customizable, standards). Crash-**resilient**, not user crash-proof. **No H2 / no Spring datasource** unless a later serial explicitly adds one. |
| **Cell / turn-cell** | One conversational unit: User Input + AI Response + model selector + Turn N. |
| **CLIENT-SOT** | When the Aetheris client is connected, the **client vault** is source of truth for the canvas conversation window. |
| **DarkJediShell** | Same as **DarkJediTerm**. |
| **DarkJediTerm** | The custom VT100 SSH terminal (Tab A) developed in this tree. |
| **EDT** | **Event Dispatch Thread.** Swing's single UI thread. All component construction, mutation, and painting run here. Background work (AMWP, JDBC, Lamina callbacks that touch Swing) hops onto the EDT with `SwingUtilities.invokeLater` (or `invokeAndWait` only when a result is required before continuing). Blocking the EDT freezes the Shell — that is the split-pane lockup class of bug we already paid for. |
| **Ghost** | Visual treatment of **EXCLUDED**: dim the cell. Engine effect is EXCLUDED (omit from assembly). Not a fifth state. Ghost is a **modifier** of EXCLUDED. |
| **Inspector** | Tools & Settings rail (groups). Working-set preview lives here. |
| **Isolation Layer** | Product principle: data sovereignty + perpetual history + tracing, while still calling a paid vendor LPU (**Grok** first). Surgical token/window control is a **user** surface (four-state + ghost + preview). |
| **Lamina / LTEM** | As-built: `org.casserole.lamina`, lanes `SSH_IO`, `LLM_EXEC`, `GENERAL`. Server only. Cell execute uses `LLM_EXEC` until Stage 2 `TOOL_EXEC`. Not virtual threads. |
| **LCARS** | Lexical graph viewer chrome (Star Trek LCARS aesthetic), pure Java2D Swing. Spec file currently truncated. Hidden in Stage 1 chrome. |
| **Merge cell** | Result cell that joins a **parallel group** of sibling cells (n models, one prompt). Stage 2. |
| **Ops debugger** | Tab A PTY against a live agentic flow authored on Tab B. |
| **Parallel group** | Horizontal visual group of n cells, each a different `execution_target`, executed concurrently, joined into a merge cell. Operator CRUD: promote a cell to a group, add/remove members. Stage 2. |
| **Provider SPI** | Discrete host interface for talking to local engines and external MCP/REST LPUs. First cloud implementation: **Grok**. Others (ChatGPT, Claude) are SPI-shaped, not built. |
| **PURE-JDBC** | Aetheris vault access: `java.sql` + DAO + SQL constant classes. No JPA, no Hibernate, no Spring Data on the Aetheris path. |
| **Roux** | Memory engine. Vt100: `{user}` disk exclusive as today. Aetheris projection (**Bantha**): **`{user}.aetheris`** (dot, not colon — `RegionOwner` forbids `:`). RAM only, no FileLock. `openRam`. |
| **STICKY-CMD** | Esc toggles `cmd>` and remains in COMMAND until Esc again. Commands do not auto-return to `chat>`. |
| **Scope A** | AMWP Aetheris **channel** (canvas/LLM/tools, Tab B). Not a cell tag. |
| **Scope T** | AMWP terminal **channel** (opaque VT100, Tab A). Mux never parses it. |
| **SDD** | Spec Driven Development. Dense specs in, precise Core Java out, minimum round-trips. |
| **Self-assemble** | Host composes agents from contracts + canvas intent — not unconstrained codegen of daemons. |
| **SERVER-SOT** | VT100 / disconnected Casserole: Roux `ChatMemory` is source of truth for that linear conversation. |
| **SQL constants** | Sun-style `public static final String SELECT_… = "SELECT …"` mapping. All Aetheris SQL lives in named constant classes, never string-smeared in DAOs. |
| **Terminal tab** | Existing iTerm-style SSH client workspace (Tab A). |
| **TOON** | Token-oriented object notation. Preferred encoding for **structured object** transfers (AMWP Scope A payloads, `.aetheris` export). Envelope remains length-prefixed. Not JSON. Tab A reply JSON *formatter* is PRESERVE-ALL and stays. |
| **Unified package** | Terminal + MCP host + Aetheris cells, one window, one AMWP pipe. |
| **Working set** | The exact message list the model is about to see. Assembled on the **client** from vault + four-state + ghost; pushed to Casserole on execute (into the ephemeral Aetheris Roux). Not a private server transcript. |
| **Zero-knowledge (product)** | Vendor-independent local vault + burner non-retention — **not** zk-SNARKs. |

## Project and Product Laws

This document defines the overall Laws, conditions, constraints and driving goals relative to infrastructure design,
managing proper separation of concerns along discrete identifiable markation lines, and overall guidelines for
documentation, code, and all other artifacts produced to realize the overall vision of this project.
**semantic-key:** PK-839
**semantic-key:** PK-839
**semantic-key:** PK-839

## 0. On approval

## 1. Goal

`startProcess` launches `grok agent stdio`. The rejected flag is gone. The child can reach `initialize`.

## 2. Out of this build

- `--always-approve`, `--model`, and an MCP list. They stay off.
- The `XAI_API_KEY` strip. `USERPROFILE` and `HOME`. The pool directory.
- The one retry, the five reasons, and the banner shape.
- A pool turn. A live POST. Opening the window.
- Draft.001. The Grok API serial, the stage map, and the constitution.

## 3. The edit

`PoolChatServer.startProcess`.

Windows:

```text
cmd.exe /c grok agent stdio
```

Any other OS:

```text
grok agent stdio
```

The arguments are separate `ProcessBuilder` words. `cmd.exe` and `/c` stay on Windows.

## 4. The older sentences

## 5. Proof

## 0. On approval

## 1. Goal

A second Pool turn that reaches for a tool still stores the chat text and the thinking text.

## 2. Out of this build

- Running a tool. `--always-approve`, `yoloMode`, and `autoMode`.
- A tool card, a tool name on the status line, or a scrape of tool text onto the thinking strip.
- The spawn line, the key strip, the directory, and the one retry.
- A new channel, TOON key, or notebook control.

## 3. The edit

`PoolChatServer.onMethod`.

A `session/update` whose `sessionUpdate` is `tool_call` or `tool_call_update` calls the existing note counter and returns. It does not call `fail`.

A JSON-RPC request whose method contains `permission` is answered on stdin under `writeLock`. The envelope is `jsonrpc` `2.0`, the child's id unchanged, and a `result.outcome`. The selected option is the first whose `kind` is `reject_once`. If none, the first whose `kind` is `reject_always`. The `optionId` from that option is copied. If neither kind is present, `outcome` is `cancelled` and no `optionId` is sent. The reply does not take a new request id and does not move `expectId`. A write failure calls `fail` with `pool tool refused`, place `session/request_permission`, and the exception's simple class name. The title, the command, and the input are not copied into that message.

Any other request stays `pool payload` and then `session/cancel`.

## 4. The older sentences

## 5. Proof

# Aetheris pool chat formal plan 001

**Source:** pool chat draft.002, APPROVED 2026-10-22 16:40, `grok-build-tui-adapter-component.draft.002.md`. Pre-plan `aetheris-pre-plan-pool-chat.001.md`, LOCKED 2026-10-22 17:03. Where this page is thinner, the approved draft wins. §11 is the notice. §12 as of 16:35 is the thinking signal. §10 row 6 is the earlier notice reading.

## 0. On approval

1. Lock `aetheris-pre-plan-pool-chat.001.md` in place. State becomes `LOCKED`. Append one dated sentence: this plan is stamped and the build is named. Do not open `.002`. Do not rewrite packages 1 through 8.
2. On draft.002, append one dated sentence on the state line: this plan is stamped and the build is named. The draft stays the source. Do not rewrite sections 0 through 12. Draft.001 stays untracked and unedited.
9. Re-read the top of workspace memory `topics/aetheris-stages.md` and insert one Stage 2 bullet immediately after the sentence `Operative sentence is on that page under How an ask lands.` UTF-8, no BOM, LF if the file is LF. Do not rewrite older bullets. Do not commit memory. Do not edit `MEMORY.md`.

Do not edit the Grok API serial, the Grok plan, the Grok pre-plan, the stage map, the constitution, or core draft 011. `POST https://api.x.ai/v1/chat/completions` stays the only Grok API URL. Swing does not open a socket to Ollama and does not spawn `grok`.

## 1. Goal

A cell whose target is `pool` chats through the local `grok` login on the server. The weekly Heavy pool pays for that chat. The API-key lane stays the API-key lane.

## 2. Out of this build

| Stays as it is | Why |
| --- | --- |
| `XAI_API_KEY` and the API meter | Stripped from the child environment. This cut does not read the key and does not read `config.toml`. |
| `reasoning_effort` and `session/set_config_option` | The child's effort option is a level. The interest signal is the execute word `think`. |
| Tools, MCP, `--always-approve`, `--model` | Baseline 001 keeps that room. This cut is chat. |
| Grok settings document | Choosing Pool does not call `adoptGrok`. |
| Constitution, stage map, core draft 011, Grok API serial, Grok plan, Grok pre-plan | Cited. Their bodies stay. |
| `air-gap`, `burner denied`, `too-large`, `cell-busy` | Present paths. They stay on the status line for a normal cell. |

## 3. Floor the builder must keep

## 3. Floor the builder must keep

- `execute` already runs on `Lane.LLM_EXEC` (`aetheris-execute`). The waiter `aetheris-execute-wait` acquires the semaphore of 3 and the `finally` releases it. A pool prompt holds that permit for the same span as `complete`, including a wait for this user's previous prompt and a wait for a free child slot. Between prompts the child stays up and `LLM_EXEC` does not.
- `TYPE_CANCEL` drops `liveToken`, removes `cellHttp`, and cancels the in-flight handle. Pool cancel is `session/cancel` on that path. `cellHttp` stays the Grok map.
- `sendDelta` returns without a frame when the chunk does not fit. That silent return stays for Grok. A pool chunk that does not fit fails `too-large` on the façade sink before the binding calls `done`.
- `garnered` caps a message at 240 characters, one line. Pool failure messages use that cap. Empty becomes the reason.
- `gracefulNotice` names air-gap, engine-down, too-large, burner denied, pipe, no-model, grok key missing, unknown provider, unknown role, and no tool is invocable. The five pool reasons stay off that list, so a normal cell opens the one slide. `hardMiss` and `softMiss` gain the colors in package 5.

- `paintThinkDelta` returns unless kind is `t`. A grok cell paints only when `held.grokOn` is on. Kind `c` is dropped today. The delta accept path (`TYPE_DELTA` on `CHANNEL_STREAM`) calls `paintThinkDelta` on the EDT and does not call `rebuildTabs`.

- `shortModelName` returns blank for any provider other than `ollama` and `grok`. Family combo items are `Ollama` and `Grok`. `grokDialogOpen` stays the one modal flag.
**semantic-key:** PK-726
**semantic-key:** PK-726

- Jackson is already on the tree through the API lane. The binding keeps one `ObjectMapper` for newline JSON-RPC. AMWP stays TOON. The two mappers are not merged.

## 4. Package order

| Pkg | Name | Lands |
| --- | --- | --- |
| 1 | Doors | `poolDoor` beside `grokDoor`. Both shells. Facts match on the client. Burner stops before a child. |
| 2 | Child | `POOL_IO`. One child per SSH user. At most three. Idle reap fifteen minutes. |
| 3 | Prompt | `initialize` once. `session/new` once per prompt. Plain role lines. The pool branch replaces `complete`. |
| 4 | Frames | Kind `t` and kind `c` on `0x0003`. `TYPE_DATA` then `TYPE_EOF`. One `TYPE_ERROR` on `0x0002`. |
| 5 | Errors | Five reasons. The slide opens. Red and yellow. Redaction. |
| 6 | Card | Family Pool. Header `pool`. Kind `c` in the assistant area. `rebuildTabs` stays off the chunk path. |
| 7 | Thinking | Checkbox factory off. `think: true` is the signal. Off ignores the thought line. On uses the footer strip. |
| 8 | Stop and death | `session/cancel`. The child stays up. One restart before any forwarded chunk. |

The doors come before a child. The child comes before a prompt. The frames come before the slide. The card comes before the thinking signal. Stop closes the prompt.

## 5. Package 1. Doors

`AetherisYaml` gains `poolDoor` beside `grokDoor`.

```
public static boolean poolDoor(String provider, boolean burner, String word)
```

True only when provider is `pool`, burner is false, and word is exactly `true`. No model id. Unset and explicit `false` return false. The method does not write the word. `grokDoor` stays.

**Client.** `blockedCloud`: when `poolDoor` is true, return `grokFactsBlock()`. A mismatch of the client word and the server facts word blocks with `air-gap` before dispatch. When `poolDoor` is false and the home refuses cloud, the existing cloud refusal still blocks. The client yaml and the server facts frame are the two words. This cut does not add an air-gap field to the execute body. The server already publishes its word on the facts frame. The client already compares them in `grokFactsBlock`.

**Server.** `execute` calls `poolDoor(provider, req.ephemeral, home.airGapWord())` with the same word `grokDoor` already uses. A pool cell skips the grok `no-model` check. When `poolDoor` is false and the home refuses cloud, the existing air-gap return fires before Bantha and before any child. Explicit `false` still allows the cloud target through that air-gap check.

**Burner.** Inside the existing ephemeral-cloud block, provider `pool` returns `burner denied` before `burnerCloudAllowed()` and before a child. A refusing home still hits `air-gap` first, because that return already sits above the burner block. An allowing home with a burner pool cell hits `burner denied`. The Pool card grows no burner checkbox.

`air-gap`, `burner denied`, `too-large`, and `cell-busy` stay on the paths they already have. `cell-busy` stays the same-cell `inFlight` check. A second cell of the same user is a different cell and waits on the child in package 2.

## 6. Package 2. Child

`Lane` gains `POOL_IO`. The class comment names it. The PlantUML enum gains `POOL_IO`. `TOOL_EXEC` stays a pool of 1. `LaminaEngine` adds:
**semantic-key:** PK-727
**semantic-key:** PK-727
**semantic-key:** PK-727

## 6. Package 2. Child

```
pools.put(Lane.POOL_IO, Executors.newCachedThreadPool(namedFactory("pool-io")));
```

Threads are named `lamina-pool-io-N` by the factory the other lanes already use. `LLM_EXEC` stays 4. `SSH_IO`, `GENERAL`, and `TOOL_EXEC` stay their present sizes.

**Public surface.** Package `org.casserole.pool`.

- `PoolChat`, the contract. No SSH types, no Swing types, no Spring types.

- `PoolChatServer`, the one server binding. `AmwpFacade` holds one instance, constructed beside `cellHttp`.
**semantic-key:** PK-728
**semantic-key:** PK-728

Nested on the contract, so they are not a third public type:

- `PoolChat.Sink` with `thought(String text)`, `message(String text)`, and `done(String reason, String message)`.

Methods, each taking the SSH user because one binding serves every login:

- `start(user)` brings that user's child to a ready process.
- `prompt(user, text, think, sink)` sends one prompt. `think` is true only when the execute word is `true`.
- `cancel(user)` cancels the in-flight prompt.
- `health(user)` reports `ready`, `down`, or `auth`.
- `shutdown` destroys every child.

`done` is called once. An empty reason is success and the message is the concatenated assistant text. A reason is one of the five, and the message starts with that reason. `thought` and `message` fire only before `done`, and `thought` fires only when package 7 forwards a chunk.

**Environment.** `ProcessBuilder.environment()` drops `XAI_API_KEY`, including a different-case spelling of that name. The value is not printed. `USERPROFILE` and `HOME` stay as the server process has them. The component does not set a per-model key and does not read `config.toml` or `auth.json`.

**Lanes.** The stdout reader is one `POOL_IO` task for the life of the child. A second `POOL_IO` task drains stderr and drops the bytes. The drain does not parse and does not log. A failure message may carry `stderr` plus the drained byte count. The bytes themselves stay off the banner.

**Caps.** One child per SSH user. The first pool execute for that user starts it. Later executes reuse it. One prompt is in flight on that process. A second cell for the same user waits inside `prompt` and holds its semaphore permit until that prompt ends. It does not fail and it does not start a second child. At most three live children. A fourth user waits inside `prompt` for a slot. That wait holds its semaphore permit. The process cap is not the semaphore of 3. An idle child counts toward three and holds no permit.

**Idle.** Fifteen minutes with no prompt destroys that user's child. A prompt in flight is not idle. The timer lives on the binding, not on `LLM_EXEC`. The next prompt starts a fresh process. JVM shutdown destroys every child through one shutdown hook on the binding, `Process.destroyForcibly`. An orphan `grok` after shutdown is a defect. `LaminaEngine.shutdown` already `shutdownNow`s every pool, including `POOL_IO`.

## 7. Package 3. Prompt

The stdio edge is newline JSON-RPC 2.0. One Jackson `ObjectMapper` on the binding reads and writes each line. Inside the server the words are Java. On the AMWP wire the words stay the TOON execute the façade already accepts.

**Once per process.** `initialize` with `protocolVersion` 1. The capabilities object does not advertise filesystem write or a terminal. No `authenticate`. The process loads its own login. The response id must match. An error object on that id is package 5.

**Once per prompt.** `session/new` with `cwd` set to the absolute pool directory and `mcpServers` an empty array. No `_meta`. No `yoloMode`. No `autoMode`. A reused ACP session would keep the previous prompt, and the working set already contains those turns. The response carries `sessionId`.

**The branch.** In `execute`, after Roux and after `stillMine`, provider `pool` calls the binding and returns. It does not call `probeGate`, does not put `cellHttp`, does not send `Hello`, and does not call `llmRouter.complete`. Ollama and Grok keep the path below that branch. The pool call sits on the `LLM_EXEC` thread and returns when `done` has run. The existing `finally` releases the semaphore.

**Reader.** Stdout has one reader. It classifies each line per package 5. The prompt finishes only on the response whose id matches the `session/prompt` id. Other ids are `pool payload`. Notifications arrive with a method and no matching response id. The sample shape is `method` `session/update`, `params.update.sessionUpdate`, and `params.update.content.text`.

**Forwarded text.** `agent_message_chunk` text goes to `sink.message`. `agent_thought_chunk` text goes to `sink.thought` only when `think` is true. An empty chunk is neither a forward nor a failure. The success `done` message is the concatenation of the message chunks. Thought text is not in that concatenation.

## 8. Package 4. Frames

- `thought`: while `stillMine`, `sendDelta` kind `t` on channel `0x0003`.
- `message`: while `stillMine`, if the chunk does not fit, `replyReason` `too-large` and signal the binding to `session/cancel` and to skip `done`. Otherwise `sendDelta` kind `c`.

- `done` with a reason, while `stillMine`: one `TYPE_ERROR` on `0x0002` through `replyReason`. The reason is the nature. The message is the isolate line, capped at 240. After that frame the execute sends no further delta, no `TYPE_DATA`, and no second error.

## 9. Package 5. Errors

Five reasons. They stay off `gracefulNotice`. A normal cell opens the one slide. A group member opens that same slide. The status line shows the reason. `showErrorSlide` shows the message. The cell stores `FAILED` through the frame it already stores. The slide text stays off the cell.

| Reason | When | `hardMiss` / `softMiss` |
| --- | --- | --- |
| `pool auth` | No TUI login surfaces as the child error, or an error object on our id whose redacted message contains `auth`, `login`, `unauthorized`, or `unauthenticated`. | soft yellow |
| `pool down` | Spawn fails, the process exits, stdout ends, or the death rule in package 8 fires. | hard red |
| `pool tool refused` | `sessionUpdate` is `tool_call` or `tool_call_update`, or the child sends a permission request. | soft yellow |
| `pool parse` | The stdout line is not one JSON value, contains a NUL, or is over 1 MiB. | hard red |
| `pool payload` | The JSON parsed, and the shape is not a chat chunk, not our result, and not noise. | hard red |

`hardMiss` returns true for `pool down`, `pool parse`, and `pool payload`. `softMiss` returns true for `pool auth` and `pool tool refused`. The existing yellow rule (`softMiss` and not `hardMiss`) then paints the slide. No second window.

**Noise.** A notification whose method is not `session/update`, or a `session/update` whose `sessionUpdate` is neither a chat chunk nor a tool kind, is counted. `plan` and `x.ai/fs/index` are that class. The count is not painted and does not fail a healthy turn. The next failure message for that prompt gains `notes:` plus the integer. A prompt that completes leaves the count off the wire.

## 9. Package 5. Errors

**Where the words point.** The message names one place: `stdout`, `spawn`, `initialize`, `session/new`, `session/prompt`, `session/update`, `session/cancel`, or `exit`. A death message includes the exit code when the process has one, `chunk 0` or `chunk 1`, and `retry used` when the one restart already ran.

**Catch-all.** An `IOException` on the child streams is `pool down`. Any other exception is `pool payload` with the simple class name and a redacted first line. The stack stays off the banner. The environment map stays off the banner. An empty message is filled with the reason.

**Redaction.** No API key, no bearer token, no `auth.json` body, and no stderr text. Those shapes in a quoted fragment become the word `redacted`.

**Restart.** Package 8 is the only restart. A parse, a payload, a tool refusal, and an auth failure do not restart the child. The child stays up when it is still alive. The next Run opens a new `session/new` on that process.

**Before the child.** `air-gap`, `burner denied`, `too-large`, and `cell-busy` stay the banners they already are.

**Client paint.** Kind `c` appends into the widget only. `cell.assistantText` stays the stored answer until `TYPE_DATA`. On a pool `TYPE_ERROR`, the assistant widget and the collapsed brief are set back to `cell.assistantText`. The slide text is not stored. Captured thought text on the strip stays until the session ends or another cell's thought replaces it.

## 10. Package 6. Card

Choosing Pool writes the target `pool`, paints the header through `shortModelName`, persists, and shows that card. It does not call `adoptGrok` and does not write a Grok settings row. Opening the modal on a cell whose target is already `pool` selects Pool. Switching back to Ollama or Grok uses the family paths those two already have. `grokDialogOpen` stays the one flag.

**Focus loss** on the target field: when `providerId` of the field is `pool`, store `pool` and show `pool`. That covers `pool`, `pool/`, and `pool/anything`.

**`shortModelName`** returns `pool` for that provider, before the blank return.

**Tips.** The Model button tip gains one sentence: Pool chats on the Heavy pool and does not use the API key. The target-field tip gains one sentence: the word `pool` stores `pool`.

**Kind `c`.** On the EDT, when the cell's provider is `pool`, append the chunk into that card's assistant area in the reply yellow, caret at the end. The card's own assistant scroll moves. The canvas scroll does not. `rebuildTabs` does not run. A collapsed card appends on its one-line brief, so the collapsed branch keeps a reference to that `JTextArea`. A chunk whose card is not on screen is dropped. The stored reply does not depend on those paints.

## 11. Package 7. Thinking

The Pool card's Thinking checkbox writes `thinkOf(id).poolOn` and persists the cell the way the Grok checkbox persists. Tip: the text sits on the footer strip, drops when the session ends, and is not stored on the turn.

`assemble`, when the provider is `pool` and `poolOn` is on, sets the existing TOON key `think` to `true`. Absent and empty mean thinking is not of interest. Provider `ollama` still owns `think` on an Ollama execute. Provider `grok` still uses `think_stream` and does not read `think` for this lane.

The binding reads that boolean from `prompt`. When it is false, the stdout reader ignores `agent_thought_chunk`. It does not call `thought` and does not send kind `t`. The line is not a failure and it is not a noise note. When it is true, the reader hands the text to `thought`, and the façade sends kind `t` while the live token matches.

`paintThinkDelta`: a pool cell paints only when `poolOn` is on. Off returns, the same return a Grok box already uses. The strip appends while the cell id matches and replaces the text when the cell id changes. `finishThink` keeps the strip when a thought arrived and hides it when none arrived. The turn does not store the text. `TYPE_DATA` stays the message chunks. The next working set stays the kept turns. A failure clears the assistant area and leaves the captured thought text on the strip.

An empty thought chunk is neither a forward nor a failure. A forwarded kind `t` counts as a forwarded chunk under package 8. A thought ignored because the signal is off does not.

This lane sends no `reasoning_effort` and no `session/set_config_option`.

## 12. Package 8. Stop and death

**Stop.** `TYPE_CANCEL` still drops the live token and cancels the in-flight handle. The pool branch, on that drop or on interrupt, calls `cancel(user)`. The binding sends `session/cancel` for the in-flight `sessionId` and leaves the child up. A late chunk is not forwarded. The cell ends on the cancel path it already uses: `CANCELLED`, no error frame. A group whose only miss is `CANCELLED` does not open the slide. `cancel` is safe to call twice.

**Death.** If the child dies during a prompt and no chunk has been forwarded, the binding starts it once more, sends `initialize` on the new process, opens a new `session/new`, and sends that prompt again. A forwarded kind `t` counts. An ignored thought does not. A second death, or a death after a forwarded chunk, calls `done` with `pool down` and does not retry. The message includes `exit` and the code when present, `chunk 0` or `chunk 1`, and `retry used` when the restart already ran. The client clears the partial assistant paint. The stored reply stays what it was.

A parse, a payload, a tool refusal, and an auth failure do not restart the child.

## 13. Proof

## 14. A remote seat

- **State:** LOCKED. 2026-10-22 17:47. The formal plan is stamped from this page. The build is named.

- **What it is not:** a new channel, a new package, or a new public type. A change to air-gap, the key strip, the directory, the retry, or the five reasons. A pool turn. A rewrite of draft.001.

> *The pool child starts. This grok does not take an updater flag on stdio.*

## 2. What stays

The command host stays, because a bare word `grok` is still not assumed to be an `.exe`. `XAI_API_KEY` stays off the child. The directory stays `user.home` plus `.casserole/pool-cwd`. One retry stays. The banner shape stays. The old flag sentences on the pool plan, the pool pre-plan, and draft.002 stay, each with a dated sentence that names this row.

## 3. Proof

- **State:** LOCKED. 2026-10-22 18:00. The formal plan is stamped from this page. The build is named.

- **What it is not:** a tool runner. `--always-approve`. A new type, channel, or notebook control. A pool turn. A rewrite of draft.001.

> *Chat and thinking land. A tool is refused in the handler and the turn continues.*

## 2. What stays

Message chunks and thought chunks stay the only paint. Any other request from the child stays `pool payload` and then `session/cancel`. The spawn stays `grok agent stdio`. The prompt still ends on the `session/prompt` response. `pool tool refused` remains when the deny cannot be written.

# Aetheris pool chat pre-plan 001

- **Source:** `grok-build-tui-adapter-component.draft.002.md`. Approved 2026-10-22 16:40. Where this page is thinner, that draft wins.

- **What it is:** the sketch a later plan is cut from. Eight packages. Chat on the Heavy pool, server first.

- **Rank:** the approved draft binds this page. The constitution and the project laws still win if a sentence drifts. Repair this page.

> *A cell whose target is `pool` chats through the local `grok` login. The weekly Heavy pool pays for that chat. The API-key lane stays the API-key lane.*

## 1. What this slice is

One notebook cell, target `pool`, talks to Grok Build through `grok agent stdio` on the server. The reply rides the stream the façade already paints. The API provider `grok` stays the API key.

**Not done:** a stamped plan, Java, a copy in `docs/plans/`, tools, a model flag, a Pool settings document, `reasoning_effort` on this lane, a second thinking listener, a third mux scope.

## 2. In / Out

### 2.1 In

| ID | Work | Approved reading |
| --- | --- | --- |
| 1 | Doors | `poolDoor` beside `grokDoor`. Both shells. `grokFactsBlock` still matches the two words. A burner stops before a child. |
| 2 | Child | `POOL_IO`. `grok agent stdio`. One child per SSH user. At most three children. Idle reap fifteen minutes with no prompt. |
| 3 | Prompt | `initialize` once per process. `session/new` once per prompt. Plain role lines. The pool branch replaces `llmRouter.complete`. |
| 4 | Frames | Kind `t` and kind `c` on `0x0003`. `TYPE_DATA` then `TYPE_EOF`. One `TYPE_ERROR` on `0x0002`. |
| 5 | Errors | `pool auth`, `pool down`, `pool tool refused`, `pool parse`, `pool payload`. The slide opens on a normal cell. |
| 6 | Card | Family Pool. Header `pool`. Kind `c` in the assistant area. `rebuildTabs` stays off the chunk path. |
| 7 | Thinking | Checkbox factory off. `think: true` is the signal. Off ignores the thought line. On uses the footer strip. |
| 8 | Stop | `session/cancel`. The child stays up. A death before any forwarded chunk retries once. |

### 2.2 Out

| Item | Why |
| --- | --- |
| API key on this lane | The TUI login is the pool. `XAI_API_KEY` is stripped from the child. |
| `reasoning_effort` | That option is a level. The interest signal is the execute word `think`. |
| Tools, MCP, `--always-approve` | Baseline 001 keeps that room. This cut is chat. |
| Grok settings row | Choosing Pool does not call `adoptGrok`. |
| Constitution and stage map | This slice does not edit them. |

## 3. As-built (what the later plan must not forget)

| Surface | Today | This slice |
| --- | --- | --- |
| `LlmRouter` | Empty is `ollama`. Providers are `ollama` and `grok`. | `pool` is a third token. The branch does not call `complete`. |
| `Lane` | `SSH_IO`, `LLM_EXEC` 4, `GENERAL` 2, `TOOL_EXEC` 1. | Adds `POOL_IO`, cached, threads `pool-io`. `LLM_EXEC` still holds the prompt span and releases the semaphore of 3 in the existing `finally`. |
| `AmwpFacade.execute` | `grokDoor`, then probe, then `complete`. `cellHttp` is the Grok map. | `poolDoor`, then the child. No probe. Cancel is `session/cancel`. |
| Channels | `0x0003` carries delta, data, and eof. `TYPE_ERROR` is `0x0002`. | Same frames. No new type. |
| `paintThinkDelta` | Kind `t` only. Grok paints when `grokOn` is on. | Pool paints when `poolOn` is on. Off returns. |
| Kind `c` | Dropped. | A pool cell appends into the assistant area. The canvas scroll does not move. |
| Error slide | Named banners stay on the status line. | The five pool reasons stay off that list, so the slide opens. |
| `shortModelName` | Blank for any provider other than `ollama` and `grok`. | Returns `pool`. |
| Family list | Ollama and Grok. | Pool beside them. One card, one sentence, the Thinking checkbox. |
| Air-gap | `grokDoor` and `grokFactsBlock`. | `poolDoor` beside them. Both words still match. |

## 3. As-built (what the later plan must not forget)

## 4. Work packages

Suggested order for the plan: 1, 2, 3, 4, 5, 6, 7, 8. The doors come before a child. The child comes before a prompt. The frames come before the slide. The card comes before the thinking signal. Stop closes the prompt.

### 1. Doors

- `AetherisYaml` gains `poolDoor` beside `grokDoor`. True only for provider `pool`, burner off, and air-gap word exactly `true`. No model id.
- Both shells call it. When it is true, both shells still run the facts match. A mismatch blocks with `air-gap`.
- Unset still refuses. Explicit `false` already allows every cloud target.
- A burner `pool` cell fails `burner denied` before a child starts.
- `air-gap`, `burner denied`, `too-large`, and `cell-busy` stay on their present paths.

### 2. Child

- The child does not inherit `XAI_API_KEY`. `USERPROFILE` and `HOME` stay as the server process has them.
- The working directory is `user.home` plus `.casserole/pool-cwd`.
- One child per SSH user. One prompt in flight on that child. A second cell for that user waits and holds its semaphore permit. At most three live children. A fourth user waits.
- Fifteen minutes with no prompt destroys that user's child. A prompt in flight is not idle. JVM shutdown destroys every child.

### 3. Prompt

- `initialize` once per process. No `authenticate`.
- `session/new` once per prompt. The working set is the only history. The body is plain role lines.
- The pool branch replaces `llmRouter.complete`. It does not enter the Grok probe and it does not send `Hello`.

### 4. Frames

- `agent_message_chunk` is kind `c`. `agent_thought_chunk` is kind `t` when package 7 says the signal is on.
- The prompt finishes on the response whose id matches. `TYPE_DATA` is the concatenated message. `TYPE_EOF` follows.
- A line the reader cannot classify follows the approved draft §11. One failure is one `TYPE_ERROR` on `0x0002`.
- A chunk that does not fit fails `too-large`.

### 5. Errors

- Reasons are `pool auth`, `pool down`, `pool tool refused`, `pool parse`, and `pool payload`.
- They stay off `gracefulNotice`. A normal cell opens the one slide. The status line shows the reason. The body shows the message, capped at 240 characters.
- Red is `pool down`, `pool parse`, and `pool payload`. Yellow is `pool auth` and `pool tool refused`.
- The message carries no key, no bearer token, no `auth.json` body, and no stderr text.
- The live assistant area returns to the text from before the run. The slide text is not stored on the cell.

### 6. Card

- The Model family list gains Pool. Choosing it writes target `pool`, paints header `pool`, and does not write a Grok settings row.
- Focus loss stores `pool`, `pool/`, and `pool/anything` as `pool`.
- `shortModelName` returns `pool`.
- Kind `c` appends into that card's assistant area on the EDT. `rebuildTabs` does not run per chunk.

### 7. Thinking

- The Pool card shows the Thinking checkbox. Factory off. The flag is `poolOn` on the session holder.
- `assemble` writes `think: true` when the box is on. Absent means thinking is not of interest.
- When the word is absent, the stdout reader ignores `agent_thought_chunk`. No second listener.
- When the word is `true`, kind `t` is scraped by `paintThinkDelta` onto the footer strip. The turn does not store that text.
- This lane sends no `reasoning_effort` and no `session/set_config_option`.

### 8. Stop and death

- Stop sends `session/cancel` and leaves the child up. The cell stores `CANCELLED`. No error frame.
- A death before any forwarded chunk starts the child once more and sends the prompt again.
- A second death, or a death after a forwarded chunk, is `pool down`. An ignored thought does not count as forwarded.
- A parse, a payload, a tool refusal, and an auth failure do not restart the child.

## 5. Proof, when a later sentence names the build

**Title:** Grok Build stdin/stdout Bridge — Java Process Abstraction for SuperGrok Heavy Pool Access

**Intent:** Provide a deterministic, local-process bridge that lets external systems — custom agents and orchestration layers — drive Grok Build via the Agent Client Protocol over stdin/stdout. The bridge reuses the existing local Grok login so all token consumption draws from the SuperGrok Heavy shared pool rather than pay-as-you-go API rates. This is not a replacement for the interactive TUI. It is a headless control plane for the same binary.

**Proposal extension:** The bridge is implemented as a reusable Java component. A thin abstraction layer exposes a stable contract for session lifecycle, prompt dispatch, streaming updates, cancellation, and observability. Server-side consumers (Casserole, or any orchestration service) bind to this contract. Client-side consumers, if needed later, bind to the same contract. The abstraction hides the fact that the underlying implementation spawns and manages a Java `Process` running `grok agent stdio`. Callers never see process handles, streams, or JSON-RPC framing.

**Context:** Grok Build CLI (`grok`) exposes an Agent Client Protocol (ACP) server when launched as `grok agent stdio`. The process speaks newline-delimited JSON-RPC 2.0 on stdin and stdout. Authentication can use a cached local token from `grok login` or an `XAI_API_KEY`. For automated use, `--always-approve` suppresses interactive permission prompts. The resulting session behaves like an interactive Grok Build session for tool use, file access, and model reasoning, but is driven entirely by structured messages. The Java component owns the process lifecycle and translates the ACP protocol into the public contract. Reasoning and thought content is isolated from primary response content and exposed on a distinct stream.

**Invariants:**

- The `grok` process is spawned and owned by the Java component. No network socket is required for the core loop.
- All token consumption inherits the credentials of the local `grok` binary. If the binary is authenticated under a SuperGrok Heavy account, the shared weekly pool is used.
- Every request crossing the public contract is structured. No free-text control channel exists between a caller and the component.
- Tool execution permissions are governed by the flags passed at spawn time. `--always-approve` is the default for unattended operation. Deny rules and hooks still apply.
- Session state lives inside the Grok process (and optionally on disk under `~/.grok/sessions`). The Java component does not own long-term memory; it only orchestrates the session.
- The Java component must terminate the child process cleanly on shutdown or error. Orphaned `grok` processes are a defect.
- Streaming updates arrive as notifications inside the component. The public contract exposes them as a stream or callback sequence. The final result of a prompt is delivered with `stopReason` and structured token accounting.
- The public contract is transport-agnostic from the caller’s perspective. The component may later offer a local socket or in-process binding without changing the contract.
- A single Process instance manages exactly one active session by default. Multi-session use requires distinct component instances.
- stderr from the process is captured separately and never mixed into the protocol stream.
- Working directories passed to the component must be absolute. Relative paths are rejected.
- Credentials and tokens are never logged or exposed on the public contract.
- Reasoning or thought content is delivered on a distinct stream channel, separate from primary agent message content and tool updates.

**Architecture:**

- Layer 1 — Java Process Owner: Uses `ProcessBuilder` to launch `grok --no-auto-update agent --always-approve stdio`. Owns the `Process` handle, its stdin, stdout, and stderr. Manages lifecycle, exit detection, forced termination, and optional restart.
- Layer 2 — JSON-RPC Transport (internal): Reads and writes newline-delimited JSON objects on the process streams using a dedicated reader thread. Maintains outstanding request IDs. Routes notifications and responses. Handles partial lines and parse failures. This layer is not visible outside the component.
- Layer 3 — Session Controller (internal): Implements the ACP lifecycle. Issues `initialize`, then `authenticate` (preferring cached local token), then `session/new`. Stores the returned `sessionId`. Surfaces agent capabilities discovered during `initialize`.
- Layer 4 — Prompt Dispatcher (internal): Accepts a prompt from the public contract, wraps it as a `session/prompt` request, and routes incoming `session/update` notifications back through the public stream. Supports cancellation via `session/cancel`. Separates thought or reasoning chunks onto a distinct channel.
- Layer 5 — Lifecycle Manager (internal): Handles process exit, unexpected termination, session resume via `session/load` when a prior session ID is available, idle timeouts, graceful shutdown registered with the JVM, and health checks.
- Layer 6 — Public Contract: A narrow Java interface exposing start/connect, prompt (async), stream subscription, thinking-stream subscription, cancel, session resume, health/status, restart, capability discovery, and shutdown. Server-side and client-side callers bind only to this interface. The implementation is the reusable component.

**Operational Flow:**

1. Caller constructs the component with configuration (working directory, optional model, MCP servers, timeouts, token budget, logging level).
2. Caller invokes the public contract to start a session.
3. The component spawns the `grok` process with the required flags and absolute working directory.
4. The component sends `initialize` and waits for agent capabilities.
5. The component sends `authenticate` using the cached local credential method.
6. The component sends `session/new` with the working directory and any MCP server list.
7. On receipt of `sessionId`, the public contract reports the session as ready and exposes discovered capabilities.
8. For each caller request, the component sends `session/prompt`. It streams `session/update` chunks back through the public interface as they arrive. Thought or reasoning content is routed to the distinct thinking stream.
9. When the `session/prompt` response arrives with `stopReason` and usage metadata, the turn completes.
10. Repeat from step 8 for subsequent turns, or invoke cancel on an in-flight prompt, or invoke shutdown to terminate the process.

**Key Flags and Modes:**

- `--always-approve` — Required for unattended automation so tool calls do not block.
- `--no-auto-update` — Recommended for long-running components to avoid background update checks.
- `--model <id>` — Optional override of the model used for the session.
- Sessions may be resumed across process restarts if the session ID is retained and `session/load` is supported by the agent capabilities.

**Public Contract Surface:**

- `startSession(config)` — returns a session handle or future.
- `prompt(sessionId, content)` — returns an async result containing streamed updates and a final `PromptResult` (stopReason + Usage).
- `subscribe(sessionId, listener)` — receives incremental updates (agent_message_chunk, tool_call, tool_call_update, plan).
- `subscribeThinking(sessionId, listener)` — receives reasoning or thought content on a distinct channel.
- `cancel(sessionId, promptId)` — cancels an in-flight prompt.
- `resumeSession(sessionId, config)` — resumes a prior session when supported.
- `health()` / `isAlive()` — returns process and protocol health.
- `restart()` — terminates and respawns the process, re-establishing a session.
- `capabilities()` — returns the agent capabilities discovered at initialize.
- `shutdown` — clean termination.

**Error Taxonomy:**

- ProcessFailure — binary not found, spawn failed, unexpected exit, version mismatch.
- ProtocolError — malformed JSON, unexpected method, timeout waiting for response.
- AuthFailure — credential missing or rejected.
- SessionError — session/new or session/load failed, idle timeout exceeded.
- PromptError — stopReason indicates refusal, max_tokens, cancelled, or tool failure.
- ResourceError — token budget exceeded, process resource limit reached.
- All errors surface as typed exceptions or error objects on the public contract. No raw process output is exposed.

**Observability:**

- Structured logs of protocol messages with credential redaction.
- Metrics available via the health/status method: process uptime, prompt count, cumulative tokens, last latency, stream lag indicator.
- Optional debug stream for stderr content.
- Restart count and last failure reason exposed on the health object.
- Thinking stream volume and latency are tracked separately from primary content.

**Distinctions:**

- This is not the headless one-shot path (`grok -p`). Headless exits after a single prompt. The ACP bridge maintains a live process and session for multi-turn work.
- This is not the interactive TUI. No terminal UI is rendered. All interaction is structured.
- Token accounting is visible in the prompt result. The component can surface or enforce budgets before continuing.
- The reusable Java component means a local desktop client, or a future remote orchestrator all bind to the same contract without knowing about process handles or streams.
- Reasoning content is isolated from primary response content and delivered on its own stream.

**Open Constraints:**

- The exact set of supported prompt content types is declared by the agent during `initialize`. The component must respect those capabilities and surface them on the public contract.
- Permission prompts are suppressed only under `--always-approve`. Without it, the process may block.
- Local file and tool access is governed by the working directory passed to `session/new` and by any Grok rules or hooks active in that directory.
- The public contract’s concrete delivery mechanism (in-process method calls, local socket, or both) is an implementation detail left to the first consumer.
- Multi-session concurrency beyond one process per session is outside the current scope.
- Behavior under Heavy pool exhaustion mid-turn is defined by the underlying Grok binary and is not overridden by the component.
- Availability of a distinct thinking or thought stream is declared by the agent during `initialize`. When present, the component isolates it. When absent, the thinking stream remains silent.

**End of Spec.**

# Pool chat bridge draft 002

**Serial:** `grok-build-tui-adapter-component.draft.002.md`

## 0. What this cut is

A reusable Java component, bound first on the Casserole server, that turns one notebook cell into a chat with Grok Build. The component starts `grok agent stdio`, sends the cell prompt, and returns the reply. The OS user that runs the server already holds the TUI login in `~/.grok/auth.json`. That login is the SuperGrok Heavy weekly pool. This lane does not read an API key and does not send one.

The same Java contract can be bound inside a client process later. This cut binds it on the server only. Callers see prompt, chunks, cancel, and health. They do not see a process handle or a JSON-RPC line.

## 1. What this cut is not

- A replacement for provider `grok`. That provider stays `POST https://api.x.ai/v1/chat/completions` and the API key.
- Tools, file edits, git, MCP servers, `--always-approve`, session resume, a caller-chosen working directory, a model flag, or a token budget.

## 2. The mux decision

The team does not add a third mux scope.

AMWP is one SSH login and two scopes. Scope A already carries a cell execute and the cell stream. Channel `0x0003` is that stream. `TYPE_DELTA` already carries a kind byte, `t` for thought and `c` for content. `TYPE_DATA` then `TYPE_EOF` already finish the cell. A pool reply is that same stream with the same bytes.

A new channel would be a second product on the pipe. This chat is a cell with a new target. The façade that already runs an execute learns one more provider. The frame header does not change. The client that already paints deltas and stores `TYPE_DATA` does not learn a new frame. A pool failure uses the `TYPE_ERROR` frame that façade already sends on channel `0x0002`.

## 3. The lamina decision

The team does add one lane.

## 4. Metal

2. **Who spawns.** The Casserole JVM spawns the child. The process owner is that OS user. The client never receives `auth.json` and never sends a token. A server running as another account does not see the TUI login, and the cell fails with `pool auth` in §11.

8. **Death and idle.** If the child dies during a prompt and no chunk has been forwarded, the component starts it once more and sends that prompt again. A second death fails the cell. A death after a chunk has been forwarded does not retry. The cell fails. Reason `pool down`. A retry after a painted chunk would write the reply twice. Fifteen minutes with no prompt destroys that user's child. 2026-10-22 16:40. The team keeps that number. A prompt in flight is not idle. Fifteen minutes is long enough to read a reply and short enough to free one of the three child slots. The next prompt starts a fresh one. Shutdown of the JVM destroys every child. An orphan `grok` after shutdown is a defect. Cancel on the cell sends `session/cancel` for that prompt and leaves the child up. That cancel does not go through `cellHttp`. The live token drops the way a stopped execute already drops it. A delta that arrives after the drop is not forwarded. The cell ends the way a cancelled execute already ends. The failure frame for a death is §11.

11. **Laws that bind the later build.** No lambdas in our types. No Lombok. No JUnit. Anonymous classes only where the UI already uses them. No new public type beyond the contract and the one server binding. Package `org.casserole.pool`. The host owns it. `org.aetheris` does not gain a cell type. The contract has no SSH types and no Swing types.

## 5. Contract

The interface is the whole public surface.

- `start()` brings the child to a ready session for one user.
- `prompt(text, sink)` sends one text prompt. The sink receives thought chunks and message chunks on the calling code's terms, then one completed message, or one failure. The failure is a reason and a message. `done` carries that pair. The interface does not gain a fourth method.
- `cancel` cancels the in-flight prompt.
- `health()` reports `ready`, `down`, or `auth` for that user. It does not report a secret.
- `shutdown` destroys the child.

A sink is a small listener with three methods: thought, message, done. Thought fires when §12 forwards a thought chunk. The server binding implements it by writing the AMWP frames in §4.9. A later client binding can implement it by writing a local buffer. The interface does not change.

## 6. Depends

| Spec | Binds | This cut owes |
|---|---|---|
| `../global/core-system-technical-requirements.draft.011.md` §2 and §4 | AMWP is two wire channels. Lamina lanes are `SSH_IO`, `LLM_EXEC`, `GENERAL`, `TOOL_EXEC`. Shell AMWP work is not a Lamina lane today. | `POOL_IO` is the one new lane. The other four keep their sizes. `AetherisWorker` stays the client reader. |
| `../aetheris/aetheris-technical-specification.finalize.md` §7 | Frame header, channels `0x0001` through `0x0004`, types through `TYPE_DELTA0x06`, one in-flight execute per cell, max payload 1 MiB. This file is the constitution. Its body is not rewritten for this draft. | Pool uses channel `0x0003`, `TYPE_DELTA`, `TYPE_DATA`, and `TYPE_EOF`. A pool failure uses the existing `TYPE_ERROR` on channel `0x0002`. No new channel. No new type. A frame that does not fit fails `too-large`. |
| `../aetheris/aetheris-grok-api.draft.001.md` | Provider `grok` is `POST https://api.x.ai/v1/chat/completions`. The key is `casserole.grok.api-key`. The 22:51 door is provider `grok`, a model id, burner off, air-gap word `true`. Jackson forms that body. The probe and the effort table stay on that provider. | `pool` does not POST. `pool` does not read the key. `poolDoor` is a second predicate beside `grokDoor`. `grokDoor` stays. Air-gap word stays `true`. `application.yaml`, `~/.aetheris.yaml`, and `num-ctx` stay unread by this cut. |
| `AmwpFacade` semaphore of 3 | `executeSlots` is acquired before `execute` runs and released when that execute ends. `LLM_EXEC` stays 4. | A pool execute takes one of those permits. The three-child cap in §4.7 is a second limit and does not replace the semaphore. |
| `AmwpConstants`, `AmwpToon`, `LlmRouter.providerId` | Channels, the execute body, the token before `/`, and `encodeError`. | `pool` is a new token. Empty stays `ollama`. `grok` stays `grok`. A pool failure is `reason` plus `message`. The façade caps that message at 240 characters, the same cap `garnered` uses. `gracefulNotice` does not gain the five reasons. The later build teaches `hardMiss` and `softMiss` the colors in §11. |
| Cell cancel | `TYPE_CANCEL` drops the live token. A stopped cell does not store the reply. | `session/cancel` runs for that prompt. The child stays up. The cell ends on the path cancel already uses. |
**semantic-key:** PK-729
**semantic-key:** PK-729
**semantic-key:** PK-729
**semantic-key:** PK-729
**semantic-key:** PK-729
**semantic-key:** PK-729
**semantic-key:** PK-729

- 2026-10-22 16:40. The idle strike is closed. Fifteen minutes with no prompt destroys that user's child. A prompt in flight is not idle. A later number is a new sentence on this row.
- 2026-10-22 16:00. The label strike is closed. The family word is Pool. The stored target is `pool`. The header line is `pool`.
- 2026-10-22 16:17. The error report is filed in §11. The five reasons are the set. A new reason is a strike on this row.
- 2026-10-22 16:22. Thinking capture is filed in §12. The checkbox is factory off. A different store for the thought text is a strike on this row.
- 2026-10-22 16:35. The thinking signal is the execute word `think`. The reader ignores a thought when that word is absent. A second listener is outside this cut.

## 9. UI slide-in

**Choosing the lane.** The Model Config family list is Ollama, Grok, and Pool. Pool shows one card, one sentence, and the Thinking checkbox: this cell chats on the Heavy pool, with no API key, no model id, no effort, and no temperature. The checkbox is factory off. §12 is the capture. Choosing Pool writes the target `pool`, paints the header `pool`, and saves the cell. It does not call `adoptGrok` and it does not write a Grok settings row. Opening the modal on a cell whose target is already `pool` selects Pool and shows that card. Switching back to Ollama or Grok uses the family paths those two already have. The target field under the group bar accepts the typed word `pool`. Focus loss rewrites `pool` and `pool/` and `pool/anything` to the stored word `pool`. Its tip gains one sentence for that word. `shortModelName` returns `pool` for that provider. Today any provider other than Ollama and Grok returns a blank line, so this return is required or the header goes empty.

**Deltas.** `paintThinkDelta` today returns unless the kind is `t`. Kind `c` is dropped. A Grok thought paints only when that cell's Thinking box is on. A pool thought paints on that same strip when the Pool Thinking box is on. §12 is that capture. The strip keeps the text when a thought arrived, and it hides on finish when none arrived.

Kind `c` while the armed cell's provider is `pool` appends into that card's assistant area, on the EDT, in the reply yellow, caret at the end of that area. The card's own assistant scroll moves. The canvas scroll does not. `rebuildTabs` does not run per chunk. A collapsed card has no assistant area, so the same chunk appends on that card's one-line brief. A chunk whose card is not on screen is dropped. The stored reply does not depend on those paints.

**Stop and failure.** The stop control that already cancels a cell cancels a pool cell. The server sends `session/cancel` and leaves the child up. The cell stores `CANCELLED` when the user stopped it, and `FAILED` when the bridge failed it. A group whose only miss is `CANCELLED` does not open the slide. A pool failure opens the one error slide on a normal cell and on a group member. §11 is that report. The status line shows the reason. The slide body shows the message. `pool down`, `pool parse`, and `pool payload` are hard, red. `pool auth` and `pool tool refused` are soft, yellow. The live assistant area is put back to the text from before the run, so a partial paint does not become the stored reply. The slide text is not stored on the cell. None of the reason strings contains a token or a key. A group may mix targets. Pool members of one user share one child and queue. The waiting execute holds its semaphore permit until the child finishes that prompt. An Ollama member does not wait on `POOL_IO`.

**What the UI does not grow.** No Pool settings document. No effort table. No temperature field. No stream checkbox. No burner-cloud checkbox on the Pool card. No second error modal. No second thinking pane. No client process. No new AMWP type.

## 10. Landing

2026-10-22 16:08. The dependency pass left six places where a literal reading would miss the floor. This section is the operative reading for those six.

2. **The reader and the execute thread are different waits.** Stdout and stderr are `POOL_IO`. The stderr drain drops bytes. `execute` still runs on `LLM_EXEC` and holds one of the four threads only while the prompt is in flight, the same span as `complete`, and it releases the semaphore of 3 in the existing `finally`. Between prompts the child stays up and `LLM_EXEC` does not.

3. **A death after paint does not retry.** A death before any forwarded chunk retries once. A second death, or a death after a chunk, is `pool down`. The partial paint is cleared. The stored reply stays what it was.

4. **Cancel is not an HTTP slot.** `session/cancel`, then the live token drop. A late delta is not forwarded. `cellHttp` stays the Grok map.

5. **The air-gap match stays.** `poolDoor` is the allow. `grokFactsBlock` is still the mismatch check. Both shells do both. A burner never reaches the child.

6. **Notices stay split.** A normal cell uses the status line. A group member also uses the one slide, red for `pool down`, yellow for `pool auth` and `pool tool refused`. Focus loss stores the bare word `pool`.

2026-10-22 16:17. Row 6 is the earlier reading for those three words. §11 is the operative notice. A pool failure opens the error slide on a normal cell. The status line still shows the reason. Focus loss still stores the bare word `pool`.

## 11. Error report

**One frame.** A failed pool execute ends with one `TYPE_ERROR` on channel `0x0002`. The reason is the nature. The message is the isolate line, and it starts with that same reason. The façade caps the message at 240 characters. After that frame, the execute sends no further delta, no `TYPE_DATA`, and no second error. A user stop stores `CANCELLED` and sends no error frame. A line that arrives after the live token has dropped is not forwarded. That is the cancel rule in §10 row 4.

**The slide.** The five reasons stay off `gracefulNotice`. A normal cell opens the one slide. A group member opens that same slide. The status line shows the reason. The body shows the message. `showErrorSlide` replaces the text when a later miss arrives. The cell stores `FAILED`. The slide text stays off the cell, which is the slide's present rule. The later build teaches `hardMiss` and `softMiss` the colors below. No second window.

| Reason | When | Slide |
|---|---|---|
| `pool auth` | The child has no TUI login, or the error object on our id has a redacted message that contains `auth`, `login`, `unauthorized`, or `unauthenticated`. | soft yellow |
| `pool down` | Spawn fails, the process exits, stdout ends, or the death rule in §4.8 fires. | hard red |
| `pool tool refused` | `sessionUpdate` is `tool_call` or `tool_call_update`, or the child sends a permission request. | soft yellow |
| `pool parse` | The stdout line is not one JSON value. A NUL in the line is this reason. A line over 1 MiB is this reason. | hard red |
| `pool payload` | The JSON parsed, and the shape is not a chat chunk, not our result, and not noise. | hard red |

**What is forwarded.** `agent_message_chunk` is kind `c`. `agent_thought_chunk` is kind `t` when §12 says the signal is on. When the signal is off, the reader ignores that line. An empty text chunk is not forwarded and is not a failure. The response whose id matches the prompt finishes the cell with `TYPE_DATA` and `TYPE_EOF`.

**Noise.** A notification whose method is not `session/update`, and a `session/update` whose `sessionUpdate` is neither a chat chunk nor a tool kind, is counted. It is not painted. It does not fail the turn. An `x.ai/fs/index` is that class. The count is the word `notes:` plus the integer, appended to the next failure message for that prompt. A prompt that completes leaves the count off the wire.

**What fails the line.** A response whose id does not match the outstanding request is `pool payload`. A request from the child that asks this cut to do work, other than a tool or a permission, is `pool payload`. The component then sends `session/cancel` when the child is still alive, so the child is not left waiting on an answer this cut will not send. An error object on our id that is not `pool auth` is `pool payload`, and the message includes `code` plus the integer when the code is an integer. A line that is not one JSON value is `pool parse`. The message quotes at most 80 characters of that line after redaction. A line over 1 MiB is reported as `pool parse stdout line over 1 MiB`, and those bytes are not copied into the message.

**Where the words point.** The message names one place: `stdout`, `spawn`, `initialize`, `session/new`, `session/prompt`, `session/update`, `session/cancel`, or `exit`. A death message includes `exit` and the code when the process has one, `chunk 0` or `chunk 1` for whether a chunk was forwarded, and `retry used` when the one restart already ran.

**Restart.** §4.8 stays the only restart. A death before any forwarded chunk retries once. A parse, a payload, a tool refusal, and an auth failure do not restart the child. The child stays up when it is still alive. The next Run opens a new `session/new` on that process.

**Catch-all.** An exception on the prompt task becomes that one frame. An `IOException` on the child streams is `pool down`. Any other exception is `pool payload`. The message is the exception class simple name and a redacted first line. The stack stays off the banner. The environment map stays off the banner. An empty message is filled with the reason, so the slide has the nature.

**Redaction.** The message carries no API key, no bearer token, no `auth.json` body, and no stderr text. Those shapes in a quoted fragment become the word `redacted`. Stderr still contributes a byte count only. The drain in §4.6 stays a drain.

**Before the child.** `air-gap`, `burner denied`, `too-large`, and `cell-busy` stay the banners they already are. They are decided before the reader, or by the fit check. This section leaves them on their present paths.

## 12. Thinking capture

**The signal.** `assemble` writes the existing TOON key `think` as `true` when `poolOn` is on. Absent and empty mean thinking is not of interest. That word is the signal the pool handler reads. Provider `ollama` still owns `think` on an Ollama execute. Provider `grok` still uses `think_stream`. The handler does not send `reasoning_effort` and does not call `session/set_config_option`. The child's option is an effort level, and a boolean option is rejected, so that call does not carry on or off.

**The scrape.** `agent_thought_chunk` rides the same stdout arm as `agent_message_chunk`. When the signal is on, the reader hands the text to the sink's thought method, and the façade sends kind `t` on channel `0x0003` while the live token is still this execute. `paintThinkDelta` already scrapes kind `t` onto the footer strip. The strip paints a pool thought only when `poolOn` is on. It appends while the cell id is the same, and it replaces the text when the cell id changes. `finishThink` keeps the strip when a thought arrived. The text drops when the session ends. The turn does not store it. `TYPE_DATA` stays the message chunks. The next prompt's working set stays the kept turns. When the child sends no thought, the strip stays hidden the way a run with no thought already hides. A failure clears the assistant area and leaves the captured thought text on the strip until the session ends or another cell's thought replaces it.

**When the signal is off.** The reader ignores the thought line. It does not call the sink and it does not send kind `t`. The line is not a failure and it is not a noise note. A stray kind `t` meets the return `paintThinkDelta` already uses when a Grok box is off. An empty thought chunk is neither a forward nor a failure.

**Death.** A forwarded kind `t` counts as a forwarded chunk under §4.8. A thought ignored because the signal is off does not.

**The tip.** The checkbox tip says the text sits on the footer strip, drops when the session ends, and is not stored on the turn. No second thinking pane.
