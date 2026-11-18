<#
.SYNOPSIS
    Remove the Rust Aetheris Client build so the next build starts clean.

.DESCRIPTION
    This file lives in rust/aetheris/bin. Three directories up is the
    repository root. The script removes the installed binary from
    rust/aetheris/app, then removes the compiled workspace under
    rust/aetheris/target. That directory holds the debug binary, the
    release binary, and the compiled dependencies. The same removal
    covers rust/aetheris/aetheris-client/target and
    rust/aetheris/aetheris-term/target when those directories exist.

    When the Rust toolchain script can select Cargo, the script also
    runs cargo clean in the workspace. cargo clean is Cargo's own
    removal of that compiled tree. A missing Cargo still leaves the
    directories removed. The Rust compiler, rustup, and the downloaded
    crate cache on the machine stay. Cargo.lock stays. The next install
    compiles the client again.

    Success prints aetheris-client reset finished.
#>
$ErrorActionPreference = "Stop"

# rust/aetheris/bin -> aetheris -> rust -> repository root.
$root = Resolve-Path (Join-Path $PSScriptRoot "..\..\..")
$ws = Join-Path $root "rust\aetheris"
$app = Join-Path $ws "app"

# A missing file is already reset.
foreach ($name in @("aetheris-client.exe", "aetheris-client")) {
    $path = Join-Path $app $name
    if (Test-Path -LiteralPath $path) {
        Remove-Item -LiteralPath $path -Force
    }
}

# Cargo's removal when the pinned toolchain is present. Progress stays
# on the screen. A failure here does not keep the compiled tree.
$old = $ErrorActionPreference
try {
    . (Join-Path $root "bin\rust-env.ps1")
    Set-Location $ws
    $ErrorActionPreference = "Continue"
    & cargo clean 2>&1 | ForEach-Object { Write-Host ([string]$_) }
} catch {
    Write-Host "cargo clean was not run"
} finally {
    $ErrorActionPreference = $old
}

# The directories cargo clean removes, and any copy left beside a crate.
foreach ($rel in @("target", "aetheris-client\target", "aetheris-term\target")) {
    $dir = Join-Path $ws $rel
    if (Test-Path -LiteralPath $dir) {
        Remove-Item -LiteralPath $dir -Recurse -Force
    }
}

Write-Output "aetheris-client reset finished"
