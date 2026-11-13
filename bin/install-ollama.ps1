$ErrorActionPreference = "Stop"
$model = "qwen2.5-coder:7b"
if ($args.Count -ge 1) { $model = $args[0] }
if (-not (Get-Command ollama -ErrorAction SilentlyContinue)) {
    winget install --id Ollama.Ollama -e --accept-source-agreements --accept-package-agreements
}
& ollama pull $model
if ($LASTEXITCODE -ne 0) { throw "ollama pull failed" }
Write-Output "ollama model ready"
