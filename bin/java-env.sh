#!/usr/bin/env bash
# Select a Java 26 JDK and make Maven 3.9.16 runnable.
#
# Callers source this script. The JDK and the Maven directory it selects
# stay in the caller's process.
#
# A JDK 26 home has java and javac, and java -version prints version "26.
# The search stops at the first home that passes.
#
# The order is:
#
# 1. JAVA_HOME, when it is already a JDK 26.
# 2. java on PATH. The home is the parent of that java's bin directory.
# 3. /usr/lib/jvm and ~/.local/share/casserole. A child whose name starts
#    with zulu26 or jdk-26 counts.
# 4. ~/.local/share/casserole/zulu26. The directory itself counts, then a
#    child that is a JDK 26.
# 5. A download of the current Azul Zulu 26 JDK, unpacked under that
#    zulu26 directory.
#
# An earlier home that passes is kept. The download runs only when every
# earlier home failed.
#
# Maven is separate from the JDK. mvn already on PATH is kept, with no
# version check. Otherwise ~/.local/share/casserole/maven is used when its
# mvn is present. When it is not, Apache Maven 3.9.16 is downloaded there.
#
# Success prints JAVA_HOME=<path> and java-env ready.
set -euo pipefail

# A home counts only when both tools exist and the version text names 26.
# java -version writes on stderr. The pipe feeds that text to grep.
is_jdk26() {
    local home="${1%/}"
    [ -n "$home" ] || return 1
    [ -x "$home/bin/java" ] || return 1
    [ -x "$home/bin/javac" ] || return 1
    "$home/bin/java" -version 2>&1 | grep -q 'version "26'
}

# Remember the home, export JAVA_HOME, and put its bin first on PATH.
use_home() {
    CHOSEN="${1%/}"
    export JAVA_HOME="$CHOSEN"
    export PATH="$CHOSEN/bin:$PATH"
}

# CHOSEN stays empty until a home passes. Each later block runs only then.
CHOSEN=""
if [ -n "${JAVA_HOME:-}" ] && is_jdk26 "$JAVA_HOME"; then use_home "$JAVA_HOME"; fi

# java on PATH. One parent up from the bin directory is the JDK home.
if [ -z "$CHOSEN" ] && command -v java >/dev/null 2>&1; then
    bin=$(command -v java)
    guess=$(cd "$(dirname "$bin")/.." && pwd)
    if is_jdk26 "$guess"; then use_home "$guess"; fi
fi

# Package roots and the local casserole directory. A matching child name counts.
if [ -z "$CHOSEN" ]; then
    for root in /usr/lib/jvm "${HOME}/.local/share/casserole"; do
        [ -d "$root" ] || continue
        for dir in "$root"/zulu26* "$root"/jdk-26*; do
            [ -d "$dir" ] || continue
            if is_jdk26 "$dir"; then use_home "$dir"; break; fi
        done
        [ -n "$CHOSEN" ] && break
    done
fi

# A previous unpack. The directory itself, or the first child that is a JDK.
if [ -z "$CHOSEN" ]; then
    root="${HOME}/.local/share/casserole/zulu26"
    if is_jdk26 "$root"; then use_home "$root"; fi
    if [ -z "$CHOSEN" ] && [ -d "$root" ]; then
        for child in "$root"/*; do
            if [ -d "$child" ] && is_jdk26 "$child"; then use_home "$child"; break; fi
        done
    fi
fi

# Nothing already installed is JDK 26. Download the current Zulu 26 JDK.
# The metadata is JSON. sed takes the first download_url. The archive is
# removed after it is unpacked. A home that still fails stops the script.
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

# mvn on PATH is kept with no version check. The local copy is next.
# The download is the fixed Apache Maven 3.9.16 binary archive. The
# unpacked directory is renamed onto the local maven path.
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
