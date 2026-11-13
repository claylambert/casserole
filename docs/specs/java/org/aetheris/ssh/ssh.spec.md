# org.aetheris.ssh

## Console family

2. The next console turn calls `AmwpFacade.completeTurn`. That is the cell door: the air-gap checks, the probe, `llmRouter.complete` with the call options, and `poolChat.prompt`. The console does not take the execute semaphore. `execute` calls the same private refusal and the same private routed complete, and the cell still sends its own frames. An empty Ollama id keeps the configured model. An empty Grok id fails with `no model defined`. Pool sends no model id. The shell mints one pool boot for the SSH session. Air-gap stays true. The console turn is not a burner cell. The burner box is stored.
**semantic-key:** PK-10

### Holes

 - `AmwpFacade.execute` never reads `executionTarget`. It always calls `LlmService.complete`.
**semantic-key:** PK-11

### 1. Grok gets a provider, and the stored target stays stored - Make it so. Approved

- `AmwpFacade.execute` reads `execution_target`. The token before `/` selects the provider. An empty stored field selects `ollama` on the outbound body only. That empty field is not written back. `ollama` selects the Ollama provider. `grok` selects the Grok provider. Any other token fails the cell. The stored target is left as written.
**semantic-key:** PK-12

## The report

 - The semaphore of 3 lives in `AmwpFacade`. The pool stays 4.

### Incremental Roux

Agreed 2026-10-20 20:02. `complete` uses the local Prompt and does not read the region back. One facade mutex covers `clear` and every `add` for that execute, and it ends before `complete`. A second execute waits. It does not fail at 50 ms. Ollama parallelism stays the operator's setting. The three slots are a semaphore in `AmwpFacade`. `ChatShell` does not take it.

### Findings

 - The mutex the agreement names is a per-region lock in `AmwpFacade`, taken before `clear` and released after the last `add`, before `complete`. It is not `tryAcquireLock`. It is not an outside hold of the Roux lock.
**semantic-key:** PK-13

### Objects the twelve solutions share - Smoked. Approved

- **Facade push.** One method on `AmwpFacade` for one region. It holds a per-region mutex, calls `clear`, calls every `add`, and releases the mutex before `complete`. The mutex is not Roux's lock and not `tryAcquireLock`.
**semantic-key:** PK-14

## 3. Edits

sendConsoleText is public on AmwpFacade. It writes channel A data on the console sink already stored for that sanitized user. It does not paint the VT100. It does not sanitize a second time.
**semantic-key:** PK-15

## 3. Packages

5. `AmwpFacade`. The probe and the cell call pass the flag. A cell-call 400 of that shape retries once with the flag false, then sends `effort_off:` on `CHANNEL_A` `TYPE_DATA` for that cell before the answer. A second miss is the existing failure reply. One retry. No loop.
**semantic-key:** PK-16

## 3. The edit

`ProbeGate.admit` no longer assumes the region mutex is held. The probe lock is still released before Hello. The region lock is not held across that wait, and it is not taken while the probe lock is held.

## 3. landing

**Mux.** In `AmwpAwareShell.demuxLoop`, a Scope A `TYPE_DATA` frame whose stripped body starts with `shell_model:` or `shell_model_set:` is handled on the shell and is not passed to `facade.handle`. The inner command is used when it is a `ChatShell`. Any other inner command ignores the frame. The reply is Scope A `TYPE_DATA`, nil cell id.
**semantic-key:** PK-17

## 3. Edits

A VT100 pool turn calls `completeTurn`. That path collects text in `TextSink`. `TextSink.thought` is empty, so a pool thought never becomes a stream delta. The pull-down opens only from kind `t` on that delta, and only when the id is a canvas cell. The console id is the nil cell. `findCell` of that id returns null, so the pane stays shut.
**semantic-key:** PK-18

### 3. Grok body and the probe level

- `probeBody` takes this cell's level and puts it on the Hello body. `startGrokProbe` and `ProbeGate.admit` take that level for the leader's body. The gate stays one Hello for the server process, keyed to the API key. A later grok cell does not probe again. Its own call sends its own level.
**semantic-key:** PK-19

## 3. Cause

The answer is one `TYPE_DATA` frame plus one `TYPE_EOF` on A's channel, written by `AmwpFacade.execute` when `complete()` returns. The demux on A keeps reading that channel. With the listener null, the frame is discarded. It is not queued. `applyAssistant` never sees it, so the cell reloaded from the vault still has the prompt and no assistant text.
**semantic-key:** PK-20

## 6. Files

`AmwpFacade` was read and left as it is. It still cancels only on `TYPE_CANCEL`.
**semantic-key:** PK-21

### Cause

User A hits Run. The pair becomes A and that cell. The execute is written on A's connector. `AmwpFacade.execute` calls `llmService.complete` and does not return until Ollama does.
**semantic-key:** PK-22

Casserole does not set `OLLAMA_NUM_PARALLEL`. Ollama serves a loaded model with a small parallel slot and queues the rest. The hello waits in that queue. The client cannot tell a queued hello from a live think. The spinner stays up until B's DATA frame arrives. The mux reply path itself is per session: `AmwpAwareShell` captures that session's output stream in the reply sink, and the client holds frames on the connector whose listener is null.

### Not a hole

The server does not need a second map. `AmwpAwareShell` already captures that session's output stream, so the reply rides the mux that took the execute. `inFlight` keyed by cell id does not collide when the two cells differ. The 07:37 answer map still paints the right cell once the frame is remembered under the right user. The hold queue stays for a connector whose listener is actually absent. After a listener is left aimed, a user switch no longer depends on that queue.
**semantic-key:** PK-23

## 3. Floor the builder must keep

- `AmwpFacade.execute` (`AmwpFacade.java` around the provider branch) is the server door. Today: grok `no-model`, then `grokDoor`, then air-gap, then burner, then Bantha, then Roux, then the grok probe, then `cellHttp` and `llmRouter.complete`. The pool branch replaces the probe and `complete`. The component does not open H2.

## 4. Metal

7. **One process per SSH user.** The first `pool` execute for that user starts the child and keeps it. Later executes on that user reuse it. One prompt is in flight on that process. A second cell for the same user waits. It does not fail and it does not start a second child. The server holds at most three live children. A fourth user waits. That process cap is not the execute semaphore. A pool execute still takes one permit from the semaphore of 3 inside `AmwpFacade.execute` before it reaches the child. The two caps stay separate. §6 names both.
**semantic-key:** PK-24

## Two regions — frozen

> `ChatSession` opens a UUID region. `AmwpFacade.banthaRegion` is `user.aetheris`. `holdRoux` clears that region and adds the prompt.
**semantic-key:** PK-25

### Seats

He selected grok. Status showed model grok/ with an empty id. The next turn threw no model defined from AmwpFacade.completeTurn. The console keeps a stored grok id. An empty id takes the standing row in aetheris-grok.yaml. A missing row takes factory grok-4.3. Status and the turn read that id. The file is not written. A key is not read into the seed. The Rust client still only sends the typed line. No model-id argument. No Rust modal. The chrome still says Ollama. No F-number. Head stays f14fc738.

## 17. Chat first

He selected grok. Status showed model grok/ with an empty id. The next turn threw no model defined from AmwpFacade.completeTurn. The console keeps a stored grok id. An empty id takes the standing row in aetheris-grok.yaml. A missing row takes factory grok-4.3. Status and the turn read that id. The file is not written. A key is not read into the seed. The Rust client still only sends the typed line. No model-id argument. No Rust modal. The chrome still says Ollama. No F-number.

## 25. VT100 stand-up

## 2. The Swing lesson

Java `assemble` opens the working set with `HOST_SYSTEM`, the sentence `You are the Casserole assistant. Be concise and useful.` When the switch is on, the saved mood string and the activate line follow. `AmwpFacade.assembleMessages` replaces the first system line with `chat.system-prompt` when that prompt is non-empty. The mood string and the activate line stay. The checked-in prompt is that same host sentence.
**semantic-key:** PK-26

## 2. The turn

Side move: `AmwpFacade.consoleCarriesThought` attaches the nil-cell sink for that Grok turn. The requirement is the kind `t` frames the pane paints. Ollama keeps its think string. Pool keeps its own sink. The sibling is the Rust pane.
**semantic-key:** PK-27

## 6. The stream
