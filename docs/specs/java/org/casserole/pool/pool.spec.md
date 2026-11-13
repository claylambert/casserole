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

## 3. Proof

PoolMoodRulesTest passed, 2 tests. No pool turn. No SSH. cargo test -p aetheris-client was not re-run.
