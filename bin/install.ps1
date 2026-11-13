$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
& (Join-Path $PSScriptRoot "build.ps1")
$jars = Get-ChildItem (Join-Path $root "target\*.jar") | Where-Object { $_.Name -notlike "*original*" }
if (-not $jars) { throw "casserole jar was not produced" }
New-Item -ItemType Directory -Force -Path (Join-Path $root "app") | Out-Null
Copy-Item $jars[0].FullName (Join-Path $root "app\casserole.jar") -Force
Write-Output "casserole installed to app"
