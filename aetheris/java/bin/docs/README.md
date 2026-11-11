# aetheris/java scripts

These scripts build the Java desktop client and copy its jar, plus the runtime jars it needs, into the folder a start script can launch. A jar is the packaged Java program. Swing is the Java library that draws the window. Run the scripts from the repository root, the folder that contains the top-level [pom.xml](../../../../pom.xml). Maven, which is the Java build tool, reads that file when it builds the Java programs.

A silo is one project directory with its own build script, its own install script, and its own start script. This page is that directory's scripts. The scripts call the shared Java toolchain at the repository root. The page that explains the client itself is [aetheris/java/README.md](../../README.md). The root toolchain page is [bin/docs/README.md](../../../../bin/docs/README.md). The class the jar starts is [SshClientApp](../../../../casserole-server/src/main/java/org/casserole/app/ui/ssh/SshClientApp.java). That class is the Java type whose `main` method opens the window. The file sits with the server sources because its package is `org.casserole`.

## Fast path

[app/start.ps1](../../app/start.ps1) on Windows and [app/start.sh](../../app/start.sh) on Linux are the start scripts for this client. A start script is designed to check the environment, check the pre-build, build, bind and install, and run the client in one operation. Check the environment selects the Java toolchain. Check the pre-build prints the tools on one screen. Build compiles the client. Bind and install places the jar in the app folder the start launches. The goal is to get you up and running fast.

If that start runs into trouble, walk the checklist in this section and the numbered sections on the [client page](../../README.md#fast-path), one script at a time, and match the window to the quote after each script. The block under this heading is the command list for this directory.

Windows. The scripts are [install.ps1](../install.ps1) and [app/start.ps1](../../app/start.ps1).

```powershell
powershell -File aetheris\java\bin\install.ps1
powershell -File aetheris\java\app\start.ps1
```

Linux. The scripts are [install.sh](../install.sh) and [app/start.sh](../../app/start.sh).

```bash
bash aetheris/java/bin/install.sh
bash aetheris/java/app/start.sh
```

The start script is finished when the window prints the line `aetheris-java 0.0.1-SNAPSHOT`. `0.0.1-SNAPSHOT` is the build identifier of the Java client, the version string of the bits you just launched. The Swing window then opens on the desktop.

## build

The build asks Maven to package this client. The install step is the separate step that copies the result into `app/`. [build.ps1](../build.ps1) and [build.sh](../build.sh) load [java-env.ps1](../../../../bin/java-env.ps1) or [java-env.sh](../../../../bin/java-env.sh). They then run `mvn -pl aetheris/java -am package` from the repository root. `-pl aetheris/java` tells Maven which module to package. `-am` tells Maven to build the modules that module needs. The module file is [pom.xml](../../pom.xml).

Windows:

```powershell
powershell -File aetheris\java\bin\build.ps1
```

Linux:

```bash
bash aetheris/java/bin/build.sh
```

> **What appears**
>
> Maven's compile and test log, then the line `aetheris-java build finished`.

The line `aetheris-java build finished` means the package step completed. The client jar then exists for the install step to copy.

> **Failed line**
>
> The script throws `aetheris-java build failed`.

Read the Maven module name in the text above that line. Fix the failure that module names, and run the build again.

## install

Install runs the build, then lays the jar and its runtime libraries where start can see them. [install.ps1](../install.ps1) and [install.sh](../install.sh) run the build. They then copy `aetheris-java-*.jar` into `aetheris/java/app/aetheris-java.jar`. They copy the runtime jars into `aetheris/java/app/lib`. A jar whose name contains `original` is skipped, because that file is Maven's unprocessed copy and not the jar you launch. The jar's manifest main class is [SshClientApp](../../../../casserole-server/src/main/java/org/casserole/app/ui/ssh/SshClientApp.java). The manifest is the list inside the jar that names the class `java` should start. The shell log configuration [log4j2-shell.properties](../../src/main/resources/log4j2-shell.properties) is inside that jar. JediTerm and the other runtime jars sit in `lib`. JediTerm is the library that draws the terminal inside the Swing window.

Windows:

```powershell
powershell -File aetheris\java\bin\install.ps1
```

Linux:

```bash
bash aetheris/java/bin/install.sh
```

> **What appears**
>
> The build output, then the line `aetheris-java installed to aetheris\java\app`.

That line means the jar and the runtime jars were copied into `app`.

> **Failed line**
>
> `java shell jar was not produced`, or `runtime jars were not produced`, means the build did not write the files the copy expects.

Run the build and read the Maven result.

## start

Start prints the build identifier and opens the window. When the pieces are missing, it installs them first, then opens the window. [start.ps1](../../app/start.ps1) and [start.sh](../../app/start.sh) print `aetheris-java 0.0.1-SNAPSHOT`. They load the Java toolchain. They then run `java -cp` with `aetheris-java.jar`, `app/lib`, and the main class `org.casserole.app.ui.ssh.SshClientApp`. `-cp` is the classpath, which is the list of jars Java searches for classes. When the jar is absent, or when `lib` has no jars, start runs [install.ps1](../install.ps1) or [install.sh](../install.sh), then opens the window. The installer still runs alone when you call it by itself, and that call only installs.

Windows:

```powershell
powershell -File aetheris\java\app\start.ps1
```

Linux:

```bash
bash aetheris/java/app/start.sh
```

> **What appears**
>
> The version line, then the Swing window.

When the jar was absent, the install output appears first, and the window opens after that copy finishes. The server listens on port 2222. Port 2222 is the Secure Shell port the server opens. Secure Shell, written SSH, is the encrypted login a terminal uses to reach a host. Connect to `localhost` on that port as the user `admin` with the password `demo-admin`. `localhost` means this same machine. Start the server before this client. The server page is [casserole-server/README.md](../../../../casserole-server/README.md).
