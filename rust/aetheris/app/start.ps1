$ErrorActionPreference = "Stop"
$root = Resolve-Path (Join-Path $PSScriptRoot "..\..\..")
. (Join-Path $root "bin\rust-env.ps1")
$exe = Join-Path $root "rust\aetheris\app\aetheris-client.exe"
$bin = Join-Path $root "rust\aetheris\app\aetheris-client"
if (-not (Test-Path $exe) -and -not (Test-Path $bin)) {
    & (Join-Path $root "rust\aetheris\bin\install.ps1")
}
if (Test-Path $exe) { & $exe @args } else { & $bin @args }
