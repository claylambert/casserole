#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../.." && pwd)
# shellcheck disable=SC1091
source "$root/bin/java-env.sh"
cd "$root"
mvn -pl casserole-server -am package
echo 'casserole-server build finished'
