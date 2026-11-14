<#
.SYNOPSIS
    Print the toolchain this machine will use to build and start.

.DESCRIPTION
    The script dot-sources bin/java-env.ps1 and bin/rust-env.ps1 first.
    Those scripts select JDK 26 and Rust 1.99.1, and they can still throw.
    After they return, this screen reports what it found and does not throw
    for a missing optional tool.

    The lines are JDK, Maven, Cargo, rustc, Ollama, and Model. The model
    name is qwen2.5-coder:7b. A missing ollama command prints the line that
    names bin\install-ollama.ps1. A model that is not a whole field in
    ollama list prints the not-pulled line and names that same script.
    The screen then prints pre-build-check finished.

    mvn, cargo, rustc, and ollama write on the error stream. Under Stop,
    PowerShell 5.1 turns that stream into a terminating error even when
    the tool exits 0. Invoke-Quiet lowers Stop, returns one object with
    Code and Text, and restores Stop. The screen prints the first line of
    that text. A non-zero code prints the named failure for that tool
    and the screen continues.

    Nothing here starts the server, the client, or a build.
#>
$ErrorActionPreference = "Stop"

# These two can throw. A JDK that cannot be selected, or a Rust pin that
# still will not run, stops before the screen. That is their job.
. (Join-Path $PSScriptRoot "java-env.ps1")
. (Join-Path $PSScriptRoot "rust-env.ps1")

# One object. Code is the exit code. Text is the combined output.
# The error stream is part of Text, and it does not stop the screen.
function Invoke-Quiet {
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

# Two spaces between the label and the state, matching the bash screen.
function Line {
    param([string]$Name, [string]$State)
    Write-Output ($Name + "  " + $State)
}

# The version tools print several lines. The screen keeps the first.
function First-Line {
    param([string]$Text)
    if (-not $Text) { return "" }
    return ($Text -split "`n")[0]
}

Line "JDK" $env:JAVA_HOME

$mvn = Invoke-Quiet { mvn -version }
if ($mvn.Code -eq 0) { Line "Maven" (First-Line $mvn.Text) } else { Line "Maven" "mvn -version failed" }

$cargo = Invoke-Quiet { cargo --version }
if ($cargo.Code -eq 0) { Line "Cargo" (First-Line $cargo.Text) } else { Line "Cargo" "cargo --version failed" }

# The same pin rust-env uses. A miss here is reported. It does not heal again.
$rustc = Invoke-Quiet { rustup run 1.99.1 rustc --version }
if ($rustc.Code -eq 0) { Line "rustc" (First-Line $rustc.Text) } else { Line "rustc" "toolchain 1.99.1 will not run" }

# Ollama is optional on this screen. A missing engine or model names the
# install script and does not throw. The model name has to be a whole field.
if (Get-Command ollama -ErrorAction SilentlyContinue) {
    $ollama = Invoke-Quiet { ollama --version }
    if ($ollama.Text) { Line "Ollama" (First-Line $ollama.Text) } else { Line "Ollama" "present" }
    $list = Invoke-Quiet { ollama list }
    $listed = $list.Text -match '(?m)(^|\s)qwen2\.5-coder:7b(\s|$)'
    if ($list.Code -eq 0 -and $listed) {
        Line "Model" "qwen2.5-coder:7b"
    } else {
        Line "Model" "qwen2.5-coder:7b is not pulled. Run bin\install-ollama.ps1"
    }
} else {
    Line "Ollama" "missing. Run bin\install-ollama.ps1"
}

Write-Output "pre-build-check finished"
