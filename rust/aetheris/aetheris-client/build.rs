// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The build script stores two window icons in the Windows executable.
//!
//! The picture file is `aetheris.jpg` under `src/main/resources/icons`, three directories above this crate. The running window already uses that picture. The picture is the Eye of Horus. This step writes that picture into the binary so a taskbar pin keeps it. A target other than Windows returns before any file is written. A second Windows run leaves an icon file alone when the bytes already match.
//!
//! The small icon is `aetheris-taskbar.ico`. It holds the 16 and 32 pixel plates. The resource script names that file as icon 1, which is the icon the taskbar reads. The big icon is `aetheris-app.ico`. It holds the 48 and 256 pixel plates. The resource script names that file as icon 2. `rc.exe` from the Windows SDK compiles `aetheris.rc` into `aetheris.res`. Cargo then links `aetheris.res` into the binary.

use std::env;
use std::fs;
use std::path::PathBuf;
use std::process::Command;

const TASKBAR_EDGES: [u32; 2] = [16, 32];
const APP_EDGES: [u32; 2] = [48, 256];

/// The `main` function embeds the window icons when the build target is Windows.
/// The JPEG path is `../../../src/main/resources/icons/aetheris.jpg` from `CARGO_MANIFEST_DIR`. Cargo reruns this script when that file changes. Any other target operating system returns after that rerun line.
///
/// On Windows the function reads the JPEG. `icon_file` builds each icon. The small file is `aetheris-taskbar.ico`. The big file is `aetheris-app.ico`. The resource script is icon 1 for the taskbar file and icon 2 for the app file. The function runs `rc.exe` with `/nologo`, `/fo`, `aetheris.res`, and `aetheris.rc`. A start failure panics with the tool path. A failed status panics with the tool's standard error. The link argument is the `aetheris.res` path with each backslash changed to a slash.
fn main() {
    let jpeg = PathBuf::from(env::var("CARGO_MANIFEST_DIR").expect("manifest"))
        .join("../../../src/main/resources/icons/aetheris.jpg");
    println!("cargo:rerun-if-changed={}", jpeg.display());
    if env::var("CARGO_CFG_TARGET_OS").unwrap_or_default() != "windows" {
        return;
    }
    let out = PathBuf::from(env::var("OUT_DIR").expect("out"));
    let jpeg_bytes = fs::read(&jpeg).unwrap_or_else(|_| panic!("missing {}", jpeg.display()));
    let picture = image::load_from_memory(&jpeg_bytes).expect("eye");
    let taskbar_path = out.join("aetheris-taskbar.ico");
    let app_path = out.join("aetheris-app.ico");
    let rc_path = out.join("aetheris.rc");
    let res = out.join("aetheris.res");
    let rc_bytes = b"1 ICON \"aetheris-taskbar.ico\"\r\n2 ICON \"aetheris-app.ico\"\r\n";
    let taskbar_changed = write_same(&taskbar_path, &icon_file(&picture, &TASKBAR_EDGES));
    let app_changed = write_same(&app_path, &icon_file(&picture, &APP_EDGES));
    let rc_changed = write_same(&rc_path, rc_bytes);
    if taskbar_changed || app_changed || rc_changed || !res.is_file() {
        let rc = find_rc();
        let compiled = Command::new(&rc)
            .current_dir(&out)
            .args(["/nologo", "/fo", "aetheris.res", "aetheris.rc"])
            .output()
            .unwrap_or_else(|_| panic!("rc.exe did not start at {}", rc.display()));
        if !compiled.status.success() {
            panic!(
                "rc.exe failed\n{}",
                String::from_utf8_lossy(&compiled.stderr)
            );
        }
    }
    let link = res.display().to_string().replace('\\', "/");
    println!("cargo:rustc-link-arg-bins={link}");
}

/// The function `write_same` writes `bytes` to `path` when the file is absent or the bytes differ.
/// A matching file stays. The return is true when the file was written.
fn write_same(path: &std::path::Path, bytes: &[u8]) -> bool { // @aa-sdd PK-1434 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    if let Ok(existing) = fs::read(path) {
        if existing == bytes {
            return false;
        }
    }
    fs::write(path, bytes).unwrap_or_else(|_| panic!("write {}", path.display()));
    true
}

/// The `find_rc` function returns the newest `rc.exe` under the Windows SDK `bin` directory.
/// `kits_root` supplies the SDK root. Each child of `bin` is kept when `x64/rc.exe` is a file. The directory names are sorted as text. The last name is the one returned, joined as `bin/<name>/x64/rc.exe`.
fn find_rc() -> PathBuf {
    let root = kits_root().expect("Windows SDK KitsRoot10");
    let bin = root.join("bin");
    let mut versions = Vec::new();
    let entries = fs::read_dir(&bin).unwrap_or_else(|_| panic!("no SDK bin {}", bin.display()));
    for entry in entries {
        let Ok(entry) = entry else {
            continue;
        };
        let rc = entry.path().join("x64").join("rc.exe");
        if rc.is_file() {
            versions.push(entry.file_name());
        }
    }
    versions.sort();
    let name = versions.last().expect("rc.exe");
    bin.join(name).join("x64").join("rc.exe")
}

/// The `kits_root` function reads `KitsRoot10` from the Windows registry.
/// The first key is `HKLM\SOFTWARE\WOW6432Node\Microsoft\Windows Kits\Installed Roots`. The second key is `HKLM\SOFTWARE\Microsoft\Windows Kits\Installed Roots`. A failed query skips that key. The path is the text after `REG_SZ` on the `KitsRoot10` line. An empty path is skipped. Both keys missing returns `None`.
fn kits_root() -> Option<PathBuf> {
    for key in [
        "HKLM\\SOFTWARE\\WOW6432Node\\Microsoft\\Windows Kits\\Installed Roots",
        "HKLM\\SOFTWARE\\Microsoft\\Windows Kits\\Installed Roots",
    ] {
        let output = Command::new("reg")
            .args(["query", key, "/v", "KitsRoot10"])
            .output()
            .ok()?;
        if !output.status.success() {
            continue;
        }
        let text = String::from_utf8_lossy(&output.stdout);
        for line in text.lines() {
            let line = line.trim();
            let Some(rest) = line.strip_prefix("KitsRoot10") else {
                continue;
            };
            let Some(path) = rest.split("REG_SZ").nth(1) else {
                continue;
            };
            let path = path.trim();
            if !path.is_empty() {
                return Some(PathBuf::from(path));
            }
        }
    }
    None
}

/// The `icon_file` function builds a classic icon file from one picture.
/// `picture` is the Eye. `edges` is the plate sizes for this file. Each image is a 32-bit device-independent bitmap, stored bottom-up. The mask height is folded into the bitmap header so the shell reads the alpha channel.
///
/// Each size is a triangle-filtered resize of the picture. An edge of 256 is stored as width 0 and height 0 in the directory entry. Every entry says one color plane and 32 bits. The file header is reserved 0, type 1, and the image count.
fn icon_file(picture: &image::DynamicImage, edges: &[u32]) -> Vec<u8> {
    let mut images = Vec::new();
    for edge in edges {
        let sized = picture.resize_exact(*edge, *edge, image::imageops::FilterType::Triangle);
        let rgba = sized.to_rgba8();
        images.push((*edge, dib(&rgba, *edge)));
    }
    let count = images.len() as u16;
    let mut file = Vec::new();
    file.extend_from_slice(&0u16.to_le_bytes());
    file.extend_from_slice(&1u16.to_le_bytes());
    file.extend_from_slice(&count.to_le_bytes());
    let mut offset = 6u32 + u32::from(count) * 16;
    let mut blobs = Vec::new();
    for (edge, dib) in &images {
        let mark = if *edge >= 256 { 0 } else { *edge as u8 };
        file.push(mark);
        file.push(mark);
        file.push(0);
        file.push(0);
        file.extend_from_slice(&1u16.to_le_bytes());
        file.extend_from_slice(&32u16.to_le_bytes());
        file.extend_from_slice(&(dib.len() as u32).to_le_bytes());
        file.extend_from_slice(&offset.to_le_bytes());
        offset += dib.len() as u32;
        blobs.extend_from_slice(dib);
    }
    file.extend_from_slice(&blobs);
    file
}

/// The `dib` function builds one 32-bit device-independent bitmap for an icon image.
/// The header is 40 bytes. The stored height is twice `edge`, which counts the color bitmap and the mask. The pixels are written bottom-up as blue, green, red, then alpha.
///
/// The AND mask follows the pixels. A pixel whose alpha is 0 sets the matching mask bit. Any other pixel leaves that bit clear. Each mask row is padded to a multiple of 4 bytes.
fn dib(rgba: &image::RgbaImage, edge: u32) -> Vec<u8> {
    let side = edge as usize;
    let xor_size = side * side * 4;
    let mask_stride = (side + 31) / 32 * 4;
    let mut out = Vec::with_capacity(40 + xor_size + mask_stride * side);
    out.extend_from_slice(&40u32.to_le_bytes());
    out.extend_from_slice(&(edge as i32).to_le_bytes());
    out.extend_from_slice(&((edge as i32) * 2).to_le_bytes());
    out.extend_from_slice(&1u16.to_le_bytes());
    out.extend_from_slice(&32u16.to_le_bytes());
    out.extend_from_slice(&0u32.to_le_bytes());
    out.extend_from_slice(&(xor_size as u32).to_le_bytes());
    out.extend_from_slice(&0i32.to_le_bytes());
    out.extend_from_slice(&0i32.to_le_bytes());
    out.extend_from_slice(&0u32.to_le_bytes());
    out.extend_from_slice(&0u32.to_le_bytes());
    for y in (0..side).rev() {
        for x in 0..side {
            let pixel = rgba.get_pixel(x as u32, y as u32).0;
            out.push(pixel[2]);
            out.push(pixel[1]);
            out.push(pixel[0]);
            out.push(pixel[3]);
        }
    }
    for y in (0..side).rev() {
        let mut row = vec![0u8; mask_stride];
        for x in 0..side {
            if rgba.get_pixel(x as u32, y as u32).0[3] == 0 {
                row[x / 8] |= 0x80 >> (x % 8);
            }
        }
        out.extend_from_slice(&row);
    }
    out
}
