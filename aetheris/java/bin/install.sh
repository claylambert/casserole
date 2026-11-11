#!/usr/bin/env bash
set -euo pipefail
root=$(cd "$(dirname "$0")/../../.." && pwd)
"$root/aetheris/java/bin/build.sh"
jar=$(find "$root/aetheris/java/target" -maxdepth 1 -name 'aetheris-java-*.jar' ! -name '*original*' | head -n 1)
if [[ -z "${jar}" ]]; then
  echo 'java shell jar was not produced' >&2
  exit 1
fi
app="$root/aetheris/java/app"
lib="$app/lib"
mkdir -p "$lib"
shopt -s nullglob
rm -f "$lib"/*.jar
cp "$jar" "$app/aetheris-java.jar"
dep="$root/aetheris/java/target/dependency"
runtime=("$dep"/*.jar)
if [[ ${#runtime[@]} -eq 0 ]]; then
  echo 'runtime jars were not produced' >&2
  exit 1
fi
cp "${runtime[@]}" "$lib/"
echo 'aetheris-java installed to aetheris/java/app'
