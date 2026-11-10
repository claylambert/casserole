# aetheris-client::mood

Component file. It carries how this package is built.

**Depends:** [rust/aetheris/aetheris-client/aetheris-client.spec.md](../aetheris-client.spec.md)

## Wire

The console door order is unchanged. The pinned host stays first. The mood string follows. The activate line follows. The turns follow.

No system line is added, removed, or reordered.

**Truth.** The SSH mood flag for the focused canvas user is a boolean in ~/.casserole/aetheris-ssh-mood.yaml, version 1, keyed by sanitized name. An empty user disables the box. A name that sanitizes to anon is not stored. A blocked file is left unread and is not overwritten. A missing file starts empty. Startup and connect reseat the panes of armed users. Rust ssh_mood is the glass copy of that canvas user, and ssh_users is the map. The shared file aetheris-mood.yaml keeps version, line, on, width, and height.

**Reason.** One shared mood file cannot remember which canvas user armed SSH. A second file, loaded after the shared file, stores that boolean per sanitized name. The box has to freeze when the mood is held or the user is empty, or a paint would write a flag the user did not change.

**Avoidances.** Do not open a second revision. Do not rewrite slices already locked, and do not close the one-transcript smoke. Do not stage hunks beyond the named hunks. Do not retarget branch, action, stage, or the when field. Do not add a lambda, Lombok, JUnit, a G-key, a public type, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not change version, line, on, width, or height on aetheris-mood.yaml. Do not drop the label SSH Mood Active. Do not store anon. Do not overwrite a blocked file. Do not touch a raw session. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.


**semantic-key:** PK-0151

## The seat

One mood record is the seat. The opener, the line, and the closer splice onto the head of one turn's user text. The format is the opener, a newline, the line, a newline, the closer, a newline, then the raw turn. spliceTurn, unspliceTurn, and lineAtTurnHead are the public methods on MoodDesk. rulesFromTurn stays private. seat stays the system-row helper. Canvas, group, and pipe no longer prepend canvasSystems. The empty host seat stays. The server still replaces it. The vault cell keeps the raw user text. Assemble splices the outbound copy.

The pin is a cell id, or the word head, per canvas, in aetheris-mood.yaml. Version stays 1. The keys pending and pins are written in place. A missing pin means head. It is not a vault column.

A user turn is a kept cell that contributes user text, including a merge sink. Zero or one such turn already ahead of the target keeps the pin at the head, and the splice rides the first included user cell in the send. More than one, and a newly defined or updated mood, sets pending. The Run that sends the next turn consumes pending and pins that target cell. The same trimmed line is not an update. Two approves before a send use the latest line and one move. The pin stays until the next define or update. Further turns of the same mood append after the pin. Marks do not move the pin. An excluded pin cell contributes no lines. The send places the splice on the first included user cell at or after the pin that is in this send. A run whose target sits before the pin sends no splice. Reorder keeps the cell id. The record travels with the cell. deleteFocusedTurn walks the pin back one remaining cell when the removed ids contain it. None behind that hole returns the pin to head. clearTurns sets that canvas pin to head and clears pending. A new session clears the line, the switch, the pins, and pending. The switch off leaves the pin and sends no splice. The switch on with the same held line puts the splice back at that pin.

SSH has no cells. The spliced user message in the Roux username region is the pointer. moodHeld, moodPinOrdinal, and moodMovePending sit on ChatSession. appendUser stores the raw text unless the stored user ordinal is the pin and the move is not pending, and then it stores the splice. A define or update with one stored user turn, or with none, writes the splice onto that first user turn and drops a stored system mood row. A define or update with more than one stored user turn sets pending and leaves the old splice until buildPrompt of the next turn. That build drops the system mood rows, strips the stored splices, sets the pin to the user count about to be added, and splices the new user message. A stored system triple with no user splice still rides through seat until the line changes. Switch off strips the stored splice, keeps moodHeld and the pin, and clears pending. Switch on of that held line writes the splice back onto the pin and does not jump. reset and clearTurns drop the turns, set the pin ordinal to 0, clear pending, and leave the mood line. They do not re-seat a system triple. ChatSession has no single-turn delete. clearTurns and reset are the return to the first turn. replaceTurns clears the console mood with the turns. adopt reads a leading system triple, and otherwise the first spliced user head, and that user ordinal becomes the pin. porchRules returns the system triple when one is present, and otherwise the spliced user head, as the opener, a blank line, the line, a blank line, and the closer. A narrow Roux window does not promise a mid-list splice. The full prompt does. getWindow stays. Glass appendMoodHeader stays. The switch does not gate that chat. The shell frame stays shell_mood and the line, or shell_mood_clear. Replies above the pin stay. The pin does not delete them.

No new public type is added. No new G-key, vault column, channel, AMWP type, color key, library, or Cargo.toml edit is added. There is no side move on this plate. The public methods are spliceTurn, unspliceTurn, and lineAtTurnHead. Java moves. Rust stays. The crate delta for this splice waits until it is named. The Rust mood seat already seated the prior mood in the crate. grid.rs stays. chrome.rs stays. MoodDeskTest gained aSplicedTurnWearsTheTripleAtItsHead. That gain is battery maintenance. It is not a new JUnit class. SshMoodMemoryTest was maintained. ConsoleMoodTest and PoolMoodRulesTest were not edited.


**semantic-key:** PK-0202

## Dependencies

MoodDesk already seats one opener, one line, and one closer. The one-desk seat put that triple at the head. The footer-switch seat gates it and keeps the SSH checkbox as the availability bit. The pool-hold seat already full-repushes a pool session when porch rules change. The Rust mood seat already seated that triple in the crate as system lines. This plate moves the Java place. The crate delta for this splice waits. spliceTurn, unspliceTurn, and lineAtTurnHead are methods on the existing desk. The pin is user state in aetheris-mood.yaml. Version stays 1. It is not a vault column. Canvas cell text stays unspliced. SSH stores the splice on the pinned user message so adopt can read it. getWindow stays. chrome.rs stays. grid.rs stays. No new library is added. Cargo.toml is not edited. No new JUnit class is added.

The proof runs the named battery: MoodDeskTest, SshMoodMemoryTest, ConsoleMoodTest, and PoolMoodRulesTest. That battery passed on the working tree. The Rust client crate was not checked. A crate test was not run. The window stays closed.


**semantic-key:** PK-0321

## What stays

Clear mood, Approve, and Cancel stay. The mood switch stays. The saved line stays in aetheris-mood.yaml.

Canvas, group, and pipe doors stay. The console door order stays. The pinned host stays first. The mood string follows. The activate line follows. The turns follow. placeHost still inserts the host at index 0 only when absent. A raw session stays untouched. A pane that already holds the same line sends nothing. The one-transcript plan stays open for smoke. Its type list stays unbuilt. The open batch already in the working tree stays open. The Rust draft  stays as filed.

The Java Mood modal was built with Clear, Approve, and Cancel. SSH Mood Active is seated on this row. The status strip passed the mood gate as false. This row passes the focused pane user line. The server already consumes shell_mood and shell_mood_clear. Rust already paints SSH Mood Active. The empty Rust note on this batch is not a row.


**semantic-key:** PK-3729

## Wire

The console door order is unchanged. The pinned host stays first. The mood string follows. The activate line follows. The turns follow.

No system line is added, removed, or reordered.

**Truth.** The Mood modal shows a checkbox labeled SSH Mood Active, to the left of Clear mood, enabled when the canvas user name is non-empty. An armed user with the switch on and a saved line seats that line on every connected AMWP pane of that user. A pane that already holds the line sends nothing. An empty want clears only a pane that was already told, by shell_mood_clear. A non-empty want sends shell_mood and the line on scope A, channel A, type data. A failed write keeps the held line for the next beat. Rust does not gain a crate-only change on this row.

**Reason.** The modal had Clear, Approve, and Cancel, and the status strip was passing the mood gate as false. The server already consumes shell_mood and shell_mood_clear, and Rust already paints the checkbox, so this row seats the Java box and the relay. The per-user store and the live typing beat are the other two slices in the same tree.

**Avoidances.** Do not open a second revision. Do not rewrite slices already locked, and do not close the one-transcript smoke or build its type list. Do not stage hunks in chrome.rs or SshClientApp.java beyond the named hunks. Do not retarget branch, action, stage, or the when field. Do not add a lambda, Lombok, JUnit, a G-key, a public type, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not drop Clear mood, Approve, Cancel, the mood switch, or aetheris-mood.yaml. Do not change door order or let placeHost insert a host that is already present. Do not touch a raw session. Do not send when the pane already holds the same line. Do not clear a pane that was never told. Do not drop the held line after a failed write. Do not treat the empty Rust note as a row. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.


**semantic-key:** PK-3730
