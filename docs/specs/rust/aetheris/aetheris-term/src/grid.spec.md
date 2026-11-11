# aetheris-term::grid

Component file. It carries how this package is built.

**Depends:** [rust/aetheris/aetheris-term/aetheris-term.spec.md](../aetheris-term.spec.md)

## What does not reflow

The alternate screen still pads or clips. A zero size keeps the last positive size. The host still hears the new size. The other client keeps the input line as it stands. The history lives in the grid. This plate does not change the folded row.

The terminal-library check counted 28 passes. The client check counted 188 passes. The window stayed closed. The check did not start a session and it did not open the window.

**Truth.** A column change reflows the primary screen: soft wraps join, the text wraps to the new width, a hard newline stays a break, a wide glyph stays whole with its spacer, and color, bold, and red stay. Overflow enters scrollback and is still painted. A last-row cursor stays on the last row. The alternate screen still pads or clips. A zero size keeps the last positive size. The host still hears the new size.

**Reason.** The grid padded or clipped, so a narrower column cut the glyphs off the line instead of wrapping them.

**Avoidances.** Do not pad or clip the primary screen on a column change. Do not drop a glyph. Do not turn a hard newline into a soft wrap. Do not split a wide glyph or drop its spacer. Do not drop color, bold, or red. Do not hide the lines that moved into scrollback. Do not move a last-row cursor off the last row. Do not reflow the alternate screen. Do not apply a zero size. Do not hide the new size from the host. Do not change the folded row. Do not move the history out of the grid.

**semantic-key:** PK-6915

## Paste mode

The binary is `aetheris-client`. The conversational stand-up is the VT100 behavior slice. The grid stays the VT100 pane. Private mode 2004 grows a flag the host can read, so a paste can be bracketed. Other private modes stay consumed with effect off. This account writes no crate.

**Truth.** The core is synchronous and runtime-free. `vte` parses. The grid, the cursor, the pen, the ring, and the parser have one owner. `feed` borrows that state and returns. A cell owns its character, its width, and its color. The host paints. The host encodes keys and writes them. Private mode 2004 is a flag. The first thread is one reader and a bounded queue. A burst coalesces for 8 ms of quiet, or 16 ms from the first byte, or 8 KiB, then one `feed` and one paint.

**Reason.** A core that starts a runtime cannot sit inside a host that already picked one, and a hand-rolled parser is the part that desyncs a shell.

**Avoidances.** Do not name an Aetheris type. Do not fork the other client's shell specs. Do not implement `help`, `message list`, or `message find`. Do not rebuild the host line editor. Do not time Esc in the library. Do not start a runtime or spawn a thread in the core. Do not take a lock inside the core. Do not let a cell point into the read buffer. Do not hand-roll the parser. Do not treat an 8-bit C1 control as part of this parser. Do not reflow on resize. Do not play a bell. Do not record an OSC title. Do not put deleted lines into the scrollback. Do not let a combining mark grow a cell without the small cap. Do not speak channel windows. Do not depend on `iced`, `egui`, or `winit`. Do not write a crate file before a sentence names the build. Do not publish. Do not pin a version on this page. Do not add a second runtime for the jitter. Do not paint inside the read of one host redraw.


**semantic-key:** PK-6945
