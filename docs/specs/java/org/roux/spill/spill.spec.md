# org.roux.spill

Component file. It carries how this package is built.

**Depends:** [java/org/roux/page/page.spec.md](../page/page.spec.md), [java/org/roux/page/Page.spec.md](../page/page.spec.md), [java/org/roux/roux.spec.md](../roux.spec.md), [java/org/roux/session/session.spec.md](../session/session.spec.md)

## Protected head region

The protected head is a resizable, write-protected window at the absolute start of the active context, and only an explicit operator operation may change it.

It may hold any static context the operator wants kept stable relative to the window: specifications, standing contracts, locked invariants, or any other material paging must not move. Once set for the session, the bytes are not immutable in an absolute sense. The operator may revise them. Paging and spill have no authority to alter, move, or evict anything in the region. The head behaves as a small memory store under operator control: the operator updates it at will, and spill skips it entirely. Volume, age, and pressure do not move it. Its size is set at runtime and persists across session recovery, so the frame stays at the position of strongest attention until the operator revises it.

**Truth.** The protected head is operator-sized, recovered with the session, and invisible to spill.

**Reason.** If spill could evict the head, the frame that governs the session would move to the place attention is weakest, which is the failure this region exists to prevent.

**Avoidances.** Do not let volume, age, or pressure move the head. Do not treat a paging pass as a writer of the head. Do not drop the head size on recovery.


**semantic-key:** PK-0815

## 7.2 Region mutex

Append, clear, and seal take the region write lock. Cursor reads do not. Chat waits. There is no failure after 50 milliseconds.

The lock is a reentrant lock. Readers use the sequence lock and the pin. An operator exclusive, later, should show in status as `memory: locked` rather than throwing into the prompt path.

**Truth.** Mutators take the region lock. Readers do not. Chat waits with no 50 millisecond failure.

**Reason.** A 50 millisecond throw fails a turn that was only waiting for the writer. Status is the place an operator lock is visible.

**Avoidances.** Do not fail chat after 50 milliseconds. Do not take the exclusive lock on a cursor read. Do not hide an operator lock inside a prompt error.


**semantic-key:** PK-3467
