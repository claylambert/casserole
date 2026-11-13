$ErrorActionPreference = "Stop"
function Invoke-Pin {
    & rustup run 1.99.1 rustc --version
    return $LASTEXITCODE
}
if (-not (Get-Command rustup -ErrorAction SilentlyContinue)) {
    $init = Join-Path $env:TEMP "rustup-init.exe"
    Invoke-WebRequest "https://win.rustup.rs/x86_64" -OutFile $init
    & $init -y --default-toolchain 1.99.1
    $cargoHome = Join-Path $env:USERPROFILE ".cargo\bin"
    $env:Path = $cargoHome + ";" + $env:Path
}
if (-not (Get-Command cargo -ErrorAction SilentlyContinue)) {
    throw "cargo is not on PATH after rustup. Open a new shell and run this script again."
}
if ((Invoke-Pin) -ne 0) {
    Write-Output "toolchain 1.99.1 will not run. Removing it and installing it again."
    & rustup toolchain uninstall 1.99.1
    & rustup toolchain install 1.99.1
    & rustup default 1.99.1
    if ((Invoke-Pin) -ne 0) { throw "toolchain 1.99.1 still will not run" }
}
Write-Output "rust-env ready"
& cargo --version
if ($LASTEXITCODE -ne 0) { throw "cargo --version failed" }
