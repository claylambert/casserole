# org.casserole.format

Component file. It carries how this package is built.

**Depends:** [java/org/casserole/format/fence/fence.spec.md](fence/fence.spec.md), [java/org/casserole/format/json/json.spec.md](json/json.spec.md), [java/org/casserole/format/list/list.spec.md](list/list.spec.md), [java/org/casserole/format/markdown/markdown.spec.md](markdown/markdown.spec.md), [java/org/casserole/format/table/table.spec.md](table/table.spec.md)

## A paragraph is one run

A plain reply is one paragraph run. A single line feed inside a paragraph becomes one space. A blank line stays a paragraph break.

The package-private type is ParagraphFlow. The method is flow. FormatRouter flows the identity path before toCrlf. WordWrap calls flow before the width flush. The declared test is ParagraphFlowTest. InputPage stays. paintFull stays. parseColumns stays. Shift-Enter stays a hard break. grid.rs stays.

The files are ParagraphFlow.java, FormatRouter.java, WordWrap.java, and ParagraphFlowTest.java. No new public type, no new public method, and no new library are added. The Java formatter carries this. The Rust grid file is not edited.

The formatter is the shared console. A plain reply's bytes change for both clients. The Rust grid soft-wraps the one run it is now given. A blank line stays a break. A known gap in paste undo stays.

The doors do not gain, lose, or reorder a line.

**Truth.** A plain reply flows as one paragraph. One line feed inside that paragraph becomes one space. A blank line remains a paragraph break. Shift-Enter remains a hard break. FormatRouter runs flow on the identity path before toCrlf. WordWrap runs flow before the width flush. Both clients see the new bytes. The Rust grid is not edited and soft-wraps the single run it receives.

**Reason.** A line feed inside a paragraph was wrapping as its own run, so a plain reply painted as a stack of lines instead of one paragraph. Collapsing a single line feed to one space, and keeping a blank line as the break, is the paragraph. Doing it before the carriage-return conversion and before the width flush means the wrap measures the run, not the raw breaks. The grid already soft-wraps whatever run it is given, so it does not need an edit.

**Avoidances.** A blank line is not turned into a space. Shift-Enter is not softened. InputPage, paintFull, and parseColumns stay. grid.rs is not edited. The doors do not change. The known paste-undo gap stays.


**semantic-key:** PK-0128 PK-0129 PK-0130 PK-0131

# Format router

This page builds the reply painter. The map is [program-spine.md](../../aetheris/config/config.spec.md).


**semantic-key:** PK-0558

# Reply format router

One assistant reply is painted by one pass of `FormatRouter`. The pass either accepts the first registered handler or, when none match, flows the paragraph. It then forces CRLF, forces a trailing CRLF on a non-empty result, and annotates links. An empty or null reply stops before all of that.

The types that do the work live in `org.casserole.format` and in the `json`, `fence`, `table`, `list`, and `markdown` packages under it. Detectors are strict. They do not score. Renderers paint VT100 text and return a `FormatResult`: the text, and `applyBodyColor`. A null text stored in a result becomes the empty string. The router does not run the detectors a second time on the painted text.

**Truth.** The first matching handler paints the reply. No match flows the paragraph, then CRLF, then links. Null or empty input is an empty result with body color still requested, and it is not given a CRLF.

**Reason.** A reply that is sometimes a table and sometimes prose cannot be painted by every handler, and a reply that is nothing must not grow a blank line.

**Avoidances.** Do not score handlers or let a later handler repaint the first match. Do not flow, wrap, or annotate the empty input. Do not treat the reply header chrome as output of this router.


**semantic-key:** PK-0559

## Registration

`FormatRouterFactory.create` builds a new router. `smart-format` false registers nothing and returns that empty router. `smart-format` true registers five handlers, in this order, and in no other order:

1. `json` — `JsonDetector`, `JsonRenderer`
2. `fence` — `FenceDetector`, `FenceRenderer`
3. `table` — `TableDetector`, `TableRenderer`
4. `list` — `ListDetector`, `ListRenderer`
5. `markdown` — `MarkdownDetector`, `MarkdownRenderer`

The chat property defaults to true. The key is `casserole.chat.smart-format`. An empty router is not a missing pass: every non-empty reply still takes the identity path below. `register` appends. `getHandlers` returns the live list, not a copy. There is no separate unregister. A null handler is not rejected at registration; the pass would fail when it reached that slot.

**Truth.** The only registration order is json, fence, table, list, markdown. `smart-format` false registers none of them.

**Reason.** A blob can look like more than one of these. The earlier handler has to win, or a table inside a fence, and a list that is also markdown, get painted twice and wrong.

**Avoidances.** Do not reorder the five. Do not register a sixth detector in front of them. Do not treat `smart-format` false as "skip CRLF and links."

**semantic-key:** PK-5649

## The pass

`format` takes the original reply and a `FormatContext`. The branches, in order, are:

1. If the reply is null or its length is 0, return a result whose text is empty and whose body-color flag is true. Handlers are not called. Paragraph flow, line endings, and link annotation are not called.
2. If the context is null, substitute a new `FormatContext` (80 columns, table style `ascii`, four empty SGR strings) before any handler runs.
3. Walk the handlers in registration order. The first detector whose `matches` returns true paints the reply. Later detectors are not asked.
4. On a match, take the renderer text, convert it with `LineDiscipline.toCrlf`, then `ensureTrailingCrlf`, then `LinkAnnotator.annotate`. Keep the renderer's body-color flag. The router does not catch a renderer exception. Only the JSON renderer has its own catch.
5. If no handler matches, flow the original reply with `ParagraphFlow`. Do not call `WordWrap`. Then the same CRLF, trailing CRLF, and link steps. The body-color flag is true.

Detectors see the original characters, including the original line breaks. They do not see the flowed paragraph. A reply that is only spaces is not the empty input; it fails the five detectors and is flowed.

**Truth.** Match, then CRLF, then a trailing CRLF, then links. No match does the same after paragraph flow, and still does not hard-wrap.

**Reason.** Structured paint and plain paint have to share one line ending and one link pass, or the seat grows a second formatter.

**Avoidances.** Do not hard-wrap the identity path. Do not annotate before CRLF. Do not drop the body-color flag the renderer returned. Do not run the router again on its own output.

**semantic-key:** PK-5650

## Columns

`FormatContext` stores the column budget. The field starts at 80. `setColumns` stores 80 when the argument is less than 20, and stores the argument otherwise. There is no upper clamp. `getColumns` returns the stored value and does not clamp again. `setTableStyle(null)` stores `ascii`. Any other string is stored as given. The chat key `casserole.chat.table-style` defaults to `ascii`. The drawer, not the setter, decides which strings mean box drawing.

Width floors that the painters actually apply:

| Site | Rule |
|---|---|
| Context setter | Below 20 becomes 80. 20 and above are kept. |
| `WordWrap` | Below 8 becomes 8. |
| List body | Null context uses 78. Otherwise `columns - 2`, then below 8 becomes 8. |
| Fence prose and markdown headings and paragraphs | Null context uses 80. Otherwise the stored column count. `WordWrap` may still lift a width below 8. |
| Markdown list items and quotes | `columns - 2`, with no extra floor in the renderer. `WordWrap` lifts a width below 8. |
| Table budget | Below 16 becomes 16, then the chrome math below. |
| JSON | Columns are ignored. There is no wrap. |
| Identity | Columns are ignored. There is no wrap. |

The router never hands a renderer a null context, because a null context was already replaced. The null-context widths above are the renderers' own branches. The table renderer does not substitute a context; a null context there would fail when the drawer reads columns. The public setter cannot store a column count below 20, so the table floor of 16 and the list floor of 8 are not reached through that setter. They are still the rules in the painters. A list body at the setter's minimum of 20 columns is wrapped at 18. A markdown quote or markdown list at 20 columns is wrapped at 18. A heading or a paragraph at 20 columns is wrapped at 20.

The seat that builds a reply context copies the terminal width into `setColumns` after its own read of `COLUMNS`. A missing environment, a missing value, a value that is not an integer, or a value below 20 becomes 80. Every value that survives that floor is greater than 4, so the seat subtracts 2. `setColumns` then lifts a result below 20 back to 80. A reported width of 20 becomes 18 and is stored as 80. A reported width of 21 becomes 19 and is stored as 80. A reported width of 22 is stored as 20. A reported width of 80 is stored as 78. The router's own default of 80 still applies when the caller passes a null context or never sets columns.

**Truth.** The context default is 80 columns, and a set width below 20 is stored as 80. Painters then apply their own floors: wrap 8, table budget 16, list body `columns - 2` floored at 8.

**Reason.** A tiny budget collapses a table into a single unreadable column, and a list marker needs two columns that the body must not also consume.

**Avoidances.** Do not wrap the identity path to the column count. Do not use the list's minus-two for headings, fence prose, or JSON. Do not treat the seat's minus-two as a second clamp inside the router.

**semantic-key:** PK-5651

## Paragraph flow

`ParagraphFlow` makes one paragraph one run. It does not wrap to a width. It does not trim. Null or empty input returns empty.

Carriage return is discarded. It is not a break and it is not a space. A CRLF is a break only because the line feed remains after the carriage return is skipped. A lone carriage return joins the characters on either side with nothing between them.

A line feed is one of three branches:

- More line feeds follow, ignoring carriage returns between them. That is a blank line. The paragraph buffered so far is appended, then exactly two line feeds are written, however many extra line feeds were in the run. Three or more blank-looking breaks still become one blank line. The buffer is cleared.
- The line feed is the end of the string. It is dropped. It does not become a space, and it does not become a trailing break.
- The line feed is in the middle of the text and no extra line feed follows. If the buffer is non-empty, does not already end with a space, and the next kept character is not a space, one space is inserted. Otherwise no space is inserted. A tab is not a space for this test, so a tab on either side does not suppress the inserted space.

Appending a paragraph writes nothing when the buffer is empty. If the output is non-empty and does not already end in a line feed, one line feed is inserted first. A blank line has already written two line feeds, so the next paragraph sees a trailing line feed and does not insert a third.

Leading consequences: one leading line feed disappears; two or more leading line feeds become one leading blank line (`\n\n`) and then the text. Trailing consequences: one trailing line feed disappears; a trailing blank line stays as two line feeds after the paragraph. Spaces already in the text are kept. The flow does not collapse repeated spaces and does not invent a second space when one side of the line feed is already a space.

`one.\ntwo` becomes `one. two`. `one.\n Two` stays a single space. `one.\n\ntwo` and `one.\n\n\ntwo` both become `one.` plus a blank line plus `two`. `One.\nTwo.\n` becomes `One. Two.` with no trailing break.

The identity path is this flow and nothing wider. `WordWrap` calls this same flow before it measures a line.

**Truth.** A single line feed inside a paragraph becomes one space. A blank line stays one blank line. A trailing single line feed adds nothing. A carriage return adds nothing.

**Reason.** A hard break on every sentence made the terminal wrap each sentence as its own line. The terminal already soft-wraps one continuous run.

**Avoidances.** Do not join text across a blank line. Do not turn a blank run of three line feeds into two blank lines. Do not width-wrap inside this flow. Do not treat a carriage return alone as a paragraph break.

**semantic-key:** PK-5652

## Hard wrap

`WordWrap` is for the structured painters only. The identity path does not call it. Null or empty input returns empty. The text is flowed first. If the flow is empty, the result is empty. A requested width below 8 is raised to 8. The wrap walks the flowed text. A carriage return is skipped. At each line feed, and at the end, the current line is flushed. A line feed that is not the end of the text always appends one line feed after the flush, even when the flushed line was empty and wrote nothing. The last line does not get an extra line feed from the wrapper. A blank line from the flow therefore survives as a blank line. A single trailing line feed that the flow already dropped does not reappear.

Flushing a line:

- An empty line writes nothing.
- Leading spaces and tabs are consumed while the column is 0. They are not emitted. A body that is only spaces wraps to nothing.
- Each space or tab while the column is greater than 0 and less than the width emits one space. A tab is not expanded to a tab stop. It is one space. Whitespace at or past the width is consumed and not emitted.
- A word is a run that is not a space or a tab.
- If the column is greater than 0 and `column + 1 + word length` is greater than the width, a line feed is written and the column returns to 0. The word is not given a leading space on the new line. Spaces already emitted stay at the end of the previous line.
- Otherwise, if the column is greater than 0, one more space is written before the word. This space is in addition to any spaces the whitespace loop already emitted for the gap. A single space between two words that stay on the same line therefore becomes two spaces. Two spaces in the source become three. `hello world` at width 80 becomes `hello  world`. `a  b` at width 80 becomes `a   b`. `aa\tbb` at width 80 becomes `aa  bb`.
- If the word is longer than the width and the column is 0, the word is cut into chunks of exactly the width. Each chunk except the last is followed by a line feed. The last chunk's length is the new column. `abcdefghij` at width 8 becomes `abcdefgh` plus a line feed plus `ij`.
- A long word that does not start at column 0 takes the wrap branch first, which puts the column at 0, and then takes the hard cut.
- A word that fits is copied whole. The column advances by its length.

The normative short wrap is `one.\ntwo stays` at width 8. The flow joins it to `one. two stays`. The flush produces `one. ` plus a line feed plus `two ` plus a line feed plus `stays`. The spaces at the ends of the first two lines are the separator spaces emitted before the next word failed to fit. Those next words do not get the extra same-line space.

**Truth.** Structured prose is flowed, then hard-wrapped at spaces, with a floor of 8. A word that stays on the line gets one space beyond the spaces already copied from the gap. A word longer than the width is cut.

**Reason.** Fence prose, list bodies, and markdown paragraphs have to fit a column budget. The identity path must not, because the terminal soft-wraps that run.

**Avoidances.** Do not call this wrap from the identity path. Do not describe the same-line gap as a single space. Do not drop leading spaces in the spec and then emit them; the flush discards them at column 0. Do not expand tabs to stops.

**semantic-key:** PK-5653

## Line endings

`LineDiscipline` runs on every non-empty path, including identity and including a handler that already emitted line feeds. Null or empty input returns empty.

`toCrlf` walks each character:

- A carriage return is kept. If the next character is a line feed, that line feed is kept and consumed with it. If the carriage return is alone, a line feed is added. A lone carriage return becomes CRLF.
- A lone line feed becomes CRLF.
- Any other character is copied.

Existing CRLF stays one CRLF. It is not doubled.

`ensureTrailingCrlf` leaves an empty string empty. A text that already ends in CRLF is unchanged. A text that ends in a lone line feed has that line feed replaced by CRLF. Any other non-empty text gains one CRLF at the end.

JSON's pretty printer emits the platform line separator between object lines and does not add a line feed after the final brace. On a platform whose separator is already CRLF, `toCrlf` keeps those breaks, and `ensureTrailingCrlf` adds the final CRLF. On a platform whose separator is LF, `toCrlf` converts them, and the trailing step still adds one CRLF. The bytes that leave the router are CRLF either way.

**Truth.** Every non-empty result uses CRLF, and a non-empty result ends with CRLF. An empty result does not.

**Reason.** The terminal is a VT100 seat. A lone line feed does not return the carriage, and a missing trailing break glues the next prompt to the reply.

**Avoidances.** Do not convert an existing CRLF into two breaks. Do not add a trailing CRLF to the empty result. Do not skip this step on the identity path or on a handler that already used line feeds.

**semantic-key:** PK-5654

## Links

`LinkAnnotator` runs after line endings, on every non-empty path. Null or empty input returns empty. If the text already contains the OSC 8 opener `ESC ] 8 ; ;` anywhere, the whole text is returned unchanged. No URL in that text is annotated.

Otherwise the scan finds the earlier of `http://` and `https://`, case-sensitive, from the current index. `HTTP://` and `www.` are not links. `https://` does not contain `http://`, because the character after `http` is `s`.

The URL runs until a character whose code is at most 32, or until `<`, `>`, `"`, `'`, or `)`. The scan therefore stops at space, tab, carriage return, and line feed. Trailing `.` `,` `;` and `]` are removed from the URL that is annotated. They are written after the reset, outside the link. `)` never enters the URL, because the scan already stopped. `:` `!` and `?` are not trimmed.

If the trimmed URL contains a carriage return or a line feed, or if its length is less than 8, the untrimmed scan is copied as ordinary text. The scan stops at a control character, so the carriage-return reject does not fire for a URL the scan itself built. The length gate does fire. `http://` is 7 characters and is not annotated. `https://` is 8 characters and is annotated, even with an empty host. `http://` plus one more character is 8 and is annotated. `http://x` is annotated. A trimmed `http://.` is 7 and is not annotated; the period remains text.

A URL that passes is replaced, in this byte order:

1. `ESC [ 3 5 m` — SGR 35, the link purple. This is standard magenta, not bright SGR 95.
2. `ESC ] 8 ; ;` then the trimmed URL, then BEL (`U+0007`). This opens OSC 8. The terminator is BEL, not `ESC \`.
3. The trimmed URL again, as the visible text.
4. `ESC ] 8 ; ;` then BEL. This closes OSC 8.
5. `ESC [ 0 m` — SGR 0, reset.

The scan then continues after the untrimmed end, so a later URL is wrapped on its own. Each link carries its own purple and its own reset.

**Truth.** An `http://` or `https://` URL of at least 8 characters is painted purple and wrapped in OSC 8 with BEL. A shorter candidate, an uppercase scheme, and a text that already contains OSC 8 are left alone.

**Reason.** A capable terminal can open the URL. A second OSC 8 wrapped around an existing one, or a purple wrap around the seven characters `http://`, paints a link that does not go anywhere.

**Avoidances.** Do not annotate before CRLF. Do not lowercase the scheme in order to match it. Do not switch the terminator to `ESC \`. Do not use bright magenta. Do not swallow the trimmed punctuation into the URL. Do not annotate when OSC 8 is already present.

**semantic-key:** PK-5655

## JSON

`JsonDetector` matches a whole reply that is one JSON object or one JSON array. Null is no match. The reply is trimmed. A trimmed length below 2 is no match. The first remaining character must be `{` or `[`. A leading byte-order mark is not stripped, so a reply that starts with that mark does not match. A scalar does not match: a JSON string, number, `true`, `false`, or `null` fails the first-character test even when it is longer than one character. The trimmed text is parsed with a default Jackson mapper. The node must be non-null and must be an object or an array. Any parse failure is no match. Trailing tokens are not a failure: the mapper's default does not reject them. `{}\n{"b":2}` matches. Only the first value is the value that matched.

`JsonRenderer` trims again, parses again, and writes with Jackson's default pretty printer on a mapper that has indent output enabled. Columns and SGR are ignored. There is no word wrap. The body-color flag is true. If parse or write throws, the original untrimmed reply is returned and the flag stays true.

The pretty printer's shape, which the renderer does not restyle, is:

- Object fields are indented two spaces per level and separated with the platform line separator.
- A colon has one space on each side: `"a" : 1`.
- An empty object is `{ }` with one space. An empty array is `[ ]` with one space.
- Array values stay on one line. A space follows `[`, a comma and a space separate values, and a space precedes `]`: `[ 1, 2 ]`.
- The document does not grow a line break after the final `}` or `]`. The router's trailing CRLF supplies the last break.
- A successful write emits only the first value. Trailing text that the detector tolerated is dropped, not appended. `{}\n{"b":2}` paints `{ }`.

**Truth.** A trimmed object or array is pretty-printed and keeps body color. A scalar, a short string, a parse failure, or a reply that does not start with `{` or `[` is not JSON.

**Reason.** A raw object is one long line on an 80-column seat. A scalar is ordinary prose and must not be claimed by the first handler.

**Avoidances.** Do not wrap JSON to the column count. Do not match scalars. Do not keep trailing text after a value that parsed. Do not put JSON after fence in the registration order.

**semantic-key:** PK-5656

## Fences

`FenceDetector` matches when some line opens a fence and a later line closes it with the same mark. An orphan opener does not match. The reply is split on line feeds after every CRLF and every lone carriage return has been turned into a line feed. A trailing empty piece from a final line feed is kept by the split.

A line's mark is decided after trim:

- If the trimmed line starts with three backticks, the mark is exactly `` ``` ``. The scan that follows rejects a non-backtick only in those first three columns, which the prefix has already filled, so an info string does not reject the line. `` ```java `` is an opener. A run of four backticks is the same mark, not a longer mark.
- If the trimmed line starts with three tildes, the mark is exactly `~~~`. An info string is allowed. The mark is not the info string.
- Otherwise the line has no mark.

The first marked line is the opener. A later line with the equal mark closes it, and the detector returns true immediately. The rest of the reply is not required to be empty. A different mark does not close the opener. Backticks and tildes do not close each other. Indentation is ignored by the trim, so an indented fence still matches. The closer does not have to be at least as long as the opener; both marks are exactly three characters.

`FenceRenderer` walks those same lines. The width is 80 when the context is null, otherwise the stored column count. Outside a fence, lines accumulate into a prose buffer, joined by line feeds. A fence opener flushes that prose, writes the original opener line plus a line feed, and enters the fence. The original line is not trimmed, so indentation in front of the marker is kept. Inside the fence, every line is copied plus a line feed, with no wrap and no flow. A line whose mark equals the opener's mark leaves the fence and is copied the same way. A different mark inside the fence is interior text, not a nested fence. After the closer, prose accumulation starts again.

Flushing prose runs `WordWrap` at the column width. If the wrap does not already end in a line feed, one line feed is added. An empty prose buffer writes nothing. A single blank line immediately before a fence is a trailing line feed on that prose buffer; the flow drops one trailing line feed, so that blank line disappears. Two blank lines before a fence survive as one blank line, because the flow keeps a trailing blank. A blank line between two prose lines, with text on both sides, stays a blank line.

If the walk ends still inside a fence, the renderer flushes a fresh empty buffer and does not flush the prose buffer again. The opener already flushed the prose before it. No synthetic closer is written. The detector does not select this renderer for an orphan, so the router does not use that branch for an unmatched fence. The branch is still in the renderer.

The body-color flag is true. Fence markers and info strings stay. The interior is not pretty-printed even when it is JSON, because JSON was already given the first chance and a fenced reply does not start with `{` or `[` unless the fence is inside the JSON text.

**Truth.** A backtick fence or a tilde fence with a later line of the same mark matches. Prose outside is flowed and hard-wrapped. The fence interior, the opener, and the closer are copied raw.

**Reason.** Wrapping code breaks the code. Leaving the prose around it unwrapped blows the column budget.

**Avoidances.** Do not require the closer's run to be longer than three. Do not let tildes close backticks. Do not wrap the interior. Do not drop the info string on this path. Do not treat an orphan as a fence match; that reply falls through.

**semantic-key:** PK-5657

## Tables

`TableDetector` matches only when `parse` returns a model. Null input does not match. Line endings are normalized the same way as fences. Lines whose trim is empty are dropped. Fewer than three remaining lines does not match.

The first line that is a separator is the separator. It must not be the first remaining line, and it must not be the last, so there is a header line and at least one data row. The header is only the remaining line immediately before that separator. Earlier remaining lines are not validated and are not kept. A prose line in front of an otherwise valid table still matches, and the painter discards that prose.

A separator cell, after trim, is non-empty, contains at least one `-`, and contains only `-` and `:`. `:---`, `---:`, `:---:`, and `---` are separators. A cell that is only `:` is not. An empty cell is not. Alignment colons are accepted and then ignored. The painter does not right-align or center.

Rows are split on `|` after trim. One leading `|` is dropped, when present. One trailing `|` is dropped when that would not eat the start. The last segment is always kept, so a row produces at least one cell. Each cell is trimmed. Interior empty cells are kept. A pipe is not an escape: `\|` is still a split. The separator's cell count must equal the header's cell count. The header-size guard below 1 does not fire for a split that always returns a cell; it remains the guard.

Each later remaining line must contain `|`. A line that does not is not a table, even when the header had one column. `h` / `---` / `v` does not match. `| h |` / `| --- |` / `| v |` does. Each data row's cell count must equal the header's. A second separator-looking line is data, if it has pipes and the same count. It is not a new header. There is no caption after the rows.

`TableRenderer` parses again. If that returns null, it returns the original text and sets body color true. The router only calls it after a match, so this is the renderer's own miss. On a model, `TextTableDrawer` paints the table and body color is false. The table paints its own SGR and must not also be wrapped in the body color.

An empty column count draws nothing. The budget is the context's column count, raised to 16 when it is below 16. Content widths start at the longest cell in that column, including the header, and are at least 1. A short data row contributes only the cells it has. A missing cell later draws as empty. Extra cells past the header width are not read.

Chrome is `1 + (column count × 3)`. The available content sum is `budget - chrome`, and if that is less than the column count it is raised to the column count, one each. While the sum of widths exceeds the available sum, the widest column shrinks by one. Ties take the leftmost index. Shrinking stops when the widest column is already 1, even if the table is still wider than the budget. There is no ellipsis. A value longer than its width is cut with `substring`, from the start, to that width.

At a budget of 20 and two content widths of 10, chrome is 7 and the available sum is 13. The leftmost-tie shrink ends at widths 6 and 7. The ASCII rules are `+--------+---------+`. The cells show `abcdef` and `klmnopq`, and `123456` and `zzzzzzz`.

Box drawing is used only when the table style equals `box`, ignoring case. `BOX` and `Box` are box. `ascii`, empty, null, and any other word are ASCII. Null style from the getter is treated as not box. The setter already turned a null style into `ascii`.

ASCII glyphs:

| Role | Character |
|---|---|
| Left, junction, right | `+` `U+002B` |
| Horizontal | `-` `U+002D` |
| Vertical | `\|` `U+007C` |

Box glyphs:

| Role | Character |
|---|---|
| Horizontal | `─` `U+2500` |
| Top left, top join, top right | `┌` `U+250C`, `┬` `U+252C`, `┐` `U+2510` |
| Middle left, cross, middle right | `├` `U+251C`, `┼` `U+253C`, `┤` `U+2524` |
| Bottom left, bottom join, bottom right | `└` `U+2514`, `┴` `U+2534`, `┘` `U+2518` |
| Vertical | `│` `U+2502` |

A rule is one border-SGR span around the whole rule, then reset. It is the left glyph, then for every column `width + 2` horizontal glyphs, with a junction between columns, then the right glyph. The `+ 2` is the pad spaces, not extra content. A top rule uses the top corners. A rule under the header uses the middle joins. The bottom rule uses the bottom corners. ASCII uses `+` for every corner and junction, so top, middle, and bottom look the same.

A data line opens with border SGR, vertical bar, reset. Each cell is then cell SGR, one space, the cut value, spaces out to the width, one space, reset, border SGR, vertical bar, reset. The header row uses the header SGR. Body rows use the cell SGR. The drawn order is top rule, header, middle rule, each body row, bottom rule. Every one of those lines ends with a line feed. Cells are left aligned. The separator's colons do not change that.

The border, header, cell, and reset strings are whatever the context holds. Getters turn a null SGR into an empty string, which adds no bytes. The context default is four empty strings, so a table drawn without the seat's colors has no SGR of its own and still clears body color.

**Truth.** A header, a dash-and-colon separator, and at least one pipe row of the same width, with three or more non-empty lines, become an ASCII table unless the style is `box`. Body color is off when the table draws.

**Reason.** A pipe row is not a paragraph, and a table that also receives the body-color wrap paints every bar in the body color and fights the border color.

**Avoidances.** Do not honor separator alignment. Do not keep the prose that sat above the header. Do not wrap cell text. Do not ellipsize a cut cell. Do not treat a non-`box` style as box. Do not set body color true on a drawn table.

**semantic-key:** PK-5658

## Lists

`ListDetector` matches when every non-empty line is one item and there are at least two items. Null does not match. Line endings are normalized as for fences. A line whose trim is empty is skipped, not rejected. One rejecting line rejects the whole reply. One item is not a list. A blank reply has zero items and is not a list.

An item may start with spaces or tabs. After that indent:

- A bullet is `-`, `*`, or `+`, then a space or a tab, then at least one more character. `- item` matches. `-item` does not. A marker with no body does not.
- An ordered item is one or more digits, then `.` or `)`, then a space or a tab, then at least one more character. `1. a` and `10) b` and `01. a` match. `1.` and `1. ` do not. A sign is not accepted. Letters are not accepted.

Mixed bullets and numbers match, as long as every non-empty line is some item. Indentation does not make a nested list; the detector is flat.

`ListRenderer` drops empty lines. The body width is 78 when the context is null. Otherwise it is `columns - 2`, raised to 8 when that is below 8. The marker is stripped: indent, then one bullet or the digit run, then one `.` or `)` when present, then one space or tab. The rest is the body. The body is hard-wrapped at that width. The first wrapped line is `- ` plus the text. Each continuation is two spaces plus the text. The original marker is not kept. Ordered numbers are not kept. `*` and `+` become `-`. There is no blank line between items and no extra blank line after the list. The body-color flag is true.

A pure two-item list therefore never reaches the markdown handler. The markdown list painter runs only when this detector has already said no.

**Truth.** Two or more item lines, and no other non-empty line, become a hanging `- ` list wrapped at `columns - 2`, floored at 8.

**Reason.** A one-item line is a sentence that happens to start with a dash. Painting it as a list, or letting markdown repaint a pure list, drops the marker the detector already accepted.

**Avoidances.** Do not require a blank line between items. Do not preserve `1.` or `*`. Do not match a marker that has no body. Do not let one prose line through. Do not wrap the body at the full column count; the marker and the continuation indent take two columns.

**semantic-key:** PK-5659

## Color

Two different colors exist, and they are applied at two different times.

The router itself emits color only in the link annotator: SGR 35 purple around the OSC 8 URL, and SGR 0 after it. Table SGR is not chosen by the router. The drawer copies four context strings: border, header, cell, and reset. Empty strings are the context default and add no bytes. Getters replace a null with an empty string. Setters store a null SGR as null.

Body color is a flag, not a byte, until the seat reads the result. The flag is true for the empty input, for the identity path, and for JSON, fence, list, and markdown. It is true for a table renderer miss. It is false when a table was actually drawn. The seat's reply wrap, when the flag is true, is `ESC [ 9 3 m` plus the whole result plus `ESC [ 0 m`. That is SGR 93, bright yellow, then reset. When the flag is false, the seat copies the result unchanged and does not add that yellow.

The seat's reply context sets the four table strings before `format`:

| Slot | Bytes | Color |
|---|---|---|
| Border | `ESC [ 3 8 ; 5 ; 4 5 m` | Electric blue, xterm index 45 |
| Header | `ESC [ 9 3 m` | Bright yellow, SGR 93 |
| Cell | `ESC [ 9 3 m` | Bright yellow, SGR 93 |
| Reset | `ESC [ 0 m` | SGR 0 |

A caller that never sets those strings gets an uncolored table and a cleared body-color flag. The electric blue is the seat's assignment, not a default inside the drawer.

Order on a body-colored reply that contains a link: the seat opens bright yellow, the annotator later opens purple and OSC 8, the visible URL is purple, and the link's SGR 0 clears every attribute, including the yellow. Text after that link is not put back into yellow. The seat's final SGR 0 still closes the result. Each further link opens purple again on its own.

Order on a drawn table: there is no outer yellow. Each rule is one border span. Each bar is border, reset. Each cell interior is header or cell yellow, then reset. Link annotation runs after the draw, so a URL inside a cell inserts SGR 35 and a later SGR 0 in the middle of that cell. The SGR 0 clears the cell yellow for the padding that follows, until the next bar opens the border color again.

**Truth.** Links are SGR 35 and OSC 8. Body color, when the flag is true, is an outer SGR 93. A drawn table clears that flag and uses the context SGR, which the seat fills with electric blue borders and bright yellow cells.

**Reason.** A table that has already painted its bars cannot sit inside a second color wrap. A link that does not reset would leave the rest of the reply purple.

**Avoidances.** Do not hard-code electric blue in the drawer. Do not yellow-wrap a result whose flag is false. Do not use SGR 95 for links or SGR 33 for the body. Do not move link annotation before the table SGR is written. Do not re-open yellow after a link reset; the reset is SGR 0, and the seat does not repaint the tail.


**semantic-key:** PK-5660
