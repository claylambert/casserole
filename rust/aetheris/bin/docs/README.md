# Rust Aetheris Client scripts

These scripts build the Rust Aetheris Client and copy the release binary into the folder a start script can launch. A release binary is the compiled program built for running, rather than the debug build you use while you are changing the code. Run them from the repository root, the folder that contains the top-level [pom.xml](../../../../pom.xml). The Cargo workspace is the directory `rust/aetheris`. A Cargo workspace is the Rust build file that names the packages Cargo should compile together. Cargo is the Rust build tool. A crate is one Rust package. The members are [aetheris-term](../../../../rust/aetheris/aetheris-term/Cargo.toml), the terminal library, and [aetheris-client](../../../../rust/aetheris/aetheris-client/Cargo.toml), the window.

This page documents the scripts of the Rust workspace `rust/aetheris`. The scripts call the shared Rust toolchain at the repository root. The page that explains the Rust Aetheris Client itself is [Rust Aetheris Client](../../README.md). The root toolchain page is [bin/docs/README.md](../../../../bin/docs/README.md). The binary starts at [main.rs](../../../../rust/aetheris/aetheris-client/src/main.rs). That file contains the program entry point, which is the function Rust runs when you launch the binary.

## Fast path

[app/start.ps1](../../../../rust/aetheris/app/start.ps1) on Windows and [app/start.sh](../../../../rust/aetheris/app/start.sh) on Linux are the start scripts for the Rust Aetheris Client. A start script is designed to check the environment, check the pre-build, build, bind and install, and run the Rust Aetheris Client in one operation. Check the environment selects the Rust toolchain. Check the pre-build prints the tools on one screen. Build compiles the Rust Aetheris Client. Bind and install places the binary in the app folder the start launches. The goal is to get you up and running fast.

If that start runs into trouble, walk the checklist in this section and the numbered sections on the [Rust Aetheris Client page](../../README.md#fast-path), one script at a time. The block under this heading is the command list for this directory.

Windows. The scripts are [install.ps1](../../../../rust/aetheris/bin/install.ps1) and [app/start.ps1](../../../../rust/aetheris/app/start.ps1).

```powershell
powershell -File rust\aetheris\bin\install.ps1
powershell -File rust\aetheris\app\start.ps1
```

Linux. The scripts are [install.sh](../../../../rust/aetheris/bin/install.sh) and [app/start.sh](../../../../rust/aetheris/app/start.sh).

```bash
bash rust/aetheris/bin/install.sh
bash rust/aetheris/app/start.sh
```

The start script is finished when the window prints the line `aetheris-client 0.1.0`. `0.1.0` is the version of the Rust Aetheris Client crate you just launched. The Rust Aetheris Client's own window then opens.

## build

The dev build runs the tests and compiles the Rust Aetheris Client. It is the step you use while you are changing the crate. The install section is the separate step that produces the release binary the start script launches. [build.ps1](../../../../rust/aetheris/bin/build.ps1) and [build.sh](../../../../rust/aetheris/bin/build.sh) load [rust-env.ps1](../../../../bin/rust-env.ps1) or [rust-env.sh](../../../../bin/rust-env.sh). They then, in `rust/aetheris`, run `cargo test` and `cargo build -p aetheris-client`. `cargo test` runs the crate tests. `cargo build -p aetheris-client` compiles the window crate. The workspace file is [Cargo.toml](../../../../rust/aetheris/Cargo.toml).

Windows:

```powershell
powershell -File rust\aetheris\bin\build.ps1
```

Linux:

```bash
bash rust/aetheris/bin/build.sh
```

> **What appears**
>
> Cargo's test and compile log, then the line `aetheris-rust build finished`.

The line `aetheris-rust build finished` means the dev build completed.

> **Failed line**
>
> The script throws `aetheris-rust build failed`.

The Cargo text names the crate. Fix the failure that crate names, and run the build again. On Windows the Rust Aetheris Client build reads [aetheris.jpg](../../../../src/main/resources/icons/aetheris.jpg). [build.rs](../../../../rust/aetheris/aetheris-client/build.rs) is the file that reads it. A missing file stops the build.

## install

Install is the release build, copied into `app/` so start can launch it without asking Cargo again. [install.ps1](../../../../rust/aetheris/bin/install.ps1) and [install.sh](../../../../rust/aetheris/bin/install.sh) load the Rust toolchain. They run `cargo build --release -p aetheris-client`. `--release` tells Cargo to compile the binary you run, rather than the debug binary. They copy that release binary into `rust/aetheris/app/`.

Windows:

```powershell
powershell -File rust\aetheris\bin\install.ps1
```

Linux:

```bash
bash rust/aetheris/bin/install.sh
```

> **What appears**
>
> The release build, then the line `aetheris-client installed to rust\aetheris\app`.

That line means the release binary was copied into `app`.

> **Failed line**
>
> `release build failed` means the release compile did not produce the binary.

Read the Cargo text. The binary name is `aetheris-client.exe` on Windows. The binary name is `aetheris-client` on Linux. On Windows a missing [aetheris.jpg](../../../../src/main/resources/icons/aetheris.jpg) stops the build. [build.rs](../../../../rust/aetheris/aetheris-client/build.rs) is the file that reads it.

## start

Start prints the crate version and opens the window. When the binary is not in `app/` yet, start installs it, then opens the window. [start.ps1](../../../../rust/aetheris/app/start.ps1) and [start.sh](../../../../rust/aetheris/app/start.sh) print `aetheris-client 0.1.0`. They then launch the binary in the same directory. When that binary is absent, start runs [install.ps1](../../../../rust/aetheris/bin/install.ps1) or [install.sh](../../../../rust/aetheris/bin/install.sh), then opens the window. The installer still runs alone when you call it by itself, and that call only installs.

Windows:

```powershell
powershell -File rust\aetheris\app\start.ps1
```

Linux:

```bash
bash rust/aetheris/app/start.sh
```

> **What appears**
>
> The version line, then the window.

When the binary was absent, the install output appears first, and the window opens after that copy finishes. The Casserole Server listens on port 2222. Port 2222 is the Secure Shell port the Casserole Server opens. Secure Shell, written SSH, is the encrypted login a terminal uses to reach a host. Connect to `localhost` on that port as the user `admin` with the password `demo-admin`. `localhost` means this same machine. Start the Casserole Server before the Rust Aetheris Client. The Casserole Server page is [Casserole Server](../../../../casserole-server/README.md).
