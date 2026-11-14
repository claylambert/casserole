<#
.SYNOPSIS
    Install the Ollama engine when it is missing, then pull a model when
    that model is not already listed.

.DESCRIPTION
    The default model is qwen2.5-coder:7b. The first argument, when it is
    present, is the model name for this run. A later run with another name
    pulls that name when it is absent.

    Each stage checks, then acts only when the check misses.

    The engine: when ollama is on PATH, ollama --version runs and an exit
    of 0 keeps that engine. The script does not install it again. When the
    command is missing, winget installs the package id Ollama.Ollama. The
    machine Path and the user Path are read again after that install. A
    command that is still missing throws and asks for a new shell.

    The registry key is the file id_ed25519 in the user's .ollama directory.
    This script does not write that file and does not print that file. The
    engine creates it. The script asks http://127.0.0.1:11434/api/version.
    When the engine answers and the key file is already present, this stage
    does nothing. When the API is down or the key file is absent, the script
    starts ollama serve so the engine can create the key and answer. It
    waits up to 15 seconds. A key that is still absent throws
    ollama did not create its registry key. An engine that is still silent
    throws ollama is not answering on port 11434.

    A serve this script started is stopped only when it is not the process
    listening on port 11434. An engine that was already listening stays.
    The temporary serve logs are deleted unread.

    The model: ollama list runs. A list that already names the requested
    model as a whole field prints ollama model ready and returns before
    the pull. A pull that fails throws ollama pull failed and the engine's
    text. Success prints ollama model ready.

    ollama writes on the error stream. Under Stop, PowerShell 5.1 turns
    that stream into a terminating error even when the command exits 0.
    Invoke-Native lowers Stop and returns one object with Code and Text.
#>
$ErrorActionPreference = "Stop"

# The default model. One argument replaces it for this run only.
$model = "qwen2.5-coder:7b"
if ($args.Count -ge 1 -and $args[0]) { $model = [string]$args[0] }

# Code is the exit code. Text is the combined output, including the error
# stream. The caller prints Text. The caller tests Code.
function Invoke-Native {
    param([scriptblock]$Command)
    $old = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    try {
        $raw = & $Command 2>&1
        $code = $LASTEXITCODE
        $parts = New-Object System.Collections.Generic.List[string]
        foreach ($item in @($raw)) {
            if ($null -eq $item) { continue }
            [void]$parts.Add([string]$item)
        }
        $text = $parts -join "`n"
    } finally {
        $ErrorActionPreference = $old
    }
    return [pscustomobject]@{ Code = $code; Text = $text }
}

# The model name has to be a whole field. Regex metacharacters in a caller
# supplied name are escaped, so they match as text.
function Test-ModelListed {
    param([string]$Text, [string]$Name)
    $pattern = '(?m)(^|\s)' + [regex]::Escape($Name) + '(\s|$)'
    return $Text -match $pattern
}

# True only for HTTP 200 from the local engine. A timeout or a refused
# connection is false. It is not a terminating error.
function Test-OllamaApi {
    $old = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    try {
        $reply = Invoke-WebRequest -Uri "http://127.0.0.1:11434/api/version" -UseBasicParsing -TimeoutSec 3
        return ($reply.StatusCode -eq 200)
    } catch {
        return $false
    } finally {
        $ErrorActionPreference = $old
    }
}

# Install only when the command is absent. An engine that is already on PATH
# skips winget. The Path refresh is what makes a just-installed ollama visible
# without opening a new shell.
if (-not (Get-Command ollama -ErrorAction SilentlyContinue)) {
    $old = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    & winget install --id Ollama.Ollama -e --accept-source-agreements --accept-package-agreements
    $wingetCode = $LASTEXITCODE
    $ErrorActionPreference = $old
    if ($wingetCode -ne 0) { throw "ollama install failed" }
    $env:Path = [Environment]::GetEnvironmentVariable("Path", "Machine") + ";" + [Environment]::GetEnvironmentVariable("Path", "User")
}
if (-not (Get-Command ollama -ErrorAction SilentlyContinue)) {
    throw "ollama is not on PATH after install. Open a new shell and run this script again."
}

# A version that exits 0 keeps this engine. The text is shown. A non-zero
# exit includes that text in the throw so the failure is not a bare name.
$version = Invoke-Native { ollama --version }
if ($version.Code -ne 0) { throw "ollama --version failed`n$($version.Text)" }
Write-Output $version.Text

# The engine owns this file. This script does not write it and does not print it.
$key = Join-Path $env:USERPROFILE ".ollama\id_ed25519"
$apiBefore = Test-OllamaApi
if ((-not $apiBefore) -or (-not (Test-Path -LiteralPath $key))) {
    # Serve in the background so this script can wait for the key and the API.
    # The logs exist so Start-Process has somewhere to put the engine's text.
    # They are deleted unread.
    $outLog = Join-Path $env:TEMP "casserole-ollama-serve-out.txt"
    $errLog = Join-Path $env:TEMP "casserole-ollama-serve-err.txt"
    $old = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    try {
        $serve = Start-Process -FilePath (Get-Command ollama).Source -ArgumentList "serve" -PassThru -WindowStyle Hidden -RedirectStandardOutput $outLog -RedirectStandardError $errLog
    } finally {
        $ErrorActionPreference = $old
    }
    # Up to 15 seconds. Leave early when the key exists and the API answers.
    # Also leave when the serve process has already exited and the key exists.
    # A serve that finds the port taken exits after the engine writes the key.
    $deadline = (Get-Date).AddSeconds(15)
    do {
        $keyReady = Test-Path -LiteralPath $key
        $apiUp = Test-OllamaApi
        if ($keyReady -and $apiUp) { break }
        if ($serve.HasExited -and $keyReady) { break }
        Start-Sleep -Milliseconds 300
    } while ((Get-Date) -lt $deadline)
    if (-not (Test-Path -LiteralPath $key)) { throw "ollama did not create its registry key" }
    if (-not (Test-OllamaApi)) { throw "ollama is not answering on port 11434" }
    # Stop the serve this script started only when some other process owns
    # port 11434. The listener that was already there stays.
    if (-not $serve.HasExited) {
        $owners = @(Get-NetTCPConnection -LocalPort 11434 -State Listen -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess -Unique)
        if ($owners.Count -gt 0 -and ($owners -notcontains $serve.Id)) {
            Stop-Process -Id $serve.Id -Force -ErrorAction SilentlyContinue
        }
    }
    Remove-Item $outLog, $errLog -ErrorAction SilentlyContinue
}

# A listed model returns here. return leaves the script. The pull does not run.
$list = Invoke-Native { ollama list }
if ($list.Code -ne 0) { throw "ollama list failed`n$($list.Text)" }
if (Test-ModelListed $list.Text $model) {
    Write-Output "ollama model ready"
    return
}

# Show the pull as it runs, and keep the lines for the failure text.
# Continue is required because the pull writes on the error stream.
$old = $ErrorActionPreference
$ErrorActionPreference = "Continue"
$pullLines = New-Object System.Collections.Generic.List[string]
& ollama pull $model 2>&1 | ForEach-Object {
    $line = [string]$_
    [void]$pullLines.Add($line)
    Write-Output $line
}
$pullCode = $LASTEXITCODE
$ErrorActionPreference = $old
if ($pullCode -ne 0) { throw "ollama pull failed`n$($pullLines -join "`n")" }
Write-Output "ollama model ready"
