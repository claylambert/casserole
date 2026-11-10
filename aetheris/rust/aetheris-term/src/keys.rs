// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The module turns a classified key into the bytes a terminal writes.
//!
//! The window maps the event. The library writes the bytes. The table is library serial section 9. DECCKM is application-cursor mode. DECCKM sends CSI while off and SS3 while on, for the arrow keys. The library does not time Esc.
//!
//! @aa-sdd PK-4435 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
//! @aa-sdd PK-4439 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
//! @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
//! @author clayl (2026-10-23)

/// @aa-sdd PK-0170 PK-0289 docs/specs/java/org/casserole/casserole.spec.md
/// @aa-sdd PK-4208 docs/specs/java/org/aetheris/swing/swing.spec.md
/// The [`Key`] enum is one key the window has already classified.
/// `encode` turns that value into the bytes the library writes. The library does not time `Esc`.
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub enum Key {
    Enter,
    Backspace,
    Left,
    Right,
    CtrlLeft,
    CtrlRight,
    Esc,
    Up,
    Down,
    Home,
    End,
    Delete,
    CtrlA,
    CtrlC,
    CtrlE,
    /// The `CtrlZ` variant is undo.
    /// The encoded byte is `0x1A`. The shell keeps that step in memory.
    CtrlZ,
    /// The `Redo` variant is Shift-Ctrl-Z.
    /// The encoded bytes are ESC, `[`, `1`, `;`, `6`, `Z`.
    Redo,
    Char(char),
}

/// `PASTE_CAP` is the paste body limit from the VT100 slice.
/// The limit is 65536 bytes of UTF-8. `bracket_paste` truncates a longer body to this length.
pub const PASTE_CAP: usize = 65536;

/// The `encode` function turns one [`Key`] into terminal bytes.
/// `decckm` is application-cursor mode. It changes only the four arrow keys.
///
/// `Enter` is `0x0D`. `Backspace` is `0x7F`. `Esc` is `0x1B`. `CtrlA` is `0x01`. `CtrlC` is `0x03`. `CtrlE` is `0x05`. `CtrlZ` is `0x1A`. `Redo` is ESC `[1;6Z`. `Delete` is CSI `3~`. `Home` is CSI `H`. `End` is CSI `F`. `CtrlLeft` is CSI `1;5D`. `CtrlRight` is CSI `1;5C`.
///
/// An arrow with `decckm` false is CSI plus `A`, `B`, `C`, or `D`. An arrow with `decckm` true is SS3, which is ESC `O` plus that same letter. `Char` is the UTF-8 bytes of that character.
pub fn encode(key: Key, decckm: bool) -> Vec<u8> {
    match key {
        Key::Enter => vec![0x0D],
        Key::Backspace => vec![0x7F],
        Key::Esc => vec![0x1B],
        Key::CtrlA => vec![0x01],
        Key::CtrlC => vec![0x03],
        Key::CtrlE => vec![0x05],
        Key::CtrlZ => vec![0x1A],
        Key::Redo => b"\x1b[1;6Z".to_vec(),
        Key::Delete => csi(b"3~"),
        Key::Home => csi(b"H"),
        Key::End => csi(b"F"),
        Key::CtrlLeft => csi(b"1;5D"),
        Key::CtrlRight => csi(b"1;5C"),
        Key::Up => arrow(b'A', decckm),
        Key::Down => arrow(b'B', decckm),
        Key::Right => arrow(b'C', decckm),
        Key::Left => arrow(b'D', decckm),
        Key::Char(ch) => {
            let mut buf = [0u8; 4];
            let text = ch.encode_utf8(&mut buf);
            text.as_bytes().to_vec()
        }
    }
}

/// The `bracket_paste` function turns paste text into terminal bytes.
/// Each `\r\n` becomes CR. Each remaining `\n` becomes CR. The body is capped at [`PASTE_CAP`] bytes of UTF-8. A cut that would split a UTF-8 continuation byte moves back to the character boundary.
///
/// `enabled` is private mode 2004. A false value returns the capped body alone. A true value wraps that body in CSI `200~` and CSI `201~`.
pub fn bracket_paste(text: &str, enabled: bool) -> Vec<u8> {
    let normalized = text.replace("\r\n", "\r").replace('\n', "\r");
    let mut bytes = normalized.into_bytes();
    if bytes.len() > PASTE_CAP {
        let mut end = PASTE_CAP;
        while end > 0 && (bytes[end] & 0xC0) == 0x80 {
            end -= 1;
        }
        bytes.truncate(end);
    }
    if !enabled {
        return bytes;
    }
    let mut out = csi(b"200~");
    out.extend_from_slice(&bytes);
    out.extend_from_slice(&csi(b"201~"));
    out
}

/// The `csi` function builds one CSI sequence.
/// The first byte is ESC. The second byte is `[`. `rest` follows those two bytes.
fn csi(rest: &[u8]) -> Vec<u8> {
    let mut out = Vec::with_capacity(2 + rest.len());
    out.push(0x1B);
    out.push(b'[');
    out.extend_from_slice(rest);
    out
}

/// The `arrow` function builds one arrow-key sequence.
/// `decckm` true uses ESC, `O`, and `letter`. `decckm` false uses ESC, `[`, and `letter`.
fn arrow(letter: u8, decckm: bool) -> Vec<u8> {
    if decckm {
        vec![0x1B, b'O', letter]
    } else {
        vec![0x1B, b'[', letter]
    }
}

#[cfg(test)]
/// These functions check the key encoder.
mod tests {
    use super::{bracket_paste, encode, Key, PASTE_CAP};

    #[test]
    /// The `the_host_table_bytes` test checks the encoded bytes for each [`Key`] with DECCKM off.
    /// The arrow keys use CSI. `Char` of `é` is that character's UTF-8 bytes.
    fn the_host_table_bytes() {
        assert_eq!(encode(Key::Enter, false), vec![0x0D]);
        assert_eq!(encode(Key::Backspace, false), vec![0x7F]);
        assert_eq!(encode(Key::Up, false), b"\x1b[A");
        assert_eq!(encode(Key::Down, false), b"\x1b[B");
        assert_eq!(encode(Key::Home, false), b"\x1b[H");
        assert_eq!(encode(Key::End, false), b"\x1b[F");
        assert_eq!(encode(Key::Delete, false), b"\x1b[3~");
        assert_eq!(encode(Key::CtrlA, false), vec![0x01]);
        assert_eq!(encode(Key::CtrlC, false), vec![0x03]);
        assert_eq!(encode(Key::CtrlE, false), vec![0x05]);
        assert_eq!(encode(Key::CtrlZ, false), vec![0x1A]);
        assert_eq!(encode(Key::Redo, false), b"\x1b[1;6Z");
        assert_eq!(encode(Key::Left, false), b"\x1b[D");
        assert_eq!(encode(Key::Right, false), b"\x1b[C");
        assert_eq!(encode(Key::CtrlLeft, false), b"\x1b[1;5D");
        assert_eq!(encode(Key::CtrlRight, false), b"\x1b[1;5C");
        assert_eq!(encode(Key::Esc, false), vec![0x1B]);
        assert_eq!(encode(Key::Char('é'), false), "é".as_bytes());
    }

    #[test]
    /// The `application_cursor_uses_ss3` test checks arrow keys with DECCKM on.
    /// Those arrows use SS3. `Home` stays CSI `H`.
    fn application_cursor_uses_ss3() {
        assert_eq!(encode(Key::Up, true), b"\x1bOA");
        assert_eq!(encode(Key::Down, true), b"\x1bOB");
        assert_eq!(encode(Key::Right, true), b"\x1bOC");
        assert_eq!(encode(Key::Left, true), b"\x1bOD");
        assert_eq!(encode(Key::Home, true), b"\x1b[H");
    }

    #[test]
    /// The `paste_brackets_and_caps` test checks newline folding, bracket wrapping, and the cap.
    /// A disabled paste turns a newline into CR and adds no brackets. An enabled paste wraps the body. A body past [`PASTE_CAP`] is cut to that length.
    fn paste_brackets_and_caps() {
        assert_eq!(bracket_paste("a\nb", false), b"a\rb");
        assert_eq!(bracket_paste("a\r\nb", true), b"\x1b[200~a\rb\x1b[201~");
        let big = "x".repeat(PASTE_CAP + 10);
        let raw = bracket_paste(&big, false);
        assert_eq!(raw.len(), PASTE_CAP);
    }
}
