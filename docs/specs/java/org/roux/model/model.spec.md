# org.roux.model

## One counter — frozen - says; Yes! Perfect unified approach. I love it

> `windowIds` adds `IndexEntry.getTokenCount`. The canvas bar counts one render of the kept working set, not that sum. `HeuristicTokenCounter` counts `(length + 3) / 4`, and an empty body counts as 1.
**semantic-key:** PK-487

# Roux 1.1 — identity + real RAM/disk paging (design, not a plan)

An oversize frame fails the add. `FrameType.CONT` stays reserved.

## 9. Coherent 1.1 “must / should / could”

Item 8 stays in version 1. The knob is `durability: commit`. Item 9 stays in version 1. The map is `region.byId`. Item 10 waits. A chunked frame is a second story beside F0. `FrameType.CONT` stays reserved.

### 5.1 Why not two layouts

No embeddings. `BitVector` in the old spec is replaced by `BitAddress` as a **view** `(regionId, pageId, byteOffset, bitOffset, bitLength)` into a live page. It does not own a `byte[]`.
**semantic-key:** PK-488

### 5.2 Page header (64 bytes, start of every page)

Pages never shrink. Frames **do not span pages**. If the next frame is larger than remaining space: CRC, spill used bytes to file, unmap RAM, new hot page. If a single payload is larger than `pageBytes - header - frameHeader - pad`: `OperationStatus` fail (no CONT frames in v1). Keep `roux.page-bytes` ≥ 1 MiB so chat text fits.
**semantic-key:** PK-489

### 7.1 Seqlock on HOT pages

4. Verify payload CRC; if fail → `DIRTY_SUSPECT`, do not decode (no crash, `HealthReport`)
**semantic-key:** PK-490

## 8. Engine APIs (no I-prefix)

Semantics from the old spec, cleaned. No `Optional` required; Casserole style: `null` or `OperationResult`.
**semantic-key:** PK-491

## 8. Engine APIs (no I-prefix)

- `void readRaw(MemoryAddress address, MemorySegment dest)` // copy into caller-provided dest, not `byte[]` return
**semantic-key:** PK-492

- `AllocationResult growHotPage(regionId)` // linked child page, no copy of history
**semantic-key:** PK-493

Enums keep `LockLevel` (`EXCLUSIVE_WRITE`, `READ_ONLY_SNAPSHOT`, `DRAIN_AND_HALT`) for CLI later. V1 chat uses internal seqlock, not operator tokens.
**semantic-key:** PK-494

## 12. Telemetry (v1 counters, collector later)

`MemoryTelemetry` is a snapshot of those plus fragmentation (1 - used/capacity of hot page).
**semantic-key:** PK-495

## 16. Implementation slices (v1 plan)

House style: no lambdas, explicit Java, JavaDoc, `@author `, end-of-class banners. Prefer ordinary classes over records on the engine path. Result types (`AllocationResult`, …) may be simple classes with getters.
**semantic-key:** PK-977

B.A. An oversize frame fails the add. `FrameType.CONT` stays reserved.
**semantic-key:** PK-1379

`AllocationStatus` is a public type in this package.

`DecodedFrame` is a public type in this package.

`LockToken` is a public type in this package.

`MessageInspect` is a public type in this package.

`PageFlags` is a public type in this package.

`RegionDescriptor` is a public type in this package.

`Role` is a public type in this package.

`SnapshotHandle` is a public type in this package.

`SnapshotOptions` is a public type in this package.
