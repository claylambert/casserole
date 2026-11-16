# org.casserole.ollama

### 4.4 Model load / unload (not `ollama run`)

- `OllamaProcess.validate` rejects empty tokens, `..`, and `; | & < > \n \r`.
**semantic-key:** PK-402
