#!/usr/bin/env bash
set -euo pipefail
is_jdk26() {
    local home="${1%/}"
    [ -n "$home" ] || return 1
    [ -x "$home/bin/java" ] || return 1
    [ -x "$home/bin/javac" ] || return 1
    "$home/bin/java" -version 2>&1 | grep -q 'version "26'
}
use_home() {
    CHOSEN="${1%/}"
    export JAVA_HOME="$CHOSEN"
    export PATH="$CHOSEN/bin:$PATH"
}
CHOSEN=""
if [ -n "${JAVA_HOME:-}" ] && is_jdk26 "$JAVA_HOME"; then use_home "$JAVA_HOME"; fi
if [ -z "$CHOSEN" ] && command -v java >/dev/null 2>&1; then
    bin=$(command -v java)
    guess=$(cd "$(dirname "$bin")/.." && pwd)
    if is_jdk26 "$guess"; then use_home "$guess"; fi
fi
if [ -z "$CHOSEN" ]; then
    root="${HOME}/.local/share/casserole/zulu26"
    if is_jdk26 "$root"; then use_home "$root"; fi
    if [ -z "$CHOSEN" ] && [ -d "$root" ]; then
        for child in "$root"/*; do
            if [ -d "$child" ] && is_jdk26 "$child"; then use_home "$child"; break; fi
        done
    fi
fi
if [ -z "$CHOSEN" ]; then
    dest="${HOME}/.local/share/casserole/zulu26"
    mkdir -p "$dest"
    meta=$(curl -fsSL "https://api.azul.com/metadata/v1/zulu/packages/?java_version=26&os=linux&arch=x64&archive_type=tar.gz&java_package_type=jdk&latest=true&release_status=ga")
    url=$(printf '%s' "$meta" | sed -n 's/.*"download_url"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/p' | head -n 1)
    [ -n "$url" ] || { echo "JDK 26 download was not found" >&2; exit 1; }
    tmp=$(mktemp)
    curl -fsSL "$url" -o "$tmp"
    tar -xzf "$tmp" -C "$dest"
    rm -f "$tmp"
    for child in "$dest"/*; do
        if [ -d "$child" ] && is_jdk26 "$child"; then use_home "$child"; break; fi
    done
    [ -n "$CHOSEN" ] || { echo "JDK 26 was not unpacked" >&2; exit 1; }
fi
if ! command -v mvn >/dev/null 2>&1; then
    local="${HOME}/.local/share/casserole/maven"
    if [ -x "$local/bin/mvn" ]; then
        export PATH="$local/bin:$PATH"
    else
        mkdir -p "$local"
        tmp=$(mktemp)
        curl -fsSL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.16/apache-maven-3.9.16-bin.tar.gz" -o "$tmp"
        tar -xzf "$tmp" -C "$(dirname "$local")"
        rm -rf "$local"
        mv "$(dirname "$local")/apache-maven-3.9.16" "$local"
        rm -f "$tmp"
        [ -x "$local/bin/mvn" ] || { echo "Maven 3.9.16 was not unpacked" >&2; exit 1; }
        export PATH="$local/bin:$PATH"
    fi
fi
printf 'JAVA_HOME=%s\n' "$JAVA_HOME"
printf 'java-env ready\n'
