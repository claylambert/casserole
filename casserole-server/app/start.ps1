$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
. (Join-Path $root 'bin\java-env.ps1')
Write-Host 'casserole-server 0.0.1-SNAPSHOT'
$jar = Join-Path $PSScriptRoot 'casserole-server.jar'
if (-not (Test-Path $jar)) {
    & (Join-Path $root 'casserole-server\bin\install.ps1')
}
if (-not (Test-Path $jar)) {
    throw 'casserole-server.jar is missing.'
}
Push-Location $root
& java -jar $jar
$code = $LASTEXITCODE
Pop-Location
exit $code
