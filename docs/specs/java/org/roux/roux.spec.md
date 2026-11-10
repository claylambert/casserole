# org.roux

Concept file. It states what this program is.

**Depends:** [java/org/aetheris/vault/vault.spec.md](../aetheris/vault/vault.spec.md), [java/org/aetheris/wire/wire.spec.md](../aetheris/wire/wire.spec.md), [java/org/casserole/chat/chat.spec.md](../casserole/chat/chat.spec.md), [java/org/casserole/chat/cmd/cmd.spec.md](../casserole/chat/cmd/cmd.spec.md), [java/org/roux/api/api.spec.md](api/api.spec.md), [java/org/roux/crypto/crypto.spec.md](crypto/crypto.spec.md), [java/org/roux/frame/frame.spec.md](frame/frame.spec.md), [java/org/roux/id/id.spec.md](id/id.spec.md), [java/org/roux/index/index.spec.md](index/index.spec.md), [java/org/roux/layout/layout.spec.md](layout/layout.spec.md), [java/org/roux/lock/lock.spec.md](lock/lock.spec.md), [java/org/roux/model/model.spec.md](model/model.spec.md), [java/org/roux/page/page.spec.md](page/page.spec.md), [java/org/roux/page/Page.spec.md](page/page.spec.md), [java/org/roux/session/session.spec.md](session/session.spec.md), [java/org/roux/spill/spill.spec.md](spill/spill.spec.md), [java/org/roux/spring/spring.spec.md](spring/spring.spec.md), [java/org/roux/tel/tel.spec.md](tel/tel.spec.md), [java/org/roux/token/token.spec.md](token/token.spec.md), [java/org/roux/toon/toon.spec.md](toon/toon.spec.md)

## The head roll and the tail spill

A formal test is filed only when it is declared. This rule declares the Roux 1.1 window battery. The head rolls. The tail stays on the hot page.

`RouxWindowSpillTest` exercises the head roll and the tail spill. A small page forces the spill. The hot page is the tail. A spilled head still reads. The resident cap holds. The window keeps the tail and rolls the head. A turn atom stays together. A pinned system stays. With spill off, the window stops at the cap. No load test is part of this battery.

The test type is package-private. The Swing client changes. The Rust client stays. The wire does not gain, lose, or reorder a system line. The paging note stays.

**Truth.** The Roux 1.1 window keeps the tail on the hot page and rolls the head. A spilled head still reads. The resident cap holds. A turn atom stays together. A pinned system prompt stays. Spill off stops at the cap. The declared battery is `RouxWindowSpillTest`. A load test is not included.

**Reason.** The window can spill the head off the hot page and still has to read that head, keep the tail, and keep a turn in one piece. Without a declared battery, that roll can move and nothing checks it. A load test was not declared, so it stays unwritten.

**Avoidances.** Do not drop the tail off the hot page. Do not split a turn atom. Do not spill a pinned system prompt. Do not read a spilled head as missing. Do not pass the resident cap while spill is off. Do not add a load test under this rule. Do not file a formal test that was not declared. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.

**semantic-key:** PK-5774

## Roux code carries the specification tags and the source prose

Every type under `org.roux` carries literate source prose and a specification tag. The same pass does not change behavior. The shell stays out.

Every type carries type-level prose and a type-level tag. A method that realizes a contract key carries the prose and the tag. A private getter stays. The edits are comments and documentation only. The prose says what the type is, what it does, and why. The tag is `@aa-sdd`.

The Swing sources change. The Rust client stays. Parked edits stay out. The wire does not gain, lose, or reorder a system line.

**Truth.** Each type in `org.roux` has type-level prose and an `@aa-sdd` tag. Each method that realizes a key has the same prose and tag. Private getters stay. Comments and documentation are the only edit. Behavior does not change. The shell is not part of this pass.

**Reason.** The Roux sources could not be read as the program. A tag that names the contract, and prose that says what the type is, what it does, and why, is the page a later reader builds from. A behavior edit does not belong in that pass.

**Avoidances.** Do not change behavior. Do not retag or rewrite the shell in this pass. Do not drop the prose or the tag from a type, or from a method that realizes a key. Do not rewrite a private getter. Do not put the tag only on a method when the type has none. Do not stage unrelated parked edits or the paging note. Do not change the Rust client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-0735

# A declared test covers the head roll and the tail spill

The full cycle is named, and the build is named. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0743 PK-0744

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. This row is not a feature. This sentence declares the formal test, so the declared-test rule is named. An outside-list row is not added.

Branch, action, stage, and the when field are not retargeted.

The battery below is the build. Our types take no lambdas. Lombok stays out. The test type is package-private. There is no new G-key, no new public type, no new public method, and no new package. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited.

The declared battery runs from the product tree. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The Java product commit names the test and the pages that record the declared-test rule. Its message states that the change is in the tree. Its message states that the change landed.

**semantic-key:** PK-7883

## Goal

A formal unit test exists when it is declared, and this sentence declares the Roux 1.1 window battery.

The head rolls. The tail stays on the hot page. A spilled head still reads. The working set stays inside the resident cap. A load test stays unwritten.

**semantic-key:** PK-5775

## What stays

The ordinary build-mode batteries stay the proof when a slice does not declare a test.

The step that refuses a test framework stays in force on those slices. The sentence already on record for that refusal stays. The open batch already in the working tree stays open. Rust stays.

**semantic-key:** PK-5776

## Build

RouxWindowSpillTest lives in src/test/java/org/roux, and the type is package-private.

A 512-byte page forces one fat frame per page. Resident pages stay 2. With spill on, four frames leave three spilled, the hot page is the tail, the head reads back, and the resident count stays inside the cap. The window of two keeps the tail and rolls the head. A token budget of 20 on four notes of 10 keeps the crossing note and rolls the oldest. A turn atom stays together. A pinned system stays in front of the tail. Spill off stops at the resident cap.

The declared-test rule is the row on the project-laws page. A declared test is maintained as the behavior evolves. A new Roux 1.1 layer updates this battery. The live next-free sentences on the glossary move to the following free number. The cold-start read moves through the declared-test rule. The finesse workflow gains one dated exception under the product minute's neighbor, at the stamp minute.

**semantic-key:** PK-5777

## Proof

The declared battery RouxWindowSpillTest runs from the product tree, and the window stays closed.

**Truth.** RouxWindowSpillTest, package-private in src/test/java/org/roux, covers the Roux 1.1 head roll and the tail spill. A 512-byte page forces one fat frame per page. Resident pages stay 2. Spill on yields four frames, three spilled, a hot tail, a readable head, and a resident count inside the cap. A window of two keeps the tail and rolls the head. A token budget of 20 on four notes of 10 keeps the crossing note and rolls the oldest. A turn atom stays together. A pinned system stays in front of the tail. Spill off stops at the resident cap. A load test stays unwritten. Java moves. Rust stays.

**Reason.** A formal unit test is filed only when it is declared. This sentence is that declaration, so the ordinary refusal of a test framework does not apply to this battery, and it still applies to every slice that does not declare a test. The battery has to stay maintained, and a new Roux 1.1 layer has to update it, because the head roll and the tail spill are the behavior the window is for.

**Avoidances.** Do not open a second revision. Do not file a smoke. Do not treat this row as a feature. Do not add an outside-list row. Do not retarget branch, action, stage, or the when field. Do not add a lambda, Lombok, a G-key, a public type, a public method, a package, a vault column, a channel, an AMWP type, a color key, or a library, and do not edit Cargo.toml. Do not make the test type public. Do not write a load test. Do not drop the 512-byte page, the resident cap of 2, the four-frame spill, the budget of 20, or the four notes of 10. Do not split a turn atom or drop a pinned system from in front of the tail. Do not put a test framework on a slice that does not declare a test. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not move Rust.

**semantic-key:** PK-5778

# Roux code carries the source tags and the source prose

The full cycle is named, and the build is named. Java moves. Rust stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-0745 PK-0746 PK-0747 PK-0748 PK-0749 PK-0750 PK-0751 PK-0752 PK-0753 PK-0754 PK-0755 PK-0756 PK-0757

## Approval

The pre-plan stays locked, and a second revision is not opened.

The locked row is the lock. Slices already locked are not rewritten. No smoke is filed. This row is not a feature. A law is not added. An outside-list row is not added.

Branch, action, stage, and the when field are not retargeted.

Comments and JavaDoc only. Our types take no lambdas. Lombok stays out. There is no new G-key, no new public type, no new public method, no new package, and no new field. There is no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited. Copyright headers stay. The author tag stays.

The product tree compiles with tests skipped. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

The Java product commit names the Roux sources and the source-prose headings. Its message states that the change is in the tree. Its message states that the change landed.

**semantic-key:** PK-7884

## Goal

A reader of org.roux can follow each type from its prose, and each tag names the spec that type serves.

The prefix is @aa-sdd. The key stays in the family the source-prose rule already locks. The spec file is one trailing word. A further trailing word is a hint. ChatShell stays out.

**semantic-key:** PK-5779

## What stays

Behavior stays. Spill, the window, identity, crypto, and close stay as the tree has them.

Crypto stays disabled. Requirement 15 stays the open weigh. The prose describes the tree. It does not lock a design sentence over the tree. roux-design.md stays locked. roux-v1.md stays. The open batch already in the working tree stays open. Rust stays. A backfill of every private line in the historical tree stays refused. This slice names Roux. The scope is type-level prose and the method that realizes a key. A private getter stays. The next free garage number stays the number already next. The outside-list number already next stays next.

**semantic-key:** PK-5780

## Standing

The working field stays the stage map. The when field stays the date already written on the resume card. This filing does not move that point. The smoke copies are this plan and the pre-plan.

**semantic-key:** PK-5781

## Filing

The locked row records the words, and slices already locked are not rewritten.

The analysis page is not rewritten. No smoke is filed ahead of the smoke that was filed with the build sentence. This row is not a feature. A law is not added. An outside-list row is not added. A glossary row is not added for NUT.

Branch, action, stage, and the when field are not retargeted.

This filing writes no Java and no Rust. Our types take no lambdas. Lombok stays out. JUnit stays out. There is no new G-key, no new vault column, no new channel, no new AMWP type, no new color key, and no new library. Cargo.toml is not edited. No new package is added. The public types named below are for the build sentence. This filing does not add them.

The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called.

That commit has no product. Java stays and Rust stays on each of those paper commits.

The sentence that names the build is the smoke. The product commit states that the change is in the tree. The ledger spends the marker two ahead of the marker this filing left as next, and the marker after that spend becomes next. Java moves. Rust stays.

**semantic-key:** PK-5782

## Goal

One transcript and one estimator seam are the estimate, and the heatbar asks the seam and does not know which estimator ran.

The transcript is the entire conversation through the armed construct. A normal cell, a group action cell, a member, a merge, and an SSH message turn are constructs in that transcript. The armed construct does not pick a second builder. Every state modifier on the construct runs. Every structural modifier on the construct runs.

**semantic-key:** PK-5783

## What stays

The landed ring stays tokenActive on an Included cell, and the redesign seats every mark on the estimate.

Included keeps both halves. Excluded keeps neither. Input only keeps the user half. Output only keeps the assistant half. That rule is the modifier section. The Java keeps tokenActive until the sentence that names the build.

CellState stays the four marks. GHOST stays EXCLUDED. Unreadable stays out, the same way kept drops it. An ephemeral card with both halves blank stays out, the same way kept drops it. Those checks are state modifiers. They run on the same walk.

A settled host count stays off the word until a later sentence names that estimator. NotebookDip.settled stays empty. The seam allows that estimator. This slice does not seat it.

A held Roux session still wears the stored tally. That tally is rouxSessionUsed for that user. The SSH section is the rule. The client does not build a second chat under it.

noteLiveDraft still calls refreshInspector. The live draft is the armed user half. The later build points that refresh at the one transcript.

assemble stays the string a Run sends. Pipes on the send stay the send's own resolution. The working set on a Run stays the working set.

Mood stays a head of the rendered string when the switch is on and the line is non-empty: the mood line, a newline, MOOD_ACTIVATE, a newline. Mood is not a turn. Mood off leaves the head off the rendered string.

The inspector paint stays a capped view. preview stays the paint cut. The user and assistant prefixes, each with two trailing spaces, stay on the paint. The ellipsis stays on the paint. The line spinner changes the paint. The number is the estimator on the full rendered transcript.

The heuristic stays (length + 3) / 4 for a non-empty string, and 1 for null or length 0. length stays Java's UTF-16 length. HeuristicTokenCounter stays that math. TokenCounter stays int count(String text).

Collapse stays view-only. The analysis page stays as filed. Faults 1 through 10 stay the diagnosis this redesign retires. The open batch already in the working tree stays open. The slices through the control-bar word stay closed. The crate stays until it is named. This plan does not judge the crate.

**semantic-key:** PK-5784

## Transcript

A transcript is ordered turns, oldest first, from the start through the armed construct, inclusive.

A turn is one user half and one assistant half. Either half may be empty. One walk builds it. A merge arm, a member arm, and an ordinary arm use that walk.

The walk reads orderedCells of the current canvas. The order is cellOrder, then any cell that list omitted. The walk does not call snapGroup. It does not write cellOrder. It does not call armFocus. It does not persist. It does not set executionStatus. It does not clear chainQueue.

The selected card is targetCell. An empty canvas has no card, and the estimate paints 0. A canvas with cards and no focusedCellId uses the fall-through targetCell already uses, the last cell in canvas.cells. The stop is that card's index in orderedCells when the card has no group id. The stop is the last index of that group id when the card has one. Cells after the stop stay out.

A cell with no group id emits one ordinary turn. A group id emits one turn when its first cell at or before the stop is reached. That turn gathers every cell of that group id at an index at or before the stop. A later cell of a group already emitted is skipped. Members are not also ordinary turns. An Excluded merge stays one group turn. An unreadable merge stays one group turn. The members do not become ordinary cards.

Member prompts are the cells with columnPosition above 0, in that canvas order. Empty prompt text stays out of the join. A newline sits between the prompts that remain, the same join memberPrompts uses. The walk uses that gate. It does not call activeMemberPrompts. stripJoinBanners runs on the merge reply. Member prompts stay as stored. A member line that begins with JOIN followed by a space stays that member's text.

An ordinary card is one turn. The raw user half is the full user text. On the armed card the live draft is that half. The raw assistant half is the full assistant text when the card already holds a reply. The modifier section decides which halves the count keeps. The reply stays in the raw transcript because the transcript is the conversation through that construct. A Run still sends the working set from assemble. The number and the send are allowed to differ.

A merge group is one turn wherever it sits. Members are not also their own turns. The user half is the member prompts the modifier section keeps, full text, joined by a newline. The assistant half is the merge reply after AetherisStage2.stripJoinBanners, when the modifier section keeps that reply, full text. JOIN banners stay out of the counted string on every arm, including the merge arm. Cards above the group are turns in the transcript when the armed construct is the group or a later card.

A click on the merge and a click on a member of that group arm the same unit: the turns above the group, then the group as that one turn. A click on a card below the group includes the group as that full turn. A click on a card above the group stops at that card. The group stays outside that transcript until the arm reaches it.

The counted string is the rendered transcript. Full turn text. No role prefix. No ellipsis. Turns concatenate in order. A non-empty half is followed by one newline. An empty half adds nothing. No extra blank line is inserted between turns. The character length is that rendered string. An empty rendering has length 0. Mood, when on, is the head.

A pipe is resolved by resolvePipes against sourcesBefore(orderedCells, stop card, cloud). The stop card is the selected card when it has no group id. The stop card is the merge sink when the selected card has a group id. That list is the pipe index. k stays 1-based into that list. The list is not the transcript. A return that starts with pipe: contributes no text for that half. The other half of the turn still follows its mark. The estimate does not call assemble. It does not mark the cell failed. It does not clear the run queue. The existing red error path stays a different slice. This plan does not open it.

The token window's character length is the rendered transcript length. The token window's block count is the turn count. The painted line count stays paint.

**semantic-key:** PK-5785

## Modifiers

Structure runs first, and state runs second.

Both run for every construct through the armed construct. The estimator sees the halves that remain. A structural modifier says how a construct becomes turns.

An ordinary card is one turn. The raw user half is the full user text. On the armed card the live draft is that half. The raw assistant half is the full assistant text when the card holds a reply.

A merge group is one turn. Each member's state modifier gates that member's prompt. Prompts that remain join by a newline. That join is the group's user half. The merge card's state modifier gates the reply after stripJoinBanners. That reply is the group's assistant half. The merge mark gates the reply. A member mark gates that member's prompt. One mark does not stand in for the other.

An SSH message is one turn. A message with no canvas mark uses IncludedModifier, so the message text counts. A later mark on a message uses the same state chain.

A state modifier says which halves count. The seated four match the gates appendCell already uses on a send.

Included keeps the user half and the assistant half. Excluded keeps both out. The walk still visits the card. The rendered transcript gains no text from it. The block count does not gain a turn. Input only keeps the user half and drops the assistant half. A live draft on an Input only card counts. Output only keeps the assistant half and drops the user half. The prompt on an Output only card stays out, including a live draft.

GHOST is Excluded. Unreadable contributes ExcludedModifier, then the mark. A blank ephemeral card contributes ExcludedModifier. A half survives when every modifier in the chain keeps it.

The same gates sit on a group. A member prompt counts when that member is Included or Input only, which is the gate in memberPrompts. A merge reply counts when the merge is Included or Output only, which is the gate in appendMergedTurn. An Excluded merge leaves the cards above it in the transcript. The group stays one turn. The reply stays out because the mark is Excluded. Member prompts still follow each member's own mark. The members are not emitted as ordinary cards.

A construct whose marks keep no half adds no turn. A construct whose marks keep a half adds one turn, including a half whose text is empty. A blank ephemeral card adds no turn, including when that card is the selected card.

When cloudTarget(outboundTarget(selected card)) is true, every ephemeral cell other than the selected card contributes ExcludedModifier, then its mark. A local target does not add that modifier. The selected card is not dropped for being ephemeral. outboundTarget is the card's execution target, and an empty target reads as ollama.

A later state implements StateModifier. A later structure implements StructuralModifier. The walk calls the modifier the construct carries. The armed card does not open another builder.

assemble stays the Run. The estimate is this transcript. The send gates halves and stops before the armed reply. The estimate includes the armed reply when the armed mark keeps that half. The two strings are allowed to differ.

**semantic-key:** PK-5786

## Selection

A click on a merge group arms the group as one turn for the estimate.

armFocus records the clicked card. focusedCellId becomes that card's id. The selected card stays the card under the pointer. The caret and the scroll stay on the clicked card. targetCell may still return that card.

The estimate reads the group when that card belongs to one. An input member is groupedMemberCell: a group id is set and the column is above 0. The merge output is mergeSink: a group id is set and the column is 0. Either click resolves to the same armed construct. GroupStructure emits one turn. The transcript is every turn above the group, then that turn. The count is the same number for both clicks.

The member is not its own turn. The mark on the clicked card does not split the group into separate turns. The modifier section still gates each half inside the one turn. An Excluded member, an Input only member, and an Output only member arm the same group unit.

A card with no group id arms that card. The walk stops at that card.

transcriptThroughArm performs the resolution. A group id on the selected card selects the group before the walk builds the transcript. The armed construct for the count is the group. The clicked card stays the selection. transcriptThroughArm does not change focusedCellId. A click on a member leaves the member selected.

The count reads userText and assistantText on the cell records. noteLiveDraft writes the user pane onto userText before refreshInspector. The walk reads those fields. A merge sink has no user pane. A keystroke draft lives on the focused card that has one. A click on the merge reads the stored member prompts. The two clicks match on those stored texts.

**semantic-key:** PK-5787

## Estimator

The windows call one entry, and the entry takes the transcript and an estimator.

The heatbar paints the integer the entry returns. TokenCounter stays the string seam. HeuristicTokenCounter stays the string math.

A transcript estimator implements int estimate(Transcript transcript). The canvas seats the heuristic estimator. It renders the transcript and calls HeuristicTokenCounter. A later method implements the same estimate entry. A host prompt_eval_count, a real tokenizer, and a stored sum are later estimators. The settled host count stays off the canvas word until a sentence names one of them onto that word.

The canvas heuristic counts the rendered transcript once. A sum of one heuristic per turn is a different number, because (length + 3) / 4 is not a sum across the joins. The canvas uses the one call.

Zero turns and no mood head paints 0 and does not call the counter. A mood head with zero turns is a rendered string, and the counter runs. No card on the canvas paints 0. Mood waits for a card. A transcript with at least one turn whose rendered text is empty calls the counter. The heuristic returns 1. An empty rendering has character length 0. That pair is seated. Fault 10 stays the diagnosis of the split that exists today. The SSH section does not use this zero-turn rule.

**semantic-key:** PK-5788

## Canvas

refreshInspector asks the one entry, and click and keystroke share it.

tokenEstimate stores the result. tokenRingUsed returns it. paintTokenEstimate paints it. refreshInspector still paints the inspector, and it still calls paintFacts and paintMoodStatus.

The inspector document stays the capped paint. The prefixes and the ellipsis stay on that paint. The estimator does not read inspector.getText(). paintTokenEstimate receives the estimate, the rendered length, the turn count, and the rendered transcript. notebookDip.watch receives that same transcript. A settled host count stays off the word.

The armed construct is inside the transcript. Its mark still gates the halves. tokenActive stays the Included predicate. The walk does not call it. appendActiveGroupTokens and appendActiveMergeTokens retire from the counted string on the later build. The paint helpers may stay for the capped inspector. The number does not read the capped lines.

**semantic-key:** PK-5789

## SSH

The SSH plate and the canvas plate share the heatbar entry, and the tab selects the source.

The entry does not require a canvas card. The held Roux tally is one triple for that user: rouxSessionUsed, rouxSessionChars, and rouxSessionBlocks. The wire reply is session_tokens_result with used, chars, and blocks for the whole chat. The server already summed that chat in sessionTokenReply. There is no per-message stored count on the reply. The client does not hold the chat as a second transcript.

When rouxSessionHeld is true, the SSH heatbar integer is rouxSessionUsed. The token window opens on that same triple. The client does not sum the chat again. The client does not add a heuristic on top of used. StoredCountEstimator returns that held integer. The heatbar does not read Vt100TokenWindow.Book.

When the session is not held, sessionMemoryText is one turn, including empty text. The heuristic counts that string. Empty text returns 1. The character length is the string length. The block count stays sessionMemoryBlocks. This path does not use the canvas zero-turn rule.

feedComposerHeat and openTokenCalc keep that split. The Aetheris tab reads tokenRingUsed. The SSH tab reads the held integer or the session heuristic. The heatbar stays on the result. A later sentence that wants the client to walk Roux messages by name has to name that list. This plan does not invent one. The wire stanza stays empty.

Vt100TokenWindow.heuristic stays the string math until the later build points it at HeuristicTokenCounter. The results stay the ones Vt100TokenWindowTest already asserts: null is 1, empty is 1, abcd is 1, abcde is 2. This filing does not change that battery. A later build that delegates the method keeps those results. A new test is not declared.

**semantic-key:** PK-5790

## Build inventory

This filing adds none of the types, and the later build accounts for every type named here and implements them.

The package org.roux.token already holds TokenCounter and HeuristicTokenCounter. The later build adds the types beside them. TokenCounter.count stays the string seam.

org.roux.token.Transcript holds ordered turns. A public static Turn holds the user half and the assistant half. rendered returns the counted string. The held SSH integer stays the estimator result. It is not a field on the canvas turn.

org.roux.token.TokenEstimator is int estimate(Transcript transcript).

org.roux.token.HeuristicEstimator implements TokenEstimator. It renders, then calls HeuristicTokenCounter.

org.roux.token.StoredCountEstimator implements TokenEstimator. It is the SSH held-session rule. It returns rouxSessionUsed for that user. It does not walk the chat a second time.

org.roux.token.StateModifier answers keepUser and keepAssistant. An empty return means that half stays out of the counted string.

org.roux.token.IncludedModifier keeps both halves. org.roux.token.ExcludedModifier keeps both out. org.roux.token.InputOnlyModifier keeps the user half and drops the assistant half. org.roux.token.OutputOnlyModifier keeps the assistant half and drops the user half.

StateChain is package-private in org.roux.token. It applies the modifiers in order. A half stays when every modifier keeps it.

org.roux.token.StructuralModifier is the structure seam. OrdinaryStructure emits one turn. GroupStructure emits one turn and gathers member prompts. MessageStructure is the type a later sentence may use when that sentence names a client message list. MessageStructure has no caller. The SSH section is the SSH behavior this plan seats. A later structure implements the same type.

On AetherisNotebookPanel, the private method transcriptThroughArm builds the canvas transcript. A selected card with a group id resolves to the group before that build. refreshInspector calls the seated estimator. No new public method on the notebook.

On Vt100TokenWindow, heuristic delegates to HeuristicTokenCounter.count. The results stay the ones the SSH section names for the unheld string. The held path returns the triple the SSH section names. No new public method is required beyond the types this section names. The heatbar does not gain a reader of Vt100TokenWindow.Book.

No lambdas. Anonymous classes stay in product UI. Java leads. The crate stays until it is named.

**semantic-key:** PK-5791

## Build order

The type list is the inventory, and this order is the order the build follows when the sentence names it.

The package org.roux.token stays. TokenCounter and HeuristicTokenCounter stay. The new types are classes beside them. No lambdas. No Lombok. No new Spring stereotype. The notebook constructs the estimator it seats. HeuristicTokenCounter.count stays the string math. This naming adds none of the types on the paper commits. The shakedown seal stays. The sentence that names the build ends the wait.

Step 1 is Transcript. It holds ordered turns, oldest first. Turn is a public static type on it. A turn holds the user half and the assistant half. rendered walks the turns. A non-empty half is followed by one newline. An empty half adds nothing. No role prefix. No ellipsis. No extra blank line between turns. The caller passes the mood head when the switch is on and the line is non-empty: the mood line, a newline, the activate line, a newline, then the turns. Mood is not a turn. Zero turns and no mood head return the empty string.

Step 2 is state. StateModifier answers keepUser and keepAssistant. IncludedModifier keeps both. ExcludedModifier keeps neither. InputOnlyModifier keeps the user half. OutputOnlyModifier keeps the assistant half. StateChain is package-private. A half stays when every modifier in the chain keeps it. GHOST contributes ExcludedModifier. Unreadable contributes ExcludedModifier, then the mark. A blank ephemeral card contributes ExcludedModifier. A cloud arm contributes ExcludedModifier on every ephemeral cell other than the selected card, then that cell's mark. A local arm does not add that modifier.

Step 3 is structure. StructuralModifier says how a construct becomes turns. Structure runs first. State runs second. OrdinaryStructure emits one turn. The user half is the full user text. On the armed card that half is the live draft noteLiveDraft already wrote. The assistant half is the full assistant text when the card holds a reply. GroupStructure emits one turn. Member prompts are the cells with columnPosition above 0, in canvas order. Empty prompt text stays out of the join. A newline sits between the prompts that remain. Each member's chain gates that member's prompt. The assistant half is stripJoinBanners on the merge reply, gated by the merge mark. A member is not also an ordinary turn. MessageStructure is the type only. It has no caller. The SSH section does not invent a message list.

Step 4 is the walk. transcriptThroughArm is private on AetherisNotebookPanel. A selected card with a group id resolves to the group before the walk. The clicked card stays the selection. The method does not change focusedCellId. The walk reads orderedCells. It does not call snapGroup. It does not write cellOrder. It does not call armFocus. It does not persist. It does not set executionStatus. It does not clear chainQueue. The stop is the transcript section. Pipes stay in that section. The walk calls resolvePipes against sourcesBefore. A return that starts with pipe: contributes no text for that half. The estimate does not call assemble. One group id emits one turn. A later cell of a group already emitted is skipped.

Step 5 is the estimators. TokenEstimator is int estimate(Transcript transcript). HeuristicEstimator implements it. Zero turns and no mood head return 0 and do not call count. A mood head with zero turns renders that head and calls count. A transcript that has a turn and renders empty calls count, and the heuristic returns 1. Any other transcript renders once and calls count once. A sum of one heuristic per turn stays out. StoredCountEstimator implements the held SSH rule. The caller names the user. The result is rouxSessionUsed for that user. The estimator does not call the heuristic. It does not walk the chat. It does not read the unsent line.

Step 6 is the canvas entry. refreshInspector calls transcriptThroughArm, then the seated HeuristicEstimator, and stores the integer on tokenEstimate. tokenRingUsed returns it. paintTokenEstimate paints it. The inspector paint stays the capped document. notebookDip.watch receives the rendered transcript. tokenActive stays the Included predicate. The walk does not call it. appendActiveGroupTokens and appendActiveMergeTokens leave the counted string. The paint helpers may stay. noteLiveDraft still writes the prompt and still calls refreshInspector. Click and keystroke share that entry. No new public method on the notebook.

Step 7 is the SSH entry. Vt100TokenWindow.heuristic delegates to HeuristicTokenCounter.count. The results stay null is 1, empty is 1, abcd is 1, and abcde is 2. The heatbar does not gain a reader of Vt100TokenWindow.Book. feedComposerHeat and openTokenCalc keep the tab split. The Aetheris tab reads tokenRingUsed. On the SSH tab, a held session takes StoredCountEstimator as the base. That base is rouxSessionUsed. The unsent line stays at the caller. sshLiveCount still adds HeuristicTokenCounter.count of the focused pane's unsent line when that line is non-empty, and adds 0 when the line is empty. That addition is not inside StoredCountEstimator. It is not a second walk of the chat. The SSH section binds the estimator. The live-line slice binds the unsent line. An unheld session keeps the caller string already seated: sessionMemoryText, plus the draft when the draft is not already a suffix. The build does not invent a message list to get there.

Step 8 is the proof when the sentence comes. The product tree compiles with tests skipped. JUnit stays out. A battery is not declared. The window stays closed. No live POST is made. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. Java moves on that sentence. Rust stays. The crate waits until it is named.

The package and the type list stay one inventory. transcriptThroughArm is one pass. rendered is one count. The heatbar wears the integer the entry returns, and the unsent line stays at the caller. No extra cut is opened.

There is no side move. assemble stays the Run. The canvas door, the group door, the pipe door, and the console door stay in the order they have.

**semantic-key:** PK-5792

## Proof when the build is named

The product tree compiles with tests skipped, and the window stays closed.

The proof does not make a live POST. A pool turn is not sent. A live SSH connect is not made. Session::start is not called. chrome::run is not called. This filing runs no proof. No Java moves on the filing.

**semantic-key:** PK-5793

## Smoke

The rules above are the seated plan, and these checks are the smoke.

One walk runs through the armed construct. A click on an input member and a click on the merge output arm the group as one turn. The count matches. The counted string is full text. JOIN banners are stripped. Member prompts join by newline. Prefixes and the ellipsis stay paint. The block count is the turn count. The character length is the rendered length. assemble stays the Run. The estimate includes the armed reply. The two strings are allowed to differ. The four marks gate the halves. The Included-only ring stays the landed code until the build sentence. The settled host count stays. The held Roux tally stays. The live draft stays. An empty transcript paints 0. A non-empty transcript with an empty rendering counts 1. A resolved pipe contributes the resolved text. A failed pipe is named and contributes no fake body. The canvas seats HeuristicEstimator. A held SSH session seats rouxSessionUsed. An unheld SSH session seats the heuristic on sessionMemoryText, and empty text returns 1. A later estimator implements TokenEstimator. The heatbar stays on the integer. The walk reads orderedCells. A group id emits one turn. An Excluded merge stays one group turn. Cards above a focused group stay in the transcript. The canvas count is one heuristic of the rendered transcript. An empty half adds no characters. A kept empty half still adds one turn. Pipe k uses sourcesBefore. A pipe: half contributes no text. The cell stays unfailed. The run queue stays. A cloud arm drops ephemeral cells other than the selected card. A local arm does not. No focus on a populated canvas uses targetCell's fall-through. An empty canvas paints 0. focusedCellId stays the clicked card. The inspector paint stays the capped document. The dip watches the rendered transcript.

**semantic-key:** PK-5794

## Shakedown

Where a later sentence is tighter, that sentence is the rule, and the filing, the marks, and the arm stay.

The walk reads orderedCells. A group emits once. sourcesBefore stays the pipe index. An empty half adds no characters. A kept empty half still adds one turn. Zero turns and no mood head paints 0. The canvas count is one call on the rendered string. A pipe failure contributes no text and leaves the inspector up. A cloud arm drops ephemeral cells other than the selected card. An Excluded merge stays one group. The members stay inside that turn. focusedCellId stays the clicked card. The merge has no user pane. The inspector paint stays capped. The dip watches the rendered transcript. tokenActive stays the Included predicate. The held SSH integer is rouxSessionUsed. The unheld string is sessionMemoryText. The build stays unnamed until the sentence that names it. The types stay on this page. The filing adds none of them. The sentence that names the build ends that wait. The types in the inventory are the build.

**semantic-key:** PK-5795

## Scope

One transcript, one estimator, and the modifier chain are the working, and the one walk seats them.

The count is one string after the modifiers run. The heatbar word is the estimator result. The shakedown is the tighter rule. The build design is the inventory and the order. No extra cut is opened. There is no side move on this slice. StateChain stays package-private. MessageStructure has no caller. The crate stays until it is named.

**semantic-key:** PK-5796

## Wire

The wire stanza is empty, and no system line is added, removed, or reordered.

This filing leaves the working set as it stands. The later build leaves the working set as it stands.

**Truth.** One walk builds one transcript, oldest first, through the armed construct. A merge click and a member click arm that group as one turn and produce the same count. Structure runs before state. Included, Excluded, Input only, and Output only gate the halves. The canvas counts one heuristic of the rendered string, (length + 3) / 4 on Java UTF-16 length, or 1 for null or length 0. Zero turns and no mood head paint 0. A turn that renders empty counts 1. A held SSH session wears rouxSessionUsed and does not sum the chat again. An unheld session counts sessionMemoryText, and empty text returns 1. The unsent line stays at the caller. The filing adds no type. The named build adds Transcript, TokenEstimator, HeuristicEstimator, StoredCountEstimator, StateModifier, IncludedModifier, ExcludedModifier, InputOnlyModifier, OutputOnlyModifier, StructuralModifier, OrdinaryStructure, GroupStructure, and MessageStructure in org.roux.token, with StateChain package-private and MessageStructure unused. Java moves on that build. Rust stays.

**Reason.** Separate builders were counting different windows for a merge, a member, and an ordinary card, and a sum of per-turn heuristics is not the heuristic of the joined string. One rendered transcript lets every mark and every structure run on the same walk, while assemble remains the Run and is allowed to differ. The held Roux integer is already the server's sum, so the client must not add a second sum or fold the unsent line into StoredCountEstimator.

Do not file a smoke before the sentence that names the build. Do not retarget branch, action, stage, or the when field. Do not add the named types on the filing. Do not add a lambda, Lombok, JUnit, a G-key, a vault column, a channel, an AMWP type, a color key, a library, a package, or a Cargo.toml edit. Do not call snapGroup, write cellOrder, call armFocus, persist, set executionStatus, or clear chainQueue from the walk. Do not call assemble from the estimate, mark the cell failed, or clear the run queue on a pipe failure. Do not call activeMemberPrompts. Do not read inspector.getText() for the number. Do not sum one heuristic per turn. Do not use the canvas zero-turn rule on SSH. Do not read Vt100TokenWindow.Book from the heatbar. Do not invent a client message list. Do not put the unsent line inside StoredCountEstimator. Do not change focusedCellId from transcriptThroughArm. Do not give MessageStructure a caller. Do not make StateChain public. Do not declare a new test. Do not change the asserted results null 1, empty 1, abcd 1, and abcde 2. Do not seat a settled prompt_eval_count on the word until a sentence names that estimator. Do not judge the crate. Do not open the window, send a live POST, send a pool turn, open a live SSH session, call Session::start, or call chrome::run. Do not add, remove, or reorder a system line.

**semantic-key:** PK-7903

# A declared test covers the head roll and the tail spill

A formal unit test is declared. The battery covers the Roux 1.1 head roll and the tail spill. A load test stays unwritten. The declared-test rule says a declared test is maintained as the behavior evolves. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-0779 PK-0780

## Dependencies

The battery depends on RouxEngine, RouxProperties, RegionPack, RouxCounters, CryptoSink, and RouxIdGenerator.

JUnit is already on the test classpath through spring-boot-starter-test. No new library is added. Cargo.toml is not edited. Rust stays.

The proof is the declared battery RouxWindowSpillTest, run from the product tree. The window stays closed.

**semantic-key:** PK-5797

## Seat

The test type is the package-private RouxWindowSpillTest in src/test/java/org/roux.

A small page forces a spill. The hot page is the tail. A spilled head still reads. The resident cap holds. The window keeps the tail and rolls the head. A turn atom stays together. A pinned system stays. Spill off stops at the cap.

The declared-test rule is filed on the project-laws page. The live next-free sentences move to the following free number. The cold-start read moves through the declared-test rule. The finesse workflow gains one dated exception. The step that refuses a test framework stays in force until a sentence declares a test. The sentence already on record for that refusal stays.

**Truth.** A declared Roux 1.1 window test covers the head roll and the tail spill. The type is package-private RouxWindowSpillTest in src/test/java/org/roux. A load test stays unwritten.

**Reason.** The spill has to be a declared test, maintained as the behavior evolves, because the ordinary build-mode batteries are the proof only when a slice does not declare a test. The types already in the tree are enough, and the test framework is already on the classpath through spring-boot-starter-test.

Do not write a load test. Do not add a library or edit Cargo.toml. Do not move Rust. Do not stage the paging memorandum. Do not open the window for the proof. Do not put a test framework on a slice until a sentence declares a test. Do not drop the hot tail, the readable spilled head, the resident cap, the turn atom, or the pinned system.

**semantic-key:** PK-5798

# Roux code carries the source tags and the source prose

The Roux code is scanned. The scan applies the relevant @aa-sdd tags. The same scan upgrades the Roux documentation to the source prose a reader can follow type by type. ChatShell stays out. The formal plan is the page stamped from this sketch.


**semantic-key:** PK-0781 PK-0782 PK-0783 PK-0784 PK-0785 PK-0786 PK-0787 PK-0788 PK-0789 PK-0790 PK-0791 PK-0792 PK-0793

## Dependencies

The scan covers the types under org.roux, and the test RouxWindowSpillTest.

The source-prose page is aa-sdd-source-prose.md, where a type with no G-key takes its heading. No new library is added. Cargo.toml is not edited. Rust stays. roux-design.md stays locked. roux-v1.md stays.

The proof is a compile of the product tree with tests skipped. The window stays closed.


**semantic-key:** PK-0794

## Seat

Every public type under org.roux carries type-level source prose and one type-level tag.

A method that realizes a seated requirement carries the prose and the tag. A private getter stays. The existing openRam tag keeps G-08 and gains G-09 and Q-S1-1 on the stages file. ToonCodec encode carries G-13 for the one MSG. Other tags use the source-prose key and aa-sdd-source-prose.md with a heading fragment, and a trailing hint that names the Roux page. Comments and JavaDoc only. No behavior change.

The source-prose page gains a heading for each Roux type the tags name. Those headings ride the product commit with the Java.

**Truth.** Every public type under org.roux carries source prose and one @aa-sdd tag. Methods that realize a seated requirement do the same. openRam keeps G-08 and gains G-09 and Q-S1-1 on the stages file. ToonCodec encode carries G-13 for the one MSG. ChatShell stays out.

**Reason.** The tags have to name the spec, and the prose has to say what the type is, without a behavior change and without a new library. A type with no G-key takes a heading on aa-sdd-source-prose.md. A private getter is not that scan.

Do not change behavior. Do not include ChatShell. Do not add a library or edit Cargo.toml. Do not move Rust. Do not stage the paging memorandum or roux-1.1-paging.md. Do not unlock roux-design.md or drop roux-v1.md. Do not drop a private getter's existing shape in order to tag it. Do not open the window for the proof.

**semantic-key:** PK-5799

## Dependencies

armFocus stores the clicked card in focusedCellId, and targetCell returns that card.

A member click and a merge click are different cards today. The estimate requirement is that both arm the group as one turn. refreshInspector builds the counted string today. A focused merge calls appendActiveGroupTokens and leaves the cards above the group out. Any other arm calls sourcesBefore, then appendActiveMergeTokens for a group inside that walk, and counts the armed prompt in full when the card is Included. paintTokenEstimate stores HeuristicTokenCounter.count on tokenEstimate. tokenRingUsed returns that integer. The heatbar reads it. noteLiveDraft calls refreshInspector. The live-draft slice stays on that path. The later build points the refresh at the one transcript.

assemble stays the Run string. The estimate is the transcript. They are allowed to differ.

TokenCounter is int count(String text), at least 1. HeuristicTokenCounter implements it. That is the seated string math.

Vt100TokenWindow.heuristic repeats that math. messageCount prefers a stored count. sum adds the message counts. feedComposerHeat wears tokenRingUsed on the Aetheris tab. On the SSH tab a held Roux session wears rouxSessionUsed. An unheld session wears heuristic on sessionMemoryText. openTokenCalc opens the same integer. That seating stays. A settled host count stays off the word until a later sentence names that estimator.

CellState is the four marks. GHOST is EXCLUDED. tokenActive is the landed Included filter. appendCell gates halves: Included keeps both, Input only keeps the user half, Output only keeps the assistant half. kept drops Excluded and unreadable. memberPrompts keeps Included and Input only prompts. appendMergedTurn keeps the reply when the merge is Included or Output only. The estimate uses those gates on the one transcript. The Java keeps tokenActive until the build sentence. Mood stays a head when the switch is on. The inspector paint stays capped. The line spinner changes paint. The analysis page stays as filed. The crate stays until it is named. No new library is added. Cargo.toml is not edited. No new color key is added. No new package is added.

This filing runs no product compile and no crate check. The window stays closed. When the sentence names the build, the proof compiles the product tree with tests skipped. The window stays closed on that proof.

**semantic-key:** PK-5800

## Seat

One transcript runs oldest first, through the armed construct, inclusive.

A click on any card of a merge group arms that group as one turn. One estimator seam answers the heatbar. The canvas seats HeuristicTokenCounter on the rendered transcript. The SSH tab seats rouxSessionUsed when the session holds one, and the heuristic of sessionMemoryText when it does not. A later estimator implements the same seam.

The counted string is the full turn text after the modifiers. Included keeps both halves. Excluded keeps neither. Input only keeps the user half. Output only keeps the assistant half. A later modifier implements the same seam. Role prefixes stay on the paint. The ellipsis stays on the paint. JOIN banners are stripped from the merge reply. Member prompts that the member's mark keeps join by newline. An empty canvas paints 0. A transcript that has a turn and renders empty counts 1.

The shakedown is the tighter rule. The walk reads orderedCells and emits a group once. The canvas count is one heuristic of the rendered string. Pipe k stays on sourcesBefore. A cloud arm drops ephemeral cells other than the selected card. The held SSH integer is rouxSessionUsed. The client does not sum that chat again. An unheld session counts sessionMemoryText as one string. Empty glass text returns 1.

No new public type is added on this filing. The plan names the types the build sentence adds. This pre-plan has no product of its own.

**Truth.** One transcript, oldest first and through the armed construct, is the estimate for a normal cell, a group action, and an SSH message turn. A member click and a merge click arm the group as one turn. I, E, P, and O are state modifiers on that one walk, beside any structural modifier. The canvas uses one heuristic of the rendered string. A held SSH session uses rouxSessionUsed and is not summed again. An unheld session counts sessionMemoryText, and empty glass text returns 1. The build sentence adds the types named on the plan. Java moves then. Rust stays.

**Reason.** The old refresh built a different string for a focused merge than for any other arm, and it left cards above the group out. One walk and one rendered string stop that split. assemble stays the Run so the send and the estimate may differ. The shakedown keeps the pipe index, the cloud drop, and the held tally from slipping off that walk.

Do not add a public type, a package, a color key, a library, or a Cargo.toml edit on the filing. Do not name the crate. Do not rewrite the analysis page or drop faults 1 through 10. Do not add a glossary row for NUT. Do not let a member click and a merge click count different turns. Do not sum the held chat on the client. Do not call the product compile or the crate check on the filing. Do not open the window. Do not treat this pre-plan as the product commit.

**semantic-key:** PK-5801

## Attention principle

The model attends the head and the tail most strongly, and the middle least, and the store places stable material at the head, relocatable material in the middle, and live state at the tail.

The head is the frame: preconditions, constraints, and standing rules. The tail is the fluctuating edge of the current turn. The middle is where attention degrades, so that is where content may move at the lowest semantic cost. The geometry is a constraint the design uses, not a defect it works around.

**Truth.** Stable rules sit in the head, live state sits in the tail, and the weak middle is the span that may be relocated.

**Reason.** Putting the standing frame where attention is strongest keeps that frame in force. Putting recoverable reference where attention is weakest moves the bytes that cost the least to move.

**Avoidances.** Do not put the standing frame in the middle. Do not treat the tail as the first spill source. Do not design as if attention were flat across the window.

**semantic-key:** PK-5802

## Reichenbach fall

When head pressure or the window limit requires eviction, middle content falls as a gradient toward a barrier immediately below the protected head, and hitting that barrier spills the block with its pointer.

The fall is not a cliff. Content slides toward the barrier. At the barrier it is spilled to disk, or to the configured spill store, with full pointer metadata. Spill is relocation, not deletion. Each spilled block carries a return address, a pointer index, so it can be rehydrated without reconstruction and without summarization. Any spilled block may be rolled back into the fuzzy middle, or higher, by that pointer, on demand.

**Truth.** The fall spills at a barrier under the protected head and can be rolled back by pointer.

**Reason.** A cliff that deletes or summarizes cannot be reversed. A pointer index is enough to put the same block back.

**Avoidances.** Do not delete on the fall. Do not summarize a spilled block in order to restore it. Do not reconstruct the text from a digest. Do not spill before the block reaches the barrier.


**semantic-key:** PK-0814

## Fuzzy middle

Everything between the protected head and the live tail is the fuzzy middle, and that middle is the only region eligible for spill.

Content there may be relocated because attention is weakest there, provided the pointers that name it stay intact. The frame and the live edge are not part of this span.

**Truth.** Spill sources are middle turns only. The head and the tail are outside that eligibility.

**Reason.** Relocating the middle leaves the frame and the live edge in place. Relocating either of those would corrupt the geometry the window is built on.

**Avoidances.** Do not spill the protected head. Do not spill the live tail ahead of the middle. Do not relocate a middle block without keeping its pointer.

**semantic-key:** PK-5803

## Intent

The paged memory manager grows a protected head and a fall so eviction is deterministic and reversible, and so a region the operator fixed stays fixed unless that operator changes it.

The head holds the standing frame of the window. The tail holds the live turn. The middle is what the fall may relocate. Nothing in this pattern compacts the store to free a slot.

**Truth.** Eviction under this intent relocates the middle and leaves the protected head under operator control.

**Reason.** A window that both forgets its frame and rewrites history cannot be rolled back to a known return address.

**Avoidances.** Do not evict the protected head to relieve pressure. Do not make the fall a one-way drop. Do not compact in order to spill.

**semantic-key:** PK-5804

## Layer placement

The protected-head barrier and the fall are placed in layer two, above the head-and-tail spill that already works.

The barrier is a hard wall on the eviction path. The fall is the only legal way content leaves the active window. This placement sits on top of the page spill that is already in service. It does not replace that spill.

**Truth.** Layer two, in this design, is the barrier and the fall, above the working page spill.

**Reason.** The page spill already moves a full page. The fall is a window rule about which span may leave, and it needs the lower spill underneath it.

**Avoidances.** Do not implement the fall as a second byte mover beside page spill. Do not let content leave the active window by any path other than the fall. Do not put the barrier below the page spill it depends on.

**semantic-key:** PK-5805

## Outcome

The active window stays a flowing region whose head is stable under operator control and whose middle can be spilled and restored by pointer.

Strongest attention stays on a fixed frame at the front and on live state at the back. Recoverable reference occupies the weaker middle. Compaction is eliminated.

**Truth.** The outcome is a stable head, a live tail, a pointer-restorable middle, and no compaction.

**Reason.** That shape matches the attention geometry and keeps every omitted turn recoverable as itself.

**Avoidances.** Do not compact the window as a substitute for the fall. Do not leave the middle unrestorable. Do not let the head drift unless the operator moves it.

**semantic-key:** PK-5806

## Spill contract

Spill always takes from the fuzzy middle, never changes the protected head, leaves the tail live until last, and moves pointers instead of compacting.

The protected head is never a spill source and is never modified by paging. Only an explicit operator operation may alter it. The tail remains live and is the last region considered for any eviction. No stop-the-world compaction is permitted. Eviction is a pointer move only. Crash recovery restores the protected head from its durable form and reattaches any in-flight spill pointers so the fall can resume or roll back cleanly.

**Truth.** The spill contract is middle-only, pointer-only, and recoverable, with the head restored from its durable form.

**Reason.** A compaction pause rewrites the store the operator did not ask to rewrite. A crash that drops the head or the in-flight pointers cannot resume the fall.

**Avoidances.** Do not compact to evict. Do not modify the protected head from the pager. Do not evict the tail while middle blocks remain. Do not drop in-flight spill pointers across a crash.

**semantic-key:** PK-5807

## Reichenbach fall

The paged memory gains a protected head and a fall that relocates context without compaction.

The head of the active window stays fixed relative to attention unless the operator changes it. Eviction is reversible. The middle is the only span the fall may move. The tail stays the live edge.

**Truth.** A protected head plus a reversible fall is the intent of this design. Compaction is not the way content leaves the window.

**Reason.** Attention is strongest at the head and the tail. A design that rewrites or summarizes the middle spends its cost where the model is weakest and risks the frame where the model is strongest.

**Avoidances.** Do not treat the fall as deletion. Do not let paging rewrite the protected head. Do not compact the active window to make room.


**semantic-key:** PK-0816

## Compaction

The sink does not compact, summarize, or delete a turn.

No stop-the-world compaction is permitted. Leaving the wire is not a deletion. The pack is not rewritten to close a gap.

**Truth.** The sink never compacts, summarizes, or deletes.

**Reason.** Compaction rewrites history and renumbers or relocates bytes the index still needs. The selection is a read filter.

**Avoidances.** Do not compact on selection. Do not summarize an omitted turn. Do not delete a turn to fit the managed size.

**semantic-key:** PK-5808

## Contract

A later build matches these headings. Until that build lands, the tree stays on today's `windowIds`.

The headings are the behavior. A plan stamp is what licenses the code. This page does not.

**Truth.** The headings bind the later build. Today's `windowIds` stays until that build lands.

**Reason.** Implementing from a draft before the stamp cuts code the headings have not yet been planned against.

**Avoidances.** Do not change `windowIds` before the build lands. Do not treat this page as the stamp. Do not invent behavior the headings do not state.

**semantic-key:** PK-5809

## Edit

The operator changes the current text of one sequence from the console.

The change is by message id, which on this tape is the sequence.

**Truth.** Console edit of one sequence is how the operator changes current text.

**Reason.** The lock keeps a turn on the wire. Without an edit, the operator cannot revise a locked turn without clearing the region.

**Avoidances.** Do not require a region clear to change one turn. Do not edit by a filtered row number. Do not edit from the pager.

**semantic-key:** PK-5810

## Head

The head is the turns the operator designates, and the count of those turns is the size the operator sets.

The fixed head is a specified number of operator-controlled static turns. They make up the head context and stay locked until the operator changes them.

**Truth.** The head is an operator-set count of designated turns.

**Reason.** A head defined by age or by token width would move without an operator action. The count is the size the operator set.

**Avoidances.** Do not define the head as "the oldest N tokens." Do not change membership except by an operator designation.

**semantic-key:** PK-5811

## Layer

The sink is its own layer on the window, after the publish layer and after reattach, and the filed name of layer 2 stays reattach.

The barrier and the fall are a window concern. They are not a rename of reattach. Reattach remains the scan that rebuilds the index from frames. The sink sits after that, on the window.

**Truth.** The sink is a window layer after publish and after reattach. Layer 2 keeps the name reattach.

**Reason.** An earlier note placed the fall in layer two. That layer is already the reattach scan. Reusing the name would bury the scan or pretend the fall moves bytes.

**Avoidances.** Do not rename reattach to the sink. Do not implement the sink as a change to the page-spill transaction. Do not put the sink before the publish layer.

**semantic-key:** PK-5812

## Middle

Every sequence between the designated head and the tail stays off the wire.

That span is the fuzzy middle. On the wire it is the only region left out of the transcript. It stays listed and showable.

**Truth.** Sequences strictly between the designated head and the fitting tail are off the wire.

**Reason.** Those sequences are the weak middle. Sending them would push out either the head or the live tail.

**Avoidances.** Do not place a middle sequence on the wire. Do not delete a middle sequence because it was omitted.

**semantic-key:** PK-5813

## Mood

After the head is designated, mood and the system prompt leave those sequences alone.

Only an explicit operator operation may alter the protected head.

**Truth.** Mood seating and the system prompt do not rewrite a designated head sequence.

**Reason.** Those writers already rewrite a leading run today. Once a head is designated, that rewrite would change locked text without an edit.

**Avoidances.** Do not let mood rewrite a designated sequence. Do not let the system-prompt writer rewrite a designated sequence. Do not treat mood seating as the explicit operation that may alter the head.

**semantic-key:** PK-5814

## Name

The component name stays Reichenbach Sink.

A window name would sit on `windowIds`. A fall name would name the motion. The component is the basin that selects.

**Truth.** The name is Reichenbach Sink.

**Reason.** The selection is a basin under a locked head, not a rename of the walk and not a rename of the page spill.

**Avoidances.** Do not rename the component to the walk. Do not rename it to the fall. Do not publish a second name for the same class.

**semantic-key:** PK-5815

## One row

The console list keeps one row for that sequence, and that row shows the current text.

The whole conversation stays listable. An edit does not add a second visible row for the same sequence.

**Truth.** One sequence is one list row, and the row shows the text the index now points at.

**Reason.** Two rows for one sequence would make the operator think the tape minted a new turn. The sequence did not change.

**Avoidances.** Do not list the superseded bytes as a second row. Do not hide the sequence after an edit.

**semantic-key:** PK-5816

## Pager

The paging mechanism leaves a designated head turn untouched.

Paging and spill have no authority to alter, move, or evict anything in that region.

**Truth.** A designated head turn is not altered, moved, or evicted by paging or spill.

**Reason.** The head is the frame. A pager that can rewrite it would move the standing rules without an operator action.

**Avoidances.** Do not evict a designated head turn under pressure. Do not let spill edit head text. Do not treat a page rotate as a change to head membership.

**semantic-key:** PK-5817

## Pointer

The return address of a turn is its sequence.

Each omitted block carries that return address so it can be brought back without reconstruction. The fall is reversible. The sequence does not change when the turn leaves the wire.

**Truth.** The return address is `seq`. Rehydration is by that sequence, not by rewriting the text.

**Reason.** A pointer index that is the sequence already exists. A second address would drift from the index row.

**Avoidances.** Do not invent a second pointer beside `seq`. Do not renumber a sequence when the turn leaves or returns to the wire. Do not reconstruct the text in order to show it.

**semantic-key:** PK-5818

## Reattach

Reattach restores the frames, and the side record restores the head.

Crash recovery restores the protected head from its durable form and reattaches in-flight pointers so the selection can resume or roll back cleanly. The frame walk does not by itself know which sequences were designated.

**Truth.** Frames come back from reattach. The head comes back from the side record.

**Reason.** The pack holds every frame, not the designation. A scan that guessed the head from the leading run would lock turns the operator had not locked.

**Avoidances.** Do not infer the designated head from the leading frames alone. Do not store the head only inside a frame field. Do not skip the side record on recovery.

**semantic-key:** PK-5819

## Reel

`message list` and `messages` still list every turn, and `message show <seq>` still opens any sequence.

The terminal can list the entire conversation and view any turn. The active window is what is transmitted. Sizing and membership are owned here, not by the provider.

**Truth.** List and show cover the whole tape. The sink does not narrow them to the wire.

**Reason.** The operator has to open a turn the provider will never see, including a turn about to be edited.

**Avoidances.** Do not filter `message list` down to the wire transcript. Do not refuse `message show` for a sequence the sink omitted.

**semantic-key:** PK-5820

## Sink

The Reichenbach Sink builds the transcript a provider receives.

The name is the component that manages which turns fit. It is the basin under the locked head. It is not a second store.

**Truth.** The provider transcript is what the sink builds.

**Reason.** The wire needs one named place that concatenates the head and the fitting tail, or every caller will clip on its own.

**Avoidances.** Do not leave the transcript as an unnamed slice of `windowIds`. Do not make the sink a second memory engine.

**semantic-key:** PK-5821

## Sketchpad

Opening a sequence is the roll of the tape, and a new turn appends at the live end.

The middle behaves as a roll that can move either way. What was before the opened sequence, and what is after it, stays on the tape and stays viewable. Adding still happens at the live end.

**Truth.** A show or a roll views a sequence in place. New content appends at the live end and does not insert into the middle.

**Reason.** Inserting into the middle would renumber sequences and break the return address. The roll is a view. The append is the write.

**Avoidances.** Do not insert a new turn in the middle of the tape. Do not drop the turns before or after the opened sequence. Do not reorder frames to match the view.

**semantic-key:** PK-5822

## Tail

The tail is the newest turns that still fit after the head, and a user line travels with its assistant reply.

The tail turns are the most relevant live edge. A pair stays together.

**Truth.** The tail is the newest fitting turns after the head, kept as whole user-and-reply atoms.

**Reason.** Splitting a reply from its user line sends a half turn. The newest edge is the live state.

**Avoidances.** Do not split a user line from its assistant reply. Do not fill the tail with older turns while newer atoms fit.

**semantic-key:** PK-5823

## Wire

The transcript on the wire is the locked head concatenated with the tail that fits.

Only the head region, and what can actually fit from the tail together with that head, is sent. Turns that fit in neither place are not on the wire.

**Truth.** The wire is head plus fitting tail, concatenated, and nothing else.

**Reason.** Sending the whole tape makes the provider the clipper. Sending a summary replaces turns the operator still owns.

**Avoidances.** Do not send the middle. Do not send a tail that does not fit. Do not replace the concatenation with a digest.

**semantic-key:** PK-5824

## 1. Brief

The serial stays the behavior, this page is the build sketch, and the code waits.

One class does one trim, on the host, with no second gearbox. The landing order is the proof list. The name stays `ReichenbachSink`. The lock is not a fifth letter on the card.

**Truth.** The sketch does not replace the serial. The type name is `ReichenbachSink`, and the lock is not another inclusion letter.

**Reason.** Two gearboxes would trim in two places and drift. A fifth letter would confuse the lock with Included, Excluded, Input only, and Output only.

**Avoidances.** Do not build from the sketch alone. Do not add a second trim outside the one class. Do not make the lock a fifth letter. Do not rename the class.

**semantic-key:** PK-5825

## 2. What this build is

One package-private class, `ReichenbachSink`, in `org.roux`, trims the host send, and it selects only when `managed-tokens` is set and a head is designated.

`ChatSession.buildPrompt` calls it for the terminal tape. `AmwpFacade` calls it on the assembled list before `holdRoux` and before the provider call. Both clients keep sending the working set they send today. The host trims. Otherwise the walk and the bars stay as they are today. The head on both doors is a packed prefix. Zero locks means that send has no head. A shorter prefix than `head-count` is still a head. The locked turns ride in ascending order after the system pin. The tail is the newest whole atoms that fit. The walk stops at the first atom that does not. The live line is charged before that tail, and it rides.

**Truth.** `ReichenbachSink` is package-private in `org.roux`. It selects only when the managed size is set and a head is designated, and the shape is pin, packed head, live line, then whole tail atoms.

**Reason.** The two callers do not share a walk today. One class on the host keeps both clients from trimming differently. Stopping at the first atom that does not fit keeps a pair intact.

**Avoidances.** Do not make the class public. Do not select when the size is unset or no head is designated. Do not put a hole in the packed prefix. Do not drop the live line. Do not include a partial tail atom.

**semantic-key:** PK-5826

## 3. What stays

`windowIds` keeps its signature and stays the walk when the sink is not selecting, the manifest stays 112 bytes, and the select path never punches a page.

`chat.max-messages` is passed only into that walk. `head.yaml` is not `clip.bin`. The clip-range floor is retired. That page stays the filed history. A build does not stack a floor sequence on the designated head. `holdRoux` keeps clearing only `user.aetheris`. The terminal region stays. The dead-page prune stays a plan. Compaction stays out. A middle sequence stays off the wire and stays listed. The other client does not gain the type and does not trim the send. Its inspector borrows the rule in a private function beside `paint_inspector`. The code note on that function says it borrows the idea from the Java class `ReichenbachSink`. This paper has no product landing.

**Truth.** The unselected walk, the 112-byte manifest, the terminal region, and the full list stay. The floor sequence is retired. Nothing in this sketch punches a page.

**Reason.** Growing the manifest, trimming on the client, or stacking a floor on the head would each add a second cursor. Punching a page is pack hygiene, not wire selection.

**Avoidances.** Do not change the `windowIds` signature. Do not pass `chat.max-messages` into the sink walk. Do not name the head file `clip.bin`. Do not grow the manifest. Do not punch a page on the select path. Do not clear the terminal region from `holdRoux`. Do not compact. Do not put the type on the other client.

**semantic-key:** PK-5827

## 4. The sketch

The sketch is twelve ordered pieces of work, and each piece points at a serial seat rather than inventing a new rule.

The session facts gain `managed-tokens` and `head-count` beside `num-ctx`. Absent or `0` means unset. Both decoders ignore a line they do not know. `head.yaml` is version 1, fields `count` and `seqs`, beside `manifest.bin`, written by a temporary file and a move. An empty list means no head. The list is the packed prefix. `ReichenbachSink` selects in this order: system pin, designated head ascending, live line, then whole tail atoms, and stops at the first atom that does not fit. The gate is a set size and a designated head, or the caller keeps today's walk. A designated head returns `replaceTranscript` before the clear. The sink never reads `moodPins`. The word `head` stays the splice seat. The inspector calls the same class. The heat bar has three bands. The numerator is the sink's sum while the sink is selecting. The other client's inspector paints from a private reading of the same rule, and that reading supplies the numerator while the sink is selecting. The send stays the working set. The canvas lock sits beside the letter. The vault stores it. Execute writes `head: true` for each locked cell already in the working set. Excluded stays out. `message lock <seq>` and `message unlock <seq>` change the prefix. `message list` and `messages` show the eligible rows and the locks. A miss returns a yellow error and changes nothing. `message edit <seq>` ends at `<.>`, appends a frame, moves slot `seq - 1`, leaves the lock, and does not send. The order is assemble, then the sink when it is selecting, then encode, then `AmwpCodec.fits`. A miss keeps the mood triple. The canvas sink drops omitted turns before `holdRoux`. The terminal sink is a read filter.

**Truth.** The twelve pieces are facts, head file, selection order, gate, mood, inspector bands, borrowed paint, vault lock, lock verbs, edit, channel order, and the two region roles.

**Reason.** Each piece is already a heading on the serial. The sketch only orders them so a later plan does not invent a second trim.

**Avoidances.** Do not add a thirteenth behavior in the sketch. Do not let a decoder fail on an unknown fact line. Do not read `moodPins` from the sink. Do not drop the mood triple to satisfy `AmwpCodec.fits`. Do not write an excluded turn as `head: true`.

**semantic-key:** PK-5828

## 5. Landing order

The plan cuts the proofs in this order, and each proof is a behavior the build has to show.

Unset size, or zero locks: the walk and the bars stay as they are today. Size set and no head: the denominator is `managed-tokens`, and the walk stays today's walk. The sink selecting: pin, packed head, live line, whole tail atoms, and the crossing atom stays off. Pin and head over the allowance: the historical tail is empty, the live line rides, and the console says the head is over the allowance. `message lock` and `message unlock` keep a packed prefix. A hole, a system turn, a missing sequence, and a sequence outside the eligible run return a yellow error and change nothing. `message edit` appends a frame, retargets slot `seq - 1`, and leaves the lock as it is. The prior bytes stay in the pack. A canvas lock is a packed prefix of eligible cards from the top of the deck. An excluded card stays out. A locked card spends one of the count. The inspector lists the sink transcript. `message list` still lists every stored turn. One tape: the other client's inspector rows match the host list, and the working set that leaves that client stays that working set. A frame that misses `AmwpCodec.fits` sets `too-large` and keeps the mood triple.

**Truth.** The proof order is unset, denominator-only, full selection, over-allowance, packed lock, edit, canvas prefix, inspector versus full list, one tape, and a channel miss that keeps the mood triple.

**Reason.** That order separates the gate from the selection, and the selection from the pack edit, so a failure points at one seat.

**Avoidances.** Do not prove the sink while the size is unset. Do not include the crossing atom while the sink is selecting. Do not change the pack on a yellow lock error. Do not drop the mood triple on `too-large`. Do not trim the working set that leaves the client.

**semantic-key:** PK-5829

## 6. One trim

One class does the trim, the other client only paints, and the files and the pack stay the sizes they are.

A settings key is not the managed size and is not the head count. `head.yaml` is not `clip.bin`. The lock is not a fifth letter. The manifest does not grow. The build does not add a public type. The build does not punch a page.

**Truth.** One host class trims. Paint is a borrowing. The manifest stays, the head file is not the clip file, and no page is punched.

**Reason.** A key in the client settings would diverge from the session facts. A public type would be a second door beside the engine. A punch would mix pack hygiene into the trim.

**Avoidances.** Do not trim in the painting client. Do not read the managed size from a dropped settings key. Do not grow the manifest. Do not add a public type. Do not punch a page. Do not make the lock a fifth letter.

**semantic-key:** PK-5830

## 7. Next

The formal plan is cut from this sketch and from the serial, the plan is stamped, and the build follows that stamp.

Until that stamp, the tree stays on today's `windowIds`.

**Truth.** No build starts from the pre-plan. Until the plan is stamped, `windowIds` remains the walk.

**Reason.** The sketch is an order of work. The serial is the behavior. Neither is the stamp that licenses source.

**Avoidances.** Do not implement while the plan is unstamped. Do not replace `windowIds` from this page alone.

**semantic-key:** PK-5831

## Reichenbach Sink pre-plan

This page is the build sketch for one package-private class, two doors, and one packed prefix, and it is not a license to write code.

The serial is the behavior. Where this page is thinner, the serial wins. The formal plan is not stamped. No program source is written. A plan waits until the plan cycle is named. Do not copy this page into a plans directory until that stamp. Do not implement from this page. The next step is the named plan cycle, then the stamp, then the build.

**Truth.** The pre-plan is a sketch of one class and two doors. The serial remains the behavior, and the tree stays on today's walk until a plan is stamped.

**Reason.** A sketch that is treated as a plan gets implemented before the headings are weighed. The serial already holds the behavior, so a thinner sketch must not override it.

**Avoidances.** Do not implement from this page. Do not copy it into a plans directory before the stamp. Do not treat it as a second memory engine, a crate, or a product landing.


**semantic-key:** PK-3459

## 0. What the first spill actually is

The happy path spills: a full page is written, unmapped, and a new hot page opens, the index stays in memory, and reads use a channel. That path is not yet a foundation to trust.

The flag is set before success. Rotation marks the memory header spilled, then waits for pins, then writes. A pin timeout or a write error leaves a hot page that says spilled, with the file missing, and the add aborted. There is no atomic file publish. The write truncates `{id}.pg` in place. A crash mid-write can leave a torn file with the same name as a good page. The hot page is memory-only until it is full. A crash mid-session loses the current page and the memory index. Spilled files may remain as orphans, and nothing reattaches them. There is no page-in. Once unmapped, a page never returns to an arena. Every later hit on an old turn pays a channel read and a heap copy. Identity is not on the frame, so spilled bytes are not self-describing as cluster-unique messages. A token window exists and is unused. The session still uses a message count only. One frame larger than `pageBytes - 64` fails. Tool payloads can be larger than a page. The paging work should close the first six tightly. The oversize case is an option, not a requirement of this honesty list.

**Truth.** The first spill works on the happy path and fails the trust test on flags-before-success, torn in-place files, a memory-only hot page, no page-in, no identity on the frame, and an unwired token window.

**Reason.** Those six are how a crash or a pin timeout loses or mislabels turns. The oversize frame is real and is deliberately not in the must-fix list.

**Avoidances.** Do not trust an in-place truncate of `{id}.pg` as publish. Do not treat a spilled flag written before the file exists as success. Do not assume a spilled page will return to an arena on this first path.

**semantic-key:** PK-5832

## 10. Config sketch

The sketch sets page size, resident cap, spill, and the chat window, and it is the shape to use when the slice is built.

```yaml
roux:
  page-bytes: 1048576
  resident-pages: 2
  spill:
    enabled: true
    dir: ${user.home}/.casserole/roux
  crypto:
    enabled: false

casserole.chat:
  max-messages: 40
  max-tokens: 0
```

**Truth.** Defaults in this sketch are page 1048576, resident 2, spill on under `${user.home}/.casserole/roux`, crypto off, `max-messages` 40, and `max-tokens` 0.

**Reason.** These match the locks: a 1 MiB page, a two-page working set, and a message window with the token cap off until set.

**Avoidances.** Do not enable crypto in this sketch. Do not set `max-tokens` to a positive default here. Do not point spill at a directory other than `${user.home}/.casserole/roux` unless the operator changes it.

**semantic-key:** PK-5833

## 11. Why this is a complete piece

After this slice, a live session can name every message in a cluster without hexadecimal or a node setting, find bytes in constant time whether the page is memory or disk, rotate without corrupting the hot page on failure, build a prompt from a correct window, and keep the tail in memory.

Resume, the console, and encryption are products on top of this log. They are not prerequisites.

**Truth.** The slice is enough for a live session's name, lookup, safe rotate, window, and resident tail.

**Reason.** Those five are what an agentic loop needs from the pager. Recovery products can sit on the same log later.

**Avoidances.** Do not block the slice on resume, the console, or encryption. Do not claim those products are included.

**semantic-key:** PK-5834

## 12. Locked decisions

The log is the source of truth. The index and residency are caches. Absolute persist, acknowledgement, and a cluster sync are a future layer, and the core must not block them.

The spill transaction is mandatory: temporary publish, force, rename, then unmap, with failure meaning no state change. The fast path is a reentrant lock per region, the pin table, and an atomic generator. There is no extra consensus. The working set is `roux.resident-pages`, default 2. Durability is `roux.durability`: `hot-only`, `commit`, or `append`. The default is `commit`. The window shoulds and coulds are in: dual cap, turn atom including the tool pair, page-aligned page-in, and `windowIds` may also expose the identity. Still no eviction on add, no summary, and no vectors. Large messages are rejection only, with a clear error and a yaml maximum. No continuation implementation. `FrameType.CONT` stays reserved. Identity lookup is in this slice: a memory map from the pair to `seq`. Continuation is not implemented. The shared identity is the layer point. Resume, encryption, the console, the collector, and mapped files are out.

**Truth.** Log wins. Spill is a forced publish before unmap. Resident default is 2. Durability default is `commit`. Oversize is rejected. `findById` is in. Continuation is not.

**Reason.** These locks keep one pager. A second consensus protocol, a second residency engine, or a continuation reader would each be another product.

**Avoidances.** Do not block the core on cluster acknowledgement. Do not change the durability default from `commit`. Do not implement continuation. Do not drop the memory identity map. Do not add eviction, summary, or a vector index.

**semantic-key:** PK-5835

## 13.1 Bytes a later recovery can scan

The page file and the hot checkpoint carry magic, version 2, page id, used bytes, CRC, and next page id. Each frame carries sequence, both identity longs, role, time, tokens, type, the payload, and the CRC.

The index is rebuildable: walk pages in page-id order, read frames, and insert sequence to pointer. Identity is on the frame, so a spilled page is self-describing even if the memory hash is gone. The session UUID still names the directory. This heading says hangup still deletes the region, which is the earlier product sentence. A later recovery layer is "do not delete, rebuild the index, and reopen." This slice does not open that door except by refusing to paint into a corner. The layer map on this page is the seat that wins where this sentence disagrees, and that map leaves close as an open weigh on the requirements page.

**Truth.** Version 2 headers and frames that carry sequence, identity, and CRC are the bytes a later scan needs. This heading's hangup sentence still says the region is deleted.

**Reason.** A scan cannot rebuild what the frame never stored. Deleting on hangup is the earlier product, and the page says the layer map wins when they disagree, so this sentence stays as worked reasoning rather than as a second close rule.

**Avoidances.** Do not omit identity or CRC from the frame. Do not build the recovery layer in this slice. Do not treat this heading's delete-on-hangup sentence as overriding the layer map.

**semantic-key:** PK-5836

## 13.2 Layer rows

Do not implement these rows now beyond what version 1 already includes. The table is the map the later sections name.

The live row is atomic spill, the working set, identity, and the window, with drop on hangup as this table first wrote it. The durable-hot row is the checkpoint of the hot page, still with no resume. Reattach, on open, rebuilds the index from the frames when restore is on. Resume session, later, maps the user or a named session to the region UUID and keeps files on hangup. Acknowledgement, later, waits for force and an optional replica on the same publish path. Continuation, later, is the same identity with type `CONT`. Dead-page prune, later, releases a spilled page that no live row names. The hot page stays. Page ids stay. The first two rows are the publish layer. The later rows assume frames already carry sequence, identity, and CRC.

**Truth.** The rows are live publish, durable hot, reattach on open, resume, acknowledgement, continuation, and dead-page prune. Only the first two belong to the version 1 build, and prune does not renumber them.

**Reason.** Naming the rows now stops a later slice from inventing a second publish path. The frame contents are the due diligence those rows assume.

**Avoidances.** Do not implement resume, acknowledgement, continuation, or prune in this slice. Do not give continuation a different identity. Do not slide page ids in the prune row. Do not invent a second publish path for acknowledgement.

**semantic-key:** PK-5837

## 13.3 Spill locking

A per-region reentrant lock covers append, rotate, clear, and free. Readers pin and copy and do not take the exclusive lock.

The id generator is an atomic last-millisecond plus a 12-bit counter, as the identity page specifies. Not in this slice: a stamped lock, striped locks, or group commit. Coherence beats cleverness. If the every-append checkpoint is chatty, one lock still serializes appends, which is already true: one model worker per session.

**Truth.** One reentrant lock per region for mutators. Readers pin. The generator is last-millisecond plus 12 bits. No group commit.

**Reason.** Striped locks and group commit add ways for two appends to publish one slot. One worker per session does not need them.

**Avoidances.** Do not add a stamped lock, striped locks, or group commit in this slice. Do not take the exclusive lock on a reader. Do not replace the 12-bit counter.

**semantic-key:** PK-5838

## 13. Recovery

This slice is functionally correct for a live session. A catastrophic acknowledgement on many disks is a later layer, not this slice.

The child headings say which bytes must exist now, which layers wait, and how locking stays boring.

**Truth.** Recovery is accounted now and built later. The live session does not wait on it.

**Reason.** Putting replica acknowledgement in the first pager would block append on a product that does not exist yet.

**Avoidances.** Do not implement multi-disk acknowledgement in this slice. Do not paint the bytes into a corner that a later scan cannot read.

**semantic-key:** PK-5839

## 14. Durability levels

One knob, `roux.durability`, defaults to `commit`. The three values share one checkpoint helper. The live helper writes the hot slot in `pages.bin`.

`hot-only` writes a page only when it rotates, and a process crash loses the whole hot page. `commit` writes after a successful user and assistant pair, and a crash loses only the in-flight turn, which is already not stored. `append` writes after every `appendMessage`, and a crash loses almost nothing on the hot page. This heading's sketch copies `usedBytes` to `{pageId}.hot.tmp`, forces, and renames to `{pageId}.hot`. The layer map replaces that sidecar with a write of the hot slot in the same `pages.bin`. The knob only changes when that write runs. `hot-only` never calls it during the session. `commit` calls it after both appends succeed. `append` calls it at the end of `appendMessage`. The helper plus a switch is small. It is not a second pager. Default is `commit`. The every-append value plus a large paste is a disk write per message. A development setting can use `hot-only`. An agentic setting uses `commit`. This slice does not reload the hot checkpoint on startup. Files may exist after a crash. Reattach later consumes them. This heading also says hangup deletes the region directory, including the sidecar and the per-page files. That delete sentence is the earlier sketch. The layer map is the seat where they disagree.

**Truth.** `roux.durability` is `hot-only`, `commit`, or `append`, default `commit`. All three use one hot-slot write in `pages.bin`. The page is not unmapped. Startup does not reload it.

**Reason.** Three policies and one helper avoid a second pager. Default `commit` matches "store a completed turn, not a cancelled one" without a write on every token of a paste.

**Avoidances.** Do not use a `{pageId}.hot` file once the pack slot is the checkpoint. Do not reload the hot slot on startup in this slice. Do not make `append` the default. Do not treat this heading's hangup-deletes sentence as the layer map.

**semantic-key:** PK-5840

## 15. Locked settings

The locked settings sketch fixes the window, the page, the resident cap, and durability together.

The chat block sets `max-messages` to 40, the prompt window as a non-system count, `max-tokens` to 0 so the token cap is off until set and otherwise shares the dual cap, `max-paste-chars` to 65536, and `escape-timeout-ms` to 100. The existing spinner and format keys stay unchanged. The store block sets `page-bytes` to 1048576, and a frame must fit in `page-bytes - 64`. `max-frame-bytes` 0 means `page-bytes - 64`. Otherwise the add rejects sooner. `resident-pages` is 2, the hot page plus the previous page-in. `durability` is `commit`. Spill is enabled. The directory is `${user.home}/.casserole/roux`. Crypto is disabled. Tune without code: a smaller `page-bytes`, for example 65536, forces spill; `resident-pages: 1` is the earlier residency; `durability: hot-only` is the bench setting; `max-tokens` exercises the dual window. The sketched system prompt text is "You are the Casserole assistant. Be concise and useful."

**Truth.** Locked defaults are `max-messages` 40, `max-tokens` 0, `max-paste-chars` 65536, `escape-timeout-ms` 100, `page-bytes` 1048576, `max-frame-bytes` 0, `resident-pages` 2, `durability` `commit`, spill on at `${user.home}/.casserole/roux`, crypto off.

**Reason.** One sketch is what play and production share so a tune is a value change rather than a code change. `max-frame-bytes` 0 has to mean the page minus the 64-byte header, not a zero-byte frame.

**Avoidances.** Do not treat `max-frame-bytes` 0 as a zero-length frame. Do not enable crypto in this sketch. Do not change spinner and format keys from this block. Do not require a code change to force a spill in a trial.

**semantic-key:** PK-5841

## 17. Gaps

Inspect uses the real sequence. Publish keeps memory if a rename fails after force. Identity compares unsigned. Spill off at the resident cap fails the rotate. A bad CRC does not crash the shell.

A filtered list versus sequence is documented in help. The publish move replaces the existing file. If the rename fails after force, keep memory and delete the temporary, the same as the spill transaction. Compare `idHi` as unsigned even though the top 48 time bits stay positive for a long time. The every-append checkpoint under the region lock is fine for one model thread per session. Do not invent group commit. If spill is off and the resident cap would be exceeded, fail the rotate with a clear error. Do not silently delete memory pages that have no file. The operator enables spill or raises `resident-pages` or `page-bytes`. The identity map dies with the region. There is no disk secondary index. A later rebuild scans frames. A duplicate identity, if the generator is sound, is ignored. Do not treat it as an update in place. The log is append-only. A turn atom with `max-tokens` 1 may exceed the budget. Include the whole turn even if it is slightly over the token cap. Never half a turn. The count cap treats the pair as one atom too. Page version 2 only. There is no in-session reader for version 1 frames. Mixed files after a crash are a later concern. This heading says a later layer skips an unknown version. A bad CRC on read skips the frame, increments `dirtyFrames`, does not crash the shell, and `message show` reports `crc: fail`. A paste is one add. The every-append checkpoint runs once. The oversize rule applies if the paste exceeds the max frame. `get()` for the list is every sequence. The prompt is the window. Inspect uses sequence, not window position.

**Truth.** Spill off plus a full resident cap fails the rotate. A CRC failure increments `dirtyFrames` and `message show` reports `crc: fail`. A pair is included whole even when `max-tokens` is 1. Version 2 is the only in-session page version.

**Reason.** Deleting a RAM page with no file loses turns that were never published. Crashing on one bad CRC takes down the session. Splitting a pair at a token count of 1 sends half a turn.

**Avoidances.** Do not delete RAM pages when spill is off. Do not crash on CRC failure. Do not update a frame in place on a duplicate identity. Do not half a turn at `max-tokens` 1. Do not read version 1 frames as version 2 in session. Do not add group commit.

**semantic-key:** PK-5842

## 18. Nice to have

A payload dump, a hexadecimal identity line, a status page table, and an explicit reindex are not this slice unless they are free.

`message toon <seq>` would dump the stored payload. Show could add a hexadecimal identity line. Status could list page id, used bytes, and hot or spilled. Rebuilding the index from per-page files on `message reindex` is the reattach row, not this slice.

**Truth.** Those four are optional and out unless they come free. Reindex is reattach.

**Reason.** Each one is operator convenience. None is required to append, rotate, or build a prompt.

**Avoidances.** Do not block the slice on a payload dump or a page table. Do not treat `message reindex` as part of publish.

**semantic-key:** PK-5843

## 19.1 When a file per page is fine

A live session at 1 MiB pages, with the directory deleted on hangup, is fine. About 1 KiB frames give about 1000 messages a page, and 100 spilled files are about 100 MB. Ordinary filesystems handle that. The directory problem shows up when pages are small, sessions run long, resume leaves thousands of regions, or a scanner stats every new file.

A 64 KiB page and 8 GiB of history is about 131000 files in one folder. Listing, opening, and scanning then dominate the path.

**Truth.** One file per 1 MiB page is fine for a short session. A 64 KiB page over 8 GiB is about 131000 files and is not fine.

**Reason.** The cost is dentries and scans, not the bytes of the frames. Hangup that deletes one folder hides the problem only while resume is absent.

**Avoidances.** Do not choose a 64 KiB page for a long resumed history and keep one file per page. Do not ignore directory scans as a cost.

**semantic-key:** PK-5844

## 19.2 What hurts

Opening a channel per spilled message, unpadded writes, in-place truncate, rewriting a hot sidecar on every append, and many tiny files hurt more than the mere count of files.

A read that opens a channel for every spilled message pays a lookup per window hit on a cold page. An unpadded `usedBytes` write makes the next page another inode and extra extents, so a sequential read of page N-1 then N is poor. An in-place truncate of `N.pg` is the torn file a crash leaves, already banned by a temporary plus rename. Rewriting a growing hot sidecar on every add amplifies writes and fragments that inode. Many tiny files cost an inode plus about 256 to 4096 bytes of directory entry each, which fills caches, slows listing, and invites a scan. A page in memory is a contiguous `pageBytes` range. One file per page does not place page 4 next to page 5 on disk.

**Truth.** The expensive choices are a channel per frame, unpadded extents, in-place truncate, a rewritten sidecar, and a dentry per page.

**Reason.** The memory page is contiguous. The filesystem will not keep `4.pg` and `5.pg` adjacent, so the working-set read is not two reads in one inode.

**Avoidances.** Do not open a channel per spilled message. Do not truncate a page file in place. Do not rewrite a sidecar on every append. Do not expect adjacent page files to be adjacent blocks.

**semantic-key:** PK-5845

## 19.3 Pack file

Keep the region directory `{spill.dir}/{regionUuid}/`. Change the payload from many files to one `pages.bin` whose slot `P` starts at `P * page-bytes`.

The file may be sparse. The first `usedBytes` of a slot are valid, taken from the header. The tail of the slot may be a hole. That is the page as a block range. A working-set read of the hot page and the previous page is two positioned reads in one inode, usually one or two extents when page ids are sequential, and they are. Publish still waits until the page is unpinned, writes `pages.bin` at `pageId * pageBytes` for bytes `[0, usedBytes)`, optionally pads to `align-bytes`, `force(true)`, and only then unmaps. A torn slot fails the header or payload CRC and is skipped. The log is the truth. There is no directory of 131000 entries. Renaming a whole 1 MiB sibling is unnecessary if the unmap waits until force returns. The same guarantee as a temporary plus rename holds, in one file. The checkpoint writes the hot slot in the same `pages.bin`. There is no extra hot inode. The durability knob only changes when that write runs. Page-in reads at `pageId * pageBytes` for `usedBytes` and copies into an arena. Cache the one channel on the region, not per page.

**Truth.** `pages.bin` is sparse. Slot `P` is at `P * page-bytes`. Publish is a positioned write of used bytes, optional align pad, `force(true)`, then unmap. One channel per region.

**Reason.** Sequential page ids in one inode are the contiguous range a file-per-page layout does not give. Waiting to unmap until force returns is the same crash rule as a rename, without a sibling file.

**Avoidances.** Do not unmap before `force(true)` returns. Do not open a channel per page. Do not add a hot sidecar inode. Do not treat a torn slot as a good page. Do not give up the region directory.

**semantic-key:** PK-5846

## 19.4 Hedges if page files stay

If the pack file were refused, keep per-page files and still align, cache a channel, and isolate by region.

Align spilled writes to `roux.spill.align-bytes`, default 4096. Cache the channel on the page, open on the first cold read, close when the region is freed. Never dump pages in the spill root. The region subdirectory stays. A later fan-out, only if resume meets tiny pages, would be `{region}/{pageId>>8}/{pageId}.pg`. Do not build that now. Fail the rotate if spill is off and `resident-pages` would overflow. That failure is already locked.

**Truth.** The hedges are align 4096, a cached channel, no files in the spill root, and no directory fan-out in this slice.

**Reason.** These are the cheap parts of the pack-file win if the one-file store is delayed. Fan-out is a resume problem and is not this slice.

**Avoidances.** Do not build directory fan-out now. Do not write page files into the spill root. Do not leave align unbounded. Do not rotate when spill is off and the resident cap would overflow.

**semantic-key:** PK-5847

## 19.5 Recommendation

Do the pack file in this slice, while nothing depends on per-page names. Keep the region directory. Align to 4096. Cache one channel. Do not shard directories.

Many `N.pg` files are not the long-term page store. `pages.bin` with slot `pageId * pageBytes` is the store. `align-bytes` is 4096. A cached channel is mandatory. Directory sharding is not this slice. This is still one pager, not a mapped-file primary. The operating system sees one growing sparse file per live session.

**Truth.** `pages.bin`, slot `pageId * pageBytes`, align 4096, one cached channel, region directory kept, no sharding.

**Reason.** Nothing depends on the per-page names yet, so the pack file is cheaper to adopt now than after resume has stored thousands of `N.pg` files.

**Avoidances.** Do not keep `N.pg` as the long-term store. Do not shard directories in this slice. Do not make a mapped file the primary pager. Do not drop the region directory.

**semantic-key:** PK-5848

## 19. Filesystem layout

The earlier on-disk shape is one file per spilled page, `{spill.dir}/{regionUuid}/{pageId}.pg`, plus a temporary and a hot sidecar. That is one inode per spilled page.

The child headings say when that is fine, what actually hurts, and the pack file that replaces it.

**Truth.** The earlier layout is one inode per page under the region directory. The recommendation that follows replaces the payload with one pack file.

**Reason.** A directory of page files isolates a region and makes hangup a directory delete, and it stops scaling when pages get small and sessions get long.

**Avoidances.** Do not put page files in the spill root. Do not treat one inode per page as the long-term store.

**semantic-key:** PK-5849

## 1. Picture

`seq` and the identity pair never change because a page moved. Only the segment versus the file changes.

The identity pair is never used to find bytes. The region id plus `seq` from 1 through N finds the index row, which holds page id and offset. That pointer stays stable across spill and page-in. A hot page is an arena segment. A spilled page is `{dir}/{region}/{pageId}.pg`. Page-in copies the file into an arena. The working set is the hot page plus recently touched spilled pages. The log is the truth. The index is a memory cache of pointers. Residency is a cache of pages.

**Truth.** Two keys stay stable across spill. The index is a cache of pointers. Residency is a cache of pages.

**Reason.** A fat pointer dies when the segment is unmapped. The sequence survives, which is why it fits a spill better than an address.

**Avoidances.** Do not find bytes with the identity pair. Do not change `seq` or the identity when a page moves. Do not treat the index as the durable log.

**semantic-key:** PK-5850

## 2. Identity

Mint the pair at append, write it on the frame and in the index, and bump the page version so a spilled file can rebuild an index later.

`RouxIdGenerator` mints at `appendMessage`, time-ordered bits, no hexadecimal, no UUID object. The index stride goes from 40 to 56. `PageHeader.VERSION` distinguishes sequence-only version 1 from sequence-plus-id version 2. Live regions already die on hangup, so there is no migration tool. `message list` still prints the sequence unless an optional id line is added later. Do not put the identity in the payload text. The payload stays `m[1]{r,ts,tok,x}:`. The bytes must allow a scan to read sequence and identity and rebuild an index. Doing that rebuild is not required here.

**Truth.** Identity is on the frame and in a 56-byte index row. The page version bumps. The payload text stays `m[1]{r,ts,tok,x}:`.

**Reason.** A spilled file that does not carry the identity cannot be reattached after the memory map is gone. Hangup already drops version 1 regions, so a migration tool is unnecessary.

**Avoidances.** Do not put the identity in the payload text. Do not keep stride 40 once the pair is stored. Do not require a migration tool. Do not print the identity in place of the sequence unless a later line is added.

**semantic-key:** PK-5851

## 3. Spill transaction

Rotate waits until the page is unpinned, publishes, forces, and only then unmaps. Failure leaves the old page hot and does not write the new message.

The order on this heading is: wait until unpinned, fail if crypto is enabled, write `{dir}/{region}/{pageId}.pg.tmp` with `usedBytes` and a correct header CRC, `force(true)`, best-effort directory sync, rename the temporary onto `{pageId}.pg`, optionally read back magic and CRC, set the file, unmap, then mark the page spilled, and open the new hot page. The spilled bit is set on the file copy, or in the memory header after a successful rename and immediately before unmap. It is never set if the page still has to stay hot on failure. On any failure: leave the old page hot, delete the temporary if present, return the error, and do not write the new message. The region stays consistent. The layer map on this page says this per-page temporary rename is the earlier picture, and version 1 publishes a slot in `pages.bin` instead. The transaction rule that still stands is the same: do not unmap until the publish has been forced, and do not mark spilled if the page must stay hot. `spill.enabled=false` skips the file. The old page stays in memory. A new hot page still opens, subject to the working-set cap.

**Truth.** Publish is forced before unmap. Failure leaves the old page hot and drops the new message. The live publish target is a `pages.bin` slot, not a renamed `{pageId}.pg`.

**Reason.** Flags before the write are how a hot page lies that it spilled. Unmap before force drops the only copy. The pack file keeps that transaction without a sibling file per page.

**Avoidances.** Do not set the spilled bit before the publish succeeds. Do not unmap on failure. Do not write the new message after a failed rotate. Do not keep the per-page rename as the long-term publish. Do not delete a RAM page when spill is off.

**semantic-key:** PK-5852

## 4. Working set

Each region keeps `roux.resident-pages` pages mapped, default 2: the hot page, optionally the last spilled page, and the rest on disk.

Page-in, when a read needs a spilled page and the resident count is under the cap, copies the file's used bytes into a new arena segment, sets the segment, and keeps the file as the source of truth. The copy is immutable. There is no dirty bit. Page-out unmaps the coldest non-hot resident. The file already exists. The hot page is never paged out. Cap 2 covers a window that crossed one page boundary. A long message list can stay on the channel, or page in one page at a time and drop it. There is no third engine. The same page object and the same index pointers are used. Default `resident-pages` is 2. `1` is the earlier hot-only residency. `0` is invalid.

**Truth.** Default resident cap is 2. Page-in copies used bytes into a new arena and keeps the file. The hot page is never paged out. Zero is invalid.

**Reason.** The prompt window is almost always the tail, which is the hot page, plus maybe the previous page when the window crosses a boundary. A general eviction product is more mechanism than that.

**Avoidances.** Do not page out the hot page. Do not treat the page-in copy as writable. Do not drop the file when the segment is restored. Do not accept `resident-pages` of 0. Do not add a third pager for the working set.

**semantic-key:** PK-5853

## 5. Hot-page durability

The locked choice is a checkpoint after a successful completion, and the live target of that checkpoint is the hot slot in `pages.bin`, which this heading's earlier sketch called a sidecar file.

No checkpoint loses the whole hot page on a crash. Checkpoint after a successful user-plus-assistant pair writes the hot used bytes once per completed turn, aligned with the existing commit gate. Checkpoint on every append is safer and slower, and too chatty for this choice. A mapped hot file makes the operating system the pager, which was asked not to be the primary design. The recommendation that stands is the commit checkpoint. A crash loses at most an in-flight call, which is already not stored. A completed turn is on disk as spilled pages plus the hot checkpoint. This heading's sketch names `{pageId}.hot.tmp` renamed to `{pageId}.hot`. The layer map says that sidecar is the earlier sketch, and version 1 checkpoints the hot slot in `pages.bin` and does not unmap it. On region start, a leftover sidecar may be ignored because resume is not this slice. Implementing the write without auto-resume is the foundation. Resume stays later. If the checkpoint feels extra, a rotate-only hot page plus a solid rotate is still a coherent smaller slice, and it is not the lock.

**Truth.** Durability locks to a checkpoint after a successful pair. The bytes go to the hot slot in `pages.bin`. The page stays mapped. Resume does not auto-load that slot.

**Reason.** Cancelled turns are already unstored. A completed pair should survive a process crash without making every append a disk write and without making a mapped file the pager.

**Avoidances.** Do not make a mapped file the hot pager. Do not unmap on the checkpoint. Do not auto-resume from the hot slot in this slice. Do not use a `{pageId}.hot` sidecar once the pack slot is the checkpoint. Do not checkpoint a cancelled turn.

**semantic-key:** PK-5854

## 6.1 Dual cap

The window is the pinned system messages plus the non-system tail until the message count or the token count fires. Zero tokens means the token cap is off.

```yaml
casserole.chat:
  max-messages: 40
  max-tokens: 0
```

The walk uses the stored heuristic token counts. It does not retokenize. The first limit that fires wins. `max-tokens` 0 means message cap only.

**Truth.** `max-messages` defaults to 40. `max-tokens` defaults to 0, which leaves the token cap off. Stored counts are used. The first limit wins.

**Reason.** Retokenizing on the walk repeats work the frame already stored. A default of 0 keeps today's message window until an operator sets a token cap.

**Avoidances.** Do not retokenize the window. Do not treat `max-tokens` 0 as a budget of zero tokens. Do not let the second limit keep walking after the first has fired.

**semantic-key:** PK-5855

## 6.2 Turn atom

Never split a user and assistant pair at the window edge. Take both or neither.

If the last non-system frame is tool or tool-call, pin it with the following assistant when that assistant is present. Tool results are the bytes that must not be half-evicted from context.

**Truth.** A user and assistant pair is one atom. A trailing tool or tool-call stays with the following assistant.

**Reason.** Half a turn is a prompt the model cannot answer from. Tool output is exactly the context that breaks when only the assistant remains.

**Avoidances.** Do not split the pair to honor a cap. Do not drop a tool frame that belongs to the following assistant. Do not treat each frame as an independent window unit.

**semantic-key:** PK-5856

## 6.3 Page-aligned hint

A window that would touch three or more pages is still allowed. Page-in only the newest spilled page, under the working-set cap. There is no new window math.

**Truth.** Correctness allows a window across many pages. Residency page-in stays the newest spilled page.

**Reason.** Refusing a wide window would hide turns that fit the caps. Page-in of every touched page would blow the resident cap.

**Avoidances.** Do not reject a window for touching three pages. Do not page in every touched page. Do not add window math for alignment.

**semantic-key:** PK-5857

## 6.4 Identity-stable cursor

`windowIds` returns sequences today. It could also return the identity pair for telemetry. That is not needed to assemble a prompt.

**Truth.** Prompt assembly uses sequences. Returning the identity pair is optional telemetry.

**Reason.** The prompt is built from roles and text addressed by sequence. The pair does not change which frames are chosen.

**Avoidances.** Do not require the identity pair to build a prompt. Do not replace the sequence array with the pair.

**semantic-key:** PK-5858

## 6.5 Not in this paging slice

Do not evict on add, do not summarize and drop, do not score by recency decay, and do not add a vector index.

Eviction on add is the window-memory behavior this store refuses. Summarize-and-drop is a later agent, not paging.

**Truth.** Those four are out of the paging slice.

**Reason.** Each one deletes or replaces history. Paging only changes residency and which tail is read.

**Avoidances.** Do not evict on add. Do not summarize and drop. Do not add recency scores. Do not add a vector index.

**semantic-key:** PK-5859

## 6. Window

The window already pins the last system message and a non-system tail under `max-messages`. The token walk is implemented and not yet wired. The additions are the child headings.

This heading does not add a fourth cap of its own.

**Truth.** The window work is a dual cap, a turn atom, and two optional hints, on top of the system pin and `max-messages`.

**Reason.** Wiring the token walk without an atom rule would split a user line from its reply at the budget edge.

**Avoidances.** Do not leave the token walk unwired if the dual cap is adopted. Do not invent a separate window engine.


**semantic-key:** PK-3461

## 7. Frames versus pages

An oversize frame is rejected. Continuation frames stay reserved and are not implemented.

Today one message must fit in `pageBytes - 64`. The locked choice is to keep the rejection, with a clear error and `roux.max-frame-bytes`. A one-off larger page is possible and messy. Continuation frames would share `seq` and the identity, use type `CONT`, chunk the payload, and concatenate on read. That is the real cross-boundary design and it is more code. The lock is rejection for this slice. Raise `page-bytes` if a tool dump is huge. The type byte stays reserved. The two-key identity is what makes continuation safe later, because chunks share one identity.

**Truth.** Oversize frames fail the add. `FrameType.CONT` stays reserved and unimplemented. `roux.max-frame-bytes` is the yaml ceiling.

**Reason.** A jumbo page complicates the slot size. Continuation is a second story beside a clear rejection, and the identity pair is already the hook that makes it safe later.

**Avoidances.** Do not implement continuation frames in this slice. Do not allocate a one-off larger page. Do not reuse the type byte for something else. Do not silently truncate an oversize frame.

**semantic-key:** PK-5860

## 8. Read path

The read walks the memory index by sequence, copies from a resident segment or page-in or a channel, checks the payload CRC, and decodes to a message.

If the working set has room, a cold page is paged in. Otherwise the channel reads it. The prompt still unmarshals. The tabular text is never placed in the message text.

**Truth.** Reads go index, then segment or page-in or channel, then CRC, then decode. The message text is the unmarshalled text.

**Reason.** The index is the pointer cache. Decoding only at the edge keeps the tabular form out of the prompt body.

**Avoidances.** Do not put the tabular payload in the message text. Do not skip the payload CRC. Do not scan the file when the index has the offset.

**semantic-key:** PK-5861

## 9. Must, should, and could

The foundation is identity, a transactional spill, CRC on disk reads, and an honest spill-off flag. The working set and the dual window should land. The commit checkpoint and the identity map land in version 1. Continuation waits.

Must: two-key identity on the frame and the index, page version 2, and the generator. Transactional spill is pin wait, then publish, then force, then unmap. Failure leaves the region as it was before the rotate. CRC is checked on disk reads, including the page header CRC after spill. `spill.enabled=false` does not lie about flags. The resident cap still applies. Should: `resident-pages` default 2, page-in of the last spilled page when the window crosses a boundary, the dual window with `max-tokens` 0 meaning off, and a turn-atomic tail that does not split user and assistant and that pins a trailing tool with its assistant. The commit checkpoint stays in version 1 under `durability: commit`. The identity map stays in version 1 as `region.byId`. Continuation waits. A chunked frame is a second story beside the oversize rejection. `FrameType.CONT` stays reserved. The requirements page is the write-up. Out of this slice: reconnect resume, encryption, copy-on-write snapshots, a console, a custom collector, a mapped-file primary pager, and an 8-byte node id instead of the two longs.

**Truth.** Version 1 includes the transactional spill, the resident cap, the dual window, `durability: commit`, and `region.byId`. Continuation is reserved, not built.

**Reason.** The checkpoint and the identity map fit the first version without a second pager. Continuation threatens that coherence and waits.

**Avoidances.** Do not build continuation in version 1. Do not drop the commit checkpoint out of version 1. Do not drop `region.byId`. Do not let spill-off pretend a page was published.

**semantic-key:** PK-5862

## Roux paging

This page is the paging design, not a plan and not code, and its job is a complete memory-to-disk page mechanic plus a few sharp edges on the prompt window.

The locked identity is two keys: `seq` as locator and `idHi` / `idLo` as the time-ordered pair. Decisions in the later sections of this page are locked. No plan and no code follow until asked. Where an older section disagrees with the layer map, the map wins, and the older sentences stay as the worked reasoning.

**Truth.** This page designs paging and the window edges. The layer map wins over an older section of the same page.

**Reason.** The page grew a per-file spill and a hot sidecar before the pack file was chosen. Leaving both as equal rules would build two stores.

**Avoidances.** Do not treat this page as a license to cut code. Do not let an older section override the layer map. Do not drop the two-key identity.

**semantic-key:** PK-5863

## 0. The batch

Manage the clip range in the store. Roll the head at 1 percent remaining or on a truncation error. Keep init messages. Leave rolled messages on the list. Use the pages already in the region.

Estimate the remaining tokens. At 1 percent remaining, or when a truncation-related error is thrown, roll the head. The head keeps only the messages that fit the chat window. The init messages stay. When the mood is on, the current mood is seated the way the shell already seats it. The roll is automatic. The server owns it. The session that owns the region is the session the roll coordinates with. A message that has left the head still appears on the message list. The operator can still show that message. The spool uses the pages already in the region. A new section is added only for the clip record the pages cannot hold. The chat window management already in the tree stays. The session mux already in the tree stays.

**Truth.** The batch is an automatic roll at 1 percent or on truncation, with init kept, the list intact, and a new clip record only where the pages cannot hold the head origin.

**Reason.** The pages already address every frame. A second copy of the tape would be a second region. The record that does not fit in a page header is the head origin.

**Avoidances.** Do not make the operator ask for the roll. Do not drop init messages. Do not remove a rolled message from the list. Do not replace the existing window or the mux.

**semantic-key:** PK-5864

## 10. Server and the session

The server runs the estimate, the roll, and the rewind on the connection that owns the region. The client does not keep a second copy of the head.

The place is the turn runner for that connection, and the command objects for `message rewind` and the status line. After a roll, the next prompt, the message list, and show on that connection read the same region. No new channel. No new push type. No new mux scope. One user's roll does not write another user's region.

**Truth.** One connection owns the roll. There is no second head on the client and no new channel.

**Reason.** A client-side head would drift from the region the next show reads. A new channel would be a second session for a window decision.

**Avoidances.** Do not keep a second head on the client. Do not add a channel or a push type. Do not let one user's roll write another user's region.

**semantic-key:** PK-5865

## 11. Implementation proposal

The later build, when a sentence names it, adds a clip holder, a floor overload, the estimate, a percent key defaulting to 1, the roll, rewind, and the status line. This filing does not do those steps.

The holder sits beside the manifest, in `org.roux.session`, file name `clip.bin`, layout as the clip record. Load on region open. Absent file and CRC miss mean no floor. This filing creates no public type. `windowIds` keeps its present signature and its present walk. An overload takes a floor sequence. Floor 0 delegates to the present method. The prompt passes a floor only when the clip record has an origin. The estimate uses stored counts first, and the heuristic when a message has no stored count. The chat settings gain `clip-remaining-percent`, default 1. 0 disables the percent trigger. The truncation trigger stays. The settings file stays unedited until a sentence names that edit. A missing key reads as 1. The turn estimates after the prompt is built and before the provider call. On a fire it rolls, rebuilds, and sends the rebuilt prompt. The catch classifies truncation, rolls, writes the error line, and returns. There is no second send. The roll publishes eligible hot pages when spill is on and the region is disk-backed. It writes `clip.bin` on that same path. It does not delete index rows. Rewind follows the rewind section. A successful complete clears bit 1 and sets the origin to 0. Status prints the `head:` line. The settings document gains the key on the turn that edits the settings. The design, the spill account, and the paging page stay as they are. Done means a disk-backed session whose prompt reaches 1 percent of the configured size rolls before the send. The provider receives the rebuilt head. Init stays. A mood that is on is on that prompt, seated by the existing unseat and seat. The message list still lists the reeled frames. Show still opens one of them. Rewind scopes the next prompt and a successful complete returns the head to auto. A truncation match rolls and writes the error the shell already writes. A region with no `clip.bin` prompts the way it does today. A second session on the mux is untouched. The push still receives the prompt the chat shell already built.

**Truth.** The proposal is `clip.bin`, a floor overload that keeps the old signature, `clip-remaining-percent` default 1, one roll before the send, and no new public type in this filing.

**Reason.** Floor 0 delegating to the present method keeps every current caller valid. Default 1 makes a missing key mean the batch's 1 percent rather than off.

**Avoidances.** Do not build these steps in this filing. Do not add a public type here. Do not edit the settings file here. Do not send twice. Do not delete index rows. Do not change the design, the spill account, or the paging page from this proposal.

**semantic-key:** PK-5866

## 12. Config sketch

The later build, not written on this filing, adds `clip-remaining-percent` defaulting to 1 beside the existing window keys.

```yaml
casserole:
  chat:
    max-messages: 40
    max-tokens: 0
    clip-remaining-percent: 1
```

`clip-remaining-percent: 0` leaves the percent trigger quiet. The truncation trigger stays. `max-tokens: 0` leaves the token cap off. The percent trigger then uses a positive local `num-ctx` when that provider is seated, and stays quiet for the other providers until `max-tokens` is positive or a truncation error arrives.

**Truth.** The sketch default is `clip-remaining-percent` 1, `max-messages` 40, and `max-tokens` 0. Zero percent quiets only the percent trigger.

**Reason.** The other providers have no `num-ctx`, so a zero token cap leaves them with no percent capacity until a truncation error or a positive `max-tokens`.

**Avoidances.** Do not write this block into the settings file on this filing. Do not let percent 0 disable the truncation trigger. Do not invent a `num-ctx` for providers that do not send one.

**semantic-key:** PK-5867

## 13. Depends

The clip depends on full history with the window at read, on page-full publish, and on the existing window, rotate, prompt, mood, init, list, show, and counter.

Evict-on-add remains a version 1 non-goal. The live publisher is the pack's page publish. The paging design is not this clip and is not treated as built. The local context size is the positive `num-ctx` on that call. The manifest stays the sibling file at size 112.

**Truth.** The clip sits on page-full publish and the existing window. It is not the paging slice, and the manifest stays 112 bytes.

**Reason.** The roll publishes through the pack that already exists. Treating the paging design as built would make this proposal depend on a slice this page says it does not build.

**Avoidances.** Do not depend on evict-on-add. Do not treat the paging design as built. Do not resize the manifest. Do not add a publisher beside the pack.

**semantic-key:** PK-5868

## 14. This filing

This filing adds the page and does not move product, does not edit the application settings, and does not build the roll.

The window stayed closed. The build of this page waits until it is named.

**Truth.** The filing is the proposal page. The roll is not built here.

**Reason.** A proposal that also edits settings and product would be the build the page says is waiting.

**Avoidances.** Do not build the roll from this filing. Do not edit the application settings from this filing. Do not open a live session to prove the page.

**semantic-key:** PK-5869

## 2. Beside the locked design

Full history stays in the region. The window stays at read. `get()` with no limit stays the whole region. Evict-on-add stays a non-goal. Spill-on-page-full stays the residency rule.

The page-full account stays the page-full account. The paging design stays the paging design. Its window lines stay recommendations. This page does not build that paging slice and does not treat it as built. The clip is a later layer on that lock. The head is which frames the next prompt includes. The reel is the frames the head has passed. Those frames stay indexed, listed, and showable. The roll does not delete a row, does not renumber a sequence, and does not clear the region.

**Truth.** The clip is a later read-head on the locked full-history store. The roll does not delete, renumber, or clear.

**Reason.** Evict-on-add and a clear would destroy the reel the operator is supposed to rewind.

**Avoidances.** Do not evict on add. Do not renumber sequences. Do not clear the region on a roll. Do not treat the paging design as already built by this page.

**semantic-key:** PK-5870

## 3. Preserve

With or without a clip record, the no-floor walk, the live user line, the mood writers, the init writer, append-after-success, the full list, show-as-inspect, the page-full rotate, and the 112-byte manifest stay.

`windowIds` with no floor walks as the as-built section describes. The atom that crosses `max-tokens` is still included, then the walk stops. Pinned system rows stay pinned. The prompt still appends the live user line, unseats stored mood pairs, and reseats the in-memory mood when it is set. The mood setter and the composer stay the only mood writers. The system-prompt writer stays the init writer. Append stays after a successful complete. The list stays the full region. The terse display cap stays `list-preview-chars`. Show stays an inspect and does not move the head. Rotate stays the page-full rotate. The pack publish stays the publisher. `manifest.bin` stays 112 bytes. The index stride stays 56. The page header stays 64. The session loop, the busy line, interrupt, the spinner, the reply box, the command-line history file, and the grid stay. A roll on one region leaves every other region alone, including `{user}.aetheris`. The heuristic counter stays the counter. A real tokenizer stays out. `roux.spill.enabled=false` still creates no pack.

**Truth.** A clip record does not change the no-floor walk, the sizes 112, 56, and 64, or the rule that spill off creates no pack.

**Reason.** The clip is an origin on the existing walk. Changing those sizes or writers would be a different layer.

**Avoidances.** Do not let show move the head. Do not let a roll touch `{user}.aetheris`. Do not create a pack when spill is off. Do not add a second mood writer. Do not change stride 56 or header 64.

**semantic-key:** PK-5871

## 4. The tape

The tape is every message frame in sequence order. The head is what the next prompt sends. The reel is every frame behind the head origin. The origin is a sequence, and 0 means no floor.

The head is init, the mood pair when the mood is on, the tail that fits, and the live user line. The reel stays indexed, listed, and showable. Frames at or after the origin are eligible for the tail. The leading system run stays on the head even when its sequence is behind the origin. Origin 0 means no floor, and the read is the as-built walk. Rewind is an operator move of that origin.

**Truth.** Head origin is a sequence. Zero means the as-built walk. System frames stay on the head even when they sit behind the origin.

**Reason.** The init frame is the standing prompt. A floor that also dropped it would send a conversation with no system frame whenever the operator rewound past it.

**Avoidances.** Do not drop the leading system run because its sequence is behind the origin. Do not treat origin 0 as sequence 0 of the tape. Do not remove reeled frames from the index.

**semantic-key:** PK-5872

## 5. Triggers

Both triggers are automatic. The percent fires when remaining times 100 is at most the capacity. The truncation trigger matches a provider message and still rolls when the percent key is 0.

When `casserole.chat.max-tokens` is positive and the seated provider is the local model with a positive `num-ctx`, capacity is the smaller of the two. When only one is positive, capacity is that one. The other providers use `max-tokens` only. When neither integer is positive, the percent trigger stays quiet for that turn. `max-messages` still caps the head through the existing walk. The truncation trigger still runs. Estimate sums the stored token count of each prompt message that has one. A message with no stored count, including the live user line and a mood line seated only on the prompt list, uses the heuristic counter. Remaining is capacity minus estimate. When the estimate is at least the capacity, remaining is 0. The percent trigger fires when capacity is positive and `remaining * 100 <= capacity`. The arithmetic is integer. At exactly 1 percent it fires. Above 1 percent it does not. The turn builds the prompt as today, then estimates. On a fire it rolls, rebuilds the prompt from the new head, and sends the rebuilt prompt. One roll before the call. The pre-roll prompt is not sent. In the existing catch, before the error line is written, the message is classified. A provider message matches when its lowercased text contains `context length`, `truncat`, `maximum context`, or `num_ctx`, and does not contain `AMWP` or `paste exceeds`. A match rolls, then the existing error line is written, then the prompt returns. The failed turn stays unstored. The turn is not sent again. The next turn uses the rolled head. A match still rolls when the percent key is 0. Display cuts, paste caps, and short reads of that other channel are not this trigger. The roll drops older tail atoms until remaining is above 1 percent, or until the tail is the newest atom. It does not drop the leading system run. It does not drop the mood pair when the mood is on. It does not split an atom. It does not drop the live user line. When the head cannot shrink further and remaining is still at or under 1 percent, that prompt is the one that is sent. The truncation path is the backstop.

**Truth.** Capacity is the smaller positive of `max-tokens` and local `num-ctx`, or the one that is positive. The percent fires on integer `remaining * 100 <= capacity`. Truncation matches those four substrings and excludes `AMWP` and `paste exceeds`. One roll, then the rebuilt prompt. The newest atom, the system run, the mood pair, and the live line are not dropped.

**Reason.** Integer percent avoids a floating comparison at the exact 1 percent edge. Excluding paste and the other channel keeps a local limit from looking like a provider truncation. Sending the pre-roll prompt would deliver the size that just fired.

**Avoidances.** Do not fire the percent when both capacity integers are non-positive. Do not use floating division for the 1 percent test. Do not send the pre-roll prompt. Do not resend the failed turn. Do not classify paste or the other channel as truncation. Do not split an atom or drop the live line to get under 1 percent. Do not quiet the truncation trigger when the percent key is 0.

**semantic-key:** PK-5873

## 6. The roll

On a fire, the oldest tail sequence that still satisfies the floor becomes the head origin, eligible hot pages are published, and the prompt is rebuilt with that floor.

Take the prompt the as-built path already builds. Estimate it against the capacity. On a fire, choose the oldest tail sequence that still satisfies the floor of the roll. That sequence is the new head origin. Origin 0 when the existing walk already fits. Leave every index row in place. When spill is enabled and the region is disk-backed, each hot page whose last message id is behind the origin, and which holds no leading-system frame and no seated mood frame, is published through the pack. A page that mixes a head frame and a reel frame stays in the residency it already has. A page already published stays published. When spill is off, or the region is RAM-only, the origin still clips the next prompt. No pack is created. The hot segments stay hot until the existing resident-page rule says otherwise. Write the clip record. Rebuild the prompt with that origin as the floor. The prompt still appends the live user line and still runs the mood unseat and seat. A roll does not call the mood setter. The mood pair stays where that setter put it. The prompt still reseats the in-memory line, so a mood that is on is on the prompt the provider sees.

**Truth.** The roll sets an origin, publishes only pure reel hot pages when spill is on, writes the clip record, and rebuilds the prompt. It does not call the mood setter and does not create a pack when spill is off.

**Reason.** A mixed page still holds a head frame, so publishing it as if it were reel would unmap the head. Calling the mood setter would rewrite the region the roll is only trying to window.

**Avoidances.** Do not delete index rows. Do not publish a page that mixes head and reel. Do not create a pack for a RAM-only region or when spill is off. Do not call the mood setter from the roll. Do not send the prompt from before the rebuild.

**semantic-key:** PK-5874

## 7. Rewind

`message rewind <seq>` moves the head origin to that sequence. `message rewind` with no sequence returns the origin to 0. A successful complete returns the head to auto.

The sequence must exist and must sit outside the leading system run. The next prompt pins that run, seats the mood the way the prompt already seats it, and takes atoms from that sequence forward under `max-messages`, `max-tokens`, and the percent floor. A span that is too long keeps the newest atoms that fit at or after the sequence. Rewind does not copy bytes into a second region. A published page is read by the path the frame read already uses. A bare rewind sets origin 0, and the next prompt is the as-built walk. A successful complete returns the head to auto. The new turn is stored as today. The operator can rewind again. A failed turn does not clear a rewind that was set and not yet used. Show stays the way to read one frame the head is not sending. Showing a frame does not move the origin. Status gains one line, `head:`, with the origin, `auto` or `rewind`, the capacity, the estimate, and the remaining count. The lines status already prints stay.

**Truth.** `message rewind <seq>` sets the origin. A bare rewind clears it to 0. Success returns to auto. Failure keeps an unused rewind. Status prints `head:`.

**Reason.** Rewind is a view of bytes already in the pack. Copying them to a second region would fork the tape. Clearing a rewind on failure would drop an operator move that never got a turn.

**Avoidances.** Do not rewind onto the leading system run. Do not copy bytes into a second region. Do not move the origin from show. Do not clear an unused rewind on a failed turn. Do not drop the existing status lines.

**semantic-key:** PK-5875

## 9. The clip record

The page header, the frame header, and the index stride cannot hold a head origin without changing packs that already load. The clip record is `clip.bin`, 64 bytes, beside `manifest.bin` and `pages.bin`.

Offset 0 is magic `C L I P`. Offset 4 is version, a short, `1`. Offset 6 is size, a short, `64`. Offset 8 is flags, an int. Bit 0 set means the record is live. Bit 1 set means rewind. Clear bit 1 means auto. Offset 12 is the head origin, a long. `0` means no floor. Offset 20 is capacity, an int, `0` when the percent trigger had no capacity. Offset 24 is the estimate, an int. Offset 28 is remaining, an int. Offset 32 is the roll epoch, a long, milliseconds. Offset 40 is CRC32C over bytes `[0, 40)`. Offset 44 pads through 63. An absent file means no clip. The session uses the as-built walk. A CRC miss leaves the record unread and the session on that same walk. The write uses a temporary file and a move, the same discipline the manifest uses, so a crash leaves the previous record. RAM-only regions keep the same fields on the in-memory region for the life of the shell. Hangup drops them with the region. No `clip.bin` is written.

**Truth.** `clip.bin` is 64 bytes, magic `CLIP`, version 1, CRC32C over `[0, 40)`, origin 0 meaning no floor. Absent or a bad CRC means the as-built walk. RAM does not write the file.

**Reason.** Stuffing the origin into the 64-byte page header or the 56-byte stride would change every pack already on disk. A sibling file can be absent, which is the no-clip state.

**Avoidances.** Do not grow the page header, the frame header, or the index stride for the origin. Do not treat a CRC miss as a live clip. Do not write `clip.bin` for a RAM-only region. Do not rewrite `clip.bin` in place without the temporary file and the move.

**semantic-key:** PK-5876

## Roux clip range

The runtime chat window is a tape head. It reads only the span that fits. The tape that has passed the head stays on the reel, and the whole tape stays on the message list.

This page is a proposal beside the locked version 1 design. It does not rewrite that design. `message show` still opens one frame, including a frame the head is not reading. The build waits until a later sentence names it.

**Truth.** The clip is a read head over a full tape. The list still shows every frame. This page does not rewrite version 1.

**Reason.** The operator has to see and open turns the provider is not being sent. Deleting them would make the window a compaction.

**Avoidances.** Do not rewrite the locked design from this page. Do not build until the page is named. Do not hide a reeled frame from the list.

**semantic-key:** PK-5877

## 1. Where it sits

Dead-page release is the next row after continuation frames, and the rows already on the map keep their numbers and their meaning.

The sink stays the wire. This page is the pack. Orphans appear when an edit retargets a sequence number onto a later frame. Until that edit exists, a sweep finds nothing to release and returns. Resume, acknowledgement, and continuation do not block this row.

**Truth.** The prune is pack housekeeping after continuation frames. It waits on edit retargets and does not move the wire.

**Reason.** Without a retarget, every stored frame is still named by a live row, so a sweep has nothing honest to punch.

**Avoidances.** Do not renumber the earlier rows. Do not make the sink wait on this sweep. Do not release slots before an edit can orphan them.

**semantic-key:** PK-5878

## 2. What is dead

A page is dead only when every live index row names some other page id, and one live frame keeps the whole slot.

The slot is `page-bytes`. The address stays `pageId * page-bytes`. The hot page is the write cursor. The latest edit sits at its tail, so that page stays mixed until a later page holds every live row. The sweep leaves the hot page in place. A pinned page waits, using the same pin wait a hot rotation already uses, and a page that is still pinned is left for the next pass. A region with no pack is skipped. That is a RAM-only region, and a region whose spill is off.

**Truth.** Dead means no live row names the page id. The hot page, a pinned page, and a region with no pack are not punched.

**Reason.** One live frame still needs its slot at `pageId * page-bytes`. Punching the hot page would punch the write cursor. Punching a pinned page would unmap a reader. A region with no pack has no slot to return.

**Avoidances.** Do not punch a slot that any live row still names. Do not punch the hot page. Do not punch a pinned page. Do not sweep a RAM-only region or a region with spill off. Do not renumber page ids.

**semantic-key:** PK-5879

## 3. What the punch does

`RegionPack.releaseSlot` returns a dead slot as disk space without sliding later pages down.

A dead run at the tail of `pages.bin` is `FileChannel.truncate` back to the first page that is still live or still hot, so the length an operator sees comes back. A dead slot in the middle is a sparse hole over `[slotOffset, slotOffset + page-bytes)`. The file is marked sparse on the first hole. The clusters go back to the volume. The logical length stays, and the later page ids stay put. Size on disk is the number an operator can check. A sweep that slides later pages down to close the gap is a different plan: page ids would move, and every index row would move with them. This layer does not do that. After a release, the slot reads as zeros. The `ROUX` magic is gone. The next sweep sees the missing magic and leaves the slot alone.

**Truth.** A dead tail is truncated. A dead middle slot becomes a sparse hole of `page-bytes`. Page ids do not slide, and a released slot loses the `ROUX` magic.

**Reason.** Sliding pages would rewrite every later index row. A hole keeps the address `pageId * page-bytes` stable. A missing magic tells the next sweep the id is already released.

**Avoidances.** Do not slide later pages to close a gap. Do not renumber page ids. Do not refill a slot whose `ROUX` magic is gone. Do not treat logical length as size on disk after a hole.

**semantic-key:** PK-5880

## 4. Order

The superseding frame is already forced before the old slot is released.

A crash between those two steps would otherwise lose the only remaining copy of that sequence. When this layer is built, the reattach walk skips a slot whose header is not the `ROUX` magic. When an old frame and a newer frame still share a sequence, the later frame wins. After the punch, only the later frame remains. Until this layer is cut, reattach stays the walk that is filed. The manifest stays 112 bytes. Deadness is a scan of the index. There is no free-slot table.

**Truth.** Force the new frame first, then release the old slot. The manifest stays 112 bytes, and deadness is an index scan.

**Reason.** Releasing first can drop the only copy if the new frame is not durable yet. A free-slot table would be a second source of truth beside the index and the magic.

**Avoidances.** Do not release a slot before the superseding frame is forced. Do not grow the manifest to record free slots. Do not let an older frame win when it shares a sequence with a later frame. Do not change the filed reattach walk before this layer is cut.

**semantic-key:** PK-5881

## 6. Lifecycle

`DeadPagePrune` in `org.roux` is a context-lifecycle bean with one thread, and it is not a scheduler framework.

It starts with the context and stops with the context. It does not take a pool. The thread runs one sweep of every open region when the context has started. It runs one sweep of a region when that region opens. It then sleeps for `roux.prune.interval` and sweeps the open regions again. `roux.prune.enabled` defaults to true. `roux.prune.interval` defaults to `20m`. `5m` is a legal setting of the same key. A value below one minute is refused, and the bean does not start. When the interval elapses during a sweep, that tick waits. The thread does not run two sweeps at once. On stop, the thread finishes the slot already in hand and does not start another slot. The keys live on `RouxProperties`, in a nested `Prune` block beside `Spill` and `Session`:

```yaml
roux:
  prune:
    enabled: true
    interval: 20m
```

**Truth.** The prune bean is one thread on the context. It sweeps at start, on region open, and every `roux.prune.interval`, default `20m`, enabled by default.

**Reason.** A scheduler framework would be a second clock beside a bean that already owns one thread. A floor of one minute keeps a legal `5m` and refuses a tighter loop. Finishing the slot in hand on stop avoids a half-released punch.

**Avoidances.** Do not add a scheduler framework or a pool. Do not accept an interval below one minute. Do not run two sweeps at once. Do not abandon the slot already in hand when the context stops. Do not nest the keys anywhere but `Prune` beside `Spill` and `Session`.

**semantic-key:** PK-5882

## 7. What this layer leaves alone

Version 1 still spills forward when the hot page cannot hold the next frame, and this layer does not replace that spill.

The index still finds a live frame. `message list` and `message show` still follow the current row. The sink still chooses the wire. Compaction, evict-on-add, and summarize-and-drop stay outside this layer.

**Truth.** The prune does not change forward spill, live reads, the list, the show path, or the wire selection.

**Reason.** Dead-page release is hygiene of slots no row names. The motions that append, display, and select are already specified elsewhere and do not need a punch to stay correct.

**Avoidances.** Do not compact, evict on add, or summarize and drop inside this layer. Do not make list or show follow a punched offset. Do not let the sweep choose the wire.

**semantic-key:** PK-5883

## Roux dead-page prune

A page that no live index row names is punched out so the disk returns to other use, while the pack keeps spilling forward.

This is a plan, not a build. It sits on the paging specification. It does not rewrite version 1, reattach, or the sink draft. No product source is cut with this page. The sweep is the housekeeping that follows behind the spill.

**Truth.** A fully dead page is punched out and the disk is freed. The pack still spills forward.

**Reason.** Superseded bytes that no live row names occupy a whole slot and never come back into a read. Leaving them forever grows the file for no indexed frame.

**Avoidances.** Do not rewrite version 1, reattach, or the sink in order to punch a page. Do not treat this page as a license to build before it is named.

**semantic-key:** PK-5884

## 0.1 Payload in the prompt

Store the tabular payload in the frames, and unmarshal at the Spring and host edge. Do not put that payload in `ChatMemory.get()` text.

Off-heap and the spill file store the payload inside length-prefixed frames, for density, fixed pages, and the monotonic index. `message list` and `status` unmarshal to the original role and text, because an operator must read chat rather than the notation. `ChatMemory.get()` and the prompt unmarshal to system, user, and assistant messages, because that is the drop-in contract. A later compact prompt may send one table of prior turns as a single block plus the latest utterance as plain text. Token savings live there, not in per-message notation stuffed into role-separated messages.

Feeding the notation as each message body in version 1 is refused. Drop-in `get()` returns semantically typed messages, and notation in the text breaks every orchestrator. Paying both role wrappers and the notation is worse than either. Models vary. A table is good as a block of history, not as a fake assistant turn. Unmarshalling at prompt time is cheap next to the model call. The storage win is pages, index, and spill, not prompt microseconds.

Later, not version 1, a setting `casserole.chat.prompt-encoding` may be `messages` or `toon`. The table mode would inject one compact block. The codec is designed so that path is a config switch, not a layout change. Listing always unmarshals.

**Truth.** Frames store the tabular payload. `get()`, the prompt, and the list unmarshal to role and text. A compact table prompt is a later config switch, not a layout change.

**Reason.** Notation inside `getText()` breaks the drop-in message contract and pays for syntax twice. The density win is on disk and in the index.

**Avoidances.** Do not put the tabular text in `ChatMemory.get()` text in version 1. Do not change the frame layout to add the later compact prompt. Do not list the raw notation to the operator.

**semantic-key:** PK-5885

## 0.2 Tiers and spill

Version 1 has one page format and two residencies, hot memory and a spilled file. Seal is an operation, not a third engine.

There is no mapped file as the primary pager, no least-recently-used product, no encryption, and no copy-on-write. The hot page lives in a foreign-memory arena segment. When the next frame does not fit, the page is checksummed, the used bytes are written to `{roux.data-dir}/{regionId}/{pageId}.pg`, that memory page is unmapped, and a new hot page opens. The cursor reads the segment when the page id is in memory, and otherwise reads a channel at the index offset and length. There is no list and no mapped file on that read. The monotonic index stays memory-resident. If spill I/O is noisy during first wiring, `roux.spill.enabled=false` keeps every page hot. The cursor still goes through the page store, so enabling spill does not rewrite callers. Encryption, snapshot, and restore remain stubs.

**Truth.** Two residencies, one format. Spill writes `{roux.data-dir}/{regionId}/{pageId}.pg` and unmaps. Spill off keeps every page hot through the same page store.

**Reason.** A third engine for seal, encryption, or copy-on-write would be a second pager. The flag keeps the first wiring in memory without a second code path.

**Avoidances.** Do not add a mapped-file pager, a least-recently-used product, encryption, or copy-on-write in version 1. Do not bypass the page store when spill is off. Do not leave the index on disk.

**semantic-key:** PK-5886

## 0.3 Conversation id

The conversation id is a session key only. It names the region for this connection. It is not stored on frames or in the payload, and it is not a message field.

Disk-backed, the region id is the sanitized username. A UUID lives in `manifest.bin` for status as `region-uuid:`. Hangup keeps `{spill.dir}/{user}/`. RAM-only, which is `hot-only` or spill off, mints a UUID per shell. Hangup unmaps memory and does not attach or write the user pack. Status prints `conversation:` as the region id and `ssh-user:`. Disk-backed also prints `region-uuid:`. `message list` is session-implicit. There is no UUID to type and no per-message mapping.

**Truth.** The conversation id names the region and is absent from frames and from the payload. Disk-backed hangup keeps `{spill.dir}/{user}/`. RAM-only hangup unmaps and does not touch the pack.

**Reason.** Storing the id on every frame would duplicate the directory key and would make a region rename a rewrite. The manifest UUID is display, not the lookup.

**Avoidances.** Do not store the conversation id on a frame or in the payload. Do not delete `{spill.dir}/{user}/` on a disk-backed hangup. Do not attach the user pack from a RAM-only shell. Do not require a UUID on `message list`.

**semantic-key:** PK-5887

## 0. Locked product calls

Three product calls are closed for this iteration: where the tabular payload is decoded, how many residencies exist, and what the conversation key is.

Each call has its own rule. This heading only records that the iteration is closed.

**Truth.** The three calls are closed. Their rules are the child headings.

**Reason.** Leaving them open would let a build pick a prompt encoding, a third residency, or a message-scoped conversation id that this iteration already refused.

**Avoidances.** Do not reopen the three calls inside version 1. Do not add a fourth product call to this closed set.

**semantic-key:** PK-5888

## 10. Host integration

The session keeps a conversation id, an active flag, the adapter, and the system prompt text, which is also the first system frame on create or reset.

A new session allocates a region and adds the system message. Building a prompt takes the window and does not store the new user line yet. User and assistant append only after success. Interrupt and error do not add. Reset and start clear and add the system message. Clearing turns does the same. Stop sets active false only. The message list is `get`. Counts use the role count. Destroying the session persist-closes and keeps the user directory when persist is on. The list command gets the region, filters by role, and prints unmarshalled text. Status prints `conversation:` as the username or the ephemeral UUID, optional `region-uuid:`, `ssh-user:`, and the sequence. The prompt is still the messages plus the current user message. Settings are page bytes 1048576, spill enabled, directory `${user.home}/.casserole/roux`, crypto disabled, and the token counter `heuristic`. `casserole.chat.max-messages` remains the read window for prompts. The implementation slice sets the language level to 25.

**Truth.** The session is a thin holder. Append is after success. Hangup persist-closes and keeps the user directory when persist is on. The read window stays `casserole.chat.max-messages`.

**Reason.** The engine should not learn prompt policy. The session already knows success, cancel, and the window size.

**Avoidances.** Do not store the in-flight user line before success. Do not add on interrupt or error. Do not delete the user directory when persist is on. Do not move `max-messages` into a write-time eviction.

**semantic-key:** PK-5889

## 1.1 Goals

Version 1 is an off-heap paged store with append-only frames, a monotonic id-to-offset index, a chat-memory adapter, and spill to a cleartext file.

There is no vector database and no embeddings. The payload encoding inside frames is the tabular form, structured and page-bounded. The adapter implements add, get, and clear on a message list. Marshalling is the codec plus the mapper only, not a message subtype. The host session's list is replaced by this store. Full history stays in the region. The window is at read. List, clear, start, and status counts work against the store. Direct engine surfaces cover inspect, layout, state, and lifecycle. A console binds later. Allocation is honest: off-heap pages and index, heap only at the contract edges. Concurrent readers and one writer do not tear a frame, using a sequence lock and a page generation. A stale or dead-log address fails cleanly. Spill is cleartext. Encryption is off. Frame types for policy, telemetry, and access control are reserved and not implemented. Telemetry is primitive counters. A custom collector is not in version 1.

**Truth.** Version 1 stores full history off heap, windows at read, spills cleartext, and counts with primitive counters.

**Reason.** Those are the motions a chat session needs from a pager without pulling a collector, a policy engine, or a console into the first cut.

**Avoidances.** Do not add embeddings. Do not evict on add. Do not encrypt the spill. Do not implement the reserved frame types. Do not put a custom collector in version 1.

**semantic-key:** PK-5890

## 11. Tokenizer

Version 1 counts tokens with `max(1, (text.length() + 3) / 4)` and stores that integer at write.

`TokenCounter.count` is that function. Budget walks use the stored integers. A real tokenizer dependency is not in version 1. The counter interface allows adding one later. The orientation is the local model.

**Truth.** The count is `max(1, (text.length() + 3) / 4)`, stored on the frame and the index.

**Reason.** The budget must not retokenize on every window walk. A minimum of 1 keeps an empty body from looking free.

**Avoidances.** Do not add a tokenizer library in version 1. Do not recompute the count on every read. Do not return 0 for empty text.

**semantic-key:** PK-5891

## 1.2 Non-goals

Version 1 does not build a vector index, a console, a custom collector, a policy engine, named sessions, a compact prompt by default, unsafe or preview memory, chat behavior inside the engine, eviction on add, or a separate build tool.

The restore slice covers happy-path persist by username. That is not a user-admin product. Evict-on-add, the kind that deletes to fit a window, is a non-goal. The store is full history until clear or the region drops. A compact tabular prompt is not the default.

**Truth.** Those items are outside version 1. History stays until clear or drop.

**Reason.** Each item is either a later product or a behavior that would delete turns the operator did not clear.

**Avoidances.** Do not build the console, the collector, the policy engine, or eviction on add inside version 1. Do not put chat behavior in `org.roux`. Do not use preview or unsafe memory. Do not make the compact prompt the default.

**semantic-key:** PK-5892

## 12. Telemetry

`RouxCounters` is per process and primitive. A collector is later and must read these counters without reflection on the append path.

The counters are `pagesAllocated`, `pagesSealed`, `pagesSpilled`, `framesAppended`, `indexEntries`, `seqlockRetries`, `dirtyFrames`, `staleViews`, `spillBytes`, `spillNanos`, `lockWaitNanos`, `usedBytesHot`, and `usedBytesSpilled`. `MemoryTelemetry` snapshots those plus fragmentation, which is 1 minus used over capacity of the hot page. There is no collector SDK in version 1.

**Truth.** Those primitive counters are the version 1 telemetry. Fragmentation is `1 - used/capacity` of the hot page.

**Reason.** A collector SDK on the append path would allocate and reflect where the frame write has to stay primitive.

**Avoidances.** Do not put a collector SDK in version 1. Do not reflect on the append path. Do not drop `dirtyFrames` or `staleViews` from the set.

**semantic-key:** PK-5893

## 1.3 Binding constraints

The host and the engine use no lambdas, keep primitives on the hot path, document each type, turn encryption off by default, drop the I prefix on interfaces, and append only after a successful completion.

Anonymous classes stand where a lambda would have. Documentation carries an author tag and an end-of-class banner. No unit-test source is added unless asked. Encryption defaults off. Interfaces are `MemoryInspector`, not an I-prefixed name. Cancelled turns are never written.

**Truth.** No lambdas, encryption off, no I prefix, and no store of a cancelled turn.

**Reason.** The hot path stays explicit and primitive. A cancelled turn that was stored would resurrect a reply the operator stopped.

**Avoidances.** Do not add lambdas in these types. Do not default encryption on. Do not prefix interfaces with I. Do not append a cancelled or failed turn. Do not add unit-test source unless asked.

**semantic-key:** PK-5894

## 13. Console, later

The console is accounted and not built. Commands bind to the engine surfaces as host commands, longest name first.

`roux memory telemetry` calls telemetry. `roux memory regions list` lists regions. `roux memory region free --id=` frees safely. `roux memory lock acquire --id=` tries the lock. `roux memory toon export --id=` calls the adapter export. `roux memory snapshot create` stays a stub until lifecycle exists. The region UUID on status is why the conversation id exists as a display value.

**Truth.** The console commands are named and not built in version 1.

**Reason.** The surfaces exist so a later command can bind without a second engine. Building them now pulls the shell into the pager slice.

**Avoidances.** Do not build these commands in version 1. Do not invent a second memory tree for them. Do not implement snapshot create beyond the stub.

**semantic-key:** PK-5895

## 14. Security

Encryption is off. On free, clear, or shutdown, the segment is filled with zero and the arena is closed before the file is deleted.

`CryptoSink` is the stub. Spill files are cleartext in `roux.spill.dir`, and that is documented. There is no memory lock in version 1. Swap exposure is accepted.

**Truth.** Spill is cleartext. Zero the segment and close the arena before deleting the file. No memory lock in version 1.

**Reason.** Encryption is unimplemented, so a cleartext directory is the honest statement. Zeroing before delete keeps a freed page from lingering in a reusable segment. Swap is accepted rather than pretended away.

**Avoidances.** Do not claim the spill is encrypted. Do not delete the file before the zero fill and the arena close. Do not add a memory lock in version 1.

**semantic-key:** PK-5896

## 15. Dropped from the earlier paste

The earlier paste's package name, I-prefixed interfaces, required optional type, packed message inside the engine, dual layout, owning bit vector, confined shared arena, 50 millisecond lock throw, native pin, latency acceptance tests, in-version collector, heap and floating-register types, eviction on add, an unrelated model runner, and vector indexes are dropped.

A shared arena is not renamed into a confined one. Zero-pause and percentile microsecond targets are not acceptance tests.

**Truth.** Those pasted shapes are not part of this design.

**Reason.** Each one either fights the locked calls or measures a latency this pager does not promise.

**Avoidances.** Do not revive the dual layout, the 50 millisecond throw, eviction on add, or a vector index. Do not make latency percentiles an acceptance test.

**semantic-key:** PK-5897

## 16. Implementation slices

Build bottom up. The host history cutover is last, so the session keeps working until that slice.

The same build artifact holds the packages. `org.roux` must not import the Spring AI packages or the host package. `org.roux.spring` may import the chat-memory library. One process-wide `RouxEngine` bean implements the four surfaces. Slice 0 is language level 25, `roux.*` settings, `RouxProperties`, and empty packages that compile. Slice 1 is page, frame, codec, monotonic index, memory page store, sequence lock, CRC, pins, and append plus cursor, able to append and read frames with no host. Slice 2 is spill to a file and a channel cursor, with `roux.spill.enabled`. History survives a page fill. The flag false keeps all pages hot. Slice 3 is the heuristic counter, the mapper, and the adapter: add, get, clear, window, and count by role. Slice 4 is the session on this store: list, clear, start, status, add after success, and drop on hangup, with conversation on status. Slice 5 and after, the console, collector, crypto, resume, and compact prompt, are not this plan. House style is no lambdas, explicit code, documentation, an author tag, and an end-of-class banner. Prefer ordinary classes over records on the engine path. Result types may be simple classes with getters. `RouxProperties` binds prefix `roux`, next to the host properties, as its own configuration properties.

**Truth.** Slices 0 through 4 are the version 1 plan, cut over last. Slice 5 is not this plan. `org.roux` imports neither the host nor the chat library.

**Reason.** Cutting the session over first would leave the live shell on a half-built pager. The import boundary is what keeps the engine free of the shell.

**Avoidances.** Do not cut the session over before spill and the adapter exist. Do not import the host from `org.roux`. Do not pull the console, collector, crypto, or compact prompt into slices 0 through 4. Do not use records on the engine path.

**semantic-key:** PK-5898

## 17. Acceptance

Version 1 is accepted when the session behaves as it does today, status shows the conversation and the counts, hangup removes that region's spill files, spill still reads history, the adapter works without the shell, and the engine does not depend on the host.

The prompt pins system, applies `max-messages` on the prompt, `message list` shows all, replies stay on their existing path, and cancel does not store. Status shows the conversation UUID and the store-backed counts. Hangup leaves the region gone and the spill files for that id gone. With spill on, after enough data to seal a page, later turns still read history because the cursor hits the file. The adapter can add, get, and clear messages without the shell. The engine has no dependency on the host package or the chat library except the adapter package. There is no unit-test mandate.

**Truth.** Acceptance is today's prompt behavior, a full list, no store on cancel, a readable spilled page, an adapter that stands alone, and an engine that does not import the host.

**Reason.** Those checks prove the cutover did not change the session and did not leak the shell into the engine. This heading's hangup sentence says the region and its spill files are gone, which is the acceptance as written here.

**Avoidances.** Do not require a unit-test suite for acceptance. Do not accept a build whose engine imports the host. Do not accept a cancel that stores the turn. Do not accept a list that shows only the window.

**semantic-key:** PK-5899

## 1. Goals and non-goals

Version 1 states goals, non-goals, and binding constraints as three lists.

The lists are the child headings. This heading does not add a goal of its own.

**Truth.** Goals, non-goals, and constraints are the three child lists.

**Reason.** Mixing a non-goal into the goal list makes a later build look required.

**Avoidances.** Do not promote a non-goal into version 1 from this heading. Do not add a goal that is not in the child list.

**semantic-key:** PK-5900

## 2. Locked decisions

The index is a monotonic message id plus a fixed-stride offset table. One page format holds append-only frames. The tabular form is the payload, not a second store. Export rebuilds a table from the index.

The adapter is `org.roux.spring.RouxChatMemory` implementing the chat-memory contract. The engine stays frames. The version 1 tokenizer for the local model is heuristic only, `max(1, (len+3)/4)`, stored at write. A counter interface lets a real tokenizer be added later. It is not in version 1. The full region persists. The window and the token budget are applied at read. `get()` with no limit returns all, which is the message list. The prompt uses the window plus a pinned system message. Disconnect on a disk-backed region checkpoints, writes the manifest, and keeps `{spill.dir}/{user}/`. RAM-only unmaps only. The conversation id is the session key: username when disk-backed, UUID per shell when RAM-only, not on frames or in the payload. Status may show it. List commands are implicit. The host writes the user and assistant pair only after a successful complete. The version 1 prompt unmarshals to messages. A compact prompt is later and config-gated. Spill is hot memory plus a file when a page fills. There is no mapped file. Encrypt, copy-on-write, and snapshot are stubbed. `roux.spill.enabled` can force RAM-only. Interfaces have no I prefix. The type byte is reserved and implemented later. The console is designed and not built. Counters are primitive now. A custom collector is later. The language level is 25. Pages and the index are off heap. A message list and an export string may allocate.

**Truth.** One format, heuristic tokens `max(1, (len+3)/4)`, window at read, disk-backed disconnect keeps the user directory, and append waits for a successful complete.

**Reason.** These locks keep the pager from growing a second store, a second tokenizer, or a hangup that deletes the pack the restore slice keeps.

**Avoidances.** Do not add a vector index. Do not tokenize with anything but the heuristic in version 1. Do not delete the user directory on disk-backed disconnect. Do not store a cancelled turn. Do not put the conversation id on the frame.

**semantic-key:** PK-5901

## 3. System shape

The host shell and the adapter both call `org.roux`, and the engine holds a page store, a frame log, and a memory-resident monotonic index.

The shell builds prompts and lists messages. The adapter implements the chat-memory contract. Below them are the inspector, the layout manager, the state controller, and the lifecycle manager. The host never walks an array list for history. It holds a conversation id, an active flag that is not part of the engine, and the adapter or the region plus the mapper. The line-history file `~/.casserole/history` is unchanged and is not this store.

**Truth.** History is the engine region. `~/.casserole/history` stays the line recall file and is not a page pack.

**Reason.** Two histories would diverge. The line file is the operator's input recall, not the transcript.

**Avoidances.** Do not walk an array list for transcript history. Do not store pages in `~/.casserole/history`. Do not put the active flag inside the engine.

**semantic-key:** PK-5902

## 4. Package map

`org.roux` holds the engine, the page, the index, the frame, the codec, the counter, the locks, the counters, the spill writer, and a disabled crypto stub. `org.roux.spring` holds the adapter and the mapper.

The surfaces are `MemoryInspector`, `MemoryLayoutManager`, `MemoryStateController`, and `MemoryLifecycleManager`. Model types include `BitAddress`, `RegionDescriptor`, `LockToken`, `FrameType`, `Role`, `MemoryTelemetry`, `AllocationResult`, and `OperationResult`. There is no packed-message type. Marshalling is the codec and the mapper. Page types are `PageHeader`, `Page`, `PageStore`, `RamPageStore`, and `FilePageStore`. The index is `MonotonicIndex`, message id to page id plus offset plus header fields. Frame types are `FrameHeader` and `FrameCursor`. `ToonCodec` is static and has no Spring types. Token types are `TokenCounter` and `HeuristicTokenCounter`. A real tokenizer is later, not version 1. Lock types are `RegionSeqLock` and `PinTable`. `RouxCounters` uses primitive adders. There is no collector SDK in version 1. `SpillWriter` writes the hot page's used bytes to a file. `CryptoSink` is a stub and is disabled. `RouxEngine` is process-wide, holds many regions, and implements the four surfaces. There is no `org.roux.cli`. The host adds console commands later against `org.roux.api`.

**Truth.** The engine is process-wide in `org.roux`. The adapter is the only Spring package. There is no console package and no packed-message type.

**Reason.** A console package inside the engine would import the shell. A message subtype would put the contract type on the hot path.

**Avoidances.** Do not add `org.roux.cli` in version 1. Do not put Spring types in `ToonCodec`. Do not add a packed-message subtype. Do not put a collector SDK in `RouxCounters`.

**semantic-key:** PK-5903

## 5.1 Why not two layouts

A bit-packed stream beside a columnar arena is rejected. The page is the container, the frame is the record, the index is the dense table, and export walks that table.

The page is a byte-bounded container, default 1 MiB, setting `roux.page-bytes`, aligned to 8. The frame is one append record inside a page. Its payload is the UTF-8 tabular form for that item. The index is a dense array of fixed-stride entries. `messageId` equals `seq`, starting at 1. The slot is `seq - 1`. Export walks the index, decodes payloads, and emits one tabular document. There are no embeddings. A bit vector that owned a byte array is replaced by `BitAddress`, a view `(regionId, pageId, byteOffset, bitOffset, bitLength)` into a live page. It does not own a byte array.

**Truth.** One layout: 8-aligned pages defaulting to 1 MiB, one frame per record, slot `seq - 1`, and `BitAddress` as a view.

**Reason.** Two layouts force every reader to know which one a page is. A bit vector that owns bytes copies the page the segment already holds.

**Avoidances.** Do not revive a columnar in-page stride beside the packed frame. Do not give `BitAddress` its own byte array. Do not start `seq` at 0. Do not change the default page from 1 MiB without `roux.page-bytes`.

**semantic-key:** PK-5904

## 5.2 Page header

Every page starts with a 64-byte header, and frames do not span pages.

Offset 0, 4 bytes, magic `ROUX` (`0x524F5558`). Offset 4, 2 bytes, format version `1`. Offset 6, 2 bytes, header size 64. Offset 8, 4 bytes, `pageId`, region-local, monotonic from 0. Offset 12, 4 bytes, `generation`, the sequence lock, odd while a writer is in progress. Offset 16, 4 bytes, flags `HOT`, `SPILLED`, `RETIRED`, and `DIRTY_SUSPECT`. Offset 20, 4 bytes, `usedBytes`, from 0, including the header. Offset 24, 4 bytes, CRC32C of bytes `[0, usedBytes)` excluding this field, written at seal time, and 0 while the page is hot. Offset 28, 4 bytes, `frameCount`. Offset 32, 8 bytes, `firstMessageId`. Offset 40, 8 bytes, `lastMessageId`. Offset 48, 4 bytes, `nextPageId`, 0 when there is no next page. Offset 52, 12 bytes, reserved.

Pages never shrink. Frames do not span pages. If the next frame is larger than the remaining space, the page is checksummed, the used bytes spill to a file, memory is unmapped, and a new hot page opens. If a single payload is larger than `pageBytes` minus the page header minus the frame header minus the pad, the operation fails. There are no continuation frames in version 1. Keep `roux.page-bytes` at least 1 MiB so chat text fits.

**Truth.** The header is 64 bytes, magic `ROUX` (`0x524F5558`), version `1` on this page, generation odd while writing, and CRC32C over `[0, usedBytes)` excluding the CRC field, 0 while hot.

**Reason.** A fixed header makes the slot address stable. An odd generation tells a reader the frame is torn. Excluding the CRC field from the CRC is what makes the checksum well defined. A frame larger than the page cannot be split in version 1, so the add fails.

**Avoidances.** Do not shrink a page. Do not span a frame across pages. Do not emit continuation frames in version 1. Do not set a page size under 1 MiB for chat text. Do not include the CRC field in the bytes it covers. Do not leave a non-zero CRC on a hot page before seal.

**semantic-key:** PK-5905

## 5.3 Frame

A frame is 8-byte aligned. The header before the payload is length, type, flags, role, pad, message id, timestamp, token count, payload length, and payload CRC.

`length` is a 32-bit count of the total bytes of this frame, including padding. `type` is one byte: 1 is `MSG`, 2 is `POLICY`, 3 is `TELEM`, 4 is `ACL`, and 5 is `CONT`, reserved. `flags` is one byte, and bit 0 means the frame has a checksum. `role` is one byte: 0 none, 1 system, 2 user, 3 assistant, 4 tool, 5 tool call. `pad` is one byte, written 0. `messageId` is 64 bits. `timestamp` is 64 bits of epoch milliseconds. `tokenCount` is 32 bits, stored at write. `payloadLen` is 32 bits. `crc32c` is 32 bits over the payload, always present in version 1, for dirty detection. The payload is UTF-8 tabular bytes. Padding brings the frame to a multiple of 8.

Those fields sum to 36 bytes before the payload: 4 + 1 + 1 + 1 + 1 + 8 + 8 + 4 + 4 + 4. A type other than `MSG` is still length-prefixed. Version 1 writers never emit those types. Version 1 readers skip an unknown type so a later policy frame does not break a scan.

**Truth.** The frame header is 36 bytes before the payload, aligned to 8, type 1 for a message, and the payload CRC is always written in version 1.

**Reason.** Alignment keeps the following long on an 8-byte boundary. Skipping unknown types lets a reserved type appear later without a format break. The payload CRC is how a dirty frame is detected without crashing.

**Avoidances.** Do not emit types other than `MSG` in version 1. Do not fail a scan on an unknown type. Do not leave the frame unaligned. Do not omit the payload CRC. Do not use the role byte for a type the table does not name without a defined mapping.

**semantic-key:** PK-5906

## 5.4 Payload row

Each message frame carries one single-row table, not the whole conversation.

The form is specification 4.1 tabular shape so encode and decode share one path with export. A message frame is:

```text
m[1]{r,ts,tok,x}:
user,1710000000000,12,Hello world
```

`r` is the role name. `ts` is epoch milliseconds. `tok` is the token count, redundant with the frame header. The header wins for budget math. The payload copy is for export. `x` is the message text, quoted when the text contains a delimiter or a newline. `ToonCodec` is static, with no instances on the hot path. `encode` writes role, time, tokens, and text into a segment at an offset and returns the length. `decode` reads a segment range into a small holder, and the text string is lazy until the caller needs heap text. `exportTable` emits the conversation-level `messages[N]{r,ts,tok,x}:` for a later console. The codec has no Spring types. Newlines and commas use the quoting rules. Binary and media are not version 1. The host is text chat.

**Truth.** One frame is `m[1]{r,ts,tok,x}:` plus one row. The header's token count wins for budgets. Export is `messages[N]{r,ts,tok,x}:`.

**Reason.** One code path serves the frame and the later export. Putting the whole conversation in every frame would copy the tape into every append.

**Avoidances.** Do not store a multi-row conversation inside one frame. Do not put Spring types in the codec. Do not let the payload token count override the header for budget math. Do not encode binary in version 1.

**semantic-key:** PK-5907

## 5.5 Monotonic index

The index is an off-heap segment with a fixed stride of 40 bytes, dense, and not spilled in version 1.

Offset 0, 8 bytes, `messageId`. Offset 8, 4 bytes, `pageId`. Offset 12, 4 bytes, offset in the page. Offset 16, 4 bytes, length, the frame total. Offset 20, 4 bytes, `tokenCount`. Offset 24, 8 bytes, timestamp. Offset 32, 1 byte, role. Offset 33, 1 byte, type. Offset 34, 2 bytes, flags. Offset 36, 4 bytes, reserved. The entry for an id is base plus `(id - 1)` times the stride. Lookup is constant time. The window walks ids downward. The token budget walks downward summing `tokenCount` until the budget. The system pin is applied in the host or the adapter, not in the index. The index grows by allocating a larger segment and copying the used bytes. It is small. It is not stored in the page. It is not spilled in version 1. It is rebuilt on a later restore. Version 1 as first drawn here has no restore. This index replaces any embedding store. The old radix store is this index plus the frame log.

**Truth.** Stride is 40 bytes. Slot is `(id - 1) * 40`. The index stays in memory and is not in the page.

**Reason.** A fixed stride is an O(1) locator. Spilling the index would make the window a file scan. Embeddings are a different product.

**Avoidances.** Do not spill the index in version 1. Do not store the index inside the page. Do not apply the system pin inside the index. Do not replace this table with a vector index.

**semantic-key:** PK-5908

## 5.6 Marshalling

There is no packed-message type in the engine and no message subclass.

Pages, the index, and frames are the structure. The codec converts UTF-8 rows to role, timestamp, tokens, and text at a segment offset or a file buffer. The mapper converts host messages to those fields and back. It is used only at the adapter and host edges. Window and token walks use the index fields: role, token count, page id, and offset. The payload is decoded only when the edge needs a string or a message.

**Truth.** Marshalling is the codec plus the mapper at the edge. The window walks the index and does not decode until text is required.

**Reason.** Decoding every row to walk a token budget would allocate the history the index already summarizes.

**Avoidances.** Do not add a packed-message subtype. Do not decode the payload inside the index walk. Do not put the mapper on the spill path.

**semantic-key:** PK-5909

## 5. Storage

Version 1 storage is one page, one frame, one tabular payload, and one monotonic index.

The child headings hold the layout. This heading names the four pieces and no second layout.

**Truth.** Pages, frames, payload, and index are one store.

**Reason.** Two layouts were rejected. Naming the four pieces here keeps a reader from inventing a fifth.

**Avoidances.** Do not add a second on-disk layout beside the page. Do not split the payload into its own store.

**semantic-key:** PK-5910

## 6. Tiers

Hot and spilled are the version 1 residencies. Encrypted spill, copy-on-write snapshot, cross-session restore, and mapped files are not.

Hot is an arena segment, writable, with a sequence lock. Spilled, after a page fills, writes the used bytes to a file, drops memory, and the cursor reads through a channel. Encrypted spill is a stub with `roux.crypto.enabled=false`. Copy-on-write snapshot is unsupported. Cross-session restore is no: disconnect deletes the region files on this heading's original lock. Mapped files are not version 1. The page store acquires the hot page, spills by checksum then write then unmap, and if spill is disabled keeps the full page in memory and still allocates a new hot page with no file. Open returns memory or file. Retire is the dead log. A cursor is the way to read a page from disk without a list. `get()` may still assemble a list at the edge. Internal window and token packing walk the cursor.

**Truth.** Hot and spilled are the residencies. Spill off keeps the old page in memory and still opens a new hot page. The cursor is the disk read.

**Reason.** A list of every historical message inside the engine would undo the off-heap page. The cursor walks frames without building that list until the edge asks.

**Avoidances.** Do not enable encryption by default. Do not use a mapped file as the pager. Do not treat copy-on-write as implemented. Do not drop the old RAM page when spill is disabled.

**semantic-key:** PK-5911

## 7.1 Sequence lock on hot pages

The writer bumps the generation to odd, writes the frame, appends the index, and bumps the generation to even. The reader retries if the generation changes.

The writer increments generation so it is odd, writes the frame at `usedBytes`, pads, and bumps `usedBytes`, `frameCount`, and the last id. The index append is under the index's own mutex and is short. The writer increments generation so it is even. The reader loads an even generation and spins if it is odd, with a bound. It reads the frame bytes, reloads the generation, and retries if it changed. It verifies the payload CRC. On failure it sets `DIRTY_SUSPECT`, does not decode, and does not crash. A health report carries that. A spilled page has a frozen even generation. The file is immutable. The CRC is written before the file write.

**Truth.** Odd generation means a write is in progress. A CRC failure sets `DIRTY_SUSPECT` and does not crash. Spilled pages stay even and immutable.

**Reason.** A reader that decodes a half-written frame would show torn text. A crash on a bad CRC would take down the shell for one dirty frame.

**Avoidances.** Do not decode when the generation changed. Do not crash on a payload CRC failure. Do not write the spill file before the CRC. Do not leave a spilled page writable.

**semantic-key:** PK-5912

## 7.3 Pins

A pin is an in-flight hold on a page id. Spill and unmap of a pinned page wait or copy, then retire.

`PinTable` records the hold for a cursor or a prompt. The pin prevents use after unmap, which is the foreign-memory fault this design has to avoid. This is not a native-interface pin story.

**Truth.** A pinned page is not unmapped until the pin drops, or it is copied and then retired.

**Reason.** Closing an arena under a live cursor is a use-after-unmap. Waiting or copying is the fence.

**Avoidances.** Do not unmap a page whose pin count is above zero. Do not use a native-interface pin. Do not spill a pinned page without the wait or the copy.

**semantic-key:** PK-5913

## 7.5 Dirty

A magic mismatch is not a page. A CRC mismatch skips the frame and counts `dirtyFrames`. A partial last frame is a torn write and the tail is ignored.

`usedBytes` against the last frame's length is how a torn tail is recognized. That is the power-loss case on spill. The shell does not crash.

**Truth.** Bad magic is not a page. Bad CRC skips and counts `dirtyFrames`. A torn tail is ignored.

**Reason.** A crash mid-write leaves a short tail. Treating it as a frame would decode garbage. Counting the skip keeps the damage visible.

**Avoidances.** Do not decode a magic mismatch as a page. Do not crash on a bad CRC. Do not repair a torn tail in version 1. Do not count a torn tail as a successful frame.

**semantic-key:** PK-5914

## 7. Concurrency, dirty, and dead-log

Version 1 has one model worker and one session thread per region, and the design still allows a later operator thread.

The child headings are the sequence lock, the region mutex, pins, the dead log, and dirty detection.

**Truth.** One writer per region is the version 1 shape, with room for a later operator thread.

**Reason.** The session is already single-writer. Designing only for that would make a later operator thread tear frames.

**Avoidances.** Do not assume a second writer in version 1. Do not leave the later operator thread unable to take the same locks.

**semantic-key:** PK-5915

## 8. Engine surfaces

The four surfaces use result objects or null, not a required optional type, and they do not use an I prefix.

The inspector is non-mutating: telemetry, list regions, get region or null if missing, raw read into a caller-provided segment rather than a returned byte array, bit views through the cursor, and an integrity report. The layout manager allocates a region, grows the hot page as a linked child with no copy of history, frees safely, and defragment is unsupported or a no-op in version 1. Pin sets the region pin. There is no heap segment type. Residencies are hot and spilled. Floating-register and radix-store types are dropped. The index is the lookup. The state controller writes a frame by encoding into the hot page, not from a raw caller byte array. It clears a region. `tryAcquireLock` takes a region, a lock level, and a timeout in milliseconds, and returns null on timeout. `releaseLock` returns whether the token was released. The lifecycle manager's atomic snapshot is unsupported in version 1. Restore is unsupported. Heap export dumps page files plus an index image if useful, or it is a stub. Shutdown takes a force-unmap flag. Lock levels `EXCLUSIVE_WRITE`, `READ_ONLY_SNAPSHOT`, and `DRAIN_AND_HALT` stay for a later console. Version 1 chat uses the internal sequence lock, not operator tokens.

**Truth.** Four surfaces, null or a result object, defragment and snapshot unsupported in version 1, and lock levels reserved for later.

**Reason.** A required optional type and an I prefix were style from the superseded paste. Unsupported snapshot keeps the surface without pretending restore exists.

**Avoidances.** Do not require optional types. Do not prefix the surfaces with I. Do not return a fresh byte array from the raw read. Do not implement defragment or snapshot restore in version 1. Do not expose a heap segment type. Do not accept a raw byte array as the frame write.


**semantic-key:** PK-3469 PK-3470 PK-3471 PK-3472 PK-3473

## 9. Spring adapter

`RouxChatMemory` implements the chat-memory contract. Add counts tokens, encodes, and appends, and it does not evict. Get returns every message frame unmarshalled in message-id order. Clear clears the region.

Extra methods live on the adapter, not on the contract: a window by non-system cap and system pin, a window by token budget and system pin, a count by role, and an export for a later console. The system pin includes the latest system frames, then the non-system tail until the count or the token budget. That matches the current prompt: system is always in, and `max-messages` applies to non-system. Tool roles map to `tool` and `tool_call`. Unknown types are stored as `user` with a flag, or rejected. Prefer mapping to `tool` and preserving the text.

**Truth.** Add does not evict. Get returns all message frames in id order. The window methods and the export are adapter methods, not contract methods.

**Reason.** Evicting on add would delete history the non-goals already refused. Putting window methods on the contract would fork the library interface.

**Avoidances.** Do not evict inside add. Do not put `getWindow` on the library interface. Do not drop tool text. Do not apply `max-messages` to system frames.

**semantic-key:** PK-5916

## Project Roux memory engine design

`org.roux` is the memory manager only, the Spring surface is only the chat-memory adapter, and the host consumes it.

The engine has no terminal shell, no command objects, no model client, and no Spring types. `org.roux.spring` is the `ChatMemory` adapter only. Chat behavior stays in the host. The language level is 25, the current long-term release with finalized foreign memory, and preview flags are not required. The host moves with this store. A pasted earlier spec is superseded. The implementation slices are in this file.

**Truth.** The engine package is `org.roux`. Spring stays in `org.roux.spring`. The language level is 25 with finalized foreign memory and no preview flag.

**Reason.** Putting the shell or the model client in the engine would make the pager a chat program. Finalized foreign memory is what the page segment needs without a preview switch.

**Avoidances.** Do not put shell, command, model, or Spring types in `org.roux`. Do not require a preview flag. Do not revive the superseded paste as the source of truth.


**semantic-key:** PK-3474 PK-3475 PK-3476 PK-3477 PK-3478

## 0. The five calls

Five calls close reattach. Close keeps the tree. The boundary is the scan. The scan runs on open. A bad frame stops that page. A short or missing manifest is bounded by the pack walk.

Persist on leaves the directory. Persist off deletes it. That close is already what the tree does. Session policy stays on the restore page. The scan runs when that region opens and restore is on. There is no startup sweep and no reindex command. A bad frame stops that page, keeps the good prefix, and opens short. An unknown version refuses the region. The pack walk is the bound. A longer walk wins. A missing manifest with a valid pack is walked. A manifest that names a slot the walk cannot see refuses the region. Until those calls are accepted, the tree stays as it is. Accepting the first four does not by itself close the fifth. All five are accepted. The build is named.

**Truth.** The five calls are close, the scan boundary, open-time scan, stop-that-page, and the header walk as the bound.

**Reason.** The first four are sentences the tree already matches. The fifth is the crash hole the tree does not: a manifest shorter than the forced slots, or a pack with no manifest.

**Avoidances.** Do not scan at startup. Do not add a reindex command on this layer. Do not treat the first four calls as a fix for a short manifest.

**semantic-key:** PK-5917

## 10. What stays

Session policy, version 1 requirements through the identity map, and the later rows stay. No new frame field, no new manifest field, no second pack, and no renumber of sequence.

Exclusive login, the username key, clear, RAM-only, and the persist and restore flags stay on the restore page. The pack, the force, the working set, the durability knob, the turn atom, oversize rejection, and the memory identity map stay. Resume, acknowledgement, and held continuation stay. Clip range stays. The wire stanza is empty. No system line is added, removed, or reordered.

**Truth.** Reattach adds no frame field, no manifest field, no second pack, and no wire change.

**Reason.** The bytes version 1 already writes are enough for the walk. A new field would be a format bump this layer does not need.

**Avoidances.** Do not add a frame field or a manifest field. Do not renumber sequence. Do not rewrite a frame to repair it. Do not add, remove, or reorder a system line on the wire. Do not add a replica.

**semantic-key:** PK-5918

## 11. This filing

This filing records the calls and does not cut product source, open a live session, or add a public type.

The build waits.

**Truth.** The filing is the page. The tree's types stay as they are until the build is named.

**Reason.** Recording the five calls is not a license to open a live model call or to grow the public surface.

**Avoidances.** Do not cut source from this filing. Do not add a public type. Do not open a live session to prove the page.

**semantic-key:** PK-5919

## 12. The bake

The five calls are accepted. The first four are sentences. The fifth is the walk.

The walk reads headers from slot 0. A short read or a header without magic `ROUX` ends the walk. Magic with a version other than 2, or a page id that is not that slot, refuses the region. A missing manifest with a valid slot 0 is walked. A longer walk is taken and the hot page is the highest taken id. A shorter walk than `pageCount` refuses. A matching count keeps the manifest hot id. A repair writes the manifest. A failed write leaves the region open. The files stay on a refusal. There is no new public type and no new public method. The private method is the slot walk. No startup sweep. No new command. No change to publish, the frame layout, or the manifest size.

**Truth.** The accepted walk is private, starts at slot 0, stops on a short or non-`ROUX` header, refuses a bad version or a bad page id, and repairs the manifest without a public type.

**Reason.** The private walk is the recovery. A public method would be a second door beside open.

**Avoidances.** Do not publish the walk as a public method. Do not add a command. Do not change publish, the frame, or the manifest size. Do not delete files when the walk refuses.

**semantic-key:** PK-5920

## 13. The battery

The window coverage names the roll, the tail, and the walk, and the cases cover the pack.

The reopen walk is covered. The wire stanza stays empty. No product source beyond the walk is required by this heading.

**Truth.** The pack is covered by cases for the roll, the tail, and the reopen walk.

**Reason.** A walk that is only described can still hide a short manifest. The cases are what showed the reopen path holds.

**Avoidances.** Do not treat an uncovered pack as accepted. Do not add a wire change in the coverage. Do not reopen a case the pack already covers.

**semantic-key:** PK-5921

## 1. The seat

Version 1 is the publish layer. The frame already carries sequence, the identity pair, and a CRC. The page header is 64 bytes, magic `ROUX`, version 2. The frame header is 56 bytes. The pack is one `pages.bin`. Slot `P` starts at `P * page-bytes`.

`findById` is the memory map. It dies with the region. A rebuild is a walk of the frames. `manifest.bin` is 112 bytes. The CRC covers `[0, 104)`. The fields the open trusts are `pageBytes`, `pageCount`, `hotPageId`, `formatVersion`, and `owner`. `nextSeq` is stored and is not the sequence authority after a scan. The write is temporary, force, then replace. A failed manifest write is swallowed. The layer map still says a later startup may scan a leftover pack. Where an older section of the paging page disagrees with that map, the map wins. This page does not narrow the map by itself. Clip range stays its own page. Resume, acknowledgement, and continuation stay later. Continuation stays held. Oversize rejection stays the large-frame rule.

**Truth.** Reattach reads a 64-byte `ROUX` version 2 header and a 56-byte frame from `pages.bin` at `P * page-bytes`, and it does not trust `nextSeq` over the scan. The manifest stays 112 bytes.

**Reason.** Those sizes are what version 1 already writes. A scan that trusted `nextSeq` would invent rows the frames do not hold. Narrowing the layer map inside this page would edit a page this page said it does not rewrite.

**Avoidances.** Do not change the header, the frame, or the manifest size. Do not let `nextSeq` outrank the scan. Do not pull clip range, continuation, or acknowledgement into reattach. Do not narrow the layer map from this page alone.

**semantic-key:** PK-5922

## 2. What the tree does

Persist-on close checkpoints, writes the manifest, unmaps, and keeps the directory. The open walks only `0` through `pageCount - 1` and stops a page on the first bad frame.

Persist off deletes the directory, including `pages.bin`. A RAM-only region unmaps and does not write or delete a pack. Clear wipes the pack and writes a fresh manifest. The manifest is written from close and from clear. It is not written from page publish or from the hot checkpoint. A spill forces the slot and leaves the manifest at the previous close. Open is per region id. The factory passes the sanitized username for a disk region. RAM-only allocates a new region and does not open the pack. A live region, or a held file lock, refuses. Both files absent starts a new region. One of the two files absent refuses, and the message names the directory. Restore off, with a leftover pack, refuses and does not attach. A manifest that fails its CRC, disagrees on `page-bytes`, disagrees on format version, disagrees on owner, or names a hot page outside `pageCount`, refuses. The files stay. The hot id from the manifest is mapped as the hot page. Every other slot is scanned and left unmapped. A frame is taken only when the length fits, the frame CRC matches, and the sequence is exactly one more than the index size. The first failure stops that page. The loop continues with the next page. After the walk, the next message id is the index size plus 1. An I/O error, or no hot page, destroys the attempt and refuses. The pack is not deleted. The scan does not check the page CRC, the page magic, or the page version. It does not read past `pageCount`. It does not skip one bad frame and continue. It does not renumber sequence. It does not sweep the spill directory. It does not implement a reindex command. Sequence is global. A torn tail on page 2 stops page 2. Page 3's first frame has a higher sequence, so it fails the sequence check and adds nothing. The later pages are walked and contribute nothing. The region opens short. The frames after the tear are absent from the index.

**Truth.** The tree's scan is bounded by `pageCount`, stops a page on length, frame CRC, or a sequence gap, and does not read page magic or page version.

**Reason.** The manifest is the only bound the tree has, so bytes past `pageCount` are invisible. A global sequence means a torn page also silences every later page.

**Avoidances.** Do not delete the pack on a failed open. Do not attach when restore is off. Do not skip a bad frame and continue. Do not renumber sequence. Do not treat a later page as recovered after a torn earlier page on this walk.

**semantic-key:** PK-5923

## 3. The hole

A clean hangup with persist on already reattaches. A crash strands forced slots the next spill can overwrite.

First session, no close yet: spills have forced slots into `pages.bin`, the manifest was never written, and open sees a pack without a manifest and refuses. The forced frames are stranded until the directory is deleted. Later session, crash before close: the manifest is from the previous close, newer slots are invisible, and open maps the old hot id. After that short restore, the next page id is the old count. The next rotate publishes at that id and overwrites the unread frames. The manifest writer tries an atomic move, and on failure deletes `manifest.bin` and then moves the temporary into place. A crash in that window leaves no manifest. The cleanup also deletes the temporary. The pack can be the only survivor. `commit` and `append` force the hot slot into `pages.bin` and still do not make open see a slot the manifest has not counted. `hot-only` never put the hot page on disk until a rotate. A crash loses that hot page. That loss stays. This hole's fix does not change it.

**Truth.** A missing or stale manifest hides forced slots, and the next publish can overwrite them. `hot-only` still loses the unforced hot page.

**Reason.** The open trusts `pageCount`. Durability forces bytes the open will not walk. The fallback that deletes the manifest before the new one lands can remove the only hint.

**Avoidances.** Do not assume a forced slot is visible to open. Do not treat durability as a fix for a stale manifest. Do not claim `hot-only` survives a crash. Do not delete the directory as the only recovery.

**semantic-key:** PK-5924

## 4. Close

`session.persist` true writes the hot slot and the manifest and leaves the directory. `session.persist` false deletes the directory. RAM-only unmaps and does not touch a frozen pack.

The older sentence that hangup deletes the directory yields to this sentence. The factory settings already have persist and restore true. The restore page already locked this. The code matches that lock. The requirements page's close sentence closes in favor of the tree and of the restore page. The other reading, delete on hangup, would make a leftover pack exist only after a crash that never reached close, and a clean reconnect would start empty. That fights the restore page and the settings that already ship. Accepting this call is one sentence. No code. The leftover is the directory a persist-on close left behind, and the directory a crash left behind.

**Truth.** Persist on keeps the directory. Persist off deletes it. RAM-only does not touch a frozen pack.

**Reason.** Delete-on-hangup fights a restore that already reopens the pack. The tree and the settings already keep it.

**Avoidances.** Do not delete the directory when persist is on. Do not write or delete a frozen pack from a RAM-only close. Do not change code for this call. Do not make a clean reconnect start empty.

**semantic-key:** PK-5925

## 5. Boundary

Reattach is the scan of one pack: rebuild the index and the identity map, or refuse, and leave the files when refusing.

It does not choose the region key, the exclusive rule, clear, named sessions, or the persist flag. Those stay on the restore page. The scan rules are this layer. The session policy on that page stays the resume row, including the part the tree already runs. Putting exclusive login and username mapping into this build would reopen locks that page already has. Filing one combined layer would make a frame-scan change carry the session policy, and the map would have to be rewritten. Accepting this call is the boundary sentence. No code.

**Truth.** Reattach scans one pack and leaves files on a refusal. Session policy stays on the restore page.

**Reason.** The map already splits the rows. Mixing exclusive login into the scan would reopen a lock that is not a frame rule.

**Avoidances.** Do not move the region key, the exclusive rule, clear, or the persist flag onto this layer. Do not delete files when refusing. Do not rewrite the layer map into one combined row.

**semantic-key:** PK-5926

## 6. When the scan runs

The scan runs inside region open, and only when `session.restore` is true, for the one region the caller named.

There is no walk of sibling directories under the spill directory. There is no scan at startup. A reindex command stays off this layer. Restore false keeps the refusal: the leftover is not attached, the files stay, and the message names the directory. A startup sweep would map every user's pack, take locks for users who are not connected, and pay the walk before anyone asks. A reindex on a live region would rebuild an index the shell already has. The wide reading of the map, scan every leftover directory at startup, attaches sessions nobody opened. An explicit reindex is a console and does not help a reconnect by itself. Accepting this call narrows "later startup" to "on open of that region." The tree already runs this trigger. No code.

**Truth.** Scan on open of the named region when restore is on. No startup sweep. No reindex command. Restore off does not attach.

**Reason.** The connecting shell is the one that can use the index. A startup sweep spends locks and memory for users who are not here.

**Avoidances.** Do not scan sibling directories. Do not scan at startup. Do not add a reindex command on this layer. Do not attach a leftover when restore is off. Do not delete that leftover.

**semantic-key:** PK-5927

## 7. A bad frame

Refuse the region without deleting the files when the pack cannot be trusted as a whole. Stop one page on the first bad frame and keep the good prefix. Leave the page CRC out of this decision.

Refuse when one of `pages.bin` or `manifest.bin` is missing, until the walk call says otherwise for a missing manifest. Refuse when the manifest CRC fails, `page-bytes` disagree, the manifest format version disagrees, the owner disagrees, `pageCount` or `hotPageId` is nonsense, a slot read throws, or the walk ends with no hot page. Any walked slot whose header is present and whose version is not 2 refuses the region. A mixed pack is not a version 1 pack. Stop the page on the first frame whose length does not fit, whose CRC does not match, or whose sequence is not the next index id. Frames already taken on that page stay. The walk goes on to the next page. A later page whose first sequence does not line up adds nothing. The region opens short. The next message id is the count of frames actually indexed. The manifest's `nextSeq` does not raise that count and does not fill the hole. A torn tail can fail the page CRC and still have a good prefix. Frame CRC is the scan's truth. The page CRC stays the checksum publish writes. A later audit can read it. This scan does not drop a page for it. Do not skip one bad frame and continue. The next frame would fail the sequence check, and the log is not rewritten. Do not mark the stopped tail as `dirtyFrames`. That counter is the later read of a frame that was indexed and then fails its CRC. A frame the scan refused was never indexed. Refusing the whole region for a torn tail would force the operator to delete a pack that still holds the session. Skipping an unknown page version and continuing breaks sequence on a foreign middle slot. Unknown version refuses the region instead. The frame rule is accepted. The version check on a slot header belongs with the walk. On today's count-bounded walk the slot version is not read.

**Truth.** Frame length, frame CRC, or a sequence gap stops that page and keeps the prefix. A present header whose version is not 2 refuses the region. The page CRC does not drop a page. `dirtyFrames` is not the scan's counter.

**Reason.** The durability contract loses the in-flight turn and keeps the committed prefix. A torn last frame is that loss. A foreign version is a different layout, and skipping it punches a hole in sequence.

**Avoidances.** Do not delete files on a refusal. Do not skip one bad frame and continue. Do not renumber sequence. Do not use `nextSeq` to fill a hole. Do not increment `dirtyFrames` for a frame the scan never indexed. Do not drop a page because its page CRC fails. Do not open a mixed pack as version 1.

**semantic-key:** PK-5928

## 8. A short or missing manifest

The walk finds the slots. The manifest is a check, not the only bound. File length is the wrong bound.

Start at slot 0. The pitch is `page-bytes` from the manifest when the manifest is valid, otherwise from settings. Read 64 bytes at `P * page-bytes`. A short read is the end of the pack. Magic `ROUX`, version 2, and page id `P` means the slot is taken. Magic `ROUX` with a version other than 2, or with a page id other than `P`, refuses the region. Any other header, including zeros in a hole, is the end. Page ids are issued in order. Do not skip a hole to look further out. A missing manifest with a pack present and slot 0 valid is walked. Do not refuse. The owner is the directory name. The region UUID is minted when the next manifest is written. The frames do not carry that UUID. A manifest present and the pack absent refuses. There is nothing to scan. When both are present and the walk is longer than `pageCount`, the extra slots are taken. The hot page is the highest taken id. The next message id is the indexed count. When the walk is shorter than `pageCount`, refuse. Do not invent an empty page to fill the count. Do not open a short region over a manifest that promised more. When the counts match, keep today's hot id when that slot was taken. A nonsense hot id still refuses. `page-bytes`, owner, or manifest format version disagree: refuse, as today. After a successful walk that repaired the bound, write the manifest. A failed write leaves the region open, the same swallow as today. The walk remains the recovery if the process dies again before a later close. A publish writes the used bytes, padded to `align-bytes`, at `P * page-bytes`. It does not fill the slot. Size divided by page bytes can see zero pages on a pack that holds a valid slot 0. The header walk is the bound. Keeping `pageCount` as the only bound leaves the crash hole. Trusting file length is rejected because the pitch is not the written length. Opening a short walk and ignoring extra manifest names hides a truncated pack. Rewriting the manifest on every publish still needs this walk when the manifest itself is torn. Accepting this call grows the open. The frame rule stays. No new public type. No startup sweep. No new command. No change to publish. No change to the frame layout. No change to manifest size.

**Truth.** Slots are discovered by a 64-byte header at `P * page-bytes`. A valid slot 0 with no manifest is walked. A longer walk is taken. A shorter walk than `pageCount` refuses. File length is not the bound.

**Reason.** The crash path forces slots past the old count, and the next spill overwrites them if open will not see them. A pack with no manifest is the operator's only recovery, and refusing it tells them to delete the directory.

**Avoidances.** Do not bound the scan by file length. Do not skip a hole to look further out. Do not refuse a missing manifest when slot 0 is valid. Do not accept a walk shorter than `pageCount`. Do not invent empty pages to fill the count. Do not grow the manifest. Do not change the frame layout. Do not add a manifest write on the hot publish path.

**semantic-key:** PK-5929

## 9. What a named build changes

Only when the walk call is included. The earlier calls, accepted alone, are sentences.

Discover slots by the header walk. Stop at the first short header or the first header without magic `ROUX`. A `ROUX` header whose version is not 2, or whose page id disagrees, refuses the region. A missing manifest with a valid slot 0 is walked. A walk longer than `pageCount` is taken. The hot page is the highest taken id. A walk shorter than a valid `pageCount` refuses. A present header whose version is not 2 refuses the region. Frame CRC, length, and sequence still stop that page. The good prefix stays. The region opens short. The scan wins the next message id. A successful repair writes the manifest. A failed write leaves the region open. Refuse still leaves the files in place. `hot-only` still loses the unforced hot page. The highest forced slot is the recovered hot page. The next append rotates when that page cannot hold the frame.

**Truth.** The named build is the header walk, the missing-manifest walk, the longer-walk take, the shorter-walk refusal, and a manifest rewrite that may fail open.

**Reason.** Those are the crash recoveries the sentences do not implement by themselves. `hot-only` remains a loss of the unforced page because that page was never forced.

**Avoidances.** Do not change publish, the frame layout, or the manifest size. Do not add a public type. Do not delete files on refusal. Do not pretend `hot-only` recovered an unforced page.

**semantic-key:** PK-5930

## Reattach

Reattach rebuilds the memory index and the identity map from the frames in a leftover pack.

A clean close with persist and restore already walks the frames. The hole is a crash, or any close that never wrote a matching manifest. Forced slots can sit in `pages.bin` while the manifest is missing, stale, or still describing the previous session. This page sits beside the version 1 requirements and the layer map. It does not rewrite those pages.

**Truth.** Reattach is the frame scan that rebuilds the index and the identity map when the manifest does not match the pack.

**Reason.** The manifest is a hint written at close. A crash leaves forced slots the hint does not name, and the next spill can overwrite them.

**Avoidances.** Do not treat a clean close as the only reattach case. Do not rewrite the version 1 page or the layer map from this page.

**semantic-key:** PK-5931

## 10. What we refuse

The core refuses string encodings, UUID objects on the hot path, a node id in settings, native addresses inside an id, a page pointer inside the public id, a snowflake in place of `seq`, and a central id service.

Hexadecimal and Base32 stay out of `org.roux`. A UUID type stays off append and off the window. A node id in settings is refused unless a later choice switches to an 8-byte time-sorted id. No id encodes a segment address. No public id encodes `pageId` plus offset. The dense `seq` is not replaced by a snowflake used as the array index. There is no central id service.

**Truth.** The refused shapes are string ids, hot-path UUID objects, node settings, address-bearing ids, and a central mint.

**Reason.** Each of those shapes either allocates on the hot path, breaks when a page is unmapped, or adds a service this split was chosen to avoid.

**Avoidances.** Do not reopen hexadecimal or Base32 in the core. Do not encode a segment address or a page offset in the public id. Do not replace `seq` with a snowflake index. Do not add a central id service.

**semantic-key:** PK-5932

## 11. Optional 8-byte fallback

If sixteen bytes ever hurts, an 8-byte time-sorted id plus a required unique `roux.node-id` is the fallback, and it is not recommended now.

That fallback is more key management, which this agreement set out to avoid. Stay on two longs.

**Truth.** The 8-byte fallback requires `roux.node-id` and is not the choice.

**Reason.** Saving eight bytes costs a node registry. The split exists to avoid that registry.

**Avoidances.** Do not switch to an 8-byte id to save space. Do not add `roux.node-id` for this layout.

**semantic-key:** PK-5933

## 12. Summary

Page I/O stays `seq` plus a 56-byte index row plus a channel read, uniqueness is a 128-bit version-7 pair with no cluster setting, and the footprint is 16 extra bytes per message in the index and in the frame.

`idHi` and `idLo` sort by time and never find bytes. The region id plus `seq` from 1 through N is the locator. The index row is the page pointer and stays stable across spill. One generator component is the touch point. There is no hexadecimal and no node registry. That is the solution to implement when it is wanted.

**Truth.** Speed stays on `seq` and a 56-byte row. Uniqueness is 128 bits with no cluster setting. The cost is 16 bytes on the row and on the frame.

**Reason.** This summary is the agreement: the pointer is not the identity, and the identity is not a string.

**Avoidances.** Do not find bytes with the identity pair. Do not add cluster configuration for the pair. Do not shrink the row back to 40 bytes after the identity is stored.

**semantic-key:** PK-5934

## 1. Decision

Use two keys, never one fused number: `seq` locates the row inside one region, and `idHi` with `idLo` identify the message across a cluster.

`seq` is a long. It is the dense index slot, the window walk, and the page pointer only by way of the index. It is unique inside one region. `idHi` and `idLo` are two longs in a time-ordered 128-bit layout. They are unique across a cluster with no key service. `seq` stays what the store already is: it runs from 1 through N, and the slot is `seq - 1`. Identity is a 128-bit word pair stored next to the frame, compared as two longs, never as a UUID object and never as a dashed hexadecimal string on the engine path.

Page access is always `regionId` plus `seq`, then the monotonic index, then `pageId` plus offset, then a memory segment or a `.pg` channel. Identity is not a page pointer. Spill can move memory to disk without renaming messages.

**Truth.** `seq` finds bytes inside one region. The two longs are the cluster identity and are never the lookup key.

**Reason.** A single number cannot be dense enough for an array slot and wide enough for cluster uniqueness. The index row is the pointer, so spill does not rename the message.

**Avoidances.** Do not fuse the locator and the identity into one number. Do not look up a page by the identity pair. Do not put a UUID object or a dashed hexadecimal form on the engine path.

**semantic-key:** PK-5935

## 2. Why this split

The I/O key is a small monotonic `seq`, the public unique key is 128 bits of time-ordered entropy, and the page pointer is the index row.

A one-register scheme that shifts milliseconds by 16 and ors a counter is time-sortable, and it still fails here. It is not unique across hosts, because it has no node bits and no entropy. It wraps at 65536 ids in one millisecond. It cannot be a dense array index. It does not encode `pageId` plus offset. A native address dies when the segment is unmapped. Fusing a UUID, a page pointer, and a time into 64 bits is impossible: the region is already 128 bits, a 1 MiB page needs about 20 bits of offset, and spill invalidates any foreign-memory address. Key management stays minimal: no worker id, no central ticker, and no string codec on add or get.

**Truth.** Split locator, identity, and pointer. Do not use a 64-bit fusion or a millisecond-shifted counter as the index.

**Reason.** The fusion overflows the bits, wraps inside a millisecond, and points at an address spill destroys. The three jobs need three places.

**Avoidances.** Do not adopt a `millis << 16 | counter` id as the array index. Do not encode a native address in the id. Do not add a worker id or a central ticker to make the split work.

**semantic-key:** PK-5936

## 3. Identity bit layout

The identity is sixteen bytes in two native longs, big-endian field order matching the time-ordered layout, the same most-significant and least-significant bits a UUID conversion would use at a boundary.

`idHi` holds a 48-bit `unix_ts_ms`, a 4-bit version, and a 12-bit `rand_a`. `idLo` holds a 2-bit variant and a 62-bit `rand_b`. `unix_ts_ms` is milliseconds since the Unix epoch, so `idHi` increases with time and the sort is `hi` then `lo`. The version is the constant 7, so a later boundary can wrap a platform UUID. `rand_a` is a monotonic counter in the same millisecond, not random on the hot generator. The variant is the constant 2-bit value 10. `rand_b` is drawn from a cryptographic generator, or from a buffered secure or thread-local policy. Cluster uniqueness lives in `rand_b`.

There is no hexadecimal, no Base32, no Crockford alphabet, and no dashes inside `org.roux`. Optional hexadecimal exists only in a later status formatter. Compare is primitive: if the high longs differ, return the sign of their unsigned difference; otherwise return the sign of the unsigned difference of the low longs. Equality is high equals high and low equals low.

**Truth.** The pair is a version-7 layout: 48 bits of epoch milliseconds, version 7, a 12-bit same-millisecond counter, variant 10, and 62 random bits.

**Reason.** That layout sorts by time, interoperates if a boundary wraps a UUID, and puts cluster uniqueness in the 62-bit random field without a string form on the hot path.

**Avoidances.** Do not store dashes or hexadecimal in `org.roux`. Do not compare the longs as signed time. Do not randomize the 12-bit counter on the hot generator. Do not change the field widths.

**semantic-key:** PK-5937

## 4. Locator

`seq` stays the dense locator, and the index row grows by 16 bytes so a window can see the identity without touching the page.

Per region, `nextSeq` starts at 1. On append, `seq` is `nextSeq` and then the counter increments. The index slot is `seq - 1`. The window for the message cap walks `seq` from the size down to 1, pins system rows, and ignores identity. The index row is 40 bytes today. Add 16 bytes for identity. The stride becomes 56.

Offset 0, 8 bytes, `seq`. Offset 8, 4 bytes, `pageId`. Offset 12, 4 bytes, offset in the page. Offset 16, 4 bytes, length. Offset 20, 4 bytes, token count. Offset 24, 8 bytes, timestamp. Offset 32, 1 byte, role. Offset 33, 1 byte, type. Offset 34, 2 bytes, flags. Offset 36, 8 bytes, `idHi`. Offset 44, 8 bytes, `idLo`. Offset 52, 4 bytes, pad. Stride 56.

The frame header keeps `seq` at the message-id offset. Append `idHi` and `idLo` after the existing 36-byte header, or in a reserved tail before the payload. The CRC still covers the payload. Include the new words in the frame length. Spill carries identity inside the page bytes so a `.pg` file is self-describing. The index copy in memory is for speed.

**Truth.** Slot `seq - 1` stays the locator. The index stride becomes 56, with `idHi` at offset 36 and `idLo` at offset 44. The frame keeps a 36-byte header and gains the two longs.

**Reason.** The window and a status line need the identity without a page read. A spilled file must still name the message if the memory index is gone.

**Avoidances.** Do not leave the stride at 40 once identity is stored. Do not omit the new words from the frame length. Do not put the identity only in the memory index. Do not change the message-id offset of `seq`.

**semantic-key:** PK-5938

## 5. Page access path

Add, the window, and the message list find bytes by `seq`, then the index slot `seq - 1`, then `pageId`, offset, and length.

A hot page copies from the memory segment at that offset and length. A spilled page reads the channel at that offset and length. Identity is not consulted to find the page. A later `findById(hi, lo)` is an optional memory map from the id to `seq`. It is not a disk format change.

**Truth.** The page path is `seq` to the index to `pageId` plus offset. Identity never selects the page.

**Reason.** Spill and page-in change residency, not the sequence. A disk map from identity to slot would be a second format the scan does not need.

**Avoidances.** Do not consult the identity pair on add, window, or list. Do not change the disk format to add `findById`. Do not skip the index on a hot read.

**semantic-key:** PK-5939

## 6. Generator

One process-wide generator mints the pair with a 12-bit counter and a 62-bit random low half, and it never emits a smaller high word than the last one from this process.

The generator is one component with instance state, not scattered statics. State is `lastMs`, a 12-bit `counter` from 0 through 4095, and a source of 64-bit draws for `rand_b`. On the next id, take the current millisecond. If it is greater than `lastMs`, set `lastMs` to now and set the counter to a random draw masked with `0xFFF`, so the counter is seeded and does not always start at 0. If the millisecond is the same or the clock went backward, increment the counter under the same mask. If the counter wrapped, wait until the clock moves past `lastMs`, then seed the counter again. The high long is `(lastMs << 16) | (0x7 << 12) | counter`. The low long is variant bits `10` shifted by 62, ored with a 62-bit random draw.

Same-millisecond uniqueness on this process is the 12-bit counter, waiting for the next millisecond on wrap. 4096 ids per millisecond per process is far above chat. Uniqueness across the cluster is the 62-bit `rand_b` plus the 12-bit seed. No two nodes share a counter. The birthday bound at one millisecond is the industry bar at these rates. Clock rollback stays on `lastMs` and increments, so this process never emits a smaller high word than its last one. There is no node registry and no hexadecimal on this path. Allocation is two longs. A UUID object appears only if a debug edge asks. Do not use a random version-4 UUID. Do not call a string conversion. A platform helper that builds from epoch milliseconds is acceptable only if the bits are taken immediately and the object is dropped. Prefer a generator with no heap UUID.

**Truth.** The generator is process-wide, seeds a 12-bit counter, waits out a wrap, sets version 7 in the high word, sets variant 10 in the low word, and never goes backward in `idHi`.

**Reason.** A shared node id is extra key management. A version-4 UUID does not sort. A string form allocates on the hot path. Waiting on wrap keeps the same millisecond unique without a second registry.

**Avoidances.** Do not use a version-4 random UUID. Do not call a string conversion on this path. Do not keep a node registry. Do not emit a smaller `idHi` after a clock rollback. Do not start the counter at 0 on every new millisecond.

**semantic-key:** PK-5940

## 7. Uniqueness story

Inside one region, `(regionId, seq)` is unique by construction. Inside one process, `(idHi, idLo)` is unique by the generator. Across a cluster, uniqueness is probabilistic, the same class as a UUID, not a central lock.

Sixty-two or more random bits at chat rates is the working definition of a collision that will not happen. It is not guaranteed against two hosts with a broken or constant generator, or against ids written by hand. This design does not add a network consensus protocol. Cryptographic uniqueness, or coordinated exactly-once ids, is a different product and a ticket service. It is not this.

**Truth.** Regional uniqueness is exact. Cluster uniqueness is probabilistic and does not use a central lock.

**Reason.** A consensus protocol would be a second product. The random width is the ordinary bar for "will not collide" at these rates, and it fails closed only if the generator itself is broken.

**Avoidances.** Do not claim absolute cluster uniqueness. Do not add a network consensus protocol here. Do not treat a hand-written id as covered by the generator.

**semantic-key:** PK-5941

## 8. Class sketch

The later types are `org.roux.id.RouxId`, two longs with compare, equals, and segment read and write, and `org.roux.id.RouxIdGenerator`, an instance whose `next` returns a `RouxId` and is safe for threads.

This sketch is not a plan. Hot methods stay primitive-friendly: `next` returns the id, `write` places it on a segment at an offset, `read` loads it, and `compare` orders two ids. `appendMessage` takes or mints a `RouxId`, writes it on the frame, and copies the high and low longs into the index row. The adapter and the message list keep showing sequence brackets unless a later column is added. Message metadata may carry the two longs later. Do not put hexadecimal in the text body. A later console may print a decimal pair or hexadecimal only at the terminal edge.

**Truth.** Two types mint and store the pair. The list stays sequence-based. Hexadecimal stays off the core and off the text body.

**Reason.** The engine path must stay primitive. A string id in the message text would leak the identity into the model payload and into every list line.

**Avoidances.** Do not treat this sketch as a build plan. Do not put hexadecimal in the text body. Do not show the identity pair in place of the sequence brackets unless a later column is added. Do not print hexadecimal anywhere but a later terminal edge.

**semantic-key:** PK-5942

## 9. Impact on current code

When this is built, `seq` and the spill pointer stay, the index stride goes from 40 to 56, and the frame header goes from 36 to 52.

`seq` and `nextMessageId` are unchanged. Rotation and spill are unchanged, because the pointer is still `pageId` plus offset. `MonotonicIndex.STRIDE` goes from 40 to 56. `FrameHeader.SIZE` goes from 36 to 52 for the two longs. Frame CRC and length are recalculated. Old pages are a format bump, and the page header already has a version. The payload codec stays `r, ts, tok, x`. Do not stuff the 128-bit id into the payload string on the hot path. Bump `PageHeader.VERSION` so old 36-byte frames are not misread. The bump means new regions only, or a one-time reader that distinguishes sequence-only version 1 from sequence-plus-id version 2. Prefer the version bump. Version 1 regions die on hangup anyway.

**Truth.** The build changes stride 40 to 56, frame header 36 to 52, and the page version, and it leaves `seq`, spill, and the payload fields alone.

**Reason.** Misreading a 36-byte frame as a 52-byte frame corrupts the payload boundary. A version bump is cheaper than a migration, because a version 1 region does not survive hangup.

**Avoidances.** Do not change `seq` or the spill pointer. Do not put the 128-bit id in the payload string. Do not keep reading old frames at the old size after the version bump. Do not require a migration tool for regions that hangup already drops.


**semantic-key:** PK-3479

## 10. Locked for the restore slice

Disk-backed hangup keeps the user directory after a checkpoint and a manifest write. Memory-only does no pack I/O. This lock supersedes any earlier close checkpoint for `hot-only`.

Persist on hangup or process stop, when disk-backed, checkpoints the hot page, writes the manifest atomically, unmaps, closes the channel, and drops the lock. It does not delete the user directory. When memory-only, it unmaps only and does not write or delete the pack. Restore on SSH connect, when disk-backed, opens `{spill.dir}/{sanitized username}/` when `pages.bin` and a valid manifest both exist; otherwise it creates a new empty region. Half-present files or a CRC failure refuse and do not clobber. When memory-only, the pack is not opened; a new ephemeral memory session starts and a frozen pack stays put. The region and chat-memory key, when disk-backed, is the authenticated SSH username. It is not a hard-coded yaml SSH username, and it is not a new UUID on each connect. The manifest UUID is display-only and is reused across reconnects. When memory-only, the key stays today's session UUID, already minted per shell and already isolated in the engine's region map. The username is a label only. The connection's session id is a transport id, a byte array, one per SSH connection, shared by every channel on that connection. It may appear on status for correlation. It is not the engine key. Tenanting, when disk-backed, is one folder and one live writer per username. Concurrent different users are allowed. There are no cross-user reads. When memory-only, any number of independent memory regions exist and the pack is frozen. The engine already holds many regions; this slice uses that. User management and multi-user configuration are not this slice. There is no accounts database, no roles, no access lists, no extra passwords, and no admin commands. Authentication stays as today. The layout is ready when that product exists. Exclusive login is disk-backed only: one live SSH shell per username. A second connect as the same user is refused with a red line. Another username gets its own channel and its own `{dir}/{other user}/`. Memory-only, which is `hot-only` or spill off, turns exclusive off, so the same username may open multiple sessions. The exclusive mechanism, when disk-backed, is an in-process map from username to live session plus an operating-system file lock on `{user}/.lock`. Crash or process death releases the lock and the next open takes over. A second process on the same directory is refused. Memory-only has no exclusive map entry, no file lock, and no pack open. `clear` and `chat start`, when disk-backed, wipe the pack in place, keep the user folder, and keep the exclusive lock held. When memory-only, they wipe that shell's memory region only and do not touch a frozen pack. They re-insert the yaml system prompt. Restore does not re-insert the system prompt over existing history. The manifest size is 112. The CRC is last over `[0,104)`. The fields are the ones named on the manifest page. The scan is the source of truth. The manifest bounds the scan. The owner must match the username. A `page-bytes` mismatch refuses reopen with a red line. The operator reverts yaml or deletes that user directory. `hot-only` does no pack I/O: no rotate spill, no close checkpoint, no restore attach, and no exclusive. Multiple sessions per username are each ephemeral memory. This supersedes the earlier idea of checkpointing `hot-only` on close, which would re-introduce a disk writer. A hard kill or a hangup loses that session. `commit` and `append` keep persist, exclusive, and restore. The frame scan wins over the index. `nextSeq` is the max of the manifest value and one more than the highest seq seen. Slot `usedBytes` bounds each page. The first bad CRC stops that page. The username path allows `[A-Za-z0-9._-]+` only, rejects `..` and separators, refuses an invalid connect, and keeps case as logged in. The working set on restore maps the pack, rebuilds the memory index from headers only with no TOON decode, and loads the hot page into an arena. Cold pages page in on demand, as the pager already does. Close is idempotent. Shell destroy and process shutdown may both fire. Yaml keys are `roux.session.persist`, `roux.session.restore`, and `roux.session.exclusive`. Defaults are all true for this slice. The banner is `Restored N messages for {user}` against `New session`. Status already shows `roux: seq=…`. Out of the slice: torn-pack repair, replicas, named sessions, continuation frames, sharing one region across users, and a user-admin or multi-user configuration product.

**Truth.** Disk-backed close keeps `{spill.dir}/{user}/` after an atomic manifest write and drops `{user}/.lock` without deleting the directory. Memory-only never writes, locks, or restores the pack, and uses the session UUID. Mismatch and a bad CRC refuse. The scan wins. Defaults for persist, restore, and exclusive are true.

**Reason.** A close checkpoint on `hot-only` would make a second writer. Refusing a bad pack keeps the operator's files intact. The username is the only stable disk key that the next login already has.

**Avoidances.** Do not delete the user directory on a disk-backed close. Do not checkpoint `hot-only` on close. Do not key disk storage with a fresh UUID or with the connection session id. Do not hard-code the yaml SSH username as the region. Do not exclusive-lock memory-only sessions. Do not drop the exclusive lock on clear. Do not re-insert the system prompt over restored history. Do not clobber a half-present or CRC-failed pack. Do not add passwords, roles, access lists, admin commands, torn-pack repair, replicas, named sessions, continuation frames, or cross-user sharing.

**semantic-key:** PK-5943

## 1. Why it does not restore today

The live pack can already hold message bytes, and the way out throws them away. Restore is stop deleting, name the folder by the session owner, and rebuild the memory caches from the log.

`ChatShell.destroy` calls `ChatSession.close`, which calls `safeFreeRegion`, which deletes the directory. Process shutdown destroys every region the same way. The directory name is a random UUID, not the SSH user, so a file that survived would still not be found on the next login. What exists only in memory, and is gone on restart even if files survived, is the monotonic index (`seq` to page id plus offset), the id-to-seq map, the hot arena as the writable tail, the next message id and the next page id, and the conversation id UUID used as the region key. The low-hanging change is not a new log.

**Truth.** Close deletes a UUID directory, so the next login cannot find the pack. The fix is keep the files, key the folder by owner, and rebuild the index from the frames.

**Reason.** The message bytes are already in `pages.bin` slots. A second log would duplicate a record the frames already are.

**Avoidances.** Do not add a new log to get restore. Do not keep keying the directory with a fresh UUID. Do not expect a leftover UUID folder to be found on the next login.

**semantic-key:** PK-5944

## 2. Product change

The only policy shift is what happens on the way out: a disk-backed close keeps the pack, and a memory-only close unmaps and leaves any pack frozen.

On SSH hangup or exit, the earlier behavior deletes the region directory. The restore behavior, when durability is disk-backed, checkpoints the hot page, writes the manifest, unmaps memory, and keeps the files. When the region is memory-only, it unmaps only and the pack stays frozen. A process stop does the same thing for each open region, where the earlier behavior deleted all of them. `clear` and `chat start` still wipe and open a new page 0, because the user asked to forget. A process crash leaves the hot page only as durable as `roux.durability` already made it. The happy path assumes a clean shutdown, or durability `commit` or `append`. Reconnect as the same SSH user opens that user's pack and the history is at the prompt. Disk-backed means `commit` or `append` with spill on: one live region per SSH username. Memory-only means `hot-only`, or spill off: no pack I/O, the exclusive rule waived, and any number of independent memory sessions for that username. Named sessions such as `chat session foo` come later.

**Truth.** Disk-backed hangup and process stop keep the user's files after a checkpoint and a manifest write. Memory-only hangup unmaps and does not touch the pack. Clear still wipes.

**Reason.** Naming the folder by the SSH user is what makes the next login find the history. Clear stays a wipe because the user asked to forget, not to archive.

**Avoidances.** Do not delete a disk-backed directory on hangup or process stop. Do not write a pack from a memory-only close. Do not turn clear into a keep. Do not add named sessions in this slice. Do not promise crash recovery beyond what `roux.durability` already wrote.

**semantic-key:** PK-5945

## 3. On-disk layout

The pack file stays, and a small sibling manifest is added beside it. The manifest is not written into page 0.

Under `{roux.spill.dir}/` the folder is the sanitized SSH username. Inside it, `pages.bin` is the existing pack, with slot equal to page id times page bytes. `manifest.bin` is the new fixed header of primitives only. Putting that header in page 0 would shift every slot offset. The folder is not keyed by the ephemeral UUID. The UUID is stored inside the manifest when status still wants it. The username class is `[A-Za-z0-9._-]+`. `..` and path separators are rejected.

**Truth.** The directory is `{roux.spill.dir}/{sanitized username}/` and holds `pages.bin` plus `manifest.bin`. Page 0 is not the manifest. The UUID is display data inside the manifest.

**Reason.** A sibling header leaves slot math at page id times page bytes. A UUID folder cannot be found from the next login's username.

**Avoidances.** Do not cram the manifest into page 0. Do not name the folder with the ephemeral UUID. Do not accept `..` or a path separator in the username. Do not accept a character outside `[A-Za-z0-9._-]+`.

**semantic-key:** PK-5946

## 4. Manifest

The manifest is a fixed 112-byte header. The CRC is last and covers every byte before it, including the UUID and the owner.

Longs and ints stay 8-byte aligned, in the same foreign-memory discipline as the rest of the pack. JSON is not required. The size is 112. Offset 0 is 4 bytes of magic `RXM1`. Offset 4 is a 2-byte meta version, value 1. Offset 6 is a 2-byte flags field, value 0. Offset 8 is a 4-byte `pageBytes` and must match yaml on reopen or the open is refused. Offset 12 is a 4-byte `pageCount`, the slots that exist, which is the next page id. Offset 16 is a 4-byte `hotPageId`, the last writable slot. Offset 20 is a 4-byte `hotUsedBytes`, a hint; the slot header and the scan win. Offset 24 is an 8-byte `nextSeq`, a hint; the log wins. Offset 32 is an 8-byte `lastWriteEpochMs`. Offset 40 is an 8-byte `createdEpochMs`. Offset 48 is a 4-byte `formatVersion`, the page header version, value 2. Offset 52 is 4 bytes of pad. Offset 56 is the 8-byte region UUID high half, display only; the lookup key is the username. Offset 64 is the 8-byte region UUID low half. Offset 72 is 32 bytes of owner, UTF-8 padded, and it must match the folder and the SSH user. Offset 104 is the 4-byte CRC32C of bytes `[0,104)`. Offset 108 is 4 bytes of pad to 112. The write is `manifest.bin.tmp`, then force, then an atomic replace of `manifest.bin`, the same discipline as a page publish. `pages.bin` is not rewritten to publish metadata. The minimum that actually reloads is `pageBytes` (refuse if yaml changed), `pageCount` and `hotPageId` (how far to scan, and which slot is hot), `hotUsedBytes` as a hint only, `nextSeq` as a hint with the log winning when the scan finds a higher seq, `formatVersion` (refuse unknown), the CRC of the whole header except the CRC word, and `owner` equal to the sanitized SSH username (folder versus user mismatch refuses). `nextSeq` and the index contents are hints. A happy-path reopen scans frames and treats the log as truth. The manifest exists so a sparse hole, up to 4 GiB, is not walked blindly: the walk is only `0 .. pageCount-1`. Each page's walk bound is that slot's `usedBytes`. The first bad frame CRC stops that page. There is no repair and no walk into garbage. Not needed in this slice: system-prompt text (it is already frame 1, and restore does not re-insert it), window sizes (they are yaml), generator state (new ids stay unique), the pin table, and the working-set list (page in the hot page only; cold pages come in on demand as the pager already does).

**Truth.** `manifest.bin` is 112 bytes, magic `RXM1`, meta version 1, flags 0, format version 2, CRC32C at offset 104 over `[0,104)`, owner 32 bytes at offset 72. The scan is truth. The manifest only bounds that scan.

**Reason.** A fixed primitive header can be forced and replaced without shifting pack slots and without a JSON parse on open. Bounding the walk by `pageCount` is what keeps a sparse file from being read as if every hole were a page.

**Avoidances.** Do not put the CRC anywhere but last over `[0,104)`. Do not treat `hotUsedBytes` or `nextSeq` as stronger than the slot header and the frame scan. Do not scan past `pageCount - 1`. Do not repair a bad frame CRC. Do not re-insert the system prompt on restore. Do not rewrite `pages.bin` just to publish this header. Do not store window sizes, generator state, the pin table, or the working-set list in the manifest.

**semantic-key:** PK-5947

## 5.1 Close

A disk-backed close checkpoints the hot page, writes the manifest, unmaps, closes the channel, and does not delete the directory. This heading's earlier sentence also checkpoints once on a `hot-only` close; the lock later on this page withdraws that sentence.

The steps this heading names are `checkpointHot` (the same helper durability already uses), `writeManifest` with page count, hot page id, hot used bytes, next seq, and page bytes, unmap every arena, close the file channel, and do not delete the directory. The sentence that stands only in this heading says that if durability is `hot-only`, still checkpoint once on close so a restart can see the tail, and that this is the cheap way to make restore work, while a live `hot-only` session stays memory-only during the session. The lock at the end of this page supersedes that sentence: memory-only means no pack I/O, including no close checkpoint, because a close checkpoint would put a disk writer back. What remains from this heading for a disk-backed close is the checkpoint, the manifest, the unmap, the channel close, and the kept directory.

**Truth.** Disk-backed close writes the hot checkpoint and the manifest and keeps the directory. A `hot-only` close does not checkpoint. The checkpoint-on-close idea for `hot-only` is the earlier sentence, withdrawn by the later lock.

**Reason.** Restore needs the tail on disk after a clean disk-backed shutdown. Writing that tail from a memory-only session would make two sessions two writers.

**Avoidances.** Do not delete the directory on a disk-backed close. Do not checkpoint, spill, or otherwise write the pack on a `hot-only` close. Do not keep the earlier `hot-only` close checkpoint once the later lock is in force.

**semantic-key:** PK-5948

## 5.2 Open

Open reads the user's pack, checks the manifest, and rebuilds the index from frame headers. A page-bytes mismatch is refused. Starting empty on top of that mismatch is the option this heading left open and does not take.

The path is the spill directory plus the sanitized username. If `pages.bin` and the manifest are both missing, this is a new region, seq starts at 1, and the system prompt is written, which is today's behavior. Otherwise read the manifest and check its CRC. This heading says a `pageBytes` mismatch with yaml is an error to the shell, and then either start empty or refuse, and it recommends refuse plus a red line. The lock on this page takes refuse. Open the file channel on `pages.bin`. For each page id from 0 through `pageCount - 1`, read the slot header to get `usedBytes`, walk the frames in the slot, append each to the index as seq, page id, and offset, and put the id to seq. Load the hot page id's slot into an arena, writable. `nextSeq` is the max of the manifest's `nextSeq` and one more than the highest seq the scan saw. `nextPageId` is `pageCount`. No TOON decode is required to rebuild the index. Headers are enough. Decode happens on the first get or message list, as today.

**Truth.** A missing pack and manifest create a new region at seq 1 and write the system prompt. A present pack is scanned from headers only. `nextSeq` is `max(manifest.nextSeq, maxSeqSeen + 1)`. A page-bytes mismatch is refused, not replaced with an empty region.

**Reason.** The index is a cache of frame headers. Decoding TOON on the way in would do work the first read already does. Starting empty over a mismatched pack would hide the operator's files.

**Avoidances.** Do not decode TOON while rebuilding the index. Do not start an empty region on top of a page-bytes mismatch. Do not let the manifest seq override a higher seq found in the log. Do not walk pages outside `0 .. pageCount - 1`.

**semantic-key:** PK-5949

## 5.3 Clear

`clear` and `chat start` wipe the frames to a new page 0, rewrite an empty manifest, and keep the user folder.

The next restart is an empty chat. It is not a resurrected copy of the turns the user asked to forget. The folder stays so the same username still owns the same directory.

**Truth.** Clear wipes frames and rewrites an empty manifest in the same user folder. The next open is an empty chat.

**Reason.** The user asked to forget. Deleting the folder would also drop the owner key the next login uses to find that empty chat.

**Avoidances.** Do not delete the user folder on clear. Do not leave the old frames in place. Do not let the next restart bring the wiped turns back.

**semantic-key:** PK-5950

## 5. Happy-path flows

Close keeps the directory after a checkpoint and a manifest write, open rebuilds the index by scanning the pack, and clear wipes in place.

The three flows are the close on hangup or process stop, the open on SSH connect, and `clear` or `chat start`. Each flow has its own rule on the following pages. Together they are the happy path: a clean shutdown can be reopened, and an explicit forget does not resurrect the old turns.

**Truth.** The happy path is close without delete, open by scan, and clear as an in-place wipe.

**Reason.** Those three events are the only lifecycle changes this slice needs. The pager already stores and reads the frames.

**Avoidances.** Do not add a fourth flow for catastrophe repair. Do not treat clear as a close that keeps history. Do not delete the directory on the close flow.

**semantic-key:** PK-5951

## 6. What the pager already gives

This slice is lifecycle policy, one manifest file, and a scan that rebuilds memory. It is not a new pager.

Frames already carry seq, the two identity halves, role, timestamp, token count, and CRC. `pages.bin` is already random-access by page id. The region pack already caches a channel. Durability already knows how to write the hot slot at a file position. Those four facts are the whole free foundation.

**Truth.** Seq, identity, role, timestamp, tokens, and CRC are already on the frame. The pack is already addressable by page id. The hot slot is already a positioned write. The new pieces are the manifest and the open scan.

**Reason.** Rebuilding an index from headers does not need a second storage format. The pager already publishes and rereads the bytes.

**Avoidances.** Do not design a new pager for restore. Do not drop seq, the identity halves, role, timestamp, tokens, or CRC from the scan's inputs. Do not ignore the cached channel or the existing hot-slot write.

**semantic-key:** PK-5952

## 7. Gaps

Exclusive is a disk-tenant lock, not a rule that only one human may log in. Memory-only sessions do not write the pack. The seals below are closed for this slice.

User B may connect while user A is live. Each disk-backed user has one folder and one live writer: `{spill.dir}/{user}/`. There is no user-admin product in this slice: no accounts database, no roles, no password file, and no add-user commands. Authentication stays whatever the process already does. Tenanting is directory isolation plus the exclusive lock, so a later user-management slice can hang on the same layout. Memory-only is `roux.durability: hot-only`, including the alias `h0`, and also `roux.spill.enabled: false`. That mode does no pack writes: no rotate spill, no close checkpoint, and no file lock on `{user}/`. The same username may open multiple SSH sessions. Each is an independent ephemeral region, not one shared arena. Hangup drops that session's memory. Any existing `{user}/` pack is frozen: not opened, not restored, not overwritten, until durability is disk-backed again. Two SSH logins as the same user are refused when disk-backed, with a red line that the session is already open, and allowed when memory-only, each shell its own region. Different usernames are independent either way. A stale exclusive after a crash or kill is taken over because the in-process map is empty on a new process and the operating-system file lock on `{user}/.lock` (this heading also names `pages.bin` as an alternate target) is released when the process dies. Two processes on the same spill directory use that same file lock: the second gets "session already open" or region in use. There is no shared-writer protocol. A yaml `page-bytes` change refuses reopen. The operator deletes that user folder or reverts yaml. A half-present pack, one of `pages.bin` or a valid manifest but not both, is refused. Do not start empty on top of leftover bytes. The operator deletes the user directory. A torn manifest is an atomic temporary plus rename, and a CRC failure refuses the same way as half-present. `hot-only` plus a hard kill or a hangup loses that session's memory. Nothing was written, so nothing reloads for that session. A frozen pack from an earlier `commit` or `append` is unchanged. A memory-only session must not position-write `{user}/pages.bin`, or two sessions would be two writers. Restore, persist, and exclusive apply only when durability is disk-backed. The memory-only region key cannot be the username alone, because two live shells would share one arena. Keep the session UUID already minted when the shell is created. The SSH session id is a fine label and not the map key: it is per connection, not per shell channel. The disk-backed key remains the username. A sparse `pages.bin` is walked only for `0 .. pageCount - 1`, and each page stops at the slot `usedBytes`. A bad frame CRC mid-scan stops that page and does not repair. The happy path assumes a clean close. Usernames allow `[A-Za-z0-9._-]+` only, reject `..` and separators, and an invalid name refuses the connect. Case is kept as logged in. The owner field must match. Status may show the conversation UUID. The UUID stays in the manifest for display. The engine and chat-memory key is the username. If the index and the log disagree, the scan wins, and `nextSeq` is the max of the manifest value and one more than the highest seq seen. On Windows, close the file channel and release the file lock inside `close` before process exit. `close` is idempotent, because shell destroy and process shutdown may both run. Restore does not rewrite frame 1. `clear` and `chat start` re-insert the yaml system prompt after the wipe. `persist`, `restore`, and `exclusive` default to true. `persist` false is today's delete. `restore` false leaves files idle when persist was on, and does not auto-delete. Out of this slice: torn-pack and bit-rot repair, replica acknowledgement, named sessions such as `chat session foo`, continuation frames, sharing one region across users, and a user-admin interface or multi-user configuration (password lists, roles). The tenant layout is in.

**Truth.** Disk-backed exclusive is one writer per username, enforced by the in-process map and a file lock that dies with the process. Memory-only does no pack I/O and isolates each shell by UUID. Mismatch, half-present files, and a bad CRC refuse. The scan wins over the manifest.

**Reason.** Two writers on one pack corrupt it. Refusing a partial directory keeps leftover bytes visible. A username key cannot serve two live memory-only shells without merging their arenas.

**Avoidances.** Do not make exclusive a single global human login. Do not add accounts, roles, a password file, or add-user commands. Do not write or lock a pack from `hot-only` or from spill off. Do not key a memory-only region by username or by the SSH connection id. Do not start empty on a half-present directory. Do not repair a bad frame. Do not walk past `pageCount`. Do not rewrite frame 1 on restore. Do not auto-delete when `restore` is false. Do not build torn-pack repair, replicas, named sessions, continuation frames, cross-user sharing, or a user-admin product in this slice. The later lock names `{user}/.lock` as the lock file; do not switch the lock onto `pages.bin`.

**semantic-key:** PK-5953

## 8. Console

If commands are touched, the banner tells a restored session from a new one, and status already carries the seq that should be nonzero after restore. No new command is required for restore itself.

The banner is `Restored N messages for {user}` against `New session`. Status already has `roux: seq=…`. After a restore those numbers should be nonzero. `clear` remains the way to forget.

**Truth.** The banner is `Restored N messages for {user}` or `New session`. Status seq is nonzero after a restore that had messages. Clear is still the forget command. Restore adds no command of its own.

**Reason.** The operator needs to see that the reopen loaded history, and already has seq on the status line. Forget stays the existing clear.

**Avoidances.** Do not add a restore command. Do not drop the seq from status. Do not replace clear as the way to forget. Do not print a restored banner for a brand-new session.

**semantic-key:** PK-5954

## 9. Slice shape

The build order is the manifest, a close that does not delete, an open that scans, the SSH username as the region key, and exclusive connect only when the region is disk-backed.

First, manifest read and write with primitives and CRC. Second, close means checkpoint plus manifest plus unmap, and no delete. Third, open by username scans `pages.bin` into the memory index and the hot arena. Fourth, the session and the shell factory pass the SSH username as the region key, or map the user to the folder and keep the UUID in the manifest. Fifth, exclusive connect applies when disk-backed. Memory-only allows any number of sessions per username. Clear wipes files in place, and that wipe is disk-backed only. That is the lifecycle and the exclusive behavior the paging document already named, constrained to the happy path.

**Truth.** Five steps: manifest, close without delete, scan on open, username as the disk key, exclusive only when disk-backed. Clear's in-place wipe is disk-backed only.

**Reason.** Each step sits on the pager that already exists. The happy path does not need repair, replicas, or a new command.

**Avoidances.** Do not delete on close. Do not exclusive-lock a memory-only session. Do not wipe a frozen pack from a memory-only clear. Do not widen this order into catastrophe repair.

**semantic-key:** PK-5955

## 0. Goal

Happy-path persist and reload keeps a disk-backed directory across hangup, rebuilds the memory index on the next login, refuses a second live disk session for that user, and does no pack I/O when the session is memory-only.

Hangup or process stop keeps `{roux.spill.dir}/{ssh username}/` when durability is disk-backed. The next SSH login as that user rebuilds the memory index from `pages.bin` and `manifest.bin`. The same username on a second live SSH session is refused, and that refuse is disk-backed only. `hot-only`, or spill off, does no pack I/O, turns exclusive off, and allows any number of independent memory sessions under the existing session UUID. This is lifecycle policy, one manifest, a scan that rebuilds the index, and an exclusive connect. It is not a new pager.

**Truth.** Disk-backed close keeps the user directory. The next login scans `pages.bin` plus `manifest.bin`. A second live disk session for that user is refused. Memory-only sessions stay independent UUIDs with no pack I/O.

**Reason.** The pager already stores frames. The missing behavior is keep, name, scan, and one writer per disk tenant.

**Avoidances.** Do not build a new pager for this goal. Do not refuse a second memory-only session. Do not write a pack when durability is `hot-only` or spill is off. Do not drop the directory on a disk-backed hangup.

**semantic-key:** PK-5956

## 10. Split

If the slice is split, five landings follow the package order. On this branch, landing them as sequential commits is enough.

The first landing is `roux.session` yaml and the property helpers, and it depends on nothing. It ships configuration only. The second is the owner, the manifest, and the pack file lock with wipe-in-place. It depends on the first and ships an unused library. The third is engine open, persist close, the scan, memory-only with no I/O, and shutdown that keeps the directory. It depends on the second. It ships engine behavior, while the host still uses a UUID and still deletes until the fourth landing. The fourth is session keying, the reject shell, exclusive, the banner, and status. It depends on the third and ships end-to-end restore. The fifth is the specification and diagram catch-up. It depends on the fourth and ships the docs. Sequential commits of all five on the working branch are enough. A date-stamped branch name is not part of the rule.

**Truth.** Five landings, each depending on the previous: config, unused library, engine keep-on-close, host restore, then docs. The host still deletes until the fourth landing.

**Reason.** The engine can keep directories before the shell knows the username. Shipping the shell first would still mint UUIDs and delete them.

**Avoidances.** Do not ship host keying before the engine can open and persist-close. Do not treat the third landing as the end of UUID deletion in the host. Do not require a branch name as a product rule.

**semantic-key:** PK-5957

## 11. Definition of done

A disk-backed hangup keeps the pack and the manifest, a reconnect restores history, a second disk login is refused, and a memory-only session never writes the pack.

Disk-backed hangup keeps `{spill.dir}/{user}/pages.bin` and `manifest.bin`. Reconnect as that user restores history. The banner and status agree. A second disk-backed login as the same user is refused. Another username is independent. `hot-only` allows multiple sessions, has no pack writer, and isolates by UUID. A quiet compile that skips tests is green. The yaml keys are documented as live, not proposed. The packages are recorded as landed.

**Truth.** Hangup keeps `pages.bin` and `manifest.bin`. Reconnect restores and the banner matches status. The same user is refused a second disk session. `hot-only` is multi-session, UUID-isolated, and write-free. The keys are documented as live. The compile that skips tests is green.

**Reason.** Those are the four behaviors the goal named, plus the proof that the tree still builds and the yaml page no longer says proposed.

**Avoidances.** Do not call the slice done while hangup deletes the directory. Do not call it done while a second disk login succeeds. Do not call it done while `hot-only` writes under the user directory. Do not leave the yaml keys marked proposed.

**semantic-key:** PK-5958

## 1. Out of this slice

Torn-pack and bit-rot repair, replica acknowledgement, named sessions, continuation frames, a shared arena, user administration, encryption, copy-on-write, a command-line tool, and telemetry export are out. Leftover UUID folders are not migrated.

Named sessions means `chat session foo`. Sharing one arena across users or shells is out. User administration and multi-user configuration means password lists, roles, and an accounts database. Encryption, copy-on-write, a Roux command-line tool, and export of telemetry are out. There is no migration of leftover paging-slice `{uuid}/` folders. That slice's hangup already deleted them. Crash leftovers stay ignored.

**Truth.** This slice does not repair torn packs, replicate, name sessions, continue frames across pages, share an arena, administer users, encrypt, copy on write, add a command-line tool, or export telemetry. UUID crash leftovers stay ignored.

**Reason.** Happy-path reopen is the whole slice. A UUID folder from a crash is not the username key, and a clean hangup of the older slice already removed its directories.

**Avoidances.** Do not migrate `{uuid}/` leftovers. Do not delete ignored crash leftovers as part of this slice. Do not pull password lists, roles, or an accounts database in. Do not add encryption or a second writer protocol.

**semantic-key:** PK-5959

## 2. Key decisions

The disk key is the sanitized SSH username, the memory-only key stays the session UUID, exclusive is disk-backed only, and a mismatch refuses without clobbering.

The disk region key is the authenticated SSH username, sanitized to `[A-Za-z0-9._-]+`. The memory-only region key is the existing session UUID, not the connection's session id. Exclusive applies to disk-backed regions only. Inside one process it is the engine's region map. Across two processes it is a file lock on `{user}/.lock`. Memory-only is `hot-only` or `spill.enabled` false: no rotate spill, no close checkpoint, no restore attach, and the pack stays frozen. `isRamOnly` is true when normalized durability is `hot-only` or spill is not enabled. Otherwise the process is disk-backed. Durability is process-wide yaml. One process is not mixed-mode. The manifest size is 112. The CRC is last over `[0,104)`. The write is an atomic temporary plus rename. A mismatch or a half-present pack refuses reopen and does not clobber. The log wins over the manifest. `pageCount` bounds the walk. The first bad frame CRC stops that page. `clear` and `chat start` wipe in place on disk, or wipe that memory region only when memory-only, and re-insert the yaml system prompt. Close is idempotent: shell destroy and the engine's shutdown hook may both run. User administration is not this slice. Tenanting is the folder plus the exclusive lock.

**Truth.** Disk key is the sanitized username. Memory-only key is the session UUID. Manifest is 112 bytes with CRC32C over `[0,104)`. Mismatch refuses. The scan wins. `isRamOnly` is hot-only or spill disabled. Durability is process-wide.

**Reason.** Two processes need a file lock the operating system drops on death. One process can see the username in its own region map because that username is the disk key. A mixed durability inside one process would make the pack both frozen and writable.

**Avoidances.** Do not key memory-only regions with the connection session id. Do not apply exclusive when memory-only. Do not clobber a mismatch or a half-present pack. Do not walk past `pageCount` or past the first bad frame CRC. Do not let close run twice and delete or double-unlock incorrectly. Do not add user administration. Do not run one process in mixed durability.

**semantic-key:** PK-5960

## 3. As-built versus target

The shell factory must read the username before the session starts, and a disk-backed session must open the pack before any UUID region is allocated.

Today the session mints a UUID and ensures the system prompt immediately. The target, on disk, uses the username as the region id, opens the pack first, and writes the system prompt only if the region is empty. In memory it keeps the UUID. Today the shell factory ignores the username. The target reads `channel.getServerSession().getUsername()` in `createShell`, where the name is available before `start`. Today `safeFreeRegion`, shutdown, and `clearRegion` call `regionPack.deleteRegion`. The target persist close checkpoints, writes the manifest, unmaps, and drops the lock, and keeps the directory. Memory-only unmaps only and does not touch a frozen pack. Today `rotateHot` publishes whenever spill is enabled. The target skips publish when memory-only, so `hot-only` must not write even if spill is on. Today the folder is the region UUID. The target folder is the sanitized username. Today there is no manifest. The target writes `{user}/manifest.bin`. Today two SSH sessions as the same user are two UUID regions. The target refuses the second when disk-backed, and keeps two UUID regions when memory-only, which is today's isolation. The username is already captured in `ChatShell.start` for the `ssh-user:` status line. Region keying must happen in `createShell`, because the session currently allocates the region in its constructor.

**Truth.** Disk region id is the username, chosen in `createShell` from `getUsername()` before start. The system prompt is written only when the opened region is empty. Persist close keeps the directory. Memory-only skips publish. The manifest is `{user}/manifest.bin`.

**Reason.** The session constructor already allocates a region. Waiting until `start` would mint a UUID before the username could become the key.

**Avoidances.** Do not ignore the username in `createShell`. Do not allocate a UUID region for a disk-backed connect. Do not publish on rotate when memory-only. Do not delete the directory on a persist close. Do not keep two disk regions for one username.

**semantic-key:** PK-5961

## 4. Shape

The engine does not learn SSH. Exclusive inside one process is the region map keyed by username. The file lock is only for a second process on the same spill directory.

`org.roux.session` holds `RegionOwner` (sanitize and validate the username, memory-only, path-safe), `RegionManifest` (a 112-byte foreign-memory header, CRC, atomic write), and `OpenResult` (`NEW`, `RESTORED`, or `REFUSED`, plus counts and reason). `org.roux.spill.RegionPack` holds the file lock on `{user}/.lock`, keeps the directory (no delete on a persist close), and offers `wipePackInPlace` for clear. `org.roux.RouxEngine` holds `isRamOnly`, `openRegion(regionId)` which scans or creates, `persistClose(regionId)` which checkpoints, writes the manifest, and unmaps without delete, a `rotateHot` that does no pack I/O when memory-only, a shutdown that persist-closes each region, and a `clearRegion` that wipes the pack in place when disk-backed. `org.roux.spring.RouxChatMemory` is thin wrappers: open, persist close, and `isRamOnly`. `org.casserole.ssh.shell` holds the shell factory (username, exclusive, open versus ephemeral), a reject shell (red line and exit for an invalid user, an exclusive clash, or a refused reopen), and `ChatShell.start` (banner `Restored N` or `New session`). `org.casserole.chat.ChatSession` is built by `forUser(username, openResult)` or by `ephemeral()`, and carries a restored flag plus the display UUID. Because the disk key is the username, one process checks `regions.containsKey(username)`. `FileLock` exists for a second process on the same `spill.dir`.

**Truth.** Owner, manifest, and open result live in `org.roux.session`. The pack locks `{user}/.lock` and can wipe in place. The engine opens, persist-closes, and skips pack I/O when memory-only. The shell learns the username; the engine does not learn SSH.

**Reason.** A username key makes the in-process exclusive check a map lookup. A second process cannot see that map, so it needs the file lock.

**Avoidances.** Do not teach the engine SSH. Do not delete the directory from the pack on a persist close. Do not put exclusive policy only in the shell with no file lock. Do not share one file lock across memory-only sessions.

**semantic-key:** PK-5962

## 5. Work packages

The slice lands as one ordered series. Each package leaves the tree compiling. Later packages depend on earlier ones. The packages are the review boundaries.

The five packages are yaml and properties with no behavior change, then owner plus manifest plus pack lock still unused by the engine, then engine persist and restore and memory-only I/O, then host wiring with exclusive and the banner, then the docs. Implement them on the current branch as one slice.

**Truth.** Five ordered packages, each compiling, each depending on the ones before it, landed as one slice.

**Reason.** The engine cannot open a manifest that does not exist, and the shell cannot banner a result the engine does not return. Splitting the review does not split the behavior into parallel work.

**Avoidances.** Do not reorder the packages. Do not land a later package before the ones it depends on. Do not treat the package boundaries as five independent products.

**semantic-key:** PK-5963

## 7. Sequencing

The username is known in `createShell`. A disk session opens before any UUID allocation. Close is idempotent. `hot-only` does not write even when spill is enabled.

Do not wait for `ChatShell.start` to learn the username. The session constructor must not allocate a UUID region when disk-backed. Open first, then attach. `close` is idempotent because shell destroy and process shutdown may run in either order, including when the channel is already gone. `hot-only` with `spill.enabled` true still must not write. Exclusive is waived only because there is no pack writer. Rotate, persist close, and restore attach all skip the pack. Two memory shells cannot share the username as the region id. Keep the UUID. The connection session id is a transport id, shared by every channel on that connection. It is a status label only, not the map key. A disk `clear` must not drop the exclusive lock. A second SSH session is still refused. On Windows, close the `pages.bin` channel and the `.lock` channel before process exit so a reconnect can open. An atomic move may fail; the fallback is delete then move. Scan `usedBytes` comes from the slot header, not blindly from the manifest's hot used bytes. The manifest bounds which slots. The header bounds how far into the slot. Restore does not rewrite frame 1. `clear` and `chat start` re-insert the yaml text. Message list, the window, and inspect stay unchanged once the index exists. There is no TOON on the restore path.

**Truth.** Create the shell with the username already in hand, open before attach, and make close idempotent. Memory-only never writes. Disk clear keeps the lock. The slot header bounds the scan. Frame 1 is not rewritten on restore.

**Reason.** A constructor that allocates first cannot be talked out of a UUID. A manifest hint that overrides the slot header would walk padding or stop short of real frames.

**Avoidances.** Do not wait until `start` for the username. Do not allocate a disk region in the session constructor. Do not write from `hot-only` just because spill is enabled. Do not key memory shells by username or by the connection id. Do not drop the exclusive lock on clear. Do not leave `pages.bin` or `.lock` open across process exit on Windows. Do not trust `hotUsedBytes` over the slot header. Do not rewrite frame 1 on restore. Do not decode TOON to rebuild the index.

**semantic-key:** PK-5964

## 8. Verification

There is no unit-test suite. The check is a quiet compile that skips tests after every package, then an SSH smoke under the default yaml. If a browser or other UI is not in play, the SSH list is the verification. The terminal client stays unchanged.

The default yaml for the smoke is durability `commit`, with persist, restore, and exclusive true. One login, one turn, exit, process still up, login again: the banner is `Restored N`, status seq is at least that N, and the last turn is visible in the message list. A second login as the same user while the first is live: the second gets a red `session already open`, and the first still works. After the first exits, the second login restores the first's history. `clear` or `chat start`, then exit, then login: the restore is empty aside from the system prompt, not the old turns. A clean process stop, then start, then login: history comes from the last persist close. With durability `hot-only`, two logins as the same user both connect, with independent histories, and nothing is written under `{spill.dir}/{user}`. A `hot-only` hangup and a login again banners `New session`. A frozen pack left over from `commit` is not attached. Changing `page-bytes` and logging in against an existing pack gives a red mismatch and leaves the files untouched. Status shows `ssh-user`, `conversation` equal to the username on disk or the UUID in memory, and `roux: seq=` nonzero after a restore.

**Truth.** Default smoke yaml is `commit` with persist, restore, and exclusive true. The nine checks cover reopen, exclusive refuse, restore after exit, clear, clean process restart, two `hot-only` sessions with no pack writes, a `hot-only` hangup that banners `New session` and does not attach a frozen pack, a page-bytes mismatch that does not touch files, and status fields after restore.

**Reason.** The behavior is a lifecycle across two connects. A unit suite is out of the slice, so the SSH session is the check, and the terminal client does not change to make that check possible.

**Avoidances.** Do not add a unit-test source for this slice. Do not require a browser when the SSH list is available. Do not change the terminal client. Do not treat a `hot-only` reopen as a restore of a frozen pack. Do not accept a page-bytes change that rewrites the existing files.

**semantic-key:** PK-5965

## 9. Constraints

No unit tests are added. No lambdas, streams, or method references appear in the host or the engine. The frame header stays 56 bytes. A CRC failure mid-scan stops that page.

No test source is added for this slice. The host and the engine take no lambdas, streams, or method references. No host-framework types appear in `org.roux` except `org.roux.spring`. The frame header stays 56 bytes. The manifest is not crammed into page 0. Do not add user-management commands or a second yaml password. Do not implement catastrophe repair when a CRC fails mid-scan: stop that page.

**Truth.** The frame header remains 56 bytes. The manifest stays out of page 0. A mid-scan CRC failure stops that page. This slice adds no tests, no lambdas, no streams, no method references, no user-management commands, and no second yaml password.

**Reason.** The page header and the frame header already have their widths. Folding the manifest into page 0 would move every slot. Repairing a bad frame is the catastrophe work this slice refused.

**Avoidances.** Do not grow or shrink the 56-byte frame header. Do not store the manifest in page 0. Do not repair past a bad CRC. Do not add a password field or a user-admin command. Do not import the host framework outside `org.roux.spring`.

**semantic-key:** PK-5966

## P1. Yaml and properties

Add `roux.session` with persist, restore, and exclusive, each defaulting to true, and do not change behavior in this package.

The files are `RouxProperties.java`, `application.yaml`, and the application yaml specification. The keys bind as follows. `persist` defaults to true and means hangup and shutdown keep files when disk-backed. `restore` defaults to true and means reopen the pack on connect when disk-backed. `exclusive` defaults to true and means refuse a second disk-backed shell for that user. Helpers on `RouxProperties`, with no lambdas: `normalizedDurability` already exists; `isRamOnly` is hot-only or spill disabled; `isDiskBacked` is the opposite of `isRamOnly`; `sessionPersist`, `sessionRestore`, and `sessionExclusive` are true when the key is omitted. `persist` false on a disk-backed region keeps today's delete. `restore` false leaves files idle and does not auto-delete. The exclusive yaml value is ignored when memory-only. Move the session keys on the application yaml page from proposed to current.

**Truth.** `roux.session.persist`, `restore`, and `exclusive` default to true. Omitted means true. `persist` false deletes as today. `restore` false leaves files idle. Exclusive is ignored when memory-only. This package changes no runtime behavior beyond the new fields.

**Reason.** The engine and the shell need a single place to read the three flags before any open or close changes. Defaults true match the restore lock.

**Avoidances.** Do not auto-delete when restore is false. Do not honor exclusive yaml for a memory-only process. Do not leave the session keys marked proposed. Do not use lambdas in the helpers. Do not change hangup behavior in this package.


**semantic-key:** PK-3482

## P3. Engine persist and restore

`openRegion` scans or creates, `persistClose` keeps a disk directory and never touches a frozen pack, and a memory-only rotate does not publish.

The files are `RouxEngine.java`, `RouxChatMemory.java`, and, if a new method belongs on an interface, the memory state controller or the memory layout manager. Memory-only open is today's `allocateRegion` of an empty hot page. It never opens the pack and never calls `tryLock`. Disk-backed open treats `regionId` as the folder name; the caller has already sanitized it. If `regions` already contains that id, the result is refused for exclusive. Existence is `pages.bin` present and a manifest read. If neither exists, take the lock, allocate an empty region, mint a UUID stored on the region until the first persist write, and return new. If one exists without the other, or the CRC or magic fails, or `pageBytes` does not equal yaml, or the format version is unknown, or the owner does not equal the region id, refuse. Do not allocate and do not clobber. If `restore` is false and files exist, do not attach and do not delete. Starting an ephemeral session without touching the files is the option this step names and does not prefer. Prefer refuse with a red line so the leftover bytes are obvious. The operator deletes the user directory. If both are valid, take the lock, and if the lock fails, refuse. The scan walks page id from 0 through `pageCount - 1`. It reads the slot header for `usedBytes`, bounded by the page header size. When the page id is not the hot page id, the page is the spilled form for that id, page bytes, and pack file. The walk starts at offset 64 and runs to `usedBytes`. It reads the frame header. An invalid length or a CRC failure stops that page. Each good frame appends seq, page id, and offset to the index and puts the id to seq. No TOON decode runs on the scan. The frame header readers already exist, and the id reader already exists. The hot page id's slot is loaded into an arena, writable, and that is the region's hot page. The next message id is the max of the manifest's next seq and one more than the highest seq seen. The next page id is `pageCount`. The return is restored, plus the index size, plus the UUID from the manifest. `persistClose` is idempotent. A missing region is success. Memory-only destroys the region (unmap), removes it from the map, does not delete the region, and does not unlock a lock it never took. Disk-backed with persist checkpoints the hot page (disk-backed here is `commit` or `append`; a live `hot-only` is memory-only), writes the manifest, destroys the region, closes the channel, unlocks, removes the map entry, and keeps the directory. Disk-backed with persist false is today's delete of the region. `checkpoint` during a turn is unchanged, and it is a no-op when hot-only. Disk-backed `commit` still checkpoints after a successful model pair. `rotateHot`, when memory-only, takes the spill-disabled path: keep the old page in memory, and fail if the resident-pages cap is hit. It does not publish the page. `clearRegion`, when memory-only, resets memory only and does not delete the region. When disk-backed it closes pages, resets the index and counters, opens a new page 0, wipes the pack, rewrites an empty manifest with the same UUID and owner, and keeps the lock. Shutdown and the pre-destroy hook persist-close each region. They do not delete the region. Extra fields on the region are the UUID, the created epoch in milliseconds as a long, and a closed flag for the idempotent close. The chat-memory wrapper exposes `open(String regionId)` returning the open result, `persistClose(String)`, and `isRamOnly`. Closing a conversation becomes persist-close, not a free that deletes.

**Truth.** Memory-only open allocates an empty hot page and takes no lock. Disk open refuses a live key, a half-present pack, a bad magic or CRC, a page-bytes mismatch, an unknown format version, or an owner mismatch, and does not clobber. A valid pack is scanned from offset 64 to slot `usedBytes` with no TOON decode. Persist close keeps the directory. Memory-only rotate does not publish.

**Reason.** The slot header, not the manifest hint, is how far a page actually goes. Stopping at the first bad CRC keeps garbage out of the index. Publishing from a memory-only rotate would thaw the frozen pack.

**Avoidances.** Do not `tryLock` or open the pack when memory-only. Do not start empty on a half-present directory. Do not prefer a silent new session over a red refuse when restore is false and files exist. Do not decode TOON on the scan. Do not delete the directory on persist close. Do not unlock a lock memory-only never took. Do not publish on a memory-only rotate. Do not drop the exclusive lock on a disk clear. Do not change the UUID or the owner when rewriting the empty manifest. Do not delete regions from shutdown.


**semantic-key:** PK-3484 PK-3485 PK-3486 PK-3487 PK-3488

## P4. Wiring, exclusive, and banner

The shell reads the username, opens or rejects, and prints `Restored N messages for {user}` or `New session`. A disk status line adds the manifest UUID.

The files are the shell factory, the chat session, the chat shell, a new reject shell, and the status command. `createShell` takes the channel. The username comes from `channel.getServerSession().getUsername()`. Sanitize it. Failure yields the reject shell, a red line, and exit. Memory-only builds a session with an ephemeral UUID as today and ensures the system prompt. Disk-backed calls `memory.open` on the sanitized name. Refused yields the reject shell with the engine message, including session already open, page-bytes mismatch, or an incomplete pack. New or restored builds `ChatSession.forUser` with the sanitized name, the open result, the system prompt, and the memory. The system prompt is ensured only when the system-role count is 0, so a restore skips it. The reject shell implements the command. On start it writes the reason in red, disables paste, and exits with code 1. It holds no region. After the welcome lines, `ChatShell.start` writes `Restored N messages for {user}` when the session was restored, and otherwise `New session`, then the existing prompt. `ChatSession.close` calls persist close and is safe to call twice. Status keeps `ssh-user`. `conversation:` is the region id, the username or the ephemeral UUID. When disk-backed, status adds `region-uuid:` and the manifest UUID so the display UUID survives reconnects. The factory still catches a runtime exception as today, prints it, and rethrows, so the shell host does not show a bare `shell request failed`.

**Truth.** Invalid username, exclusive clash, and a refused pack exit through the reject shell with a red line and code 1, and with no region. A restore banners `Restored N messages for {user}` and skips a second system prompt. A new session banners `New session`. Status shows `ssh-user`, `conversation` as the region id, and `region-uuid` when disk-backed.

**Reason.** The open result already knows restored versus new and the message count. The banner is that result, printed where the operator already looks. Exit code 1 and no region keep a refused connect from holding a writer.

**Avoidances.** Do not ensure the system prompt when a restored region already has a system row. Do not give the reject shell a region. Do not leave paste enabled on a reject. Do not drop `ssh-user`. Do not hide a runtime exception behind a bare shell-request failure. Do not make close unsafe to call twice.

**semantic-key:** PK-5967

## P5. Docs

After the code, mark the restore page implemented, keep its lock, and update the design page, the spill page, the yaml page, and the diagrams.

The restore page's mode becomes implemented after the code, and its lock section stays the lock. The design page records that hangup no longer deletes, and that the conversation id is the username when disk-backed. The spill page updates the row for the safe free and the close. The application yaml page shows the session keys as live. Regenerate the design diagrams with `generate-uml.ps1` after the new types exist.

**Truth.** The lock section stays. Hangup no longer deletes in the design page. The conversation id is the username when disk-backed. Session keys are documented as live. Diagrams regenerate from the new types.

**Reason.** The code and the pages will disagree if hangup is still described as a delete, or if the session keys stay marked proposed.

**Avoidances.** Do not rewrite the lock while marking the page implemented. Do not leave hangup-as-delete on the design page. Do not leave the session keys proposed. Do not regenerate diagrams before the new types exist.

**semantic-key:** PK-5968

## Roux session restore plan

This is the formal build plan for the restore lock. Coding waits until the plan is accepted. The packages are recorded as landed after that acceptance.

The design lock is the last section of the restore page. The key reference is the application yaml page. There is no unit-test suite for this work. Types in `org.roux` and `org.casserole` take no lambdas. The language level is Java 25. The engine stays free of the host framework except the package `org.roux.spring`.

**Truth.** The plan is the ordered build of the restore lock. It adds no unit tests, no lambdas in those two trees, and no host-framework types outside `org.roux.spring`.

**Reason.** The restore page already chose the policy. This page only places the types, the order, and the checks. The engine stays usable without the host.

**Avoidances.** Do not start the code before the plan is accepted. Do not add a unit-test source for this slice. Do not put lambdas in `org.roux` or `org.casserole`. Do not import the host framework from the engine outside `org.roux.spring`. Do not treat a date-stamped branch name as part of the product rule.

**semantic-key:** PK-5969

## Roux session restore

Happy-path persist and reload puts history back at the prompt after a clean process stop and a reconnect. Catastrophe repair stays out.

This page is the terminal restore specification. The design lock is the last section of this page. Identity and the pager are a different slice. The work here is restart the process, reconnect, and have the history back. The full key reference stays on the application yaml page. This slice adds `roux.session.persist`, `roux.session.restore`, and `roux.session.exclusive`, documented there. Scope is standard persist and reload. It does not cover a torn `pages.bin`, bit rot, a clock jump, or two writers. Acknowledgement and replication stay a later layer. When the vault-backed client scope is live, the host also holds a second chat memory keyed `{username}:aetheris`, held only in memory, dropped when that session ends, and filled from the client vault. That region is not this restore pack. It does not take the `{user}/` file lock. It is not a second disk writer. A multi-writer engine is withdrawn. The slice is recorded as implemented. The lock on this page still governs.

**Truth.** Reconnect after a clean stop reopens that user's pack and shows the history. Torn packs, bit rot, clock jumps, two writers, and replication are outside this slice.

**Reason.** The bytes are already in the pack. The missing piece is lifecycle: keep the directory, name it by the owner, and rebuild the memory caches. A second memory filled from the client vault must not become another writer on that pack.

**Avoidances.** Do not treat this page as the identity or pager slice. Do not open catastrophe repair here. Do not give `{username}:aetheris` the user file lock or a disk write. Do not turn the engine into a multi-writer.

**semantic-key:** PK-5970

## 0. The batch

Version 1 is the live pager: a session can name a message, find its bytes, rotate a page without losing the hot page on a failed write, build a prompt from a whole turn, and keep the tail in memory.

The tree already runs this layer. This page records the requirements so the next review can weigh them. It does not open a build. Close is requirement 15. Persist on leaves the directory. Persist off deletes it.

**Truth.** The batch is name, find, safe rotate, whole-turn prompt, and a tail that stays resident. Close is requirement 15.

**Reason.** Those five motions are what a first publish of the pager has to stand on. Close is called out because persist-on and persist-off disagree with an older delete-on-hangup sentence, and the weigh belongs on requirement 15.

**Avoidances.** Do not open a build from this batch page. Do not treat close as already settled inside the batch sentence. Do not drop the tail out of memory as the price of a rotate.

**semantic-key:** PK-5971

## 1. Requirements

A live session names a message with two keys, finds its bytes, spills as one transaction, keeps a working set, and rebuilds from the pack on the next open when restore is on.

1. Two keys. `seq` is the locator. `(idHi, idLo)` is the identity. Neither changes because a page moved.
2. The identity is on the frame and on the index row. The index stays in memory.
3. One region directory. One pack file, `pages.bin`. Slot `P` starts at `P * page-bytes`.
4. Spill is one transaction. Wait until the page is unpinned. Write the used bytes into the slot. Force. Then unmap. A thrown write leaves the hot page mapped and does not append the new message.
5. One cached channel per region. Reads of a spilled page use that channel.
6. The working set is `roux.resident-pages`. The default is 2. The hot page stays mapped. A spilled page may be copied back into memory while the cap allows. The coldest non-hot resident is the one that unmaps.
7. `spill.enabled=false` does not drop a memory page that has no file. A rotate that would pass the resident cap fails with a clear error.
8. Durability is one knob. `hot-only` writes a page when it rotates. `commit` also writes the hot slot after a successful user and assistant pair, and leaves the page mapped. `append` writes the hot slot at the end of each append. The default is `commit`.
9. A failed hot checkpoint does not roll back the append. The hot page stays in memory. The next checkpoint or the next spill is the retry.
10. The prompt window pins the leading system run. It takes the newest non-system frames until `max-messages` or `max-tokens`. `0` leaves that cap off. A user and assistant pair is one atom. The atom that crosses the token cap is included, then the walk stops.
11. An encoded frame larger than `max-frame-bytes` fails the add. `0` means `page-bytes - 64`. The type byte `CONT` stays reserved. Writers emit `MSG`.
12. `findById` is a memory map from the two longs to `seq`. It dies with the region.
13. `message show <seq>` reads one frame in this session. `message find <idHi> <idLo>` uses the map. Both take the locator, not a filtered row number.
14. A bad CRC on a shown frame is reported. It does not crash the shell.
15. Close keeps the tree. `session.persist` true writes the hot slot and the manifest and leaves the directory. `session.persist` false deletes the directory. RAM-only unmaps and does not touch a frozen pack. `session.restore` true rebuilds the index and `byId` from the frames on the next open of that region. A bad frame CRC, a length miss, or a sequence gap stops that page. A header whose version is not 2 refuses the region. A walk longer than the manifest count is taken. A walk shorter than that count refuses. A missing manifest with a valid slot 0 is walked. The factory settings have persist and restore true. The older sentence that hangup deletes the directory yields to this sentence.

**Truth.** Requirements 1 through 15 are the publish layer: two keys, one `pages.bin`, spill-after-force, resident default 2, durability default `commit`, a whole-turn window, oversize rejection, a memory identity map, inspect by locator, a reported CRC failure, and close that keeps or deletes the directory by `session.persist`.

**Reason.** Each line closes a hole in the earlier page-full spill: identity had to survive a move, a failed write had to leave the hot page mapped, a crash had to be recoverable from frames, and hangup had to stop deleting a pack the session is supposed to reopen.

**Avoidances.** Do not change `seq` or the identity pair because a page moved. Do not unmap before the force returns. Do not drop a RAM page that has no file when spill is off. Do not roll back an append because a checkpoint failed. Do not split a user and assistant pair at the window edge. Do not emit `CONT`. Do not crash the shell on a bad shown CRC. Do not let the old hangup-deletes sentence override requirement 15.

**semantic-key:** PK-5972

## 2. As seated

The tree already seats the pack, the rotate, the checkpoint, the identity, the window, the frame cap, inspect, status, and close, and a later build of this layer keeps those seats true.

Pack. `RegionPack` owns `pages.bin`. `slotOffset` is `pageId * page-bytes`. `publishPage` writes the page CRC, copies the used bytes, pads to `align-bytes` (default 4096) inside the page, writes the slot, and `force(true)`. It does not unmap.

Rotate. `RouxEngine.rotateHot` waits for the pin, calls `publishPage`, then unmaps. An `IOException` returns before the unmap. Spill off, and the resident count already at the cap, returns `resident-pages cap reached and spill is disabled`.

Checkpoint. `checkpointHot` calls `publishPage` on the hot page and does not unmap. Durability `hot-only` returns before that call. `append` calls it at the end of `appendMessage`. The shell calls `session.checkpoint()` on the commit path. A thrown write inside `checkpointHot` is swallowed. Requirement 9 is that behavior.

Identity. `appendMessage` mints a `RouxId` and stores it on the frame and the index row. `region.byId` maps that id to the message id. `findSeq` reads the map.

Window. `windowIds` pins the system run and walks turn atoms under `max-messages` and `max-tokens`.

Frame cap. `total > maxFrameBytes()` fails the add with `frame larger than max-frame-bytes`. `FrameType.CONT` is byte 5. The writer passes `FrameType.MSG`.

Inspect. `inspect` returns sequence, Roux id, role, time, tokens, type, page, offset, length, residency, crc, and text. `message show` and `message find` are the shell commands.

Status. `memoryStatusLine` reports pages, hot, spilled, resident, durability, and sequence.

Close. `persistClose` with `session.persist` calls `checkpointHot`, writes the manifest, and keeps the directory. `writeManifest` swallows a failed manifest write. With persist off, close deletes the directory, including `pages.bin`. `openRegion` with `session.restore` scans each slot. `scanFrames` rebuilds the index row and `byId`. The hot page is mapped. A spilled page is scanned and left unmapped. The first bad CRC, length, or sequence gap stops that page.

**Truth.** The seated path publishes a slot and forces without unmapping, unmaps only after rotate returns, checkpoints the hot slot in place, and scans frames back into the index and `byId`.

**Reason.** These sentences describe the code the requirements have to stay true against. A checkpoint that unmapped would drop the writable tail. A rotate that unmapped before `publishPage` returned would drop the only copy on a failed write.

**Avoidances.** Do not unmap inside `publishPage`. Do not unmap when `checkpointHot` throws. Do not let spill-off pass the resident cap. Do not write `CONT`. Do not delete the directory when persist is on. Do not keep scanning a page after the first bad CRC, length, or sequence gap.

**semantic-key:** PK-5973

## 3. Abstractions

Version 1 does not add a second pager beside the pack, the page, the index, the identity map, the working set, the window, and the durability checkpoint.

The pack is `RegionPack`: one file, one channel, slot publish and slot read. The page is `Page`: a hot segment, or a spilled slot, or a resident copy. The index is `MonotonicIndex`: `seq` to pointer, with the Roux id on the row. The identity map is `region.byId`: `(idHi, idLo)` to `seq`. The working set is `maybePageIn` and `pageOutOldest`: the resident cap. The window is `windowIds`: the prompt span. Durability is `checkpointHot`: when the hot slot is written.

**Truth.** Those seven seats are the pager. Version 1 adds no second one beside them.

**Reason.** A second pager would split the slot address and the residency rule. The seats already cover publish, lookup, identity, the cap, the prompt, and the hot write.

**Avoidances.** Do not add another pager type beside these seats. Do not store the identity map on disk as a second index. Do not let the window write the pack.

**semantic-key:** PK-5974

## 4. Config the layer already reads

The publish layer reads page size, frame cap, resident cap, durability, spill, and session flags, plus the chat window caps, and this filing does not edit the application settings file.

```yaml
roux:
  page-bytes: 1048576
  max-frame-bytes: 0
  resident-pages: 2
  durability: commit
  spill:
    enabled: true
    align-bytes: 4096
    dir: ${user.home}/.casserole/roux
  session:
    persist: true
    restore: true
    exclusive: true

casserole:
  chat:
    max-messages: 40
    max-tokens: 0
```

`page-bytes` defaults to 1048576. `max-frame-bytes` 0 means the page size minus 64. `resident-pages` defaults to 2. `durability` defaults to `commit`. Spill is enabled, aligned to 4096, under `${user.home}/.casserole/roux`. Session persist, restore, and exclusive default to true. `max-messages` defaults to 40. `max-tokens` 0 leaves the token cap off.

**Truth.** The layer's defaults are page 1048576, frame cap 0, resident 2, durability `commit`, spill on, align 4096, directory `${user.home}/.casserole/roux`, session flags true, `max-messages` 40, and `max-tokens` 0.

**Reason.** These are the values the layer already reads. Editing the application file is a different change and is not part of this filing.

**Avoidances.** Do not edit the application settings file from this filing. Do not treat `max-frame-bytes` 0 as a zero-byte frame. Do not treat `max-tokens` 0 as a token budget of zero messages.

**semantic-key:** PK-5975

## 5. Later layers

Layers after the publish layer are named on the paging specification and are not requirements of this page.

The surrounding product stages stay those plans. The layer numbers are the rows on the paging specification. Clip range is its own page and is not one of those rows. Reattach is the scan, and it runs when that region opens. Resume session is what the design calls later; the tree already keeps and restores, and requirement 15 is locked. Acknowledgement uses the same publish path. Continuation frames stay held.

**Truth.** This page requires the publish layer only. Clip range, reattach, resume, acknowledgement, and continuation are named elsewhere and are not requirements here.

**Reason.** Folding later rows into the publish requirements would make a review of version 1 also a build of every later row.

**Avoidances.** Do not implement clip range, continuation frames, or acknowledgement as part of meeting this page. Do not treat resume as unbuilt where requirement 15 already locks close. Do not renumber the rows.

**semantic-key:** PK-5976

## 6. Depends

The locked design, the two-key agreement, the paging plan, the clip-range page, reattach, and the page-full account stay beside this layer, and this page is the requirement where they disagree about the pack.

The design stays the locked design beside this layer. The identity page stays the two-key agreement. The paging specification stays the layer plan. Where that plan still shows a `{pageId}.pg` rename or a `.hot` file, the layer map on that page wins, and this page is the requirement. Clip range stays a later layer and is not a dependency of the publish layer. Reattach is the reattach layer. The spill account stays the page-full account.

**Truth.** Version 1 depends on the design, the two keys, the layer map, reattach, and page-full spill. It does not depend on clip range. The pack requirement wins over a per-page rename and over a `.hot` file.

**Reason.** Those pages already hold the layout, the identity, and the spill trigger. Pointing the requirement at them keeps one publish rule when an older sketch still names `{pageId}.pg` or `{pageId}.hot`.

**Avoidances.** Do not treat clip range as a dependency of the publish layer. Do not let a `{pageId}.pg` rename or a `.hot` file override `pages.bin`. Do not replace the page-full account with a window spill.

**semantic-key:** PK-5977

## 7. This filing

This filing changes no product source, opens no live session, and adds no public type.

No program source is cut. The window stays closed. No live request is posted. A pooled turn is not sent. A live terminal session is not opened. No client session is started. No new public type is added. No new layer number is taken.

**Truth.** The filing is the page itself. The tree and the public types stay as they are.

**Reason.** Recording requirements is not a license to exercise a live model call or to grow the public surface.

**Avoidances.** Do not cut source from this filing. Do not open a live session to prove the page. Do not add a public type.

**semantic-key:** PK-5978

## 8. The bake

The publish layer is accepted as the version 1 slice: the pack publishes a slot and forces, rotate unmaps after that call returns, the hot checkpoint leaves the page mapped, the window keeps the turn atom, and the identity lookup is the memory map.

Requirements 1 through 14 were weighed against the tree and hold. The oversize frame is rejected. The seats hold. Requirement 15 stays the open weigh on that count, and close stays as the tree has it. The bake adds no new program source. The window stayed closed. No live request was posted. A pooled turn was not sent. A live terminal session was not opened. No client session was started. No new public type was added. No new layer number was taken. Reattach through continuation stay later. Clip range stays later. Continuation stays held. The oversize rejection stays the large-frame rule.

**Truth.** The accepted slice is publish-and-force, unmap after rotate returns, checkpoint without unmap, a whole turn in the window, and `findById` as a memory map. Close remains the tree's close.

**Reason.** Those motions are what the tree already does for requirements 1 through 14. Accepting them as the slice keeps the next layers from being pulled forward. Requirement 15 is left as its own weigh because close is the sentence the older design still disputes.

**Avoidances.** Do not pull reattach, acknowledgement, continuation, or clip range into this slice. Do not add a public type in the bake. Do not treat the oversize rejection as optional. Do not unmap on the hot checkpoint.

**semantic-key:** PK-5979

## Roux version 1

Version 1 is the publish layer: the requirements a live pager must meet, recorded so the next review can weigh them, without opening a build.

The paging specification is the layer plan. This page is the version 1 layer that plan names. The surrounding product stages stay those plans. This filing records requirements and the layer map. It does not cut product source, does not open a plan directory, and does not name a later layer into existence.

**Truth.** This page is the requirement set for the publish layer. It does not itself cut code.

**Reason.** The tree already runs the layer. Writing the requirements down lets a review weigh them without pretending a new build has started.

**Avoidances.** Do not treat this page as a license to cut a later layer. Do not open a separate plan from this filing. Do not move the surrounding product stages onto this page.

**semantic-key:** PK-5980

## 10. Failure modes

A failed rotate leaves the hot pointer on the old page, and the add that triggered it does not write its frame.

A frame larger than `pageBytes - 64` returns `CORRUPTED_SEGMENT`, does not rotate, and leaves the hot page unchanged. `roux.crypto.enabled=true` returns `UNSUPPORTED` and does not spill. The crypto check is first, so flags are not written before that return. A pin timeout of 5 seconds returns `BLOCKED_BY_PIN`. The header may already be marked spilled in memory, the file is not written, `unmapRam` is not called, and the hot pointer is still the old page. An `IOException` from `SpillWriter.write` returns `CORRUPTED_SEGMENT`. The same sharp edge applies: flags say spilled in memory, the page is still mapped, no new hot page exists, and the add is aborted. Spill disabled writes no file, keeps the old page in memory, and creates a new hot page, so two or more memory pages exist. The in-memory header can say spilled while the page is still the hot writable page. That is a known sharp edge.

**Truth.** Oversize and crypto fail with the hot page unchanged and no rotate. Pin timeout and write failure can leave a spilled flag on a page that is still hot and still mapped. Spill off keeps every old page in memory.

**Reason.** The flag is written before the pin wait and the file write, so a later failure has already labeled the header. The add must not also store the new message on that inconsistent page.

**Avoidances.** Do not write the new message after a failed rotate. Do not unmap on pin timeout or on write failure. Do not treat the in-memory spilled flag as proof the file exists. Do not silently drop RAM pages when spill is disabled.

**semantic-key:** PK-5981

## 11. Lifecycle vs spill files

A successful disk-backed rotate lands a slot in `{spill.dir}/{user}/pages.bin`. Clear wipes the pack in place. Persist close keeps the directory. A hot-only session does not write the pack.

`clearRegion`, `chat start`, and `clear` on a disk-backed region wipe the pack in place, rewrite an empty manifest, and keep the folder and the lock. A RAM-only region resets memory only. `persistClose` and the session close, when disk-backed, checkpoint, write the manifest, and unmap, and they keep the user directory. RAM-only unmaps only. Process shutdown persist-closes each live region. Disconnect keeps the user pack when durability is disk-backed, which is `commit` or `append`. `hot-only` does not write or attach the pack.

**Truth.** Disk-backed close keeps `{spill.dir}/{user}/`. Clear wipes in place and keeps the folder and the lock. `hot-only` does not write or attach.

**Reason.** Restore needs the directory after hangup. Clear is the operator asking to forget, so the bytes go and the folder stays. A hot-only writer would put a second writer on a frozen pack.

**Avoidances.** Do not delete the user directory on a disk-backed close. Do not drop the lock on clear. Do not attach the pack from `hot-only`. Do not wipe a RAM-only region's frozen pack on clear.

**semantic-key:** PK-5982

## 12. What does not cause spill

The prompt window, the stored token count, and the read paths do not spill a page.

`casserole.chat.max-messages` is the prompt window only. The heuristic token count is stored on the frame and the index and is unused for paging. `get`, `message list`, and `buildPrompt` are read-only and may channel-read a spilled page. Compaction, least-recently-used eviction, mapped files, and encryption are stubs or are not implemented.

**Truth.** None of the window, the token count, a read, compaction, eviction, mapped files, or encryption causes a spill.

**Reason.** Spill is the page-full rotate. Mixing the prompt window into that trigger would publish a page that still had room.

**Avoidances.** Do not spill because `max-messages` was reached. Do not spill because a token count was stored. Do not spill from a read. Do not compact as a stand-in for spill.

**semantic-key:** PK-5983

## 13. End-to-end

One overflowing add encodes, counts tokens, rotates page 0 into `0.pg`, closes that arena, allocates page 1, writes the frame there, and indexes page id 1.

The session appends the user and the assistant only after the reply succeeds. The adapter counts with the heuristic and encodes. `appendMessage` asks whether the total exceeds remaining space on page 0. If it does, `rotateHot` writes `0.pg`, closes arena 0, and allocates page 1 as hot. The frame is written on page 1. The index row records page id 1.

**Truth.** A successful overflowing add leaves the new frame on the new hot page and the old page in `0.pg`.

**Reason.** That is the whole page-full path in one add: encode, rotate, write, index.

**Avoidances.** Do not write the overflowing frame onto page 0. Do not index it at page id 0. Do not append before the reply succeeds.

**semantic-key:** PK-5984

## 1. What is paged vs what stays in RAM

The index always stays in memory, spilled pages become files, and the hot page stays a writable segment.

One region is one conversation. The monotonic index is always an off-heap segment and is never spilled. It maps message id to page id, offset, length, token count, role, and the rest of the row. The page map holds spilled pages and one hot page. A spilled page has its arena closed, its segment null, and its path set to `{spill.dir}/{regionId}/{pageId}.pg`. The hot page is `Arena.ofShared` plus a writable segment with a sequence-lock generation.

One format covers memory and disk: a 64-byte page header plus append-only frames. Frames do not span pages. If the next frame is larger than the remaining hot space, the old page rotates out and the frame is written on the new hot page. If a single frame is larger than `pageBytes - 64`, the add fails. There are no continuation frames in this account. The index does not spill. After spill, lookups still use memory, and payload bytes come from a channel at the index offset. The default page size is `roux.page-bytes` = 1048576, which is 1 MiB.

**Truth.** Index stays in memory. A spilled page is `{spill.dir}/{regionId}/{pageId}.pg` with a null segment. A frame larger than `pageBytes - 64` fails. The default page is 1048576 bytes.

**Reason.** The index is the transit map. Spilling it would make every lookup a file scan. A frame that cannot fit a page has no continuation type in this account.

**Avoidances.** Do not spill the index. Do not let a frame span pages. Do not continue an oversize frame. Do not change the default page size from 1048576 on this page.

**semantic-key:** PK-5985

## 2. Configuration

Spill is on by default, the directory is `${user.home}/.casserole/roux`, and encryption refuses a rotate when the flag is on.

```yaml
roux:
  page-bytes: 1048576
  spill:
    enabled: true
    dir: ${user.home}/.casserole/roux
  crypto:
    enabled: false
```

When `spill.enabled` is true and the hot page is full, the page CRC is written, the file `{dir}/{regionId}/{pageId}.pg` is written, the arena is closed, and a new hot page opens. When it is false, the old page stays in memory and a new hot page is still allocated, with no file. If crypto is enabled, rotate refuses as unsupported. Disk path for page 0 of region `abc` is `{user.home}/.casserole/roux/abc/0.pg`.

**Truth.** Defaults are page 1048576, spill enabled, directory `${user.home}/.casserole/roux`, crypto disabled. Spill off keeps the old page in memory and still opens a new hot page. Crypto on refuses the rotate.

**Reason.** A single enabled flag lets the first wiring stay in memory without rewriting callers. Encryption is not implemented, so a true flag must fail rather than pretend to encrypt.

**Avoidances.** Do not write a `.pg` file when spill is disabled. Do not drop the old RAM page when spill is disabled. Do not rotate when crypto is enabled. Do not put spilled pages in the spill root without the region directory.

**semantic-key:** PK-5986

## 3. Class dependencies on the add-that-spills path

The add path runs from the session through the adapter into the engine, and the spill itself is `rotateHot`.

`ChatSession.appendUser` and `appendAssistant` call `RouxChatMemory.add` in `org.roux.spring`. That uses `RouxMessageMapper` for text and role, and `HeuristicTokenCounter` for the count. `RouxEngine.appendMessage` allocates the region if it is missing, encodes with `ToonCodec`, computes `FrameHeader.totalLength`, takes the region `ReentrantLock`, checks `Page.remaining`, and calls `RouxEngine.rotateHot` when the frame does not fit. `rotateHot` asserts crypto is disabled, sets the spilled flag and the next page id, writes the header CRC, waits on `PinTable.waitUnpinned` for 5 seconds, and calls `SpillWriter.write`. The writer uses `roux.spill.dir`, writes the CRC again, copies the segment to a byte array, and writes a channel with `force(true)`. The page then records the file, `unmapRam` closes the arena, a new `Page` is opened for the next id and the page size, and the counters record the spill and the allocation. The frame is written with `beginWrite`, `FrameHeader.write`, the header setters, and `endWrite`. `MonotonicIndex.append` stays in memory. The pin table pins and unpins the new hot page. `framesAppended` increments.

`MemoryInspector` does not participate in the spill itself, except a later read. Snapshot calls are unsupported. `message list` is a read path.

**Truth.** Spill happens inside `rotateHot` after the crypto check, the pin wait of 5 seconds, and `SpillWriter.write` with `force(true)`, and only then is the arena closed.

**Reason.** The pin wait is what keeps a reader off a segment that is about to close. The force is what makes the file the copy that survives the unmap.

**Avoidances.** Do not unmap before `force(true)` returns. Do not skip the 5 second pin wait. Do not append the index on the spilled page's old segment after the rotate. Do not involve snapshot calls in the spill.


**semantic-key:** PK-3489

## 4. Page and frame layout

What hits disk is a 64-byte page header, then 8-byte-aligned frames, and the index row that finds them stays a 40-byte memory record.

The hot segment and the `.pg` file have capacity `pageBytes`, typically 1 MiB. The page header is 64 bytes: magic `ROUX`, version, page id, generation, flags, used bytes, crc, frame count, first id, last id, next page id, and reserved. Each frame is length, type, flags, role, pad, message id, timestamp, token count, payload length, crc32c, then the UTF-8 payload, padded to 8. Frames append. Unused space is capacity minus used bytes.

One message payload is:

```text
m[1]{r,ts,tok,x}:
user,1710000000000,12,Hello world
```

The index row is 40 bytes, memory only: message id, page id, offset, length, token count, timestamp, role, type, flags. `offset` is the byte offset of the frame in the page, including the header. After spill, the frame read uses that offset into the `.pg` file.

**Truth.** Disk is a 64-byte header plus 8-aligned frames in a `pageBytes` slot, typically 1 MiB. The 40-byte index stays in memory. The payload form is `m[1]{r,ts,tok,x}:`.

**Reason.** One layout on the segment and in the file means a spill is a copy of used bytes, not a translation. The index offset is what makes the file readable without a scan.

**Avoidances.** Do not spill the 40-byte index. Do not change the payload header to a conversation-sized table on this path. Do not treat `offset` as an offset past the frame header only. Do not leave the frame unaligned to 8.

**semantic-key:** PK-5987

## 5. When remaining space is enough

When the hot page's remaining bytes are at least the frame total, the frame is written on that page and nothing spills.

The hot page is pinned. `beginWrite` makes the generation odd. The frame header is written with the payload already encoded. Used bytes, frame count, and last id advance. `endWrite` makes the generation even. The index appends in memory. The page is unpinned. The picture is the same page: the free tail shrinks by the frame total.

**Truth.** A frame that fits does not rotate. The generation goes odd for the write and even when the write ends.

**Reason.** The odd generation is the sequence lock that tells a reader the frame is torn. An even generation is the published frame.

**Avoidances.** Do not rotate when remaining space is enough. Do not append the index before `endWrite`. Do not leave the generation odd after a successful write.

**semantic-key:** PK-5988

## 6.1 Sequence

On the happy path with `spill.enabled=true`, an assistant append encodes, aligns, refuses an oversize frame, and otherwise rotates before writing the new hot page.

`appendAssistant` goes through `RouxChatMemory.add`. The token count is `HeuristicTokenCounter.count`. `appendMessage` encodes with `ToonCodec`. The total is `align8(36 + payload.length)`. If the total is greater than `pageBytes - 64`, the add fails with a frame larger than the page and does not rotate. Otherwise the region is locked. If remaining is less than the total, `rotateHot` runs. Crypto must be disabled. The old page is marked spilled, its next page id is the upcoming id, and the header CRC is written. The pin count of the old page must reach 0, with a timeout of 5000 ms. `SpillWriter.write` writes `{dir}/{regionId}/{oldPageId}.pg`. `unmapRam` closes the arena. The hot page becomes `new Page(nextPageId, pageBytes)`. The new frame is written on that new hot page. The index appends the new message id, the new page id, and the offset. Then the lock drops.

**Truth.** Frame total is `align8(36 + payload.length)`. Over `pageBytes - 64` fails with no rotate. Otherwise rotate waits up to 5000 ms, writes the `.pg` file, unmaps, and the new frame lands on the new hot page.

**Reason.** The 36-byte header plus payload, aligned to 8, is the frame this account writes. The 64-byte page header is why the limit is `pageBytes - 64`. Unmapping before the file write would drop the only copy.

**Avoidances.** Do not rotate a frame larger than `pageBytes - 64`. Do not write the new frame on the old page. Do not skip the pin wait. Do not leave crypto enabled on this path.

**semantic-key:** PK-5989

## 6.2 Before and after

Before the rotate, the hot page is page 0 in a memory arena, nearly full, and the index points at page 0 offsets. After the rotate and before the new frame, the hot page is page 1 with used bytes equal to 64, page 0 is spilled at its `.pg` file with a null segment, and the index is unchanged.

After the new frame is written, page 1 holds the header and the new frame, and the index gains a row whose page id is 1 and whose offset is 64.

**Truth.** Rotation moves the hot cursor to a header-only page of 64 used bytes and leaves existing index rows on the spilled page. The new frame's index offset is 64.

**Reason.** The new page has only its header until the frame is written, so the first frame starts at byte 64. Old rows must keep their old offsets or the spilled file is unreadable.

**Avoidances.** Do not rewrite old index rows to the new page. Do not start the new page's used bytes at 0. Do not place the first new frame at an offset other than 64 when the page is otherwise empty.

**semantic-key:** PK-5990

## 6.3 Disk vs RAM after spill

After spill, memory holds the whole index and the hot page's segment, and disk holds the old page's used bytes.

The index stays in memory for every id. The hot page, page 1 in the picture, is a memory segment. Disk is `~/.casserole/roux/<region>/0.pg` for the spilled page, holding `usedBytes`. New frames go to the hot segment. Old frames are read through the channel.

**Truth.** The index and the hot page stay in memory. The spilled page's used bytes are the `.pg` file under `~/.casserole/roux/<region>/`.

**Reason.** The tail is what the next append writes. The cold page is already sealed, so it does not need a segment.

**Avoidances.** Do not keep the spilled page's segment. Do not drop the index. Do not write new frames into the `.pg` file in place.

**semantic-key:** PK-5991

## 6. When remaining space is not enough

A frame that does not fit the remaining hot bytes rotates the hot page before it is written.

The child sequences are the happy path, the region picture, and the disk picture. The parent rule is the trigger: remaining less than total, and the frame is not larger than the page.

**Truth.** Shortage of remaining bytes, not shortage of page capacity, is what rotates.

**Reason.** A frame that fits in an empty page but not in the tail belongs on the next page. A frame that fits in neither fails before the rotate.

**Avoidances.** Do not rotate a frame that fits. Do not write the new frame on the page that was just spilled.

**semantic-key:** PK-5992

## 8. Read after spill

The window and `message list` walk index ids and then read the frame bytes. They do not scan the `.pg` file linearly.

The page is taken from the map and pinned. If the segment is non-null, the bytes are copied from the segment. If the file is non-null, `SpillWriter.read` reads the channel into a heap buffer. If both are absent, stale views increment and the read returns null. The pin is released in a finally. Then the CRC is checked, `ToonCodec.decode` runs, and `RouxMessageMapper` builds the message. `getWindow` and `get` call `windowIds` on the memory index only, then `readMessage` for each id. The index is the memory transit map. The page payload is memory or file.

**Truth.** Reads walk the memory index, then copy from the segment or from the channel at the index offset, under a pin.

**Reason.** A linear scan of the file would ignore the index and would not know which offset is still live. The pin keeps the arena from closing during the copy.

**Avoidances.** Do not scan `.pg` to build a window. Do not read without the pin. Do not decode when both segment and file are absent. Do not treat a stale view as a crash.

**semantic-key:** PK-5993

## 9. Concurrency during spill

The region lock is exclusive for append, rotate, clear, and free, and chat waits. There is no 50 millisecond throw.

The page generation is the sequence lock, odd from `beginWrite` through `endWrite` on the hot page. Spill does not run a sequence-lock write loop. It sets flags and the CRC, then unmaps. The pin table is what `append` uses on the page it writes. `rotateHot` waits up to 5 seconds for the old page's pin count to hit 0 before `unmapRam`, so a concurrent `readFrameBytes` is not under `Arena.close`. `mapLock` protects only the process-wide region map, for require and allocate. A second writer cannot overlap because append already holds the region lock. The overlap that remains is a reader against the spill of the old page. `get` and the window pin around the copy.

**Truth.** Writers serialize on the region lock and wait. Readers pin. Rotate waits up to 5 seconds for the old page's pins before closing the arena. There is no 50 millisecond lock failure.

**Reason.** Failing the chat after 50 milliseconds drops a turn that was only waiting for a reader to finish a copy. The pin is the use-after-unmap fence.

**Avoidances.** Do not throw after 50 milliseconds. Do not close the arena while the old page's pin count is above zero. Do not take the region lock for a cursor read. Do not use `mapLock` as the append lock.

**semantic-key:** PK-5994

## Roux RAM-to-disk spill semantics

Spill, as built, runs when an added message's encoded frame will not fit in the remaining bytes of the region's hot page.

This page describes the current rotation and spill writer, not a later mapped-file or copy-on-write design. Token budget, `max-messages`, idle time, and operating-system memory pressure are not triggers. Spill is page-full, and nothing else.

**Truth.** The only spill trigger is a frame that does not fit the hot page.

**Reason.** A window cap or a memory-pressure signal would move bytes the operator did not fill a page with, and this build does not do that.

**Avoidances.** Do not spill on token budget, `max-messages`, idle time, or memory pressure. Do not read this page as a mapped-file or copy-on-write design.

**semantic-key:** PK-5995

## Incremental sync stays later

Full push stays, and the first plan does not include incremental sync of the notebook region.

Incremental sync is preferred. It is not a start gate. Full push remains the hydration until a later plan builds the increment.

The region is a second region, in RAM, with its own lock, no file lock, and no append into the terminal pack. The id is `{user}.aetheris`. The method is `openRam`. The terminal region stays `{user}`. Cells never enter the terminal pack.

Do not open a multi-writer protocol.

`complete` uses the local `Prompt` and does not read the region back. One facade mutex covers `clear` and every `add` for that execute, and it ends before `complete`. A second execute waits. It does not fail at 50 ms. Local-engine parallelism stays the operator's setting. The local base is `http://localhost:11434`.

The mutex is the facade push. It is not the paged-memory lock and not `tryAcquireLock`. The three slots are a semaphore in `AmwpFacade`. The terminal chat does not take it. The pool stays 4. `reason: cell-busy` does not clear the live run.

After reconnect, the vault wins. The server region is discarded and filled again.

**Truth.** Every execute clears `{user}.aetheris`, writes the working set, and calls `complete` on the local prompt. The facade mutex covers only `clear` and `add`. Incremental sync is later. The pool stays 4 and the notebook semaphore is 3.

**Reason.** The product already pushes the whole working set. Building the increment first would block every other row on a sync the slice does not need, and a clear another execute can enter mixes two pushes in one region.

**Avoidances.** Do not make incremental sync a start gate. Do not return a later increment to a clear another execute can enter. Do not add a second region to hide the lock. Do not append notebook cells to the terminal pack. Do not open a multi-writer protocol. Do not read the region back inside `complete`. Do not fail a waiter at 50 ms. Do not use `tryAcquireLock` as the facade mutex. Do not change local parallelism. Do not let the terminal take the semaphore. Do not shrink the pool to 3.

**semantic-key:** PK-5996

## The facade mutex covers clear and add, then lets go

`AmwpFacade` holds one mutex per `{user}.aetheris` region across `clear` and every `add`, and `complete` runs after the release.

The finding is that each region call takes `region.lock` and releases it inside that call. `tryAcquireLock` is a different lock and can time out. Holding `region.lock` and then calling `clear` drops a hold in `clear`'s `finally`.

`AmwpFacade` keeps one mutex per `{user}.aetheris` region. The terminal region `{user}` is a different region and does not share this mutex.

The push method takes the mutex, runs `clear` and every `add`, and releases it. `complete` runs after the release and uses the local `Prompt`. It does not read the region back.

Lock order is facade mutex, then the region call. Never the region lock first. The class comment says that order.

A second execute waits on the mutex. It does not fail at 50 ms. `tryAcquireLock` is not used.

The seam is that the paged memory stays unchanged. The race was the gap between two public calls. The owner of that gap is the facade.

The region id uses a dot. There is no file lock. Cells are not appended to the terminal pack. Full push stays.

**Truth.** One facade mutex per `{user}.aetheris` covers `clear` and every `add`, then releases before `complete`. Lock order is facade mutex first. A second execute waits and does not fail at 50 ms. The region implementation does not change.

**Reason.** Separate `clear` and `add` calls each release `region.lock`, so another execute can clear between them, and holding that lock into `clear` loses a hold in `clear`'s `finally`.

**Avoidances.** Do not use `tryAcquireLock`. Do not take the region lock before the facade mutex. Do not hold the region lock and then call `clear`. Do not read the region back inside `complete`. Do not fail a waiter at 50 ms. Do not change the paged-memory calls to hide the gap. Do not share the mutex with `{user}`. Do not add a file lock.


**semantic-key:** PK-5997
