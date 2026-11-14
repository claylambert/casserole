$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
Set-Location $root
. (Join-Path $root "bin\java-env.ps1")
Write-Output "casserole-server 1.0.0"
$jar = Join-Path $root "app\casserole.jar"
if (-not (Test-Path $jar)) { & (Join-Path $root "bin\install.ps1") }
$ollama = Get-Command ollama -ErrorAction SilentlyContinue
$need = -not $ollama
if (-not $need) {
    $list = & ollama list 2>&1 | Out-String
    if ($list -notmatch "qwen2.5-coder:7b") { $need = $true }
}
if ($need) { & (Join-Path $root "bin\install-ollama.ps1") }
& java -jar $jar
