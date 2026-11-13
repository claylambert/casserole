# rust.aetheris.aetheris-client.src.session

**Depends:** [aetheris-client](../aetheris-client.spec.md), [amwp](amwp.spec.md)

### RustSshSession

The SSH session. russh runs on the read thread. The UI thread uses try_send. Tests do not call Session::start.
