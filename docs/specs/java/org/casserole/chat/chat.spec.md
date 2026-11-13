# org.casserole.chat

## Child pages

<pre>
org.casserole.chat/
└── <a href="cmd/cmd.spec.md">org.casserole.chat.cmd</a>
</pre>

## 12. Rust row

Side move. `ChatSession.replaceTurns` clears the turns and appends the user and assistant list. The requirement is the cleared Roux memory. The sibling is `clearTurns`, which keeps the configured prompt.
**semantic-key:** PK-286

## 7. Package 4. Groups

7. Chrome stays: footer Stop after Run, `ThinkingClockm:ss`, glyph `#00FF66`, word and counter `#00D7FF`. Do not reopen the feature.

## 3. Side move

`ChatSession.replaceTurns` clears the turns and appends the user and assistant list. The sibling is `clearTurns`, which keeps the configured prompt.
**semantic-key:** PK-287

The footer clear and the VT100 `clear` are cousins, not the same box. VT100 `clear` drops `ChatSession` turns and keeps the system prompt. The canvas is the vault. The new button clears the active canvas back to one empty `INCLUDED` turn and writes the vault. It does not also wipe the SSH session. One gesture that clears both memories would surprise the console. Ghost cells go away with the rest of the turns. The burner pin does not spare a cell. `clear` on the console stays what it is.
**semantic-key:** PK-955

## Mood format

The dialog footer carries the same thinking spinner the notebook uses. The glyph is emerald. The word thinking is electric blue. The clock is `m:ss` from `ThinkingClock`, and it counts from that send. A retry starts the clock again. The spinner stops when the reply lands, when the yellow slide paints, when the dialog closes, and when a miss closes the dialog. Approve and Cancel stay available while the spinner runs. A second Enter while a reply is in flight still leaves the typed line.
**semantic-key:** PK-288

### One provider call - Make it so. Approved

- Tab A cancel and Scope A cancel stay two paths. `ThinkingClock` stays the one clock.
**semantic-key:** PK-289

## The report

- Both thinking spinners show `ThinkingClock` as `m:ss`, and as `h:mm:ss` after an hour.
**semantic-key:** PK-290

## Floor the plan stands on

Footer Run, then Stop, then New turn, Clear turns, Preview. `ThinkingClock` on both spinners. `TYPE_CANCEL` on Stop. Import on the VT100 context menu and the File menu. `num_ctx` from yaml when the integer is positive, on chat, on `ollama load`, and on the load half of `swap`.
**semantic-key:** PK-291

### 1.4 What is in the POC today vs not

- Per-connection `ChatSession` + command processor + format router.
**semantic-key:** PK-292

### 4.2 Prompt assembly (`ChatSession`)

On SSH connect, a new `ChatSession` is created with:
**semantic-key:** PK-293

## 2. Assessment

The VT100 spinner is one line, glyph `#00FF66`, word `thinking` in `#00D7FF`. Ctrl-C sets `turnCancelled`, cancels the worker, and stops the spinner. `ChatSession.appendUser` and `appendAssistant` run only after a finished reply, and only while `turnCancelled` is still false. `handleChar` and that append share the shell lock, so the append cannot slip past Ctrl-C. `message list` reads that session. An interrupted line is not a turn.
**semantic-key:** PK-294

## 3. Resolution

`ThinkingClock` formats the elapsed time as `m:ss`, and as `h:mm:ss` after an hour. Both spinners show it after the word `thinking`, in `#00D7FF`. The Aetheris count is stored on the user's run, so switching vaults shows that vault's request. The VT100 count starts with the spinner. `spinner: none` still draws no line.
**semantic-key:** PK-295

## Two call sites — frozen

The SSH sink runs inside the walk `ChatSession.buildPrompt` already uses, and the canvas sink runs on the host list that `holdRoux` stores and the provider receives, so the Java client and the Rust client share one trim.
**semantic-key:** PK-296

## Live line — frozen

> `ChatSession.buildPrompt` appends that line after `getWindow` returns. The line is stored only after a non-empty reply.
**semantic-key:** PK-297

## Two regions — frozen

The SSH tape is the region `ChatSession` opens. A canvas push writes `user.aetheris` and replaces that region. The sink does not clear the SSH region. A canvas trim does not delete SSH turns.
**semantic-key:** PK-298

### 0.3 `conversationId` (question 7)

- **RAM-only (`hot-only` / spill off):** mint a UUID per shell (today’s `ChatSession` constructor). Hangup unmaps RAM; does not attach or write the user pack.
**semantic-key:** PK-299

### 1.1 Goals (v1)

4. Replace Casserole `ChatSession`’s `ArrayList<Message>` with Roux. Full history in the region; **window at read**. `message list` / `clear` / `chat start` / `status` counts work against Roux.
**semantic-key:** PK-300

## 10. Casserole integration

Replace `ChatSession` history list. Keep a thin session object:
**semantic-key:** PK-301

## 0. Goal

4. `hot-only` (or spill off): **no pack I/O**, exclusive off, N independent RAM sessions (existing `ChatSession` UUID).
**semantic-key:** PK-302

### P4 — Casserole wiring + exclusive + banner

`ChatSession.close()` → `persistClose`. Safe to call twice.
**semantic-key:** PK-303

## 7. Sequencing gotchas (must not miss)

2. **`ChatSession` constructor must not allocate a UUID region when disk-backed.** Open first, then attach.
**semantic-key:** PK-304

## 7. Gaps / gotchas (sealed for this slice)

| Issue | Seal |
|---|---|
| Two SSH logins as the **same** user | **Disk-backed:** refuse the second (red line: session already open). **RAM-only:** allow; each shell is its own RAM region. Different usernames are independent either way. |
| Stale exclusive after crash / kill | In-process map empty on new Boot **and** OS `FileLock` on `{user}/.lock` (or `pages.bin`) is released when the JVM dies → **take over**. |
| Two Boot JVMs, same `spill.dir` | Same `FileLock`: second process gets “session already open” / region in use. No shared-writer protocol. |
| Yaml `page-bytes` change | **Refuse** reopen; operator deletes that user folder or reverts yaml. |
| Half-present files (pack xor valid manifest) | **Refuse**. Do not start empty on top of leftover bytes. Operator deletes the user dir. |
| Torn manifest | Atomic tmp+rename; CRC fail → refuse (same as half-present). |
| `hot-only` + kill -9 / hangup | That session’s RAM is gone. No pack write, so nothing to reload for that session. A frozen pack from an earlier `commit`/`append` is unchanged. |
| RAM-only vs shared pack | RAM-only sessions **must not** pwrite `{user}/pages.bin`. Otherwise two sessions would be two writers. Restore/persist/exclusive apply only when durability is disk-backed. |
| RAM-only region key | Cannot be username alone (two live shells would share one Arena). Keep `ChatSession`’s UUID (already minted in `createShell`). SSH session id is a fine *label*, not the map key: it is per connection, not per shell channel. Disk-backed key remains the username. |
| Sparse `pages.bin` scan | Walk `0.. pageCount-1` only. Per-page bound = slot `usedBytes`. |
| Bad frame CRC mid-scan | Stop that page; do not repair. Happy path assumes clean close. |
| Username path traversal | Allow `[A-Za-z0-9._-]+` only; reject `..` and separators. Invalid name → refuse connect. Owner field must match. |
| `status conversation:` UUID | Keep UUID in manifest for display; **engine / ChatMemory key is username**. |
| Index vs log disagreement | Scan wins; `nextSeq = max(manifest, maxSeqSeen+1)`. |
| Windows file lock / channel leak | Close `FileChannel` + release `FileLock` in `close()` **before** process exit. `close()` is **idempotent** (SSH `destroy` and Boot `@PreDestroy` may both run). |
| System prompt on restore | Do **not** rewrite frame 1. `clear` / `chat start` re-insert yaml system prompt after wipe. |
| `persist`/`restore` false combos | Defaults all `true`. `persist=false` = today’s delete. `restore=false` leaves files idle if persist was on; do not auto-delete. |
**semantic-key:** PK-305

## 11. Lifecycle vs spill files

| Event | Spill files |
|---|---|
| Successful rotate (disk-backed) | Slot in `{spill.dir}/{user}/pages.bin` |
| `clearRegion` / `chat start` / `clear` | Disk-backed: wipe pack in place, rewrite empty manifest, keep folder + lock. RAM-only: RAM reset only |
| `persistClose` / SSH `ChatSession.close` | Disk-backed persist: checkpoint + manifest + unmap; **keep** user dir. RAM-only: unmap only |
| Process `@PreDestroy` | `persistClose` each live region |
**semantic-key:** PK-306

## 1. The window

Door: a new ChatSession, and a canvas working set.
**semantic-key:** PK-307
