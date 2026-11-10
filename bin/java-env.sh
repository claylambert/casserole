#!/usr/bin/env bash
set -euo pipefail

use_java_home() {
  local jdk="${1%/}"
  if [[ -z "$jdk" || ! -x "$jdk/bin/java" ]]; then
    return 1
  fi
  export JAVA_HOME="$jdk"
  case ":$PATH:" in
    *":$jdk/bin:"*) ;;
    *) export PATH="$jdk/bin:$PATH" ;;
  esac
  return 0
}

found=0
if [[ -n "${JAVA_HOME:-}" ]]; then
  use_java_home "$JAVA_HOME" && found=1 || true
fi
if [[ "$found" -eq 0 ]]; then
  for root in /usr/lib/jvm "$HOME/.local/share/casserole"; do
    [[ -d "$root" ]] || continue
    for dir in "$root"/zulu26* "$root"/jdk-26*; do
      [[ -d "$dir" ]] || continue
      if use_java_home "$dir"; then
        found=1
        break
      fi
    done
    [[ "$found" -eq 1 ]] && break
  done
fi
if [[ "$found" -eq 0 ]]; then
  install="${HOME}/.local/share/casserole/zulu26"
  if [[ ! -x "$install/bin/java" ]]; then
    query='https://api.azul.com/metadata/v1/zulu/packages/?java_version=26&os=linux&arch=x64&archive_type=tar.gz&java_package_type=jdk&latest=true&release_status=ga'
    url=$(curl -fsSL "$query" | python3 -c 'import json,sys; print(json.load(sys.stdin)[0]["download_url"])')
    tmp=$(mktemp -d)
    curl -fsSL "$url" -o "$tmp/zulu.tgz"
    tar -xzf "$tmp/zulu.tgz" -C "$tmp"
    inner=$(find "$tmp" -mindepth 1 -maxdepth 1 -type d | head -n 1)
    mkdir -p "$(dirname "$install")"
    rm -rf "$install"
    mv "$inner" "$install"
  fi
  use_java_home "$install"
fi

if ! command -v mvn >/dev/null 2>&1; then
  echo 'Maven is not on PATH. Install Apache Maven 3.9 or newer and reopen the shell.' >&2
  exit 1
fi

echo "JAVA_HOME=$JAVA_HOME"
echo 'java-env ready'
