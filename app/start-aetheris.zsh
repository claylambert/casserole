#!/bin/zsh
# Start the Java Aetheris Client on macOS.
#
# macOS is the operating system on a Mac. The client is
# org.casserole.app.ui.ssh.SshClientApp. It lives in the fat jar
# app/casserole.jar. The jar manifest Main-Class is JarLauncher, and
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
# java-env.zsh is sourced, so the JDK it selects stays in this process.
# The launch runs the java inside that home. The script prints
# aetheris-java 1.0.1. A missing jar runs bin/install.zsh once. A jar that
# is already present is not rebuilt. This start does not install Ollama
# and does not pull a model.
#
# exec replaces this shell with the client, so the client's status is the
# status of the start. The executable is the java inside the JDK 26 home
# pinned for this process. An older java on the machine is not that
# executable.
#
# This start does not install Ollama and does not read a system PATH.
# It still calls use_home again before exec. use_home is the function in
# java-env.zsh. It exports JAVA_HOME and PATH for this process and forgets
# a java this shell already resolved. A home whose java is not executable
# prints JDK 26 java is missing on the error stream and exits 1.
set -eu
set -o pipefail

# This file lives in app/. The parent of that directory is the repository root.
root=$(cd "$(dirname "$0")/.." && pwd)
cd "$root"

# shellcheck disable=SC1091
. "$root/bin/java-env.zsh"

printf 'aetheris-java 1.0.1\n'

# Install builds the jar only when app/casserole.jar is absent.
if [[ ! -f "$root/app/casserole.jar" ]]; then
    zsh "$root/bin/install.zsh"
fi

# use_home is defined by java-env.zsh. Call it again before exec.
# java-env.zsh already selected the home. This call exports that pin again
# and clears remembered commands. The executable below is
# $JAVA_HOME/bin/java. An older java on the machine is not that executable.
use_home "$JAVA_HOME"
if [[ ! -x "$JAVA_HOME/bin/java" ]]; then
    printf 'JDK 26 java is missing\n' >&2
    exit 1
fi
exec "$JAVA_HOME/bin/java" -Dloader.main=org.casserole.app.ui.ssh.SshClientApp -cp "$root/app/casserole.jar" org.springframework.boot.loader.launch.PropertiesLauncher
