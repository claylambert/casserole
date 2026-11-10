#!/usr/bin/env bash
set -euo pipefail
model='qwen2.5-coder:7b'
if [[ $# -ge 1 && -n "$1" ]]; then
  model="$1"
fi
if ! command -v ollama >/dev/null 2>&1; then
  curl -fsSL https://ollama.com/install.sh | sh
fi
echo "ollama pull $model"
ollama pull "$model"
echo 'install-ollama finished'
