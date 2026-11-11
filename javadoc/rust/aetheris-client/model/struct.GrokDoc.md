# struct GrokDoc

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **model**

**Source** `aetheris/rust/aetheris-client/src/model.rs`

```rust
pub struct GrokDoc
```

The [`GrokDoc`](struct.GrokDoc.md) struct is the Grok settings document.
The file is `aetheris-grok.yaml` under `.casserole` in the home directory. A version other than 1 is left unread. A blocked document is not written. `path` is the file. `blocked` stops reads and writes. `standing` is the default entry. `cells` are the per-cell entries.

| | |
| --- | --- |
| **Specification** | L37 rust-aetheris-client.draft.001.md |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| Option<PathBuf> | [`path`](#member-path) |  |
| bool | [`blocked`](#member-blocked) |  |
| Option<GrokEntry> | [`standing`](#member-standing) |  |
| Vec<(String, GrokEntry)> | [`cells`](#member-cells) |  |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`memory`](#method-memory) | The `memory` method returns a document with no file. |
| fn | [`load`](#method-load) | The `load` method reads `path`. |
| fn | [`blocked`](#method-blocked) | The `blocked` method reports a document that was refused. |
| fn | [`standing`](#method-standing) | The `standing` method returns a copy of the default entry. |
| fn | [`entry`](#method-entry) | The `entry` method returns a copy of one cell. |
| fn | [`burner_cloud`](#method-burner_cloud) | The `burner_cloud` method reports the burner flag of one cell. |
| fn | [`write`](#method-write) | The `write` method stores `entry` for `cell_id` and saves the file. |
| fn | [`copy_onto`](#method-copy_onto) | The `copy_onto` method writes `source` onto `cell_id`. |
| fn | [`forget_all`](#method-forget_all) | The `forget_all` method drops the named cells in one write. |
| fn | [`console_seed`](#method-console_seed) | The `console_seed` method returns five strings for a console whose Grok id is empty. |
| fn | [`read`](#method-read) | The `read` method loads the file into this document. |
| fn | [`save`](#method-save) | The `save` method writes the document. |

## Field Detail

### path

<a id="member-path"></a>

```rust
path: Option<PathBuf>
```

### blocked

<a id="member-blocked"></a>

```rust
blocked: bool
```

### standing

<a id="member-standing"></a>

```rust
standing: Option<GrokEntry>
```

### cells

<a id="member-cells"></a>

```rust
cells: Vec<(String, GrokEntry)>
```

## Method Detail

### memory

<a id="method-memory"></a>

```rust
pub fn memory() -> Self
```

The `memory` method returns a document with no file.
The document is not blocked. Nothing is written.

### load

<a id="method-load"></a>

```rust
pub fn load(path: PathBuf) -> Self
```

The `load` method reads `path`.
A missing file stays empty. An empty file stays empty. A bad document is blocked.

### blocked

<a id="method-blocked"></a>

```rust
pub fn blocked(&self) -> bool
```

The `blocked` method reports a document that was refused.
True means the file is left unread and later writes return.

### standing

<a id="method-standing"></a>

```rust
pub fn standing(&self) -> Option<GrokEntry>
```

The `standing` method returns a copy of the default entry.
A blocked document returns `None`. A missing default returns `None`.

### entry

<a id="method-entry"></a>

```rust
pub fn entry(&self, cell_id: &str) -> Option<GrokEntry>
```

The `entry` method returns a copy of one cell.
A blocked document returns `None`. A missing id returns `None`.

### burner_cloud

<a id="method-burner_cloud"></a>

```rust
pub fn burner_cloud(&self, cell_id: &str) -> bool
```

The `burner_cloud` method reports the burner flag of one cell.
A missing cell returns false.

### write

<a id="method-write"></a>

```rust
pub fn write(&mut self, cell_id: &str, entry: &GrokEntry, also_default: bool)
```

The `write` method stores `entry` for `cell_id` and saves the file.
A blocked document returns. An empty cell id returns. An empty model never becomes standing. A non-empty model becomes standing when `also_default` is set. A non-empty model also becomes standing when no standing row exists.

### copy_onto

<a id="method-copy_onto"></a>

```rust
pub fn copy_onto(&mut self, cell_id: &str, source: &GrokEntry)
```

The `copy_onto` method writes `source` onto `cell_id`.
The standing row is not forced by this call.

### forget_all

<a id="method-forget_all"></a>

```rust
pub fn forget_all(&mut self, ids: &[String])
```

The `forget_all` method drops the named cells in one write.
Standing stays. A blocked document returns. An empty list returns. A list that removes nothing does not write.

### console_seed

<a id="method-console_seed"></a>

```rust
pub fn console_seed(&self) -> [String; 5]
```

The `console_seed` method returns five strings for a console whose Grok id is empty.
The file is not written. A standing row with a model is used. Otherwise the factory entry is used. The five strings are the model, the effort, send effort, stream, and burner cloud. An empty effort becomes `high`.

### read

<a id="method-read"></a>

```rust
fn read(&mut self)
```

The `read` method loads the file into this document.
A document with no path returns. A missing file returns. An empty file returns. A blocked parse clears the rows and sets `blocked`. A good parse replaces standing and the cells.

### save

<a id="method-save"></a>

```rust
fn save(&self)
```

The `save` method writes the document.
A blocked document returns. A document with no path returns. The parent directory is created. A failed write is ignored.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
