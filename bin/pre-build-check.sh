#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/.." && pwd)
# shellcheck disable=SC1091
source "$root/bin/java-env.sh"
# shellcheck disable=SC1091
source "$root/bin/rust-env.sh"
echo "JDK  $JAVA_HOME"
echo "Maven  $(command -v mvn)"
echo "Cargo  $(cargo --version)"
echo "rustc  $(rustc --version)"
if command -v ollama >/dev/null 2>&1; then
  echo 'Ollama  present'
  ollama list
else
  echo 'Ollama  missing. Run casserole-server/bin/install-ollama.sh'
fi
echo 'pre-build-check finished'
