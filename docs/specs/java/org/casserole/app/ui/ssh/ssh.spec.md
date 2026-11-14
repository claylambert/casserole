# org.casserole.app.ui.ssh

## 3. Proof

ConsoleCopyChord names the chord. Ctrl and Shift on the C key is the copy. Ctrl-C without Shift is the interrupt. Any other key passes. An empty highlight returns no clipboard text. No Session::start. No chrome::run. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

## 3. Proof

Vt100StatusLine names the strip. pty paints PTY. No Session::start. No chrome::run. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

## 3. Proof

Vt100TokenWindow names the sum, the heuristic, the available count, and the menu seat. A stored count is kept. A missing count uses the heuristic. Empty text is 1. (length + 3) / 4 counts the rest. No Session::start. No chrome::run. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

## 3. Proof

ToolsThinking names the bar and the reveal. A closed pane starts at slide 0. An open pane keeps its slide. The fold opens. No Session::start. No chrome::run. The live POST was not made. A pool turn was not sent. A live SSH connect was not made.

### What the room already does

`SshClientProperties` loads `~/.casserole/ssh-client.yaml` with SnakeYAML, then stores every key except `accounts` as a string. A nested map becomes one string on that load, and the next save writes the string back. That file also holds the connection password. A Grok document does not go in it.
**semantic-key:** PK-172

## 8. P5 — `bin/` scripts + sample yaml

**`aetheris-shell`:** `java -cp` or exec `org.casserole.app.ui.ssh.SshClientApp` from `target/classes` + deps, **or** `mvn -q exec:java`. Pick one that works without Boot. `mvn -q exec:java -Dexec.mainClass=org.casserole.app.ui.ssh.SshClientApp` with resolved `JAVA_HOME`, plus pass-through args (`--aetheris-yaml`). Does **not** start Boot.

# Aetheris — Stage 1 tweaks plan — serial 001

T1–T8 are from the stamped pre-plan. **No Lombok.** **No lambdas in our types.** on a **new** type only. Class stays `org.casserole.app.ui.ssh.SshClientApp`.
**semantic-key:** PK-935

## 3. N9 — Divider width

4. `DarkDivider.paint` already fills the live rectangle and then paints the one-touch buttons. Leave that paint alone.
**semantic-key:** PK-173

## 7. N4 — Incrementor

5. Store the integer on `SshClientProperties` in `~/.casserole/ssh-client.yaml`, key `ui.aetheris.view.rows`. Missing key means 4. Not a vault column. Not `application.yaml`. Switching vault users does not change it.
**semantic-key:** PK-174

## 9. N3 — Chooser size and folder

2. Add Host methods the notebook already reaches through `SshClientApp`: prepare a `JFileChooser` with the saved folder and size, and remember the dialog afterward. `sendFileRequested` uses the same two methods. One memory for both doors.
**semantic-key:** PK-175

## 5. N13 — Scrollbar arrows

2. The UI is a private static subclass of `BasicScrollBarUI` inside `SshClientTheme`. It is not a new top-level type and gets no `// `.

## 3. N15 — Wrap columns

6. `SshClientProperties` key `ui.aetheris.inspector.wrap.columns`. `getAetherisWrapColumns` returns 80 when the key is missing, and at least 1 otherwise. `setAetherisWrapColumns` writes the integer and saves. `applyDefaultsForMissingKeys` does not grow this key.
**semantic-key:** PK-176

7. `SshClientApp.shellWrapColumns` and `shellWrapColumnsChanged` are the host pair. The footer row pair stays.
**semantic-key:** PK-177

## 2. Slice order (build this sequence)

P0 is first so operators can type. P1 shares `SshClientApp` with P0. P2–P4 can overlap after P0. P5 can start after P0 (mux is independent of vault). P7 is the notebook; it waits for P3+P4+P5+P6. P8 is continuous, verified in P9.
**semantic-key:** PK-178

## 8. P5 — AMWP

**Client:** byte demux **in front of** `SshTtyConnector` Reader. JediTerm sees **only** Scope T. No UTF-8-decode of the mux stream.
**semantic-key:** PK-179

### 3.2 Client threads (not Lamina)

- `SshSessionController` connect worker

### WP-C — `aetheris.yaml` load only (S0-09)

CLI: pass through `SshClientApp.main` args. Do not require Spring to load this file.
**semantic-key:** PK-180

# Aetheris — Stage 1 tweaks pre-plan — serial 001

No Lombok. No lambdas in our types. `// ` on a new type only. Class stays `SshClientApp`.

# Aetheris — Stage 1 tweaks pre-plan — serial 003

No Lombok. No lambdas in our types. Anonymous classes only. `// ` on a new type only. Class stays `SshClientApp`. Shell launch stays `java -cp`. Do not edit `src/main/resources/application.yaml`.

### N9 — Divider width

- `DarkDivider` already paints the live size and then the one-touch buttons.
**semantic-key:** PK-181

# Aetheris — Stage 1 tweaks pre-plan — serial 004

No Lombok. No lambdas in our types. Anonymous classes only. `// ` on the one new type, the soft-wrap area, when a later build creates it. Class stays `SshClientApp`. Shell launch stays `java -cp`. Do not edit `src/main/resources/application.yaml`.

### WP-B — Chrome (S1-01…S1-03, S1-19, S1-20)

- `JTabbedPane` inside existing `SshClientApp`. Split tree **stays in SSH tab**. Do not reparent JediTerm into the canvas.
**semantic-key:** PK-182

- N9's constant is 11. `applySplitPane` is the only `setDividerSize`, and the literal there is 8 today. `collapseControlPanel` treats a divider location above 8 as expanded. That 8 moves with the constant. `DarkDivider` paints the live size and then the one-touch buttons, so the extra 3 pixels are hit area. The shell split, the session split, the notebook split, and every open cell split take the constant. Each open card pays the extra 3 pixels.

### Solution

3. The count is `ui.aetheris.inspector.wrap.columns` in `~/.casserole/ssh-client.yaml`, through `SshClientProperties`. A missing key means 80. It is not written by `applyDefaultsForMissingKeys`. It is not `application.yaml` and not a vault column. The checkbox still starts on and is not saved.
**semantic-key:** PK-183

## Product tab

4. The new methods this build names are `SshClientProperties.getProductTab` and `SshClientProperties.setProductTab`. No new package, public type, vault column,, channel, or AMWP type. The help dialog stays. The exit question stays.

## Aetheris waits for Connect

1. `SshClientApp` sets the restore flag when the stored word is `aetheris`. It does not connect from that flag.
**semantic-key:** PK-184

## Mood string

The connector is local. It is the terminal byte seam for this glass. Keystrokes stop there. Terminal reports stop there. The glass writes nothing on channel T. It does not use `SshTtyConnector`. The page editor, bracketed paste, and the shell commands stay on the real terminal.
**semantic-key:** PK-185

## The Apple scroll

`AppleScrollBar.install` arms a scroll pane. `applyScrollPane` calls it. The canvas, the member row, the cell prompt, the cell reply, the inspector, the thinking strip, the mood draft, the paste editor, and the help text all pass through that call. Another Java app calls `install` on its own pane.
**semantic-key:** PK-186

## Hot strip

No new package. No new public type. No new public method. `AppleScrollBar` stays. The method stays `install`. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

## Wider bar

No new package. No new public type. No new public method. `AppleScrollBar` stays. The method stays `install`. `BAR_PX` stays the measure. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

## The VT100 bar

No new package. No new public type. No new public method. `AppleScrollBar` stays. `install` stays the pane arm. The package method is `armStrip`. `BAR_PX` stays the measure. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

## Quiet edge

No new package. No new public type. No new public method. The private class is QuietOcean. The private methods are installQuietEdges and quietFill. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No yaml edit. No side move on this plate. This land leaves the working set as it stands. The card-round hunks stay unstaged.

## Dark line

The line under the control bar and the divider edges take the dark split ground. The open rule and the folded rule take the split background. The divider border is empty. DarkDivider fills that ground and does not stroke a second edge. The arrows stay. The divider stays 11 pixels. The chip words stay.
**semantic-key:** PK-187

## Button face

The private class is PlateFaceUi. The private methods are seatPlateFace and plateSkip.
**semantic-key:** PK-188

## Composer beat is the FLASH

The Java SSH strip sentence drops the est. tokens suffix. The colored word on the heat row is the est. tokens on that strip. `Vt100TokenWindow.withEstimate` stays. The Rust status sentence keeps `shell_status_with_tokens`, and the strip word is painted beside it.
**semantic-key:** PK-189

## Pull-down arrow wears the emerald

The pull-down arrow paints in the existing hover emerald, #00FF66. Java seats the private method seatComboArrow on SshClientTheme. The anonymous Icon draws the downward triangle on the Metal combo button. Rust seats the private function emerald_combo_arrow and passes it to each combo icon. The field, the word, and the pane arrows stay.
**semantic-key:** PK-190

## The SSH divider wears the lighter gray

An SSH terminal split sets the client property ssh.pane.split before applySplitPane. DarkDivider paints SSH_DIVIDER, the bytes 0x3A3A3A, when that property is set. The inspector split keeps the split ground. One-touch arrows stay. The divider size stays 11.
**semantic-key:** PK-191

## Font control sits after Close Pane

SshConnectionPanel.layoutControls keeps the host row on Mode, Host, Port, Username, Password, and Remember password. The button row keeps Connect, Disconnect, Reconnect, Clear, Send file, New Term, and Close Pane. barRule follows Close Pane. The font label and the spinner follow the rule. FONT_TIP stays. [chrome.rs](../../../../../../../../rust/aetheris/aetheris-client/src/chrome.rs) paints the same order on the button row. A separator follows Close Pane. Font size, minus, the size, and plus follow that separator. The host row keeps the fields and the link status. The line between Connect and the SSH tab stays. the_folded_bar_sits_on_one_row seats Font size on the button row, after Close Pane. The folded row stays.
**semantic-key:** PK-192

## One mood desk seats the canvas and the Roux session

The public type is MoodDesk. The interface is MoodDoor. The package is org.casserole.mood. MoodDesk.DESK is the desk. MoodDesk.ACTIVATE is the one activate sentence. The package methods are normalize, canvasSystems, moodHead, sshLine, announceBody, and parseShell. The door methods are seat, adopt, and porchRules. TerminalSession.moodTold and setMoodTold remember whether this connection announced the seat. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The paging memorandum stays unstaged. The yaml stays. The two mood files stay.
**semantic-key:** PK-936

## A mood change sends the whole pool session once

RulesHold compares the rules this hold opened with to the rules on this prompt. A null memory does not extend. A different line does not extend. An empty next matches an empty hold. The switch off is an empty rule string. canExtend requires that match, then the tail rules it already had. align stores the rules with the held text. dropHold forgets them. The next success with those same rules may send the tail. MoodDesk.seat stays the Roux rewrite. canvasSystems stays the canvas triple. sshLine stays the shell line. A collapsed card stays in the working set. An excluded card stays out. The package-visible static is rulesHold. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. SshClientApp stays. The paging memorandum stays unstaged. The yaml stays.

## A key on an SSH pane posts the count

SshEditQueue keeps the newest post. The key writes the unsent line and posts. The walk copies that pane and counts off the key. A result seats the integer on that pane when its stamp is still current, and the heatbar paints it when that pane is in front. The timer posts the focused pane and paints the stored integer. It does not call sshLiveCount. A click still calls sshLiveCount. The private class is SshEditQueue. The private types are SshEdit and SshSeat. The package methods are liveCount and seatLiveCount. No new public type. No new public method. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. The paging memorandum stays unstaged. The yaml stays.
**semantic-key:** PK-937

## Shell launch enables native access

`SshClientApp` calls `SymbolLookup.libraryLookup` for `user32` and for `dwmapi` from an unnamed module. JDK 26 warns and names `--enable-native-access=ALL-UNNAMED`. `bin/aetheris-shell.ps1`, `bin/aetheris-shell.sh`, and the stick launcher written by `bin/install-aetheris-shell.ps1` pass that flag to the shell JVM. The IDE configuration `SshClientApp` in `.idea/workspace.xml` sets `VM_PARAMETERS` to the same flag. That workspace file stays unstaged. The class stays `org.casserole.app.ui.ssh.SshClientApp`. The launch stays `java -cp`. `paintCaption` stays. The server launch stays. No new public type. No new public method. No new JUnit class. Java sources stay.
**semantic-key:** PK-938
**semantic-key:** PK-938

## Button bar folds to a line

BB1 and the encapsulated BB2 sit in a parent fold. The fold mark sits at the far left of BB1. A divider sits between that mark and Run. The bar starts open. A fold drops the bars to the bottom of the canvas. The folded view is the thinking line. One grey line and the unfold mark at the left. BB1, BB2, and the user combo leave. Unfold restores the open bar. The private method is seatFooterBars. The private functions are bb_fold_mark and paint_bb_folded_line. The test is the_bb_parent_folds_to_the_think_line. The files are [AetherisNotebookPanel.java](../../../../../../../../src/main/java/org/aetheris/swing/AetherisNotebookPanel.java) and [chrome.rs](../../../../../../../../rust/aetheris/aetheris-client/src/chrome.rs). FoldingPanel stays. The connection bar stays. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. No new module file. No new OS thread. The choice is not saved. BB2 keeps its own mark. The thinking pane stays. The folded thinking line still clears the bar. Debug Mood stays. Painted borders stay. Each user still keeps a separate mood.
**semantic-key:** PK-939

## Fold mark is the glyph

The BB1 fold button width is the width of the mark glyph. Open, the height stays the bar height. Folded, the button height and the folded line height are the height of the unfold glyph. Preferred, minimum, and maximum are that size. The margin and the border are zero. The lock runs after the bar fit, after a font refit, and again after the plate face seats the button. The private method is lockFoldMark. The file is [AetherisNotebookPanel.java](../../../../../../../../src/main/java/org/aetheris/swing/AetherisNotebookPanel.java). Functions bb_fold_mark and paint_bb_folded_line stay. The crate move for this delta waits. FoldingPanel stays. The BB2 tools mark stays. The connection bar stays. The grey line stays. The ink stays 0x8a8a8a. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. No new module file. No new OS thread. The choice is not saved. BB2 keeps its own mark. The other bar buttons keep their word width. The thinking pane stays. The folded thinking line still clears the bar. Debug Mood stays. Painted borders stay. Each user still keeps a separate mood.
**semantic-key:** PK-940

## Each user keeps a thinking pane

The only time a thinking pane shares input is for a merge cell group. Otherwise every user's single prompt thinking pane is per user, isolated from another, and each persists state independently by user. The glass is still one strip. The strip shows the pane of the armed cell. The key is the user and the scope. A scope with a group id is g: and that id. A scope with no group id is c: and the cell id. Two single-prompt cells do not share, even for the same user. Members of one merge group share one pane. The banner word is the model defined for the cell that produced the chunk. The same cell and the same word append. A different cell or a different word starts a header. Each user keeps a file under ~/.casserole/aetheris-thinking/. The file is that user's name plus.yaml. A clear of one pane drops that scope from that user's file and leaves every other scope. A clear does not open another user's file. The canvas seat does not read aetheris-thinking.txt and does not write it. The chrome file aetheris-think.yaml stays one file. The private seat is ThinkSeats. The private methods are seatThinkDelta, showThinkGlass, and armFocusedCell. ThinkSeats is package-private. text and showLog on ThinkStrip are package-private. The files are [ThinkSeats.java](../../../../../../../../src/main/java/org/aetheris/swing/ThinkSeats.java), [AetherisNotebookPanel.java](../../../../../../../../src/main/java/org/aetheris/swing/AetherisNotebookPanel.java), and [ThinkStrip.java](../../../../../../../../src/main/java/org/aetheris/swing/ThinkStrip.java). The crate move for this delta waits. The VT100 strip stays. FoldingPanel stays. seatFooterBars stays. The BB1 fold stays. The connection bar stays. No new public type. No new public method. No new JUnit class. No new library. No Cargo.toml edit. No new module file. No new OS thread. The tags on this plate stay keyless.
**semantic-key:** PK-941

### Holes

 - `SshTtyConnector.writeAmwp` always writes channel `0x0002`.
**semantic-key:** PK-193

### 4.8 Tab A + Shell operator remainder `[STAGE 1]`

Face: these ride in Stage 1 because they are either **defects** (PRESERVE-ALL) or **the same `SshClientApp` / theme files already opens**. They are not Stage 2 cloud work. They must not delay AMWP/vault; they can ship in the same stage as chrome.
**semantic-key:** PK-942

#### 4.8.4 Theme palette — ****

Named keys on the existing `SshClientProperties` / `SshClientTheme` (not SnakeYAML on the server). Defaults:
**semantic-key:** PK-194
**semantic-key:** PK-194

### 2.1 Placement in this tree

Product-name the desktop app **Aetheris Shell** (scripts, window title). Class stays `SshClientApp`.
**semantic-key:** PK-195

### 7.1 Frame header

**Client:** wrapper **in front of** `SshTtyConnector`. JediTerm sees only concatenated `0x0001` payloads. Do not subclass JediTerm to parse AMWP.
**semantic-key:** PK-196

## 3. Edits

`fitAetherisButton` calls the floor. `paintMarkChoice` calls it again after the mark border. `ThinkStrip.fitButton` calls the floor. `paintClear` calls it again after the C border. The thinking fold calls it when the letter changes. `FoldingPanel` calls it when the connection bar mark changes.
**semantic-key:** PK-197

## 3. Edits

The seat is the rule under the open control bar, the folded rule beside the plate buttons, and the split divider. The rules were painted with the panel foreground, a light grey. They now take the split background. The divider border key was the Ocean edge. It is now an empty border. `DarkDivider` fills the split background and does not stroke a second edge. The one-touch arrows still paint. The divider stays 11 pixels.
**semantic-key:** PK-198

## 3. Edits

The seat is `paintCaption` and `applyCaption` on `SshClientApp`. Immersive dark mode stays on. Caption, border, and title text take the system default. That is the dark caption the Rust window leaves. The caption words stay. `colorRef` stays. No new private method.
**semantic-key:** PK-199

## 0. On approval

4. Build the edit below. No lambdas in our types. No Lombok. No JUnit. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. The private class is `FlowRow`. The private method is `flowLabel`.

## 3. Edits

The seat is `SshConnectionPanel`. The host row holds Mode, Host, Port, Username, Password, Remember password, and Font size. The button row holds Connect, Disconnect, Reconnect, Clear Terminal, Send file..., New Term, and Close Pane. Both rows flow left and wrap inside the frame width. The private class is `FlowRow`. The private method is `flowLabel`.
**semantic-key:** PK-200
**semantic-key:** PK-200

## 0. On approval

4. Build the edit below. No lambdas in our types. No Lombok. No JUnit. No new public type. No new public method. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library. The private class is `PlateFaceUi`. The private methods are `seatPlateFace` and `plateSkip`.

## 3. Edits

The private class is `PlateFaceUi`. Idle fill is the tab gray with no stroke. Hover takes the hover fill and a one-pixel hover edge. Press fills `#4a4a4a`. The word is `getForeground()`. A disabled word is that foreground at half alpha. The corner radius is 12. The private methods are `seatPlateFace` and `plateSkip`. A later dialog button takes the same face.
**semantic-key:** PK-201

## 0. On approval

6. One commit of [SshClientApp.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/SshClientApp.java).

## 0. On approval

6. One commit of the menu hover in [SshClientTheme.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/SshClientTheme.java) and the bar call in [SshClientApp.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/SshClientApp.java).

## 3. Edits

`FoldingPanel.applyMenuFont` walks the one bar and every row and sets the point size. `fontSizeChanged` calls it. The first seat calls it with the current size. `paint_menu` calls `seat_bar_font` on the bar and inside each menu. Clear mood Yes takes `getPanelForeground`. The Rust Yes takes INK. `OptionPane.buttonForeground` takes the panel foreground. The search clear is a red x on the pin fill, snapped to the field with no gap, and it is not a bar button. Search copies the field into the filter and selects the first hit. `findHits` and `find_slots` omit a cell whose user text and assistant text both miss a case-insensitive contains. An empty filter shows the conceal-allowed deck. The x empties the field and the filter and rebuilds. `search_turn` uses that same contains.
**semantic-key:** PK-202

## 0. On approval

6. One commit of the showAbout hunk in SshClientApp.

## 12. Java, then Rust

Casserole seats ComposerHeatbar and OllamaTokenDip, and the SSH strip in `SshClientApp`. The private method is feedComposerHeat. The frame heatbar hides on the Aetheris tab.
**semantic-key:** PK-203
**semantic-key:** PK-203
**semantic-key:** PK-203

## 0. On approval

6. One commit of the named hunks in FoldingPanel and SshClientApp. The parked banner, the connect path, and the card-round hunks stay unstaged.

## 3. Edits

Java. SshClientTheme.applyComboBox calls the private method seatComboArrow. That method finds the Metal combo button and sets an anonymous Icon. The icon is 10 by 5. It fills a downward triangle in BUTTON_HOVER_INK. The notebook seats applyComboBox on the user combo, the family combo, the api combo, the Grok effort combo, the Grok stream combo, and the Ollama effort combo. Combos that already called applyComboBox pick up the same arrow.
**semantic-key:** PK-204

## 3. Edits

Java. splitFocused sets the client property ssh.pane.split on the new terminal split before applySplitPane. DarkDivider paints SSH_DIVIDER when that property is set. The inspector split does not set the property and keeps the split ground. selectSession calls the private method markPaneFocus. That method calls the package method seatPaneFocus. The focused pane paints a 1 pixel inside stroke after its children. A single pane stays bare. Unfocused panes stay bare.
**semantic-key:** PK-205

## 2. What stays

The canvas ring stays on tokenRingUsed and ring_used. The message list stays. The heatbar geometry stays. The budget stays 131072 when the engine word contains ollama, and 0 otherwise. The glass helpers stay for a caller that does not hold the Roux integer. The focused pane ring stays. The bar height stays. The yaml stays. Line on the paging page stays unstaged. SshClientApp and [chrome.rs](../../../../../../../../rust/aetheris/aetheris-client/src/chrome.rs) stay hunk-filtered.

## 2. What stays

The control bar stays on applyBarFont. The menu stays on applyMenuFont. The canvas tab titles stay on the notebook. The link chip stays. The plate colors stay. The plate shape stays. The range stays 8 through 36. The bar height stays. Line on the paging page stays unstaged. showFields stays unstaged. SshClientApp stays unedited on this plate.

## 3. Edits

Java. FoldingPanel. seatPlateFont sets the SSH button, the Aetheris button, and the product tab pane to sans serif at the control-bar size, clamped from 8 through 36. It clears a stored button size so the word can grow. applyMenuFont calls it, and that method already runs from the font spinner and from the first menu seat. showChrome calls it again after a fold installs a tab UI, so the size survives that install. SshClientApp stays as it stands.
**semantic-key:** PK-943

## 3. Edits

Java. SshConnectionPanel.layoutControls. The host row drops the font label and the spinner. The button row adds barRule after Close Pane, then the font label, then the spinner.
**semantic-key:** PK-206

## 3. Edits

Java. SshConnectionPanel. The button text is Load File. SEND_FILE_TIP reads Load File reads a file and opens it in the paste editor. OK inserts the text.
**semantic-key:** PK-207

## 3. Edits

Java. SshClientApp. The notice title is Load File. The help sentence reads Load File is the same path.
**semantic-key:** PK-208

## 7. What a later build may add - says; Please make sure these are accounted for and implemented in the build

On `Vt100TokenWindow`, `heuristic` delegates to `HeuristicTokenCounter.count`. The results stay the ones section 6 names for the unheld string. The held path returns the triple section 6 names. No new public method is required beyond the types this section names. The heatbar does not gain a reader of `Vt100TokenWindow.Book`.
**semantic-key:** PK-209

## 0. On approval

6. The same message. The crate parent is the Java commit. [chrome.rs](../../../../../../../../rust/aetheris/aetheris-client/src/chrome.rs) and [SshClientApp.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/SshClientApp.java) stage only the named hunks. The proofs are shared because the plates land in one tree.

## 3. Edits

Java. SshClientApp.relayArmedMoods. Walk the connected AMWP panes. The user is the vault name on the last request. The wanted line is sshMoodLineFor for that user. A match sends nothing. An empty want on a pane that was already told sends shell_mood_clear. A non-empty want sends shell_mood and the line. The frame is scope A, channel A, type data. A failed write leaves the held line so the next beat retries.
**semantic-key:** PK-210

## 0. On approval

## 0. On approval

## 3. Edits

Java. SshTerminalPanel keeps liveLine. Printable characters append. Backspace pops one. Enter clears. Shift-Enter appends a newline. setDraftBeat runs feedComposerHeat on the change. Each pane keeps its own line.
**semantic-key:** PK-211

## 3. Edits

Java. SshClientApp.relayArmedMoods. After the pane walk, paintFrameStatus runs. A null panel returns before that call. The walk is unchanged. A null want is still an empty want. An empty want still clears a pane that was told.
**semantic-key:** PK-212

## 3. Edits

Java. SshConnectionPanel.holdThink remembers the cluster. parkThink seats it at the end of the button row, after the font control. applyBarFont still sizes its labels when the folded row is holding it.
**semantic-key:** PK-213

Java. FoldingPanel.seatThink takes the cluster. placeThink parks it on the button row while the bar is open, and seats it on the east of the folded menu row while the bar is folded. One cluster moves. It is not painted twice.
**semantic-key:** PK-214

Java. SshClientApp seats the cluster after the menu is installed.
**semantic-key:** PK-215

## 3. The build

Type-level tag, inside the class JavaDoc: ` * // #TypeName`. A type that already names a keeps that line. The source-prose page gains a heading for each new type. SshClientTheme and SshConnectionPanel keep their headings and gain one sentence. The Chrome heading gains one sentence. The class prose names what the type is, the seat it holds, and the step a reader needs.
**semantic-key:** PK-944
**semantic-key:** PK-944

## 2. What stays

PlateFaceUi keeps the idle fill, the hover fill, the hover edge, and the press fill. The seated letter adds the existing PLATE_SELECT fill and PLATE_SELECT_INK. Those colors are already on the theme. No new color key.
**semantic-key:** PK-216

## 3. The cause

A press that is still armed writes the letter. The rebuilt card ghosts when the letter is E. A press that has already lost armed writes nothing. armFocus still refreshes the inspector, so the screen still moves and the cell stays included. A later press, once the header is still, stays armed and the write lands. PlateFaceUi does not paint isSelected, so the four letters look the same when the mouse is up. E shows up as the ghost, on the card built at the end of the rebuild.
**semantic-key:** PK-217

## 0. This stamp

4. SshClientApp stages the comment.

## 4. The seat

SshClientApp keeps the relay. The comment above paintFrameStatus states the gate. That comment is the product edit in that file.
**semantic-key:** PK-218

## 4. The seat

SshEditQueue holds one pending post. offerSshEdit bumps the stamp and drops an unstarted post. The worker copies that pane on the EDT, counts off the EDT, and seats the integer on that pane when the stamp is still current. The heatbar paints when that pane is focused. The timer posts the focused pane and then paints the stored integer. It does not call sshLiveCount. paintFrameStatus paints the stored integer. A focus change posts the pane now in front. The private class is SshEditQueue. The private types are SshEdit and SshSeat. The package methods on the pane are liveCount and seatLiveCount. No new public type. No new public method.
**semantic-key:** PK-219
**semantic-key:** PK-219
**semantic-key:** PK-219

## 6. Product

[SshTerminalPanel.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/SshTerminalPanel.java) and the lines in [SshClientApp.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/SshClientApp.java). The parked lines in SshClientApp stay unstaged.
**semantic-key:** PK-945

## 2. What stays

The inspector still builds oldest first and reverses once. The header stays. The separators stay. The role lines keep their numbers. The facts line stays. The pin rules stay. debugSpliceId still previews and does not write the pin. Glass stays. moodHead stays. The shell frame stays. debug_mood stays. The settings seat stays. This row overrides the separate debug card. SshClientApp stays unstaged. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No new public type. No new public method. No new JUnit class.

## 0. This stamp

1. The Java shell launch passes `--enable-native-access=ALL-UNNAMED`. The IDE run configuration `SshClientApp` gains that VM parameter.
**semantic-key:** PK-220

## 1. Goal

The console that starts `SshClientApp` no longer warns that `SymbolLookup.libraryLookup` is restricted.
**semantic-key:** PK-221

## 2. What stays

The class stays `org.casserole.app.ui.ssh.SshClientApp`. The launch stays `java -cp` plus `LocalM2Classpath`. It does not become `exec:java`. `paintCaption` stays. The two libraries stay `user32` and `dwmapi`. The server launch stays. `application.yaml` stays. No new vault column. No new channel. No new AMWP type. No new library. No Cargo.toml edit. No new public Java type. No new public Java method. No new JUnit class.

## 3. The cause

JDK 26 treats `SymbolLookup.libraryLookup` as restricted native access. `SshClientApp` is an unnamed module, loaded from `target/classes`. The JDK prints four warnings and names `--enable-native-access=ALL-UNNAMED`. A future JDK blocks the call unless that access is enabled.
**semantic-key:** PK-222

## 4. The seat

`bin/aetheris-shell.ps1` passes `--enable-native-access=ALL-UNNAMED` on the shell `java` line. `bin/aetheris-shell.sh` passes the same flag. The stick launcher embedded in `bin/install-aetheris-shell.ps1` passes the same flag ahead of `-Duser.home`. Quote that flag. The IDE configuration `SshClientApp` in `.idea/workspace.xml` sets `VM_PARAMETERS` to `--enable-native-access=ALL-UNNAMED`. That workspace file stays unstaged.
**semantic-key:** PK-946

## 0. This stamp

1. Rust cell input and VT100 typing post a heatbar beat. The queue keeps the newest beat. The walk runs off the key. The heatbar paints the last seated integer. Java offerTokenEdit and SshEditQueue already do this.
**semantic-key:** PK-223

## 2. What stays

The folded bar stays. No new vault column. No new channel. No new AMWP type. No new library. No Cargo.toml edit. No new public Java type. No new public method. No new JUnit class. No decision block on this plate.

## 2. What stays

The header line stays the rule characters and the model word. SshTerminalPanel stays. No new vault column. No new channel. No new AMWP type. No new library. No Cargo.toml edit. No new public Java type. No new public method. No new JUnit class. No decision block on this plate.

## 2. What stays

FoldingPanel stays. The connection bar stays. The choice is not saved. BB2 keeps its own mark. The thinking pane stays. The folded thinking line still clears the bar. Debug Mood stays. Painted borders stay. Each user still keeps a separate mood. The same user does not reload. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. Every member frame still leaves together. One press still seats one cell. The model header still starts each thinking stream. The footer spinner still follows the vault on screen. No new vault column. No new channel. No new AMWP type. No new library. No Cargo.toml edit. No new public Java type. No new public method. No new JUnit class. No decision block on this stamp.

## 2. What stays

FoldingPanel stays. The connection bar stays. The BB2 tools mark stays. The other bar buttons keep their word width. The choice is not saved. BB2 keeps its own mark. The thinking pane stays. The folded thinking line still clears the bar. The grey line stays. The ink stays 0x8a8a8a. The line still starts six pixels after the mark and stops sixteen pixels before the right edge. Debug Mood stays. Painted borders stay. Each user still keeps a separate mood. The same user does not reload. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The thinking file stays one file. The in-memory log moves with the seat. Every member frame still leaves together. One press still seats one cell. The model header still starts each thinking stream. The footer spinner still follows the vault on screen. No new vault column. No new channel. No new AMWP type. No new library. No Cargo.toml edit. No new public Java type. No new public method. No new JUnit class. No decision block on this stamp.

## 2. What stays

The glass widget stays one strip. The chrome file aetheris-think.yaml stays one file. Height and closed stay on that widget. The VT100 strip stays and still calls finish. A console cell stays off this seat. Mood wire frames stay off this seat. Each user still keeps a separate mood. The armed cell of the same user does not reload the strip. A different cell in the same merge group keeps the strip because the key did not change. Replacing a socket stops only that pane's run. Stop, Clear, and Delete cancel on the pane that owns the run. The same cell and the same model keep the open block. A different cell or a different model starts a header. A later bar. The canvas thinking text is no longer one shared file. Each user keeps a file under aetheris-thinking. The legacy aetheris-thinking.txt stays on disk. The canvas seat does not read it and does not write it. That row stays as filed. This Java seat is stricter. The crate move for this delta waits. FoldingPanel stays. The connection bar stays. The BB1 fold stays. The BB2 tools mark stays. The folded thinking line still clears the bar. Debug Mood stays. Painted borders stay. The footer spinner still follows the vault on screen. Every member frame still leaves together. One press still seats one cell. No new vault column. No new channel. No new AMWP type. No new library. No Cargo.toml edit. No new public Java type. No new public method. No new JUnit class. No decision block on this stamp. The tags on this plate stay keyless. A temp driver is not in the tree.

## 0. This stamp

1. The font control is minus, the size, and plus. The range stays 8 to 36. The row incrementor, the soft-wrap column incrementor, and the inspector line incrementor stay that shape. The files are [SshConnectionPanel.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/SshConnectionPanel.java) and [SshClientTheme.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/SshClientTheme.java).

## 3. landing

**Client bytes.** `SshTtyConnector` strips that OSC from Scope T chunks before they are queued for JediTerm. A sequence split across chunks stays in a hold of at most 256 bytes. A longer hold is painted and the hold clears. The strip still runs when no listener is aimed. When a listener is aimed, `onConsoleModel(model, think)` runs off the EDT.
**semantic-key:** PK-224

**Menu.** `DarkJediTermWidget` keeps Import and adds Select model after it. The tip says Select model opens the Model Config modal for this console, the Ollama id is the model the next chat turn calls, Thinking is on or off for this console, and Grok and Pool stay on the cell. `SshTerminalPanel` keeps the runnable across a new widget, the same way Import is kept. `SshClientApp` wires it to `requestConsoleModel` after `selectSession`.
**semantic-key:** PK-225
**semantic-key:** PK-225

## 0. On approval

4. Build the edits below. No lambdas in our types. No Lombok. No JUnit. No new public type. No new package. No new vault column. No new channel. No new AMWP type. The new methods are `SshClientProperties.getProductTab` and `SshClientProperties.setProductTab`.

## 3. Edits

**Store.** `SshClientProperties` gains `UI_PRODUCT_TAB` (`ui.product.tab`), `PRODUCT_TAB_SSH` (`ssh`), and `PRODUCT_TAB_AETHERIS` (`aetheris`). `applyDefaultsForMissingKeys` puts `ssh` when the key is missing. `getProductTab` returns `aetheris` when the stored word is that word, and `ssh` otherwise. `setProductTab` stores `aetheris` when the argument is that word, and `ssh` otherwise, then calls `save`.
**semantic-key:** PK-226

**Click.** `SshClientApp` listens to the product tab pane. A change while writes are allowed stores `aetheris` when the index is 1 and `ssh` otherwise, and clears the restore flag. A change while writes are suppressed returns.
**semantic-key:** PK-227

## 3. Edits

The seat is the package-private `FoldingPanel` in `org.casserole.app.ui.ssh`. `SshClientApp` seats it in the center. The vertical control split leaves. `setJMenuBar` leaves. The same File, Tools, and Help bar is seated on the panel.
**semantic-key:** PK-228
**semantic-key:** PK-228

## 3. Edits

The Java session seat is `importIntoSession` in `SshClientApp`. It no longer shares the capped reader with Send file. `readImportStream` reads 8192-byte chunks into a growing buffer. It does not allocate one array of the file length. It does not refuse a text longer than 65536 characters. The binary sample stays the first 4096 bytes. An empty file stays refused.
**semantic-key:** PK-229

## 3. Edits

The seat is `SshClientTheme`. `applyPopup` paints one popup. The popup is opaque. The fill is the panel background. The words are the menu foreground. The border is the button background. The painter is the basic popup painter. Each plain item is opaque on those colors and uses the basic item painter. A nested menu uses the basic menu painter and the same popup paint. `installLookAndFeelDefaults` puts the same background and foreground on PopupMenu, Menu, and MenuItem before the window is built.
**semantic-key:** PK-230

## 3. Edits

The seat is `SshClientApp`. `paintCaption` runs when the window opens and when the window state changes. On Windows it reads the native handle and paints the caption. Any other system returns. A missing handle returns. A failure is caught and the window still opens.
**semantic-key:** PK-231

## 0. On approval

4. Build the edits below. No lambdas in our types. No Lombok. No JUnit. The new public type is `AppleScrollBar`. The public method is `install`. The public measures are `BAR_PX`, `THIN_PX`, `HANDLE_MIN`, `RADIUS_PX`, `ANIM_SEC`, and `PLATE`. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

## 3. Edits

The seat is `AppleScrollBar` in the existing ssh ui package. `install` arms a `JScrollPane`. A second call leaves the pane as it is. `SshClientTheme.applyScrollPane` calls `install`. The light-gray arrow bar leaves that method.
**semantic-key:** PK-232
**semantic-key:** PK-232

## 0. On approval

4. Build the edits below. No lambdas in our types. No Lombok. No JUnit. No new public type. No new public method. `AppleScrollBar` stays. The method stays `install`. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

## 3. Edits

The seat stays `AppleScrollBar`. `install` stays the arm. `applyScrollPane` stays the call. No new public type. No new public method.
**semantic-key:** PK-233
**semantic-key:** PK-233

## 0. On approval

4. Build the edits below. No lambdas in our types. No Lombok. No JUnit. No new public type. No new public method. `AppleScrollBar` stays. The method stays `install`. `BAR_PX` stays the measure. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

## 3. Edits

## 0. On approval

4. Build the edits below. No lambdas in our types. No Lombok. No JUnit. No new public type. No new public method. `AppleScrollBar` stays. `install` stays the pane arm. The package method is `armStrip`. `BAR_PX` stays the measure. No new package. No new vault column. No new channel. No new AMWP type. No new color key. No new library.

## 3. Edits

The seat stays `AppleScrollBar`. `install` stays the pane arm. `applyScrollPane` stays the call. No new public type. No new public method.
**semantic-key:** PK-234

## 3. Edits

`armStrip` arms one bar a host lays out itself. `DarkJediTermWidget.createScrollBar` calls it. JediTerm calls that method while the widget is built. Every new VT100 pane builds a widget, and a fresh session builds a fresh widget.
**semantic-key:** PK-235

## 3. Edits

The seat is `SshClientTheme`.

## 3. Edits

`QuietOcean` is the private theme. It returns that grey for the secondary grounds and for white. Primary colors stay. The black ink stays. Disabled ink stays the inactive grey. `installQuietEdges` seats the theme, then the tab keys, the menu line, the menu frame, the button edge, the spinner edge, the field edge, the check-box fill, the combo row, and the dialog frames. `quietFill` is the flat grey list a check box reads. The selected tab face is the panel charcoal. An unselected tab, the strip, and the content frame are the quiet grey. The tab shadow stays black, so the frame still reads. A themed field still replaces its edge with the button face. The cell rounds stay.
**semantic-key:** PK-236

## 2. What stays

Font colors stay. Selection highlights stay. The sapphire stays. The two find painters stay. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The white letters on P and C stay. The menu green stays. The caret on an open pane stays white. The round field stays a round of 8. The green line and the gray line stay. The cell stays a round of 6. The cell split stays clear. The Apple bar stays. The button face stays `#3c3c3c`. A form field stays `#1a1a1a`. The cell panes stay black. The panel ground stays `0x121212`. The frame, the split, and the status stay `0x0c0c0c`. The bevel shadow stays black. The bevel lip stays `0x0a0a0a`. The cell bar stays the short quiet bar. The other split bars stay. The cell divider thickness stays 11. The one-touch arrows on the other splits stay. Drag on the other splits stays. Pane widths of the cells stay. Yes and No stay. P and C stay. The thinking clear mark stays. The pin box, the field, Clear, and OK stay. The facts line stays where it sits. The Ocean edges stay. QuietOcean stays. The bevel stays. The per-mille save on the inspector share stays. The words stay SSH, Aetheris, and Canvas. The Aetheris tab stays disabled until the mux answers. File, Tools, and Help stay. No yaml edit. The card-round hunks stay unstaged.

## 3. Edits

`installPlateTabs` seats `PlateTabUi` on the SSH and Aetheris tabs and on the Canvas tab. `installPlateButton` seats `PlateButtonUi` on the folded SSH and Aetheris buttons. Selected fill is `0x005c80` and the word is `0xc0deff`. Idle fill is `0x323232` and the word is `0xd7d7d7`. Hover fill is `0x404040`, the stroke is `0x969696`, and the word is `0xf5f5f5`. The corner is 6. The strip is `0x0d0d0d`. The content frame stays off. A disabled tab wears half ink. The seats are `SshClientTheme`, `FoldingPanel`, and `AetherisNotebookPanel`.
**semantic-key:** PK-237
**semantic-key:** PK-237
**semantic-key:** PK-237
**semantic-key:** PK-237

## 2. What stays

The color a button already wears stays the rest color. P and C rest white. Yes rests black. No rests white. Plate fills stay. Plate strokes stay. A selectable tab keeps its word. A menu title keeps its word. File, Tools, and Help stay. The Connected chip stays. An arrow mark has no word. Check boxes stay. Labels stay. Font colors stay. Selection highlights stay. The sapphire stays. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The menu green stays. The caret on an open pane stays white. The round field stays a round of 8. The green line and the gray line stay. The cell stays a round of 6. The cell split stays clear. The Apple bar stays. The button face stays. A form field stays. The cell panes stay black. The panel ground stays. The frame, the split, and the status stay. The bevel shadow stays black. The bevel lip stays. The cell bar stays the short quiet bar. The other split bars stay. The folded thinking row stays. The inspector bar stays. The pin box, the field, Clear, and OK stay. The facts line stays. The Ocean edges stay. QuietOcean stays. The words stay SSH, Aetheris, and Canvas. The Aetheris tab stays disabled until the mux answers. No yaml edit. The card-round hunks stay unstaged.
**semantic-key:** PK-238

## 3. Edits

`seatButtonHover` seats one listener on the toolkit. A mouse enter on an enabled button saves that button foreground and paints the word `#00FF66`. A mouse exit restores the saved color. The shift is immediate. `PlateButtonUi` paints that emerald for the word while the saved color is held. The plate fill and the plate stroke stay. The listener is installed once from `installLookAndFeelDefaults`. The tooltip initial delay grows by 50 ms once. The seat found 750 ms. The wait becomes 800 ms. The word does not wait on that timer. The seat is `SshClientTheme`.
**semantic-key:** PK-239
**semantic-key:** PK-239

## 2. What stays

The text margin stays. The grip stays. The fold stays. The folded row stays. Close stays. C stays. The black line on C stays. The cell panes stay. The Apple bar stays. The round field stays a round of 8. The green line and the gray line stay. The cell stays a round of 6. The cell split stays clear. The cell bar stays the short quiet bar. Font colors stay. Selection highlights stay. The sapphire stays. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The caret on an open pane stays white. The emerald word stays. The tooltip wait stays. The button face stays. A form field stays. The cell panes stay black. The panel ground stays. The frame, the split, and the status stay. The bevel stays. The inspector bar stays. The pin box stays. The facts line stays. The Ocean edges stay. QuietOcean stays. The words stay SSH, Aetheris, and Canvas. File, Tools, and Help stay. No yaml edit. The card-round hunks stay unstaged.

## 2. What stays

The folded row stays. The folded rule stays on the menu. The menu stays at the top while the bar is open. File, Tools, and Help stay. The words stay SSH, Aetheris, and Canvas. The Aetheris tab stays disabled until the mux answers. Connect stays the first button of the open row. The door note stays above the line. The grip stays. The fold stays. The thinking text stays clear. The emerald word stays. The tooltip wait stays. The plate fills stay. The plate strokes stay. Font colors stay. Selection highlights stay. The sapphire stays. The prompt ink stays green. A reply with text stays yellow. The think ink stays cyan. The caret on an open pane stays white. The round field stays a round of 8. The green line and the gray line stay. The cell stays a round of 6. The cell split stays clear. The Apple bar stays. The button face stays. A form field stays. The cell panes stay black. The panel ground stays. The frame, the split, and the status stay. The bevel stays. The cell bar stays the short quiet bar. The inspector bar stays. The pin box stays. The facts line stays. The Ocean edges stay. QuietOcean stays. No yaml edit. No Cargo.toml edit. The card-round hunks stay unstaged.
**semantic-key:** PK-240

## 3. Edits

The Java seat is `FoldingPanel`. The open head keeps the menu on the north and the connection form in the center. A one-pixel panel sits on the south of that head. The ink is the panel foreground. The Java default of that ink is #dcdcdc. The live theme may already hold another panel foreground. This plate reads no home file and adds no color key. The preferred size is 1 by 1. The minimum size is 1 by 1. The maximum width is the widest integer and the maximum height is 1. The line leaves with the open bar. The folded rule stays a vertical 1 by 18 on the folded row, after Aetheris and before the menu.
**semantic-key:** PK-241

## 3. Edits

Help is already seated after Token Calc. Token Calc is a menu item on the bar. A menu item grows across the remaining width, so Help sat past the edge. The item maximum size becomes its preferred size. Help stays on the bar. Token Calc still opens the token window. The seat is `installMenu` in `SshClientApp`.
**semantic-key:** PK-242

# Aetheris pre-plan 001

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library. The private class is `FlowRow`. The private method is `flowLabel`.
**semantic-key:** PK-243

# Aetheris pre-plan 001

No Lombok. No lambdas in our types. No new public type. No new public method. No new package. No new channel. No new AMWP type. No new color key. No new library. The private class is `PlateFaceUi`. The private methods are `seatPlateFace` and `plateSkip`.
**semantic-key:** PK-244

## Dependencies pitied

SshConnectionPanel.layoutControls. The private method is barRule. FONT_TIP stays. [chrome.rs](../../../../../../../../rust/aetheris/aetheris-client/src/chrome.rs) paints the same order on the button row. ui.separator follows Close Pane. The line between Connect and the SSH tab stays. Stays. the_folded_bar_sits_on_one_row retargets the Font size seat. No new library. No Cargo.toml edit. No new color key.
**semantic-key:** PK-947

## Dependencies pitied

`Vt100TokenWindow.heuristic` repeats that math. `messageCount` prefers a stored count. `sum` adds the message counts. `feedComposerHeat` wears `tokenRingUsed` on the Aetheris tab. On the SSH tab a held Roux session wears `rouxSessionUsed`. An unheld session wears `heuristic` on `sessionMemoryText`. `openTokenCalc` opens the same integer.
**semantic-key:** PK-948

## Dependencies pitied

The held integer stays the base. The unsent line of the focused pane is the only addition. An empty draft adds nothing. The 200 ms beat stays. ComposerHeatbar.showHeuristic already lunges when the integer changes. No server call on a key. No new library. No Cargo.toml edit.
**semantic-key:** PK-949

## Dependencies pitied

installImportPopup on mousePressed calls armFocus, then focusCanvas or focusGroupCell. That steals focus during the press. rebuildTabs removes every card. restoreHold moves the viewport and focusHeader lands on the collapse button. The release is not delivered. PlateFaceUi paints press and hover. It does not paint isSelected. Ghost is cell.state == EXCLUDED, painted on the card the rebuild builds.
**semantic-key:** PK-245

## Out of scope

The Rust crate. The pin rules. Glass appendMoodHeader. moodHead. The shell frame. The settings seat. debug_mood. A new public type. A new public method. A new JUnit class. [chrome.rs](../../../../../../../../rust/aetheris/aetheris-client/src/chrome.rs). [grid.rs](../../../../../../../../rust/aetheris/aetheris-term/src/grid.rs).

## Out of scope

The Rust crate. The send. The pin write. The inspector header. The one reverse. The role-line numbers. Glass. moodHead. The shell frame. [chrome.rs](../../../../../../../../rust/aetheris/aetheris-client/src/chrome.rs). [grid.rs](../../../../../../../../rust/aetheris/aetheris-term/src/grid.rs).

## Dependencies pitied

`SshClientApp` calls `SymbolLookup.libraryLookup` for `user32` and for `dwmapi` when it paints the Windows caption. The process is an unnamed module, loaded from `target/classes`. JDK 26 prints the restricted-method warning and names the flag. The shell launch stays `java -cp` plus `LocalM2Classpath`. It does not become `exec:java`. The class stays `org.casserole.app.ui.ssh.SshClientApp`. No new public type. No new public method. No new JUnit class. `.idea/workspace.xml` stays unstaged.
**semantic-key:** PK-950

## Seat

`bin/aetheris-shell.ps1`, `bin/aetheris-shell.sh`, and the stick launcher written by `bin/install-aetheris-shell.ps1` pass `--enable-native-access=ALL-UNNAMED` to the shell JVM. The command pages that quote that launch carry the same flag. The IDE configuration `SshClientApp` in `.idea/workspace.xml` gains the same VM parameter. That file stays unstaged.
**semantic-key:** PK-246

## The cause

Java offerTokenEdit and SshEditQueue keep the newest beat and walk off the key. Rust was building the inspector and the console text on the frame that paints the key. That walk is the lag.
**semantic-key:** PK-247

## What stays

## 4. The floor

`mvn -q -DskipTests compile` from ` The live POST is not made. A pool turn is not sent. A live SSH connect is not made. The product files are `SshClientProperties` and `SshClientApp`.
**semantic-key:** PK-951

# Aetheris pre-plan 001

No Lombok. No lambdas in our types. The new public type is `AppleScrollBar`. The method is `install`. No new package. No new channel. No new AMWP type. No new color key. No new library.
**semantic-key:** PK-248

# Aetheris pre-plan 001

No Lombok. No lambdas in our types. No new public type. No new public method. `AppleScrollBar` stays. The method stays `install`. No new package. No new channel. No new AMWP type. No new color key. No new library.
**semantic-key:** PK-249
**semantic-key:** PK-249

# Aetheris pre-plan 001

# Aetheris pre-plan 001

No Lombok. No lambdas in our types. No new public type. No new public method. `AppleScrollBar` stays. `install` stays the pane arm. The package method is `armStrip`. No new package. No new channel. No new AMWP type. No new color key. No new library.
**semantic-key:** PK-250

## 2. Fingerprint

Client always sends one AMWP Init frame at channel-open (`SshTtyConnector.tryAmwpInit`). Dump of that frame:
**semantic-key:** PK-251

## 2. Fingerprint

1. The combo calls `SshClientApp.focusConnectedUser`.
**semantic-key:** PK-252

6. `SshTtyConnector.demuxLoop` reads the later Scope A frame and, because the listener is null, does not deliver it.
**semantic-key:** PK-253

## 9. Applied

`SshTtyConnector` holds up to 64 Scope A frames while its listener is null. `setAmwpListener` delivers that hold only to the listener just aimed. `aimAetherisListener` still aims one connector, and only after that user's vault is the open one.
**semantic-key:** PK-254

### Cause

One Aetheris pane keeps one run. `runningCellId` and `runningUser` are one pair. The spinner is one widget. `SshClientApp` aims one Scope A listener, on the connector of the vault that is showing, and sets the other connector's listener to null.
**semantic-key:** PK-255

### Listeners stay aimed

`aimAetherisListener` stops clearing the other connector. `SshClientApp` keeps the connectors it has already aimed. A second aim on the same connector does nothing. `clearAetherisListener` is not called from a user switch.
**semantic-key:** PK-256

### What is already one per user

The SSH pane is one `TerminalSession` and one `SshTtyConnector`. That connector is the client end of the mux. The listener sits on it. The in-flight cell is a fact about that user. The answers waiting to paint are already a map under that user.
**semantic-key:** PK-257
**semantic-key:** PK-257

## 4. Resolution

Newlines collapse to LF. A body longer than 65536 characters is refused before confirm. The status says `Paste exceeds 65536 characters.` Nothing is written. The cap is `ConsolePaste.MAX_CHARS`, the same number as the paste editor and `casserole.chat.max-paste-chars`.
**semantic-key:** PK-258

## 2. Fingerprint

Ctrl+V and Ctrl+Shift+V on a focused terminal open `PasteEditorDialog`. OK called `sendPasteAndSubmit`, which writes the bracketed paste and then `\r`.
**semantic-key:** PK-259

### 1.4 What is in the POC today vs not

- Standalone JediTerm SSH client (`SshClientApp`) that does **not** start Spring Boot.
**semantic-key:** PK-260

### 2.2 Two processes, two entry points

- Client: run `org.casserole.app.ui.ssh.SshClientApp` from the IDE. Connect to `localhost:2222`.
**semantic-key:** PK-261

### 3.2 Client: `~/.ssh-client.properties`

Standalone from Spring. `SshClientProperties` (`java.util.Properties`, UTF-8). Created with defaults on first run.
**semantic-key:** PK-262

#### Paste (server)

- Ctrl+V / Ctrl+Shift+V / Send file open `PasteEditorDialog`.
**semantic-key:** PK-263

## 7. Standalone SSH client

Reusable Swing pieces so they can later move into a larger app: `SshConnectionPanel`, `SshTerminalPanel`, `SshSessionController`, `SshClientTheme`, `SshClientProperties`.
**semantic-key:** PK-264
**semantic-key:** PK-264
**semantic-key:** PK-264
**semantic-key:** PK-264
**semantic-key:** PK-264

### 7.3 Session / PTY

`SshSessionController` runs connect/disconnect/reconnect on daemon workers serialized on `workerLock`. UI callbacks hop to the EDT in `SshClientApp`.
**semantic-key:** PK-265

### 7.3 Session / PTY

5. `SshTtyConnector` wraps inverted streams; `write` is **synchronized** (JediTerm can write from multiple threads).
**semantic-key:** PK-266

## 2. Assessment

The console context menu is JediTerm's right-click menu, built in `TerminalPanel.createPopupMenu`. Copy, Paste, and the rest of that menu stay. `DarkTerminalPanel` adds `Import...` after them.
**semantic-key:** PK-267

## 2. Assessment

The console insert is `SshSessionController.sendPaste`. That is a bracketed paste with no trailing Enter. The shell places the text at its cursor. Enter in the terminal still submits. A one-line `cmd>` paste stays on the prompt.
**semantic-key:** PK-268

## 2. The Swing lesson

Swing keeps the one-touch control inside the divider. DarkDivider fills the bar, then super.paint draws the buttons on that bar. The button does not cross into the panes. The triangle fits the button.
**semantic-key:** PK-269

## 2. The Swing lesson

Swing turns one-touch on. The divider is 11 pixels. DarkDivider fills the bar, strokes the edge, then calls super.paint. That call draws the one-touch buttons: a filled control with a triangle fitted inside. The upper button points at the canvas. The lower button points at the inspector. The pair stays centered on the bar.
**semantic-key:** PK-270

## 9. Language rules

Asks for professional-grade class and method documentation, inner notes where complexity warrants, and PlantUML where a junior reader needs the picture. A module carries `//!`. A type and a function carry `///`. The prose states the contract. An inner note sits where the complexity warrants it. PlantUML markup sits in the source where a junior reader needs the picture. The doc comment cites the specs the way the Java class banner does. `SshTtyConnector` states the contract, then `// ` and the keys, then `@author ` and the date. A Rust doc comment states the contract, carries the keys, and carries `@author ` with the date. Keeps `// ` as the comment bridge. The same keys in the Rust doc comment keep the round-trip grep. The reader walks from the type back to the spec.
**semantic-key:** PK-952

### Shell

As-built on `SshClientApp`, `SshConnectionPanel`, `DarkJediTermWidget`, `SshSessionMode`, `SshClientProperties`, and `SshClientTheme`.
**semantic-key:** PK-271
**semantic-key:** PK-271
**semantic-key:** PK-271
**semantic-key:** PK-271
**semantic-key:** PK-271
**semantic-key:** PK-271

## 18. Terminal component

JediTerm's seam is the one this client already uses. The widget paints. `TtyConnector` carries the bytes. `SshTtyConnector` is our connector, and Apache MINA sits behind it. The Rust component copies that split. A connector trait. A widget. `russh` behind one connector.
**semantic-key:** PK-953

## 8. Package 4. Session

- On connect, write the same client init `SshTtyConnector.tryAmwpInit` writes on channel `0x0002`, type init. Wait 1500 ms. A valid init or data frame whose magic is `0xAE` enters the mux. Later paints are channel `0x0001` payloads only. Other channels are read and dropped. Keepalive matches `sendKeepalive` on a 2000 ms timer. No `0xAE` answer, or a failed parse, unread the peeked bytes and paint the raw PTY.
**semantic-key:** PK-272

## 5. The door

The door remembers the login. The file is `~/.casserole/ssh-client.yaml`, the same store as `SshClientProperties`. The keys are `ui.connection.host`, `ui.connection.port`, `ui.connection.username`, `ui.connection.password`, and `ui.connection.rememberPassword`. The `accounts` list keeps one row per user name, with `username` and `password`.
**semantic-key:** PK-954

The host key check matches the Java client: accept the server key. That is `AcceptAllServerKeyVerifier` on `SshSessionController`, and app-design records it as Version 1 with no `known_hosts`. A pin is an interest below. This stand-up connects the way the Java client connects.
**semantic-key:** PK-273

## 2. Widget

The widget is the JediTerm seat. `DarkJediTermWidget` is the Java example of the extension. This library owns the paint.
**semantic-key:** PK-274

## 3. Connector

`TtyConnector` is the Java seam. `SshTtyConnector` is the Aetheris connector behind it, with Apache MINA on the far side.
**semantic-key:** PK-275

### P3 — Client yaml store + account combo

- Evolve `SshClientProperties` (or sibling `SshClientStore`) to read/write **`~/.casserole/ssh-client.yaml`** via SnakeYAML. Keep the same getters the panel uses plus `List` of accounts.
**semantic-key:** PK-276

- `SshConnectionPanel` — username `JTextField` → editable `JComboBox`. Selecting a known account fills password if stored. Remember + successful connect appends/updates that account.
**semantic-key:** PK-277

### P4 — Multi-pane terminals + New Term + dividers + menu

- `TerminalSession` (name as you like) — one `SshTerminalPanel` + one `SshSessionController` + last `ConnectRequest` + state. Listener attaches the connector to **this** panel only; UI hops to EDT; status/button enablement only if this session is **focused**.
**semantic-key:** PK-278
**semantic-key:** PK-278
**semantic-key:** PK-278
**semantic-key:** PK-278
**semantic-key:** PK-278

- Custom divider via `SshClientTheme` (dark fill `#3a3a3a`, light `#8a8a8a` outline) applied to **all** splitters including connection vs terminals.
**semantic-key:** PK-279

- `SshConnectionPanel.Listener` — `newTermRequested()`. **New Term** button → dialog Horizontal / Vertical → split **focused** pane. Copies host/port from focus; user from combo.
**semantic-key:** PK-280

- `SshClientApp` — tree of sessions; track focus (widget focus listener). Connection bar `applyConnectionState` + field fill follow **focused** session. Connect/Disconnect/Reconnect/Send file/Clear act on focus. `fontSizeChanged` → **every** `SshTerminalPanel.setFontSize` (shared `DarkJediTermSettings` + refresh each widget).
**semantic-key:** PK-281
**semantic-key:** PK-281

## Sequencing gotchas

6. Client yaml parser must work **without** a running Spring context (`SshClientApp.main`).
**semantic-key:** PK-282

## Order

On `POC_2026_08_25_00`, sequential commits (or one slice) is fine. Do not start P4 until P3 yaml store compiles. Do not wire New Term until a `TerminalSession` owns its own controller.
**semantic-key:** PK-283

### 5.2 Panes (iTerm2-style) — locked with New Term dialog

Nested `JSplitPane`s, not tabs. Each pane = own `SshTerminalPanel` + own `SshSessionController` + own SSH shell. Like four independent terminals in one window.
**semantic-key:** PK-284
**semantic-key:** PK-284

### 5.3 Font, shells, exclusive, layout persist

| Item | Lock |
|---|---|
| Font size | **One** size for the window. Spinner change updates **all** panes. Keep the shared `DarkJediTermSettings`; on change, refresh every live widget (do not invent per-pane fonts) |
| Shells | One SSH shell per pane. Never share a MINA channel |
| Same username | Disk-backed: server exclusive (red line). RAM-only: allowed. Client does not add a second exclusive layer |
| Layout persist | **None.** Restart = one empty pane |
**semantic-key:** PK-285

The DarkJediTermWidget type is the JediTerm widget with a public font refresh. The name JediTerm means the terminal widget this class extends. The upstream font refresh is protected, so this type exposes #applyFontSize. The context menu adds Import, Transfer Conversation, and Select model beside the JediTerm copy and paste items. The menu action does nothing until the matching request is set.
**semantic-key:** PK-1227
**semantic-key:** PK-1227

The DarkTerminalPanel type is the terminal panel with a public font refresh. The protected upstream refresh is reinitFontAndResize. The context menu adds Import, Transfer Conversation, and Select model after the JediTerm items.
**semantic-key:** PK-1228
**semantic-key:** PK-1228

The listener runs the stored transfer action. The listener does nothing when that action is null.
**semantic-key:** PK-1229

The listener runs the stored model action. The listener does nothing when that action is null.
**semantic-key:** PK-1230

The shell already kept the window width and height. It now also keeps where the window sits on the desktop, and whether it is maximized. The new keys are ui.frame.x, ui.frame.y, and ui.frame.maximized. They sit beside ui.frame.width and ui.frame.height in the shell client file.
**semantic-key:** PK-1231
**semantic-key:** PK-1231
**semantic-key:** PK-1231
**semantic-key:** PK-1231
**semantic-key:** PK-1231
**semantic-key:** PK-1231

The SshClientApp window is the dark SSH client. JediTerm is the terminal widget inside each pane. The frame holds the FoldingPanel, the SshConnectionPanel, the TerminalSession list, and the AetherisNotebookPanel. Connect and disconnect go through SshSessionController. The window uses the Metal look from SshClientTheme.
**semantic-key:** PK-1232
**semantic-key:** PK-1232

The listener restarts the save timer when the frame changes. A resize also reports the new width and height to FoldingPanel#noteSpan. A move only restarts the timer.
**semantic-key:** PK-1233

The listener confirms an exit and paints the caption when the window opens. A closing event calls #confirmAndExit. An opened event calls #paintCaption.
**semantic-key:** PK-1234

The listener restarts the save timer when the window state changes. #paintCaption runs after that restart.
**semantic-key:** PK-1235

The runnable selects this pane. The notebook is asked for the console model.
**semantic-key:** PK-1236

The runnable posts an SSH edit for this pane. The count is not computed on this call.
**semantic-key:** PK-1237

The runnable sends the imported text as a paste. Focus returns to the terminal. A failure shows a notice titled Import.
**semantic-key:** PK-1238

The sendFileAsPaste method reads a file and opens the paste editor. A refused read returns immediately. The editor does not submit the text by itself.
**semantic-key:** PK-1239

The listener opens settings. A missing notebook does nothing.
**semantic-key:** PK-1240

The SshClientProperties store is the per-user SSH client file. The file is ssh-client.yaml inside the .casserole folder under the user home directory. The first load may import .ssh-client.properties from the user home directory once. That import runs only when the YAML file is not already there. The theme reads its colors from this store.
**semantic-key:** PK-1241

The SshTerminalPanel is one JediTerm host for a single SSH pane. JediTerm is the terminal widget that paints the session. A new SSH session builds a new widget. Disconnect leaves that widget so the last buffer stays on the screen.
**semantic-key:** PK-1242
**semantic-key:** PK-1242

Vt100StatusLine names the strip. pty paints PTY. A missing token and any other token paint VT100. A connect asks for xterm-256color or vt100. mood active follows the link when the gate is on. mvn -q -Dtest=Vt100StatusLineTest test passed, 6 tests, 0 failures. The window stayed closed. No Session::start. No chrome::run. The live POST was not made. A pool turn was not sent. A live SSH connect was not made. The proof is eca73da3.
**semantic-key:** PK-1296

Vt100TokenWindow names the sum, the heuristic, the available count, and the menu seat. A stored count is kept. A missing count uses the heuristic. Empty text is 1. (length + 3) / 4 counts the rest. An empty chat sums to 0. The Aetheris plate keeps the canvas estimate. mvn -q -Dtest=Vt100TokenWindowTest test passed, 6 tests, 0 failures. The window stayed closed. No Session::start. No chrome::run. The live POST was not made. A pool turn was not sent. A live SSH connect was not made. The proof is 26a93c76.
**semantic-key:** PK-1297

ToolsThinking names the bar and the reveal. A closed pane starts at slide 0. An open pane keeps its slide. The fold opens. The other plate is the same seat. mvn -q -Dtest=ToolsThinkingTest test passed, 6 tests, 0 failures. The window stayed closed. No Session::start. No chrome::run. The live POST was not made. A pool turn was not sent. A live SSH connect was not made. The proof is 1115a253.
**semantic-key:** PK-1298

SshClientProperties. 0. Locked answers this plan uses. The code tag is the next pass.
**semantic-key:** PK-1304

SshSessionController. 3. As-built (what the plan must not forget). The code tag is the next pass.
**semantic-key:** PK-1310

SshTtyConnector. 3. As-built (what the plan must not forget). The code tag is the next pass.
**semantic-key:** PK-1315

SshEditQueue keeps the newest post. The key writes the unsent line and posts. The walk copies that pane and counts off the key. A result seats the integer on that pane when its stamp is still current, and the heatbar paints it when that pane is in front. The timer posts the focused pane and paints the stored integer. It does not call sshLiveCount. A click still calls sshLiveCount. The private class is SshEditQueue. The private types are SshEdit and SshSeat. The package methods are liveCount and seatLiveCount. No new public type. No new public method. No new G-key. No new vault column. No new channel. No new AMWP type. No new color key. No new library. No Cargo.toml edit. No side move on this plate. Java moves. Rust stays. [chrome.rs](../../../../../../../../rust/aetheris/aetheris-client/src/chrome.rs) stays. [grid.rs](../../../../../../../../rust/aetheris/aetheris-term/src/grid.rs) stays. Parked hunks stay unstaged. The paging memorandum stays unstaged. The yaml stays. F-132 through F-137 stay the open Book'em in the working tree. F-01 through F-191 stay locked. FEAT-007 stays closed. This row is not a Feature. The next free Garage number stays L45. OL-31 stays. The next publish marker stays PM-58 until the land.
**semantic-key:** PK-1328
**semantic-key:** PK-1328

TerminalSession. 2. Gaps from the Stage 0 code scan — **locked** `[PUFF]` 002 → `[LOCK.... The code tag is the next pass.
**semantic-key:** PK-1347

ConnectRequest. 2. Gaps from the Stage 0 code scan — **locked** `[PUFF]` 002 → `[LOCK.... The code tag is the next pass.
**semantic-key:** PK-1348

The Rust crate. The pin rules. Glass appendMoodHeader. moodHead. The shell frame. The settings seat. debug_mood. A new public type. A new public method. A new JUnit class. [chrome.rs](../../../../../../../../rust/aetheris/aetheris-client/src/chrome.rs). [grid.rs](../../../../../../../../rust/aetheris/aetheris-term/src/grid.rs). SshClientApp parked hunks.
**semantic-key:** PK-1353

The Rust crate. The send. The pin write. The inspector header. The one reverse. The role-line numbers. Glass. moodHead. The shell frame. [chrome.rs](../../../../../../../../rust/aetheris/aetheris-client/src/chrome.rs). [grid.rs](../../../../../../../../rust/aetheris/aetheris-term/src/grid.rs). SshClientApp parked hunks.
**semantic-key:** PK-1354

The strip reads the focused pane. Each pane keeps its own word. A focus change paints that pane's word. A change in that pane's Model Config paints the word at once. The word is ollama, ollama plus the id, grok, grok plus the id, or pool. A connect asks the shell for the model saved in the console model file and paints the answer without opening Model Config. A restart reads that same file, so the pane shows the model it had. The file stays one file per SSH user. Two panes of one user share that file. Each pane still paints the word its own shell last reported. A result that Select model did not arm does not open the dialog and does not seat the dialog fields. A dismissed Model Config still ignores a late result, so an empty think line cannot clear the checkbox. The canvas strip still names the selected cell. A blank pane word still counts as ollama for the token budget. An engine word that contains ollama still uses 131072 when the managed count is zero. Any other SSH engine still uses zero. Effort, temperature, burner, think, the Ollama show ask, and the remote model list stay. The factory id stays grok-4.3. The files are [AetherisNotebookPanel.java](../../../../../../../../src/main/java/org/aetheris/swing/AetherisNotebookPanel.java), [SshClientApp.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/SshClientApp.java), [TerminalSession.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/TerminalSession.java), [Vt100StatusLine.java](../../../../../../../../src/main/java/org/casserole/app/ui/ssh/Vt100StatusLine.java), and [Vt100StatusLineTest.java](../../../../../../../../src/test/java/org/casserole/app/ui/ssh/Vt100StatusLineTest.java). The product hash is ca04d662. This backfill does not amend that commit.
**semantic-key:** PK-1360
**semantic-key:** PK-1360
**semantic-key:** PK-1360

`mvn -q -DskipTests compile` from `C:\Projects\MCP\Casserole`. The window stays closed. The live POST is not made. A pool turn is not sent. The product files are `SelectModelCommand`, `CommandProcessorFactory`, `CommandContext`, `ChatShell`, `AmwpAwareShell`, `AmwpClientListener`, `SshTtyConnector`, `DarkJediTermWidget`, `SshTerminalPanel`, `SshClientApp`, and `AetherisNotebookPanel`.
**semantic-key:** PK-1362
**semantic-key:** PK-1362
**semantic-key:** PK-1362
**semantic-key:** PK-1362
**semantic-key:** PK-1362
**semantic-key:** PK-1362
**semantic-key:** PK-1362
**semantic-key:** PK-1362
**semantic-key:** PK-1362
**semantic-key:** PK-1362

`mvn -q -DskipTests compile` from `C:\Projects\MCP\Casserole`. The window stays closed. The live POST is not made. A pool turn is not sent. A live SSH connect is not made. The product files are `SshClientProperties` and `SshClientApp`.
**semantic-key:** PK-1363

SshTtyConnector. 2. Gaps from the Stage 0 code scan — **locked** `[PUFF]` 002 → `[LOCK]` 003. The code tag is the next pass.
**semantic-key:** PK-1396

`SshConnectionState` is a public type in this package.

Import sits on the VT100 console menu and on the File menu. Both open the file dialog. The chosen file is inserted at the console cursor.
**semantic-key:** PK-1400
