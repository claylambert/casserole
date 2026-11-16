#!/usr/bin/env bash
# Release-build the Rust Aetheris Client and copy it into rust/aetheris/app.
#
# This file lives in rust/aetheris/bin. Three directories up is the
# repository root. The script sources bin/rust-env.sh, changes to the
# Rust workspace at rust/aetheris, and runs cargo build --release for the
# workspace. It does not pass -p. The workspace build is what produces
# the client binary.
#
# A non-zero exit prints aetheris-client build failed and exits 1.
# The if keeps set -e from exiting before that name is printed.
#
# After a successful build the script expects
# target/release/aetheris-client. A missing file prints
# aetheris-client is missing and exits 1. The file is copied to
# rust/aetheris/app/aetheris-client, overwriting an older copy.
# Success prints aetheris-client 1.0.0.
#
# The start script calls this script only when the app copy is missing
# or is not executable.
set -euo pipefail

# rust/aetheris/bin -> aetheris -> rust -> repository root.
root=$(cd "$(dirname "$0")/../../.." && pwd)

# shellcheck disable=SC1091
. "$root/bin/rust-env.sh"

# cargo reads the workspace Cargo.toml from the current directory.
cd "$root/rust/aetheris"

# The workspace release build. A non-zero exit is named.
if ! cargo build --release; then
    printf 'aetheris-client build failed\n' >&2
    exit 1
fi

src="$root/rust/aetheris/target/release/aetheris-client"
if [[ ! -e "$src" ]]; then
    printf 'aetheris-client is missing\n' >&2
    exit 1
fi
mkdir -p "$root/rust/aetheris/app"
cp "$src" "$root/rust/aetheris/app/aetheris-client"
printf 'aetheris-client 1.0.0\n'
