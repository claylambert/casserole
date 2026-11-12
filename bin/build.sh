#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/.." && pwd)
cd "$root"
# shellcheck disable=SC1091
. "$root/bin/java-env.sh"
if ! mvn package; then
    echo "casserole build failed" >&2
    exit 1
fi
printf 'casserole build finished\n'
