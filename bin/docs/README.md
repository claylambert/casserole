# Scripts

This page documents every script in the repository. A script is one pair. Windows 11 runs the `.ps1` file in Windows PowerShell. Linux runs the `.sh` file in Bash. The two files share the steps except where a section names a difference. Each section names the script, each way to run it, the finished line, and the failed line.

The shared scripts live in `bin/` at the repository root, the folder that contains [pom.xml](../../pom.xml). Maven, which is the Java build tool, reads that file when a Java build runs from that folder. Those scripts are [java-env.ps1](../java-env.ps1), [java-env.sh](../java-env.sh), [rust-env.ps1](../rust-env.ps1), [rust-env.sh](../rust-env.sh), [pre-build-check.ps1](../pre-build-check.ps1), and [pre-build-check.sh](../pre-build-check.sh). They select the Java Development Kit and the Rust compiler, then print whether those tools, Maven, and Ollama are present.

The Casserole Server scripts build that program, copy its jar, prepare the local model, and start it. A jar is the packaged Java program. Those scripts are [bin/build.ps1](../build.ps1), [bin/build.sh](../build.sh), [bin/install.ps1](../install.ps1), [bin/install.sh](../install.sh), [bin/install-ollama.ps1](../install-ollama.ps1), [bin/install-ollama.sh](../install-ollama.sh), [app/start-casserole.ps1](../../app/start-casserole.ps1), and [app/start-casserole.sh](../../app/start-casserole.sh).

The Java Aetheris Client scripts build that program, copy its fat jar, and start it. Those scripts are [bin/build.ps1](../build.ps1), [bin/build.sh](../build.sh), [bin/install.ps1](../install.ps1), [bin/install.sh](../install.sh), [app/start-aetheris.ps1](../../app/start-aetheris.ps1), and [app/start-aetheris.sh](../../app/start-aetheris.sh).

The Rust Aetheris Client scripts build that program, copy the release binary, and start it. A release binary is the compiled program built for running, rather than the debug build you use while you are changing the code. Those scripts are [rust/aetheris/bin/build.ps1](../../rust/aetheris/bin/build.ps1), [rust/aetheris/bin/build.sh](../../rust/aetheris/bin/build.sh), [rust/aetheris/bin/install.ps1](../../rust/aetheris/bin/install.ps1), [rust/aetheris/bin/install.sh](../../rust/aetheris/bin/install.sh), [rust/aetheris/app/start.ps1](../../rust/aetheris/app/start.ps1), and [rust/aetheris/app/start.sh](../../rust/aetheris/app/start.sh).

A JDK is the Java development kit, the set of tools that compile and run Java. Ollama is the program that runs a language model on this computer. That program is the local engine. A chat can also use the Grok API or Grok Build ACP via the pool. The Grok API is Grok's hosted programming interface. Grok Build ACP via the pool is Grok Build reached through the Agent Client Protocol. ACP is that protocol. The pool is the agent connection for that protocol. The Casserole Server opens that connection. The Aetheris Client is the Sovereign Desktop in this repository. That Aetheris Client is the Java program or the Rust program. One Aetheris Client carries more than one conversation at the same time. Those conversations stay unified on that desktop. The desktop can use more than one of those models at the same time. Cargo is the Rust build tool.

The path that uses them in order, from a fresh clone through a running Aetheris Client, is the [root page](../../README.md). Those steps take about thirty minutes from a fresh clone. Opening the same tree in IntelliJ is [IntelliJ IDEA](../../README.md#intellij-idea) on that page.

![Pre-build check, then the model, then the Casserole Server, then one Aetheris Client](../../figures/02-first-hour.png)

*The pre-build check reports the tools. The model, the Casserole Server, and each Aetheris Client are built by the scripts on this page.*

## Fast path

The start script for each program is the convenience. It is designed to check the environment, check the pre-build, build, bind and install, and run that program in one operation. Bind and install copies the built program into the folder the start script launches. The goal is to get you up and running fast. The Casserole Server start is [app/start-casserole.ps1](../../app/start-casserole.ps1) or [app/start-casserole.sh](../../app/start-casserole.sh). The Java Aetheris Client start is [app/start-aetheris.ps1](../../app/start-aetheris.ps1) or [app/start-aetheris.sh](../../app/start-aetheris.sh). The Rust Aetheris Client start is [rust/aetheris/app/start.ps1](../../rust/aetheris/app/start.ps1) or [rust/aetheris/app/start.sh](../../rust/aetheris/app/start.sh).

If a start runs into trouble, walk the checklist on the [root page](../../README.md#fast-path) and the sections on this page. The block under this heading is the toolchain check, with nothing written between the commands. The sections below document every script. Compare the window with the quoted line after each script.

Windows 11, from the repository root. The scripts are [java-env.ps1](../java-env.ps1), [rust-env.ps1](../rust-env.ps1), and [pre-build-check.ps1](../pre-build-check.ps1).

```powershell
powershell -File bin\java-env.ps1
powershell -File bin\rust-env.ps1
powershell -File bin\pre-build-check.ps1
```

Linux, from the repository root. The scripts are [java-env.sh](../java-env.sh), [rust-env.sh](../rust-env.sh), and [pre-build-check.sh](../pre-build-check.sh).

```bash
source bin/java-env.sh
source bin/rust-env.sh
bash bin/pre-build-check.sh
```

Source means the shell runs the file and keeps the values the file set. The pre-build check is finished when the window shows the line `pre-build-check finished`.

The Casserole Server start is the window you leave open. In a second window, start the Java Aetheris Client or the Rust Aetheris Client. The sections for each build, each install, `install-ollama`, and each start are below.

Windows, from the repository root:

```powershell
powershell -File app\start-casserole.ps1
```

```powershell
powershell -File app\start-aetheris.ps1
```

```powershell
powershell -File rust\aetheris\app\start.ps1
```

Linux, from the repository root:

```bash
bash app/start-casserole.sh
```

```bash
bash app/start-aetheris.sh
```

```bash
bash rust/aetheris/app/start.sh
```

## java-env

Run this script before any Java build, because the later scripts want a JDK they can name. [java-env.ps1](../java-env.ps1) and [java-env.sh](../java-env.sh) select a Java 26 JDK and then require Maven. `JAVA_HOME` is the folder where that JDK is installed. A home counts only when it contains `java` and the compiler `javac`, and when that home is Java 26. The script reads the `release` file in that folder when the file is present. The version line in that file must start at 26. When the `release` file is absent, the script reads `java -version`. That text must say version 26.

A Java 26 JDK already named by `JAVA_HOME` stays. A trailing slash on that folder is ignored. An older JDK named by `JAVA_HOME`, or an older `java` earlier on `PATH`, is set aside. The script then looks along `PATH` for a directory that contains `java`, and it tests the parent of that directory as a JDK home. When that search misses, Windows looks for a directory whose name starts with `zulu26` or `jdk-26` under `C:\java\Java`, `C:\Program Files\Zulu`, `C:\Program Files\Java`, and `%LOCALAPPDATA%\casserole`. Linux looks under `/usr/lib/jvm` and `~/.local/share/casserole`. A Java 26 JDK already unpacked at `%LOCALAPPDATA%\casserole\zulu26` on Windows, or at `~/.local/share/casserole/zulu26` on Linux, is that home.

When none of those homes is Java 26, the script downloads Azul Zulu 26. Azul Zulu 26 is the JDK build the script downloads. The download is a JDK, because Maven compiles with `javac`. Windows downloads a zip. Linux downloads a tar.gz. The download is the latest general-availability build for that operating system on x64. The unpack lands in the `zulu26` folder named above. The script sets `JAVA_HOME` to that JDK and places that folder's `bin` directory at the front of `PATH`, so the `java` command in this process is Java 26.

Maven is required after the JDK is selected. When `mvn` is already on `PATH`, that Maven stays. On Windows, when `mvn` is missing, the script looks under `C:\java\maven` for `mvn.cmd` and places the folder that holds `mvn.cmd` at the front of `PATH`. Linux does not look in that folder. When `mvn` is still missing, the script stops.

Windows:

```powershell
powershell -File bin\java-env.ps1
```

Linux. Source the script so the shell keeps `JAVA_HOME`.

```bash
source bin/java-env.sh
```

> **What appears**
>
> A line `JAVA_HOME=` followed by the JDK folder the script selected, then the line `java-env ready`.

The line `JAVA_HOME=` is the folder the script selected. The line `java-env ready` means the script finished. On Windows this script runs as its own process, so the `JAVA_HOME` it set ends when that process ends. A later install loads the toolchain again in its own window. On Linux you source the script, and the shell that sourced it keeps `JAVA_HOME` for the commands you type next in that same window. A build or a start that loads this script keeps `JAVA_HOME` for that process.

> **Failed line**
>
> `Azul Zulu 26 JDK was not installed. Set JAVA_HOME to a JDK 26 home.` means the download did not leave a Java 26 JDK.

Set `JAVA_HOME` to a Java 26 JDK that contains `javac`, and run the script again.

> **Failed line**
>
> `Maven is not on PATH. Install Apache Maven 3.9 or newer and reopen the shell.` means Maven is missing.

Install Apache Maven 3.9 or newer and open a new shell. The script sets the toolchain for the process that loaded it. Each project install loads the toolchain again.

## rust-env

The Rust Aetheris Client will not compile until Cargo can see the compiler this repository pins. [rust-env.ps1](../rust-env.ps1) and [rust-env.sh](../rust-env.sh) require Cargo. The channel is `1.99.1`. A pin is a file that names the exact compiler a directory expects. The pin file is [rust/aetheris/rust-toolchain.toml](../../rust/aetheris/rust-toolchain.toml). The channel written in that file is the channel these scripts install and test. rustup is the installer that places the Rust compiler and Cargo on the machine.

When rustup is already on `PATH`, the script does not install it again. A stable toolchain that is already on the machine stays installed. When rustup is missing, the script installs rustup and selects `1.99.1` as the default toolchain. On Windows the script looks for `rustup-init.exe` in `%USERPROFILE%\.cargo\bin`. When that file is already there, the script runs it. When that file is absent, the script downloads `rustup-init.exe` and runs it. On Linux the script downloads the rustup installer and runs it, then loads `$HOME/.cargo/env` so this shell can see Cargo.

Before the script prints `rust-env ready`, it runs `rustup run 1.99.1 rustc --version`. A compiler that exits 0 stays. A compiler that does not exit 0 will not run. The script prints `toolchain 1.99.1 will not run. Removing it and installing it again.` It uninstalls channel `1.99.1`, installs that channel, and sets that channel as the default. It runs `rustup run 1.99.1 rustc --version` again. On Windows a failed uninstall throws `toolchain 1.99.1 did not uninstall`. A failed install throws `toolchain 1.99.1 did not install`. A failed default throws `toolchain 1.99.1 did not become the default`. A second miss throws `toolchain 1.99.1 still will not run`. On Linux the script stops on the rustup command that failed.

`rust-env ready` is printed only after that pin runs. The script then runs `cargo --version`.

Windows:

```powershell
powershell -File bin\rust-env.ps1
```

Linux:

```bash
source bin/rust-env.sh
```

> **What appears**
>
> The line `rust-env ready`, and a line that prints the Cargo version.

The line `rust-env ready` means the script finished and Cargo can see the pinned compiler.

> **Failed line**
>
> `rustup-init failed` means the Windows installer did not finish.

Open a new shell and run the script again.

> **Failed line**
>
> `cargo is not on PATH after rustup. Open a new shell and run this script again.` means the install landed and this window cannot see it yet.

Open a new shell and run the script again.

> **Failed line**
>
> `toolchain 1.99.1 still will not run` means the second install of that channel still does not compile.

The lines `toolchain 1.99.1 did not uninstall`, `toolchain 1.99.1 did not install`, and `toolchain 1.99.1 did not become the default` name the rustup step that failed. On Windows `cargo --version failed` means the version command after `rust-env ready` did not exit 0. On Linux the script stops on that command.

## pre-build-check

The pre-build check is the screen you read before you spend a build. It does not install the product. It tells you which tool is already here. [pre-build-check.ps1](../pre-build-check.ps1) and [pre-build-check.sh](../pre-build-check.sh) load both toolchain scripts first. A failure in `java-env` or in `rust-env` stops the screen. The screen then prints one line each for JDK, Maven, Cargo, rustc, and Ollama. rustc is the Rust compiler that Cargo calls.

The JDK line is `JDK` and the `JAVA_HOME` folder. The Maven line is `Maven` and the path of `mvn`, or `Maven` and `missing`. The Cargo line is `Cargo` and the Cargo version, or `Cargo` and `missing`. The rustc line is `rustc` and the compiler version, or `rustc` and `missing`. When Ollama is present, the line is `Ollama  present`, and the next lines are the output of `ollama list`. `ollama list` is the list of models already pulled on this machine. When Ollama is missing, Windows prints `Ollama  missing. Run bin\install-ollama.ps1`. Linux prints `Ollama  missing. Run bin/install-ollama.sh`. Those scripts install the Ollama engine and pull an Ollama model. They do not install the Grok API or Grok Build ACP via the pool.

Windows:

```powershell
powershell -File bin\pre-build-check.ps1
```

Linux:

```bash
bash bin/pre-build-check.sh
```

> **What appears**
>
> A line for each tool, then the line `pre-build-check finished`.

The line `pre-build-check finished` means the screen is complete.

> **Failed line**
>
> A toolchain line says `missing`.

Run the matching env script, install the tool that line names, and run the pre-build check again. A throw from `java-env` or from `rust-env` is that script's failed line, printed before this screen.

## Casserole Server build

The build asks Maven to package the one module `casserole`. The install step is the separate step that copies the fat jar to `app/casserole.jar`. [build.ps1](../build.ps1) and [build.sh](../build.sh) load [java-env.ps1](../java-env.ps1) or [java-env.sh](../java-env.sh). They then run `mvn package` from the repository root. There is no `-pl` module list. The reactor file is [pom.xml](../../pom.xml). Maven reads that file to compile the module, run its tests, and package the jar.

Windows:

```powershell
powershell -File bin\build.ps1
```

Linux:

```bash
bash bin/build.sh
```

> **What appears**
>
> Maven's compile and test log, then the line `casserole build finished`.

The line `casserole build finished` means the package step completed. The jar then exists for the install step to copy.

> **Failed line**
>
> The script throws `casserole build failed`.

The Maven text above that line names the failure. On Linux the script stops when Maven exits with a failure, and it does not print that throw. Fix that failure, and run the build again.

## Casserole Server install

Install is the build, plus the copy that puts the jar where the Casserole Server start looks. [install.ps1](../install.ps1) and [install.sh](../install.sh) run the build. They then copy the fat jar to `app/casserole.jar`. A jar whose name contains `original` is the plain jar, and install does not copy it.

Windows:

```powershell
powershell -File bin\install.ps1
```

Linux:

```bash
bash bin/install.sh
```

> **What appears**
>
> The build output, then the line `casserole installed to app`.

That line means the jar was copied into `app`.

> **Failed line**
>
> `casserole jar was not produced` means the build did not write the jar.

Run the build and read the Maven result.

## install-ollama

This script installs the local Ollama model. The Casserole Server can also answer through the Grok API or through Grok Build ACP via the pool. The Grok API is Grok's hosted programming interface. Grok Build ACP via the pool is Grok Build reached through the Agent Client Protocol. ACP is that protocol. The pool is the agent connection for that protocol. The Casserole Server opens that connection. The Aetheris Client is the Sovereign Desktop in this repository. That Aetheris Client is the Java program or the Rust program. One Aetheris Client carries more than one conversation at the same time. Those conversations stay unified on that desktop. The desktop can use more than one of those models at the same time. [install-ollama.ps1](../install-ollama.ps1) and [install-ollama.sh](../install-ollama.sh) install the Ollama engine when it is missing. Ollama is the program that runs a language model on this computer. The script then pulls a model. With no argument the model is `qwen2.5-coder:7b`. That name is the Ollama model used when you do not name another Ollama model. With a model name, the script pulls that model. Run the script again when you want another model.

When the `ollama` command is missing, Windows downloads `OllamaSetup.exe` into the temporary folder, prints the two lines below, and exits. `OllamaSetup.exe` is the Windows installer for Ollama. Linux runs the Ollama install script from `https://ollama.com/install.sh`. That install needs a network and permission to install. After the engine is present, both scripts pull the model.

Windows:

```powershell
powershell -File bin\install-ollama.ps1
powershell -File bin\install-ollama.ps1 llama3.2
```

The second command is the same script with the model name `llama3.2`.

Linux:

```bash
bash bin/install-ollama.sh
bash bin/install-ollama.sh llama3.2
```

> **What appears**
>
> `ollama pull` and the model name, then the line `install-ollama finished`.

The line `install-ollama finished` means the pull completed.

> **Failed line on Windows**
>
> `Ollama installer downloaded. Run it, reopen the shell, then run this script again.`

The next line is the path of `OllamaSetup.exe`. Run that installer, open a new PowerShell, and run the script again.

> **Failed line**
>
> On Windows a failed pull throws `ollama pull failed`.

On Linux the script stops when the pull exits with a failure, and it does not print that throw. After the engine is installed, run the script again.

## Casserole Server start

Start is the line you leave open. The log in that window is the Casserole Server, waiting for a login. [start-casserole.ps1](../../app/start-casserole.ps1) and [start-casserole.sh](../../app/start-casserole.sh) load [java-env.ps1](../java-env.ps1) or [java-env.sh](../java-env.sh). They print `casserole-server 1.0.0`. `1.0.0` is the build identifier of the Casserole Server. They then run `java -jar app/casserole.jar` with the repository root as the working directory. `java -jar` launches the packaged program. PropertiesLauncher is the Spring Boot class named in the fat jar manifest. PropertiesLauncher reads the manifest start class `org.casserole.CasseroleApplication`. The class the jar starts is [CasseroleApplication](../../src/main/java/org/casserole/CasseroleApplication.java). When `app/casserole.jar` is absent, start runs the install, then starts. The same start runs install-ollama when Ollama is missing or the default model `qwen2.5-coder:7b` is not pulled. The installer still runs alone when you call it by itself, and that call only installs. [UserAccounts](../../src/main/java/org/casserole/config/UserAccounts.java) reads the roster from the jar when no `users.yaml` sits in the working directory and none sits under `~/.casserole/`.

Windows:

```powershell
powershell -File app\start-casserole.ps1
```

Linux:

```bash
bash app/start-casserole.sh
```

> **What appears**
>
> The version line, then the Casserole Server log.

The Casserole Server listens on port 2222. When the jar was absent, the install output appears first, and the log follows after the Casserole Server starts. The first start creates `hostkey.ser` in the working directory. `hostkey.ser` is the host key the SSH login uses, and that file stays on your machine. If port 2222 is already taken, another process is already listening there. Stop that other listener and start again.

> **Failed line**
>
> `app/casserole.jar is missing.` means the install did not leave the jar.

Run the Casserole Server install and read the Maven result.

## Java Aetheris Client build

The Java Aetheris Client is built by the same scripts as the Casserole Server. The one module is `casserole`. [build.ps1](../build.ps1) and [build.sh](../build.sh) load [java-env.ps1](../java-env.ps1) or [java-env.sh](../java-env.sh). They then run `mvn package` from the repository root. There is no `-pl` module list. The reactor file is [pom.xml](../../pom.xml).

Windows:

```powershell
powershell -File bin\build.ps1
```

Linux:

```bash
bash bin/build.sh
```

> **What appears**
>
> Maven's compile and test log, then the line `casserole build finished`.

The line `casserole build finished` means the package step completed. The fat jar then exists for the install step to copy.

> **Failed line**
>
> The script throws `casserole build failed`.

The Maven text above that line names the failure. On Linux the script stops when Maven exits with a failure, and it does not print that throw. Fix that failure, and run the build again.

## Java Aetheris Client install

Install is the build, plus the copy that puts the fat jar where the Java Aetheris Client start looks. [install.ps1](../install.ps1) and [install.sh](../install.sh) run the build. They then copy the fat jar to `app/casserole.jar`. There is no `app/lib`. A jar whose name contains `original` is the plain jar, and install does not copy it.

Windows:

```powershell
powershell -File bin\install.ps1
```

Linux:

```bash
bash bin/install.sh
```

> **What appears**
>
> The build output, then the line `casserole installed to app`.

That line means the fat jar was copied into `app`.

> **Failed line**
>
> `casserole jar was not produced` means the build did not write the jar.

Run the build and read the Maven result.

## Java Aetheris Client start

Start prints the build identifier and opens the Java Aetheris Client window. When the fat jar is missing, it installs it first, then opens the window. [start-aetheris.ps1](../../app/start-aetheris.ps1) and [start-aetheris.sh](../../app/start-aetheris.sh) load [java-env.ps1](../java-env.ps1) or [java-env.sh](../java-env.sh). They print `aetheris-java 1.0.0`. `1.0.0` is the build identifier of the Java Aetheris Client. They then run `java -Dloader.main=org.casserole.app.ui.ssh.SshClientApp -jar app/casserole.jar` with the repository root as the working directory. `loader.main` names the class PropertiesLauncher starts. The manifest start class stays [CasseroleApplication](../../src/main/java/org/casserole/CasseroleApplication.java). The class the window opens is [SshClientApp](../../src/main/java/org/casserole/app/ui/ssh/SshClientApp.java). When `app/casserole.jar` is absent, start runs the install, then opens the window. This start does not pull Ollama. The installer still runs alone when you call it by itself, and that call only installs.

Windows:

```powershell
powershell -File app\start-aetheris.ps1
```

Linux:

```bash
bash app/start-aetheris.sh
```

> **What appears**
>
> The version line, then the Java Aetheris Client window.

When the jar was absent, the install output appears first, and the window opens after that copy finishes. The Casserole Server listens on port 2222. Port 2222 is the Secure Shell port the Casserole Server opens. Secure Shell, written SSH, is the encrypted login a terminal uses to reach a host. Connect to `localhost` on that port as the user `admin` with the password `demo-admin`. `localhost` means this same machine. Start the Casserole Server before this Java Aetheris Client. The Casserole Server page is [Casserole Server](../../casserole-server/README.md). Import [demo/first-canvas.toon](../../demo/first-canvas.toon) after you are connected. The file is a version-1 canvas export with three cells. The cells are a hello, an air-gap question, and an empty next prompt. The vault file stays on your machine.

> **Failed line**
>
> `casserole jar was not produced` means the install did not leave the fat jar.

Run the Java Aetheris Client install and read the Maven result.

## Rust Aetheris Client build

The dev build runs the tests and compiles the Rust Aetheris Client. It is the step you use while you are changing the crate. A crate is one Rust package. The install section is the separate step that produces the release binary the start script launches. [build.ps1](../../rust/aetheris/bin/build.ps1) and [build.sh](../../rust/aetheris/bin/build.sh) load [rust-env.ps1](../rust-env.ps1) or [rust-env.sh](../rust-env.sh). They then, in `rust/aetheris`, run `cargo test` and `cargo build -p aetheris-client`. `cargo test` runs the crate tests. `cargo build -p aetheris-client` compiles the window crate. The workspace file is [Cargo.toml](../../rust/aetheris/Cargo.toml). The workspace members are [aetheris-term](../../rust/aetheris/aetheris-term/Cargo.toml), the terminal library, and [aetheris-client](../../rust/aetheris/aetheris-client/Cargo.toml), the window. On Windows, when `cargo test` fails, the script does not run `cargo build`. On Linux the script stops at the test command.

On Windows the build reads [aetheris.jpg](../../src/main/resources/icons/aetheris.jpg). [build.rs](../../rust/aetheris/aetheris-client/build.rs) is the file that reads it. The path from that crate is `../../../src/main/resources/icons/aetheris.jpg`. The script writes the picture into the Windows executable so a taskbar pin keeps it. It compiles that picture with `rc.exe` from the Windows SDK. A missing picture stops the build. A missing `rc.exe` stops the build. Linux returns before that picture is read.

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
> On Windows the script throws `aetheris-rust build failed`.

The Cargo text names the crate. On Linux the script stops when `cargo test` or `cargo build` exits with a failure, and it does not print that throw. Fix the failure that crate names, and run the build again.

## Rust Aetheris Client install

Install is the release build, copied into `rust/aetheris/app/` so the start can launch it without asking Cargo again. [install.ps1](../../rust/aetheris/bin/install.ps1) and [install.sh](../../rust/aetheris/bin/install.sh) load the Rust toolchain. They run `cargo build --release -p aetheris-client` in `rust/aetheris`. `--release` tells Cargo to compile the binary you run, rather than the debug binary. Windows looks for `rust/aetheris/target/release/aetheris-client.exe`, then for `rust/aetheris/target/release/aetheris-client`. Linux copies `rust/aetheris/target/release/aetheris-client`. The copy lands in `rust/aetheris/app/` under the same file name. The Windows icon step in the build section runs for this release compile too.

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
> The release build, then the line `aetheris-client installed to rust\aetheris\app` on Windows, or `aetheris-client installed to rust/aetheris/app` on Linux.

That line means the release binary was copied into `rust/aetheris/app/`.

> **Failed line**
>
> On Windows `release build failed` means the release compile did not produce the binary.

Read the Cargo text. The binary name is `aetheris-client.exe` on Windows. The binary name is `aetheris-client` on Linux. On Linux the script stops when the release compile or the copy fails, and it does not print that throw. On Windows a missing [aetheris.jpg](../../src/main/resources/icons/aetheris.jpg) stops the build. [build.rs](../../rust/aetheris/aetheris-client/build.rs) is the file that reads it.

## Rust Aetheris Client start

Start prints the crate version and opens the Rust Aetheris Client window. When the binary is not in `rust/aetheris/app/` yet, start installs it, then opens the window. [start.ps1](../../rust/aetheris/app/start.ps1) and [start.sh](../../rust/aetheris/app/start.sh) print `aetheris-client 1.0.0`. `1.0.0` is the version of the Rust Aetheris Client crate. Windows looks for `aetheris-client.exe` in that folder, then for `aetheris-client`. Linux looks for an executable file named `aetheris-client` in that folder. When the file is absent, start runs the Rust Aetheris Client install, then looks again. When the file is still absent, the script stops. When the file is present, start launches that binary. This start does not load `rust-env` itself. The install loads `rust-env`. The check that `rustc` 1.99.1 exits 0 stays in that toolchain script. The installer still runs alone when you call it by itself, and that call only installs.

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
> The version line, then the Rust Aetheris Client window.

When the binary was absent, the install output appears first, and the window opens after that copy finishes. The Casserole Server listens on port 2222. Port 2222 is the Secure Shell port the Casserole Server opens. Secure Shell, written SSH, is the encrypted login a terminal uses to reach a host. Connect to `localhost` on that port as the user `admin` with the password `demo-admin`. `localhost` means this same machine. Start the Casserole Server before the Rust Aetheris Client. The Casserole Server page is [Casserole Server](../../casserole-server/README.md).

> **Failed line**
>
> `aetheris-client is missing.` means the install did not leave the binary.

Run the Rust Aetheris Client install and read the Cargo text.
