// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The module paints the composer token bar.
//!
//! The track is 5 pixels tall. The needle is 2 pixels wide and 9 pixels tall. An amber post sits at 70 percent of the track. A red post sits at 90 percent of the track. The fill colors are the green, amber, red, and head colors in this module. The needle rests on the true fill. The needle lunges 6 pixels when the token count changes. The frame that records that change draws the full lunge. A later frame eases one step when 16 milliseconds have passed. Crossing a post flashes the head color. This crate has no HTTP client. The needle uses the `used` and `budget` numbers the caller passes in.
//!
//! @aa-sdd PK-0104 docs/specs/java/org/aetheris/swing/swing.spec.md
//! @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
//! @aa-sdd PK-0104 docs/specs/java/org/aetheris/swing/swing.spec.md
//! @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md

use std::time::Duration;

const TRACK: egui::Color32 = egui::Color32::from_rgb(0x2a, 0x34, 0x40);
const GREEN: egui::Color32 = egui::Color32::from_rgb(0x3c, 0xb3, 0x71);
const AMBER: egui::Color32 = egui::Color32::from_rgb(0xf0, 0xa2, 0x02);
const RED: egui::Color32 = egui::Color32::from_rgb(0xe5, 0x39, 0x35);
const HEAD: egui::Color32 = egui::Color32::from_rgb(0xdc, 0xdc, 0xdc);

/// @aa-sdd PK-0088 docs/specs/java/org/aetheris/swing/swing.spec.md
/// @aa-sdd PK-4484 PK-4499 PK-4571 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/// The [`Beat`] struct is the animation state stored on the egui context.
/// `shown` is the token count last accepted. `lunge` is the extra pixels on the needle. `flash` is the remaining head-color frames. `flash_post` is the band that flashed. `seated` is false until the first `step` call. `eased` is the egui time of the last animation step.
#[derive(Clone)]
struct Beat {
    shown: i32,
    lunge: f32,
    flash: i32,
    flash_post: i32,
    seated: bool,
    eased: f64,
}

impl Default for Beat {
    /// The `default` method returns a [`Beat`] with every field at rest.
    /// `shown` is 0. `lunge` is 0. `flash` is 0. `flash_post` is 0. `seated` is false. `eased` is 0.
    fn default() -> Self {
        Self {
            shown: 0,
            lunge: 0.0,
            flash: 0,
            flash_post: 0,
            seated: false,
            eased: 0.0,
        }
    }
}

/// The `paint` function draws the token bar inside `rect`.
/// A width under 8 or a height under 9 returns without drawing. A negative `used` is treated as 0. A negative `budget` is treated as 0. A `budget` of 0 draws the posts and an empty fill.
///
/// The [`Beat`] value is stored on the egui context under the id `composer-heat-beat`. The frame that records a count change draws the full lunge. A later frame takes one decay step when 16 milliseconds have passed. A faster frame does not spend that step. A live lunge or a live flash asks for another paint after 16 milliseconds.
/// @aa-sdd PK-4484 PK-4485 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
pub fn paint(ui: &mut egui::Ui, rect: egui::Rect, used: i32, budget: i32) {
    if rect.width() < 8.0 || rect.height() < 9.0 {
        return;
    }
    let id = egui::Id::unique("composer-heat-beat");
    let mut beat = ui
        .ctx()
        .data(|data| data.get_temp::<Beat>(id))
        .unwrap_or_default();
    let tokens = if used < 0 { 0 } else { used };
    let window = if budget < 0 { 0 } else { budget };
    let now = ui.input(|input| input.time);
    let armed = step(&mut beat, tokens, window);
    // The arm frame draws the full lunge. A later frame takes one Java step
    // when 16 ms have passed. A faster frame does not spend that step.
    // @aa-sdd PK-0104 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    if armed {
        beat.eased = now;
    } else if now - beat.eased >= 0.016 {
        if beat.lunge > 0.4 {
            beat.lunge *= 0.72;
        } else {
            beat.lunge = 0.0;
        }
        if beat.flash > 0 {
            beat.flash -= 1;
        }
        beat.eased = now;
    }
    let live = beat.lunge > 0.4 || beat.flash > 0;
    ui.ctx().data_mut(|data| data.insert_temp(id, beat.clone()));
    if live {
        ui.ctx().request_repaint_after(Duration::from_millis(16));
    }
    draw(ui, rect, &beat, window);
}

/// The `step` function records a new token count on `beat`.
/// The first call sets `seated` and returns false. A later call with a different count sets `lunge` to 6 and returns true. A band change into band 1, 2, or 3 sets `flash` to 8 and stores that band in `flash_post`.
fn step(beat: &mut Beat, tokens: i32, budget: i32) -> bool {
    let prior = band(fill(beat.shown, budget));
    let moved = beat.seated && tokens != beat.shown;
    beat.shown = tokens;
    if !beat.seated {
        beat.seated = true;
        return false;
    }
    if moved {
        beat.lunge = 6.0;
    }
    let next = band(fill(beat.shown, budget));
    if next != prior && next > 0 {
        beat.flash = 8;
        beat.flash_post = next;
    }
    moved
}

/// The `fill` function returns the fraction of `budget` that `used` occupies.
/// A budget under 1 returns 0. A `used` of 0 or less returns 0. A `used` at or above `budget` returns 1. Any other pair returns `used` divided by `budget`.
fn fill(used: i32, budget: i32) -> f32 {
    if budget < 1 || used <= 0 {
        return 0.0;
    }
    if used >= budget {
        return 1.0;
    }
    used as f32 / budget as f32
}

/// The `band` function maps a fill fraction to a color band.
/// A fraction of 0 or less is band 0. A fraction of at least 0.9 is band 3. A fraction of at least 0.7 is band 2. Any other positive fraction is band 1.
fn band(amount: f32) -> i32 {
    if amount <= 0.0 {
        0
    } else if amount >= 0.9 {
        3
    } else if amount >= 0.7 {
        2
    } else {
        1
    }
}

/// The `band_ink` function returns the color for a band.
/// Band 3 or higher is red. Band 2 is amber. Band 1 is green. Band 0 is the track color.
fn band_ink(band: i32) -> egui::Color32 {
    if band >= 3 {
        RED
    } else if band == 2 {
        AMBER
    } else if band == 1 {
        GREEN
    } else {
        TRACK
    }
}

/// The `draw` function paints the track, the fill, the posts, the needle, and the token label.
/// The label is `est. tokens:` plus `shown`, in an 11-point proportional font. A live flash paints the fill in the head color. Otherwise the fill uses `band_ink`. The label width plus 8 pixels is kept clear of the track. The track width is at least 8 pixels.
fn draw(ui: &mut egui::Ui, rect: egui::Rect, beat: &Beat, budget: i32) {
    let painter = ui.painter();
    let amount = fill(beat.shown, budget);
    let word = format!("est. tokens: {}", beat.shown);
    let font = egui::FontId::proportional(11.0);
    let fill_ink = if beat.flash > 0 {
        HEAD
    } else {
        band_ink(band(amount))
    };
    let galley = painter.layout_no_wrap(word, font, fill_ink);
    let word_w = galley.size().x + 8.0;
    let width = (rect.width() - word_w).max(8.0);
    let track_top = rect.top() + (rect.height() - 5.0) * 0.5;
    let track = egui::Rect::from_min_size(egui::pos2(rect.left(), track_top), egui::vec2(width, 5.0));
    painter.rect_filled(track, 0.0, TRACK);
    let edge = rect.left() + amount * (width - 1.0);
    if edge > rect.left() && band(amount) > 0 {
        let filled = egui::Rect::from_min_max(track.min, egui::pos2(edge, track.bottom()));
        painter.rect_filled(filled, 0.0, fill_ink);
    }
    let lane = egui::Rect::from_min_size(rect.min, egui::vec2(width, rect.height()));
    post(painter, lane, 0.7, AMBER, beat, 2);
    post(painter, lane, 0.9, RED, beat, 3);
    let mut needle_x = edge + beat.lunge;
    if needle_x > rect.left() + width - 2.0 {
        needle_x = rect.left() + width - 2.0;
    }
    if needle_x < rect.left() {
        needle_x = rect.left();
    }
    let needle = egui::Rect::from_min_size(egui::pos2(needle_x, rect.top()), egui::vec2(2.0, 9.0));
    painter.rect_filled(needle, 0.0, HEAD);
    let text_x = rect.right() - galley.size().x - 4.0;
    let text_y = rect.center().y - galley.size().y * 0.5;
    painter.galley(egui::pos2(text_x.max(rect.left()), text_y), galley, fill_ink);
}

/// The `post` function paints one 1-by-9 mark at fraction `at` of `rect`.
/// A live flash whose `flash_post` equals `post_band` uses the head color. Any other call uses `ink`.
fn post(painter: &egui::Painter, rect: egui::Rect, at: f32, ink: egui::Color32, beat: &Beat, post_band: i32) {
    let x = rect.left() + at * (rect.width() - 1.0);
    let color = if beat.flash > 0 && beat.flash_post == post_band {
        HEAD
    } else {
        ink
    };
    let mark = egui::Rect::from_min_size(egui::pos2(x, rect.top()), egui::vec2(1.0, 9.0));
    painter.rect_filled(mark, 0.0, color);
}
