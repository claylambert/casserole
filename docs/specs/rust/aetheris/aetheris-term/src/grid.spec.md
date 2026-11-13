# rust.aetheris.aetheris-term.src.grid

### 2.2 Out

| Item | Why |
|---|---|
| `assemble` order | N10 reverses the list on screen. The working set still sends oldest first. |
| Character wrap | The hyphen rule and the UUID stay one row at a normal width. |
| Cell panes and the View modal | They can take the same handler later. This slice's seat is the inspector. |
| Saved wrap checkbox | It starts on at each launch. He did not ask to keep an off state. |
| Terminal scrollbar | JediTerm paints its own bar. N13 is the Swing scroll panes. |
| Shell button height | The bevel reaches the shell buttons. The 40 percent cut is the Aetheris pane. |
| N6, Stage 2, prod 1.0 | Held, closed, frozen. |
**semantic-key:** PK-626

## Floor the plan stands on

 - **Inherits:** Saved wrap columns, default 80.
**semantic-key:** PK-627

## 3. Edits

Recommends the dark shadow. The bright lip is `Color.brighter()` on the button bevel and on Yes and No, the one-pixel white line on P and C and the thinking clear mark, the grab-bar outline `0x8a8a8a`, and the look-and-feel split bevel.
**semantic-key:** PK-123

# Rust Aetheris cell ink

- **What it is:** The prompt on a working card is green, `#00ff66`. A reply that has text is yellow, `#ffff55`. A reply whose text is blank is gray, `#666666`. An excluded card paints both panes gray, `128,128,128`. Java names those inks `INPUT_GREEN`, `OUTPUT_YELLOW`, `RESPONSE_GRAY`, and `Color.GRAY`. This land leaves the working set as it stands.
**semantic-key:** PK-628

### Canvas

| Requirement | Behavior | Mark |
|---|---|---|
| Run | Sends the focused turn. A grouped member sends every member, each its own call. The merge cell is not sent. The call keeps the earlier turns the card is allowed to see, then writes the reply. Ctrl+Enter does the same when the main view is open: not the complex filter, and not while the view window is open. One execute is in flight for a cell. The wire rules stay section 12. | CARRY |
| Stop | Cancels the focused turn when it is running. Other group members keep running. The stored reply stays. If nothing is running, the status says `Nothing is running.` Then `Stopped.` | CARRY |
| New turn | An empty card at the end. The cursor sits in the prompt. The prior turn's model is kept. The card starts Included. The canvas lands on that card. | CARRY |
| Clear turns | Deletes every turn on this canvas and leaves one empty turn. Other canvases stay. The button is not an undo. The mood file stays. The thinking log stays. Status `cleared turns on {name}`. | CARRY |
| Preview | Rebuilds the inspector for the focused turn. The inspector shows host:, then mood: (none), mood: off and the string, or mood: and shape: while the switch is on. Earlier turns this call would keep come newest first, and an estimated token count. It does not send. | CARRY |
| Row incrementor | Prompt and reply each keep that many lines. Saved with the shell. Minimum 1. | CARRY |
| Search | The first turn whose text contains the field. An empty field matches nothing. Percent and underscore are ordinary characters. Search does not change state. | CARRY |
| Collapse | Collapse All and Expand All are this canvas only. A collapsed card keeps a short reply line, or the prompt when no reply exists, to the right of the buttons. The choice is saved per turn. The card button is `+` and `-`. An unreadable turn shows `?`, disabled, labeled `load error`. It cannot expand, edit, or send. | CARRY |
| Tool bar | Starts hidden behind `v` and `^`. The choice is not saved. The bar holds Search, Export, Import, Pull, To terminal, a separator, the mood switch, and Mood. | CARRY |
| User combo | Pinned. The tip says it switches users. The selection focuses that connected user. One vault per SSH username. The status user line switches with it. | CARRY |
| Think | A strip above the footer. It rises from the top of the footer and stays until Close. `v` folds the text. `^` restores it. The fold is this window only. Close writes closed true on ~/.casserole/aetheris-think.yaml and hides the pane. The next open loads the log and leaves the pane shut. A later thought opens the pane and clears that mark. A version other than 1 is left unread and is not overwritten. The log is ~/.casserole/aetheris-thinking.txt. It is a continuous scroll. Each block is tagged with the model. A separator sits between blocks. Clear on the pane wipes the pane and that file. The north edge sets the height, and the height is kept on the yaml when it falls between 48 and 2000. The thinking checkbox is kept on that yaml and is copied with the model, including poolOn. The glyph keeps a fixed box. The word thinking and the clock stay put while the glyph turns. The glyph is #00FF66. The word is #00D7FF. The spinner sits east of the footer, beside the user combo. | CARRY |
| Inspector | Horizontal split. Canvas, then inspector. Resize weight 0.72. The divider ratio is persisted, and restored after the split has a real width. Soft wrap applies to the inspector only. The column count comes from the shell. The default is 80. One-touch tips hide the canvas or hide the inspector. Empty text is `(empty canvas)`. | CARRY |
| Error slide | Body plus Close. It stays until closed. A hard miss is red. Any other miss is yellow. A mood miss does not use a popup dialog. | CARRY |
**semantic-key:** PK-629

## 5. Package 1. Terminal library

- SGR reset, bold, reverse, and the 16 colors. Color stored as default, index 0 through 255, or RGB. The first paint uses the 16 and the dark plate.
**semantic-key:** PK-630

## 1. The window

The primary screen joins a soft wrap with the next row. The joined text wraps again to the new width. A hard newline stays a break. A wide glyph stays whole and keeps its spacer. Color and bold stay on the cells.
**semantic-key:** PK-631

## 8. Grid

- Color is a default, an index from 0 through 255, or an RGB triple. The first paint uses the 16 and the plate.
**semantic-key:** PK-632
