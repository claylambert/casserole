$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $PSScriptRoot))
& (Join-Path $PSScriptRoot 'build.ps1')
$target = Join-Path $root 'aetheris\java\target'
$jar = Get-ChildItem $target -Filter 'aetheris-java-*.jar' | Where-Object { $_.Name -notmatch 'original' } | Select-Object -First 1
if (-not $jar) { throw 'java shell jar was not produced' }
$app = Join-Path $root 'aetheris\java\app'
$lib = Join-Path $app 'lib'
New-Item -ItemType Directory -Force -Path $lib | Out-Null
Get-ChildItem $lib -Filter '*.jar' -ErrorAction SilentlyContinue | Remove-Item -Force
Copy-Item $jar.FullName (Join-Path $app 'aetheris-java.jar') -Force
$dep = Join-Path $target 'dependency'
$runtime = @(Get-ChildItem $dep -Filter '*.jar' -ErrorAction SilentlyContinue)
if ($runtime.Count -eq 0) { throw 'runtime jars were not produced' }
Copy-Item (Join-Path $dep '*.jar') $lib -Force
Write-Host 'aetheris-java installed to aetheris\java\app'
