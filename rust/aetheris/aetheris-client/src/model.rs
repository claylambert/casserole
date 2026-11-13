// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The module stores Model Config for one cell and the same dialog for the console.
//!
//! Java `GrokSettingsDocument` and `AetherisNotebookPanel` are the rule. The document is `aetheris-grok.yaml` under `.casserole` in the home directory. A test passes its own path. This module does not open a socket. This module does not call the Grok API.
//!
//! @aa-sdd G-12 rust-aetheris-client.draft.001.md
//! @author clayl (2026-10-27)

use std::path::{Path, PathBuf};

use crate::amwp::ExecuteTune;

/// The `GROK_FILE` constant is the settings file name.
/// The name is `aetheris-grok.yaml`.
pub const GROK_FILE: &str = "aetheris-grok.yaml";
/// The `CONSOLE_CELL` constant is the cell id of the console dialog.
/// The text is the all-zero UUID.
pub const CONSOLE_CELL: &str = "00000000-0000-0000-0000-000000000000";
/// The `OSC_HOLD_CAP` constant is the longest unfinished select-model sequence that stays buffered.
/// The value is 256 bytes. A longer unfinished sequence is painted.
pub const OSC_HOLD_CAP: usize = 256;
/// The `OSC_OPEN` constant is the start of the private select-model sequence.
/// The bytes are ESC `]777;select-model;`.
const OSC_OPEN: &[u8] = b"\x1b]777;select-model;";

pub const MODEL_LABEL: &str = "Model";
pub const MODEL_TIP: &str = "Model opens the Model Config modal for this cell. The pull-down chooses the model. The modal shows only that model's options. The Thinking checkbox sets thinking on or off for this cell and this model. Ollama is the default. A change updates this cell at once. Pool chats on the Heavy pool and does not use the API key.";
pub const MODEL_LINE_TIP: &str = "The line is the model this turn will call.";
pub const TARGET_TIP: &str = "Execution target for this turn. Empty sends to ollama and is not written back as a name. grok/model sends to Grok when a key is present. The word pool stores pool. Join does not change this field.";
pub const OLLAMA_MODEL_TIP: &str = "Models on the configured Ollama host. A chosen id is the model this cell calls.";
pub const OLLAMA_THINK_TIP: &str = "On asks this cell to think. Off leaves think unset.";
pub const OLLAMA_EFFORT_TIP: &str = "A chosen level is what think sends for this cell.";
pub const GROK_MODEL_TIP: &str = "Models from the Grok catalog. grok-4.3 is the factory id. A chosen row writes grok/ plus that id. An empty selection stays empty until a row is chosen. A missed catalog is a red error.";
pub const GROK_EFFORT_TIP: &str = "The level stored for this cell. Send effort decides whether that level goes on the wire.";
pub const SEND_EFFORT_TIP: &str = "On sends this cell's level as reasoning_effort. Off leaves the field off the wire. A host 400 that names the parameter turns this off and retries once.";
pub const BURNER_CLOUD_TIP: &str = "A check allows a burner hop to this cloud model. An unchecked box refuses that hop. This box does not change the air-gap word.";
pub const TEMPERATURE_TIP: &str = "Temperature on the cell call. The factory value is 1. top_p is not sent.";
pub const MAX_TOKENS_TIP: &str = "max_completion_tokens on the cell call. The factory value is 128000. The probe uses 16 and does not use this number.";
pub const GROK_THINK_TIP: &str = "The checkbox is on or off for this cell. It leaves reasoning_effort on the call.";
pub const POOL_SENTENCE: &str = "This cell chats on the Heavy pool, with no API key, no model id, no effort, and no temperature.";
pub const POOL_THINK_TIP: &str = "The text sits on the footer strip. It stays between sessions. It is not stored on the turn. Clear wipes the log.";
pub const RAW_CONSOLE: &str = "raw console has no modal";
pub const SETTINGS_WORD: &str = "settings";
pub const NO_MODEL: &str = "no-model";
/// The `GROK_LIST_MISS` constant is the red notice when the Grok catalog has no model lines.
/// The words are `Grok model list is empty.`
pub const GROK_LIST_MISS: &str = "Grok model list is empty.";
/// The `OLLAMA_LIST_MISS` constant is the red notice when the Ollama tag list has no model lines.
/// The words are `Ollama model list is empty.`
pub const OLLAMA_LIST_MISS: &str = "Ollama model list is empty.";

const FOUR: &[&str] = &["low", "medium", "high", "xhigh"];
const THREE: &[&str] = &["low", "medium", "high"];
const ONE: &[&str] = &["high"];

/// The `effort_rows` function returns the legal effort rows for `model`.
/// `grok-4.7`, `grok-4.6`, and `grok-4.20-multi-agent-0309` list `low`, `medium`, `high`, and `xhigh`. `grok-4.5` lists `low`, `medium`, and `high`. An empty id lists `high` only. Every other id lists `high` only.
pub fn effort_rows(model: &str) -> &'static [&'static str] {
    if model == "grok-4.7" || model == "grok-4.6" || model == "grok-4.20-multi-agent-0309" {
        FOUR
    } else if model == "grok-4.5" {
        THREE
    } else {
        ONE
    }
}

/// The `effort_start` function returns the factory effort.
/// The word is always `high`. The model id is not read.
pub fn effort_start(_model: &str) -> &'static str {
    "high"
}

/// The `sends_effort` function reports whether a fresh cell sends `reasoning_effort`.
/// The result is false for `grok-4.20-0309-reasoning`. The result is false for `grok-4.20-0309-non-reasoning`. The result is false for `grok-build-0.1`. Those are the three ids the service refuses. Every other id returns true.
pub fn sends_effort(model: &str) -> bool {
    model != "grok-4.20-0309-reasoning"
        && model != "grok-4.20-0309-non-reasoning"
        && model != "grok-build-0.1"
}

/// The `normalize_effort` function keeps `value` when it is still a row for `model`.
/// Any other value becomes the factory level `high`.
pub fn normalize_effort(model: &str, value: &str) -> String {
    if effort_rows(model).contains(&value) {
        value.to_string()
    } else {
        effort_start(model).to_string()
    }
}

/// The `provider_id` function returns the provider word.
/// The rule matches Swing `providerId`. An empty word is `ollama`. The text before the first slash is kept. The result is lowercase.
pub fn provider_id(target: &str) -> String {
    let trimmed = target.trim();
    if trimmed.is_empty() {
        return "ollama".to_string();
    }
    let id = match trimmed.split_once('/') {
        Some((head, _)) => head,
        None => trimmed,
    };
    id.to_ascii_lowercase()
}

/// The `engine_from_console` function builds the SSH strip word for one pane.
/// Pool returns `pool`. Grok returns `grok/` plus the id, or `grok` when the id is blank.
/// Any other family returns `ollama/` plus the Ollama id, or `ollama` when that id is blank.
/// An id that already begins with its family prefix is returned as stored. A canvas cell is not an input.
/// // @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230
pub fn engine_from_console(provider: &str, ollama_id: &str, grok_id: &str) -> String {
    let family = provider.trim();
    if family == "pool" {
        return "pool".to_string();
    }
    if family == "grok" {
        let id = grok_id.trim();
        if id.is_empty() {
            return "grok".to_string();
        }
        if id.starts_with("grok/") {
            return id.to_string();
        }
        return format!("grok/{id}");
    }
    let id = ollama_id.trim();
    if id.is_empty() {
        return "ollama".to_string();
    }
    if id.starts_with("ollama/") {
        return id.to_string();
    }
    format!("ollama/{id}")
}

/// The `engine_from_shell_result` function reads a `shell_model_result:` body.
/// A body that does not start with that prefix returns `None`. A missing provider line is Ollama.
/// The Ollama id is `ollama_model:`, or `model:` when that line is empty. The Grok id is `grok_model:`.
/// // @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230
pub fn engine_from_shell_result(text: &str) -> Option<String> {
    if !text.starts_with("shell_model_result:") {
        return None;
    }
    let provider = shell_line(text, "provider:");
    let mut ollama = shell_line(text, "ollama_model:");
    if ollama.is_empty() {
        ollama = shell_line(text, "model:");
    }
    let grok = shell_line(text, "grok_model:");
    Some(engine_from_console(&provider, &ollama, &grok))
}

/// The `model_after_slash` function returns the text after the first slash.
/// The rule matches Swing `modelAfterSlash`. A missing slash returns an empty string. A slash at the end returns an empty string.
pub fn model_after_slash(target: &str) -> String {
    match target.find('/') {
        Some(i) if i + 1 < target.len() => target[i + 1..].trim().to_string(),
        _ => String::new(),
    }
}

/// The `short_model_name` function returns the label under the bar.
/// An `ollama` or `grok` target uses the text after the slash. An empty id uses the provider word. `pool` stays `pool`. An unknown provider is blank.
pub fn short_model_name(target: &str) -> String {
    let provider = provider_id(target);
    match provider.as_str() {
        "ollama" | "grok" => {
            let id = model_after_slash(target);
            if id.is_empty() { provider } else { id }
        }
        "pool" => "pool".to_string(),
        _ => String::new(),
    }
}

/// The `outbound_target` function returns the target that goes out.
/// Empty stored text goes out as the word `ollama`. That empty text is not written back as a name. Other text is trimmed.
pub fn outbound_target(stored: &str) -> String {
    let trimmed = stored.trim();
    if trimmed.is_empty() {
        "ollama".to_string()
    } else {
        trimmed.to_string()
    }
}

/// The `no_model` function reports Grok with no model id.
/// The client says `no-model` and does not send. An empty stored word is not this case.
pub fn no_model(stored: &str) -> bool {
    let outbound = outbound_target(stored);
    provider_id(&outbound) == "grok" && model_after_slash(&outbound).is_empty()
}

/// The `number_text` function prints a number for a field.
/// A finite whole number whose absolute value is under `1e15` prints without a fraction. Every other value uses the default text. The wire does not use this function.
pub fn number_text(value: f64) -> String {
    if value.is_finite() && value == value.round() && value.abs() < 1e15 {
        format!("{}", value as i64)
    } else {
        format!("{value}")
    }
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The [`Family`] enum is the provider family of the dialog.
/// `Ollama` is the local catalog. `Grok` is the Grok table. `Pool` is the Heavy pool.
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
// @aa-sdd GK-1424 aetheris-stage-1-tweaks-002.enir.md
// @aa-sdd GK-1427 aetheris-stage-1-tweaks-build.enir.md
// @aa-sdd PK-989 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
// @aa-sdd PK-592 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
// @aa-sdd PK-990 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
// @aa-sdd PK-593 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
// @aa-sdd PK-594 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
// @aa-sdd PK-595 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
// @aa-sdd PK-991 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
// @aa-sdd PK-596 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
// @aa-sdd PK-726 docs/specs/common/server.spec.md
// @aa-sdd PK-796 docs/specs/common/roux.spec.md
pub enum Family {
    Ollama,
    Grok,
    Pool,
}

impl Family {
    /// The `label` method returns the visible family name.
    /// The words are `Ollama`, `Grok`, and `Pool`.
    pub fn label(self) -> &'static str {
        match self {
            Family::Ollama => "Ollama",
            Family::Grok => "Grok",
            Family::Pool => "Pool",
        }
    }

    /// The `word` method returns the stored family word.
    /// The words are `ollama`, `grok`, and `pool`.
    pub fn word(self) -> &'static str {
        match self {
            Family::Ollama => "ollama",
            Family::Grok => "grok",
            Family::Pool => "pool",
        }
    }

    /// The `parse` method reads a family word.
    /// The match is trimmed and lowercase. An unknown word returns `None`.
    pub fn parse(word: &str) -> Option<Self> {
        match word.trim().to_ascii_lowercase().as_str() {
            "ollama" => Some(Family::Ollama),
            "grok" => Some(Family::Grok),
            "pool" => Some(Family::Pool),
            _ => None,
        }
    }
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The [`ThinkSnap`] struct is the think state for one cell.
/// It is not a vault column. `ollama_on` is the Ollama checkbox. `ollama_level` is the Ollama effort. `ollama_id` is the Ollama model id. `grok_on` is the Grok checkbox. `pool_on` is the pool checkbox.
#[derive(Clone, Debug, PartialEq, Eq)]
pub struct ThinkSnap {
    pub ollama_on: bool,
    pub ollama_level: String,
    pub ollama_id: String,
    pub grok_on: bool,
    pub pool_on: bool,
}

impl ThinkSnap {
    /// The `off` method returns a snap with every box off.
    /// The strings are empty.
    pub fn off() -> Self {
        Self {
            ollama_on: false,
            ollama_level: String::new(),
            ollama_id: String::new(),
            grok_on: false,
            pool_on: false,
        }
    }
}

impl Default for ThinkSnap {
    /// The `default` method returns the same snap as `off`.
    /// Every box is off.
    fn default() -> Self {
        Self::off()
    }
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The [`GrokEntry`] struct is one Grok settings object.
/// Factory values match the Grok plan. `api` is the API word. `model` is the model id. `reasoning_effort` is the effort word. `send_reasoning_effort` decides whether that word is written. `stream` and `burner_cloud` are flags. `temperature` and `max_completion_tokens` are the call numbers.
#[derive(Clone, Debug, PartialEq)]
pub struct GrokEntry {
    pub api: String,
    pub model: String,
    pub reasoning_effort: String,
    pub send_reasoning_effort: bool,
    pub stream: bool,
    pub burner_cloud: bool,
    pub temperature: f64,
    pub max_completion_tokens: i32,
}

impl GrokEntry {
    /// The `factory` method returns the factory Grok entry.
    /// The API is `chat`. The model is `grok-4.3`. The effort is `high`. Send effort is true. Stream is false. Burner cloud is false. The temperature is 1.0. The max token count is 128000.
    pub fn factory() -> Self {
        Self {
            api: "chat".to_string(),
            model: "grok-4.3".to_string(),
            reasoning_effort: "high".to_string(),
            send_reasoning_effort: true,
            stream: false,
            burner_cloud: false,
            temperature: 1.0,
            max_completion_tokens: 128000,
        }
    }

    /// The `blank` method returns an entry whose model is `model`.
    /// The effort is `high`. Send effort follows `sends_effort` for that model. This is not [`GrokEntry::factory`] when the model is not `grok-4.3`.
    pub fn blank(model: &str) -> Self {
        let model = model.to_string();
        let mut entry = Self::factory();
        entry.model = model;
        entry.send_reasoning_effort = sends_effort(&entry.model);
        entry.reasoning_effort = "high".to_string();
        entry
    }

    /// The `copy` method clones the entry and fills two gaps.
    /// An empty API becomes `chat`. The effort is normalized for the model.
    pub fn copy(&self) -> Self {
        let mut next = self.clone();
        if next.api.is_empty() {
            next.api = "chat".to_string();
        }
        next.reasoning_effort = normalize_effort(&next.model, &next.reasoning_effort);
        next
    }
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The [`GrokDoc`] struct is the Grok settings document.
/// The file is `aetheris-grok.yaml` under `.casserole` in the home directory. A version other than 1 is left unread. A blocked document is not written. `path` is the file. `blocked` stops reads and writes. `standing` is the default entry. `cells` are the per-cell entries.
pub struct GrokDoc {
    path: Option<PathBuf>,
    blocked: bool,
    standing: Option<GrokEntry>,
    cells: Vec<(String, GrokEntry)>,
}

impl GrokDoc {
    /// The `memory` method returns a document with no file.
    /// The document is not blocked. Nothing is written.
    pub fn memory() -> Self {
        Self {
            path: None,
            blocked: false,
            standing: None,
            cells: Vec::new(),
        }
    }

    /// The `load` method reads `path`.
    /// A missing file stays empty. An empty file stays empty. A bad document is blocked.
    pub fn load(path: PathBuf) -> Self {
        let mut doc = Self {
            path: Some(path),
            blocked: false,
            standing: None,
            cells: Vec::new(),
        };
        doc.read();
        doc
    }

    /// The `blocked` method reports a document that was refused.
    /// True means the file is left unread and later writes return.
    pub fn blocked(&self) -> bool {
        self.blocked
    }

    /// The `standing` method returns a copy of the default entry.
    /// A blocked document returns `None`. A missing default returns `None`.
    pub fn standing(&self) -> Option<GrokEntry> {
        if self.blocked {
            return None;
        }
        self.standing.as_ref().map(GrokEntry::copy)
    }

    /// The `entry` method returns a copy of one cell.
    /// A blocked document returns `None`. A missing id returns `None`.
    pub fn entry(&self, cell_id: &str) -> Option<GrokEntry> {
        if self.blocked {
            return None;
        }
        self.cells
            .iter()
            .find(|(key, _)| key == cell_id)
            .map(|(_, entry)| entry.copy())
    }

    /// The `burner_cloud` method reports the burner flag of one cell.
    /// A missing cell returns false.
    pub fn burner_cloud(&self, cell_id: &str) -> bool {
        self.entry(cell_id).is_some_and(|entry| entry.burner_cloud)
    }

    /// The `write` method stores `entry` for `cell_id` and saves the file.
    /// A blocked document returns. An empty cell id returns. An empty model never becomes standing. A non-empty model becomes standing when `also_default` is set. A non-empty model also becomes standing when no standing row exists.
    pub fn write(&mut self, cell_id: &str, entry: &GrokEntry, also_default: bool) {
        if self.blocked || cell_id.is_empty() {
            return;
        }
        let stored = entry.copy();
        if let Some(slot) = self.cells.iter_mut().find(|(key, _)| key == cell_id) {
            slot.1 = stored.clone();
        } else {
            self.cells.push((cell_id.to_string(), stored.clone()));
        }
        if !stored.model.is_empty() && (also_default || self.standing.is_none()) {
            self.standing = Some(stored);
        }
        self.save();
    }

    /// The `copy_onto` method writes `source` onto `cell_id`.
    /// The standing row is not forced by this call.
    pub fn copy_onto(&mut self, cell_id: &str, source: &GrokEntry) {
        self.write(cell_id, source, false);
    }

    /// The `forget_all` method drops the named cells in one write.
    /// Standing stays. A blocked document returns. An empty list returns. A list that removes nothing does not write.
    pub fn forget_all(&mut self, ids: &[String]) {
        if self.blocked || ids.is_empty() {
            return;
        }
        let before = self.cells.len();
        self.cells.retain(|(key, _)| !ids.iter().any(|id| id == key));
        if self.cells.len() != before {
            self.save();
        }
    }

    /// The `console_seed` method returns five strings for a console whose Grok id is empty.
    /// The file is not written. A standing row with a model is used. Otherwise the factory entry is used. The five strings are the model, the effort, send effort, stream, and burner cloud. An empty effort becomes `high`.
    pub fn console_seed(&self) -> [String; 5] {
        let entry = match self.standing() {
            Some(entry) if !entry.model.trim().is_empty() => entry,
            _ => GrokEntry::factory(),
        };
        let effort = if entry.reasoning_effort.is_empty() {
            "high".to_string()
        } else {
            entry.reasoning_effort.clone()
        };
        [
            entry.model.trim().to_string(),
            effort,
            if entry.send_reasoning_effort { "true" } else { "false" }.to_string(),
            if entry.stream { "true" } else { "false" }.to_string(),
            if entry.burner_cloud { "true" } else { "false" }.to_string(),
        ]
    }

    /// The `read` method loads the file into this document.
    /// A document with no path returns. A missing file returns. An empty file returns. A blocked parse clears the rows and sets `blocked`. A good parse replaces standing and the cells.
    fn read(&mut self) {
        let Some(path) = &self.path else {
            return;
        };
        let Ok(bytes) = std::fs::read(path) else {
            return;
        };
        if bytes.is_empty() {
            return;
        }
        let text = String::from_utf8_lossy(&bytes);
        match parse_doc(&text) {
            Parse::Blocked => {
                self.blocked = true;
                self.standing = None;
                self.cells.clear();
            }
            Parse::Doc { standing, cells } => {
                self.standing = standing;
                self.cells = cells;
            }
        }
    }

    /// The `save` method writes the document.
    /// A blocked document returns. A document with no path returns. The parent directory is created. A failed write is ignored.
    fn save(&self) {
        if self.blocked {
            return;
        }
        let Some(path) = &self.path else {
            return;
        };
        if let Some(parent) = path.parent() {
            if !parent.as_os_str().is_empty() {
                let _ = std::fs::create_dir_all(parent);
            }
        }
        let _ = std::fs::write(path, dump_doc(self.standing.as_ref(), &self.cells));
    }
}

/// The `grok_path` function joins the settings path under `home`.
/// The folders are `.casserole` and `aetheris-grok.yaml`.
pub fn grok_path(home: &Path) -> PathBuf {
    home.join(".casserole").join(GROK_FILE)
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The `Parse` enum is the result of reading one settings document.
/// `Blocked` means the text is refused. `Doc` is a standing entry and the cell rows.
// @aa-sdd GK-1314 aetheris-plan-stage-1.001.md
// @aa-sdd PK-740 docs/specs/common/stages.spec.md
// @aa-sdd PK-597 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
// @aa-sdd PK-598 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
// @aa-sdd PK-599 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
enum Parse {
    Blocked,
    Doc {
        standing: Option<GrokEntry>,
        cells: Vec<(String, GrokEntry)>,
    },
}

/// The `parse_doc` function reads YAML text into a [`Parse`].
/// Bad YAML is blocked. A root that is not a map is blocked. A version other than 1 is blocked. An empty default model is dropped. A cell key that is not text is skipped. A cell value that is not a map is skipped.
fn parse_doc(text: &str) -> Parse {
    let value: serde_yaml::Value = match serde_yaml::from_str(text) {
        Ok(value) => value,
        Err(_) => return Parse::Blocked,
    };
    let Some(root) = value.as_mapping() else {
        return Parse::Blocked;
    };
    if !version_one(map_get(root, "version")) {
        return Parse::Blocked;
    }
    let standing = map_get(root, "default")
        .and_then(serde_yaml::Value::as_mapping)
        .map(parse_entry)
        .filter(|entry| !entry.model.is_empty());
    let mut cells = Vec::new();
    if let Some(map) = map_get(root, "cells").and_then(serde_yaml::Value::as_mapping) {
        for (key, raw) in map {
            let Some(key) = key.as_str() else {
                continue;
            };
            let Some(raw) = raw.as_mapping() else {
                continue;
            };
            cells.push((key.to_string(), parse_entry(raw)));
        }
    }
    Parse::Doc { standing, cells }
}

/// The `version_one` function reports version 1.
/// A missing value is false. An integer 1 is true. The text `1` is true after trim.
fn version_one(value: Option<&serde_yaml::Value>) -> bool {
    let Some(value) = value else {
        return false;
    };
    if let Some(n) = value.as_i64() {
        return n == 1;
    }
    if let Some(n) = value.as_u64() {
        return n == 1;
    }
    value
        .as_str()
        .is_some_and(|text| text.trim() == "1")
}

/// The `map_get` function returns one YAML map value.
/// The key is compared as a string.
fn map_get<'a>(map: &'a serde_yaml::Mapping, key: &str) -> Option<&'a serde_yaml::Value> {
    map.get(serde_yaml::Value::String(key.to_string()))
}

/// The `parse_entry` function reads one settings map.
/// Missing fields start from the factory entry. An empty API becomes `chat`. A missing send flag uses `sends_effort`. A present send flag uses the stored bool. A missing temperature keeps 1.0. A missing max token count keeps 128000.
fn parse_entry(raw: &serde_yaml::Mapping) -> GrokEntry {
    let mut entry = GrokEntry::factory();
    let api = yaml_text(map_get(raw, "api"));
    entry.api = if api.is_empty() { "chat".to_string() } else { api };
    entry.model = yaml_text(map_get(raw, "model"));
    let effort = yaml_text(map_get(raw, "reasoning_effort"));
    entry.reasoning_effort = normalize_effort(&entry.model, &effort);
    if map_get(raw, "send_reasoning_effort").is_some() {
        entry.send_reasoning_effort = yaml_bool(map_get(raw, "send_reasoning_effort"));
    } else {
        entry.send_reasoning_effort = sends_effort(&entry.model);
    }
    entry.stream = yaml_bool(map_get(raw, "stream"));
    entry.burner_cloud = yaml_bool(map_get(raw, "burner_cloud"));
    if let Some(n) = map_get(raw, "temperature").and_then(yaml_f64) {
        entry.temperature = n;
    }
    if let Some(n) = map_get(raw, "max_completion_tokens").and_then(yaml_i32) {
        entry.max_completion_tokens = n;
    }
    entry
}

/// The `yaml_text` function reads one scalar as text.
/// A missing value is empty. A string is trimmed. A number uses `number_text` when it is a float. A bool is `true` or `false`.
fn yaml_text(value: Option<&serde_yaml::Value>) -> String {
    let Some(value) = value else {
        return String::new();
    };
    if let Some(text) = value.as_str() {
        return text.trim().to_string();
    }
    if let Some(n) = value.as_i64() {
        return n.to_string();
    }
    if let Some(n) = value.as_f64() {
        return number_text(n);
    }
    if let Some(flag) = value.as_bool() {
        return if flag { "true".to_string() } else { "false".to_string() };
    }
    String::new()
}

/// The `yaml_bool` function reads one scalar as a bool.
/// A missing value is false. A bool is itself. The integer 1 is true. The words `true`, `yes`, and `1` are true.
fn yaml_bool(value: Option<&serde_yaml::Value>) -> bool {
    let Some(value) = value else {
        return false;
    };
    if let Some(flag) = value.as_bool() {
        return flag;
    }
    if let Some(n) = value.as_i64() {
        return n == 1;
    }
    let raw = yaml_text(Some(value));
    raw.eq_ignore_ascii_case("true") || raw.eq_ignore_ascii_case("yes") || raw == "1"
}

/// The `yaml_f64` function reads a float.
/// A float, an integer, or a trimmed number string is accepted. Any other shape returns `None`.
fn yaml_f64(value: &serde_yaml::Value) -> Option<f64> {
    if let Some(n) = value.as_f64() {
        return Some(n);
    }
    if let Some(n) = value.as_i64() {
        return Some(n as f64);
    }
    value.as_str().and_then(|text| text.trim().parse().ok())
}

/// The `yaml_i32` function reads an `i32`.
/// A signed integer, an unsigned integer, or a trimmed number string is accepted when it fits. Any other shape returns `None`.
fn yaml_i32(value: &serde_yaml::Value) -> Option<i32> {
    if let Some(n) = value.as_i64() {
        return i32::try_from(n).ok();
    }
    if let Some(n) = value.as_u64() {
        return i32::try_from(n).ok();
    }
    value.as_str().and_then(|text| text.trim().parse().ok())
}

/// The `dump_doc` function writes the document as YAML.
/// The version line is `version: 1`. A standing entry is written under `default`. An empty cell map is `cells: {}`.
fn dump_doc(standing: Option<&GrokEntry>, cells: &[(String, GrokEntry)]) -> String {
    let mut out = String::from("version: 1\n");
    if let Some(entry) = standing {
        out.push_str("default:\n");
        dump_entry(&mut out, entry, "  ");
    }
    if cells.is_empty() {
        out.push_str("cells: {}\n");
    } else {
        out.push_str("cells:\n");
        for (key, entry) in cells {
            out.push_str("  ");
            out.push_str(&yaml_key(key));
            out.push_str(":\n");
            dump_entry(&mut out, entry, "    ");
        }
    }
    out
}

/// The `dump_entry` function writes one entry.
/// An empty API is written as `chat`. The effort is normalized. Temperature uses `wire_double`.
fn dump_entry(out: &mut String, entry: &GrokEntry, indent: &str) {
    let api = if entry.api.is_empty() { "chat" } else { entry.api.as_str() };
    let effort = normalize_effort(&entry.model, &entry.reasoning_effort);
    push_line(out, indent, "api", api);
    push_line(out, indent, "model", &entry.model);
    push_line(out, indent, "reasoning_effort", &effort);
    push_line(
        out,
        indent,
        "send_reasoning_effort",
        if entry.send_reasoning_effort { "true" } else { "false" },
    );
    push_line(out, indent, "stream", if entry.stream { "true" } else { "false" });
    push_line(
        out,
        indent,
        "burner_cloud",
        if entry.burner_cloud { "true" } else { "false" },
    );
    push_line(out, indent, "temperature", &crate::amwp::wire_double(entry.temperature));
    push_line(
        out,
        indent,
        "max_completion_tokens",
        &entry.max_completion_tokens.to_string(),
    );
}

/// The `push_line` function appends one YAML line.
/// The value is escaped by `yaml_scalar`.
fn push_line(out: &mut String, indent: &str, key: &str, value: &str) {
    out.push_str(indent);
    out.push_str(key);
    out.push_str(": ");
    out.push_str(&yaml_scalar(value));
    out.push('\n');
}

/// The `yaml_key` function quotes a key that is not a plain token.
/// A plain token is a non-empty run of ASCII letters, digits, `-`, and `_`. A quoted key escapes backslash and quote.
fn yaml_key(key: &str) -> String {
    if key.chars().all(|c| c.is_ascii_alphanumeric() || c == '-' || c == '_') && !key.is_empty() {
        key.to_string()
    } else {
        format!("\"{}\"", key.replace('\\', "\\\\").replace('"', "\\\""))
    }
}

/// The `yaml_scalar` function quotes a scalar that YAML would misread.
/// An empty value is quoted. A value with whitespace or a YAML marker is quoted. The words `true`, `false`, `null`, `yes`, `no`, and `~` are quoted.
fn yaml_scalar(value: &str) -> String {
    if value.is_empty()
        || value.chars().any(|c| c.is_whitespace() || matches!(c, ':' | '#' | '"' | '\'' | '{' | '}' | '[' | ']' | ',' | '&' | '*' | '!' | '|' | '>' | '%' | '@' | '`'))
        || matches!(value, "true" | "false" | "null" | "yes" | "no" | "~")
    {
        format!("\"{}\"", value.replace('\\', "\\\\").replace('"', "\\\""))
    } else {
        value.to_string()
    }
}

/// The `canvas_tune` function builds the execute tune for a canvas run.
/// Grok carries the document. Ollama carries `think` when the box is on. An empty Ollama level is the word `true`. Pool carries `think: true` when its box is on. Pool also carries `pool_boot`. An empty stored word is `ollama`.
pub fn canvas_tune(
    stored: &str,
    ephemeral: bool,
    think: &ThinkSnap,
    entry: Option<&GrokEntry>,
    pool_boot: &str,
) -> ExecuteTune {
    let target = outbound_target(stored);
    let provider = provider_id(&target);
    let mut tune = ExecuteTune::ollama(ephemeral);
    tune.target = target;
    if provider == "grok" {
        let model_id = entry.map(|entry| entry.model.clone()).unwrap_or_default();
        fill_grok(&mut tune, entry, true, &model_id);
    } else if provider == "ollama" && think.ollama_on {
        tune.think = if think.ollama_level.is_empty() {
            "true".to_string()
        } else {
            think.ollama_level.clone()
        };
    } else if provider == "pool" {
        if think.pool_on {
            tune.think = "true".to_string();
        }
        tune.pool_boot = pool_boot.to_string();
    }
    tune
}

/// The `mood_tune` function builds the execute tune for mood shaping.
/// Think stays off the call. Grok stream stays off. A Grok target with no model id uses the document model when that model is not empty. A pool target is stored as the word `pool`. Pool sends the boot id.
pub fn mood_tune(
    stored: &str,
    ephemeral: bool,
    entry: Option<&GrokEntry>,
    pool_boot: &str,
) -> ExecuteTune {
    let outbound = outbound_target(stored);
    let provider = provider_id(&outbound);
    let mut target = outbound;
    if provider == "pool" {
        target = "pool".to_string();
    } else if provider == "grok" && model_after_slash(&target).is_empty() {
        if let Some(entry) = entry {
            let model = entry.model.trim();
            if !model.is_empty() {
                target = format!("grok/{model}");
            }
        }
    }
    let mut tune = ExecuteTune::ollama(ephemeral);
    tune.target = target.clone();
    if provider_id(&target) == "grok" {
        fill_grok(&mut tune, entry, false, &model_after_slash(&target));
    } else if provider_id(&target) == "pool" {
        tune.pool_boot = pool_boot.to_string();
    }
    tune
}

/// The `fill_grok` function copies Grok fields onto a tune.
/// The effort is normalized for `model_id`. A missing entry uses `sends_effort` and the factory temperature and max token count. `think_stream` is set only when `stream_from_entry` is true and the entry streams. `think` is cleared.
fn fill_grok(
    tune: &mut ExecuteTune,
    entry: Option<&GrokEntry>,
    stream_from_entry: bool,
    model_id: &str,
) {
    let raw = entry.map(|entry| entry.reasoning_effort.clone()).unwrap_or_default();
    tune.reasoning_effort = normalize_effort(model_id, &raw);
    tune.send_reasoning_effort = entry
        .map(|entry| entry.send_reasoning_effort)
        .unwrap_or_else(|| sends_effort(model_id));
    tune.burner_cloud = entry.is_some_and(|entry| entry.burner_cloud);
    tune.think_stream = stream_from_entry && entry.is_some_and(|entry| entry.stream);
    tune.temperature = Some(entry.map(|entry| entry.temperature).unwrap_or(1.0));
    tune.max_completion_tokens = Some(entry.map(|entry| entry.max_completion_tokens).unwrap_or(128000));
    tune.think.clear();
}

/// The `grok_burner_allows` function reports a burner hop to Grok.
/// The hop is allowed when the provider is `grok` and this cell's box is checked.
pub fn grok_burner_allows(stored: &str, burner_cloud: bool) -> bool {
    provider_id(stored) == "grok" && burner_cloud
}

/// The `nearest_grok` function returns the nearest earlier cell whose Grok model is non-empty.
/// Index 0 returns `None`. An index past `keys` returns `None`. The search walks backward.
pub fn nearest_grok<'a>(
    keys: &'a [String],
    index: usize,
    doc: &'a GrokDoc,
) -> Option<(&'a str, GrokEntry)> {
    if index == 0 || index > keys.len() {
        return None;
    }
    for key in keys[..index].iter().rev() {
        if let Some(entry) = doc.entry(key) {
            if !entry.model.is_empty() {
                return Some((key.as_str(), entry));
            }
        }
    }
    None
}

/// The `adopt_grok` function crosses into Grok from the group field.
/// An existing document for `key` is left as typed and `target` is returned. A typed model id is written from the nearest earlier cell or from standing. A blank crossing copies a non-empty source model. A crossing with no model writes a blank entry and returns `grok/`.
pub fn adopt_grok(doc: &mut GrokDoc, key: &str, target: &str, earlier: &[String], index: usize) -> String {
    if doc.entry(key).is_some() {
        return target.to_string();
    }
    let mut typed = String::new();
    let mut stored = target.to_string();
    if provider_id(&stored) == "grok" {
        typed = model_after_slash(&stored);
    } else {
        stored = "grok/".to_string();
    }
    let nearest = nearest_grok(earlier, index, doc);
    let standing = doc.standing();
    let (source, source_key) = if let Some((id, entry)) = nearest {
        (Some(entry), Some(id.to_string()))
    } else {
        (standing, None)
    };
    if !typed.is_empty() {
        let mut entry = source.unwrap_or_else(|| GrokEntry::blank(&typed));
        entry.model = typed.clone();
        entry.reasoning_effort = normalize_effort(&typed, &entry.reasoning_effort);
        doc.write(key, &entry, false);
        let _ = source_key;
        return format!("grok/{typed}");
    }
    if let Some(entry) = source.as_ref() {
        if !entry.model.is_empty() {
            let model = entry.model.clone();
            let mut entry = entry.copy();
            entry.reasoning_effort = normalize_effort(&entry.model, &entry.reasoning_effort);
            doc.write(key, &entry, false);
            return format!("grok/{model}");
        }
    }
    doc.write(key, &GrokEntry::blank(""), false);
    stored
}

/// The `target_keystroke` function handles one keystroke in the group field.
/// Leaving Ollama stores that model id on `think`. The returned text is `text`.
pub fn target_keystroke(previous: &str, text: &str, think: &mut ThinkSnap) -> String {
    let prior = previous;
    if provider_id(prior) == "ollama" && provider_id(text) != "ollama" {
        think.ollama_id = model_after_slash(prior);
    }
    text.to_string()
}

/// The `target_focus_loss` function stores the group field when focus leaves.
/// Bare `ollama` clears the stored text. Bare `ollama/` clears it. A pool word stores `pool`. A stored Ollama id is copied onto `think`.
pub fn target_focus_loss(text: &str, think: &mut ThinkSnap) -> String {
    let trimmed = text.trim();
    let mut stored = text.to_string();
    if trimmed == "ollama" || trimmed == "ollama/" {
        stored.clear();
    }
    if provider_id(trimmed) == "pool" {
        stored = "pool".to_string();
    }
    if provider_id(&stored) == "ollama" {
        think.ollama_id = model_after_slash(&stored);
    }
    stored
}

/// The `sync_grok_model` function writes a Grok model id onto the existing document.
/// A provider other than `grok` returns. A missing cell returns. An empty id returns. An id that already matches returns.
pub fn sync_grok_model(doc: &mut GrokDoc, key: &str, stored: &str) {
    if provider_id(stored) != "grok" {
        return;
    }
    let Some(mut entry) = doc.entry(key) else {
        return;
    };
    let model = model_after_slash(stored);
    if model.is_empty() || model == entry.model {
        return;
    }
    entry.model = model.clone();
    entry.reasoning_effort = normalize_effort(&model, &entry.reasoning_effort);
    doc.write(key, &entry, false);
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// @aa-sdd GK-1119 aetheris-grok-api.draft.001.md
/// The [`ModelWire`] enum is one request the dialog wants written.
/// `Tags` asks for the Ollama catalog. `GrokModels` asks for the Grok language-model catalog. `Show` asks for one Ollama model. `ShellModel` asks for the console model. `ShellSet` carries a console settings body.
#[derive(Clone, Debug, PartialEq, Eq)]
// @aa-sdd PK-992 docs/specs/rust/aetheris/aetheris-client/src/model.spec.md
pub enum ModelWire {
    Tags { cell: [u8; 16] },
    GrokModels { cell: [u8; 16] },
    Show { cell: [u8; 16], id: String },
    ShellModel,
    ShellSet(String),
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The [`ModelEffect`] struct is the result of one dialog change.
/// `target` is a new stored target. `think` is a new think snap. `wires` are the requests to write. `status` is a status word, or `None` when the change is quiet.
#[derive(Clone, Debug, PartialEq)]
pub struct ModelEffect {
    pub target: Option<String>,
    pub think: Option<ThinkSnap>,
    pub wires: Vec<ModelWire>,
    pub status: Option<&'static str>,
}

impl ModelEffect {
    /// The `quiet` method returns an effect with no target, no think, no wires, and no status.
    /// Callers use it when nothing should change.
    fn quiet() -> Self {
        Self {
            target: None,
            think: None,
            wires: Vec::new(),
            status: None,
        }
    }

    /// The `wires` method returns an effect that carries only `wires`.
    /// Target, think, and status stay empty.
    fn wires(wires: Vec<ModelWire>) -> Self {
        Self {
            target: None,
            think: None,
            wires,
            status: None,
        }
    }
}

/// The [`ModelSession`] struct is the one model dialog.
/// Close leaves the cell as last written. `open` means the dialog is showing. `console` means this is the console dialog. `dismissed` blocks a later shell open. `cell` and `cell_key` name the cell. `family` is the selected family.
pub struct ModelSession {
    pub open: bool,
    pub console: bool,
    pub dismissed: bool,
    pub cell: [u8; 16],
    pub cell_key: String,
    pub family: Option<Family>,
    pub ollama_tags: Vec<String>,
    pub ollama_id: String,
    pub ollama_think: bool,
    pub ollama_levels: Vec<String>,
    pub ollama_level: Option<String>,
    pub show_id: String,
    pub grok_choices: Vec<String>,
    pub grok_model: Option<String>,
    /// The red model-list notice. `None` means the last list had rows.
    pub list_miss: Option<&'static str>,
    /// Increases each time the notice is shown or cleared.
    pub list_miss_gen: u64,
    pub grok_efforts: Vec<String>,
    pub grok_effort: String,
    pub send_effort: bool,
    pub effort_enabled: bool,
    pub stream: bool,
    pub burner_cloud: bool,
    pub temperature: String,
    pub max_tokens: String,
    pub grok_think: bool,
    pub pool_think: bool,
    console_provider: String,
    console_ollama: String,
    /// The `console_think` field is the console Ollama think string.
    /// Java calls this `consoleOllamaThink`. The gate reads this string.
    console_think: String,
    /// The `console_level` field is the console Ollama level kept across a show reply.
    /// Java calls this `dialogConsoleThink`. A show reply restores this level.
    console_level: String,
    console_grok: Option<GrokEntry>,
    console_grok_think: String,
    console_pool_on: bool,
}

impl ModelSession {
    /// The `shut` method returns a closed dialog.
    /// The family is unset. The Grok effort list is `high`. Send effort is true. The temperature text is `1`. The max token text is `128000`.
    pub fn shut() -> Self {
        Self {
            open: false,
            console: false,
            dismissed: false,
            cell: [0u8; 16],
            cell_key: String::new(),
            family: None,
            ollama_tags: Vec::new(),
            ollama_id: String::new(),
            ollama_think: false,
            ollama_levels: Vec::new(),
            ollama_level: None,
            show_id: String::new(),
            grok_choices: Vec::new(),
            grok_model: None,
            list_miss: None,
            list_miss_gen: 0,
            grok_efforts: vec!["high".to_string()],
            grok_effort: "high".to_string(),
            send_effort: true,
            effort_enabled: true,
            stream: false,
            burner_cloud: false,
            temperature: "1".to_string(),
            max_tokens: "128000".to_string(),
            grok_think: false,
            pool_think: false,
            console_provider: String::new(),
            console_ollama: String::new(),
            console_think: String::new(),
            console_level: String::new(),
            console_grok: None,
            console_grok_think: String::new(),
            console_pool_on: false,
        }
    }

    /// The `family_label` method returns the visible family name.
    /// A missing family returns an empty string.
    pub fn family_label(&self) -> String {
        self.family.map(Family::label).unwrap_or("").to_string()
    }

    /// The `console_pool_thinking` method reports console pool thinking.
    /// Java calls this `consolePoolThinking`. The value is `console_pool_on`.
    pub fn console_pool_thinking(&self) -> bool {
        self.console_pool_on
    }

    /// The `console_thought_open` method reports whether the console thought strip is open.
    /// Pool opens on the pool switch. Grok opens when `grok_think` is `true`. An empty family reads as Ollama. Ollama opens when the think string is set and is not `false`. Any other family returns false. Java calls this `consoleThoughtOpen`.
    pub fn console_thought_open(&self) -> bool {
        if self.console_provider == "pool" {
            return self.console_pool_on;
        }
        if self.console_provider == "grok" {
            return self.console_grok_think.trim() == "true";
        }
        let family = self.console_provider.trim();
        if !family.is_empty() && family != "ollama" {
            return false;
        }
        let level = self.console_think.trim();
        !level.is_empty() && level != "false"
    }

    /// The `console_thought_tag` method returns the short name on the console thought strip.
    /// Pool reads `pool`. Grok reads the Grok id, or `grok` when that id is empty. Ollama reads the model id, or `ollama` when that id is empty. Java calls this `consoleThoughtTag`.
    pub fn console_thought_tag(&self) -> String {
        if self.console_provider == "pool" {
            return "pool".to_string();
        }
        if self.console_provider == "grok" {
            let id = self
                .console_grok
                .as_ref()
                .map(|entry| entry.model.trim().to_string())
                .unwrap_or_default();
            if id.is_empty() {
                return "grok".to_string();
            }
            let tagged = short_model_name(&format!("grok/{id}"));
            return if tagged.is_empty() { id } else { tagged };
        }
        let id = self.console_ollama.trim();
        if id.is_empty() {
            return "ollama".to_string();
        }
        let tagged = short_model_name(&format!("ollama/{id}"));
        if tagged.is_empty() { id.to_string() } else { tagged }
    }

    /// The `console_engine_word` method returns the SSH strip word for the stored console model.
    /// Pool, Grok, and Ollama use [`engine_from_console`]. A canvas cell is not this word.
    /// // @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230
    pub fn console_engine_word(&self) -> String {
        let grok = self
            .console_grok
            .as_ref()
            .map(|entry| entry.model.clone())
            .unwrap_or_default();
        engine_from_console(&self.console_provider, &self.console_ollama, &grok)
    }

    /// The `show_list_miss` method clears the named list and stores the red notice.
    /// A Grok miss clears the Grok rows. An Ollama miss clears the Ollama rows.
    /// The stored model id stays. The generation increases so the slide can open again.
    /// // @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230
    pub fn show_list_miss(&mut self, words: &'static str) {
        if words == GROK_LIST_MISS {
            self.grok_choices.clear();
        } else if words == OLLAMA_LIST_MISS {
            self.ollama_tags.clear();
        }
        self.list_miss = Some(words);
        self.list_miss_gen = self.list_miss_gen.wrapping_add(1);
    }

    /// The `clear_list_miss` method drops the red notice after a list arrives.
    /// A notice that is already clear stays clear and does not bump the generation.
    fn clear_list_miss(&mut self) {
        if self.list_miss.is_none() {
            return;
        }
        self.list_miss = None;
        self.list_miss_gen = self.list_miss_gen.wrapping_add(1);
    }

    /// The `doorbell` method handles a bare `select-model` sequence.
    /// `dismissed` is cleared. An open dialog stays open and the method returns false. A shut dialog returns true.
    pub fn doorbell(&mut self) -> bool {
        self.dismissed = false;
        !self.open
    }

    /// The `open_cell` method opens the dialog for one cell.
    /// An open dialog returns a quiet effect. The cell, the key, and the think snap are stored. A Grok row is loaded from the document. An Ollama family also queues a tags request and a show request. A Grok family queues a language-model request. The combo stays empty until that reply has model lines.
    pub fn open_cell(
        &mut self,
        cell: [u8; 16],
        key: &str,
        stored: &str,
        think: &ThinkSnap,
        doc: &mut GrokDoc,
    ) -> ModelEffect {
        if self.open {
            return ModelEffect::quiet();
        }
        self.console = false;
        self.cell = cell;
        self.cell_key = key.to_string();
        self.show_id.clear();
        self.ollama_levels.clear();
        self.ollama_level = None;
        let provider = provider_id(stored);
        let mut think = think.clone();
        let seed = if provider == "ollama" {
            let id = model_after_slash(stored);
            think.ollama_id = id.clone();
            id
        } else {
            think.ollama_id.clone()
        };
        self.seat_family(provider.as_str());
        self.ollama_id = seed;
        self.ollama_think = think.ollama_on;
        self.pool_think = think.pool_on;
        self.load_grok(doc.entry(key).as_ref(), think.grok_on);
        self.open = true;
        let mut effect = ModelEffect::quiet();
        effect.think = Some(think);
        if self.family == Some(Family::Ollama) {
            self.show_id = self.ollama_id.clone();
            effect.wires.push(ModelWire::Tags { cell });
            effect.wires.push(ModelWire::Show {
                cell,
                id: self.ollama_id.clone(),
            });
        }
        if self.family == Some(Family::Grok) {
            // @aa-sdd GK-1120 aetheris-grok-api.draft.001.md
            effect.wires.push(ModelWire::GrokModels { cell });
        }
        effect
    }

    /// The `seat_console_result` method stores a background shell answer.
    /// A key other than [`CONSOLE_CELL`] returns false. An open gate stays open. The other SSH session's empty `think:` line does not shut this one. A thought that was open is restored when the new text would close it.
    pub fn seat_console_result(&mut self, cell_key: &str, text: &str) -> bool {
        if cell_key != CONSOLE_CELL {
            return false;
        }
        let was_open = self.console_thought_open();
        let provider = self.console_provider.clone();
        let ollama = self.console_ollama.clone();
        let think = self.console_think.clone();
        let level = self.console_level.clone();
        let pool = self.console_pool_on;
        if !self.seat_shell(cell_key, text) {
            return false;
        }
        if was_open && !self.console_thought_open() {
            self.console_provider = provider;
            self.console_ollama = ollama;
            self.console_think = think;
            self.console_level = level;
            self.console_pool_on = pool;
        }
        true
    }

    /// The `seat_shell` method stores a shell answer.
    /// The dialog stays shut. Java holds the file. A key other than [`CONSOLE_CELL`] returns false. An open dialog returns false. An empty provider becomes `ollama`. An empty `ollama_model` falls back to `model`.
    pub fn seat_shell(&mut self, cell_key: &str, text: &str) -> bool {
        if cell_key != CONSOLE_CELL || self.open {
            return false;
        }
        let mut provider = shell_line(text, "provider:");
        if provider.is_empty() {
            provider = "ollama".to_string();
        }
        self.console_provider = provider;
        let mut ollama = shell_line(text, "ollama_model:");
        if ollama.is_empty() {
            ollama = shell_line(text, "model:");
        }
        self.console_ollama = ollama;
        let grok_model = shell_line(text, "grok_model:");
        let mut entry = GrokEntry::blank(&grok_model);
        let effort = shell_line(text, "effort:");
        if !effort.is_empty() {
            entry.reasoning_effort = normalize_effort(&grok_model, &effort);
        }
        let send = shell_line(text, "send_effort:");
        if send == "false" {
            entry.send_reasoning_effort = false;
        } else if send == "true" {
            entry.send_reasoning_effort = true;
        }
        entry.stream = shell_line(text, "stream:") == "true";
        entry.burner_cloud = shell_line(text, "burner:") == "true";
        let temp = shell_line(text, "temperature:");
        if let Ok(parsed) = temp.parse::<f64>() {
            if parsed.is_finite() {
                entry.temperature = parsed;
            }
        }
        let max = shell_line(text, "max_tokens:");
        if let Ok(parsed) = max.parse::<i32>() {
            if parsed >= 1 {
                entry.max_completion_tokens = parsed;
            }
        }
        self.console_grok = Some(entry);
        self.console_grok_think = shell_line(text, "grok_think:");
        self.console_pool_on = shell_line(text, "pool_think:") == "true";
        let think = shell_line(text, "think:");
        self.console_think = think.clone();
        self.console_level = think;
        true
    }

    /// The `apply_shell` method stores a shell answer and opens the console dialog.
    /// A key other than [`CONSOLE_CELL`] returns a quiet effect. An open dialog returns a quiet effect. A dismissed dialog returns a quiet effect.
    pub fn apply_shell(&mut self, cell_key: &str, text: &str, doc: &GrokDoc) -> ModelEffect {
        if cell_key != CONSOLE_CELL || self.open || self.dismissed {
            return ModelEffect::quiet();
        }
        if !self.seat_shell(cell_key, text) {
            return ModelEffect::quiet();
        }
        let ollama = self.console_ollama.clone();
        let think = self.console_think.clone();
        self.open_console(&ollama, &think, doc)
    }

    /// The `open_console` method opens the console dialog from the stored shell answer.
    /// An open dialog returns a quiet effect. The cell id is [`CONSOLE_CELL`]. The document argument is not read. An Ollama family queues tags and show on the zero cell. A Grok family queues a language-model request on the zero cell.
    fn open_console(&mut self, model: &str, think: &str, doc: &GrokDoc) -> ModelEffect {
        if self.open {
            return ModelEffect::quiet();
        }
        self.console = true;
        self.cell = [0u8; 16];
        self.cell_key = CONSOLE_CELL.to_string();
        self.show_id.clear();
        self.ollama_levels.clear();
        self.ollama_level = None;
        let provider = if self.console_provider.is_empty() {
            "ollama".to_string()
        } else {
            self.console_provider.clone()
        };
        self.seat_family(&provider);
        self.ollama_id = model.trim().to_string();
        let seed = think.trim();
        self.ollama_think = !seed.is_empty() && seed != "false";
        self.pool_think = self.console_pool_on;
        let grok_on = self.console_grok_think == "true";
        let entry = self.console_grok.clone();
        self.load_grok(entry.as_ref(), grok_on);
        self.open = true;
        let mut effect = ModelEffect::wires(Vec::new());
        if self.family == Some(Family::Ollama) {
            self.show_id = self.ollama_id.clone();
            effect.wires.push(ModelWire::Tags { cell: [0u8; 16] });
            effect.wires.push(ModelWire::Show {
                cell: [0u8; 16],
                id: self.ollama_id.clone(),
            });
        }
        if self.family == Some(Family::Grok) {
            // @aa-sdd GK-1120 aetheris-grok-api.draft.001.md
            effect.wires.push(ModelWire::GrokModels { cell: [0u8; 16] });
        }
        let _ = doc;
        effect
    }

    /// The `close` method shuts the dialog.
    /// The cell id is cleared. The family is cleared. `dismissed` is left as it was.
    pub fn close(&mut self) {
        self.open = false;
        self.console = false;
        self.cell = [0u8; 16];
        self.cell_key.clear();
        self.family = None;
        self.show_id.clear();
    }

    /// The `dismiss` method marks the dialog dismissed and then closes it.
    /// A later shell open stays quiet while `dismissed` is set.
    pub fn dismiss(&mut self) {
        self.dismissed = true;
        self.close();
    }

    /// The `choose_family` method selects a family and writes the matching target.
    /// The family is stored even when the dialog is shut. A console dialog uses the console path. Ollama stores `ollama/` plus the remembered id, or an empty target when that id is empty. Grok copies or adopts a document row. Pool stores the word `pool`. Ollama also queues tags and show. Grok also queues a language-model request.
    pub fn choose_family(
        &mut self,
        family: Family,
        stored: &str,
        think: &ThinkSnap,
        doc: &mut GrokDoc,
        earlier: &[String],
        index: usize,
        grok_flags: &[(&str, bool)],
    ) -> ModelEffect {
        if !self.open || self.family == Some(family) {
            self.family = Some(family);
        }
        self.family = Some(family);
        self.clear_list_miss();
        if self.console {
            return self.choose_console_family(family, doc);
        }
        let mut think = think.clone();
        let mut target = stored.to_string();
        match family {
            Family::Ollama => {
                let id = think.ollama_id.trim().to_string();
                self.ollama_id = id.clone();
                target = if id.is_empty() {
                    String::new()
                } else {
                    format!("ollama/{id}")
                };
                think.ollama_id = id;
            }
            Family::Grok => {
                if provider_id(stored) == "ollama" {
                    think.ollama_id = self.ollama_id.trim().to_string();
                }
                target = self.apply_grok_family(&target, doc, earlier, index, &mut think, grok_flags);
                self.load_grok(doc.entry(&self.cell_key).as_ref(), think.grok_on);
            }
            Family::Pool => {
                if provider_id(stored) == "ollama" {
                    think.ollama_id = self.ollama_id.trim().to_string();
                }
                target = "pool".to_string();
                self.pool_think = think.pool_on;
            }
        }
        let mut effect = ModelEffect {
            target: Some(target),
            think: Some(think),
            wires: Vec::new(),
            status: None,
        };
        if family == Family::Ollama {
            effect.wires.push(ModelWire::Tags { cell: self.cell });
            effect.wires.push(ModelWire::Show {
                cell: self.cell,
                id: self.ollama_id.clone(),
            });
        }
        if family == Family::Grok {
            // @aa-sdd GK-1120 aetheris-grok-api.draft.001.md
            effect.wires.push(ModelWire::GrokModels { cell: self.cell });
        }
        effect
    }

    /// The `choose_console_family` method selects a family on the console dialog.
    /// Grok loads a console entry from the document when the current one is empty. The effect is a shell set. Ollama inserts tags and show ahead of that set. Grok inserts a language-model request ahead of that set.
    fn choose_console_family(&mut self, family: Family, doc: &GrokDoc) -> ModelEffect {
        if family == Family::Grok {
            self.ensure_console_grok(doc);
            let grok_on = self.console_grok_think == "true";
            let entry = self.console_grok.clone();
            self.load_grok(entry.as_ref(), grok_on);
        }
        let mut effect = self.shell_effect();
        if family == Family::Ollama {
            effect.wires.insert(0, ModelWire::Show {
                cell: [0u8; 16],
                id: self.ollama_id.clone(),
            });
            effect.wires.insert(0, ModelWire::Tags { cell: [0u8; 16] });
        }
        if family == Family::Grok {
            // @aa-sdd GK-1120 aetheris-grok-api.draft.001.md
            effect.wires.insert(0, ModelWire::GrokModels { cell: [0u8; 16] });
        }
        effect
    }

    /// The `pick_grok_model` method chooses one Grok model.
    /// A shut dialog returns a quiet effect. Send effort follows `sends_effort` for that model. A console dialog returns a shell set. Any other dialog writes the cell and returns `grok/` plus the model.
    pub fn pick_grok_model(&mut self, model: &str, doc: &mut GrokDoc) -> ModelEffect {
        if !self.open {
            return ModelEffect::quiet();
        }
        self.grok_model = Some(model.to_string());
        self.fill_grok_effort(model, &self.grok_effort.clone());
        self.send_effort = sends_effort(model);
        self.effort_enabled = self.send_effort;
        if self.console {
            return self.shell_effect();
        }
        let target = format!("grok/{model}");
        self.write_grok(doc);
        ModelEffect {
            target: Some(target),
            think: None,
            wires: Vec::new(),
            status: None,
        }
    }

    /// The `set_send_effort` method stores whether the effort word is sent.
    /// `effort_enabled` follows the same flag. The Grok document is touched.
    pub fn set_send_effort(&mut self, on: bool, doc: &mut GrokDoc) -> ModelEffect {
        self.send_effort = on;
        self.effort_enabled = on;
        self.touch_grok(doc)
    }

    /// The `set_grok_effort` method stores the Grok effort word.
    /// The Grok document is touched.
    pub fn set_grok_effort(&mut self, level: &str, doc: &mut GrokDoc) -> ModelEffect {
        self.grok_effort = level.to_string();
        self.touch_grok(doc)
    }

    /// The `set_stream` method stores the Grok stream flag.
    /// The Grok document is touched.
    pub fn set_stream(&mut self, on: bool, doc: &mut GrokDoc) -> ModelEffect {
        self.stream = on;
        self.touch_grok(doc)
    }

    /// The `set_burner` method stores the burner-cloud flag.
    /// The Grok document is touched.
    pub fn set_burner(&mut self, on: bool, doc: &mut GrokDoc) -> ModelEffect {
        self.burner_cloud = on;
        self.touch_grok(doc)
    }

    /// The `set_grok_think` method stores the Grok think checkbox.
    /// A console dialog returns a shell set. Any other dialog returns the snap with `grok_on` set.
    pub fn set_grok_think(&mut self, on: bool, think: &ThinkSnap) -> ModelEffect {
        self.grok_think = on;
        if self.console {
            self.console_grok_think = if on { "true".to_string() } else { String::new() };
            return self.shell_effect();
        }
        let mut think = think.clone();
        think.grok_on = on;
        ModelEffect {
            target: None,
            think: Some(think),
            wires: Vec::new(),
            status: None,
        }
    }

    /// The `set_pool_think` method stores the pool think checkbox.
    /// A console dialog returns a shell set. Any other dialog returns the snap with `pool_on` set.
    pub fn set_pool_think(&mut self, on: bool, think: &ThinkSnap) -> ModelEffect {
        self.pool_think = on;
        if self.console {
            self.console_pool_on = on;
            return self.shell_effect();
        }
        let mut think = think.clone();
        think.pool_on = on;
        ModelEffect {
            target: None,
            think: Some(think),
            wires: Vec::new(),
            status: None,
        }
    }

    /// The `set_temperature` method stores a temperature.
    /// A console dialog checks the text through the shell body. A value that is not a finite float returns the status word `settings`. A good value is written on the cell and the effect is quiet.
    pub fn set_temperature(&mut self, text: &str, doc: &mut GrokDoc) -> ModelEffect {
        let text = text.trim();
        if self.console {
            return self.shell_effect_checked(text, &self.max_tokens.clone());
        }
        let Ok(temp) = text.parse::<f64>() else {
            return ModelEffect {
                target: None,
                think: None,
                wires: Vec::new(),
                status: Some(SETTINGS_WORD),
            };
        };
        if !temp.is_finite() {
            return ModelEffect {
                target: None,
                think: None,
                wires: Vec::new(),
                status: Some(SETTINGS_WORD),
            };
        }
        self.temperature = number_text(temp);
        let mut entry = doc.entry(&self.cell_key).unwrap_or_else(|| GrokEntry::blank(""));
        entry.temperature = temp;
        doc.write(&self.cell_key, &entry, false);
        ModelEffect::quiet()
    }

    /// The `set_max_tokens` method stores a max token count.
    /// A console dialog checks the text through the shell body. A value that is not an integer of at least 1 returns the status word `settings`. A good value is written on the cell and the effect is quiet.
    pub fn set_max_tokens(&mut self, text: &str, doc: &mut GrokDoc) -> ModelEffect {
        let text = text.trim();
        if self.console {
            return self.shell_effect_checked(&self.temperature.clone(), text);
        }
        let Ok(max) = text.parse::<i32>() else {
            return ModelEffect {
                target: None,
                think: None,
                wires: Vec::new(),
                status: Some(SETTINGS_WORD),
            };
        };
        if max < 1 {
            return ModelEffect {
                target: None,
                think: None,
                wires: Vec::new(),
                status: Some(SETTINGS_WORD),
            };
        }
        self.max_tokens = max.to_string();
        let mut entry = doc.entry(&self.cell_key).unwrap_or_else(|| GrokEntry::blank(""));
        entry.max_completion_tokens = max;
        doc.write(&self.cell_key, &entry, false);
        ModelEffect::quiet()
    }

    /// The `ollama_pane_owns_cell` method reports that the Ollama pane may write the cell.
    /// The family must be Ollama or still unset. A missing family keeps the old write. The console path does not ask.
    fn ollama_pane_owns_cell(&self) -> bool {
        // @aa-sdd PK-1085 docs/specs/common/formal-plan.spec.md FINESSE:f-195
        matches!(self.family, None | Some(Family::Ollama))
    }

    /// The `type_ollama` method updates the Ollama label on each character.
    /// Show waits for focus loss. A cell dialog whose family is not Ollama returns a quiet effect. A console dialog stores the id and returns a quiet effect. A shut dialog does the same. An open cell dialog returns `ollama/` plus the id, or an empty target when the id is empty.
    pub fn type_ollama(&mut self, id: &str, think: &ThinkSnap) -> ModelEffect {
        if !self.console && !self.ollama_pane_owns_cell() {
            return ModelEffect::quiet();
        }
        let id = id.trim().to_string();
        self.ollama_id = id.clone();
        if self.console || !self.open {
            return ModelEffect::quiet();
        }
        let mut think = think.clone();
        think.ollama_id = id.clone();
        let target = if id.is_empty() {
            String::new()
        } else {
            format!("ollama/{id}")
        };
        ModelEffect {
            target: Some(target),
            think: Some(think),
            wires: Vec::new(),
            status: None,
        }
    }

    /// The `commit_ollama` method stores the Ollama id when focus leaves the field.
    /// A shut dialog returns a quiet effect. A cell dialog whose family is not Ollama returns a quiet effect. `ask_show` queues a show request.
    pub fn commit_ollama(&mut self, ask_show: bool, think: &ThinkSnap) -> ModelEffect {
        if !self.open {
            return ModelEffect::quiet();
        }
        if !self.console && !self.ollama_pane_owns_cell() {
            return ModelEffect::quiet();
        }
        let id = self.ollama_id.trim().to_string();
        self.ollama_id = id.clone();
        if self.console {
            let mut effect = self.shell_effect();
            self.queue_show(&mut effect, ask_show, &id);
            return effect;
        }
        let mut think = think.clone();
        think.ollama_id = id.clone();
        let target = if id.is_empty() {
            String::new()
        } else {
            format!("ollama/{id}")
        };
        let mut effect = ModelEffect {
            target: Some(target),
            think: Some(think),
            wires: Vec::new(),
            status: None,
        };
        self.queue_show(&mut effect, ask_show, &id);
        effect
    }

    /// The `set_ollama_think` method stores the Ollama think checkbox.
    /// Turning it off clears the level list. A console dialog may queue a show when the box is on and the id is not empty. A shut cell dialog returns a quiet effect.
    pub fn set_ollama_think(&mut self, on: bool, think: &ThinkSnap) -> ModelEffect {
        if !self.console && !self.ollama_pane_owns_cell() {
            return ModelEffect::quiet();
        }
        self.ollama_think = on;
        if !on {
            self.ollama_levels.clear();
            self.ollama_level = None;
        }
        if self.console {
            let mut effect = self.shell_effect();
            if on && !self.ollama_id.is_empty() {
                effect.wires.push(ModelWire::Show {
                    cell: [0u8; 16],
                    id: self.ollama_id.clone(),
                });
            }
            return effect;
        }
        if !self.open {
            return ModelEffect::quiet();
        }
        let mut think = think.clone();
        think.ollama_on = on;
        let mut effect = ModelEffect {
            target: None,
            think: Some(think),
            wires: Vec::new(),
            status: None,
        };
        if on && !self.ollama_id.is_empty() {
            if self.ollama_id != self.show_id {
                self.ollama_levels.clear();
                self.ollama_level = None;
                self.show_id = self.ollama_id.clone();
            }
            effect.wires.push(ModelWire::Show {
                cell: self.cell,
                id: self.ollama_id.clone(),
            });
        }
        effect
    }

    /// The `set_ollama_level` method stores one Ollama effort word.
    /// An empty word returns a quiet effect. The words `true` and `false` return a quiet effect. A console dialog returns a shell set.
    pub fn set_ollama_level(&mut self, level: &str, think: &ThinkSnap) -> ModelEffect {
        if level.is_empty() || level == "true" || level == "false" {
            return ModelEffect::quiet();
        }
        self.ollama_level = Some(level.to_string());
        if self.console {
            self.console_think = level.to_string();
            self.console_level = level.to_string();
            return self.shell_effect();
        }
        let mut think = think.clone();
        think.ollama_level = level.to_string();
        ModelEffect {
            target: None,
            think: Some(think),
            wires: Vec::new(),
            status: None,
        }
    }

    /// The `apply_grok_models` method stores Grok model ids from a catalog reply.
    /// A shut dialog returns. A key other than the open cell returns. A reply with no model lines clears the combo and stores the red miss. The stored id stays. A selected id that is not in the reply is placed first.
    /// // @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230
    pub fn apply_grok_models(&mut self, cell_key: &str, text: &str) {
        if !self.open || cell_key != self.cell_key {
            return;
        }
        let names = tags_models(text);
        if names.is_empty() {
            self.show_list_miss(GROK_LIST_MISS);
            return;
        }
        let current = self.grok_model.clone().unwrap_or_default();
        let chosen = current.trim().to_string();
        let mut rows = Vec::new();
        if !chosen.is_empty() && !names.iter().any(|row| row == &chosen) {
            rows.push(chosen);
        }
        rows.extend(names);
        self.grok_choices = rows;
        self.clear_list_miss();
    }

    /// The `apply_tags` method stores the Ollama model names from a tags reply.
    /// A shut dialog returns. A key other than the open cell returns. The current Ollama id is kept. A reply with no model lines clears the rows and stores the red miss. The typed id is not added as a row.
    /// // @aa-sdd PK-994 docs/specs/common/stages.spec.md FINESSE:f-230
    pub fn apply_tags(&mut self, cell_key: &str, text: &str) {
        if !self.open || cell_key != self.cell_key {
            return;
        }
        let current = self.ollama_id.clone();
        let names = tags_models(text);
        self.ollama_id = current;
        if names.is_empty() {
            self.show_list_miss(OLLAMA_LIST_MISS);
            return;
        }
        self.ollama_tags = names;
        self.clear_list_miss();
    }

    /// The `apply_show` method applies a show reply.
    /// A show reply fills an empty field and does not by itself store a target. The method returns false. A shut dialog returns false. A key other than the open cell returns false. A reply for a different id returns false. Levels `true` and `false` are not kept.
    pub fn apply_show(&mut self, cell_key: &str, text: &str, think: &ThinkSnap) -> bool {
        if !self.open || cell_key != self.cell_key {
            return false;
        }
        let id = show_model(text);
        let mut current = self.ollama_id.clone();
        let mut filled = false;
        if current.is_empty() && !id.is_empty() {
            self.ollama_id = id.clone();
            self.show_id = id.clone();
            current = id.clone();
            filled = true;
        } else if id != current {
            return false;
        }
        let _ = current;
        self.ollama_levels = show_values(text);
        let level = if self.console {
            let seed = self.console_level.clone();
            if !seed.is_empty() && seed != "true" && seed != "false" {
                Some(seed)
            } else {
                None
            }
        } else if think.ollama_level.is_empty() {
            None
        } else {
            Some(think.ollama_level.clone())
        };
        self.ollama_level = level.filter(|level| self.ollama_levels.iter().any(|row| row == level));
        let _ = filled;
        false
    }

    /// The `effort_off` method turns send-effort off for one cell and writes the document.
    /// An empty key returns false. A missing cell returns false. An open dialog for that cell clears its send flag.
    pub fn effort_off(&mut self, cell_key: &str, doc: &mut GrokDoc) -> bool {
        if cell_key.is_empty() {
            return false;
        }
        let Some(mut entry) = doc.entry(cell_key) else {
            return false;
        };
        entry.send_reasoning_effort = false;
        doc.write(cell_key, &entry, false);
        if self.open && cell_key == self.cell_key {
            self.send_effort = false;
            self.effort_enabled = false;
        }
        true
    }

    /// The `effort_visible` method reports that the Ollama effort list should show.
    /// The think box must be on. The level list must not be empty.
    pub fn effort_visible(&self) -> bool {
        self.ollama_think && !self.ollama_levels.is_empty()
    }

    /// The `queue_show` method appends a show request when `ask_show` is true.
    /// An empty id clears the level list. A changed id clears the level list before the request.
    fn queue_show(&mut self, effect: &mut ModelEffect, ask_show: bool, id: &str) {
        if !ask_show {
            return;
        }
        if id.is_empty() {
            self.ollama_levels.clear();
            self.ollama_level = None;
            self.show_id.clear();
            return;
        }
        if id != self.show_id {
            self.ollama_levels.clear();
            self.ollama_level = None;
            self.show_id = id.to_string();
        }
        effect.wires.push(ModelWire::Show {
            cell: self.cell,
            id: id.to_string(),
        });
    }

    /// The `touch_grok` method saves the Grok controls.
    /// A console dialog returns a shell set. A shut dialog returns a quiet effect. An open cell dialog writes the document and returns a quiet effect.
    fn touch_grok(&mut self, doc: &mut GrokDoc) -> ModelEffect {
        if self.console {
            return self.shell_effect();
        }
        if !self.open {
            return ModelEffect::quiet();
        }
        self.write_grok(doc);
        ModelEffect::quiet()
    }

    /// The `write_grok` method writes the dialog's Grok controls onto the cell.
    /// An empty cell key returns. A console dialog returns. The standing row is not forced.
    fn write_grok(&mut self, doc: &mut GrokDoc) {
        if self.cell_key.is_empty() || self.console {
            return;
        }
        let mut entry = doc.entry(&self.cell_key).unwrap_or_else(|| GrokEntry::blank(""));
        if let Some(model) = &self.grok_model {
            entry.model = model.clone();
        }
        entry.reasoning_effort = normalize_effort(&entry.model, &self.grok_effort);
        self.grok_effort = entry.reasoning_effort.clone();
        entry.send_reasoning_effort = self.send_effort;
        entry.stream = self.stream;
        entry.burner_cloud = self.burner_cloud;
        doc.write(&self.cell_key, &entry, false);
    }

    /// The `apply_grok_family` method stores a Grok target for the open cell.
    /// An existing row keeps its model unless the field already names one. A new row copies the nearest earlier Grok cell or the standing row. A typed id is kept. A copied think flag comes from `grok_flags` for that source cell. A row with no model returns `grok/`.
    fn apply_grok_family(
        &mut self,
        stored: &str,
        doc: &mut GrokDoc,
        earlier: &[String],
        index: usize,
        think: &mut ThinkSnap,
        grok_flags: &[(&str, bool)],
    ) -> String {
        let typed = if provider_id(stored) == "grok" {
            model_after_slash(stored)
        } else {
            String::new()
        };
        if let Some(mut existing) = doc.entry(&self.cell_key) {
            let mut model = existing.model.clone();
            if !typed.is_empty() {
                model = typed.clone();
                existing.model = typed.clone();
                existing.reasoning_effort = normalize_effort(&typed, &existing.reasoning_effort);
                doc.write(&self.cell_key, &existing, false);
            }
            return if model.is_empty() {
                "grok/".to_string()
            } else {
                format!("grok/{model}")
            };
        }
        let nearest = nearest_grok(earlier, index, doc);
        let standing = doc.standing();
        let (source, source_key) = if let Some((id, entry)) = nearest {
            (Some(entry), Some(id.to_string()))
        } else {
            (standing, None)
        };
        if !typed.is_empty() {
            let mut entry = source.clone().unwrap_or_else(|| GrokEntry::blank(&typed));
            entry.model = typed.clone();
            entry.reasoning_effort = normalize_effort(&typed, &entry.reasoning_effort);
            doc.write(&self.cell_key, &entry, false);
            if let Some(source_key) = source_key {
                if let Some(prior) = grok_flag(grok_flags, &source_key) {
                    think.grok_on = prior;
                }
            }
            return format!("grok/{typed}");
        }
        if let Some(entry) = source.as_ref() {
            if !entry.model.is_empty() {
                let model = entry.model.clone();
                let mut entry = entry.copy();
                entry.reasoning_effort = normalize_effort(&entry.model, &entry.reasoning_effort);
                doc.write(&self.cell_key, &entry, false);
                if let Some(source_key) = source_key {
                    if let Some(prior) = grok_flag(grok_flags, &source_key) {
                        think.grok_on = prior;
                    }
                }
                return format!("grok/{model}");
            }
        }
        doc.write(&self.cell_key, &GrokEntry::blank(""), false);
        "grok/".to_string()
    }

    /// The `ensure_console_grok` method fills an empty console Grok entry.
    /// A stored entry with a model is kept. Otherwise standing is copied. A missing standing row becomes a blank entry.
    fn ensure_console_grok(&mut self, doc: &GrokDoc) {
        let model = self
            .console_grok
            .as_ref()
            .map(|entry| entry.model.trim().to_string())
            .unwrap_or_default();
        if self.console_grok.is_some() && !model.is_empty() {
            return;
        }
        if let Some(standing) = doc.standing() {
            self.console_grok = Some(standing);
            return;
        }
        if self.console_grok.is_none() {
            self.console_grok = Some(GrokEntry::blank(""));
        }
    }

    /// The `load_grok` method copies one Grok entry onto the dialog controls.
    /// A missing entry uses the factory effort, temperature 1, and max tokens 128000. `grok_on` becomes the think checkbox.
    fn load_grok(&mut self, entry: Option<&GrokEntry>, grok_on: bool) {
        let model = entry.map(|entry| entry.model.clone()).unwrap_or_default();
        self.grok_choices.clear();
        self.clear_list_miss();
        self.grok_model = if model.is_empty() { None } else { Some(model.clone()) };
        let effort_model = self.grok_model.clone().unwrap_or(model);
        let effort = entry
            .map(|entry| entry.reasoning_effort.clone())
            .unwrap_or_else(|| "high".to_string());
        self.fill_grok_effort(&effort_model, &effort);
        self.send_effort = entry
            .map(|entry| entry.send_reasoning_effort)
            .unwrap_or_else(|| sends_effort(&effort_model));
        self.effort_enabled = self.send_effort;
        self.stream = entry.is_some_and(|entry| entry.stream);
        self.burner_cloud = entry.is_some_and(|entry| entry.burner_cloud);
        self.temperature = number_text(entry.map(|entry| entry.temperature).unwrap_or(1.0));
        self.max_tokens = entry
            .map(|entry| entry.max_completion_tokens)
            .unwrap_or(128000)
            .to_string();
        self.grok_think = grok_on;
    }

    /// The `fill_grok_effort` method loads the effort rows for `model`.
    /// `keep` stays when it is one of those rows. Otherwise the factory level is used.
    fn fill_grok_effort(&mut self, model: &str, keep: &str) {
        self.grok_efforts = effort_rows(model).iter().map(|row| (*row).to_string()).collect();
        self.grok_effort = if self.grok_efforts.iter().any(|row| row == keep) {
            keep.to_string()
        } else {
            effort_start(model).to_string()
        };
    }

    /// The `seat_family` method stores the family parsed from `provider`.
    /// An unknown word clears the family.
    fn seat_family(&mut self, provider: &str) {
        self.family = Family::parse(provider);
    }

    /// The `shell_effect` method builds a console shell-set wire.
    /// A bad temperature or a bad max token count returns the status word `settings`.
    fn shell_effect(&mut self) -> ModelEffect {
        match shell_set_body(self) {
            Ok(body) => ModelEffect::wires(vec![ModelWire::ShellSet(body)]),
            Err(word) => ModelEffect {
                target: None,
                think: None,
                wires: Vec::new(),
                status: Some(word),
            },
        }
    }

    /// The `shell_effect_checked` method stores `temp` and `max` and then builds the shell set.
    /// The check happens inside `shell_set_body`.
    fn shell_effect_checked(&mut self, temp: &str, max: &str) -> ModelEffect {
        self.temperature = temp.to_string();
        self.max_tokens = max.to_string();
        self.shell_effect()
    }
}

/// The `grok_flag` function returns the think flag stored for `source`.
/// A missing key returns `None`.
fn grok_flag(flags: &[(&str, bool)], source: &str) -> Option<bool> {
    flags.iter().find(|(key, _)| *key == source).map(|(_, on)| *on)
}

/// The `shell_set_body` function builds the `shell_model_set` body.
/// A missing family returns an empty body. A temperature that is not a finite float returns `settings`. A max token count below 1 returns `settings`. An empty temperature line is left off the body. An empty max token line is left off the body. The body starts with `shell_model_set:`.
fn shell_set_body(session: &mut ModelSession) -> Result<String, &'static str> {
    let Some(family) = session.family else {
        return Ok(String::new());
    };
    let temp_text = session.temperature.trim().to_string();
    let mut temp_present = false;
    let mut temp = 0.0;
    if !temp_text.is_empty() {
        temp = temp_text.parse::<f64>().map_err(|_| SETTINGS_WORD)?;
        if !temp.is_finite() {
            return Err(SETTINGS_WORD);
        }
        temp_present = true;
    }
    let max_text = session.max_tokens.trim().to_string();
    let mut max_present = false;
    let mut max = 0;
    if !max_text.is_empty() {
        max = max_text.parse::<i32>().map_err(|_| SETTINGS_WORD)?;
        if max < 1 {
            return Err(SETTINGS_WORD);
        }
        max_present = true;
    }
    let ollama_id = session.ollama_id.trim().to_string();
    let think = console_think_value(session);
    session.console_think = think.clone();
    if !think.is_empty() && think != "true" && think != "false" {
        session.console_level = think.clone();
    }
    let grok_model = session.grok_model.clone().unwrap_or_default();
    let effort = normalize_effort(&grok_model, &session.grok_effort);
    session.grok_effort = effort.clone();
    let grok_think = if session.grok_think { "true" } else { "" };
    let pool_think = if session.pool_think { "true" } else { "" };
    if session.console_grok.is_none() {
        session.console_grok = Some(GrokEntry::blank(&grok_model));
    }
    if let Some(entry) = session.console_grok.as_mut() {
        entry.model = grok_model.clone();
        entry.reasoning_effort = effort.clone();
        entry.send_reasoning_effort = session.send_effort;
        entry.stream = session.stream;
        entry.burner_cloud = session.burner_cloud;
        if temp_present {
            entry.temperature = temp;
        }
        if max_present {
            entry.max_completion_tokens = max;
        }
    }
    session.console_grok_think = grok_think.to_string();
    session.console_pool_on = session.pool_think;
    session.console_provider = family.word().to_string();
    session.console_ollama = ollama_id.clone();
    let mut body = String::from("shell_model_set:\n");
    push_shell(&mut body, "provider", family.word());
    push_shell(&mut body, "model", &ollama_id);
    push_shell(&mut body, "ollama_model", &ollama_id);
    push_shell(&mut body, "think", &think);
    push_shell(&mut body, "grok_model", &grok_model);
    push_shell(&mut body, "grok_think", grok_think);
    push_shell(&mut body, "effort", &effort);
    push_shell(&mut body, "send_effort", if session.send_effort { "true" } else { "false" });
    push_shell(&mut body, "stream", if session.stream { "true" } else { "false" });
    if temp_present {
        push_shell(&mut body, "temperature", &crate::amwp::wire_double(temp));
    }
    if max_present {
        push_shell(&mut body, "max_tokens", &max.to_string());
    }
    push_shell(&mut body, "burner", if session.burner_cloud { "true" } else { "false" });
    push_shell(&mut body, "pool_think", pool_think);
    Ok(body)
}

/// The `console_think_value` function returns the Ollama think word for a shell set.
/// A box that is off returns an empty string. A stored level is returned when it is not empty. Otherwise the word is `true`.
fn console_think_value(session: &ModelSession) -> String {
    if !session.ollama_think {
        return String::new();
    }
    if let Some(level) = &session.ollama_level {
        let level = level.trim();
        if !level.is_empty() {
            return level.to_string();
        }
    }
    "true".to_string()
}

/// The `push_shell` function appends one `key: value` line.
/// Carriage returns and newlines inside the value are removed.
fn push_shell(body: &mut String, key: &str, value: &str) {
    body.push_str(key);
    body.push_str(": ");
    body.push_str(&clean_wire(value));
    body.push('\n');
}

/// The `clean_wire` function removes carriage returns and newlines.
/// Every other character is kept.
fn clean_wire(raw: &str) -> String {
    raw.chars().filter(|c| *c != '\r' && *c != '\n').collect()
}

/// The `shell_line` function returns the text after the first line that starts with `key`.
/// A carriage return at the end of a line is ignored. A missing key returns an empty string.
pub fn shell_line(text: &str, key: &str) -> String {
    for raw in text.split('\n') {
        let line = raw.strip_suffix('\r').unwrap_or(raw);
        if let Some(rest) = line.strip_prefix(key) {
            return rest.trim().to_string();
        }
    }
    String::new()
}

/// The `tags_models` function collects `model:` lines.
/// Empty names are skipped. A repeated name is skipped.
pub fn tags_models(text: &str) -> Vec<String> {
    let mut names = Vec::new();
    for raw in text.split('\n') {
        let line = raw.strip_suffix('\r').unwrap_or(raw);
        let Some(rest) = line.strip_prefix("model:") else {
            continue;
        };
        let name = rest.trim();
        if !name.is_empty() && !names.iter().any(|have| have == name) {
            names.push(name.to_string());
        }
    }
    names
}

/// The `show_model` function returns the model on the first line.
/// The line must start with `show_result:`. Any other first line returns an empty string.
pub fn show_model(text: &str) -> String {
    let first = text.split('\n').next().unwrap_or("");
    let first = first.strip_suffix('\r').unwrap_or(first);
    first
        .strip_prefix("show_result:")
        .map(|rest| rest.trim().to_string())
        .unwrap_or_default()
}

/// The `show_values` function collects `value:` lines.
/// Empty text is skipped. The words `true` and `false` are skipped.
pub fn show_values(text: &str) -> Vec<String> {
    let mut values = Vec::new();
    for raw in text.split('\n') {
        let line = raw.strip_suffix('\r').unwrap_or(raw);
        let Some(rest) = line.strip_prefix("value:") else {
            continue;
        };
        let level = rest.trim();
        if !level.is_empty() && level != "true" && level != "false" {
            values.push(level.to_string());
        }
    }
    values
}

/// The `console_result_open` function reads whether one console result opens the thought strip.
/// `Some` is returned when `text` starts with `shell_model_result:`. Pool reads `pool_think`. Ollama reads `think`. Grok reads `grok_think`. An empty provider reads as Ollama. Any other provider is `Some(false)`. Text that is not a result returns `None`. Java calls the matching gate `consoleThoughtOpen`.
pub fn console_result_open(text: &str) -> Option<bool> {
    if !text.starts_with("shell_model_result:") {
        return None;
    }
    let mut provider = shell_line(text, "provider:");
    if provider.is_empty() {
        provider = "ollama".to_string();
    }
    if provider == "pool" {
        return Some(shell_line(text, "pool_think:") == "true");
    }
    if provider == "grok" {
        return Some(shell_line(text, "grok_think:") == "true");
    }
    if provider != "ollama" {
        return Some(false);
    }
    let level = shell_line(text, "think:");
    let level = level.trim();
    Some(!level.is_empty() && level != "false")
}

/// The `model_note` function reports a model reply the dialog keeps.
/// The prefixes are `effort_off:`, `show_result:`, `tags_result:`, `grok_models_result:`, and `shell_model_result:`.
pub fn model_note(body: &[u8]) -> bool {
    let text = String::from_utf8_lossy(body);
    text.starts_with("effort_off:")
        || text.starts_with("show_result:")
        || text.starts_with("tags_result:")
        || text.starts_with("grok_models_result:")
        || text.starts_with("shell_model_result:")
}

/// The `grok_models_body` function returns the Grok catalog request.
/// The text is `grok_models:` and a newline.
pub fn grok_models_body() -> &'static str {
    "grok_models:\n"
}

/// The `tags_body` function returns the tags request.
/// The text is `tags:` and a newline.
pub fn tags_body() -> &'static str {
    "tags:\n"
}

/// The `show_body` function returns a show request for `id`.
/// The text is `show: `, the id, and a newline.
pub fn show_body(id: &str) -> String {
    format!("show: {id}\n")
}

/// The `shell_model_body` function returns the shell-model request.
/// The text is `shell_model:` and a newline.
pub fn shell_model_body() -> &'static str {
    "shell_model:\n"
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The [`OscHold`] struct removes the private select-model sequence from Scope T bytes.
/// `tail` holds a prefix that was cut off at the end of the last chunk.
pub struct OscHold {
    tail: Vec<u8>,
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The [`OscOpen`] struct is one select-model sequence peeled from the stream.
/// `model` is the text before the semicolon. `think` is the text after it.
#[derive(Clone, Debug, PartialEq, Eq)]
pub struct OscOpen {
    pub model: String,
    pub think: String,
}

impl OscHold {
    /// The `new` method returns an empty hold.
    /// No prefix is pending.
    pub fn new() -> Self {
        Self { tail: Vec::new() }
    }

    /// The `peel` method returns paint bytes and the select-model sequences inside `chunk`.
    /// The sequence starts with ESC `]777;select-model;` and ends at BEL. A cut-off prefix is held. A cut-off body longer than [`OSC_HOLD_CAP`] is painted. A body with no semicolon stores the whole body as the model and an empty think string.
    pub fn peel(&mut self, chunk: &[u8]) -> (Vec<u8>, Vec<OscOpen>) {
        let mut data = std::mem::take(&mut self.tail);
        data.extend_from_slice(chunk);
        let mut paint = Vec::new();
        let mut opens = Vec::new();
        let mut i = 0;
        while i < data.len() {
            let Some(rel) = data[i..].iter().position(|byte| *byte == 0x1B) else {
                paint.extend_from_slice(&data[i..]);
                break;
            };
            let esc = i + rel;
            if esc > i {
                paint.extend_from_slice(&data[i..esc]);
            }
            let matched = prefix_len(&data, esc, OSC_OPEN);
            if matched < OSC_OPEN.len() {
                if matched > 0 && esc + matched == data.len() {
                    self.tail = data[esc..].to_vec();
                    return (paint, opens);
                }
                paint.push(data[esc]);
                i = esc + 1;
                continue;
            }
            let body_at = esc + OSC_OPEN.len();
            let Some(rel_bel) = data[body_at..].iter().position(|byte| *byte == 0x07) else {
                let pending = data.len() - esc;
                if pending > OSC_HOLD_CAP {
                    paint.extend_from_slice(&data[esc..]);
                } else {
                    self.tail = data[esc..].to_vec();
                }
                return (paint, opens);
            };
            let bel = body_at + rel_bel;
            let body = String::from_utf8_lossy(&data[body_at..bel]);
            let (model, think) = match body.split_once(';') {
                Some((model, think)) => (model.to_string(), think.to_string()),
                None => (body.into_owned(), String::new()),
            };
            opens.push(OscOpen { model, think });
            i = bel + 1;
        }
        (paint, opens)
    }
}

impl Default for OscHold {
    /// The `default` method returns the same hold as `new`.
    /// No prefix is pending.
    fn default() -> Self {
        Self::new()
    }
}

/// The `prefix_len` function counts how many prefix bytes match at `from`.
/// The count stops at the first mismatch or at the end of `data`.
fn prefix_len(data: &[u8], from: usize, prefix: &[u8]) -> usize {
    let mut n = 0;
    while n < prefix.len() && from + n < data.len() && data[from + n] == prefix[n] {
        n += 1;
    }
    n
}

#[cfg(test)]
/// These functions check the model table and the settings document.
mod tests {
    use super::*;

    /// The `the_catalog_matches_the_java_table` test checks the effort rows and the name helpers.
    /// Three ids refuse send-effort. An empty stored target goes out as `ollama`. `grok/` is a missing model.
    /// The SSH strip word is `ollama`, `ollama/` plus an id, `grok`, `grok/` plus an id, or `pool`.
    #[test]
    fn the_catalog_matches_the_java_table() {
        assert_eq!(effort_rows("grok-4.7"), FOUR);
        assert_eq!(effort_rows("grok-4.6"), FOUR);
        assert_eq!(effort_rows("grok-4.20-multi-agent-0309"), FOUR);
        assert_eq!(effort_rows("grok-4.5"), THREE);
        assert_eq!(effort_rows("grok-4.3"), ONE);
        assert_eq!(effort_rows(""), ONE);
        assert_eq!(effort_start("grok-4.5"), "high");
        assert!(!sends_effort("grok-4.20-0309-reasoning"));
        assert!(!sends_effort("grok-4.20-0309-non-reasoning"));
        assert!(!sends_effort("grok-build-0.1"));
        assert!(sends_effort("grok-4.7"));
        assert!(sends_effort("grok-4.5"));
        assert!(sends_effort("grok-4.3"));
        assert_eq!(normalize_effort("grok-4.5", "xhigh"), "high");
        assert_eq!(normalize_effort("grok-4.7", "xhigh"), "xhigh");
        assert_eq!(normalize_effort("grok-4.7", ""), "high");
        assert_eq!(short_model_name(""), "ollama");
        assert_eq!(short_model_name("ollama/qwen"), "qwen");
        assert_eq!(short_model_name("grok/"), "grok");
        assert_eq!(short_model_name("grok/grok-4.3"), "grok-4.3");
        assert_eq!(short_model_name("pool"), "pool");
        assert_eq!(short_model_name("other/x"), "");
        assert!(no_model("grok/"));
        assert!(no_model("grok"));
        assert!(!no_model("grok/grok-4.3"));
        assert!(!no_model(""));
        assert_eq!(outbound_target("  "), "ollama");
        assert_eq!(outbound_target(" grok/m "), "grok/m");
        assert_eq!(engine_from_console("ollama", "deepseek-r1:7b", ""), "ollama/deepseek-r1:7b");
        assert_eq!(engine_from_console("ollama", "", "grok-4.3"), "ollama");
        assert_eq!(engine_from_console("", "", ""), "ollama");
        assert_eq!(engine_from_console("", "ollama/deepseek-r1:7b", ""), "ollama/deepseek-r1:7b");
        assert_eq!(engine_from_console("grok", "deepseek-r1:7b", "grok-4.3"), "grok/grok-4.3");
        assert_eq!(engine_from_console("grok", "deepseek-r1:7b", "  "), "grok");
        assert_eq!(engine_from_console("pool", "deepseek-r1:7b", "grok-4.3"), "pool");
        let grok = "shell_model_result:\nprovider: grok\nollama_model: qwen\ngrok_model: grok-4.3\n";
        assert_eq!(engine_from_shell_result(grok).as_deref(), Some("grok/grok-4.3"));
        let ollama = "shell_model_result:\nprovider: ollama\nmodel: deepseek-r1:7b\n";
        assert_eq!(engine_from_shell_result(ollama).as_deref(), Some("ollama/deepseek-r1:7b"));
        assert_eq!(
            engine_from_shell_result("shell_model_result:\nprovider: pool\n").as_deref(),
            Some("pool")
        );
        assert!(engine_from_shell_result("vault: disk  engine: grok/m").is_none());
    }

    /// The `the_document_round_trips_and_a_foreign_version_stays` test writes a document and reloads it.
    /// An empty model does not replace standing. A file whose version is 2 stays blocked and is not overwritten. `forget_all` drops the cell and keeps standing.
    #[test]
    fn the_document_round_trips_and_a_foreign_version_stays() {
        let dir = std::env::temp_dir().join(format!("aetheris-grok-test-{}", std::process::id()));
        let _ = std::fs::create_dir_all(&dir);
        let path = dir.join("aetheris-grok.yaml");
        let _ = std::fs::remove_file(&path);
        let mut doc = GrokDoc::load(path.clone());
        assert!(doc.standing().is_none());
        let mut entry = GrokEntry::blank("grok-4.7");
        entry.reasoning_effort = "xhigh".to_string();
        entry.send_reasoning_effort = false;
        entry.stream = true;
        entry.burner_cloud = true;
        entry.temperature = 0.5;
        entry.max_completion_tokens = 32;
        doc.write("cell-a", &entry, false);
        assert_eq!(doc.standing().expect("first model").model, "grok-4.7");
        doc.write("cell-b", &GrokEntry::blank(""), false);
        assert_eq!(doc.standing().expect("standing stays").model, "grok-4.7");
        let loaded = GrokDoc::load(path.clone());
        let again = loaded.entry("cell-a").expect("cell");
        assert_eq!(again.model, "grok-4.7");
        assert_eq!(again.reasoning_effort, "xhigh");
        assert!(!again.send_reasoning_effort);
        assert!(again.stream);
        assert!(again.burner_cloud);
        assert_eq!(again.temperature, 0.5);
        assert_eq!(again.max_completion_tokens, 32);
        assert_eq!(again.api, "chat");
        assert!(loaded.entry("cell-b").expect("empty").model.is_empty());
        let seed = loaded.console_seed();
        assert_eq!(seed[0], "grok-4.7");
        assert_eq!(seed[2], "false");
        let blocked = dir.join("blocked.yaml");
        std::fs::write(&blocked, "version: 2\ncells: {}\n").expect("write");
        let mut foreign = GrokDoc::load(blocked.clone());
        assert!(foreign.blocked());
        foreign.write("cell-a", &GrokEntry::factory(), true);
        let kept = std::fs::read_to_string(&blocked).expect("read");
        assert!(kept.contains("version: 2"));
        assert!(!kept.contains("grok-4.3"));
        foreign.forget_all(&["cell-a".to_string()]);
        doc.forget_all(&["cell-a".to_string()]);
        let after = GrokDoc::load(path);
        assert!(after.entry("cell-a").is_none());
        assert_eq!(after.standing().expect("standing").model, "grok-4.7");
        let _ = std::fs::remove_dir_all(&dir);
    }

    /// The `a_missing_send_flag_uses_the_catalog` test reads a missing send flag.
    /// `grok-build-0.1` is false. The text version `1` and the word `yes` are accepted.
    #[test]
    fn a_missing_send_flag_uses_the_catalog() {
        let text = "version: 1\ncells:\n  one:\n    api: chat\n    model: grok-build-0.1\n    reasoning_effort: high\n";
        match parse_doc(text) {
            Parse::Doc { cells, .. } => {
                assert!(!cells[0].1.send_reasoning_effort);
            }
            Parse::Blocked => panic!("version 1"),
        }
        let stored = "version: \"1\"\ndefault:\n  model: grok-4.3\n  send_reasoning_effort: \"yes\"\n";
        match parse_doc(stored) {
            Parse::Doc { standing, .. } => {
                assert!(standing.expect("standing").send_reasoning_effort);
            }
            Parse::Blocked => panic!("string version"),
        }
    }

    /// The `the_canvas_frame_carries_the_cell_and_the_mood_frame_leaves_think_off` test checks canvas and mood tunes.
    /// An empty stored word is `ollama`. An Ollama level is copied. A Grok entry copies effort, stream, burner, temperature, and max tokens. Mood leaves think empty and leaves stream off. A pool mood target is the word `pool`.
    #[test]
    fn the_canvas_frame_carries_the_cell_and_the_mood_frame_leaves_think_off() {
        let mut think = ThinkSnap::off();
        let plain = canvas_tune("", false, &think, None, "");
        assert_eq!(plain.target, "ollama");
        assert!(plain.think.is_empty());
        assert!(plain.temperature.is_none());
        think.ollama_on = true;
        think.ollama_level = "high".to_string();
        let asked = canvas_tune("ollama/qwen", false, &think, None, "boot");
        assert_eq!(asked.target, "ollama/qwen");
        assert_eq!(asked.think, "high");
        assert!(asked.pool_boot.is_empty());
        think.ollama_on = true;
        think.ollama_level.clear();
        let bare = canvas_tune("ollama/qwen", true, &think, None, "");
        assert_eq!(bare.think, "true");
        assert!(bare.ephemeral);
        let mut entry = GrokEntry::blank("grok-4.7");
        entry.reasoning_effort = "low".to_string();
        entry.send_reasoning_effort = false;
        entry.stream = true;
        entry.burner_cloud = true;
        entry.temperature = 0.2;
        entry.max_completion_tokens = 16;
        let grok = canvas_tune("grok/grok-4.7", false, &ThinkSnap::off(), Some(&entry), "");
        assert_eq!(grok.target, "grok/grok-4.7");
        assert_eq!(grok.reasoning_effort, "low");
        assert!(!grok.send_reasoning_effort);
        assert!(grok.think_stream);
        assert!(grok.burner_cloud);
        assert_eq!(grok.temperature, Some(0.2));
        assert_eq!(grok.max_completion_tokens, Some(16));
        assert!(grok.think.is_empty());
        think.pool_on = true;
        let pool = canvas_tune("pool", false, &think, None, "boot-1");
        assert_eq!(pool.target, "pool");
        assert_eq!(pool.think, "true");
        assert_eq!(pool.pool_boot, "boot-1");
        assert!(pool.reasoning_effort.is_empty());
        let mood = mood_tune("grok/", false, Some(&entry), "");
        assert_eq!(mood.target, "grok/grok-4.7");
        assert!(!mood.think_stream);
        assert!(mood.think.is_empty());
        assert!(mood.burner_cloud);
        let mood_pool = mood_tune("pool/heavy", true, None, "boot-1");
        assert_eq!(mood_pool.target, "pool");
        assert!(mood_pool.think.is_empty());
        assert_eq!(mood_pool.pool_boot, "boot-1");
        assert!(grok_burner_allows("grok/m", true));
        assert!(!grok_burner_allows("grok/m", false));
        assert!(!grok_burner_allows("pool", true));
    }

    /// The `the_group_field_normalizes_and_the_first_grok_adopts` test checks the group field.
    /// Leaving Ollama remembers `keep`. Bare `ollama/` clears the stored text. `Pool/heavy` stores `pool`. The first Grok cell adopts `grok-4.3`. A later edit syncs `grok-4.7`.
    #[test]
    fn the_group_field_normalizes_and_the_first_grok_adopts() {
        let mut think = ThinkSnap::off();
        think.ollama_id = "keep".to_string();
        let next = target_keystroke("ollama/keep", "grok/", &mut think);
        assert_eq!(next, "grok/");
        assert_eq!(think.ollama_id, "keep");
        let stored = target_focus_loss("  ollama/ ", &mut think);
        assert!(stored.is_empty());
        let pooled = target_focus_loss("Pool/heavy", &mut think);
        assert_eq!(pooled, "pool");
        let mut doc = GrokDoc::memory();
        doc.write("old", &GrokEntry::factory(), false);
        let keys = vec!["old".to_string(), "new".to_string()];
        let adopted = adopt_grok(&mut doc, "new", "grok/", &keys, 1);
        assert_eq!(adopted, "grok/grok-4.3");
        assert_eq!(doc.entry("new").expect("copied").model, "grok-4.3");
        let typed = target_focus_loss("grok/grok-4.7", &mut think);
        sync_grok_model(&mut doc, "new", &typed);
        assert_eq!(doc.entry("new").expect("synced").model, "grok-4.7");
    }

    /// The `tags_and_show_keep_the_textual_levels` test parses tags, show, and the request bodies.
    /// Duplicate model names are dropped. `true` and `false` are not effort levels.
    #[test]
    fn tags_and_show_keep_the_textual_levels() {
        let names = tags_models("tags_result:\nmodel: a\nmodel: a\nmodel:\nmodel: b\n");
        assert_eq!(names, vec!["a".to_string(), "b".to_string()]);
        assert_eq!(show_model("show_result: qwen\nvalue: low\n"), "qwen");
        let levels = show_values("show_result: qwen\nvalue: true\nvalue: low\nvalue: false\nvalue: high\n");
        assert_eq!(levels, vec!["low".to_string(), "high".to_string()]);
        assert!(model_note(b"tags_result:\n"));
        assert!(model_note(b"effort_off:\n"));
        assert!(!model_note(b"cell_id: x\n"));
        assert_eq!(tags_body(), "tags:\n");
        assert_eq!(grok_models_body(), "grok_models:\n");
        assert!(model_note(b"grok_models_result:\n"));
        assert_eq!(show_body("qwen"), "show: qwen\n");
        assert_eq!(shell_model_body(), "shell_model:\n");
    }

    /// The `the_select_model_sequence_leaves_the_grid` test peels a select-model sequence.
    /// `hi` and `ok` stay for paint. The model is `qwen` and the think word is `high`. A cut sequence is finished by the next chunk. A body longer than the hold cap is painted.
    #[test]
    fn the_select_model_sequence_leaves_the_grid() {
        let mut hold = OscHold::new();
        let (paint, opens) = hold.peel(b"hi\x1b]777;select-model;qwen;high\x07ok");
        assert_eq!(paint, b"hiok");
        assert_eq!(opens.len(), 1);
        assert_eq!(opens[0].model, "qwen");
        assert_eq!(opens[0].think, "high");
        let (left, none) = hold.peel(b"ab\x1b]777;select-mo");
        assert_eq!(left, b"ab");
        assert!(none.is_empty());
        let (right, opens) = hold.peel(b"del;\x07Z");
        assert_eq!(right, b"Z");
        assert_eq!(opens[0].model, "");
        assert!(opens[0].think.is_empty());
        let mut wide = OscHold::new();
        let mut chunk = vec![0x1B];
        chunk.extend(std::iter::repeat(b'x').take(OSC_HOLD_CAP));
        let (raw, none) = wide.peel(&chunk);
        assert!(none.is_empty());
        assert_eq!(raw.len(), chunk.len());
    }

    /// The `choosing_a_family_writes_the_cell_at_once` test opens a cell and writes Grok at once.
    /// A second open returns no wires. Choosing Grok writes a row. Picking `grok-4.3` stores that id and standing. A bad temperature returns `settings` and leaves 1.0. Close keeps the written model.
    #[test]
    fn choosing_a_family_writes_the_cell_at_once() {
        let mut doc = GrokDoc::memory();
        let mut session = ModelSession::shut();
        let cell = [0x11u8; 16];
        let key = crate::amwp::cell_text(&cell);
        let effect = session.open_cell(cell, &key, "", &ThinkSnap::off(), &mut doc);
        assert!(session.open);
        assert_eq!(session.family, Some(Family::Ollama));
        assert_eq!(effect.wires.len(), 2);
        assert!(session.open_cell(cell, &key, "grok/m", &ThinkSnap::off(), &mut doc).wires.is_empty());
        let mut think = ThinkSnap::off();
        think.ollama_id = "qwen".to_string();
        let moved = session.choose_family(Family::Ollama, "grok/m", &think, &mut doc, &[key.clone()], 0, &[]);
        assert_eq!(moved.target.as_deref(), Some("ollama/qwen"));
        let grok = session.choose_family(Family::Grok, "ollama/qwen", &think, &mut doc, &[key.clone()], 0, &[]);
        assert_eq!(grok.target.as_deref(), Some("grok/"));
        assert!(doc.entry(&key).is_some());
        let picked = session.pick_grok_model("grok-4.3", &mut doc);
        assert_eq!(picked.target.as_deref(), Some("grok/grok-4.3"));
        assert_eq!(doc.entry(&key).expect("model").model, "grok-4.3");
        assert!(doc.standing().expect("first id").model == "grok-4.3");
        session.set_send_effort(false, &mut doc);
        assert!(!doc.entry(&key).expect("send").send_reasoning_effort);
        let bad = session.set_temperature("nope", &mut doc);
        assert_eq!(bad.status, Some(SETTINGS_WORD));
        assert_eq!(doc.entry(&key).expect("temp stays").temperature, 1.0);
        session.set_temperature("0.5", &mut doc);
        assert_eq!(doc.entry(&key).expect("temp").temperature, 0.5);
        session.close();
        assert!(!session.open);
        assert_eq!(doc.entry(&key).expect("close keeps").model, "grok-4.3");
    }

    /// The `opening_on_grok_asks_for_the_language_models` test queues one catalog wire.
    /// The combo starts empty. An empty reply clears the combo and stores the red miss. The stored id stays.
    /// A reply with model lines replaces the combo and clears the miss. An unknown selected id stays first.
    #[test]
    fn opening_on_grok_asks_for_the_language_models() {
        let mut doc = GrokDoc::memory();
        let mut session = ModelSession::shut();
        let cell = [0x33u8; 16];
        let key = crate::amwp::cell_text(&cell);
        let effect = session.open_cell(cell, &key, "grok/grok-4.7", &ThinkSnap::off(), &mut doc);
        assert_eq!(session.family, Some(Family::Grok));
        assert_eq!(effect.wires.len(), 1);
        assert!(matches!(effect.wires[0], ModelWire::GrokModels { cell: found } if found == cell));
        assert!(session.grok_choices.is_empty());
        assert!(session.grok_model.is_none());
        session.grok_model = Some("grok-4.7".to_string());
        session.apply_grok_models(&key, "grok_models_result:\n");
        assert!(session.grok_choices.is_empty());
        assert_eq!(session.grok_model.as_deref(), Some("grok-4.7"));
        assert_eq!(session.list_miss, Some(GROK_LIST_MISS));
        session.apply_grok_models(&key, "grok_models_result:\nmodel: grok-4.7\nmodel: remote-only\n");
        assert_eq!(session.grok_choices, vec!["grok-4.7".to_string(), "remote-only".to_string()]);
        assert!(session.list_miss.is_none());
        session.grok_model = Some("kept-local".to_string());
        session.apply_grok_models(&key, "grok_models_result:\nmodel: remote-only\n");
        assert_eq!(session.grok_choices[0], "kept-local");
        assert_eq!(session.grok_choices[1], "remote-only");
        assert_eq!(session.grok_model.as_deref(), Some("kept-local"));
    }

    /// The `a_show_reply_fills_an_empty_field_without_a_target` test fills an empty Ollama id from a show reply.
    /// The method returns false. `effort_off` clears the send flag on the document.
    #[test]
    fn a_show_reply_fills_an_empty_field_without_a_target() {
        let mut doc = GrokDoc::memory();
        let mut session = ModelSession::shut();
        let cell = [0x22u8; 16];
        let key = crate::amwp::cell_text(&cell);
        let _ = session.open_cell(cell, &key, "", &ThinkSnap::off(), &mut doc);
        session.ollama_id = "typed".to_string();
        session.apply_tags(&key, "tags_result:\n");
        assert!(session.ollama_tags.is_empty());
        assert_eq!(session.ollama_id, "typed");
        assert_eq!(session.list_miss, Some(OLLAMA_LIST_MISS));
        session.apply_tags(&key, "tags_result:\nmodel: qwen\n");
        assert_eq!(session.ollama_id, "typed");
        assert!(session.list_miss.is_none());
        session.ollama_id = "qwen".to_string();
        assert_eq!(session.ollama_tags, vec!["qwen".to_string()]);
        let stored = session.apply_show(&key, "show_result: qwen\nvalue: low\nvalue: true\n", &ThinkSnap::off());
        assert!(!stored);
        assert_eq!(session.ollama_id, "qwen");
        assert_eq!(session.ollama_levels, vec!["low".to_string()]);
        assert!(session.effort_visible() == false);
        let think = session.set_ollama_think(true, &ThinkSnap::off()).think.expect("think");
        assert!(think.ollama_on);
        session.ollama_levels = vec!["low".to_string()];
        assert!(session.effort_visible());
        doc.write(&key, &GrokEntry::blank("grok-4.3"), false);
        assert!(session.effort_off(&key, &mut doc));
        assert!(!session.send_effort);
        assert!(!doc.entry(&key).expect("flag").send_reasoning_effort);
    }

    /// The `the_console_set_names_the_family_and_skips_a_bad_number` test builds a console shell set.
    /// The body names provider `grok` and temperature `1.0`. The word `nope` returns `settings`. A dismissed dialog does not open.
    #[test]
    fn the_console_set_names_the_family_and_skips_a_bad_number() {
        let mut session = ModelSession::shut();
        session.open = true;
        session.console = true;
        session.family = Some(Family::Grok);
        session.grok_model = Some("grok-4.3".to_string());
        session.temperature = "1".to_string();
        session.max_tokens = "128000".to_string();
        let effect = session.shell_effect();
        let ModelWire::ShellSet(body) = &effect.wires[0] else {
            panic!("set");
        };
        assert!(body.starts_with("shell_model_set:\n"));
        assert!(body.contains("provider: grok\n"));
        assert!(body.contains("send_effort: true\n"));
        assert!(body.contains("temperature: 1.0\n"));
        assert!(body.contains("max_tokens: 128000\n"));
        assert!(body.contains("grok_model: grok-4.3\n"));
        assert!(!body.contains("key"));
        session.grok_choices.clear();
        session.temperature = "1".to_string();
        let kept = session.shell_effect();
        let ModelWire::ShellSet(kept_body) = &kept.wires[0] else {
            panic!("kept");
        };
        assert!(kept_body.contains("grok_model: grok-4.3\n"));
        session.temperature = "nope".to_string();
        assert_eq!(session.shell_effect().status, Some(SETTINGS_WORD));
        let mut quiet = ModelSession::shut();
        quiet.dismissed = true;
        let skipped = quiet.apply_shell(CONSOLE_CELL, "shell_model_result:\nprovider: ollama\n", &GrokDoc::memory());
        assert!(skipped.wires.is_empty());
        assert!(!quiet.open);
    }

    /// The `the_shell_seats_the_chosen_model` test stores a shell result without opening the dialog.
    /// A Grok result keeps `grok-4.3`. A later `apply_shell` opens the console dialog. A foreign cell key does not replace the stored provider.
    #[test]
    fn the_shell_seats_the_chosen_model() {
        let doc = GrokDoc::memory();
        let body = "shell_model_result:\nprovider: grok\nollama_model: qwen\ngrok_model: grok-4.3\npool_think: true\nthink: high\n";
        let mut seated = ModelSession::shut();
        assert!(seated.seat_shell(CONSOLE_CELL, body));
        assert!(!seated.open);
        assert!(seated.console_pool_thinking());
        assert_eq!(seated.console_provider, "grok");
        assert_eq!(seated.console_ollama, "qwen");
        assert_eq!(
            seated.console_grok.as_ref().expect("grok").model,
            "grok-4.3"
        );
        let mut family = ModelSession::shut();
        assert!(family.seat_shell(CONSOLE_CELL, "shell_model_result:\nprovider: pool\n"));
        assert_eq!(family.console_provider, "pool");
        assert!(family.console_ollama.is_empty());
        assert!(!family.console_pool_thinking());
        assert!(!family.open);
        assert!(!family.seat_shell("00000000-0000-0000-0000-000000000001", body));
        assert_eq!(family.console_provider, "pool");
        let effect = seated.apply_shell(CONSOLE_CELL, body, &doc);
        assert!(seated.open);
        assert!(seated.console);
        assert!(effect.wires.is_empty());
        seated.family = Some(Family::Grok);
        seated.grok_model = Some("grok-4.3".to_string());
        seated.ollama_id = "qwen".to_string();
        seated.temperature = "1".to_string();
        seated.max_tokens = "128000".to_string();
        seated.pool_think = true;
        let set = seated.shell_effect();
        let ModelWire::ShellSet(wire) = &set.wires[0] else {
            panic!("set");
        };
        assert!(wire.contains("provider: grok\n"));
        assert!(wire.contains("ollama_model: qwen\n"));
        assert!(wire.contains("grok_model: grok-4.3\n"));
        assert!(wire.contains("pool_think: true\n"));
    }

    /// The `the_console_thought_follows_the_family` test checks the console thought gate.
    /// Ollama `think: true` opens the strip. Ollama `think: false` closes it. Grok opens only when `grok_think` is `true`. Pool opens only when `pool_think` is `true`. A background result with an empty think line does not close a strip that was open.
    #[test]
    fn the_console_thought_follows_the_family() {
        let mut ollama = ModelSession::shut();
        assert!(ollama.seat_shell(
            CONSOLE_CELL,
            "provider: ollama\nollama_model: qwen3.5:9b\nthink: true\npool_think: true\n",
        ));
        assert!(ollama.console_thought_open());
        assert_eq!(ollama.console_thought_tag(), "qwen3.5:9b");
        assert!(ollama.console_pool_thinking());

        let mut level = ModelSession::shut();
        assert!(level.seat_shell(
            CONSOLE_CELL,
            "provider: ollama\nmodel: qwen3.5:9b\nthink: high\n",
        ));
        assert!(level.console_thought_open());
        assert_eq!(level.console_thought_tag(), "qwen3.5:9b");
        assert_eq!(level.console_level, "high");

        let mut off = ModelSession::shut();
        assert!(off.seat_shell(
            CONSOLE_CELL,
            "provider: ollama\nollama_model: qwen3.5:9b\nthink: false\npool_think: true\n",
        ));
        assert!(!off.console_thought_open());
        assert!(off.console_pool_thinking());

        let mut bare = ModelSession::shut();
        assert!(bare.seat_shell(CONSOLE_CELL, "provider: ollama\nthink: true\n"));
        assert!(bare.console_thought_open());
        assert_eq!(bare.console_thought_tag(), "ollama");

        let mut grok = ModelSession::shut();
        assert!(grok.seat_shell(
            CONSOLE_CELL,
            "provider: grok\nollama_model: qwen3.5:9b\nthink: true\n",
        ));
        assert!(!grok.console_thought_open());
        assert_eq!(grok.console_thought_tag(), "grok");

        let mut grok_on = ModelSession::shut();
        assert!(grok_on.seat_shell(
            CONSOLE_CELL,
            "provider: grok\ngrok_model: grok-4.3\ngrok_think: true\nthink: true\n",
        ));
        assert!(grok_on.console_thought_open());
        assert_eq!(grok_on.console_thought_tag(), "grok-4.3");

        let mut live = ModelSession::shut();
        live.console = true;
        live.open = true;
        live.family = Some(Family::Grok);
        live.grok_model = Some("grok-4.3".to_string());
        live.ollama_id = "qwen".to_string();
        let armed = live.set_grok_think(true, &ThinkSnap::off());
        assert!(matches!(armed.wires.first(), Some(ModelWire::ShellSet(_))));
        assert!(live.console_thought_open());
        assert_eq!(live.console_thought_tag(), "grok-4.3");

        let mut pool = ModelSession::shut();
        assert!(pool.seat_shell(
            CONSOLE_CELL,
            "provider: pool\nollama_model: qwen3.5:9b\nthink: true\npool_think: true\n",
        ));
        assert!(pool.console_thought_open());
        assert_eq!(pool.console_thought_tag(), "pool");

        let mut pool_off = ModelSession::shut();
        assert!(pool_off.seat_shell(
            CONSOLE_CELL,
            "provider: pool\nthink: true\npool_think: false\n",
        ));
        assert!(!pool_off.console_thought_open());

        let shut = ModelSession::shut();
        assert!(!shut.console_thought_open());
        assert_eq!(shut.console_thought_tag(), "ollama");

        let mut dialog = ModelSession::shut();
        assert!(dialog.seat_shell(
            CONSOLE_CELL,
            "provider: ollama\nollama_model: qwen3.5:9b\nthink: high\n",
        ));
        dialog.open = true;
        dialog.console = true;
        dialog.family = Some(Family::Ollama);
        dialog.ollama_id = "qwen3.5:9b".to_string();
        dialog.ollama_think = false;
        let cleared = dialog.shell_effect();
        assert!(matches!(cleared.wires.first(), Some(ModelWire::ShellSet(_))));
        assert!(!dialog.console_thought_open());
        assert_eq!(dialog.console_level, "high");
        dialog.ollama_think = true;
        dialog.ollama_level = Some("high".to_string());
        let restored = dialog.shell_effect();
        assert!(matches!(restored.wires.first(), Some(ModelWire::ShellSet(_))));
        assert!(dialog.console_thought_open());
        assert_eq!(dialog.console_think, "high");
        assert_eq!(dialog.console_thought_tag(), "qwen3.5:9b");

        assert_eq!(
            console_result_open("shell_model_result:\nprovider: ollama\nthink: true\n"),
            Some(true)
        );
        assert_eq!(
            console_result_open("shell_model_result:\nprovider: ollama\nthink: \n"),
            Some(false)
        );
        assert_eq!(
            console_result_open("shell_model_result:\nprovider: pool\npool_think: true\n"),
            Some(true)
        );
        assert_eq!(
            console_result_open(
                "shell_model_result:\nprovider: grok\ngrok_model: grok-4.3\ngrok_think: true\n"
            ),
            Some(true),
        );
        assert_eq!(
            console_result_open("shell_model_result:\nprovider: grok\nthink: true\n"),
            Some(false),
        );
        assert_eq!(console_result_open("tags_result:\n"), None);
        let mut kept = ModelSession::shut();
        assert!(kept.seat_console_result(
            CONSOLE_CELL,
            "shell_model_result:\nprovider: ollama\nollama_model: deepseek-r1\nthink: true\n",
        ));
        assert!(kept.console_thought_open());
        assert_eq!(kept.console_thought_tag(), "deepseek-r1");
        assert!(kept.seat_console_result(
            CONSOLE_CELL,
            "shell_model_result:\nprovider: ollama\nollama_model: qwen\nthink: \n",
        ));
        assert!(kept.console_thought_open());
        assert_eq!(kept.console_think, "true");
        assert_eq!(kept.console_thought_tag(), "deepseek-r1");
        assert!(kept.seat_console_result(
            CONSOLE_CELL,
            "shell_model_result:\nprovider: ollama\nollama_model: qwen3.5:9b\nthink: high\n",
        ));
        assert!(kept.console_thought_open());
        assert_eq!(kept.console_think, "high");
        assert_eq!(kept.console_thought_tag(), "qwen3.5:9b");
    }
}
