$ErrorActionPreference = 'Stop'

function Use-JavaHome {
    param([string]$JavaHome)
    if ($null -eq $JavaHome) { return $false }
    $JavaHome = $JavaHome.Trim().TrimEnd('\', '/')
    if ($JavaHome.Length -eq 0) { return $false }
    $javaExe = Join-Path $JavaHome 'bin\java.exe'
    if (-not (Test-Path -LiteralPath $javaExe)) { return $false }
    $env:JAVA_HOME = $JavaHome
    $bin = Join-Path $JavaHome 'bin'
    if ($env:Path.IndexOf($bin) -lt 0) {
        $env:Path = $bin + ';' + $env:Path
    }
    return $true
}

$found = $false
if ($env:JAVA_HOME) {
    $found = Use-JavaHome $env:JAVA_HOME
}
if (-not $found) {
    $roots = @('C:\java\Java', 'C:\Program Files\Zulu', 'C:\Program Files\Java')
    foreach ($root in $roots) {
        if (-not (Test-Path $root)) { continue }
        $dirs = Get-ChildItem $root -Directory -ErrorAction SilentlyContinue
        foreach ($dir in $dirs) {
            if ($dir.Name -like 'zulu26*' -or $dir.Name -like 'jdk-26*') {
                if (Use-JavaHome $dir.FullName) {
                    $found = $true
                    break
                }
            }
        }
        if ($found) { break }
    }
}
if (-not $found) {
    $install = Join-Path $env:LOCALAPPDATA 'casserole\zulu26'
    $javaExe = Join-Path $install 'bin\java.exe'
    if (-not (Test-Path $javaExe)) {
        $query = 'https://api.azul.com/metadata/v1/zulu/packages/?java_version=26&os=windows&arch=x64&archive_type=zip&java_package_type=jdk&latest=true&release_status=ga'
        $meta = Invoke-RestMethod $query
        $url = $meta[0].download_url
        $zip = Join-Path $env:TEMP 'zulu26-jdk.zip'
        Invoke-WebRequest $url -OutFile $zip
        $unpack = Join-Path $env:TEMP 'zulu26-unpack'
        if (Test-Path $unpack) { Remove-Item -Recurse -Force $unpack }
        Expand-Archive $zip $unpack
        $inner = Get-ChildItem $unpack -Directory | Select-Object -First 1
        New-Item -ItemType Directory -Force -Path (Split-Path $install) | Out-Null
        if (Test-Path $install) { Remove-Item -Recurse -Force $install }
        Move-Item $inner.FullName $install
    }
    if (-not (Use-JavaHome $install)) {
        throw 'Azul Zulu 26 JDK was not installed. Set JAVA_HOME to a JDK 26 home.'
    }
}

if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    $mavenRoots = @('C:\java\maven')
    foreach ($root in $mavenRoots) {
        if (-not (Test-Path $root)) { continue }
        $cmds = Get-ChildItem $root -Recurse -Filter mvn.cmd -ErrorAction SilentlyContinue | Select-Object -First 1
        if ($cmds) {
            $mvnBin = Split-Path $cmds.FullName
            $env:Path = $mvnBin + ';' + $env:Path
            break
        }
    }
}
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    throw 'Maven is not on PATH. Install Apache Maven 3.9 or newer and reopen the shell.'
}

Write-Host ('JAVA_HOME=' + $env:JAVA_HOME)
Write-Host 'java-env ready'
