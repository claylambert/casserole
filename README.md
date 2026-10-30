# casserole
A Java SpringAI enabled MCP agentic server with embedded SSH server and Custom FFM memory model for chat. Accompanied by a Sovereign Desktop UI leveraging Casserole for mult-model integration in a unified Canvas. Client is implemented in both Java and Rust; the Project is built using AA-SDD (Architecture Augmented Spec-Driven Development)

## Users

The server starts with four accounts in [casserole-server/src/main/resources/users.yaml](casserole-server/src/main/resources/users.yaml).

| Name | Password |
| --- | --- |
| admin | demo-admin |
| user | demo-user |
| guest | demo-guest |
| dev | demo-dev |

These passwords are for a local start. Change them before anyone but you can reach the server.

The server reads the first roster it finds.

1. `users.yaml` in the working directory.
2. `users.yaml` under the home directory at `.casserole/users.yaml`.
3. The packed file linked above.

A name uses letters, digits, `.`, `_`, and `-`. A password is required. Each name appears once.

To add a user, open the file the server will read and add a row under `users`.

```yaml
  - name: newname
    password: newpassword
```

To remove a user, delete that row. Leave at least one user. An empty roster will not start.

The server reads the roster when it starts. A change is there on the next start.
