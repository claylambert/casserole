# Context menu options

A context menu is the menu a right-click opens. This book shows each item and what it does. It does not describe the action.

The first half is the SSH terminal. SSH is the encrypted login this window uses to reach the host. The second half is the Aetheris canvas. The canvas is the notebook of turns. The menu bar is [the desk](../the-desk/README.md). The Model button on a card is not a context menu. That button opens Model Config. The screen-shots of that dialog are in [the merge cell](../the-merge-cell/README.md).

This book has eighteen screen-shots. If a menu item is not visible, this book does not show a picture of it. This book does not invent a name for an item it does not show.

## SSH

Right-click the terminal pane. The menu that opens is the context menu for that pane. The menu in these screen-shots is Find, Open as URL, Copy, Paste, Select All, Clear Buffer, Page Up, Page Down, Line Up, Line Down, Import..., Transfer Conversation, and Select model.

| |
| --- |
| <h2>Find</h2><br><br>![Find is highlighted.](images/ssh-01-find_130.png)<br><br>Find is highlighted. Find opens the search strip for this terminal. The shortcut on the item is Ctrl+F. |
| <h2>Open as URL</h2><br><br>![Open as URL is highlighted.](images/ssh-02-open-as-url_130.png)<br><br>Open as URL is highlighted. The item is disabled in this screen-shot. It becomes available when the selection is a web address. |
| <h2>Copy</h2><br><br>![Copy is highlighted.](images/ssh-03-copy_130.png)<br><br>Copy is highlighted. Copy is disabled in this screen-shot. No text is selected. A selection lets Copy place that text on the clipboard. The shortcut is Ctrl+Shift+C. Ctrl+C in this pane is the interrupt. |
| <h2>Paste</h2><br><br>![Paste is highlighted.](images/ssh-04-paste_130.png)<br><br>Paste is highlighted. Choosing Paste sends the clipboard into the terminal. The shortcut on the item is Ctrl+Shift+V. Ctrl+V and Ctrl+Shift+V open the Edit paste dialog. That dialog is [the terminal](../the-terminal/README.md). |
| <h2>Select All</h2><br><br>![Select All is highlighted.](images/ssh-05-select-all_130.png)<br><br>Select All is highlighted. Select All selects the text in the terminal buffer. |
| <h2>Clear Buffer</h2><br><br>![Clear Buffer is highlighted.](images/ssh-06-clear-buffer_130.png)<br><br>Clear Buffer is highlighted. Clear Buffer clears the scrollback and the screen and keeps the last line. The shortcut is Ctrl+L. |
| <h2>Page Up</h2><br><br>![Page Up is highlighted.](images/ssh-07-page-up_130.png)<br><br>Page Up is highlighted. Page Up scrolls the terminal up one screen. The shortcut is Shift+Page Up. |
| <h2>Page Down</h2><br><br>![Page Down is highlighted.](images/ssh-08-page-down_130.png)<br><br>Page Down is highlighted. Page Down scrolls the terminal down one screen. The shortcut is Shift+Page Down. |
| <h2>Line Up</h2><br><br>![Line Up is highlighted.](images/ssh-09-line-up_130.png)<br><br>Line Up is highlighted. Line Up scrolls the terminal up one line. The shortcut is Ctrl+Up. |
| <h2>Line Down</h2><br><br>![Line Down is highlighted.](images/ssh-10-line-down_130.png)<br><br>Line Down is highlighted. Line Down scrolls the terminal down one line. The shortcut is Ctrl+Down. |
| <h2>Import...</h2><br><br>![Import... is highlighted.](images/ssh-11-import_130.png)<br><br>Import... is highlighted. Import... on this menu reads a text file and inserts it at the cursor. Enter is not sent. A disconnected pane says Not connected. File, then Import... is [the desk](../the-desk/README.md). |
| <h2>Transfer Conversation</h2><br><br>![Transfer Conversation is highlighted.](images/ssh-12-transfer-conversation_130.png)<br><br>Transfer Conversation is highlighted. Transfer Conversation copies this Roux session onto the canvas after you press OK. Roux is the conversation stored for this terminal session. Cancel leaves both sides as they are. The picture shows the menu item before you click it. |
| <h2>Select model</h2><br><br>![Select model is highlighted.](images/ssh-13-select-model_130.png)<br><br>Select model is highlighted. Select model opens Model Config for this console. The next screen-shot is that dialog. The row in this screen-shot was not changed. |
| <h2>Model Config</h2><br><br>![Model Config is highlighted.](images/ssh-14-config_130.png)<br><br>Model Config is highlighted. Select model opened this dialog. Family is the group of models this console uses. The group in this screen-shot is Ollama. The model row is deepseek-r1:1.5b. The row was left as the console already held it. Family can be Ollama, Grok, or Pool. The Model button on a card is a different button. That button opens Model Config for the card. |

## Canvas

Right-click a card. A right-click on the prompt opens a menu that includes Import. A right-click on the reply omits Import. A card in a group adds Duplicate group. Duplicate group copies the group. The new prompts and replies are empty. The models stay.

| |
| --- |
| <h2>Import...</h2><br><br>![Import... is highlighted.](images/canvas-01-import_130.png)<br><br>Import... is highlighted. A right-click on the prompt opened this menu. Import... reads a text file into this turn. A file over 1 MiB is refused. A binary file is refused. The picture shows the menu item before you click it. |
| <h2>Transfer Conversation</h2><br><br>![Transfer Conversation is highlighted.](images/canvas-02-transfer_130.png)<br><br>Transfer Conversation is highlighted. Transfer Conversation copies this canvas into the Roux session after you press OK. Roux is the conversation stored for the terminal session. The turns in that session become these prompts and replies. Cancel leaves both sides as they are. The picture shows the menu item before you click it. |
| <h2>Reply</h2><br><br>![Transfer Conversation is highlighted.](images/canvas-03-reply_130.png)<br><br>Transfer Conversation is highlighted. A right-click on the reply opened this menu. Import... is omitted. The status line reads import refused on output. |
| <h2>Duplicate group</h2><br><br>![Duplicate group is highlighted.](images/canvas-04-duplicate_130.png)<br><br>Duplicate group is highlighted. This card is in a group. Duplicate group copies this group to the next place in the column. The new prompts and replies are empty. The arrangement of the members stays. The models stay. Shift-Ctrl-G does the same. The picture shows the menu item before you click it. Import... and Transfer Conversation stay on this menu. |
