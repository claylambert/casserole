#!/usr/bin/env bash
# Make Rust 1.99.1 and cargo runnable.
#
# The user cargo bin is put on PATH when that directory exists, so a rustup
# installed for this user is visible before the script looks for it. A
# rustup that is already installed is the one that runs.
#
# When rustup is missing, the script runs the official rustup installer with
# the default toolchain 1.99.1 and then sources the cargo env file. When
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
set -euo pipefail

# A per-user install puts rustup and cargo here. Prepend it when it exists.
if [[ -d "${HOME}/.cargo/bin" ]]; then
    export PATH="${HOME}/.cargo/bin:${PATH}"
fi

# Install rustup only when the command is absent. The default channel is 1.99.1.
if ! command -v rustup >/dev/null 2>&1; then
    curl --proto '=https' --tlsv1.2 -fsSL https://sh.rustup.rs | sh -s -- -y --default-toolchain 1.99.1
    # shellcheck disable=SC1090
    . "${HOME}/.cargo/env"
fi
if ! command -v cargo >/dev/null 2>&1; then
    printf 'cargo is not on PATH after rustup. Open a new shell and run this script again.\n' >&2
    exit 1
fi

# A working compiler exits 0 and prints its version. The if sees the exit code.
# The heal names channel 1.99.1 only. Each failed step exits before the next one.
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
