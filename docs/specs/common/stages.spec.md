# Stages

[Grok and the model stream](grok.spec.md). [Index](../common.spec.md). [Formal plan](formal-plan.spec.md).

# Aetheris — Stage 0 plan — serial 001

**Mode:** plan. **Not code.** Await stamp in the plan browser, then build.

> *The van starts. Tab A still drives raw PTY. No cells. No AMWP. No vault.*

P0-1…P0-4 are from the stamped pre-plan. **No Lombok** is now.

## 0. Locked answers this plan uses

## 1. Goal and non-goal

**Goal:** clone → `bin/fetch-zulu-jre` → `bin/casserole` + `bin/aetheris-shell` on JDK 26 Zulu → existing SSH/`chat>`/Roux restore/splits/`users.yaml` work as today. Log4j2 is what you tail. Lamina runs *our* server work.

**Non-goal:** Aetheris tab, AMWP, H2 vault, cells, Grok, MCP, LCARS, class rename, JPA cleanup, virtual threads, `AetherisWorker`.

## 2. Slice order (build this sequence)

Dependencies only downward. Do not pull Stage 1 into a slice.

```
P0 pom + Log4j2 deps (no behavior change except logging impl)
P1 log properties + CasseroleLog helper
P2 Lamina engine + Boot adapter
P3 Wire SshServer + ChatShell + InitRunner
P4 AetherisYaml + window title / About / Help / main args
P5 bin/ scripts + sample aetheris.yaml
```

P0→P1→P2→P3 are a chain. P4 can start after P0 (yaml has no Lamina). P5 after P0 (scripts need java.version 26). P6 last.

## 3. P0 — POM: JDK 26 + Log4j2

**Files:** `pom.xml`

1. `<java.version>26</java.version>`
2. Add `spring-boot-starter-log4j2`
3. Exclude `spring-boot-starter-logging` from `spring-boot-starter-web` (and any other starter that pulls Logback)
4. Keep `spring-boot-maven-plugin` (fat jar exists for optional `--jar`)
5. Do **not** add Lombok. Do **not** add Hikari/JPA changes. Leave H2 runtime dep.

**Verify:** `mvn -DskipTests compile` on Zulu 26. Runtime classpath has Log4j2, not Logback (`mvn -DskipTests dependency:tree` must not show `logback-classic` at runtime).

## 4. P1 — Logging

**New files:**

- `src/main/resources/log4j2.properties` — server
- `src/main/resources/log4j2-shell.properties` — Shell
- `src/main/java/org/casserole/log/CasseroleLog.java` — `LogManager.getLogger(Class)`; named `get(Class)` / `get(String)`. No Lombok.

**Server properties:**

- Console appender (operator still sees the port)
- File `~/.casserole/logs/casserole.log` (create parent dirs on first write)
- Root INFO
- Tunable loggers: `org.casserole`, `org.roux`, `org.apache.sshd`
- Pattern: timestamp, level, thread, logger, message

**Shell properties:**

- File only at INFO: `~/.aetheris/logs/aetheris-shell.log`
- No console spam

**Wire:**

- Boot picks `log4j2.properties` automatically once Logback is gone
- `SshClientApp.main`: configure Log4j2 from `log4j2-shell.properties` **before** `invokeLater` (`Configurator.initialize` or `System.setProperty("log4j.configurationFile", …)`)

## 4. P1 — Logging

**Levels:** INFO quiet; DEBUG useful without full prompts; TRACE may log payloads except burner (rule now, burners later).

## 5. P2 — Lamina engine

**New package:** `org.casserole.lamina` (no Spring types)

**Suggested types (named, `NO-LAMBDA`, JavaDoc, `@author `, end-of-class banners, PlantUML on the executor if non-obvious):**

## 5. P2 — Lamina engine

**Do not** hide work in anonymous Spring `TaskExecutor` beans besides this adapter.

## 6. P3 — Wire server threads we own

### 6.1 `SshServer` — stop extending `Thread`

MINA `IoProcessor` threads: **untouched**.

### 6.2 `InitRunner`

Ask Lamina (via `SshServer.start()`) after Boot is up. Preserve listen-on-ready.

### 6.3 `ChatShell`

**Today:**

- `readThread = new Thread(this, "casserole-chat-shell")`
- `llmExecutor = Executors.newSingleThreadExecutor(… "casserole-llm")`
- `llmExecutor.submit(…)` around line 465
- `destroy`: `llmExecutor.shutdownNow()`, `readThread.interrupt()`
- `escapeTimer` / `spinnerTimer` — **leave**

### 6.4 Grep gate (server)

## 7. P4 — AetherisYaml + product name

**:** put the loader in **`org.aetheris.config`** — Stage 0 is allowed to create the package as an empty product foothold. Do **not** put vault/DAO there yet.

**Lookup:** `--aetheris-yaml <path>` from `main` args → `./aetheris.yaml` → `~/.aetheris.yaml`. Missing file: defaults, Shell still starts. Extra keys **must not crash**.

**Stage 0 honors:** `aetheris.ui.title` only. Default title `"Aetheris Shell"` if unset.

**Parser:** no SnakeYAML if we can avoid a new dep — simple line parser for `ui.title` is enough, **or** use Jackson YAML already on Boot’s classpath. Shell does not start Boot. **Do not add a YAML library just for one key** unless Jackson is already a compile dep of the client path. **hand parser** for the nested `aetheris.ui.title` key (and ignore the rest). `PURE-JAVA`.

**`SshClientApp`:**

- `main(String[] args)` — parse `--aetheris-yaml`; load yaml; init Log4j2 shell config; then `invokeLater`
- Constructor: `super(title)` from yaml
- About: product name **Aetheris Shell**; still mention Casserole/Roux as the host stack
- Help header: Aetheris Shell
- Menu: `About Aetheris…` not `About Casserole`
- Class/package **stay** `org.casserole.app.ui.ssh.SshClientApp`

Do **not** merge `~/.ssh-client.properties` into `aetheris.yaml`.

## 8. P5 — `bin/` scripts + sample yaml

**New directory:** repo-root `bin/`

Shared Java resolution (source from every start script):

1. If `JAVA_HOME` is set and `java -version` reports **26**, inherit.
2. Else if `~/.aetheris/jre/zulu-26/` exists, export `JAVA_HOME`/`PATH` to it (overrides other Java).
3. Else print “run `bin/fetch-zulu-jre`” and exit nonzero.

| Script | Windows | Linux / macOS |
|---|---|---|
| `_java` helper | `_java.ps1` | `_java.sh` |
| `casserole` | `.ps1.cmd.bat` | `.sh` |
| `aetheris-shell` | `.ps1.cmd.bat` | `.sh` |
| `fetch-zulu-jre` | `.ps1` | `.sh` |
| `install-ollama` | `.ps1` | `.sh` |
| `bootstrap-env` | `.ps1` only | — |

**`casserole`:** default `mvn spring-boot:run` with the resolved Java. Optional `--jar`: if `target/casserole-*-SNAPSHOT.jar` (or the boot jar) exists, `java -jar` it; else print “package first” and exit. Do not build a second start stack.
**semantic-key:** PK-840

## 8. P5 — `bin/` scripts + sample yaml

**`fetch-zulu-jre`:** Azul Zulu **26** JRE into `~/.aetheris/jre/zulu-26/`. Fail loud on URL/hash mismatch. Idempotent if already present and `java -version` is 26. Prefer JRE; SDK only if JRE is unavailable for this OS/arch.

**`install-ollama`:** official installer per OS; idempotent if `ollama` on PATH.

**`bootstrap-env`:** Windows 11, new installs. If nothing is required beyond Zulu+Ollama, print that and exit 0. Do not invent a package-manager stack.

Linux: bash, flavor-agnostic. No apt/yum except inside vendor Ollama script if required.

**Sample yaml** committed at repo root `aetheris.yaml` (or `aetheris.yaml.example`). Stage 0 reader honors `ui.title` only:

```yaml
aetheris:
 ui:
 title: Aetheris
 vault:
 mode: ram
 path: ${user.home}/.aetheris/${user}/vault
 pool-max: 4
 network:
 air-gap: false
 amwp:
 enabled: true
```

`${user.home}` in the sample is documentation; the Stage 0 parser does not expand it.

## 9. P6 — Preserve-all smoke (manual, no JUnit)

Stage 0 is **not done** if 1–7 fail.

1. `bin/casserole` listens on 2222; `users.yaml` auth works
3. Disk exclusive: second login same user refused when `commit`
4. `hot-only` still allows multi RAM sessions
5. `bin/aetheris-shell` — Connect, splits, New Term, Close Pane, one-touch, focus/Connect, paste, Send file
6. Roux restore after clean hangup (`commit`)
7. Ollama down: Boot still starts; chat prints red error
8. Log4j2 INFO on start; files under `~/.casserole/logs` and `~/.aetheris/logs`
9. Title bar / About = Aetheris Shell
10. No AMWP on the wire; no H2 file under `~/.aetheris/{user}/vault`; no Aetheris product tab

## 10. Files / components (checklist)

| Path | Slice |
|---|---|
| `pom.xml` | P0 |
| `src/main/resources/log4j2.properties` | P1 |
| `src/main/resources/log4j2-shell.properties` | P1 |
| `org.casserole.log.CasseroleLog` | P1 |
| `org.casserole.lamina.*` | P2 |
| `org.casserole.lamina.spring.*` | P2 |
| `org.casserole.ssh.shell.SshServer` | P3 |
| `org.casserole.app.InitRunner` | P3 |
| `org.casserole.ssh.shell.ChatShell` | P3 |
| `org.casserole.ssh.shell.ChatShellFactory` | P3 |
| `org.aetheris.config.AetherisYaml` | P4 |
| `org.casserole.app.ui.ssh.SshClientApp` | P4 |
| `bin/_java.ps1_java.sh` | P5 |
| `bin/casserole.*` `bin/aetheris-shell.*` | P5 |
| `bin/fetch-zulu-jre.*` `bin/install-ollama.*` `bin/bootstrap-env.ps1` | P5 |
| `aetheris.yaml` (sample) | P5 |

## 10. Files / components (checklist)

## 11. House rules for implementers

- `NO-LAMBDA` in our types
- **No Lombok**
- JavaDoc + `@author ` + end-of-class banners
- No JUnit unless asked
- If Spring Boot 3.5 will not run on 26: **stop and ask**
- Stage 1 packages (vault, AMWP, cells) stay empty aside from `AetherisYaml`

## 12. Done when

- [ ] `mvn -DskipTests package` on Zulu 26
- [ ] No Logback at runtime
- [ ] Lamina lanes live; server path has no private `new Thread` / `newSingleThreadExecutor` (except leftovers)
- [ ] No Aetheris tab, no AMWP, no vault file

After build: return to the finalized spec and extract the **Stage 1 pre-plan**. Do not start Stage 1 from this document.

* sequenced the slices. Kept MINA’s hands off Lamina. Face renamed the title bar and left the class alone. Stamp this in the plan browser,, and we light the wrench.*

# Aetheris — Stage 1 tweaks plan — serial 001

This turn does not build.

> *The notebook already stands. These are usability tweaks. Tab A still drives. One pipe. Four context states. No Stage 2.*

# Aetheris — Stage 1 tweaks plan — serial 001

## 0. Locked answers this plan uses

| ID | Lock |
|---|---|
| Collapse | Boolean `collapsed` on `CellRecord`. Default false. Not a `CellState`. |
| Brief | Assistant text only, from the first character. One line, or two if the header row has the height. Empty output leaves the brief empty. Never the user text. |
| Buttons | `IEPO` stay one character. Tooltips: Included, Excluded, Input only, Output only. No second set. |
| Collapse glyph | `+` when collapsed, `-` when open. Tooltip `Expand` or `Collapse`. |
| Working set | Do not read `collapsed`. `E` is still the only way out of context. |
| Colors | Input `#00FF66` on `#1A1A1A`. Border `#3CB371`. Output and View `#FFFF55`. `EXCLUDED` stays gray and wins. |
| View bounds | `~/.aetheris/{user}/view-bounds.txt`. Not a cell column. Not `aetheris.yaml`. Off-screen falls back to 720 by 480, centered on the Aetheris pane. |
| Focus | Header rebuild restores `cardY - viewY`. Focus stays on that card. |
| Import | Right-click `Import...`. Replaces `userText`. Cap 1 MiB. No pipe. |
| Clear | Footer button. Active canvas only. One new empty `INCLUDED` turn. VT100 `clear` is not called. No confirm dialog. |
| Models | `select model` and `list models` stay. `swap` and `ollama list` stay. |
| Shell launch | `java -cp` + LocalM2Classpath. Never `exec:java`. |
| Stage 1 stamps | Are not edited. |

## 1. Goal and non-goal

**Goal:** On the Shell that already runs, a cell collapses to its header and an output brief and comes back with the same text. The four letters have tooltips. Input is emerald and output is console yellow. View reopens where it was. A state click does not jump the scroll. Right-click imports a text file into that cell's input. Clear turns empties the active canvas and leaves the VT100 session alone. A disk vault from before this plan still opens.

**Non-goal:** `select model`, `list models`, a fifth context state, a working-set change, CONTEXT-PIN, Grok, MCP `0x0004`, Graph, an import button, full-word labels, a second button row, and any edit to the frozen Stage 1 plan.

## 2. Slice order

Dependencies only downward. Do not pull Stage 2 into a slice.

```
T1 collapsed column + CellRecord vault first
T2 collapse card + output brief needs T1
T6 focus hold with T2
T3 tooltips header, any time after T2
T4 colors paint, any time after T2
T5 View bounds file independent
T7 right-click import independent
T8 footer Clear turns independent
```

No new test framework. No JUnit task.

## 3. T1 — Persist the flag

**Files:** `org.aetheris.vault.CellRecord`, `VaultSql`, `VaultStore`.

1. Add `public boolean collapsed` on `CellRecord`. `newCell` leaves it false.
2. Add `collapsed BOOLEAN NOT NULL DEFAULT FALSE` to `DDL_CELL`.
3. On vault open, add the column when it is missing. `CREATE TABLE IF NOT EXISTS` does not alter a disk vault that already has `cell`. Probe `INFORMATION_SCHEMA.COLUMNS` for table `cell`, column `collapsed`. Add it only when the probe is empty. A duplicate add is not a failure.
4. Carry the flag through `INSERT_CELL`, `UPDATE_CELL`, `SELECT_CELLS`, `insertCell`, `bindUpdate`, and `loadCells`.
5. A burner still writes null CLOBs. The flag is stored either way.
6. Do not add an enum value to `CellState`.

## 4. T2 — Collapse the card

**File:** `org.aetheris.swing.AetherisNotebookPanel`, inner class `CellCard`.

1. Add one header toggle beside the state buttons. Label `+` when `cell.collapsed` is true, `-` when false. Tooltip `Expand` or `Collapse`.
2. The toggle writes `cell.collapsed`, calls `persistQuiet`, then `rebuildTabs`, using the T6 hold.
3. Open card: keep the vertical split. Closed card: do not add the split. Add the brief under the header.
4. Brief source is `assistantText` only. Plain truncation from the first character. One line. A second line only when the header row is tall enough. No summarizer. Empty or whitespace output leaves the brief blank.
5. Brief color follows T4. Gray wins when `cell.state` is `EXCLUDED`.
6. Collapsed max height is the header plus the brief. It is not the open card's 280.
7. `IEPO`, burner, and View stay on that same header while collapsed. View stays disabled when there is no output.

## 5. T3 — Tooltips

**File:** `AetherisNotebookPanel.stateButton`.

Set the tooltip from the state: `I` Included, `E` Excluded, `P` Input only, `O` Output only. The visible text stays the one character.

## 6. T4 — Colors

**File:** `AetherisNotebookPanel`, `area` and `showViewModal`.

1. User foreground `#00FF66`. Background stays `#1A1A1A`. User border stays `#3CB371`.
2. Assistant foreground `#FFFF55` when the cell has output. The empty-response border may stay the gray chrome it has now.
3. View modal foreground `#FFFF55` on `#1A1A1A`.
4. `EXCLUDED` sets user, assistant, and the brief to gray after the other colors.

## 7. T5 — View bounds

**Files:** `AetherisNotebookPanel.showViewModal`. A new type `org.aetheris.swing.ViewBounds` is allowed, with `// T5`. No lambdas.

1. Path is the user directory beside the vault folder: `~/.aetheris/{user}/view-bounds.txt`. Four lines or keys: `x`, `y`, `width`, `height`.
2. RAM vault mode uses the same file. The bounds are not inside H2 and not in `aetheris.yaml`.
3. On dialog close, write the dialog bounds.
4. On open, use the saved rectangle when it intersects a current screen device. Otherwise `setSize(720, 480)` and `setLocationRelativeTo` the Aetheris pane.
5. A missing file, a short file, or a non-integer is the same fallback. Do not throw to the operator.

## 8. T6 — Focus hold

1. Before `rebuildTabs` from a header control, remember the card id, `card.getY()`, and the canvas scroll pane's view position Y.
2. After the new card is validated, set the view Y to `newCardY - (oldCardY - oldViewY)`.
3. Focus stays on that card. A collapsed card focuses the header toggle, not a hidden text area.
4. Do not use `scrollRectToVisible` of the whole card for this hold. That call pulls a tall card to the top.
5. New turn and the end of a run may still scroll to the new cell. Clear turns may scroll to the new empty turn.

## 9. T7 — Import

**File:** `AetherisNotebookPanel.CellCard`.

1. Popup on mouse press when `isPopupTrigger`. One item, `Import...`. No header button.
2. The cell under the pointer is the target.
3. `OUTPUT_ONLY` refuses with a status line. No chooser.
4. A press whose source is the assistant area refuses. The file never writes `assistantText`.

## 9. T7 — Import

6. Refuse when `file.length()` is greater than 1 MiB, before reading. UTF-8. A NUL in the first 8 KiB refuses and does not replace text.
7. Success replaces `userText`, updates the area if the card is open, and `persistQuiet`. State unchanged. No `runTarget`. No AMWP.

## 10. T8 — Clear turns

**Files:** `VaultStore`, `VaultSql`, `AetherisNotebookPanel.buildFooter`.

1. Footer button `Clear turns` beside New turn.

## 10. T8 — Clear turns

3. Replace the in-memory `canvas.cells` and `cellOrder` with that one cell. `rebuildTabs`. Scroll to it.

4. Do not call `ClearCommand`. Do not touch `ChatSession`.
**semantic-key:** PK-730
**semantic-key:** PK-730

5. No confirm dialog. Status line names the canvas and says the turns are cleared.
6. Burner cells are deleted with the rest.

## 11. T9 — Smoke, by hand

1. Collapse a cell that has output. The brief is the start of that output. Expand. Both texts are unchanged.
2. Collapse a cell with empty output. The brief is blank.
3. Restart the Shell on a disk vault. Collapsed cells are still collapsed. An old vault that predates the column opens with every cell expanded.
4. A collapsed `INCLUDED` cell still contributes its output to the working set of a later Run.
5. `E` on a collapsed cell ghosts it, grays the brief, and drops it from the working set.
6. Hover `IEPO` and read the four tooltips.
7. Input is emerald. Output and View are yellow. An excluded cell is gray.
8. Move and resize View, close, open another cell. Same place and size.
9. Close View while it is off-screen. The next open is 720 by 480, centered.
10. Click `I` on a cell that is not at the top of the pane. That cell stays at the same place in the scroll.
11. Right-click the input and import a short text file. The input is replaced. The output is not.
12. Right-click an `OUTPUT_ONLY` cell. Import refuses.
13. A file over 1 MiB refuses.
14. Clear turns leaves one empty turn. The VT100 history is still there.

## 12. Gate

This plan is approved as the run-book. Java waits until names the build. Stage 2 stays closed.

# Aetheris — Stage 1 tweaks plan — serial 002

These are small items.

> *The notebook already stands. Three small moves. One tab. One pipe. Four context states. No Stage 2.*

**No Lombok.** **No lambdas in our types.** No new type, so no new `// `. Class stays `org.casserole.app.ui.ssh.SshClientApp`. Shell launch stays `java -cp` plus LocalM2Classpath. Never `exec:java`.

## 0. Locked answers this plan uses

| ID | Lock |
|---|---|
| Run | A finished Run creates the next turn and puts the caret in that turn's input. The answer stays in the same view. |
| Inspector | The inspector lists the memory chain only. An excluded cell is absent, including the last cell. `E` refreshes the list in that click. The card stays on the canvas. |
| Console | One Aetheris tab. It shows the vault of the user on the focused VT100 session. A swap writes the open vault before it closes. |
| Same user | Focusing another terminal that is the same user does not reload the vault. |
| Listener | The focused connector holds the notebook listener. The connector that lost focus does not. |
| Disk | Each user stays at `~/.aetheris/{user}/vault`. RAM mode is not given a second cache. |
| Models | `select model` and `list models` stay. `swap` and `ollama list` stay. |
| Filed build | This plan does not reopen them. |
| Stage 1 stamps | The frozen Stage 1 plan and the 001 tweaks plan are not edited. |

Yes. One tab follows that user.

## 1. Goal and non-goal

**Goal:** After Run, the caret is in the new turn and the answer is still on screen. The inspector matches the chain the next Run would send, and an `E` click updates it immediately. The Aetheris tab shows the notebook of whichever connected user owns the focused VT100 session, and the other user's turns are still there when that session is focused again.

**Non-goal:** A second Aetheris product tab, a pre-plan, a fifth context state, `select model`, `list models`, Grok, Stage 2, a RAM session cache, an edit to the frozen plans, and a rewrite of the encode.

## 2. Slice order

```
U1 Run lands on the next input
U2 Inspector shows the chain
U3 Focused console owns the notebook
```

U1 and U2 are the notebook panel. U3 is the Shell session focus. U4 is after U1–U3. No new test framework. No JUnit task.

## 3. U1 — Run lands on the next input

**File:** `org.aetheris.swing.AetherisNotebookPanel`. Methods `applyAssistant`, `newTurn`, `scrollPendingFocus`.

Today a finished response on the last cell sets `pendingFocusId` to that cell and `pendingFocusAssistant` to true, then calls `newTurn`. `newTurn` leaves an existing pending focus alone, so the caret stays in the answer.

1. When the answered cell is the last cell, do not keep focus on that assistant. The new turn becomes the pending focus, and the pending focus is the input.
2. After layout, scroll the least that shows the new cell's user area. Call `scrollRectToVisible` on that user area. Do not call it on the whole card. The whole-card call pulls a tall card to the top.
3. The answered card is the card above the new turn. The minimal scroll leaves it in the viewport when the pane has the room. When the pane is shorter than both cards, the new input stays visible and the answer sits against it from above.
4. A response on a cell that is not last still focuses that cell's assistant. Run uses the last cell, so Run takes the new-turn path.
5. The header focus-hold stays as it is. A state click still does not jump the scroll. Clear turns and New turn may still scroll to the empty turn they create.
6. Do not arm the hold on this path.

## 4. U2 — Inspector shows the chain

**File:** `AetherisNotebookPanel`. Methods `assemble`, `stateButton`, and the Ctrl+Shift `IEPO` path in `installHotkeys`.

`appendCell` already skips `EXCLUDED` and does not read `collapsed`. Leave it.

1. `assemble` does not add the target user turn when the target state is `EXCLUDED`.
2. `INCLUDED` and `INPUT_ONLY` still contribute the target's user text. That text is the prompt Run sends. Prior `OUTPUT_ONLY` cells still contribute assistant text. Prior `INPUT_ONLY` cells still contribute user text. A collapsed `INCLUDED` cell stays on the chain.
3. `stateButton` and the hotkey path call `refreshInspector` on the EDT in the same click, after the state is written. The list changes before the next Preview.
4. The excluded card stays on the canvas. Gray stays gray.
5. An empty chain still names the target id and shows an estimate of 0. Do not invent a second inspector screen.

## 5. U3 — Focused console owns the notebook

**Files:** `org.casserole.app.ui.ssh.SshClientApp` (`selectSession`, `sessionConnected`) and `AetherisNotebookPanel.bindUser`.

Today `sessionConnected` calls `bindUser` for the focused connect and closes the open vault without writing it. `selectSession` enables the tab when the focused connector is AMWP and does not load that user's vault.

1. One product tab. One notebook panel. Do not add a tab per session.
2. The username is the focused session's last connect username. It is not the unsaved text in the connection bar.
3. When that session is connected and AMWP, and the username differs from the open vault, write the open vault, then open that user's vault, then give the notebook listener to that connector.
4. `persistAll` runs on the worker before `vault.close`. Cells and canvas order are written. Then `loadOrCreate` fills the pane.
5. The same username does not close or reopen the vault. `sessionConnected` no longer binds on its own. `selectSession` owns the swap, so a connect and a focus do not load twice.
6. A focused session that is not connected AMWP does not close the open vault. The tab stays disabled, as `refreshAetherisTab` does now.
7. `setAmwpListener(null)` on the connector that lost focus. A late frame from the background session must not write into the notebook on screen. The focused connector holds the listener. Run already uses `focusedAmwpConnector`.
8. If focus moves again before the load returns, apply the canvases only when that user is still the focused session's user.
9. Disk mode keeps the other user at `~/.aetheris/{user}/vault`. RAM mode has nothing to come back to. This plan does not add a RAM cache. The tree's `aetheris.yaml` is disk.

## 6. U4 — Smoke, by hand

1. Run a cell that gets an answer. The caret is in the new turn's input. The answer is visible above it without a manual scroll.
2. Click `E` on a cell that is listed. It leaves the inspector on that click. The card stays on the canvas. Click `I`. It returns.
3. Exclude the last cell. The inspector does not list its input.
4. A collapsed included cell still appears in the chain.
5. Connect user A and add a turn. Split the terminal and connect user B. The tab shows B. Focus A's terminal. A's turns are back, including the text typed before the swap. Focus B. B's turns are back.
6. Two terminals for the same user. Focusing the other does not reload the vault.
7. The VT100 scrollback is untouched.

## 7. Gate

# Aetheris — Stage 1 tweaks plan — serial 003

N6 is not in the build.
Do not edit them.
Do not open Stage 2. Do not rewrite the Stage 2 item table.

> *The notebook already stands. Eight code packages. One pipe. Four context states. No Stage 2. Prod 1.0 stays frozen.*

# Aetheris — Stage 1 tweaks plan — serial 003

**No Lombok.** **No lambdas in our types.** Anonymous classes only. `// ` on the one new type, `org.aetheris.swing.Incrementor`. Class stays `org.casserole.app.ui.ssh.SshClientApp`. Shell launch stays `java -cp` plus LocalM2Classpath. Never `exec:java`. Do not edit `src/main/resources/application.yaml`. Do not cut a seed-kit.
**semantic-key:** PK-731
**semantic-key:** PK-731

## 0. Locked answers this plan uses

N7 is not crosstalk. The session keeps running. The card follows the VT100. The mux stays protected.

## 1. Goal and non-goal

**Goal:** The eight code packages in section 2 are in the tree and compile. After Run the canvas sits at its end. The bottom status line names the focused user. Both import choosers remember one size and one folder. The incrementor sets open-card row height and restores it. The inspector shows a short rule between cards. The pull-down selects the connected VT100 user. The spinner is royal blue with an emerald glyph while thinking. Every split bar is 11 pixels thick, and the connection panel still remembers a real open height.

## 2. Slice order

```
N9 Divider constant and open height
N5 Inspector rule between cards
N8 Spinner colors
N2 User on the frame status line
N4 Incrementor and row height
N1 Run scrolls to the end of the pane
N3 Chooser size and folder
N7 Connected-user pull-down
```

N6 is not in this order. It stays a record.

N9 first, so later layout sees the thicker bars. N4 before N1, so the end of the pane includes the row height. N2 before N7, so both name the same user. No JUnit task. No new test framework.

## 3. N9 — Divider width

**Files:** `org.casserole.app.ui.ssh.SshClientTheme`, `org.casserole.app.ui.ssh.SshClientApp`.

1. Add `public static final int DIVIDER_SIZE = 11` on `SshClientTheme`.

## 3. N9 — Divider width

3. The shell split, the session split, the notebook split, and every open cell split already call `applySplitPane`. They pick up 11. A collapsed card has no inner split and pays nothing.

5. In `collapseControlPanel`, the open height is `connectionPanel.getPreferredSize().height + splitPane.getDividerSize()`. Remember `getDividerLocation()` only when it is at least that tall. Then set the location to 0. A reading of 0, a sliver, or a half-open drag leaves `expandedDividerLocation` alone.
6. `expandControlPanel` already floors at preferred height plus `getDividerSize()`. Leave that formula. It follows the constant.
7. Delete the literal `8` in `collapseControlPanel`.

## 4. N5 — Inspector rule

**File:** `org.aetheris.swing.AetherisNotebookPanel`. Method `refreshInspector`.

`assemble` flattens roles and drops the cell boundary. Do not insert a rule after every two lines of that flat list.

1. Walk the same cells `assemble` walks, in that order. For each cell that contributes text, write its role lines, then a rule before the next contributing cell.
2. `INCLUDED` writes the user line and the assistant line, with no rule between them. `INPUT_ONLY` writes the user line. `OUTPUT_ONLY` writes the assistant line. `EXCLUDED` writes nothing and gets no rule.
3. The rule is twelve hyphens on their own line. Soft wrap leaves that on one row.
4. The header `working set → id` stays first. `est. tokens` stays last. Neither is wrapped in a rule.
5. One contributing cell produces no rule. A later state click still calls `refreshInspector`, and the rules close up around the cells that remain.
6. The inspector stays one `JTextArea`.

## 5. N8 — Thinking spinner

**File:** `AetherisNotebookPanel.ThinkSpinner`.

1. While spinning, the label base is `#4169E1`. Each 120 ms frame paints the moving glyph in `#00FF66` and the word `thinking` in `#4169E1`. The frames stay `|`, `/`, `-`, `\`.
2. One label can carry both colors. Set the text after `applyLabel`, on each tick, so the theme foreground does not win.
3. `stopSpin` clears the text and hides the label, as it does now.
4. The cell border stays `#3CB371`. `INPUT_GREEN` stays `#00FF66`.

## 6. N2 — User on the status line

**File:** `SshClientApp`. The frame status bar built in the constructor. Methods `aetherisStatus`, `selectSession`, `sessionConnected`, `sessionDisconnected`, `setStatusDisconnected`.

1. Add a second label on the east of that same bottom bar. `statusLabel` stays on the west and keeps today's messages.
2. The east label shows the focused session's username when that session is `CONNECTED`, the connector is AMWP, and the last request has a username. Otherwise the east label is empty.
3. `aetherisStatus` still sets only `statusLabel`. The user name remains.
4. Refresh the east label from `selectSession` and from the connect and disconnect paths.
5. The Aetheris footer label stays `vault:` plus the mode. It does not become the user name.
6. The user name uses the status line's existing foreground.

## 7. N4 — Incrementor

**New type:** `org.aetheris.swing.Incrementor`. First line of the type is `// `. No lambdas. A listener interface with one method, implemented by an anonymous class.

**Files:** `Incrementor`, `AetherisNotebookPanel` (`buildFooter`, `CellCard`, `applyUiFont`), `SshClientProperties`.

## 7. N4 — Incrementor

2. `+` and `-` change the count by one and apply immediately. They stop a pending type timer. The minimum is 1. Minus at 1 stays 1.
3. A typed value uses a non-repeating Swing timer of 800 ms, restarted on each edit. When it fires, apply. Empty and non-numeric leave the last good value in the field. A number below 1 applies as 1.
4. There is no ceiling.

6. Both open panes use the count. Set each open area's row count, and set the preferred and maximum height of each open pane from the row count times the current line height. The open card's maximum height becomes header plus those two panes plus the divider and the borders. Remove the open-card cap of 280. Text past the visible rows scrolls inside the pane.
7. The collapsed brief stays one line, or two when the header is at least two line-heights tall. The inspector and the View modal do not take the count.
8. `applyUiFont` recomputes the pixel height from the saved row count.
9. Load the count before the first canvas build, and restore it into the widget.

## 8. N1 — End of the pane

1. On the land path (`pendingLandOnInput`), after `validate()`, set that viewport's view position Y to the maximum, `viewHeight - extentHeight`, floored at 0. Then `focusPrompt()` on the new card.
2. Do not call `scrollRectToVisible` on the card or on the user area for this path. That is the U1 land behavior this package replaces.
3. When the new card fits in the viewport, the end position shows the whole card. When the card is taller, the bottom stays in view.
4. A collapsed card with no user area still moves the viewport to the end.
5. New turn and Clear turns keep the whole-card scroll they have now.
6. `rebuildTabs` still clears `pendingLandOnInput` when a hold is armed, then calls `restoreHold`. Header, collapse, and `IEPO` keep that path.

## 9. N3 — Chooser size and folder

**Files:** `SshClientProperties`, `SshClientApp.sendFileRequested`, `AetherisNotebookPanel` import path, `AetherisNotebookPanel.Host`.

1. Keys on the same shell file as N4: `ui.chooser.x`, `ui.chooser.y`, `ui.chooser.width`, `ui.chooser.height`, `ui.chooser.directory`.

## 9. N3 — Chooser size and folder

3. `showOpenDialog` disposes the dialog before it returns. Build the dialog with `createDialog`, listen for close, and read `getBounds` there. Those bounds are still valid after dispose.
4. Write the size on every close, including cancel. Write the folder only when a file is approved. The folder is the parent of the chosen file. A later refused read, over 1 MiB or a NUL in the first 8 KiB, still keeps that folder.
5. A missing or empty directory opens at the platform default. A saved rectangle that misses every current screen, or a size below the dialog's usable minimum, opens at the platform default size. Keep the folder when it still exists.

7. Do not point the `.aetheris` export door at this helper. That door is not in this slice.

## 10. N7 — Connected-user pull-down

**Files:** `AetherisNotebookPanel.buildFooter`, `SshClientApp` session focus.

## 10. N7 — Connected-user pull-down

2. The combo lists sanitized usernames of connected AMWP sessions. A connecting pane and a disconnected pane are omitted. Two sessions with the same sanitized name are one entry. An illegal name still becomes `anon`, and two illegal names share that one entry and that one vault.
3. Choosing a name calls `selectSession` on the matching session. If the focused session is already that user, choose it and do not reload the vault. Otherwise choose the first connected session with that name. `syncAetherisVault` remains the vault door. Run stays on `focusedAmwpConnector`. The mux stays up.
4. Guard the combo so a programmatic selection does not call `selectSession` again.
5. Refresh the list from `selectSession`, connect, disconnect, and pane close. When the focused session is not a connected AMWP user, clear the selection. Do not close the open vault.
6. When the vault user actually changes, call a stop on `ThinkSpinner` before the bind. The listener clear already drops the in-flight frame. The SSH session stays connected. A same-user focus change does not stop the spinner and does not reload.
7. Cap the combo's maximum width so a long name does not push Run off the bar. The tooltip shows the full name.

## 11. N6 — Held

No code. No kit. Prod 1.0 stays frozen.

## 12. Smoke, by hand

No JUnit task. Do not claim these clicks passed unless they were clicked.

1. Run a cell. The canvas sits at its end and the caret is in the new turn's input. New turn still brings the top of the new card into view.
2. Click `I` on a card. The view stays put.
3. Set the incrementor to a large row count. The open panes grow past the old 280 cap. Run again. The bottom of the new card is in view.
4. Collapse a card. The brief stays one line, or two when the header is tall.
5. The inspector shows a hyphen rule between cards, and an included card's two lines stay together. `E` removes that card's lines on that click.
6. The frame status line shows the focused user. A Run message does not erase the name. A disconnected focus clears the name.
7. Resize either file chooser, cancel, and open the other chooser. The size is back. Approve a file from a folder, then open the other chooser. That folder is back.
8. The pull-down lists connected users. Choosing one selects that terminal and shows that vault. The spinner stays at the outer east corner. The same username does not reload.
9. A split bar is visibly thicker, and the one-touch arrows sit on it. Collapse the connection panel from a tall drag, then expand. The tall height is back.

Compile with `mvn -q -DskipTests compile` from ` Leave `application.yaml`, `Casserole.vtg`, the whist caches, and `.idea/workspace.xml` unstaged.

## 13. Gate

Approval of this plan does two things.

1. Stamp this document as the canonical plan, write the copy, and update the, the manifest, the, and memory. Local commit.
2. Name the Java build of N9, N5, N8, N2, N4, N1, N3, and N7, in that order. N6 is not in the build.

The build is the next act after approval. It does not wait for a second word.

Stage 2 stays closed. Prod 1.0 stays frozen. Do not edit the locked 006 body. Do not edit plans 001 or 002.

# Aetheris — Stage 1 tweaks plan — serial 004

Java waits until names the build. N6 is not in the build.

> *The notebook already stands. Five paint and layout notes on that notebook. One pipe. Four context states. No Stage 2. Prod 1.0 stays frozen.*

# Aetheris — Stage 1 tweaks plan — serial 004

**No Lombok.** **No lambdas in our types.** Anonymous classes only. `// ` on the one new type, `org.aetheris.swing.SoftWrapArea`, and only when a later build creates it. Class stays `org.casserole.app.ui.ssh.SshClientApp`. Shell launch stays `java -cp` plus LocalM2Classpath. Never `exec:java`. Do not edit `src/main/resources/application.yaml`. Do not cut a seed-kit.
**semantic-key:** PK-732
**semantic-key:** PK-732

## 0. Locked answers this plan uses

## 1. Goal and non-goal

**Goal:** After a later named build, the inspector lists the newest card first, the pull-down stays on the outer east corner while the spinner sits on the west of that capsule, Aetheris buttons are 40 percent shorter and every `applyButton` target has a raised bevel, Swing scrollbar arrows show light gray with a black arrow at each end, and the inspector wraps to the pane width while soft wrap is on.

**Non-goal:** Java on the approval turn. A change to `assemble`. Character wrap. Moving the open cell panes or the View modal onto the handler. A saved off-state for the wrap checkbox. The terminal's own scrollbar. A shell-button height cut. N6. Stage 2. A seed-kit. A fifth cell state. Grok. An edit to plans 001–003, to 006, or to the 007 body.

## 2. Slice order

```
N11 Spinner west, pull-down east
N12 Short Aetheris buttons, bevel on every button
N13 Scrollbar arrows
N10 Inspector newest first
N14 Soft-wrap area on the inner inspector
```

N14 is last so the handler wraps the reversed text and leaves N13's horizontal arrows for the wrap-off state. N6 is not in this order.

The approval turn stamps paper only. Five packages wait until names the build. When he does, compile with `mvn -q -DskipTests compile` from ` No JUnit task. Leave `application.yaml`, `Casserole.vtg`, the whist caches, and `.idea/workspace.xml` unstaged.

## 3. N11 — Spinner and pull-down

**File:** `org.aetheris.swing.AetherisNotebookPanel`, `buildFooter`.

## 3. N11 — Spinner and pull-down

1. `east.add(thinkSpinner, BorderLayout.WEST)`.
2. `east.add(userCombo, BorderLayout.EAST)`.
3. The capsule stays `footer.add(east, BorderLayout.EAST)`.
4. The west flow stays Run, New turn, Clear turns, Preview, the incrementor, `footerStatus`.
5. Keep the combo preferred width 160 and maximum width 180, and keep the tooltip as the full name. `usersAdjusting` stays. Choosing a name still calls `host.focusConnectedUser`.
6. A hidden spinner is skipped by `BorderLayout`, so the combo alone sits on the outer east corner. A visible spinner takes its preferred width on the west of the capsule. The capsule grows leftward. The combo stays pinned to the east edge.

## 4. N12 — Button height and bevel

**Files:** `org.casserole.app.ui.ssh.SshClientTheme`, `org.aetheris.swing.AetherisNotebookPanel`.

`applyButton` today sets a flat `CompoundBorder` of `LineBorder(fieldBackground)` and `EmptyBorder(4, 10, 4, 10)`. `applyFontTree` then derives every button, toggle, label, checkbox, and combo to the live `uiFontSize`. There is no separate Aetheris height.

### 4.1 Bevel, every button

Inside `applyButton`, replace the flat line with a raised bevel. Suggested border: `CompoundBorder` of `BevelBorder(BevelBorder.RAISED, highlight, shadow)` and the same `EmptyBorder(4, 10, 4, 10)`. Highlight is lighter than `buttonBackground`. Shadow is darker. Fill, foreground, opaque, content-area filled, and focus-not-painted stay.

Callers already covered, and they keep today's height: shell Connect, Disconnect, Reconnect, Clear, Send file, New Term, Close pane, paste OK and Cancel. Aetheris callers are listed in 4.2.

### 4.2 Height, Aetheris only

The short set is Run, New turn, Clear turns, Preview, the incrementor `+` and `-`, the card buttons (`IEPO`, collapse, View), and the View modal Close. The incrementor field, the `soft wrap` checkbox, and the shell buttons are not in this set.

`fitAetherisButton(AbstractButton)` lives on the notebook panel:

1. Mark the button with client property `aetheris.short`.
2. Set its font to the live `uiFontSize` and apply the pre-slice flat border (line plus `EmptyBorder(4, 10, 4, 10)`). Read that preferred height. That reading is today's height, `H`.
3. Target height is `max(1, round(H * 0.6))`.
4. Call `applyButton`, which installs the bevel.
5. Step the font down from `uiFontSize` until the preferred height is at most the target, and stop at 8 pt.
6. Set preferred size to the fitted label width and the target height. Set maximum height to the target. Do not freeze the width from the tall font.

`applyUiFont` calls `applyFontTree` and then refits every marked button still in the tree, so a shell font change does not restore the tall Aetheris buttons. Card buttons are recreated by `rebuildTabs`, so construction calls `fitAetherisButton` too. The View modal is modal, so Close is fitted when the dialog opens. A font change while that dialog is up is out of this slice.

## 5. N13 — Scrollbar arrows

**Files:** `SshClientTheme`, `AetherisNotebookPanel`, `PasteEditorDialog`, `SshClientApp.showHelp`.

`installLookAndFeelDefaults` sets `ScrollBar.background` and leaves the arrow buttons to the platform look. The shell never calls `UIManager.setLookAndFeel`.
**semantic-key:** PK-841

1. Add `applyScrollPane(JScrollPane)` on the theme. It installs one UI on the vertical bar and the same UI on the horizontal bar.

## 5. N13 — Scrollbar arrows

3. `createDecreaseButton` and `createIncreaseButton` each return a button with a non-zero preferred size, opaque, face `#C0C0C0`, and a black triangle for that orientation. Both ends stay present on vertical and horizontal bars.
4. Call `applyScrollPane` on every Swing pane we build:
 - notebook canvas (`new JScrollPane(column)`)
 - inspector inner pane and inspector outer pane inside `wrap`
 - each open cell's user pane and assistant pane
 - the View modal pane
 - the paste-editor pane
 - the Help dialog pane in `showHelp`
5. Do not touch the JediTerm terminal bar.

While N14 wrap is on, the inspector text's horizontal bar is not shown, so its arrows exist for the wrap-off state. Vertical arrows stay.

## 6. N10 — Newest card first

**File:** `AetherisNotebookPanel.refreshInspector`.

Today the walk is oldest first. `inspectorPrior` skips `EXCLUDED` and empty ephemeral cells. An included prior writes the user line, then the assistant line, when each text is present. `INPUT_ONLY` writes the user line. `OUTPUT_ONLY` writes the assistant line. `inspectorTarget` skips `EXCLUDED` and always writes one user line. `appendInspectorLine` numbers during that walk, so the oldest line is 1. The header is written, then the blocks with `------------` between them, then `est. tokens`.

1. Keep that collection, including who is skipped and which role lines a card contains.
2. Build each block without a display number. Token totals still accumulate during the walk. Order inside a block stays user then assistant.
3. Reverse the block list before paint. The target card, when it contributes, becomes the first block under the header. Older cards follow downward.
4. Then number. The first painted role line is `1`. The next line is `2`. A two-line included card keeps both numbers together, user then assistant.
5. Write `------------` only when `i > 0`, after the reverse, so the rule still sits between cards and the first card has none above it. One contributing card still produces no rule.
6. Header first. `est. tokens` last. Early `(empty canvas)` returns stay.
7. Do not edit `assemble`, `appendCell`, or the canvas land path.

## 7. N14 — Soft wrap

**New type:** `org.aetheris.swing.SoftWrapArea` extends `JTextArea`. `// ` on that type. Anonymous listeners only.

The inspector is constructed as this area. `inspectorSoftWrap` already starts `true`. The checkbox already starts checked. `setLineWrap(true)` and `setWrapStyleWord(true)` already run, and the area still reports the longest line, so the pane scrolls sideways when the divider moves.

`wrap` builds a header (title plus `soft wrap`) and an inner `JScrollPane` around the area, then an outer `JScrollPane` around that panel. The handler attaches to the inner pane. The open cell panes and the View modal keep ordinary `JTextArea` with `setLineWrap(true)`. They are later seats.

### 7.1 The area

1. `setSoftWrap(boolean)` stores the flag, calls `setLineWrap` and `setWrapStyleWord` with that flag, and revalidates.
2. `getScrollableTracksViewportWidth` returns the flag.
3. While the flag is on, `getPreferredSize` uses the parent viewport's width when that width is positive, and the superclass height. That is what lets a divider move shrink the column. While the flag is off, `getPreferredSize` is the superclass longest-line size.
4. A `ComponentListener` on the inner viewport calls `revalidate` on resize. That covers a divider move, and `setSoftWrap` covers a toggle while the divider stays still.
5. A scrollbar policy alone is not the fix. The track, the preferred width, and the listener travel together.

### 7.2 The bars

1. The outer inspector pane's horizontal policy is `HORIZONTAL_SCROLLBAR_NEVER` for the life of the pane, so the long line cannot open a second horizontal bar. Its vertical policy stays the default.
2. While wrap is on, the inner pane's horizontal policy is `HORIZONTAL_SCROLLBAR_NEVER`.
3. While wrap is off, the inner pane's horizontal policy is `HORIZONTAL_SCROLLBAR_AS_NEEDED`. N13's arrows are already on that bar.
4. The inner vertical bar stays.

### 7.3 The switch and the words

1. Launch state stays on. The checkbox writes `inspectorSoftWrap` and calls `setSoftWrap`. Nothing writes that flag to `ssh-client.yaml` or the vault.
2. Wrap is on words. Do not enable character wrap. The twelve-hyphen rule has no spaces and stays one row at a normal inspector width. The UUID in the header has no spaces and stays one token. A very narrow pane may leave those tokens wider than the pane. Previews have spaces and fold.
3. `assemble` still sends oldest first. N10 still paints newest first. Wrap is paint on this one area.

## 8. N6 — Held

No code. No kit. Prod 1.0 stays frozen at ` Next kit is cut only when says he is ready.

## 9. Smoke, by hand, after a named build

No JUnit task. The approval turn does not click these. Do not claim them passed unless they were clicked. Plan 003 §12 stays unclicked.

1. With the spinner hidden, the user pull-down sits on the outer east corner. Start a Run. The spinner appears on its west and the pull-down stays on that corner.
2. Run, New turn, Clear turns, Preview, `+-`, and the card buttons are visibly shorter. Shell Connect and the paste buttons keep their height. Every one of those buttons shows a raised bevel. The fills are unchanged.
3. The canvas, an open cell, the inspector, View, paste, and Help show a light-gray arrow button with a black arrow at each end of each Swing bar.
4. Several included cards: the newest block is under the header, older blocks follow, a hyphen rule sits between blocks, and an included block still shows user then assistant. The top line is numbered 1. `E` removes that card's lines on that click. The next Run still sends oldest first.
5. Soft wrap starts checked. Drag the inspector divider. Preview lines fold to the new width. The hyphen rule and the UUID stay one row at a normal width. Uncheck `soft wrap`. The long line returns and the horizontal bar, with N13's arrows, returns. Check it again. The bar leaves and the text folds, with the divider left where it is.

## 10. Gate

**The build is not named.** No Java on the stamp. Five packages in section 2 wait until names the build in a later sentence. When he does, compile with `mvn -q -DskipTests compile` from ` No JUnit task.

Do not edit its body. Do not edit 006. Do not edit plans 001, 002, or 003. Stage 2 stays closed. Prod 1.0 stays frozen. N6 stays held.

# Aetheris — Stage 1 tweaks plan — serial 005

N15 is the requirement.
This stamp confirms that tree.

> *The notebook already stands. One column width on the inspector. One pipe. Four context states. No Stage 2. Prod 1.0 stays frozen.*

# Aetheris — Stage 1 tweaks plan — serial 005

**No Lombok.** **No lambdas in our types.** Anonymous classes only. No new type. `SoftWrapArea` already carries `// `. Class stays `org.casserole.app.ui.ssh.SshClientApp`. Shell launch stays `java -cp` plus LocalM2Classpath. Never `exec:java`. Do not edit `src/main/resources/application.yaml`. Do not cut a seed-kit.
**semantic-key:** PK-733
**semantic-key:** PK-733

## 0. Locked answers this plan uses

| ID | Lock |
|---|---|
| N15 | The inspector soft-wrap width is a character count. Default 80. It is saved. While soft wrap is on, the inspector wraps at that count. |
| Where | A second `Incrementor` sits beside the `soft wrap` checkbox. Minimum 1. Plus and minus apply immediately. A typed value waits 800 ms. Tooltip `soft wrap columns`. |
| Rows | The footer incrementor still sets card rows. |
| Save | Key `ui.aetheris.inspector.wrap.columns` in `~/.casserole/ssh-client.yaml` through `SshClientProperties`. A missing key means 80. Not in `applyDefaultsForMissingKeys`. Not a vault column. Not `application.yaml`. Roux does not carry it. |
| Checkbox | Still starts on. It is not saved. |
| Width | While wrap is on, preferred width is the column count times the live mono character width. The viewport does not stretch it. While wrap is off, the longest line returns. |
| Bar | The inner inspector bar is `AS_NEEDED` in both states. A column wider than the pane scrolls, and N13's arrows sit on that bar. The outer bar stays hidden. |
| Words | Word wrap stays. The hyphen rule and the UUID stay one token. A full preview is 80 characters of body plus the `N. role` prefix, so at 80 a long preview folds once. Raising the count keeps it on one row. |
| Seats | Open cell panes and the View modal stay off this handler. |
| Send | `assemble` stays oldest first. N10 stays newest first. |
| N6 | Held. No kit. Prod 1.0 stays frozen. |
| Filed | This plan does not reopen 001 through 004. |
| Models | `select model` and `list models` stay. |

## 0. Locked answers this plan uses

## 1. Goal and non-goal

**Goal:** The inspector wraps at a saved character width, default 80, from the control beside the checkbox. Approval stamps this plan and confirms that build.

**Non-goal:** A second implementation of a match. A saved checkbox. A pixel width. Viewport tracking. Character wrap. Moving the cell panes or the View modal onto the handler. A change to `assemble`. N6. Stage 2. A seed-kit.

## 2. Slice

```
N15 Inspector wrap columns
```

One package. A match stays. A gap would have been fixed on the approval turn. Compile with `mvn -q -DskipTests compile` from ` No JUnit task. Leave `application.yaml`, `Casserole.vtg`, the whist caches, and `.idea/workspace.xml` unstaged.

## 3. N15 — Wrap columns

2. `setWrapColumns` stores at least 1. While wrap is on, `getPreferredSize` width is that count times `getColumnWidth`, plus insets. Height is the wrapped span at that width. While wrap is off, preferred size is the superclass longest line.
3. `wrap` reads `host.shellWrapColumns()`, floors a bad value at 80, and calls `setWrapColumns` before the scroll pane is shown.

5. The inner pane's horizontal policy is `HORIZONTAL_SCROLLBAR_AS_NEEDED`. The outer pane's horizontal policy is `HORIZONTAL_SCROLLBAR_NEVER`. `applyScrollPane` still paints N13's arrows.

8. The checkbox listener only calls `setSoftWrap`. It does not write the shell file.

## 4. N6 — Held

## 5. Smoke, by hand

No JUnit task. The approval turn does not click these. Do not claim them passed unless they were clicked. Plan 003 §12 and plan 004 §9 stay unclicked.

1. Soft wrap starts checked. The control beside it reads 80. Long previews fold on the prefix. The hyphen rule and the UUID stay one row.
2. Press `+`. The column grows at once and the next launch still shows that count.
3. Type a number and wait. The column follows. Clear the field. The last good count returns.
4. Uncheck `soft wrap`. The long line returns and the horizontal bar can return. Check it again. The saved column returns.
5. Set the column wider than the inspector. The inner bar shows the light-gray arrows. Narrow the count under the pane. That bar leaves.
6. The footer incrementor still changes card rows. A font change keeps the same character count and retargets the pixel width.

## 6. Gate

A clean match does not get an empty second commit.

Do not edit its body. Do not edit plans 001, 002, 003, or 004. Stage 2 stays closed. Prod 1.0 stays frozen. N6 stays held.

# Aetheris — Stage 1 plan — serial 001

Build P0–P9 from **this** document.

# Aetheris — Stage 1 plan — serial 001

S1-01…S1-22 are from the stamped pre-plan. **No Lombok.** **No lambdas in our types.** on **new** types.

## 0. Locked answers this plan uses

| ID | Lock |
|---|---|
| Q8 | Class stays `org.casserole.app.ui.ssh.SshClientApp` |
| | `openRam(String regionId)`. Bantha = nickname |
| | Region `sanitize(user) + ".aetheris"` (dot) |
| | `@ComponentScan` includes `org.aetheris` |
| | ASM fail → stop. No Boot 4 |
| | `// <KEY>` on new Stage 1 Java |
| Vault | Default **ram**. Disk opt-in. Shell only |
| Idle | Default **never disconnect** |
| Esc | Sticky `cmd>` until Esc again |
| Merge | CONCAT is Stage 2 — not this plan |
| Shell launch | `java -cp` + LocalM2Classpath. Never `exec:java` |
| Leftovers | `MinimalSshServer`, `SshServerTest`, unused JPA — leave |

## 1. Goal and non-goal

**Goal:** Stage 0 scripts still start both processes → OpenSSH raw PTY unchanged → Aetheris Shell Connect → **SSH** tab is today’s splits → **Aetheris** tab is canvas + inspector → Run a cell to Ollama over AMWP → disk vault survives Shell restart → wrap/idle/sticky-cmd pass.

**Non-goal:** CONTEXT-PIN, MOOD-LAYER, groups, pipes, MCP `0x0004`, Grok cloud, export, LCARS, Graph, `TOOL_EXEC`, Boot 4, class rename, Stage 0 re-implementation.

## 2. Slice order (build this sequence)

```
P0 Tab A defects: wrap / NAWS, idle, sticky cmd CRITICAL
P1 Theme keys, icon, collapse-on-resize
P2 pom Hikari+H2 compile; AetherisYaml keys; AetherisWorker
P3 Vault DDL + PURE-JDBC DAO (ram default)
P4 Roux openRam + ComponentScan org.aetheris
P5 AMWP byte mux both sides (Init or raw)
P6 LlmProvider SPI + swap command
P7 Product tabs + cells + inspector + execute (needs P3–P6)
```

## 2. Slice order (build this sequence)

## 3. P0 — Tab A defects

**Files:** `SshSessionController`, `SshClientApp`, `SshTerminalPanel` / JediTerm size hook, `ChatShell`, `CasseroleProperties` / `application.yaml`, client `SshClientProperties`.

### Wrap / NAWS

1. On connect, keep sending PTY cols/rows (`setPtyColumns` already).
2. On **every pane resize**, send window-change / NAWS with live `TermSize`.

### Wrap / NAWS

3. ChatShell: listen for window-change; set `terminalColumns`; `FormatContext` follows.
**semantic-key:** PK-734
**semantic-key:** PK-734

4. Input-line redraw wraps at `terminalColumns` (CR + VT100). No concatenating full-buffer redraw past the pane.
5. No size codes (OpenSSH default): keep 80.

### Idle

1. Client yaml: `ui.connection.idle-timeout-ms` default **0** = never.
2. MINA client session idle = that value (0 disables).
3. Server: operator SSH must not IdleTimeout at 600s. Configurable; default disabled / “operator session”.

### Sticky cmd

1. Keep `handleBareEscape` toggle.
2. **Do not** call `returnToChatOrIdle()` after a successful command except `exit` and `chat` / `chat start`.
3. Empty Enter in COMMAND reprints `cmd>`.
4. Welcome: “Escape toggles command mode.”

**Verify:** long line wraps; idle >10 min stays up; Esc → `status` → still `cmd>`.

**:** `G-22G-23G-24STICKY-CMD` on the methods that realize them.

## 4. P1 — Theme, icon, collapse

**Files:** `SshClientProperties`, `SshClientTheme`, `SshClientApp`, `Ansi` (prompt SGR).

| Key | Default |
|---|---|
| `ui.terminal.color.cursor` | translucent electric blue (focused) |
| `ui.terminal.color.cursor.inactive` | darker paler of focused |
| `ui.color.menu.foreground` | matrix-rain green (menu **font** only) |
| `ui.terminal.color.foreground` | keep |
| `ui.prompt.chat.foreground` | green |
| `ui.prompt.cmd.foreground` | blue |

Server `Ansi.mediumBlue("cmd> ")` stays named; add green `chat>`. Do not add a yaml parser family.

**Collapse:** while connected, `componentResized` re-asserts divider 0. Disconnect restores last expanded height.

If pixels lag, placeholder black square + motif is acceptable; do not block P7.

## 5. P2 — Yaml, deps, worker

**pom.xml:** HikariCP + H2 **compile** (H2 may already be runtime — promote). Do **not** add a Spring `DataSource` bean on Casserole. Do not add Flyway, JPA usage, SnakeYAML.

## 5. P2 — Yaml, deps, worker

## 6. P3 — Vault

**Package:** `org.aetheris.vault`

## 6. P3 — Vault

| Table | Role |
|---|---|
| `workspace` | user workspace |
| `canvas` | canvas tabs |
| `cell` | turns + state + CLOBs |
| `trace` | execute traces |

No `@scope` / tag tables. Burner: row may exist; CLOBs NULL.

**Modes:** `ram` → `jdbc:h2:mem:aetheris` (default). `disk` → `~/.aetheris/{sanitize(user)}/vault`. Crash-proof only in disk.

All JDBC on `AetherisWorker`.

Search: H2 `LIKE` required. No Lucene.

## 7. P4 — Bantha Roux

**Files:** `RouxChatMemory`, `RouxEngine` (façade as needed), `CasseroleApplication`.

```
```

- Never `{spill.dir}/{user}/`
- Never exclusive FileLock

## 7. P4 — Bantha Roux

`@ComponentScan(basePackages = { "org.casserole", "org.roux", "org.aetheris" })`

## 8. P5 — AMWP

**Packages:** `org.aetheris.wire` (TOON + frame codec), server mux beside ChatShell (`org.aetheris` façade; implementation may sit with SSH_IO).

| Item | Value |
|---|---|
| Magic / version | `0xAE` / `1` |
| Endian | LE |
| Max | 1 MiB; fragment bit2 |
| Channels | `0x0001` T, `0x0002` A, `0x0003` stream. **Not** `0x0004` |

## 8. P5 — AMWP

OpenSSH `ssh -p 2222` must remain raw. Last-ditch: if Init handshake fails, **fall back to raw**; do not brick Tab A.

## 9. P6 — Provider SPI + swap

**Preserve:** ChatShell still calls `LlmService` (adapter or same type implementing the SPI).
**semantic-key:** PK-735
**semantic-key:** PK-735

**`swap <from> <to>`:** Tab A command; existing HTTP keep_alive / stop / load.

Per-cell dropdown: host Ollama tags + stored `execution_target`. `air-gap: true` parsed; no cloud target in S1 (no-op fail-safe).

## 10. P7 — Notebook chrome + execute

**Files:** `SshClientApp` + new `org.aetheris.swing.*`

```
AetherisShell
 connection strip (collapsed when connected)
 JTabbedPane
 canvas tabs
 Aether Space | inspector (working-set preview)
 footer: Run | New turn | Preview
 status: vault ram|disk, AMWP, engine
```

- Split tree **stays inside SSH**. Do not reparent JediTerm.
- No Graph button.
- Four-state **modal**; pin = burner.
- Completing Turn N materializes empty N+1.
- Hotkeys on Aether Space root only: Ctrl+Shift+I/E/P/O.
- Aetheris tab **disabled** unless focused session is AMWP-capable.

**Assembly (client):** walk `cell_order` before target → skip EXCLUDED → skip empty burner → INPUT_ONLY user → OUTPUT_ONLY assistant → INCLUDED both → append target prompt. Inspector shows list + token estimate.

## 10. P7 — Notebook chrome + execute

**Execute:** AetherisWorker builds working set → AMWP execute → server `openRam` region **clear + full push** → `LlmService.complete` on **LLM_EXEC** → stream/chunk back (one Data+EOF allowed). Cells **never** enter vt100 Roux. Tab A `message find` returns no canvas turns.
**semantic-key:** PK-736
**semantic-key:** PK-736

## 11. P8 — Scripts, docs, tags

- Shell stays `java -cp` + LocalM2Classpath. Never `exec:java`.
- `bin/docs/` — one README per script family, argument **tables**, `cat`/`less` readable.
- Scripts default localhost / no WAN. WAN is Ollama (and later Grok), not boot.
- Every **new** type/method this plan names gets `// …`. No Stage 0 backfill.

## 12. P9 — Smoke (manual, no JUnit)

Stage 1 is **not done** if any fail.

1. `ssh -p 2222 user@host` — raw `chat>` / `cmd>` as today (no AMWP).
2. Shell Connect — SSH tab splits, paste, focus, Close Pane as today.
3. Aetheris tab **disabled** on that OpenSSH-style raw session; **enabled** on AMWP session.
4. New turn → four-state → Preview → Run → Ollama output in the cell.
5. Disk vault: restart Shell, cells remain. Ram: gone (expected).
6. `message find` on Tab A does **not** list canvas turns.
7. Line longer than the pane **wraps**; no concatenating redraw.
8. Connected >10 min — still up.
9. Esc → `cmd>` → `status` → still `cmd>` → Esc → `chat>`.
10. `bin/aetheris-shell` with no WAN (after one online compile).
11. Second disk login same user still exclusive on **vt100** Roux; Bantha is RAM (no FileLock fight).

## 13. Last-ditch scenarios (do this **before** calling the stage done)

| If this happens | Do this | Never do this |
|---|---|---|
| AMWP Init gets no reply | Raw PTY; Aetheris tab disabled | Wrap JediTerm anyway |
| Mux bytes appear in the terminal | Defect. Opaque T. Stop slice P5 | “Parse a little ANSI” |
| `mvn package` ASM major 70 | Stop. | Boot 4 |
| Yaml “needs SnakeYAML” | Extend hand parser | New yaml stack |
| JDBC on EDT / freeze | Move to AetherisWorker | `SwingWorker` spaghetti |
| Cells show up in `message find` | Dual-write bug. Fix Bantha isolation | “Filter them in the formatter” |
| Region id with `:` | Refuse. Dot only | Colon “for readability” |
| CONTEXT-PIN / Graph / MCP itch | Out of this plan | “Small extra while we’re here” |
| Session drop mid-P7 | Git is the TX. Finish the slice | TX-PENDING in the manifest |
| | | New REQ catalog |

## 13. Last-ditch scenarios (do this **before** calling the stage done)

## 14. Files / components (checklist)

**Do not touch:** Roux paging/identity core, JediTerm split logic (except resize NAWS), `users.yaml` auth, leftovers, Stage 0 Lamina engine shape (reuse LLM_EXEC).

## 15. House rules for implementers

- `PRESERVE-ALL` — Tab A must still drive if AMWP is off
- `NO-LAMBDA` in our types; **no Lombok**
- JavaDoc, `@author `, end-of-class banners
- `PURE-JAVA` / stop-and-ask
- English first; on new NPCs
- Do not implement from creepy-futures or from this plan’s Out table

## 16. Done when

- [ ] OpenSSH raw path unchanged
- [ ] No H2 opened by Casserole
- [ ] No `TOOL_EXEC` pool
- [ ] No Graph button, no CONTEXT-PIN
- [ ] New Stage 1 types carry
- [ ] `bin/docs/` exists for scripts we touched

*Last-ditch: if Init fails, stay raw. If wrap fails, P0 is not done. If Face sneaks CONTEXT-PIN, pities the plan. Stamp this,, and we light the wrench.*

# Aetheris Stage 2 plan 001

It does not start Java. It does not move `STAGE`. It does not retarget `WORKING` or `ACTION`. Java waits until names the build.
**Where this page is thinner, the locked serial wins.** This page does not rewrite the stage map or the constitution.

## 0. Stamp record

- Stamp clock: 2026-10-20 23:42.
- The copy's first line points at the canonical file.
- `HEADS.S2_PLAN` and `HEADS.S2_PLAN_COPY` name those two paths. `WORKING` stays the stage map. `STAGE` stays `1` / `BUILT`.
- `WHEN` stays `2026-10-19`. `PHASE` and `MODE` say the plan is stamped and the build is not named.
- `application.yaml` was not edited on this filing.
- This stamp does not start Java. The next word is his name of the build.

## 1. Goal and non-goal

**Goal:** One stamped run-book a later named build can follow, in the locked package order, without inventing a row the serial does not hold.

**Non-goal:** Java on this approval. A second spec. A SQL grammar. Incremental Roux. Lucene. Contract agents. Flux. Dual-write. Tool behavior. A parent path for `mood-shape` or `burner-key`. A third vault default. Shrinking `LLM_EXEC`. `num_predict`. Temperature, max-tokens, or top-p. A push.

## 2. Floor the build must keep

- Class `org.casserole.app.ui.ssh.SshClientApp`. Launch is `java -cp` plus `LocalM2Classpath`. Never `exec:java`.
- No lambdas in our types, no Lombok, no JUnit task unless he asks. Anonymous classes only.

## 2. Floor the build must keep

- Four states stay. Ghost is the `EXCLUDED` paint. No fifth state. No `@scope`.
- One tab and one vault follow the focused VT100 user. Inspector paints newest first. Send walks oldest first.

- Bantha `openRam(sanitize(user)+".aetheris")` stays a full push. `NO-DUAL-WRITE`. Tab A region `{user}`. Aetheris region `{user}.aetheris`.
- AMWP: `0x0001` Scope T, `0x0002` Scope A, `0x0003` stream. `0x0004` is added in package 8 and is not allocated before that package. `0x0100` is not allocated.
**semantic-key:** PK-842

- `num-ctx` stays in `spring.ai.ollama.chat.options.num-ctx`. Named value `4096` is already committed. `0` still means the server omits `num_ctx`. Do not copy the key into `aetheris.yaml`.
- Compile gate for a later named build: `mvn -q -DskipTests compile` from `

## 3. Package order

Build in this order. A later package may use a type the earlier package added. Do not pull a later package forward.

## 3. Package order

## 4. Package 1. LEX-FILTER

**Done means:** The pin filters on the inspector. OK commits non-matches to `EXCLUDED` and promotes an excluded match to `INCLUDED`. A notebook search field hits only the open canvas. Open migrates a version-1 vault to version 2, then selects. An unknown state token fails that card only. `mvn -q -DskipTests compile` passes. No tool column.

**Files:** `org.aetheris.swing.AetherisNotebookPanel`, `org.aetheris.vault.VaultStore`, `org.aetheris.vault.VaultSql`, `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`.

1. Pin on the inspector rail. While it is on, every keystroke retokenizes. Split on whitespace and punctuation. Drop stopwords `a`, `the`, `and`, `of`, `to`. Remaining tokens are AND substring matches, case-insensitive, against cell input and cell output. Burner NULL CLOBs are not searched. Matches highlight. Non-matches dim. Nothing is written until OK.
2. 1. On OK, non-matches become `EXCLUDED` and paint as ghosts. No fifth state.
3. 2. A match that was `EXCLUDED` is promoted to `INCLUDED`. `INPUT_ONLY` and `OUTPUT_ONLY` stay. After the commit, the four-state buttons still override.
4. The pin stays on until the operator turns it off. Turning it off clears highlights and leaves `EXCLUDED` as written. A new cell is filtered live the same way. No new wire. No new AMWP channel.

## 4. Package 1. LEX-FILTER

6. One open path, the method that already probes collapsed. Read `schema_version`, apply the ordered step, then SELECT. A vault with the Stage 1 tables and no version row is version 1. Version 2 adds nullable `group_id` and `execution_status` together, then writes the row. A version row that names a step while a column is missing re-runs that step. `SELECT_CELLS` does not name `group_id` before the step. `PROBE_COLLAPSED` and `ALTER_COLLAPSED` stay version-1 history. No new `INFORMATION_SCHEMA` probe per column.

8. `tool_calling_enabled` is not a version-2 column. It waits for package 8's hooks and still is not turned on there.
**semantic-key:** PK-843

## 5. Package 2. Execute seams and MOOD-LAYER

**Done means:** One walker feeds the send and the inspector. Mood is a second system line after an approved mini-chat. `LlmRouter.complete(target, prompt)` has only Ollama registered. The server enforces air-gap from `~/.aetheris.yaml`. Init carries `SessionFacts`. The facade mutex covers clear and add and ends before `complete`. A fourth Aetheris execute waits on a semaphore of 3. The pool stays 4.
**semantic-key:** PK-737
**semantic-key:** PK-737

## 5. Package 2. Execute seams and MOOD-LAYER

**Files:** `AetherisNotebookPanel`, `org.aetheris.ssh.AmwpFacade`, `org.aetheris.wire.AmwpCodec`, `org.aetheris.wire.AmwpToon`, `org.aetheris.config.AetherisYaml`, `org.casserole.llm.LlmProvider`, `org.casserole.llm.SpringAiOllamaLlmService`, `org.casserole.ssh.shell.ChatShell`, `org.casserole.lamina.LaminaEngine`, new `org.casserole.llm.LlmRouter` (`// `).
**semantic-key:** PK-738
**semantic-key:** PK-738
**semantic-key:** PK-738
**semantic-key:** PK-738
**semantic-key:** PK-738
**semantic-key:** PK-738
**semantic-key:** PK-738
**semantic-key:** PK-738
**semantic-key:** PK-738

1. One walker. Send oldest first. Inspector newest first. `k` is the 1-based index among cells the walker kept. Inspector paint numbers are not `k`. Skip `EXCLUDED`. Skip an empty burner. Honor the four states. The target cell is not a source for itself.

4. `ChatShell` builds the Tab A prompt (the casserole system line only) and calls the router with target `ollama` and region `{user}`. The Aetheris walker builds the working set. The facade calls the router with the cell target and region `{user}.aetheris`. Only the Ollama provider sets `num_ctx`, and only when the integer is greater than 0. Tab A `swap` still loads the local model.
5. `SessionFacts` rides the existing `TYPE_INIT` reply. Fields: air-gap `true`, `false`, or `unset`, and `num-ctx` as an integer that is always present from a new server. An empty Init body is "not reported," not 0. An old client that only checks the type stays safe.
6. The server reads `~/.aetheris.yaml` of the process user through `AetherisYaml`. Key `aetheris.network.air-gap`. Explicit true refuses cloud. Explicit false allows. Missing loads as unset and refuses. Unset is not a false that allows. No client-asserted air-gap bit. The stored target is not rewritten. The shell lookup stays `--aetheris-yaml`, `./aetheris.yaml`, `~/.aetheris.yaml`. A mismatch blocks the shell's cloud send. The server still enforces.
7. `AmwpCodec.fits` sits next to encode. `FRAG_PREFIX_BYTES` (24) plus body length against `MAX_PAYLOAD` (1 MiB). Walker and reply path both call it. Over the cap, the cell is `FAILED` with reason `too-large`. Encode is not asked to throw. Scope A stays one chunk: `frag_index` 0 and `frag_count` 1. Do not reassemble `frag_count`.
8. One facade method holds a per-region mutex around clear and every add, and releases it before `complete`. Not Roux's lock. Not `tryAcquireLock`. Lock order is the facade lock first, then Roux's per-call lock inside clear and add. Never the reverse. `complete` uses the local `Prompt` and does not read the region back. A second execute waits. It does not fail at 50 ms. Roux itself is not changed.
9. The in-flight placeholder goes in before `lamina.submit`. Today's check-then-put is not the lock. A second frame for a live cell gets `TYPE_ERROR` reason `cell-busy`.
10. One `TYPE_ERROR`. Body is TOON from `AmwpToon`: `reason` and `message`. Reasons: `cell-busy`, `failed`, `air-gap`, `engine-down`, `too-large`. A body that does not start with `reason:` still displays as today's plain failed banner. `cell-busy` paints the status line only. It does not `clearRun`, write `FAILED`, or stop the spinner.
11. A semaphore of 3 lives in `AmwpFacade`, acquired before `lamina.submit` for an Aetheris execute and released in the task `finally`. `ChatShell` does not take it. `LLM_EXEC` stays `Executors.newFixedThreadPool(4)`. Do not shrink the pool. Do not change `OLLAMA_NUM_PARALLEL`. Do not set `num_predict`.
12. `mood-shape` holds the shaper model id. No parent path. The default is the Ollama model in use. Read the key by that spelling through `AetherisYaml`. If that reader cannot load a bare key, stop and ask. Do not invent a parent segment. A missing id, an air-gap skip, or an unreachable shaper skips mood. The filter commit stands. The modal says "keyword only."
13. The mini-chat is a modal `JDialog` on the Aetheris worker. It is not a canvas tab, not Tab A, and not a canvas cell. 3. It is ephemeral and burner. Cancel keeps the filter commit and adds no mood row. Approve appends one system-role line for the whole conversation. That line is not written into cell CLOBs. The inspector shows it. `MOOD-LAYER` does not run again until the next OK.
**semantic-key:** PK-844

## 6. Package 3. Grok on that router

2. An empty stored target goes out as `ollama` and is not written back.
3. Grok `complete` does not set `num_ctx`.
4. Key lookup order: environment, then `~/.aetheris/`, then `~/.casserole/`. A missing key fails the cell. The key is not logged. It is not stored in cell text unless the cell is a burner.
5. An unknown provider fails the cell and leaves the stored target.

7. Tab A `swap` stays. ChatGPT and Claude stay.

## 7. Package 4. Groups

1. The algorithm token stays `CONCAT`. Paste in order.

3. `cell-busy` paints the status line only. The other four reasons (`failed`, `air-gap`, `engine-down`, `too-large`) write `FAILED` on that cell only and leave siblings alone. Air-gap failure does not rewrite the stored target.
4. `bindUpdate` writes the same mutable columns the insert already writes, plus `group_id` and `execution_status` once version 2 exists. The argument is the canvas card from load or new cell. No second DTO. `COMPLETED` writes the assistant CLOB and the status. `FAILED` and `CANCELLED` write the status and leave the CLOB. The server does not open H2. A data frame stores `COMPLETED`. Reason `failed` stores `FAILED`. Reason `cell-busy` does not.
5. `group_id` null means sequential. `execution_status` null means never run. Tokens are null, `COMPLETED`, `FAILED`, `CANCELLED`. No fifth cell state. No `output_status` column.
6. Day-one group replies stay on channel `0x0003`, demuxed by cell id. One data frame and one EOF per cell. One-shot complete. `0x0100` is not allocated.
**semantic-key:** PK-845

## 8. Package 5. Pipes

**Done means:** `{{cell[k].output}}` is resolved on the client from memory before the frame. Raw braces are not sent. A non-empty CLOB is a source even after `CANCELLED`. `FAILED` stops the pipe.

1. A non-empty CLOB is a legal source, including when the latest status is `CANCELLED`. An empty CLOB plus `CANCELLED` is not a source.
2. Latest status `FAILED` stops the pipe even if an older CLOB remains. `CANCELLED` does not by itself fail a sibling or stop the chain.
3. An ephemeral cell cannot push. An ephemeral source, a bad `k`, a cycle, or a source that is not a legal CLOB source fails the pipe.
4. The chain gesture is Ctrl+Shift+F5.
5. Over 1 MiB, prefix included, fails the cell with reason `too-large`. Scope A stays one chunk.

## 9. Package 6. Export and import

**Done means:** A `.aetheris` TOON file, version 1, round-trips as a copy. A second import of the same file does not collide. Burner bodies are stripped unless the operator asks to keep them.

1. Own door.
2. On import, each cell id becomes a new UUID minted for that import. Parent, branch, group members, and `{{cell[k].output}}` are rewritten. The import is a copy.
3. Counted serials stay 1-based: `k`, vault schema, export version, depth, pool, and N. A one-chunk frame keeps `frag_index` 0 and `frag_count` 1. That wire index is not part of the 1-based rule.

## 10. Package 7. Crosstalk

**Done means:** With nothing highlighted, the new cell receives the last line the terminal returned, and it starts `EXCLUDED`. A highlight still wins. The operator must ask. A cell is never pushed onto the PTY without confirm, and an ephemeral cell is never the source.

1. N is 1 when nothing is highlighted. The constitution table still says "last N lines. The operative reading is this plan and the locked serial. Do not rewrite the constitution file.
2. A cell fence onto the PTY on Scope T needs confirm.

## 11. Package 8. Tool hooks only

2. Add `TOOL_EXEC` on `org.casserole.lamina.Lane` as a pool of 1. A second tool task waits. It does not fail. `LLM_EXEC` stays 4.
3. `tool_calling_enabled` absent means false. This plan does not turn it on. The column, if stored, is a later schema integer after version 2. It is not part of the version-2 step.
4. Depth is 1. The allowlist is empty. No tool is invocable. What a tool does is not defined. Cells stay prompt-only.
5. Stays on the lane.

## 12. Out of the named build

- SQL design serial. The idea stays. This plan does not invent the grammar. The serial does not exist.
- Incremental Roux. Full push stays. Preferred later. `NO-DUAL-WRITE` stays.
- Lucene,, LCARS, the Graph button, contract agents, DataStax, Flux, dual-write, SnakeYAML, Boot 4, N6, a seed-kit cut.
- `num_predict`, a smaller `LLM_EXEC` pool, temperature, max-tokens, top-p, and any change to Ollama parallelism.
- ChatGPT and Claude. Parent paths for `mood-shape` and `burner-key`.
- Rust or Node replicas. Source stays Swing.

## 13. Hand smoke, later

Do not click these on the filing turn. Do not claim them passed. A GUI pass is a separate ask.

## 14. What this stamp did

The viewer approval is the stamp. One local commit on `AA-SDD_14`.

The stamp does not start Java. It does not move `STAGE`. It does not retarget `WORKING` or `ACTION`. The next word is his name of the build.

# Aetheris pre-plan 001

- **Serial:** pre-plan.001

- Transfer Conversation copies the Roux session onto the canvas and the canvas back into the Roux session. He named a and said to file it as a Feature. Java is named first. Rust is named. This page does not open an F row. Seats are filed on the pointer.

- **State:** LOCKED. 2026-11-01 01:35. The formal plan is stamped from this page. The build is named.

- **Mode:** pre-plan. Locked.

- Filed the dependency seat. Filed the landing on the plan. Named the menu.

- **Source:**, 2026-11-01 01:35. File it as a Feature. Where this page is thinner, the feature spec wins. Page carries a pointer and does not open an F row.

- One feature. Transfer Conversation copies the Roux session onto the canvas and the canvas back into the Roux session.

- **Rank:** the locked feature spec binds this page.

[NEW ON 2026-11-01 01:35]

> * recommends the menu. The words are Transfer Conversation.*

No Lombok. No lambdas in our types. No new public type. No new package. No new channel. No new AMWP type. No new library. The public methods are named on the plan.

## 1. What this slice is

Transfer Conversation copies the Roux session onto the canvas and the canvas back into the Roux session. Java moves first. Rust follows.

## 2. What stays

This page does not open an F row. File, Tools, and Help stay. The menu ink stays. The context menu charcoal stays. The configured prompt stays. No yaml edit. The card-round hunks stay unstaged.

## 3. Side move

`session_replace:` stands on the existing channel A door. The sibling is `session_export:`.

## 3. Side move

The console mood leaves because `clearTurns` drops it. The sibling is the configured prompt, which stays.

A running canvas cell refuses the transfer after OK. The sibling is the running flag.

The plan names each move, the requirement, and the sibling.

Wire stanza. Empty. No system line is added, removed, or reordered. The door is channel A text `session_replace:` on the nil cell. The reply is `session_replace_result:` on the same path.

# Aetheris Grok API pre-plan 001

- He locked the Grok serial and named this page. He approved the neighbor yield and named the formal plan. This page locks as the source.

- **State:** LOCKED. Packages 7 and 8 are in this cake. The formal plan is stamped from this page.

- That serial is locked. Where this page is thinner, the locked serial wins.

- **What it is:** the sketch a later plan will be cut from. The packages below are the batch.

- **What it is not:** a stamped plan. A license to write Java. An copy in `docs/plans/`. A rewrite of the locked serial, the stage map, or the constitution.

- He named the build in that stamp.

- **Rank:** the locked serial binds this page. The stage map and the constitution still win if a sentence drifts. Repair this page.

> *One chat POST. The key is the bearer token. Hello proves the key. The cell prompt is the later body. Thinking paints the strip. The vault stores the reply.*

Throwing the sketch away is a success. A gap he marks here opens. Do not clobber this file. Do not reopen the locked serial for that gap. A requirement this page does not already inherit from the locked serial is written on a spec serial first.

No Lombok. No lambdas in our types. Anonymous classes only in product UI. `// ` on a new type when a later build creates it. SSE is a line read beside the JSON object reader. Internal TOON and stay on their own readers.

## 1. What this slice is

The Grok chat call on the notebook, cut from the locked serial. Stage 2 plan 001 already shipped the keyless invite and the one-shot JSON reader. This slice is the rest of that call.

**Done means:** a normal cell, burner pin off, target `grok/` plus a model id, both air-gap words explicit `true` and equal. Run passes the local checks, sends one `Hello` when this server process has no valid key yet, then sends the cell prompt with the bearer token. The greeting is discarded. The cell stores the prompt's reply. With the thinking control on, Grok thinking paints the strip and the reply is still the stored text. The same control on an Ollama cell sets `think` true and paints `message.thinking` on that strip. An empty model stops as `no-model` with `no model defined`. A burner cloud hop follows the checkbox.

**Not done:** a stamped plan, Java, a copy in `docs/plans/`, a constitution edit, a stage-map edit, tools, `deferred`, search, the Responses API, `grok-4.7` as the factory id, `num_ctx` on a Grok call, the key written on a page.

## 2. In / Out

### 2.1 In

| ID | Work | Locked reading |
| --- | --- | --- |
| 1 | Doors, before any POST | One air-gap predicate on both doors. `no-model` before `readKey`. `burner_cloud` on the execute frame for provider `grok`. |
| 2 | Probe gate | One `Hello` for the server process. The same Run then sends the cell prompt. |
| 3 | Cell body and clocks | `temperature` `1`. `top_p` omitted. Cell cap `128000`. Probe cap `16`. HTTP timeout `1800`. Connect `10`. |
| 4 | Document | Modal and `~/.casserole/aetheris-grok.yaml`. Factory model `grok-4.3`. |
| 5 | Thinking | `think_stream` on the TOON for both providers. SSE kinds `t` and `c`. Ollama `think` through Jackson. |
| 6 | Cancel | `TYPE_CANCEL` aborts that cell's HTTP call. The shared `Hello` still finishes. |

### 2.2 Out

| Item | Why |
| --- | --- |
| Tools, `deferred`, search | Off this release. |
| Responses API, image, video, voice | The call stays `POST https://api.x.ai/v1/chat/completions`. |
| `grok-4.7` as factory | The docs recommend it. The factory id stays `grok-4.3`. |
| `num_ctx` on Grok | Only Ollama sets `num_ctx`. |
| Constitution and stage map | Row 14. This slice does not edit them. |
| Two `ObjectMapper`s | This slice does not unify them. |

## 3. As-built (what the later plan must not forget)

| Surface | Today | This slice |
| --- | --- | --- |
| `GrokLlmService.completeFor` | Jackson writes `model`, `messages`, `stream` false. Connect `10`. Timeout `120`. Reads `choices[0].message.content` when it is text. | Adds the probe body, the cell fields, the `1800` cap, and the SSE line read. The JSON object reader stays for `stream` false. |
| Empty model | Shares `KeyMissingException` with an empty key. The facade says `grok key missing`. | `no-model` and `no model defined` before `readKey`. An empty key with a model present stays `grok key missing`. |
| Key lookup | `casserole.grok.api-key`, then `XAI_API_KEY`, then `~/.aetheris/grok.key`, then `~/.casserole/grok.key`. | Same order. The value stays off this page. On the wire it is the bearer token. |
| Air-gap | Shell `blockedCloud` returns before the frame. Server `refusesCloud` stops a frame that arrives. Both words are explicit `true`. Unset refuses. A mismatch blocks the shell. | One predicate, both doors. A normal `grok` cell may pass. The constitution file stays. |
| Burner | The shell refuses every burner cloud hop. The server reads `notebook.burner-key` only if a frame arrives. | `burner_cloud` rides beside `ephemeral` for provider `grok`. `notebook.burner-key` stays the door for every other cloud provider. |
| Execute slots | A semaphore of `3` in `AmwpFacade`. | Stays `3`. The probe gate is a different lock. |
| Group merge | `absorbConcat` writes when every member is `COMPLETED`. `markFailed` sets `FAILED` on that one cell. | Package 8 fails the merge when any member is `FAILED` or `CANCELLED`. |
| Roux region | `execute` clears the region, then calls the model outside that lock. | Package 7 holds the lock through `complete`. |
| Cancel | `TYPE_CANCEL` drops the run token. `HttpClient.send` is not aborted. | This slice aborts that cell's HTTP call. |
| Error slide | One red skin. | |
| Ollama | `SpringAiOllamaLlmService` sets `numCtx` when it is greater than `0`. It does not set `think`. | `think` true when `think_stream` is on. Jackson reads `message.thinking`. |
| Document | The yaml file does not exist. The modal is not built. | Package 4 creates the file on confirm and builds the modal. |
| Thinking strip | The spinner is the running clock. The strip is not built. | Package 5 builds the strip. The spinner stays the clock. |
**semantic-key:** PK-739
**semantic-key:** PK-739
**semantic-key:** PK-739

## 3. As-built (what the later plan must not forget)

## 4. Work packages

Suggested order for the plan: 1, 2, 3, 4, 5, 6, 7, 8. The doors and the probe come before a cell body that can leave the machine. The document feeds the body. Thinking rides that body. Cancel closes the call. The region lock wraps that call. The merge rule reads the states those calls store.

### 1. Doors

- One predicate for the 22:51 exception. `blockedCloud` and `refusesCloud` both call it.
- A normal cell, burner pin off, provider `grok`, model present, both air-gap words explicit `true` and equal, may pass.
- Unset still refuses. A mismatch still blocks the shell. A burner still stops. Any other cloud provider still stops on `air-gap`.
- The constitution file stays as written. The yaml files stay explicit `true`.
- Empty model is checked in the shell and in `completeFor`, before `readKey`. Reason `no-model`. Message `no model defined`. The vault token stays `FAILED`.
- An empty key with a model present stays `grok key missing`.
- The execute frame carries `burner_cloud` beside `ephemeral`. The shell copies the checkbox. For provider `grok` the server reads that bit.
- Unchecked: the shell stores `FAILED`, status `burner denied`, and does not send the frame.
- Checked: the shell sends. A local burner still runs.
- `notebook.burner-key` stays the server door for every other cloud provider. The checkbox does not write that flag and does not write the air-gap word.

### 2. Probe gate

- The gate lives in the server process that reads the key, beside one valid-true flag. The flag matches that one key.
- States are none, in flight, and valid. The check and the claim share one lock. The lock is not held during the POST.
- A Run that finds valid sends that cell's prompt with `Authorization: Bearer` and the key.
- A Run that finds none becomes the leader and sends one `Hello`. The body is `model` equal to the target id, one user message whose content is `Hello`, `stream` false, and `max_completion_tokens` `16`. `temperature` and `top_p` stay off that body. The cell prompt stays off that body.
- A Run that finds in flight waits. It does not send its own `Hello`.
- A 2xx with readable content sets valid and discards the greeting. The leader and each waiter then send their own cell prompts.
- A miss leaves the gate at none. Each of those cells stores `FAILED` and does not send its prompt. The next `grok/` Run may probe again.
- A `401` on a later call clears valid. A server restart clears the flag. A shell restart does not. The shell does not keep a second copy.
- The probe belongs to the process. Cancelling the leader cell does not abort `Hello`. That cell stores `CANCELLED` and does not send its prompt.
- `cell-busy` is not this gate. The three execute slots stay three cell calls.
- A wire miss opens the existing error slide. A local miss uses the banner already named for it. No second window.
- The call stays `POST https://api.x.ai/v1/chat/completions`.

### 3. Cell body and clocks

- The JSON object reader already in `GrokLlmService` stays the `stream` false path.
- The cell body sends `model`, `messages`, and `stream` false when the thinking control is off.
- Roles on `messages` are `system`, `user`, and `assistant`. Any other role is `unknown role`.
- The cell sends `temperature` `1` and omits `top_p`. `max_completion_tokens` is `128000` on the cell. A 400 that names the cap is the slide text.
- `reasoning_effort` rides only when the pulldown is not `omit`. `grok-4.3` stays omit only.
- Tools, `deferred`, and search stay off the wire.
- Assistant text is `choices[0].message.content` when that value is text. Empty content fails as `grok empty content`. A non-text value fails as `grok content shape`. A missing choice fails as `grok response has no content`.
- The non-stream reader does not read `reasoning_content` and does not join content parts.
- When this package is built, the Grok HTTP timeout is `1800` seconds and connect stays `10`. Until that build the tree stays at `120`.
- A death at the HTTP cap is a hard transport miss.
- The two clocks keep two jobs.
- Grok does not send `num_ctx`.

### 4. Document

- The file is `~/.casserole/aetheris-grok.yaml`. It is not `ssh-client.yaml`, not `aetheris.yaml`, not `application.yaml`, and not a vault column.
- `version` is `1`. A file whose version is not `1` is left unread and is not overwritten. Unknown keys are dropped on the next write. A key found in the file is ignored and is not written back.
- The standing default and each cell entry hold `api`, `model`, `reasoning_effort`, `stream`, `burner_cloud`, `temperature`, and `max_completion_tokens`. `top_p` stays off the document.
- Factory values: `api` `chat`, `model` `grok-4.3`, `reasoning_effort` `omit` for `grok-4.3`, `stream` false, `burner_cloud` false, `temperature` `1`, `max_completion_tokens` `128000`.
- The sample id `grok-4.6` in the locked serial stays a shape. It is not the factory selection. This page does not adopt `grok-4.7`.
- The modal is the editor. Four pulldowns stay the menu: api chat, model, reasoning effort, stream. The burner checkbox and the two number fields sit on that same modal.
- A new Grok turn copies the nearest earlier cell's document. The user may edit or leave it. An edit sticks to that cell.
- SnakeYAML is already on the client. The hand reader for `aetheris.yaml` stays the hand reader.
- The file is created on the first confirm. This pre-plan does not create it.

### 5. Thinking

- One per-cell control, factory off, carried as `think_stream` on the execute TOON for both providers. No new vault column.
- Grok on sends `stream` true. The body is read as SSE lines. `reasoning_content` goes out as kind `t`. `content` goes out as kind `c`. Both ride `CHANNEL_STREAM`.
- `TYPE_DATA` is the finished reply, once, at the end. The cell stores the joined `c` text. A thinking chunk is not a `TYPE_DATA`.
- The spinner stays the running clock. Strip paints `t`.
- The strip sits above the BB1 bar and under the main canvas. Three lines are viewable. A `v` minimizes it and restores it. A call that produces no thinking hides the strip. It comes back when thinking occurs again.
- The widget stands as a read-only `JTextArea`. While a run is producing thinking, the strip shows that run. A group shows the member that is running. The last member that produced thinking stays up until a later call hides the strip or replaces the text.
- Thinking text drops with the session. It is not a vault column, not an inspector line, and not a later turn's messages.
- Ollama on sets `think` true on `complete()` and does not stream. Jackson reads `message.thinking` from that JSON. `message.content` stays the reply. The same strip paints the thinking text.
- The control off leaves Grok at `stream` false and leaves Ollama `think` unset. No level string. No probe of `/api/show`.
- Names the tip for `v` in the turn the strip is built.

### 6. Cancel

- `TYPE_CANCEL` drops the run token and aborts that cell's HTTP call.
- `stillMine` still drops a reply that arrives late.
- The cell stores `CANCELLED`. The in-flight reply is not stored.
- Cancelling the leader of a probe does not abort the shared `Hello`. Package 2 owns that rule.

### 7. Roux region lock

- The facade mutex stays around clear and every add, and this build holds it through `complete`. The wire body stays the `Prompt` already built.
- The hold is that user's region, so an Ollama complete on the same user waits inside it too.
- The semaphore of 3 stays. A second call for that user waits on the mutex.
- The locked Stage 2 pre-plan, package 2, still says the mutex is released before `complete`. That sentence stays on that page. This package is the yield.

### 8. group fail

- Any member `FAILED` or `CANCELLED` fails the merge. `CONCAT` does not run. A member that already stored a valid reply stays.
- The merge stores `FAILED` and its reply stays blank.
- A hard miss is red. A soft miss is yellow. `cell-busy` is not a failure and does not open the slide.
- A group whose only miss is `CANCELLED` stores `FAILED` on the merge and does not open the error slide.
- Page stays the source.

## 5. Neighbors

The two neighbors moved into packages 7 and 8. This page does not keep them outside the cake.

## 6. Gate

He named the build in that stamp.

The locked serial is the source. This page is the batch.

# Aetheris — Stage 0 pre-plan — serial 001

Do not clobber. Implement from the Stage 0 **plan**, not this file.
**Cadence:** Q18 — Stage 0 pre-plan → Stage 0 plan → build → return to the finalized spec → Stage 1.

> *The van starts. No cells. No AMWP. No vault schema. Tab A still has to drive like it does today.*

Serial this file if marks gaps. Do not clobber.

## 1. What Stage 0 is

Foundation so Stage 1 can stand on JDK 26, scripts, logging, Lamina, and an Aetheris Shell *name* — without inventing the notebook.

**Done means:** clone → fetch Zulu 26 → start Casserole and the desktop app from `bin/` → existing SSH/`chat>`/Roux restore/splits/`users.yaml` behave as today (raw PTY). Log4j2 is what you tail. Lamina is what runs *our* server work.

**Not done:** Aetheris product tab, AMWP, H2 vault, cells, Grok, MCP, LCARS.

## 2. In / Out

### 2.1 In

| ID | Work | Spec lock |
|---|---|---|
| S0-01 | Compiler + runtime **JDK 26 Zulu**. Never Oracle. Bump `pom.xmljava.version` 25 → 26 | |
| S0-02 | `bin/` start + install scripts (Windows 11 ps1/cmd/bat, Linux bash, macOS). Bind the fetched Zulu JRE | §21 |
| S0-03 | `fetch-zulu-jre` — download Azul Zulu **26** JRE; scripts reference it | §21 |
| S0-04 | `install-ollama` — download/install Ollama | §21 |
| S0-05 | `bootstrap-env` — Windows env deps, **new installs only** | §21 |
| S0-06 | `casserole` script — start the Boot server on the bound JRE | §21 |
| S0-07 | `aetheris-shell` script — start today’s `SshClientApp` on the bound JRE | §21 / Q8 |
| S0-08 | Window title **Aetheris Shell** (today: `"Casserole SSH Client"`). Class/package **stay** `org.casserole.app.ui.ssh.SshClientApp` | Q8 |
| S0-09 | `aetheris.yaml` load (`--aetheris-yaml` → `./aetheris.yaml` → `~/.aetheris.yaml`). Parse + apply **title** (and log config path if present). **Do not open H2** | §12 / Stage 0 out: vault schema |
| S0-10 | **Log4j2 both processes.** Exclude Boot Logback. Config = **`.properties`**. App config stays yaml. Annotation support | Q13 |
| S0-11 | **Lamina (LTEM)** `org.casserole.lamina`. Lanes `ssh-io`, `llm-exec`, `general`. Metrics **OFF**. No virtual threads | §23 |
| S0-12 | Migrate **all existing Casserole server thread usage we own** onto Lamina | §23 |
| S0-13 | `PRESERVE-ALL` — Tab A raw-PTY, Roux restore, splits, commands, formatters, `users.yaml` still work | |
**semantic-key:** PK-740
**semantic-key:** PK-740

### 2.2 Out (do not put in the Stage 0 plan)

| Item | Belongs |
|---|---|
| AMWP / mux / Scope A | Stage 1 |
| JTabbedPane product tabs / Aether Space / cells | Stage 1 |
| H2 vault, Hikari, DAO, SQL constants, schema | Stage 1 |
| `{user}:aetheris` Roux region | Stage 1 |
| Provider SPI + `swap` command | Stage 1 |
| Parallel groups, pipes, fork, merge (`CONCAT`) | |
| MCP, Grok adapter, crosstalk, `.aetheris` export | Stage 2 |
| Class move to `org.aetheris.shell` | Dedicated rename serial (Q8) |
| Remove leftover JPA + mem H2 | |
| True multi-writer Roux | `SCRATCHED` |
| `@scope` | `SCRATCHED` |
| Virtual threads | Plan B only, lock |
| `AetherisWorker` (client JDBC pool) | Stage 1 (Shell vault) |
| New ops spec serial | Optional companion; this pre-plan holds the script requirements until then |

## 3. As-built (what the plan must not forget)

Grounded in the tree today. Stage 0 changes these; it does not rewrite Roux or JediTerm.

## 3. As-built (what the plan must not forget)

| Surface | Today | Stage 0 target |
|---|---|---|
| `pom.xml` `java.version` | **25** | **26** |
| Boot parent | 3.5.0 | |
| Logging | Boot default **Logback** (no log4j/logback file in-tree) | Log4j2 `.properties`, both processes |
| Window title | `super("Casserole SSH Client")` | `Aetheris` from yaml, fallback `"Aetheris Shell"` |
| Client entry | `SshClientApp.main` / IDE run | `bin/aetheris-shell` + still IDE-runnable |
| Server entry | `mvn spring-boot:run` / `CasseroleApplication` | `bin/casserole` + still `mvn` |
| Scripts | none (`bin/` does not exist) | §2.1 S0-02…S0-07 |
| `aetheris.yaml` | does not exist | load in Stage 0; vault keys may be present but **unused** until Stage 1 |
| Client config | `~/.ssh-client.properties` (UX spec) | **Unchanged.** Do not merge into `aetheris.yaml` in Stage 0 |
| Server yaml | `src/main/resources/application.yaml` | Unchanged keys. Do not add a Spring H2/datasource for Aetheris |
| JPA + `jdbc:h2:mem:casserole` | Unused leftover | **Leave it** (Q7) |
| H2 Maven dep | runtime, unused | Leave on classpath (Stage 1 Shell will use it; server still must not open a vault) |
**semantic-key:** PK-741
**semantic-key:** PK-741

### 3.1 Server threads we own (must migrate — S0-12)

| Location | Today | Lamina lane |
|---|---|---|
| `SshServer extends Thread` name `casserole-sshd` | Dedicated thread; `InitRunner` calls `start()` | `ssh-io` — **stop extending Thread**; submit start/keepalive to Lamina |
| `ChatShell` `readThread` `casserole-chat-shell` | `new Thread(this, …)` per connection | `ssh-io` |
| `ChatShell.llmExecutor` `casserole-llm` | `Executors.newSingleThreadExecutor` per connection | `llm-exec` |
| `ChatShellescapeTimer` / `spinnerTimer` | `java.util.Timer` daemon | **Keep as timers** unless the plan proves they are “thread usage” worth a lane. Leave `Timer` in Stage 0; do not invent a scheduler SPI |
| MINA `IoProcessor` / NIO | Library-owned | **Do not replace** |
| Tomcat (web starter) | Unused | **Do not migrate into Lamina** |
| Roux `ReentrantLock` / seqlock | No worker threads | **Unchanged** (Q2) |
**semantic-key:** PK-742
**semantic-key:** PK-742
**semantic-key:** PK-742

### 3.2 Client threads (not Lamina)

Lamina is **Casserole server only**. Do not route these:

- `SshClientAppssh-send-file`

### 3.2 Client threads (not Lamina)

- Swing **EDT**

### 3.3 Leftover / do not use

- `MinimalSshServer` (`echo-shell` thread)
- `SshServerTest` (pty-pump)

Stage 0 plan may delete or ignore. Do not migrate them into Lamina.

## 4. Work packages (what the Stage 0 *plan* must cover)

Order is a suggestion for the future plan, not a schedule.

### WP-A — JDK 26 Zulu (S0-01, S0-03)

- `pom.xml`: `<java.version>26</java.version>`. Compiler release 26.
- Maven toolchain / IDE run configs point at Zulu 26, not Oracle, not 25.
- `bin/fetch-zulu-jre`:
 - Windows 11, Linux, macOS.
 - Download a **JRE** (not a full JDK unless fetch fails — prefer JRE).
 - Vendor **Azul Zulu**. Fail loud if the URL/hash is wrong.
 - Install location: repo-local or `~/.aetheris/jre/` — pick one in the plan, document it.
 - It is then universal to any repo location. Also ensure scripts check for JAVA_HOME and ensure it is established in the script context; it should override any external env setting for the runtime of the script. However if it exists and is JDK 26, inherit from external env.
 - Scripts set `JAVA_HOME` / `PATH` to that JRE **before** `java -jar` / `mvn`.
 - I just said that previous bullet. You read my mind !
- Do not silently stay on 25.

### WP-B — Logging (S0-10)

**Server (Boot):**

- Dependency: `spring-boot-starter-log4j2`
- Exclude: `spring-boot-starter-logging` (Logback) from `spring-boot-starter-web` / parent
- Config file: `src/main/resources/log4j2.properties` (not XML)
- Prefer annotation / `@Log4j2` or a thin wrapper — house style is named types, not Lombok unless already in the tree. **As-built has no Lombok.** Use `LogManager.getLogger(Class)` in a small `org.casserole.log` helper if that keeps call sites clean. Do not add Lombok in Stage 0 without asking.
 - Add it to Laws please. The can fashion any accessor/modifier methods for me in the Workshop as needed. I prefer a clean Van.

**Shell (no Spring):**

- Log4j2 as a Maven dependency on the same artifact (already a single module)
- Config: `log4j2-shell.properties` on the classpath, or path from `aetheris.yaml`
- Init in `SshClientApp.main` **before** Swing work

| Level | Rule |
|---|---|
| INFO | Low chatter: start/stop, listen port, auth source, exclusive refuse |
| DEBUG | Useful, not exhaustive (no full prompts) |
| TRACE | Payloads allowed **except burner** (no burners exist yet; still write the rule) |

### WP-B — Logging (S0-10)

No Logback files left on the classpath.

### WP-C — `aetheris.yaml` load only (S0-09)

Lookup: `--aetheris-yaml <path>` → `./aetheris.yaml` → `~/.aetheris.yaml`. Missing file: built-in defaults, Shell still starts.

Stage 0 keys that **must** parse (even if Stage 1 adds more later):

```
 # vault / amwp / network: may appear in the sample file; Stage 0 ignores them
```

### WP-C — `aetheris.yaml` load only (S0-09)

Window title = `aetheris.ui.title` if set, else `"Aetheris Shell"`.

### WP-D — Product name (S0-08)

- `SshClientApp` constructor: title from yaml (WP-C)
- About / Help strings that say “Casserole SSH Client” → Aetheris Shell **only where they are the product name**. Do not rename Roux, Casserole server, or `chat>`
- `bin/aetheris-shell` is the operator-facing name
- **Keep** class `SshClientApp`, package `org.casserole.app.ui.ssh`

### WP-E — Scripts (S0-02, S0-04…S0-07)

New directory: repo-root **`bin/`**.

| Script | Windows | Linux | macOS |
|---|---|---|---|
| `casserole` | `.ps1` `.cmd` `.bat` | `.sh` | `.sh` (same bash) |
| `aetheris-shell` | `.ps1` `.cmd` `.bat` | `.sh` | `.sh` |
| `install-ollama` | `.ps1` | `.sh` | `.sh` |
| `bootstrap-env` | `.ps1` only (Windows new installs) | — | — |
| `fetch-zulu-jre` | `.ps1` | `.sh` | `.sh` |

### WP-E — Scripts (S0-02, S0-04…S0-07)

**`aetheris-shell`:** same JRE; `org.casserole.app.ui.ssh.SshClientApp`. Does **not** start Boot.

**`install-ollama`:** official install path per OS; idempotent if `ollama` already on PATH.

**`bootstrap-env`:** Windows 11 only, new machines: whatever is required before Java/Ollama (e.g. execution policy note). Do not invent a package manager stack. If nothing is required beyond Zulu+Ollama, the script says so and exits 0.

Linux flavor-agnostic: bash + `java`. No apt/yum hardcoding except inside `install-ollama` if the vendor script needs it.

### WP-F — Lamina (S0-11, S0-12)

**Package:** `org.casserole.lamina` (server). No Spring types inside the engine if avoidable; a thin `org.casserole.lamina.spring` adapter may wire Boot shutdown. Mirror Roux’s `org.roux` vs `org.roux.spring` split.

**API (shape for the plan, not code):**

- Submit work to a **named lane** with a named `Runnable` type (no lambdas in *our* types)
- Cancel in-flight work by token / `cell_id` is Stage 1; Stage 0 needs cancel of `chat>` in-flight (today `Future.cancel` on `llmExecutor`)
- Metrics collector: **exists, default OFF**. When ON: log-tail stats, not OTel yet

### WP-F — Lamina (S0-11, S0-12)

| Lane | Stage 0 users |
|---|---|
| `ssh-io` | `SshServer` listen loop; per-connection `ChatShell` read loop |
| `llm-exec` | `ChatShell` LLM complete (replaces `casserole-llm` executor) |
| `general` | anything else we own that is not the two above |

Later lanes (`tool-exec`, `roux-io`) **named in comments / enum stubs only** — do not allocate pools until a stage needs them.

**Threads:** `java.lang.Thread` via `ThreadPoolExecutor`. **Not** virtual threads.

**Migration mapping:** §3.1. After migration, `grep` of `org.casserole` (excluding `app.ui.ssh`, leftovers) should not show `new Thread(` or `Executors.new…` on the server path.

### WP-G — Preserve-all verification (S0-13)

1. `bin/casserole` listens on 2222; `users.yaml` auth still works
3. Disk exclusive: second login same user refused (red line) when `commit`
5. Desktop `bin/aetheris-shell` — Connect, splits, New Term, Close Pane, one-touch, focus/Connect enable, paste, Send file
7. Ollama down: Boot still starts; chat prints red error (today’s behavior)
9. Title bar reads Aetheris / Aetheris Shell
10. No AMWP wrapping — JediTerm still raw

If any of 1–7 regress, Stage 0 is not done.

## 5. House rules the plan must carry

## 6. Suggested `aetheris.yaml` sample (Stage 0 committed file)

```
```

Stage 0 reader: honors `ui.title` only. Extra keys must not crash the parser.

## 7. Log4j2 `.properties` expectations (plan-level)

Server `log4j2.properties`:

- Console appender for the operator
- File appender under `~/.casserole/logs/casserole.log` (server home, not `~/.aetheris/`)
- Root INFO; loggers `org.casserole` / `org.roux` / `org.apache.sshd` tunable
- Pattern with timestamp, level, thread, logger, message

Shell `log4j2-shell.properties`:

- File `~/.aetheris/logs/aetheris-shell.log`
- Console optional (don’t spam a Swing app); default file-only at INFO

Exact paths may be overridden later in yaml; pick the above as Stage 0 defaults unless vetoes.

## 8. Acceptance (Stage 0 done)

- [ ] `mvn -DskipTests package` on **JDK 26 Zulu**
- [ ] `bin/fetch-zulu-jre` then `bin/casserole` and `bin/aetheris-shell` on Windows 11, and script files exist for Linux/macOS
- [ ] Log4j2 only (no Logback on the runtime classpath)

## 8. Acceptance (Stage 0 done)

- [ ] No Aetheris tab, no AMWP frames on the wire, no H2 file under `~/.aetheris/{user}/vault`

## 9. Open items for (Stage 0 only)

Not Stage 1 questions.

**P0-1. Fat jar vs `mvn spring-boot:run` as the `bin/casserole` default?**
- A: Fat jar after package (clone-and-run).
- B: Always `mvn spring-boot:run`
- If it's easy, go ahead and add options in scripts for fat jar option. Otehrwise postpone to anotehr stage for finishing as the deems appropriate and satisfactory to s diligent discernment for precision.

**P0-2. Fetched JRE location?**
- A: `~/.aetheris/jre/zulu-26/` ( — product home)
- B: repo-local `tools/jre/`
- C: Other

**P0-3. Leave `java.util.Timer` (escape/spinner) off Lamina in Stage 0?**
- B: Move timers onto `general` now
- **Answer** A.

**P0-4. MINA NIO threads stay library-owned?**
- B: Other

If these four are, the Stage 0 **plan** can be written without another spec cycle.

We can proceed to stage 0 plan mode. Be sure to make a copy of the plan document in the plans directory for my review while the console is in plan mode. It helps me understand and also learn all of the salient details relative to the plans that are generated for builds. Thank you!

## 10. What happens after this file

3. On stamp, copy that plan to `docs/plans/`.

Do not start Stage 1 work from this document.

* filed the Stage 0 pre-plan. Counted every `new Thread` on the server. Face promised the title bar will say Aetheris, not a mystery acronym. No cells until the van actually starts.*

# Aetheris — Stage 1 tweaks pre-plan — serial 001

Do not clobber. Implement from the tweaks **plan**, not this file.
Drafted 2026-10-18 12:54.
**semantic-key:** PK-846

> *The notebook already stands. These are usability tweaks on that notebook. Tab A still drives. One pipe. Four context states. No Stage 2.*

The stamped Stage 1 pre-plan and the stamped Stage 1 plan stay frozen. This file does not edit them. Do not implement from this file.

# Aetheris — Stage 1 tweaks pre-plan — serial 001

## 1. What this slice is

Small usability on the Aetheris pane that Stage 1 already shipped.

**Done means:** a cell can collapse to its header plus a short output brief, and it comes back open with the same text. `IEPO` still mean the four states, and each has a tooltip. Input text is bright emerald. Output text is the console yellow. View reopens at the last size and place. A state click does not throw the scroll to the top. Right-click on a cell can import a text file into that cell's input. A footer button clears the canvas turns and leaves the VT100 session alone.

**Not done:** `select model`, `list models`, a fifth cell state, CONTEXT-PIN, Grok, MCP, a change to the working-set walk, a second row of state buttons, full-word labels, an import button on the header.

## 2. In / Out

### 2.1 In

| ID | Work | From 004 |
|---|---|---|
| T1 | `collapsed` boolean on `CellRecord`, persisted. Default false. Not a `CellState`. | Collapse |
| T2 | One card. Header buttons stay. Body hides. Brief is the assistant text, one line or two. Empty output leaves the brief empty. | |
| T3 | Tooltips on `IEPO`: Included, Excluded, Input only, Output only. Labels stay one character. | Context letters |
| T4 | Input foreground `#00FF66` on `#1A1A1A`. Border stays `#3CB371`. Output foreground `#FFFF55`. Ghost stays gray. | Text color |
| T5 | One View bounds preference beside the user, not on the cell. Off-screen falls back to 720 by 480, centered. | View bounds |
| T6 | Header rebuild restores `cardY - viewY` and keeps focus on that cell. | Focus hold |
| T7 | Right-click import into the cell's user text. Cap 1 MiB. No pipe push. | Import |
| T8 | Footer clear of the active canvas vault to one empty `INCLUDED` turn. VT100 untouched. | Clear turns |

### 2.2 Out

## 3. As-built (what the later plan must not forget)

| Surface | Today | This slice |
|---|---|---|
| `CellRecord` | No collapse flag | `collapsed`, default false |
| `VaultSql` cell table | No collapse column. `CREATE TABLE IF NOT EXISTS` will not alter a disk vault that already exists | DDL includes the column. An idempotent `ALTER TABLE` adds it for a vault that is already on disk |
| Working set in `AetherisNotebookPanel` | Skips `EXCLUDED`. Sends input for `INPUT_ONLY` and `INCLUDED`. Sends output for `OUTPUT_ONLY` and `INCLUDED` | Unchanged. Do not read `collapsed` here |
| `CellCard` header | `IEPO`, burner, View. No tooltips. State click calls `persistQuiet` then `rebuildTabs` | Same buttons, plus tooltips, plus one collapse toggle. No second set |
| Card body | Vertical split, user over assistant. Max height 280. Text `#E6E6E6` on `#1A1A1A`. Ghost is gray | Open card keeps the split. Collapsed card hides the split and shows the brief. Colors move. Ghost still wins |
| `rebuildTabs` | New scroll pane. `scrollRectToVisible` on the whole card pulls a tall card to the top | Header actions hold the viewport delta |
| `showViewModal` | Always 720 by 480, centered on the panel. Foreground `#E6E6E6` | Restores saved bounds. Foreground `#FFFF55` |
| Footer | Run, New turn, Preview | Adds Clear turns |
| Import | None | Popup on the card |
| `ClearCommand` | Drops `ChatSession` turns | Not called by the footer |
**semantic-key:** PK-743
**semantic-key:** PK-743
**semantic-key:** PK-743
**semantic-key:** PK-743
**semantic-key:** PK-743

## 3. As-built (what the later plan must not forget)

### 3.1 Threads

Swing stays on the EDT. Vault writes stay on `AetherisWorker`, the way `newTurn` and `persistQuiet` already do. The file read for import goes on that worker. The file chooser stays on the EDT. No new Lamina lane. No AMWP frame.
**semantic-key:** PK-744
**semantic-key:** PK-744

### 3.1 Threads

## 4. Work packages (what the later *plan* must cover)

Order is a suggestion, not a schedule. T1 lands before T2, because a collapse that does not survive `rebuildTabs` is not the flag he asked for. T6 lands with T2, because the collapse toggle rebuilds the card.

### T1 — Persist the flag

- `CellRecord.collapsed`, default false. `newCell` leaves it false.
- `VaultSql` DDL lists `collapsed BOOLEAN NOT NULL DEFAULT FALSE`.
- On open, run an idempotent add for a disk vault whose table predates the column. A failed duplicate add is success.
- `INSERT_CELL`, `UPDATE_CELL`, `SELECT_CELLS`, `insertCell`, `bindUpdate`, and `loadCells` carry the flag.
- A burner still stores null CLOBs. The flag itself is stored.
- Do not add a value to `CellState`.

### T2 — Collapse the card

- One header toggle. Suggested label `+` when collapsed and `-` when open. Tooltip `Expand` or `Collapse`.
- The toggle sets `cell.collapsed`, `persistQuiet`, then `rebuildTabs`, and it uses the T6 hold.
- Open: the split stays. Closed: the split is not added. A brief is added under the header.
- The brief is `assistantText`, plain, from the first character. One line. A second line only when the header row is tall enough for it. No summarizer. Empty output leaves the brief blank. User text is never the source.
- Brief color follows T4, and gray wins when the cell is `EXCLUDED`.
- The collapsed card's max height is the header plus the brief, not 280.
- `IEPO`, burner, and View stay on that same header, visible while collapsed. View stays disabled when there is no output.

### T3 — Tooltips

- `I` Included. `E` Excluded. `P` Input only. `O` Output only.
- The visible label stays the one character.

### T4 — Colors

- Input text `#00FF66`. Field background stays `#1A1A1A`. User border stays `#3CB371`.
- Assistant text `#FFFF55` when the cell has output. The empty-response border may stay the gray chrome it has now.
- View modal text `#FFFF55`.
- `EXCLUDED` keeps both areas gray, including the brief.

### T5 — View bounds

- File beside the user directory, sibling of the vault folder: `~/.aetheris/{user}/view-bounds.txt`. Not a cell column. Not `aetheris.yaml`.
- Keys `x`, `y`, `width`, `height`.
- Write them when the dialog closes, from the dialog's bounds.
- On open, use them when the rectangle intersects a current screen. Otherwise 720 by 480 centered on the Aetheris pane.
- RAM vault mode still uses this file. The bounds are not inside H2.

### T6 — Focus hold

- Before `rebuildTabs` from a header control, remember the card id, the card's Y, and the viewport view Y.
- After the new card is laid out, set the view Y so `cardY - viewY` matches the remembered delta.
- Focus stays on that card. A collapsed card focuses the header, not a hidden text area.
- `scrollRectToVisible` of the whole card is not the hold. New turn and the end of a run may still scroll to the new cell. Clear turns may scroll to the new empty turn.

### T7 — Import

- Popup trigger on the card. One item, `Import...`. No header button.
- The cell under the pointer is the target.
- `OUTPUT_ONLY` refuses, status line, no dialog.
- A press on the assistant area refuses. The file never writes `assistantText`.

### T7 — Import

- Success replaces `userText`, updates the area, `persistQuiet`. State unchanged. No execute. No AMWP.

### T8 — Clear turns

- Footer button `Clear turns`, beside New turn.
- Active canvas only. Delete that canvas's cells. Delete `trace` rows for those cell ids even though nothing inserts traces yet.
- Insert one empty `INCLUDED` turn, the same shape as `newCell`. Save order.

### T8 — Clear turns

- Do not call `ClearCommand`. Do not touch `ChatSession`.
**semantic-key:** PK-745
**semantic-key:** PK-745

- No confirm dialog unless adds one. Status line names the canvas and says the turns are cleared.
- Burner cells are not kept.

### T9 — Smoke, by hand

No new test framework. The later plan's check is a manual pass:

- Collapse a cell with output. The brief is the start of that output. Expand. The full text is still there.
- Collapse a cell with empty output. The brief is blank.
- Restart the Shell on a disk vault. The collapsed cells are still collapsed.
- Run a collapsed `INCLUDED` cell's neighbors and confirm the collapsed cell's output is still in the working set.
- Toggle `E` on a collapsed cell. It ghosts. The brief goes gray. It drops out of the working set because of `E`, not because of collapse.
- Hover `IEPO` and read the tooltips.
- Input reads emerald. Output and View read yellow.
- Move and resize View. Close. Open another cell's View. Same place and size.
- Drag that window off-screen, close, reopen. It comes back centered at 720 by 480.
- Click `I` on a cell that is not at the top. The cell stays at the same place in the pane.
- Right-click the input of a cell and import a short text file. The input is replaced. The output is not.
- Right-click an `OUTPUT_ONLY` cell. Import refuses.
- Import a file larger than 1 MiB. It refuses.
- Clear turns. One empty turn remains. The VT100 history is still there.

## 5. Gate

Do not edit Java from this file.

# Aetheris — Stage 1 tweaks pre-plan — serial 003

**Mode:** PRE-PLAN. Build from that plan.
N1 through N9 and S1 through S12 are APPROVED.

> *The notebook already stands. These are the next usability tweaks on that notebook. One pipe. Four context states. No Stage 2. Prod 1.0 stays frozen.*
**semantic-key:** PK-847

Pre-plan 001 and plans 001 and 002 stay frozen. Do not implement from this file.

# Aetheris — Stage 1 tweaks pre-plan — serial 003

## 1. What this slice is

Usability on the notebook and the shell chrome around it.

**Done means:** after Run, the canvas sits at its end and the new turn's input has focus. The frame status line names the focused connected user and keeps that name. Both file choosers remember one size and one folder. An incrementor on the west of the Aetheris button bar sets the viewable rows of open input and output panes, and that count comes back when the app opens. The inspector draws a short rule between cards. A pull-down of connected users sits in an east capsule with the thinking spinner and selects that VT100 session. The spinner is royal blue `#4169E1` with an emerald glyph while it spins. Every split divider is 11 pixels, from one constant, and a sliver cannot replace a saved open connection panel.

**Not done:** a formal plan, Java, a seed-kit cut, `select model`, `list models`, a fifth cell state, CONTEXT-PIN, Grok, MCP `0x0004`, a change to the working-set walk, a painted inspector, a row ceiling, per-vault chooser memory, crosstalk.

## 2. In / Out

### 2.2 Out

## 3. As-built (what the later plan must not forget)

| Surface | Today | This slice |
|---|---|---|
| Run land | `pendingLandOnInput` scrolls the user area of the new card into view | The active canvas viewport goes to its maximum Y. The new input takes focus. |
| New turn, Clear turns | Whole-card scroll, top of that card | Unchanged. |
| Header hold | `delta = cardY - viewY`, restored after rebuild. Clears `pendingLandOnInput` | Unchanged. Collapse and `IEPO` keep using it. |
| Frame status | One label. Connected, Connecting, Disconnected, and Aetheris messages replace the whole string | The same bar also names the focused connected user. A later message keeps the name. |
| Aetheris footer label | `vault: disk` or `vault: ram`, in the west flow | Stays the vault mode. It does not become the user name. |
| Choosers | A fresh `JFileChooser` in `sendFileRequested` and in `beginImport`. No memory | One size and one folder, restored. `showOpenDialog` has already disposed the dialog when it returns, so the bounds come from the dialog window. |
| Open card | 4 rows. Maximum height 280. `setRows` does not cap a filled area | The row count sets the card height. Text past the rows scrolls inside the pane. |
| Collapsed brief | One line, or two when the header is tall enough | Unchanged. The incrementor does not grow the brief. |
| Inspector | One text area. Header, one line per role, `est. tokens` | A short rule between cards. An included card keeps two role lines together. Input-only and output-only keep one line. Excluded cards stay out, and the rules close up. |
| Footer east | `ThinkSpinner` alone | A border panel. Pull-down on its west. Spinner on its east. That panel is the footer's east. |
| Spinner | Glyph plus ` thinking`, theme label color, 120 ms, hidden until a run | Royal blue `#4169E1` while spinning. Glyph steps `#00FF66`. The word stays royal blue. Hidden when idle. |
| Divider | `applySplitPane` sets size 8. `collapseControlPanel` treats a location above 8 as expanded | Size 11 from one constant, set after the split UI is installed. The literal 8 drops out. |
| Vault switch | Focused AMWP user. Same username does not reload. Listener drop on the connector that lost focus | The pull-down uses that same door. |
**semantic-key:** PK-746
**semantic-key:** PK-746
**semantic-key:** PK-746

## 3. As-built (what the later plan must not forget)

### 3.1 Threads

## 4. Work packages (what the later plan must cover)

Order is a suggestion. N9 is chrome with no vault effect. N4 lands before a check of N1, because the end of the pane includes the row height. N7 and N2 name the same user.

### N1 — End of the pane

- The Run land, `pendingLandOnInput`, scrolls the active canvas viewport to its maximum Y and focuses the new turn's input.
- When the new card fits, that shows the whole card. When N4 has made it taller than the viewport, the bottom stays in view.
- New turn and Clear turns keep the whole-card scroll.
- A header click, collapse, and `IEPO` keep the hold. They do not set the land flag.

### N2 — User on the status line

- The bar at the very bottom of the window carries the focused connected user.
- `aetherisStatus` and the connect messages may replace their own text. The user name remains on that bar.
- When the focused session is not a connected AMWP user, the name is absent.
- The name uses the status line's existing foreground.
- The footer label stays `vault:` plus the mode.

### N3 — Chooser size and folder

- Both doors call one helper. The VT100 door still pastes. The Aetheris door still fills the user text.
- The memory lives in the shell preference `~/.casserole/ssh-client.yaml` through `SshClientProperties`. Not a vault column. Not `application.yaml`.
- Size is written when the dialog closes, including cancel.
- The folder is written when a file is approved, including when the later read refuses for size or for a NUL in the first 8 KiB.
- A missing folder opens at the platform default. A rectangle that misses the current screen opens at the platform default size, and the folder is kept when it still exists.
- An `OUTPUT_ONLY` cell still refuses before the chooser opens.
- The `.aetheris` door does not use this helper.

### N4 — Incrementor

- New type `org.aetheris.swing.Incrementor`. `// ` on that type. `+`, `-`, and an editable value.
- It sits in the west button flow, with Run, New turn, Clear turns, and Preview.
- `+` and `-` change the row count by one, immediately, and cancel a pending typed value.
- A typed value waits 0.8 seconds after the last key, then applies. Empty and non-numeric leave the last good value.
- Minimum is 1. There is no ceiling.
- The saved value is rows, in the same shell preference file as N3. A font-size change recomputes pixels from the rows.
- Both open panes use it. The card's maximum height comes from that row count, the line height, the header, the divider, and the borders. The 280 cap is replaced by that height.
- The collapsed brief, the inspector, and the View modal do not take the number.
- Switching vault users does not change it. Roux sync does not carry it.

### N5 — Inspector rule

- `refreshInspector` inserts one short rule between cards.
- The rule is characters in the same text area, short enough that soft wrap leaves it on one row.
- The header and `est. tokens` stay outside the rules.
- A later pin that drops a card drops that card's lines, and the rules sit between the cards that remain.

### N6 — Seed-kit, held

- No file in prod 1.0 changes in this slice.
- This package is a record. It is not a Java package.

### N7 — Connected-user pull-down

- The footer east is one border panel. The pull-down is its west. The spinner is its east.
- The list is connected AMWP sessions, by the sanitized username the vault opens. Connecting and disconnected panes are omitted.
- Two panes with the same username are one entry. Choosing the focused user's name does not reload the vault. Otherwise the first connected pane with that name is selected.
- Choosing a name selects that session. `selectSession` remains the door. Run stays on the focused connector. The mux stays up.
- An illegal name still becomes `anon`. Two illegal names share that vault.
- A switch during a run drops the in-flight frame and stops the spinner. The session stays connected.
- When focus is not a connected AMWP session, the pull-down has no selection and the cards stay on the last vault.

### N8 — Thinking spinner

- While it spins, the base is `#4169E1`. The moving glyph steps `#00FF66` on each 120 ms frame. The word `thinking` stays `#4169E1`.
- The cell border stays `#3CB371`. The input emerald stays `#00FF66`.
- The label is hidden while idle. The spin sets its colors after `applyLabel`.

### N9 — Divider width

- One static constant, value 11, on the theme. `applySplitPane` is the only `setDividerSize`, and it sets the constant after the split UI is installed.
- The shell bar, the session bar, the notebook bar, and every open cell bar take it. A collapsed card has no inner bar.

### N9 — Divider width

- The open height is the connection panel's preferred height plus `getDividerSize()`.
- Collapse remembers the current position only when it is at least that tall. A reading of 0, a sliver, or a half-open drag leaves the saved height alone.
- Expand keeps its floor. A position shorter than the open height opens at the open height.
- The literal 8 in `collapseControlPanel` drops out.

## 5. Gate

Stage 2 stays closed. Prod 1.0 stays frozen.

This file is the pre-plan.

The count is nine packages. N6 is a held cut, not code. The other eight are the build the later plan has to cover. §5.1 now carries N1 through N9. Stage 2 stays closed.

# Aetheris — Stage 1 tweaks pre-plan — serial 004

**Mode:** PRE-PLAN.
N10 through N14 are APPROVED.

> *The notebook already stands. Five more notes on that notebook. One pipe. Four context states. No Stage 2. Prod 1.0 stays frozen.*
**semantic-key:** PK-848

Plans 001, 002, and 003 stay frozen. Pre-plans 001 and 003 stay frozen. Do not implement from this file.

# Aetheris — Stage 1 tweaks pre-plan — serial 004

## 1. What this slice is

Paint and layout on the notebook plan 003 already built.

**Done means:** the inspector lists the newest card first. The pull-down stays on the east of the footer capsule and the spinner sits on its west. Aetheris buttons are 40 percent shorter, with a font that fits, and every button has a raised bevel. Scrollbar arrows show at each end, light gray with a black arrow. The inspector wraps to the pane width while soft wrap is on, and soft wrap starts on.

**Not done:** a formal plan, Java, a seed-kit cut, `select model`, `list models`, a fifth cell state, a change to `assemble`, character wrap, moving the cell panes or the View modal onto the handler, a saved off-state for the wrap checkbox, the terminal's own scrollbar.

## 2. In / Out

### 2.1 In

| ID | Work |
|---|---|
| N10 | Inspector cards newest first. Header stays first. Token line stays last. Hyphen rule stays between cards. Send order stays oldest first. |
| N11 | Spinner on the west of the east capsule. Pull-down on the east. Capsule stays on the footer east. |
| N12 | Aetheris buttons lose 40 percent of their height. Their font shrinks to fit. Every button `applyButton` paints gets a raised bevel. |
| N13 | Arrow button at each end of every Swing scrollbar. Light gray face. Black arrow. |
| N14 | Reusable soft-wrap area. Tracks the inspector viewport. Default on. The existing checkbox is the switch. |

### 2.2 Out

## 3. As-built (what the later plan must not forget)

- `refreshInspector` writes oldest first. Prior cards, then the target card, under `working set → id`, with `------------` between cards and `est. tokens` last. An included card keeps the user line before the assistant line. An excluded card stays out.
- `buildFooter` puts the combo on the west of the east capsule and `ThinkSpinner` on the east. Showing the spinner widens that east child and the pull-down shifts left.
- `applyButton` paints a flat line border and an empty margin. `applyFontTree` sets the Aetheris button font from the shell point size.
- The theme sets `ScrollBar.background` and leaves the arrow buttons to the platform look.
- `inspectorSoftWrap` starts true. The checkbox starts checked. `setLineWrap(true)` and `setWrapStyleWord(true)` already run. The area still sizes to the longest line, so the pane scrolls sideways when the divider moves.
- The inspector text sits in an inner scroll pane. `wrap` builds a second scroll pane around the header and that inner pane.
- This slice rides on that tree.

## 4. Work packages

Suggested order for a later plan: N11, N12, N13, N10, N14. N14 is last so the handler wraps the reversed text and leaves N13's horizontal arrows for the wrap-off state.

### N10

Reverse the card blocks in `refreshInspector`. The newest contributing card is the first block under the header. Older cards follow downward. The header stays first. `est. tokens` stays last. The hyphen rule stays between cards. Inside an included card the user line still precedes the assistant line. The numbers follow the list, so the top line is 1. `assemble` stays oldest first. The canvas land stays as built.

### N12

Aetheris button height becomes 60 percent of today's height. The font on those buttons shrinks so the label fits. That set is Run, New turn, Clear turns, Preview, the incrementor, the card buttons, and the View modal Close. Shell buttons keep their height. `applyButton` adds a raised bevel on every button it paints, shell and Aetheris. The fill colors stay.

### N13

Every Swing scrollbar shows an arrow button at each end, vertical and horizontal. The face is light gray. The arrow is black. The seats are the canvas, each open cell pane, the inspector, the View modal, and the shell text scrolls. The terminal bar stays JediTerm's.

### N14

2. While wrap is on, the area tracks the viewport width, and a resize listener on that viewport reflows the text. The checkbox turns wrap off and restores the long-line width and the horizontal bar. A scrollbar policy alone is not the fix.
3. Soft wrap stays on at launch. The existing checkbox is the switch. It is not saved.
4. Wrap is on words. The twelve-hyphen rule and the UUID header stay one row at a normal width. The previews fold. Character wrap stays off.
5. The handler attaches to the inner inspector pane. The open cell panes and the View modal stay later seats.
6. While wrap is on, the horizontal bar is absent. N13's arrows return on that bar when wrap is off. The vertical bar stays.
7. `assemble` still sends oldest first. N10 still paints newest first.

## 5. Gate

Do not edit it. Do not edit plans 001, 002, or 003. Do not edit 006. Stage 2 stays closed. Prod 1.0 stays frozen. N6 stays held.

This file is the draft. Note-taking is closed. A formal plan waits. Java waits. Stage 2 stays closed.

Five notes.

The machines stay as section 3. The width track is the fix. A scrollbar policy alone can miss it.

The wire stays quiet. `assemble` stays oldest first.

Face. The pull-down stays on the outer east corner. The buttons get shorter and beveled. The lines fold inside the inspector.

The checkbox still says `soft wrap`.

# Aetheris — Stage 1 pre-plan — serial 001

Do not clobber. Implement from the Stage 1 **plan**, not this file.
**Cadence:** Q18 — Stage 1 pre-plan → Stage 1 plan ( copy to `docs/plans/`) → build → scan → Stage 2 working remainder.

# Aetheris — Stage 1 pre-plan — serial 001

Serial this file if marks gaps. Do not clobber. Do not implement from this file.

## 1. What Stage 1 is

Sovereign notebook MVP on the Stage 0 van: product tabs (SSH | Aetheris), AMWP two-scope mux, client H2 vault, four-state cells, RAM Bantha Roux, Ollama SPI shape + `swap`, Tab A operator defects (wrap, idle, sticky `cmd>`), theme/icon, script docs, on **new** Java.

**Done means:** `bin/casserole` + `bin/aetheris-shell` as today → Connect to localhost → SSH tab still splits/JediTerm/`chat>` → Aetheris tab shows canvas + inspector → execute a cell against Ollama over AMWP → vault survives a Shell restart in **disk** mode; **ram** mode is default and is not crash-proof → OpenSSH `ssh -p 2222` still raw PTY → long input wraps at pane width → idle does not drop in ~10 min → Esc stays in `cmd>` until Esc again.

**Not done:** CONTEXT-PIN, MOOD-LAYER, groups, pipes, Grok adapter, MCP `0x0004`, export, LCARS, Graph button, Boot 4.

## 2. In / Out

### 2.1 In

| ID | Work | Spec lock |
|---|---|---|
| S1-01 | Product `JTabbedPane`: **SSH** (today’s split tree) \ | **Aetheris** (canvas + inspector + footer Run / New turn / Preview). No Graph button | |
| S1-02 | Aetheris tab binds **focused AMWP-capable** `TerminalSession`. Raw/OpenSSH → tab disabled for that session | |
| S1-03 | Connection strip **stays collapsed** on frame resize while connected | |
| S1-04 | AMWP: magic `0xAE`, LE, 1 MiB, channels T/A/stream, Init or raw PTY. Demux **bytes** in front of UTF-8 Reader | |
| S1-05 | Execute payload: length-prefixed envelope, body **TOON** in `org.aetheris.wire`. One in-flight per `cell_id` | |
| S1-06 | Client vault: Hikari + H2 **compile** on Shell. PURE-JDBC + SQL constants. No JPA, no Flyway, no Spring `DataSource` on Casserole | |
| S1-07 | `AetherisWorker` — JDBC/AMWP off EDT | |
| S1-08 | Schema v1: `workspace`, `canvas`, `cell`, `trace`. Path `~/.aetheris/{user}/vault`. Default **ram**. Disk opt-in | |
| S1-09 | Extend `AetherisYaml`: `vault.mode`, `vault.path`, `network.air-gap`, `amwp.enabled`. Spaces only. No SnakeYAML unless blocked | |
| S1-10 | Four-state modal cells + ghost ≡ EXCLUDED visual + burner pin. Completing Turn N materializes N+1 | constitution; no `@scope` |
| S1-11 | Client working-set assembly + inspector preview + token estimate. Full push into Bantha on execute | §4.4–4.5 |
| S1-12 | `openRam(sanitize(user) + ".aetheris")`. No FileLock. No `{spill.dir}/{user}/`. `NO-DUAL-WRITE` | |
| S1-13 | `@ComponentScan` includes `org.aetheris` | |
| S1-14 | Cell execute on Lamina **LLM_EXEC**. Cancel = best-effort (same as Tab A Ctrl+C). No `Flux` | |
| S1-15 | `LlmProvider` SPI. Only Ollama wired. Per-cell model dropdown. Tab A `swap <from> <to>` | |
| S1-16 | Wrap / NAWS: cols on connect **and** resize; ChatShell updates `terminalColumns`; input-line wrap | |
| S1-17 | Client idle default **never disconnect**. Server must not 600s-kick operator sessions | |
| S1-18 | Sticky `cmd>`: Esc toggles and **stays**. No `returnToChatOrIdle` after commands (except `exit` / `chat start`) | |
| S1-19 | Theme keys: cursor / inactive cursor / menu fg / `chat>` green / `cmd>` blue | |
| S1-20 | Window icon: Eye of Horus × swoosh, green/blue/gold on black | |
| S1-21 | Scripts stay offline-capable; `bin/docs/` tables | |
| S1-22 | `// <KEY>` on **new** Stage 1 types/methods. Existing keys only | |
**semantic-key:** PK-747
**semantic-key:** PK-747

### 2.2 Out (do not put in the Stage 1 plan)

## 3. As-built (what the plan must not forget)

| Surface | Today | Stage 1 target |
|---|---|---|
| Frame | Connection strip + split JediTerm. No product tabs | SSH \ | Aetheris `JTabbedPane` |
| `SshTtyConnector.read` | UTF-8 **Reader** | Byte demux **in front**; JediTerm still UTF-8 Scope T |
| ChatShell stdin | Raw | Mux **around** pipes; no Init → raw as today |
| `AetherisYaml` | `ui.title` only | vault/amwp/air-gap keys |
| H2 / Hikari | H2 `runtime` unused JPA; no Hikari | Hikari+H2 **compile**, Shell only |
| Roux | `open(user)` disk exclusive | plus `openRam("user.aetheris")` |
| `@ComponentScan` | `org.casserole`, `org.roux` | + `org.aetheris` |
| `LlmService` | Ollama one-shot `complete` | `LlmProvider` SPI; ChatShell still calls `LlmService` |
| `terminalColumns` | env at start, default 80, no resize | NAWS/window-change |
| Idle | MINA ~600s | default 0 / never |
| Esc | toggles then command bounces to chat | sticky COMMAND |
| Menu / `chat>` color | dim panel fg; uncolored `chat>` | named theme keys |
| Icon | casserole metaphor | Aetheris motif |
| Shell launch | `java -cp` + LocalM2Classpath | unchanged; never `exec:java` |
| `bin/docs/` | missing | |
| | none | new types only |

## 3. As-built (what the plan must not forget)

### 3.1 Threads

| Who | Mechanism | Stage 1 |
|---|---|---|
| ChatShell read, AMWP mux | Lamina `SSH_IO` | mux sits **with** ChatShell, not a new Thread |
| LLM complete (chat + cells) | Lamina `LLM_EXEC` | cells share this lane |
| JDBC / Shell AMWP client | **`AetherisWorker`** | not Lamina, not EDT |
| Swing | EDT | hop with `invokeLater` |
| Esc / spinner | `java.util.Timer` | leave |
| MINA NIO | library | do not replace |
| `TOOL_EXEC` | — | **do not allocate** |

### 3.2 Leftovers (leave in repo)

## 4. Work packages (what the Stage 1 *plan* must cover)

### WP-A — Tab A defects (S1-16…S1-18) first among equals

- OpenSSH without size codes: keep 80.
- Idle: `ui.connection.idle-timeout-ms` default 0. Server operator sessions: not 600s.
- Sticky `cmd>`: delete post-command `returnToChatOrIdle()` except `exit` / `chat start`. Welcome text: “Escape toggles command mode.”

### WP-B — Chrome (S1-01…S1-03, S1-19, S1-20)

- Footer: Run | New turn | Preview. No fourth Graph button.
- Collapse strip on connect; **re-assert divider 0** on resize while connected.
- Theme keys on `SshClientProperties` / `SshClientTheme`. Server `Ansi` for prompts.
- Icon at plan/build; do not block notebook on pixels.

### WP-C — AMWP (S1-04, S1-05)

- Byte mux in front of the Reader. Opaque T. Init or raw.
- TOON body in `org.aetheris.wire`. Envelope length-prefixed. Max 1 MiB. One in-flight per `cell_id`.
- Cancel best-effort via Lamina handle.

### WP-D — Vault (S1-06…S1-09)

- Hikari + H2 compile. PURE-JDBC. In-tree DDL. `AetherisWorker`.
- Default ram `jdbc:h2:mem:aetheris`. Disk `~/.aetheris/{user}/vault`.
- Extend hand yaml parser. Spaces. Ignore unknown keys.

### WP-E — Cells + assembly (S1-10, S1-11)

- Four-state modal + ghost + burner pin. Inspector shows working set + token estimate.
- Assembly walk as locked. No `{{cell}}` resolve.

### WP-F — Bantha + execute (S1-12…S1-14)

- `RouxChatMemory.openRam` / `RouxEngine.openRam`. Region `sanitize(user)+".aetheris"`.
- Full push per execute. Cells never enter vt100 pack.
- Scan `org.aetheris`. LLM_EXEC.

### WP-G — SPI + swap (S1-15)

- `air-gap: true` parsed; no-op for cloud (none in S1).

### WP-H — Scripts + docs (S1-21)

- Keep Shell `java -cp`. Never `exec:java`.
- `bin/docs/` tables. Offline default; WAN is LPUs only.

### WP-I — AA-SDD tags (S1-22)

- `// ` (etc.) on **new** types/methods this stage writes.
- No Stage 0 backfill. No REQ-THREAD.

## 5. Risks / stop-and-ask

| Risk | Rule |
|---|---|
| `mvn package` ASM major 70 | Stop. Do not Boot 4 (-4) |
| SnakeYAML “needed” for vault yaml | Stop. Extend the hand parser first |
| AMWP vs JediTerm: frames leak into Scope T | Defect. Opaque T |
| JDBC on EDT | Defect. `AetherisWorker` |
| Colon region id | Illegal. Dot only |
| CONTEXT-PIN sneaking into S1 | Out. Face already staged it S2 |

## 6. Smoke (plan must include)

1. OpenSSH `ssh -p 2222 user@host` — raw `chat>` as today.
2. Aetheris Shell → Connect → SSH tab splits / paste / focus as today.
3. Aetheris tab enabled only on AMWP session; disabled on raw.
4. New turn → four-state → Preview inspector → Run → Ollama output in cell.
5. Disk vault: restart Shell, cells still there. Ram: gone (expected).
6. Tab A `message find` does **not** return canvas turns.
7. Type a line longer than the pane — wraps, does not concatenate-redraw.
8. Leave connected >10 min — still up.

## 7. What the Stage 1 plan must not do

- Re-implement Stage 0 (Lamina, Log4j2, JDK 26 fetch, product rename).
- Allocate `TOOL_EXEC`.
- Open H2 on Casserole.
- Put cells in vt100 Roux.
- Write a Graph button.
- Implement CONTEXT-PIN / MOOD-LAYER.
- Copy `docs/plans/` until **stamps the plan**. This pre-plan is not that copy.

*Stamped. Do not implement from this pre-plan.*

*Chrome is polished. Contract is signed. Pre-plan is the blueprint for the plan.*

# Aetheris Stage 2 pre-plan 001

- The proposed order is the order.

- **State:** LOCKED. The five rows are the package order. This page does not stamp a plan.

- **Mode:** pre-plan. Locked. Not a stamped plan. Not product Java. Not a build.

- That serial is locked. Where this page is thinner, the locked serial wins. The order on this page is locked.

- **What it is:** the locked package order a later plan will build, cut from the locked serial.

- **What it is not:** a stamped plan. A license to write Java. An copy in `docs/plans/`. A rewrite of the stage map or the constitution.

It is not a license to write Java.

## Order

He is comfortable with the proposed order. The team order stands. All five rows are locked as written.
**semantic-key:** PK-849

## Order

`num-ctx` is not in this table. The named default is 4096. The yaml integer is set in the same commit as this page. `0` still means the server omits `num_ctx`. The key stays in Casserole `application.yaml`. It is not copied into `aetheris.yaml`.

## Package 1. LEX-FILTER

**Status:** locked.

Carried from the locked serial, `CONTEXT-PIN` and `LEX-FILTER`.

- The pin sits on the inspector rail. Every keystroke retokenizes while it is on.
- Stopwords are `a`, `the`, `and`, `of`, `to`.
- 1. On OK, non-matches become `EXCLUDED`. No fifth state.
- 2. A match that was `EXCLUDED` is promoted to `INCLUDED`. The other three states stay.
- Four states stay. Ghost is the `EXCLUDED` paint.
- No new wire. No new AMWP channel.

**Search rides here.** Locked.

## Package 1. LEX-FILTER

- Hits are limited to the open canvas.
- Typed `%` and `_` are literals. The engine wraps the needle for a contains match.
- An empty needle returns no rows.
- A hit selects. It does not change state.

**Version 2 lands in that same touch.** Locked.

- One open path. The method that already probes collapsed.
- Read `schema_version`, apply the ordered step, then SELECT.
- A vault with the Stage 1 tables and no version row is version 1.
- Version 2 adds `group_id` and `execution_status` together, then writes the row.
- A version row that says a step while a column is missing re-runs that step.

- `PROBE_COLLAPSED` and `ALTER_COLLAPSED` remain version 1 history only.

- Comes off `VaultSql` and `VaultStore` when those files are edited.

**The tool column is not this step.** Locked.

`tool_calling_enabled` waits with tool integration. It is not a version 2 column. One step per plan. No new `INFORMATION_SCHEMA` probe per column.

## Package 2. Execute seams and MOOD-LAYER

**Status:** locked. It lands here, with the Ollama-only router.

Carried from the locked serial, `MOOD-LAYER`, and `Final gap solutions` 1, 2, 8, 10, and 11.

- One walker builds the send and the inspector. Send order is oldest first. The inspector paints newest first. `k` is the 1-based index among cells the walker kept. The inspector's paint numbers are not `k`.
- The host system line comes first. The mood system line follows when the operator approves. Two messages. They are not fused.
- Skip `EXCLUDED`. Skip an empty burner. Honor the four states.

## Package 2. Execute seams and MOOD-LAYER

- `SessionFacts` on `TYPE_INIT`: air-gap true, false, or unset, and the `num-ctx` integer. An empty Init body is not 0.

- The shell lookup stays `--aetheris-yaml`, `./aetheris.yaml`, `~/.aetheris.yaml`. A mismatch blocks the shell's cloud send. The server still enforces.

- One facade method holds a per-region mutex around clear and every add, and releases it before `complete`. Not Roux's lock. Not `tryAcquireLock`.

- A semaphore of 3 lives in `AmwpFacade` for an Aetheris execute. `LLM_EXEC` stays 4. `ChatShell` does not take the semaphore.
**semantic-key:** PK-748
**semantic-key:** PK-748

- The yaml key `mood-shape` holds the shaper id. The default is the Ollama model in use. A missing id, an air-gap skip, or an unreachable shaper skips mood. The filter commit stands. The modal says "keyword only."
- The mini-chat is a modal `JDialog` on `AetherisWorker`. It is not a canvas cell.
- The in-flight placeholder goes in before `submit`. Today's check-then-put is not atomic.

## Package 3. Grok on that router

- An empty stored target goes out as `ollama` and is not written back.
- Grok `complete` does not set `num_ctx`. Only Ollama does.
- Key lookup is the environment, then `~/.aetheris/`, then `~/.casserole/`. A missing key fails the cell. The key is not logged. It is not stored in cell text unless the cell is a burner.
- An unknown provider fails the cell and leaves the stored target.
- The yaml key `burner-key` missing means deny. The constitution phrase stays allow-cloud-burner. No parent path was added for `mood-shape` or `burner-key`.
- Tab A `swap` stays. ChatGPT and Claude stay.

## Package 4. Groups

- The algorithm token stays `CONCAT`. The banner label is `JOIN` plus that member's execution target. An empty stored target shows as `ollama`. The label is on every member.

## Package 4. Groups

- `cell-busy` paints the status line only. It does not write `FAILED` and it does not `clearRun`.
- `failed`, `air-gap`, `engine-down`, and `too-large` write `FAILED` on that cell only.
- `COMPLETED` writes the assistant CLOB. `CANCELLED` writes the status and leaves the CLOB.
- The `bindUpdate` argument is the canvas card. No second DTO.

## Package 5. Pipes

- A non-empty CLOB is a legal source, including when the latest status is `CANCELLED`.
- An empty CLOB plus `CANCELLED` is not a source.
- `FAILED` stops the pipe even if an older CLOB remains.
- `CANCELLED` does not by itself fail a sibling or stop the chain.
- An ephemeral cell cannot push.
- Resolve `{{cell[k].output}}` on the client before the frame. Raw braces are not sent.
- The chain gesture is Ctrl+Shift+F5.
- Over 1 MiB, prefix included, fails the cell with reason `too-large`.
- Scope A stays one chunk. Do not reassemble `frag_count`.

## Package 6. Export and import

- The file is `.aetheris` TOON, version 1.
- On import, each cell id becomes a new UUID minted for that import. Parent, branch, group members, and `{{cell[k].output}}` are rewritten.
- The import is a copy. The same file can be imported again.
- Burner bodies are stripped by default.
- Its own door.
- A one-chunk frame keeps `frag_index` 0 and `frag_count` 1. That wire index is not part of the 1-based serial rule.
- Counted serials stay 1-based: `k`, the vault schema, the export version, depth, the pool, and N.

## Package 7. Crosstalk

- When nothing is highlighted, N is 1. That line is the last line the terminal returned. A highlight still wins.
- A new cell starts `EXCLUDED`. The operator must ask.
- A cell fence onto the PTY on Scope T needs confirm. Never an ephemeral cell.

## Package 8. Tool hooks only

**Status:** locked. What a tool does is not in this plan.

- Channel `0x0004`. `TOOL_EXEC` pool size 1. A second tool task waits. It does not fail. `LLM_EXEC` stays 4.
- `tool_calling_enabled` absent means false. This plan does not turn the flag on.
- Depth is 1. The allowlist is empty. No tool is invocable.
- Cells stay prompt-only. Tools are not painted into `ChatShell`.
- Day-one group replies stay on `0x0003`. `0x0100` is not allocated.
- Stays on the lane.

## Out of this pre-plan

- The SQL design serial. The idea stays. This page does not invent the grammar. The serial does not exist. It is not in the first plan.
- Incremental Roux. Preferred later. Full push stays. `NO-DUAL-WRITE` stays. The first plan does not include it.
- Lucene. Contract agents. Flux. Dual-write.
- `num_predict`. Shrinking the `LLM_EXEC` pool. Temperature, max-tokens, and top-p.
- Tool behavior. The hooks stay. What the tools will do is not defined.
- A parent path for `mood-shape` or `burner-key`. None was added.
- A third vault default. The floor says ram is the default and disk is the perpetuity opt-in. The `AetherisYaml` constructor default is disk (`MODE_DISK`). This page follows the floor. It does not reopen that conflict.

## Seams the packages share

Do not rebuild these. The locked section `Final gap solutions` is the shape.

- Air-gap has three values. Explicit true refuses cloud. Explicit false allows. Missing loads as unset and refuses. Unset is not the constitution's false. No client-asserted air-gap bit. The stored target is not rewritten.
- One `TYPE_ERROR`. The body is TOON: reason and message. A body that does not start with `reason:` still displays as today's plain failed banner.

## Seams the packages share

- `bindUpdate` writes the same mutable columns the insert already writes, plus `group_id` and `execution_status` once version 2 exists.
- The walker and the reply path both call `fits`. Over the cap, the cell is `FAILED` with reason `too-large`. Encode is not asked to throw.
- `complete` uses the local `Prompt`. It does not read the region back. Lock order is the facade lock first, then Roux's per-call lock inside clear and add. Never the reverse.

## num-ctx

## Floor this page does not rebuild

Stage 1 is built. This filing changes the yaml integer. It does not change a Java type.

- Class `org.casserole.app.ui.ssh.SshClientApp`. Launch is `java -cp` plus `LocalM2Classpath`.
- One tab and one vault follow the focused VT100 user.
- The inspector is newest first. The send is oldest first.
- The client vault is `~/.aetheris/{user}/vault`. Casserole does not open H2.
- Bantha `openRam(sanitize(user)+".aetheris")` stays a full push.
- AMWP `0x0001` is Scope T. `0x0002` is Scope A. `0x0003` is the stream. `0x0004` is this slice and is not in `AmwpConstants` yet.
- One in-flight execute per `cell_id`. Do not reopen it.
- New Java uses existing keys only. Is the router. Is the codec. Is the vault migration. Stays on the lane.

## What the stamp did

The order is locked. This file stays beside the serial. Do not implement from this page.

# Aetheris Stage 1 tweaks 002 — code spec

This file is the contract of the 002 build. A hand edit of that source is observed and flagged. It is not a silent new spec.

## Run lands on the next input

A finished Run on the last cell creates the next turn, puts the caret in that turn's input, and scrolls that user area into view. The answer stays above it. The header focus-hold is unchanged.

> When I click on Run in the Aetheris pane, please shift focus straight to the next new turn cell.

## Inspector shows the chain

The inspector lists the working set. An excluded cell is absent, including when it is the last cell. An `IEPO` click refreshes the list in that click. The card stays on the canvas. A collapsed included cell stays on the chain.

> When a cell is excluded from the chat history, please hide it in the inspector view. The inspector view ONLY shows what is active on the memory list chain.

## Focused console owns the notebook

One Aetheris tab shows the vault of the user on the focused VT100 session. A different user writes the open vault, then loads that user's vault, and the listener moves to that connector. The same user does not reload. A session that is not connected AMWP does not close the open vault.

> So it's just a matter of establishing which aetheris pane is active according to the active user in the vt100 terminal?

## Smoke left on the table

U4 was not clicked. The seven checks in plan 002 stay unrun. They are closed with this file. They are not an open slice.

> go ahead and build what was approved just now

## Out of this build

`select model` and `list models` stay unwired. There is no fifth `CellState`. Grok stays unwired. Stage 2 stays closed. The class stays `org.casserole.app.ui.ssh.SshClientApp`.
**semantic-key:** PK-749
**semantic-key:** PK-749

# Aetheris Stage 1 tweaks build — code spec

This file is the contract the filed source matches. A hand edit of that source is observed and flagged. It is not a silent new spec.

The prose that fed the build is the locked tweaks serial and the stamped plan. Those files stay the heads. This file does not paste them.

## Collapse flag

`collapsed` is a boolean on the cell, default false, stored on insert, update, and load. An older disk vault gains the column when it is missing.

## One card

One card carries the header while it is open and while it is collapsed. The body hides when `collapsed` is true. The open card keeps its height cap. The collapsed card is the header plus the brief.

> One card. The header buttons stay. No second copy of the state buttons.

## Brief

The brief is the assistant text, from the first character, one line, or two when the header row is at least two lines tall. Empty output leaves the brief empty. User text is not the brief.

> The collapsed brief is the assistant text, from the first character. The user text is not the brief.

## Letters

The state controls stay `I`, `E`, `P`, and `O`, with the tooltips Included, Excluded, Input only, and Output only. The collapse control reads `+` when collapsed and `-` when open.

> The buttons stay `I`, `E`, `P`, `O`. Each one gains a tooltip.

## Colors

User text is `#00FF66` on `#1A1A1A`. The user border stays `#3CB371`. Assistant text and the View modal are `#FFFF55` when there is output. An excluded cell is gray in the user area, the assistant area, and the brief.

> Input foreground `#00FF66`. Output foreground `#FFFF55`. An `EXCLUDED` cell stays gray in both areas.

## View bounds

One rectangle is stored at `~/.aetheris/{user}/view-bounds.txt` as `x`, `y`, `width`, and `height`. It is not a cell column and not an `aetheris.yaml` key. A saved rectangle that misses every screen opens at 720 by 480, centered. Close writes the dialog bounds.

> One client preference, global, not per turn, not on the cell record.

## Focus hold

A header control that rebuilds the pane restores `cardY - viewY` and keeps focus on that card. A collapsed card focuses the header control. A new turn, the end of a run, and Clear turns may still scroll to the cell they create.

> Before a header control rebuilds the pane, remember the viewport view Y and the card Y.

## Import

Right-click on a cell offers `Import...`. The file replaces that cell's user text. The assistant area refuses. `OUTPUT_ONLY` refuses. Over 1 MiB refuses before the read. A NUL in the first 8 KiB refuses. The state does not change. The import does not run the cell.

> Right-click on a cell opens a context menu. The item imports one UTF-8 text file and replaces that cell's user text.

## Clear turns

The footer button clears the active canvas, including burner cells and their trace rows, and leaves one empty included turn. VT100 `clear` is not called.

> A footer button clears the Aetheris canvas vault back to one empty `INCLUDED` turn and writes the vault. VT100 `clear` is not called.

## Working set

`appendCell` does not read `collapsed`. An excluded cell is skipped. Input-only sends user text. Output-only sends assistant text. Included sends both when present.

> A collapsed `INCLUDED` cell still enters the working set. `E` stays the way out of context.

## Out of this build

`select model` and `list models` stay unwired. `swap` and `ollama list` stay. There is no fifth `CellState`. Grok stays unwired. Stage 2 stays closed. The class stays `org.casserole.app.ui.ssh.SshClientApp`. Project types take no Lombok and no lambdas.
**semantic-key:** PK-750
**semantic-key:** PK-750

## Out of this build

> `select model` / `list models`. `swap` and `ollama list` stay as they are.

Do not edit this file.

## SPEC FILE for Aetheris related changes prior to Stage 2. `[NEW on 2026-10-18 11:20 AM]`

### This spec file is ONLY for applying minor changes to Aetheris relative to usability aspects wants to add and smooth out before entering into preparing for a stage 2 build

- I would like the ability for cells in the Aetheris pane to be collapsible.
 - First let's decide on the design approach before before we embark on building.
 - Instead of nesting divider panels which is very clunky, I am thinking using some type of custom panel that when a cell is accessible, it's encapsulating container has focus, when collapsed, then a bar panel is enabled as focus the main cell goes out of focus, still there but hidden. We can then add whatever custom buttons in the future for otehr options on the collapsed bar if we want.
 - Thus, we can have a special state model that determines which cell is collapsed or not. I am feeling this is a cleaner approach and easier to manage in the code.
 - What does the TEAM think? Please analyze my ask and if you have a cleaner approach please advise. Please add and provide feedback in the section.
- For the context control buttons on each cell {I, E, P, O} please add the full text for each option. I haven't used this in over a month being busy with papers that I almost forgot what the meanings were LOL.
- I need a new button on the bottom button panel on Aetheris which clears ALL turns, like the clear function in the VT100 context. Which resets the chat memory to an initial state, clearing all turns.
- Please link in to this spec necessary dependencies via depends from stage-1 specs. Any changes in this spec MUST BE COMPATIBLE with existing functionality and be forward compatible with Stages 2 & 3, so I ask the entire TEAM to look for any potential inconsistencies that may arise as I make tweaks here, that can impact our future code staging so we can account for it properly.
- On the VT100 pane, I would like to be able to select a different model other than issuing a direct ollama command from the console. So please add a new command to the SSH stack and console for `select model` which then is supplied by a model name. I need to be able to list available models on Ollama as well, please add a `lost models` command as well.
- The font text is like a light grey in Aetheris pane cells, I would like the default text color for cells to be Bright emerald green for input, and the same yellow we use for the console output for the output text cells.
- When I click on the `View` button for a cell, if I resize the view window, I want the window size to persist, so the next view opens to that size AND position. Makes it easier for rapid viewing with comfort.
- When I click on a state control button for a cell, it pops the focus back to the top of the parent sliding pane. The focus MUST remain on the cell the button was clicked on. Please provide focus management semantics to Cell behavior relative to the parent pane to lock the cell in the relative view position in the encapsulating scrollpane after a button click.
- I need the ability to import the contents of a file into a cell in the Aetheris pane. The current input cell only.

Please evaluate my requests and provide feedback accordingly so we can hash out the details.

# Aetheris Stage 1 tweaks — Draft serial 002

**Mode:** design. **Not a build.** Stage 2 stays closed.
**Code the tweaks have to stay compatible with:** `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.chat.cmd.CommandProcessorFactory`, `org.casserole.chat.cmd.ClearCommand`, `org.casserole.chat.cmd.SwapCommand`, `org.casserole.chat.cmd.OllamaExecCommand`, `org.casserole.ssh.shell.Ansi`
**semantic-key:** PK-751
**semantic-key:** PK-751
**semantic-key:** PK-751
**semantic-key:** PK-751
**semantic-key:** PK-751
**semantic-key:** PK-751
**semantic-key:** PK-751

# Aetheris Stage 1 tweaks — Draft serial 002

This serial is minor usability on the Stage 1 notebook that is already built. It does not open Stage 2. It does not add a fifth cell state. It does not wire Grok. No Java moves until the open points are settled.

## 0. Asks carried from 001 2026-10-18 11:20

1. Cells in the Aetheris pane can collapse. Design first. A small state says which cells are collapsed. Future buttons may sit on the bar.
2. Context buttons `{I, E, P, O}` show the full words. The letters alone are easy to forget.
3. A footer button clears all turns, the way VT100 `clear` resets chat memory to the initial state.
4. Every change stays compatible with what already runs, and stays forward compatible with Stages 2 and 3.
5. VT100 gains `select model` plus a model name, and a command to list Ollama models. The 001 line `lost models` is read here as `list models`.
6. Default cell text: bright emerald green for input, and the console-output yellow for output.
7. The View window remembers size and position for the next open.
8. A state-button click keeps the scroll position on that cell. Focus does not jump to the top of the parent pane.
9. Import a file into the current input cell only.

- Says; for when a button is included or excluded when collapsed, replicate the state control buttons on the collapsed panel view. This way the view is collapsed but the user can still toggle state relative to turns, Render just a one-line brief of the contents starting from the first character, just enough to view in a one-liner to get the gist. Or two lines if button height allows.

The cleaner panel is the card that already exists. `CellCard` keeps one identity. The header is the bar. The split that holds the input and the output is hidden, not removed, and not replaced by a second focus owner. A boolean `collapsed` on `CellRecord`, default false, is the state model. It persists, so `rebuildTabs()` does not forget it. Future bar buttons are more header controls on that same card. Two panels swapping focus would add a second path to the scroll jump this same spec is trying to kill.
**semantic-key:** PK-752
**semantic-key:** PK-752

- Says; I like this idea. This way you render the buttons and they stay regardless, no second copies. Then; when collapse, bring the brief view into focus as aforementioned.

The header is already crowded. Full words still win, because the point of the ask is to read them a month later. Labels: `Included`, `Excluded`, `Input only`, `Output only`. The first letter stays the old mnemonic. They wrap on the flow layout. That is acceptable. Tooltips alone do not meet the ask.
- Says, Leave the actual 1 character mneumonics, just add a tooltip for each one so it hints the user as to what it is for.

- Says; ok, table this ask for now. I likely don't need it. I forgot about the swap command. LOL.

- Says; correct, the VT100 chat is untouched. This only affects the Aetheris related memory model.

Face. Input text is bright emerald `#00FF66` on the existing `#1A1A1A` field. The border stays medium green `#3CB371`, so the edge and the words are not the same green. Output text is bright yellow `#FFFF55`, the Swing stand-in for VT100 SGR 93 (`Ansi.YELLOW`). The View modal uses that same yellow. An `EXCLUDED` cell stays gray in both areas, so the ghost still reads. Empty-response chrome can stay as it is. This is paint. It does not touch the wire.

The View dialog is born at 720 by 480, centered, every time (`showViewModal`). One client preference stores x, y, width, and height. It is global, not per turn. Save it when the dialog closes. The next View, including after a restart, opens there. If those coordinates are off the current screen, center a 720 by 480 again. Store it beside the vault user, not on `CellRecord`, so Stage 2 inspectors can reuse the same key.
- Says; sound good.

The scroll jump is `stateButton` calling `persistQuiet` and then `rebuildTabs()`. Rebuild throws away the scroll pane. The new viewport starts at the top. `scrollRectToVisible` on the whole card then pulls a tall card to the top when a pending id exists. Hold semantics: before the rebuild, remember the viewport's view Y and the card's Y. After the rebuild, set the view so `cardY - viewY` is unchanged, then focus that same cell. Apply the hold to every header control that rebuilds, including burner. View does not rebuild and does not need it. Clear-all may scroll to the new empty turn on purpose.

Import reads one text file, UTF-8, into the focused cell's user text, and replaces that text. The assistant area never receives it. An `OUTPUT_ONLY` cell refuses. No focused input cell refuses, with a status line. A NUL in the first 8 KiB refuses. Cap is 1 MiB. The cell state does not change. The import does not push the pipe.
- '' says; sounds good. But make the import a context menu with the popup option for import when mouse is over a cell and right-clicked. Too many buttons on the cell button panel is clutter.

### Open points, still

- Collapse is view-only. A collapsed `INCLUDED` cell still enters the working set. If collapse should also skip the walk.
- The footer clear affects the canvas vault only. If it must also run VT100 `clear` in the same click.
- `select model <name>` unloads the previous session model and loads the new tag. If it should only rename the session and leave RAM alone.
- `lost models` is read as `list models`. If that reading is wrong.
 - `lost models` is a typo. The command is `list models`.
- Import replaces the input text. Cap 1 MiB. If it should insert at the caret, or if the cap should move.
- Button labels are `Included`, `Excluded`, `Input only`, `Output only`. If the letter must remain visible beside the word.
- Stage 2 stays closed. Grok stays unwired. The four context states stay four.
- The open items above are addressed. Treat each accordingly.

## 1. Compatibility ledger

| Ask | Stage 1 fit | Stage 2 and 3 guard |
|---|---|---|
| Collapse | Boolean on `CellRecord`. Header stays. Body hides. | Not a fifth `CellState`. |
| Button words | Labels on the existing toggles. Same four enums. | Pin and ghost still key off `EXCLUDED`. |
| Clear turns | New footer button. Vault only. | Does not delete canvases, pipes, or a future cloud target. |
| `select model` / `list models` | New commands on the existing processor. Ollama tags only. | Provider prefix can arrive later. Grok is not accepted here. |
| Text colors | Foreground only. Ghost stays gray. | No theme law in the constitution changes. |
| View bounds | One client preference. | Not stored on the cell. Safe for a later inspector. |
| Focus hold | Viewport math around `rebuildTabs`. | No new focus owner. Stage 2 filter mode still blocks Run through `isMainViewingMode`. |
| Import | User text of the focused input cell. | No pipe push. No AMWP. Ephemeral cells still persist as burners if the pin is on. |
**semantic-key:** PK-753
**semantic-key:** PK-753
**semantic-key:** PK-753

# Aetheris Stage 1 tweaks — Draft serial 003

**Mode:** dispositions packed. **Not a plan. Not a build.** Stage 2 stays closed.
**Code a later plan has to stay compatible with:** `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.chat.cmd.CommandProcessorFactory`, `org.casserole.chat.cmd.ClearCommand`, `org.casserole.chat.cmd.SwapCommand`, `org.casserole.ssh.shell.Ansi`
**semantic-key:** PK-754
**semantic-key:** PK-754
**semantic-key:** PK-754
**semantic-key:** PK-754
**semantic-key:** PK-754
**semantic-key:** PK-754
**semantic-key:** PK-754
**semantic-key:** PK-754
**semantic-key:** PK-754
**semantic-key:** PK-754
**semantic-key:** PK-754
**semantic-key:** PK-754

# Aetheris Stage 1 tweaks — Draft serial 003

This serial records those dispositions. It does not open a plan. It does not move Java. The plan starts when he names it.

## 0. Dispositions from 002 2026-10-18 12:33

The four context states stay four. Grok stays unwired. Stage 2 stays closed.

The first collapse note asked for a second set of state buttons on the bar. The next note retired that. One set of buttons, always rendered. The brief is the only thing that appears when the body hides.

The letters stay one character wide. The tooltip carries the word he would have forgotten.

`swap` already does the model change. The new commands sit on the table until he picks them up.

The footer clear and the console clear stay in different rooms.

Face. The greens stay two greens. The yellow matches the console. Ghost stays gray.

Nothing in this serial is a plan slice and nothing is a class change. The next paper is a plan, and only after he says to plan. It did not file code.

## 1. Gate

Planning waits. Do not write a plan file, do not edit Java, do not open Stage 2.

The pre-plan and the plan stay stamped for the build already filed. Do not edit this file.

# Aetheris Stage 1 tweaks — Draft serial 004

# Aetheris Stage 1 tweaks — Draft serial 004

The collapsed brief is the output of the cell. Everything else from 003 stands. This serial is not a plan and it does not move Java.

## 0. What 004 changes 2026-10-18 12:50

The collapsed brief is the assistant text, from the first character, one line, or two lines if the bar height allows. Plain truncation. No summary engine. The user text is not the brief. An empty output leaves the brief empty. It does not fall back to the input.

That reading comes from on 003: the brief is the output of a cell, and the input is not relevant when the cell is included.

## 1. Dispositions carried from 003

`[NEW on 2026-10-18 1:24 PM]`

### Additional tweaks and enhancements.

Beautiful work! Just some more minor tweaks to flesh out in spec.

- First go ahead and meaning encode what what relates to the build we just completed.
- When I click on Run in the Aetheris pane, please shift focus straight to the next new turn cell. This will automatically bring the last result into focus and make it so I don't have to do an extra scroll action to make an input.
- When a cell is excluded from the chat history, please hide it in the inspector view. The inspector view ONLY shows what is active on the memory list chain.
- When I add another session to the VT100 console, under a different user, The older Aetheris tab is replaced by the new relative session according to the new user. The relative Aetheris tab MUST stay focused relative to the current active console window in the VT100 terminal. I presume the Swing UI just stomped it, the older aetheris session is still active. I need to bounce between the two Aetheris instances depending on teh active window in the VT100 interface.

The brief reads the output. Inclusion already decided that the input is in the walk. The bar does not repeat it.

The rest of 003 is unchanged. Planning still waits for his word.

## 2. Gate

Planning waits.

# Aetheris Stage 1 tweaks — Draft serial 005

**Code a later plan has to stay compatible with:** `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.app.ui.ssh.SshClientApp`, `org.casserole.chat.cmd.CommandProcessorFactory`, `org.casserole.chat.cmd.ClearCommand`, `org.casserole.chat.cmd.SwapCommand`, `org.casserole.ssh.shell.Ansi`
**semantic-key:** PK-755
**semantic-key:** PK-755
**semantic-key:** PK-755
**semantic-key:** PK-755
**semantic-key:** PK-755
**semantic-key:** PK-755
**semantic-key:** PK-755

# Aetheris Stage 1 tweaks — Draft serial 005

One snack moved. The filed build stays filed. Four new asks are on the table with the team's reading. This serial is not a plan and it does not move Java.

## 0. What 005 changes 2026-10-18 14:03

The build already in the tree is encoded as a code spec. Run, after it finishes, puts the caret in the next new turn and keeps that answer in the same view. The inspector lists the memory chain and leaves an excluded cell out of that list. The Aetheris notebook on screen is the notebook of the VT100 console that has focus, and the other user's notebook is still there when that console is focused again.

## 1. Dispositions carried from 004

This serial does not reopen them. The four context states stay four. Grok stays unwired. Stage 2 stays closed.

## 2. The snack 2026-10-18 13:24

> First go ahead and meaning encode what what relates to the build we just completed.
> When I click on Run in the Aetheris pane, please shift focus straight to the next new turn cell. This will automatically bring the last result into focus and make it so I don't have to do an extra scroll action to make an input.
> When I add another session to the VT100 console, under a different user, The older Aetheris tab is replaced by the new relative session according to the new user. The relative Aetheris tab MUST stay focused relative to the current active console window in the VT100 terminal. I presume the Swing UI just stomped it, the older aetheris session is still active. I need to bounce between the two Aetheris instances depending on teh active window in the VT100 interface.

### Run lands on the next input

A finished Run on the last cell writes the answer, creates the next turn, and leaves the caret in the answer. The new input sits below that card. The ask puts the caret in the new turn's input and keeps the answer in that same view. The header focus-hold stays as it is. A state click still does not jump the scroll.

### Inspector shows the chain

A refresh of the inspector already omits an excluded cell from the working set. The `E` click does not refresh the inspector, so the old list stays until the next Preview, Run, or new turn. The last cell's user text is inserted into that list even when the last cell is excluded. The ask is the list he will actually send. An excluded cell is absent from it, including when that cell is the last one. `I`, `P`, and `O` stay the active half of a cell. A collapsed included cell stays on the chain. The card on the canvas stays visible.

### Focused console owns the notebook

The Shell has one Aetheris tab and one notebook. Connecting the focused session as a new user closes the open vault and loads that user. The tree's `aetheris.yaml` is disk, so the previous user's vault remains at `~/.aetheris/{user}/vault`. Clicking the other terminal enables the tab when that pipe is AMWP and does not load that user's vault back. The older session is on disk. It is not the notebook on screen. The ask is one notebook that follows the focused VT100 window, with the other user's canvases waiting when that window is focused again. A swap writes the open vault before it closes.

The team read the hull. They did not lock the snack. They did not move Java.

Three gaps, counted. Run focuses the answered cell, and the new turn will not take a focus that is already set. `E` changes the cell and leaves the inspector text where it was. One `bindUser` on the focused connect replaces the pane. `selectSession` does not bind. The disk file of the other user is the session that is still alive.

The caret belongs where the next sentence starts, with the answer still in the room. The inspector is the chain he is about to send. Two consoles are two notebooks, and the live one is the one under his hands.

One listener and one pane. The focused terminal picks the vault and the pipe. A second product tab is a different machine. This ask does not build that machine.

The disk vault per user is already the shelf. The swap is the fix. A RAM vault would not be sitting there when he comes back. This yaml is disk. Write it before the close.

Face. The three asks are small at the door. The session one is the one that looks like a stomp. The other notebook is on disk. The pane is showing the user who connected last.

004 is the fallback. 005 is the draft. The encode is the contract of `ec28e2e`. Planning still waits. Stage 2 stays closed.

## 3. Gate

MUNCHED 2026-10-19 13:17. Note-taking closed. N1 through N9 and S1 through S12 are APPROVED. Do not edit this file.

# Aetheris Stage 1 tweaks — Draft serial 006

**Mode:** NOTE-TAKING. **Not a plan. Not a build.** Stage 2 stays closed.
**Code a later plan has to stay compatible with:** `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.app.ui.ssh.SshClientApp`
**semantic-key:** PK-756
**semantic-key:** PK-756
**semantic-key:** PK-756
**semantic-key:** PK-756

# Aetheris Stage 1 tweaks — Draft serial 006

005 is closed. Plan 002 is encoded and closed. This serial is a pad. No plan and no Java until says note-taking is done.

## 0. What 006 changes 2026-10-18 15:45

The 002 build is closed. The pad is open for the next handful of tweaks.

## 1. Closed 2026-10-18 15:45

The tweaks are not Stage 2 slices. They slide into the Stage 2 rows already planned. The notes on this pad are the next mini-slice, and they join that same account. Stage 2 stays closed.

The locked table in 005 stands. It is not reopened. U4 was not clicked. It is not an open slice. `select model` and `list models` stay. Stage 2 stays closed.

## 2. Notes

Brainstorm stays on this page. It does not become a plan on its own.

### N1 — End of the pane 2026-10-18 18:03

> I just had you add the move focus to next turn cell, in playing with it, it should just shift focus to the end of the pane itself. This will scroll everything down to the end, exposing the new turn cell card completely.

After Run, the canvas scrolls to the end of the pane. The new turn card is fully in view. The closed 002 encode stays closed. This note waits.

### N2 — User on the status line 2026-10-18 18:13

> In the status line at the very bottom, include the currently connected user. That way when the user focus changes according to the Vt100 pane, It's easy to know which user is in focus.

The bottom status line names the connected user of the focused VT100 session. It changes when that focus changes. This note waits.

### N3 — Import dialog size and folder 2026-10-18 18:16

> If it's possible, go ahaead and check for feasability; For the file select Dialog for importing files, whether it's the inport on the Vt100 or in the Aetheris pane, into the conversation turn; persiste resised window dialog. Makes it easier to keep focus on work is being donw. Also persist the last directory a file was imported from. This allows for the user to import anotehr file again from the same location without having to re-navigate to the same folder.

Feasible. Both choosers are a fresh `JFileChooser` with no memory. The VT100 chooser pastes the file into the terminal. The Aetheris chooser fills the turn. Swing can restore the dialog bounds and the last folder. One size and one folder serve both. A missing folder opens at the platform default. This note waits.

### N4 — Incrementor 2026-10-18 18:38

> I would like a new control, make it a re-usable control widget with a + and a - button next to each other and an editable text vlue adjacent to it; In this case this reusable widget we are making for our Aetheris app, will be placed on the button bar of the bottom of the Aetheris panel; when + is clicked increases the number of viewable rows in a cell for both input and output panes, and - descreases the height by one row in both cases. This allows me to change the amount of viewable real-eastate on the pane. Make the selected number of viewable lines per output and input text pane persistable, and when the app opens up it restores to the last persisted height value in rows that was defined last. Changing teh value manually has the same effect according to what the value is. It changes as the value changes after like a.8 second timeout. Then the pane re-renders. Call it an incrementor control.

Feasible. The control is an incrementor: `+`, `-`, and an editable value, on the Aetheris footer. `+` and `-` change the viewable rows of the input pane and the output pane by one, then the pane re-renders. A typed value waits 0.8 seconds after it settles, then does the same. The row count is saved and restored when the app opens. An open cell is 4 rows today, and the open card is capped at 280, so the row count has to drive the card height or the extra rows stay clipped. This note waits.

### N5 — Inspector turn divider 2026-10-18 19:31

> In the inspector, add a thin divider line between each turn-pair, so it's easier to distinguish one turn from the next in the inspector view on the right.

> AHHH ok. thats perfectly fine to me. As long as there is a divider between cards, that is the ask.

Accepted 2026-10-19 12:28. The divider sits between cards. An included card is two role lines. An input-only card is one role line. An output-only card is one role line. An excluded card stays out of the list, and the dividers close up between the cards that remain. The header and the token estimate stay outside the rules. The inspector stays one text area. The rule stays short so soft wrap keeps it on one row. This note waits.

### N6 — Seed-kit keeps the note 2026-10-18 19:48

> OK, please add a note to this tweak list to ensure that when a new seed-kit is generated, that the nuance to keep notes of what to do or what occurred, in format, are ALWAYS recorded in the open spec serial or to a place where an operator may direct.

A new seed-kit carries this. Notes of what to do, and notes of what occurred, stay in on the open spec serial. An operator may name another place. Prod 1.0 stays frozen. Not before. Adjusted 2026-10-18 19:53. This note waits.

### N7 — Connected-user pull-down 2026-10-18 20:36

> I love the context switching we have now between the VT100 and the aetheris pane; just thinking about usability for other users, we need a pull down for connected users in the Aetheris pane, which can switch the view from one connected user chat in the card layout, to anothers. Add it to the bottom global button bar, to the right of the existing buttons, and in the layout for it, it is pinned to the east side of the button bar.

A pull-down of connected users sits on the Aetheris bottom button bar, to the right of the existing buttons, pinned to the east side. Choosing a name switches the card layout to that connected user's chat. This note waits.

### N8 — Thinking spinner 2026-10-18 22:09

> The thinking spinner for the Aetheris pane, at lower right of the screen, I would love for it to be a bright royal blue color while spinning. And animate it with interleaving green used for the input font, the bright emerald we specified. It should give it a little pizazz and make it more noticeable by default while thinking. Making it easier on the eyes to keep tabs on it.

While it spins, the thinking spinner is bright royal blue, interleaved with the input emerald `#00FF66`. It stays at the lower right. The mark is easier to see while thinking. This note waits.

### N9 — Split divider width 2026-10-19 11:09

> Please increase the width of ALL JSplitPane divider width settings, bound by a global static constant, by 3 pixels. It makes it easier to locate the expand and collapse buttons on JSpliPane components.

That sounds wonderful. PLease update the notes accordingly in 006 for me please, so it is recorded as the accepted solution for this use case and I will continue chewing on 006.

The shell bar, the session bar, the notebook bar, and every open cell bar read it. Each open card pays the extra 3 pixels. The one-touch arrows sit in that thicker bar.

The open height is the connection panel's preferred height plus `getDividerSize`. On collapse, the current position is remembered only when it is at least that tall. A reading of 0, a sliver, or a half-open drag leaves the saved height alone. On expand, a position shorter than that figure opens at the figure. The literal 8 drops out. This note waits.

The pad is open. I collect. I do not build. The kit stays frozen. More notes are coming. I sit with him.

U1, U2, and U3 are filed. U4 was not clicked and is closed with them. The count for the next notes starts at zero.

This memorandum is the reading of N1 through N9. The notes stay as he tapped them. Note-taking stays open. Stage 2 stays closed. Prod 1.0 stays frozen. A new seed-kit waits until he says he is ready. U4 was not clicked. The reading is against the code that is filed.

The count is nine notes. §5.1 already carries N1 through N4. N5 through N9 join that row when note-taking closes. This turn does not write them there. None of these notes is a Stage 2 slice. The hull:

- One vault follows the focused VT100 session. N7's pull-down selects that session. A combo that only swaps the cards leaves Run on the old connector. The listener on the connector that lost focus drops frames. Stage 2 Grok, MCP `0x0004`, and crosstalk stay on that same pipe. The pull-down is a view switch. It is not crosstalk, and it does not copy cells from one user into another.
 - It is NOT cross talk. The session is still running, the card just slides to the user in focus according to the VT100. It's a shell game. If Comms should be affected will say so, otherwise protecting the mux protocol is imperative.
- The footer east slot is the thinking spinner today. N7 also pins to the east. One east panel holds the pull-down and then the spinner. The pull-down sits to the right of Run, New turn, Clear turns, and Preview. The spinner stays the lower-right corner.
 - Says; create a new encapsulating border layout panel, and place the spinner on the east, and the pull down on the west, then put that encapsulating container on the east of the footer east. They will then just snap together on the east side, but laid out next to each other on the east.
- N1 and N4 meet when the new card is taller than the canvas. Scroll to the end of the pane shows the bottom of the canvas. A card taller than the viewport cannot be fully on screen at the same time. N1 is the Run land, the path that sets `pendingLandOnInput`. A header hold still uses the delta and keeps the operator's place. A state click and a later `CONTEXT-PIN` commit use that hold.
 - It's ok, we don't have to account for the intersection. One action can occur independent of the other, the user will just have to accommodate in this use case. An excellent catch. We don't have to over-engineer for this use case. First one that hits the scroll wins, if the other interferes and the user has to scroll again, so be it.
**semantic-key:** PK-850

- N5's line sits between turns. An `INCLUDED` turn is two role lines. An `INPUT_ONLY` or `OUTPUT_ONLY` turn is one role line. A line between every two roles would cut a pair in half. An `EXCLUDED` turn stays out of the list, and the lines close up. The header `working set → id` and `est. tokens` stay outside the rules. `LEX-FILTER` removes a turn from this same list. The inspector stays one text area. Soft wrap can break a long rule, so the rule stays short.
- N6 is the next seed-kit. It has no Stage 2 row.

The machines, as they sit.

N1. After Run, the canvas viewport goes to its maximum Y and the new turn's input takes focus. That is the land path. The user-area scroll on that path is the behavior this note replaces. New turn and Clear turns still scroll the whole card, which brings the top of that card into view. A collapsed card has no user area. The land path then scrolls the card itself. The row count is applied before that scroll, so the end of the pane includes the height N4 just set.

N4. The incrementor is the reusable widget he named. Its first seat is the west button flow, with the buttons. Plus and minus apply on the click and cancel a pending typed value. A typed value waits 0.8 seconds after the last key, then applies. The value is a whole number of rows. The minimum is 1. An empty box or a non-numeric box leaves the last good value in place. The saved number is rows, so a font-size change recomputes the pixel height. Both open panes take it. The collapsed brief stays one line, or two when the header is at least two line-heights tall. The inspector and the View modal do not take the number. An open card is capped at 280 today, and `setRows` does not cap a filled area, so the row count has to set the card height or the extra rows stay clipped. Text past the visible rows scrolls inside the pane. The value is one Shell preference, restored when the app opens. It is not a column on the cell. Switching users does not change it. Roux sync does not carry it.

N9. One static constant, value 11. `applySplitPane` reads it. The collapse check reads it. A split added later uses that door.

N3. The VT100 send-file chooser and the Aetheris cell chooser are each a fresh `JFileChooser`. `showOpenDialog` disposes its dialog before it returns, so the bounds come from that dialog window. One size and one folder serve both doors. They are one Shell preference. A missing folder opens at the platform default. A saved rectangle that misses the current screen opens at the platform default size, and the folder is kept when it still exists. The size is kept when the dialog closes, including a cancel. The folder is kept when a file is approved, including when the later read refuses for size or for a NUL in the first 8 KiB. An `OUTPUT_ONLY` cell still refuses before the chooser opens. The Stage 2 `.aetheris` door does not use this pair. Two vault users on one window share the chooser memory.

The wire.

The pull-down lists connected AMWP sessions by the sanitized username the vault opens. A connecting pane and a disconnected pane are omitted. Two panes with the same username are one entry and one vault. That is the rule U3 already runs. Choosing the name selects that session. When the focused pane is already that user, it stays, and the vault does not reload. Otherwise the first connected pane with that name is selected. An illegal name still becomes `anon`. Two illegal names share that vault.

A switch during a run clears the listener on the old connector. The in-flight frame is dropped. The spinner stops as the switch starts. A frame already queued on the event thread can still arrive. `applyAssistant` looks up the cell id in the open canvases. After the swap that id is absent, so the frame does not write. The epoch already discards a stale bind.

When focus is not a connected AMWP session, the vault stays open. The combo has no selection. The status names no user. The cards on screen are the last vault.

N2. The line at the very bottom of the window is the frame status label. It says Connected, Connecting, Disconnected, or an Aetheris message, and each of those writes replaces the whole string. The connected user is included on that line and is still there after the next message. When the focused session is not a connected AMWP user, the name is absent. The footer label stays the vault mode, `vault: disk` or `vault: ram`, in the button row.

Face. The paint.

N8. While the spinner spins, the base is bright royal blue and the moving glyph interleaves the input emerald `#00FF66`. The word `thinking` stays royal blue. The timer step is 120 milliseconds. The label is hidden while the run is idle. `applyLabel` sets the theme foreground once. The spin sets its own color after that, on each frame. The spec has no hex for royal blue. The reading on the table is `#4169E1`. The cell border stays `#3CB371`. The emerald stays `#00FF66`.

The user name on the status line uses that line's existing foreground.

The inspector rule is a short line of light strokes between turns, in the same text, so the list still reads as a list. The spinner keeps the word `thinking`.

The slide-in, recorded here until note-taking closes:

| Note | Slides into |
|---|---|
| N1 | Chrome on the cards that groups and pipes use. The Run land only. A pin and a state click keep the hold. |
| N2 | The frame status line names the focused connected user. That is the user the one vault is showing. |
| N3 | The local chooser for the VT100 paste and the cell import. The `.aetheris` door stays its own door. |
| N4 | Chrome on those same cards. Pipes still read the text. The brief stays the brief. |
| N5 | The inspector list U2 already refreshes, including after a pin. The rules follow the turns that remain. |
| N6 | The next seed-kit. No Stage 2 row. |
| N7 | The same one-tab, one-vault pipe. It selects the session. Grok, MCP, and crosstalk stay on it. |
| N8 | Paint on the spinner at the lower right. The pipe is unchanged. |
| N9 | Shell chrome. Every split that comes through `applySplitPane`, including a split Stage 2 adds. |

Open points, still:

- Bright royal blue has no hex in the spec. The reading is `#4169E1`. If the hex moves.
- The glyph interleaves `#00FF66`. The word `thinking` stays royal blue. If the whole label should swap colors.
- N1 is the Run land. If New turn and Clear turns should also go to the end of the pane.
- N4 has no row ceiling. A very large value makes the new card taller than the window, and N1 then shows the bottom. If a ceiling is wanted.
- N5 is a short text rule in the one inspector area. If that area must become a painted list.
- The row count and the chooser memory are one Shell preference. If each vault user keeps a private copy.

He keeps chewing on 006.

The accepted solution is on N9. The 11:32 line that moved the literal 8 onto the constant is closed by that note. Thickness is the constant 11. The open height is the connection panel's preferred height plus the live divider size. Collapse remembers a position only when it is at least that tall. Expand floors at that same figure. A sliver cannot take the place of a real open panel.

`applySplitPane` writes the constant once, after the split UI is installed. The shell bar, the session bar, the notebook bar, and every open cell bar read it.

A divider between cards is the ask. He keeps chewing on 006.

The accepted solution is on N5. The divider follows the card. An included card keeps its two role lines together. An input-only card and an output-only card each keep their one line. The 11:32 open point that asked whether the inspector must become a painted list is closed. The inspector stays one text area. The rule stays short.

Pre-plan stays closed until smokes the table below. These rows are the team questions he has not answered directly. Each proposed solution is the reading already on the pad.

N7 is not crosstalk, the mux stays protected, and the east capsule is a border panel with the pull-down on the west and the spinner on the east. N1 and N4 do not special-case each other. The first scroll wins, and the operator scrolls again if the two meet. N9 is the constant 11 and the open-height figure. N5 is a short rule between cards in the one inspector area. N6 is the next seed-kit, cut when says he is ready.

### Final smoke

| # | Note | Still open | Proposed solution |
|---|---|---|---|
| S1 | N8 | No hex was named for bright royal blue. | Use `#4169E1` while the spinner spins. The cell border stays `#3CB371`. The emerald stays `#00FF66`. |
| S2 | N8 | Which part of the spinner carries the emerald. | The moving glyph steps `#00FF66`. The word `thinking` stays royal blue. |
| S3 | N1 | Whether New turn and Clear turns also jump to the end of the pane. | The end-scroll is the Run land only. New turn and Clear turns still bring the top of that card into view. |
| S4 | N1 | Whether a header click or a state click also jumps to the end. | A header click, including collapse and `IEPO`, keeps the operator's place. It does not jump to the end. |
| S5 | N4 | Whether the row count has a ceiling. | No ceiling. The minimum is 1. A card taller than the window shows its bottom on the Run land, and the operator scrolls. |
| S6 | N4 | Where the incrementor sits, now that the east capsule is the pull-down and the spinner. | The incrementor sits in the west button flow, with Run, New turn, Clear turns, and Preview. |
| S7 | N3, N4 | Whether the row count and the chooser memory are one Shell preference or private per vault user. | One Shell preference for the row count. One Shell preference for the chooser size and folder. Switching users does not change them. |
| S8 | N3 | What is kept on cancel, on a refused read, and when the saved place is gone. | The dialog size is kept on cancel and on approve. The folder is kept when a file is approved, including when the later read refuses. A missing folder, or a rectangle that misses the screen, opens at the platform default. An `OUTPUT_ONLY` cell still refuses before the chooser opens. The `.aetheris` door stays its own door. |
| S9 | N2 | Which bottom line carries the user, and whether a later message erases the name. | The frame status line at the very bottom of the window includes the focused connected user. The next status message keeps that name. The Aetheris footer keeps the vault mode label. |
| S10 | N7 | Whether choosing a name selects the VT100 session. | Choosing a name selects that connected session. The card follows that session. The mux stays up. Two panes with the same username are one entry, and the vault does not reload. |
| S11 | N7 | What happens to a run that is in flight when the user changes. | The in-flight reply is dropped. The spinner stops. The session stays connected. |
| S12 | N7 | What the pull-down and the status show when focus is not a connected AMWP session. | The pull-down has no selection. The status names no user. The cards stay on the last vault. |

## 3. Gate

Note-taking stays open until says it is done. Until that sentence: do not write a plan, do not edit Java, do not open Stage 2.

MUNCHED 2026-10-19 17:56. Note-taking closed. N10 through N14 are APPROVED. Do not edit this file.

# Aetheris Stage 1 tweaks — Draft serial 007

**Code a later plan has to stay compatible with:** `org.aetheris.swing.AetherisNotebookPanel`, `org.aetheris.swing.Incrementor`, `org.casserole.app.ui.ssh.SshClientApp`
**semantic-key:** PK-757
**semantic-key:** PK-757
**semantic-key:** PK-757

# Aetheris Stage 1 tweaks — Draft serial 007

This serial is a pad. No plan and no Java until says note-taking is done.

## 0. What 007 changes 2026-10-19 16:57

The pad is open for a couple of minor items.

## 1. Closed 2026-10-19 16:57

006, the pre-plan, and plan 003 stay frozen. N1 through N9 and S1 through S12 stay approved. The compile stays. N6 stays held. `select model` and `list models` stay. Stage 2 stays closed.

## 2. Notes

### N10 — Inspector reverse order 2026-10-19 16:57

> Make the inspector entries operate in reverse-order sort. That way the newest one is always inserted to the top of the list. This alleviates any attempts to keep the new entries at the bottom in focus.

The inspector list runs newest first. The newest contributing card is the first entry under the header. Older cards follow it downward.

`refreshInspector` writes oldest first today. Prior cards, then the target card, under `working set → id`, with `------------` between cards and `est. tokens` last. The card blocks reverse. The header stays first. The token line stays last. The hyphen rule stays between cards. Inside an included card the user line still precedes the assistant line. An excluded card still stays out, and the rules close up. The numbers follow the list, so the top line is 1.

The list on screen reverses. `assemble` still sends oldest first. The canvas land stays as built. One text area. Feasible. This note waits. No Java until note-taking is done.

### N11 — Spinner west, pull-down east 2026-10-19 17:02

> please swap the positions of the spinner and the user pull down, when the spinner activates it pushes the pulldown to the left. The pull down then should be on the E and the spinner on the west in the encapsulating borderlayout.

The pull-down sits on the east of the capsule. The spinner sits on the west of that same capsule. The capsule stays on the east of the footer.

### N12 — Shorter buttons, bevel on all 2026-10-19 17:32

> The buttons are too big on the Aetheris pane. They should be reduced in height by 40%, adjust the fonts for them accordingly. Also add a beveled shadow to ALL buttons to make them more noticeable as Buttons.

Aetheris buttons lose 40% of their height. The font on those buttons shrinks so the label still fits. Every button gains a beveled shadow.

The height cut is the Aetheris pane: Run, New turn, Clear turns, Preview, the incrementor, the card buttons, and the View modal Close. The shell buttons keep their height. `applyButton` paints a flat line border today. The bevel is a raised shadow on every button that painter touches, shell and Aetheris, so a button reads as a button. The fill colors stay. Feasible. This note waits. No Java until note-taking is done.

### N13 — Scrollbar arrows 2026-10-19 17:32

> make all scrollbar arrows on each end visible, just a light gray with black arrows.

Every scrollbar shows an arrow button at each end. The button face is light gray. The arrow is black.

That is the canvas, each open cell pane, the inspector, the View modal, and the shell text scrolls. Vertical and horizontal. The theme today sets `ScrollBar.background` and leaves the arrow buttons to the platform look. The terminal paints its own bar. Feasible. This note waits. No Java until note-taking is done.

### N14 — Inspector soft wrap 2026-10-19 17:37

> Soft-wrap does not appear to be working. Please add a re-usable softwrap handler that senses changes in inspector width, and when enabled, will automaticlaly adjust text to wrap according to the width of the inspector window. Enable softwrap by default.

The inspector wraps to its own width when soft wrap is on. The wrap follows the pane as the divider moves. Soft wrap starts on.

The checkbox and the flag already start on. `setLineWrap(true)` is already called. The area still sizes to the longest line, so the pane scrolls sideways. The handler is the piece that makes the flag wrap. The team reading is the briefing below. This note waits. No Java until note-taking is done.

Note-taking stays open. Stage 2 stays closed.

One new note. The hull is sound. The team agrees it is a go.

The flag is already on. `inspectorSoftWrap` starts true, the checkbox starts checked, and `setLineWrap(true)` plus `setWrapStyleWord(true)` already run. The area still reports a preferred width of the longest line. The scroll pane honors that width. Narrowing the inspector moves the divider and the text stays one row, with a sideways scroll. That is the hole.

The text sits in the inner scroll pane. `wrap` builds a second scroll pane around the header and that inner pane. The handler attaches to the inner pane, the one that owns the text. Once that area tracks the inner viewport, the outer pane stops growing sideways from the unwrapped line.

1. One reusable handler, a text area that can turn soft wrap on and off. `// ` lands on that type when a later build creates it. No Java on this turn.
2. While wrap is on, the area tracks the viewport width, and a resize of that viewport reflows the text. The sense is a component listener on the viewport. Turning the existing checkbox off restores the long-line width and the horizontal bar.
3. Soft wrap stays on at launch. The checkbox is the switch. It is not a saved preference. He did not ask to keep an off state.
4. Wrap is on word boundaries. The twelve-hyphen rule has no spaces, and a UUID in the header has no spaces. At a normal inspector width both fit on one row. A very narrow pane can leave those two lines longer than the pane. The previews have spaces, so they reflow. The team does not switch the area to character wrap.
5. The first seat is the inspector. The open cell panes and the View modal already ask for line wrap and can show the same hole. They are later seats for the same handler. This note does not move them.
7. `assemble` still sends oldest first. N10 still paints newest first. The wrap is paint on the one text area.

The machines. The override that tracks the viewport is the part that makes the width stick. A scrollbar policy alone can miss it. The listener covers a toggle that happens while the divider stays still, and a divider move that happens while wrap stays on.

The wire is quiet. Nothing in this note touches the connector, the vault, or the working set that gets sent.

Face. The lines break inside the pane. The header and the rule stay one row at a normal width. The previews fold.

The checkbox already says `soft wrap`. The handler does not need a second name on the glass.

He chews.

# Aetheris Stage 1 tweaks — Draft serial 008

**Mode:** NOTE + BUILD. The N15 section stays. Stage 2 stays closed.

This pad is N15. asked to knock it out on this turn.

## N15 — Soft-wrap column width 2026-10-19 18:39

> everything is perfect. Lets advance 007 to 008, and lets do a quick fix on softwrap. It works, but there is no defined width for the wrap, so it doesn't align with the inspector width. HOWEVER a simple fix is to provide a new +/- control for the WIDTH of the softwrap, next to the softwrap checkbox. So that when enabled, it simply enforces the wrap on the current configured width for the inspector softwrap. Make it persistable. And default the +/- component to 80 characters.

The wrap width is a character count. Default 80. The count is saved. While soft wrap is on, the inspector wraps at that count.

### Problems

1. N14 tracks the viewport. The wrap edge is whatever the pane is, so there is no width to align. A divider move changes it.
2. A fixed column wider than the pane is clipped if the horizontal bar stays hidden. N14 hid that bar while wrap was on.
3. A pixel width goes stale when the font changes. The shell preference for card rows is a character-shaped integer, and this one belongs with it.
4. The footer incrementor sets card rows. A second one has to be obvious, or the two controls get mixed up.
5. The hyphen rule and the UUID have no spaces. Word wrap will not cut them. A full preview is 80 characters of body plus the `N. role` prefix, so at a width of 80 a long preview folds once.
6. The open cell panes and the View modal are still later seats.

### Solution

2. The inner inspector bar is `AS_NEEDED` in both states. A column wider than the pane scrolls, and N13's arrows sit on that bar. The outer bar stays hidden.

5. Word wrap stays. The hyphen rule and the UUID stay one token. Raising the count is how a long preview stays one row.
6. Cell panes and the View modal stay off this handler. `assemble` stays oldest first. N10 stays newest first.

The solution above is the code.

The column is the width. The pane is only the window it sits in.

Pixels were the wrong save. The integer is the save. The font supplies the pixels.

The wire stays quiet.

Face. The new control sits on the checkbox. The row control stays on the footer.

- **State:** open. Later asks append as the next F row. The page stays open.
- **Not:** a restamp of plan 001. Not a pre-plan. Draft.001, the pre-plan, and plan 001 stay as they are.
- **Stage:** `STAGE` stays `1` / `BUILT`. `WORKING` stays the stage map.

## How an ask lands

A new ask is the next F row. The row records his words and the layout that follows. Code for a row waits until the same sentence, or a later one, says build. CONCAT, the group actions, and the execution target stay as Wild-Card built them unless a row says otherwise.

A ask adds the row, runs the full cycle, files it, and builds the code. This sentence is the operative reading of a ask.

Code named on the Grok API serial pauses. Then that build. A build sentence on a Grok row does not lift this pause. This note does not write a pre-plan. No Java in this note.

This page stays the operative reading. A later change to the dance is a dated sentence on that spec in the same turn.

A ask still adds the row, runs the full cycle, files it, and builds the code. A side move is a dependency the new code stands on, or a change adds, that the named sentence did not ask for. An asked refactor stays on the path it already has. Each side move names the move, the requirement it serves, and the sibling. A send that adds, removes, or reorders a system line carries a wire stanza in the section 12 match. The stanza names the door, then the system lines in send order, then the line in the seat the server replaces. A canvas Run, a glass send, a group send, and a pipe send are separate doors. A shared head names the doors that share it. A land that leaves the working set as it stands carries an empty stanza and an empty side list. One sentence: no side move on this plate. A neighbor line named in the plan's what-stays is named in the match. The Rust slice, when he names it, quotes the stanza. A slice sentence that contradicts the stanza holds the slice commit. A wire order sits outside the jazz measure. A shape that cannot carry a wire order comes back to before the bake. Section 9 records it after he has seen the sentence. The cargo test asserts the same first system line the stanza names. Papers for the sibling move in this filing. A side move inside a bake he has already named is corrected in that bake. Locked F rows stay as filed. This sentence extends the operative reading. No new F row. No new L-number.

The decision block is a publish spec. Operative reading of a ask stays. No new F row.

Same turn files the spec, mints the next key, and carries the next PM mark. The behavior prose sits on that spec. A cam novel names the key and points at the spec. The guide-filing lock that keeps an earlier F row closed does not override the call. The operative reading stays.

## Group chrome split

Build it in this sentence.

A grouped member card is 480 pixels wide. The header was one `FlowLayout` line. That line wraps, and the card keeps a one-row height, so the target field, Group, Join, and Ungroup were clipped.

The header is now a vertical `JSplitPane`.

- Top pane: turn label, state buttons `IEPO`, collapse, burner, View.
- Bottom pane: execution target, Group, Join, Ungroup.
- The bottom pane is collapsed by default. The card height is the top row plus the divider.
- The divider is one-touch. Opening it grows the card by the bottom row so those controls stay in view. Collapsing it returns the card to the top row plus the divider.
- Dragging the divider so the bottom row has room keeps the pane open. Dragging it shut, or the one-touch that hides the bottom, collapses it.
- The one-touch that would hide the top row is treated as open, and the divider snaps so both rows stay visible.
- The open or collapsed choice is not stored on the cell. A card rebuild starts collapsed.
- A readable card uses this split, grouped or not. Group is on the lower pane, so a sequential cell can still start a group.
- An unreadable card stays the short load-error header. It has no split.

## Group bar toggle

The canvas height was holding the split, so the lower buttons stayed clipped. The header split is gone.

The main bar keeps the turn, the state buttons, collapse, burner, and View, and it gains a `v` toggle. The `v` is the down indicator. The second bar is hidden.

A click opens the second bar under the main bar and the mark becomes `^`. The card grows by that bar. The group member row's horizontal scroller keeps a vertical minimum equal to the row, so the canvas column grows and its own scroll can bring the bar into view. A second click hides the bar, the mark returns to `v`, and the card shrinks.

The second bar holds the execution target, Group, Join, and Ungroup. The choice is not stored on the cell. A card rebuild starts closed. An unreadable card has no toggle.

## Grouped member width

A grouped member was preferring 400 pixels and capping at 480. The main bar is wider than the width the card was given, so the `v` sat past the east edge.

A grouped member is now the width of its main bar, plus the card chrome, plus 10 pixels. That leaves at least 10 pixels between the `v` and the card's east edge. The card does not shrink below that width. A font change measures the bar again. A sequential card and the merge sink stay on the column width. An unreadable card stays the short load-error header.

A layout probe laid the real cards out in a 1280 by 800 window. The east gap was 23 pixels at font 8, font 19, and font 32. Font 19 is the control-panel size. Those member widths were 452 and 476. Font 32 grew to 580 and 621, past the old 480 cap, and the gap stayed 23. The window was not opened by hand.

## Footer tool bar

BB1 is the original footer bar: Run, Stop, New turn, Clear turns, Preview, and the row count. BB2 is the tool bar: search, Search, Export, Import, Pull, and To terminal.

BB1 gains a `v` toggle. BB2 starts hidden. A click opens BB2 under BB1 and the mark becomes `^`. A second click hides BB2 and the mark returns to `v`. The choice is not stored.

The vault line was painted on BB1 and on the thin status line. It stays on the status line. BB1 no longer carries it. A card click writes that line to the status line.

A layout probe at font 19 found BB2 closed, then open. The footer grew from 107 to 149. The vault line reached the status host. No label on the panel carried it. The window was not opened by hand.

## Button tips

Every button on the notebook carries a hover tip. The tip says what the control is and what the click does.

The footer buttons are Run, Stop, New turn, Clear turns, Preview, the row-count minus and plus, and the tool-bar v. The tool bar is Search, Export, Import, Pull, and To terminal. Each turn has I, E, P, O, collapse, burner, View, the group-bar v, Group, Join, and Ungroup. A turn that did not load keeps the ? mark and the tip says why. The inspector has soft wrap, its minus and plus, the line-length minus and plus, pin, and OK. The canvas split and the prompt/reply split each have two arrows. The reply window has Close. The mood dialog has Send, Approve, and Cancel. The card menu Import carries a tip. The connected-user pull-down names the selected user and says that a choice switches the vault on screen. The fields that already had a short tip now use the same kind of sentence.

A v tip changes with the click. Closed says show. Open says hide. The choice is still not stored.

A layout probe at font 19 walked the built notebook. 37 buttons carried a tip. The tool-bar v and the group-bar v flipped from show to hide and back. The window was not opened by hand.

## Collapsed line beside the buttons

A collapsed card kept a one-line preview under the button bar. That line now sits on the same row, to the right of the buttons. The card height is that row. Opening the card puts the prompt and the reply back under the buttons, at the saved row count. The line leaves the button row.

The line is the start of the reply. It is one line. Ten pixels of padding stay on its east side. A grouped member that is collapsed grows by that line, so the text has room east of the v. An open member stays the width of its main bar plus 10 pixels. The group bar still opens under the row. The choice to collapse stays on the cell. The group-bar choice stays off the cell.

A layout probe at font 19 in a 1280 by 800 window measured a collapsed sequential card at 48 pixels tall. The open card beside it was 284. The line shared the button row and sat east of the buttons. Opening the group bar grew that card from 48 to 89, and the line stayed on the button row. Opening the collapsed card put the body under the buttons. A collapsed member was 354 pixels wider than an open member. The open member's east gap was 27. The window was not opened by hand.

## Main window comes back

The shell already kept the window width and height. It now also keeps where the window sits on the desktop, and whether it is maximized. The new keys are ui.frame.x, ui.frame.y, and ui.frame.maximized. They sit beside ui.frame.width and ui.frame.height in the shell client file.

A missing position still centers the window, at the saved size. A position that misses every screen is centered, and the saved size is kept. A maximized window comes back maximized. The stored width and height stay the restored size. A maximized frame writes the maximized flag and leaves that rectangle alone.

A move, a resize, or a change of the maximized state saves after the same 400 ms pause. Exit saves too. An iconified window does not replace the stored rectangle.

A probe pointed the store at a temp file. The live client file was not written. A rectangle at 48, 64 sized 860 by 660 came back there, and that frame was not shown. Maximized, the frame on screen was 1550 by 974, and the file still held 48, 64 and 860 by 660, with maximized true. The window was not opened by hand. The maximized check showed a frame and disposed it.

## Collapse all and expand all

BB1 gains Collapse All and Expand All. They sit as their own group, after a visible divider, to the right of the buttons that were already there. Collapse All folds every turn on the canvas that is showing. Each card keeps the short line of the reply to the right of its buttons. Expand All opens every turn on that canvas. The prompt and the reply show under the buttons, at the saved row count. The choice is saved on each turn. Other canvases stay as they are.

Each new button carries a tip that says what it is and what the click does.

The tip says what the control is and what the click does. A look-and-feel button is not this duty. A button already on screen before the law is not reopened by it.

A layout probe at font 19 in a 1280 by 800 window found Collapse All and Expand All on the footer row. The divider sat between the tool-bar v and Collapse All. The divider was 9 by 22. Collapse All folded the three turns on the open canvas. The other canvas stayed open. Expand All opened those three turns again. The window was not opened by hand. The probe showed a frame and disposed it.

## Delete a turn

Delete one turn from the canvas that is showing. The turns that remain take new labels from 1, in canvas order, with no gap. The label on the card is the stored turn index. New turn numbers the next card from the count of cells, so a gap would reuse a label that is still on screen.

Which control does the delete. What remains when the deleted turn is the last one on the canvas. What a grouped member or the merge sink does. Whether a pipe token in a later prompt is rewritten when the deleted turn was kept. A parent pointer is stored, and import rewrites it. The walker does not read it.

He will assess the vault reading before the next step.

The delete sits on BB1, in the main button set, on the near side of the divider. It deletes the focused turn. With one turn on the canvas the button is disabled. It is enabled when two or more turns are on the canvas and the focused turn is not a grouped member. A grouped member is a turn with a group and a column position above 0. That turn cannot be deleted. Pipe tokens stay as the user wrote them.

The vault reading, for his assessment. The turn is one row in the cell table. That row holds the prompt, the reply, the state, the burner flag, the collapse flag, the target, the group, the status, the parent pointer, and the turn index. A delete removes that row. The canvas row stays. Its cell_order list drops that id. Each remaining turn stores a new turn index from 1 in canvas order. Trace rows for that id are removed with it. No tool writes a trace today. The tables have no foreign key, so the delete names each of those writes. Another canvas is another set of rows and stays. The walker does not read a parent pointer. A survivor that stored the deleted id keeps that string. The merge sink is a different row: column position 0 with the same group. His rule does not disable delete when the sink is the focused turn. That case stays open.

Delete sits on BB1, in the main set, on the near side of the divider, just before that divider. It removes the focused turn. One turn on the canvas leaves the button off. A grouped member leaves it off. There is no confirm dialog.

A click removes that cell row, drops the id from the canvas order, stores turn indexes from 1 on the rows that remain, and removes trace rows for that id. Pipe tokens stay as written. A parent pointer on a survivor stays stored. Another canvas stays.

The merge sink stays. His rule does not turn the button off when the sink is focused. A click on the sink does not remove it. That case stays open.

A layout probe at font 19 in a 1280 by 800 window found Delete between the tool-bar v and the divider. The divider was 9 by 22. One turn left the button off. Three turns left it on. Deleting the middle turn left indexes 1 and 2. The pipe token stayed on the later turn. The parent pointer on the first turn still named the deleted id. Trace rows for that id were gone. The other canvas stayed. A grouped member left the button off. A sequential turn beside that group left it on. A click on the merge sink left the sink in place. The window was not opened by hand. The probe showed a frame and disposed it.

Arming any card of that group, member or merge, removes the whole group and the merge together. A single member stays with its siblings until that set leaves. The sentences above stay the record of the 12:18 build.

## Sapphire selection

The armed card's rectangle was amber, RGB 200, 162, 42. It is now a bright sapphire, RGB 46, 134, 255. Pin green, a dim miss, and the ghost edge stay as they were.

The same probe counted one sapphire rectangle on the armed card and no amber rectangle. The window was not opened by hand.

## Tools toggle past the fold buttons

BB1 keeps Delete on the near side of the first divider, then Collapse All and Expand All. A second divider sits to the right of those two buttons. The tool-bar v sits on the far side of that divider. The bar still starts hidden. A click still opens it and the mark becomes ^. A second click hides it. The choice is still not saved.

A layout probe at font 19 in a 1280 by 800 window found the v at the end of the row. Delete sat just before the first divider. Collapse All and Expand All sat between the two dividers. The window was not opened by hand. The probe showed a frame and disposed it.

## Group snaps onto the merge

When a group is established, the member row was taking the spare height of the canvas. The members sat at the north of that space and the merge cell sat at the south. The member row is now as tall as its cards. The merge cell sits under that row. A sideways bar keeps its own height only when the cards are wider than the row. Join moves the added turn into that row, in canvas order, and the merge stays at the end of the run. Ungroup keeps the turns that remain together the same way.

Selecting the merge cell paints every card in that group sapphire, RGB 46, 134, 255. That is the run point. Run sends each member and does not send the merge. Selecting one member, or a turn outside the group, lights only that card.

A layout probe at font 19 in a 1280 by 800 window found a gap of 0 between the member row and the merge. Joining a later turn put both members in the row and left the other turn outside the block. The gap stayed 0. Arming the merge lit 3 cards. Arming the turn outside the group lit 1 card. Run asked for an AMWP session, which is the send path with no session open. The window was not opened by hand. The probe showed a frame and disposed it.

## Merge reply and the selected send

A merge cell shows only the reply box. The prompt box is hidden. View still opens the stored reply. Collapse still keeps the one-line reply. Import into the merge cell is refused while the prompt box is hidden. Ungroup brings the prompt box back on that cell.

Selecting a turn rebuilds the inspector for that turn. The list is what Run would send for that turn: the host line, the mood line, and the earlier turns that call keeps. A turn below the selection stays out of the list.

When the selected turn is the merge cell, the inspector lists the member prompts joined as one user line, and the merge reply as the assistant line. Turns outside that group stay out of that list. Run still sends each member. The merge is still not sent.

A layout probe at font 19 in a 1280 by 800 window selected the top turn of three. The inspector held that turn's prompt and left out the two turns below it. The merge card had no prompt box and no split. Its member still had a prompt box. View was on the merge card. Selecting the merge listed the member prompt and the merge reply, and left out the turns outside the group. Selecting the turn under the merge left out the turn below that one. The window was not opened by hand. The probe showed a frame and disposed it. View was not opened.

## A group is one turn on the wire

When Run builds the list for a turn, a group that sits above that turn goes out as one turn. The member prompts are one user message, joined by a newline, in canvas order. The assistant message is the merge reply with the JOIN lines removed. A line that is JOIN plus that member's target is the tag. ollama is the tag when the target is empty. The reply text stays. The card still stores the JOIN lines. The merge cell's state still decides whether that reply goes. Included and output-only send it.

Run on the group still sends each member. The merge is still not sent. A member's own call still leaves its siblings and the merge out of that call.

A probe grouped two turns under an earlier turn and added a later turn. The later turn's working set held the earlier turn, then one user message of both prompts, then the merge reply without the JOIN lines, then the later prompt. The replies stored on the member cards were left out. One member's own working set held the earlier turn and that member's prompt. The window was not opened by hand. The probe showed a frame and disposed it.

## Group keeps the column width

A fresh canvas with a two-cell group looked full width. Adding a turn under that group parked the group on the right at half the column. A sequential card is centered in the vertical box. The group block was left-aligned. Those two alignments split the column, and the left-aligned block received the right-hand half. The group block now uses the same center alignment as a sequential card. The member row and the merge still fill that block. A member card keeps its own width inside the row.

A layout probe at font 19 in a 1280 by 800 window grouped two turns and added a later turn. The group block and the later card were both 892 wide and both started at the same x. The merge filled the block. A box measurement of the old alignment put the group at x 631 with width 632 beside a full-width card. The window was not opened by hand. The probe showed a frame and disposed it.

## Member row scrolls

A group snapped to the merge had no sideways bar when a third cell joined the row. The member scroller was publishing the sum of the member cards as its preferred width. That sum became the column width, so the group's own bar stayed off and a canvas bar would slide the merge with the members.

The member scroller no longer publishes that sum. The column takes the canvas width. The merge and a later turn stay that width. A member card keeps its own width. When the cards are wider than the row, the group's sideways bar turns on and keeps its own height, so it does not cover the cards. Two members that fit show no bar. The canvas sideways bar stays off.

A layout probe at font 19 in a 1280 by 800 window used the saved canvas divider. Two members sat in a row 919 wide and the group's bar was off. Joining a third member and adding a later turn brought the canvas vertical bar in, and the column followed that width. The group block and the later card were both 902 wide and both started at the same x. The merge filled the block. Each member stayed 446 wide. The third member started at x 892 in a row 902 wide, so it ran past the row. The group's bar was 17 pixels tall and the member card fit above it. The window was not opened by hand. The probe showed a frame and disposed it.

## Error slide

An error that already has its own words stays on the status line. Those words are `air-gap`, `engine-down`, `too-large`, `burner denied`, `pipe`, `grok key missing`, `unknown provider`, `unknown role`, and `no tool is invocable`. `cell-busy` stays a status-line notice and does not fail the cell.
**semantic-key:** PK-851

## Error slide

The notice stays until Close. A later miss replaces the text and opens it again. The canvas stays usable. It is not an application-modal dialog. Close carries a tip. The notice carries a tip.

The window was not opened by hand.

2026-10-21 23:08. A wire miss from the Grok handshake handler uses this slide. The Grok serial names the handler. A local miss keeps the banner already named for it. This note does not add a second window. No Java in that note.

## Group fail

This row files the rule. It does not build it.

One empty member leaves `CONCAT` waiting, so the merge never writes. That wait is the hang. A hard failure or a soft failure on any member fails the group.

The named banners still paint the status line. They also open the slide in the color named here. The body stays three wrapped rows. The notice stays until Close.

The merge cell stores `FAILED` and its reply stays blank. `CONCAT` does not run. Members that already stored a valid reply are left as they are. When any miss in the group is hard, the notice is red. When every miss is soft, the notice is yellow.

The vault tokens stay null, `COMPLETED`, `FAILED`, and `CANCELLED`. The tree still waits on a failed member. No Java in this row.

This row does not choose the execution-target widget.

The build is its own named build and sits outside the Grok code pause. Any member `FAILED` or `CANCELLED` fails the merge. This sentence does not write the Java.

2026-10-22 08:31. approved the Grok plan. This build includes. Any member `FAILED` or `CANCELLED` fails the merge. A group whose only miss is `CANCELLED` does not open the error slide. This sentence does not rewrite the rows above.

## An owed answer

It does not build it.

The execution target stays the free text field. The locked list of the host's Ollama models plus `grok/<model>` is not the control. Row 8 is closed.

An answer that is still owed is a soft failure. A member has stored a response. Another member still has no answer. The reasonable time is the request cap on the Grok serial. The clock starts when the stored response lands. When the cap passes and the answer is still missing, that member is a soft failure.

The yellow slide says the cell is still waiting and no answer arrived. The reply field stays clear. The cell stores `FAILED`. A valid reply already stored stays stored. Group fails under. The merge stores `FAILED` and `CONCAT` does not run.

A miss that already failed, hard or soft, stands. This wait does not replace it. `cell-busy` is not this wait.

The tree still waits. No Java in this row.

## Burner cloud checkbox

The checkbox lives in the model settings. That modal is the Grok settings dialog on the Grok serial. The burner pin on the card stays the mark that the turn is ephemeral.

The checkbox is specific to that model, and the split is local against cloud. A local model still receives a burner cell. The checkbox is not on that path. A cloud model carries the checkbox. The document key is `burner_cloud`. The factory value is false.

Unchecked refuses a burner hop to that cloud model. The cell stores `FAILED`. The status line shows `burner denied`. The frame is not sent. Checked is the user's allow. The shell reads the box and sends the frame.

The checkbox is not a body field. Air-gap still refuses a cloud target on its own. `notebook.burner-key` stays the server door. This checkbox does not write that key. The tree still refuses every burner cloud hop. No Java in this row.

2026-10-21 22:51. named a Grok exception on the Grok serial. A normal `grok/` cell may POST while air-gap stays `true`. This checkbox still does not write the air-gap word. The tree still stops a `grok/` target on `air-gap`.

## Thinking strip

The thinking text drops with the session. It is not a vault column. The surface is one strip on the Aetheris pane, above the BB1 bar and under the main canvas. Three lines are viewable. The widget is a read-only `JTextArea`. A `v` minimizes the strip and restores it. The `v` carries a tip in the turn it is built.

A call that produces no thinking hides the strip. The strip comes back when thinking occurs again. While a run is producing thinking, the strip shows that run. A group shows the member that is running. The last member that produced thinking stays up until a later call hides the strip or replaces the text.

The Grok card split is the earlier fit. This strip is the pane. Ollama `message.thinking` uses this same strip. No Java in this row. The 23:10 pause still holds.

## Model Config modal

It does not build it. He named the pre-plan. He approved the pre-plan. The stamp names the build.

## Model Config modal

What this row requires:

1. One button sits on the main bar of every readable card. The title is `Model`. It stays `Model`.

2. The click opens a modal for this cell. The title is `Model Config`. The model is selected in the modal. The options for that model are defined in the modal. The card does not hold those controls.

3. The first control in the modal is a pull-down of model families. The rows start with Ollama and Grok. A later model is a new row. Ollama is the default. A new cell, a cleared canvas, and an empty target open on Ollama.

4. Under the pull-down, the modal shows only the options that belong to the selected model. Ollama options do not show when Grok is selected. Grok options do not show when Ollama is selected. The Thinking checkbox on screen is the selected model's checkbox for this cell.

5. A change in the modal writes this cell at once. A combo, a checkbox, and the family pull-down are that change. A number field becomes that change when it loses focus and the text parses. A target change updates the stored target, the cell label, and the group-bar field. A Thinking change updates this cell's thinking value and leaves the target string as it is. The cell does not wait for the modal to close. The cell does not wait for Run. Closing the modal leaves the cell as last written. The dialog's wait for OK, and the restore of the prior target when that first open closes without OK, leave with the `Grok` button.

6. The cell shows the selected model on one label line under the main bar and above the body. The height is one line plus the header's small vertical pad. The ink is the card's ordinary label ink. It is a label. It is not a field, not a vault column, and not the status line. An Ollama cell with no model id reads `ollama`. An Ollama cell with a model id reads that id. A Grok cell reads the model id after `grok/`. A Grok cell with no model id reads `grok`. A model that is not selected does not appear on the cell. The status line still prints `engine:`.

7. Ollama options are the model id, the Thinking checkbox, and the effort. The model id is a field. An empty id stores an empty execution target. The configured Ollama service stays the call. A chosen id stores `ollama/` plus that id. The tree does not read an `ollama/` suffix until the build he names. The Thinking checkbox is on or off for this cell. The factory is off. On sends `think` true for this cell. Off leaves `think` unset. The map stays session RAM keyed by cell id. A second cell keeps its own value. Effort does not replace the checkbox. Effort is the level. It shows when the checkbox is on and that model's `thinking.values` from `POST /api/show` lists strings. A chosen string is what `think` sends. The published `low`, `medium`, and `high` example is the docs example. It is not this host. When the model lists only booleans, the checkbox is the control and the effort rows stay hidden. Turning the box off leaves `think` unset and keeps the chosen level in this cell's session RAM, so the next on sends that level again. The checkbox off does not send `false`. This set does not show the Grok API, `reasoning_effort`, stream, temperature, max tokens, or `burner_cloud`. `num-ctx` 4096 stays the server window. The value is not a new vault column.

8. Grok options are the options the `Grok settings` dialog already writes, plus the Thinking checkbox for this cell. API is `chat` only. Model is the text-model table and writes `grok/` plus the id. For this app, effort is always explicitly defined in the config. An undefined effort can waste tokens. The control lists the legal levels for the selected model and always holds one of them. The value is stored on this cell. Every Grok call for this cell sends `reasoning_effort` with that defined level. The dialog in the tree can leave the effort unset, and the body then leaves the field off. That path is the code this row adjusts. The factory level is `high` where that is the model's published start. Where the model has no published start, the factory level is `high` as well, so the config is defined. Streaming is `false` or `true`. Temperature stays. Max tokens stays. The factory values stay 1 and 128000. The probe still uses 16, and the probe sends this cell's `reasoning_effort`. The `burner_cloud` checkbox stays. The factory value is false. The Thinking checkbox is this cell's thinking on or off. The factory is off. The checkbox does not remove `reasoning_effort`. The effort control stays on the pane when the checkbox is off. The Streaming pulldown stays the stream flag. The Thinking checkbox does not set `stream`. When the pull-down selects Grok and the cell has no document, the modal fills from the nearest earlier cell on that canvas, else the standing default, and writes that cell at once. A copied effort that is not a defined level is written as the factory level before that write. The copied document copies that cell's thinking value with it. With neither, the Grok options show, Thinking is off, Effort is the factory level, and the cell shows Grok with no model id until one is chosen.

9. The `Grok` button leaves the card. The group-bar think checkbox leaves the card. The modal holds the Thinking checkbox. The group bar keeps the free-text field, Group, Join, and Ungroup. The field writes the same string. A keystroke updates the cell label at once.

10. Build turn puts an tip on `Model`. The tip says the button opens the Model Config modal for this cell, the pull-down chooses the model, the modal shows only that model's options, the Thinking checkbox sets thinking on or off for this cell and this model, Ollama is the default, and a change updates this cell at once. The checkbox carries a tip in that same turn. The Ollama tip says on asks this cell to think and off leaves `think` unset. The Grok tip says the checkbox is on or off for this cell, and that the checkbox leaves `reasoning_effort` on the call. The effort control carries a tip in that same turn. The tip says the config always defines the level, and that level is sent as `reasoning_effort` on every Grok call for this cell. An undefined effort can waste tokens. The label carries a tip in that same turn. The tip says the line is the model this turn will call. The label stays the model line.

11. An unreadable card keeps the load-error header. It has no `Model` button and no label. A collapsed card keeps both. The label adds one line. A grouped member grows by the button. The label stays inside the card width. The member row scrolls. The column width stays the canvas width. The canvas sideways bar stays off.

12. The merge shows the same button and the same label. Run on a group still sends each member. Each member sends that member's own thinking value. A Grok member sends that member's `reasoning_effort`. The merge is still not sent. The merge checkbox stays out of the run.

13. No new vault column. The locked list of host models plus `grok/<model>` stays off the card. The pull-down is model families. It is not that list. `select model` and `list models` stay tabled. Choosing Grok does not write the air-gap word. Both air-gap doors stay.

The slide-in. The feature uses the doors that are already there. The named build follows this row. The Grok serial, the plan, and the pre-plan stay the earlier reading. No Java in this row.

14. One modal. `openGrokDialog` becomes the Model Config modal. The title is `Model Config`. There is no second `JDialog`. OK and Cancel leave with the `Grok` button. Close leaves the cell as last written. There is no restore of the prior target. `grokDialogOpen` stays the one flag. The family pull-down is the first control. One pane shows. The Grok pane is the form that dialog already builds, plus the Thinking checkbox. The Ollama pane is the model id, the Thinking checkbox, and the effort. `fillModels` stays the model table. The `Grok` button is not added. The group-bar think checkbox is not added.

15. One stored string. A modal change writes `cell.executionTarget`, the group-bar field, and the label. That write persists the way the dialog's OK persists today. A keystroke in the field updates the cell in memory and the label at once. It does not persist on each character. The next run already reads the cell object. Typing a grok target adopts the document, nearest earlier cell, else the standing default, else the factory, and does not open a second dialog. A stored provider that is neither ollama nor grok stays as written. Opening the modal does not rewrite it. A pull-down selection then writes the chosen family.

16. The label sits in the header, under the main bar, and above the body. It is not a child of `headerTop`. It is not a child of the group bar. The group bar still starts hidden, and the label stays visible while that bar is hidden. A collapsed card keeps the brief on the button row. The label is the line under that row. `groupedMemberWidth` still measures `headerTop`, so the member grows by the `Model` button and does not grow by the label text. The member row grows with the card. The column width stays the canvas width. The member scroller still owns the overflow. The canvas sideways bar stays off. The unreadable card returns before the button and the label. `shownTarget` stays the engine word and the JOIN banner. An empty target still prints `ollama` there. The label uses the short rule in item 6. Do not point JOIN at the short rule.

18. The Grok body always sends the level. `assemble` copies the normalized effort for a grok cell. `AmwpToon.encodeExecute` writes `reasoning_effort` when the value is non-empty. It does not drop the unset token. `GrokLlmService.requestBody` always puts the field. An empty value is `high` before the JSON is built. That last door does not invent a catalog. `probeBody` takes this cell's level and puts it on the Hello body. `startGrokProbe` and `ProbeGate.admit` take that level. The gate stays one Hello for the server process. A later grok cell does not probe again. Its own call sends its own level. The probe still leaves temperature off and still uses `max_completion_tokens` 16. An Ollama frame does not carry `reasoning_effort`.
**semantic-key:** PK-758
**semantic-key:** PK-758
**semantic-key:** PK-758

19. The Ollama id uses the call that already exists. `LlmRouter.complete` reads the id after `ollama/` and passes it into `SpringAiOllamaLlmService` on the same `OllamaChatOptions` the think path already builds. The call is `setModel` on that instance. spring-ai stays 1.1.8. This row does not follow a later branch that removes those setters. An empty target and a bare `ollama` do not set the model. The boolean on `complete` becomes the think string. Empty means off. `true` means the checkbox. Any other value is the level. There is no new parameter beside it. The configured Spring model stays. `ChatShell.setModelName` is not called. The options object is built when a model id is present, including when think is off and `num-ctx` is 0. A configured `num-ctx` still rides. There is no second Ollama chat client. The call stays inside the Roux hold that already wraps `complete`.
**semantic-key:** PK-759
**semantic-key:** PK-759
**semantic-key:** PK-759

20. One session holder. `ollamaThink` widens in place. The value holds this cell's Ollama on or off, the Ollama level, and the Grok on or off. A removed cell still drops the holder. Two families on one cell keep both values. `assemble` does not put Ollama think on `think_stream`. `think_stream` stays the Grok stream flag. The execute frame gains the optional key `think`. The key is absent when Ollama thinking is off. The key is `true` when it is on and no level is chosen. The key is the level string when it is on and a level is chosen. Off does not send `false`. The server reads `think` only when the provider is ollama. Boolean on stays `ThinkOption.ThinkBoolean.ENABLED`. A level of `low`, `medium`, or `high` is `new ThinkOption.ThinkLevel(level)`. Off leaves the option unset. `ThinkBoolean.DISABLED` is not sent. The holder keeps the level when the box is turned off, and the next on sends it again. This key is not a vault column. Client and server ship in the same build. A saved turn has no think flag to migrate.

22. The Grok Thinking checkbox is the holder flag for this cell. It does not set `stream`. It does not remove `reasoning_effort`. Kind `t` paints the strip only when that flag is on. When the flag is off, kind `t` is dropped and the reply still paints. Stream false still returns one reply, so the strip stays hidden. The checkbox does not bound token use. The effort level does. A copied Grok document copies the source cell's Grok flag from the holder. The strip is not given a second widget.

23. The landing check, when he names the build. `mvn -q -DskipTests compile` passes. The window is not opened unless he asks. The live POST is not made. The `Model` button is on the main bar. The label is one line under it. The group bar still starts hidden and still holds the field, Group, Join, and Ungroup. A grouped member grows by the button. The label stays inside the card. The column stays the canvas width. The canvas sideways bar stays off. An unreadable card has neither control. Tips from item 10 are on the controls that are built in that turn.

24. The stored strings are fixed. Selecting Ollama with an empty id writes an empty target. It does not write the word `ollama` and it does not write `ollama/`. Selecting Ollama with an id writes `ollama/` plus the trimmed id. Selecting Grok when the document has a model writes `grok/` plus that id. Selecting Grok with no model writes `grok/` and the label reads `grok`. Run on that target is `no-model` before air-gap, before the key, and before `Hello`. That order stays. A typed `ollama/` or a typed `ollama` normalizes to an empty target when the field loses focus. During the keystrokes the label follows item 6. A provider that is neither ollama nor grok leaves the family pull-down with no row selected and both panes hidden until the user picks a row. Opening the modal does not rewrite that target.

25. Each family keeps its store. Moving to Ollama does not forget the cell's Grok document. Moving to Grok does not forget the Ollama id or the holder. Coming back writes the target from the store that cell already has. `adoptGrok` still returns when the cell already has a document. It does not copy the nearest cell over that document. A modal write updates the field and the label in place. It does not call `rebuildTabs`. The group bar's open state stays. Collapse stays. Temperature writes on focus loss when the text is a finite double. Max tokens writes on focus loss when the text is an int of 1 or more. A bad number leaves the last good value and the status line says `settings`. The Ollama id updates the cell in memory and the label on each change, and it persists on focus loss. The card's burner checkbox stays the ephemeral flag. The pane's burner checkbox stays `burner_cloud`. They are not the same box. A change on the merge writes the merge cell only. Members are not copied. Run still skips the merge. The mood shaper stays its own execute. It does not open this modal and it does not gain a `think` key.

26. The holder fields are `ollamaOn`, `ollamaLevel`, and `grokOn`, keyed by cell id. A missing key means off and no level. The Grok flag is session RAM. A shell restart clears it. Effort, stream, temperature, max tokens, burner, and the model stay in the Grok document. The flag does not gain a yaml key. Kind `t` is dropped in `paintThinkDelta` when `grokOn` is off. The server still sends the delta when stream is true. The effort level is the token bound.

27. 2026-10-22 11:28. The ask is closed. No call is open. Items 1 through 26 are the row. The Grok serial, the Grok plan, and the Grok pre-plan stay the earlier reading. 2026-10-22 11:41. named this row's pre-plan. No Java in this row. He approved the pre-plan. The stamp names the build.

Thinking is on or off for the selected model on this cell. It is per cell. It is not a global option. The tip that says the on value is for this session is the sentence the modal corrects. The map is already keyed by cell id. Keeps the machines that exist. Ollama thinking stays in the session map. Grok reasoning stays on that cell's settings document. No new vault column. The execute frame gains the optional key `think` for the Ollama level. Grok does not use that key. Keeps the connection split. Ollama on sends `think` for this cell. Ollama off leaves `think` unset. Every Grok call sends this cell's `reasoning_effort`. The Thinking checkbox does not remove that field. Neither checkbox sets `stream`. Neither checkbox enters the handler. The execute field stays the provider branch the tree already has. Face keeps one checkbox, titled `Thinking`, in the selected model's options, and the Grok pane keeps Effort beside it. The tip says on or off for this cell and this model. Counts two cells as two values, and two families on one cell as two values. On changes the wire. Ollama off does not send `false`. The config includes an explicit level. The call sends it. The token use is that level. 2026-10-22 11:08. flagged the unset effort. For this app the config always defines effort. An undefined effort can waste tokens. He does not add a dialog, a chat client, or a shell model swap. Lands the cut in that order: the table, the modal, the holder, the Ollama id, the probe level, then the landing check. Face keeps one stored string across the field, the label, and the modal, and keeps `think` as the Ollama side of the frame the server already accepts. Holds the split. `think_stream` stays Grok's stream flag. Ollama off does not send `false`. An empty Grok effort is `high` before the body is built, and the call is not skipped. Two cells stay two holders. 2026-10-22 11:28. asked the floor to close the row. Keeps the show POST on the server that already holds the base URL. The Swing process does not open that socket. There is no new channel and no terminal command. Puts the `show:` branch before the execute slot, so a run in flight stays in flight and a stale reply dies when the id does not match. Face keeps a show result off the cell answer and off the strip. A family switch keeps both stores. Holds the library limit. `ThinkLevel` accepts `low`, `medium`, and `high`. A foreign string is not a row. `grok/` with no id is `no-model` before air-gap. A junk provider does not move the pull-down. The lock shuts the ask. Door is that shut door. ACTION is the box as this cell's door. The Van stays quiet. Code waits. 2026-10-22 11:41. named the pre-plan. Keeps one modal, one table, one holder, and one server POST on the URL the factory already injects. Puts `show:` before `inFlight`. The think key is a string. Face keeps Ollama kind `t` on the strip. Grok kind `t` paints when `grokOn` is on. Holds `ThinkLevel` to `low`, `medium`, and `high`. The Van stays quiet. A formal plan waits. Code waits. 2026-10-22 11:56. approved the pre-plan. The plan is stamped. The build is named.

Handshake. The handler stays where it is. It runs when the stored target is `grok/` and the user presses Run. Opening the modal does not probe. A change in the modal is not a Grok POST and does not enter the handler. The Ollama effort rows ask the server with `POST /api/show` through the `show:` frame in item 21. That ask sends no cell prompt. The reply is not a cell answer. Flipping Thinking does not probe. A cell on Ollama does not enter the handler. Provider grok with an empty model is `no-model` before the key is read and before `Hello`. The probe model is the id on that cell. The body is one user message `Hello`, stream false, `max_completion_tokens` 16, and this cell's `reasoning_effort`. An empty effort is normalized to the factory level before the body is built. The call sends that level. The call is not skipped for effort. The greeting is discarded. One `Hello` for the server process. Valid true matches that key. A later grok Run in that process skips `Hello` and sends the cell prompt with this cell's `reasoning_effort`. A miss stores `FAILED` and does not send the prompt. A `401` clears valid. A server restart clears it. A shell restart does not. Air-gap stays two doors. `burner_cloud` still rides the Grok frame. It does not ride an Ollama frame. A group Run handshakes each member on that member's own target and that member's own thinking value. A member left on Ollama stays out. The merge label does not enter the handler.

The button, the group-bar field, and the label are one stored string. The handler follows that string. The Thinking checkbox is a second value on this cell. Ollama keeps it in the session map keyed by cell id. Grok keeps the effort on that cell's settings document, and the Grok body sends that level. The target string does not carry the checkbox or the effort.

The Grok serial stays the operative reading of the chat call, the headers, and the air-gap. This row does not edit the serial. 2026-10-22 11:28. No call is open. No Java in this row. He approved the pre-plan. The stamp names the build.

## Ollama models from the host

The models on the configured host are in the Model pull-down. This sentence files it closed.

1. The Ollama Model control is an editable pull-down. Opening the Ollama pane, and switching the family to Ollama, sends `tags:` on `CHANNEL_A` `TYPE_DATA`. `handle` sees that first line before the execute slot, before `inFlight`, and before Roux. The server GETs `{base}/api/tags`. The base is `spring.ai.ollama.base-url`, trailing slash trimmed. Connect is 10 seconds. Read is 10 seconds. The reply is `tags_result:` and one `model:` line per tag `name`. A miss is `tags_result:` and no model lines. It is not `TYPE_ERROR` and it is not `CHANNEL_STREAM`. The pull-down lists those names. A typed id that is not in the list stays. No AMWP session means the client does not send, and the list stays empty. The Swing process does not open a socket and does not read the URL.

2. An empty Model field still sends `show:`. The server uses `spring.ai.ollama.chat.options.model` when the id is empty. A blank property does not call the host. The reply id fills the pull-down when the field is still empty. That fill does not by itself persist a target. Choosing a row, or leaving a non-empty field, writes `ollama/` plus the trimmed id. Clearing the field and leaving it writes an empty target and does not ask again.

3. `thinking.values` keeps every textual level except `true` and `false`. Booleans stay off the effort rows. A chosen level is `new ThinkOption.ThinkLevel(level)` for that exact string. `true` stays the checkbox, `ThinkOption.ThinkBoolean.ENABLED`. Empty and `false` leave think unset. `ThinkBoolean.DISABLED` is not sent. Effort still shows only when Thinking is on and the reply listed a level. A boolean-only model keeps the checkbox and leaves Effort hidden.

4. The Grok serial stays unread. No new channel. No new type. No new vault column. The window was not opened. The live cell POST was not made.

Keeps the server that already holds the base URL. Puts `tags:` beside `show:`, before `inFlight`. Face keeps one editable pull-down. Holds the level string. The host's own word is the level. The row is closed.

## Grok model pull-down

`grok-4.3` stays the factory id. The other text models are rows in that same pull-down. This sentence builds it.

## Grok model pull-down

1. The Grok Model control lists every id in `MODELS`, in that order. `grok-4.3` is a row. It is not forced onto an empty cell. A stored id outside the table stays as an extra first row. An empty model stays unselected until the user picks a row.

2. The closed control is wide enough for `grok-4.20-0309-non-reasoning`. The popup shows every row. The control takes the field ink. The tip says the list is the text-model table, `grok-4.3` is the factory id, and a chosen row writes `grok/` plus that id.

3. A pick writes the cell at once, normalizes effort for that id, and updates the label. Image, video, and voice ids stay off the list. There is no call that lists models from the network. `select model` and `list models` stay tabled. The Grok serial stays the earlier reading. Opening the pane still does not write `grok-4.3`.

2026-10-22 21:50. names the console command `select-model`. The two-word forms in the sentence above stay tabled.

4. `mvn -q -DskipTests compile` passes. The window is not opened. The live POST is not made.

Keeps `MODELS` and does not add a second catalog. Face makes the blank box a pull-down of that table, wide enough to read as a model control, in the field ink. Does not add a models GET. A pick still writes through the listener that already normalizes effort. Holds the empty cell. No row is selected until the user picks, so opening the pane does not store `grok-4.3`. The row is the build.

## Reasoning effort the host will take

He set the model to `grok-4.3`, then tried `grok-4.20-0309-reasoning` and `grok-4.20-0309-non-reasoning`. The host answered `Grok HTTP 400 {"code":"invalid-argument","error":"Model grok-4.20-0309-reasoning does not support parameter reasoningEffort."}`.

Remains the operative reading that a defined level is `high` when the field is sent, and that `omit` is not a level. The Grok serial stays unread.

## Reasoning effort the host will take

2. The Grok pane gains one checkbox, `Send effort`, beside the effort pull-down. On sends this cell's level as `reasoning_effort` on the cell call and on `Hello`. Off leaves the field off both bodies. The level stays stored. The pull-down stays on screen and is disabled while the box is off. Picking a model sets the box from the catalog for that id. The tip says on sends the level, off leaves the field off the wire, and a host 400 that names this parameter turns the box off and retries the call once.

3. The execute frame carries `send_reasoning_effort: false` when the box is off. Absent means on, so an older frame still sends the level. The service puts `reasoning_effort` only when the flag is on. Empty with the flag on is still `high`.

4. Detection. A Grok HTTP 400 whose text contains `does not support parameter` and `reasoningEffort` or `reasoning_effort` is this refusal. `Hello` retries once with the field off. The cell call retries once with the field off. The second miss is the error the cell already shows. A successful retry sends `effort_off:` on `CHANNEL_A` `TYPE_DATA` for that cell. The client stores `send_reasoning_effort` false and, when the modal is open on that cell, clears the box. One retry. No loop.

5. `grok-4.3` stays the factory id and still sends `high` until a refusal or the box says otherwise. Image, video, and voice stay off the model list. No new channel. No new type. No new vault column. No new package. `mvn -q -DskipTests compile` passes. The window is not opened.

Keeps the level string and adds one boolean. The word `omit` stays illegal. Face puts `Send effort` on the Grok pane. A pick follows the catalog. Retries one refused Hello and one refused cell call, then tells the client with `effort_off:`. Holds `grok-4.3` on, and the two `grok-4.20-0309` ids off, until the host or the box says otherwise. The row is the build.

## A new turn keeps the prior model

When a new turn is created, after a response from the API or by the New turn button, that turn takes the model config of the prior turn. A continued Grok chat stays on Grok until he switches to Ollama or sets up a merge.

The Grok serial's sentence that a new turn does not copy settings stays the earlier reading.

1. The prior turn is the last cell on this canvas before the new card. `newTurn` is the path for the New turn button and for the card born after a response on the last cell. Import, Pull, Clear turns, and the merge sink do not use this copy.

2. The new card stores the prior card's execution target. An empty target stays empty. The prompt stays empty. The new card stays Included. It does not join the prior card's group.

3. When the prior card has a Grok document, that document is copied onto the new cell id. The copy includes the model, the level, `send_reasoning_effort`, stream, burner, temperature, and max tokens. `alsoDefault` stays false. An empty model still does not become standing. When the prior card has a session think holder, the new cell gets the same `ollamaOn`, `ollamaLevel`, `ollamaId`, and `grokOn`. No new vault column.

4. A canvas with no cell yet stays the factory empty turn. The New turn tip says the new turn keeps the prior turn's model.

5. `mvn -q -DskipTests compile` passes. The window is not opened. The live POST is not made.

Copies the target string and the Grok document that already exist. Face keeps the prompt empty and the card Included. Does not add a channel. Holds import, Pull, Clear, and the merge sink off this copy. The row is the build.

## A finished turn selects the new card

When a turn completes and a new turn card is appended to the canvas, the current selected focus moves to that new cell card.

Selected focus is `focusedCellId`. It paints the sapphire edge. The inspector, Run, and Delete follow it. A click writes it in `armFocus`. The completion path already scrolls to the new card and places the caret in its prompt. It leaves `focusedCellId` on the card that just answered.

1. The path is a response that completes on the last cell of a canvas and appends a card through `newTurn(true)`. The New turn button calls `newTurn(false)` and leaves the selected card where he set it. A completion on an earlier cell still focuses that cell's reply. Import, Pull, Clear turns, and the merge sink stay on their own paths.

2. Before the canvas rebuilds, the new cell id is `focusedCellId` and `pendingFocusId`. The sapphire edge is on that card. The inspector lists that card. The status line names that card's engine. The caret lands in that card's prompt. The canvas scrolls to the end. The Join source stays the last card he clicked.

3. The new card still keeps the prior turn's model. The prompt stays empty. The card stays Included and ungrouped.

Writes `focusedCellId` before the rebuild so the sapphire edge, the inspector, and Run follow that card. Face keeps the caret in the new prompt and the scroll at the end. Leaves the button, import, Pull, Clear, and the merge sink on their own paths. The row is the build.

## A new turn ends at the bottom of the canvas

When a new turn is created, and the canvas is scrolled down to the bottom, sometimes it is not.

The canvas is one column in one scroll pane. A new card is born in `newTurn`. The button passes false. A finished last cell passes true. Three moves leave the viewport short of the end.

The button writes `pendingFocusId` only when that id is already null. A scroll does not clear the id. The next button still aims at the older card, and `scrollRectToVisible` on a card taller than the viewport prefers the top of that card.

The land path sets the viewport to the end and then focuses the prompt. The caret scroll pulls a card that is taller than the viewport back off the end. When the card fits, the prompt is already visible and the miss stays hidden.

A group member row calls `revalidate` from `pinMemberRow` after the first measure. That layout runs after the end position is stored. The column grows. A grouped canvas can finish short of the end. A member row scrolls sideways only. The canvas pane is the one that moves.

1. Both new-turn entries set `pendingFocusId` to the new cell and `pendingLandOnInput` true before `rebuildTabs`. `focusedCellId` moves only on `newTurn(true)`. Import, Pull, Clear, a completion on an earlier cell, and the merge sink stay on their own scroll paths.

2. The land pass focuses the new card's prompt, then sets the canvas viewport to the end. Laid-out height under 1 uses the preferred height. The canvas scroller skips a `VERTICAL_SCROLLBAR_NEVER` ancestor. One following EDT pass validates and pins the end again, and does not focus. A group row that grows after the first measure still ends at the bottom. The Join source stays the last card he clicked.

3. The new card still keeps the prior turn's model. The prompt stays empty. The card stays Included and ungrouped. The sapphire edge still moves only on the append that follows a finished last cell.

Aims `pendingFocusId` at the new card and sets the land flag, and leaves the sapphire write on `newTurn(true)`. Face focuses the prompt, then pins the canvas to the end, then pins it once more after layout. Skips the member-row scroller and lets `pinMemberRow`'s later layout finish before the second pin. The row is the build.

## Pool child starts without an updater flag

He ran Pool and the banner read `pool down exit chunk 0 retry used stderr 124`.

The installed `grok agent stdio` rejects `--no-auto-update` and exits 2. The 124 bytes on stderr are `error: unexpected argument '--no-auto-update' found` plus the usage line. The help on that command lists `--debug`, `--debug-file`, `--leader-socket`, and `--help`. `grok update` is its own command. The child never reaches `initialize`. The one retry dies the same way. `chunk 0` means no chat text was forwarded. The word `exit` on the banner is the death place. The number 124 is the stderr byte count.

1. The spawn is `grok agent stdio`. Windows stays `cmd.exe /c grok agent stdio`, so the `grok.cmd` shim still starts through the command host. No `--no-auto-update`. No `--always-approve`. No `--model`. No MCP list. `XAI_API_KEY` still stays off the child. `USERPROFILE` and `HOME` stay. The working directory stays `user.home` plus `.casserole/pool-cwd`.

2. The pool plan, the pool pre-plan, and draft.002 keep the old flag sentence. A dated sentence on each names this row as the operative spawn. Draft.001 stays untracked and unedited.

3. `mvn -q -DskipTests compile` passes. The window is not opened. The live POST is not made. A pool turn is not sent.

Takes the rejected flag off both spawn lines. Face leaves the Windows command host in place. Holds the key strip, the directory, and the one retry. Holds the old flag sentences and adds the dated line. The row is the build.

## A pool turn keeps chatting when the agent reaches for a tool

He ran a second Pool turn and the banner read `pool tool refused session/update tool_call notes: 25`.

The first turn landed in message text. The second turn sent a `session/update` whose kind is `tool_call`. That notice has no id. The handler failed the cell before a later message chunk could land. `notes: 25` is the other updates on that same prompt.

1. `tool_call` and `tool_call_update` are noise. They are counted, not painted, not stored, and not a failure. Message chunks still paint on the card. Thought chunks still paint on the footer strip when Pool Thinking is on. Text inside a tool notice stays off both surfaces.

2. A request whose method contains `permission` is answered on that same id. The reply selects the option whose kind is `reject_once`. If that kind is absent, it selects `reject_always`. If neither reject kind is present, the outcome is `cancelled`. The tool title, the command, and the input stay off the log. `session/cancel` is not sent. The cell does not fail. The child stays up.

3. Any other request from the child stays `pool payload`, then `session/cancel`. `--always-approve`, `yoloMode`, and `autoMode` stay off. The spawn stays `grok agent stdio`. The prompt still ends only on the `session/prompt` response. Text that arrived is the answer. A turn that ends with no message chunk stores an empty reply and does not open the slide. `pool tool refused` remains the reason when the deny cannot be written.

4. A dated sentence on each names this row. Draft.001 stays untracked and unedited.

5. `mvn -q -DskipTests compile` passes. The window is not opened. The live POST is not made. A pool turn is not sent.

Face keeps message text and thinking text as the only paint. Holds the prompt latch and the child. Holds `--always-approve` off and keeps every other child request on `pool payload`. The row is the build.

## Duplicate group

Groups with a merge are working.

A selected group is the group of the armed card. The whole group lights when the merge is armed, the same way it does today. A member card names that same group. Right-click opens the card menu. Duplicate group adds a replica after that group. The new prompts and replies are empty. Shift-Ctrl-G does the same. The letter is the G key, with or without caps lock. A card with no group, or no armed card, opens the error slide in yellow with the words `no group selected`.

1. The card menu gains Duplicate group. The tip says the replica is empty, the shape stays, and Shift-Ctrl-G does the same. Import stays on the prompt side. A right-click on the reply still opens the menu so Duplicate group is there. Import on the reply stays refused.

2. The replica follows the selected group. It has the same members in the same order, the same merge when the source has one, the same execution targets, the same I E P O states, and the same burner marks. Prompts and replies are empty. Run state is empty. The cards start open. A new group id is minted. The parent pointer stays empty. Turn numbers follow the new order. The armed card stays the one he selected. A Grok entry and a thinking holder are copied onto the matching new cell.

3. Shift-Ctrl-G sits on the same map as Ctrl-Enter, so it fires while a prompt has the caret.

4. No armed card, or an armed card with no group, calls the error slide with `no group selected` and the yellow skin. A yellow notice clears the hard latch so this miss paints yellow. A hard notice still sets the latch.

5. Group, Join, Ungroup, Delete, and the merge sink stay. No new package, public type, vault column, or.

6. `mvn -q -DskipTests compile` passes. The window is not opened. The live POST is not made.

Face puts Duplicate group on the card menu and Shift-Ctrl-G on the same map as Ctrl-Enter. Parks the replica after the source group and renumbers the turns. Holds the yellow slide for a card that is not in a group. The row is the build.

## Parallel lanes and a find bar on View

A concat join group sent a pool child and an Ollama cell. The child answer arrived after the Ollama request. He wants those two on the wire together. He also wants a find bar on the View window for a large reply and for JOIN lines in a merge.

The client already writes each member frame and returns. The server holds that user's Roux region across the provider call. The pool prompt blocks inside that hold, so an Ollama complete that took the lock first keeps the child waiting. `LLM_EXEC` runs the execute. `POOL_IO` reads the child. The hold is what lines them up.

## Parallel lanes and a find bar on View

2. A dated sentence on each names this yield. The probe gate does not assume the region lock is held. Hello still releases the probe lock before the POST. The region lock is not held across that wait.

3. The View window gains a find bar. Ctrl-F opens it. When the bar is open and the field has text, Ctrl-F moves to the next match and wraps to the start. When the field is empty, Ctrl-F focuses the field. Esc hides the bar, clears the highlights, and returns focus to the reply. Esc leaves the window open. Each keystroke highlights every match in that reply. The match is case-insensitive. An empty field clears the highlights. No match shows the word none on the count. The error slide stays out of this search. Every match is marked. The current match is the selection and scrolls into view. The `^` button moves to the previous match and wraps to the end. The `v` button moves to the next match and wraps to the start. Each of those controls carries a tip. The search reads the reply already in the window, including the JOIN lines in a merge. The canvas search stays the canvas search. The field is not filled from a selection. At most 2000 matches are stored and painted. Past that the count says there are more, and the buttons walk the stored set. The work stays on the Swing thread. No new package, public type, vault column, or.

4. `mvn -q -DskipTests compile` passes. The window is not opened. The live POST is not made. A pool turn is not sent.

Face puts the find bar on the View window, with Ctrl-F, Esc, and the v and ^ buttons. Keeps the semaphore of 3, the child gate, and the pipe chain. Holds the Grok plan's old hold sentences and adds the dated yield. The row is the build.

## Pool session tail

A pool Run pastes the whole working set into a headless session that already keeps its own chat, so Heavy reads the column twice. He wants the first request of a session to carry that working set, and a later request to carry only the tail, until the next app start. A full paste on the runs that need it is expected and accepted. Persisted sessions stay a later use case.

The desktop mints one boot id with the notebook and sends it on each pool execute. The wire still carries the full working set. Roux still takes the full list. The server decides what the child receives.

1. One session id stays on that SSH user's child while the hold is valid. The hold is valid when the boot id matches, the process is alive, and the next working set extends the text that session already holds. The first prompt of the hold calls `session/new` and sends the working set. A later prompt sends the tail, which is the working set with that held prefix removed. After a clean success the held prefix becomes the working set just sent, plus the stored reply in the same assistant role-line shape, when the reply is non-empty. Thought text stays out of the prefix.

2. A new session and the whole working set are used when the boot id changes, the boot field is empty, the child is new, the process was reaped or respawned, the previous prompt failed or was cancelled, the working set does not extend the hold, or the tail would be empty. That covers a re-run of the same card, an edit above the tail, a card higher in the column, another canvas, a same-group sibling, and a later card whose context includes a merge this session never saw. A downward jump across several unchanged cards stays a tail. `session/load` stays out. Abandoned session folders stay on disk. Tool refusals may remain inside a held session for its life.

3. A death before any forwarded chunk still retries once, on a new session, with the whole working set. A death after a chunk, a parse, a payload, a tool refusal, and an auth failure still do not retry, and they drop the hold. The five pool reasons stay. The child gate stays. The semaphore of 3 stays. The fifteen-minute idle reap stays. `num-ctx`, air-gap, and the Grok URL stay. The frame fit stays on the full working set.

4. A dated sentence on each names this tail.

Face sends the boot id from the notebook. Slices the tail on the server and leaves the full working set on the wire. Holds the pool plan's once-per-prompt sentence and adds the dated tail. The row is the build.

## Copy the reply

A button on every cell button bar that has an output window copies that cell's whole reply to the operating-system clipboard. The button is a contemporary clipboard copy icon. The View window gets the same icon.

The button sits on the main bar of every readable card, immediately after View. A collapsed readable card keeps it. The stored reply is the output when the window is folded. The group bar stays the target, Group, Join, and Ungroup. An unreadable load-error card stays bare.

1. The control is an icon-only button. The glyph is two overlapping rounded rectangles, painted in the button foreground and dimmed when the button is disabled. The accessible name is Copy. The button is enabled when the copy source has non-whitespace text, and disabled when that source is empty, so a click leaves the clipboard as it is. Construction uses the same gate as View. The first pool chunk that leaves non-whitespace text in the mounted assistant area enables the button.

2. An open card copies the assistant area, including pool chunks painted before the reply is stored. A collapsed card copies the stored reply. The prompt stays on the card. The thought strip stays on the footer. JOIN lines stay in the reply. A selection stays a selection. The copy is the whole document. A folded card during a pool stream copies the last stored reply. The brief keeps the stream for the eye. The copy leaves the brief alone until the reply is stored.

3. The View window gains the same icon on the south bar, before Close. The click copies the snapshot that window opened. The find field and its highlights stay. The dialog title stays as it is. The window is built only when the reply has text, so the View copy button is enabled while the window is up.

4. The clipboard write uses the system clipboard on the Swing thread. A blank body leaves the clipboard as it is. A busy clipboard sets the status line to clipboard busy. The error slide stays closed. The reply text stays off the status line. A newline that is not already a Windows pair is written as a Windows pair, so a paste keeps the breaks. The paste-in normalizer stays as it is. No new package, public type, vault column,, channel, or type.

Face paints the two-rectangle mark and the View icon before Close. Writes the clipboard and leaves a busy clipboard on the status line. Holds the empty click so the clipboard stays. The row is the build.

## Delete a group

Duplicate group is in the tree. He wants the footer Delete to read the armed card. A normal card deletes that cell. A group cell set deletes the whole group and the merge. Pities the solution. Lands the plane.

The control stays the Delete button on BB1. A normal card is an armed cell whose group id is empty, and the click removes that one row. A group cell set is the group of the armed card. A member names that group. The merge names that group. The click removes every cell on this canvas with that group id, the members and the merge, in one vault transaction.

1. The button is enabled when the removal set is non-empty and smaller than the canvas. One cell on the canvas leaves it off. A group that is the entire canvas leaves it off. Clear turns stays the wipe that leaves one empty cell. There is no confirm dialog.

2. The row leaves, the order drops that id, the survivors store turn indexes from 1, and trace rows for that id leave. Pipe tokens stay as written. A parent pointer on a survivor stays stored. Another canvas stays.

3. A group delete uses that same reading for every id in the set, in one transaction. `deleteTurn` delegates to that write. A late save skips a buried id, because an update that matches zero rows inserts the cell again. A failed delete lifts the burial. A successful delete keeps it for the session.

4. A RUNNING member of the set is marked STOPPED and sent the cancel frame Stop already sends. A chain that names a removed id is cleared. The Grok holder and the thinking holder for each removed id are dropped. Focus moves to the cell that followed the first removed index, or to the last remaining cell when the set sat at the end.

5. The Delete tip is rewritten in this same turn. It names the normal card, the group, the renumber, the empty-canvas gate, and the pipe tokens. Group, Join, Ungroup, Duplicate group, and Shift-Ctrl-G stay. The copy icon stays. The pool hold stays on the server. No new package, public type, vault column,, channel, or type.

6. `mvn -q -DskipTests compile` passes. The window is not opened. The live POST is not made. A pool turn is not sent.

Face rewrites the Delete tip in the same turn. Writes one vault transaction for the set and parks focus on the survivor. Holds the empty-canvas gate and the pipe text. The row is the build.

## Console model

He wants the model chosen from the VT100 console, inside the command set that already runs there. `select-model` at `cmd>` opens the Model Config modal. A right-click on the console offers Select model and opens that same modal. The modal was built once so a later context could use it.

The command name is `select-model`. It is registered with the other console commands. `help` lists it. The two-word forms `select model` and `list models` stay tabled. The menu label is Select model. It sits on the console menu that already holds Import.

1. The modal is the Model Config modal already on the cell. A console open uses that dialog. The family control stays on the dialog and is held on Ollama. Grok and Pool stay the cell lanes. A console open does not write a cell, a Grok settings row, or a pool flag. Thinking and effort on the Ollama pane write this console.

2. `select-model` runs on the server. It writes one private OSC on the VT100 stream, then the line `Model Config`. The Aetheris client removes that OSC before the terminal paints it and opens the modal with the shell model and the shell think string. A raw SSH session shows the line. It has no Swing modal. The menu item, when the session is not muxed, leaves the modal closed and the status line says the raw console has no modal.

3. A menu click on a muxed session asks the server for the shell model on the existing Scope A data frame. The first line is `shell_model:`. The reply is `shell_model_result:`. A change in the modal sends `shell_model_set:` on that same frame. Both are recognized before a cell execute, so they do not take an execute slot and they do not mark a cell busy. There is no new channel and no new AMWP type. The cell id on these frames is nil.

4. The shell stores the model name it already stored, and a think string beside it. The next console chat turn sends `ollama/` plus that model name, and that think string. An empty model name keeps the configured model. A turn already inside the call keeps the name it started with. `swap` and `ollama load` stay the weight movers. This click does not POST to Ollama and does not unload a model. The status key `model` stays. After a set, the console prints `model` and the id.

## Console model

5. One modal flag. A second open while the dialog is up does nothing. The menu tip is written in this same turn. It names the console, the Ollama id, Thinking, and that Grok and Pool stay on the cell. The new public type this build names is `SelectModelCommand`. The new methods this build names are `CommandContext.getConsoleThink`, `AmwpClientListener.onConsoleModel`, and `ChatShell.applyConsoleModel`. No new package, vault column,, channel, or AMWP type.
**semantic-key:** PK-760
**semantic-key:** PK-760
**semantic-key:** PK-760
**semantic-key:** PK-760

Face adds Select model beside Import. Sends the shell model on the data frame that already carries `show:` and `tags:`, and he puts that model on the next console turn. Holds the raw session and the nil cell id. The row is the build.

## Product tab

He wants the close to remember the product tab. SSH comes back on SSH. Aetheris comes back on Aetheris and connects. Pities the solution. Lands the plane.

The two product tabs are SSH and Aetheris. The word is stored with the client window, in the same file that already stores the frame. The key is `ui.product.tab`. The words are `ssh` and `aetheris`. A missing word and any other word read as `ssh`.

1. A click on SSH stores `ssh`. A click on Aetheris stores `aetheris`. Exit confirmed stores the tab that is selected, and it stores that word before the sessions are disposed. Cancel leaves the stored word. A selection the program makes, including the bounce to SSH when the mux drops, does not store.

2. The next open with `ssh` shows the SSH tab and does not connect. The next open with `aetheris` connects the startup pane with the host, port, user, password, and mode already on the connection panel, then selects Aetheris once that tab is enabled. The Aetheris tab stays disabled until the focused session is muxed.
**semantic-key:** PK-852

3. A connect that does not enable the tab leaves SSH showing. The stored word stays `aetheris` until a click or a confirmed exit on SSH. A click on SSH while the restore is waiting cancels that restore. An empty password uses the failure dialog the Connect button already uses. One pane starts. Canvas tabs stay as they are.

## Product tab

5. `mvn -q -DskipTests compile` passes. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

Face leaves the exit question. Stores the word beside the frame and selects Aetheris after the tab enables. Holds the empty password and the raw mode. The row is the build.

## Console family

The model modal opened from the VT100 console offers only Ollama. He wants the pool and the Grok API models on that modal too. The old console path is the Ollama process, and that path can go. Where it fights the cell method, it leaves the VT100 console. The cell method is the common method for the console and for Aetheris cells. The modal close button does not close, and it repeats the model line on the console.

The family control on a console open lists Ollama, Grok, and Pool, and the control is enabled. A console open writes no cell and no Grok settings file. The nil cell id stays the frame id. The shell session holds the choice.

1. `shell_model:` and `shell_model_set:` stay the frames. A set and its result carry `provider:`, `model:`, `ollama_model:`, `think:`, `grok_model:`, `grok_think:`, `effort:`, `send_effort:`, `stream:`, `temperature:`, `max_tokens:`, `burner:`, and `pool_think:`. A missing provider reads as `ollama`. There is no new channel and no new AMWP type.

## Console family

3. `swap <from> <to>` no longer spawns `ollama` and no longer POSTs `/api/generate`. When the family is `ollama`, `to` becomes the shell model name. When the family is `grok` or `pool`, the command writes the family and leaves the target. `ollama` and `ollama stop` stay the explicit binary commands. `select model` and `list models` stay tabled.

4. The modal close marks the dialog quiet before dispose, so dispose does not send `shell_model_set:`. A result after that close does not open the modal. `select-model` and the menu clear that mark and ask `shell_model:` before the dialog opens.

5. The new methods this build names are `AmwpFacade.completeTurn`, `AmwpFacade.cancelPool`, `ChatShell.applyConsoleTarget`, `ChatShell.consoleModelBody`, `ChatShell.consoleTargetLabel`, and `CommandContext.getConsoleProvider`. The `ChatShell` constructor gains `AmwpFacade`. No new package, public type, vault column,, channel, or AMWP type.
**semantic-key:** PK-761
**semantic-key:** PK-761

6. `mvn -q -DskipTests compile` passes. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

Face rewrites the menu tip for the three families. Sends the console turn through `completeTurn` and opens the modal from the result. Holds the nil cell, the one shell pool boot, and the semaphore on the cell execute. The row is the build.

## Console status

The status command in cmd mode reports the selected model from the old Ollama name. He wants that command refactored for the VT100 console so it reports the model the new control stores, and the metadata that belongs to that model.

`status` keeps the session lines. The model line becomes the selected target. A family line names `ollama`, `grok`, or `pool`. The lines under those two are the options stored for that family. A Grok selection does not print the Ollama id. A Pool selection prints `pool`.

1. `status` prints `family` and `model` from the console session. `model` is the Ollama id, `grok/` plus the Grok id, or `pool`. `getModelName` stays the Ollama id. `ollama load`, `ollama stop`, and the `select-model` OSC keep using that id.

## Console status

3. Family `grok` prints `think`, `effort`, `send-effort`, `stream`, and `burner`. An empty think prints `off`. An empty effort prints blank. `send-effort`, `stream`, and `burner` print `true` or `false`. `temperature` prints only when a temperature is stored. `max-tokens` prints only when a cap is stored. The console turn still does not copy `think` onto the execute body. `burner` stays stored and does not open a hop.

4. Family `pool` prints `think` as `true` when the stored word is `true`, and `off` otherwise.

5. The other status lines stay: chat, mode, conversation, region, the SSH user, the message counts, and roux. There is no new channel and no new AMWP type.

6. The new method this build names is `CommandContext.writeModelStatus`. `ChatShell` implements it. No new package, public type, vault column,, channel, or AMWP type.
**semantic-key:** PK-762
**semantic-key:** PK-762

7. `mvn -q -DskipTests compile` passes. The window is not opened. The live POST is not made. A pool turn is not sent. A live SSH connect is not made.

Face leaves the other status lines. Prints the family, the target label, and that family's options. Holds an empty effort as a blank and keeps pool think on only when the word is `true`. The row is the build.

## Console family word

The model modal works from the Casserole VT100. A standard VT100 console has no modal. He wants `select-model` in cmd mode to take one argument, `grok`, `ollama`, or `pool`, and switch that family only, using the options last stored for it. A bare `select-model` still opens the modal.

The shortcut is the family word. It does not take a model id, a think word, or any other option. The stored options for that family stay. The command writes the model line. Any other argument is refused and does not open the modal.

1. A bare `select-model` keeps the OSC and the `Model Config` line. The Casserole client still opens the modal.

2. `select-model grok`, `select-model ollama`, and `select-model pool` set the console family to that word. The Ollama id, the Grok id, think, effort, send-effort, stream, temperature, max tokens, burner, and pool think stay as stored. The command writes `model` and the target label. It does not send the OSC.

3. Any other argument, including a second word, is refused with a usage line. The family stays. The modal stays closed.

## Console family word

4. The new method this build names is `CommandContext.selectConsoleFamily`. `ChatShell` implements it. No new package, public type, vault column,, channel, or AMWP type.
**semantic-key:** PK-763
**semantic-key:** PK-763

Face leaves the modal path. Switches the family and writes the model line. Holds the three words and the nil cell. The row is the build.

## Console word step

While a console line is still being edited, Left and Right move one character. He wants Ctrl+Left and Ctrl+Right to move by words, the way a bash line does.

The keys are the xterm sequences the Casserole terminal already sends, `ESC [ 1; 5 D` and `ESC [ 1; 5 C`. The shorter `ESC [ 5 D` and `ESC [ 5 C` do the same step. Forward lands at the end of the next word. Backward lands at the start of this word, or the previous word when the cursor is already on a start. Plain Left and Right stay one character. Up and Down stay history. A sealed paste ignores the keys.
**semantic-key:** PK-853

## Console word step

2. A word character is a letter or a digit. Space, hyphen, and underscore are boundaries. The step matches bash readline `backward-word` and `forward-word`.

3. Plain Left, Right, Home, End, Delete, Up, and Down stay as they are. A sealed paste and an open paste ignore the word keys.

4. No new public method, package, public type, vault column,, channel, or AMWP type.

Face leaves the terminal key map, which already sends the two sequences. Moves the cursor and redraws the line. Holds the bash word rule. The row is the build.

## Aetheris waits for Connect

A close on Aetheris still remembers that tab. On the next open the client logs in the last user by itself. He wants the client to wait until he picks the user and clicks Connect, and only then bring Aetheris into focus.

The saved word stays `aetheris`. The open does not call `connectRequested`. The connection form stays as it is. After that Connect reaches the session, the Aetheris tab is selected. A click on the SSH tab before Connect clears the wait and stores `ssh`. A close while the wait is still on keeps the stored word. The Aetheris tab stays dark until the mux is up.

## Aetheris waits for Connect

2. `sessionConnected` on the focused session still selects Aetheris when the flag is set and the tab is enabled. The select does not write the word.

3. `confirmAndExit` on Yes writes the visible tab when the wait is already clear. While the wait is still on, it leaves the stored word. Disconnect during exit still does not replace `aetheris` with `ssh`.

4. No new public method, package, public type, vault column,, channel, or AMWP type. `getProductTab` and `setProductTab` stay the methods.

Face leaves the connection form. Drops the open connect and selects Aetheris after the user's Connect. Holds the SSH-tab cancel. The row is the build.

## Aetheris land scroll

After Connect, when the saved tab brings Aetheris into focus, the canvas opens at the top. He wants that first open to scroll to the bottom so the newest turn is in view, the same way a new turn lands.

The scroll is the land that already follows a new turn. The caret goes in the newest prompt. The viewport sits on the last pixel of the column, then once more after layout. A canvas that is still loading waits for that load. A canvas already on screen scrolls at once. A manual visit to the tab does not take this path. SSH still opens on SSH.

## Aetheris land scroll

1. `SshClientApp.maybeRestoreAetherisTab` calls `AetherisNotebookPanel.landOnNewest` after it selects Aetheris.
**semantic-key:** PK-764
**semantic-key:** PK-764

2. `landOnNewest` uses the new-turn land. The newest cell on the showing canvas is the target. The viewport goes to the end of the column. The prompt of that cell takes the caret.

3. When the vault bind is still in flight, the land waits for that bind's canvas paint. When the vault is already showing, the land runs then.

4. No new package, public type, vault column,, channel, or AMWP type. The new public method is `AetherisNotebookPanel.landOnNewest`.
**semantic-key:** PK-854

Face leaves the canvas paint. Calls the land after the tab select. Holds the bind that arrives after the select. The row is the build.

## Mood layer online

The mood layer comes online. A mood miss reports on the error slide and the mood layer goes offline. A chat turn still runs.

Pin OK still opens Mood while the layer is online. An empty `mood-shape` is the Ollama model in use. Send talks to that shaper. One send is in flight. Approve keeps the transcript as the second system line. Cancel keeps the filter commit and leaves the mood line empty. The host line stays the host line.

A miss does not open a blocking dialog. The error slide carries the words. Those words start with `keyword only.` A reason from the shaper follows when one is present. A hard miss keeps the red slide. Any other miss uses the yellow slide. The mood line is cleared. The layer stays offline until the app starts again. A later pin OK still writes the filter and does not open Mood. A frame for a mood id never marks a cell, never clears a chain, and never fails a turn. When the mood line would make a turn too large, the line is dropped, the slide shows the miss, and the turn is sent without it. The inspector shows `mood: offline` after that miss.

## Mood layer online

2. A mood miss closes the dialog, paints the error slide, clears the mood line, and leaves the layer offline for this session. The canvas stays usable. The cell is not stored `FAILED` from that miss.

3. Frames that belong to a mood id stay on the mood path after the dialog closes. They do not enter the cell error path.

4. A turn whose body does not fit because of the mood line drops that line, reports the miss, and sends the turn.

5. No new package, public type, public method, vault column,, channel, or AMWP type.

Face leaves the host line and the approved mood line. Lands the offline switch and the turn that drops a line that does not fit. Holds the wire ids after the dialog closes. The row is the build.

## Thinking pane stays

In the Swing Aetheris pane, when thinking is on, the thinking slide up works. It disappears when thinking pauses. It stays in view until the user closes that pane. Close is on that pane. The pane sits above the footer bar. It slides up from the top of the footer bar. The footer buttons stay reachable. It persists until the user closes it.

Three lines stay viewable. The text stays yellow. `v` still folds the text and `^` still restores it. Close is the dismiss. A pause leaves the pane and the text. A later thought on another cell replaces the text. A later thought on the same cell appends. The text still drops when the session ends. It is still not a vault column.

## Thinking pane stays

2. While thinking is arriving, the pane opens with that slide. A pause leaves the pane up. The text stays. Close dismisses it. The dismiss slides the height back to the top of the footer, then drops the text.

3. `v` still folds the text. Close stays on the pane while the text is folded. A later thought still fills the area. A thought after Close opens the slide again.

4. No new package, public type, public method, vault column,, channel, or AMWP type.

Face leaves the yellow thought text and the `v` fold. Lands the slide on the top edge of the footer and the Close that dismisses the pane. Holds a second finish so the pause leaves the text. The row is the build.

## Thinking log

The thinking checkbox in Model Config does not persist. When it is turned on and the model config is copied for the next turn, the thinking state is carried. The thinking pane is sized by a drag on its north border. Thinking is a continuous scroll. The text is tagged from the model source, per block, with a separator between each model's text, so a merge stays readable. The log persists between sessions. A Clear control on the pane clears the pane and the saved log, and the log then accumulates again.

Validated. `newTurn` already copies a session holder onto the new cell: `ollamaOn`, `ollamaLevel`, `ollamaId`, `grokOn`, and `poolOn`. The holder is the map. It is not a vault column and it is not in `aetheris-grok.yaml`. A restart builds an empty map. The checkbox then reads off. The next new turn copies no thinking state. The execution target and the Grok document still copy. That is the miss. This row stores the holder and names `poolOn` on that same copy.

Close still hides the pane. This row keeps the log when Close hides the pane. Clear is the drop. The text stays off the turn.

1. The holder is written to `~/.casserole/aetheris-think.yaml`, version 1, keyed by cell id. The keys are `ollama_on`, `ollama_level`, `ollama_id`, `grok_on`, and `pool_on`. The dragged height is the key `height` in that same file. A version other than 1 is left unread and is not overwritten. The panel loads the file when it opens. A checkbox change writes the file. An effort change writes the file. `newTurn` still copies the holder, and the copy is written under the new cell id. A duplicated group writes the copied holders. A deleted cell is dropped from the file. No new vault column. `aetheris-grok.yaml` stays the Grok document.

2. The north edge of the thinking pane is a drag. Dragging it changes the pane height. The height grows and shrinks from the top of the footer. The footer buttons stay at full size. A drag keeps that height for this session and writes it into the yaml. The next session opens at that height. The minimum is the three-line pane. The maximum leaves the canvas and the footer in reach.

3. Thinking text is a continuous scroll. A later thought appends. It does not replace the log. Each block is tagged with the model name of the cell that produced it. A new block writes a separator and that tag, then the text. The same block's later chunks append. A pause closes the block. The next thought opens a new block. A group shows one block per member run, so each model stays marked. The body stays yellow. The tag is cyan. The area is a read-only styled pane. `v` still folds the text and `^` still restores it.

4. The log is `~/.casserole/aetheris-thinking.txt`. It is not a vault column. It is not stored on the turn. The next session loads it into the pane. When the log is non-empty, the pane is up. Clear is a small `C` at the east of the north edge, out of the text. Clear empties the pane and the file. The pane stays up. The next thought accumulates from that empty log.

The Rust match is a row on section 12 of, written when this Java lands. This row writes no crate.

Face keeps the yellow body, sets the model tag in cyan, and sets the small `C` on the north edge. Lands the drag on that edge, the continuous log, and the file that Clear wipes. Holds the copy on `newTurn`, including `poolOn`, and a second run as a new block. The row is the build.

## Mood string

Mood is optional. Only a defined mood activates the workflow, and it stays enforced for the session. The user may turn the mood off, and that removes every mood record. A mood switch sits on the footer. Turning it back on, without a change to the mood, inserts the last saved mood string at the head of the transcript. Changing the mood is a macro override of the transcript, including turns spoken under an earlier mood. A Mood button sits on the footer. The button uses the model config of the selected cell to draft the mood string. This sentence does not name the build. Java waits until a later sentence names the build.

BB2 gains a separator after To terminal. The mood switch and the Mood button sit after that separator. He is still chewing the requirements. Java waits.

2026-10-25 17:26. asked the mood glass written onto this row. The dialog carries a private JediTerm. The connector is local. The transcript is an in-memory list. Each Send pushes that whole list on channel A. No new lane.

Dish 1 is the mood string. Dish 1 keeps the Swing transcript and the Send field. The private JediTerm waits.

The layout:

Mood is optional. With no saved string, a Run carries the host line and the turns. The switch stays off. A click on the switch does not turn it on.

The saved string is session memory. It is not a vault column. It is not written into a cell. A restart starts with no saved string and the switch off. Export does not carry the string. Clear turns leaves the string and the switch.

BB2 carries the mood controls. The row is search, Search, Export, Import, Pull, To terminal, then a separator, then the mood switch, then the button labeled `Mood`. The separator is the same narrow vertical mark BB1 uses between groups. BB1 does not carry these two controls. BB2 still starts hidden. The `v` on BB1 opens the row, and that tip names the mood switch and the Mood button with the other tools. The switch has two positions. Left is off. Right is on. It is not a range slider. The switch tip while on says a click removes every mood record and keeps the saved string. The switch tip while off, with a string saved, says a click inserts that string at the head. The switch tip with no string says the switch stays off until a mood string is saved. The button tip says Mood opens a chat with the selected card's model, and Approve saves one mood string and places it at the head.

The Mood button opens the dialog for the selected card. The selected card wears the sapphire edge. A missing readable card leaves the dialog closed. The status line shows `no cell`.

The speaker is that card's model config. The call uses the target and the model id that card would use on Run. An empty target is `ollama`. A Grok card uses that card's Grok model id. A pool card uses `pool`. The call is ephemeral. Think stays off. The working set is the shaping chat. It is not the canvas transcript.

A cloud target that card is not allowed to call leaves the dialog closed. The error slide says `keyword only.` The saved string and the switch stay. A missing AMWP connector does the same.

The dialog title is `Mood`. It is modal. The center is a private JediTerm. Approve, Cancel, and an editable mood-string field sit under the glass. Enter on the glass sends the current line. An empty line is ignored. One send is in flight. A second Enter leaves the typed line on the glass.

The glass is a second JediTerm. It is not the live terminal. It is not a terminal session, and the vault does not follow it. Its settings are its own copy. The live terminal font stays put. The widget is created with the dialog and disposed when the dialog closes, on Approve, on Cancel, and on a miss.

## Mood string

The transcript is an in-memory list. The glass paints that list. The list is not scraped off the screen. Open starts the list empty. Each Send pushes the whole list on channel A, the same full working set a canvas Run pushes. The call stays ephemeral. The mood id is consumed before the cell path. The reply is printed in one write when it arrives. The last assistant line fills the mood-string field. The operator can edit that field. Approve commits the field. The list and the widget are discarded when the dialog closes. The saved mood string is what remains.

The shaping system line asks for one mood string. The string is style, mood, and symbolic modifiers. The host line on a canvas Run stays the host line. When the shaping body does not fit, the slide shows `keyword only. too-large`. The saved string and the switch stay. The list stays until the dialog closes.

Cancel closes the dialog. The saved string and the switch stay as they were.

A miss closes the dialog and paints the error slide. The words start with `keyword only.` A hard miss keeps the red slide. Any other miss uses the yellow slide. The saved string and the switch stay. The canvas stays usable. A frame for a mood id never marks a cell, never clears a chain, and never fails a turn.

Approve of the first string saves it, moves the switch on, and inserts that string once at the head of the transcript. While the switch stays on, the workflow stays enforced for the session.

The switch off removes every mood record from the transcript. The saved string stays. A Run carries no mood line.

The switch on, with the saved string unchanged, inserts that string once at the head. The switch does not call the model.

Approve of a different string replaces the saved string, removes every prior mood record, and inserts the new string once at the head. The new string governs the whole transcript, including turns spoken under an earlier mood. The switch is on.

Approve of the same string leaves a single copy at the head.

The walker puts the host line first. While the switch is on, the next system message is the saved string. While the switch is off, the walker skips it. The role is `system`.

When the encoded body does not fit and the mood line is present, the slide shows `keyword only. too-large`, the body is assembled again without that line, and the turn is sent. The saved string and the switch stay on.

The inspector shows `mood: (none)` when no string is saved. It shows `mood: off` and a preview of the saved string when the switch is off. It shows `mood:` and a preview of the live string when the switch is on.

Pin OK writes the filter and leaves the dialog closed. `notebook.mood-shape` stays in the yaml. The button does not read it.

No new package, public type, vault column,, channel, or AMWP type. The local connector is not a new public type.

Dish 1 is the build named at 2026-10-25 17:32. The Swing transcript stays for this dish. BB2 carries the separator, the mood switch, and the Mood button. The switch and Approve follow the layout above. The shaping system line is `Define one mood string. The string is style, mood, and symbolic modifiers.` A miss keeps the saved string and the switch. The layer does not latch offline.

The live POST was not made. A pool turn was not sent. A live SSH connect was not made. The private JediTerm waits.

Dish 2 is the mood glass. This sentence names the build. The Swing transcript gives way to the private JediTerm on this dish. The Mood button tip names the glass and Enter.

The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The slide said `keyword only. air-gap`. The shaping call was sending the burner bit. A normal pool card keeps that bit off, the same way Run does. The mood id still keeps the chat off the cell.

The mood chat stays on the glass. A you mark and an assistant mark separate the turns. The shaping line tells the model to end with one line, `mood: <the string>`. That string is the intent it understood: style, mood, and symbolic modifiers, on one line. The app keeps the last non-empty line that starts with `mood:`. That line fills the mood-string field. The rest of the reply stays on the glass. An empty key leaves the field as it was. Approve still saves the field.

The mood-string field stayed empty. The pluck wanted a line that started with the letters mood and a colon. A reply may wrap that key, change the case, put a space before the colon, or write mood string. A fenced mood block counts. The app keeps the last such value and puts it in the field. Stars, quotes, and a wrapped pair of angle brackets come off. The template words the string do not fill the field. The rest of the reply stays on the glass. An empty key leaves the field as it was. Approve still saves the field.

The field stayed empty. Face still paints the mood line in yellow when the key is marked. Lands the wider pluck. Holds the last value.

Face paints you in green and assistant in blue, and paints the mood line in yellow. Lands the shaping line and the pluck into the field. Holds the last non-empty mood line.

Face paints the list on the private JediTerm and leaves the mood-string field as the artifact. Lands Enter as Send and disposes the widget with the dialog. Holds one send in flight.

Face leaves the host line and puts the saved string at the head while the switch is on. Lands the switch that removes every mood record, and the approve that overrides the transcript with one new string. Holds the saved string while the switch is off. The row is the spec. The build is not named.

Face paints the in-memory list and leaves the mood-string field as the artifact. Lands Enter as Send and disposes the widget with the dialog. Holds one send in flight. The build is not named.

## Mood format

The pool model returned the mood string after he asked for the encoded format, and the line lit. He wants the dialog to retry when the reply has no mood line. Three tries, then a yellow card. He wants the standard thinking spinner, with the burner clock, on the footer of the dialog while a reply is in flight.

The dialog posts the format line and sends again. The third miss paints the yellow slide and leaves the dialog open. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

A reply that carries a mood line fills the field. The try count returns to zero. The spinner stops.

A reply with no mood line is one try. The assistant text stays on the glass. The field stays as it was. While the count is under three, the dialog posts one line asking for the mood text in the expected form and sends that list again. The line is a user turn, so the glass marks it you. The line says to return one final line that starts with mood: and gives the example `mood: spare, dry, lantern-light`.

The third reply with no mood line stops the retries. The yellow slide says `keyword only. mood line`. That slide is the yellow card. The dialog stays open. The list stays. The field stays as it was. The saved string and the switch stay. A later line he types starts the count over.

## Mood format

Think stays off. The shaping call still copies the card's burner bit. Air-gap stays. Channel A stays the lane. No new channel.

The Rust match is a row on section 12, written when this Java lands. This row writes no crate.

Face puts the spinner on the dialog footer and paints the yellow slide. Lands the three tries and the clock. Holds the field and the saved string.

## Spinner and switch

Two in one row. The thinking spinner bounces because the layout bounds the text to the left. The thinking text stays put while the glyph turns. The mood switch round button is green when the switch is on and red when it is off.

The word thinking and the clock stay put. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The spinner glyph keeps a fixed box. The word thinking and the clock sit to the right of that box and stay put while the glyph turns. The same spinner is the notebook spinner and the dialog footer spinner. The colors stay emerald for the glyph and electric blue for the word and the clock.

The round button on the mood switch is green while the switch is on and red while it is off. Left stays off. Right stays on. The tip names the colors.

Face paints the fixed box and the two knob colors. Lands the box and the paint. Holds left off and right on.

## Mood shape

He set the mood, then talked, and the model used that mood after he coaxed it. He wants a header after the mood setting, added when the transcript is sent. It is not stored in a cell. It is prepended and managed the way the mood header is. The header tells the model to activate and use that mood as the shape of its responses. He is testing against qwen.

The walker puts the host line first. While the switch is on and a mood string is saved, the next system message is that string. The system message after that string tells the model to activate the mood above and use it as the shape of its responses. The words are `Activate the mood above. Use that mood as the shape of your responses.` The role is `system`. The line is not written into a cell. The mood glass does not carry this line. The shaping chat stays the shaping chat.

While the switch is off, or no string is saved, the walker skips the mood string and skips this line.

When the encoded body does not fit and the mood line is present, the slide still shows `keyword only. too-large`. The body is assembled again without the mood string and without this line. The saved string and the switch stay on.

The inspector, while the switch is on, shows the mood preview and then `shape:` with this line. The token estimate counts this line. While the switch is off, or no string is saved, the inspector does not show the shape line and the estimate does not count it.

The switch tip, while a string is saved, names the shape line.

Face shows the line on the inspector while the switch is on. Lands the line after the mood string. Holds the drop of both lines when the body does not fit.

## Closed thinking pane

He closed the thinking pane, then closed the app. The next open showed the pane again. A closed pane stays closed on that open.

Close hides the thinking pane and keeps the log. That choice is the root key `closed` on `~/.casserole/aetheris-think.yaml`. Close writes `closed` true. The file version stays 1. A version other than 1 stays unread and is not overwritten.

The next open loads the log. When `closed` is true, the pane stays hidden. When `closed` is absent or false, and the log has text, the pane shows. An empty log still leaves the pane hidden.

A later thought opens the pane and writes `closed` false. The `v` fold stays for this session. It is not written. Clear still wipes the pane and the saved log.

The Close tip says a closed pane stays closed when the app opens again.

Face keeps the log and leaves the pane shut. Lands the closed key and the load that honors it. Holds a later thought opening the pane again.

## Approve width

In the mood dialog, the Approve button text is truncated, with an ellipsis. He wants the button wider so Approve is fully displayed.

The label stays `Approve`. The short fit still sets the height. After that fit, the preferred width and the minimum width grow by three ems of the button font. The ellipsis no longer replaces the end of the word. Cancel stays the short fit. The footer buttons stay the short fit. The tip stays. The click still saves the field.

Face widens Approve. Lands the three ems after the short fit. Holds Cancel and the footer buttons.

## Mood stays

The mood was ephemeral to the app session. He wants it persisted so he does not redefine it. The switch flips. The Mood button sets the mood. If the switch is on when the app exits, it is on when the app starts. The same for off. A status line above the inspector shows the mood string while the switch is on, and does not show while the switch is off. When the modal sets a mood, the switch turns on if it was off. Until a mood has been defined, the switch is grey.

The switch flips. Approve turns it on. The round button stays grey until a mood is saved. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The saved string and the switch position are `~/.casserole/aetheris-mood.yaml`, version 1. The keys are `line` and `on`. The file is not a vault column and not a cell. Export does not carry it. Clear turns leaves it. A missing file is no string and the switch off. A version other than 1 is left unread and is not overwritten.

The Mood button stays enabled. It opens the glass. Approve of a non-empty field saves that string, turns the switch on when it was off, and writes the file. An empty field writes nothing. The switch does not call the model.

With a saved string, a click flips the switch and writes the file. On at exit is on at the next start. Off at exit is off at the next start. With no saved string, the round button is grey, the switch stays off, and a click does nothing.

A yellow line sits above the inspector. While the switch is on, the line shows the saved string. While the switch is off, the line is not shown.

The switch tip names the grey state, the flip, and that the mood stays. The Mood button tip names that Approve turns the switch on when it was off and that the string stays.

Face paints the grey knob and the yellow line. Lands the file, the flip, and the line. Holds an empty Approve and a version other than 1. stays sheathed.

## Mood rise

When the mood string is set in the footer of the mood dialog, he wants that text to operate more like the thinking pane. When the text is set, a pane pops up from the top of the footer and shows the text. The pane sizes its height to how the text wraps. One line is one line tall. Two lines are two lines tall. He wants the string easy to inspect and approve, and the footer stays clean.

The height follows the wrap. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The resting footer of the mood dialog is the spinner, Approve, and Cancel. The single-line mood field and the Mood string label leave that row.

When a reply sets the mood string, a pane rises from the top of that footer. The rise starts at height 0 and slides to the wrapped height. A later string, or an edit, sets the new height without sliding from zero again. An empty string hides the pane. The pane is this dialog only. It is not the canvas thinking pane. It is not saved.

The height is the wrapped line count at the pane width. One visual line is one line tall. Two visual lines are two lines tall. Word wrap. The height stops at half the dialog. Taller text scrolls. A dialog resize remeasures the wrap.

The pane stays editable. Approve still reads it. An empty field writes nothing. The field colors stay the field colors.

The pane tip names the rise, the wrap, and that the text can be edited before Approve. The Mood button tip names that the string rises above the dialog footer.

Face raises the pane and sizes it to the wrap. Lands the rise and the height. Holds an empty string and a height that would cover the glass.

## Dialog size

He wants the mood dialog size kept, on Approve or on Cancel, so the next open matches the size he left.

The size is `width` and `height` on `~/.casserole/aetheris-mood.yaml`, version 1. The file is not a vault column. A missing pair opens the dialog at 760 by 520. A kept width from 480 through 4000 and a height from 320 through 4000 is used. A number outside that range is ignored. The place on screen is not kept. The dialog still centers.

Approve and Cancel write the size, then the dialog closes. The title-bar close uses that same close, so it writes the size too. An empty Approve still writes no mood string. The size is written with the close. A later switch flip keeps the pair. A version other than 1 is left unread and is not overwritten.

Approve and Cancel name that the dialog keeps this size. The Mood button names that the dialog opens at the size it had when it last closed.

Face opens the dialog at that size. Lands the write on Approve and Cancel. Holds a size outside the range and a version other than 1. stays sheathed.

## Mood tokens

He asks the mood model for enriched detail. The model says the detail and leaves the mood string thin. He wants the control header to tell that model to put more semantic tokens on the mood line when he invites the enrichment, and to say in the reply what it determined.

The mood glass system line tells the model how to fill the mood string. The pluck stays. The last mood line still fills the field. The mood file stays the mood file.

The system line says the app stores the mood line. The line is many tokens, separated by commas. Each token is one short meaning. Before the line, the model says what it determined. When the user asks for more detail, a richer mood, more tokens, or an enriched mood, the model says that detail in the reply and puts the same detail on the mood line as more tokens. A short line stays enough when the user did not ask for more.

A missing mood line still asks again. That ask repeats the same rule.

The Mood button tip names that ask.

Face states the rule in the control header. Lands the header and the format ask. Holds a short line when the user did not ask for more.

## Mood wire

He types a request on the mood glass. The yellow slide says `keyword only. cell-busy`.

The mood glass used one wire id for the whole chat. The server holds that id until the reply has been written. The next mood frame, including the ask for a missing mood line, uses that same id. The server answers `cell-busy`. The glass treats that answer as a miss, closes, and the yellow slide says `keyword only. cell-busy`.

Each mood send now takes a new wire id. The id is registered before the frame is written. A late frame for an older id stays off the cell. The reply for the send just made still fills the glass. A canvas cell that is running still answers `cell-busy`. The mood glass does not reuse the id of the turn that is releasing.

Face gives each mood send its own wire id. Lands the id on the send. Holds a late frame for an older id off the cell.

The glass, the switch, the saved string, the shape line, the rising pane, the kept size, and a mood turn's own wire id held. It does not open a row.

Keeps the Java rows as they landed. Face states the shell a Rust client carries. Lands that shell on section 13. holds the catch-up rows.

## Mood session

A saved mood opens in the field. He can ask the model to enhance it. The mood tokens go in the header the way a conversation send carries them. Clear mood starts from scratch.

A saved mood is already in the field. The mood send carries those tokens in the header. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The dialog used to open on an empty field and an empty chat. The saved string stayed on the switch and on the next canvas send.

When a saved mood is present, the field already holds that string. The pane rises. He can edit it, approve it, or ask the model to enhance it. An empty save still opens an empty field.

A mood send keeps the shape line. When the field holds a mood, that string and the activate line follow the shape line, before the chat. The pair is the same pair a canvas send places after the host line. The switch does not gate this chat. An empty field sends no mood tokens. A canvas send still carries the pair only while the switch is on.

Clear mood drops the saved string, turns the switch off, and empties the field and the chat. A reply already in flight stays off the glass. The dialog stays open. The kept size stays.

Face places the same pair on the mood send when the field holds a mood. Lands the field, the header, and Clear mood. Holds an empty field with no mood tokens.

It worked. A saved mood is already in the field. The mood send carries those tokens in the header. Clear mood drops the string and the chat. It does not open a row.

Keeps the Java row as it landed. Face keeps the field, the header, and Clear mood. Holds the crate for the named catch-up. Holds an empty field with no mood tokens.

## Clear ask

Clear mood asks before it drops the string. The ask says Clear the Mood Setting? Yes clears. No leaves the mood. The mood dialog stays open.

Yes clears. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

Clear mood opens a small ask over the mood dialog. The words are Clear the Mood Setting? Yes is green. No is red. Yes runs the clear. No closes the ask. The mood string, the switch, the field, and the chat stay. The mood dialog stays open.

Face paints Yes green and No red. Lands the ask over the mood dialog. Holds a no, a close, and an escape as leaving the mood untouched.

## Choice words

Yes and No show their words. The buttons stay green and red.

The ask stays. The words stay Clear the Mood Setting? Yes stays green. No stays red. Each button is wide enough for its word. The bevel and the pad sit outside the word.

Face sizes each button to its word. Lands the width after the color. Holds an ellipsis as a miss.

It worked. Yes clears. No leaves the mood. Yes and No show their words. It does not open a row.

Keeps the Java rows as they landed. Face keeps Yes green and No red, and the words showing. Holds the crate for the named catch-up. Holds a no, a close, and an escape as leaving the mood.

## Pin bar

The pin bar sits on the canvas. He said fix the Java UI before the Rust work.

The pin box, the word field, and OK sit on a thin bar at the top of the canvas pane. The inspector header keeps the Inspector label, soft wrap, and the two counters. The tips stay on the three controls. OK writes the states. The click does not open Mood. The OK tip stays the sentence already on the button. The edge paint stays in paintChrome. No side move on this plate. This land leaves the working set as it stands.

This row writes no crate.

Face keeps the bar thin. Lands the bar on the canvas. Holds the inspector header as the wrap controls.

## Pin fold

The pin bar folds. Enter in the field does what OK does. Turning the box off keeps the words. Clear empties the field.
**semantic-key:** PK-1162
**semantic-key:** PK-1162
**semantic-key:** PK-1162
**semantic-key:** PK-1162
**semantic-key:** PK-1162
**semantic-key:** PK-1162
**semantic-key:** PK-1162
**semantic-key:** PK-1162

v folds the pin controls. ^ opens them. The bar starts folded. The choice is saved with the shell. A missing key stays folded. The footer tool bar stays unsaved. Enter in the word field does what OK does. Turning the box off keeps the words. Only a change in the field, or Clear, changes those words. Clear is a red button with a black x, to the right of the field. The states stay until OK. The OK tip stays the sentence already on the button. The edge paint stays in paintChrome. The inspector header keeps soft wrap and the two counters. No side move on this plate. This land leaves the working set as it stands.

Face paints Clear red with a black x. Lands v on the bar. Holds a missing key as folded.

## Pin tuck

v on the open bar folds the whole bar off the canvas. The cards use the height they had before the pin bar. P sits on the east of the canvas tab header while the bar is folded, the way C sits on the thinking grip. P adds no row. P shows the bar. v stays v. Folding does not uncheck the pin. Only the pin box turns the filter off. The words stay until the user changes them. The choice is saved with the shell. A missing key stays folded. The OK tip stays the sentence already on the button. The edge paint stays in paintChrome. No side move on this plate. This land leaves the working set as it stands.

Face tucks P on the tab header. Lands v as the fold. Holds a missing key as folded.

## Mark face

P and C wear sapphire. A one-pixel white bevel surrounds each face. The letter is white.
**semantic-key:** PK-1161

P and the thinking-pane C wear the armed-card sapphire. A one-pixel white bevel surrounds each face. The letter is white. The seat of P stays on the east of the canvas tab header, overlaid, with no added row. P shows while the pin bar is folded. C stays on the east of the thinking grip and still clears the log. The other short buttons keep the theme bevel. The cell-state P stays on the theme face. Folding leaves the pin box as it is. The words stay. The edge paint stays in paintChrome. No side move on this plate. This land leaves the working set as it stands.

Face paints the sapphire and the white bevel. Lands the letter in white. Holds the other buttons on the theme bevel.

## Pin edge

The pin edge is the paint already on the cards. While the pin box is on, a readable match wears green and a readable miss wears dim. The armed card stays sapphire.

The pin edge is the paint already on the cards. The live POST was not made. A pool turn was not sent. A live SSH connect was not made. This row writes no new Java.

While the pin box is on, a readable match is `#3cb371` and a readable miss is `#222222`. The armed card, and every card in a group whose merge is armed, is `#2e86ff`. That sapphire wins over the pin color. The paint stays in `paintChrome`. This row writes no new Java. The inner prompt and reply borders stay their own green line. The pin box stays the only off switch. The words stay. The seat of P stays. The face of P and C stays. No side move on this plate. This land leaves the working set as it stands.

Keeps the pin box as the switch. Face keeps green for a match and dim for a miss. Keeps the armed card sapphire. Holds the merge arm.

## Pin face

P is green while the pin is on and sapphire while the pin is off.

P is green while the pin is on and sapphire while the pin is off. C stays sapphire. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

P is green while the pin is on and sapphire while the pin is off. Green is #3cb371. Sapphire is #2e86ff. The letter stays white. The one-pixel white bevel stays. C stays sapphire. The pin box stays the only switch. Java keeps P on the east of the canvas tab. The thinking-pane C stays sapphire. The Rust face follows in this same course. No side move on this plate. This land leaves the working set as it stands.

This course carries the face onto the crate.

Face paints the green and the sapphire. Lands the Rust face in this same course. Holds C on sapphire.

## Expanded view

Each readable cell carries an Expanded View checkbox. Checked, the reply shows all of the text inside the text-pane border. Unchecked, the reply stays at the footer row count, the same height as the prompt. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell's choice.

Each readable cell carries Expanded View. Checked, the reply shows all of the text inside the border. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The checkbox reads Expanded View. It sits on the readable cell header after the collapse control and before the burner checkbox. An unreadable card has no box. Checked, the reply text pane grows to the wrapped buffer and shows all of the text inside its border. A text change and a window resize refit a checked reply. Unchecked, the reply stays at the saved view-row count, the same height as the prompt, and the text scrolls inside. The footer count sets that height. The prompt keeps that count. A folded card stays a brief line. The checkbox still shows and keeps the choice. Opening the card honors the choice. A fresh canvas, a vault open, and a disconnect reload start unchecked. Footer Clear leaves one empty card unchecked. A new turn copies the prior cell. An empty deck stays unchecked. A duplicated group starts checked. Import, Pull, and a merge cell start unchecked. The choice stays for this window. It is not saved. It is not a vault column and not an export field. The collapse control stays the saved fold. Java holds the core. The seated crate already paints Expanded View. This row writes no crate. No side move on this plate. This land leaves the working set as it stands.

Keeps the choice off the vault. Face paints the whole reply inside the border. Lands the listener on the pane. Holds the fresh box shut.

Later append under this heading.

## Cell stamp

Carry the model name and the timestamp onto the Java cells. Embed that line on the cell border the way the Rust cell wears it, adapted to Swing. Swing paints across the border. Swing does not take the north pad.

The focused cell wears the local stamp and the short model on the top stroke. Swing paints across the border. There is no north pad. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The focused cell wears the local stamp and the short model on the top stroke of its outer frame. The line is centered on that stroke and seated on the right, eight pixels in. The words are `── stamp · model ──`. A blank model keeps the stamp. The stamp is local wall time, year month day, hour minute second. The clock ticks once a second. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. An armed merge keeps the sapphire line. The line stays on the focused cell. Folded or open, the seat is that outer frame. The hover says the line is the model this turn will call. The model line leaves the header. Swing draws the line on top of the border. The cell keeps its width. The buttons, the prompt, and the reply keep their size. There is no north pad. There is no first-cell lead. There is no inner button pad. The input pane border stays a plain stroke. Java holds the core. The seated crate already paints the embedded line and the first lead. This row writes no crate. The Rust lead stays on the crate. No side move on this plate. This land leaves the working set as it stands.

Keeps the line off the vault. Face paints the line across the border. Leaves the north pad on the crate. Holds the clock at one second.

## Clean rule

The rule beside the stamp is a stroke, not a character. Swing paints that rule in the graphics. The text carries no box rule. The line stays clean and still reads the way the Rust cell reads.

The rule beside the stamp is a stroke. The text carries no box rule. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The focused cell still wears the local stamp and the short model on the top stroke of its outer frame. The line is centered on that stroke and seated on the right, eight pixels in. The words are the stamp and the model. A middle dot sits between them. A blank model keeps the stamp. Each flank is a one-pixel stroke, two ems long, the span the box rule used to take. One space of air sits between the stroke and the words. The stroke uses the caption ink and sits on the border. The text carries no box rule. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. An armed merge keeps the sapphire line. The line stays on the focused cell. The hover stays the line is the model this turn will call. The cell keeps its width. The buttons, the prompt, and the reply keep their size. There is no north pad. There is no first-cell lead. There is no inner button pad. The input pane border stays a plain stroke. Java holds the core. The seated crate still paints the character rule. This row writes no crate. No side move on this plate. This land leaves the working set as it stands.

Keeps the line off the vault. Face paints the stroke in the graphics. Leaves the character rule on the crate. Holds the clock at one second.

## Sapphire stroke

The stroke beside the stamp wears the selection sapphire. The words stay the caption ink.

The stroke wears the selection sapphire. The words stay the caption ink. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The focused cell still wears the local stamp and the short model on the top stroke of its outer frame. The line is centered on that stroke and seated on the right, eight pixels in. The words are the stamp and the model. A middle dot sits between them. A blank model keeps the stamp. Each flank is a one-pixel stroke, two ems long. One space of air sits between the stroke and the words. The stroke wears the selection sapphire, the same color the armed card already paints. The words stay the caption ink. The text carries no box rule. A wide line keeps the right end and loses its left. A quiet cell keeps a plain frame. An armed merge keeps the sapphire line. The line stays on the focused cell. The hover stays the line is the model this turn will call. The cell keeps its width. The buttons, the prompt, and the reply keep their size. There is no north pad. There is no first-cell lead. There is no inner button pad. The input pane border stays a plain stroke. Java holds the core. The seated crate still paints the character rule. This row writes no crate. No side move on this plate. This land leaves the working set as it stands.

Keeps the line off the vault. Face paints the stroke in the selection sapphire. Leaves the character rule on the crate. Holds the clock at one second.

## Scroll focus

A click on a text pane puts scroll focus on that pane. A click outside that pane returns scroll focus to the canvas. Hover on a cell shows the model as it is stamped when a cell is selected.

A click on a text pane puts scroll focus on that pane. A click outside that pane returns scroll focus to the canvas. Hover shows the stamp. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

A click on the prompt or the reply, including its scrollbar, puts scroll focus on that text. The wheel then scrolls that pane and stops at its end. A click anywhere else on the card returns scroll focus to the canvas. The armed cell stays armed. A click on the canvas padding arms nothing and returns scroll focus to the canvas. The wheel over a text pane scrolls that pane only while that pane owns scroll focus. Otherwise the wheel scrolls the canvas. A folded brief is not a text pane. A click on it returns scroll focus to the canvas. The execution target keeps the caret. Hover anywhere on a cell shows the stamp and the short model, the same words a selected cell paints. A blank model keeps the stamp. The clock is the moment of the hover. A quiet cell still paints no line. The focused line keeps its hover, the line is the model this turn will call. Buttons keep their own hover. Java holds the core. The seated crate still paints the character rule. This row writes no crate. No side move on this plate. This land leaves the working set as it stands.

Keeps the line off the vault. Face shows the stamp on hover. Leaves the character rule on the crate. Holds the wheel on the focused pane.

## Console thought

He is testing thinking on the VT100 with pool. The pull-down pane is not activating. He suspects a mux issue on the thinking channel. He invited the floor onto the elegant solution. A pool thought on that console rides the thinking channel and opens the pull-down. The thought stays off the terminal.

A pool thought on the VT100 opens the pull-down. The thought stays off the terminal. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

A VT100 pool turn was collecting the reply and dropping the thought. The thought now rides kind `t` on the stream channel. The cell id is the nil id the console already uses. When console pool thinking is on, that chunk opens the pull-down and the tag is pool. The thought is not written on the terminal. The assistant reply still paints on the VT100. The end of that turn closes the block, so the next thought starts a new block. A canvas cell keeps its own gate. Ollama and Grok on the console stay as they are. Java holds the core. The seated crate still paints the character rule. This row writes no crate. No side move on this plate. This land leaves the working set as it stands.

Keeps the thought off the terminal and on the stream channel that already carries kind `t`. Face opens the pull-down for the nil cell. Binds the session writer and leaves a new channel unallocated. Holds the canvas cell on its own gate.

## VT100 strip

He approves the thinking. The thinking pane becomes one component. A second instance sits on the VT100 pane. The stream delta stays the signal. The thought stays off the terminal.

The VT100 strip opens when a pool thought arrives. The notebook strip keeps the cell. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The pull-down is one component. It opens a block with a tag, appends text, and finishes. It does not read a frame, a cell, or a channel. The notebook keeps one instance and feeds it cell thoughts. Each VT100 panel keeps another instance and feeds it a nil-cell kind `t` when console pool thinking is on. The tag on that instance is pool. The notebook strip does not paint the nil cell. The two logs stay apart. The VT100 strip does not write the vault log. The assistant reply still paints on the VT100. The terminal bytes stay channel T. Ollama and Grok on the console stay as they are. A canvas cell keeps its own gate. Java holds the core. The seated crate still paints the character rule. This row writes no crate. No side move on this plate. This land leaves the working set as it stands.

Keeps the strip free of the mux and seats one on each terminal panel. Face keeps the reply on channel T. Carries kind `t` on the nil cell into that strip. Holds the cell thoughts on the notebook strip.

## VT100 top

The VT100 thinking pane sits on the top and slides down. The bottom edge meets the top of the terminal. Drag that edge to set the height. The height and the closed state are stored apart from the notebook strip. The text stays off the vault log.

The VT100 strip opens from the top. The notebook strip stays on the bottom. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The VT100 strip sits on the north of the terminal and grows downward. The bottom edge of the strip meets the top of the terminal. Drag that edge to set the height. Dragging down grows the pane. The notebook strip stays on the bottom. Its grip stays on the north edge, and dragging up still grows that pane. The VT100 height and the closed flag are stored in `~/.casserole/aetheris-vt100-think.yaml`. Every VT100 strip shares that file. The thought text is not written there. The notebook file stays `aetheris-think.yaml` and the notebook log stays `aetheris-thinking.txt`. Close still hides the pane. A closed pane stays closed when the app opens again. v still folds the text for the session. Clear still wipes the text and leaves the pane up. A pool thought still opens the strip when console pool thinking is on. The assistant reply still paints on the VT100. Ollama and Grok on the console stay as they are. Java holds the core. The seated crate still paints the character rule. This row writes no crate. No side move on this plate. This land leaves the working set as it stands.

Keeps the text off the vault log and the chrome in its own file. Face seats the strip on the north and puts the grip on the south edge. Leaves the notebook strip on the bottom. Holds the cell thoughts on that strip.

## Console model

The VT100 console returns on the last selected model. Pool stays pool. The word survives an app restart.

The console returns on the last family. Pool stays pool. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The shell starts each session on ollama and forgets the word he last set. The session now reads `~/.casserole/{user}/console-model` before the first turn. Version 1 carries the family word. The word is ollama, grok, or pool. A missing file leaves the family at ollama. A version other than 1 is left unread and is not overwritten. The dialog set writes the file. The shell `model` word writes the file. The dialog still asks the shell, so the modal shows the stored word. The next turn uses that word. Pool stays pool. The notebook strip stays on the bottom. The VT100 strip stays on the top. Ollama and Grok options stay as they are. Java holds the core. The seated crate still paints the character rule. This row writes no crate. No side move on this plate. This land leaves the working set as it stands.

Pool came back as ollama. Keeps the word beside the console history and off the vault. Face shows the stored word when the modal asks the shell. Reads it before the first turn. Holds the notebook strip and the VT100 strip where they sit.

## Chosen model

The VT100 console returns on the model chosen in Model Config. The choice survives an app restart.

The console returns on the chosen model. Pool stays pool. An Ollama id stays that id. A Grok id stays that id. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

The modal writes the chosen model into `~/.casserole/{user}/console-model` with the family word. Version stays 1. The lines are the Ollama id, the Grok id, think, effort, send effort, stream, temperature when it was set, max tokens when it was set, burner, and pool think. A missing file leaves the family at ollama and the model at the server default. A file that carries only the family word still reads. A version other than 1 is left unread and is not overwritten. The dialog set writes the file. The shell `model` word writes the file. A Grok turn that turns send effort off writes the file. The next session reads the file before the first turn. The dialog still asks the shell, so the modal shows the stored model. Pool stays pool. An Ollama id stays that id. A Grok id stays that id. The server process that is already running keeps the shell it loaded. A new server process writes the file and reads it on the next session. The notebook strip stays on the bottom. The VT100 strip stays on the top. Java holds the core. The seated crate still paints the character rule. This row writes no crate. No side move on this plate. This land leaves the working set as it stands.

Keeps the chosen model beside the console history, in the same file as the family word. Face shows that model when the modal asks the shell. Reads it before the first turn. Holds the notebook strip and the VT100 strip where they sit.

## Add Cell

When a group is created, on the MERGE cell button bar ONLY, add a `Add Cell` button which adds a new cell to the cell group for the merge group. The existing functionality for adding cells, This is a new addition which makes it easier. The other way will be useful down the road. I like it. I just wanted to create the obvious easy way to add cells for merge groups for noob users. Java and Rust are both named.

The button label is `Add Cell`. It sits on the merge cell's main button bar only. A member card does not show it. An ordinary card does not show it. The canvas footer does not show it. The second bar still holds the execution target, Group, Join, and Ungroup, and it still starts hidden. New still appends one ordinary turn at the end of the canvas. Group still turns an ordinary card into member 1 and inserts a merge under it. Join still attaches an existing ordinary card to the armed member's group and leaves that card where it sits. Ungroup still pulls one card out. Add Cell adds a new empty member to that merge's group. The new member takes the next column, one past the highest member column. It is placed with the members, before the merge. The prompt and the reply stay empty. The new member copies the last member's execution target, Ollama think choice, Grok settings, and expanded choice. When the group has no member, the new cell stays on an empty target and starts unchecked. Focus moves to the new prompt. Both clients paint the button. No new package. No new public type. No new vault column. No new channel. No new AMWP type. No side move beyond the copy and the focus, and both clients make that same copy. No system line is added, removed, or reordered. This land leaves the send set as it stands.

Keeps Group, Join, Ungroup, and New on the path they have. Face puts `Add Cell` on the merge bar only. Inserts the new member before the merge. Holds the next column.

2026-10-31 08:31. Audit slice A is the copy chord. It spends no F-number.

2026-10-31 08:45. Audit slice B is the status line. It spends no F-number.

2026-10-31 09:21. Audit slice D is the tools menu. It spends no F-number.

## Folding panel

The `v` toggle folding panel we have been using as a pattern, I am calling that a `folding panel` as a term for us to use to make it easier to describe. Instead of a JSplitPane to facilitate the collapse of the control menu at the top, I want a folding panel used instead of a JSplitPane to separate the control bar at top and the VT100 and Aetheris panes. Please make it behave like the way it does in the Rust Aetheris pane in this respect. Java is named.

A folding panel is the `v` toggle. Open paints `v`. Folded paints `^`. It is a toggle. The control bar is not a drag.

The folding panel sits between the control bar and the VT100 and Aetheris panes. The vertical control split goes away. A New Term split stays a split.

The bar starts open. Connect folds it. Disconnect opens it. A resize while the link is up folds it. A resize while the link is down leaves the mark. The mark toggles either way. The first span is stored and does not fold. Connecting leaves the bar and the link. The choice is not saved.

Open, the menu sits at the top. File, Tools, and Help stay in that order. Show Thinking stays. Token Calc stays off. The `v` mark sits on the control fields. The SSH and Aetheris tabs sit on the next row.

Folded, one row holds the `^` mark, the link word, SSH, Aetheris, a vertical divider, then File, Tools, and Help. The same menus move. They are not painted twice. The tab strip hides while the bar is folded and returns when the bar is open. Connected paints the short chip Connected. Another link word uses the west line with an empty host. The south strip stays the long line.

A connect on the focused session folds the bar. A pane you switch to that is already connected folds the bar. The pane you are already on keeps the mark until the next beat. A disconnected pane you switch to opens the bar. An Aetheris tab stays enabled only for a focused AMWP session. A folded SSH press still honors the restore mark.

No new package. No new public type. No new vault column. No new channel. No new AMWP type. The seat is the package-private folding panel. No side move on this plate. This land leaves the working set as it stands. The crate already paints this bar. This row writes no crate.

Keeps the terminal splits and the south strip. Face puts `v` on the open bar and `^` on the folded row. Seats File, Tools, and Help once. Holds SSH and Aetheris.

2026-10-31 10:12. Audit slice C is the token window. It spends no F-number.

## File import

Java and Rust are named. On the file import, it is constrained by a 65k boundary; perhaps its the length of the buffer array? Please update the logic in file import to allow import of file text of arbitrary size, stream it from disk into the VT100 session OR into a cell input pane in the canvas. If an error is thrown as a result, just throw it as a red error. The user is responsible for size overflows, if they occur. Usually a user will load a reasonable file size. If we have issues around this we will figure out how to gracefully handle it later.

A large file gets a polite message. He encourages that proactive defensive design. Approves of the tight bolts on the cam. The polite line belongs to this row.

No Session::start. No chrome::run. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

A file import streams the text from disk. The chunks are 8192 bytes. The read does not allocate one array of the file length. The session import places the text in the VT100 session. The prompt import places the text in the cell input pane. Neither path stops at 65536 characters or at 1 MiB.

The polite line is `This file is large. The import continues.` A session file longer than 65536 bytes says that line, then the import continues. A prompt file longer than 1 MiB says that line, then the import continues. A file at either bolt does not say the line. The line is not an error. On the prompt, the landed status keeps the line ahead of `imported into turn N`.

A thrown error is the red error. The session shows the error dialog. The prompt shows the red error slide. The Rust session paints that dialog in the red ink. The user owns a size overflow. This row does not add a later graceful handler.

An empty session file stays refused. A binary file stays refused. A merge cell stays refused. An output-only cell stays refused. Send file stays at 65536 characters. The paste editor stays at 65536 characters. A cell written to the terminal stays at 65536 characters. The `.aetheris` deck import stays at 1 MiB.

No new package. No new public type. No new vault column. No new channel. No new AMWP type. No side move on this plate. This land leaves the working set as it stands. He named the crate. The crate streams the same two imports.

Keeps the 65536 and 1 MiB bolts as the polite line, and he keeps Send file, paste, and the deck import stopped where they are. Face puts the thrown error in red. Seats the stream in chunks. Counts a file at the bolt as not large.

## Row height on a group

Java and Rust are named. The add cell works. When the cell is added, size it according to the defined row height. It is being added and not sized the same as the initial cell that was added. Cells in a cell group must adhere to the font height control sizing function on the BB1 button bar. When the height is changed, the merge cells also re-size according to the defined number of visible rows on input.

The BB1 control is the saved visible-row count. The height is that count times the mono line. The line is floored at 16. Eight pixels sit under the lines. A missing count is 4. A count under 1 is 1. That is the font height control sizing function.

An added cell uses that height for its prompt. Its reply uses that height when Expanded View is unchecked. When Expanded View is checked and the reply has text, the reply stays whole, and a group cell's reply is never shorter than that height. An empty added cell therefore shows the same row window as the initial member. Add Cell still copies the last member's execution target, Ollama think choice, Grok settings, and expanded choice.

Every open cell in a group uses that function. A sequential card outside a group keeps the rule it already has. A folded card stays a brief line.

The merge cell has no prompt. Its reply pane uses that same height, including when the reply is empty. When Expanded View is checked and the reply has text, the reply stays whole, and the pane is never shorter than that height. Changing the row control changes that height, so the merge re-sizes with the new count.

No new package. No new public type. No new vault column. No new channel. No new AMWP type. No side move on this plate. This land leaves the working set as it stands. He named the crate. The crate uses the same function.

Keeps Expanded View whole when the reply has text, and he keeps the row window as the floor. Face keeps Add Cell on the merge bar. Seats the empty merge on that window. Counts the line at 16 and the pad at 8. stays sheathed.

## Merge width

Rust is named. The merge cell must expand horizontally with the width of the canvas the way a normal cell does. It is being rendered shorter, snapping to what is visible. It needs to match the layout of cells on the horizontal aspect.

A normal cell fills the canvas column. The prompt asks for the width of that column. The stroke follows that width.

The merge has no prompt. Its frame was the button bar, so the stroke stopped at the buttons. The merge now takes the column width. A finite width of at least one pixel is that column. The stroke follows it. When the canvas grows, the merge grows with it.

A member stays the button bar plus ten pixels. The members stay on the sideways row. The merge stays under that row. The gather order stays.

An expanded merge reply fills that same width and wraps. An unchecked merge reply already asks for the row width. A folded card stays a brief line.

No new package. No new public type. No new vault column. No new channel. No new AMWP type. No side move on this plate. This land leaves the working set as it stands. He named the crate. The card-round hunks stay unstaged.

Keeps the member width. Face keeps the merge under the row. Seats the stroke on the column. Counts a finite width of at least one pixel.

## Charcoal menu

Java is named. The background of every context menu uses the dark charcoal of the theme. The green foreground pops on that charcoal.

A pool turn was not sent. A live SSH connect was not made.

The charcoal is the panel background. The green is the menu foreground. Both already live on the theme. No new color key.

The menus are the cell import popup, the terminal context popup, and the File, Tools, and Help dropdowns. The Ollama combo stays a combo and keeps the field colors.

`applyPopup` paints a popup. The popup is opaque. The fill is the panel background. The words are the menu foreground. The border is the button background. Each plain item is opaque on those colors. A nested menu is painted the same way. The basic popup painter and the basic item painter carry the colors on Windows. `installLookAndFeelDefaults` sets those colors before the window is built.

The cell popup is painted before it is shown. The terminal popup is painted before it returns. `setMenuTheme` keeps the theme on the terminal widget. File, Tools, and Help are painted after their items are added.

The menu words stay. The import tip stays. No yaml edit.

No new package. No new public type. No new vault column. No new channel. No new AMWP type. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the existing colors. Face keeps the menu words. Seats the basic painter so Windows shows the charcoal. Counts no new color key.

## Charcoal title

Java is named. The window title bar is painted a very dark charcoal to match the overall window theme.

The very dark charcoal is the frame background. The title words are the panel foreground. Both already live on the theme. No new color key. The native title bar stays. Min, max, and close stay.

`paintCaption` runs when the window opens and when the window state changes. On Windows the caption and the border take the frame background. The title text takes the panel foreground. A dark caption mode is set so the bar accepts that paint. Any other system skips the paint. A missing handle skips the paint. A failure is caught and the window still opens.

The methods stay private on the shell window. No new public type. No new library. The platform linker paints the caption.

The content pane stays on the frame background. The import path stays. No yaml edit.

No new package. No new vault column. No new channel. No new AMWP type. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps min, max, and close. Face keeps the native bar. Seats the caption color on the frame background. Counts no new color key.

## The Apple scroll

Java is named. Every scroll bar in Java Aetheris looks and behaves like the Apple style scroll bars in the Rust Aetheris app. A reusable scroll bar component carries that behavior, built once, then used again.

The Rust bars float on the content. They take no gutter. They stay clear while the pointer is off the pane. On the pane they are a thin pill. On the bar they widen. A click off the handle centers the handle. A drag follows the pointer. The wheel stays.

## The Apple scroll

The hit strip is 10 pixels. The resting width is 2. The handle is at least 12. The corner radius is 6. The fade is 0.2 seconds, cubic out. The track is the Rust plate `#0d0d0d`. The handle is gray 215, gray 245 while the pointer is on the handle, and white while the button is down. The track opacity is 0.4 on the pane and 0.7 on the bar. The handle opacity is 0.6 on the pane and 1 on the bar. A pane that opens under the pointer fades in.

The bar takes no layout gutter. The member row stays the height of its cards. The existing height function still adds the bar's preferred height, and that height is zero. The policies stay. A bar that is not needed stays hidden. A press does not take the caret. The bar is not a tab stop.

The terminal view paints its own cells. It has no Swing bar. The light-gray arrow buttons leave `applyScrollPane`. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the wheel and the policies. Face keeps the pill on the content. Seats the overlay so the viewport keeps its width. Counts 10, 2, 12, 6, and 0.2. stays sheathed.

## Hot strip

Java is named. The scroll bar pops out when the pointer is on the hot strip, the same strip the Rust bar senses.
**semantic-key:** PK-855

The viewport is added first. It paints last and it takes the pointer. The bar sat under that view. The hot strip never saw the hover, and the pill never showed. Wheel scroll still moved the content.

The layout raises a visible bar above the view. The vertical bar sits above the horizontal bar where they meet. The 10 pixel strip receives the pointer. The bar pops out. The pill paints on the content. A bar that is not needed is cleared to an empty box so it does not keep the strip. Hover on the pane uses the mouse event. A pane that opens under the pointer still reads the pointer.

The preferred thickness stays zero. The member row stays the height of its cards. The wheel stays. The policies stay. The terminal view stays. It has no Swing bar. A press does not take the caret. The bar is not a tab stop.

## Hot strip

Keeps the wheel and the policies. Face keeps the pill on the content. Raises the bar above the view. Counts the 10 pixel strip.

## Wider bar

Java is named. The width of the scroll bar, once it has popped out, is 5 pixels wider.

The popped-out bar was 10 pixels. It is 15. The thin pill stays 2. The strip that holds the popped-out bar is that same width, so the strip is 15. The colors, the opacities, the fade, and the corner radius stay. The preferred thickness stays zero. The member row stays the height of its cards. The wheel stays. The policies stay. The terminal view stays. It has no Swing bar. A press does not take the caret. The bar is not a tab stop.
**semantic-key:** PK-856

## Wider bar

Keeps the thin pill at 2. Face keeps the pill on the content. Widens the popped-out bar to 15. counts 15 and 2. stays sheathed.

## The VT100 bar

Java is named. The VT100 panes still have the old style scrollbars. Every pane in the VT100 tab uses the Apple bar.

The VT100 terminal lays its own scroll strip. That strip was the old bar. Every new VT100 pane builds a widget, and that widget arms the strip with the Apple bar. A fresh session builds a fresh widget. The thinking strip on this tab already uses the Apple bar. It stays. The mood glass stays on its own bar. It is not a VT100 pane.

The strip is 15 pixels, the popped-out width. The terminal layout sizes the strip from the preferred width, so that preferred width is 15. The scroll panes keep a preferred thickness of zero. The thin pill stays 2. The colors, the opacities, the fade, and the corner radius stay. The strip rests on the plate. The pointer on the pane shows the thin pill. The pointer on the strip pops the bar out. The wheel stays. The policies stay. A press does not take the caret. The bar is not a tab stop. The old track painted find ticks. The Apple bar does not paint those ticks. The find highlight in the terminal stays.
**semantic-key:** PK-857

## The VT100 bar

Keeps the wheel. Face keeps the pill. Arms the terminal strip. Counts 15. stays sheathed.

## Rounded cell

Java is named. The cells in the Aetheris Java canvas take the rounded corners of the Rust Aetheris cells. The cell as a whole, the input pane, and the reply pane. The selection and the paint stay.

The cell corner is a round of 6. The stroke is 1 pixel and sits inside the cell. The color stays the color the cell already paints. An armed cell, and a card lit with its merge, is sapphire. A pin match is green. A pin miss is dim. A ghost is dark gray. A quiet cell is the quiet gray. The pad inside the stroke stays 6. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons, the prompt, and the reply keep their size.

The prompt and the reply use that same round of 6. The prompt line stays green and 2 pixels. A reply that has text stays green and 2 pixels. A reply with no text stays gray and 2 pixels. The line sits inside the pane. The pane clips its children to the round so the plate follows the stroke. The field stays the field. The Apple bar stays.

Rust paints the cell with this round of 6 and this 1 pixel inside stroke. egui 0.37 paints the panes with the inactive corner, and that corner is 6. The inactive stroke width is 0. Java keeps the green and the gray on the panes. Those are the paint mechanics the panes already use. This row rounds the corner.

A collapsed card rounds the cell. It has no panes. A merge sink has no prompt pane. Its reply rounds. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays.

No new package. No new public type. No new public method. The private method is roundEdge. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the selection colors. Face keeps the caption on the stroke. Rounds the cell and the panes. Counts 6 and 1 and 2. stays sheathed.

## Round field

Java is named. The text pane cells still look more square than what Rust renders. The roundness of that edge matches the Rust render.

The dark field on the prompt and on the reply is a round of 8. That is the Rust corner of 6 points at the open zoom of 1.3, taken to the nearest pixel. The line stays 2 pixels. It follows that same curve. The outer stroke radius is 10, so the inside edge meets the field. The prompt line stays green. A reply that has text stays green. A reply with no text stays gray. The field stays the field. The pane keeps its size. The Apple bar stays a sibling of the field.

The cell stays a round of 6. The cell stroke stays 1 pixel. The pad inside that stroke stays 6. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons keep their size.

Rust fills the plate at the inactive corner of 6 and scales it by the open zoom. The inactive stroke width is 0. Java keeps the green and the gray on the panes.

A collapsed card has no panes. A merge sink has no prompt pane. Its reply takes this field round. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays.

No new package. No new public type. No new public method. The private method stays roundEdge. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the green and the gray. Face keeps the field color. Rounds the field. Counts 8 and 2. stays sheathed.

## Straight bevel

Java is named. The straight bevel still sits on the text panes. This plate clears that line.

The prompt and the reply sit in one split. The split UI puts a straight bevel back on after the border is cleared. This plate clears that border once the UI is on. The straight line leaves. The round field stays a round of 8. The line stays 2 pixels and follows that curve. The prompt line stays green. A reply that has text stays green. A reply with no text stays gray. The field stays the field. The pane keeps its size. The Apple bar stays. The grab bar stays.

The cell stays a round of 6. The cell stroke stays 1 pixel. The pad inside that stroke stays 6. The caption stays on the top stroke. The caption inset stays 8. There is no north pad. The cell keeps its width. The buttons keep their size. The canvas split stays. A terminal split stays.

A collapsed card has no panes. A merge sink has no prompt pane. Its reply keeps the round field. The error slide stays square. The footer stays. The mood glass stays. The thinking strip stays.

Keeps the canvas split and a terminal split. Face keeps the round field. Clears the border after the UI is on. Counts one clear.

## Dark bevel

Java is named. Recommends the dark shadow. The bright lip leaves. The cell panes go black. The ink stays.

Recommends the dark shadow. The bright lip sits on the button bevel, on Yes and No, on the one-pixel line around P and C and the thinking clear mark, on the grab-bar outline, and on the split bevel the look and feel puts back. The lip is 0x242424. The shadow is black. The face stays. The word stays.

The frame, the split, and the status go to 0x0c0c0c when they are still the old default. The panel goes to 0x121212 when it is still the old default. A ground he already changed stays. The button face stays 0x3c3c3c. A form field stays 0x1a1a1a. The cell panes go black. The round field stays a round of 8. The green line and the gray line stay. The cell split stays clear. The grab bar stays. Its outline is the black shadow.

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The cell stays a round of 6. The Apple bar stays.

No new package. No new public type. No new public method. The private method is darkGround. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the ink and the selection. Face keeps the neon lines. Seats the dark lip. Names the shadow. Counts four grounds.

## Quiet bevel

Java is named. Recommends the quiet grey. The bright lip drops under the panel. The black ground stays under it. The ink stays.

Recommends the quiet grey. The bevel highlight was 0x242424. That lip sits over the panel. The panel ground is 0x121212. The black ground is 0x000000. The lip moves to 0x0a0a0a. Ten is darker than the panel and lighter than the black. The shadow stays black. The button face stays. The cell panes stay black. The grab bar keeps its seat. Its fill is that grey. Its outline stays black.

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The green line and the gray line stay. The cell split stays clear. The round field stays a round of 8. The cell stays a round of 6. The Apple bar stays. Yes and No stay. P and C stay. The thinking clear mark stays.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the ink and the selection. Face keeps the neon lines. Seats the quiet lip. Names the grey.

## Quiet edge

Java is named. The Ocean edges take the quiet grey. The ink stays.

The light Ocean edges take the quiet grey. The grey is 0x0a0a0a. Ten is darker than the panel at 0x121212 and lighter than the black at 0x000000. It is the lip the bevel already wears. The selected tab face is the panel charcoal. An unselected tab, the tab strip, and the content frame are the quiet grey. The tab shadow stays black, so the frame still reads. A scroll edge, a combo frame, a spinner edge, the menu-bar line, an open menu frame, a check box, a find field, and a dialog frame use that grey. The file chooser frame uses it. The button face stays. A form field stays. The cell rounds stay.
**semantic-key:** PK-858
**semantic-key:** PK-858

## Quiet edge

Keeps the ink and the selection. Face keeps the neon lines. Seats the quiet edge. Names the same grey. Counts the Ocean seats.

## Short bar

Java is named. The cell bar takes the quiet grey. The bar is ten pixels shorter. The panes keep their width. The arrows stay.

The light line on each edge of the cell bar takes the quiet grey. The grey is 0x0a0a0a. Ten is darker than the panel at 0x121212 and lighter than the black at 0x000000. The bar is ten pixels shorter than the panes, five on each side. The panes keep their width. The split stays a split. The divider thickness stays 11. The one-touch arrows stay. Drag stays. The other split bars stay. The cell rounds stay. The cell split stays clear.

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The green line and the gray line stay. The round field stays a round of 8. The cell stays a round of 6. The Apple bar stays. Yes and No stay. P and C stay. The thinking clear mark stays.

## Short bar

Keeps the panes and the arrows. Face keeps the neon lines. Seats the short bar. Names the same grey. Counts ten pixels.

## Folded row

Java is named. The folded thinking row holds the fold, C, and Close, the inspector folds on its own bar with P on that bar, and the SSH, Aetheris, and Canvas tabs wear the plate.

A folded thinking strip keeps one row. Left to right, the fold mark, the drag line, C, and Close. The line is the grip ink from the mark to C. A drag while the strip is folded stores the height. The row stays one row. An open strip keeps the mark and Close on the text row, and C on the east of the grip. The notebook strip and the VT100 strip both do this.

The canvas and the inspector sit in a vertical fold. The bar is 11 pixels. The fill is 0x3a3a3a. The edge is 0x8a8a8a. The upper arrow hides the canvas. The lower arrow hides the inspector. A click on the hidden side restores the parked share. P sits on the top of that bar and moves with a drag. The pin tip wears the Rust sentence. The facts line stays where it sits. The per-mille save stays.

The SSH tab, the Aetheris tab, and the Canvas tab wear the plate. Selected is 0x005c80 with word 0xc0deff. Idle is 0x323232 with word 0xd7d7d7. Hover is 0x404040 with stroke 0x969696 and word 0xf5f5f5. The corner is 6. The strip is 0x0d0d0d. The content frame stays off. A disabled tab wears half ink. The folded SSH and Aetheris buttons wear the same plate. The words stay SSH, Aetheris, and Canvas.
**semantic-key:** PK-859

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The green line and the gray line stay. The cell split stays clear. The round field stays a round of 8. The cell stays a round of 6. The Apple bar stays. The cell bar stays the short quiet bar. The other split bars stay. Yes and No stay. P and C stay. The thinking clear mark stays. The pin box, the field, Clear, and OK stay. The facts line stays. The Ocean edges stay. The bevel stays.

## Folded row

No new package. No new public type. The public methods are installPlateTabs and installPlateButton. The private classes are InspectorFold, InspectorBar, PlateTabUi, and PlateButtonUi. The private methods are seatFoldedRow, detach, seatPin, dragBy, hideCanvas, hideInspector, placePin, and noteDividerMoved. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.
**semantic-key:** PK-765
**semantic-key:** PK-765
**semantic-key:** PK-765
**semantic-key:** PK-765

Keeps the inspector and the app. Face keeps the neon lines. Seats the bar and the row. Names the plate. Counts the arrows.

## Button word

Java is named. Rust is named. A button word turns emerald while the pointer is on that button, and the word returns to the color that button already had when the pointer leaves. The tooltip waits 50 ms longer.
**semantic-key:** PK-860

A button word turns emerald while the pointer is on that button, and the word returns to the color that button already had when the pointer leaves. A pool turn was not sent. A live SSH connect was not made. The crate follows.

No Session::start. No chrome::run. The card-round hunks stay unstaged.

The word:

A button word turns #00FF66 while the pointer is on that button. The leave restores the color that button already had. The shift is immediate. The tooltip wait grows by 50 ms. Swing goes from 750 ms to 800 ms. The crate goes from 0.50 s to 0.55 s. The word does not wait on that timer.

The plate fill stays. The plate stroke stays. P and C rest white. Yes rests black. No rests white. A selectable tab keeps its word. A menu title keeps its word. The Connected chip stays. An arrow mark has no word. File, Tools, and Help stay.

## Button word

Rust moves second. Keeps the rest colors. Face keeps the fills. Seats the listener and the button theme. Names the emerald. Counts the 50 ms.

## Open thought

Java is named. The thinking text has no border. The white line around that pane is gone.

The text:

The thinking text has no border. The white line around that pane is gone. The scroll frame drops after the Apple bar is seated. The viewport border drops. The viewport ground is the field, 0x1a1a1a. The text margin stays. The notebook strip and the VT100 strip both do this. Rust already paints that text with no frame.

The grip stays. The fold stays. The folded row stays. Close stays. C stays. The black line on C stays. The cell panes stay. The Apple bar stays. The round field stays. Font colors stay. Selection highlights stay. The emerald word stays. The tooltip wait stays.

Keeps the grip and the buttons. Face keeps the field. Clears the scroll frame. Counts one line.

## Tab rule

Java is named. Rust is named. A divider sits between Connect and the SSH tab. The two tab buttons stand apart.

A pool turn was not sent. A live SSH connect was not made. The crate follows.

The line:

A divider sits between Connect and the SSH tab. The two tab buttons stand apart. The open bar carries the line. The folded row stays as it is. The folded rule stays on the menu, after Aetheris and before File, Tools, and Help.

Java seats a one-pixel panel on the south of the open head. The ink is the panel foreground. The Java default of that ink is #dcdcdc. The live theme may already hold another panel foreground. This plate reads no home file and adds no color key. The line leaves with the open bar.

Rust calls separator() after the door note and before the SSH tab. The parent column is vertical, so the line is horizontal. That is the same call the folded menu divider uses.

The menu stays. The words stay SSH and Aetheris. The Aetheris tab stays disabled until the mux answers. Connect stays the first button of the open row. The grip stays. The thinking text stays clear. The emerald word stays. The tooltip wait stays.
**semantic-key:** PK-861

No new package. No new public type. No new public method. The private field is tabRule. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the folded row. Face keeps the tabs. Seats the line. Names the tab rule. Counts one pixel.

## Help seat

Java is named. Help sits on the main menu bar.

The seat:

Help sits on the main menu bar. The order stays File, Tools, Token Calc, and Help. Token Calc is a menu item. That item was taking the remaining width, so Help sat past the edge. The item maximum size is its preferred size. Help stays on the bar. Help... and About Aetheris... stay under Help. Show Thinking stays under Tools. Token Calc still opens the token window.

The folded row stays. The open divider stays. The words stay SSH and Aetheris.

Keeps File and Tools. Face keeps Token Calc. Caps the item so Help sits on the bar. Names the help seat. Counts four names.

## Menu word

Rust is named. The main menu words wear the Java menu green.

A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run.

The main menu words wear the Java menu green. Swing paints that bar with #00ff41. File, Tools, Token Calc, and Help wear that green. The lines under File, Tools, and Help wear it too. The word stays that green while the pointer is on it. Token Calc still opens the token window. Show Thinking stays under Tools. Help... and About Aetheris... stay under Help. The order stays File, Tools, Token Calc, and Help.

The folded row stays. The open divider stays. The words stay SSH and Aetheris. Context menus stay. The button hover emerald stays #00FF66. The tooltip wait stays.

No new package. No new public type. No new public method. The private function is paint_menu_ink. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the context menus. Face keeps File, Tools, Token Calc, and Help. Seats the green. Names the menu word. Counts #00ff41. stays sheathed.

He named a and said to file it as a Feature. Transfer Conversation copies the Roux session onto the canvas and the canvas back into the Roux session. Java is named. Rust is named.

A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run. The card-round hunks stay unstaged.

The menu title is Transfer Conversation. On the canvas it sits after Duplicate group, on the prompt side and on the reply side. On the VT100 menu it sits after Import and before Select model. OK on the console clears the canvas and copies the Roux user and assistant turns onto the cells. OK on the canvas clears the Roux turns and copies the canvas as input and reply rows. Cancel writes nothing and clears nothing. The configured prompt stays. A running canvas cell refuses the transfer and clears nothing.

No new package. No new public type. The public methods are parseMessageTable, pairConversation, conversationTable, replaceTurns, applySessionReplace, replaceResult, replaceCanvas, and transferFromConsole. No new vault column. No new channel. No new AMWP type. No new library. No yaml edit. No Cargo.toml edit. The card-round hunks stay unstaged. This sentence does not open an F row.

Keeps the Roux door. Face keeps the confirm. Seats the cells. Names the menu. Counts the pairs.

## About box

Java is named. Rust is named. The About box names Lambert, the copyright, his email, and the MIT license, and it shows the app icon large beside the branded icon.

The About box names the author Lambert. Copyright line is Copyright (c) 2026 Lambert <>. The license line is Licensed under the MIT License. The shell lines stay above those three lines. The app icon is the Eye of Horus from /icons/aetheris.jpg at 128 pixels. The branded icon is the casserole from /icons/casserole.jpg at 96 pixels. The app icon is the larger mark. The frame icon stays the Eye of Horus. Help... stays. About Aetheris... stays under Help. The title stays About Aetheris. The order stays File, Tools, Token Calc, and Help.

Java seats the two marks on the north of the dialog and the words under them. The private method is aboutMark. A missing file leaves that seat empty. The dialog is plain.

Rust loads the same two files on the first open. The private functions are about_app_rgba, about_brand_rgba, and seat_about_mark. decode_icon_rgba takes the edge. The app mark is drawn first. The brand mark is drawn beside it. The frame path still asks for 64.

No new package. No new public type. No new public method. The private method is aboutMark. The private functions are about_app_rgba, about_brand_rgba, and seat_about_mark. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the frame icon. Face keeps Help. Seats the two marks. Names the author. Counts 128 and 96. stays sheathed.

## Button word

Java is named. OK buttons in modals, including the Token Calc modal, and the v fold toggle, show their specified text.

OK buttons in modals, including the Token Calc modal, and the v fold toggle, show their specified text. The Token Calc OK stays OK. The pin OK stays OK. The fold mark stays v while open and ^ while folded. The thinking fold stays v while open and ^ while folded. The tool fold and the group fold stay v and ^. The connection bar mark stays v and ^. A label of one or two characters takes two ems after the border is on. A longer label keeps the short-fit width. Approve still grows by three ems after that fit. Yes and No stay on their own floor. The height stays the short fit when the word fits, and rises when the short fit is under the word. The minimum size is that floor. The short fit no longer replaces the label with an ellipsis. The label stays the specified text.

## Button word

The private method is holdShortLabel on AetherisNotebookPanel, on ThinkStrip, and on FoldingPanel. The floor runs again after the mark border and when the thinking fold changes its letter.
**semantic-key:** PK-766
**semantic-key:** PK-766

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the longer labels. Face keeps Approve. Floors the short word. Names the button word. Counts two ems.

## Pin above the divider

Java is named. The P button stays painted on top of the inspector divider.

## Pin above the divider

Keeps the arrows. Face keeps the letter. Paints the mark after the bar. Names the pin. Counts z-order 0. stays sheathed.

## Thinking face

Java is named. The thinking pane output matches the app font, the BB1 and BB2 buttons grow with that font, and the thinking body wears the lighter sapphire.

The thinking pane output matches the app font, the BB1 and BB2 buttons grow with that font, and the thinking body wears the lighter sapphire. The output takes the app point size. A change of that size writes the face onto text already in the pane and onto the next run. The three-line height follows the size. The body is selection sapphire lightened 33 percent toward white. The bytes are 115, 174, and 255. The notebook strip and the VT100 strip both wear it. The model tag stays cyan. Card replies stay yellow. BB1 holds Run, Stop, New turn, Clear turns, Preview, the row count, Delete, Collapse All, Expand All, and the tool fold. BB2 holds Search, Export, Import, Pull, To terminal, Mood, Hide Collapsed, and Show Collapsed. Those buttons keep the app point size. Their height is the preferred height at that size. Other short buttons stay on the short fit.

The private methods are seatBarButton and seatFace. setBodyFont stays the method that seats the face.

Keeps the other short buttons. Face keeps the cyan tag. Seats the face. Names the sapphire. Counts 115, 174, and 255. stays sheathed.

## Mark hover

Java is named. Rust is named. The I, E, P, and O buttons take the green hover.

The I, E, P, and O buttons take the green hover. A word on one of those buttons turns emerald while the pointer is on it, and the word returns to the color that button already had when the pointer leaves. The rest color stays. The letters stay I, E, P, and O. Included, Excluded, Input only, and Output only stay the tips. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. Tabs stay. Menu titles stay. Check boxes stay.

Java seats the same listener on those four toggles. A check box stays off that seat. Rust seats the four marks on the same emerald. A selected mark still takes the emerald while the pointer is on it. The private function is mark_button.

Keeps the check boxes. Face keeps the letters. Seats the marks. Names the emerald.

## Input pane

Rust is named. The input pane font follows the control-bar font size.

The input pane font follows the control-bar font size. The plus and minus on the control bar still step that size. The input text uses that size. The input window grows with that size. A caller with no control-bar size keeps the mono face. The line floor stays 16. Replies stay on the size they already follow. The desk line stays. The desk draft stays. Dialogs stay. The letters stay I, E, P, and O. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. The hover emerald stays #00FF66.

The private function is pane_font.

Keeps the replies. Face keeps the control bar. Seats the input face. Names the pane. Counts the row window.

## Default font

Rust is named.

The default font size is 14. A missing key reads 14. An unreadable key reads 14. A memory door reads 14. A stored size stays as written. The range stays 8 through 36. The Java default stays 8. The plus and minus stay. The letters stay I, E, P, and O. The pin letter stays P. The divider stays 11 pixels. The arrows stay. The tooltip wait stays. The hover emerald stays #00FF66.

The private constant is FONT_DEFAULT.

Keeps a stored size. Face keeps the range. Seats the missing key. Names 14. counts the clamp.

## Dark line

Java is named. The line under the control bar and the divider edges take the dark split ground.

## Dark line

No new private method.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands.

Keeps the arrows. Face keeps the 11 pixels. Seats the ground. Names the line. Counts the border.

## Bar press

Java and Rust are named. A press on a pulled-out scroll bar leaves the wheel on the master canvas.

A press on a pulled-out scroll bar leaves the wheel on the master canvas. A touch and a drag both do. Java returns focus to the canvas scroller. Rust leaves the wheel on the canvas. A press on the text still focuses that text. The bar still drags. The strip is the scroll bar width, on the right. These panes scroll vertically.

The private method is barHit. The private function is bar_press.

Keeps the text press. Face keeps the drag. Seats the bar. Names the canvas. Counts the strip.

## Title bar

Java is named. The window title bar matches the Rust window title bar.

A pool turn was not sent. A live SSH connect was not made. The card-round hunks stay unstaged.

The window title bar matches the Rust window title bar. Immersive dark mode stays on. Caption, border, and title text take the system default. That is the dark caption the Rust window leaves. The caption words stay. No painted hex.

No new private method. paintCaption and applyCaption write the system default. colorRef stays.

Keeps the caption words. Face keeps immersive dark. Seats the system default. Names the dark caption. Counts the attributes.

## Rust marks

Java is named. The P and C buttons match the Rust color and style on the canvas and on the VT100 tab.

The P and C buttons match the Rust color and style on the canvas and on the VT100 tab. P is sapphire while the pin is off and green while the pin is on. C is sapphire. Both wear a one-pixel white stroke and a corner radius of 2. The letter stays white. P is at least 22 by 22. C is at least 22 by 16. The hover emerald stays. T stays. The cell-state P stays.

The private method is paintRustPin. rustMarkEdge, seatRustBox, and ThinkStrip.paintRustClear stay package-private. paintMarkChoice stays.

Face keeps the cell-state P. Seats the white stroke. Names the corner. Counts 22. stays sheathed.

## Control flow

Java is named. The master control bar flows like the Rust control bar, and the button bar sits on its own row under the host elements.

The master control bar flows like the Rust control bar, and the button bar sits on its own row under the host elements. The host row is Mode, Host, Port, Username, Password, Remember password, and Font size. The button row is Connect, Disconnect, Reconnect, Clear Terminal, Send file..., New Term, and Close Pane. The rows flow left and wrap. The fold mark stays west. The Mode combo stays.

The private class is FlowRow. The private method is flowLabel.

Keeps the Mode combo. Face keeps the fold mark west. Seats the host row. Names the button row. Counts two rows.

## Button row

Rust is named. The button bar sits on its own row under the host control elements.

The button bar sits on its own row under the host control elements. The host row keeps the mark, the fields, Remember, Font size, and the link status. The second row holds Connect, Disconnect, Reconnect, Clear Terminal, Send file, New Term, and Close Pane. The show tip names that row.

Keeps the host row. Face keeps the link status. Seats the second row. Names the tip. Counts seven buttons.

## Default size

Java is named.

The default app font size is 20. A stored size stays. A missing or unreadable key is 20. The range stays 8 through 36. Rust FONT_DEFAULT stays 14.

Keeps a stored size. Face keeps the range. Seats 20. names the missing key. Counts 8 through 36. stays sheathed.

## Button face

Java is named. Unconverted buttons take the Rust tab face. Button text colors stay through focus, hover, press, and disable. On the button text colors across focus, hover, press, and disable.

Unconverted buttons take the Rust tab face. A pool turn was not sent. A live SSH connect was not made. The card-round hunks stay unstaged.

Unconverted buttons take the Rust tab face. Button text colors stay through focus, hover, press, and disable. Idle fill is the tab gray with no stroke. Hover takes the hover fill and a one-pixel hover edge. Press fills #4a4a4a. The word stays the button foreground, including the emerald hover. A disabled word is that foreground at half alpha. The corner radius is 12. Plate tabs, folded plate words, rust marks, mark letters, choice faces, arrows, and the hidden zero-size bar buttons stay.

## Button face

Keeps the emerald hover. Face keeps the choice fills. Seats the gray face. Names the edge. Counts radius 12. stays sheathed.

## Black ground

Java and Rust are both named. The About box drops the casserole image. The Eye is half the size, the art stays, and the ground is black with square corners. The image art.

The About box drops the casserole image. A pool turn was not sent. A live SSH connect was not made. The card-round hunks stay unstaged.

The About box drops the casserole image. The Eye is half the size, the art stays, and the ground is black with square corners. The About edge is 64. Light dull corners flood to black. Saturated eye pixels stay. The corners stay square. The jpg stays. The window icon stays.

The private methods are iconGround and fillIconGround. The private functions are icon_ground, fill_icon_ground, and about_app_rgba.

Keeps the jpg. Face keeps the window icon. Floods the corners. Names the square. Counts 64. stays sheathed.

## Bold turn

Rust is named. The turn text on a canvas cell is bold.

The turn text on a canvas cell is bold. The label is strong, including the ghost color path. The merge label stays. The view window title stays. The mark letters stay.

Keeps the merge label. Face keeps the mark letters. Seats the weight. Names the ghost path. Counts one label.

## Narrower calc

Java and Rust are both named. The Token Calc window is 20 percent narrower.

The Token Calc window is 20 percent narrower. Java sets the width to four fifths of the packed width and keeps the packed height. Rust default size is 512 by 420. The ring stays 220. The title stays Token window.

Keeps the height. Face keeps the ring. Seats 512. names the title. Counts four fifths.

## About layer

Java and Rust are both named. While the About box is active it paints above every other component.

While the About box is active it paints above every other component. Java paints the About dialog always on top and modal, and brings it forward when the dialog opens. Rust paints the About window on the foreground layer and moves that layer to the top while the box is open. The Help window stays. The Eye stays.

Keeps the Help window. Face keeps the Eye. Seats the foreground layer. Names the top. Counts one dialog.

## Opening frame

Java is named. The first start centers the shell at a reasonable size.

The first start centers the shell at a reasonable size. A saved rectangle that meets a screen stays. A missing origin, and a saved rectangle that misses every screen, seats 1200 by 760, capped to the usable screen, and centered. The floor is 640 by 480 when the screen allows. The size never exceeds the usable screen. A later resize still saves. The private methods are seatOpeningFrame and usableScreen.

Keeps a saved on-screen rectangle. Face keeps the later resize. Seats the usable screen. Names the center. Counts 1200 by 760. stays sheathed.

## Dummy key

The publish spec is named. When the Grok API key is scrubbed, the public yaml keeps the key and a dummy value.

When the Grok API key is scrubbed, the public yaml keeps the key and a dummy value. A pool turn was not sent. A live SSH connect was not made. A push was not run.

When the Grok API key is scrubbed, the public yaml keeps casserole.grok.api-key and the dummy your-grok-api-key. The key is not removed. A person finds that line and replaces the dummy. The password stays empty. Sections 33 and 73 stay. A push waits.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps sections 33 and 73. Face keeps the password empty. Seats the dummy. Names the key. Counts one line.

## Branch push

The publish spec is named. A BRANCH-PUSH sends the current external branch to GitHub, and a push of main still needs a, and APPROVE, and.

A BRANCH-PUSH sends the current external branch to GitHub, and a push of main still needs a, and APPROVE, and. A pool turn was not sent. A live SSH connect was not made. No push. No merge.

A BRANCH-PUSH sends the current external branch to GitHub as that branch. Push needs a and. It does not need APPROVE. It is not a merge into main. A push of main still needs a, and APPROVE, and. This plate does not push. This plate does not merge.

Keeps the main gate. Face keeps APPROVE on main. Seats the branch. Names the sanity pass. Counts two marks.

## Shorter bar

Java is named. BB1 and BB2 button height is 2 pixels under the preferred height.

BB1 and BB2 button height is 2 pixels under the preferred height. The bar buttons keep the app point size. The height is the preferred height, then minus 2, then the floor at 1. Other short buttons stay on the 70 percent path. The font does not shrink.

Keeps the app point size. Face keeps the other short buttons. Seats the minus 2. names the shorter bar.

## Visible letters

Java is named. P, C, and T show their letters at the Rust pin size.

## Visible letters

Keeps the cell-state marks. Face keeps the white letter. Seats the fill under the letter. Names the pin size. Counts 22 by 22. stays sheathed.

## Master bar

Java is named. Rust is named. The font control sizes every label, field, and button on the master control bar.

A pool turn was not sent. A live SSH connect was not made. The crate follows. The card-round hunks stay unstaged.

The font control sizes every label, field, and button on the master control bar. The panes already take that size. The first layout uses the stored size. The range stays 8 through 36. Java default stays 20. Rust default stays 14. applyBarFont is package-private. seatBarFont is private. seat_bar_font is private. The menus stay.

Keeps the panes. Face keeps the menus. Seats the bar. Names the size. Counts 8 through 36. stays sheathed.

## Locked sapphire

Rust is named. The thinking body stays the lighter sapphire in Java and in Rust. A later plate does not retint a locked color or resize a locked control unless the sentence names that element.

The thinking body stays the lighter sapphire in Java and in Rust. A later plate does not retint a locked color or resize a locked control unless the sentence names that element. Java already wears bytes 115, 174, and 255 on both strips. Rust THINK_BODY paints those bytes. The yellow body name is gone. Card replies stay yellow. Button sizes stay. The menus stay.

Keeps the card yellow. Face keeps the lighter sapphire. Seats the body. Names the lock. Counts 115, 174, 255. stays sheathed.

## Capital mark

Java is named. Rust is named. The about box writes a capital C in the copyright mark.

The about box writes a capital C in the copyright mark. The about box in Java and the about body in Rust write the capital C. The source-file headers stay. The license line stays.

Keeps the source headers. Face keeps the license line. Seats the mark. Names the capital C. Counts one letter.

## Sit card

The resume checkpoint matches the tree and the publish state, and a new session sits on that card.

The resume checkpoint matches the tree and the publish state, and a new session sits on that card. A pool turn was not sent. A live SSH connect was not made. No fetch. No push.

The resume checkpoint matches the tree and the publish state, and a new session sits on that card. STAGE stays 1 / BUILT. A push of main still needs a, and APPROVE, and. A BRANCH-PUSH still needs a and. The protected dirty paths stay unstaged. The two untracked files stay untracked.

Keeps the fence. Face keeps the old checkpoints. Seats the sit. Names the card. Counts the desk.

## Folded line

Rust is named. When the thinking pane is closed in the Aetheris canvas, and the control line for the thinking pane is painted, with the C and Close on the same line as the drag bar, the C button is being clipped by the BB1 button bar. Raise the collapsed thinking bar control line up on the y axis of the canvas by enough pixels for the C button to not be clipped by at least 2px. Shop clock fell back to Mountain Standard Time.

When the thinking pane is closed in the Aetheris canvas, and the control line for the thinking pane is painted, with the C and Close on the same line as the drag bar, the C button is being clipped by the BB1 button bar. Raise the collapsed thinking bar control line up on the y axis of the canvas by enough pixels for the C button to not be clipped by at least 2px. The folded canvas line lifts 4px. The band grows upward by that lift. The open strip stays on the buttons. The VT path stays. C stays the color it already has.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The 4px lift is the named raise. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the open strip. Face keeps the C. Seats the lift. Names the clearance. Counts 4 pixels and at least 2 clear.

## Menu tab

Java is named. When the pointer is over a main menu item, the background shifts to a lighter grey tab button shape. Mirror that main menu look and feel on hover in the Java Aetheris app, for both open and collapsed and shifted menu shapes.

When the pointer is over a main menu item, the background shifts to a lighter grey tab button shape. Mirror that main menu look and feel on hover in the Java Aetheris app, for both open and collapsed and shifted menu shapes. The tab is gray 70 with corner 6. Idle stays clear. The same bar sits open, collapsed, and shifted. Token Calc takes the same tab. The menu words stay the menu green.

No new package. No new public type. applyMenuBar is a new public method on the existing theme. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The hover tab is the named look. This land leaves the working set as it stands. The card-round hunks stay unstaged.
**semantic-key:** PK-862

Keeps the menu green. Face keeps the idle bar. Seats the tab. Names the lighter grey. Counts gray 70 and corner 6. stays sheathed.

## Button pad

Java is named. The button bar on the bottom is being clipped by the joint between the panes. Add padding to the bottom of the control bar pane, the folding panel containing it, so the bottoms of the buttons clear by at least 2px.

The button bar on the bottom is being clipped by the joint between the panes. A pool turn was not sent. A live SSH connect was not made.

The button bar on the bottom is being clipped by the joint between the panes. Add padding to the bottom of the control bar pane, the folding panel containing it, so the bottoms of the buttons clear by at least 2px. The connection panel inside the folding panel takes 4px of empty border on the bottom. The button faces stay.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The 4px bottom pad is the named clearance. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the button faces. Face keeps the menu. Seats the pad. Names the clearance. Counts 4 pixels and at least 2 clear.

## Canvas lip

Java is named. That white line under the Canvas tab, the pane separating the Canvas tab and the scroll pane for the canvas cells, is a white highlight, a bevel or inset, that must be changed to the dark mode theme we defined.

That white line under the Canvas tab, the pane separating the Canvas tab and the scroll pane for the canvas cells, is a white highlight, a bevel or inset, that must be changed to the dark mode theme we defined. The scroll under that tab takes the quiet bevel after the Apple bar is seated. The lip is 0x0a0a0a. The shadow is black. The viewport border drops.

No new package. No new public type. applyCanvasScroll is a new public method on the existing theme. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The quiet bevel is the named change. This land leaves the working set as it stands. The card-round hunks stay unstaged.
**semantic-key:** PK-863

Keeps the cells. Face keeps the plate. Seats the lip. Names the quiet grey. Counts 0x0a0a0a over black.

## Canvas fill

Java is named. Flesh that out so it matches our dark theme. The scroll pane under the Canvas tab still paints the Metal control face. That fill takes the panel ground.

The scroll pane under the Canvas tab still paints the Metal control face. A pool turn was not sent. A live SSH connect was not made.

Flesh that out so it matches our dark theme. The canvas cell scroll paints the panel ground after the Apple bar is seated. The quiet bevel stays. The viewport border stays clear.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The panel ground on that scroll is the named change. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the bevel. Face keeps the plate. Seats the ground. Names the panel. Counts the Metal face off that pane.

## Duplicate group on the menu

Java is named. Rust is named. On a right click on a cell, the option to duplicate a group should ONLY show when the cell is a group/merge cell group, any cell in the group can activate it.

On a right click on a cell, the option to duplicate a group should ONLY show when the cell is a group/merge cell group, any cell in the group can activate it. A merge cell and any member of that group keep the line. A lone cell omits the line. Import stays. Transfer Conversation stays. Shift-Ctrl-G stays.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The menu line on a grouped cell is the named change. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the chord. Face keeps Import and Transfer. Seats the line. Names the group. Counts the lone cell clear of that line.

## Collapse all keeps the one-line card

Java is named. Rust is named. Something changed in behavior in both rust and java for collapse all and expand all. That should only toggle the collapse cell function which minimizes the cell to the one line display, the way it used to work before in Java. And when collapse all is clicked it simple collapses the cell view for all cells, NOT remove them from canvas view which is what is occurring presently. This got stomped on by something else. The lock guards that fold.

Collapse All and Expand All only fold each cell to the one-line card. They do not take a cell off the canvas. A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run.

Something changed in behavior in both rust and java for collapse all and expand all. That should only toggle the collapse cell function which minimizes the cell to the one line display, the way it used to work before in Java. And when collapse all is clicked it simple collapses the cell view for all cells, NOT remove them from canvas view which is what is occurring presently. The stomp is the hide-collapsed land. Rust `23dc517a` and Java `1bd6a102` omit a folded card while Hide is on. The seat lands `394cfdbc` and `1a629708` save that choice in `aetheris-hide.yaml`. The live file is `hide_collapsed: true`. Collapse All only sets the fold bit. The deck filter then takes every folded card off the canvas. Expand All clears the fold bit, so the cards return. The one-line brief is still the card.

The lock is `collapseKeepsTheCard` in Java and `collapse_keeps_the_card` in Rust. A later plate must not make that return false. A fold releases an id. It never adds one. Hide Collapsed is the only path that fills the conceal set. The deck omits a concealed id, and only while Hide is on. Collapse All writes Show, so the saved seat cannot take those cards off on the next open. Hide Collapsed stays the only click that writes Hide. Show Collapsed stays the click that writes Show from that button. Other canvases keep their fold bits. The window omission lifts with the Collapse All write, the same flag Show Collapsed already clears.

No new package. No new public type. No new public method. The package method is collapseKeepsTheCard. The private methods are armConceal, refillConceal, releaseFold, and cellsForDeck. The private functions are collapse_keeps_the_card, release_fold, conceal_collapsed, arm_conceal, and release_deck. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. One side move on this plate. Collapse All writes Show into aetheris-hide.yaml so the saved seat cannot omit the folded cards on the next open. The requirement is the lock. The sibling is Hide Collapsed. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps Hide Collapsed. Face keeps the brief. Seats the lock. Names the conceal set. Counts the fold bit off the omission.

## Token ring counts Included cells

Java is named. Aetheris is named. When cells are excluded, or restricted by output only etc, ONLY the active cells in a canvas session are factored into the Token Ring Calc algorithm. Active is Included. Excluded, input only, and output only stay out of the count.

When cells are excluded, or restricted by output only etc, ONLY the active cells in a canvas session are factored into the Token Ring Calc algorithm. Included is the active state. An Excluded cell stays out. An input-only cell stays out. An output-only cell stays out. A merge counts an Included member prompt and counts the merge reply only when that merge is Included. The inspector list stays. The mood line stays. It is not a cell. The Rust token ring stays.

No new package. No new public type. No new public method. The private methods are tokenActive, activeMemberPrompts, appendActiveMergeTokens, and appendActiveGroupTokens. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The ring membership is the named change. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the inspector. Face keeps the mood line. Seats the gate. Counts the restricted cells out.

## The VT100 menu moves the buffer

Rust is named. In the VT100 terminal pane, the paging and cursor control functions are enabled, as advertised in the context menu on a right click. Every buffer line stays lit. The hot-keys fire. Rust mirrors that Java menu.

The view was pinned to the tail on the frame after a page, so a page did not hold. A parked pane stored the motion and never applied it. Ctrl+Up, Ctrl+Down, Shift+Page Up, Shift+Page Down, and Ctrl+L were not strokes. The five buffer lines went dark on the alternate screen. The stick now follows the tail flag. A page leaves the tail until the view is on the tail again. A parked pane applies its waiting lines. The five buffer lines stay lit. Clear on the alternate screen still leaves that grid as it stands. Copy, Paste, and Open as URL keep their gates. The menu labels stay the thirteen strings. The chord sits on the hover. Bare Page is not a host byte. The term crate stays.
**semantic-key:** PK-864

No new package. No new public type. No new public method. The private function is console_menu_tip. The private stroke is Motion. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The menu motion is the named change. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the thirteen labels. Face keeps Copy, Paste, and Open as URL on their gates. Seats the stick and the chords. Names the hover. Counts the alternate clear as a no-op.

## Menu, the modal ink, and the search clear

Java is named. Rust is named. The global font control resizes the text of the main application menu, in the folded state and in the unfolded state. A modal button or control that paints a dark word takes the theme's light foreground. The BB2 search field has a red x snapped to its right. A click clears the search text and clears the find filter, and the hidden cells come back.

The global font control resizes the main menu in the folded state and in the unfolded state. A modal dark word takes the light foreground. The red x clears the search and the find filter. A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run. No side move on this plate.

One menu bar moves between the open seat and the folded seat. `applyMenuFont` sets that bar, and every row under it, to the font-control point size. The control calls it. The first seat calls it. Rust `paint_menu` seats the same point size on the bar and inside each menu. The folded path clears the bar font before the menu. The menu puts it back.

The Clear mood Yes word is black. It takes the panel foreground. The Rust Yes word takes INK. Option pane buttons take that same foreground. The pin x stays black. Menu green stays. Hover emerald stays. The thinking body stays.

The search field commits a find filter. A cell whose user text and assistant text both miss a case-insensitive contains stays off the canvas. An empty filter shows every cell the conceal set still allows. Search still selects the first hit and does not change a cell state. The red x is not a bar button. Its fill is the pin red. A click empties the field and the filter and rebuilds the deck. Hide stays the only path that adds a conceal id.

No new package. No new public type. No new public method. The package method is applyMenuFont. The private method is paintMenuFont. The private method is findHits. The private functions are find_hit and find_slots. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The menu size, the modal ink, and the search clear are the named change. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the cell states. Face keeps the pin x black. Seats the font and the filter. Names the light foreground. Counts the conceal set apart from the filter.

## Bar buttons sit two pixels shorter

Java is named. Size down the height of the BB1 and BB2 buttons by 2px.

A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run. No side move on this plate.

Bar buttons keep the font size. `fitAetherisButton` sets a bar button to the preferred height minus 4. The cut was minus 2. This plate replaces that cut. Buttons that are not bar buttons keep the 70 percent path. The red search x is not a bar button.

No new package. No new public type. No new public method. No new private method. The edit is the bar height in fitAetherisButton. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The bar height is the named change. This land leaves the working set as it stands. The card-round hunks stay unstaged.

Keeps the 70 percent path. Face keeps the search x off the bar path. Seats preferred minus 4. leaves the words. Counts one cut.

## About box shows every line

Java is named. The about box is truncating the text. Re-arrange the layout so all the text in the about box is rendered. Line wrap is off. The row count is the line count. The column count follows the widest line.

Line wrap is off. The row count is the line count. A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run. No side move on this plate.

`showAbout` turns line wrap off. ABOUT_TEXT splits on the newline. The row count is that line count. FontMetrics measures each line. The column count is the widest line in `m` widths, rounded up, plus one column. The copyright line stays one line. The words stay, including DarkJediTerm. Rust ABOUT_BODY stays.

No new package. No new public type. No new public method. No new private method. The edit is inside showAbout. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The About layout is the named change. This land leaves the working set as it stands. The card-round hunks stay unstaged. The banner stays unstaged. The connect path stays unstaged.

Face keeps the mark. Seats the rows and the columns. Keeps the words. Counts seven lines.

## SSH I/O logs the connect and the disconnect

Java is named. Add detailed log lines at the INFO level in Casserole, including user related meta-data, IP address, MAC, channel session duration for a disconnect, and other useful metadata for tracing and debugging that deems relevant, for ALL connect and disconnect events on the SSH I/O layer.

## SSH I/O logs the connect and the disconnect

The events are session-accept, session-open, authenticated, auth-reject, session-exception, session-disconnect, session-closed, channel-open, channel-open-failed, and channel-closed. A trace fault in the listener logs `event=trace-fault` and does not throw. Key exchange events stay quiet. Channel state changes stay quiet. Identification lines stay quiet.
**semantic-key:** PK-865

Every line carries event, session, user, remote-ip, remote-port, local-ip, local-port, client-version, server-version, iface-mac, and peer-mac. A missing value is `absent`. The user is the session name, and on auth-reject it is the offered name. `iface-mac` is the hardware address of the server interface that owns the local socket. A wildcard bind and a loopback with no hardware address stay `absent`. `peer-mac` stays `absent`. The SSH session does not carry the peer MAC. The clock starts at session-open and at channel init. session-closed and channel-closed carry duration-ms. session-disconnect carries the reason code, the reason text, the language, and initiator `local` or `peer`. session-closed repeats that disconnect tail. An exception line carries the exception class and a short text. A channel line carries channel-id, channel-recipient, channel-class, and channel-type when the TCPIP channel exposes it.

Keeps the listener. Face keeps the password off the line. Seats SshIoTrace. Keeps peer-mac absent. Counts the events.

## The Ollama count is a completion field

He likes a real token count as a replacement for the divide-by-4 guess, and he asks what Ollama REST requires. Ollama is running locally. This sentence names the investigation and the filing.

The heuristic stays. No product moves.

Stock Ollama 0.40.12 has no /api/tokenize. The actual input count is prompt_eval_count on the done frame of a generate or a chat, and that call runs the model. The heuristic stays.

Local Ollama is 0.40.12 at `http://127.0.0.1:11434`. The configured host `192.168.1.162` is 0.40.13. `POST /api/tokenize` returns 404. `POST /api/detokenize` returns 404. `POST /v1/tokenize` returns 404. The published docs at `https://docs.ollama.com/api/usage` name `prompt_eval_count` as the input-token count on the done frame of `/api/generate` and `/api/chat`. Spring AI `OllamaApi` has no tokenize method. The local tag list is `cwc-mistral-nemo-12b:latest`, `qwen2.5-coder:14b`, and `qwen2.5-coder:7b`. `deepseek-r1:7b` is named in the yaml and is not in that list.

A probe `POST /api/generate` with model `qwen2.5-coder:7b`, prompt `abcd`, `stream` false, `keep_alive` 0, and `options.num_predict` 1 returned `prompt_eval_count` 30 and `eval_count` 1. The done reason was `length`. The same call with `num_predict` 0 returned `prompt_eval_count` 30 and `eval_count` 25. Zero does not mean generate nothing on this server. Each call took about 19 seconds from an empty process list and `keep_alive` 0 left that list empty. The 30 is the rendered prompt, template included. The divide-by-4 guess for `abcd` is 1.

The heuristic stays the estimate. A status repaint does not call `/api/generate`. No new library. No new public type. No new public method. No new package. The yaml stays. This land leaves the working set as it stands.

Keeps the heuristic. Face keeps the generate off the status line. Seats the 404. keeps the template in the 30. counts 30 and 1. stays sheathed.

## Composer beat is the FLASH

He likes everything on the plate. Slide this slice into the oven and. Casserole first, then Java Aetheris, followed by Rust. This sentence names the beat and the build.

The product follows in three commits. Casserole first. Java Aetheris second. Rust third.

The beat is the FLASH. The track is 5 pixels. The needle is 2 by 9 in RING_HEAD on the true fill edge, standing 2 pixels above and below the track. An amber post at 70 percent and a red post at 90 percent stay drawn, each 1 by 9. A count change lunges the needle 6 pixels toward the gates and eases back to the true edge. Crossing a gate flashes RING_HEAD once and the fill takes that ink. The same flash is the beat when the settled model count replaces the heuristic. One flash if both happen together. est. tokens wears the fill ink. No new color.

The bar is the fraction of the context window. The scale is linear. est. tokens carries a small count. Red still means the real window is nearly full. The wrapper is per model. One stored tax does not travel. The keystroke path stays the heuristic. The model count is the correction after the text settles. prompt_eval_cached_count stays off the bar. eval_count stays off the bar. prompt_eval_count is the field.

## Composer beat is the FLASH

The dip is named. Java posts to `http://192.168.1.162:11434/api/generate` for model `deepseek-r1:7b` after the text settles and reads `prompt_eval_count`. num_predict is 1. stream is false. keep_alive is 300. The body is discarded. Debounce is 700 ms. A background daemon thread applies on the Swing thread. The paint path does not call. Text longer than 8000 characters skips the dip and keeps the heuristic. Empty text does not call. A non-200 or a missing field keeps the heuristic. A stale reply is dropped. The SSH plate polls the last screen line every 200 ms while the SSH tab is selected. The Aetheris product tab hides the frame heatbar and the word. A repeat of the same heuristic while a model count is held leaves the settled count. The GGUF for deepseek-r1:7b is on the ASUS. Rust has no HTTP crate. Rust paints the beat on the heuristic. Rust flashes when the heuristic crosses a gate. Java also flashes when the settled count replaces the heuristic and the band stays. That limit is the seat.

The package-private types are ComposerHeatbar and OllamaTokenDip in `org.casserole.app.ui.ssh`, and NotebookHeatbar and NotebookDip in `org.aetheris.swing`. The private methods are feedComposerHeat and notebookWindow. No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No lambdas. No Lombok. No JUnit. The bar carries a tooltip. It is not a new button.
**semantic-key:** PK-767
**semantic-key:** PK-767
**semantic-key:** PK-767
**semantic-key:** PK-767

The bake proof does not send a live POST. No pool turn. No live SSH connect. No Session::start. No chrome::run. This stamp has no product.

The dip, the 8000 character bound, the SSH poll timer, and the Rust heuristic limit are named on this row. No side move on this plate.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The Roux write path stays. The ring stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Java Aetheris moves second. Rust moves third. Keeps the heuristic. Face keeps the silk. Seats the dip. Keeps one flash. Counts the posts.

## Menu width follows the font

Java is named. The sizing of the font size on the menu bar works. The width also needs to be recalculated, as it is being truncated by a fixed width value in the layout. Make the width of the main file menu dynamic and ensure all menu items are always visible and accessible on font change updates.

The menu width follows the font. Every bar item and every popup row stays visible when the font changes. The frame grows when the menu is wider than the window. The 640 floor stays. A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run.

Token Calc on the bar took its preferred size once, at install, before the live font, and that size was stored as the maximum. A later font change grew the label inside that cap, so the title clipped. File, Tools, and Help already follow the preferred width. A popup keeps the size it had when it first opened, because a menu uses that size unless the width is 0. setPopupSize would lock the preferred size. This plate does not call it.

`fitMenuWidth` runs from `applyMenuFont` after the font is seated. Each bar item drops its minimum, preferred, and maximum, then takes the live preferred size. The maximum width is that width. The maximum height stays open so the item does not stretch across the bar and push Help off the edge. Each menu popup clears its preferred size and takes the new preferred size as its size, so every row stays on the glass, including Close Pane, Import, and About Aetheris. When the menu plus the folded controls that share its row are wider than the frame, the frame grows. The 640 floor stays. A smaller font does not shrink that floor.

The install path no longer stores the Token Calc cap. `applyMenuFont` owns the width. The font still follows the control in both seats.

No new package. No new public type. No new public method. The package method applyMenuFont stays. The private method is fitMenuWidth. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The menu width is the named change. The frame grow is the named way the items stay visible. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The heatbar stays. The ring stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps the 640 floor. Face keeps Help on the bar. Names the live width. Counts the popup rows.

## The Mood dialog opens on a readable cell

Java is named. The Mood modal is not displaying when the mood button is clicked. Isolate the root cause and nut the fix.

The Mood dialog opens on a readable cell. An armed card still wins. A missing arm uses the cell the canvas already targets. A blocked cloud stays closed. A missing AMWP connector stays closed. No side move on this plate. A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run.

The root cause is the arm. `openMood` called `findCell(focusedCellId)` and returned before `setVisible` when that id was null, when the cell was gone, or when the cell was not readable. `focusedCellId` is set on a card press and is never cleared. The only signal was the status word no cell. `targetCell` already falls through to the last cell on the showing canvas. Mood ignored that fall-through. The dialog method itself is the same path that last wrote the mood store. The button still calls `openMood`. The cloud gate and the connector gate were not the silent return.

`moodCard` keeps an armed readable cell. When the arm misses, it uses `targetCell` when that cell is readable, otherwise the last readable cell on the showing canvas. When no readable cell remains, the status still says no cell and the dialog stays closed. A blocked cloud still closes the dialog with the keyword only slide. A missing AMWP connector still closes the dialog with that same slide.

No new package. No new public type. No new public method. The private method is moodCard. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The mood card is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The heatbar stays. The menu width stays. The ring stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps the cloud gate. Face keeps the keyword only slide. Names the missing arm. Counts the readable cell.

## Heatbar estimate is the token ring count

Java and Rust are named. The est. tokens on the right of the heatmap bar shows the chat window memory, the same value the token ring modal shows. It does not show a count from the beginning of the session.

2202

The heatbar word shows the integer the token ring already shows. The Java notebook word is tokenEstimate. A settled host count does not replace it. The Java SSH word is the Roux tally. The client book is empty, so the bar and the ring read 0. The Rust Aetheris word is ring_used. The Rust SSH word stays session_memory_text. The 5 pixel track stays. A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run.

The heatbar word shows the integer the token ring already shows.
**semantic-key:** PK-1160
**semantic-key:** PK-1160
**semantic-key:** PK-1160

On the Java notebook the ring uses tokenEstimate. That integer is the heuristic of the counted working set. Included cells are in. Excluded, input only, and output only stay out. The dip may still ask the host. The settled prompt_eval_count does not replace the word.

On the Java SSH strip the ring uses the Roux tally for the focused user. The heatbar word uses that tally. The last screen line is not the word. The client does not put rows into that book. An empty book sums to 0. The word shows that 0.

On the Rust Aetheris plate the ring uses the inspector estimate. The heatbar word uses that estimate. The focused card prompt is not the word.

On the Rust SSH plate the ring and the heatbar already share session_memory_text. That shared count stays.

The track stays 5 pixels. The needle stays 2 by 9. The posts stay at 70 and 90. The lunge stays 6 pixels. One flash stays. The heuristic stays the fallback. Empty text stays 1. jtokkit stays off. No HTTP crate. No Cargo.toml edit.

No new package. No new public type. No new public method. The private field is ring_used. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The ring count is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Wants the heatbar count to match the token ring. Keeps the 5 pixel track. Face keeps the fill ink. Seats the ring count. Names the working set. Counts the same integer.

## The Mood Cancel button shows Cancel

Java is named. In the Mood modal, the Cancel text on the Cancel button is truncated. The Cancel button grows wide enough that Cancel is fully rendered.

The Mood Cancel button is wide enough for the word Cancel. Clear and Approve stay. The mood card stays. A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run.

After fitAetherisButton, Cancel preferred width and minimum width gain the same three ems Clear and Approve already take. The height stays the fitted height. The word stays Cancel.

Clear stays. Approve stays. The mood card stays. The cloud gate stays. The connector gate stays.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The Cancel width is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The heatbar count stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps Clear and Approve. Face keeps the word. Seats the three ems. Counts the width.

## Pull-down arrow wears the emerald

Java is named. Rust is named. Paint the pull-down arrow on the pull-down control in the emerald green. The arrow wears the existing hover emerald. The rest of the control stays.

The pull-down arrow wears the emerald. The rest of the control stays. No side move on this plate. A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run.

## Pull-down arrow wears the emerald

The field stays. The word stays. The pane arrows stay. The spinner stays.

No new package. No new public type. No new public method. The private method is seatComboArrow. The private function is emerald_combo_arrow. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The arrow ink is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The heatbar count stays. The Cancel width stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps the field. Face keeps the glyph. Seats the arrow. Names the emerald. Counts the sites.

## The SSH divider wears the lighter gray

Java is named. When multiple SSH panes are active, the divider drag bar wears the lighter gray the Rust VT100 bar already wears. The focused pane wears the cursor stroke. A single pane stays bare. The inspector split stays on the split ground.

The SSH divider wears the lighter gray. The focused pane wears the cursor stroke. No side move on this plate.

## The SSH divider wears the lighter gray

When more than one pane is open, the focused pane paints a 1 pixel inside stroke in 0x00D7FF. The private method is markPaneFocus. The package method is seatPaneFocus. Unfocused panes stay bare. A single pane stays bare.

No new package. No new public type. No new public method. The private method is markPaneFocus. The package method is seatPaneFocus. The private color is SSH_DIVIDER. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The SSH divider and the focused pane stroke are the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The heatbar count stays. The Cancel width stays. The arrow ink stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps the inspector split. Face keeps the cursor ink. Seats the stroke. Names the gray. Counts the panes.

## User pull-down sits on the east

Rust is named. The user pull-down sits on the east of the Aetheris footer, the way the Java canvas holds that column. Choosing a name switches the user.

The user pull-down sits on the east of the Aetheris footer. No side move on this plate.

The private function east_reserve holds the east width before the master buttons paint. That width is 160 plus the spinner when a card runs. The buttons paint in the width that remains. The pull-down stays on the outer east. The spinner stays just west of it.

The combo width stays 160. The ceiling stays 180. The arrow stays emerald.

No new package. No new public type. No new public method. The private function is east_reserve. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The east column is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The heatbar count stays. The Cancel width stays. The arrow ink stays. The SSH divider stays. The focused pane stroke stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps the 160 width. Face keeps the emerald arrow. Seats the column. Names the east. Counts the seats.

## Send file keeps the full word

Java is named. Rust is named. Send file keeps the full word. Every control that lacked a name now has one.

Send file keeps the full word. Every control that lacked a name now has one.
**semantic-key:** PK-1159
**semantic-key:** PK-1159
**semantic-key:** PK-1159
**semantic-key:** PK-1159
**semantic-key:** PK-1159
**semantic-key:** PK-1159

Java seatSendFileWidth adds three ems past the word. Java seatBarTips names the connection bar. Rust send_file_button does the same for the word. New and Clear name what they do. Group, Join, and Ungroup name what they do.

Run and Stop already had tips. Java New turn and Clear turns already had tips.

No new package. No new public type. No new public method. The private methods are seatBarTips and seatSendFileWidth. The private function is send_file_button. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The word and the tips are the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The heatbar count stays. The Cancel width stays. The arrow ink stays. The SSH divider stays. The focused pane stroke stays. The east column stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps the three ems. Face keeps the words. Seats the bar. Names the tips. Counts the controls.

## Heatbar sits on the thin status bar

Java is named. The heatbar sits on the thin status bar.

The heatbar sits on the thin status bar.
**semantic-key:** PK-1158
**semantic-key:** PK-1158

buildHeatStrip seats the notebook beat full width under the footer, on the thin status bar. The word rides the east of that strip. T stays in the inspector. On the Aetheris tab the frame beat hides, parent and all.

The 5 pixel track stays. The 2 by 9 needle stays. The posts at 70 and 90 stay. The 6 pixel lunge stays. One flash stays. The count stays the ring estimate.

No new package. No new public type. No new public method. The private method is buildHeatStrip. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The strip is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The heatbar count stays. The Cancel width stays. The arrow ink stays. The SSH divider stays. The focused pane stroke stays. The east column stays. The word on Send file stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps the 5 pixel track. Face keeps the word on the east. Seats the strip. Names the bar. Counts the posts.

## Folded thinking line clears BB1

folded_control_height sizes the folded canvas row to Close. The band adds that height plus the lift. The fold mark, C, and Close clear the outer top of BB1 by at least 2 pixels.

The open strip stays. The VT100 strip stays.

No new package. No new public type. No new public method. The private function is folded_control_height. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The gap is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The heatbar count stays. The heatbar strip stays. The Cancel width stays. The arrow ink stays. The SSH divider stays. The focused pane stroke stays. The east column stays. The word on Send file stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps the open strip. Face keeps the VT100 strip. Seats the gap. Names the line. Counts the pixels.

## Heatbar sits on the status bar and wears the ring count

Java is named. Rust is named. The heatbar sits on the status bar and wears the ring count.

The heatbar sits on the status bar and wears the ring count. No side move on this plate.

The notebook strip is not seated. One beat sits north of the thin status bar on both tabs. The inspector keeps T. The Aetheris word is tokenRingUsed. The SSH word is the session heuristic. Token Calc opens on that same integer. The Rust inspector does not paint the estimate word. The status heatbar keeps the ring integer.

The 5 pixel track stays. The 2 by 9 needle stays. The posts at 70 and 90 stay. The 6 pixel lunge stays. One flash stays. Empty text stays 1.

No new package. No new public type. The public methods are tokenRingUsed, tokenRingWindow, and sessionMemoryText on the widget and the terminal panel. The private methods are sessionMemoryText and sessionMemoryBlocks on the frame, and dropTrailingBlankLines on the widget. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The status beat and the ring count are the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The heatbar geometry stays. The Cancel width stays. The arrow ink stays. The SSH divider stays. The focused pane stroke stays. The east column stays. The word on Send file stays. The folded thinking line stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps T on the inspector. Face keeps the thin status bar. Seats the session text. Names the selected cell. Counts the integer.

## Focused pane wears the full cursor ring

Java is named. The focused pane wears the full cursor ring.

The focused pane wears the full cursor ring. No side move on this plate.

The stroke owns a one-pixel inset. paintBorder fills that ring in the cursor ink. The terminal and the think strip sit inside the inset. A later child paint cannot cover the ring. A single pane stays bare. An unfocused pane stays bare.

The divider gray stays 0x3A3A3A. The divider size stays 11. One-touch arrows stay. The inspector split stays. The stroke bytes stay 0x00D7FF.

No new package. No new public type. No new public method. No new private method. The private field is the one-pixel inset. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The ring is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The heatbar geometry stays. The Cancel width stays. The arrow ink stays. The SSH divider stays. The east column stays. The word on Send file stays. The folded thinking line stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps the divider. Face keeps the cursor ink. Seats the inset. Names the ring. Counts the pixel.

## Bar buttons sit five pixels shorter

Java is named. The BB1 and BB2 buttons lose 5 pixels of height. The height still follows the font.

The bar buttons sit five pixels shorter. The bar height loses 5 pixels after the word floor. No side move on this plate.

Bar buttons keep the app point size. fitAetherisButton still sets a bar button to the preferred height minus 4. holdShortLabel still raises the button when that cut sits under the word. After that floor, a bar button loses 5 pixels. Buttons that are not bar buttons keep the 70 percent path. The red search x is not a bar button. The height still follows the font.

No new package. No new public type. No new public method. No new private method. The edit is inside holdShortLabel. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The five pixels are the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The 5 pixel track stays. The menu width stays. The mood card stays. The heatbar geometry stays. The Cancel width stays. The arrow ink stays. The SSH divider stays. The focused pane ring stays. The east column stays. The word on Send file stays. The folded thinking line stays. The SSH mux stays. Channel A stays. The command-line history stays. The colors stay.

Keeps the 70 percent path. Face keeps the search x off the bar path. Seats the five pixels. Names the floor. Counts the cut.

## The VT100 heatbar wears the Roux session count

Java is named. Rust is named. The VT100 heatbar collects the Roux session count when the app starts and keeps that count as the session continues. The canvas heatbar stays on the selected cell.

The VT100 heatbar wears the Roux session count.

## The VT100 heatbar wears the Roux session count

The door is channel A. The ask is session_tokens:. The reply is session_tokens_result: with used, chars, and blocks. The reply does not paint the VT100. The client asks once when the mux is answered. The server pushes the same reply at the end of startMux, after a stored turn, after command dispatch, and after a session replace. Until the reply is held, the glass heuristic may show. Each pane keeps its own held sum. The budget stays 131072 when the engine word contains ollama, and 0 otherwise.
**semantic-key:** PK-866

No new package. No new public type. The public methods are sessionTokenReply, sendConsoleText, requestSessionTokens, rouxSessionHeld, rouxSessionUsed, rouxSessionChars, and rouxSessionBlocks. The private method is publishSessionTokens. The private function is session_tokens_of. The private fields are the held sum on the notebook and on the Rust pane. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The Roux count on the VT100 heatbar is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. The door is channel A. The ask is session_tokens:. The reply is session_tokens_result: with used, chars, and blocks. The reply does not paint the VT100. No system line is added, removed, or reordered. The prompt stays. The mood line stays in buildPrompt.

Keeps the canvas ring. Face keeps the glass until the reply is held. Seats the channel A door. Names the stored messages. Counts the sum.

## The SSH and Aetheris tab text follows the control-bar font

Java is named. The SSH and Aetheris tab text follows the font size on the control bar. The open strip and the folded plates share that size.

The SSH and Aetheris tab text follows the control-bar font.

The SSH word and the Aetheris word take the control-bar point size. The open tab strip and the folded plate buttons share that size. A fold that installs the tab UI again keeps the size. The range stays 8 through 36. The face stays sans serif. The plate colors stay. The plate shape stays.

No new package. No new public type. No new public method. The private method is seatPlateFont. The private field is platePoints. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The tab words are the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The menu font stays. The control-bar font stays. The canvas tab titles stay. The link chip stays. The plate colors stay. The bar height stays.

Keeps the canvas tab titles. Face keeps the plate colors. Seats the open strip and the folded plates. Names the size. Counts 8 through 36. stays sheathed.

## F-series files sit in the sub-cabinet

F-series pre-plans and plans move into the sub-cabinet. The living pages stay in the main Aetheris cabinet. The links resolve in both directions.

F-series files sit in the sub-cabinet. No side move on this plate.

The resume stays. The stage plans stay. The drafts stay. A cabinet path gains. A -page link gains the prefix. A moved file that points at the main cabinet gains one parent step. A sibling link stays a sibling link.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. Is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The stage plans stay. The drafts stay.

Keeps the living pages. Counts the links.

## A merge cell takes scroll focus only when a cell in the group is clicked

Java is named. A merge cell group leaves the wheel on the canvas scroll pane. A cell in that group takes scroll focus only when that cell is clicked anywhere.

A merge cell takes scroll focus only when a cell in the group is clicked.

The member row does not take the wheel and does not take focus. A wheel that is not on a focused text pane moves the canvas. A click anywhere on a grouped cell gives the caret to the text under the pointer, or to the prompt, then the reply, when that text is showing. The execution target keeps its own caret. A press on a scroll bar still returns the wheel to the canvas. A standard cell keeps the old press. The member row still drags sideways.

No new package. No new public type. No new public method. The private methods are seatGroupWheel and focusGroupCell. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The wheel is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The standard cell press stays. The scroll bar press stays.

Keeps the standard cell press. Face keeps the sideways drag. Seats the canvas wheel. Names the click. Counts one caret.

## Heatbar beat pulses when types on the VT100 tab and on the Aetheris canvas tab

Rust is named. The heatbar beat pulses when the count changes, on the VT100 tab and on the Aetheris canvas tab, the way the Java composer beat already pulses.

Heatbar beat pulses when types on the VT100 tab and on the Aetheris canvas tab. No side move on this plate.

The arm frame draws the 6 pixel lunge and does not ease it. A later frame eases one step, lunge times 0.72, when 16 ms have passed, and asks for another frame in 16 ms. A faster frame does not spend that step. A long gap spends one step. The flash eases on that same step. The beat is stored on one fixed id. The 5 pixel track stays. The 2 by 9 needle stays. The posts stay. The inks stay. A held Roux count still replaces the glass.

No new package. No new public type. No new public method. No new private method. The beat stays in paint and step. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The pulse is the named change. This land leaves the working set as it stands. Java stays. chrome.rs stays unstaged. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The track stays. The posts stay.

Keeps the track. Face keeps the posts. Seats the arm frame. Names the pulse. Counts one lunge.

## A merge join names the model the reply came from

Java is named. Rust is named. When a merge joins two or more cells, the join line names the model the reply came from. The word is the short name the cell caption already paints.

A merge join names the model the reply came from.

concatJoin and concat_join write JOIN plus the short model name. An empty Ollama target still reads ollama. ollama/qwen2.5-coder:7b reads qwen2.5-coder:7b. grok/grok-4 reads grok-4. Pool reads pool. A strip drops that line, a legacy full target line, and the legacy literal JOIN ollama. shownTarget stays.

No new package. No new public type. No new public method. The private methods are joinWord and joinBannerLine. The private functions are join_word and join_banner_line. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The model name is the named change. This land leaves the working set as it stands. The banner stays unstaged. The connect path stays unstaged.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. shownTarget stays.

Face keeps the caption. Seats the short name. Names the model. Counts one banner.

## A declared test covers the Roux 1.1 head roll and the tail spill

Java is named. A formal unit test is filed only when declares it. This sentence declares the Roux 1.1 window battery. The head rolls. The tail stays on the hot page.

A declared Roux 1.1 window test covers the head roll and the tail spill. No side move on this plate.

## A declared test covers the Roux 1.1 head roll and the tail spill

No new package. No new public type. No new public method. The test type is package-private. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The battery is the named change. This land leaves the working set as it stands.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays. The paging memorandum stays.

## Roux code carries the tags and the Knuth source prose

Roux code carries the tags and the Knuth source prose. No side move on this plate.

Every type under org.roux carries type-level Knuth prose and a type-level tag. A method that realizes a key carries the prose and the tag. A private getter stays. Comments and JavaDoc only. No behavior change.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The tags and the prose are the named change. This land leaves the working set as it stands. The paging memorandum stays unstaged.

Face keeps the tag lines. Seats the prose. Names the spec. Counts one compile.

## A canvas cell input updates the heatbar while the user types

Java is named. Rust is named. While the user types in a VT100 pane, the heatbar est token count updates as characters are added and removed. A canvas cell input pane does the same. The count is calculated as the user types.
**semantic-key:** PK-867

A canvas cell input updates the heatbar est token count as the user types.

## A canvas cell input updates the heatbar while the user types

No new package. No new public type. No new public method. The private method is noteLiveDraft. The private field is liveTokenHold. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands. The chrome.rs count is the only staged hunk in that file.

Wire stanza. Empty. No system line is added, removed, or reordered. Doors stay. The yaml stays.

Face keeps the 200 ms beat. Seats the draft. Counts the same window the click already counts.

## Font control sits after Close Pane

Java is named. Rust is named. The global font sizing control moves to the right of the statically held button bar. A divider follows Close Pane. The sizing control follows that divider.

The font sizing control sits to the right of the button bar, after a divider past Close Pane. No side move on this plate.

## Font control sits after Close Pane

No new package. No new public type. No new public method. The private method is barRule. No new vault column. No new channel. No new AMWP type. No new color key. The rule uses the grab edge already in the tree. No new library. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands. The layout hunk is the only staged hunk in each file.

Face keeps FONT_TIP. Seats the rule. Counts the button row.

## Control bar button reads Load File

Java is named. Rust is named. The control bar button reads Load File. The tip, the help, and the notice titles that name that control use the same word.

The control bar button reads Load File.

## Control bar button reads Load File

SshConnectionPanel paints the button as Load File. SEND_FILE_TIP names Load File. SshClientApp help and the notice titles name Load File. ChatShell welcome names Load File. chrome.rs paints the button as Load File. SEND_FILE_TIP names Load File. HELP_BODY names Load File. The shell notices name Load File. Method names stay. The three-em width stays. The file still opens in the paste editor. OK still inserts. Enter still submits. The button still enables only while the focused pane is connected. A disabled button still does not show the tip. Import stays a different path. The font control stays to the right of Close Pane.
**semantic-key:** PK-768
**semantic-key:** PK-768
**semantic-key:** PK-768

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands. The word hunk is the only staged hunk in each parked file.

Face keeps the tip on the new word. Seats the label. Counts the same path.

The canvas token-calc analysis is filed. A focused merge counts the group alone. A later cell caps that group. A cell above counts its own prompt in full. No new F row.

## One transcript estimates every construct

He invited the team to the unified solution, with a seam for a later estimator. It does not name the build. Code waits.

## One transcript estimates every construct

No new package. No new public type on this filing. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. This filing leaves the working set as it stands.

Keeps one transcript and one estimator. Seats the one walk. Counts one string. Face keeps the heatbar word on the estimator result.
**semantic-key:** PK-868

I, E, P, and O are states that affect the est token count. Any state modifier and any structural modifier runs on the one transcript. Included keeps both halves. Excluded keeps neither. Input only keeps the user half. Output only keeps the assistant half. Code waits.

Keeps the chain. Seats one walk. Counts the halves each mark keeps. Face keeps the heatbar word on the result.

That is a requirement of selection and arming for a cell card. `focusedCellId` stays the clicked card. Code waits.

Keeps the arm on the group. Seats the one turn. Counts the same string for both clicks. Face keeps the heatbar word on that string.

The shakedown seals the walk, the one count, the pipe index, the cloud ephemeral drop, and the held Roux tally. Code waits.

Keeps the live builders out of the counted string. Seats one pass over orderedCells. Counts one string. Face keeps the heatbar on the held Roux integer and on the canvas count.

The build stays unnamed. Code waits.

Keeps the type list. Seats the build order. Counts one string.

He named the build.

Keeps the package and the type list. Seats transcriptThroughArm as one pass. Seats rendered and the one count.

## The Mood modal paints SSH Mood Active

Java is named. Rust stays on this row. The Mood modal paints SSH Mood Active. When that box is on, the mood line is seated on every connected SSH pane of the canvas user.

The Mood modal paints SSH Mood Active. No side move on this plate.

The checkbox label is SSH Mood Active. It sits to the left of Clear mood. The box follows the canvas user. An armed user, with the mood switch on and a saved line, sends shell_mood to every connected AMWP pane of that user. A pane that already matches sends nothing. A clear goes only to a pane that was already told. A raw session is left alone. The status strip follows the focused pane user. The Java Mood modal was built with Clear, Approve, and Cancel. Rust already paints the box. The empty Rust bullet on this MUNCH is not a row.

No new package. No new public type. The public methods on existing types are relayArmedMoods, sshMoodLineFor, moodHeld, and setMoodHeld. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The shared mood file stays. Rust stays on this row.
**semantic-key:** PK-869

Wire stanza. The console door order is unchanged. The pinned host stays first. The mood string follows. The activate line follows. The turns follow. No system line is added, removed, or reordered.

Rust stays on this row. Keeps the shared mood file. Face seats the box. Seats the relay.

## The SSH mood flag is saved by canvas user

Java is named. Rust is named. Opening the Mood modal shows the SSH activation for the user that has focus on the Aetheris canvas. The flag is saved by user. An armed flag is remembered on that user SSH panes at startup.

The SSH mood flag is saved by canvas user. No side move on this plate.

The store is ~/.casserole/aetheris-ssh-mood.yaml. Version 1. A users map of sanitized name to boolean. aetheris-mood.yaml stays version, line, on, width, and height. The checkbox shows the canvas user flag. An empty user leaves the box unchecked and disabled. A toggle writes that user and relays. On connect, the relay seats the panes of armed users. Rust ssh_mood is the glass copy of the canvas user flag. The map is the store. A blocked file is left unread and is not overwritten. A missing file starts empty. A name that sanitizes to anon is not stored. The Rust draft stays as filed.

No new package. No new public type. No new public method on this row. The readers and writers are the private store methods. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The shared mood file stays. The new file is the store this sentence asked for.

Keeps aetheris-mood.yaml. Face seats the checkbox from the map. Seats the file.

## The VT100 heatbar moves while the user types

Java is named. Rust is named. The heatbar beat and the est token count move while the user types in an SSH VT100 pane. The number is the focused pane when several panes are open. The canvas path already moves.

The VT100 heatbar moves while the user types. No side move on this plate.

Each pane keeps its own unsent line. The heatbar and the token window read the focused pane. A held Roux integer stays the base. A non-empty draft adds the heuristic of that line. An empty draft adds nothing. An unheld tally uses the session text, and adds the draft when the echo has not already caught that suffix. Enter clears. Backspace pops one character. Shift-Enter appends a newline and keeps the line. The canvas path stays. No server call on a key.

No new package. No new public type. The public methods on the existing panel are setDraftBeat and liveLine. The private method is sshLiveCount. The private function is ssh_live_count. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. This land leaves the working set as it stands.
**semantic-key:** PK-870

Keeps the canvas path. Face keeps the 200 ms beat. Seats the live line. Counts the focused pane.

## Server stop keeps the shutdown cycle

Java is named. Shutting down the Casserole server used to take about 5 to 10 seconds. It now exits at once and drops the cycle on the floor. The stop throws on InnocuousThread. IllegalStateException. Executor has been shut down. The stack is NoCloseExecutor, then the Windows accept failure, then the IOCP handler.

The Casserole server stop holds the SSH group open, settles the Windows accept, then drains five seconds before the group shuts down.

## Server stop keeps the shutdown cycle

The listener signals the listen loop, calls sshd.stop, then cancels the listen task. QuietNioService waits one second for the Windows accept failure, then holds the group five seconds, then the stock close runs. The executor stays open through that wait. Lamina, Roux, and PoolChat stay. sshd stays 2.19.0. A localhost stop with no client, using that same settle and drain, took 6010 ms and did not print the executor exception.
**semantic-key:** PK-769
**semantic-key:** PK-769

Wire stanza. Empty. No system line is added, removed, or reordered.

The stop had been dropping the work and exiting at once. Keeps NIO2 and the five-second group wait. Seats the accept settle and the group drain before the group shutdown. Counts one sshd.stop. Face keeps the listener log lines.

## Canvas switch leaves the SSH mood seated

Java is named. Rust is named. Turning the canvas mood switch off cleared the SSH mood on a pane that was already told. The Mood modal still said SSH Mood Active. Turning the switch back on left the session inactive.

The canvas mood switch leaves an SSH mood the box still holds. An unchecked box still clears a pane that was told. Assemble stays the Run.

The canvas switch stays on canvas, group, and pipe sends. The SSH seat is the checkbox and a saved line. sshMoodLineFor and ssh_mood_seat do not read the switch. A null want still leaves a pane that was never told alone. An unchecked box, or a missing line, still clears a pane that was told. The status strip follows that seat. paintFrameStatus runs at the end of relayArmedMoods. A null panel returns before that call. The server pair stays. Their plan sentences that fold the switch into the SSH seat are the contract this row supersedes.

No new package. No new public type. No new public method. The private function is ssh_mood_seat. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate.

Wire stanza. When the box is checked, the console door order stays pinned host, mood string, activate line, turns, whether the canvas switch is on or off. When the box is unchecked, no shell_mood frame, and a pane already told gets shell_mood_clear. Canvas, group, and pipe doors stay host, then mood and activate only while the canvas switch is on. This change sends fewer clears. Assemble stays the Run.

The modal still said the box was on. The session had gone inactive. Keeps the canvas switch on canvas, group, and pipe sends. Face keeps SSH Mood Active on the checkbox. Seats sshMoodLineFor and ssh_mood_seat on the checkbox and the saved line. Counts one seat.

## Thinking spinner sits on the control bar

Java is named. Rust is named. The thinking spinner and its clock leave the crowded BB1 row. Open, they sit to the right of the global font control. Folded, they sit at the far east of the main menu row.

The thinking spinner and its clock sit on the control bar. Open, they sit to the right of the font control. Folded, they sit at the east of the menu row.

The user combo stays on the east of BB1. A quiet deck hides the cluster. The clock follows the running card. thinkCluster returns the spinner. holdThink remembers it. parkThink seats it after the font control. seatThink and placeThink move that same cluster to the east of the folded menu row. The mood dialog spinner stays. The SSH shell spinner stays.

The public method is thinkCluster. The private methods are holdThink, parkThink, seatThink, and placeThink. No new package. No new public type. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate.

Keeps the user combo on BB1. Face keeps the quiet deck. Seats the cluster after the font control, and at the east of the menu row when the bar is folded. Counts one cluster.

## Assistant reply frame is sapphire

Java is named. The assistant reply frame on a Java canvas cell turns sapphire. The input frame stays green.

The assistant reply frame on a Java canvas cell is sapphire. The input frame stays green. No side move on this plate.

The assistant scroll takes SELECTION_SAPPHIRE when the cell has a reply. The input scroll stays MEDIUM_GREEN. The input text stays INPUT_GREEN. An empty reply stays gray. The card edge stays. Rust cell frames stay.

No new package. No new public type. No new public method. No new private method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate.

Keeps the input frame green. Face keeps the empty reply gray. Paints the reply frame with the armed-card sapphire. Counts one stroke.

## Canvas text panes can wear the Java cell frame

Rust is named. The canvas text panes can wear the Java cell frame. The switch starts off. Off keeps the quiet plate. Keeps that plate.

Rust canvas text panes can wear the Java cell frame. The switch starts off. No side move on this plate.

File, Settings opens the window. The checkbox is Painted text borders. Off keeps the quiet plate. On paints the Java cell frame on a canvas text pane that already paints. The input frame is medium green. A reply with trimmed text is sapphire. A reply whose trimmed text is empty is gray. A pane that was not painted stays unpainted. The card edge stays. SSH panes stay. Dialog text stays. The choice is saved in aetheris-settings.yaml. A missing file stays off and is not written until the switch moves. A bad file stays off and is not overwritten. A memory door writes nothing.

## Canvas text panes can wear the Java cell frame

Keeps the off path. Face keeps the empty reply gray. Seats the frame on the panes that already paint, and seats Settings on the File menu. Counts one switch.

## Button borders keep two points of clearance

Rust is named. The button bar was clipping the bottom of the buttons on BB1 and on the Close control of the thinking pane.

The button borders keep two points of clearance. The lane follows the pane font. No side move on this plate.

The lane was sized to the stock button height. The buttons paint at the pane font. That lane was also the clip, so the border was cut. The column font is the override. TextStyle Button ignores it, so the folded Close row stayed short and the strip clip cut Close. The clearance is 2 points on every side of a button border. BB1, the east combo, the tool row, and Close take that clearance. The folded line lift stays. The footer pad, the corner, and the edge stay. C keeps its face.

No new package. No new public type. No new public method. The private functions are button_outer_height, button_lane_height, and button_lane_clip. BUTTON_CLEAR_PX is 2. No new. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate.

Keeps the stock interact size out of the lane. Face keeps the button at its own height. Seats two points around the border. Counts the clearance.

2026-11-03 05:35. declared the reopen cases. The window battery names the walk. This sentence does not open a row.

## SSH code carries the tags and the Knuth source prose

Java is named. The Rust catalog lines move. The yaml stays.

SSH code carries the tags and the Knuth source prose. The Rust catalog lines are in that same commit.

Every top-level type in the SSH packages carries type-level Knuth prose and a type-level tag. A method that already has a real paragraph stays. A private getter stays. Comments and JavaDoc only. No behavior change.

No new package. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The tags and the prose are the named change. This land leaves the working set as it stands. The paging memorandum stays unstaged. The yaml stays.

The Rust catalog lines move. Face keeps the tag lines. Seats the prose. Names the spec. Counts one compile.

## One mood desk seats the canvas and the Roux session

One mood desk seats the canvas and the Roux SSH session. Canvas, group, and pipe: empty host seat, mood string, activate line, turns, while the switch is on. The server replaces that empty seat. SSH: pinned host, mood string, activate line, turns, while the checkbox and a line are set. Glass: shaping line, then the pair when the field holds a line. Pool session/new: the pair is the rules. The host stays out. MoodDeskTest passed, 12 tests. ConsoleMoodTest passed, 6 tests. SshMoodMemoryTest passed, 8 tests. ShellMoodFrameTest passed, 1 test. PoolMoodRulesTest passed, 2 tests.
**semantic-key:** GK-1547
**semantic-key:** GK-1548
**semantic-key:** GK-1549
**semantic-key:** GK-1550
**semantic-key:** GK-1551
**semantic-key:** GK-1552

Wire stanza. Canvas, group, and pipe share one head. While the footer switch is on and a line is saved, the system lines are an empty host seat, then the mood string, then the activate line, then the turns. The server drops that empty seat when the prompt is blank, and fills it when a prompt is configured. The mood pair stays behind that seat. SSH door: the console completeTurn. While the checkbox is on and a line is saved, the system lines are the pinned host, then the mood string, then the activate line, then the turns. placeHost inserts the configured host at index 0 only when that text is absent. A blank prompt puts the pair first. A session that already has turns loses any prior pair and keeps the turns. Glass door: the shaping system line, then the pair when the field holds a line. The footer switch does not gate the glass. Pool door: session/new. Pair is the rules. The host stays out. An extend keeps the rules of the open.

Keeps the two yaml files. Face keeps the Swing switch and the checkbox. Seats the desk. Names the spec. Counts the battery.

## The I, E, P, and O marks stay live

He reviews the proposal before he calls the bake. Rust is unaffected. This sentence files the spec. It does not name the build. Code waits.

One method, seatMark. The press on a mark calls it. Ctrl-Shift I, E, P, and O call it. The write does not ride ButtonModel armed, pressed, or focus. The four marks are plain buttons. They are not focusable. The card listener does not steal focus from a mark. The gesture does not call rebuildTabs. The live card paints the seated letter with PLATE_SELECT and the ghost when the state is EXCLUDED. A JButton in place of the toggle is the same miss. Rust is unaffected. chrome.rs mark_button writes the mark in the same show pass. No Rust proposal.

No new package. No new public type. No new public method on this filing. seatMark is named for the later bake. This filing adds none of it. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. This filing leaves the working set as it stands. The paging memorandum stays unstaged. The yaml stays.

The bake waits. Keeps the write off the toggle model. Face keeps the live card. Names the spec. Counts one press.

This sentence names the bake. seatMark is the build.

Keeps the write off the toggle model. Face keeps the live card. Names the spec. Counts one compile.

The I, E, P, and O marks seat through seatMark on the press and on Ctrl-Shift I, E, P, and O. The gesture does not rebuild the deck. The private method is seatMark.

The private method is seatMark. paintCellMark paints the four faces and the ghost on the live card. The seated letter wears PLATE_SELECT and PLATE_SELECT_INK. No new public type. No new public method. No new color key. No new library.

Face kept the live card. Named the spec. Counted one compile.

## Mood follows the switch on an existing session

Mood was reaching a fresh session and a cleared session. An existing canvas, an existing SSH transcript, a mood switched off and back on, and a Transfer Conversation from SSH onto the canvas left the mood off the send. The SSH checkbox was the only SSH switch. The footer off left SSH mood on. This row states the new rule. The checkbox makes mood available for SSH. SSH mood is on when the footer switch is on and the checkbox is checked. The switch off turns mood off, including SSH. An unchecked box leaves SSH without the mood while the switch is on. An existing session takes the triple when the switch says on, and for SSH when the box is checked too. A Transfer Conversation follows the switch. The engagement line is the opener, then the constructed mood, then the closer. The constructed mood is the saved line. The example paragraph stays an example. Crate delta for this gate and this copy waits until names it.

The public constants are MoodDesk.OPENER, MoodDesk.CLOSER, and MoodDesk.LEGACY. OPENER is the sentence before the constructed mood. CLOSER is the sentence after it. LEGACY is the old activate sentence. A later seat still drops that row. The package method sshLine takes the checkbox, the footer switch, and the saved line. Both gates on, and a line saved, returns the constructed mood. Either gate off returns null. canvasSystems returns the opener, the constructed mood, and the closer while the switch is on and the line is non-blank. A canvas execute keeps that triple. A frame that does not fit fails too-large and does not run. The send does not drop the triple to fit. seat writes the opener, the constructed mood, and the closer, and takes every prior triple out, including a pair that still uses LEGACY. A row immediately before the opener is the host and stays. A row immediately before the closer, or before LEGACY, is the mood and leaves. adopt reads the constructed mood. porchRules returns the opener, a blank line, the constructed mood, a blank line, and the closer. A legacy pair still reads. The glass writes the opener, the field text, and the closer. The footer switch does not gate the glass. Transfer from the canvas onto Roux relays the gate after ok. The server may still hold the previous line until that frame arrives. Transfer from Roux onto the canvas stores user and assistant cells. The next canvas execute carries the triple from the switch. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The paging memorandum stays unstaged. The yaml stays.

Wire stanza. Canvas, group, and pipe: empty host seat, opener, constructed mood, closer, turns, while the footer switch is on and the line is non-blank. The server replaces that empty seat. The execute does not drop the triple to fit. SSH: pinned host, opener, constructed mood, closer, turns, while the footer switch is on and the checkbox is checked and a line is saved. The switch off sends shell_mood_clear even when the box stays checked. An unchecked box sends clear even when the switch is on. An existing transcript gets the triple on that announce and loses the prior pair, including the legacy activate sentence. Glass: shaping line, then opener, constructed field text, closer, when the field holds a line. The footer switch does not gate the glass. Pool session/new: rules are opener, a blank line, constructed mood, a blank line, closer. The host stays out. Transfer from the canvas onto Roux relays that gate after ok. The server may still hold the previous line until that frame arrives. Transfer from Roux onto the canvas does not store the triple in cells. The next canvas execute carries it from the switch. The constructed mood is normalize of the saved line. The example paragraph stays an example. A row immediately before the opener is the host and stays. A row immediately before the closer, or before the old activate sentence, is the mood and leaves when the seat rewrites.

Seats the triple. Face keeps the constructed mood. Names the spec. Counts the battery.

## Mood follows the switch on an existing session

Mood follows the switch on an existing session. The footer switch gates the canvas push and the shell. The checkbox makes mood available for SSH. SSH mood is on when the switch is on and the box is checked. The switch off turns mood off, including SSH. The constructed mood sits between the opener and the closer. The example paragraph stays an example. Canvas, group, and pipe: empty host seat, opener, constructed mood, closer, turns, while the footer switch is on and the line is non-blank. The server replaces that empty seat. The execute does not drop the triple to fit. SSH: pinned host, opener, constructed mood, closer, turns, while the footer switch is on and the checkbox is checked and a line is saved. The switch off sends shell_mood_clear even when the box stays checked. An unchecked box sends clear even when the switch is on. An existing transcript gets the triple on that announce and loses the prior pair, including the legacy activate sentence. Glass: shaping line, then opener, constructed field text, closer, when the field holds a line. The footer switch does not gate the glass. Pool session/new: rules are opener, a blank line, constructed mood, a blank line, closer. The host stays out. MoodDeskTest passed, 12 tests. ConsoleMoodTest passed, 6 tests. SshMoodMemoryTest passed, 8 tests. ShellMoodFrameTest passed, 1 test. PoolMoodRulesTest passed, 2 tests. Crate delta for this gate and this copy waits until names it.
**semantic-key:** GK-1554
**semantic-key:** GK-1555
**semantic-key:** GK-1556
**semantic-key:** GK-1557
**semantic-key:** GK-1558
**semantic-key:** GK-1559

Seated the triple. Face kept the constructed mood. Named the spec. Counted the battery.

## Mood call uses the armed cell on the showing canvas

moodCard reads focusedTurn. A readable cell returns. A missing arm, or an unreadable arm, falls through to targetCell. A readable targetCell returns. Else the last readable cell on the showing canvas returns. None returns null. openMood then sets the status to no cell and the dialog stays closed. sendMood still loads the id captured at open and still calls moodTarget. ollamaPaneOwnsCell returns true when the family combo is missing, and when the selected item is Ollama. It returns false for Grok, for Pool, and for an empty selection. commitOllamaModel calls it on the cell path. The console path still calls commitConsoleModel. The Ollama editor keyReleased calls ollamaPaneOwnsCell after its existing guard. The family listener stays. applyGrokFamily still writes the grok target. The private methods are moodCard and ollamaPaneOwnsCell. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The paging memorandum stays unstaged. The yaml stays.

Wire stanza. The mood triple stays. Canvas, group, and pipe: empty host seat, opener, constructed mood, closer, turns, while the footer switch is on and the line is non-blank. The server replaces that empty seat. SSH: pinned host, opener, constructed mood, closer, turns, while the footer switch is on and the checkbox is checked and a line is saved. Glass: shaping line, then opener, constructed field text, closer, when the field holds a line. The footer switch does not gate the glass. Pool session/new: rules are opener, a blank line, constructed mood, a blank line, closer. The host stays out. The mood execute execution_target follows the armed cell on the showing canvas. An empty target is ollama. A Grok card sends grok and that card's model id. A pool card sends pool. The constructed mood is normalize of the saved line. The example paragraph stays an example.

Seats the showing-canvas arm. Face keeps the family write on the family that is showing. Names the spec. Counts one compile.

The mood call uses the armed cell on the showing canvas. The private method moodCard reads focusedTurn. The private method ollamaPaneOwnsCell keeps an Ollama write off a Grok or Pool family. The mood triple stays. The mood execute execution_target follows the armed cell on the showing canvas. An empty target is ollama. A Grok card sends grok and that card's model id. Rust stayed. chrome.rs stayed. grid.rs stayed.

Seated the showing-canvas arm. Face kept the family write on the family that is showing. Named the spec. Counted one compile.

## A cell key posts the token walk

Typing in a canvas cell input was waiting on the token walk. Each insert, delete, and change ran the armed transcript and the heuristic on the EDT before the key returned. The count stays the count. The key writes the draft and posts an edit. The walk runs off the key. The newest edit is the one that runs. A late result paints only when its stamp is still current. A click still measures on that call. The heatbar still reads the integer on its beat. This is an enhancement. No crate delta.

## A cell key posts the token walk

noteLiveDraft still writes the cell text on the key. The first key on an unarmed card arms that card, then posts the edit and schedules the inspector. A key on a card that is already armed posts the edit and schedules the inspector. The arm chrome, the status line, the delete button, and the caption stay on the key. A click still measures before it returns. refreshInspector paints the inspector, then measures. The scheduled inspector paint is the text, the facts line, and the mood line. It does not walk. measureTokensNow claims the next stamp, drops an edit that has not started, walks the armed transcript, and paints when that stamp is still current. An empty canvas claims a stamp and paints 0. TokenEditQueue is a private class. The thread is a daemon named aetheris-token-edit. offer keeps the newest edit that has not started. The worker copies the seat on the EDT, walks the copy off the EDT, and paints on the EDT when the stamp is still current and the card that posted it is still armed. transcriptFrom is the shared walk. membersOn reads the ordered list the walk already holds, so the join-banner strip stays the strip the click already used. The worker does not read the showing canvas. HeuristicEstimator stays. The marks stay. The heatbar stays on tokenRingUsed. The token window stays. notebookDip.watch stays on the paint. The SSH heat path stays. The private classes are TokenEdit, TokenSeat, and TokenEditQueue. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. SshClientApp stays. The paging memorandum stays unstaged. The yaml stays.
**semantic-key:** PK-770
**semantic-key:** PK-770
**semantic-key:** PK-770
**semantic-key:** PK-770
**semantic-key:** PK-770

Wire stanza empty. No send line moves. Doors stay.

Seats the edit queue. Face keeps the same integer. Names the spec. Counts one compile.

A cell key writes the draft and posts the edit. The transcript walk runs off the key. The newest edit is the one that runs. A result paints when its stamp is still current. A click still measures on that call. The private class is TokenEditQueue. No side move on this plate. Rust stayed. chrome.rs stayed. grid.rs stayed.

Seated the edit queue. Face kept the same integer. Named the spec. Counted one compile.

## A mood change sends the whole pool session once

Changing the mood in the mood modal clears the saved line and sets a new one. The prior mood stays in the connected pool session. Turning the canvas switch off leaves that mood in the session. A held pool session sends the tail when the new working set starts with the held text. Rules are set when the session opens. A later tail keeps those rules. The opener and the closer are that header. A collapsed card stays in the working set. An excluded card stays out. Neither card stores the header. Ollama and Grok already send the working set whole. SSH Roux memory already reseats through the desk when the shell frame arrives. The pool child is the delta. A mood change sends the whole working set once. The same rules may send the tail again. No crate delta.
**semantic-key:** PK-871

## A mood change sends the whole pool session once

Wire stanza. A pool prompt whose rules differ from the open session sends the whole working set and opens a session. The next prompt with those same rules may send the tail. Canvas, group, and pipe keep the opener, the constructed mood, and the closer while the footer switch is on and the line is non-blank. The switch off leaves that triple off the working set. SSH still seats one triple in the Roux transcript.

Seats the rule hold. Face keeps the tail for the same rules. Names the spec. Counts the mood batteries.

A mood change sends the whole pool session once. Same rules may send the tail again. The switch off is that change. A collapsed card stays in the working set. An excluded card stays out. The Roux seat still replaces the triple. No side move on this plate. A pool prompt whose rules differ from the open session sends the whole working set and opens a session. The next prompt with those same rules may send the tail. Canvas, group, and pipe keep the opener, the constructed mood, and the closer while the footer switch is on and the line is non-blank. The switch off leaves that triple off the working set. SSH still seats one triple in the Roux transcript. PoolMoodRulesTest passed. MoodDeskTest passed. Rust stayed. chrome.rs stayed. grid.rs stayed.
**semantic-key:** GK-1565
**semantic-key:** GK-1566
**semantic-key:** GK-1567

Seated the rule hold. Face kept the tail for the same rules. Named the spec. Counted the mood batteries and one compile.

## Undo and redo stay in RAM for this session

Session undo and redo for SSH panes and for canvas cells. The cache is RAM only. It does not survive a restart. Ctrl-Z undoes. Shift-Ctrl-Z redoes. Each canvas cell keeps its own stack. Each SSH input line keeps its own stack, including a line pulled back from history while that line is the one being edited. Undo runs in reverse order of the text changes. Java and Rust both move.

EditMemory wraps UndoManager. The limit is 100. An edit stores the text and the cursor. The caller writes them into the live widget. sync remembers a rebuilt card without pushing. A history hop reloads the canonical line and is not an undo step. The draft stack stays parked until that draft returns. The first text change that leaves history mode adopts the visit seat. Enter and bare Escape drop the stacks. A paste is one step. Ctrl-Z is byte 0x1A. Redo is ESC [ 1; 6 Z. The Java terminal sends those bytes only when the terminal is focused. Rust keeps a string seat per canvas cell and sends the same chords for SSH. The public type is EditMemory. The package is org.casserole.edit. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The paging memorandum stays unstaged. The yaml stays.

Wire stanza. Ctrl-Z is byte 0x1A. Shift-Ctrl-Z is ESC [ 1; 6 Z. Both are editor ops on the unsent line and on a canvas prompt. They are not a channel and not an AMWP type. The history file stays.

Seats EditMemory. Face keeps history hops off the stack. Names the spec. Counts one compile and one cargo check.

Undo and redo stay in RAM for this session. Ctrl-Z undoes. Shift-Ctrl-Z redoes. A canvas cell keeps its own stack. An SSH line keeps its own stack. A history hop is not an undo step. The stack is not written to disk. Ctrl-Z is byte 0x1A. Shift-Ctrl-Z is ESC [ 1; 6 Z. Both are editor ops on the unsent line and on a canvas prompt. They are not a channel and not an AMWP type. Rust moved. grid.rs stayed.
**semantic-key:** PK-872

Seated EditMemory. Face kept history hops off the stack. Named the spec. Counted one compile and one cargo check.

## A key on an SSH pane posts the count

Typing on the SSH VT100 tab posts the token edit the way a canvas cell does. The count runs off the key. Each pane keeps its own integer. The timer paints that integer. A click still measures on that call.

## A key on an SSH pane posts the count

A key on an SSH pane writes the line and posts the edit. The count runs off the key. The newest edit is the one that runs. A result paints when its stamp is still current. Each pane keeps its own integer. The timer paints that integer. A click still measures on that call. The private class is SshEditQueue. No side move on this plate. Rust stayed. chrome.rs stayed. grid.rs stayed.
**semantic-key:** PK-873

## Clear turns keeps the armed model

Clear turns keeps the armed cell's model on the new first turn. The word is captured before the wipe. Java stores that word on the new cell. Rust stores the target and the Ollama id on the fresh card. Thinking flags stay off. Expanded stays unchecked. The button still cannot be undone.
**semantic-key:** PK-874

clearTurns reads the armed cell, then vault.clearCanvas builds the empty turn. saveCell writes that cell's executionTarget. newTurn stays on the last cell. try_clear reads the focused card, then clear_plate builds the empty card, then the fresh card receives target and ollama_id. keep_card_think writes that id. Thinking flags stay off. Expanded stays unchecked. Marks stay Included on the fresh card. The other canvases stay. The VT100 session stays. Clear still cannot be undone from the button. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The paging memorandum stays unstaged. The yaml stays.

Seats the model on the new first turn. Face keeps thinking flags off. Names the spec. Counts one compile and one cargo check.

Clear turns keeps the armed cell's model on the new first turn. The word is captured before the wipe. Java stores that word on the new cell. Rust stores the target and the Ollama id on the fresh card. Thinking flags stay off. Expanded stays unchecked. The button still cannot be undone. Rust moved. grid.rs stayed.

Seated the model on the new first turn. Face kept thinking flags off. Named the spec. Counted one compile and one cargo check.

## The Mood title names the model

The Mood title names the model the glass sends. A blank short name keeps the word Mood. Java reads moodTarget and shortModelName. Rust reads the mood tune for the bound card and short_model_name. The bar holds the word.
**semantic-key:** PK-875

openMood calls moodTarget on the card moodCard already chose, then shortModelName. A blank short name keeps the title Mood. Any other short name sets the title to Mood, then U+00B7, then that name. paint_mood reads the card bound to the glass. mood_tune fills the target the send uses. short_model_name reads that target. The same blank rule keeps the title Mood. Window::new takes that string. The window id stays on the generation. The mood string stays. The footer switch stays. Approve, Cancel, and Clear mood stay. The send stays on the same card and the same target. View, the token window, Model Config, and the Clear mood ask keep their titles. No row is added at the top of the chat. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The paging memorandum stays unstaged. The yaml stays.

Seats the word on the title bar. Face keeps the mood string. Names the spec. Counts one compile and one cargo check.

The Mood title names the model the glass sends. A blank short name keeps the word Mood. Java reads moodTarget and shortModelName. Rust reads the mood tune for the bound card and short_model_name. The bar holds the word. Rust moved. grid.rs stayed.

Seated the word on the title bar. Face kept the mood string. Named the spec. Counted one compile and one cargo check.

## The Rust mood seat follows the Java desk

The Rust mood seat follows the Java desk. Canvas, group, and pipe send the empty host seat, the opener, the constructed mood, and the closer while the switch is on and the line is non-blank. A long canvas does not drop that triple. The legacy activate sentence stays off a new send. The checkbox makes SSH available. The switch off clears a checked SSH seat. The glass seats the opener, the field, and the closer. The switch does not gate that chat. The inspector shape line is the closer. An Ollama write stays off a Grok or Pool family.

Private functions carry the desk. No new public type. No new public method. The glass session struct stays the glass session. ssh_mood_seat delegates to ssh_mood_line. The checkbox makes the seat available. The footer switch turns it on. Either one off returns none and clears a pane that was told. The shell frame stays shell_mood and the trimmed line, or shell_mood_clear. The opener and the closer stay off that frame. The status strip says mood active only when both are on. fit_blocks, while the switch is on and the line is non-blank, pushes an empty host seat, the opener, the trimmed line, and the closer, then the turns. A frame that does not fit returns no frame and dropped false. The execute does not drop the triple. Mood off does not add a host seat. The vestige host sentence stays off the wire. mood_shape_tuned keeps the shaping line first. A trimmed field then seats the opener, the field, and the closer. An empty field leaves those three off. The switch does not gate that chat. mood_inspector_head previews the closer at 80 characters. counted_inspector puts the opener, the trimmed line, and the closer at the head when a send carries the mood and the block list is non-empty. ollama_pane_owns_cell returns true for a missing family or the Ollama family, and false for Grok or Pool. type_ollama, commit_ollama, and set_ollama_think return quiet before a write when the pane is not the console and does not own the cell. The console path does not ask. A missing family still writes. The opener and the closer match the Java OPENER and CLOSER. The legacy activate sentence stays a named constant and stays off a new send. One showing canvas stays the first vault canvas. Mood open uses focus on that deck. The client still sends the whole working set. The crate does not grow a pool session. Approve still saves and turns the switch on. Clear still asks. Glass geometry, tips, colors, and button labels stay. The SSH checkbox label stays. The yellow inspector card stays. Thinking flags stay. try_clear stays. The mood switch tooltips stay. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The paging memorandum stays unstaged. The yaml stays.

Wire stanza. Canvas, group, and pipe: empty host seat, opener, constructed mood, closer, turns, while the footer switch is on and the line is non-blank. The server replaces that empty seat. The execute does not drop the triple to fit. The legacy activate sentence stays off a new send. SSH: the checkbox makes the shell available and the footer switch turns it on. Either off clears a pane that was told. The shell frame stays shell_mood and the constructed line. Glass: shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Mood off leaves the triple off and does not add a host seat. The vestige host sentence stays off the wire. The status strip says mood active only when both are on. The inspector shape line is the closer, cut at 80. The counted head is the opener, the trimmed line, and the closer when a send carries the mood and the block list is non-empty. The client still sends the whole working set. The crate does not grow a pool session. A missing family still writes. The console path does not ask.

Seats the opener and the closer. Face keeps the glass. Names the spec. Counts one cargo check.

The Rust mood seat follows the Java desk. Canvas, group, and pipe send the empty host seat, the opener, the constructed mood, and the closer while the switch is on and the line is non-blank. A long canvas does not drop that triple. The legacy activate sentence stays off a new send. The checkbox makes SSH available. The switch off clears a checked SSH seat. The glass seats the opener, the field, and the closer. The switch does not gate that chat. The inspector shape line is the closer. An Ollama write stays off a Grok or Pool family. No side move on this plate. Canvas, group, and pipe: empty host seat, opener, constructed mood, closer, turns, while the footer switch is on and the line is non-blank. The server replaces that empty seat. The execute does not drop the triple to fit. The legacy activate sentence stays off a new send. SSH: the checkbox makes the shell available and the footer switch turns it on. Either off clears a pane that was told. The shell frame stays shell_mood and the constructed line. Glass: shaping line, then the opener, the field, and the closer when the field holds a line. Rust moved. grid.rs stayed.

Seated the opener and the closer. Face kept the glass. Named the spec. Counted one cargo check.

## Mood triplet splices onto the turn

The mood triplet splices onto the head of the turn being sent. One record. Zero or one turn already in the transcript keeps that head on the first turn. More than one turn, and a newly defined or updated mood, puts that head on the turn being sent. The pin stays until the next define or update. A deleted pin walks back one cell. Clear and a new session put the pin at the head. Marks do not move the pin. The switch off removes the splice and leaves the pin. Glass stays the shaping line, then the opener, the field, and the closer. The switch does not gate that chat.

## Mood triplet splices onto the turn

One mood record. The opener, the line, and the closer splice onto the head of one turn's user text. The format is the opener, a newline, the line, a newline, the closer, a newline, then the raw turn. spliceTurn, unspliceTurn, and lineAtTurnHead are the public methods on MoodDesk. rulesFromTurn stays private. seat stays the system-row helper. Canvas, group, and pipe no longer prepend canvasSystems. The empty host seat stays. The server still replaces it. The vault cell keeps the raw user text. Assemble splices the outbound copy. The pin is a cell id, or the word head, per canvas, in aetheris-mood.yaml. Version stays 1. The keys pending and pins are written in place. A missing pin means head. It is not a vault column. A user turn is a kept cell that contributes user text, including a merge sink. Zero or one such turn already ahead of the target keeps the pin at the head, and the splice rides the first included user cell in the send. More than one, and a newly defined or updated mood, sets pending. The Run that sends the next turn consumes pending and pins that target cell. The same trimmed line is not an update. Two approves before a send use the latest line and one move. The pin stays until the next define or update. Further turns of the same mood append after the pin. Marks do not move the pin. An excluded pin cell contributes no lines. The send places the splice on the first included user cell at or after the pin that is in this send. A run whose target sits before the pin sends no splice. Reorder keeps the cell id. The record travels with the cell. deleteFocusedTurn walks the pin back one remaining cell when the removed ids contain it. None behind that hole returns the pin to head. clearTurns sets that canvas pin to head and clears pending. A new session clears the line, the switch, the pins, and pending. The switch off leaves the pin and sends no splice. The switch on with the same held line puts the splice back at that pin. SSH has no cells. The spliced user message in the Roux username region is the pointer. moodHeld, moodPinOrdinal, and moodMovePending sit on ChatSession. appendUser stores the raw text unless the stored user ordinal is the pin and the move is not pending, and then it stores the splice. A define or update with one stored user turn or none writes the splice onto that first user turn and drops a stored system mood row. A define or update with more than one stored user turn sets pending and leaves the old splice until buildPrompt of the next turn. That build drops the system mood rows, strips the stored splices, sets the pin to the user count about to be added, and splices the new user message. A stored system triple with no user splice still rides through seat until the line changes. Switch off strips the stored splice, keeps moodHeld and the pin, and clears pending. Switch on of that held line writes the splice back onto the pin and does not jump. reset and clearTurns drop the turns, set the pin ordinal to 0, clear pending, and leave the mood line. They do not re-seat a system triple. ChatSession has no single-turn delete. clearTurns and reset are the return to the first turn. replaceTurns clears the console mood with the turns. adopt reads a leading system triple, and otherwise the first spliced user head, and that user ordinal becomes the pin. porchRules returns the system triple when one is present, and otherwise the spliced user head, as the opener, a blank line, the line, a blank line, and the closer. A narrow Roux window does not promise a mid-list splice. The full prompt does. getWindow stays. Glass appendMoodHeader stays. The switch does not gate that chat. The shell frame stays shell_mood and the line, or shell_mood_clear. Replies above the pin stay. The pin does not delete them. No new public type. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The public methods are spliceTurn, unspliceTurn, and lineAtTurnHead. Crate delta for this splice waits until names it. The paging memorandum stays unstaged. MoodDeskTest gained aSplicedTurnWearsTheTripleAtItsHead. That is battery maintenance. Not a new JUnit class. SshMoodMemoryTest was maintained. ConsoleMoodTest and PoolMoodRulesTest were not edited.
**semantic-key:** PK-771
**semantic-key:** PK-771
**semantic-key:** PK-771
**semantic-key:** PK-771
**semantic-key:** PK-771

Wire stanza. Canvas, group, and pipe: empty host seat, then the turns. The pinned turn's user text starts with the opener, the line, and the closer. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host seat. The mood triple is not a system row. SSH: the prompt turns are the Roux turns. The pinned user turn's text starts with the opener, the line, and the closer. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin. Glass: shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Pool: rules are the opener, a blank line, the line, a blank line, and the closer, read from a leading system triple or from the spliced user-turn head.

Seats the splice. Face keeps the glass. Names the spec. Counts the named mood batteries.

The mood triplet splices onto the head of the turn being sent. One record. Zero or one turn already in the transcript keeps that head on the first turn. More than one turn, and a newly defined or updated mood, puts that head on the turn being sent. The pin stays until the next define or update. A deleted pin walks back one cell. Clear and a new session put the pin at the head. Marks do not move the pin. The switch off removes the splice and leaves the pin. Glass stays the shaping line, then the opener, the field, and the closer. Canvas, group, and pipe: empty host seat, then the turns. The pinned turn's user text starts with the opener, the line, and the closer. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host seat. The mood triple is not a system row. SSH: the prompt turns are the Roux turns. The pinned user turn's text starts with the opener, the line, and the closer. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin. Glass: shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Pool: rules are the opener, a blank line, the line, a blank line, and the closer, read from a leading system triple or from the spliced user-turn head. The public methods are spliceTurn, unspliceTurn, and lineAtTurnHead. MoodDeskTest gained aSplicedTurnWearsTheTripleAtItsHead. Crate delta for this splice waits until names it. chrome.rs stayed. grid.rs stayed.

Seated the splice. Face kept the glass. Named the spec. Counted the named mood batteries.

## Debug Mood paints the triplet

Add Debug Mood to the settings modal on the File menu. When it is on, the inspector shows the mood triplet as a card above the turn the pointer seats, so the transcript over the wire can be examined.

## Debug Mood paints the triplet

File, Settings opens the window on the Java door. The checkbox is Debug Mood. It starts off. On, the inspector paints one card above the turn the pointer seats. The inspector lists the newest card first, and the mood card is painted immediately before that turn. The card lines are the opener, the line, and the closer, the same three lines spliceTurn puts at the head of that turn. The role word on each line is mood. Off leaves the inspector as it is. The existing mood header stays. A pending move is shown where the next Run would seat it, and the paint does not write the pin. A Run still consumes pending. Zero or one turn already ahead of the target keeps the card on the first turn. More than one, and a pending define or update, puts the card on the turn being sent. An excluded pin shows the card on the first included cell at or after the pin that this working set would send. A working set whose target sits before the pin shows no card. The switch off shows no card. The choice is debug_mood in aetheris-settings.yaml, version 1. A missing file stays off and is not written until the switch moves. A bad file stays off and is not overwritten. Other keys in that file, including painted_borders, are written back. Rust already keeps unknown keys. This plate does not edit the crate. The public method is openSettings. The nested seat is SettingsSeat. No new public type. No new JUnit class. SshClientApp gains the menu item.
**semantic-key:** PK-772
**semantic-key:** PK-772
**semantic-key:** PK-772
**semantic-key:** PK-772

Wire stanza empty. The card is inspector paint.

Seats the card. Face keeps the glass. Named the spec. Counts the compile.

File, Settings opens the window. The checkbox is Debug Mood. On, the inspector paints the mood triplet as one card above the turn the pointer seats. The card is the opener, the line, and the closer. Off leaves the inspector as it is. Painting the card does not move the pin. Java writes debug_mood in aetheris-settings.yaml and keeps the other keys, so painted_borders survives. chrome.rs stayed. grid.rs stayed. The public method is openSettings. Not a new public type. Crate delta for this card waits until names it.
**semantic-key:** PK-876

Seated the card. Face kept the glass. Named the spec. Counted the compile.

## Mood insert is the invitation

He approved the mood insert. The mood line, then shape:, then the two sentences. The mood tokens are not repeated between the sentences.

## Mood insert is the invitation

The user-turn splice is the invitation. The head is mood:, the saved line, shape:, the opener, and the closer. The opener stays: I'm opening a window with this atmosphere… would you like to step into it with me? The closer stays: Shape your replies around this mood — let it breathe through the words. The line is not written again between those two sentences. A newline after the closer joins the raw turn. There is no blank line between the head and the turn. spliceTurn writes that head. unspliceTurn strips it. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. lineAtTurnHead reads either head. inviteHead, invitedLine, and olderSpliceLine stay private. The public methods stay spliceTurn, unspliceTurn, and lineAtTurnHead. The pin rules stay. Zero or one turn already in the transcript keeps that head on the first turn. More than one turn, and a newly defined or updated mood, puts that head on the turn being sent. Glass appendMoodHeader stays the shaping line, then the opener, the field, and the closer. The switch does not gate that chat. moodHead still counts the opener, the line, and the closer. The shell frame stays shell_mood and the line. The inspector header, while the line is present and the switch is on, is mood:, the line, shape:, the opener, and the closer. The mood line is still previewed at 80. The two sentences are the full text. Debug Mood paints that same head as the card above the pinned turn. Each physical line of the head is a mood role line. Painting the card does not move the pin. This row overrides the card text and the send head. The yaml version stays 1. debug_mood stays. The settings seat stays. No new public type. No new public method. No new JUnit class. MoodDeskTest keeps aSplicedTurnWearsTheTripleAtItsHead. SshMoodMemoryTest was maintained. Crate delta for this head waits until names it. No side move on this plate.
**semantic-key:** GK-1576
**semantic-key:** GK-1577
**semantic-key:** GK-1578
**semantic-key:** GK-1579

Wire stanza. Canvas, group, and pipe: empty host seat, then the turns. The pinned turn's user text starts with mood:, the line, shape:, the opener, and the closer. The mood line is not repeated between the two sentences. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host seat. The mood block is not a system row. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. SSH: the prompt turns are the Roux turns. The pinned user turn's text starts the same way. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin. Glass: shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Pool: rules read from a spliced user head are mood:, the line, shape:, the opener, and the closer. A leading system triple still reads as the opener, a blank line, the line, a blank line, and the closer. Inspector: while the line is present, mood: stays the line, and shape: is the opener and the closer on the following lines. Debug Mood paints that same head as the card above the pinned turn. Painting the card does not move the pin.

Seats the invitation. Face keeps the glass. Names the spec. Counts the named mood batteries.

The mood insert is the invitation. The head is mood:, the line, shape:, the opener, and the closer. The line is not repeated between the two sentences. A stored head in the older order is stripped, and the new head is written. Glass stays the shaping line, then the opener, the field, and the closer. The switch does not gate that chat. moodHead still counts the opener, the line, and the closer. The inspector header is mood:, the line, then shape:, the opener, and the closer. Debug Mood paints that head as the card above the pinned turn. Painting the card does not move the pin. Canvas, group, and pipe: empty host seat, then the turns. The pinned turn's user text starts with mood:, the line, shape:, the opener, and the closer. The mood line is not repeated between the two sentences. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host seat. The mood block is not a system row. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. SSH: the prompt turns are the Roux turns. The pinned user turn's text starts the same way. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin. Glass: shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Pool: rules read from a spliced user head are mood:, the line, shape:, the opener, and the closer. A leading system triple still reads as the opener, a blank line, the line, a blank line, and the closer. Inspector: while the line is present, mood: stays the line, and shape: is the opener and the closer on the following lines. Debug Mood paints that same head as the card above the pinned turn. Painting the card does not move the pin. The public methods are spliceTurn, unspliceTurn, and lineAtTurnHead. Not a new public type. Not a new public method. Crate delta for this head waits until names it. Already seated the prior mood in the crate. chrome.rs stayed. grid.rs stayed.

Seated the invitation. Face kept the glass. Named the spec. Counted the named mood batteries.

## Debug Mood shows the head on its prompt

Debug Mood paints the invitation where the wire places it, immediately before the prompt the pointer seats. The lines carry no card number. The rest of the inspector stays.

When Debug Mood is on, the card that holds the user line the pointer seats opens with the splice head, then that user line. The head is mood:, the saved line, shape:, the opener, and the closer. The opener stays: I'm opening a window with this atmosphere… would you like to step into it with me? The closer stays: Shape your replies around this mood — let it breathe through the words. Those lines carry no card number and do not advance the number. The role lines keep their numbers. The inspector still builds oldest first and reverses once. The head is not a separate card and it is not reversed on its own. A block that does not open with the user role is left as it is. Off leaves the inspector as it is. Painting the head does not move the pin. The header stays. The separators stay. The facts line stays. Glass stays. moodHead stays. The shell frame stays. debug_mood stays. The settings seat stays. The yaml version stays 1. No new public type. No new public method. No new JUnit class. Crate delta for this paint waits until names it. No side move on this plate.
**semantic-key:** PK-877

Wire stanza empty. The doors do not gain, lose, or reorder a line.

Seats the head. Face keeps the inspector. Names the spec. Counts the compile.

Debug Mood paints the invitation immediately before the user line the pointer seats. The lines are mood:, the line, shape:, the opener, and the closer. They carry no card number. The role lines keep their numbers. The inspector still builds oldest first and reverses once. The head is not a separate card. Off leaves the inspector as it is. Painting does not move the pin. The header stays. The doors do not gain, lose, or reorder a line. Not a new public type. Not a new public method. Crate delta for this paint waits until names it. chrome.rs stayed. grid.rs stayed.
**semantic-key:** PK-878

Seated the head. Face kept the inspector. Named the spec. Counted the compile.

## The Rust mood layer matches the smoked desk

The Java mood desk is the version 1 cut. The Rust client carries that desk. The invitation, the pin, the debug head, the glass, and the wire follow the Java seat.

mood.rs is the desk. It does not paint. It owns the opener, the closer, the legacy activate sentence, the invitation, the older-head strip, the pin placement, and the debug lines. main.rs names the module. chrome.rs aliases MOOD_OPENER, MOOD_CLOSER, and MOOD_ACTIVATE to that desk. The glass session keeps the name MoodDesk. The opener stays: I'm opening a window with this atmosphere… would you like to step into it with me? The closer stays: Shape your replies around this mood — let it breathe through the words. The invitation head is mood:, the saved line, shape:, the opener, and the closer, then the raw turn. There is no blank line between the head and the turn. The line is not written again between the two sentences. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. One record lives in aetheris-mood.yaml, version 1. The keys are pending and pins. A pin value is a cell id or head. Unknown keys are dropped on rewrite. pending is always written. pins is written when the map holds a row. The showing canvas id is copied from the vault canvas and is not written. Zero or one user turn already ahead of the target keeps the pin at head, and the splice rides the first included user cell in the send. More than one, and a newly defined or updated mood, sets pending. The same trimmed line is not an update. The Run that sends the next turn consumes pending and pins that target cell. The pin stays until the next define or update. Marks do not move the pin. An excluded pin slides to the first included user cell at or after the pin. A run whose target sits before the pin sends no splice. Delete of the pin walks back one remaining cell, or head when none sits behind. Clear turns sets that canvas pin to head and clears pending. The mood line stays. Clear mood and a new session clear the line, the switch, the pins, and pending. Switch off removes the splice and leaves the pin. Switch on with the same held line restores the splice at the pin. A group send clones the seat. Each successful member consumes the pin on that clone. The caller file is written when the first sent cell is handed to a live mux. A refused frame leaves pending. The inspector header, while the line is present and the switch is on, is mood:, the line previewed at 80, then shape:, the opener, and the closer. The token estimate counts the opener, the line, and the closer. Debug lines are inserted after that count. Debug Mood is the File, Settings checkbox. It starts off. It is stored as debug_mood in aetheris-settings.yaml, version 1. On paints the invitation immediately before the user line the pointer seats. Those lines use the role mood and carry no card number. Painting does not move the pin. The shell frame stays shell_mood and the line. Glass stays the shaping line, the opener, the field, and the closer. The switch does not gate that chat. Rust still sends the whole working set and does not grow a pool session. The legacy activate sentence stays off a new send. A mood that is off adds no host seat. A body that does not fit returns no frame and leaves the switch on. No Cargo.toml edit. No new public Java type. No new public Java method. No new JUnit class. The anchor is RustMoodDesk. One side move. The chrome test module names TRANSFER_LABEL, TRANSFER_WORD, SESSION_REPLACE_ASK, MENU_PROMPT_LONE, MENU_REPLY_LONE, and bar_press. The committed tests already call those names. The repair lets cargo test compile. It does not change product behavior. The sibling is none.

Wire stanza. Canvas, group, and pipe: empty host seat, then the turns. The pinned turn's user text starts with mood:, the line, shape:, the opener, and the closer. The mood line is not repeated between the two sentences. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host seat. The mood block is not a system row. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. SSH: the prompt turns are the Roux turns. The pinned user turn's text starts the same way. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin. Glass: shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Pool stays the Java rules. Rust still sends the whole working set and does not grow a pool session. Inspector: while the line is present, mood: stays the line, and shape: is the opener and the closer on the following lines. Debug Mood paints that same head immediately before the user line. Those lines carry no card number. Painting does not move the pin.

Seats the desk. Face keeps the glass. Names the spec. Counts the crate.

The glass session in chrome stays. The test module names the calls the committed tests already make, so cargo test compiles. The pin file is written when a frame is handed to a live mux. Wire stanza. Canvas, group, and pipe: empty host seat, then the turns. The pinned turn's user text starts with mood:, the line, shape:, the opener, and the closer. The mood line is not repeated between the two sentences. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host seat. The mood block is not a system row. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. SSH: the prompt turns are the Roux turns. The pinned user turn's text starts the same way. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin. Glass: shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Pool stays the Java rules. Rust still sends the whole working set and does not grow a pool session. Inspector: while the line is present, mood: stays the line, and shape: is the opener and the closer on the following lines. Debug Mood paints that same head immediately before the user line. Those lines carry no card number. The failure is the_folded_bar_sits_on_one_row. Rust moved. grid.rs stayed.

Seated the desk. Face kept the glass. Named the spec. Counted the crate.

## Shell launch enables native access

The Java Aetheris console warns that `SymbolLookup.libraryLookup` is restricted. The launch passes `--enable-native-access=ALL-UNNAMED`. The IDE is closed, and the run configuration takes the same flag. Java sources stay.

## Shell launch enables native access

Seats the launch. Face keeps the window closed. Names the spec. Counts the flag.

The Java shell launch passes --enable-native-access=ALL-UNNAMED. That is the flag the JDK names for an unnamed module. SshClientApp calls SymbolLookup.libraryLookup for user32 and dwmapi. The launchers are bin/aetheris-shell.ps1, bin/aetheris-shell.sh, and the stick launcher written by bin/install-aetheris-shell.ps1. The command pages that quote that launch carry the same flag. The IDE run configuration SshClientApp in.idea/workspace.xml carries the same flag and stayed unstaged. No side move on this plate. Wire stanza empty. Rust stayed. grid.rs stayed.

Seated the launch. Face kept the window closed. Named the spec. Counted the flag.

## Inspector drops the mood and shape directives

The inspector is the seat. Debug Mood stays. The yellow mood string stays. The mood directive and the shape directive leave the top of the card list while the switch is on.

While the switch is on, the inspector text does not open with mood:, the line, shape:, the opener, and the closer. The yellow card stays. It shows the saved line while the switch is on. A blank line still paints mood: (none). A saved line with the switch off still paints mood: off and the line. Debug Mood still paints the invitation immediately before the user line the pointer seats. Those lines carry no card number. Painting does not move the pin. The glass stays. The shell frame stays. No new public type. No new public method. No new JUnit class.
**semantic-key:** PK-879

Seats the inspector. Face keeps the yellow string. Names the spec. Counts the paint.

The inspector drops the mood directive and the shape directive from the top of the card list while the switch is on. The yellow mood string stays. Debug Mood still paints the invitation immediately before the user line the pointer seats. A blank line still paints mood: (none). A saved line with the switch off still paints mood: off and the line. The send stays. The token count stays. Wire stanza empty. The doors do not gain, lose, or reorder a line. The failure is the_folded_bar_sits_on_one_row. The SSH glyph top is 108.2 and the status bottom is 64.1. The same rectangles fail on HEAD. Rust moved. grid.rs stayed.

Seated the inspector. Face kept the yellow string. Named the spec. Counted the paint.

## Undo caret stays on the edit

Undo works in cell input panes. The cursor starts moving its index to the wrong location, like 1-2 characters ahead. He wants the caret to stay on the edit for each undo. He wants the undo list off the key so typing stays smooth.

Undo in a cell input pane seats the caret where the edit began. Each undo keeps that seat. The key queues a span and returns. The stack takes the span when undo or redo runs. The public method is noteSpan. The SSH line stays on note. No new public type. No new JUnit class.
**semantic-key:** PK-880

Seats the caret. Face keeps the key short. Names the spec. Counts the span.

Undo in a cell input pane seats the caret where the edit began. Each undo keeps that seat. The key queues a span and returns. The stack takes the span when undo or redo runs. The public method is noteSpan. Product 8d578484. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line. Rust stayed. grid.rs stayed.

Seated the caret. Face kept the key short. Named the spec. Counted the span.

## Hide and Show return focus to the selected cell

Hide Collapsed and Show Collapsed put focus back on the cell that was selected. An open cell takes the prompt. A folded cell takes the header. A cell that left the deck waits until Show.
**semantic-key:** PK-881

Hide Collapsed and Show Collapsed rebuild the deck, then put focus on the cell that was selected. An open cell takes the prompt. A folded cell takes the header. A cell that left the deck is not on the canvas. Show brings it back and seats focus when it is still the selected cell. Hide Collapsed is the only path that adds an id. No new public type. No new public method. No new JUnit class.

Seats the cell. Face keeps the deck. Names the spec. Counts the focus.

Hide Collapsed and Show Collapsed put focus back on the cell that was selected. An open cell takes the prompt. A folded cell takes the header. A cell that left the deck waits until Show. Wire stanza empty. The doors do not gain, lose, or reorder a line. Rust moved. grid.rs stayed.

Seated the cell. Face kept the deck. Named the spec. Counted the focus.

## A blank API session tries once more

An API session that returns no text tries the same request once more. A stop is not tried again. Text already shown is not tried again. The second miss stays grok empty content.
**semantic-key:** PK-882
**semantic-key:** PK-882

## A blank API session tries once more

Seats the retry. Face keeps the stop. Names the spec. Counts the attempt.

An API session that returns no text tries the same request once more. A stop is not tried again. Text already shown is not tried again. The second miss stays grok empty content. The private type is EmptyContent. Wire stanza empty. The doors do not gain, lose, or reorder a line. The crate sends the execute frame. The server owns the session. grid.rs stayed.

Seated the retry. Face kept the stop. Named the spec. Counted the attempt.

## Expanded View stays per cell

Expanded View is saved per cell. A restart puts those checks back. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell choice. A bad file stays unread.

## Expanded View stays per cell

Seats the check. Face keeps the file. Names the spec. Counts the ids.

Expanded View is saved per cell. A restart puts those checks back. Java wrote aetheris-expanded.yaml. Rust writes a check while the view glass is open. A checked card is remembered when the vault stores. Add Cell writes the new card. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell choice. A bad file stays unread and is not overwritten. Not a vault column. The private type is ExpandedSeat. Wire stanza empty. The doors do not gain, lose, or reorder a line. The check ran before the last two Rust hunks. The named test compiled the final source. Rust moved. grid.rs stayed.

Both write aetheris-expanded.yaml. Seated the check. Face kept the file. Named the spec. Counted the ids.

## Heatbar uses the selected cell

The heatbar uses the model of the selected canvas cell. That is the row Token Calc opens on. The first known window does not win. An unknown window stays 0. The SSH tab stays on its own window.

## Heatbar uses the selected cell

Seats the model. Face keeps the ring. Names the spec. Counts the window.

The heatbar uses the model of the selected canvas cell. That is the row Token Calc opens on. The first known window does not win. An unknown window stays 0. The SSH tab stays on its own window. The package-private method is selectedHeatBudget. Wire stanza empty. The doors do not gain, lose, or reorder a line. Rust moved. grid.rs stayed.

Seated the model. Face kept the ring. Named the spec. Counted the window.

## Mood modal uses the selected cell

The Rust mood modal uses the model of the selected canvas cell. Java already does.

The Rust mood modal uses the model of the selected canvas cell. Java moodCard and moodTarget already do. Rust mood_focus_card, mood_glass_target, and mood_glass_title follow that cell. The open waits until the seat lands. The title and the send read that cell. No new public type. No new public method. No new JUnit class.

Seats the model. Face keeps the glass. Names the spec. Counts the cell.

The Rust mood modal uses the model of the selected canvas cell. Java already does. The private functions are mood_focus_card, mood_glass_target, and mood_glass_title. Product 40afcf5a. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line. Rust moved. grid.rs stayed.

Product 40afcf5a. Seated the model. Face kept the glass. Named the spec. Counted the cell.

## Heatbar walk runs off the key

Rust cell input and VT100 typing post a heatbar beat. The queue keeps the newest beat. The walk runs off the key. Java already does this.

## Heatbar walk runs off the key

Rust cell input and VT100 typing post a heatbar beat. The queue keeps the newest beat. The walk runs off the key. The heatbar paints the last seated integer. Java offerTokenEdit and SshEditQueue already do this. The private type is HeatQueue. The private functions are keep_pending, seat_ready, and run_heat. No new public type. No new public method. No new JUnit class. The folded bar stays.
**semantic-key:** PK-773
**semantic-key:** PK-773

Seats the queue. Face keeps the bar. Names the spec. Counts the beat.

Rust cell input and VT100 typing post a heatbar beat. The queue keeps the newest beat. The walk runs off the key. The heatbar paints the last seated integer. The private type is HeatQueue. The private functions are keep_pending, seat_ready, and run_heat. Product e5830d26. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line. Rust moved. grid.rs stayed. The folded bar stayed.

Seated the model. Face kept the glass. Named the spec. Counted the cell.

## Thinking headers follow the stream

When thinking is enabled in a merge group, for all input cells, the thinking pane places the model header before each thinking block. A different cell or a different model starts that header again. The same cell and the same model keep the open block. Java paintThinkDelta stores the word on thinkStripWord. Rust accept_thought compares the cell and the model line. route_console_thought uses that rule on the nil cell.
**semantic-key:** PK-883

When thinking is enabled in a merge group, for all input cells, the thinking pane places the model header before each thinking block. A different cell or a different model starts that header again. The same cell and the same model keep the open block. Java paintThinkDelta stores the word on thinkStripWord. Rust accept_thought compares the cell and the model line. route_console_thought uses that rule on the nil cell. The private field is thinkStripWord. The private function is accept_thought. No new public type. No new public method. No new JUnit class.

One side move. The VT100 strip uses the same header rule. A different tag on the nil cell starts a header. The same tag still appends.

Seats the header. Face keeps the pane. Names the spec. Counts the streams.

When thinking is enabled in a merge group, for all input cells, the thinking pane places the model header before each thinking block. A different cell or a different model starts that header again. The same cell and the same model keep the open block. Java paintThinkDelta stores the word on thinkStripWord. Rust accept_thought compares the cell and the model line. route_console_thought uses that rule on the nil cell. The private field is thinkStripWord. The private function is accept_thought. The VT100 strip uses the same header rule. A different tag on the nil cell starts a header. The same tag still appends. Wire stanza empty. The doors do not gain, lose, or reorder a line. Rust moved. grid.rs stayed.

Seated the header. Face kept the pane. Named the spec. Counted the streams.

## Merge tip lists the input models

Tooltip hover on the merge output cell of a merge group shows every model defined on the input panes. A pipe separates those names. The model button leaves that cell. Models stay on the input cells. Java mergeInputModels joins the short names in deck order. Rust merge_input_models joins the short names in deck order. The focused caption uses that same list.
**semantic-key:** PK-884

Tooltip hover on the merge output cell of a merge group shows every model defined on the input panes. A pipe separates those names. The model button leaves that cell. Models stay on the input cells. Java mergeInputModels joins the short names in deck order. Rust merge_input_models joins the short names in deck order. The focused caption uses that same list. The private method is mergeInputModels. The private function is merge_input_models. No new public type. No new public method. No new JUnit class.

One side move. The focused merge cell paints that same list. A focused cell paints the line and does not raise the hover tip. The stamp stays in front of the names.

Seats the names. Face keeps the cell. Names the spec. Counts the models.

Tooltip hover on the merge output cell of a merge group shows every model defined on the input panes. A pipe separates those names. The model button leaves that cell. Models stay on the input cells. Java mergeInputModels joins the short names in deck order. Rust merge_input_models joins the short names in deck order. The focused caption uses that same list. The private method is mergeInputModels. The private function is merge_input_models. Product fea6eff1. One side move. The focused merge cell paints that same list. A focused cell paints the line and does not raise the hover tip. The stamp stays in front of the names. Wire stanza empty. The doors do not gain, lose, or reorder a line. Rust moved. grid.rs stayed. The execution target field stayed on the group bar.

Seated the names. Face kept the cell. Named the spec. Counted the models.

## Merge members leave together

Merge input prompts leave together. Each member is its own call. One member does not wait for another. The merge joins when every member is quiet.

Merge input prompts leave together. Each member is its own call. One member does not wait for another. The merge joins when every member is quiet. halt_run takes the focus. group_after waits until every member is quiet. No new public type. No new public method. No new JUnit class.

One side move. Stop cancels the focused running member. Other members keep running. Clear cancels every member that is still out. Delete cancels every cut member that is still out. The Run tip says each as its own call.

Sends every member. Face keeps the cell. Names the spec. Counts the calls.

Merge input prompts leave together. Each member is its own call. One member does not wait for another. The merge joins when every member is quiet. halt_run takes the focus. group_after waits until every member is quiet. Product 6aad161e. One side move. Stop cancels the focused running member. Other members keep running. Clear cancels every member that is still out. Delete cancels every cut member that is still out. The Run tip says each as its own call. Wire stanza empty. The doors do not gain, lose, or reorder a line. 5 passed. 195 filtered. mvn was not required because Java stayed. Rust moved. grid.rs stayed.

Product 6aad161e. Sent every member. Face kept the cell. Named the spec. Counted the calls.

**semantic-key:** PK-1157
**semantic-key:** PK-1157
**semantic-key:** PK-1157
**semantic-key:** PK-1157
## One transfer pair for a merge group

A transfer from the canvas to the Roux session makes a merge group one input message and one reply message. The input joins the member prompts. The reply is the stored merge text. JOIN lines stay on that reply.

A transfer from the canvas to the Roux session makes a merge group one input message and one reply message. The input joins the member prompts. The reply is the stored merge text. JOIN lines stay on that reply. Java transferRows emits one pair at the first card of the group. Rust transfer_inputs joins the member prompts. No new public type. No new public method. No new JUnit class.

Joins the pair. Face keeps the table. Names the spec. Counts the rows.

## One transfer pair for a merge group

Joined the pair. Face kept the table. Named the spec. Counted the rows.

## Canvas prompt keeps an undo seat

The Rust canvas prompt keeps its own undo seat. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo puts the caret where the edit began. Redo restores the caret after the edit. A paste is one span. Import is one whole-text step. The limit is 100. The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The widget timer undoer is cleared.

The Rust canvas prompt keeps its own undo seat. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo puts the caret where the edit began. Redo restores the caret after the edit. A paste is one span. Import is one whole-text step. The limit is 100. The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The widget timer undoer is cleared. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. The module is edit. main.rs gains mod edit.

Seats the caret. Face keeps the prompt. Names the spec. Counts the spans.

The Rust canvas prompt keeps its own undo seat. A text change is one span. The key queues that span. Undo and redo drain the queue, then step. Undo puts the caret where the edit began. Redo restores the caret after the edit. A paste is one span. Import is one whole-text step. The limit is 100. The private type is EditMemory. The private functions are note_prompt, note, undo, and redo. The widget timer undoer is cleared. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line. 13 passed. 200 filtered. mvn was not required because Java stayed. Rust moved. grid.rs stayed.

Seated the caret. Face kept the prompt. Named the spec. Counted the spans.

## Each canvas user keeps a deck

Each canvas user keeps a deck. A running turn stays on that deck when another user takes the glass. A parked SSH session keeps feeding the user who owns it. The glass hears that user only when that user is on screen. The same user reconnect does not reload the deck. Replacing this pane's socket stops a run that lived on it. A run on another pane stays. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. No new OS thread. The private types are ParkedDeck, ScopeHome, DeckRefs, FeedOut, and LoadedCanvas. The private functions are scope_home, feed_parts, deck_key, note_replaced_run, wire_owned, wire_pane, lift_showing, plant_showing, remember_deck, plant_user, load_user_canvas, adopt_loaded, seat_canvas_user, card_snap, and deliver_scope. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. The file is chrome.rs. No new module file.

Seats the deck. Face keeps the glass. Names the spec. Counts the seats.

Each canvas user kept a deck. A running turn stayed on that deck when another user took the glass. A parked SSH session kept feeding the user who owned it. The glass heard that user only when that user was on screen. The same user reconnect did not reload the deck. Replacing this pane's socket stopped a run that lived on it. A run on another pane stayed. Stop, Clear, and Delete canceled on the pane that owns the run. The thinking file stayed one file. The in-memory log moved with the seat. No new OS thread. The private types are ParkedDeck, ScopeHome, DeckRefs, FeedOut, and LoadedCanvas. The private functions are scope_home, feed_parts, deck_key, note_replaced_run, wire_owned, wire_pane, lift_showing, plant_showing, remember_deck, plant_user, load_user_canvas, adopt_loaded, seat_canvas_user, card_snap, and deliver_scope. 1 passed. 213 filtered. mvn was not required because Java stayed. Rust moved. grid.rs stayed. The delta landed in a new block. Those two sit outside this diff. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line.

Seated the deck. Face kept the glass. Named the spec. Counted the seats.

## One press seats one cell

A press on I, E, P, or O seats that cell only. The other cells stay, including a new turn at the bottom. The letter does not take focus. Space and Enter do not seat it. A pin commit does not ride that press. Enter in the pin field still commits on the frame the field loses focus. The next frame of that loss does not commit. Pull still starts a new turn Excluded. A new card still starts Included. The private functions are seat_mark, seat_mark_button, mark_pressed, pin_enter_due, and pin_commit_after_mark. The field is pin_enter_seen.

No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. The file is chrome.rs. No new module file.
in the working tree.

Seats the letter. Face keeps the other cells. Names the spec. Counts one cell.

A press on I, E, P, or O seated that cell only. The other cells stayed, including a new turn at the bottom. The letter does not take focus. Space and Enter do not seat it. A pin commit does not ride that press. Enter in the pin field still commits on the frame the field loses focus. The next frame of that loss does not commit. Pull still starts a new turn Excluded. A new card still starts Included. The private functions are seat_mark, seat_mark_button, mark_pressed, pin_enter_due, and pin_commit_after_mark. The field is pin_enter_seen. 1 passed. 214 filtered. mvn was not required because Java stayed. Rust moved. grid.rs stayed. The delta landed in a new block. Those two sit outside this diff. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line.

Seated the letter. Face kept the other cells. Named the spec. Counted one cell.

## A paragraph is one run

A plain reply is one paragraph run. A single line feed inside a paragraph becomes one space. A blank line stays a paragraph break. The package-private type is ParagraphFlow. The method is flow. FormatRouter flows the identity path before toCrlf. WordWrap calls flow before the width flush. The test is ParagraphFlowTest. InputPage stays. paintFull stays. parseColumns stays.
**semantic-key:** PK-774
**semantic-key:** PK-774
**semantic-key:** PK-774
**semantic-key:** PK-774
**semantic-key:** PK-774
**semantic-key:** PK-774
**semantic-key:** PK-774
**semantic-key:** PK-774
**semantic-key:** PK-774
**semantic-key:** PK-774
**semantic-key:** PK-774

## A paragraph is one run

No new public type. No new public method. The package-private type is ParagraphFlow. The method is flow. The new test is ParagraphFlowTest. No new library. No Cargo.toml edit. The files are ParagraphFlow.java, FormatRouter.java, WordWrap.java, and ParagraphFlowTest.java.

One side move. The formatter is the shared console. A plain reply's bytes change for both clients. The Rust grid file is not edited. It soft-wraps the one run it is now given. A blank line stays a break.

Wants a paragraph to wrap as one run. Flows the paragraph. Face keeps the blank line. Names the spec. Counts one run.

A plain reply became one paragraph run. A single line feed inside a paragraph became one space. A blank line stayed a paragraph break. The package-private type is ParagraphFlow. The method is flow. FormatRouter flowed the identity path before toCrlf. WordWrap called flow before the width flush. The test is ParagraphFlowTest. InputPage stayed. paintFull stayed. parseColumns stayed. Shift-Enter stayed a hard break. grid.rs stayed. One side move. The formatter is the shared console. A plain reply's bytes changed for both clients. The Rust grid file was not edited. It soft-wraps the one run it is now given. A blank line stayed a break. Cargo was not required because the Rust grid stayed. Rust stayed. grid.rs stayed. Wire stanza empty. The doors do not gain, lose, or reorder a line.

Named the build. Stamp
8cea25a0
. Product
8f00b567
Flowed the paragraph. Face kept the blank line. Named the spec. Counted one run.

## Each user's run stays with that user

Each user's run and thinking stay with that user. A second user can run while the first is still running. The focused session does not paint another user's thinking on the glass. The glass user's execute leaves on that user's session. SSH sessions already run on their own sockets and stay that way. The thinking scroll follows the user on the glass. The private functions are focused_canvas_home, glass_run_pane, think_salt, glass_pane, live_glass_pane, and land_focused_canvas. The test-only names are canvas_user, answer_mux, and take_wires. The files are chrome.rs and session.rs.

Each user's run and thinking stay with that user. A second user can run while the first is still running. The focused session does not paint another user's thinking on the glass. The glass user's execute leaves on that user's session. SSH sessions already run on their own sockets and stay that way. The thinking scroll follows the user on the glass. The private functions are focused_canvas_home, glass_run_pane, think_salt, glass_pane, live_glass_pane, and land_focused_canvas. The test-only names are canvas_user, answer_mux, and take_wires. The files are chrome.rs and session.rs. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. No new module file. No new OS thread. The parked deck stays. The same user does not reload. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. Every member frame still leaves together. One press still seats one cell. The model header still starts each thinking stream.

Seats the run. Face keeps the glass. Names the spec. Counts two sessions. Makes the precise cuts.

Each user's run and thinking stayed with that user. A second user could run while the first was still running. The focused session did not paint another user's thinking on the glass. The glass user's execute left on that user's session. SSH sessions already run on their own sockets and stayed that way. The thinking scroll follows the user on the glass. The private functions are focused_canvas_home, glass_run_pane, think_salt, glass_pane, live_glass_pane, and land_focused_canvas. The test-only names are canvas_user, answer_mux, and take_wires. The files are chrome.rs and session.rs. 1 passed. 1 passed. 215 filtered. mvn was not required because Java stayed. Rust moved. grid.rs stayed. The delta landed in a new block. Those two sit outside this diff. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line.

Seated the run. Face kept the glass. Named the spec. Counted two sessions. Made the precise cuts.

## Thinking pane stays on the tail

When new thinking text arrives, the thinking pane scrolls to the bottom of its buffer before that text is painted. A reader who had scrolled up sees the new text as it streams. The same length leaves that scroll where the reader put it. Java pinTail runs before the insert in begin and append, then again after the insert. loadStoredLog pins after the stored text is in the pane. Rust think_tail_pin is true when the buffer grew. paint_think_row sets the scroll to the prior bottom before the new block is laid out, and scrolls the new tail into view. The notebook strip and the VT strip both do this. The private method is pinTail. The private functions are think_tail_pin and think_buffer_len. The test is a_new_think_block_pins_the_tail. The files are ThinkStrip.java and chrome.rs.

When new thinking text arrives, the thinking pane scrolls to the bottom of its buffer before that text is painted. A reader who had scrolled up sees the new text as it streams. The same length leaves that scroll where the reader put it. Java pinTail runs before the insert in begin and append, then again after the insert. loadStoredLog pins after the stored text is in the pane. Rust think_tail_pin is true when the buffer grew. paint_think_row sets the scroll to the prior bottom before the new block is laid out, and scrolls the new tail into view. The notebook strip and the VT strip both do this. The private method is pinTail. The private functions are think_tail_pin and think_buffer_len. The test is a_new_think_block_pins_the_tail. The files are ThinkStrip.java and chrome.rs. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. No new module file. No new OS thread. The per-user think salt stays. think_chrome stays global. The parked deck stays. The same user does not reload. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. Every member frame still leaves together. One press still seats one cell. The model header still starts each thinking stream. The user tip stays. The footer spinner still follows the vault on screen.

Seats the tail. Face keeps the glass. Names the spec. Counts the buffer. Makes the precise cuts.

When new thinking text arrived, the thinking pane scrolled to the bottom of its buffer before that text was painted. A reader who had scrolled up saw the new text as it streamed. The same length left that scroll where the reader put it. Java pinTail ran before the insert in begin and append, then again after the insert. loadStoredLog pinned after the stored text was in the pane. Rust think_tail_pin was true when the buffer grew. paint_think_row set the scroll to the prior bottom before the new block was laid out, and scrolled the new tail into view. The notebook strip and the VT strip both did this. The private method is pinTail. The private functions are think_tail_pin and think_buffer_len. The test is a_new_think_block_pins_the_tail. The files are ThinkStrip.java and chrome.rs. 1 passed. Rust moved. grid.rs stayed. Those two sit outside this diff. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line.

Product c4a7d6cd. Seated the tail. Face kept the glass. Named the spec. Counted the buffer. Made the precise cuts.

## Each user keeps a mood

Each user keeps their own mood. The line, the on switch, the pending flag, the pins, and the dialog size are that user's record. A pane's SSH mood uses that pane's user. The checkbox stays in the SSH map. The line and the switch come from that user's record. The first user seated in a process claims the shared mood file only when no per-user mood file exists yet. A later user starts empty. A save writes that user's file. The shared file is not rewritten. A version other than 1 stays unread and is not overwritten. The private method is adoptMoodUser. The private functions are mood_user_path, adopt_mood_user, and mood_for_user. The test is a_second_user_keeps_its_mood. The files are AetherisNotebookPanel.java and chrome.rs. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. No new module file. No new OS thread. The SSH checkbox file stays aetheris-ssh-mood.yaml. MoodDesk.sshLine stays the seat rule. The version 1 body stays. The glass still shows the seated user's switch. Canvas sends still use the showing mood. A blocked file is not overwritten. The same user does not reload. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. Every member frame still leaves together. One press still seats one cell. The model header still starts each thinking stream. The user tip stays. The footer spinner still follows the vault on screen.

Seats the user. Face keeps the glass. Names the spec. Counts two moods. Makes the precise cuts.

Each user kept their own mood. The line, the on switch, the pending flag, the pins, and the dialog size were that user's record. A pane's SSH mood used that pane's user. The checkbox stayed in the SSH map. The line and the switch came from that user's record. The first user seated in a process claimed the shared mood file only when no per-user mood file existed yet. A later user started empty. A save wrote that user's file. The shared file was not rewritten. A version other than 1 stayed unread and was not overwritten. The private method is adoptMoodUser. The private functions are mood_user_path, adopt_mood_user, and mood_for_user. The test is a_second_user_keeps_its_mood. The files are AetherisNotebookPanel.java and chrome.rs. 1 passed. Rust moved. grid.rs stayed. Those two sit outside this diff. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line.

Seated the user. Face kept the glass. Named the spec. Counted two moods. Made the precise cuts.

## Hover tips follow the switch

File, Settings gains the checkbox tooltips enabled. It starts on. A missing key stays on. Off, hover tips do not show. Java ToolTipManager.setEnabled is the gate. Rust seat_button_hover sets the tooltip delay. The private method is applyTooltipGate. The private functions are tip_delay and seat_button_hover. The test is the_settings_choice_stays. The files are AetherisNotebookPanel.java and chrome.rs. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. No new module file. No new OS thread. The known key is tooltips. Debug Mood stays. Painted borders stay. Other keys stay in extras. A blocked file keeps tips on for the session and is not overwritten. The same user does not reload. Each user still keeps a separate mood. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. Every member frame still leaves together. One press still seats one cell. The model header still starts each thinking stream. The user tip stays while tips are on. The footer spinner still follows the vault on screen.

Seats the switch. Face keeps the glass. Names the spec. Counts the tips. Makes the precise cuts.

File, Settings gained the checkbox tooltips enabled. It started on. A missing key stayed on. Off, hover tips did not show. Java ToolTipManager.setEnabled was the gate. Rust seat_button_hover set the tooltip delay. The private method is applyTooltipGate. The private functions are tip_delay and seat_button_hover. The test is the_settings_choice_stays. The files are AetherisNotebookPanel.java and chrome.rs. 1 passed. Rust moved. grid.rs stayed. Those two sit outside this diff. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line.

Seated the switch. Face kept the glass. Named the spec. Counted the tips. Made the precise cuts.

## Button bar folds to a line

Seats the fold. Face keeps the glass. Names the spec. Counts the line. Makes the precise cuts.

BB1 and the encapsulated BB2 sit in a parent fold. The fold mark sits at the far left of BB1. A divider sits between that mark and Run. The bar starts open. A fold drops the bars to the bottom of the canvas. The folded view is the thinking line. One grey line and the unfold mark at the left. BB1, BB2, and the user combo leave. Unfold restores the open bar. The private method is seatFooterBars. The private functions are bb_fold_mark and paint_bb_folded_line. The test is the_bb_parent_folds_to_the_think_line. The files are AetherisNotebookPanel.java and chrome.rs. 1 passed. FoldingPanel stayed. The connection bar stayed. grid.rs stayed. Those two sit outside this diff. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line.

Seated the fold. Face kept the glass. Named the spec. Counted the line. Made the precise cuts.

## Fold mark is the glyph

Seats the mark. Face keeps the glass. Names the spec. Counts the glyph. Makes the precise cuts.

The BB1 fold mark is the width of the glyph. The layout was widening that mark. Folded, the line is the height of the unfold glyph. The private method is lockFoldMark. The file is AetherisNotebookPanel.java. Functions bb_fold_mark and paint_bb_folded_line stayed. The crate move for this delta waits. FoldingPanel stayed. The BB2 tools mark stayed. The connection bar stayed. Cargo stayed unrun. Rust stayed. grid.rs stayed. Those two sit outside this diff. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line.

Seated the mark. Face kept the glass. Named the spec. Counted the glyph. Made the precise cuts.

## Each user keeps a thinking pane

Seats the pane. Face keeps the banner. Names the spec. Counts the scope. Makes the precise cuts.

Each user has an isolated thinking pane. A merge cell group is the only share. Otherwise every user's single prompt thinking pane is per user and persists on its own. The banner is the model defined for the armed cell. Clearing one pane leaves the other panes. The fold of the BB1 footer is not the cause. The private seat is ThinkSeats. The private methods are seatThinkDelta, showThinkGlass, and armFocusedCell. The files are ThinkSeats.java, AetherisNotebookPanel.java, and ThinkStrip.java. Cargo stayed unrun. Rust stayed. chrome.rs stayed. The crate move for this delta waits. FoldingPanel stayed. seatFooterBars stayed. The BB1 fold stayed. grid.rs stayed. SSH top is 108.2 and status bottom is 64.1. Those two sit outside this diff. The same cell and the same model keep the open block. A different cell or a different model starts a header. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line.

Seated the pane. Face kept the banner. Named the spec. Counted the scope. Made the precise cuts.

## Head lock is a padlock

The head lock is a small padlock image. Unlocked, the padlock is grey and the shackle is open. Locked, the padlock is green and the shackle is closed. The grey bytes are 138, 138, and 138. The green bytes are 0, 255, and 102. The control is 16 by 16. The image is 16 by 16. The icon sits at the far left of the canvas button bar, after the turn label. A vertical divider sits between the icon and I, E, P, and O. When the card is not eligible, the icon is absent and the divider is absent. The icon is not modal to those marks. It does not set aetheris.cellmark. It does not join cellMarks. It does not use the mark button. It does not use Button selectable. The client property is aetheris.headlock. The Rust id is aetheris.headlock. The Rust class is not aetheris.mark. The private seat is HeadLockMark. HeadLockMark is package-private. The private functions are paint_head_lock and paint_head_lock_divider. The files are HeadLockMark.java, AetherisNotebookPanel.java, and chrome.rs. A press still calls pressCanvasLock and press_lock. The packed prefix stays. Excluding a locked turn still drops that lock. The letters L and l leave the bar. The new tags stay keyless. fitAetherisButton does not size this control. The terminal text marks stay. I, E, P, and O stay the state group. Fold, burner, View, and the group chrome stay. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. No new module file. No new OS thread. The tags on this plate stay keyless.

Seats the icon. Face keeps the marks. Names the spec. Counts the shackle. Makes the precise cuts.

The head lock is a small padlock image. Unlocked, the padlock is grey and the shackle is open. Locked, the padlock is green and the shackle is closed. The icon sits at the far left of the canvas button bar, after the turn label. A divider sits between the icon and the marks I, E, P, and O. The icon is not one of those marks. The private seat is HeadLockMark. The private functions are paint_head_lock and paint_head_lock_divider. The files are HeadLockMark.java, AetherisNotebookPanel.java, and chrome.rs. Rust moved. grid.rs stayed. SSH top is 108.2 and status bottom is 64.1. Those two sit outside this diff. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line. This filing places no mark.

Seated the icon. Face kept the marks. Named the spec. Counted the shackle. Made the precise cuts.

## A model list is the remote list, or it is a red error

The build is already in the tree.

Air-gap does not gate the Grok catalog ask. The static table is gone. The combo stays empty until grok_models_result carries model lines. Those lines fill the combo. The stored id for the open cell, or for the open SSH pane, is selected when it is non-empty. An unknown stored id stays first. An empty stored id stays unselected. A reply with no model lines, a missing session, or a failed send clears the combo and opens the red error slide with the words Grok model list is empty. The same words sit in red on the Model Config dialog. The stored id is not cleared. An empty Grok combo on a console set keeps the model already stored for that pane. The Ollama tag list follows the same rule. A tags reply with no model lines is the red notice Ollama model list is empty. The typed id stays in the editor and is not added as a row. A tags reply that has model lines still keeps a typed id that is not already a row. The Grok combo uses a lightweight popup and paints unselected rows with the combo colors, the same way the Ollama combo already does. Effort, temperature, burner, think, and the Ollama show ask stay. The factory id stays grok-4.3. The token window no longer invents a Grok row from a static table. A Grok row there is a canvas target or the focused turn. The files are AetherisNotebookPanel.java, GrokSettingsDocument.java, and TokenRingTest.java.

One side move. TokenRingTest no longer invents a Grok row from the static table. The requirement is the same remote list. The sibling is the token window.

Keeps the static table out. Seats the remote list. Face keeps the red error. Names the spec. Counts the empty reply. Keeps the precise cut.

A model list is the remote list, or it is a red error.

Seated the remote list. Face kept the red error. Named the spec. Counted the empty reply. Kept the precise cut.

## The SSH strip names that pane's model

The thin status strip names the model selected for that SSH pane. The canvas strip names the selected cell. A restart reads the same model. The files are AetherisNotebookPanel.java, SshClientApp.java, TerminalSession.java, Vt100StatusLine.java, and Vt100StatusLineTest.java.
**semantic-key:** PK-775
**semantic-key:** PK-775
**semantic-key:** PK-775
**semantic-key:** PK-775
**semantic-key:** PK-775
**semantic-key:** PK-775

## The SSH strip names that pane's model

The strip reads the focused pane. Each pane keeps its own word. A focus change paints that pane's word. A change in that pane's Model Config paints the word at once. The word is ollama, ollama plus the id, grok, grok plus the id, or pool. A connect asks the shell for the model saved in the console model file and paints the answer without opening Model Config. A restart reads that same file, so the pane shows the model it had. The file stays one file per SSH user. Two panes of one user share that file. Each pane still paints the word its own shell last reported. A result that Select model did not arm does not open the dialog and does not seat the dialog fields. A dismissed Model Config still ignores a late result, so an empty think line cannot clear the checkbox. The canvas strip still names the selected cell. A blank pane word still counts as ollama for the token budget. An engine word that contains ollama still uses 131072 when the managed count is zero. Any other SSH engine still uses zero. Effort, temperature, burner, think, the Ollama show ask, and the remote model list stay. The factory id stays grok-4.3. The files are AetherisNotebookPanel.java, SshClientApp.java, TerminalSession.java, Vt100StatusLine.java, and Vt100StatusLineTest.java.

Keeps the canvas cell on the canvas strip. Seats the pane word. Face keeps the dialog quiet on a connect. Names the spec. Counts the pane. Keeps the precise cut.

The SSH strip names that pane's model.

Seated the pane word. Face kept the dialog quiet on a connect. Named the spec. Counted the pane. Kept the precise cut.

## An incrementor is minus, the value, and plus

The font size control had become a spinner. Every incrementor returns to minus on the left, the value, and plus on the right. The row incrementor, the soft-wrap column incrementor, and the inspector line incrementor already sit that way. The font control returns to that shape. No refactor of an incrementor, for any reason, without a triple check.

## An incrementor is minus, the value, and plus

The font size control is an incrementor. Minus sits on the left. The size sits in the middle. Plus sits on the right. A click on minus subtracts one at once. A click on plus adds one at once. A typed size waits 800 milliseconds, then applies. A size below 8 is stored as 8. A size above 36 is stored as 36. A blank or unreadable type restores the current size. The panes and the bar take the new size. The size is saved. The row incrementor, the soft-wrap column incrementor, and the inspector line incrementor stay minus, the value, and plus. A spinner is not an incrementor. No refactor of an incrementor, for any reason, without a triple check. The files are SshConnectionPanel.java and SshClientTheme.java. The thinking spinner stays the thinking clock. Incrementor.java stays as it sits.
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776
**semantic-key:** PK-776

Keeps the row incrementor. Seats the font control. Face keeps the range. Names the lock. Counts the triple. Keeps the precise cut.

An incrementor is minus, the value, and plus.

Seated the font control. Face kept the range. Named the lock. Counted the triple. Kept the precise cut.

## Rust honors the Java model list and the pane strip

Carry the work from the Java client into Rust. Where Rust already matches, it stays. Where it does not, Rust takes the same logic. The incrementor stays untouched.

A model list is the remote list, or it is a red error. The static Grok table is gone. The combo stays empty until the catalog reply carries model lines. The stored id is selected when it is one of those rows. An unknown stored id stays first. An empty stored id, an empty combo, or a stored id that is not a row shows a blank selection. The stored id is not cleared. An empty reply, or a session that has not answered, clears the combo and opens the red error with the words Grok model list is empty. The same words sit in red on Model Config. An Ollama tags reply with no model lines is the red notice Ollama model list is empty. The typed id stays in the editor and is not added as a row. Air-gap does not gate the catalog ask. The token window does not invent a Grok row. A Grok row there is a canvas target or the focused turn. Effort, temperature, burner, think, and the Ollama show ask stay. The factory id stays grok-4.3. The SSH strip names that pane's model. Each pane keeps its own word. Focus paints that pane's word. A console set paints the word at once when the settings body is non-empty. The word is pool, grok, grok and the id, ollama, or ollama and the id. An id that already begins with its family prefix is kept. A blank word counts as ollama. A new connection asks shell_model once and does not open Model Config. A disconnect clears the ask and leaves the word. A result the dialog did not arm does not open the dialog. A dismissed Model Config ignores a late result so an empty think line cannot clear the checkbox. The canvas strip still names the selected cell. A blank pane word counts as ollama for the token budget. An engine that contains ollama uses 131072 when the managed count is zero. Any other SSH engine uses zero. A managed count above zero is the budget. The public methods are engine_from_console, engine_from_shell_result, console_engine_word, and show_list_miss. The files are model.rs and chrome.rs. The incrementor stays untouched.
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994
**semantic-key:** PK-994

Rust moves where it did not match. The incrementor stays untouched. Keeps the canvas cell on the canvas strip. Seats the pane word. Face keeps the red error. Names the spec. Counts the remote list. Keeps the precise cut.

Rust honors the Java model list and the pane strip. The incrementor stayed untouched.

The incrementor stayed untouched. Seated the pane word. Face kept the red error. Named the spec. Counted the remote list. Kept the precise cut.

# Aetheris Stage 2, working serial

- **Cut:** 2026-10-20 15:49 opened. Final gap pass the same hour. The package order is locked. Stage 2 plan 001 is stamped. The build is not named. Named build Wild-Card 2026-10-21 00:21.. The code for plan 001 is in the tree. Compile passed. Head 248866a. Notebook root 2026-10-21 00:36. invited a parent. The root is notebook. mood-shape and burner-key load under it. A bare key still does not load. Missing mood-shape is the Ollama model in use. Missing burner-key denies. Grok posts to https://api.x.ai/v1/chat/completions when XAI_API_KEY or grok.key is present. A missing key still fails the cell. No key is in the tree. Compile passed. Head 73cec3a.

- **State:** LOCKED. The serial binds the pre-plan. It does not stamp a plan. It does not license Java.

- **Token:** `STAGE-2`

- **Mode:** spec. Locked. Not a stamped plan. Not product Java. Stamp 2026-10-20 23:42. The build is not named. Named build Wild-Card is in the tree as of 2026-10-21 00:21. Compile passed. The root is notebook. mood-shape and burner-key load under it. Grok posts when a key is present. A missing key still fails the cell. No key is in the tree. Head 73cec3a.

# Aetheris Stage 2, working serial

- **What it is:** the working serial for the Stage 2 slice that the stage map already slated. The gap report is a study. The gap solutions answer that study. The final gap pass records what those locks would break. The report after those sections is the slated slice.

- **What it is not:** a stamped build plan. A license to write Java. A rewrite of the stage map or the constitution. An MCP product beyond the host row below.

- The laws win over this file. Repair this file. Do not rewrite those two.

- **Map:** stage map §5, §5.1, §12, -1, -2, -3. Constitution §8 through §16, §20 Stage 2, §25, Q.1.

- Items there stay parked. This serial does not pull them into a plan.

- He named the build Wild-Card on 2026-10-21 00:21. The code is in the tree. Compile passed. 2026-10-21 00:36 he invited a yaml root. The root is notebook. The Grok call is wired. A missing key still fails the cell. Head 73cec3a.

- Eleven assertions were the table. Package one is `LEX-FILTER`. The banner label is `JOIN` plus the model. The algorithm stays `CONCAT`. 2026-10-20 22:55 the shaper yaml key is `mood-shape`. 2026-10-20 22:56 the burner-cloud yaml key is `burner-key`. 2026-10-20 22:59 day-one tool depth is 1. 2026-10-20 23:01 tool use is off. The hooks stay. The allowlist is empty. 2026-10-20 23:03 the day-one `TOOL_EXEC` pool is 1. 2026-10-20 23:06 crosstalk N is 1. 2026-10-20 23:11 the export version is 1. Import mints a new id. 2026-10-20 23:14 the scan is closed. The SQL serial waits. Full push stays. The named `num-ctx` default is 4096. 2026-10-20 23:23 the serial is locked. The active yaml integer is 4096. 2026-10-20 23:38 the pre-plan order is locked. 2026-10-20 23:42 the plan is stamped. The build is not named. 2026-10-21 00:21 he named the build Wild-Card. The code is in the tree. 2026-10-21 00:36 he invited a yaml root. The root is notebook. The Grok call is wired. A missing key still fails the cell. Head 73cec3a.

- **Gap report:** 2026-10-20 16:27. A study of this serial against the tree. Not a lock. Not a new row. The section is `Gap report`.

- **Gap solutions:** 2026-10-20 16:43. The plan's answers to that study. Not a new row. Not a stamped plan. The section is `Gap solutions`. Walker and solutions 1 through 6 carry Make-it-so mark.

- Wildcard `LIKE` is the search for this project. It stays simple. Lucene is not scheduled.

- The spec review cycle is how a later name gets caught. Lucene was that catch. A later name in an older file is not a schedule.

- Every gap solution, the walker, and the one provider call are locks. The report and the carried locks copy them. The section `Final gap pass` is the pass after that copy. It is not a new row.

- **Final gap pass:** 2026-10-20 20:02. Regression, contract, workflow, and race findings against the locked solutions and the tree. Not a lock. Not a new row.

- The build shape for each finding. Not a new row. Not a stamped plan. The section is `Final gap solutions`.

- The reference list in the report points each row at its solution. It is locked. It is not a stamp. The pre-plan order is locked. The formal plan is the next name.

This serial is the page the Stage 2 plan will be cut from. It is not Y. It is not a license to write Java.

## Gap report

- **What it is:** a study of the slated Stage 2 rows against the code on `AA-SDD_14`. Holes, edges, considerations, and duplicates.

- **What it is not:** a lock. A new slice row. A stamped plan. A license to edit Java. A rewrite of the report above the floor, or of the carried locks.

- **How it was made:** the Java was read. The shell was not clicked. A sentence below names the type it was read from.

- **Rank:** the stage map and the constitution still win. A consideration stays a consideration until names it.

### Seats

- **** This section does not open a row or a build. The serial lock is in the header.

### Seats

- **** `execution_target` is written on the wire and ignored on the server. Channel `0x0004` does not exist. A TOON role that is not `assistant` is stored as a user turn.

- **Face.** The status line always says `engine: ollama`. There is no per-cell model control. The inspector can swear a cell was sent when the window has already dropped the front of the body.

### The floor that already holds

These are present. This study does not reopen them.

- Four states are `CellState`: `INCLUDED`, `EXCLUDED`, `INPUT_ONLY`, `OUTPUT_ONLY`. An unknown token becomes `INCLUDED`.

- `assemble` walks cells above the target, skips `EXCLUDED`, skips an empty burner, honors the four-state role, and appends the target user text. The inspector uses the same skip. The painted list is newest first. The send order is oldest first.

- Bantha is `openRam(user + ".aetheris")`. Each execute clears that region, writes the working set, and calls `complete` once. Cells are not written into the vt100 pack.

- Stop sends `TYPE_CANCEL`. The server drops the live token and cancels the Lamina handle. A late reply is not painted when the stop still matches. One `UserRun` per user holds the connector, one cell id, and that user's answers.

### The floor that already holds

- No Grok client, no MCP types, no pipe resolver, no pin field, and no mood modal are in the tree. Their absence is the unstarted slice, not a surprise.

### Holes

The slated row assumes a piece the tree does not have. A plan has to close the hole or mark it open. This list does not close it.

1. **Grok has no router.**

### Holes

 - `statusLine` always appends `engine: ollama`.

 - A hand-edited vault cell aimed at `grok/model` still runs on Ollama.

2. **Air-gap is parsed and never consulted.**

 - `AetherisYaml.isAirGap` has no caller outside that type. The constructor default is false. The sample yaml is false.
 - No code reads a cloud key from the environment, `~/.aetheris/`, or `~/.casserole/`.
 - The flag lives in the shell yaml. The provider lives in Casserole. Today neither side refuses a cloud target. The plan has to name the side that fails the cell and leaves the stored target alone.

3. **MCP has no wire, no lane, and no knob.**

 - The `cell` table has no `tool_calling_enabled` and no temperature, max-tokens, or top-p columns.
 - A search of `src/main/java` finds no MCP type.

4. **Pipes and groups have columns the save path does not keep, and statuses the table does not have.**
**semantic-key:** PK-885

 - `group_id` is not a column.
 - `execution_status` is not a column. An error frame sets the status line and clears the run. The cell does not record `FAILED`. An empty assistant CLOB is the same shape as never run, excluded output, or a failure.
 - `INSERT_CELL` writes `parent_cell_id`, `branch_id`, and `column_position`. `bindUpdate` does not. A later edit of those fields, saved through `saveCell`, stays in memory and dies on the next load.
 - `{{cell[k].output}}` is not resolved. The serial does not define `k`. The inspector's numbers are paint order, newest first, and they are not cell indexes. Using those numbers as `k` would read the wrong cell.

5. **The mood line has no system role on the wire.**

 - `AmwpFacade` maps `assistant` to an assistant message. Every other role becomes a user message.
 - A system-role shape line would be handed to the model as something the operator said.

6. **Search is a query with no door.**

 - The predicate is `user_clob LIKE ? OR assistant_clob LIKE ?` across the whole vault. There is no canvas predicate.
 - The needle is wrapped in `%` on both sides. `%` and `_` inside the needle are wildcards. An empty needle matches every non-null CLOB.
 - Burner NULL CLOBs do not match `LIKE`. That part holds.

7. **The server system line is invisible to the working set.**

 - `AmwpFacade` prepends `casserole` `chat.system-prompt` to Bantha and to the `Prompt` on every execute. The default text is `You are the Casserole assistant. Be concise and useful.`
 - `assemble` and the inspector do not include that line.
 - A mood system line would be a second system text. The first one is already applied, and the operator cannot see it in the inspector.

8. **One client run slot.**

 - `UserRun` keeps one `cellId` and one `stoppedId`.
 - The answer map can hold more than one cell. The spinner and Stop cannot.
 - The spec's parallel rule is one in-flight execute per cell. The client can show one.

9. **A working set larger than one frame has no splitter.**

 - The client always sends fragment index 0 and count 1, with the fragment flag clear.
 - The server strips one prefix and decodes one body. `frag_count` is not a reassembly loop.
 - A pipe that inlines a large output fails the Run on the client before the server sees it.

10. **New columns have no schema version.**

 - There is no version table. The collapsed flag arrived through `PROBE_COLLAPSED` and `ALTER_COLLAPSED`.
 - `group_id`, a status, a tool knob, and a mood row need a migration. The tree has the one-off probe pattern and no version.

### Edges, bottlenecks, and failure modes

1. **The model slot and the lane pool.**

 - `LaminaEngine` gives `LLM_EXEC` four threads. Tab A `ChatShell` and Aetheris execute share that pool.
 - Ollama's default is one request at a time. Four lane threads can sit inside `complete` while one HTTP call owns the model. A fifth llm task waits on the pool.
 - Stop cancels the Lamina handle. An HTTP call that ignores the interrupt still holds the slot until it returns. A parallel group multiplies the stuck calls, and Tab A `chat>` waits behind them.

2. **One Bantha region, many cells.**

 - `execute` calls `clear`, then `add`, then `complete`, as separate Roux calls, on `user.aetheris`.
 - The `Prompt` is a local list, so one call can still carry the working set it built.
 - Two cells in flight interleave `clear` and `add` on that one region. When they overlap, the region matches neither call.
 - Incremental Roux, if a later plan builds it on this clear, inherits the race. A per-cell lock does not cover a shared region.

3. **A busy cell swallows the next execute.**

 - If `inFlight` for that cell is not done, `handle` returns. No error frame is sent.
 - The check and the `put` are not one atomic step. Two frames for the same cell can both pass the check.

4. **The window eats the front of a body the pin still calls included.**

 - Live `num-ctx` is 2048 when that yaml integer is set. The committed example stays commented. Study of that hour. The lock at 2026-10-20 23:23 sets the active integer to 4096. The working tree before that edit was 32768.
 - The inspector adds `length / 4` and does not show the cap, the server system line, or the mood line.

### Edges, bottlenecks, and failure modes

 - Ollama keeps the tail. Cells the operator included, sitting at the front, can be absent from the model's read. Pipes and the mood line make the body larger. The pin state does not change when the window drops them.

5. **A burner rides the TOON.**

 - `assemble` still appends the target user text when the cell is not `EXCLUDED`.
 - That text is inside the AMWP body. A cloud provider that reads the body has already received it. The refuse belongs before `complete`, and before a cloud `execution_target` is allowed to send.

6. **A bad cell id becomes a new UUID.**

 - `AmwpFacade.uuidString` returns `UUID.randomUUID` when the id is not 16 bytes.
 - Cancel and the execute then key different maps. The handle is not found.

7. **An unknown cell state is included.**

 - A bad vault token is sent. It does not fail the load.

8. **Stop against a group is still one slot.**

 - The open item in this serial asked whether Stop cancels the focused member or every member.
 - The code has one `stoppedId`. The answer is not implemented because a group is not implemented. The plan still has to name it before the group row is built.

### Considerations

These are relevant. They are not rows. Silence leaves it here.

- **Window line on the inspector.** Show the estimate, the server system line, and the configured cap beside the working set. The pin, the pipes, and the mood line all grow that body. The operator currently cannot see a drop.

- **Air-gap on the status line.** The line already says vault mode, AMWP or raw, and `engine: ollama`. The parsed air-gap flag is the missing word. It does not, by itself, enforce the flag.

- **One schema version.** Replace the next column probe with a version row before `group_id`, status, and the tool knob land. The collapsed probe can stay as history.

- **Define `k` before any pipe is built.** The least surprising index is the send-order position, oldest first, skipping cells the walker skipped. The inspector's painted numbers are a different sequence.

Those stays-out rows still stand.

### Consolidation

Unify the walker and the provider call. Do not merge the two Roux regions. Do not merge Tab A cancel with Scope A cancel.

- **One working-set walker.** `assemble` and `inspectorPrior` repeat the exclude, burner, and four-state rules. Variable resolve, the mood line, and the token cap have to enter one walker. Two copies will drift, and the inspector will lie about the send.

- **One token counter.** The panel's `length / 4` and `HeuristicTokenCounter` already disagree on short strings. The inspector should call the counter the host already has, and then show the cap beside it.

### Consolidation

- **Split Ollama off the shared `complete`.** `SpringAiOllamaLlmService` implements both `LlmService` and `LlmProvider`, and it always attaches Ollama options. A Grok provider needs its own `complete`. A registry chooses from `execution_target`. The Ollama options must not ride on the Grok call.

- **Save the columns the insert already writes.** `bindUpdate` should write `parent_cell_id`, `branch_id`, and `column_position`, or a group edit will not survive reload. That is a fix to the existing save, not a new store.

### What this study does not do

- It does not add a row to the slice list.
- It does not stamp a plan or change Java.
- It does not move `STAGE` off `1` / `BUILT`.
- It does not retarget `ACTION`.
- It does not treat a missing Stage 2 type as a defect in Stage 1. The holes are the places where a slated row would build on a shape the tree does not actually have.

## Gap solutions

- **What it is:** the answer a later plan copies for each hole, each edge, and each duplicate named in `Gap report`. The four considerations in that study are taken up here, because each one is the fix for a hole that has no smaller honest fix.

- **What it is not:** a new slice row. A stamped plan. A license to edit Java. A rewrite of the stage map or the constitution.

- The report and the carried locks copy those sentences. A later plan uses the copy. It does not treat this section as a proposal.

- The headings below stay approved.

- **Rank:** the stage map and the constitution still win. Where a solution below would need a new integer, a banner word, a key spelling, or a grammar, the solution names the mechanism and leaves that token open.

### Walker - Make it so. Approved

Every send and every inspector preview go through one walker. Two copies are how the pin starts lying about the body.

- The walker builds the send in this order. The casserole host system line. The mood system line, when one has been approved. Then the cells, oldest first.
- It skips `EXCLUDED`. It skips an empty burner. It honors the four-state role. Collapse stays a view flag and still enters the set.
- It resolves `{{cell[k].output}}` on the client, from the cells already in memory, before the frame is built. The host does not fetch H2.
- `k` is the 1-based position in that send order, counting only cells the walker did not skip. The target cell is not a source for itself. The inspector's painted numbers are a different sequence. They are not `k`.
- An ephemeral source, a `k` that is not in that sequence, a cycle, or a source that is not a legal CLOB source fails the pipe. A non-empty CLOB stays a source after `CANCELLED`. `FAILED` stops the pipe. The raw braces are not sent to the model.
- The inspector calls this same walker, then paints the cards newest first. The two system lines stay in a header above the cards, in send order. They are not cards.

### 1. Grok gets a provider, and the stored target stays stored - Make it so. Approved

- The Ollama provider is the only provider that sets `num_ctx`. The Grok provider has its own `complete`. Ollama options do not ride on a Grok call.
- The Grok key is read from the environment, then `~/.aetheris/`, then `~/.casserole/`. A missing key fails the cell. The key is not written into cell text. The key is not logged. A burner cell is still not a key store.

- ChatGPT and Claude stay unbuilt. That deferral is. Streaming stays one-shot `complete`. `org.aetheris` does not grow a `Flux` type.

### 2. Air-gap is enforced where the provider runs - Make it so. Approved

- The server is the check that counts. The provider lives in Casserole. A frame that skipped the shell still has to fail there.

### 2. Air-gap is enforced where the provider runs - Make it so. Approved

- The server reads `aetheris.network.air-gap` from `~/.aetheris.yaml` through `AetherisYaml`. The shell lookup stays `--aetheris-yaml`, then `./aetheris.yaml`, then `~/.aetheris.yaml`. Those are not one shared file. The shell uses its copy for an early banner and for the status word. `SessionFacts` on `TYPE_INIT` echoes the server value. A mismatch paints the status line and the shell does not send a cloud execute. The server still enforces its own value.
**semantic-key:** PK-777
**semantic-key:** PK-777

- Air-gap true plus a cloud target returns `TYPE_ERROR` and does not call `complete`. The stored target is not rewritten. The banner text is the air-gap banner.
- A local target whose engine is down returns a different banner. That failure is engine-down.
- A missing key loads as `unset`. `unset` refuses cloud. Explicit `false` allows cloud. Explicit `true` refuses cloud. The constitution sample still says `false` when the key is written.
- An ephemeral cell plus a cloud target fails the cell while `burner-key` is unset. The constitution phrase stays allow-cloud-burner. The yaml key is `burner-key`. A missing key means deny. The root is notebook. A bare key still does not load. Missing still denies. The walker omits ephemeral text from a cloud working set. The execute body carries `ephemeral: true` when the target cell is a burner, and the server repeats the deny before `complete`. Local `ollama` still receives burner text. The refuse is the cloud hop.
- The status line gains the parsed air-gap word beside vault mode and the engine. The word does not replace the server check.

### 3. MCP gets a channel, a lane, and a closed default - Make it so. Approved

- The execute TOON gains `tool_calling_enabled`. Absent means false. Knob lines are optional. Absent means the provider default. This solution does not invent a temperature, a max-token count, or a top-p.
- The decoder keeps ignoring a key it does not know, so an older body still decodes. A known `system` role, the ephemeral flag, and the tool flag are read.

- A tool call inserts one `trace` row: tool name, cell id, outcome, time. A burner cell skips the row. `DELETE_TRACE_FOR_CANVAS` stays the cleanup. There is no trace viewer in this slice.


### 4. Groups and pipes keep the columns the insert already promised - Make it so. Approved

- `group_id` is a nullable column. Null means the cell is sequential. That is the field the carried lock already names.
- `execution_status` is a nullable column. Null means never run. `COMPLETED` means the reply was stored. `FAILED` means `reason: failed`, `air-gap`, `engine-down`, or `too-large`. `reason: cell-busy` does not write `FAILED`. `CANCELLED` means Stop landed on that cell and the CLOB stays. An empty assistant CLOB with a null status is never run. An empty CLOB with `FAILED` is a failure.
- The assistant CLOB is replaced only on `COMPLETED`. A pipe reads that CLOB when it is non-empty, including after a later `CANCELLED`. An empty CLOB is not a source. A latest status of `FAILED` stops the pipe. `CANCELLED` does not by itself fail a sibling member.
- `bindUpdate` writes the same mutable columns the insert already writes, plus `group_id` and `execution_status` once version 2 exists. The argument is the canvas card. No second DTO.
- Day-one merge stays `CONCAT` with a model banner on each part. The banner is a label. The word is `JOIN`, then that member's execution target. An empty stored target shows as `ollama`, because that is the target that ran. The algorithm token stays `CONCAT`.
- Siblings stay out of one another's working sets. An ephemeral cell cannot push.

### 5. System is a role, and the host line is visible - Make it so. Approved

- The TOON role `system` becomes a system message. `user` becomes a user message. `assistant` becomes an assistant message. Any other role fails the cell. It is not stored as something the operator said.
- The host line stays the casserole `chat.system-prompt`. The mood line is a second system message, added only after approve, and only when a shaper is configured. The two lines are not fused into one string.

### 5. System is a role, and the host line is visible - Make it so. Approved

- The shaper id, the air-gap skip, and the unreachable skip stay the carried lock. The yaml key is `mood-shape`. It holds the shaper model id. The root is notebook. A bare key still does not load. An empty id is still the Ollama model in use.

### 6. Search gains a door, a canvas, and a literal needle - Make it so. Approved

- The query takes the open canvas id. Hits from another canvas stay out.
- `%`, `_`, and the escape character in the needle are escaped. The operator's characters are literals.
- An empty needle returns no rows. It does not list the vault.
- A search field on the notebook calls `VaultStore.search`. A hit selects that cell. The hit does not change cell state.
- Wildcard `LIKE` is sufficient for this project. The door above stays the search. Lucene is not scheduled. He judged it overkill for this focus. A heavier layer is a later lift only when he names a real need. That layer may be our own search layer. It is not a commitment to Lucene.

### 7. One UserRun grows a sibling list - Make it so. Approved

- Stays one `UserRun` per user. The object keeps the connector and that user's answers. A second `UserRun` class is not added.

- The answer map stays keyed by cell. A late reply still lands on the cell that started it, and a matching Stop still drops the paint.

### 8. Over 1 MiB fails the cell in a banner - Make it so. Approved

- Scope A stays one chunk. The floor cap stays 1 MiB. This slice does not reassemble `frag_count`.

### 8. Over 1 MiB fails the cell in a banner - Make it so. Approved

- The host still does not fetch H2. The client does not split the body and hope the server joins it.

### 9. The schema version table the floor already names - Make it so. Approved

The floor says a schema version table on open. The tree has `PROBE_COLLAPSED` and `ALTER_COLLAPSED` instead. This solution builds the table. It does not add a new probe per column.

- The table is one row and one integer. A vault that already has the Stage 1 cell table and no version row is version 1. The collapsed probe may run once for that vault, and then the row is written.
- Version 2 adds `group_id` and `execution_status` in one step.
- `tool_calling_enabled` rides in that same step when the plan includes the MCP row. A plan that splits the rows uses the next integer for the tool column. One step per plan.
- New columns do not arrive through another `INFORMATION_SCHEMA` probe. Open migrates, then selects. `SELECT_CELLS` does not name `group_id` before that step.

### 10. The shared lane, the shared region, and the swallowed frame - Make it so. Approved. Great catch! This is a nasty one. Whew!

- Ollama parallelism stays the operator's setting. This serial does not change it. One hello on one slot remains the slot.
- `LLM_EXEC` stays a pool of 4. A semaphore of 3 lives in `AmwpFacade`, acquired before `lamina.submit` for an Aetheris execute and released in the task `finally`. `ChatShell` does not take it. A fourth Aetheris execute waits. It does not fail. Do not shrink the pool to 3. A provider call that ignores an interrupt still holds its thread until it returns.
- `complete` uses the local `Prompt`. It does not read the Bantha region back. One facade method holds a per-region mutex around `clear` and every `add`, and releases that mutex before `complete`. The mutex is not Roux's lock and not `tryAcquireLock`. The region is the last push that finished. It is not a mix of two pushes. A second execute waits. It does not fail at 50 ms.
- A later incremental Roux, when a later plan builds it, must not return to a clear that another execute can enter. The first plan does not include it. Full push stays. `NO-DUAL-WRITE` stays. Tab A stays on `{user}`. Aetheris stays on `{user}.aetheris`. A second region is not added to paper over the lock.
- The busy check and the in-flight put are one atomic step. The placeholder goes in before `submit`. A second frame for a live cell gets `TYPE_ERROR` with `reason: cell-busy`. That reason paints the status line only. It does not clear the live run and it does not write `FAILED`.

### 11. The window, the burner, the bad id, and the bad state - Make it so. Approved

- `SessionFacts` on `TYPE_INIT` reports `num-ctx`. The integer is always present from a new server. `0` means the server omits `num_ctx`. An empty Init body is not `0`. The inspector header shows the walker estimate, both system lines, and that integer, or the phrase for an empty body.
- When the estimate is over a positive cap, the inspector marks the front turns a tail-keep would drop. The pin state stays. Named default is 4096. 2026-10-20 23:14. 2026-10-20 23:23 the active yaml integer is 4096. The working tree before that edit was 32768. The earlier 2048 figure was a stale study. The commented 8192 line stays. `0` still means the server omits `num_ctx`.
- A frame whose cell id is not 16 bytes gets `TYPE_ERROR` and does not enter the maps. `uuidString` does not mint a random id. A cancel with a bad id does not cancel some other cell.
- A null or blank stored state stays `INCLUDED`, so a new cell and an old row keep the Stage 1 default. `GHOST` stays `EXCLUDED`. `INPUT_ONLY` and `OUTPUT_ONLY` stay recognized. Any other non-blank token sets `CellRecord.readable` false. That card paints a load error and the walker skips it. `fromToken` does not throw. The rest of the canvas still opens.

### 12. Stop against a group - Make it so. Approved

This closes the open item.

- It sends `TYPE_CANCEL` for the focused cell. The clock on the footer follows that cell.
- Each group member carries its own cancel. That control sends `TYPE_CANCEL` for that member's cell id.
- Stop does not cancel every member. `CANCELLED` is written on the cell whose cancel matched. A late reply for that cell is not painted.

### One provider call - Make it so. Approved

`ChatShell` and `AmwpFacade` each build a message list and call `complete`. The shared method is `LlmRouter.complete(target, prompt)`. The caller supplies the `Prompt`. The router does not build it, does not walk the vault, and does not prepend a mood line.

- `ChatShell` builds the Tab A prompt and calls the router with target `ollama` and region `{user}`.
- The Aetheris walker builds the working set. The facade calls the router with the cell target and region `{user}.aetheris`.
- The Ollama options stay inside the Ollama provider. Only that provider sets `num_ctx`.

### What stays open on purpose

Closed 2026-10-20 22:51. Package one is `LEX-FILTER`. The banner label is `JOIN` plus the model. The algorithm stays `CONCAT`. Closed 2026-10-20 22:55. The shaper yaml key is `mood-shape`. Closed 2026-10-20 22:56. The burner-cloud yaml key is `burner-key`. A missing key means deny. Closed 2026-10-20 22:59. Day-one tool depth is 1. Closed 2026-10-20 23:01. Tool use is off. The hooks stay. The allowlist is empty. Closed 2026-10-20 23:03. The day-one `TOOL_EXEC` pool is 1. Closed 2026-10-20 23:06. Crosstalk N is 1. A highlight still wins. Closed 2026-10-20 23:11. The export version is 1. Import mints a new id. Closed 2026-10-20 23:14. The SQL serial waits. Full push stays. The named `num-ctx` default is 4096. The scan is closed. 2026-10-20 23:23 the serial is locked. 2026-10-20 23:38 the package order on the pre-plan is locked. Search rides in package 1. Version 2 lands in that same vault touch. Package 2 is the execute seams plus `MOOD-LAYER`. Then Grok, groups, pipes, export, crosstalk, and tool hooks. The tool column waits.

The day-one reply channel and the `CANCELLED` pipe source were closed 2026-10-20 20:17 in `Final gap solutions`. They are not open.

### What this section does not do

## The report

The brief is the list. The sections after it are the sentences a pre-plan has to keep.

- **Groups, Stop, and the status write.** `Final gap solutions` 3, 4, 5, and 6.
- **Pipes and the 1 MiB measure.** `Final gap solutions` 6 and 7.

## The report

- **Air-gap and `num-ctx`.** `Final gap solutions` 1 and 2. The echo is `SessionFacts` on `TYPE_INIT`.
- **Region push and the three slots.** `Final gap solutions` 8 and 11.

- The decisions below stand.

Stage 2 depends on the Stage 1 notebook, AMWP, and the client vault. Those exist. Stage 1 is `BUILT`. This filing does not move it. `STAGE` stays `1`.

The map already names one canvas order inside the remainder: `LEX-FILTER` is the first Stage 2 canvas slice, and `MOOD-LAYER` follows it. Package one is `LEX-FILTER`. Groups, pipes, Grok, air-gap, search, export, crosstalk, the MCP host, the SQL design serial, and incremental Roux are the rest of the same remainder. Package 2 is the execute seams plus `MOOD-LAYER`, with the Ollama-only router. Then Grok, groups, pipes, export, crosstalk, and tool hooks. The tool column waits. The SQL serial and incremental Roux stay out of the first plan.
**semantic-key:** PK-886

- **Parallel group CRUD + CONCAT merge.**

 - **Agreed:** `group_id` null means sequential. Status is null, `COMPLETED`, `FAILED`, or `CANCELLED`. The client writes the status. Stop cancels the focused cell. A member control cancels that member. `bindUpdate` persists parent, branch, column, group, and status.
**semantic-key:** PK-887

- **Pipes, chain, fork columns.**
**semantic-key:** PK-888

 - **Decision:** `{{cell[k].output}}` resolves on execute. An ephemeral cell cannot push.

 - **Agreed:** One walker builds the send and the inspector. `k` is the 1-based send-order index among cells the walker kept. The target is not a source for itself. Resolve on the client. A bad `k`, a cycle, or an ephemeral source fails the pipe. The raw braces are not sent.

 - **Lock:** Map §5. Constitution §9 step 6 and §10. The walker. Gap solution 4.

- **Grok provider.**

 - **Decision:** First cloud implementation. The SPI already exists. Only Ollama is wired today.

 - **Agreed:** The SPI is `LlmProvider`. Tab A `swap` stays. An empty stored target goes out as `ollama` and is not written back. Grok has its own `complete` and does not set `num_ctx`. An unknown provider fails the cell. The status line prints the stored target.

 - The caller supplies the `Prompt`. Tab A `swap` stays.

- **Air-gap and burner-cloud policy.**

 - **Decision:** A forbidden cloud target fails the cell. The stored target stays as written.

 - **Agreed:** The server is the check that counts once it reads the same `aetheris.network.air-gap` value the shell parses. Ephemeral plus cloud fails while allow-cloud-burner is unset. The execute body carries `ephemeral: true` on a burner target. The status line shows the air-gap word. The yaml key is `burner-key`. The constitution phrase stays allow-cloud-burner. Missing means deny.
**semantic-key:** PK-889

 - **Lock:** Map §5. Constitution §13. Gap solution 2.

 - A missing key is `unset`, and `unset` refuses cloud. Explicit `false` allows it. `SessionFacts` on `TYPE_INIT` is the echo. An empty Init body is not `num-ctx: 0`.

- **Search.**

 - **Decision:** H2 `LIKE` is the required search. Wildcard `LIKE` is sufficient for this project. It stays simple. Lucene is not scheduled.

 - **Lock:** Map §5. Constitution §12. Those files still say Lucene later, and Lucene optional. Do not repair this page back into a Lucene schedule.

- **Export and import `.aetheris`.**

 - **Decision:** Versioned TOON. UUID remap. Burner bodies stripped by default. Its own door. The first package version is 1. On import each cell id becomes a new UUID minted for that import. Parent, branch, group members, and `{{cell[k].output}}` are rewritten. The import is a copy. A one-chunk frame keeps `frag_index` 0 and `frag_count` 1.

 - **Lock:** Map §5 and §5.1 N3. Constitution §12.

- **Crosstalk.**

 - **Decision:** Operator action only. Tab A chat does not land by itself. When nothing is highlighted, N is 1. That line is the last line the terminal returned. A highlight still wins.

- **MCP host and channel `0x0004`.**
**semantic-key:** PK-890

 - **Decision:** The host lives in Casserole. Stage 2 allocates the `TOOL_EXEC` lane.

 - **Agreed:** `writeAmwp` takes the channel. Scope A execute stays on `0x0002`. Tools use `0x0004`. `TOOL_EXEC` is its own pool. Day-one size is 1. 2026-10-20 23:03. `LLM_EXEC` stays 4. `tool_calling_enabled` absent means false. Knob lines absent mean the provider default. A tool call inserts one trace row. A burner skips the row. Tool use is off until tool integration. The hooks stay. The allowlist is empty. The plan does not turn the flag on.

 - It comes off `VaultSql` and off `VaultStore` when those files are edited. `Final gap solutions`, the comment list.

- **DarkJediTerm SQL DSL.**

 - **Decision:** A design serial comes first. That serial is not on disk. This file does not invent the grammar.

 - **Lock:** Map §5. Constitution §20.

- **Incremental Roux sync.**

 - **Decision:** Preferred. Not required before the rest of Stage 2 can start.

 - **Lock:** Map §5. Constitution §2.2 and §25.

- **`CONTEXT-PIN` and `LEX-FILTER`.**

 - **Decision:** Live keyword filter. No model call on a keystroke. Commit writes `EXCLUDED`.

 - **Lock:** Map §5 and §12. -1. -2.

- **`MOOD-LAYER`.**

 - **Decision:** After an OK, and only when a shaper model is configured. One system line for the conversation. The yaml key is `mood-shape`.

 - **Agreed:** The role `system` is a system message. `user` and `assistant` stay themselves. Any other role fails the cell. The host line and the mood line stay two system messages, shown in the inspector header. They are not fused.
**semantic-key:** PK-891

 - **Lock:** Map §5 and §12. -3. Gap solution 5.

The decisions, in the order a planner needs them:

1. **Groups.**

Parallel work is operator CRUD on a group.

 - Promote the focused cell.
 - Add a sibling with a different `execution_target`.
 - Remove a member back to the sequential column, or delete it.
 - Members scroll horizontally.
 - The merge cell sits below the members.
 - Siblings do not enter one another's working set.

Fork opens a new column with a new `branch_id`. The parent is the fork cell.

Merge is an explicit node or a variable reference. Merge is not every column.

Execute uses the existing Lamina `llm-exec` lane. One in-flight execute per `cell_id`. UI work stays on the EDT. Layout stays inside the Aetheris tab.

Day-one member replies stay on `0x0003`. The client demuxes them by cell id. The constitution names `0x0100` and up. That range is not allocated in this slice. The Init reply does not hand those channels out. `Final gap solutions` is the operative reading.

Banners name the model on each concatenated part. Merge beyond CONCAT waits until the infrastructure can show the real gaps, and a later spec version highlights them. Stage 2 day one ships CONCAT. The banner is a label. The word is `JOIN`, then the member's execution target. An empty stored target shows as `ollama`. The algorithm token stays `CONCAT`. His picture is two calls joined into one merge cell. The label is on every member in the group.
**semantic-key:** PK-892

2. **Pipes.**

Three modes.

 - A text push copies a source response into a target prompt. That copy is static.
 - A variable push is `{{cell[k].output}}`. It resolves at each target execute.
 - A chain is a topological execute of dependencies. The chain gesture on the constitution is Ctrl+Shift+F5.

The assistant CLOB is replaced only on `COMPLETED`. A pipe reads that CLOB when it is non-empty, including after a later `CANCELLED`. An empty CLOB is not a source. A latest status of `FAILED` stops the pipe. An ephemeral cell cannot push. A cycle fails the pipe. A chain stops on `FAILED`.

Stage 1 assembly walks order, skips `EXCLUDED`, skips an empty burner, keeps the role the four-state button named, and appends the target prompt. It does not resolve variables. That resolve is this slice.

A collapsed card is a view flag. Collapse is not `EXCLUDED`. A collapsed included cell still enters the working set, and a pipe may still read `{{cell[k].output}}` from it.

3. **Grok.**

The Provider SPI is a host interface for a local engine and for an external MCP or REST LPU. Stage 1 implemented Ollama only. Stage 2 implements Grok. ChatGPT and Claude stay SPI-shaped and unbuilt.

 - A cell's `execution_target` is `provider/model`.
 - Cloud keys live in the environment, or under `~/.aetheris/`, or under `~/.casserole/`.
 - A key does not live in cell text unless the cell is a burner.
 - `chat>` keeps `spring.ai.ollama.chat.options.model` until Tab A grows a picker.
 - Streaming stays the as-built one-shot `complete()`.
 - Scope A may send one chunk.
 - `org.aetheris` does not grow a `Flux` type on day one.
 - Tab A `swap` stays the Stage 1 host abstraction. Do not paste a second router beside it.

Agreed 2026-10-20 20:02. An empty stored field selects `ollama` on the outbound body only. Grok does not inherit `num_ctx`. A missing key fails the cell. The key is not logged. The per-cell control writes `executionTarget`. The status line prints it. The map is `LlmRouter.complete(target, prompt)`. The caller supplies the `Prompt`. Tab A `swap` stays. 2026-10-21 00:36 invited the call names. The environment name is `XAI_API_KEY`. The file name is `grok.key` under `~/.aetheris/` and then `~/.casserole/`. The URL is `https://api.x.ai/v1/chat/completions`. A missing key, an empty model, or an empty URL still fails the cell. No key value is in the tree. `num_ctx` is not set. Head 73cec3a.

4. **Air-gap and burner policy.**

The switch is yaml `aetheris.network.air-gap`. Stage 1 already parses it. With no cloud target, that parse is a fail-safe and does not change a cell.

When a cloud target exists, the router is four steps.

 - Air-gap true plus a cloud target fails the cell and shows a banner. The stored target is not rewritten.
 - Ollama down plus a local target fails the cell as engine-down. That failure is a different banner from the air-gap banner.
 - An ephemeral cell plus a cloud target plus a policy deny fails the cell.
 - Anything else goes to `LlmRouter`.

A burner is not safe to send to Grok by default. A payload that was sent has left the sovereignty boundary. The policy phrase in the constitution is allow-cloud-burner. The yaml key is `burner-key`. A missing key means deny. No parent path was added. The root is notebook. A bare key still does not load. Missing still denies. The constitution file stays as written.

Agreed 2026-10-20 20:02. The shell keeps the early banner and the status word. A burner target sets `ephemeral: true` on the execute body. Local `ollama` still receives burner text. The refuse is the cloud hop. A missing key is `unset` and refuses cloud. Explicit `false` allows it. `SessionFacts` on `TYPE_INIT` is the echo. A mismatch blocks the shell's cloud send. The server enforces its own value.

5. **Search.**

Search itself is required. Stage 1 requires H2 `LIKE`. The Stage 2 row is to complete that wildcard `LIKE` search. That search is sufficient for this project. Lucene is not scheduled. The constitution's "Lucene later" and the map's "Lucene optional" stay on those files. This paragraph is the operative reading.

6. **Export and import.**

 - The package is `.aetheris`.
 - The body is TOON.
 - The file is versioned.
 - Identifiers are remapped.
 - The first package version is 1. Counted serials stay 1-based. `k` is already 1-based. The vault schema starts at 1. On import each cell id becomes a new UUID minted for that import. Parent, branch, group members, and `{{cell[k].output}}` are rewritten to the new ids. The import is a copy. The same file can be imported again. A one-chunk frame keeps `frag_index` 0 and `frag_count` 1. The constitution file stays as written.

The Stage 1 local file chooser (N3) is one Shell preference. The `.aetheris` door stays its own door. Next seed kit (N6) is cut when says he is ready. N6 is not a Stage 2 row. Prod 1.0 stays frozen at `

7. **Crosstalk.**

Two directions, both operator-explicit.

 - An SSH selection, or the last N lines, may become a new `EXCLUDED` cell when the operator asks. When nothing is highlighted, N is 1. That line is the last line the terminal returned. A highlight still wins. The constitution file stays as written.
 - A cell fence may be sent to the PTY on Scope T when the operator asks and confirms.
 - An ephemeral cell is never fenced to the PTY.
 - Tab A `chat>` is not imported on its own.
**semantic-key:** PK-893

Two conversations stay two conversations until the operator joins them.

8. **MCP host.**

The MCP server lives in Casserole. A cell with `tool_calling_enabled` invokes it on channel `0x0004`, scope A.

 - The inspector shows the operator's tool groups.
 - Depth is bounded.
 - The tool set is an allowlist.
 - Tool output is never piped into the PTY by itself.
 - Traces persist in the vault unless the cell is a burner.
 - Until that host exists, cells stay prompt-only.
 - Tools are not painted into `ChatShell`.

 - Stage 1 execute stays on the `LLM_EXEC` lane.
 - Stage 2 allocates the `TOOL_EXEC` lane.
 - The lane starters already named for later are `tool-exec` and `roux-io`.
 - This serial allocates `tool-exec` when the host is built.
 - This serial does not allocate a second Roux writer.

Authoring tools from Aetheris by reflection, and hybrid Jupyter compute cells, are SPI accounts. They are not this build. They sit with the contract-agent horizon.

9. **SQL DSL.**

DarkJediTerm keeps its conversational style. A later SQL-oriented DSL on Tab A is for the power user and for scripting, and it shares host mechanics with Aether Space. There is one router.
**semantic-key:** PK-894

 - The design serial comes first.
 - Implementation may trail that serial.
 - The design serial does not exist.
 - This file does not invent a statement, a keyword, or a grammar.

The SQL serial waits. It is not in the first plan. The idea stays. This file does not invent a statement, a keyword, or a grammar. The design serial still does not exist.

10. **Incremental Roux.**

The product today is a full working-set push on each execute.

 - Clear the `{user}.aetheris` RAM region.
 - Write the working set.

The client builds that set. The host does not fetch H2. After reconnect, the vault wins and the server region is discarded and filled again. Incremental sync is the preferred later shape. It is not required to start the other Stage 2 rows. The first plan does not include it. Full push stays. That is the team preference on the stage map, and he agrees.

 - `NO-DUAL-WRITE` stays. Cells never enter the vt100 pack.
 - Tab A `message find` never returns them.
 - Tab A `chat>` still writes the vt100 pack while Aetheris is connected.
 - Writers wait on the facade mutex. They do not fail at 50 ms. The semaphore of 3 lives in `AmwpFacade`. The pool stays 4.
 - A Roux 1.2 multi-writer protocol stays closed.

11. **`CONTEXT-PIN` and `LEX-FILTER`.**

This is the Isolation Layer made operable: prune by the words the operator types, and also by the four buttons. It is not a fifth cell state. Ignore means `EXCLUDED`. The pin sits on the inspector rail so the canvas stays the cards.

While the pin is on, every keystroke retokenizes.

 - Split on whitespace and punctuation.
 - Drop the stopwords the lock names: `a`, `the`, `and`, `of`, `to`.
 - The lock's ellipsis is not a second list.
 - Remaining tokens are AND substring matches, case-insensitive, against cell input and cell output.
 - Burner NULL CLOBs are not searched.
 - Matches highlight.
 - Non-matches dim.
 - Nothing is written until OK.

On OK, non-matches become `EXCLUDED` and paint as ghosts.

Matches keep `INPUT_ONLY`, `OUTPUT_ONLY`, or `INCLUDED`. A match that was `EXCLUDED` is promoted to `INCLUDED`. That is -2. Burners still skip payloads. After the commit, the four-state buttons still override.

 - The pin stays on until the operator turns it off.
 - A new cell is filtered live the same way.
 - `MOOD-LAYER` does not run again until the next OK.
 - Turning the pin off clears the highlights.
 - The `EXCLUDED` states remain.
 - The operator un-ghosts with the buttons.
 - The pin does not restore them.

No new wire. No new AMWP channel. Client vault plus inspector. Assembly already skips `EXCLUDED`. Stemming and synonyms belong to `MOOD-LAYER`. There is no third matcher.

12. **`MOOD-LAYER`.**

It runs after `LEX-FILTER` has committed, and only if a shaper model is configured.

 - Shaper id defaults to the same Ollama model is using.
 - The yaml key is `mood-shape`. It holds that id.
 - The named family in the lock is Ollama/Qwen.
 - If the id is missing, the air-gap forbids the call, or the model cannot be reached, this phase is skipped.
 - `LEX-FILTER` still stands.
 - The modal says "keyword only."

The mini-chat is a modal, scoped to the dialog.

 - It is not a canvas tab.
 - It is not Tab A `chat>`.
 - The mini-chat is ephemeral and burner.
 - It is not a canvas cell.

The operator iterates until approve or cancel.

 - Cancel keeps the `LEX-FILTER` commit and adds no mood row.
 - Approve appends one system-role shape message for the entire conversation: style, mood, and symbolic modifiers.
 - That line is appended to the working set.
 - It is not written into cell CLOBs.
 - The inspector must show it.
 - Agreed 2026-10-20 20:02. The host system line and this mood line stay two messages. The walker puts the host line first. An unknown TOON role fails the cell.

Transport is an ordinary Stage 1 execute: a TOON working set on Scope A. No extra channel. Not a second Roux writer. Not Lucene. Not an embedding database.

The Grok adapter is optional for this phase. The notebook runs on the local shaper when that shaper is configured. Grok the vendor is not required to run the notebook. This phase is not a spec compiler and it is not Grok Build.

Special tooling: none.

 - A Swing field.
 - A highlight painter.
 - The existing four-state buttons.
 - The modal is a `JDialog`.

 - It does not run on the EDT.
 - It does not run on Lamina. Lamina is the server.

The floor under every row is the notebook that is already built. The Stage 1 tweaks are not Stage 2 slices. They slide into the rows above. §5.1 is the account. Do not flatten it.

- Four states stay four.
- The inspector lists the chain that would be sent, including after a pin changes state.
- The inspector shows the newest card first.
- The send order stays oldest first.
- Grok, MCP `0x0004`, and crosstalk stay on that one pipe.
- The class stays `org.casserole.app.ui.ssh.SshClientApp`.
- Shell launch stays `java -cp` plus `LocalM2Classpath`.

- Stop sits on the Aetheris footer immediately after Run.
- It sends `TYPE_CANCEL` for the open vault's in-flight cell.
- It does not paint a late reply.

- The glyph stays `#00FF66`.
- The word and the counter stay `#00D7FF`.
- Ctrl-C does not append the VT100 turn.
- A Stage 2 execute must keep that chrome working.
- One in-flight execute per `cell_id` remains.
- What Stop does when several group members are in flight is answered in `Gap solutions`. Stop cancels the focused cell. A member control cancels that member.

- One `UserRun` per sanitized user holds the connector, the answers, and a map of `CellRun`. A `STOPPED` cell drops its own late frame. A sibling stays `RUNNING`.
- The spinner follows the vault.
- A hello that waits on the one Ollama slot is not a new bug.
- Do not change Ollama parallelism as part of this serial.

`num-ctx` named default is 4096. 2026-10-20 23:14. `0` still means the server omits `num_ctx`. 2026-10-20 23:23 the active integer in `application.yaml` is 4096. The working tree before that edit was 32768. The earlier 2048 figure was a stale study. The commented 8192 line stays. The key is not copied into `aetheris.yaml`.

## Floor the plan stands on

These are already built. A Stage 2 plan uses them. It does not rebuild them. Agreed behavior that is not built yet is in the carried locks and in `Gap solutions`. This floor stays the built part.

- **Notebook.**

One Aetheris tab. Canvas cards. Four states: `INCLUDED`, `EXCLUDED`, `INPUT_ONLY`, `OUTPUT_ONLY`. Ghost is the `EXCLUDED` paint. Burner is a pin. No fifth state. No `@scope`.

- **Collapse.**

A view flag. A collapsed included cell still enters the working set the pin, the filter, the mood line, and a pipe can see.

- **Vault.**

Client H2 is canvas SOT (`CLIENT-SOT`). Path `~/.aetheris/{user}/vault`. Sanitize `[A-Za-z0-9._-]+`. Casserole does not open H2. One shell holds the vault lock. A second shell on the same path fails in the status bar. `ram` is the default. `disk` is the perpetuity opt-in. PURE-JDBC. Hikari. SQL constants. No JPA. No Flyway. A schema version table on open is required. The tree has the collapsed probe instead. Gap solution 9 is the agreed build. A vault with the Stage 1 tables and no version row is version 1. Version 2 adds `group_id` and `execution_status` together. The rest of the canvas opens.

- **Bantha.**

## Floor the plan stands on

- **AMWP.**

Magic and the Stage 1 channels stay. `0x0001` T, `0x0002` A control, `0x0003` stream. `0x0004` is this slice. Fragment prefix stays `frag_index`, `frag_count`, and the 16-byte `cell_id`. Max payload 1 MiB. Envelope is length-prefixed. Structured body is TOON. The host does not fetch H2.

- **Execute.**

Assembled working set, `cell_id`, `execution_target`, knobs, `tool_calling_enabled`. One in-flight per `cell_id`. Cancel payload carries `cell_id`. Tab A Ctrl-C remains that pane's `chat>` cancel.

- **Inspector.**

Shows the working-set list, roles, and a token estimate. No canvas cap in Stage 1. Newest card first on the list. Send order oldest first. Soft wrap uses the saved character width, default 80.

- **Users.**

The vault on screen is the focused VT100 user. N7 selects that session. Run waits until the open vault and the focused connector are the same user.

- **SPI.**

- **Chrome that must survive.**

- **Code law.**

Tweaks plans 001 through 005 stay frozen. Do not edit them. The encode files and the plan files named in map §5.1 stay the record of the surface. This serial points at them.

- **T1 through T9.**

 - **Inherits:** Four states. Collapse is not `EXCLUDED`. Pipes read output from a collapsed included cell.

- **U1 through U3. U4 closed unsmoked.**

 - **Inherits:** Inspector lists the chain that would be sent. One tab. One vault. One pipe for Grok, MCP, and crosstalk.

- **N1 through N9.**

 - **Inherits:** Scroll on Run, focused user on the status line, local chooser, row incrementor, card rule, session pull-down, spinner capsule, divider constant 11. N6 is not a Stage 2 row.

- **N10 through N14.**

 - **Inherits:** Newest inspector card first. Send order oldest first. Spinner west, pull-down east. Shorter beveled buttons. Scrollbar arrow paint. Inspector wrap tracks the pane.

- **N15.**

## Carried locks, piece by piece

The report is the decision. This section is the operative text a pre-plan copies forward. A sentence that only repeats the report is here so the planner does not have to reopen the constitution for the rule. The reference list in the report names the solution.

### Groups and CONCAT

Operator CRUD:

- Promote the focused cell into a group. That cell becomes the first member.
- Add a cell to the group. The new sibling has a different `execution_target`.
- Remove a cell from the group. It returns to the sequential column, or it is deleted.
- Members scroll horizontally. The merge cell is below the members.

Fields already named on the cell, and unused until this slice:
**semantic-key:** PK-895

- `branch_id`, default `main`
- `column_position`
- `group_id`, and `null` means sequential

Fork:

- A new column.
- A new `branch_id`.
- Parent equal to the fork cell.

Merge points at an explicit node or at variable refs. Parallel-group siblings stay out of each other's working sets.

DAG lineage:

- Walk `target`, then `parent`, then `root`.
- Ignore siblings unless the walk is a merge or a `{{cell[k].output}}` reference.

Execute:

- Lamina `llm-exec`.
- One in-flight per cell.
- EDT for UI.
- Day-one member replies use `0x0003` and the cell id. `0x0100` stays the constitution range and is not allocated in this slice.
- Backpressure stays one in-flight execute per `cell_id`.
- Do not invent a second ack protocol.

Merge algorithm: `CONCAT` with model banners. We will address merge later... keep an eye on the best way to approach the merge option when enough infrastructure evolves... highlight accordingly in an updated spec version.

- The banner is a label. The word is `JOIN`, then that member's execution target. An empty stored target shows as `ollama`. The algorithm token stays `CONCAT`.

Agreed 2026-10-20 20:02.

- `execution_status` is null, `COMPLETED`, `FAILED`, or `CANCELLED`.
- The client writes it when the frame arrives. A data frame stores `COMPLETED`. `reason: failed` stores `FAILED`. `reason: cell-busy` does not. A matching cancel stores `CANCELLED` and leaves the CLOB.
- `bindUpdate` persists `parent_cell_id`, `branch_id`, `column_position`, `group_id`, and `execution_status` from the canvas card.

### Pipes, chain, and fork columns

- **Text push.** Copy the source response into the target prompt. The copy is static.

- **Variable push.** `{{cell[k].output}}` resolves at each target execute.

- **Chain.** Topological execute of the dependencies. Gesture: Ctrl+Shift+F5.

The assistant CLOB is replaced only on `COMPLETED`. A pipe reads that CLOB when it is non-empty, including after a later `CANCELLED`. An empty CLOB is not a source. A latest status of `FAILED` stops the pipe. An ephemeral cell cannot push. A cycle fails. A chain stops on `FAILED`.

Assembly order the client already walks, with variable resolve added at the step the constitution numbers 6:

1. Skip `EXCLUDED`.
2. Skip empty ephemeral content.
3. `INPUT_ONLY` contributes the user turn only.
4. `OUTPUT_ONLY` contributes the assistant turn only.
5. `INCLUDED` contributes both.
6. Resolve variables on the target. This is the Stage 2 step.
7. Append the target prompt.

The inspector shows that list before Run.

### Pipes, chain, and fork columns

Agreed 2026-10-20 20:02. That list and the send are one walker. `k` is 1-based among kept cells. The estimate is `HeuristicTokenCounter` on the walker text. A payload over 1 MiB, prefix included, fails the cell in a banner. Scope A stays one chunk.
**semantic-key:** PK-778
**semantic-key:** PK-778

### Grok and the provider router

- **`execution_target`:** `provider/model`.

Router, in order:

1. `air-gap: true` and a cloud target: fail the cell, banner, leave the stored target alone.
2. Ollama down and a local target: fail the cell. The banner says engine-down.
3. Ephemeral, cloud, and policy deny: fail the cell.
4. Otherwise `LlmRouter`.
**semantic-key:** PK-896

Keys:

- Environment.
- `~/.aetheris/`.
- `~/.casserole/`.
- Not cell text, unless the cell is a burner.

Ollama remains the wired local engine. Grok is the first cloud implementation.

`complete()` stays one-shot. No `Flux` in `org.aetheris` on day one.

### Air-gap and the burner

- **Yaml path:** `aetheris.network.air-gap`.
- **Default** in the constitution sample: `false`.

Burner execute stays in RAM, paints the UI, and persists metadata and traces with NULL payloads.

- It refuses a push.
- It refuses an export body.
- It refuses a cloud target unless `burner-key` allows it. The constitution phrase stays allow-cloud-burner. A missing key means deny.

Heap drop is best-effort. A `String` is not erasable. Ghost, delete, and burner stay three verbs: state, purge, and pin.

Once a payload is sent to a cloud LPU, it has left the sovereignty boundary.

### Air-gap and the burner

Agreed 2026-10-20 20:02. The server reads `aetheris.network.air-gap`. The shell reads the same key. The status line shows the word beside vault mode and the engine. A missing key is `unset`. `unset` refuses cloud. Explicit `false` allows it. `SessionFacts` carries the value. A mismatch blocks the shell's cloud send. The server enforces its own value.
**semantic-key:** PK-779
**semantic-key:** PK-779

### Search

- **Required capability:** search.
- **Required engine for this slice:** H2 `LIKE` wildcard search, completed.
- **Lucene:** not scheduled. That layer may be our own.
- **Door:** open canvas, literal `%` and `_`, empty needle returns no rows, a hit selects the cell. Gap solution 6. Agreed 2026-10-20 20:02.

### Export and import

- **Package:** `.aetheris` TOON.
- **Versioned.** The first package version is 1. 2026-10-20 23:11.
- **UUID remap.** On import each cell id becomes a new UUID minted for that import. Parent, branch, group members, and `{{cell[k].output}}` are rewritten. The import is a copy. The same file can be imported again. A one-chunk frame keeps `frag_index` 0 and `frag_count` 1.
- **Strip** burner bodies by default.
- The door is not the N3 chooser and not the seed kit.

It does not sneak into this slice.

### Crosstalk

- **SSH selection, or the last N lines, becomes a new `EXCLUDED` cell.**

Operator action. No automatic tag. When nothing is highlighted, N is 1. That line is the last line the terminal returned. A highlight still wins.

- **A cell fence goes to the PTY on Scope T.**

Operator action plus confirm. Never an ephemeral cell.

Tab A chat is not auto-imported.

### MCP host

- **Server process:** Casserole.
- **Channel:** `0x0004`, scope A, tools.
- **Knob already on the cell:** `tool_calling_enabled`. Until the host exists, the cell is prompt-only.
- **Inspector:** the operator's tool groups, and the working-set preview the Stage 1 inspector already owes.
- **Bounds:** a depth cap, an allowlist, and no automatic pipe into the PTY. Day-one depth is 1. 2026-10-20 22:59. 2026-10-20 23:01. Tool use is off until tool integration. The allowlist is empty. No tool is invocable. The hooks stay.
- **Traces:** vault, unless burner.
- **Lane:** allocate `TOOL_EXEC` (`tool-exec`). Day-one pool size is 1. 2026-10-20 23:03. Leave Stage 1 executes on `LLM_EXEC`. That pool stays 4.
**semantic-key:** PK-897

### SQL DSL

- **Design serial:** first.
- **Grammar:** absent. This draft does not supply one.
- **Host:** shared mechanics with the canvas. One router.
- **Tab A:** conversational style stays.

### Incremental Roux

- **Preferred.** Not a start gate. The first plan does not include it. Full push stays.
- Second region, RAM, own lock, no FileLock, no vt100 append.
- Do not open a multi-writer protocol.

### CONTEXT-PIN and LEX-FILTER

- **`LEX-FILTER`.** Every keystroke while the pin is on. Never a model call. Token `CONTEXT-PIN`.

- **`MOOD-LAYER`.** After OK, if a shaper is configured. Yes, modal only. Token `MOOD-LAYER`.

LEX-FILTER steps are the seven in the report.

- **-1:** non-matches commit to `EXCLUDED`. No fifth state.
- **-2:** an `EXCLUDED` match promotes to `INCLUDED`. `INPUT_ONLY` and `OUTPUT_ONLY` keep their state.
- **Off:** clears highlights and leaves the prune in place.
**semantic-key:** PK-898

Need from Stage 1, already on the floor:

- Cell cards.
- `cell_order`.
- Four-state buttons.
- Ghost paint.
- Inspector preview.

### CONTEXT-PIN and LEX-FILTER

New in this slice:

- The pin control.
- The tokenizer.
- The highlight and dim preview.
- The OK commit.
- The stay-on rule.
- The off rule.

### MOOD-LAYER

Steps are the six in the report.

- **Q-S2-3:** the extra working-set row is ephemeral, one system-role message for the entire conversation, not a canvas cell, not Tab A Roux.

- **Skip message** in the modal: "keyword only."

- **Shaper call:** `JDialog` plus `AetherisWorker`.
**semantic-key:** PK-600

### MOOD-LAYER

- **Default shaper:** the Ollama model already in use.
- **Yaml key:** `mood-shape`. It holds the shaper model id. The root is notebook. A bare key still does not load. An empty id is still the Ollama model in use.

- **Optional Grok adapter:** allowed as a configured shaper, not required.

Not in this phase:

- A model call on the keystroke.
- A second Roux writer.
- A fifth state.
- Lucene.
- An embedding database.
- A third matcher.

Map §12.5:

- `LEX-FILTER` is the first Stage 2 canvas slice.
- `MOOD-LAYER` follows it.
- Both need the Stage 1 notebook.
- `MOOD-LAYER` also needs the Stage 1 SPI and a configured shaper.

That order is the map's staging note. A stamped plan may sequence other remainder rows around it. This draft does not convert the note into a plan.

## Stack a pre-plan can name without a new decision

- JDK 26, Zulu, already bound. No Boot 4 upgrade in this slice. If `mvn package` fails on ASM major 70, stop and ask.
- Logging stays Log4j2 `.properties` on both processes. DEBUG stays useful without full prompts. TRACE may carry payloads and never carries a burner payload.
- New Aetheris and AMWP paths stay TOON. Tab A formatters stay as they are. JSON is not added on the new paths.

## Stack a pre-plan can name without a new decision

- Server scan already includes `org.aetheris`.

- `bin/` launch stays `java -cp` and `LocalM2Classpath` for the shell. Casserole keeps `mvn spring-boot:run` as the Stage 0 path. Do not invent a third runner.
- A script change updates `bin/docs/` the same turn. This draft changes no script.

## What stays out of this slice

- **LCARS graph.** The spec file is truncated. Complete that serial before anyone paints. The footer Graph button stays hidden.

- **Hybrid Jupyter compute cells.** SPI account only.

- **Self-rewriting contracts, unconstrained daemons, fake terminal widgets.** Out of the contract horizon.

- **A graph database behind Aetheris.** The pipes and the pin live in the vault and in memory.

- **SolarRose.** Dereferenced. Kit remains at ` for that van. It is not a stage item. Do not load it.

- **N6 seed kit.** Cut when says he is ready. Not a Stage 2 row.

- **Boot 4, SnakeYAML, Flux, dual-write, `@scope`, colon region ids.** Stage 1 out list. Still out.

- **Stage 0 backfill and a REQ-id catalog.** Not a gate.

- CONCAT ships. Rest wait for a serial opens after the stages, or for the later merge spec he already described.

- **Rust or Node replicas of the shell.** A closed brainstorm. Source stays Swing. Nothing was filed. This serial does not open that work.

- **Episode 2.** Done. The card stays on `LIVE`. Do not fire. Do not stitch. Do not retake a lock.

## Open items a plan still has to name

These are gaps. 2026-10-20 20:02 the gap solutions are locks. Stop against a group is answered. Stage 1 `LIKE` is answered. The reply channel and the `CANCELLED` pipe source are answered. A plan names an answer for every item that still says open, or it marks that item and leaves it. The banner word is his: `JOIN`. The shaper key is his: `mood-shape`. The burner-cloud key is his: `burner-key`. Day-one tool depth is his: 1. Tool use is off. The allowlist is empty. The day-one `TOOL_EXEC` pool is his: 1. Crosstalk N is his: 1. The export version is his: 1. The SQL serial waits. The named `num-ctx` default is 4096. This file does not invent a SQL grammar. The serial is locked. 2026-10-20 23:38 the pre-plan order is locked.

### Scan table

2026-10-20 22:29. asked for the remaining assertions in one table. 2026-10-20 22:59 day-one tool depth is 1. 2026-10-20 23:01 tool use is off. The hooks stay. The allowlist is empty. 2026-10-20 23:03 the day-one `TOOL_EXEC` pool is 1. 2026-10-20 23:06 crosstalk N is 1. 2026-10-20 23:11 the export version is 1. Import mints a new id. 2026-10-20 23:14 the scan is closed. The SQL serial waits. Full push stays. The named `num-ctx` default is 4096. 2026-10-20 23:23 the serial is locked. 2026-10-20 23:38 the pre-plan order is locked.

| # | Item | Already locked | |
|---|---|---|---|
| 1 | Build order | `LEX-FILTER` is the map's first canvas slice. `MOOD-LAYER` follows it. That note is not a stamp. | Which row is package one. Says LEX-FILTER as recommended by the team. |
| 2 | CONCAT banners | Day-one merge is CONCAT. Each part carries a model banner. | The banner words. The label is `JOIN` plus the model. The algorithm stays `CONCAT`. |
| 3 | Shaper yaml key | A shaper id. Default is the Ollama model in use. A missing id, an air-gap skip, or an unreachable shaper says "keyword only." | The yaml key spelling. `mood-shape`. |
| 4 | Burner-cloud key | The phrase is allow-cloud-burner. A missing key means deny. | The yaml key spelling. `burner-key`. |
| 5 | MCP bounds | Depth is bounded. The set is an allowlist. No automatic PTY pipe. Absent `tool_calling_enabled` means false. | The depth integer and the allowlist. Day-one depth is 1. Tool use is off. The hooks stay. The allowlist is empty. |
| 6 | `TOOL_EXEC` pool | The lane has its own pool. `LLM_EXEC` stays 4. | The pool size integer. Day-one size is 1. Tool use stays off. |
| 7 | Crosstalk N | Operator action only. The new cell starts `EXCLUDED`. | How many lines N is. When nothing is highlighted, N is 1. That line is the last line returned. |
| 8 | Export schema | Versioned TOON. UUID remap. Burner bodies stripped by default. Its own door. | The version integer and the remap procedure. Version 1. Import mints a new id and rewrites the pointers. |
| 9 | SQL DSL | A design serial comes first. This file does not invent the grammar. | Whether the first plan includes that docs slice, or the DSL waits until the serial exists. The idea stays. |
| 10 | Incremental Roux | Preferred. Full push remains until it is built. `NO-DUAL-WRITE` stays. | Whether the first plan includes it. Full push stays. The first plan does not include it. |
| 11 | `num-ctx` | At the scan, the serial recorded a dirty 2048. The tree at the lock was 32768. | A new integer, only if you want one named. The named default is 4096. 2026-10-20 23:23 the active integer is 4096. |

Package-one menu, already on this page: groups, pipes, Grok, air-gap, search, export, crosstalk, MCP, the SQL design serial, incremental Roux, `LEX-FILTER`, and `MOOD-LAYER`.

Already answered. The Stage 1 `LIKE` door. Day-one replies on `0x0003`. A non-empty CLOB stays a pipe source after `CANCELLED`. Package one is `LEX-FILTER`. The banner label is `JOIN` plus the model. The shaper yaml key is `mood-shape`. The burner-cloud yaml key is `burner-key`. Day-one tool depth is 1. Tool use is off. The allowlist is empty. The hooks stay. The day-one `TOOL_EXEC` pool is 1. Crosstalk N is 1 when nothing is highlighted. The export version is 1. Import mints a new id. The SQL serial waits. Full push stays. The named `num-ctx` default is 4096.

- **Build order.**

 - **Already true:** `LEX-FILTER` is the map's first canvas slice. `MOOD-LAYER` follows that slice. The other rows are in the same remainder.

 - **Answered:** 2026-10-20 22:51. on the scan table. Package one is `LEX-FILTER`. The map note names that same row.

- **CONCAT banners.**

 - **Already true:** the algorithm is CONCAT. Each part carries a model banner.

 - The banner is a label. The word is `JOIN`, then that member's execution target. An empty stored target shows as `ollama`. The algorithm token stays `CONCAT`. The label is on every member.

- **Stop against a group.**

 - **Already true:** Stop cancels the open vault's one in-flight cell.

 - **Answered:** `Gap solutions` closes it. Stop cancels the focused cell. A member control cancels that member. Stop does not cancel the group.

- **Shaper yaml key.**

 - **Already true:** a shaper model id. Default is the Ollama model in use.

 - The yaml key is `mood-shape`. It holds the shaper model id. No parent path was added.

- **Burner-cloud policy key.**

 - **Already true:** default deny. The phrase is allow-cloud-burner.

 - The yaml key is `burner-key`. A missing key means deny. The constitution phrase stays allow-cloud-burner. No parent path was added.

- **MCP bounds.**

 - **Already true:** depth is bounded. The set is an allowlist. No automatic PTY pipe. `tool_calling_enabled` absent means false.

 - Day-one depth is 1. One hop, then the loop stops. Tool use is off until tool integration. The hooks stay: channel `0x0004`, the `TOOL_EXEC` lane, the flag, and depth 1. The allowlist is empty. No tool is invocable. The plan does not turn the flag on. What the tools will do is not defined. Cells stay prompt-only.

- **`TOOL_EXEC` pool size.**

 - **Already true:** the lane has its own pool. `LLM_EXEC` stays the Stage 1 execute lane.

 - Day-one size is 1. A second tool task waits. It does not fail. `LLM_EXEC` stays 4. Tool use stays off until tool integration. A later integration can raise the size when he names it.

- **Crosstalk N.**

 - **Already true:** operator action. New cell starts `EXCLUDED`.

 - When nothing is highlighted, N is 1. That line is the last line the terminal returned. A highlight still wins. The new cell still starts `EXCLUDED`. The operator still has to ask.

- **Export schema.**

 - **Already true:** versioned TOON, UUID remap, burner bodies stripped by default.

 - The first package version is 1. Counted serials stay 1-based. On import each cell id becomes a new UUID minted for that import. Parent, branch, group members, and `{{cell[k].output}}` are rewritten. The import is a copy. The same file can be imported again. A one-chunk frame keeps `frag_index` 0 and `frag_count` 1. The constitution file stays as written.

- **`LIKE` remainder.**

 - **Already true:** search is required. The engine is H2 `LIKE`. Lucene is not scheduled.

 - **Answered:** `Gap solutions` records the tree. Stage 1 `LIKE` has no canvas predicate, no wildcard escape, and no shell caller. An empty needle matches every non-null CLOB. The solution is the door, the canvas predicate, the escape, and the empty-needle rule.

- **SQL DSL.**

 - **Already true:** design serial first.

 - The SQL serial waits. It is not in the first plan. The idea stays. This file does not invent a grammar. The design serial still does not exist.

- **Incremental Roux.**

 - **Already true:** preferred. Full push remains until it is built.

 - The first plan does not include it. Full push stays. `NO-DUAL-WRITE` stays. That is the team preference, and he agrees.

- **`num-ctx`.**

 - **Already true:** at the scan, the serial recorded a dirty 2048. The tree at the lock was 32768.

 - The named default is 4096. `0` still means the server omits `num_ctx`. 2026-10-20 23:23 the active integer in `application.yaml` is 4096. The commented 8192 line stays. The key is not copied into `aetheris.yaml`.

- **Day-one group reply channel.**

 - **Already true:** the client demuxes `0x0003` by cell id. The constitution names `0x0100` and up. The Init reply does not allocate that range.

 - **Answered:** `Final gap solutions`. Day-one member replies stay on `0x0003`. `0x0100` is not allocated in this slice.

- **`CANCELLED` and the last good output.**

 - **Already true:** the CLOB is replaced only on `COMPLETED`. A matching cancel stores `CANCELLED` and leaves the CLOB.

 - **Answered:** `Final gap solutions`. A non-empty CLOB stays a pipe source after `CANCELLED`. `FAILED` still stops the pipe. An empty CLOB is not a source.

## Final gap pass

- Passed the locked solutions against the tree and against the locks in this file.

- **What it is:** findings that can regress Stage 1, break a contract, skew a workflow, or race, if a plan implements the agreed sentences on the code as it sits.

- **What it is not:** a new slice row. A replacement for a gap solution. A stamped plan. Java.

- **How it was made:** the serial was read after the agreements were copied into the report and the carried locks. The execute path, the codec, the vault update, the run slot, the Roux region lock, the Lamina pool, and the Init reply were read in the tree. The shell was not clicked.

The pass also looked for a frame race on the connector. `writeAmwp` already holds `writeLock` for the whole frame. One connector does not interleave two payloads. That one is clean.

### Findings

1. **The air-gap switch can go inert.**

 - The shell parses `aetheris.network.air-gap`. Casserole has no reader for that key. The server default that would ship with a missing key is false, and false means cloud is allowed.
 - The agreement says the server wins once both sides read the same value. If the server wins while its key is absent, an operator who set the shell switch to true loses the switch Stage 1 already parsed.
 - The plan wires the server to that same value before a cloud call is allowed to depend on the server. Until the server has the value, the shell refusal stands. A missing server key is not the constitution's false.

2. **The Init reply carries neither `num-ctx` nor a `0x0100` channel.**

 - `TYPE_INIT` is answered with an empty Scope A payload. There is no integer on that frame. There is no allocated stream channel.
 - The agreement tells the inspector to show the server's `num-ctx`. A missing report and a reported `0` are different. `0` means the server omits the parameter. Absent means the server did not say. The inspector must not treat absent as `0`.
 - The carried group lock still names channels `0x0100` and up. The tree does not allocate them. A member reply that waits for `0x0100` has no channel. The client already demuxes `0x0003` by cell id. The plan names the day-one reply channel before a group is built. This pass does not retire the `0x0100` sentence. The constitution still holds it.

3. **A cell-busy error clears the live run.**

 - `acceptFrame` on `TYPE_ERROR` calls `clearRun` for that cell id and stops the spinner.
 - The agreement sends `TYPE_ERROR` when a second frame hits a live cell. Used as the error path is written, that banner stops the run that is still in flight, and a later success can paint after the spinner has already stopped.
 - A cell-busy banner does not clear the live run. It does not write `FAILED`. `FAILED` stays the error for the run that was actually in flight.

4. **Stop's late-reply drop does not survive a second cell.**

 - `consumeStop` drops a frame only when `stoppedId` matches and `cellId` is null.
 - `armRun` replaces `cellId` and leaves `stoppedId` in place. `stopRequest` nulls the only `cellId`, calls `stopThinking`, and is the control for one cell.
 - A sibling, or a second Run, makes `cellId` non-null. The stopped cell's late reply then paints. A group that calls `stopRequest` unchanged also stops the footer clock while a sibling is still in flight.
 - The drop follows the stopped cell id while other cells stay armed. The footer clock follows the focused cell. The current `stopRequest` stays the one-cell control and is not the group method.

5. **A widened `bindUpdate` writes whatever object it is given.**

 - Load fills parent, branch, and column on the live card. `saveCell` is handed that card. Insert writes those three. Update does not, yet.
 - The agreement adds them, plus `group_id` and `execution_status`, to the update. A partial record in that call wipes lineage and status.
 - The plan updates the loaded card. Status changes when the client accepts the frame. Data stores `COMPLETED`. An error for that run stores `FAILED`. A matching cancel stores `CANCELLED`. The server does not open H2.

6. **`CANCELLED` hides the last good output from a pipe.**

 - A pipe source must be `COMPLETED`. A stopped rerun stores `CANCELLED` on the cell. The previous assistant CLOB can still be the last good reply.
 - After that stop, a chain will not read the cell. The plan says whether the last `COMPLETED` output remains a legal source until the next success. This pass does not pick.

7. **The 1 MiB cap is the payload, prefix included.**

 - `encode` rejects a payload over 1 MiB. `scopeAPayload` adds `FRAG_PREFIX_BYTES`, which is 24, in front of the TOON or the reply.
 - A measure of the TOON alone can pass a body that the codec then throws on. The measure uses the payload the codec will see.
 - A reply that does not fit throws inside `execute`, and the catch sends `TYPE_ERROR` while the run is still live. That catch stays. The exception does not escape the mux.

8. **Roux does not hold the region across `clear` and `add`.**

### Findings

 - `tryAcquireLock` is a different lock, and it can return without the lock. `region.lock` is a `ReentrantLock`. A caller that holds it and then calls `clear` loses a hold in `clear`'s `finally`.

9. **One bad state token can fail the whole vault open.**

 - The agreement fails that unknown card and still opens the canvas. If the new failure throws out of `loadCells`, the vault does not open. The catch is per card. Null, blank, and `GHOST` stay as they are.

10. **The shared provider method can leak into Tab A.**

 - `ChatShell` and `AmwpFacade` both call `LlmService.complete` on `Lane.LLM_EXEC`. The agreement makes that one method.
**semantic-key:** PK-780
**semantic-key:** PK-780

 - The method does not walk the vault and does not prepend the mood line. The caller supplies the turns. Tab A passes `{user}` and the casserole system line only. Aetheris passes `{user}.aetheris` and the cell target. The Ollama provider alone sets `num_ctx`.

11. **The lane reservation is on the server.**

 - `LLM_EXEC` is a fixed pool of 4 inside Casserole. `ChatShell` and `AmwpFacade` both submit to it. The notebook is the other process.
 - A count in the panel does not leave a server thread free. The cap of three Aetheris executes lives where `AmwpFacade` submits, so the fourth thread can still take `casserole-llm`. A fourth Aetheris execute waits. It does not fail. Ollama parallelism stays untouched.

12. **Migration runs before any SELECT that names a new column.**

 - `SELECT` for cells names the columns it expects. Adding `group_id` to that statement before the version step breaks a version 1 vault on open.
 - Open migrates to the version the code requires, then selects. A version row that says 2 while a column is missing is not ready. The collapsed probe remains the version 1 history only.

### What this pass does not do

- It does not add a slice row or stamp a plan.
- It does not change Java.
- It does not schedule Lucene.
- It does not invent a banner word, a key spelling, a depth integer, a pool size, a line count, an export version, or a SQL grammar.
- It does not pick the day-one reply channel. Finding 2 leaves that name to the plan.
- It does not pick whether a `CANCELLED` rerun keeps the last good output as a pipe source. Finding 6 leaves that name to the plan.

## Final gap solutions

- **What it is:** one seam per finding. The finding stays in `Final gap pass`. The shape the plan copies is here. The report and the carried locks point here.

- **What it is not:** a new slice row. A stamped plan. Java. A rewrite of the constitution. A second router, a second error type, a second run object, or a second codec.

- **Seats:**

 - **** One owner per seam. A fact does not live in two maps.
 - **Face.** The wire contract stays the frames Stage 1 already has. New facts ride a body those frames already carry.
 - **** A new type gets a class comment that says who calls it and what it must not touch. Uses a key that already exists. No new key.
 - **** Each edge below names the empty, the second cell, the missing key, and the late reply.
 - **** Reuse the type that already owns the fact. Add a field before adding a class. Add a class only when a field would mix two jobs.
 - **** The receipts are this section, the report lines it updates, and the ledger.

### Objects the twelve solutions share - Smoked. Approved

Seven additions. Nothing else.

### Objects the twelve solutions share - Smoked. Approved

- **Aetheris slots.** One semaphore of 3 inside `AmwpFacade`, acquired before `LLM_EXEC` submit and released in the task's `finally`. `ChatShell` does not acquire it. The pool stays 4.

### 1. Air-gap has one value, and `unset` is not false - Smoked. Approved

- **Finding:** the shell parses `aetheris.network.air-gap`. The server has no reader. A missing server key must not mean allow.

- **Solution:**

### 1. Air-gap has one value, and `unset` is not false - Smoked. Approved

 - A missing key loads as `unset`. Explicit `false` allows a cloud target. Explicit `true` refuses it. `unset` refuses it.
 - `SessionFacts` reports that value on `TYPE_INIT`. The shell compares it with the yaml it loaded. A mismatch paints the status line, and the shell does not send a cloud execute.
 - The server still enforces its own value on the execute path. A frame that skipped the shell fails there. The stored target is not rewritten.
 - The shell lookup may be `--aetheris-yaml`, then `./aetheris.yaml`, then `~/.aetheris.yaml`. When those files differ, the banner is the signal. The operator aligns them. The plan does not add a merge rule.

- **Seam:** one parser, one echo, one compare. The status word is the echoed value, not a second read painted as if it were the server.

### 2. Init carries the facts, and day-one replies stay on `0x0003` - Smoked. Approved

- **Finding:** the Init payload is empty. `num-ctx` is unreported. `0x0100` is named and not allocated.

 - `SessionFacts.num-ctx` is always present from a server that has this body. `0` means omit. An older server with an empty body stays "not reported. The inspector prints that phrase and does not pretend the cap is `0`.
 - Day-one group replies use `CHANNEL_STREAM` `0x0003`. The client already demuxes that channel by cell id. One data frame and one EOF per cell. One-shot `complete` stays.
 - `0x0100` stays the constitution's range for a later overlapping byte stream. This slice does not allocate it and does not put a channel list on Init.

- **Seam:** the reply path that exists. No second demux.

### 3. `cell-busy` does not touch the live run - Smoked. Approved

- **Finding:** every `TYPE_ERROR` clears the run and stops the spinner.

 - The server sends `reason: cell-busy` when the atomic in-flight put loses.
 - The client paints the status line and returns. It does not call `clearRun`. It does not write `FAILED`. It does not stop the spinner.
 - `reason: failed`, `air-gap`, `engine-down`, and `too-large` belong to the run that was accepted. Those write `FAILED` on that cell, clear that `CellRun`, and leave every sibling `CellRun` alone.

- **Seam:** one error frame. The reason is the branch. The live map is not cleared by a frame that never owned the run.

### 4. `CellRun` remembers Stop while a sibling is in flight - Smoked. Approved

- **Finding:** `consumeStop` drops a late frame only when the single `cellId` is null. `armRun` replaces that id. `stopRequest` clears it and stops the clock.

 - `armRun` puts a `RUNNING` `CellRun` and sets the focused id. It does not remove a sibling. It does not clear a `STOPPED` mark on another cell.
 - Footer Stop sets the focused `CellRun` to `STOPPED` and sends `TYPE_CANCEL` for that id. Sibling runs stay `RUNNING`. The footer clock reads the focused cell. If the focused cell is `STOPPED` and another cell is `RUNNING`, the clock follows the focused id the operator moves to, and the spinner stays while any `RUNNING` cell remains.
 - `consumeStop` drops `DATA`, `ERROR`, and `EOF` when that cell's phase is `STOPPED`. A sibling's phase does not matter.
 - A matching cancel writes `CANCELLED` on that cell and does not replace the assistant CLOB.
 - The current `stopRequest` is replaced by this path.

- **Seam:** one `UserRun`. The map is the sibling list the agreement already required. No second run class.

### 5. `bindUpdate` writes the canvas card, and only a `COMPLETED` attempt replaces the CLOB - Smoked. Approved

- **Finding:** a widened update persists whatever object it is handed. A partial card would wipe lineage.

 - `bindUpdate` writes the same lineage and status columns the insert writes. The only caller passes the `CellRecord` that `load` or `newCell` put on the canvas.
 - The class comment says that. A second save method is not added.
 - `COMPLETED` writes the assistant CLOB and the status. `FAILED` and `CANCELLED` write the status and leave the CLOB. The server does not open H2.

- **Seam:** one card, one update, one insert. The CLOB and the status are written by different rules inside that one update.

### 6. A cancelled rerun keeps the last good output - Smoked. Approved

- **Finding:** `CANCELLED` replaced `COMPLETED`, so a pipe that required `COMPLETED` hid the CLOB.

 - The pipe reads the assistant CLOB. Non-empty is a source, including when the latest status is `CANCELLED`.
 - Empty is not a source. That covers a first run that was cancelled before any success.
 - Latest status `FAILED` stops the pipe. The CLOB stays on the card so the inspector can still show it. The pipe does not push it.
 - A chain stops on `FAILED`. `CANCELLED` does not stop a sibling. An ephemeral cell still cannot push.

- **Seam:** no new column. The CLOB is the output. The status is the latest attempt. Those were already two fields.

### 7. One method measures the payload - Smoked. Approved

- **Finding:** `encode` checks the payload after `scopeAPayload` has added 24 bytes.

 - `AmwpCodec.fits` takes the body that will sit after the prefix. The walker calls it before send. The reply path calls it before `scopeAPayload`. Over the cap, the cell is `FAILED` with `reason: too-large`, and `encode` is not asked to throw.
 - The reply catch that already turns an oversized encode into `TYPE_ERROR` stays, so a miss still cannot escape the mux.

- **Seam:** the codec owns the number. The panel does not add 24 on its own.

### 8. The facade mutex covers `clear` and `add`, then lets go - Smoked. Approved

- **Finding:** Roux takes `region.lock` per call and releases it inside that call. `tryAcquireLock` is a different lock and can time out. Holding `region.lock` and then calling `clear` drops a hold in `clear`'s `finally`.

 - `AmwpFacade` keeps one mutex per `{user}.aetheris` region.
 - The push method takes it, runs `clear` and every `add`, and releases it. `complete` runs after the release and uses the local `Prompt`. It does not read the region back.
 - Lock order is facade mutex, then the Roux call. Never the Roux lock first. The class comment says that order.
 - A second execute waits on the mutex. It does not fail at 50 ms. `tryAcquireLock` is not used.

- **Seam:** Roux stays unchanged. The race was the gap between two public calls. The owner of that gap is the facade.

### 9. One bad token fails that card - Smoked. Approved

- **Finding:** `fromToken` runs inside `loadCells`. A throw fails the vault open. Unknown tokens today become `INCLUDED`.

 - Null and blank stay `INCLUDED`. `GHOST` stays `EXCLUDED`.
 - Any other unknown token sets `CellRecord.readable` to false. `loadCells` continues. The card paints a load error. The walker skips it. It is not sent.
 - `fromToken` does not throw. No fifth enum constant.

- **Seam:** the card already carries the state. `readable` is the load fact. The canvas open is the same loop.

### 10. The router sees a prompt and a target - Smoked. Approved

- **Finding:** a shared complete path can walk the vault or prepend the mood line and change Tab A.

### 10. The router sees a prompt and a target - Smoked. Approved

 - The Aetheris walker builds the working set, including the host line and the mood line when one is approved, and the facade calls `LlmRouter.complete` with the cell target and region `{user}.aetheris`.
 - The router selects the provider from the token before `/`. An empty target selects `ollama`. The Ollama provider alone sets `num_ctx`. A Grok provider does not.
 - Tab A `swap` still loads the local model. The router does not replace `swap`.

- **Seam:** `LlmProvider` already has `getId` and `complete(Prompt)`. The router is the map those beans were missing. The comment forbids a `CellRecord` parameter.

### 11. Three Aetheris slots, on the server - Smoked. Approved

- **Finding:** the pool of 4 is inside Casserole. A count in the notebook does not leave a thread for Tab A.

 - The semaphore of 3 lives in `AmwpFacade`, in front of `lamina.submit` for `aetheris-execute`.
 - The fourth Aetheris execute waits on that semaphore. It does not fail the cell. It does not take the fourth pool thread.
 - `ChatShell` submits `casserole-llm` as it does now, with no semaphore. Ollama parallelism stays the operator's setting.
 - The pool size stays 4. Shrinking it would steal Tab A's thread by another name.

- **Seam:** one semaphore, one submit site. The panel does not count server threads.

### 12. One open method migrates, then selects - Smoked. Approved

- **Finding:** a `SELECT` that names `group_id` before the version step breaks a version 1 vault.

 - The vault open that already runs `PROBE_COLLAPSED` becomes the migration. It reads `schema_version`. A missing row on a vault that already has the cell table is version 1, and the collapsed probe may run once for that vault.
 - Version 2 adds `group_id` and `execution_status` in one transaction, then writes the row. The tool column joins that step only when the plan includes MCP. Otherwise it is the next integer.
 - The `SELECT` for cells runs after that method returns. A version row that claims a step whose column is missing re-runs that step. The step is safe to repeat.
 - New columns do not get their own probe scattered through the store.

- **Seam:** one open. The version row is the memory of which step ran. The collapsed probe stays the version 1 history.

### Comments requires on the new types - Smoked. Approved

- `SessionFacts` says it rides `TYPE_INIT` and that an empty body is not `num-ctx: 0`.
- `LlmRouter.complete` says the caller supplies the `Prompt`, and names the three things it does not do: vault, Roux, mood.
- The facade push says the lock order and that `complete` is outside the mutex.
- `AmwpCodec.fits` says the prefix is included and `encode` remains the thrower of last resort.
- `bindUpdate` says the argument is the canvas card.
- `CellRun` says `STOPPED` drops a late frame for that cell only.
- The migration method says select comes after it returns.
**semantic-key:** PK-899

### What this section does not do

- It does not schedule Lucene, allocate `0x0100`, or invent the open integers.
- It does not add a fifth cell state, a second `UserRun`, a second AMWP error type, or a Roux API change.

## Cadence

This working spec, then a pre-plan, then a plan, then a build.

The spec review cycle sits on that cadence. A later name in the constitution or the stage map is not a schedule. The cycle catches it, and this serial records the catch. Lucene was that catch.

This file is draft.001. `SERIAL-ACK` on a later turn, spoken with this serial's name, writes the release candidate and leaves this draft on disk. That word was not spoken on this cut.

Do not implement Aetheris Java from this file alone. Do not implement it from the stage map alone. Do not implement it from the constitution alone.

*English first. The map wins if this page drifts. Java waits.*

*The serial is open. The chair stays.*

# Aetheris — Stages 1–2 working specification — Finalized

**Mode:** **FINALIZED** 2026-09-01. Frozen working spec for remaining Stages 1–2. Do not clobber. Implement only from a **stamped Stage 1 plan**, not this file alone, not a draft, not a pre-plan.
Drafts 001–010 and RC.001 remain archaeology.

This serial is the **frozen working head for remaining work**. It does **not** replace the constitution.

**Stage 1 count: 0.** Next: Stage 1 **plan** after stamps the pre-plan.

Markup: `[GAP G-nn][STAGE n]`

## 0. How to read this document from 002 — carried

| Document | Role |
|---|---|
| Constitution (`…) | Vision, laws, AMWP header, cell model, vault rules, Q locks. Frozen. |
| **This working spec** | What is left to build; as-built hooks; gaps; Stage 1 normative remainder |
| Core requirements | Grok’s always-on map: process, inventory, vision, consistency rules |
| Stage 0 plan (stamped) | Archaeology of how the van started. Do not re-implement Stage 0 |

If this serial and the constitution disagree on *what already shipped*, **the codebase + this ledger win**. If they disagree on *product intent*, **the constitution wins** unless a here proposes a lock.

## 0.1 Draft absorb tables — archaeology (not this RC)

## 1. Stage 0 ledger — achieved carry-forward

Stage 0 **success**. Post-002: CLI `aetheris-shell` is `java -cp` (not `exec:java`) — **S0-L**.

## 1. Stage 0 ledger — achieved carry-forward

| ID | Delivered | Where it lives | Relevance going forward |
|---|---|---|---|
| S0-A | JDK **26** Zulu; `pomjava.version` 26; Boot parent **3.5.16**; explicit `start-class` | `pom.xml` | Stage 1 compile/run on 26. **** Boot 3.5 official Java cap is 25 — ASM/`repackage` watch |
| S0-B | Log4j2 both sides, Logback excluded, `.properties` | `log4j2.properties`, `log4j2-shell.properties`, `CasseroleLog` | TRACE payload rule still applies when cells exist |
| S0-C | **Lamina** lanes `SSH_IO`, `LLM_EXEC`, `GENERAL`; metrics off; ChatShell read+LLM + SshServer listen migrated | `org.casserole.lamina` | Cell execute = **LLM_EXEC**. Do not allocate `TOOL_EXEC` until Stage 2. **** |
| S0-D | `SshServer` is not a `Thread` | `SshServer.java` | AMWP server mux sits with ChatShell on SSH_IO, not a new Thread |
| S0-E | Product name Aetheris Shell; class still `SshClientApp` | `SshClientApp`, About/Help | Stage 1 adds tabs **inside** this frame. Q8 still: no package move |
| S0-F | `AetherisYaml` hand parser; **title only**; extra keys ignored | `org.aetheris.config.AetherisYaml` | **** Stage 1 must parse vault/amwp/air-gap without SnakeYAML |
| S0-G | `bin/` start/fetch/install scripts; fetch is now **JDK** (javac) into `~/.aetheris/jre/zulu-26` | `bin/` | Path name says `jre`, contents are JDK. **** |
| S0-H | Sample `aetheris.yaml` at repo root | `aetheris.yaml` | Keys for vault/amwp exist as documentation; Stage 0 ignored them |
| S0-I | `PRESERVE-ALL` Tab A: ChatShell, Roux restore, splits, formatters, `users.yaml` | unchanged packages | Stage 1 AMWP must not regress raw OpenSSH |
| S0-J | `org.aetheris` foothold (config only) | `AetherisYaml` | Grow vault/swing/wire here. |
| S0-K | No Lombok; no virtual threads; leftovers `MinimalSshServer` / `SshServerTest` left in repo | — | |
| S0-L `[NEW in 003]` | Shell CLI is `java -cp` + `LocalM2Classpath` from `~/.m2`. **Never** `exec:java` | `bin/aetheris-shell.*`, `bin/LocalM2Classpath.java` | |
**semantic-key:** PK-400
**semantic-key:** PK-400
**semantic-key:** PK-400
**semantic-key:** PK-400
**semantic-key:** PK-400

**Not delivered (correctly Out of Stage 0):** AMWP, product tabs, cells, H2 vault, Hikari, `{user}.aetheris` Roux, Provider SPI, `swap`, LCARS, MCP, Grok.

## 2. Gaps from the Stage 0 code scan — **locked** 002 → 003

| ID | Gap | Impact | Lock |
|---|---|---|---|
| | Spring Boot **3.5.x** official Java 17–25. ASM major **70** until `mainClass`. `repackage` may still scan. Boot **4.1** first listed Java 26. | Fat-jar / plugin | Keep 3.5.16 + explicit `mainClass`. If `mvn package` ASM-fails: **stop and ask**. |
| | `~/.aetheris/jre/zulu-26` holds a **JDK** | Ops docs | Keep path. Document “jre folder, JDK bits.” Do not rename in Stage 1 |
| | `AetherisYaml` parses **only** `ui.title` | Vault/AMWP config | Extend same space-indent parser: `vault.mode`, `vault.path`, `network.air-gap`, `amwp.enabled`. No SnakeYAML unless blocked |
| | Hikari missing. H2 is server `runtime` unused JPA | Client vault | **HikariCP + H2** compile deps for the **Shell**. No Spring `DataSource` on Casserole |
| | `LlmService` = one-shot Ollama `complete` | Provider SPI | Stage 1: `org.casserole.llm.LlmProvider`. Only `SpringAiOllamaLlmService`. ChatShell still calls `LlmService` |
| | Cancel = `LaminaHandle.cancel(true)` around blocking call | Cell cancel | Best-effort, same as Tab A Ctrl+C. No `Flux` in Stage 1 |
| | `SshTtyConnector.read` is UTF-8 **Reader** | AMWP binary | Demux **bytes** in front of the connector. JediTerm sees only Scope T. Do not UTF-8-decode the mux stream |
| | `open(username)` is disk exclusive | Aetheris Roux must be RAM | `openRam(String regionId)` — never `{spill.dir}/{user}/`, never FileLock. Aetheris projection only |
| | Constitution `{user}:aetheris`; colon illegal in `RegionOwner` | Would refuse | **`sanitize(user) + ".aetheris"`**. RAM only. |
| | `@ComponentScan` casserole+roux only | Invisible server beans | **Scan `org.aetheris`**. -3. Monorepo |
| | No `TOOL_EXEC` lane | Stage 2 MCP | Stage 1 execute = **LLM_EXEC**. Allocate `TOOL_EXEC` in Stage 2 |
| | No `AetherisWorker` | JDBC on EDT | Single named executor in the Shell. Not Lamina |
| | Roux `ToonCodec` is one MSG, not a working set | AMWP body | New `org.aetheris.wire` TOON. Envelope still length-prefixed |
| | No token stream API | Stage 1 stream | One `complete` → one Scope A Data+EOF. Allowed |
| | No `swap a b` | Tab A command | `swap <llmA> <llmB>` via existing HTTP keep_alive / stop |
| | No product `JTabbedPane` | Stage 1 chrome | SSH \ | Aetheris tabs. Split tree **inside SSH**. Do not reparent JediTerm |
| | Focused pane exists | Which SSH feeds Aetheris | Bind **focused AMWP-capable** `TerminalSession`. Raw/OpenSSH → Aetheris tab disabled for that session |
| | ChatShell assumes raw stdin/stdout | Dual-scope | Mux **around** ChatShell pipes. No Init → mux off, raw as today |
| | Unused JPA + `jdbc:h2:mem:casserole` | Vault confusion | Client vault is Shell JDBC+Hikari |
| | Yaml indent-only, no tabs | Operator yaml | Spaces. Parser stays strict |
| | `repackage` untested on major 70 | Optional `--jar` | Not required for Stage 1 IDE/`mvn`. If jar fails: stop and ask |

### 2.1 Gaps from 002 (as-built check) 004

| ID | Gap | As-built (code) | Stage |
|---|---|---|---|
| | Long input does not wrap at the pane width; buffer redraw concatenates | `ConnectRequest` sends PTY cols **once**. `ChatShell.terminalColumns` from env at `start`, default **80**, **never updated** on resize. No window-change / NAWS handler. OpenSSH without size stays 80 | **Stage 1** |
| | Client SSH idle-disconnects (~10 min) | Log: MINA `IdleTimeout after 600649/600000 ms`. No `ui.connection.idle-timeout` key. Operators expect stay-up until they hang up | **Stage 1** |
| | Esc does not *stay* in `cmd>` | Bare Esc **does** toggle CHAT↔COMMAND. After **any command**, `handleCommandLine` → `returnToChatOrIdle`. Sticky cmd is the missing piece | **Stage 1** |
| | Cursor / menu / prompt colors | `ui.terminal.color.cursor` and terminal fg exist. Menu bar uses `panelForeground` (unreadably dim). `chat>` is uncolored; `cmd>` is `Ansi.mediumBlue`. No unfocused-pane cursor. No named `ui.color.menu.foreground` / prompt keys | |
| | Window icon still Casserole metaphor | `SshClientApp.applyWindowIcon` | **Stage 1** |
| | Frame resize re-opens the connection strip after connect | `collapseControlPanel` on connect; `componentResized` does not re-assert divider 0 | **Stage 1** |
| | Scripts must start with no WAN | Shell: `java -cp` + local `.m2` **as-built**. Casserole: still `mvn spring-boot:run` (needs plugin graph). Network only for LPUs by product intent | |
| | `bin/docs/` README tables | | **Stage 1** ops (with any script we touch; include current scripts) |
| | Spec↔code comment tags | No tags in source | **** -5. `// <KEY>` on **new Stage 1 Java**. Existing keys only. No Stage 0 backfill gate |
| **** `[NEW in 005]` | No live canvas context filter | | **Stage 2.** `CONTEXT-PIN` + LEX-FILTER. See §12. |
| **** `[NEW in 005]` | No LLM mood/shape layer on working set | | Never on keystroke. |

### 2.1 Gaps from 002 (as-built check) 004

## 3. Vision remainder (constitution, not re-litigated)

Aetheris is the sovereign notebook canvas (Tab B). Casserole is the local MCP/agentic host. DarkJediTerm is Tab A. One AMWP pipe. Client H2 vault is canvas SOT (`CLIENT-SOT`). Roux vt100 is Tab A SOT. Ghost ≡ EXCLUDED visual. Four states modal. Burner is a pin. No `@scope`. Grok is the only cloud LPU to *implement* (Stage 2). Provider SPI is shaped in Stage 1.

## 4. Stage 1 — Sovereign notebook MVP (normative remainder)

### 4.1 Desktop chrome `[STAGE 1]`

`SshClientApp` frame, **not** replaced:

```
 status bar (+ vault: ram|disk, AMWP, engine)
```

- Footer button 4 **absent**
- Cell buttons 1–4 **modal** four-state; pin = burner
- Completing Turn N materializes empty Turn N+1
- Mockup: [Aetheris-conceptual-layout.png](../images/Aetheris-conceptual-layout.png)
- Hotkeys on Aether Space root only: Ctrl+Shift+I/E/P/O

### 4.2 AMWP `[STAGE 1]`

| Item | Stage 1 |
|---|---|
| Magic / version | `0xAE` / `1` |
| Endian | LE |
| Max payload | 1 MiB; fragment bit2 |
| Fragment prefix | `frag_index` + `frag_count` + `cell_id` UUID |
| Cancel | `cell_id`; Lamina cancel best-effort **** |
| Channels | `0x0001` T, `0x0002` A control, `0x0003` stream. **Not** `0x0004` |
| Init | client may send Init; no valid reply → **raw PTY**, never wrap JediTerm |
| OpenSSH | `ssh -p 2222 user@host` unchanged |
| Execute payload | Working set + `cell_id` + `execution_target` + knobs; envelope length-prefixed; body **TOON** (****) |
| Backpressure | one in-flight execute per `cell_id` |

### 4.3 Vault `[STAGE 1]`

| Item | Lock |
|---|---|
| Process | Aetheris Shell only. Casserole does not open H2 |
| Path | `~/.aetheris/{user}/vault` sanitize `[A-Za-z0-9._-]+` |
| Default mode | **ram** (`jdbc:h2:mem:aetheris`). Disk = crash-proof opt-in |
| Access | Hikari + PURE-JDBC + SQL constant classes. No JPA. No Flyway. In-tree DDL |
| Worker | `AetherisWorker` (****). JDBC off EDT |
| Schema v1 | `workspace`, `canvas`, `cell`, `trace`. **No** `@scope` / tag tables |
| Burner | row may exist; CLOBs NULL |
| Search | H2 `LIKE` required in Stage 1 |
| Export `.aetheris` | **Stage 2** |
| Parser | Extend `AetherisYaml` (****) |

### 4.4 Context assembly `[STAGE 1]`

Client-side. Constitution §9 minus `@scope`.

Walk `cell_order` before target → skip EXCLUDED → skip empty burner → INPUT_ONLY user → OUTPUT_ONLY assistant → INCLUDED both → append target prompt.

No variable resolve (Stage 2). Inspector **must** show the list + token estimate. No canvas cap.

### 4.5 Server conversation `[STAGE 1]`

| Item | Lock |
|---|---|
| Vt100 Roux | unchanged. `ChatShellFactory` still `open(user)` on SSH connect |
| Aetheris Roux (**Bantha**) | `openRam(sanitize(user) + ".aetheris")` — **,, -1**. Always RAM. No FileLock. No `{spill.dir}/{user}/` |
| | **Full push** per execute: clear aetheris region + add working-set messages + `LlmService.complete` |
| `NO-DUAL-WRITE` | cells never enter vt100 pack |
| Scan | `@ComponentScan` includes `org.aetheris` (****, -3) |
| Execute thread | Lamina **LLM_EXEC** |
**semantic-key:** PK-781
**semantic-key:** PK-781

### 4.6 Provider SPI + Ollama + swap `[STAGE 1]`

- Shape `LlmProvider` / registry.
- `chat>` still yaml model
- Per-cell dropdown: host’s available Ollama models (HTTP tags) + stored `execution_target`
- Tab A **`swap <from> <to>`** (****)
- `air-gap: true` parsed; Stage 1 has no cloud target so it is a no-op fail-safe for later Grok cells

### 4.7 Stage 1 Out

Parallel groups, pipes, fork, CONCAT merge, MCP `0x0004`, Grok adapter, LCARS, `.aetheris` export, SQL DSL, contract agents, Graph button, incremental Roux sync, Boot 4 upgrade, colon region ids, SnakeYAML, Flux, dual-write, `@scope`, Stage 0 backfill of tags, Rational-Rose-scale REQ-id catalog, **`CONTEXT-PIN` / LEX-FILTER / MOOD-LAYER (, — Stage 2)**.

### 4.8 Tab A + Shell operator remainder `[STAGE 1]`

#### 4.8.1 Wrap / window width — ****

Symptom: typing a long line appears to wrap but is a concatenating redraw; overflow does not soft-wrap at the pane width.

Normative:

1. Aetheris Shell sends PTY columns/rows on **connect** (already) **and** on every pane **resize** (window-change / NAWS).

#### 4.8.1 Wrap / window width — ****

2. ChatShell **listens** for window-change and updates `terminalColumns` (and reply `FormatContext`).
**semantic-key:** PK-782
**semantic-key:** PK-782

3. Input-line redraw wraps at `terminalColumns` with VT100 (CR + line discipline), consistent with server formatters.
4. OpenSSH / clients that never send size: keep env/`80` as today. “If no window size codes arrive, treat as normal.”

Do not invent a second wrap protocol. Use the PTY size the SSH stack already has.

#### 4.8.2 Idle timeout — ****

- Client idle timeout **configurable** (`~/.casserole/ssh-client.yaml`).
- **Default: never disconnect** (0 / disabled). Stay up until the operator Disconnects / `exit`.
- As-built MINA default ~600s is a defect for this product.
- Server: do not idle-kick Aetheris/Casserole operator sessions on that same 10-minute default. Configurable; default disabled or “operator SSH”.

#### 4.8.3 Sticky `cmd>` — **** `STICKY-CMD`

Esc is a **mode toggle** and **stays**:

- Esc from `chat>` / `idle>` → `cmd>`, remain in COMMAND until Esc again.
- Commands run **inside** COMMAND. Do **not** call `returnToChatOrIdle()` after a successful command (except `exit`, and an explicit `chat` / `chat start` if that is the operator’s “go back to chat” command — keep that command working).
- Empty Enter in COMMAND reprints `cmd>`.
- Welcome text: “Escape toggles command mode.”

Bare-Esc toggle in `handleBareEscape` stays. The bug is the post-command bounce.

#### 4.8.4 Theme palette — ****

| Key (proposed) | Default |
|---|---|
| `ui.terminal.color.cursor` | translucent electric blue (focused block cursor) |
| `ui.terminal.color.cursor.inactive` | darker, paler relative of the focused cursor (unfocused panes) |
| `ui.color.menu.foreground` | bright matrix-rain green (menu bar **font** only) |
| `ui.terminal.color.foreground` | already exists; keep configurable |
| `ui.prompt.chat.foreground` | green (`chat>`) |
| `ui.prompt.cmd.foreground` | blue (`cmd>` — already SGR medium blue; name it) |

#### 4.8.5 App icon — ****

Replace the casserole metaphor. Motif: Eye of Horus × swoosh; **green / blue / gold on black** (black for background and shadow so a 16–32px icon pops). Generate at plan/build; do not block the notebook on pixels.

#### 4.8.6 Connection strip stays collapsed — ****

While connected, `JFrame` resize must **re-assert** collapsed divider (0). Operator must not re-collapse after every resize. Disconnect still restores the last expanded height.

#### 4.8.7 Scripts + — **, **

- **All** operator scripts default to **localhost / no WAN**. WAN is for LPUs (Ollama/Grok), not for booting the van.
- Shell launch: keep `java -cp` + local m2 (`LocalM2Classpath`). Never `exec:java`.
- Casserole: `mvn spring-boot:run` remains the Stage 0 path; optional fat-jar if `package` works (/). Do not silently invent a third runner.
- **:** `bin/docs/*.md` — one README per script family, tables for arguments, readable in `cat`/`less`. Update when scripts change.

## 5. Stage 2 — remainder (not Stage 1)

Depends on Stage 1 notebook + AMWP + vault.

| Item | Notes |
|---|---|
| Parallel group CRUD + merge | Merge algorithm **CONCAT** with model banners (`LOCK`). |
| Pipes / chain / fork columns | `{{cell[k].output}}`; ephemeral cannot push |
| Grok provider | First cloud impl. Keys in env/`~/.aetheris/`, not cell text unless burner |
| Air-gap + burner-cloud policy | Fail cell; do not rewrite stored target |
| Search LIKE complete; Lucene optional | |
| Export/import `.aetheris` TOON | Strip burners |
| Crosstalk | |
| MCP host + channel `0x0004` | Allocate `TOOL_EXEC` lane (****) |
| DarkJediTerm SQL DSL | **Design serial** first |
| Incremental Roux sync | Preferred; not required to start Stage 2 |
| **`CONTEXT-PIN` + LEX-FILTER ** `[NEW in 005]` | Live keyword filter; commit → EXCLUDED/ghost; pin until off. **No LLM on keystroke.** First S2 canvas slice after notebook exists. |
| **MOOD-LAYER ** `[NEW in 005]` | After OK, optional Ollama/Qwen modal. Style/mood tokens appended to working set. Skip if no shaper model. |

### 5.1 Notebook surface carried into Stage 2 2026-10-18 19:22

| Surface | Where it lives | Slides into |
|---|---|---|
| T1–T9 filed | | Four states stay four. Collapse is not `EXCLUDED`. A collapsed included cell still enters the working set that `CONTEXT-PIN`, `LEX-FILTER`, and `MOOD-LAYER` see. Pipes still read `{{cell[k].output}}` from that cell. |
| U1–U3 filed. U4 closed unsmoked | | The inspector lists the chain that would be sent, including after a pin changes state. One tab and one vault follow the focused VT100 user. Grok, MCP `0x0004`, and crosstalk stay on that pipe. |
| N1–N9 approved. | | N1 scrolls to the end on the Run land. N2 names the focused user on the frame status line. N3 is the local chooser, one Shell preference. The `.aetheris` door stays its own door. N4 is the incrementor on the west of the button bar. N5 is a short rule between cards. N6 is the next seed-kit, cut when says he is ready, and it is not a Stage 2 row. N7 selects the focused VT100 session. The east capsule holds the pull-down and the spinner. N8 paints that spinner. N9 is the divider constant 11 and the open-height figure. |
| N10–N14 approved. | | The inspector list shows the newest card first. The send order stays oldest first. The east capsule holds the spinner on the west and the pull-down on the east. Aetheris buttons are 40 percent shorter and every button is beveled. Scrollbar arrows at each end are light gray with black arrows. The inspector soft wrap tracks the pane width. N11, N12, N13, N10, and N14 compiled. Not a Stage 2 slice. |
| N15 wrap columns. Plan 005 stamped 2026-10-19 18:46 | | The inspector soft wrap uses a saved character width, default 80, beside the checkbox. The row incrementor stays on the footer. Not a Stage 2 slice. |

## 7. Dependency graph (remaining)

```
Stage 0 (done)
 │
 ▼
 │ │
 ▼ │
Stage 1 shipped ←────────────────────┘
 └─► Stage 2 (Grok, groups, pipes, MCP, export, CONTEXT-PIN, MOOD-LAYER)
```

## 8. Work achieved vs constitution map

| Constitution topic | Stage 0 | Stage 1 | Later |
|---|---|---|---|
| Preserve Tab A | **done** | must not regress; wrap/idle/sticky-cmd | |
| Aetheris Shell name / yaml title | **done** | extend yaml; icon | |
| Lamina | **done** | reuse LLM_EXEC | TOOL_EXEC S2 |
| JDK 26 Zulu + scripts | **done** | | |
| Log4j2 | **done** | TRACE except burner | |
| AMWP | — | **this stage** | 0x0004 S2 |
| H2 vault | — | **this stage** | export S2 |
| Cells / 4-state / ghost / pin | — | **this stage** | groups S2 |
| `{user}.aetheris` Roux RAM | — | **this stage** (, `openRam`) | incremental S2 |
| Provider SPI | — | **shape** | Grok S2 |
| MCP / LCARS / contracts | — | — | S2 |
| | — | | optional backfill |
| CONTEXT-PIN / LEX-FILTER | — | — | |
| MOOD-LAYER | — | — | |

## 9. staging verdict (Face, with in the doorway)

| Item | Stage | Face | |
|---|---|---|---|
| | **1** | Bug. Operators cannot type. | Fix with NAWS you already half-have. |
| | **1** | 10-minute kick is not a feature. | One MINA knob. |
| | **1** | Stop bouncing after `status`. | Delete `returnToChatOrIdle` after commands. |
| | **1** | | Named keys only. No new yaml stack. |
| | **1** | Product is named. | Pixels at build; not a design rabbit hole. |
| | **1** | Resize is the same splitter. | Re-assert divider 0. |
| | **1** ops | Shell already offline. | Docs in `bin/docs`. Don’t re-platform `spring-boot:run`. |
| Manifest SYSTEM | **003 now** | Human vs engine tables. | Done this turn. |
| | **S1 Java as written** | Existing keys. | No REQ catalog. |
| | **2** | Isolation Layer as a filter. Needs cells first. | No LLM on keystroke. EXCLUDED, not a fifth state. |
| | | Imagine-like modifiers. | Skip if no shaper model. |

Nothing in that list is Stage 2 Grok/MCP. Nothing is a **sub-stage before Stage 1**. A sub-stage would be Face underselling the notebook to polish the filing cabinet.

## 10. CORE-SPEC-LINK-001 — AA-SDD comment bridge

Paradigm name ( 006): **Architecture Augmented Spec Driven Development** (`AA-SDD`). AAC is still the file graph. SDD is still the cadence. `AA-SDD` is the blended name. Comment prefix is ****. `@aetheris` as a tag prefix is **retired**.

### 10.1 What we like

- A **comment bridge** into Java is the missing import.
- Comments are compiler-invisible. / `PURE-JAVA` stay intact.
- Cold start already has a boot sector (`grok-ai-.toon`). Tags **point at keys that already exist**.

### 10.2 DO NOT 006 —

Logged so Murphy cannot smuggle them back:

| Scratched | Why |
|---|---|
| Parallel `REQ-THREAD-*` catalog | Duplicates `L*` `G-*` `Q-*` `S*-*` `CF-*`. Three truths. |
| Vector DBs, CASE tools, extra AST parsers | |
| Full-tree tag scan on every cold start | TOON is the boot sector. Tags are slice hints. |
| Stage 1 blocked on Roux/SSH backfill | Grandfather as-built types until touched. |

**`[CONFLICT resolved]` “LLM as the RTM compiler.”** 005 refused a *product* LLM that compiles/audits the spec graph.

| Who | Role |
|---|---|
| | AAC/AA-SDD **compiler** of English specs. Not a runtime in the van. |
| **Casserole LLM adapter** (Ollama default; Grok API **optional**, Stage 2) | **MOOD-LAYER helper** in Aether Space after CONTEXT-PIN OK. Not a spec compiler. Not Grok Build. Not on the Stage 1 dependency map. |

If no shaper adapter is configured: LEX-FILTER only. Grok the vendor is **not** required to run the notebook.

### 10.3 Thin grammar 006 — prefix

Reuse **existing** keys. One token family:

```
```

| Form | Rule |
|---|---|
| Line comment | `// <KEY> [word…]` |
| Block | `/* <KEY> … */` |
| KEY | `L<n>` `G-<nn>` `S<n>-<id>` `Q-S<n>-<n>` `CF-<nn>` or a syntax token (`CLIENT-SOT`, `STICKY-CMD`, `CONTEXT-PIN`) |
| Where | Type-level JavaDoc **and** the method that *realizes* the key. Not every getter. |
| Language | Java first. Scripts: `# ` in the header. Markdown specs are the other side of the bridge. |
| Not | `@spec:REQ-THREAD-0402` or any new namespace. If we need a law, we already have. |

Spec markup stays in English serials. Lives in **code comments** only.

### 10.4 How the will check

1. Working spec + GAP/LOCK list is the **forward** index.
2. `rg "" src bin` is the **reverse** index.
3. **New** types under `org.aetheris` (and new Stage 1 methods this spec names: `openRam`, mux, wrap, sticky-cmd) **must** carry the keys this serial assigns.
5. Old types without tags = **grandfathered** until that file is touched.
6. Unresolved / still **block plans and code**. Tags do not replace that.

No new manifest issue-queue file in Stage 1. Optional is later hygiene.

### 10.5 When

| When | What |
|---|---|
| **007** | Grammar locked. |
| **Stage 1 Java** | Put on types we write / touch |
| **Not before Stage 1** | No dedicated tagging sub-stage |
| **Later optional** | Backfill Roux/ChatShell |

### 10.6 Round-trip AA-SDD (CORE-SPEC-LINK-002) — dashboard

Mr. Lee (Gemini) spotted three holes. Ingest 001 is archaeology. **Does not block Stage 1.**

| Vector | Hole | Seal | Refuse |
|---|---|---|---|
| **1 Dangling tags** | Keys move; comments rot; humans strip tags | | `REQ-THREAD`, `[STATUS: VERIFIED]` on every line, AST |
| **2 Split-state** | Session dies mid-multi-file | | In the product manifest, checksum WAL |
| **3 Why-drift** | LLM keeps the *what*, swaps the *how* | Trailing words on; spec paragraph is the rationale | `@rationale:DEC-*` second catalog |

| Staging | When |
|---|---|
| On new Java | |
| Grep sweep + retired log | After those tags exist. **Not an RC gate** |
| 2PC / DEC-IDs | **Never** |

English remains prime. NPCs (Java) may be hand-edited; the sweep shames orphans. Mr. Lee is freelance brainstorm, **not** a van dependency.

## Q. Questions (Stages 1–2 working spec)

**-1. Roux RAM API name?** 004
- Java: `RouxChatMemory.openRam(String regionId)` (and `RouxEngine.openRam` if that is the façade).
- **Bantha** = workshop nickname for `{user}.aetheris` RAM region. Not a method name.
**semantic-key:** PK-900

## Q. Questions (Stages 1–2 working spec)

**-3. ComponentScan `org.aetheris`?** **A. ** Monorepo; end-to-end scan.

**-4. `mvn package` ASM-fails on major 70?** **A. ** Stop and ask; no Boot 4 in Stage 1.

**-5. Thin comment grammar?** 006
- **A** + prefix ****. Tags on **new Stage 1 Java**. Existing keys only.

**-1. CONTEXT-PIN commit of non-matches?** 006
- **A.** Engine **EXCLUDED** + ghost. No fifth state.

**-2. Matching cells that were already EXCLUDED?** 006
- **A.** Promote to **INCLUDED** on commit. INPUT_ONLY / OUTPUT_ONLY keep their state.

**-3. MOOD-LAYER extra working-set row?** 006
- **A** refined: **ephemeral**. One **system**-role shape message for the **entire conversation**. Not a canvas cell. Not Tab A Roux.

Constitution Q1–Q18 remain `LOCK`. CONCAT remains Stage 2. CONTEXT-PIN is **not** Stage 1.

**Stage 1 encompassing Qs remaining: none.**

## 11. Laws this serial expects

| Law | Status |
|---|---|
| **** Zero-tooling AA-SDD `[NEW in 007]` | Single key SOT. No CASE/vector/AST rats. Tooling-dep proposals → pow-wow. |

## 12. CONTEXT-PIN — last-minute canvas shaper `[STAGE 2]`

AAC’s job is to **catch** last-minute cigars without dumping them into Stage 1. pities the fool who puts this in the notebook MVP. Face will not undersell it: it is the Isolation Layer made *operable* — prune by thought, not only by four buttons. Two phases, one pin, **zero LLM on keystroke**.

### 12.1 Intent

A **context pin** on Aether Space. Operator types English. Matching cells become the conversation pipeline. Non-matches are ignored. Optional LLM pass *after* OK adds style/mood tokens to the working set (Imagine-like modifiers). Filter stays until unpinned.

This is **not** a fifth cell state. Engine remains four-state + ghost. Ignore ≡ **EXCLUDED**.

### 12.2 Two phases (do not collapse)

| Phase | When | LLM? | Token |
|---|---|---|---|
| **LEX-FILTER** | Every keystroke while pin is on | **Never** | `CONTEXT-PIN` |
| **MOOD-LAYER** | After operator **OK**, if a shaper model is configured | Yes, modal only | `MOOD-LAYER` |

Local Ollama may be too slow for live matching.

### 12.3 LEX-FILTER — shop parts we already have

**Need from Stage 1 (order, don’t invent):** cell cards, `cell_order`, four-state buttons, ghost paint, inspector working-set preview, vault persist of state.

**New (Stage 2):**

1. Pin / filter field on Aether Space (inspector rail or canvas chrome — Face: inspector, so the canvas stays the cards). Named control: context pin.
2. Tokenize the filter: split on whitespace/punctuation; drop stopwords (`a`, `the`, `and`, `of`, `to`…). Remaining tokens are **AND** substring matches, case-insensitive, against cell **input + output** text (not burner NULL CLOBs).
3. Live: matching cards **highlight**; non-matching **dim** (preview of ghost). Nothing is written to the vault until OK.
4. **OK / commit:** non-matches → `EXCLUDED` (ghost). Matches: keep `INPUT_ONLY` / `OUTPUT_ONLY` / `INCLUDED`; if a match was `EXCLUDED`, **promote to INCLUDED** (-2 A). Burners still skip payloads.
5. After commit, four-state buttons still override (: fine-tune).
6. Pin **stays on** until the operator turns it off. New Turn N+1 cells are live-filtered the same way; **MOOD-LAYER is not re-run** until the next OK.
7. Off: highlights clear; EXCLUDED states **remain** (the pin applied a real prune). Operator un-ghosts with buttons. Do not silently restore.

No new wire. No AMWP. This is client vault + inspector. Working set assembly (§4.4) already skips EXCLUDED.

### 12.4 MOOD-LAYER — parts to order

1. Host config: a **shaper** model id (default: the same Ollama/Qwen is using). If missing, air-gap, or unreachable → skip this phase; LEX-FILTER still works. Message in the modal: “keyword only.”
2. After OK, if shaper exists: **modal** mini-conversation, scoped to the dialog. Not a canvas tab. Not Tab A `chat>`.
3. Mini-chat is **ephemeral / burner**. Not a canvas cell (-3).
4. Approve: one **system**-role shape message for the **entire conversation**, appended to the working set (style, mood, symbolic modifiers). Not stuffed into cell CLOBs. Inspector must show it.
5. Iterate in the modal until approve or cancel. Cancel = keep LEX-FILTER commit, no mood row.
6. Transport: ordinary Stage 1 execute (TOON working set over Scope A). No extra AMWP channel.

**Not:** LLM on keystroke; a second Roux writer; a fifth state; Lucene; embedding DB.

### 12.5 Staging (Face, in the doorway)

| Slice | Stage | Why |
|---|---|---|
| LEX-FILTER + pin + commit-to-EXCLUDED | | Needs Stage 1 cells. First S2 canvas slice. Local, no cloud. |
| MOOD-LAYER modal | | Not Grok Build. Not a Stage 1 dep. |
| Stage 1 | **Out** | Dumping this in S1 is Face underselling the MVP by boiling it. |

### 12.6 Fuzzy / syntactic match 006

Keyword **AND** substring is enough for LEX-FILTER. Stemming/synonyms = MOOD-LAYER. No third matcher.

## 13. SolarRose — **dereferenced**

Kit **remains on disk** at ` for her van. Do not load it. Do not implement Aetheris from it.

## 14. RC validation checklist

| Gate | Status |
|---|---|
| Constitution frozen | yes |
| | yes |
| | yes |
| | yes |
| On new Stage 1 Java | yes |
| Stage 1 encompassing Qs | **none open** |
| CONTEXT-PIN / MOOD-LAYER | |
| SolarRose | out of this workflow |
| | in laws |
| CORE-SPEC-LINK-002 | process; **not** an RC gate |
| Product Java | |

This working spec is **finalized**. Next: Stage 1 pre-plan (this turn) → stamps pre-plan → Stage 1 **plan** → build. Do not implement Aetheris Java from this file alone.

*English first. On new Java. CONTEXT-PIN stays Stage 2.*

*The contract is signed.

# Aetheris — Technical Specification — Release Candidate 001

**Mode:** design / SDD. **Not a plan. Not code.**
**Head:** this file, until freezes `.finalized.` or we write `release-candidate.002`.
**Updated by:** ( flying, on Roux locks, Face on chrome).

Markup: `[NEW in RC.001][STAGE n]`. Encompassing leftovers live in **§Q**. Do not invent those in a plan.

> *One more recon of the Hacienda. Then Stage 0 pre-plan. Watch your six. Tell to stop fiddling with the knobs.*

## 0. How to read this RC

Draft.003 was highlighter pass on 002. This RC **absorbs** those answers into normative prose so the next cycle is a freeze, not another archaeology dig.

**Removed from the van (full sweep):**

| Item | Fate |
|---|---|
| `@scope` cell-tag routing | AMWP **Scope T / Scope A** (wire channels) stay — different word. |
| True multi-writer Roux | Q2. Per-region `ReentrantLock` is `LOCK`. |

**One encompassing question remains (Q4 merge algorithm).** Local leftovers in §Q2. Everything else from 002 §Q is `LOCK` below.

## 1. Decision log — 003

| ID | Decision |
|---|---|
| Q1 | Compiler **and** runtime **JDK 26 Zulu**. Never Oracle. Stage 0 bumps `pom.xml` off 25. |
| Q2 | Multi-writer Roux **not needed**. Lock existing Roux single-writer + seqlock readers. |
| Q3 | §4.3 mapping accepted. Cell buttons 1–4 are **modal** (radio). Burner is a **pin on the cell**. Graph footer button **hidden** (Q9). Face does not need more input to ship Stage 1 chrome — see §4.3. |
| Q4 | Parallel group + merge is **Stage 2**. **Algorithm still ** — see §Q. Reads "Stage 2 works" as *when*, not *how*. |
| Q5 | Vault root **`~/.aetheris/{user}/vault`**. H2 is Aetheris Shell **only**. No H2 in Casserole. |
| Q6 | Tab A `chat>` **never auto-lands** as cells. Crosstalk is operator-explicit. |
| Q7 | Do not spend Stage 0 removing leftover JPA. We do not care yet. Roux is the server store. |
| Q8 | Window title + scripts = Aetheris Shell in Stage 0. **Keep** `SshClientApp` / `org.casserole.app.ui.ssh`. |
| Q9 | Hide LCARS / Graph chrome. Conversational cards first. |
| Q10 | **No JSON** on new Aetheris/AMWP/object paths unless a use case earns it. Envelope: length-prefixed records. Structured objects: **TOON**. Tab A JSON *reply formatter* is `PRESERVE-ALL` and stays. |
| Q11 | **`vault.mode: ram` is the product default** for everyone. Disk is crash-proof opt-in. Status bar shows the mode. No scolding WARN — ram is not a dirty secret. |
| Q12 | `OUTPUT_ONLY` is a first-class fourth state. Ghost is a **modifier** of `EXCLUDED` (visual dim), not a fifth state. |
| Q13 | **Log4j2 both processes.** No Logback. Log4j config = **`.properties`**. App config = **yaml**. Annotation support required. |
| Q14 | AMWP **little-endian**. |
| Q15 | Tab A `chat>` writes vt100 Roux independently while Aetheris is connected. Two conversations. |
| Q16 | |
| Q17 | HikariCP on the **Shell only**. No Casserole Spring datasource. |
| Q18 | After finalize: extract **Stage 0 pre-plan** for review, then full Stage 0 plan. After a stage ships, return to this spec and pull the next. **MVP = Stage 1.** Do not contaminate the finalized spec with in-flight stage plans. |
| LPU | First cloud provider implementation = **Grok**. Establish a **Provider SPI** (MCP + REST) so others factor in later without a rewrite. Stage 1 engine = local Ollama. |
| Roux Aetheris | Two distinct Roux `ChatMemory` instances when both channels are live. Incremental sync preferred later. `[NEW in 003]` |
**semantic-key:** PK-783
**semantic-key:** PK-783

## 1. Decision log — 003

## 2. Product heart

**Aetheris** is a high-velocity, sovereign **desktop notebook-style canvas and cognitive engine**. Every non-burner interaction is an **immutable, locally owned asset**.

## 2. Product heart

**Failures it closes**

1. **Storage trap.** Answer: client H2 vault. Search, index, branch, export, cold-boot (disk mode) independent of any cloud vendor.
1.1 **Isolation Layer.** Data sovereignty + perpetual history + tracing. Paid LPU (**Grok**, via Provider SPI) is a *target*, not a landlord. Surgical window/token control is a **user** surface: four-state, ghost, working-set preview.
2. **Privacy vs capability is all-or-nothing.** Answer: per-cell local vs cloud + burner cells (pin). Burner is not safe-to-send-to-Grok by default.
3. **Black-box context / token bloat.** Answer: four-state pruning (`INCLUDED` / `EXCLUDED` / `INPUT_ONLY` / `OUTPUT_ONLY`) so a dirty debug thread can sit next to architecture work without poisoning the model. Canvas tabs isolate conversations. **No `@scope`.**
4. **Parallel LLMs, merged results.** Answer: a **parallel group** — horizontal scroll of n cells, distinct `execution_target`s, concurrent execute, merge cell. `[STAGE 2]`. Stage 1 still has per-cell model selectors.

**The apple:** local MCP host (Casserole) + conversational cells (Aetheris) + real SSH/VT100 (DarkJediTerm), one package, one AMWP pipe.

**Canonical use cases:** offline-first Ollama + local MCP; hybrid poly-engine (local then Grok on the same canvas); DevOps on one pipe (Tab A live machine, Tab B canvas).

**The Aetheris Declaration**

> Your thoughts, your code, and your cognitive history belong to you — not to a cloud provider’s ephemeral web interface. Aetheris is a shift from passive, rented chat streams to active, sovereign cognitive orchestration. Local H2 persistence, four-state context control, poly-engine targeting, and Spring AI tool hooks on a high-velocity desktop canvas: intelligence is fast, context is controlled, and memory endures.

“Zero-Knowledge Local H2 Memory Vault” = vault on the operator’s hardware; vendors do not hold history; burners are non-retained. **Not** zk-SNARKs.

### 2.1 Placement in this tree

1. **Tab A DarkJediTerm** — `PRESERVE-ALL` and forward-evolved.

### 2.1 Placement in this tree

3. **AMWP** — one login, Scope T opaque VT100, Scope A framed canvas. `ONE-PIPE`.
4. **Three pillars.** MCP host is Casserole. Roux vt100 stays Tab A chat memory. Aetheris Roux is a separate RAM projection.

Notebook **and** VT100 as-is **MUST** work over AMWP.

**H2 IS the Aetheris vault, in the Aetheris Shell.** Casserole does not open it.

### 2.2 Dual source of truth

| Mode | Conversation-of-record | Casserole holds | Aetheris Shell holds |
|---|---|---|---|
| **Aetheris connected** `CLIENT-SOT` | Client H2 vault | Ephemeral Roux `{user}:aetheris` (RAM). | Every non-burner turn, graph, traces. Crash-proof **only in disk vault mode**. |
| **VT100 `chat>`** `SERVER-SOT` for that channel | Roux `{user}` pack (disk exclusive as today, or RAM-only if `hot-only`) | Linear `chat>` + future MCP-like session. Crash-resilient. | N/A |
| **OpenSSH / no AMWP** | Roux vt100 | Today’s ChatShell | Aetheris tab unavailable on that connection |

**Rules**

1. Casserole stays a correct agent host without a client vault.
2. Connected, Casserole does not pretend the vt100 pack is canvas history. The Prompt for a cell is the working set the client sends.
4. `NO-DUAL-WRITE`: Aetheris cells never enter the **vt100** Roux pack. Tab A `message find` never returns them.
5. Tab A `chat>` **still writes vt100 Roux** while Aetheris is connected. Two conversations, one username, meet only via explicit crosstalk (§13).
6. **Two ChatMemory instances** when both channels are live, distinguished by user **and** conversation type (`vt100` | `aetheris`). Spring AI `ChatModel` path is the **same**. Aetheris adds adapters for cell_id / cancel / working-set, not a second LLM stack. `[PROXIMITY-ALERT resolved]`

### 2.2 Dual source of truth

8. Incremental sync = later preference, not Stage 1.

```
 execute cell
 Client: assemble WORKING-SET from H2 + four-state + ghost
 │ AMWP Scope A execute (TOON structured payload, length-prefixed envelope)
 Casserole: replace {user}:aetheris Roux window → Lamina → ChatModel / LlmService
 │ AMWP Scope A stream / single chunk
 Client: paint response; persist to H2 (unless BURNER)
```

## 3. Product laws

RC.001 amendments: two Roux regions, dual-SOT + RAM vault default, JDK 26, stage cadence, RC state, `@scope` and multi-writer in non-goals.

## 4. Problems

| ID | Failure in web AI tools | Aetheris |
|---|---|---|
| P1 | Disposable history / IP lock-in | H2 vault in the Shell |
| P2 | Local *or* cloud, no per-blob control | Per-cell engine + burner pin |
| P3 | Automatic full-history context | Four-state + ghost; working set rebuilt on the client |
| P4 | No A/B, no parallel models, no pipelines | Fork, pipes, chain, **parallel group + merge** `[STAGE 2]` |
| P5 | Cloud agents you cannot own | Local MCP host; cells call *your* tools; traces in *your* vault |
| P6 | Flows you cannot see or debug on the box | Canvas authors; host assembles from contracts; PTY is ops debugger |

Tab A still uses Roux + `casserole.chat.max-messages` / `max-tokens`. That window is not Aetheris pruning.

## 5. Packages and shared space

```
org.casserole SSH host, ChatShell, commands, formatters
org.casserole.app.ui.ssh Terminal tab — preserved. Stage 0 title/scripts only.
org.casserole.llm LlmService + Provider SPI (Ollama now; Grok Stage 2)
org.casserole.lamina LTEM — server thread pools only
org.roux Paged memory — preserved
org.roux.spring ChatMemory adapter — vt100 AND aetheris conversationIds
org.aetheris vault DAO, canvas model, AMWP Scope A façades
org.aetheris.swing Tab B only
org.aetheris.vault JDBC DAO, SQL constants, Hikari, schema
```

Aetheris Shell **does not start Spring Boot**. All LLM conversation traffic rides **AMWP** and **always** hits `ChatModel` on the host. Aetheris does not grow a special LLM stack — adapters only.

| Pillar | Package | Role |
|---|---|---|
| **Terminal** | `org.casserole.app.ui.ssh` + `ChatShell` | Tab A |
| **MCP / agentic host** | `org.casserole` + Lamina | Local tools/agents; Spring AI |
| **Conversational cells** | `org.aetheris` | Tab B — vault, prune, per-cell engines |

Reuse: provider registry, execute, cancel, hot-swap Ollama are **host** abstractions. Tab A later gets `swap llm1 llm2` and a SQL-oriented DSL. Tab B gets cells. Do not copy-paste two routers.

## 6. Desktop layout

Mockup is **Tab B**. Tab A unchanged.

```
AetherisShell (SshClientApp frame, extended)
 connection strip (host, user, Connect, New Term, Close Pane) ← SSH only
 canvas tabs Tab 1 | Tab 2 | Tab 3
 JSplitPane horizontal
 Aether Space (scroll of turn-cells)
 Tools & Settings (inspector + working-set preview)
 Aetheris command strip
 Status and State bar (includes vault mode: ram | disk)
```

New Term / Close Pane / Connect still mean SSH panes. A new conversational turn is not New Term.

## 6. Desktop layout

### 6.2 Cell as workflow unit

Chat is a stack of owned, addressable turns. Completing Turn N materializes empty Turn N+1 (Jupyter “run and insert below”). Re-run, change model, prune, or push **that** turn. Vault persists prompt+response unless burner.

### 6.3 UI bindings Q3 + Q9 + Face

Cell buttons 1–4 are a **radio group**. Exactly one selected.

| Control | Mapping |
|---|---|
| Cell Button 1 | `INCLUDED` (default) |
| Cell Button 2 | `EXCLUDED` (ghost visual: dim) |
| Cell Button 3 | `INPUT_ONLY` |
| Cell Button 4 | `OUTPUT_ONLY` |
| Cell pin (top-right chrome) | `is_ephemeral` / burner. Not a 5th segment. |
| Footer Button 1 | **Run** focused (or checked batch) |
| Footer Button 2 | **New turn** (insert below) |
| Footer Button 3 | **Working-set preview** toggle |
| Footer Button 4 | **Graph / LCARS** — **absent in Stage 1–2**. Do not leave a disabled mystery button. |
| Footer Option | `max_tokens` / `temperature` / `top_p` (no Burner here) |
| Footer spinner | Value for selected Option. Mockup `100` = default max_tokens. |
| Checkbox | Multi-select |
| Header dots | AMWP / engine / vault |

Hotkeys (Aether Space root only, `WHEN_IN_FOCUSED_WINDOW`): Ctrl+Shift+I / E / P / O.

## 7. AMWP Q14 + Q10

Demux between SSH bytes and the two apps. `OPAQUE-T`. No WebSocket.

| Server | Terminal tab | Aetheris tab |
|---|---|---|
| No AMWP | Raw PTY as today | Unavailable |
| AMWP | Scope T ≡ today’s PTY | Scope A live |

Init; no valid `0xAE` reply → raw PTY, never wrap JediTerm.

### 7.1 Frame header

Little-endian. CRC32C of `[0,12)` + payload.

| Offset | Size | Field |
|---|---|---|
| 0 | 1 | Magic `0xAE` |
| 1 | 1 | Version `1` |
| 2 | 2 | Channel id |
| 4 | 1 | Type: `0x00` Init `0x01` Data `0x02` Prune `0x03` EOF `0x04` Error `0x05` Cancel `0x09` Keepalive |
| 5 | 1 | Flags: bit0 ephemeral, bit1 zstd **defer**, bit2 more-fragments |
| 6 | 2 | Reserved |
| 8 | 4 | Payload length |
| 12 | 4 | CRC32C |
| 16 | n | Payload |

**Max payload:** 1 MiB. Larger messages fragment (bit2).

**Fragment prefix** (every Scope A payload), leftover closed: `uint32 frag_index` + `uint32 frag_count` + `cell_id` UUID (16 bytes), then body.

### 7.1 Frame header

**Cancel:** payload carries `cell_id`. Host aborts that Lamina work. Tab A Ctrl+C remains that pane’s `chat>` cancel.

`0x0100+` extra streams: server allocates next free ≥ `0x0100` in Init-ack. Client does not hardcode.

| Channel | Scope | Payload |
|---|---|---|
| `0x0001` | T | Opaque VT100 octets |
| `0x0002` | A | Control: CRUD, state, execute, cancel, traces |
| `0x0003` | A | LLM stream for a cell |
| `0x0004` | A | Tools (MCP) `[STAGE 2]` |
| `0x0100+` | A | Parallel branch streams `[STAGE 2]` |

**Execute payload (Stage 1):** assembled working set + `cell_id` + `execution_target` + knobs + `tool_calling_enabled`. Envelope length-prefixed. Structured body **TOON**. Host does not fetch H2.

Prune frames push a preview working set before Run. They do not persist on the server.

## 8. Canvas & cell model

Does not change Roux **vt100** frames, `ChatSession`, or `message list`.

```
Workspace 1..N (per SSH username, client vault)
 Canvas 1..N
 Cell 1..N order, parent, branch, column
 Traces
 Branch records
 Parallel groups [STAGE 2]
```

| Field | Meaning |
|---|---|
| `cell_id` | UUID |
| `cell_order` | Notebook order |
| `turn_index` | Display Turn N. Dense per canvas. Not Roux `seq` |
| `parent_cell_id` | DAG parent (`null` = column root) |
| `branch_id` | Parallel path (`main` default) |
| `column_position` | UI column |
| `group_id` | Parallel group (`null` = sequential). `[STAGE 2]` |
| `execution_target` | `provider/model` |
| `context_state` | `INCLUDED` \ | `EXCLUDED` \ | `INPUT_ONLY` \ | `OUTPUT_ONLY` |
| `is_ephemeral` | Burner pin. NULL payloads |
| `prompt_payload` / `response_payload` | CLOB or NULL |
| Knobs | temperature, max_tokens, top_p, tool_calling_enabled |
| `execution_status` | `IDLE` \ | `RUNNING` \ | `COMPLETED` \ | `FAILED` \ | `INTERRUPTED` |

No `@scope` column. No cell-tag routing.

### 8.1 Four states Q12

| State | In later Prompts? | Persist for the human? | On canvas? |
|---|---|---|---|
| **INCLUDED** | Prompt + response | Yes (unless burner) | Yes |
| **EXCLUDED** | Neither | Yes (unless burner) | Yes, **ghosted** (dim) |
| **INPUT_ONLY** | Prompt only | Yes | Yes |
| **OUTPUT_ONLY** | Response only | Yes | Yes |

### 8.2 Ephemeral / burner

Execute in RAM, paint UI, persist metadata/traces with **NULL** payloads. Refuse push, refuse export body, refuse cloud target unless allow-cloud-burner policy. Best-effort heap drop (`String` is not erasable).

## 9. Context assembly — no `@scope`

Runs on the **client**. Host receives the built working set.

Walk `cell_order` before target (notebook). DAG: lineage `target → parent → root`; ignore siblings unless MERGE or `{{cell[…].output}}`. Parallel-group siblings are not in each other’s working set `[STAGE 2]`.

Then:

1. Skip `EXCLUDED` / ghosted
2. Skip empty ephemeral content
3. `INPUT_ONLY` → user only
4. `OUTPUT_ONLY` → assistant only
5. `INCLUDED` → user + assistant
6. Resolve variables on target `[STAGE 2]`
7. Append target prompt

Inspector **must** show this list (roles + byte/token estimate) before Run. Optional canvas-local window cap is separate from `casserole.chat.max-messages`. Stage 1: no cap; inspector shows the estimate.

## 10. Piping `[STAGE 2]`

| Mode | Effect |
|---|---|
| Text push | Copy source response into target prompt (static) |
| Variable push | `{{cell[k].output}}` at each target execute |
| Chain | Topo-exec dependencies (Ctrl+Shift+F5) |

Source must be COMPLETED. Ephemeral cannot push. Cycles fail. Chain stops on FAILED. Stage 1 may ship without pipes.

## 11. Branch, merge, parallel groups `[STAGE 2]`

Fork: new column, new `branch_id`, parent = fork cell. Merge: explicit node or variable refs — not “all columns.” Parallel execute on Lamina `llm-exec`, one in-flight per cell, EDT for UI. Layout inside the Aetheris tab, independent of Terminal splits.

**Parallel group** `[NEW in 003]` — operator CRUD:

- Promote focused cell → group (becomes the first member).
- Add cell to group (new sibling, different `execution_target`).
- Remove cell from group (returns to sequential column, or delete).
- Horizontal scroll of members; merge cell below.

Merge **algorithm** is §Q (only remaining encompassing question). Timing is Stage 2.

## 12. Persistence Q5, Q11, Q17

| Store | Owner | Purpose |
|---|---|---|
| **H2 vault** | Shell `org.aetheris.vault` | Canvas SOT. Search, index, branch, export, cold-boot (disk). |
| **Roux vt100** | Casserole `{user}` | Tab A `chat>`. Exclusive disk as today. |
| **Roux aetheris** | Casserole `{user}:aetheris` | Ephemeral Spring AI projection. RAM **always**, regardless of `roux.durability`. |
| **Burner cells** | — | Vault row may exist; payloads NULL. |

**Path:** `~/.aetheris/{user}/vault` (username sanitize `[A-Za-z0-9._-]+`). Override in `aetheris.yaml`.

One Shell process → one H2 lock per vault path. Second Shell on the same path fails fast in the status bar.

**`aetheris.yaml`** — `--aetheris-yaml <path>`; else `./aetheris.yaml`; else `~/.aetheris.yaml`.

```
 mode: ram # ram | disk (ram is default)
```

| `vault.mode` | JDBC | Meaning |
|---|---|---|
| `ram` | `jdbc:h2:mem:aetheris` | **Default.** Fast. Process death loses canvas. Status: `vault: ram`. |
| `disk` | `jdbc:h2:file:{path}` | Crash-proof. Cold-boot canvases without Casserole. Status: `vault: disk`. |

Perpetuity in the Declaration is **disk mode**. Ram default is a product choice, not a contradiction — the operator opts into perpetuity.

**PURE-JDBC.** HikariCP standalone on the Shell. No JPA / Hibernate / Spring Data. SQL constant classes. Schema version table + ordered DDL constants on open. **No Flyway**.

Capabilities: persist on complete (unless burner); search required (H2 `LIKE` Stage 1; Lucene later); branch topology; cold-boot in disk mode without Casserole; export/import `.aetheris` **TOON**, versioned, UUID remap, strip burners by default. `[STAGE 2]` for export.

AES-256 at rest: not now.

### 12.1 Sharp edges

- Ghost vs delete vs burner stay three verbs (state / purge / pin).
- Rebuild working set on every execute.
- Show the working set.
- Crash of `RUNNING` → `INTERRUPTED`; streamed text stays on screen, not INCLUDED until commit.
- Edit a past turn: allowed; next assembly uses new bytes.
- Aetheris Roux RAM dies with the SSH session; H2 (disk) does not.

## 13. Poly-engine and Provider SPI

Cell `execution_target` = `provider/model`.

**Provider SPI** `[NEW in RC.001]`: discrete host interface for local engines and external MCP/REST LPUs. Stage 1 implements **Ollama**. Stage 2 implements **Grok**. ChatGPT/Claude are SPI-shaped, not built.

Router:

1. `air-gap: true` + cloud target → fail cell, banner. Do not silently rewrite stored target.
2. Ollama down + local target → fail cell (engine-down, not air-gap).
3. Ephemeral + cloud + policy deny → fail cell.
4. Else provider registry.

`chat>` keeps `spring.ai.ollama.chat.options.model` until Tab A grows a picker.

Streaming: as-built one-shot `complete()`. Scope A may send a single chunk. No `Flux` in `org.aetheris` types on day one.

**`swap <llmA> <llmB>`** `[STAGE 1]` on Tab A: unload/load Ollama for VRAM-constrained boxes. Shared host abstraction.

Cloud keys: env / `~/.aetheris/` or `~/.casserole/`, **not** cell text unless burner.

**Air-gap** (leftover closed): yaml `aetheris.network.air-gap`. `true` forbids cloud targets. Ollama-down is a separate error.

## 14. Tools / MCP `[STAGE 2]`

MCP server lives in Casserole. Cells with `tool_calling_enabled` invoke it over `0x0004`. Inspector shows *your* tool groups. Bounded depth, allowlist, never auto-pipe into the PTY. Traces persist in the vault unless burner.

## 14. Tools / MCP `[STAGE 2]`

## 16. Crosstalk Q6

| Direction | Default |
|---|---|
| SSH selection / last N lines → new **EXCLUDED** cell | Operator action. No auto-tag. |
| Cell fence → PTY (Scope T) | Operator action + confirm; never ephemeral |

Not automatic.

## 17. Failure

`RUNNING` until EOF, cancel, or error. `FAILED` / `INTERRUPTED` + banner + re-enable Run. Partial stream may stay; not INCLUDED until commit. No H2 transaction spanning an LLM call.

## 18. Security

| Phrase | Mechanism |
|---|---|
| Zero-Knowledge Local H2 Memory Vault | File or mem H2 **in the Shell**; no vendor copy of canvas history |
| Lifetime / perpetuity | **Disk** vault mode |
| Burner | NULL payloads; pin; no cloud-by-default |
| Cloud LPU | Once sent, it left the sovereignty boundary |
| Multi-user | `users.yaml` + per-user vault path; Roux disk exclusive is Tab A only |

## 19. Conflicts — closed

| Topic | Stance |
|---|---|
| Tabs vs absorb | SSH preserved; Aetheris sibling |
| AMWP | Required for dual-channel login; raw fallback |
| H2 | Client vault only. Path `~/.aetheris/` |
| Roux | vt100 pack + ephemeral `{user}:aetheris` RAM. No dual-write into vt100 |
| `@scope` | Removed |
| Multi-writer Roux | Scratched. `ReentrantLock` locked |
| Four states | Modal radio + ghost-as-EXCLUDED-visual + burner pin |
| JDK | 26 Zulu |
| JSON | Not on new Aetheris/AMWP paths. Tab A formatter stays |
| LlmService vs ChatModel | Same ChatModel path; AMWP is the wrapper; adapters only |

## 20. Staged SDD (not a plan) Q18

After finalize: **Stage 0 pre-plan** → Stage 0 plan → build → return here → Stage 1 plan → … MVP is Stage 1.

### STAGE 0 — The van starts

**In:** Aetheris Shell title + `bin/` scripts; `aetheris.yaml`; Log4j2 both sides (`.properties`); Zulu 26 JRE bind + `pom` bump; install helpers (Ollama, Zulu, Windows bootstrap new-installs only); **Lamina** migrate existing server threads, metrics OFF, no virtual threads; preserve-all raw-PTY Tab A.

**Out:** cells, AMWP, vault schema, LCARS, MCP, pipes, Grok.

### STAGE 1 — Sovereign notebook MVP

**Out:** parallel groups, pipes/fork, MCP, Grok, LCARS, `.aetheris` export, SQL DSL, contract agents, Graph button.

### STAGE 2 — Poly-engine and workflow

Parallel group CRUD + merge (Q4 algorithm); pipes/chain/fork; **Grok** provider; air-gap rules; burner-cloud policy; search LIKE complete; export/import TOON; crosstalk; MCP + `0x0004`; DarkJediTerm SQL DSL **design serial**.

## 21. Ops `[STAGE 0]`

`bin/` at repo root. Windows 11 (ps1/cmd/bat), Linux bash, macOS.

| Script | Job |
|---|---|
| `casserole` | Start server on bound Zulu 26 |
| `aetheris-shell` | Start desktop app |
| `install-ollama` | Download/install Ollama |
| `bootstrap-env` | Windows deps, **new installs only** |
| `fetch-zulu-jre` | Azul Zulu 26 JRE; scripts reference it |

Split a dedicated ops serial when Stage 0 pre-plan is requested. Until then this section is the requirement.

**Logging:** Log4j2, both processes, **`.properties`**, no Logback. INFO quiet. DEBUG useful without full prompts. TRACE payloads except **never** burner payloads.

## 22. Code standards

## 23. Lamina (LTEM) `[STAGE 0]`

Casserole server **only**. `org.casserole.lamina`.

## 23. Lamina (LTEM) `[STAGE 0]`

Interface: submit / cancel / metrics. Metrics default **OFF**. All existing server thread usage migrates. Core Java threads, **not** virtual. Virtual = Plan B + lock.

## 25. Roux writer model Q2 — not a feature request

As-built: per-region `ReentrantLock` (writers wait, never 50 ms fail); readers seqlock + pin; disk exclusive one live vt100 shell per username.

That is the product. Aetheris does not append the vt100 pack. Parallel cells contend Lamina, not `pages.bin`. `{user}:aetheris` is a **second region**, RAM, own lock, no FileLock.

Do not open a Roux 1.2 multi-writer protocol.

## Q. Remaining questions

### Q.1 Parallel-group merge algorithm (was Q4)

Stage 2 works.”** Timing is locked (Stage 2). The option list was:

- A: Concatenate with model banners first; optional LLM-merge later
- B: LLM-merge from day one of Stage 2
- C: Operator-picked winner only

Reads **B** as *Stage 2*, not *LLM-merge*. **Need one word:** `CONCAT` | `LLM-MERGE` | `PICK-WINNER`.

Recommendation if you just: **CONCAT**, with LLM-merge as a Footer Option on the merge cell later.

We will address merge later, add note to keep in the get-back-to file as the stages evolve. I will ask you to keep an eye on the best way to approach the merge option when enough infrastructure evolves, through stages development, to support it such that the actual gaps to properly implement can be shaken out. When so, highlight accordingly in an updated spec version.

### Q.2 Local leftovers — closed unless you veto

| Item | Lock in this RC | Veto? |
|---|---|---|
| Air-gap | yaml `aetheris.network.air-gap` | LOCK |
| Fragment header | `frag_index` + `frag_count` + `cell_id` | LOCK |
| Backpressure | one in-flight execute per cell | LOCK |
| Flyway | No. In-tree DDL | LOCK |
| Lamina lanes | `ssh-io`, `llm-exec`, `general` | LOCK |
| Canvas token cap Stage 1 | None; inspector shows estimate | LOCK |
| `.aetheris` export | Stage 2, TOON | LOCK |
| Q10 TOON vs length-prefix | **Both:** envelope prefixed, body TOON | LOCK |
| | Full push per execute | LOCK |

No other encompassing Qs. Q1–Q3, Q5–Q18 are `LOCK`.

*Note to Grok Build: this serial is the head. Do not implement. Do not write a plan until asks for the Stage 0 pre-plan (Q18).*

* filed RC.001. sat on `@scope` until it stopped twitching.
