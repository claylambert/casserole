# org.roux.model

Component file. It carries how this package is built.

**Depends:** [java/org/roux/id/id.spec.md](../id/id.spec.md)

# Roux

This page builds the paged memory. The map is [program-spine.md](../../aetheris/config/config.spec.md).


**semantic-key:** PK-0733 PK-0734

## Build

The type-level tag sits inside the class JavaDoc, and it reads ` * // @aa-sdd L40 aa-sdd-source-prose.md#TypeName` plus one hint word for the Roux page that already describes the type.

The source-prose page gains that heading. The heading cites the Roux page. The class prose names what the type is, the seat it holds, and the step a reader needs.

openRam keeps its tag, and the line becomes ` * // @aa-sdd G-08 G-09 Q-S1-1 aetheris-stages-1-3.finalize.md`. ToonCodec.encode carries ` * // @aa-sdd G-13 aetheris-stages-1-3.finalize.md` for the one MSG. RouxChatMemory keeps the G-08 tag on the method that already has it.

Methods that realize a seated requirement take the source-prose key, the type heading, and a hint. The seated methods are appendMessage, rotateHot, the hot checkpoint, windowIds, findSeq, inspect, memoryStatusLine, persistClose, and openRegion. RegionPack.publishPage, the page-in and page-out that hold the resident cap, FrameHeader.write, RouxIdGenerator.next, the index put that stores seq, RouxMessageMapper.roleOf, CryptoSink.assertDisabled, HeuristicTokenCounter.count, the pin method, and Page residency are the same kind of method. A getter is not in this list.

RouxWindowSpillTest keeps the type-level source-prose tag and a finesse hint. A second line names the type heading and roux-v1.md. The assertions stay.

The hint for the engine and the seated methods is roux-v1.md. The hint for rotateHot and publishPage also allows spillover-semantics.md on that same line. The hint for the identity types is roux-message-identity.md. The hint for the package types the design map names is roux-design.md. One spec file per tag line. The spec file word is the page the key lives in.


**semantic-key:** PK-0758

## Proof

The product tree compiles with tests skipped, and the window stays closed.

**Truth.** Comments and JavaDoc are the only change. Each public Roux type carries prose and one @aa-sdd tag that names the spec file. Methods that realize a seated requirement carry the same kind of tag. openRam names G-08, G-09, and Q-S1-1 on aetheris-stages-1-3.finalize.md. ToonCodec.encode names G-13 on that same file for the one MSG. ChatShell stays out. Behavior does not change. Rust stays.

**Reason.** A reader should be able to start at the type and land on the spec that type serves, and the prose should say what the type is, the seat it holds, and the step required, without overriding the tree. One spec file per tag line keeps the key on the page where that key lives. A getter is not a seated requirement, so it stays without a new tag.

**Avoidances.** Do not open a second revision. Do not change behavior, enable crypto, close requirement 15, or unlock roux-design.md. Do not drop roux-v1.md. Do not tag ChatShell. Do not backfill every private line in the historical tree. Do not tag a getter. Do not put two spec files on one tag line. Do not change copyright headers or the author tag. Do not add a lambda, Lombok, a public type, a public method, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not let the prose lock a design sentence over the tree. Do not retarget branch, action, stage, or the when field. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not move Rust.


**semantic-key:** PK-0759

## 16. Inspect

`message list` stays the human transcript. `message show <seq>` and `message find <idHi> <idLo>` inspect by locator, not by a filtered row number.

Today `message list` with a type filter prints a bracketed number that is the list index. With a filter, bracket 1 is not sequence 1. Keep the list as the transcript. Add, by longest-name match on the same processor, and not under a later memory-command tree: `message list` unchanged, with an optional `meta` token that prints one line of sequence, id, and page per message. `message show <seq>` is the full inspect of this session by locator. `message find <idHi> <idLo>` inspects through the identity map, decimal longs, with no hexadecimal required. Hexadecimal may come later. The show card is sequence, the two identity decimals, role, timestamp, tokens, type, page, offset, length, residency `HOT` or `RESIDENT` or `SPILLED`, crc `ok` or `fail`, and the unmarshalled text. Status adds `conversation:` and `roux:` with pages, hot, spilled, resident, durability, and sequence. An example shape is `roux: pages=3 hot=2 spilled=1 resident=2 durability=commit seq=128`. Out of this console slice: a raw payload dump, hexadecimal identity, and a cross-session find. The region is scoped to the session. Identity lookup belongs on operator debug, not on the prompt path.

**Truth.** Show and find take the real sequence or the two decimal longs. A filtered list index is not the locator. Residency is `HOT`, `RESIDENT`, or `SPILLED`. A bad crc is reported as `fail`.

**Reason.** A filter renumbers the brackets, so an inspect that used the bracket would open the wrong frame. The prompt path does not need the identity map.

**Avoidances.** Do not treat a filtered bracket as `seq`. Do not require hexadecimal for find. Do not dump raw payload in this slice. Do not find across sessions. Do not call the identity map from prompt assembly.


**semantic-key:** PK-3460

## 8. Message list

`message list`, `messages`, and `message list meta` keep walking the full index.

A reeled frame keeps its sequence, its role, its page, and its residency. The terse cap stays a display cap. Verbose still prints the body. The operator sees the tape. The provider sees the head.

**Truth.** List and meta walk the full index. The terse cap stays display-only.

**Reason.** The provider's head is not the operator's tape. Filtering the list to the head would hide the reel the rewind commands move.

**Avoidances.** Do not filter the list to the head. Do not renumber a reeled frame. Do not turn the terse cap into a storage cap.


**semantic-key:** PK-3465

## 7.4 Dead-log and stale views

A retired page id is not reused until its pin count is 0, and an old address returns a stale view.

Retire in version 1 follows region clear or drop. A `BitAddress` or cursor with the old page id and the old generation gets `OperationStatus.STALE_VIEW`. Leftover bytes are never interpreted as frames. Clear bumps the region epoch, allocates a new page 0 and a new index, and the old pages are retired and then unmapped or deleted. An in-flight get is single-threaded with the write after the model call, so this is mostly for a later console.

**Truth.** Retired page ids wait for pin count 0 and then stay unreadable as frames. A stale address is `STALE_VIEW`.

**Reason.** Reusing a page id while a cursor holds it would show the next conversation's bytes as the old one. Interpreting leftover bytes would resurrect cleared turns.

**Avoidances.** Do not reuse a page id while pins remain. Do not decode leftover bytes after retire. Do not return success for a stale generation.


**semantic-key:** PK-3468

## Roux message identity

Page I/O stays fast and message identity stays unique across a cluster by using two primitive keys, with no hexadecimal parse and no node-id registry.

This is a final analysis for agreement. It is not implemented. No plan and no code follow until that agreement is named. The locator stays dense. The identity is a 128-bit pair. Neither key is a page pointer.

**Truth.** Identity is two primitive longs in a time-ordered layout, and the page is found only by the dense sequence.

**Reason.** One fused 64-bit number cannot be a dense index, a cluster-unique id, and a stable page pointer at the same time. Spill throws away a native address.

**Avoidances.** Do not implement from this page before it is agreed. Do not parse hexadecimal on the engine path. Do not keep a node-id registry for this layout.


**semantic-key:** PK-3480
