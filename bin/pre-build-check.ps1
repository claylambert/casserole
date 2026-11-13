$ErrorActionPreference = "Stop"
. (Join-Path $PSScriptRoot "java-env.ps1")
. (Join-Path $PSScriptRoot "rust-env.ps1")
& java -version
& mvn -version
& rustup run 1.99.1 rustc --version
if (Get-Command ollama -ErrorAction SilentlyContinue) { & ollama --version } else { Write-Output "ollama is not installed" }
Write-Output "pre-build-check finished"
