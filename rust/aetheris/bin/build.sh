#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../.." && pwd)
# shellcheck disable=SC1091
. "$root/bin/rust-env.sh"
cd "$root/rust/aetheris"
cargo build
printf 'aetheris-client build finished\n'
