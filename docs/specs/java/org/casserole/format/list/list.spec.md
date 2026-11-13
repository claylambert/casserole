# org.casserole.format.list

**Depends:** [org.casserole.format](../format.spec.md)

## 6.2 Handler order (fixed)

4. **list** — every non-empty line is a bullet (`- * +`) or ordered (`1. ` / `1) `) item; ≥2 items. Rendered as hanging `- ` with wrap at `columns-2`. Body color on.

`ListDetector` is a public type in this package.

`ListRenderer` is a public type in this package.
