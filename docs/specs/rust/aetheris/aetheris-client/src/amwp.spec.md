# rust.aetheris.aetheris-client.src.amwp

## 3. Edits

**Frame.** A set calls `applyConsoleTarget`, then writes `model `, the label, and CRLF. A read does not write that line. The result is `shell_model_result:` plus `consoleModelBody`.
**semantic-key:** PK-570

### 5.1 Why not two layouts

- **Frame** = one append record inside a page. Payload = TOON UTF-8 for that item.
**semantic-key:** PK-571

## 7. P4. A bad frame

3. **Leave the page CRC out of this decision.** A torn tail can fail the page CRC and still have a good prefix. Frame CRC is the scan's truth. The page CRC stays the checksum `publishPage` writes. A later audit can read it. This scan does not drop a page for it.
**semantic-key:** PK-572

## 9. What a named build changes

- Frame CRC, length, and seq still stop that page. The good prefix stays. The region opens short.
**semantic-key:** PK-573

## 4. Locator (`seq`) — unchanged job

Frame header: keep `seq` at `OFF_MESSAGE_ID`. Append `idHi` / `idLo` after the existing 36-byte header **or** in a reserved tail before TOON. CRC still covers the payload; include the new words in the frame length.
**semantic-key:** PK-574

## 2. The canvas

`Demux::push` still returns Scope T bytes for the grid. A channel A body stays off that paint.
**semantic-key:** PK-575

### Palette

| Place | Ink |
|---|---|
| Frame, split | `#1e1e1e` |
| Panel | `#2d2d2d` on `#dcdcdc` |
| Field | `#1a1a1a` on `#e6e6e6` |
| Button | `#3c3c3c` on `#f0f0f0` |
| Status | `#171717` on `#b4b4b4` |
| Terminal | `#0d0d0d` on `#dcdcdc` |
| Selection | `#345a8c` on `#ffffff` |
| Cursor | `#9900d7ff`, inactive `#664080a0` |
| Menu | `#00ff41` |
| Chat prompt, cmd prompt | `#00c853`, `#2196f3` |
| Scrollbar | face `#C0C0C0`, arrow black |
| Canvas input, output | `#00ff66`, `#ffff55` |
| Sapphire edge | `#2e86ff` |
| Medium green, reply gray | `#3cb371`, `#666666` |
| Inspector | `#121212` on `#dcdcdc` |

## 2. The pipe

`Demux::push` still returns Scope T bytes for the grid. A channel A body is kept beside that, and the grid does not receive it.
**semantic-key:** PK-576

## 2. The gate

The gate is the demux. `note_mux` stores true on the shared flag only when `Demux::is_mux` is true, after `push`. A wait leaves the flag clear. A raw PTY leaves the flag clear. Init timeout calls `timed_out`, the demux becomes raw, and the flag stays clear. The console still works on that raw PTY.
**semantic-key:** PK-577

The [`SetLine`] struct is one working-set line. `role` is `user` or `assistant`. `text` is the line. `head` is the canvas designation. A false `head` is absent on the wire.
**semantic-key:** PK-1173

Frame. 3. As-built (what the plan must not forget). The code tag is the next pass.
**semantic-key:** PK-1314

Demux. 2. Gaps from the Stage 0 code scan — **locked** `[PUFF]` 002 → `[LOCK]` 003. The code tag is the next pass.
**semantic-key:** PK-1395

Frame. 2. Gaps from the Stage 0 code scan — **locked** `[PUFF]` 002 → `[LOCK]` 003. The code tag is the next pass.
**semantic-key:** PK-1398

`ScopePiece` is a public type in this module.

`ExecuteTune` is a public type in this module.
