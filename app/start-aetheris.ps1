$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
. (Join-Path $root "bin\java-env.ps1")
Write-Output "aetheris-java 1.0.0"
$jar = Join-Path $root "app\casserole.jar"
if (-not (Test-Path $jar)) { & (Join-Path $root "bin\install.ps1") }
& java "-Dloader.main=org.casserole.app.ui.ssh.SshClientApp" -jar $jar
