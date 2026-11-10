# org.casserole.lamina.spring

Component file. It carries how this package is built.

**Depends:** [java/org/casserole/config/config.spec.md](../../config/config.spec.md), [java/org/casserole/lamina/lamina.spec.md](../lamina.spec.md), [java/org/casserole/lamina/Lamina.spec.md](../lamina.spec.md)

# The default is qwen2.5-coder:7b, switching is configuration only, and there is no download path inside the host.

The proof default is `qwen2.5-coder:7b` because it is small enough for local memory and behaves reasonably for a coding and operations assistant. Larger instruct models, for example the commented `cwc-mistral-nemo-12b:latest`, were discussed for cleaner prose and tables. Switching is configuration only.

No download-from-a-hub path exists in the host. Models are whatever the local engine already has. The engine is `http://localhost:11434`. There is no API key and no runtime select-model command in this capture.

**Truth.** Default `qwen2.5-coder:7b`. Yaml switches models. No hub download. No select-model command.

**Reason.** The default fits local memory. The host does not fetch weights.

**Avoidances.** Do not add a hub download. Do not treat the larger model as the default. Do not add a picker in this capture.


**semantic-key:** PK-0540
