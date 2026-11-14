<#
.SYNOPSIS
    Build the Casserole fat jar and copy it to app/casserole.jar.

.DESCRIPTION
    The script runs bin/build.ps1, then copies the packaged jar from target/
    to app/casserole.jar. The Spring Boot repackage leaves a second jar whose
    name contains "original". That jar is the thin jar from before the
    repackage. It is skipped. The first remaining jar is the fat jar that
    the start scripts launch.

    No remaining jar throws casserole jar was not produced. Success prints
    casserole installed to app. The copy overwrites an older app/casserole.jar.
    The start scripts call this script only when app/casserole.jar is absent,
    so a present jar is not rebuilt by a start.
#>
$ErrorActionPreference = "Stop"

# This file lives in bin/. The parent of that directory is the repository root.
$root = Split-Path -Parent $PSScriptRoot

# build.ps1 loads the JDK, runs mvn package, and returns here on success.
& (Join-Path $PSScriptRoot "build.ps1")

# Skip the thin jar that the Spring Boot repackage keeps beside the fat jar.
$jars = Get-ChildItem (Join-Path $root "target\*.jar") | Where-Object { $_.Name -notlike "*original*" }
if (-not $jars) { throw "casserole jar was not produced" }

New-Item -ItemType Directory -Force -Path (Join-Path $root "app") | Out-Null
Copy-Item $jars[0].FullName (Join-Path $root "app\casserole.jar") -Force
Write-Output "casserole installed to app"
