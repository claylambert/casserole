<#
.SYNOPSIS
    Start the Java Aetheris Client.

.DESCRIPTION
    The client is org.casserole.app.ui.ssh.SshClientApp. It lives in the fat
    jar app/casserole.jar. That jar's manifest Main-Class is JarLauncher, and
    JarLauncher starts the Casserole Server. JarLauncher does not read
    loader.main. This script therefore does not use java -jar.

    The script puts the jar on the classpath and starts PropertiesLauncher.
    The system property loader.main names the client class. PropertiesLauncher
    reads that property and starts the client.

    The jar path below is absolute. Set-Location is not how the script finds
    the jar. The process working directory becomes the repository root so a
    relative file the client opens, including aetheris.yaml, resolves there.

    bin/java-env.ps1 is dot-sourced, so the JDK it selects stays in this
    process. The script prints aetheris-java 1.0.0. A missing jar runs
    bin/install.ps1 once, which builds and copies the jar. A jar that is
    already present is not rebuilt. This start does not install Ollama and
    does not pull a model.

.NOTES
    The -D argument is one quoted string. PowerShell 5.1 otherwise splits the
    property. java-env takes its directory in the parameter JavaHome. This
    script does not assign PowerShell's automatic Home variable.
#>
$ErrorActionPreference = "Stop"

# This file lives in app/. The parent of that directory is the repository root.
$root = Split-Path -Parent $PSScriptRoot

# Relative config is resolved from the process working directory.
Set-Location $root

# Dot-source. An ordinary call would drop JAVA_HOME when that call returned.
. (Join-Path $root "bin\java-env.ps1")

Write-Output "aetheris-java 1.0.0"

$jar = Join-Path $root "app\casserole.jar"

# Install builds the jar only when app/casserole.jar is absent.
if (-not (Test-Path $jar)) { & (Join-Path $root "bin\install.ps1") }

# The property is set before the launcher class. PropertiesLauncher starts
# the class named by loader.main. java -jar would ignore that property.
& java "-Dloader.main=org.casserole.app.ui.ssh.SshClientApp" -cp $jar org.springframework.boot.loader.launch.PropertiesLauncher
