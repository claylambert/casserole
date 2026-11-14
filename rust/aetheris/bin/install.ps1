$ErrorActionPreference = "Stop"
$root = Resolve-Path (Join-Path $PSScriptRoot "..\..\..")
. (Join-Path $root "bin\rust-env.ps1")
Set-Location (Join-Path $root "rust\aetheris")
& cargo build --release
if ($LASTEXITCODE -ne 0) { throw "aetheris-client build failed" }
$bin = Join-Path $root "rust\aetheris\target\release\aetheris-client.exe"
if (-not (Test-Path $bin)) { $bin = Join-Path $root "rust\aetheris\target\release\aetheris-client" }
New-Item -ItemType Directory -Force -Path (Join-Path $root "rust\aetheris\app") | Out-Null
Copy-Item $bin (Join-Path $root "rust\aetheris\app") -Force
Write-Output "aetheris-client 1.0.0"
