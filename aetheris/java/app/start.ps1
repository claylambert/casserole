$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent (Split-Path -Parent (Split-Path -Parent $PSScriptRoot))
. (Join-Path $root 'bin\java-env.ps1')
Write-Host 'aetheris-java 0.0.1-SNAPSHOT'
$jar = Join-Path $PSScriptRoot 'aetheris-java.jar'
$lib = Join-Path $PSScriptRoot 'lib'
$runtime = @(Get-ChildItem $lib -Filter '*.jar' -ErrorAction SilentlyContinue)
if (-not (Test-Path $jar) -or $runtime.Count -eq 0) {
    & (Join-Path $root 'aetheris\java\bin\install.ps1')
    $runtime = @(Get-ChildItem $lib -Filter '*.jar' -ErrorAction SilentlyContinue)
}
if (-not (Test-Path $jar) -or $runtime.Count -eq 0) {
    throw 'aetheris-java runtime is missing.'
}
$cp = $jar + ';' + (Join-Path $lib '*')
& java -cp $cp org.casserole.app.ui.ssh.SshClientApp
exit $LASTEXITCODE
