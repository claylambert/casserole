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
# The launch runs the java inside that home. The script prints
# casserole-server 1.0.1. A missing jar runs
# bin/install.sh once. A jar that is already present is not rebuilt.
#
# Ollama is installed only when the ollama command is missing or when
# ollama list does not name qwen2.5-coder:7b as its own field. awk prints
# the first field of every row after the header. grep -Fxq requires the
# whole field to match, so a longer name that merely contains this text
# does not count. A listed model skips the install.
#
# exec replaces this shell with the server, so the server's status is the
# status of the start. The executable is the java inside the JDK 26 home
# pinned for this process. An older java on the machine is not that
# executable.
#
# install-ollama.sh runs the installer in a child shell. A PATH change
# inside that child does not replace this process PATH. This script still
# calls use_home again before exec. use_home is the function in java-env.sh.
# It exports JAVA_HOME and PATH for this process and forgets a java this
# shell already resolved. A home whose java is not executable prints
# JDK 26 java is missing on the error stream and exits 1.
set -euo pipefail

# This file lives in app/. The parent of that directory is the repository root.
root=$(cd "$(dirname "$0")/.." && pwd)
cd "$root"

# shellcheck disable=SC1091
. "$root/bin/java-env.sh"

printf 'casserole-server 1.0.1\n'

# Install builds the jar only when app/casserole.jar is absent.
if [ ! -f "$root/app/casserole.jar" ]; then
    "$root/bin/install.sh"
fi

# A missing command, or a list that does not name the model, runs the install.
# The install checks again and skips work that is already done.
if ! command -v ollama >/dev/null 2>&1 || ! ollama list | awk 'NR>1 {print $1}' | grep -Fxq -- 'qwen2.5-coder:7b'; then
    "$root/bin/install-ollama.sh"
fi

# use_home is defined by java-env.sh. Call it again before exec.
# The installer ran in a child shell, so this process PATH may already be
# the pin. The call exports that pin again and clears remembered commands.
# The executable below is $JAVA_HOME/bin/java. An older java on the machine
# is not that executable.
use_home "$JAVA_HOME"
if [ ! -x "$JAVA_HOME/bin/java" ]; then
    printf 'JDK 26 java is missing\n' >&2
    exit 1
fi
exec "$JAVA_HOME/bin/java" -jar "$root/app/casserole.jar"
