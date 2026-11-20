# Module llama

[Rust tree](../index.md) · **aetheris-client**

**Source** `rust/aetheris/aetheris-client/src/llama.rs`

The module holds the llama-openapi seats for the Rust client.

A path-shaped id, the shown id, the window, the alias, the tokenize body, the tokens-array length, and the heat-count decision live here. Those functions open no socket. `post_tokenize` is the HTTP post. The tests do not call `post_tokenize`.

## Type Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| struct | [`LlamaYaml`](llama/struct.LlamaYaml.md) | The `LlamaYaml` struct is the llama block read from one YAML document. |
| struct | [`LlamaMemo`](llama/struct.LlamaMemo.md) | The `LlamaMemo` struct is the last tokenize payload and the last painted count. |
| enum | [`LlamaBeat`](llama/enum.LlamaBeat.md) | The `LlamaBeat` enum is the heat-count decision for one prompt. |
| struct | [`HttpTarget`](llama/struct.HttpTarget.md) | One parsed `http` URL. `https` is not a target. |

## Function Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`path_shaped`](#function-path_shaped) | The `path_shaped` function reports whether a model id is a file path. |
| fn | [`shown_id`](#function-shown_id) | The `shown_id` function returns the model id to display. |
| fn | [`shown_rows`](#function-shown_rows) | The `shown_rows` function maps catalog names through `shown_id`. |
| fn | [`shown_engine`](#function-shown_engine) | The `shown_engine` function returns the engine word for a status strip. |
| fn | [`heat_label`](#function-heat_label) | The `heat_label` function returns the token-window label for one llama id. |
| fn | [`llama_window`](#function-llama_window) | The `llama_window` function returns the llama-openapi context window. |
| fn | [`llama_yaml`](#function-llama_yaml) | The `llama_yaml` function reads `spring.ai.llama-openapi` from one YAML document. |
| fn | [`map_get`](#function-map_get) | The `map_get` function returns one value from a YAML mapping. |
| fn | [`yaml_window`](#function-yaml_window) | The `yaml_window` function reads a positive `i32` from one YAML value. |
| fn | [`tokenize_body`](#function-tokenize_body) | The `tokenize_body` function writes one tokenize document. |
| fn | [`token_count`](#function-token_count) | The `token_count` function reads the length of a `tokens` array. |
| fn | [`find_bytes`](#function-find_bytes) | The `find_bytes` function returns the first index of `needle` in `hay`. |
| fn | [`tokenize_url`](#function-tokenize_url) | The `tokenize_url` function returns the tokenize URL for one base. |
| fn | [`missed_count`](#function-missed_count) | The `missed_count` function returns a stored tokenize count. |
| fn | [`llama_step`](#function-llama_step) | The `llama_step` function decides whether one prompt posts. |
| fn | [`shell_prompt`](#function-shell_prompt) | The `shell_prompt` function returns the text the SSH guess already counts. |
| fn | [`finish_post`](#function-finish_post) | The `finish_post` function paints one tokenize result. |
| fn | [`post_tokenize`](#function-post_tokenize) | The `post_tokenize` function posts one tokenize body and returns the tokens-array length. |
| fn | [`http_target`](#function-http_target) | The `http_target` function parses an `http` URL. |
| fn | [`resolve_host`](#function-resolve_host) | The `resolve_host` function returns one socket address for a host and a port. |
| fn | [`split_http`](#function-split_http) | The `split_http` function splits a response at the header break. |

## Function Detail

### path_shaped

<a id="function-path_shaped"></a>

```rust
pub fn path_shaped(id: &str) -> bool
```

The `path_shaped` function reports whether a model id is a file path.
A blank id is not a path. An id that contains `/` or `\` is a path. An id that ends with `.gguf` is a path. The suffix compare ignores case.

### shown_id

<a id="function-shown_id"></a>

```rust
pub fn shown_id(id: &str, alias: &str) -> String
```

The `shown_id` function returns the model id to display.
A blank id stays blank. A path-shaped id shows the trimmed alias when that alias is set. A short id stays. A blank alias leaves the id unchanged.

### shown_rows

<a id="function-shown_rows"></a>

```rust
pub fn shown_rows(names: &[String], alias: &str) -> Vec<String>
```

The `shown_rows` function maps catalog names through `shown_id`.
An empty name is skipped. A repeated name is one row. The reply order is kept.

### shown_engine

<a id="function-shown_engine"></a>

```rust
pub fn shown_engine(word: &str, alias: &str) -> String
```

The `shown_engine` function returns the engine word for a status strip.
A word that does not start with `llama-openapi` is returned as stored. An empty id returns `llama-openapi`. A path-shaped id shows `llama-openapi/` plus the alias when that alias replaces the id. Any other llama word is returned as stored.

### heat_label

<a id="function-heat_label"></a>

```rust
pub fn heat_label(id: &str, alias: &str) -> String
```

The `heat_label` function returns the token-window label for one llama id.
An empty id returns `llama-openapi`. Any other id returns `llama-openapi/` plus the shown id.

### llama_window

<a id="function-llama_window"></a>

```rust
pub fn llama_window(stored: i32) -> i32
```

The `llama_window` function returns the llama-openapi context window.
A stored window greater than 0 is that window. A missing window and a stored window of 0 both return 4096.

### llama_yaml

<a id="function-llama_yaml"></a>

```rust
pub fn llama_yaml(text: &str) -> LlamaYaml
```

The `llama_yaml` function reads `spring.ai.llama-openapi` from one YAML document.
A bad document returns a blank block. The text is not logged. A missing `num-ctx` leaves the window at 0.

### map_get

<a id="function-map_get"></a>

```rust
fn map_get<'a>(map: &'a serde_yaml::Mapping, key: &str) -> Option<&'a serde_yaml::Value>
```

The `map_get` function returns one value from a YAML mapping.
A missing key returns `None`.

### yaml_window

<a id="function-yaml_window"></a>

```rust
fn yaml_window(value: &serde_yaml::Value) -> i32
```

The `yaml_window` function reads a positive `i32` from one YAML value.
A number under 1 returns 0. A number above `i32::MAX` returns 0. A numeric string is accepted. Any other value returns 0.

### tokenize_body

<a id="function-tokenize_body"></a>

```rust
pub fn tokenize_body(text: &str) -> String
```

The `tokenize_body` function writes one tokenize document.
The document has one field, `content`. The text is JSON-escaped. The function does not open a socket.

### token_count

<a id="function-token_count"></a>

```rust
pub fn token_count(json: &str) -> i32
```

The `token_count` function reads the length of a `tokens` array.
A body that is not JSON returns -1. A missing `tokens` array returns -1. An empty array returns 0. The function does not open a socket.

### find_bytes

<a id="function-find_bytes"></a>

```rust
fn find_bytes(hay: &[u8], needle: &[u8]) -> Option<usize>
```

The `find_bytes` function returns the first index of `needle` in `hay`.
A missing needle returns `None`.

### tokenize_url

<a id="function-tokenize_url"></a>

```rust
pub fn tokenize_url(base: &str) -> String
```

The `tokenize_url` function returns the tokenize URL for one base.
A blank base returns `http://127.0.0.1:3333/tokenize`. A trailing slash is removed. The path `/tokenize` is appended.

### missed_count

<a id="function-missed_count"></a>

```rust
pub fn missed_count(stored: Option<i32>, fallback: i32) -> i32
```

The `missed_count` function returns a stored tokenize count.
A stored count of 0 or more is that count. A missing count returns `fallback`.

### llama_step

<a id="function-llama_step"></a>

```rust
pub fn llama_step(prompt: Option<&str>, memo: &LlamaMemo, fallback: i32) -> LlamaBeat
```

The `llama_step` function decides whether one prompt posts.
A missing prompt keeps `missed_count`. The same payload with a stored count of 0 or more keeps that count. The same payload with no stored count keeps `fallback`. A new payload returns `Post`.

### shell_prompt

<a id="function-shell_prompt"></a>

```rust
pub fn shell_prompt(held: bool, memory: &str, live: &str) -> Option<String>
```

The `shell_prompt` function returns the text the SSH guess already counts.
A held tally with an empty draft returns `None`. A held tally with a draft returns that draft. An unheld draft that is empty, or that the memory already ends with, returns the memory. Any other unheld draft returns the memory plus the draft.

### finish_post

<a id="function-finish_post"></a>

```rust
pub fn finish_post(
    tokens: i32,
    payload: &str,
    prior: Option<i32>,
    fallback: i32,
    held_base: Option<i32>,
) -> (i32, LlamaMemo)
```

The `finish_post` function paints one tokenize result.
A count of 0 or more is the painted integer. A held base is added to that count. A miss keeps the stored count, or `fallback` when no tokenize count has landed. The payload is stored either way. The count is stored only on success.

### post_tokenize

<a id="function-post_tokenize"></a>

```rust
pub fn post_tokenize(url: &str, key: &str, body: &str) -> i32
```

The `post_tokenize` function posts one tokenize body and returns the tokens-array length.
The scheme `https` returns -1. A status outside 2xx returns -1. A connect failure returns -1. A blank key omits `Authorization`. The key is not written to a log. The tests do not call this function.

### http_target

<a id="function-http_target"></a>

```rust
fn http_target(url: &str) -> Option<HttpTarget>
```

The `http_target` function parses an `http` URL.
An empty URL returns `None`. An `https` URL returns `None`. A missing host returns `None`. A missing port uses 80. The path starts with `/`.

### resolve_host

<a id="function-resolve_host"></a>

```rust
fn resolve_host(host: &str, port: u16) -> Option<SocketAddr>
```

The `resolve_host` function returns one socket address for a host and a port.
A name that does not resolve returns `None`.

### split_http

<a id="function-split_http"></a>

```rust
fn split_http(text: &str) -> Option<(&str, &str)>
```

The `split_http` function splits a response at the header break.
A missing break returns `None`.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
