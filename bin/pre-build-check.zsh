#!/bin/zsh
# Print the toolchain this Mac will use to build and start.
#
# macOS is the operating system on a Mac. The script sources
# bin/java-env.zsh and bin/rust-env.zsh first. Those scripts select
# JDK 26 and Rust 1.99.1, and they can still exit. After they return,
# this screen reports what it found.
#
# The lines are JDK, Maven, Cargo, rustc, Ollama, and Model. The model
# name is qwen2.5-coder:7b. A missing ollama command names
# bin/install-ollama.zsh. A list that does not name the model as its own
# field names that same script. The screen then prints
# pre-build-check finished.
#
# A version command that fails is printed as an empty value or as the
# not-pulled line. The "|| true" keeps set -e from stopping the screen on
# that optional read. java-env and rust-env have already had their chance
# to stop. Nothing here starts the server, the client, or a build.
set -eu
set -o pipefail

# This file lives in bin/. One directory up is the repository root.
root=$(cd "$(dirname "$0")/.." && pwd)

# shellcheck disable=SC1091
. "$root/bin/java-env.zsh"
# shellcheck disable=SC1091
. "$root/bin/rust-env.zsh"

printf 'JDK  %s\n' "$JAVA_HOME"

# head -n 1 keeps the version line. The rest of a multi-line banner stays off
# this screen. "|| true" lets a missing mvn leave the line empty.
mvn_line=$(mvn -version 2>&1 | head -n 1 || true)
printf 'Maven  %s\n' "$mvn_line"
printf 'Cargo  %s\n' "$(cargo --version 2>&1 || true)"
printf 'rustc  %s\n' "$(rustup run 1.99.1 rustc --version 2>&1 || true)"

# Ollama is optional on this screen. awk prints the model field. grep -Fxq
# requires the whole field, so a longer name that contains this text does not count.
if command -v ollama >/dev/null 2>&1; then
    printf 'Ollama  %s\n' "$(ollama --version 2>&1 || true)"
    if ollama list 2>/dev/null | awk 'NR>1 {print $1}' | grep -Fxq -- 'qwen2.5-coder:7b'; then
        printf 'Model  qwen2.5-coder:7b\n'
    else
        printf 'Model  qwen2.5-coder:7b is not pulled. Run bin/install-ollama.zsh\n'
    fi
else
    printf 'Ollama  missing. Run bin/install-ollama.zsh\n'
fi
printf 'pre-build-check finished\n'
