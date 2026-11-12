$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
. (Join-Path $PSScriptRoot "java-env.ps1")
& mvn package
if ($LASTEXITCODE -ne 0) { throw "casserole build failed" }
Write-Output "casserole build finished"
