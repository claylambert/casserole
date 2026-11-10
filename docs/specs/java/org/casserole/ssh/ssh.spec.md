# org.casserole.ssh

Component file. It carries how this package is built.

**Depends:** This container names no other spec.

# The inventory lists the boot types, the model types, the shell, the formatters, the client, and the two leftovers.

Boot and config:

- `src/main/java/org/casserole/CasseroleApplication.java`
- `src/main/java/org/casserole/config/CasseroleProperties.java`
- `src/main/java/org/casserole/app/InitRunner.java`
- `src/main/resources/application.yaml`

Model:

- `src/main/java/org/casserole/llm/LlmService.java`
- `src/main/java/org/casserole/llm/SpringAiOllamaLlmService.java`
- `src/main/java/org/casserole/ollama/OllamaProcess.java`

Chat and commands:

- `src/main/java/org/casserole/chat/ChatSession.java`
- `src/main/java/org/casserole/chat/ShellMode.java`
- `src/main/java/org/casserole/chat/SpinnerStyle.java`
- `src/main/java/org/casserole/chat/cmd`

Shell:

- `src/main/java/org/casserole/ssh/shell/SshServer.java`
- `src/main/java/org/casserole/ssh/shell/ChatShellFactory.java`
- `src/main/java/org/casserole/ssh/shell/ChatShell.java`
- `src/main/java/org/casserole/ssh/shell/Ansi.java`

Format:

- `src/main/java/org/casserole/format/FormatRouter.java`
- `src/main/java/org/casserole/format/FormatRouterFactory.java`
- The context, handler, result, detector, renderer, line endings, link annotator, and word wrap.
- The structured-data, fence, table, list, and markdown handlers.

Client:

- `SshClientApp`, `SshConnectionPanel`, `SshTerminalPanel`, `SshSessionController`, `SshTtyConnector`, `SshClientProperties`, `SshClientTheme`, `DarkJediTermSettings`, `DarkJediTermWidget`, `PasteEditorDialog`, `SshSessionMode`, and `SshConnectionState`, all under `org.casserole.app.ui.ssh`.

Leftover:

- `src/main/java/org/casserole/ssh/MinimalSshServer.java`
- `src/main/java/org/casserole/ssh/SshServerTest.java`

End of the capture. Update the file when a design decision lands in code, not when it is only discussed.

**Truth.** The checklist is the implementation inventory, including the two leftovers and the empty instruction to update on code, not on talk.

**Reason.** A reader has to be able to find every type the capture depends on.

**Avoidances.** Do not drop the leftovers from the inventory. Do not update the capture for a discussion that has not landed in code.


**semantic-key:** PK-0461
