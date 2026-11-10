# org.casserole.chat

Component file. It carries how this package is built.

**Depends:** [java/org/casserole/mood/mood.spec.md](../mood/mood.spec.md), [java/org/roux/crypto/crypto.spec.md](../../roux/crypto/crypto.spec.md), [java/org/roux/id/id.spec.md](../../roux/id/id.spec.md), [java/org/roux/model/model.spec.md](../../roux/model/model.spec.md), [java/org/roux/roux.spec.md](../../roux/roux.spec.md), [java/org/roux/session/session.spec.md](../../roux/session/session.spec.md), [java/org/roux/spill/spill.spec.md](../../roux/spill/spill.spec.md), [java/org/roux/spring/spring.spec.md](../../roux/spring/spring.spec.md), [java/org/roux/tel/tel.spec.md](../../roux/tel/tel.spec.md), [java/org/roux/token/token.spec.md](../../roux/token/token.spec.md)

# The spinner paints an emerald frame and a cyan thinking word on the current line, and stop erases it.

While the turn is in flight:

- The timer paints a carriage return, an emerald `#00FF66` frame, a space, the word `thinking` in `#00D7FF`, and erase to end of line.
- ASCII frames are `|`, `/`, `-`, and `\`.
- Braille starts at U+280B and cycles. It needs a font with those glyphs. Consolas does not have them.
- Stop is a carriage return plus erase, so the spinner does not become a committed line.

An earlier bug wrote `thinking...` as a normal line and then could not overwrite it. The carriage return plus erase design is required.

`none` skips animation. The frame interval is `spinner-ms`, default 100, clamped from 40 to 500.

This capture's word color is `#00D7FF`. A later notebook spinner uses a different blue. Do not merge them here.

**Truth.** Glyph `#00FF66`. Word `thinking` in `#00D7FF`. Frames `| / - \`. Stop erases. `none` skips.

**Reason.** A committed thinking line could not be overwritten and became part of the scrollback.

**Avoidances.** Do not write a committed thinking line. Do not use braille as the default. Do not replace `#00D7FF` with a later blue inside this capture.


**semantic-key:** PK-0549 PK-0550

# The header follows the spinner, the prompt is medium blue, and find-by is an argument of the existing find.

Edit:

- `Ansi` gains dim gray, a brighter gray, medium blue at about 32 or 33, `kv` with a green key and a hot-pink value, and box-drawing helpers.
- `ChatShell.runLlmTurn`, after `stopSpinner`, writes a two-row header at `terminalColumns`, default 80, then the existing reply color. Cancel, empty, or error: no header. Commands: no header. Truncate the model if the label is longer than `columns - 4`. Pad `─` to the width. The timestamp is `yyyy-MM-dd HH:mm:ss`, local to the JVM, at write time. The model is the live `modelName`. The example model string used by the design is `qwen2.5-coder:7b`.
- `ChatShell.promptText`. Command mode is a medium-blue `cmd> `. Typed characters stay green.
- The line editor on `ChatShell`. `editCursor`. Insert at the cursor. Backspace deletes left. CSI `3~` deletes at the cursor. CSI `C`, `D`, `H`, `F`, `1~`, and `4~`. Ctrl+A and Ctrl+E. After a change, `redrawInputLine()` plus N backspaces. A sealed paste is unchanged. Up and Down still land at the end of the line unless the operator moves.
- `StatusCommand`. Each `key: value` goes through `Ansi.kv`. No yellow wrap.
- `MessageListCommand`. Parse `verbose` the way `meta` is parsed. Default truncate to `list-preview-chars`. `0` means full. `message list meta` is terse too.
- `MessageFindCommand`. If the arguments start with `by`, the remainder is the needle and one quote pair is stripped. Case-insensitive contains. Print every hit terse, `[seq] ROLE: preview`. Otherwise the existing high id and low id. Bad usage lists both spellings.
- `HelpCommand` one-liners pick up the new help strings automatically.

Done: compile. A plain terminal hand check covers the reply box, Escape to a blue `cmd>`, `status` colors, a truncated `message list`, a full `verbose`, `message find by`, and left and right editing.

**Truth.** Header after the spinner, blue `cmd>`, green typed text, editor sequences, status colors, terse list, find-by.

**Reason.** These are the server half of the locked design, and they do not need the window.

**Avoidances.** Do not banner a failure. Do not yellow-wrap status. Do not drop the id form of find. Do not append when the cursor is mid-line.


**semantic-key:** PK-0554

# The header is written after the spinner stops, and a failed turn has no header.

- The spinner sits on the prompt line, carriage return plus the frame plus erase-to-end. The header comes after `stopSpinner`, or it overwrites the thinking line.
- A resize mid-session uses `terminalColumns` at write time. That width is already parsed from the terminal environment. If the column count never arrived, use 80.
- A very long model id truncates the model. Never wrap the box onto a third line into the reply.
- A failed, empty, or cancelled turn has no header, because nothing was received.
- Command output, including `status` and `message list`, has no header.
- The timestamp is server-local. Two hosts will disagree. The proof is one host.

**Truth.** Six seals: after the spinner, width at write time, no third line, no header on failure, no header on commands, one host's clock.

**Reason.** Writing the box too early erases the thinking line, and wrapping it corrupts the reply.

**Avoidances.** Do not write the header before the spinner stops. Do not banner a failure. Do not assume two hosts share a clock.


**semantic-key:** PK-0555

# After a successful complete, and after the spinner stops, a dim-gray box is written at the live column width.

After a successful model complete, after `stopSpinner`, and before the body, write a dim-gray box-drawing header, then the existing body, then `chat>`.

Width is the current `terminalColumns`. The default is 80. Dim gray is `ESC[90m`, or 256-color about 245, for the drawing. Inner text is a slightly brighter gray. Then reset.

The box the operator sees is two rows. The first row opens with `┌`, a space-dash pair, the stamp, a middle dot, the model id, dashes, and `┐`. The second row is `└`, dashes, and `┘`. Both rows are exactly `terminalColumns` wide. The sample stamp is `2026-08-26 14:32:05`. The sample model is `qwen2.5-coder:7b`. Under the box is the formatted reply, the same yellow and smart-format as today, then `chat>`.

| Item | Lock |
|---|---|
| When | The message was received. The wall clock is the moment the assistant reply is about to be written. That clock is the host JVM's local time, not a frame timestamp, and not the client clock. |
| Stamp format | `yyyy-MM-dd HH:mm:ss`. No zone. This is a proof format. |
| Model | The live model id already on the shell. A load updates it. The same string `status` uses. Do not invent `qwen_coder`. |
| Glyphs | Box drawing `┌ ┐ └ ┘ ─ ·`. If the inner label is longer than `columns - 4`, truncate the model and keep the timestamp. Pad `─` so both rows are exactly `terminalColumns`. |
| Fallback | If ASCII is required: `+-- 2026-08-26 14:32:05 · model --+` and a second row of `+` and dashes. The terminal font has the box glyphs. The tofu risk is the same as the braille spinner. |
| Who | Model replies only. Commands are unbannered. |
| Body | Unchanged. The existing reply color and format router still run. The chrome is not wrapped in yellow. |

**Truth.** Dim gray `ESC[90m`, width `terminalColumns` default 80, stamp `yyyy-MM-dd HH:mm:ss`, model truncated first, both rows exact.

**Reason.** The header is receipt chrome. It is not part of the yellow body.

**Avoidances.** Do not use a frame timestamp or the client clock. Do not wrap the box onto a third line. Do not truncate the timestamp. Do not yellow-wrap the chrome.


**semantic-key:** PK-0556

# Today the shell stops the spinner, writes the yellow assistant blob, and then writes chat>.

`ChatShell.runLlmTurn` stops the spinner, writes the yellow-formatted assistant blob, then `chat>`.

**Truth.** The current path is spinner, yellow body, then the chat prompt.

**Reason.** The header has to be inserted into that path without becoming part of the yellow body.

**Avoidances.** Do not leave the header out of the description of today. Today has no header.


**semantic-key:** PK-0557
