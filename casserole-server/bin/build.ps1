$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
. (Join-Path $root 'bin\java-env.ps1')
Push-Location $root
mvn -pl casserole-server -am package
$code = $LASTEXITCODE
Pop-Location
if ($code -ne 0) { throw 'casserole-server build failed' }
Write-Host 'casserole-server build finished'
