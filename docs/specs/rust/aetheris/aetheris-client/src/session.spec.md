# rust.aetheris.aetheris-client.src.session

**Depends:** [aetheris-client](../aetheris-client.spec.md), [amwp](amwp.spec.md)

### RustSshSession

The SSH session. russh runs on the read thread. The UI thread uses try_send. Tests do not call Session::start.
**semantic-key:** PK-1429

`Cmd` is a public type in this module.

`Burst` is a public type in this module.
