#!/usr/bin/env bash
set -euo pipefail
if ! command -v rustup >/dev/null 2>&1; then
    curl --proto '=https' --tlsv1.2 -fsSL https://sh.rustup.rs | sh -s -- -y --default-toolchain 1.99.1
    # shellcheck disable=SC1090
    . "${HOME}/.cargo/env"
fi
if ! command -v cargo >/dev/null 2>&1; then
    echo "cargo is not on PATH after rustup. Open a new shell and run this script again." >&2
    exit 1
fi
if ! rustup run 1.99.1 rustc --version; then
    echo "toolchain 1.99.1 will not run. Removing it and installing it again."
    rustup toolchain uninstall 1.99.1
    rustup toolchain install 1.99.1
    rustup default 1.99.1
    rustup run 1.99.1 rustc --version
fi
printf 'rust-env ready\n'
cargo --version
