param([string]$JavaHome)
$ErrorActionPreference = "Stop"
function Test-Jdk26([string]$Home) {
    if (-not $Home) { return $false }
    $Home = $Home.TrimEnd('\', '/')
    $java = Join-Path $Home "bin\java.exe"
    $javac = Join-Path $Home "bin\javac.exe"
    if (-not (Test-Path $java) -or -not (Test-Path $javac)) { return $false }
    $line = & $java -version 2>&1 | Out-String
    return $line -match "version `"26"
}
function Use-Home([string]$Home) {
    $script:Chosen = $Home.TrimEnd('\', '/')
    $env:JAVA_HOME = $script:Chosen
    $env:Path = (Join-Path $script:Chosen "bin") + ";" + $env:Path
}
$Chosen = $null
if ($JavaHome -and (Test-Jdk26 $JavaHome)) { Use-Home $JavaHome }
elseif ($env:JAVA_HOME -and (Test-Jdk26 $env:JAVA_HOME)) { Use-Home $env:JAVA_HOME }
else {
    $cmd = Get-Command java -ErrorAction SilentlyContinue
    if ($cmd) {
        $homeGuess = Split-Path (Split-Path $cmd.Source -Parent) -Parent
        if (Test-Jdk26 $homeGuess) { Use-Home $homeGuess }
    }
}
if (-not $Chosen) {
    $roots = @(
        (Join-Path $env:LOCALAPPDATA "casserole\zulu26"),
        "C:\java\zulu26"
    )
    foreach ($root in $roots) {
        if (Test-Jdk26 $root) { Use-Home $root; break }
        if (Test-Path $root) {
            $child = Get-ChildItem $root -Directory -ErrorAction SilentlyContinue | Select-Object -First 1
            if ($child -and (Test-Jdk26 $child.FullName)) { Use-Home $child.FullName; break }
        }
    }
}
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
    Use-Home $child.FullName
}
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
