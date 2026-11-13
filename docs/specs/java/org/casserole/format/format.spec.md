# org.casserole.format

## Child pages

<pre>
org.casserole.format/
├── <a href="fence/fence.spec.md">org.casserole.format.fence</a>
├── <a href="json/json.spec.md">org.casserole.format.json</a>
├── <a href="list/list.spec.md">org.casserole.format.list</a>
├── <a href="markdown/markdown.spec.md">org.casserole.format.markdown</a>
└── <a href="table/table.spec.md">org.casserole.format.table</a>
</pre>

**Depends:** [org.casserole.format.json](json/json.spec.md), [org.casserole.format.fence](fence/fence.spec.md), [org.casserole.format.table](table/table.spec.md), [org.casserole.format.list](list/list.spec.md), [org.casserole.format.markdown](markdown/markdown.spec.md)

## 5. Proof

The test is ParagraphFlowTest. No pool turn. No live SSH connect. Cargo is not required because the Rust grid stays. The folded bar failure stays outside this diff. The thinking-strip check stays outside this diff.

## 6. Prose

No new public type. No new public method. The package-private type is ParagraphFlow. The method is flow. The new test is ParagraphFlowTest. The files are ParagraphFlow.java, FormatRouter.java, WordWrap.java, and ParagraphFlowTest.java.
**semantic-key:** PK-344
**semantic-key:** PK-344
**semantic-key:** PK-344

#### Width

`COLUMNS` from the SSH environment. Parsed in `parseColumns`: default 80; if &lt; 20 → 80; if &gt; 4 → `cols - 2` (margin for wrap/tables). Used by `FormatContext`. Not updated on later window-change in the current code (a gap: client *does* send `window-change`).
**semantic-key:** PK-345

### 6.1 Contract

`FormatRouter.format(raw, ctx)`:

### 6.1 Contract

3. Renderer output → `LineDiscipline.toCrlf` + trailing CRLF → `LinkAnnotator.annotate`.
**semantic-key:** PK-346
**semantic-key:** PK-346

### 6.2 Handler order (fixed)

1. **json** — whole-string JSON object or array (Jackson). Scalars do not match. Pretty-print, no wrap. Body color on.
2. **fence** — matching ` ``` ` or ` ~~~ ` opener/closer. Prose wrapped; fence interiors untouched. Body color on.
3. **table** — whole-blob GFM pipe table: header, separator (`---` / `:---:`), ≥1 data row, equal column counts. Drawn as ASCII or Unicode box. **`applyBodyColor=false`** (borders electric blue, cells yellow via FormatContext SGR).
**semantic-key:** PK-343
4. **list** — every non-empty line is a bullet (`- * +`) or ordered (`1. ` / `1) `) item; ≥2 items. Rendered as hanging `- ` with wrap at `columns-2`. Body color on.
5. **markdown** — CommonMark AST has a heading, fenced code, block quote, or a list of ≥2 items. A single paragraph (even with `*emphasis*`) does **not** match. Renderer drops emphasis markers, keeps structure, wraps prose, lists as `- `, quotes as `> `, fenced code literal. Body color on.

## 12. How to use this document in Grok

4. For new reply shapes: new detector+renderer + `FormatRouterFactory.register` in the agreed order.
**semantic-key:** PK-347

## 13. File checklist (implementation inventory)

- `src/main/java/org/casserole/format/FormatRouter.java`
**semantic-key:** PK-348

- `src/main/java/org/casserole/format/FormatRouterFactory.java`
**semantic-key:** PK-349

- `src/main/java/org/casserole/format/{FormatContext,FormatHandler,FormatResult,FormatDetector,FormatRenderer,LineDiscipline,LinkAnnotator,WordWrap}.java`
**semantic-key:** PK-350
**semantic-key:** PK-350
**semantic-key:** PK-350
**semantic-key:** PK-350
**semantic-key:** PK-350
**semantic-key:** PK-350
**semantic-key:** PK-350
**semantic-key:** PK-350
