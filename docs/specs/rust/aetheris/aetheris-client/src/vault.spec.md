# aetheris-client::vault

Component file. It carries how this package is built.

**Depends:** [rust/aetheris/aetheris-client/aetheris-client.spec.md](../aetheris-client.spec.md)

## Vault file

The Java vault file is not opened by this client, and the standing store is a new local file.

The Java vault is H2. The test URL is `jdbc:h2:mem:aetheris`. The product file is `jdbc:h2:file:` under `~/.aetheris/{user}/vault`, unless the yaml path says otherwise. Disk is the product default. RAM is testing only. That rank already sits on the functional spine.

No pure-Rust library opens those H2 files. The product rules can still be carried in a Rust store: disk by default, one vault per SSH user, the client is the source of truth, search, a burner stores metadata with empty payloads, and a crash of RUNNING becomes INTERRUPTED. This brief, at the time it was written, did not pick the store and did not ask for a second export format. The question of whether the store must also read the person's existing H2 file was left as a collaboration. The later lock closes that question: the Rust file is new, and this client does not read the H2 file.

Vault rules carry: disk, one vault per user, the client as source of truth, search, burner payloads empty, and a RUNNING crash becoming INTERRUPTED. The existing H2 file is the flag: there is no pure-Rust reader, and that fact is checked before a build. The candidate family is open: `redb` is a pure-Rust file store, named for a check. `rusqlite` is a note outside the lane because it bundles C.

**Truth.** Vault rules carry. The existing H2 file is the flag. The standing answer is a new local file, and this client does not open `jdbc:h2:mem:aetheris` or `jdbc:h2:file:` under `~/.aetheris/{user}/vault`.

**Reason.** No pure-Rust library reads those H2 files, and the product rules do not require a second export format in order to keep disk, search, empty burner payloads, and the RUNNING crash rule.

**Avoidances.** Do not open the Java H2 file from this client. Do not treat RAM as the product default. Do not treat `rusqlite` as inside the lane. Do not leave "must read the existing H2 file" as the requirement after the new-file lock.

**semantic-key:** PK-6960

## Vault engine

The Rust vault is Turso, embedded, a new local file, and cloud sync stays off.

Turso is the drop-in for the H2 vault on this client. The engine is embedded. There is no wire. The crate is `turso`, the in-process rewrite. SQLite's SQL and SQLite's file format come with it. There is one local file per SSH user. Cloud sync stays off. Cloud push and cloud pull stay off. There is no Turso Cloud account.

The Java vault stays H2, on the file it already uses. This client does not open that file. The vault-file question closes on that answer. The Rust file is new.

The table definitions move with a short translation. `CLOB` becomes `TEXT`. `MERGE ... KEY` becomes `INSERT ... ON CONFLICT`. The `INFORMATION_SCHEMA` probe becomes a catalog read. `LIKE` stays. Text is valid UTF-8. One write statement finishes before the next starts.

The crate is pre-1.0. A proof of one canvas in and out, on this machine, comes before a build. Naming the engine does not by itself write a crate, a crate file, or a version pin. The design stays open for that proof.

The proof is one canvas in and one canvas out on a local file. The working canvas follows that proof. The window writes the open canvas. Connect opens the file after the session starts. The first canvas loads.

The vault rules on the functional spine still hold. Disk is the default. The client is the source of truth. Search stays. A burner stores metadata with empty payloads. A crash of RUNNING becomes INTERRUPTED.

**Truth.** The engine is embedded `turso`, one new local file per SSH user, cloud sync off, the H2 file unopened, `CLOB` as `TEXT`, and one write finished before the next. Connect opens the file after the session starts. The crate is pre-1.0, and the proof is one canvas in and out.

**Reason.** No pure-Rust reader opens the Java H2 file, and an embedded engine with SQLite's SQL and file format carries the vault rules without a cloud account or a wire.

**Avoidances.** Do not open the Java H2 file. Do not turn on cloud sync, cloud push, or cloud pull. Do not require a Turso Cloud account. Do not start a second write before the first finishes. Do not treat `LIKE` as removed. Do not treat text as anything but valid UTF-8. Do not build before one canvas has gone in and come out.

**semantic-key:** PK-6965
