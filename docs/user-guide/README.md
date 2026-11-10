# Aetheris user guide

Every plate in the guide is a screen-shot of the Java desktop. Swing is the Java toolkit that renders the client. The Rust desktop follows the same behavior and look and feel, and Rust matches the Java theme.

The terminal is the VT100 session, the place you type to the machine you connected to. The canvas is the notebook of turns. The host is the server side that runs the model. Those three pieces share one login. The introduction on the [root page](../../README.md) says why each piece has its own job.

## Features

This list names the controls on the Java desktop.

A feature with a link has screen-shots. The sentences under those screen-shots describe the control. The screen-shots are there so you can see the control and read what it does.

A feature without a link exists in the Aetheris Client. That feature works. The list names it even though this guide does not describe it yet. This guide does not describe that control with a screen-shot. The user guide covers the more complex operations only at present.

The linked pages below describe the linked features. The features without links are listed here so you can see the full list and try those controls on your own.

Care went into how the Client is used. The Client is still a work in progress. It should be easy to pick up the features this guide does not describe directly.

- **[The desk](the-desk/README.md)** is this Java window. It holds the connection bar, the menus, the terminal, and the canvas.
  - The window has two tabs above the work. SSH is the terminal. Aetheris is the canvas. The Aetheris tab stays disabled until the session can carry the canvas. The window remembers which tab was in front.
  - The mark on the connection bar folds that bar. The fields leave the window. The terminal and the canvas below use that height. The mark ^ brings the bar back. Connect folds the bar. A resize while the session is up folds it. Disconnect opens it. The choice is not saved.
  - While the bar is folded, an SSH button shows the terminal and an Aetheris button shows the canvas. The Aetheris button stays disabled until the canvas session is up. The folded row also keeps the link chip. The chip reads Connected in green while the session is up.
  - A fresh open of the window starts with one terminal pane. The window remembers its place, its size, and whether it was maximized.
  - [Font size](the-desk/README.md) is the type size for this window, in points. The same size goes to the bar, the menus, every terminal pane, and the canvas. The spinner runs from 8 to 36.
  - [File](the-desk/README.md) is the menu that opens New Term, Close Pane, Import, Settings, and Exit.
  - Exit asks `Exit the SSH client?` The title of that question is Confirm exit. Yes closes the window. No leaves the window open.
  - [Settings](the-desk/README.md) is the dialog of global switches for this window. The switches are painted text borders, Debug Mood, and hover text. Each switch is saved for the next open. The file is aetheris-settings.yaml in the .casserole folder under your home directory. Escape closes the dialog.
  - [Import](the-desk/README.md) on the File menu reads a file into the focused terminal at the cursor. That dialog does not put cards on the notebook. The notebook Import button loads a canvas file onto the canvas.
  - [Help](the-desk/README.md) opens the help text for this window. About Aetheris opens the about text and the Aetheris picture. Both dialogs sit over this same window.
  - [Tools](the-desk/README.md) opens Show Thinking. Show Thinking reveals the thinking pane of the tab in front.
  - Token Calc sits on the menu bar. It is not an item inside the Tools menu. It opens the token window for the tab in front. A terminal pane reports the length of the session text and the count of non-blank rows. The canvas tab opens the notebook token window.
  - [New Term](the-desk/README.md) splits the focused pane. Horizontal places the new pane beside it. Vertical places the new pane under it. The keys are Ctrl+Shift+D and Ctrl+Shift+E. The new pane has no session. The pane you split keeps the session it already had.
  - [Close Pane](the-desk/README.md) closes the focused pane. The key is Ctrl+Shift+W. The last pane stays.
- **[Connect](connect-to-the-server/README.md)** is the path that opens the session from the connection bar. The terminal and the canvas both use that session.
  - [Mode](connect-to-the-server/README.md) is the kind of terminal this window asks the server to open. VT100 is the kind a fresh window selects. Standard PTY asks for a pseudoterminal the server calls xterm-256color.
  - [Host](connect-to-the-server/README.md) is the machine the session connects to. A fresh window writes localhost. localhost means this same machine.
  - [Port](connect-to-the-server/README.md) is the number the server listens on for the login. A fresh window writes 22. The Casserole server listens on 2222. Letters in that field are read as 22.
  - [Username](connect-to-the-server/README.md) is the account the session logs in as. The list remembers accounts. The roster keeps letters, digits, a dot, an underscore, and a hyphen.
  - [Password](connect-to-the-server/README.md) is the secret for that account. The field shows dots.
  - Remember password keeps that secret for the username. The store is ssh-client.yaml under the .casserole folder in your home directory.
  - [Connect](connect-to-the-server/README.md) opens the session with the host, the port, the user, and the password on the bar. The chip then reads Connected. The chip means the session is up.
  - Disconnect closes the session. The connection bar opens again.
  - Reconnect closes the session and opens it again with the fields on the bar.
  - Clear Terminal wipes the focused pane.
  - Load File reads a text file into the paste editor. OK inserts the text. Cancel drops it.
- **[The terminal](the-terminal/README.md)** is the VT100 session on that login. VT100 is the terminal kind a fresh window selects.
  - [The pane](the-terminal/README.md) is where you type to the host. The green chat> line is the host prompt. Enter sends the line you typed.
  - [Ctrl+C](the-terminal/README.md) sends the interrupt to the host. It is not copy. Copy in this pane is Ctrl+Shift+C. An empty highlight leaves the clipboard as it is.
  - [Paste](the-terminal/README.md) opens the dialog titled Edit paste. Ctrl+V opens it. Ctrl+Shift+V opens it too. Enter inserts a newline. Ctrl+Enter accepts. OK accepts the same way. Cancel drops the text.
  - The [right-click menu](context-menu-options/README.md#ssh) is the menu on the terminal pane. It holds Find, Open as URL, Copy, Paste, Select All, Clear Buffer, Page Up, Page Down, Line Up, Line Down, Import, Transfer Conversation, and Select model.
  - Find searches the pane. Open as URL stays disabled until the selection is a web address. Copy stays disabled until text is selected. Paste sends the clipboard. Select All selects the pane. Clear Buffer keeps the last line. The key for Clear Buffer is Ctrl+L. Page Up is Shift+Page Up. Line Up is Ctrl+Up. Line Down is Ctrl+Down.
  - Import on that menu reads a file and inserts it at the terminal cursor. The file is not sent until you press Enter.
  - Select model opens Model Config for the console. Family is the group of models this turn uses. The groups are Ollama, Grok, and Pool. The next console turn uses that group.
  - Transfer Conversation copies this terminal session onto the canvas after OK. Cancel leaves both sides as they are.
  - The terminal status line is the sentence at the bottom of the SSH tab. The left side says Connecting, or Connected with the host and the port, or Disconnected with the reason. The rest of the line says vault: disk, then VT100 or PTY, then the air-gap word, then the engine. While SSH Mood Active is on, the line also says mood active.
  - A thin heat bar on that same row shows the token fill against the context window. Amber means 70 percent. Red means 90 percent. The needle rests on the estimate. The bar does not ask the host.
- **[The canvas](the-canvas/README.md)** is the notebook of cards. A card is one turn.
  - The notebook shows one tab for each canvas in the vault. A vault is the file on this machine that keeps the canvases for the connected user. A new vault names the first canvas Canvas 1. Before the vault is open, the only tab is titled Canvas. Selecting a tab shows that canvas. This window has no button that creates another canvas.
  - [I, E, P, and O](the-canvas/README.md) are the four marks on a card. I keeps the prompt and the reply. E drops both, and an excluded card is not sent. P sends the prompt only. O sends the reply only. Ctrl+Shift with I, E, P, or O writes that one mark.
  - [The padlock](the-canvas/README.md) is the lock that holds a card in the head. A closed green padlock means the card is locked. An open grey padlock means the card is unlocked. The count of locks comes from the server. How this window activates the lock is [The locked head](../../aetheris/java/README.md#the-locked-head). The count and the keys are [The Reichenbach sink](../../casserole-server/README.md#the-reichenbach-sink).
  - [The working set](the-canvas/README.md) is the list the focused card would send. The inspector shows that list, newest first. The send uses the earlier cards first, starting with the oldest. Later cards stay on the notebook.
  - [The facts line](the-canvas/README.md) is the line above the inspector controls. It shows what the host reported for the session. The air-gap word and the context size on that line are not part of the working set.
  - [Soft wrap](the-canvas/README.md) folds a long inspector line onto the next row. The wrap-column count is the column where that fold happens. The line-length count is how far a painted preview runs before an ellipsis. The cards and the send keep the full text.
  - [T](the-canvas/README.md) opens the token window for the notebook. The open does not ask the host, and it does not send. OK closes the window.
  - [The divider](the-canvas/README.md) is the bar between the notebook and the inspector. A drag shares the width. The arrows hide one side or the other.
  - [View](the-canvas/README.md) opens the stored reply of one card in its own window. The window is read-only. The button stays disabled until the card has a reply. The window size is remembered for this vault user. Ctrl+F finds in that reply. Esc hides the find bar. Copy in that window copies the whole reply and leaves the find field as it is.
  - In the View window, ^ moves to the previous match and wraps to the end. v moves to the next match and wraps to the start. A count shows where the match sits. Typing in the field highlights every match. The reply text does not change.
  - Copy on the card places that card's whole reply on the clipboard. The prompt stays. The button stays disabled until the card has a reply.
  - The fold mark on a card collapses that card to a short line of the reply, or of the prompt when there is no reply. The text stays. The choice is saved on the turn. Collapse All and Expand All do that for every card on this canvas. Other canvases stay as they are.
  - Hide Collapsed takes the folded cards off the canvas. Show Collapsed brings them back.
  - The burner box marks the turn as ephemeral. An ephemeral turn is one whose text an export can leave out. To terminal refuses it. A cloud Run on a burner turn is denied. The text stays on the card.
  - A card whose stored state is not one of the four marks shows load error. The fold button on that card reads ? and stays disabled. That card cannot be expanded, edited, or sent.
  - An error notice slides down from the top of the canvas pane. The title reads Error. Close dismisses the notice. The notice stays until it is closed. The card does not keep that text.
  - The user pull-down sits at the east end of the first canvas button bar. Choosing a name switches the vault on screen to that connected user. An empty selection says that no user is selected.
  - The thinking spinner sits on the control bar while a turn is thinking. The glyph turns, and the words read thinking plus a clock. The spinner follows the vault that is running. It hides when the thinking stops.
  - The canvas status line is the sentence at the bottom of the Aetheris tab. It names the vault, whether the session is AMWP or raw, the air-gap word, and the engine of the focused card. The word mismatch appears when the session air-gap differs from the settings. Search, export, import, pull, delete, stop, and a refused send each print their own notice on that same line.
  - The v on a card opens the group row. That row holds the execution-target field, Group, Join, and Ungroup. The row starts hidden. ^ folds the row away. The choice is not saved.
  - The execution-target field shows the target this turn would send. An empty field sends to Ollama and is not stored as a name. A name that begins with grok/ sends to Grok when a key is present. The word pool stores pool. The Model button opens Model Config. The execution-target field does not open that dialog.
- **[The conversation](the-conversation-canvas/README.md)** is one notebook in use. A notebook is the set of cards on the canvas in front.
  - [New turn](the-conversation-canvas/README.md) adds an empty included card at the end of this canvas and puts the cursor in its prompt. The new card keeps the prior card's model.
  - [The heatbar](the-conversation-canvas/README.md) estimates tokens from the keystrokes in the prompt. Amber means about 70 percent of the window. Red means about 90 percent. The estimate does not ask the host.
  - [Undo and redo](the-conversation-canvas/README.md) move through the prompt one character at a time. Undo is Ctrl+Z. Redo is Shift+Ctrl+Z.
  - [The row count](the-conversation-canvas/README.md) is how many lines an open prompt and a folded reply keep. The smallest count is 1. The count is saved with the shell. The shell is the saved layout for this window. The row count is not the wrap column. It is not the line length.
  - [Expanded View](the-conversation-canvas/README.md) shows the whole reply of that one card. Unchecked, the reply stays in a window the same height as the prompt. A new turn keeps the prior card's choice.
  - [Delete](the-conversation-canvas/README.md) removes the card that has focus from this canvas. A card in a group removes that whole group and its merge card. The turns that remain are numbered from 1. The button stays disabled when the delete would leave the canvas empty.
  - Clear turns removes every card on this canvas and leaves one empty card. The other canvases stay. The button cannot undo that clear.
  - Stop cancels the focused turn when that turn is running. Other turns in a group keep running. The reply already stored stays. If nothing is running, the status line says nothing is running.
  - Preview rebuilds the inspector for the focused turn. It lists the host line, the mood line, and the earlier turns this call would keep. It does not send.
  - Export writes this canvas to a file. A burner card can leave its text out of that file. Import adds the turns in a file as copies, with new ids. Turns already on the canvas stay. A file over 1 MiB is refused.
  - Pull copies the highlighted terminal line, or the last non-empty line, into a new turn. That new turn starts Excluded.
  - To terminal writes the focused turn into the terminal session after you confirm. It writes the reply, or the prompt when there is no reply. A burner turn is refused.
- **[The turn](the-turn/README.md)** is the act of sending one card to its model.
  - [Run](the-turn/README.md) sends the focused card. Ctrl+Enter does that send when the main view is showing. An open View window does not send. When no card has focus, the key runs the last card.
  - [The chain](the-turn/README.md) sends the earlier cards the prompt names, then this card. The prompt names a card with `{{cell[N].output}}`. In that token, cell means the card. The key is Ctrl+Shift+F5. Run sends only the focused card.
  - [Model](the-merge-cell/README.md) on the card opens Model Config. Family is the group of models this card uses. The groups are Ollama, Grok, and Pool. The model row is the choice inside that group. Ollama is the group a fresh card uses. Pool chats on the Heavy pool and does not use the API key.
  - Ollama settings on that dialog are the model list, Thinking, and Effort. The arrow on the model list reads the models registered on the Ollama host. A typed id that is not in the list stays. Thinking asks this card to think. Effort appears when that model offers levels. The chosen level is what think sends.
  - Grok settings on that dialog are API, Model, Effort, Send effort, Stream, Temperature, Max tokens, Burner cloud, and Thinking. API is the kind of call this window makes. The row on this window is chat. A chosen model row writes grok/ plus that id. Send effort decides whether that level is sent with the request. Stream true is what sends reasoning into the thinking pane. Temperature starts at 1. Max tokens starts at 128000. Burner cloud allows an ephemeral turn to be sent to that cloud model. An unchecked box refuses that send.
  - Pool settings are one sentence and a Thinking box. The sentence says the card chats on the Heavy pool, with no API key, no model id, no effort, and no temperature. Thinking on Pool keeps its text on the footer strip between sessions. That text is not stored on the turn.
- **[The merge cell](the-merge-cell/README.md)** is a group of cards that share one joined reply.
  - [Group](the-merge-cell/README.md) makes this card the first member and adds a merge card under it. Group does nothing if this card is already in a group.
  - [Join](the-merge-cell/README.md) brings in another card. Click a member first, then Join on the card you want to add. Each member keeps its own model.
  - [Run](the-merge-cell/README.md) sends the members, each as its own call. The merge card is not sent. When every member has completed, the joined reply holds one JOIN line for each member. A failed or cancelled member fails the merge and leaves that reply blank.
  - [Add Cell](the-merge-cell/README.md) sits on the merge card only. It adds an empty member in the next column. The new member copies the last member's target and settings. Focus moves to the new prompt. The joined reply stays. The button is visible while the group row is closed.
  - [Ungroup](the-merge-cell/README.md) returns this card to an ordinary turn. When the last member leaves, the merge card returns to an ordinary turn too.
  - [Duplicate group](context-menu-options/README.md#canvas) copies the group. The new prompts and replies are empty. The models stay. The item is on the canvas menu, and only while the card is grouped. The key is Ctrl+Shift+G. A click that only shows the item does not run it.
- **[Canvas right-click](context-menu-options/README.md#canvas)** is the menu a right-click opens on a card.
  - Import reads a text file into the prompt. A file over 1 MiB is refused. A binary file is refused. A reply click omits Import, and the status line reads import refused on output. A merge card and an output-only card refuse Import as well.
  - Transfer Conversation copies this canvas into the terminal session after OK. Cancel leaves both sides as they are.
- **[Mood](special-features/README.md#mood)** is a saved line the conversation can carry in front of the prompt.
  - The model of the selected card defines the mood. That model is the one Model Config last stored on the card you are on.
  - Approve saves the line. Cancel leaves the stored line as it was. The round switch turns the saved line on and off.
  - SSH Mood Active shows mood active on the SSH status line while the switch is on.
  - Debug Mood lists the shape of that line in the inspector. The shape is the sentence the inspector uses to describe that mood. Clear mood asks `Clear the Mood Setting?` Yes removes the saved line. No leaves it. The prompt on the card stays the words that were typed.
- **[The pin filter](special-features/README.md#the-pin-filter)** is the P above the canvas. That P is the pin. It is not the prompt-only mark on a card.
  - The bar is the pin checkbox, the word field, the red x, OK, and a v that folds the bar. P stays green while the pin is on, even when the bar is folded.
  - The word field, the red x, and OK stay disabled until the checkbox is checked. OK writes Included and Excluded from those words. A match needs every remaining word. The filter drops a, the, and, of, and to. A card that does not match becomes Excluded. An Excluded card is not sent. OK on an empty field excludes every card whose text can be read. Clearing the checkbox does not restore the letters.
- **[Search](special-features/README.md#search)** is the field on the second bar. The field is the text to find on this canvas.
  - Search keeps the cards whose text contains the words. It selects the first match. When nothing matches, search stays off until you press the red x. An empty field matches nothing. A percent sign and an underscore are ordinary characters. The Included and Excluded marks stay as they were.
  - The red x clears the search and brings the cards back. The status line says search cleared. The pin marks stay. Clearing the search does not change them.
- **[The thinking pane](special-features/README.md#the-thinking-pane)** is the log of model reasoning. It opens from Tools, Show Thinking, on the tab in front.
  - v folds the text. The mark reads ^ while the text is hidden. C clears the log. Close hides the pane. The top edge sets the height.
  - Thinking checked, with Stream set true, sends the reasoning into that pane. The banner is the model word. The status reads running, then Stopped after C. The text on the card stays.
- The v at the left of the first button bar folds that bar and the second bar away. The canvas uses the height. ^ brings both bars back. The choice is not saved.
- The v at the right of the first button bar opens the second bar. Search, Export, Import, Pull, To terminal, the mood switch, Mood, Hide Collapsed, and Show Collapsed sit on that bar. The bar starts hidden. ^ folds it away. The choice is not saved.

Each screen-shot is the maximized window at the pixels of that display. The Font size spinner is set to 20 before the picture is taken. The label is in the margin. A line runs from the label to the control. The highlight is on the control. The sentence under a screen-shot is the whole explanation. That same sentence is the text a screen reader reads for the picture.

## The books

The books are the pages of this guide, in the order a session needs them. A session is one login from this window to the host.

1. [The desk](the-desk/README.md) is this Java window. It holds the connection bar and the menus. The other books begin after that window is open.
2. [Connect to the Server](connect-to-the-server/README.md) opens the session from the desk. The terminal and the canvas both use that session.
3. [The terminal](the-terminal/README.md) is the VT100 session, the place you type to the host. It depends on the desk. It does not require the canvas.
4. [The canvas](the-canvas/README.md) is the notebook of cards. A card is one turn. It depends on the desk.
5. [The conversation Canvas](the-conversation-canvas/README.md) is one notebook in use. It depends on the desk and on the canvas.
6. [The turn](the-turn/README.md) is the act of sending one card to its model. It depends on the canvas.
7. [The merge cell](the-merge-cell/README.md) is a group of cards that share one joined reply. It depends on the desk. This page has fourteen screen-shots.
8. [Context menu options](context-menu-options/README.md) is the menu a right-click opens. SSH is first. The canvas is second. It depends on the desk.
9. [Special Features](special-features/README.md) describes the mood, the pin filter, the search on the second bar, and the thinking pane. The model is the Grok API id grok-4.3. This page has thirty-nine screen-shots. It depends on the desk.

## Where the server decides

A screen-shot shows one click or one key on the window. The picture stopped on that click or that key. When a file the server reads, or the server's behavior, decides what that click or that key does, the sentence under the screen-shot links to the page that states the rule.

The [root page](../../README.md) states the account, the model path, and the air gap. The [server page](../../casserole-server/README.md) states the sink, the allowance, and the lock count. The [Java client page](../../aetheris/java/README.md) states how this window activates the locked head. A locked head is the run of cards the padlock can hold.
