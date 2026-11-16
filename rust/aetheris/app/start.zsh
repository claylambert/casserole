#!/bin/zsh
# Start the Rust Aetheris Client on macOS.
#
# macOS is the operating system on a Mac. This file lives in
# rust/aetheris/app. Three directories up is the repository root. The
# script sources bin/rust-env.zsh, so Rust 1.99.1 stays in this process.
# The machine environment stays.
#
# The client binary is rust/aetheris/app/aetheris-client, copied there by
# the install script. The binary name is aetheris-client. When that file
# is missing or not executable, rust/aetheris/bin/install.zsh builds the
# release binary and copies it. A binary that is already executable is
# not rebuilt.
#
# A file that is still not executable after the install prints
# aetheris-client is missing and exits 1. exec replaces this shell with
# the client, so the client's status is the status of the start. Arguments
# after the script name are passed through.
set -eu
set -o pipefail

# rust/aetheris/app -> aetheris -> rust -> repository root.
root=$(cd "$(dirname "$0")/../../.." && pwd)

# shellcheck disable=SC1091
. "$root/bin/rust-env.zsh"

bin="$root/rust/aetheris/app/aetheris-client"

# -x is the check. A file that exists but cannot be executed is installed again.
if [[ ! -x "$bin" ]]; then
    zsh "$root/rust/aetheris/bin/install.zsh"
fi
if [[ ! -x "$bin" ]]; then
    printf 'aetheris-client is missing\n' >&2
    exit 1
fi
exec "$bin" "$@"
