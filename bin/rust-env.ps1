<#
.SYNOPSIS
    Make Rust 1.99.1 and cargo runnable.

.DESCRIPTION
    The user cargo bin is put on Path when that directory exists, so a
    rustup installed for this user is visible before the script looks for
    it. A rustup that is already installed is the one that runs.

    When rustup is missing, the script downloads https://win.rustup.rs/x86_64
    and runs rustup-init -y --default-toolchain 1.99.1. A non-zero exit
    throws rustup-init failed. When cargo is still missing after that, the
    script throws and asks for a new shell. It does not keep going with a
    cargo it cannot see.

    The pin is rustup run 1.99.1 rustc --version. The decision uses the
    exit code only. The version text is printed. It is not part of the
    value that is tested. A test of the text would treat a working compiler
    as a failure, because the text is not the number 0.

    A pin that does not exit 0 prints the tool text and then heals channel
    1.99.1 only. The stable toolchain is left installed. Each heal step has
    its own failure:

        toolchain 1.99.1 did not uninstall
        toolchain 1.99.1 did not install
        toolchain 1.99.1 did not become the default

    The pin runs a second time. A second miss throws
    toolchain 1.99.1 still will not run. rust-env ready is printed only
    after the pin has exited 0. cargo --version then has to exit 0.
    A non-zero cargo throws cargo --version failed.

    rustup, rustc, and cargo write on the error stream. Under Stop,
    PowerShell 5.1 turns that stream into a terminating error even when
    the tool exits 0. Invoke-Quiet and Invoke-Stream lower Stop, stringify
    each record, and restore Stop. Each function returns one object with
    Code and Text. Write-Output of the tool text from inside the function
    would become part of the return value.

.NOTES
    Invoke-Stream prints each line with Write-Host so the operator sees the
    heal while the function still returns one object.
#>
$ErrorActionPreference = "Stop"

# A per-user install puts rustup and cargo here. Prepend it when it exists
# so this process sees them without a new shell.
$cargoBin = Join-Path $env:USERPROFILE ".cargo\bin"
if (Test-Path -LiteralPath $cargoBin) {
    $env:Path = $cargoBin + ";" + $env:Path
}

# Run a native command and return one object: Code is the exit code, Text
# is the combined output. The caller tests Code. Text is for printing.
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

# Same capture as Invoke-Quiet, and each line is shown as it arrives.
# Write-Host keeps those lines out of the returned object.
function Invoke-Stream {
    param([scriptblock]$Command)
    $old = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    $parts = New-Object System.Collections.Generic.List[string]
    try {
        & $Command 2>&1 | ForEach-Object {
            $line = [string]$_
            [void]$parts.Add($line)
            Write-Host $line
        }
        $code = $LASTEXITCODE
    } finally {
        $ErrorActionPreference = $old
    }
    return [pscustomobject]@{ Code = $code; Text = ($parts -join "`n") }
}

# Install rustup only when the command is absent. The default channel is 1.99.1.
if (-not (Get-Command rustup -ErrorAction SilentlyContinue)) {
    $init = Join-Path $env:TEMP "rustup-init.exe"
    Invoke-WebRequest "https://win.rustup.rs/x86_64" -OutFile $init
    $setup = Invoke-Stream { & $init -y --default-toolchain 1.99.1 }
    if ($setup.Code -ne 0) { throw "rustup-init failed" }
    $env:Path = $cargoBin + ";" + $env:Path
}
if (-not (Get-Command cargo -ErrorAction SilentlyContinue)) {
    throw "cargo is not on PATH after rustup. Open a new shell and run this script again."
}

# The pin value is the exit code. A working compiler prints rustc 1.99.1
# and exits 0. That text must not be treated as a failed comparison.
$pin = Invoke-Quiet { rustup run 1.99.1 rustc --version }
if ($pin.Code -ne 0) {
    if ($pin.Text) { Write-Output $pin.Text }
    Write-Output "toolchain 1.99.1 will not run. Removing it and installing it again."
    # Each step names itself. A failed uninstall is not reported as a failed pin.
    $removed = Invoke-Stream { rustup toolchain uninstall 1.99.1 }
    if ($removed.Code -ne 0) { throw "toolchain 1.99.1 did not uninstall" }
    $installed = Invoke-Stream { rustup toolchain install 1.99.1 }
    if ($installed.Code -ne 0) { throw "toolchain 1.99.1 did not install" }
    $defaulted = Invoke-Stream { rustup default 1.99.1 }
    if ($defaulted.Code -ne 0) { throw "toolchain 1.99.1 did not become the default" }
    $again = Invoke-Quiet { rustup run 1.99.1 rustc --version }
    if ($again.Text) { Write-Output $again.Text }
    if ($again.Code -ne 0) { throw "toolchain 1.99.1 still will not run" }
} elseif ($pin.Text) {
    Write-Output $pin.Text
}

# Ready is printed only after the pin exited 0. cargo is checked after that.
Write-Output "rust-env ready"
$cargo = Invoke-Quiet { cargo --version }
if ($cargo.Text) { Write-Output $cargo.Text }
if ($cargo.Code -ne 0) { throw "cargo --version failed" }
