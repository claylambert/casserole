# org.roux

## Child pages

<pre>
org.roux/
├── <a href="api/api.spec.md">org.roux.api</a>
├── <a href="crypto/crypto.spec.md">org.roux.crypto</a>
├── <a href="frame/frame.spec.md">org.roux.frame</a>
├── <a href="id/id.spec.md">org.roux.id</a>
├── <a href="index/index.spec.md">org.roux.index</a>
├── <a href="layout/layout.spec.md">org.roux.layout</a>
├── <a href="lock/lock.spec.md">org.roux.lock</a>
├── <a href="model/model.spec.md">org.roux.model</a>
├── <a href="page/page.spec.md">org.roux.page</a>
├── <a href="session/session.spec.md">org.roux.session</a>
├── <a href="spill/spill.spec.md">org.roux.spill</a>
├── <a href="spring/spring.spec.md">org.roux.spring</a>
├── <a href="tel/tel.spec.md">org.roux.tel</a>
├── <a href="token/token.spec.md">org.roux.token</a>
└── <a href="toon/toon.spec.md">org.roux.toon</a>
</pre>

## 7. P4 — Bantha Roux

- Region id `sanitize(user) + ".aetheris"` only for this call site
**semantic-key:** PK-499

## A declared test covers the Roux 1.1 head roll and the tail spill

RouxWindowSpillTest exercises the head roll and the tail spill. A small page forces the spill. The hot page is the tail. A spilled head still reads. The resident cap holds. The window keeps the tail and rolls the head. A turn atom stays together. A pinned system stays. Spill off stops at the cap. No load test.
**semantic-key:** PK-500

## Floor the plan stands on

`{user}.aetheris` is RAM. Java name is `openRam`. Bantha is the nickname, not a method. Full push per execute. Region id uses a dot. No FileLock.
**semantic-key:** PK-501

## Q. Questions (Stages 1–2 working spec)

**-2. Region id `user.aetheris` (dot)?** **A.

## 0. On approval

5. mvn -q -Dtest=RouxWindowSpillTest test from Casserole. A pool turn is not sent. A live SSH connect is not made. No Session::start. No chrome::run.

## 3. The build

`RouxWindowSpillTest` in `src/test/java/org/roux`. A 512-byte page forces one fat frame per page. Resident pages stay 2. Spill on: four frames, three spilled, the hot page is the tail, the head reads back, the resident count stays inside the cap. The window of two keeps the tail and rolls the head. A token budget of 20 on four notes of 10 keeps the crossing note and rolls the oldest. A turn atom stays together. A pinned system stays in front of the tail. Spill off stops at the resident cap.
**semantic-key:** PK-502

## 4. Proof

`mvn -q -Dtest=RouxWindowSpillTest test` from `

## 2. What stays

Behavior stays. Spill, the window, identity, crypto, and close stay as the tree has them. Crypto stays disabled. Requirement 15 stays the open weigh. The prose describes the tree. This plate names Roux. The scope is type-level prose and the method that realizes a key. A private getter stays.

## 3. The build

The assertions stay.

## Seat

The test type is package-private `RouxWindowSpillTest` in `src/test/java/org/roux`. A small page forces a spill. The hot page is the tail. A spilled head still reads. The resident cap holds. The window keeps the tail and rolls the head. A turn atom stays together. A pinned system stays. Spill off stops at the cap.
**semantic-key:** PK-503

## Dependencies pitied

The types under org.roux. The test RouxWindowSpillTest. No new library. No Cargo.toml edit.

**Protected Head Region**
Define a resizable, write-protected window at the absolute head of the active context.
This is a region that can hold any static context the operator chooses to keep stable relative to the window itself — specifications, standing contracts, locked invariants, or any other material that must not be moved by the paging system.
Once set for the session, the contents of the protected head are not immutable in the absolute sense. They may be changed, but only by the user through an explicit operation. The paging and spill mechanisms themselves have no authority to alter, move, or evict anything in this region.
In effect the protected head functions as a small RAM disk under user control: the operator may update it at will, yet whatever is written there remains unaffected by the spill mechanism. Spill logic must skip this region entirely. Nothing in the protected head may be moved out by volume, age, or pressure.
The size of the protected head is user-settable at runtime and persists across session recovery. Its purpose is to keep the frame of the session fixed at the position of strongest attention unless the user deliberately revises it.
**semantic-key:** PK-504
**semantic-key:** PK-504

| Seat | Line |
|---|---|
| | Chair. He names the plan cycle. |
| | The pre-plan is the home of the sketch. The serial stays the behavior. |
| | One class. One trim, on the host. No second gearbox. |
| | Section 5 is the landing order. |
| Face | The name stays `ReichenbachSink`. The lock is not a fifth letter. |
**semantic-key:** PK-505

## 3. What stays

Rust does not gain the type and does not trim the send. The Rust inspector borrows the rule in a private function beside `paint_inspector`. The code doc on that function says it borrows the idea from the Java class `ReichenbachSink`.
**semantic-key:** PK-506

## Code seat — frozen

The type is the package-private class `ReichenbachSink` in `org.roux`, and `RouxEngine.windowIds` asks it for the ids when a head is designated.
**semantic-key:** PK-507
**semantic-key:** PK-507

## Two call sites — frozen

The proposal is one class, `ReichenbachSink`. `buildPrompt` calls it for the SSH tape. `AmwpFacade` calls it on the assembled list before `holdRoux` and before the provider call. Both clients keep sending the working set they send today. The host trims. The sink selects only when the managed size is set and a head is designated. Otherwise each caller keeps the walk it has today.
**semantic-key:** PK-508

## Tail fit — frozen

> `RouxEngine.windowIds` adds the atom, then stops. The first non-system atom is kept even when it is already over the token cap. A budget of 20 with four 10-token notes keeps three notes.
**semantic-key:** PK-509

## Three surfaces — frozen

The proposal is that the Java inspector calls `ReichenbachSink` and lists that transcript, newest first, still cut by `inspectorLineChars`. The yellow mood row stays the first row. Debug Mood paints the invitation when that invitation is in the transcript. The canvas cards and `message list` do not call the sink.
**semantic-key:** PK-510

## Rust bar — frozen

> Rust reads `num-ctx` from the session facts. Its yaml reader keeps the Grok context windows and the pool window, and it drops every other key. `ReichenbachSink` is not in the tree. `paint_inspector` lists the kept working set, newest first.
**semantic-key:** PK-511

## SSH lock — frozen

Solution pass. The sink selects only when the managed size is set and a head is designated. Otherwise the walk and the bars stay as they are today. One class, `ReichenbachSink`, serves `buildPrompt` and the host list before `holdRoux`. `managed-tokens` rides the session facts beside `num-ctx`. SSH designation is `head.yaml`. Canvas designation is a lock on the vault cell, sent as `head: true`. The mood pin word `head` is the splice seat. A designated head blocks `replaceTranscript`. The Java inspector calls the same class. Rust reads the fact and does not select.

## Layers

Identity on the frame. One `pages.bin`. Spill after force. A working set. Durability default `commit`. A dual window. A turn atom. findById. F0. Inspect. Close is an open weigh on the requirements page. This is the layer the tree already runs. This filing does not cut code.
**semantic-key:** PK-978

## 0. What 1.0 actually is (honesty)

Spill **works on the happy path**: page full → write `.pg` → unmap → new hot page → index still RAM → reads use `FileChannel`.
**semantic-key:** PK-512

## 3. Spill as a transaction (must)

**Header `SPILLED` bit:** set on the **file copy** (or in the in-memory header *after* successful rename, immediately before unmap). Never set it if we still need the page to stay hot on failure.
**semantic-key:** PK-513

On any failure: leave old page hot, delete `.tmp` if present, return error, **do not** write the new message (caller retries or surfaces red). Region remains consistent.
**semantic-key:** PK-514

### 19.5 Recommendation to lock

| Item | 1.1 |
|---|---|
| Region directory | Keep |
| Many `N.pg` files | **Prefer not** as the long-term page store |
| Pack file `pages.bin`, slot = `pageId * pageBytes` | **Yes — do this in 1.1** while nothing depends on `N.pg` names |
| Align | `align-bytes: 4096` |
| Cached channel | Must |
| Directory sharding | Not 1.1 |

## 2. Beside the locked design

Stays the v1 lock. Full history stays in the region. The window stays at read. `get` with no limit stays the whole region, which is the message list. Evict-on-add stays a v1 non-goal. Spill-on-page-full stays the residency rule.
**semantic-key:** PK-979

## 6. Lifecycle

The keys live on `RouxProperties`, in a nested `Prune` block beside `Spill` and `Session`.
**semantic-key:** PK-515
**semantic-key:** PK-515
**semantic-key:** PK-515

### 1.1 Goals (v1)

8. Spill-to-file when a page fills (cleartext). Encryption off.
**semantic-key:** PK-516

## 16. Implementation slices (v1 plan)

Same Maven artifact (`casserole`). Package boundary is the module split: `org.roux` must not import `org.springframework.ai` or `org.casserole`. `org.roux.spring` may import Spring AI. One process-wide `RouxEngine` bean (implements the four APIs).
**semantic-key:** PK-517

## 12. The bake

No new public type. No new public method. The private method is walkSlots. No Boot sweep. No new command. No change to publishPage. No change to the frame layout. No change to manifest size. A clean checkout of this commit was not recompiled. A pool turn was not sent. A live SSH connect was not made. No Session::start. No chrome::run.

## 13. The battery

The window battery is RouxWindowSpillTest. It names the roll, the tail, and the walk. The cases cover the pack.

## 1. Decision

Identity is **not** a page pointer. Spill can move RAM → disk without renaming messages.
**semantic-key:** PK-518

## 4. Locator (`seq`) — unchanged job

Spill: identity travels **inside the page bytes** (frame) so a `.pg` file is self-describing. Index copy in RAM is for speed.
**semantic-key:** PK-519

### P3 — Engine persist / restore / ram-only I/O

4. Neither → `tryLock`, allocate empty, mint UUID (store on `Region` until first persist write), NEW.
**semantic-key:** PK-520

## 1. Requirements

4. Spill is one transaction. Wait until the page is unpinned. Write the used bytes into the slot. Force. Then unmap. A thrown write leaves the hot page mapped and does not append the new message.
**semantic-key:** PK-521

## 2. As seated

**Rotate.** `RouxEngine.rotateHot` waits for the pin, calls `publishPage`, then unmaps. An `IOException` returns before the unmap. Spill off, and the resident count already at the cap, returns `resident-pages cap reached and spill is disabled`.
**semantic-key:** PK-522
**semantic-key:** PK-522

The ReichenbachSink selects which turns fit inside a token allowance. The result order is the system pin, then the packed head, then the tail that fits, then the live line. A refused selection returns null. The type holds no region and writes no page. The type refuses construction.
**semantic-key:** PK-1259

The Piece is one turn the selector can see. The ordinal is the position the caller assigned. The seq is the message id, and a seq of 0 is the live line. The role is the role byte. The tokens value is the token count of the turn. The head flag marks a turn in the packed head. The stay flag marks a turn that joins the pin. The live flag marks the live line.
**semantic-key:** PK-1260

The Transcript is the selection result. The pieces array is the pin, then the head, then the tail, then the live line. The headOver flag is true when the pin tokens plus the head tokens exceed the allowance. That comparison does not include the live line. The tokenSum is the sum of every piece in the array, and that sum includes the live line when it is present.
**semantic-key:** PK-1261

The method orders two pieces. A smaller message id comes first. Equal message ids use the difference of the ordinals.
**semantic-key:** PK-1262
**semantic-key:** PK-1262

Roux is the paged memory beside the terminal. The RouxEngine type is that memory. One region is stored for each conversation id. The type implements MemoryInspector, MemoryLayoutManager, MemoryStateController, and MemoryLifecycleManager. A region holds the hot page, the off-heap index, and the map from a RouxId to a message id. The hot page receives writes. A disk region publishes through RegionPack. A memory-only region stays mapped and does not take a pack lock. A caller opens a region, appends a message, reads a window, and closes the region. The type refuses an empty conversation id. The type refuses a snapshot, a heap dump, and defragment in this version.
**semantic-key:** PK-1263

The SinkCut type holds one selection from ReichenbachSink. The ordinals follow the selection order. The headOver flag is true when the pin and the head exceed the allowance. The tokenSum includes the live line when that line is in the selection. A refused selection is a null return from #selectCut or #selectTape.
**semantic-key:** PK-1264

The Region type holds one conversation. The id is the conversation id. The lock is the region lock. The pin table tracks pinned pages. The index is the off-heap message index. The page map stores each page by page id. The identity map stores a RouxId and its message id. The hot page receives writes. The next message id starts at 1. The next page id starts at 1. The region pin starts false. The head count starts at 0, and the head ids start empty. The operator lock starts unset. The region UUID is random, and the creation time is the current epoch milliseconds. The closed flag starts false.
**semantic-key:** PK-1265

Region. 13. Last-ditch scenarios (do this **before** calling the stage done). The code tag is the next pass.
**semantic-key:** PK-1306

Fail rotate if spill is off and `resident-pages` would overflow (already locked).
**semantic-key:** PK-1382

Taken from `RouxEngine` and `RegionPack` on 2026-11-02. These sentences describe the code.
**semantic-key:** PK-1383
**semantic-key:** PK-1383

A clean hangup with persist on is already a reattach. The next open sees a manifest whose page count matches the forced slots.
**semantic-key:** PK-1401
**semantic-key:** PK-1401

