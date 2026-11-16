# rust.aetheris.aetheris-term.src.keys

**Depends:** [aetheris-term](../aetheris-term.spec.md)

## 9. Keys

The library defines its own key values. The window crate maps its events into those values. The library encodes bytes. The host writes the bytes on the connector.

`ChatShell.handleChar` and `ChatShell.handleCompleteEscape` are the host's table. The encoder matches it.
**semantic-key:** PK-633

| Key | Bytes | Host behavior |
|---|---|---|
| Enter | CR (`0x0D`) | Enter. A LF after a CR is swallowed by the host, so the widget sends CR only |
| Backspace | DEL (`0x7F`) | Deletes the character before the cursor. The host also accepts BS (`0x08`) |
| Left | `ESC [ D` | Moves one character left |
| Right | `ESC [ C` | Moves one character right |
| Ctrl+Left | `ESC [ 1 ; 5 D` | Moves one word left |
| Ctrl+Right | `ESC [ 1 ; 5 C` | Moves one word right |
| Esc | `ESC` (`0x1B`) | The host times a bare Esc. The library sends the byte and does not time it |

Text input is the UTF-8 of the committed characters.

The same encoder already knows the rest of the host table, so a later slice does not grow a second path.

| Key | Bytes |
|---|---|
| Up | `ESC [ A` |
| Down | `ESC [ B` |
| Home | `ESC [ H` |
| End | `ESC [ F` |
| Delete | `ESC [ 3 ~` |
| Ctrl+A | `0x01` |
| Ctrl+C | `0x03` |
| Ctrl+E | `0x05` |

Application cursor keys are a mode the host can set. `ChatShell` accepts both `ESC [ A` and `ESC O A`. The encoder sends CSI while that mode is off, and SS3 while it is on.

The library does not rebuild the host line editor. It does not implement `help`, `message list`, or `message find`.
