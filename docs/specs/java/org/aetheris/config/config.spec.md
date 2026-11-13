# org.aetheris.config

### What the room already does

`AetherisYaml` is a space-indent hand parser. Unknown keys are ignored. The shell file and the server file are not the same file. Keeps SnakeYAML off that parser. Call settings do not go in `aetheris.yaml`.
**semantic-key:** PK-901

## 5. Package 1. Doors

1. One static method `AetherisYaml.grokDoor(provider, modelPresent, burner, word)`. It returns true only when the provider is `grok`, a model id is present, the burner flag is false, and `word` is exactly `true`. Unset returns false. Explicit `false` returns false from this method. The old `refusesCloud` path stays for every other case.
**semantic-key:** PK-1

## 7. P4 — AetherisYaml + product name

**New:** `org.aetheris.config.AetherisYaml` (or `org.casserole.app.ui.ssh.AetherisYaml` if we refuse to create `org.aetheris` until Stage 1).
**semantic-key:** PK-2

## 5. P2 — Yaml, deps, worker

**`AetherisYaml`:** parse `vault.mode`, `vault.path`, `network.air-gap`, `amwp.enabled` with the existing space-indent hand parser. Unknown keys ignored.
**semantic-key:** PK-902

## 2. Floor the build must keep

- Client vault `~/.aetheris/{user}/vault`. Casserole does not open H2. Ram is the product default. Disk is the perpetuity opt-in. Do not change the `AetherisYaml` constructor's `MODE_DISK` default in this plan. Do not reopen that conflict.
**semantic-key:** PK-3

## 6. Package 3. Grok on that router

6. `burner-key` missing means deny. No parent path. Same bare-key rule as `mood-shape`: read the spelling through `AetherisYaml`, and stop to ask if a bare key cannot load. Do not invent a parent. The constitution phrase stays allow-cloud-burner. Ephemeral plus cloud fails while `burner-key` is unset. Local Ollama still receives burner text. The execute body carries `ephemeral: true` on a burner target.
**semantic-key:** PK-4

## Package 2. Execute seams and MOOD-LAYER

- The server reads `~/.aetheris.yaml` through `AetherisYaml`. The key is `aetheris.network.air-gap`. Missing is unset and refuses cloud. Explicit false allows. Explicit true refuses.
**semantic-key:** PK-5

### The floor that already holds

- `air-gap` is parsed by `AetherisYaml`. Burner saves write NULL CLOBs. Search SQL uses `LIKE`. A `trace` table exists. `branch_id`, `parent_cell_id`, and `column_position` exist on `cell`.
**semantic-key:** PK-6

## Stack a pre-plan can name without a new decision

- Yaml parser stays the in-tree `AetherisYaml` space-indent parser. SnakeYAML stays out unless a plan is blocked and approves the exception.
**semantic-key:** PK-903

### 1. Air-gap has one value, and `unset` is not false - Smoked. Approved

 - The server reads `~/.aetheris.yaml` for the process user through `AetherisYaml`. Same parser. Same key. Not a second file and not `application.yaml`.
**semantic-key:** PK-7

## 3. Floor the builder must keep

- `blockedCloud` calls `grokDoor`, then `grokFactsBlock` when the door is open. `AetherisYaml.grokDoor` stays the 22:51 predicate. `refusesCloud` stays true when the word is unset or true. Explicit false allows every cloud target. This cut does not write the air-gap word.
**semantic-key:** PK-8

## 4. Metal

10. **Air-gap.** A burner cell still does not hop. A normal cell with target `pool` is allowed when `poolDoor` is true and the shell word matches the server word. `poolDoor` is provider `pool`, burner off, and air-gap word exactly `true`. No model id is required. `AetherisYaml` gains `poolDoor` beside `grokDoor`. `grokDoor` stays the Grok predicate. Both shells call `poolDoor`. When `poolDoor` is true, the client still runs the same facts match `grokFactsBlock` already runs for Grok. A mismatch still blocks with `air-gap`. The server compares those same two words before it spawns. Unset still refuses. Explicit `false` already allows every cloud target. The component still does not read the API key.
**semantic-key:** PK-9
