// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The `main` function starts the VT100 window for one Casserole session.
//!
//! The binary name is `aetheris-client`. The grid lives in the `aetheris-term` crate. The token ring reads `casserole.grok.context-windows` and `casserole.pool.context-window` from `application.yaml`. It drops every other key. This crate does not open a socket to Ollama. This crate does not call the Grok API. On startup the binary writes `.aetheris.yaml` in the home directory when that file is absent. An existing file is left as it stands.
//!
//! @aa-sdd PK-4435 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
//! @aa-sdd PK-4439 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
//! @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
//! @author clayl (2026-10-23)

#![forbid(unsafe_code)]

/// The module encodes and decodes AMWP frames for the VT100 client.
mod amwp;
/// The module is the client window.
mod chrome;
/// The module keeps an undo stack in memory for one editor.
mod edit;
/// The module paints the composer token bar.
mod heatbar;
/// The module reads and writes the SSH client settings file.
mod door;
/// The module stores Model Config for one cell and the same dialog for the console.
mod model;
/// The module builds the mood invitation and chooses which turn carries it.
mod mood;
/// The module runs one SSH session.
mod session;
/// The module stores one local vault for one SSH user.
mod vault;

/// The bytes are the home file written when `.aetheris.yaml` is absent.
/// `network.air-gap` is explicit `true`. The Java client writes these same bytes.
/// The text is not copied from a repository file or from an install folder.
const HOME_YAML: &str = "\
# Aetheris home configuration. The application writes this file when it is absent.\n\
aetheris:\n\
  ui:\n\
    title: Aetheris\n\
  vault:\n\
    mode: disk\n\
    path: ${user.home}/.aetheris/${user}/vault\n\
    pool-max: 4\n\
  # air-gap true refuses a cloud target. air-gap false allows that target.\n\
  network:\n\
    air-gap: true\n\
  amwp:\n\
    enabled: true\n\
  # mood-shape holds the shaper id. Empty means the Ollama model in use.\n\
  # burner-key allows a burner to hop to a cloud target. Missing or false denies.\n\
  notebook:\n\
    mood-shape: \"\"\n\
    burner-key: false\n\
";

/// The `ensure_home_yaml` function writes `.aetheris.yaml` in the home directory when that file is absent.
/// The home directory is `USERPROFILE` or else `HOME`. An existing file is left as it stands.
/// A file created by another start is left as it stands. A missing home directory returns without a write.
/// A write error prints `Unable to write` plus the path.
fn ensure_home_yaml() {
    let home = match std::env::var_os("USERPROFILE").or_else(|| std::env::var_os("HOME")) {
        Some(home) => home,
        None => return,
    };
    let path = std::path::PathBuf::from(home).join(".aetheris.yaml");
    if path.is_file() {
        return;
    }
    let mut file = match std::fs::OpenOptions::new()
        .write(true)
        .create_new(true)
        .open(&path)
    {
        Ok(file) => file,
        Err(err) if err.kind() == std::io::ErrorKind::AlreadyExists => return,
        Err(err) => {
            eprintln!("Unable to write {}: {err}", path.display());
            return;
        }
    };
    if let Err(err) = std::io::Write::write_all(&mut file, HOME_YAML.as_bytes()) {
        eprintln!("Unable to write {}: {err}", path.display());
    }
}

/// The `main` function writes the home file when it is absent, then opens the window by calling `chrome::run`.
fn main() {
    ensure_home_yaml();
    chrome::run();
}
