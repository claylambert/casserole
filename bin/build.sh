#!/usr/bin/env bash
# Build the Casserole fat jar with Maven.
#
# The script sources bin/java-env.sh, changes to the repository root, and
# runs mvn package. The root is the directory that holds pom.xml.
#
# A non-zero exit prints casserole build failed and exits 1. Success prints
# casserole build finished. This script does not copy the jar into app/.
# bin/install.sh does that copy.
set -euo pipefail

# This file lives in bin/. The parent of that directory is the repository root.
root=$(cd "$(dirname "$0")/.." && pwd)
cd "$root"

# shellcheck disable=SC1091
. "$root/bin/java-env.sh"

# The if keeps set -e from exiting before the named failure is printed.
if ! mvn package; then
    echo "casserole build failed" >&2
    exit 1
fi
printf 'casserole build finished\n'
