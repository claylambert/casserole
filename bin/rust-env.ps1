$ErrorActionPreference = 'Stop'
$channel = '1.99.1'
$cargoBin = Join-Path $env:USERPROFILE '.cargo\bin'
$env:Path = $cargoBin + ';' + $env:Path
if (-not (Get-Command rustup -ErrorAction SilentlyContinue)) {
    $rustup = Join-Path $cargoBin 'rustup-init.exe'
    if (-not (Test-Path $rustup)) {
        $rustup = Join-Path $env:TEMP 'rustup-init.exe'
        Invoke-WebRequest 'https://win.rustup.rs/x86_64' -OutFile $rustup
    }
    & $rustup -y --default-toolchain $channel
    if ($LASTEXITCODE -ne 0) { throw 'rustup-init failed' }
    $env:Path = $cargoBin + ';' + $env:Path
}
if (-not (Get-Command cargo -ErrorAction SilentlyContinue)) {
    throw 'cargo is not on PATH after rustup. Open a new shell and run this script again.'
}
& rustup run $channel rustc --version > $null
if ($LASTEXITCODE -ne 0) {
    Write-Host "toolchain $channel will not run. Removing it and installing it again."
    & rustup toolchain uninstall $channel
    if ($LASTEXITCODE -ne 0) { throw "toolchain $channel did not uninstall" }
    & rustup toolchain install $channel
    if ($LASTEXITCODE -ne 0) { throw "toolchain $channel did not install" }
    & rustup default $channel
    if ($LASTEXITCODE -ne 0) { throw "toolchain $channel did not become the default" }
    & rustup run $channel rustc --version > $null
    if ($LASTEXITCODE -ne 0) { throw "toolchain $channel still will not run" }
}
Write-Host 'rust-env ready'
& cargo --version
if ($LASTEXITCODE -ne 0) { throw 'cargo --version failed' }
