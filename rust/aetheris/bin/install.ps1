<#
.SYNOPSIS
    Release-build the Rust Aetheris Client and copy it into rust/aetheris/app.

.DESCRIPTION
    This file lives in rust/aetheris/bin. Three directories up is the
    repository root. The script dot-sources bin/rust-env.ps1, changes to
    the Rust workspace at rust/aetheris, and runs cargo build --release
    for the workspace. It does not pass -p. The workspace build is what
    produces the client binary.

    cargo writes progress on the error stream. Under Stop, PowerShell 5.1
    turns that stream into a terminating error even when the build exits 0.
    Invoke-Stream lowers Stop, prints each line with Write-Host, and
    returns only the exit code.

    A non-zero exit throws aetheris-client build failed. After a successful
    build the script looks for target/release/aetheris-client.exe and then
    for target/release/aetheris-client. Neither file throws
    aetheris-client is missing. The file that was found is copied into
    rust/aetheris/app, overwriting an older copy. Success prints
    aetheris-client 1.0.0.

    The start script calls this script only when both names are absent
    from rust/aetheris/app.
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

# The workspace release build. A non-zero exit is named. It is not left as
# a raw cargo status.
if ((Invoke-Stream { cargo build --release }) -ne 0) { throw "aetheris-client build failed" }

# Windows cargo writes the .exe. The unsuffixed name is the other host.
$bin = Join-Path $root "rust\aetheris\target\release\aetheris-client.exe"
if (-not (Test-Path -LiteralPath $bin)) {
    $bin = Join-Path $root "rust\aetheris\target\release\aetheris-client"
}
if (-not (Test-Path -LiteralPath $bin)) { throw "aetheris-client is missing" }

New-Item -ItemType Directory -Force -Path (Join-Path $root "rust\aetheris\app") | Out-Null
Copy-Item -LiteralPath $bin -Destination (Join-Path $root "rust\aetheris\app") -Force
Write-Output "aetheris-client 1.0.0"
