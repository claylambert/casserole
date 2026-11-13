#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../.." && pwd)
# shellcheck disable=SC1091
. "$root/bin/rust-env.sh"
bin="$root/rust/aetheris/app/aetheris-client"
if [ ! -x "$bin" ]; then
    "$root/rust/aetheris/bin/install.sh"
fi
exec "$bin" "$@"
