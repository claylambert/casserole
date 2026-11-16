<#
.SYNOPSIS
    Start the Casserole Server.

.DESCRIPTION
    The server is the manifest start class of app/casserole.jar, which is
    org.casserole.CasseroleApplication. java -jar is the right launch here.
    The same jar also holds the Java client. The client start does not use
    java -jar, because that path ignores loader.main and starts this server.

    The jar path is absolute. Set-Location is not how the script finds the
    jar. The process working directory becomes the repository root. A
    relative users.yaml and a relative hostkey.ser resolve from that root.

    bin/java-env.ps1 is dot-sourced, so the JDK it selects stays in this
    process. The launch runs the java inside that home. The script prints
    casserole-server 1.0.0. A missing jar runs bin/install.ps1 once. A jar
    that is already present is not rebuilt.

    Ollama is installed only when the ollama command is missing, when
    ollama list fails, or when that list does not name qwen2.5-coder:7b
    as its own field. A listed model skips the install. The list is read
    with ErrorAction Continue because ollama writes on the error stream,
    and Stop would turn that stream into a terminating error.

    install-ollama.ps1 can replace this process Path with the machine Path
    and the user Path. That read does not write either store. It can put
    an older JDK bin ahead of the JDK 26 bin. After that script returns,
    this script calls Use-JdkHome again. Use-JdkHome is the function in
    java-env.ps1 that keeps the chosen home on this process. The launch
    runs java.exe inside that home. A missing file throws JDK 26 java is
    missing. The machine environment and the user environment stay as
    they are.

.NOTES
    java-env takes its directory in the parameter JavaHome. This script
    does not assign PowerShell's automatic Home variable.
#>
$ErrorActionPreference = "Stop"

# This file lives in app/. The parent of that directory is the repository root.
$root = Split-Path -Parent $PSScriptRoot

# Relative users.yaml and hostkey.ser resolve from the process working directory.
Set-Location $root

# Dot-source. An ordinary call would drop JAVA_HOME when that call returned.
. (Join-Path $root "bin\java-env.ps1")

Write-Output "casserole-server 1.0.0"

$jar = Join-Path $root "app\casserole.jar"

# Install builds the jar only when app/casserole.jar is absent.
if (-not (Test-Path $jar)) { & (Join-Path $root "bin\install.ps1") }

# Start from "the model is needed" only when the command itself is missing.
$ollama = Get-Command ollama -ErrorAction SilentlyContinue
$need = -not $ollama
if (-not $need) {
    # ollama list writes progress and errors on the error stream. Under Stop
    # that stream becomes a terminating error even when the command exits 0.
    # Continue lets the script read the text, then Stop is restored.
    $old = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    try {
        $raw = & ollama list 2>&1
        $code = $LASTEXITCODE
        # 2>&1 yields a mix of strings and error records. Stringify each one
        # so the match below sees the text the engine printed.
        $parts = New-Object System.Collections.Generic.List[string]
        foreach ($item in @($raw)) {
            if ($null -eq $item) { continue }
            [void]$parts.Add([string]$item)
        }
        $list = $parts -join "`n"
    } finally {
        $ErrorActionPreference = $old
    }
    # The model name has to be a whole field. A longer name that merely
    # contains this text does not count.
    $listed = $list -match '(?m)(^|\s)qwen2\.5-coder:7b(\s|$)'
    if ($code -ne 0 -or -not $listed) { $need = $true }
}
if ($need) { & (Join-Path $root "bin\install-ollama.ps1") }

# install-ollama.ps1 may have replaced this process Path with the machine
# Path and the user Path. That read can put an older JDK bin in front.
# Use-JdkHome runs again on the JAVA_HOME this script already selected.
# It rebuilds this process Path and does not write either store.
# The executable is java.exe inside that home, not the java command.
# A missing file throws JDK 26 java is missing.
Use-JdkHome $env:JAVA_HOME
$java = Join-Path $env:JAVA_HOME "bin\java.exe"
if (-not (Test-Path -LiteralPath $java)) { throw "JDK 26 java is missing" }
& $java -jar $jar
