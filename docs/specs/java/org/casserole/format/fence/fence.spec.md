# org.casserole.format.fence

**Depends:** [org.casserole.format](../format.spec.md)

## 6.2 Handler order (fixed)

2. **fence** — matching ` ``` ` or ` ~~~ ` opener/closer. Prose wrapped; fence interiors untouched. Body color on.
**semantic-key:** PK-343

`FenceDetector` is a public type in this package.

`FenceRenderer` is a public type in this package.

## A reply paragraph stays together

Fence prose is not broken at the width boundary. The wrap receives `Integer.MAX_VALUE`, so a paragraph stays together and the pane wraps it. The width parameter stays on the method. The method is `flushProse`. The file is [FenceRenderer.java](../../../../../../../src/main/java/org/casserole/format/fence/FenceRenderer.java). No new public method.
**semantic-key:** PK-1409
