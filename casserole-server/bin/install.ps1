$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
& (Join-Path $PSScriptRoot 'build.ps1')
$target = Join-Path $root 'casserole-server\target'
$jar = Get-ChildItem $target -Filter 'casserole-server-*.jar' | Where-Object { $_.Name -notmatch 'original' } | Select-Object -First 1
if (-not $jar) { throw 'server jar was not produced' }
$app = Join-Path $root 'casserole-server\app'
New-Item -ItemType Directory -Force -Path $app | Out-Null
Copy-Item $jar.FullName (Join-Path $app 'casserole-server.jar') -Force
Write-Host 'casserole-server installed to casserole-server\app'
