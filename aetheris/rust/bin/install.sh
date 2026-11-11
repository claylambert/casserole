#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../../.." && pwd)
# shellcheck disable=SC1091
source "$root/bin/rust-env.sh"
cd "$root/aetheris/rust"
cargo build --release -p aetheris-client
mkdir -p "$root/aetheris/rust/app"
cp "$root/aetheris/rust/target/release/aetheris-client" "$root/aetheris/rust/app/aetheris-client"
echo 'aetheris-client installed to aetheris/rust/app'
