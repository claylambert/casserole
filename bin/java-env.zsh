#!/bin/zsh
# Select a Java 26 JDK and make Maven 3.9.16 runnable on macOS.
#
# macOS is the operating system on a Mac. Callers source this script from
# zsh. The JDK and the Maven directory it selects stay in the caller's
# process. This file does not write a shell startup file. It does not
# write ~/.zshrc, ~/.zprofile, ~/.bash_profile, ~/.profile, or /etc/paths.
# It does not call launchctl. It does not change the machine environment
# or the user environment.
#
# The pin is this process. Exporting JAVA_HOME and PATH here leaves those
# stores as they are. An older JDK already in JAVA_HOME or on PATH is not
# the session JDK. A bin directory that holds another JDK or JRE leaves
# this process PATH. That directory stays on the machine. /usr/bin and
# /bin stay, because the macOS java stub in those directories is not a
# JDK layout.
#
# use_home stores the home. It exports JAVA_HOME and PATH for this process
# and clears the shell's remembered command locations. It does not write a
# profile or a system file. is_other_java_bin recognizes another JDK bin
# or a JRE layout. A directory that fails that test stays on this process
# PATH. A start script calls use_home again before it launches the java
# inside the chosen home. The chosen java is $JAVA_HOME/bin/java.
#
# A second run in the same shell rebuilds PATH the same way. The chosen
# bin is first. Other JDK bins stay off. A directory that is already the
# chosen bin is not added twice.
#
# A JDK 26 home has java and javac, and java -version prints version "26.
# The search stops at the first home that passes. A Mac JDK is often a
# bundle. The directory that holds bin/java may be the path itself, the
# Contents/Home directory inside a .jdk bundle, or
# libexec/openjdk.jdk/Contents/Home under a Homebrew prefix.
#
# The order is:
#
# 1. JAVA_HOME, when it is already a JDK 26. That home stays.
# 2. java on PATH. The home is the parent of that java's bin directory.
# 3. /usr/libexec/java_home -v 26, when that home is a JDK 26.
# 4. /Library/Java/JavaVirtualMachines and
#    ~/Library/Java/JavaVirtualMachines. A child whose name starts with
#    zulu26 or jdk-26 counts, and a child whose name ends in .jdk counts.
# 5. Homebrew. Homebrew is the package manager a Mac usually has. The
#    brew command is the one on PATH, or /opt/homebrew/bin/brew, or
#    /usr/local/bin/brew. brew --prefix names the folder of openjdk@26,
#    then zulu@26, then openjdk. A folder counts only when it is a JDK 26.
#    When brew is absent, the same names are read under /opt/homebrew/opt
#    and /usr/local/opt. The script does not run brew install.
# 6. ~/.local/share/casserole and ~/.local/share/casserole/zulu26. The
#    directory itself counts, then a child, then Contents/Home.
# 7. A download of the current Azul Zulu 26 JDK, unpacked under that
#    zulu26 directory. The archive is tar.gz. Apple silicon asks for
#    arch=arm. Intel asks for arch=x64. The package name contains -ca-jdk.
#    A name that contains crac, and a name that contains -fx-, is skipped.
#
# An earlier home that passes is kept. The download runs only when every
# earlier home failed. A second run that still sees that home does not
# download again.
#
# Maven is separate from the JDK. mvn already on PATH is kept, with no
# version check. Otherwise brew --prefix maven is used when that formula
# is already installed. Otherwise ~/.local/share/casserole/maven is used
# when its mvn is present. When it is not, Apache Maven 3.9.16 is
# downloaded there. The chosen Maven bin is prepended only when mvn is
# still missing, and only when that bin is not already on PATH.
#
# Success prints JAVA_HOME=<path> and java-env ready.
set -eu
set -o pipefail

# A home counts only when both tools exist and the version text names 26.
# java -version writes on stderr. The text is captured without a pipe, so
# a closed pipe cannot fail a real JDK 26. A java that exits non-zero is
# not a JDK 26.
is_jdk26() {
    local home="${1%/}"
    local text
    [[ -n "$home" ]] || return 1
    [[ -x "$home/bin/java" ]] || return 1
    [[ -x "$home/bin/javac" ]] || return 1
    text=$("$home/bin/java" -version 2>&1 || true)
    case "$text" in
        *'version "26'*) return 0 ;;
        *) return 1 ;;
    esac
}

# is_other_java_bin reports whether one PATH entry is another JDK or JRE
# bin. A zero status means use_home drops that entry from this process
# PATH. The directory stays on the machine.
# An empty directory is not another JDK. A directory with no executable
# java is not another JDK, so that directory stays.
# /usr/bin and /bin stay. The macOS java stub lives there, and those
# directories are not a JDK layout.
# A directory that holds both java and javac is another JDK bin.
# A directory that holds java, and whose parent holds a file named
# release, is a JDK or JRE layout. The release file is the marker in the
# JDK home.
# Any other directory stays on this process PATH.
is_other_java_bin() {
    local dir="${1%/}"
    if [[ "$dir" == "/usr/bin" || "$dir" == "/bin" ]]; then
        return 1
    fi
    if [[ ! -x "$dir/java" ]]; then
        return 1
    fi
    if [[ -x "$dir/javac" ]]; then
        return 0
    fi
    if [[ -f "$(dirname "$dir")/release" ]]; then
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
# zsh does not split an unquoted parameter on that colon, so the split is
# written out. An empty entry is skipped. The comparison is the text of
# the entry. An entry that is already the chosen bin is skipped, because
# that bin is placed at the front. An entry that is_other_java_bin accepts
# is skipped. Every other entry stays, in the order it already had.
# When no entry stays, PATH is only the chosen bin. Otherwise PATH is the
# chosen bin, a colon, and then the kept entries.
# hash -r clears the locations this shell already resolved. A java found
# on the old PATH is looked up again.
# A skipped directory stays on the machine. It is only absent from this
# process. A second call with the same home produces the same PATH.
use_home() {
    local bin entry trim kept
    CHOSEN="${1%/}"
    export JAVA_HOME="$CHOSEN"
    bin="$CHOSEN/bin"
    kept=""
    for entry in "${(@s/:/)PATH}"; do
        [[ -n "$entry" ]] || continue
        trim="${entry%/}"
        [[ "$trim" == "$bin" ]] && continue
        if is_other_java_bin "$trim"; then
            continue
        fi
        if [[ -n "$kept" ]]; then
            kept="$kept:$entry"
        else
            kept=$entry
        fi
    done
    if [[ -n "$kept" ]]; then
        export PATH="$bin:$kept"
    else
        export PATH="$bin"
    fi
    hash -r
}

# resolve_home prints the directory that holds bin/java and bin/javac.
# A bundle whose java lives in Contents/Home resolves to that home.
# A Homebrew prefix whose java lives in libexec/openjdk.jdk/Contents/Home
# resolves to that home. A path that is already the home prints itself.
# A path that is none of those returns 1 and prints nothing.
resolve_home() {
    local guess="${1%/}"
    local candidate
    [[ -n "$guess" ]] || return 1
    for candidate in \
        "$guess" \
        "$guess/Contents/Home" \
        "$guess/libexec/openjdk.jdk/Contents/Home"
    do
        if [[ -x "$candidate/bin/java" && -x "$candidate/bin/javac" ]]; then
            printf '%s\n' "$candidate"
            return 0
        fi
    done
    return 1
}

# try_home resolves one candidate, keeps it when it is JDK 26, and returns
# 1 when it is not. The caller tests the status. A miss does not exit.
try_home() {
    local resolved
    resolved=$(resolve_home "$1") || return 1
    is_jdk26 "$resolved" || return 1
    use_home "$resolved"
}

# try_tree accepts a directory, then one child that is a .jdk bundle or a
# zulu26 or jdk-26 folder. The unpack of a macOS Azul archive is a folder
# that holds the bundle. The bundle holds Contents/Home.
try_tree() {
    local dir="${1%/}"
    local bundle
    [[ -d "$dir" ]] || return 1
    if try_home "$dir"; then
        return 0
    fi
    for bundle in "$dir"/*.jdk(N) "$dir"/zulu26*(N) "$dir"/jdk-26*(N); do
        [[ -d "$bundle" ]] || continue
        if try_home "$bundle"; then
            return 0
        fi
    done
    return 1
}

# prepend_dir puts one directory at the front of this process PATH when
# that directory exists and is not already an entry. A second call does
# not grow PATH.
prepend_dir() {
    local dir="${1%/}"
    local entry
    [[ -n "$dir" && -d "$dir" ]] || return 0
    for entry in "${(@s/:/)PATH}"; do
        [[ "${entry%/}" == "$dir" ]] && return 0
    done
    export PATH="$dir:$PATH"
    hash -r
}

# brew_exe prints the Homebrew command. PATH is first. The two prefixes
# a Mac install uses are next. A miss returns 1. This does not run
# brew install and does not change PATH.
brew_exe() {
    local candidate
    if command -v brew >/dev/null 2>&1; then
        command -v brew
        return 0
    fi
    for candidate in /opt/homebrew/bin/brew /usr/local/bin/brew; do
        if [[ -x "$candidate" ]]; then
            printf '%s\n' "$candidate"
            return 0
        fi
    done
    return 1
}

# brew_prefix prints the folder of one installed formula. A formula that
# is not installed returns 1. The brew command is not put on PATH.
brew_prefix() {
    local brew prefix
    brew=$(brew_exe) || return 1
    prefix=$("$brew" --prefix "$1" 2>/dev/null || true)
    [[ -n "$prefix" && -d "$prefix" ]] || return 1
    printf '%s\n' "$prefix"
}

# CHOSEN stays empty until a home passes. Each later block runs only then.
CHOSEN=""
if [[ -n "${JAVA_HOME:-}" ]]; then
    try_home "$JAVA_HOME" || true
fi

# java on PATH. The real path is followed so a symlink into a bundle still
# yields the JDK home. /usr/bin/java is the stub. It does not pass.
if [[ -z "$CHOSEN" ]] && command -v java >/dev/null 2>&1; then
    java_bin=$(command -v java)
    java_real=${java_bin:A}
    java_parent=$(cd "$(dirname "$java_real")/.." && pwd 2>/dev/null || true)
    if [[ -n "$java_parent" ]]; then
        try_home "$java_parent" || true
    fi
fi

# The system helper. A missing JDK 26 makes java_home exit non-zero.
# That exit does not stop the script. The printed home is usually already
# Contents/Home.
if [[ -z "$CHOSEN" && -x /usr/libexec/java_home ]]; then
    java_helper=$(/usr/libexec/java_home -v 26 2>/dev/null || true)
    if [[ -n "$java_helper" ]]; then
        try_home "$java_helper" || true
    fi
fi

# Installed bundles. A matching child name counts. The (N) qualifier
# leaves an empty match off the list, so a missing folder does not stop
# the script.
if [[ -z "$CHOSEN" ]]; then
    for root in /Library/Java/JavaVirtualMachines "${HOME}/Library/Java/JavaVirtualMachines"; do
        [[ -d "$root" ]] || continue
        for dir in "$root"/zulu26*(N) "$root"/jdk-26*(N) "$root"/*.jdk(N); do
            [[ -d "$dir" ]] || continue
            if try_home "$dir"; then
                break
            fi
        done
        [[ -n "$CHOSEN" ]] && break
    done
fi

# Homebrew. brew --prefix is asked first. The opt folders cover a Mac
# where the formula is installed and the brew command is not on PATH.
# openjdk with no version counts only when it is JDK 26.
if [[ -z "$CHOSEN" ]]; then
    for name in openjdk@26 zulu@26 openjdk; do
        brew_home=$(brew_prefix "$name" || true)
        if [[ -n "$brew_home" ]] && try_home "$brew_home"; then
            break
        fi
    done
fi
if [[ -z "$CHOSEN" ]]; then
    for root in /opt/homebrew/opt /usr/local/opt; do
        [[ -d "$root" ]] || continue
        for name in openjdk@26 zulu@26 openjdk; do
            [[ -e "$root/$name" ]] || continue
            if try_home "$root/$name"; then
                break
            fi
        done
        [[ -n "$CHOSEN" ]] && break
    done
fi

# A previous unpack under the local casserole directory.
if [[ -z "$CHOSEN" ]]; then
    casserole_root="${HOME}/.local/share/casserole"
    if [[ -d "$casserole_root" ]]; then
        for dir in "$casserole_root"/zulu26*(N) "$casserole_root"/jdk-26*(N); do
            [[ -d "$dir" ]] || continue
            if try_tree "$dir"; then
                break
            fi
        done
    fi
fi
if [[ -z "$CHOSEN" ]]; then
    unpack_root="${HOME}/.local/share/casserole/zulu26"
    if [[ -d "$unpack_root" ]]; then
        try_tree "$unpack_root" || true
        if [[ -z "$CHOSEN" ]]; then
            for child in "$unpack_root"/*(N); do
                [[ -d "$child" ]] || continue
                if try_tree "$child"; then
                    break
                fi
            done
        fi
    fi
fi

# Nothing already installed is JDK 26. Download the current Zulu 26 JDK.
# latest=true returns more than one package. The first download_url can
# be the crac archive. This loop keeps the object whose name contains
# -ca-jdk and does not contain crac or -fx-. The archive is removed after
# it is unpacked. A home that still fails stops the script.
if [[ -z "$CHOSEN" ]]; then
    dest="${HOME}/.local/share/casserole/zulu26"
    mkdir -p "$dest"
    machine=$(uname -m)
    case "$machine" in
        arm64|aarch64) azul_arch=arm ;;
        x86_64) azul_arch=x64 ;;
        *)
            printf 'JDK 26 download was not found\n' >&2
            exit 1
            ;;
    esac
    meta=$(curl -fsSL "https://api.azul.com/metadata/v1/zulu/packages/?java_version=26&os=macos&arch=${azul_arch}&archive_type=tar.gz&java_package_type=jdk&latest=true&release_status=ga&javafx_bundled=false")
    url=""
    rest=$meta
    while [[ "$rest" == *'{'* ]]; do
        rest=${rest#*\{}
        obj="{${rest%%\}*}"
        rest=${rest#*\}}
        pkg_name=$(printf '%s\n' "$obj" | sed -n 's/.*"name"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/p' || true)
        pkg_name=${pkg_name%%$'\n'*}
        case "$pkg_name" in
            *crac*|*-fx-*) continue ;;
        esac
        case "$pkg_name" in
            *-ca-jdk*) ;;
            *) continue ;;
        esac
        url=$(printf '%s\n' "$obj" | sed -n 's/.*"download_url"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/p' || true)
        url=${url%%$'\n'*}
        [[ -n "$url" ]] && break
    done
    [[ -n "$url" ]] || { printf 'JDK 26 download was not found\n' >&2; exit 1; }
    tmp=$(mktemp)
    curl -fsSL "$url" -o "$tmp"
    tar -xzf "$tmp" -C "$dest"
    rm -f "$tmp"
    for child in "$dest"/*(N); do
        [[ -d "$child" ]] || continue
        if try_tree "$child"; then
            break
        fi
    done
    [[ -n "$CHOSEN" ]] || { printf 'JDK 26 was not unpacked\n' >&2; exit 1; }
fi

# mvn on PATH is kept with no version check. An installed Homebrew maven
# formula is next. The local copy follows. The download is the fixed
# Apache Maven 3.9.16 binary archive. The unpacked directory is renamed
# onto the local maven path. The bin is prepended only when it is not
# already on PATH.
if ! command -v mvn >/dev/null 2>&1; then
    brew_maven=$(brew_prefix maven || true)
    if [[ -n "$brew_maven" && -x "$brew_maven/bin/mvn" ]]; then
        prepend_dir "$brew_maven/bin"
    fi
fi
if ! command -v mvn >/dev/null 2>&1; then
    maven_home="${HOME}/.local/share/casserole/maven"
    if [[ -x "$maven_home/bin/mvn" ]]; then
        prepend_dir "$maven_home/bin"
    else
        mkdir -p "$maven_home"
        tmp=$(mktemp)
        curl -fsSL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.16/apache-maven-3.9.16-bin.tar.gz" -o "$tmp"
        tar -xzf "$tmp" -C "$(dirname "$maven_home")"
        rm -rf "$maven_home"
        mv "$(dirname "$maven_home")/apache-maven-3.9.16" "$maven_home"
        rm -f "$tmp"
        [[ -x "$maven_home/bin/mvn" ]] || { printf 'Maven 3.9.16 was not unpacked\n' >&2; exit 1; }
        prepend_dir "$maven_home/bin"
    fi
fi

printf 'JAVA_HOME=%s\n' "$JAVA_HOME"
printf 'java-env ready\n'
