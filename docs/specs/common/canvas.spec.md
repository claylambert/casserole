# Canvas and engine

[Method](method.spec.md). [Index](../common.spec.md). [Grok and the model stream](grok.spec.md).

# Ctrl-Shift-C copies the console highlight

Ctrl-Shift-C copies the console highlight. Ctrl-C stays the interrupt. An empty highlight leaves the clipboard as it is.
**semantic-key:** L40

- Audit slice A seats the copy chord.

- **State:** OPEN. This page is audit slice A. This plate has no sub-slice.

- **What it is:** Ctrl-Shift-C, while the terminal has focus, copies the console highlight to the system clipboard. The console writes nothing on that chord. The interrupt byte 0x03 stays unwritten. Ctrl-C without Shift stays the interrupt. The host still owns that interrupt. An empty highlight leaves the clipboard as it is and still writes nothing. A busy clipboard leaves the clipboard as it is and still writes nothing. A canvas field keeps its keys.

- **What it is not:** A change to menu Copy. A change to drag copy. A change to paste. Copy message. Ctrl-Alt-C. A Rust edit. An F-number. A new law. A status-line move.

## 1. The window

Ctrl-Shift-C copies the highlight when the terminal is focused. The clipboard receives that text. The console writes nothing, so the interrupt mark stays off the screen.

Ctrl-C without Shift is not consumed. The terminal still sends byte 0x03.

An empty highlight does not write the clipboard. A busy clipboard stays as it is. The console still writes nothing.

Menu Copy and a drag copy stay. Paste stays. Section 99 copy message and Ctrl-Alt-C stay open and are not this slice.

## 2. The turn

No side move on this plate.

## 4. Checkpoint 2026-10-31 08:31

The copy chord landed.

# The VT100 status line carries the facts

- Audit slice B seats the status line.

- **State:** OPEN. This page is audit slice B. This plate has no sub-slice.

- A change to the Aetheris strip. A token estimate. An SSH Mood Active box. A Rust edit. An F-number. A new law.

## 1. The window

The SSH tab wears one thin status line at the bottom.

The west word stays the link. Connecting stays Connecting. Connected shows Connected host:port. Disconnected stays Disconnected. A disconnect reason keeps the em dash.

The SSH mood gate is off on this window. The word mood active follows the link when that gate is on. The mood string stays off the strip.

The facts follow. The order is vault, wire, air-gap, then engine. The vault word is disk. A blank air-gap reads unset. An empty engine reads ollama. The wire word is the console mode.

The Aetheris tab keeps vault, AMWP or raw, air-gap, and engine. A canvas extra stays on that strip. The east name stays the user while the session is a connected AMWP user.

## 2. The turn

## 4. Checkpoint 2026-10-31 08:45

The status line landed.

Keeps the Aetheris strip.

# The VT100 token window counts the Roux chat

- Audit slice C seats the token window.

- **State:** OPEN. This page is audit slice C. This plate has no sub-slice.

- Section 98 names the count.

- **What it is:** The VT100 strip shows est. tokens for the focused user's Roux chat. Token Calc sits on the main menu before Help. A click opens the token window. The used side is the sum of the token counts Roux stored. A message with no stored count uses the heuristic on its text. Empty text is 1. Any other text is (length + 3) / 4. The canvas window stays on the canvas working set.

- A terminal-grid count. A host ask. A new channel. A new AMWP type. A Rust edit. A yaml write. An F-number. A new law.

## 1. The window

The count is the sum of the Roux chat for the focused user. A stored count is the count kept when that message was appended. A message with no stored count uses the heuristic on its text. An empty chat sums to 0. A user with no held lines is an empty chat. This plate does not read the terminal grid. This open does not ask the host. This plate does not open the Roux pack.

Token Calc sits on the menu bar after Tools and before Help. Help stays last. Show Thinking stays under Tools.

A click on the SSH plate opens the token window. The ring uses the Roux sum. The budget stays the context window the token window already shows. Used subtracted from that budget is the available approximation.

A click on the Aetheris plate opens the same window. The ring keeps the inspector estimate.

## 2. The turn

## 4. Checkpoint 2026-10-31 10:12

The token window landed.

Keeps the canvas estimate.

# Tools shows the thinking pane

- Audit slice D seats the tools menu.

- **State:** OPEN. This page is audit slice D. This plate has no sub-slice.

- **What it is:** Tools sits on the menu bar between File and Help. Show Thinking is the item under it. A click reveals the thinking pane of the plate in front.

- A token window. Token Calc. A move of the menu onto the tab row. A Rust edit. A yaml write. A log clear. An F-number. A new law.

## 1. The window

Tools sits between File and Help. Help stays last. Token Calc stays off this bar.

Show Thinking closes the menu and reveals the thinking pane of the plate in front.

On the Aetheris tab the canvas thinking pane opens. A closed pane rises. A folded pane opens so the text is visible. A pane that is already showing stays at its height. The log stays. The closed mark on the file stays. The VT100 strips stay as they are.

The menu stays on the menu bar.

## 2. The turn

## 4. Checkpoint 2026-10-31 09:21

The tools menu landed.

Keeps the menu bar.

> **SUPERSEDED.** Archaeology / Gemini ingest only. Do **not** implement from this file.
Four-state + ghost remain. AMWP **Scope T / Scope A** (wire channels) are unrelated and stay.

### **Executive Overview & Product Vision**

**Aetheris** is a high-velocity, sovereign desktop canvas and cognitive engine built to transform how human operators orchestrate artificial intelligence. It bridges the gap between raw, low-level systems engineering and high-level conversational AI while restoring total ownership of digital memory to the developer.

### **Executive Overview & Product Vision**

### **The Problem & Historical Gaps Solved**

Modern enterprise developers, architects, and researchers face three critical structural failures in standard AI tooling:

#### **1. Ephemeral Memory & Loss of Intellectual Property (The Storage Trap)**

* **The Problem:** Current web clients treat conversational history as disposable. You lose valuable code spikes, architectural decisions, and complex prompt chains across fragmented browser tabs. You cannot back up, query, export, or own your history offline.
* **The Aetheris Solution: Zero-Knowledge Local H2 Memory Vault.** Every turn, prompt, execution trace, and output payload is automatically persisted directly to your local hardware in a lightweight embedded H2 database. As long as you have disk space, your cognitive history is **yours into perpetuity**. You can search, index, branch, or cold-boot your entire workspace history offline?100% independent of cloud vendors.

#### **2. Privacy & Data Sovereignty Disconnect**

* **The Problem:** Developers are forced into an all-or-nothing trade-off between private local models (Ollama) and high-speed cloud providers (Groq/Gemini). There is no granular control over what sensitive data leaves the machine.
* **The Aetheris Solution: Per-Cell Sovereignty & Ephemeral ("Burner") Execution.** Aetheris lets you mix local offline models and cloud LPUs on the exact same canvas on a per-cell basis. Furthermore, sensitive cells (containing API keys, credentials, or proprietary code) can be flagged as **Ephemeral / Non-Persisted**, zeroing out memory buffers upon execution so private data never touches disk or external caches.

#### **3. The "Black Box" Context Trap & Token Bloat**

* **The Problem:** Standard chat interfaces pass all historical turns to the LLM automatically. Low-value debugging chatter, off-topic tangents, and hallucinatory outputs permanently clog the model?s context window?slowing down local models and inflating cloud costs.
* **The Aetheris Solution: Selective Context Pruning & Scoped Routing.** Visual, 3-state controls on every cell (`INCLUDED`, `EXCLUDED`, `INPUT ONLY`) alongside semantic `@scope` tagging allow operators to prune noise instantly. You can run dirty C++ debugging threads right next to high-level architecture planning without polluting the model's active working memory.

### **Target Use Cases**

* **Offline-First Sovereign Engineering:** Build, code, and orchestrate complex AI workflows completely air-gapped using local Ollama models and local MCP file tools?storing decades of operational knowledge locally in H2.
* **Hybrid Poly-Engine Workflows:** Prototype code locally on private hardware, sanitize the output, and forward clean payloads to ultra-fast cloud LPUs (like Groq) for documentation and optimization?all on a single canvas.
* **DevOps & Terminal-Integrated Workflows:** Monitor live server operations in a full-fledged SSH terminal on Tab A while dynamically orchestrating agentic models and local storage pipelines on Tab B over a single AMWP SSH pipe.

### **Final Scope & Vision Declaration**

> **The Aetheris Declaration**
> "Your thoughts, your code, and your cognitive history belong to you?not to a cloud provider's ephemeral web interface. Aetheris represents a fundamental paradigm shift: from passive, rented chat streams to active, sovereign cognitive orchestration.
> By uniting local H2 data persistence, scoped context routing, poly-engine targeting, and Spring AI tool hooks onto a high-velocity desktop canvas, Aetheris delivers a zero-compromise environment where intelligence is fast, context is controlled, and memory endures into perpetuity."

# Aetheris: High-Velocity Sovereign Desktop Canvas & Cognitive Engine

**Technical Design & System Architecture Specification**

## **1. Executive Overview & System Architecture**

### **1.1 System Architecture Diagram**

```
+-----------------------------------------------------------------------------------+
| AETHERIS CANVAS UI |
| +---------------------------------------------------------------------------+ |
| | Cell Execution & Context Manager | |
| | [ Cell 1: Local ] [ Cell 2: Hybrid ] [ Cell 3: Cloud LPU ] | |
| | (3-State Pruning: INCLUDED / EXCLUDED / INPUT ONLY | @scope tagging) | |
| +---------------------------------------------------------------------------+ |
+-----------------------------------------|-----------------------------------------+
 |
 Internal IPC
+-----------------------------------------v-----------------------------------------+
| AETHERIS CORE ENGINE |
| |
| +---------------------------+ +--------------------------+ +----------------+ |
| | Context Pruner & Pipeline | | Local H2 Storage Vault | | Spring AI | |
| | Routing Orchestrator | | (AES-256 / Zero-Knowledge| | Tool Hooks & | |
| | | | Lifetime Persistence) | | MCP Adapters | |
| +-------------|-------------+ +------------|-------------+ +-------|--------+ |
+----------------|-----------------------------|------------------------|-----------+
 | | |
 +-----------------------------+------------------------+
 AMWP Protocol Engine
 (Aetheris Multiplexed WebSocket / SSH Pipe)
 +---------------------------------+---------------------------------+
 | |
+------------v------------+ +------------v------------+
| LOCAL HARDWARE / MCP | | CLOUD ENDPOINTS |
| +-------------------+ | | +-------------------+ |
| | Ollama Models | | | | Groq / Gemini | |
| | Local File Tools | | | | High-Speed LPUs | |
| +-------------------+ | | +-------------------+ |
+-------------------------+ +-------------------------+

```

### **1.2 Core Architectural Principles**

* **Local-First Sovereignty:** All user interactions, code spikes, context states, and model outputs are stored directly on local hardware via an embedded H2 database vault.
* **Granular Context Control:** Prevents context bloat and reduces cloud API overhead by enforcing a 3-state cell selection mechanism (`INCLUDED`, `EXCLUDED`, `INPUT_ONLY`) alongside semantic `@scope` routing.
* **Ephemeral/Burner Execution Safety:** High-security cells zero out memory buffers upon completion, preventing private credentials, code base keys, or proprietary parameters from persisting to disk or remote log caches.
* **Hybrid Poly-Engine Orchestration:** Dynamic execution target switching allows offline, local models (via Ollama) and ultra-fast cloud inference engines (such as Groq LPUs or Gemini) to execute concurrently on the same workspace canvas.

## **2. Sovereign Memory Vault (H2 Database Layer)**

### **2.1 Schema Definition**

```sql
-- Workspace & Canvas Architecture
CREATE TABLE workspaces (
 workspace_id VARCHAR(36) PRIMARY KEY,
 name VARCHAR(255) NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE canvases (
 canvas_id VARCHAR(36) PRIMARY KEY,
 workspace_id VARCHAR(36) NOT NULL,
 title VARCHAR(255) NOT NULL,
 FOREIGN KEY (workspace_id) REFERENCES workspaces(workspace_id) ON DELETE CASCADE

-- Cell Execution & Context Control
CREATE TABLE cells (
 cell_id VARCHAR(36) PRIMARY KEY,
 canvas_id VARCHAR(36) NOT NULL,
 cell_order INT NOT NULL,
 execution_target VARCHAR(64) NOT NULL, -- e.g., 'ollama/llama3', 'cloud/groq-mixtral'
 context_state VARCHAR(16) CHECK (context_state IN ('INCLUDED', 'EXCLUDED', 'INPUT_ONLY')),
 is_ephemeral BOOLEAN DEFAULT FALSE,
 prompt_payload CLOB,
 response_payload CLOB,
 FOREIGN KEY (canvas_id) REFERENCES canvases(canvas_id) ON DELETE CASCADE

-- Cell Metadata & Semantic Tags
CREATE TABLE cell_tags (
 tag_id VARCHAR(36) PRIMARY KEY,
 cell_id VARCHAR(36) NOT NULL,
 scope_tag VARCHAR(128) NOT NULL, -- e.g., '@scope:architecture', '@scope:c-debug'
 FOREIGN KEY (cell_id) REFERENCES cells(cell_id) ON DELETE CASCADE

-- Execution & Audit Traces
CREATE TABLE execution_traces (
 trace_id VARCHAR(36) PRIMARY KEY,
 tokens_prompt INT NOT NULL,
 tokens_completion INT NOT NULL,
 latency_ms BIGINT NOT NULL,
 executed_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

```

### **2.2 Ephemeral Data Zeroing Pipeline**

When a cell is configured with `is_ephemeral = TRUE`:

1. The execution engine processes the input via target provider memory channels.
2. Output is rendered directly to the active canvas screen buffer.
3. The database layer interceptor substitutes memory payloads with NULL values prior to writing to storage:

$$\text{Cell}_{\text{db\_record}} \leftarrow \{ \text{prompt\_payload}: \text{NULL}, \text{response\_payload}: \text{NULL} \}$$

### **2.2 Ephemeral Data Zeroing Pipeline**

## **3. AMWP Binary Protocol & Multiplexing Architecture**

Aetheris utilizes the **Aetheris Multiplexed WebSocket / SSH Protocol (AMWP)** to encapsulate SSH terminal streams, LLM inference text frames, and local tool execution commands over a single multiplexed channel.

### **3.1 Packet Frame Structure**

| Field | Size | Description |
| --- | --- | --- |
| **Magic Byte** | 1 Byte | Protocol identifier (`0xAE`) |
| **Channel ID** | 2 Bytes | Stream identifier (`0x01` = Terminal, `0x02` = LLM Payload, `0x03` = Tool Execution) |
| **Frame Type** | 1 Byte | `0x00` (Init), `0x01` (Data), `0x02` (Prune Signal), `0x03` (EOF), `0x09` (KeepAlive) |
| **Flags** | 1 Byte | Bit 0: Ephemeral Flag, Bit 1: Compression (zstd), Bit 2-7: Reserved |
| **Payload Length** | 4 Bytes | Big-endian 32-bit unsigned integer stating payload size in bytes |
| **Payload Data** | Variable | Raw binary payload segment |

### **3.2 Multiplexing Logic & Channel Routing**

```
+--------------------------------------------------------------------+
| AMWP FRAME STRUCT |
| [ Magic: 0xAE ] [ Channel ID ] [ Type ] [ Flags ] [ Length ] [ Data ] |
+---------------------------------|----------------------------------+
 v
| AMWP DEMULTIPLEXER ROUTER |
 v v v
 [ Channel 0x01 ] [ Channel 0x02 ] [ Channel 0x03 ]
 Terminal SSH Stream LLM Frame Orchestrator Spring AI Tool Hooks
 (Interactive PTY) (Streaming Context Pipeline)(Local File/MCP Access)

```

## **4. Context Selection & Scoped Routing Engine**

### **4.1 3-State Context Logic Matrix**

| Context State | Sent to Model as Input Context? | Saved in Main Context Thread Output? | Appears in Visual Canvas Canvas? |
| --- | --- | --- | --- |
| **`INCLUDED`** | **Yes** | **Yes** | Yes |
| **`EXCLUDED`** | **No** | **No** | Yes (Visual view only) |
| **`INPUT_ONLY`** | **Yes** | **No** (Output dropped from future context) | Yes |

### **4.2 Scoped Context Assembly Algorithm**

Let $C = [c_1, c_2, \dots, c_n]$ be the ordered list of cells on a canvas.
Let $T$ be the set of active `@scope` tags specified for the target cell $c_{\text{target}}$.

$$\text{Context}(c_{\text{target}}) = \bigcup_{i=1}^{\text{target}-1} f(c_i)$$

Where the filtering function $f(c_i)$ is defined as:

$$f(c_i) = \begin{cases} c_i & \text{if } c_i.\text{state} \in \{\text{INCLUDED}, \text{INPUT\_ONLY}\} \land (c_i.\text{tags} \cap T \neq \emptyset \lor T = \emptyset) \\ \emptyset & \text{if } c_i.\text{state} = \text{EXCLUDED} \lor (c_i.\text{tags} \cap T = \emptyset \land T \neq \emptyset) \end{cases}$$

## **5. Poly-Engine Orchestration & Tool Integration**

### **5.1 Dynamic Provider Interface**

Aetheris abstracts provider execution via unified Spring AI adapters.

```java
public interface AetherisExecutionEngine {

 /**
 * Executes context-pruned cell prompts across local or cloud infrastructure.
 */
 Flux<ExecutionChunk> streamCellExecution(CellExecutionRequest request);

 * Inspects target engine capabilities (e.g., Tool Calling support, Context Window sizes).
 EngineCapabilities getCapabilities(String providerId);

```

### **5.2 Provider Capabilities Comparison**

| Feature | Local Ollama Engine | Groq Cloud LPU Engine | Gemini Cloud Engine |
| --- | --- | --- | --- |
| **Execution Path** | Air-Gapped Local Loop | Remote TLS Endpoint | Remote TLS Endpoint |
| **Latency Profile** | Variable (Hardware dependent) | Ultra-Low (< 20ms TTFT) | Low (~200ms TTFT) |
| **Data Sovereignty** | Total (Stays on device) | Ephemeral Session | Standard Cloud Session |
| **Tool/MCP Support** | Native Spring AI Hooks | Native Spring AI Hooks | Native Function Call |

## **6. Sovereign Desktop Canvas Features**

* **Dual-Tab Synchronized Canvas:** Tab A houses a low-overhead SSH Terminal via AMWP stream `0x01`; Tab B displays the interactive block-based cognitive canvas stream `0x02`.
* **Instant Context Pruning UI:** Toggle cell states with one click or apply keyboard shortcuts (`Cmd/Ctrl + Shift + I/E/P`) to dynamically swap execution inclusion without losing rendered canvas UI states.
* **Offline Fallback Automation:** Auto-routes requests to local Ollama endpoints when local network checks indicate an air-gapped environment.

# Aetheris Workspace: UI Semantics & Client-Server Integration Technical Specification

## **1. Core Architectural Layout & Swing Component Hierarchy**

The Aetheris UI is implemented using a pure, highly optimized Java Swing component hierarchy. Custom painting and double-buffering are applied to guarantee high-throughput, lag-free canvas rendering when receiving real-time streaming tokens.

### **1.1 Java Swing Component Structure**

```
javax.swing.JFrame (AetherisFrame)
??? javax.swing.JSplitPane (MainSplitPane)
 ??? javax.swing.JTabbedPane (WorkspaceTabbedPane)
 ? ??? AetherisTerminalPanel (AMWP Channel 0x01 - Terminal SSH PTY)
 ? ??? AetherisCanvasPanel (AMWP Channel 0x02 - Interactive Canvas)
 ? ??? javax.swing.JScrollPane (CanvasScrollPane)
 ? ??? AetherisCellContainerPanel (BoxLayout: Y_AXIS)
 ? ??? AetherisCellView (Cell 1)
 ? ??? AetherisCellView (Cell 2)
 ? ??? AetherisCellView (Cell n)
 ??? AetherisInspectorPanel (Context Topology & Scope Inspector)

```

### **1.2 Cell Visual States & Palette**

```
| [STATE: INCLUDED] [Scope: @architecture] [Target: ollama/llama3] | <- Cell Header Toolbar
| Prompt Input (JTextArea / RSyntaxTextArea) |
| > Analyze structural boundary between H2 DB and AMWP channel routing |
| Execution Response (JTextPane - Markdown / ANSI Stream Buffer) |
| The separation of concerns mandates that the H2 Storage Vault handles... |
| [Ephemeral: OFF] [Tokens: P:142 C:89] [Latency: 14ms] [EXECUTE (F5)]| <- Cell Action Bar

```

#### **Visual State Indicator Specs**

* **`INCLUDED` State:**
* **Border:** `2px solid #2E7D32` (Emerald Green).
* **Header Background:** `#1B5E20`.
* **Visual Cue:** Full opacity ($100\%$), prominent execution status badge.

* **`EXCLUDED` State:**
* **Border:** `1px dashed #757575` (Muted Slate).
* **Header Background:** `#212121`.
* **Visual Cue:** De-emphasized canvas opacity ($50\%$), prompt text dimmed (`#9E9E9E`).

* **`INPUT_ONLY` State:**
* **Border:** `2px solid #F57F17` (Amber / Warning Yellow).
* **Header Background:** `#F57F17` (Low alpha blend).
* **Visual Cue:** Output text pane collapsed or styled with strike-through context badge ("Output Omitted from Context").

* **`EPHEMERAL` (Burner) Marker:**
* **Badge:** Red flame icon / `#D32F2F` pill tag in the action bar.

## **2. Swing UI Semantics & Event Handling Model**

### **2.1 Cell Context-State Cycle Model**

Each `AetherisCellView` encapsulates a state machine managing its context propagation and selection:

```
 +-------------------+
 | INCLUDED |
 +---------+---------+
 / \
 Click / Shortcut / \ Click / Shortcut
 (Ctrl+Shift+E) / \ (Ctrl+Shift+P)
 v v
 +--------------+ +--------------+
 | | | |
 | EXCLUDED |<->| INPUT_ONLY |
 Click / Shortcut (Ctrl+Shift+I)

```

### **2.2 Key Bindings & Actions Mapping**

All key events are bound using the Swing `InputMap` and `ActionMap` framework at the `AetherisCanvasPanel` root to ensure hotkeys function reliably regardless of which child component currently holds focus.

```java
public class AetherisCellController {

 public void registerKeyBindings(JComponent rootComponent, AetherisCellView activeCell) {
 InputMap inputMap = rootComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
 ActionMap actionMap = rootComponent.getActionMap();

 // Context Switching Hotkeys
 bindAction(inputMap, actionMap, "ctrl shift I", "setIncluded", new SetContextStateAction(activeCell, ContextState.INCLUDED));
 bindAction(inputMap, actionMap, "ctrl shift E", "setExcluded", new SetContextStateAction(activeCell, ContextState.EXCLUDED));
 bindAction(inputMap, actionMap, "ctrl shift P", "setInputOnly", new SetContextStateAction(activeCell, ContextState.INPUT_ONLY));

 // Execution Hotkeys
 bindAction(inputMap, actionMap, "F5", "executeCell", new ExecuteCellAction(activeCell));
 bindAction(inputMap, actionMap, "ctrl ENTER", "executeCell", new ExecuteCellAction(activeCell));

 // Scope & Tagging Shortcuts
 bindAction(inputMap, actionMap, "ctrl T", "focusTagInput", new FocusTagInputAction(activeCell));

 private void bindAction(InputMap im, ActionMap am, String keyStroke, String actionKey, Action action) {
 im.put(KeyStroke.getKeyStroke(keyStroke), actionKey);
 am.put(actionKey, action);

```

## **3. Server-Side Protocol Integration: Casserole Server & AMWP Binding**

The Aetheris UI communicates directly with the **Casserole Server** backend infrastructure via AMWP binary frames. UI events trigger structured payload assembly, and backend responses stream directly into Swing `Document` models via thread-safe UI updates.

### **3.3 Event Sequence: Execution & Streaming Payload Parsing**

```
+------------------+ +-------------------------+ +------------------------+
| Swing UI Component| | AMWP Protocol Engine | | Casserole Server |
+--------+---------+ +------------+------------+ +-----------+------------+
 | User Presses F5 / Clicks Run | |
 |------------------------------->| |
 | | Encapsulate AMWP Frame (0x02) |
 | | (Context Pruned Payload) |
 | |--------------------------------->|
 | | | Runs Engine Pipeline /
 | | | Spring AI Adapter
 | | AMWP 0x02 Data Frame (Stream) |
 | |<---------------------------------|
 | Render Stream Chunk | |
 |<-------------------------------| |
 | (SwingUtilities.invokeLater) | |
 | | AMWP Frame Type 0x03 (EOF) |
 | Finalize Trace & Metrics UI | |

```

### **3.4 Swing Thread Safety & Streaming Document Integration**

To prevent Swing EDT (Event Dispatch Thread) lockups during high-frequency server streaming over AMWP binary sockets, incoming frame buffers are funneled into an asynchronous queue and flushed to the `JTextPane` document model using `SwingUtilities.invokeLater` batching.

```java
public class AMWPStreamConsumer implements AMWPFrameListener {

 private final AetherisCellView cellView;
 private final StringBuilder chunkBuffer = new StringBuilder();
 private final Timer flushTimer;

 public AMWPStreamConsumer(AetherisCellView cellView) {
 this.cellView = cellView;
 // Batch UI refreshes every 30ms to maintain 30+ FPS during heavy streaming
 this.flushTimer = new Timer(30, e -> flushToUI());
 this.flushTimer.start;

 @Override
 public void onFrameReceived(AMWPFrame frame) {
 if (frame.getChannelId() == 0x02 && frame.getType() == 0x01) { // LLM Payload Stream
 synchronized (chunkBuffer) {
 chunkBuffer.append(new String(frame.getPayload(), StandardCharsets.UTF_8));
 } else if (frame.getType() == 0x03) { // EOF
 flushTimer.stop;
 flushToUI;
 SwingUtilities.invokeLater(() -> cellView.setExecutionState(ExecutionState.IDLE));

 private void flushToUI {
 String textToAppend;
 if (chunkBuffer.length == 0) return;
 textToAppend = chunkBuffer.toString;
 chunkBuffer.setLength(0);
 SwingUtilities.invokeLater( -> {
 StyledDocument doc = cellView.getResponsePane().getStyledDocument();
 try {
 doc.insertString(doc.getLength(), textToAppend, null);
 } catch (BadLocationException e) {
 e.printStackTrace;
 });

```

## **4. H2 Data Synchronization & UI State Persistence**

### **4.1 Real-Time UI Persistence Pipeline**

1. **Cell Context State Toggle (`INCLUDED` / `EXCLUDED` / `INPUT_ONLY`):**
* Fires an update request to Casserole DB endpoint: `UPDATE cells SET context_state = ? WHERE cell_id = ?`.
* Triggers an immediate UI repaint of the cell's outer border and header banner.

2. **Ephemeral Toggle Execution:**
* Flags `is_ephemeral = TRUE` in the local UI state.
* Casserole server interceptor ensures `prompt_payload` and `response_payload` are replaced with `NULL` in the database write pipeline upon execution completion.

3. **Scope Tag Input (`@scope:tagname`):**
* Swing `DocumentListener` on the scope field updates `cell_tags` table references on focus loss or Enter key submission.

# Aetheris Core Engine Execution Workflows & Pseudocode Specifications

## **1. Architectural Scope & Context Assembly Engine**

### **1.1 The "Why" Behind Scoped Mechanics**

Standard chat interfaces inject an entire conversation history into every request, leading to context bloat, elevated cloud API costs, degraded local model performance, and accidental prompt poisoning.

Aetheris resolves this via a deterministic, selective context assembly algorithm:

* **State Filtering (`INCLUDED`, `EXCLUDED`, `INPUT_ONLY`):** Keeps inactive or dirty debugging sessions visually available on the canvas while excluding them from the active LLM context window.
* **Semantic `@scope` Tagging:** Allows targeted execution threads (e.g., `@scope:backend`, `@scope:c-debug`) to pull only relevant context cells, running isolated parallel tasks on a single canvas.

### **1.2 Context Assembly Workflow Pseudocode**

```python

# Function: AssembleScopedContext

# Purpose: Prepares a pruned, scope-restricted array of messages for model execution.

FUNCTION AssembleScopedContext(canvas_id: String, target_cell_id: String) -> List<Message>:
 canvas = Database.getCanvas(canvas_id)
 target_cell = canvas.getCell(target_cell_id)

 # 1. Resolve active scopes requested by target cell
 target_scopes = ExtractScopeTags(target_cell.prompt_text) # e.g., ["@scope:architecture", "@scope:db"]

 assembled_messages = []

 # 2. Iterate sequentially over cells prior to target_cell
 FOR cell IN canvas.getCellsUpTo(target_cell_id):

 # Rule A: HARD EXCLUSION - Skip excluded cells immediately
 IF cell.context_state == "EXCLUDED":
 CONTINUE

 # Rule B: SCOPE MATCHING LOGIC
 # If target defines scopes, cell must share at least one scope tag or be globally scoped
 IF target_scopes IS NOT EMPTY:
 cell_scopes = cell.getScopeTags
 IF cell_scopes IS NOT EMPTY AND INTERSECTION(target_scopes, cell_scopes) IS EMPTY:
 CONTINUE # Skip cell due to scope mismatch

 # Rule C: INPUT_ONLY HANDLING
 # Include prompt; omit response from historical execution context
 IF cell.context_state == "INPUT_ONLY":
 assembled_messages.append(Message(role="user", content=cell.prompt_text))

 # Rule D: STANDARD INCLUDED CELL
 IF cell.context_state == "INCLUDED":
 IF cell.response_text IS NOT NULL:
 assembled_messages.append(Message(role="assistant", content=cell.response_text))

 # 3. Append target cell's own prompt payload as final turn
 assembled_messages.append(Message(role="user", content=target_cell.prompt_text))

 RETURN assembled_messages
END FUNCTION

```

## **2. Poly-Engine Targeting & Provider Selection Protocol**

### **2.1 The "Why" Behind Dynamic Poly-Engine Routing**

Different tasks demand different compute models:

* **Air-Gapped Privacy / Heavy Iteration:** Local Ollama engines process private code spikes without data leaving local hardware.
* **Complex Architectural Optimization:** Low-latency Cloud LPUs (Groq) or high-context models (Gemini) deliver sub-second generation speeds for non-sensitive data.

The Routing Engine dynamically resolves provider configurations, enforces fallback policies when offline, and ensures security settings are strictly applied.

### **2.2 Poly-Engine Execution Pseudocode**

```python

# Function: ResolveAndExecuteEngine

# Purpose: Dynamically routes execution requests to local or cloud target providers.

FUNCTION ResolveAndExecuteEngine(cell: Cell, payload: List<Message>) -> Stream<Chunk>:
 target_provider = cell.execution_target # e.g., "ollama/llama3", "groq/mixtral", "gemini/flash"

 # 1. Air-Gap & Connectivity Verification
 IF NetworkManager.isOffline() AND target_provider.isCloudProvider():
 Logger.warn("Cloud target requested while offline. Triggering local fallback.")
 target_provider = Configuration.getLocalFallbackTarget() # Fallback to "ollama/llama3"

 # 2. Ephemeral / Burner Buffer Initialization
 IF cell.is_ephemeral == TRUE:
 MemoryManager.flagBufferAsSensitive(payload)

 # 3. Provider Engine Adapter Resolution
 engine_adapter = ProviderRegistry.getAdapter(target_provider)

 # 4. Stream Context Payload to Engine Adapter via AMWP Stream Channel 0x02
 stream_response = engine_adapter.executeStream(
 messages=payload,
 temperature=cell.temperature,
 max_tokens=cell.max_tokens
 )

 RETURN stream_response

```

## **3. Tool Call Pipeline Mechanics (Pre/Post Execution)**

### **3.1 The "Why" Behind Spring AI Tool Hooks & MCP Adapters**

To execute local shell commands, read local files, or inspect system state through Model Context Protocol (MCP) servers, LLMs require a multi-pass tool call pipeline:

* **Pre-Execution Check:** Intercepts LLM tool invocations, validates safe permissions, and executes tools locally.
* **Post-Execution Loop:** Feeds tool execution results back into the context window for a secondary completion pass before final UI rendering.

### **3.2 Tool Call Interception Pseudocode**

```python

# Function: OrchestrateToolCallPipeline

# Purpose: Manages tool calling, authorization, execution, and loop completion.

FUNCTION OrchestrateToolCallPipeline(cell: Cell, initial_payload: List<Message>) -> Stream<Chunk>:
 current_messages = DeepCopy(initial_payload)
 tool_loop_active = TRUE
 max_tool_depth = 5
 current_depth = 0

 WHILE tool_loop_active AND current_depth < max_tool_depth:
 current_depth += 1

 # Step A: Request Completion from Target Engine
 response_stream = ResolveAndExecuteEngine(cell, current_messages)
 accumulated_response = ResponseBuffer

 FOR CHUNK in response_stream:
 accumulated_response.append(CHUNK)
 # Yield streaming tokens directly to Swing EDT via AMWP Buffer
 AMWP Demux.sendChunk(channel=0x02, data=CHUNK)

 # Step B: Inspect Response for Tool Invocations
 tool_calls = ToolParser.extractToolCalls(accumulated_response.toString())

 IF tool_calls IS EMPTY:
 # No tool invocation required; finalize response pipeline
 tool_loop_active = FALSE
 BREAK

 # Step C: Pre-Tool Authorization & Execution Phase
 current_messages.append(Message(role="assistant", tool_calls=tool_calls))

 FOR tool_call IN tool_calls:
 # 1. Security Assertion Checklist
 IF NOT ToolSecurityPolicy.isAuthorized(tool_call):
 tool_result = ErrorPayload("Execution Denied: Security Violation")
 ELSE:
 # 2. Pre-Execution Hook: Execute local tool via Spring AI / MCP Interface
 TRY:
 tool_result = LocalMCPExecutor.execute(
 tool_name=tool_call.name,
 args=tool_call.arguments
 CATCH ToolExecutionException e:
 tool_result = ErrorPayload(e.getMessage())

 # 3. Post-Execution Hook: Append Tool Output to Message Context
 current_messages.append(Message(
 role="tool",
 tool_call_id=tool_call.id,
 content=tool_result.toString
 ))

 RETURN accumulated_response

```

## **4. Persistent Storage Vault & Zero-Knowledge Memory Lifecycle**

### **4.1 The "Why" Behind Ephemeral Zeroing & H2 Persistence**

* **Zero-Knowledge Security:** When processing sensitive credentials or private key data (`is_ephemeral = TRUE`), memory buffers must be explicitly scrubbed from local disk, RAM, and log trails.

### **4.2 Persistence & Buffer Zeroing Pseudocode**

```python

# Function: FinalizeAndPersistCellExecution

# Purpose: Commits cell output to local H2 storage or zeroes ephemeral buffers.

FUNCTION FinalizeAndPersistCellExecution(cell: Cell, prompt: String, response: String, execution_trace: Trace):

 # 1. Ephemeral Buffer Zeroing Check
 # Zero-out payload fields prior to storage write
 prompt_payload_to_store = NULL
 response_payload_to_store = NULL

 # Perform explicit memory wipe on in-memory byte arrays
 MemoryManager.zeroByteArray(prompt.getBytes())
 MemoryManager.zeroByteArray(response.getBytes())

 Logger.info("Ephemeral cell executed. Memory buffers successfully zeroed out.")
 # Standard Sovereign Persistence
 prompt_payload_to_store = prompt
 response_payload_to_store = response

 # 2. Database Synchronization Transaction (H2 Storage Layer)
 Database.transaction:
 # Update Cell Entity
 Database.executeUpdate(
 """UPDATE cells
 SET prompt_payload = ?, response_payload = ?, updated_at = CURRENT_TIMESTAMP
 WHERE cell_id = ?""",
 prompt_payload_to_store, response_payload_to_store, cell.id

 # Insert Metrics Audit Trace
 Database.executeInsert(
 """INSERT INTO execution_traces
 (trace_id, cell_id, tokens_prompt, tokens_completion, latency_ms)
 VALUES (?, ?, ?, ?, ?)""",
 UUID.generate(), cell.id, execution_trace.promptTokens,
 execution_trace.completionTokens, execution_trace.latencyMs

 # 3. Swing UI Event Dispatching
 SwingUtilities.invokeLater(LAMBDA:
 CellViewManager.updateExecutionStatusUI(cell.id, Status.COMPLETED, execution_trace)

```

## **5. Complete End-to-End Dynamic Workflow Execution Map**

```
 [User Presses F5 / Ctrl+Enter]
 1. Scope Assembly Engine
 - Collect active cells
 - Evaluate INCLUDED/EXCLUDED/INPUT_ONLY
 - Match @scope tags
 2. Poly-Engine Router
 - Check network connectivity (Cloud vs Local Ollama)
 - Bind target engine adapter
 3. AMWP Channel Dispatch (Channel 0x02)
 - Open binary socket stream
 4. Tool Interception Pipeline
 - Check response for tool calls
 - Execute local MCP tools if invoked
 - Re-inject result into prompt loop
 5. Asynchronous Swing EDT Flush
 - Batch streaming tokens to JTextPane
 6. Final Memory Lifecycle Audit
 - If Ephemeral: Zero out RAM buffers & write NULLs to H2
 - If Standard: Persist prompt/response to local H2 Vault

```

# Aetheris Core Engine: Cell-to-Cell Data Flow & Pipeline Orchestration Specification

## **1. Architectural Concept & Core Mechanisms**

Cell-to-cell orchestration in Aetheris enables dynamic, modular pipelines across the desktop canvas. Rather than relying solely on vertical top-to-bottom execution, operators can explicitly chain outputs from upstream cells into downstream target cells.

### **1.1 Push Transfer Modes**

* **Text Push (Raw Payload Ingestion):** Feeds the evaluated string or structured text response of a source cell directly into the prompt payload of a destination cell as literal text or context.
* **Variable Push (Symbolic Reference Injection):** Binds the output of a source cell to a dynamic scope variable (e.g., `$cell[1].output` or `{{cell_2.response}}`), permitting dynamic late-binding evaluation at execution time.

## **2. End-to-End Workflow Mechanics**

```
| SOURCE CELL (Cell 01) |
| Prompt: "Write an assembly logic parser for radix math." |
| Status: COMPLETED | Target: local/ollama |
 [ Push Trigger: Text or Variable ]
| FORWARDING INTERCEPTOR & PIPELINE |
| - Validates source status (COMPLETED) |
| - Applies payload sanitization & ephemeral checks |
| - Binds static string OR dynamic dynamic variable reference |
| TARGET CELL (Cell 02) |
| Prompt: "Optimize the following output for C++20: {{cell_1.output}}" |
| Target: cloud/groq-mixtral |

```

## **3. Execution Workflows & Pseudocode Specifications**

### **3.1 Response Forwarding & Push Option Interception**

```python

# Function: OrchestrateCellForwarding

# Purpose: Manages user requests for cell-to-cell data transfer via Text Push or Variable Push.

FUNCTION OrchestrateCellForwarding(source_cell_id: String, target_cell_id: String, push_mode: Enum):
 source_cell = Database.getCell(source_cell_id)
 target_cell = Database.getCell(target_cell_id)

 # 1. Pipeline Validation Assertion
 IF source_cell.status != "COMPLETED" OR source_cell.response_payload IS NULL:
 RaiseError("Forwarding Failed: Source cell output is uninitialized or pending execution.")

 # 2. Ephemeral Security Interception
 IF source_cell.is_ephemeral == TRUE:
 RaiseError("Security Assertion Failed: Ephemeral (Burner) cells cannot forward outputs.")

 # 3. Process Transfer Mode
 IF push_mode == PushMode.TEXT_PUSH:
 # Append or replace text directly in target input editor
 formatted_payload = FormatAsTextContext(source_cell_id, source_cell.response_payload)
 target_cell.prompt_text = AppendToPrompt(target_cell.prompt_text, formatted_payload)

 ELSE IF push_mode == PushMode.VARIABLE_PUSH:
 # Inject dynamic symbolic variable token
 var_token = String.format("{{cell[%s].output}}", source_cell.order_index)
 target_cell.prompt_text = InjectVariableToken(target_cell.prompt_text, var_token)

 # Register pipeline dependency in target metadata
 target_cell.addDependency(source_cell_id)

 # 4. Synchronize Swing UI & Database
 Database.updateCellPrompt(target_cell.id, target_cell.prompt_text)
 CellViewManager.refreshCellPromptView(target_cell.id)

```

### **3.2 Symbolic Variable Binding & Late-Binding Engine**

```python

# Function: EvaluateCellVariables

# Purpose: Resolves dynamic variable references prior to scope context assembly and model dispatch.

FUNCTION EvaluateCellVariables(target_cell: Cell) -> String:
 raw_prompt = target_cell.prompt_text

 # Identify variable syntax patterns (e.g., {{cell[N].output}} or $cell[N].output)
 variable_tokens = VariableRegex.findAll(raw_prompt)

 IF variable_tokens IS EMPTY:
 RETURN raw_prompt

 resolved_prompt = raw_prompt

 FOR token IN variable_tokens:
 referenced_cell_index = token.extractIndex()
 source_cell = Database.getCellByIndex(target_cell.canvas_id, referenced_cell_index)

 # Verify source cell execution integrity
 IF source_cell IS NULL OR source_cell.response_payload IS NULL:
 RaiseError(String.format("Variable Binding Error: Unresolved cell reference [%s]", token))

 # Perform substitution
 resolved_prompt = resolved_prompt.replace(token.raw_string, source_cell.response_payload)

 RETURN resolved_prompt

```

### **3.3 Multi-Cell Automated Pipeline Execution (Cascading Chains)**

```python

# Function: ExecuteCellPipelineChain

# Purpose: Executes a sequence of linked cells, auto-forwarding outputs downstream.

FUNCTION ExecuteCellPipelineChain(start_cell_id: String):
 execution_queue = PipelineResolver.buildDependencyTree(start_cell_id)

 FOR cell IN execution_queue:
 # Step A: Evaluate variables from upstream source dependencies
 resolved_prompt = EvaluateCellVariables(cell)

 # Step B: Assemble scoped context
 scoped_context = AssembleScopedContext(cell.canvas_id, cell.id)

 # Override target cell prompt with fully evaluated variable payload
 scoped_context.updateTargetTurn(resolved_prompt)

 # Step C: Dispatch to designated provider engine (Local/Cloud)
 response_stream = ResolveAndExecuteEngine(cell, scoped_context)

 # Step D: Process tool calling and stream to Swing EDT
 final_response = OrchestrateToolCallPipeline(cell, scoped_context)

 # Step E: Commit state & persist output
 FinalizeAndPersistCellExecution(cell, resolved_prompt, final_response.toString(), final_response.getTrace())

 # If pipeline encounters error, halt downstream execution
 IF cell.hasExecutionError:
 Logger.error("Pipeline chain halted due to execution failure at cell: " + cell.id)

```

## **4. Swing Component Interaction & User Actions**

### **4.1 Interaction Mapping**

| User Action | Trigger Mechanism | Target Operation |
| --- | --- | --- |
| **Push Text to Next Cell** | Action Bar Button / `Ctrl+Shift+T` | Instantly creates or appends source output to downstream cell prompt. |
| **Inject Variable Reference** | Context Menu / `Alt+V` | Inserts `{{cell[N].output}}` token into destination cell input pane. |
| **Execute Chain Pipeline** | Shift + Click `Run` / `Ctrl+Shift+F5` | Triggers sequential cascading execution starting from active cell down through all dependent nodes. |

# The Architectural Philosophy & Paradigm of Aetheris

## **1. The Nature of the Aetheris Workspace**

Aetheris is not a traditional chat app or an ephemeral web wrapper; it is a **sovereign desktop canvas and cognitive engine**. It reimagines human-AI orchestration as an active, local-first operational environment.

At its core, Aetheris shifts the paradigm of interacting with artificial intelligence:

* **From Disposable Streams to Lifetime Memory:** Instead of locking conversation histories inside vendor cloud silos where chats disappear or search fails, Aetheris treats every code spike, architectural decision, and execution trace as a locally owned, immutable digital asset.
* **From Linear Chat Streams to Modular Canvases:** Rather than forcing developers down a rigid, linear timeline, Aetheris provides a block-based, multi-cell workspace. Each cell acts as an independent execution node capable of targeting different compute engines, applying isolated scope boundaries, and piping inputs/outputs horizontally or vertically.

## **1. The Nature of the Aetheris Workspace**

## **2. Design Rationale & Fundamental Problems Solved**

Modern enterprise developers, architects, and researchers face three core structural failures in standard AI web interfaces:

### **1. Ephemeral Memory & Intellectual Property Lock-In**

* **The Problem:** Cloud providers treat chat history as disposable. Complex prompt chains, debugging sessions, and architectural blueprints are trapped behind fragile web sessions. Developers cannot easily export, query, version-control, or audit their historical context offline.

### **2. The Data Sovereignty & Privacy Dilemma**

* **The Problem:** Operators are forced into an all-or-nothing trade-off between private, resource-constrained local models (Ollama) and high-speed, hyper-capable cloud LPUs (Groq/Gemini). Standard tools offer no granular way to keep sensitive API keys, system secrets, or proprietary code offline while sending non-sensitive documentation tasks to the cloud.
* **The Solution:** **Per-Cell Ephemeral Execution & Poly-Engine Targeting.** Aetheris allows local offline models and cloud LPUs to execute side-by-side on the exact same canvas. Sensitive cells can be flagged as **Ephemeral / Non-Persisted ("Burner")**, executing in volatile memory buffers that are instantly zeroed out upon completion�ensuring private data never touches local disk storage or external caches.

### **3. Context Window Bloat & "Black Box" Token Poisoning**

* **The Problem:** Standard chat platforms automatically append the entire linear history to every new prompt turn. Low-value terminal logs, off-topic tangents, hallucinated code iterations, and dirty debugging threads clog the model�s context window. This inflates API costs, degrades output quality, and slows down inference times.
* **The Solution:** **Selective Context Pruning & Semantic `@scope` Routing.** Aetheris breaks the linear history chain by allowing operators to explicitly control which cells are visible to the LLM during any given execution pass.

## **3. The Canvas Metaphor: Bridging Selective Conversational State to the Agent Server**

The central UI innovation of Aetheris is its **Cell-Based Canvas Metaphor**, which transforms context management from a hidden black box into an explicit, visual visual canvas control system.

```
+------------------------------------------------------------------------------------+
| VISUAL CANVAS STATE |
| |
| [ Cell 01: INCLUDED ] ===> Sent as active context to LLM Engine |
| [ Cell 02: EXCLUDED ] ===> Retained visually on Canvas; Omitted from LLM Context|
| [ Cell 03: INPUT_ONLY ] ===> Prompt sent as input; Response dropped from history|
| [ Cell 04: @scope:db ] ===> Selectively routed based on semantic matching tags |

```

### **How the UI Semantics Empower Selective State Control**

1. **Direct Visual Feedback via 3-State Controls:**
Instead of deleting old chat turns to save context space, operators can visually toggle any cell's inclusion state:
* **`INCLUDED` (Active Context):** Evaluated and passed downstream as standard conversational history.
* **`EXCLUDED` (Visual-Only Scratchpad):** Remains visible on the developer's canvas for reference, but is completely filtered out of the binary AMWP payload sent to the agent server. Dirty debugging threads or large raw data dumps can sit directly next to high-level architectural work without polluting model context.
* **`INPUT_ONLY` (One-Way Context):** Passes the prompt to the model, but drops the generated output from subsequent conversational passes�preventing verbose LLM responses from bloating future turns.

2. **Semantic `@scope` Tagging:**
Operators can attach tags (e.g., `@scope:architecture`, `@scope:c-debug`, `@scope:db`) to individual cells. When executing a target cell with a matching scope tag, the engine dynamically filters the canvas hierarchy�assembling a context payload composed *only* of cells sharing that semantic tag.
3. **Explicit Data Piping (Push Mechanics):**
Rather than relying on the LLM to remember an output generated ten turns ago, the canvas UI provides explicit **Text Push** and **Variable Push (`{{cell[N].output}}`)** controls. Operators can explicitly route clean outputs from upstream local models into downstream cloud optimization tasks.

By bringing low-level context assembly controls directly to the UI layer, Aetheris bridges the gap between raw systems engineering and high-level conversational AI�giving developers deterministic control over model memory, data privacy, and agent execution.

# Aetheris Workspace: The Cell-Based Notebook Architecture for Conversational AI

## **1. Executive Paradigm: The Notebook Metaphor for AI Orchestration**

Aetheris reimagines the traditional conversational AI interface by abandoning the linear, single-stream chat window in favor of a **block-based, dynamic Notebook Canvas**.

In conventional chat interfaces (like web-based ChatGPT or Claude), interactions are structured as a rigid, monolithic thread. Every turn is appended to a single continuous log, forcing the user into a rigid timeline where past mistakes, tangents, and verbose debugging logs permanently contaminate future context.

Aetheris adapts the interactive notebook metaphor�pioneered by environments like Jupyter, Mathematica, and org-mode�to LLM prompt engineering, cognitive modeling, and multi-engine orchestration. Each interaction exists inside a discrete, controllable **Cell Node** on a flexible desktop workspace.

```
| AETHERIS NOTEBOOK CANVAS |
| |
| +-----------------------------------------------------------------------------+ |
| | [CELL 01] Context: INCLUDED | Target: local/ollama-llama3 | |
| | Prompt: "Draft C++ bitwise mask structures for high-speed packet parsing." | |
| +-----------------------------------------------------------------------------+ |
| | |
| (Text Push / Var) |
| v |
| +-----------------------------------------------------------------------------+ |
| | [CELL 02] Context: EXCLUDED | Target: local/ollama-llama3 | |
| | Prompt: "Test output against bad bitmask inputs and print raw error log." | |
| +-----------------------------------------------------------------------------+ |
| |
| +-----------------------------------------------------------------------------+ |
| | [CELL 03] Context: INPUT_ONLY | Target: cloud/groq-mixtral | |
| | Prompt: "Optimize bitwise mask structures from Cell 01 for C++20 standard." | |
| +-----------------------------------------------------------------------------+ |

```

## **2. Architectural Mechanics of Cell-Based Conversational Design**

### **2.1 Discrete Execution Units (The Cell Structure)**

Rather than sending an entire chat window as a single blob of text, Aetheris models every conversational step as an isolated, self-contained unit. A cell encapsulates:

* **Input Buffer (Prompt Editor):** Rich code/text editor (e.g., Swing-based syntax view) supporting markdown, variable expansion tokens, and semantic scope annotations.
* **Output Stream Buffer:** Real-time streaming output container that captures text tokens, code outputs, execution metrics (latency, token counts), and tool call traces.
* **Execution Engine Binding:** Per-cell configuration targeting distinct inference backends (e.g., local Ollama instance vs. low-latency cloud Groq LPU vs. Gemini).
* **Context State Flag:** Deterministic 3-state setting (`INCLUDED`, `EXCLUDED`, `INPUT_ONLY`) determining how the cell participates in context assembly.
* **Privacy & Memory Flag:** Ephemeral / Burner toggle (`is_ephemeral`) controlling whether payloads write to the local H2 storage vault or zero out in memory upon execution.

### **2.2 The 3-State Context Control Model**

The core innovation of the notebook approach in Aetheris is giving operators fine-grained, cell-by-cell authority over what enters the LLM's active working memory.

```
 +--------------------------+
 | CELL CONTEXT STATES |
 +-------------+------------+
 +-----------------------+-----------------------+
 +--------------+ +--------------+ +--------------+
 | INCLUDED | | EXCLUDED | | INPUT_ONLY |
 | Passed as | | Visually | | Prompt passed|
 | full context | | present on | | to engine; |
 | to model for | | canvas, but | | output dropped|
 | standard turn| | omitted from | | from future |
 | progression. | | context payload| | turn context.|

```

1. **`INCLUDED` (Standard Sequence):** Functions like a traditional notebook cell. The prompt and its output form part of the visible history passed to downstream cells.
2. **`EXCLUDED` (Visual Scratchpad):** The cell remains fully rendered on the visual GUI canvas for reference, editing, or developer notes, but is completely filtered out of the binary protocol payload sent to the LLM.
* *Use Case:* Storing dirty compile logs, experimental scratchpad thoughts, or large raw data dumps directly alongside code without wasting model tokens or polluting context.

3. **`INPUT_ONLY` (One-Way Instruction):** The cell's prompt is included during context assembly, but its generated response is omitted from future execution turns.
* *Use Case:* Running one-off code formatting, inline validation, or intermediate analysis steps without bloating downstream context windows with long assistant outputs.

### **2.3 Dynamic Scope Routing (`@scope` Mechanics)**

Unlike traditional linear notebooks (like Jupyter) that process state sequentially from top to bottom, Aetheris allows non-linear semantic branching via `@scope` tagging.

* Operators attach scope tags (e.g., `@scope:backend`, `@scope:parser`, `@scope:h2-db`) to individual cells.
* When executing a target cell tagged with `@scope:parser`, the Aetheris context assembler scans upstream cells and constructs a dynamic payload composed **only** of cells sharing the `@scope:parser` tag (or untagged global cells).
* **Benefit:** You can run parallel architectural threads�such as frontend layout design and backend logic optimization�on a single visual canvas without cross-thread context contamination.

## **3. Data Flow & Piping: Text Push vs. Variable Push**

The notebook design allows explicit inter-cell data routing, giving operators manual and programmatically controlled options for passing outputs between cells.

### **3.1 Text Push (Literal Injection)**

* **Mechanism:** Takes the static text response from a completed source cell and appends or injects it directly into a downstream target cell's prompt input area.
* **Use Case:** Preparing a clean prompt payload to be reviewed and modified by the human operator before executing a second model turn.

### **3.2 Variable Push (Symbolic Late-Binding)**

* **Mechanism:** Injects dynamic, evaluated variable tokens (such as `{{cell[1].output}}` or `$cell[2].response`) into the destination prompt.
* **Use Case:** Building automated execution pipelines. When Cell 01 finishes updating, executing Cell 02 automatically re-evaluates `{{cell[1].output}}` at runtime, supporting multi-step cascading workflows across different LLM targets.

## **4. Why the Notebook Metaphor Outperforms Traditional Chat**

| Operational Metric | Standard Web Chat (ChatGPT / Claude) | Aetheris Cell-Based Notebook Canvas |
| --- | --- | --- |
| **Context Assembly** | Monolithic linear log (all turns included automatically). | Granular 3-state pruning (`INCLUDED`, `EXCLUDED`, `INPUT_ONLY`). |
| **Context Branching** | Fragile tree branches; difficult to compare or merge paths. | Parallel semantic `@scope` tags on a single unified visual layout. |
| **Token Efficiency** | Rapid token bloat from historical chatter and logs. | High token efficiency; non-essential cells are marked `EXCLUDED`. |
| **Multi-Engine Target** | Restricted to the single cloud model of the active session. | Mixed poly-engine execution (Local Ollama + Cloud LPUs per cell). |
| **Data Persistence** | Cloud-dependent, ephemeral session history. | Sovereign local embedded H2 Vault with zero-knowledge burner cells. |

# Aetheris Core Engine: Branch Forking & Parallel Path UI Technical Specification

## **1. Executive Overview & Paradigm**

In traditional LLM web UI trees, branching is destructive and linear: picking an earlier turn to "edit and retry" hides or discards the alternative history path, forcing developers to navigate back and forth through hidden step controls.

Aetheris introduces **Branch Forking & Parallel Path Canvas Execution**. Rather than hiding alternative execution paths, Aetheris renders parallel execution threads side-by-side on a multi-column visual canvas. Operators can split a single conversation tree into parallel branches, target distinct models simultaneously (e.g., local Ollama vs. Groq LPU vs. Gemini), pass alternate prompt parameters, and visually compare or merge execution outcomes in real time.

```
 +-----------------------+
 | ROOT CELL 01 |
 | (Shared Architecture) |
 +-----------+-----------+
 [ FORK / SPLIT ]
 +------------------------+------------------------+
 +-----------------------+ +-----------------------+
 | BRANCH A (Cell 02A) | | BRANCH B (Cell 02B) |
 | Target: local/ollama| | Target: cloud/groq |
 | Prompt: Low-level C | | Prompt: Modern C++ |
 +-----------+-----------+ +-----------+-----------+
 [ MERGE CELL 03 ]
 | SYNTHESIS CELL |
 | (Compare & Consolidate|

```

## **2. Target Use Cases & Technical Value**

* **A/B Model Comparison Spikes:** Fork a cell into three parallel execution branches�running the exact same context payload against a local Ollama model, a ultra-fast Groq LPU instance, and a cloud Gemini model�to directly evaluate output precision, latency, and token efficiency.
* **Algorithmic Strategy Exploration:** Test alternate algorithmic implementations concurrently (e.g., Branch A: bitwise shift mask vs. Branch B: lookup table optimization) without losing the original root context or polluting downstream nodes.
* **Poly-Engine Hybrid Sanitization:** Fork a private code branch locally on an offline Ollama model to generate sanitized abstractions, then pass the sanitized fork output to a high-throughput cloud engine for documentation generation.
* **Parallel Tool Execution Chains:** Execute distinct local Model Context Protocol (MCP) or Spring AI file search tool calls down separate paths to gather independent telemetry before merging findings into a primary architectural decision cell.

## **3. UI Semantics & Java Swing Layout Architecture**

Parallel branch forking requires a non-linear Java Swing layout capable of dynamically organizing cells horizontally and vertically while maintaining real-time EDT streaming synchronization.

### **3.1 Swing Component Hierarchy for Parallel Canvas**

```
??? AetherisCanvasPanel (Multi-Column Layout / Custom Dynamic Grid)
 ??? AetherisCellColumn (Column 0: Main Thread Root)
 ? ??? AetherisCellView (Root Cell 01)
 ??? javax.swing.JSplitPane (Parallel Split Container)
 ??? AetherisCellColumn (Column 1: Branch A)
 ? ??? AetherisCellView (Cell 02A)
 ??? AetherisCellColumn (Column 2: Branch B)
 ??? AetherisCellView (Cell 02B)

```

### **3.2 Visual UI Branch Control Semantics**

Each cell header includes visual action triggers for branching and tree navigation:

* **`[ FORK BRANCH ]` Button (`Ctrl + Shift + F`):** Instantly creates a parallel child column adjacent to the active cell, inheriting all upstream context parameters while maintaining an independent cell sequence.
* **Branch Relationship Connectors:** Custom paint routines draw SVG-style anti-aliased connection lines between child branch columns and their root parent cell using color-coded branch IDs.
* **`[ MERGE INTO CELL ]` Trigger:** Connects two or more parallel terminal nodes as input dependencies into a single synthesis downstream cell.
* **Independent Context Toggle per Branch:** Each parallel branch can independently maintain its own 3-state context configuration (`INCLUDED`, `EXCLUDED`, `INPUT_ONLY`) and dynamic `@scope` tags without affecting adjacent branches.

## **4. Data Model & Database Schema Extensions**

### **4.1 Database Schema Additions**

```sql
-- Extended Cell Architecture for DAG Canvas Navigation
ALTER TABLE cells ADD COLUMN parent_cell_id VARCHAR(36) REFERENCES cells(cell_id);
ALTER TABLE cells ADD COLUMN branch_id VARCHAR(64) DEFAULT 'main';
ALTER TABLE cells ADD COLUMN column_position INT DEFAULT 0;

-- Branch Topology & Metadata Table
CREATE TABLE canvas_branches (
 branch_id VARCHAR(64) PRIMARY KEY,
 parent_branch_id VARCHAR(64),
 branch_name VARCHAR(128) NOT NULL, -- e.g., 'Branch A: Local Ollama', 'Branch B: Cloud Groq'
 color_hex VARCHAR(7) DEFAULT '#29B6F6',

```

## **5. Context Assembly Logic for Directed Acyclic Graphs (DAG)**

When assembling context for a cell in a parallel branch, the engine must traverse backwards through that specific branch's parent lineage, ignoring sibling branches unless explicitly referenced via a `MERGE` node or dynamic variable token (`{{cell[02A].output}}`).

### **5.1 DAG Context Assembly Pseudocode**

```python

# Function: AssembleDAGBranchContext

# Purpose: Traverses parent lineage to build isolated context for a parallel branch cell.

FUNCTION AssembleDAGBranchContext(canvas_id: String, target_cell_id: String) -> List<Message>:

 # 1. Traversal Path Discovery: Construct explicit parent lineage root -> target
 lineage_path = []
 current = target_cell

 WHILE current IS NOT NULL:
 lineage_path.prepend(current) # Prepend to preserve chronological sequence
 current = Database.getCell(current.parent_cell_id)

 # 2. Extract active scope tags from target cell
 target_scopes = ExtractScopeTags(target_cell.prompt_text)

 # 3. Process ordered lineage path
 FOR cell IN lineage_path:

 # Omit target cell from context iteration (appended at end)
 IF cell.id == target_cell_id:

 # Hard Rule: Skip EXCLUDED state

 # Scope Filter Verification

 # Handle INPUT_ONLY vs INCLUDED
 ELSE IF cell.context_state == "INCLUDED":

 # 4. Resolve variable bindings for target cell (e.g., cross-branch references)
 evaluated_target_prompt = EvaluateCellVariables(target_cell)
 assembled_messages.append(Message(role="user", content=evaluated_target_prompt))

```

## **6. Multi-Threaded Parallel Execution Workflow**

Aetheris permits simultaneous execution of parallel branches. When an operator triggers `[ EXECUTE ALL BRANCHES ]`, the engine dispatches concurrent workers over independent AMWP channels.

### **6.1 Parallel Execution Dispatcher Pseudocode**

```python

# Function: DispatchParallelBranchExecution

# Purpose: Executes multiple branch terminal nodes concurrently across local/cloud engines.

FUNCTION DispatchParallelBranchExecution(target_cell_ids: List<String>):
 thread_pool = ExecutorService.newFixedThreadPool(target_cell_ids.size())

 FOR cell_id IN target_cell_ids:
 thread_pool.submit(LAMBDA:
 cell = Database.getCell(cell_id)

 # Step 1: Assemble lineage context isolated to this branch
 branch_context = AssembleDAGBranchContext(cell.canvas_id, cell.id)

 # Step 2: Open dedicated AMWP multiplex stream channel (e.g., Channel 0x02 + offset)
 amwp_channel_id = AMWPProtocol.allocateStreamChannel()

 # Step 3: Stream target model execution
 response_stream = ResolveAndExecuteEngine(cell, branch_context)

 # Step 4: Intercept tools & render to Swing EDT in real time
 final_payload = OrchestrateToolCallPipeline(cell, branch_context)

 # Step 5: Commit metrics and zero out ephemeral memory if flagged
 FinalizeAndPersistCellExecution(cell, cell.prompt_text, final_payload.toString(), final_payload.getTrace())

 thread_pool.shutdown

```

# Selective Multi-Engine Workflows: Technical Semantics & Specifications

## **1. Executive Paradigm: Poly-Engine Canvas Mechanics**

In standard AI tools, an entire session is locked to a single model vendor (e.g., all turns run strictly on ChatGPT, Gemini, or a local Ollama model). **Selective Multi-Engine Workflows** break this limitation by allowing operators to assign target execution engines on a **per-cell basis** across a single notebook canvas.

A single Aetheris workspace can seamlessly blend local air-gapped models with high-throughput cloud LPUs and high-reasoning frontier models:

```
| AETHERIS POLY-ENGINE CANVAS |
| |
| [ CELL 01 ] Local Engine: ollama/llama3-70b (Air-Gapped / Private Core Code) |
| | |
| v (Text Push / Dynamic Variable Injection: {{cell_1.output}}) |
| [ CELL 02 ] Cloud LPU: groq/mixtral-8x7b (Ultra-Fast Iteration / Documentation) |
| | |
| v (Sanitized Architecture Abstraction Payload) |
| [ CELL 03 ] Cloud Frontier: gemini/gemini-1.5-pro (High-Context Refactoring) |

```

## **2. Technical Specifications & Semantics**

### **2.1 Engine Binding Mechanics**

Each cell contains a discrete engine target binding string (`execution_target`) stored in the cell runtime metadata and backed by the local H2 storage vault:

* **Target Syntax:** `[provider_type]/[model_identifier]` (e.g., `ollama/codellama`, `groq/llama-3.3-70b`, `gemini/gemini-2.5-pro`).
* **Runtime Adapter Resolution:** The Aetheris core engine resolves the target string via Spring AI unified provider adapters, translating abstract prompt payloads into provider-specific REST, gRPC, or binary socket formats.
* **Per-Cell Parameter Overrides:** Each cell manages independent hyper-parameter configurations:
* `temperature` (0.0 to 1.0)
* `max_tokens` / `context_window_limit`
* `top_p` / `frequency_penalty`
* `tool_calling_enabled` (Boolean flag for MCP/Spring AI local tool hooks)

### **2.2 Engine Capabilities Matrix**

| Feature Spec | Local Ollama Adapters | Groq LPU Cloud Adapters | Gemini Cloud Adapters |
| --- | --- | --- | --- |
| **Transport Layer** | Local Loopback REST / IPC | AMWP Binary / TLS Stream | TLS Stream Engine |
| **Latency Profile** | Hardware Dependent | Ultra-Low (< 20ms TTFT) | Low (~200ms TTFT) |
| **Data Privacy** | 100% Air-Gapped Local | Remote Ephemeral Session | Remote Cloud Session |
| **Context Window** | Restricted by VRAM (e.g., 8k�32k) | Fast Medium Window (128k) | Massive Window (1M+) |
| **Local MCP Tools** | Native Local Execution | Supported via Function Bridge | Supported via Native Call |

### **2.3 Fallback & Air-Gap Routing Specifications**

The multi-engine selector includes automatic network sensing and air-gap protection logic:

```python

# Function: ResolveEngineWithFallback

# Purpose: Ensures runtime engine selection respects network connectivity and privacy bounds.

FUNCTION ResolveEngineWithFallback(cell: Cell) -> ProviderAdapter:
 requested_target = cell.execution_target

 # 1. Enforce Air-Gap Directive
 IF SystemNetwork.isAirGapped() OR SystemNetwork.isOffline():
 IF requested_target.isCloudProvider:
 Logger.warn("Cloud engine target [" + requested_target + "] unavailable while offline. Rerouting to local fallback.")
 RETURN ProviderRegistry.getAdapter("ollama/default_local")

 IF cell.is_ephemeral == TRUE AND requested_target.isCloudProvider():
 # Assert strict security preference
 IF SecurityPolicy.allowCloudEphemeral() == FALSE:
 RaiseError("Security Violation: Ephemeral burner cells restricted to local offline engines.")

 RETURN ProviderRegistry.getAdapter(requested_target)

```

## **3. Targeted Use Cases & Problems Solved**

### **1. The Privacy vs. Speed Dilemma (Hybrid Poly-Engine Orchestration)**

* **The Problem:** Developers usually must choose between keeping sensitive code 100% private using slow local hardware or uploading raw proprietary code to ultra-fast cloud APIs.
* **The Solution:** Run proprietary code analysis locally in **Cell 01** using `ollama/llama3`. Pass *only* sanitized, non-sensitive interface abstractions to **Cell 02** targeting `groq/mixtral` for instant, high-speed API documentation generation.

### **2. Large Context Ingestion & Local Refactoring**

* **The Problem:** Local models struggle to ingest massive 500k-token repository codebases due to local RAM/VRAM limitations.
* **The Solution:** Ingest a massive repository payload into **Cell 01** using `gemini/gemini-pro` (1M+ context window). Extract the isolated, high-value algorithmic logic and forward it into **Cell 02** running locally on `ollama/codellama` for offline low-level bitwise refactoring.

### **3. Multi-Model A/B Benchmarking on a Single Canvas**

* **The Problem:** Evaluating how different models handle identical context requires copying and pasting prompts between multiple browser windows or terminal sessions.
* **The Solution:** Using **Branch Forking**, duplicate a single prompt node into three parallel cells�Cell 02A (`ollama`), Cell 02B (`groq`), and Cell 02C (`gemini`). Execute all three concurrently to evaluate output quality, generation speed, and token cost side-by-side on a single unified canvas layout.

### **4. Cost & Token Optimization**

* **The Problem:** Sending simple formatting or repetitive text transformations to top-tier, expensive cloud models wastes API credits.
* **The Solution:** Route high-reasoning architectural design tasks to top-tier cloud models, while routing simple code cleanup, markdown formatting, or JSON parsing steps to free, local Ollama instances on intermediate cells.

We have covered an exceptionally thorough, production-ready specification for the **Aetheris** platform across every layer of the architecture:

* **Executive Vision & Core Principles:** Local-first memory sovereignty, embedded H2 storage, binary AMWP protocol, per-cell ephemeral execution, and selective context pruning.
* **Database & Persistence Specifications:** Embedded H2 SQL schema, trace audit tables, and zero-knowledge memory buffer scrubbing pipelines.
* **AMWP Binary Protocol Engine:** Binary frame structures, multiplexed channels (`0x01` PTY terminal, `0x02` streaming LLM payloads, `0x03` Spring AI tool calls), and demux routing logic.
* **UI Semantics & Client Integration (Java Swing):** EDT thread-safe streaming via buffered batch flushes, visual state indicators (`INCLUDED`, `EXCLUDED`, `INPUT_ONLY`), hotkey maps, and component hierarchies.
* **Execution Workflows & Pseudocode:** Deterministic context assembly algorithms, dynamic `@scope` tag filtering, multi-pass tool call hooks, and pre/post MCP execution checks.
* **Cell-to-Cell Inter-Data Flow:** Response forwarding, static Text Push, late-binding Variable Push (`{{cell[N].output}}`), and dynamic dynamic pipeline execution.
* **Cell-Based Notebook Metaphor & Branch Forking:** Paradigm breakdown, parallel DAG tree lineage context assembly, side-by-side split column Swing rendering, and multi-threaded parallel engine dispatching.
* **Selective Multi-Engine Routing:** Poly-engine canvas mechanics, Spring AI provider adapters, fallback offline routing, and model capabilities matrices.

### **The Final Remaining Gaps**

To ensure your technical specification package is **100% complete** for building or presenting Aetheris, there are **three final edge-case subsystems** that we haven't explicitly mapped in code and pseudocode:

1. **Terminal-Canvas Crosstalk (AMWP Channel `0x01` $\leftrightarrow$ `0x02` Integration)**
2. **Global Canvas Import/Export & H2 Vault Backup Engine**
3. **Workspace Conflict Resolution & Error Recovery Pipeline**

Here are the complete design specifications and pseudocode for these final three components:

### **1. Terminal-Canvas Crosstalk Mechanics**

#### **The "Why"**

Aetheris features an interactive PTY terminal on Tab A (over AMWP Channel `0x01`) and the AI Canvas on Tab B (over AMWP Channel `0x02`). Operators frequently need to capture terminal stdout (e.g., compile errors, build traces) into a canvas cell, or execute generated code blocks directly in the active PTY session.

#### **Crosstalk Execution Pseudocode**

```python

# Function: PipeTerminalToCanvasCell

# Purpose: Captures active terminal buffer selection and pushes it into a new canvas cell.

FUNCTION PipeTerminalToCanvasCell(terminal_stream_buffer: ByteStream, canvas_id: String, push_mode: Enum):
 selected_text = terminal_stream_buffer.getSelectedTextOrLastNLines(100)

 # 1. Instantiate new cell node on active canvas
 new_cell = Cell(
 id=UUID.generate,
 canvas_id=canvas_id,
 context_state="EXCLUDED", # Default to EXCLUDED to prevent terminal noise from clogging context
 execution_target="local/ollama",
 prompt_text=String.format("```bash\n%s\n```", selected_text)

 # 2. Add semantic tag automatically
 new_cell.addScopeTag("@scope:terminal-dump")

 # 3. Persist and focus UI
 Database.insertCell(new_cell)
 SwingUtilities.invokeLater(LAMBDA: CanvasUI.scrollAndFocusCell(new_cell.id))

# Function: PipeCellCodeToTerminal

# Purpose: Sends a generated code block from a canvas cell directly into the active PTY session.

FUNCTION PipeCellCodeToTerminal(cell_id: String, code_block_index: Int):
 code_snippet = CodeExtractor.extractBlock(cell.response_payload, code_block_index)

 # 1. Sanitize payload for shell execution
 sanitized_bytes = CommandSanitizer.prepareForPTY(code_snippet)

 # 2. Transmit binary frame over AMWP Channel 0x01 (Terminal Stream)
 amwp_frame = AMWPFrame(
 magic=0xAE,
 channel_id=0x01, # Terminal Channel
 type=0x01, # Data Payload
 payload=sanitized_bytes

 AMWPSocketEngine.sendFrame(amwp_frame)

```

### **2. Global Canvas Import/Export & H2 Vault Backup Engine**

#### **The "Why"**

To maintain absolute data sovereignty, operators must be able to export entire workspace canvases�including DAG branch topologies, cell states, tags, and execution traces�into portable, open-standard JSON bundle files (`.aetheris`), as well as import them back into any air-gapped machine.

#### **Vault Import/Export Pseudocode**

```python

# Function: ExportCanvasToSovereignBundle

# Purpose: Serializes a canvas and its full H2 database entity tree into an encrypted or plain JSON asset.

FUNCTION ExportCanvasToSovereignBundle(canvas_id: String, target_file_path: String, include_ephemeral: Boolean):
 cells = Database.getAllCellsForCanvas(canvas_id)
 branches = Database.getCanvasBranches(canvas_id)

 bundle = JSONObject
 bundle.put("version", "1.0.0")
 bundle.put("exported_at", Timestamp.now())
 bundle.put("canvas_meta", canvas.toJSON())
 bundle.put("branches", branches.toJSONArray())

 cell_array = JSONArray
 FOR cell IN cells:
 cell_json = cell.toJSON

 # Security Directive: Scrub ephemeral cells if requested
 IF cell.is_ephemeral == TRUE AND include_ephemeral == FALSE:
 cell_json.put("prompt_payload", NULL)
 cell_json.put("response_payload", NULL)

 cell_array.add(cell_json)

 bundle.put("cells", cell_array)

 # Write to local disk file
 FileIO.writeStringToFile(target_file_path, bundle.toString(indent=2))
 Logger.info("Successfully exported sovereign canvas bundle to: " + target_file_path)

```

### **3. Workspace Conflict Resolution & Error Recovery Pipeline**

#### **The "Why"**

If an LLM execution streams partial tokens over AMWP and fails midway (e.g., due to an API timeout, local VRAM out-of-memory crash, or socket loss), the system must cleanly roll back state, prevent database corruption, and provide clear UI recovery prompts without locking the Swing Event Dispatch Thread (EDT).

#### **Error Recovery Pipeline Pseudocode**

```python

# Function: HandleExecutionFailure

# Purpose: Catches pipeline crashes, restores H2 database consistency, and alerts UI.

FUNCTION HandleExecutionFailure(cell_id: String, error: Exception):
 # 1. Log audit metric
 Logger.error("Execution failed on cell [" + cell_id + "]: " + error.getMessage())

 # 2. Roll back H2 transaction state for cell
 "UPDATE cells SET execution_status = 'FAILED', updated_at = CURRENT_TIMESTAMP WHERE cell_id = ?",
 cell_id

 # Append error payload trace
 "INSERT INTO execution_traces (trace_id, cell_id, tokens_prompt, tokens_completion, latency_ms, error_flag) VALUES (?, ?, 0, 0, 0, TRUE)",
 UUID.generate, cell_id

 # 3. Thread-Safe Swing EDT UI Alert Updates
 cell_view = CellViewManager.getCellView(cell_id)
 cell_view.setExecutionState(ExecutionState.ERROR)
 cell_view.renderErrorBanner("Execution Failure: " + error.getMessage())
 cell_view.enableRunControls() # Re-enable F5 / Run buttons

```

### **Summary Checklist**

With these additions, your design documentation covers:

* System Architecture & Core Principles
* Embedded H2 Database Schema & Ephemeral Memory Scrubbing
* AMWP Binary Protocol Mechanics & Multiplexing Frame Specifications
* Java Swing EDT Thread-Safe Streaming Architecture & UI Semantics
* Scoped Context Assembly, `@scope` Tagging, & 3-State Pruning Logic
* Poly-Engine Routing (Ollama / Groq / Gemini) & Fallback Protocols
* Pre/Post Local MCP & Spring AI Tool Interception Loops
* Cell-to-Cell Push Forwarding (Text Push & Variable Push)
* Multi-Column Parallel Branch Forking & DAG Context Assembly
* Terminal-Canvas AMWP Crosstalk (`0x01` $\leftrightarrow$ `0x02`)
* Sovereign Canvas Export/Import Engine (`.aetheris` JSON bundles)
* Transactional Error Recovery & Circuit Breaker Pipelines

You now possess the entire blueprint for **Aetheris**.
