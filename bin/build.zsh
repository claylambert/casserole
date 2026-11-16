#!/bin/zsh
# Build the Casserole fat jar with Maven on macOS.
#
# macOS is the operating system on a Mac. The script sources
# bin/java-env.zsh, changes to the repository root, and runs mvn package.
# The root is the directory that holds pom.xml. Sourcing keeps the JDK
# this process selected. The machine environment stays.
#
# A non-zero exit prints casserole build failed and exits 1. Success prints
# casserole build finished. This script does not copy the jar into app/.
# bin/install.zsh does that copy.
set -eu
set -o pipefail

# This file lives in bin/. The parent of that directory is the repository root.
root=$(cd "$(dirname "$0")/.." && pwd)
cd "$root"

# shellcheck disable=SC1091
. "$root/bin/java-env.zsh"

# The if keeps set -e from exiting before the named failure is printed.
if ! mvn package; then
    printf 'casserole build failed\n' >&2
    exit 1
fi
printf 'casserole build finished\n'
