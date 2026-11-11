#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../.." && pwd)
# shellcheck disable=SC1091
source "$root/bin/java-env.sh"
echo 'casserole-server 0.0.1-SNAPSHOT'
jar="$root/casserole-server/app/casserole-server.jar"
if [[ ! -f "$jar" ]]; then
  "$root/casserole-server/bin/install.sh"
fi
if [[ ! -f "$jar" ]]; then
  echo 'casserole-server.jar is missing.' >&2
  exit 1
fi
cd "$root"
exec java -jar "$jar"
