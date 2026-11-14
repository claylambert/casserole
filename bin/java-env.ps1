<#
.SYNOPSIS
    Select a Java 26 JDK and make Maven 3.9.16 runnable.

.DESCRIPTION
    Callers dot-source this script. The JDK and the Maven directory it
    selects stay in the caller's process. A normal call would drop them
    when the call returned.

    A JDK 26 home has java.exe and javac.exe, and java -version prints
    version "26. The search stops at the first home that passes.

    The order is:

    1. The JavaHome parameter, when the caller passed one.
    2. JAVA_HOME, when it is already a JDK 26.
    3. java on PATH. The home is the parent of that java.exe's bin directory.
    4. These roots, in order: C:\java\Java, C:\Program Files\Zulu,
       C:\Program Files\Java, and the local casserole directory. The root
       itself counts when it is a JDK 26. Otherwise a child whose name
       starts with zulu26 or jdk-26 counts.
    5. The local unpack directories %LOCALAPPDATA%\casserole\zulu26 and
       C:\java\zulu26. The directory itself counts, then its first child.
    6. A download of the current Azul Zulu 26 JDK, unpacked under
       %LOCALAPPDATA%\casserole\zulu26.

    An earlier home that passes is kept. The download runs only when every
    earlier home failed.

    Maven is separate from the JDK. mvn already on PATH is kept, with no
    version check. Otherwise C:\java\maven\bin\mvn.cmd is used, then
    %LOCALAPPDATA%\casserole\maven. When none of those exist, Apache Maven
    3.9.16 is downloaded into the local casserole directory.

    java -version writes on the error stream. Under Stop, PowerShell 5.1
    turns that stream into a terminating error even when java exits 0.
    Test-Jdk26 lowers Stop, stringifies each record, and restores Stop
    before it matches the version text.

    Success prints JAVA_HOME=<path> and java-env ready.

.PARAMETER JavaHome
    Optional JDK directory. It is used only when it is a JDK 26. The name
    is JavaHome on purpose. PowerShell's automatic Home variable is
    read-only, and assigning it stops the caller.

.NOTES
    The directory variable inside the test is JdkHome. This script does not
    bind or assign the automatic Home variable.
#>
param([string]$JavaHome)

$ErrorActionPreference = "Stop"

# A home counts only when both tools exist and the version text names 26.
# The error stream is captured. It is not allowed to stop the search.
function Test-Jdk26([string]$JdkHome) {
    if (-not $JdkHome) { return $false }
    $JdkHome = $JdkHome.TrimEnd('\', '/')
    $java = Join-Path $JdkHome "bin\java.exe"
    $javac = Join-Path $JdkHome "bin\javac.exe"
    if (-not (Test-Path $java) -or -not (Test-Path $javac)) { return $false }
    $old = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    try {
        $raw = & $java -version 2>&1
        # 2>&1 yields strings and error records. Stringify each one so the
        # match sees the version line java wrote.
        $parts = New-Object System.Collections.Generic.List[string]
        foreach ($item in @($raw)) {
            if ($null -eq $item) { continue }
            [void]$parts.Add([string]$item)
        }
        $line = $parts -join "`n"
    } finally {
        $ErrorActionPreference = $old
    }
    return $line -match "version `"26"
}

# Remember the home, export JAVA_HOME, and put its bin first on Path.
# $script:Chosen is visible to the rest of this dot-sourced script.
function Use-JdkHome([string]$JdkHome) {
    $script:Chosen = $JdkHome.TrimEnd('\', '/')
    $env:JAVA_HOME = $script:Chosen
    $env:Path = (Join-Path $script:Chosen "bin") + ";" + $env:Path
}

# $Chosen stays empty until a home passes. Each later block runs only then.
$Chosen = $null
if ($JavaHome -and (Test-Jdk26 $JavaHome)) { Use-JdkHome $JavaHome }
elseif ($env:JAVA_HOME -and (Test-Jdk26 $env:JAVA_HOME)) { Use-JdkHome $env:JAVA_HOME }
else {
    # java on PATH. Two parents up from java.exe is the JDK home.
    $cmd = Get-Command java -ErrorAction SilentlyContinue
    if ($cmd) {
        $homeGuess = Split-Path (Split-Path $cmd.Source -Parent) -Parent
        if (Test-Jdk26 $homeGuess) { Use-JdkHome $homeGuess }
    }
}

# Named install roots. The root counts, then a child with a JDK 26 name.
if (-not $Chosen) {
    $namedRoots = @(
        "C:\java\Java",
        "C:\Program Files\Zulu",
        "C:\Program Files\Java",
        (Join-Path $env:LOCALAPPDATA "casserole")
    )
    foreach ($root in $namedRoots) {
        if ($Chosen) { break }
        if (-not (Test-Path -LiteralPath $root)) { continue }
        if (Test-Jdk26 $root) { Use-JdkHome $root; break }
        foreach ($dir in @(Get-ChildItem -LiteralPath $root -Directory -ErrorAction SilentlyContinue)) {
            if ($dir.Name -like "zulu26*" -or $dir.Name -like "jdk-26*") {
                if (Test-Jdk26 $dir.FullName) { Use-JdkHome $dir.FullName; break }
            }
        }
    }
}

# A previous unpack. The directory itself, or the first child that is a JDK.
if (-not $Chosen) {
    $roots = @(
        (Join-Path $env:LOCALAPPDATA "casserole\zulu26"),
        "C:\java\zulu26"
    )
    foreach ($root in $roots) {
        if (Test-Jdk26 $root) { Use-JdkHome $root; break }
        if (Test-Path -LiteralPath $root) {
            $child = Get-ChildItem -LiteralPath $root -Directory -ErrorAction SilentlyContinue | Select-Object -First 1
            if ($child -and (Test-Jdk26 $child.FullName)) { Use-JdkHome $child.FullName; break }
        }
    }
}

# Nothing already installed is JDK 26. Download the current Zulu 26 JDK.
if (-not $Chosen) {
    $dest = Join-Path $env:LOCALAPPDATA "casserole\zulu26"
    New-Item -ItemType Directory -Force -Path $dest | Out-Null
    $meta = "https://api.azul.com/metadata/v1/zulu/packages/?java_version=26&os=windows&arch=x64&archive_type=zip&java_package_type=jdk&latest=true&release_status=ga"
    $pkg = Invoke-RestMethod $meta
    $zip = Join-Path $env:TEMP "zulu26.zip"
    Invoke-WebRequest $pkg[0].download_url -OutFile $zip
    Expand-Archive $zip -DestinationPath $dest -Force
    $child = Get-ChildItem $dest -Directory | Select-Object -First 1
    if (-not (Test-Jdk26 $child.FullName)) { throw "JDK 26 was not unpacked" }
    Use-JdkHome $child.FullName
}

# Maven is kept when any of these is already present. No version is required
# of an mvn that is already on PATH.
function Find-Maven {
    $cmd = Get-Command mvn -ErrorAction SilentlyContinue
    if ($cmd) { return $true }
    $fixed = "C:\java\maven\bin\mvn.cmd"
    if (Test-Path $fixed) { $env:Path = "C:\java\maven\bin;" + $env:Path; return $true }
    $local = Join-Path $env:LOCALAPPDATA "casserole\maven"
    $localCmd = Join-Path $local "bin\mvn.cmd"
    if (Test-Path $localCmd) { $env:Path = (Join-Path $local "bin") + ";" + $env:Path; return $true }
    return $false
}

# The download is the fixed Apache Maven 3.9.16 binary zip.
if (-not (Find-Maven)) {
    $local = Join-Path $env:LOCALAPPDATA "casserole\maven"
    New-Item -ItemType Directory -Force -Path $local | Out-Null
    $zip = Join-Path $env:TEMP "apache-maven-3.9.16-bin.zip"
    Invoke-WebRequest "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.16/apache-maven-3.9.16-bin.zip" -OutFile $zip
    Expand-Archive $zip -DestinationPath $env:TEMP -Force
    Copy-Item (Join-Path $env:TEMP "apache-maven-3.9.16\*") $local -Recurse -Force
    if (-not (Test-Path (Join-Path $local "bin\mvn.cmd"))) { throw "Maven 3.9.16 was not unpacked" }
    $env:Path = (Join-Path $local "bin") + ";" + $env:Path
}

Write-Output ("JAVA_HOME=" + $env:JAVA_HOME)
Write-Output "java-env ready"
