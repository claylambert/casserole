#!/usr/bin/env bash
# Build the Casserole fat jar and copy it to app/casserole.jar.
#
# The script runs bin/build.sh, then copies the packaged jar from target/
# to app/casserole.jar. The Spring Boot repackage leaves a second jar whose
# name contains "original". That jar is the thin jar from before the
# repackage. It is skipped. The first remaining jar is the fat jar that
# the start scripts launch.
#
# No remaining jar prints casserole jar was not produced and exits 1.
# Success prints casserole installed to app. The copy overwrites an older
# app/casserole.jar. The start scripts call this script only when
# app/casserole.jar is absent, so a present jar is not rebuilt by a start.
set -euo pipefail

# This file lives in bin/. The parent of that directory is the repository root.
root=$(cd "$(dirname "$0")/.." && pwd)
"$root/bin/build.sh"

# -maxdepth 1 stays in target/. A jar in a submodule directory is not a candidate.
# The name filter drops the thin jar the Spring Boot repackage keeps.
jar=$(find "$root/target" -maxdepth 1 -type f -name '*.jar' ! -name '*original*' | head -n 1)
if [ -z "${jar:-}" ]; then
    echo "casserole jar was not produced" >&2
    exit 1
fi
mkdir -p "$root/app"
cp "$jar" "$root/app/casserole.jar"
printf 'casserole installed to app\n'
