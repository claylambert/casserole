# org.casserole.ssh.shell

Component file. It carries how this package is built.

**Depends:** [java/org/aetheris/ssh/ssh.spec.md](../../../aetheris/ssh/ssh.spec.md), [java/org/aetheris/swing/swing.spec.md](../../../aetheris/swing/swing.spec.md), [java/org/aetheris/wire/wire.spec.md](../../../aetheris/wire/wire.spec.md), [java/org/casserole/chat/chat.spec.md](../../chat/chat.spec.md), [java/org/casserole/chat/cmd/cmd.spec.md](../../chat/cmd/cmd.spec.md), [java/org/casserole/config/config.spec.md](../../config/config.spec.md), [java/org/casserole/edit/edit.spec.md](../../edit/edit.spec.md), [java/org/casserole/format/format.spec.md](../../format/format.spec.md), [java/org/casserole/lamina/Lamina.spec.md](../../lamina/lamina.spec.md), [java/org/casserole/lamina/lamina.spec.md](../../lamina/lamina.spec.md), [java/org/casserole/llm/llm.spec.md](../../llm/llm.spec.md), [java/org/casserole/log/log.spec.md](../../log/log.spec.md), [java/org/casserole/mood/mood.spec.md](../../mood/mood.spec.md), [java/org/roux/session/session.spec.md](../../../roux/session/session.spec.md), [java/org/roux/spring/spring.spec.md](../../../roux/spring/spring.spec.md), [java/org/roux/token/token.spec.md](../../../roux/token/token.spec.md), [java/org/roux/toon/toon.spec.md](../../../roux/toon/toon.spec.md)

## SSH I/O logs the connect and the disconnect

Every SSH connect and disconnect on the host writes one INFO line. The password never appears.

`SshIoTrace` is package-private in `org.casserole.ssh.shell`. `SshServer.listenLoop` calls `SshIoTrace.attach` after the shell factory is set and before `sshd.start`. The password authenticator calls `SshIoTrace.authReject` when the account check returns false. The password is not a parameter of that call. Each line is INFO. Each line starts with `ssh-io`. The root logger is already INFO.

The events are `session-accept`, `session-open`, `authenticated`, `auth-reject`, `session-exception`, `session-disconnect`, `session-closed`, `channel-open`, `channel-open-failed`, and `channel-closed`. A trace fault in the listener logs `event=trace-fault` and does not throw. Key exchange stays quiet. Channel state changes stay quiet. Identification lines stay quiet.

Every line carries `event`, `session`, `user`, `remote-ip`, `remote-port`, `local-ip`, `local-port`, `client-version`, `server-version`, `iface-mac`, and `peer-mac`. A missing value is `absent`. The user is the session name, and on `auth-reject` it is the offered name. `iface-mac` is the hardware address of the server interface that owns the local socket. A wildcard bind, and a loopback with no hardware address, stay `absent`. `peer-mac` stays `absent`. The SSH session does not carry the peer hardware address. The clock starts at `session-open` and at channel init. `session-closed` and `channel-closed` carry `duration-ms`. `session-disconnect` carries the reason code, the reason text, the language, and initiator `local` or `peer`. `session-closed` repeats that disconnect tail. An exception line carries the exception class and a short text. A channel line carries `channel-id`, `channel-recipient`, `channel-class`, and `channel-type` when the TCP/IP channel exposes it.

The package methods are `attach` and `authReject`. The Swing client stays. The Rust client stays. The mux stays. Channel A stays. The command-line history stays. The model configuration stays. The log level stays.

**Truth.** Connect and disconnect events log at INFO as `ssh-io` lines with the fields above. A missing field is the word `absent`. `peer-mac` is always `absent`. The password is never a parameter and never a field. A trace fault logs `event=trace-fault` and does not throw. Key exchange, channel state, and identification stay quiet.

**Reason.** Connect and disconnect had no single INFO line that named the user, the sockets, the accepting interface, and how long the channel lived, so a failed login and a drop could not be traced without the password or the peer hardware address, neither of which the session has.

**Avoidances.** Do not log the password. Do not invent a peer hardware address. Do not throw from a trace fault. Do not log key exchange, channel state changes, or identification lines. Do not change the log level, the mux, channel A, or either client. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the model configuration or the Rust manifest.


**semantic-key:** PK-0506 PK-0507 PK-0508 PK-0509 PK-0510

## The server stop keeps the shutdown cycle

Stopping the server holds the shell group open, settles the Windows accept, then drains five seconds before the group shuts down.

A stop had been leaving at once. The work was dropped on the floor. The failure was an IllegalStateException, Executor has been shut down, thrown on InnocuousThread. The stack was NoCloseExecutor, then the Windows accept failure, then the IOCP handler. Before that drop, a shutdown took about 5 to 10 seconds.

The listener signals the listen loop, calls sshd.stop, then cancels the listen task. QuietNioService waits one second for the Windows accept failure, then holds the group five seconds, then the stock close runs. The executor stays open through that wait. The private classes are QuietNioFactory and QuietNioService. The private methods are settleAccept, drainGroup, and pause. ACCEPT_SETTLE_MS is 1000. GROUP_GRACE_MS is 5000.

Lamina, Roux, and PoolChat stay. sshd stays 2.19.0. A stop on localhost with no client, using that same settle and drain, took 6010 ms and did not print the executor exception.

The server carries this. The clients do not. No new package, no new public type, no new public method, no new vault column, no new channel, no new shell message type, no new color key, and no new library are added. No system line is added, removed, or reordered.

**Truth.** A server stop signals the listen loop, stops sshd, and cancels the listen task, while the executor stays open. The accept is given 1000 ms to fail, the group is held 5000 ms, and then the stock close runs. A localhost stop with no client took 6010 ms and did not throw Executor has been shut down. sshd stays 2.19.0.

**Reason.** Closing the executor before the Windows accept and the IOCP handler finished made the stop throw and skip the drain. The one-second settle covers the accept failure. The five-second hold is the group drain that the old stop used to spend, which is why a quiet localhost stop lands near 6010 ms instead of returning at once. One sshd.stop is enough.

**Avoidances.** The executor is not shut before the wait. Lamina, Roux, and PoolChat are not removed. sshd is not upgraded. No wire line is added. The clients are not edited.


**semantic-key:** PK-0511 PK-0512 PK-0513

# The server stop keeps the shutdown cycle

The full cycle is named, and the build is named. Java moves. Rust stays. The working word is NUT, and that word stays the owner's. No glossary row is minted for it. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0514 PK-0515 PK-0516

# The server stop keeps the shutdown cycle

The Casserole server shutdown cycle is asked back. The stop had been about 5 to 10 seconds. It was exiting at once. The thread InnocuousThread throws IllegalStateException. The message is Executor has been shut down. The stack runs through NoCloseExecutor and the Windows accept failure. Java moves. Rust stays. The working word is NUT, and that word stays the owner's. This page does not mint a glossary row. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-0523 PK-0524 PK-0525

## Chosen model

The VT100 console returns on the model chosen in Model Config. The choice survives an application restart.

The modal writes the chosen model into `~/.casserole/{user}/console-model` with the family word. The version stays 1. The lines are the Ollama id, the Grok id, think, effort, send effort, stream, temperature when it was set, max tokens when it was set, burner, and pool think. A missing file leaves the family at ollama and the model at `qwen2.5-coder:7b`. A file that carries only the family word still reads. A version other than 1 is left unread and is not overwritten. The dialog set writes the file. The shell word `model` writes the file. A Grok turn that turns send effort off writes the file. The next session reads the file before the first turn. The dialog still asks the shell, so the modal shows the stored model. Pool stays pool. An Ollama id stays that id. A Grok id stays that id. The server process that is already running keeps the shell it loaded. A new server process writes the file and reads it on the next session. The notebook strip stays on the bottom. The VT100 strip stays on the top.

The chosen model sits beside the console history, in the same file as the family word. The Java client carries this behavior. The Rust client still paints the character rule. This row writes no Rust crate. The crate change for this model waits until it is named. Nothing beside this model moves. The cells already on the canvas stay as they are. Use confirmed the chosen model.

**Truth.** Version 1 of `~/.casserole/{user}/console-model` stores the family word together with the Ollama id, the Grok id, think, effort, send effort, stream, temperature when set, max tokens when set, burner, and pool think. A missing file leaves the family at ollama and the model at `qwen2.5-coder:7b`. A family-only file still reads. A version other than 1 is left unread and is not overwritten. Dialog set, the shell word `model`, and a Grok turn that turns send effort off all write the file. The next session reads it before the first turn. An already-running server keeps the shell it loaded. A new server process writes the file and reads it on the next session. Pool stays pool. An Ollama id stays that id. A Grok id stays that id.

**Reason.** The family word was surviving, and the model chosen in Model Config was not. That chosen model has to come back after an application restart. Pool must stay pool, an Ollama id must stay that id, and a Grok id must stay that id.

**Avoidances.** Do not drop temperature or max tokens when they were set, and do not write them when they were not set. Do not refuse a file that carries only the family word. Do not read or overwrite a version other than 1. Do not reload an already-running server process from the file. Do not move the notebook strip or the VT100 strip. Do not put the chosen model in the vault. Do not write a Rust crate on this row.


**semantic-key:** PK-0675

## Layers

The layer map is the seat. A row here is a paging layer. Where an older section disagrees, this map wins, and the older sentences stay as the worked reasoning.

The surrounding product stages stay those plans. Version 1, the publish layer, is the live session: identity on the frame, one `pages.bin`, spill after force, a working set, durability default `commit`, a dual window, a turn atom, identity lookup, oversize rejection, and inspect. Close is an open weigh on the requirements page. The tree already runs this layer. This filing does not cut code. Clip range is its own page and is not one of these rows. The head rolls. The reel stays listable. Reattach is the scan. It runs when that region opens and restore is on. It does not scan at startup. Resume session is the row the design on this page still calls keep-on-hangup, a later layer. The tree already keeps the pack and restores the index and the identity map. The factory settings have `session.persist` and `session.restore` true. This filing does not move that row. Acknowledgement is a later policy that may wait for force and for a replica. The publish path stays the version 1 path. Continuation is held: the same identity, type `CONT`, and a reader that concatenates. The type byte stays. Dead-page prune releases a spilled page that no live index row names. The hot page stays. Page ids stay. The sweep runs at startup, when a region opens, and on `roux.prune.interval`. The default interval is 20 minutes. The build waits until it is named. It is not a renumber of the earlier rows. Out of the map: encryption as a product, copy-on-write snapshots, a console, a custom collector, mapped files as the primary pager, a node id in place of the two longs, eviction on add, summarize-and-drop, and a vector index.

The per-page temporary rename is the earlier picture. Version 1 publishes a slot in `pages.bin`. The per-page hot sidecar is the earlier sketch. Version 1 checkpoints the hot slot in `pages.bin` and does not unmap it.

**Truth.** Publish is one `pages.bin`, spill after force, resident working set, durability default `commit`. Reattach scans on open. Continuation stays held. Dead-page prune defaults to a 20 minute interval and is not yet built. The pack file replaces both the per-page rename and the hot sidecar.

**Reason.** One slot file is the address rule the rest of the map assumes. A startup scan would attach sessions nobody opened. Continuation and prune are named so they are not reinvented inside publish.

**Avoidances.** Do not renumber the rows. Do not scan every pack at startup. Do not implement continuation or the prune as part of publish. Do not publish by renaming `{pageId}.pg`. Do not checkpoint through a `{pageId}.hot` file. Do not unmap the hot page on checkpoint.


**semantic-key:** PK-3462 PK-3463

# Help returns to the main menu bar

The locked requirement binds this sketch. Where the sketch is thinner, that requirement wins.


**semantic-key:** PK-3794
