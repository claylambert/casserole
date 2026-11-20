# BitNet and the OpenAI-compatible llama server

A BitNet model can answer a Casserole card. BitNet is Microsoft's 1.58-bit model family. The weights are mostly the values minus one, zero, and plus one. The program that serves the model is `llama-server` from that BitNet tree. `llama-server` is the HTTP process. One listener on one port serves both the built-in web page and the programming interface.

Casserole does not ship that binary. Casserole does not download the model. Casserole does not start `llama-server`. You stand the server up. You then point Casserole at the address that is already listening.

The same client also reaches any other llama.cpp server that speaks the same two routes. Those routes are the OpenAI chat routes. OpenAI chat is the request shape a hosted chat API uses. llama.cpp publishes that shape on `POST /v1/chat/completions` and `GET /v1/models`. The Casserole family name for that shape is `llama-openapi`. There is no separate OpenAPI document in this repository. The family name is the convention the Java client implements.

## The lean server

The lean server is the small Falcon pack used to prove the client. Falcon3-3B-Instruct-1.58bit is the model. The quant is `i2_s`. An `i2_s` block holds 128 ternary values and one scale. Token embeddings stay float32. The output head stays `Q6_K`. The GGUF file is the packed model `llama-server` loads with `-m`.

The lean listen address is `http://127.0.0.1:3333`. Port `3333` keeps this server off port `8080`. Port `8080` is the Java process. Port `11434` stays Ollama. Ollama is the other local model program. A second llama.cpp server is another port and another base URL. A Falcon3-10B server, for example, can listen on port `8081` with the alias `Falcon3-10B`. You change `spring.ai.llama-openapi.base-url` when you want that server. You restart Casserole after that change.

The lean launch uses one model file, context `4096`, four threads, one slot, and no GPU layers. The predict cap on that launch is `512`. The temperature on that launch is `0.8`. Continuous batching stays on. The alias is `Falcon3-3B`. The alias is the name `GET /v1/models` returns in `data[].id`. Without `--alias`, that id falls back to the GGUF file name. Restart `llama-server` after you add the alias. A running process does not pick the flag up by itself.

The arguments are the ones the lean process uses.

```text
llama-server -m ggml-model-i2_s.gguf -c 4096 -t 4 -n 512 -ngl 0 --temp 0.8 --host 127.0.0.1 --port 3333 --alias Falcon3-3B -cb -np 1
```

On Windows the Release binary is `llama-server.exe`. The BitNet build that produced the lean binary had every GPU backend off. `-ngl 0` asks for no GPU layers. A CUDA build of llama.cpp still has no `i2_s` GPU path. The ternary math stays on the CPU. `-ngl` does not move those layers onto a card.

Context length is the launch flag `-c`. It is not a field on the chat body. The lean slot is `4096` because that pack is trained to `4096`. A larger `-c` still caps the slot at the trained length. Changing `-c` is a restart of `llama-server`. Changing the yaml key `num-ctx` does not resize a process that is already running.

Falcon3 writes `<|user|>` and `<|assistant|>` as ordinary text. The end token is `<|endoftext|>`. The lean launch stops on `<|user|>`, on `<|endoftext|>`, and on a line that is only the word user. Those stops belong on the launcher. The first Casserole chat body does not send `stop`. A different llama.cpp server starts with an empty stop list.

The page and the API move together. `--port` is one flag. `GET /health` answers when the process is up. On the lean server that answer included `n_ctx_slot` of `4096`. `GET /v1/models` then lists one id. With the alias set, that id is `Falcon3-3B`.

A single-model launch passes `-m` and one file. That process ignores the `model` field and answers from the loaded file. Casserole still sends `model`. A body that omits `model` is a miss. A router launch omits `-m` and reads a model directory. That process returns HTTP `400` and the text `model name is missing from the request` when the field is absent. The id then has to be one of the `data[].id` values. One body serves the lean server, a router, and another llama.cpp engine.

`POST /models/load` and `POST /models/unload` belong to router mode. Casserole does not call them. Casserole does not call Ollama's `GET /api/tags` for this family.

The Microsoft tree stays outside this repository. The public project is [https://github.com/microsoft/BitNet](https://github.com/microsoft/BitNet). Build it, quantize the GGUF, and start `llama-server` before you open a card. A float32 conversion is the large intermediate. It is not the file you serve. The file you serve is `ggml-model-i2_s.gguf`.

## Configure Casserole

The keys live under `spring.ai.llama-openapi` in [application.yaml](../src/main/resources/application.yaml). Ollama lives under `spring.ai.ollama`. There is no Spring AI starter for `llama-openapi`. [LlamaOpenApiProperties](../src/main/java/org/casserole/llm/LlamaOpenApiProperties.java) binds the prefix. [CasseroleApplication](../src/main/java/org/casserole/CasseroleApplication.java) enables that binding.

```yaml
spring:
  ai:
    llama-openapi:
      base-url: http://127.0.0.1:3333
      api-key: ""
      chat:
        options:
          num-ctx: 4096
          temperature: 0.8
          num-predict: 512
```

`base-url` is the server root. The lean value is `http://127.0.0.1:3333`. A blank value falls back to that same address. Another computer is another root, such as `http://192.0.2.40:3333`. The chat URL is that root plus `/v1/chat/completions`. The model-list URL is that root plus `/v1/models`.

`api-key` is optional. A blank key omits the `Authorization` header. A set key sends `Authorization: Bearer` and the key. The Grok key is not this key. The pool token is not this key.

`num-ctx` is the context length you record beside the launcher `-c`. The lean value is `4096`. The chat body does not send it. Ollama does send `num_ctx` on each request. This server does not.

`temperature` is the sampling temperature. The lean value is `0.8`. `num-predict` is the completion cap. The lean value is `512`. Casserole writes that cap as `max_tokens`. A card that stored its own temperature or its own cap wins. A yaml key that is absent stays off the body. The server then keeps the temperature and the predict cap from its own launch.

Edit the file the process will read. A start from `app/casserole.jar` carries the copy that was inside the jar at build time. Build again after you edit the example, then restart the Casserole Server. A process that is already running keeps the old binding.

`llama-openapi` is a local family. Ollama is the other local family. An air-gap refusal does not apply to either one. The air gap still refuses a cloud target. Grok is that cloud target. The home file `air-gap` switch is unchanged.

## Choose it on the desktop

Open Model Config on a Java card. The family word is `llama-openapi`. The model combo fills from `GET /v1/models`. Each `data[].id` is one row. The rows stay in reply order. A typed id that is not already a row stays in the list. A hand-typed id is still sent. The lean server still answers from the loaded file.

An empty list, a body with no id, or a failed call paints the red notice `llama-openapi model list is empty.` The stored id stays on the card. Choose the family again after `llama-server` is up. The combo asks again.

The stored target is `llama-openapi/` plus the id. The lean target is `llama-openapi/Falcon3-3B`. An empty id does not call the server. The card answers `no model defined`.

The card stores a temperature and a completion cap when you type them. A blank field means that value was not stored. The yaml value is used when the yaml key is present. The think box is not on this card. Falcon3 has no separate thinking channel on this path.

The SSH console uses the same family word. `select-model` accepts `grok`, `ollama`, `pool`, or `llama-openapi`. The console target is `llama-openapi/` plus the console model id. The Java window is [AetherisNotebookPanel](../src/main/java/org/aetheris/swing/AetherisNotebookPanel.java). The console family is [ChatShell](../src/main/java/org/casserole/ssh/shell/ChatShell.java).

The Rust Aetheris Client chooses this family. The HTTP call stays in the Java server.

## The request the server receives

[LlamaCppLlmService](../src/main/java/org/casserole/llm/LlamaCppLlmService.java) is the client. The provider id is `llama-openapi`. [LlmRouter](../src/main/java/org/casserole/llm/LlmRouter.java) calls that provider when the target starts with `llama-openapi/`. A Grok call stays on the Grok client. An Ollama call stays on the Ollama client. The pool stays its own family.

The chat call is `POST` to the base URL plus `/v1/chat/completions`. The header `Content-Type` is `application/json`. Connect timeout is 10 seconds. The chat request timeout is 120 seconds. A status outside `200` through `299` fails the turn. The list call is `GET` of the base URL plus `/v1/models`. That call waits 10 seconds to connect and 10 seconds to read.

The body always contains `model`, `messages`, and `stream`. `model` is the text after `llama-openapi/`. The service writes `model` after the options are applied, so an option block cannot drop it. `messages` is the cell thread. The roles are `system`, `user`, and `assistant`. A null text becomes an empty string. A null thread is the error `llama empty prompt`. Any other role is the error `llama unknown role`. The latest user line is not a substitute for the thread.

`stream` is true when the desktop is listening for deltas. Each server-sent line contributes `delta.content`. Other delta fields are not copied. `reasoning_content` is not read. `stream` is false when there is no listener. The reply is then `choices[0].message.content`. An empty reply is the error `llama empty content`. The client does not retry an empty body.

[LlamaCppCallOptions](../src/main/java/org/casserole/llm/LlamaCppCallOptions.java) holds the extra fields this server accepts. A field is written only when the app set it. The card sets `temperature` and `max_tokens`. The yaml `num-predict` sets that same `max_tokens` when the card did not store a cap. The client sends one cap. It does not also send `n_predict` from the card. An empty `stop` list is omitted. Image, audio, and video parts stay unset. Tools stay unset. The lean launcher does not pass `--jinja`, and tools need that flag.

`best_of` and `suffix` are not sent. Context, thread count, and slot count stay launch flags. The Grok sampling pair is not copied onto this body. A Grok card that left temperature unset still carries the Grok factory values. A llama card does not.

[AmwpFacade](../src/main/java/org/aetheris/ssh/AmwpFacade.java) asks for the model list and places the chat. [AetherisStage2](../src/main/java/org/aetheris/swing/AetherisStage2.java) treats `ollama` and `llama-openapi` as local. A cloud check is true only for a different family.

## Another llama.cpp server

Keep the family `llama-openapi`. Change `base-url` to that server. Restart the Casserole Server. Open Model Config and choose the family again. The combo lists whatever `data[].id` that server returns. Send the id you want in `model`. A typed id that the list does not show is still sent.

A server with a key needs `api-key` set to that key. A server with no key keeps `api-key` blank. A server that is not BitNet starts with an empty stop list, so Casserole omits `stop` until you store one. The first smoke target remains `llama-openapi/Falcon3-3B` on `http://127.0.0.1:3333`.

## What stays as it was

Ollama stays `spring.ai.ollama` and `GET /api/tags`. Grok stays `POST https://api.x.ai/v1/chat/completions` and `GET https://api.x.ai/v1/language-models`. The pool stays the Grok agent connection. Those three settings are [Model settings](../casserole-server/README.md#model-settings). This family is the fourth block on that page.

A custom gateway in front of `llama-server` is not built. Router load and unload are not called. The Rust window chooses `llama-openapi`. GPU offload of `i2_s` is not this client.

## A first chat

Start `llama-server` with the lean arguments. Confirm `GET http://127.0.0.1:3333/v1/models` lists `Falcon3-3B`. Restart the Casserole Server so it reads `spring.ai.llama-openapi`. Open the Java Aetheris Client. On a card, open Model Config, choose `llama-openapi`, and choose `Falcon3-3B`. Send one turn.

The request body includes `model` with the value `Falcon3-3B`. A normal sentence in the reply is the smoke. The Casserole Server does not start the model process for you. If the combo is empty, the red notice is `llama-openapi model list is empty.` Start `llama-server`, then choose the family again.
