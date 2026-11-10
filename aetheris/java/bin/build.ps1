$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $PSScriptRoot))
. (Join-Path $root 'bin\java-env.ps1')
Push-Location $root
mvn -pl aetheris/java -am package
$code = $LASTEXITCODE
Pop-Location
if ($code -ne 0) { throw 'aetheris-java build failed' }
Write-Host 'aetheris-java build finished'
