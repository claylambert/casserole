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
//! @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
//! @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
//! @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
//! @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
//! @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
//! @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
//! @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
//! @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
//! @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
//! @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
//! @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
//! @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
//! @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
//! @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
//! @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
//! @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
//! @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
//! @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
//! @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
//! @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
//! @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
//! @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
//! @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
//! @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
//! @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
//! @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
//! @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
//! @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
//! @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
//! @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
//! @aa-sdd PK-4521 PK-4593 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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

/// @aa-sdd PK-4522 PK-4594 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
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
