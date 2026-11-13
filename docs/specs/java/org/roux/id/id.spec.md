# org.roux.id

## SSH lock — frozen

The edit of one head turn is drawn on this page. `message edit` takes the seq. A body on the same line is the new text. A bare seq reads until a line that is `<.>`. The frame is appended. The index slot moves. The seq and the RouxId stay. [reichenbach-sink-message-edit.png](../../../../images/reichenbach-sink-message-edit.png) is that flow.

## 2. Identity in 1.1 (locked mechanics)

Identity makes a spilled `.pg` **recoverable**: scan frames, read seq + RouxId, rebuild an index. 1.1 does not have to *do* crash-rebuild, but the bytes must *allow* it. That is the foundation.
**semantic-key:** PK-483

## 7. Frames vs pages (large agent payloads)

**Recommendation:** **F0** for 1.1 (raise `page-bytes` if tool dumps are huge). Design F2 in the type byte (already reserved) but do not implement unless you insist. Two-key identity is what makes F2 **safe later** (chunks share RouxId). Make it so number one. **F0** it is.
**semantic-key:** PK-976

### 13.2 Layers (do not implement now)

+ are “good enough for jazz.” + assume frames already carry `seq` + RouxId + CRC — that is the due diligence.

## 17. Gaps and gotchas (this pass)

7. **Duplicate RouxId.** If the generator is sound, ignore. Do not treat as an update-in-place (log is append-only).
**semantic-key:** PK-484

## 18. Nice-to-haves (not 1.1 unless free)

- Hex RouxId in show as extra line

## 4. Manifest — metadata to capture

**Not needed for this slice:** system-prompt text (already frame 1; restore does not re-insert it), window sizes (yaml), RouxId generator state (new ids stay unique), pin table, working-set list (page-in hot only; cold on demand as 1.1).
**semantic-key:** PK-485

## 2. As seated

**Identity.** `appendMessage` mints a `RouxId` and stores it on the frame and the index row. `region.byId` maps that id to the message id. `findSeq` reads the map.
**semantic-key:** PK-486
