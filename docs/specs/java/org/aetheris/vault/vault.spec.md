# org.aetheris.vault

Component file. It carries how this package is built.

**Depends:** [java/org/aetheris/config/config.spec.md](../config/config.spec.md), [java/org/roux/session/session.spec.md](../../roux/session/session.spec.md)

## Delete removes the focused turn, renumbers the survivors from 1, and stays off for one turn or a grouped member.

Delete one turn from the canvas that is showing. The turns that remain take new labels from 1, in canvas order, with no gap. The label on the card is the stored turn index. New turn numbers the next card from the count of cells, so a gap would reuse a label that is still on screen.

Delete sits on the original footer, in the main button set, on the near side of the divider, just before that divider. It deletes the focused turn. With one turn on the canvas the button is disabled. It is enabled when two or more turns are on the canvas and the focused turn is not a grouped member. A grouped member is a turn with a group and a column position above 0. That turn cannot be deleted. There is no confirm dialog. Pipe tokens stay as the user wrote them.

The turn is one row in the cell table. That row holds the prompt, the reply, the state, the burner flag, the collapse flag, the target, the group, the status, the parent pointer, and the turn index. A click removes that cell row, drops the id from the canvas order, stores turn indexes from 1 on the rows that remain, and removes trace rows for that id. No tool writes a trace today. The tables have no foreign key, so the delete names each of those writes. Another canvas is another set of rows and stays. The walker does not read a parent pointer. A survivor that stored the deleted id keeps that string. Import rewrites a parent pointer. The walker still does not read it.

The merge sink is a different row: column position 0 with the same group. The rule does not turn the button off when the sink is the focused turn. A click on the sink does not remove it. That case stays open on this rule.

A later rule closes that open case. Arming any card of that group, member or merge, removes the whole group and the merge together. A single member stays with its siblings until that set leaves. The sentences above stay the record of the earlier build.

A layout probe at font 19 in a 1280 by 800 window found Delete between the tool-bar `v` and the divider. The divider was 9 by 22. One turn left the button off. Three turns left it on. Deleting the middle turn left indexes 1 and 2. The pipe token stayed on the later turn. The parent pointer on the first turn still named the deleted id. Trace rows for that id were gone. The other canvas stayed. A grouped member left the button off. A sequential turn beside that group left it on. A click on the merge sink left the sink in place. The labels stayed Turn 1, Turn 3, and Turn 2. The window was not opened by hand. The probe showed a frame and disposed it.

**Truth.** Delete sits just before the footer divider, removes the focused cell row, renumbers survivors from 1, and drops that id's trace rows. One turn, or a grouped member, leaves the button off. There is no confirm dialog. A click on the merge sink left the sink in place and the labels stayed Turn 1, Turn 3, and Turn 2. A later rule removes the whole group, members and merge, when any card of that group is armed.

**Reason.** Labels are the stored turn index, and New turn numbers from the cell count, so a gap would reuse a label still on screen. The delete names each write because the tables have no foreign key. Pipe tokens stay as written so a later prompt is not rewritten. A parent pointer stays stored because the walker does not read it, while import still rewrites one. The merge sink stayed open on this rule because the member test does not match column position 0. The later rule closes that case so a group leaves as one set.

**Avoidances.** Do not confirm the delete. Do not delete a grouped member from this button. Do not delete the last remaining turn. Do not rewrite pipe tokens. Do not clear a survivor's stored parent pointer. Do not touch another canvas. Do not treat the earlier sink click as a removal. Do not drop the earlier sentences when the later group removal is read.


**semantic-key:** PK-3849 PK-3850 PK-3851 PK-3852 PK-3853 PK-3854 PK-3855 PK-3856 PK-3857

## The armed card's rectangle is bright sapphire, RGB 46, 134, 255.

The armed card's rectangle was amber, RGB 200, 162, 42. It is now a bright sapphire, RGB 46, 134, 255. Pin green, a dim miss, and the ghost edge stay as they were.

The same probe counted one sapphire rectangle on the armed card and no amber rectangle. The window was not opened by hand.

**Truth.** The armed card paints one rectangle at RGB 46, 134, 255. The amber rectangle at RGB 200, 162, 42 is gone. Pin green, a dim miss, and the ghost edge are unchanged.

**Reason.** The armed edge changed from amber to sapphire so the selection is the bright blue and not the old gold.

**Avoidances.** Do not paint the armed rectangle amber. Do not change pin green, a dim miss, or the ghost edge. Do not leave an amber rectangle on the armed card.


**semantic-key:** PK-3858 PK-3859 PK-3860 PK-3861 PK-3862 PK-3863 PK-3864 PK-3865 PK-3866

# The cell record stores `collapsed` default false, the table adds the column idempotently, and the flag rides insert, update, and load.

- `CellRecord.collapsed`, default false. `newCell` leaves it false.
- The cell definition lists `collapsed BOOLEAN NOT NULL DEFAULT FALSE`.
- On open, run an idempotent add for a disk vault whose table predates the column. A failed duplicate add is success.
- `INSERT_CELL`, `UPDATE_CELL`, `SELECT_CELLS`, `insertCell`, `bindUpdate`, and `loadCells` carry the flag.
- A burner still stores null large-text columns. The flag itself is stored.
- Do not add a value to `CellState`.

The probe the plan uses is `INFORMATION_SCHEMA.COLUMNS` for table `cell` and column `collapsed`. Add only when the probe is empty.

**Truth.** Boolean default false, idempotent column add, flag on every read and write path, no new state value.

**Reason.** A burner with null text still has a collapse bit, or a rebuild would forget that the burner was closed.

**Avoidances.** Do not add an enum value. Do not skip the burner flag. Do not treat a duplicate add as a failure.


**semantic-key:** PK-4415 PK-4416

# This serial records dispositions and does not open a plan or move code.

This file is locked. It is the fallback. The live draft is the next tweak serial. Do not edit this file.

Mode: dispositions packed. Not a plan. Not a build. The next stage stays closed.

It absorbs the previous design serial, which is locked. It depends on the stages paper, the technical specification, the Stage 1 plan, and the Stage 1 build record.

Code a later plan has to stay compatible with: `org.aetheris.vault.CellState`, `org.aetheris.vault.CellRecord`, `org.aetheris.swing.AetherisNotebookPanel`, `org.casserole.chat.cmd.CommandProcessorFactory`, `org.casserole.chat.cmd.ClearCommand`, `org.casserole.chat.cmd.SwapCommand`, `org.casserole.ssh.shell.Ansi`.

The previous briefing was accepted. This serial records those dispositions. It does not open a plan. It does not move code. The plan starts when the operator names it.

Markup kinds: locked, accepted, tabled, and under review.

**Truth.** Dispositions only. No plan. No code. The next stage stays closed.

**Reason.** The acceptances needed a locked table before anyone wrote a plan.

**Avoidances.** Do not edit this file. Do not open a plan from it. Do not move code. Do not open the next stage.


**semantic-key:** PK-4422 PK-4423
