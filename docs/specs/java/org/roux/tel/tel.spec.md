# org.roux.tel

Component file. It carries how this package is built.

**Depends:** [java/org/roux/model/model.spec.md](../model/model.spec.md)

## 5. One pass

The sweep copies the dead set under the region lock, then releases one slot at a time, re-checking before each release.

If the region has no pack, the sweep returns. Under the region lock it takes the page ids named by index rows, the hot page id, and the issued page ids outside that live set and outside the hot id. It drops any id whose pin count is above zero, and any id whose slot has no `ROUX` magic. If any live row was written after the last force of the hot page, the sweep returns without a release. It then walks the dead ids from the highest id downward. Under the region lock it re-checks that the page is still dead, still not hot, and still not pinned, then calls `releaseSlot` and forces that one slot. A page that gained a live row between the copy and the release stays. The sweep counts the slots released. A pass that releases nothing stays quiet. `RouxCounters.pagesReleased` counts the slots that were released.

**Truth.** One pass releases from the highest dead id downward, re-checks under the lock, forces each released slot, and stays quiet when it releases nothing.

**Reason.** Copying the dead set and then re-checking closes the race where a row lands between the scan and the punch. Releasing highest-first keeps a dead tail contiguous for truncate. A quiet empty pass avoids noise when edits have not yet orphaned a page.

**Avoidances.** Do not release without the re-check. Do not release while a live row is newer than the last force of the hot page. Do not release a pinned id or an id with no `ROUX` magic. Do not run the punch outside the region lock. Do not announce a pass that released nothing.


**semantic-key:** PK-3466
