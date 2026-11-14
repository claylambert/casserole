# org.roux.index

**Depends:** [org.roux.id](../id/id.spec.md), [org.roux.layout](../layout/layout.spec.md), [org.roux.model](../model/model.spec.md)

### MonotonicIndex

The dense off-heap index. Message id starts at 1. The slot is id minus 1. Each row stores seq and the RouxId together. The index stays in RAM.
**semantic-key:** L40

The MonotonicIndex is the off-heap list of message rows. A message id starts at 1. The slot for that id is id - 1. Each row is 56 bytes. A row stores the message id, the page, the offset, the length, and the token count. A row stores the time, the role, the type, the flags, and the two identity longs. The index stays in memory. A new index holds 256 rows. The backing memory grows by doubling.
**semantic-key:** PK-1266

