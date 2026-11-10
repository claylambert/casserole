# org.roux.session

Contract file. Two sides match this component.

**Depends:** [java/org/roux/layout/layout.spec.md](../layout/layout.spec.md)

## 6. API sketches

The open result is an int kind, a message count, a UUID, and a reason. Refuse text is stable. There are no lambdas and no streams.

`RegionOwner.sanitize` returns the string and throws `IllegalArgumentException` when the raw value is illegal. `RegionOwner.isValid` returns whether the raw value passes. `OpenResult` constants are `NEW` equal to 0, `RESTORED` equal to 1, and `REFUSED` equal to 2. The fields are `kind` as an int, `messageCount` as an int for a restored open, `regionUuid` for a disk region, and `reason` as the string for a refusal. The engine exposes `isRamOnly`, `openRegion(String regionId)` returning `OpenResult`, and `persistClose(String regionId)` returning the operation result. No lambdas, no streams, explicit loops. Foreign-memory longs stay 8-byte aligned, which the manifest layout already is. Refuse text, stable enough to find in a smoke log, is `session already open for {user}`, `invalid username`, `roux pack page-bytes mismatch (manifest {n}, yaml {n})`, and `roux pack incomplete or corrupt; delete {dir} or revert yaml`.

**Truth.** `NEW` is 0, `RESTORED` is 1, `REFUSED` is 2. Sanitize throws `IllegalArgumentException`. The four refuse lines are the ones above, with `{user}`, `{n}`, and `{dir}` filled in.

**Reason.** Callers branch on an int, not on a type hierarchy. Stable refuse text is how an operator and a smoke log recognize a mismatch without parsing a stack.

**Avoidances.** Do not renumber the three kinds. Do not paraphrase the four refuse lines. Do not use lambdas, streams, or method references in these types. Do not misalign the 8-byte longs in the manifest.


**semantic-key:** PK-3481

## P2. Owner, manifest, and pack lock

Sanitize the username, read and write a 112-byte manifest, and lock `{dir}/.lock`. The engine does not call this package yet.

New types are `org.roux.session.RegionOwner` and `RegionManifest`. `RegionPack` is edited. `RegionOwner.sanitize` fails on null or blank, allows `[A-Za-z0-9._-]+` only, rejects `..` and any `/` or `\`, keeps case as logged in, and returns the same string when it is valid. `RegionManifest` uses primitives, CRC32C, and little-endian foreign memory through the existing layout helper. It is not JSON. The size is 112. Magic is `RXM1`, meta version is 1, flags are 0. Then `pageBytes`, `pageCount`, `hotPageId`, `hotUsedBytes`, `nextSeq`, `lastWriteEpochMs`, `createdEpochMs`, format version value 2, a pad, the region UUID high and low halves, the owner as UTF-8 padded to 32 bytes, the CRC32C of `[0,104)`, and pad to 112. The API uses explicit types and no lambdas. `write(Path dir, fields)` and `read(Path dir)` return null when the file is missing, and throw or return a failure object when the CRC or the magic is bad. The write path is `manifest.bin.tmp`, then `FileChannel.force(true)`, then replace `manifest.bin`. On Windows, if an atomic move fails, delete the target and then move. `RegionPack.lockFile(regionId)` is `{dir}/.lock`. `tryLock(regionId)` opens `.lock` and calls `tryLock`; failure means "session already open". `unlock(regionId)` releases and closes the lock channel and is idempotent. The `pages.bin` channel stays as it is today. `wipePack(regionId)` deletes `pages.bin` and `manifest.bin` and keeps the directory and the lock. `deleteRegion` stays for `persist` false only.

**Truth.** Sanitize accepts only `[A-Za-z0-9._-]+`, rejects blank, `..`, and separators, and keeps case. The manifest is 112 bytes, magic `RXM1`, CRC32C over `[0,104)`, written by temporary, force, and replace. `.lock` is try-lock, and unlock is idempotent. Wipe deletes the two files and keeps the directory and the lock.

**Reason.** A path-safe owner is the folder name. A forced temporary replace is what makes a torn manifest look like a CRC miss instead of a half header. The lock file is separate so a wipe can delete the pack without dropping exclusive.

**Avoidances.** Do not accept `..`, `/`, or `\`. Do not encode the manifest as JSON. Do not call this from the engine in this package. Do not delete the directory or the lock from `wipePack`. Do not use `deleteRegion` except when persist is false. Do not leave a lock channel open after unlock. Do not skip `force(true)` before the replace.


**semantic-key:** PK-3483
