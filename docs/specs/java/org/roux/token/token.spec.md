# org.roux.token

## 5. Package 2. Execute seams and MOOD-LAYER

2. Host system line first, then the mood system line when approved. Two messages. They are not fused. The inspector header shows both, plus the walker estimate from `org.roux.token.HeuristicTokenCounter`, plus the `SessionFacts` integer or the phrase for an empty Init body. The panel's `length/4` sum is retired for this view.
**semantic-key:** PK-535

## The VT100 heatbar wears the Roux session count

The SSH heat integer is the sum of HeuristicTokenCounter.count over the stored Roux messages for that username. Empty text counts as 1. A chat with no messages uses 1, with 0 characters and 0 blocks. A chat with messages uses the sum. The system prompt is stored and counted. Mood stays in buildPrompt and is not added. The message list stays. The canvas ring stays. Token Calc opens on that same integer.
**semantic-key:** PK-536

## One transcript estimates every construct

One transcript, oldest first, through the armed construct. One walk for a merge arm, a member arm, and an ordinary arm. A group is one turn. The counted string is full text. JOIN banners are stripped. The paint stays capped. `assemble` stays the Run. The canvas seats the heuristic. A held SSH session seats the stored tally over the same transcript shape. The later build may add `Transcript`, `TokenEstimator`, `HeuristicEstimator`, and `StoredCountEstimator` in `org.roux.token`. This filing adds none of them.
**semantic-key:** PK-980

One transcript through the armed construct. One estimator on the rendered string. No side move on this plate. The public types are Transcript, TokenEstimator, HeuristicEstimator, StoredCountEstimator, StateModifier, IncludedModifier, ExcludedModifier, InputOnlyModifier, OutputOnlyModifier, StructuralModifier, OrdinaryStructure, GroupStructure, and MessageStructure. StateChain is package-private. MessageStructure has no caller. The private method is transcriptThroughArm.
**semantic-key:** PK-981

### Edges, bottlenecks, and failure modes

 - `HeuristicTokenCounter` uses `(length + 3) / 4`, and an empty string counts as 1. The inspector uses `length / 4`, and an empty string adds 0.
**semantic-key:** PK-537

### Walker - Make it so. Approved

- The estimate calls `HeuristicTokenCounter` on the text the walker would send, including both system lines. The panel's `length / 4` sum is retired for this view.
**semantic-key:** PK-538

## The report

Agreed 2026-10-20 20:02. One walker does the send and the inspector. `k` is the 1-based index among cells the walker kept, oldest first. Inspector paint numbers are not `k`. Resolve on the client before the frame is built. The host does not fetch H2. The inspector calls `HeuristicTokenCounter` on that same text, including the host line and the mood line.
**semantic-key:** PK-539

## 3. Edits

`tokenActive` is true for a readable Included cell. `refreshInspector` still paints the working set. The string passed to `HeuristicTokenCounter` appends a source block only when that cell is Included. A merge source appends Included member prompts, and the merge reply only when the merge is Included. A focused merge appends Included member prompts in full, and the merge reply only when the merge is Included. The focused cell's prompt is counted only when that cell is Included.
**semantic-key:** PK-540

## 2. What stays

`HeuristicTokenCounter` stays. The Rust `estimate_tokens` copy stays. The canvas estimate stays. The SSH strip stays. The yaml stays. Roux stored counts stay.

## 2. What stays

The heuristic stays `(length + 3) / 4` for a non-empty string, and 1 for null or length 0. `length` stays Java's UTF-16 length. `HeuristicTokenCounter` stays that math. `TokenCounter` stays `int count(String text)`.
**semantic-key:** PK-541
**semantic-key:** PK-541

## 3A. Modifiers

An SSH message is one turn. A message with no canvas mark uses `IncludedModifier`, so the message text counts. A later mark on a message uses the same state chain.
**semantic-key:** PK-542

`GHOST` is Excluded. Unreadable contributes `ExcludedModifier`, then the mark. A blank ephemeral card contributes `ExcludedModifier`. A half survives when every modifier in the chain keeps it.
**semantic-key:** PK-543

A later state implements `StateModifier`. A later structure implements `StructuralModifier`. The walk calls the modifier the construct carries. The armed card does not open another builder.
**semantic-key:** PK-544
**semantic-key:** PK-544

## 3B. Selection and arming

The estimate reads the group when that card belongs to one. An input member is `groupedMemberCell`: a group id is set and the column is above 0. The merge output is `mergeSink`: a group id is set and the column is 0. Either click resolves to the same armed construct. `GroupStructure` emits one turn. The transcript is every turn above the group, then that turn. The count is the same number for both clicks.
**semantic-key:** PK-545

## 4. The estimator seam

A transcript estimator implements `int estimate(Transcript transcript)`. The canvas seats the heuristic estimator. It renders the transcript and calls `HeuristicTokenCounter`. A later method implements the same estimate entry. A host `prompt_eval_count`, a real tokenizer, and a stored sum are later estimators.
**semantic-key:** PK-982

## 7. What a later build may add - says; Please make sure these are accounted for and implemented in the build

The package `org.roux.token` already holds `TokenCounter` and `HeuristicTokenCounter`. The later build adds the types beside them. `TokenCounter.count` stays the string seam.
**semantic-key:** PK-546
**semantic-key:** PK-546

`org.roux.token.Transcript`. Ordered turns. A public static `Turn` holds the user half and the assistant half. `rendered` returns the counted string in section 3. The held SSH integer stays the estimator result in section 6. It is not a field on the canvas turn.
**semantic-key:** PK-547

`org.roux.token.HeuristicEstimator`. Implements `TokenEstimator`. Renders, then `HeuristicTokenCounter`.
**semantic-key:** PK-548
**semantic-key:** PK-548

`org.roux.token.StoredCountEstimator`. Implements `TokenEstimator`. The SSH held-session rule in section 6. It returns `rouxSessionUsed` for that user. It does not walk the chat a second time.
**semantic-key:** PK-549

`org.roux.token.StateModifier`. `keepUser` and `keepAssistant`. An empty return means that half stays out of the counted string.
**semantic-key:** PK-550

`org.roux.token.InputOnlyModifier`. The user half stays. The assistant half stays out.
**semantic-key:** PK-551

`org.roux.token.OutputOnlyModifier`. The assistant half stays. The user half stays out.
**semantic-key:** PK-552

`StateChain` is package-private in `org.roux.token`. It applies the modifiers in order. A half stays when every modifier keeps it.
**semantic-key:** PK-553

`org.roux.token.StructuralModifier`. `OrdinaryStructure` emits one turn. `GroupStructure` emits one turn and gathers member prompts. `MessageStructure` is the type a later sentence may use when that sentence names a client message list. Section 6 is the SSH behavior this plan seats. A later structure implements the same type.
**semantic-key:** PK-554
**semantic-key:** PK-554
**semantic-key:** PK-554
**semantic-key:** PK-554

**Step 2. State.** `StateModifier` answers `keepUser` and `keepAssistant`. `IncludedModifier` keeps both. `ExcludedModifier` keeps neither. `InputOnlyModifier` keeps the user half. `OutputOnlyModifier` keeps the assistant half. `StateChain` is package-private. A half stays when every modifier in the chain keeps it. `GHOST` contributes `ExcludedModifier`. Unreadable contributes `ExcludedModifier`, then the mark. A blank ephemeral card contributes `ExcludedModifier`. A cloud arm contributes `ExcludedModifier` on every ephemeral cell other than the selected card, then that cell's mark. A local arm does not add that modifier.
**semantic-key:** PK-555
**semantic-key:** PK-555

## 9. For his smoke

8. The canvas seats `HeuristicEstimator`. A held SSH session seats `rouxSessionUsed`. An unheld SSH session seats the heuristic on `sessionMemoryText`, and empty text returns 1.
**semantic-key:** PK-556

9. A later estimator implements `TokenEstimator`. The heatbar stays on the integer.
**semantic-key:** PK-557

## 12. Seats

2026-11-02 19:43. the plan and named the build. The types in section 7 are the build. StateChain stays package-private. MessageStructure has no caller. No side move on this plate. Wire stanza empty.

## 3. The cause

The cell document listener calls noteLiveDraft on the EDT for insert, remove, and change. noteLiveDraft copies the pane text onto the cell, then calls refreshInspector. refreshInspector builds the inspector text and walks transcriptThroughArm. That walk renders the armed transcript and HeuristicEstimator counts the whole rendered string. The key does not return until that count is painted. liveTokenHold only stops a document event from reentering the same call. It is not a queue. The heatbar does not calculate. It reads the integer already stored. The 200 ms beat is that read. The measurement is what blocks the key.
**semantic-key:** PK-558

## 2. What stays

## Dependencies pitied

`armFocus` stores the clicked card in `focusedCellId`. `targetCell` returns that card. A member click and a merge click are different cards today. The estimate requirement is that both arm the group as one turn. `refreshInspector` builds the counted string today. A focused merge calls `appendActiveGroupTokens` and leaves the cards above the group out. Any other arm calls `sourcesBefore`, then `appendActiveMergeTokens` for a group inside that walk, and counts the armed prompt in full when the card is Included. `paintTokenEstimate` stores `HeuristicTokenCounter.count` on `tokenEstimate`. `tokenRingUsed` returns that integer. The heatbar reads it. `noteLiveDraft` calls `refreshInspector`. The later build points the refresh at the one transcript.
**semantic-key:** PK-983

## Dependencies pitied

`TokenCounter` is `int count(String text)`, at least 1. `HeuristicTokenCounter` implements it. That is the seated string math.
**semantic-key:** PK-559

## Seat

One transcript. Oldest first. Through the armed construct, inclusive. A click on any card of a merge group arms that group as one turn. One estimator seam. The heatbar asks the seam. The canvas seats `HeuristicTokenCounter` on the rendered transcript. The SSH tab seats `rouxSessionUsed` when the session holds one, and the heuristic of `sessionMemoryText` when it does not. A later estimator implements the same seam.
**semantic-key:** PK-560

## One counter — frozen - says; Yes! Perfect unified approach. I love it

When the managed size is set, the heatbar counts the wire transcript with `HeuristicTokenCounter` and fills against that size. Stored SSH frames spend the token count on the index row. The unsent line uses the same counter. When the size is unset, the bar keeps today's count and today's window.
**semantic-key:** PK-561

## 1. As built

**Tokens.** `HeuristicTokenCounter` stores `max(1, (length + 3) / 4)` at write. Empty text is 1. The VT100 strip sums those stored counts for the focused user. `casserole.chat.max-tokens` defaults to 0, which means the read imposes no token cap. `casserole.chat.max-messages` defaults to 40. Ollama sends `options.num_ctx` when `spring.ai.ollama.chat.options.num-ctx` is a positive integer. Grok does not send `num_ctx`. Pool does not send `num_ctx`.
**semantic-key:** PK-562

## 5. Triggers

**Estimate.** Sum the stored token count of each prompt message that has one. A message with no stored count, including the live user line and a mood line seated only on the prompt list, uses `HeuristicTokenCounter`. Remaining is `capacity - estimate`. When the estimate is at least the capacity, remaining is 0.
**semantic-key:** PK-563

## 11. Implementation proposal

3. An estimate on the prompt messages, stored counts first, `HeuristicTokenCounter` when a message has no stored count.
**semantic-key:** PK-564

## 11. Tokenizer

`TokenCounter.count(String text)` → `max(1, (text.length() + 3) / 4)`.
**semantic-key:** PK-565

## 4. The estimate

The last line is `est. tokens:` and the count. The counted text is each sent line plus a newline, in send order. An empty count is 1. Any other count is `(length + 3) / 4`. The length is UTF-16 code units, the same count `HeuristicTokenCounter` uses. The drawn space that stands in for a newline is not the counted character. The newline is.
**semantic-key:** PK-566

One transcript, oldest first, through the armed construct. One walk for a merge arm, a member arm, and an ordinary arm. A group is one turn. The counted string is full text. JOIN banners are stripped. The paint stays capped. `assemble` stays the Run. F-142 stays. F-152 stays. F-163 stays. F-171 stays. The canvas seats the heuristic. A held SSH session seats the stored tally over the same transcript shape. The later build may add `Transcript`, `TokenEstimator`, `HeuristicEstimator`, and `StoredCountEstimator` in `org.roux.token`. This filing adds none of them.
**semantic-key:** PK-1325
**semantic-key:** PK-1325

F-174 landed. One transcript through the armed construct. One estimator on the rendered string. The Java proof is 134d1a2d. Resume HEAD is 134d1a2d. No side move on this plate. The next publish marker is PM-43. Wire stanza empty. Rust stays. The public types are Transcript, TokenEstimator, HeuristicEstimator, StoredCountEstimator, StateModifier, IncludedModifier, ExcludedModifier, InputOnlyModifier, OutputOnlyModifier, StructuralModifier, OrdinaryStructure, GroupStructure, and MessageStructure. StateChain is package-private. MessageStructure has no caller. The private method is transcriptThroughArm.
**semantic-key:** PK-1326
**semantic-key:** PK-1326
**semantic-key:** PK-1326
**semantic-key:** PK-1326
**semantic-key:** PK-1326
**semantic-key:** PK-1326
**semantic-key:** PK-1326
**semantic-key:** PK-1326
**semantic-key:** PK-1326

`HeuristicTokenCounter` stays. The Rust `estimate_tokens` copy stays. The canvas estimate stays. The SSH strip stays. The yaml stays. Roux stored counts stay. F-147 stays locked. F-132 through F-137 stay the open Book'em. Rust stays. Java stays.
**semantic-key:** PK-1352

