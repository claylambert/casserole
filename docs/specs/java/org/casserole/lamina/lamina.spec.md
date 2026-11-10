# org.casserole.lamina

Component file. It carries how this package is built.

**Depends:** [java/org/casserole/log/log.spec.md](../log/log.spec.md)

# Characters are handled under the shell lock, a line feed after a carriage return is dropped, and a sealed paste stays atomic.

Decoded characters go through `handleChar`, synchronized on the shell.

1. If an escape sequence is being collected, append until it is complete: a letter, `~`, or a two-character sequence that is not a control-sequence introducer or a single-shift.
2. Escape starts a sequence and schedules the timeout.
3. Ctrl+C, byte `0x03`, cancels a paste, cancels the model, or clears the line.
4. Carriage return is a paste newline or Enter. A line feed after a carriage return is dropped. A lone line feed is Enter.
5. Backspace or delete erases one glyph, or aborts a sealed paste.
6. A printable character is echoed and buffered, unless the paste is sealed. The blob is atomic until Enter, Ctrl+C, or Backspace.

Other control characters are ignored.

**Truth.** Lock on the shell. `0x03` cancels. CRLF drops the line feed. A sealed paste ignores further typing.

**Reason.** A line feed after carriage return would submit twice, and a sealed blob must not accept more typing.

**Avoidances.** Do not insert ignored controls. Do not echo into a sealed paste. Do not treat line feed after carriage return as a second Enter.


**semantic-key:** PK-0547

# Bare Escape waits 100 milliseconds, and bracketed paste plus the arrows are the sequences that are handled.

Bare Escape is the mode switch. Arrow keys and bracketed paste also start with Escape. The solution:

- On Escape, wait `escape-timeout-ms`, default 100.
- If more bytes arrive in time and form a complete control sequence, handle that sequence.
- If the buffer is still only Escape when the timer fires, treat it as bare Escape. Discard the unfinished line. Toggle `COMMAND` with `CHAT` or `IDLE`.

Handled sequences:

| Sequence | Meaning |
|---|---|
| `ESC[200~` | Bracketed paste start |
| `ESC[201~` | Bracketed paste end, then seal the blob |
| `ESC[A` or `ESC[OA` | History up |
| `ESC[B` or `ESC[OB` | History down |

Other control sequences are dropped. They are not inserted into the line. That is intentional. The shell is not a full emulator.

On start the shell writes `ESC[?2004h` to enable bracketed paste. On destroy or exit it writes `ESC[?2004l`.

**Truth.** 100 ms wait. Paste `200~` and `201~`. History `A`/`OA` and `B`/`OB`. Enable `ESC[?2004h`. Disable `ESC[?2004l`. Other sequences dropped.

**Reason.** Without the wait, a bare Escape and an arrow are the same first byte.

**Avoidances.** Do not insert unknown sequences. Do not treat an arrow as a mode switch. Do not leave bracketed paste enabled after exit.


**semantic-key:** PK-0548

# The engine base is localhost port 11434, pull is never, and options.model is what the shell injects.

| Key | Default | Role |
|---|---|---|
| `spring.ai.ollama.base-url` | `http://localhost:11434` | Injected into the load command. |
| `spring.ai.ollama.init.pull-model-strategy` | `never` | Do not pull models at startup. The operator pulls them. |
| `spring.ai.ollama.chat.model` | `qwen2.5-coder:7b` | The model-library property. Also duplicated under `options.model`. |
| `spring.ai.ollama.chat.options.model` | `qwen2.5-coder:7b` | This is what `ChatShellFactory` injects as `modelName` for `status`, `ollama load`, and `ollama stop`. |
| `spring.ai.ollama.chat.options.num-ctx` | `8192` | Demo value sent as `options.num_ctx`. A value of 0 omits the field. |

A commented alternative in the file is `cwc-mistral-nemo-12b:latest`. It is not the default. Switching models is a file or environment change. There is no runtime `select model` command yet.

`ChatShellFactory` falls back to `qwen2.5-coder:7b` if the property is missing. No API key.

**Truth.** Base `http://localhost:11434`. Pull never. Both model keys are `qwen2.5-coder:7b`. The options key is the one the shell uses. Demo context 8192.

**Reason.** Startup must not download a model. The shell has to show the same id that load and stop use.

**Avoidances.** Do not pull at startup. Do not treat the commented model as the default. Do not invent a select-model command in this capture. Do not send num_ctx when the value is 0.


**semantic-key:** PK-0551
