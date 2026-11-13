#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/.." && pwd)
"$root/bin/build.sh"
jar=$(find "$root/target" -maxdepth 1 -type f -name '*.jar' ! -name '*original*' | head -n 1)
if [ -z "${jar:-}" ]; then
    echo "casserole jar was not produced" >&2
    exit 1
fi
mkdir -p "$root/app"
cp "$jar" "$root/app/casserole.jar"
printf 'casserole installed to app\n'
