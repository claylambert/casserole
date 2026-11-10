# casserole-server scripts

These scripts build the server, copy the jar to the folder a start script can launch, and prepare the local Ollama model. A jar is the packaged Java program. Run them from the repository root, the folder that contains the top-level [pom.xml](../../../pom.xml). Maven, which is the Java build tool, reads that file when it builds the Java programs.

A silo is one project directory with its own build script, its own install script, and its own start script. This page is that directory's scripts. The scripts call the shared toolchain at the repository root. The page that explains the server itself is [casserole-server/README.md](../../README.md). The root toolchain page is [bin/docs/README.md](../../../bin/docs/README.md). The class the jar starts is [CasseroleApplication](../../src/main/java/org/casserole/CasseroleApplication.java). That class is the Java type whose `main` method boots the process.

## Fast path

[app/start.ps1](../../app/start.ps1) on Windows and [app/start.sh](../../app/start.sh) on Linux are the start scripts for this server. A start script is designed to check the environment, check the pre-build, build, bind and install, and run the server in one operation. Check the environment selects the Java toolchain. Check the pre-build prints the tools on one screen. Build compiles the server. Bind and install places the jar in the app folder the start launches. The goal is to get you up and running fast.

If that start runs into trouble, walk the checklist in this section and the numbered sections on the [server page](../../README.md#fast-path), one script at a time. The block under this heading is the command list for this directory.

Windows. The scripts are [install-ollama.ps1](../install-ollama.ps1), [install.ps1](../install.ps1), and [app/start.ps1](../../app/start.ps1).

```powershell
powershell -File casserole-server\bin\install-ollama.ps1
powershell -File casserole-server\bin\install.ps1
powershell -File casserole-server\app\start.ps1
```

Linux. The scripts are [install-ollama.sh](../install-ollama.sh), [install.sh](../install.sh), and [app/start.sh](../../app/start.sh).

```bash
bash casserole-server/bin/install-ollama.sh
bash casserole-server/bin/install.sh
bash casserole-server/app/start.sh
```

The start script is finished when the window prints the line `casserole-server 0.0.1-SNAPSHOT`. `0.0.1-SNAPSHOT` is the build identifier of the server, the version string of the bits you just launched. The server log then keeps printing in that same window. Leave the window open, because the log staying open is the server. The SSH port is 2222. SSH, which is Secure Shell, is the encrypted login a terminal uses to reach a host.

## build

The build is the step that asks Maven whether the server still compiles and still passes its tests. Install calls this step. You can call it alone when you want the jar produced and not yet copied into `app/`. [build.ps1](../build.ps1) and [build.sh](../build.sh) load [java-env.ps1](../../../bin/java-env.ps1) or [java-env.sh](../../../bin/java-env.sh). They then run `mvn -pl casserole-server -am package` from the repository root. `-pl casserole-server` tells Maven which module to package. `-am` tells Maven to build the modules that module needs. The module file is [pom.xml](../../pom.xml). Maven reads that file to compile the module, run its tests, and package the jar.

Windows:

```powershell
powershell -File casserole-server\bin\build.ps1
```

Linux:

```bash
bash casserole-server/bin/build.sh
```

> **What appears**
>
> Maven's compile and test log, then the line `casserole-server build finished`.

The line `casserole-server build finished` means the package step completed. The jar then exists for the install step to copy.

> **Failed line**
>
> The script throws `casserole-server build failed`.

The Maven text above that line names the module. Fix the failure that module names, and run the build again.

## install

Install is the build, plus the copy that puts the jar where the start script looks. [install.ps1](../install.ps1) and [install.sh](../install.sh) run the build. They then copy `casserole-server-*.jar` into `casserole-server/app/casserole-server.jar`. A jar whose name contains `original` is skipped, because that file is Maven's unprocessed copy and not the jar you launch. The jar contains [users.yaml](../../src/main/resources/users.yaml) and [application.yaml](../../src/main/resources/application.yaml). The roster is the list of names and passwords the SSH login accepts. The example configuration leaves the API key empty. The example configuration leaves the SSH password empty.

Windows:

```powershell
powershell -File casserole-server\bin\install.ps1
```

Linux:

```bash
bash casserole-server/bin/install.sh
```

> **What appears**
>
> The build output, then the line `casserole-server installed to casserole-server\app`.

That line means the jar was copied into `app`.

> **Failed line**
>
> `server jar was not produced` means the build did not write the jar.

Run the build and read the Maven result.

## install-ollama

This script installs the local Ollama model. The server can also answer through the Grok API or through Grok Build ACP via the pool. The Grok API is Grok's hosted programming interface. Grok Build ACP via the pool is Grok Build reached through its shared pool. The Aetheris client is the Sovereign Desktop in this repository. That client is the Java program or the Rust program. One client carries more than one conversation at the same time. Those conversations stay unified on that desktop. The desktop can use more than one of those models at the same time. [install-ollama.ps1](../install-ollama.ps1) and [install-ollama.sh](../install-ollama.sh) install the Ollama engine when it is missing. Ollama is the program that runs a language model on this computer. The script then pulls a model. With no argument the model is `qwen2.5-coder:7b`. That name is the Ollama model used when you do not name another Ollama model. With a model name, the script pulls that model. Run the script again when you want another model.

Windows:

```powershell
powershell -File casserole-server\bin\install-ollama.ps1
powershell -File casserole-server\bin\install-ollama.ps1 llama3.2
```

The second command is the same script with the model name `llama3.2`.

Linux:

```bash
bash casserole-server/bin/install-ollama.sh
bash casserole-server/bin/install-ollama.sh llama3.2
```

> **What appears**
>
> `ollama pull` and the model name, then the line `install-ollama finished`.

The line `install-ollama finished` means the pull completed.

> **Failed line on Windows**
>
> Ollama is missing, so the script downloads `OllamaSetup.exe`, prints that path, and exits.

`OllamaSetup.exe` is the Windows installer for Ollama. Run that installer, open a new PowerShell, and run the script again.

> **Failed line on Linux**
>
> The engine install needs a network and permission to install.

After the engine is installed, run the script again. A failed pull throws `ollama pull failed`.

## start

Start is the line you leave open. The log in that window is the server, waiting for a login. [start.ps1](../../app/start.ps1) and [start.sh](../../app/start.sh) print `casserole-server 0.0.1-SNAPSHOT`. They then run `java -jar casserole-server.jar` with the repository root as the working directory. `java -jar` launches the packaged program. When `casserole-server.jar` is absent, start runs [install.ps1](../install.ps1) or [install.sh](../install.sh), then starts the server. The installer still runs alone when you call it by itself, and that call only installs. The start script loads the Java toolchain so the JDK is the one that script selected. A JDK is the Java development kit. [UserAccounts](../../src/main/java/org/casserole/config/UserAccounts.java) reads the roster from the jar when no `users.yaml` sits in the working directory and none sits under `~/.casserole/`.

Windows:

```powershell
powershell -File casserole-server\app\start.ps1
```

Linux:

```bash
bash casserole-server/app/start.sh
```

> **What appears**
>
> The version line, then the server log.

The server listens on port 2222. When the jar was absent, the install output appears first, and the log follows after the server starts. The first start creates `hostkey.ser` in the working directory. `hostkey.ser` is the host key the SSH login uses, and that file stays on your machine. If port 2222 is already taken, another process is already listening there. Stop that other listener and start again.
