# org.casserole.lamina

## Child pages

<pre>
org.casserole.lamina/
└── <a href="spring/spring.spec.md">org.casserole.lamina.spring</a>
</pre>

## 10. Package 6. Cancel

1. The cell prompt call is `sendAsync`. The future is held beside that cell's live token. `TYPE_CANCEL` cancels that future and removes the token, and still cancels the `LaminaHandle`.
**semantic-key:** PK-352

## 5. P2 — Lamina engine

| Type | Job |
|---|---|
| `Lane` | Enum: `SSH_IO`, `LLM_EXEC`, `GENERAL`. Comment stubs only: `TOOL_EXEC`, `ROUX_IO` — **do not allocate pools** |
| `LaminaTask` | Named work: lane, name, `Runnable` (or `run()` on the task itself) |
| `LaminaHandle` | Cancel / isDone for in-flight work (wraps `Future`) |
| `Lamina` | `submit(LaminaTask) → LaminaHandle`; `shutdown()` |
| `LaminaEngine` | `ThreadPoolExecutor` per live lane. Core Java threads. **Not** virtual |
| `LaminaMetrics` | Exists; **default OFF**. When ON: log-tail counters (submit/complete/fail/wait). No OTel |
| `LaminaProperties` | Optional `casserole.lamina.metrics` boolean default false; pool sizes code-default if yaml omitted |
**semantic-key:** PK-353
**semantic-key:** PK-353
**semantic-key:** PK-353
**semantic-key:** PK-353

### 6.1 `SshServer` — stop extending `Thread`

**Target:** `SshServer` is a `@Component` that is **not** a `Thread`. `start()` submits a `LaminaTask` on `SSH_IO` that: set up MINA, `sshd.start()`, then wait/loop until stop (same `running` flag). `@PreDestroy` sets `running=false`, stops MINA, does not leak the lane.
**semantic-key:** PK-354

### 6.3 `ChatShell`

**Target:** inject `Lamina`. Read loop = `LaminaTask` `SSH_IO` named `casserole-chat-shell-{user}`. LLM complete = `LLM_EXEC` named `casserole-llm-{user}`. Hold `LaminaHandle`s; `destroy` cancels them (same as today’s `Future.cancel(true)` + interrupt).
**semantic-key:** PK-355

## 8. P5 — AMWP

**Execute frame:** length-prefixed; body TOON (working set + `cell_id` + `execution_target` + knobs). One in-flight per `cell_id`. Cancel → LaminaHandle.cancel best-effort.
**semantic-key:** PK-356

### Holes

 - `Lane` is `SSH_IO`, `LLM_EXEC`, and `GENERAL`. The comment names `TOOL_EXEC`. The enum does not. A task on a missing lane throws `no pool`.
**semantic-key:** PK-357

### 3. MCP gets a channel, a lane, and a closed default - Make it so. Approved

- `Lane` gains `TOOL_EXEC`. The pool is its own pool, so a tool wait does not occupy `LLM_EXEC` and an Ollama call does not occupy the tool pool. Day-one size is 1. A second tool task waits. It does not fail. `LLM_EXEC` stays 4. Tool use stays off until tool integration.

## 3. Floor the builder must keep

- `Lane` is `SSH_IO`, `LLM_EXEC` 4, `GENERAL` 2, `TOOL_EXEC` 1. `LaminaEngine` builds `SSH_IO` with `Executors.newCachedThreadPool(namedFactory("ssh-io"))`, and those threads are named `lamina-ssh-io-N`. `POOL_IO` follows that cached pattern with factory name `pool-io`.
**semantic-key:** PK-358
**semantic-key:** PK-358

### 2. Child

- `Lane` gains `POOL_IO`. The stdout reader lives there for the life of the child. A second task on that lane drains stderr and drops the bytes.
**semantic-key:** PK-359

## 3. The lamina decision

`Lane` gains `POOL_IO`. The engine map gains the cached pool beside the four that exist. No other lane changes size.
**semantic-key:** PK-360

## 23. Lamina (LTEM) `[STAGE 0]`

**Lanes `[LOCK]` starters:** `ssh-io`, `llm-exec`, `general`. Later: `tool-exec`, `roux-io`.
**semantic-key:** PK-361
