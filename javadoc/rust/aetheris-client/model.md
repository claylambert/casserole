# Module model

[Rust tree](../index.md) · **aetheris-client**

**Source** `rust/aetheris/aetheris-client/src/model.rs`

The module stores Model Config for one cell and the same dialog for the console.

Java `GrokSettingsDocument` and `AetherisNotebookPanel` are the rule. The document is `aetheris-grok.yaml` under `.casserole` in the home directory. A test passes its own path. This module does not open a socket. This module does not call the Grok API.

| | |
| --- | --- |
| **Specification** | G-12 rust-aetheris-client.draft.001.md |
| **Author** | clayl (2026-10-27) |

## Type Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| enum | [`Family`](model/enum.Family.md) | The [`Family`] enum is the provider family of the dialog. |
| struct | [`ThinkSnap`](model/struct.ThinkSnap.md) | The [`ThinkSnap`] struct is the think state for one cell. |
| struct | [`GrokEntry`](model/struct.GrokEntry.md) | The [`GrokEntry`] struct is one Grok settings object. |
| struct | [`GrokDoc`](model/struct.GrokDoc.md) | The [`GrokDoc`] struct is the Grok settings document. |
| enum | [`Parse`](model/enum.Parse.md) | The `Parse` enum is the result of reading one settings document. |

## Constant Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| const | [`GROK_FILE`](#constant-grok_file) | The `GROK_FILE` constant is the settings file name. |
| const | [`CONSOLE_CELL`](#constant-console_cell) | The `CONSOLE_CELL` constant is the cell id of the console dialog. |
| const | [`OSC_HOLD_CAP`](#constant-osc_hold_cap) | The `OSC_HOLD_CAP` constant is the longest unfinished select-model sequence that stays buffered. |
| const | [`OSC_OPEN`](#constant-osc_open) | The `OSC_OPEN` constant is the start of the private select-model sequence. |
| const | [`MODEL_LABEL`](#constant-model_label) |  |
| const | [`MODEL_TIP`](#constant-model_tip) |  |
| const | [`MODEL_LINE_TIP`](#constant-model_line_tip) |  |
| const | [`TARGET_TIP`](#constant-target_tip) |  |
| const | [`OLLAMA_MODEL_TIP`](#constant-ollama_model_tip) |  |
| const | [`OLLAMA_THINK_TIP`](#constant-ollama_think_tip) |  |
| const | [`OLLAMA_EFFORT_TIP`](#constant-ollama_effort_tip) |  |
| const | [`GROK_MODEL_TIP`](#constant-grok_model_tip) |  |
| const | [`GROK_EFFORT_TIP`](#constant-grok_effort_tip) |  |
| const | [`SEND_EFFORT_TIP`](#constant-send_effort_tip) |  |
| const | [`BURNER_CLOUD_TIP`](#constant-burner_cloud_tip) |  |
| const | [`TEMPERATURE_TIP`](#constant-temperature_tip) |  |
| const | [`MAX_TOKENS_TIP`](#constant-max_tokens_tip) |  |
| const | [`GROK_THINK_TIP`](#constant-grok_think_tip) |  |
| const | [`POOL_SENTENCE`](#constant-pool_sentence) |  |
| const | [`POOL_THINK_TIP`](#constant-pool_think_tip) |  |
| const | [`RAW_CONSOLE`](#constant-raw_console) |  |
| const | [`SETTINGS_WORD`](#constant-settings_word) |  |
| const | [`NO_MODEL`](#constant-no_model) |  |
| const | [`GROK_LIST_MISS`](#constant-grok_list_miss) | The `GROK_LIST_MISS` constant is the red notice when the Grok catalog has no model lines. |
| const | [`OLLAMA_LIST_MISS`](#constant-ollama_list_miss) | The `OLLAMA_LIST_MISS` constant is the red notice when the Ollama tag list has no model lines. |
| const | [`FOUR`](#constant-four) |  |
| const | [`THREE`](#constant-three) |  |
| const | [`ONE`](#constant-one) |  |

## Function Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`effort_rows`](#function-effort_rows) | The `effort_rows` function returns the legal effort rows for `model`. |
| fn | [`effort_start`](#function-effort_start) | The `effort_start` function returns the factory effort. |
| fn | [`sends_effort`](#function-sends_effort) | The `sends_effort` function reports whether a fresh cell sends `reasoning_effort`. |
| fn | [`normalize_effort`](#function-normalize_effort) | The `normalize_effort` function keeps `value` when it is still a row for `model`. |
| fn | [`provider_id`](#function-provider_id) | The `provider_id` function returns the provider word. |
| fn | [`engine_from_console`](#function-engine_from_console) | The `engine_from_console` function builds the SSH strip word for one pane. |
| fn | [`engine_from_shell_result`](#function-engine_from_shell_result) | The `engine_from_shell_result` function reads a `shell_model_result:` body. |
| fn | [`model_after_slash`](#function-model_after_slash) | The `model_after_slash` function returns the text after the first slash. |
| fn | [`short_model_name`](#function-short_model_name) | The `short_model_name` function returns the label under the bar. |
| fn | [`outbound_target`](#function-outbound_target) | The `outbound_target` function returns the target that goes out. |
| fn | [`no_model`](#function-no_model) | The `no_model` function reports Grok with no model id. |
| fn | [`number_text`](#function-number_text) | The `number_text` function prints a number for a field. |
| fn | [`grok_path`](#function-grok_path) | The `grok_path` function joins the settings path under `home`. |
| fn | [`parse_doc`](#function-parse_doc) | The `parse_doc` function reads YAML text into a [`Parse`]. |
| fn | [`version_one`](#function-version_one) | The `version_one` function reports version 1. |
| fn | [`map_get`](#function-map_get) | The `map_get` function returns one YAML map value. |
| fn | [`parse_entry`](#function-parse_entry) | The `parse_entry` function reads one settings map. |
| fn | [`yaml_text`](#function-yaml_text) | The `yaml_text` function reads one scalar as text. |
| fn | [`yaml_bool`](#function-yaml_bool) | The `yaml_bool` function reads one scalar as a bool. |
| fn | [`yaml_f64`](#function-yaml_f64) | The `yaml_f64` function reads a float. |
| fn | [`yaml_i32`](#function-yaml_i32) | The `yaml_i32` function reads an `i32`. |
| fn | [`dump_doc`](#function-dump_doc) | The `dump_doc` function writes the document as YAML. |
| fn | [`dump_entry`](#function-dump_entry) | The `dump_entry` function writes one entry. |
| fn | [`push_line`](#function-push_line) | The `push_line` function appends one YAML line. |
| fn | [`yaml_key`](#function-yaml_key) | The `yaml_key` function quotes a key that is not a plain token. |

## Constant Detail

### GROK_FILE

<a id="constant-grok_file"></a>

```rust
pub const GROK_FILE: &str = "aetheris-grok.yaml";
```

The `GROK_FILE` constant is the settings file name.
The name is `aetheris-grok.yaml`.

### CONSOLE_CELL

<a id="constant-console_cell"></a>

```rust
pub const CONSOLE_CELL: &str = "00000000-0000-0000-0000-000000000000";
```

The `CONSOLE_CELL` constant is the cell id of the console dialog.
The text is the all-zero UUID.

### OSC_HOLD_CAP

<a id="constant-osc_hold_cap"></a>

```rust
pub const OSC_HOLD_CAP: usize = 256;
```

The `OSC_HOLD_CAP` constant is the longest unfinished select-model sequence that stays buffered.
The value is 256 bytes. A longer unfinished sequence is painted.

### OSC_OPEN

<a id="constant-osc_open"></a>

```rust
const OSC_OPEN: &[u8] = b"\x1b]777;select-model;";
```

The `OSC_OPEN` constant is the start of the private select-model sequence.
The bytes are ESC `]777;select-model;`.

### MODEL_LABEL

<a id="constant-model_label"></a>

```rust
pub const MODEL_LABEL: &str = "Model";
```

### MODEL_TIP

<a id="constant-model_tip"></a>

```rust
pub const MODEL_TIP: &str = "Model opens the Model Config modal for this cell. The pull-down chooses the model. The modal shows only that model's options. The Thinking checkbox sets thinking on or off for this cell and this model. Ollama is the default. A change updates this cell at once. Pool chats on the Heavy pool and does not use the API key.";
```

### MODEL_LINE_TIP

<a id="constant-model_line_tip"></a>

```rust
pub const MODEL_LINE_TIP: &str = "The line is the model this turn will call.";
```

### TARGET_TIP

<a id="constant-target_tip"></a>

```rust
pub const TARGET_TIP: &str = "Execution target for this turn. Empty sends to ollama and is not written back as a name. grok/model sends to Grok when a key is present. The word pool stores pool. Join does not change this field.";
```

### OLLAMA_MODEL_TIP

<a id="constant-ollama_model_tip"></a>

```rust
pub const OLLAMA_MODEL_TIP: &str = "Models on the configured Ollama host. A chosen id is the model this cell calls.";
```

### OLLAMA_THINK_TIP

<a id="constant-ollama_think_tip"></a>

```rust
pub const OLLAMA_THINK_TIP: &str = "On asks this cell to think. Off leaves think unset.";
```

### OLLAMA_EFFORT_TIP

<a id="constant-ollama_effort_tip"></a>

```rust
pub const OLLAMA_EFFORT_TIP: &str = "A chosen level is what think sends for this cell.";
```

### GROK_MODEL_TIP

<a id="constant-grok_model_tip"></a>

```rust
pub const GROK_MODEL_TIP: &str = "Models from the Grok catalog. grok-4.3 is the factory id. A chosen row writes grok/ plus that id. An empty selection stays empty until a row is chosen. A missed catalog is a red error.";
```

### GROK_EFFORT_TIP

<a id="constant-grok_effort_tip"></a>

```rust
pub const GROK_EFFORT_TIP: &str = "The level stored for this cell. Send effort decides whether that level goes on the wire.";
```

### SEND_EFFORT_TIP

<a id="constant-send_effort_tip"></a>

```rust
pub const SEND_EFFORT_TIP: &str = "On sends this cell's level as reasoning_effort. Off leaves the field off the wire. A host 400 that names the parameter turns this off and retries once.";
```

### BURNER_CLOUD_TIP

<a id="constant-burner_cloud_tip"></a>

```rust
pub const BURNER_CLOUD_TIP: &str = "A check allows a burner hop to this cloud model. An unchecked box refuses that hop. This box does not change the air-gap word.";
```

### TEMPERATURE_TIP

<a id="constant-temperature_tip"></a>

```rust
pub const TEMPERATURE_TIP: &str = "Temperature on the cell call. The factory value is 1. top_p is not sent.";
```

### MAX_TOKENS_TIP

<a id="constant-max_tokens_tip"></a>

```rust
pub const MAX_TOKENS_TIP: &str = "max_completion_tokens on the cell call. The factory value is 128000. The probe uses 16 and does not use this number.";
```

### GROK_THINK_TIP

<a id="constant-grok_think_tip"></a>

```rust
pub const GROK_THINK_TIP: &str = "The checkbox is on or off for this cell. It leaves reasoning_effort on the call.";
```

### POOL_SENTENCE

<a id="constant-pool_sentence"></a>

```rust
pub const POOL_SENTENCE: &str = "This cell chats on the Heavy pool, with no API key, no model id, no effort, and no temperature.";
```

### POOL_THINK_TIP

<a id="constant-pool_think_tip"></a>

```rust
pub const POOL_THINK_TIP: &str = "The text sits on the footer strip. It stays between sessions. It is not stored on the turn. Clear wipes the log.";
```

### RAW_CONSOLE

<a id="constant-raw_console"></a>

```rust
pub const RAW_CONSOLE: &str = "raw console has no modal";
```

### SETTINGS_WORD

<a id="constant-settings_word"></a>

```rust
pub const SETTINGS_WORD: &str = "settings";
```

### NO_MODEL

<a id="constant-no_model"></a>

```rust
pub const NO_MODEL: &str = "no-model";
```

### GROK_LIST_MISS

<a id="constant-grok_list_miss"></a>

```rust
pub const GROK_LIST_MISS: &str = "Grok model list is empty.";
```

The `GROK_LIST_MISS` constant is the red notice when the Grok catalog has no model lines.
The words are `Grok model list is empty.`

### OLLAMA_LIST_MISS

<a id="constant-ollama_list_miss"></a>

```rust
pub const OLLAMA_LIST_MISS: &str = "Ollama model list is empty.";
```

The `OLLAMA_LIST_MISS` constant is the red notice when the Ollama tag list has no model lines.
The words are `Ollama model list is empty.`

### FOUR

<a id="constant-four"></a>

```rust
const FOUR: &[&str] = &["low", "medium", "high", "xhigh"];
```

### THREE

<a id="constant-three"></a>

```rust
const THREE: &[&str] = &["low", "medium", "high"];
```

### ONE

<a id="constant-one"></a>

```rust
const ONE: &[&str] = &["high"];
```

## Function Detail

### effort_rows

<a id="function-effort_rows"></a>

```rust
pub fn effort_rows(model: &str) -> &'static [&'static str]
```

The `effort_rows` function returns the legal effort rows for `model`.
`grok-4.7`, `grok-4.6`, and `grok-4.20-multi-agent-0309` list `low`, `medium`, `high`, and `xhigh`. `grok-4.5` lists `low`, `medium`, and `high`. An empty id lists `high` only. Every other id lists `high` only.

### effort_start

<a id="function-effort_start"></a>

```rust
pub fn effort_start(_model: &str) -> &'static str
```

The `effort_start` function returns the factory effort.
The word is always `high`. The model id is not read.

### sends_effort

<a id="function-sends_effort"></a>

```rust
pub fn sends_effort(model: &str) -> bool
```

The `sends_effort` function reports whether a fresh cell sends `reasoning_effort`.
The result is false for `grok-4.20-0309-reasoning`. The result is false for `grok-4.20-0309-non-reasoning`. The result is false for `grok-build-0.1`. Those are the three ids the service refuses. Every other id returns true.

### normalize_effort

<a id="function-normalize_effort"></a>

```rust
pub fn normalize_effort(model: &str, value: &str) -> String
```

The `normalize_effort` function keeps `value` when it is still a row for `model`.
Any other value becomes the factory level `high`.

### provider_id

<a id="function-provider_id"></a>

```rust
pub fn provider_id(target: &str) -> String
```

The `provider_id` function returns the provider word.
The rule matches Swing `providerId`. An empty word is `ollama`. The text before the first slash is kept. The result is lowercase.

### engine_from_console

<a id="function-engine_from_console"></a>

```rust
pub fn engine_from_console(provider: &str, ollama_id: &str, grok_id: &str) -> String
```

The `engine_from_console` function builds the SSH strip word for one pane.
Pool returns `pool`. Grok returns `grok/` plus the id, or `grok` when the id is blank.
Any other family returns `ollama/` plus the Ollama id, or `ollama` when that id is blank.
An id that already begins with its family prefix is returned as stored. A canvas cell is not an input.
// @aa-sdd PK-994 docs/specs/common/stages.spec.md

### engine_from_shell_result

<a id="function-engine_from_shell_result"></a>

```rust
pub fn engine_from_shell_result(text: &str) -> Option<String>
```

The `engine_from_shell_result` function reads a `shell_model_result:` body.
A body that does not start with that prefix returns `None`. A missing provider line is Ollama.
The Ollama id is `ollama_model:`, or `model:` when that line is empty. The Grok id is `grok_model:`.
// @aa-sdd PK-994 docs/specs/common/stages.spec.md

### model_after_slash

<a id="function-model_after_slash"></a>

```rust
pub fn model_after_slash(target: &str) -> String
```

The `model_after_slash` function returns the text after the first slash.
The rule matches Swing `modelAfterSlash`. A missing slash returns an empty string. A slash at the end returns an empty string.

### short_model_name

<a id="function-short_model_name"></a>

```rust
pub fn short_model_name(target: &str) -> String
```

The `short_model_name` function returns the label under the bar.
An `ollama` or `grok` target uses the text after the slash. An empty id uses the provider word. `pool` stays `pool`. An unknown provider is blank.

### outbound_target

<a id="function-outbound_target"></a>

```rust
pub fn outbound_target(stored: &str) -> String
```

The `outbound_target` function returns the target that goes out.
Empty stored text goes out as the word `ollama`. That empty text is not written back as a name. Other text is trimmed.

### no_model

<a id="function-no_model"></a>

```rust
pub fn no_model(stored: &str) -> bool
```

The `no_model` function reports Grok with no model id.
The client says `no-model` and does not send. An empty stored word is not this case.

### number_text

<a id="function-number_text"></a>

```rust
pub fn number_text(value: f64) -> String
```

The `number_text` function prints a number for a field.
A finite whole number whose absolute value is under `1e15` prints without a fraction. Every other value uses the default text. The wire does not use this function.

### grok_path

<a id="function-grok_path"></a>

```rust
pub fn grok_path(home: &Path) -> PathBuf
```

The `grok_path` function joins the settings path under `home`.
The folders are `.casserole` and `aetheris-grok.yaml`.

### parse_doc

<a id="function-parse_doc"></a>

```rust
fn parse_doc(text: &str) -> Parse
```

The `parse_doc` function reads YAML text into a [`Parse`](model/enum.Parse.md).
Bad YAML is blocked. A root that is not a map is blocked. A version other than 1 is blocked. An empty default model is dropped. A cell key that is not text is skipped. A cell value that is not a map is skipped.

### version_one

<a id="function-version_one"></a>

```rust
fn version_one(value: Option<&serde_yaml::Value>) -> bool
```

The `version_one` function reports version 1.
A missing value is false. An integer 1 is true. The text `1` is true after trim.

### map_get

<a id="function-map_get"></a>

```rust
fn map_get<'a>(map: &'a serde_yaml::Mapping, key: &str) -> Option<&'a serde_yaml::Value>
```

The `map_get` function returns one YAML map value.
The key is compared as a string.

### parse_entry

<a id="function-parse_entry"></a>

```rust
fn parse_entry(raw: &serde_yaml::Mapping) -> GrokEntry
```

The `parse_entry` function reads one settings map.
Missing fields start from the factory entry. An empty API becomes `chat`. A missing send flag uses `sends_effort`. A present send flag uses the stored bool. A missing temperature keeps 1.0. A missing max token count keeps 128000.

### yaml_text

<a id="function-yaml_text"></a>

```rust
fn yaml_text(value: Option<&serde_yaml::Value>) -> String
```

The `yaml_text` function reads one scalar as text.
A missing value is empty. A string is trimmed. A number uses `number_text` when it is a float. A bool is `true` or `false`.

### yaml_bool

<a id="function-yaml_bool"></a>

```rust
fn yaml_bool(value: Option<&serde_yaml::Value>) -> bool
```

The `yaml_bool` function reads one scalar as a bool.
A missing value is false. A bool is itself. The integer 1 is true. The words `true`, `yes`, and `1` are true.

### yaml_f64

<a id="function-yaml_f64"></a>

```rust
fn yaml_f64(value: &serde_yaml::Value) -> Option<f64>
```

The `yaml_f64` function reads a float.
A float, an integer, or a trimmed number string is accepted. Any other shape returns `None`.

### yaml_i32

<a id="function-yaml_i32"></a>

```rust
fn yaml_i32(value: &serde_yaml::Value) -> Option<i32>
```

The `yaml_i32` function reads an `i32`.
A signed integer, an unsigned integer, or a trimmed number string is accepted when it fits. Any other shape returns `None`.

### dump_doc

<a id="function-dump_doc"></a>

```rust
fn dump_doc(standing: Option<&GrokEntry>, cells: &[(String, GrokEntry)]) -> String
```

The `dump_doc` function writes the document as YAML.
The version line is `version: 1`. A standing entry is written under `default`. An empty cell map is `cells: {}`.

### dump_entry

<a id="function-dump_entry"></a>

```rust
fn dump_entry(out: &mut String, entry: &GrokEntry, indent: &str)
```

The `dump_entry` function writes one entry.
An empty API is written as `chat`. The effort is normalized. Temperature uses `wire_double`.

### push_line

<a id="function-push_line"></a>

```rust
fn push_line(out: &mut String, indent: &str, key: &str, value: &str)
```

The `push_line` function appends one YAML line.
The value is escaped by `yaml_scalar`.

### yaml_key

<a id="function-yaml_key"></a>

```rust
fn yaml_key(key: &str) -> String
```

The `yaml_key` function quotes a key that is not a plain token.
A plain token is a non-empty run of ASCII letters, digits, `-`, and `_`. A quoted key escapes backslash and quote.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
