#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../../.." && pwd)
# shellcheck disable=SC1091
. "$root/bin/rust-env.sh"
cd "$root/rust/aetheris"
cargo build --release
mkdir -p "$root/rust/aetheris/app"
cp "$root/rust/aetheris/target/release/aetheris-client" "$root/rust/aetheris/app/aetheris-client"
printf 'aetheris-client 1.0.0\n'
