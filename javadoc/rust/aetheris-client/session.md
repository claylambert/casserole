# Module session

[Rust tree](../index.md) · **aetheris-client**

**Source** `rust/aetheris/aetheris-client/src/session.rs`

The module runs one SSH session.

`russh` runs on the read thread's runtime. The UI thread parks writes and sends them with `try_send`. It never enters that runtime. It does not block on the command channel. Disconnect still uses `blocking_send`. Paint bytes cross a bounded `sync_channel`. The read thread holds a burst and sends one buffer when the channel has been quiet for 8 milliseconds, when 16 milliseconds have passed since the first byte, or when the buffer reaches 8 kibibytes.

Disconnect flushes that hold and reports at once. A broken mux does the same. The server key is accepted. Version 1 stores no known host. One flag crosses to the UI when the demux is mux. The word Connected is the channel status. The flag is the init answer. Raw leaves the flag clear. Scope A pieces cross on their own queue and skip the paint hold. An init piece is the facts body. Later pieces are channel A data. A wire frame is written as itself. It is not wrapped as Scope T. Tests do not call [`Session::start`](session/struct.Session.md#method-start).

The UI thread sends writes with `try_send`. The read thread sends paint on the paint channel. The read thread sends scope A pieces on their queue.

| | |
| --- | --- |
| **Specification** | L39 rust-aetheris-client.draft.001.md |
| **Specification** | L40 aa-sdd-source-prose.md#RustSshSession |
| **Author** | clayl (2026-10-23) |

## Type Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| enum | [`Cmd`](session/enum.Cmd.md) | The [`Cmd`] enum is a command from the UI thread. |
| struct | [`Burst`](session/struct.Burst.md) | The [`Burst`] struct is the writes and the newest positive size from one drain. |
| struct | [`AcceptAll`](session/struct.AcceptAll.md) | The `AcceptAll` struct is the `russh` handler for this session. |
| struct | [`Session`](session/struct.Session.md) | The [`Session`] struct is the UI handle on the read thread. |
| enum | [`Pump`](session/enum.Pump.md) | The `Pump` enum is the result of one try on the command channel. |
| struct | [`PaintHold`](session/struct.PaintHold.md) | The `PaintHold` struct keeps paint bytes until the channel is quiet, the burst is old, or the buffer is full. |

## Constant Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| const | [`JOIN_BOUND`](#constant-join_bound) | `JOIN_BOUND` is how long close waits for the read thread. |
| const | [`PAINT_BOUND`](#constant-paint_bound) |  |
| const | [`CMD_BOUND`](#constant-cmd_bound) |  |
| const | [`QUIET_MS`](#constant-quiet_ms) | `QUIET_MS` is the quiet gap before a held burst is released. |
| const | [`CAP_MS`](#constant-cap_ms) | `CAP_MS` is the age of the first byte that forces a release while bytes keep arriving. |
| const | [`HOLD_CAP`](#constant-hold_cap) | `HOLD_CAP` is the size that releases the whole buffer, including one larger chunk. |

## Function Summary

| Modifier and Type | Member | Description |
| --- | --- | --- |
| fn | [`fold_cmds`](#function-fold_cmds) | The `fold_cmds` function folds commands into one [`Burst`]. |
| fn | [`join_bounded`](#function-join_bounded) | The `join_bounded` function joins `handle`, or gives up after `bound`. |
| fn | [`note_mux`](#function-note_mux) | The `note_mux` function sets `flag` once [`Demux`] is in the mux state. |
| fn | [`pump`](#function-pump) | The `pump` function sends parked commands in order. |
| fn | [`drive`](#function-drive) | The `drive` function opens the shell and then runs the channel. |
| fn | [`open_shell`](#function-open_shell) | The `open_shell` function connects, authenticates, and requests a shell. |
| fn | [`run_channel`](#function-run_channel) | The `run_channel` function reads the shell and writes parked bursts. |
| fn | [`plan_send`](#function-plan_send) | The `plan_send` function chooses the bytes for one burst. |
| fn | [`write_burst`](#function-write_burst) | The `write_burst` function writes one planned burst and applies its resize. |
| fn | [`emit`](#function-emit) | The `emit` function sends paint bytes when the buffer is not empty. |
| fn | [`elapsed_ms`](#function-elapsed_ms) | The `elapsed_ms` function returns milliseconds since `origin`. |
| fn | [`arm_hold`](#function-arm_hold) | The `arm_hold` function resets the paint sleep when a release is still in the future. |
| fn | [`stage_paint`](#function-stage_paint) | The `stage_paint` function stages demux paint. |
| fn | [`end_link`](#function-end_link) | The `end_link` function flushes the hold and sends `status`. |

## Constant Detail

### JOIN_BOUND

<a id="constant-join_bound"></a>

```rust
pub const JOIN_BOUND: Duration = Duration::from_secs(2);
```

`JOIN_BOUND` is how long close waits for the read thread.
The duration is 2 seconds. `shutdown` receives it from the caller.

### PAINT_BOUND

<a id="constant-paint_bound"></a>

```rust
const PAINT_BOUND: usize = 64;
```

### CMD_BOUND

<a id="constant-cmd_bound"></a>

```rust
const CMD_BOUND: usize = 64;
```

### QUIET_MS

<a id="constant-quiet_ms"></a>

```rust
const QUIET_MS: u64 = 8;
```

`QUIET_MS` is the quiet gap before a held burst is released.
The gap is 8 milliseconds.

### CAP_MS

<a id="constant-cap_ms"></a>

```rust
const CAP_MS: u64 = 16;
```

`CAP_MS` is the age of the first byte that forces a release while bytes keep arriving.
The age is 16 milliseconds.

### HOLD_CAP

<a id="constant-hold_cap"></a>

```rust
const HOLD_CAP: usize = 8 * 1024;
```

`HOLD_CAP` is the size that releases the whole buffer, including one larger chunk.
The size is 8 kibibytes.

## Function Detail

### fold_cmds

<a id="function-fold_cmds"></a>

```rust
pub fn fold_cmds(cmds: Vec<Cmd>) -> Burst
```

The `fold_cmds` function folds commands into one [`Burst`](session/struct.Burst.md).
An empty write is dropped. An empty wire is dropped. A resize with a zero axis is dropped, so the last positive size stays. Disconnect stops the fold. Commands after that disconnect are ignored.

### join_bounded

<a id="function-join_bounded"></a>

```rust
pub fn join_bounded(handle: JoinHandle<()>, bound: Duration) -> Result<(), &'static str>
```

The `join_bounded` function joins `handle`, or gives up after `bound`.
The join runs on another thread. A timeout returns `read thread did not stop`. A finished join returns `Ok`. A sleeper is enough to prove the bound.

### note_mux

<a id="function-note_mux"></a>

```rust
pub fn note_mux(flag: &AtomicBool, demux: &Demux)
```

The `note_mux` function sets `flag` once [`Demux`](amwp/struct.Demux.md) is in the mux state.
A wait state leaves the flag unchanged. A raw stream leaves the flag unchanged. A timeout leaves the flag unchanged. The function does not store false. The flag stays set after the first mux answer. Dropping the session is what returns the tab strip to SSH.

### pump

<a id="function-pump"></a>

```rust
fn pump(queue: &mut VecDeque<Cmd>, mut step: impl FnMut(Cmd) -> Pump)
```

The `pump` function sends parked commands in order.
A full channel puts that command back at the front and returns. A closed channel clears the queue. A sent command continues with the next one.

### drive

<a id="function-drive"></a>

```rust
async fn drive(
    host: String,
    port: u16,
    user: String,
    mut password: String,
    term: String,
    cmds: async_mpsc::Receiver<Cmd>,
    paint_tx: SyncSender<Vec<u8>>,
    status_tx: mpsc::Sender<&'static str>,
    mux: Arc<AtomicBool>,
    scope_tx: mpsc::Sender<ScopePiece>,
)
```

The `drive` function opens the shell and then runs the channel.
The password is cleared after the open attempt. A failed open sends that status and returns.

### open_shell

<a id="function-open_shell"></a>

```rust
async fn open_shell(
    host: &str,
    port: u16,
    user: &str,
    password: &str,
    term: &str,
) -> Result<
    (
        russh::client::Handle<AcceptAll>,
        russh::Channel<russh::client::Msg>,
    ),
    &'static str,
>
```

The `open_shell` function connects, authenticates, and requests a shell.
The client config is the `russh` default. The connect wait is 20 seconds. A failed connect returns `Disconnected: connection failed`. A failed password returns `Disconnected: auth failed`. A password that is not accepted returns the same auth text. A failed session channel returns `Disconnected: connection failed`. An empty terminal name becomes `vt100`. The first PTY size is 80 columns by 24 rows. The pixel size is 0 by 0. The mode list is empty. A failed PTY request returns `Disconnected: connection failed`. A failed shell request returns the same text.

### run_channel

<a id="function-run_channel"></a>

```rust
async fn run_channel(
    handle: russh::client::Handle<AcceptAll>,
    mut channel: russh::Channel<russh::client::Msg>,
    mut cmds: async_mpsc::Receiver<Cmd>,
    paint_tx: SyncSender<Vec<u8>>,
    status_tx: mpsc::Sender<&'static str>,
    mux: Arc<AtomicBool>,
    scope_tx: mpsc::Sender<ScopePiece>,
)
```

The `run_channel` function reads the shell and writes parked bursts.
The handle is held so the socket stays open until the function returns. The word Connected is sent when the channel is up. A failed status send returns at once. The init frame is written next. A failed init write sends `Disconnected` and returns. The init wait is [`INIT_WAIT_MS`](amwp.md#constant-init_wait_ms). The keepalive interval is [`KEEPALIVE_MS`](amwp.md#constant-keepalive_ms). Missed keepalive ticks are skipped. The first interval tick is consumed before the loop.

A closed command channel flushes the paint hold and leaves the loop. A burst that fails to write ends the link with `Disconnected`. A disconnect flushes the hold, closes the channel, sends `Disconnected`, and leaves the loop. A closed shell ends the link with `Disconnected`. Scope pieces are sent on their own queue and skip the paint hold. A broken mux appends the last paint, flushes the hold, and sends `Disconnected: mux broken`. A keepalive is written only while the demux is mux. A failed keepalive ends the link with `Disconnected`.

### plan_send

<a id="function-plan_send"></a>

```rust
pub fn plan_send(mux: bool, burst: &Burst) -> Option<Vec<Vec<u8>>>
```

The `plan_send` function chooses the bytes for one burst.
Terminal writes are wrapped with `scope_t_data` once the mux is up. Before that they are copied as themselves. Wire frames stay as sent. A wrap that does not fit returns `None`.

### write_burst

<a id="function-write_burst"></a>

```rust
async fn write_burst(
    channel: &russh::Channel<russh::client::Msg>,
    demux: &Demux,
    burst: &Burst,
) -> bool
```

The `write_burst` function writes one planned burst and applies its resize.
A plan that returns `None` returns false. A failed channel write returns false. A failed window change returns false. No resize still returns true after the writes.

### emit

<a id="function-emit"></a>

```rust
fn emit(tx: &SyncSender<Vec<u8>>, bytes: Vec<u8>) -> bool
```

The `emit` function sends paint bytes when the buffer is not empty.
An empty buffer returns true without a send. A failed send returns false.

### elapsed_ms

<a id="function-elapsed_ms"></a>

```rust
fn elapsed_ms(origin: tokio::time::Instant) -> u64
```

The `elapsed_ms` function returns milliseconds since `origin`.
A value that does not fit in `u64` returns `u64::MAX`.

### arm_hold

<a id="function-arm_hold"></a>

```rust
fn arm_hold(
    hold: &PaintHold,
    now_ms: u64,
    sleep: std::pin::Pin<&mut tokio::time::Sleep>,
) -> bool
```

The `arm_hold` function resets the paint sleep when a release is still in the future.
An empty hold returns false. A delay of 0 returns false. Any other delay resets `sleep` from the current instant plus that delay and returns true.

### stage_paint

<a id="function-stage_paint"></a>

```rust
fn stage_paint(
    hold: &mut PaintHold,
    paint: &[u8],
    now_ms: u64,
    paint_tx: &SyncSender<Vec<u8>>,
    sleep: std::pin::Pin<&mut tokio::time::Sleep>,
    armed: &mut bool,
) -> bool
```

The `stage_paint` function stages demux paint.
True means the UI is still listening. A release from `push` disarms the sleep and sends that buffer. A release from `poll` does the same. An empty hold disarms the sleep and returns true. A held burst arms the sleep and returns true. A failed send returns false.

### end_link

<a id="function-end_link"></a>

```rust
fn end_link(
    hold: &mut PaintHold,
    paint_tx: &SyncSender<Vec<u8>>,
    status_tx: &mpsc::Sender<&'static str>,
    status: &'static str,
)
```

The `end_link` function flushes the hold and sends `status`.
A failed paint send is ignored. A failed status send is ignored.

---

Copyright 2026 Clay Lambert. MIT License.
Generated by `rust_javadoc_md.py` from the Rust doc comments.
