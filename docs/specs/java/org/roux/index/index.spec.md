# org.roux.index

**Depends:** [org.roux.id](../id/id.spec.md), [org.roux.layout](../layout/layout.spec.md), [org.roux.model](../model/model.spec.md)

### MonotonicIndex

The dense off-heap index. Message id starts at 1. The slot is id minus 1. Each row stores seq and the RouxId together. The index stays in RAM.
**semantic-key:** L40
