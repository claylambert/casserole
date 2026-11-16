# rust.aetheris.aetheris-client.src.model

# Aetheris Stage 1 tweaks 002 — code spec

**What it is not:** Not a data packet. Not a pad for new notes. Not Stage 2. Not permission to rebuild.
**Token:** `AETHERIS-S1-TWEAKS-002`
**Family:** code. The realization in the tree is Java. The source is not this spec.

# Aetheris Stage 1 tweaks build — code spec

**What it is not:** Not a data packet. Not a plan for the snack on serial 005. Not Stage 2. Not permission to rebuild.
**Token:** `AETHERIS-S1-TWEAKS`
**State:** draft

### Encode

The filed build is encoded. Family is code. State is draft. The encode is not a yes to another build.
**semantic-key:** PK-989

## Console status

2. Family `ollama` prints `think`. An empty think prints `off`. Any other stored word prints as stored.
**semantic-key:** PK-592

## Mood call uses the armed cell on the showing canvas

In the Java client the mood call used Ollama after he selected Grok on the cell. The call used to take the model of the armed cell. This row restores that. The server completes grok on a grok target and ollama on an empty target or an ollama target. An Ollama reply means the card that was read stored an empty target or an ollama target. moodCard was taking a readable cell from any canvas. Run and the engine word stay on the showing canvas. A readable Ollama cell on a hidden canvas beat the Grok cell in front. This row states the speaker. The arm is the readable cell on the showing canvas. A readable cell on a hidden canvas is a stale arm and falls through. A probe of the Family combo click wrote ollama on focus loss while the combo still showed Ollama, then wrote grok, and the grok write stayed. The row does not treat that click as the cause. The Ollama pane can still write the cell after the family item is already Grok or Pool. That write stops unless the family item is Ollama. A missing family combo keeps the old write. The console path stays. No crate delta.
**semantic-key:** PK-990

# Aetheris Stage 2, working serial

- **Family:** code. The Stage 2 remainder, carried so a later pre-plan can be written from this file.
**semantic-key:** PK-593

## 3. Edits

**Swap.** No process spawn. No `/api/generate`. Usage stays `swap <from> <to>`. Family `ollama` stores `to`. Any other family writes `The console family is ` plus the word and leaves the target.
**semantic-key:** PK-594

## 3. Edits

**Print.** `writeModelStatus` reads the console session and writes the block. `family` is the provider word. `model` is `consoleTargetLabel`. Family `ollama` then writes `think`. An empty think is `off`. Family `grok` then writes `think`, `effort`, `send-effort`, `stream`, `burner`, and the two numbers when they are stored. An empty effort is blank. Family `pool` then writes `think` as `true` or `off`.
**semantic-key:** PK-595

## Dependencies pitied

The Java desk already seats the opener, the constructed mood, and the closer. fit_blocks, mood_shape_tuned, ssh_mood_line, counted_inspector, and mood_inspector_head are the Rust seats. The glass session struct stays the glass session. Protocol stays private functions. ollama_pane_owns_cell is the carry. Family is not copied out of the session. A missing family still writes. The console path does not ask. No new library. No Cargo.toml edit. No new JUnit class. No cargo test.
**semantic-key:** PK-991

## 3. pity

| Dependency | What this row does with it |
|---|---|
| Bare command | The OSC and the `Model Config` line stay. The client opens the modal. |
| Family word | `grok`, `ollama`, or `pool` sets the provider. The command writes the model line. It does not send the OSC. |
| Stored options | The Ollama id, the Grok id, think, effort, send-effort, stream, temperature, max tokens, burner, and pool think stay. |
| Other arguments | A second word or any other word is refused. The family stays. The modal stays closed. |
| Raw VT100 | The shortcut does not need the Swing modal. |
**semantic-key:** PK-596

## 9. Package 5. Errors

**Parse text.** The message quotes at most 80 characters of the line after redaction. A line over 1 MiB is `pool parse stdout line over 1 MiB`. Those bytes are not copied into the message. The reader stops at 1 MiB plus one byte and does not retain the rest.
**semantic-key:** PK-597

### Catch-up order

2026-11-08. Java asks for the Grok language-model catalog when the dialog is on Grok. The crate matches that ask. `ModelWire::GrokModels` carries `grok_models:` and a newline. An empty `grok_models_result:` leaves the static choices. A reply with model lines replaces those choices. An unknown selected id stays first. The crate does not call the Grok API. The crate does not dial the pool. No wire stanza. The grok_models ask is a channel A catalog ask, the same shape as tags, and it is not a system line on a canvas, glass, group, or pipe send. Side move: `casserole.pool.secret` is the token `grok agent serve` expects. The crate does not read it.
**semantic-key:** PK-992
**semantic-key:** PK-992

### P1 — Yaml + `users.yaml` + per-user history + auth

SnakeYAML is already on the Boot classpath (no new Maven dep). Parse to `List`/`Map` with explicit loops.
**semantic-key:** PK-598

## 8. `message list` terse / verbose — approved

| Item | Lock |
|---|---|
| Yaml | `casserole.chat.list-preview-chars: 120` (`0` = never truncate) |
| Default | `message list`, `message list <filter>`, `message list meta` → **terse** (truncate + `…`) |
| Full text | `message list verbose` (and `verbose user` / `meta verbose`) |
| Parse | Flag on the existing command (same style as `meta`) |
| Truncate | Java `char` count. Unmarshalled text |
**semantic-key:** PK-599

GrokModels asks for the language-model catalog.
**semantic-key:** PK-1280

The `pick_grok_model` method chooses one Grok model. A shut dialog returns a quiet effect. Send effort follows `sends_effort` for that model. A console dialog returns a shell set. Any other dialog writes the cell and returns `grok/` plus the model.
**semantic-key:** PK-1281

Parse. 13. Last-ditch scenarios (do this **before** calling the stage done). The code tag is the next pass.
**semantic-key:** PK-1305

**Family:** code. The realization in the tree is Java. The source is not this spec.
**semantic-key:** PK-1320
**semantic-key:** PK-1320

`ThinkSnap` is a public type in this module.

`GrokEntry` is a public type in this module.

`GrokDoc` is a public type in this module.

`ModelEffect` is a public type in this module.

`ModelSession` is a public type in this module.

`OscHold` is a public type in this module.

`OscOpen` is a public type in this module.

## Checking Grok thinking sets stream true

When the thinking box turns on and the family is Grok, stream becomes true. Turning the box off leaves stream as it is. A later choice of stream stays until the box is turned on again. Turning the box on again sets stream to true. Ollama thinking and Pool thinking do not set stream. A console dialog returns a shell set. Any other dialog returns the snap with `grok_on` set. The method is `set_grok_think`. The file is [model.rs](../../../../../../rust/aetheris/aetheris-client/src/model.rs).
**semantic-key:** PK-1404
