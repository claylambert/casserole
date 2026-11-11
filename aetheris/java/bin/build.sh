#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../../.." && pwd)
# shellcheck disable=SC1091
source "$root/bin/java-env.sh"
cd "$root"
mvn -pl aetheris/java -am package
echo 'aetheris-java build finished'
