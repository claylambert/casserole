#!/usr/bin/env bash
set -euo pipefail
channel=1.99.1
if ! command -v rustup >/dev/null 2>&1; then
  curl --proto '=https' --tlsv1.2 -fsSL https://sh.rustup.rs | sh -s -- -y --default-toolchain "$channel"
  # shellcheck disable=SC1090
  source "$HOME/.cargo/env"
fi
if ! command -v cargo >/dev/null 2>&1; then
  echo 'cargo is not on PATH after rustup. Open a new shell and run this script again.' >&2
  exit 1
fi
if ! rustup run "$channel" rustc --version >/dev/null 2>&1; then
  echo "toolchain $channel will not run. Removing it and installing it again."
  rustup toolchain uninstall "$channel"
  rustup toolchain install "$channel"
  rustup default "$channel"
  rustup run "$channel" rustc --version >/dev/null
fi
echo 'rust-env ready'
cargo --version
