# org.casserole.ollama

Component file. It carries how this package is built.

**Depends:** This container names no other spec.

# Load posts keep_alive -1 with a 10 second connect timeout, and stop is a 30 second process.

Load, the commands `ollama load` and `ollama start`:

- HTTP `POST` to `{base-url}/api/generate`.
- Body: model set to the configured id, prompt a single space, `stream` false, `keep_alive` `-1`.
- Connect timeout 10 seconds. Request timeout 120 seconds.
- Pins the model in memory until stop, or until the engine's own policy.

Stop, the command `ollama stop`:

- `ProcessBuilder` arguments `ollama stop` and the model. No shell. Timeout 30 seconds.

Arbitrary command line, such as `ollama list` and `ollama ps`:

- The command name `ollama` matches the remaining tokens as arguments.
- Longest-name match means `ollama load` and `ollama stop` win over a bare `ollama`.
- `OllamaProcess.validate` rejects an empty token, `..`, and the characters `;`, `|`, `&`, `<`, `>`, newline, and carriage return.
- Combined standard output and standard error, capped at 65536 bytes.
- Quotes are not supported in the split.

The `ollama` binary must be on the server path. Do not use `ollama run`. It opens an interactive prompt and hangs. No API key. The base URL is `http://localhost:11434`.

**Truth.** POST generate, keep_alive -1, 10s connect, 120s request. Stop is 30s with no shell. Validate rejects metacharacters. Output cap 65536. No quotes.

**Reason.** An interactive runner hangs the listener. A shell string would accept metacharacters.

**Avoidances.** Do not call ollama run. Do not pass the line through a shell. Do not accept quotes. Do not raise the output cap past 65536 in this capture.


**semantic-key:** PK-0539

# The system prompt, the live model, and spill settings stay process-wide.

Still process-wide: the system prompt for new regions, the live local-engine model, and durability, spill, and page size.

There is no per-user model and no per-user system prompt in this slice. The engine is `http://localhost:11434`. The code model default is `qwen2.5-coder:7b`.

**Truth.** Prompt, model, and spill stay one process. Accounts do not each carry a model.

**Reason.** The out-list already refused a model or prompt per user.

**Avoidances.** Do not store a model or a system prompt on the account row.


**semantic-key:** PK-0553
