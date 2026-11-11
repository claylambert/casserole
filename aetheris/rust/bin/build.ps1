$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $PSScriptRoot))
. (Join-Path $root 'bin\rust-env.ps1')
Push-Location (Join-Path $root 'aetheris\rust')
cargo test
$code = $LASTEXITCODE
if ($code -eq 0) {
    cargo build -p aetheris-client
    $code = $LASTEXITCODE
}
Pop-Location
if ($code -ne 0) { throw 'aetheris-rust build failed' }
Write-Host 'aetheris-rust build finished'
