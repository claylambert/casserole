# org.roux.spill

# Roux 1.1 — identity + real RAM/disk paging (design, not a plan)

One pack. One channel. One spill transaction. `RegionPack.publishPage` writes the slot and forces. The engine unmaps only after that call returns.

### 19.2 What actually hurts I/O (more than “lots of files”)

| Issue | 1.0 / naive 1.1 | Effect |
|---|---|---|
| **Open/close per frame** | `SpillWriter.read` opens a `FileChannel` for every spilled message | Syscall + dentry lookup per `getWindow` hit on cold pages |
| **Unpadded `usedBytes` writes** | File size is arbitrary; next page is another inode | Extra extents, poor sequential read of page N-1 then N |
| **In-place truncate** | Write directly to `N.pg` | Torn file on crash (already banned by tmp+rename) |
| **H2 rewrite of `.hot`** | Growing prefix rewritten every add | Write amplification, same inode fragmented |
| **Many tiny files** | One inode + ~256–4096 B dentry each | RAM (inode/dentry cache), slow `readdir`, AV scan |
**semantic-key:** PK-526

## 3. Preserve

- `rotateHot` stays the page-full rotate. `RegionPack.publishPage` stays the publisher. `manifest.bin` stays 112 bytes. The index stride stays 56. The page header stays 64.
**semantic-key:** PK-527

## 6. The roll

5. When spill is enabled and the region is disk-backed, each hot page whose last message id is behind the origin, and which holds no leading-system frame and no seated mood frame, is published through `RegionPack.publishPage`. A page that mixes a head frame and a reel frame stays in the residency it already has. A page already published stays published.
**semantic-key:** PK-528

## 11. Implementation proposal

6. The roll publishes eligible hot pages through `RegionPack.publishPage` when spill is on and the region is disk-backed. It writes `clip.bin` on that same path. It does not delete index rows.
**semantic-key:** PK-529

## 2. As seated

**Pack.** `RegionPack` owns `pages.bin`. `slotOffset` is `pageId * page-bytes`. `publishPage` writes the page CRC, copies the used bytes, pads to `align-bytes` (default 4096) inside the page, writes the slot, and `force(true)`. It does not unmap.
**semantic-key:** PK-530

## 3. Abstractions

| Seat | Type in the tree | Job |
|---|---|---|
| Pack | `RegionPack` | One file, one channel, slot publish and slot read |
| Page | `Page` | Hot segment, or a spilled slot, or a resident copy |
| Index | `MonotonicIndex` | `seq` to pointer. RouxId on the row |
| Identity map | `region.byId` | `(idHi, idLo)` to seq |
| Working set | `maybePageIn`, `pageOutOldest` | The resident cap |
| Window | `windowIds` | The prompt span |
| Durability | `checkpointHot` | When the hot slot is written |
**semantic-key:** PK-531

## 13. End-to-end ASCII (one add that overflows)

*As-built 2026-08-25. If `rotateHot` or `SpillWriter` change, update this file with the code, not the other way around.*
**semantic-key:** PK-532
