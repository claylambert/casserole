#!/usr/bin/env bash
set -euo pipefail
model="${1:-qwen2.5-coder:7b}"
if ! command -v ollama >/dev/null 2>&1; then
    curl -fsSL https://ollama.com/install.sh | sh
fi
ollama pull "$model"
printf 'ollama model ready\n'
