#!/usr/bin/env bash
# Select a Java 26 JDK and make Maven 3.9.16 runnable.
#
# Callers source this script. The JDK and the Maven directory it selects
# stay in the caller's process.
#
# The pin is this process. Exporting JAVA_HOME and PATH here leaves the
# machine environment and the user environment as they are. An older JDK
# already in JAVA_HOME or on PATH is not the session JDK. A bin directory
# that holds another JDK or JRE leaves this process PATH. That directory
# stays on the machine.
#
# use_home stores the home. It exports JAVA_HOME and PATH for this process
# and clears the shell's remembered command locations. It does not write a
# profile or a system file. is_other_java_bin recognizes another JDK bin
# or a JRE layout. A directory that fails that test stays on this process
# PATH. A start script calls use_home again before it launches the java
# inside the chosen home.
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

# is_other_java_bin reports whether one PATH entry is another JDK or JRE
# bin. A zero status means use_home drops that entry from this process
# PATH. The directory stays on the machine.
# An empty directory is not another JDK. A directory with no executable
# java is not another JDK, so that directory stays.
# A directory that holds both java and javac is another JDK bin.
# A directory that holds java, and whose parent holds a file named
# release, is a JDK or JRE layout. The release file is the marker in the
# JDK home.
# Any other directory stays on this process PATH.
is_other_java_bin() {
    local dir="${1%/}"
    if [ ! -x "$dir/java" ]; then
        return 1
    fi
    if [ -x "$dir/javac" ]; then
        return 0
    fi
    if [ -f "$(dirname "$dir")/release" ]; then
        return 0
    fi
    return 1
}

# use_home pins one JDK home for this process.
# The argument is the directory that holds bin/java. A trailing slash is
# removed before the path is stored in CHOSEN.
# JAVA_HOME and PATH are exported for this process. export does not write
# a profile or a system file. The machine environment and the user
# environment stay as they are.
# PATH is rebuilt for this process only. Entries are split on a colon.
# An empty entry is skipped. The comparison is the text of the entry.
# An entry that is already the chosen bin is skipped, because that bin is
# placed at the front. An entry that is_other_java_bin accepts is skipped.
# Every other entry stays, in the order it already had.
# When no entry stays, PATH is only the chosen bin. Otherwise PATH is the
# chosen bin, a colon, and then the kept entries.
# hash -r clears the locations this shell already resolved. A java found
# on the old PATH is looked up again.
# A skipped directory stays on the machine. It is only absent from this
# process.
use_home() {
    CHOSEN="${1%/}"
    export JAVA_HOME="$CHOSEN"
    local bin="$CHOSEN/bin"
    local kept="" entry trim old_ifs
    old_ifs=$IFS
    IFS=':'
    for entry in $PATH; do
        [ -n "$entry" ] || continue
        trim=${entry%/}
        [ "$trim" = "$bin" ] && continue
        if is_other_java_bin "$trim"; then
            continue
        fi
        if [ -n "$kept" ]; then
            kept="$kept:$entry"
        else
            kept=$entry
        fi
    done
    IFS=$old_ifs
    if [ -n "$kept" ]; then
        export PATH="$bin:$kept"
    else
        export PATH="$bin"
    fi
    hash -r
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
