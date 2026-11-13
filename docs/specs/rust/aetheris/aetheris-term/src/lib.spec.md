# rust.aetheris.aetheris-term.src.lib

**Depends:** This container names no other spec.

## 6. Ownership

The terminal state owns the grid, the cursor, the pen, the scrollback ring, and the parser. `feed` takes a byte slice, borrows the state for that call, and returns. The host paints from a shared borrow when `feed` is not running.

A cell stores an owned character, a column width, and a color. It does not store a pointer into the read buffer. The buffer dies at the end of the call.

The host calls `feed` on one task. Paint runs when that task is between calls. Bytes from another task move to the owner, and the owner calls `feed`. A lock, when a host cannot serialize those calls, stays outside the core. The core does not take it.
