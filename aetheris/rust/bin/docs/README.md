# aetheris/rust scripts

These scripts build the Rust desktop client and copy the release binary into the folder a start script can launch. A release binary is the compiled program built for running, rather than the debug build you use while you are changing the code. Run them from the repository root, the folder that contains the top-level [pom.xml](../../../../pom.xml). The Cargo workspace is the directory `aetheris/rust`. A Cargo workspace is the Rust build file that names the packages Cargo should compile together. Cargo is the Rust build tool. A crate is one Rust package. The members are [aetheris-term](../../aetheris-term/Cargo.toml), the terminal library, and [aetheris-client](../../aetheris-client/Cargo.toml), the window.

A silo is one project directory with its own build script, its own install script, and its own start script. This page is that directory's scripts. The scripts call the shared Rust toolchain at the repository root. The page that explains the client itself is [aetheris/rust/README.md](../../README.md). The root toolchain page is [bin/docs/README.md](../../../../bin/docs/README.md). The binary starts at [main.rs](../../aetheris-client/src/main.rs). That file contains the program entry point, which is the function Rust runs when you launch the binary.

## Fast path

[app/start.ps1](../../app/start.ps1) on Windows and [app/start.sh](../../app/start.sh) on Linux are the start scripts for this client. A start script is designed to check the environment, check the pre-build, build, bind and install, and run the client in one operation. Check the environment selects the Rust toolchain. Check the pre-build prints the tools on one screen. Build compiles the client. Bind and install places the binary in the app folder the start launches. The goal is to get you up and running fast.

If that start runs into trouble, walk the checklist in this section and the numbered sections on the [client page](../../README.md#fast-path), one script at a time. The block under this heading is the command list for this directory.

Windows. The scripts are [install.ps1](../install.ps1) and [app/start.ps1](../../app/start.ps1).

```powershell
powershell -File aetheris\rust\bin\install.ps1
powershell -File aetheris\rust\app\start.ps1
```

Linux. The scripts are [install.sh](../install.sh) and [app/start.sh](../../app/start.sh).

```bash
bash aetheris/rust/bin/install.sh
bash aetheris/rust/app/start.sh
```

The start script is finished when the window prints the line `aetheris-client 0.1.0`. `0.1.0` is the version of the Rust client crate you just launched. The client's own window then opens.

## build

The dev build runs the tests and compiles the client. It is the step you use while you are changing the crate. The install section is the separate step that produces the release binary the start script launches. [build.ps1](../build.ps1) and [build.sh](../build.sh) load [rust-env.ps1](../../../../bin/rust-env.ps1) or [rust-env.sh](../../../../bin/rust-env.sh). They then, in `aetheris/rust`, run `cargo test` and `cargo build -p aetheris-client`. `cargo test` runs the crate tests. `cargo build -p aetheris-client` compiles the window crate. The workspace file is [Cargo.toml](../../Cargo.toml).

Windows:

```powershell
powershell -File aetheris\rust\bin\build.ps1
```

Linux:

```bash
bash aetheris/rust/bin/build.sh
```

> **What appears**
>
> Cargo's test and compile log, then the line `aetheris-rust build finished`.

The line `aetheris-rust build finished` means the dev build completed.

> **Failed line**
>
> The script throws `aetheris-rust build failed`.

The Cargo text names the crate. Fix the failure that crate names, and run the build again. On Windows the client build reads [aetheris.jpg](../../../java/src/main/resources/icons/aetheris.jpg). [build.rs](../../aetheris-client/build.rs) is the file that reads it. A missing file stops the build.

## install

Install is the release build, copied into `app/` so start can launch it without asking Cargo again. [install.ps1](../install.ps1) and [install.sh](../install.sh) load the Rust toolchain. They run `cargo build --release -p aetheris-client`. `--release` tells Cargo to compile the binary you run, rather than the debug binary. They copy that release binary into `aetheris/rust/app/`.

Windows:

```powershell
powershell -File aetheris\rust\bin\install.ps1
```

Linux:

```bash
bash aetheris/rust/bin/install.sh
```

> **What appears**
>
> The release build, then the line `aetheris-client installed to aetheris\rust\app`.

That line means the release binary was copied into `app`.

> **Failed line**
>
> `release build failed` means the release compile did not produce the binary.

Read the Cargo text. The binary name is `aetheris-client.exe` on Windows. The binary name is `aetheris-client` on Linux. On Windows a missing [aetheris.jpg](../../../java/src/main/resources/icons/aetheris.jpg) stops the build. [build.rs](../../aetheris-client/build.rs) is the file that reads it.

## start

Start prints the crate version and opens the window. When the binary is not in `app/` yet, start installs it, then opens the window. [start.ps1](../../app/start.ps1) and [start.sh](../../app/start.sh) print `aetheris-client 0.1.0`. They then launch the binary in the same directory. When that binary is absent, start runs [install.ps1](../install.ps1) or [install.sh](../install.sh), then opens the window. The installer still runs alone when you call it by itself, and that call only installs.

Windows:

```powershell
powershell -File aetheris\rust\app\start.ps1
```

Linux:

```bash
bash aetheris/rust/app/start.sh
```

> **What appears**
>
> The version line, then the window.

When the binary was absent, the install output appears first, and the window opens after that copy finishes. The server listens on port 2222. Port 2222 is the Secure Shell port the server opens. Secure Shell, written SSH, is the encrypted login a terminal uses to reach a host. Connect to `localhost` on that port as the user `admin` with the password `demo-admin`. `localhost` means this same machine. Start the server before this client. The server page is [casserole-server/README.md](../../../../casserole-server/README.md).
