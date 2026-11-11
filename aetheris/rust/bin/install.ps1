$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $PSScriptRoot))
. (Join-Path $root 'bin\rust-env.ps1')
Push-Location (Join-Path $root 'aetheris\rust')
cargo build --release -p aetheris-client
$code = $LASTEXITCODE
Pop-Location
if ($code -ne 0) { throw 'release build failed' }
$exe = Join-Path $root 'aetheris\rust\target\release\aetheris-client.exe'
if (-not (Test-Path $exe)) {
    $exe = Join-Path $root 'aetheris\rust\target\release\aetheris-client'
}
$app = Join-Path $root 'aetheris\rust\app'
New-Item -ItemType Directory -Force -Path $app | Out-Null
Copy-Item $exe (Join-Path $app (Split-Path -Leaf $exe)) -Force
Write-Host 'aetheris-client installed to aetheris\rust\app'
