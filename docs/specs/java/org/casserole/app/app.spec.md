# org.casserole.app

## Child pages

<pre>
org.casserole.app/
└── <a href="ui/README.md">ui</a>
</pre>

### 6.1 `SshServer` — stop extending `Thread`

**Today:** `class SshServer extends Thread`, name `casserole-sshd`; `InitRunner.run` → `sshServer.start()`.
**semantic-key:** GK-1254

### WP-F — Lamina (S0-11, S0-12)

**`SshServer extends Thread`:** replace with a Lamina-submitted task + MINA `sshd.start()`. `InitRunner` asks Lamina, not `sshServer.start()` as a raw `Thread.start()`. Preserve listen-on-Boot-ready behavior.
**semantic-key:** GK-1368

### 5.1 Lifecycle

- **Does not start in the constructor.** `InitRunner` (an `ApplicationRunner`) calls `start()` after the Spring context is up so `ChatModel` / properties exist.
**semantic-key:** PK-171
