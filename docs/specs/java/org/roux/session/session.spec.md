# org.roux.session

## `casserole.chat`

Load **one** file: `./users.yaml` → `~/.casserole/users.yaml` → classpath `users.yaml` → else `casserole.ssh` pair. File present = source of truth (no merge). Duplicate names refuse Boot. Invalid `RegionOwner` names skipped.

## Side file — frozen

> `RegionManifest.SIZE` is 112. names a 64-byte `clip.bin` whose head origin is one long. Designated seqs do not fit in that long.

## 11. Implementation proposal

1. A clip holder beside `RegionManifest`, in `org.roux.session`, file name `clip.bin`, layout in section 9. Load on region open. Absent file and CRC miss mean no floor. The build sentence names the type if a new public type is required. This filing creates none.
**semantic-key:** PK-523

## 3. The hole

4. **A manifest can disappear on the fallback path.** `RegionManifest.write` tries an atomic move. On failure it deletes `manifest.bin` and then moves the tmp into place. A crash in that window leaves no manifest. The `finally` also deletes the tmp. The pack can be the only survivor.
**semantic-key:** PK-524

### Lock

| Rule | Lock |
|---|---|
| Match | Any row: name + password |
| Names | `RegionOwner` `[A-Za-z0-9._-]+`. Invalid row: skip + log at Boot |
| Duplicate names | **Refuse Boot** |
| Empty password | Reject that row |
| Product | Config file only. No add-user command, no roles, no hashing, no MFA |
**semantic-key:** PK-525

## 14. Locked summary (this slice)

| Topic | Lock |
|---|---|
| Reply header | Dim box: local `yyyy-MM-dd HH:mm:ss` · live model; LLM success only; body unchanged |
| Auth | `users.yaml` cascade; fallback single pair; `RegionOwner`; dup names refuse Boot |
| History file | `~/.casserole/{user}/history` |
| Exclusive | Server rule only; second same-user disk pane = red line (change username) |
| Line editor | 1-line insert / backspace / delete / left-right / home-end |
| Client store | `~/.casserole/ssh-client.yaml`; import old properties once |
| Splits | New Term → H/V dialog on **focused** pane; hotkeys; menu; no layout persist |
| Font | One size, all panes |
| Dividers | Dark gray + light outline |
| Help | Menu About + Help dialog; SSH `help` for commands |
| `message list` | Terse 120; `verbose` for full |
| `message find by` | Case-insensitive substring; terse hits |
| `cmd>` | Medium blue prompt |
| k/v color | Status only |

The HeadFile is the head.yaml file beside manifest.bin. The file name is head.yaml. A readable file has version: 1, a count, and a seqs list. count is the stored head count. seqs is the list of locked message ids. A missing file is no head. A version other than 1 is no head. A count of 0 is no head. An empty list is no head. A number that cannot be parsed is no head.
**semantic-key:** PK-1268

RegionOwner. 2. Gaps from the Stage 0 code scan — **locked** `[PUFF]` 002 → `[LOCK.... The code tag is the next pass.
**semantic-key:** PK-1346

`OpenResult` is a public type in this package.
