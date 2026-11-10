# org.aetheris.wire

Contract file. Two sides match this component.

**Depends:** This container names no other spec.

# Wire

This page builds the AMWP codec. The map is [program-spine.md](../config/config.spec.md). The contract is on the map. The procedure is here.

**semantic-key:** PK-5548

## Over 1 MiB fails the cell in a banner

A payload over 1 MiB, prefix included, fails that cell with `reason: too-large`, and this slice does not reassemble fragments.

Notebook scope stays one chunk. The floor cap stays 1 MiB. This slice does not reassemble `frag_count`. The client sends fragment index 0 and count 1.

`AmwpCodec.fits` measures the payload the codec will see, prefix included. The prefix is `FRAG_PREFIX_BYTES`, 24 bytes, in front of the body. The walker and the reply path both call `fits`. Over the cap, the cell is `FAILED` with `reason: too-large`, and `encode` is not asked to throw. The panel stays up.

The host still does not fetch the client database. The client does not split the body and hope the server joins it.

**Truth.** `AmwpCodec.fits` is the measure, prefix included, against a 1 MiB cap. Over the cap the cell is `FAILED` with `reason: too-large`. Scope stays one chunk. `frag_index` is 0 and `frag_count` is 1.

**Reason.** `encode` throws after the body is already built, and a measure of the body alone can pass a frame the codec then rejects because 24 prefix bytes were added.

**Avoidances.** Do not reassemble `frag_count` in this slice. Do not ask `encode` to throw for a body `fits` already rejected. Do not let the panel close on `too-large`. Do not split the body on the client. Do not have the host read the vault to shrink the frame.

**semantic-key:** PK-5549

## Init carries the facts, and day-one replies stay on 0x0003

`TYPE_INIT` carries `SessionFacts`, and day-one group replies stay on channel `0x0003`.

The finding is that the Init payload is empty, `num-ctx` is unreported, and `0x0100` is named and not allocated.

`SessionFacts.num-ctx` is always present from a server that has this body. `0` means omit the `num_ctx` field. An older server with an empty body stays "not reported." The inspector prints that phrase and does not pretend the cap is `0`. The named default on a new server is 4096. The published example is 8192.

Day-one group replies use `CHANNEL_STREAM` `0x0003`. The client already demuxes that channel by cell id. One data frame and one end-of-file per cell. One-shot `complete` stays.

`0x0100` stays the range for a later overlapping byte stream. This slice does not allocate it and does not put a channel list on Init.

The seam is the reply path that exists. No second demux.

**Truth.** A new server's Init body always includes `num-ctx`. `0` omits the model field. An empty body means not reported. Day-one member replies are one data frame and one end on `0x0003`, demuxed by cell id. `0x0100` is not allocated.

**Reason.** An empty Init cannot tell a omitted context size from a server that never spoke, and a member reply that waits on `0x0100` has no channel in the tree.

**Avoidances.** Do not treat an empty Init body as `0`. Do not allocate `0x0100` in this slice. Do not put a channel list on Init. Do not add a second demux. Do not retire the later-range sentence. It stays for a later overlapping stream. Do not split one-shot `complete` into a stream for day-one replies.

**semantic-key:** PK-5550

## One method measures the payload

`AmwpCodec.fits` measures the body plus the 24-byte prefix against 1 MiB, and over the cap the cell is `FAILED` with `reason: too-large`.

The finding is that `encode` checks the payload after the notebook payload has added 24 bytes.

`AmwpCodec.fits` takes the body that will sit after the prefix. The walker calls it before send. The reply path calls it before the prefix is attached. Over the cap, the cell is `FAILED` with `reason: too-large`, and `encode` is not asked to throw.

The reply catch that already turns an oversized encode into `TYPE_ERROR` stays, so a miss still cannot escape the mux.

The seam is that the codec owns the number. The panel does not add 24 on its own.

Notebook scope stays one chunk. `frag_index` is 0 and `frag_count` is 1. This slice does not reassemble.

**Truth.** `fits` is the only measure. The prefix is 24 bytes and is included. Over 1 MiB the reason is `too-large` and `encode` is not asked to throw. The existing catch remains the last resort.

**Reason.** A measure of the body alone passes a frame the codec rejects once `FRAG_PREFIX_BYTES` is added, and that throw happens inside a live run.

**Avoidances.** Do not let the panel add 24 on its own. Do not keep a second size table. Do not ask `encode` to throw after `fits` has already failed the cell. Do not remove the catch that keeps an oversized encode inside the mux. Do not reassemble fragments in this slice.

**semantic-key:** PK-5551

# AMWP codec

A frame is a sixteen-octet header plus a payload of at most one mebibyte. Java and Rust build that header the same way. Java turns a bad frame into a named failure on a stream. Rust turns a buffer into need-more, bad, or one frame, and its demux then sits in Wait, Mux, Raw, or Broken. The execute text is a separate line codec. It is not the frame header.


**semantic-key:** PK-0471 PK-0472 PK-0473 PK-0474 PK-0475 PK-0476 PK-0477 PK-0478 PK-0479

## 1. Header

Every multi-octet integer in the header is little-endian, and the integrity check covers the first twelve header octets and the payload, not the check field itself.

The payload begins at offset 16. Its length is the unsigned count stored at offset 8, bounded by `1024 * 1024` octets. That bound is `1048576`. A payload of that length is legal. A payload one octet longer is not.

| Offset | Width | Field |
|---|---|---|
| 0 | 1 | Magic `0xAE` |
| 1 | 1 | Version `1` |
| 2 | 2 | Channel |
| 4 | 1 | Type |
| 5 | 1 | Flags |
| 6 | 2 | Written as `0`. Not a named field. Not required to be `0` on read |
| 8 | 4 | Payload length |
| 12 | 4 | CRC-32C residue |
| 16 | length | Payload octets |

A little-endian 16-bit write stores the low octet at the first offset and the high octet at the next. A little-endian 32-bit write stores the low octet first and the high octet fourth. A little-endian read rebuilds the value in that same order. Java masks each octet with `0xFF` before shifting. The 32-bit read is a Java `int`, so a high bit in the fourth octet makes the result negative. Rust reads the channel as `u16` and the length and residue as `u32`.

The residue is CRC-32C starting from zero. Java uses `java.util.zip.CRC32C`. Rust uses `crc32c_append` from zero. Both feed octets `[0, 12)` first. Both feed the payload only when it is not empty. Both store the 32-bit residue little-endian at offsets 12 through 15. The residue octets are not fed back into the check. Offsets 6 and 7 are inside the twelve octets, so a nonzero reserved pair changes the residue, and a matching residue still decodes. Neither procedure rejects a nonzero reserved pair.

Java writes a channel from the low 16 bits of an `int`, and a type and a flags value from the low 8 bits. Rust takes a `u16` channel and `u8` type and flags, and those widths are already the widths on the wire. Both leave offsets 6 and 7 as zero because the header buffer starts zeroed and nothing else is stored there.

**Truth.** The header is sixteen octets, the integers are little-endian, the payload cap is `1024 * 1024`, and CRC-32C over `[0, 12)` plus the payload is the only integrity check.

**Reason.** The length and the type sit in the header, so a check of the payload alone would miss a flipped length or a flipped type. The residue cannot include itself. The cap is applied before the payload is kept, so the length field cannot demand an unbounded buffer.

**Avoidances.** Do not read the multi-octet fields big-endian. Do not fold the residue into the CRC input. Do not reject a frame only because offsets 6 and 7 are nonzero. Do not accept a payload longer than `1048576`. Do not treat a length equal to `1048576` as over the cap.

**semantic-key:** PK-5552

## 2. Names and numbers

A constant is in force only in the language that defines it. The same numeric value under two names is one width, not two protocols.

Shared values:

| Name in Java | Name in Rust | Value |
|---|---|---|
| `MAGIC` | `MAGIC` | `0xAE` |
| `VERSION` | `VERSION` | `1` |
| `HEADER_BYTES` | `HEADER_LEN` | `16` |
| `MAX_PAYLOAD` | `MAX_PAYLOAD` | `1024 * 1024` |
| `CHANNEL_T` | `CHANNEL_T` | `0x0001` |
| `CHANNEL_A` | `CHANNEL_A` | `0x0002` |
| `CHANNEL_STREAM` | `CHANNEL_STREAM` | `0x0003` |
| `TYPE_INIT` | `TYPE_INIT` | `0x00` |
| `TYPE_DATA` | `TYPE_DATA` | `0x01` |
| `TYPE_EOF` | `TYPE_EOF` | `0x03` |
| `TYPE_ERROR` | `TYPE_ERROR` | `0x04` |
| `TYPE_CANCEL` | `TYPE_CANCEL` | `0x05` |
| `TYPE_DELTA` | `TYPE_DELTA` | `0x06` |
| `TYPE_KEEPALIVE` | `TYPE_KEEPALIVE` | `0x09` |
| `FRAG_PREFIX_BYTES` | `FRAG_PREFIX` | `24` |
| `KEEPALIVE_MS` | `KEEPALIVE_MS` | `2000` milliseconds |
| `INIT_WAIT_MS` | `INIT_WAIT_MS` | `1500` milliseconds |

Java also defines:

| Name | Value | What the wire package does with it |
|---|---|---|
| `CHANNEL_TOOL` | `0x0004` | Names the channel. No codec, eater, peek, TOON, or listener branch reads it |
| `TYPE_PRUNE` | `0x02` | Names the type. No other type in the wire package reads it |
| `FLAG_FRAGMENT` | `0x04` | Mask used only by `AmwpFrame.isFragment` |
| `SERVER_PEEK_MS` | `400` milliseconds | Defined and not read by any other type in the wire package |

Java does not name a type `0x07` or `0x08`. The named types go from `0x06` to `0x09`. Java does not assign `0x0100` as a channel or as a type. The constants type says that value is not allocated.

Rust does not define `CHANNEL_TOOL`, `TYPE_PRUNE`, `FLAG_FRAGMENT`, or `SERVER_PEEK_MS`. Rust still carries whatever type octet and flags octet an encoder stored. An unnamed value is not a special case in `encode`. It becomes special only where a later Rust test compares the type with init, data, eof, delta, error, or keepalive.

`KEEPALIVE_MS`, `INIT_WAIT_MS`, and `SERVER_PEEK_MS` are durations. `encode`, `parse`, `take_frame`, and `Demux` do not sleep. The Rust demux exposes `timed_out` for a waiter that is still in Wait. The Java wire package does not start a timer and does not call a sleep of `400`, `1500`, or `2000`.

Java also publishes `AmwpPeekStream.TIMEOUT` as `-2`. That value is not an octet and not a frame field. Rust has no peek timeout constant.

**Truth.** The shared table is the frame vocabulary both sides name. Java-only names stay Java-only. Unnamed octets `0x07` and `0x08`, and the value `0x0100`, are not frame kinds in these sources.

**Reason.** A Rust match that only knows the names it defines will treat a Java-only name as an ordinary octet. Inventing the missing constant on the Rust side would make a tool channel or a prune type look implemented where the demux never mentions them.

**Avoidances.** Do not copy `CHANNEL_TOOL`, `TYPE_PRUNE`, `FLAG_FRAGMENT`, or `SERVER_PEEK_MS` into the Rust procedure as if the module defined them. Do not drop them from the Java procedure. Do not allocate `0x0100`. Do not name `0x07` or `0x08`. Do not make the codec sleep for the three durations.

**semantic-key:** PK-5553

## 3. Java encode and write

Java `encode` returns one new byte array, header then payload, or it throws before any frame exists.

`encode(channel, type, flags, payload)`:

1. A null payload is an empty payload. A non-null payload is used as given.
2. If the payload length is greater than `MAX_PAYLOAD`, throw `IllegalArgumentException` with the exact message `AMWP payload exceeds 1 MiB`. Do not allocate the frame.
3. Allocate `16 + payload length` octets, initially zero.
4. Store magic at 0, version at 1, the channel at 2, the type at 4, the flags at 5, and the payload length at 8.
5. Build a new CRC-32C over the first twelve header octets, then over the payload when the payload is not empty.
6. Store that residue at offset 12.
7. When the payload is not empty, copy it to offset 16. An empty payload adds no octets after the header.
8. Return the array. The caller may reuse the input payload. The returned array is a separate buffer.

`write(out, channel, type, flags, payload)` calls `encode`, writes every octet of that array to the output stream, then flushes. An `IOException` from the stream propagates. The size exception from `encode` propagates before the write. One call writes one frame and flushes once, including when the payload is empty.

`encode` does not look at `fits`, does not set `FLAG_FRAGMENT`, and does not parse the payload as text.

**Truth.** Java encode emits the header of section 1, copies the payload after the residue is computed, and refuses an oversize payload by throwing `AMWP payload exceeds 1 MiB`. `write` adds one flush per frame.

**Reason.** The size test sits before allocation so a caller cannot trade a long array for a frame. The flush sits on the frame boundary so a later reader is not left waiting for a finished frame that is still in a stream buffer.

**Avoidances.** Do not return a truncated frame for an oversize payload. Do not treat a null payload as a failure. Do not flush only when the payload is non-empty. Do not include the residue octets in the CRC input.

**semantic-key:** PK-5554

## 4. Java read and parse

Java `read` returns one frame, returns null on a clean end before any octet, or throws. It does not choose a session mode, and it does not decode UTF-8.

`read(in)`:

1. Read one octet.
2. If that read returns a negative value, return null. No exception. No header was consumed.
3. Otherwise place that octet at header index 0 and fill indexes 1 through 15 with `readFully`.
4. Return `parse` of that header and the same stream.

`readFully(buf, off, len)` reads until `len` octets have been stored at `off`. A read that returns a negative count throws `EOFException` with the exact message `AMWP truncated`. A read that returns zero does not finish the fill and does not throw. It is tried again. A short count that is positive advances the fill by that count.

`parse(header, in)`:

1. If the header is null or shorter than 16 octets, throw `IOException` with the exact message `AMWP header truncated`. A longer array is accepted. Only the first 16 octets are read.
2. If `(header[0] & 0xFF)` is not `0xAE`, throw `IOException` with the exact message `AMWP magic mismatch`.
3. Let the version be `(header[1] & 0xFF)`. If it is not `1`, throw `IOException` with the exact message `AMWP version ` followed by the decimal version and nothing else. Version 2 is the message `AMWP version 2`.
4. Read the channel from offsets 2 and 3, the type from offset 4, and the flags from offset 5. Do not inspect offsets 6 and 7.
5. Read the length from offsets 8 through 11 as a signed `int`. If the length is negative or greater than `MAX_PAYLOAD`, throw `IOException` with the exact message `AMWP payload length ` followed by that signed decimal integer. Do not read a payload. A length whose high bit is set is negative in this `int` and fails this test. A length from `1048577` through `2147483647` fails it as too large.
6. Allocate a payload of that length. If the length is positive, `readFully` it. A stream that ends during that fill throws `AMWP truncated`. Those octets stay consumed.
7. Compute CRC-32C over header `[0, 12)` and, when the length is positive, over the payload.
8. Read the expected residue from offsets 12 through 15 as a signed `int`. Compare it to the CRC value truncated to `int`. The high bit makes both sides negative together when the bits match. If they differ, throw `IOException` with the exact message `AMWP CRC32C mismatch`. The payload has already been consumed. The message does not include either residue.
9. Return an `AmwpFrame` of the channel, the type, the flags, and the payload. The payload array is the array just allocated. It is empty, not null, when the length is zero.

`read` always hands `parse` a 16-octet header, so `read` itself does not throw `AMWP header truncated`. A direct `parse` of a short or null header does.

A magic, version, or length failure happens before the payload read. The stream sits on the first octet after the sixteen header octets the caller already holds. A CRC failure happens after the payload read. The stream sits on the first octet after the payload.

`isMagic(b)` is true when `(b & 0xFF) == 0xAE`. The low eight bits decide. A value such as `0x1AE` matches. A negative Java byte whose low eight bits are `0xAE` matches.

`parse` does not branch on channel, type, or flags. A well-formed prune frame, tool-channel frame, keepalive, or unknown type is returned as a frame. The payload remains raw octets.

**Truth.** Java decode is a blocking stream read of exactly one frame, with a null return only when no octet of that frame arrived, and with the six messages above as the only codec failures.

**Reason.** An end before the first octet is a closed stream. An end after the first octet is a torn frame and must not look like a clean close. Separate messages keep a bad magic, a bad version, a bad length, a short header, a short body, and a bad residue from collapsing into one another.

**Avoidances.** Do not return null for a torn header or a torn payload. Do not throw on a clean end before the first octet. Do not UTF-8-decode the payload in `parse`. Do not drop a frame because the channel or the type is unfamiliar. Do not put the observed magic, the residues, or a unit suffix into those messages. Do not change the version message to anything other than the prefix `AMWP version ` plus the decimal octet.

**semantic-key:** PK-5555

## 5. Scope prefix

A scope payload is twenty-four octets followed by a body. The twenty-four octets are not a second header, and neither language reassembles fragments.

| Offset | Width | Field |
|---|---|---|
| 0 | 4 | Fragment index, little-endian |
| 4 | 4 | Fragment count, little-endian |
| 8 | 16 | Cell id |
| 24 | rest | Body |

Java `scopeAPayload(fragIndex, fragCount, cellId, body)`:

1. If the cell is null or its length is not 16, use sixteen new zero octets. Otherwise use the given cell.
2. A null body is an empty body.
3. Allocate `24 + body length`. This method does not apply `MAX_PAYLOAD`.
4. Write the index at offset 0 and the count at offset 4.
5. Copy the sixteen cell octets to offset 8.
6. When the body is not empty, copy it to offset 24.
7. Return that array.

Java `fits(body)` is true when `(long) 24 + (long) bodyLength <= (long) MAX_PAYLOAD`. A null body has length zero, so the total is 24 and the result is true. The sum is a 64-bit add. `fits` does not encode and does not throw. It counts the prefix inside the payload. It does not count the sixteen-octet frame header. A true result is a payload that `encode` will accept for size. A false result is a payload that would throw `AMWP payload exceeds 1 MiB`.

Java `stripFragPrefix(payload)`:

1. Null returns a new empty array.
2. A payload shorter than 24 returns that same array, not a copy.
3. Otherwise return a new array of the octets from offset 24 to the end. A payload of exactly 24 returns a new empty array.

Java `cellIdOf(payload)` always returns a new 16-octet array. It is zeros when the payload is null or shorter than 24. Otherwise it is a copy of offsets 8 through 23. The method never returns null. It does not read the index or the count.

Rust `scope_payload(cell, body)` allocates `24 + body length` zeros, sets octet 4 to `1`, copies the sixteen cell octets to offsets 8 through 23, and copies a non-empty body to offset 24. The index stays zero. The count is the little-endian value 1 because only the low octet of the count is set. The cell argument is already sixteen octets. There is no null cell and no caller-chosen index or count.

Rust `scope_a_body` returns the subslice from offset 24 when the payload length is at least 24. A shorter payload is returned whole, as the body, including when it is empty. Rust `cell_of` returns sixteen zeros when the payload is shorter than 24, and otherwise copies offsets 8 through 23.

Neither `stripFragPrefix`, `cellIdOf`, `scope_a_body`, `cell_of`, `parse`, nor the demux reads the index or the count. A count other than 1 is still just payload. `FLAG_FRAGMENT` is not consulted by those readers. `isFragment` is the only Java test of that bit, and nothing else in the wire package calls it.

**Truth.** The prefix is index, count, and sixteen cell octets. Java can write any index and count and substitutes zeros for a missing cell. Rust writers in this module always write index 0 and count 1. Readers strip or copy. They do not reassemble.

**Reason.** A short payload has to remain readable, or a peer that omitted the prefix would be rejected as corrupt. A null Java cell has to occupy the same sixteen octets or the body would slide forward. The cap on a scope body includes the prefix because the prefix rides inside the payload.

**Avoidances.** Do not require the prefix before accepting a short payload as a body. Do not return null from `cellIdOf` or `cell_of`. Do not treat `fits` as a splitter. Do not drop a frame because the fragment count is not 1. Do not make Rust `scope_payload` take a caller-chosen index. Do not make the Java method ignore a caller-chosen index.

**semantic-key:** PK-5556

## 6. Java frame and listener

An `AmwpFrame` is the channel, the type, the flags, and the payload octets from one successful decode, or the same four values from a direct constructor.

The constructor stores a null payload as a new empty array. It stores a non-null payload as that same array, not a copy. The getters return those stored values. `getPayload` therefore aliases a caller-supplied array. A frame from `parse` holds the array `parse` allocated.

`isFragment` is true when `(flags & 0x04) != 0`. No other flag bit is named. Encode and parse do not branch on the bit.

`AmwpClientListener` is three callbacks. The interface does not switch threads. Its contract is that the caller invokes them off the UI thread. The wire package does not call them.

- `onAmwpFrame(frame)` receives one scope or stream frame.
- `onAmwpClosed(reason)` receives the caller's reason string when the mux has gone raw or has closed. This interface does not fix the reason text.
- `onConsoleModel(model, think)` is the console asking for the model-config surface. `model` is the shell's model id, empty when the shell has none. `think` empty means off, the word `true` means on, and any other value is a level. The interface does not check those words.

**Truth.** A frame value is four fields and a fragment-bit test. The listener is a callback contract, not a decoder and not a thread hop.

**Reason.** The payload has to stay raw octets so a terminal frame and a TOON frame can share one decode. The listener has to stay outside the codec so a CRC failure cannot be mistaken for a model-config call.

**Avoidances.** Do not UTF-8-decode inside `AmwpFrame`. Do not copy the payload in the constructor. Do not treat `isFragment` as a reassembly signal. Do not add a fourth listener method. Do not invent a fixed close-reason catalog.


**semantic-key:** PK-0480

## 7. Java init eater

The init eater is a one-shot filter over another input stream. It is armed until the first `read()` that is allowed to pull octets. It never writes an answer.

`read()`:

1. If it is not armed, return one read from the source.
2. Clear the arm before touching the source. A throw from that first source read leaves the eater disarmed.
3. If the source returns negative, return that value.
4. If the octet is not magic, return that octet. Do not read ahead.
5. If the octet is magic, attempt one consume, then return the next source read. The consume's true and false results take the same last step. The boolean is not visible in the returned value. It only records whether a parsed frame had type init.

The consume:

1. Place the magic octet in a 16-octet header and fill the rest from the source.
2. A negative read before the header is full returns false. The octets already taken are not put back. This is not the `AMWP truncated` exception.
3. A zero read during the fill does not finish it and does not return. It is tried again.
4. An `IOException` from a source read, other than a negative count, propagates out of `read`.
5. Once sixteen octets are in hand, call `parse`. Any `IOException` from `parse`, including `AMWP truncated`, `AMWP magic mismatch`, `AMWP version ...`, `AMWP payload length ...`, and `AMWP CRC32C mismatch`, is caught. The method returns false. The exception is not rethrown.
6. How far the stream moved depends on `parse`. A version or length failure has consumed only the header. A CRC failure, a truncated-payload failure, or a success has consumed the payload that `parse` managed to read. A success consumes the whole frame.
7. A successful parse returns true only when the type is init. Any other type still returns false, and the frame is not put back.

So a magic lead-in consumes one parse attempt. A frame that parses is discarded whether or not it is init. The caller then sees whatever octet follows the octets the attempt consumed, or a negative end.

`read(buf, off, len)`:

1. A null buffer or a length less than or equal to zero returns 0. The arm is unchanged. The source is not read.
2. Otherwise take one octet through `read()`, which may run the one-shot above.
3. If that octet is negative, return `-1`.
4. Store it at `buf[off]`.
5. If the caller asked for more and `src.available()` is positive, read the remainder straight from the source into `buf` at `off + 1`. A positive count is added. A zero or negative count leaves the return value at 1 and does not unread. This extra read does not re-enter the arm logic.
6. If the source reports nothing available, return 1 even when the caller asked for more.

`available` is the source's `available`. It does not add back a frame the consume already took. `close` closes the source.

**Truth.** The eater disarms on the first real read, drops one parsed frame that begins with magic, does not restore a non-init frame, and does not write an acknowledgement.

**Reason.** The arm has to clear before the first source read so a thrown read cannot consume a later init on retry. The type test cannot put bytes back because the method has no unread slot. Both results of that test therefore surface as the next source octet.

**Avoidances.** Do not echo the consumed frame. Do not write a reply frame. Do not leave the eater armed after a failed first read. Do not treat a non-init that parsed as something the caller still receives. Do not fill a bulk read by blocking when `available` is zero. Do not disarm on a zero-length or null-buffer bulk read.

**semantic-key:** PK-5557

## 8. Java peek stream

The peek stream is the only reader of its source. A blocking wait takes from an unbounded queue of boxed integers. It does not call `available` on the source.

Construction starts a daemon thread named `amwp-byte-pump`. The thread runs until the stream is closed or the source fails.

The pump:

1. While `closed` is false, read one octet from the source.
2. A negative read queues `-1` and the pump returns.
3. Any other read queues `b & 0xFF`, which is an integer from 0 through 255, then loops.
4. If the loop sees `closed` before a read, it returns and queues no sentinel.
5. A byte that was read before the loop notices `closed` is still queued, and the following loop check can then exit with no `-1`.
6. Any exception, including a read failure and an interrupted queue put, attempts to queue one `-1`. If that put is interrupted, the interrupt flag is set and no further sentinel is attempted. A data octet whose put threw is not queued.

`pollByte(timeoutMs)`:

1. If an unread integer is stored, clear it and return it. The timeout is not used.
2. Otherwise poll the queue for `timeoutMs` milliseconds.
3. A null poll returns `TIMEOUT`, which is `-2`.
4. An interrupt sets the interrupt flag and returns `-2`, not `-1`.

`unread(b)` stores that integer in the one slot, replacing any previous unread integer. The stored value is returned unchanged by the next `pollByte` or single-byte `read`. The slot accepts an octet or `-1`, and the code does not mask it.

`read()`:

1. If an unread integer is stored, clear it and return it.
2. Otherwise take from the queue, blocking with no timeout.
3. An interrupt sets the interrupt flag and returns `-1`. That is the same numeric result as a queued end, with the interrupt flag set.

`read(buf, off, len)`:

1. A null buffer or a non-positive length returns 0 and does not take an unread value or a queued value.
2. Take the first octet through `read()`.
3. If it is negative, return `-1` and do not write the buffer.
4. Store it at `buf[off]`.
5. While the buffer is not full, poll the queue without waiting. Do not consult the unread slot in this loop.
6. A null poll ends the fill. The method returns the count already stored, which is at least 1.
7. A queued value less than zero is not stored in the buffer. The unread slot becomes `-1`, whatever the negative value was, and the fill ends. The next `read` or `pollByte` therefore observes end.
8. Any other queued value is stored and the count grows.

`available` is the queue size, plus one when the unread slot is set. Octets still inside the source are not included. `close` sets `closed`, interrupts the pump, and closes the source.

**Truth.** The pump is the sole source reader. `pollByte` returns `0` through `255`, `-1` for a queued end, or `-2` for a timeout or an interrupted wait. `read` uses `-1` both for end and for its own interrupt.

**Reason.** A source can report no bytes available while a blocking read would still return data, so the wait has to sit on the queue the pump fills. One unread slot is enough to put back the single end marker a bulk read must not swallow.

**Avoidances.** Do not call `available` on the source to decide a blocking peek. Do not use `-2` as an octet. Do not let a bulk read treat a queued negative value as data. Do not drop that negative on the floor. Do not wait for a full bulk buffer. Do not start a second reader on the same source.


**semantic-key:** PK-0481 PK-0482

## 9. TOON bodies

The execute body, the error body, and the session-fact body are UTF-8 text lines. They are not JSON. The frame codec does not parse them. Java `AmwpToon` writes and reads them. Rust writes an execute body and does not read one.

A value is escaped before it is placed on a line. Java `esc` maps null to empty, then replaces `\` with `\\`, then replaces a line feed with the two characters `\` and `n`, then deletes every carriage return. Rust walks the string and does the same three things to each character. It has no null string. Unescaping happens only in Java, and only after a line has been split, so a newline inside a value cannot become a second field line. Java `unesc` maps null to empty. A backslash followed by `n` becomes a line feed. A backslash followed by a backslash becomes one backslash. Any other backslash is kept, and the following character is handled on the next step. A final backslash with nothing after it is kept.

Java `truth` is true only for `true` in any case, `yes` in any case, or the exact character `1`. The caller trims first. `TRUE`, `Yes`, and `1` are true. `01`, `on`, and the empty string are false.

**semantic-key:** PK-5558
### Execute body

An execute body names one cell and one target, then writes a flag or a string only when that value is on, and always ends with the working set.

Java `ExecuteBody` defaults: empty `cellId`, empty `executionTarget`, `ephemeral` false, `toolCallingEnabled` false, `burnerCloud` false, `thinkStream` false, empty `reasoningEffort`, `sendReasoningEffort` true, empty `think`, `temperaturePresent` false, `temperature` `0.0`, `maxCompletionPresent` false, `maxCompletionTokens` `0`, empty `poolBoot`, and an empty working set.

A Java `Turn` defaults to role `user`, empty text, and `head` false.

Absent booleans stay off the wire, except `send_reasoning_effort`, which is written only when it is false. Absent or empty strings stay off the wire. A temperature or a max-token value is written only when its present flag is set. Zero is written when the flag is set. Absence is not the integer zero. `think_stream` is its own boolean. It is not the `think` string. The encoder does not turn `toolCallingEnabled` on by itself. A caller that sets it true gets one line.

Java `encodeExecute` always emits, in this order, with a line feed after every line:

1. `cell_id: ` plus the escaped cell string.
2. `execution_target: ` plus the escaped target.
3. `ephemeral: true` only when ephemeral is true.
4. `tool_calling_enabled: true` only when tool calling is true.
5. `burner_cloud: true` only when that flag is true.
6. `think_stream: true` only when that flag is true.
7. `send_reasoning_effort: false` only when send-reasoning-effort is false. The codec still writes a `reasoning_effort` line later if that string is non-empty. It does not suppress one because of the other.
8. `reasoning_effort: ` plus the escaped string, only when the string is non-null and not empty.
9. `think: ` plus the escaped string, only when the string is non-null and not empty.
10. `temperature: ` plus `Double.toString` of the temperature, only when the present flag is set.
11. `max_completion_tokens: ` plus the decimal `int`, only when the present flag is set. A negative value keeps its minus sign. There is no plus sign.
12. `pool_boot: ` plus the escaped string, only when the string is non-null and not empty.
13. `working_set:` even when there are no turns.
14. For each turn, in list order: `- role: ` plus the escaped role, then `  text: ` plus the escaped text, then `  head: true` only when that turn's head flag is true.

The role line has no leading spaces. The text line and the head line each have two leading spaces. The encoder never writes `head: false`, `ephemeral: false`, or `think_stream: false`. The result is UTF-8.

Java `decodeExecute`:

1. Null or a zero-length array returns a default body. `sendReasoningEffort` stays true. Nothing is reported as a parse error.
2. Otherwise decode the bytes as UTF-8. The platform UTF-8 decoder's replacement is used for a sequence that is not UTF-8. The codec does not throw for that.
3. Split on `\n` and keep a trailing empty line. An empty line matches nothing.
4. Walk the lines. The first matching test wins. Top-level tests use the line as it stands, so a leading space makes them miss. The value is the remainder after the prefix, trimmed, then unescaped. Trim runs before unescape, so a value that is only outer whitespace becomes empty.
5. Match order: `cell_id:`, `execution_target:`, `ephemeral:`, `tool_calling_enabled:`, `burner_cloud:`, `think_stream:`, `send_reasoning_effort:`, `reasoning_effort:`, `think:`, `temperature:`, `max_completion_tokens:`, `pool_boot:`, then a trimmed line that starts with `- role:`, then, when a turn is current, a trimmed line that starts with `text:`, then, when a turn is current, a trimmed line that starts with `head:`.
6. Boolean lines use `truth`. A present `send_reasoning_effort` line that is not truthy sets the flag false, including an empty remainder. An absent line leaves the default true.
7. `temperature:` parses with `Double.parseDouble`. Success sets the present flag and the value. `NumberFormatException` sets the present flag false and does not write a new double. A later bad line clears the flag and leaves an earlier double sitting in the field.
8. `max_completion_tokens:` parses with `Integer.parseInt`. Success sets the present flag and the value. Failure sets the present flag false and does not write a new int.
9. A `- role:` line starts a new turn, sets its role from the escaped remainder, appends it, and makes it current. The default role `user` is overwritten by that remainder, which may be empty.
10. A `text:` or `head:` line before any role is ignored. After a role, `text:` overwrites that turn's text and `head:` overwrites that turn's head through `truth`.
11. A line that matches none of the tests is ignored. `working_set:` is such a line. Ignoring it does not clear the current turn. The next `text:` or `head:` still applies to that turn. A later scalar line overwrites that scalar. The last one wins.

The codec does not check that a role is `system`, `user`, or `assistant`. It stores the string it is given.

**semantic-key:** PK-5559
### Error body

An error body is structured only when the text starts with `reason:`. Anything else is a plain banner, kept whole.

Java `encodeError(reason, message)` always writes two lines, `reason: ` plus the escaped reason, then `message: ` plus the escaped message. Null arguments become empty values. The result is UTF-8.

Java `decodeError` takes a string, not bytes.

1. Null yields an unstructured body, empty reason, empty message.
2. If the string does not start with `reason:`, the whole string is the message, unstructured, not unescaped, not split. A leading space keeps it unstructured. The reason stays empty.
3. Otherwise the body is structured. Split on `\n`, keep a trailing empty line, and walk. A line that starts with `reason:` sets the reason through trim and unescape. Else a line that starts with `message:` sets the message the same way. Any other line is ignored. The last matching line wins. A structured body with no message line keeps an empty message.

**semantic-key:** PK-5560
### Session facts

A fact body always writes four lines, and an empty array is not reported.

Java `SessionFacts` defaults: `reported` false, `airGap` `unset`, `numCtxPresent` false, `numCtx` `0`, `managedTokens` `0`, `headCount` `0`.

`encodeFacts(airGap, numCtx)` calls the four-argument form with managed tokens `0` and head count `0`.

`encodeFacts(airGap, numCtx, managedTokens, headCount)` always writes four lines. A null or empty air-gap word is written as `unset`. Otherwise the word is escaped as given. The three integers are decimal, not escaped, and they are written even when they are zero:

1. `air-gap: ` plus the word.
2. `num-ctx: ` plus the integer.
3. `managed-tokens: ` plus the integer.
4. `head-count: ` plus the integer.

`decodeFacts`:

1. Null or a zero-length array returns the defaults. `reported` stays false. That state is not the same as a body that reports the integers zero.
2. Any other array sets `reported` true before lines are read. A body that contains no known line is still reported. The air-gap word stays `unset` until an `air-gap:` line appears.
3. Decode UTF-8 the same way as an execute body, split on `\n`, and keep a trailing empty line.
4. `air-gap:` sets the word through trim and unescape.
5. `num-ctx:` sets `numCtxPresent` true, then `Integer.parseInt`. On `NumberFormatException`, `numCtxPresent` becomes false and the stored integer is left unchanged.
6. `managed-tokens:` sets the integer from `parseInt`, or sets it to `0` on `NumberFormatException`. There is no present flag. An absent line, a bad line, and a written zero are all zero.
7. `head-count:` uses that same rule.
8. No other line changes the facts. There is no range check.

A body `num-ctx: 0` is reported, and `numCtxPresent` is true. An empty body is not reported, and `numCtxPresent` is false. A bad `num-ctx` line looks absent by the flag, while a previously parsed integer can still sit in the field. A reader that honors the flag ignores that leftover.

**Truth.** Java TOON writes the execute lines in the order above, omits false booleans except the explicit `send_reasoning_effort: false` line, and treats an empty fact array as not reported. Rust does not decode these lines.

**Reason.** An omitted false flag has to mean the default, so writing `false` for every flag would make absence and presence look alike. An empty fact array has to stay unreported, or a missing sheet would look like a server that sent zeros. Trim-before-unescape keeps a padding space from becoming part of the value, and it also means outer whitespace is not significant.

**Avoidances.** Do not write `head: false` or `ephemeral: false`. Do not omit `working_set:` when the list is empty. Do not omit a zero `num-ctx`, `managed-tokens`, or `head-count` line from an encoded fact body. Do not require `air-gap:` or `num-ctx:` before `reported` becomes true. Do not treat a missing `managed-tokens` line as anything but zero. Do not unescape a plain error banner. Do not let a newline inside a value start a new field. Do not JSON-encode the body.

**semantic-key:** PK-5561

## 10. Rust encode, take, and builders

Rust `encode` returns `None` when the payload is longer than `MAX_PAYLOAD`, and otherwise `Some` of the header plus the payload. It has no error string.

The bytes match section 1. The output buffer is zeroed, so offsets 6 and 7 stay zero. The channel occupies offsets 2 and 3. The type is offset 4. The flags are offset 5. The length is the payload length as `u32` at offset 8. The CRC-32C step matches Java, including the skip of an empty payload update. A payload equal to `MAX_PAYLOAD` is returned. There is no null payload. A slice is empty or it is not.

`take_frame` does not throw and does not name a failure.

1. Fewer than 16 octets is need-more.
2. If octet 0 is not `0xAE` or octet 1 is not `1`, the result is bad. Those two failures are one result.
3. The length is the little-endian `u32` at offsets 8 through 11, as `usize`. If it is greater than `MAX_PAYLOAD`, the result is bad. This test happens before the method waits for a payload. A length with the high bit set is a large unsigned value and fails this test. It is not a distinct message.
4. If the buffer is shorter than `16 + length`, the result is need-more.
5. Copy the payload out. Compute CRC-32C the same way as encode. Compare it as `u32` with offsets 12 through 15. A mismatch is bad.
6. Otherwise the result is a frame and the total length `16 + payload`. The frame holds the channel, the type, the flags, and an owned payload. Offsets 6 and 7 are not checked.

Need-more means the caller keeps the bytes. Bad means this slice is not a frame. The function does not say which check failed.

`cell_text` writes sixteen octets as lowercase hexadecimal with hyphens in the 8-4-4-4-12 pattern, two digits per octet, octets 0 through 15 in order. Sixteen zero octets are `00000000-0000-0000-0000-000000000000`. The function does not inspect version or variant nibbles.

`wire_double`:

1. A NaN is the string `NaN`.
2. A positive infinity is `Infinity`. A negative infinity is `-Infinity`.
3. Otherwise render the finite value with the default display. If that text contains `.`, `e`, or `E`, use it. If it contains none of those, append `.0`.

Java writes a present temperature with `Double.toString`. Rust writes a present temperature with `wire_double`. This module does not decode a temperature.

`ExecuteTune` holds a target string, ephemeral, burner-cloud, think-stream, send-reasoning-effort, reasoning-effort, think, an optional temperature, an optional `i32` max, and a pool-boot string. `ExecuteTune::ollama(ephemeral)` sets the target to the word `ollama`, copies that ephemeral flag, sets send-reasoning-effort true, and sets every other flag false, every string empty, and both options absent. There is no tool-calling field.

`SetLine` is a role string, a text string, and a head flag. The encoder does not check the role.

`execute_tuned(cell, tune, lines)` builds one UTF-8 body, wraps it with `scope_payload`, and encodes channel `0x0002`, type `0x01`, flags `0`. The line order matches Java `encodeExecute` except there is no `tool_calling_enabled` line. `cell_id` is `cell_text` of the same sixteen octets copied into the prefix, not a caller-supplied spelling. The target is escaped. Booleans follow the Java omission rules, including `send_reasoning_effort: false` only when the flag is false. Temperature uses `wire_double` when the option is present. The max uses decimal `i32` text when the option is present. `working_set:` is always written. Each line is `- role: `, then `  text: `, then `  head: true` only when head is set. The result is `None` when the prefixed payload exceeds `MAX_PAYLOAD`.

`execute_set(cell, lines)` is `execute_tuned` with `ollama(false)`, so the target is `ollama` and ephemeral is false. `execute_frame(cell, prompt)` is `execute_set` with one line whose role is `user`, whose text is the prompt, and whose head flag is false.

`text_frame(cell, text)` is channel A, type data, flags 0, prefix count 1, and the UTF-8 octets of the string as the body. It does not require the body to be TOON. It returns `None` when the prefixed payload is over the cap.

`scope_t_data(bytes)` is channel `0x0001`, type data, flags 0, and those octets as the payload with no prefix. It returns `None` when they exceed the cap.

`init_frame` is channel A, type init, flags 0, and a 24-octet prefix: index 0, count 1, sixteen zero cell octets, empty body. `keepalive_frame` is channel A, type keepalive, flags 0, and an empty payload, so the frame is exactly 16 octets and has no prefix. `cancel_frame(cell)` is channel A, type cancel, flags 0, prefix count 1, that cell, and an empty body. Each of those three calls `expect` if `encode` returns `None`. The panic texts are `init fits`, `keepalive fits`, and `cancel fits`. Their payloads are 24, 0, and 24 octets, so the ordinary path does not panic. They return the byte vector, not an option.

**Truth.** Rust encode is the shared header with `None` past one mebibyte. `take_frame` is need-more, bad, or one frame. The builders above are the only framed messages this module constructs, and only Java writes `tool_calling_enabled`.

**Reason.** `None` is the size refusal because this encoder has no exception channel. Need-more is not bad, because a split read is how a buffer demux works. The init, keepalive, and cancel helpers are fixed small payloads, so their failure path is a panic text rather than an option the caller is expected to handle. An execute that grows with the working set stays an option.

**Avoidances.** Do not throw `AMWP payload exceeds 1 MiB` from Rust encode. Do not add an error string to `take_frame`. Do not treat need-more as bad. Do not make `execute_tuned` panic on an oversize body. Do not emit `tool_calling_enabled` from `ExecuteTune`. Do not give `init_frame` a fact body. Do not put a prefix on `keepalive_frame` or on `scope_t_data`. Do not decode TOON in this module.

**semantic-key:** PK-5562

## 11. Rust demux

The demux starts in Wait, with an empty buffer and no scope pieces. It leaves Wait only for Mux, Raw, or Broken. `timed_out` is the only other leave, and only from Wait to Raw.

The public tests are `is_wait`, `is_mux`, `is_raw`, and `is_broken`. `take_scope_a` returns the queued pieces and leaves the queue empty. Pieces already queued stay queued when the mode changes, until that take. `push` does not clear them.

A scope piece is one of: `Init` of a body; `Later` of a cell and a body; `Fault` of a cell and a body; `Reply` of a cell and a body; `End` of a cell; `Delta` of a cell, a kind octet, and a body. `Later` is channel A data. `Fault` is channel A error. `Reply` and `End` and `Delta` are stream pieces. A short payload yields a zero cell wherever a cell is read.

`push(bytes)` returns the octets to paint.

1. In Broken, return an empty vector. Ignore the new bytes. Do not buffer them.
2. In Raw, return a copy of the new bytes. Do not parse them. Do not buffer them.
3. Otherwise append the bytes and walk the buffer.

Wait:

1. An empty buffer stops the walk. The mode stays Wait. The return is empty unless an earlier frame in this same call already painted something. On the first call it is empty.
2. If the first buffered octet is not magic, paint the entire buffer, clear it, become Raw, and stop. Bytes that arrived in the same push are part of that paint.
3. Otherwise `take_frame`.
4. Need-more stops the walk and stays in Wait. The partial bytes remain buffered. Nothing new is painted.
5. Bad paints the entire buffer, clears it, becomes Raw, and stops. Bad here is a wrong version, a length over the cap, or a CRC miss, because a wrong magic was already handled as the first-octet test. Bytes after the bad span in the same buffer are painted too. They are not parsed later.
6. A complete frame whose type is neither init nor data paints the entire buffer, clears it, becomes Raw, and stops. Keepalive, eof, error, cancel, delta, prune's octet `0x02`, and any other type take this path when they are first. The keepalive skip used inside Mux is not used here.
7. A complete frame whose type is init or data is removed from the front of the buffer, and the mode becomes Mux. The channel does not have to be A. `keep_scope_a` runs on that frame. If the channel is T and the payload is not empty, the payload octets, not the header, are painted. The walk then continues in Mux on whatever remains in the buffer.

Mux:

1. An empty buffer stops the walk.
2. Need-more stops the walk and stays in Mux. The partial frame stays buffered.
3. Bad clears the whole buffer, becomes Broken, and stops. Nothing from that buffer is painted, including octets that followed the bad frame. Pieces already taken from earlier frames in this walk remain queued and remain in the paint vector already built.
4. A complete frame is removed from the front.
5. If its type is keepalive, on any channel, it is dropped, payload included, and the walk continues. It is not painted and it is not a scope piece.
6. Otherwise `keep_scope_a` runs. If the channel is T and the payload is not empty, the payload is appended to the paint. An empty channel T payload adds nothing.

`keep_scope_a`:

1. On channel stream, read the cell, then branch on type. Eof pushes `End` and ignores the body, including an empty body and a short payload. Data pushes `Reply` only when `scope_a_body` is non-empty. Delta, when that body is non-empty, pushes `Delta` whose kind is the first body octet and whose body is the remainder, which may be empty when the raw body was one octet. Every other stream type, including error, cancel, and init, pushes nothing. The method then returns. A stream error is not a `Fault`.
2. If the channel is not A, return. Channel T, channel `0x0004`, and any other channel push no piece.
3. On channel A, return unless the type is init, data, or error. Cancel, eof, delta, keepalive, and `0x02` push no piece here. A keepalive never reaches this point from Mux, because `push` already continued.
4. Take the body through `scope_a_body`. If that body is empty, return. A 24-octet prefix with an empty tail is an empty body and is not kept. The client init frame is that shape, so it opens Mux and queues nothing. A payload shorter than 24 is its own body. If that short payload is non-empty, it is kept, and the cell is zeros.
5. Otherwise push `Init` of the body, or `Fault` of the cell and the body for an error, or `Later` of the cell and the body for data.

A first init or data frame and a later frame can arrive in one `push`. The first is handled by the Wait rules. The rest are handled by the Mux rules in the same call. A later bad frame still promotes nothing backward. The frames already accepted stay accepted, then Broken drops the remainder.

`timed_out` while the mode is Wait paints the buffered octets, which may be empty, clears that buffer by taking it, and becomes Raw. `timed_out` in Mux, Raw, or Broken returns an empty vector and does not change the mode.

After Raw, further bytes are painted unparsed. After Broken, further bytes vanish. A CRC miss in Wait is Raw and the bytes are painted. A CRC miss in Mux is Broken and the bytes are not painted.

Channel T is the only channel whose payload is painted, and only when the frame was accepted and the type is not keepalive and the payload is not empty. Accepted channel A and stream frames stay off that paint. Their non-empty kept bodies come out of `take_scope_a` instead. Any other channel is consumed and dropped, aside from the promotion rule that an init or data type on that channel can still be what moves Wait to Mux.

The body test is not a minimum length of 24. A channel A data payload of the two octets `n` and `o` is a `Later` with a zero cell and that two-octet body. A stream data payload that is shorter than 24 and not empty is a `Reply` of those octets. A delta uses the first of those body octets as the kind. The kind is not checked against a list. This module does not name the kind values.

**Truth.** Wait becomes Mux only for a complete first frame of type init or data. Any other first failure or first type becomes Raw and paints the buffer. After Mux, a bad frame becomes Broken and paints nothing more. Keepalive is ignored only after Mux. Empty channel A bodies are not pieces. Stream eof is `End` even when the body is empty. Stream error is not `Fault`.

**Reason.** A peer that never completes a mux frame still has octets to show, so Wait must be able to fall to Raw and paint what it held. A bad frame after the mux is up must not be shown as terminal text, so Mux falls to Broken and drops the buffer. An empty channel A body must not become facts or a later card, or the client's own empty init would look like a report.

**Avoidances.** Do not enter Mux on a first keepalive, error, eof, cancel, or delta. Do not paint a CRC miss after Mux. Do not treat a CRC miss during Wait as Broken. Do not resync by skipping a single octet. Do not keep a stream error as `Fault`. Do not drop a stream eof because the body is empty. Do not drop a short non-empty data body because it lacks a prefix. Do not paint channel A onto the terminal path. Do not clear queued scope pieces merely because the mode changed.

**semantic-key:** PK-5563

## 12. Where Java and Rust differ

A difference counts only where these sources do not do the same thing. The header layout, the shared constants, the CRC coverage, the little-endian integers, the reserved octets, and the one-mebibyte cap are not differences.

1. Oversize encode: Java throws `IllegalArgumentException` `AMWP payload exceeds 1 MiB`. Rust `encode` returns `None`.
2. Java treats a null payload, a null scope body, and a null scope cell as empty or as sixteen zeros. Rust borrows slices and fixed sixteen-octet cells and has no null.
3. Java masks an `int` channel to 16 bits and an `int` type and flags to 8 bits. Rust arguments are already `u16` and `u8`.
4. Java `read` returns null on a clean end before the first octet and throws the named messages in section 4. Rust `take_frame` returns need-more, bad, or a frame, with no message. A short buffer is need-more, not `AMWP truncated`. A bad magic and a bad version are one bad result. Java reports `AMWP magic mismatch` and `AMWP version ` plus the octet as different failures.
5. Java's length is a signed `int`. A high bit makes it negative and the message includes that negative number. Rust's length is unsigned and the same bit pattern is bad because it is above the cap.
6. Java `parse` of a short header throws `AMWP header truncated`. Rust never has that state. It waits until sixteen octets exist.
7. Java returns every well-formed frame. Rust's demux then drops, paints, or keeps it by the rules in section 11. Java has no Wait, Mux, Raw, or Broken mode.
8. Java names `CHANNEL_TOOL`, `TYPE_PRUNE`, `FLAG_FRAGMENT`, and `SERVER_PEEK_MS`. Rust does not. Both can still carry flags `0x04` and type `0x02` as raw octets. Only Java reports the fragment bit, and nothing in the Java wire package acts on it.
9. Java `scopeAPayload` writes the caller's index and count. Rust `scope_payload` writes index 0 and count 1. Java `fits` exists. Rust has no `fits`. The Rust execute and text builders learn the cap from `encode` returning `None`.
10. Java `stripFragPrefix` copies a long tail, returns a short array by reference, and turns null into a new empty array. Rust `scope_a_body` returns a subslice or the original slice.
11. Java decodes execute, error, and fact text. Rust does not. Java can write `tool_calling_enabled: true`. Rust `ExecuteTune` cannot. Java's execute `cell_id` is the caller's string. Rust's is `cell_text` of the sixteen prefix octets. A present Java temperature is `Double.toString`. A present Rust temperature is `wire_double`.
12. Rust builds `init_frame`, `keepalive_frame`, `cancel_frame`, `text_frame`, `execute_frame`, `execute_set`, and `execute_tuned`. The Java wire package has none of those helpers. The three fixed Rust helpers panic with `init fits`, `keepalive fits`, or `cancel fits` if `encode` ever returns `None`.
13. Java's init eater and peek stream are not the Rust demux. The eater discards one parsed leading frame and does not ack. The peek stream is a pump and a one-slot unread. The demux is the four-mode buffer machine. Neither procedure implements the other.
14. `AmwpClientListener` exists only on the Java side. The Rust demux delivers paint bytes and scope pieces instead of those callbacks.
15. Both define `KEEPALIVE_MS` as `2000` and `INIT_WAIT_MS` as `1500`. Only Java defines `SERVER_PEEK_MS` as `400`. None of the encode, parse, or demux procedures sleep. Java `write` flushes each frame. Rust `encode` only returns bytes.

**Truth.** The two procedures share the frame and differ in the failure signal, the constant set, the prefix writer, the TOON reader, the helpers, and the session machine. Rust encode's oversize result is `None`. Rust demux states are Wait, Mux, Raw, and Broken.

**Reason.** One failure channel cannot be both a Java exception text and a Rust option without pretending one of the sources does something it does not. One state machine cannot be both the init eater and the demux. Those are different filters with different leftovers.

**Avoidances.** Do not describe Rust encode as throwing `AMWP payload exceeds 1 MiB`. Do not describe Java `read` as returning need-more. Do not add the Java-only constants to the Rust module in this spec. Do not delete them from the Java side. Do not claim the init eater is a four-state demux. Do not claim the demux writes an acknowledgement. Do not invent a second difference for the shared header, the shared cap, or the shared CRC.


**semantic-key:** PK-5564
