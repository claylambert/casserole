#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/.." && pwd)
cd "$root"
# shellcheck disable=SC1091
. "$root/bin/java-env.sh"
printf 'casserole-server 1.0.0\n'
if [ ! -f "$root/app/casserole.jar" ]; then
    "$root/bin/install.sh"
fi
if ! command -v ollama >/dev/null 2>&1 || ! ollama list | grep -q 'qwen2.5-coder:7b'; then
    "$root/bin/install-ollama.sh"
fi
exec java -jar "$root/app/casserole.jar"
