#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/.." && pwd)
cd "$root"
# shellcheck disable=SC1091
. "$root/bin/java-env.sh"
printf 'aetheris-java 1.0.0\n'
if [ ! -f "$root/app/casserole.jar" ]; then
    "$root/bin/install.sh"
fi
exec java -Dloader.main=org.casserole.app.ui.ssh.SshClientApp -jar "$root/app/casserole.jar"
