#!/usr/bin/env bash
# Start the Java Aetheris Client.
#
# The client is org.casserole.app.ui.ssh.SshClientApp. It lives in the fat
# jar app/casserole.jar. The jar manifest Main-Class is JarLauncher, and
# JarLauncher starts the Casserole Server. JarLauncher does not read
# loader.main, so this script does not use java -jar.
#
# The jar goes on the classpath. The process is PropertiesLauncher. The
# system property loader.main names the client, and it is set before the
# launcher class. PropertiesLauncher starts that class.
#
# The jar path is absolute. cd is not how the script finds the jar. The
# working directory becomes the repository root so a relative file the
# client opens, including aetheris.yaml, resolves there.
#
# java-env.sh is sourced, so the JDK it selects stays in this process.
# The script prints aetheris-java 1.0.0. A missing jar runs bin/install.sh
# once. A jar that is already present is not rebuilt. This start does not
# install Ollama and does not pull a model.
#
# exec replaces this shell with the client, so the client's status is the
# status of the start.
set -euo pipefail

# This file lives in app/. The parent of that directory is the repository root.
root=$(cd "$(dirname "$0")/.." && pwd)
cd "$root"

# shellcheck disable=SC1091
. "$root/bin/java-env.sh"

printf 'aetheris-java 1.0.0\n'

# Install builds the jar only when app/casserole.jar is absent.
if [ ! -f "$root/app/casserole.jar" ]; then
    "$root/bin/install.sh"
fi

exec java -Dloader.main=org.casserole.app.ui.ssh.SshClientApp -cp "$root/app/casserole.jar" org.springframework.boot.loader.launch.PropertiesLauncher
