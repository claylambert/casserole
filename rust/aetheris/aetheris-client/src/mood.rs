// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The module builds the mood invitation and chooses which turn carries it.
//!
//! This module does not paint. The egui window stays in the chrome module. Canvas, group, and pipe call `splice_turn` to put the invitation on one user turn. The shell frame stays `shell_mood` and the line. The shaping sentences stay [`OPENER`] and [`CLOSER`]. A missing pin is the text `head`. A pending define is a move that the next send consumes through `consume_pin`.
//!
//! @aa-sdd L40 aa-sdd-source-prose.md#RustMoodDesk
//! @aa-sdd PK-1169 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-200
//! @author clayl (2026-11-03)

/// [`OPENER`] is the sentence placed before the constructed mood in the older splice, and after `shape:` in the current invitation.
/// Canvas and the chrome window both use this line. The text asks whether to step into the atmosphere.
pub const OPENER: &str =
    "I'm opening a window with this atmosphere\u{2026} would you like to step into it with me?";

/// [`CLOSER`] is the sentence placed after the constructed mood.
/// Canvas and the chrome window both use this line. The text asks the reply to follow the mood.
pub const CLOSER: &str =
    "Shape your replies around this mood \u{2014} let it breathe through the words.";

/// [`LEGACY`] is the activate sentence a transcript stored before the current invitation.
/// A fresh splice does not write this sentence. The tests check that the new text does not contain it.
pub const LEGACY: &str =
    "Activate the mood above. Use that mood as the shape of your responses.";

/// The `normalize` function trims `line` and returns that text.
/// An empty result after trim returns `None`. Any other result returns `Some` with the trimmed text.
pub fn normalize(line: &str) -> Option<&str> {
    let trimmed = line.trim();
    if trimmed.is_empty() {
        None
    } else {
        Some(trimmed)
    }
}

/// The `invite_head` function builds the invitation that sits at the start of a turn.
/// The text is `mood:`, a space, `line`, a newline, `shape:`, a newline, [`OPENER`], a newline, [`CLOSER`], and a newline.
pub fn invite_head(line: &str) -> String {
    format!("mood: {line}\nshape:\n{OPENER}\n{CLOSER}\n")
}

/// The `splice_turn` function puts one invitation at the start of `turn`.
/// The function strips a stored invitation first, including the older order of [`OPENER`], the line, and [`CLOSER`]. A missing line returns that stripped turn. A line that trims to empty returns that stripped turn. Any other line prepends `invite_head` of the trimmed line.
pub fn splice_turn(turn: &str, line: Option<&str>) -> String {
    let raw = unsplice_turn(turn);
    let Some(next) = line.and_then(normalize) else {
        return raw;
    };
    let mut out = invite_head(next);
    out.push_str(&raw);
    out
}

/// The `unsplice_turn` function returns `turn` with one spliced mood removed.
/// No mood at the start returns a copy of `turn`. A current invitation prefix is removed. Otherwise the older prefix of [`OPENER`], the line, and [`CLOSER`] is removed. A recognized line whose prefix does not match returns the original `turn`.
pub fn unsplice_turn(turn: &str) -> String {
    let Some(line) = line_at_turn_head(turn) else {
        return turn.to_string();
    };
    let fresh = invite_head(&line);
    if let Some(rest) = turn.strip_prefix(&fresh) {
        return rest.to_string();
    }
    let older = format!("{OPENER}\n{line}\n{CLOSER}\n");
    if let Some(rest) = turn.strip_prefix(&older) {
        return rest.to_string();
    }
    turn.to_string()
}

/// The `line_at_turn_head` function returns the mood line at the start of `turn`.
/// The current `mood:` form is tried first. The older form that starts with [`OPENER`] is tried second. Neither form returns `None`.
pub fn line_at_turn_head(turn: &str) -> Option<String> {
    invited_line(turn).or_else(|| older_splice_line(turn))
}

/// The `invited_line` function reads the current invitation at the start of `turn`.
/// The text must start with `mood: `. The mood is the rest of that first line, after trim. The following text must be `shape:`, [`OPENER`], and [`CLOSER`], alone or with more text after a newline. Any other shape returns `None`.
fn invited_line(turn: &str) -> Option<String> {
    let rest = turn.strip_prefix("mood: ")?;
    let (line, rest) = rest.split_once('\n')?;
    let line = normalize(line)?;
    let shape = format!("shape:\n{OPENER}\n{CLOSER}");
    if rest == shape || rest.starts_with(&(shape + "\n")) {
        Some(line.to_string())
    } else {
        None
    }
}

/// The `older_splice_line` function reads the older invitation at the start of `turn`.
/// The text must start with [`OPENER`] and a newline. The next line is the mood. The text after that line must be [`CLOSER`], alone or with more text after a newline. Any other shape returns `None`.
fn older_splice_line(turn: &str) -> Option<String> {
    let rest = turn.strip_prefix(&format!("{OPENER}\n"))?;
    let (line, rest) = rest.split_once('\n')?;
    let line = normalize(line)?;
    if rest == CLOSER || rest.starts_with(&format!("{CLOSER}\n")) {
        Some(line.to_string())
    } else {
        None
    }
}

/// The `debug_lines` function returns the physical lines of one invitation.
/// A line that trims to empty returns an empty vector. Any other line splices that mood onto an empty turn and keeps each non-empty line. The trailing newline is not a line.
pub fn debug_lines(line: &str) -> Vec<String> {
    let Some(line) = normalize(line) else {
        return Vec::new();
    };
    splice_turn("", Some(line))
        .lines()
        .filter(|line| !line.is_empty())
        .map(str::to_string)
        .collect()
}

/// @aa-sdd PK-1169 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md FINESSE:f-200
/// The [`SendTurn`] struct is one turn the send may carry.
/// `place` walks these values from oldest to newest and returns the first user turn at or after the pin.
pub struct SendTurn<'a> {
    /// The `id` field is the cell id for this turn.
    pub id: &'a str,
    /// The `user` field is true when this turn contributes user text.
    pub user: bool,
    /// The `at` field is the index on the canvas, with the oldest turn at 0.
    pub at: usize,
}

/// The `pin_index` function returns the index of `pin` in `ordered`.
/// An empty pin returns `None`. The pin text `head` returns `None`. A pin that `ordered` does not hold returns `None`. Any other pin returns `Some` with that index.
pub fn pin_index(ordered: &[&str], pin: &str) -> Option<usize> {
    if pin.is_empty() || pin == "head" {
        return None;
    }
    ordered.iter().position(|id| *id == pin)
}

/// The `place` function returns the first user turn at or after the pin.
/// A missing pin, which is `None`, is the first user turn in `send`. A turn whose `user` flag is false is skipped. A turn whose `at` is before `pin_at` is skipped. No matching turn returns `None`.
pub fn place<'a>(send: &[SendTurn<'a>], pin_at: Option<usize>) -> Option<&'a str> {
    for turn in send {
        if !turn.user {
            continue;
        }
        if pin_at.is_none_or(|at| turn.at >= at) {
            return Some(turn.id);
        }
    }
    None
}

/// The `consume_pin` function returns the pin a pending define stores.
/// More than one prior user turn returns `target_id`. One prior user turn returns `head`. Zero prior user turns returns `head`. This function does not clear a flag. The caller drops the pending mark after it reads this pin.
pub fn consume_pin(prior_users: usize, target_id: &str) -> String {
    if prior_users > 1 {
        target_id.to_string()
    } else {
        "head".to_string()
    }
}

/// The `walked_pin` function returns the pin after a delete that removed it.
/// An empty pin stays. The pin text `head` stays. A pin that is not in `removed` stays. The cell before the first removed id keeps the pin. No cell before that hole returns `head`.
pub fn walked_pin(ordered: &[&str], removed: &[&str], pin: &str) -> String {
    if pin.is_empty() || pin == "head" || !removed.iter().any(|id| *id == pin) {
        return pin.to_string();
    }
    let Some(first) = ordered.iter().position(|id| removed.iter().any(|gone| gone == id)) else {
        return pin.to_string();
    };
    let mut last = None;
    for (index, id) in ordered.iter().enumerate() {
        if index >= first {
            break;
        }
        if !removed.iter().any(|gone| gone == id) {
            last = Some((*id).to_string());
        }
    }
    last.unwrap_or_else(|| "head".to_string())
}

#[cfg(test)]
/// These functions check the mood invitation and which turn carries it.
mod tests {
    use super::*;

    #[test]
    /// The `the_invitation_splices_and_strips` test checks a fresh splice, an older splice, and an empty line.
    /// A fresh splice starts with `mood:` and ends with the turn text. An older splice is replaced. `None` and a blank line leave the turn. `debug_lines` returns four lines.
    fn the_invitation_splices_and_strips() {
        let line = "sunny-day lantern light";
        let spliced = splice_turn("one", Some(line));
        assert!(spliced.starts_with(&format!("mood: {line}\nshape:\n{OPENER}\n{CLOSER}\n")));
        assert!(spliced.ends_with("one"));
        assert!(!spliced.contains(LEGACY));
        assert_eq!(unsplice_turn(&spliced), "one");
        assert_eq!(line_at_turn_head(&spliced).as_deref(), Some(line));
        let older = format!("{OPENER}\n{line}\n{CLOSER}\nold");
        let fresh = splice_turn(&older, Some("spare, dry"));
        assert!(fresh.starts_with("mood: spare, dry\nshape:\n"));
        assert!(fresh.ends_with("old"));
        assert!(!fresh.contains(&format!("{OPENER}\n{line}\n")));
        assert_eq!(splice_turn("one", None), "one");
        assert_eq!(splice_turn("one", Some("   ")), "one");
        let lines = debug_lines(line);
        assert_eq!(lines.len(), 4);
        assert_eq!(lines[0], format!("mood: {line}"));
        assert_eq!(lines[1], "shape:");
        assert_eq!(lines[2], OPENER);
        assert_eq!(lines[3], CLOSER);
    }

    #[test]
    /// The `the_pin_places_the_user_turn` test checks pin placement, `consume_pin`, and `walked_pin`.
    /// A head pin and a missing pin select the first user turn. Two prior user turns keep the target id. A removed pin moves to the cell before the hole.
    fn the_pin_places_the_user_turn() {
        let ordered = ["a", "b", "c"];
        let send = [
            SendTurn { id: "a", user: true, at: 0 },
            SendTurn { id: "b", user: false, at: 1 },
            SendTurn { id: "c", user: true, at: 2 },
        ];
        assert_eq!(place(&send, pin_index(&ordered, "head")), Some("a"));
        assert_eq!(place(&send, pin_index(&ordered, "c")), Some("c"));
        assert_eq!(place(&send, pin_index(&ordered, "missing")), Some("a"));
        assert_eq!(consume_pin(0, "c"), "head");
        assert_eq!(consume_pin(1, "c"), "head");
        assert_eq!(consume_pin(2, "c"), "c");
        assert_eq!(walked_pin(&ordered, &["b"], "b"), "a");
        assert_eq!(walked_pin(&ordered, &["a"], "a"), "head");
        assert_eq!(walked_pin(&ordered, &["b"], "c"), "c");
    }
}
