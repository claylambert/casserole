#!/usr/bin/env bash
# Start the Rust Aetheris Client.
#
# This file lives in rust/aetheris/app. Three directories up is the
# repository root. The script sources bin/rust-env.sh, so Rust 1.99.1
# stays in this process.
#
# The client binary is rust/aetheris/app/aetheris-client, copied there by
# the install script. When that file is missing or not executable,
# rust/aetheris/bin/install.sh builds the release binary and copies it.
# A binary that is already executable is not rebuilt.
#
# A file that is still not executable after the install prints
# aetheris-client is missing and exits 1. exec replaces this shell with
# the client, so the client's status is the status of the start. Arguments
# after the script name are passed through.
set -euo pipefail

# rust/aetheris/app -> aetheris -> rust -> repository root.
root=$(cd "$(dirname "$0")/../../.." && pwd)

# shellcheck disable=SC1091
. "$root/bin/rust-env.sh"

bin="$root/rust/aetheris/app/aetheris-client"

# -x is the check. A file that exists but cannot be executed is installed again.
if [[ ! -x "$bin" ]]; then
    "$root/rust/aetheris/bin/install.sh"
fi
if [[ ! -x "$bin" ]]; then
    printf 'aetheris-client is missing\n' >&2
    exit 1
fi
exec "$bin" "$@"
