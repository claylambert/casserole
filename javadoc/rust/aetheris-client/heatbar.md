# Module heatbar

[Rust tree](../index.md) · **aetheris-client**

**Source** `aetheris/rust/aetheris-client/src/heatbar.rs`

The module paints the composer token bar.

The track is 5 pixels tall. The needle is 2 pixels wide and 9 pixels tall. An amber post sits at 70 percent of the track. A red post sits at 90 percent of the track. The fill colors are the green, amber, red, and head colors in this module. The needle rests on the true fill. The needle lunges 6 pixels when the token count changes. The frame that records that change draws the full lunge. A later frame eases one step when 16 milliseconds have passed. Crossing a post flashes the head color. This crate has no HTTP client. The needle uses the `used` and `budget` numbers the caller passes in.

| | |
| --- | --- |
| **Specification** | L40 aa-sdd-source-prose.md#RustHeatbar FINESSE:f-149 |
| **Specification** | L40 aa-sdd-source-prose.md#RustHeatbar FINESSE:f-167 |

## Type Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| struct | [`Beat`](heatbar/struct.Beat.md) | The [`Beat`] struct is the animation state stored on the egui context. |

## Constant Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| const | [`TRACK`](#constant-track) |  |
| const | [`GREEN`](#constant-green) |  |
| const | [`AMBER`](#constant-amber) |  |
| const | [`RED`](#constant-red) |  |
| const | [`HEAD`](#constant-head) |  |

## Function Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`paint`](#function-paint) | The `paint` function draws the token bar inside `rect`. |
| fn | [`step`](#function-step) | The `step` function records a new token count on `beat`. |
| fn | [`fill`](#function-fill) | The `fill` function returns the fraction of `budget` that `used` occupies. |
| fn | [`band`](#function-band) | The `band` function maps a fill fraction to a color band. |
| fn | [`band_ink`](#function-band_ink) | The `band_ink` function returns the color for a band. |
| fn | [`draw`](#function-draw) | The `draw` function paints the track, the fill, the posts, the needle, and the token label. |
| fn | [`post`](#function-post) | The `post` function paints one 1-by-9 mark at fraction `at` of `rect`. |

## Constant Detail

### TRACK

<a id="constant-track"></a>

```rust
const TRACK: egui::Color32 = egui::Color32::from_rgb(0x2a, 0x34, 0x40);
```

### GREEN

<a id="constant-green"></a>

```rust
const GREEN: egui::Color32 = egui::Color32::from_rgb(0x3c, 0xb3, 0x71);
```

### AMBER

<a id="constant-amber"></a>

```rust
const AMBER: egui::Color32 = egui::Color32::from_rgb(0xf0, 0xa2, 0x02);
```

### RED

<a id="constant-red"></a>

```rust
const RED: egui::Color32 = egui::Color32::from_rgb(0xe5, 0x39, 0x35);
```

### HEAD

<a id="constant-head"></a>

```rust
const HEAD: egui::Color32 = egui::Color32::from_rgb(0xdc, 0xdc, 0xdc);
```

## Function Detail

### paint

<a id="function-paint"></a>

```rust
pub fn paint(ui: &mut egui::Ui, rect: egui::Rect, used: i32, budget: i32)
```

The `paint` function draws the token bar inside `rect`.
A width under 8 or a height under 9 returns without drawing. A negative `used` is treated as 0. A negative `budget` is treated as 0. A `budget` of 0 draws the posts and an empty fill.

The [`Beat`](heatbar/struct.Beat.md) value is stored on the egui context under the id `composer-heat-beat`. The frame that records a count change draws the full lunge. A later frame takes one decay step when 16 milliseconds have passed. A faster frame does not spend that step. A live lunge or a live flash asks for another paint after 16 milliseconds.

### step

<a id="function-step"></a>

```rust
fn step(beat: &mut Beat, tokens: i32, budget: i32) -> bool
```

The `step` function records a new token count on `beat`.
The first call sets `seated` and returns false. A later call with a different count sets `lunge` to 6 and returns true. A band change into band 1, 2, or 3 sets `flash` to 8 and stores that band in `flash_post`.

### fill

<a id="function-fill"></a>

```rust
fn fill(used: i32, budget: i32) -> f32
```

The `fill` function returns the fraction of `budget` that `used` occupies.
A budget under 1 returns 0. A `used` of 0 or less returns 0. A `used` at or above `budget` returns 1. Any other pair returns `used` divided by `budget`.

### band

<a id="function-band"></a>

```rust
fn band(amount: f32) -> i32
```

The `band` function maps a fill fraction to a color band.
A fraction of 0 or less is band 0. A fraction of at least 0.9 is band 3. A fraction of at least 0.7 is band 2. Any other positive fraction is band 1.

### band_ink

<a id="function-band_ink"></a>

```rust
fn band_ink(band: i32) -> egui::Color32
```

The `band_ink` function returns the color for a band.
Band 3 or higher is red. Band 2 is amber. Band 1 is green. Band 0 is the track color.

### draw

<a id="function-draw"></a>

```rust
fn draw(ui: &mut egui::Ui, rect: egui::Rect, beat: &Beat, budget: i32)
```

The `draw` function paints the track, the fill, the posts, the needle, and the token label.
The label is `est. tokens:` plus `shown`, in an 11-point proportional font. A live flash paints the fill in the head color. Otherwise the fill uses `band_ink`. The label width plus 8 pixels is kept clear of the track. The track width is at least 8 pixels.

### post

<a id="function-post"></a>

```rust
fn post(painter: &egui::Painter, rect: egui::Rect, at: f32, ink: egui::Color32, beat: &Beat, post_band: i32)
```

The `post` function paints one 1-by-9 mark at fraction `at` of `rect`.
A live flash whose `flash_post` equals `post_band` uses the head color. Any other call uses `ink`.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
