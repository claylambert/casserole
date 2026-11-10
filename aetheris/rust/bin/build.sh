#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../../.." && pwd)
# shellcheck disable=SC1091
source "$root/bin/rust-env.sh"
cd "$root/aetheris/rust"
cargo test
cargo build -p aetheris-client
echo 'aetheris-rust build finished'
