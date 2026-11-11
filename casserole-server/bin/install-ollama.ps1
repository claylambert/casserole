$ErrorActionPreference = 'Stop'
$model = 'qwen2.5-coder:7b'
if ($args.Count -ge 1 -and $args[0]) {
    $model = $args[0]
}
if (-not (Get-Command ollama -ErrorAction SilentlyContinue)) {
    $installer = Join-Path $env:TEMP 'OllamaSetup.exe'
    Invoke-WebRequest 'https://ollama.com/download/OllamaSetup.exe' -OutFile $installer
    Write-Host 'Ollama installer downloaded. Run it, reopen the shell, then run this script again.'
    Write-Host $installer
    exit 1
}
Write-Host ('ollama pull ' + $model)
& ollama pull $model
if ($LASTEXITCODE -ne 0) { throw 'ollama pull failed' }
Write-Host 'install-ollama finished'
