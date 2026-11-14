# Method

[Index](../common.spec.md). [Canvas and engine](canvas.spec.md).

# AA-SDD architecture standard

| Field | Value |
|---|---|
| Name | Architecture-Augmented Spec-Driven Development |
| Version | |
| Bound | |
| Mode | Architecture standard. Not a product. Not code. Not a boot file. |
| Audience | A friend standing up an instance, and a runtime that must keep the concept |

This file is the bound description of the architecture. It does not drive the truth of an instance. A law outranks it. Repair this file when it disagrees with one of those. Do not repair them so this file can stay wrong.

If that draft and this file disagree, this file wins. Neither file boots a session.

## Standard frame (read this first)

This frame is the page a system, or a human who was not in the room, can accept. The sections after it are the requirements.

### 0A. Scope

This document is the architecture standard for AA-SDD.

It is for three readers.

1. **A runtime.** It reads this file so the concept of the method stays in force across sessions. It does not boot from this file. It does not treat this file as the current action.
2. **A friend** who wants a base instance and a page they can hold. Every term used as a requirement is defined here or in the file this standard names.
3. **The authoring instance.** Daily work already runs on the laws, the constitution, and the procedure files. This file is the tidy copy of the architecture those files share.

In scope:

- What the method is, and what it is not.
- The file graph a session trusts: system files, product files, plans, and spec files.
- How a spec file is declared, including a batch, the keys, and a dependency tree.
- The order of work: spec, then pre-plan, then plan, then build.
- How a token reaches the, including a bracket command that is already defined and a bracket command that is not.

Out of scope:

- A product. The owner writes that constitution. This file does not fill it.
- One instance's seat names, story, or private notes.
- The interchange grammar. This file does not amend it.
- A video bay, a picture pipeline, or an application extension. Those cite this file. They do not amend it.
- A delivered kit. A kit is a cut. It is the text of the day it was cut.

**Normative references.** The laws file of the instance, the prime-directives table, and the baseline. This file does not restate them into a second list that can drift.

### 0B. Terms

A later section that uses one of these words means this definition.

| Term | Definition |
|---|---|
| AA-SDD | Architecture-Augmented Spec-Driven Development. The name has no other expansion. |
| Architecture | The English files that say what will be built, written before the build. |
| Spec file | The requirements for one batch. A later reader can check them without asking the owner. |
| Batch | One spec that already holds the requirements, the stack, the libraries, the pre-conditions, the post-conditions, and **Depends** for that piece of work. |
| Pre-plan | The sketch. A direction the owner may still reject. It is not a build source. |
| Plan | The decision the build follows. It exists only after the owner asks for it and approves it. |
| Working spec | The spec for the stage in front of the instance. It cites the constitution and carries the list of stages already finished. |
| Constitution | What the product is. One project, one constitution. A new instance starts with a blank one. |
| | A law above every other law and above the constitution. The legal row is the default. |
| Law | |
| Owner | The human who names the work, answers an open question, and approves a plan. |
| | The seat that files, links, and keeps the record. Does not invent the product, the seat names, or the next phase. |
| System file | The status panel, the index, and the boot file. The owner may look. The owner must not modify them. |
| Token | A directive or a boolean the syntax reference already defines, or a bracket command the files the same turn. |
| System directive | A bracket command that already has a row in the syntax reference. In that sentence it is the action. |
| Resume checkpoint | The state update on the phase card. Writes it when the owner asks, because the session holds work and has become bloated. |
| Instance | Any project that holds a copy of this standard and runs under it. |
| Authoring instance | The project that amends this file. A friend copies it. A friend does not edit the copy and keep the version line. |
| Cut | A copy frozen at a stamp and handed over. It stays that stamp. It is not the live standard. |
| Conformance | The statements in section 11. A runtime or a friend accepts the statement that matches the work. |

### 0C. Requirements language

In this file the words already on the page mean the following. They are tests. They are not capital directives.

| Wording on the page | Means |
|---|---|
| is, does, writes, reads, the default is | A requirement. A conforming implementation does that sentence. |
| Do not, is not, must not | A prohibition. |
| may | Permitted. Not required. |
| once | One time in the scope the sentence names. A second time in that scope does not run. |

Writing the same words in full capitals does not make a stronger requirement. `MUST` is not a directive.

### 0D. Normative sentences and instance speech

**Normative.** A sentence that states what is written, what is kept, what is omitted, or what fails is a requirement.

**Instance speech.** A sentence in some other file that names one project's seats, a story, or a private note is how that instance speaks. A friend keeps their own names and still does the normative sentence. This file does not require those names.

Seat names travel as duties. `OWNER` and are the duty labels this standard uses. A person's name is the owner's to write. An empty seat stays unnamed. A runtime must not invent one.

### 0E. Immutability

The standard text is the same in every conforming instance.

- An instance copies this file. It does not edit the copy. It does not paraphrase it into a second architecture.
- A changed sentence is a rewrite.
- A dated amendment replaces the previous text of that line in the authoring instance.
- A newer version line exists only when 1.0 cannot hold the architecture, and only when the owner of the authoring instance locks it.
- A defect or a clearer sentence goes back to the authoring instance. The friend does not patch the copy and keep the version line.
- A cut is immutable, and it is not the live standard. A friend who holds a kit holds the kit. A friend who wants the current standard takes this file.
- The operator manual teaches this file. If the manual and this file disagree, a later session follows this file and the manual is changed. The manual is not changed by editing this file to match a short paragraph.

### 0F. What a new reader may set aside

Set these aside and the conformance statements still stand.

- Any seat name that belongs to one instance.
- A story setting. It changes tone. It does not change the files.
- A product constitution. It is that product. It is not this method.
- Picture pipelines and other extensions. Open them only when the work is that extension.
- The 07:41 draft in this directory. It is the earlier map. It is not a second standard.

### 0G. Reading order

1. This frame. Scope, terms, immutability, what may be set aside.
2. Sections 1 through 4. The name, what the method is, what it is not, and the rank.
3. Sections 5 and 6 when the work is a spec. Section 7 when the work is a build.
4. Section 8 when the work is a sentence to the. Section 9 when the work is a new session.
5. Section 10 for a runtime. Section 11 to test conformance.

## 1. Name

AA-SDD means Architecture-Augmented Spec-Driven Development. Do not publish a second expansion. A second expansion is a second name, and a later session will not know which string the files mean.

Is the interchange. AA-SDD is the method. They are not the same document.

## 2. What AA-SDD is

Architecture comes first. The spec is written before the build. English files are the source a later session reads. The chat is not that record. A summary pasted into a prompt is still chat.

The owner names the work and approves the plan. Files the record. The build realizes the plan the owner approved. A seat advises and files. A seat does not invent the product, the seat names, or the next phase.

The spec is reasoned as a batch before a pre-plan exists. The pre-plan is a sketch. The plan is the decision. The build follows the plan. That order is where the requirements stay coherent, and where the instance does not pay for a fresh reasoning pass on every small addition before the spec is complete. One local commit still closes a turn that changed files.

Make that reasoning as complete as the owner can make it before any iteration. Enough of the problem has to be in the file that a later session sees one picture. Specify the batch the way an architecture is specified. Then iterate from that file: sketch, plan, build. A spec that arrives one sentence per turn spends the session on rediscovery, and each addition can drift from the lines already accepted. A requirement found during that iteration is written back into the spec and marked `[NEW IN...]` before the sketch may use it.

## 3. What AA-SDD is not

- It is not a product, a stack, or a launch date. Those belong in the spec the owner is writing, or they stay blank.
- It is not a law and it is not a constitution. It does not take the next L-number.
- It is not a boot file. A cold session does not open this directory to find the next action.
- It is not a second copy of the procedure files. A procedure owns its steps. If this file and a procedure disagree about a step, the procedure wins, and this file is repaired.
- It is not permission to fill an empty field so the page looks finished.

## 4. Rank

| Rank | What it is | What a conflict does |
|---|---|---|
| 1 | The guard rail. | The row wins. The work stops or stays inside that row. This file is repaired if it crossed the rail. |
| 2 | Laws. How the instance is allowed to work. | The law wins. This file is repaired. |
| 3 | Constitution. What the product is. | This file does not fill the constitution. |
| 4 | This standard. The architecture. | Manuals, sketches, and chat are repaired to match it. |
| 5 | Operator manual and procedure files. | They teach. A procedure's own steps win over a paraphrase in this file. This file wins over a paraphrase in the manual about what the architecture is. |

The index names the live file for a module. The status panel repeats that path. If they disagree, the index wins, and the rewrites the panel the same turn. The owner must not edit either system file so the other can stay wrong.

## 5. File graph

Three files are system files.

| File | Job |
|---|---|
| Status panel | Publishes the one current action, the headline, the log, and the pointers. |
| Index | Names the live file for each module. |
| Boot file | Says why the startup order exists. It is not a second procedure. |

The owner may look at all three. The owner must not modify them.

| File | Job |
|---|---|
| Laws | |
| Prime-directive table | The guard rail. The legal row is the default when a sentence names no row. |
| Constitution | Product truth. Blank until the owner writes it. Do not build from a draft. |
| Working spec | What remains to build. Cites the constitution. Carries finished stages. |
| Plans | The 's files, after the owner asks and approves. The owner does not create, rename, edit, or delete them. |
| Syntax reference | The token list. The Do column is the file effect. |
| | The ordered read for a session with no chat memory. |
| Resume card | The shape of a phase card. The kit ships none. Writes one when asked. |
| | The interchange. Two paths. A spec file, and a data packet. |

Other files are product, pictures, or ledgers. They are pages. They are not the method.

## 6. Declaring a spec

A spec file holds all of the requirements for the batch. It is not a sample and not the first idea. A requirement that stays in the conversation is a requirement the next session does not have. Make the reasoning as complete as the file can hold before a sketch. Section 2 is that rule. The headings in a starter layout are a starting point. The owner may rename, drop, or add a section. The keys in this section stay the declaration.

The owner declares a spec by writing these keys. Each bold title is a key. The text under the key is the value. An empty key means the owner has not decided. Do not fill it to make the page look finished.

**Stack** is the language and runtime the owner has chosen.

**Libraries** are the libraries the owner has chosen. A library named only in chat is not in the build. A version the owner has not chosen stays blank.

**Pre-conditions** are what must already be true before the batch starts. If a pre-condition is false, the batch waits.

**Post-conditions** are what a later reader can check when the batch is finished, without asking the owner. A mood, a hope, and a launch date are not a post-condition.

**Depends** is the other spec files this one requires. Write paths. When the line means one part of a file, add the section after a hash mark. A parent lists its children. A child may list its own. Reads a file after the files it names. Do not paste a depended spec into the parent. A cycle has no first file. Break the cycle in the **Depends** lines before a pre-plan. A path that does not exist is a missing spec. Create the file and write its batch, or remove the line. Do not leave the missing path for the build.

```text
**Stack:** the language and runtime you have chosen.
**Libraries:** each library you have chosen. Leave a version blank until you know it.
**Pre-conditions:** what is already true, or this batch waits.
**Post-conditions:** what is true when this batch is finished.
**Depends:** product/ledger.draft.001.md, product/billing.draft.001.md#post-conditions
```

`[NEW IN...]` marks a block the owner just added. Replace the dots with a short name of that block. Reads the marked block as the new material. Unmarked text stays as it was. The mark does not accept the file, and it does not open a pre-plan, a plan, or a build.

A requirement that first appears in the sketch, the plan, or the code is not in the batch. Write it into the spec, mark it `[NEW IN...]`, and reason it there before a pre-plan may use it.

The paths in the example are illustrations. They are not files this standard ships. Do not create them. A real path under **Depends** that does not exist is a missing spec. Create that file and write its batch, or remove the line.

## 7. Cadence

```text
spec, reasoned as a batch
 -> sketch, which is the pre-plan
 -> build against that plan
 -> scan of what remains
 -> new working-spec serial, carrying the finished stages
```

If this section and that file disagree about a step, that file wins.

Do not open the pre-plan while a known requirement is missing from the spec. The sketch must not contain a requirement the spec does not already state. Do not build from a draft, a sketch, or a kit. Do not write a plan until the owner asks. The word Build, with no file and no stage named, is not a request to start.

## 8. Tokens and

One sentence to the names one file and one directive.

A word in full capitals is a directive or a boolean. A boolean is a yes or no a file already stores. `UNNAMED` means that seat has no name. The words yes, true, on, and ready in a comment do not set a boolean.

Each binds only the file named in the same sentence. A comment does not add a second file or a second directive.

A bracket command is a token in square brackets, written in capitals, with a hyphen between words when it has more than one word.

When is already in the syntax reference, it is a system directive. It is the token for the action. In that same sentence it supersedes a capitals-only word that has no backticks and no brackets. Runs the bracket form. The capitals-only word does not take its own action. A capitals-only word used alone, with its row already in the list, still binds the one file named in that sentence. A spelling in backticks is a citation. The backticks alone do not run the command.

When the owner uses a bracket command that is not in the syntax reference, the adds the row in that same turn. The defined shape is:

(= what it does.; why = the purpose.)

The text before `why =` is the description and goes in the Do column. The text after `why =` is the purpose and goes in the Means column. If the owner leaves the parenthetical off, the still adds the row and infers the purpose and the description from the sentence, and from the words inside the brackets. Does not ask the owner to draft the row first. After the row exists, the command means the Do cell. A capital word with no brackets that is not in the list, and is not a boolean the folder already prints, does nothing. Must not guess a meaning for that word.

`[NEW IN...]` is already defined. Using it does not add a second row.

And are one instruction block. Is the short spelling. They share one row. Closes the block and is not a second command. A between `---` lines ends at or at the next `---`. The block names one change and the place: where the block sits, or the place the words name. Applies that change and does not rewrite the ask. Text the block does not name stays. The block is not the product sentence. It stays under the changed text until the owner says to remove it. The block does not accept the file, open a pre-plan, or start a build. Using either spelling does not add a second row.

Writes the report there. A named seat writes its report there. The owner reads it. The report is not written inside a memorandum the owner wrote. A reply is a new memorandum. May open that new memorandum. It is not an edit of the owner's memorandum. Do not exchange notes in the same memorandum.

`[NEW ON...]` is the search mark on that new memorandum, and on any new item added to a spec. Replace the dots with the date, `YYYY-MM-DD`, as in `[NEW ON 2026-10-16]`. Searches for `[NEW ON` and does not diff the file to find the new block. The mark does not accept the file, open a pre-plan, or start a build. `[NEW IN...]` still names a new requirement. A new requirement carries both marks when it is also new on this date.

Pins a key action there before the turn ends: the state, and what occurred. The owner may pin a note back to the cohort. A named seat may pin in that seat's own voice. A new note is a new pin at the top. No one writes inside another person's pin. Newest pin first. The date and the time are the key. The board is not a system file. It does not boot.

Is the open grid. Board changes its alert line to ` SYSTEM REPORT` and keeps the link. When OPEN is empty, the line says the grid is clear. An alert says what is wrong and where. It does not accept the file, lock the file, or become a law. A discrete change with no acceptance is an open alert. Flags it and does not treat it as accepted. When the owner closes it, the moves the item under CLOSED and restores the clear line. Closed items stay. Using does not add a second row.

The index echoes the same string. If the two strings disagree, the index wins and the rewrites the panel. The field is the baseline a kit was cut from. `VER` on that file is the file format. A patch names the version it leaves and the version it reaches. It does not skip. Applying a patch is a turn the owner names. The owner may look and must not edit the panel or the index to bump it. Saying the field does not add a token-list row.

The invitation runs only after a story setting is written. The ask is not required. The owner does not type the character. Other seats stay `UNNAMED`. `ASK-CAST` stays until the owner has named the seats the owner means to name.

The syntax reference is the token list. This section does not copy every row. If a row and this section disagree about a file effect, the row wins for that token, and this section is repaired if it taught the architecture wrong.

## 9. Session start and the resume checkpoint

The next issue belongs to a new session.

The first install is the reset. The repository has the copied folder and no work of the owner's yet. The session reads the files, prints the report, and stops. It does not fill the roster, the vision, or the product document. It does not start a build.

It is not a reset. It does not clear the roster, the vision, the product document, the specs, or the plans. It continues the work already present.

When that work is in the session and the session has become bloated, the owner asks the to establish a RESUME CHECKPOINT state update. The ask is one sentence. RESUME CHECKPOINT is the name of the update. It is the phase card. It is not a new numbered rule, and it is not a token that starts a build by itself. Writes the update on the card, at the path the status panel will name. The owner must not edit the panel or the index to do it. The update says where the work is, what is already done, and what the next session continues. An instance ships no card. Writes one when the owner asks. A startup that has no work and no card does not invent one.

That session resumes from the checkpoint. It continues the work the checkpoint states. It does not invent a piece of work the checkpoint does not state. It does not build a program the checkpoint does not already name.

The card wins over the headline. The headline is a subject line. It is not the resume source when the card disagrees with it.

## 10. How a runtime behaves

A runtime that has read this file does the following.

- It treats the English files as the record. It does not rebuild the project from chat.
- It does not open this directory to choose the next action. The status panel publishes that action. The index names the live files.
- It does not modify a system file on the owner's behalf. Maintains those files.
- It does not invent a product, a seat name, a library version, or a phase.
- It reasons a batch in the spec before it sketches. The reasoning is complete enough that the problem is one picture. A spec that arrives one sentence per turn is not that picture. It does not treat a requirement that first appears in the sketch, the plan, or the code as specified.
- It files a plan only after the owner asks and approves. It does not let the owner manage the plan files.
- It runs a listed bracket command as the system directive. It files an unknown bracket command the same turn, inferring the purpose and the description when the parenthetical is absent.
- On a first install it stops after the report. After work is on disk it resumes the checkpoint.
- It does not boot an instance from this file.

Reading this file reinforces the concept. It is not a substitute for the laws, and it is not permission to skip a stamp the laws require.

## 11. Conformance

Meeting one statement does not require the others.

**Conforming spec file.** It follows section 6. It states the batch: the requirements, the stack, the libraries, the pre-conditions, the post-conditions, and **Depends**. The reasoning is in the file, complete enough to see the problem as one picture, before a sketch exists. An empty key stays empty. A new block is marked `[NEW IN...]`. A requirement that is only in chat is not in the file. The file is not a data packet. While it is a draft, the instance does not build from it.

**Conforming plan.** It follows section 7. It adds no requirement, library, pre-condition, or dependency the spec left out. The owner asked for it and approved it. The owner did not create, rename, edit, or delete it.

The first install stops after the report. A later session with work on disk continues from the checkpoint and does not clear the files.

**Conforming runtime.** It follows section 10. It can say which file wins under section 4. It does not boot from this file. It does not invent a seat name.

**Conforming instance.** It holds this text, or it declares the cut it holds and does not call that cut the live standard. It does not edit the baseline. Its product constitution is its own. Its seat names are its own.

A friend who can say yes to the matching statement has accepted the standard.

## 12. What this file will not do

- It will not replace the laws file.
- It will not replace a constitution.
- It will not amend
- It will not boot a session.
- It will not name a product, a cast, or a phase the owner has not named.
- It will not stand in for a white paper. A paper argues. This file bounds the architecture.
- It will not retarget the current action of an instance that already has one.

A friend does not keep a quiet local fork under the same version line.

# AA-SDD — baseline

**Mode:** process. **Not product Java.**
Interchange standard. The same text in every conforming instance. Two paths. Spec files are serial form. Data packets transfer or persist. Packet intent only upon request. The acronym expands only as §1. Interoperability is §2.1. The file is the method. ENCODE, lock, invariant, contract, the winning sentence, the spec reader, field value, and named field intent are this amendment. Tensor geometry is the reading of the headed English.
This file wins if they differ.

This file is the whole baseline.

A conforming instance copies this text and does not edit it. The standard frame below is what an engineer who was not in the room accepts.

The 2026-09-26 export handshake is no longer the law. It is a pointer. Copies that still say "this handshake is the whole law" are snapshots. This file wins.

---

## Standard frame (read this first)

They do not need this frame in daily use.

### 0A. Scope

This document is the interchange standard for.

1. **Another system.** It sends or receives a data packet, or it reads a spec file and seeks a heading.
2. Every term is defined before it is used. That is the ordinary expectation. This frame meets it.
3. This frame is the tidy copy of that law.

- The two paths. A spec file. A data packet.
- Interoperability. §2.1. The file is the method. A key keeps its name. The sentence under the key is the meaning. The first sentence wins. The quote is the receipt.
- ENCODE. The verb compiles the named source onto one path. §0B.
- Lock. Four jobs. Bytecode, file state, packet seal, and a heading that can be executed. §4.
- An invariant is a key. A contract is a key. §4.3.
- The packet grammar, the field value, the English CRC, and the prohibition on JSON, XML, or a vector dump as the record.
- How an extension is named, and that it lives outside this file.

- Application hatches. Orbital intent is one of those. It is defined in the bay that needs it.
- A product, a build, or a video bay. Those cite this file. They do not amend it.

**Normative references.** None. This file is sufficient for the baseline. No extension is required to implement it.

### 0B. Terms

| Term | Definition |
|---|---|
| | English-Native Intermediate Representation. EN is English-Native. IR is Intermediate Representation. No other expansion. English as locked bytecode. The interchange language of this standard. Spec files and data packets are written in it. |
| ENCODE | The verb. It compiles the named source onto one path. Spec path when the human says spec, or raw prose for a spec. Packet path when the human says transfer, persist, or hand this to another system. If the sentence does not name the path, the encoding seat asks. It does not guess. It does not compress, base64-encode, or swap the record for a popular serialization. |
| Lock | Four jobs. Bytecode: the same source on the same path yields the same keys. File state: draft until the requirement owner accepts. A draft does not bind a later build. Packet seal: `HEADER.state` and `INTEGRITY.seal` are `LOCKED`, and the packet is complete for rehydration. That seal does not accept a spec. Notes: a heading with no first sentence that can be executed is not locked. It is notes. |
| Baseline | This file. |
| Path | One of two jobs under the same name. Spec file, or data packet. |
| Spec file | A markdown document. A later reader seeks one heading and obeys the first sentence under it. It is not a packet. |
| Data packet | A headed-English record. It transfers data, or it stores data so a later read can rebuild it. |
| Hatch | A named block in a packet: `HEADER`, `STRUCTURED-DATA`, `PACKED-TEXT`, `PACKED-INTENT`, `CARRIER-ANCHOR`, `SHOW-STATE`, `INTEGRITY`. |
| Rehydration | Building the original structure and every original value again, using only the packet. |
| Intent | An instruction: what must happen because the text exists. On a spec file, the encoding seat compiles it into the headings when asked to encode prose. On a data packet, the packet carries intent only when the human requested it in that turn. |
| Interoperability | The file is the method. A receiver who was not in the room seeks one heading and obeys its first sentence, or rebuilds every structure and every value from the packet alone. Not a second format, an API, a sidecar schema, or a test harness. |
| Key | The seek address on the page. On a spec, the heading. On a packet, the source field name. A bound token such as is a key. The key stays the source's name. A second name is a second key. |
| First sentence | The law under a spec heading. It wins. The requirement owner's prose quoted under it is the receipt. The quote is not a second law. A second requirement is a second heading. A tighten that changes what must happen is a new heading. |
| Invariant | One key in a spec. The first sentence is what must stay true. The heading is marked frozen, or a different heading is marked open. A later emit that contradicts a frozen invariant fails. The reader does not reopen it. |
| Contract | One key in a code-family spec. The first sentence is what the source must match. A forbidden invention is its own key. A synthesis pass that adds an architecture the contract did not name fails. A hand edit that changes the source without a new heading is flagged under a heading and is not a new spec. |
| Value | The source value on a packet field row, under the key `value`. It is not a paraphrase. The anchor remains the original form. A receiver compares each value to the anchor. A mismatch is a CRC fail. |
| Tensor geometry | The model's reading of the headed English. It is not an embedding, a coordinate list, or a vector dump. |
| English CRC | The recount in §7. It is a comparison of counts, field values, and witnesses. It is not a hash. |
| Authoring instance | This directory is that instance. |
| Instance | |
| Extension | A named addition in another file. It does not edit this one. Its version line has the form `x-{NAME}`. |
| Cut | A copy frozen at a stamp and handed over. It stays that stamp. It is not the live baseline. |
| Live baseline | This file, as amended in the authoring instance. The amendment stamp in the version line identifies it. |
| Requirement owner | The human who owns the requirement. In this instance the name is |
| Encoding seat | The seat that compiles headings from prose when asked. In this instance the name is |
| Conformance | The statements in §0F. An engineer accepts the statement that matches the work. Instance speech is not on that list. |

### 0C. Requirements language

This standard does not import an outside keyword spec. In §0 through §11 the words already on the page mean the following.

| Wording on the page | Means |
|---|---|
| is, does, survives, the default is | A requirement. A conforming implementation does that sentence. |
| Do not, is not, fails, a CRC fail | A prohibition. |
| optional | Permitted to omit. `SHOW-STATE` is optional. A field `intent` line is omitted unless the human named that field's instruction. |
| frozen | The heading's first sentence must stay true. A later emit that contradicts it fails. |
| open | The heading is still allowed to move. It is a different heading from a frozen invariant. |
| may | Permitted. |
| upon request | Present only after the human asked, in that turn. Otherwise absent. |

`PACKED-INTENT` is required once intent was requested. It is forbidden when intent was not requested. "Optional upon request" means that rule. It does not mean the encoding seat may invent the request.

### 0D. Normative sentences and instance speech

Two kinds of sentence live in §0 through §12.

**Normative.** A sentence that states what is written, what is kept, what is omitted, or what fails is a requirement. The procedures in §4 and §5 are normative. The packet grammar is normative. The English CRC is normative.

A friend keeps their own names and still does the normative sentence.

Read the names this way.

| Where this file says | A conforming instance reads |
|---|---|
| | the requirement owner |
| | the encoding seat |
| | the requirement owner locked the amendment, out loud, in that turn |
| Y, RC, Final | this instance's acceptance ceremony for a spec file |

The requirement that travels with the ceremony words: the spec file shows a state, and a locked file is not rewritten in place. A new truth is a new file. A vibe is not a lock. A metaphor is not an acceptance.

### 0E. Immutability

The baseline text is the same in every conforming instance.

- An instance copies this file. It does not edit the copy. It does not paraphrase it. It does not clean it up.
- The words match the authoring instance. A newline convention is not an amendment. A changed sentence is a rewrite.
- A rewrite is not. Two files that both claim and differ in their sentences are not the same standard.
- Is one version line. A dated amendment replaces the previous text of that line in the authoring instance. An older stamp is an older amendment. It is not a second baseline.
- A newer version line exists only when 1.x cannot hold the law, and only when the requirement owner of the authoring instance locks it. §9. Instances then copy that new file.
- An extension does not change this file. It lives outside it, under its own version line.
- A defect, a wish, or a clearer sentence goes back to the authoring instance. The friend does not patch the copy and keep the version line.
- A cut is immutable, and it is not the live baseline. Seed kit prod 001, cut 2026-10-15 19:36, is that cut. A friend who holds the kit holds the kit. A friend who wants the current standard takes this file. This amendment does not recut the kit.

### 0F. Conformance

An engineer, or a system, accepts by meeting the statement that matches the work. Meeting one statement does not require the others. Shop speech is not another statement.

**Conforming spec file.** It follows §4. It says what it is, what it is not, and a token. One heading per decision. The heading is the key. The first sentence under the heading is the requirement, and it is the meaning of that key. The first sentence wins. The quote under it is the receipt. A second requirement is a second heading. One family in that file. An invariant is a key marked frozen or open. A contract is a key. A head that already has a file is linked, not pasted. The spec is not wrapped as a data packet. Its state is visible. A draft does not bind a later build. A locked file is not improved in place.

**Conforming spec reader.** It reads a conforming spec file. It seeks the heading and obeys the first sentence. It treats the quote as the receipt. It refuses a contradiction of a frozen invariant. It does not invent past the contract. It does not build, fire, or stitch from a draft.

**Conforming data packet.** It follows §5, hatches in that order. Every source structure and every source value survives, under the source name, in the source order. Each field row carries `value`, the source value, not a paraphrase. `CARRIER-ANCHOR` is the original form, or a headed English rendering that still contains every value. Image means that rendering. It does not mean a summary. `HEADER.intent` is `omitted` unless the human requested intent in that turn. When intent was not requested, `PACKED-INTENT` is absent. A field `intent` line exists only for an instruction the human named for that field. The English CRC in §7 passes. `INTEGRITY.orbital` is `0` unless a named extension defines hops. JSON, XML, and a vector dump are not the record.

**Conforming receiver.** It reads a conforming packet. It does the job from the headed English alone. §2.1. It does not require JSON, XML, an API, or a sidecar schema. It compares each field `value` to the anchor. A mismatch is a CRC fail. It does not mint a field `intent` the human did not name for that field. It recounts §7. On a CRC fail it does not execute. It preserves an unknown hatch and still checks the baseline hatches. It does not treat `ORBITAL-INTENT` as law unless that extension was named.

It does not edit the baseline. Its extensions are named `x-{NAME}` and live outside this file. It may keep its own seats and its own speech. Those are not required for conformance.

### 0G. What a new reader may set aside

Set them aside and the conformance statements still stand.

- Names and, and any other seat name.
- The ceremony words Y, RC, and Final, provided the spec file shows a state and a lock is not rewritten in place.
- Cadence files and the video-bay core. Open them only when the work is that family inside an instance that uses them.
- Orbital intent, and any bay hatch. They are extensions.
- The closed brief named by `HEADS.ENIR`. It is history. It is not the protocol.

### 0H. Reading order

A reader who was not in the room reads in this order.

1. This frame. Scope, terms, conformance, what may be set aside.
2. §0 through §3, including §2.1. The two paths, the name, what is, interoperability, what it is not.
3. §4 when the work is a spec, including the invariant and the contract in §4.3. §5 when the work is a transfer or a store. §5.3 is the worked packet for a turn that requested intent. §5.4 is the default. §5.5 is a nested value and a table row. Intent is omitted in §5.4 and §5.5.
4. §7, then §8, then §11. The recount, the behavior, the checklist.
5. §6 when the payload is a story, a roster, or a scene.
6. §9 only when the work needs an extension.
7. §10 and §12. The carrier note, and the record of what changed.

## 0. Two paths

** is English as locked bytecode. It has two paths. They share the name. They do not share a job.**

**Spec file.** Structured and formal, the way a serial is built. A later session seeks one heading and obeys it. §4.

**Data packet.** Transfer from one system to another, or persistence. Every structure and every value survives. Pack as far as a full rehydration still allows. Intent is embedded only when the human requests it. §5.

Both paths are a direct symbolic transfer into a language model's tensor geometry, without a context pile, without a sidecar schema, without a vector dump. The story, the roles, and the headed English are the bytecode. The model reads the headed English. Tensor geometry is that reading. It is not an embedding and it is not a coordinate list.

A smaller pile of words is not the goal of a spec. A dropped value is a failed data packet. Hatches a human cannot read are not on either path. Do not encode a spec as a data packet. Do not encode a transfer or a store as a serial. If a turn needs both, write both and link them.

## 1. Name (do not stray)

- **** = **English-Native Intermediate Representation**
- Letter lock: **EN** is English-Native. **IR** is Intermediate Representation. That pairing is the only expansion.
- When asked what the acronym stands for, answer with that expansion. Do not invent another expansion from the letters.
- Use: is the interchange language of this standard. A spec file is written in it so a later reader seeks one heading and obeys the first sentence. A data packet is written in it so another system rebuilds every structure and every value. Headed English is the bytecode that carries intent into the model. It is not a format beside English.
- Expansion lock: **English as locked bytecode**
- **AA-SDD** = **Architecture-Augmented Spec-Driven Development**

AA-SDD is the sister plate. It is not a synonym for. AA-SDD freezes architecture and spec before orchestration. Is the protocol those specs and payloads are written in.

Not "English-ish notes. Not "a compressed prompt. Not "the JSON we wrap English in."

## 2. What is

1. **Source** is ordinary English and headed English fields. A human writes it. A human can read it back.
2. **Bytecode** is that English locked. Deterministic headings. No leftover chat. The same source on the same path yields the same keys.
3. **Seek, not scrape.** The words are a symbolic address. Another model should land in the same neighborhood. It should not retrieve a document from a RAG index.
4. **The path in use is the encoding.** A spec file is not a packet. A packet is not a spec. There is no hidden bus. What is not in the file is not in the program.
5. **Packing follows the path.** On a spec, a smaller pile is not the goal. On a data packet, pack as far as a full rehydration still allows. If a human cannot read it, the encode failed. Embeddings, coordinates, and vector blobs fail both paths.
6. **Intent follows the path.** On a spec, when the requirement owner says encode from raw prose, the encoding seat's intent is the compile of the headings. In this instance those roles are named The standard frame maps the names. On a data packet, intent is embedded only when the human requests it. Do not invent intent. Do not bolt it on after the fact.

### 2.1 Interoperability

Interoperability is the file. A receiver who was not in the room does the job from the page alone. On a spec, the receiver seeks one heading and obeys the first sentence. On a data packet, the receiver rebuilds every structure and every value. That is the whole method.

The encoding seat does not reach interoperability by choosing a popular carrier. JSON, XML, YAML, protobuf, an API, a version-control scheme, and a test harness are not the method. §3. A boundary the human named may serialize after the record is locked. That artifact is not. §10.

Looking at the data, the encoding seat does these five things. In this instance the seat is and the requirement owner is The standard frame maps the names. The receipt is what he writes under the key.

1. **Name the path.** Spec file, or data packet. The path is the encoding. Do not write one as the other. §0. If the human did not name the path, ask. Do not guess.
2. **Keep the key.** On a spec, one decision is one heading. The heading is the key. On a packet, the source field name is the key. A bound token such as is a key. The key stays the source's name. A second name is a second key. Do not rename. Do not invent a synonym. §6. §7 rule 6.
3. **Bind the meaning to the key, on the page.** The meaning is one sentence a later reader can execute. It sits under that key. It does not sit in a glossary, a comment, a schema, or a memory the file does not hold.
 - Spec: the first sentence under the heading is what must happen because that heading exists. That sentence is the encoding seat's compiled intent. The first sentence wins. The requirement owner's prose is quoted under the heading. The quote is the receipt. It is not a second law. A second requirement is a second heading. A tighten that changes what must happen is a new heading. The seat does not add a requirement the prose did not carry. §4.1. §4.2.
 - Packet, intent not requested: `purpose` says what the field is, in the source's words. A bare name stays a bare name. No field `intent`. No `PACKED-INTENT`. §5.1. §5.4.
 - Packet, intent requested: a field `intent` line exists only for an instruction the human named for that field. Otherwise omit it. The seat does not mint a per-field instruction. `PACKED-INTENT` is one compiled sentence for the whole payload. It is not a slogan. It is not a second document. §5.1. §5.3.
4. **Leave the receipt on the key.** The quote, the source name, the purpose, and an intent line that was requested are the receipt. What is not written under the key is not in the program. Do not point at a hidden memory, a retrieval index, or a sidecar schema. The key is in the paste. §6.
5. **Stop where the receiver can still do the job.** A human can read the page aloud. On a spec, the next session seeks the heading and obeys the first sentence. On a packet, each field row carries `value`, the source value. `CARRIER-ANCHOR` is the original form. A receiver compares each `value` to the anchor. A mismatch is a CRC fail. The English CRC is the check. It is a recount of counts, values, and witnesses. It is not a hash and it is not a test plan. §7.

A model asked how achieves interoperability, how to encode, or how a key carries meaning or intent, answers from this section and from §4 or §5 for the path in use. It does not invent a stack.

## 3. What is not

Do not emit these and call them:

- JSON as system of record. JSON may exist downstream at an integration boundary the human named. It is not.
- XML, `<Node:ID>`, AST dumps, protobuf, base64, YAML-as-source-of-truth.
- Vector dumps, embedding tables, coordinate lists. Coordinates or an embedding emitted as tensor geometry. §0. The model reads the headed English.
- ENCODE used as compress, base64, or a popular serialization. The verb is §0B.
- A second requirement under a heading that already has its first sentence.
- A build, a fire, or a stitch taken from a draft.
- A field `value` that paraphrases the source, or a field `value` that is not in the anchor.
- A field `intent` the human did not name for that field.
- A summary paragraph wearing a fancy name.
- A compression contest.
- Confetti. A lecture that is not an instruction.
- An invented expansion of the letters. The only expansion is §1.
- A plan that makes the record interoperable by replacing it with JSON, XML, YAML, protobuf, an API, a version-control scheme, or a test harness. §2.1 is the method.
- A key whose meaning lives off the page. A glossary, a comment, a schema, or a memory the file does not hold is not the bind.
- An invented binary CRC, hash, or checksum in place of the English INTEGRITY hatch.

## 4. Spec-file path

A spec file is the practice of headed English. It is markdown. It is not a fenced data packet.

Lock is four jobs. The same word does not make them one job.

1. **Bytecode.** The same source on the same path yields the same keys.
2. **File state.** The file stays draft until accepts it. A draft does not bind a later build. The encode leaves the state at draft. The encode is not Y.
3. **Packet seal.** On a data packet, `HEADER.state` is `LOCKED` and `INTEGRITY.seal` is `LOCKED`. The seal means the packet is complete for rehydration. It does not accept a spec.
4. **A heading that can be executed.** If a spec cannot state what must happen because it exists, it is not locked. It is notes. That line means the heading has no first sentence that can be executed.

Draft, then Y, then RC, then Y, then Final. Never overwrite. Roll the next number. This section does not mint a second skeleton.

### 4.1 Why a spec is encoded as

These are the semantics. A spec that cannot say them is notes.

1. **Seek, not replay.** The next session lands on a heading. It does not scrape the chat that produced the file. Compaction is ordinary when the page is true.
2. **A law is a sentence that can be executed.** A tour is not bytecode. Leftover lecture is not a heading.
3. **File state is the second job of lock.** The file stays draft until accepts it. A draft does not bind a later build. Draft, RC, and Final are states. Improving a locked file in place is not a new truth.
4. **Raw prose is the wish.
5. **A cite is a link.** Pasting another spec's novel into this file hides two heads. The next session will obey the paste and miss the lock.
6. **The encode is not Y.** It does not stamp RC or Final. It does not start a build, a clip, or a still. A metaphor is not a yes.

### 4.2 How a spec is encoded as

When says encode a spec from his raw prose, does this, in this order.

1. Name the path. This is a spec. It is not a data packet.
2. Name the family in §4.3, or ask. Do not guess a bay. Do not guess the family. If the prose does not name the family, ask.
3. Open the file with what it is, what it is not, and the token. That opening is intent for the file.
4. One heading per decision. The heading is the key and the seek address. The first sentence under it is the law, and it is the meaning of the key. That sentence is what must happen because the heading exists. The first sentence wins. The quote under it is the receipt. The quote is not a second law. A second sentence that states a second requirement is a second heading.
5. Quote prose under the heading it fed, enough that he can find his sentence. A line marked stays byte-identical.
6. Link a head that already has a file. Do not paste that file. Do not repeat its law.
7. Keep it readable aloud. Concise means no leftover lecture.
8. Leave the state at draft. The same prose, encoded again for the same family, yields the same headings. The 's sentences may tighten. The structure does not drift. A tighten that changes what must happen is a new heading, not a tighten.
9. Product nouns stay out of a protocol file. An extension lives in the bay that needs it.

```text

# <name> — <what this file is>

**What it is not:** <one sentence>
**Token:** <token>
**State:** draft
**Depends:** <linked heads. Not pasted.>

## <seek address>
<one sentence: what must happen>
```

If a spec cannot state what must happen because it exists, it is not locked. It is notes. That line means the heading has no first sentence that can be executed.

The first sentence is the law. The block quote is the receipt.

An invariant carries the mark on the heading. Frozen and open are different headings.

```text

## <invariant> — frozen
<one sentence: what must stay true>

## <still allowed to move> — open
<one sentence: what may still move>
```

A contract and a forbidden invention are two headings.

```text

## <contract>
<one sentence: what the source must match>

## <forbidden invention>
<one sentence: what a synthesis pass must not add>
```

These shapes are keys. They are not a second skeleton. The sister plate keeps the batch keys.

### 4.3 Three families

A spec encoded from prose belongs to one family. The family chooses the headings. It does not choose a second protocol.

One family per file. If the prose mixes families, split the files and link them. A video hatch does not go in a code spec. A code contract does not go in a video package.

**Project.** Building the work. Working spec, pre-plan, stamped plan. Do not mint a second build-spec token.

An invariant is one key. The heading is the key. The first sentence is what must stay true. Mark the heading frozen. A sentence that is still allowed to move is a different heading, marked open. Frozen and open do not share a heading. A decision that has collapsed is its own heading. The head the file cites is a link. The file does not paste another spec's novel.

A later emit that contradicts a frozen invariant fails. The model does not reopen it.

That sentence is the intent. The fail is the reader rule.

**Code.** The contract the living source must match. Name the realization language when this field has one. Say that other languages remain permitted when the method does not care. The source is not the spec.

A contract is one key. The first sentence is what the source must match. A forbidden invention is its own key. The first sentence of that key is what a synthesis pass must not add. A synthesis pass that adds an architecture the contract did not name fails. A hand edit that changes the source without a new heading is flagged. Flagged means writes that the edit was seen, under a heading. The edit is not a new spec.

Ask the family when the prose does not name it. Do not paste the sister plate's batch into this file.

**Video.** A bay package. The package file holds instance values only: title, roster, voice map, clip count, palette, stamps. It does not re-encode CLIP, CAMERA, token, or law. A bay hatch is `x-{BAY}` in that bay. It does not enter this baseline.

A still did not name is not fired. When the prose carries that rule, it is its own heading, and the first sentence is the law. The intent sentence alone is not the heading.

That sentence is the intent. The heading is the reader rule.

### 4.4 While encoding a spec, does not

- Paraphrase a preserved line.
- Invent a character, a clip, a database, a thread model, or a law the prose did not carry.
- Wrap the spec in JSON and call it encoded.
- Pack the spec into a data packet unless also asked for a packet.
- Treat the encode as permission to build, fire, or stitch. A later session that only reads the file does not take that permission from a draft.
- Guess the family.
- Add a second requirement under a heading that already has its law.

## 5. Data-packet path

A data packet moves data from one system to another, or it persists data so a later read can rebuild it. It is not a spec. It is not a serial.

**Preserve all exact structure and data as-is.** Every field survives. A source name stays that name. A source order stays that order. A source value is not paraphrased. The value sits on the field row as `value`. Nesting survives as headed English. A nested child is its own field row. `meta` may say which parent it sat under. The anchor keeps the original indent. Flattening the anchor is a CRC fail. It is not a new hatch.

**Encode as much as possible inside that bound.** Headed English. No lecture. No leftover chat. Readable aloud. If the next cut would drop a value or a level of structure, stop. The pack has gone too far.

**Rehydrate to the original form.** A receiver who has only the packet rebuilds the original structure and every value. `CARRIER-ANCHOR` holds that form, or a headed English rendering that still contains every value. Image means that rendering. It does not mean a summary. The receiver compares each field `value` to the anchor. A mismatch is a CRC fail. Witness head and tail are checks. They are not the data. They do not cover the middle.

**Intent embedding is optional upon request.** The default is omitted. The human requests intent, or the human does not. A request is a sentence in the turn. A vibe is not a request.

A complete baseline packet is plain text with these English headings, in this order. Omit a hatch only when this spec marks it optional.

```text
HEADER:
 id: <short unique token>
 state: LOCKED
 struct-source: <prose | table | csv | pdf-text | image-caption | unknown>
 orig-encoding: <a normal-world encoding the human named, else none>
 purpose: <one English sentence: why this packet exists>
 intent: <present | omitted>

STRUCTURED-DATA:
 - field: <name>
 value: <the source value. Not a paraphrase.>
 type: <token | number | flag | bound | enum | prose | timestamp | path>
 size: <hint: short | line | block>
 purpose: <plain English: what this field is for>
 intent: <only when the human named an instruction for this field. Otherwise omit the line.>
 struct-source: <where it was found>
 orig-encoding: <if any>
 meta: <optional English>

PACKED-TEXT:
 <non-field material, packed as far as a full rebuild still allows.
 Still readable. No leftovers. No dropped values.>

PACKED-INTENT: # only when the human requested intent this turn.
 <the intent of the whole payload, in English, compiled. Not a slogan.>

CARRIER-ANCHOR:
 <the original form, or a headed English rendering that still contains every
 value. Image means that rendering. Not a sample. Not a summary.>

SHOW-STATE: # optional. Use when a story or a session continues.
 <last beat, enough continuity for the next hop. Not a memoir.>

INTEGRITY:
 fields: <count of STRUCTURED-DATA fields>
 anchor-chars: <character count of CARRIER-ANCHOR>
 orbital: 0
 witness-head: <first ~24 characters of the original source>
 witness-tail: <last ~24 characters of the original source>
 seal: LOCKED
```

`ORBITAL-INTENT` is not in this grammar. A baseline receiver that sees it preserves the hatch as packed text and still validates the baseline hatches. It does not treat the hatch as law. The bay that defines it does.

`from` does not invent mill or counter seats. Those names belong to a bay that has them.

### 5.1 Intent on a data packet is upon request

The 2026-10-15 20:04 line said payload intent depends on the use. Does not choose. The human requests intent, or the packet carries none.

| This turn | `HEADER.intent` | Field `intent` | `PACKED-INTENT` |
|---|---|---|---|
| The human did not request intent | `omitted` | Omit | Omit |
| The human requested intent | `present` | Only on a field whose instruction he named. The seat does not mint one. | Required. One compiled sentence. Not a slogan. |
| He requested intent, and did not name an instruction for a field | `present` | Omit on that field | Still required for the payload |

`purpose` names what the field is, in the source's own words. It is not an instruction. Do not write a purpose the source did not support. A bare column name stays a bare column name.

Nothing in the source is dropped. Material that is not a field rides in `PACKED-TEXT` and in `CARRIER-ANCHOR`. When intent is present, it is compiled into the heading. It is not a second document.

### 5.2 Field rules

- `field` names are readable English tokens.
- `value` is the source value. It is not a paraphrase. Omitting the line drops the value. An empty source value is written `value:` with nothing after it. A receiver compares each `value` to `CARRIER-ANCHOR`. The characters of `value` occur in the anchor as the source wrote them. A mismatch is a CRC fail. A paraphrased `purpose` is not that check.
- `intent` on a field exists only for an instruction the human named for that field. Otherwise omit the line. The seat does not mint one.
- `type` is a word. It is not a JSON Schema reference.
- Counts in `INTEGRITY` are decimal integers. A receiver recounts. No hash algorithm.
- Baseline `orbital` is `0`. An extension may define hops. This file does not.

### 5.3 Example — intent requested

This packet exists because the human requested intent on this turn. Without that request, use §5.4. He named the instruction for `claim_id` and for `status`. The page is only a number, so that row omits `intent`. The seat does not mint one. Each `value` occurs in the anchor.

```text
 id: claim-A-19
 from: HUMAN
 struct-source: prose
 orig-encoding: none
 purpose: bind this claim to the exception on page 188
 intent: present
 - field: claim_id
 value: A-19
 type: token
 size: short
 purpose: identifies the claim in the source narrative
 intent: join this row to the exception on page 188
 - field: status
 value: pending
 type: enum
 purpose: lifecycle of the claim
 intent: treat as pending until a human locks otherwise
 - field: page
 value: 188
 type: number
 purpose: the page number as written
 Claim A-19 is pending.
PACKED-INTENT:
 Join A-19 to page 188. Do not close the claim.
 Claim A-19 is pending. See the exception on page 188.
 fields: 3
 anchor-chars: 53
 witness-head: Claim A-19 is pending
 witness-tail: exception on page 188.
```

Wrong: `{"claim_id":"A-19","status":"pending"}`.
Wrong: a shorter paraphrase that drops page 188.

### 5.4 Example — intent not requested

This is the default for transfer and for persistence. `HEADER.intent` is `omitted`. There is no `PACKED-INTENT`. Field rows have purpose, `value`, and no intent. The value is the source value. The human named no per-field instruction, so the seat writes none.

```text
 id: claim-A-19-record
 purpose: hold the claim record
 intent: omitted
 purpose: the claim token as written
 fields: 1
 anchor-chars: 22
 witness-tail: Claim A-19 is pending.
```

### 5.5 Example — a nest, and a row

Intent is omitted. These packets show the value on the row. §5.3 is the intent example. Do not copy intent into these.

A nested child is its own field row. `meta` names the parent. The anchor keeps the indent. The character count is the anchor text. The fence indent is not counted. A newline between anchor lines is counted. A trailing newline after the last line is not.

```text
 id: claim-nested
 purpose: hold the nested claim
 - field: id
 purpose: the claim token under claim
 meta: nested under claim
 purpose: the page under claim
 claim
 id: A-19
 page: 188
 fields: 2
 anchor-chars: 28
 witness-head: claim
 witness-tail: page: 188
```

A table row uses the same field row. `struct-source` says `csv`. The anchor is the source text. The values `A-19` and `pending` occur in that text.

```text
 id: claim-row
 struct-source: csv
 purpose: hold one claim row
 purpose: the claim token in the row
 purpose: the status cell in the row
 claim_id,status
 A-19,pending
 witness-head: claim_id,status
 witness-tail: A-19,pending
```

## 6. Story, roles, syntax

When the payload is a story, a roster, or a scene:

- Character names and role lines are bytecode. Preserve them. Do not rename a character to "Agent 1."
- A local token such as is a semantic key. Bind it once. Every later use is that same row. Do not invent a second one.
- Dialog is a bound: speaker, exact line, only that mouth. A specified line is spoken. A mouth that is not the speaker does not speak.
- Do not improve dialog. The string is exact.

The key is in the paste. It is not in a hidden memory.

## 7. English CRC

Not a cryptographic checksum.

1. Count STRUCTURED-DATA rows. Compare to `INTEGRITY.fields`.
2. Count characters in `CARRIER-ANCHOR` (Unicode code points). The count is the anchor text. Do not count the fence indent. Count a newline between lines. Do not count a trailing newline after the last line. Compare to `anchor-chars`.
3. Compare `orbital` to the hops an extension defined. Baseline is `0`.
4. Check `witness-head` and `witness-tail` against the original when the original is present. About 24 characters at each end do not cover the middle.
5. Compare each field `value` to the anchor. The characters of `value` occur in the anchor as the source wrote them. A mismatch is a CRC fail.
6. A required baseline hatch missing, or `HEADER.intent: present` with no `PACKED-INTENT`, or `HEADER.intent: omitted` with a `PACKED-INTENT` still attached, is a CRC fail.
7. A data packet that drops a source field, renames a source key, reorders a source sequence, or paraphrases a value is a CRC fail. A data packet that embeds intent when the human did not request it is a CRC fail. A field `intent` line the human did not name for that field is a CRC fail.
8. On fail, do not execute. Ask the human to re-emit. A repair ticket may be headed English. The bay that uses tickets defines the ticket. This baseline does not.

## 8. How a model behaves

When the human says encode,, or pastes this spec, name the path before writing.

When asked what stands for, what it is, or what it is used for, answer from §1 and §2. The acronym stands for English-Native Intermediate Representation. EN is English-Native. IR is Intermediate Representation. Do not invent a second expansion. It is used for spec files and for data packets, as §0 says.

When asked what ENCODE means, answer from §0B. ENCODE compiles the named source onto one path. Spec path when he says spec or raw prose for a spec. Packet path when he says transfer, persist, or hand this to another system. If he did not name the path, ask. Do not guess. Do not compress. Do not base64-encode. Do not swap the record for a popular serialization.

When asked how achieves interoperability, how to encode it, or how a key carries semantic meaning or intent, answer from §2.1 and from the path in §4 or §5. The meaning of a key is the sentence bound to it on the page. The first sentence wins. The quote is the receipt. Do not invent JSON, XML, YAML, an API, a version-control scheme, or a test harness as the method.

When asked how a spec locks an invariant, answer from §4.3. An invariant is one key. The first sentence is what must stay true. A frozen heading and an open heading are not the same heading. A later emit that contradicts a frozen invariant fails. The model does not reopen it.

When asked what an operating contract is, answer from §4.3. A contract is one key in a code-family spec. The first sentence is what the source must match. A forbidden invention is its own key. A synthesis pass that adds an architecture the contract did not name fails. A hand edit that changes the source without a new heading is flagged and is not a new spec. If the prose does not name the family, ask.

When asked how two systems interchange a packet, answer from §5 and §7. Every source value is on the field row as `value`. The anchor is the original form. The receiver compares each `value` to the anchor. A mismatch is a CRC fail. A field `intent` line exists only for an instruction the human named for that field. Otherwise omit it. `PACKED-INTENT` is the one compiled sentence when intent was requested. The seat does not mint a per-field instruction.

When asked what tensor geometry means, answer from §0. The model reads the headed English. Tensor geometry is that reading. It is not an embedding and it is not a coordinate list.

When asked whether an encoded spec may be built, the answer is no while the state is draft. A draft does not bind a later build. §4. §0F.

1. Spec, or encode a spec from raw prose: follow §4. Serial form. The family is project, code, or video. If the prose does not name the family, ask. Do not guess it.
2. Transfer, persist, packet, or hand this to another system: follow §5. Preserve every structure and every value. Pack only as far as rehydration allows. Embed intent only if he requested it this turn.
3. If he did not name the path, ask. Do not guess.
4. Emit headed English. Keep it human-readable. Do not wrap either path in JSON.
5. Do not add files the human did not name.
6. The same input on the same path yields the same headings and the same field names. On a spec, the 's sentences may tighten. A tighten that changes what must happen is a new heading. On a data packet, values do not tighten.
7. Preserve roles that are in the paste.
8. A data packet writes `INTEGRITY`. A receiver recounts, and compares each field `value` to the anchor. A mismatch is not trusted.
9. Do not add `ORBITAL-INTENT` unless the human named that extension.

## 9. Extensions

You may add an optional hatch in a later version line, documented in the bay that needs it. You may add a `type` word if one English sentence defines it.

You may not:

- Replace English with JSON, XML, or protobuf and keep the name.
- Nest hatches inside JSON.
- Require a tool or a binary compiler.
- Drop human readability.
- Leak a bay hatch into the baseline.

A baseline receiver that sees an unknown hatch preserves it and still validates the baseline hatches.

| Extension | Where | What it adds |
|---|---|---|
| `-KHAZAD` | the Khazad video package | Tokens and clip hatches for that bay only. |

Happens only when 1.x cannot hold the law, and only when locks it.

It defines ENCODE, splits lock into four jobs, makes an invariant a key, makes a contract a key, makes the first sentence win, adds a conforming spec reader, puts `value` on the field row, and limits field intent to an instruction the human named. Tensor geometry is the reading of the headed English. It does not mint a new version line.

The 2026-10-15 amendments change 1.0 in place. Orbital intent left the baseline. Payload intent on a data packet is optional upon request (20:33), which refines the 20:04 line that left the choice to the use. Spec-file intent is the encoding seat's compile of the headings, and it is not a packet hatch. The standard frame (20:43) is the same amendment line. It states scope, terms, conformance, and immutability. It does not mint a new version. Old text that requires intent on every field, or that lists `ORBITAL-INTENT` as a baseline hatch, is the 2026-09-26 snapshot.

## 10. Ordinary files

Markdown is a carrier. The headings are the grammar.

Downstream systems may serialize to JSON after is locked. That JSON is not.

This file is sufficient for the baseline. Do not fetch another product's tree to complete a packet. Do not invent seats the human did not name.

It is not the baseline. If the two differ, this file wins.

## 11. Compile checklist

Name the path. Then use that list. Passing a box does not amend this file. A local edit to this file is not how a box is passed.

**Spec file**

- [ ] The file says what it is, what it is not, and the token
- [ ] The shape is a serial. State is draft until an explicit Y
- [ ] One heading per decision. The first sentence is the law. The quote is the receipt. A second requirement is a second heading
- [ ] An invariant is one key, marked frozen or open on its own heading
- [ ] A contract is one key. A forbidden invention is its own key
- [ ] The state is draft. A draft does not bind a later build
- [ ] A spec from raw prose names its family. Intent sentences are A preserved line is byte-identical. If the prose does not name the family, the seat asked
- [ ] Heads are linked, not pasted
- [ ] A human can read it aloud

**Data packet**

- [ ] Every source field, name, order, and value survived
- [ ] Each field row carries `value`, the source value, not a paraphrase
- [ ] Each `value` occurs in `CARRIER-ANCHOR`. A mismatch is a CRC fail
- [ ] A field `intent` line exists only for an instruction the human named for that field
- [ ] `CARRIER-ANCHOR` is enough to rebuild the original form. It is not a sample and it is not a summary
- [ ] The pack stopped before a value or a level of structure would drop
- [ ] `HEADER.intent` is `omitted` unless the human requested intent this turn
- [ ] If he requested it, `PACKED-INTENT` is one compiled sentence and is not a slogan
- [ ] If he did not, `PACKED-INTENT` is absent and no field carries an intent line
- [ ] `INTEGRITY.orbital` is `0` unless a named extension defines hops
- [ ] `INTEGRITY` counts match a recount
- [ ] No `ORBITAL-INTENT` hatch unless that extension was named
- [ ] No JSON or XML wrapper
- [ ] A human can read the packet aloud

**Name.** Asked what stands for, the answer is English-Native Intermediate Representation. Any other expansion fails the name.

**Interoperability.** Asked how another system uses the record, the answer is §2.1. The file is the method. A plan that swaps the record for JSON, XML, YAML, an API, or a test suite fails. A key that does not keep the source name fails. A meaning that is not the sentence under the key fails.

**ENCODE.** Asked what ENCODE means, the answer is §0B. Compress, base64, and a popular serialization fail.

**Lock.** Asked what lock means, the answer is the four jobs in §4. A draft treated as accepted fails. A packet seal treated as a spec acceptance fails.

**Invariant.** Asked how a spec locks an invariant, the answer is one key, marked frozen. An open item on that same heading fails.

**Contract.** Asked what an operating contract is, the answer is one key in a code-family spec. An essay with that title and no first sentence fails.

**Spec reader.** A reader that builds from a draft, or that contradicts a frozen invariant, fails §0F.

**Tensor.** An embedding or a coordinate list offered as tensor geometry fails §0.

If a box fails, do not call it. Say which path, and what failed, in English.

## 12. Sweep (2026-10-15)

These pointed at the export handshake. The live edge is this file.

| Was | Now |
|---|---|
| As the law | Pointer. Baseline is this file. |
| As commentary that deferred to the handshake | Pointer. |
| ` as the directory | Moved 2026-10-15 20:28 to ` The export path is a stub. |
| | Pointer to this file. |
| | Opens this file. |
| | Opens this file. Not the panel. Do not take `NOW` from it. |
| Mill and counter blobs inlined 2026-10-13 | Snapshots. They do not win. |
| Seed kit prod 001, cut 2026-10-15 19:36 | Frozen. Its handshake copy is that cut. It is not this head. |
| One path, the packet, as the whole encoding | Two paths as of 2026-10-15 20:33. Spec file is §4. Data packet is §5. |
| Shop speech as the only way to read the baseline | Standard frame, 2026-10-15 20:43. Scope, terms, requirements language, conformance, immutability. A local rewrite is not A friend accepts §0F. |
| An invented expansion of the letters | 2026-10-20 08:55. §1 is the only expansion. English-Native Intermediate Representation. EN is English-Native. IR is Intermediate Representation. §8 answers the question from §1 and §2. |
| Interoperability as a second format | 2026-10-20 09:24. §2.1. The file is the method. A key keeps the source name. The sentence under the key is the meaning. Intent on a spec is that sentence. Intent on a packet is upon request. JSON, XML, an API, and a test harness are not the method. |
| ENCODE undefined, lock as one word, invariant and contract as bullets, a quote that can win, no spec reader, a field row with no value, invented field intent, tensor geometry read as coordinates | 2026-10-20 10:14. ENCODE is a term. Lock is four jobs. An invariant is a key. A contract is a key. The first sentence wins. A conforming spec reader is §0F. A field row carries `value`, compared to the anchor. Field intent is named or omitted. Tensor geometry is the reading of the headed English. |
| Spec as the only reading for a new engineer | This file remains the law. |

Closed brief, not the protocol:. `HEADS.ENIR` still names that closed brief. `HEADS.ENIR_SPEC` names this file.

# Source prose

- **Serial:** draft.001

- Generated source carries Knuth prose, and each tag names the spec it serves.

- **State:** OPEN. This page is the law's spec.

- This page binds a tag to a spec file. It does not open a second catalog.

- **What it is:** The class prose is the program a person reads. The tag carries an existing key and the spec file that key lives in. A type with no heading gets a heading on this page.

- **What it is not:** A change to how source is generated. A new key namespace. A backfill of every private line in the historical tree. A rewrite of a filed page.

## 1. The prose

A generated type opens with prose in a pretty block. A blank line ends a block.

The prose names what the type is, what it does at each step a reader needs, and what it leaves alone. A programmer can read that prose and follow the type.

The prose does not change the generated statements. Still places a PlantUML picture where a machine, a dependency, or a branch is easier to see than a paragraph. An inner note still waits until the step is hard.

A private line with no spec does not grow a novel on the turn that files the law. A later turn that touches the type brings the prose up to this page.

## 2. The tag

The line stays inside the locked grammar.

```
```

The key stays in the family section 10.3 already locks: `L<n>`, `G-<nn>`, `S<n>-<id>`, `Q-S<n>-<n>`, `CF-<nn>`, or a syntax token such as `STICKY-CMD`.

The spec file is a trailing word. It is the file the key already lives in, or this page when the heading is created here. It is not a new namespace. `@spec:` stays refused.

A page may sit as a further trailing hint, `:<stem>`. A defect id may sit as `fix:BF-NNN`. That hint is not a key. Still keeps the id in.

Where the type has no heading, this page holds the heading. The tag names this file and that heading.

## 3. The picture

```
@startuml
skinparam monochrome true
package "generated source" {
 class Type
}
package "spec shelf" {
 artifact Key
 artifact Page
Type --> Page: trailing spec file
@enduml
```

The Rust mux module and the session module carry the same kind of picture beside their prose.

## 4. Anchors

### Incrementor

Minus sits on the left. The value sits in the middle. Plus sits on the right. A typed value waits 800 ms. Plus and minus apply at once. No refactor of an incrementor, for any reason, without a triple check. The plan that seated the type is the stage-1 tweaks plan.
**semantic-key:** L40

### SoftWrapArea

A text area that wraps at a character column while soft wrap is on. Word wrap starts on at 80 columns. Soft wrap off leaves the text on one line.
**semantic-key:** L40

### PoolChat

The server-first chat contract for one local grok agent child per SSH user. The contract has no SSH types and no Swing types.
**semantic-key:** L40

### PoolChatServer

One grok agent child per SSH user. At most three children. Stdout and stderr ride the pool lane. The prompt blocks the caller.
**semantic-key:** L40

### Chrome

The Rust window. The folded canvas control line lifts so C clears the BB1 button bar by at least 2px. The module note names the Rust client draft.

### SshClientTheme

The Java theme. A main menu hover paints the lighter grey tab.
**semantic-key:** L40

### SshClientTheme

The canvas cell scroll under the Canvas tab wears the quiet bevel. The lip is 0x0a0a0a. The shadow is black. The class prose names the dark Metal seat for the SSH client and the Aetheris window. Color values come from SshClientProperties.

### SshConnectionPanel

The control bar pane inside the folding panel. The bottom pad clears the buttons from the joint by at least 2px. The panel holds host, port, user, password, and the session mode.

### RouxEngine

The process-wide Roux memory engine. One region holds the hot page, the RAM index, and the identity map. A disk region publishes through the pack. A RAM region stays mapped.
**semantic-key:** L40

### RouxProperties

The bound `roux.*` settings. Page bytes default to 1 MiB. The resident cap defaults to 2. Durability normalizes to hot-only, commit, or append.
**semantic-key:** L40

### RouxProperties.Spill

Spill-to-file settings. Spill starts on. The directory is the user home under `.casserole/roux`.
**semantic-key:** L40

### RouxProperties.Crypto

The encryption knob on the Roux settings. The knob defaults to false. V1 stays off.
**semantic-key:** L40

### RouxProperties.Session

Persist, restore, and exclusive connect for a disk-backed region. Each knob defaults to true.
**semantic-key:** L40

### MemoryInspector

The read seat of the memory API. A caller reads telemetry, the live region list, one descriptor, a raw range, or a health report. The frames stay put.
**semantic-key:** L40

### MemoryLayoutManager

Region allocation, growth, and unmap. Allocate opens a region. Grow rotates the hot page.
**semantic-key:** L40

### MemoryStateController

Append, clear, and the operator locks. Append stores one MSG frame. Clear drops the frames and keeps the region. Chat uses the region mutex.
**semantic-key:** L40

### MemoryLifecycleManager

Snapshots and shutdown. A snapshot, a restore, and a heap dump return unsupported in v1. Shutdown unmaps every region.
**semantic-key:** L40

### CryptoSink

The encryption stub. An enabled knob is refused. A disabled knob lets the rotate continue.
**semantic-key:** L40

### FrameHeader

The fixed 56-byte frame header before the TOON payload. The header stores length, type, role, seq, time, tokens, the CRC, and the two identity longs.
**semantic-key:** L40

### RouxId

Message identity as two primitive longs. Seq locates the frame. These two longs stay when a page moves.
**semantic-key:** L40

### RouxIdGenerator

The process-wide generator for the two-long identity. Each call advances a millisecond and a counter. Seq is assigned later, on the index row.
**semantic-key:** L40

### MonotonicIndex

The dense off-heap index. Message id starts at 1. The slot is id minus 1. Each row stores seq and the RouxId together. The index stays in RAM.
**semantic-key:** L40

### RouxLayout

Little-endian value layouts for pages and index rows. Byte, short, int, and long reads share these layouts.
**semantic-key:** L40

### PinTable

Per-page pin counts so a spill waits for an in-flight read. Pin raises the count. A rotate waits until the hot page count is zero, or until the wait times out.
**semantic-key:** L40

### AllocationResult

The result of allocate or grow. The caller reads success, the status code, the region descriptor, and the error text.
**semantic-key:** L40

### AllocationStatus

Status codes for a region or page allocation. Success is 0. The other codes name an address-space miss, fragmentation, or a frame that does not fit.
**semantic-key:** L40

### BitAddress

A view of a page: region, page, and byte offset. The payload bytes stay with the page.
**semantic-key:** L40

### DecodedFrame

A heap decode of one MSG frame at the contract edge. The frame carries seq, role, time, the stored token count, and the text.
**semantic-key:** L40

### FrameType

The frame type byte. Writers emit MSG. CONT is byte 5 and stays reserved.
**semantic-key:** L40

### HealthReport

An integrity check for one region. The report names the region, whether it is usable, and a detail string.
**semantic-key:** L40

### IndexEntry

One monotonic index row, copied onto the heap for a window walk. The off-heap index remains the store.
**semantic-key:** L40

### LockLevel

Operator lock levels. The codes name an exclusive write, a read-only snapshot, and a drain. V1 chat takes the region mutex.
**semantic-key:** L40

### LockToken

An operator lock handle. The token names the lock, the region, the level, and the time it was taken.
**semantic-key:** L40

### MemoryAddress

A byte range in a page. The address names the region, the page, the offset, and the length.
**semantic-key:** L40

### MemoryTelemetry

A process-wide counter snapshot at the inspect edge. The object freezes pages, spills, frames, index rows, and byte totals.
**semantic-key:** L40

### MessageInspect

An inspect view of one stored message. The view carries seq, the RouxId, residency, the CRC check, and the text. Residency is the page state before a later page-in.
**semantic-key:** L40

### OperationResult

The result of a mutating engine call. The caller reads success, the status code, and the message.
**semantic-key:** L40

### OperationStatus

Status codes for a mutating engine operation. Success is 0. The other codes name a pin, a busy resident cap, a bad segment, a lock wait, a stale view, an unsupported call, or a missing region.
**semantic-key:** L40

### PageFlags

Bit flags on a page header. Hot, spilled, retired, and dirty-suspect each occupy one bit. Init writes the hot flag on a new page.
**semantic-key:** L40

### RegionDescriptor

A snapshot of a region's layout at the inspect edge. The descriptor names the hot page, the capacities, the used bytes, the message count, and whether the region is pinned.
**semantic-key:** L40

### Role

The message role stored as a single byte on the frame and on the index. System is 1, user is 2, assistant is 3. Tool roles follow.
**semantic-key:** L40

### SnapshotHandle

A snapshot id. The handle keeps an id, a label, and a time. V1 does not mint one.
**semantic-key:** L40

### SnapshotOptions

Snapshot options. The options name the regions, whether writes freeze, and a storage path. V1 does not run a capture.
**semantic-key:** L40

### Page

One page. A hot page is a live arena. A spilled page keeps a file path. A resident page is a spilled page mapped back into RAM.
**semantic-key:** L40

### PageHeader

Accessors for the 64-byte page header. Init writes the magic ROUX, version 2, and the hot flag.
**semantic-key:** L40

### OpenResult

The outcome of an open. New means an empty region. Restored means the pack was scanned. Refused means the open stopped.
**semantic-key:** L40

### RegionManifest

The fixed 112-byte sibling of `pages.bin`. A close with persist writes this file and leaves the directory.
**semantic-key:** L40

### RegionOwner

A path-safe folder name for a disk region. A valid owner is letters, digits, dot, underscore, or hyphen.
**semantic-key:** L40

### RegionPack

One random-access `pages.bin` per region. Publish writes the used bytes, pads to the align, forces the channel, and leaves the page mapped.
**semantic-key:** L40

### SpillWriter

A writer of one page file, `{dir}/{regionId}/{pageId}.pg`. The seated pack is `pages.bin` on RegionPack. This writer still emits one file per page id when a caller uses it.
**semantic-key:** L40

### RouxChatMemory

The Spring chat-memory adapter over the engine. Full history is stored. A window is applied at read. Tag key is. `openRam` keeps on the stages file.
**semantic-key:** L40

### RouxMessageMapper

The static map between a Spring message and a Roux role byte. System, user, assistant, and tool map. Any other type becomes user.
**semantic-key:** L40

### RouxCounters

Process counters for the engine. Each counter is an atomic long. A snapshot copies them into telemetry.
**semantic-key:** L40

### TokenCounter

The token estimate stored on write. The count is at least 1. The v1 counter is the heuristic.
**semantic-key:** L40

### HeuristicTokenCounter

The v1 token heuristic, `max(1, (length + 3) / 4)`.
**semantic-key:** L40

### ToonCodec

The static TOON encode and decode for one MSG payload. The header is `m[1]{r,ts,tok,x}:` and one row follows.
**semantic-key:** L40

### RouxWindowSpillTest

The declared Roux 1.1 window battery. The head rolls. The tail stays on the hot page. A spilled head still reads.
**semantic-key:** L40

### SshServer

The Casserole SSH listener. Apache MINA SSHD accepts the password, binds the port, and loads the host key from the casserole settings. Each connection asks ChatShellFactory for a shell. The listen loop runs on the SSH_IO lane. The bean does not start itself. InitRunner calls start.
**semantic-key:** L40

### ChatShell

The VT100 shell for one SSH connection. It owns the line buffer and the escape mode. Command echo is green. Enter goes to the command processor or the LLM worker. The shell does not name the commands.
**semantic-key:** L40

### ChatShellFactory

One ChatShell per SSH connection. That shell gets its own session and its own command processor. A refused login does not take a Roux region.
**semantic-key:** L40

### Ansi

VT100 SGR bytes for command echo, errors, prompts, and key-value status. The sequences do not decide the mode.
**semantic-key:** L40

### InputPage

One walk of the unsent VT100 page. The paint and the cursor read this same result.
**semantic-key:** L40

### PagePaint

Bytes that take the screen from one input page to the next. A glyph edit rewrites the damaged tail.
**semantic-key:** L40

### RejectShell

One red line, then hang up. A refused login does not open a Roux region.
**semantic-key:** L40

### SshIoTrace

INFO lines for every connect and disconnect on the Casserole SSH listener. The password never appears. peer-mac stays absent.
**semantic-key:** L40

### AmwpAwareShell

The mux in front of ChatShell. Init-or-raw. OpenSSH stays a raw PTY.
**semantic-key:** L40

### AmwpFacade

The server AMWP Scope A facade. Bantha Roux is RAM-only. The semaphore of 3 is here. LLM_EXEC stays 4. ChatShell does not take that semaphore.
**semantic-key:** L40

### MinimalSshServer

A local MINA listener on port 2222. It is not the Spring bean. The host key is hostkey.ser. The Casserole listener is SshServer.
**semantic-key:** L40

### SshServerTest

A local MINA listener that pumps bytes between the SSH streams and a PTY. It is not the Spring bean.
**semantic-key:** L40

### SshClientApp

The dark JediTerm SSH client window. The frame owns the folding panel, the connection bar, the terminal sessions, and the Aetheris notebook. Connect and disconnect go through the session controller. The window stays on the Metal theme.
**semantic-key:** L40

### SshSessionController

Connect, disconnect, and reconnect off the event thread. The worker opens the MINA client session and the shell channel. A user disconnect does not reconnect.
**semantic-key:** L40

### SshTtyConnector

The JediTerm connector on a MINA shell channel. JediTerm sees only Scope T octets.
**semantic-key:** L40

### SshTerminalPanel

One JediTerm host. A new SSH session builds a new widget. Disconnect leaves that widget so the buffer stays on the screen.
**semantic-key:** L40

### TerminalSession

One SSH shell and one JediTerm canvas. The frame keeps a tree of these sessions.
**semantic-key:** L40

### SshSessionMode

The remote PTY request. VT100 asks for a vt100 PTY and a short mode map. Standard PTY asks for xterm-256color and the POSIX mode map. JediTerm stays the local emulator.
**semantic-key:** L40

### SshConnectionState

Disconnected has no session. Connecting means the worker is in the handshake. Connected means the shell channel is open and wired to JediTerm.
**semantic-key:** L40

### SshClientProperties

The per-user SSH client file is ~/.casserole/ssh-client.yaml. The first load may import ~/.ssh-client.properties once. The theme reads its colors from this file.
**semantic-key:** L40

### AppleScrollBar

Overlay scroll bars, the same floating bars the Rust window paints. install arms both directions. armStrip is for a strip the host lays out itself.
**semantic-key:** L40

### ComposerHeatbar

The composer beat. A 5 pixel track, a 2 by 9 needle, and posts at 70 and 90. The keystroke path stays the heuristic.
**semantic-key:** L40

### ConsolePaste

The newline rule and the cap shared by the paste editor, Send file, and To terminal. A bracketed paste keeps LF. Nothing here adds Enter.
**semantic-key:** L40

### DarkJediTermSettings

JediTerm settings for the dark canvas. The font size can change without building a new widget. Scrollback stays at 10000 lines.
**semantic-key:** L40

### DarkJediTermWidget

The JediTerm widget with a public font refresh. Upstream reinit is protected.
**semantic-key:** L40

### FoldingPanel

The v toggle. Open paints v. Folded paints ^. The control fields sit under the menu while the bar is open.
**semantic-key:** L40

### OllamaTokenDip

One settled reading from the configured Ollama host. The call is POST /api/generate. A miss leaves the heuristic in place. The paint path does not call this type.
**semantic-key:** L40

### PasteEditorDialog

The modal editor for a clipboard or file paste. Ctrl+Enter or OK inserts the blob. Enter inserts a newline.
**semantic-key:** L40

### ToolsThinking

The Tools menu on the SSH client window. Show Thinking reveals the pane of the plate in front. The other plate stays. The log stays.
**semantic-key:** L40

### Vt100StatusLine

The VT100 thin status line. The link word leads. The console mode is the wire word. The Aetheris strip keeps AMWP and raw.
**semantic-key:** L40

### Vt100TokenWindow

The VT100 token window. The used side is the Roux chat for that user. A stored count is the count Roux kept.
**semantic-key:** L40

### RustSshSession

The SSH session. russh runs on the read thread. The UI thread uses try_send. Tests do not call Session::start.

### TermConnector

Bytes in, bytes out, and a size. The host owns the socket. russh stays behind the host impl.

### RustSshDoor

The login door. The file is ~/.casserole/ssh-client.yaml. Unknown keys stay. A password is written only when Remember password is on.

### RustAmwp

AMWP frames for the VT100 client. Scope T payloads are painted. A failed parse after the mux is up marks the session broken.

### RustHeatbar

The composer beat on the Rust client. A 5 pixel track, a 2 by 9 needle, and the amber and red posts. The needle stays the heuristic.
**semantic-key:** L40

### InputPageTest

Row math for the VT100 input page. No PTY.
**semantic-key:** L40

### PagePaintTest

Damage bytes for one page edit. No PTY.
**semantic-key:** L40

### SoftReturnTest

Shift-Enter is a lone LF. CR still submits. A LF after CR stays swallowed. No PTY.
**semantic-key:** L40

### ShellMoodFrameTest

The console mood frame. A model frame stays a model frame. No PTY.
**semantic-key:** L40

### ConsoleGrokThinkTest

The Grok think switch on an SSH turn. No PTY.
**semantic-key:** L40

### ConsoleThoughtSinkTest

Which console family attaches the nil-cell think sink. No mux.
**semantic-key:** L40

### BlankHostSeatTest

A blank prompt drops the vestige seat. No pool turn.
**semantic-key:** L40

### Vt100TokenWindowTest

The VT100 token window. No window and no host.
**semantic-key:** L40

### Vt100StatusLineTest

The VT100 status line. No window and no PTY.
**semantic-key:** L40

### ToolsThinkingTest

Tools and Show Thinking. No window and no pane.
**semantic-key:** L40

### ConsoleCopyChordTest

The copy chord and the interrupt stay apart. No window and no PTY.
**semantic-key:** L40

### MoodDoor

The transcript door for one mood. A seat writes one triple and takes every prior triple out. A stored pair that still uses the old activate sentence leaves too. A clear leaves the turns. Adopt reads the constructed mood that is already stored.
**semantic-key:** L40

### MoodDesk

The one mood desk. Canvas, group, and pipe pushes ask this desk for the system lines. An SSH session asks this desk to rewrite the Roux transcript. A connect asks this desk whether the pane still needs a shell frame. The footer switch gates the canvas push and the shell. The SSH checkbox only makes the shell available. The switch off clears a checked SSH seat. The constructed mood sits between the opener and the closer.
**semantic-key:** L40

### RustMoodDesk

The Rust mood desk. [mood.rs](../../../rust/aetheris/aetheris-client/src/mood.rs) holds the invitation, the pin, and the debug lines. It does not paint. The glass session stays in the chrome. Canvas, group, and pipe splice the invitation onto one user turn. The empty host seat stays the line the server replaces. The shell frame stays shell_mood and the line. The pin file is aetheris-mood.yaml. A missing pin is the head. Pending is consumed by the next send.
**semantic-key:** L40

## 5. Where the known keys already live

A Java tag that realizes one of those keys names that file.

Mux names and on the stages file.

`T5` names the plan. It stays a trailing hint from that plan. It is not a new key family.

## 6. This cut

No side move. The generated statements stay. The tags name the spec.

## 7. One spec, one path

A code-doc line that names one spec many times writes the keys as one space-delimited set. One opens the set. The spec path follows the set once. The spec path is not repeated. A key that names a different spec stays its own set.

The public keys are `PK-<nnnn>`. Spec word is the path under `

The grammar block in section 2 stays. This section adds the set. A code-doc line that names one spec many times writes those public keys as one space-delimited set, then that spec once. One opens the set. The spec path is not repeated. A key that names a different spec stays its own set. Section 9 carries the shape. Section 21 records it. The code doc page carries the same shape. This filing does not rewrite a source tag. It places no mark.

## 8. Each tag sentence is its own comment line

A comment that carries more than one tag sentence gives each sentence its own comment line. The break is the newline after the spec path. Next line repeats the comment mark that line already uses, then the next. Section 7 stays the set. One still opens the set. The spec path still follows the set once.

Door section 156 is the same rule. The break is on the copied source. This filing does not rewrite a source tag. It places no mark.

# Spec detail

- A specification is never limited by size or by a detail cap.

- **State:** OPEN. This page is the law spec.

- This page is that spec for the detail rule.

- **What it is:** The record that a specification carries the detail. Disk size is not a reason to thin it.

- **What it is not:** A cap on a paper. A cap on code. A rewrite of a filed paper. A reason to drop the live spine before reviews the large pages.

## 1. The rule

A specification is never limited by size or by a detail cap.

Detail is the requirement. Disk size is not a reason to thin a specification. A later expansion writes every contract the program has.

## 3. The check

The check does not retarget `NOW.ACTION` and does not change the phase.
