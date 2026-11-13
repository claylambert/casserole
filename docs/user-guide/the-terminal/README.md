# The terminal

The terminal is the VT100 session on [the desk](../the-desk/README.md). VT100 is the terminal kind a fresh window selects. This book does not require [the canvas](../the-canvas/README.md). The canvas is the notebook of turns.

This book has twenty-nine screen-shots. A conversation is the chat on this session. Escape switches the session between that chat and command mode. Command mode is the layer where a command runs. The words the model returned are not the point of a screen-shot. The screen-shot shows the prompt, the key, and the command.

The host that owns the line is the fast path on the [server page](../../../casserole-server/README.md#fast-path). The host is the server side that runs the model and prints the prompt.

Ctrl+C in this pane sends the interrupt to the host. It is not copy. Copy in this pane is Ctrl+Shift+C. Ctrl+V opens the paste dialog over the maximized window. The dialog title is Edit paste. Enter inserts a newline in that dialog. Ctrl+Enter accepts the text and sends it to the terminal.

The server remains a host when this window is closed. This guide does not describe that fact with a screen-shot. The account of the host is on the [server page](../../../casserole-server/README.md).

| |
| --- |

## A conversation

A conversation is the chat on this terminal. The green `chat>` line is the prompt. You type on that line. Enter sends the line. An empty Enter reprints `chat>` and does not send. Shift-Enter adds a line in the buffer. Enter submits once.

The host prints a rule, then the reply, then `chat>` returns. The rule names the time and the model. The model on the rules in these screen-shots is `grok/grok-4.3`. The same session keeps the earlier turns. `chat start` is the command that clears those turns. That command is later in this book.

| |
| --- |

## Escape

Escape is the key that switches this terminal between chat and command mode. One press from the green `chat>` lands on the blue `cmd>` prompt. One press from `cmd>` returns to `chat>` while the session is active. After `chat stop`, one press from `cmd>` lands on `idle>`. A bare Escape drops the line you had not sent.

Escape on the Settings dialog closes that dialog. Esc in the View window hides the find bar. The Escape key in this book is the terminal switch.

The command you type at `cmd>` is green. The `cmd>` prompt is blue. The `chat>` prompt is green.

| |
| --- |

## Command mode

Command mode is the layer under the blue `cmd>` prompt. A command is a line you type there. Enter runs it. The prompt stays `cmd>` when the command finishes. `chat start` is the command that leaves command mode and returns the terminal to `chat>`.

An empty Enter in command mode reprints `cmd>`. A name the host does not know is written in red. The words are `Unknown command:`. This book does not show those two lines with a screen-shot.

Ctrl+C in this mode clears the line you are typing, or cancels a reply that is still arriving. The interrupt plate is earlier in this book.

An alias is another name for the same command. The sentence names the alias.

The commands, in the order of these screen-shots, are help, help for one command, status, message list, message show, message find, message lock, message unlock, message edit, clear, chat stop, chat start, ollama, ollama load, ollama stop, swap, select-model, the Model Config dialog, and exit.

## Help

`help` lists every command. The alias is `?`. The last line of the list says Escape toggles command mode, and Ctrl+C cancels a line or an in-flight reply.

| |
| --- |

## One command

`help` and a name print the detail for that one command. This screen-shot asks for `chat start`.

| |
| --- |

## Status

`status` shows the session state, the message counts, the mode, and the selected model. Family is the group of models this console uses. The model line is the choice inside that group.

| |
| --- |

## Message list

`message list` shows the history. The alias is `messages`. The role names are the uppercase names USER, ASSISTANT, and SYSTEM. A seq that can still be locked carries the mark `may lock`. `verbose` prints the full text. The filters are all, user, assistant, and system.

| |
| --- |

## Message show

`message show` and a seq inspect that one message. The card names the seq, the id, the role, the time, the token count, the type, the page, the residency, the crc, and the text.

| |
| --- |

## Message find

`message find by` and a substring search the history. The search ignores case. A hit prints the seq, the role in lowercase, and the text.

| |
| --- |

## Message lock

`message lock` and a seq lock that seq. The host writes `locked` and the seq.

| |
| --- |

## Message unlock

`message unlock` releases the last locked seq. The host writes `unlocked` and the seq.

| |
| --- |

## Message edit

`message edit`, a seq, and the new text revise that seq. A bare seq reads until a line that is only a dot. The host writes `edited` and the seq.

| |
| --- |

## Clear

`clear` clears the user and assistant history. The alias is `history clear`. A configured system prompt stays. A blank prompt leaves no system frame. The session stays.

| |
| --- |

## Chat stop

`chat stop` stops the chat and keeps the history. The prompt stays `cmd>` until Escape lands on `idle>`.

| |
| --- |

## Chat start

`chat start` resets this connection's chat session and enters chat mode. It clears user and assistant turns. A configured system prompt stays. A blank prompt leaves no system frame. It marks the session active, and it returns the terminal to `chat>`.

| |
| --- |

## Ollama

`ollama` runs an ollama command. There is no shell. `ollama list` prints the name, the id, the size, and the modified time.

| |
| --- |

## Ollama load

`ollama load` loads the configured model into RAM. The alias is `ollama start`. The host keeps that model until `ollama stop`.

| |
| --- |

## Ollama stop

`ollama stop` asks the host to unload the configured Ollama model.

| |
| --- |

## Swap

`swap` takes a from word and a to word. When the console family is ollama, the to word is stored. When the family is grok, the host names that family and leaves the target.

| |
| --- |

## Select model

`select-model` and a family switch the console. The families are grok, ollama, and pool. A line with no family opens Model Config.

| |
| --- |

## Model Config

A bare `select-model` opens the modal. The terminal prints `Model Config`. The dialog title is Model Config.

| |
| --- |

## Exit

`exit` closes this SSH session. The alias is `quit`. The host writes `Goodbye.` The window then says the remote host closed the connection.

| |
| --- |
