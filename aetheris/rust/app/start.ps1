$ErrorActionPreference = 'Stop'
Write-Host 'aetheris-client 0.1.0'
$root = Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $PSScriptRoot))
$exe = Join-Path $PSScriptRoot 'aetheris-client.exe'
if (-not (Test-Path $exe)) {
    $exe = Join-Path $PSScriptRoot 'aetheris-client'
}
if (-not (Test-Path $exe)) {
    & (Join-Path $root 'aetheris\rust\bin\install.ps1')
    $exe = Join-Path $PSScriptRoot 'aetheris-client.exe'
    if (-not (Test-Path $exe)) {
        $exe = Join-Path $PSScriptRoot 'aetheris-client'
    }
}
if (-not (Test-Path $exe)) {
    throw 'aetheris-client is missing.'
}
& $exe
exit $LASTEXITCODE
