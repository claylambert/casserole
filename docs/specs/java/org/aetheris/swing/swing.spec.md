# org.aetheris.swing

## 12. Package 8. group fail

**Files:** `AetherisNotebookPanel`.

## 8. T6 — Focus hold

**File:** `AetherisNotebookPanel.rebuildTabs` and the header listeners that call it (`stateButton`, burner stays as it is if it does not rebuild, the new collapse toggle).
**semantic-key:** PK-28

## 9. T7 — Import

5. `JFileChooser` on the EDT. The read runs on `AetherisWorker`.
**semantic-key:** PK-29

## 10. T8 — Clear turns

2. Active canvas only. On `AetherisWorker`: delete `trace` rows whose `cell_id` is in that canvas, then delete those `cell` rows, then `addTurn` one empty `INCLUDED` cell, then save order.
**semantic-key:** PK-30

## 0. Locked answers this plan uses

| ID | Lock |
|---|---|
| N1 | After Run, the active canvas viewport goes to its maximum Y and the new turn's input takes focus. |
| S3 | New turn and Clear turns still scroll the whole card, which brings the top of that card into view. |
| S4 | A header click, collapse, and `IEPO` keep the focus hold. They do not jump to the end. |
| N2, S9 | The frame status bar names the focused connected user. Later status text keeps that name. |
| S12 | When focus is not a connected AMWP session, the status names no user and the pull-down has no selection. The last vault stays open. |
| N3, S7, S8 | One shell preference for chooser size and folder, shared by the VT100 send-file door and the Aetheris import door. Not per vault user. Not the `.aetheris` export door. |
| N4, S5, S6 | Incrementor on the west of the Aetheris button bar. Minimum 1 row. No ceiling. Restored when the app opens. |
| N5 | A short text rule between cards in the one inspector area. An included card keeps both role lines together. |
| N6 | Held. No kit is cut. Prod 1.0 stays frozen. |
| N7 | East capsule: pull-down on the west, spinner on the east. Choosing a name selects that VT100 session. The mux stays up. |
| S10 | Two panes with the same username are one entry. The vault does not reload. |
| S11 | A user change during a run drops the in-flight frame and stops the spinner. The session stays connected. |
| N8, S1, S2 | Spinner base `#4169E1`. Moving glyph steps `#00FF66`. The word `thinking` stays `#4169E1`. |
| N9 | Divider constant is 11. Open height is preferred panel height plus the live divider size. A sliver does not replace a saved open position. |
| Filed builds | This plan does not reopen them. U1's user-area scroll is the land path N1 replaces. |
| Models | `select model` and `list models` stay. |

## 3. N9 — Divider width

2. `applySplitPane` calls `setDividerSize(DIVIDER_SIZE)` after `setUI`, so the constant is the last write. It stays the only `setDividerSize` in the tree.
**semantic-key:** PK-31

## 7. N4 — Incrementor

1. The widget is `+`, `-`, and an editable integer. It sits in the west `FlowLayout`, with Run, New turn, Clear turns, and Preview.
**semantic-key:** PK-32

## 8. N1 — End of the pane

**File:** `AetherisNotebookPanel`. Method `scrollPendingFocus`. The canvas scroll pane is the one `scrollOf` already finds.
**semantic-key:** PK-33

## 9. N3 — Chooser size and folder

6. An `OUTPUT_ONLY` cell still returns before the chooser opens. The assistant-area hit still refuses. The read still runs on `AetherisWorker`. The chooser stays on the EDT.
**semantic-key:** PK-34

## 10. N7 — Connected-user pull-down

1. Build one `JPanel` with `BorderLayout`. Put a `JComboBox` on its west and the existing `ThinkSpinner` on its east. Put that panel on the footer's east.
**semantic-key:** PK-35

## 0. Locked answers this plan uses

| ID | Lock |
|---|---|
| N10 | Inspector cards newest first. Header `working set → id` stays first. `est. tokens` stays last. Twelve hyphens sit between cards. Inside an included card the user line still precedes the assistant line. Numbers follow the painted list, so the top line is 1. |
| Send | `assemble` stays oldest first. A collapsed INCLUDED cell still enters the working set. `E` is the way out of context. |
| Canvas | The Run land stays the plan 003 end-scroll. New turn and Clear turns stay whole-card. Header, collapse, and I/E/P/O stay on the delta hold. |
| N11 | East capsule: `ThinkSpinner` on `BorderLayout.WEST`, user combo on `BorderLayout.EAST`. The capsule stays `BorderLayout.EAST` on the footer. West button flow stays. |
| N7 | Choosing a name still selects that connected VT100 session. The mux stays up. Same username does not reload. N11 only swaps the two children. |
| N12 | Aetheris buttons become 60 percent of today's height. Their font shrinks to fit, floored at 8 pt, the floor `setUiFontSize` already uses. `applyButton` adds a raised bevel on every button it paints. Fill colors stay. Shell buttons keep their height. |
| N13 | Every Swing scrollbar shows an arrow button at each end. Face `#C0C0C0`. Arrow `#000000`. JediTerm's bar stays out. |
| N14 | One `SoftWrapArea`. It tracks the inner inspector viewport. Soft wrap starts on. The existing `soft wrap` checkbox is the switch and is not saved. Word wrap. The hyphen rule and the UUID stay one row at a normal width. Cell panes and the View modal stay later seats. |
| Bar | While wrap is on, the inspector's horizontal bar is absent. N13's arrows return on that bar when wrap is off. The vertical bar stays. |
| N6 | Held. No kit. Prod 1.0 stays frozen. |
| Filed | U4 stays closed unsmoked. This plan does not reopen them. |
| Models | `select model` and `list models` stay. |

## 3. N11 — Spinner and pull-down

Today the east capsule is a `BorderLayout` with an 8 px horizontal gap. The combo is `WEST`. `ThinkSpinner` is `EAST`. Showing the spinner widens that east child, the capsule grows, and the pull-down shifts left.
**semantic-key:** PK-36

## 3. N15 — Wrap columns

1. `SoftWrapArea.getScrollableTracksViewportWidth` returns false.
**semantic-key:** PK-37

4. The second `Incrementor` is the next child after the `soft wrap` checkbox. Its listener calls `setWrapColumns` and `host.shellWrapColumnsChanged`. The `+` and `-` buttons go through `fitAetherisButton`. The field is a text field. Tooltips say `soft wrap columns`.
**semantic-key:** PK-38

## 5. P2 — Yaml, deps, worker

**`org.aetheris.swing.AetherisWorker`:** single named executor in the Shell. JDBC and AMWP client I/O. Not Lamina. Not EDT. Shutdown on window close.
**semantic-key:** PK-39

## 2. Floor the build must keep

- One in-flight execute per `cell_id`. Stays one `UserRun`. The `CellRun` map extends it. It is not a second run object.

## 3. Package order

| Pkg | Name | Lands |
|---|---|---|
| 1 | `LEX-FILTER` | Pin, four-state commit, search door, vault version 2 |
| 2 | Execute seams and `MOOD-LAYER` | Walker, `LlmRouter` with Ollama only, air-gap, `SessionFacts`, facade mutex, semaphore, mood modal |
| 3 | Grok | Second provider on that router |
| 4 | Groups | `CONCAT` merge, `JOIN` label, `CellRun` map |
| 5 | Pipes | `{{cell[k].output}}` resolved on the client |
| 6 | Export and import | `.aetheris` TOON version 1 |
| 7 | Crosstalk | N is 1 when nothing is highlighted |
| 8 | Tool hooks | Channel `0x0004`, `TOOL_EXEC` pool 1, flag stays false |
**semantic-key:** PK-40

## 7. Package 4. Groups

**Done means:** Members of one group run as sibling `CellRun` entries inside the one `UserRun`. Day-one merge is `CONCAT`. Each member's banner is the label `JOIN` plus that member's execution target. An empty stored target shows as `ollama`. Stop cancels the focused cell only.
**semantic-key:** PK-41
**semantic-key:** PK-41

## 7. Package 4. Groups

2. `UserRun` (private, inside `AetherisNotebookPanel`) holds a map of `CellRun`: cell id, `startedAtMs`, phase `RUNNING` or `STOPPED`. `focusedId` is the footer cell. `armRun` puts `RUNNING` and does not remove a sibling. Stop sets the focused `CellRun` to `STOPPED`, sends `TYPE_CANCEL` for that id, and does not drop siblings. Spinner and `stopThinking` run only when no `RUNNING` cell remains. `consumeStop` drops DATA, ERROR, and EOF when that cell's phase is `STOPPED`. A matching cancel writes `CANCELLED` and does not replace the assistant CLOB.
**semantic-key:** PK-42

### 3.2 Client threads (not Lamina)

`AetherisWorker` waits for Stage 1 (JDBC off EDT).
**semantic-key:** PK-43

### T7 — Import

- Chooser on the EDT. Read on `AetherisWorker`. UTF-8. Length above 1 MiB refuses before the read. A NUL in the first 8 KiB refuses.
**semantic-key:** PK-44

### 2.1 In

| ID | Work | Approved on 006 |
|---|---|---|
| N1 | After Run, scroll the active canvas to the end of the pane and focus the new turn's input. | N1, S3, S4 |
| N2 | The frame status line includes the focused connected user and keeps the name across later messages. | N2, S9, S12 |
| N3 | One remembered size and one remembered folder for the VT100 send-file chooser and the Aetheris import chooser. | N3, S7, S8 |
| N4 | Reusable incrementor on the west of the Aetheris button bar. Rows for both open panes. Restored at app open. | N4, S5, S6, S7 |
| N5 | A short text rule between cards in the one inspector area. | N5 |
| N6 | Held. Next seed-kit records notes on the open serial, or where the operator directs. Prod 1.0 stays frozen. | N6 |
| N7 | Pull-down of connected users. Choosing a name selects that VT100 session. East capsule with the spinner. | N7, S10, S11, S12 |
| N8 | Thinking spinner is `#4169E1`. The moving glyph steps `#00FF66`. The word `thinking` stays royal blue. | N8, S1, S2 |
| N9 | Divider thickness is the constant 11. Open height guards the connection panel. | N9 |
**semantic-key:** PK-45

### 3.1 Threads

Swing stays on the EDT. Vault writes and import reads stay on `AetherisWorker`. The file chooser stays on the EDT. No new Lamina lane. No AMWP frame on import. A user switch during a run clears the listener, drops the in-flight frame, and stops the spinner. The session stays connected.
**semantic-key:** PK-46

### N11

In the east capsule, `ThinkSpinner` is `BorderLayout.WEST` and the user combo is `BorderLayout.EAST`. The capsule stays `BorderLayout.EAST` on the footer. The west button flow stays. A spin shows and hides on the west of the capsule. The pull-down stays on the outer east corner.
**semantic-key:** PK-47

### N14

1. One reusable text area, proposed name `org.aetheris.swing.SoftWrapArea`. `// ` on that type when the build creates it.
**semantic-key:** PK-908

## Package 4. Groups

- One `UserRun` holds a map of `CellRun`. Phase is `RUNNING` or `STOPPED`.
**semantic-key:** PK-48
**semantic-key:** PK-48

## Seams the packages share

- One `UserRun`. The `CellRun` map is the sibling list. It is not a second run object.

### N11 — Spinner west, pull-down east 2026-10-19 17:02

`buildFooter` puts the combo on the west and `ThinkSpinner` on the east today. Showing the spinner widens that east child, and the pull-down shifts left. After the swap the pull-down stays on the outer east corner while the spinner shows and hides. The west button flow stays. Feasible. This note waits. No Java until note-taking is done.
**semantic-key:** PK-49

### Solution

1. `SoftWrapArea` stops tracking the viewport. While wrap is on, the preferred width is the column count times the live mono character width. While wrap is off, the longest line returns.
**semantic-key:** PK-50

### Solution

4. A second `Incrementor` sits beside the `soft wrap` checkbox. Minimum 1. Plus and minus apply immediately. A typed value waits 800 ms. The tooltip says `soft wrap columns`. The footer incrementor still sets card rows. Both buttons take the short Aetheris height.
**semantic-key:** PK-51

## Model Config modal

17. One effort table. `effortRows` and `effortStart` move next to `GrokSettingsDocument.MODELS`. The dialog, the load, and the copy call that table. The service does not grow a model table. The unset token leaves the rows. `grok-4.7` and `grok-4.6` list `low`, `medium`, `high`, and `xhigh`, and start at `high`. `grok-4.5` lists `low`, `medium`, and `high`, and starts at `high`. `grok-4.20-multi-agent-0309` keeps those four levels and starts at `high`. Every other id lists `high` and starts at `high`. `fillEffort` keeps the current level when it is still a row, and otherwise selects the start. Empty and the unset token normalize to that start. `Entry` factory effort is `high`. Load and copy run the normalizer, and the next save stores `high`. Version stays `1`. A blocked file stays unread and is not overwritten. A Grok pane with no document does not select `grok-4.3` and does not write that id onto the target. The effort row is `high` until a model is chosen. `Entry.model` stays `grok-4.3` only as the factory id inside a document that already has a model. The first time a cell gains a model id, and the standing default is empty, that entry becomes the standing default, matching today's first confirm. The new entry's model is empty until a row is chosen. A stored `grok-4.3` already on a document stays. A later edit writes the cell only. A pane with no model id does not become the standing default.
**semantic-key:** PK-52
**semantic-key:** PK-52

## Grok model pull-down

The text table is already `GrokSettingsDocument.MODELS`, in the Grok serial's Models order: `grok-4.7`, `grok-4.6`, `grok-4.5`, `grok-4.3`, `grok-4.20-0309-reasoning`, `grok-4.20-0309-non-reasoning`, `grok-build-0.1`, `grok-4.20-multi-agent-0309`. `fillModels` already adds those rows. An empty document does not select `grok-4.3` and does not write it. The closed control has no prototype and no field ink, so an empty selection paints as a short blank box. The eye reads that box as no selector.
**semantic-key:** PK-53
**semantic-key:** PK-53
**semantic-key:** PK-53
**semantic-key:** PK-53
**semantic-key:** PK-53
**semantic-key:** PK-53
**semantic-key:** PK-53
**semantic-key:** PK-53
**semantic-key:** PK-53
**semantic-key:** PK-53
**semantic-key:** PK-53
**semantic-key:** PK-53

## Reasoning effort the host will take

1. The catalog is on `GrokSettingsDocument`. `grok-4.20-0309-reasoning`, `grok-4.20-0309-non-reasoning`, and `grok-build-0.1` start with the field off. `grok-4.7`, `grok-4.6`, `grok-4.5`, `grok-4.3`, and `grok-4.20-multi-agent-0309` start with the field on. Any other id starts on. A stored `send_reasoning_effort` boolean wins over that start. A file written before this row has no key, so the start applies on load. Version stays 1.
**semantic-key:** PK-54

## Mood layer online

1. `AetherisNotebookPanel` keeps the Mood dialog for a live shaper. One send is in flight. A second Send leaves the typed line in the field.
**semantic-key:** PK-55

## Thinking pane stays

1. `AetherisNotebookPanel` keeps the thinking pane above the footer. The footer sits on the bottom of that stack at its own height. The pane's bottom edge stays on the top edge of the footer. The height grows upward from that edge. The footer buttons stay at full size.
**semantic-key:** PK-56

## Short bar

No new package. No new public type. No new public method. The private classes are QuietSplitUi and QuietDivider. The private method is seatQuietBar. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

## Pin above the divider

The P button stays painted on top of the inspector divider. The letter stays P. The divider stays 11 pixels. The arrows stay. A repaint of those arrows paints the bar, and the mark paints after the bar. The mark stays at z-order 0. InspectorFold returns false from isOptimizedDrawingEnabled so a child repaint that meets the mark includes the mark. placePin keeps that z-order.
**semantic-key:** PK-57

## Visible letters

P, C, and T show their letters at the Rust pin size. The letter is painted after the fill. The pad is 8 by 2, twice, plus 8 by 4, so the box adds 24 by 8, and the floor is 22 by 22. The app point size stays. P is green while the pin is on and sapphire while it is off. C and T stay sapphire. The stroke is one white pixel and the corner radius is 2. Cell-state I, E, P, and O stay. The private class is RustLetterFace. The private method is paintRustToken. seatRustLetter is package-private.
**semantic-key:** PK-58

## A canvas cell input updates the heatbar while the user types

CellCard noteLiveDraft writes the prompt and calls refreshInspector. The 200 ms composer beat already paints tokenRingUsed. Paste, cut, and delete take that path. Rust counts the live prompt vector every frame, including when the inspector column width is 0. The heatbar wears ring_used. The count function stays the one the click already uses. This plate owns the live draft.

## Expanded View stays per cell

Expanded View is saved per cell in aetheris-expanded.yaml. A restart puts those checks back. A fresh canvas and Clear start unchecked. A new turn keeps the prior cell choice. A bad file stays unread and is not overwritten. Not a vault column. Java joins that file. Rust writes a check while the view glass is open. No new public type. The private type is ExpandedSeat. No new public method. No new JUnit class.
**semantic-key:** PK-909
**semantic-key:** PK-909
**semantic-key:** PK-909

## Heatbar uses the selected cell

The heatbar uses the model of the selected canvas cell. That is the row Token Calc opens on. The first known window does not win. An unknown window stays 0. The SSH tab stays on its own window. No new public type. No new public method. The package-private method is selectedHeatBudget. No new JUnit class. TokenRingTest keeps the new asserts.

## One transfer pair for a merge group

A transfer from the canvas to the Roux session makes a merge group one input message and one reply message. The input joins the member prompts. The reply is the stored merge text. JOIN lines stay on that reply. Java transferRows emits one pair at the first card of the group. Rust transfer_inputs joins the member prompts. No side move on this plate. Wire stanza empty. The doors do not gain, lose, or reorder a line. 2 passed. 199 filtered. Rust moved. grid.rs stayed.

## Each user keeps a mood

Each user keeps their own mood. The line, the on switch, the pending flag, the pins, and the dialog size are that user's record. A pane's SSH mood uses that pane's user. The checkbox stays in the SSH map. The line and the switch come from that user's record. The first user seated in a process claims the shared mood file only when no per-user mood file exists yet. A later user starts empty. A save writes that user's file. The shared file is not rewritten. A version other than 1 stays unread and is not overwritten. The private method is adoptMoodUser. The private functions are mood_user_path, adopt_mood_user, and mood_for_user. The test is a_second_user_keeps_its_mood. The files are AetherisNotebookPanel.java and chrome.rs.
**semantic-key:** PK-910
**semantic-key:** PK-910
**semantic-key:** PK-910
**semantic-key:** PK-910
**semantic-key:** PK-910

## Hover tips follow the switch

File, Settings gains the checkbox tooltips enabled. It starts on. A missing key stays on. Off, hover tips do not show. Java ToolTipManager.setEnabled is the gate. Rust seat_button_hover sets the tooltip delay. The private method is applyTooltipGate. The private functions are tip_delay and seat_button_hover. The test is the_settings_choice_stays. The files are AetherisNotebookPanel.java and chrome.rs.
**semantic-key:** PK-911
**semantic-key:** PK-911
**semantic-key:** PK-911

## Button bar folds to a line

Encapsulate BB1 and the encapsulated BB2 in a parent folding panel. The fold button sits at the far left of the BB1 button bar. A divider sits between that button and the first button on BB1. The bar starts unfolded. A fold drops it to the bottom of the canvas. The folded view is the folded thinking pane: one line, and the unfold button at the left. Only that button. It is the BB1 bar folded. Unfold restores the open bar with the fold button at the left. This is an override of the folding panel. The folded line gives the canvas that height. The private method is seatFooterBars. The private functions are bb_fold_mark and paint_bb_folded_line. The test is the_bb_parent_folds_to_the_think_line. The files are AetherisNotebookPanel.java and chrome.rs.
**semantic-key:** PK-912

## Fold mark is the glyph

Size the button width of the fold button for BB1 to the width of the icon itself. It is expanding because of the layout. When folded, size the height of the folded line to the height of the unfold icon itself. The private method is lockFoldMark. The file is AetherisNotebookPanel.java. Functions bb_fold_mark and paint_bb_folded_line stay. The crate move for this delta waits.
**semantic-key:** PK-913

## Each user keeps a thinking pane

When two canvases are running a prompt and thinking, one thinking pane seems to be getting another user's thinking pane, or the title of the model is not correct on the banner of the second one. Clearing one pane cleared the other one. Make sure each user has their own thinking pane in the Aetheris canvas. The only time a thinking pane shares input is for a merge cell group. Otherwise every user's single prompt thinking pane is per user, isolated from another, and each persists state independently by user. The banner is the model defined for the active cell. The private seat is ThinkSeats. The private methods are seatThinkDelta, showThinkGlass, and armFocusedCell. The files are ThinkSeats.java, AetherisNotebookPanel.java, and ThinkStrip.java. The crate move for this delta waits.
**semantic-key:** PK-914

## Head lock is a padlock

Java and Rust. The head lock should be a lock symbol image icon, greyed out and unlatched when unlocked, and green and latched when locked. The letters are hard to tell apart. The icon is small. It sits on the far left of the canvas button bar. A divider sits between it and the normal {I, E, P, O} buttons. It has separate behavior and is not modal to those state buttons. It is encapsulated away from that button group. It is isolated to control the locked messages for the head. The private seat is HeadLockMark. The private functions are paint_head_lock and paint_head_lock_divider. The files are HeadLockMark.java, AetherisNotebookPanel.java, and chrome.rs.
**semantic-key:** PK-915

## A model list is the remote list, or it is a red error

The model modal did not populate the Grok model list. Ollama did. Air-gap was not the gate. A model list is the remote list, or it is a red error. The static table is deleted. The last selected model per SSH pane and per canvas cell stays. The files are AetherisNotebookPanel.java, GrokSettingsDocument.java, and TokenRingTest.java.
**semantic-key:** PK-916
**semantic-key:** PK-916
**semantic-key:** PK-916
**semantic-key:** PK-916

### Seats

- **** The counts that matter are a Lamina `LLM_EXEC` pool of 4, one Ollama slot unless the operator changes it, one `UserRun.cellId` per vault user, a 1 MiB AMWP payload cap, and two token formulas that do not match.

### Holes

 - `AetherisNotebookPanel.assemble` sends `execution_target`. An empty cell field becomes the string `ollama`.
**semantic-key:** PK-59

### 5. System is a role, and the host line is visible - Make it so. Approved

- The mood modal stays a `JDialog` on `AetherisWorker`. It is not the EDT. It is not Lamina. It is not a canvas cell. It is not Tab A. Cancel adds no mood line. Approve hands one system line to the walker. That line is not written into a cell CLOB. The inspector header shows it. The skip text stays "keyword only."
**semantic-key:** PK-60

### 7. One UserRun grows a sibling list - Make it so. Approved

- The footer cell id, the footer clock, and Stop follow the focused in-flight cell. One `UserRun` holds a map of `CellRun`. The phase is `RUNNING` or `STOPPED`. `armRun` does not erase a sibling that is still in flight. A `STOPPED` cell drops its own late frame.
**semantic-key:** PK-917

## The report

 - **Decision:** Day-one merge is CONCAT with model banners. LLM-merge is later. The banner is the label `JOIN` plus the model. The algorithm token stays `CONCAT`.

 - `cell-busy` does not write `FAILED`. The CLOB changes only on `COMPLETED`.

Agreed 2026-10-20 20:02. `group_id` null means sequential. `execution_status` null means never run. `COMPLETED`, `FAILED`, and `CANCELLED` are the other tokens. The client vault is the writer, because Casserole does not open H2. `bindUpdate` writes parent, branch, column, group, and status from the canvas card. The insert already writes the first three. Stop cancels the focused cell. Each member cancels itself. One `UserRun` per user stays. It holds a map of `CellRun` with phase `RUNNING` or `STOPPED`. The footer clock follows the focused cell. The CLOB changes only on `COMPLETED`. A non-empty CLOB stays a source after `CANCELLED`.

 - The shaper call runs on `AetherisWorker`.
**semantic-key:** PK-61

## Floor the plan stands on

Pure Core Java. No lambdas by default. No Lombok. No JUnit unless asks. On new Java, existing keys only. No CASE, vector, or AST tooling. New client work uses `AetherisWorker`. JDBC stays off the EDT. Server threads stay on Lamina, core Java threads, metrics off.
**semantic-key:** PK-918

### Groups and CONCAT

- One `UserRun` holds a map of `CellRun`. The phase is `RUNNING` or `STOPPED`. The footer Stop and the footer clock follow the focused cell.

### MOOD-LAYER

- **Shaper call:** `JDialog` plus `AetherisWorker`.

## Stack a pre-plan can name without a new decision

- Client worker name stays `AetherisWorker`.
**semantic-key:** PK-62

### Objects the twelve solutions share - Smoked. Approved

- **`CellRun`.** A private state object inside the one `UserRun`. Fields: cell id, start time, phase `RUNNING` or `STOPPED`. The map is keyed by cell id. `UserRun` stays one per user and keeps the connector and the answers.
**semantic-key:** PK-919

### 12.5 Staging (Face, in the doorway)

Special tooling: none. Swing field + highlight painter + existing four-state. Modal is a `JDialog` on `AetherisWorker` for the shaper call (not EDT, not Lamina — Shell).
**semantic-key:** PK-63

## 22. Code standards

Pure Core Java. `NO-LAMBDA` default. GoF over streams. Junior-readable. JavaDoc, PlantUML where complexity warrants. `@author ` banners. No JUnit unless asked. LCARS ingest `record` → named class. Client-side named executor **`AetherisWorker`** (not Lamina). JDBC off the EDT.
**semantic-key:** PK-920

## 23. Lamina (LTEM) `[STAGE 0]`

**Lanes `[LOCK]` starters:** `ssh-io`, `llm-exec`, `general`. Later: `tool-exec`, `roux-io`.

## 3. Edits

The P mark overlaps the divider. The bar is opaque. A repaint of the arrows paints that bar across the mark, and the mark does not paint again. InspectorFold returns false from isOptimizedDrawingEnabled. A child repaint that meets the mark starts from the fold, and the mark paints after the bar. placePin keeps the mark at z-order 0.
**semantic-key:** PK-64

## 3. Edits

The seat is InspectorFold in AetherisNotebookPanel.
**semantic-key:** PK-65

## 3. Edits

The seat is ThinkStrip and the footer in AetherisNotebookPanel. The existing ConsoleThoughtGateTest expectation for the notebook body is the lighter sapphire. No new test class.
**semantic-key:** PK-66

## 0. On approval

4. Build the edit below. No lambdas in our types. No Lombok. No JUnit. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. The private class is RustLetterFace. The private method is paintRustToken. seatRustLetter is package-private.

## 3. Edits

P, C, and T take RustLetterFace. The fill is painted, then a one-pixel white stroke with corner radius 2, then the letter. The size is the letter plus 24 by 8, from the egui button pad of 8 by 2 twice plus 8 by 4, and at least 22 by 22. The font is the app point size. P is green while the pin is on and sapphire while it is off. C and T stay sapphire. T carries aetheris.token so the cell-state marks stay on their own face.
**semantic-key:** PK-67

## 0. On approval

6. The crate follows.

## 0. On approval

## 0. On approval

6. One commit of AetherisNotebookPanel.java.

## 0. On approval

6. One commit of AetherisNotebookPanel.

## 12. Java, then Rust

Java Aetheris seats NotebookHeatbar and NotebookDip, and the south bar in `AetherisNotebookPanel`. The private method is notebookWindow. `paintTokenEstimate` takes the counted text.
**semantic-key:** PK-68
**semantic-key:** PK-68
**semantic-key:** PK-68

## 0. On approval

## 3. Edits

The Java notebook ring uses tokenEstimate, the heuristic of the counted working set. The heatbar word already starts there. NotebookDip may still watch that text. The seat does not call showSettled. A late prompt_eval_count does not replace the word. The seat puts the ring integer back on the bar.
**semantic-key:** PK-69

## 3. Edits

Java. AetherisNotebookPanel. The member row is a sideways scroller. While that row is wider than the canvas, its own wheel listener takes a vertical wheel and scrolls the members sideways. seatGroupWheel turns that listener off, refuses focus on the row, and seats the canvas wheel on the row. A wheel that is not on a focused text pane moves the canvas bar. A click anywhere on a grouped cell, other than its scroll bar and other than the execution target, calls focusGroupCell. The text under the pointer takes the caret. A click on the chrome gives the caret to the prompt, then the reply, when that text is showing. The execution target keeps its own caret.
**semantic-key:** PK-921

## 3. Edits

Java. AetherisStage2. concatJoin writes JOIN plus the short model name. stripJoinBanners drops that line, the legacy full target line, and the legacy literal JOIN ollama. A JOIN line whose model token has no whitespace is a banner line. Rust. chrome.rs. concat_join takes the member targets and writes the same short name. strip_join drops a JOIN line whose model token has no whitespace. The legacy literal stays in the constant list. The cfg(test) calls pass the targets. An empty target still reads JOIN ollama. A named target reads the short name. Java moves first. Rust moves second.
**semantic-key:** PK-70

## 3. Edits

Java. AetherisNotebookPanel. A document edit on the cell prompt writes userText and calls refreshInspector when that card is armed. A card that is not armed is armed, and that arm recounts. Paste, cut, and delete take that path. liveTokenHold stops a recount from reentering. The 200 ms beat paints tokenRingUsed.
**semantic-key:** PK-71

## 2. What stays

`NotebookDip.settled` stays empty. A settled `prompt_eval_count` stays off the word until a later sentence names that estimator. The seam allows that estimator. This plate does not seat it.

## 3. The one transcript

A merge group is one turn wherever it sits. Members are not also their own turns. The user half is the member prompts section 3A keeps, full text, joined by a newline. The assistant half is the merge reply after `AetherisStage2.stripJoinBanners`, when section 3A keeps that reply, full text. JOIN banners stay out of the counted string on every arm, including the merge arm. Cards above the group are turns in the transcript when the armed construct is the group or a later card.
**semantic-key:** PK-72

## 7. What a later build may add - says; Please make sure these are accounted for and implemented in the build

On `AetherisNotebookPanel`, the private method `transcriptThroughArm` builds the canvas transcript. A selected card with a group id resolves to the group before that build. Section 3B is the requirement. `refreshInspector` calls the seated estimator. No new public method on the notebook.
**semantic-key:** PK-73

## 3. Edits

Java. AetherisNotebookPanel.openMood. The footer adds a checkbox labeled SSH Mood Active, to the left of Clear mood. The box is selected from the canvas user flag. The box is enabled when that user name is non-empty. A toggle writes that user flag and relays. Approve, Clear mood, and the mood switch relay after the mood file is saved.
**semantic-key:** PK-74

## 3. Edits

Java. AetherisNotebookPanel.sshMoodLineFor. The seat is sshMoodOn and a saved line. The method returns the trimmed line. A missing box or a missing line returns null.
**semantic-key:** PK-75

## 3. Edits

Java. AetherisNotebookPanel no longer parents the cluster on the footer. The footer keeps the user combo. thinkCluster returns the same spinner.
**semantic-key:** PK-76

## 3. Edits

Java. AetherisNotebookPanel. The assistant scroll, when the cell has a reply, takes SELECTION_SAPPHIRE. That color is already the armed-card sapphire, 0x2e86ff. The input scroll keeps MEDIUM_GREEN.
**semantic-key:** PK-77

## 4. The seat

TokenEditQueue is a private class. Its thread is a daemon named aetheris-token-edit. The panel starts it in the constructor. offer runs on the EDT. It assigns the next stamp and keeps only the newest edit that has not started. The worker does not touch Swing. It takes one edit. When a newer edit is already waiting, it drops the one it took. Otherwise it asks the EDT for a copied seat and waits only for that copy. The copy holds the fields the walk reads: id, column position, group id, state, readable, ephemeral, user text, assistant text, and execution target. The mood head and the cloud bit are taken on the EDT before the copy leaves. The worker then runs transcriptFrom on the copy. A torn seat drops that pass. The next edit catches up. The result is painted on the EDT when its stamp is still the newest claim and still the newest paint, and when the card that posted it is still the armed card.
**semantic-key:** PK-78

The private classes are TokenEdit, TokenSeat, and TokenEditQueue. The private methods that this plate adds are paintInspectorView, measureTokensNow, claimTokenSequence, acceptTokenPaint, scheduleInspectorView, offerTokenEdit, transcriptFrom, membersOn, captureTokenSeat, and copySeatCell. armFocus gains the draft flag. No new public type. No new public method.
**semantic-key:** PK-79
**semantic-key:** PK-79

## 6. Product

AetherisNotebookPanel.java and the lines in chrome.rs.

## 6. Product

## 4. The seat

`AetherisNotebookPanel` stops appending `mood:`, the line, `shape:`, the opener, and the closer ahead of `working set` while the switch is on. `mood_inspector_head` returns an empty string while the switch is on and a line is saved. The two assertions in `chrome.rs` that locked that on-header move with this paint. They are the lock, not a side move.
**semantic-key:** PK-80

## 2. What stays

The file stays version 1. Checked ids only. A blocked file is not overwritten. Not a vault column. A fresh canvas and Clear start unchecked. No new vault column. No new channel. No new AMWP type. No new library. No Cargo.toml edit. No new public Java type. The private type is ExpandedSeat. No new public method. No new JUnit class. No decision block on this plate.

## 0. This stamp

3. A pool turn is not sent. A live SSH connect is not made. No Session::start. No chrome::run. mvn -q -Dtest=TokenRingTest test runs from cargo test --offline -p aetheris-client chrome::tests::the_token_ring_reads_the_window -- --exact runs from rust\aetheris.

## 5. Proof

mvn -q -Dtest=TokenRingTest test passes. cargo test --offline -p aetheris-client chrome::tests::the_token_ring_reads_the_window -- --exact passes from rust\aetheris. No pool turn. No live SSH connect.

## 0. This stamp

3. A pool turn is not sent. A live SSH connect is not made. No Session::start. 2 passed. 199 filtered.

## 5. Proof

2 passed. 199 filtered. No pool turn. No live SSH connect.

## 6. Prose

No new public type. No new public method. No new JUnit class. Package-private static transferRows, transferInputs, and transferReply sit on AetherisNotebookPanel. Rust transfer_inputs, transfer_reply, and push_transfer_pair sit in chrome.rs. The test method aMergeGroupLeavesAsOnePair sits on HideCollapsedTest.
**semantic-key:** PK-81

## 5. Package 1. Effort table

- `Entry` factory effort is `high`. `copy()`, load, and save run the normalizer. The next save stores `high`. Version stays `1`. A blocked file stays unread and is not overwritten.
**semantic-key:** PK-82

## 6. Package 2. Holder and the think key

Replace `HashMap<String, Boolean> ollamaThink` with `HashMap<String, CellThink>`. `CellThink` is a private static class in `AetherisNotebookPanel`. Fields: `ollamaOn`, `ollamaLevel`, `grokOn`. A missing key means off and no level. Keep the one map. `ollamaThink.remove` at cell removal stays.
**semantic-key:** PK-83
**semantic-key:** PK-83

## 0. This stamp

1. Each user keeps their own mood. The line, the on switch, the pending flag, the pins, and the dialog size are that user's record. A pane's SSH mood uses that pane's user. The checkbox stays in the SSH map. The line and the switch come from that user's record. The first user seated in a process claims the shared mood file only when no per-user mood file exists yet. A later user starts empty. A save writes that user's file. The shared file is not rewritten. A version other than 1 stays unread and is not overwritten. The private method is adoptMoodUser. The private functions are mood_user_path, adopt_mood_user, and mood_for_user. The test is a_second_user_keeps_its_mood. The files are AetherisNotebookPanel.java and chrome.rs.

## 4. The seat

## 0. This stamp

1. File, Settings gains the checkbox tooltips enabled. It starts on. A missing key stays on. Off, hover tips do not show. Java ToolTipManager.setEnabled is the gate. Rust seat_button_hover sets the tooltip delay. The private method is applyTooltipGate. The private functions are tip_delay and seat_button_hover. The test is the_settings_choice_stays. The files are AetherisNotebookPanel.java and chrome.rs.

## 4. The seat

File, Settings gains the checkbox tooltips enabled. It starts on. A missing key stays on. Off, hover tips do not show. The known key is tooltips. Java ToolTipManager.setEnabled is the gate. Rust tip_delay is 0.55 while the switch is on and the largest finite wait while it is off. seat_button_hover applies that wait. The private method is applyTooltipGate. The private functions are tip_delay and seat_button_hover. The test is the_settings_choice_stays. The files are AetherisNotebookPanel.java and chrome.rs.
**semantic-key:** PK-922

## 4. The seat

The head lock is a small padlock image. Unlocked, the padlock is grey and the shackle is open. Locked, the padlock is green and the shackle is closed. The grey bytes are 138, 138, and 138. The green bytes are 0, 255, and 102. The control is 16 by 16. The image is 16 by 16. The icon sits at the far left of the canvas button bar, after the turn label. A vertical divider sits between the icon and I, E, P, and O. When the card is not eligible, the icon is absent and the divider is absent. The icon is not modal to those marks. It does not set aetheris.cellmark. It does not join cellMarks. It does not use the mark button. It does not use Button selectable. The client property is aetheris.headlock. The Rust id is aetheris.headlock. The Rust class is not aetheris.mark. The private seat is HeadLockMark. HeadLockMark is package-private. The private functions are paint_head_lock and paint_head_lock_divider. The files are HeadLockMark.java, AetherisNotebookPanel.java, and chrome.rs. A press still calls pressCanvasLock and press_lock. The packed prefix stays. Excluding a locked turn still drops that lock. The letters L and l leave the bar. The new tags stay keyless. fitAetherisButton does not size this control. The terminal text marks stay. I, E, P, and O stay the state group. Fold, burner, View, and the group chrome stay. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. No new module file. No new OS thread. The tags on this plate stay keyless.
**semantic-key:** PK-923
**semantic-key:** PK-923

## 0. This stamp

1. A model list is the remote list, or it is a red error. Air-gap does not gate the Grok catalog ask. The static Grok table is gone. The files are AetherisNotebookPanel.java, GrokSettingsDocument.java, and TokenRingTest.java. Java already moved.

## 4. The seat

Air-gap does not gate the Grok catalog ask. The static table is gone. The combo stays empty until grok_models_result carries model lines. Those lines fill the combo. The stored id for the open cell, or for the open SSH pane, is selected when it is non-empty. An unknown stored id stays first. An empty stored id stays unselected. A reply with no model lines, a missing session, or a failed send clears the combo and opens the red error slide with the words Grok model list is empty. The same words sit in red on the Model Config dialog. The stored id is not cleared. An empty Grok combo on a console set keeps the model already stored for that pane. The Ollama tag list follows the same rule. A tags reply with no model lines is the red notice Ollama model list is empty. The typed id stays in the editor and is not added as a row. A tags reply that has model lines still keeps a typed id that is not already a row. The Grok combo uses a lightweight popup and paints unselected rows with the combo colors, the same way the Ollama combo already does. Effort, temperature, burner, think, and the Ollama show ask stay. The factory id stays grok-4.3. The token window no longer invents a Grok row from a static table. A Grok row there is a canvas target or the focused turn. The files are AetherisNotebookPanel.java, GrokSettingsDocument.java, and TokenRingTest.java. Java moved in that commit.
**semantic-key:** PK-924
**semantic-key:** PK-924
**semantic-key:** PK-924
**semantic-key:** PK-924

## 2. What stays

The range stays 8 to 36. The font size is still saved. The control still sits after Close Pane. The host row stays. The seven buttons stay. The row incrementor, the soft-wrap column incrementor, and the inspector line incrementor stay. The thinking spinner stays the thinking clock. The sealed public pages stay. application.yaml stays home. No new vault column. No new channel. No new AMWP type. No new public Java type. No new public method.

## 5. The lock

An incrementor is minus, then the value, then plus. No refactor of an incrementor, for any reason, without a triple check.
**semantic-key:** PK-84

## 3. Packages

1. `GrokSettingsDocument`. `sendsEffort(model)` is the catalog in item 1. `Entry.sendReasoningEffort` copies, parses, and saves as `send_reasoning_effort`. A missing key uses the catalog. Version stays 1.
**semantic-key:** PK-925

## 3. Packages

2. `AetherisNotebookPanel`. Checkbox `Send effort`. Picking a model sets it from the catalog. `assemble` sets `sendReasoningEffort` on the execute body. `effort_off:` on `CHANNEL_A` stores the flag false for that cell and clears the box when that modal is open.
**semantic-key:** PK-85

## 3. Package

`AetherisNotebookPanel.newTurn`. Before the worker runs, snapshot the last cell: execution target, Grok entry copy, and think-holder copy. After `addTurn`, store that target and `saveCell`. On the EDT, `copyOnto` the Grok entry with `alsoDefault` false, and put the think holder on the new id. Then `rebuildTabs`. A canvas with no cell skips the copy. The New turn tip gains one sentence: the new turn keeps the prior turn's model.
**semantic-key:** PK-86

## 3. Package

`AetherisNotebookPanel.newTurn(boolean)`. The completion path passes true. The button passes false. When true, the EDT runnable sets `focusedCellId` and `pendingFocusId` to the new cell, clears the assistant flag, and sets the land-on-input flag, all before `rebuildTabs`. The status line then names that card's engine. When false, the existing pending-focus assignment stays, and `focusedCellId` stays where the click left it. The Join source stays the last clicked card. Copy inside `newTurn` stays.
**semantic-key:** PK-926

## 3. Package

`AetherisNotebookPanel.newTurn(boolean)` and the land pass in `scrollPendingFocus`.
**semantic-key:** PK-87

## 3. The edit

A private final class on the panel, same privacy as `CellRun`, owns the bar. The bar sits north of the reply. It starts hidden. Ctrl-F and Esc sit on the dialog root pane for `WHEN_IN_FOCUSED_WINDOW`, because the notebook map does not see keys inside the dialog.
**semantic-key:** PK-88

## 3. The edit

The button sits on `headerTop` of every readable `CellCard`, immediately after View. A collapsed readable card keeps the button on that same bar. The output window is folded. The stored reply is the output. Included, excluded, input-only, output-only, and a readable ghost card all keep the output pane, so they all keep the button. A merge sink copies the merge reply, and the JOIN lines stay in that text.
**semantic-key:** PK-89

## 0. On approval

4. Build the edits below. No lambdas in our types. No Lombok. No JUnit. No new public type. No new package. No new vault column. No new channel. No new AMWP type. The new public method is `AetherisNotebookPanel.landOnNewest`.

## 3. Edits

The seat is this section. All of them are on `AetherisNotebookPanel`.
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90
**semantic-key:** PK-90

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

**Spinner.** `ThinkSpinner` gives the glyph a fixed box measured from the widest frame. The word thinking and the clock are a second label to the right of that box. The row is left aligned. A frame change does not move the word. The notebook spinner and the dialog spinner are that same class.
**semantic-key:** PK-91

## 3. Edits

## 3. Edits

## 3. Edits

The seat is this section. All of them are on `AetherisNotebookPanel`, in `openMood`.
**semantic-key:** PK-92

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

The seat is this section. The edit is `paintClearChoice` on `AetherisNotebookPanel`.
**semantic-key:** PK-93

## 3. Edits

The seat is this section. The edit is `AetherisNotebookPanel`.
**semantic-key:** PK-94
**semantic-key:** PK-94
**semantic-key:** PK-94
**semantic-key:** PK-94
**semantic-key:** PK-94
**semantic-key:** PK-94
**semantic-key:** PK-94
**semantic-key:** PK-94
**semantic-key:** PK-94

## 3. Edits

## 3. Edits

## 3. Edits

The seat is this section. The paint is `paintChrome` on `AetherisNotebookPanel`.
**semantic-key:** PK-95

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

## 3. Edits

The Java seat is `AetherisNotebookPanel`. The button is built on `headerTop` only when `mergeSink` is true. The listener is an anonymous class. `addCell` calls `vault.addTurn`, sets the group and the next column, saves the cell, and `snapGroup` places the new id with the members and before the merge.
**semantic-key:** PK-96

## 3. Edits

The Java prompt seat is `CellCard.beginImport` in `AetherisNotebookPanel`. The 1 MiB refusal leaves this path. `readPromptStream` uses the same chunked read. A NUL in the first 8192 bytes stays `import refused: binary`. The deck import keeps `IMPORT_CAP`.
**semantic-key:** PK-97
**semantic-key:** PK-97

## 3. Edits

The Java seat is `CellCard.applyOpenHeight` in `AetherisNotebookPanel`. The row window stays `rows * line + 8`. The line is the mono font height, floored at 16. The prompt stays on that window.
**semantic-key:** PK-98
**semantic-key:** PK-98

## 3. Edits

The seat stays `AetherisNotebookPanel`. `roundEdge` paints an inside stroke on a round corner. The corner wedges take the parent plate. The pad sits inside the stroke.
**semantic-key:** PK-99

## 3. Edits

The seat stays `AetherisNotebookPanel`. The dark field on the prompt and on the reply is a round of 8. That is the Rust corner of 6 points at the open zoom of 1.3, taken to the nearest pixel. The outer stroke radius is 10, the field radius plus the 2 pixel line, so the inside edge of the stroke meets the field. `textScroll` clips its children to that outer radius. The viewport clips its own paint to the field radius of 8. The viewport is not opaque, so a later paint of the field includes the stroke under the corner. `update` paints without filling a square first.
**semantic-key:** PK-100

## 3. Edits

The seat stays `AetherisNotebookPanel`. The prompt and the reply sit in one split. `applySplitPane` clears the border, then the split UI puts the straight bevel back. This plate clears that border after the UI is on. The straight line leaves. The round field stays. The green line and the gray line stay.
**semantic-key:** PK-101

## 3. Edits

`seatQuietBar` seats the cell split. `QuietSplitUi` is the private split UI. `QuietDivider` paints the short bar. The lip is the quiet grey. The edge is black. The ocean border does not paint. The arrows stay on the bar. The seat is `AetherisNotebookPanel`.
**semantic-key:** PK-102
**semantic-key:** PK-102
**semantic-key:** PK-102

## 3. Edits

`InspectorFold` replaces the canvas and inspector split. `InspectorBar` paints the bar. The bar is 11 pixels. The fill is `0x3a3a3a`. The edge is `0x8a8a8a`. The upper arrow hides the canvas. The lower arrow hides the inspector. A click on a hidden side restores the parked share. `seatPin` puts P on the fold. `placePin` seats P on the top of the bar, at least 22 by 22, and a drag moves it with the bar. `dragBy` moves the share. `hideCanvas` and `hideInspector` are the arrow clicks. Extra width still gives 72 percent to the canvas while a side is open. A closed side stays closed. `noteDividerMoved` keeps the per-mille save. The seat is `AetherisNotebookPanel`.
**semantic-key:** PK-103
**semantic-key:** PK-103
**semantic-key:** PK-103

# Aetheris pre-plan 001

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. RustLetterFace is private. seatRustLetter is package-private. The cell-state marks stay.
**semantic-key:** PK-104

## Dependencies pitied

AetherisNotebookPanel.moodCard chooses the speaker. focusedTurn is the armed cell on the showing canvas. targetCell is the fall-through Run already uses. sendMood still sets the execute target from moodTarget on the card captured at open. commitOllamaModel writes an Ollama id onto the cell. The Ollama editor keyReleased writes that id on each key. The console path stays on commitConsoleModel. MoodDesk stays the desk. Triple stays. chrome.rs stays. grid.rs stays. stays. No new library. No Cargo.toml edit. The paging memorandum stays unstaged. application.yaml stays. The two mood files stay.
**semantic-key:** PK-927

## Dependencies pitied

The Java inspector builds its text in `AetherisNotebookPanel`. While the switch is on and a line is saved, that text opens with `mood:`, the line, `shape:`, the opener, and the closer, then `working set`, then the cards. The yellow card is `moodStatus`. It paints the saved line while the switch is on. Debug Mood marks the invitation with `MOOD_DEBUG_MARK` and `appendNumbered` leaves those lines unnumbered, immediately before the user line.
**semantic-key:** PK-105

## Dependencies pitied

No new public type. The private type is ExpandedSeat. No new public method. No new JUnit class.

## Dependencies pitied

No new public type. No new public method. The package-private method is selectedHeatBudget. No new JUnit class.

### 2.1 In

| ID | Work | Locked reading |
| --- | --- | --- |
| 1 | One modal | `openGrokDialog` becomes `Model Config`. No second `JDialog`. OK and Cancel leave. Close leaves the cell as last written. |
| 2 | One stored string | The button, the group-bar field, and the label are one `executionTarget`. A family switch keeps the other family's store. |
| 3 | Effort table | One table beside `GrokSettingsDocument.MODELS`. Factory level `high`. Empty normalizes to `high`. The service does not grow a catalog. |
| 4 | Grok body | Every Grok call sends `reasoning_effort`. The probe sends this cell's level. The gate stays one `Hello` for the process. |
| 5 | Ollama id and think | `setModel` on the options object the think path already builds. The router boolean becomes the think string. spring-ai stays 1.1.8. |
| 6 | Show ask | One `show:` frame. The server POSTs `{base}/api/show`. The reply is `show_result:` on `CHANNEL_A`. |
| 7 | Card chrome | `Model` on the main bar. One label line. Grouped width grows by the button. The unreadable card has neither control. |
**semantic-key:** PK-106

### 1. Effort table

- `Entry` factory effort is `high`. Load and copy run the normalizer. The next save stores `high`.
**semantic-key:** PK-107

## The seat

## The seat

## The seat

## The seat

Air-gap does not gate the Grok catalog ask. The static table is gone. The combo stays empty until grok_models_result carries model lines. Those lines fill the combo. The stored id for the open cell, or for the open SSH pane, is selected when it is non-empty. An unknown stored id stays first. An empty stored id stays unselected. A reply with no model lines, a missing session, or a failed send clears the combo and opens the red error slide with the words Grok model list is empty. The same words sit in red on the Model Config dialog. The stored id is not cleared. An empty Grok combo on a console set keeps the model already stored for that pane. The Ollama tag list follows the same rule. A tags reply with no model lines is the red notice Ollama model list is empty. The typed id stays in the editor and is not added as a row. A tags reply that has model lines still keeps a typed id that is not already a row. The Grok combo uses a lightweight popup and paints unselected rows with the combo colors, the same way the Ollama combo already does. Effort, temperature, burner, think, and the Ollama show ask stay. The factory id stays grok-4.3. The token window no longer invents a Grok row from a static table. A Grok row there is a canvas target or the focused turn. The files are AetherisNotebookPanel.java, GrokSettingsDocument.java, and TokenRingTest.java.

## What stays

The range stays 8 to 36. The font size is still saved. The control still sits after Close Pane. The row incrementor, the soft-wrap column incrementor, and the inspector line incrementor stay. The thinking spinner stays the thinking clock. The sealed public pages stay.

## 4. landing

The landing is section 3 of the plan. The edits stay on `AetherisNotebookPanel`.
**semantic-key:** PK-108
**semantic-key:** PK-108
**semantic-key:** PK-108

## 4. landing

## 4. landing

## 3. Cause

`AetherisNotebookPanel` builds that split with `setResizeWeight(0.72)` and never calls `setDividerLocation`. Nothing writes the location. Each open recomputes it. A wider wrap column makes the inspector's preferred width larger, so the recomputed bar moves. The column key is doing its job. The divider had no key.
**semantic-key:** PK-109

## 16. Applied 2026-10-20 08:33

`UserRun` is the map value. The key is the sanitized user. The object holds the connector, the cell id in flight or none, and that user's answers. The 07:37 answer map lives on the object. The one `ThinkSpinner` stays on the pane.
**semantic-key:** PK-110
**semantic-key:** PK-110

## 7. Verify

`mvn -q -Dtest=TerminalPasteTest test` from ` 9 tests, 0 failures. That run compiles the main tree.

## 3. Resolution

A second `Incrementor` is the next child after the width control. Tooltip `inspector line characters`. Minimum 1. Plus and minus apply immediately. A typed value waits 800 ms. The `+` and `-` buttons go through `fitAetherisButton`.
**semantic-key:** PK-111

## 2. Assessment

The Aetheris footer is the bar. Its buttons were Run, New turn, Clear turns, and Preview. The spinner is the footer label at the right. One `UserRun` per user still owns the in-flight cell. The wire already has `TYPE_CANCEL`. The server cancelled the worker and could still send the reply if the model call returned.
**semantic-key:** PK-112

## 3. Resolution

`Stop` sits immediately after Run. It reads the open vault's `UserRun`. When a cell is in flight, it marks that cell stopped, clears the in-flight id, stops the spinner, and writes `TYPE_CANCEL` for that cell. A later `DATA`, `ERROR`, or `EOF` for that stopped cell does not paint, as long as a newer run has not taken the same user. A click with nothing running says `Nothing is running.`
**semantic-key:** PK-113

## 3. Floor the builder must keep

- `CellThink` is session RAM: `ollamaOn`, `ollamaLevel`, `ollamaId`, `grokOn`. `poolOn` is a fifth field. It is not a vault column. `copyThink` copies it. `copyGrokOn` stays the Grok flag.
**semantic-key:** PK-114

- `AetherisStage2.cloudTarget` is already true for any provider other than `ollama`, so a pool run already leaves prior burner text out of the working set. Kept prior turns stay in.
**semantic-key:** PK-115

## 11. Package 7. Thinking

`CellThink` gains `poolOn`, factory false. `copyThink` copies it. Choosing Pool does not copy `grokOn` onto `poolOn`. A new pool cell with no copied holder stays off.
**semantic-key:** PK-116

## 3. The lamina decision

`POOL_IO` is a cached pool, the same shape as `SSH_IO`, with threads named `pool-io`. One reader lives there for the life of one `grok` process. The reader blocks on the child's stdout. That wait must not sit on `SSH_IO`, or the login stalls. It must not sit on `LLM_EXEC`. That pool is four threads and a held reader would starve Ollama and the API lane. It must not sit on `TOOL_EXEC`. That pool is one thread and its allowlist is empty. It must not sit on the Swing thread. `AetherisWorker` stays the client JDBC and AMWP reader. It does not own the child.
**semantic-key:** PK-117

## 1. Decision

Use **two keys**, never one fused number.
**semantic-key:** PK-118

# Rust Aetheris client, working serial

- To terminal sends a bracketed paste. Newlines stay. No trailing Enter. A burner is refused. Over the cap is refused before confirm. Cancel writes nothing. No F-number. Plate 3 is the Rust client. The think strip waits.

### Shell

| Requirement | Behavior | Mark |
|---|---|---|
| Window | One frame titled Aetheris. Icon from the casserole mark, scaled. Minimum 640 by 480. Default 1024 by 968. | JAZZ |
| Stack | Connection strip on top. A vertical split, one-touch, continuous, resize weight 0, above the product tabs. | CARRY |
| Product tabs | SSH, and Aetheris. The Aetheris tab is disabled until AMWP answers. Close on a tab restores that tab. A saved Aetheris tab waits for Connect, then takes focus, then lands on the newest turn. | CARRY |
| Frames | Scope A frames from one SSH user go to that user's canvas. | CARRY |
| File menu | New Term, Close Pane (Ctrl+Shift+W), Import, Exit. | CARRY |
| Help menu | Help, and About Aetheris. About names the shell, the SSH terminal, and the Casserole/Roux host. The Java stack line is jazz. | CARRY |
| Help text | Connect is host, port, user, password. The username combo remembers accounts when Remember password is on. New Term splits the focused pane. Font size applies to every pane and to the canvas type. Paste and Import are the paths below. Restart always starts with one pane. On the server, Esc toggles `cmd>` in medium blue. `help` lists commands. `message list` is terse. `message list verbose` is full. `message find by 'text'` scans this session, case-insensitive. One disk-backed login per username. Change the combo before Connect on a new pane. | CARRY |
| Splits | Nested splits. Divider at half. Ctrl+Shift+D horizontal. Ctrl+Shift+E vertical. Ctrl+Shift+W closes the focused pane. Close Pane stays off when one pane remains. | CARRY |
| Paste | Ctrl+V and Ctrl+Shift+V open the paste editor. OK and Ctrl+Enter insert. Enter in the terminal submits. Send file is the same path. Over 65536 characters is refused. | CARRY |
| Import on the console | File menu or the console right-click. The file dialog inserts at the terminal cursor. | CARRY |
| Console menu | Copy, Paste, Import, Select model. Select model opens Model Config for this console. The family is Ollama, Grok, or Pool. The next console turn uses that family. A raw console with no AMWP has no modal. The status says `raw console has no modal`. | CARRY |
| Close | The window asks before it exits. | CARRY |
| Geometry | The restored rectangle and the maximized flag are saved. A maximized or iconified frame does not replace the restored rectangle. The product-tab word is saved. | CARRY |
| Word motion | Left, Right, Ctrl+Left, and Ctrl+Right pass through the PTY. The host line editor moves by character and by word. This client does not rebuild that editor. Esc still toggles `cmd>` on the host. | CARRY |
**semantic-key:** PK-119

## 2. The Swing lesson

Swing SoftWrapArea wraps on words at a character count. The count is ui.aetheris.inspector.wrap.columns. A missing key is 80. A count under 1 is 1. The checkbox starts on and is not saved. Plus and minus write at once. A typed number waits 800 ms. A hyphen and a UUID stay one token. While wrap is off, each line stays whole and the pane scrolls sideways.
**semantic-key:** PK-120

## 2. The row

A divider follows Mood. Hide Collapsed and Show Collapsed follow that divider. Hide sets the window flag and rebuilds the tabs. Show clears the flag and rebuilds the tabs. cellsOnCanvas drops a folded card and keeps an open card. A folded member leaves and the open merge stays. Show returns the full order, still folded where the turn is folded. The flag is not written. HideCollapsedTest holds the filter and the names. The tests do not open a window.

## 2. The file

The file is the same aetheris-hide.yaml. Hide writes hide_collapsed true and rebuilds the tabs. Show writes false and rebuilds the tabs. A missing file starts on Show. A version other than 1 stays unread and is not overwritten. HideCollapsedTest holds the file and the names. The tests do not open a window.

## 2. The turn

Side move: `AetherisNotebookPanel.consoleFamilyOpen` opens the Swing strip when `grok_think` is true. The requirement is one gate. The Rust client copies that gate. The sibling is the Swing strip.
**semantic-key:** PK-121

## 2. The Swing lesson

`TerminalPaste.decide` refuses a burner before the body is used. The reply is sent when its trim is not empty. Otherwise the prompt is sent, including a prompt that is only spaces. An empty string returns. Newlines collapse to LF. A body longer than 65536 characters is refused before confirm. `sendPaste` wraps `ESC[200~` and `ESC[201~` and writes no trailing CR.
**semantic-key:** PK-122

## 2. The ring

The estimate leaves the card body and sits on the south of the inspector. T opens the ring. The count is the same estimate the body used. Ollama rows share the session num-ctx. This open does not ask the host. TokenRingTest holds the math, the model rows, and the painted arc. The tests do not open a window.

## 3. Proof

grokOpensWhenTheThinkWordIsTrue holds the bytes.

## 3. Edits

The bright lip is `Color.brighter()` on the button bevel and on Yes and No, the one-pixel white line on P and C and the thinking clear mark, the grab-bar outline `0x8a8a8a`, and the look-and-feel split bevel.
**semantic-key:** PK-123

## A reader can read the mood triplet in the inspector, on the card above the turn the pointer seats, and see the same opener, line, and closer the wire puts at the head of that turn

A reader can read the mood triplet in the inspector, on the card above the turn the pointer seats, and see the same opener, line, and closer the wire puts at the head of that turn.

**semantic-key:** PK-1164
**semantic-key:** PK-1164
**semantic-key:** PK-1164
**semantic-key:** PK-1164
**semantic-key:** PK-1164
**semantic-key:** PK-1164
**semantic-key:** PK-1164
**semantic-key:** PK-1164
**semantic-key:** PK-1164
**semantic-key:** PK-1164

## The heatbar buffer is the context window of the selected canvas cell, the same row Token Calc opens on

The heatbar buffer is the context window of the selected canvas cell, the same row Token Calc opens on.

**semantic-key:** PK-1165
**semantic-key:** PK-1165
**semantic-key:** PK-1165
**semantic-key:** PK-1165
**semantic-key:** PK-1165
**semantic-key:** PK-1165
**semantic-key:** PK-1165
**semantic-key:** PK-1165

## A merge input stream wears its model header

A merge input stream wears its model header. A different stream starts that header again. The same cell and the same model keep the open block.

**semantic-key:** PK-1166
**semantic-key:** PK-1166
**semantic-key:** PK-1166
**semantic-key:** PK-1166
**semantic-key:** PK-1166

## A merge output cell lists every input model

A merge output cell lists every input model. A pipe separates the names. The model button is gone from that cell.

**semantic-key:** PK-1167
**semantic-key:** PK-1167
**semantic-key:** PK-1167
**semantic-key:** PK-1167
**semantic-key:** PK-1167

## A canvas transfer sends a merge group as one input and one reply

A canvas transfer sends a merge group as one input and one reply.

**semantic-key:** PK-1168
**semantic-key:** PK-1168
**semantic-key:** PK-1168
**semantic-key:** PK-1168
**semantic-key:** PK-1168
**semantic-key:** PK-1168
**semantic-key:** PK-1168
**semantic-key:** PK-1168
**semantic-key:** PK-1168
**semantic-key:** PK-1168
**semantic-key:** PK-1168

1. The Ollama Model control is an editable pull-down. Opening the Ollama pane, and switching the family to Ollama, sends `tags:` on `CHANNEL_A` `TYPE_DATA`. `handle` sees that first line before the execute slot, before `inFlight`, and before Roux. The server GETs `{base}/api/tags`. The base is `spring.ai.ollama.base-url`, trailing slash trimmed. Connect is 10 seconds. Read is 10 seconds. The reply is `tags_result:` and one `model:` line per tag `name`. A miss is `tags_result:` and no model lines. It is not `TYPE_ERROR` and it is not `CHANNEL_STREAM`. The pull-down lists those names. A typed id that is not in the list stays. No AMWP session means the client does not send, and the list stays empty. The Swing process does not open a socket and does not read the URL.
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184
**semantic-key:** PK-1184

The armed card's rectangle was amber, RGB 200, 162, 42. It is now a bright sapphire, RGB 46, 134, 255. Pin green, a dim miss, and the ghost edge stay as they were.
**semantic-key:** PK-1187
**semantic-key:** PK-1187
**semantic-key:** PK-1187
**semantic-key:** PK-1187
**semantic-key:** PK-1187
**semantic-key:** PK-1187
**semantic-key:** PK-1187
**semantic-key:** PK-1187
**semantic-key:** PK-1187

BB1 keeps Delete on the near side of the first divider, then Collapse All and Expand All. A second divider sits to the right of those two buttons. The tool-bar v sits on the far side of that divider. The bar still starts hidden. A click still opens it and the mark becomes ^. A second click hides it. The choice is still not saved.
**semantic-key:** PK-1188

When a group is established, the member row was taking the spare height of the canvas. The members sat at the north of that space and the merge cell sat at the south. The member row is now as tall as its cards. The merge cell sits under that row. A sideways bar keeps its own height only when the cards are wider than the row. Join moves the added turn into that row, in canvas order, and the merge stays at the end of the run. Ungroup keeps the turns that remain together the same way.
**semantic-key:** PK-1189
**semantic-key:** PK-1189
**semantic-key:** PK-1189
**semantic-key:** PK-1189
**semantic-key:** PK-1189
**semantic-key:** PK-1189
**semantic-key:** PK-1189
**semantic-key:** PK-1189
**semantic-key:** PK-1189
**semantic-key:** PK-1189

A merge cell shows only the reply box. The prompt box is hidden. View still opens the stored reply. Collapse still keeps the one-line reply. Import into the merge cell is refused while the prompt box is hidden. Ungroup brings the prompt box back on that cell.
**semantic-key:** PK-1190
**semantic-key:** PK-1190
**semantic-key:** PK-1190

When Run builds the list for a turn, a group that sits above that turn goes out as one turn. The member prompts are one user message, joined by a newline, in canvas order. The assistant message is the merge reply with the JOIN lines removed. A line that is JOIN plus that member's target is the tag. ollama is the tag when the target is empty. The reply text stays. The card still stores the JOIN lines. The merge cell's state still decides whether that reply goes. Included and output-only send it.
**semantic-key:** PK-1191
**semantic-key:** PK-1191

A fresh canvas with a two-cell group looked full width. Adding a turn under that group parked the group on the right at half the column. A sequential card is centered in the vertical box. The group block was left-aligned. Those two alignments split the column, and the left-aligned block received the right-hand half. The group block now uses the same center alignment as a sequential card. The member row and the merge still fill that block. A member card keeps its own width inside the row.
**semantic-key:** PK-1192

A group snapped to the merge had no sideways bar when a third cell joined the row. The member scroller was publishing the sum of the member cards as its preferred width. That sum became the column width, so the group's own bar stayed off and a canvas bar would slide the merge with the members.
**semantic-key:** PK-1193
**semantic-key:** PK-1193
**semantic-key:** PK-1193

A grouped member was preferring 400 pixels and capping at 480. The main bar is wider than the width the card was given, so the `v` sat past the east edge.
**semantic-key:** PK-1194
**semantic-key:** PK-1194

BB1 is the original footer bar: Run, Stop, New turn, Clear turns, Preview, and the row count. BB2 is the tool bar: search, Search, Export, Import, Pull, and To terminal.
**semantic-key:** PK-1195
**semantic-key:** PK-1195
**semantic-key:** PK-1195
**semantic-key:** PK-1195

Every button on the notebook carries a hover tip. The tip says what the control is and what the click does.
**semantic-key:** PK-1196
**semantic-key:** PK-1196
**semantic-key:** PK-1196
**semantic-key:** PK-1196
**semantic-key:** PK-1196

BB1 gains Collapse All and Expand All. They sit as their own group, after a visible divider, to the right of the buttons that were already there. Collapse All folds every turn on the canvas that is showing. Each card keeps the short line of the reply to the right of its buttons. Expand All opens every turn on that canvas. The prompt and the reply show under the buttons, at the saved row count. The choice is saved on each turn. Other canvases stay as they are.
**semantic-key:** PK-1197
**semantic-key:** PK-1197
**semantic-key:** PK-1197
**semantic-key:** PK-1197
**semantic-key:** PK-1197
**semantic-key:** PK-1197

Delete one turn from the canvas that is showing. The turns that remain take new labels from 1, in canvas order, with no gap. The label on the card is the stored turn index. New turn numbers the next card from the count of cells, so a gap would reuse a label that is still on screen.
**semantic-key:** PK-1198
**semantic-key:** PK-1198
**semantic-key:** PK-1198
**semantic-key:** PK-1198
**semantic-key:** PK-1198
**semantic-key:** PK-1198
**semantic-key:** PK-1198
**semantic-key:** PK-1198
**semantic-key:** PK-1198

The AetherisNotebookPanel is the notebook surface on tab B. The panel is a JPanel. The panel paints three regions on that tab. The canvas is the row of conversation cards. The inspector is the text pane beside the canvas. The footer is the control bar under the canvas. The panel implements AmwpClientListener so an AMWP frame can arrive on this surface. AMWP is the frame type AmwpFrame that a session sends to this client. The canvas record lives in the client vault, the store named CLIENT-SOT. CLIENT-SOT means the client vault is the source of truth for the canvas conversation. The panel does not draw a graph of that conversation. A caller builds the panel with a theme, a worker, a yaml file, a Host, and a point size. The Host is the application side that owns the focused session and the saved shell numbers.
**semantic-key:** PK-1199
**semantic-key:** PK-1199

The Host is the application side this panel calls. The host shows status text for the notebook. The host keeps the saved shell numbers for open-card rows, inspector wrap, the divider, the inspector line length, and the pin bar. The host owns the focused terminal session and the file chooser. A method that reads a saved number returns the stored value. A method whose name ends in Changed writes the value the panel just applied.
**semantic-key:** PK-1200

The UserRun is one user's session binding, the sibling cell runs, and the answers waiting to paint. The binding is the SshTtyConnector for that user. The sibling runs are the CellRun map keyed by cell id. The answers are text waiting for the canvas to paint. The spinner stays on the pane. This type is not a second run object beside that spinner.
**semantic-key:** PK-1201

The method writes shell_model: as UTF-8 inside a scope A payload. The write uses channel A and the data type. An exception is ignored so the menu stays quiet when the mux is gone.
**semantic-key:** PK-1202

The method writes session_tokens: as UTF-8 inside a scope A payload. The write uses channel A and the data type. An exception removes username from the asked set.
**semantic-key:** PK-1203

The method calls applyShowResult with the frame's cell id and text.
**semantic-key:** PK-1204

The method calls applyShellModelResult with the frame's cell id, text, and open flag.
**semantic-key:** PK-1205

The method parses used: , chars: , and blocks: . A trailing carriage return is removed before the parse. A line that is not a number returns without storing. A reply missing any of the three prefixes returns without storing. A complete reply stores the three numbers for the user.
**semantic-key:** PK-1206

The method calls applyTransferReplace with the frame text.
**semantic-key:** PK-1207

The method calls AetherisNotebookPanel#sessionClosed(String, SshTtyConnector, String) with the same three arguments.
**semantic-key:** PK-1208

The method validates the panel and then calls AetherisNotebookPanel#restoreHold(String, int, int).
**semantic-key:** PK-1209

The method clears the canvas, stores the kept model word, and saves a one-cell order. A database failure sets the clear-failed status and does not post the paint.
**semantic-key:** PK-1210

The method replaces the canvas with the new cell and focuses it. Removed ids other than the new cell are forgotten. A canvas id pins the mood at head, clears the pending mood, and saves the mood store. The new cell's expanded flag is cleared. The status is cleared turns on plus the canvas name.
**semantic-key:** PK-1211

The method writes body as UTF-8 inside a scope A payload. The write uses channel A and the data type. A failure clears transferToCanvas when toCanvas is true. A failure then sets the status to Not connected.
**semantic-key:** PK-1212

The method replaces the canvas with the prompt and reply pairs and saves that order. A database failure sets the replace-failed status and does not post the paint.
**semantic-key:** PK-1213

The class GlassPiece is one inspector line the sink can keep or drop. text is the full body. The painted line is a preview of that body. role is a org.roux.model.Role byte. cellId is the cell that owns the line. head copies the cell head lock. stay is true when a mood line sits at the head of the text. seq starts at 0 and is assigned while the sink walks the pieces. live starts false and marks the open user line.
**semantic-key:** PK-1214

The class SinkGlass holds the chronological preview lines and the sink sum. blocks are those lines. tokenSum is the sum the cut reported. A caller uses null instead of this class when the gate is closed.
**semantic-key:** PK-1215

A grouped member card is 480 pixels wide. The header was one `FlowLayout` line. That line wraps, and the card keeps a one-row height, so the target field, Group, Join, and Ungroup were clipped.
**semantic-key:** PK-1216

The canvas height was holding the split, so the lower buttons stayed clipped. The header split is gone.
**semantic-key:** PK-1217
**semantic-key:** PK-1217
**semantic-key:** PK-1217
**semantic-key:** PK-1217
**semantic-key:** PK-1217

A collapsed card kept a one-line preview under the button bar. That line now sits on the same row, to the right of the buttons. The card height is that row. Opening the card puts the prompt and the reply back under the buttons, at the saved row count. The line leaves the button row.
**semantic-key:** PK-1218

The class CellCard is one turn on the canvas. The card holds the cell record, the prompt, the reply, the collapse button, and the header. The four marks are included, excluded, input-only, and output-only. The burner box stores whether the turn is ephemeral. A cell that is not readable shows load error and a disabled ?. That card cannot be expanded, edited, or sent. A readable card shows the marks, the fold button, Expanded View, burner, View, and Copy. Model is added when the cell is not a merge sink. Add Cell is added when the cell is a merge sink. The group row starts hidden. A collapsed card shows a one-line brief instead of the prompt and the reply. An open card that is not a merge sink shows the prompt and the reply in a vertical split. An open merge sink shows only the reply. The reply area is not editable. An excluded cell paints the prompt and the reply in gray.
**semantic-key:** PK-1219

The action listener starts a conversation transfer from this canvas. The listener calls AetherisNotebookPanel#transferFromCanvas.
**semantic-key:** PK-1220

The method paints the next frame and the current clock. A positive ownStartedAtMs formats that instant. A zero ownStartedAtMs uses AetherisNotebookPanel#thinkingTime(). The glyph is green #00FF66. The words are cyan #00D7FF and begin with thinking. frame advances after the paint.
**semantic-key:** PK-1221
**semantic-key:** PK-1221

An unknown Grok id stays first. An empty reply leaves the static rows.
**semantic-key:** PK-1277

The dialog asks grok_models on channel A.
**semantic-key:** PK-1278

A grok_models_result frame fills the combo.
**semantic-key:** PK-1279

Incrementor. 0. Locked answers this plan uses. The code tag is the next pass.
**semantic-key:** PK-1301
**semantic-key:** PK-1301

Divider. 0. Locked answers this plan uses. The code tag is the next pass.
**semantic-key:** PK-1302

SoftWrapArea. 0. Locked answers this plan uses. The code tag is the next pass.
**semantic-key:** PK-1303

- One in-flight execute per `cell_id`. FEAT-006 chrome stays closed. BF-008 stays one `UserRun`. The `CellRun` map extends it. It is not a second run object.
**semantic-key:** PK-1309

- One `UserRun`. BF-008 stays closed. The `CellRun` map is the sibling list. It is not a second run object.
**semantic-key:** PK-1319

No new package. No new public type. No new public method. The private classes are QuietSplitUi and QuietDivider. The private method is seatQuietBar. No new G-key. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. Java moves. Rust stays. The card-round hunks stay unstaged.
**semantic-key:** PK-1324

- **Spock.** The counts that matter are a Lamina `LLM_EXEC` pool of 4, one Ollama slot unless the operator changes it, one `UserRun.cellId` per vault user, a 1 MiB AMWP payload cap, and two token formulas that do not match.
**semantic-key:** PK-1334

- 2026-10-20 19:05. Hannibal `[PUFF]`. Wildcard `LIKE` is sufficient for this project. The door above stays the search. Lucene is not scheduled. He judged it overkill for this focus. A heavier layer is a later lift only when he names a real need. That layer may be our own search layer. It is not a commitment to Lucene.
**semantic-key:** PK-1336

- One `UserRun` holds a map of `CellRun`. The phase is `RUNNING` or `STOPPED`. The footer Stop and the footer clock follow the focused cell. Smoked `Final gap solutions` 4.
**semantic-key:** PK-1338

- **`CellRun`.** A private state object inside the one `UserRun`. Fields: cell id, start time, phase `RUNNING` or `STOPPED`. The map is keyed by cell id. `UserRun` stays one per user and keeps the connector and the answers. BF-008 stays one object.
**semantic-key:** PK-1340

The head lock is a small padlock image. Unlocked, the padlock is grey and the shackle is open. Locked, the padlock is green and the shackle is closed. The grey bytes are 138, 138, and 138. The green bytes are 0, 255, and 102. The control is 16 by 16. The image is 16 by 16. The icon sits at the far left of the canvas button bar, after the turn label. A vertical divider sits between the icon and I, E, P, and O. When the card is not eligible, the icon is absent and the divider is absent. The icon is not modal to those marks. It does not set aetheris.cellmark. It does not join cellMarks. It does not use the mark button. It does not use Button selectable. The client property is aetheris.headlock. The Rust id is aetheris.headlock. The Rust class is not aetheris.mark. The private seat is HeadLockMark. HeadLockMark is package-private. The private functions are paint_head_lock and paint_head_lock_divider. The files are HeadLockMark.java, AetherisNotebookPanel.java, and chrome.rs. Java moves. Rust moves. A press still calls pressCanvasLock and press_lock. The packed prefix stays. Excluding a locked turn still drops that lock. The letters L and l leave the bar. The listener that already carries GK-0573 and GK-0574 keeps those keys. The new tags stay keyless. fitAetherisButton does not size this control. The terminal text marks stay. I, E, P, and O stay the state group. Fold, burner, View, and the group chrome stay. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. No new module file. No new OS thread. No new G-key. The tags on this plate stay keyless. The next publish marker stays PM-91 until the land. The next free public key stays PK-8250. The next free Garage key stays GK-1108. The next free Garage number stays L46. The next free outside number stays OL-32. F-01 through F-225 stay locked. FEAT-007 stays closed. F-132 through F-137 stay the open Book'em in the working tree. The F-191 paste gap stays. grid.rs stays. Parked hunks stay unstaged. The window stays closed.
**semantic-key:** PK-1358

Air-gap does not gate the Grok catalog ask. The static table is gone. The combo stays empty until grok_models_result carries model lines. Those lines fill the combo. The stored id for the open cell, or for the open SSH pane, is selected when it is non-empty. An unknown stored id stays first. An empty stored id stays unselected. A reply with no model lines, a missing session, or a failed send clears the combo and opens the red error slide with the words Grok model list is empty. The same words sit in red on the Model Config dialog. The stored id is not cleared. An empty Grok combo on a console set keeps the model already stored for that pane. The Ollama tag list follows the same rule. A tags reply with no model lines is the red notice Ollama model list is empty. The typed id stays in the editor and is not added as a row. A tags reply that has model lines still keeps a typed id that is not already a row. The Grok combo uses a lightweight popup and paints unselected rows with the combo colors, the same way the Ollama combo already does. Effort, temperature, burner, think, and the Ollama show ask stay. The factory id stays grok-4.3. The token window no longer invents a Grok row from a static table. A Grok row there is a canvas target or the focused turn. The files are AetherisNotebookPanel.java, GrokSettingsDocument.java, and TokenRingTest.java. The product hash is e35bcf8b. This backfill does not amend that commit.
**semantic-key:** PK-1359

AetherisNotebookPanel. F-225. Each user keeps a thinking pane. The code tag is the next pass.
**semantic-key:** PK-1389

AetherisNotebookPanel. F-226. The head lock is a padlock. The code tag is the next pass.
**semantic-key:** PK-1390

AetherisNotebookPanel. F-227. A model list is the remote list, or it is a red error. The code tag is the next pass.
**semantic-key:** PK-1391

`ViewBounds` is a public type in this package.

The inspector caps how many characters of a cell are shown. That cap stays. An incrementor beside the inspector width control chooses how many characters to inspect.
**semantic-key:** PK-1399
