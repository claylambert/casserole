<#
.SYNOPSIS
    Debug-build the Rust Aetheris Client.

.DESCRIPTION
    This file lives in rust/aetheris/bin. Three directories up is the
    repository root. The script dot-sources bin/rust-env.ps1, changes to
    the Rust workspace at rust/aetheris, and runs cargo build. That is the
    debug build. The install script is the one that runs cargo build
    --release and copies the binary into rust/aetheris/app.

    cargo writes progress on the error stream. Under Stop, PowerShell 5.1
    turns that stream into a terminating error even when the build exits 0.
    Invoke-Stream lowers Stop, prints each line with Write-Host, and
    returns only the exit code. Write-Host keeps those lines out of the
    function result.

    A non-zero exit throws aetheris-client build failed. Success prints
    aetheris-client build finished.
#>
$ErrorActionPreference = "Stop"

# rust/aetheris/bin -> aetheris -> rust -> repository root.
$root = Resolve-Path (Join-Path $PSScriptRoot "..\..\..")

# Dot-source so the selected toolchain stays in this process.
. (Join-Path $root "bin\rust-env.ps1")

# cargo reads the workspace Cargo.toml from the current directory.
Set-Location (Join-Path $root "rust\aetheris")

# The returned value is the exit code alone.
function Invoke-Stream {
    param([scriptblock]$Command)
    $old = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    try {
        & $Command 2>&1 | ForEach-Object { Write-Host ([string]$_) }
        $code = $LASTEXITCODE
    } finally {
        $ErrorActionPreference = $old
    }
    return $code
}

if ((Invoke-Stream { cargo build }) -ne 0) { throw "aetheris-client build failed" }
Write-Output "aetheris-client build finished"
