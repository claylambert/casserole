// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The module holds the llama-openapi seats for the Rust client.
//!
//! A path-shaped id, the shown id, the window, the alias, the tokenize body, the tokens-array length, and the heat-count decision live here. Those functions open no socket. `post_tokenize` is the HTTP post. The tests do not call `post_tokenize`.

use std::io::{Read, Write};
use std::net::{SocketAddr, TcpStream, ToSocketAddrs};
use std::time::Duration;

/// The `path_shaped` function reports whether a model id is a file path.
/// A blank id is not a path. An id that contains `/` or `\` is a path. An id that ends with `.gguf` is a path. The suffix compare ignores case.
pub fn path_shaped(id: &str) -> bool {
    let name = id.trim();
    if name.is_empty() {
        return false;
    }
    if name.contains('/') || name.contains('\\') {
        return true;
    }
    name.len() >= 5 && name[name.len() - 5..].eq_ignore_ascii_case(".gguf")
}

/// The `shown_id` function returns the model id to display.
/// A blank id stays blank. A path-shaped id shows the trimmed alias when that alias is set. A short id stays. A blank alias leaves the id unchanged.
pub fn shown_id(id: &str, alias: &str) -> String {
    let raw = id.trim();
    if raw.is_empty() {
        return String::new();
    }
    let name = alias.trim();
    if !name.is_empty() && path_shaped(raw) {
        return name.to_string();
    }
    raw.to_string()
}

/// The `shown_rows` function maps catalog names through `shown_id`.
/// An empty name is skipped. A repeated name is one row. The reply order is kept.
pub fn shown_rows(names: &[String], alias: &str) -> Vec<String> {
    let mut shown = Vec::new();
    for name in names {
        let one = shown_id(name, alias);
        if one.is_empty() {
            continue;
        }
        if shown.iter().any(|row| row == &one) {
            continue;
        }
        shown.push(one);
    }
    shown
}

/// The `shown_engine` function returns the engine word for a status strip.
/// A word that does not start with `llama-openapi` is returned as stored. An empty id returns `llama-openapi`. A path-shaped id shows `llama-openapi/` plus the alias when that alias replaces the id. Any other llama word is returned as stored.
pub fn shown_engine(word: &str, alias: &str) -> String {
    let stored = word.trim();
    if !stored.starts_with("llama-openapi") {
        return stored.to_string();
    }
    let id = match stored.find('/') {
        Some(i) if i + 1 < stored.len() => stored[i + 1..].trim(),
        _ => "",
    };
    if id.is_empty() {
        return "llama-openapi".to_string();
    }
    let shown = shown_id(id, alias);
    if shown != id {
        return format!("llama-openapi/{shown}");
    }
    stored.to_string()
}

/// The `heat_label` function returns the token-window label for one llama id.
/// An empty id returns `llama-openapi`. Any other id returns `llama-openapi/` plus the shown id.
pub fn heat_label(id: &str, alias: &str) -> String {
    let shown = shown_id(id, alias);
    if shown.is_empty() {
        "llama-openapi".to_string()
    } else {
        format!("llama-openapi/{shown}")
    }
}

/// The `llama_window` function returns the llama-openapi context window.
/// A stored window greater than 0 is that window. A missing window and a stored window of 0 both return 4096.
pub fn llama_window(stored: i32) -> i32 {
    if stored > 0 { stored } else { 4096 }
}

/// The `LlamaYaml` struct is the llama block read from one YAML document.
/// `window` is `num-ctx`, or 0 when that key is absent. `alias` is the alias text. `base` is the base URL text. `key` is the API key text. The key is not written to a log.
pub struct LlamaYaml {
    pub window: i32,
    pub alias: String,
    pub base: String,
    pub key: String,
}

impl LlamaYaml {
    /// The `blank` method returns a block with nothing read.
    /// The window is 0. The three strings are empty.
    fn blank() -> Self {
        Self {
            window: 0,
            alias: String::new(),
            base: String::new(),
            key: String::new(),
        }
    }
}

/// The `llama_yaml` function reads `spring.ai.llama-openapi` from one YAML document.
/// A bad document returns a blank block. The text is not logged. A missing `num-ctx` leaves the window at 0.
pub fn llama_yaml(text: &str) -> LlamaYaml {
    if text.trim().is_empty() {
        return LlamaYaml::blank();
    }
    let loaded: serde_yaml::Value = match serde_yaml::from_str(text) {
        Ok(value) => value,
        Err(_) => return LlamaYaml::blank(),
    };
    let Some(root) = loaded.as_mapping() else {
        return LlamaYaml::blank();
    };
    let Some(spring) = map_get(root, "spring").and_then(serde_yaml::Value::as_mapping) else {
        return LlamaYaml::blank();
    };
    let Some(ai) = map_get(spring, "ai").and_then(serde_yaml::Value::as_mapping) else {
        return LlamaYaml::blank();
    };
    let Some(llama) = map_get(ai, "llama-openapi").and_then(serde_yaml::Value::as_mapping) else {
        return LlamaYaml::blank();
    };
    let mut read = LlamaYaml::blank();
    if let Some(alias) = map_get(llama, "alias").and_then(serde_yaml::Value::as_str) {
        read.alias = alias.to_string();
    }
    if let Some(base) = map_get(llama, "base-url").and_then(serde_yaml::Value::as_str) {
        read.base = base.to_string();
    }
    if let Some(key) = map_get(llama, "api-key").and_then(serde_yaml::Value::as_str) {
        read.key = key.to_string();
    }
    if let Some(chat) = map_get(llama, "chat").and_then(serde_yaml::Value::as_mapping) {
        if let Some(options) = map_get(chat, "options").and_then(serde_yaml::Value::as_mapping) {
            if let Some(value) = map_get(options, "num-ctx") {
                read.window = yaml_window(value);
            }
        }
    }
    read
}

/// The `map_get` function returns one value from a YAML mapping.
/// A missing key returns `None`.
fn map_get<'a>(map: &'a serde_yaml::Mapping, key: &str) -> Option<&'a serde_yaml::Value> {
    map.get(serde_yaml::Value::String(key.to_string()))
}

/// The `yaml_window` function reads a positive `i32` from one YAML value.
/// A number under 1 returns 0. A number above `i32::MAX` returns 0. A numeric string is accepted. Any other value returns 0.
fn yaml_window(value: &serde_yaml::Value) -> i32 {
    let raw = if let Some(n) = value.as_i64() {
        n
    } else if let Some(n) = value.as_u64() {
        match i64::try_from(n) {
            Ok(n) => n,
            Err(_) => return 0,
        }
    } else if let Some(text) = value.as_str() {
        match text.trim().parse::<i64>() {
            Ok(n) => n,
            Err(_) => return 0,
        }
    } else {
        return 0;
    };
    if raw < 1 || raw > i32::MAX as i64 {
        return 0;
    }
    raw as i32
}

/// The `tokenize_body` function writes one tokenize document.
/// The document has one field, `content`. The text is JSON-escaped. The function does not open a socket.
pub fn tokenize_body(text: &str) -> String {
    let mut out = String::from("{\"content\":\"");
    for c in text.chars() {
        match c {
            '"' => out.push_str("\\\""),
            '\\' => out.push_str("\\\\"),
            '\n' => out.push_str("\\n"),
            '\r' => out.push_str("\\r"),
            '\t' => out.push_str("\\t"),
            c if (c as u32) < 0x20 => out.push_str(&format!("\\u{:04x}", c as u32)),
            c => out.push(c),
        }
    }
    out.push_str("\"}");
    out
}

/// The `token_count` function reads the length of a `tokens` array.
/// A body that is not JSON returns -1. A missing `tokens` array returns -1. An empty array returns 0. The function does not open a socket.
pub fn token_count(json: &str) -> i32 {
    let bytes = json.as_bytes();
    let key = b"\"tokens\"";
    let Some(at) = find_bytes(bytes, key) else {
        return -1;
    };
    let mut i = at + key.len();
    while i < bytes.len() && bytes[i].is_ascii_whitespace() {
        i += 1;
    }
    if i >= bytes.len() || bytes[i] != b':' {
        return -1;
    }
    i += 1;
    while i < bytes.len() && bytes[i].is_ascii_whitespace() {
        i += 1;
    }
    if i >= bytes.len() || bytes[i] != b'[' {
        return -1;
    }
    i += 1;
    let mut count = 0i32;
    let mut depth = 0i32;
    let mut in_string = false;
    let mut escape = false;
    let mut saw = false;
    while i < bytes.len() {
        let c = bytes[i];
        if in_string {
            if escape {
                escape = false;
            } else if c == b'\\' {
                escape = true;
            } else if c == b'"' {
                in_string = false;
            }
            i += 1;
            continue;
        }
        if c == b'"' {
            in_string = true;
            if depth == 0 {
                saw = true;
            }
        } else if c == b'[' || c == b'{' {
            depth += 1;
            if depth == 1 {
                saw = true;
            }
        } else if c == b']' && depth == 0 {
            if saw {
                count += 1;
            }
            return count;
        } else if c == b']' || c == b'}' {
            if depth > 0 {
                depth -= 1;
            }
        } else if c == b',' && depth == 0 {
            if saw {
                count += 1;
            }
            saw = false;
        } else if !c.is_ascii_whitespace() && depth == 0 {
            saw = true;
        }
        i += 1;
    }
    -1
}

/// The `find_bytes` function returns the first index of `needle` in `hay`.
/// A missing needle returns `None`.
fn find_bytes(hay: &[u8], needle: &[u8]) -> Option<usize> {
    if needle.is_empty() || hay.len() < needle.len() {
        return None;
    }
    let last = hay.len() - needle.len();
    let mut i = 0;
    while i <= last {
        if &hay[i..i + needle.len()] == needle {
            return Some(i);
        }
        i += 1;
    }
    None
}

/// The `tokenize_url` function returns the tokenize URL for one base.
/// A blank base returns `http://127.0.0.1:3333/tokenize`. A trailing slash is removed. The path `/tokenize` is appended.
pub fn tokenize_url(base: &str) -> String {
    let mut base = base.trim().to_string();
    while base.ends_with('/') {
        base.pop();
    }
    if base.is_empty() {
        return "http://127.0.0.1:3333/tokenize".to_string();
    }
    format!("{base}/tokenize")
}

/// The `missed_count` function returns a stored tokenize count.
/// A stored count of 0 or more is that count. A missing count returns `fallback`.
pub fn missed_count(stored: Option<i32>, fallback: i32) -> i32 {
    match stored {
        Some(n) if n >= 0 => n,
        _ => fallback,
    }
}

/// The `LlamaMemo` struct is the last tokenize payload and the last painted count.
/// `payload` is the prompt that was posted. `count` is the painted integer, or `None` when no tokenize count has landed.
pub struct LlamaMemo {
    pub payload: String,
    pub count: Option<i32>,
}

impl LlamaMemo {
    /// The `fresh` method returns a memo with nothing stored.
    /// The payload is empty. The count is absent.
    pub fn fresh() -> Self {
        Self {
            payload: String::new(),
            count: None,
        }
    }
}

/// The `LlamaBeat` enum is the heat-count decision for one prompt.
/// `Keep` paints a count and does not post. `Post` carries the JSON body. The tests do not post.
pub enum LlamaBeat {
    Keep(i32),
    Post { body: String },
}

/// The `llama_step` function decides whether one prompt posts.
/// A missing prompt keeps `missed_count`. The same payload with a stored count of 0 or more keeps that count. The same payload with no stored count keeps `fallback`. A new payload returns `Post`.
pub fn llama_step(prompt: Option<&str>, memo: &LlamaMemo, fallback: i32) -> LlamaBeat {
    let Some(prompt) = prompt else {
        return LlamaBeat::Keep(missed_count(memo.count, fallback));
    };
    if memo.payload == prompt {
        if memo.count.is_some_and(|n| n >= 0) {
            return LlamaBeat::Keep(memo.count.unwrap_or(fallback));
        }
        return LlamaBeat::Keep(fallback);
    }
    LlamaBeat::Post {
        body: tokenize_body(prompt),
    }
}

/// The `shell_prompt` function returns the text the SSH guess already counts.
/// A held tally with an empty draft returns `None`. A held tally with a draft returns that draft. An unheld draft that is empty, or that the memory already ends with, returns the memory. Any other unheld draft returns the memory plus the draft.
pub fn shell_prompt(held: bool, memory: &str, live: &str) -> Option<String> {
    if held {
        if live.is_empty() {
            return None;
        }
        return Some(live.to_string());
    }
    if live.is_empty() || memory.ends_with(live) {
        return Some(memory.to_string());
    }
    let mut joined = String::with_capacity(memory.len() + live.len());
    joined.push_str(memory);
    joined.push_str(live);
    Some(joined)
}

/// The `finish_post` function paints one tokenize result.
/// A count of 0 or more is the painted integer. A held base is added to that count. A miss keeps the stored count, or `fallback` when no tokenize count has landed. The payload is stored either way. The count is stored only on success.
pub fn finish_post(
    tokens: i32,
    payload: &str,
    prior: Option<i32>,
    fallback: i32,
    held_base: Option<i32>,
) -> (i32, LlamaMemo) {
    if tokens >= 0 {
        let painted = match held_base {
            Some(base) => base.saturating_add(tokens),
            None => tokens,
        };
        return (
            painted,
            LlamaMemo {
                payload: payload.to_string(),
                count: Some(painted),
            },
        );
    }
    (
        missed_count(prior, fallback),
        LlamaMemo {
            payload: payload.to_string(),
            count: prior,
        },
    )
}

/// The `post_tokenize` function posts one tokenize body and returns the tokens-array length.
/// The scheme `https` returns -1. A status outside 2xx returns -1. A connect failure returns -1. A blank key omits `Authorization`. The key is not written to a log. The tests do not call this function.
pub fn post_tokenize(url: &str, key: &str, body: &str) -> i32 {
    let Some(target) = http_target(url) else {
        return -1;
    };
    let Some(addr) = resolve_host(&target.host, target.port) else {
        return -1;
    };
    let mut stream = match TcpStream::connect_timeout(&addr, Duration::from_secs(2)) {
        Ok(stream) => stream,
        Err(_) => return -1,
    };
    let _ = stream.set_read_timeout(Some(Duration::from_secs(2)));
    let _ = stream.set_write_timeout(Some(Duration::from_secs(2)));
    let mut req = format!(
        "POST {path} HTTP/1.1\r\nHost: {host}\r\nContent-Type: application/json\r\nContent-Length: {len}\r\nConnection: close\r\n",
        path = target.path,
        host = target.host_header,
        len = body.len(),
    );
    if !key.is_empty() {
        req.push_str("Authorization: Bearer ");
        req.push_str(key);
        req.push_str("\r\n");
    }
    req.push_str("\r\n");
    req.push_str(body);
    if stream.write_all(req.as_bytes()).is_err() {
        return -1;
    }
    let mut buf = Vec::new();
    match stream.read_to_end(&mut buf) {
        Ok(_) => {}
        Err(_) if !buf.is_empty() => {}
        Err(_) => return -1,
    }
    let text = String::from_utf8_lossy(&buf);
    let Some((head, rest)) = split_http(&text) else {
        return -1;
    };
    let status = head.lines().next().unwrap_or("");
    let code = status.split_whitespace().nth(1).unwrap_or("");
    if code.len() != 3 || !code.starts_with('2') {
        return -1;
    }
    token_count(rest)
}

/// One parsed `http` URL. `https` is not a target.
struct HttpTarget {
    host: String,
    port: u16,
    path: String,
    host_header: String,
}

/// The `http_target` function parses an `http` URL.
/// An empty URL returns `None`. An `https` URL returns `None`. A missing host returns `None`. A missing port uses 80. The path starts with `/`.
fn http_target(url: &str) -> Option<HttpTarget> {
    let url = url.trim();
    let rest = url.strip_prefix("http://")?;
    if rest.is_empty() {
        return None;
    }
    let (authority, path) = match rest.find('/') {
        Some(i) => (&rest[..i], rest[i..].to_string()),
        None => (rest, "/".to_string()),
    };
    if authority.is_empty() || path.is_empty() {
        return None;
    }
    let (host, port) = match authority.rfind(':') {
        Some(i) => {
            let host = &authority[..i];
            let port = authority[i + 1..].parse::<u16>().ok()?;
            if host.is_empty() {
                return None;
            }
            (host.to_string(), port)
        }
        None => (authority.to_string(), 80),
    };
    let host_header = if port == 80 {
        host.clone()
    } else {
        format!("{host}:{port}")
    };
    Some(HttpTarget {
        host,
        port,
        path,
        host_header,
    })
}

/// The `resolve_host` function returns one socket address for a host and a port.
/// A name that does not resolve returns `None`.
fn resolve_host(host: &str, port: u16) -> Option<SocketAddr> {
    let mut found = (host, port).to_socket_addrs().ok()?;
    found.next()
}

/// The `split_http` function splits a response at the header break.
/// A missing break returns `None`.
fn split_http(text: &str) -> Option<(&str, &str)> {
    text.split_once("\r\n\r\n")
}

#[cfg(test)]
mod tests {
    use super::*;

    /// The `llama_alias_replaces_a_path` test checks the shown id.
    /// A path shows the alias. A short id stays. A blank alias leaves the path. A repeated alias is one row.
    #[test]
    fn llama_alias_replaces_a_path() {
        assert!(path_shaped("C:\\models\\a.gguf"));
        assert!(path_shaped("/models/a.gguf"));
        assert!(path_shaped("a.GGUF"));
        assert!(!path_shaped("Falcon3-3B"));
        assert!(!path_shaped(""));
        assert_eq!(shown_id("/m/a.gguf", "Falcon3-3B"), "Falcon3-3B");
        assert_eq!(shown_id("Falcon3-3B", "Falcon3-3B"), "Falcon3-3B");
        assert_eq!(shown_id("/m/a.gguf", "  "), "/m/a.gguf");
        assert_eq!(shown_id("", "Falcon3-3B"), "");
        let rows = shown_rows(
            &[
                "/a/x.gguf".to_string(),
                "/b/y.gguf".to_string(),
                "short".to_string(),
            ],
            "Falcon3-3B",
        );
        assert_eq!(rows, vec!["Falcon3-3B".to_string(), "short".to_string()]);
        assert_eq!(
            shown_engine("llama-openapi//m/a.gguf", "Falcon3-3B"),
            "llama-openapi/Falcon3-3B"
        );
        assert_eq!(shown_engine("ollama/qwen", "Falcon3-3B"), "ollama/qwen");
        assert_eq!(shown_engine("llama-openapi", "Falcon3-3B"), "llama-openapi");
        assert_eq!(heat_label("", "Falcon3-3B"), "llama-openapi");
        assert_eq!(
            heat_label("/m/a.gguf", "Falcon3-3B"),
            "llama-openapi/Falcon3-3B"
        );
    }

    /// The `llama_window_uses_num_ctx` test reads the window from YAML text.
    /// A stored window above 0 is kept. A missing key and a stored 0 both use 4096. A blank base uses the local tokenize URL.
    #[test]
    fn llama_window_uses_num_ctx() {
        assert_eq!(llama_window(8192), 8192);
        assert_eq!(llama_window(0), 4096);
        assert_eq!(llama_window(-1), 4096);
        let yaml = "\
spring:\n  \
  ai:\n    \
    llama-openapi:\n      \
      alias: Falcon3-3B\n      \
      base-url: http://127.0.0.1:3333/\n      \
      chat:\n        \
        options:\n          \
          num-ctx: 8192\n";
        let read = llama_yaml(yaml);
        assert_eq!(read.window, 8192);
        assert_eq!(read.alias, "Falcon3-3B");
        assert_eq!(llama_window(read.window), 8192);
        assert_eq!(tokenize_url(&read.base), "http://127.0.0.1:3333/tokenize");
        let missing = llama_yaml("spring:\n  ai:\n    llama-openapi:\n      alias: \"\"\n");
        assert_eq!(missing.window, 0);
        assert_eq!(missing.alias, "");
        assert_eq!(llama_window(missing.window), 4096);
        assert_eq!(tokenize_url(""), "http://127.0.0.1:3333/tokenize");
        assert_eq!(
            tokenize_url("http://127.0.0.1:3333/"),
            "http://127.0.0.1:3333/tokenize"
        );
        assert!(read.key.is_empty());
    }

    /// The `llama_tokenize_body` test writes the content document and reads a tokens array.
    /// An empty array is 0. A missing array is -1. The test does not open a socket.
    #[test]
    fn llama_tokenize_body() {
        assert_eq!(tokenize_body("hi"), "{\"content\":\"hi\"}");
        assert_eq!(tokenize_body(""), "{\"content\":\"\"}");
        assert_eq!(tokenize_body("a\"b"), "{\"content\":\"a\\\"b\"}");
        assert_eq!(token_count("{\"tokens\":[1,2,3]}"), 3);
        assert_eq!(token_count("{\"tokens\":[]}"), 0);
        assert_eq!(token_count("nope"), -1);
        assert_eq!(token_count(""), -1);
        assert_eq!(token_count("{\"content\":\"hi\"}"), -1);
    }

    /// The `llama_miss_keeps_the_held_count` test checks a miss and a same-payload keep.
    /// A stored count is kept. A miss with no stored count uses the fallback. The same payload does not ask for a post. A held success adds the base. The test does not open a socket.
    #[test]
    fn llama_miss_keeps_the_held_count() {
        assert_eq!(missed_count(None, 40), 40);
        assert_eq!(missed_count(Some(12), 40), 12);
        let memo = LlamaMemo {
            payload: "draft".to_string(),
            count: Some(12),
        };
        match llama_step(Some("draft"), &memo, 40) {
            LlamaBeat::Keep(n) => assert_eq!(n, 12),
            LlamaBeat::Post { .. } => panic!("same payload"),
        }
        let (painted, next) = finish_post(-1, "draft", None, 40, Some(30));
        assert_eq!(painted, 40);
        assert!(next.count.is_none());
        assert_eq!(next.payload, "draft");
        assert!(shell_prompt(true, "memory", "").is_none());
        let (added, stored) = finish_post(5, "hi", None, 40, Some(30));
        assert_eq!(added, 35);
        assert_eq!(stored.count, Some(35));
        match llama_step(Some("new"), &LlamaMemo::fresh(), 40) {
            LlamaBeat::Post { body } => assert_eq!(body, "{\"content\":\"new\"}"),
            LlamaBeat::Keep(_) => panic!("new payload"),
        }
    }

    /// The `llama_head_flag` test writes a llama execute with the existing encoder.
    /// A locked card writes `head: true`. An unlocked card omits `head`. Think, temperature, and the completion cap stay off when the card fields are blank. The test does not open a socket.
    #[test]
    fn llama_head_flag() {
        let cell = [0u8; 16];
        let snap = crate::model::ThinkSnap::off();
        let tune = crate::model::canvas_tune("llama-openapi/m", false, &snap, None, "");
        assert_eq!(tune.target, "llama-openapi/m");
        assert!(tune.think.is_empty());
        assert!(tune.temperature.is_none());
        assert!(tune.max_completion_tokens.is_none());
        let locked = [
            crate::amwp::SetLine {
                role: "user",
                text: "one",
                head: true,
            },
            crate::amwp::SetLine {
                role: "assistant",
                text: "two",
                head: true,
            },
        ];
        let bytes = crate::amwp::execute_tuned(&cell, &tune, &locked).expect("frame");
        let text = String::from_utf8_lossy(&bytes);
        assert!(text.contains("execution_target: llama-openapi/m"));
        assert!(text.contains("head: true"));
        assert!(!text.contains("think:"));
        assert!(!text.contains("temperature:"));
        assert!(!text.contains("max_completion_tokens:"));
        let open = [
            crate::amwp::SetLine {
                role: "user",
                text: "one",
                head: false,
            },
            crate::amwp::SetLine {
                role: "assistant",
                text: "two",
                head: false,
            },
        ];
        let plain = crate::amwp::execute_tuned(&cell, &tune, &open).expect("frame");
        let plain_text = String::from_utf8_lossy(&plain);
        assert!(plain_text.contains("execution_target: llama-openapi/m"));
        assert!(!plain_text.contains("head:"));
        assert!(!plain_text.contains("think:"));
    }
}
