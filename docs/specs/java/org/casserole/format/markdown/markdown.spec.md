# org.casserole.format.markdown

**Depends:** [org.casserole.format](../format.spec.md)

## 6.2 Handler order (fixed)

5. **markdown** — CommonMark AST has a heading, fenced code, block quote, or a list of ≥2 items. A single paragraph (even with `*emphasis*`) does **not** match. Renderer drops emphasis markers, keeps structure, wraps prose, lists as `- `, quotes as `> `, fenced code literal. Body color on.

`MarkdownDetector` is a public type in this package.

`MarkdownRenderer` is a public type in this package.

## A reply paragraph stays together

A heading and a paragraph are not broken at the width boundary. The wrap receives `Integer.MAX_VALUE`, so the pane wraps the paragraph. A fenced code block copies its literal lines. A list wraps each item to the width minus two. A quote wraps to the width minus two. The method is `emitBlock`. The file is [MarkdownRenderer.java](../../../../../../../src/main/java/org/casserole/format/markdown/MarkdownRenderer.java). No new public method.
**semantic-key:** PK-1408
