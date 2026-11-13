# org.casserole.ssh

## Child pages

<pre>
org.casserole.ssh/
└── <a href="shell/shell.spec.md">org.casserole.ssh.shell</a>
</pre>

## 0. Locked answers this plan uses

| ID | Lock |
|---|---|
| P0-1 | Scripts default **`mvn spring-boot:run`**. Optional `--jar` / fat-jar if the packaged jar already exists; otherwise postpone fat-jar polish. |
| P0-2 | JRE home **`~/.aetheris/jre/zulu-26/`**. Universal across repo locations. |
| P0-3 | Leave `java.util.Timer` (escape/spinner) off Lamina. |
| P0-4 | MINA NIO stays library-owned. |
| JAVA_HOME | If env `JAVA_HOME` exists **and** is JDK/JRE **26**, inherit it for that script run. Else scripts set `JAVA_HOME`/`PATH` to the fetched Zulu 26 and **override** any other external Java. |
| JRE vs SDK | Prefer JRE; full SDK only if JRE fetch cannot satisfy compile/`mvn`. |
| Leftovers | `MinimalSshServer`, `SshServerTest` — **leave in repo**, do not migrate, do not delete. |
| Lombok | Named getters/setters. |
| Fat jar | Easy optional flag only; default is Maven start. |
**semantic-key:** PK-477
**semantic-key:** PK-477

### 6.4 Grep gate (server)

After P3, `org.casserole` **excluding** `app.ui.ssh`, `ssh.MinimalSshServer`, `ssh.SshServerTest` must not contain `new Thread(` or `Executors.new` on the server path.
**semantic-key:** PK-478
**semantic-key:** PK-478

## 10. Files / components (checklist)

**Do not touch:** Roux engine, formatters, JediTerm split logic, `users.yaml` auth, `MinimalSshServer`, `SshServerTest`, JPA yaml.

### 2.2 Out (do not put in the Stage 1 plan)

| Item | Belongs |
|---|---|
| CONTEXT-PIN / LEX-FILTER / MOOD-LAYER | |
| Parallel groups, pipes, fork, CONCAT merge | Stage 2 |
| MCP `0x0004`, `TOOL_EXEC` pool | Stage 2 |
| Grok cloud adapter | Stage 2 (optional shaper later; not S1) |
| `.aetheris` export/import | Stage 2 |
| Boot 4, SnakeYAML, Flux, colon region ids | Never in S1 |
| `@scope`, dual-write, REQ-THREAD catalog | `SCRATCHED` |
| | Optional later; not a gate |
| 2PC in manifest, `DEC-*` rationale IDs | LINK-002 scratched |
| Class rename off `SshClientApp` | Q8 — dedicated serial |
| Delete leftover JPA / `MinimalSshServer` | |

### 3.2 Leftovers (leave in repo)

`MinimalSshServer`, `SshServerTest`, unused JPA + `jdbc:h2:mem:casserole`. Do not migrate. Do not delete until says.

## SSH I/O logs the connect and the disconnect

No new package. No new public type. No new public method. The package-private type is SshIoTrace. The package methods are attach and authReject. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The INFO lines are the named change. This land leaves the working set as it stands. The SSH mux stays. Channel A stays. The command-line history stays. Roux stays. The yaml stays. MinimalSshServer stays. The log level stays. The Swing client stays unstaged. The 18:22 layer sentences stay.

## 2. What stays

The SSH mux stays. Channel A stays. The command-line history stays. Roux stays. The yaml stays. MinimalSshServer stays. The log level stays. The Swing client stays unstaged. The card-round hunks stay unstaged. The page-arm, the pane scroll take, and the map_key flip stay unstaged. The banner stays unstaged. The 18:22 layer sentences stay.

## 3. Edits

`iface-mac` is the hardware address of the NetworkInterface that owns the local socket address. The lookup is getByInetAddress, then a walk that matches that same address. A wildcard bind has no owning interface, so iface-mac is absent. `peer-mac` is absent on every line. The duration clock is an attribute on the session at session-open and on the channel at channel init. Server shutdown closes sessions through these listeners. MinimalSshServer is not the product listener.
**semantic-key:** PK-479

## 2. What stays

### 5.1 Lifecycle

Leftover classes `MinimalSshServer` and `SshServerTest` are **not** Spring beans. They are earlier echo/PTY experiments with the same POC credentials. Ignore them for product behavior.
**semantic-key:** PK-480
**semantic-key:** PK-480

### 11.4 Engineering hygiene

- Delete or isolate `MinimalSshServer` / `SshServerTest`.
**semantic-key:** PK-481
**semantic-key:** PK-481
