# org.aetheris.vault

### What the room already does

`CellRecord` stores `executionTarget`. It has no effort field and no stream field.

## 12. Rust row

Side move. `VaultStore.replaceCanvas` wipes the canvas and seats the copied cells, with no leftover empty cell when the session has turns. The requirement is the cleared canvas. The sibling is `clearCanvas`, which leaves one empty cell. An empty list still leaves one empty cell.
**semantic-key:** PK-124

## 2. Floor the build must keep

- New Java carries `// ` with an existing key only. Is the router. Is the codec. Is the vault migration. Stays on the lane and comes off `VaultSql` and `VaultStore` when those files are edited.

## 4. Package 1. LEX-FILTER

5. Search door in the same vault edit. A notebook field calls `VaultStore.search`. Hits are the open canvas. Typed `%` and `_` are literals. The engine wraps the needle for a contains match. An empty needle returns no rows. A hit selects and does not change state.
**semantic-key:** PK-125

7. `CellState.fromToken` does not throw. Null and blank stay `INCLUDED`. `GHOST` stays `EXCLUDED`. Any other unknown non-blank token sets `CellRecord.readable` false. That card paints a load error. The walker skips it. The rest of the canvas opens.
**semantic-key:** PK-126
**semantic-key:** PK-126

9. When `VaultSql` and `VaultStore` are edited, take off those files.

## 5. Package 2. Execute seams and MOOD-LAYER

3. `LlmRouter` is one map and one method, `complete(target, prompt)`. It does not accept a `CellRecord`. The caller supplies the `Prompt`. The router does not walk the vault, read Roux, or prepend mood. This package registers Ollama only. An empty target selects `ollama`. The token before `/` selects the provider. Unknown id throws a typed failure the facade turns into `TYPE_ERROR` reason `failed`. A comment forbids a `CellRecord` parameter.
**semantic-key:** PK-127

## 3. Side move

`VaultStore.replaceCanvas` wipes the canvas and seats the copied cells. The sibling is `clearCanvas`, which leaves one empty cell. An empty list still leaves one empty cell.
**semantic-key:** PK-128

### 2.2 Out

| Item | Why |
|---|---|
| `select model`, `list models` | `swap` and `ollama list` stay. |
| Fifth `CellState`, or collapse skipping the working set | Stage 2 lock. `E` remains the way out of context. |
| Second copy of the state buttons | Retired on 002. |
| Full-word button labels | Retired on 002. |
| Import button on the header | Clutter. The menu is the door. |
| User text as the collapsed brief | Retired on 003. |
| VT100 `clear` from the footer button | Different memory. |
| Stage 2 canvas, Grok, MCP `0x0004`, Graph | Not this slice. |
| | Those stamps stay. |

### 2.2 Out

| Item | Why |
|---|---|
| Formal plan and Java | Wait until names each. |
| Cutting a new seed-kit | N6. He cuts it when he says he is ready. |
| Rewriting prod 1.0 | Frozen. |
| End-scroll on New turn and Clear turns | S3. Those two still bring the top of that card into view. |
| End-scroll on a header click or `IEPO` | S4. The hold keeps his place. |
| A row ceiling | S5. Minimum is 1. No maximum. |
| Per-vault row count or chooser memory | S7. One Shell preference. |
| Painted inspector | N5. The inspector stays one text area. |
| A combo that swaps cards and leaves the VT100 session alone | S10. The pull-down selects the session. Mux stays up. |
| Crosstalk, or a copy of one user's cells into another vault | Comms stay put unless he says otherwise. |
| `select model`, `list models` | |
| Fifth `CellState` | Stage 2 lock. |
| Stage 2 rows, Grok wiring, MCP `0x0004` | Not this slice. The notes slide into those rows later. §5.1 records the fit. |
| `.aetheris` export using the N3 chooser memory | S8. That door stays its own door. |
**semantic-key:** PK-129

## Package 1. LEX-FILTER

- A notebook field calls `VaultStore.search`.
**semantic-key:** PK-130

- `VaultSql.SELECT_CELLS` does not name `group_id` before the version step.
**semantic-key:** PK-131

- An unknown non-blank state token sets `CellRecord.readable` false and does not throw. That card paints a load error. The walker skips it. The rest of the canvas opens.
**semantic-key:** PK-132

## Collapse flag

> `collapsed` is a boolean on the cell record, default false. It is not a fifth `CellState`.
**semantic-key:** PK-133

Collapse is a view flag, not a context state. `CellState` stays the four values `INCLUDED`, `EXCLUDED`, `INPUT_ONLY`, `OUTPUT_ONLY`. Stage 2 already locks "no fifth state" for the pin and the ghost. A collapsed cell that is `INCLUDED` still walks into the working set. The `E` button remains the way out of context. Putting collapse on the enum would make Stage 2's promote-to-INCLUDED rule and the ghost paint lie.
**semantic-key:** PK-928

## 0. Dispositions from 002 2026-10-18 12:33

| Ask | Disposition |
|---|---|
| Collapse | One card. The header buttons stay. No second copy of the state buttons. `collapsed` is a boolean on the cell record, default false. It is not a fifth `CellState`. While collapsed, the body hides and a brief of the contents is in focus: the text from the first character, one line, or two lines if the bar height allows. User text first. If the user text is empty, the assistant text. Plain truncation. No summary engine. A collapsed `INCLUDED` cell still enters the working set. `E` stays the way out of context. Says, I meant the brief of the output of a cell. The input is not relevant when included. |
| Context letters | The buttons stay `I`, `E`, `P`, `O`. Each one gains a tooltip: Included, Excluded, Input only, Output only. Full words are not the labels. |
| Clear turns | A footer button clears the Aetheris canvas vault back to one empty `INCLUDED` turn and writes the vault. VT100 `clear` is not called. The SSH session stays. Burner cells are not spared. |
| `select model` / `list models` | He remembered `swap`. The ask waits. `lost models` was a typo for `list models`, and that command waits with the rest of the ask. `ollama list` and `swap` stay as they are. |
| Text color | Input foreground `#00FF66` on `#1A1A1A`. Border stays `#3CB371`. Output foreground `#FFFF55`, the Swing stand-in for VT100 SGR 93. The View modal uses that yellow. An `EXCLUDED` cell stays gray in both areas. |
| View bounds | One client preference, global, not per turn, not on the cell record. Save x, y, width, and height when the dialog closes. Next open, including after a restart, uses it. Off-screen coordinates fall back to 720 by 480, centered. |
| Focus hold | Before a header control rebuilds the pane, remember the viewport view Y and the card Y. After the rebuild, restore `cardY - viewY` and keep focus on that cell. View does not rebuild. Clear-all may scroll to the new empty turn. |
| Import | Right-click on a cell opens a context menu. The item imports one UTF-8 text file and replaces that cell's user text. No import button on the header. The assistant area never receives the file. An `OUTPUT_ONLY` cell refuses. No focused input cell refuses, with a status line. A NUL in the first 8 KiB refuses. Cap is 1 MiB. The cell state does not change. The import does not push the pipe. |

## 1. Dispositions carried from 003

| Ask | Disposition |
|---|---|
| Collapse | One card. The header buttons stay. No second copy of the state buttons. `collapsed` is a boolean on the cell record, default false. It is not a fifth `CellState`. While collapsed, the body hides and the brief in §0 is in focus. A collapsed `INCLUDED` cell still enters the working set. `E` stays the way out of context. |
| Context letters | The buttons stay `I`, `E`, `P`, `O`. Each one gains a tooltip: Included, Excluded, Input only, Output only. Full words are not the labels. |
| Clear turns | A footer button clears the Aetheris canvas vault back to one empty `INCLUDED` turn and writes the vault. VT100 `clear` is not called. The SSH session stays. Burner cells are not spared. |
| `select model` / `list models` | `swap` and `ollama list` stay as they are. |
| Text color | Input foreground `#00FF66` on `#1A1A1A`. Border stays `#3CB371`. Output foreground `#FFFF55`, the Swing stand-in for VT100 SGR 93. The View modal uses that yellow. An `EXCLUDED` cell stays gray in both areas. |
| View bounds | One client preference, global, not per turn, not on the cell record. Save x, y, width, and height when the dialog closes. Next open, including after a restart, uses it. Off-screen coordinates fall back to 720 by 480, centered. |
| Focus hold | Before a header control rebuilds the pane, remember the viewport view Y and the card Y. After the rebuild, restore `cardY - viewY` and keep focus on that cell. View does not rebuild. Clear-all may scroll to the new empty turn. |
| Import | Right-click on a cell opens a context menu. The item imports one UTF-8 text file and replaces that cell's user text. No import button on the header. The assistant area never receives the file. An `OUTPUT_ONLY` cell refuses. No focused input cell refuses, with a status line. A NUL in the first 8 KiB refuses. Cap is 1 MiB. The cell state does not change. The import does not push the pipe. |

## 1. Dispositions carried from 004

| Ask | Disposition |
|---|---|
| Collapse | One card. The header buttons stay. No second copy of the state buttons. `collapsed` is a boolean on the cell record, default false. It is not a fifth `CellState`. While collapsed, the body hides and the brief is the assistant text, from the first character, one line, or two if the bar height allows. A collapsed `INCLUDED` cell still enters the working set. `E` stays the way out of context. |
| Context letters | The buttons stay `I`, `E`, `P`, `O`. Each one gains a tooltip: Included, Excluded, Input only, Output only. Full words are not the labels. |
| Clear turns | A footer button clears the Aetheris canvas vault back to one empty `INCLUDED` turn and writes the vault. VT100 `clear` is not called. The SSH session stays. Burner cells are not spared. |
| `select model` / `list models` | `swap` and `ollama list` stay as they are. |
| Text color | Input foreground `#00FF66` on `#1A1A1A`. Border stays `#3CB371`. Output foreground `#FFFF55`, the Swing stand-in for VT100 SGR 93. The View modal uses that yellow. An `EXCLUDED` cell stays gray in both areas. |
| View bounds | One client preference, global, not per turn, not on the cell record. Save x, y, width, and height when the dialog closes. Next open, including after a restart, uses it. Off-screen coordinates fall back to 720 by 480, centered. |
| Focus hold | Before a header control rebuilds the pane, remember the viewport view Y and the card Y. After the rebuild, restore `cardY - viewY` and keep focus on that cell. View does not rebuild. Clear-all may scroll to the new empty turn. |
| Import | Right-click on a cell opens a context menu. The item imports one UTF-8 text file and replaces that cell's user text. No import button on the header. The assistant area never receives the file. An `OUTPUT_ONLY` cell refuses. No focused input cell refuses, with a status line. A NUL in the first 8 KiB refuses. Cap is 1 MiB. The cell state does not change. The import does not push the pipe. |

### Holes

 - No panel code assigns `CellRecord.executionTarget`. The operator cannot pick a model.
**semantic-key:** PK-134

 - `VaultSql.DDL_TRACE` creates `trace`. The only trace statement in use is `DELETE_TRACE_FOR_CANVAS`. Nothing inserts a row.
**semantic-key:** PK-135

 - `VaultStore.search` is the only caller of `SEARCH_CELLS`, and the shell never calls `search`.
**semantic-key:** PK-136

### Edges, bottlenecks, and failure modes

 - `CellState.fromToken` maps a blank or unknown token to `INCLUDED`.
**semantic-key:** PK-137

### 1. Grok gets a provider, and the stored target stays stored - Make it so. Approved

- The per-cell model control the constitution already names is this row's control. It lists the host's Ollama models and `grok/<model>`. It writes `CellRecord.executionTarget`. The existing update already persists `execution_target`. The status line prints the focused cell's target. An empty stored field prints `ollama`.
**semantic-key:** PK-138

### 3. MCP gets a channel, a lane, and a closed default - Make it so. Approved

- Class tag on `VaultSql` names the tool lane. That class is the vault SQL. When the file is edited for the version table, the tag comes off the class.
**semantic-key:** PK-929

### 6. Search gains a door, a canvas, and a literal needle - Make it so. Approved

Stage 1 `LIKE` is `VaultStore.search`. It wraps the needle in `%` on both sides, it has no canvas predicate, and the shell never calls it. `%` and `_` in the needle are wildcards. An empty needle matches every non-null CLOB. Burner NULL CLOBs do not match. That is the whole remainder.
**semantic-key:** PK-139

## The report

 - A bad stored state token is a different fact. `Final gap solutions` 9 sets `CellRecord.readable` false and does not throw.

Agreed with gap solution 6. The query takes the open canvas id. `%` and `_` in the needle stay literals. An empty needle returns no rows. A notebook field calls `VaultStore.search`. A hit selects the cell and does not change state.
**semantic-key:** PK-140

Agreed 2026-10-20 20:02. `TOOL_EXEC` is a separate pool. Day-one size is 1. A second tool task waits. It does not fail. `LLM_EXEC` stays 4. 2026-10-20 22:59. Day-one tool depth is 1. One hop, then the loop stops. Tool use is off until tool integration. The hooks stay. The allowlist is empty. No tool is invocable. What the tools will do is not defined. `tool_calling_enabled` defaults false. The plan does not turn the flag on. A tool call writes one trace row. A burner writes none. Tag comes off `VaultSql` when that file is edited for the version table.

### MCP host

Agreed 2026-10-20 20:02. `TOOL_EXEC` has its own pool. Day-one size is 1. `LLM_EXEC` stays 4. Absent `tool_calling_enabled` means false. Absent knobs mean the provider default. One trace row per tool call. A burner skips the row. Comes off `VaultSql` and off `VaultStore` when those files are edited. The lane keeps the key.
**semantic-key:** PK-930

### Findings

 - `CellState.fromToken` runs while `loadCells` walks the canvas. Null and blank return `INCLUDED`. `GHOST` returns `EXCLUDED`. Any other unknown token also returns `INCLUDED` today.
**semantic-key:** PK-141

### Objects the twelve solutions share - Smoked. Approved

`CellRecord` gains one boolean, `readable`. It does not gain a fifth `CellState`. The vault open path stays the one method that already probes the schema. It migrates, then selects.
**semantic-key:** PK-142
**semantic-key:** PK-142

### Comments requires on the new types - Smoked. Approved

- Each of those comments uses an key that already exists. Stays on the lane. It comes off `VaultSql` and off `VaultStore` when those files are edited. The lane does not move onto the SQL file.

## 2. What stays

`CellState` stays the four marks. `GHOST` stays `EXCLUDED`. Unreadable stays out, the same way `kept` drops it. An ephemeral card with both halves blank stays out, the same way `kept` drops it. Those checks are state modifiers. They run on the same walk.
**semantic-key:** PK-143

## 2. What stays

CellState stays INCLUDED, EXCLUDED, INPUT_ONLY, OUTPUT_ONLY. Ghost stays EXCLUDED. The four letters stay I, E, P, and O. The tips stay. fitAetherisButton stays. The import popup stays. armFocus may still record the card. persistQuiet stays the save. refreshInspector may still rewrite the inspector. It does not rebuild the deck.
**semantic-key:** PK-144

## 3. landing

**Dialog.** `openModelDialog(CellRecord, JTextField)` stays the cell entry. A console entry sets `dialogConsole`, sets `dialogCell` null, sets `dialogCellId` to the nil UUID string, selects Ollama, and disables the family control. The Ollama pane is the visible card. Tags and show are asked with that nil id. `applyTagsResult` and `applyShowResult` already match `dialogCellId`, so the nil id fills this dialog and not a cell.
**semantic-key:** PK-145

## Dependencies pitied

`CellState` is the four marks. `GHOST` is `EXCLUDED`. `tokenActive` is the landed Included filter from. `appendCell` gates halves: Included keeps both, Input only keeps the user half, Output only keeps the assistant half. `kept` drops Excluded and unreadable. `memberPrompts` keeps Included and Input only prompts. `appendMergedTurn` keeps the reply when the merge is Included or Output only. The estimate uses those gates on the one transcript. The Java keeps `tokenActive` until the build sentence. Mood stays a head when the switch is on. The inspector paint stays capped. The line spinner changes paint. The analysis page stays as filed. No new library. No Cargo.toml edit. No new color key. No new package.
**semantic-key:** PK-931

## 2. Resolution

| Piece | Change |
|---|---|
| `AetherisYaml` | Constructor default `disk`. `isRamVault()` only if mode is explicitly `ram`. |
| `aetheris.yaml` | `vault.mode: disk` |
| `VaultStore` | Comment; still honors yaml |
| | Disk is product default; ram is testing, not crash-proof |
| | Same |

### Proposal

- Apply that answer in the same EDT turn that installs that user's canvases, onto those cells. Then save through the `VaultStore` just opened, on the worker, before a later bind can close it.
**semantic-key:** PK-146

## 11. Applied 2026-10-20 07:37

`applyPendingNow` paints it only when that vault is the open one, in the same EDT turn that installs the canvases, and on a later show of that user. The save goes through that `VaultStore`. A failed save puts the answer back.
**semantic-key:** PK-147

The CanvasHead class decides which cells may show a lock. A lock is a packed prefix of the eligible cells. Index 0 is the top of the canvas. A new cell is appended after the last index. A null state is skipped. CellState#EXCLUDED is skipped.
**semantic-key:** PK-1222

The CellRecord class is one cell on a canvas. A burner cell is an ephemeral cell, and an ephemeral cell stores null text columns. The id and canvasId fields start empty. The branchId field starts as main. The state field starts as CellState#INCLUDED. The userText and assistantText fields start empty.
**semantic-key:** PK-1223

The VaultStore class is the H2 vault for the shell process. The store talks to H2 with JDBC statements and a Hikari pool. The default store is a file. The ram store is the in-memory vault used for testing. #open creates the tables and migrates missing columns. A later #load selects the canvases. Version 2 is the group_id column and the execution_status column. A caller loads canvases, saves cells, and deletes turns through this type.
**semantic-key:** PK-1224

- New Java carries `// @aa-sdd` with an existing key only. `G-05` is the router. `G-07` is the codec. `G-12` is the vault migration. `G-11` stays on the lane and comes off `VaultSql` and `VaultStore` when those files are edited.
**semantic-key:** PK-1308

CellState. 2. In / Out `[LOCK]`. The code tag is the next pass.
**semantic-key:** PK-1312

CellState. 0. Dispositions from 002 `[PUFF]` 2026-10-18 12:33. The code tag is the next pass.
**semantic-key:** PK-1321

CellState. 1. Dispositions carried from 003 `[LOCK]`. The code tag is the next pass.
**semantic-key:** PK-1322

CellState. 1. Dispositions carried from 004 `[LOCK]`. The code tag is the next pass.
**semantic-key:** PK-1323

Agreed 2026-10-20 20:02. `TOOL_EXEC` has its own pool. 2026-10-20 23:03 Hannibal `[PUFF]`. Day-one size is 1. `LLM_EXEC` stays 4. Absent `tool_calling_enabled` means false. Absent knobs mean the provider default. One trace row per tool call. A burner skips the row. Smoked `Final gap solutions`, the comment list. `G-11` comes off `VaultSql` and off `VaultStore` when those files are edited. The lane keeps the key.
**semantic-key:** PK-1339

- Each of those comments uses an `@aa-sdd` key that already exists. G-05 for the router. G-07 for the codec. G-12 for the vault migration. `G-11` stays on the lane. It comes off `VaultSql` and off `VaultStore` when those files are edited. The lane does not move onto the SQL file.
**semantic-key:** PK-1341

`mvn -q -DskipTests compile` from `C:\Projects\MCP\Casserole`. The window stays closed. The live POST is not made. A pool turn is not sent. The product files are `AetherisNotebookPanel` and `VaultStore`. No new package, public type, vault column, G-key, channel, or AMWP type.
**semantic-key:** PK-1361

VaultStore. 2. Resolution. The code tag is the next pass.
**semantic-key:** PK-1367

`CanvasRecord` is a public type in this package.
