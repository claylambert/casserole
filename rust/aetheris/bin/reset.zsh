#!/bin/zsh
# Remove the Rust Aetheris Client build on macOS so the next build
# starts clean.
#
# macOS is the operating system on a Mac. This file lives in
# rust/aetheris/bin. Three directories up is the repository root. The
# script removes the installed binary from rust/aetheris/app, then
# removes the compiled workspace under rust/aetheris/target. That
# directory holds the debug binary, the release binary, and the
# compiled dependencies. The same removal covers
# rust/aetheris/aetheris-client/target and
# rust/aetheris/aetheris-term/target when those directories exist.
# The binary name is aetheris-client.
#
# When the Rust toolchain script can select Cargo, the script also
# runs cargo clean in the workspace. cargo clean is Cargo's own
# removal of that compiled tree. A missing Cargo still leaves the
# directories removed. The Rust compiler, rustup, and the downloaded
# crate cache on the machine stay. Cargo.lock stays. The next install
# compiles the client again.
#
# Success prints aetheris-client reset finished.
set -eu
set -o pipefail

# rust/aetheris/bin -> aetheris -> rust -> repository root.
root=$(cd "$(dirname "$0")/../../.." && pwd)
ws="$root/rust/aetheris"

# A missing file is already reset.
rm -f "$ws/app/aetheris-client" "$ws/app/aetheris-client.exe"

# Cargo's removal when the pinned toolchain is present.
set +e
# shellcheck disable=SC1091
. "$root/bin/rust-env.zsh"
env_code=$?
set -e
if [ "$env_code" -eq 0 ]; then
    if ! ( cd "$ws" && cargo clean ); then
        printf 'cargo clean was not run\n'
    fi
else
    printf 'cargo clean was not run\n'
fi

# The directories cargo clean removes, and any copy left beside a crate.
rm -rf "$ws/target" "$ws/aetheris-client/target" "$ws/aetheris-term/target"
printf 'aetheris-client reset finished\n'
