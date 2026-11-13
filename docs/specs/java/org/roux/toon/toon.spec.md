# org.roux.toon

## 12. Rust row

Side move. `ToonCodec.parseMessageTable`, `ToonCodec.conversationTable`, and `ToonCodec.pairConversation` read and write the same table `exportToon` writes. The requirement is one codec for both directions. The sibling is `appendTableRow`.
**semantic-key:** PK-567

## 3. Side move

`ToonCodec.parseMessageTable`, `ToonCodec.conversationTable`, and `ToonCodec.pairConversation` use the table `exportToon` already writes. The sibling is `appendTableRow`.
**semantic-key:** PK-568

## Seat

Every public type under org.roux carries type-level Knuth prose and one type-level tag. A method that realizes a seated requirement carries the prose and the tag. A private getter stays. ToonCodec encode carries for the one MSG. Comments and JavaDoc only. No behavior change.

## 8. Engine APIs (no I-prefix)

- `void writeFrame(...)` // not raw `byte[]` from callers; encode via `ToonCodec` into the hot page
**semantic-key:** PK-569
