# org.casserole.format.markdown

**Depends:** [org.casserole.format](../format.spec.md)

## 6.2 Handler order (fixed)

5. **markdown** — CommonMark AST has a heading, fenced code, block quote, or a list of ≥2 items. A single paragraph (even with `*emphasis*`) does **not** match. Renderer drops emphasis markers, keeps structure, wraps prose, lists as `- `, quotes as `> `, fenced code literal. Body color on.

`MarkdownDetector` is a public type in this package.

`MarkdownRenderer` is a public type in this package.
