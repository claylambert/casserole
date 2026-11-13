# org.casserole.llm

## Already in the tree

- `LlmRouter.complete(target, prompt)` is the map. There is no registry class. The token before `/` selects the provider. `grok` selects `GrokLlmService.completeFor`. The model id is the text after `/`, trimmed. An empty stored target goes out as `ollama` and is not written back as that word.
**semantic-key:** PK-363
**semantic-key:** PK-363

## The call as it stands

5. `LlmRouter` takes the target. `grok` with an empty model is its own miss, named under Empty model. Any other provider token fails as `unknown provider`. The stored target stays as written. Until that build is in the tree, an empty model still throws the same exception as a missing key, and the message is `grok key missing`.
**semantic-key:** PK-364

## Empty model

This note writes no Java. Until that build is in the tree, `completeFor` still throws `KeyMissingException` for an empty model, and the facade still sends reason `failed` and message `grok key missing`.
**semantic-key:** PK-365

### Readable text on this cell

`GrokLlmService` writes `stream` false. Jackson reads `choices[0].message.content` when that node is a string. It does not read `reasoning_content`. Connect stays 10 seconds. He named 1800 seconds. The client in the tree still uses 120 seconds until that number is built. The readable thinking text arrives only once `stream` is true. Those deltas are not sent, and they are not painted.
**semantic-key:** PK-366

A non-2xx body is `Grok HTTP` plus the status plus at most 180 characters, with the key text replaced by `[redacted]`. A transport miss is `Grok` plus the exception name plus the redacted detail. That string is the slide text. The cell stores `FAILED`. The reply box is not replaced. The status line shows `failed`.
**semantic-key:** PK-367

### What this product does

`SpringAiOllamaLlmService.getId()` returns `ollama`. `complete()` calls `ChatModel.call` once. When `numCtx` is greater than 0 it builds `OllamaChatOptions` and calls `setNumCtx` only. It does not set `think`. It does not stream. A null response throws `Ollama returned no response.`
**semantic-key:** PK-368

## Outstanding

- **Ollama think is a consideration.** The separate section names `think`. `SpringAiOllamaLlmService` does not set it and does not stream. The host model was not probed. 2026-10-21 23:27 supports Ollama thinking on the same control.
**semantic-key:** PK-369

## Out of this slice

- The 14:45 filing wrote no Java. The committed value at 14:53 is blank. The 20:03 note names the empty-model banner and writes no Java. The live POST was not made. No row is answered. The factory id is `grok-4.3`. The request cap is 1800 seconds. The tree still uses 120 seconds. The engine-word repaint is named and is not in the tree. Compile passed. The live POST was not made.
**semantic-key:** PK-960

## Out of this slice

- Ollama `think` is named in its own section for consideration. `SpringAiOllamaLlmService` does not set `think` and does not stream. `num_ctx` stays the existing Ollama option. `0` still omits it.

Ollama `think` is a separate section for him to consider. `think` is `true`, `false`, `null`, or a string taken exactly from `POST /api/show`. Chat returns `message.thinking` apart from `message.content`. `SpringAiOllamaLlmService` does not set `think` and does not stream. The host model was not probed.
**semantic-key:** PK-370

## 3. Floor

- Grok JSON and SSE JSON objects stay on the Jackson mapper already in `GrokLlmService`. Internal TOON and stay on their readers.

## 5. Package 1. Doors

4. Empty model is checked in `dispatchCell` and in `GrokLlmService.completeFor`, before `readKey`. Target `grok` or `grok/` with nothing after the slash. Reason `no-model`. Message `no model defined`. The cell stores `FAILED`. The status line shows the banner. The key is not consulted. An empty key with a model present stays `grok key missing`. Add a typed exception for the empty model. The facade maps it to reason `no-model`. Do not reuse `KeyMissingException` for that case.
**semantic-key:** PK-371
**semantic-key:** PK-371

## 9. Package 5. Thinking

4. Grok on reads the body as SSE lines. A line is `data:` plus a JSON object, then `data: [DONE]`. `delta.reasoning_content` goes out as kind `t`. `delta.content` goes out as kind `c`. Both ride `CHANNEL_STREAM`. Jackson reads each JSON object with the mapper already in `GrokLlmService`.
**semantic-key:** PK-372

8. `LlmProvider.complete(Prompt)` stays. The router already special-cases `grok.completeFor`. It special-cases the Ollama think flag the same way.
**semantic-key:** PK-373

## 9. P6 — Provider SPI + swap

**New:** `org.casserole.llm.LlmProvider` (id, complete, optional cancel). `SpringAiOllamaLlmService` is the only impl.
**semantic-key:** PK-374
**semantic-key:** PK-374

## 6. Package 3. Grok on that router

**Done means:** A cell whose target is Grok completes through the same `LlmRouter`. Ollama still sets `num_ctx`. Grok does not. A missing key fails that cell. An unknown provider fails that cell and leaves the stored target.
**semantic-key:** PK-375

## 6. Package 3. Grok on that router

1. Register a Grok `LlmProvider` on the router from package 2. There is no registry class.
**semantic-key:** PK-376

### WP-G — SPI + swap (S1-15)

- `org.casserole.llm.LlmProvider`. Ollama only. `swap` via existing HTTP keep_alive / stop.
**semantic-key:** PK-377

## Order

| # | Order | Already locked | Lock |
|---|---|---|---|
| 1 | Search rides in package 1 | Package one is `LEX-FILTER`. | |
| 2 | Version 2 columns land in that same vault touch | Open migrates, then selects. Version 2 adds `group_id` and `execution_status` together. | |
| 3 | Package 2 is the execute seams plus `MOOD-LAYER` | `MOOD-LAYER` follows the pin. `LlmRouter.complete(target, prompt)` is the map. Only Ollama is registered until Grok. | |
| 4 | Packages 3 through 8 stay in the order below | Each row's behavior is locked. | 2026-10-20 23:38. Grok, groups, pipes, export, crosstalk, then tool hooks. |
| 5 | The tool column waits | Tool use is off. The hooks stay. The allowlist is empty. | 2026-10-20 23:38. `tool_calling_enabled` is not a version 2 column. |

## Package 2. Execute seams and MOOD-LAYER

- `LlmRouter.complete(target, prompt)`. This package registers Ollama only. The caller supplies the `Prompt`. The router does not walk the vault, read Roux, or prepend mood.
**semantic-key:** PK-378

## A blank API session tries once more

An API session that returns no text, and has shown nothing, tries the same request once more. A stop is not tried again. Text already shown is not tried again. The second miss stays grok empty content. Rust sends the execute frame. The server owns the session. No new public type. The private type is EmptyContent. No new public method. No new JUnit class.

### Seats

- **** Two execute paths share one `LlmService`. One Bantha region is cleared on every Aetheris execute. Parallel cells will step on that region. Do not invent a second gearbox to hide it.
**semantic-key:** PK-961

### Holes

 - `SpringAiOllamaLlmService` is the only `LlmProvider`, and its id is `ollama`. `complete` sets Ollama `num_ctx` whenever the integer is positive.
**semantic-key:** PK-379
**semantic-key:** PK-379

### 1. Grok gets a provider, and the stored target stays stored - Make it so. Approved

- The SPI is `LlmProvider`. There is no registry class in the tree. The map is `LlmRouter.complete(target, prompt)`. The caller supplies the `Prompt`. Tab A `swap` stays. The router does not replace `swap`.
**semantic-key:** PK-962

## The report

- **Grok.** `Final gap solutions` 10. The map is `LlmRouter`. The SPI is `LlmProvider`.
**semantic-key:** PK-380
**semantic-key:** PK-380

 - Call `LlmService.complete`.

## Floor the plan stands on

`LlmProvider` is the SPI. Ollama is wired. A registry class is not in the tree. `LlmRouter` is the new map.
**semantic-key:** PK-381
**semantic-key:** PK-381

### Grok and the provider router

Agreed 2026-10-20 20:02. One provider method takes the working set, the target, and the region id. Tab A passes `{user}`. Aetheris passes `{user}.aetheris`. The caller supplies the turns. Ollama options stay inside the Ollama provider. The router does not build the prompt.

### Findings

 - Each of those calls takes `region.lock` and releases it in its own `finally`. Another execute can `clear` between them. `complete` does not read the region back. `SpringAiOllamaLlmService.complete` uses the `Prompt` it was given. That part of the agreement matches the tree.
**semantic-key:** PK-382

### Objects the twelve solutions share - Smoked. Approved

- **`LlmRouter`.** One map from `LlmProvider.getId()` to that provider. One method: `complete(target, prompt)`. The caller built the `Prompt`. The router does not open H2, does not read Roux, and does not prepend a mood line.
**semantic-key:** PK-383
**semantic-key:** PK-383

## 2. What stays

History recall stays. CallStopped stays a cancel. The probe stays one shot. The message text stays grok empty content. No new vault column. No new channel. No new AMWP type. No new library. No Cargo.toml edit. No new public Java type. The private type is EmptyContent. No new public method. No new JUnit class. No decision block on this plate.

## 4. The seat

completeFor catches that empty result and sends the same request once more. A cancelled slot becomes CallStopped and is not sent again. A chunk already given to the sink is not sent again. The second empty result is still grok empty content.
**semantic-key:** PK-384

## 6. Package 2. Holder and the think key

- `SpringAiOllamaLlmService.complete(Prompt)` stays and calls the wide method with an empty model and empty think.
**semantic-key:** PK-385

## 8. Package 4. Ollama id and ThinkLevel

- `LlmRouter.complete` reads the id after `ollama/` and passes it into `SpringAiOllamaLlmService`.
**semantic-key:** PK-386
**semantic-key:** PK-386

## 3. Packages

4. `GrokCallOptions`, `LlmRouter`, `GrokLlmService`. The flag false leaves `reasoning_effort` out of `requestBody` and `probeBody`. `rejectedEffort` matches a 400 whose text contains `does not support parameter` and either `reasoningEffort` or `reasoning_effort`. `startProbe` retries that refusal once with the flag false.
**semantic-key:** PK-387
**semantic-key:** PK-387
**semantic-key:** PK-387

## 3. landing

**Next turn.** `runLlmTurn` reads the model and the think string under the shell lock before `complete`. The target is `ollama/` plus the model when the model is non-empty, and `ollama` when it is empty. The call is the six-argument `complete`, with a new `GrokCallOptions`, that think string, and null slot and sink. A turn already inside `complete` keeps the values it read.
**semantic-key:** PK-388

# Aetheris pre-plan 001

He recalled the send and sent it again, and it worked. He wants a retry for an API session that comes back empty like that. Java and Rust both use that server session.
**semantic-key:** PK-963

## Dependencies pitied

completeFor calls completeStream or completeOnce. An empty joined content throws grok empty content. CallStopped is a cancel. A chunk already handed to the sink has been shown. The probe path is not this session. Rust sends an execute frame. It does not open the x.ai call. The server owns that call. One retry on the server covers both desks. A second retry in the crate would double the attempts.
**semantic-key:** PK-389

## Dependencies pitied

No new public type. The private type is EmptyContent. No new public method.

### 2. Session holder and the think key

- `complete(target, prompt)` passes empty think. `SpringAiOllamaLlmService.complete(Prompt)` stays and calls the wide method with an empty model and empty think.
**semantic-key:** PK-390

### 4. Ollama model id and ThinkLevel

- `LlmRouter.complete` reads the id after `ollama/` and passes it into `SpringAiOllamaLlmService` on the same `OllamaChatOptions` the think path already builds. The call is `setModel` on that instance.
**semantic-key:** PK-391

### Cause

Both executes share one Spring bean. `AmwpFacade` is one `@Component`. `inFlight` is one map, keyed by cell id, not by SSH session. The two cell ids differ, so the second execute is not dropped. `Lane.LLM_EXEC` has four threads, so both calls sit inside `SpringAiOllamaLlmService.complete` together. That method is one `ChatModel`. Both SSH shells are created with the same `AmwpFacade` and the same `LlmService`. Chat on the VT100 uses that same `complete`.
**semantic-key:** PK-392
**semantic-key:** PK-392

### 1.3 Constraints the owner has stated (binding)

7. **Spring AI is isolated** behind `LlmService`. The shell talks Prompt in / ChatResponse out.
**semantic-key:** PK-393

### 4.1 Boundary

`LlmService` is the **only** Spring AI type the rest of the app is supposed to depend on. Commands do not call `ChatModel`.

## `casserole.grok`

| Key | Type | Default | Effect |
|---|---|---|---|
| `api-key` | string | empty | Grok bearer token. Blank is a miss. `GrokLlmService` reads this first, then `XAI_API_KEY`, then `~/.aetheris/grok.key`, then `~/.casserole/grok.key`. The Grok call does not send `num_ctx`. |
**semantic-key:** PK-394

## 2. Assessment

Chat from the VT100 shell and chat from Aetheris both enter through `SpringAiOllamaLlmService.complete`. That is the chat request layer. A positive value is set on that prompt's options, and Spring merges it with the model name already configured.
**semantic-key:** PK-395

## 3. Resolution

`SpringAiOllamaLlmService` reads it at startup. On each `complete`, when the value is positive, the prompt carries `OllamaChatOptions.numCtx`. Spring writes `options.num_ctx` on that chat request.
**semantic-key:** PK-396

## 3. Floor the builder must keep

- `LlmRouter.providerId` already returns the token before `/`, lower case. Empty stays `ollama`. A stored `pool`, `pool/`, or `pool/anything` is provider `pool`. `complete` is not taught `pool`. A pool target that reached `complete` would still throw `unknown provider`. The façade branches first.
**semantic-key:** PK-397

## 4. Metal

1. **Target.** The execution target is the word `pool`. `LlmRouter.providerId` already takes the token before `/`. Empty stays `ollama`. `grok` stays the API provider. Focus loss stores a typed `pool/something` as the bare word `pool`, so the saved target has no suffix. §9 and §10 row 6 name that rewrite. The Model control lists Pool beside Ollama and Grok. The header line reads `pool`. The tip says the cell chats on the Heavy pool and does not use the API key. The slide-in is §9.
**semantic-key:** PK-398

### 0.1 TOON in the LLM prompt vs unmarshal (question 9)

| Path | Encoding | Why |
|---|---|---|
| Off-heap / spill file | TOON inside length-prefixed frames | Density, fixed pages, monotonic index |
| `message list` / `status` | Unmarshal to original role + text | Operators must read chat, not notation |
| Spring `ChatMemory.get()` / `Prompt` | Unmarshal to `SystemMessage` / `UserMessage` / `AssistantMessage` | Drop-in contract. Advisors and Casserole `LlmService` already consume `Message` |
| Compact prompt (later) | Optional: one TOON **table** of *prior* turns as a single system/user block + latest utterance as plain text | Token savings live here, not in per-message TOON stuffed into role-separated Messages |
**semantic-key:** PK-399
