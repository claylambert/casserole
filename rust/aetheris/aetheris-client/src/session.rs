// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The module runs one SSH session.
//!
//! `russh` runs on the read thread's runtime. The UI thread parks writes and sends them with `try_send`. It never enters that runtime. It does not block on the command channel. Disconnect still uses `blocking_send`. Paint bytes cross a bounded `sync_channel`. The read thread holds a burst and sends one buffer when the channel has been quiet for 8 milliseconds, when 16 milliseconds have passed since the first byte, or when the buffer reaches 8 kibibytes.
//!
//! Disconnect flushes that hold and reports at once. A broken mux does the same. The server key is accepted. Version 1 stores no known host. One flag crosses to the UI when the demux is mux. The word Connected is the channel status. The flag is the init answer. Raw leaves the flag clear. Scope A pieces cross on their own queue and skip the paint hold. An init piece is the facts body. Later pieces are channel A data. A wire frame is written as itself. It is not wrapped as Scope T. Tests do not call [`Session::start`].
//!
//! The UI thread sends writes with `try_send`. The read thread sends paint on the paint channel. The read thread sends scope A pieces on their queue.
//!
//! @aa-sdd L39 rust-aetheris-client.draft.001.md
//! @aa-sdd L40 aa-sdd-source-prose.md#RustSshSession
//! @author clayl (2026-10-23)

use std::collections::VecDeque;
use std::sync::atomic::{AtomicBool, Ordering};
use std::sync::mpsc::{self, Receiver, SyncSender};
use std::sync::{Arc, Mutex};
use std::thread::JoinHandle;
use std::time::Duration;

use tokio::sync::mpsc as async_mpsc;
use tokio::sync::mpsc::error::TrySendError;

use crate::amwp::{self, Demux, ScopePiece};

/// `JOIN_BOUND` is how long close waits for the read thread.
/// The duration is 2 seconds. `shutdown` receives it from the caller.
pub const JOIN_BOUND: Duration = Duration::from_secs(2);

const PAINT_BOUND: usize = 64;
const CMD_BOUND: usize = 64;

/// `QUIET_MS` is the quiet gap before a held burst is released.
/// The gap is 8 milliseconds.
const QUIET_MS: u64 = 8;
/// `CAP_MS` is the age of the first byte that forces a release while bytes keep arriving.
/// The age is 16 milliseconds.
const CAP_MS: u64 = 16;
/// `HOLD_CAP` is the size that releases the whole buffer, including one larger chunk.
/// The size is 8 kibibytes.
const HOLD_CAP: usize = 8 * 1024;

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The [`Cmd`] enum is a command from the UI thread.
/// `Write` is terminal bytes. `Wire` is one AMWP frame. `Resize` is a positive column count and a positive row count. `Disconnect` stops the read loop.
#[derive(Clone, Debug)]
pub enum Cmd {
    Write(Vec<u8>),
    Wire(Vec<u8>),
    Resize { cols: u32, rows: u32 },
    Disconnect,
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The [`Burst`] struct is the writes and the newest positive size from one drain.
/// `writes` are terminal chunks. `wires` are frames. `resize` is the last positive size in the drain. `disconnect` is true when a disconnect was seen.
#[derive(Clone, Debug, PartialEq, Eq)]
pub struct Burst {
    pub writes: Vec<Vec<u8>>,
    pub wires: Vec<Vec<u8>>,
    pub resize: Option<(u32, u32)>,
    pub disconnect: bool,
}

/// The `fold_cmds` function folds commands into one [`Burst`].
/// An empty write is dropped. An empty wire is dropped. A resize with a zero axis is dropped, so the last positive size stays. Disconnect stops the fold. Commands after that disconnect are ignored.
pub fn fold_cmds(cmds: Vec<Cmd>) -> Burst {
    let mut burst = Burst {
        writes: Vec::new(),
        wires: Vec::new(),
        resize: None,
        disconnect: false,
    };
    for cmd in cmds {
        if burst.disconnect {
            break;
        }
        match cmd {
            Cmd::Write(bytes) => {
                if !bytes.is_empty() {
                    burst.writes.push(bytes);
                }
            }
            Cmd::Wire(frame) => {
                if !frame.is_empty() {
                    burst.wires.push(frame);
                }
            }
            Cmd::Resize { cols, rows } => {
                if cols > 0 && rows > 0 {
                    burst.resize = Some((cols, rows));
                }
            }
            Cmd::Disconnect => burst.disconnect = true,
        }
    }
    burst
}

/// The `join_bounded` function joins `handle`, or gives up after `bound`.
/// The join runs on another thread. A timeout returns `read thread did not stop`. A finished join returns `Ok`. A sleeper is enough to prove the bound.
pub fn join_bounded(handle: JoinHandle<()>, bound: Duration) -> Result<(), &'static str> {
    let (tx, rx) = mpsc::channel();
    std::thread::spawn(move || {
        let _ = handle.join();
        let _ = tx.send(());
    });
    match rx.recv_timeout(bound) {
        Ok(()) => Ok(()),
        Err(_) => Err("read thread did not stop"),
    }
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The `AcceptAll` struct is the `russh` handler for this session.
/// It accepts the server key. Version 1 stores no known host.
struct AcceptAll;

impl russh::client::Handler for AcceptAll {
    /// The associated type `Error` is the error type from `russh`.
    /// The trait requires it. [`AcceptAll::check_server_key`] returns `Ok` and does not construct this error.
    type Error = russh::Error;

    /// The `check_server_key` method accepts the server key.
    /// The key bytes are not stored. The result is `Ok(true)`.
    async fn check_server_key(
        &mut self,
        _server_public_key: &russh::keys::PublicKeyOrCertificate,
    ) -> Result<bool, Self::Error> {
        Ok(true)
    }
}

/// The `note_mux` function sets `flag` once [`Demux`] is in the mux state.
/// A wait state leaves the flag unchanged. A raw stream leaves the flag unchanged. A timeout leaves the flag unchanged. The function does not store false. The flag stays set after the first mux answer. Dropping the session is what returns the tab strip to SSH.
pub fn note_mux(flag: &AtomicBool, demux: &Demux) {
    if demux.is_mux() {
        flag.store(true, Ordering::Relaxed);
    }
}

/// @aa-sdd PK-1074 docs/specs/common/formal-plan.spec.md FINESSE:f-218
/// The [`Session`] struct is the UI handle on the read thread.
/// Drop sends disconnect. The password is not stored here. `outbound` holds commands the command channel has not accepted yet.
pub struct Session {
    cmd: Option<async_mpsc::Sender<Cmd>>,
    outbound: Mutex<VecDeque<Cmd>>,
    paint: Option<Receiver<Vec<u8>>>,
    status: Option<Receiver<&'static str>>,
    thread: Option<JoinHandle<()>>,
    mux: Arc<AtomicBool>,
    scope: Option<Receiver<ScopePiece>>,
    /// The `mood_held` field is the mood line last handed to this pane.
    /// `None` means this pane was not told, or the last note was blank.
    mood_held: Mutex<Option<String>>,
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The `Pump` enum is the result of one try on the command channel.
/// `Sent` means the command left. `Full` returns the command so it can stay at the front. `Closed` means the read thread is gone.
enum Pump {
    Sent,
    Full(Cmd),
    Closed,
}

/// The `pump` function sends parked commands in order.
/// A full channel puts that command back at the front and returns. A closed channel clears the queue. A sent command continues with the next one.
fn pump(queue: &mut VecDeque<Cmd>, mut step: impl FnMut(Cmd) -> Pump) {
    while let Some(cmd) = queue.pop_front() {
        match step(cmd) {
            Pump::Sent => {}
            Pump::Full(cmd) => {
                queue.push_front(cmd);
                return;
            }
            Pump::Closed => {
                queue.clear();
                return;
            }
        }
    }
}

impl Session {
    /// The `start` method spawns the read thread and starts the handshake.
    /// `host` is the server name. `port` is the server port. `user` is the login name. `password` is moved onto that thread. `term` is the terminal name. No test calls this. The thread is named `aetheris-read`. The command channel holds 64 commands. The paint channel holds 64 bursts. The status channel and the scope channel are unbounded. A runtime that fails to build sends `Disconnected: connection failed`. A thread that fails to spawn returns `Disconnected`.
    pub fn start(
        host: String,
        port: u16,
        user: String,
        password: String,
        term: String,
    ) -> Result<Self, &'static str> {
        let (cmd_tx, cmd_rx) = async_mpsc::channel(CMD_BOUND);
        let (paint_tx, paint_rx) = mpsc::sync_channel(PAINT_BOUND);
        let (status_tx, status_rx) = mpsc::channel();
        let (scope_tx, scope_rx) = mpsc::channel();
        let mux = Arc::new(AtomicBool::new(false));
        let mux_read = Arc::clone(&mux);
        let thread = std::thread::Builder::new()
            .name("aetheris-read".into())
            .spawn(move || {
                let rt = match tokio::runtime::Builder::new_current_thread()
                    .enable_all()
                    .build()
                {
                    Ok(rt) => rt,
                    Err(_) => {
                        let _ = status_tx.send("Disconnected: connection failed");
                        return;
                    }
                };
                rt.block_on(drive(
                    host,
                    port,
                    user,
                    password,
                    term,
                    cmd_rx,
                    paint_tx,
                    status_tx,
                    mux_read,
                    scope_tx,
                ));
            })
            .map_err(|_| "Disconnected")?;
        Ok(Self {
            cmd: Some(cmd_tx),
            outbound: Mutex::new(VecDeque::new()),
            paint: Some(paint_rx),
            status: Some(status_rx),
            thread: Some(thread),
            mux,
            scope: Some(scope_rx),
            mood_held: Mutex::new(None),
        })
    }

    /// The `mood_held` method returns the line last sent on this pane.
    /// The UI thread is the only caller. A poisoned lock still returns the inner value.
    pub fn mood_held(&self) -> Option<String> {
        self.mood_held
            .lock()
            .unwrap_or_else(|poison| poison.into_inner())
            .clone()
    }

    /// The `note_mood_held` method remembers the line just handed to this pane.
    /// `None` means a clear was sent. A line that trims to empty is stored as `None`.
    pub fn note_mood_held(&self, line: Option<String>) {
        let mut held = self
            .mood_held
            .lock()
            .unwrap_or_else(|poison| poison.into_inner());
        *held = line.filter(|text| !text.trim().is_empty());
    }

    /// The `enqueue` method parks `cmd` and tries to send the parked commands.
    /// A test session has no read thread. In a test the queue keeps the command. Outside a test a missing sender clears the queue.
    fn enqueue(&self, cmd: Cmd) {
        let mut queue = self.outbound.lock().unwrap_or_else(|poison| poison.into_inner());
        queue.push_back(cmd);
        let Some(tx) = &self.cmd else {
            // A test session has no read thread. The queue keeps the frame so the test can read it.
            #[cfg(not(test))]
            queue.clear();
            return;
        };
        pump(&mut queue, |cmd| match tx.try_send(cmd) {
            Ok(()) => Pump::Sent,
            Err(TrySendError::Full(cmd)) => Pump::Full(cmd),
            Err(TrySendError::Closed(_)) => Pump::Closed,
        });
    }

    /// The `flush_outbound` method tries the parked commands.
    /// The window thread calls this before it paints work. A missing sender clears the queue.
    pub fn flush_outbound(&self) {
        let mut queue = self.outbound.lock().unwrap_or_else(|poison| poison.into_inner());
        let Some(tx) = &self.cmd else {
            queue.clear();
            return;
        };
        pump(&mut queue, |cmd| match tx.try_send(cmd) {
            Ok(()) => Pump::Sent,
            Err(TrySendError::Full(cmd)) => Pump::Full(cmd),
            Err(TrySendError::Closed(_)) => Pump::Closed,
        });
    }

    /// The `write` method parks terminal bytes.
    /// An empty slice is ignored. The bytes are a `Write` command.
    pub fn write(&self, bytes: &[u8]) {
        if bytes.is_empty() {
            return;
        }
        self.enqueue(Cmd::Write(bytes.to_vec()));
    }

    /// The `wire` method parks one AMWP frame.
    /// The read thread writes these bytes as they are. An empty slice is ignored.
    pub fn wire(&self, frame: &[u8]) {
        if frame.is_empty() {
            return;
        }
        self.enqueue(Cmd::Wire(frame.to_vec()));
    }

    /// The `resize` method parks a size.
    /// A zero column or a zero row is kept in the command and dropped later by `fold_cmds`.
    pub fn resize(&self, cols: u32, rows: u32) {
        self.enqueue(Cmd::Resize { cols, rows });
    }

    /// The `try_paint` method returns one terminal burst when the paint channel has one.
    /// An empty channel returns `None`. A session with no paint channel returns `None`.
    pub fn try_paint(&self) -> Option<Vec<u8>> {
        self.paint.as_ref()?.try_recv().ok()
    }

    /// The `scope_only` method returns a scope queue and no read thread.
    /// Tests push pieces. No test calls [`Session::start`].
    #[cfg(test)]
    pub fn scope_only(scope: Receiver<ScopePiece>) -> Self {
        Self {
            cmd: None,
            outbound: Mutex::new(VecDeque::new()),
            paint: None,
            status: None,
            thread: None,
            mux: Arc::new(AtomicBool::new(false)),
            scope: Some(scope),
            mood_held: Mutex::new(None),
        }
    }

    /// The `try_scope` method returns one scope piece when that channel has one.
    /// An empty channel returns `None`.
    pub fn try_scope(&self) -> Option<ScopePiece> {
        self.scope.as_ref()?.try_recv().ok()
    }

    /// The `poll_status` method returns the newest status waiting on the status channel.
    /// Older statuses in the same call are dropped. An empty channel returns `None`.
    pub fn poll_status(&self) -> Option<&'static str> {
        let status = self.status.as_ref()?;
        let mut last = None;
        while let Ok(text) = status.try_recv() {
            last = Some(text);
        }
        last
    }

    /// The `mux_answered` method reports whether the demux has answered mux.
    /// Connected is not this answer. The flag is the init answer.
    pub fn mux_answered(&self) -> bool {
        self.mux.load(Ordering::Relaxed)
    }

    /// The `answer_mux` method marks this test session as having answered mux.
    /// No read thread starts.
    #[cfg(test)]
    pub fn answer_mux(&self) {
        self.mux.store(true, Ordering::Relaxed);
    }

    /// The `take_wires` method returns wire frames parked on a test session.
    /// Other commands stay in the queue. The wire commands are removed.
    #[cfg(test)]
    pub fn take_wires(&self) -> Vec<Vec<u8>> {
        let mut queue = self
            .outbound
            .lock()
            .unwrap_or_else(|poison| poison.into_inner());
        let mut wires = Vec::new();
        let mut keep = VecDeque::new();
        while let Some(cmd) = queue.pop_front() {
            match cmd {
                Cmd::Wire(frame) => wires.push(frame),
                other => keep.push_back(other),
            }
        }
        *queue = keep;
        wires
    }

    /// The `shutdown` method disconnects, drops the queues so a blocked send wakes, then joins.
    /// Parked commands are flushed first. Disconnect uses `blocking_send`. The join gives up after `bound`. A session with no thread returns `Ok`.
    pub fn shutdown(mut self, bound: Duration) -> Result<(), &'static str> {
        self.flush_outbound();
        if let Some(cmd) = self.cmd.take() {
            let _ = cmd.blocking_send(Cmd::Disconnect);
        }
        drop(self.paint.take());
        drop(self.status.take());
        drop(self.scope.take());
        match self.thread.take() {
            Some(handle) => join_bounded(handle, bound),
            None => Ok(()),
        }
    }
}

impl Drop for Session {
    /// The `drop` method flushes parked commands and sends disconnect.
    /// Disconnect uses `blocking_send`. The read thread is not joined here.
    fn drop(&mut self) {
        self.flush_outbound();
        if let Some(cmd) = self.cmd.take() {
            let _ = cmd.blocking_send(Cmd::Disconnect);
        }
    }
}

/// The `drive` function opens the shell and then runs the channel.
/// The password is cleared after the open attempt. A failed open sends that status and returns.
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
) {
    let opened = open_shell(&host, port, &user, &password, &term).await;
    password.clear();
    drop(password);
    let (handle, channel) = match opened {
        Ok(pair) => pair,
        Err(text) => {
            let _ = status_tx.send(text);
            return;
        }
    };
    run_channel(handle, channel, cmds, paint_tx, status_tx, mux, scope_tx).await;
}

/// The `open_shell` function connects, authenticates, and requests a shell.
/// The client config is the `russh` default. The connect wait is 20 seconds. A failed connect returns `Disconnected: connection failed`. A failed password returns `Disconnected: auth failed`. A password that is not accepted returns the same auth text. A failed session channel returns `Disconnected: connection failed`. An empty terminal name becomes `vt100`. The first PTY size is 80 columns by 24 rows. The pixel size is 0 by 0. The mode list is empty. A failed PTY request returns `Disconnected: connection failed`. A failed shell request returns the same text.
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
> {
    let config = std::sync::Arc::new(russh::client::Config::default());
    let connect = russh::client::connect(config, (host, port), AcceptAll);
    let mut handle = match tokio::time::timeout(Duration::from_secs(20), connect).await {
        Ok(Ok(handle)) => handle,
        _ => return Err("Disconnected: connection failed"),
    };
    let auth = match handle.authenticate_password(user, password).await {
        Ok(auth) => auth,
        Err(_) => return Err("Disconnected: auth failed"),
    };
    if !auth.success() {
        return Err("Disconnected: auth failed");
    }
    let channel = match handle.channel_open_session().await {
        Ok(channel) => channel,
        Err(_) => return Err("Disconnected: connection failed"),
    };
    let modes: &[(russh::Pty, u32)] = &[];
    let term = if term.is_empty() { "vt100" } else { term };
    if channel
        .request_pty(true, term, 80, 24, 0, 0, modes)
        .await
        .is_err()
    {
        return Err("Disconnected: connection failed");
    }
    if channel.request_shell(true).await.is_err() {
        return Err("Disconnected: connection failed");
    }
    Ok((handle, channel))
}

/// The `run_channel` function reads the shell and writes parked bursts.
/// The handle is held so the socket stays open until the function returns. The word Connected is sent when the channel is up. A failed status send returns at once. The init frame is written next. A failed init write sends `Disconnected` and returns. The init wait is [`INIT_WAIT_MS`]. The keepalive interval is [`KEEPALIVE_MS`]. Missed keepalive ticks are skipped. The first interval tick is consumed before the loop.
///
/// A closed command channel flushes the paint hold and leaves the loop. A burst that fails to write ends the link with `Disconnected`. A disconnect flushes the hold, closes the channel, sends `Disconnected`, and leaves the loop. A closed shell ends the link with `Disconnected`. Scope pieces are sent on their own queue and skip the paint hold. A broken mux appends the last paint, flushes the hold, and sends `Disconnected: mux broken`. A keepalive is written only while the demux is mux. A failed keepalive ends the link with `Disconnected`.
async fn run_channel(
    handle: russh::client::Handle<AcceptAll>,
    mut channel: russh::Channel<russh::client::Msg>,
    mut cmds: async_mpsc::Receiver<Cmd>,
    paint_tx: SyncSender<Vec<u8>>,
    status_tx: mpsc::Sender<&'static str>,
    mux: Arc<AtomicBool>,
    scope_tx: mpsc::Sender<ScopePiece>,
) {
    // `handle` owns the connection. Dropping it closes the socket.
    let _hold = handle;
    if status_tx.send("Connected").is_err() {
        return;
    }
    if channel
        .data_bytes(amwp::init_frame())
        .await
        .is_err()
    {
        let _ = status_tx.send("Disconnected");
        return;
    }
    let mut demux = Demux::new();
    let mut waiting = true;
    let sleep = tokio::time::sleep(Duration::from_millis(amwp::INIT_WAIT_MS));
    tokio::pin!(sleep);
    let paint_sleep = tokio::time::sleep(Duration::from_millis(QUIET_MS));
    tokio::pin!(paint_sleep);
    let mut paint_hold = PaintHold::new();
    let mut paint_armed = false;
    let origin = tokio::time::Instant::now();
    let mut ka = tokio::time::interval(Duration::from_millis(amwp::KEEPALIVE_MS));
    ka.set_missed_tick_behavior(tokio::time::MissedTickBehavior::Skip);
    ka.tick().await;

    loop {
        tokio::select! {
            biased;
            cmd = cmds.recv() => {
                let Some(first) = cmd else {
                    let _ = emit(&paint_tx, paint_hold.take());
                    break;
                };
                let mut queued = vec![first];
                while let Ok(next) = cmds.try_recv() {
                    queued.push(next);
                }
                let burst = fold_cmds(queued);
                if !write_burst(&channel, &demux, &burst).await {
                    end_link(&mut paint_hold, &paint_tx, &status_tx, "Disconnected");
                    break;
                }
                if burst.disconnect {
                    let _ = emit(&paint_tx, paint_hold.take());
                    let _ = channel.close().await;
                    let _ = status_tx.send("Disconnected");
                    break;
                }
            }
            msg = channel.wait() => {
                let Some(msg) = msg else {
                    end_link(&mut paint_hold, &paint_tx, &status_tx, "Disconnected");
                    break;
                };
                if let russh::ChannelMsg::Data { data } = msg {
                    let paint = demux.push(&data);
                    note_mux(&mux, &demux);
                    for body in demux.take_scope_a() {
                        if scope_tx.send(body).is_err() {
                            break;
                        }
                    }
                    if !demux.is_wait() {
                        waiting = false;
                    }
                    let now = elapsed_ms(origin);
                    if demux.is_broken() {
                        paint_hold.append(&paint);
                        end_link(
                            &mut paint_hold,
                            &paint_tx,
                            &status_tx,
                            "Disconnected: mux broken",
                        );
                        break;
                    }
                    if !stage_paint(
                        &mut paint_hold,
                        &paint,
                        now,
                        &paint_tx,
                        paint_sleep.as_mut(),
                        &mut paint_armed,
                    ) {
                        break;
                    }
                }
            }
            _ = &mut paint_sleep, if paint_armed => {
                let now = elapsed_ms(origin);
                if let Some(out) = paint_hold.poll(now) {
                    if !emit(&paint_tx, out) {
                        break;
                    }
                }
                if paint_hold.delay_ms(now) == Some(0) {
                    if !emit(&paint_tx, paint_hold.take()) {
                        break;
                    }
                }
                paint_armed = arm_hold(&paint_hold, now, paint_sleep.as_mut());
            }
            _ = &mut sleep, if waiting => {
                let paint = demux.timed_out();
                note_mux(&mux, &demux);
                waiting = false;
                let now = elapsed_ms(origin);
                if !stage_paint(
                    &mut paint_hold,
                    &paint,
                    now,
                    &paint_tx,
                    paint_sleep.as_mut(),
                    &mut paint_armed,
                ) {
                    break;
                }
            }
            _ = ka.tick(), if demux.is_mux() => {
                if channel.data_bytes(amwp::keepalive_frame()).await.is_err() {
                    end_link(&mut paint_hold, &paint_tx, &status_tx, "Disconnected");
                    break;
                }
            }
        }
    }
}

/// The `plan_send` function chooses the bytes for one burst.
/// Terminal writes are wrapped with `scope_t_data` once the mux is up. Before that they are copied as themselves. Wire frames stay as sent. A wrap that does not fit returns `None`.
pub fn plan_send(mux: bool, burst: &Burst) -> Option<Vec<Vec<u8>>> {
    let mut out = Vec::new();
    for bytes in &burst.writes {
        let payload = if mux {
            amwp::scope_t_data(bytes)?
        } else {
            bytes.clone()
        };
        out.push(payload);
    }
    for frame in &burst.wires {
        if !frame.is_empty() {
            out.push(frame.clone());
        }
    }
    Some(out)
}

/// The `write_burst` function writes one planned burst and applies its resize.
/// A plan that returns `None` returns false. A failed channel write returns false. A failed window change returns false. No resize still returns true after the writes.
async fn write_burst(
    channel: &russh::Channel<russh::client::Msg>,
    demux: &Demux,
    burst: &Burst,
) -> bool {
    let Some(planned) = plan_send(demux.is_mux(), burst) else {
        return false;
    };
    for payload in planned {
        if channel.data_bytes(payload).await.is_err() {
            return false;
        }
    }
    if let Some((cols, rows)) = burst.resize {
        if channel.window_change(cols, rows, 0, 0).await.is_err() {
            return false;
        }
    }
    true
}

/// The `emit` function sends paint bytes when the buffer is not empty.
/// An empty buffer returns true without a send. A failed send returns false.
fn emit(tx: &SyncSender<Vec<u8>>, bytes: Vec<u8>) -> bool {
    if bytes.is_empty() {
        return true;
    }
    tx.send(bytes).is_ok()
}

/// The `elapsed_ms` function returns milliseconds since `origin`.
/// A value that does not fit in `u64` returns `u64::MAX`.
fn elapsed_ms(origin: tokio::time::Instant) -> u64 {
    u64::try_from(origin.elapsed().as_millis()).unwrap_or(u64::MAX)
}

/// @aa-sdd L37 rust-aetheris-client.draft.001.md
/// The `PaintHold` struct keeps paint bytes until the channel is quiet, the burst is old, or the buffer is full.
/// The caller owns the clock. [`PaintHold::push`] does not release because the quiet gap is zero at that instant. [`PaintHold::poll`] releases on the quiet gap or the age cap. [`PaintHold::take`] is the immediate flush. An empty push leaves the quiet mark where it was.
struct PaintHold {
    buf: Vec<u8>,
    start_ms: u64,
    last_ms: u64,
}

impl PaintHold {
    /// The `new` method returns an empty hold.
    /// The buffer is empty. Both clock marks are 0.
    fn new() -> Self {
        Self {
            buf: Vec::new(),
            start_ms: 0,
            last_ms: 0,
        }
    }

    /// The `push` method stages `bytes` at `now_ms`.
    /// An empty slice returns `None` and leaves the quiet mark where it was. The first byte of a burst stores `now_ms` as the start. `now_ms` is also stored as the last byte time. A buffer of [`HOLD_CAP`] bytes or more returns the whole buffer. A start that is [`CAP_MS`] old or older returns the whole buffer. A quiet gap does not release inside this method.
    fn push(&mut self, bytes: &[u8], now_ms: u64) -> Option<Vec<u8>> {
        if bytes.is_empty() {
            return None;
        }
        if self.buf.is_empty() {
            self.start_ms = now_ms;
        }
        self.buf.extend_from_slice(bytes);
        self.last_ms = now_ms;
        if self.buf.len() >= HOLD_CAP || now_ms.saturating_sub(self.start_ms) >= CAP_MS {
            return Some(self.take());
        }
        None
    }

    /// The `poll` method releases the buffer when a cap has elapsed.
    /// An empty buffer returns `None`. The age cap is [`CAP_MS`] since the first byte. The quiet gap is [`QUIET_MS`] since the last byte. A full buffer also releases. The returned value is the whole buffer.
    fn poll(&mut self, now_ms: u64) -> Option<Vec<u8>> {
        if self.buf.is_empty() {
            return None;
        }
        let aged = now_ms.saturating_sub(self.start_ms) >= CAP_MS;
        let quiet = now_ms.saturating_sub(self.last_ms) >= QUIET_MS;
        if aged || quiet || self.buf.len() >= HOLD_CAP {
            return Some(self.take());
        }
        None
    }

    /// The `append` method joins `bytes` onto the buffer.
    /// The method does not release. The caller uses [`PaintHold::take`] for the immediate flush. The mux break uses this path.
    fn append(&mut self, bytes: &[u8]) {
        self.buf.extend_from_slice(bytes);
    }

    /// The `take` method returns the buffer and leaves it empty.
    /// The clock marks stay where they were.
    fn take(&mut self) -> Vec<u8> {
        std::mem::take(&mut self.buf)
    }

    /// The `is_empty` method reports an empty buffer.
    /// True means no paint is held.
    fn is_empty(&self) -> bool {
        self.buf.is_empty()
    }

    /// The `delay_ms` method returns the milliseconds until the next release.
    /// An empty buffer returns `None`. The value is the sooner of the remaining quiet gap and the remaining age cap. `Some(0)` means [`PaintHold::poll`] is due.
    fn delay_ms(&self, now_ms: u64) -> Option<u64> {
        if self.buf.is_empty() {
            return None;
        }
        let cap_left = CAP_MS.saturating_sub(now_ms.saturating_sub(self.start_ms));
        let quiet_left = QUIET_MS.saturating_sub(now_ms.saturating_sub(self.last_ms));
        Some(cap_left.min(quiet_left))
    }
}

/// The `arm_hold` function resets the paint sleep when a release is still in the future.
/// An empty hold returns false. A delay of 0 returns false. Any other delay resets `sleep` from the current instant plus that delay and returns true.
fn arm_hold(
    hold: &PaintHold,
    now_ms: u64,
    sleep: std::pin::Pin<&mut tokio::time::Sleep>,
) -> bool {
    let Some(delay) = hold.delay_ms(now_ms) else {
        return false;
    };
    if delay == 0 {
        return false;
    }
    sleep.reset(tokio::time::Instant::now() + Duration::from_millis(delay));
    true
}

/// The `stage_paint` function stages demux paint.
/// True means the UI is still listening. A release from `push` disarms the sleep and sends that buffer. A release from `poll` does the same. An empty hold disarms the sleep and returns true. A held burst arms the sleep and returns true. A failed send returns false.
fn stage_paint(
    hold: &mut PaintHold,
    paint: &[u8],
    now_ms: u64,
    paint_tx: &SyncSender<Vec<u8>>,
    sleep: std::pin::Pin<&mut tokio::time::Sleep>,
    armed: &mut bool,
) -> bool {
    if let Some(out) = hold.push(paint, now_ms) {
        *armed = false;
        return emit(paint_tx, out);
    }
    // An empty push leaves the quiet mark. If that mark is already due,
    // release now. Disarming here would leave the burst on the read thread.
    if let Some(out) = hold.poll(now_ms) {
        *armed = false;
        return emit(paint_tx, out);
    }
    if hold.is_empty() {
        *armed = false;
    } else {
        *armed = arm_hold(hold, now_ms, sleep);
    }
    true
}

/// The `end_link` function flushes the hold and sends `status`.
/// A failed paint send is ignored. A failed status send is ignored.
fn end_link(
    hold: &mut PaintHold,
    paint_tx: &SyncSender<Vec<u8>>,
    status_tx: &mpsc::Sender<&'static str>,
    status: &'static str,
) {
    let _ = emit(paint_tx, hold.take());
    let _ = status_tx.send(status);
}

#[cfg(test)]
/// These functions check the SSH session pump, the paint hold, and the command fold.
mod tests {
    use super::{fold_cmds, join_bounded, note_mux, plan_send, pump, Cmd, Demux, PaintHold, Pump};
    use std::collections::VecDeque;
    use std::sync::atomic::{AtomicBool, Ordering};
    use std::time::Duration;

    /// The `a_full_channel_keeps_the_key_in_front` test checks `pump`.
    /// A full step puts the first write back at the front. A sent step empties the queue. A closed step clears a later write.
    #[test]
    fn a_full_channel_keeps_the_key_in_front() {
        let mut queue = VecDeque::new();
        queue.push_back(Cmd::Write(b"a".to_vec()));
        queue.push_back(Cmd::Write(b"b".to_vec()));
        pump(&mut queue, |cmd| Pump::Full(cmd));
        assert_eq!(queue.len(), 2);
        match queue.front() {
            Some(Cmd::Write(bytes)) => assert_eq!(bytes, b"a"),
            _ => panic!("parked key"),
        }
        pump(&mut queue, |_cmd| Pump::Sent);
        assert!(queue.is_empty());
        queue.push_back(Cmd::Write(b"c".to_vec()));
        pump(&mut queue, |_cmd| Pump::Closed);
        assert!(queue.is_empty());
    }

    /// The `the_mux_flag_follows_the_init_answer` test checks `note_mux`.
    /// A waiting demux leaves the flag clear. Raw text `login: ` leaves the flag clear. A timeout leaves the flag clear. An init frame sets the flag. Channel A data `canvas` is kept as `Later` and is not painted. A broken frame leaves the flag set.
    #[test]
    fn the_mux_flag_follows_the_init_answer() {
        let flag = AtomicBool::new(false);
        let mut waiting = Demux::new();
        note_mux(&flag, &waiting);
        assert!(!flag.load(Ordering::Relaxed));

        let peeked = waiting.push(b"login: ");
        note_mux(&flag, &waiting);
        assert!(waiting.is_raw());
        assert!(!peeked.is_empty());
        assert!(!flag.load(Ordering::Relaxed));

        let mut timed = Demux::new();
        let _ = timed.timed_out();
        note_mux(&flag, &timed);
        assert!(timed.is_raw());
        assert!(!flag.load(Ordering::Relaxed));

        let mut muxed = Demux::new();
        let paint = muxed.push(&crate::amwp::init_frame());
        note_mux(&flag, &muxed);
        assert!(muxed.is_mux());
        assert!(paint.is_empty());
        assert!(flag.load(Ordering::Relaxed));

        let scope_a = crate::amwp::encode(
            crate::amwp::CHANNEL_A,
            crate::amwp::TYPE_DATA,
            0,
            b"canvas",
        )
        .expect("scope a fits");
        let painted = muxed.push(&scope_a);
        assert!(painted.is_empty());
        assert_eq!(
            muxed.take_scope_a(),
            vec![crate::amwp::ScopePiece::Later {
                cell: [0u8; 16],
                body: b"canvas".to_vec(),
            }]
        );
        assert!(muxed.is_mux());

        let mut bad = vec![0u8; 16];
        bad[0] = 0x00;
        muxed.push(&bad);
        assert!(muxed.is_broken());
        note_mux(&flag, &muxed);
        assert!(flag.load(Ordering::Relaxed));
    }

    /// The `a_burst_keeps_the_latest_positive_size_and_stops_at_disconnect` test checks `fold_cmds`.
    /// A zero axis is dropped. The last positive size is 100 by 40. An empty write is dropped. A write after disconnect is ignored. The disconnect flag is true.
    #[test]
    fn a_burst_keeps_the_latest_positive_size_and_stops_at_disconnect() {
        let burst = fold_cmds(vec![
            Cmd::Resize { cols: 80, rows: 24 },
            Cmd::Resize { cols: 0, rows: 10 },
            Cmd::Write(b"ab".to_vec()),
            Cmd::Resize { cols: 100, rows: 40 },
            Cmd::Write(Vec::new()),
            Cmd::Disconnect,
            Cmd::Write(b"no".to_vec()),
        ]);
        assert_eq!(burst.resize, Some((100, 40)));
        assert_eq!(burst.writes, vec![b"ab".to_vec()]);
        assert!(burst.wires.is_empty());
        assert!(burst.disconnect);
    }

    /// The `a_wire_frame_stays_out_of_the_terminal_writes` test keeps a cancel frame out of the terminal writes.
    /// The write `hi` is wrapped as Scope T when the mux flag is true. The cancel frame is copied as itself. A wire after disconnect is ignored.
    #[test]
    fn a_wire_frame_stays_out_of_the_terminal_writes() {
        let frame = crate::amwp::cancel_frame(&[0x22; 16]);
        let burst = fold_cmds(vec![
            Cmd::Write(b"hi".to_vec()),
            Cmd::Wire(frame.clone()),
            Cmd::Disconnect,
            Cmd::Wire(b"late".to_vec()),
        ]);
        assert_eq!(burst.writes, vec![b"hi".to_vec()]);
        assert_eq!(burst.wires, vec![frame.clone()]);
        let planned = plan_send(true, &burst).expect("plan");
        assert_eq!(planned.len(), 2);
        assert_eq!(planned[0], crate::amwp::scope_t_data(b"hi").unwrap());
        assert_eq!(planned[1], frame);
    }

    /// The `a_zero_size_keeps_the_last_positive_size` test drops a 0 by 0 resize.
    /// The size stays 80 by 24. The disconnect flag is false.
    #[test]
    fn a_zero_size_keeps_the_last_positive_size() {
        let burst = fold_cmds(vec![
            Cmd::Resize { cols: 80, rows: 24 },
            Cmd::Resize { cols: 0, rows: 0 },
        ]);
        assert_eq!(burst.resize, Some((80, 24)));
        assert!(!burst.disconnect);
    }

    /// The `join_bounded_returns_for_a_short_sleeper_and_reports_a_stuck_one` test checks the join bound.
    /// A thread that sleeps 30 milliseconds joins inside 2 seconds. A thread that sleeps 400 milliseconds returns `read thread did not stop` after 30 milliseconds.
    #[test]
    fn join_bounded_returns_for_a_short_sleeper_and_reports_a_stuck_one() {
        let quick = std::thread::spawn(|| std::thread::sleep(Duration::from_millis(30)));
        assert!(join_bounded(quick, Duration::from_secs(2)).is_ok());
        let stuck = std::thread::spawn(|| std::thread::sleep(Duration::from_millis(400)));
        assert_eq!(
            join_bounded(stuck, Duration::from_millis(30)),
            Err("read thread did not stop")
        );
    }

    /// The `quiet_holds_seven_ms_and_releases_at_eight` test checks the quiet gap.
    /// A push at 0 holds `hi`. A poll at 7 holds it. A poll at 8 returns `hi`. A later poll at 8 returns `None`.
    #[test]
    fn quiet_holds_seven_ms_and_releases_at_eight() {
        let mut hold = PaintHold::new();
        assert!(hold.push(b"hi", 0).is_none());
        assert!(hold.poll(7).is_none());
        assert_eq!(hold.poll(8).as_deref(), Some(&b"hi"[..]));
        assert!(hold.poll(8).is_none());
    }

    /// The `empty_paint_does_not_move_the_quiet_mark` test pushes an empty slice.
    /// The empty push does not move the quiet mark. A poll at 8 still returns `ab`.
    #[test]
    fn empty_paint_does_not_move_the_quiet_mark() {
        let mut hold = PaintHold::new();
        assert!(hold.push(b"ab", 0).is_none());
        assert!(hold.push(b"", 7).is_none());
        assert_eq!(hold.poll(8).as_deref(), Some(&b"ab"[..]));
    }

    /// The `the_cap_releases_at_sixteen_even_when_bytes_keep_arriving` test checks the age cap.
    /// Pushes at 0, 7, and 14 stay held. A poll at 15 stays held. A poll at 16 returns `abc`.
    #[test]
    fn the_cap_releases_at_sixteen_even_when_bytes_keep_arriving() {
        let mut hold = PaintHold::new();
        assert!(hold.push(b"a", 0).is_none());
        assert!(hold.push(b"b", 7).is_none());
        assert!(hold.push(b"c", 14).is_none());
        assert!(hold.poll(15).is_none());
        assert_eq!(hold.poll(16).as_deref(), Some(&b"abc"[..]));
    }

    /// The `a_push_on_the_cap_releases_the_whole_burst` test releases inside `push`.
    /// A second push at 16 returns `ab`. The hold is empty afterward.
    #[test]
    fn a_push_on_the_cap_releases_the_whole_burst() {
        let mut hold = PaintHold::new();
        assert!(hold.push(b"a", 0).is_none());
        assert_eq!(hold.push(b"b", 16).as_deref(), Some(&b"ab"[..]));
        assert!(hold.take().is_empty());
    }

    /// The `eight_kib_releases_once_including_a_larger_chunk` test releases at the size cap.
    /// A push of 8192 bytes returns that chunk. A push of 8242 bytes returns that larger chunk in one buffer.
    #[test]
    fn eight_kib_releases_once_including_a_larger_chunk() {
        let mut hold = PaintHold::new();
        let big = vec![b'x'; 8 * 1024];
        assert_eq!(hold.push(&big, 0).as_deref(), Some(big.as_slice()));
        let mut hold = PaintHold::new();
        let bigger = vec![b'y'; 8 * 1024 + 50];
        let out = hold.push(&bigger, 5).expect("one release");
        assert_eq!(out, bigger);
        assert!(hold.take().is_empty());
    }

    /// The `the_size_cap_joins_the_tail_and_releases_once` test joins a tail onto a held head.
    /// The head is 8000 bytes. The tail is 300 bytes. The one release is 8300 bytes. The hold is empty afterward.
    #[test]
    fn the_size_cap_joins_the_tail_and_releases_once() {
        let mut hold = PaintHold::new();
        let head = vec![b'h'; 8000];
        assert!(hold.push(&head, 0).is_none());
        let tail = vec![b't'; 300];
        let out = hold.push(&tail, 1).expect("joined");
        assert_eq!(out.len(), 8300);
        assert_eq!(&out[..8000], head.as_slice());
        assert_eq!(&out[8000..], tail.as_slice());
        assert!(hold.take().is_empty());
    }

    /// The `one_byte_under_the_size_cap_stays_until_quiet` test holds 8191 bytes.
    /// A poll at 7 stays held. A poll at 8 returns those 8191 bytes.
    #[test]
    fn one_byte_under_the_size_cap_stays_until_quiet() {
        let mut hold = PaintHold::new();
        let almost = vec![b'z'; 8 * 1024 - 1];
        assert!(hold.push(&almost, 0).is_none());
        assert!(hold.poll(7).is_none());
        assert_eq!(hold.poll(8).expect("quiet").len(), 8 * 1024 - 1);
    }

    /// The `a_later_burst_starts_a_new_window` test starts a new age after a release.
    /// The first burst releases at 8. The next burst starts at 9. A poll at 16 stays held. A poll at 17 returns `two`.
    #[test]
    fn a_later_burst_starts_a_new_window() {
        let mut hold = PaintHold::new();
        assert!(hold.push(b"one", 0).is_none());
        assert_eq!(hold.poll(8).as_deref(), Some(&b"one"[..]));
        assert!(hold.push(b"two", 9).is_none());
        assert!(hold.poll(16).is_none());
        assert_eq!(hold.poll(17).as_deref(), Some(&b"two"[..]));
    }

    /// The `take_flushes_immediately_and_leaves_the_hold_empty` test flushes with `take`.
    /// The first take returns `zz`. The next take is empty. A poll at 100 returns `None`.
    #[test]
    fn take_flushes_immediately_and_leaves_the_hold_empty() {
        let mut hold = PaintHold::new();
        assert!(hold.push(b"zz", 3).is_none());
        assert_eq!(hold.take(), b"zz");
        assert!(hold.take().is_empty());
        assert!(hold.poll(100).is_none());
    }

    /// The `append_joins_a_held_burst_for_an_immediate_flush` test joins bytes before `take`.
    /// The held text is `held`. The appended text is `tail`. The take returns `heldtail`.
    #[test]
    fn append_joins_a_held_burst_for_an_immediate_flush() {
        let mut hold = PaintHold::new();
        assert!(hold.push(b"held", 4).is_none());
        hold.append(b"tail");
        assert_eq!(hold.take(), b"heldtail");
    }

    /// The `two_chunks_under_the_caps_stay_one_buffer` test keeps three pushes in one buffer.
    /// The pushes are `ESC[J`, `prompt`, and `hello`. A poll at 113 stays held. A poll at 114 returns `ESC[Jprompthello`.
    #[test]
    fn two_chunks_under_the_caps_stay_one_buffer() {
        let mut hold = PaintHold::new();
        assert!(hold.push(b"ESC[J", 100).is_none());
        assert!(hold.push(b"prompt", 103).is_none());
        assert!(hold.push(b"hello", 106).is_none());
        assert_eq!(hold.poll(113).as_deref(), None);
        assert_eq!(
            hold.poll(114).as_deref(),
            Some(&b"ESC[Jprompthello"[..])
        );
    }

    /// The `delay_is_the_sooner_of_quiet_and_cap` test checks `delay_ms`.
    /// An empty hold returns `None`. After a push at 10 the delay is 8. At 14 the delay is 4. At 20 the delay is 0. A later push at 18 makes the delay 8 again. At 24 the delay is 2. A poll at 26 releases. The delay is then `None`.
    #[test]
    fn delay_is_the_sooner_of_quiet_and_cap() {
        let mut hold = PaintHold::new();
        assert_eq!(hold.delay_ms(0), None);
        assert!(hold.push(b"a", 10).is_none());
        assert_eq!(hold.delay_ms(10), Some(8));
        assert_eq!(hold.delay_ms(14), Some(4));
        assert_eq!(hold.delay_ms(20), Some(0));
        assert!(hold.push(b"b", 18).is_none());
        assert_eq!(hold.delay_ms(18), Some(8));
        assert_eq!(hold.delay_ms(24), Some(2));
        assert!(hold.poll(26).is_some());
        assert_eq!(hold.delay_ms(26), None);
    }
}
