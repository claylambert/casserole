# org.roux.frame

Component file. It carries how this package is built.

**Depends:** [java/org/roux/id/id.spec.md](../id/id.spec.md), [java/org/roux/layout/layout.spec.md](../layout/layout.spec.md)

## 7. Pseudocode

The add, append, and rotate steps match the engine and the spill writer, including the failure returns.

`ChatMemory.add` takes the current millisecond and, for each message, maps text and role, counts tokens with `HeuristicTokenCounter`, and calls `appendMessage`. The token count is persisted. It is not a spill trigger. `appendMessage` allocates the region if missing, encodes, and takes `FrameHeader.totalLength` so the total is 8-byte aligned. If the total is greater than `pageBytes()` minus `PageHeader.SIZE`, it fails with "frame larger than page". Under the region lock, if remaining is less than the total, it rotates and returns on failure. If remaining is still less than the total, it fails with "hot page too small". Otherwise the offset is `usedBytes`, the message id is `nextMessageId`, and the hot page is pinned. `beginWrite` makes the generation odd. The frame is written as `MSG` with the role, id, time, tokens, and payload. Used bytes become offset plus total. Frame count increases by one. First id is set on the first frame of the page. Last id is set. `endWrite` makes the generation even. The index appends message id, page id, offset, total, tokens, time, role, `MSG`, and flags 0. `nextMessageId` increases by one. The pin is released in a finally. The lock is released in a finally.

`rotateHot` fails unsupported if crypto is enabled, and that check is first. If the old segment is non-null, the spilled flag is set, the next page id field becomes `region.nextPageId`, and the header CRC is written. If spill is enabled, `waitUnpinned` is given 5000 ms and a timeout fails `BLOCKED_BY_PIN`. The writer creates `{spill.dir}/{regionId}`, copies `usedBytes` from the segment to a heap array, writes the channel, `force(true)`, and returns `.../{pageId}.pg`. The page records the file and `unmapRam` closes the arena and nulls the segment. If spill is disabled, the old page stays in memory and in the page map and stays readable. The next page is `new Page(region.nextPageId, pageBytes())`, a new arena whose header starts at used 64. `nextPageId` increases by one. That page becomes hot and is put in the map. `pagesAllocated` increases. The rotate returns ok.

**Truth.** Oversize fails before the lock's rotate. Crypto fails first inside rotate. Spill writes used bytes and forces. Spill off keeps the old page in memory. The new page starts at used 64.

**Reason.** These steps are the built path. A defensive second remaining check covers a rotate that did not actually free a full page. Flags 0 on the index append is the built write.

**Avoidances.** Do not count tokens as a spill trigger. Do not check crypto after setting the spilled flag. Do not unmap when spill is disabled. Do not start a new page at used 0. Do not skip `force(true)`.


**semantic-key:** PK-3490
