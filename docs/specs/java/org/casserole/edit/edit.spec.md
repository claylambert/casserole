# org.casserole.edit

**Depends:** This container names no other spec.

## 1. Goal

While a session is open, Ctrl-Z undoes the last text change in the focused canvas prompt or in the SSH line being typed, and Shift-Ctrl-Z redoes it. Each cell has its own stack. Each SSH message slot has its own stack. A restart forgets the stacks.
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163
**semantic-key:** PK-1163

## 3. The cause

The canvas prompt is a text area that is rebuilt with the card. An undo edit bound to that document dies with the card. The SSH line is not a Swing document. It lives in ChatShell. A byte 0x1A was ignored. Shift-Ctrl-Z had no sequence. The clients had to send a chord the shell could recognize without changing the keys that already do work.
**semantic-key:** PK-341

## 4. The seat

EditMemory is the container. It wraps UndoManager. The limit is 100. An edit stores the text and the cursor before the change, and the text and the cursor after it. note pushes when the text differs. The same text only moves the cursor. undo and redo swap that stored text. The caller writes text and cursor into the widget that is live. applying stops the listener from pushing the write it just applied. sync remembers a rebuilt widget without pushing. discard drops the stack.

A canvas cell id maps to one EditMemory. The map is RAM. A deleted cell drops its seat. Clear turns drops the seats of the cells that left. The prompt binds Ctrl-Z and Shift-Ctrl-Z while it is focused. The initial text is synced, not recorded. Import through setText is one step.

ChatShell keeps a draft seat and a visit seat. A history hop discards the visit stack and syncs the canonical line. The draft stack stays parked with the draft. The first text change that leaves history mode adopts the visit seat as the draft seat. Enter and bare Escape drop both stacks. Ctrl-Z is byte 0x1A. Redo is ESC [ 1 ; 6 Z. A paste is one step, recorded when the paste ends. The history file does not store the stacks.
**semantic-key:** PK-342

The Java terminal writes those bytes only when the terminal is focused, so a canvas prompt keeps its own chord. Rust canvas undo is a sibling seat of previous strings, keyed by cell, limit 100. It applies before the prompt paint and records at the writeback. Rust SSH sends Ctrl-Z and ESC [ 1 ; 6 Z through the key table.

The public type is EditMemory. The package is org.casserole.edit. No new public method on the notebook or the shell.
