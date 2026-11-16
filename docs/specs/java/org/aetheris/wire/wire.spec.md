# org.aetheris.wire

## Already in the tree

- The shell reads `--aetheris-yaml`, then `./aetheris.yaml`, then `~/.aetheris.yaml`. Those are not one shared file. The shell status word is its own file. `SessionFacts` echoes the server. A reported mismatch blocks the shell's cloud send. The server still enforces its own value.
**semantic-key:** PK-148

## 5. Package 1. Doors

5. `ExecuteBody` gains `burnerCloud`, default false. Encode writes `burner_cloud: true` only when set. Decode reads that line. Unknown lines stay ignored.
**semantic-key:** PK-149

## 9. Package 5. Thinking

1. `ExecuteBody` gains `thinkStream`, default false. Encode writes `think_stream: true` only when set. Decode reads it. No vault column.
**semantic-key:** PK-150

## 11. Package 8. Tool hooks only

1. Add channel `0x0004` on `AmwpConstants`. Do not allocate `0x0100`.
**semantic-key:** GK-1348

## Package 2. Execute seams and MOOD-LAYER

- `AmwpCodec.fits` counts the 24-byte prefix against the 1 MiB cap.
**semantic-key:** PK-151

## num-ctx

Named default is 4096. 2026-10-20 23:14. The active integer in `spring.ai.ollama.chat.options.num-ctx` is 4096 as of 2026-10-20 23:23. The working tree before that write was 32768. The serial's earlier 2048 was a stale study. The commented 8192 line stays. `0` still means the server omits `num_ctx`. `SessionFacts` reports the integer. Restart the server to pick the new integer up. This page does not treat that yaml edit as a later build step.
**semantic-key:** PK-932

### Holes

 - `AmwpConstants` stops at `CHANNEL_STREAM` `0x0003`. There is no `0x0004`.
**semantic-key:** PK-152

 - `AmwpToon` writes `role` and `text`.

 - `AmwpCodec.encode` throws `AMWP payload exceeds 1 MiB`.
**semantic-key:** PK-153

### 3. MCP gets a channel, a lane, and a closed default - Make it so. Approved

- `AmwpConstants` gains channel `0x0004`. `writeAmwp` takes the channel. Scope A execute, and today's other shell writes, stay on `0x0002`. Tool frames use `0x0004`. Day-one group replies stay on `0x0003`. `0x0100` stays the constitution's later range and is not allocated in this slice. Tools do not use it.
**semantic-key:** PK-933

### 8. Over 1 MiB fails the cell in a banner - Make it so. Approved

- `AmwpCodec.fits` measures the payload the codec will see, prefix included. The walker and the reply path both call it. Over the cap, the cell is `FAILED` with `reason: too-large`, and `encode` is not asked to throw. The panel stays up.
**semantic-key:** PK-934

## The report

 - `FAILED` stops the pipe. `AmwpCodec.fits` measures the payload, prefix included.

### Objects the twelve solutions share - Smoked. Approved

- **`SessionFacts`.** A TOON body on the existing `TYPE_INIT` reply. Two fields. `air-gap` is `true`, `false`, or `unset`. `num-ctx` is an integer. `0` means the server omits the parameter. An empty Init body means the server did not report. The client must not treat that empty body as `0`.
**semantic-key:** PK-154

- **Error reason.** The existing `TYPE_ERROR` frame. The body becomes TOON from `AmwpToon`: `reason` and `message`. Reasons are `cell-busy`, `failed`, `air-gap`, `engine-down`, and `too-large`. A body that is not that TOON still displays as a failed banner, so a Stage 1 error frame keeps working. No new AMWP type.
**semantic-key:** PK-155

- **`AmwpCodec.fits`.** One method. It returns whether `FRAG_PREFIX_BYTES` plus the body is within 1 MiB. The walker and the reply path both call it. No second size table.
**semantic-key:** PK-156

## 6. Package 2. Holder and the think key

- `ExecuteBody` gains `String think`. Encode writes `think:` when the value is non-empty, through the existing `esc`. Decode reads the string with `unesc`. Do not call `truth()` on it. Absent and empty mean off. `true` is the checkbox. Any other value is the level. Off does not send `false`.
**semantic-key:** PK-157

## 3. Packages

3. `AmwpToon`. `ExecuteBody.sendReasoningEffort` defaults true. Encode writes `send_reasoning_effort: false` only when false. Decode reads that line. Absent stays true.
**semantic-key:** PK-158
**semantic-key:** PK-158

## 2. Out of this build

- A thinner AMWP execute. The frame still carries the full working set, and `AmwpCodec.fits` stays the frame check.
**semantic-key:** PK-159

## 3. The edit

The panel mints one `poolBootId` at construction, a random UUID. The panel is built with the desktop, so a new app start mints a new id and an SSH reconnect on the same window keeps the id. `assemble` writes it on `ExecuteBody.poolBoot` when the provider is `pool`. Other providers leave the field empty.
**semantic-key:** PK-160

## 5. Gotchas the code keeps

The frame can still fail `AmwpCodec.fits` on the full working set. The tail does not shrink that frame.
**semantic-key:** PK-161

### 2. Session holder and the think key

- `ExecuteBody` gains the optional string `think`. Absent and empty mean off. `true` means the checkbox with no level. Any other value is the level. Encode writes the key when the value is non-empty. Decode reads the string. The level does not pass through the boolean reader. Off does not send `false`.
**semantic-key:** PK-162

### 3. Grok body and the probe level

- `AmwpToon.encodeExecute` writes `reasoning_effort` when the value is non-empty. The unset-token skip leaves.
**semantic-key:** PK-163

## 8. Package 4. Frames

- `done` with an empty reason, while `stillMine`: one `TYPE_DATA` of the message bytes, then `TYPE_EOF`, both on `0x0003`, with the cell id the execute already carries. The fit check on `TYPE_DATA` stays the one `execute` already uses (`too-large` when `AmwpCodec.fits` is false).
**semantic-key:** PK-164

## 4. Metal

9. **Bytes back.** As chunks arrive, the façade sends `TYPE_DELTA` on channel `0x0003` with the existing kind byte, and only while the live token is still this execute. When the prompt result arrives, the façade sends one `TYPE_DATA` of the concatenated message, then `TYPE_EOF`, both on `0x0003`, with the cell id the execute already carries. The client stores `TYPE_DATA` as the cell answer. A failure does not store the partial chunks. The live assistant area is put back to the text the cell held before the run. A failure sends one `TYPE_ERROR` on channel `0x0002` through `replyReason`. The body is the TOON `reason` and `message` that `AmwpToon.encodeError` already writes. §11 names the five reasons and the slide. A pool chunk that does not fit the 1 MiB cap fails the cell with `too-large` on that same path. No token and no key is in the text.
**semantic-key:** PK-165

## 5. landing

10. A frame that misses `AmwpCodec.fits` sets `too-large` and keeps the mood triple.
**semantic-key:** PK-166

## Channel cap — frozen

The sink omits middle turns so the transcript meets the managed size, and the send still goes. `AmwpCodec.fits` still refuses a frame that is too large for the channel. The sink does not drop the mood triple to pass that byte cap.
**semantic-key:** PK-167

## 2. The canvas

The body rule matches `AmwpCodec.stripFragPrefix`. A payload of 24 bytes or more yields the bytes after the prefix. A shorter payload is kept whole. An empty body is not queued.
**semantic-key:** PK-168
**semantic-key:** PK-168

Init is kept apart from data and error. Those numbers are the ones `AmwpConstants` already names. Keepalive stays off the plate. A frame on any other channel stays off the grid and off the plate.
**semantic-key:** PK-169

### Seats

| Seat | Line |
|---|---|
| | The pipe is the slice. |
| | This page is the home. The crate follows this page. |
| | No new crate. The same `egui` window. The same read thread. One queue carries Scope A bodies to the UI. The fragment prefix is the 24 bytes `AmwpCodec` already writes. No new AMWP type. The library stays untouched. No Turso. |
| | The proof lines below are the landing, in that order. |
| Face | The plate stays dark until a body arrives. No heading is painted over it. |
| | The body is the check. The notebook is the foundation, and it waits. |
| | Sheathed. |

## 2. The pipe

The types that queue a body are init, data, and error. Those numbers are the ones `AmwpConstants` already names. Keepalive stays off the plate. A frame on any other channel stays off the grid and off the plate.
**semantic-key:** PK-170

The SessionFacts class holds the facts from an Init body. An empty body leaves reported false. That absence is not the integer 0.
**semantic-key:** PK-1225

The Turn class is one row in the working set. The role field starts as user. The text field starts empty.
**semantic-key:** PK-1226

- **Code this page reads:** `[src/main/java/org/casserole/llm/GrokLlmService.java](../../../../../../src/main/java/org/casserole/llm/GrokLlmService.java)`, `[src/main/java/org/casserole/llm/LlmService.java](../../../../../../src/main/java/org/casserole/llm/LlmService.java)`, `[src/main/java/org/casserole/config/CasseroleProperties.java](../../../../../../src/main/java/org/casserole/config/CasseroleProperties.java)`, `src/main/resources/application.yaml`, `[src/main/java/org/casserole/llm/LlmRouter.java](../../../../../../src/main/java/org/casserole/llm/LlmRouter.java)`, `[src/main/java/org/aetheris/ssh/AmwpFacade.java](../../../../../../src/main/java/org/aetheris/ssh/AmwpFacade.java)`, `[src/main/java/org/aetheris/swing/AetherisNotebookPanel.java](../../../../../../src/main/java/org/aetheris/swing/AetherisNotebookPanel.java)`, `[src/main/java/org/aetheris/swing/AetherisStage2.java](../../../../../../src/main/java/org/aetheris/swing/AetherisStage2.java)`, `[src/main/java/org/aetheris/config/AetherisYaml.java](../../../../../../src/main/java/org/aetheris/config/AetherisYaml.java)`, `[src/main/java/org/aetheris/wire/AmwpCodec.java](../../../../../../src/main/java/org/aetheris/wire/AmwpCodec.java)`, `[src/main/java/org/aetheris/wire/AmwpConstants.java](../../../../../../src/main/java/org/aetheris/wire/AmwpConstants.java)`, `[src/main/java/org/aetheris/wire/AmwpToon.java](../../../../../../src/main/java/org/aetheris/wire/AmwpToon.java)`, `[src/main/java/org/casserole/app/ui/ssh/SshClientProperties.java](../../../../../../src/main/java/org/casserole/app/ui/ssh/SshClientProperties.java)`. The key reference is `application-yaml.md`.
**semantic-key:** PK-1299
**semantic-key:** PK-1299
**semantic-key:** PK-1299
**semantic-key:** PK-1299
**semantic-key:** PK-1299
**semantic-key:** PK-1299
**semantic-key:** PK-1299
**semantic-key:** PK-1299
**semantic-key:** PK-1299
**semantic-key:** PK-1299
**semantic-key:** PK-1299

- **Smoked:** `Final gap solutions` 1 and 2. The server reads `~/.aetheris.yaml` through `AetherisYaml`. A missing key is `unset`, and `unset` refuses cloud. Explicit `false` allows it. `SessionFacts` on `TYPE_INIT` is the echo. An empty Init body is not `num-ctx: 0`.
**semantic-key:** PK-1337

> Hannibal puffed the pipe check and lit the Cigar. Book'em Dano. B.A. approves. The plate shows the Scope A body. The foundation waits.
**semantic-key:** PK-1387

`AmwpClientListener` is a public type in this package.

`AmwpFrame` is a public type in this package.

`AmwpInitEater` is a public type in this package.

`AmwpPeekStream` is a public type in this package.
