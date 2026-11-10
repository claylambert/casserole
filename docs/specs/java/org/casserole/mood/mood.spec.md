# org.casserole.mood

Component file. It carries how this package is built.

**Depends:** This container names no other spec.

# The mood insert is the invitation

The stamp names the build. The approved picture is how the mood insert renders in the transcript. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0203 PK-0204 PK-0205 PK-0206 PK-0207

# The mood insert is the invitation

The mood insert is approved. The mood line, then shape:, then the two sentences. The mood tokens are not repeated between the sentences. Java moves. Rust stays. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-0322 PK-0323 PK-0324 PK-0325 PK-0326

## One mood desk places the canvas and the Roux session

One component, MoodDesk, behind MoodDoor, in org.casserole.mood, owns mood transitions, insertion, deletion, and override for the canvas full-push and for the Roux shell session.

MoodDesk.DESK is the desk. MoodDesk.ACTIVATE is the one activate sentence. The package methods are normalize, canvasSystems, moodHead, sshLine, announceBody, and parseShell. The door methods are seat, adopt, and porchRules. TerminalSession.moodTold and setMoodTold remember whether this connection announced the gate. Canvas, group, and pipe still full-push the pair after the host place while the switch is on. The shell still uses the checkbox and a saved line. A session that already has turns gets the current pair and loses any prior pair. Startup adopts a stored pair, and the client announces the gate on connect, including a clear when the shell gate is off. A declared test battery covers the desk, the canvas prefix, and the shell session.

The Java client carries this. The Rust client does not change. chrome.rs and grid.rs stay. The two mood files stay. No new vault column, no new channel, no new shell message type, no new color key, and no new library are added.

Canvas, group, and pipe share one head. While the footer switch is on and a line is saved, the system lines are an empty host place, then the mood string, then the activate line, then the turns. The server drops that empty place when the prompt is blank, and fills it when a prompt is configured. The mood pair stays behind that place.

On the shell door, the console completeTurn is the path. While the checkbox is on and a line is saved, the system lines are the pinned host, then the mood string, then the activate line, then the turns. placeHost inserts the configured host at index 0 only when that text is absent. A blank prompt puts the pair first. A session that already has turns loses any prior pair and keeps the turns.

On the glass door, the shaping system line comes first, then the pair when the field holds a line. The footer switch does not gate the glass.

On the pool door, session/new, the pair is the porch rules. The host stays out. An extend keeps the rules of the open session.

**Truth.** MoodDesk in org.casserole.mood is the one place that inserts, replaces, and clears the mood pair. Canvas, group, and pipe send an empty host place, the mood string, the activate line, and the turns while the footer switch is on and a line is saved. The shell sends the pinned host, the mood string, the activate line, and the turns while the checkbox is on and a line is saved. A session that already has turns drops any prior pair and keeps the turns. The glass adds the pair after the shaping line when the field holds a line, and the footer switch does not gate that chat. Pool session/new takes the pair as porch rules and leaves the host out.

**Reason.** Canvas full-push and the Roux session were each inventing the pair. One desk owns the transitions so a connect, a startup adopt, and a session that already has turns all announce the same gate. The server still owns the empty host place: it drops that place when the prompt is blank and fills it when a prompt is configured. The glass is a field, not the footer switch. An extend must not replace the porch rules of the session that is already open.

**Avoidances.** No new vault column, channel, shell message type, color key, or library. The Rust client is not edited. The two mood files stay. The host stays out of the pool porch. The footer switch does not gate the glass. placeHost does not insert a second copy of a host that is already present.


**semantic-key:** PK-4156 PK-4157 PK-4158 PK-4159 PK-4160 PK-4161 PK-4162 PK-4163 PK-4164 PK-4165 PK-4166

## The mood insert is the invitation

The mood insert is the invitation. The head is mood:, the saved line, shape:, the opener, and the closer. The mood line is not written again between the two sentences.

The user-turn splice is the invitation. The opener stays: I'm opening a window with this atmosphere… would you like to step into it with me? The closer stays: Shape your replies around this mood — let it breathe through the words. The line is not written again between those two sentences. A newline after the closer joins the raw turn. There is no blank line between the head and the turn. spliceTurn writes that head. unspliceTurn strips it. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. lineAtTurnHead reads either head. inviteHead, invitedLine, and olderSpliceLine stay private. The public methods stay spliceTurn, unspliceTurn, and lineAtTurnHead.

The pin rules stay. Zero or one turn already in the transcript keeps that head on the first turn. More than one turn, and a newly defined or updated mood, puts that head on the turn being sent. The glass appendMoodHeader stays the shaping line, then the opener, the field, and the closer. The switch does not gate that chat. moodHead still counts the opener, the line, and the closer. The shell frame stays shell_mood and the line. The inspector header, while the line is present and the switch is on, is mood:, the line, shape:, the opener, and the closer. The mood line is still previewed at 80. The two sentences are the full text. Debug Mood paints that same head as the card above the pinned turn. Each physical line of the head is a mood role line. Painting the card does not move the pin. This rule overrides the card text and the send head. The settings version stays 1. debug_mood stays. The settings place stays.

No new public type, no new public method, and no new test class are added. The Java client carries this. The Rust client does not change on this rule. A known gap in paste undo stays.

Canvas, group, and pipe send an empty host place, then the turns. The pinned turn's user text starts with mood:, the line, shape:, the opener, and the closer. The mood line is not repeated between the two sentences. Zero or one turn already in the transcript: that head is the first turn. More than one turn and a new or updated mood: that head is the turn being sent. The server replaces the empty host place. The mood block is not a system row. A stored head that still starts with the opener, the line, and the closer is stripped, and the new head is written. On the shell, the pinned user turn's text starts the same way. The shell frame stays shell_mood and the line. Switch off sends no splice and leaves the pin. The glass stays the shaping line, then the opener, the field, and the closer when the field holds a line. The switch does not gate that chat. Pool porch rules read from a spliced user head are mood:, the line, shape:, the opener, and the closer. A leading system triple still reads as the opener, a blank line, the line, a blank line, and the closer. In the inspector, while the line is present, mood: stays the line, and shape: is the opener and the closer on the following lines. Debug Mood paints that same head as the card above the pinned turn. Painting the card does not move the pin.

**Truth.** The spliced user head is the label mood:, the saved line, the label shape:, then the sentence I'm opening a window with this atmosphere… would you like to step into it with me?, then the sentence Shape your replies around this mood — let it breathe through the words, then a newline and the raw turn, with no blank line between the head and the turn. The saved line is not repeated between the sentences. An older head that starts with the opener, the line, and the closer is stripped and rewritten. The glass does not use this head. moodHead still counts the opener, the line, and the closer. The mood line preview is 80 characters. The two sentences are shown in full.

**Reason.** The invitation is the mood line and the two sentences, in that order, so the model meets the atmosphere before it is asked to shape the reply. Repeating the mood tokens between the sentences doubled the line. The older order is still recognized so a stored head is rewritten rather than stacked. The glass keeps the shaping line and the two sentences because that chat is not the user-turn splice. The count stays on the opener, the line, and the closer, not on the labels.

**Avoidances.** The line is not repeated between the sentences. There is no blank line between the head and the turn. The glass is not rewritten into the invitation and is not gated by the switch. The shell frame stays shell_mood and the line. The pin rules do not change. Painting does not move the pin. The Rust client is not edited. The known paste-undo gap stays. The example is not substituted for these two sentences.


**semantic-key:** PK-4249 PK-4250 PK-4251 PK-4252 PK-4253
