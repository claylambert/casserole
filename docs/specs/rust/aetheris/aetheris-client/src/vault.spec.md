# rust.aetheris.aetheris-client.src.vault

### **Executive Overview & Product Vision**

While cloud providers like OpenAI, Google, or Groq hold your data captive behind ephemeral web UI sessions?where chat histories disappear, search fails, and context is locked in vendor silos?**Aetheris treats every interaction as an immutable, locally owned asset.** Powered by a local-first **H2 Storage Vault** and operating over a custom, ultra-lightweight binary SSH multiplexing protocol (**AMWP**), Aetheris gives operators complete privacy, lifetime persistence, and deterministic control over their entire AI workflow.
**semantic-key:** PK-601

### **2.2 Ephemeral Data Zeroing Pipeline**

4. In-memory `byte[]` buffers holding key material or confidential snippets undergo an immediate zeroing sweep (`Arrays.fill(buffer, (byte) 0)`).
**semantic-key:** PK-602

### **4.1 Real-Time UI Persistence Pipeline**

Every interactive state change on the Swing canvas triggers an immediate asynchronous event update to the Casserole Server to keep the embedded H2 Storage Vault in sync:
**semantic-key:** PK-603

### **4.1 The "Why" Behind Ephemeral Zeroing & H2 Persistence**

* **Lifetime Asset Ownership:** Default cell executions must persist locally in the embedded H2 database to retain intellectual property across workspace reboots.
**semantic-key:** PK-604

## **1. The Nature of the Aetheris Workspace**

* **From Opaque Cloud Silos to Complete Sovereignty:** Powered by an embedded, zero-knowledge H2 Storage Vault and communicating over a custom binary multiplexing protocol (AMWP), Aetheris ensures that intellectual property and sensitive credentials remain strictly under the operator's control.
**semantic-key:** PK-605

### **1. Ephemeral Memory & Intellectual Property Lock-In**

* **The Solution:** **Zero-Knowledge Local H2 Storage Vault.** Every turn, execution trace, token count, and latency metric is automatically persisted to local hardware. Workspace memory endures into perpetuity, allowing operators to branch, search, and cold-boot entire historical projects 100% offline.
**semantic-key:** PK-606

## **4. Data Model & Database Schema Extensions**

To support non-linear DAG (Directed Acyclic Graph) trees in the embedded H2 Storage Vault, the schema utilizes explicit parent and branch references.
**semantic-key:** PK-607

# Aetheris — Stage 1 plan — serial 001

> *The notebook stands. Tab A still drives. One pipe. Vault in the Shell. No Graph. No CONTEXT-PIN.*
**semantic-key:** PK-608

## 6. P3 — Vault

PURE-JDBC. SQL constants class. In-tree DDL applied on open.
**semantic-key:** PK-609

## 6. Suggested `aetheris.yaml` sample (Stage 0 committed file)

Ship a sample the Shell can copy. Vault keys are documentation for Stage 1 — **ignored** now.
**semantic-key:** PK-610

# Aetheris — Stage 1 pre-plan — serial 001

> *The notebook stands. Tab A still drives. One pipe. Vault in the Shell. No Graph button. No CONTEXT-PIN.*
**semantic-key:** PK-611

### Edges, bottlenecks, and failure modes

 - Vault save writes NULL CLOBs for an ephemeral cell.
**semantic-key:** PK-612

## The report

- **Vault open and a bad state token.** `Final gap solutions` 9 and 12.
**semantic-key:** PK-613

### CONTEXT-PIN and LEX-FILTER

- Vault persist of state.

## 2. Product heart

Powered by a local-first **H2 Storage Vault** (in the **Aetheris Shell**) and **AMWP** over one SSH login. Privacy, lifetime persistence (disk vault), and deterministic context control are the point.
**semantic-key:** PK-614

## 6. Desktop layout

**Which SSH session feeds Aetheris?** Focused AMWP-capable pane’s user. Vault is local per username. Legacy raw-PTY pane → Aetheris tab unavailable; SSH tab still works.
**semantic-key:** PK-615

## 2. What stays

The empty host seat stays. The server still replaces it. Vault cell text stays unspliced. The pin file stays. A paint does not consume pending. A Run still does. The mood header on the inspector stays. Glass appendMoodHeader stays. The switch does not gate that chat. The shell frame stays. SSH has no inspector card on this plate. The paging memorandum stays unstaged. application.yaml stays. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No new public type. No new JUnit class.

## 3. The edit

The write is `Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(body), null)` on the Swing thread. A null, empty, or whitespace body returns and leaves the clipboard as it is. `IllegalStateException` sets the status line through `host.aetherisStatus(statusLine("clipboard busy"))`. The error slide stays closed. The reply text stays off the status line and off the logs. Vault text and `JTextArea` use `\n`. The clipboard write turns a `\n` that is not already part of `\r\n` into `\r\n`, so a paste into Notepad keeps the breaks. `normalizeNewlines` stays the paste-in path.
**semantic-key:** PK-616

## 3. pity

| Dependency | What the delete does with it |
|---|---|
| | An empty group id still removes that one row, drops it from the order, renumbers the survivors from 1, and removes trace rows for that id. |
| | A member is a card of the set. The click removes the set. One member does not leave alone. |
| | The open case closes. The status line that said the sink stays is gone. The merge row leaves with the members. |
| | The button is off when the removal set is the whole canvas. Delete does not insert a replacement cell. Clear turns remains the wipe. |
| | Token text stays as written. A parent pointer on a survivor stays stored. |
| | Sapphire stays as filed. Delete reads the group id, including when only the armed member is lit. |
| | A later turn's working set is built from the cells that remain. The JOIN lines leave with the merge row. |
| | The group timer is stopped before the worker, so a wait does not mark the members and write them back. CONCAT has no members left to join. |
| | The copy has a new group id and stays. |
| | The region lock stays. The desktop sends no pool session message. A later working set that no longer extends the hold starts a session by the rule already landed. |
| | The copy icon and the View icon stay. |
| In-flight member | A RUNNING member of the set receives the same cancel frame Stop sends. Its phase becomes STOPPED. A held answer for a removed id is dropped. |
| Pipe chain | A chain that names a removed id is cleared, so the next cell in that chain is not dispatched into a hole. |
| Late save | `saveCell` inserts when an update matches zero rows. The removed ids are buried before the worker. `persistAll`, `persistQuiet`, and `saveCells` skip a buried id. A failed delete lifts the burial. A successful delete keeps it for the session. |
| Vault write | One transaction deletes every id, its trace rows, the renumber, and the order. `deleteTurn` delegates to that write so a one-cell delete stays one transaction. |
| Focus | The armed card becomes the cell that followed the first removed index, or the last remaining cell when the set sat at the end. |
| Grok and thinking holders | Every removed id is forgotten from the Grok document and dropped from the thinking map. |
| Other canvas | Stays. The delete names this canvas id. |
| Tip | The existing Delete tip is rewritten in the same turn. It names the normal card and the group. |
| Confirm | There is no confirm dialog. |
**semantic-key:** PK-617

## 3. pity

| Dependency | What this row does with it |
|---|---|
| Tab select | `maybeRestoreAetherisTab` calls `landOnNewest` after the select. |
| New-turn land | The viewport goes to the last pixel, then once more after layout. The newest prompt takes the caret. |
| Bind in flight | The land waits for that canvas paint. |
| Vault already open | The land runs on the canvas that is showing. |
| Manual visit | This path is the restore select only. |
**semantic-key:** PK-618

## 2. Fingerprint

`isAmwp` stays false → `setEnabledAt(1, false)` →. Vault/AMWP status text is never bound.

# — Vault default is disk persist, not ram

### Hole 2. Run waits until the vault and the connector are the same user

`runTarget` reads `openUsername()` and `focusedVaultUser()`. It writes only when both are non-empty and equal, and the focused connector is AMWP. Otherwise it sets the status `Vault is still opening.` and returns. It does not arm a record and it does not write.
**semantic-key:** PK-619

## 16. Applied 2026-10-20 08:33

`runTarget` writes only when `openUsername()` equals `focusedVaultUser()`. Until then the status is `Vault is still opening.` `armRun` stores the entry and starts the spinner. A second Run on that user replaces the cell id.
**semantic-key:** PK-620

## 12. Functional spine

| Anchor | Cite | Duty on this client |
|---|---|---|
| Wire | | This client honors the locked frames. The frame table stays on section 7. The payload cap is the one that section names. |
| One pipe, two conversations | Core section 6, the lines on two products, the OpenSSH raw PTY, and Casserole disconnected. | One login carries both conversations. Scope T stays the host terminal. Scope A is the canvas. The client sends the working set. The host keeps the vt100 conversation. A raw PTY with no client still works. Casserole without this client is still a host. |
| Execute | Constitution section 2.2, the diagram and rules 2, 3, and 8. Constitution section 7, the Stage 1 execute payload. | The client assembles the working set and pushes the full set on each execute. On reconnect the vault wins. The host replaces its Aetheris Roux window and returns the reply. The client stores that reply unless the cell is a burner. One execute is in flight for a cell. |
| Cell | Constitution section 8. | The fields the host understands are `cell_id`, parent, branch, `execution_target`, `context_state`, the knobs, `execution_status`, and ephemeral. There is no `@scope` field. |
| Four states | | The four states decide membership of the working set. The ghost dim is look, and goal 2 holds that dim. |
| Burner | Constitution section 8.2 and section 18. Core section 6, the three verbs. | A burner runs in memory, stores metadata with empty payloads, and stays off the cloud unless the allow-cloud-burner policy says otherwise. Burner, ghost, and delete stay three verbs. |
| Context assembly | Constitution section 9. Constitution section 7, prune frames. | Assembly runs on the client. The host receives the built set. The inspector shows that list before Run. A prune preview does not persist on the host. |
| Failure | Constitution section 17 and section 12.1. | The cell stays RUNNING until EOF, cancel, or error. A partial reply stays off INCLUDED until commit. A vault write does not wrap the model call. A crash of RUNNING becomes INTERRUPTED. |
| Engines | Constitution section 13. | `execution_target` is `provider/model`. The Stage 1 host engine is Ollama. Air-gap forbids a cloud target and keeps the stored target as written. The reply is Scope A data until EOF. Section 13 records the as-built host as one-shot `complete()`, which may be one chunk. This client accepts the frames the host sends. |
| Crosstalk | Constitution section 16. | Crosstalk is an operator action. |
| Security boundary | Constitution section 18, section 2.1, and section 7. | The vault lives with this client. The host does not open it. A cloud send has left the boundary. The vault is per user. |
| Vault mode | | Disk is the product default. RAM is testing-only. The law is the rule on this rank. Constitution section 12 still names the stores and the vault path. The frozen constitution stays as filed. |
| Later work | | Piping, branch, merge, and parallel groups wait for Stage 2. Tools wait for Stage 2 on channel `0x0004`. This client is not the MCP server. Until that channel exists, a cell is prompt-only. |
| Stage boundary | The stage map. | The stage map is the working-spec head and the stage boundary. This serial does not copy its rows. Stage 1 host behavior is the contract this client reuses. Stage 2 waits until the host has it, unless names that build. |

## 14. Vault engine

The Rust vault is Turso. It is the drop-in for the H2 vault on this client. The engine is embedded. There is no wire.
