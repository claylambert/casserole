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

This page builds the reply painter. The map is [program-spine.md](../../../../README.md).


**semantic-key:** PK-0558

# Reply format router

One assistant reply is painted by one pass of `FormatRouter`. The pass either accepts the first registered handler or, when none match, flows the paragraph. It then forces CRLF, forces a trailing CRLF on a non-empty result, and annotates links. An empty or null reply stops before all of that.

The types that do the work live in `org.casserole.format` and in the `json`, `fence`, `table`, `list`, and `markdown` packages under it. Detectors are strict. They do not score. Renderers paint VT100 text and return a `FormatResult`: the text, and `applyBodyColor`. A null text stored in a result becomes the empty string. The router does not run the detectors a second time on the painted text.

**Truth.** The first matching handler paints the reply. No match flows the paragraph, then CRLF, then links. Null or empty input is an empty result with body color still requested, and it is not given a CRLF.

**Reason.** A reply that is sometimes a table and sometimes prose cannot be painted by every handler, and a reply that is nothing must not grow a blank line.

**Avoidances.** Do not score handlers or let a later handler repaint the first match. Do not flow, wrap, or annotate the empty input. Do not treat the reply header chrome as output of this router.


**semantic-key:** PK-0559

## Markdown

`MarkdownDetector` uses the CommonMark parser at the default settings, with no extensions. Null, or a trim that is empty, does not match. Only the document's top-level children are inspected.

A top-level heading, fenced code block, or block quote sets the structure flag. A top-level bullet list or ordered list adds its direct child item count. Nested items inside an item are not added. When the running item count reaches 2, the structure flag is set. The match is true when that flag is set and either the item count is at least 2 or a heading, fenced code block, or block quote is present. A heading alone matches. A quote alone matches. A fenced block alone matches. Two items match, including two separate one-item lists whose counts add to 2. One item does not match. A single paragraph does not match, even with emphasis, code spans, or a link. A thematic break does not match. An indented code block does not match. A setext heading is a heading and matches. An ATX heading matches.

An orphan fence does not match `FenceDetector`, and CommonMark still builds a fenced code block that runs to the end of the document. That reply matches here. The router then uses this renderer, not the fence renderer.

`MarkdownRenderer` parses again. The width is 80 when the context is null, otherwise the stored column count. Only top-level blocks are emitted. A block this renderer does not name writes nothing. That includes a thematic break, an indented code block, and an HTML block.

- A heading of any level becomes the inline text, hard-wrapped at the full width, then two line feeds. The level and the marker characters are dropped.
- A paragraph is the same shape as a heading.
- A fenced code block writes the literal, adds a line feed if the literal does not end with one, then one more line feed. The opener, the closer, and the info string are not written. The literal is not wrapped. A null literal writes only the extra line feed.
- A bullet list or an ordered list writes each direct item as `- ` plus the trimmed inline text, hard-wrapped at `width - 2`. Continuations get two spaces. Ordered numbers and the original bullet character are dropped. One extra line feed follows the list.
- A block quote hard-wraps the inline text at `width - 2` and prefixes every piece with `> `, then one extra line feed.

Inline text keeps text literals and code-span literals, and drops the backtick markers. Emphasis and strong emphasis keep their inner text and drop the markers. A soft break and a hard break each become one space, so a markdown hard break is not a hard break on the seat. A link keeps the label and drops the destination. If the label is not the URL, the URL is gone before link annotation runs. An image and any other node fall through the same recursion: child text is kept, and a leaf that is not text, code, or a break writes nothing. HTML inline is such a leaf and is dropped. Two block children of one item are concatenated with nothing inserted between them. A list inside a quote is flattened into the quote text; it does not grow `- ` markers of its own.

Because `WordWrap` adds the extra same-line space, a paragraph `Hello *there*` becomes `Hello  there` before the two trailing line feeds. That is the wrap rule, not a second markdown rule.

The body-color flag is true. A pure list of two items was already taken by the list handler, so this renderer's list branch is the one that runs when a heading, a quote, a fence, or a non-item line kept the list detector from matching.

**Truth.** A top-level heading, fenced block, quote, or at least two list items is CommonMark structure. The painter keeps the structure in plain text, drops emphasis markers, and hard-wraps prose.

**Reason.** Emphasis markers and a raw pipe-free paragraph are noise on this seat. A heading or a real list is structure, and it must not be flattened by the identity flow.

**Avoidances.** Do not match one paragraph. Do not count nested items toward the two-item gate. Do not let this handler run before the list handler. Do not re-emit fence markers on this path. Do not keep a link destination that is not in the label. Do not preserve a hard break as a line feed.


**semantic-key:** PK-0560
