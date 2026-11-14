$ErrorActionPreference = "Stop"
$root = Resolve-Path (Join-Path $PSScriptRoot "..\..\..")
. (Join-Path $root "bin\rust-env.ps1")
Set-Location (Join-Path $root "rust\aetheris")
& cargo build
if ($LASTEXITCODE -ne 0) { throw "aetheris-client build failed" }
Write-Output "aetheris-client build finished"
