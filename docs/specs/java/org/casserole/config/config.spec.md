# org.casserole.config

## Error slide

A transport miss is `Grok` plus the exception name plus the redacted detail. That string is the slide text. The cell stores `FAILED`. The reply box is not replaced. The status line shows `failed`.

## Error slide

Any other error opens a notice at the top of the Aetheris pane. It slides down. The border is red. The title is Error. The body is a text area of three rows, wrapping, and not editable. The text is the garnered message. A transport miss sends `Grok` plus the exception name plus the redacted detail. The cell stores `FAILED`. The reply box is not replaced. The status line shows `failed`.

## Model Config modal

The card in the tree keeps model configuration on the group bar, and that bar starts hidden. The 12-column field stores one execution target. An empty field is ollama and uses the configured Ollama service. The word `ollama` is not written back. `grok/` plus a model id is Grok. The `Grok` button is shown only when the provider is grok. It opens the `Grok settings` dialog. OK writes that dialog. Cancel writes nothing. A first change into grok with no document copies the nearest earlier cell, else the standing default, else opens the dialog, and a close without OK puts the prior target back. The think checkbox sits on that bar and is shown only when the provider is ollama. The map is session RAM keyed by cell id. On sends `think` true for that cell. Off leaves `think` unset. The tip on that box says the on value is for this session. The stored value is already this cell's. That tip is the sentence this row moves into the modal. It is not a vault column. Group, Join, and Ungroup share that bar. The main bar does not name the model. The Grok serial's 23:27 sentence uses one thinking control for both providers. The tree already splits the call. An Ollama run reads this map. A Grok run reads that cell's stream flag into the same execute field. This row keeps the split and gives each model its own Thinking checkbox for this cell. The serial stays the operative reading of the chat call. This row moves that configuration into one modal on the cell. The cell shows the choice as it is made.
**semantic-key:** PK-957

## 10. Package 6. Modal, label, and stored strings

- Remove the `Grok` button and the group-bar think checkbox, and the `applyTargetChrome` branches that show them. The field, Group, Join, and Ungroup stay. The group bar still starts hidden.
**semantic-key:** PK-332

## 1. What this slice is

**Done means:** every readable card has one `Model` button on the main bar. The click opens one application modal titled `Model Config` for that cell. The first control is a family pull-down. The rows start with Ollama and Grok. Ollama is the default for a new cell, a cleared canvas, and an empty target. Under the pull-down, only that family's options show. A combo, a checkbox, and the family pull-down write the cell at once and persist the way the dialog's OK persists today. A number field writes when it loses focus and the text parses. One label line under the main bar shows the short model name. The group bar still starts hidden and still holds the free-text field, Group, Join, and Ungroup. The `Grok` button and the group-bar think checkbox are gone. For this app, Grok effort is always a defined level. The factory level is `high`. Every Grok call, including `Hello`, sends `reasoning_effort` with that level. Ollama thinking stays session RAM on this cell. On with no level sends `think` true. On with a level sends that level. Off leaves `think` absent. The Ollama effort rows ask the server that already holds the Ollama base URL. Swing does not open that socket. A family switch keeps both stores. `grok/` with no model id is `no-model` before air-gap. `mvn -q -DskipTests compile` passes. The live POST is not made.
**semantic-key:** PK-958

### 6. Modal, label, and stored strings

- `openGrokDialog` is that modal. The title is `Model Config`. There is no second `JDialog`. OK and Cancel leave with the `Grok` button. Close leaves the cell as last written. There is no restore of the prior target. `grokDialogOpen` stays the one flag. Application modality already blocks delete of the cell while the modal is open.
**semantic-key:** PK-333

## 3. pity

| Dependency | What this row does with it |
|---|---|
| Session holder | Written to `~/.casserole/aetheris-think.yaml`. Loaded when the panel opens. |
| | `newTurn` still copies the holder, including `poolOn`, and writes the new cell. |
| | Close hides the pane and keeps the log. The footer stays at full height. |
| Thinking text | Appends. A new block writes a separator and the model tag. |
| Log file | `~/.casserole/aetheris-thinking.txt`. Clear empties the pane and the file. |
| North edge | A drag sets the height. The height is stored in the yaml. |
**semantic-key:** PK-334

### 3.1 Server: `application.yaml`

Bound to `CasseroleProperties` (`prefix = casserole`) plus Spring AI Ollama properties.
**semantic-key:** PK-335

### 3.3 Server-side history file

- Loaded at shell start; saved after each remembered Enter (not after sealed paste — paste is excluded from history).
**semantic-key:** PK-336

## `casserole.grok`

Bound on `CasseroleProperties.Grok`. Restart the server after a change. The value is not logged.
**semantic-key:** PK-337

## 10. Package 6. Card

The Model Config family list becomes `Ollama`, `Grok`, and `Pool`. The deck gains a `pool` card: one sentence and the Thinking checkbox from package 7. The sentence says this cell chats on the Heavy pool, with no API key, no model id, no effort, and no temperature.
**semantic-key:** PK-338
**semantic-key:** PK-338

## 11. Implementation proposal

4. `CasseroleProperties.Chat` gains `clip-remaining-percent`, default `1`. `0` disables the percent trigger. The truncation trigger stays. The yaml file stays unedited until a sentence names that edit. A missing key reads as `1`.
**semantic-key:** PK-339

### P1 — Yaml + `users.yaml` + per-user history + auth

- `org.casserole.config.UserEntry` — name + password
**semantic-key:** PK-340
