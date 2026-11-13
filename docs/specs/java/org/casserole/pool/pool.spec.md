# org.casserole.pool

## Parallel lanes and a find bar on View

1. clear and add stay inside the region lock, as one pair, so the region is the last writer's whole list. The lock is released before `runPool`, before the probe, and before `llmRouter.complete`. The wire body stays the Prompt already built. `stillMine` still drops a late reply. The semaphore of 3 stays. One pool prompt per user stays on that child's prompt gate, so two pool members of one group stay in order on that gate. A pool member and an Ollama member overlap. Two Ollama members overlap on this release. Ollama's own queue stays the operator's setting. `num-ctx` stays. A pipe chain stays sequential. No new mux channel. No new pool reason. `PoolChatServer` stays.
**semantic-key:** PK-403

## 2. What stays

Lamina shutdown stays. Roux persistClose stays. The PoolChat hook stays. sshd-core stays 2.19.0. Port, auth, host key, and idle timeout stay. The console door order stays.

## 4. The seat

The package-visible static is rulesHold. PoolMoodRulesTest names the cases. No new public type. No new public method.

## Dependencies pitied

SSHD 2.19.0 stays. NIO2 stays. The group executor is a NoCloseExecutor. On this JDK the stock awaitTermination returns at once after the accept callback kills the IOCP thread. Lamina still drains its pools after sshd.stop returns. Roux persistClose stays. The PoolChat hook stays. No new library.
**semantic-key:** PK-965

## 2. What stays

The semaphore of 3 stays. One pool prompt per user stays on the child gate, so two pool members of one group stay in order on that gate. A pipe chain stays sequential. Ollama's own queue stays the operator's setting. num-ctx stays. The five pool reasons stay. PoolChatServer stays. The canvas search stays the canvas search. The error slide stays off this search. The Grok plan package 7 keeps the hold-through-complete sentences. A dated sentence names this yield.
**semantic-key:** PK-404

## 6. Package 2. Child

- Health is the three words `ready`, `down`, and `auth`. A nested enum on the contract is the return. It carries no secret.
**semantic-key:** PK-405

## 8. Package 4. Frames

The façade implements `PoolChat.Sink` as a private static class. It closes over the reply sink, the cell id, the cell key, and the live token.
**semantic-key:** PK-406

## 1. What this slice is

`PoolChatServer.startProcess` stops passing `--no-auto-update`. Windows stays `cmd.exe /c grok agent stdio`. Any other OS stays `grok agent stdio`. The child can reach `initialize`.
**semantic-key:** PK-407

## 1. What this slice is

`PoolChatServer.onMethod` stops failing the cell on `tool_call` and `tool_call_update`. Those notices are counted with the other noise. A `session/request_permission` request is answered on the same id with `reject_once`, else `reject_always`, else outcome `cancelled`.
**semantic-key:** PK-408

## 4. Metal

4. **Child environment.** The child does not inherit `XAI_API_KEY`. A missing session then fails closed. It does not fall through to the API meter. The component does not set a per-model `api_key`. An operator who puts a key on the default model in `~/.grok/config.toml` leaves this cut. The component does not read that file. The child keeps `USERPROFILE` and `HOME` as the server process has them. Pointing either at the pool directory would hide `auth.json`.
**semantic-key:** PK-409

**Outcome**
The active window stays a clean, flowing region whose head is stable under user control and whose middle can be spilled and restored by pointer. Attention geometry is respected: the strongest attention remains on a fixed frame at the front and on live state at the back, while recoverable reference occupies the weaker middle. Compaction is eliminated.
**semantic-key:** PK-410

## Sink

The Reichenbach Sink builds the transcript a provider receives.
**semantic-key:** PK-411

## 4. Child

The Aetheris core specs are the one set of rules and contracts. Those rules and requirements feed this client. A sentence that cites a Java-specific concern is answered in the working serial. Where Rust cannot carry a rule, because of a language constraint or a missing corollary, that serial writes the alternative for the Rust model and this client's domain, to the best of Rust's ability. The central specs stay the one set. The serial records the alternative and does not copy the set.
**semantic-key:** PK-966

## 3. Proof

PoolMoodRulesTest passed, 2 tests. No pool turn. No SSH. cargo test -p aetheris-client was not re-run.

## A cancelled pool prompt does not fail the resend

Ctrl-C in the SSH pane cancels the pool turn and leaves the child up. The resend uses that same child. When a new request replaces the expected id, the previous expected id is retired. When a new cancel replaces the cancel id, the previous cancel id is retired. A response whose id is retired is ignored. A response whose id is the cancel id is ignored. A response whose id is the outstanding id still finishes the turn. An id this child never issued still fails with `pool payload` and detail `id`. An update whose session id is non-empty and differs from the current session id only increments the note count. The same session still forwards assistant text and thought text. Tool updates on that session still count as notes. A permission request is still answered. Any other child request still fails with the method name and then `session/cancel`. The child stays up. Stderr bytes stay a count. The banner grammar stays. `TextSink.text` still throws a stored failure. Rust stays. The file is PoolChatServer.java. No new public method.
**semantic-key:** PK-995

The installed `grok agent stdio` rejects `--no-auto-update` and exits 2. The 124 bytes on stderr are `error: unexpected argument '--no-auto-update' found` plus the usage line. The help on that command lists `--debug`, `--debug-file`, `--leader-socket`, and `--help`. `grok update` is its own command. The child never reaches `initialize`. The one retry dies the same way. `chunk 0` means no chat text was forwarded. The word `exit` on the banner is the death place. The number 124 is the stderr byte count.
**semantic-key:** PK-1254

The first turn landed in message text. The second turn sent a `session/update` whose kind is `tool_call`. That notice has no id. The handler failed the cell before a later message chunk could land. `notes: 25` is the other updates on that same prompt.
**semantic-key:** PK-1255

A blank host keeps the local child. A set host dials grok agent serve.
**semantic-key:** PK-1271

The pool seat is a process or a WebSocket.
**semantic-key:** PK-1272

The PoolChatServer class is the pool of ACP seats. The class implements PoolChat. It keeps one child for each user. It keeps at most three children. Stdout and stderr run on Lane#POOL_IO. #prompt blocks the caller until the turn finishes. A blank casserole.pool.host keeps the local child. The working directory is ~/.casserole/pool-cwd. Windows starts cmd.exe /c grok agent stdio. Any other operating system starts grok agent stdio. The child environment does not receive XAI_API_KEY. A set host dials ws://host:port/ws on grok agent serve. The JSON-RPC lines stay the same. The serve token is not logged. An idle child is destroyed after 15 minutes. One stdout line is capped at 1 MiB.
**semantic-key:** PK-1274

The contract is one seat, local or remote.
**semantic-key:** PK-1283

