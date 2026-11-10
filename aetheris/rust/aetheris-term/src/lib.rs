// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The crate exports a VT100 [`Grid`], a key encoder, and the [`Connector`] trait.
//!
//! The contract is the terminal library serial, sections 6 through 13, and the VT100 slice. `feed` borrows the grid for the call. A [`Cell`] owns its character, its width, and its color. No reference into the read buffer survives `feed`. The core does not start a runtime. The core does not call [`Connector`].
//!
//! @aa-sdd PK-4435 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
//! @aa-sdd PK-4439 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
//! @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
//! @author clayl (2026-10-23)

#![forbid(unsafe_code)]

/// The module defines the socket contract the owning program implements.
mod connector;
/// The module owns one terminal grid.
mod grid;
/// The module turns a classified key into the bytes a terminal writes.
mod keys;

pub use connector::Connector;
pub use grid::{Cell, Color, Grid, DEFAULT_COLS, DEFAULT_ROWS, SCROLLBACK_CAP};
pub use keys::{bracket_paste, encode, Key, PASTE_CAP};
