# org.casserole.format.table

**Depends:** [org.casserole.format](../format.spec.md)

## 6.2 Handler order (fixed)

3. **table** — whole-blob GFM pipe table: header, separator (`---` / `:---:`), ≥1 data row, equal column counts. Drawn as ASCII or Unicode box. **`applyBodyColor=false`** (borders electric blue, cells yellow via FormatContext SGR).
**semantic-key:** PK-343

## 6.4 Tables

`TextTableDrawer`:

- Column widths from content, then shrink widest columns to fit `FormatContext.columns` including chrome (`1 + 3*n` for borders/padding).
- `ascii`: `+` / `-` / `|`
- `box`: Unicode `┌┬┐ ├┼┤ └┴┘ │ ─`
- Truncates cells that still overflow after shrink.
- Header and body cells use yellow SGR from ChatShell’s `replyContext()`.
**semantic-key:** PK-351

`FormatContext` from ChatShell: columns from PTY, table-style from yaml, border electric blue, header/cell yellow, reset.

`TableDetector` is a public type in this package.

`TableModel` is a public type in this package.

`TableRenderer` is a public type in this package.
