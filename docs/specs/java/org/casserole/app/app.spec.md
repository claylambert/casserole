# org.casserole.app

Component file. It carries how this package is built.

**Depends:** [java/org/casserole/ssh/shell/shell.spec.md](../ssh/shell/shell.spec.md)

# The listener thread is casserole-sshd, it is not a daemon, and it starts from the runner rather than the constructor.

`org.casserole.ssh.shell.SshServer` extends `Thread`. The thread name is `casserole-sshd`. It is not a daemon.

- It does not start in the constructor. `InitRunner`, an application runner, calls `start()` after the context is up so the model and the properties exist.
- Shutdown on destroy sets `running` false, notifies, and stops the listener.
- The host key provider reads `Paths.get` of the host-key path, `hostkey.ser`.
- Default server setup, plus a password authenticator, plus `ChatShellFactory`.

Leftover classes `MinimalSshServer` and `SshServerTest` are not host beans. They are earlier echo and pseudo-terminal experiments with the same proof credentials. Ignore them for product behavior. Do not publish those credentials.

**Truth.** Non-daemon thread `casserole-sshd`. Start after the context is up. Host key `hostkey.ser`. Leftovers are not beans.

**Reason.** Starting in the constructor raced the model and the properties.

**Avoidances.** Do not start in the constructor. Do not register the leftovers as beans. Do not publish the proof password.


**semantic-key:** PK-0541
