#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/.." && pwd)
# shellcheck disable=SC1091
. "$root/bin/java-env.sh"
# shellcheck disable=SC1091
. "$root/bin/rust-env.sh"
java -version
mvn -version
rustup run 1.99.1 rustc --version
if command -v ollama >/dev/null 2>&1; then ollama --version; else echo "ollama is not installed"; fi
printf 'pre-build-check finished\n'
