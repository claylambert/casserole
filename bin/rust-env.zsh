#!/bin/zsh
# Make Rust 1.99.1 and cargo runnable on macOS.
#
# macOS is the operating system on a Mac. Callers source this script from
# zsh. The cargo bin it selects stays in the caller's process. This file
# does not write a shell startup file. The rustup installer is run with
# --no-modify-path, so the installer does not edit ~/.zshrc. This script
# does not source a file that would edit a startup file.
#
# The user cargo bin is put on PATH when that directory exists and is not
# already an entry. A second run in the same shell does not grow PATH. A
# rustup that is already installed is the one that runs. A pin that
# already exits 0 is not uninstalled.
#
# When rustup is missing, an installed Homebrew rustup formula is used
# first. Homebrew is the package manager a Mac usually has. brew --prefix
# rustup names that formula's folder. The script does not run brew install.
# When that formula is absent, the script runs the official rustup
# installer with the default toolchain 1.99.1 and --no-modify-path, then
# prepends ~/.cargo/bin when that directory is not already on PATH. When
# cargo is still missing, the script exits and asks for a new shell.
#
# The pin is rustup run 1.99.1 rustc --version. The if tests that command's
# exit code. The version text is the command's own output. It is not a
# second value the if has to ignore.
#
# A pin that does not exit 0 heals channel 1.99.1 only. The stable toolchain
# is left installed. Each heal step has its own failure:
#
#     toolchain 1.99.1 did not uninstall
#     toolchain 1.99.1 did not install
#     toolchain 1.99.1 did not become the default
#
# The pin runs a second time. A second miss prints
# toolchain 1.99.1 still will not run and exits 1. rust-env ready is
# printed only after the pin has exited 0. cargo --version then has to
# exit 0. A non-zero cargo prints cargo --version failed and exits 1.
set -eu
set -o pipefail

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
# a Mac install uses are next. A miss returns 1.
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
# is not installed returns 1.
brew_prefix() {
    local brew prefix
    brew=$(brew_exe) || return 1
    prefix=$("$brew" --prefix "$1" 2>/dev/null || true)
    [[ -n "$prefix" && -d "$prefix" ]] || return 1
    printf '%s\n' "$prefix"
}

# A per-user install puts rustup and cargo here. Prepend it once.
prepend_dir "${HOME}/.cargo/bin"

# An installed Homebrew rustup is kept. Its bin is prepended only when
# rustup is still missing and that bin is not already on PATH.
if ! command -v rustup >/dev/null 2>&1; then
    brew_rust=$(brew_prefix rustup || true)
    if [[ -n "$brew_rust" && -x "$brew_rust/bin/rustup" ]]; then
        prepend_dir "$brew_rust/bin"
    fi
fi

# Install rustup only when the command is absent. The default channel is
# 1.99.1. --no-modify-path keeps the installer from editing a startup file.
if ! command -v rustup >/dev/null 2>&1; then
    curl --proto '=https' --tlsv1.2 -fsSL https://sh.rustup.rs | sh -s -- -y --default-toolchain 1.99.1 --no-modify-path
    prepend_dir "${HOME}/.cargo/bin"
fi
if ! command -v cargo >/dev/null 2>&1; then
    printf 'cargo is not on PATH after rustup. Open a new shell and run this script again.\n' >&2
    exit 1
fi

# A working compiler exits 0 and prints its version. The if sees the exit code.
# The heal names channel 1.99.1 only. Each failed step exits before the next one.
# A pin that already exits 0 does not uninstall.
if ! rustup run 1.99.1 rustc --version; then
    printf 'toolchain 1.99.1 will not run. Removing it and installing it again.\n'
    if ! rustup toolchain uninstall 1.99.1; then
        printf 'toolchain 1.99.1 did not uninstall\n' >&2
        exit 1
    fi
    if ! rustup toolchain install 1.99.1; then
        printf 'toolchain 1.99.1 did not install\n' >&2
        exit 1
    fi
    if ! rustup default 1.99.1; then
        printf 'toolchain 1.99.1 did not become the default\n' >&2
        exit 1
    fi
    if ! rustup run 1.99.1 rustc --version; then
        printf 'toolchain 1.99.1 still will not run\n' >&2
        exit 1
    fi
fi

# Ready is printed only after the pin exited 0. cargo is checked after that.
printf 'rust-env ready\n'
if ! cargo --version; then
    printf 'cargo --version failed\n' >&2
    exit 1
fi
