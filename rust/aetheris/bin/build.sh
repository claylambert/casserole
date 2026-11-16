#!/usr/bin/env bash
# Debug-build the Rust Aetheris Client.
#
# This file lives in rust/aetheris/bin. Three directories up is the
# repository root. The script sources bin/rust-env.sh, changes to the
# Rust workspace at rust/aetheris, and runs cargo build. That is the
# debug build. The install script is the one that runs
# cargo build --release and copies the binary into rust/aetheris/app.
#
# A non-zero exit prints aetheris-client build failed and exits 1.
# Success prints aetheris-client build finished. The if keeps set -e from
# exiting before the named failure is printed.
set -euo pipefail

# rust/aetheris/bin -> aetheris -> rust -> repository root.
root=$(cd "$(dirname "$0")/../../.." && pwd)

# shellcheck disable=SC1091
. "$root/bin/rust-env.sh"

# cargo reads the workspace Cargo.toml from the current directory.
cd "$root/rust/aetheris"
if ! cargo build; then
    printf 'aetheris-client build failed\n' >&2
    exit 1
fi
printf 'aetheris-client build finished\n'
