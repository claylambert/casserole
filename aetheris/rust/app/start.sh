#!/usr/bin/env bash
set -euo pipefail
echo 'aetheris-client 0.1.0'
here="$(cd "$(dirname "$0")" && pwd)"
root="$(cd "$here/../../.." && pwd)"
exe="$here/aetheris-client"
if [[ ! -x "$exe" ]]; then
  "$root/aetheris/rust/bin/install.sh"
fi
if [[ ! -x "$exe" ]]; then
  echo 'aetheris-client is missing.' >&2
  exit 1
fi
exec "$exe"
