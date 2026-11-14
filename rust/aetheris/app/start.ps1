<#
.SYNOPSIS
    Start the Rust Aetheris Client.

.DESCRIPTION
    This file lives in rust/aetheris/app. Three directories up is the
    repository root. The script dot-sources bin/rust-env.ps1, so Rust
    1.99.1 stays in this process.

    The client binary is copied into this directory by the install script.
    Windows produces aetheris-client.exe. Another host produces
    aetheris-client. When both are absent, rust/aetheris/bin/install.ps1
    builds the release binary and copies it here. A binary that is already
    present is not rebuilt.

    The .exe is preferred when both names exist. Arguments after the script
    name are passed through. exit $LASTEXITCODE makes this start's status
    the client's status. A binary that is still absent after the install
    throws aetheris-client is missing.
#>
$ErrorActionPreference = "Stop"

# rust/aetheris/app -> aetheris -> rust -> repository root.
$root = Resolve-Path (Join-Path $PSScriptRoot "..\..\..")

# Dot-source so the selected toolchain stays in this process.
. (Join-Path $root "bin\rust-env.ps1")

$exe = Join-Path $root "rust\aetheris\app\aetheris-client.exe"
$bin = Join-Path $root "rust\aetheris\app\aetheris-client"

# Install only when neither name is present. A present binary is left as it is.
if (-not (Test-Path -LiteralPath $exe) -and -not (Test-Path -LiteralPath $bin)) {
    & (Join-Path $root "rust\aetheris\bin\install.ps1")
}

# The Windows name wins when both copies exist. exit passes the client's code on.
if (Test-Path -LiteralPath $exe) {
    & $exe @args
    exit $LASTEXITCODE
}
if (Test-Path -LiteralPath $bin) {
    & $bin @args
    exit $LASTEXITCODE
}
throw "aetheris-client is missing"
