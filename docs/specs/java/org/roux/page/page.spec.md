# org.roux.page

## 2. Identity in 1.1 (locked mechanics)

- Bump `PageHeader.VERSION` (v1 seq-only vs v2 seq+id). Live SSH regions already die on hangup, so no migration tool.
**semantic-key:** PK-496

### 19.4 Cheap hedges if we keep `N.pg` for 1.1

2. **Cache `FileChannel`** on `Page` (open on first cold read, close on region free).
**semantic-key:** PK-497

## 1. As built

**Pages.** `PageHeader.SIZE` is 64. Version is 2. A frame does not span a page. A frame larger than `pageBytes - 64` fails the add. Default `roux.page-bytes` is 1048576. The hot page is an FFM `Arena.ofShared` `MemorySegment`.
**semantic-key:** PK-498
