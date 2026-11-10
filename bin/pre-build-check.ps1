$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
. (Join-Path $root 'bin\java-env.ps1')
. (Join-Path $root 'bin\rust-env.ps1')

function Line {
    param([string]$Name, [string]$State)
    Write-Host ($Name + '  ' + $State)
}

Line 'JDK' $env:JAVA_HOME
$mvnCmd = Get-Command mvn -ErrorAction SilentlyContinue
if ($mvnCmd) { Line 'Maven' $mvnCmd.Source } else { Line 'Maven' 'missing' }
$cargoCmd = Get-Command cargo -ErrorAction SilentlyContinue
if ($cargoCmd) { Line 'Cargo' (cargo --version) } else { Line 'Cargo' 'missing' }
$rustcCmd = Get-Command rustc -ErrorAction SilentlyContinue
if ($rustcCmd) { Line 'rustc' (rustc --version) } else { Line 'rustc' 'missing' }
if (Get-Command ollama -ErrorAction SilentlyContinue) {
    Line 'Ollama' 'present'
    & ollama list
} else {
    Line 'Ollama' 'missing. Run casserole-server\bin\install-ollama.ps1'
}
Write-Host 'pre-build-check finished'
