#!/bin/zsh
# Install the Ollama engine on macOS when it is missing, then pull a model
# when that model is not already listed.
#
# macOS is the operating system on a Mac. The default model is
# qwen2.5-coder:7b. The first argument, when it is present, is the model
# name for this run. A later run with another name pulls that name when
# it is absent.
#
# Each stage checks, then acts only when the check misses.
#
# The engine: an ollama already on PATH is kept. When the command is
# missing, the script looks for a binary it can name:
#
#     /opt/homebrew/bin/ollama
#     /usr/local/bin/ollama
#     /Applications/Ollama.app/Contents/Resources/ollama
#     ~/Applications/Ollama.app/Contents/Resources/ollama
#     ~/.local/share/casserole/Ollama.app/Contents/Resources/ollama
#
# Homebrew is the package manager a Mac usually has. When those paths
# miss, brew --prefix ollama names an installed formula, and
# brew --prefix --cask ollama names an installed cask. The script does
# not run brew install. When every one of those is missing, the script downloads
# https://ollama.com/download/Ollama-darwin.zip into
# ~/.local/share/casserole. That zip is the macOS app. This script does
# not run the Linux installer. It does not copy the app into /Applications.
# It does not ask for an administrator password. It does not write a shell
# startup file. A binary already unpacked under the casserole directory is
# not downloaded again.
#
# The download runs in this process only when this file is executed. A
# PATH change here does not replace the caller's PATH, because the caller
# runs this file in a child shell. This script does not assign JAVA_HOME.
# start-casserole.zsh calls use_home again after this script returns.
# The directory that holds the chosen ollama binary is prepended only when
# that directory is not already on PATH. A second run does not grow PATH.
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
# listening on port 11434. macOS has no ss for that check. lsof prints the
# listening pid, one per line. The serve pid has to match a whole line.
# An engine that was already listening stays. The temporary serve logs are
# deleted unread.
#
# The model: ollama list runs. awk prints the first field after the header.
# grep -Fxq requires the whole field to match. A listed model prints
# ollama model ready and exits before the pull. A pull that fails exits
# under set -e. Success prints ollama model ready.
set -eu
set -o pipefail

model="${1:-qwen2.5-coder:7b}"

# prepend_dir puts one directory at the front of this process PATH when
# that directory exists and is not already an entry. A second call does
# not grow PATH.
prepend_dir() {
    local dir="${1%/}"
    local entry
    [[ -n "$dir" && -d "$dir" ]] || return 0
    for entry in "${(@s/:/)PATH}"; do
        [[ "${entry%/}" == "$dir" ]] && return 0
    done
    export PATH="$dir:$PATH"
    hash -r
}

# brew_exe prints the Homebrew command. PATH is first. The two prefixes
# a Mac install uses are next. A miss returns 1.
brew_exe() {
    local candidate
    if command -v brew >/dev/null 2>&1; then
        command -v brew
        return 0
    fi
    for candidate in /opt/homebrew/bin/brew /usr/local/bin/brew; do
        if [[ -x "$candidate" ]]; then
            printf '%s\n' "$candidate"
            return 0
        fi
    done
    return 1
}

# brew_prefix prints the folder of one installed formula. A formula that
# is not installed returns 1.
brew_prefix() {
    local brew prefix
    brew=$(brew_exe) || return 1
    prefix=$("$brew" --prefix "$1" 2>/dev/null || true)
    [[ -n "$prefix" && -d "$prefix" ]] || return 1
    printf '%s\n' "$prefix"
}

# brew_cask_prefix prints the folder of one installed cask. A cask that
# is not installed returns 1.
brew_cask_prefix() {
    local brew prefix
    brew=$(brew_exe) || return 1
    prefix=$("$brew" --prefix --cask "$1" 2>/dev/null || true)
    [[ -n "$prefix" && -d "$prefix" ]] || return 1
    printf '%s\n' "$prefix"
}

# Find an engine that is already on the machine. The casserole copy counts.
# A hit skips the download.
ollama_bin=""
if command -v ollama >/dev/null 2>&1; then
    ollama_bin=$(command -v ollama)
else
    for candidate in \
        /opt/homebrew/bin/ollama \
        /usr/local/bin/ollama \
        /Applications/Ollama.app/Contents/Resources/ollama \
        "${HOME}/Applications/Ollama.app/Contents/Resources/ollama" \
        "${HOME}/.local/share/casserole/Ollama.app/Contents/Resources/ollama"
    do
        if [[ -x "$candidate" ]]; then
            ollama_bin=$candidate
            break
        fi
    done
fi

# An installed Homebrew formula or cask is kept. The formula bin is the
# first hit. A cask folder is searched for the app binary.
if [[ -z "$ollama_bin" ]]; then
    brew_ollama=$(brew_prefix ollama || true)
    if [[ -n "$brew_ollama" && -x "$brew_ollama/bin/ollama" ]]; then
        ollama_bin="$brew_ollama/bin/ollama"
    fi
fi
if [[ -z "$ollama_bin" ]]; then
    brew_ollama=$(brew_cask_prefix ollama || true)
    if [[ -n "$brew_ollama" ]]; then
        ollama_bin=$(find "$brew_ollama" -path '*/Contents/Resources/ollama' -type f 2>/dev/null | head -n 1 || true)
    fi
fi

# Download only when every earlier location missed. The zip lands under
# the casserole directory. find uses -path because the macOS find has no
# -printf. A binary that is already there is not downloaded again.
if [[ -z "$ollama_bin" ]]; then
    dest="${HOME}/.local/share/casserole"
    mkdir -p "$dest"
    ollama_bin=$(find "$dest" -path '*/Contents/Resources/ollama' -type f 2>/dev/null | head -n 1 || true)
    if [[ -z "$ollama_bin" ]]; then
        tmp=$(mktemp)
        curl -fsSL "https://ollama.com/download/Ollama-darwin.zip" -o "$tmp"
        unzip -q -o "$tmp" -d "$dest"
        rm -f "$tmp"
        ollama_bin=$(find "$dest" -path '*/Contents/Resources/ollama' -type f 2>/dev/null | head -n 1 || true)
    fi
    if [[ -z "$ollama_bin" ]]; then
        printf 'ollama was not unpacked\n' >&2
        exit 1
    fi
    if [[ ! -x "$ollama_bin" ]]; then
        chmod +x "$ollama_bin"
    fi
fi

# The binary's directory has to be on this process PATH before the later
# commands. A directory that is already there is left where it is.
prepend_dir "$(dirname "$ollama_bin")"
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
    # and the listener on 11434 is some other pid. lsof prints one pid per
    # line. grep -Fxq matches the serve pid as a whole line. The listener stays.
    if [[ "$api_before" -eq 1 ]] && kill -0 "$serve_pid" 2>/dev/null; then
        listener=$(lsof -nP -iTCP:11434 -sTCP:LISTEN -t 2>/dev/null || true)
        if [[ -n "$listener" ]] && ! printf '%s\n' "$listener" | grep -Fxq -- "$serve_pid"; then
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
