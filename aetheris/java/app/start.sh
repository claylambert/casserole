#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../../.." && pwd)
# shellcheck disable=SC1091
source "$root/bin/java-env.sh"
echo 'aetheris-java 0.0.1-SNAPSHOT'
jar="$root/aetheris/java/app/aetheris-java.jar"
lib="$root/aetheris/java/app/lib"
shopt -s nullglob
runtime=("$lib"/*.jar)
if [[ ! -f "$jar" || ${#runtime[@]} -eq 0 ]]; then
  "$root/aetheris/java/bin/install.sh"
  runtime=("$lib"/*.jar)
fi
if [[ ! -f "$jar" || ${#runtime[@]} -eq 0 ]]; then
  echo 'aetheris-java runtime is missing.' >&2
  exit 1
fi
exec java -cp "$jar:$lib/*" org.casserole.app.ui.ssh.SshClientApp
