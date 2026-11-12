# struct Session

[Rust tree](../../index.md) · [aetheris-client](../index.md) · **session**

**Source** `rust/aetheris/aetheris-client/src/session.rs`

```rust
pub struct Session
```

The [`Session`](struct.Session.md) struct is the UI handle on the read thread.
Drop sends disconnect. The password is not stored here. `outbound` holds commands the command channel has not accepted yet.

| | |
| --- | --- |
| **Specification** | GK-0153 aetheris-plan-f-218.001.md FINESSE:f-218 |

## Field Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| Option<async_mpsc::Sender<Cmd>> | [`cmd`](#member-cmd) |  |
| Mutex<VecDeque<Cmd>> | [`outbound`](#member-outbound) |  |
| Option<Receiver<Vec<u8>>> | [`paint`](#member-paint) |  |
| Option<Receiver<&'static str>> | [`status`](#member-status) |  |
| Option<JoinHandle<()>> | [`thread`](#member-thread) |  |
| Arc<AtomicBool> | [`mux`](#member-mux) |  |
| Option<Receiver<ScopePiece>> | [`scope`](#member-scope) |  |
| Mutex<Option<String>> | [`mood_held`](#member-mood_held) | The `mood_held` field is the mood line last handed to this pane. |

## Method Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`start`](#method-start) | The `start` method spawns the read thread and starts the handshake. |
| fn | [`mood_held`](#method-mood_held) | The `mood_held` method returns the line last sent on this pane. |
| fn | [`note_mood_held`](#method-note_mood_held) | The `note_mood_held` method remembers the line just handed to this pane. |
| fn | [`enqueue`](#method-enqueue) | The `enqueue` method parks `cmd` and tries to send the parked commands. |
| fn | [`flush_outbound`](#method-flush_outbound) | The `flush_outbound` method tries the parked commands. |
| fn | [`write`](#method-write) | The `write` method parks terminal bytes. |
| fn | [`wire`](#method-wire) | The `wire` method parks one AMWP frame. |
| fn | [`resize`](#method-resize) | The `resize` method parks a size. |
| fn | [`try_paint`](#method-try_paint) | The `try_paint` method returns one terminal burst when the paint channel has one. |
| fn | [`scope_only`](#method-scope_only) | The `scope_only` method returns a scope queue and no read thread. |
| fn | [`try_scope`](#method-try_scope) | The `try_scope` method returns one scope piece when that channel has one. |
| fn | [`poll_status`](#method-poll_status) | The `poll_status` method returns the newest status waiting on the status channel. |
| fn | [`mux_answered`](#method-mux_answered) | The `mux_answered` method reports whether the demux has answered mux. |
| fn | [`answer_mux`](#method-answer_mux) | The `answer_mux` method marks this test session as having answered mux. |
| fn | [`take_wires`](#method-take_wires) | The `take_wires` method returns wire frames parked on a test session. |
| fn | [`shutdown`](#method-shutdown) | The `shutdown` method disconnects, drops the queues so a blocked send wakes, then joins. |

## Field Detail

### cmd

<a id="member-cmd"></a>

```rust
cmd: Option<async_mpsc::Sender<Cmd>>
```

### outbound

<a id="member-outbound"></a>

```rust
outbound: Mutex<VecDeque<Cmd>>
```

### paint

<a id="member-paint"></a>

```rust
paint: Option<Receiver<Vec<u8>>>
```

### status

<a id="member-status"></a>

```rust
status: Option<Receiver<&'static str>>
```

### thread

<a id="member-thread"></a>

```rust
thread: Option<JoinHandle<()>>
```

### mux

<a id="member-mux"></a>

```rust
mux: Arc<AtomicBool>
```

### scope

<a id="member-scope"></a>

```rust
scope: Option<Receiver<ScopePiece>>
```

### mood_held

<a id="member-mood_held"></a>

```rust
mood_held: Mutex<Option<String>>
```

The `mood_held` field is the mood line last handed to this pane.
`None` means this pane was not told, or the last note was blank.

## Method Detail

### start

<a id="method-start"></a>

```rust
pub fn start(
    host: String,
    port: u16,
    user: String,
    password: String,
    term: String,
) -> Result<Self, &'static str>
```

The `start` method spawns the read thread and starts the handshake.
`host` is the server name. `port` is the server port. `user` is the login name. `password` is moved onto that thread. `term` is the terminal name. No test calls this. The thread is named `aetheris-read`. The command channel holds 64 commands. The paint channel holds 64 bursts. The status channel and the scope channel are unbounded. A runtime that fails to build sends `Disconnected: connection failed`. A thread that fails to spawn returns `Disconnected`.

### mood_held

<a id="method-mood_held"></a>

```rust
pub fn mood_held(&self) -> Option<String>
```

The `mood_held` method returns the line last sent on this pane.
The UI thread is the only caller. A poisoned lock still returns the inner value.

### note_mood_held

<a id="method-note_mood_held"></a>

```rust
pub fn note_mood_held(&self, line: Option<String>)
```

The `note_mood_held` method remembers the line just handed to this pane.
`None` means a clear was sent. A line that trims to empty is stored as `None`.

### enqueue

<a id="method-enqueue"></a>

```rust
fn enqueue(&self, cmd: Cmd)
```

The `enqueue` method parks `cmd` and tries to send the parked commands.
A test session has no read thread. In a test the queue keeps the command. Outside a test a missing sender clears the queue.

### flush_outbound

<a id="method-flush_outbound"></a>

```rust
pub fn flush_outbound(&self)
```

The `flush_outbound` method tries the parked commands.
The window thread calls this before it paints work. A missing sender clears the queue.

### write

<a id="method-write"></a>

```rust
pub fn write(&self, bytes: &[u8])
```

The `write` method parks terminal bytes.
An empty slice is ignored. The bytes are a `Write` command.

### wire

<a id="method-wire"></a>

```rust
pub fn wire(&self, frame: &[u8])
```

The `wire` method parks one AMWP frame.
The read thread writes these bytes as they are. An empty slice is ignored.

### resize

<a id="method-resize"></a>

```rust
pub fn resize(&self, cols: u32, rows: u32)
```

The `resize` method parks a size.
A zero column or a zero row is kept in the command and dropped later by `fold_cmds`.

### try_paint

<a id="method-try_paint"></a>

```rust
pub fn try_paint(&self) -> Option<Vec<u8>>
```

The `try_paint` method returns one terminal burst when the paint channel has one.
An empty channel returns `None`. A session with no paint channel returns `None`.

### scope_only

<a id="method-scope_only"></a>

```rust
pub fn scope_only(scope: Receiver<ScopePiece>) -> Self
```

The `scope_only` method returns a scope queue and no read thread.
Tests push pieces. No test calls [`Session::start`](#method-start).

### try_scope

<a id="method-try_scope"></a>

```rust
pub fn try_scope(&self) -> Option<ScopePiece>
```

The `try_scope` method returns one scope piece when that channel has one.
An empty channel returns `None`.

### poll_status

<a id="method-poll_status"></a>

```rust
pub fn poll_status(&self) -> Option<&'static str>
```

The `poll_status` method returns the newest status waiting on the status channel.
Older statuses in the same call are dropped. An empty channel returns `None`.

### mux_answered

<a id="method-mux_answered"></a>

```rust
pub fn mux_answered(&self) -> bool
```

The `mux_answered` method reports whether the demux has answered mux.
Connected is not this answer. The flag is the init answer.

### answer_mux

<a id="method-answer_mux"></a>

```rust
pub fn answer_mux(&self)
```

The `answer_mux` method marks this test session as having answered mux.
No read thread starts.

### take_wires

<a id="method-take_wires"></a>

```rust
pub fn take_wires(&self) -> Vec<Vec<u8>>
```

The `take_wires` method returns wire frames parked on a test session.
Other commands stay in the queue. The wire commands are removed.

### shutdown

<a id="method-shutdown"></a>

```rust
pub fn shutdown(mut self, bound: Duration) -> Result<(), &'static str>
```

The `shutdown` method disconnects, drops the queues so a blocked send wakes, then joins.
Parked commands are flushed first. Disconnect uses `blocking_send`. The join gives up after `bound`. A session with no thread returns `Ok`.

## impl Drop for Session

```rust
impl Drop for Session
```

### drop

<a id="method-drop"></a>

```rust
fn drop(&mut self)
```

The `drop` method flushes parked commands and sends disconnect.
Disconnect uses `blocking_send`. The read thread is not joined here.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
