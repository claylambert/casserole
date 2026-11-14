<#
.SYNOPSIS
    Build the Casserole fat jar with Maven.

.DESCRIPTION
    The script loads bin/java-env.ps1 in this process, changes to the
    repository root, and runs mvn package. The root is the directory that
    holds pom.xml. Maven's own lines are printed as they arrive.

    mvn writes its version and its progress on the error stream. Under Stop,
    PowerShell 5.1 turns that stream into a terminating error even when the
    build exits 0. Invoke-Stream lowers Stop, prints each line with
    Write-Host, and returns only the exit code. Write-Host is required.
    Write-Output would mix those lines into the function result, and the
    exit-code test would then see text.

    A non-zero exit throws casserole build failed. Success prints
    casserole build finished. This script does not copy the jar into app/.
    bin/install.ps1 does that copy.

.NOTES
    java-env takes its directory in the parameter JavaHome. This script
    does not assign PowerShell's automatic Home variable.
#>
$ErrorActionPreference = "Stop"

# This file lives in bin/. The parent of that directory is the repository root.
$root = Split-Path -Parent $PSScriptRoot
Set-Location $root

# Dot-source so the selected JDK and Maven stay in this process.
. (Join-Path $PSScriptRoot "java-env.ps1")

# Read a native command without letting its error stream stop a good run.
# The returned value is the exit code alone.
function Invoke-Stream {
    param([scriptblock]$Command)
    $old = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    try {
        & $Command 2>&1 | ForEach-Object { Write-Host ([string]$_) }
        $code = $LASTEXITCODE
    } finally {
        $ErrorActionPreference = $old
    }
    return $code
}

if ((Invoke-Stream { mvn package }) -ne 0) { throw "casserole build failed" }
Write-Output "casserole build finished"
