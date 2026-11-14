#!/usr/bin/env bash
# Install the Ollama engine when it is missing, then pull a model when
# that model is not already listed.
#
# The default model is qwen2.5-coder:7b. The first argument, when it is
# present, is the model name for this run. A later run with another name
# pulls that name when it is absent.
#
# Each stage checks, then acts only when the check misses.
#
# The engine: when ollama is on PATH, the official install does not run.
# When the command is missing, the script runs https://ollama.com/install.sh.
# A command that is still missing exits and asks for a new shell.
# ollama --version then has to succeed. An engine that exits 0 stays.
#
# The registry key is the file id_ed25519 in the user's .ollama directory.
# This script does not write that file and does not print that file. The
# engine creates it. The script asks http://127.0.0.1:11434/api/version.
# When the engine answers and the key file is already present, this stage
# does nothing. When the API is down or the key file is absent, the script
# starts ollama serve so the engine can create the key and answer. It waits
# up to about 15 seconds. A key that is still absent prints
# ollama did not create its registry key and exits 1. An engine that is
# still silent prints ollama is not answering on port 11434 and exits 1.
#
# A serve this script started is stopped only when it is not the process
# listening on port 11434. An engine that was already listening stays.
# The temporary serve logs are deleted unread.
#
# The model: ollama list runs. awk prints the first field after the header.
# grep -Fxq requires the whole field to match. A listed model prints
# ollama model ready and exits before the pull. A pull that fails exits
# under set -e. Success prints ollama model ready.
set -euo pipefail

model="${1:-qwen2.5-coder:7b}"

# Install only when the command is absent. An engine already on PATH skips this.
if ! command -v ollama >/dev/null 2>&1; then
    curl -fsSL https://ollama.com/install.sh | sh
fi
if ! command -v ollama >/dev/null 2>&1; then
    printf 'ollama is not on PATH after install. Open a new shell and run this script again.\n' >&2
    exit 1
fi

# A non-zero version exits the script because of set -e. The text stays on the terminal.
ollama --version

# The engine owns this file. This script does not write it and does not print it.
key="${HOME}/.ollama/id_ed25519"
api_up() {
    curl -fsS --max-time 3 http://127.0.0.1:11434/api/version >/dev/null 2>&1
}

# Remember whether the API was already up, so a serve we started on top of
# a live engine can be told apart from the engine that owns the port.
api_before=0
if api_up; then
    api_before=1
fi
if [[ "$api_before" -eq 0 || ! -f "$key" ]]; then
    # Serve in the background. The logs exist so the engine's text has
    # somewhere to go. They are deleted unread.
    out_log="${TMPDIR:-/tmp}/casserole-ollama-serve-out.txt"
    err_log="${TMPDIR:-/tmp}/casserole-ollama-serve-err.txt"
    ollama serve >"$out_log" 2>"$err_log" &
    serve_pid=$!
    # Thirty half-second waits. Leave early when the key exists and the API
    # answers, or when the serve has exited and the key exists. A serve that
    # finds the port taken exits after the engine writes the key.
    for _ in 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30; do
        if [[ -f "$key" ]] && api_up; then
            break
        fi
        if ! kill -0 "$serve_pid" 2>/dev/null && [[ -f "$key" ]]; then
            break
        fi
        sleep 0.5
    done
    if [[ ! -f "$key" ]]; then
        printf 'ollama did not create its registry key\n' >&2
        exit 1
    fi
    if ! api_up; then
        printf 'ollama is not answering on port 11434\n' >&2
        exit 1
    fi
    # Stop the serve this script started only when the API was already up
    # and the listener on 11434 is some other pid. The listener stays.
    if [[ "$api_before" -eq 1 ]] && kill -0 "$serve_pid" 2>/dev/null; then
        listener=$(ss -ltnp 2>/dev/null | awk '/:11434/ {print}' || true)
        if [[ -n "$listener" && "$listener" != *"pid=${serve_pid},"* ]]; then
            kill "$serve_pid" 2>/dev/null || true
        fi
    fi
    rm -f "$out_log" "$err_log"
fi

# A listed model exits here. The pull does not run. A failed list prints
# the captured text. The assignment is in a conditional so set -e does not
# exit before that text is shown.
list_text=$(ollama list) || {
    printf 'ollama list failed\n%s\n' "$list_text" >&2
    exit 1
}
if printf '%s\n' "$list_text" | awk 'NR>1 {print $1}' | grep -Fxq -- "$model"; then
    printf 'ollama model ready\n'
    exit 0
fi

ollama pull "$model"
printf 'ollama model ready\n'
