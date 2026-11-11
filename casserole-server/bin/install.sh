#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../.." && pwd)
"$root/casserole-server/bin/build.sh"
jar=$(find "$root/casserole-server/target" -maxdepth 1 -name 'casserole-server-*.jar' ! -name '*original*' | head -n 1)
mkdir -p "$root/casserole-server/app"
cp "$jar" "$root/casserole-server/app/casserole-server.jar"
echo 'casserole-server installed to casserole-server/app'
