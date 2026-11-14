#!/usr/bin/env bash
# Start the Casserole Server.
#
# The server is the manifest start class of app/casserole.jar, which is
# org.casserole.CasseroleApplication. java -jar is the right launch here.
# The same jar also holds the Java client. The client start does not use
# java -jar, because that path ignores loader.main and starts this server.
#
# The jar path is absolute. cd is not how the script finds the jar. The
# working directory becomes the repository root. A relative users.yaml and
# a relative hostkey.ser resolve from that root.
#
# java-env.sh is sourced, so the JDK it selects stays in this process.
# The script prints casserole-server 1.0.0. A missing jar runs
# bin/install.sh once. A jar that is already present is not rebuilt.
#
# Ollama is installed only when the ollama command is missing or when
# ollama list does not name qwen2.5-coder:7b as its own field. awk prints
# the first field of every row after the header. grep -Fxq requires the
# whole field to match, so a longer name that merely contains this text
# does not count. A listed model skips the install.
#
# exec replaces this shell with the server, so the server's status is the
# status of the start.
set -euo pipefail

# This file lives in app/. The parent of that directory is the repository root.
root=$(cd "$(dirname "$0")/.." && pwd)
cd "$root"

# shellcheck disable=SC1091
. "$root/bin/java-env.sh"

printf 'casserole-server 1.0.0\n'

# Install builds the jar only when app/casserole.jar is absent.
if [ ! -f "$root/app/casserole.jar" ]; then
    "$root/bin/install.sh"
fi

# A missing command, or a list that does not name the model, runs the install.
# The install checks again and skips work that is already done.
if ! command -v ollama >/dev/null 2>&1 || ! ollama list | awk 'NR>1 {print $1}' | grep -Fxq -- 'qwen2.5-coder:7b'; then
    "$root/bin/install-ollama.sh"
fi

exec java -jar "$root/app/casserole.jar"
